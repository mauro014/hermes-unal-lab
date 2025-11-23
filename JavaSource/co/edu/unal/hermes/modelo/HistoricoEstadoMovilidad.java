package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * The Class HistoricoEstadoMovilidad.
 */
public class HistoricoEstadoMovilidad {

	/** The id. */
	private Long id;

	/** The estado aceptacion. */
	private String aceptacion;

	/** The estado aprobacion. */
	private String aprobacion;

	/** The id movilidad. */
	private Long idMovilidad;

	/** The fecha. */
	private Date fecha;

	/** The responsable. */
	private Persona responsable;
	
	/** The justificacion. */
	private String justificacion;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the id movilidad.
	 *
	 * @return the id movilidad
	 */
	public Long getIdMovilidad() {
		return idMovilidad;
	}

	/**
	 * Sets the id movilidad.
	 *
	 * @param idMovilidad
	 *            the new id movilidad
	 */
	public void setIdMovilidad(Long idMovilidad) {
		this.idMovilidad = idMovilidad;
	}

	/**
	 * Gets the fecha.
	 *
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Sets the fecha.
	 *
	 * @param fecha
	 *            the new fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Gets the responsable.
	 *
	 * @return the responsable
	 */
	public Persona getResponsable() {
		return responsable;
	}

	/**
	 * Sets the responsable.
	 *
	 * @param responsable
	 *            the new responsable
	 */
	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	/**
	 * Gets the aceptacion.
	 *
	 * @return the aceptacion
	 */
	public String getAceptacion() {
		return aceptacion;
	}

	/**
	 * Sets the aceptacion.
	 *
	 * @param aceptacion the new aceptacion
	 */
	public void setAceptacion(String aceptacion) {
		this.aceptacion = aceptacion;
	}

	/**
	 * Gets the aprobacion.
	 *
	 * @return the aprobacion
	 */
	public String getAprobacion() {
		return aprobacion;
	}

	/**
	 * Sets the aprobacion.
	 *
	 * @param aprobacion the new aprobacion
	 */
	public void setAprobacion(String aprobacion) {
		this.aprobacion = aprobacion;
	}

	/**
	 * Gets the justificacion.
	 *
	 * @return the justificacion
	 */
	public String getJustificacion() {
		return justificacion;
	}

	/**
	 * Sets the justificacion.
	 *
	 * @param justificacion the new justificacion
	 */
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}
	
	/**
	 * Gets the estado.
	 *
	 * @return the estado
	 */
	public String getEstado() {
		return MovilidadInvestigador.getEstadoNombre(this.aceptacion, this.aprobacion);
	}

}
