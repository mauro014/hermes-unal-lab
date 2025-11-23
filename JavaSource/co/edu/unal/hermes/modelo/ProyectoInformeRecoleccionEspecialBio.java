/*
 * Created on 23-feb-2025
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * Maneja los objetivos específicos que enmarcan cada proyecto
 */
public class ProyectoInformeRecoleccionEspecialBio {

	private Long id;
	private ProyectoInforme informe;
	private String nombreCientifico;
	private String nroPermiso;
	private String categoria;
	private Date fechaDesde;
	private Date fechaHasta;
	private String localidad;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombreCientifico() {
		return nombreCientifico;
	}
	public void setNombreCientifico(String nombreCientifico) {
		this.nombreCientifico = nombreCientifico;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public Date getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public Date getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public ProyectoInforme getInforme() {
		return informe;
	}
	public void setInforme(ProyectoInforme informe) {
		this.informe = informe;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getNroPermiso() {
		return nroPermiso;
	}
	public void setNroPermiso(String nroPermiso) {
		this.nroPermiso = nroPermiso;
	}
	

}
