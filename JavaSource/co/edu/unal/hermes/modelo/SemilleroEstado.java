package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroEstado implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nombre;
	
	 public static final Integer ESTADO_ACTIVO = 5;
	 public static final Integer ESTADO_INACTIVO = 7;

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

}
