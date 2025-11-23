package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroArchivo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Semillero semillero;
	private String nombre;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Semillero getSemillero() {
		return semillero;
	}

	public void setSemillero(Semillero semillero) {
		this.semillero = semillero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}