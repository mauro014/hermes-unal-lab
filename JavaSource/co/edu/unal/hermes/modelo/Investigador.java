package co.edu.unal.hermes.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * Información acerca de información personal del usuario investigador.
 */
public class Investigador extends Persona {

	/** The Constant ADMINISTRATIVO. */
	public static final String ADMINISTRATIVO = "AD0000";

	/** The Constant ADMINISTRATIVO_PENSIONADO. */
	public static final String ADMINISTRATIVO_PENSIONADO = "ADP0000";

	/** The Constant CATEDRA_0_1. */
	public static final String CATEDRA_0_1 = "24";

	/** The Constant CATEDRA_0_2. */
	public static final String CATEDRA_0_2 = "25";

	/** The Constant CATEDRA_0_3. */
	public static final String CATEDRA_0_3 = "7";

	/** The Constant CATEDRA_0_4. */
	public static final String CATEDRA_0_4 = "6";

	/** The Constant CATEDRA_0_5. */
	public static final String CATEDRA_0_5 = "26";

	/** The Constant CATEDRA_0_6. */
	public static final String CATEDRA_0_6 = "27";

	/** The Constant CATEDRA_0_7. */
	public static final String CATEDRA_0_7 = "28";

	/** The Constant CONTRATISTA_EGRESADO. */
	public static final String CONTRATISTA_EGRESADO = "CT0000";

	/** The Constant CONTRATISTA_NO_EGRESADO. */
	public static final String CONTRATISTA_NO_EGRESADO = "CNE0000";

	/** The Constant EGRESADO. */
	public static final String EGRESADO = "EGES0000";

	/** The Constant ESTUDIANTE_EXTERNO. */
	public static final String ESTUDIANTE_EXTERNO = "ESEX0000";

	/** The Constant ESTUDIANTE_LIDER. */
	public static final String ESTUDIANTE_LIDER = "AL0000";

	/** The Constant ESTUDIANTE_POSG. */
	public static final String ESTUDIANTE_POSG = "ESPO0000";

	/** The Constant ESTUDIANTE_PREG. */
	// DOCUMENTOS INVESTIGADOR FM
	public static final String ESTUDIANTE_PREG = "ESPR0000";

	/** The Constant ESTUDIANTE_PREG. SEMILLERO */
	// DOCUMENTOS INVESTIGADOR FM
	public static final String ESTUDIANTE_PREG_SEM = "ESPR0001";

	/** The Constant JOVEN INV. EGRESADO. */
	// DOCUMENTOS INVESTIGADOR FM
	public static final String JOVEN_EGRE = "JIEU0000";

	/** The Constant JOVEN INV. ESTUDIANTE_POSG. */
	// DOCUMENTOS INVESTIGADOR FM
	public static final String JOVEN_POSG = "JIPO0000";

	/** The Constant EVALUADOR. */
	public static final String EVALUADOR = "S";

	/** The Constant EXCLUSIVA. */
	// HER_TIPO_DEDICACION:
	public static final String EXCLUSIVA = "3";

	/** The Constant EXTERNO. */
	public static final String EXTERNO = "N";

	/** The Constant INTERNO. */
	public static final String INTERNO = "S";
	
	public static final String FUNCIONARIO = "S";
	
	public static final String NO_FUNCIONARIO = "N";

	/** The Constant MEDIOTIEMPO. */
	public static final String MEDIOTIEMPO = "5";

	/** The Constant NO_EVALUADOR. */
	public static final String NO_EVALUADOR = "N";

	/** The Constant PROFESOR_CARRERA. */
	public static final String PROFESOR_CARRERA = "PCD0000";

	/** The Constant PROFESOR_EXTERNO. */
	public static final String PROFESOR_EXTERNO = "PEX0000";

	/** The Constant PROFESOR_PENSIONADO. */
	public static final String PROFESOR_PENSIONADO = "PEES0000";

	/** The Constant PROFESOR_SIN_CARRERA. */
	public static final String PROFESOR_SIN_CARRERA = "PSCD0000";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1548781414683872690L;

	/** The Constant TIEMPO_COMPLETO_ADMINISTRATIVO. */
	public static final String TIEMPO_COMPLETO_ADMINISTRATIVO = "1";

	/** The Constant TIEMPOCOMPLETO. */
	public static final String TIEMPOCOMPLETO = "4";

	/**
	 * Son las categorias del investigador.
	 */
	private CategoriaInvestigador categoriaInvestigador;

	/** The clasificaciones conocimiento. */
	private Set clasificacionesConocimiento = new HashSet();

	/** The dependencia. */
	private Dependencia dependencia;

