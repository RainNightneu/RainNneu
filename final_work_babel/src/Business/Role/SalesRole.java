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
import Business.Organization.SalesOrganization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import userInterface.Sales.SalesWorkAreaJPanel;

/**
 *
 * @author Yin
 */
public class SalesRole extends Role{

   
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, JPanel jp, UserAccount account, Organization organization, Enterprise enterprise, Network network,EcoSystem business) {
        return new SalesWorkAreaJPanel(userProcessContainer, account, enterprise, (SalesOrganization) organization, network);
    }
    
}
