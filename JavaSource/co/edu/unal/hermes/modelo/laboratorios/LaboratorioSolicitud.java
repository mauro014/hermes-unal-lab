package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Edificio;
import co.edu.unal.hermes.modelo.Empresa;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Tipos;

/**
 * Clase que representa una solicitud de laboratorio
 * 
 * 
 */
public class LaboratorioSolicitud {

	private Long id;
	private Persona persona;
	private Persona personaRevision;
	private Laboratorio laboratorio;
	private Tipos tipo;
	private Tipos estado;
	private String descripcion;
	private String justificacion;
	private String pertinencia;
	private Dependencia dependenciaDirigidaA;
	private Date fechaRegistro;
	private Date fechaUltimoCambioEstado;
	private Date fechaRespuesta;
	private String observaciones;
	private LaboratorioDetalleEquipos equipo;
	private Laboratorio laboratorioDestinoEquipo;
	private Tipos motivo;
	private String motivo_otro;

	public LaboratorioSolicitud() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Persona getPersonaRevision() {
		return personaRevision;
	}

	public void setPersonaRevision(Persona personaRevision) {
		this.personaRevision = personaRevision;
	}

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	public Tipos getTipo() {
		return tipo;
	}

	public void setTipo(Tipos tipo) {
		this.tipo = tipo;
	}

	public Tipos getEstado() {
		return estado;
	}

	public void setEstado(Tipos estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public Dependencia getDependenciaDirigidaA() {
		return dependenciaDirigidaA;
	}

	public void setDependenciaDirigidaA(Dependencia dependenciaDirigidaA) {
		this.dependenciaDirigidaA = dependenciaDirigidaA;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaUltimoCambioEstado() {
		return fechaUltimoCambioEstado;
	}

	public void setFechaUltimoCambioEstado(Date fechaUltimoCambioEstado) {
		this.fechaUltimoCambioEstado = fechaUltimoCambioEstado;
	}

	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	public LaboratorioDetalleEquipos getEquipo() {
		return equipo;
	}

	public void setEquipo(LaboratorioDetalleEquipos equipo) {
		this.equipo = equipo;
	}

	public Laboratorio getLaboratorioDestinoEquipo() {
		return laboratorioDestinoEquipo;
	}

	public void setLaboratorioDestinoEquipo(Laboratorio laboratorioDestinoEquipo) {
		this.laboratorioDestinoEquipo = laboratorioDestinoEquipo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getPertinencia() {
		return pertinencia;
	}

	public void setPertinencia(String pertinencia) {
		this.pertinencia = pertinencia;
	}

	public Tipos getMotivo() {
		return motivo;
	}

	public void setMotivo(Tipos motivo) {
		this.motivo = motivo;
	}

	public String getMotivo_otro() {
		return motivo_otro;
	}

	public void setMotivo_otro(String motivo_otro) {
		this.motivo_otro = motivo_otro;
	}

}