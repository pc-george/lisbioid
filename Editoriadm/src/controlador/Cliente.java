/*
 * Cliente.java
 *
 * Created on 7 de marzo de 2007, 17:45
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

public class Cliente {
    
    private String nombre;
    private String direccion;
    private String encargado;
    private String cuil;
    private String telefono;
    //private float comision;
    private String email;
    private int id;
    
    
    /** Creates a new instance of Cliente */
    public Cliente(int id, String nombre, String direccion, String encargado, String cuil, String telefono, String email) {
        this.setId(id);
        this.setNombre(nombre);
        this.setDireccion(direccion);
        this.setEncargado(encargado);
        this.setCuil(cuil);
        this.setTelefono(telefono);
        //this.setComision(comision);
        this.setEmail(email);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void hacerAlta(){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        
        bd.insertar("INSERT INTO Cliente(nombre, direccion, telefono, email, encargado, cuil ) VALUES ('" +this.getNombre() + "', '" +this.getDireccion()+ "', '" + this.getTelefono()+ "', '" + this.getEmail()+ "','"  +this.getEncargado() + "', '" + this.getCuil()+ "');");
        ResultSet rs = bd.consulta("SELECT id FROM Cliente WHERE nombre = '" +this.getNombre()+"';" );
        try{
            rs.next();
            this.setId(rs.getInt("id"));
        }catch(SQLException e) {System.out.println("errorrrrrr");}
        bd.cerrar();
    }
    
    public Cliente (int id) {
        this.setId(id);
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Cliente where id = " + this.getId() );
        try {
            if(rs.next()){
        
            this.setNombre(rs.getString("nombre"));
            this.setDireccion(rs.getString("direccion"));
            this.setEncargado(rs.getString("encargado"));
            this.setEmail(rs.getString("email"));
            this.setTelefono(rs.getString("telefono"));
            this.setCuil(rs.getString("cuil"));
            //this.setComision(rs.getFloat("comision"));
            
            rs.close();
            
        }
        else {
            System.out.println("error al buscar un cliente");
        } } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("Error sql al buscar un Cliente en BD");
        }
        bd.cerrar();
    }
    
    public Cliente (String nombre) {
        this.setNombre(nombre);
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Cliente where nombre= '" + this.getNombre() + "';" );
        try {
            if(rs.next()){
        
            this.setId(rs.getInt("id"));
            this.setDireccion(rs.getString("direccion"));
            this.setEncargado(rs.getString("encargado"));
            this.setEmail(rs.getString("email"));
            this.setTelefono(rs.getString("telefono"));
            this.setCuil(rs.getString("cuil"));
            //this.setComision(rs.getFloat("comision"));
            
            rs.close();
            
        }
        else {
            System.out.println("error al buscar un cliente");
        } } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("Error sql al buscar un Cliente en BD");
        }
        bd.cerrar();
    }
    
    public void modificarCliente(){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar( "UPDATE Cliente SET nombre = '" + this.getNombre() + "', direccion = '" + this.getDireccion() + "', encargado = '" + this.getEncargado() + "', telefono= '" + this.getTelefono() + "' , email= '" + this.getEmail() + "', cuil = '" + this.getCuil() + "'  WHERE id = " + this.id ); 
        bd.cerrar();
    }
    
    public void borrar(){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar( " UPDATE Cliente SET desactivado = TRUE, WHERE id = " + this.getId() ); 
        bd.cerrar();
    }
     
     public static LinkedList getClientes(){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id FROM Cliente WHERE desactivado = False "); 
        try{
            int id;
            while(rs.next()){
                id = rs.getInt("id");
                Cliente cliente = new Cliente(id);
                lista.add(cliente);
               }
        }catch(SQLException e ) {System.out.println(e.toString() + "\ntuzatuza"); return null; }
        return lista;
     }
     public LinkedList getDeudas(){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id_detalle, conrecibo FROM Deuda WHERE id_cliente = " + this.id + " ORDER BY nro_remito;"); 
        try{
            int id_detalle;
            boolean conRecibo;
            while(rs.next()){
                id_detalle = rs.getInt("id_detalle");
                conRecibo = rs.getBoolean("conrecibo");
                if(conRecibo){
                    DeudaFactura deuda = new DeudaFactura(id_detalle);
                    lista.add(deuda);
                }
                else{
                    DeudaRemito deuda = new DeudaRemito(id_detalle);
                    lista.add(deuda);
                }
                
           }
        }catch(SQLException e ) {System.out.println(e.toString() + "\nerror en deuda  x cliente"); return null; }
        return lista;
     }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
