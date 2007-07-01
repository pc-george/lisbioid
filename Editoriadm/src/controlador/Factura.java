/*
 * Factura.java
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
import java.util.NoSuchElementException;

public class Factura {
    
    protected int id;
    protected String numero;
    protected String tipo;
    protected String fecha;
    protected Cliente cliente;
    protected float total;
    protected boolean pagado;
    protected float comision;
    private boolean completo;
    
    
    /** Creates a new instance of Factura */
    public Factura(String numero, String tipo, String fecha, Cliente cliente, float comision , boolean pagado ) {
        this.setId(0);
        this.numero = numero;
        this.tipo = tipo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.setTotal(0);
        this.setPagado(pagado);
        this.setComision(comision);
    }
    
    public Factura(int id){
        this.setId(id);
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Factura where id = " + this.getId() + ";");
        try {
            if(rs.next()){
        
            this.numero = rs.getString("numero");
            this.tipo = rs.getString("tipo");
            this.fecha = rs.getString("fecha");
            this.cliente = new Cliente(rs.getInt("id_cliente"));
            this.setTotal(rs.getFloat("total"));
            this.setPagado(rs.getBoolean("pagado"));
            this.setComision(rs.getFloat("comision"));
            this.setCompleto(rs.getBoolean("completo"));
            rs.close();
        }
        else {
            System.out.println("error al buscar una Factura");
        } } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("Error sql al buscar una Factura en BD");
        }
        bd.cerrar();
    }
    public Factura(String tipo, String numero){
        this.numero = numero;
        this.tipo = tipo;
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Factura where numero = '" + this.numero + "' and tipo = '" + this.tipo +"' ;");
        try {
            if(rs.next()){
        
            this.setId(rs.getInt("id"));
            this.fecha = rs.getString("fecha");
            this.cliente = new Cliente(rs.getInt("id_cliente"));
            this.setTotal(rs.getFloat("total"));
            this.setPagado(rs.getBoolean("pagado"));
            this.setComision(rs.getFloat("comision"));
            this.setCompleto(rs.getBoolean("completo"));
            
                      
            rs.close();
            
        }
        else {
            System.out.println("error al buscar una Factura");
        } } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("Error sql al buscar una Factura en BD");
        }
        bd.cerrar();
}
    
    public void hacerAlta(){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        //tuza, checkear la fecha!!!
        bd.insertar("INSERT INTO Factura (numero, tipo, fecha, id_cliente, total, comision, pagado) VALUES ('" +this.getNumero() + "', '" +this.getTipo()+ "', '" + this.getFecha()+ "'," + this.getCliente().getId() +", " + this.getTotal() + ", " + this.getComision() + " , True );");
        ResultSet rs = bd.consulta("SELECT id FROM Factura WHERE numero = '" +this.getNumero() +"' and tipo = '" + this.getTipo() + "';");
        try{
            rs.next();
            this.setId(rs.getInt("id"));
        }catch(SQLException e) {System.out.println("errorrrrrr insertando remito en la BD");}
        bd.cerrar();
    }
    public float calcularTotal(){
        int i;
        float temp = 0;
        LinkedList detalleFactura = this.getDetalles();
        DetalleFactura detalle;
        try{
            while((detalle = (DetalleFactura)detalleFactura.removeFirst()) != null){
                temp = detalle.getEjemplar().getPrecioUnitario() * detalle.getCantidad();
            }
            
       }catch(NoSuchElementException e ){
           System.out.println("Error: " + e.toString());
       }
       this.setTotal(temp);
       return temp;
    }
    
    public float calcularComision(float comision){
        return this.getTotal() * ((Float)(comision / 100));
    }
    
    public static LinkedList getFacturas(int idCliente){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id, pagado FROM Factura WHERE id_cliente = " + idCliente + " ;"); 
        try{
            int id;
            boolean pag;
            while(rs.next()){
                id = rs.getInt("id");
                //pag = rs.getBoolean("pagado");
                Factura factura;
                //if(pag){
                    factura = new Factura(id);
                //}
                //else{
                 //   factura = new FacturaConRecibo(id);
                //}
                
                lista.add(factura);
               }
            return lista;
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
        
     }
    public static boolean existeId(String numeroFactura, String tipo){
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from Factura where numero  = '" + numeroFactura+"' and tipo = '" + tipo + "';" );
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
    
    public LinkedList getDetalles(){
        LinkedList lista = new LinkedList();
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("SELECT id FROM DetalleFactura where id_factura=" + this.getId() + " ;"); 
        try{
            int ide;
            while(rs.next()){
                ide = rs.getInt("id");
                DetalleFactura detalleFactura = new DetalleFactura(ide);
                lista.add(detalleFactura);
               }
        }catch(SQLException e ) {System.out.println(e.toString()); return null; }
        return lista;
     }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
    
    public float getTotal(){
        return this.total;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }
    
    public void borrar(){
        LinkedList detalles = this.getDetalles();
        for(int i = 0; i < detalles.size(); i++){
            DetalleFactura detalle = (DetalleFactura)detalles.get(i);
            detalle.recrearDeudaRemito(); //recrea la deuda que este detalle habia cancelado
            detalle.borrar();
        }
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar("DELETE FROM Factura WHERE id = " + this.getId());
        bd.cerrar();
    }
    
    public void setCompletoBackwards(boolean valor){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar("UPDATE Factura SET completo = " + valor + " WHERE id = " + this.getId());
        bd.cerrar();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTotal(float total) {
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        this.total = total;
        bd.insertar("UPDATE Factura SET total  = " + this.getTotal() + " WHERE id = " + this.id );
        bd.cerrar();                
    }

    public float getComision() {
        return comision;
    }

    public void setComision(float comision) {
        this.comision = comision;
    }

    public boolean isCompleto() {
        return completo;
    }

    public void setCompleto(boolean completo) {
        this.completo = completo;
    }
   
    
}
