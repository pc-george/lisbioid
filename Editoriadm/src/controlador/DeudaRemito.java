/*
 * Deuda.java
 *
 * Created on 22 de marzo de 2007, 21:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Registra las deudas para las ventas que se hacen con remito / factura
 * @author PC
 */
public class DeudaRemito {
    
    private int id;
    private DetalleRemito detalleRemito;
    private int cantidad;
    private String numeroRemito;
    private Cliente cliente;
    private float total;
    
    /** Creates a new instance of Deuda */
    public DeudaRemito(DetalleRemito detalle) {
        this.setId(0);
        this.setDetalleRemito(detalle);
        this.setNumeroRemito(detalle.getRemito().getNumero());
        this.setCliente(detalle.getRemito().getCliente());
        this.setTotal(detalle.getEjemplar().getPrecioUnitario() * detalle.getCantidad());
        this.setCantidad(detalle.getCantidad());
    }
    
    public DeudaRemito(int idDetalleRemito){
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Deuda where id_detalle = " + idDetalleRemito + ";");
        try {
            if(rs.next()){
        
            this.setDetalleRemito(new DetalleRemito(idDetalleRemito));
            this.setNumeroRemito(getDetalleRemito().getRemito().getNumero());
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
        bd.insertar("INSERT INTO Deuda (id_detalle, cantidad, total, nro_remito, id_cliente) VALUES (" +this.getDetalleRemito().getId()  + ", " +this.getCantidad()+ ", " + this.getTotal() + ", '" + this.getNumeroRemito() + "', " + this.getCliente().getId() + ");");
        ResultSet rs = bd.consulta("SELECT id FROM Deuda WHERE id_detalle = " + this.detalleRemito.getId() ) ;
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
    
    public static LinkedList getDeudasRemito(int idCliente){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id FROM Deuda WHERE id_cliente = " + idCliente + " and conrecibo = False ;"); 
        try{
            int id;
            while(rs.next()){
                id = rs.getInt("id");
                DeudaRemito deuda = new DeudaRemito(id);
                lista.add(deuda);
               }
            return lista;
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
        
     }
    public static LinkedList getDeudaXRemito(String nroRemito){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id_detalle FROM Deuda WHERE nro_remito = '" + nroRemito + "' and conrecibo = False ;"); 
        try{
            int id_detalle;
            while(rs.next()){
                id_detalle = rs.getInt("id_detalle");
                DeudaRemito deuda = new DeudaRemito(id_detalle);
                lista.add(deuda);
               }
            bd.cerrar();
            return lista;
            
        }catch(SQLException e ) {System.out.println(e.toString()); 
             bd.cerrar();
             return null; }
        
     }
    
    public void actualizarDeuda(DetalleFactura detalleFactura){
        //se le pasa un detalle de factura pagada, con referencia a un detalle de remito
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT * FROM Deuda WHERE id_cliente = " + detalleFactura.getFactura().getCliente().getId() + " and id_detalle = " + detalleFactura.getIdDetalleRemito() + " and conrecibo = False ;"); 
        try{
            rs.next();
            this.detalleRemito = new DetalleRemito(rs.getInt("id_detalle"));
            this.cliente = detalleFactura.getFactura().getCliente();
            this.setCantidad(rs.getInt("cantidad"));
            this.id = rs.getInt("id");
            this.numeroRemito = detalleRemito.getRemito().getNumero();
            this.total = rs.getFloat("total");
            
            //empieza la actualizacion
            if(detalleRemito.getEjemplar().getId() == detalleFactura.getEjemplar().getId() && detalleFactura.getCantidad() <= this.getCantidad()){
                System.out.println("entro, son iguales los ejemplares");
                if(detalleFactura.getCantidad() == this.getCantidad()){
                    //son iguales las cantidades, deuda cancelada totalmente, borrar el registro
                    //imprimo ambos totales por las deudas
                    System.out.println(this.total + " =  "  + (detalleFactura.getCantidad()*detalleRemito.getEjemplar().getPrecioUnitario()));
                    bd.insertar("DELETE FROM Deuda where id = " + this.id);
                    
                    
                }
                else{
                    //son cantidades distintas, se actualiza la deuda
                    int nuevaCant = this.getCantidad() - detalleFactura.getCantidad();
                    float nuevoTotal = this.total - (detalleFactura.getCantidad() * detalleFactura.getEjemplar().getPrecioUnitario());
                    
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

    public DetalleRemito getDetalleRemito() {
        return detalleRemito;
    }

    public void setDetalleRemito(DetalleRemito detalleRemito) {
        this.detalleRemito = detalleRemito;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNumeroRemito() {
        return numeroRemito;
    }

    public void setNumeroRemito(String numeroRemito) {
        this.numeroRemito = numeroRemito;
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
    
    
}
