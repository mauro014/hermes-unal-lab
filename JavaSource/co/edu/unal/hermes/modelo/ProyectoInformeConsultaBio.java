/*
ProyectoInformeConsultaBio Created on 23-feb-2025
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * Maneja los objetivos específicos que enmarcan cada proyecto
 */
public class ProyectoInformeConsultaBio {

	private Long id;
	private ProyectoInforme informe;
	private String localidad;
	private Date fecha;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public ProyectoInforme getInforme() {
		return informe;
	}
	public void setInforme(ProyectoInforme informe) {
		this.informe = informe;
	}

	

	
}
