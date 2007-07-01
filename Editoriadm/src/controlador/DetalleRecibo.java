/*
 * DetalleRecibo.java
 *
 * Created on 27 de marzo de 2007, 20:15
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
public class DetalleRecibo {
    
    private int id;
    private Recibo recibo ;
    private EjemplarLibro ejemplar;
    private int cantidad;
    private int idDetalleFactura; 
    
    /** Creates a new instance of DetalleFactura */
    public DetalleRecibo(EjemplarLibro ejemplar, int cantidad, Recibo recibo, int idDetalleFactura) {
        this.setEjemplar(ejemplar);
        this.setCantidad(cantidad);
        this.setRecibo(recibo);
        this.setIdDetalleFactura(idDetalleFactura);
        this.setId(0);
    }
    
    public DetalleRecibo(int id){
        this.setId(id);
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        ResultSet rs = bd.consulta("select * from DetalleRecibo where id = " + this.getId() );
        try {
            if(rs.next()){
        
                this.ejemplar = new EjemplarLibro(rs.getInt("id_ejemplar"));
                this.cantidad = rs.getInt("cantidad");
                this.recibo= new Recibo(rs.getInt("id_recibo"));
                this.setIdDetalleFactura(rs.getInt("id_detalle_factura"));
                rs.close();
            }
            else {
            System.out.println("error al buscar un Detalle de recibo");
            }
        } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("Error sql al buscar un Detalle de recibo en BD");
        }
        bd.cerrar();
    }
    
    public void hacerAlta(){
        ControladorBaseDeDatos bd= new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar("INSERT INTO DetalleRecibo(id_ejemplar, cantidad, id_recibo, id_detalle_factura) VALUES (" +this.getEjemplar().getId() + ", " +this.getCantidad()+ ", " + this.getRecibo().getId()+ " , " + this.getIdDetalleFactura() + " );");
        ResultSet rs = bd.consulta("SELECT id FROM DetalleRecibo WHERE id_recibo = " +this.getRecibo().getId() +" and id_ejemplar = " + this.getEjemplar().getId() + " and cantidad = " + this.getCantidad() + ";");
        try{
            rs.next();
            this.setId(rs.getInt("id"));
        }catch(SQLException e) {System.out.println("errorrrrrr insertando detalle recibo en la BD");}
        
        
       //empieza a cancelar la deuda preexistente
       //segun lo pagado en el remito
       DeudaFactura deuda = new DeudaFactura(this.idDetalleFactura);
       System.out.println(this.idDetalleFactura);
       deuda.actualizarDeuda(this);
       
        //aca checkeo que el detalle borrado, no haya dejado a la facturaConRecibo "pagada"
       //es decir, que sea el ultimo detalle del remito
       Factura fact = new DetalleFactura(this.getIdDetalleFactura()).getFactura();
       if(deuda.getDeudaXFactura(fact.getNumero() ).size() == 0){
            bd.insertar("UPDATE Factura SET completo = True WHERE numero = '" + fact.getNumero() + "' and tipo = '" + fact.getTipo() + "' ;" );
       }
       bd.cerrar();
    }
    
    public void borrar(){
        ControladorBaseDeDatos bd = new ControladorBaseDeDatos(Main.baseDatos);
        bd.insertar("DELETE FROM DetalleRecibo WHERE id = " + this.id);
        bd.cerrar();
    }
    //se llama cuando se borra un recibo, entonces la deuda que fue pagafda, debe recrearse
    public void recrearDeudaFactura(){
        DetalleFactura def = new DetalleFactura(this.idDetalleFactura);
        def.getFactura().setCompletoBackwards(false);
        DeudaFactura deudaFactura = new DeudaFactura(def);
        deudaFactura.hacerAlta();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recibo getRecibo() {
        return recibo;
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
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

    public int getIdDetalleFactura() {
        return idDetalleFactura;
    }

    public void setIdDetalleFactura(int idDetalleFactura) {
        this.idDetalleFactura = idDetalleFactura;
    }
    
}
