package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * @author dgbenitezc
 * 
 */
public class TipoCargo implements Serializable {

	private static final long serialVersionUID = -1292667572157285177L;

	String id;
	String nombre;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
