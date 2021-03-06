/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import client.Count;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import readers.EmailsReader;
import tcp.ClientTCP;
import util.Constants;
import util.Email;
import util.EnumEmail;

/**
 *
 * @author Matheus Diógenes
 */
public class FrmListaEmails extends javax.swing.JFrame {
    private Count count;
    /**
     * Creates new form FrmListaEmails
     */
    public FrmListaEmails(Count count) {
        initComponents();
        this.count = count;        
        atualizarListEmails();
    }

    private void atualizarListEmails(){
        ClientTCP clientTcp = new ClientTCP(Constants.serverPort, Constants.serverIp);
        clientTcp.send(count.toString()+EnumEmail.GET_RECEIVED_EMAILS);
        String receivedEmails = clientTcp.read();
        if (!receivedEmails.isEmpty()){        
            List<Email> emails = EmailsReader.read(receivedEmails);
            for(Email email:emails)
                count.addReceivedEmail(email);            
            DefaultTableModel tbm = (DefaultTableModel) tblListaEmails.getModel();
            while (tbm.getRowCount() > 0) {            	
                //tbm.removeRow(0);
            	DefaultTableModel dm = (DefaultTableModel) tblListaEmails.getModel();
    			dm.getDataVector().removeAllElements();
            }
            int i = 0;
            List<Email> emailFromCount = count.getReceivedEmails();
            for (Email email : emailFromCount) {
                tbm.addRow(new String[i]);
                tbm.setValueAt(email.getFrom().getUserName() +" "+ email.getFrom().getEmailAddress() , i, 0);
                tbm.setValueAt(email.getTitle(), i, 1);                
                tbm.setValueAt(email.getContent(), i, 2);            
                i++;
            }
        }      
        try {
            clientTcp.getSocket().close();
        } catch (IOException ex) {
            Logger.getLogger(FrmListaEmails.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListaEmails = new javax.swing.JTable();
        btnEnviarEmail = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Lista de E-mails");

        tblListaEmails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "De", "Título", "Conteúdo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListaEmails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListaEmailsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblListaEmails);

        btnEnviarEmail.setText("Enviar E-mail");
        btnEnviarEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarEmailActionPerformed(evt);
            }
        });

        btnAtualizar.setText("Atualizar");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAtualizar)
                        .addGap(96, 96, 96)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEnviarEmail))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnEnviarEmail)
                    .addComponent(btnAtualizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarEmailActionPerformed
        new FrmEnviarEmail(this.count).setVisible(true);
    }//GEN-LAST:event_btnEnviarEmailActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        atualizarListEmails();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void tblListaEmailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListaEmailsMouseClicked
        JOptionPane.showMessageDialog(this, "De: "+tblListaEmails.getValueAt(tblListaEmails.getSelectedRow(), 0).toString()
                                            +"\nTítulo: "+tblListaEmails.getValueAt(tblListaEmails.getSelectedRow(), 1).toString()
                                            +"\nConteúdo: "+tblListaEmails.getValueAt(tblListaEmails.getSelectedRow(), 2).toString());
    }//GEN-LAST:event_tblListaEmailsMouseClicked
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnEnviarEmail;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblListaEmails;
    // End of variables declaration//GEN-END:variables
}
