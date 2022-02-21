/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.antogo.Visual;

import com.antogo.Generador.Generador;
import com.antogo.MongoDBFinal.App;
import com.mongodb.client.FindIterable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.bson.Document;

/**
 *
 * @author Ángel Torada
 */
public class MainUI extends javax.swing.JFrame {

    App app;

    public MainUI() {
        initComponents();
        setLocationRelativeTo(null);
    }

    MainUI(App app) {
        this.app = app;
        initComponents();
        cargarCups();
        cargarClientes();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinnerDiasConsumo = new javax.swing.JSpinner();
        jLabelDias = new javax.swing.JLabel();
        jButtonInsertarConsumos = new javax.swing.JButton();
        jComboBoxConsumos = new javax.swing.JComboBox<>();
        jLabelInsertarConsum = new javax.swing.JLabel();
        jLabelMesConsumos = new javax.swing.JLabel();
        jLabelGenerarFact = new javax.swing.JLabel();
        jComboBoxFacturas = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jButtonGenerarFacturas = new javax.swing.JButton();
        jComboBoxCups = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButtonBorrarContrato = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxClientes = new javax.swing.JComboBox<>();
        jButtonBorrarCliente = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSpinnerDiasConsumo.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        jLabelDias.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabelDias.setText("Dias");

        jButtonInsertarConsumos.setText("Insertar Consumos");
        jButtonInsertarConsumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertarConsumosActionPerformed(evt);
            }
        });

        jComboBoxConsumos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        jComboBoxConsumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxConsumosActionPerformed(evt);
            }
        });

        jLabelInsertarConsum.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelInsertarConsum.setText("Insertar consumos de un mes");

        jLabelMesConsumos.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabelMesConsumos.setText("Mes");

        jLabelGenerarFact.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelGenerarFact.setText("Generar Facturas Un Mes");

        jComboBoxFacturas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel5.setText("Mes");

        jButtonGenerarFacturas.setText("Generar Facturas");
        jButtonGenerarFacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenerarFacturasActionPerformed(evt);
            }
        });

        jComboBoxCups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCupsActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Borrar contrato y sus consumos");

        jButtonBorrarContrato.setText("Borrar");
        jButtonBorrarContrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarContratoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Borrar cliente y sus contratos/consumos");

        jButtonBorrarCliente.setText("Borrar");
        jButtonBorrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarClienteActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel3.setText("Cliente");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel4.setText("CUPS");

        jButton1.setText("Factura");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabelInsertarConsum))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonInsertarConsumos)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelDias)
                                    .addComponent(jLabelMesConsumos))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxConsumos, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSpinnerDiasConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelGenerarFact, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBoxFacturas, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonGenerarFacturas, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxCups, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonBorrarContrato))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jButtonBorrarCliente))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(80, 80, 80))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxCups, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBoxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jButton1))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBorrarContrato)
                    .addComponent(jButtonBorrarCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelInsertarConsum)
                    .addComponent(jLabelGenerarFact))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jSpinnerDiasConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDias))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxConsumos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelMesConsumos)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBoxFacturas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonInsertarConsumos)
                    .addComponent(jButtonGenerarFacturas))
                .addGap(78, 78, 78))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonInsertarConsumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertarConsumosActionPerformed
        String mes = "consumo_" + jComboBoxConsumos.getSelectedItem().toString().toLowerCase();
        if ((int) Generador.cliente_collection.count() >= 500000) {
            app.numeroRegistrosXRound = 500000;
        } else {
            app.numeroRegistrosXRound = (int) Generador.cliente_collection.count();
        }
        for (int i = 0; i < (int) jSpinnerDiasConsumo.getValue(); i++) {
            app.insertarUnDia(mes);
        }
        JOptionPane.showMessageDialog(null, "Se han insertado los respectivos consumos (" + jSpinnerDiasConsumo.getValue() + " días) en el mes de " + jComboBoxConsumos.getSelectedItem().toString());
    }//GEN-LAST:event_jButtonInsertarConsumosActionPerformed

    private void jComboBoxConsumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxConsumosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxConsumosActionPerformed

    private void jButtonGenerarFacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerarFacturasActionPerformed
        app.numeroRegistrosXRound = (int) Generador.cliente_collection.count();
        Generador.preparar_facturas();
        app.insertar_Facturas(jComboBoxFacturas.getSelectedIndex());
        JOptionPane.showMessageDialog(null, "Se han generado las facturas.");
    }//GEN-LAST:event_jButtonGenerarFacturasActionPerformed

    private void jComboBoxCupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCupsActionPerformed

    }//GEN-LAST:event_jComboBoxCupsActionPerformed

    private void jButtonBorrarContratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarContratoActionPerformed
        Generador.borrar_consumos_contrato(Integer.parseInt(jComboBoxCups.getSelectedItem().toString()));
        JOptionPane.showMessageDialog(null, "Se ha borrado el contrato con CUPS " + jComboBoxCups.getSelectedItem().toString() + " y sus consumos");
        cargarCups();
        cargarClientes();
    }//GEN-LAST:event_jButtonBorrarContratoActionPerformed

    private void jButtonBorrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarClienteActionPerformed
        Generador.borrar_contratos_consumos_cliente(jComboBoxClientes.getSelectedItem().toString());
        JOptionPane.showMessageDialog(null, "Se ha borrado el cliente con DNI " + jComboBoxClientes.getSelectedItem().toString() + " junto a sus contratos y sus consumos");
        cargarCups();
        cargarClientes();
    }//GEN-LAST:event_jButtonBorrarClienteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        MesesFactura mf = new MesesFactura(this, true, app, jComboBoxCups.getSelectedItem().toString());
        mf.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonBorrarCliente;
    private javax.swing.JButton jButtonBorrarContrato;
    private javax.swing.JButton jButtonGenerarFacturas;
    private javax.swing.JButton jButtonInsertarConsumos;
    private javax.swing.JComboBox<String> jComboBoxClientes;
    private javax.swing.JComboBox<String> jComboBoxConsumos;
    private javax.swing.JComboBox<String> jComboBoxCups;
    private javax.swing.JComboBox<String> jComboBoxFacturas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelDias;
    private javax.swing.JLabel jLabelGenerarFact;
    private javax.swing.JLabel jLabelInsertarConsum;
    private javax.swing.JLabel jLabelMesConsumos;
    private javax.swing.JSpinner jSpinnerDiasConsumo;
    // End of variables declaration//GEN-END:variables

    private void cargarCups() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) jComboBoxCups.getModel();
        model.removeAllElements();
        FindIterable<Document> contratos = Generador.contratos_collection.find();

        for (Document contrato : contratos) {
            model.addElement(contrato.get("cups"));
        }

        jComboBoxCups.setModel(model);

    }

    private void cargarClientes() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) jComboBoxClientes.getModel();
        model.removeAllElements();
        FindIterable<Document> clientes = Generador.cliente_collection.find();

        for (Document cliente : clientes) {
            model.addElement(cliente.get("DNI"));
        }

        jComboBoxClientes.setModel(model);
    }
}