package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroIntegranteTipo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String tipo;
	private String nombre;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}