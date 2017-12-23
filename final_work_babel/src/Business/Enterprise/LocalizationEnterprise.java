/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;


import Business.Employee.Employee;
import Business.Enterprise.Supplier.SupplierDirectory;
import Business.Enterprise.Supplier.SupplierInfo;
import Business.Network.Network;
import Business.Role.Role;
import Business.Role.TransRole;
import Business.TransAbility.Ability;
import Business.UserAccount.UserAccount;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author leopardyin
 */
public class LocalizationEnterprise extends Enterprise{
    
    
    SupplierDirectory supplierDirectory;
    
    public LocalizationEnterprise(String name){
        super(name, Enterprise.EnterpriseType.Localization);
        
        supplierDirectory = new SupplierDirectory();
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        return null;
    }

    public SupplierDirectory getSupplierDirectory() {
        return supplierDirectory;
    }

    public void setSupplierDirectory(SupplierDirectory supplierDirectory) {
        this.supplierDirectory = supplierDirectory;
    }
    
    
    
    
  public void loadSupplierInfo(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Data_csv/"+this.getName()+"Supplier.csv"));  
            String line = null; 
            while((line=reader.readLine())!=null){
               String Item[] = line.split(",");
               
               SupplierInfo si = this.getSupplierDirectory().createSupplierInfo();
               si.setTranslationEnterprise(Item[0]);
               si.setNewsRate(Double.parseDouble(Item[1]));
               si.setEssayRate(Double.parseDouble(Item[2]));
               si.setMovieRate(Double.parseDouble(Item[3]));
               si.setAdvertisementRate(Double.parseDouble(Item[4]));
               si.setGameRate(Double.parseDouble(Item[5]));
              
               System.out.println(this.getName());
            }
        } catch (Exception e) {    
            e.printStackTrace();    
        }
        
        
    }
    
    
    
}
