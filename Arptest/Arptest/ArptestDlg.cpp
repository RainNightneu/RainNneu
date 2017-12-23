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

// Implementation(ʵ�֣�
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
// CArptestDlg message handlers����Ϣ�������

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

	CListCtrl* list=(CListCtrl*)GetDlgItem(IDC_LIST_NIC);      // ��ʼ�������б��list��ʽ

	list->ModifyStyle(NULL,LVS_REPORT);
	list->SetExtendedStyle(LVS_EX_FULLROWSELECT|LVS_EX_ONECLICKACTIVATE|LVS_EX_GRIDLINES);

	list->InsertColumn(0,"��������");         // ÿ���ֶ�����
	list->InsertColumn(1,"IP");
	list->InsertColumn(2,"MAC");

	list->SetColumnWidth(0,335);          // ÿ���ֶο��
	list->SetColumnWidth(1,105);
	list->SetColumnWidth(2,128);

	CSysInfo::AddAdapInfoToList(*list);

	list=(CListCtrl*)GetDlgItem(IDC_LIST_ARP);      // ��ʼ��ARP�б���ʽ

	list->ModifyStyle(NULL,LVS_REPORT);
	list->SetExtendedStyle(LVS_EX_FULLROWSELECT|LVS_EX_ONECLICKACTIVATE|LVS_EX_GRIDLINES);

	list->InsertColumn(0,"IP");                                   // ÿ���ֶ�����
	list->InsertColumn(1,"MAC");
	list->InsertColumn(2,"TYPE");

	list->SetColumnWidth(0,200);                                  // ÿ���ֶο��
	list->SetColumnWidth(1,231);
	list->SetColumnWidth(2,137);

	CSysInfo::AddARPInfoToList(*list,nAdapterSelected);           // ��ʼ��ARP�б�

	GetDlgItem(IDC_EDIT_INTERVAL)->SetWindowText("5");            // ����Ĭ�ϵķ��ͼ��

	CButton* pButton=(CButton*)GetDlgItem(IDC_RADIO_REQUEST);     // Ĭ��Ϊ�����
	pButton->SetCheck(TRUE);
	SetDlgItemText(IDC_DLC_DESMAC,"ff-ff-ff-ff-ff-ff");  // Ĭ��DLC���ն�Ϊ�㲥
    SetDlgItemText(IDC_ARP_DESMAC,"ff-ff-ff-ff-ff-ff");
	SetDlgItemText(IDC_DLC_SRCMAC,CSysInfo::GetCurAdapterMAC(nAdapterSelected));   // ��ʼ������λ�õ�ֵ
	SetDlgItemText(IDC_ARP_SRCMAC,CSysInfo::GetCurAdapterMAC(nAdapterSelected)); 
	SetDlgItemText(IDC_ARP_SRCIP,CSysInfo::GetCurAdapterIP(nAdapterSelected)); 
	SetDlgItemText(IDC_TIP_DESIP,"Ŀ��IP��ַ:"); 
	SetDlgItemText(IDC_TIP_DESMAC,"Ŀ��MAC��ַ:"); 

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

