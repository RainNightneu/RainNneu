// ArptestDlg.cpp : implementation file
//

#include "stdafx.h"
#include "Arptest.h"
#include "ArptestDlg.h"
#include "SysInfo.h"
#include "Sender.h"
#define MAX_LEN 128
#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CAboutDlg dialog used for App About

class CAboutDlg : public CDialog
{
public:
	CAboutDlg();

// Dialog Data
	//{{AFX_DATA(CAboutDlg)
	enum { IDD = IDD_ABOUTBOX };
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CAboutDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation(实现）
protected:
	//{{AFX_MSG(CAboutDlg)
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialog(CAboutDlg::IDD)
{
	//{{AFX_DATA_INIT(CAboutDlg)
	//}}AFX_DATA_INIT
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CAboutDlg)
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialog)
	//{{AFX_MSG_MAP(CAboutDlg)
		// No message handlers
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CArptestDlg dialog

CArptestDlg::CArptestDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CArptestDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CArptestDlg)
	Temp = _T("");
	m_check = FALSE;
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CArptestDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CArptestDlg)
	DDX_Text(pDX, IDC_ARP_DESMAC, Temp);
	DDX_Check(pDX, IDC_CK_INTERVAL, m_check);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CArptestDlg, CDialog)
	//{{AFX_MSG_MAP(CArptestDlg)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_EN_CHANGE(IDC_EDIT_INTERVAL, OnChangeEditInterval)
	ON_EN_SETFOCUS(IDC_ARP_DESMAC, OnSetfocusArpDesmac)
	ON_NOTIFY(NM_CLICK, IDC_LIST_NIC, OnClickListNic)
	ON_BN_CLICKED(IDC_BTN_REFRESH, OnBtnRefresh)
	ON_BN_CLICKED(IDC_CK_INTERVAL, OnCkInterval)
	ON_BN_CLICKED(IDC_RADIO_REQUEST, OnRadioRequest)
	ON_BN_CLICKED(IDC_RADIO_REPLY, OnRadioReply)
	ON_EN_CHANGE(IDC_ARP_SRCMAC, OnChangeArpSrcmac)
	ON_WM_TIMER()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CArptestDlg message handlers（消息处理程序）

BOOL CArptestDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Add "About..." menu item to system menu.

	// IDM_ABOUTBOX must be in the system command range.
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		CString strAboutMenu;
		strAboutMenu.LoadString(IDS_ABOUTBOX);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon
	
	// TODO: Add extra initialization here
	nAdapterSelected = 0;

	CListCtrl* list=(CListCtrl*)GetDlgItem(IDC_LIST_NIC);      // 初始化网络列表的list样式

	list->ModifyStyle(NULL,LVS_REPORT);
	list->SetExtendedStyle(LVS_EX_FULLROWSELECT|LVS_EX_ONECLICKACTIVATE|LVS_EX_GRIDLINES);

	list->InsertColumn(0,"网卡名称");         // 每个字段内容
	list->InsertColumn(1,"IP");
	list->InsertColumn(2,"MAC");

	list->SetColumnWidth(0,335);          // 每个字段宽度
	list->SetColumnWidth(1,105);
	list->SetColumnWidth(2,128);

	CSysInfo::AddAdapInfoToList(*list);

	list=(CListCtrl*)GetDlgItem(IDC_LIST_ARP);      // 初始化ARP列表样式

	list->ModifyStyle(NULL,LVS_REPORT);
	list->SetExtendedStyle(LVS_EX_FULLROWSELECT|LVS_EX_ONECLICKACTIVATE|LVS_EX_GRIDLINES);

	list->InsertColumn(0,"IP");                                   // 每个字段内容
	list->InsertColumn(1,"MAC");
	list->InsertColumn(2,"TYPE");

	list->SetColumnWidth(0,200);                                  // 每个字段宽度
	list->SetColumnWidth(1,231);
	list->SetColumnWidth(2,137);

	CSysInfo::AddARPInfoToList(*list,nAdapterSelected);           // 初始化ARP列表

	GetDlgItem(IDC_EDIT_INTERVAL)->SetWindowText("5");            // 设置默认的发送间隔

	CButton* pButton=(CButton*)GetDlgItem(IDC_RADIO_REQUEST);     // 默认为请求包
	pButton->SetCheck(TRUE);
	SetDlgItemText(IDC_DLC_DESMAC,"ff-ff-ff-ff-ff-ff");  // 默认DLC接收端为广播
    SetDlgItemText(IDC_ARP_DESMAC,"ff-ff-ff-ff-ff-ff");
	SetDlgItemText(IDC_DLC_SRCMAC,CSysInfo::GetCurAdapterMAC(nAdapterSelected));   // 初始化各个位置的值
	SetDlgItemText(IDC_ARP_SRCMAC,CSysInfo::GetCurAdapterMAC(nAdapterSelected)); 
	SetDlgItemText(IDC_ARP_SRCIP,CSysInfo::GetCurAdapterIP(nAdapterSelected)); 
	SetDlgItemText(IDC_TIP_DESIP,"目的IP地址:"); 
	SetDlgItemText(IDC_TIP_DESMAC,"目的MAC地址:"); 

	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CArptestDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialog::OnSysCommand(nID, lParam);
	}
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CArptestDlg::OnPaint() 
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, (WPARAM) dc.GetSafeHdc(), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

