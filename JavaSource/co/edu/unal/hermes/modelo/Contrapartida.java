package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * Especifíca la información de la contrapartida para la financiacion externa
 */
public class Contrapartida extends Modalidad {

	private static final long serialVersionUID = 8983511756825114817L;

	private FuenteFinanciacion financiacionExterna;
	private String numeroAval;
	private String numeroAprobacionFinanciacionExterna;
	private Date fechaAval;
	private Date fechaAprobacionFinanciacionExterna;
	private String nombre;
	private EstadoConvocatoria estadoContrapartida;

	public EstadoConvocatoria getEstadoContrapartida() {
		return estadoContrapartida;
	}

	public void setEstadoContrapartida(EstadoConvocatoria estadoContrapartida) {
		this.estadoContrapartida = estadoContrapartida;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public FuenteFinanciacion getFinanciacionExterna() {
		return financiacionExterna;
	}

	public void setFinanciacionExterna(FuenteFinanciacion financiacionExterna) {
		this.financiacionExterna = financiacionExterna;
	}

	public Date getFechaAprobacionFinanciacionExterna() {
		return fechaAprobacionFinanciacionExterna;
	}

	public void setFechaAprobacionFinanciacionExterna(
			Date fechaAprobacionFinanciacionExterna) {
		this.fechaAprobacionFinanciacionExterna = fechaAprobacionFinanciacionExterna;
	}

	public Date getFechaAval() {
		return fechaAval;
	}

	public void setFechaAval(Date fechaAval) {
		this.fechaAval = fechaAval;
	}

	public String getNumeroAprobacionFinanciacionExterna() {
		return numeroAprobacionFinanciacionExterna;
	}

	public void setNumeroAprobacionFinanciacionExterna(
			String numeroAprobacionFinanciacionExterna) {
		this.numeroAprobacionFinanciacionExterna = numeroAprobacionFinanciacionExterna;
	}

	public String getNumeroAval() {
		return numeroAval;
	}

	public void setNumeroAval(String numeroAval) {
		this.numeroAval = numeroAval;
	}

}