/********************************************************************************
Autor 		: Martha Correa
Clase    	: co.edu.unal.hermes.modelo.SolicitudGrupo
Objetivo 	: Clase POJO para  la  tabla HER_SOLICITUD_GRUPO, en la cual se  encuentran 
			  las  solicitudes  realiizadas  por  parte de los investigadores con 
			  respecto a un grupo de investigación.
Creación	: Octubre 23 de 2023

Modificación: 
Autor 		: 
Detalle		: 
********************************************************************************/

package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * The Class Solicitud.
 */
public class SolicitudGrupo {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant BORRADA. */
	public static final String BORRADA = "B";

	/** The Constant GUARDADA. */
	public static final String GUARDADA = "G";

	/** The Constant TRAMITE. */
	public static final String TRAMITE = "T";

	/** The Constant PENDIENTE. */
	public static final String PENDIENTE = "P";

	/** The Constant DEVUELTO_CORRECCIONES. */
	public static final String DEVUELTO_CORRECCIONES = "C";

	/** The Constant APROBADA. */
	public static final String APROBADA = "A";

	/** The id. */
	private Long id;

	/** The fecha. */
	private Date fecha;
	
	private Date fechaRespuesta;

	/** The respuesta. */
	private String respuesta;

	/** The descripcion. */
	private String descripcion;

	/** The proyecto. */
	private Grupo grupo;

	/** The tipo solicitud. */
	private TipoSolicitudGrupo tipoSolicitud;
	
	private EstadoGrupo estadoSolicitado;

	/** The responsable. */
	private Persona responsable;
	
	private String comentarios;
	
	private Dependencia dependenciaRevision; //es la dependencia del liderdel grupo que solicita
	private Persona solicitante;

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
	 * Gets the respuesta.
	 *
	 * @return the respuesta
	 */
	public String getRespuesta() {
		return respuesta;
	}

	/**
	 * Sets the respuesta.
	 *
	 * @param respuesta the new respuesta
	 */
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	/**
	 * Gets the descripcion.
	 *
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Sets the descripcion.
	 *
	 * @param descripcion the new descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	
	public String getNombreTipoSolicitud() {
		return "Creación de grupo";
	}

	public TipoSolicitudGrupo getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(TipoSolicitudGrupo tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public EstadoGrupo getEstadoSolicitado() {
		return estadoSolicitado;
	}

	public void setEstadoSolicitado(EstadoGrupo estadoSolicitado) {
		this.estadoSolicitado = estadoSolicitado;
	}

	public Dependencia getDependenciaRevision() {
		return dependenciaRevision;
	}

	public void setDependenciaRevision(Dependencia dependenciaRevision) {
		this.dependenciaRevision = dependenciaRevision;
	}

	public Persona getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Persona solicitante) {
		this.solicitante = solicitante;
	}

}
