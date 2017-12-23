// ArptestDlg.h : header file
//

#if !defined(AFX_ARPTESTDLG_H__146783D8_7591_4EE9_8E86_758E63DCCE44__INCLUDED_)
#define AFX_ARPTESTDLG_H__146783D8_7591_4EE9_8E86_758E63DCCE44__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

/////////////////////////////////////////////////////////////////////////////
// CArptestDlg dialog

class CArptestDlg : public CDialog
{
// Construction
public:
	CArptestDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CArptestDlg)
	enum { IDD = IDD_ARPTEST_DIALOG };
	CString	Temp;
	BOOL	m_check;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CArptestDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CArptestDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnChangeEditInterval();
	afx_msg void OnSetfocusArpDesmac();
	afx_msg void OnTimer(UINT nIDEvent);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	short nAdapterSelected;
public:
	virtual void OnOK();
	afx_msg void OnClickListNic(NMHDR* pNMHDR, LRESULT* pResult);
	afx_msg void OnBtnRefresh();
	afx_msg void OnCkInterval();
	afx_msg void OnRadioRequest();
	afx_msg void OnRadioReply();
	afx_msg void OnChangeArpSrcmac();
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_ARPTESTDLG_H__146783D8_7591_4EE9_8E86_758E63DCCE44__INCLUDED_)
