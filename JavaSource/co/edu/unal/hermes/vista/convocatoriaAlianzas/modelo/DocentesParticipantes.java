package co.edu.unal.hermes.vista.convocatoriaAlianzas.modelo;

import java.io.Serializable;

public class DocentesParticipantes extends DatosModelo<DocentesParticipantes> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 209740279251123609L;

	private String idDocente;
	private String nombre;
	private String sede;
	private String facultad;
	private String departamento;

	public DocentesParticipantes() {

	}

	public DocentesParticipantes(String idDocente, String nombre, String sede, String facultad, String departamento) {
		super();
		this.idDocente = idDocente;
		this.nombre = nombre;
		this.sede = sede;
		this.facultad = facultad;
		this.departamento = departamento;
	}

	public DocentesParticipantes(String toString) {
		this();
		String[] data = toString.split(SEPARADOR_COLUMNA);
		idDocente = data[0];
		nombre = data[1];
		sede = data[2];
		facultad = data[3];
		departamento = data[4];
	}

	@Override
	boolean validar() {
		boolean ret = true;
		if (!validarDatos(idDocente, nombre, sede, facultad, departamento)) {
			ret = false;
		}
		return ret;
	}

	@Override
	public String toString() {
		return idDocente + SEPARADOR_COLUMNA + nombre + SEPARADOR_COLUMNA + sede + SEPARADOR_COLUMNA + facultad + SEPARADOR_COLUMNA + departamento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getIdDocente() {
		return idDocente;
	}

	public void setIdDocente(String idDocente) {
		this.idDocente = idDocente;
	}

}
