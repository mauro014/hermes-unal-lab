package co.edu.unal.hermes.vista.convocatoriaAlianzas.modelo;

import java.io.Serializable;

import co.edu.unal.hermes.modelo.anotaciones.NotNull;

public class FormacionEstudiantes extends DatosModelo<FormacionEstudiantes> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7877071411014493460L;
	public static String PROP_ANNIO = "annio";
	public static String PROP_NIVEL_EDUCATIVO = "nivelEducativo";
	public static String PROP_ESTADO = "estado";
	public static String PROP_CANTIDAD = "cantidad";

	@NotNull
	private String annio;
	@NotNull
	private String nivelEducativo;
	@NotNull
	private String estado;
	@NotNull
	private String cantidad;

	public FormacionEstudiantes() {
		annio = "";
		nivelEducativo = "";
		estado = "";
		cantidad = "";
	}

	public FormacionEstudiantes(String toString) {
		this();
		String[] data = toString.split(SEPARADOR_COLUMNA);
		annio = data[0];
		nivelEducativo = data[1];
		estado = data[2];
		cantidad = data[3];
	}

	public String getAnnio() {
		return annio;
	}

	public void setAnnio(String annio) {
		this.annio = annio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNivelEducativo() {
		return nivelEducativo;
	}

	public void setNivelEducativo(String nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	boolean validar() {
		boolean ret = true;
		if (!validarDatos(annio, nivelEducativo, estado, cantidad)) {
			ret = false;
		}
		return ret;
	}

	@Override
	public String toString() {
		return annio + SEPARADOR_COLUMNA + nivelEducativo + SEPARADOR_COLUMNA + estado + SEPARADOR_COLUMNA + cantidad;
	}

}
