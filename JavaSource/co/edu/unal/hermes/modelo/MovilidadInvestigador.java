/**
 * 
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class Movilidad.
 *
 * @author Mauro
 */
public abstract class MovilidadInvestigador extends Movilidad {

	/** The fecha aceptacion. */
	private Date fechaAceptacion;

	/** The fecha aprobacion. */
	private Date fechaAprobacion;

	/** The valor aprobado facultad. */
	private Long valorAprobadoFacultad;
	
	/** The anio vigencia aprobado facultad. */
	private Long anioVigenciaAprFacultad;
	
	/** The anio vigencia aprobado sede. */
	private Long anioVigenciaAprSede;

	/** The comentarios fac. */
	private String comentariosFac = "";

	/** The comentarios dir. */
	private String comentariosDir = "";

	/** The persona seguimiento. */
	private Persona personaSeguimiento;

	/** The estado revision seguimiento. */
	private String estadoRevisionSeguimiento;

	/** The comentarios aprobacion seguimiento. */
	private String comentariosAprobacionSeguimiento;

	/** The numero notificaciones. */
	private Integer numeroNotificaciones;

	/** The tipo notificacion. */
	private Integer tipoNotificacion;

	/** The fecha notificacion. */
	private Date fechaNotificacion;

	/** The id persona aprobacion sede. */
	private String idPersonaAprobacionSede;

	/** The tipo id persona aprobacion sede. */
	private String tipoIdPersonaAprobacionSede;

	/** The cumple requisitos. */
	private String cumpleRequisitos;

	/** The aprobacion. */
	private String aprobacion;

	/** The aceptacion. */
	private String aceptacion;
	
	private String dispPresupuestal;
	private String dispPresupuestalSede;

	/**
	 * Checks if is mostrar no aprobacion fac.
	 *
	 * @return true, if is mostrar no aprobacion fac
	 */
	public boolean isMostrarRequisitosFacultad() {
		if (this.getConvocatoria() != null
				&& StringUtils.isNotBlank(this.getConvocatoria().getCriteriosCalificacionConv())
				&& this.getConvocatoria().getCriteriosCalificacionConv().equals("CONFIGURADO_SISTEMA")) {
			return StringUtils.isBlank(this.cumpleRequisitos);
		}
		return false;
	}

	/**
	 * Checks if is mostrar no aprobacion fac.
	 *
	 * @return true, if is mostrar no aprobacion fac
	 */
	public boolean isMostrarNoAprobacionFac() {
		if (this.getConvocatoria() != null
				&& StringUtils.isNotBlank(this.getConvocatoria().getCriteriosCalificacionConv())
				&& this.getConvocatoria().getCriteriosCalificacionConv().equals("CONFIGURADO_SISTEMA")) {
			return StringUtils.isNotBlank(this.cumpleRequisitos) && this.cumpleRequisitos.equals("SI");
		}
		return true;
	}

	/**
	 * Checks if is mostrar aprobacion fac con criterios.
	 *
	 * @return true, if is mostrar aprobacion fac con criterios
	 */
	public boolean isMostrarAprobacionFacConCriterios() {
		return mostrarAprobacionFac(true);
	}

	/**
	 * Mostrar aprobacion fac sin criterios.
	 *
	 * @return true, if successful
	 */
	public boolean isMostrarAprobacionFacSinCriterios() {
		return mostrarAprobacionFac(false);
	}

