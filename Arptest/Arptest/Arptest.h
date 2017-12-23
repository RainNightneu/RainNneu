// Arptest.h : main header file for the ARPTEST application
//

#if !defined(AFX_ARPTEST_H__01C3FADB_FBCD_4066_A1CB_D17BDC7E50A9__INCLUDED_)
#define AFX_ARPTEST_H__01C3FADB_FBCD_4066_A1CB_D17BDC7E50A9__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"		// main symbols

/////////////////////////////////////////////////////////////////////////////
// CArptestApp:
// See Arptest.cpp for the implementation of this class
//

class CArptestApp : public CWinApp
{
public:
	CArptestApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CArptestApp)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation

	//{{AFX_MSG(CArptestApp)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_ARPTEST_H__01C3FADB_FBCD_4066_A1CB_D17BDC7E50A9__INCLUDED_)
