/*
 * Nodo.java
 *
 * Created on 17 de febrero de 2007, 11:36
 *
 * @author Martin Farias
 * 
 */

package Controlador;


import com.griaule.grfingerjava.Template;
import java.io.*;

public class Nodo {

	private Template template;
	private String codigo;
	private int id;
	private Nodo proximo;
	
    public Nodo() {
    	this.proximo = null;
    }
    
    public Nodo(Template template, int id ){
    	this.template = template;
    	this.id = id;
    	this.proximo = null;
    }
    
    public Template getTemplate(){
    	return template;
    }
    
    public Nodo getProximo(){
    	return proximo;
    }
    
    public int getId(){
    	return id;
    }
   public String getCodigo(){
   	return codigo;
    }
    
    public void setTemplate(Template b){
    	this.template = b;
    }
     public void setId(int n){
     	this.id = n;
     }
    public void setProximo(Nodo n){
    	this.proximo = n;
    }
    public void setCodigo(String c){
        this.codigo = c;
    }
    
    public String toString(){
    	return id + ": " + codigo;
    }
    
}
