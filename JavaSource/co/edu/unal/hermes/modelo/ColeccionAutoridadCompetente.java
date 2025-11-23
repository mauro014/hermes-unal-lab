package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;


public class ColeccionAutoridadCompetente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long idColeccion ;
	private String numeroRegistro;
	private FuenteFinanciacion autoridad;
	private DominioDetalle estado;
	private Date fechaActualizacion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public FuenteFinanciacion getAutoridad() {
		return autoridad;
	}
	public void setAutoridad(FuenteFinanciacion autoridad) {
		this.autoridad = autoridad;
	}
	public DominioDetalle getEstado() {
		return estado;
	}
	public void setEstado(DominioDetalle estado) {
		this.estado = estado;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Long getIdColeccion() {
		return idColeccion;
	}
	public void setIdColeccion(Long idColeccion) {
		this.idColeccion = idColeccion;
	}
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

}
