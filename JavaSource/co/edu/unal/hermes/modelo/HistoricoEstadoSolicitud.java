package co.edu.unal.hermes.modelo;

import java.util.Date;

import co.edu.unal.hermes.modelo.seguimiento.Solicitud;

/**
 * The Class HistoricoEstadoSolicitud.
 */
public class HistoricoEstadoSolicitud {

	/** The id. */
	private Long id;
	
	/** The estado informe. */
	private String respuesta;
	
	/** The solicitud. */
	private Solicitud solicitud;
	
	/** The fecha. */
	private Date fecha;
	
	/** The comentarios. */
	private String comentarios;
	
	/** The responsable. */
	private Persona responsable;

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
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @param fecha the new fecha
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
	 * @param responsable the new responsable
	 */
	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	/**
	 * Gets the comentarios.
	 *
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}

	/**
	 * Sets the comentarios.
	 *
	 * @param comentarios the new comentarios
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * Gets the solicitud.
	 *
	 * @return the solicitud
	 */
	public Solicitud getSolicitud() {
		return solicitud;
	}

	/**
	 * Sets the solicitud.
	 *
	 * @param solicitud the solicitud to set
	 */
	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	/**
	 * @return the respuesta
	 */
	public String getRespuesta() {
		return respuesta;
	}

	/**
	 * @param respuesta the respuesta to set
	 */
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

}
