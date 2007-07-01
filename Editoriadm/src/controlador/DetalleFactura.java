/*
 * DetalleFactura.java
 *
 * Created on 7 de marzo de 2007, 18:02
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
public class DetalleFactura {
    
    private int id;
    private Factura factura;
    private EjemplarLibro ejemplar;
    private int cantidad;
    private int idDetalleRemito ; //cuando ides 0, entonces es una factura que la pagaran con recibo
    
    /** Creates a new instance of DetalleFactura */
    public DetalleFactura(EjemplarLibro ejemplar, int cantidad, Factura factura, int idDetalleRemito) {
        this.ejemplar = ejemplar;
        this.cantidad = cantidad;
        this.setFactura(factura);
        this.setId(0);
        this.setIdDetalleRemito(idDetalleRemito);
    }
    public DetalleFactura(int id){
        this.setId(id);
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from DetalleFactura where id = " + this.getId() );
        try {
            if(rs.next()){
        
                this.ejemplar = new EjemplarLibro(rs.getInt("id_ejemplar"));
                this.cantidad = rs.getInt("cantidad");
                this.factura = new Factura(rs.getInt("id_factura"));
                this.setIdDetalleRemito(rs.getInt("id_detalle_remito"));
                rs.close();
            }
            else {
            System.out.println("error al buscar un Detalle de factura");
            }
        } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("Error sql al buscar un Detalle de factura en BD");
        }
        bd.cerrar();
    }
    public void hacerAlta(){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar("INSERT INTO DetalleFactura (id_ejemplar, cantidad, id_factura, id_detalle_remito) VALUES (" +this.getEjemplar().getId() + ", " +this.getCantidad()+ ", " + this.getFactura().getId()+ " , " + this.getIdDetalleRemito() + " );");
        ResultSet rs = bd.consulta("SELECT id FROM DetalleFactura WHERE id_factura = " +this.getFactura().getId() +" and id_ejemplar = " + this.getEjemplar().getId() + " and cantidad = " + this.getCantidad() + ";");
        try{
            rs.next();
            this.setId(rs.getInt("id"));
        }catch(SQLException e) {System.out.println("errorrrrrr insertando detalle factura en la BD");}
        
        
        //hace el alta de la deuda
        if(this.idDetalleRemito == 0){
            //se crea una deuda de FACTURA nueva, en base a esta factura todavia no pagada
            DeudaFactura deuda = new DeudaFactura(this);
            deuda.hacerAlta();
        }
        else{
            //quiere decir que esta factura se paga, tiene un remito, y cancela una deuda anterior
            DeudaRemito deuda = new DeudaRemito(this.idDetalleRemito);
            System.out.println(this.idDetalleRemito);
            deuda.actualizarDeuda(this);
            
            //aca checkeo que el detalle borrado, no haya dejado al remito "pagado"
            //es decir, que sea el ultimo detalle del remito
            String nroRemito = new DetalleRemito(this.idDetalleRemito).getRemito().getNumero();
            int iddet = this.idDetalleRemito;
            if(deuda.getDeudaXRemito(nroRemito).size() == 0){
                bd.insertar("UPDATE Remito SET completo = True WHERE numero = '" + nroRemito + "' ;" );
                }
            bd.cerrar();
        }
        
    }
    public void borrar(){
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar("DELETE FROM DetalleFactura WHERE id = " + this.id);
        bd.cerrar();
    }
    //se llama cuando se borra un recibo, entonces la deuda que fue pagafda, debe recrearse
    public void recrearDeudaRemito(){
        DetalleRemito dr = new DetalleRemito(this.getIdDetalleRemito());
        dr.getRemito().setCompletoBackwards(false);
        DeudaRemito deudaRemito = new DeudaRemito(dr);
        deudaRemito.hacerAlta();
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
    
    public int getIdDetalleRemito() {
        return idDetalleRemito;
    }

    public void setIdDetalleRemito(int idRemito) {
        this.idDetalleRemito = idRemito;
    }
    public Factura getFactura(){
        return this.factura;
    }
    public void setFactura(Factura factura){
        this.factura = factura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   
  
}

