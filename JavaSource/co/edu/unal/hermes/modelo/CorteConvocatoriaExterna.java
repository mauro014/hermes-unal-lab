/*
 * Created on 02-junio-2023
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * 
 */
public class CorteConvocatoriaExterna {

	private Long id;
	private ConvocatoriaExterna convocatoriaExterna;
	private Sede sede;
	private Date fechaLimite;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ConvocatoriaExterna getConvocatoriaExterna() {
		return convocatoriaExterna;
	}

	public void setConvocatoriaExterna(ConvocatoriaExterna convocatoriaExterna) {
		this.convocatoriaExterna = convocatoriaExterna;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
	
}
