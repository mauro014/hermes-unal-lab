package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class TipoPonencia implements Serializable{
	
	public static final String RAIZ = "0";
	
	private Long id;
	private String nombre;
	private String descripcion;
	private String mostrar;
	
	
	public TipoPonencia(){
	}
	
	public TipoPonencia(Long id){
		setId(id);
	}
	
	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMostrar() {
		return mostrar;
	}

	public void setMostrar(String mostrar) {
		this.mostrar = mostrar;
	}


}
