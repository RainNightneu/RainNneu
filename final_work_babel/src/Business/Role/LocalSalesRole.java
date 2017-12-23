/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.LocalSalesOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import userInterface.Local_Sales.LocalSalesWorkAreaJPanel;

/**
 *
 * @author Yin
 */
public class LocalSalesRole extends Role{

   @Override
    public JPanel createWorkArea(JPanel userProcessContainer, JPanel jp, UserAccount account, Organization organization, Enterprise enterprise, Network network,EcoSystem system) {
         return new LocalSalesWorkAreaJPanel(userProcessContainer, account, enterprise, (LocalSalesOrganization)organization);
    }
    
    
}
