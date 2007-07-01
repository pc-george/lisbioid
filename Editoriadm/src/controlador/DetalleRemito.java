/*
 * DetalleRemito.java
 *
 * Created on 7 de marzo de 2007, 18:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class DetalleRemito {
    
    private int id;
    private EjemplarLibro ejemplar;
    private int cantidad;
    private Remito remito;
        
    /** Creates a new instance of DetalleRemito */
    public DetalleRemito(EjemplarLibro ejemplar, int cantidad, Remito remito) {
        this.ejemplar = ejemplar;
        this.cantidad = cantidad;
        this.setRemito(remito);
        this.setId(0);
    }
    public DetalleRemito(int id){
        this.setId(id);
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from DetalleRemito where id = " + this.getId() );
        try {
            if(rs.next()){
        
                this.ejemplar = new EjemplarLibro(rs.getInt("id_ejemplar"));
                this.cantidad = rs.getInt("cantidad");
                this.remito = new Remito(rs.getInt("id_remito"));
                rs.close();
            }
            else {
            System.out.println("error al buscar un Remito");
            }
        } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("Error sql al buscar un Remito en BD");
        }
        bd.cerrar();
    }
    
    public void borrar(){
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar("DELETE FROM DetalleRemito WHERE id = " + this.id);
        bd.cerrar();
    }

    public EjemplarLibro getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(EjemplarLibro ejemplar) {
        this.ejemplar = ejemplar;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Remito getRemito() {
        return remito;
    }
   

    public void setRemito(Remito remito) {
        this.remito = remito;
    }
    public void hacerAlta(){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar("INSERT INTO DetalleRemito (id_ejemplar, cantidad, id_remito) VALUES (" +this.getEjemplar().getId() + ", " +this.getCantidad()+ ", " + this.getRemito().getId()+ ");");
        ResultSet rs = bd.consulta("SELECT id FROM DetalleRemito WHERE id_remito = " +this.getRemito().getId() +" and id_ejemplar = " + this.getEjemplar().getId() + ";");
        try{
            rs.next();
            this.setId(rs.getInt("id"));
        }catch(SQLException e) {System.out.println("errorrrrrr insertando remito en la BD");}
        bd.cerrar();
        //hace el alta de la deuda
        DeudaRemito deuda = new DeudaRemito(this);
        deuda.hacerAlta();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    
}
