/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Business.Network.Network;
import Business.Organization.Organization;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author Yin
 */
public class EcoSystem extends Organization{
    private static EcoSystem system;
    private ArrayList<Network> networkList;
    
    public static EcoSystem getInstance(){
        if(system == null){
            system = new EcoSystem();
        }
        return system;
    }
    
    private EcoSystem(){
        super(null);
        networkList = new ArrayList();
    }

    public ArrayList<Network> getNetworkList() {
        return networkList;
    }
    
    public Network addNetwork(String name){
        Network n = new Network(name);
        networkList.add(n);
        return n;
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        return null; //To change body of generated methods, choose Tools | Templates.
    }
    
}
