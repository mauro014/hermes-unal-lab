package co.edu.unal.hermes.modelo.laboratorios;

/**
 * Objeto que representa la relación entre un estudio de Costos
 * (LaboratorioCostosServicio), y los costos relativos a la Gestión del
 * Laboratorio: Acreditación, Certificación, Habilitación, Registro ICA,
 * Licencias, etc.
 * 
 * @author dgbenitezc
 */

public class LaboratorioCostosGestion {
	private Long id;
	private LaboratorioCostosServicio costosServicio;
	private String nombreGestion;
	private Float costoAnual;
	private Integer tiempoUsoMinutos;

	public Float getCostoMinuto() {
		// 123552 = 12 meses/año * 4,29 semanas/mes * 5 dias/semana
		// * 8 horas/dia * 60 minutos/hora
		return costoAnual / 123552;
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
	 * @return the nombreGestion
	 */
	public String getNombreGestion() {
		return nombreGestion;
	}

	/**
	 * @param nombreGestion
	 *            the nombreGestion to set
	 */
	public void setNombreGestion(String nombreGestion) {
		this.nombreGestion = nombreGestion;
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
