/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.Local_Sales;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.LocalSalesOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.LocalRequest;
import Business.WorkQueue.WorkRequest;
import Business.WorkQueue.WorkRequest.workStatus;
import java.awt.CardLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import userInterface.Sales.missionProgressJPanel;

/**
 *
 * @author Yin
 */
public class LocalSalesWorkAreaJPanel extends javax.swing.JPanel {

    /**
     * Creates new form LocalSalesWorkAreaJPanel
     */
    LocalSalesOrganization organization;
    JPanel upc;
    Enterprise enterprise;
    UserAccount LocalSalesAccount;

    public LocalSalesWorkAreaJPanel(JPanel upc, UserAccount account, Enterprise enterprise, LocalSalesOrganization organization) {
        initComponents();
        this.upc = upc;
        this.organization = organization;
        this.enterprise = enterprise;
        this.LocalSalesAccount = account;
        
        populateTable();
        
        txtPosition.setText(account.getRole().toString());
        txtCompanyName.setText(enterprise.getName());
        

        ImageIcon icon = new ImageIcon("image/keyword3.png");
        icon.setImage(icon.getImage().getScaledInstance(170,170,Image.SCALE_DEFAULT));
        picLabel.setIcon(icon);
    }

    
    
    public void populateTable(){
    
        DefaultTableModel dtm = (DefaultTableModel)tblProjectMonitor.getModel();
        dtm.setRowCount(0);
        
            for(WorkRequest wr: enterprise.getWorkQueue().getWorkRequestList()){ 
System.out.println("☆");
                LocalRequest lr = (LocalRequest) wr;
                if(lr.getLocalSR() == LocalSalesAccount){
                Object[] row = new Object[7];
                row[0] = wr;
                row[1] = wr.getSourceLanguage() + " → " +wr.getDestinationLanguage();
                row[2] = wr.getStatus();
                dtm.addRow(row);
                }
        }
    
    }
    
