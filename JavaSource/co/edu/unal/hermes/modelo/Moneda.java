/*
 * Created on 24-abr-2006
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package co.edu.unal.hermes.modelo;

/**
 * @author Jassar David Issa Co
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Moneda implements IIdentidad {
	
	
	private	String	id;
	private	String	nombre;

	public String getId() {
		return id;
		
		 
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
} 
