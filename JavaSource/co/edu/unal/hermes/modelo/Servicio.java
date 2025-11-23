/*
 * Created on 05-sep-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * @author Juan Pablo
 */
public class Servicio implements Serializable {

	private static final long serialVersionUID = -5497241836761858428L;

	private String id;
	private String nombre;
	private String zona;
	private String url;
	private Servicio padre;

	public Servicio getPadre() {
		return padre;
	}

	public void setPadre(Servicio padre) {
		this.padre = padre;
	}

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

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
