package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 ** @author dgbenitezc
 */

public class ConvocatoriaPublicacionLibros {

	Long id;
	Investigador investigador;
	String nombreLibro;
	Date fechaRegistro;

	public ConvocatoriaPublicacionLibros() {
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
	 * @return the investigador
	 */
	public Investigador getInvestigador() {
		return investigador;
	}

	/**
	 * @param investigador
	 *            the investigador to set
	 */
	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}

	/**
	 * @return the nombreLibro
	 */
	public String getNombreLibro() {
		return nombreLibro;
	}

	/**
	 * @param nombreLibro
	 *            the nombreLibro to set
	 */
	public void setNombreLibro(String nombreLibro) {
		this.nombreLibro = nombreLibro;
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
