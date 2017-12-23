/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Employee.EmployeeDirectory;
import Business.Role.Role;
import Business.UserAccount.UserAccountDirectory;
import Business.WorkQueue.WorkQueue;
import java.util.ArrayList;

/**
 *
 * @author Yin
 */
public abstract class Organization {
    private String name;
    private EmployeeDirectory employeeDirectory;
    private UserAccountDirectory userAccountDirectory;
    private int organizationID;
    private static int counter = 1000;
    private WorkQueue workQueue;

    public String getName() {
        return name;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void setCounter(int counter) {
        Organization.counter = counter;
    }
    
    
    
    public enum Type{

// 翻译公司四种角色；       
    Sales("Sales Department"),
    Project("Project Department"),
    Translator("Translation Department"),
    Proofread("Proofread Department"),
    
// 本地化公司两种角色
    LocalSales("Localization Sales Department"),
    LocalProject("Localization Project Department");
    
// 
    private String value;
    private Type(String value){
        this.value = value;
    }
    
        public String getValue() {
            return value;
        }

}
    public Organization(String name){
        this.name = name;
        employeeDirectory = new EmployeeDirectory();
        userAccountDirectory = new UserAccountDirectory();
        workQueue = new WorkQueue();
        organizationID = counter;
        ++counter;
    }
             
    public abstract ArrayList<Role> getSupportedRole();

    public EmployeeDirectory getEmployeeDirectory() {
        return employeeDirectory;
    }

    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }

    public WorkQueue getWorkQueue() {
        return workQueue;
    }

   
    
    
    
    
}
