/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Maneja los objetivos específicos que enmarcan cada proyecto.
 */
public class InvestigadorGrupo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6659114633554677720L;

	/** The lider. */
	public static final String LIDER = "L";

	/** The codirector. */
	public static final String CODIRECTOR = "C";

	/** The externo. */
	public static final String EXTERNO = "E";

	/** The interno. */
	public static final String INTERNO = "I";
	
	/** The estudiante. */
	public static final String ESTUDIANTE = "A";
	
	/** The estudiante. */
	public static final String ESTUDIANTE_PREGRADO = "APR";
	
	/** The estudiante. */
	public static final String ESTUDIANTE_POSGRADO = "APO";
	
	/** The estudiante LIDER. */
	public static final String ESTUDIANTE_LIDER = "AL";
	
	public static final String ASISTENTE_LIDER = "ADL";
	
	/** The estudiante VISITANTE. */
	public static final String ESTUDIANTE_VISITANTE = "AV";

	/** The docente. */
	public static final String DOCENTE = "D";
	
	/** The docente. */
	public static final String ADMINISTRATIVO = "AD";
	
	public static final String DOCENTE_IPARM = "DI";

	/** The id. */
	private Long id;

	/** The investigador. */
	private Investigador investigador;

	/** The grupo. */
	private Grupo grupo;

	/** The tipo. */
	private String tipo;

	/** The funcion. */
	private String funcion;

	/** The plan estudiante. */
	private PlanEstudios planEstudioEstudiante;
	private String planEstudiante;
	private String nombrePlanEstudiante; // Se utiliza para la vista

	/** The tipo. */
	private String tipoVinculacion;
	private String nombreAreaOcde;
	private String nombreSubAreaOcde;
	private String nombreInstitucion;
	
	public static final String EGRESADO = "O";

	/**
	 * Gets the tipo investigador.
	 *
	 * @return the tipo investigador
	 */
	public String getTipoInvestigador() {
		if (tipo.equals(LIDER)) {
			return "Lider";
		} else if (tipo.equals(DOCENTE)) {
			return "Docente";
		} else if (tipo.equals(ESTUDIANTE)) {
			return "Estudiante";
		} else if (tipo.equals(EXTERNO)) {
			return "Externo";
		} else {
			return "";
		}
	}

	/**
	 * Gets the grupo.
	 *
	 * @return the grupo
	 */
	public Grupo getGrupo() {
		return grupo;
	}

	/**
	 * Sets the grupo.
	 *
	 * @param grupo
	 *            the new grupo
	 */
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	/**
	 * Gets the tipo.
	 *
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Sets the tipo.
	 *
	 * @param tipo
	 *            the new tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the investigador.
	 *
	 * @return the investigador
	 */
	public Investigador getInvestigador() {
		return investigador;
	}

	/**
	 * Sets the investigador.
	 *
	 * @param investigador
	 *            the new investigador
	 */
	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}

	/**
	 * Gets the funcion.
	 *
	 * @return the funcion
	 */
	public String getFuncion() {
		return funcion;
	}

	/**
	 * Sets the funcion.
	 *
	 * @param funcion
	 *            the new funcion
	 */
	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	/**
	 * Gets the plan estudiante.
	 *
	 * @return the plan estudiante
	 */
	public String getPlanEstudiante() {
		return planEstudiante;
	}

	/**
	 * Sets the plan estudiante.
	 *
	 * @param planEstudiante
	 *            the new plan estudiante
	 */
	public void setPlanEstudiante(String planEstudiante) {
		this.planEstudiante = planEstudiante;
	}

	public String getTipoVinculacion() {
		return tipoVinculacion;
	}

	public void setTipoVinculacion(String tipoVinculacion) {
		this.tipoVinculacion = tipoVinculacion;
	}
	
	public String getNombreVinculacion() {
		if (this.tipoVinculacion == null) {
			return "N/A";
		} else if(this.tipoVinculacion.equals("E")) {
			return "Externo";
		} else {
			return "Interno";
		}
	}
	
	public String getNombreTipo() {
			if (this.tipo == null) {
				return "N/A";
			} else if (this.tipo.equals(InvestigadorGrupo.DOCENTE)) {
				return "Docente";
			} else if (this.tipo.equals(InvestigadorGrupo.ESTUDIANTE)) {
				return "Estudiante";
			} else if (this.tipo.equals(InvestigadorGrupo.CODIRECTOR)) {
				return "Co-líder";
			} else if (this.tipo.equals(InvestigadorGrupo.EXTERNO)) {
				return "Investigador";
			} else if (this.tipo.equals(InvestigadorGrupo.ESTUDIANTE_LIDER)) {
				return "Estudiante Líder";
			} else if (this.tipo.equals(InvestigadorGrupo.LIDER)) {
				return "Líder";
			} else if (this.tipo.equals(InvestigadorGrupo.ESTUDIANTE_VISITANTE)) {
				return "Estudiante Visitante";
			} else if (this.tipo.equals(InvestigadorGrupo.ADMINISTRATIVO)) {
				return "Administrativo";
			} else if (this.tipo.equals(InvestigadorGrupo.ESTUDIANTE_PREGRADO)) {
				return "Estudiante Pregrado";
			} else if (this.tipo.equals(InvestigadorGrupo.ESTUDIANTE_POSGRADO)) {
				return "Estudiante Posgrado";
			} else if (this.tipo.equals(InvestigadorGrupo.EGRESADO)) {
				return "Egresado";
			} else if (this.tipo.equals(InvestigadorGrupo.DOCENTE_IPARM)) {
				return "Docente de Enseñanza educación básica y media";
			}
		
		
		return "";
		
	}

	public String getNombrePlanEstudiante() {
		return nombrePlanEstudiante;
	}

	public void setNombrePlanEstudiante(String nombrePlanEstudiante) {
		this.nombrePlanEstudiante = nombrePlanEstudiante;
	}

	public String getNombreAreaOcde() {
		return nombreAreaOcde;
	}

	public void setNombreAreaOcde(String nombreAreaOcde) {
		this.nombreAreaOcde = nombreAreaOcde;
	}

	public String getNombreSubAreaOcde() {
		return nombreSubAreaOcde;
	}

	public void setNombreSubAreaOcde(String nombreSubAreaOcde) {
		this.nombreSubAreaOcde = nombreSubAreaOcde;
	}

	public PlanEstudios getPlanEstudioEstudiante() {
		return planEstudioEstudiante;
	}

	public void setPlanEstudioEstudiante(PlanEstudios planEstudioEstudiante) {
		this.planEstudioEstudiante = planEstudioEstudiante;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}
}