/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Network;

import Business.Enterprise.EnterpriseDirectory;

/**
 *
 * @author Yin
 */
public class Network {
      String countryName;
      EnterpriseDirectory enterpriseDirectory;
      
      public Network(String name){
          this.countryName = name;
          enterpriseDirectory = new EnterpriseDirectory();
      }

    public String getCountryName() {
        return countryName;
    }
      
      

    public EnterpriseDirectory getEnterpriseDirectory() {
        return enterpriseDirectory;
    }
      
      
      

}
