package co.edu.unal.hermes.vista.convocatoriaAlianzas.modelo;

import java.io.Serializable;

import co.edu.unal.hermes.modelo.anotaciones.NotNull;

public class ProduccionAcademica extends DatosModelo<ProduccionAcademica> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7877071411014493460L;
	public static String PROP_ANNIO = "annio";
	public static String PROP_TIPO = "tipo";
	public static String PROP_PRODUCTO = "producto";
	public static String PROP_CANTIDAD = "cantidad";

	@NotNull
	private String annio;
	@NotNull
	private String tipo;
	@NotNull
	private String producto;
	@NotNull
	private String cantidad;

	public ProduccionAcademica() {
		annio = "";
		tipo = "";
		producto = "";
		cantidad = "";
	}

	public ProduccionAcademica(String toString) {
		this();
		String[] data = toString.split(SEPARADOR_COLUMNA);
		annio = data[0];
		tipo = data[1];
		producto = data[2];
		cantidad = data[3];
	}

	@Override
	boolean validar() {
		boolean ret = true;
		if (!validarDatos(annio, tipo, producto, cantidad)) {
			ret = false;
		}
		return ret;
	}

	@Override
	public String toString() {
		return annio + SEPARADOR_COLUMNA + tipo + SEPARADOR_COLUMNA + producto + SEPARADOR_COLUMNA + cantidad;
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

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

}