    public void populateTableByStatus(String status){
        DefaultTableModel dtm = (DefaultTableModel)tblProjectMonitor.getModel();
        dtm.setRowCount(0);
        
            for(WorkRequest wr: enterprise.getWorkQueue().getWorkRequestList()){ 
                    LocalRequest lr = (LocalRequest) wr;
                if(lr.getLocalSR() == LocalSalesAccount && lr.getStatus().equals(status)){
                    Object[] row = new Object[7];
                row[0] = wr;
                row[1] = wr.getSourceLanguage() + " → " +wr.getDestinationLanguage();
                row[2] = wr.getStatus();
                dtm.addRow(row);
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

        jToggleButton1 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCompanyName = new javax.swing.JLabel();
        txtPosition = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProjectMonitor = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCustPrice = new javax.swing.JTextField();
        txtSuppPrice = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        txtProfit = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        picLabel = new javax.swing.JLabel();

        jToggleButton1.setText("jToggleButton1");

        jButton1.setFont(new java.awt.Font("Calibri", 1, 13)); // NOI18N
        jButton1.setText("Add a Localization Mission");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Calibri", 1, 13)); // NOI18N
        jButton2.setText("Personal Mission In Progress");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Jazz LET", 0, 14)); // NOI18N
        jLabel3.setText("Action: ");

        jLabel1.setFont(new java.awt.Font("Jazz LET", 1, 24)); // NOI18N
        jLabel1.setText("As");

        jLabel2.setFont(new java.awt.Font("Jazz LET", 1, 24)); // NOI18N
        jLabel2.setText("You Are Working For：");

        txtCompanyName.setFont(new java.awt.Font("Jazz LET", 1, 24)); // NOI18N
        txtCompanyName.setForeground(new java.awt.Color(51, 153, 255));
        txtCompanyName.setText("Company Name");

        txtPosition.setFont(new java.awt.Font("Jazz LET", 1, 24)); // NOI18N
        txtPosition.setForeground(new java.awt.Color(51, 51, 51));
        txtPosition.setText("Position");

        tblProjectMonitor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mission ID", "Src → Dest", "Status"
            }
        ));
        tblProjectMonitor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProjectMonitorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProjectMonitor);

        jLabel4.setFont(new java.awt.Font("Jazz LET", 0, 14)); // NOI18N
        jLabel4.setText("Mission Monitor:");

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        jLabel5.setText("CustomerPrice:  $");

        jLabel6.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        jLabel6.setText("SupplierPrice: $");

        txtCustPrice.setEditable(false);
        txtCustPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustPriceActionPerformed(evt);
            }
        });

        txtSuppPrice.setEditable(false);

        jLabel7.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel7.setText("↓");

        jLabel8.setFont(new java.awt.Font("Synchro LET", 0, 13)); // NOI18N
        jLabel8.setText(" = Profit per thousand words:  $");

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "----Status----", "LocalizeReceived", "LocalizeRequested", "Scored", "Received", "Allocated", "Translating", "Translated" }));
        cbStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbStatusItemStateChanged(evt);
            }
        });
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });

        txtProfit.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        txtProfit.setForeground(new java.awt.Color(255, 0, 0));
        txtProfit.setText("Not Decided");

        jPanel1.setLayout(new java.awt.BorderLayout());

        picLabel.setText("_");
        jPanel1.add(picLabel, java.awt.BorderLayout.LINE_START);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCompanyName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPosition))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(159, 159, 159)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel5))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtSuppPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtCustPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtProfit))))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(320, 320, 320)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(73, 73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCompanyName)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(txtPosition))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtCustPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(txtProfit))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtSuppPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 62, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        LocalcreateWorkJPanel panel = new LocalcreateWorkJPanel(upc,enterprise,LocalSalesAccount);
        upc.add("createWorkJPanel",panel);
        CardLayout layout = (CardLayout) upc.getLayout();
        layout.next(upc);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        LocalMissionProgressJPanel panel = new LocalMissionProgressJPanel(upc,enterprise,LocalSalesAccount);
        upc.add("LocalMissionProgressJPanel",panel);
        CardLayout layout = (CardLayout) upc.getLayout();
        layout.next(upc);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtCustPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustPriceActionPerformed

    private void tblProjectMonitorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProjectMonitorMouseClicked
        // TODO add your handling code here:
        
        int selectedRow = tblProjectMonitor.getSelectedRow();
         if(selectedRow >= 0){
            LocalRequest lr = (LocalRequest) tblProjectMonitor.getValueAt(selectedRow, 0);
            txtCustPrice.setText(String.valueOf(lr.getLocalizeUnitPrice()));
            if(lr.getUnitPrice() != 0.0){
                txtSuppPrice.setText(String.valueOf(lr.getUnitPrice()));
                txtProfit.setText(String.valueOf(lr.getLocalizeUnitPrice() - lr.getUnitPrice()));
            }else{
                txtSuppPrice.setText("Not Decided");
            }
        }else{
            
            JOptionPane.showMessageDialog(null,"Please select any Row");
        }
    }//GEN-LAST:event_tblProjectMonitorMouseClicked

    private void cbStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbStatusItemStateChanged
        // TODO add your handling code here:
        String status = cbStatus.getSelectedItem().toString();
        if(status.equals("----Status----")){
            populateTable();
        }else{
             populateTableByStatus(status);
        }
        
        
        
        
       // if(status.equals(ABORT))
       
    }//GEN-LAST:event_cbStatusItemStateChanged

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStatusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel picLabel;
    private javax.swing.JTable tblProjectMonitor;
    private javax.swing.JLabel txtCompanyName;
    private javax.swing.JTextField txtCustPrice;
    private javax.swing.JLabel txtPosition;
    private javax.swing.JLabel txtProfit;
    private javax.swing.JTextField txtSuppPrice;
    // End of variables declaration//GEN-END:variables
}
