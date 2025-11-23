package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Tipos;

public class LaboratorioLogEquipos {

	private Long id;
	private LaboratorioDetalleEquipos equipo;
	private Persona responsable;
	private Tipos tipoOperacion;
	private Date fechaRegistro;

	public LaboratorioLogEquipos() {
	}

//	@Override
//	public boolean equals(Object otroObjeto) {
//		LaboratorioDetalleInsumos otroDetalle = (LaboratorioDetalleInsumos) otroObjeto;
//		return lineaInvestigacion.getId().equals(
//				otroDetalle.getLineaInvestigacion().getId());
//	}

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

	public LaboratorioDetalleEquipos getEquipo() {
		return equipo;
	}

	public void setEquipo(LaboratorioDetalleEquipos equipo) {
		this.equipo = equipo;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	public Tipos getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(Tipos tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	
}
