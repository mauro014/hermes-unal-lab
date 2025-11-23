package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class TipoConvenio implements Serializable {

	private static final long serialVersionUID = -7259106470708434813L;

	private Long id;
	private String descripcion;

	public TipoConvenio() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
