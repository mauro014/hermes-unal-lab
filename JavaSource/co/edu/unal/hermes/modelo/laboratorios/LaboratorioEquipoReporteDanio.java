package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

public class LaboratorioEquipoReporteDanio {

	private Long id;
	private LaboratorioDetalleEquipos equipo;
	private Date fechaReporte;
	private String descripcion;
	private Date fechaRegistro;
	private Date fechaActualizacion;
	
	private Date fechaSolucion;
	private String descripcionSolucion;
	
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
	public Date getFechaReporte() {
		return fechaReporte;
	}
	public void setFechaReporte(Date fechaReporte) {
		this.fechaReporte = fechaReporte;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	public Date getFechaSolucion() {
		return fechaSolucion;
	}
	public void setFechaSolucion(Date fechaSolucion) {
		this.fechaSolucion = fechaSolucion;
	}
	public String getDescripcionSolucion() {
		return descripcionSolucion;
	}
	public void setDescripcionSolucion(String descripcionSolucion) {
		this.descripcionSolucion = descripcionSolucion;
	}
		
}
