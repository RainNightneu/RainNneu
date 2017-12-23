/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.Local_PMs;

import Business.Enterprise.Enterprise;
import Business.Enterprise.LocalizationEnterprise;
import Business.Enterprise.Supplier.SupplierInfo;
import Business.Enterprise.TranslationEnterprise;
import Business.Organization.LocalPMsOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.LocalRequest;
import Business.WorkQueue.WorkRequest;
import Business.WorkQueue.WorkRequest.workStatus;
import java.awt.CardLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import userInterface.Local_Sales.*;
import userInterface.PMs.allocateProjectJPanel;

/**
 *
 * @author Yin
 */
public class LocalPMsWorkAreaJPanel extends javax.swing.JPanel {

    /**
     * Creates new form LocalSalesWorkAreaJPanel
     */

    JPanel upc;
    ArrayList<String> p=new ArrayList<String>();
    UserAccount account;
    LocalizationEnterprise enterprise;
    LocalPMsOrganization organization;
    JPanel jp;
    public LocalPMsWorkAreaJPanel(JPanel upc,JPanel jp, UserAccount account, LocalizationEnterprise enterprise, LocalPMsOrganization organization) {
       initComponents();
       this.upc = upc;
       this.jp=jp;
       this.account = account;
       this.enterprise = enterprise;
       this.organization = organization;
       populateProjectTable();
       upc.remove(0);
       txtS.setVisible(false);
       txtR.setVisible(false);
       txtSource.setVisible(false);
       txtResult.setVisible(false);
       spinnerScore.setVisible(false);
       btnSubmit.setVisible(false);
       
    }

    public void populateProjectTable(){
         DefaultTableModel dtm = (DefaultTableModel)tblProject.getModel();
         dtm.setRowCount(0);
        
         for(WorkRequest wr  :enterprise.getWorkQueue().getWorkRequestList()){
             
            if(wr.getClass().getSimpleName().equals("LocalRequest")){
                LocalRequest lr = (LocalRequest)wr;
                if(lr.getLocalPM() == account){
                Object[] row = new Object[7];
                row[0] = lr;
                row[1] = lr.getRequestDate();
                row[2] = lr.getLocalSR().getEmployee().getName();
                row[3] = lr.getSourceLanguage() + " → " +lr.getDestinationLanguage();
                row[4] = lr.getWordCount();
                row[5] = lr.getContentType();
                row[6] = lr.getStatus();
                dtm.addRow(row);
            }
             
             
         }
             
System.out.println("123");
         }
       
    }
    
    public void populateFinishedProjectTable(){
         DefaultTableModel dtm = (DefaultTableModel)tblProject.getModel();
         dtm.setRowCount(0);
        
         for(WorkRequest wr  :enterprise.getWorkQueue().getWorkRequestList()){
             
            if(wr.getClass().getSimpleName().equals("LocalRequest") && wr.getStatus().equals(workStatus.Translated.name())){
                LocalRequest lr = (LocalRequest)wr;
                if(lr.getLocalPM() == account){
                Object[] row = new Object[7];
                row[0] = lr;
                row[1] = lr.getRequestDate();
                row[2] = lr.getLocalSR().getEmployee().getName();
                row[3] = lr.getSourceLanguage() + " → " +lr.getDestinationLanguage();
                row[4] = lr.getWordCount();
                row[5] = lr.getContentType();
                row[6] = lr.getStatus();
                dtm.addRow(row);
            }
             
             
         }
             
System.out.println("123");
         }
       
    }
    
    
    public void populateRankTable(String articleType){
        
        DefaultTableModel dtm = (DefaultTableModel)tblRank.getModel();
        dtm.setRowCount(0);
        
        for(SupplierInfo si: enterprise.getSupplierDirectory().getSupplierList()){
            Object[] row = new Object[7];
            row[0] = si.getTranslationEnterprise();
            switch(articleType){
                case "News": row[1]=si.getNewsRate();
                    break;
                case "Essay": row[1]=si.getEssayRate();
                    break;
                case "Movie": row[1]=si.getMovieRate();
                    break;
                case "Advertisement": row[1]=si.getAdvertisementRate();
                    break;
                case "Game": row[1]=si.getGameRate(); 
                    break;
            }
           
            row[2] = printStars(Double.parseDouble(String.valueOf(row[1])));
            long diff;
            long days=0;
            double n=0;
            DecimalFormat df = new DecimalFormat( "0.0000 "); 
            for(WorkRequest wr  :enterprise.getWorkQueue().getWorkRequestList())
            {
                LocalRequest lr = (LocalRequest)wr;
                if(lr.getTransEnterprise()!=null && lr.getResolveDate()!=null && lr.getRequestDate()!=null)
                {
                if(wr.getClass().getSimpleName().equals("LocalRequest") && lr.getTransEnterprise().equals(si.getTranslationEnterprise()))
                {
                    
                
                    diff =lr.getResolveDate().getTime() - lr.getRequestDate().getTime();
                    days = days+diff / (60 * 24);
                    n++;
                }
                else
                {
                    n=n+0;
                    days=days+0;
                    
                }
                }
                else
                {
                    n=n+0;
                    days=days+0;
                    
                }
            }
            if(days==0)
            {
                row[3]= 0;
                p.add(String.valueOf(days/n));
            }
            else
            {
                row[3]=df.format(days/n);
                p.add(String.valueOf(days/n));
            }
            
            dtm.addRow(row);
        
        }
                
        
    }
    
    
    
