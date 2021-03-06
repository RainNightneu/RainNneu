/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.PMs;

import Business.Enterprise.Enterprise;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.WorkRequest;
import Business.WorkQueue.WorkRequest.workStatus;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Yin
 */
public class allocateProjectJPanel extends javax.swing.JPanel {

    /**
     * Creates new form allocateProjectJPanel
     */
    JPanel upc;
    Enterprise enterprise;
    UserAccount PMaccount;
    WorkRequest wr;

    public allocateProjectJPanel(JPanel upc, Enterprise enterprise, UserAccount PMaccount,WorkRequest wr) {
        initComponents();
        this.upc = upc;
        this.enterprise = enterprise;
        this.PMaccount = PMaccount;
        this.wr = wr;
        populateTable();
        
    }
    
    public void populateTable(){
        DefaultTableModel dtm = (DefaultTableModel)tblTranslator.getModel();
        dtm.setRowCount(0);
        
        for(UserAccount ua: enterprise.getUserAccountDirectory().getUserAccountList()){
            if(ua.getRole().toString().equals("TransRole")){
                if(ua.getAbilityDirectory().getAbility(wr.getSourceLanguage(), wr.getDestinationLanguage() ) != null){
                    Object[] row = new Object[4];
                    row[0] = ua;
                    row[1] = ua.getEmployee().getName();
                    row[2] = wr.getSourceLanguage() +" → "+wr.getDestinationLanguage();
                    row[3] = ua.getAbilityDirectory().getAbility(wr.getSourceLanguage(), wr.getDestinationLanguage()).getUnitPrice();
                    dtm.addRow(row);
                }
                
                            
        }
            
                
               
                
                
        
    }
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
        tblTranslator = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        tblTranslator.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "account ID", "Translator", "Src → Dest", "Unit Price"
            }
        ));
        jScrollPane1.setViewportView(tblTranslator);

        jButton1.setText("Allocate");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("< Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(88, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblTranslator.getSelectedRow();
        if(selectedRow >= 0){
            UserAccount ua =  (UserAccount) tblTranslator.getValueAt(selectedRow,0);
            wr.setTL(ua);
            wr.setStatus(workStatus.Allocated.name());
            JOptionPane.showMessageDialog(null,"Success");
            
            upc.remove(this);
            Component[] componentArray = upc.getComponents();
            Component component = componentArray[componentArray.length - 1];
            PMsWorkAreaJPanel pmp = (PMsWorkAreaJPanel) component;
            pmp.populateTable();
            CardLayout layout = (CardLayout) upc.getLayout();
            layout.previous(upc);
            
        }else{
            JOptionPane.showMessageDialog(null,"Please select any Row");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        upc.remove(this);
        Component[] componentArray = upc.getComponents();
        Component component = componentArray[componentArray.length - 1];
        PMsWorkAreaJPanel pmp = (PMsWorkAreaJPanel) component;
        pmp.populateTable();
        CardLayout layout = (CardLayout) upc.getLayout();
        layout.previous(upc);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTranslator;
    // End of variables declaration//GEN-END:variables
}
