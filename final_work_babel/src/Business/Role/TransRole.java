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
import Business.Organization.TransOrganization;
import Business.TransAbility.AbilityDirectory;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import userInterface.PMs.PMsWorkAreaJPanel;
import userInterface.Sales.SalesWorkAreaJPanel;
import userInterface.Trans.TransWorkAreaJPanel;

/**
 *
 * @author Yin
 */
public class TransRole extends Role{

    
    
     @Override
    public JPanel createWorkArea(JPanel userProcessContainer, JPanel jp, UserAccount account, Organization organization, Enterprise enterprise,Network network, EcoSystem business) {
        return new TransWorkAreaJPanel(userProcessContainer, jp, account, enterprise, (TransOrganization)organization,network);
    }
    
    
    
    
}
