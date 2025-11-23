package co.edu.unal.hermes.modelo.laboratorios;

/**
 * Objeto que relaciona a personal (mano de obra) con un estudio de costos
 * (LaboratorioCostosServicio)
 * 
 * @author dgbenitezc
 */

public class LaboratorioCostosPersonal {

	private Long id;
	private LaboratorioCostosServicio costosServicio;
	private String nombrePersonal;
	private Long salarioMensual;
	private Integer tiempoMinutos;

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
	 * @return the nombrePersonal
	 */
	public String getNombrePersonal() {
		return nombrePersonal;
	}

	/**
	 * @param nombrePersonal
	 *            the nombrePersonal to set
	 */
	public void setNombrePersonal(String nombrePersonal) {
		this.nombrePersonal = nombrePersonal;
	}

	/**
	 * @return the salarioMensual
	 */
	public Long getSalarioMensual() {
		return salarioMensual;
	}

	/**
	 * @param salarioMensual
	 *            the salarioMensual to set
	 */
	public void setSalarioMensual(Long salarioMensual) {
		this.salarioMensual = salarioMensual;
	}

	/**
	 * @return the tiempoMinutos
	 */
	public Integer getTiempoMinutos() {
		return tiempoMinutos;
	}

	/**
	 * @param tiempoMinutos
	 *            the tiempoMinutos to set
	 */
	public void setTiempoMinutos(Integer tiempoMinutos) {
		this.tiempoMinutos = tiempoMinutos;
	}

	public Float getValorMinuto() {
		// salario ($/mes) / (4,29 semanas/mes * 40 horas/semana * 60
		// minutos/hora)
		Float valorMinuto = (float) salarioMensual / (4.29F * 40 * 60);
		return valorMinuto;
	}

	public Float getCosto() {
		Float costo = getValorMinuto() * tiempoMinutos;
		return costo;
	}

}
