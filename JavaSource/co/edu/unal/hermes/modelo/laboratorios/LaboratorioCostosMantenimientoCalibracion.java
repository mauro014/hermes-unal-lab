package co.edu.unal.hermes.modelo.laboratorios;

import java.io.Serializable;

import co.edu.unal.hermes.modelo.Tipos;

/**
 * Objeto que representa la relación entre un estudio de Costos
 * (LaboratorioCostosServicio), y los costos relativos al manteniminento y/o
 * calibración de los equipos usados en el servicio
 * 
 * @author dgbenitezc
 */

public class LaboratorioCostosMantenimientoCalibracion implements Serializable {

	private static final long serialVersionUID = 7248475771271221769L;

	private LaboratorioCostosServicio costosServicio;
	private LaboratorioDetalleEquipos equipo;
	private Float costoAnual;
	private Tipos tipoActividad;
	private Integer tiempoUsoMinutos;

	public Float getCostoMinuto() {
		// 123552 = 12 meses/año * 4,29 semanas/mes * 5 dias/semana
		// * 8 horas/dia * 60 minutos/hora
		return costoAnual / 123552;
	}

	/**
	 * @return the costosServicio
	 */
	public LaboratorioCostosServicio getCostosServicio() {
		return costosServicio;
	}

	/**
	 * @param costosServicio
	 *            the costosServicio to set
	 */
	public void setCostosServicio(LaboratorioCostosServicio costosServicio) {
		this.costosServicio = costosServicio;
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
	 * @return the costoAnual
	 */
	public Float getCostoAnual() {
		return costoAnual;
	}

	/**
	 * @param costoAnual
	 *            the costoAnual to set
	 */
	public void setCostoAnual(Float costoAnual) {
		this.costoAnual = costoAnual;
	}

	/**
	 * @return the tipoActividad
	 */
	public Tipos getTipoActividad() {
		return tipoActividad;
	}

	/**
	 * @param tipoActividad
	 *            the tipoActividad to set
	 */
	public void setTipoActividad(Tipos tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	/**
	 * @return the tiempoUsoMinutos
	 */
	public Integer getTiempoUsoMinutos() {
		return tiempoUsoMinutos;
	}

	/**
	 * @param tiempoUsoMinutos
	 *            the tiempoUsoMinutos to set
	 */
	public void setTiempoUsoMinutos(Integer tiempoUsoMinutos) {
		this.tiempoUsoMinutos = tiempoUsoMinutos;
	}

}
