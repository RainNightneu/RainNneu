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

// 网卡信息
typedef struct AdapterInfo          
{
	char szDeviceName[128];           // 名字
	char szIPAddrStr[16];             // IP
	char szHWAddrStr[18];             // MAC
	DWORD dwIndex;                    // 编号          
}INFO_ADAPTER, *PINFO_ADAPTER;

// ARP条目信息
typedef struct ARPInfo             
{
	char szIPAddrStr[16];			  // IP 
	char szHWAddrStr[18];             // MAC
	DWORD dwType;                     // 类型
}INFO_ARP, *PINFO_ARP;


class CSysInfo
{ 
public:
	CSysInfo(void);
	~CSysInfo(void);

	static void AddAdapInfoToList(CListCtrl& list);

	static void AddARPInfoToList(CListCtrl& list,const short nAdpaterIndex);

	inline static char* GetCurAdapterName(const short nAdapterIndex)   // 返回对应网卡名字 
	{
		return AdapterList[nAdapterIndex].szDeviceName;
	}
	static char* GetCurAdapterMAC(const short nAdapterIndex);         // 返回对应网卡MAC

	inline static char* GetCurAdapterIP(const short nAdapterIndex)    //返回对应网卡IP
	{
		return AdapterList[nAdapterIndex].szIPAddrStr;
	}

	static void formatStrToMAC(const LPSTR lpHWAddrStr,unsigned char *HWAddr);// 用户输入的MAC地址字符串赋给数据包结构体

private:

	static INFO_ADAPTER	AdapterList[ MAX_ADAPTER];               // 网卡列表
	static INFO_ARP		ARPList[MAX_ARP	];                       // ARP列表

	static void formatMACToStr(LPSTR lpHWAddrStr,const unsigned char *HWAddr);
};