// The system calls this to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CArptestDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

//发送
void CArptestDlg::OnOK() 
{
	// TODO: Add extra validation here
	KillTimer(1);                                                 // 清除上次Timer

	char TempSrcDLC[MAX_LEN]={0},TempDesDLC[MAX_LEN]={0};

	GetDlgItemText(IDC_DLC_SRCMAC,TempSrcDLC,MAX_LEN);            // 获得用户输入的DLC帧头的地址信息
	GetDlgItemText(IDC_DLC_DESMAC,TempDesDLC,MAX_LEN);	

	if(strcmp(TempSrcDLC,"")==0 || strcmp(TempDesDLC,"")==0)
	{
		AfxMessageBox("请输入完整的DLC帧头信息！");
		return;
	}


	char TempSrcMAC[MAX_LEN]={0},TempSrcIP[MAX_LEN]={0};      
	char TempDesMAC[MAX_LEN]={0},TempDesIP[MAX_LEN]={0};
	int TempARPType=2;

	GetDlgItemText(IDC_ARP_SRCMAC,TempSrcMAC,MAX_LEN);            // 获得用户输入的ARP帧的地址信息
	GetDlgItemText(IDC_ARP_SRCIP,TempSrcIP,MAX_LEN);
	GetDlgItemText(IDC_ARP_DESMAC,TempDesMAC,MAX_LEN);
	GetDlgItemText(IDC_ARP_DESIP,TempDesIP,MAX_LEN);

	if(strcmp(TempSrcMAC,"")==0 || strcmp(TempSrcIP,"")==0 ||
		strcmp(TempDesMAC,"")==0 || strcmp(TempDesIP,"")==0)
	{
		AfxMessageBox("请输入完整的ARP帧信息！");
		return;
	}

	CButton* pButton=(CButton*)GetDlgItem(IDC_RADIO_REQUEST);  // 如果选中"请求包"，则
    if((pButton->GetState() & 0x0003)==1)                      // ARPType为 1
		TempARPType = 1;

	pButton=(CButton*)GetDlgItem(IDC_CK_INTERVAL);             // 如果选中每隔一段时间发送一次则
	if((pButton->GetState() & 0x0003)==1)                      // 启动Timer
	{
		int nInterval = GetDlgItemInt(IDC_EDIT_INTERVAL);
		if(nInterval<0)
		{
			AfxMessageBox("请输入正整数！");
			return;
		}
		CSender::formatARPPacket(TempSrcDLC,TempDesDLC,TempSrcMAC,TempSrcIP,TempDesMAC,TempDesIP,TempARPType);
//		CSender::SendARPPacket(nAdapterSelected);
		SetTimer(1,nInterval,NULL);
		AfxMessageBox("发送数据包成功！");
		return;
	}
		

	CSender::formatARPPacket(TempSrcDLC,TempDesDLC,TempSrcMAC,TempSrcIP,TempDesMAC,TempDesIP,TempARPType);   // 根据用户输入信息格式化数据包
 
	CSender::SendARPPacket(nAdapterSelected);     // 发送数据包
	AfxMessageBox("发送数据包成功！");
	//CDialog::OnOK();
}

