/*
 * VentanaRecibos.java
 *
 * Created on 27 de marzo de 2007, 20:05
 */

package vista;

import controlador.Cliente;
import controlador.Recibo;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  PC
 */
public class VentanaRecibos extends javax.swing.JFrame {
    
    private Cliente cliente;
    private VentanaClientes padre;
    /** Creates new form VentanaRecibos*/
    public VentanaRecibos(VentanaClientes vc, Cliente cli) {
        this.cliente = cli;
        this.padre= vc;
        initTabla();
        initComponents();
        
        this.setTitle("Recibos de " + cliente.getNombre());
        
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jTable1.setModel(getModel());
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Nuevo Recibo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Borrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Ver Detalle");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Cerrar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 225, Short.MAX_VALUE)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        NuevoRecibo nr = new NuevoRecibo(this, this.cliente);
        nr.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int fila = jTable1.getSelectedRow();
        if (fila == -1){
            JOptionPane.showMessageDialog(this, "Seleccione un recibo primero.", "No hay ningun recibo seleccionado", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Recibo recibo = new Recibo((String)dtm.getValueAt(fila, 0)) ;
        VerDetalleRecibo vdr = new VerDetalleRecibo(recibo);
        vdr.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int fila = jTable1.getSelectedRow();
        if (fila == -1 ){
            JOptionPane.showMessageDialog(this, "Seleccione un recibo primero.", "No hay ningun recibo seleccionado", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object[] options = {"Borrar",
                    "Cancelar"};
        int n = JOptionPane.showOptionDialog(this, "Se perderan todos los datos de este recibo. "
            + "Realmente desea borrarlo?", "Precaución", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
        if(n == 0){
            Recibo recibo = new Recibo((String)dtm.getValueAt(fila, 0)) ;
            recibo.borrar(); 
            borrarFila();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed
   public DefaultTableModel getModel(){
        return this.dtm;
    }
    public void initTabla(){
        dtm = new DefaultTableModel();
        LinkedList listaRecibos = Recibo.getRecibos(cliente.getId());
        dtm.addColumn("Numero");
        dtm.addColumn("Fecha");
        dtm.addColumn("Total");
        
        
        for(int i = 0; i < listaRecibos.size(); i++){
            Recibo recibo= (Recibo) listaRecibos.get(i);
            dtm.addRow(getFila(recibo));
        }
        listaRecibos.clear();
    }
    private Object[] getFila(Recibo recibo){
        Object[] datos = new Object[4];
        datos[0] = recibo.getNumero();
        datos[1] = recibo.getFecha();
        datos[2] = recibo.getTotal();
               
        
        return datos;
    }
    public void insertarFila(Recibo recibo){
        this.dtm.addRow(getFila(recibo));
    }
    public boolean borrarFila(){
        if(jTable1.getSelectedRow() != -1){
            this.dtm.removeRow(jTable1.getSelectedRow());
            return true;
        }
        return false;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    private DefaultTableModel dtm;
}
