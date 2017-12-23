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


void CSysInfo::AddAdapInfoToList(CListCtrl& list)//���ϵͳ��������Ϣ�����������ӵ�list�ؼ���

{
	char tempChar;
	ULONG uListSize=1;
	PIP_ADAPTER_INFO pAdapter;           // ����PIP_ADAPTER_INFO�ṹ�洢������Ϣ
	int nAdapterIndex = 0;

	DWORD dwRet = GetAdaptersInfo((PIP_ADAPTER_INFO)&tempChar, &uListSize); // �ؼ�����,����GetAdaptersInfo������ȡ�������󻺳�����Ϣ,���浽uListSize��


	if (dwRet == ERROR_BUFFER_OVERFLOW) //����GetAdaptersInfo((PIP_ADAPTER_INFO)&tempChar, &uListSize)�ɹ�

	{
		PIP_ADAPTER_INFO pAdapterListBuffer = (PIP_ADAPTER_INFO)new(char[uListSize]);//��pAdapterInfo����uListSize��С���ڴ�ռ�

		dwRet = GetAdaptersInfo(pAdapterListBuffer, &uListSize);//��ȡ������Ϣ�����洢��pAdapterListBuffer

		if (dwRet == ERROR_SUCCESS)//����GetAdaptersInfo(pAdapterInfo,&uListSize)�����ɹ�

		{
			pAdapter = pAdapterListBuffer;
			while (pAdapter)                        // ö������,Ȼ�������Ŀ���ӵ�List��
			{
				CString strTemp = pAdapter->AdapterName;                     // ��������
				strTemp = "\\Device\\NPF_" + strTemp;                        // ����ǰ׺ 
				list.InsertItem(nAdapterIndex,strTemp);                  
				strcpy(AdapterList[nAdapterIndex].szDeviceName,strTemp);

				strcpy(AdapterList[nAdapterIndex].szIPAddrStr, pAdapter->IpAddressList.IpAddress.String );// IP
				list.SetItemText(nAdapterIndex,1,AdapterList[nAdapterIndex].szIPAddrStr);

				formatMACToStr( AdapterList[nAdapterIndex].szHWAddrStr, pAdapter->Address ); // MAC
				list.SetItemText(nAdapterIndex,2,AdapterList[nAdapterIndex].szHWAddrStr);

				AdapterList[nAdapterIndex].dwIndex = pAdapter->Index;           // ���

				pAdapter = pAdapter->Next;

				nAdapterIndex ++;
			}
			delete pAdapterListBuffer;//�ͷŻ������ڴ�ռ�
		}
	}
}


void CSysInfo::AddARPInfoToList(CListCtrl& list,const short nAdapterIndex)// ����ϵͳ��ARP�����б�,�����ӵ��Ի�����
                                                                         //�õ���IpHelper api GetIpNetTable  �����õ���WinSock��api������Ҫ����<WinSock2.h>
{
	char tempChar;
	DWORD dwListSize = 1;
	DWORD dwRet;

	in_addr inaddr;

	list.DeleteAllItems();                                                       

	dwRet = GetIpNetTable((PMIB_IPNETTABLE)&tempChar, &dwListSize, TRUE);  // �ؼ�����   IP Helper API GetIpNetTable �����ܹ���ȡ�������ϵ�����ARP����
	if (dwRet == ERROR_INSUFFICIENT_BUFFER)
	{
		PMIB_IPNETTABLE pIpNetTable = (PMIB_IPNETTABLE)new(char[dwListSize]);//�����ڴ�ռ�
		dwRet = GetIpNetTable(pIpNetTable, &dwListSize, TRUE);
		if (dwRet == ERROR_SUCCESS)
		{
			for (int i=0; i<(int)pIpNetTable->dwNumEntries; i++) //dwNumEntriesΪPMIB_IPNETTABLE�ṹ�������С
			{
				inaddr.S_un.S_addr = pIpNetTable->table[i].dwAddr; //table[i]ΪPMIB_IPNETTABLE�ṹ�����鱾��
				strcpy( ARPList[i].szIPAddrStr, inet_ntoa(inaddr) );                 // IP
				formatMACToStr( ARPList[i].szHWAddrStr, pIpNetTable->table[i].bPhysAddr ); // MAC
				ARPList[i].dwType = pIpNetTable->table[i].dwType;         // Type

				if (AdapterList[nAdapterIndex].dwIndex != pIpNetTable->table[i].dwIndex)	
					continue;

				list.InsertItem(i,ARPList[i].szIPAddrStr);
				list.SetItemText(i,1, ARPList[i].szHWAddrStr);
				switch(ARPList[i].dwType) {                         // ����type��ֵ��ת�����ַ���ʾ
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

void CSysInfo::formatMACToStr(LPSTR lpHWAddrStr,const unsigned char *HWAddr)//���û������MAC��ַ�ַ�ת����Ӧ��ʽ

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
		if (i<5)	strcat(lpHWAddrStr, "-");         // ���� - 
	}
}
void CSysInfo::formatStrToMAC(const LPSTR lpHWAddrStr, unsigned char *HWAddr) //���û������MAC��ַ�ַ�ת�����ݰ��ṹ����Ҫ�ĸ�ʽ

{
	unsigned int i, index = 0, value, temp;
	unsigned char c;

	_strlwr(lpHWAddrStr);                            // ת����Сд

	for (i = 0; i < strlen(lpHWAddrStr); i++)
	{
		c = *(lpHWAddrStr + i);
		if (( c>='0' && c<='9' ) || ( c>='a' && c<='f' ))
		{
			if (c>='0' && c<='9')	temp = c - '0';             // ����
			if (c>='a' && c<='f')	temp = c - 'a' + 0xa;       // ��ĸ,0xa��ʾ��ĸ�Ŀ�ʼ
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

char* CSysInfo::GetCurAdapterMAC(const short nAdapterIndex) //��ȡ����MAC
{
	static char chTemp[12];
	int j=0;
     for(int i=0;i<18;i++)                     // ȥ��mac��ַ�м�ĺ��� 
	{
		if(AdapterList[nAdapterIndex].szHWAddrStr[i]!='-')
		{
	    	chTemp[j]=AdapterList[nAdapterIndex].szHWAddrStr[i];
			j++;
		}
	}
	return chTemp;
}