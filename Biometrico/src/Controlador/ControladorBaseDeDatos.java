/*
 * ControladorBaseDeDatos.java
 *
 * Created on 15 de febrero de 2007, 12:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Controlador;

/**
 * Se encarga de centralizar todos los accesos a la base de datos.
 * 
 * @author Ing. Valerio Frittelli
 * @version Octubre de 2004
 * @modificada por Martin Farias para LISBioID del Labopratorio de Investigacion de Software
 * @Febrero de 2007
 *
 */

import java.sql.*;

public class ControladorBaseDeDatos
{
    private Connection con;
    private Statement  st;
    private ResultSet  res;

     
    public ControladorBaseDeDatos(String baseDato)
    {
         try
         {
               String base = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + baseDato;
               Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
               con = DriverManager.getConnection(base);
               st = con.createStatement();
         }
    
         catch(Exception e)
         {
               System.out.println("Error general: " + e.getMessage());
         }
    }
    
    
    public ResultSet consulta( String query )
    {
        try
        {
            res = st.executeQuery( query );
        }
        catch( Exception e )
        {
            res = null;
            System.out.println( e.toString()  + "\n" + "En consulta" );
            e.printStackTrace();
        }
        
        return res;
    }

    public ResultSet getResult() {
        return res;
    }

    public boolean insertar( String query )
    {
        try
        {
            st.executeUpdate( query );
        }
        catch( Exception e )
        {
            System.out.println( e.toString() );
            return false;
        }
        
        return true;
    }
    
    public void cerrar()
    {
        try
        {
            st.close();
            con.close();
        }
        
        catch(Exception e)
        {
            System.out.println("Error al cerrar las bases de datos: "  +  e.getMessage());   
        }
    }
}
    
    
    