    public String printStars(double rate){
        int number  = (int) rate;
        String stars = "";
        for(int i=0;i<number;i++){
            stars = stars+"☆";
        }
        return stars;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblProject = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        txtType = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtResult = new javax.swing.JTextArea();
        txtS = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtSource = new javax.swing.JTextArea();
        txtR = new javax.swing.JLabel();
        tgFinished = new javax.swing.JToggleButton();
        spinnerScore = new javax.swing.JSpinner();
        btnSubmit = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblRank = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();

        tblProject.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Article ID", "StartDate", "Sales Name", "Src→Dest", "Word Count", "Type", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProjectMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProject);

        jButton1.setText("Send Request to Company");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtType.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        txtType.setText("ContentType");

        jLabel2.setText("Sort By Suppliers Grade of: ");

        txtResult.setColumns(20);
        txtResult.setLineWrap(true);
        txtResult.setRows(5);
        jScrollPane3.setViewportView(txtResult);

        txtS.setFont(new java.awt.Font("Jazz LET", 0, 14)); // NOI18N
        txtS.setText("Source: ");

        txtSource.setColumns(20);
        txtSource.setLineWrap(true);
        txtSource.setRows(5);
        jScrollPane4.setViewportView(txtSource);

        txtR.setFont(new java.awt.Font("Jazz LET", 0, 14)); // NOI18N
        txtR.setText("Result: ");

        tgFinished.setText("Finished Work → Make a Score");
        tgFinished.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgFinishedActionPerformed(evt);
            }
        });

        spinnerScore.setModel(new javax.swing.SpinnerNumberModel(4, 1, 5, 1));

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        jButton3.setText("Bar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tblRank.setAutoCreateRowSorter(true);
        tblRank.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "SupplierName", "Grade", "Grade(Stars)", "Ave Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tblRank);

        jLabel10.setFont(new java.awt.Font("Jazz LET", 0, 24)); // NOI18N
        jLabel10.setText("Localization Project Manager");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtType))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtS))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(txtR)
                                            .addGap(100, 100, 100))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(spinnerScore, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnSubmit)))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                            .addComponent(tgFinished, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(134, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 644, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtType)
                        .addComponent(jLabel2))
                    .addComponent(tgFinished, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(152, 152, 152))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtR)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(spinnerScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSubmit))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int selectedProjectRow = tblProject.getSelectedRow();
        int selectedCompanyRow = tblRank.getSelectedRow();
         if(selectedProjectRow >= 0 && selectedCompanyRow >=0){
            LocalRequest lr = (LocalRequest) tblProject.getValueAt(selectedProjectRow, 0);
            
            if(lr.getStatus().equals(workStatus.LocalizeReceived.name())){
                String companyName = (String) tblRank.getValueAt(selectedCompanyRow, 0);
System.out.println(companyName)   ;            
                lr.setTransEnterprise(companyName);
                lr.setStatus(workStatus.LocalizeRequested.name());
                populateProjectTable();
            }else{
                JOptionPane.showMessageDialog(null,"The Project is already requested");
            }
            

        }else{
            
            JOptionPane.showMessageDialog(null,"Please select any Row");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblProjectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProjectMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblProject.getSelectedRow();
         if(selectedRow >= 0 ){
            LocalRequest lr = (LocalRequest) tblProject.getValueAt(selectedRow, 0);
            if(lr.getStatus().equals("Translated")){
                txtSource.setText(lr.getContent());
                txtResult.setText(lr.getResult());
                txtType.setText(lr.getContentType());
                 populateRankTable(lr.getContentType());
            }else{
                txtType.setText(lr.getContentType());
                populateRankTable(lr.getContentType());
            }
           
            
        }else{
            
            JOptionPane.showMessageDialog(null,"Please select any Row");
        }
         
System.out.println("wori");
    }//GEN-LAST:event_tblProjectMouseClicked

    private void tgFinishedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgFinishedActionPerformed
        // TODO add your handling code here:
        boolean selected = tgFinished.isSelected();
        if(selected==true){
            txtS.setVisible(selected);
            txtR.setVisible(selected);
            txtSource.setVisible(selected);
            txtResult.setVisible(selected);
            spinnerScore.setVisible(selected);
            btnSubmit.setVisible(selected);
            populateFinishedProjectTable();
        }else{
            txtS.setVisible(false);
            txtR.setVisible(false);
            txtSource.setVisible(false);
            txtResult.setVisible(false);
            txtSource.setText("");
            txtResult.setText("");
            spinnerScore.setVisible(false);
            btnSubmit.setVisible(false);
            populateProjectTable();
        }
    }//GEN-LAST:event_tgFinishedActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        int selectedProject = tblProject.getSelectedRow();
        int score =  (int) spinnerScore.getValue();
        
        Double baseScore = 0.0;
        
       if(selectedProject >= 0 ){
            LocalRequest lr = (LocalRequest) tblProject.getValueAt(selectedProject,0);
            txtType.setText(lr.getContentType());
            if(lr.getStatus().equals(workStatus.Translated.name())){
                  SupplierInfo si = enterprise.getSupplierDirectory().findInfoByTranslationName(lr.getTransEnterprise());
                String content = lr.getContent();
            switch(content){
                case "News": baseScore=si.getNewsRate();
                    break;
                case "Essay": baseScore=si.getEssayRate();
                    break;
                case "Movie": baseScore=si.getMovieRate();
                    break;
                case "Advertisement": baseScore=si.getAdvertisementRate();
                    break;
                case "Game": baseScore=si.getGameRate(); 
                    break;
            }
System.out.println(baseScore);
            baseScore = baseScore + (score-baseScore)/5;
            
            switch(content){
                case "News": si.setNewsRate(baseScore);
                    break;
                case "Essay": si.setEssayRate(baseScore);
                    break;
                case "Movie": si.setMovieRate(baseScore);
                    break;
                case "Advertisement": si.setAdvertisementRate(baseScore);
                    break;
                case "Game": si.setGameRate(baseScore);
                    break;
            }
System.out.println(baseScore);
                
            lr.setStatus(workStatus.Scored.name());
            txtSource.setText("");
            txtResult.setText("");
            
            populateProjectTable();
            }else{
                JOptionPane.showMessageDialog(null,"Already Scored");
            }
 
        }else{
           
            JOptionPane.showMessageDialog(null,"Please select any Row");
        }
          
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //upc.removeAll();
        BarJPanel tljp=new BarJPanel(upc,jp,enterprise, p);
        upc.add("BarJPanel",tljp);
        CardLayout layout = (CardLayout) upc.getLayout();
        layout.next(upc);
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSpinner spinnerScore;
    private javax.swing.JTable tblProject;
    private javax.swing.JTable tblRank;
    private javax.swing.JToggleButton tgFinished;
    private javax.swing.JLabel txtR;
    private javax.swing.JTextArea txtResult;
    private javax.swing.JLabel txtS;
    private javax.swing.JTextArea txtSource;
    private javax.swing.JLabel txtType;
    // End of variables declaration//GEN-END:variables
}
