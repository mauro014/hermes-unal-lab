package co.edu.unal.hermes.modelo.laboratorios;

import co.edu.unal.hermes.modelo.Tipos;

/**
 * Objeto que relaciona un Equipo (LaboratorioDetalleEquipos) con una Actividad
 * de Mantenimiento Preventivo
 * 
 * @author dgbenitezc
 */
public class LaboratorioActividadMantenimiento {

	private Long id;
	private LaboratorioDetalleEquipos equipo;
	private String nombreActividad;
	private Tipos frecuencia;
	private String responsable;
	private Boolean puedeBorrarse;

	public LaboratorioActividadMantenimiento() {
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
	 * @return the equipo
	 */
	public LaboratorioDetalleEquipos getEquipo() {
		return equipo;
	}

	/**
	 * @param equipo
	 *            the equipo to set
	 */
	public void setEquipo(LaboratorioDetalleEquipos equipo) {
		this.equipo = equipo;
	}

	/**
	 * @return the nombreActividad
	 */
	public String getNombreActividad() {
		return nombreActividad;
	}

	/**
	 * @param nombreActividad
	 *            the nombreActividad to set
	 */
	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	/**
	 * @return the frecuencia
	 */
	public Tipos getFrecuencia() {
		return frecuencia;
	}

	/**
	 * @param frecuencia
	 *            the frecuencia to set
	 */
	public void setFrecuencia(Tipos frecuencia) {
		this.frecuencia = frecuencia;
	}

	/**
	 * @return the responsable
	 */
	public String getResponsable() {
		return responsable;
	}

	/**
	 * @param responsable
	 *            the responsable to set
	 */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	/**
	 * @return the puedeBorrarse
	 */
	public Boolean getPuedeBorrarse() {
		return puedeBorrarse;
	}

	/**
	 * @param puedeBorrarse
	 *            the puedeBorrarse to set
	 */
	public void setPuedeBorrarse(Boolean puedeBorrarse) {
		this.puedeBorrarse = puedeBorrarse;
	}

}
