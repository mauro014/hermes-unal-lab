package co.edu.unal.hermes.vista.convocatoriaAlianzas.modelo;

import java.io.Serializable;

import co.edu.unal.hermes.modelo.anotaciones.NotNull;

public class ParticipacionEventos extends DatosModelo<ParticipacionEventos> implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1281582402811380750L;
	public static String PROP_ANNIO = "annio";
	public static String PROP_TIPO = "tipo";
	public static String PROP_CANTIDAD = "cantidad";
	
	@NotNull
	private String annio;
	@NotNull
	private String tipo;
	@NotNull
	private String cantidad;
	
	public ParticipacionEventos() {
		annio = "";
		tipo = "";
		cantidad = "";
	}

	public ParticipacionEventos(String toString) {
		this();
		String[] data = toString.split(SEPARADOR_COLUMNA);
		annio = data[0];
		tipo = data[1];
		cantidad = data[2];
	}


	public String getAnnio() {
		return annio;
	}

	public void setAnnio(String annio) {
		this.annio = annio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
		if (!validarDatos(annio, tipo, cantidad)) {
			ret = false;
		}
		return ret;
	}

	@Override
	public String toString() {
		return annio + SEPARADOR_COLUMNA + tipo + SEPARADOR_COLUMNA + cantidad;
	}


}
