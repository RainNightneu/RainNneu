#include "StdAfx.h"
#include "SysInfo.h"
INFO_ADAPTER	CSysInfo::AdapterList[MAX_ADAPTER];
INFO_ARP		CSysInfo::ARPList[MAX_ARP];

CSysInfo::CSysInfo(void)
{
}

CSysInfo::~CSysInfo(void)
{
}


void CSysInfo::AddAdapInfoToList(CListCtrl& list)//获得系统的网卡信息，并将其添加到list控件中

{
	char tempChar;
	ULONG uListSize=1;
	PIP_ADAPTER_INFO pAdapter;           // 定义PIP_ADAPTER_INFO结构存储网卡信息
	int nAdapterIndex = 0;

	DWORD dwRet = GetAdaptersInfo((PIP_ADAPTER_INFO)&tempChar, &uListSize); // 关键函数,调用GetAdaptersInfo函数获取网络需求缓冲区信息,并存到uListSize中


	if (dwRet == ERROR_BUFFER_OVERFLOW) //调用GetAdaptersInfo((PIP_ADAPTER_INFO)&tempChar, &uListSize)成功

	{
		PIP_ADAPTER_INFO pAdapterListBuffer = (PIP_ADAPTER_INFO)new(char[uListSize]);//给pAdapterInfo分配uListSize大小的内存空间

		dwRet = GetAdaptersInfo(pAdapterListBuffer, &uListSize);//获取网络信息，并存储到pAdapterListBuffer

		if (dwRet == ERROR_SUCCESS)//调用GetAdaptersInfo(pAdapterInfo,&uListSize)函数成功

		{
			pAdapter = pAdapterListBuffer;
			while (pAdapter)                        // 枚举网卡,然后将相关条目添加到List中
			{
				CString strTemp = pAdapter->AdapterName;                     // 网卡名字
				strTemp = "\\Device\\NPF_" + strTemp;                        // 加上前缀 
				list.InsertItem(nAdapterIndex,strTemp);                  
				strcpy(AdapterList[nAdapterIndex].szDeviceName,strTemp);

				strcpy(AdapterList[nAdapterIndex].szIPAddrStr, pAdapter->IpAddressList.IpAddress.String );// IP
				list.SetItemText(nAdapterIndex,1,AdapterList[nAdapterIndex].szIPAddrStr);

				formatMACToStr( AdapterList[nAdapterIndex].szHWAddrStr, pAdapter->Address ); // MAC
				list.SetItemText(nAdapterIndex,2,AdapterList[nAdapterIndex].szHWAddrStr);

				AdapterList[nAdapterIndex].dwIndex = pAdapter->Index;           // 编号

				pAdapter = pAdapter->Next;

				nAdapterIndex ++;
			}
			delete pAdapterListBuffer;//释放缓冲区内存空间
		}
	}
}


void CSysInfo::AddARPInfoToList(CListCtrl& list,const short nAdapterIndex)// 读入系统的ARP缓存列表,并添加到对话框中
                                                                         //用到了IpHelper api GetIpNetTable  而且用到了WinSock的api，所以要包含<WinSock2.h>
{
	char tempChar;
	DWORD dwListSize = 1;
	DWORD dwRet;

	in_addr inaddr;

	list.DeleteAllItems();                                                       

	dwRet = GetIpNetTable((PMIB_IPNETTABLE)&tempChar, &dwListSize, TRUE);  // 关键函数   IP Helper API GetIpNetTable 函数能够提取出本机上的所有ARP表项
	if (dwRet == ERROR_INSUFFICIENT_BUFFER)
	{
		PMIB_IPNETTABLE pIpNetTable = (PMIB_IPNETTABLE)new(char[dwListSize]);//分配内存空间
		dwRet = GetIpNetTable(pIpNetTable, &dwListSize, TRUE);
		if (dwRet == ERROR_SUCCESS)
		{
			for (int i=0; i<(int)pIpNetTable->dwNumEntries; i++) //dwNumEntries为PMIB_IPNETTABLE结构体数组大小
			{
				inaddr.S_un.S_addr = pIpNetTable->table[i].dwAddr; //table[i]为PMIB_IPNETTABLE结构体数组本身
				strcpy( ARPList[i].szIPAddrStr, inet_ntoa(inaddr) );                 // IP
				formatMACToStr( ARPList[i].szHWAddrStr, pIpNetTable->table[i].bPhysAddr ); // MAC
				ARPList[i].dwType = pIpNetTable->table[i].dwType;         // Type

				if (AdapterList[nAdapterIndex].dwIndex != pIpNetTable->table[i].dwIndex)	
					continue;

				list.InsertItem(i,ARPList[i].szIPAddrStr);
				list.SetItemText(i,1, ARPList[i].szHWAddrStr);
				switch(ARPList[i].dwType) {                         // 根据type的值来转换成字符显示
				case 3:
					list.SetItemText(i,2,"Dynamic");
					break;
				case 4:
					list.SetItemText(i,2,"Static");
					break;
				case 1:
					list.SetItemText(i,2,"Invalid");
				default:
					list.SetItemText(i,2,"Other");
				}
					
			}
		}
		delete pIpNetTable;
	}
}

void CSysInfo::formatMACToStr(LPSTR lpHWAddrStr,const unsigned char *HWAddr)//将用户输入的MAC地址字符转成相应格式

{
	int i;
	short temp;
	char szStr[3];

	strcpy(lpHWAddrStr, "");
	for (i=0; i<6; ++i)
	{
		temp = (short)(*(HWAddr + i));
		_itoa(temp, szStr, 16);
		if (strlen(szStr) == 1)	strcat(lpHWAddrStr, "0");
		strcat(lpHWAddrStr, szStr);
		if (i<5)	strcat(lpHWAddrStr, "-");         // 加上 - 
	}
}
void CSysInfo::formatStrToMAC(const LPSTR lpHWAddrStr, unsigned char *HWAddr) //将用户输入的MAC地址字符转成数据包结构体需要的格式

{
	unsigned int i, index = 0, value, temp;
	unsigned char c;

	_strlwr(lpHWAddrStr);                            // 转换成小写

	for (i = 0; i < strlen(lpHWAddrStr); i++)
	{
		c = *(lpHWAddrStr + i);
		if (( c>='0' && c<='9' ) || ( c>='a' && c<='f' ))
		{
			if (c>='0' && c<='9')	temp = c - '0';             // 数字
			if (c>='a' && c<='f')	temp = c - 'a' + 0xa;       // 字母,0xa表示字母的开始
			if ( (index % 2) == 1 )
			{
				value = value*0x10 + temp;
				HWAddr[index/2] = value;
			}
			else	value = temp;
			index++;
		}
		if (index == 12) break;
	}
}

char* CSysInfo::GetCurAdapterMAC(const short nAdapterIndex) //获取网卡MAC
{
	static char chTemp[12];
	int j=0;
     for(int i=0;i<18;i++)                     // 去掉mac地址中间的横线 
	{
		if(AdapterList[nAdapterIndex].szHWAddrStr[i]!='-')
		{
	    	chTemp[j]=AdapterList[nAdapterIndex].szHWAddrStr[i];
			j++;
		}
	}
	return chTemp;
}