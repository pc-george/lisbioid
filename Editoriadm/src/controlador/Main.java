/*
 * Main.java
 *
 * Created on 7 de marzo de 2007, 17:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package controlador;

import vista.Principal;


/**
 *
 * @author PC
 */
public class Main {
    
    public static String baseDatos = "C:/documents and settings/pc/vicky/vicky.mdb";
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Principal p = new Principal();
        p.setVisible(true);
    }
    
    //devuelve un string con la fecha que se pasa por parametro
    public static String getHora(int ano, int mes, int dia){
        String hora = String.valueOf(dia) + "/" + String.valueOf(mes) + "/" + String.valueOf(ano);
        
        return hora;
    }
    
    
}
