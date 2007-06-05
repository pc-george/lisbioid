/*
 * Asistencia.java
 *
 * Created on 31 de mayo de 2007, 10:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.TimeZone;

/**
 *
 * @author mfrias
 */
public class Asistencia {
    
    private Investigador inv;
    private int id;
    private String horaEntrada;
    private String horaSalida;
    
    public Asistencia(Investigador inv ){
        this.inv = inv;
        entrada(); //setea la id
        
    }
    
    public Asistencia(int id) {
        this.id = id;
        ControladorBaseDeDatos conLIS = new ControladorBaseDeDatos(Main.baseLIS);
        ResultSet rs = conLIS.consulta("select * from Asistencias where asiId= " + this.id );
        try {
            if(rs.next()){
        
            this.horaEntrada = rs.getString("asiHEntrada");
            this.horaSalida = rs.getString("asiHSalida");
            this.inv = new Investigador(rs.getInt("asiInvId"));
            rs.close();
            
        }
        else {
            System.out.println("error al buscar una asistencia por id");
        } } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("Error sql al buscar una asistencia en BD");
        }
        conLIS.cerrar();
    }
    
    public void salida(){
        ControladorBaseDeDatos conLIS = new ControladorBaseDeDatos(Main.baseLIS);
        conLIS.insertar("UPDATE Asistencias SET asiHSalida = Time() where asiId = " + this.id + ";");
        inv.setEstado(false);
        this.horaSalida = getHora("");
        conLIS.cerrar();
        
    }
    
    public void entrada(){
        ControladorBaseDeDatos conLIS = new ControladorBaseDeDatos(Main.baseLIS);
        conLIS.insertar("INSERT INTO Asistencias (asiHEntrada, asiFecha, asiCodigo, asiInvId) VALUES (Now(), Date(), '" + inv.getCodigo()+  "', '" + inv.getId() + "' );");
        ResultSet rs =  conLIS.consulta("select @@identity AS id from Asistencias;");
        //conLIS.consulta("SELECT asiHEntrada, asiHSalida, asiId FROM Asistencias where asiFecha = Date() and asiInvId = " + this.id ); 
        try{
            rs.next();
            this.id = rs.getInt("id");
            System.out.println(id);
            
        }catch (SQLException e){
            System.out.println(e.toString());
        }
        this.inv.setEstado(true);
        this.horaEntrada = getHora("");
        conLIS.cerrar();
        
    }
    
    public static LinkedList getAsistenciasPendientes(){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos conLIS = new ControladorBaseDeDatos(Main.baseLIS);
        ResultSet rs = conLIS.consulta("SELECT * FROM Asistencias WHERE asiFecha = Date() ;"); 
        try{
            String salida;
            int id; 
                        
            while(rs.next() != false){
                salida = rs.getString("asiHSalida");
                if( salida == null || salida.compareTo("null") == 0 || salida.compareTo("") == 0){ 
                    Asistencia a = new Asistencia(rs.getInt("asiId")); //esto es circular...mejorar
                    Investigador inv = a.getInvestigador();
                    inv.setAsistencia(a); // aca
                    inv.setEstado(true);
                    lista.add(a);
               }
            }                  
            return lista;
        }
        catch (SQLException e){e.printStackTrace();};
        return null;
    }
    
    public Investigador getInvestigador(){
        return this.inv;
        
    }
    
     public static String getHora(String fecha) {
      String hora;
      if (fecha.compareTo("") == 0) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        String FORMATOHORA = "HH:mm:ss";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(FORMATOHORA);
        hora = sdf.format(cal.getTime());
      }
      else { 
        String[] temp = fecha.split(" ");
        hora  = temp[1];
      }
      return hora;
    }
    
    
    
    public String getHoraEntrada(){
        return this.horaEntrada;
    }
    
    public String getHoraSalida(){
        return this.horaSalida;
    }
    public void setHoraEntrada(String hora){
        this.horaEntrada = hora;
    }
    public void setHoraSalida(String hora){
        this.horaSalida = hora;
    }
    
}
