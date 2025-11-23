package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.mapeo.GastoFM;
import co.edu.unal.hermes.modelo.seguimiento.ProyectoProrroga;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;
import co.edu.unal.hermes.utils.UtilPalabraClave;
import co.edu.unal.hermes.vista.proyectos.manejadorproyecto.InvestigadorProyectoVista;

/**
 * 
 * @author Juan Pablo Duque G.
 */
public class Proyecto implements Serializable, Comparable<Proyecto>, Cloneable {

    private static final long serialVersionUID = 1066430357866601094L;

    public static final Long MAXIMO_SIN_PRODUCTOS_ACADEMICOS = 36725L;

    public static final String PROYECTO_INTERNO = "Interno";
    public static final String PROYECTO = "PRY";
    public static final String PROGRAMA= "PRG";

    public static final String RECLAMACION_APROBADO = "A";
    public static final String RECLAMACION_RECHAZADO = "R";
    public static final String RECLAMACION_ENVIADO = "E";

    public static final String PROGRAMA_MARCO_SI = "SI";

    public static final String ROL_UNIVERSIDAD_PARTICIPANTE = "ROL_PARTICIP";

    // llave primaria
    private Long id;

    // Atributos foraneos
    /**
     * Representa el estado actual del proyecto en la convocatoria Dentro de
     * estos están: Propuesto, rechazado, negado, aprobado, suspendido, activo,
     * cancelado y finalizado
     */
    private EstadoProyecto estadoProyecto;
    private EstadoProyecto estadoProyectoLegalizacion;
    private Proyecto proyectoRefinanciado;
    private ProgramaNacionalPRN programaNacional;

    private ProyectoSaldoFinanciacion proyectoSaldoFinanciacion;
    private InvestigadorProyecto investigadorPrincipalVista;
    private Convenio convenio;

    /**
     * El proyecto depende de una modalidad especifica, la cual puede ser una
     * convocatoria, jorndad docente, etc.
     */
    private Modalidad modalidad;

    /**
     * Son los tipos de investigación donde se encuentran: aplicada, basica, etc
     */
    private TipoInvestigacion tipoInvestigacion;

    private PlanGlobalDesarrollo planGlobalDesarrollo;

    // Persona responsable del proyecto o quien lo dirige
    private Investigador responsable;
    private String nombre = "--";
    private String resumen = "--";
    private String entidadesParticipantes;
    private TipoDuracion tipoDuracion;
    private Integer duracion;
    private Integer fase;
    private String codigoQuipu;
    private String segundoCodigoQuipu;
    private String codigoDib;
    private String lugar;
    private String observaciones;
    private Long valorSolicitado;
    private Long otrosAportes;
    private String justificacion;
    private String marcoTeorico;
    private String objetivoGeneral;
    private String metodologia;
    private String programaAcademico;

    private String asignatura;

    private String presentacionPrograma;
    private String consideracionesEticas;

    private String propiedadIntelectual;
    private String impactoEsperado;
    private String descripcion;
    private String permitirModificacion;
    private String involucraMaestriaDoctorado;
    private String lineaBaseProyecto;
    private String nombreConvocatoriaPadre;
    private Dependencia dependenciaPrincipal;

    private Date fechaTentativaInicio;
    private Date fechaFinalizacion;
    private Date fechaAti;
    private Integer duracionAcumulada;
    private Tipos relacionBicentenario;

    private VAsignaturasSIA asignaturaSIA;
    // ficha minima
    private String tipoActividad;
    private String tipoActividadECP;
    private String subTipoActividadECP;
    private String rolUniversidad;
    private String mecanismoParticipacion;
    private Long valorTotalCostosDirectos;
    private Long valorTotalCostosIndirectos;
    private Long valorTotalTransferencias;
    private Long contrapartidaEfectivo = 0L;
    private Long contrapartidaEspecieTotal = 0L;
    private Long contrapartidaExternaEspecieTotal = 0L;
    private Long valorTotal = 0L;

    // lmom
    private Integer duracions = 0;
    private Integer duraciond = 0;
    private Integer duracionh = 0;
    private Integer duracionA = 0;
    private Long semanasFormulacion = 0L;
    private Long horasSemanaFormulacion = 0L;
    private String objetivoSocioeconomico;
    private Integer duraciona = 0;
    private Long valorPersonalTotal = 0L;
    private Boolean naDuracion;
    private Boolean naDuracionF;
    private Long valorAdministrativoTotal = 0L;
    private String numeroRes;
    private Long costoFacultad = 0L; // se reemplaza en FM por valor en especie
                                     // entidad externa
    private Long costoSede = 0L;
    private Long costoNacional = 0L;
    private Long fondoExtSol = 0L;
    private Long fondoRiesgosUN = 0L;
    private Long fondoEspecialFac = 0L;
    private Long fondoInvestigacUN = 0L;
    private Long fondoEspecialDirA = 0L;
    private Long dirNacionalExt = 0L;
    private Long dirExtSede = 0L;
    private String antecedentes;
    private String tipoActividadPpal;
    private String duracionTipo;
    public static final String DURACION_ANOS = "A";
    public static final String DURACION_MESES = "M";
    public static final String DURACION_SEMANAS = "S";
    public static final String DURACION_DIAS = "D";
    public static final String DURACION_HORAS = "H";
    private Integer codLineaAccion;
    private Integer codPrograma;
    private Integer componentePlanDesarrollo;
    private Integer ambitoGeneralPM;
    private Integer coleccionBiologicaPM;
    private String noColeccionBiologicaPM;
    private String cateogoriasTaxonomicasPM;
    private Integer tipoActividadInvestigacionPM;
    private Integer areaGeograficaPM;
    private String jurisdiccionPM;
    private Double porcentajeFacultad;
    private Double porcentajeCostosIndirectos;
    private Double porcentajeTransferencias;
    private String exoneraInd; // en legalización tiene asociado un programa
    // macro
    private String exoneraTra; // en legalización tiene asociado 'otroTipo de
                               // compromiso'
    private String creadorId;
    private String creadorDocumento;
    private Long corte;
    private CorteConvocatoria corteConvocatoria;
    private Boolean tienePryAsociado;
    private Long proyectoAsociado;
    private String esJornadaDocente;
    private String esPryContrapartida;
    private String directorExterno;
    private Long documentoExterno;
    private Short horasExterno;
    private Long estimuloExterno;
    private String tipoDocExterno;
    private String activoPI;
    private String tieneActivoPI = "No";
    private int numeroActaInicio;
    private Date fechaInformeFinal;
    private String motivoEstadoProyectoOtro;
    private boolean requiereAutorizacCR;
    private String tieneCompromisosPendientes;
    private Date fechaLegalizacion;
    private Date fechaLegalizacionParcial;

    // Biodiversidad
    private String tieneBiodiversidad = "No";
    private String tieneLaboratorios = "No";
    private String tieneComunidades = "No";
    private String tieneParques = "No";
    private String tieneRecoleccion = "Si";
    private String tieneBioProspeccion = "No";
    private String tieneEspecieAmenazada = "No";
    private String tieneAcuicultura = "No";

    // Cuando el proyecto es de tipo "Creacion Aritistica" tien un objeto
    // adicional
    private CreacionArtistica creacionArtistica;

    // Beneficios extension solidaria
    private String beneficiosExtSol;

    private String iniciativaDe;
    private String iniciativa;
    private String cargaDocenteSiNo;
    private String tipoZona;
    private Long numeroGrupos;

    // reclamaciones
    private String reclamacion;
    private String respuestaReclamacion;
    private String estadoReclamacion;
    private String reclamacionEvaluacion;
    private String respuestaReclamacionEvaluacion;
    private String estadoReclamacionEvaluacion;
    private Date fechaEnvioReclamacionReq;
    private Date fechaRespuestaReclamacionReq;
    private Date fechaEnvioReclamacionEva;
    private Date fechaRespuestaReclamacionEva;

    // tipo de proyecto
    private boolean esInvestigacion;
    private boolean esExtension;
    
    private Dependencia dependenciaPresentacion;

    public Integer getDuracionAcumulada() {
        return duracionAcumulada;
    }

    public void setDuracionAcumulada(Integer duracionAcumulada) {
        this.duracionAcumulada = duracionAcumulada;
    }

    private Set<Ciudad> ciudades = new HashSet<Ciudad>();
    private Set grupos = new HashSet();
    private Set laboratorios = new HashSet();
    private Set lugaresEjecucion = new HashSet();
    private Set equiposAdquisicion = new HashSet();
    private Set<InvestigadorProyecto> investigadoresProyecto = new HashSet<InvestigadorProyecto>();
    private Set lineas = new HashSet();
    private Set<AgendaProyecto> agendas = new HashSet<AgendaProyecto>();
    private Set<ResultadoProyecto> resultados = new HashSet<ResultadoProyecto>();
    private Set<ObjetivoEspecifico> objetivosEspecificos = new HashSet<ObjetivoEspecifico>();
//    private Set<MetaProyecto> metasProyecto = new HashSet<MetaProyecto>();
    private Set<PalabraClave> palabrasClaves = new HashSet<PalabraClave>();

    private Set posiblesEvaluadoresExternos = new HashSet();
    private Set<Archivo> archivos = new HashSet<Archivo>();
    private Set<Actividad> actividades = new HashSet();
    private Set objetivosResultados = new HashSet();
    private Set bibliografias = new HashSet();
    private Set historico = new HashSet();
    private Set<ClasificacionConocimiento> clasificacionConocimiento = new HashSet<ClasificacionConocimiento>();
    private Set<AreaTematica> areasTematicas = new HashSet<AreaTematica>();
    private Set<ValoresListasProyecto> objetivosDesarrolloSostenible = new HashSet<ValoresListasProyecto>();
    private Set posiblesEvaluadoresInternos = new HashSet();

    private Set<Persona> asesores = new HashSet<Persona>();
    private Set observacionesProyecto = new HashSet();
    private Set<ProyectoProrroga> prorrogasProyecto = new HashSet<ProyectoProrroga>();
    private Set<Solicitud> solicitudes = new HashSet<Solicitud>();
    private Set<ProyectoInforme> informesProyecto = new HashSet<ProyectoInforme>();
    private Set historicoEstLega = new HashSet();

    /**
     * Set con las dependencias y sus respectivas areas de responsabilidad
     */
    private Set dependenciasAreaResponsabilidad = new HashSet();
    private Set proyectosPrograma = new HashSet();

    // Presupuesto o gastos
    private Set<Financiacion> financiaciones = new HashSet<Financiacion>();

    // Evaluadores del proyecto
    private Set evaluadoresProyecto = new HashSet();
    private Set equipos = new HashSet();

    private Set productosProyecto = new HashSet();
    private Set requisitosProyecto = new HashSet();
    private Set<ProyectoCompromiso> compromisosProyecto = new HashSet<ProyectoCompromiso>();

    private Set empresas = new HashSet();

    private Set posiblesEvaluadoresInvestigadoresExternos;

    // sostenibilidad proyectos extensión
    private String nivelSostenibilidad;
    private String porqueNivelSostenibilidad;
    private String continuidadProyecto;
    private String porqueContinuidadProyecto;
    private String articulacionIniciativas;
    private String aliados;
    private String observacionesSostenibilidad;
    private String soportesUrl;
    private String viabilidad;
    private String apropiacion;
    private String transferencia;
    private String marcoConceptualExtSol;
    private String condicionesEntornoExtSol;
    private String procesosTransferenciaExtSol;
    private String planteamientoRolesExtSol;
    private String planteamientoIndicadoresExtSol;
    private String participacionComunidadExtSol;
    private String atributosSolInnoExtSol;

    // HER_EXT AVAL EXTENSIÓN
    private String modalidadEcp;
    private String claseEvento;
    private String claseEventoExt;
    private String otroClaseEvento;
    private Dependencia dependencia;
    private String ejeTematico;
    private String costoPersona;
    private String maxAsistentes;
    private Integer sesiones;
    private Float horasSesiones;
    private String diaSesion;
    private String horaSesion;
    private String tipoEspacio;
    private String monitores; // En legalizacion tiene asociado el campo 'otro
                              // tipo de fecha de inicio'
    private Integer nroMonitores; // En legalizacion tiene asociado el campo
                                  // 'Número del acto adm interno'
    private String logistica;
    private String otraLogistica;
    private String servicios;
    private String otroServicio; // En legalizacion tiene asociado el campo
                                 // 'otro tipo de programa nacional'
    private String descripcionLogistica; // en legalización = nombre del
                                         // contrato
    private Long conferencistas; // en legalización = numero del acta de
                                 // inicio ext.

    private String planTematico;
    private String metodo;
    private String horario;
    private String ciudad;

    private Float valorInscripcion;
    private Long cuposDescuento; // En legalizacion tiene asociado el campo
                                 // 'Número del acto ext'
    private String sistCalificacion;
    private String tipoCertificacion;

    private Set horarioCursoECP = new HashSet();

    // Sesiones curso ECP
    private Set sesionesECP = new HashSet();

    // lmom
    private Sede sedeEjecucion;
    private Dependencia facultadEjecucion;
    
    private Set<PoblacionObjetivoEvento> poblacionObjetivo = new HashSet<PoblacionObjetivoEvento>();
    
    private Set<ProyectoAreaGestionConocimiento> areaGestionConocimiento = new HashSet<ProyectoAreaGestionConocimiento>();

    // convocatoria eventos
    private String rei1 = "NO";
    private String rei2 = "NO";
    private String rei3 = "NO";
    private String rei4 = "NO";
    private String rei5 = "NO";
    private String rei6 = "NO";
    private String rei7 = "NO";
    private String ren1 = "NO";
    private String ren2 = "NO";
    private String ren3 = "NO";
    private String ren4 = "NO";
    private String ren5 = "NO";
    private String ren6 = "NO";
    private String ren7 = "NO";
    private String aporteEconomico; // En FM es = la entidad aporta recursos en
                                    // especie?
    private String paisEvento;
    private String asistentes;
    private String trabajoColaborativo;
    private String conferencistasNacExt;
    private String conferencistasNacVinc;
    private String conferencistasInternac;
    private String tipoParticipacion;
    private String opcionesPermisoMarco;
    
    private String grupoDirige;
    private String poblacionDirige;
    private String articulacionLineasGrupos;
    
    private String experienciaDocenteCompetencias;
    private String formacionEstudiantesCompetencia;

    // DescuentosECP
    private Set DescuentosECP = new HashSet();
    private String claseActividadECP;

 // ISBN
    private String tituloVolumenISBN;
    private String tituloObraCompletaISBN;
    private String subTituloObraCompletaISBN;

    private String materiaISBN;
    private String tipoContenidoISBN;
    private String nombreColeccionISBN;
    private Long numeroColeccionISBN;
    private String serieISBN;
    private String idiomaPrincipalISBN;

    private String esTraduccionISBN;
    private String idiomaOriginalISBN;
    private String idiomaDestinoISBN;
    private String tituloIdiomaOriginalISBN;

    private Long numeroEdicionISBN;
    private String ciudadEdicionISBN;
    private String departamentoEdicionISBN;
    private Date fechaAparicionISBN;
    private String esCoedicionISBN;
    private String coeditorISBN;

    private String esComercializableISBN;
    private Long numEjemplaresNacionalISBN;
    private Long precioCOPISBN;
    private Long numEjemplaresExternosISBN;
    private Long precioUSDISBN;
    private Long ofertaTotalISBN;
    private String razonesNoComercializableISBN;

    private String tipoSoporteISBN;
    private String descripcionFisicaISBN;
    private String tipoEncuadernacionISBN;
    private String tipoPapelISBN;
    private String gramajeISBN;
    private String tipoImpresionISBN;
    private Long numPaginasISBN;
    private String numTintasISBN;
    private Long anchoISBN;
    private Long altoISBN;
    private String medioElectronicoISBN;
    private String formatoISBN;
    private Long tamañoISBN;
    private String unidadMedidaTamañoISBN;
    
    private String tipoObra;
    private String tipoPublicacion;
    private String audiencia;
    private String idiomaOrigen;
//    private Long numeroEjemplaresNacional;
//    private Long numeroEjemplaresExt;
//    private Long numeroEjemplaresTotal;
    private String tipoISBN;
    private Long pesoGramosObra;
    private String tipoSoporteDigital;
    private String tipoContenidoProducto;
    private String proteccionTecnicaArchivos;
    private String permisoUso;
    private String esCamaraColLibro;
    private String resena;
    
    private Boolean esISBNImpreso;
    private Boolean esISBNDigital;
    private Boolean esISBNIBD;
    
    private String descripcionFisicaISBNIBD;
    private String tipoEncuadernacionISBNIBD;
    private String tipoPapelISBNIBD;
    private String gramajeISBNIBD;
    private Long pesoGramosObraIBD;
    private String tipoImpresionISBNIBD;
    private Long numPaginasISBNIBD;
    private String numTintasISBNIBD;
    private Long anchoISBNIBD;
    private Long altoISBNIBD;
    
    private Long clasificacionThemaISBNNivel1;
    private Long clasificacionThemaISBNNivel2;
    private Long clasificacionThemaISBNNivel3;
    private Long clasificacionThemaISBNNivel4;
    private Long clasificacionThemaISBNNivel5;
    private Long clasificacionThemaISBNNivel6;
    
    private Long clasificacionLugarISBNNivel1;
    private Long clasificacionLugarISBNNivel2;
    private Long clasificacionLugarISBNNivel3;
    private Long clasificacionLugarISBNNivel4;
    private Long clasificacionLugarISBNNivel5;
    private Long clasificacionLugarISBNNivel6;
    private Long clasificacionLugarISBNNivel7;
    private Long clasificacionLugarISBNNivel8;
    private Long clasificacionLugarISBNNivel9;
    
    private Long clasificacionIdiomaISBNNivel1;
    private Long clasificacionIdiomaISBNNivel2;
    private Long clasificacionIdiomaISBNNivel3;
    private Long clasificacionIdiomaISBNNivel4;
    private Long clasificacionIdiomaISBNNivel5;
    
    private Long clasificacionPerHistoricoISBNNivel1;
    private Long clasificacionPerHistoricoISBNNivel2;
    private Long clasificacionPerHistoricoISBNNivel3;
    private Long clasificacionPerHistoricoISBNNivel4;
    private Long clasificacionPerHistoricoISBNNivel5;
    
    private Long clasificacionFinDidacticoISBNNivel1;
    private Long clasificacionFinDidacticoISBNNivel2;
    private Long clasificacionFinDidacticoISBNNivel3;
    private Long clasificacionFinDidacticoISBNNivel4;
    private Long clasificacionFinDidacticoISBNNivel5;
    
    private Long clasificacionEdadInteresISBNNivel1;
    private Long clasificacionEdadInteresISBNNivel2;
    private Long clasificacionEdadInteresISBNNivel3;
    private Long clasificacionEdadInteresISBNNivel4;
    
    private Long clasificacionEstiloISBNNivel1;
    private Long clasificacionEstiloISBNNivel2;
    
    private Boolean tipoPublicacionLibro;
    private Boolean tipoPublicacionElect;
    private Long disponibilidadComercializable;
    private Long tipoAccesoISBNDigital;
    
    private Long numeroVolumenISBN;
    private String disponibleEnISBN;
    private Long precioLibroDigitalISBN;
    private String tablaContenidoISBN;
    private String tieneSelloDigitalISBN;
    
    private String serviciosAcademicos;
    // private Double porcentajeExoneracionCostosInd;
    // private Double porcentajeExoneracionTransferencias;
    private String justificacionExoneracionCostosInd; // en legalización =
                                                      // justificación no
    // aprobación pry
    // externo
    private String justificacionExoneracionTransferencias; // en legalización =
                                                           // motivo no
    // aprobación pry
    // externo
    private String proyectoUsadoNoHermes;
    private String entidadFinancieraNoHermes;

    private String publicoObjetivo;

    private List avalesProyecto;

    // Formalizac pry externo
    private String tipologiaProyecto;
    private String unidadEjecutora;
    private String empresaEjecutora;
    private String actoEntidad;
    private String actoInterno;

    private String entidadContratante;
    private String tipofechaPry;
    private String desembolsos;
    private String informes;
    private String trasladoRubros;
    private String vinculaFacultades = "0";
    // private List<Dependencia> listaFacultadesAdicionadas;
    private String vinculaSedes = "0";
    private String sedeSel;
    // private List<Sede> listaSedesAdicionadas;
    private Sede sedeSeleccionada;
    private Dependencia facSeleccionada;
    private String entidadSel;
    // private List<Financiacion> listaEntidadesAdicionadas;
    private String entidadSeleccionada;
    private String vinculaEntidades = "0";
    private Long valorEntidad = 0L;
    private Long montoUNTotal;
    private Long contrapartidaEspecieUN;
    private Long contrapartidaEfectivoUN;

    private Long duracionFinal;

    private String tipologia_sec;
    private Long proyectoPadre;
    private Long sedeEjecutora;
    private String presentaInformeParcial;
    private String periodoInforme;
    private String presentaInformeFinal;
    private Long valorTotalFinal = 0L;

    private int numeroDesembolsos;
    private String programaCYT;
    private Integer duracionDiasAcumulada;
    private Long avalAsociado;
    private String programaMacro;

    private Date fechaActoAdministrativo;
    private String compromisoEjecucion;
    private String entidadSeleccionadaEspecie; // FM entidad que aporta recursos
                                               // en especie
    private Date fechaActaInicioExt;

    private Tipos tipoProyectoLaboratorios;

    private Tipos subtipoProyectoLaboratorios;

    private Long vigencia;

    private String otroActoAdministrativoInterno;
    
    private String nombreLaboratorio;
    
    private boolean tieneAvalesAprobados;
    private String lugarEspecifico;
    private Date fechaInicioEjecucionEvento;
    private Date fechaFinEjecucionEvento;
    protected static final String DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE = "OBJETIVOS_DESARROLLO_SOSTENIBLE";
    
    private String notaCodigoQuipu;
    private String caracteristicaProyectoPrograma;
    private String campoGenericoUno;
    private String campoGenericoDos;
    private String campoGenericoTres;
    private String campoGenericoCuatro;
    private String campoGenericoCinco;
    private String campoGenericoSeis;
    private String campoGenericoSiete;
    private String campoGenericoOcho;
    private String campoGenericoNueve;
    private String campoGenericoDiez;
    private String campoGenericoOnce;
    private String campoGenericoDoce;
    private String campoGenericoTrece;
    private String campoGenericoCatorce;
    private String campoGenericoQuince;
    private String campoGenericoDieciseis;
    
    private String solucionAlternaExtSol;
    private String aplicabilidadJustificacionExtSol;
    private String aplicabilidadJustificacionAEExtSol;
    
    private Set<Semillero> semilleros = new HashSet<Semillero>();
    
    private ProyectoEditorial fichaEditorial;
    private Set<ProyectoEditorialTitulo> titulosEditorial =new HashSet<ProyectoEditorialTitulo>();
    
    private Set dependenciasAportantes = new HashSet();
    private Long montoInicialAprobado; //Variable para guardar el monto al momento de la aprobación del proyecto, sirve para validar posteriormente la discriminación por vigencias.
    
    private String permitirAvalRegaliasRequisitos;
    
    public Proyecto() {
    }

    /**
     * Constructor que recibe como parametro el id, para inicializar el id del
     * objeto.
     * 
     * @param LongpIdTipoDato
     */
    public Proyecto(Long pId) {
        this.id = pId;
    }

    public void adicionarAsesor(Persona p) {
        asesores.clear();
        if (p != null) {
            asesores.add(p);
        }
    }

    public void adicionarResultado(ResultadoProyecto resultado) {
        if (resultados == null) {
            resultados = new HashSet<ResultadoProyecto>();
        }
        resultado.setProyecto(this);
        resultados.add(resultado);
    }

    public void borrarResultado(ResultadoProyecto resultado) {
        resultados.remove(resultado);
    }

    public void adicionarObjetivoEspecifico(ObjetivoEspecifico objetivo) {
        if (objetivosEspecificos == null) {
            objetivosEspecificos = new HashSet<ObjetivoEspecifico>();
        }
        objetivo.setProyecto(this);
        objetivosEspecificos.add(objetivo);
    }

    public void borrarObjetivoEspecifico(ObjetivoEspecifico objetivo) {
        objetivosEspecificos.remove(objetivo);
    }

    public void adicionarPosibleEvaluador(PosibleEvaluador evaluador) {
        evaluador.setProyecto(this);
        posiblesEvaluadoresExternos.add(evaluador);
    }

    public void borrarPosibleEvaluador(PosibleEvaluador evaluador) {
        posiblesEvaluadoresExternos.remove(evaluador);
    }

    public void adicionarPosibleEvaluadorInterno(Investigador investigador) {
        // SI EL INVESTIGADOR INTERNO SE ENCUENTRA ASOCIADO AL GRUPO AL QUE
        // ACTUALMENTE SE ENCUENTRA
        // ASOCIADO EL PROYECTO, SI LA CONVOCATORIA ES DE GRUPOS. SI LA
        // CONVOCATORIA NO ES DE GRUPOS
        // VALIDAR DUPLICIDAD DEL INVESTIGADOR
        posiblesEvaluadoresInternos.add(investigador);
    }