	/**
	 * Mostrar aprobacion fac.
	 *
	 * @param tieneCriterios
	 *            the tiene criterios
	 * @return true, if successful
	 */
	public boolean mostrarAprobacionFac(boolean tieneCriterios) {
		if (this.getConvocatoria() != null && this.isEsFechaValidaAprobacion()) {
			if (StringUtils.isNotBlank(this.getConvocatoria().getCriteriosCalificacionConv())
					&& this.getConvocatoria().getCriteriosCalificacionConv().equals("CONFIGURADO_SISTEMA")) {
				return StringUtils.isNotBlank(this.cumpleRequisitos) && this.cumpleRequisitos.equals("SI")
						|| !tieneCriterios;
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the fecha aceptacion.
	 *
	 * @return the fechaAceptacion
	 */
	public Date getFechaAceptacion() {
		return fechaAceptacion;
	}

	/**
	 * Sets the fecha aceptacion.
	 *
	 * @param fechaAceptacion
	 *            the fechaAceptacion to set
	 */
	public void setFechaAceptacion(Date fechaAceptacion) {
		this.fechaAceptacion = fechaAceptacion;
	}

	/**
	 * Gets the fecha aprobacion.
	 *
	 * @return the fechaAprobacion
	 */
	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	/**
	 * Sets the fecha aprobacion.
	 *
	 * @param fechaAprobacion
	 *            the fechaAprobacion to set
	 */
	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	/**
	 * Gets the valor aprobado facultad.
	 *
	 * @return the valorAprobadoFacultad
	 */
	public Long getValorAprobadoFacultad() {
		return valorAprobadoFacultad;
	}

	/**
	 * Sets the valor aprobado facultad.
	 *
	 * @param valorAprobadoFacultad
	 *            the valorAprobadoFacultad to set
	 */
	public void setValorAprobadoFacultad(Long valorAprobadoFacultad) {
		this.valorAprobadoFacultad = valorAprobadoFacultad;
	}

	/**
	 * Gets the persona seguimiento.
	 *
	 * @return the personaSeguimiento
	 */
	public Persona getPersonaSeguimiento() {
		return personaSeguimiento;
	}

	/**
	 * Sets the persona seguimiento.
	 *
	 * @param personaSeguimiento
	 *            the personaSeguimiento to set
	 */
	public void setPersonaSeguimiento(Persona personaSeguimiento) {
		this.personaSeguimiento = personaSeguimiento;
	}

	/**
	 * Gets the estado revision seguimiento.
	 *
	 * @return the estadoRevisionSeguimiento
	 */
	public String getEstadoRevisionSeguimiento() {
		return estadoRevisionSeguimiento;
	}

	/**
	 * Sets the estado revision seguimiento.
	 *
	 * @param estadoRevisionSeguimiento
	 *            the estadoRevisionSeguimiento to set
	 */
	public void setEstadoRevisionSeguimiento(String estadoRevisionSeguimiento) {
		this.estadoRevisionSeguimiento = estadoRevisionSeguimiento;
	}

	/**
	 * Gets the comentarios fac.
	 *
	 * @return the comentariosFac
	 */
	public String getComentariosFac() {
		return comentariosFac;
	}

	/**
	 * Sets the comentarios fac.
	 *
	 * @param comentariosFac
	 *            the comentariosFac to set
	 */
	public void setComentariosFac(String comentariosFac) {
		this.comentariosFac = comentariosFac;
	}

	/**
	 * Gets the comentarios dir.
	 *
	 * @return the comentariosDir
	 */
	public String getComentariosDir() {
		return comentariosDir;
	}

	/**
	 * Sets the comentarios dir.
	 *
	 * @param comentariosDir
	 *            the comentariosDir to set
	 */
	public void setComentariosDir(String comentariosDir) {
		this.comentariosDir = comentariosDir;
	}

	/**
	 * Gets the comentarios aprobacion seguimiento.
	 *
	 * @return the comentariosAprobacionSeguimiento
	 */
	public String getComentariosAprobacionSeguimiento() {
		return comentariosAprobacionSeguimiento;
	}

	/**
	 * Sets the comentarios aprobacion seguimiento.
	 *
	 * @param comentariosAprobacionSeguimiento
	 *            the comentariosAprobacionSeguimiento to set
	 */
	public void setComentariosAprobacionSeguimiento(String comentariosAprobacionSeguimiento) {
		this.comentariosAprobacionSeguimiento = comentariosAprobacionSeguimiento;
	}

	/**
	 * Gets the numero notificaciones.
	 *
	 * @return the numeroNotificaciones
	 */
	public Integer getNumeroNotificaciones() {
		return numeroNotificaciones;
	}

	/**
	 * Sets the numero notificaciones.
	 *
	 * @param numeroNotificaciones
	 *            the numeroNotificaciones to set
	 */
	public void setNumeroNotificaciones(Integer numeroNotificaciones) {
		this.numeroNotificaciones = numeroNotificaciones;
	}

	/**
	 * Gets the tipo notificacion.
	 *
	 * @return the tipoNotificacion
	 */
	public Integer getTipoNotificacion() {
		return tipoNotificacion;
	}

	/**
	 * Sets the tipo notificacion.
	 *
	 * @param tipoNotificacion
	 *            the tipoNotificacion to set
	 */
	public void setTipoNotificacion(Integer tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}

	/**
	 * Gets the fecha notificacion.
	 *
	 * @return the fechaNotificacion
	 */
	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	/**
	 * Sets the fecha notificacion.
	 *
	 * @param fechaNotificacion
	 *            the fechaNotificacion to set
	 */
	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	/**
	 * Gets the id persona aprobacion sede.
	 *
	 * @return the idPersonaAprobacionSede
	 */
	public String getIdPersonaAprobacionSede() {
		return idPersonaAprobacionSede;
	}

	/**
	 * Sets the id persona aprobacion sede.
	 *
	 * @param idPersonaAprobacionSede
	 *            the idPersonaAprobacionSede to set
	 */
	public void setIdPersonaAprobacionSede(String idPersonaAprobacionSede) {
		this.idPersonaAprobacionSede = idPersonaAprobacionSede;
	}

	/**
	 * Gets the tipo id persona aprobacion sede.
	 *
	 * @return the tipoIdPersonaAprobacionSede
	 */
	public String getTipoIdPersonaAprobacionSede() {
		return tipoIdPersonaAprobacionSede;
	}

	/**
	 * Sets the tipo id persona aprobacion sede.
	 *
	 * @param tipoIdPersonaAprobacionSede
	 *            the tipoIdPersonaAprobacionSede to set
	 */
	public void setTipoIdPersonaAprobacionSede(String tipoIdPersonaAprobacionSede) {
		this.tipoIdPersonaAprobacionSede = tipoIdPersonaAprobacionSede;
	}

	/**
	 * Gets the cumple requisitos.
	 *
	 * @return the cumpleRequisitos
	 */
	public String getCumpleRequisitos() {
		return cumpleRequisitos;
	}

	/**
	 * Sets the cumple requisitos.
	 *
	 * @param cumpleRequisitos
	 *            the cumpleRequisitos to set
	 */
	public void setCumpleRequisitos(String cumpleRequisitos) {
		this.cumpleRequisitos = cumpleRequisitos;
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
	 * @param aprobacion
	 *            the new aprobacion
	 */
	public void setAprobacion(String aprobacion) {
		this.aprobacion = aprobacion;
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
	 * @param aceptacion
	 *            the new aceptacion
	 */
	public void setAceptacion(String aceptacion) {
		this.aceptacion = aceptacion;
	}

	/**
	 * Gets the estado nombre.
	 *
	 * @return the estado nombre
	 */
	public String getEstadoNombre() {
		return getEstadoNombre(this.aceptacion, this.aprobacion);
	}

	public Long getAnioVigenciaAprFacultad() {
		return anioVigenciaAprFacultad;
	}

	public void setAnioVigenciaAprFacultad(Long anioVigenciaAprFacultad) {
		this.anioVigenciaAprFacultad = anioVigenciaAprFacultad;
	}

	public Long getAnioVigenciaAprSede() {
		return anioVigenciaAprSede;
	}

	public void setAnioVigenciaAprSede(Long anioVigenciaAprSede) {
		this.anioVigenciaAprSede = anioVigenciaAprSede;
	}

	public String getDispPresupuestal() {
		return dispPresupuestal;
	}

	public void setDispPresupuestal(String dispPresupuestal) {
		this.dispPresupuestal = dispPresupuestal;
	}

	public String getDispPresupuestalSede() {
		return dispPresupuestalSede;
	}

	public void setDispPresupuestalSede(String dispPresupuestalSede) {
		this.dispPresupuestalSede = dispPresupuestalSede;
	}

	/**
	 * Gets the estado nombre.
	 *
	 * @param aceptacion
	 *            the aceptacion
	 * @param aprobacion
	 *            the aprobacion
	 * @return the estado nombre
	 */
	public static String getEstadoNombre(String aceptacion, String aprobacion) {
		if (StringUtils.isBlank(aceptacion) && StringUtils.isBlank(aprobacion)) {
			return "Enviado";
		} else if ("NO".equals(aceptacion) && StringUtils.isBlank(aprobacion)) {
			return "No aprobado facultad";
		} else if ("SI".equals(aceptacion) && StringUtils.isBlank(aprobacion)) {
			return "Aprobado facultad";
		} else if ("NO".equals(aprobacion)) {
			return "No aprobado sede";
		} else if ("SI".equals(aprobacion)) {
			return "Aprobado sede";
		}
		return "Estado indeterminado";
	}

}
