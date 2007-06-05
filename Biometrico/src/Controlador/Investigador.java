/*
 * Investigador.java
 *
 * Created on 15 de febrero de 2007, 13:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Controlador;

/**
 *
 * @author PC
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import java.util.LinkedList;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;


public class Investigador {
    
    private int id;
    
    private String nombre;
    private String apellido;
    private int dni; //el dni deberia ser string, pero esta seteado en la BD como un int
    private String telefono;
    private String telefono2;
    private String observacion;
    private String email;
    private String codigo;
    private String pathHuella;
    private String pathFoto;
    private BufferedImage foto;
 
    private boolean esEncargado;
    private Asistencia asistencia;
    
    private boolean logeado;
    
    
    
    /** Creates a new instance of Investigador */
    public Investigador(int id, String nombre, String apellido, int dni, String telefono, String telefono2, String email, String codigo, String observacion, boolean esEncargado ) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.codigo = codigo;
        this.logeado = false;
        
        this.setTelefono2(telefono2);
        this.observacion = observacion;
        this.esEncargado = esEncargado;
        this.asistencia = null;
        
        this.pathFoto = "";
        this.pathHuella = "";
    }
    
    //constructor que nos crea un investigador a partir de cierto id que se encuentra en la base de datos
    public Investigador(int id) {
        this.id = id;
        ControladorBaseDeDatos conLIS = new ControladorBaseDeDatos(Main.baseLIS);
        ResultSet rs = conLIS.consulta("select * from Investigadores where invId = " + this.id );
        try {
            if(rs.next()){
        
            this.nombre = rs.getString("invNombre");
            this.apellido = rs.getString("invApellido");
            this.dni = rs.getInt("invNDoc");
            this.codigo = rs.getString("invCodigo");
            this.telefono = rs.getString("invTE");
            this.setTelefono2(rs.getString("invTE2"));
            this.email = rs.getString("invEMail");
            this.pathFoto = rs.getString("pathFoto");
            this.pathHuella =rs.getString("pathHuella");
            this.esEncargado = rs.getBoolean("esEncargado");
            rs.close();
            this.asistencia = null;
        }
        else {
            System.out.println("error al buscar un investigador por id");
        } } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("Error sql al buscar un investigador en BD");
        }
        conLIS.cerrar();
    }
    //este constructor crea un investigador en base a un DNI de la BD
    public Investigador(String parametro) {
        ResultSet rs;
        ControladorBaseDeDatos conLIS = new ControladorBaseDeDatos(Main.baseLIS);
        if (parametro.length() == 12){ 
            //se pasó el numero de código como param
            //esto es solo para compatibilidad hacia atras...maldicion!
            rs = conLIS.consulta("select * from Investigadores where invCodigo = '" + parametro + "' ;");
        }
        else{
            int dni;
            try{
                //entonces se paso el dni como parametro...y se parsea...bazofia total
                dni = Integer.parseInt(parametro);
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Formato invalido de DNI.\nEl dni debe contener solamente números", "Error", JOptionPane.ERROR_MESSAGE);
                
                return;
            }
            rs = conLIS.consulta("select * from Investigadores where invNDoc = " + dni);
        }
        try{
                if(rs.next()){
                    this.id = rs.getInt("invId");
                    this.nombre = rs.getString("invNombre");
                    this.apellido = rs.getString("invApellido");
                    this.dni = rs.getInt("invNDoc");
                    this.telefono = rs.getString("invTE");
                    this.setTelefono2(rs.getString("invTE2"));
                    this.email = rs.getString("invEMail");
                    this.codigo = rs.getString("invCodigo");
                    this.pathFoto = rs.getString("pathFoto");
                    this.pathHuella =rs.getString("pathHuella");
                    this.esEncargado = rs.getBoolean("esEncargado");
                }
                else{
                   JOptionPane.showMessageDialog(null, "DNI o Código no encontrado en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error sql al buscar un investigador en la BD\n" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
             conLIS.cerrar();
        }
    
       
    //constructor que crea un investigador en blanco y deslogeado
    public Investigador() {
        this.logeado = false;
             
    }
    
    public void hacerAlta(){
        ControladorBaseDeDatos conLIS = new ControladorBaseDeDatos(Main.baseLIS);
        
        conLIS.insertar("INSERT INTO Investigadores (invNombre, invApellido, invNDoc, invTE, invTE2, invEMail, invObservaciones, invTDoc, invCodigo, esEncargado) VALUES ('" +this.nombre + "', '" +this.apellido + "', " + this.dni + ", '" + this.telefono + "','" + this.getTelefono2() + "', '" +this.email + "', '" +this.observacion + "', 'DNI', '" + this.codigo + "', " + this.esEncargado +");");
        ResultSet rs = conLIS.consulta("SELECT invId FROM Investigadores WHERE invNDoc = " +this.dni  );
        try{
            rs.next();
            this.id = rs.getInt("invId");
        }catch(SQLException e) {System.out.println("errorrrrrr");}
        conLIS.cerrar();
        
    }
    
   public String getPathFoto(){
        return pathFoto;
    }
   public boolean getEsEncargado(){
       return esEncargado;
   }
   public void setEsEncargado(boolean esEncargado){
       this.esEncargado = esEncargado;
   }
    public boolean setPathFoto(String pathF){
        ControladorBaseDeDatos conLIS = new ControladorBaseDeDatos(Main.baseLIS);
        if(conLIS.insertar("update Investigadores set pathFoto = '" + pathF + "' where invId = " + this.id)){
            this.pathFoto = pathFoto;
        }
        else{
            conLIS.cerrar();
            return false;
        }
        conLIS.cerrar();
        return true;
    }
    public String getPathHuella(){
        return pathHuella;
    }
    public boolean setPathHuella(String pathH){
        ControladorBaseDeDatos conLIS = new ControladorBaseDeDatos(Main.baseLIS);
        if (conLIS.insertar("update Investigadores set pathHuella = '" + pathH + "' where invId = " + this.id )){
            this.pathHuella = pathH;
        }
        else{
            conLIS.cerrar();
            return false;
        }
        conLIS.cerrar();
        return true;
    }
    public void setEstado(boolean estado){
        this.logeado = estado;
    }
    public boolean getEstado(){
        return this.logeado;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getApellido(){
        return apellido;
    }
    public void setApellido(String apellido){
        this.apellido = apellido;
    }
   
    public String getArchivo(){
        return nombre + apellido;
    }
    public int getDni(){
        return dni;
    }
    public String getEmail(){
        return email;
    }
    public String getTelefono(){
        return telefono;
    }
    public String getCodigo(){
        return codigo;
    }
    public int getId(){
        return id;
    }
    public BufferedImage getFoto(){
        return this.foto;
    }
    public void setFoto(BufferedImage foto){
        this.foto = foto;
    }
    
    public boolean registrarMovimiento(){
        if(this.asistencia == null){
            this.asistencia = new Asistencia(this); //el constructor marca la entrada
            return true;
        }
        else{
            this.asistencia.salida();
            return false;
        }
    }
    
   
    
    public String getHoraEntrada(){
        return this.asistencia.getHoraEntrada();
    }
    
    public String getHoraSalida(){
        return this.asistencia.getHoraSalida();
    }
    
    
    public String getObservaciones(){
        ControladorBaseDeDatos conLIS = new ControladorBaseDeDatos(Main.baseLIS);
        ResultSet rs = conLIS.consulta("SELECT invObservaciones FROM Investigadores where invId = " + this.id ); 
        try{
            if(rs.next()){
            return rs.getString("invObservaciones");
            
            }
        }catch (SQLException e) {System.out.println("error sql en observaciones");}
        conLIS.cerrar();
        return "";
    }
    
    public static LinkedList getInvestigadoresLogeados(){
        //modificar esto despues...es circular
        LinkedList lista = new LinkedList();
        LinkedList asist = Asistencia.getAsistenciasPendientes();
        for(int i = 0; i < asist.size(); i++){
            lista.add(((Asistencia)asist.get(i)).getInvestigador());
        }
        return lista;
    }
    
    public static LinkedList getListaInvestigadores(){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos conLIS = new ControladorBaseDeDatos(Main.baseLIS);
        ResultSet rs = conLIS.consulta("SELECT invId FROM Investigadores where desactivado <> TRUE ;"); 
        try{
            int id;
            while(rs.next()){
                id = rs.getInt("invId");
                Investigador inv = new Investigador(id);
                lista.add(inv);
               }
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
        return lista;
    }
    public void borrarInvestigador(){
        ControladorBaseDeDatos conLIS = new ControladorBaseDeDatos(Main.baseLIS);
        conLIS.insertar( " UPDATE Investigadores SET desactivado = TRUE, pathHuella = '', pathFoto = '' WHERE invId = " + this.id ); 
        
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }
    
    public void modificarInvestigador(int ide){
        ControladorBaseDeDatos conLIS = new ControladorBaseDeDatos(Main.baseLIS);
        conLIS.insertar( "UPDATE Investigadores SET invNombre = '" + this.getNombre() + "', invApellido = '" + this.getApellido() + "', invTE = '" + this.getTelefono() + "', invTE2 = '" + this.getTelefono2() + "', invNDoc = " + this.getDni() + ", invCodigo = '" + this.getCodigo() + "', invEMail = '" + this.getEmail() + "', invObservaciones = '" + this.getObservaciones() + "', esEncargado = "  + this.getEsEncargado() + " WHERE invId = " + ide ); 
        conLIS.cerrar();
    }
    
    public void setAsistencia(Asistencia asist){
        this.asistencia = asist;
    }
   
}