    public void borrarPosibleEvaluadorInvestigadorExterno(Investigador investigador) {

        posiblesEvaluadoresInvestigadoresExternos.remove(investigador);
    }

    public void adicionarPosibleEvaluadorInvestigadorExterno(Investigador investigador) {
        // SI EL INVESTIGADOR INTERNO SE ENCUENTRA ASOCIADO AL GRUPO AL QUE
        // ACTUALMENTE SE ENCUENTRA
        // ASOCIADO EL PROYECTO, SI LA CONVOCATORIA ES DE GRUPOS. SI LA
        // CONVOCATORIA NO ES DE GRUPOS
        // VALIDAR DUPLICIDAD DEL INVESTIGADOR
        posiblesEvaluadoresInvestigadoresExternos.add(investigador);
    }

    public void borrarPosibleEvaluadorInterno(Investigador investigador) {
        posiblesEvaluadoresInternos.remove(investigador);
    }

    public void adicionarArchivo(Archivo archivo) {
        archivo.setProyecto(this);
        archivos.add(archivo);
    }

    public void adicionarActividad(Actividad actividad) {
        actividad.setProyecto(this);
        actividades.add(actividad);
    }

    public void adicionarAgenda(AgendaProyecto agendaProyecto) {
        if (agendas == null) {
            agendas = new HashSet<AgendaProyecto>();
        }
        agendaProyecto.setProyecto(this);
        agendas.add(agendaProyecto);
    }

    public void borrarActividad(Actividad actividad) {
        actividades.remove(actividad);
    }

    public void adicionarObjetivoResultado(ActividadObjetivo objetivoResultado) {
        objetivoResultado.setProyecto(this);
        objetivosResultados.add(objetivoResultado);
    }

    public void borrarObjetivoResultado(ActividadObjetivo objetivoResultado) {
        objetivosResultados.remove(objetivoResultado);
    }

    public void adicionarBibliografia(Bibliografia bibliografia) {
        bibliografia.setProyecto(this);
        bibliografias.add(bibliografia);
    }

    public void borrarBibliografia(Bibliografia bibliografia) {
        bibliografias.remove(bibliografia);
    }

    public void adicionarPalabraClave(PalabraClave palabra) {
        if (palabrasClaves == null) {
            palabrasClaves = new HashSet<PalabraClave>();
        }
        UtilPalabraClave.limpiaPalabraClave(palabra);
        palabrasClaves.add(palabra);
    }

    public void borrarPalabraClave(PalabraClave palabra) {
        palabrasClaves.remove(palabra);
    }

    public void adicionarHistorico(HistoricoEstadoProyecto hist) {
        hist.setProyecto(this);
        historico.add(hist);
    }
        
    public void adicionarHistoricoEstLega(HistoricoEstadoLegalizacion hist) {
        hist.setProyecto(this);
        historicoEstLega.add(hist);
    }

    public void adicionarFinanciacion(Financiacion financiacion) {
        if (financiaciones == null) {
            financiaciones = new HashSet<Financiacion>();
        }
        financiacion.setProyecto(this);
        financiaciones.add(financiacion);
    }

    public void borrarFinanciacion(Financiacion financiacion) {
        financiaciones.remove(financiacion);
    }

    public void adicionarLinea(LineaInvestigacion linea) {
        boolean encontro = false;
        Iterator it = lineas.iterator();
        while (it.hasNext()) {
            LineaInvestigacion l = (LineaInvestigacion) it.next();
            if (l.getId().equals(linea.getId())) {
                encontro = true;
                break;
            }
        }
        if (!encontro) {
            lineas.add(linea);
        }
    }

    public AgendaProyecto obtenerAgenda(String orden) {

        Iterator it = agendas.iterator();
        AgendaProyecto agenda = new AgendaProyecto();
        while (it.hasNext()) {
            AgendaProyecto l = (AgendaProyecto) it.next();

            if (l.getOrden().equals(orden)) {
                agenda = l;
                break;
            }

        }

        return agenda;
    }

    public void borrarLinea(LineaInvestigacion linea) {
        lineas.remove(linea);
    }

    public void borrarAgenda(AgendaConocimiento agenda) {
        agendas.remove(agenda);
    }

    public void adicionarGrupo(Grupo grupo) {
        grupos.add(grupo);
    }

    public void borrarGrupo(Grupo grupo) {
        grupos.remove(grupo);
    }

    public List getListaGrupos() {
        List listaCC = new ArrayList();
        listaCC.addAll(grupos);
        return listaCC;
    }
    
    public void adicionarLaboratorio(Laboratorio laboratorio) {
    	laboratorios.add(laboratorio);
    }

    public void borrarLaboratorio(Laboratorio laboratorio) {
    	laboratorios.remove(laboratorio);
    }
    
    public List getListaLaboratorios() {
        List listaCC = new ArrayList();
        listaCC.addAll(laboratorios);
        return listaCC;
    }
    
    public void adicionarLugarEjecucion(Dependencia dependencia) {
    	lugaresEjecucion.add(dependencia);
    }

    public void borrarLugarEjecucion(Dependencia dependencia) {
    	lugaresEjecucion.remove(dependencia);
    }
    
    public List getListaLugaresEjecucion() {
        List listaCC = new ArrayList();
        listaCC.addAll(lugaresEjecucion);
        return listaCC;
    }
    
    public void adicionarEquipoAdquisicion(ProyectoEquipoAdquisicion equipo) {
    	equiposAdquisicion.add(equipo);
    }

    public void borrarEquipoAdquisicion(ProyectoEquipoAdquisicion equipo) {
    	equiposAdquisicion.remove(equipo);
    }
    
    public List getListaEquiposAdquisicion() {
        List listaCC = new ArrayList();
        listaCC.addAll(equiposAdquisicion);
        return listaCC;
    }
    
    public void adicionarInvestigadorProyecto(InvestigadorProyecto investigadorProyecto) {
        investigadorProyecto.setProyecto(this);
        investigadoresProyecto.add(investigadorProyecto);
    }

    public void borrarInvestigadorProyecto(InvestigadorProyecto investigadorProyecto) {
        investigadoresProyecto.remove(investigadorProyecto);
    }

    public void adicionarDependencia(DependenciaAreaResponsabilidad dependencia) {
        if (dependenciasAreaResponsabilidad == null) {
            dependenciasAreaResponsabilidad = new HashSet();
        }
        dependencia.setProyecto(this);
        dependenciasAreaResponsabilidad.add(dependencia);
    }

    public void borrarDependencia(DependenciaAreaResponsabilidad dependencia) {
        dependenciasAreaResponsabilidad.remove(dependencia);
    }
    
    public void adicionarDependenciaAportante(DependenciaAportante dependencia) {
        if (dependenciasAportantes == null) {
        	dependenciasAportantes = new HashSet();
        }
        dependencia.setProyecto(this);
        dependenciasAportantes.add(dependencia);
    }

    public void borrarDependenciaAportante(DependenciaAportante dependencia) {
    	dependenciasAportantes.remove(dependencia);
    }
    
    public void adicionarProyectoPrograma(ProyectoPrograma proyectoPrograma) {
        if (proyectosPrograma == null) {
        	proyectosPrograma = new HashSet();
        }
        proyectoPrograma.setProyecto(this);
        proyectosPrograma.add(proyectoPrograma);
    }

    public void borrarProyectoPrograma(ProyectoPrograma proyectoPrograma) {
    	proyectosPrograma.remove(proyectoPrograma);
    }

    public void adicionarCiudad(Ciudad ciudad) {
        ciudades.add(ciudad);
    }

    public void borrarCiudad(Ciudad ciudad) {
        ciudades.remove(ciudad);
    }

    public void adicionarClasificacionConocimiento(ClasificacionConocimiento conocimiento) {
        this.clasificacionConocimiento.add(conocimiento);
    }

    public void borrarClasificacionConocimiento(ClasificacionConocimiento conocimiento) {
        this.clasificacionConocimiento.remove(conocimiento);
    }

    public void adicionarCreacionArtistica(CreacionArtistica creacionArt) {
        creacionArt.setProyecto(this);
        setCreacionArtistica(creacionArt);
    }

    public void adicionarEvaluadorProyecto(ProyectoEvaluador proyectoEvaluador) {
        proyectoEvaluador.setProyecto(this);
        evaluadoresProyecto.add(proyectoEvaluador);
    }

    public void borrarEvaluadorProyecto(ProyectoEvaluador proyectoEvaluador) {
        evaluadoresProyecto.remove(proyectoEvaluador);
    }

    public void adicionarEquipo(Equipo equipo) {
        equipo.setProyecto(this);
        equipos.add(equipo);
    }

    public void borrarEquipo(Equipo equipo) {
        equipos.remove(equipo);
    }

    public void adicionarProductoProyecto(ProyectoProducto productoProyecto) {
        if (productosProyecto == null) {
            productosProyecto = new HashSet();
        }
        productoProyecto.setProyecto(this);
        productosProyecto.add(productoProyecto);

    }

    public void adicionarRequisitoProyecto(ProyectoRequisito requistoProyecto) {

        requistoProyecto.setProyecto(this);
        requisitosProyecto.add(requistoProyecto);
    }

    public void adicionarCompromisoProyecto(ProyectoCompromiso compromisoProyecto) {
        compromisoProyecto.setProyecto(this);
        if (compromisosProyecto == null) {
            compromisosProyecto = new HashSet<ProyectoCompromiso>();
        }
        compromisosProyecto.add(compromisoProyecto);
    }

    public void borrarProductoProyecto(ProyectoProducto productoProyecto) {
        productosProyecto.remove(productoProyecto);
    }

    public void borrarRequisitoProyecto(ProyectoRequisito requisitoProyecto) {

        requisitosProyecto.remove(requisitoProyecto);
    }

    public void borrarCompromisoProyecto(ProyectoCompromiso compromisoProyecto) {

        compromisosProyecto.remove(compromisoProyecto);
    }

