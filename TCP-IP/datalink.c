#include <stdio.h>
#include <string.h>
#include "protocol.h"
#include "datalink.h"

#define MAX_SEQ    31                     //���֡���
#define NR_BUFS    16                     //��������NR_BUFS=((MAX+SEQ+1)/2)
#define DATA_TIMER 5000                   //DATA_TIMERʱ��
#define ACK_TIMER  280                    //ACK_TIMERʱ��                   
#define inc(k) if(k<MAX_SEQ)k++;else k=0  //ѭ����1 �����֡���кŹ��� 
typedef enum {false, true} bool;          //����C����û�в�������������ò�������
typedef unsigned char seq_nr;             //֡��Ż�ȷ�Ϻ�

typedef struct 
{
	unsigned char info[PKT_LEN];
}packet;                                  //����

static int phl_ready = 0;                 //�����������λ
bool no_nak=true;                         //no_ackδ������

typedef struct
{
    unsigned char kind;                   //֡����
    seq_nr ack;                           //ȷ�Ϻ�
	seq_nr seq;                           //֡���
    packet data;                          //��������
    unsigned int  padding;                //CRCУ���
}frame;


//��֡����ʱ���жϸ�֡�Ƿ����ڽ��շ�������
static bool between (seq_nr a, seq_nr b, seq_nr c)
{
	return ((a <= b)&&(b < c)) || ((c < a)&&(a <= b)) || ((b < c)&&(c < a));
}


//����CRC-32 У��ͣ������͵������
static void put_frame(unsigned char *frame, int len) 
{
    *(unsigned int *)(frame + len) = crc32(frame, len);             //���ɲ����32λУ���
	send_frame(frame, len + 4);                                     //���ڴ�frame�������鷢�͵������
	                                                                //ÿ�ֽڷ�����Ҫ1ms��֡��֮֡��ı߽籣��1ms
    phl_ready = 0;                                                  //��������δ����
}


//��������֡��ACK֡�� NAK֡
static void send_data(unsigned char fk,seq_nr frame_nr,seq_nr frame_expected,packet buffer[])
{
    frame s;
    s.kind = fk;                                                     //֡����
    s.seq = frame_nr;                                                //֡���
    s.ack = (frame_expected+ MAX_SEQ)%(MAX_SEQ+1);                   //ȷ�Ϻ�

	if(fk == FRAME_DATA)
		memcpy(s.data.info, buffer[frame_nr%NR_BUFS].info, PKT_LEN); //������������
	if(fk == FRAME_NAK)
		no_nak = false;                                              //ÿ��֡���෢��һ��NAK

	if(fk == FRAME_DATA)
		dbg_frame("==== Send DATA seq:%d ack:%d, ID %d\n", s.seq, s.ack, *(short *)s.data.info);
	if(fk == FRAME_NAK)
		dbg_frame("==== Send NAK  ack:%d ====\n", s.ack);
	if(fk == FRAME_ACK)
		dbg_frame("==== Send ACK  ack:%d ====\n", s.ack);

	if (fk == FRAME_DATA)
     	put_frame((unsigned char *)&s, 3 + PKT_LEN);                 //����У��Ͳ����͵�����㣬����=kind+ack+seq+data
	if(fk == FRAME_NAK||fk==FRAME_ACK)
		put_frame((unsigned char *)&s, 2);                           //����У��Ͳ����͵�����㣬����=kind+ack

    if(fk == FRAME_DATA)
		start_timer(frame_nr%NR_BUFS, DATA_TIMER);                   //����frame_nr�Ŷ�ʱ����ʱ��ΪDATA_TIMER
	stop_ack_timer();                                                //�ر����Ӵ�ȷ�ϵ�ack��Ӧ�Ķ�ʱ��
}

