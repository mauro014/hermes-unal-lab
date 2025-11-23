package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Suministra la información principal de las ciudades y municipios donde se
 * realiza el proyecto
 */
public class Ciudad implements Serializable {

	private static final long serialVersionUID = -917436237891232234L;

	private String id;
	private String nombre;
	private String sigla;

	/**
	 * Determina el departamento de cobertura donde se realiza el proyecto.
	 */
	private Departamento departamento;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

}
