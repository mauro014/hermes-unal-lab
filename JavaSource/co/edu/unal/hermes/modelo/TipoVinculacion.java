/*
 * Created on 17-ago-2006
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class TipoVinculacion implements Serializable {

	private static final long serialVersionUID = 3488508546212080165L;

	String id;
	String nombre;
	private boolean esDocente;
	private boolean esAdministrativo;
    public static final String PROFESOR_CARRERA_DOCENTE = "PCD";
    public static final String PROFESOR_SIN_CARRERA = "PSCD";
    public static final String ESTUDIANTE_PREGRADO = "ESPR";
    public static final String ESTUDIANTE_PREGRADO_SEMILLERO = "EPRS";
    public static final String ESTUDIANTE_POSGRADO = "ESPO";
    public static final String ESTUDIANTE_LIDER = "AL";
    public static final String SEMILLERO = "SEMC";
    public static final String TIPO_EXTERNO = "E";
    public static final String TIPO_ESTUDIANTE = "A";
    public static final String TIPO_FUNCIONARIO = "F";
    public static final String CONECTOR = "--";
	public static final String ESTUDIANTE = CONECTOR + ESTUDIANTE_PREGRADO + CONECTOR + ESTUDIANTE_POSGRADO + CONECTOR
			+ ESTUDIANTE_LIDER + CONECTOR;
	public static final String DOCENTE_ESP_CON_PREST = "34";

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

	public boolean isEsAdministrativo() {
		return esAdministrativo;
	}

	public void setEsAdministrativo(boolean esAdministrativo) {
		this.esAdministrativo = esAdministrativo;
	}

	public boolean isEsDocente() {
		return esDocente;
	}

	public void setEsDocente(boolean esDocente) {
		this.esDocente = esDocente;
	}
}
