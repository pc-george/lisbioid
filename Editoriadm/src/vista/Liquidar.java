/*
 * Liquidar.java
 *
 * Created on 1 de abril de 2007, 20:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package vista;

import controlador.Cliente;
import controlador.DetalleFactura;
import controlador.DetalleRemito;
import controlador.DeudaFactura;
import controlador.DeudaRemito;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.TimeZone;

/**
 *
 * @author PC
 */
public class Liquidar {
    private FileWriter fw;
    
    private String contenido;
    private Cliente cliente;
    private String numFAnterior ;
    private String numRAnterior;
    private float totalRemito = 0;
    private float totalFactura = 0;
    private float total = 0;
    
    /** Creates a new instance of Liquidar */
    public Liquidar(Cliente cli) {
        this.cliente = cli;
        hacerLiquidacion();
        guardarArchivo();
    }
    public boolean guardarArchivo(){
        try{
            String url = "temp" + cliente.getNombre() + ".html" ;
            fw = new FileWriter(url);
            fw.write(this.contenido);
            fw.flush();
            fw.close();
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            return true;
        }catch(IOException e){
            System.out.println(e.toString());
            return false;}
        
    }
    
    public static String getFecha() {
      String fecha;
      Calendar cal = Calendar.getInstance(TimeZone.getDefault());
      String FORMATOHORA = "dd/MM/yy";
      java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(FORMATOHORA);
      fecha = sdf.format(cal.getTime());
      return fecha;
      }
    public void hacerLiquidacion(){
        this.contenido = "<html>" +
                "<head> <title>Ferreyra Editor - Liquidación</title>\n<link rel=\"stylesheet\" href=\"estilo.css\"> </head> <body >" +
                "<div class=\"central\">" +
                "<center><h1>Ferreyra Editor</h1><h2>Liquidación al " + getFecha() + "</h2></center>" +
                "<table id=\"cli\"><tr id=\"arriba\"><td >Cliente: </td><td>" + cliente.getNombre() + "</td></tr>" +
                "<tr id=\"abajo\"><td></td><td >" + cliente.getDireccion() + " - " + cliente.getEmail() + "</td></tr></table><hr/>" ;
        this.contenido += getTablaRemitos();
        this.contenido += "<br/>" +
                "<hr/><div class=\"fin\"><table id=\"deuda\"><tr><td>Total adeudado: </td><td>" + total + "</td></tr></table>";
        this.contenido += "Administración - Ferreyra Editor</h5> </div><br/>";
        this.contenido += "</body></html>";
        System.out.println(total);
    }
    
    public String getTablaRemitos(){
        LinkedList deudas = cliente.getDeudas();
        
        String  temp ;
        String numRAnterior = "";
        String numFAnterior = "";
        boolean esConRemito = false;
        temp = "";
        
        for(int i = 0; i < deudas.size(); i++){
             try{
                DeudaRemito dr = (DeudaRemito)deudas.get(i);
                esConRemito = true;
                if(numRAnterior.compareTo(dr.getNumeroRemito()) == 0 ){
                    temp += getLineaRem(dr);
                }
                else{
                    if(numRAnterior.compareTo("") != 0){
                        temp += getPieRem();
                    }
                    numRAnterior = dr.getNumeroRemito();
                    temp += getCabezaRem(dr); //incluye la primer linea (getLineaRem)
                }   
                
             }catch(ClassCastException e){
                DeudaFactura def = (DeudaFactura)deudas.get(i);
                if(numFAnterior.compareTo(def.getNumeroFactura()) == 0 ){
                    temp += getLineaFac(def);
                }
                else{
                    if(numFAnterior.compareTo("") != 0){
                        temp += getPieFac();
                    }
                    numFAnterior = def.getNumeroFactura();
                    temp += getCabezaFac(def); //incluye la primer linea (getLineaFac)
                }   
             }
        }
        if(esConRemito){
                 temp += getPieRem();
                 return temp;
        }
        temp += getPieFac();
        return temp;
    }
    
    public String getCabezaRem(DeudaRemito dr ) {
        String cabeza;
        cabeza = "<div class=\"main\"> " +
                "<h3> Remito nro " + dr.getNumeroRemito() + "</h3>" + 
                "<table class =\"remito\"> " +
                "   <tr class=\"cabeza\"> " +
                "       <td> Titulo </td> " +
                "       <td> Precio </td> " +
                "       <td> Cantidad </td> " +
                "       <td> Subtotal </td> " +
                "   </tr> \n";
        return cabeza + getLineaRem(dr);
    }
    public String getLineaRem(DeudaRemito dr){
        String temp ;
        DetalleRemito det = dr.getDetalleRemito();
        this.totalRemito += det.getEjemplar().getPrecioUnitario() * dr.getCantidad() ;
        temp = "<tr>" +
               "    <td> " + det.getEjemplar().getLibro().getTitulo() + "</td>" +
               "    <td> " + det.getEjemplar().getPrecioUnitario() + "</td> " +
               "    <td> " + dr.getCantidad() + " </td> " +
               "    <td> " + dr.getTotal() + "</td> " +
               "</tr>"; 
        return temp;        
    }
    public String getPieRem(){
        String temp;
        temp = "<tr class=\"total\"><td></td><td></td><td>Total</td><td>" + this.totalRemito + "</td></tr>" +
                "</table> </div>\n" ;
        this.total += totalRemito;
        this.totalRemito = 0;
        return temp;
    }
    public String getCabezaFac (DeudaFactura d ) {
        String cabeza;
        cabeza = "<div class=\"main\"> " +
                "<h3> Factura  A " + d.getNumeroFactura() + "</h3>" + 
                "<table class =\"factura\"> " +
                "   <tr class=\"cabeza\"> " +
                "       <td> Titulo </td> " +
                "       <td> Precio </td> " +
                "       <td> Cantidad </td> " +
                "       <td> Subtotal </td> " +
                "   </tr> \n";
        return cabeza + getLineaFac(d);
    }
    public String getLineaFac(DeudaFactura d){
        String temp ;
        DetalleFactura det = d.getDetalleFactura();
        this.totalFactura += det.getEjemplar().getPrecioUnitario() * d.getCantidad() ;
        temp = "<tr>" +
               "    <td> " + det.getEjemplar().getLibro().getTitulo() + "</td>" +
               "    <td> " + det.getEjemplar().getPrecioUnitario() + "</td> " +
               "    <td> " + d.getCantidad() + " </td> " +
               "    <td> " + d.getTotal() + "</td> " +
               "</tr>"; 
        return temp;        
    }
    public String getPieFac (){
        String temp;
        temp = "<tr class=\"total\"><td></td><td></td><td>Total</td><td>" + this.totalFactura + "</td></tr>" +
                "</table> </div>\n" ;
        this.total = totalFactura;
        this.totalFactura = 0;
        
        return temp;
    }
    
    
}
