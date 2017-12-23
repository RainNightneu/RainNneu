/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.LocalizationEnterprise;
import Business.Network.Network;
import Business.Organization.LocalPMsOrganization;
import Business.Organization.Organization;
import Business.Organization.PMsOrganization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import userInterface.LRole.LAdminWorkAreaJPanel;
import userInterface.TRole.TAdminWorkAreaJPanel;

/**
 *
 * @author raunak
 */
public class AdminRole extends Role{

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, JPanel jp, UserAccount account, Organization organization, Enterprise enterprise, Network network,EcoSystem system) {
        
        if(enterprise.getEnterpriseType().getValue().equals("Localization Company"))
        {
            return new LAdminWorkAreaJPanel(userProcessContainer, jp,  account, organization, enterprise, network, system);
        }
        if(enterprise.getEnterpriseType().getValue().equals("Translation Company"))
        {
            return new TAdminWorkAreaJPanel(userProcessContainer, jp, account, organization, enterprise, network, system);
        }

        return null;
    }  
}
