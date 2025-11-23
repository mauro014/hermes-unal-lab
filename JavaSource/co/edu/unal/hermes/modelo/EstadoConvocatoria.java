/*
 * Created on 03-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Representa el estado actual de la convocatoria. Dentro de las opciones están:
 * Activa, Inactiva, Cancelada y Creación.
 */
public class EstadoConvocatoria implements Serializable {

	private static final long serialVersionUID = 7260300810933931855L;

	public static String ACTIVA = "A";
	public static String INACTIVA = "I";
	public static String CANCELADA = "C";
	public static String CREACION = "CR";

	private String id;
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
