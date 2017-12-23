/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;


import Business.Organization.Organization.Type;
import java.util.ArrayList;

/**
 *
 * @author leopardyin
 */
public class OrganizationDirectory {

    private ArrayList<Organization> organizationList;

    public OrganizationDirectory() {
        organizationList = new ArrayList<>();
    }

    public ArrayList<Organization> getOrganizationList() {
        return organizationList;
    }

    public Organization createOrganization(Type type) {
        Organization organization = null;
        if (type.getValue().equals(Type.Sales.getValue())) {
            organization = new SalesOrganization();
            organizationList.add(organization);
        }
        if (type.getValue().equals(Type.Project.getValue())) {
            organization = new PMsOrganization();
            organizationList.add(organization);
        }
        if (type.getValue().equals(Type.Translator.getValue())) {
            organization = new TransOrganization();
            organizationList.add(organization);
        }
        if (type.getValue().equals(Type.LocalProject.getValue())) {
            organization = new LocalPMsOrganization();
            organizationList.add(organization);
        }
        if (type.getValue().equals(Type.LocalSales.getValue())) {
            organization = new LocalSalesOrganization();
            organizationList.add(organization);
        }
        return organization;
    }
}
