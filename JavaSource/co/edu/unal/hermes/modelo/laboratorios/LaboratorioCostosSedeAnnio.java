package co.edu.unal.hermes.modelo.laboratorios;

import java.io.Serializable;

import co.edu.unal.hermes.modelo.Sede;

/**
 * Objeto que representa la relación entre una Sede y un Año, y los costos
 * relativos a energía eléctrica, acueducto, alcantarillado, y valor del metro
 * cuadrado, para la realización de un estudio de Costos
 * (LaboratorioCostosServicio)
 * 
 * @author dgbenitezc
 */

public class LaboratorioCostosSedeAnnio implements Serializable {

	private static final long serialVersionUID = 8431435273792341857L;

	private Integer annio;
	private Sede sede;
	private Float valorkWh;
	private Float valorM2;
	private Float valorM3Acueducto;
	private Float valorM3Alcantarillado;

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
	 * @return the sede
	 */
	public Sede getSede() {
		return sede;
	}

	/**
	 * @param sede
	 *            the sede to set
	 */
	public void setSede(Sede sede) {
		this.sede = sede;
	}

	/**
	 * @return the valorkWh
	 */
	public Float getValorkWh() {
		return valorkWh;
	}

	/**
	 * @param valorkWh
	 *            the valorkWh to set
	 */
	public void setValorkWh(Float valorkWh) {
		this.valorkWh = valorkWh;
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
	 * @return the valorM3Acueducto
	 */
	public Float getValorM3Acueducto() {
		return valorM3Acueducto;
	}

	/**
	 * @param valorM3Acueducto
	 *            the valorM3Acueducto to set
	 */
	public void setValorM3Acueducto(Float valorM3Acueducto) {
		this.valorM3Acueducto = valorM3Acueducto;
	}

	/**
	 * @return the valorM3Alcantarillado
	 */
	public Float getValorM3Alcantarillado() {
		return valorM3Alcantarillado;
	}

	/**
	 * @param valorM3Alcantarillado
	 *            the valorM3Alcantarillado to set
	 */
	public void setValorM3Alcantarillado(Float valorM3Alcantarillado) {
		this.valorM3Alcantarillado = valorM3Alcantarillado;
	}

}
