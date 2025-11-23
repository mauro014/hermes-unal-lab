/*
 * @author Diego Felipe Garcia Cortes, Consult-Sotf
 * Fecha: Agosto 15 de 2007
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Bean que mapea la tabla "HER_TIPO_DATO" de la base de datos
 * 
 * @author Diego Felipe Garcia Cortes, Consult-Sotf
 * @version 1.0
 */
public class TipoActividadMovilidadVE implements Serializable {

	private static final long serialVersionUID = -2212694286271302514L;

	/**
	 * Atributos que hacen referencia a los campos de la tabla
	 */
	private Long id; // TDT_ID
	private String nombre; // TDT_NOMBRE
	private String vigencia; // TDT_NOMBRE

	public TipoActividadMovilidadVE() {

	}

	/**
	 * Constructor que recibe como parametro el id, para inicializar el id del
	 * objeto.
	 * 
	 * @param LongpIdTipoDato
	 */
	public TipoActividadMovilidadVE(Long id) {
		setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	public String getVigencia() {
		return vigencia;
	}

}
