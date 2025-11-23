package co.edu.unal.hermes.modelo;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * Especifíca las información principal de la convocatoria.
 */
public class Convocatoria extends Modalidad {

    /** The Constant TIPO_EVENTO_1. */
    public static final String TIPO_EVENTO_1 = "CE1";

    /** The Constant TIPO_EVENTO_2. */
    public static final String TIPO_EVENTO_2 = "CE2";

    /** The Constant CONVOCATORIA_COMUNICADO. */
    public static final String CONVOCATORIA_COMUNICADO = "CONVOCATORIA_INACTIVA_COMUNICADO";

    /** The Constant SIN_TIPO_FINANCIACION. */
    public static final String SIN_TIPO_FINANCIACION = "NINGUN";

    /** The Constant TIPO_FINANCIACION_MINIMA. */
    public static final String TIPO_FINANCIACION_MINIMA = "MINIMA";

    /** The Constant TIPO_FINANCIACION_BASICA. */
    public static final String TIPO_FINANCIACION_BASICA = "BASICA";

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant TIPO_MODALIDAD_MOVILIDAD. */
    public static final String TIPO_MODALIDAD_MOVILIDAD = "CM";
    
    /** The Constant MODALIDAD_CONVOCATORIA_EXTERNA. */
    public static final Long MODALIDAD_CONVOCATORIA_EXTERNA = 10L;

    /** The Constant MODALIDAD_FICHA_MINIMA_ID. */
    public static final Long MODALIDAD_FICHA_MINIMA_ID = 2L;
    
    /** The id convocatoria alianzas. */
    public static final Long ID_CONVOCATORIA_ALIANZAS = 343L;
    
    /** The id convocatoria doctorados nacionales Colciencias. */
    public static final Long ID_MODALIDAD_CONV_DOCTORADOS_2015 = 717L;
    
    public static final Long ID_CONVOCATORIA_AGROSAVIA_2020 = 971L;
    /**
     * Son las dependencias de la Universidad Nacional que están asociadas al
     * proyecto.
     */
    private Dependencia dependencia;

    /**
     * Son las sedes de la Universidad Nacional que están asociadas al proyecto.
     */
    private Sede sede;

    /**
     * Representa el estado actual de la convocatoria estos están: Activa,
     * Inactiva, Cancelado,.
     */
    private EstadoConvocatoria estadoConvocatoria;

    /** Convocatoria padre de la cual depende. */
    private ConvocatoriaPadre padre;

    /** Restriccion para la convocatoria. */
    private RestriccionConvocatoria restriccion;
    
    private Boolean esInterDependencias;
	  
    private String interDependenciasRestriccion;

    /**
     * Cuando se crea una convocatoria con restriccion para dependencia por
     * ejemplo una convocatoria solo para una sede o una facultad especifica.
     */
    private Dependencia dependenciaRestriccion;
    private long tamanoArchivoMb;
    
    private Long numeroMovilidadesPorEstudiante;

    /** The elegible asesor. */
    boolean elegibleAsesor = true;

    /** The titulo. */
    private String titulo;

    /** The fecha inicio. */
    private Date fechaInicio;

    /** The fecha final. */
    private Date fechaFinal;

    /** The fecha publicacion resultados. */
    private Date fechaPublicacionResultados;

    /** The dirigido a. */
    private String dirigidoA;

    /** The oferente. */
    private String oferente;

    /** The objetivo. */
    private String objetivo;

    /** The recursos financieros. */
    private String recursosFinancieros;

    /** The informacion adicional. */
    private String informacionAdicional;

    /** The area dirigida. */
    private String areaDirigida;

    /** The numero apoyos. */
    private String numeroApoyos;

    /** The monto apoyo ganadores. */
    private String montoApoyoGanadores;

    /** The tiempo ejecucion proyecto. */
    private Integer tiempoEjecucionProyecto;

    /** The estados. */
    private List estados;

    /** The mostrar archivos evaluador. */
    private String mostrarArchivosEvaluador;

    /** The informacion adicional mesa. */
    private String informacionAdicionalMesa;

    /** The fecha inicio reclamacion. */
    private Date fechaInicioReclamacion;

    /** The fecha final reclamacion. */
    private Date fechaFinalReclamacion;

    /** The fecha inicio reclamacion evaluación. */
    private Date fechaInicioReclamacionEvaluacion;

    /** The fecha final reclamacion evaluación. */
    private Date fechaFinalReclamacionEvaluacion;

    /** The requisitos conv texto. */
    private String requisitosConvTexto;

    /** The compromisos conv texto. */
    private String compromisosTexto;

    /** The criterios calificacion conv. */
    private String criteriosCalificacionConv;

    /** The es para grupos. */
    // Datos de la convocotaria para grupos
    private Boolean esParaGrupos;

    /** The grupos registrados. */
    private Boolean gruposRegistrados;

    /** The grupos reconocidos. */
    private Boolean gruposReconocidos;

    /** The grupos categoria a. */
    private Boolean gruposCategoriaA;

    /** The grupos categoria a1. */
    private Boolean gruposCategoriaA1;

    /** The grupos categoria b. */
    private Boolean gruposCategoriaB;

    /** The grupos categoria c. */
    private Boolean gruposCategoriaC;

    /** The tiene proyecto asociado. */
    private Boolean tieneProyectoAsociado;

    /**
     * Establece si muestra el campo descripción del proble en la ficha mínima.
     */
    private Long conMostrarDescripcionProblema;

    /** Establece si muestra el campo biodiversidad en la ficha mínima. */
    private Long conMostrarBiodiversidad;
    
    /** Establece si muestra el campo resumen en la ficha mínima. */
    private Long conMostrarResumen;
    
    /** Establece si muestra el campo resultados en la ficha mínima. */
    private Long conMostrarResultados;

    // wam²
    /** The grupos categoria d. */
    // categotia grupo D
    private Boolean gruposCategoriaD;

    /** The es intersedes. */
    private Boolean esIntersedes;

    /** The acto nombre. */
    private String actoNombre;

    /** The acto numero. */
    private String actoNumero;

    /** The acto fecha. */
    private Date actoFecha;

    /** The acto nombre funcionario. */
    private String actoNombreFuncionario;

    /** The fecha inicio eval. */
    private Date fechaInicioEval;

    /** The fecha final eval. */
    private Date fechaFinalEval;
    
    /** The mostrar metas. */
    private boolean mostrarMetas;
    
    /** The mostrar nombre lab. */
    private boolean mostrarNombreLab;

    /** The productos. */
    private Set productos = new HashSet();

    /** The compromisos. */
    private Set compromisos = new HashSet();

    /** The requisitos. */
    private Set requisitos = new HashSet();

    /** The programa academico. */
    private String programaAcademico; // Si se desea solicitar programa
                                      // academico

    /** The documento evaluacion. */
    private String documentoEvaluacion; // Si se permiten documento en la
                                        // evaluación por parte de la mesa de
                                        // trabajo

    /** The documento evaluacion docente. */
    private String documentoEvaluacionDocente; // Si se permiten documento en la
                                               // evaluación por parte del
                                               // evaluador

    /** The etapa creacion. */
    private Long etapaCreacion;

    /** The modalidad modulo. */
    private String modalidadModulo;

    /** The clase evento ecp. */
    private String claseEventoECP;

    /** The sub evento ecp. */
    private String subEventoECP;

    /** The otro evento. */
    private String otroEvento;

    /** The es proyecto obligagorio. */
    private String esProyectoObligagorio;

    /** The habilitar mod estudiantes. */
    private String habilitarModEstudiantes;

    /** The habilitar mod tutor. */
    private String habilitarModTutor;

    /** The varios informes avance. */
    private String variosInformesAvance;

    /** The varios informes finales. */
    private String variosInformesFinales;

    /** The permitir informe tutor. */
    private String permitirInformeTutor;

    /** The mensaje formulario informe. */
    private String mensajeFormularioInforme;

    /** The es asignatura obligatoria. */
    private String esAsignaturaObligatoria;

    /** The habilitar cartas firma vicedecanatura. */
    private String habilitarCartasFirmaVicedecanatura;

    /** The habilitar cartas firma vicedecanatura. */
    private String variasSolicituesAnio;

    /** The acto nombre. */
    private boolean mostrarIntegrantesSinDatos;

    /** The acto nombre. */
    private boolean mostrarActividadesInvestigador;

    /** The acto nombre. */
    private boolean mostrarActividadesInvestigadorNoConocido;

    /** The mostrar evaluaciones individuales. */
    private boolean mostrarEvaluacionesIndividuales;

    /** The mostrar estudiante. */
    private boolean mostrarEstudianteLider;

    /** The mostrar estudiantes participantes. */
    private boolean mostrarEntidadesParticipantes;

    /** The mostrar estudiantes participantes. */
    private boolean mostrarEspecieConvocantes;

    /** The permitir crear financiacion. */
    private boolean permitirCrearFinanciacion;

    /** The mostrar productos academicos. */
    private boolean mostrarProductosAcademicos;

    /** The mostrar productos academicos. */
    private boolean mostrarOtroProyecto;

