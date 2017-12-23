/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.UserAccount;

import Business.Employee.Employee;
import Business.Role.Role;
import Business.TransAbility.AbilityDirectory;



/**
 *
 * @author leopardyin
 */
public class UserAccount {
    
    private static int count = 100;
    private int accountID;
    private String username;
    private String password;
    private Employee employee;
    private Role role;
    private double time;

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
    private AbilityDirectory abilityDirectory;
    
    
    public UserAccount(){
        accountID = count;
        count++;
        abilityDirectory = new AbilityDirectory();
    }
    
    public AbilityDirectory getAbilityDirectory() {
        return abilityDirectory;
    }

    public void setAbilityDirectory(AbilityDirectory abilityDirectory) {
        this.abilityDirectory = abilityDirectory;
    }
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }
 
    @Override
    public String toString() {
        return String.valueOf(accountID);
    }
}