	/** The elegible. */
	boolean elegible = true;

	/** The es funcionario. */
	private String esFuncionario;

	/** The evaluador. */
	private String evaluador; // determina si es evaluador o no

	/** The grupos. */
	public List grupos;

	/** The grupos investigador. */
	private Set<InvestigadorGrupo> gruposInvestigador = new HashSet<InvestigadorGrupo>();

	/** The interno. */
	private String interno;

	/** The lineas. */
	private Set lineas = new HashSet();

	/** The programa estudiante. */
	private String programaEstudiante;

	/** The proyectos evaluador. */
	// Proyectos que evalua
	private SortedSet proyectosEvaluador = new TreeSet();

	/** The proyectos investigador. */
	private Set proyectosInvestigador = new HashSet();

	/** The tel oficina. */
	private String telOficina;

	/** The tipo dedicacion. */
	private TipoDedicacion tipoDedicacion;

	/** The tipo formacion. */
	private TipoFormacion tipoFormacion;

	/** The tipo vinculacion. */
	private TipoVinculacion tipoVinculacion;

	/** The url colciencias. */
	private String urlColciencias;

	/** The insitucion externa. */
	private Institucion institucion;

	/** The dependencia. */
	private Dependencia dependencia2;

	/** The plan de estudios. */
	private PlanEstudios plan; // solo para la vista temporal

	/**
	 * Este atributo indica si el investigador es nuevo, y se debe enviar
	 * contrasenia. Si el correo ya se ha enviado, no será necesario enviar
	 * contrasenia.
	 */
	private boolean enviarContrasenia;

	private String areaOcde;

	private String subareaOcde;

	/**
	 * Adicionar clasificacion conocimiento.
	 *
	 * @param cc
	 *            the cc
	 */
	public void adicionarClasificacionConocimiento(ClasificacionConocimiento cc) {
		boolean ccExiste = false;
		Iterator it = clasificacionesConocimiento.iterator();
		while (it.hasNext() && !ccExiste) {
			ClasificacionConocimiento ccaux = (ClasificacionConocimiento) it.next();
			if (ccaux.getId().equals(cc.getId())) {
				ccExiste = true;
			}
		}
		if (!ccExiste) {
			clasificacionesConocimiento.add(cc);
		}
	}

	/**
	 * Borrar clasificacion conocimiento.
	 *
	 * @param cc
	 *            the cc
	 */
	public void borrarClasificacionConocimiento(ClasificacionConocimiento cc) {
		clasificacionesConocimiento.remove(cc);
	}

	/**
	 * funcion temporal para obtener el uid de un investigador con su correo
	 */
	public void crearUidTemporal() {
		String email = getEmail();
		if (email != null) {
			if (email.endsWith("@unal.edu.co")) {
				StringTokenizer emailToken = new StringTokenizer(email, "@");
				setUid(emailToken.nextToken());
			}
		}
	}

	/**
	 * Sobreescritura del metodo equals en donde un investigador es igual a
	 * otro, si su id es el mismo.
	 *
	 * @param arg0
	 *            the arg0
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object arg0) {
		return super.equals(arg0);
	}

	/**
	 * Gets the categoria investigador.
	 *
	 * @return the categoria investigador
	 */
	public CategoriaInvestigador getCategoriaInvestigador() {
		return categoriaInvestigador;
	}

	/**
	 * Gets the clasificaciones conocimiento.
	 *
	 * @return the clasificaciones conocimiento
	 */
	public Set getClasificacionesConocimiento() {
		return clasificacionesConocimiento;
	}

	/**
	 * Gets the dependencia.
	 *
	 * @return the dependencia
	 */
	public Dependencia getDependencia() {
		if (this.dependencia == null) {
			this.dependencia = new Dependencia();
			this.dependencia.setNombre("");
			Sede sede = new Sede();
			sede.setNombre("");
			this.dependencia.setSede(sede);
			Dependencia facultad = new Dependencia();
			facultad.setNombre("");
			this.dependencia.setFacultad(facultad);
		}
		return dependencia;
	}

	/**
	 * Gets the dependencia2.
	 *
	 * @return the dependencia2
	 */
	public Dependencia getDependencia2() {
		if (this.dependencia2 == null) {
			this.dependencia2 = new Dependencia();
			this.dependencia2.setNombre("");
			Sede sede = new Sede();
			sede.setNombre("");
			this.dependencia2.setSede(sede);
			Dependencia facultad = new Dependencia();
			facultad.setNombre("");
			this.dependencia2.setFacultad(facultad);

		}
		return dependencia2;
	}

