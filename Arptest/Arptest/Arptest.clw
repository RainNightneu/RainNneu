; CLW file contains information for the MFC ClassWizard

[General Info]
Version=1
LastClass=CArptestDlg
LastTemplate=CDialog
NewFileInclude1=#include "stdafx.h"
NewFileInclude2=#include "Arptest.h"

ClassCount=5
Class1=CArptestApp
Class2=CArptestDlg
Class3=CAboutDlg

ResourceCount=3
Resource1=IDD_ABOUTBOX
Resource2=IDR_MAINFRAME
Class4=CSysInfo
Class5=CSender
Resource3=IDD_ARPTEST_DIALOG

[CLS:CArptestApp]
Type=0
HeaderFile=Arptest.h
ImplementationFile=Arptest.cpp
Filter=N

[CLS:CArptestDlg]
Type=0
HeaderFile=ArptestDlg.h
ImplementationFile=ArptestDlg.cpp
Filter=D
LastObject=IDC_LIST_NIC
BaseClass=CDialog
VirtualFilter=dWC

[CLS:CAboutDlg]
Type=0
HeaderFile=ArptestDlg.h
ImplementationFile=ArptestDlg.cpp
Filter=D

[DLG:IDD_ABOUTBOX]
Type=1
Class=CAboutDlg
ControlCount=4
Control1=IDC_STATIC,static,1342177283
Control2=IDC_STATIC,static,1342308480
Control3=IDC_STATIC,static,1342308352
Control4=IDOK,button,1342373889

[DLG:IDD_ARPTEST_DIALOG]
Type=1
Class=CArptestDlg
ControlCount=27
Control1=IDOK,button,1342242817
Control2=IDCANCEL,button,1342242816
Control3=IDC_STATIC,button,1342177287
Control4=IDC_STATIC,static,1342308352
Control5=IDC_DLC_SRCMAC,edit,1350631552
Control6=IDC_STATIC,static,1342308352
Control7=IDC_DLC_DESMAC,edit,1350631552
Control8=IDC_STATIC,button,1342177287
Control9=IDC_STATIC,static,1342308352
Control10=IDC_RADIO_REQUEST,button,1342177289
Control11=IDC_RADIO_REPLY,button,1342177289
Control12=IDC_TIP_SRCIP,static,1342308352
Control13=IDC_TIP_SRCMAC,static,1342308352
Control14=IDC_ARP_SRCMAC,edit,1350631552
Control15=IDC_TIP_DESIP,static,1342308352
Control16=IDC_TIP_DESMAC,static,1342308352
Control17=IDC_ARP_DESMAC,edit,1350631552
Control18=IDC_STATIC,static,1342308352
Control19=IDC_LIST_NIC,SysListView32,1350631425
Control20=IDC_STATIC,static,1342308352
Control21=IDC_LIST_ARP,SysListView32,1350631425
Control22=IDC_BTN_REFRESH,button,1342242816
Control23=IDC_CK_INTERVAL,button,1342242819
Control24=IDC_EDIT_INTERVAL,edit,1350631552
Control25=IDC_STATIC,static,1342308352
Control26=IDC_ARP_SRCIP,SysIPAddress32,1342242816
Control27=IDC_ARP_DESIP,SysIPAddress32,1342242816

[CLS:CSysInfo]
Type=0
HeaderFile=SysInfo.h
ImplementationFile=SysInfo.cpp
BaseClass=CDialog
Filter=D

[CLS:CSender]
Type=0
HeaderFile=Sender.h
ImplementationFile=Sender.cpp
BaseClass=CDialog
Filter=D

