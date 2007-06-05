/*
 * Lista.java
 *
 * Created on 17 de febrero de 2007, 11:33
 *
 */

package Controlador;

/**
 * @(#)Lista.java
 *
 * @author Martin Farias
 */

import java.io.*;
import com.griaule.grfingerjava.Template;

public class Lista {
	
	private Nodo frente;
	
	
	
    public Lista() {
    	frente = null;
    }
    
    public void insertarPrincipio(Template template, int id){
    	//agrega al principio
    	Nodo nuevo = new Nodo(template, id);
    	nuevo.setProximo(this.getFrente()); //al nodo nuevo se le asigna el frente anterior  como proximo
    	this.setFrente(nuevo); //se pone el nodo nuevo al frente
    	
    }
    public void append(Template template, int id){
    	Nodo nuevo = new Nodo(template, id);
    	//System.out.println(id + template.toString()); //debugin
        nuevo.setProximo(null);
    	if(this.getFrente() == null){
    		this.setFrente(nuevo);
    	}
    	else{
    		Nodo n = this.getFrente();
                Nodo antes = n;
                n = n.getProximo();
    		//Nodo antes = n;
    		while(n != null){
    			antes = n;
    			n = n.getProximo();
    		}
    		antes.setProximo(nuevo);
    	}
    }
    
    //busca un nodo en la lista por el id del nodo (el mismo id del investigador de la BD)
    public Nodo buscar(int id){
    	Nodo p = null;
    	p = this.getFrente();
        while(p != null && id != p.getId()){
            p = p.getProximo();
    	}
    	return p;
    }
    public Nodo getFrente(){
    	return frente;
    }
    public void setFrente(Nodo n){
    	this.frente = n;
    }
    
    
    
}