	/**
	 * Gets the es funcionario.
	 *
	 * @return the es funcionario
	 */
	public String getEsFuncionario() {
		return esFuncionario;
	}

	/**
	 * Gets the evaluador.
	 *
	 * @return the evaluador
	 */
	public String getEvaluador() {
		return evaluador;
	}

	/**
	 * Gets the grupos.
	 *
	 * @return the grupos
	 */
	public List<InvestigadorGrupo> getGrupos() {
		return grupos;
	}

	/**
	 * Gets the grupos investigador.
	 *
	 * @return the grupos investigador
	 */
	public Set getGruposInvestigador() {
		return gruposInvestigador;
	}

	/**
	 * Gets the interno.
	 *
	 * @return the interno
	 */
	public String getInterno() {
		return interno;
	}

	/**
	 * Gets the lineas.
	 *
	 * @return the lineas
	 */
	public Set getLineas() {
		return lineas;
	}

	/**
	 * Gets the lista clasificacion conocimiento.
	 *
	 * @return the lista clasificacion conocimiento
	 */
	public List getListaClasificacionConocimiento() {
		List listaCC = new ArrayList();
		listaCC.addAll(clasificacionesConocimiento);
		return listaCC;
	}

	/**
	 * Gets the lista proyectos evaluador.
	 *
	 * @return the lista proyectos evaluador
	 */
	public List getListaProyectosEvaluador() {
		List listaCC = new ArrayList();
		listaCC.addAll(proyectosEvaluador);
		return listaCC;
	}

	/**
	 * Gets the programa estudiante.
	 *
	 * @return the programa estudiante
	 */
	public String getProgramaEstudiante() {
		return programaEstudiante;
	}

	/**
	 * Gets the proyectos evaluador.
	 *
	 * @return the proyectos evaluador
	 */
	public Set getProyectosEvaluador() {
		return proyectosEvaluador;
	}

	/**
	 * Gets the proyectos investigador.
	 *
	 * @return the proyectos investigador
	 */
	public Set getProyectosInvestigador() {
		return proyectosInvestigador;
	}

	/**
	 * Gets the tel oficina.
	 *
	 * @return the tel oficina
	 */
	public String getTelOficina() {
		return telOficina;
	}

	/**
	 * Gets the tipo dedicacion.
	 *
	 * @return the tipo dedicacion
	 */
	public TipoDedicacion getTipoDedicacion() {
		if (this.tipoDedicacion == null) {
			this.tipoDedicacion = new TipoDedicacion();
			this.tipoDedicacion.setNombre("");
		}
		return tipoDedicacion;
	}

	/**
	 * Gets the tipo formacion.
	 *
	 * @return the tipo formacion
	 */
	public TipoFormacion getTipoFormacion() {
		if (this.tipoFormacion == null) {
			this.tipoFormacion = new TipoFormacion();
			this.tipoFormacion.setNombre("");
		}
		return tipoFormacion;
	}

	/**
	 * Gets the tipo vinculacion.
	 *
	 * @return the tipo vinculacion
	 */
	public TipoVinculacion getTipoVinculacion() {
		if (this.tipoVinculacion == null) {
			this.tipoVinculacion = new TipoVinculacion();
			this.tipoVinculacion.setNombre("");
		}
		return tipoVinculacion;
	}

	/**
	 * Gets the url colciencias.
	 *
	 * @return the url colciencias
	 */
	public String getUrlColciencias() {
		return urlColciencias;
	}

	/**
	 * Checks if is elegible.
	 *
	 * @return true, if is elegible
	 */
	public boolean isElegible() {
		return elegible;
	}

	/**
	 * Sets the categoria investigador.
	 *
	 * @param categoriaInvestigador
	 *            the new categoria investigador
	 */
	public void setCategoriaInvestigador(CategoriaInvestigador categoriaInvestigador) {
		this.categoriaInvestigador = categoriaInvestigador;
	}

	/**
	 * Construye el url de colciencias especifico para el invetigador y se lo
	 * asigna al atributo urlColciencias.
	 */

	/**
	 * Sets the clasificaciones conocimiento.
	 *
	 * @param clasificacionesConocimiento
	 *            the new clasificaciones conocimiento
	 */
	public void setClasificacionesConocimiento(Set clasificacionesConocimiento) {
		this.clasificacionesConocimiento = clasificacionesConocimiento;
	}

	/**
	 * Sets the dependencia.
	 *
	 * @param dependencia
	 *            the new dependencia
	 */
	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	/**
	 * Sets the elegible.
	 *
	 * @param elegible
	 *            the new elegible
	 */
	public void setElegible(boolean elegible) {
		this.elegible = elegible;
	}

