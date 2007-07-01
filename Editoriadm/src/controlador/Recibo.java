/*
 * Recibo.java
 *
 * Created on 27 de marzo de 2007, 20:15
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
public class Recibo {
    
    private int id;
    private String numero;
    private String fecha;
    private Cliente cliente;
    private float total;
    
    
    /** Creates a new instance of Recibo */
    public Recibo(int id, String numero, String fecha, Cliente cliente, float total) {
        this.setId(id);
        this.setNumero(numero);
        this.setFecha(fecha);
        this.setCliente(cliente);
        this.setTotal(total);
    }
    
    public Recibo(int id){
        this.setId(id);
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Recibo where id = " + this.getId() );
        try {
            if(rs.next()){
                this.numero = rs.getString("numero");
                this.fecha = rs.getString("fecha");
                this.cliente = new Cliente(rs.getInt("id_cliente"));
                this.setTotal(rs.getFloat("total"));
                rs.close();
            }
            else {
                System.out.println("error al buscar un Recibo");
            } 
        } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("Error sql al buscar un Recibo en BD");
        }
        bd.cerrar();
    }
    
    //busca recibo por numero
    public Recibo(String numero){
        this.numero = numero;
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Recibo where numero  = '" + this.getNumero() +"' ;" );
        try {
            rs.next();
        
            this.setId(rs.getInt("id"));
            this.fecha = rs.getString("fecha");
            this.cliente = new Cliente(rs.getInt("id_cliente"));
            this.setTotal(rs.getFloat("total"));
            rs.close();
            
        } catch (SQLException e) {
                System.out.println(e.toString());
                e.printStackTrace();
                System.out.println("Error sql al buscar un Reciboen BD por numero");
                this.setId(0);
        }
        bd.cerrar();
    }
    
    public static boolean existeId(String numeroRecibo){
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Recibo where numero  = '" + numeroRecibo +"' ;" );
        try {
            if(rs.next()){
                bd.cerrar();
                return true;
            }
            bd.cerrar();
            return false;
                        
        } catch (SQLException e) {
                System.out.println(e.toString());
                e.printStackTrace();
                bd.cerrar();
                return false;
        }
    }
    
    public void hacerAlta(){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        //tuza, checkear la fecha!!!
        bd.insertar("INSERT INTO Recibo (numero, fecha, id_cliente, total) VALUES ('" +this.getNumero() + "', '" +this.getFecha()+ "', " + this.getCliente().getId()+ ", " + this.getTotal() + ");");
        ResultSet rs = bd.consulta("SELECT id FROM Recibo WHERE numero = '" +this.getNumero() +"';");
        try{
            rs.next();
            this.setId(rs.getInt("id"));
        }catch(SQLException e) {System.out.println("errorrrrrr insertando recibo en la BD");}
        bd.cerrar();
    }
    
    public void borrar(){
        LinkedList detalles = this.getDetalles();
        for(int i = 0; i < detalles.size(); i++){
            DetalleRecibo detalle = (DetalleRecibo)detalles.get(i);
            detalle.recrearDeudaFactura();
            detalle.borrar();
        }
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar("DELETE FROM Recibo WHERE id = " + this.id);
        bd.cerrar();
    }
    
    public static LinkedList getRecibos(int idCliente){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id FROM Recibo WHERE id_cliente = " + idCliente ); 
        try{
            int id; 
            while(rs.next()){ 
                id = rs.getInt("id"); 
                Recibo recibo= new Recibo(id);
                lista.add(recibo);
               }
            return lista; 
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
     } 
    
    public LinkedList getDetalles(){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id FROM DetalleRecibo where id_recibo = " + this.id); 
        try{
            int ide;
            while(rs.next()){
                ide = rs.getInt("id");
                DetalleRecibo detalleRecibo = new DetalleRecibo(ide);
                lista.add(detalleRecibo);
               }
            rs.close();
            bd.cerrar();
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
        return lista;
     }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
}
