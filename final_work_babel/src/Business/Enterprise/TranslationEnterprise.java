/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;


import Business.Employee.Employee;
import Business.Role.Role;
import Business.Role.TransRole;
import Business.TransAbility.Ability;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.WorkRequest.Language;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author leopardyin
 */
public class TranslationEnterprise extends Enterprise{
    
    
    
     public TranslationEnterprise(String name){
        super(name, Enterprise.EnterpriseType.Translation);
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        return null;
    }
    
    public void loadTranslator(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Data_csv/"+this.getName()+"Translator.csv"));  
            String line = null; 
            while((line=reader.readLine())!=null){
               String Item[] = line.split(",");
               
               if(this.getEmployeeDirectory().checkIfExist(Item[0])==false){
                   
                   Employee e = this.getEmployeeDirectory().createEmployee(Item[0]);
                   UserAccount ua = this.getUserAccountDirectory().createUserAccount(Item[0], Item[0], e, new TransRole());
                   Ability a = ua.getAbilityDirectory().addAbility();
                   a.setSource(Item[1]);
                   a.setDestination(Item[2]);
                   a.setUnitPrice(Integer.parseInt(Item[3]));
               }else{
                   
                   UserAccount ua = this.getUserAccountDirectory().findUserAccountByName(Item[0]);
                   Ability a = ua.getAbilityDirectory().addAbility();
                   a.setSource(Item[1]);
                   a.setDestination(Item[2]);
                   a.setUnitPrice(Integer.parseInt(Item[3]));
               }
               
               System.out.println(this.getName());
            }
        } catch (Exception e) {    
            e.printStackTrace();    
        }
        
        
    }
    
}
