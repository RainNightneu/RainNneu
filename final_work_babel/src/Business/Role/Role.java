/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;



import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;

/**
 *
 * @author leopardyin
 */
public abstract class Role {

    public enum RoleType {
        
//Translation Company.    
        Admin("Admin"),
        
        SR("Sales Representative"),
        PM("Project Manager"),
        Translator("Translator"),
        PF("Proofreader"),
        
        LSR("Localization Sales Representative"),
        LPM("Localization Project Manager");
        
        private String value;

        private RoleType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public abstract JPanel createWorkArea(JPanel userProcessContainer, JPanel jp, UserAccount account, Organization organization, Enterprise enterprise, Network network,EcoSystem system);

    @Override
    public String toString() {
        
        return this.getClass().getSimpleName();
    }
}