	/**
	 * Sets the es funcionario.
	 *
	 * @param esFuncionario
	 *            the new es funcionario
	 */
	public void setEsFuncionario(String esFuncionario) {
		this.esFuncionario = esFuncionario;
	}

	/**
	 * Sets the evaluador.
	 *
	 * @param evaluador
	 *            the new evaluador
	 */
	public void setEvaluador(String evaluador) {
		this.evaluador = evaluador;
	}

	/**
	 * Sets the grupos.
	 *
	 * @param grupos
	 *            the new grupos
	 */
	public void setGrupos(List<InvestigadorGrupo> grupos) {
		this.grupos = grupos;
	}

	/**
	 * Sets the grupos investigador.
	 *
	 * @param gruposInvestigador
	 *            the new grupos investigador
	 */
	public void setGruposInvestigador(Set gruposInvestigador) {
		this.gruposInvestigador = gruposInvestigador;
	}

	/**
	 * Sets the interno.
	 *
	 * @param interno
	 *            the new interno
	 */
	public void setInterno(String interno) {
		this.interno = interno;
	}

	/**
	 * Sets the lineas.
	 *
	 * @param lineas
	 *            the new lineas
	 */
	public void setLineas(Set lineas) {
		this.lineas = lineas;
	}

	/**
	 * Sets the programa estudiante.
	 *
	 * @param programaEstudiante
	 *            the new programa estudiante
	 */
	public void setProgramaEstudiante(String programaEstudiante) {
		this.programaEstudiante = programaEstudiante;
	}

	/**
	 * Sets the proyectos evaluador.
	 *
	 * @param proyectosEvaluador
	 *            the new proyectos evaluador
	 */
	public void setProyectosEvaluador(Set proyectosEvaluador) {
		this.proyectosEvaluador = (SortedSet) proyectosEvaluador;
	}

	/**
	 * Sets the proyectos investigador.
	 *
	 * @param proyectosInvestigador
	 *            the new proyectos investigador
	 */
	public void setProyectosInvestigador(Set proyectosInvestigador) {
		this.proyectosInvestigador = proyectosInvestigador;
	}

	/**
	 * Sets the tel oficina.
	 *
	 * @param telOficina
	 *            the new tel oficina
	 */
	public void setTelOficina(String telOficina) {
		this.telOficina = telOficina;
	}

	/**
	 * Sets the tipo dedicacion.
	 *
	 * @param tipoDedicacion
	 *            the new tipo dedicacion
	 */
	public void setTipoDedicacion(TipoDedicacion tipoDedicacion) {
		this.tipoDedicacion = tipoDedicacion;
	}

	/**
	 * Sets the tipo formacion.
	 *
	 * @param tipoFormacion
	 *            the new tipo formacion
	 */
	public void setTipoFormacion(TipoFormacion tipoFormacion) {
		this.tipoFormacion = tipoFormacion;
	}

	/**
	 * Sets the tipo vinculacion.
	 *
	 * @param tipoVinculacion
	 *            the new tipo vinculacion
	 */
	public void setTipoVinculacion(TipoVinculacion tipoVinculacion) {
		this.tipoVinculacion = tipoVinculacion;
	}

	/**
	 * Sets the url colciencias.
	 *
	 * @param urlColciencias
	 *            the new url colciencias
	 */
	public void setUrlColciencias(String urlColciencias) {
		this.urlColciencias = urlColciencias;
	}

	public Institucion getInstitucion() {
		if (this.institucion == null) {
			this.institucion = new Institucion();
			this.institucion.setNombre("");
		}
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public void setDependencia2(Dependencia dependencia2) {
		this.dependencia2 = dependencia2;
	}

	public PlanEstudios getPlan() {
		return plan;
	}

	public void setPlan(PlanEstudios plan) {
		this.plan = plan;
	}

	/**
	 * @return the enviarContrasenia
	 */
	public boolean isEnviarContrasenia() {
		return enviarContrasenia;
	}

	/**
	 * @param enviarContrasenia
	 *            the enviarContrasenia to set
	 */
	public void setEnviarContrasenia(boolean enviarContrasenia) {
		this.enviarContrasenia = enviarContrasenia;
	}

	public String getAreaOcde() {
		return areaOcde;
	}

	public void setAreaOcde(String areaOcde) {
		this.areaOcde = areaOcde;
	}

	public String getSubareaOcde() {
		return subareaOcde;
	}

	public void setSubareaOcde(String subareaOcde) {
		this.subareaOcde = subareaOcde;
	}

}
