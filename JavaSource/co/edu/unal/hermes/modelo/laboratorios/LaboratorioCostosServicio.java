package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

/**
 * Objeto que representa la relación entre un Servicio/Ensayo de laboratorio
 * (LaboratorioDetalleEnsayosServicios) con todos los Costos asociados a su
 * realización
 * 
 * @author dgbenitezc
 */

public class LaboratorioCostosServicio {

	private Long id;
	private LaboratorioDetalleEnsayosServicios servicio;
	private Integer annio;
	private Float valorM2;

	private String iluminacionNombre;
	private Float iluminacionPotenciaW;
	private Integer iluminacionTiempoMinutos;

	private Float consumoAcueductoM3;
	private Float consumoAlcantarilladoM3;

	private Date fechaRegistro;

	/**
	 * @return the servicio
	 */
	public LaboratorioDetalleEnsayosServicios getServicio() {
		return servicio;
	}

	/**
	 * @param servicio
	 *            the servicio to set
	 */
	public void setServicio(LaboratorioDetalleEnsayosServicios servicio) {
		this.servicio = servicio;
	}

	/**
	 * @return the annio
	 */
	public Integer getAnnio() {
		return annio;
	}

	/**
	 * @param annio
	 *            the annio to set
	 */
	public void setAnnio(Integer annio) {
		this.annio = annio;
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
	 * @return the valorM2
	 */
	public Float getValorM2() {
		return valorM2;
	}

	/**
	 * @param valorM2
	 *            the valorM2 to set
	 */
	public void setValorM2(Float valorM2) {
		this.valorM2 = valorM2;
	}

	/**
	 * @return the iluminacionNombre
	 */
	public String getIluminacionNombre() {
		return iluminacionNombre;
	}

	/**
	 * @param iluminacionNombre
	 *            the iluminacionNombre to set
	 */
	public void setIluminacionNombre(String iluminacionNombre) {
		this.iluminacionNombre = iluminacionNombre;
	}

	/**
	 * @return the iluminacionPotenciaW
	 */
	public Float getIluminacionPotenciaW() {
		return iluminacionPotenciaW;
	}

	/**
	 * @param iluminacionPotenciaW
	 *            the iluminacionPotenciaW to set
	 */
	public void setIluminacionPotenciaW(Float iluminacionPotenciaW) {
		this.iluminacionPotenciaW = iluminacionPotenciaW;
	}

	/**
	 * @return the iluminacionTiempoMinutos
	 */
	public Integer getIluminacionTiempoMinutos() {
		return iluminacionTiempoMinutos;
	}

	/**
	 * @param iluminacionTiempoMinutos
	 *            the iluminacionTiempoMinutos to set
	 */
	public void setIluminacionTiempoMinutos(Integer iluminacionTiempoMinutos) {
		this.iluminacionTiempoMinutos = iluminacionTiempoMinutos;
	}

	/**
	 * @return the consumoAcueductoM3
	 */
	public Float getConsumoAcueductoM3() {
		return consumoAcueductoM3;
	}

	/**
	 * @param consumoAcueductoM3
	 *            the consumoAcueductoM3 to set
	 */
	public void setConsumoAcueductoM3(Float consumoAcueductoM3) {
		this.consumoAcueductoM3 = consumoAcueductoM3;
	}

	/**
	 * @return the consumoAlcantarilladoM3
	 */
	public Float getConsumoAlcantarilladoM3() {
		return consumoAlcantarilladoM3;
	}

	/**
	 * @param consumoAlcantarilladoM3
	 *            the consumoAlcantarilladoM3 to set
	 */
	public void setConsumoAlcantarilladoM3(Float consumoAlcantarilladoM3) {
		this.consumoAlcantarilladoM3 = consumoAlcantarilladoM3;
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

}