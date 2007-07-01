/*
 * Remito.java
 *
 * Created on 7 de marzo de 2007, 17:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package controlador;

/**
 *
 * @author PC
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Remito {
    
    private int id;
    private String numero;
    private String fecha;
    private Cliente cliente;
    private boolean completo;
    private float total;
    
    /** Creates a new instance of Remito */
    public Remito(String numero, String fecha, Cliente cliente, float total) {
        this.setId(0);
        this.numero = numero;
        this.fecha = fecha;
        this.cliente = cliente;
        this.setCompleto(false);
        this.setTotal(total);
    }
    
    public Remito(int id){
        this.setId(id);
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Remito where id = " + this.getId() );
        try {
            if(rs.next()){
        
            this.numero = rs.getString("numero");
            this.fecha = rs.getString("fecha");
            this.cliente = new Cliente(rs.getInt("id_cliente"));
            this.setCompleto(rs.getBoolean("completo"));
            this.setTotal(rs.getFloat("total"));
            rs.close();
        }
        else {
            System.out.println("error al buscar un Remito");
        } } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("Error sql al buscar un Remito en BD");
        }
        bd.cerrar();
    }
    
    //busca remito por numero
    public Remito(String numero){
        this.numero = numero;
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Remito where numero  = '" + this.getNumero() +"' ;" );
        try {
            rs.next();
        
            this.setId(rs.getInt("id"));
            this.fecha = rs.getString("fecha");
            this.cliente = new Cliente(rs.getInt("id_cliente"));
            this.setCompleto(rs.getBoolean("completo"));
            this.setTotal(rs.getFloat("total"));
            rs.close();
            
        } catch (SQLException e) {
                System.out.println(e.toString());
                e.printStackTrace();
                System.out.println("Error sql al buscar un Remito en BD por numero");
                this.setId(0);
                
        }
        bd.cerrar();
}

    public String getNumero() {
        return numero;
    }
    
    public static boolean existeId(String numeroRemito){
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Remito where numero  = '" + numeroRemito +"' ;" );
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
                //System.out.println("Error sql al buscar un Remito en BD por numero");bd.cerrar();
                bd.cerrar();
                return false;
        }
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
    public void hacerAlta(){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        //tuza, checkear la fecha!!!
        bd.insertar("INSERT INTO Remito (numero, fecha, id_cliente, total) VALUES ('" +this.getNumero() + "', '" +this.getFecha()+ "', " + this.getCliente().getId()+ ", " + this.getTotal() + ");");
        ResultSet rs = bd.consulta("SELECT id FROM Remito WHERE numero = '" +this.getNumero() +"';");
        try{
            rs.next();
            this.setId(rs.getInt("id"));
        }catch(SQLException e) {System.out.println("errorrrrrr insertando remito en la BD");}
        bd.cerrar();
    }

    public int getId() {
        return id;
    }
    
    public void setCompletoBackwards(boolean valor){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar("UPDATE Remito SET completo = " + valor + " WHERE id = " + this.getId());
        bd.cerrar();
        
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleto() {
        return completo;
    }

    public void setCompleto(boolean completo) {
        this.completo = completo;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
    public void borrar(){
        LinkedList detalles = this.getDetalles();
        for(int i = 0; i < detalles.size(); i++){
            DetalleRemito detalle = (DetalleRemito)detalles.get(i);
            DeudaRemito dr = new DeudaRemito(detalle.getId());
            dr.borrar();
            detalle.borrar();
        }
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar("DELETE FROM Remito WHERE id = " + this.id);
        bd.cerrar();
    }
    
    public boolean tieneFacturasAsociadas(){
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        LinkedList detalles = this.getDetalles();
        Integer[] idDetalles = new Integer[detalles.size()];
        for(int i = 0 ; i < detalles.size(); i++){
            DetalleRemito dr = (DetalleRemito) detalles.get(i);
            ResultSet rs = bd.consulta("SELECT * FROM DetalleFactura WHERE id_detalle_remito = " + dr.getId());
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
     
     
    public static LinkedList getRemitosPendientes(int idCliente){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id FROM Remito WHERE id_cliente = " + idCliente + " and completo = False;"); 
        try{
            int id; 
            while(rs.next()){ 
                id = rs.getInt("id"); 
                Remito remito = new Remito(id);
                lista.add(remito);
               }
            return lista; 
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
        
     } 
    public static LinkedList getRemitos(int idCliente){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id FROM Remito WHERE id_cliente = " + idCliente ); 
        try{
            int id; 
            while(rs.next()){ 
                id = rs.getInt("id"); 
                Remito remito = new Remito(id);
                lista.add(remito);
               }
            return lista; 
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
        
     } 
     public LinkedList getDetalles(){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id FROM DetalleRemito where id_remito = " + this.id); 
        try{
            int ide;
            while(rs.next()){
                ide = rs.getInt("id");
                DetalleRemito detalleRemito = new DetalleRemito(ide);
                lista.add(detalleRemito);
               }
            rs.close();
            bd.cerrar();
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
        return lista;
     }
    
}