//����
void CArptestDlg::OnOK() 
{
	// TODO: Add extra validation here
	KillTimer(1);                                                 // ����ϴ�Timer

	char TempSrcDLC[MAX_LEN]={0},TempDesDLC[MAX_LEN]={0};

	GetDlgItemText(IDC_DLC_SRCMAC,TempSrcDLC,MAX_LEN);            // ����û������DLC֡ͷ�ĵ�ַ��Ϣ
	GetDlgItemText(IDC_DLC_DESMAC,TempDesDLC,MAX_LEN);	

	if(strcmp(TempSrcDLC,"")==0 || strcmp(TempDesDLC,"")==0)
	{
		AfxMessageBox("������������DLC֡ͷ��Ϣ��");
		return;
	}


	char TempSrcMAC[MAX_LEN]={0},TempSrcIP[MAX_LEN]={0};      
	char TempDesMAC[MAX_LEN]={0},TempDesIP[MAX_LEN]={0};
	int TempARPType=2;

	GetDlgItemText(IDC_ARP_SRCMAC,TempSrcMAC,MAX_LEN);            // ����û������ARP֡�ĵ�ַ��Ϣ
	GetDlgItemText(IDC_ARP_SRCIP,TempSrcIP,MAX_LEN);
	GetDlgItemText(IDC_ARP_DESMAC,TempDesMAC,MAX_LEN);
	GetDlgItemText(IDC_ARP_DESIP,TempDesIP,MAX_LEN);

	if(strcmp(TempSrcMAC,"")==0 || strcmp(TempSrcIP,"")==0 ||
		strcmp(TempDesMAC,"")==0 || strcmp(TempDesIP,"")==0)
	{
		AfxMessageBox("������������ARP֡��Ϣ��");
		return;
	}

	CButton* pButton=(CButton*)GetDlgItem(IDC_RADIO_REQUEST);  // ���ѡ��"�����"����
    if((pButton->GetState() & 0x0003)==1)                      // ARPTypeΪ 1
		TempARPType = 1;

	pButton=(CButton*)GetDlgItem(IDC_CK_INTERVAL);             // ���ѡ��ÿ��һ��ʱ�䷢��һ����
	if((pButton->GetState() & 0x0003)==1)                      // ����Timer
	{
		int nInterval = GetDlgItemInt(IDC_EDIT_INTERVAL);
		if(nInterval<0)
		{
			AfxMessageBox("��������������");
			return;
		}
		CSender::formatARPPacket(TempSrcDLC,TempDesDLC,TempSrcMAC,TempSrcIP,TempDesMAC,TempDesIP,TempARPType);
//		CSender::SendARPPacket(nAdapterSelected);
		SetTimer(1,nInterval,NULL);
		AfxMessageBox("�������ݰ��ɹ���");
		return;
	}
		

	CSender::formatARPPacket(TempSrcDLC,TempDesDLC,TempSrcMAC,TempSrcIP,TempDesMAC,TempDesIP,TempARPType);   // �����û�������Ϣ��ʽ�����ݰ�
 
	CSender::SendARPPacket(nAdapterSelected);     // �������ݰ�
	AfxMessageBox("�������ݰ��ɹ���");
	//CDialog::OnOK();
}

// �û����������б��ĳһ���ʾѡ������
void CArptestDlg::OnClickListNic(NMHDR* pNMHDR, LRESULT* pResult) 
{
	// TODO: Add your control notification handler code here
	NM_LISTVIEW* pNMListView = (NM_LISTVIEW*)pNMHDR;

	nAdapterSelected =(short)pNMListView->iItem;          // ���ѡ�е�������
	*pResult = 0;
}
// ˢ��arp�б�
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
// ��ʱ����
void CArptestDlg::OnTimer(UINT nIDEvent)
{
	CSender::SendARPPacket(nAdapterSelected);

	CDialog::OnTimer(nIDEvent);
}

// ����������Radio��ť
void CArptestDlg::OnRadioRequest() 
{
	// TODO: Add your control notification handler code here
	SetDlgItemText(IDC_DLC_DESMAC,"ff-ff-ff-ff-ff-ff"); 
	SetDlgItemText(IDC_ARP_DESMAC,"ff-ff-ff-ff-ff-ff");
	SetDlgItemText(IDC_TIP_SRCIP,"ԴIP��ַ��");
	SetDlgItemText(IDC_TIP_SRCMAC,"ԴMAC��ַ:");
      
	SetDlgItemText(IDC_TIP_DESIP,"Ŀ��IP��ַ��");
	SetDlgItemText(IDC_TIP_DESMAC,"Ŀ��MAC��ַ��");
}

void CArptestDlg::OnRadioReply() 
{
	// TODO: Add your control notification handler code here
	SetDlgItemText(IDC_DLC_DESMAC,"");
	SetDlgItemText(IDC_ARP_DESMAC,"");
	SetDlgItemText(IDC_TIP_SRCIP,"�ظ���IP��ַ��");
	SetDlgItemText(IDC_TIP_SRCMAC,"�ظ���MAC��ַ��");
    
	SetDlgItemText(IDC_TIP_DESIP,"���շ�IP��ַ��");
	SetDlgItemText(IDC_TIP_DESMAC,"���շ�MAC��ַ��");
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
