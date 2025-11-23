package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Región de cobertura que afecta el proyecto.
 */
public class Region implements Serializable {

	private static final long serialVersionUID = 5762107263570044027L;

	private String id;
	private String nombre;
	private Pais pais;

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

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
}
