/*
 * Libro.java
 *
 * Created on 7 de marzo de 2007, 17:45
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
public class Libro {
    
    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private int edicion;
    private String ano;
    private float precio;
    private boolean desactivado;
    
    /** Creates a new instance of Libro */
    public Libro(int id, String titulo, String autor, String isbn, String ano, int edicion, float precio) {
        this.setId(id);
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.ano = ano;
        this.edicion = edicion;
        this.precio = precio;
        this.desactivado = false;
        
    }
    

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getEdicion() {
        return edicion;
    }

    public void setEdicion(int edicion) {
        this.edicion = edicion;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    public Libro(int id) {
        this.setId(id);
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Libro where id = " + this.getId() );
        try {
            if(rs.next()){
        
            this.titulo = rs.getString("titulo");
            this.autor = rs.getString("autor");
            this.ano= rs.getString("ano");
            this.edicion = rs.getInt("edicion");
            this.isbn= rs.getString("isbn");
            this.precio = rs.getFloat("precio");
            this.desactivado = rs.getBoolean("desactivado");
            
            rs.close();
            
        }
        else {
            System.out.println("error al buscar un Libro");
        } } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("Error sql al buscar un Libro en BD");
        }
        bd.cerrar();
    }
    
    //busca un titulo por el titulo 
    public Libro(String titulo) {
        this.setTitulo(titulo);
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Libro where titulo = '" + this.getTitulo() + "';");
        try {
            if(rs.next()){
        
            this.id = rs.getInt("id");
            this.autor = rs.getString("autor");
            this.ano= rs.getString("ano");
            this.edicion = rs.getInt("edicion");
            this.isbn= rs.getString("isbn");
            this.precio = rs.getFloat("precio");
            this.desactivado = rs.getBoolean("desactivado");
            
            rs.close();
            
        }
        else {
            System.out.println("error al buscar un Libro");
        } } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("Error sql al buscar un Libro en BD");
        }
        bd.cerrar();
    }
    
    public void borrar(){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar( " UPDATE Libro SET desactivado = True, WHERE id = " + this.getId() ); 
    }
    
    public static LinkedList getLibros(){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id FROM Libro WHERE desactivado = False;"); 
        try{
            int id;
            while(rs.next()){
                id = rs.getInt("id");
                Libro libro = new Libro(id);
                lista.add(libro);
               }
            rs.close();
            bd.cerrar();
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
        return lista;
     }
    public void hacerAlta(){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar("INSERT INTO Libro(titulo, autor, isbn, edicion, ano, precio) VALUES ('" +this.getTitulo() + "', '" +this.getAutor()+ "', '" + this.getIsbn()+ "',"  +this.getEdicion()+ ", '" +this.getAno()+"', " + this.getPrecio()+ ");");
        ResultSet rs = bd.consulta("SELECT id FROM Libro WHERE titulo = '" +this.getTitulo() + "';" );
        try{
            rs.next();
            this.setId(rs.getInt("id"));
        }catch(SQLException e) {System.out.println("errorrrrrr insertando libro en la BD");
            e.printStackTrace();}
        bd.cerrar();
    }
    public void modificarLibro(){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar( "UPDATE Libro SET titulo = '" + this.getTitulo() + "', autor = '" + this.getAutor() + "', ano = '" + this.getAno() + "', edicion = " + this.getEdicion() + ", isbn= '" + this.getIsbn() + "', precio = " + this.getPrecio() + " WHERE id = " + this.id ); 
        bd.cerrar();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
