/*
 * VentanaClientes.java
 *
 * Created on 7 de marzo de 2007, 18:56
 */

package vista;

/**
 *
 * @author  PC
 */

import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import controlador.*;

public class VentanaClientes extends javax.swing.JFrame {
    
    private DefaultTableModel dtm;
    private Principal padre;
    
    /** Creates new form VentanaClientes */
    public VentanaClientes(Principal principal) {
        initTabla();
        initComponents();
        this.padre= principal;
        this.setTitle("Clientes registrados de Ferreyra Editor");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTable1.setModel(getModel());
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Nuevo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Modificar datos");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Borrar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Facturas");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Remitos");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Recibos");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Liquidar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
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
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton1))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (jTable1.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "Seleccione un cliente primero.", "No hay ningun cliente seleccionado", JOptionPane.ERROR_MESSAGE);
            return;
        }  
        Cliente cli = new Cliente((Integer)jTable1.getValueAt(jTable1.getSelectedRow(), 0)); //crea un cliente con el campo del codigo como param
        Liquidar li = new Liquidar(cli);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (jTable1.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "Seleccione un cliente primero.", "No hay ningun cliente seleccionado", JOptionPane.ERROR_MESSAGE);
            return;
        }  
        Cliente cli = new Cliente((Integer)jTable1.getValueAt(jTable1.getSelectedRow(), 0)); //crea un cliente con el campo del codigo como param
        VentanaRecibos vr = new VentanaRecibos(this, cli);
        vr.setVisible(true); 
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (jTable1.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "Seleccione un cliente primero.", "No hay ningun cliente seleccionado", JOptionPane.ERROR_MESSAGE);
            return;
        }  
        Cliente cli = new Cliente((Integer)jTable1.getValueAt(jTable1.getSelectedRow(), 0)); //crea un cliente con el campo del codigo como param
        VentanaRemitos vr = new VentanaRemitos(this, cli);
        vr.setVisible(true); 
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (jTable1.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "Seleccione un cliente primero.", "No hay ningun cliente seleccionado", JOptionPane.ERROR_MESSAGE);
            return;
        }  
        Cliente cli = new Cliente((Integer)jTable1.getValueAt(jTable1.getSelectedRow(), 0)); //crea un cliente con el campo del codigo como param
        VentanaFacturas vf = new VentanaFacturas(this, cli);
        vf.setVisible(true); 
    }//GEN-LAST:event_jButton5ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.padre.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
    }//GEN-LAST:event_formWindowClosing

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (jTable1.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "Seleccione un cliente primero.", "No hay ningun cliente seleccionado", JOptionPane.ERROR_MESSAGE);
            return;
        }  
        Cliente cli = new Cliente((Integer)jTable1.getValueAt(jTable1.getSelectedRow(), 0)); //crea un cliente con el campo del codigo como param
        ModificarCliente modificar = new ModificarCliente(cli, this);
        modificar.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        AltaCliente ac = new AltaCliente(this);
        ac.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int fila = jTable1.getSelectedRow();
        if (fila == -1 ){
            JOptionPane.showMessageDialog(this, "Seleccione un cliente primero.", "No hay ningun cliente seleccionado", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object[] options = {"Si, borrarlo",
                    "Cancelar"};
        int n = JOptionPane.showOptionDialog(this, "Un cliente puede tener asociados remitos, facturas, pagos, etc\n "
            + "Si borra el cliente, todos estos datos se perder�n.\n"
            + "Realmente desea borrar este cliente?", "Precauci�n", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
        if(n == 0){
            borrarFila();
            Cliente cli = new Cliente((Integer)dtm.getValueAt(fila , 0));
            cli.borrar();  
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
 
    public DefaultTableModel getModel(){
        return this.dtm;
    }
    public void initTabla(){
        dtm = new DefaultTableModel();
        LinkedList listaCli = Cliente.getClientes();
        dtm.addColumn("Codigo");
        dtm.addColumn("Librer�a");
        dtm.addColumn("Email");
        dtm.addColumn("Direcci�n");
        dtm.addColumn("Encargado");
        dtm.addColumn("Telefono");
        dtm.addColumn("CUIL");
        
        
        
        for(int i = 0; i < listaCli.size(); i++){
            Cliente cli = (Cliente) listaCli.get(i);
            dtm.addRow(getFila(cli));
        }
        listaCli.clear();
    }
    private Object[] getFila(Cliente cli){
        Object[] datos = new Object[7];
        datos[0] = cli.getId();
        datos[1] = cli.getNombre(); 
        datos[2] = cli.getEmail(); 
        datos[3] = cli.getDireccion(); 
        datos[4] = cli.getEncargado();
        datos[5] = cli.getTelefono();
        //datos[6] = cli.getComision();
        datos[6] = cli.getCuil();
        return datos;
    }
    public void insertarFila(Cliente cli){
        this.dtm.addRow(getFila(cli));
    }
    public boolean borrarFila(){
        if(jTable1.getSelectedRow() != -1){
            this.dtm.removeRow(jTable1.getSelectedRow());
            return true;
        }
        return false;
    }
    public void actualizarFila(Cliente nuevo){
        this.dtm.setValueAt(nuevo.getId(), jTable1.getSelectedRow(), 0);
        this.dtm.setValueAt(nuevo.getNombre(), jTable1.getSelectedRow(), 1);
        this.dtm.setValueAt(nuevo.getEmail(), jTable1.getSelectedRow(), 2);
        this.dtm.setValueAt(nuevo.getDireccion(), jTable1.getSelectedRow(), 3);
        this.dtm.setValueAt(nuevo.getEncargado(), jTable1.getSelectedRow(), 4);
        this.dtm.setValueAt(nuevo.getTelefono(), jTable1.getSelectedRow(), 5);
        //this.dtm.setValueAt(nuevo.getComision(), jTable1.getSelectedRow(), 6);
        this.dtm.setValueAt(nuevo.getCuil(), jTable1.getSelectedRow(), 6);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    
}
