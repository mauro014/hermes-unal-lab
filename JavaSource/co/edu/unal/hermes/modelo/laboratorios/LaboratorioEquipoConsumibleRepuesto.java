package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

public class LaboratorioEquipoConsumibleRepuesto {

	private Long id;
	private LaboratorioDetalleEquipos equipo;
	private String nombre;
	private String referencia;
	private Integer cantidad;
	private Integer tiempoEstimado;
	private Date fechaRegistro;
	private Date fechaActualizacion;
	
	private Date fechaCambio;
	private Integer costo;
	
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Integer getTiempoEstimado() {
		return tiempoEstimado;
	}
	public void setTiempoEstimado(Integer tiempoEstimado) {
		this.tiempoEstimado = tiempoEstimado;
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
	public Date getFechaCambio() {
		return fechaCambio;
	}
	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}
	public Integer getCosto() {
		return costo;
	}
	public void setCosto(Integer costo) {
		this.costo = costo;
	}
	
}
