/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.TRole;

import Business.EcoSystem;
import userInterface.Trans.*;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.TransOrganization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.LocalRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author qwe09
 */
public class ImageJPanel extends javax.swing.JPanel {

    /**
     * Creates new form ImageJPanel
     */
     JPanel upc;
    JPanel jp;
    Enterprise enterprise;
    EcoSystem s;


    ImageJPanel(JPanel userProcessContainer, JPanel jp, Enterprise enterprise, EcoSystem system) {
          this.upc = userProcessContainer;
          this.jp = jp;
       this.enterprise = enterprise;
       this.s=system;
      // this.wr = wr;
        initComponents();
       // upc.removeAll();
     // jp.setLayout(null);
     jp.setPreferredSize(new Dimension(100, 100));
        JPanel n= new ChartPanel(CreatePieJPanel());
         upc.add(n);
          JButton newButton = new JButton("<Back");
      newButton.setPreferredSize(new java.awt.Dimension(33,33));
      jp.add(newButton);
newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                upc.remove(n);
                upc.remove(1);
                CardLayout layout = (CardLayout) upc.getLayout();
                
        layout.previous(upc);
        newButton.setSize(0,0);
        jp.remove(newButton);
                        CardLayout layout1 = (CardLayout) jp.getLayout();
                
        layout1.previous(jp);
        
                System.out.println("触发了事件");
            }
        });
       
        //jPanel1.setPreferredSize(new Dimension(10,10));
       
      //newButton.setSize(10, 10);
    }
JFreeChart CreatePieJPanel()
    {
        int n=0, e=0, m=0, a=0, g=0;
        
               DefaultPieDataset dfp=new DefaultPieDataset();
               for(int i=0;i<enterprise.getWorkQueue().getWorkRequestList().size();i++)
        {
            if(enterprise.getWorkQueue().getWorkRequestList().get(i).getContentType().equals("News"))
            {
                n++;
            }
            if(enterprise.getWorkQueue().getWorkRequestList().get(i).getContentType().equals("Essay"))
            {
                e++;
            }
            if(enterprise.getWorkQueue().getWorkRequestList().get(i).getContentType().equals("Movie"))
            {
                m++;
            }
            if(enterprise.getWorkQueue().getWorkRequestList().get(i).getContentType().equals("Advertisement"))
            {
                a++;
            }if(enterprise.getWorkQueue().getWorkRequestList().get(i).getContentType().equals("Game"))
            {
                g++;
            }
        }
            for(int j=0;j<s.getNetworkList().size();j++)
            {
                for(int k=0;k<s.getNetworkList().get(j).getEnterpriseDirectory().getEnterpriseList().size();k++)
                {
                    if(s.getNetworkList().get(j).getEnterpriseDirectory().getEnterpriseList().get(k).getEnterpriseType().getValue().equals("Localization Company"))
                    {
                        for(int l=0;l<s.getNetworkList().get(j).getEnterpriseDirectory().getEnterpriseList().get(k).getWorkQueue().getWorkRequestList().size();l++)
                        {
                            LocalRequest lr = (LocalRequest)s.getNetworkList().get(j).getEnterpriseDirectory().getEnterpriseList().get(k).getWorkQueue().getWorkRequestList().get(l);
                            if(lr.getTransEnterprise().equals(enterprise.getName()))
                            {
                                for(int h=0;h<enterprise.getWorkQueue().getWorkRequestList().size();h++)
        {
            if(lr.getContentType().equals("News"))
            {
                n++;
            }
            if(lr.getContentType().equals("Essay"))
            {
                e++;
            }
            if(lr.getContentType().equals("Movie"))
            {
                m++;
            }
            if(lr.getContentType().equals("Advertisement"))
            {
                a++;
            }if(lr.getContentType().equals("Game"))
            {
                g++;
            }
                            }
                        }
                    }
                }
            }
            
           
        }
;
       dfp.setValue("News", n);
       dfp.setValue("Essay", e);
       dfp.setValue("Movie", m);
       dfp.setValue("Advertisement", a);
       dfp.setValue("Game", g);
        //Create JFreeChart object
       JFreeChart chart =ChartFactory.createPieChart("Statistic of article",dfp, true, true, true);
      
        return chart;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 730, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
