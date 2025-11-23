package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioEquipoActualizacionSoftware {

	private Long id;
	private LaboratorioDetalleEquipos equipo;
	private Tipos tipo;
	private String versionAnterior;
	private String versionNueva;
	private Date fechaActualizacionSoftware;
	private String responsable;
	private Date fechaRegistro;
	private Date fechaActualizacion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LaboratorioDetalleEquipos getEquipo() {
		return equipo;
	}
	public void setEquipo(LaboratorioDetalleEquipos equipo) {
		this.equipo = equipo;
	}
	public Tipos getTipo() {
		return tipo;
	}
	public void setTipo(Tipos tipo) {
		this.tipo = tipo;
	}
	public String getVersionAnterior() {
		return versionAnterior;
	}
	public void setVersionAnterior(String versionAnterior) {
		this.versionAnterior = versionAnterior;
	}
	public String getVersionNueva() {
		return versionNueva;
	}
	public void setVersionNueva(String versionNueva) {
		this.versionNueva = versionNueva;
	}
	public Date getFechaActualizacionSoftware() {
		return fechaActualizacionSoftware;
	}
	public void setFechaActualizacionSoftware(Date fechaActualizacionSoftware) {
		this.fechaActualizacionSoftware = fechaActualizacionSoftware;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
}