    /**
     * Sobreescritura del metodo equals() de "object", especifica para proyecto
     */
    public boolean equals(Object object) {
        if (object instanceof Proyecto) {
            Proyecto proyecto = (Proyecto) object;
            if (this.id.equals(proyecto.getId())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Sobreescritura del metodo hashCode() de "object", especifica para
     * proyecto
     */
    public int hashCode() {
        return 1;
    }

    /**
     * Cambia el estado del proyecto, agregando una entrada en el historico de
     * estados de proyecto
     * 
     * @param estado
     */
    public void cambiarEstado(String estado) {

        EstadoProyecto estadoProyectoActual = new EstadoProyecto();
        estadoProyectoActual.setId(estado);
        if (this.estadoProyecto != null) {
            // Si no hay un cambio de estado, no se actualiza el historico
            if (!this.getEstadoProyecto().getId().equals(estado)) {
                HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
                Date fechaHoy = new Date();
                hepry.setEstadoProyecto(estadoProyectoActual);
                hepry.setFecha(fechaHoy);
                hepry.setProyecto(this);
                this.adicionarHistorico(hepry);
            }
        } else {
            HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
            Date fechaHoy = new Date();
            hepry.setEstadoProyecto(estadoProyectoActual);
            hepry.setFecha(fechaHoy);
            this.adicionarHistorico(hepry);
        }
        this.setEstadoProyecto(estadoProyectoActual);
    }

    public void cambiarEstadoPersona(String estado, Persona persona) {
        cambiarEstadoPersona(estado, persona, "");
    }

    public void cambiarEstadoPersona(String estado, Persona persona, String justificacion) {

        EstadoProyecto estadoProyectoActual = new EstadoProyecto();
        estadoProyectoActual.setId(estado);
        if (this.estadoProyecto != null) {
            // Si no hay un cambio de estado, no se actualiza el historico
            if (!this.getEstadoProyecto().getId().equals(estado)) {
                HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
                Date fechaHoy = new Date();
                hepry.setEstadoProyecto(estadoProyectoActual);
                hepry.setFecha(fechaHoy);
                hepry.setJustificacion(justificacion);
                if (persona != null)
                    hepry.setResponsable(persona);
                this.adicionarHistorico(hepry);
            }
        } else {
            HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
            Date fechaHoy = new Date();
            hepry.setEstadoProyecto(estadoProyectoActual);
            hepry.setFecha(fechaHoy);
            this.adicionarHistorico(hepry);
        }
        this.setEstadoProyecto(estadoProyectoActual);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public PlanGlobalDesarrollo getPlanGlobalDesarrollo() {
        return planGlobalDesarrollo;
    }

    public void setPlanGlobalDesarrollo(PlanGlobalDesarrollo planGlobalDesarrollo) {
        this.planGlobalDesarrollo = planGlobalDesarrollo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResultados(Set<ResultadoProyecto> resultados) {
        this.resultados = resultados;
    }

    public Set<ResultadoProyecto> getResultados() {
        return resultados;
    }

    public Set getGrupos() {
        return grupos;
    }

    public void setGrupos(Set grupos) {
        this.grupos = grupos;
    }

    public Set<InvestigadorProyecto> getInvestigadoresProyecto() {
        return investigadoresProyecto;
    }

    public void setInvestigadoresProyecto(Set<InvestigadorProyecto> investigadoresProyecto) {
        this.investigadoresProyecto = investigadoresProyecto;
    }

    public String getCodigoQuipu() {
        return codigoQuipu;
    }

    public void setCodigoQuipu(String codigoQuipu) {
        this.codigoQuipu = codigoQuipu;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public TipoDuracion getTipoDuracion() {
        return tipoDuracion;
    }

    public void setTipoDuracion(TipoDuracion tipoDuracion) {
        this.tipoDuracion = tipoDuracion;
    }

    public EstadoProyecto getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public TipoInvestigacion getTipoInvestigacion() {
        return tipoInvestigacion;
    }

    public void setTipoInvestigacion(TipoInvestigacion tipoInvestigacion) {
        this.tipoInvestigacion = tipoInvestigacion;
    }

    public Set getLineas() {
        return lineas;
    }

    public void setLineas(Set lineas) {
        this.lineas = lineas;
    }

    public Set getLaboratorios() {
		return laboratorios;
	}

	public void setLaboratorios(Set laboratorios) {
		this.laboratorios = laboratorios;
	}

	public Set<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(Set<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public String getMarcoTeorico() {
        return marcoTeorico;
    }

    public void setMarcoTeorico(String marcoTeorico) {
        this.marcoTeorico = marcoTeorico;
    }

    public String getMetodologia() {
        return metodologia;
    }

    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }

    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }

    public Set<ObjetivoEspecifico> getObjetivosEspecificos() {
        return objetivosEspecificos;
    }

    public void setObjetivosEspecificos(Set<ObjetivoEspecifico> objetivosEspecificos) {
        this.objetivosEspecificos = objetivosEspecificos;
    }

	public String getPropiedadIntelectual() {
        return propiedadIntelectual;
    }

    public void setPropiedadIntelectual(String propiedadIntelectual) {
        this.propiedadIntelectual = propiedadIntelectual;
    }

    public String getConsideracionesEticas() {
        return consideracionesEticas;
    }

    public void setConsideracionesEticas(String consideracionesEticas) {
        this.consideracionesEticas = consideracionesEticas;
    }

    public String getImpactoEsperado() {
        return impactoEsperado;
    }

    public void setImpactoEsperado(String impactoEsperado) {
        this.impactoEsperado = impactoEsperado;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getOtrosAportes() {
        return otrosAportes;
    }

    public void setOtrosAportes(Long otrosAportes) {
        this.otrosAportes = otrosAportes;
    }

    public Set getPosiblesEvaluadoresExternos() {
        return posiblesEvaluadoresExternos;
    }

    public void setPosiblesEvaluadoresExternos(Set posiblesEvaluadores) {
        this.posiblesEvaluadoresExternos = posiblesEvaluadores;
    }

    public Long getValorSolicitado() {
        return valorSolicitado;
    }

    public void setValorSolicitado(Long valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    public Set<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(Set<Actividad> actividades) {
        this.actividades = actividades;
    }

    public Set<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(Set<Archivo> archivos) {
        this.archivos = archivos;
    }

    public Set getBibliografias() {
        return bibliografias;
    }

    public void setBibliografias(Set bibliografias) {
        this.bibliografias = bibliografias;
    }

    public Set<PalabraClave> getPalabrasClaves() {
        return palabrasClaves;
    }

    public void setPalabrasClaves(Set palabrasClaves) {
        this.palabrasClaves = palabrasClaves;
    }

    public Set getHistorico() {
        return historico;
    }

    public void setHistorico(Set historico) {
        this.historico = historico;
    }

    public Set<ClasificacionConocimiento> getClasificacionConocimiento() {
        return clasificacionConocimiento;
    }

    public void setClasificacionConocimiento(Set<ClasificacionConocimiento> clasificacionConocimiento) {
        this.clasificacionConocimiento = clasificacionConocimiento;
    }

    public List getListaClasificacionConocimiento() {
        List listaCC = new ArrayList();
        listaCC.addAll(clasificacionConocimiento);
        return listaCC;
    }

    public Integer getFase() {
        return fase;
    }

    public void setFase(Integer fase) {
        this.fase = fase;
    }

    public Set<Financiacion> getFinanciaciones() {
        return financiaciones;
    }

    public void setFinanciaciones(Set financiacion) {
        this.financiaciones = financiacion;
    }

    /**
     * Se asigna el investigador responsable con la lista de investigadores del
     * proyecto Advertencia: para que pueda asignar el responsable es necesario
     * que se alla cargado la lista de los investigadores de la base de datos
     */
    public Investigador getResponsable() {
        Iterator it = getInvestigadoresProyecto().iterator();
        while (it.hasNext()) {
            InvestigadorProyecto invP = (InvestigadorProyecto) it.next();
            if (invP.getTipo().getId().equals(InvestigadorProyecto.PRINCIPAL)) {
                this.responsable = invP.getInvestigador();
                break;
            }
        }
        return responsable;
    }

    /**
     * Se asigna el investigador responsable con la lista de investigadores del
     * proyecto Advertencia: para que pueda asignar el responsable es necesario
     * que se alla cargado la lista de los investigadores de la base de datos
     */
    public List<InvestigadorProyecto> getListaInvestigadorPrincipal() {
        List<InvestigadorProyecto> investigadores = new ArrayList<InvestigadorProyecto>();
        if (getInvestigadoresProyecto() != null) {
            Iterator it = getInvestigadoresProyecto().iterator();
            while (it.hasNext()) {
                InvestigadorProyecto invP = (InvestigadorProyecto) it.next();
                if (invP.getTipo().getId().equals(InvestigadorProyecto.PRINCIPAL)) {
                    investigadores.add(invP);
                    break;
                }
            }
        }
        return investigadores;
    }

    public List<ProyectoCompromiso> getListaCompromisoInformesParciales() {
        return getListaCompromiso(ProyectoInforme.TIPO_INFORME_AVANCE);
    }

    public List<ProyectoCompromiso> getListaCompromisoInformesFinales() {
        return getListaCompromiso(ProyectoInforme.TIPO_INFORME_FINAL);
    }
    
    public List<ProyectoCompromiso> getListaCompromisoObligaciones() {
        return getListaCompromiso(TipoInforme.OBLIGACIONES);
    }
        
    public List<ProyectoCompromiso> getListaCompromisoInformes() {
        List<ProyectoCompromiso> listaCompromisosInformes= new ArrayList<ProyectoCompromiso>();       	
        if (getCompromisosProyecto() != null) {
            Iterator<ProyectoCompromiso> i = getCompromisosProyecto().iterator();
            while (i.hasNext()) {
                ProyectoCompromiso proyectoCompromiso = i.next();
                if (proyectoCompromiso.getTipoInforme().getId().equals(ProyectoInforme.TIPO_INFORME_AVANCE)
                	|| proyectoCompromiso.getTipoInforme().getId().equals(ProyectoInforme.TIPO_INFORME_FINAL)) {
                	listaCompromisosInformes.add(proyectoCompromiso);
                }
            }
        }        
    	return listaCompromisosInformes;
    }

    public List<ProyectoCompromiso> getListaCompromiso(Long tipo) {
        List<ProyectoCompromiso> listaCompromisos = new ArrayList<ProyectoCompromiso>();
        if (getCompromisosProyecto() != null) {
            Iterator<ProyectoCompromiso> i = getCompromisosProyecto().iterator();
            while (i.hasNext()) {
                ProyectoCompromiso proyectoCompromiso = i.next();
                if (proyectoCompromiso.getTipoInforme().getId().equals(tipo)) {
                    listaCompromisos.add(proyectoCompromiso);
                }
            }
        }
        return listaCompromisos;
    }

    public List<ProyectoCompromiso> getListaCompromisos() {
        List<ProyectoCompromiso> listaCompromisos = new ArrayList<ProyectoCompromiso>();
        if (getCompromisosProyecto() != null) {
            listaCompromisos.addAll(getCompromisosProyecto());
        }
        return listaCompromisos;
    }

    public List<InvestigadorProyecto> getListaInvestigadoresProyectoConDatos() {
        return getListaInvestigadoresProyecto(true);
    }

    public List<InvestigadorProyecto> getListaInvestigadoresProyectoSinDatos() {
        return getListaInvestigadoresProyecto(false);
    }
    
    public List<Estudiante> getListaEstudiantesProyecto(){
    	List<Estudiante> listaEstudiantesProyecto = new ArrayList<Estudiante>();
    	List<InvestigadorProyecto> listaInvestigadoresProyectos = getListaInvestigadoresProyectoConDatos();
    	if(listaInvestigadoresProyectos!=null && !listaInvestigadoresProyectos.isEmpty()) {
    		for (int i=0; i<listaInvestigadoresProyectos.size();i++) {
    			InvestigadorProyecto ip = new InvestigadorProyecto();
    			ip = listaInvestigadoresProyectos.get(i);
    			if(ip.getTipo().getTipo().equals("A")){
    				listaEstudiantesProyecto.add(ip.convertirAEstudiante());
    			}
    		}
    		
    	}
    	return listaEstudiantesProyecto;
    }

    private List<InvestigadorProyecto> getListaInvestigadoresProyecto(boolean conDatos) {
        List<InvestigadorProyecto> listaInvestigadoresProyectos = new ArrayList<InvestigadorProyecto>();
        if (getListaInvestigadoresProyecto().size() > 0) {
            Iterator<InvestigadorProyecto> i = getListaInvestigadoresProyecto().iterator();
            while (i.hasNext()) {
                InvestigadorProyecto ip = i.next();
                if (!ip.getTipo().getId().equals(InvestigadorProyecto.PRINCIPAL)) {
                    boolean esDocumentoConDatos = true;
                    if (ip.getInvestigador().getId().getDocumento().startsWith("PEX")
                            || ip.getInvestigador().getId().getDocumento().startsWith("AD0")
                            || ip.getInvestigador().getId().getDocumento().startsWith("AL0")
                            || ip.getInvestigador().getId().getDocumento().startsWith("CT0")
                            || ip.getInvestigador().getId().getDocumento().startsWith("CNE")
                            || ip.getInvestigador().getId().getDocumento().startsWith("PCD")
                            || ip.getInvestigador().getId().getDocumento().startsWith("PSCD")
                            || ip.getInvestigador().getId().getDocumento().startsWith("POSD")
                            || ip.getInvestigador().getId().getDocumento().startsWith("ESP")
                            || ip.getInvestigador().getId().getDocumento().startsWith("EUDC")
                            || ip.getInvestigador().getId().getDocumento().startsWith("EPDC")
                            || ip.getInvestigador().getId().getDocumento().startsWith("DCDC")
                            || ip.getInvestigador().getId().getDocumento().startsWith("ESEX")
                            || ip.getInvestigador().getId().getDocumento().startsWith("ADP0")
                            || ip.getInvestigador().getId().getDocumento().startsWith("PEES")
                            || ip.getInvestigador().getId().getDocumento().startsWith("EGES")
                            || ip.getInvestigador().getId().getDocumento().startsWith("JIPO")
                            || ip.getInvestigador().getId().getDocumento().startsWith("JIEU")
                            || ip.getInvestigador().getId().getDocumento().startsWith("PEAMA")
                            || ip.getInvestigador().getId().getDocumento().startsWith("IPAS")
                            || ip.getInvestigador().getId().getDocumento().startsWith("ESMA")
                            || ip.getInvestigador().getId().getDocumento().startsWith("ESDO")
                            || ip.getInvestigador().getId().getDocumento().startsWith("IPUR")
                            || ip.getInvestigador().getId().getDocumento().startsWith("PPUR")
                            || ip.getInvestigador().getId().getDocumento().startsWith("PPEUR")
                            || ip.getInvestigador().getId().getDocumento().startsWith("EPRUR")
                            || ip.getInvestigador().getId().getDocumento().startsWith("EPOUR")
                            || ip.getInvestigador().getId().getDocumento().startsWith("EGRUR")
                            || ip.getInvestigador().getId().getDocumento().startsWith("ADMUR")
                            || ip.getInvestigador().getId().getDocumento().startsWith("ESEPO")
                            || ip.getInvestigador().getId().getDocumento().startsWith("ESEPR")
                            || ip.getInvestigador().getId().getDocumento().startsWith("EPOS0")) {
                        esDocumentoConDatos = false;
                    }
                    if (conDatos && esDocumentoConDatos) {
                        listaInvestigadoresProyectos.add(ip);
                    }
                    if (!conDatos && !esDocumentoConDatos) {
                        listaInvestigadoresProyectos.add(ip);
                    }
                }
            }
        }
        return listaInvestigadoresProyectos;
    }

    public CreacionArtistica getCreacionArtistica() {
        return creacionArtistica;
    }

    public void setCreacionArtistica(CreacionArtistica creacionArtistica) {
        this.creacionArtistica = creacionArtistica;
    }

    public Set getEvaluadoresProyecto() {
        return evaluadoresProyecto;
    }

    public void setEvaluadoresProyecto(Set evaluadoresProyecto) {
        this.evaluadoresProyecto = evaluadoresProyecto;
    }

    public Set<DependenciaAreaResponsabilidad> getDependenciasAreaResponsabilidad() {
        return dependenciasAreaResponsabilidad;
    }

    public void setDependenciasAreaResponsabilidad(Set dependenciasAreaResponsabilidad) {
        this.dependenciasAreaResponsabilidad = dependenciasAreaResponsabilidad;
    }

    public Date getFechaTentativaInicio() {

        return fechaTentativaInicio;
    }

    public void setFechaTentativaInicio(Date fechaTentativaInicio) {
        this.fechaTentativaInicio = fechaTentativaInicio;
    }

    public Set getEquipos() {
        return equipos;
    }

    public void setEquipos(Set equipos) {
        this.equipos = equipos;
    }

    public String getCodigoDib() {
        return codigoDib;
    }

    public void setCodigoDib(String codigoDib) {
        this.codigoDib = codigoDib;
    }

    public Set<ProyectoProducto> getProductosProyecto() {
        return productosProyecto;
    }

    public void setProductosProyecto(Set productosProyecto) {
        this.productosProyecto = productosProyecto;
    }

    public Set<Persona> getAsesores() {
        return asesores;
    }

    public void setAsesores(Set asesores) {
        this.asesores = asesores;
    }

    // Listas de atributos

    public List getListaPalabras() {
        List listaPalabras = new ArrayList();
        listaPalabras.addAll(palabrasClaves);
        return listaPalabras;
    }

    public List getListaPalabrasEN() {
        List listaPalabras = new ArrayList();
        if (palabrasClaves != null && palabrasClaves.size() > 0) {
            Iterator ite = palabrasClaves.iterator();

            if (ite != null) {
                while (ite.hasNext()) {
                    PalabraClave obj = (PalabraClave) ite.next();
                    if (obj.getIdioma().equals("EN")) {
                        listaPalabras.add(obj);
                    }
                }
            }

        }
        // listaPalabras.addAll(palabrasClaves);
        return listaPalabras;
    }

    public List<PalabraClave> getListaPalabrasES() {
        List listaPalabras = new ArrayList();
        if (palabrasClaves != null && palabrasClaves.size() > 0) {
            Iterator ite = palabrasClaves.iterator();

            if (ite != null) {
                while (ite.hasNext()) {
                    PalabraClave obj = (PalabraClave) ite.next();
                    if (obj.getIdioma().equals("ES")) {
                        listaPalabras.add(obj);
                    }
                }
            }

        }
        // listaPalabras.addAll(palabrasClaves);
        return listaPalabras;
    }
    
    public List<Ciudad> getListaCiudadesProyecto() {
        List listaCiudadesProyecto = new ArrayList();
        if (ciudades != null) {
            Iterator<Ciudad> i = ciudades.iterator();
            while (i.hasNext()) {
            	Ciudad proyectoCiudad = i.next();
            	listaCiudadesProyecto.add(proyectoCiudad);
            }
        }
        return listaCiudadesProyecto;
    }
    
    public List<ProyectoPrograma> getListaProyectosPrograma() {
        List listaProyectosPrograma = new ArrayList();
        if (proyectosPrograma != null) {
            Iterator<ProyectoPrograma> i = proyectosPrograma.iterator();
            while (i.hasNext()) {
            	ProyectoPrograma proyectoPrograma = i.next();
            	listaProyectosPrograma.add(proyectoPrograma);
            }
        }
        return listaProyectosPrograma;
    }
    
    public List<DependenciaAportante> getListaDependenciasAportantes() {
        List listaDependencias = new ArrayList();
        if (dependenciasAportantes != null) {
            Iterator<DependenciaAportante> i = dependenciasAportantes.iterator();
            while (i.hasNext()) {
                DependenciaAportante dependencia = i.next();
                    listaDependencias.add(dependencia);
            }
        }
        return listaDependencias;
    }

    public List<DependenciaAreaResponsabilidad> getListaDependenciasAreaResponsabilidad() {
        List listaDependencias = new ArrayList();
        if (dependenciasAreaResponsabilidad != null) {
            Iterator<DependenciaAreaResponsabilidad> i = dependenciasAreaResponsabilidad.iterator();
            while (i.hasNext()) {
                DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad = i.next();
                if (dependenciaAreaResponsabilidad.getAreaResponsabilidad() == null
                        || dependenciaAreaResponsabilidad.getAreaResponsabilidad().length() == 0) {
                    listaDependencias.add(dependenciaAreaResponsabilidad);
                }
            }
        }
        return listaDependencias;
    }

    public List<DependenciaAreaResponsabilidad> getListaSedesLegalizacion() {
        List listaDependencias = new ArrayList();
        if (dependenciasAreaResponsabilidad != null) {
            Iterator<DependenciaAreaResponsabilidad> i = dependenciasAreaResponsabilidad.iterator();
            while (i.hasNext()) {
                DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad = i.next();
                if (dependenciaAreaResponsabilidad.getAreaResponsabilidad() != null
                        && DependenciaAreaResponsabilidad.SEDE
                                .equals(dependenciaAreaResponsabilidad.getAreaResponsabilidad())) {
                    listaDependencias.add(dependenciaAreaResponsabilidad);
                }
            }
        }
        return listaDependencias;
    }

    public List<DependenciaAreaResponsabilidad> getListaFacultadesLegalizacion() {
        List listaDependencias = new ArrayList();
        if (dependenciasAreaResponsabilidad != null) {
            Iterator<DependenciaAreaResponsabilidad> i = dependenciasAreaResponsabilidad.iterator();
            while (i.hasNext()) {
                DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad = i.next();
                if (dependenciaAreaResponsabilidad.getAreaResponsabilidad() != null
                        && DependenciaAreaResponsabilidad.FACULTAD
                                .equals(dependenciaAreaResponsabilidad.getAreaResponsabilidad())) {
                    listaDependencias.add(dependenciaAreaResponsabilidad);
                }
            }
        }
        return listaDependencias;
    }

    public List<InvestigadorProyecto> getListaInvestigadoresProyecto() {
        List listaInvestigadoresProyecto = new ArrayList();
        if (investigadoresProyecto != null) {
            listaInvestigadoresProyecto.addAll(investigadoresProyecto);
        }
        return listaInvestigadoresProyecto;
    }

    public List<ObjetivoEspecifico> getListaObjetivos() {
        List listaObjetivos = new ArrayList();
        listaObjetivos.addAll(objetivosEspecificos);
        Iterator<ObjetivoEspecifico> i = objetivosEspecificos.iterator();
        Long value = 1L;
        while (i.hasNext()) {
            ObjetivoEspecifico objetivoEspecifico = i.next();
            objetivoEspecifico.setNumeroOrden(value++);
        }
        return listaObjetivos;
    }
    
    public List<MetaProyecto> getListaMetas() {
        List listaObjetivos = getListaObjetivos();
        List listaMetas = new ArrayList();
        
        for (int k = 0; k < listaObjetivos.size(); k++) {
			
	        //listaMetas = new ArrayList();
	        ObjetivoEspecifico objetivoFor = (ObjetivoEspecifico) listaObjetivos.get(k);
	        listaMetas.addAll(objetivoFor.getListaMetas());
	        Iterator<MetaProyecto> i = objetivoFor.getListaMetas().iterator();
	        Long value = 1L;
	        while (i.hasNext()) {
	        	MetaProyecto meta = i.next();
	        	meta.setNumeroOrden(value++);
	        }
	        
        }
        
        return listaMetas;
    }

    public List<ResultadoProyecto> getListaResultados() {
        List listaResultados = new ArrayList();
        listaResultados.addAll(resultados);
        Long value = 1L;
        Iterator<ResultadoProyecto> i = resultados.iterator();
        while (i.hasNext()) {
            ResultadoProyecto resultadoProyecto = i.next();
            resultadoProyecto.setNumeroOrden(value++);
        }
        return listaResultados;
    }

    //Retorna lista de actividades del proyecto
    public List getListaActividades() {
        List listaActividades = new ArrayList();
        listaActividades.addAll(actividades);
        return listaActividades;
    }
    
    //Retorna lista de actividades del proyecto asociadas a una meta especifica
    public List getListaActividadesAsociadasAMeta(MetaProyecto metaObjeto) {
      List listaActividades = new ArrayList();
        //listaActividades.addAll(actividades);
      if(metaObjeto != null)  
      {	  
        for (Actividad act : actividades) {
			if(act.getMetaObjeto().getId().equals(metaObjeto.getId()))
				listaActividades.add(act);	
		}
      } 
        return listaActividades;
    }
    
    public List getListaActividadesAsociadasAResultado(ResultadoProyecto resultadoObjeto) {
        List listaActividades = new ArrayList();
          //listaActividades.addAll(actividades);
        if(resultadoObjeto != null)  
        {	  
          for (Actividad act : actividades) {
//  			if(act.getMetaObjeto().getId().equals(resultadoObjeto.getId()))
  			if(act.getResultado().getId().equals(resultadoObjeto.getId()))
  				listaActividades.add(act);	
  		}
        } 
          return listaActividades;
      }
    
    public void recalcularNumeroOrden(MetaProyecto meta, Actividad actividad)
    {
    	List listaActividades = new ArrayList();
        //listaActividades.addAll(actividades);
	    if(meta != null)  
	    {	  
	      for (Actividad act : actividades) {
	    	  if(act.getMetaObjeto().getId().equals(meta.getId()) && act.getNumeroOrden() > actividad.getNumeroOrden())
					act.setNumeroOrden(act.getNumeroOrden() - 1);
	      }
	    } 
    }

    public List getListaBibliografias() {
        List listaBibliografias = new ArrayList();
        listaBibliografias.addAll(bibliografias);
        return listaBibliografias;
    }

    public List getListaProductosProyecto() {
        List listaProductos = new ArrayList();
        listaProductos.addAll(productosProyecto);
        return listaProductos;
    }

    public List getListaRequisitosProyecto() {

        List listaRequisitos = new ArrayList();
        listaRequisitos.addAll(requisitosProyecto);
        return listaRequisitos;
    }

    public List getListaCompromisosProyecto() {

        List listaCompromisos = new ArrayList();
        listaCompromisos.addAll(compromisosProyecto);
        return listaCompromisos;
    }

    public List getListaEvaluadoresProyecto() {
        List listaEvaluadoresProyecto = new ArrayList();
        listaEvaluadoresProyecto.addAll(evaluadoresProyecto);
        return listaEvaluadoresProyecto;
    }

    public List getListaLineas() {
        List listaLineas = new ArrayList();
        listaLineas.addAll(lineas);
        return listaLineas;
    }

    public List getListaAgendas() {
        List listaAgendas = new ArrayList();
        listaAgendas.addAll(agendas);
        return listaAgendas;
    }

    public Set getPosiblesEvaluadoresInternos() {
        return posiblesEvaluadoresInternos;
    }

    public void setPosiblesEvaluadoresInternos(Set evaluadoresInternos) {
        this.posiblesEvaluadoresInternos = evaluadoresInternos;
    }

    public List getListaEvaluadoresInternos() {
        List listaEvaluadoresInternos = new ArrayList();
        listaEvaluadoresInternos.addAll(posiblesEvaluadoresInternos);
        return listaEvaluadoresInternos;
    }

    /**
     * @return Returns the compromisosProyecto.
     */
    public Set<ProyectoCompromiso> getCompromisosProyecto() {
        return compromisosProyecto;
    }

    /**
     * @param compromisosProyecto
     *            The compromisosProyecto to set.
     */
    public void setCompromisosProyecto(Set compromisosProyecto) {
        this.compromisosProyecto = compromisosProyecto;
    }

    /**
     * @return Returns the observacionesProyecto.
     */
    public Set getObservacionesProyecto() {
        return observacionesProyecto;
    }

    /**
     * @param observacionesProyecto
     *            The observacionesProyecto to set.
     */
    public void setObservacionesProyecto(Set observacionesProyecto) {
        this.observacionesProyecto = observacionesProyecto;
    }

    /**
     * @return Returns the prorrogasProyecto.
     */
    public Set<ProyectoProrroga> getProrrogasProyecto() {
        return prorrogasProyecto;
    }

    /**
     * @param prorrogasProyecto
     *            The prorrogasProyecto to set.
     */
    public void setProrrogasProyecto(Set<ProyectoProrroga> prorrogasProyecto) {
        this.prorrogasProyecto = prorrogasProyecto;
    }

    public String getPermitirModificacion() {
        return permitirModificacion;
    }

    public void setPermitirModificacion(String permitirModificacion) {
        this.permitirModificacion = permitirModificacion;
    }

    public Set getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Set empresas) {
        this.empresas = empresas;
    }

    public List getListaEmpresas() {
        if (empresas != null && empresas.size() > 0) {
            return new Vector(getEmpresas());
        }
        return new Vector();
    }

    public String getInvolucraMaestriaDoctorado() {
        return involucraMaestriaDoctorado;
    }

    public void setInvolucraMaestriaDoctorado(String involucraMaestriaDoctorado) {
        this.involucraMaestriaDoctorado = involucraMaestriaDoctorado;
    }

    public String getLineaBaseProyecto() {
        return lineaBaseProyecto;
    }

    public void setLineaBaseProyecto(String lineaBaseProyecto) {
        this.lineaBaseProyecto = lineaBaseProyecto;
    }

    public Set<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(Set solicitudes) {
        this.solicitudes = solicitudes;
    }

    public List<Solicitud> getListaSolicitudes() {
        List<Solicitud> listaSolicitudes = new ArrayList<Solicitud>();
        if (solicitudes != null) {
            listaSolicitudes.addAll(solicitudes);
        }
        return listaSolicitudes;
    }

    public List<Solicitud> getListaSolicitudesEnviadas() {
        List<Solicitud> listaSolicitudesEnviadas = new ArrayList<Solicitud>();
        Iterator<Solicitud> i = getListaSolicitudes().iterator();
        while (i.hasNext()) {
            Solicitud solicitud = i.next();
            
            //Se carga la respuesta anterior ya que pudo haber sido modificada.
            String respuesta = solicitud.getRespuestaAnterior();
            
            //Si no existe respuesta anterior entonces se carga la que esta en el objeto.
            if(respuesta == null) {
                respuesta = solicitud.getRespuesta();
            }
            
            if (respuesta == null || (respuesta != null
                    && (respuesta.equals("A") || respuesta.equals(Solicitud.TRAMITE)
                            || respuesta.equals(Solicitud.RESUELTO)
                            || "".equals(respuesta))
                    && !solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.INFORME_AVANCE)
                    && !solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.INFORME_FINAL))) {
                listaSolicitudesEnviadas.add(solicitud);
            }
        }

        // create comparator for reverse order
        Comparator cmp = Collections.reverseOrder();

        // sort the list
        Collections.sort(listaSolicitudesEnviadas, cmp);

        return listaSolicitudesEnviadas;
    }

    public Set getPosiblesEvaluadoresInvestigadoresExternos() {
        return posiblesEvaluadoresInvestigadoresExternos;
    }

    public void setPosiblesEvaluadoresInvestigadoresExternos(Set posiblesEvaluadoresInvestigadoresExternos) {
        this.posiblesEvaluadoresInvestigadoresExternos = posiblesEvaluadoresInvestigadoresExternos;
    }

    public List getObtenerListaInvestigadoresVista() {
        List listaInvestigadores = new Vector();
        if (investigadoresProyecto != null) {
            for (Iterator i = investigadoresProyecto.iterator(); i.hasNext();) {
                InvestigadorProyecto ip = (InvestigadorProyecto) i.next();
                InvestigadorProyectoVista ipv = new InvestigadorProyectoVista();
                listaInvestigadores.add(ipv);
                ipv.setIp(ip);
                ipv.setEdicion(false);
            }
        }
        return listaInvestigadores;
    }

    public Tipos getRelacionBicentenario() {
        return relacionBicentenario;
    }

    public void setRelacionBicentenario(Tipos relacionBicentenario) {
        this.relacionBicentenario = relacionBicentenario;
    }

    public Set getRequisitosProyecto() {
        return requisitosProyecto;
    }

    public void setRequisitosProyecto(Set requisitosProyecto) {
        this.requisitosProyecto = requisitosProyecto;
    }

    public Dependencia getDependenciaPrincipal() {
        return dependenciaPrincipal;
    }

    public void setDependenciaPrincipal(Dependencia dependenciaPrincipal) {
        this.dependenciaPrincipal = dependenciaPrincipal;
    }

    public void setResponsable(Investigador responsable) {
        this.responsable = responsable;
    }

    public Proyecto getProyectoRefinanciado() {
        return proyectoRefinanciado;
    }

    public void setProyectoRefinanciado(Proyecto proyectoRefinanciado) {
        this.proyectoRefinanciado = proyectoRefinanciado;
    }

    public ProgramaNacionalPRN getProgramaNacional() {
        return programaNacional;
    }

    public void setProgramaNacional(ProgramaNacionalPRN programaNacional) {
        this.programaNacional = programaNacional;
    }

    public String getNombreConvocatoriaPadre() {
        return nombreConvocatoriaPadre;
    }

    public void setNombreConvocatoriaPadre(String nombreConvocatoriaPadre) {
        this.nombreConvocatoriaPadre = nombreConvocatoriaPadre;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public Set<AgendaProyecto> getAgendas() {
        return agendas;
    }

    public AgendaProyecto getAgendaConocimientoPrincipal() {
        if (agendas != null && agendas.size() > 0) {
            Iterator<AgendaProyecto> i = agendas.iterator();
            if (i.hasNext()) {
                return i.next();
            }
        }
        return null;
    }

    public void setAgendas(Set agendas) {
        this.agendas = agendas;
    }

    public String getPresentacionPrograma() {
        return presentacionPrograma;
    }

    public void setPresentacionPrograma(String presentacionPrograma) {
        this.presentacionPrograma = presentacionPrograma;
    }

    public String getNumeroRes() {
        return numeroRes;
    }

    public void setNumeroRes(String numeroRes) {
        this.numeroRes = numeroRes;
    }

    public Date getFechaAti() {
        return fechaAti;
    }

    public void setFechaAti(Date fechaAti) {
        this.fechaAti = fechaAti;
    }

    public ProyectoSaldoFinanciacion getProyectoSaldoFinanciacion() {
        return proyectoSaldoFinanciacion;
    }

    public void setProyectoSaldoFinanciacion(ProyectoSaldoFinanciacion proyectoSaldoFinanciacion) {
        this.proyectoSaldoFinanciacion = proyectoSaldoFinanciacion;
    }

    public String getEntidadesParticipantes() {
        return entidadesParticipantes;
    }

    public void setEntidadesParticipantes(String entidadesParticipantes) {
        this.entidadesParticipantes = entidadesParticipantes;
    }

    public String getBeneficiosExtSol() {
        return beneficiosExtSol;
    }

    public void setBeneficiosExtSol(String beneficiosExtSol) {
        this.beneficiosExtSol = beneficiosExtSol;
    }

    public String getIniciativaDe() {
        return iniciativaDe;
    }

    public void setIniciativaDe(String iniciativaDe) {
        this.iniciativaDe = iniciativaDe;
    }

    public String getIniciativa() {
        return iniciativa;
    }

    public void setIniciativa(String iniciativa) {
        this.iniciativa = iniciativa;
    }

    public String getCargaDocenteSiNo() {
        return cargaDocenteSiNo;
    }

    public void setCargaDocenteSiNo(String cargaDocenteSiNo) {
        this.cargaDocenteSiNo = cargaDocenteSiNo;
    }

    public String getTipoZona() {
        return tipoZona;
    }

    public void setTipoZona(String tipoZona) {
        this.tipoZona = tipoZona;
    }

    public String getNivelSostenibilidad() {
        return nivelSostenibilidad;
    }

    public void setNivelSostenibilidad(String nivelSostenibilidad) {
        this.nivelSostenibilidad = nivelSostenibilidad;
    }

    public String getPorqueNivelSostenibilidad() {
        return porqueNivelSostenibilidad;
    }

    public void setPorqueNivelSostenibilidad(String porqueNivelSostenibilidad) {
        this.porqueNivelSostenibilidad = porqueNivelSostenibilidad;
    }

    public String getContinuidadProyecto() {
        return continuidadProyecto;
    }

    public void setContinuidadProyecto(String continuidadProyecto) {
        this.continuidadProyecto = continuidadProyecto;
    }

    public String getPorqueContinuidadProyecto() {
        return porqueContinuidadProyecto;
    }

    public void setPorqueContinuidadProyecto(String porqueContinuidadProyecto) {
        this.porqueContinuidadProyecto = porqueContinuidadProyecto;
    }

    public String getArticulacionIniciativas() {
        return articulacionIniciativas;
    }

    public void setArticulacionIniciativas(String articulacionIniciativas) {
        this.articulacionIniciativas = articulacionIniciativas;
    }

    public String getAliados() {
        return aliados;
    }

    public void setAliados(String aliados) {
        this.aliados = aliados;
    }

    public String getObservacionesSostenibilidad() {
        return observacionesSostenibilidad;
    }

    public void setObservacionesSostenibilidad(String observacionesSostenibilidad) {
        this.observacionesSostenibilidad = observacionesSostenibilidad;
    }

    public String getSoportesUrl() {
        return soportesUrl;
    }

    public void setSoportesUrl(String soportesUrl) {
        this.soportesUrl = soportesUrl;
    }

    public String getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(String transferencia) {
        this.transferencia = transferencia;
    }

    public String getViabilidad() {
        return viabilidad;
    }

    public void setViabilidad(String viabilidad) {
        this.viabilidad = viabilidad;
    }

    public String getApropiacion() {
        return apropiacion;
    }

    public void setApropiacion(String apropiacion) {
        this.apropiacion = apropiacion;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public String getRolUniversidad() {
        return rolUniversidad;
    }

    public void setRolUniversidad(String rolUniversidad) {
        this.rolUniversidad = rolUniversidad;
    }

    public String getMecanismoParticipacion() {
        return mecanismoParticipacion;
    }

    public void setMecanismoParticipacion(String mecanismoParticipacion) {
        this.mecanismoParticipacion = mecanismoParticipacion;
    }

    public String getObjetivoSocioeconomico() {
        return objetivoSocioeconomico;
    }

    public void setObjetivoSocioeconomico(String objetivoSocioeconomico) {
        this.objetivoSocioeconomico = objetivoSocioeconomico;
    }

    public Integer getDuraciona() {
        return duraciona;
    }

    public void setDuraciona(Integer duraciona) {
        this.duraciona = duraciona;
    }

    public Long getValorPersonalTotal() {
        if (this.valorPersonalTotal != null) {
            return valorPersonalTotal;
        }
        return 0L;
    }

    public void setValorPersonalTotal(Long valorPersonalTotal) {
        this.valorPersonalTotal = valorPersonalTotal;
    }

    public Integer getDuracions() {
        return duracions;
    }

    public void setDuracions(Integer duracions) {
        this.duracions = duracions;
    }

    public Integer getDuraciond() {
        return duraciond;
    }

    public void setDuraciond(Integer duraciond) {
        this.duraciond = duraciond;
    }

    public Integer getDuracionh() {
        return duracionh;
    }

    public void setDuracionh(Integer duracionh) {
        this.duracionh = duracionh;
    }

    public Long getSemanasFormulacion() {
        return semanasFormulacion;
    }

    public void setSemanasFormulacion(Long semanasFormulacion) {
        this.semanasFormulacion = semanasFormulacion;
    }

    public Long getHorasSemanaFormulacion() {
        return horasSemanaFormulacion;
    }

    public void setHorasSemanaFormulacion(Long horasSemanaFormulacion) {
        this.horasSemanaFormulacion = horasSemanaFormulacion;
    }

    public VAsignaturasSIA getAsignaturaSIA() {
        return asignaturaSIA;
    }

    public void setAsignaturaSIA(VAsignaturasSIA asignaturaSIA) {
        this.asignaturaSIA = asignaturaSIA;
    }

    public Set<AreaTematica> getAreasTematicas() {
        return areasTematicas;
    }

    public void setAreasTematicas(Set<AreaTematica> areasTematicas) {
        this.areasTematicas = areasTematicas;
    }

    public List<AreaTematica> getAreasTematicasNivel(Long nivel) {
        List<AreaTematica> areasTematicasLista = new ArrayList<AreaTematica>();
        if (areasTematicasLista != null) {
            Iterator<AreaTematica> i = this.areasTematicas.iterator();
            while (i.hasNext()) {
                AreaTematica areaTematica = i.next();
                if (areaTematica.getTipo().equals(nivel)) {
                    areasTematicasLista.add(areaTematica);
                }
            }
        }
        return areasTematicasLista;
    }

    public void setAreaPrimaria(DominioDetalle dominioDetalle) {
        List<AreaTematica> areasTematica = getAreasTematicasNivel(1L);
        if (dominioDetalle != null) {
            if (areasTematica.size() > 0) {
                AreaTematica areaTematicaAntigua = areasTematica.get(0);
                areaTematicaAntigua.setProyectoAreaTematica(dominioDetalle);
            } else {
                AreaTematica areaTematica = new AreaTematica();
                areaTematica.setProyecto(this);
                areaTematica.setTipo(1L);
                areaTematica.setProyectoAreaTematica(dominioDetalle);
                areasTematicas.add(areaTematica);
            }
        } else if (areasTematica.size() > 0) {
            AreaTematica areaTematicaAntigua = areasTematica.get(0);
            areasTematicas.remove(areaTematicaAntigua);
        }
    }

    public void adicionarAreaTematica(AreaTematica areaTematica) {
        areaTematica.setProyecto(this);
        areasTematicas.add(areaTematica);
    }

    public void eliminarAreaTematica(AreaTematica areaTematica) {
        areasTematicas.remove(areaTematica);
    }
    
    public List<ValoresListasProyecto> getObjetivoDesarrolloSostenibleTipo(String tipo) {
        List<ValoresListasProyecto> objetivosDesarrolloSostenibleLista = new ArrayList<ValoresListasProyecto>();
        if (objetivosDesarrolloSostenibleLista != null) {
            Iterator<ValoresListasProyecto> i = this.objetivosDesarrolloSostenible.iterator();
            while (i.hasNext()) {
            	ValoresListasProyecto objetivoDesarrolloSostenible = i.next();
                if (objetivoDesarrolloSostenible.getDescripcion().equals(tipo)) {
                	objetivosDesarrolloSostenibleLista.add(objetivoDesarrolloSostenible);
                }
            }
        }
        return objetivosDesarrolloSostenibleLista;
    }
    
    public void setObjetivoDesarrolloSosteniblePrincipal(DominioDetalle dominioDetalle) {
        List<ValoresListasProyecto> objetivosDesarrolloSostenibleLista = getObjetivoDesarrolloSostenibleTipo("OBJETIVO_DESARROLLO_SOSTENIBLE_PRINCIPAL");
        if (dominioDetalle != null) {
            if (objetivosDesarrolloSostenibleLista.size() > 0) {
            	ValoresListasProyecto objetivoDesarrolloSostenibleAntiguo = objetivosDesarrolloSostenibleLista.get(0);
            	objetivoDesarrolloSostenibleAntiguo.setValor(dominioDetalle.getIdentificador().getTipo());
            	objetivoDesarrolloSostenibleAntiguo.setNombreValor(dominioDetalle.getDescripcion());
            } else {
            	ValoresListasProyecto objetivoDesarrolloSostenible = new ValoresListasProyecto();
            	objetivoDesarrolloSostenible.setProyecto(this);
            	objetivoDesarrolloSostenible.setTipo(DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE);
            	objetivoDesarrolloSostenible.setValor(dominioDetalle.getIdentificador().getTipo());
            	objetivoDesarrolloSostenible.setNombreValor(dominioDetalle.getDescripcion());
            	objetivoDesarrolloSostenible.setDescripcion("OBJETIVO_DESARROLLO_SOSTENIBLE_PRINCIPAL");
            	objetivosDesarrolloSostenible.add(objetivoDesarrolloSostenible);
            }
        } else if (objetivosDesarrolloSostenibleLista.size() > 0) {
        	ValoresListasProyecto objetivoDesarrolloSostenibleAntiguo = objetivosDesarrolloSostenibleLista.get(0);
            objetivosDesarrolloSostenible.remove(objetivoDesarrolloSostenibleAntiguo);
        }
    }

    public void adicionarObjetivoDesarrolloSostenible(ValoresListasProyecto objetivoDesarrolloSostenible) {
    	objetivoDesarrolloSostenible.setProyecto(this);
    	objetivosDesarrolloSostenible.add(objetivoDesarrolloSostenible);
    }

    public void eliminarObjetivoDesarrolloSostenible(ValoresListasProyecto objetivoDesarrolloSostenible) {
    	objetivosDesarrolloSostenible.remove(objetivoDesarrolloSostenible);
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getModalidadEcp() {
        return modalidadEcp;
    }

    public void setModalidadEcp(String modalidadEcp) {
        this.modalidadEcp = modalidadEcp;
    }

    public String getClaseEvento() {
        return claseEvento;
    }

    public void setClaseEvento(String claseEvento) {
        this.claseEvento = claseEvento;
    }

    public String getClaseEventoExt() {
        return claseEventoExt;
    }

    public void setClaseEventoExt(String claseEventoExt) {
        this.claseEventoExt = claseEventoExt;
    }

    public String getOtroClaseEvento() {
        return otroClaseEvento;
    }

    public void setOtroClaseEvento(String otroClaseEvento) {
        this.otroClaseEvento = otroClaseEvento;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    public String getEjeTematico() {
        return ejeTematico;
    }

    public void setEjeTematico(String ejeTematico) {
        this.ejeTematico = ejeTematico;
    }

    public String getCostoPersona() {
        return costoPersona;
    }

    public void setCostoPersona(String costoPersona) {
        this.costoPersona = costoPersona;
    }

    public String getMaxAsistentes() {
        return maxAsistentes;
    }

    public void setMaxAsistentes(String maxAsistentes) {
        this.maxAsistentes = maxAsistentes;
    }

    public Integer getSesiones() {
        return sesiones;
    }

    public void setSesiones(Integer sesiones) {
        this.sesiones = sesiones;
    }

    public Float getHorasSesiones() {
        return horasSesiones;
    }

    public void setHorasSesiones(Float horasSesiones) {
        this.horasSesiones = horasSesiones;
    }

    public String getDiaSesion() {
        return diaSesion;
    }

    public void setDiaSesion(String diaSesion) {
        this.diaSesion = diaSesion;
    }

    public String getHoraSesion() {
        return horaSesion;
    }

    public void setHoraSesion(String horaSesion) {
        this.horaSesion = horaSesion;
    }

    public String getTipoEspacio() {
        return tipoEspacio;
    }

    public void setTipoEspacio(String tipoEspacio) {
        this.tipoEspacio = tipoEspacio;
    }

    public String getMonitores() {
        return monitores;
    }

    public void setMonitores(String monitores) {
        this.monitores = monitores;
    }

    public Integer getNroMonitores() {
        return nroMonitores;
    }

    public void setNroMonitores(Integer nroMonitores) {
        this.nroMonitores = nroMonitores;
    }

    public String getLogistica() {
        return logistica;
    }

    public void setLogistica(String logistica) {
        this.logistica = logistica;
    }

    public String getOtraLogistica() {
        return otraLogistica;
    }

    public void setOtraLogistica(String otraLogistica) {
        this.otraLogistica = otraLogistica;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    public String getOtroServicio() {
        return otroServicio;
    }

    public void setOtroServicio(String otroServicio) {
        this.otroServicio = otroServicio;
    }

    public String getDescripcionLogistica() {
        return descripcionLogistica;
    }

    public void setDescripcionLogistica(String descripcionLogistica) {
        this.descripcionLogistica = descripcionLogistica;
    }

    public Long getConferencistas() {
        return conferencistas;
    }

    public void setConferencistas(Long conferencistas) {
        this.conferencistas = conferencistas;
    }

    public Sede getSedeEjecucion() {
        return sedeEjecucion;
    }

    public void setSedeEjecucion(Sede sedeEjecucion) {
        this.sedeEjecucion = sedeEjecucion;
    }

    public Dependencia getFacultadEjecucion() {
        return facultadEjecucion;
    }

    public void setFacultadEjecucion(Dependencia facultadEjecucion) {
        this.facultadEjecucion = facultadEjecucion;
    }

    public Integer getDuracionA() {
        return duracionA;
    }

    public void setDuracionA(Integer duracionA) {
        this.duracionA = duracionA;
    }

    public Boolean getNaDuracion() {
        return naDuracion;
    }

    public void setNaDuracion(Boolean naDuracion) {
        this.naDuracion = naDuracion;
    }

    public Boolean getNaDuracionF() {
        return naDuracionF;
    }

    public void setNaDuracionF(Boolean naDuracionF) {
        this.naDuracionF = naDuracionF;
    }

    public Set getHorarioCursoECP() {
        return horarioCursoECP;
    }

    public void setHorarioCursoECP(Set horarioCursoECP) {
        this.horarioCursoECP = horarioCursoECP;
    }

    public Set getSesionesECP() {
        return sesionesECP;
    }

    public void setSesionesECP(Set sesionesECP) {
        this.sesionesECP = sesionesECP;
    }

    public Long getCostoFacultad() {
        return costoFacultad;
    }

    public void setCostoFacultad(Long costoFacultad) {
        this.costoFacultad = costoFacultad;
    }

    public Long getCostoSede() {
        return costoSede;
    }

    public void setCostoSede(Long costoSede) {
        this.costoSede = costoSede;
    }

    public Long getCostoNacional() {
        return costoNacional;
    }

    public void setCostoNacional(Long costoNacional) {
        this.costoNacional = costoNacional;
    }

    public Long getFondoExtSol() {
        return fondoExtSol;
    }

    public void setFondoExtSol(Long fondoExtSol) {
        this.fondoExtSol = fondoExtSol;
    }

    public Long getFondoRiesgosUN() {
        return fondoRiesgosUN;
    }

    public void setFondoRiesgosUN(Long fondoRiesgosUN) {
        this.fondoRiesgosUN = fondoRiesgosUN;
    }

    public Long getFondoEspecialFac() {
        return fondoEspecialFac;
    }

    public void setFondoEspecialFac(Long fondoEspecialFac) {
        this.fondoEspecialFac = fondoEspecialFac;
    }

    public Long getFondoInvestigacUN() {
        return fondoInvestigacUN;
    }

    public void setFondoInvestigacUN(Long fondoInvestigacUN) {
        this.fondoInvestigacUN = fondoInvestigacUN;
    }

    public Long getFondoEspecialDirA() {
        return fondoEspecialDirA;
    }

    public void setFondoEspecialDirA(Long fondoEspecialDirA) {
        this.fondoEspecialDirA = fondoEspecialDirA;
    }

    public Long getDirNacionalExt() {
        return dirNacionalExt;
    }

    public void setDirNacionalExt(Long dirNacionalExt) {
        this.dirNacionalExt = dirNacionalExt;
    }

    public Long getDirExtSede() {
        return dirExtSede;
    }

    public void setDirExtSede(Long dirExtSede) {
        this.dirExtSede = dirExtSede;
    }

    public String getRei1() {
        return rei1;
    }

    public void setRei1(String rei1) {
        this.rei1 = rei1;
    }

    public String getRei2() {
        return rei2;
    }

    public void setRei2(String rei2) {
        this.rei2 = rei2;
    }

    public String getRei3() {
        return rei3;
    }

    public void setRei3(String rei3) {
        this.rei3 = rei3;
    }

    public String getRei4() {
        return rei4;
    }

    public void setRei4(String rei4) {
        this.rei4 = rei4;
    }

    public String getRei5() {
        return rei5;
    }

    public void setRei5(String rei5) {
        this.rei5 = rei5;
    }

    public String getRei6() {
        return rei6;
    }

    public void setRei6(String rei6) {
        this.rei6 = rei6;
    }

    public String getRei7() {
        return rei7;
    }

    public void setRei7(String rei7) {
        this.rei7 = rei7;
    }

    public String getRen1() {
        return ren1;
    }

    public void setRen1(String ren1) {
        this.ren1 = ren1;
    }

    public String getRen2() {
        return ren2;
    }

    public void setRen2(String ren2) {
        this.ren2 = ren2;
    }

    public String getRen3() {
        return ren3;
    }

    public void setRen3(String ren3) {
        this.ren3 = ren3;
    }

    public String getRen4() {
        return ren4;
    }

    public void setRen4(String ren4) {
        this.ren4 = ren4;
    }

    public String getRen5() {
        return ren5;
    }

    public void setRen5(String ren5) {
        this.ren5 = ren5;
    }

    public String getRen6() {
        return ren6;
    }

    public void setRen6(String ren6) {
        this.ren6 = ren6;
    }

    public String getRen7() {
        return ren7;
    }

    public void setRen7(String ren7) {
        this.ren7 = ren7;
    }

    public String getAporteEconomico() {
        return aporteEconomico;
    }

    public void setAporteEconomico(String aporteEconomico) {
        this.aporteEconomico = aporteEconomico;
    }

    public String getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(String asistentes) {
        this.asistentes = asistentes;
    }

    public String getConferencistasNacExt() {
        return conferencistasNacExt;
    }

    public void setConferencistasNacExt(String conferencistasNacExt) {
        this.conferencistasNacExt = conferencistasNacExt;
    }

    public String getConferencistasNacVinc() {
        return conferencistasNacVinc;
    }

    public void setConferencistasNacVinc(String conferencistasNacVinc) {
        this.conferencistasNacVinc = conferencistasNacVinc;
    }

    public String getConferencistasInternac() {
        return conferencistasInternac;
    }

    public void setConferencistasInternac(String conferencistasInternac) {
        this.conferencistasInternac = conferencistasInternac;
    }

    public String getTipoParticipacion() {
        return tipoParticipacion;
    }

    public void setTipoParticipacion(String tipoParticipacion) {
        this.tipoParticipacion = tipoParticipacion;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public String getTipoActividadPpal() {
        return tipoActividadPpal;
    }

    public void setTipoActividadPpal(String tipoActividadPpal) {
        this.tipoActividadPpal = tipoActividadPpal;
    }

    public String getDuracionTipo() {
        return duracionTipo;
    }

    public void setDuracionTipo(String duracionTipo) {
        this.duracionTipo = duracionTipo;
    }

    public Integer getCodLineaAccion() {
        return codLineaAccion;
    }

    public void setCodLineaAccion(Integer codLineaAccion) {
        this.codLineaAccion = codLineaAccion;
    }

    public Integer getCodPrograma() {
        return codPrograma;
    }

    public void setCodPrograma(Integer codPrograma) {
        this.codPrograma = codPrograma;
    }

    public void setOpcionesPermisoMarco(String opcionesPermisoMarco) {
        this.opcionesPermisoMarco = opcionesPermisoMarco;
    }

    public String getOpcionesPermisoMarco() {
        return opcionesPermisoMarco;
    }

    public ArrayList<Boolean> getOpcionesMarcoBoolean(int numero) {
        ArrayList opciones = new ArrayList<Boolean>();
        for (int j = 0; j < numero; j++) {
            boolean opcion = new Boolean(false);
            opcion = getOpcionMarco(j);
            opciones.add(opcion);
        }
        return opciones;
    }

    public boolean getOpcionMarco(int posicion) {
        if (opcionesPermisoMarco != null && opcionesPermisoMarco.length() > posicion
                && opcionesPermisoMarco.length() > 0) {
            char[] opcionesChar = opcionesPermisoMarco.toCharArray();
            if (opcionesChar != null && opcionesChar.length > 0 && opcionesChar.length > posicion) {
                if (opcionesChar[posicion] == 'S') {
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    public void setOpcionMarco(int posicion, Boolean valor) {
        if (opcionesPermisoMarco != null && opcionesPermisoMarco.length() > posicion
                && opcionesPermisoMarco.length() > 0) {
            char[] opcionesChar = opcionesPermisoMarco.toCharArray();
            if (opcionesChar != null && opcionesChar.length > 0 && opcionesChar.length > posicion) {
                if (valor) {
                    opcionesChar[posicion] = 'S';
                } else
                    opcionesChar[posicion] = 'N';
            }
            opcionesPermisoMarco = new String(opcionesChar);
        }
    }

    public void setAmbitoGeneralPM(Integer ambitoGeneralPM) {
        this.ambitoGeneralPM = ambitoGeneralPM;
    }

    public Integer getAmbitoGeneralPM() {
        return ambitoGeneralPM;
    }

    public void setColeccionBiologicaPM(Integer coleccionBiologicaPM) {
        this.coleccionBiologicaPM = coleccionBiologicaPM;
    }

    public Integer getColeccionBiologicaPM() {
        return coleccionBiologicaPM;
    }

    public void setCateogoriasTaxonomicasPM(String cateogoriasTaxonomicasPM) {
        this.cateogoriasTaxonomicasPM = cateogoriasTaxonomicasPM;
    }

    public String getCateogoriasTaxonomicasPM() {
        return cateogoriasTaxonomicasPM;
    }

    public void setTipoActividadInvestigacionPM(Integer tipoActividadInvestigacionPM) {
        this.tipoActividadInvestigacionPM = tipoActividadInvestigacionPM;
    }

    public Integer getTipoActividadInvestigacionPM() {
        return tipoActividadInvestigacionPM;
    }

    public Integer getAreaGeograficaPM() {
        return areaGeograficaPM;
    }

    public void setAreaGeograficaPM(Integer areaGeograficaPM) {
        this.areaGeograficaPM = areaGeograficaPM;
    }

    public String getJurisdiccionPM() {
        return jurisdiccionPM;
    }

    public void setJurisdiccionPM(String jurisdiccionPM) {
        this.jurisdiccionPM = jurisdiccionPM;
    }

    public Set getDescuentosECP() {
        return DescuentosECP;
    }

    public void setDescuentosECP(Set descuentosECP) {
        DescuentosECP = descuentosECP;
    }

    public Long getNumeroGrupos() {
        return numeroGrupos;
    }

    public void setNumeroGrupos(Long numeroGrupos) {
        this.numeroGrupos = numeroGrupos;
    }

    public String getProgramaAcademico() {
        return programaAcademico;
    }

    public void setProgramaAcademico(String programaAcademico) {
        this.programaAcademico = programaAcademico;
    }

    public String getTipoActividadECP() {
        return tipoActividadECP;
    }

    public void setTipoActividadECP(String tipoActividadECP) {
        this.tipoActividadECP = tipoActividadECP;
    }

    public String getSubTipoActividadECP() {
        return subTipoActividadECP;
    }

    public void setSubTipoActividadECP(String subTipoActividadECP) {
        this.subTipoActividadECP = subTipoActividadECP;
    }

    public Long getValorTotalCostosDirectos() {
        return valorTotalCostosDirectos;
    }

    public void setValorTotalCostosDirectos(Long valorTotalCostosDirectos) {
        this.valorTotalCostosDirectos = valorTotalCostosDirectos;
    }

    public Long getValorTotalCostosIndirectos() {
        return valorTotalCostosIndirectos;
    }

    public void setValorTotalCostosIndirectos(Long valorTotalCostosIndirectos) {
        this.valorTotalCostosIndirectos = valorTotalCostosIndirectos;
    }

    public Long getValorTotalTransferencias() {
        return valorTotalTransferencias;
    }

    public void setValorTotalTransferencias(Long valorTotalTransferencias) {
        this.valorTotalTransferencias = valorTotalTransferencias;
    }

    public Double getPorcentajeFacultad() {
        return porcentajeFacultad;
    }

    public void setPorcentajeFacultad(Double porcentajeFacultad) {
        this.porcentajeFacultad = porcentajeFacultad;
    }

    public Double getPorcentajeCostosIndirectos() {
        return porcentajeCostosIndirectos;
    }

    public void setPorcentajeCostosIndirectos(Double porcentajeCostosIndirectos) {
        this.porcentajeCostosIndirectos = porcentajeCostosIndirectos;
    }

    public String getTituloVolumenISBN() {
        return tituloVolumenISBN;
    }

    public void setTituloVolumenISBN(String tituloVolumenISBN) {
        this.tituloVolumenISBN = tituloVolumenISBN;
    }

    public String getTituloObraCompletaISBN() {
        return tituloObraCompletaISBN;
    }

    public void setTituloObraCompletaISBN(String tituloObraCompletaISBN) {
        this.tituloObraCompletaISBN = tituloObraCompletaISBN;
    }

    public String getMateriaISBN() {
        return materiaISBN;
    }

    public void setMateriaISBN(String materiaISBN) {
        this.materiaISBN = materiaISBN;
    }

    public String getTipoContenidoISBN() {
        return tipoContenidoISBN;
    }

    public void setTipoContenidoISBN(String tipoContenidoISBN) {
        this.tipoContenidoISBN = tipoContenidoISBN;
    }

    public String getNombreColeccionISBN() {
        return nombreColeccionISBN;
    }

    public void setNombreColeccionISBN(String nombreColeccionISBN) {
        this.nombreColeccionISBN = nombreColeccionISBN;
    }

    public Long getNumeroColeccionISBN() {
        return numeroColeccionISBN;
    }

    public void setNumeroColeccionISBN(Long numeroColeccionISBN) {
        this.numeroColeccionISBN = numeroColeccionISBN;
    }

    public String getSerieISBN() {
        return serieISBN;
    }

    public void setSerieISBN(String serieISBN) {
        this.serieISBN = serieISBN;
    }

    public String getIdiomaPrincipalISBN() {
        return idiomaPrincipalISBN;
    }

    public void setIdiomaPrincipalISBN(String idiomaPrincipalISBN) {
        this.idiomaPrincipalISBN = idiomaPrincipalISBN;
    }

    public String getEsTraduccionISBN() {
        return esTraduccionISBN;
    }

    public void setEsTraduccionISBN(String esTraduccionISBN) {
        this.esTraduccionISBN = esTraduccionISBN;
    }

    public String getIdiomaOriginalISBN() {
        return idiomaOriginalISBN;
    }

    public void setIdiomaOriginalISBN(String idiomaOriginalISBN) {
        this.idiomaOriginalISBN = idiomaOriginalISBN;
    }

    public String getIdiomaDestinoISBN() {
        return idiomaDestinoISBN;
    }

    public void setIdiomaDestinoISBN(String idiomaDestinoISBN) {
        this.idiomaDestinoISBN = idiomaDestinoISBN;
    }

    public String getTituloIdiomaOriginalISBN() {
        return tituloIdiomaOriginalISBN;
    }

    public void setTituloIdiomaOriginalISBN(String tituloIdiomaOriginalISBN) {
        this.tituloIdiomaOriginalISBN = tituloIdiomaOriginalISBN;
    }

    public Long getNumeroEdicionISBN() {
        return numeroEdicionISBN;
    }

    public void setNumeroEdicionISBN(Long numeroEdicionISBN) {
        this.numeroEdicionISBN = numeroEdicionISBN;
    }

    public String getCiudadEdicionISBN() {
        return ciudadEdicionISBN;
    }

    public void setCiudadEdicionISBN(String ciudadEdicionISBN) {
        this.ciudadEdicionISBN = ciudadEdicionISBN;
    }

    public String getDepartamentoEdicionISBN() {
        return departamentoEdicionISBN;
    }

    public void setDepartamentoEdicionISBN(String departamentoEdicionISBN) {
        this.departamentoEdicionISBN = departamentoEdicionISBN;
    }

    public Date getFechaAparicionISBN() {
        return fechaAparicionISBN;
    }

    public void setFechaAparicionISBN(Date fechaAparicionISBN) {
        this.fechaAparicionISBN = fechaAparicionISBN;
    }

    public String getEsCoedicionISBN() {
        return esCoedicionISBN;
    }

    public void setEsCoedicionISBN(String esCoedicionISBN) {
        this.esCoedicionISBN = esCoedicionISBN;
    }

    public String getCoeditorISBN() {
        return coeditorISBN;
    }

    public void setCoeditorISBN(String coeditorISBN) {
        this.coeditorISBN = coeditorISBN;
    }

    public String getEsComercializableISBN() {
        return esComercializableISBN;
    }

    public void setEsComercializableISBN(String esComercializableISBN) {
        this.esComercializableISBN = esComercializableISBN;
    }

    public Long getNumEjemplaresNacionalISBN() {
        return numEjemplaresNacionalISBN;
    }

    public void setNumEjemplaresNacionalISBN(Long numEjemplaresNacionalISBN) {
        this.numEjemplaresNacionalISBN = numEjemplaresNacionalISBN;
    }

    public Long getPrecioCOPISBN() {
        return precioCOPISBN;
    }

    public void setPrecioCOPISBN(Long precioCOPISBN) {
        this.precioCOPISBN = precioCOPISBN;
    }

    public Long getNumEjemplaresExternosISBN() {
        return numEjemplaresExternosISBN;
    }

    public void setNumEjemplaresExternosISBN(Long numEjemplaresExternosISBN) {
        this.numEjemplaresExternosISBN = numEjemplaresExternosISBN;
    }

    public Long getPrecioUSDISBN() {
        return precioUSDISBN;
    }

    public void setPrecioUSDISBN(Long precioUSDISBN) {
        this.precioUSDISBN = precioUSDISBN;
    }

    public Long getOfertaTotalISBN() {
        return ofertaTotalISBN;
    }

    public void setOfertaTotalISBN(Long ofertaTotalISBN) {
        this.ofertaTotalISBN = ofertaTotalISBN;
    }

    public String getRazonesNoComercializableISBN() {
        return razonesNoComercializableISBN;
    }

    public void setRazonesNoComercializableISBN(String razonesNoComercializableISBN) {
        this.razonesNoComercializableISBN = razonesNoComercializableISBN;
    }

    public String getTipoSoporteISBN() {
        return tipoSoporteISBN;
    }

    public void setTipoSoporteISBN(String tipoSoporteISBN) {
        this.tipoSoporteISBN = tipoSoporteISBN;
    }

    public String getDescripcionFisicaISBN() {
        return descripcionFisicaISBN;
    }

    public void setDescripcionFisicaISBN(String descripcionFisicaISBN) {
        this.descripcionFisicaISBN = descripcionFisicaISBN;
    }

    public String getTipoEncuadernacionISBN() {
        return tipoEncuadernacionISBN;
    }

    public void setTipoEncuadernacionISBN(String tipoEncuadernacionISBN) {
        this.tipoEncuadernacionISBN = tipoEncuadernacionISBN;
    }

    public String getTipoPapelISBN() {
        return tipoPapelISBN;
    }

    public void setTipoPapelISBN(String tipoPapelISBN) {
        this.tipoPapelISBN = tipoPapelISBN;
    }

    public String getGramajeISBN() {
        return gramajeISBN;
    }

    public void setGramajeISBN(String gramajeISBN) {
        this.gramajeISBN = gramajeISBN;
    }

    public String getTipoImpresionISBN() {
        return tipoImpresionISBN;
    }

    public void setTipoImpresionISBN(String tipoImpresionISBN) {
        this.tipoImpresionISBN = tipoImpresionISBN;
    }

    public Long getNumPaginasISBN() {
        return numPaginasISBN;
    }

    public void setNumPaginasISBN(Long numPaginasISBN) {
        this.numPaginasISBN = numPaginasISBN;
    }

    public String getNumTintasISBN() {
        return numTintasISBN;
    }

    public void setNumTintasISBN(String numTintasISBN) {
        this.numTintasISBN = numTintasISBN;
    }

    public Long getAnchoISBN() {
        return anchoISBN;
    }

    public void setAnchoISBN(Long anchoISBN) {
        this.anchoISBN = anchoISBN;
    }

    public Long getAltoISBN() {
        return altoISBN;
    }

    public void setAltoISBN(Long altoISBN) {
        this.altoISBN = altoISBN;
    }

    public String getMedioElectronicoISBN() {
        return medioElectronicoISBN;
    }

    public void setMedioElectronicoISBN(String medioElectronicoISBN) {
        this.medioElectronicoISBN = medioElectronicoISBN;
    }

    public String getFormatoISBN() {
        return formatoISBN;
    }

    public void setFormatoISBN(String formatoISBN) {
        this.formatoISBN = formatoISBN;
    }

    public Long getTamañoISBN() {
        return tamañoISBN;
    }

    public void setTamañoISBN(Long tamañoISBN) {
        this.tamañoISBN = tamañoISBN;
    }

    public String getUnidadMedidaTamañoISBN() {
        return unidadMedidaTamañoISBN;
    }

    public void setUnidadMedidaTamañoISBN(String unidadMedidaTamañoISBN) {
        this.unidadMedidaTamañoISBN = unidadMedidaTamañoISBN;
    }

    public String getExoneraInd() {
        return exoneraInd;
    }

    public void setExoneraInd(String exoneraInd) {
        this.exoneraInd = exoneraInd;
    }

    public String getExoneraTra() {
        return exoneraTra;
    }

    public void setExoneraTra(String exoneraTra) {
        this.exoneraTra = exoneraTra;
    }

    public Double getPorcentajeTransferencias() {
        return porcentajeTransferencias;
    }

    public void setPorcentajeTransferencias(Double porcentajeTransferencias) {
        this.porcentajeTransferencias = porcentajeTransferencias;
    }

    public void setCreadorId(String creadorId) {
        this.creadorId = creadorId;
    }

    public String getCreadorId() {
        return creadorId;
    }

    public void setCreadorDocumento(String creadorDocumento) {
        this.creadorDocumento = creadorDocumento;
    }

    public String getCreadorDocumento() {
        return creadorDocumento;
    }

    public String getClaseActividadECP() {
        return claseActividadECP;
    }

    public void setClaseActividadECP(String claseActividadECP) {
        this.claseActividadECP = claseActividadECP;
    }

    public void setCorte(Long corte) {
        this.corte = corte;
    }

    public Long getCorte() {
        return corte;
    }

    public String getServiciosAcademicos() {
        return serviciosAcademicos;
    }

    public void setServiciosAcademicos(String serviciosAcademicos) {
        this.serviciosAcademicos = serviciosAcademicos;
    }

    public String getJustificacionExoneracionCostosInd() {
        return justificacionExoneracionCostosInd;
    }

    public void setJustificacionExoneracionCostosInd(String justificacionExoneracionCostosInd) {
        this.justificacionExoneracionCostosInd = justificacionExoneracionCostosInd;
    }

    public String getJustificacionExoneracionTransferencias() {
        return justificacionExoneracionTransferencias;
    }

    public void setJustificacionExoneracionTransferencias(String justificacionExoneracionTransferencias) {
        this.justificacionExoneracionTransferencias = justificacionExoneracionTransferencias;
    }

    public Long getContrapartidaEfectivo() {
        if (this.contrapartidaEfectivo != null) {
            return contrapartidaEfectivo;
        }
        return 0L;
    }

    public void setContrapartidaEfectivo(Long contrapartidaEfectivo) {
        this.contrapartidaEfectivo = contrapartidaEfectivo;
    }

    public Long getContrapartidaEspecieTotal() {
        if (this.contrapartidaEspecieTotal != null) {
            return contrapartidaEspecieTotal;
        }
        return 0L;
    }

    public void setContrapartidaEspecieTotal(Long contrapartidaEspecieTotal) {
        this.contrapartidaEspecieTotal = contrapartidaEspecieTotal;
    }

    public Long getValorAdministrativoTotal() {
        if (this.valorAdministrativoTotal != null) {
            return valorAdministrativoTotal;
        }
        return 0L;
    }

    public void setValorAdministrativoTotal(Long valorAdministrativoTotal) {
        this.valorAdministrativoTotal = valorAdministrativoTotal;
    }

    public Long getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Long valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getPublicoObjetivo() {
        return publicoObjetivo;
    }

    public void setPublicoObjetivo(String publicoObjetivo) {
        this.publicoObjetivo = publicoObjetivo;
    }

    public Boolean getTienePryAsociado() {
        return tienePryAsociado;
    }

    public void setTienePryAsociado(Boolean tienePryAsociado) {
        this.tienePryAsociado = tienePryAsociado;
    }

    public Long getProyectoAsociado() {
        return proyectoAsociado;
    }

    public void setProyectoAsociado(Long proyectoAsociado) {
        this.proyectoAsociado = proyectoAsociado;
    }

    public String getProyectoUsadoNoHermes() {
        return proyectoUsadoNoHermes;
    }

    public void setProyectoUsadoNoHermes(String proyectoUsadoNoHermes) {
        this.proyectoUsadoNoHermes = proyectoUsadoNoHermes;
    }

    public String getEntidadFinancieraNoHermes() {
        return entidadFinancieraNoHermes;
    }

    public void setEntidadFinancieraNoHermes(String entidadFinancieraNoHermes) {
        this.entidadFinancieraNoHermes = entidadFinancieraNoHermes;
    }

    public String getTipologiaProyecto() {
        return tipologiaProyecto;
    }

    public void setTipologiaProyecto(String tipologiaProyecto) {
        this.tipologiaProyecto = tipologiaProyecto;
    }

    public String getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(String unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public String getActoEntidad() {
        return actoEntidad;
    }

    public void setActoEntidad(String actoEntidad) {
        this.actoEntidad = actoEntidad;
    }

    public String getEntidadContratante() {
        return entidadContratante;
    }

    public void setEntidadContratante(String entidadContratante) {
        this.entidadContratante = entidadContratante;
    }

    public String getTipofechaPry() {
        return tipofechaPry;
    }

    public void setTipofechaPry(String tipofechaPry) {
        this.tipofechaPry = tipofechaPry;
    }

    public String getDesembolsos() {
        return desembolsos;
    }

    public void setDesembolsos(String desembolsos) {
        this.desembolsos = desembolsos;
    }

    public String getInformes() {
        return informes;
    }

    public void setInformes(String informes) {
        this.informes = informes;
    }

    public String getTrasladoRubros() {
        return trasladoRubros;
    }

    public void setTrasladoRubros(String trasladoRubros) {
        this.trasladoRubros = trasladoRubros;
    }

    public String getVinculaFacultades() {
        return vinculaFacultades;
    }

    public void setVinculaFacultades(String vinculaFacultades) {
        this.vinculaFacultades = vinculaFacultades;
    }

    public String getVinculaSedes() {
        return vinculaSedes;
    }

    public void setVinculaSedes(String vinculaSedes) {
        this.vinculaSedes = vinculaSedes;
    }

    public String getSedeSel() {
        return sedeSel;
    }

    public void setSedeSel(String sedeSel) {
        this.sedeSel = sedeSel;
    }

    public Sede getSedeSeleccionada() {
        return sedeSeleccionada;
    }

    public void setSedeSeleccionada(Sede sedeSeleccionada) {
        this.sedeSeleccionada = sedeSeleccionada;
    }

    public Dependencia getFacSeleccionada() {
        return facSeleccionada;
    }

    public void setFacSeleccionada(Dependencia facSeleccionada) {
        this.facSeleccionada = facSeleccionada;
    }

    public String getEntidadSel() {
        return entidadSel;
    }

    public void setEntidadSel(String entidadSel) {
        this.entidadSel = entidadSel;
    }

    public String getEntidadSeleccionada() {
        return entidadSeleccionada;
    }

    public void setEntidadSeleccionada(String entidadSeleccionada) {
        this.entidadSeleccionada = entidadSeleccionada;
    }

    public String getVinculaEntidades() {
        return vinculaEntidades;
    }

    public void setVinculaEntidades(String vinculaEntidades) {
        this.vinculaEntidades = vinculaEntidades;
    }

    public Long getValorEntidad() {
        return valorEntidad;
    }

    public void setValorEntidad(Long valorEntidad) {
        this.valorEntidad = valorEntidad;
    }

    public String getEmpresaEjecutora() {
        return empresaEjecutora;
    }

    public void setEmpresaEjecutora(String empresaEjecutora) {
        this.empresaEjecutora = empresaEjecutora;
    }

    public String getActoInterno() {
        return actoInterno;
    }

    public void setActoInterno(String actoInterno) {
        this.actoInterno = actoInterno;
    }

    public Long getMontoUNTotal() {
        return montoUNTotal;
    }

    public void setMontoUNTotal(Long montoUNTotal) {
        this.montoUNTotal = montoUNTotal;
    }

    public Long getContrapartidaEspecieUN() {
        return contrapartidaEspecieUN;
    }

    public void setContrapartidaEspecieUN(Long contrapartidaEspecieUN) {
        this.contrapartidaEspecieUN = contrapartidaEspecieUN;
    }

    public Long getContrapartidaEfectivoUN() {
        return contrapartidaEfectivoUN;
    }

    public void setContrapartidaEfectivoUN(Long contrapartidaEfectivoUN) {
        this.contrapartidaEfectivoUN = contrapartidaEfectivoUN;
    }

    public Long getDuracionFinal() {
        return duracionFinal;
    }

    public void setDuracionFinal(Long duracionFinal) {
        this.duracionFinal = duracionFinal;
    }

    public String getTipologia_sec() {
        return tipologia_sec;
    }

    public void setTipologia_sec(String tipologia_sec) {
        this.tipologia_sec = tipologia_sec;
    }

    public Long getSedeEjecutora() {
        return sedeEjecutora;
    }

    public void setSedeEjecutora(Long sedeEjecutora) {
        this.sedeEjecutora = sedeEjecutora;
    }

    public String getPresentaInformeParcial() {
        return presentaInformeParcial;
    }

    public void setPresentaInformeParcial(String presentaInformeParcial) {
        this.presentaInformeParcial = presentaInformeParcial;
    }

    public String getPeriodoInforme() {
        return periodoInforme;
    }

    public void setPeriodoInforme(String periodoInforme) {
        this.periodoInforme = periodoInforme;
    }

    public String getPresentaInformeFinal() {
        return presentaInformeFinal;
    }

    public void setPresentaInformeFinal(String presentaInformeFinal) {
        this.presentaInformeFinal = presentaInformeFinal;
    }

    public Long getProyectoPadre() {
        return proyectoPadre;
    }

    public Long getValorTotalFinal() {
        return valorTotalFinal;
    }

    public void setValorTotalFinal(Long valorTotalFinal) {
        this.valorTotalFinal = valorTotalFinal;
    }

    public void setProyectoPadre(Long proyectoPadre) {
        this.proyectoPadre = proyectoPadre;
    }

    public List getAvalesProyecto() {
        return avalesProyecto;
    }

    public void setAvalesProyecto(List avalesProyecto) {
        this.avalesProyecto = avalesProyecto;
    }

    public String getPlanTematico() {
        return planTematico;
    }

    public void setPlanTematico(String planTematico) {
        this.planTematico = planTematico;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getNumeroDesembolsos() {
        return numeroDesembolsos;
    }

    public void setNumeroDesembolsos(int numeroDesembolsos) {
        this.numeroDesembolsos = numeroDesembolsos;
    }

    public String getEsJornadaDocente() {
        return esJornadaDocente;
    }

    public void setEsJornadaDocente(String esJornadaDocente) {
        this.esJornadaDocente = esJornadaDocente;
    }

    public Integer getDuracionDiasAcumulada() {
        if (duracionDiasAcumulada == null || duracionDiasAcumulada.equals("")) {
            duracionDiasAcumulada = 0;
        }
        return duracionDiasAcumulada;
    }

    public void setDuracionDiasAcumulada(Integer duracionDiasAcumulada) {
        this.duracionDiasAcumulada = duracionDiasAcumulada;
    }

    public String getDirectorExterno() {
        return directorExterno;
    }

    public void setDirectorExterno(String directorExterno) {
        this.directorExterno = directorExterno;
    }

    public InvestigadorProyecto getInvestigadorPrincipalVista() {
        return investigadorPrincipalVista;
    }

    public void setInvestigadorPrincipalVista(InvestigadorProyecto investigadorPrincipalVista) {
        this.investigadorPrincipalVista = investigadorPrincipalVista;
    }

    public Float getValorInscripcion() {
        return valorInscripcion;
    }

    public void setValorInscripcion(Float valorInscripcion) {
        this.valorInscripcion = valorInscripcion;
    }

    public Long getCuposDescuento() {
        return cuposDescuento;
    }

    public void setCuposDescuento(Long cuposDescuento) {
        this.cuposDescuento = cuposDescuento;
    }

    public String getSistCalificacion() {
        return sistCalificacion;
    }

    public void setSistCalificacion(String sistCalificacion) {
        this.sistCalificacion = sistCalificacion;
    }

    public String getTipoCertificacion() {
        return tipoCertificacion;
    }

    public void setTipoCertificacion(String tipoCertificacion) {
        this.tipoCertificacion = tipoCertificacion;
    }

    public Long getAvalAsociado() {
        return avalAsociado;
    }

    public void setAvalAsociado(Long avalAsociado) {
        this.avalAsociado = avalAsociado;
    }

    public Long getDocumentoExterno() {
        return documentoExterno;
    }

    public void setDocumentoExterno(Long documentoExterno) {
        this.documentoExterno = documentoExterno;
    }

    public Short getHorasExterno() {
        return horasExterno;
    }

    public void setHorasExterno(Short horasExterno) {
        this.horasExterno = horasExterno;
    }

    public Long getEstimuloExterno() {
        return estimuloExterno;
    }

    public void setEstimuloExterno(Long estimuloExterno) {
        this.estimuloExterno = estimuloExterno;
    }

    public String getTipoDocExterno() {
        return tipoDocExterno;
    }

    public void setTipoDocExterno(String tipoDocExterno) {
        this.tipoDocExterno = tipoDocExterno;
    }

    public String getProgramaCYT() {
        return programaCYT;
    }

    public void setProgramaCYT(String programaCYT) {
        this.programaCYT = programaCYT;
    }

    public String getActivoPI() {
        return activoPI;
    }

    public void setActivoPI(String activoPI) {
        this.activoPI = activoPI;
    }

    public boolean isEsInvestigacion() {
        return esInvestigacion;
    }

    public void setEsInvestigacion(boolean esInvestigacion) {
        this.esInvestigacion = esInvestigacion;
    }

    public boolean isEsExtension() {
        return esExtension;
    }

    public void setEsExtension(boolean esExtension) {
        this.esExtension = esExtension;
    }

    public int getNumeroActaInicio() {
        return numeroActaInicio;
    }

    public void setNumeroActaInicio(int numeroActaInicio) {
        this.numeroActaInicio = numeroActaInicio;
    }

    public Date getFechaInformeFinal() {
        return fechaInformeFinal;
    }

    public void setFechaInformeFinal(Date fechaInformeFinal) {
        this.fechaInformeFinal = fechaInformeFinal;
    }

    public boolean isRequiereAutorizacCR() {
        return requiereAutorizacCR;
    }

    public void setRequiereAutorizacCR(boolean requiereAutorizacCR) {
        this.requiereAutorizacCR = requiereAutorizacCR;
    }

    public String getProgramaMacro() {
        return programaMacro;
    }

    public void setProgramaMacro(String programaMacro) {
        this.programaMacro = programaMacro;
    }

    public Date getFechaActoAdministrativo() {
        return fechaActoAdministrativo;
    }

    public void setFechaActoAdministrativo(Date fechaActoAdministrativo) {
        this.fechaActoAdministrativo = fechaActoAdministrativo;
    }

    public String getCompromisoEjecucion() {
        return compromisoEjecucion;
    }

    public void setCompromisoEjecucion(String compromisoEjecucion) {
        this.compromisoEjecucion = compromisoEjecucion;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public String getMotivoEstadoProyectoOtro() {
        return motivoEstadoProyectoOtro;
    }

    public void setMotivoEstadoProyectoOtro(String motivoEstadoProyectoOtro) {
        this.motivoEstadoProyectoOtro = motivoEstadoProyectoOtro;
    }

    public String getEsPryContrapartida() {
        return esPryContrapartida;
    }

    public void setEsPryContrapartida(String esPryContrapartida) {
        this.esPryContrapartida = esPryContrapartida;
    }

    public static Comparator<Proyecto> PryNombreComparator = new Comparator<Proyecto>() {

        public int compare(Proyecto s1, Proyecto s2) {
            String nombre1 = s1.getNombre().toUpperCase();
            String nombre2 = s2.getNombre().toUpperCase();

            // ascending order
            return nombre1.compareTo(nombre2);

            // descending order
            // return nombre2.compareTo(nombre1);
        }
    };

    public Set getObjetivosResultados() {
        return objetivosResultados;
    }

    public void setObjetivosResultados(Set objetivosResultados) {
        this.objetivosResultados = objetivosResultados;
    }

    public String getTieneCompromisosPendientes() {
        return tieneCompromisosPendientes;
    }

    public void setTieneCompromisosPendientes(String tieneCompromisosPendientes) {
        this.tieneCompromisosPendientes = tieneCompromisosPendientes;
    }

    public Set<ProyectoInforme> getInformesProyecto() {
        return informesProyecto;
    }

    public void setInformesProyecto(Set<ProyectoInforme> informesProyecto) {
        this.informesProyecto = informesProyecto;
    }

    public Date getFechaLegalizacion() {
        return fechaLegalizacion;
    }

    public void setFechaLegalizacion(Date fechaLegalizacion) {
        this.fechaLegalizacion = fechaLegalizacion;
    }

    public String getEntidadSeleccionadaEspecie() {
        return entidadSeleccionadaEspecie;
    }

    public void setEntidadSeleccionadaEspecie(String entidadSeleccionadaEspecie) {
        this.entidadSeleccionadaEspecie = entidadSeleccionadaEspecie;
    }

    public Date getFechaActaInicioExt() {
        return fechaActaInicioExt;
    }

    public void setFechaActaInicioExt(Date fechaActaInicioExt) {
        this.fechaActaInicioExt = fechaActaInicioExt;
    }

    public boolean getEsPermisoMarco() {
        if (this.modalidad != null && this.modalidad.getId() != null && this.modalidad.getTipo().getId().equals(TipoModalidad.PERMISOMARCO)) {
            return true;
        }
        return false;
    }

    public boolean getEsPermisoMarcoAsignatura() {
        if (this.modalidad != null && this.modalidad.getId() != null && this.modalidad.getTipo().getId().equals(TipoModalidad.PERMISOMARCOASIGNATURA)) {
            return true;
        }
        return false;
    }

    public boolean getEsContratoBiodiversidad() {
        if (this.modalidad != null && this.modalidad.getId() != null
                && (this.modalidad.getId().equals(21L) || this.modalidad.getId().equals(22L))) {
            return true;
        }
        return false;
    }

    public boolean isEsRegistroBiodiversidad() {
        if (getEsPermisoMarco() || getEsPermisoMarcoAsignatura() || getEsContratoBiodiversidad()) {
            return true;
        }
        return false;
    }

    public String getTieneBiodiversidad() {
        return tieneBiodiversidad;
    }

    public void setTieneBiodiversidad(String tieneBiodiversidad) {
        this.tieneBiodiversidad = tieneBiodiversidad;
    }
    
    public String getTieneLaboratorios() {
		return tieneLaboratorios;
	}

	public void setTieneLaboratorios(String tieneLaboratorios) {
		this.tieneLaboratorios = tieneLaboratorios;
	}

	public String getTieneComunidades() {
        return tieneComunidades;
    }

    public void setTieneComunidades(String tieneComunidades) {
        this.tieneComunidades = tieneComunidades;
    }

    public String getTieneParques() {
        return tieneParques;
    }

    public void setTieneParques(String tieneParques) {
        this.tieneParques = tieneParques;
    }

    public String getTieneRecoleccion() {
        return tieneRecoleccion;
    }

    public void setTieneRecoleccion(String tieneRecoleccion) {
        this.tieneRecoleccion = tieneRecoleccion;
    }

    public String getTieneBioProspeccion() {
        return tieneBioProspeccion;
    }

    public void setTieneBioProspeccion(String tieneBioProspeccion) {
        this.tieneBioProspeccion = tieneBioProspeccion;
    }

    public String getTieneActivoPI() {
        return tieneActivoPI;
    }

    public void setTieneActivoPI(String tieneActivoPI) {
        this.tieneActivoPI = tieneActivoPI;
    }

    /**
     * @return the tipoProyectoLaboratorios
     */
    public Tipos getTipoProyectoLaboratorios() {
        return tipoProyectoLaboratorios;
    }

    /**
     * @param tipoProyectoLaboratorios
     *            the tipoProyectoLaboratorios to set
     */
    public void setTipoProyectoLaboratorios(Tipos tipoProyectoLaboratorios) {
        this.tipoProyectoLaboratorios = tipoProyectoLaboratorios;
    }

    public Date getFechaLegalizacionParcial() {
        return fechaLegalizacionParcial;
    }

    public void setFechaLegalizacionParcial(Date fechaLegalizacionParcial) {
        this.fechaLegalizacionParcial = fechaLegalizacionParcial;
    }

    public String getReclamacion() {
        return reclamacion;
    }

    public void setReclamacion(String reclamacion) {
        this.reclamacion = reclamacion;
    }

    public String getRespuestaReclamacion() {
        return respuestaReclamacion;
    }

    public void setRespuestaReclamacion(String respuestaReclamacion) {
        this.respuestaReclamacion = respuestaReclamacion;
    }

    public String getEstadoReclamacion() {
        return estadoReclamacion;
    }

    public void setEstadoReclamacion(String estadoReclamacion) {
        this.estadoReclamacion = estadoReclamacion;
    }

    public String getReclamacionEvaluacion() {
        return reclamacionEvaluacion;
    }

    public void setReclamacionEvaluacion(String reclamacionEvaluacion) {
        this.reclamacionEvaluacion = reclamacionEvaluacion;
    }

    public String getRespuestaReclamacionEvaluacion() {
        return respuestaReclamacionEvaluacion;
    }

    public void setRespuestaReclamacionEvaluacion(String respuestaReclamacionEvaluacion) {
        this.respuestaReclamacionEvaluacion = respuestaReclamacionEvaluacion;
    }

    public String getEstadoReclamacionEvaluacion() {
        return estadoReclamacionEvaluacion;
    }

    public void setEstadoReclamacionEvaluacion(String estadoReclamacionEvaluacion) {
        this.estadoReclamacionEvaluacion = estadoReclamacionEvaluacion;
    }

    public Date getFechaEnvioReclamacionReq() {
        return fechaEnvioReclamacionReq;
    }

    public void setFechaEnvioReclamacionReq(Date fechaEnvioReclamacionReq) {
        this.fechaEnvioReclamacionReq = fechaEnvioReclamacionReq;
    }

    public Date getFechaRespuestaReclamacionReq() {
        return fechaRespuestaReclamacionReq;
    }

    public void setFechaRespuestaReclamacionReq(Date fechaRespuestaReclamacionReq) {
        this.fechaRespuestaReclamacionReq = fechaRespuestaReclamacionReq;
    }

    public Date getFechaEnvioReclamacionEva() {
        return fechaEnvioReclamacionEva;
    }

    public void setFechaEnvioReclamacionEva(Date fechaEnvioReclamacionEva) {
        this.fechaEnvioReclamacionEva = fechaEnvioReclamacionEva;
    }

    public Date getFechaRespuestaReclamacionEva() {
        return fechaRespuestaReclamacionEva;
    }

    public void setFechaRespuestaReclamacionEva(Date fechaRespuestaReclamacionEva) {
        this.fechaRespuestaReclamacionEva = fechaRespuestaReclamacionEva;
    }

    public Long getValorEspecieCalculado() {
        Long total = 0L;
        Iterator<Financiacion> i = getListaEntidadesEspecie().iterator();
        while (i.hasNext()) {
            Financiacion f = i.next();
            Long valorEspecie = 0L;
            if (f.getValorEspecie() != null) {
                valorEspecie = f.getValorEspecie();
            }
            total += valorEspecie;
        }
        return total;
    }

    public Long getValorEntidadesParticipantesCalculado() {
        Long total = 0L;
        Iterator<Financiacion> i = getListaEntidadesParticipantes().iterator();
        while (i.hasNext()) {
            Financiacion f = i.next();
            Long valor = 0L;
            if (f.getValor() != null) {
                valor = f.getValor();
            }
            Long valorEspecie = 0L;
			if (f.getValorEspecie() != null
					&& (((Convocatoria) this.getModalidad()).getRestriccion() != null
							&& !((Convocatoria) this.getModalidad()).getRestriccion().getId()
									.equals(RestriccionConvocatoria.CONV_UROSARIO))
					|| ((Convocatoria) this.getModalidad()).getRestriccion() == null) {
				valorEspecie = f.getValorEspecie();
			}
            total += valor + valorEspecie;
        }
        return total;
    }
    
    public Long getTotalSgr() {
    	Long valor = 0L;
		Iterator<Financiacion> i = getListaFinancionesFicha().iterator();
		while (i.hasNext()) {
			Financiacion f = i.next();
			if(f.getFuente().getId().equals(FuenteFinanciacion.ID_SISTEMA_GENERAL_REGALIAS)) {
				valor = f.getValor();
				break;
			}
		}
		return valor;
    }
    
    public Long getTotalFuentesNoRegalias() {
    	Long valor = 0L;
		Iterator<Financiacion> i = getListaFinancionesFicha().iterator();
		while (i.hasNext()) {
			Financiacion f = i.next();
			if(!f.getFuente().getDescripcion().toUpperCase().contains("REGAL") 
					&& (f.getTipoEntidad()!=null && !f.getTipoEntidad().equals(FuenteFinanciacion.ENTIDAD_PARTICIPANTE))) {
				valor = valor + f.getValor();
			}
		}
		return valor;
    }
    
	public Long getValorEntidadesParticipantesCalculadoEspecie() {
		Long valorEspecie = 0L;
		Iterator<Financiacion> i = getListaEntidadesParticipantes().iterator();
		while (i.hasNext()) {
			Financiacion f = i.next();
			if (f.getValorEspecie() != null) {
				valorEspecie = valorEspecie + f.getValorEspecie();
			}
		}
		return valorEspecie;
	}
	
	public Long getValorEntidadesParticipantesCalculadoEfectivo() {
		Long valor = 0L;
		Iterator<Financiacion> i = getListaEntidadesParticipantes().iterator();
		while (i.hasNext()) {
			Financiacion f = i.next();
			if (f.getValor() != null) {
				valor = valor + f.getValor();
			}
		}
		return valor;
	}
	
	public Long getValorInternoEfectivoCalculado() {
		Long valor = 0L;
		Iterator<Financiacion> i = getListaFinancionesFicha().iterator();
		while (i.hasNext()) {
			Financiacion f = i.next();
			if(f!=null && f.getFuente()!=null && f.getFuente().esInterna() && !f.getGastos().isEmpty()) {
				for(int j=0; j< f.getGastos().size(); j++) {
					Gasto gasto = f.getListaGastos().get(j);
					if (!gasto.getTipoRubro().isRubroContrapartida()) {
						valor = valor + gasto.getValor();
					}
				}
			}
			
		}
		return valor;
	}
	
	public Long getValorExternoEfectivoCalculado() {
		Long valor = 0L;
		Iterator<Financiacion> i = getListaFinancionesFicha().iterator();
		while (i.hasNext()) {
			Financiacion f = i.next();
			if(!f.getFuente().esInterna() && !f.getGastos().isEmpty()) {
				for(int j=0; j< f.getGastos().size(); j++) {
					Gasto gasto = f.getListaGastos().get(j);
					if (!gasto.getTipoRubro().isRubroContrapartida()) {
						valor = valor + gasto.getValor2();
						valor = valor + gasto.getValor3();
						valor = valor + gasto.getValor4();
						valor = valor + gasto.getValor5();
						valor = valor + gasto.getValor6();
					}
				}
			}
			
		}
		return valor;
	}
	
	public Long getValorInternoEspecieCalculado() {
		Long valor = 0L;
		Iterator<Financiacion> i = getListaFinancionesFicha().iterator();
		while (i.hasNext()) {
			Financiacion f = i.next();
			if(f.getFuente().esInterna() && !f.getGastos().isEmpty()) {
				for(int j=0; j< f.getGastos().size(); j++) {
					Gasto gasto = f.getListaGastos().get(j);
					if (gasto.getTipoRubro().isRubroContrapartida()) {
						valor = valor + gasto.getValor();
					}
				}
			}
			
		}
		return valor;
	}
	
	public Long getValorExternoCalculado() {
		Long valor = 0L;
		Iterator<Financiacion> i = getListaFinancionesFicha().iterator();
		while (i.hasNext()) {
			Financiacion f = i.next();
			if(!f.getFuente().esInterna() && (f.getTipoEntidad()==null || !f.getTipoEntidad().equals("P")) && !f.getGastos().isEmpty()) {
				for(int j=0; j< f.getGastos().size(); j++) {
					Gasto gasto = f.getListaGastos().get(j);
					valor = valor + gasto.getValor();
				}
			}
			
		}
		return valor;
	}

    public List<Financiacion> getListaFinanciones() {
		List<Financiacion> listaFinanciones = new ArrayList<Financiacion>();
		if (financiaciones != null) {
			Iterator<Financiacion> i = financiaciones.iterator();
			while (i.hasNext()) {
				Financiacion f = i.next();
				if (f.getEsLegalizacion() == null || (f.getEsLegalizacion() != null
						&& !Financiacion.SI_ES_LEGALIZACION.equals(f.getEsLegalizacion()))) {
					listaFinanciones.add(f);
				}
			}
		}
        return listaFinanciones;
    }

    public List<Financiacion> getListaFinancionesFicha() {
        List<Financiacion> listaFinanciones = new ArrayList<Financiacion>();
        if (financiaciones != null) {
            Iterator<Financiacion> i = financiaciones.iterator();
            while (i.hasNext()) {
                Financiacion f = i.next();
                if ((f.getEsLegalizacion() == null || (f.getEsLegalizacion() != null
                        && !Financiacion.SI_ES_LEGALIZACION.equals(f.getEsLegalizacion())))) {
                    listaFinanciones.add(f);
                }
            }
        }
        return listaFinanciones;
    }

    public List<Financiacion> getListaEntidadesParticipantes() {
        return getListaEntidadesTipoFicha(FuenteFinanciacion.ENTIDAD_PARTICIPANTE);
    }

    public List<Financiacion> getListaEntidadesEspecie() {
        return getListaEntidadesTipoFicha(FuenteFinanciacion.ENTIDAD_ESPECIE);
    }

    public List<Financiacion> getListaEntidadesTipoFicha(String tipo) {
        List<Financiacion> listaEntidadesParticipantes = new ArrayList<Financiacion>();
        Iterator<Financiacion> i = getListaFinancionesFicha().iterator();
        while (i.hasNext()) {
            Financiacion f = i.next();
            if (f.getTipoEntidad() != null && tipo.equals(f.getTipoEntidad())) {
                listaEntidadesParticipantes.add(f);
            }
        }
        return listaEntidadesParticipantes;
    }

    public List<Financiacion> getListaEntidadesLegalizacion() {
        List<Financiacion> listaEntidadesLegalizacion = new ArrayList<Financiacion>();
        if (financiaciones != null) {
            Iterator<Financiacion> i = financiaciones.iterator();
            while (i.hasNext()) {
                Financiacion f = i.next();
                if (f.getTipoEntidad() != null && f.getEsLegalizacion() != null
                        && f.getEsLegalizacion().equals(Financiacion.SI_ES_LEGALIZACION)) {
                    listaEntidadesLegalizacion.add(f);
                }
            }
        }
        return listaEntidadesLegalizacion;
    }

    public List<Financiacion> getListaFuentesFinancacionFicha() {
        List<Financiacion> listaFuentesFinanciacion = new ArrayList<Financiacion>();
        Iterator<Financiacion> i = getListaFinancionesFicha().iterator();
        while (i.hasNext()) {
            Financiacion f = i.next();
            if (f.getTipoEntidad() == null
                    || (f.getTipoEntidad() != null && !f.getTipoEntidad().equals(FuenteFinanciacion.ENTIDAD_ESPECIE)
                            && !f.getTipoEntidad().equals(FuenteFinanciacion.ENTIDAD_PARTICIPANTE))) {
                listaFuentesFinanciacion.add(f);
            }
        }
        return listaFuentesFinanciacion;
    }

    public boolean isExisteEntidadParticipante(String entidadParticipante) {
        Iterator<Financiacion> i = getListaEntidadesParticipantes().iterator();
        while (i.hasNext()) {
            Financiacion f = i.next();
            if (f.getFuente().getId().equals(entidadParticipante)) {
                return true;
            }
        }
        return false;
    }

    public boolean isExisteFuenteFinancionFicha(String fuenteFinanciacion) {
        Iterator<Financiacion> i = getListaFuentesFinancacionFicha().iterator();
        while (i.hasNext()) {
            Financiacion f = i.next();
            if (f.getFuente().getId().equals(fuenteFinanciacion)) {
                return true;
            }
        }
        return false;
    }

    public boolean isExisteEntidadesLegalizacion(String fuenteFinanciacion) {
        Iterator<Financiacion> i = getListaEntidadesLegalizacion().iterator();
        while (i.hasNext()) {
            Financiacion f = i.next();
            if (f.getFuente().getId().equals(fuenteFinanciacion)) {
                return true;
            }
        }
        return false;
    }

    public void eliminarFuente(Financiacion financiacion) {
        if (financiacion != null && financiaciones != null) {
            financiaciones.remove(financiacion);
        }
    }
    
    public boolean isConvUDEC(){
    	if(getModalidad().getId().equals(690L) || getModalidad().getId().equals(699L) || ((Convocatoria)getModalidad()).getPadre().getId().equals(491L)){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    
    public boolean isConvSUE(){
    	RestriccionConvocatoria restriccion = ((Convocatoria)getModalidad()).getRestriccion();
    	Boolean isSUE2017 = restriccion != null ? restriccion.getId().equals("CONV_SUE_2017") : false;
    	if(getModalidad().getId().equals(755L) || getModalidad().getId().equals(770L) || isSUE2017)
    		return true;
    	else
    		return false;
    }
    public Long getMontoFinanciadoConvocatoriaUDEC(){
    	Long total = 0L;
        Iterator<Financiacion> i = getListaFuentesFinancacionFicha().iterator();
        while (i.hasNext()) {
            Financiacion f = i.next();
            if (f.getFuente().getInternaExterna().equals(FuenteFinanciacion.OCULTA_EXTERNA)) {
                if (f.getValor() != null) {
                    total = total + f.getValor();
                }
            }
        }
        /**
         * Si se indico valor en especie para una entidad externa se suma al
         * monto a solicitar
         */
        if (getCostoFacultad() != null) {
            total = total + getCostoFacultad();
        }
        return total;
    }

    /**
     * Obtiene el total de financiación externa del proyecto para sugerir el
     * valor solicitado a la entidad convocante
     */
    public Long getMontofinanciarCalculado() {
        Long total = 0L;
        Iterator<Financiacion> i = getListaFuentesFinancacionFicha().iterator();
        while (i.hasNext()) {
            Financiacion f = i.next();
			if (f.getFuente().getInternaExterna().equals(FuenteFinanciacion.externa)
					|| f.getFuente().getInternaExterna().equals(FuenteFinanciacion.OCULTA_EXTERNA)) {
                if (f.getValor() != null) {
                    total = total + f.getValor();
                }
//                if (f.getValorEspecie() != null) {
//                    total = total + f.getValorEspecie();
//                }
            }
        }
        /**
         * Si se indico valor en especie para una entidad externa se suma al
         * monto a solicitar
         */
//        if (getCostoFacultad() != null) {
//            total = total + getCostoFacultad();
//        }
        return total;
    }
    
    public Long getMontoEspecieContrapartidaExternaUDEC(){
        Long total = 0L;
        Iterator<Financiacion> i = getListaFuentesFinancacionFicha().iterator();
        while (i.hasNext()) {
            Financiacion f = i.next();
            if (f.getFuente().getInternaExterna().equals(FuenteFinanciacion.externa)) {            	
                if (f.getValor() != null) {
                    total = total + f.getValor();
                }
            }
        }
        return total;
    }

    public Tipos getSubtipoProyectoLaboratorios() {
        return subtipoProyectoLaboratorios;
    }

    public void setSubtipoProyectoLaboratorios(Tipos subtipoProyectoLaboratorios) {
        this.subtipoProyectoLaboratorios = subtipoProyectoLaboratorios;
    }

    @Override
    public int compareTo(Proyecto arg0) {
        if (arg0.getId() == null) {
            return 1;
        }
        if (this.id == null) {
            return -1;
        }
        if (this.id < arg0.getId()) {
            return -1;
        }
        if (this.id < arg0.getId()) {
            return 1;
        }
        return 0;
    }

    /**
     * @return the vigencia
     */
    public Long getVigencia() {
        return vigencia;
    }

    /**
     * @param vigencia
     *            the vigencia to set
     */
    public void setVigencia(Long vigencia) {
        this.vigencia = vigencia;
    }

    public String getNombreLaboratorio() {
		return nombreLaboratorio;
	}

	public void setNombreLaboratorio(String nombreLaboratorio) {
		this.nombreLaboratorio = nombreLaboratorio;
	}

	/**
     * @return the contrapartidaExternaEspecieTotal
     */
    public Long getContrapartidaExternaEspecieTotal() {
        return contrapartidaExternaEspecieTotal;
    }

    /**
     * @param contrapartidaExternaEspecieTotal
     *            the contrapartidaExternaEspecieTotal to set
     */
    public void setContrapartidaExternaEspecieTotal(Long contrapartidaExternaEspecieTotal) {
        this.contrapartidaExternaEspecieTotal = contrapartidaExternaEspecieTotal;
    }

    public Proyecto clone() throws CloneNotSupportedException {
        return (Proyecto) super.clone();
    }

    public Proyecto crearVersionBase() {
        Proyecto pryDuplicado = new Proyecto();

        pryDuplicado.setProyectoPadre(this.getId());
        pryDuplicado.setDuracion(this.getDuracion());
        pryDuplicado.setModalidad(this.getModalidad());
        pryDuplicado.cambiarEstado(EstadoProyecto.BASE);
        pryDuplicado.setFase(0);
        pryDuplicado.setTienePryAsociado(false);
        pryDuplicado.setTipoActividad(this.getTipoActividad() != null ? this.getTipoActividad() : "PINV");
        pryDuplicado.setNombre(this.getNombre());
        pryDuplicado.setResumen(this.getResumen());

        // fin actualizac pry actual

        // Continuación asignación de datos proyecto duplicado
        pryDuplicado.setEntidadesParticipantes(this.getEntidadesParticipantes());
        pryDuplicado.setFase(this.getFase());
        pryDuplicado.setCodigoQuipu(this.getCodigoQuipu());
        pryDuplicado.setCodigoDib(this.getCodigoDib());
        pryDuplicado.setLugar(this.getLugar());
        pryDuplicado.setObservaciones(this.getObservaciones());
        pryDuplicado.setValorSolicitado(this.getValorSolicitado());
        pryDuplicado.setOtrosAportes(this.getOtrosAportes());
        pryDuplicado.setJustificacion(this.getJustificacion());
        pryDuplicado.setMarcoTeorico(this.getMarcoTeorico());
        pryDuplicado.setObjetivoGeneral(this.getObjetivoGeneral());
        pryDuplicado.setMetodologia(this.getMetodologia());
        pryDuplicado.setProgramaAcademico(this.getProgramaAcademico());
        pryDuplicado.setAsignatura(this.getAsignatura());
        pryDuplicado.setPresentacionPrograma(this.getPresentacionPrograma());
        pryDuplicado.setConsideracionesEticas(this.getConsideracionesEticas());
        pryDuplicado.setPropiedadIntelectual(this.getPropiedadIntelectual());
        pryDuplicado.setImpactoEsperado(this.getImpactoEsperado());
        pryDuplicado.setDescripcion(this.getDescripcion());
        pryDuplicado.setPermitirModificacion(this.getPermitirModificacion());
        pryDuplicado.setInvolucraMaestriaDoctorado(this.getInvolucraMaestriaDoctorado());
        pryDuplicado.setLineaBaseProyecto(this.getLineaBaseProyecto());
        pryDuplicado.setNombreConvocatoriaPadre(this.getNombreConvocatoriaPadre());
        pryDuplicado.setDependenciaPrincipal(this.getDependenciaPrincipal());
        pryDuplicado.setFechaTentativaInicio(this.getFechaTentativaInicio());
        pryDuplicado.setFechaFinalizacion(this.getFechaFinalizacion());
        pryDuplicado.setFechaAti(this.getFechaAti());
        pryDuplicado.setDuracionAcumulada(this.getDuracionAcumulada());
        pryDuplicado.setRelacionBicentenario(this.getRelacionBicentenario());
        pryDuplicado.setTipoActividadECP(this.getTipoActividadECP());
        pryDuplicado.setSubTipoActividadECP(this.getSubTipoActividadECP());
        pryDuplicado.setRolUniversidad(this.getRolUniversidad());
        pryDuplicado.setMecanismoParticipacion(this.getMecanismoParticipacion());
        pryDuplicado.setValorTotalCostosDirectos(this.getValorTotalCostosDirectos());
        pryDuplicado.setValorTotalCostosIndirectos(this.getValorTotalCostosIndirectos());
        pryDuplicado.setValorTotalTransferencias(this.getValorTotalTransferencias());
        pryDuplicado.setContrapartidaEfectivo(this.getContrapartidaEfectivo());
        pryDuplicado.setContrapartidaEspecieTotal(this.getContrapartidaEspecieTotal());
        pryDuplicado.setValorTotal(this.getValorTotal());
        pryDuplicado.setDuracions(this.getDuracions());
        pryDuplicado.setDuraciond(this.getDuraciond());
        pryDuplicado.setDuracionh(this.getDuracionh());
        pryDuplicado.setDuracionA(this.getDuracionA());
        pryDuplicado.setSemanasFormulacion(this.getSemanasFormulacion());
        pryDuplicado.setHorasSemanaFormulacion(this.getHorasSemanaFormulacion());
        pryDuplicado.setObjetivoSocioeconomico(this.getObjetivoSocioeconomico());
        pryDuplicado.setDuraciona(this.getDuraciona());
        pryDuplicado.setValorPersonalTotal(this.getValorPersonalTotal());
        pryDuplicado.setNaDuracion(this.getNaDuracion());
        pryDuplicado.setNaDuracionF(this.getNaDuracionF());
        pryDuplicado.setValorAdministrativoTotal(this.getValorAdministrativoTotal());
        pryDuplicado.setNumeroRes(this.getNumeroRes());
        pryDuplicado.setCostoFacultad(this.getCostoFacultad());
        pryDuplicado.setCostoSede(this.getCostoSede());
        pryDuplicado.setCostoNacional(this.getCostoNacional());
        pryDuplicado.setFondoExtSol(this.getFondoExtSol());
        pryDuplicado.setFondoRiesgosUN(this.getFondoRiesgosUN());
        pryDuplicado.setFondoEspecialFac(this.getFondoEspecialFac());
        pryDuplicado.setFondoInvestigacUN(this.getFondoInvestigacUN());
        pryDuplicado.setFondoEspecialDirA(this.getFondoEspecialDirA());
        pryDuplicado.setDirNacionalExt(this.getDirNacionalExt());
        pryDuplicado.setDirExtSede(this.getDirExtSede());
        pryDuplicado.setAntecedentes(this.getAntecedentes());
        pryDuplicado.setTipoActividadPpal(this.getTipoActividadPpal());
        pryDuplicado.setDuracionTipo(this.getDuracionTipo());
        pryDuplicado.setCodLineaAccion(this.getCodLineaAccion());
        pryDuplicado.setCodPrograma(this.getCodPrograma());
        pryDuplicado.setAmbitoGeneralPM(this.getAmbitoGeneralPM());
        pryDuplicado.setColeccionBiologicaPM(this.getColeccionBiologicaPM());
        pryDuplicado.setCateogoriasTaxonomicasPM(this.getCateogoriasTaxonomicasPM());
        pryDuplicado.setTipoActividadInvestigacionPM(this.getTipoActividadInvestigacionPM());
        pryDuplicado.setAreaGeograficaPM(this.getAreaGeograficaPM());
        pryDuplicado.setJurisdiccionPM(this.getJurisdiccionPM());
        pryDuplicado.setPorcentajeFacultad(this.getPorcentajeFacultad());
        pryDuplicado.setPorcentajeCostosIndirectos(this.getPorcentajeCostosIndirectos());
        pryDuplicado.setPorcentajeTransferencias(this.getPorcentajeTransferencias());
        pryDuplicado.setExoneraInd(this.getExoneraInd());
        pryDuplicado.setExoneraTra(this.getExoneraTra());
        pryDuplicado.setCorte(this.getCorte());
        pryDuplicado.setTienePryAsociado(this.getTienePryAsociado());
        pryDuplicado.setProyectoAsociado(this.getProyectoAsociado());
        pryDuplicado.setEsJornadaDocente(this.getEsJornadaDocente());
        pryDuplicado.setEsPryContrapartida(this.getEsPryContrapartida());
        pryDuplicado.setDirectorExterno(this.getDirectorExterno());
        pryDuplicado.setDocumentoExterno(this.getDocumentoExterno());
        pryDuplicado.setHorasExterno(this.getHorasExterno());
        pryDuplicado.setEstimuloExterno(this.getEstimuloExterno());
        pryDuplicado.setTipoDocExterno(this.getTipoDocExterno());
        pryDuplicado.setActivoPI(this.getActivoPI());
        pryDuplicado.setTieneActivoPI(this.getTieneActivoPI());
        pryDuplicado.setNumeroActaInicio(this.getNumeroActaInicio());
        pryDuplicado.setFechaInformeFinal(this.getFechaInformeFinal());
        pryDuplicado.setMotivoEstadoProyectoOtro(this.getMotivoEstadoProyectoOtro());
        pryDuplicado.setRequiereAutorizacCR(this.isRequiereAutorizacCR());
        pryDuplicado.setBeneficiosExtSol(this.getBeneficiosExtSol());
        pryDuplicado.setIniciativaDe(this.getIniciativaDe());
        pryDuplicado.setIniciativa(this.getIniciativa());
        pryDuplicado.setCargaDocenteSiNo(this.getCargaDocenteSiNo());
        pryDuplicado.setTipoZona(this.getTipoZona());
        pryDuplicado.setNumeroGrupos(this.getNumeroGrupos());
        pryDuplicado.setEsInvestigacion(this.isEsInvestigacion());
        pryDuplicado.setEsExtension(this.isEsExtension());

        pryDuplicado.setNivelSostenibilidad(this.getNivelSostenibilidad());
        pryDuplicado.setPorqueNivelSostenibilidad(this.getPorqueNivelSostenibilidad());
        pryDuplicado.setContinuidadProyecto(this.getContinuidadProyecto());
        pryDuplicado.setPorqueContinuidadProyecto(this.getPorqueContinuidadProyecto());
        pryDuplicado.setArticulacionIniciativas(this.getArticulacionIniciativas());
        pryDuplicado.setAliados(this.getAliados());
        pryDuplicado.setObservacionesSostenibilidad(this.getObservacionesSostenibilidad());
        pryDuplicado.setSoportesUrl(this.getSoportesUrl());
        pryDuplicado.setViabilidad(this.getViabilidad());
        pryDuplicado.setApropiacion(this.getApropiacion());
        pryDuplicado.setRei1(this.getRei1());
        pryDuplicado.setRei2(this.getRei2());
        pryDuplicado.setRei3(this.getRei3());
        pryDuplicado.setRei4(this.getRei4());
        pryDuplicado.setRei5(this.getRei5());
        pryDuplicado.setRei6(this.getRei6());
        pryDuplicado.setRei7(this.getRei7());
        pryDuplicado.setRen1(this.getRen1());
        pryDuplicado.setRen2(this.getRen2());
        pryDuplicado.setRen3(this.getRen3());
        pryDuplicado.setRen4(this.getRen4());
        pryDuplicado.setRen5(this.getRen5());
        pryDuplicado.setRen6(this.getRen6());
        pryDuplicado.setRen7(this.getRen7());
        pryDuplicado.setAporteEconomico(this.getAporteEconomico());
        pryDuplicado.setAsistentes(this.getAsistentes());
        pryDuplicado.setConferencistasNacExt(this.getConferencistasNacExt());
        pryDuplicado.setConferencistasNacVinc(this.getConferencistasNacVinc());
        pryDuplicado.setConferencistasInternac(this.getConferencistasInternac());
        pryDuplicado.setTipoParticipacion(this.getTipoParticipacion());
        pryDuplicado.setOpcionesPermisoMarco(this.getOpcionesPermisoMarco());
        pryDuplicado.setClaseActividadECP(this.getClaseActividadECP());
        pryDuplicado.setTituloVolumenISBN(this.getTituloVolumenISBN());
        pryDuplicado.setTituloObraCompletaISBN(this.getTituloObraCompletaISBN());
        pryDuplicado.setMateriaISBN(this.getMateriaISBN());
        pryDuplicado.setTipoContenidoISBN(this.getTipoContenidoISBN());
        pryDuplicado.setNombreColeccionISBN(this.getNombreColeccionISBN());
        pryDuplicado.setNumeroColeccionISBN(this.getNumeroColeccionISBN());
        pryDuplicado.setSerieISBN(this.getSerieISBN());
        pryDuplicado.setIdiomaPrincipalISBN(this.getIdiomaPrincipalISBN());
        pryDuplicado.setEsTraduccionISBN(this.getEsTraduccionISBN());
        pryDuplicado.setIdiomaOriginalISBN(this.getIdiomaOriginalISBN());
        pryDuplicado.setIdiomaDestinoISBN(this.getIdiomaDestinoISBN());
        pryDuplicado.setTituloIdiomaOriginalISBN(this.getTituloIdiomaOriginalISBN());
        pryDuplicado.setNumeroEdicionISBN(this.getNumeroEdicionISBN());
        pryDuplicado.setCiudadEdicionISBN(this.getCiudadEdicionISBN());
        pryDuplicado.setDepartamentoEdicionISBN(this.getDepartamentoEdicionISBN());
        pryDuplicado.setFechaAparicionISBN(this.getFechaAparicionISBN());
        pryDuplicado.setEsCoedicionISBN(this.getEsCoedicionISBN());
        pryDuplicado.setCoeditorISBN(this.getCoeditorISBN());
        pryDuplicado.setEsComercializableISBN(this.getEsComercializableISBN());
        pryDuplicado.setNumEjemplaresNacionalISBN(this.getNumEjemplaresNacionalISBN());
        pryDuplicado.setPrecioCOPISBN(this.getPrecioCOPISBN());
        pryDuplicado.setNumEjemplaresExternosISBN(this.getNumEjemplaresExternosISBN());
        pryDuplicado.setPrecioUSDISBN(this.getPrecioUSDISBN());
        pryDuplicado.setOfertaTotalISBN(this.getOfertaTotalISBN());
        pryDuplicado.setRazonesNoComercializableISBN(this.getRazonesNoComercializableISBN());
        pryDuplicado.setTipoSoporteISBN(this.getTipoSoporteISBN());
        pryDuplicado.setDescripcionFisicaISBN(this.getDescripcionFisicaISBN());
        pryDuplicado.setTipoEncuadernacionISBN(this.getTipoEncuadernacionISBN());
        pryDuplicado.setTipoPapelISBN(this.getTipoPapelISBN());
        pryDuplicado.setGramajeISBN(this.getGramajeISBN());
        pryDuplicado.setTipoImpresionISBN(this.getTipoImpresionISBN());
        pryDuplicado.setNumPaginasISBN(this.getNumPaginasISBN());
        pryDuplicado.setNumTintasISBN(this.getNumTintasISBN());
        pryDuplicado.setAnchoISBN(this.getAnchoISBN());
        pryDuplicado.setAltoISBN(this.getAltoISBN());
        pryDuplicado.setMedioElectronicoISBN(this.getMedioElectronicoISBN());
        pryDuplicado.setFormatoISBN(this.getFormatoISBN());
        pryDuplicado.setTamañoISBN(this.getTamañoISBN());
        pryDuplicado.setUnidadMedidaTamañoISBN(this.getUnidadMedidaTamañoISBN());
        pryDuplicado.setServiciosAcademicos(this.getServiciosAcademicos());
        pryDuplicado.setJustificacionExoneracionCostosInd(this.getJustificacionExoneracionCostosInd());
        pryDuplicado.setJustificacionExoneracionTransferencias(this.getJustificacionExoneracionTransferencias());
        pryDuplicado.setProyectoUsadoNoHermes(this.getProyectoUsadoNoHermes());
        pryDuplicado.setEntidadFinancieraNoHermes(this.getEntidadFinancieraNoHermes());
        pryDuplicado.setTransferencia(this.getTransferencia());

        return pryDuplicado;
    }

    /**
     * @return the otroActoAdministrativoInterno
     */
    public String getOtroActoAdministrativoInterno() {
        return otroActoAdministrativoInterno;
    }

    /**
     * @param otroActoAdministrativoInterno
     *            the otroActoAdministrativoInterno to set
     */
    public void setOtroActoAdministrativoInterno(String otroActoAdministrativoInterno) {
        this.otroActoAdministrativoInterno = otroActoAdministrativoInterno;
    }

    public void eliminarCompromisoFinal() {
        Iterator<ProyectoCompromiso> i = getListaCompromisoInformesFinales().iterator();
        while (i.hasNext()) {
            ProyectoCompromiso proyectoCompromiso = i.next();
            this.getCompromisosProyecto().remove(proyectoCompromiso);
        }
    }

    public void eliminarCompromisoParciales() {
        Iterator<ProyectoCompromiso> i = getListaCompromisoInformesParciales().iterator();
        while (i.hasNext()) {
            ProyectoCompromiso proyectoCompromiso = i.next();
            this.getCompromisosProyecto().remove(proyectoCompromiso);
        }
    }

    public FuenteFinanciacion obtenerFirmanteConvenio() {
        Iterator<Financiacion> i = getListaEntidadesLegalizacion().iterator();
        while (i.hasNext()) {
            Financiacion f = i.next();
            if (f.getTipoEntidad().equals(Financiacion.LEGALIZACION_TIPO_FIRMANTE_CONVENIO)) {
                return f.getFuente();
            }
        }
        return null;
    }

    public Financiacion obtenerFinanciacionLegalizacion(String idFuente) {
        Iterator<Financiacion> i = getListaEntidadesLegalizacion().iterator();
        while (i.hasNext()) {
            Financiacion f = i.next();
            if (f.getFuente().getId().equals(idFuente)) {
                return f;
            }
        }
        return null;
    }

    public Financiacion obtenerFinanciacionInterna(String idFuente) {
        Iterator<Financiacion> i = getFinanciaciones().iterator();
        while (i.hasNext()) {
            Financiacion f = i.next();
            if (f.getFuente().getId().equals(idFuente)) {
                return f;
            }
        }
        return null;
    }

    public List<Gasto> getDesembolsosAdicionados() {
        List<Gasto> listaGastosDesembolso = new ArrayList<Gasto>();
        Iterator<Financiacion> i = getListaEntidadesLegalizacion().iterator();
        while (i.hasNext()) {
            Financiacion financiacion = i.next();
            listaGastosDesembolso.addAll(financiacion.getGastosDesembolso());
        }
        return listaGastosDesembolso;
    }

    public List<Gasto> getRecursosAdicionados() {
        List<Gasto> listaGastosRecursos = new ArrayList<Gasto>();
        Iterator<Financiacion> i = getFinanciaciones().iterator();
        while (i.hasNext()) {
            Financiacion financiacion = i.next();
            listaGastosRecursos.addAll(financiacion.getGastosIngresos());
        }
        return listaGastosRecursos;
    }

    public List<ProyectoProrroga> getListaProrrogas() {
        List<ProyectoProrroga> listaProrrogas = new ArrayList<ProyectoProrroga>();
        if (getProrrogasProyecto() != null) {
            Iterator<ProyectoProrroga> i = getProrrogasProyecto().iterator();
            while (i.hasNext()) {
                ProyectoProrroga proyectoProrroga = i.next();
                if (proyectoProrroga.getEstado() == null
                        || !proyectoProrroga.getEstado().equals(ProyectoProrroga.BORRADO)) {
                    listaProrrogas.add(proyectoProrroga);
                }
            }
        }
        return listaProrrogas;
    }

    public void adicionarProyectoProrroga(ProyectoProrroga prorroga) {
        if (prorrogasProyecto == null) {
            prorrogasProyecto = new HashSet<ProyectoProrroga>();
        }
        prorroga.setProyecto(this);
        prorrogasProyecto.add(prorroga);
    }

    public void limpiarCompromisos() {
        Iterator<ProyectoCompromiso> i = getListaCompromisos().iterator();
        while (i.hasNext()) {
            ProyectoCompromiso proyectoCompromiso = i.next();
            if (proyectoCompromiso.getFechaVencimiento() == null) {
                this.getCompromisosProyecto().remove(proyectoCompromiso);
            }
        }
    }

    /**
     * @return the estadoProyectoLegalizacion
     */
    public EstadoProyecto getEstadoProyectoLegalizacion() {
        if (estadoProyectoLegalizacion == null) {
            estadoProyectoLegalizacion = estadoProyecto;
        }
        return estadoProyectoLegalizacion;
    }

    /**
     * @param estadoProyectoLegalizacion
     *            the estadoProyectoLegalizacion to set
     */
    public void setEstadoProyectoLegalizacion(EstadoProyecto estadoProyectoLegalizacion) {
        this.estadoProyectoLegalizacion = estadoProyectoLegalizacion;
    }

    public boolean isMostrarFormularioLegalizacion() {
        return getEstadoProyectoLegalizacion().getId().equals(EstadoProyecto.APROBADO);
    }

    public boolean isHabilitadoParaLegalizacionExterna() {
        return this.getModalidad().isEsConvocatoriaLegalizacion()
                && this.getEstadoProyecto().isEstadoHabilitadoLegalizacion();
    }

    public boolean isHabilitadoParaFormalizacionInterna() {
        return (!this.getModalidad().isEsConvocatoriaLegalizacion() || "Y".equals(this.esJornadaDocente))
                && this.getEstadoProyecto().isEstadoHabilitadoFormalizacion();
    }

    /**
     * @return the tieneAvalesAprobados
     */
    public boolean isTieneAvalesAprobados() {
        return tieneAvalesAprobados;
    }

    /**
     * @param tieneAvalesAprobados the tieneAvalesAprobados to set
     */
    public void setTieneAvalesAprobados(boolean tieneAvalesAprobados) {
        this.tieneAvalesAprobados = tieneAvalesAprobados;
    }

    /**
     * @return the corteConvocatoria
     */
    public CorteConvocatoria getCorteConvocatoria() {
        return corteConvocatoria;
    }

    /**
     * @param corteConvocatoria the corteConvocatoria to set
     */
    public void setCorteConvocatoria(CorteConvocatoria corteConvocatoria) {
        this.corteConvocatoria = corteConvocatoria;
    }
    
    public boolean isEstHabLegalSinVersiones(){
        if(estadoProyectoLegalizacion != null && estadoProyectoLegalizacion.getId() != null){
            return EstadoProyecto.ELEGIBLE.equals(estadoProyectoLegalizacion.getId()) 
                    || EstadoProyecto.APROBADO_OCAD.equals(estadoProyectoLegalizacion.getId())
                    || EstadoProyecto.NO_APROBADO.equals(estadoProyectoLegalizacion.getId());
        }
        return false;
    }
    
    public boolean isTieneSolicitudCambioContenidoAprobada() {
        Iterator<Solicitud> i = solicitudes.iterator();
        while (i.hasNext()) {
            Solicitud solicitud = i.next();
            if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CAMBIO_CONTENIDO)
                    && StringUtils.isNotBlank(solicitud.getRespuesta())
                    && solicitud.getRespuesta().equals(Solicitud.APROBADA)) {
                return true;
            }
        }
        return false;
    }

	/**
	 * @return the marcoConceptualExtSol
	 */
	public String getMarcoConceptualExtSol()
	{
		return marcoConceptualExtSol;
	}

	/**
	 * @param marcoConceptualExtSol the marcoConceptualExtSol to set
	 */
	public void setMarcoConceptualExtSol(String marcoConceptualExtSol)
	{
		this.marcoConceptualExtSol = marcoConceptualExtSol;
	}

	/**
	 * @return the condicionesEntornoExtSol
	 */
	public String getCondicionesEntornoExtSol()
	{
		return condicionesEntornoExtSol;
	}

	/**
	 * @param condicionesEntornoExtSol the condicionesEntornoExtSol to set
	 */
	public void setCondicionesEntornoExtSol(String condicionesEntornoExtSol)
	{
		this.condicionesEntornoExtSol = condicionesEntornoExtSol;
	}

	/**
	 * @return the procesosTransferenciaExtSol
	 */
	public String getProcesosTransferenciaExtSol()
	{
		return procesosTransferenciaExtSol;
	}

	/**
	 * @param procesosTransferenciaExtSol the procesosTransferenciaExtSol to set
	 */
	public void setProcesosTransferenciaExtSol(String procesosTransferenciaExtSol)
	{
		this.procesosTransferenciaExtSol = procesosTransferenciaExtSol;
	}

	/**
	 * @return the planteamientoRolesExtSol
	 */
	public String getPlanteamientoRolesExtSol()
	{
		return planteamientoRolesExtSol;
	}

	/**
	 * @param planteamientoRolesExtSol the planteamientoRolesExtSol to set
	 */
	public void setPlanteamientoRolesExtSol(String planteamientoRolesExtSol)
	{
		this.planteamientoRolesExtSol = planteamientoRolesExtSol;
	}

	/**
	 * @return the planteamientoIndicadoresExtSol
	 */
	public String getPlanteamientoIndicadoresExtSol()
	{
		return planteamientoIndicadoresExtSol;
	}

	/**
	 * @param planteamientoIndicadoresExtSol the planteamientoIndicadoresExtSol to set
	 */
	public void setPlanteamientoIndicadoresExtSol(String planteamientoIndicadoresExtSol)
	{
		this.planteamientoIndicadoresExtSol = planteamientoIndicadoresExtSol;
	}

	/**
	 * @return the participacionComunidadExtSol
	 */
	public String getParticipacionComunidadExtSol()
	{
		return participacionComunidadExtSol;
	}

	/**
	 * @param participacionComunidadExtSol the participacionComunidadExtSol to set
	 */
	public void setParticipacionComunidadExtSol(String participacionComunidadExtSol)
	{
		this.participacionComunidadExtSol = participacionComunidadExtSol;
	}

	/**
	 * @return the atributosSolInnoExtSol
	 */
	public String getAtributosSolInnoExtSol()
	{
		return atributosSolInnoExtSol;
	}

	/**
	 * @param atributosSolInnoExtSol the atributosSolInnoExtSol to set
	 */
	public void setAtributosSolInnoExtSol(String atributosSolInnoExtSol)
	{
		this.atributosSolInnoExtSol = atributosSolInnoExtSol;
	}

	public Set getHistoricoEstLega() {
		return historicoEstLega;
	}

	public void setHistoricoEstLega(Set historicoEstLega) {
		this.historicoEstLega = historicoEstLega;
	}

	public String getLugarEspecifico() {
		return lugarEspecifico;
	}

	public void setLugarEspecifico(String lugarEspecifico) {
		this.lugarEspecifico = lugarEspecifico;
	}
	
	public String getTipoObra() {
		return tipoObra;
	}

	public void setTipoObra(String tipoObra) {
		this.tipoObra = tipoObra;
	}

	public String getTipoPublicacion() {
		return tipoPublicacion;
	}

	public void setTipoPublicacion(String tipoPublicacion) {
		this.tipoPublicacion = tipoPublicacion;
	}

	public String getIdiomaOrigen() {
		return idiomaOrigen;
	}

	public void setIdiomaOrigen(String idiomaOrigen) {
		this.idiomaOrigen = idiomaOrigen;
	}

//	public Long getNumeroEjemplaresNacional() {
//		return numeroEjemplaresNacional;
//	}
//
//	public void setNumeroEjemplaresNacional(Long numeroEjemplaresNacional) {
//		this.numeroEjemplaresNacional = numeroEjemplaresNacional;
//	}
//
//	public Long getNumeroEjemplaresExt() {
//		return numeroEjemplaresExt;
//	}
//
//	public void setNumeroEjemplaresExt(Long numeroEjemplaresExt) {
//		this.numeroEjemplaresExt = numeroEjemplaresExt;
//	}
//
//	public Long getNumeroEjemplaresTotal() {
//		return numeroEjemplaresTotal;
//	}
//
//	public void setNumeroEjemplaresTotal(Long numeroEjemplaresTotal) {
//		this.numeroEjemplaresTotal = numeroEjemplaresTotal;
//	}

	public String getTipoISBN() {
		return tipoISBN;
	}

	public void setTipoISBN(String tipoISBN) {
		this.tipoISBN = tipoISBN;
	}

	public Long getPesoGramosObra() {
		return pesoGramosObra;
	}

	public void setPesoGramosObra(Long pesoGramosObra) {
		this.pesoGramosObra = pesoGramosObra;
	}

	public String getTipoSoporteDigital() {
		return tipoSoporteDigital;
	}

	public void setTipoSoporteDigital(String tipoSoporteDigital) {
		this.tipoSoporteDigital = tipoSoporteDigital;
	}

	public String getTipoContenidoProducto() {
		return tipoContenidoProducto;
	}

	public void setTipoContenidoProducto(String tipoContenidoProducto) {
		this.tipoContenidoProducto = tipoContenidoProducto;
	}

	public String getProteccionTecnicaArchivos() {
		return proteccionTecnicaArchivos;
	}

	public void setProteccionTecnicaArchivos(String proteccionTecnicaArchivos) {
		this.proteccionTecnicaArchivos = proteccionTecnicaArchivos;
	}

	public String getPermisoUso() {
		return permisoUso;
	}

	public void setPermisoUso(String permisoUso) {
		this.permisoUso = permisoUso;
	}

	public String getResena() {
		return resena;
	}

	public void setResena(String resena) {
		this.resena = resena;
	}

	public String getAudiencia() {
		return audiencia;
	}

	public void setAudiencia(String audiencia) {
		this.audiencia = audiencia;
	}

	public String getEsCamaraColLibro() {
		return esCamaraColLibro;
	}

	public void setEsCamaraColLibro(String esCamaraColLibro) {
		this.esCamaraColLibro = esCamaraColLibro;
	}

	public String getSubTituloObraCompletaISBN() {
		return subTituloObraCompletaISBN;
	}

	public void setSubTituloObraCompletaISBN(String subTituloObraCompletaISBN) {
		this.subTituloObraCompletaISBN = subTituloObraCompletaISBN;
	}

	public Boolean getEsISBNImpreso() {
		return esISBNImpreso;
	}

	public void setEsISBNImpreso(Boolean esISBNImpreso) {
		this.esISBNImpreso = esISBNImpreso;
	}

	public Boolean getEsISBNDigital() {
		return esISBNDigital;
	}

	public void setEsISBNDigital(Boolean esISBNDigital) {
		this.esISBNDigital = esISBNDigital;
	}

	public Boolean getEsISBNIBD() {
		return esISBNIBD;
	}

	public void setEsISBNIBD(Boolean esISBNIBD) {
		this.esISBNIBD = esISBNIBD;
	}

	public String getDescripcionFisicaISBNIBD() {
		return descripcionFisicaISBNIBD;
	}

	public void setDescripcionFisicaISBNIBD(String descripcionFisicaISBNIBD) {
		this.descripcionFisicaISBNIBD = descripcionFisicaISBNIBD;
	}

	public String getTipoEncuadernacionISBNIBD() {
		return tipoEncuadernacionISBNIBD;
	}

	public void setTipoEncuadernacionISBNIBD(String tipoEncuadernacionISBNIBD) {
		this.tipoEncuadernacionISBNIBD = tipoEncuadernacionISBNIBD;
	}

	public String getTipoPapelISBNIBD() {
		return tipoPapelISBNIBD;
	}

	public void setTipoPapelISBNIBD(String tipoPapelISBNIBD) {
		this.tipoPapelISBNIBD = tipoPapelISBNIBD;
	}

	public String getGramajeISBNIBD() {
		return gramajeISBNIBD;
	}

	public void setGramajeISBNIBD(String gramajeISBNIBD) {
		this.gramajeISBNIBD = gramajeISBNIBD;
	}

	public Long getPesoGramosObraIBD() {
		return pesoGramosObraIBD;
	}

	public void setPesoGramosObraIBD(Long pesoGramosObraIBD) {
		this.pesoGramosObraIBD = pesoGramosObraIBD;
	}

	public String getTipoImpresionISBNIBD() {
		return tipoImpresionISBNIBD;
	}

	public void setTipoImpresionISBNIBD(String tipoImpresionISBNIBD) {
		this.tipoImpresionISBNIBD = tipoImpresionISBNIBD;
	}

	public Long getNumPaginasISBNIBD() {
		return numPaginasISBNIBD;
	}

	public void setNumPaginasISBNIBD(Long numPaginasISBNIBD) {
		this.numPaginasISBNIBD = numPaginasISBNIBD;
	}

	public String getNumTintasISBNIBD() {
		return numTintasISBNIBD;
	}

	public void setNumTintasISBNIBD(String numTintasISBNIBD) {
		this.numTintasISBNIBD = numTintasISBNIBD;
	}

	public Long getAnchoISBNIBD() {
		return anchoISBNIBD;
	}

	public void setAnchoISBNIBD(Long anchoISBNIBD) {
		this.anchoISBNIBD = anchoISBNIBD;
	}

	public Long getAltoISBNIBD() {
		return altoISBNIBD;
	}

	public void setAltoISBNIBD(Long altoISBNIBD) {
		this.altoISBNIBD = altoISBNIBD;
	}

	public Long getClasificacionThemaISBNNivel1() {
		return clasificacionThemaISBNNivel1;
	}

	public void setClasificacionThemaISBNNivel1(Long clasificacionThemaISBNNivel1) {
		this.clasificacionThemaISBNNivel1 = clasificacionThemaISBNNivel1;
	}

	public Long getClasificacionThemaISBNNivel2() {
		return clasificacionThemaISBNNivel2;
	}

	public void setClasificacionThemaISBNNivel2(Long clasificacionThemaISBNNivel2) {
		this.clasificacionThemaISBNNivel2 = clasificacionThemaISBNNivel2;
	}

	public Long getClasificacionThemaISBNNivel3() {
		return clasificacionThemaISBNNivel3;
	}

	public void setClasificacionThemaISBNNivel3(Long clasificacionThemaISBNNivel3) {
		this.clasificacionThemaISBNNivel3 = clasificacionThemaISBNNivel3;
	}

	public Long getClasificacionThemaISBNNivel4() {
		return clasificacionThemaISBNNivel4;
	}

	public void setClasificacionThemaISBNNivel4(Long clasificacionThemaISBNNivel4) {
		this.clasificacionThemaISBNNivel4 = clasificacionThemaISBNNivel4;
	}

	public Long getClasificacionThemaISBNNivel5() {
		return clasificacionThemaISBNNivel5;
	}

	public void setClasificacionThemaISBNNivel5(Long clasificacionThemaISBNNivel5) {
		this.clasificacionThemaISBNNivel5 = clasificacionThemaISBNNivel5;
	}

	public Long getClasificacionThemaISBNNivel6() {
		return clasificacionThemaISBNNivel6;
	}

	public void setClasificacionThemaISBNNivel6(Long clasificacionThemaISBNNivel6) {
		this.clasificacionThemaISBNNivel6 = clasificacionThemaISBNNivel6;
	}
	
	public Long getClasificacionLugarISBNNivel1() {
		return clasificacionLugarISBNNivel1;
	}

	public void setClasificacionLugarISBNNivel1(Long clasificacionLugarISBNNivel1) {
		this.clasificacionLugarISBNNivel1 = clasificacionLugarISBNNivel1;
	}

	public Long getClasificacionLugarISBNNivel2() {
		return clasificacionLugarISBNNivel2;
	}

	public void setClasificacionLugarISBNNivel2(Long clasificacionLugarISBNNivel2) {
		this.clasificacionLugarISBNNivel2 = clasificacionLugarISBNNivel2;
	}

	public Long getClasificacionLugarISBNNivel3() {
		return clasificacionLugarISBNNivel3;
	}

	public void setClasificacionLugarISBNNivel3(Long clasificacionLugarISBNNivel3) {
		this.clasificacionLugarISBNNivel3 = clasificacionLugarISBNNivel3;
	}

	public Long getClasificacionLugarISBNNivel4() {
		return clasificacionLugarISBNNivel4;
	}

	public void setClasificacionLugarISBNNivel4(Long clasificacionLugarISBNNivel4) {
		this.clasificacionLugarISBNNivel4 = clasificacionLugarISBNNivel4;
	}

	public Long getClasificacionLugarISBNNivel5() {
		return clasificacionLugarISBNNivel5;
	}

	public void setClasificacionLugarISBNNivel5(Long clasificacionLugarISBNNivel5) {
		this.clasificacionLugarISBNNivel5 = clasificacionLugarISBNNivel5;
	}

	public Long getClasificacionLugarISBNNivel6() {
		return clasificacionLugarISBNNivel6;
	}

	public void setClasificacionLugarISBNNivel6(Long clasificacionLugarISBNNivel6) {
		this.clasificacionLugarISBNNivel6 = clasificacionLugarISBNNivel6;
	}

	public Long getClasificacionLugarISBNNivel7() {
		return clasificacionLugarISBNNivel7;
	}

	public void setClasificacionLugarISBNNivel7(Long clasificacionLugarISBNNivel7) {
		this.clasificacionLugarISBNNivel7 = clasificacionLugarISBNNivel7;
	}

	public Long getClasificacionLugarISBNNivel8() {
		return clasificacionLugarISBNNivel8;
	}

	public void setClasificacionLugarISBNNivel8(Long clasificacionLugarISBNNivel8) {
		this.clasificacionLugarISBNNivel8 = clasificacionLugarISBNNivel8;
	}

	public Long getClasificacionLugarISBNNivel9() {
		return clasificacionLugarISBNNivel9;
	}

	public void setClasificacionLugarISBNNivel9(Long clasificacionLugarISBNNivel9) {
		this.clasificacionLugarISBNNivel9 = clasificacionLugarISBNNivel9;
	}

	public Long getClasificacionIdiomaISBNNivel1() {
		return clasificacionIdiomaISBNNivel1;
	}

	public void setClasificacionIdiomaISBNNivel1(Long clasificacionIdiomaISBNNivel1) {
		this.clasificacionIdiomaISBNNivel1 = clasificacionIdiomaISBNNivel1;
	}

	public Long getClasificacionIdiomaISBNNivel2() {
		return clasificacionIdiomaISBNNivel2;
	}

	public void setClasificacionIdiomaISBNNivel2(Long clasificacionIdiomaISBNNivel2) {
		this.clasificacionIdiomaISBNNivel2 = clasificacionIdiomaISBNNivel2;
	}

	public Long getClasificacionIdiomaISBNNivel3() {
		return clasificacionIdiomaISBNNivel3;
	}

	public void setClasificacionIdiomaISBNNivel3(Long clasificacionIdiomaISBNNivel3) {
		this.clasificacionIdiomaISBNNivel3 = clasificacionIdiomaISBNNivel3;
	}

	public Long getClasificacionIdiomaISBNNivel4() {
		return clasificacionIdiomaISBNNivel4;
	}

	public void setClasificacionIdiomaISBNNivel4(Long clasificacionIdiomaISBNNivel4) {
		this.clasificacionIdiomaISBNNivel4 = clasificacionIdiomaISBNNivel4;
	}

	public Long getClasificacionIdiomaISBNNivel5() {
		return clasificacionIdiomaISBNNivel5;
	}

	public void setClasificacionIdiomaISBNNivel5(Long clasificacionIdiomaISBNNivel5) {
		this.clasificacionIdiomaISBNNivel5 = clasificacionIdiomaISBNNivel5;
	}

	public Long getClasificacionPerHistoricoISBNNivel1() {
		return clasificacionPerHistoricoISBNNivel1;
	}

	public void setClasificacionPerHistoricoISBNNivel1(Long clasificacionPerHistoricoISBNNivel1) {
		this.clasificacionPerHistoricoISBNNivel1 = clasificacionPerHistoricoISBNNivel1;
	}

	public Long getClasificacionPerHistoricoISBNNivel2() {
		return clasificacionPerHistoricoISBNNivel2;
	}

	public void setClasificacionPerHistoricoISBNNivel2(Long clasificacionPerHistoricoISBNNivel2) {
		this.clasificacionPerHistoricoISBNNivel2 = clasificacionPerHistoricoISBNNivel2;
	}

	public Long getClasificacionPerHistoricoISBNNivel3() {
		return clasificacionPerHistoricoISBNNivel3;
	}

	public void setClasificacionPerHistoricoISBNNivel3(Long clasificacionPerHistoricoISBNNivel3) {
		this.clasificacionPerHistoricoISBNNivel3 = clasificacionPerHistoricoISBNNivel3;
	}

	public Long getClasificacionPerHistoricoISBNNivel4() {
		return clasificacionPerHistoricoISBNNivel4;
	}

	public void setClasificacionPerHistoricoISBNNivel4(Long clasificacionPerHistoricoISBNNivel4) {
		this.clasificacionPerHistoricoISBNNivel4 = clasificacionPerHistoricoISBNNivel4;
	}

	public Long getClasificacionPerHistoricoISBNNivel5() {
		return clasificacionPerHistoricoISBNNivel5;
	}

	public void setClasificacionPerHistoricoISBNNivel5(Long clasificacionPerHistoricoISBNNivel5) {
		this.clasificacionPerHistoricoISBNNivel5 = clasificacionPerHistoricoISBNNivel5;
	}

	public Long getClasificacionFinDidacticoISBNNivel1() {
		return clasificacionFinDidacticoISBNNivel1;
	}

	public void setClasificacionFinDidacticoISBNNivel1(Long clasificacionFinDidacticoISBNNivel1) {
		this.clasificacionFinDidacticoISBNNivel1 = clasificacionFinDidacticoISBNNivel1;
	}

	public Long getClasificacionFinDidacticoISBNNivel2() {
		return clasificacionFinDidacticoISBNNivel2;
	}

	public void setClasificacionFinDidacticoISBNNivel2(Long clasificacionFinDidacticoISBNNivel2) {
		this.clasificacionFinDidacticoISBNNivel2 = clasificacionFinDidacticoISBNNivel2;
	}

	public Long getClasificacionFinDidacticoISBNNivel3() {
		return clasificacionFinDidacticoISBNNivel3;
	}

	public void setClasificacionFinDidacticoISBNNivel3(Long clasificacionFinDidacticoISBNNivel3) {
		this.clasificacionFinDidacticoISBNNivel3 = clasificacionFinDidacticoISBNNivel3;
	}

	public Long getClasificacionFinDidacticoISBNNivel4() {
		return clasificacionFinDidacticoISBNNivel4;
	}

	public void setClasificacionFinDidacticoISBNNivel4(Long clasificacionFinDidacticoISBNNivel4) {
		this.clasificacionFinDidacticoISBNNivel4 = clasificacionFinDidacticoISBNNivel4;
	}

	public Long getClasificacionFinDidacticoISBNNivel5() {
		return clasificacionFinDidacticoISBNNivel5;
	}

	public void setClasificacionFinDidacticoISBNNivel5(Long clasificacionFinDidacticoISBNNivel5) {
		this.clasificacionFinDidacticoISBNNivel5 = clasificacionFinDidacticoISBNNivel5;
	}

	public Long getClasificacionEdadInteresISBNNivel1() {
		return clasificacionEdadInteresISBNNivel1;
	}

	public void setClasificacionEdadInteresISBNNivel1(Long clasificacionEdadInteresISBNNivel1) {
		this.clasificacionEdadInteresISBNNivel1 = clasificacionEdadInteresISBNNivel1;
	}

	public Long getClasificacionEdadInteresISBNNivel2() {
		return clasificacionEdadInteresISBNNivel2;
	}

	public void setClasificacionEdadInteresISBNNivel2(Long clasificacionEdadInteresISBNNivel2) {
		this.clasificacionEdadInteresISBNNivel2 = clasificacionEdadInteresISBNNivel2;
	}

	public Long getClasificacionEdadInteresISBNNivel3() {
		return clasificacionEdadInteresISBNNivel3;
	}

	public void setClasificacionEdadInteresISBNNivel3(Long clasificacionEdadInteresISBNNivel3) {
		this.clasificacionEdadInteresISBNNivel3 = clasificacionEdadInteresISBNNivel3;
	}

	public Long getClasificacionEdadInteresISBNNivel4() {
		return clasificacionEdadInteresISBNNivel4;
	}

	public void setClasificacionEdadInteresISBNNivel4(Long clasificacionEdadInteresISBNNivel4) {
		this.clasificacionEdadInteresISBNNivel4 = clasificacionEdadInteresISBNNivel4;
	}

	public Long getClasificacionEstiloISBNNivel1() {
		return clasificacionEstiloISBNNivel1;
	}

	public void setClasificacionEstiloISBNNivel1(Long clasificacionEstiloISBNNivel1) {
		this.clasificacionEstiloISBNNivel1 = clasificacionEstiloISBNNivel1;
	}

	public Long getClasificacionEstiloISBNNivel2() {
		return clasificacionEstiloISBNNivel2;
	}

	public void setClasificacionEstiloISBNNivel2(Long clasificacionEstiloISBNNivel2) {
		this.clasificacionEstiloISBNNivel2 = clasificacionEstiloISBNNivel2;
	}
	
	public Boolean getTipoPublicacionLibro() {
		return tipoPublicacionLibro;
	}

	public void setTipoPublicacionLibro(Boolean tipoPublicacionLibro) {
		this.tipoPublicacionLibro = tipoPublicacionLibro;
	}

	public Boolean getTipoPublicacionElect() {
		return tipoPublicacionElect;
	}

	public void setTipoPublicacionElect(Boolean tipoPublicacionElect) {
		this.tipoPublicacionElect = tipoPublicacionElect;
	}

	public Long getDisponibilidadComercializable() {
		return disponibilidadComercializable;
	}

	public void setDisponibilidadComercializable(Long disponibilidadComercializable) {
		this.disponibilidadComercializable = disponibilidadComercializable;
	}

	public Long getTipoAccesoISBNDigital() {
		return tipoAccesoISBNDigital;
	}

	public void setTipoAccesoISBNDigital(Long tipoAccesoISBNDigital) {
		this.tipoAccesoISBNDigital = tipoAccesoISBNDigital;
	}

	public Long getNumeroVolumenISBN() {
		return numeroVolumenISBN;
	}

	public void setNumeroVolumenISBN(Long numeroVolumenISBN) {
		this.numeroVolumenISBN = numeroVolumenISBN;
	}

	public String getDisponibleEnISBN() {
		return disponibleEnISBN;
	}

	public void setDisponibleEnISBN(String disponibleEnISBN) {
		this.disponibleEnISBN = disponibleEnISBN;
	}

	public Long getPrecioLibroDigitalISBN() {
		return precioLibroDigitalISBN;
	}

	public void setPrecioLibroDigitalISBN(Long precioLibroDigitalISBN) {
		this.precioLibroDigitalISBN = precioLibroDigitalISBN;
	}

	public String getTablaContenidoISBN() {
		return tablaContenidoISBN;
	}

	public void setTablaContenidoISBN(String tablaContenidoISBN) {
		this.tablaContenidoISBN = tablaContenidoISBN;
	}

	public String getTieneSelloDigitalISBN() {
		return tieneSelloDigitalISBN;
	}

	public void setTieneSelloDigitalISBN(String tieneSelloDigitalISBN) {
		this.tieneSelloDigitalISBN = tieneSelloDigitalISBN;
	}

	public Date getFechaInicioEjecucionEvento() {
		return fechaInicioEjecucionEvento;
	}

	public void setFechaInicioEjecucionEvento(Date fechaInicioEjecucionEvento) {
		this.fechaInicioEjecucionEvento = fechaInicioEjecucionEvento;
	}

	public Date getFechaFinEjecucionEvento() {
		return fechaFinEjecucionEvento;
	}

	public void setFechaFinEjecucionEvento(Date fechaFinEjecucionEvento) {
		this.fechaFinEjecucionEvento = fechaFinEjecucionEvento;
	}

	public Set<ValoresListasProyecto> getObjetivosDesarrolloSostenible() {
		return objetivosDesarrolloSostenible;
	}

	public void setObjetivosDesarrolloSostenible(Set<ValoresListasProyecto> objetivosDesarrolloSostenible) {
		this.objetivosDesarrolloSostenible = objetivosDesarrolloSostenible;
	}
	
	public String getNotaCodigoQuipu() {
		return notaCodigoQuipu;
	}

	public void setNotaCodigoQuipu(String notaCodigoQuipu) {
		this.notaCodigoQuipu = notaCodigoQuipu;
	}

	public String getSegundoCodigoQuipu() {
		return segundoCodigoQuipu;
	}

	public void setSegundoCodigoQuipu(String segundoCodigoQuipu) {
		this.segundoCodigoQuipu = segundoCodigoQuipu;
	}

	public String getCaracteristicaProyectoPrograma() {
		return caracteristicaProyectoPrograma;
	}

	public void setCaracteristicaProyectoPrograma(String caracteristicaProyectoPrograma) {
		this.caracteristicaProyectoPrograma = caracteristicaProyectoPrograma;
	}

	public Set getProyectosPrograma() {
		return proyectosPrograma;
	}

	public void setProyectosPrograma(Set proyectosPrograma) {
		this.proyectosPrograma = proyectosPrograma;
	}

	public String getCampoGenericoOcho() {
		return campoGenericoOcho;
	}

	public void setCampoGenericoOcho(String campoGenericoOcho) {
		this.campoGenericoOcho = campoGenericoOcho;
	}

	public String getCampoGenericoNueve() {
		return campoGenericoNueve;
	}

	public void setCampoGenericoNueve(String campoGenericoNueve) {
		this.campoGenericoNueve = campoGenericoNueve;
	}

	public String getCampoGenericoDiez() {
		return campoGenericoDiez;
	}

	public void setCampoGenericoDiez(String campoGenericoDiez) {
		this.campoGenericoDiez = campoGenericoDiez;
	}

	public String getCampoGenericoOnce() {
		return campoGenericoOnce;
	}

	public void setCampoGenericoOnce(String campoGenericoOnce) {
		this.campoGenericoOnce = campoGenericoOnce;
	}

	public String getPaisEvento() {
		return paisEvento;
	}

	public void setPaisEvento(String paisEvento) {
		this.paisEvento = paisEvento;
	}

	public String getGrupoDirige() {
		return grupoDirige;
	}

	public void setGrupoDirige(String grupoDirige) {
		this.grupoDirige = grupoDirige;
	}

	public String getPoblacionDirige() {
		return poblacionDirige;
	}

	public void setPoblacionDirige(String poblacionDirige) {
		this.poblacionDirige = poblacionDirige;
	}

	public Set<PoblacionObjetivoEvento> getPoblacionObjetivo() {
		return poblacionObjetivo;
	}

	public void setPoblacionObjetivo(Set<PoblacionObjetivoEvento> poblacionObjetivo) {
		this.poblacionObjetivo = poblacionObjetivo;
	}
	
	public void adicionarPoblacionObjetivo(PoblacionObjetivoEvento poe) {
		this.poblacionObjetivo.add(poe);
	}

	public void borrarPoblacionObjetivo(PoblacionObjetivoEvento poe) {
		this.poblacionObjetivo.remove(poe);
	}

	public List<PoblacionObjetivoEvento> getListaPoblacionObjetivo() {
		ArrayList<PoblacionObjetivoEvento> listaPOE = new ArrayList<PoblacionObjetivoEvento>();
		listaPOE.addAll(this.poblacionObjetivo);
		return listaPOE;
	}

	public void setListaPoblacionObjetivo(List<PoblacionObjetivoEvento> listaPOE) {
		this.poblacionObjetivo.addAll(listaPOE);
	}

	public void borrarListaPoblacionObjetivo(List<PoblacionObjetivoEvento> listaPOE) {
		for (int i = 0; i < listaPOE.size(); i++) {
			borrarPoblacionObjetivo(listaPOE.get(i));
		}
	}
	//
	
	public Set<ProyectoAreaGestionConocimiento> getAreaGestionConocimiento() {
		return areaGestionConocimiento;
	}

	public void setAreaGestionConocimiento(Set<ProyectoAreaGestionConocimiento> areaGestionConocimiento) {
		this.areaGestionConocimiento = areaGestionConocimiento;
	}
	
	public void adicionarAreaGestionConocimiento(ProyectoAreaGestionConocimiento poe) {
		this.areaGestionConocimiento.add(poe);
	}

	public void borrarAreaGestionConocimiento(ProyectoAreaGestionConocimiento poe) {
		this.areaGestionConocimiento.remove(poe);
	}
	
	public List<ProyectoAreaGestionConocimiento> getListaAreaGestionConocimiento() {
		ArrayList<ProyectoAreaGestionConocimiento> listaPOE = new ArrayList<ProyectoAreaGestionConocimiento>();
		listaPOE.addAll(this.areaGestionConocimiento);
		return listaPOE;
	}

	public void setListaAreaGestionConocimiento(List<ProyectoAreaGestionConocimiento> listaPOE) {
		this.areaGestionConocimiento.addAll(listaPOE);
	}

	public void borrarListaAreaGestionConocimiento(List<ProyectoAreaGestionConocimiento> listaPOE) {
		for (int i = 0; i < listaPOE.size(); i++) {
			borrarAreaGestionConocimiento(listaPOE.get(i));
		}
	}

	public Dependencia getDependenciaPresentacion() {
		return dependenciaPresentacion;
	}

	public void setDependenciaPresentacion(Dependencia dependenciaPresentacion) {
		this.dependenciaPresentacion = dependenciaPresentacion;
	}

	public String getSolucionAlternaExtSol() {
		return solucionAlternaExtSol;
	}

	public void setSolucionAlternaExtSol(String solucionAlternaExtSol) {
		this.solucionAlternaExtSol = solucionAlternaExtSol;
	}
	
	public String getArticulacionLineasGrupos() {
		return articulacionLineasGrupos;
	}

	public String getExperienciaDocenteCompetencias() {
		return experienciaDocenteCompetencias;
	}

	public void setExperienciaDocenteCompetencias(String experienciaDocenteCompetencias) {
		this.experienciaDocenteCompetencias = experienciaDocenteCompetencias;
	}

	public String getFormacionEstudiantesCompetencia() {
		return formacionEstudiantesCompetencia;
	}

	public void setFormacionEstudiantesCompetencia(String formacionEstudiantesCompetencia) {
		this.formacionEstudiantesCompetencia = formacionEstudiantesCompetencia;
	}

	public void setArticulacionLineasGrupos(String articulacionLineasGrupos) {
		this.articulacionLineasGrupos = articulacionLineasGrupos;
	}

	public String getAplicabilidadJustificacionExtSol() {
		return aplicabilidadJustificacionExtSol;
	}

	public void setAplicabilidadJustificacionExtSol(String aplicabilidadJustificacionExtSol) {
		this.aplicabilidadJustificacionExtSol = aplicabilidadJustificacionExtSol;
	}

	public String getAplicabilidadJustificacionAEExtSol() {
		return aplicabilidadJustificacionAEExtSol;
	}

	public void setAplicabilidadJustificacionAEExtSol(String aplicabilidadJustificacionAEExtSol) {
		this.aplicabilidadJustificacionAEExtSol = aplicabilidadJustificacionAEExtSol;
	}

	public String getCampoGenericoDoce() {
		return campoGenericoDoce;
	}

	public void setCampoGenericoDoce(String campoGenericoDoce) {
		this.campoGenericoDoce = campoGenericoDoce;
	}

	public Set<Semillero> getSemilleros() {
		return semilleros;
	}

	public void setSemilleros(Set<Semillero> semilleros) {
		this.semilleros = semilleros;
	}

	public ProyectoEditorial getFichaEditorial() {
		return fichaEditorial;
	}

	public void setFichaEditorial(ProyectoEditorial fichaEditorial) {
		this.fichaEditorial = fichaEditorial;
	}
	public String getTrabajoColaborativo() {
		return trabajoColaborativo;
	}

	public void setTrabajoColaborativo(String trabajoColaborativo) {
		this.trabajoColaborativo = trabajoColaborativo;
	}

	public Set<ProyectoEditorialTitulo> getTitulosEditorial() {
		return titulosEditorial;
	}

	public void setTitulosEditorial(Set<ProyectoEditorialTitulo> titulosEditorial) {
		this.titulosEditorial = titulosEditorial;
	}
	
	public ArrayList<ProyectoEditorialTitulo> getListaTitulosEditorial() {
		ArrayList<ProyectoEditorialTitulo> returnValue = new ArrayList<ProyectoEditorialTitulo>();
		for (ProyectoEditorialTitulo ss : getTitulosEditorial()) {
			returnValue.add(ss);
		}
		return returnValue;
	}

	public Set getLugaresEjecucion() {
		return lugaresEjecucion;
	}

	public void setLugaresEjecucion(Set lugaresEjecucion) {
		this.lugaresEjecucion = lugaresEjecucion;
	}

	public Set getDependenciasAportantes() {
		return dependenciasAportantes;
	}

	public void setDependenciasAportantes(Set dependenciasAportantes) {
		this.dependenciasAportantes = dependenciasAportantes;
	}

	public Set getEquiposAdquisicion() {
		return equiposAdquisicion;
	}

	public void setEquiposAdquisicion(Set equiposAdquisicion) {
		this.equiposAdquisicion = equiposAdquisicion;
	}

	public String getCampoGenericoTrece() {
		return campoGenericoTrece;
	}

	public void setCampoGenericoTrece(String campoGenericoTrece) {
		this.campoGenericoTrece = campoGenericoTrece;
	}

	public String getCampoGenericoCatorce() {
		return campoGenericoCatorce;
	}

	public void setCampoGenericoCatorce(String campoGenericoCatorce) {
		this.campoGenericoCatorce = campoGenericoCatorce;
	}

	public String getCampoGenericoQuince() {
		return campoGenericoQuince;
	}

	public void setCampoGenericoQuince(String campoGenericoQuince) {
		this.campoGenericoQuince = campoGenericoQuince;
	}

	public String getCampoGenericoDieciseis() {
		return campoGenericoDieciseis;
	}

	public void setCampoGenericoDieciseis(String campoGenericoDieciseis) {
		this.campoGenericoDieciseis = campoGenericoDieciseis;
	}

	public Long getMontoInicialAprobado() {
		return montoInicialAprobado;
	}

	public void setMontoInicialAprobado(Long montoInicialAprobado) {
		this.montoInicialAprobado = montoInicialAprobado;
	}

	public String getCampoGenericoCuatro() {
		return campoGenericoCuatro;
	}

	public void setCampoGenericoCuatro(String campoGenericoCuatro) {
		this.campoGenericoCuatro = campoGenericoCuatro;
	}

	public String getCampoGenericoCinco() {
		return campoGenericoCinco;
	}

	public void setCampoGenericoCinco(String campoGenericoCinco) {
		this.campoGenericoCinco = campoGenericoCinco;
	}

	public String getCampoGenericoUno() {
		return campoGenericoUno;
	}

	public void setCampoGenericoUno(String campoGenericoUno) {
		this.campoGenericoUno = campoGenericoUno;
	}

	public String getCampoGenericoDos() {
		return campoGenericoDos;
	}

	public void setCampoGenericoDos(String campoGenericoDos) {
		this.campoGenericoDos = campoGenericoDos;
	}

	public String getCampoGenericoTres() {
		return campoGenericoTres;
	}

	public void setCampoGenericoTres(String campoGenericoTres) {
		this.campoGenericoTres = campoGenericoTres;
	}

	public String getCampoGenericoSeis() {
		return campoGenericoSeis;
	}

	public void setCampoGenericoSeis(String campoGenericoSeis) {
		this.campoGenericoSeis = campoGenericoSeis;
	}

	public String getCampoGenericoSiete() {
		return campoGenericoSiete;
	}

	public void setCampoGenericoSiete(String campoGenericoSiete) {
		this.campoGenericoSiete = campoGenericoSiete;
	}

	public String getNoColeccionBiologicaPM() {
		return noColeccionBiologicaPM;
	}

	public void setNoColeccionBiologicaPM(String noColeccionBiologicaPM) {
		this.noColeccionBiologicaPM = noColeccionBiologicaPM;
	}

	public String getPermitirAvalRegaliasRequisitos() {
		return permitirAvalRegaliasRequisitos;
	}

	public void setPermitirAvalRegaliasRequisitos(String permitirAvalRegaliasRequisitos) {
		this.permitirAvalRegaliasRequisitos = permitirAvalRegaliasRequisitos;
	}

	public String getTieneEspecieAmenazada() {
		return tieneEspecieAmenazada;
	}

	public void setTieneEspecieAmenazada(String tieneEspecieAmenazada) {
		this.tieneEspecieAmenazada = tieneEspecieAmenazada;
	}

	public String getTieneAcuicultura() {
		return tieneAcuicultura;
	}

	public void setTieneAcuicultura(String tieneAcuicultura) {
		this.tieneAcuicultura = tieneAcuicultura;
	}

	public Integer getComponentePlanDesarrollo() {
		return componentePlanDesarrollo;
	}

	public void setComponentePlanDesarrollo(Integer componentePlanDesarrollo) {
		this.componentePlanDesarrollo = componentePlanDesarrollo;
	}

}
