package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Formulario implements Serializable{
    

    
	private Long id;
	private String nombre;
	private String tipo;
	private String accion;
	private Set tipoModalidad = new HashSet();
	
	
	public Set getTipoModalidad() {
		return tipoModalidad;
	}
	public void setTipoModalidad(Set tipoModalidad) {
		this.tipoModalidad = tipoModalidad;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}	
	
 
}
