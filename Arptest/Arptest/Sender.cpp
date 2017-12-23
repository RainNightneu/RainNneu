#include "StdAfx.h"
#include "Sender.h"
#include "SysInfo.h"

ARPPACKET	CSender::ARPPacket;
LPADAPTER	CSender::lpAdapter;
LPPACKET	CSender::lpPacket;

CSender::CSender(void)
{
}

CSender::~CSender(void)
{
}

void CSender::SendARPPacket(short nAdapterIndex)
{
	char *AdapterDeviceName = CSysInfo::GetCurAdapterName(nAdapterIndex);  // 获得网卡名字

	lpAdapter = PacketOpenAdapter(AdapterDeviceName);     // 打开网卡

	if (!lpAdapter || (lpAdapter->hFile == INVALID_HANDLE_VALUE))
	{
		AfxMessageBox("打开网卡错误！");
		return;
	}

	lpPacket = PacketAllocatePacket();          // 给PACKET结构指针分配内存
	if (lpPacket == NULL)
	{
		PacketCloseAdapter(lpAdapter);
		AfxMessageBox("AllocatePacket错误！");
		return;
	}

	PacketInitPacket(lpPacket, &ARPPacket, sizeof(ARPPacket));   //初始化PACKET结构指针

	if (PacketSetNumWrites(lpAdapter, 1) == 0)       // 每次只发送一个包
	{
		PacketFreePacket(lpPacket);
		PacketCloseAdapter(lpAdapter);
		AfxMessageBox("SetNumWrites错误！");
		return;
	}

	if (PacketSendPacket(lpAdapter, lpPacket, true) == 0)  // Send 
	{
		PacketFreePacket(lpPacket);
		PacketCloseAdapter(lpAdapter);
		AfxMessageBox("发送数据包错误！");
		return;
	}

	PacketFreePacket(lpPacket);
	PacketCloseAdapter(lpAdapter);
}




void CSender::formatARPPacket(char* srcDLC,char* desDLC,char* srcMAC,char* srcIP,
							  char* desMAC,char* desIP,int arpType)
{
	memset(&ARPPacket, 0, sizeof(ARPPACKET));                         // 数据包初始化为0

	CSysInfo::formatStrToMAC(srcDLC,ARPPacket.dlcHeader.SrcMAC);       // DLC帧头
	CSysInfo::formatStrToMAC(desDLC,ARPPacket.dlcHeader.DesMAC);

	CSysInfo::formatStrToMAC(srcMAC,ARPPacket.arpFrame.Send_HW_Addr);  // 源地址
	ARPPacket.arpFrame.Send_Prot_Addr = inet_addr(srcIP);              
	CSysInfo::formatStrToMAC(desMAC,ARPPacket.arpFrame.Targ_HW_Addr);  // 目的地址
	ARPPacket.arpFrame.Targ_Prot_Addr = inet_addr(desIP);
	
	ARPPacket.arpFrame.Opcode = htons((unsigned short)arpType);        // arp包类型
     
	// 自动填充常量
	ARPPacket.dlcHeader.Ethertype = htons((unsigned short)0x0806);     // DLC Header的以太网类型
	ARPPacket.arpFrame.HW_Type = htons((unsigned short)1);             // 硬件类型
	ARPPacket.arpFrame.Prot_Type = htons((unsigned short)0x0800);      // 上层协议类型
	ARPPacket.arpFrame.HW_Addr_Len = (unsigned char)6;                 // MAC地址长度
	ARPPacket.arpFrame.Prot_Addr_Len = (unsigned char)4;               // IP地址长度


}