    /** The mostrar productos academicos. */
    private boolean incluirContrapartidaFinanacion;
    
    private boolean mostrarDependenciasAportantes;

    private boolean mostrarEquiposServicios;
    private boolean adquisicionEquiposObligatorio;

    /** The monton minimo. */
    private Long montonMinimo;

    /** The tipo financiacion. */
    private String tipoFinanciacion;

    /** The monton minimo. */
    private Long vigenciaPredeterminada;
    
    private String mostrarDetalleGasto;
    private boolean mostrarDetGasto; // para control en la vista
    private String detalleRubroObligatorio;

    /**
     * Establece si la convocatoria permite el registro de actividades,
     * vinculadas a los objetivos específicos y metas.
     */
    private Long actividadesObjetivosMetas;

    /** The con validar equipo trabajo. */
    private Long conValidarEquipoTrabajo;

    /** The url requisitos sede. */
    private String urlRequisitosSede;

    /** The habilitar registro. */
    private Boolean habilitarRegistro;
    
    /** The instrucciones ingreso costos. */
    private String instruccionesIngresoCostos;
    
    private String tipoInvestigadoresConvocatoria;
    
    private Long numeroProductosObligatorios;

    private String mensajeArchivosAdjuntos;
    
    private Long numeroProyectosPorProfesor;
    
    private Boolean esConvocatoriaMultigrupos;
    
    private Long numeroMinimoGrupos;
    
    private String dependenciaGruposRestriccion;
    
    private String restriccionEquipoTrabajo;
    
    private String textoValidacionEquipoTrabajo;
    
    private String mensajeRubrosBasicos;
    
    private String productosAValidar;
    
    private String mensajeProductosAValidar;
    
    private String caracterEventoConvocatoriaMovilidad;
    
    private String submodalidadConvocatoriaMovilidad;
    
    private String validacionCostosConvocatoriaMovilidad;
    
    private String validacionDuracionMovilidad;

    private String mostrarColombiaMovilidad;
    
    private String mostrarLaboratorios;
    
    private Long numeroLaboratoriosValidacion;
    
    private String tipoEstudianteMovilidad;
    
    private String dependenciasEstudiantesMovilidad;
    
    private String dependenciaRevisionMovilidadesEst;
    
    private String dominioTipologiaProyectos;
    
    private String tipoEvaluacionProyectos;
    
    private String mostrarPasaporteMovilidad;
	private Long maxTrabajosMovilidad;
	private String mostrarGrupoMovilidad;
	
	private String mostrarFinanciacion;
	
	private String mostrarEventoMovilidad;
	
	private Integer requiereEvaluacion;
	
	private Integer mostrarLugarEspecifico;
	
	private Integer registrosPorGrupo;
	
	private Integer registrosPorLaboratorio;
	
	private Integer mostrarObjetivos;
	
	private Integer obligatorioSubirArchivos;
	
	private Integer mostrarColeccionAsociada;
	
	private Integer mostrarTituloRevista;
	
	private Boolean esParaSemilleros;
	private Boolean liderSemilleros;
	private Integer numeroMinimoSemilleros;
	private Boolean coordinadorLaboratorios;
	
	private boolean usarFormularioProyectoEditorial;
	private boolean editaCreadorProyecto;
    private boolean incluirFinanciacionExterna; // se usa para las convocatorias de eventos.
    private boolean gruposSinRestriccionIntegrante;
    private String notaLaboratorios;
    private Boolean mostrarTipoMovilidad;
    private String infoAdicionalFormulario;
    /**
     * Gets the requisitos.
     *
     * @return the requisitos
     */
    public Set getRequisitos() {
        return requisitos;
    }

    /**
     * Sets the requisitos.
     *
     * @param requisitos
     *            the new requisitos
     */
    public void setRequisitos(Set requisitos) {
        this.requisitos = requisitos;
    }

    /**
     * Borrar producto.
     *
     * @param producto
     *            the producto
     */
    public void borrarProducto(ProductoTipo producto) {
        this.productos.remove(producto);
    }

    /**
     * Adicionar producto.
     *
     * @param producto
     *            the producto
     */
    public void adicionarProducto(ProductoTipo producto) {
        this.productos.add(producto);
    }

    /**
     * Borrar compromiso.
     *
     * @param compromiso
     *            the compromiso
     */
    public void borrarCompromiso(TipoCompromiso compromiso) {
        this.compromisos.remove(compromiso);
    }

    /**
     * Adicionar compromiso.
     *
     * @param compromiso
     *            the compromiso
     */
    public void adicionarCompromiso(TipoCompromiso compromiso) {
        this.compromisos.add(compromiso);
    }

    /**
     * Borrar requisito.
     *
     * @param requisito
     *            the requisito
     */
    public void borrarRequisito(TipoRequisito requisito) {
        this.requisitos.remove(requisito);
    }

    /**
     * Adicionar requisito.
     *
     * @param requisito
     *            the requisito
     */
    public void adicionarRequisito(TipoRequisito requisito) {
        this.requisitos.add(requisito);
    }

    /**
     * Gets the estado convocatoria.
     *
     * @return the estado convocatoria
     */
    public EstadoConvocatoria getEstadoConvocatoria() {
        return estadoConvocatoria;
    }

    /**
     * Sets the estado convocatoria.
     *
     * @param estadoConvocatoria
     *            the new estado convocatoria
     */
    public void setEstadoConvocatoria(EstadoConvocatoria estadoConvocatoria) {
        this.estadoConvocatoria = estadoConvocatoria;
    }

