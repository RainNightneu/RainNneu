#pragma once

#include <Packet32.h>
#include ".\Devioctl.h"
//#pragma comment(lib, "Ws2_32.lib")
#pragma comment(lib, ".\\wpdpack\\Packet.lib")

#pragma pack(push, 1)                        // 位移
typedef struct DLCHeader                     // DLC Header
{
	unsigned char		DesMAC[6];			/* destination HW addrress */
	unsigned char		SrcMAC[6];			/* source HW addresss */
	unsigned short		Ethertype;			   /* ethernet type */
} DLCHEADER, *PDLCHEADER;

typedef struct ARPFrame                      // ARP Frame
{
	unsigned short		HW_Type;			/* hardware address */
	unsigned short		Prot_Type;			/* protocol address */
	unsigned char		HW_Addr_Len;		/* length of hardware address */
	unsigned char		Prot_Addr_Len;		/* length of protocol address */
	unsigned short		Opcode;			    /* ARP/RARP */

	unsigned char		Send_HW_Addr[6];     /* sender hardware address */
	unsigned long		Send_Prot_Addr;      /* sender protocol address */
	unsigned char		Targ_HW_Addr[6];     /* target hardware address */
	unsigned long		Targ_Prot_Addr;      /* target protocol address */
	unsigned char		padding[18];
} ARPFRAME, *PARPFRAME;

typedef struct ARPPacket                  // ARP Packet = DLC header + ARP Frame
{
	DLCHEADER		dlcHeader;
	ARPFRAME		arpFrame;
} ARPPACKET, *PARPPACKET;
#pragma pack(pop)

class CSender
{
public:
	CSender(void);
	~CSender(void);

public:
	static void formatARPPacket(char* srcDLC,char* desDLC,char* srcMAC,char* srcIP,
		char* desMAC,char* desIP,int arpType);

	static void SendARPPacket(short nAdpaterIndex);

private:
	static ARPPACKET ARPPacket;

	static LPADAPTER lpAdapter;       // 在pcap头文件中定义，网卡结构指针

	static LPPACKET	lpPacket;         // 数据包结构指针


};