// 用户单击网卡列表的某一项，表示选择网卡
void CArptestDlg::OnClickListNic(NMHDR* pNMHDR, LRESULT* pResult) 
{
	// TODO: Add your control notification handler code here
	NM_LISTVIEW* pNMListView = (NM_LISTVIEW*)pNMHDR;

	nAdapterSelected =(short)pNMListView->iItem;          // 获得选中的网卡项
	*pResult = 0;
}
// 刷新arp列表
void CArptestDlg::OnBtnRefresh() 
{
	// TODO: Add your control notification handler code here
	CListCtrl* list=(CListCtrl*)GetDlgItem(IDC_LIST_ARP); 

	CSysInfo::AddARPInfoToList(*list,nAdapterSelected);
}

void CArptestDlg::OnCkInterval() 
{
	// TODO: Add your control notification handler code here
	
}
// 定时发送
void CArptestDlg::OnTimer(UINT nIDEvent)
{
	CSender::SendARPPacket(nAdapterSelected);

	CDialog::OnTimer(nIDEvent);
}

// 点击请求包的Radio按钮
void CArptestDlg::OnRadioRequest() 
{
	// TODO: Add your control notification handler code here
	SetDlgItemText(IDC_DLC_DESMAC,"ff-ff-ff-ff-ff-ff"); 
	SetDlgItemText(IDC_ARP_DESMAC,"ff-ff-ff-ff-ff-ff");
	SetDlgItemText(IDC_TIP_SRCIP,"源IP地址：");
	SetDlgItemText(IDC_TIP_SRCMAC,"源MAC地址:");
      
	SetDlgItemText(IDC_TIP_DESIP,"目的IP地址：");
	SetDlgItemText(IDC_TIP_DESMAC,"目的MAC地址：");
}

void CArptestDlg::OnRadioReply() 
{
	// TODO: Add your control notification handler code here
	SetDlgItemText(IDC_DLC_DESMAC,"");
	SetDlgItemText(IDC_ARP_DESMAC,"");
	SetDlgItemText(IDC_TIP_SRCIP,"回复方IP地址：");
	SetDlgItemText(IDC_TIP_SRCMAC,"回复方MAC地址：");
    
	SetDlgItemText(IDC_TIP_DESIP,"接收方IP地址：");
	SetDlgItemText(IDC_TIP_DESMAC,"接收方MAC地址：");
}
void CArptestDlg::OnChangeArpSrcmac() 
{
  
/*	CString Temp;
	GetDlgItemText(IDC_DLC_SRCMAC,Temp);
	SetDlgItemText(IDC_ARP_SRCMAC,Temp);
	return;*/
}

void CArptestDlg::OnChangeEditInterval() 
{
	// TODO: If this is a RICHEDIT control, the control will not
	// send this notification unless you override the CDialog::OnInitDialog()
	// function and call CRichEditCtrl().SetEventMask()
	// with the ENM_CHANGE flag ORed into the mask.
	
	// TODO: Add your control notification handler code here
	
}

void CArptestDlg::OnSetfocusArpDesmac() 
{
	// TODO: Add your control notification handler code here
	SetDlgItemText(IDC_ARP_DESMAC,"");	
}
