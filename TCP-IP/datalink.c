#include <stdio.h>
#include <string.h>
#include "protocol.h"
#include "datalink.h"

#define MAX_SEQ    31                     //最大帧序号
#define NR_BUFS    16                     //窗口数，NR_BUFS=((MAX+SEQ+1)/2)
#define DATA_TIMER 5000                   //DATA_TIMER时限
#define ACK_TIMER  280                    //ACK_TIMER时限                   
#define inc(k) if(k<MAX_SEQ)k++;else k=0  //循环加1 到最大帧序列号归零 
typedef enum {false, true} bool;          //由于C语言没有布尔变量因此设置布尔类型
typedef unsigned char seq_nr;             //帧序号或确认号

typedef struct 
{
	unsigned char info[PKT_LEN];
}packet;                                  //分组

static int phl_ready = 0;                 //物理层就绪标记位
bool no_nak=true;                         //no_ack未被发送

typedef struct
{
    unsigned char kind;                   //帧类型
    seq_nr ack;                           //确认号
	seq_nr seq;                           //帧序号
    packet data;                          //网络层分组
    unsigned int  padding;                //CRC校验和
}frame;


//当帧到达时，判断该帧是否落在接收方窗口内
static bool between (seq_nr a, seq_nr b, seq_nr c)
{
	return ((a <= b)&&(b < c)) || ((c < a)&&(a <= b)) || ((b < c)&&(c < a));
}


//生成CRC-32 校验和，并发送到物理层
static void put_frame(unsigned char *frame, int len) 
{
    *(unsigned int *)(frame + len) = crc32(frame, len);             //生成并添加32位校验和
	send_frame(frame, len + 4);                                     //将内存frame缓冲区块发送到物理层
	                                                                //每字节发送需要1ms，帧与帧之间的边界保留1ms
    phl_ready = 0;                                                  //标记物理层未就绪
}


//发送数据帧、ACK帧或 NAK帧
static void send_data(unsigned char fk,seq_nr frame_nr,seq_nr frame_expected,packet buffer[])
{
    frame s;
    s.kind = fk;                                                     //帧类型
    s.seq = frame_nr;                                                //帧序号
    s.ack = (frame_expected+ MAX_SEQ)%(MAX_SEQ+1);                   //确认号

	if(fk == FRAME_DATA)
		memcpy(s.data.info, buffer[frame_nr%NR_BUFS].info, PKT_LEN); //拷贝网络层分组
	if(fk == FRAME_NAK)
		no_nak = false;                                              //每个帧至多发送一次NAK

	if(fk == FRAME_DATA)
		dbg_frame("==== Send DATA seq:%d ack:%d, ID %d\n", s.seq, s.ack, *(short *)s.data.info);
	if(fk == FRAME_NAK)
		dbg_frame("==== Send NAK  ack:%d ====\n", s.ack);
	if(fk == FRAME_ACK)
		dbg_frame("==== Send ACK  ack:%d ====\n", s.ack);

	if (fk == FRAME_DATA)
     	put_frame((unsigned char *)&s, 3 + PKT_LEN);                 //生成校验和并发送到物理层，长度=kind+ack+seq+data
	if(fk == FRAME_NAK||fk==FRAME_ACK)
		put_frame((unsigned char *)&s, 2);                           //生成校验和并发送到物理层，长度=kind+ack

    if(fk == FRAME_DATA)
		start_timer(frame_nr%NR_BUFS, DATA_TIMER);                   //启动frame_nr号定时器，时限为DATA_TIMER
	stop_ack_timer();                                                //关闭已捎带确认的ack对应的定时器
}

