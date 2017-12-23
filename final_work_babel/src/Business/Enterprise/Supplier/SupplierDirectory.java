/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise.Supplier;

import Business.TransAbility.Ability;
import Business.UserAccount.UserAccount;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Yin
 */
public class SupplierDirectory {
    
    ArrayList<SupplierInfo> supplierList;
    
    public SupplierDirectory(){
        supplierList = new ArrayList(); 
    }

    public ArrayList<SupplierInfo> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(ArrayList<SupplierInfo> supplierList) {
        this.supplierList = supplierList;
    }
    
    
    public SupplierInfo createSupplierInfo(){
        SupplierInfo si = new SupplierInfo();
        supplierList.add(si);
        return si;
    }
      
    public SupplierInfo findInfoByTranslationName(String name){
        SupplierInfo supplierInfo = null;
        for(SupplierInfo si: supplierList){
            if(si.getTranslationEnterprise().equals(name))
                supplierInfo = si;
        }
        return supplierInfo;
        
    }
      
      
}
