/*
 * FacturaConRecibo.java
 *
 * Created on 22 de marzo de 2007, 21:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class FacturaConRecibo extends Factura{
    
    
    
   /** Creates a new instance of Factura */
    public FacturaConRecibo(String numero, String tipo, String fecha, Cliente cliente, float comision, boolean pagado ) {
        super(numero,tipo, fecha, cliente, comision, pagado);
        
    }
    
    public FacturaConRecibo(int id){
        super(id);
    }
    public FacturaConRecibo(String tipo, String numero){
        super(tipo, numero);
    }
    
    public void hacerAlta(){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        
        bd.insertar("INSERT INTO Factura (numero, tipo, fecha, id_cliente, total, comision, pagado ) VALUES ('" +this.getNumero() + "', '" +this.getTipo()+ "', '" + this.getFecha()+ "'," + this.getCliente().getId() +", " + this.getTotal() + ", " + this.getComision() + ", False);");
        ResultSet rs = bd.consulta("SELECT id FROM Factura WHERE numero = '" +this.getNumero() +"' and tipo = '" + this.getTipo() + "';");
        try{
            rs.next();
            this.id = rs.getInt("id");
        }catch(SQLException e) {System.out.println("errorrrrrr insertando remito en la BD");}
        bd.cerrar();
    }
    
    public static LinkedList getFacturas(int idCliente){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id FROM Factura WHERE id_cliente = " + idCliente + " and pagado = False;"); 
        try{
            int id;
            while(rs.next()){
                id = rs.getInt("id");
                FacturaConRecibo factura = new FacturaConRecibo(id);
                lista.add(factura);
               }
            return lista;
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
        
     }
    
    public LinkedList getDetalles(){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id FROM DetalleFactura where id_factura= " + this.id + " ;"); 
        try{
            int ide;
            while(rs.next()){
                ide = rs.getInt("id");
                DetalleFactura detalleFactura = new DetalleFactura(ide);
                lista.add(detalleFactura);
               }
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
        return lista;
     }
    
    public static LinkedList getFacturasPendientes(int idCliente){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id FROM Factura WHERE id_cliente = " + idCliente + " and pagado = False and completo = False;"); 
        try{
            int id; 
            while(rs.next()){ 
                id = rs.getInt("id"); 
                FacturaConRecibo factura= new FacturaConRecibo(id);
                lista.add(factura);
               }
            return lista; 
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
        
     } 
    
    public void borrar(){
        LinkedList detalles = this.getDetalles();
        for(int i = 0; i < detalles.size(); i++){
            DetalleFactura detalle = (DetalleFactura)detalles.get(i);
            DeudaFactura deuda = new DeudaFactura(detalle.getId());
            deuda.borrar();
            detalle.borrar();
        }
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar("DELETE FROM Factura WHERE id = " + this.id);
        bd.cerrar();
    }
    
    public boolean tieneRecibosAsociados(){
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        LinkedList detalles = this.getDetalles();
        for(int i = 0 ; i < detalles.size(); i++){
            DetalleFactura def = (DetalleFactura) detalles.get(i);
            ResultSet rs = bd.consulta("SELECT * FROM DetalleRecibo WHERE id_detalle_factura = " + def.getId());
            try{
                if(rs.next()){
                    bd.cerrar();
                    return true;
                }
            }catch(SQLException e){}
        }
        bd.cerrar();
        return false;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
   

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }
    
    
   
    
}
