package co.edu.unal.hermes.modelo.laboratorios;

import java.io.Serializable;

/**
 * Objeto que relaciona a un estudio de costos (LaboratorioCostosServicio), con
 * el costo de un insumo (InsumoLaboratorio) usado en un servicio/ensayo de
 * laboratorio
 * 
 * @author dgbenitezc
 */

public class CostoInsumoServicio implements Serializable {

	private static final long serialVersionUID = 2672564291314511164L;

	private LaboratorioCostosServicio costosServicio;
	private InsumoLaboratorio insumo;
	private Float cantidadUnidadesInsumo;
	private Float costoUnidad;

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
	 * @return the insumo
	 */
	public InsumoLaboratorio getInsumo() {
		return insumo;
	}

	/**
	 * @param insumo
	 *            the insumo to set
	 */
	public void setInsumo(InsumoLaboratorio insumo) {
		this.insumo = insumo;
	}

	/**
	 * @return the cantidadUnidadesInsumo
	 */
	public Float getCantidadUnidadesInsumo() {
		return cantidadUnidadesInsumo;
	}

	/**
	 * @param cantidadUnidadesInsumo
	 *            the cantidadUnidadesInsumo to set
	 */
	public void setCantidadUnidadesInsumo(Float cantidadUnidadesInsumo) {
		this.cantidadUnidadesInsumo = cantidadUnidadesInsumo;
	}

	/**
	 * @return the costoUnidad
	 */
	public Float getCostoUnidad() {
		return costoUnidad;
	}

	/**
	 * @param costoUnidad
	 *            the costoUnidad to set
	 */
	public void setCostoUnidad(Float costoUnidad) {
		this.costoUnidad = costoUnidad;
	}
	
	public Float getCostoInsumo () {
		return cantidadUnidadesInsumo * costoUnidad;
	}

}
