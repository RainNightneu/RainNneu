/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.TransAbility;

import Business.WorkQueue.WorkRequest.Language;
import java.util.ArrayList;

/**
 *
 * @author Yin
 */
public class AbilityDirectory {
    ArrayList<Ability> abilityList;
    
    public AbilityDirectory(){
        abilityList = new ArrayList();
    }
    
    public Ability addAbility(){
        Ability a = new Ability();
        abilityList.add(a);
        return a;
    }

    public ArrayList<Ability> getAbilityList() {
        return abilityList;
    }

    
    public Ability getAbility(String src,String dest){
        Ability a = null;
        for(Ability ability: abilityList){
            if(ability.getSource().equals(src) && ability.getDestination().equals(dest)){
                a = ability;
            }
        }
        return a;
    }
    
}
