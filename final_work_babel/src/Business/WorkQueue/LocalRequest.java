/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.Enterprise.LocalizationEnterprise;
import Business.Enterprise.TranslationEnterprise;
import Business.UserAccount.UserAccount;

/**
 *
 * @author Yin
 */
public class LocalRequest extends WorkRequest {
    
    public LocalRequest(){
        super();
        this.setStatus(workStatus.LocalizeReceived.name());
    }
    
    LocalizationEnterprise localizationCompany;
    
    String TransEnterprise; //储存接收稿件的公司
    
    private double LocalizeUnitPrice;
    private UserAccount LocalSR;
    private UserAccount LocalPM;

    public LocalizationEnterprise getLocalizationCompany() {
        return localizationCompany;
    }

    public void setLocalizationCompany(LocalizationEnterprise localizationCompany) {
        this.localizationCompany = localizationCompany;
    }

    public String getTransEnterprise() {
        return TransEnterprise;
    }

    public void setTransEnterprise(String TransEnterprise) {
        this.TransEnterprise = TransEnterprise;
    }

    public double getLocalizeUnitPrice() {
        return LocalizeUnitPrice;
    }

    public void setLocalizeUnitPrice(double LocalizeUnitPrice) {
        this.LocalizeUnitPrice = LocalizeUnitPrice;
    }

    public UserAccount getLocalSR() {
        return LocalSR;
    }

    public void setLocalSR(UserAccount LocalSR) {
        this.LocalSR = LocalSR;
    }

    public UserAccount getLocalPM() {
        return LocalPM;
    }

    public void setLocalPM(UserAccount LocalPM) {
        this.LocalPM = LocalPM;
    }
    
   
    
    
    
    
}
