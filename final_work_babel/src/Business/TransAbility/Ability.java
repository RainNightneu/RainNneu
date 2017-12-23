/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.TransAbility;

import Business.WorkQueue.WorkRequest.Language;

/**
 *
 * @author Yin
 */
public class Ability {
    String Source;
    String Destination;
    double unitPrice;
    
             
    public Ability(){

    }
    public Ability(String src,String dest){
        this.Source = src;
        this.Destination = dest;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String Destination) {
        this.Destination = Destination;
    }
    
    

    
    
    
    
    
}
