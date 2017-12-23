/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Business.Employee.Employee;
import Business.Enterprise.Enterprise;
import Business.Enterprise.LocalizationEnterprise;
import Business.Enterprise.Supplier.SupplierInfo;
import Business.Enterprise.TranslationEnterprise;
import Business.Network.Network;
import Business.Role.AdminRole;
import Business.Role.LocalPMRole;
import Business.Role.LocalSalesRole;
import Business.Role.PMRole;


import Business.Role.SalesRole;
import Business.Role.TransRole;
import Business.TransAbility.Ability;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.WorkRequest.Language;


/**
 *
 * @author leopardyin
 */
public class ConfigureASystem {

    public static EcoSystem configure() {

        
        EcoSystem system = EcoSystem.getInstance();
//新建一个Network      
        Network us = system.addNetwork("America");
        Network uk = system.addNetwork("United Kingdom");
        TranslationEnterprise trans = (TranslationEnterprise) uk.getEnterpriseDirectory().createAndAddEnterprise("Yeehe", Enterprise.EnterpriseType.Translation);
        
        Employee e = trans.getEmployeeDirectory().createEmployee("Jack");
        UserAccount sb = trans.getUserAccountDirectory().createUserAccount("sb", "sb", e , new SalesRole());

        
  //一家翻译公司    
        TranslationEnterprise trans1 = (TranslationEnterprise) us.getEnterpriseDirectory().createAndAddEnterprise("Yeehe", Enterprise.EnterpriseType.Translation);
        Employee at1 = trans1.getEmployeeDirectory().createEmployee("Jack");
        UserAccount ta1 = trans1.getUserAccountDirectory().createUserAccount("ta1", "ta1", at1 , new AdminRole());
        Employee e1 = trans1.getEmployeeDirectory().createEmployee("Jack");
        UserAccount jack = trans1.getUserAccountDirectory().createUserAccount("s1", "s1", e1 , new SalesRole());
      
        Employee e1p1 = trans1.getEmployeeDirectory().createEmployee("Tom");
        UserAccount Tom = trans1.getUserAccountDirectory().createUserAccount("p1", "p1", e1p1, new PMRole());

        Employee e1p2 = trans1.getEmployeeDirectory().createEmployee("Noah");
        UserAccount Noah = trans1.getUserAccountDirectory().createUserAccount("p2", "p2", e1p2, new PMRole());
        trans1.loadTranslator();
        
        TranslationEnterprise trans2 = (TranslationEnterprise) us.getEnterpriseDirectory().createAndAddEnterprise("SimulTrans", Enterprise.EnterpriseType.Translation);
        trans2.loadTranslator();
        Employee at2 = trans2.getEmployeeDirectory().createEmployee("Jack");
        UserAccount ta2 = trans2.getUserAccountDirectory().createUserAccount("ta2", "ta2", at2 , new AdminRole());
        
        TranslationEnterprise trans3 = (TranslationEnterprise) us.getEnterpriseDirectory().createAndAddEnterprise("Renova", Enterprise.EnterpriseType.Translation);
        trans3.loadTranslator();
        Employee at3 = trans3.getEmployeeDirectory().createEmployee("Jack");
        UserAccount ta3 = trans3.getUserAccountDirectory().createUserAccount("ta3", "ta3", at3 , new AdminRole());
        
        TranslationEnterprise trans4 = (TranslationEnterprise) us.getEnterpriseDirectory().createAndAddEnterprise("McElroy", Enterprise.EnterpriseType.Translation);
        trans4.loadTranslator();
        Employee at4 = trans4.getEmployeeDirectory().createEmployee("Jack");
        UserAccount ta4 = trans4.getUserAccountDirectory().createUserAccount("ta4", "ta4", at4 , new AdminRole());
        
        TranslationEnterprise trans5 = (TranslationEnterprise) us.getEnterpriseDirectory().createAndAddEnterprise("TransPerfect", Enterprise.EnterpriseType.Translation);
        trans5.loadTranslator();
        Employee at5 = trans5.getEmployeeDirectory().createEmployee("Jack");
        UserAccount ta5 = trans5.getUserAccountDirectory().createUserAccount("ta5", "ta5", at5 , new AdminRole());
        
/*
.
Mason.
Jacob.
William.
Ethan.
James.
Alexander.
*/       
      /*  Employee e3 = trans1.getEmployeeDirectory().createEmployee("Tommy");
        UserAccount Tommy = trans1.getUserAccountDirectory().createUserAccount("t1", "t1", e3, new TransRole());
        
        
        Ability a1 = Tommy.getAbilityDirectory().addAbility();
        a1.setSource(Language.EN.name());
        a1.setDestination(Language.CN.name());
        a1.setUnitPrice(25);
        Ability a2 = Tommy.getAbilityDirectory().addAbility();
        a2.setSource(Language.CN.name());
        a2.setDestination(Language.EN.name());
               
*/
        LocalizationEnterprise local1 = (LocalizationEnterprise) us.getEnterpriseDirectory().createAndAddEnterprise("WeLocalize", Enterprise.EnterpriseType.Localization);
        Employee al1 = local1.getEmployeeDirectory().createEmployee("Jack");       
        local1.getUserAccountDirectory().createUserAccount("la1", "la1", al1 , new AdminRole());
        
        Employee Lucky = local1.getEmployeeDirectory().createEmployee("Lucky");
        local1.getUserAccountDirectory().createUserAccount("ls1", "ls1", Lucky, new LocalSalesRole());
         
        Employee local_p1 = local1.getEmployeeDirectory().createEmployee("Jason");
        local1.getUserAccountDirectory().createUserAccount("lp1", "lp1", local_p1, new LocalPMRole());
         
        Employee local_p2 = local1.getEmployeeDirectory().createEmployee("Liam");
        local1.getUserAccountDirectory().createUserAccount("lp2", "lp2", local_p2, new LocalPMRole());

        local1.loadSupplierInfo();
for(SupplierInfo si: local1.getSupplierDirectory().getSupplierList()){
    System.out.println(si.getNewsRate()+"    " + si.getEssayRate()+ "    "+ si.getMovieRate()+ "     " + si.getAdvertisementRate()+"      "+ si.getNewsRate());
}

        
        
        LocalizationEnterprise local2 = (LocalizationEnterprise) us.getEnterpriseDirectory().createAndAddEnterprise("LionBridge", Enterprise.EnterpriseType.Localization);
        local2.loadSupplierInfo();

        Employee al2 = local2.getEmployeeDirectory().createEmployee("Jack");       
        local2.getUserAccountDirectory().createUserAccount("la2", "la2", al2 , new AdminRole());

        LocalizationEnterprise local3 = (LocalizationEnterprise) us.getEnterpriseDirectory().createAndAddEnterprise("Keywords", Enterprise.EnterpriseType.Localization);
        local3.loadSupplierInfo();
        
        Employee al3 = local3.getEmployeeDirectory().createEmployee("Jack");       
        local3.getUserAccountDirectory().createUserAccount("la3", "la3", al3 , new AdminRole());
     /*   
        Employee employee = system.getEmployeeDirectory().createEmployee("Leo");
        UserAccount ua = system.getUserAccountDirectory().createUserAccount("sa", "sa", employee, new SystemAdminRole());
    */
     
        return system;
    }
}