    /**
     * Gets the fecha final.
     *
     * @return the fecha final
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * Sets the fecha final.
     *
     * @param fechaFinal
     *            the new fecha final
     */
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * Gets the fecha inicio.
     *
     * @return the fecha inicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Sets the fecha inicio.
     *
     * @param fechaInicio
     *            the new fecha inicio
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Gets the dependencia.
     *
     * @return the dependencia
     */
    public Dependencia getDependencia() {
        return dependencia;
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
     * Gets the area dirigida.
     *
     * @return the area dirigida
     */
    public String getAreaDirigida() {
        return areaDirigida;
    }

    /**
     * Sets the area dirigida.
     *
     * @param areaDirigida
     *            the new area dirigida
     */
    public void setAreaDirigida(String areaDirigida) {
        this.areaDirigida = areaDirigida;
    }

    /**
     * Gets the dirigido a.
     *
     * @return the dirigido a
     */
    public String getDirigidoA() {
        return dirigidoA;
    }

    /**
     * Sets the dirigido a.
     *
     * @param dirigidoA
     *            the new dirigido a
     */
    public void setDirigidoA(String dirigidoA) {
        this.dirigidoA = dirigidoA;
    }

    /**
     * Gets the fecha publicacion resultados.
     *
     * @return the fecha publicacion resultados
     */
    public Date getFechaPublicacionResultados() {
        return fechaPublicacionResultados;
    }

    /**
     * Sets the fecha publicacion resultados.
     *
     * @param fechaPublicacionResultados
     *            the new fecha publicacion resultados
     */
    public void setFechaPublicacionResultados(Date fechaPublicacionResultados) {
        this.fechaPublicacionResultados = fechaPublicacionResultados;
    }

    /**
     * Gets the informacion adicional.
     *
     * @return the informacion adicional
     */
    public String getInformacionAdicional() {
        return informacionAdicional;
    }

    /**
     * Sets the informacion adicional.
     *
     * @param informacionAdicional
     *            the new informacion adicional
     */
    public void setInformacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    /**
     * Gets the objetivo.
     *
     * @return the objetivo
     */
    public String getObjetivo() {
        return objetivo;
    }

    /**
     * Sets the objetivo.
     *
     * @param objetivo
     *            the new objetivo
     */
    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    /**
     * Gets the oferente.
     *
     * @return the oferente
     */
    public String getOferente() {
        return oferente;
    }

    /**
     * Sets the oferente.
     *
     * @param oferente
     *            the new oferente
     */
    public void setOferente(String oferente) {
        this.oferente = oferente;
    }

    /**
     * Gets the recursos financieros.
     *
     * @return the recursos financieros
     */
    public String getRecursosFinancieros() {
        return recursosFinancieros;
    }

    /**
     * Sets the recursos financieros.
     *
     * @param recursosFinancieros
     *            the new recursos financieros
     */
    public void setRecursosFinancieros(String recursosFinancieros) {
        this.recursosFinancieros = recursosFinancieros;
    }

    /**
     * Gets the titulo.
     *
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Sets the titulo.
     *
     * @param titulo
     *            the new titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Gets the monto apoyo ganadores.
     *
     * @return the monto apoyo ganadores
     */
    public String getMontoApoyoGanadores() {
        return montoApoyoGanadores;
    }

    /**
     * Sets the monto apoyo ganadores.
     *
     * @param montoApoyoGanadores
     *            the new monto apoyo ganadores
     */
    public void setMontoApoyoGanadores(String montoApoyoGanadores) {
        this.montoApoyoGanadores = montoApoyoGanadores;
    }

    /**
     * Gets the numero apoyos.
     *
     * @return the numero apoyos
     */
    public String getNumeroApoyos() {
        return numeroApoyos;
    }

    /**
     * Sets the numero apoyos.
     *
     * @param numeroApoyos
     *            the new numero apoyos
     */
    public void setNumeroApoyos(String numeroApoyos) {
        this.numeroApoyos = numeroApoyos;
    }

    /**
     * Gets the tiempo ejecucion proyecto.
     *
     * @return the tiempo ejecucion proyecto
     */
    public Integer getTiempoEjecucionProyecto() {
        return tiempoEjecucionProyecto;
    }

    /**
     * Sets the tiempo ejecucion proyecto.
     *
     * @param tiempoEjecucionProyecto
     *            the new tiempo ejecucion proyecto
     */
    public void setTiempoEjecucionProyecto(Integer tiempoEjecucionProyecto) {
        this.tiempoEjecucionProyecto = tiempoEjecucionProyecto;
    }

    /**
     * Gets the acto fecha.
     *
     * @return Returns the actoFecha.
     */
    public Date getActoFecha() {
        return actoFecha;
    }

    /**
     * Sets the acto fecha.
     *
     * @param actoFecha
     *            The actoFecha to set.
     */
    public void setActoFecha(Date actoFecha) {
        this.actoFecha = actoFecha;
    }

    /**
     * Gets the acto nombre.
     *
     * @return Returns the actoNombre.
     */
    public String getActoNombre() {
        return actoNombre;
    }

    /**
     * Sets the acto nombre.
     *
     * @param actoNombre
     *            The actoNombre to set.
     */
    public void setActoNombre(String actoNombre) {
        this.actoNombre = actoNombre;
    }

    /**
     * Gets the acto nombre funcionario.
     *
     * @return Returns the actoNombreFuncionario.
     */
    public String getActoNombreFuncionario() {
        return actoNombreFuncionario;
    }

    /**
     * Sets the acto nombre funcionario.
     *
     * @param actoNombreFuncionario
     *            The actoNombreFuncionario to set.
     */
    public void setActoNombreFuncionario(String actoNombreFuncionario) {
        this.actoNombreFuncionario = actoNombreFuncionario;
    }

    /**
     * Gets the acto numero.
     *
     * @return Returns the actoNumero.
     */
    public String getActoNumero() {
        return actoNumero;
    }

    /**
     * Sets the acto numero.
     *
     * @param actoNumero
     *            The actoNumero to set.
     */
    public void setActoNumero(String actoNumero) {
        this.actoNumero = actoNumero;
    }

    /**
     * Gets the productos.
     *
     * @return the productos
     */
    public Set getProductos() {
        return productos;
    }

    /**
     * Sets the productos.
     *
     * @param productos
     *            the new productos
     */
    public void setProductos(Set productos) {
        this.productos = productos;
    }

    /**
     * Gets the es para grupos.
     *
     * @return the es para grupos
     */
    public Boolean getEsParaGrupos() {
        return esParaGrupos;
    }

    /**
     * Sets the es para grupos.
     *
     * @param esParaGrupos
     *            the new es para grupos
     */
    public void setEsParaGrupos(Boolean esParaGrupos) {
        this.esParaGrupos = esParaGrupos;
    }

    /**
     * Gets the grupos categoria a1.
     *
     * @return the grupos categoria a1
     */
    public Boolean getGruposCategoriaA1() {
        return gruposCategoriaA1;
    }

    /**
     * Sets the grupos categoria a1.
     *
     * @param gruposCategoriaA1
     *            the new grupos categoria a1
     */
    public void setGruposCategoriaA1(Boolean gruposCategoriaA1) {
        this.gruposCategoriaA1 = gruposCategoriaA1;
    }

    /**
     * Gets the grupos categoria a.
     *
     * @return the grupos categoria a
     */
    public Boolean getGruposCategoriaA() {
        return gruposCategoriaA;
    }

    /**
     * Sets the grupos categoria a.
     *
     * @param gruposCategoriaA
     *            the new grupos categoria a
     */
    public void setGruposCategoriaA(Boolean gruposCategoriaA) {
        this.gruposCategoriaA = gruposCategoriaA;
    }

    /**
     * Gets the grupos categoria b.
     *
     * @return the grupos categoria b
     */
    public Boolean getGruposCategoriaB() {
        return gruposCategoriaB;
    }

    /**
     * Sets the grupos categoria b.
     *
     * @param gruposCategoriaB
     *            the new grupos categoria b
     */
    public void setGruposCategoriaB(Boolean gruposCategoriaB) {
        this.gruposCategoriaB = gruposCategoriaB;
    }

    /**
     * Gets the grupos categoria c.
     *
     * @return the grupos categoria c
     */
    public Boolean getGruposCategoriaC() {
        return gruposCategoriaC;
    }

    /**
     * Sets the grupos categoria c.
     *
     * @param gruposCategoriaC
     *            the new grupos categoria c
     */
    public void setGruposCategoriaC(Boolean gruposCategoriaC) {
        this.gruposCategoriaC = gruposCategoriaC;
    }

    /**
     * Gets the grupos categoria d.
     *
     * @return the grupos categoria d
     */
    public Boolean getGruposCategoriaD() {
        return gruposCategoriaD;
    }

    /**
     * Sets the grupos categoria d.
     *
     * @param gruposCategoriaD
     *            the new grupos categoria d
     */
    public void setGruposCategoriaD(Boolean gruposCategoriaD) {
        this.gruposCategoriaD = gruposCategoriaD;
    }

    /**
     * Gets the grupos reconocidos.
     *
     * @return the grupos reconocidos
     */
    public Boolean getGruposReconocidos() {
        return gruposReconocidos;
    }

    /**
     * Sets the grupos reconocidos.
     *
     * @param gruposReconocidos
     *            the new grupos reconocidos
     */
    public void setGruposReconocidos(Boolean gruposReconocidos) {
        this.gruposReconocidos = gruposReconocidos;
    }

    /**
     * Gets the grupos registrados.
     *
     * @return the grupos registrados
     */
    public Boolean getGruposRegistrados() {
        return gruposRegistrados;
    }

    /**
     * Sets the grupos registrados.
     *
     * @param gruposRegistrados
     *            the new grupos registrados
     */
    public void setGruposRegistrados(Boolean gruposRegistrados) {
        this.gruposRegistrados = gruposRegistrados;
    }

    /**
     * Gets the padre.
     *
     * @return the padre
     */
    public ConvocatoriaPadre getPadre() {
        return padre;
    }

    /**
     * Sets the padre.
     *
     * @param padre
     *            the new padre
     */
    public void setPadre(ConvocatoriaPadre padre) {
        this.padre = padre;
    }

    /**
     * Gets the restriccion.
     *
     * @return the restriccion
     */
    public RestriccionConvocatoria getRestriccion() {
        return restriccion;
    }

	/**
     * Sets the restriccion.
     *
     * @param restriccion
     *            the new restriccion
     */
    public void setRestriccion(RestriccionConvocatoria restriccion) {
        this.restriccion = restriccion;
    }
    
    /**
     * Checks if is mostrar metas.
     *
     * @return true, if is mostrar metas
     */
    public boolean isMostrarMetas() {
		return mostrarMetas;
	}

	/**
	 * Sets the mostrar metas.
	 *
	 * @param mostrarMetas the new mostrar metas
	 */
	public void setMostrarMetas(boolean mostrarMetas) {
		this.mostrarMetas = mostrarMetas;
	}

    /**
     * Gets the dependencia restriccion.
     *
     * @return the dependencia restriccion
     */
    public Dependencia getDependenciaRestriccion() {
        return dependenciaRestriccion;
    }

    /**
     * Sets the dependencia restriccion.
     *
     * @param dependenciaRestriccion
     *            the new dependencia restriccion
     */
    public void setDependenciaRestriccion(Dependencia dependenciaRestriccion) {
        this.dependenciaRestriccion = dependenciaRestriccion;
    }

    /**
     * Gets the compromisos.
     *
     * @return the compromisos
     */
    public Set getCompromisos() {
        return compromisos;
    }

    /**
     * Sets the compromisos.
     *
     * @param compromisos
     *            the new compromisos
     */
    public void setCompromisos(Set compromisos) {
        this.compromisos = compromisos;
    }

    /**
     * Gets the sede.
     *
     * @return the sede
     */
    public Sede getSede() {
        return sede;
    }

    /**
     * Sets the sede.
     *
     * @param sede
     *            the new sede
     */
    public void setSede(Sede sede) {
        this.sede = sede;
    }

    /**
     * Gets the fecha inicio eval.
     *
     * @return the fecha inicio eval
     */
    public Date getFechaInicioEval() {
        return fechaInicioEval;
    }

    /**
     * Sets the fecha inicio eval.
     *
     * @param fechaInicioEval
     *            the new fecha inicio eval
     */
    public void setFechaInicioEval(Date fechaInicioEval) {
        this.fechaInicioEval = fechaInicioEval;
    }

    /**
     * Gets the fecha final eval.
     *
     * @return the fecha final eval
     */
    public Date getFechaFinalEval() {
        return fechaFinalEval;
    }

    /**
     * Sets the fecha final eval.
     *
     * @param fechaFinalEval
     *            the new fecha final eval
     */
    public void setFechaFinalEval(Date fechaFinalEval) {
        this.fechaFinalEval = fechaFinalEval;
    }

    /**
     * Checks if is elegible asesor.
     *
     * @return true, if is elegible asesor
     */
    public boolean isElegibleAsesor() {
        return elegibleAsesor;
    }

    /**
     * Sets the elegible asesor.
     *
     * @param elegibleAsesor
     *            the new elegible asesor
     */
    public void setElegibleAsesor(boolean elegibleAsesor) {
        this.elegibleAsesor = elegibleAsesor;
    }

    /**
     * Gets the etapa creacion.
     *
     * @return the etapa creacion
     */
    public Long getEtapaCreacion() {
        return etapaCreacion;
    }

    /**
     * Sets the etapa creacion.
     *
     * @param etapaCreacion
     *            the new etapa creacion
     */
    public void setEtapaCreacion(Long etapaCreacion) {
        this.etapaCreacion = etapaCreacion;
    }

    /**
     * Gets the es intersedes.
     *
     * @return the es intersedes
     */
    public Boolean getEsIntersedes() {
        return esIntersedes;
    }

    /**
     * Sets the es intersedes.
     *
     * @param esIntersedes
     *            the new es intersedes
     */
    public void setEsIntersedes(Boolean esIntersedes) {
        this.esIntersedes = esIntersedes;
    }

    /**
     * Gets the estados.
     *
     * @return the estados
     */
    public List getEstados() {
        return estados;
    }

    /**
     * Sets the estados.
     *
     * @param estados
     *            the new estados
     */
    public void setEstados(List estados) {
        this.estados = estados;
    }

    /**
     * Checks if is bandera.
     *
     * @return true, if is bandera
     */
    public boolean isBandera() {
        if (estados != null && estados.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Gets the modalidad modulo.
     *
     * @return the modalidad modulo
     */
    public String getModalidadModulo() {
        return modalidadModulo;
    }

    /**
     * Sets the modalidad modulo.
     *
     * @param modalidadModulo
     *            the new modalidad modulo
     */
    public void setModalidadModulo(String modalidadModulo) {
        this.modalidadModulo = modalidadModulo;
    }

    /**
     * Gets the clase evento ecp.
     *
     * @return the clase evento ecp
     */
    public String getClaseEventoECP() {
        return claseEventoECP;
    }

    /**
     * Sets the clase evento ecp.
     *
     * @param claseEventoECP
     *            the new clase evento ecp
     */
    public void setClaseEventoECP(String claseEventoECP) {
        this.claseEventoECP = claseEventoECP;
    }

    /**
     * Gets the sub evento ecp.
     *
     * @return the sub evento ecp
     */
    public String getSubEventoECP() {
        return subEventoECP;
    }

    /**
     * Sets the sub evento ecp.
     *
     * @param subEventoECP
     *            the new sub evento ecp
     */
    public void setSubEventoECP(String subEventoECP) {
        this.subEventoECP = subEventoECP;
    }

    /**
     * Gets the otro evento.
     *
     * @return the otro evento
     */
    public String getOtroEvento() {
        return otroEvento;
    }

    /**
     * Sets the otro evento.
     *
     * @param otroEvento
     *            the new otro evento
     */
    public void setOtroEvento(String otroEvento) {
        this.otroEvento = otroEvento;
    }

    /**
     * Gets the tiene proyecto asociado.
     *
     * @return the tiene proyecto asociado
     */
    public Boolean getTieneProyectoAsociado() {
    	if(tieneProyectoAsociado == null) {
    		return false;
    	}
        return tieneProyectoAsociado;
    }

    /**
     * Sets the tiene proyecto asociado.
     *
     * @param tieneProyectoAsociado
     *            the new tiene proyecto asociado
     */
    public void setTieneProyectoAsociado(Boolean tieneProyectoAsociado) {
        this.tieneProyectoAsociado = tieneProyectoAsociado;
    }

    /**
     * Sets the programa academico.
     *
     * @param programaAcademico
     *            the new programa academico
     */
    public void setProgramaAcademico(String programaAcademico) {
        this.programaAcademico = programaAcademico;
    }

    /**
     * Gets the programa academico.
     *
     * @return the programa academico
     */
    public String getProgramaAcademico() {
        return programaAcademico;
    }

    /**
     * Sets the documento evaluacion.
     *
     * @param documentoEvaluacion
     *            the new documento evaluacion
     */
    public void setDocumentoEvaluacion(String documentoEvaluacion) {
        this.documentoEvaluacion = documentoEvaluacion;
    }

    /**
     * Gets the documento evaluacion.
     *
     * @return the documento evaluacion
     */
    public String getDocumentoEvaluacion() {
        return documentoEvaluacion;
    }

    /**
     * Gets the mostrar archivos evaluador.
     *
     * @return the mostrar archivos evaluador
     */
    public String getMostrarArchivosEvaluador() {
        return mostrarArchivosEvaluador;
    }

    /**
     * Sets the mostrar archivos evaluador.
     *
     * @param mostrarArchivosEvaluador
     *            the new mostrar archivos evaluador
     */
    public void setMostrarArchivosEvaluador(String mostrarArchivosEvaluador) {
        this.mostrarArchivosEvaluador = mostrarArchivosEvaluador;
    }

    /**
     * Gets the es proyecto obligagorio.
     *
     * @return the es proyecto obligagorio
     */
    public String getEsProyectoObligagorio() {
        return esProyectoObligagorio;
    }

    /**
     * Sets the es proyecto obligagorio.
     *
     * @param esProyectoObligagorio
     *            the new es proyecto obligagorio
     */
    public void setEsProyectoObligagorio(String esProyectoObligagorio) {
        this.esProyectoObligagorio = esProyectoObligagorio;
    }

    /**
     * Gets the informacion adicional mesa.
     *
     * @return the informacion adicional mesa
     */
    public String getInformacionAdicionalMesa() {
        return informacionAdicionalMesa;
    }

    /**
     * Sets the informacion adicional mesa.
     *
     * @param informacionAdicionalMesa
     *            the new informacion adicional mesa
     */
    public void setInformacionAdicionalMesa(String informacionAdicionalMesa) {
        this.informacionAdicionalMesa = informacionAdicionalMesa;
    }

    /**
     * Gets the habilitar mod estudiantes.
     *
     * @return the habilitar mod estudiantes
     */
    public String getHabilitarModEstudiantes() {
        return habilitarModEstudiantes;
    }

    /**
     * Sets the habilitar mod estudiantes.
     *
     * @param habilitarModEstudiantes
     *            the new habilitar mod estudiantes
     */
    public void setHabilitarModEstudiantes(String habilitarModEstudiantes) {
        this.habilitarModEstudiantes = habilitarModEstudiantes;
    }

    /**
     * Gets the varios informes avance.
     *
     * @return the varios informes avance
     */
    public String getVariosInformesAvance() {
        return variosInformesAvance;
    }

    /**
     * Sets the varios informes avance.
     *
     * @param variosInformesAvance
     *            the new varios informes avance
     */
    public void setVariosInformesAvance(String variosInformesAvance) {
        this.variosInformesAvance = variosInformesAvance;
    }

    /**
     * Gets the habilitar mod tutor.
     *
     * @return the habilitar mod tutor
     */
    public String getHabilitarModTutor() {
        return habilitarModTutor;
    }

    /**
     * Sets the habilitar mod tutor.
     *
     * @param habilitarModTutor
     *            the new habilitar mod tutor
     */
    public void setHabilitarModTutor(String habilitarModTutor) {
        this.habilitarModTutor = habilitarModTutor;
    }

    /**
     * Gets the permitir informe tutor.
     *
     * @return the permitir informe tutor
     */
    public String getPermitirInformeTutor() {
        return permitirInformeTutor;
    }

    /**
     * Sets the permitir informe tutor.
     *
     * @param permitirInformeTutor
     *            the new permitir informe tutor
     */
    public void setPermitirInformeTutor(String permitirInformeTutor) {
        this.permitirInformeTutor = permitirInformeTutor;
    }

    /**
     * Gets the documento evaluacion docente.
     *
     * @return the documento evaluacion docente
     */
    public String getDocumentoEvaluacionDocente() {
        return documentoEvaluacionDocente;
    }

    /**
     * Sets the documento evaluacion docente.
     *
     * @param documentoEvaluacionDocente
     *            the new documento evaluacion docente
     */
    public void setDocumentoEvaluacionDocente(String documentoEvaluacionDocente) {
        this.documentoEvaluacionDocente = documentoEvaluacionDocente;
    }

    /**
     * Gets the mensaje formulario informe.
     *
     * @return the mensaje formulario informe
     */
    public String getMensajeFormularioInforme() {
        return mensajeFormularioInforme;
    }

    /**
     * Sets the mensaje formulario informe.
     *
     * @param mensajeFormularioInforme
     *            the new mensaje formulario informe
     */
    public void setMensajeFormularioInforme(String mensajeFormularioInforme) {
        this.mensajeFormularioInforme = mensajeFormularioInforme;
    }

    /**
     * Gets the varios informes finales.
     *
     * @return the varios informes finales
     */
    public String getVariosInformesFinales() {
        return variosInformesFinales;
    }

    /**
     * Sets the varios informes finales.
     *
     * @param variosInformesFinales
     *            the new varios informes finales
     */
    public void setVariosInformesFinales(String variosInformesFinales) {
        this.variosInformesFinales = variosInformesFinales;
    }

    /**
     * Gets the fecha inicio reclamacion.
     *
     * @return the fecha inicio reclamacion
     */
    public Date getFechaInicioReclamacion() {
        return fechaInicioReclamacion;
    }

    /**
     * Sets the fecha inicio reclamacion.
     *
     * @param fechaInicioReclamacion
     *            the new fecha inicio reclamacion
     */
    public void setFechaInicioReclamacion(Date fechaInicioReclamacion) {
        this.fechaInicioReclamacion = fechaInicioReclamacion;
    }

    /**
     * Gets the fecha final reclamacion.
     *
     * @return the fecha final reclamacion
     */
    public Date getFechaFinalReclamacion() {
        return fechaFinalReclamacion;
    }

    /**
     * Sets the fecha final reclamacion.
     *
     * @param fechaFinalReclamacion
     *            the new fecha final reclamacion
     */
    public void setFechaFinalReclamacion(Date fechaFinalReclamacion) {
        this.fechaFinalReclamacion = fechaFinalReclamacion;
    }

    /**
     * Gets the requisitos conv texto.
     *
     * @return the requisitos conv texto
     */
    public String getRequisitosConvTexto() {
        return requisitosConvTexto;
    }

    /**
     * Sets the requisitos conv texto.
     *
     * @param requisitosConvTexto
     *            the new requisitos conv texto
     */
    public void setRequisitosConvTexto(String requisitosConvTexto) {
        this.requisitosConvTexto = requisitosConvTexto;
    }

    /**
     * Gets the criterios calificacion conv.
     *
     * @return the criterios calificacion conv
     */
    public String getCriteriosCalificacionConv() {
        return criteriosCalificacionConv;
    }

    /**
     * Sets the criterios calificacion conv.
     *
     * @param criteriosCalificacionConv
     *            the new criterios calificacion conv
     */
    public void setCriteriosCalificacionConv(String criteriosCalificacionConv) {
        this.criteriosCalificacionConv = criteriosCalificacionConv;
    }

    /**
     * Gets the es asignatura obligatoria.
     *
     * @return the es asignatura obligatoria
     */
    public String getEsAsignaturaObligatoria() {
        return esAsignaturaObligatoria;
    }

    /**
     * Sets the es asignatura obligatoria.
     *
     * @param esAsignaturaObligatoria
     *            the new es asignatura obligatoria
     */
    public void setEsAsignaturaObligatoria(String esAsignaturaObligatoria) {
        this.esAsignaturaObligatoria = esAsignaturaObligatoria;
    }

    /**
     * Gets the habilitar cartas firma vicedecanatura.
     *
     * @return the habilitarCartasFirmaVicedecanatura
     */
    public String getHabilitarCartasFirmaVicedecanatura() {
        return habilitarCartasFirmaVicedecanatura;
    }

    /**
     * Sets the habilitar cartas firma vicedecanatura.
     *
     * @param habilitarCartasFirmaVicedecanatura
     *            the habilitarCartasFirmaVicedecanatura to set
     */
    public void setHabilitarCartasFirmaVicedecanatura(String habilitarCartasFirmaVicedecanatura) {
        this.habilitarCartasFirmaVicedecanatura = habilitarCartasFirmaVicedecanatura;
    }

    /**
     * Gets the varias solicitues anio.
     *
     * @return the variasSolicituesAnio
     */
    public String getVariasSolicituesAnio() {
        return variasSolicituesAnio;
    }

    /**
     * Sets the varias solicitues anio.
     *
     * @param variasSolicituesAnio
     *            the variasSolicituesAnio to set
     */
    public void setVariasSolicituesAnio(String variasSolicituesAnio) {
        this.variasSolicituesAnio = variasSolicituesAnio;
    }

    /**
     * Gets the fecha inicio reclamacion evaluacion.
     *
     * @return the fecha inicio reclamacion evaluacion
     */
    public Date getFechaInicioReclamacionEvaluacion() {
        return fechaInicioReclamacionEvaluacion;
    }

    /**
     * Sets the fecha inicio reclamacion evaluacion.
     *
     * @param fechaInicioReclamacionEvaluacion
     *            the new fecha inicio reclamacion evaluacion
     */
    public void setFechaInicioReclamacionEvaluacion(Date fechaInicioReclamacionEvaluacion) {
        this.fechaInicioReclamacionEvaluacion = fechaInicioReclamacionEvaluacion;
    }

    /**
     * Gets the fecha final reclamacion evaluacion.
     *
     * @return the fecha final reclamacion evaluacion
     */
    public Date getFechaFinalReclamacionEvaluacion() {
        return fechaFinalReclamacionEvaluacion;
    }

    /**
     * Sets the fecha final reclamacion evaluacion.
     *
     * @param fechaFinalReclamacionEvaluacion
     *            the new fecha final reclamacion evaluacion
     */
    public void setFechaFinalReclamacionEvaluacion(Date fechaFinalReclamacionEvaluacion) {
        this.fechaFinalReclamacionEvaluacion = fechaFinalReclamacionEvaluacion;
    }

    /**
     * Gets the compromisos texto.
     *
     * @return the compromisos texto
     */
    public String getCompromisosTexto() {
        return compromisosTexto;
    }

    /**
     * Sets the compromisos texto.
     *
     * @param compromisosTexto
     *            the new compromisos texto
     */
    public void setCompromisosTexto(String compromisosTexto) {
        this.compromisosTexto = compromisosTexto;
    }

    /**
     * Checks if is mostrar integrantes sin datos.
     *
     * @return the mostrarIntegrantesSinDatos
     */
    public boolean isMostrarIntegrantesSinDatos() {
        return mostrarIntegrantesSinDatos;
    }

    /**
     * Sets the mostrar integrantes sin datos.
     *
     * @param mostrarIntegrantesSinDatos
     *            the mostrarIntegrantesSinDatos to set
     */
    public void setMostrarIntegrantesSinDatos(boolean mostrarIntegrantesSinDatos) {
        this.mostrarIntegrantesSinDatos = mostrarIntegrantesSinDatos;
    }

    /**
     * Checks if is mostrar actividades investigador.
     *
     * @return the mostrarActividadesInvestigador
     */
    public boolean isMostrarActividadesInvestigador() {
        return mostrarActividadesInvestigador;
    }

    /**
     * Sets the mostrar actividades investigador.
     *
     * @param mostrarActividadesInvestigador
     *            the mostrarActividadesInvestigador to set
     */
    public void setMostrarActividadesInvestigador(boolean mostrarActividadesInvestigador) {
        this.mostrarActividadesInvestigador = mostrarActividadesInvestigador;
    }

    /**
     * Checks if is mostrar evaluaciones individuales.
     *
     * @return the mostrarEvaluacionesIndividuales
     */
    public boolean isMostrarEvaluacionesIndividuales() {
        return mostrarEvaluacionesIndividuales;
    }

    /**
     * Sets the mostrar evaluaciones individuales.
     *
     * @param mostrarEvaluacionesIndividuales
     *            the mostrarEvaluacionesIndividuales to set
     */
    public void setMostrarEvaluacionesIndividuales(boolean mostrarEvaluacionesIndividuales) {
        this.mostrarEvaluacionesIndividuales = mostrarEvaluacionesIndividuales;
    }

    /**
     * Checks if is mostrar estudiante lider.
     *
     * @return true, if is mostrar estudiante lider
     */
    public boolean isMostrarEstudianteLider() {
        return mostrarEstudianteLider;
    }

    /**
     * Sets the mostrar estudiante lider.
     *
     * @param mostrarEstudianteLider
     *            the new mostrar estudiante lider
     */
    public void setMostrarEstudianteLider(boolean mostrarEstudianteLider) {
        this.mostrarEstudianteLider = mostrarEstudianteLider;
    }

    /**
     * Checks if is mostrar entidades participantes.
     *
     * @return true, if is mostrar entidades participantes
     */
    public boolean isMostrarEntidadesParticipantes() {
        return mostrarEntidadesParticipantes;
    }

    /**
     * Sets the mostrar entidades participantes.
     *
     * @param mostrarEntidadesParticipantes
     *            the new mostrar entidades participantes
     */
    public void setMostrarEntidadesParticipantes(boolean mostrarEntidadesParticipantes) {
        this.mostrarEntidadesParticipantes = mostrarEntidadesParticipantes;
    }

    /**
     * Checks if is mostrar especie convocantes.
     *
     * @return true, if is mostrar especie convocantes
     */
    public boolean isMostrarEspecieConvocantes() {
        return mostrarEspecieConvocantes;
    }

    /**
     * Sets the mostrar especie convocantes.
     *
     * @param mostrarEspecieConvocantes
     *            the new mostrar especie convocantes
     */
    public void setMostrarEspecieConvocantes(boolean mostrarEspecieConvocantes) {
        this.mostrarEspecieConvocantes = mostrarEspecieConvocantes;
    }

    /**
     * Checks if is permitir crear financiacion.
     *
     * @return true, if is permitir crear financiacion
     */
    public boolean isPermitirCrearFinanciacion() {
        return permitirCrearFinanciacion;
    }

    /**
     * Sets the permitir crear financiacion.
     *
     * @param permitirCrearFinanciacion
     *            the new permitir crear financiacion
     */
    public void setPermitirCrearFinanciacion(boolean permitirCrearFinanciacion) {
        this.permitirCrearFinanciacion = permitirCrearFinanciacion;
    }

    /**
     * Gets the monton minimo.
     *
     * @return the monton minimo
     */
    public Long getMontonMinimo() {
        return montonMinimo;
    }

    /**
     * Sets the monton minimo.
     *
     * @param montonMinimo
     *            the new monton minimo
     */
    public void setMontonMinimo(Long montonMinimo) {
        this.montonMinimo = montonMinimo;
    }

    /**
     * Gets the tipo financiacion.
     *
     * @return the tipo financiacion
     */
    public String getTipoFinanciacion() {
        return tipoFinanciacion;
    }

    /**
     * Sets the tipo financiacion.
     *
     * @param tipoFinanciacion
     *            the new tipo financiacion
     */
    public void setTipoFinanciacion(String tipoFinanciacion) {
        this.tipoFinanciacion = tipoFinanciacion;
    }

    /**
     * Checks if is mostrar productos academicos.
     *
     * @return true, if is mostrar productos academicos
     */
    public boolean isMostrarProductosAcademicos() {
        return mostrarProductosAcademicos;
    }

    /**
     * Sets the mostrar productos academicos.
     *
     * @param mostrarProductosAcademicos
     *            the new mostrar productos academicos
     */
    public void setMostrarProductosAcademicos(boolean mostrarProductosAcademicos) {
        this.mostrarProductosAcademicos = mostrarProductosAcademicos;
    }

    /**
     * Checks if is incluir contrapartida finanacion.
     *
     * @return true, if is incluir contrapartida finanacion
     */
    public boolean isIncluirContrapartidaFinanacion() {
        return incluirContrapartidaFinanacion;
    }

    /**
     * Sets the incluir contrapartida finanacion.
     *
     * @param incluirContrapartidaFinanacion
     *            the new incluir contrapartida finanacion
     */
    public void setIncluirContrapartidaFinanacion(boolean incluirContrapartidaFinanacion) {
        this.incluirContrapartidaFinanacion = incluirContrapartidaFinanacion;
    }

    /**
     * Gets the vigencia predeterminada.
     *
     * @return the vigenciaPredeterminada
     */
    public Long getVigenciaPredeterminada() {
        return vigenciaPredeterminada;
    }

    /**
     * Sets the vigencia predeterminada.
     *
     * @param vigenciaPredeterminada
     *            the vigenciaPredeterminada to set
     */
    public void setVigenciaPredeterminada(Long vigenciaPredeterminada) {
        this.vigenciaPredeterminada = vigenciaPredeterminada;
    }

    /**
     * Gets the actividades objetivos metas.
     *
     * @return the actividadesObjetivosMetas
     */
    public Long getActividadesObjetivosMetas() {
        return actividadesObjetivosMetas;
    }

    /**
     * Checks if is mostrar nombre lab.
     *
     * @return true, if is mostrar nombre lab
     */
    public boolean isMostrarNombreLab() {
		return mostrarNombreLab;
	}

	/**
	 * Sets the mostrar nombre lab.
	 *
	 * @param mostrarNombreLab the new mostrar nombre lab
	 */
	public void setMostrarNombreLab(boolean mostrarNombreLab) {
		this.mostrarNombreLab = mostrarNombreLab;
	}

	/**
     * Sets the actividades objetivos metas.
     *
     * @param actividadesObjetivosMetas
     *            the actividadesObjetivosMetas to set
     */
    public void setActividadesObjetivosMetas(Long actividadesObjetivosMetas) {
        this.actividadesObjetivosMetas = actividadesObjetivosMetas;
    }

    /**
     * Gets the con mostrar descripcion problema.
     *
     * @return the conMostrarDescripcionProblema
     */
    public Long getConMostrarDescripcionProblema() {
        return conMostrarDescripcionProblema;
    }

    /**
     * Sets the con mostrar descripcion problema.
     *
     * @param conMostrarDescripcionProblema
     *            the conMostrarDescripcionProblema to set
     */
    public void setConMostrarDescripcionProblema(Long conMostrarDescripcionProblema) {
        this.conMostrarDescripcionProblema = conMostrarDescripcionProblema;
    }

    /**
     * Gets the con mostrar biodiversidad.
     *
     * @return the conMostrarBiodiversidad
     */
    public Long getConMostrarBiodiversidad() {
        return conMostrarBiodiversidad;
    }

    /**
     * Sets the con mostrar biodiversidad.
     *
     * @param conMostrarBiodiversidad
     *            the conMostrarBiodiversidad to set
     */
    public void setConMostrarBiodiversidad(Long conMostrarBiodiversidad) {
        this.conMostrarBiodiversidad = conMostrarBiodiversidad;
    }

    /**
     * Gets the con validar equipo trabajo.
     *
     * @return the conValidarEquipoTrabajo
     */
    public Long getConValidarEquipoTrabajo() {
        return conValidarEquipoTrabajo;
    }

    /**
     * Sets the con validar equipo trabajo.
     *
     * @param conValidarEquipoTrabajo
     *            the conValidarEquipoTrabajo to set
     */
    public void setConValidarEquipoTrabajo(Long conValidarEquipoTrabajo) {
        this.conValidarEquipoTrabajo = conValidarEquipoTrabajo;
    }

    /**
     * Gets the url requisitos sede.
     *
     * @return the urlRequisitosSede
     */
    public String getUrlRequisitosSede() {
        return urlRequisitosSede;
    }

    /**
     * Sets the url requisitos sede.
     *
     * @param urlRequisitosSede
     *            the urlRequisitosSede to set
     */
    public void setUrlRequisitosSede(String urlRequisitosSede) {
        this.urlRequisitosSede = urlRequisitosSede;
    }

    /**
     * Checks if is formulario ficha minima.
     *
     * @return true, if is formulario ficha minima
     */
    public boolean isFormularioFichaMinima() {
        return this.getTipo().getId().equals("CCT") || this.getTipo().getId().equals("CSF")
                || this.getTipo().getId().equals("ESI") || this.getTipo().getId().equals("ES7") || this.getTipo().getId().equals("CMP")
                || this.getTipo().getId().equals("CEQ") || this.getTipo().getId().equals("CPU")
                || this.getTipo().getId().equals("CFM") || this.getTipo().getId().equals("FMH") || this.getTipo().getId().equals("CTP")
                || this.getTipo().getId().equals("CBP") || this.getTipo().getId().equals("CL")
                || this.getTipo().getId().equals("CTV") || this.getTipo().getId().equals("CEI")
                || this.getTipo().getId().equals(TIPO_MODALIDAD_MOVILIDAD);
    }

    /**
     * Checks if is habilitar registro.
     *
     * @return the habilitarRegistro
     */
    public Boolean isHabilitarRegistro() {
        return habilitarRegistro;
    }

    /**
     * Sets the habilitar registro.
     *
     * @param habilitarRegistro
     *            the habilitarRegistro to set
     */
    public void setHabilitarRegistro(Boolean habilitarRegistro) {
        this.habilitarRegistro = habilitarRegistro;
    }

	/**
	 * Gets the con mostrar resumen.
	 *
	 * @return the conMostrarResumen
	 */
	public Long getConMostrarResumen()
	{
		return conMostrarResumen;
	}

	/**
	 * Sets the con mostrar resumen.
	 *
	 * @param conMostrarResumen the conMostrarResumen to set
	 */
	public void setConMostrarResumen(Long conMostrarResumen)
	{
		this.conMostrarResumen = conMostrarResumen;
	}

	/**
	 * Gets the con mostrar resultados.
	 *
	 * @return the conMostrarResultados
	 */
	public Long getConMostrarResultados()
	{
		return conMostrarResultados;
	}

	/**
	 * Sets the con mostrar resultados.
	 *
	 * @param conMostrarResultados the conMostrarResultados to set
	 */
	public void setConMostrarResultados(Long conMostrarResultados)
	{
		this.conMostrarResultados = conMostrarResultados;
	}

    /**
     * Gets the instrucciones ingreso costos.
     *
     * @return the instruccionesIngresoCostos
     */
    public String getInstruccionesIngresoCostos() {
        return instruccionesIngresoCostos;
    }

    /**
     * Sets the instrucciones ingreso costos.
     *
     * @param instruccionesIngresoCostos the instruccionesIngresoCostos to set
     */
    public void setInstruccionesIngresoCostos(String instruccionesIngresoCostos) {
        this.instruccionesIngresoCostos = instruccionesIngresoCostos;
    }

    /**
     * Checks if is mostrar actividades investigador no conocido.
     *
     * @return true, if is mostrar actividades investigador no conocido
     */
    public boolean isMostrarActividadesInvestigadorNoConocido() {
        return mostrarActividadesInvestigadorNoConocido;
    }

    /**
     * Sets the mostrar actividades investigador no conocido.
     *
     * @param mostrarActividadesInvestigadorNoConocido the new mostrar actividades investigador no conocido
     */
    public void setMostrarActividadesInvestigadorNoConocido(boolean mostrarActividadesInvestigadorNoConocido) {
        this.mostrarActividadesInvestigadorNoConocido = mostrarActividadesInvestigadorNoConocido;
    }

    public boolean isMostrarOtroProyecto() {
        return mostrarOtroProyecto;
    }

    public void setMostrarOtroProyecto(boolean mostrarOtroProyecto) {
        this.mostrarOtroProyecto = mostrarOtroProyecto;
    }

	public String getTipoInvestigadoresConvocatoria() {
		return tipoInvestigadoresConvocatoria;
	}

	public void setTipoInvestigadoresConvocatoria(
			String tipoInvestigadoresConvocatoria) {
		this.tipoInvestigadoresConvocatoria = tipoInvestigadoresConvocatoria;
	}

	public Long getNumeroProductosObligatorios() {
		return numeroProductosObligatorios;
	}

	public void setNumeroProductosObligatorios(Long numeroProductosObligatorios) {
		this.numeroProductosObligatorios = numeroProductosObligatorios;
	}
	
	public boolean isConvUDEC_SUE(){
    	if(this.getRestriccion()!=null && (this.getId().equals(690L) || this.getId().equals(699L) || this.getId().equals(755L) || this.getId().equals(770L) || this.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_MED_LEGAL_18) || this.getRestriccion().getId().equals(RestriccionConvocatoria.EXT_SOL_2018)|| this.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ALI_NAL_2018) || this.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ALI_NAL_2019) || this.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_DOC_EM_758_2018) || this.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_SUE_2017) || this.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_POSG_COLCI_733))){
    		return true;
    	}else{
    		return false;
    	}
    }

	public Boolean getEsInterDependencias() {
		return esInterDependencias;
	}

	public void setEsInterDependencias(Boolean esInterDependencias) {
		this.esInterDependencias = esInterDependencias;
	}

	public String getInterDependenciasRestriccion() {
		return interDependenciasRestriccion;
	}

	public void setInterDependenciasRestriccion(String interDependenciasRestriccion) {
		this.interDependenciasRestriccion = interDependenciasRestriccion;
	}

	public String getMensajeArchivosAdjuntos() {
		return mensajeArchivosAdjuntos;
	}

	public void setMensajeArchivosAdjuntos(String mensajeArchivosAdjuntos) {
		this.mensajeArchivosAdjuntos = mensajeArchivosAdjuntos;
	}

	public Long getNumeroProyectosPorProfesor() {
		return numeroProyectosPorProfesor;
	}

	public void setNumeroProyectosPorProfesor(Long numeroProyectosPorProfesor) {
		this.numeroProyectosPorProfesor = numeroProyectosPorProfesor;
	}

	public Boolean getEsConvocatoriaMultigrupos() {
		return esConvocatoriaMultigrupos;
	}

	public void setEsConvocatoriaMultigrupos(Boolean esConvocatoriaMultigrupos) {
		this.esConvocatoriaMultigrupos = esConvocatoriaMultigrupos;
	}

	public Long getNumeroMinimoGrupos() {
		return numeroMinimoGrupos;
	}

	public void setNumeroMinimoGrupos(Long numeroMinimoGrupos) {
		this.numeroMinimoGrupos = numeroMinimoGrupos;
	}

	public String getDependenciaGruposRestriccion() {
		return dependenciaGruposRestriccion;
	}

	public void setDependenciaGruposRestriccion(String dependenciaGruposRestriccion) {
		this.dependenciaGruposRestriccion = dependenciaGruposRestriccion;
	}

	public String getRestriccionEquipoTrabajo() {
		return restriccionEquipoTrabajo;
	}

	public void setRestriccionEquipoTrabajo(String restriccionEquipoTrabajo) {
		this.restriccionEquipoTrabajo = restriccionEquipoTrabajo;
	}

	public String getTextoValidacionEquipoTrabajo() {
		return textoValidacionEquipoTrabajo;
	}

	public void setTextoValidacionEquipoTrabajo(String textoValidacionEquipoTrabajo) {
		this.textoValidacionEquipoTrabajo = textoValidacionEquipoTrabajo;
	}
	
	public boolean getEsPermisoMarcoAsignatura() {
		if (this != null && this.getId() != null 
				&& (this.getId().equals(652L) || this.getId().equals(1345L))) {
            return true;
        }
        return false;
	}

    public boolean getEsContratoBiodiversidad() {
        if (this != null && this.getId() != null
                && (this.getId().equals(21L) || this.getId().equals(22L))) {
            return true;
        }
        return false;
    }
    
    public boolean getEsPermisoMarco() {
        if (this != null && this.getId() != null && (this.getId().equals(150L) || this.getId().equals(1344L))) {
            return true;
        }
        return false;
    }
    
    public boolean isEsConvBiodiversidad() {
        if (getEsPermisoMarco() || getEsPermisoMarcoAsignatura() || getEsContratoBiodiversidad()) {
            return true;
        }
        return false;
    }

	public String getMensajeRubrosBasicos() {
		return mensajeRubrosBasicos;
	}

	public void setMensajeRubrosBasicos(String mensajeRubrosBasicos) {
		this.mensajeRubrosBasicos = mensajeRubrosBasicos;
	}

	public String getProductosAValidar() {
		return productosAValidar;
	}

	public void setProductosAValidar(String productosAValidar) {
		this.productosAValidar = productosAValidar;
	}

	public String getMensajeProductosAValidar() {
		return mensajeProductosAValidar;
	}

	public void setMensajeProductosAValidar(String mensajeProductosAValidar) {
		this.mensajeProductosAValidar = mensajeProductosAValidar;
	}

	public String getCaracterEventoConvocatoriaMovilidad() {
		return caracterEventoConvocatoriaMovilidad;
	}

	public void setCaracterEventoConvocatoriaMovilidad(String caracterEventoConvocatoriaMovilidad) {
		this.caracterEventoConvocatoriaMovilidad = caracterEventoConvocatoriaMovilidad;
	}

	public String getSubmodalidadConvocatoriaMovilidad() {
		return submodalidadConvocatoriaMovilidad;
	}

	public void setSubmodalidadConvocatoriaMovilidad(String submodalidadConvocatoriaMovilidad) {
		this.submodalidadConvocatoriaMovilidad = submodalidadConvocatoriaMovilidad;
	}

	public String getValidacionCostosConvocatoriaMovilidad() {
		return validacionCostosConvocatoriaMovilidad;
	}

	public void setValidacionCostosConvocatoriaMovilidad(String validacionCostosConvocatoriaMovilidad) {
		this.validacionCostosConvocatoriaMovilidad = validacionCostosConvocatoriaMovilidad;
	}

	public String getValidacionDuracionMovilidad() {
		return validacionDuracionMovilidad;
	}

	public void setValidacionDuracionMovilidad(String validacionDuracionMovilidad) {
		this.validacionDuracionMovilidad = validacionDuracionMovilidad;
	}

	public String getMostrarColombiaMovilidad() {
		return mostrarColombiaMovilidad;
	}

	public void setMostrarColombiaMovilidad(String mostrarColombiaMovilidad) {
		this.mostrarColombiaMovilidad = mostrarColombiaMovilidad;
	}

	public String getMostrarLaboratorios() {
		return mostrarLaboratorios;
	}

	public void setMostrarLaboratorios(String mostrarLaboratorios) {
		this.mostrarLaboratorios = mostrarLaboratorios;
	}

	public Long getNumeroLaboratoriosValidacion() {
		return numeroLaboratoriosValidacion;
	}

	public void setNumeroLaboratoriosValidacion(Long numeroLaboratoriosValidacion) {
		this.numeroLaboratoriosValidacion = numeroLaboratoriosValidacion;
	}

	public String getTipoEstudianteMovilidad() {
		return tipoEstudianteMovilidad;
	}

	public void setTipoEstudianteMovilidad(String tipoEstudianteMovilidad) {
		this.tipoEstudianteMovilidad = tipoEstudianteMovilidad;
	}

	public String getDependenciasEstudiantesMovilidad() {
		return dependenciasEstudiantesMovilidad;
	}

	public void setDependenciasEstudiantesMovilidad(String dependenciasEstudiantesMovilidad) {
		this.dependenciasEstudiantesMovilidad = dependenciasEstudiantesMovilidad;
	}

	public String getDependenciaRevisionMovilidadesEst() {
		return dependenciaRevisionMovilidadesEst;
	}

	public void setDependenciaRevisionMovilidadesEst(String dependenciaRevisionMovilidadesEst) {
		this.dependenciaRevisionMovilidadesEst = dependenciaRevisionMovilidadesEst;
	}

	public String getDominioTipologiaProyectos() {
		return dominioTipologiaProyectos;
	}

	public void setDominioTipologiaProyectos(String dominioTipologiaProyectos) {
		this.dominioTipologiaProyectos = dominioTipologiaProyectos;
	}

	public String getTipoEvaluacionProyectos() {
		return tipoEvaluacionProyectos;
	}

	public void setTipoEvaluacionProyectos(String tipoEvaluacionProyectos) {
		this.tipoEvaluacionProyectos = tipoEvaluacionProyectos;
	}

	public String getMostrarPasaporteMovilidad() {
		return mostrarPasaporteMovilidad;
	}

	public void setMostrarPasaporteMovilidad(String mostrarPasaporteMovilidad) {
		this.mostrarPasaporteMovilidad = mostrarPasaporteMovilidad;
	}

	public Long getMaxTrabajosMovilidad() {
		return maxTrabajosMovilidad;
	}

	public void setMaxTrabajosMovilidad(Long maxTrabajosMovilidad) {
		this.maxTrabajosMovilidad = maxTrabajosMovilidad;
	}

	public String getMostrarGrupoMovilidad() {
		return mostrarGrupoMovilidad;
	}

	public void setMostrarGrupoMovilidad(String mostrarGrupoMovilidad) {
		this.mostrarGrupoMovilidad = mostrarGrupoMovilidad;
	}

	public String getMostrarFinanciacion() {
		return mostrarFinanciacion;
	}

	public void setMostrarFinanciacion(String mostrarFinanciacion) {
		this.mostrarFinanciacion = mostrarFinanciacion;
	}

	public String getMostrarEventoMovilidad() {
		return mostrarEventoMovilidad;
	}

	public void setMostrarEventoMovilidad(String mostrarEventoMovilidad) {
		this.mostrarEventoMovilidad = mostrarEventoMovilidad;
	}

	public Integer getRequiereEvaluacion() {
		return requiereEvaluacion;
	}

	public void setRequiereEvaluacion(Integer requiereEvaluacion) {
		this.requiereEvaluacion = requiereEvaluacion;
	}

	public Integer getMostrarLugarEspecifico() {
		return mostrarLugarEspecifico;
	}

	public void setMostrarLugarEspecifico(Integer mostrarLugarEspecifico) {
		this.mostrarLugarEspecifico = mostrarLugarEspecifico;
	}

	public Integer getRegistrosPorGrupo() {
		return registrosPorGrupo;
	}

	public void setRegistrosPorGrupo(Integer registrosPorGrupo) {
		this.registrosPorGrupo = registrosPorGrupo;
	}

	public Integer getRegistrosPorLaboratorio() {
		return registrosPorLaboratorio;
	}

	public void setRegistrosPorLaboratorio(Integer registrosPorLaboratorio) {
		this.registrosPorLaboratorio = registrosPorLaboratorio;
	}

	public Integer getMostrarObjetivos() {
		return mostrarObjetivos;
	}

	public void setMostrarObjetivos(Integer mostrarObjetivos) {
		this.mostrarObjetivos = mostrarObjetivos;
	}

	public Integer getObligatorioSubirArchivos() {
		return obligatorioSubirArchivos;
	}

	public void setObligatorioSubirArchivos(Integer obligatorioSubirArchivos) {
		this.obligatorioSubirArchivos = obligatorioSubirArchivos;
	}

	public Integer getMostrarColeccionAsociada() {
		return mostrarColeccionAsociada;
	}

	public void setMostrarColeccionAsociada(Integer mostrarColecciones) {
		this.mostrarColeccionAsociada = mostrarColecciones;
	}

	public Integer getMostrarTituloRevista() {
		return mostrarTituloRevista;
	}

	public void setMostrarTituloRevista(Integer mostrarTituloRevista) {
		this.mostrarTituloRevista = mostrarTituloRevista;
	}

	public Boolean getEsParaSemilleros() {
		return esParaSemilleros;
	}

	public void setEsParaSemilleros(Boolean esParaSemilleros) {
		this.esParaSemilleros = esParaSemilleros;
	}

	public Boolean getLiderSemilleros() {
		return liderSemilleros;
	}

	public void setLiderSemilleros(Boolean liderSemilleros) {
		this.liderSemilleros = liderSemilleros;
	}

	public Integer getNumeroMinimoSemilleros() {
		return numeroMinimoSemilleros;
	}

	public void setNumeroMinimoSemilleros(Integer numeroMinimoSemilleros) {
		this.numeroMinimoSemilleros = numeroMinimoSemilleros;
	}

	public Boolean getCoordinadorLaboratorios() {
		return coordinadorLaboratorios;
	}

	public void setCoordinadorLaboratorios(Boolean coordinadorLaboratorios) {
		this.coordinadorLaboratorios = coordinadorLaboratorios;
	}

	public boolean isMostrarDependenciasAportantes() {
		return mostrarDependenciasAportantes;
	}

	public void setMostrarDependenciasAportantes(boolean mostrarDependenciasAportantes) {
		this.mostrarDependenciasAportantes = mostrarDependenciasAportantes;
	}

	public boolean isMostrarEquiposServicios() {
		return mostrarEquiposServicios;
	}

	public void setMostrarEquiposServicios(boolean mostrarEquiposServicios) {
		this.mostrarEquiposServicios = mostrarEquiposServicios;
	}

	public boolean isUsarFormularioProyectoEditorial() {
		return usarFormularioProyectoEditorial;
	}

	public void setUsarFormularioProyectoEditorial(boolean usarFormularioProyectoEditorial) {
		this.usarFormularioProyectoEditorial = usarFormularioProyectoEditorial;
	}

	public boolean isEditaCreadorProyecto() {
		return editaCreadorProyecto;
	}

	public void setEditaCreadorProyecto(boolean editaCreadorProyecto) {
		this.editaCreadorProyecto = editaCreadorProyecto;
	}

	public boolean isAdquisicionEquiposObligatorio() {
		return adquisicionEquiposObligatorio;
	}

	public void setAdquisicionEquiposObligatorio(boolean adquisicionEquiposObligatorio) {
		this.adquisicionEquiposObligatorio = adquisicionEquiposObligatorio;
	}

	public boolean isIncluirFinanciacionExterna() {
		return incluirFinanciacionExterna;
	}

	public void setIncluirFinanciacionExterna(boolean incluirFinanciacionExterna) {
		this.incluirFinanciacionExterna = incluirFinanciacionExterna;
	}

	public boolean isGruposSinRestriccionIntegrante() {
		return gruposSinRestriccionIntegrante;
	}

	public void setGruposSinRestriccionIntegrante(boolean gruposSinRestriccionIntegrante) {
		this.gruposSinRestriccionIntegrante = gruposSinRestriccionIntegrante;
	}

	public String getMostrarDetalleGasto() {
		return mostrarDetalleGasto;
	}

	public void setMostrarDetalleGasto(String mostrarDetalleGasto) {
		this.mostrarDetalleGasto = mostrarDetalleGasto;
	}
	
	public boolean validarDetalleGasto() {
		if(mostrarDetalleGasto!=null && mostrarDetalleGasto.equals("S") && detalleRubroObligatorio!=null && detalleRubroObligatorio.equals("S")) {
			return true;
		}
			return false;
	}

	public String getDetalleRubroObligatorio() {
		return detalleRubroObligatorio;
	}

	public void setDetalleRubroObligatorio(String detalleRubroObligatorio) {
		this.detalleRubroObligatorio = detalleRubroObligatorio;
	}

	public boolean isMostrarDetGasto() {
		mostrarDetGasto = false;
		if(mostrarDetalleGasto!=null && mostrarDetalleGasto.equals("S")) {
			mostrarDetGasto = true;
		}
		return mostrarDetGasto;
	}

	public void setMostrarDetGasto(boolean mostrarDetGasto) {
		this.mostrarDetGasto = mostrarDetGasto;
	}

	public String getNotaLaboratorios() {
		return notaLaboratorios;
	}

	public void setNotaLaboratorios(String notaLaboratorios) {
		this.notaLaboratorios = notaLaboratorios;
	}

	public Boolean getMostrarTipoMovilidad() {
		return mostrarTipoMovilidad;
	}

	public void setMostrarTipoMovilidad(Boolean mostrarTipoMovilidad) {
		this.mostrarTipoMovilidad = mostrarTipoMovilidad;
	}

	public long getTamanoArchivoMb() {
		return tamanoArchivoMb;
	}
	
	public void setTamanoArchivoMb(long tamanoArchivos) {
		this.tamanoArchivoMb = tamanoArchivos;
	}
	
	public Long getNumeroMovilidadesPorEstudiante() {
		return numeroMovilidadesPorEstudiante;
	}

	public void setNumeroMovilidadesPorEstudiante(Long numeroMovilidadesPorEstudiante) {
		this.numeroMovilidadesPorEstudiante = numeroMovilidadesPorEstudiante;
	}

	public String getInfoAdicionalFormulario() {
		return infoAdicionalFormulario;
	}

	public void setInfoAdicionalFormulario(String infoAdicionalFormulario) {
		this.infoAdicionalFormulario = infoAdicionalFormulario;
	}

}