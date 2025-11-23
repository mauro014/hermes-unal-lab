package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroInformeArchivo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private SemilleroInforme informe;
	private String nombre;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SemilleroInforme getInforme() {
		return informe;
	}

	public void setInforme(SemilleroInforme informe) {
		this.informe = informe;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}