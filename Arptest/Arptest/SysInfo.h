#pragma once

//#include <Iphlpapi.h>
#include <WinSock2.h>
#include ".\Iphlpapi.h"
#include ".\IPRTRMIB.h"
#include ".\IPTYPES.h"
#include ".\IPEXPORT.h"
//#pragma comment(lib, "Iphlpapi.lib")
#pragma comment(lib, "ws2_32.lib")
#pragma comment(lib, ".\\wpdpack\\Iphlpapi.lib")
#define MAX_ADAPTER	10
#define MAX_ARP		20

// ������Ϣ
typedef struct AdapterInfo          
{
	char szDeviceName[128];           // ����
	char szIPAddrStr[16];             // IP
	char szHWAddrStr[18];             // MAC
	DWORD dwIndex;                    // ���          
}INFO_ADAPTER, *PINFO_ADAPTER;

// ARP��Ŀ��Ϣ
typedef struct ARPInfo             
{
	char szIPAddrStr[16];			  // IP 
	char szHWAddrStr[18];             // MAC
	DWORD dwType;                     // ����
}INFO_ARP, *PINFO_ARP;


class CSysInfo
{ 
public:
	CSysInfo(void);
	~CSysInfo(void);

	static void AddAdapInfoToList(CListCtrl& list);

	static void AddARPInfoToList(CListCtrl& list,const short nAdpaterIndex);

	inline static char* GetCurAdapterName(const short nAdapterIndex)   // ���ض�Ӧ�������� 
	{
		return AdapterList[nAdapterIndex].szDeviceName;
	}
	static char* GetCurAdapterMAC(const short nAdapterIndex);         // ���ض�Ӧ����MAC

	inline static char* GetCurAdapterIP(const short nAdapterIndex)    //���ض�Ӧ����IP
	{
		return AdapterList[nAdapterIndex].szIPAddrStr;
	}

	static void formatStrToMAC(const LPSTR lpHWAddrStr,unsigned char *HWAddr);// �û������MAC��ַ�ַ����������ݰ��ṹ��

private:

	static INFO_ADAPTER	AdapterList[ MAX_ADAPTER];               // �����б�
	static INFO_ARP		ARPList[MAX_ARP	];                       // ARP�б�

	static void formatMACToStr(LPSTR lpHWAddrStr,const unsigned char *HWAddr);
};

