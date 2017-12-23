/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.UserAccount.UserAccount;
import java.util.Date;

/**
 *
 * @author raunak
 */
public class WorkRequest {
     
    
    public WorkRequest(){
        requestDate = new Date();
        status = workStatus.Received.name();
        requestID = count;
        count++;
    }
    

    private static int count = 30000;
    private int requestID;
    private String customerName;
    private String content;
    private String result;
    private String contentType;
    
    public enum contentType{
        News,Essay,Movie,Advertisement,Game;
    }
    
    private String sourceLanguage;
    private String destinationLanguage;
    
    public enum Language{
        EN("en"),CN("zh-CN"),HI("hi"),FR("fr");
        private String value;
        private Language(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
        
        public String toString(){
            return value;
        }       
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public String getDestinationLanguage() {
        return destinationLanguage;
    }

    public void setDestinationLanguage(String destinationLanguage) {
        this.destinationLanguage = destinationLanguage;
    }
             

    
  
    private UserAccount SR;
    private UserAccount PM;
    private UserAccount TL;
    
    private double unitPrice;
    private int wordCount;

    public UserAccount getTL() {
        return TL;
    }

    public void setTL(UserAccount TL) {
        this.TL = TL;
    }

    public UserAccount getSR() {
        return SR;
    }

    public void setSR(UserAccount SR) {
        this.SR = SR;
    }

    public UserAccount getPM() {
        return PM;
    }

    public void setPM(UserAccount PM) {
        this.PM = PM;
    }
    
   
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getResolveDate() {
        return resolveDate;
    }

    public void setResolveDate(Date resolveDate) {
        this.resolveDate = resolveDate;
    }
    
    
    
    public enum workStatus{
        LocalizeReceived,LocalizeRequested,Scored,
        Received,Allocated,Translating,Translated,Proofread;

        
    }
    
    private Date requestDate;
    private Date resolveDate;
    
    
   

    public Date getRequestDate() {
        return requestDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public String toString(){
        return String.valueOf(requestID);
    }
    
}
