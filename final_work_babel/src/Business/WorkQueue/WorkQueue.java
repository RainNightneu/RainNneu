/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import java.util.ArrayList;

/**
 *
 * @author raunak
 */
public class WorkQueue {
    
    private ArrayList<WorkRequest> workRequestList;
  

    public WorkQueue() {
        workRequestList = new ArrayList();
        
    }

    public ArrayList<WorkRequest> getWorkRequestList() {
        return workRequestList;
    }

  
    
    
    public WorkRequest addTranslationRequest(){
        
        WorkRequest wr = new WorkRequest();
        workRequestList.add(wr);
        return wr;
    }
    
    
    public LocalRequest addLocalizationRequest(){
        LocalRequest lr =  new LocalRequest();
        workRequestList.add(lr);
        return lr;
    }
             
    
}