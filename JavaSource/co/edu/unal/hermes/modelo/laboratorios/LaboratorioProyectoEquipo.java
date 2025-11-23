package co.edu.unal.hermes.modelo.laboratorios;

import java.io.Serializable;

import co.edu.unal.hermes.modelo.Proyecto;

public class LaboratorioProyectoEquipo implements Serializable {

	private static final long serialVersionUID = -3261156811978712517L;

	private Long id;
	private Laboratorio laboratorio;
	private Proyecto proyecto;
	private LaboratorioDetalleEquipos equipo;
	private Boolean asociado;
	
	public LaboratorioProyectoEquipo() {
	}
	
	public LaboratorioProyectoEquipo(Laboratorio lab, Proyecto pry, LaboratorioDetalleEquipos equipo, Boolean asociado) {
		this.laboratorio = lab;
		this.proyecto = pry;
		this.equipo = equipo;
		this.asociado = asociado;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}
	public Proyecto getProyecto() {
		return proyecto;
	}
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	public LaboratorioDetalleEquipos getEquipo() {
		return equipo;
	}
	public void setEquipo(LaboratorioDetalleEquipos equipo) {
		this.equipo = equipo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getAsociado() {
		return asociado;
	}

	public void setAsociado(Boolean asociado) {
		this.asociado = asociado;
	}
}
