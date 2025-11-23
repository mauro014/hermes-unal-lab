package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;


/**
 * The Class ProyectoInforme.
 */
public class ProyectoLegalizacionOcad implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5143647286089532035L;

	/** The id. */
	private Long id;

	/** The proyecto. */
	private Long idProyecto;

	/** The codigoSigp. */
	private String codigoSigp;

	/** The codigoBpin. */
	private String codigoBpin;

	/** The ocadAprobo. */
	private String ocadAprobo;

	/** The numeroAprobacionOcad. */
	private String numeroAprobacionOcad;

	/** The fechaAprobacionOcad */
	private Date fechaAprobacionOcad;

	/** The directorProyecto */
	private Persona directorProyecto;

	/** The nombreSupervisor */
	private String nombreSupervisor;
	
	/** The entidadEjecutora. */
	private String entidadEjecutora; // Solo se guarda el id pero está asociado en la base de datos a la tabla HER_FUENTEFINANCIACION FFI_ID

	/** The tieneProyectoExtension. */
	private String tieneProyectoExtension;
	
	/** The tieneProyectoExtension. */
	private String codigoProyectoExtension;

	/** The valorAprobadoSgr. */
	private Long valorAprobadoSgr;

	/** The valorOtraFinanciacion. */
	private Long valorOtraFinanciacion;

	/** The valorParticipantes. */
	private Long valorParticipantes;
	
	private String rolUniversidad;

	/**
	 * Instantiates a new proyecto informe.
	 */
	//
	public ProyectoLegalizacionOcad() {
		this.directorProyecto = new Persona();
		rolUniversidad = "";
	}
	
	public ProyectoLegalizacionOcad(ProyectoLegalizacionOcad plo) {
		this.id = plo.getId();
		this.idProyecto = plo.getIdProyecto();
		this.codigoSigp = plo.getCodigoSigp();

		/** The codigoBpin.*/
		this.codigoBpin = plo.getCodigoBpin();

		/** The ocadAprobo.*/
		this.ocadAprobo = plo.getOcadAprobo();

		/** The numeroAprobacionOcad.*/
		this.numeroAprobacionOcad = plo.getNumeroAprobacionOcad();

		/** The fechaAprobacionOcad */
		this.fechaAprobacionOcad = plo.getFechaAprobacionOcad();

		/** The nombreSupervisor */
		this.nombreSupervisor = plo.getNombreSupervisor();

		/** The tieneProyectoExtension.*/
		this.tieneProyectoExtension = plo.getTieneProyectoExtension();
		
		/** The tieneProyectoExtension.*/
		this.codigoProyectoExtension = plo.getCodigoProyectoExtension();

		/** The valorAprobadoSgr.*/
		this.valorAprobadoSgr = plo.getValorAprobadoSgr();

		/** The valorOtraFinanciacion.*/
		this.valorOtraFinanciacion = plo.getValorOtraFinanciacion();

		/** The valorParticipantes.*/
		this.valorParticipantes = plo.getValorParticipantes();
		
		this.rolUniversidad = plo.getRolUniversidad();
		this.entidadEjecutora = plo.getEntidadEjecutora();
	}


	/**
	 * Gets the proyecto.
	 *

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

	public String getCodigoSigp() {
		return codigoSigp;
	}

	public void setCodigoSigp(String codigoSigp) {
		this.codigoSigp = codigoSigp;
	}

	public String getCodigoBpin() {
		return codigoBpin;
	}

	public void setCodigoBpin(String codigoBpin) {
		this.codigoBpin = codigoBpin;
	}

	public String getOcadAprobo() {
		return ocadAprobo;
	}

	public void setOcadAprobo(String ocadAprobo) {
		this.ocadAprobo = ocadAprobo;
	}

	public String getNumeroAprobacionOcad() {
		return numeroAprobacionOcad;
	}

	public void setNumeroAprobacionOcad(String numeroAprobacionOcad) {
		this.numeroAprobacionOcad = numeroAprobacionOcad;
	}

	public Date getFechaAprobacionOcad() {
		return fechaAprobacionOcad;
	}

	public void setFechaAprobacionOcad(Date fechaAprobacionOcad) {
		this.fechaAprobacionOcad = fechaAprobacionOcad;
	}

	public Persona getDirectorProyecto() {
		return directorProyecto;
	}

	public void setDirectorProyecto(Persona directorProyecto) {
		this.directorProyecto = directorProyecto;
	}

	public String getNombreSupervisor() {
		return nombreSupervisor;
	}

	public void setNombreSupervisor(String nombreSupervisor) {
		this.nombreSupervisor = nombreSupervisor;
	}

	public String getTieneProyectoExtension() {
		return tieneProyectoExtension;
	}

	public void setTieneProyectoExtension(String tieneProyectoExtension) {
		this.tieneProyectoExtension = tieneProyectoExtension;
	}

	public String getCodigoProyectoExtension() {
		return codigoProyectoExtension;
	}

	public void setCodigoProyectoExtension(String codigoProyectoExtension) {
		this.codigoProyectoExtension = codigoProyectoExtension;
	}

	public Long getValorAprobadoSgr() {
		return valorAprobadoSgr;
	}

	public void setValorAprobadoSgr(Long valorAprobadoSgr) {
		this.valorAprobadoSgr = valorAprobadoSgr;
	}

	public Long getValorOtraFinanciacion() {
		return valorOtraFinanciacion;
	}

	public void setValorOtraFinanciacion(Long valorOtraFinanciacion) {
		this.valorOtraFinanciacion = valorOtraFinanciacion;
	}

	public Long getValorParticipantes() {
		return valorParticipantes;
	}

	public void setValorParticipantes(Long valorParticipantes) {
		this.valorParticipantes = valorParticipantes;
	}

	public String getRolUniversidad() {
		return rolUniversidad;
	}

	public void setRolUniversidad(String rolUniversidad) {
		this.rolUniversidad = rolUniversidad;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getEntidadEjecutora() {
		return entidadEjecutora;
	}

	public void setEntidadEjecutora(String entidadEjecutora) {
		this.entidadEjecutora = entidadEjecutora;
	}


	

}