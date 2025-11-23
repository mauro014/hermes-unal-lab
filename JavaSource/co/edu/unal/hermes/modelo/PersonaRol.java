/*
 * Created on 05-sep-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Juan Pablo
 */

public class PersonaRol implements Serializable {
	
	private String tipoDocumento;
	private String documento;
	private String nombre;
	private Date fechaInicioRol;
	private Date fechaFinRol;
	
	
    public PersonaRol(){
		
	}
    
    public PersonaRol(String pTipoDocumento,String pDocumento,String pNombre){
		this.tipoDocumento = pTipoDocumento; 
		this.documento = pDocumento;
		this.nombre = pNombre; 	
	}
    
    public boolean equals(Object obj){
	    if(obj instanceof PersonaRol){
	    	PersonaRol idRol = (PersonaRol)obj;
	        if(idRol.getDocumento().equals(documento) && idRol.getTipoDocumento().equals(tipoDocumento) && idRol.getNombre().equals(nombre)){
	            return true; 
	        }
	    }
		return false;
	}
    
    	
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	public Date getFechaInicioRol() {
		return fechaInicioRol;
	}

	public void setFechaInicioRol(Date fechaInicioRol) {
		this.fechaInicioRol = fechaInicioRol;
	}

	public Date getFechaFinRol() {
		return fechaFinRol;
	}

	public void setFechaFinRol(Date fechaFinRol) {
		this.fechaFinRol = fechaFinRol;
	}
	
}
