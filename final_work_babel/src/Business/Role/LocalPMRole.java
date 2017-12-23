/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.LocalizationEnterprise;
import Business.Network.Network;
import Business.Organization.LocalPMsOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import userInterface.Local_PMs.LocalPMsWorkAreaJPanel;



/**
 *
 * @author Yin
 */
public class LocalPMRole extends Role{

  
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, JPanel jp, UserAccount account, Organization organization, Enterprise enterprise, Network network,EcoSystem system) {
        return new LocalPMsWorkAreaJPanel(userProcessContainer,jp, account, (LocalizationEnterprise)enterprise, (LocalPMsOrganization)organization);
    }
    
}
