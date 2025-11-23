package co.edu.unal.hermes.modelo;

import java.util.Date;

public class HistoricoPropiedadIntelectual implements java.io.Serializable, Comparable<HistoricoPropiedadIntelectual> {

	private static final long serialVersionUID = 1L;

	private Long id;
	private PropiedadIntelectual propiedad;
	private Date fecha;
	private SubEstadoPropiedadIntelectual estado;
	private Persona responsable;
	private Dependencia dependencia;
	private String observacion;
	private Tipos sectorTecnologico;

	/** default constructor */
	public HistoricoPropiedadIntelectual() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setResponsable(InvestigadorInterno responsable) {
		this.responsable = responsable;
	}

	public PropiedadIntelectual getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(PropiedadIntelectual propiedad) {
		this.propiedad = propiedad;
	}

	public Dependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	public SubEstadoPropiedadIntelectual getEstado() {
		return estado;
	}

	public void setEstado(SubEstadoPropiedadIntelectual estado) {
		this.estado = estado;
	}

	/* Inicio Requerimiento #2323 */
	@Override
	public int compareTo(HistoricoPropiedadIntelectual o) {
		return getFecha().compareTo(o.getFecha());
	}
	/* Fin Requerimiento #2323 */

	/* Inicio Requerimiento #2369 */
	public Tipos getSectorTecnologico() {
		return sectorTecnologico;
	}

	public void setSectorTecnologico(Tipos sectorTecnologico) {
		this.sectorTecnologico = sectorTecnologico;
	}
	/* Fin Requerimiento #2369 */
}