int main(int argc, char **argv)
{
	int event, arg;
    frame f;
    int len = 0;

	seq_nr ack_expected;           //���ʹ����½�
	seq_nr next_frame_to_send;     //���ʹ����Ͻ�+1
	seq_nr frame_expected;         //���մ����½�
    seq_nr too_far;                //���մ����Ͻ�+1
	
 
	packet out_buf[NR_BUFS];       //���ͻ�����
	packet in_buf[NR_BUFS];        //���ջ�����
	
	bool arrived[NR_BUFS];         //���ջ�������־λ����Ƕ�Ӧ�������Ƿ���������
	seq_nr nbuffered;              //���ͻ�������ʹ������
	seq_nr i;                      //�������±�

	/*��ʼ������*/
    protocol_init(argc, argv);     //Э�����л�����ʼ��
	enable_network_layer();       

	/*������ʼ��*/
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
        event = wait_for_event(&arg);                                               //�ȴ��¼�(��5�����)
        switch(event)
		{
            case NETWORK_LAYER_READY:                                               //���������¼�
				   nbuffered++;                                                     //���ͻ�������ʹ��������1
                   get_packet(out_buf[next_frame_to_send%NR_BUFS].info);            //��������ȡ����
                   send_data(FRAME_DATA,next_frame_to_send,frame_expected,out_buf); //��������
			       inc(next_frame_to_send);                                         //next_frame_to_send��1
                   break;

            case PHYSICAL_LAYER_READY:                                              //���������¼�
                 phl_ready = 1;                                                     //�����������
                 break;

            case FRAME_RECEIVED:                                                    //֡�����¼�
                 len = recv_frame((unsigned char *)&f, sizeof(f));                  //��������ȡ֡
                 if (len < 5 || crc32((unsigned char *)&f, len) !=0)                //CRCУ��ʹ���
				 {               
                     dbg_event("!!!! Receiver Error, Bad CRC Checksum\n");          
                     if(no_nak)
                         send_data(FRAME_NAK,0,frame_expected,out_buf);             //����NAK֡
                     break;
                 }
                 if(f.kind == FRAME_DATA)
				 {
                    if((f.seq != frame_expected) && no_nak)                         //֡˳�����
                         send_data(FRAME_NAK,0,frame_expected,out_buf);             //����NAK֡
                    else
                         start_ack_timer(ACK_TIMER);                                //����ACK��ʱ��

                    if(between(frame_expected,f.seq,too_far)&&(arrived[f.seq%NR_BUFS]==false))
					{
                         arrived[f.seq%NR_BUFS] = true;                             //��ǻ�����ռ��
                          in_buf[f.seq%NR_BUFS] = f.data;                           //��������
						 
						 /*�Ͻ����ݵ������*/
                         while(arrived[frame_expected%NR_BUFS])
						 {
                             put_packet(in_buf[frame_expected%NR_BUFS].info, len-7);//����=֡����-kind-ack-seq-CRC
                             no_nak = true;
                             arrived[frame_expected%NR_BUFS]=false;
                             inc(frame_expected);                                   //�ı���մ��ڱ߽�
                             inc(too_far);
                             start_ack_timer(ACK_TIMER);                            //����ACK��ʱ��
                         }  
                    }
                 }
				 /*�յ�NAKʱ���ط��Է���frame_expected����֡*/
                 if((f.kind == FRAME_NAK) && between(ack_expected,(f.ack+1)%(MAX_SEQ+1),next_frame_to_send))
                     send_data(FRAME_DATA,(f.ack+1)%(MAX_SEQ+1),frame_expected,out_buf);
				 
                 while(between(ack_expected,f.ack,next_frame_to_send))
				 {
                       nbuffered--;
                       stop_timer(ack_expected%NR_BUFS);                            //�رն�ʱ��
                       inc(ack_expected);                                           //�޸ķ��ʹ��ڱ߽�
                 }
                 break;

            case ACK_TIMEOUT:                                                       //ACK�Ӵ�ȷ�ϳ�ʱ�¼�
				 dbg_event("<<<< ACK timeout >>>>\n");
                 send_data(FRAME_ACK,0,frame_expected,out_buf);                     //����ACK֡
                 break;
            case DATA_TIMEOUT:                                                      //��������֡δ�յ�ACK��ʱ�¼�
                 dbg_event(">>>> DATA %d timeout <<<<\n", arg);
		         if(!between(ack_expected,arg,next_frame_to_send))
                    arg = arg + NR_BUFS;                                            //��ȡ֡��ţ�arg��arg+NR_BUFS���ö�ʱ����
                 send_data(FRAME_DATA,arg,frame_expected,out_buf);                  //�ط�����֡
                 break;
        }
        if (nbuffered <NR_BUFS && phl_ready)
            enable_network_layer();
        else
            disable_network_layer();
    }
}            