int main(int argc, char **argv)
{
	int event, arg;
    frame f;
    int len = 0;

	seq_nr ack_expected;           //发送窗口下界
	seq_nr next_frame_to_send;     //发送窗口上界+1
	seq_nr frame_expected;         //接收窗口下界
    seq_nr too_far;                //接收窗口上届+1
	
 
	packet out_buf[NR_BUFS];       //发送缓冲区
	packet in_buf[NR_BUFS];        //接收缓冲区
	
	bool arrived[NR_BUFS];         //接收缓冲区标志位，标记对应缓冲区是否已有数据
	seq_nr nbuffered;              //发送缓冲区已使用数量
	seq_nr i;                      //缓冲区下标

	/*初始化操作*/
    protocol_init(argc, argv);     //协议运行环境初始化
	enable_network_layer();       

	/*变量初始化*/
	ack_expected = 0;
	next_frame_to_send = 0;
	frame_expected = 0;
	too_far =NR_BUFS;
	nbuffered = 0;
	for (i=0;i<=NR_BUFS;i++)
		arrived[i]=false;

//	lprintf("Designed by 2013211324-2013211325, build: " __DATE__"  "__TIME__"\n");

    while(true)
	{
        event = wait_for_event(&arg);                                               //等待事件(共5种情况)
        switch(event)
		{
            case NETWORK_LAYER_READY:                                               //网络层就绪事件
				   nbuffered++;                                                     //发送缓冲区已使用数量加1
                   get_packet(out_buf[next_frame_to_send%NR_BUFS].info);            //从网络层获取分组
                   send_data(FRAME_DATA,next_frame_to_send,frame_expected,out_buf); //发送数据
			       inc(next_frame_to_send);                                         //next_frame_to_send加1
                   break;

            case PHYSICAL_LAYER_READY:                                              //物理层就绪事件
                 phl_ready = 1;                                                     //标记物理层就绪
                 break;

            case FRAME_RECEIVED:                                                    //帧到达事件
                 len = recv_frame((unsigned char *)&f, sizeof(f));                  //从物理层获取帧
                 if (len < 5 || crc32((unsigned char *)&f, len) !=0)                //CRC校验和错误
				 {               
                     dbg_event("!!!! Receiver Error, Bad CRC Checksum\n");          
                     if(no_nak)
                         send_data(FRAME_NAK,0,frame_expected,out_buf);             //发送NAK帧
                     break;
                 }
                 if(f.kind == FRAME_DATA)
				 {
                    if((f.seq != frame_expected) && no_nak)                         //帧顺序错误
                         send_data(FRAME_NAK,0,frame_expected,out_buf);             //发送NAK帧
                    else
                         start_ack_timer(ACK_TIMER);                                //启动ACK定时器

                    if(between(frame_expected,f.seq,too_far)&&(arrived[f.seq%NR_BUFS]==false))
					{
                         arrived[f.seq%NR_BUFS] = true;                             //标记缓冲区占用
                          in_buf[f.seq%NR_BUFS] = f.data;                           //插入数据
						 
						 /*上交数据到网络层*/
                         while(arrived[frame_expected%NR_BUFS])
						 {
                             put_packet(in_buf[frame_expected%NR_BUFS].info, len-7);//长度=帧长度-kind-ack-seq-CRC
                             no_nak = true;
                             arrived[frame_expected%NR_BUFS]=false;
                             inc(frame_expected);                                   //改变接收窗口边界
                             inc(too_far);
                             start_ack_timer(ACK_TIMER);                            //启动ACK定时器
                         }  
                    }
                 }
				 /*收到NAK时，重发对方的frame_expected数据帧*/
                 if((f.kind == FRAME_NAK) && between(ack_expected,(f.ack+1)%(MAX_SEQ+1),next_frame_to_send))
                     send_data(FRAME_DATA,(f.ack+1)%(MAX_SEQ+1),frame_expected,out_buf);
				 
                 while(between(ack_expected,f.ack,next_frame_to_send))
				 {
                       nbuffered--;
                       stop_timer(ack_expected%NR_BUFS);                            //关闭定时器
                       inc(ack_expected);                                           //修改发送窗口边界
                 }
                 break;

            case ACK_TIMEOUT:                                                       //ACK捎带确认超时事件
				 dbg_event("<<<< ACK timeout >>>>\n");
                 send_data(FRAME_ACK,0,frame_expected,out_buf);                     //发送ACK帧
                 break;
            case DATA_TIMEOUT:                                                      //发送数据帧未收到ACK超时事件
                 dbg_event(">>>> DATA %d timeout <<<<\n", arg);
		         if(!between(ack_expected,arg,next_frame_to_send))
                    arg = arg + NR_BUFS;                                            //获取帧序号（arg和arg+NR_BUFS共用定时器）
                 send_data(FRAME_DATA,arg,frame_expected,out_buf);                  //重发数据帧
                 break;
        }
        if (nbuffered <NR_BUFS && phl_ready)
            enable_network_layer();
        else
            disable_network_layer();
    }
}            
