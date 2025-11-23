/**
 * Modificado por Mauricio
 */

package co.edu.unal.hermes.vista.grupos;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;

/**
 * The Class GrupoVista.
 */
public class GrupoVista {

	/** The id. */
	private Long id;

	/** The nombre grupo. */
	private String nombreGrupo;

	/** The inv grupo principal. */
	private boolean invGrupoPrincipal;

	/** The inv grupo principal. */
	private boolean invEstudianteLider;
	
	private boolean invAsistenteLider;

	/** The inv docente. */
	private boolean invDocente;

	/** The es propuesto. */
	private boolean esPropuesto;

	/** The es devuelto. */
	private boolean esDevuelto;

	/** The correcciones. */
	private boolean correcciones;

	/** The inactivo. */
	private boolean inactivo;
	
	/** The activo. */
	private boolean activo;

	/** The ingresando. */
	private boolean ingresando;

	/** The solicitud aval. */
	private boolean solicitudAval;

	/** The plan accion ver. */
	private boolean planAccionVer = false;

	/** The estado colciencias. */
	private String estadoColciencias;
	
	/** The intersedes. */
	private String intersedes;

	/** The interfacultades. */
	private String interfacultades;
	
	private String interinstitucion;
	
	private String archivoAval;
	
	private Sede sede;
	private Dependencia dependencia;
	private Persona responsable;


	/**
	 * Gets the es propuesto.
	 *
	 * @return the es propuesto
	 */
	public boolean getEsPropuesto() {
		return esPropuesto;
	}

	/**
	 * Gets the estado colciencias.
	 *
	 * @return the estado colciencias
	 */
	public String getEstadoColciencias() {
		return estadoColciencias;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the nombre grupo.
	 *
	 * @return the nombre grupo
	 */
	public String getNombreGrupo() {
		return nombreGrupo;
	}

	/**
	 * Gets the plan.
	 *
	 * @return the plan
	 */
	public boolean getPlan() {
		return planAccionVer;
	}

	/**
	 * Checks if is correcciones.
	 *
	 * @return true, if is correcciones
	 */
	public boolean isCorrecciones() {
		return correcciones;
	}

	/**
	 * Checks if is es devuelto.
	 *
	 * @return true, if is es devuelto
	 */
	public boolean isEsDevuelto() {
		return esDevuelto;
	}

	/**
	 * Checks if is inactivo.
	 *
	 * @return true, if is inactivo
	 */
	public boolean isInactivo() {
		return inactivo;
	}

	/**
	 * Checks if is ingresando.
	 *
	 * @return true, if is ingresando
	 */
	public boolean isIngresando() {
		return ingresando;
	}

	/**
	 * Checks if is inv docente.
	 *
	 * @return true, if is inv docente
	 */
	public boolean isInvDocente() {
		return invDocente;
	}

	/**
	 * Checks if is inv grupo principal.
	 *
	 * @return true, if is inv grupo principal
	 */
	public boolean isInvGrupoPrincipal() {
		return invGrupoPrincipal;
	}

	/**
	 * Checks if is solicitud aval.
	 *
	 * @return true, if is solicitud aval
	 */
	public boolean isSolicitudAval() {
		return solicitudAval;
	}

	/**
	 * Sets the correcciones.
	 *
	 * @param correcciones
	 *            the new correcciones
	 */
	public void setCorrecciones(boolean correcciones) {
		this.correcciones = correcciones;
	}

	/**
	 * Sets the es devuelto.
	 *
	 * @param esDevuelto
	 *            the new es devuelto
	 */
	public void setEsDevuelto(boolean esDevuelto) {
		this.esDevuelto = esDevuelto;
	}

	/**
	 * Sets the es propuesto.
	 *
	 * @param esPropuesto
	 *            the new es propuesto
	 */
	public void setEsPropuesto(boolean esPropuesto) {
		this.esPropuesto = esPropuesto;
	}

	/**
	 * Sets the estado colciencias.
	 *
	 * @param estadoColciencias
	 *            the new estado colciencias
	 */
	public void setEstadoColciencias(String estadoColciencias) {
		this.estadoColciencias = estadoColciencias;
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
	 * Sets the inactivo.
	 *
	 * @param inactivo
	 *            the new inactivo
	 */
	public void setInactivo(boolean inactivo) {
		this.inactivo = inactivo;
	}

	/**
	 * Sets the ingresando.
	 *
	 * @param ingresando
	 *            the new ingresando
	 */
	public void setIngresando(boolean ingresando) {
		this.ingresando = ingresando;
	}

	/**
	 * Sets the inv docente.
	 *
	 * @param invDocente
	 *            the new inv docente
	 */
	public void setInvDocente(boolean invDocente) {
		this.invDocente = invDocente;
	}

	/**
	 * Sets the inv grupo principal.
	 *
	 * @param invGrupoPrincipal
	 *            the new inv grupo principal
	 */
	public void setInvGrupoPrincipal(boolean invGrupoPrincipal) {
		this.invGrupoPrincipal = invGrupoPrincipal;
	}

	/**
	 * Sets the nombre grupo.
	 *
	 * @param nombreGrupo
	 *            the new nombre grupo
	 */
	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	/**
	 * Sets the plan accion ver.
	 *
	 * @param planAccionVer
	 *            the new plan accion ver
	 */
	public void setPlanAccionVer(boolean planAccionVer) {
		this.planAccionVer = planAccionVer;
	}

	/**
	 * Sets the solicitud aval.
	 *
	 * @param solicitudAval
	 *            the new solicitud aval
	 */
	public void setSolicitudAval(boolean solicitudAval) {
		this.solicitudAval = solicitudAval;
	}

	public boolean isInvEstudianteLider() {
		return invEstudianteLider;
	}

	public void setInvEstudianteLider(boolean invEstudianteLider) {
		this.invEstudianteLider = invEstudianteLider;
	}
	
	public boolean getEsInterfacultades() {
		if(this.interfacultades!=null && this.interfacultades.equals("S")) {
			return true;
		}
		return false;
	}
	
	public boolean getEsIntersedes() {
		if(this.intersedes!=null && this.intersedes.equals("S")) {
			return true;
		}
		return false;
	}
	
	public boolean getEsInsterinstitucional() {
		if(this.interinstitucion!=null && this.interinstitucion.equals("S")) {
			return true;
		}
		return false;
	}

	public String getIntersedes() {
		return intersedes;
	}

	public void setIntersedes(String intersedes) {
		this.intersedes = intersedes;
	}

	public String getInterfacultades() {
		return interfacultades;
	}

	public void setInterfacultades(String interfacultades) {
		this.interfacultades = interfacultades;
	}

	public String getInterinstitucion() {
		return interinstitucion;
	}

	public void setInterinstitucion(String interinstitucion) {
		this.interinstitucion = interinstitucion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getArchivoAval() {
		return archivoAval;
	}

	public void setArchivoAval(String archivoAval) {
		this.archivoAval = archivoAval;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public Dependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	public boolean isInvAsistenteLider() {
		return invAsistenteLider;
	}

	public void setInvAsistenteLider(boolean invAsistenteLider) {
		this.invAsistenteLider = invAsistenteLider;
	}

}
