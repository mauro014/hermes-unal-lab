package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class EstadoGrupoColciencias implements Serializable {
	private static final long serialVersionUID = 7641114907095797783L;
	private String id;
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
