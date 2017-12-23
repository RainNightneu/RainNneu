/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;


import Business.Role.Role;
import Business.Role.SalesRole;
import java.util.ArrayList;

/**
 *
 * @author leopardyin
 */
public class SalesOrganization extends Organization{
  //  private static int id = 1;
    public SalesOrganization() {
        super(Type.Sales.getValue());
        
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new SalesRole());
        return roles;
    }
}
