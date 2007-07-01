/*
 * DeudaFactura.java
 *
 * Created on 22 de marzo de 2007, 22:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author PC
 */
public class DeudaFactura {
    
    private int id;
    private DetalleFactura detalleFactura;
    private int cantidad;
    private String numeroFactura;
    private String tipoFactura;
    private Cliente cliente;
    private float total;

    /** Creates a new instance of DeudaFactura */
    public DeudaFactura(DetalleFactura detalle) {
    this.setId(0);
        this.setDetalleFactura(detalle);
        this.setNumeroFactura(detalle.getFactura().getNumero());
        this.setCliente(detalle.getFactura().getCliente());
        this.setTotal(detalle.getEjemplar().getPrecioUnitario() * detalle.getCantidad());
        this.setCantidad(detalle.getCantidad());
        this.setTipoFactura(detalle.getFactura().getTipo());
    }
    public DeudaFactura(int idDetalleFactura){
       
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Deuda where id_detalle = " + idDetalleFactura + " and conrecibo = True;");
        try {
            if(rs.next()){
        
            this.setDetalleFactura(new DetalleFactura(rs.getInt("id_detalle")));
            this.setNumeroFactura(rs.getString("nro_remito")); //el nro_remito hace en esta clase de nro_factura
            this.setCliente(new Cliente(rs.getInt("id_cliente")));
            this.setTotal(rs.getFloat("total"));
            this.setCantidad(rs.getInt("cantidad"));
            this.setId(rs.getInt("id"));
                                  
            rs.close();
            
        }
        else {
            System.out.println("error al buscar una Deuda");
        } } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("Error sql al buscar una Deuda en BD");
        }
        bd.cerrar();
    }
    
    public void hacerAlta(){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        //en este caso, el campo id_remito es el id de la factura
        bd.insertar("INSERT INTO Deuda (id_detalle, cantidad, total, nro_remito, id_cliente, conrecibo) VALUES (" +this.getDetalleFactura().getId()  + ", " +this.getCantidad()+ ", " + this.getTotal() + ", '" + this.getNumeroFactura() + "', " + this.getCliente().getId() + ", True);");
        ResultSet rs = bd.consulta("SELECT id FROM Deuda WHERE id_detalle = " + this.detalleFactura.getId() ) ;
        try{
            rs.next();
            this.setId(rs.getInt("id"));
        }catch(SQLException e) {System.out.println("errorrrrrr insertando Deuda en la BD");}
        bd.cerrar();
    }
    
    public void borrar(){
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar("DELETE FROM Deuda WHERE id = " + this.id);
        bd.cerrar();
    }
    
    public static LinkedList getDeudasFactura(int idCliente){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id FROM Deuda WHERE id_cliente = " + idCliente + " and conrecibo = True;"); 
        try{
            int id;
            while(rs.next()){
                id = rs.getInt("id");
                DeudaFactura deuda = new DeudaFactura(id);
                lista.add(deuda);
               }
            return lista;
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
        
     }
    
    public static LinkedList getDeudaXFactura(String nroFactura){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id_detalle FROM Deuda WHERE nro_remito = '" + nroFactura+ "' and conrecibo = True;"); 
        try{
            int id_detalle;
            while(rs.next()){
                id_detalle = rs.getInt("id_detalle");
                DeudaFactura deuda = new DeudaFactura(id_detalle);
                lista.add(deuda);
               }
            return lista;
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
        
     }
    
    public void actualizarDeuda(DetalleRecibo detalleRecibo){
        //se le pasa un detalle de factura pagada, con referencia a un detalle de remito
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT * FROM Deuda WHERE id_cliente = " + detalleRecibo.getRecibo().getCliente().getId() + " and id_detalle = " + detalleRecibo.getIdDetalleFactura() + " and conrecibo = True;"); 
        try{
            rs.next();
            this.detalleFactura = new DetalleFactura(rs.getInt("id_detalle"));
            this.cliente = detalleRecibo.getRecibo().getCliente();
            this.cantidad = rs.getInt("cantidad");
            this.id = rs.getInt("id");
            this.numeroFactura = detalleFactura.getFactura().getNumero();
            this.setTipoFactura(detalleFactura.getFactura().getTipo());
            this.total = rs.getFloat("total");
            
            //empieza la actualizacion
            if(detalleFactura.getEjemplar().getId() == detalleRecibo.getEjemplar().getId() && detalleRecibo.getCantidad() <= this.cantidad){
                System.out.println("entro, son iguales los ejemplares");
                if(detalleRecibo.getCantidad() == this.cantidad){
                    //son iguales las cantidades, deuda cancelada totalmente, borrar el registro
                    //imprimo ambos totales por las deudas
                    System.out.println(this.total + " =  "  + (detalleRecibo.getCantidad()*detalleFactura.getEjemplar().getPrecioUnitario()));
                    bd.insertar("DELETE FROM Deuda where id = " + this.id);
                }
                else{
                    //son cantidades distintas, se actualiza la deuda
                    int nuevaCant = this.cantidad - detalleRecibo.getCantidad();
                    float nuevoTotal = this.total - (detalleRecibo.getCantidad() * detalleRecibo.getEjemplar().getPrecioUnitario());
                    
                    bd.insertar("UPDATE Deuda SET cantidad = " + nuevaCant + ", total = " + nuevoTotal + " WHERE id = " + this.id);
                }
            }
            else{
                System.out.println("Error...\nlos ejemplares de los detalles no coinciden,\no la cantidad pagada es mayor que la adeudada");
            }
            bd.cerrar();
       }catch(SQLException e ) {System.out.println(e.toString()); }
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DetalleFactura getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura = detalleFactura;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(String tipoFactura) {
        this.tipoFactura = tipoFactura;
    }
    
}
