package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroSolicitudTipo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nombre;
	private Integer estado;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
}