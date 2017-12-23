/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;

import java.util.ArrayList;

/**
 *
 * @author leopardyin
 */
public class EnterpriseDirectory {

    private ArrayList<Enterprise> enterpriseList;

    public EnterpriseDirectory() {
        enterpriseList = new ArrayList<>();
    }

    public ArrayList<Enterprise> getEnterpriseList() {
        return enterpriseList;
    }

    public Enterprise createAndAddEnterprise(String name, Enterprise.EnterpriseType type) {
        Enterprise enterprise = null;
        if (type == Enterprise.EnterpriseType.Localization) {
            enterprise = new LocalizationEnterprise(name);
            enterpriseList.add(enterprise);
        }
        if (type == Enterprise.EnterpriseType.Translation) {
            enterprise = new TranslationEnterprise(name);
            enterpriseList.add(enterprise);
        }
        return enterprise;
    }
    
    public TranslationEnterprise findTranslationCompanyByName(String name){
        TranslationEnterprise te = null;
        for(Enterprise e: enterpriseList){
            if(e.getClass().getSimpleName().equals("TranslationEnterprise") && e.getName().equals(name)){
                te = (TranslationEnterprise) e;
            }
        
        }
        
        
        return te;
    }
}
