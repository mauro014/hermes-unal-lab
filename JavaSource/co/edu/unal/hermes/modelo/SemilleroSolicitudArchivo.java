package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroSolicitudArchivo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private SemilleroSolicitud solicitud;
	private String nombre;
	private String tipo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SemilleroSolicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SemilleroSolicitud solicitud) {
		this.solicitud = solicitud;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}