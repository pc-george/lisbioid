/*
 * EjemplarLibro.java
 *
 * Created on 7 de marzo de 2007, 18:04
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

        
public class EjemplarLibro {
    
    private int id;
    private Libro libro;
    private float precioUnitario;
    
    /** Creates a new instance of EjemplarLibro */
    public EjemplarLibro(Libro libro, float precio) {
        this.libro = libro;
        this.precioUnitario = precio;
        this.id = 0;
    }
    
    public EjemplarLibro(int id){
        this.setId(id);
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Ejemplar where id = " + this.getId() );
        try {
            if(rs.next()){
        
                this.libro = new Libro(rs.getInt("id_libro"));
                this.precioUnitario = rs.getFloat("precio");
                
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
    
    public void hacerAlta(){
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT * from Ejemplar where id_libro = " + this.libro.getId() +" and precio = " + this.getPrecioUnitario() + " ;" );
        try{
            rs.next();
            this.id = rs.getInt("id");
            rs.close();
        }catch(SQLException e ){
            bd.insertar("INSERT INTO Ejemplar (id_libro, precio) values (" + this.getLibro().getId()+ ", " + this.getPrecioUnitario() + ");");
            ResultSet rsa = bd.consulta("SELECT * from Ejemplar where id_libro = " + this.libro.getId() + " and precio = " + this.getPrecioUnitario() + " ;");
            try{
                rsa.next();
                this.setId(rsa.getInt("id"));
                rsa.close();
            } catch (SQLException er){
                System.out.println("Error en ejemplar de libro\n" + e.toString());
            }
        }
        bd.cerrar();
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
