/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;


import Business.Role.Role;
import Business.Role.SalesRole;
import Business.Role.TransRole;
import java.util.ArrayList;

/**
 *
 * @author leopardyin
 */
public class TransOrganization extends Organization{
  //  private static int id = 1;
    public TransOrganization() {
        super(Type.Translator.getValue());
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new TransRole());
        return roles;
    }
}
