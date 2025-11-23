package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class TipoSolicitudGrupo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nombre;
	private String descripcion;
	private boolean vigencia;
	
	public static final long CAMBIO_ESTADO = 2;
	public static final long CREACION_GRUPO = 1;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isVigencia() {
		return vigencia;
	}

	public void setVigencia(boolean vigencia) {
		this.vigencia = vigencia;
	}
}
