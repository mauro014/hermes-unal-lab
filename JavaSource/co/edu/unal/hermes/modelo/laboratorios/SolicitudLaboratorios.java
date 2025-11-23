package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Tipos;

/**
 * Objeto que representa una Solicitud ante el SNL
 * 
 * @author dgbenitezc
 */
public class SolicitudLaboratorios {

	private Long id;
	private Date fecha;
	private Persona persona;
	private Tipos tipoSolicitud;
	private Laboratorio laboratorio;
	private String justificacion;
	private EstadoProyecto estadoSolicitud;
	private Dependencia dirigidaA;
	private String observaciones;
	private Date fechaRespuesta;
	private Laboratorio laboratorioSolicitante;
	private String cargoSolicitante;

	public SolicitudLaboratorios() {
		tipoSolicitud = new Tipos(Tipos.TIPOS_SOLICITUDES_LABORATORIO);
		laboratorio = new Laboratorio();
		estadoSolicitud = new EstadoProyecto();
		estadoSolicitud.setId(EstadoProyecto.INGRESANDO_SOLICITUD_LABORATORIO);
		laboratorioSolicitante = new Laboratorio();
	}

	public Boolean getEsFinalizada() {
		if (estadoSolicitud.getId().equals(EstadoProyecto.FINALIZADO)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * @param persona
	 *            the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * @return the tipoSolicitud
	 */
	public Tipos getTipoSolicitud() {
		return tipoSolicitud;
	}

	/**
	 * @param tipoSolicitud
	 *            the tipoSolicitud to set
	 */
	public void setTipoSolicitud(Tipos tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	/**
	 * @return the laboratorio
	 */
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	/**
	 * @param laboratorio
	 *            the laboratorio to set
	 */
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	/**
	 * @return the justificacion
	 */
	public String getJustificacion() {
		return justificacion;
	}

	/**
	 * @param justificacion
	 *            the justificacion to set
	 */
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	/**
	 * @return the estadoSolicitud
	 */
	public EstadoProyecto getEstadoSolicitud() {
		return estadoSolicitud;
	}

	/**
	 * @param estadoSolicitud
	 *            the estadoSolicitud to set
	 */
	public void setEstadoSolicitud(EstadoProyecto estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	/**
	 * @return the dirigidaA
	 */
	public Dependencia getDirigidaA() {
		return dirigidaA;
	}

	/**
	 * @param dirigidaA
	 *            the dirigidaA to set
	 */
	public void setDirigidaA(Dependencia dirigidaA) {
		this.dirigidaA = dirigidaA;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones
	 *            the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the fechaRespuesta
	 */
	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	/**
	 * @param fechaRespuesta
	 *            the fechaRespuesta to set
	 */
	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	/**
	 * @return the laboratorioSolicitante
	 */
	public Laboratorio getLaboratorioSolicitante() {
		return laboratorioSolicitante;
	}

	/**
	 * @param laboratorioSolicitante
	 *            the laboratorioSolicitante to set
	 */
	public void setLaboratorioSolicitante(Laboratorio laboratorioSolicitante) {
		this.laboratorioSolicitante = laboratorioSolicitante;
	}

	/**
	 * @return the cargoSolicitante
	 */
	public String getCargoSolicitante() {
		return cargoSolicitante;
	}

	/**
	 * @param cargoSolicitante
	 *            the cargoSolicitante to set
	 */
	public void setCargoSolicitante(String cargoSolicitante) {
		this.cargoSolicitante = cargoSolicitante;
	}

}
