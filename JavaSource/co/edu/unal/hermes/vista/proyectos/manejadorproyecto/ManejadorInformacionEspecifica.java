/**
 * @author  Ing Hernán Darío Bernal Parra
 */

package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.ActividadObjetivo;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CreacionArtistica;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DependenciaAportante;
import co.edu.unal.hermes.modelo.DependenciaAreaResponsabilidad;
import co.edu.unal.hermes.modelo.Dominio;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.FormularioInformacionEspecifica;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.IdDominioDetalle;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PoblacionObjetivoEvento;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoAreaGestionConocimiento;
import co.edu.unal.hermes.modelo.ProyectoEquipoAdquisicion;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.ResultadoProyecto;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.SesionCurso;
import co.edu.unal.hermes.modelo.TipoInvestigacion;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.TrayectoriaColeccionProyecto;
import co.edu.unal.hermes.modelo.VAsignaturasSIA;
import co.edu.unal.hermes.modelo.ValoresListasProyecto;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleProyectos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioEnsayoAcreditacion;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioVista;
import co.edu.unal.hermes.modelo.laboratorios.PersonaLaboratorio;
import co.edu.unal.hermes.vista.Error;
import co.edu.unal.hermes.vista.laboratorios.ManejadorUtilidadesLaboratorios;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorInformacionEspecifica extends ManejadorProyecto {

	private static final long serialVersionUID = 1L;
	private CreacionArtistica creacionArtistica;
	private boolean esProgramaNacional = false;
	private Convocatoria convocatoriaActual;
	private boolean esExtensionSolidaria = false;
	private Long ID_CONVOC_POSG = 229L;

	// Asignaturas

	protected List<VAsignaturasSIA> listaAsignaturas;
	protected String codigoAsignatura;
	protected List<VAsignaturasSIA> asignaturasEncontradas;
	protected Boolean cambiosEnAsignaturas;
	protected VAsignaturasSIA vAsignaturasSIAseleccionada;
	protected VAsignaturasSIA laboratorioDetalleDocenciaSeleccionado;
	protected boolean mostrarAsignaturasEncontradas = false;
	protected boolean mostrarAsignaturaSeleccionada = false;
	private SelectItem[] dependenciaItem;
	protected boolean esConvFichaMinima = false;
	protected boolean esConvFichaMinimaPosgrados = false;
	protected boolean esConvFichaMinimaPosgradosMod3 = false;
	private boolean mostrarSiConvocatoriaEventos = false;
	protected boolean materiasSIA = false;
	private boolean esPosgrado1_2 = false;
	protected boolean verFichaMin = false;
	private List cortes;
	private String dependenciaId;
	private Dependencia dependenciaActual;
	private String mensajeError;
	private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada;

	// Ficha Min
	private String justificacionLabel = "Justificacion";
	private String propiedadIntectualLabel = "Propiedad Intelectual";
	private boolean esConvIni = false;
	private boolean esConvPurdue = false;
	private boolean esConvDere = false;
	private boolean esProyectoInnoModDos = false;
	private boolean esProyectoInnoModDosUn = false;
	private boolean esConvCienciasAgraEquipos2015 = false;
	private boolean esConvBejarano = false;
	private List listaDependencia;
	private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad;
	private boolean esEventosModUno = false;
	private boolean esEventosModDos = false;
	private boolean esEventosModUno_2017 = false;
	private boolean esEventosModDos_2017 = false;
	private boolean esEventosModUno_2019 = false;
	private boolean esEventosModDos_2019 = false;

	private boolean esEventosHumanasModUno = false;
	private boolean esEventosHumanasModDos = false;
	private boolean esEventosFIAModUno = false;
	private boolean esEventosFIAModDos = false;
	private boolean esEventosMinasModUno = false;
	private boolean esEventosMinasModDos = false;
	private boolean esConvMoots2019 = false;

	private boolean mostrarSiEventos = false;
	private boolean mostrarSiEscuelaInternacional = false;
	private boolean mostrarSiFichaMinima = false;
	private boolean esConvAdmonMan = false;
	private boolean esConvSemillero = false;
	private boolean esTiempoVolver = false;
	private boolean esFichaExterna = false;
	private boolean esCorredorTecnologico = false;
	private boolean esConvocatoriaMedicina2015 = false;
	private boolean esConvCamposGenericos = false;
	private boolean esConvocatoriaSue2017 = false;
	private boolean esConvocatoriaRedes = false;
	private List<Grupo> listaGruposConvMedicina;
	private List<GruposExternosCorredor> listaGruposCorredor;
	private String nombreGrupoCCT;
	private String entidadGrupoCCT;

	// escuela internacional
	private Long numeroSesion;
	private String temaSesion;
	private String contenidoSesion;
	private List<SesionCurso> listaSesiones;
	public SesionCurso sesionSeleccionada;
	private String criterioEvaluacion;
	private String ponderacionEvaluacion;
	private List<CriterioEvaluacionEscuelaInternacional> criteriosEscuela;
	private CriterioEvaluacionEscuelaInternacional criterioSeleccionado;
	private Grupo grupoSeleccionado;
	private List<Grupo> listaGrupos;
	private List<Laboratorio> listaLaboratorios;
	private SelectItem[] gruposInvItem;
	private SelectItem[] laboratoriosFacItem;

	private boolean esConvJI_SEM = false;
	private boolean esConvSem_Col = false;
	private boolean esConvAuxPos = false;
	private boolean esConvJI_2014 = false;
	private boolean esConvArtes = false;
	private boolean esConvArtes2015 = false;
	private boolean esConvUnijus = false;
	private boolean esConvMan2014 = false;
	private boolean esConvIEU = false;
	private List<Ciudad> listaCiudades;
	private SelectItem[] ciudadItem;
	private boolean esCorredorTecnologicoExterno = false;
	private GruposExternosCorredor grupoCCTSeleccionado;
	private Ciudad ciudadSeleccionada;
	private List<Ciudad> listaCiudadesCCT;
	private String ciudadCCT;
	private SelectItem[] listaObjetivos;
	private ObjetivoEspecifico objetivo;
	private SelectItem[] listaResultados;
	private ResultadoProyecto resultado;
	private SelectItem[] listaParticipantes;
	private InvestigadorProyecto invParticipante;
	private boolean esConvMan2015 = false;
	private boolean esConvArqMed2015 = false;
	private boolean esConvCienciasMed2015 = false;
	private boolean esConvGruposCaribe2015 = false;
	private boolean esConvInnoPedagoBog2015 = false;
	private boolean esConvoOFB2015_MOD_1_2 = false;
	private boolean esConvoOFB2015_MOD_3_4 = false;
	private boolean esConvoBiotecManizales2016 = false;
	private boolean esConvoHumanasManizales2016 = false;
	private boolean esConvoIEU2016 = false;
	private boolean esConvCienciasBasicasMan2016 = false;

	// Programa Laboratorios
	private boolean esProyectoLaboratorios = false;
	private boolean esProyectoLaboratoriosMod2 = false;
	private boolean esCoordinadorLaboratorio = false;
	private boolean esLaboratoriosNacional = false;
	private boolean esLaboratoriosSede = false;
	private boolean esLaboratoriosFacultad = false;
	private boolean esLaboratoriosDepto = false;
	private List<Laboratorio> laboratoriosCoordinador;

	private boolean esConvocatoriaColecciones = false;
	private String DOMINIO_AREA_TEMATICA_CONV_BIO_MAN = "AREA_TEMATICA_CONV_BIO_MAN";
	private final String AREA_TEMATICA_CONV_CIENCIAS_BASICAS = "AREA_TEMATICA_CONV_CIENCIAS_BASICAS";
	private final String AREA_TEMATICA_CONV_CIENCIAS_SOCIALES = "AREA_TEMATICA_CONV_CIENCIAS_SOCIALES";
	private final String AREA_TEMATICA_CONV_INVESTIGACION_APLICADA = "AREA_TEMATICA_CONV_INV_APLICADA";
	private String DOMINIO_AREA_TEMATICA_CONV_HUM_MAN = "AREA_TEMATICA_CONV_HUM_MAN";
	private String DOMINIO_AREA_TEMATICA_CONV_IEU = "LINEAS_TEMATICAS_CONV1_IEU_2016";
	private String DOMINIO_TRAYECTORIA_COLECCION = "TRAYECTORIA_COLECCION";
	private String DOMINIO_AREA_GESTION_CONOCIMIENTO = "DOMINIO_AREA_GESTION_CONOCIMIENTO";
	private SelectItem[] listaAreaTematicaConvsMan2016;
	private boolean esConvMaestrosArte2015 = false;
	private List<ActividadObjetivo> listaObjetivosResultados;
	private ActividadObjetivo objetivoSeleccionado;
	private String idInvPry;
	private String idObj;
	private String idRes;
	private boolean esConvTraslaMed2016 = false;
	private boolean esConvTraslaMed2016ModDos = false;
	private boolean esConvRepotenciacionLab2018 = false;

	// FORMULARIO INFORMACION ESPECIFICA
	private String titulo1;
	private String titulo2;
	private String titulo3;
	private String titulo4;
	private String titulo5;
	private String titulo6;
	private String titulo7;
	private String titulo8;
	private String titulo9;
	private String titulo10;
	private String titulo11;
	private String titulo12;
	private String titulo13;
	private String titulo14;
	private String titulo15;
	private String titulo16;
	private boolean mostrarTitulo1 = false;
	private boolean mostrarTitulo2 = false;
	private boolean mostrarTitulo3 = false;
	private boolean mostrarTitulo4 = false;
	private boolean mostrarTitulo5 = false;
	private boolean mostrarTitulo6 = false;
	private boolean mostrarTitulo7 = false;
	private boolean mostrarTitulo8 = false;
	private boolean mostrarTitulo9 = false;
	private boolean mostrarTitulo10 = false;
	private boolean mostrarTitulo11 = false;
	private boolean mostrarTitulo12 = false;
	private boolean mostrarTitulo13 = false;
	private boolean mostrarTitulo14 = false;
	private boolean mostrarTitulo15 = false;
	private boolean mostrarTitulo16 = false;
	private boolean mostarAsignatura = false;
	private boolean mostrarObjResulResp = false;
	private boolean mostrarLugarEjecucion = false;
	private boolean mostrarGrupos = false;
	private boolean mostrarMarcoTeorico = false;
	private boolean mostrarListaSeleccion = false;
	private String textoListaSeleccion;
	private String dominioListaSeleccion;
	private boolean mostrarListaSeleccionDos = false;
	private String textoListaSeleccionDos;
	private String dominioListaSeleccionDos;
	private SelectItem[] listaSeleccionDos;
	private boolean mostrarTitulo1Obligatorio = false;
	private boolean mostrarTitulo2Obligatorio = false;
	private boolean mostrarTitulo3Obligatorio = false;
	private boolean mostrarTitulo4Obligatorio = false;
	private boolean mostrarTitulo5Obligatorio = false;
	private boolean mostrarTitulo6Obligatorio = false;
	private boolean mostrarTitulo7Obligatorio = false;
	private boolean mostrarTitulo8Obligatorio = false;
	private boolean mostrarTitulo9Obligatorio = false;
	private boolean mostrarTitulo10Obligatorio = false;
	private boolean mostrarTitulo11Obligatorio = false;
	private boolean mostrarTitulo12Obligatorio = false;
	private boolean mostrarTitulo13Obligatorio = false;
	private boolean mostrarTitulo14Obligatorio = false;
	private boolean mostrarTitulo15Obligatorio = false;
	private boolean mostrarTitulo16Obligatorio = false;
	private boolean mostrarListaSel1Obligatorio = false;
	private boolean mostrarListaSel2Obligatorio = false;
	private UIComponent proyectoActualTitulo1;
	private UIComponent proyectoActualJustificacion;
	private UIComponent proyectoActualMetodologia;
	private UIComponent proyectoActualTitulo4;
	private UIComponent proyectoActualTitulo5;
	private UIComponent proyectoActualTitulo6;
	private UIComponent proyectoActualTitulo7;
	private UIComponent proyectoActualTitulo7a;
	private UIComponent proyectoActualTitulo8;
	private UIComponent proyectoActualTitulo9;
	private UIComponent proyectoActualTitulo10;
	private UIComponent proyectoActualTitulo11;
	private UIComponent proyectoActualTitulo12;
	private UIComponent proyectoActualTitulo13;
	private UIComponent proyectoActualTitulo14;
	private UIComponent proyectoActualTitulo15;
	private UIComponent proyectoActualTitulo16;
	private UIComponent proyectoActualTitulo8Eventos;
	private UIComponent idListaSelUno;
	private UIComponent idListaSelDos;
	private boolean mostrarListaSeleccionMultiple = false;
	private String textoListaSeleccionMultiple;
	private boolean mostrarListaSeleccionMultiple2 = false;
	private String valorListaProyectoParametrizado2;
	private List<ValoresListasProyecto> valoresListasProyectosParametrizado2;
	private SelectItem[] listaSeleccionMultipleValoresProyecto2;
	private ValoresListasProyecto valorListaProyectoSeleccionadaParametrizada2;
	private String textoListaSeleccionMultiple2;
	private Long idLaboratorio;
	private List<LaboratorioDetalleProyectos> listaLaboratoriosProyecto;
	private List<LaboratorioDetalleProyectos> listaLaboratoriosProyectosBorrados;
	private ManejadorUtilidadesLaboratorios mUL;
	private LaboratorioDetalleProyectos laboratorioSeleccionado;
	private LaboratorioVista laboratorioVistaSeleccionado;
	private List<LaboratorioEnsayoAcreditacion> listaEnsayosAcredicion;
	private LaboratorioEnsayoAcreditacion ensayoAcreditacionSeleccionado;
	private List<LaboratorioEnsayoAcreditacion> listaEnsayosAcredicionBorrados;
	private String nombreEnsayo;
	private String areaAcreditacion;
	private String documentoReferencia;
	private String idTrayectoriaColeccion;
	private String descripcionTrayectoriaColeccion;
	private List<TrayectoriaColeccionProyecto> listaTrayectoriaColeccion;
	private List<TrayectoriaColeccionProyecto> listaTrayectoriaColeccionBorrados;
	private TrayectoriaColeccionProyecto trayectoriaColeccionSeleccionada;
	private SelectItem[] listaSeleccionTrayectoriaColeccion;

	private boolean esConvProyectos2016_2018 = false;
	private boolean esConvProyectos2017_2018 = false;

	private List<ValoresListasProyecto> listaGruposSUE;
	private List<ValoresListasProyecto> listaGruposSUEBorrados;
	private ValoresListasProyecto grupoSUESeleccionado;

	private List<ValoresListasProyecto> valoresListasProyectos;
	private List<ValoresListasProyecto> valoresListasProyectosBorrados;
	private String dominioValoresListaProyectos;
	private String dominioValoresListaSelMul2;
	private SelectItem[] listaSeleccionValoresProyecto;
	private String valorListaProyecto;
	private ValoresListasProyecto valorListaProyectoSeleccionada;
	private String tituloGruposInvestigacion;
	protected boolean esConvocatoriaAlianzas2018 = false;
	protected boolean esConvocatoriaAlianzas2019 = false;
	private boolean mostrarLabConv = false;
	private Long numLabsVal;
	private int numRegLab = 0;
	protected boolean esConvSedesPreNal2019 = false;
	protected boolean esConvProyectosBogota2019 = false;
	protected boolean esConvCentroPensamiento2019 = false;
	protected boolean esConvUnInnova2019 = false;

	protected boolean esConvCP2019 = false;
	private SelectItem[] listaCentrosPensamiento;
	private UIComponent proyectoActualTitulo1_CP;
	private UIComponent proyectoActualJustificacion_CP;
	private UIComponent proyectoActualMetodologia_CP;
	private UIComponent proyectoActualTitulo4_CP;
	private UIComponent proyectoActualTitulo5_CP;
	private UIComponent proyectoActualTitulo6_CP;
	private UIComponent proyectoActualTitulo7_CP;
	private UIComponent proyectoActualTitulo8_CP;
	private UIComponent proyectoActualTitulo9_CP;
	private UIComponent proyectoActualTitulo10_CP;
	private UIComponent proyectoActualTitulo11_CP;
	private UIComponent proyectoActualTitulo12_CP;
	private boolean mostrarObjResulRespObligatorio = false;

	private boolean mostrarlistaSeleccionMultiplObligatorio = false;

	private String[] selectedAreaGestionConocimiento;
	private SelectItem[] areaGestionConocimientoItem;
	
	//Para equipos y servicios nuevos
	private String equipoNuevo;
	private String ubicacionEquipo;
	private String responsableEquipo;
	private String servicioEquipo;
	private String justificacionEquipo;
	private Long valorEquipo;
	private ProyectoEquipoAdquisicion equipoSeleccionado;

	public ManejadorInformacionEspecifica() {
		super();
		cargarConvocatoriaActual();
		numeroSesion = 1L;
		listaSesiones = new ArrayList<SesionCurso>();
		idManejador = INFO_ESPECIFICA;

		titulo1 = "Proyecto:";
		titulo2 = "Búsqueda de Integrantes del Proyecto";
		titulo3 = "Metodología/Estrategias";
		tituloGruposInvestigacion = "Grupos de investigación";

		selectedAreaGestionConocimiento = new String[11];

		dependenciaAreaResponsabilidad = new DependenciaAreaResponsabilidad();
		criteriosEscuela = new ArrayList<CriterioEvaluacionEscuelaInternacional>();
		listaGruposConvMedicina = new ArrayList<Grupo>();
		listaGruposCorredor = new ArrayList<GruposExternosCorredor>();
		listaCiudadesCCT = new ArrayList<Ciudad>();
		listaObjetivosResultados = new ArrayList<ActividadObjetivo>();

		listaDependencia = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new Dependencia(), "nombre");

		dependenciaItem = new SelectItem[listaDependencia.size()];
		for (int i = 0; i < listaDependencia.size(); i++) {
			Dependencia d = (Dependencia) listaDependencia.get(i);
			String nombre = d.getNombre();
			if (nombre.length() > 50) {
				nombre = nombre.substring(0, 50) + "...";
			}
			dependenciaItem[i] = new SelectItem(d.getId(), nombre);
		}
		dependenciaId = ((Dependencia) listaDependencia.get(0)).getId();
		dependenciaActual = (Dependencia) listaDependencia.get(0);

		listaDependencia = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new Dependencia(), "nombre");

		proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(),
				ProyectoDAOHibernate.LINEAS_DEPENDENCIAS_UNICO);

		if ((proyectoActual.getModalidad().getTipo().getId().compareTo("CCT") == 0
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("CFM") == 0
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("CEQ") == 0
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("CMP") == 0
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("CPU") == 0
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("CJI") == 0
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("CTV") == 0
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("FMH") == 0
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("CED") == 0
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("RPL") == 0
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("RFE") == 0)) {
			esConvFichaMinima = true;
			justificacionLabel = "Aportes para el proceso de formación para la investigación, creación o innovación de los estudiantes";
			if (proyectoActual.getModalidad() instanceof Convocatoria) {
				System.out.println("Materias SIA");
				RestriccionConvocatoria r = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();
				if (r != null) {
					System.out.println(r.getId());
					if (r.getId().equals("MAT_SIA")) {
						materiasSIA = true;
						esConvSemillero = true;
					}

					if (r.getId().equals("TIEMPO_VOLVER")) {
						if (proyectoActual.getModalidad().getId().compareTo(10L) == 0) {
							esFichaExterna = true;
							esConvFichaMinima = false;
							justificacionLabel = "Impacto ambiental";
							propiedadIntectualLabel = "Consideraciones éticas";
						} else {
							esTiempoVolver = true;
							justificacionLabel = "Estado del arte";
							cargarGruposInvestigacion();
						}

					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_REP_EQU_LAB)) {
						esConvRepotenciacionLab2018 = true;
						tituloGruposInvestigacion = "Grupos de investigación que han utilizado el equipo";
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_FM_GENERICO)) {
						esConvCamposGenericos = true;
						justificacionLabel = "Justificación";
						titulo3 = "Metodología";
					}

					if (r.getId().equals("CON_COR_TEC_AGRO")) {
						esCorredorTecnologico = true;
						esCorredorTecnologicoExterno = (Boolean) sesion.getAttribute("esCorredorTecnologico");
						justificacionLabel = "Justificación.";
						titulo3 = "Propuesta técnica y metodológica";
						cargarGruposInvestigacion();
						cargarCiudades();
						listaGruposConvMedicina.addAll(proyectoActual.getGrupos());
						listaCiudadesCCT.addAll(proyectoActual.getCiudades());
						if (esCorredorTecnologicoExterno) {
							if (proyectoActual.getImpactoEsperado() != null
									&& !proyectoActual.getImpactoEsperado().equals("")) {
								obtenerGruposCCT();
							}

						}
					}

					if (r.getId().equals("CONV_SUE_2017")) {
						esConvocatoriaSue2017 = true;
						listaSeleccionDos = crearListaItemDominioDetalle("UNIVERSIDADES_SUE_2017");
						listaGruposSUE = new ArrayList<ValoresListasProyecto>();
						listaGruposSUEBorrados = new ArrayList<ValoresListasProyecto>();
						cargarGruposSUEProyecto();
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_ADMON_MAN_2015)) {
						esConvMan2015 = true;
						cargarObjetivos();
						cargarResultados();
						cargarParticipantes();
						listaObjetivosResultados.addAll(proyectoActual.getObjetivosResultados());
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_GRUPOS_ARQUITECTURA_MEDELLIN)) {
						esConvArqMed2015 = true;
						justificacionLabel = "Aportes y articulación de las líneas de investigación del grupo al proceso de formación para la investigación, creación o innovación de los programas de la Facultad de Arquitectura";
						titulo3 = "Áreas estratégicas de desarrollo del conocimiento";
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_GRU_CIEN_MED)) {
						esConvCienciasMed2015 = true;
						justificacionLabel = "Alcance de la propuesta del grupo de investigación en relación con los términos de referencia de la convocatoria";
						titulo3 = "Impacto de la propuesta para el grupo de investigación";
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_FCH_OFB_1)
							|| r.getId().equals(RestriccionConvocatoria.CONV_FCH_OFB_2)) {
						esConvoOFB2015_MOD_1_2 = true;
						justificacionLabel = "Aportes del proyecto al proceso de formación de los estudiantes";
						titulo3 = "Estrategia metodológica";
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_FCH_OFB_3)
							|| r.getId().equals(RestriccionConvocatoria.CONV_FCH_OFB_4)) {
						esConvoOFB2015_MOD_3_4 = true;
						mostrarTitulo4 = true;
						mostrarTitulo5 = true;
						justificacionLabel = "Aportes del proyecto al proceso de formación de los estudiantes";
						titulo3 = "Describa la visión y proyección del grupo en los próximos 8 años";
						titulo4 = "Estrategia para la consolidación de las líneas de investigación que trabaja el grupo";
						titulo5 = "Estrategia metodológica";
					}

					if (r.getId().equals(RestriccionConvocatoria.CON_BIO_MAN_2016)) {
						esConvoBiotecManizales2016 = true;
						justificacionLabel = "Entidad del sector estatal, social o productivo";
						titulo3 = "Metodología a seguir";
						cargarAreaTemConvBioMan2016();
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_MAN_CIEN_BA)) {
						esConvCienciasBasicasMan2016 = true;
						justificacionLabel = "Entidad del sector estatal, social o productivo";
						titulo3 = "Metodología a seguir";
						cargarAreaTemConvCienBasMan2016();
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_MAN_CIEN_SO)) {
						esConvCienciasBasicasMan2016 = true;
						cargarAreaTemConvCienSocMan2016();
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_MAN_INV_APL)) {
						esConvCienciasBasicasMan2016 = true;
						cargarAreaTemConvInvAplicaMan2016();
					}

					if (r.getId().equals(RestriccionConvocatoria.CON_HUM_MAN_2016)) {
						esConvoHumanasManizales2016 = true;
						titulo3 = "Metodología a seguir";
						cargarAreaTemConvHumMan2016();
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_IEU_1_2016)) {
						esConvoIEU2016 = true;
						cargarAreaTemConvIEU2016();
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_MAESTROS_ART)) {
						esConvMaestrosArte2015 = true;
						mostrarTitulo4 = true;
						justificacionLabel = "Aportes de la recuperación de la memoria de los profesores de la facultad de artes";
						titulo3 = "Información de colección de libros del docente maestro";
						titulo4 = "Consideraciones éticas";
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_LABORATORIOS)
							|| r.getId().equals(RestriccionConvocatoria.CONV_LABORATORIOS_MOD_2)
							|| r.getId().equals(RestriccionConvocatoria.CONV_REP_EQU_LAB)) {
						esProyectoLaboratorios = true;
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_LABORATORIOS_MOD_2)) {
						esProyectoLaboratoriosMod2 = true;
						listaEnsayosAcredicion = new ArrayList<LaboratorioEnsayoAcreditacion>();
						listaEnsayosAcredicionBorrados = new ArrayList<LaboratorioEnsayoAcreditacion>();
						String hqlLab = "select #id e.id, #nombreEnsayo e.nombreEnsayo, #areaAcreditacion e.areaAcreditacion, #documentoReferencia e.documentoReferencia from LaboratorioEnsayoAcreditacion e where e.proyecto = "
								+ proyectoActual.getId();
						listaEnsayosAcredicion = servicioGeneral
								.obtenerObjetosLimitado(LaboratorioEnsayoAcreditacion.class, hqlLab);
					}

					if (esProyectoLaboratorios || esProyectoLaboratoriosMod2) {
						// esCoordinadorLaboratorio = (Boolean)
						// sesion.getAttribute("esCoordinadorLaboratorio");
						listaLaboratoriosProyecto = new ArrayList<LaboratorioDetalleProyectos>();
						listaLaboratoriosProyectosBorrados = new ArrayList<LaboratorioDetalleProyectos>();
						mUL = new ManejadorUtilidadesLaboratorios();
						String hqlLab = "select #id e.id, #laboratorio e.laboratorio from LaboratorioDetalleProyectos e where e.proyecto = "
								+ proyectoActual.getId();
						listaLaboratoriosProyecto = servicioGeneral
								.obtenerObjetosLimitado(LaboratorioDetalleProyectos.class, hqlLab);
						cargarLaboratorios();
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_COLECCIONES)) {
						esConvocatoriaColecciones = true;
						listaTrayectoriaColeccion = new ArrayList<TrayectoriaColeccionProyecto>();
						listaTrayectoriaColeccionBorrados = new ArrayList<TrayectoriaColeccionProyecto>();
						// String hqlLab =
						// "select #id e.id, #nombre e.nombre from Coleccion e where e.proyecto = "
						// + proyectoActual.getId();
						String hqlTray = "select #id e.id, #trayectoria e.trayectoria, #descripcion e.descripcion, #proyecto e.proyecto from TrayectoriaColeccionProyecto e where e.proyecto = "
								+ proyectoActual.getId();
						listaTrayectoriaColeccion = servicioGeneral
								.obtenerObjetosLimitado(TrayectoriaColeccionProyecto.class, hqlTray);
						cargarTrayectoriaColecciones();
						cargarListaColecciones();
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_GRU_CARIBE)) {
						esConvGruposCaribe2015 = true;
						justificacionLabel = "Visión y proyección del grupo de acuerdo al aporte de la propuesta";
						titulo3 = "Impacto de la propuesta para el grupo de investigación";
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_INNO_PEDAGOGICA_BOG_2015)) {
						esConvInnoPedagoBog2015 = true;
						justificacionLabel = "Descripción detallada de la innovación pedagógica y su(s) propósito(s)";
						titulo3 = "Descripción detallada del tipo de estudio y la metodología de investigación";
					}

					if (r.getId().equals("CONV_INI")) {
						esConvIni = true;

					}

					if (r.getId().equals("CONV_IEU")) {
						esConvIEU = true;
						justificacionLabel = "Aportes de la propuesta al grupo de investigación";
						titulo3 = "Metodología";

					}

					if (r.getId().equals("CONV_PRD")) {
						esConvPurdue = true;

					}

					if (r.getId().equals("CONV_TRAS_MED")) {
						esConvTraslaMed2016 = true;
						justificacionLabel = "Entidades externas (Empresa privada) o internas (facultades) que realicen aportes económicos en efectivo al proyecto, así como el valor de los mismos";
						cargarGruposInvestigacion();

						listaGruposConvMedicina.addAll(proyectoActual.getGrupos());
					}

					if (r.getId().equals("CONV_TRAS_MED_MOD2")) {
						esConvTraslaMed2016ModDos = true;
						justificacionLabel = "Desarrollos aplicados al funcionamiento del Hospital Universitario";
						cargarGruposInvestigacion();

						listaGruposConvMedicina.addAll(proyectoActual.getGrupos());
					}

					if (r.getId().equals("CONV_MED_POSD")) {
						esConvocatoriaMedicina2015 = true;
						justificacionLabel = "Aporte del proceso para las líneas de investigación del grupo.";
						cargarGruposInvestigacion();

						listaGruposConvMedicina.addAll(proyectoActual.getGrupos());
					}
					if (r.getId().equals("CONV_DER")) {
						esConvDere = true;
					}

					if (r.getId().equals("FMHP")) {
						esConvAuxPos = true;

					}

					if (r.getId().equals("CONV_UNIJUS_2014")) {
						esConvUnijus = true;
						justificacionLabel = "Justificación.";
					}

					if (r.getId().equals("CONV_SEM_COL") || r.getId().equals("CONV_JI_COL")) {
						esConvJI_SEM = true;
					}

					if (r.getId().equals("CONV_CONJ_MAN")) {
						esConvMan2014 = true;
						justificacionLabel = "Aporte al conocimiento en el área de investigación e impacto regional.";
					}

					if (r.getId().equals("CONV_SEM_COL")) {
						esConvSem_Col = true;
					}

					if (r.getId().equals("CONV_ART_2014") || r.getId().equals("CONV_ART_2014_MD")) {
						esConvArtes = true;
						if (r.getId().equals("CONV_ART_2014")) {
							justificacionLabel = "Aportes y articulación de las lineas de investigación del grupo el proceso de formación para la investigación, creación o innovación de los estudiantes";
						}
					}

					if (r.getId().equals("CONV_ART_2015_2") || r.getId().equals("CONV_ART_2015_2_MD")) {
						esConvArtes2015 = true;
						if (r.getId().equals("CONV_ART_2015_2")) {
							justificacionLabel = "Aportes y articulación de las lineas de investigación del grupo el proceso de formación para la investigación, creación o innovación de los estudiantes";
						}
					}

					if (r.getId().equals("CONV_JI_COL_2014")) {
						esConvJI_2014 = true;
						justificacionLabel = "Plan de formación - Beneficios y estrategias del grupo para el proceso de formación del joven investigador.";
					}

					if (r.getId().equals("FM")) {
						esConvSemillero = true;

					}

					if (r.getId().equals("CONV_ADMON_MAN")) {
						esConvAdmonMan = true;
						justificacionLabel = "Aportes para el proceso de formación para la investigación, creación o innovación del grupo";
					}

					if (r.getId().equals("CONV_INNO") || r.getId().equals("CONV_INNO_MOD_2_3")) {
						esProyectoInnoModDos = true;
					}

					if (r.getId().equals("CONV_INNO_MOD_2")) {
						esProyectoInnoModDosUn = true;
						justificacionLabel = "Nombre de la entidad que está financiando el proyecto";
					}

					if (r.getId().equals("CONV_BEJARANO")) {
						esConvBejarano = true;
						justificacionLabel = "Aportes e impacto del desarrollo del proyecto";
					}

					if (r.getId().equals("CONV_CA_EQUIPOS")) {
						esConvCienciasAgraEquipos2015 = true;
						justificacionLabel = "Aporte para el programa de posgrado";
						cargarLaboratoriosFacultad();
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_ALI_NAL_2018)) {
						esConvocatoriaAlianzas2018 = true;
					}

					if (r.getId().equals(RestriccionConvocatoria.CONV_ALI_NAL_2019)) {
						esConvocatoriaAlianzas2019 = true;
					}
					if (r.getId().equals(RestriccionConvocatoria.SEDES_PRES_NAL_2019)) {
						esConvSedesPreNal2019 = true;
					}
					if (r.getId().equals(RestriccionConvocatoria.CONV_CP_NUEVOS_2019)) {
						esConvCentroPensamiento2019 = true;
					}
					if (r.getId().equals(RestriccionConvocatoria.CONV_PRY_BOG_2019)) {
						esConvProyectosBogota2019 = true;
					}
					if (r.getId().equals(RestriccionConvocatoria.CONV_UNNOVA_19)) {
						esConvUnInnova2019 = true;
					}
					if (r.getId().equals(RestriccionConvocatoria.CONV_CP_2019)) {
						setEsConvCP2019(true);
					}
					if(r.getId().equals(RestriccionConvocatoria.CONV_REDES)) {
						esConvocatoriaRedes = true;
					}
				}
				if (proyectoActual.getModalidad().getId().equals(931L)) {
					tituloGruposInvestigacion = "Grupos de investigación o creación artística que han utilizado los servicios ofrecidos por el Laboratorio en los últimos dos años en el que estuvo en funcionamiento";
				}
				if (((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(513L)) {
					esConvProyectosBogota2019 = true;
				}
			} else {
			}
		}

		if (proyectoActual.getModalidad().getTipo().getId().compareTo("CFM") == 0
				&& ((Convocatoria) proyectoActual.getModalidad()).getRestriccion() != null
				&& (((Convocatoria) proyectoActual.getModalidad()).getRestriccion().getId().equals("CONV_POSG")
						|| ((Convocatoria) proyectoActual.getModalidad()).getRestriccion().getId()
								.equals("CONV_POSG_3"))) {

			Convocatoria conv = (Convocatoria) proyectoActual.getModalidad();
			ConvocatoriaPadre convPadre = conv.getPadre();

			if (convPadre.getId().equals(ID_CONVOC_POSG)) {
				esConvFichaMinimaPosgrados = true;
				// justificacionLabel = "Articulación de los proyectos:";
				justificacionLabel = "Aportes del proyecto a la vinculación de los estudiantes (especificar áreas de trabajo y aplicación):";

				if (((Convocatoria) proyectoActual.getModalidad()).getRestriccion().getId().equals("CONV_POSG_3")) {
					esConvFichaMinimaPosgradosMod3 = true;
				}
			} else {
				justificacionLabel = "Justificación:";
			}

		}

		if (!(proyectoActual.getModalidad().getTipo().getId().compareTo("CFM") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CEQ") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CMP") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CPU") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CEI") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CJI") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("FMH") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CED") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("RPL") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CTV") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CCT") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("RFE") == 0)) {
			if (proyectoActual.getTipoInvestigacion() != null
					&& proyectoActual.getTipoInvestigacion().getId().equals("2040100")) {
				proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(),
						ProyectoDAOHibernate.CREACIONARTISTICA);
				CreacionArtistica ca = proyectoActual.getCreacionArtistica();
				if (ca != null) {
					creacionArtistica = proyectoActual.getCreacionArtistica();
				} else {
					creacionArtistica = new CreacionArtistica();
				}
			} else {
				creacionArtistica = new CreacionArtistica();
			}
		}

		if (proyectoActual.getModalidad().getTipo().getId().compareTo("CFM") == 0
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("CPU") == 0
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("CMP") == 0
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("CEQ") == 0) {
			mostrarSiFichaMinima = true;

//			if(proyectoActual.getModalidad().getId().equals(853L))

			switch (proyectoActual.getModalidad().getId().intValue()) {
			case 853:
				esEventosHumanasModUno = true;
				break;
			case 854:
				esEventosHumanasModDos = true;
				break;
			case 836:
				esEventosMinasModUno = true;
				break;
			case 837:
				esEventosMinasModDos = true;
				break;
			case 885:
				esEventosFIAModUno = true;
				break;
			case 886:
				esEventosFIAModDos = true;
				break;
			}

			asignarValoresListas();

			RestriccionConvocatoria r = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();
			if (r != null) {
				System.out.println(r.getId());
				if (r.getId().equals("CE1")) {
					esEventosModUno = true;
					esEventosModUno_2017 = false;
					esEventosModUno_2019 = false;
					mostrarSiEventos = true;
					mostrarSiEscuelaInternacional = false;
					mostrarSiFichaMinima = false;
				}
				if (r.getId().equals("CE2")) {
					esEventosModDos = true;
					esEventosModDos_2017 = false;
					esEventosModDos_2019 = false;
					mostrarSiEventos = true;
					mostrarSiEscuelaInternacional = false;
					mostrarSiFichaMinima = false;
				}
				if (r.getId().equals("CE1-N2017")) {
					esEventosModUno = false;
					esEventosModUno_2017 = true;
					esEventosModUno_2019 = false;
					mostrarSiEventos = true;
					mostrarSiEscuelaInternacional = false;
					mostrarSiFichaMinima = false;
				}
				if (r.getId().equals("CE2-N2017")) {
					esEventosModDos = false;
					esEventosModDos_2017 = true;
					esEventosModDos_2019 = false;
					mostrarSiEventos = true;
					mostrarSiEscuelaInternacional = false;
					mostrarSiFichaMinima = false;
				}
				if (r.getId().equals("CONV_EVENT_2019_M1") || r.getId().equals("EVENTOS_GENERICO_INTERNACIONAL")) {
					areaGestionConocimientoItem = crearListaItemDominioDetalle(DOMINIO_AREA_GESTION_CONOCIMIENTO);
					esEventosModUno = false;
					esEventosModUno_2017 = false;
					esEventosModUno_2019 = true;
					mostrarSiEventos = true;
					mostrarSiEscuelaInternacional = false;
					mostrarSiFichaMinima = false;
				}
				if (r.getId().equals("CONV_EVENT_2019_M2") || r.getId().equals("EVENTOS_GENERICO_NACIONAL")) {
					areaGestionConocimientoItem = crearListaItemDominioDetalle(DOMINIO_AREA_GESTION_CONOCIMIENTO);
					esEventosModDos = false;
					esEventosModDos_2017 = false;
					esEventosModDos_2019 = true;
					mostrarSiEventos = true;
					mostrarSiEscuelaInternacional = false;
					mostrarSiFichaMinima = false;
				}
				if (r.getId().equals("CONV_MOOTS_2019_M1") || r.getId().equals("CONV_MOOTS_2019_M2")
						|| r.getId().equals("CONV_MOOTS_2021_M1") || r.getId().equals("CONV_MOOTS_2021_M2")) {
					esConvMoots2019 = true;
					mostrarSiEventos = true;
					mostrarSiEscuelaInternacional = false;
					mostrarSiFichaMinima = false;
				}

			}
		} else {
			esEventosModDos = false;
			mostrarSiEventos = false;
			mostrarSiEscuelaInternacional = false;
			mostrarSiFichaMinima = true;
		}

		if (proyectoActual.getModalidad().getTipo().getId().compareTo("CEI") == 0) {
			mostrarSiEscuelaInternacional = true;
			mostrarSiEventos = false;
			mostrarSiFichaMinima = false;
			if (proyectoActual.getAntecedentes() != null) {
				obtenerCriterios();
			}

			List listaModulos = servicioGeneral
					.obtenerObjetos("select e from SesionCurso e where e.proyecto.id = " + proyectoActual.getId());

			if (listaModulos != null && listaModulos.size() > 0) {

				for (int i = 0; i < listaModulos.size(); i++) {
					SesionCurso ses = (SesionCurso) listaModulos.get(i);
					listaSesiones.add(ses);
				}

			}
		}

		if (proyectoActual.getDescripcion() == null) {
			// proyectoActual.setDescripcion("La descripción ya no se utiliza en esta
			// convocatoria");
		}

		if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0) {
			this.esProgramaNacional = true;
			titulo1 = "Programa:";
			titulo2 = "Integrantes del programa";
			titulo3 = "Estrategias/Mecanismos";
		} else {
			titulo1 = "Proyecto:";
			titulo2 = "Integrantes del proyecto de investigación";
			if (esConvSemillero) {
				titulo3 = "Estratégia pedagógica";
			} else {
				if (esConvPurdue) {
					titulo1 = "Descripción del tema de investigación/Objetivo general";
					justificacionLabel = "Aporte al proceso de formación del estudiante";
					titulo3 = "Dinámica de trabajo";
				} else {

					if (esConvJI_2014) {
						titulo3 = "Metodología del proyecto";
					} else {
						if (esCorredorTecnologico) {
							titulo3 = "Propuesta técnica y metodológica";
						} else {
							// titulo3 = "Metodología/Estrategias";
						}

					}
				}

			}

		}

		if (proyectoActual.getModalidad() instanceof Convocatoria) {
			System.out.println("Extensión Solidaria");
			RestriccionConvocatoria r = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();
			if (r != null) {
				System.out.println(r.getId());
				if (r.getId().equals(RestriccionConvocatoria.CONV_EXT_SOL)) {
					esExtensionSolidaria = true;
				}
			}
		} else {
			System.out.println("no es Extensión Solidaria");
		}

		// Asignatura

		codigoAsignatura = "";
		cambiosEnAsignaturas = false;

		listaAsignaturas = new ArrayList<VAsignaturasSIA>();

		mostrarAsignaturasEncontradas = false;

		if (proyectoActual.getAsignaturaSIA() == null)
			mostrarAsignaturaSeleccionada = false;
		else {
			mostrarAsignaturaSeleccionada = true;
			listaAsignaturas.add(proyectoActual.getAsignaturaSIA());
		}
		// String hql = "from LaboratorioDetalleDocencia WHERE laboratorio = '"
		// + laboratorioActual.getId() + "' ORDER BY id";
		// listaAsignaturas = servicioGeneral.obtenerObjetos(
		// LaboratorioDetalleDocencia.class, hql);
		// if (listaAsignaturas.size() < 1) {
		// listaAsignaturas = null;
		// }
		if (convocatoriaActual.getPadre().getEsCorte() != null
				&& convocatoriaActual.getPadre().getEsCorte().equals("Y")) {
			cortes = cargarCortes(cortes);
		}

		cargarFormularioInformacionEspecifica();
		if (esConvCP2019) {
			cargarCentrosPensamiento();
		}
	}

	public void cargarFormularioInformacionEspecifica() {
		String hqlFormEsp = "select e from FormularioInformacionEspecifica e where e.modalidad.id = "
				+ convocatoriaActual.getId();
		List<FormularioInformacionEspecifica> listaFormInfoEsp = servicioGeneral
				.obtenerObjetos(FormularioInformacionEspecifica.class, hqlFormEsp);
		if (listaFormInfoEsp != null && listaFormInfoEsp.size() > 0) {
			FormularioInformacionEspecifica fie = listaFormInfoEsp.get(0);
			titulo1 = fie.getCampoUnoTexto();
			justificacionLabel = fie.getCampoDosTexto();
			titulo3 = fie.getCampoTresTexto();
			titulo4 = fie.getCampoCuatroTexto();
			titulo5 = fie.getCampoCincoTexto();
			titulo6 = fie.getCampoSeisTexto();
			titulo7 = fie.getCampoSieteTexto();
			titulo8 = fie.getCampoOchoTexto();
			titulo9 = fie.getCampoNueveTexto();
			titulo10 = fie.getCampoDiezTexto();
			titulo11 = fie.getCampoOnceTexto();
			titulo12 = fie.getCampoDoceTexto();
			titulo13 = fie.getCampoTreceTexto();
			titulo14 = fie.getCampoCatorceTexto();
			titulo15 = fie.getCampoQuinceTexto();
			titulo16 = fie.getCampoDieciseisTexto();
			mostrarTitulo1 = fie.getCampoUno();
			mostrarTitulo2 = fie.getCampoDos();
			mostrarTitulo3 = fie.getCampoTres();
			mostrarTitulo4 = fie.getCampoCuatro();
			mostrarTitulo5 = fie.getCampoCinco();
			mostrarTitulo6 = fie.getCampoSeis();
			mostrarTitulo7 = (fie.getCampoSiete() == null) ? false : fie.getCampoSiete();
			mostrarTitulo8 = (fie.getCampoOcho() == null) ? false : fie.getCampoOcho();
			mostrarTitulo9 = (fie.getCampoNueve() == null) ? false : fie.getCampoNueve();
			mostrarTitulo10 = (fie.getCampoDiez() == null) ? false : fie.getCampoDiez();
			mostrarTitulo11 = (fie.getCampoOnce() == null) ? false : fie.getCampoOnce();
			mostrarTitulo12 = (fie.getCampoDoce() == null) ? false : fie.getCampoDoce();
			mostrarTitulo13 = (fie.getCampoTrece() == null) ? false : fie.getCampoTrece();
			mostrarTitulo14 = (fie.getCampoCatorce() == null) ? false : fie.getCampoCatorce();
			mostrarTitulo15 = (fie.getCampoQuince() == null) ? false : fie.getCampoQuince();
			mostrarTitulo16 = (fie.getCampoDieciseis() == null) ? false : fie.getCampoDieciseis();
			mostarAsignatura = fie.getAsignatura();
			mostrarObjResulResp = fie.getObjetivosResutadosResponsables();
			setMostrarObjResulRespObligatorio((fie.getObjetivosResutadosResponsablesObligatorio() == null) ? false : fie.getObjetivosResutadosResponsablesObligatorio());
			setMostrarlistaSeleccionMultiplObligatorio((fie.getListaSeleccionMultipleObligatorio() == null) ? false : fie.getListaSeleccionMultipleObligatorio());
			mostrarListaSeleccion = (fie.getListaSeleccion() == null) ? false : fie.getListaSeleccion();
			mostrarListaSeleccionDos = (fie.getListaSeleccionDos() == null) ? false : fie.getListaSeleccionDos();
			mostrarTitulo1Obligatorio = fie.getCampoUnoObligatorio();
			mostrarTitulo2Obligatorio = fie.getCampoDosObligatorio();
			mostrarTitulo3Obligatorio = fie.getCampoTresObligatorio();
			mostrarTitulo4Obligatorio = fie.getCampoCuatroObligatorio();
			mostrarTitulo5Obligatorio = fie.getCampoCincoObligatorio();
			mostrarTitulo6Obligatorio = fie.getCampoSeisObligatorio();
			mostrarTitulo7Obligatorio = (fie.getCampoSieteObligatorio() == null) ? false
					: fie.getCampoSieteObligatorio();
			mostrarTitulo8Obligatorio = (fie.getCampoOchoObligatorio() == null) ? false : fie.getCampoOchoObligatorio();
			mostrarTitulo9Obligatorio = (fie.getCampoNueveObligatorio() == null) ? false
					: fie.getCampoNueveObligatorio();
			mostrarTitulo10Obligatorio = (fie.getCampoDiezObligatorio() == null) ? false
					: fie.getCampoDiezObligatorio();
			mostrarTitulo11Obligatorio = (fie.getCampoOnceObligatorio() == null) ? false
					: fie.getCampoOnceObligatorio();
			mostrarTitulo12Obligatorio = (fie.getCampoDoceObligatorio() == null) ? false
					: fie.getCampoDoceObligatorio();
			mostrarTitulo13Obligatorio = (fie.getCampoTreceObligatorio() == null) ? false
					: fie.getCampoTreceObligatorio();
			mostrarTitulo14Obligatorio = (fie.getCampoCatorceObligatorio() == null) ? false
					: fie.getCampoCatorceObligatorio();
			mostrarTitulo15Obligatorio = (fie.getCampoQuinceObligatorio() == null) ? false
					: fie.getCampoQuinceObligatorio();
			mostrarTitulo16Obligatorio = (fie.getCampoDieciseisObligatorio() == null) ? false
					: fie.getCampoDoceObligatorio();
			mostrarListaSel1Obligatorio = (fie.getListaSel1Obligatorio() == null) ? false
					: fie.getListaSel1Obligatorio();
			mostrarListaSel2Obligatorio = (fie.getListaSel2Obligatorio() == null) ? false
					: fie.getListaSel2Obligatorio();
			mostrarListaSeleccionMultiple = (fie.getListaSeleccionMultiple() == null) ? false
					: fie.getListaSeleccionMultiple();
			mostrarListaSeleccionMultiple2 = (fie.getListaSeleccionMultiple2() == null) ? false
					: fie.getListaSeleccionMultiple2();

			if (mostrarObjResulResp) {
				cargarObjetivos();
				cargarResultados();
				cargarParticipantes();
				listaObjetivosResultados.addAll(proyectoActual.getObjetivosResultados());
			}

			mostrarLugarEjecucion = fie.getLugarEjecucion();

			if (mostrarLugarEjecucion) {
				cargarCiudades();
				listaCiudadesCCT.addAll(proyectoActual.getCiudades());
			}

			mostrarGrupos = fie.getGrupos();

			if (mostrarGrupos) {
				cargarGruposInvestigacion();
				listaGruposConvMedicina.addAll(proyectoActual.getGrupos());
			}
			mostrarMarcoTeorico = (fie.getMarcoTeorico() == null) ? false
					: fie.getMarcoTeorico();

			if (mostrarListaSeleccion) {
				textoListaSeleccion = fie.getTextoListaSeleccion();

				List listaAreaTemConvBioMan2016 = new ArrayList<DominioDetalle>();
				listaAreaTemConvBioMan2016 = servicioGeneral.obtenerObjetos(
						"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
								+ fie.getDominioListaSeleccion() + "' order by dd.descripcion");
				setListaAreaTematicaConvsMan2016(new SelectItem[listaAreaTemConvBioMan2016.size()]);
				for (int i = 0; i < listaAreaTemConvBioMan2016.size(); i++) {
					DominioDetalle dd = (DominioDetalle) listaAreaTemConvBioMan2016.get(i);
					listaAreaTematicaConvsMan2016[i] = new SelectItem(dd.getIdentificador().getTipo(),
							dd.getDescripcion());
					dd = null;
				}
			}

			if (mostrarListaSeleccionDos) {
				textoListaSeleccionDos = fie.getTextoListaSeleccionDos();

				List listaOpcionsSeleDos = new ArrayList<DominioDetalle>();
				listaOpcionsSeleDos = servicioGeneral.obtenerObjetos(
						"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
								+ fie.getDominioListaSeleccionDos() + "' order by dd.descripcion");
				setListaSeleccionDos(new SelectItem[listaOpcionsSeleDos.size()]);
				for (int i = 0; i < listaOpcionsSeleDos.size(); i++) {
					DominioDetalle dd = (DominioDetalle) listaOpcionsSeleDos.get(i);
					listaSeleccionDos[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
					dd = null;
				}
			}

			if (mostrarListaSeleccionMultiple) {
				textoListaSeleccionMultiple = fie.getTextoListaSeleccionmultiple();
				valoresListasProyectos = new ArrayList<ValoresListasProyecto>();
				valoresListasProyectosBorrados = new ArrayList<ValoresListasProyecto>();
				dominioValoresListaProyectos = fie.getDominioListaSeleccionMultiple();
				cargarListaValoresProyectos();
				cargarValoresSeleccionadosProyecto();
			}
			
			if (mostrarListaSeleccionMultiple2) {
				textoListaSeleccionMultiple2 = fie.getTextoListaSeleccionmultiple2();
				valoresListasProyectosParametrizado2 = new ArrayList<ValoresListasProyecto>();
				valoresListasProyectosBorrados = new ArrayList<ValoresListasProyecto>();
				dominioValoresListaSelMul2 = fie.getDominioListaSeleccionMultiple2();
				cargarListaSeleccion2Valores();
				cargarValoresSeleccionadosProyectoParametrizado2(dominioValoresListaSelMul2);
			}

		}
	}
	
	public void cargarValoresSeleccionadosProyectoParametrizado2(String dominio) {
		valoresListasProyectosParametrizado2 = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
				"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
						+ " and e.tipo = '"+dominio+"'");
		if (valoresListasProyectosParametrizado2 == null) {
			valoresListasProyectosParametrizado2 = new ArrayList<ValoresListasProyecto>();
		}
	}

	public void cargarValoresSeleccionadosProyecto() {
		valoresListasProyectos = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
				"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
						+ " and e.tipo = '" + dominioValoresListaProyectos + "'");
		if (valoresListasProyectos == null) {
			valoresListasProyectos = new ArrayList<ValoresListasProyecto>();
		}
	}

	public void cargarListaValoresProyectos() {
		listaSeleccionValoresProyecto = crearListaItemDominioDetalle(dominioValoresListaProyectos);
	}

	public void cargarListaSeleccion2Valores() {
		setListaSeleccionMultipleValoresProyecto2(crearListaItemDominioDetalle(dominioValoresListaSelMul2));
	}
	
	public void adicionarValorProyecto() {
		List<DominioDetalle> listaDomDet = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select e from DominioDetalle e, Dominio d where e.identificador.tipo = '" + valorListaProyecto
						+ "' and e.identificador.id = d.id and d.tipo = '" + dominioValoresListaProyectos + "'");
		DominioDetalle g = obtenerDominioDetalleLista(valorListaProyecto, listaDomDet);

		ValoresListasProyecto vlp = new ValoresListasProyecto();
		vlp.setProyecto(proyectoActual);
		vlp.setTipo(dominioValoresListaProyectos);
		vlp.setValor(g.getIdentificador().getTipo());
		vlp.setDescripcion(g.getDescripcion());

		if (!valoresListasProyectos.contains(vlp)) {
			valoresListasProyectos.add(vlp);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La opción seleccionada ya se encuentra asociada al proyecto",
					"La opción seleccionada ya se encuentra asociada al proyecto");
		}

	}

	public void eliminarValorProyecto() {
		valoresListasProyectos.remove(valorListaProyectoSeleccionada);
		valoresListasProyectosBorrados.add(valorListaProyectoSeleccionada);
		valorListaProyectoSeleccionada = new ValoresListasProyecto();
	}

	public void cargarGruposSUEProyecto() {
		listaGruposSUE = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
				"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
						+ " and e.tipo = 'GRUPOS_PROYECTO_SUE'");
		if (listaGruposSUE == null) {
			listaGruposSUE = new ArrayList<ValoresListasProyecto>();
		}
	}

	public void adicionarGrupoProyecto() {
		List<DominioDetalle> listaDomDet = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select e from DominioDetalle e where e.identificador.tipo = '" + entidadGrupoCCT
						+ "' and e.identificador.id = 181");
		DominioDetalle g = obtenerDominioDetalleLista(entidadGrupoCCT, listaDomDet);

		ValoresListasProyecto vlp = new ValoresListasProyecto();
		vlp.setProyecto(proyectoActual);
		vlp.setTipo("GRUPOS_PROYECTO_SUE");
		vlp.setValor(g.getIdentificador().getTipo());
		vlp.setNombreValor(g.getDescripcion());
		vlp.setDescripcion(nombreGrupoCCT);

		if (!listaGruposSUE.contains(vlp)) {
			listaGruposSUE.add(vlp);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El grupo ya se encuentra asociado al proyecto", "El grupo ya se encuentra asociado al proyecto");
		}

	}

	public void eliminarGrupoProyecto() {
		listaGruposSUE.remove(grupoSUESeleccionado);
		listaGruposSUEBorrados.add(grupoSUESeleccionado);
		grupoSUESeleccionado = new ValoresListasProyecto();
	}

	public void asignarValoresListas() {

		if (!esListaVacia(proyectoActual.getListaAreaGestionConocimiento())) {
			for (int i = 0; i < proyectoActual.getListaAreaGestionConocimiento().size(); i++) {
				ProyectoAreaGestionConocimiento poblacionObj = (ProyectoAreaGestionConocimiento) proyectoActual
						.getListaAreaGestionConocimiento().get(i);
				selectedAreaGestionConocimiento[i] = poblacionObj.getAreaGestionConocimiento().getIdentificador()
						.getTipo();
			}
		}
	}

	public void actualizarListaAreaGestionConocimiento() {
		List<ProyectoAreaGestionConocimiento> listaAlmacenar = new ArrayList<ProyectoAreaGestionConocimiento>();
		List<String> listaOficial = Arrays.asList(cleanArrayString(selectedAreaGestionConocimiento));

		for (int i = 0; i < listaOficial.size(); i++) {
			boolean existe = false;
			for (int a = 0; a < proyectoActual.getListaAreaGestionConocimiento().size(); a++) {
				if (listaOficial.get(i).equals(proyectoActual.getListaAreaGestionConocimiento().get(a)
						.getAreaGestionConocimiento().getIdentificador().getTipo())) {
					existe = true;
//                    if (listaOficial.get(i).equals(DominioDetalle.OTRO_CARACTER_OBRA)) {
//                    	proyectoActual.getListaPoblacionObjetivo().get(a).setObservaciones(otroCaracter);
//                    }
					listaAlmacenar.add(proyectoActual.getListaAreaGestionConocimiento().get(a));
					proyectoActual
							.borrarAreaGestionConocimiento(proyectoActual.getListaAreaGestionConocimiento().get(a));
					break;
				}
			}
			if (!existe) {
				ProyectoAreaGestionConocimiento caracterNuevo = new ProyectoAreaGestionConocimiento();
				DominioDetalle caracterDominioDetalle = new DominioDetalle();
				IdDominioDetalle id = new IdDominioDetalle();
				id.setId(Dominio.DOMINIO_AREA_GESTION_CONOCIMIENTO);
				id.setTipo(listaOficial.get(i));
				caracterDominioDetalle.setIdentificador(id);
				caracterNuevo.setAreaGestionConocimiento(caracterDominioDetalle);
				caracterNuevo.setProyecto(proyectoActual);
//                if (listaOficial.get(i).equals(DominioDetalle.OTRO_CARACTER_OBRA)) {
//                    caracterNuevo.setObservaciones(otroCaracter);
//                }
				listaAlmacenar.add(caracterNuevo);
			}
		}
		proyectoActual.borrarListaAreaGestionConocimiento(proyectoActual.getListaAreaGestionConocimiento());
		proyectoActual.setListaAreaGestionConocimiento(listaAlmacenar);
	}

	public void cargarListaColecciones() {
		String hqlLab = "select #id e.id, #nombre e.nombre from Coleccion e where e.estado <> 'B'";

		List listaAreaTemConvBioMan2016 = new ArrayList<Coleccion>();
		listaAreaTemConvBioMan2016 = servicioGeneral.obtenerObjetosLimitado(Coleccion.class, hqlLab);
		setListaAreaTematicaConvsMan2016(new SelectItem[listaAreaTemConvBioMan2016.size()]);
		for (int i = 0; i < listaAreaTemConvBioMan2016.size(); i++) {
			Coleccion dd = (Coleccion) listaAreaTemConvBioMan2016.get(i);
			listaAreaTematicaConvsMan2016[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}
	}

	public void cargarAreaTemConvBioMan2016() {
		List listaAreaTemConvBioMan2016 = new ArrayList<DominioDetalle>();
		listaAreaTemConvBioMan2016 = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_AREA_TEMATICA_CONV_BIO_MAN + "' order by dd.descripcion");
		setListaAreaTematicaConvsMan2016(new SelectItem[listaAreaTemConvBioMan2016.size()]);
		for (int i = 0; i < listaAreaTemConvBioMan2016.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaAreaTemConvBioMan2016.get(i);
			listaAreaTematicaConvsMan2016[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}
	}

	public void cargarAreaTemConvCienBasMan2016() {
		List listaAreaTemConvBioMan2016 = new ArrayList<DominioDetalle>();
		listaAreaTemConvBioMan2016 = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ AREA_TEMATICA_CONV_CIENCIAS_BASICAS + "' order by dd.descripcion");
		setListaAreaTematicaConvsMan2016(new SelectItem[listaAreaTemConvBioMan2016.size()]);
		for (int i = 0; i < listaAreaTemConvBioMan2016.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaAreaTemConvBioMan2016.get(i);
			listaAreaTematicaConvsMan2016[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}
	}

	public void cargarAreaTemConvCienSocMan2016() {
		List listaAreaTemConvBioMan2016 = new ArrayList<DominioDetalle>();
		listaAreaTemConvBioMan2016 = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ AREA_TEMATICA_CONV_CIENCIAS_SOCIALES + "' order by dd.descripcion");
		setListaAreaTematicaConvsMan2016(new SelectItem[listaAreaTemConvBioMan2016.size()]);
		for (int i = 0; i < listaAreaTemConvBioMan2016.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaAreaTemConvBioMan2016.get(i);
			listaAreaTematicaConvsMan2016[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}
	}

	public void cargarAreaTemConvInvAplicaMan2016() {
		List listaAreaTemConvBioMan2016 = new ArrayList<DominioDetalle>();
		listaAreaTemConvBioMan2016 = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ AREA_TEMATICA_CONV_INVESTIGACION_APLICADA + "' order by dd.descripcion");
		setListaAreaTematicaConvsMan2016(new SelectItem[listaAreaTemConvBioMan2016.size()]);
		for (int i = 0; i < listaAreaTemConvBioMan2016.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaAreaTemConvBioMan2016.get(i);
			listaAreaTematicaConvsMan2016[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}
	}

	public void cargarAreaTemConvHumMan2016() {
		List listaAreaTemConvHumMan2016 = new ArrayList<DominioDetalle>();
		listaAreaTemConvHumMan2016 = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_AREA_TEMATICA_CONV_HUM_MAN + "' order by dd.descripcion");
		setListaAreaTematicaConvsMan2016(new SelectItem[listaAreaTemConvHumMan2016.size()]);
		for (int i = 0; i < listaAreaTemConvHumMan2016.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaAreaTemConvHumMan2016.get(i);
			listaAreaTematicaConvsMan2016[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}
	}

	public void cargarAreaTemConvIEU2016() {
		List listaAreaTemConvHumMan2016 = new ArrayList<DominioDetalle>();
		listaAreaTemConvHumMan2016 = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_AREA_TEMATICA_CONV_IEU + "' order by dd.descripcion");
		setListaAreaTematicaConvsMan2016(new SelectItem[listaAreaTemConvHumMan2016.size()]);
		for (int i = 0; i < listaAreaTemConvHumMan2016.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaAreaTemConvHumMan2016.get(i);
			listaAreaTematicaConvsMan2016[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}
	}

	public void cargarTrayectoriaColecciones() {
		List listaTrayectoriasColecciones = new ArrayList<DominioDetalle>();
		listaTrayectoriasColecciones = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_TRAYECTORIA_COLECCION + "' order by dd.descripcion");
		setListaSeleccionTrayectoriaColeccion(new SelectItem[listaTrayectoriasColecciones.size()]);
		for (int i = 0; i < listaTrayectoriasColecciones.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaTrayectoriasColecciones.get(i);
			listaSeleccionTrayectoriaColeccion[i] = new SelectItem(dd.getIdentificador().getTipo(),
					dd.getDescripcion());
			dd = null;
		}
	}

	public void cargarObjetivos() {
		String hql = "select o from ObjetivoEspecifico o where o.proyecto.id = '" + this.proyectoActual.getId() + "'";
		List<ObjetivoEspecifico> list = servicioGeneral.obtenerObjetos(hql);

		if (list.size() > 0) {
			listaObjetivos = new SelectItem[list.size()];

			for (int i = 0; i < list.size(); i++) {
				ObjetivoEspecifico objetivo = new ObjetivoEspecifico();
				objetivo = (ObjetivoEspecifico) list.get(i);

				listaObjetivos[i] = new SelectItem(objetivo.getId(), objetivo.getNombre());
			}
			objetivo = list.get(0);
		}

	}

	public void cargarResultados() {
		String hql = "select o from ResultadoProyecto o where o.proyecto.id = '" + this.proyectoActual.getId() + "'";
		List<ResultadoProyecto> list = servicioGeneral.obtenerObjetos(hql);

		if (list.size() > 0) {
			listaResultados = new SelectItem[list.size()];

			for (int i = 0; i < list.size(); i++) {
				ResultadoProyecto resultado = new ResultadoProyecto();
				resultado = (ResultadoProyecto) list.get(i);

				listaResultados[i] = new SelectItem(resultado.getId(), resultado.getDescripcion());
			}
			resultado = list.get(0);
		}

	}

	public void cargarParticipantes() {
		String hql = "select o from InvestigadorProyecto o where o.proyecto.id = '" + this.proyectoActual.getId() + "'";
		List<InvestigadorProyecto> list = servicioGeneral.obtenerObjetos(hql);

		if (list.size() > 0) {
			listaParticipantes = new SelectItem[list.size()];

			for (int i = 0; i < list.size(); i++) {
				InvestigadorProyecto invPry = new InvestigadorProyecto();
				invPry = (InvestigadorProyecto) list.get(i);

				listaParticipantes[i] = new SelectItem(invPry.getId(),
						invPry.getInvestigador().getNombre1() + " " + invPry.getInvestigador().getNombre2() + " "
								+ invPry.getInvestigador().getApellido1() + " "
								+ invPry.getInvestigador().getApellido2());
			}
			// invParticipante = list.get(0);
		}

	}

	public void adicionarObjEspRep() {

		if (listaObjetivos.length > 0 && !idRes.equals("") && listaParticipantes.length > 0) {
			ActividadObjetivo ao = new ActividadObjetivo();

			String hqlObj = "select o from ObjetivoEspecifico o where o.id = '" + idObj + "'";
			List<ObjetivoEspecifico> listObj = servicioGeneral.obtenerObjetos(hqlObj);
			ObjetivoEspecifico o = listObj.get(0);

			ao.setObjetivoEspecifico(o);

			// String hqlRes =
			// "select o from ResultadoProyecto o where o.id = '"
			// + idRes + "'";
			// List<ResultadoProyecto> listRes =
			// servicioGeneral.obtenerObjetos(hqlRes);
			// ResultadoProyecto r = listRes.get(0);
			//
			//
			// ao.setResultadoProyecto(r);

			ao.setDescripcion(idRes);

			String hql = "select o from InvestigadorProyecto o where o.id = '" + idInvPry + "'";
			List<InvestigadorProyecto> list = servicioGeneral.obtenerObjetos(hql);
			InvestigadorProyecto p = list.get(0);
			ao.setInvestigadorPro(p.getInvestigador());

			// ao.setDescripcion("Pendiente");
			ao.setMesInicial(0);
			ao.setDuracionMeses(0);

			listaObjetivosResultados.add(ao);
			idRes = "";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registrar los datos de los objetivos, meta y responsable",
							"Por favor registrar los datos de los objetivos, meta y responsable"));
		}

	}

	public void eliminarObjetiEspResp() {
		listaObjetivosResultados.remove(objetivoSeleccionado);
		proyectoActual.borrarObjetivoResultado(objetivoSeleccionado);
		objetivoSeleccionado = new ActividadObjetivo();
	}

	public void cargarCiudades() {
		String consListaCiudades = "select e from Ciudad e where e.departamento.id in ('CO25','CO11') and e.id not in ('CO25','CO11') order by e.nombre asc";
		listaCiudades = servicioGeneral.obtenerObjetos(consListaCiudades);
		ciudadItem = new SelectItem[listaCiudades.size()];
		for (int i = 0; i < listaCiudades.size(); i++) {
			Ciudad ci = (Ciudad) listaCiudades.get(i);
			ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	public void cargarGruposInvestigacion() {
		String grupos = "select #id e.id, #nombre e.nombre from Grupo e where e.estadoGrupo.id = 'A'";
		listaGrupos = servicioGeneral.obtenerObjetosLimitado(Grupo.class, grupos);

		gruposInvItem = new SelectItem[listaGrupos.size()];
		for (int i = 0; i < listaGrupos.size(); i++) {
			Grupo dd = (Grupo) listaGrupos.get(i);
			gruposInvItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}
	}

	public void cargarLaboratoriosFacultad() {
		boolean esLaboratoriosSede = (Boolean) sesion.getAttribute("esLaboratoriosSede");
		personaActual = (Persona) sesion.getAttribute("persona");
		InvestigadorInterno inte = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
		if (esLaboratoriosSede) {
			Long dependencia = inte.getDependencia().getSede().getId();
			String laboratorios = "select #id e.id, #nombre e.nombre from Laboratorio e where e.activo = '1' and e.sede.id = "
					+ dependencia + " order by e.nombre asc";
			listaLaboratorios = servicioGeneral.obtenerObjetosLimitado(Laboratorio.class, laboratorios);
		} else {
			String dependencia = inte.getDependencia().getFacultad().getId();
			String laboratorios = "select #id e.id, #nombre e.nombre from Laboratorio e where e.activo = '1' and e.facultad.id = "
					+ dependencia + " order by e.nombre asc";
			listaLaboratorios = servicioGeneral.obtenerObjetosLimitado(Laboratorio.class, laboratorios);
		}

		laboratoriosFacItem = new SelectItem[listaLaboratorios.size()];
		for (int i = 0; i < listaLaboratorios.size(); i++) {
			Laboratorio dd = (Laboratorio) listaLaboratorios.get(i);
			laboratoriosFacItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}
	}

	public void cargarLaboratorios() {
		Investigador directorProyecto = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());

		Error error = null;
		Date fechaActual = new Date();
		Date fechaRol = new Date();

		Iterator it = directorProyecto.getRoles().iterator();
		if (!it.hasNext()) {
			// no tiene roles
			error = new Error();
			error.setMensaje("No tiene roles asignados");
			sesion.setAttribute("error", error);
		} else {
			while (it.hasNext()) {
				Rol r = (Rol) it.next();

				// /Items principales del menú (nivel 0) ----
				Submenu submenu = new Submenu();

				String nombre = r.getNombre().substring(0, 1).toUpperCase()
						+ r.getNombre().substring(1, r.getNombre().length()).toLowerCase();

				try {
					fechaRol = servicioPersona.obtenerFechaFinRol(directorProyecto.getId(), r.getId());
					if (fechaRol == null || fechaRol.equals("")) {
						fechaRol = new Date();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (r.getId().equals(Rol.DIRECCION_NACIONAL_LABORATORIOS)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esLaboratoriosNacional = true;
					}
				} else if (r.getId().equals(Rol.DIRECCION_LABORATORIOS_SEDE)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esLaboratoriosSede = true;
					}
				} else if (r.getId().equals(Rol.COORDINADOR_LABORATORIO)
						|| r.getId().equals(Rol.COORDINADOR_TECNICO_LABORATORIO)
						|| r.getId().equals(Rol.TECNICO_LABORATORISTA)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esCoordinadorLaboratorio = true;
					}
				} else if (r.getId().equals(Rol.LABORATORIOS_FACULTAD)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esLaboratoriosFacultad = true;
					}
				} else if (r.getId().equals(Rol.LABORATORIOS_DEPARTAMENTO)) {
					if (fechaRol.after(fechaActual) || fechaRol.equals(fechaActual)) {
						esLaboratoriosDepto = true;
					}
				}
			}
		}

		if (((Convocatoria) proyectoActual.getModalidad()).getCoordinadorLaboratorios()) {
			esLaboratoriosNacional = false;
			esLaboratoriosFacultad = false;
			esLaboratoriosSede = false;
			esLaboratoriosDepto = false;
			esCoordinadorLaboratorio = true;
		}

		System.out.println("esLaboratoriosNacional: " + esLaboratoriosNacional);
		System.out.println("esLaboratoriosSede: " + esLaboratoriosSede);
		System.out.println("esLaboratoriosFacultad: " + esLaboratoriosFacultad);
		System.out.println("esLaboratoriosDepto: " + esLaboratoriosDepto);
		System.out.println("esCoordinadorLaboratorio: " + esCoordinadorLaboratorio);

		String hqlAnd = "";
		// personaActual = (Persona) sesion.getAttribute("persona");
		if (esLaboratoriosSede && directorProyecto instanceof InvestigadorInterno) {
			Sede sedePersona = ((InvestigadorInterno) directorProyecto).getDependencia().getSede();
			System.out.println("Dirección de Laboratorios Sede " + sedePersona.getNombre());
			hqlAnd = " AND L.sede.id = '" + sedePersona.getId() + "' ";
			sesion.setAttribute("sedeLabsSede", sedePersona);
		}

		String idLabs = "";
		if (esCoordinadorLaboratorio) {
			String documento = directorProyecto.getId().getDocumento();
			String tipoDocumento = directorProyecto.getId().getTipoDocumento();

			String hHql = "SELECT pL.laboratorio FROM PersonaLaboratorio pL WHERE pL.persona.id.documento = '"
					+ documento + "' AND pL.persona.id.tipoDocumento = '" + tipoDocumento + "'";
			if (((Convocatoria) proyectoActual.getModalidad()).getCoordinadorLaboratorios()) {
				hHql += " and pL.rol.id ='CO'";
			}
			hHql += " order by pL.laboratorio.id ASC";
			System.out.println("hHql:" + hHql);
			laboratoriosCoordinador = servicioGeneral.obtenerObjetos(Laboratorio.class, hHql);
		}

		if (esCoordinadorLaboratorio && !esLaboratoriosSede && !esLaboratoriosNacional) {
			System.out.println("Coordinador de Laboratorio");
			// Se arma una cadena con los ids de los laboratorios que los que la
			// persona actual es coordinador:
			for (Laboratorio lc : laboratoriosCoordinador) {
				Long idLab = lc.getId();
				idLabs += idLab + " ";
			}

			System.out.println("idLabs:" + idLabs + ":");
			idLabs = idLabs.trim().replace(" ", ",");
			System.out.println("idLabs:" + idLabs + ":");
			sesion.setAttribute("idLabs", idLabs);
			if (!esCadenaVacia(idLabs)) {
				hqlAnd = " AND L.id in (" + idLabs + ") ";
			} else {
				hqlAnd = "";
			}
		}

		if (esLaboratoriosNacional) {
			System.out.println("Sistema Nacional de Laboratorios");
			hqlAnd = "";
		}

		// Se consulta la facultad, de acuerdo a
		// InvestigadorInterno.dependencia2 ó dependencia
		if (esLaboratoriosFacultad && directorProyecto instanceof InvestigadorInterno) {
			Dependencia dependencia = ((InvestigadorInterno) directorProyecto).getDependencia2();
			if (dependencia == null) {
				dependencia = ((InvestigadorInterno) directorProyecto).getDependencia();
			}
			System.out.println("dependencia:" + dependencia.getNombre());
			if (dependencia.getFacultad() != null) {
				dependencia = dependencia.getFacultad();
			}
			System.out.println("dependencia:" + dependencia.getNombre());
			System.out.println("Laboratorios " + dependencia.getNombre());
			hqlAnd = " AND L.facultad.id = '" + dependencia.getId() + "' ";

			sesion.setAttribute("depLabsFacultad", dependencia);
		}

		// Laboratorios Departamento
		if (esLaboratoriosDepto && directorProyecto instanceof InvestigadorInterno) {
			Dependencia dependencia = ((InvestigadorInterno) directorProyecto).getDependencia2();
			if (dependencia == null) {
				dependencia = ((InvestigadorInterno) directorProyecto).getDependencia();
			}
			System.out.println("dependencia:" + dependencia.getNombre());
			if (dependencia.getDepartamento() != null) {
				dependencia = servicioDependencia.obtenerDependencia(dependencia.getDepartamento());
			}
			System.out.println("dependencia:" + dependencia.getNombre());
			System.out.println("Laboratorios " + dependencia.getNombre());
			hqlAnd = " AND L.departamento.id = '" + dependencia.getId() + "' ";

			sesion.setAttribute("depLabsFacultad", dependencia);
		}

		System.out.println("buscarLaboratorios1:");

		System.out.println("hqlAnd:" + hqlAnd);
		String hqlLabs = "FROM Laboratorio L WHERE L.activo = " + Laboratorio.VERDADERO + hqlAnd
				+ " order by L.nombre ";
		System.out.println("hqlLabs:" + hqlLabs);

		if (esCoordinadorLaboratorio && !esCadenaVacia(hqlAnd)) {
			listaLaboratorios = servicioGeneral.obtenerObjetos(Laboratorio.class, hqlLabs);
		} else {
			listaLaboratorios = new ArrayList<Laboratorio>();
		}

		System.out.println("buscarLaboratorios encontrados:" + listaLaboratorios.size());

		// Se marcan los laboratorios de los cuales la persona actual es
		// coordinador:
		if (laboratoriosCoordinador != null) {
			System.out.println("buscarLaboratorios2:");
			for (Laboratorio lc : laboratoriosCoordinador) {
				Long idLab = lc.getId();
				for (Laboratorio l : listaLaboratorios) {
					if (l.getId().equals(idLab)) {
						l.setPersonaActualEsCoordinador(true);
					}
				}
			}
		}

		laboratoriosFacItem = new SelectItem[listaLaboratorios.size()];
		for (int i = 0; i < listaLaboratorios.size(); i++) {
			Laboratorio dd = (Laboratorio) listaLaboratorios.get(i);
			laboratoriosFacItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}
	}

	public void adicionarEnsayoAcreditacion() {
		LaboratorioEnsayoAcreditacion ens = new LaboratorioEnsayoAcreditacion();

		ens.setProyecto(proyectoActual);
		ens.setNombreEnsayo(nombreEnsayo);
		ens.setAreaAcreditacion(areaAcreditacion);
		ens.setDocumentoReferencia(documentoReferencia);

		listaEnsayosAcredicion.add(ens);
		nombreEnsayo = "";
		areaAcreditacion = "";
		documentoReferencia = "";

	}

	public void eliminarEnsayoAcreditacion() {
		listaEnsayosAcredicion.remove(ensayoAcreditacionSeleccionado);
		listaEnsayosAcredicionBorrados.add(ensayoAcreditacionSeleccionado);
	}

	public void eliminarTrayectoriaColeccion() {
		listaTrayectoriaColeccion.remove(trayectoriaColeccionSeleccionada);
		listaTrayectoriaColeccionBorrados.add(trayectoriaColeccionSeleccionada);
	}

	public void adicionarTrayectoriaColeccion() {

		if (!idTrayectoriaColeccion.equals(0L)) {
			TrayectoriaColeccionProyecto detLab = new TrayectoriaColeccionProyecto();
			detLab.setProyecto(proyectoActual);
			detLab.setTrayectoria(idTrayectoriaColeccion);
			detLab.setDescripcion(descripcionTrayectoriaColeccion);
			if (!listaTrayectoriaColeccion.contains(detLab)) {
				listaTrayectoriaColeccion.add(detLab);
				idTrayectoriaColeccion = "0L";
				idTrayectoriaColeccion = "";
				descripcionTrayectoriaColeccion = "";
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"La trayectoria de la colección ya se encuentra asociada al proyecto",
								"La trayectoria de la colección ya se encuentra asociada al proyecto"));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Por favor seleccione una trayectoria para colección.",
							"Por favor seleccione una trayectoria para colección."));
		}

	}

	public void adicionarLaboratorio() {

		if ((listaLaboratoriosProyecto.size() < numLabsVal && numLabsVal != 0) || numLabsVal == 0) {
			if (!idLaboratorio.equals(0L)) {

				String sqlLab = "select #proyecto p from LaboratorioDetalleProyectos e, Proyecto p where e.proyecto.id=p.id and e.laboratorio.id = "
						+ idLaboratorio + " and e.proyecto.estadoProyecto.id ='P' and e.proyecto.modalidad.id="
						+ proyectoActual.getModalidad().getId();
				if (numRegLab != 0) {
					List<LaboratorioDetalleProyectos> retVal = servicioGeneral
							.obtenerObjetosLimitado(LaboratorioDetalleProyectos.class, sqlLab);
					if (retVal.size() >= numRegLab) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Un mismo laboratorio no puede estar vinculado a más de " + numRegLab + " proyectos.",
								"Un mismo laboratorio no puede estar vinculado a más de " + numRegLab + " proyectos."));
						return;
					}
				}

				LaboratorioDetalleProyectos detLab = new LaboratorioDetalleProyectos();
				detLab.setProyecto(proyectoActual);
				sqlLab = "select #id e.id, #nombre e.nombre from Laboratorio e where e.id = " + idLaboratorio;
				List<Laboratorio> listaLabSel = servicioGeneral.obtenerObjetosLimitado(Laboratorio.class, sqlLab);
				Laboratorio lab = listaLabSel.get(0);
				detLab.setLaboratorio(lab);
				detLab.setProyecto(proyectoActual);
				detLab.setPersonasPregrado(0);
				detLab.setPersonasEspecializacion(0);
				detLab.setPersonasMaestria(0);
				detLab.setPersonasDoctorado(0);
				detLab.setHorasSemanales(0);
				detLab.setFechaRegistro(new Date());
				detLab.setEsProgramaLaboratorios(true);
				detLab.setEstado("I");

				for (LaboratorioDetalleProyectos dl : listaLaboratoriosProyecto) {
					if (dl.getLaboratorio().getId().equals(detLab.getLaboratorio().getId())) {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"El laboratorio seleccionado ya se encuentra vinculado al proyecto",
										"El laboratorio seleccionado ya se encuentra vinculado al proyecto"));
						return;
					}
				}
				if (!listaLaboratoriosProyecto.contains(detLab)) {
					listaLaboratoriosProyecto.add(detLab);
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Por favor seleccione un laboratorio", "Por favor seleccione un laboratorio"));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Por favor tenga en cuenta el número de laboratorios que permite asociar la convocatoria.",
					"Por favor tenga en cuenta el número de laboratorios que permite asociar la convocatoria."));
		}

	}

	public void consultarLaboratorio() {
		laboratorioVistaSeleccionado = new LaboratorioVista();
		laboratorioVistaSeleccionado.setIdLaboratorio(laboratorioSeleccionado.getLaboratorio().getId());
		laboratorioVistaSeleccionado.setNombreLaboratorio(laboratorioSeleccionado.getLaboratorio().getNombre());
		laboratorioVistaSeleccionado.setEmailLaboratorio(laboratorioSeleccionado.getLaboratorio().getEmail());
		PersonaLaboratorio pL = mUL.coordinadorLaboratorio(laboratorioSeleccionado.getLaboratorio().getId());
		laboratorioVistaSeleccionado
				.setCoordiadorLaboratorio(pL.getPersona().getNombre1() + " " + pL.getPersona().getNombre2() + " "
						+ pL.getPersona().getApellido1() + " " + pL.getPersona().getApellido2());
	}

	public void eliminarLaboratorio() {
		listaLaboratoriosProyecto.remove(laboratorioSeleccionado);
		listaLaboratoriosProyectosBorrados.add(laboratorioSeleccionado);
	}

	public void adicionarCiudadCCT() {
		Ciudad ciudad = new Ciudad();
		String consultaCiudad = "select e from Ciudad e where e.id = '" + ciudadCCT + "'";
		List lisCiudad = servicioGeneral.obtenerObjetos(consultaCiudad);

		if (lisCiudad != null && lisCiudad.size() > 0) {
			ciudad = (Ciudad) lisCiudad.get(0);
		}
		listaCiudadesCCT.add(ciudad);
	}

	public void eliminarCiudad() {
		listaCiudadesCCT.remove(ciudadSeleccionada);
		proyectoActual.borrarCiudad(ciudadSeleccionada);
		ciudadSeleccionada = new Ciudad();
	}

	public void adicionarCriterio() {

		CriterioEvaluacionEscuelaInternacional crit = new CriterioEvaluacionEscuelaInternacional();
		crit.setCriterio(this.criterioEvaluacion);
		crit.setPonderacion(Integer.parseInt(this.ponderacionEvaluacion));
		criteriosEscuela.add(crit);
		this.criterioEvaluacion = "";
		this.ponderacionEvaluacion = "";

	}

	public void adicionarGrupoInv() {

		Grupo g = servicioGrupo.obtenerGrupo(Long.parseLong(proyectoActual.getAliados()));

		if (!listaGruposConvMedicina.contains(g)) {
			listaGruposConvMedicina.add(g);
		} else {
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El grupo de investigación ya se encuentra asociado al proyecto", ""));
		}

	}

	public void eliminarGrupoInv() {
		listaGruposConvMedicina.remove(grupoSeleccionado);
		proyectoActual.borrarGrupo(grupoSeleccionado);
		grupoSeleccionado = new Grupo();
	}

	public String convertirGruposCCT() {
		String eva = "";

		for (int i = 0; i < listaGruposCorredor.size(); i++) {
			GruposExternosCorredor cr = listaGruposCorredor.get(i);
			eva += cr.getNombreGrupo() + "<infoGrupo>" + cr.getEntidadGrupo() + "<grupo>";
		}

		return eva;
	}

	public ArrayList<GruposExternosCorredor> obtenerGruposCCT() {
		ArrayList<GruposExternosCorredor> cre = new ArrayList<GruposExternosCorredor>();
		String c = proyectoActual.getImpactoEsperado();
		String[] cr = c.split("<grupo>");
		for (int i = 0; i < cr.length; i++) {
			String[] cri = cr[i].split("<infoGrupo>");
			GruposExternosCorredor gru = new GruposExternosCorredor();
			gru.setNombreGrupo(cri[0]);
			gru.setEntidadGrupo(cri[1]);
			this.listaGruposCorredor.add(gru);
		}
		return cre;
	}

	public String convertirCriteriosEvaluacion() {
		String eva = "";

		for (int i = 0; i < criteriosEscuela.size(); i++) {
			CriterioEvaluacionEscuelaInternacional cr = criteriosEscuela.get(i);
			eva += cr.getCriterio() + " -> " + cr.getPonderacion() + "~";
		}

		return eva;
	}

	public ArrayList<CriterioEvaluacionEscuelaInternacional> obtenerCriterios() {
		ArrayList<CriterioEvaluacionEscuelaInternacional> cre = new ArrayList<CriterioEvaluacionEscuelaInternacional>();

		String c = proyectoActual.getAntecedentes();
		String[] cr = c.split("~");
		for (int i = 0; i < cr.length; i++) {
			String[] cri = cr[i].split(" -> ");
			CriterioEvaluacionEscuelaInternacional criEva = new CriterioEvaluacionEscuelaInternacional();
			criEva.setCriterio(cri[0]);
			criEva.setPonderacion(Integer.parseInt(cri[1]));
			this.criteriosEscuela.add(criEva);
		}

		return cre;
	}

	public void eliminarCriterio() {
		criteriosEscuela.remove(criterioSeleccionado);
		criterioSeleccionado = new CriterioEvaluacionEscuelaInternacional();
	}

	public void adicionarSesion() {
		// sesionUEC = numeroSesion+"";

		boolean existeSesion = false;
		for (int i = 0; i < listaSesiones.size(); i++) {
			if (listaSesiones.get(i).getNumero() == numeroSesion)
				existeSesion = true;
		}

		if ((numeroSesion != 0 && numeroSesion != null && !temaSesion.equals("") && temaSesion != null
				&& !contenidoSesion.equals("") && contenidoSesion != null)) {
			if (!existeSesion) {
				SesionCurso sesion = new SesionCurso();
				sesion.setNumero(numeroSesion);
				sesion.setTema(temaSesion);
				sesion.setContenido(contenidoSesion);
				sesion.setProyecto(proyectoActual);
				try {

					listaSesiones.add(sesion);
					// sesionUEC = "";
					// sesion = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Error al adicionar el módulo. Numero de Módulo ya existe : " + numeroSesion);
				FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Módulo ya existe", "Numero de módulo " + numeroSesion + " ya esta registrado"));
			}

		} else {
			System.out.println("Error al adicionar módulo");
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"No se adiciono el módulo", "Por favor revise que haya ingresado toda la informacion"));
		}
		// sesionUEC = new String();
	}

	public void eliminarSesion() {

		listaSesiones.remove(sesionSeleccionada);
		sesionSeleccionada = new SesionCurso();

	}

	public SesionCurso buscarSesionPorNumero(Long num) {
		// SesionCurso ses = null;
		int k = -1;
		for (int i = 0; i < listaSesiones.size(); i++) {
			System.out.println(
					"listaSesiones.get(i).getNumero(): " + listaSesiones.get(i).getNumero() + " - num: " + num);
			if (listaSesiones.get(i).getNumero() == num) {
				k = i;
			}
		}

		System.out.println("");

		if (k != -1)
			return listaSesiones.get(k);
		else
			return null;
	}

	public void adicionarDependencia() {
		setMensajeError("");
		if (dependenciaAreaResponsabilidad.getAreaResponsabilidad().equals("")) {
			FacesContext.getCurrentInstance().addMessage("msgDep",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar un área de responsabilidad", ""));
		} else {
			Dependencia dep = buscarDependencia(dependenciaId);
			dependenciaAreaResponsabilidad.setDependencia(dep);
			proyectoActual.adicionarDependencia(dependenciaAreaResponsabilidad);
			dependenciaAreaResponsabilidad = new DependenciaAreaResponsabilidad();

			dependenciaId = ((Dependencia) listaDependencia.get(0)).getId();
			dependenciaActual = (Dependencia) listaDependencia.get(0);
		}
	}

	public void eliminarDependencia() {
		// proyectoActual.borrarDependencia((DependenciaAreaResponsabilidad)tablaDependencias.getRowData());
		proyectoActual.borrarDependencia(dependenciaAreaResponsabilidadSeleccionada);
	}

	private Dependencia buscarDependencia(String id) {
		// BUSCA UNA DEPENDENCIA DE ACUERDO A SU ID
		Dependencia d = new Dependencia();
		int i = 0;
		while (i < listaDependencia.size()) {
			d = (Dependencia) listaDependencia.get(i);
			if (id.equals(d.getId()))
				break;
			i = i + 1;
		}
		return d;
	}

	// Asignatura

	public void buscarAsignaturasPorCodigo() {
		codigoAsignatura = codigoAsignatura.trim();

		if (codigoAsignatura.length() < 4) {
			String error = "El código a buscar debe tener mínimo cuatro caracteres.";
			FacesContext.getCurrentInstance().addMessage("messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, error, ""));
			return;
		}

		String hql = "from VAsignaturasSIA WHERE codAsignatura LIKE '%" + codigoAsignatura
				+ "%' ORDER BY codAsignatura";
		System.out.println("hql:" + hql);
		asignaturasEncontradas = new ArrayList<VAsignaturasSIA>();
		asignaturasEncontradas = servicioGeneral.obtenerObjetos(VAsignaturasSIA.class, hql);
		if (asignaturasEncontradas.size() < 1) {
			FacesContext.getCurrentInstance().addMessage("messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontró ninguna asignatura", ""));
			mostrarAsignaturasEncontradas = false;
			// asignaturasEncontradas = null;
			return;
		} else
			mostrarAsignaturasEncontradas = true;
	}

	public void adicionarAsignatura() {

		if (listaAsignaturas.size() == 0) {
			VAsignaturasSIA asignatura = vAsignaturasSIAseleccionada;

			if (listaAsignaturas == null)
				listaAsignaturas = new ArrayList<VAsignaturasSIA>();

			listaAsignaturas.add(asignatura);
			proyectoActual.setAsignaturaSIA(asignatura);
			mostrarAsignaturaSeleccionada = true;

			asignaturasEncontradas.remove(asignatura);
			if (asignaturasEncontradas.size() < 1)
				mostrarAsignaturasEncontradas = false;
			else
				mostrarAsignaturasEncontradas = true;

			cambiosEnAsignaturas = true;
		} else
			FacesContext.getCurrentInstance().addMessage("messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Solo es permitido asociar una asignatura", ""));
	}

	public void eliminarAsignatura() {
		listaAsignaturas.remove(laboratorioDetalleDocenciaSeleccionado);
		if (listaAsignaturas.size() < 1) {
			// listaAsignaturas = null;
			mostrarAsignaturaSeleccionada = false;
		}
		buscarAsignaturasPorCodigo();
		cambiosEnAsignaturas = true;
	}

	public void cambiosAsignaturas(ValueChangeEvent event) {
		System.out.println("Se realizó un cambio de: " + event.getOldValue() + " a: " + event.getNewValue());
		cambiosEnAsignaturas = true;
	}

	// Asignatura

	public boolean isEsProgramaNacional() {
		return esProgramaNacional;
	}

	public void setEsProgramaNacional(boolean esProgramaNacional) {
		this.esProgramaNacional = esProgramaNacional;
	}

	// DEFINICION DE FUNCIONES BASICAS
	protected void cargarValoresIniciales() {
	}

	public String atras() {
		// ///////MODIFICADO GIOVANNI
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
		boolean bandera = false;
		if (man.getItemProyecto() != null) {
			// NavigationMenuItem lis[] =
			// man.getItemProyecto()[0].getNavigationMenuItems();
			MenuItem lis[] = man.getMenuItemArray();
			if (lis != null) {
				for (int i = lis.length - 1; i >= 0; i--) {
					if (bandera) {
						if (lis[i].isRendered()) {

							return lis[i].getOutcome();
						}
					}

					if (lis[i].getOutcome().equals("irInformacionEspecifica")) {
						bandera = true;
					}

				}
			}
		}
		// ////////////////
		// sesion.removeAttribute("manejadorInformacionEspecifica");
		return "irObjetivosResultados";
	}

	public String salir() {
		sesion.removeAttribute("proyecto");
		borrarManejadoresInsercionProyecto();
		return "misProyectos";
	}

	public String salirGuardar() {
		cortarCadenasInfoEspecificaGen();
		String link = "";
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
		boolean bandera = false;
		int pos = 0;

		if (validarCamposObligatorios()) {
			if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
					&& (pos - 1) >= proyectoActual.getFase().intValue()) {
				proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
			}
			if (proyectoActual.getTipoInvestigacion() != null
					&& proyectoActual.getTipoInvestigacion().getId().equals("2040100")) {
				proyectoActual.adicionarCreacionArtistica(creacionArtistica);
			}
			servicioProyecto.ingresarProyecto(proyectoActual);
			if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0) {
				proyectoActual.getProgramaNacional().setId(proyectoActual.getId());
				servicioGeneral.guardarObjeto(proyectoActual.getProgramaNacional());
			}

			if (proyectoActual.getModalidad().getTipo().getId().compareTo("CEI") == 0) {
				Set<SesionCurso> setSesionCurso = new HashSet<SesionCurso>();
				setSesionCurso = proyectoActual.getSesionesECP();
				setSesionCurso.clear();
				setSesionCurso.addAll(listaSesiones);
				proyectoActual.setSesionesECP(setSesionCurso);
				proyectoActual.setAntecedentes(convertirCriteriosEvaluacion());
				servicioGeneral.guardarObjeto(proyectoActual);
			}

			if (proyectoActual.getModalidad().getTipo().getId().compareTo("CMP") == 0
					|| proyectoActual.getModalidad().getTipo().getId().compareTo("CCT") == 0 || mostrarGrupos) {
				if (listaGruposConvMedicina != null && listaGruposConvMedicina.size() > 0) {
					for (int i = 0; i < listaGruposConvMedicina.size(); i++) {
						Grupo g = listaGruposConvMedicina.get(i);
						proyectoActual.adicionarGrupo(g);
					}
				}

				if (listaGruposCorredor != null && listaGruposCorredor.size() > 0) {
					String sGruCCT;
					sGruCCT = convertirGruposCCT();
					proyectoActual.setImpactoEsperado(sGruCCT);
				}

				if (listaCiudadesCCT != null && listaCiudadesCCT.size() > 0) {
					for (int i = 0; i < listaCiudadesCCT.size(); i++) {
						Ciudad c = listaCiudadesCCT.get(i);
						proyectoActual.adicionarCiudad(c);
					}
				}
			}

			if (valoresListasProyectos != null && valoresListasProyectos.size() > 0) {
				for (int i = 0; i < valoresListasProyectos.size(); i++) {
					ValoresListasProyecto g = valoresListasProyectos.get(i);
					servicioGeneral.guardarObjeto(g);
				}
			}
			
			if (valoresListasProyectosParametrizado2 != null && valoresListasProyectosParametrizado2.size() > 0) {
				for (int i = 0; i < valoresListasProyectosParametrizado2.size(); i++) {
					ValoresListasProyecto g = valoresListasProyectosParametrizado2.get(i);
					servicioGeneral.guardarObjeto(g);
				}
			}

			if (valoresListasProyectosBorrados != null && valoresListasProyectosBorrados.size() > 0) {
				for (int i = 0; i < valoresListasProyectosBorrados.size(); i++) {
					ValoresListasProyecto g = valoresListasProyectosBorrados.get(i);
					List<ValoresListasProyecto> listaLineasProyectoAux;
					listaLineasProyectoAux = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
							"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
									+ " and e.tipo = '" + dominioValoresListaProyectos + "'");
					if (listaLineasProyectoAux != null) {
						if (listaLineasProyectoAux.contains(g)) {
							servicioGeneral.eliminarObjeto(g);
						}
					}

				}
			}

			if (listaGruposSUE != null && listaGruposSUE.size() > 0) {
				for (int i = 0; i < listaGruposSUE.size(); i++) {
					ValoresListasProyecto g = listaGruposSUE.get(i);
					servicioGeneral.guardarObjeto(g);
				}
			}

			if (listaGruposSUEBorrados != null && listaGruposSUEBorrados.size() > 0) {
				for (int i = 0; i < listaGruposSUEBorrados.size(); i++) {
					ValoresListasProyecto g = listaGruposSUEBorrados.get(i);
					List<ValoresListasProyecto> listaLineasProyectoAux;
					listaLineasProyectoAux = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
							"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
									+ " and e.tipo = 'GRUPOS_PROYECTO_SUE'");
					if (listaLineasProyectoAux != null) {
						if (listaLineasProyectoAux.contains(g)) {
							servicioGeneral.eliminarObjeto(g);
						}
					}

				}
			}

			if (esConvocatoriaColecciones) {
				for (int i = 0; i < listaTrayectoriaColeccion.size(); i++) {
					TrayectoriaColeccionProyecto lab = listaTrayectoriaColeccion.get(i);
					servicioGeneral.guardarObjeto(lab);
				}

				if (listaTrayectoriaColeccionBorrados != null) {
					if (listaTrayectoriaColeccionBorrados.size() > 0) {
						for (int i = 0; i < listaTrayectoriaColeccionBorrados.size(); i++) {
							TrayectoriaColeccionProyecto lab = listaTrayectoriaColeccionBorrados.get(i);
							if (lab.getId() != null) {
								servicioGeneral.eliminarObjeto(lab);
							}
						}
					}
				}
			}

			if (esProyectoLaboratorios || esProyectoLaboratoriosMod2 /* || mostrarLabConv */) {
				for (int i = 0; i < listaLaboratoriosProyecto.size(); i++) {
					LaboratorioDetalleProyectos lab = listaLaboratoriosProyecto.get(i);
					servicioGeneral.guardarObjeto(lab);
				}

				if (listaLaboratoriosProyectosBorrados != null) {
					if (listaLaboratoriosProyectosBorrados.size() > 0) {
						for (int i = 0; i < listaLaboratoriosProyectosBorrados.size(); i++) {
							LaboratorioDetalleProyectos lab = listaLaboratoriosProyectosBorrados.get(i);
							if (lab.getId() != null)
								servicioGeneral.eliminarObjeto(lab);
						}
					}
				}

			}

			if (esProyectoLaboratoriosMod2) {
				for (int i = 0; i < listaEnsayosAcredicion.size(); i++) {
					LaboratorioEnsayoAcreditacion lab = listaEnsayosAcredicion.get(i);
					servicioGeneral.guardarObjeto(lab);
				}

				if (listaEnsayosAcredicionBorrados != null) {
					if (listaEnsayosAcredicionBorrados.size() > 0) {
						for (int i = 0; i < listaEnsayosAcredicionBorrados.size(); i++) {
							LaboratorioEnsayoAcreditacion lab = listaEnsayosAcredicionBorrados.get(i);
							servicioGeneral.eliminarObjeto(lab);
						}
					}
				}

			}

			if (this.mostrarSiEventos || this.esEventosModUno_2019 || this.esEventosModDos_2019) {
				actualizarListaAreaGestionConocimiento();
			}

			if (proyectoActual.getId() != null) {
				// Ing. Wilver Alexander Martínez Martínez -wam²
				// Cambio - Registro de cambios
				Persona personaAux = new Persona();
				personaAux = (Persona) sesion.getAttribute("persona");

				Formulario formulario = new Formulario();
				List listaFormulario = new ArrayList();

				listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='100'");
				formulario = (Formulario) listaFormulario.get(0);

				HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
				historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
				historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
				historicoFormualrioProyecto.setFormulario(formulario);
				historicoFormualrioProyecto.setProyecto(proyectoActual);
				historicoFormualrioProyecto.setFechaCambio(new Date());
				servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
			}

			if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null) {
				enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
			}

			sesion.removeAttribute("proyecto");
			sesion.removeAttribute("manejadorMenuFormularios");
			borrarManejadoresInsercionProyecto();
			return "misProyectos";
		} else {
			return "";
		}

	}

	public void cortarCadenasInfoEspecificaGen() {

		if (proyectoActual.getConsideracionesEticas() != null) {
			proyectoActual
					.setConsideracionesEticas(controlTamanoCadena(proyectoActual.getConsideracionesEticas(), 3950));
		}
		if (proyectoActual.getJustificacion() != null) {
			proyectoActual.setJustificacion(controlTamanoCadena(proyectoActual.getJustificacion(), 3950));
		}
		if (proyectoActual.getMetodologia() != null) {
			proyectoActual.setMetodologia(controlTamanoCadena(proyectoActual.getMetodologia(), 3950));
		}
		if (proyectoActual.getImpactoEsperado() != null) {
			proyectoActual.setImpactoEsperado(controlTamanoCadena(proyectoActual.getImpactoEsperado(), 3950));
		}
		if (proyectoActual.getDescripcion() != null) {
			proyectoActual.setDescripcion(controlTamanoCadena(proyectoActual.getDescripcion(), 3950));
		}
		if (proyectoActual.getTransferencia() != null) {
			proyectoActual.setTransferencia(controlTamanoCadena(proyectoActual.getTransferencia(), 3950));
		}
	}

	public boolean validarCamposObligatorios() {
		boolean val = true;

		if (mostrarTitulo1Obligatorio) {
			if ((proyectoActual.getConsideracionesEticas() == null
					|| proyectoActual.getConsideracionesEticas().equals("")) && esCadenaVacia(proyectoActual.getCampoGenericoUno())) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualTitulo1_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualTitulo1, "Campo obligatorio");
				}
			}
		}

		if (mostrarTitulo2Obligatorio) {
			if ((proyectoActual.getJustificacion() == null || proyectoActual.getJustificacion().equals("")) && esCadenaVacia(proyectoActual.getCampoGenericoDos())) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualJustificacion_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualJustificacion, "Campo obligatorio");

				}
			}
		}

		if (mostrarTitulo3Obligatorio) {
			if ((proyectoActual.getMetodologia() == null || proyectoActual.getMetodologia().equals("")) && esCadenaVacia(proyectoActual.getCampoGenericoTres())) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualMetodologia_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualMetodologia, "Campo obligatorio");
				}
			}
		}

		if (mostrarTitulo4Obligatorio) {
			if ((proyectoActual.getImpactoEsperado() == null || proyectoActual.getImpactoEsperado().equals("")) && esCadenaVacia(proyectoActual.getCampoGenericoCuatro())) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualTitulo4_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualTitulo4, "Campo obligatorio");
				}
			}
		}

		if (mostrarTitulo5Obligatorio) {
			if ((proyectoActual.getDescripcion() == null || proyectoActual.getDescripcion().equals("")) && esCadenaVacia(proyectoActual.getCampoGenericoCinco())) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualTitulo5_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualTitulo5, "Campo obligatorio");
				}
			}
		}

		if (mostrarTitulo6Obligatorio) {
			if ((proyectoActual.getTransferencia() == null || proyectoActual.getTransferencia().equals("")) && esCadenaVacia(proyectoActual.getCampoGenericoSeis())) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualTitulo6_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualTitulo6, "Campo obligatorio");
				}
			}
		}

		if (mostrarTitulo7Obligatorio) {
			if ((proyectoActual.getApropiacion() == null || proyectoActual.getApropiacion().equals("")) && esCadenaVacia(proyectoActual.getCampoGenericoSiete())) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualTitulo7_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualTitulo7, "Campo obligatorio");
					mensajeError(proyectoActualTitulo7a, "Campo obligatorio");
				}
			}
		}

		if (mostrarTitulo8Obligatorio) {
			if (proyectoActual.getCampoGenericoOcho() == null || proyectoActual.getCampoGenericoOcho().equals("")) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualTitulo8_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualTitulo8, "Campo obligatorio");
				}
			}
		}

		if (mostrarTitulo9Obligatorio) {
			if (proyectoActual.getCampoGenericoNueve() == null || proyectoActual.getCampoGenericoNueve().equals("")) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualTitulo9_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualTitulo9, "Campo obligatorio");
				}
			}
		}

		if (mostrarTitulo10Obligatorio) {
			if (proyectoActual.getCampoGenericoDiez() == null || proyectoActual.getCampoGenericoDiez().equals("")) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualTitulo10_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualTitulo10, "Campo obligatorio");
				}
			}
		}

		if (mostrarTitulo11Obligatorio) {
			if (proyectoActual.getCampoGenericoOnce() == null || proyectoActual.getCampoGenericoOnce().equals("")) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualTitulo11_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualTitulo11, "Campo obligatorio");
				}
			}
		}

		if (mostrarTitulo12Obligatorio) {
			if (proyectoActual.getCampoGenericoDoce() == null || proyectoActual.getCampoGenericoDoce().equals("")) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualTitulo11_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualTitulo12, "Campo obligatorio");
				}
			}
		}
		
		if (mostrarTitulo13Obligatorio) {
			if (proyectoActual.getCampoGenericoTrece() == null || proyectoActual.getCampoGenericoTrece().equals("")) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualTitulo11_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualTitulo13, "Campo obligatorio");
				}
			}
		}
		
		if (mostrarTitulo14Obligatorio) {
			if (proyectoActual.getCampoGenericoCatorce() == null || proyectoActual.getCampoGenericoCatorce().equals("")) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualTitulo11_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualTitulo14, "Campo obligatorio");
				}
			}
		}
		
		if (mostrarTitulo15Obligatorio) {
			if (proyectoActual.getCampoGenericoQuince() == null || proyectoActual.getCampoGenericoQuince().equals("")) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualTitulo11_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualTitulo15, "Campo obligatorio");
				}
			}
		}
		
		if (mostrarTitulo16Obligatorio) {
			if (proyectoActual.getCampoGenericoDieciseis() == null || proyectoActual.getCampoGenericoDieciseis().equals("")) {
				val = false;
				if (esConvCP2019) {
					mensajeError(proyectoActualTitulo11_CP, "Campo obligatorio");
				} else {
					mensajeError(proyectoActualTitulo16, "Campo obligatorio");
				}
			}
		}

		if (mostrarListaSel1Obligatorio) {
			if (proyectoActual.getEjeTematico() == null || proyectoActual.getEjeTematico().equals("")) {
				val = false;
				mensajeError(textoListaSeleccion + ": Campo obligatorio");
			}
		}

		if (mostrarListaSel2Obligatorio) {
			if (proyectoActual.getServicios() == null || proyectoActual.getServicios().equals("")) {
				val = false;
				mensajeError(textoListaSeleccionDos + ": Campo obligatorio");
			}
		}

		if (convocatoriaActual.getRestriccion() != null
				&& (convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M1")
						|| convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M2"))) {
			if (proyectoActual.getCampoGenericoNueve() == null || proyectoActual.getCampoGenericoNueve().equals("")) {
				val = false;
				mensajeError(proyectoActualTitulo8Eventos, "Áreas estratégicas de gestión del conocimiento y Justifique la relación del evento con el cumplimiento de los Objetivos de Desarrollo Sostenible, y las áreas estratégicas de gestión del conocimiento seleccionadas son campos obligatorio");

			}

			if (proyectoActual.getAreaGestionConocimiento().isEmpty()) {
				val = false;
				mensajeError(proyectoActualTitulo8Eventos, "Áreas estratégicas de gestión del conocimiento y Justifique la relación del evento con el cumplimiento de los Objetivos de Desarrollo Sostenible, y las áreas estratégicas de gestión del conocimiento seleccionadas son campos obligatorio");
			}

		}

		if (convocatoriaActual.getRestriccion() != null
				&& convocatoriaActual.getRestriccion().getId().equals("CE1-N2017")) {
			if (proyectoActual.getRei1().equals("NO") && proyectoActual.getRei2().equals("NO")
					&& proyectoActual.getRei3().equals("NO") && proyectoActual.getRei4().equals("NO")
					&& proyectoActual.getRei5().equals("NO") && proyectoActual.getRei6().equals("NO")) {
				val = false;
				mensajeError(
						"Por favor revise la información del carácter del evento, ya que no está cumpliendo los términos de referencia");
			}
		}

		if (convocatoriaActual.getRestriccion() != null
				&& convocatoriaActual.getRestriccion().getId().equals("CE2-N2017")) {
			if (proyectoActual.getRen1().equals("NO") && proyectoActual.getRen2().equals("NO")
					&& proyectoActual.getRen3().equals("NO") && proyectoActual.getRen4().equals("NO")
					&& proyectoActual.getRen5().equals("NO") && proyectoActual.getRen6().equals("NO")
					&& proyectoActual.getRen7().equals("NO")) {
				val = false;
				mensajeError(
						"Por favor revise la información del carácter del evento, ya que no está cumpliendo los términos de referencia");
			}
		}

		if (esConvRepotenciacionLab2018) {
			if (listaLaboratoriosProyecto.isEmpty()) {
				val = false;
				mensajeError("Por favor seleccionar el laboratorio");
			}
		}

//		if(mostrarLabConv){
//			if (listaLaboratoriosProyecto.isEmpty() && numRegLab != 0) {
//				val = false;
//				mensajeError("Por favor seleccionar el laboratorio");
//			}
//		}

		if (mostrarObjResulRespObligatorio && listaObjetivosResultados.isEmpty()) {
			val = false;
			mensajeError("Por favor especificar, al menos, un objetivo específico - resultado - responsable.");
		}

		if (mostrarlistaSeleccionMultiplObligatorio && valoresListasProyectos.isEmpty()) {
			val = false;
			mensajeError(
					"Por favor especificar, al menos, un elemento de la lista '" + textoListaSeleccionMultiple + "'.");
		}
		
		if(convocatoriaActual.isMostrarEquiposServicios() && convocatoriaActual.isAdquisicionEquiposObligatorio() && proyectoActual.getListaEquiposAdquisicion().isEmpty()) {
			val = false;
			mensajeError(
					"Debe ingresar al menos un equipo a adquirir.");
		}

		return val;
	}

	public String siguiente() {
		cortarCadenasInfoEspecificaGen();
		// ///////MODIFICADO GIOVANNI
		String link = "";
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
		boolean bandera = false;
		int pos = 0;
		if (man != null) {
			if (man.getItemProyecto() != null) {
				// NavigationMenuItem lis[] =
				// man.getItemProyecto()[0].getNavigationMenuItems();
				MenuItem lis[] = man.getMenuItemArray();
				if (lis != null) {
					for (int i = 0; i < lis.length; i++) {
						if (bandera) {
							if (lis[i].isRendered()) {
								// sesion.removeAttribute("manejadorMenuFormularios");
								link = lis[i].getOutcome();
								break;
							}
						}

						if (lis[i].getOutcome().equals("irInformacionEspecifica")) {
							bandera = true;
						}
						if (lis[i].isRendered()) {
							pos++;
						}
					}
				}
			}
		}

		if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
				&& (pos - 1) == proyectoActual.getFase().intValue()) {
			proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
		}

		if (proyectoActual.getDependenciasAreaResponsabilidad().size() == 0 && esConvFichaMinimaPosgrados) {
			mensajeError = "El proyecto no tiene dependencias asociadas.";
			FacesContext.getCurrentInstance().addMessage("msgForm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El proyecto no tiene dependencias asociadas", ""));
			return "";
		}

		if (getProyectoEsCreacionArtistica()) {
			proyectoActual.adicionarCreacionArtistica(creacionArtistica);
		}
		
		if (valoresListasProyectosParametrizado2 != null && valoresListasProyectosParametrizado2.size() > 0) {
			for (int i = 0; i < valoresListasProyectosParametrizado2.size(); i++) {
				ValoresListasProyecto g = valoresListasProyectosParametrizado2.get(i);
				servicioGeneral.guardarObjeto(g);
			}
		}

		if (valoresListasProyectos != null && valoresListasProyectos.size() > 0) {
			for (int i = 0; i < valoresListasProyectos.size(); i++) {
				ValoresListasProyecto g = valoresListasProyectos.get(i);
				servicioGeneral.guardarObjeto(g);
			}
		} else {
			if (esConvocatoriaAlianzas2018) {
				mensajeError = "Debe vincular al menos un objetivo de desarrollo sostenible.";
				FacesContext.getCurrentInstance().addMessage("msgForm", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe vincular al menos un objetivo de desarrollo sostenible.", ""));
				return "";
			}
			if (esConvocatoriaAlianzas2019 || esConvSedesPreNal2019 || esConvProyectosBogota2019) {
				mensajeError = "Debe vincular al menos una de las áreas estratégicas en las cuales están enmarcadas las actividades de la alianza o el proyecto.";
				FacesContext.getCurrentInstance().addMessage("msgForm", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe vincular al menos una de las áreas estratégicas en las cuales están enmarcadas las actividades de la alianza o el proyecto.",
						""));
				return "";
			}
			if (esConvUnInnova2019) {
				mensajeError = "Debe vincular al menos un área temática";
				FacesContext.getCurrentInstance().addMessage("msgForm",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe vincular al menos un área temática", ""));
				return "";
			}
		}

		if (valoresListasProyectosBorrados != null && valoresListasProyectosBorrados.size() > 0) {
			for (int i = 0; i < valoresListasProyectosBorrados.size(); i++) {
				ValoresListasProyecto g = valoresListasProyectosBorrados.get(i);
				List<ValoresListasProyecto> listaLineasProyectoAux;
				listaLineasProyectoAux = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
						"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
								+ " and e.tipo = '" + dominioValoresListaProyectos + "'");
				if (listaLineasProyectoAux != null) {
					if (listaLineasProyectoAux.contains(g)) {
						servicioGeneral.eliminarObjeto(g);
					}
				}

			}
		}

		if (listaGruposSUE != null && listaGruposSUE.size() > 0) {
			for (int i = 0; i < listaGruposSUE.size(); i++) {
				ValoresListasProyecto g = listaGruposSUE.get(i);
				servicioGeneral.guardarObjeto(g);
			}
		}

		if (listaGruposSUEBorrados != null && listaGruposSUEBorrados.size() > 0) {
			for (int i = 0; i < listaGruposSUEBorrados.size(); i++) {
				ValoresListasProyecto g = listaGruposSUEBorrados.get(i);
				List<ValoresListasProyecto> listaLineasProyectoAux;
				listaLineasProyectoAux = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
						"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
								+ " and e.tipo = 'GRUPOS_PROYECTO_SUE'");
				if (listaLineasProyectoAux != null) {
					if (listaLineasProyectoAux.contains(g)) {
						servicioGeneral.eliminarObjeto(g);
					}
				}

			}
		}

		if (esConvTraslaMed2016 || mostrarGrupos || esConvTraslaMed2016ModDos
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("CMP") == 0
				|| proyectoActual.getModalidad().getTipo().getId().compareTo("CCT") == 0) {
			if (listaGruposConvMedicina != null && listaGruposConvMedicina.size() > 0) {
				for (int i = 0; i < listaGruposConvMedicina.size(); i++) {
					Grupo g = listaGruposConvMedicina.get(i);
					proyectoActual.adicionarGrupo(g);
				}
			}

			if (listaGruposCorredor != null && listaGruposCorredor.size() > 0) {
				String sGruCCT;
				sGruCCT = convertirGruposCCT();
				proyectoActual.setImpactoEsperado(sGruCCT);
			}

			if (listaCiudadesCCT != null && listaCiudadesCCT.size() > 0) {
				for (int i = 0; i < listaCiudadesCCT.size(); i++) {
					Ciudad c = listaCiudadesCCT.get(i);
					proyectoActual.adicionarCiudad(c);
				}
			}
		}

		if (esConvocatoriaColecciones) {
			for (int i = 0; i < listaTrayectoriaColeccion.size(); i++) {
				TrayectoriaColeccionProyecto lab = listaTrayectoriaColeccion.get(i);
				servicioGeneral.guardarObjeto(lab);
			}

			if (listaTrayectoriaColeccionBorrados != null) {
				if (listaTrayectoriaColeccionBorrados.size() > 0) {
					for (int i = 0; i < listaTrayectoriaColeccionBorrados.size(); i++) {
						TrayectoriaColeccionProyecto lab = listaTrayectoriaColeccionBorrados.get(i);
						if (lab.getId() != null) {
							servicioGeneral.eliminarObjeto(lab);
						}
					}
				}
			}
		}

		if (esProyectoLaboratorios || esProyectoLaboratoriosMod2 /* || mostrarLabConv */) {
			for (int i = 0; i < listaLaboratoriosProyecto.size(); i++) {
				LaboratorioDetalleProyectos lab = listaLaboratoriosProyecto.get(i);
				servicioGeneral.guardarObjeto(lab);
			}

			if (listaLaboratoriosProyectosBorrados != null) {
				if (listaLaboratoriosProyectosBorrados.size() > 0) {
					for (int i = 0; i < listaLaboratoriosProyectosBorrados.size(); i++) {
						LaboratorioDetalleProyectos lab = listaLaboratoriosProyectosBorrados.get(i);
						if (lab.getId() != null)
							servicioGeneral.eliminarObjeto(lab);
					}
				}
			}

		}

		if (esProyectoLaboratoriosMod2) {
			for (int i = 0; i < listaEnsayosAcredicion.size(); i++) {
				LaboratorioEnsayoAcreditacion lab = listaEnsayosAcredicion.get(i);
				lab.setProyecto(proyectoActual);
				servicioGeneral.guardarObjeto(lab);
			}

			if (listaEnsayosAcredicionBorrados != null) {
				if (listaEnsayosAcredicionBorrados.size() > 0) {
					for (int i = 0; i < listaEnsayosAcredicionBorrados.size(); i++) {
						LaboratorioEnsayoAcreditacion lab = listaEnsayosAcredicionBorrados.get(i);
						servicioGeneral.eliminarObjeto(lab);
					}
				}
			}

		}

		if (esConvMan2015 || mostrarObjResulResp) {
			if (listaObjetivosResultados != null && listaObjetivosResultados.size() > 0) {
				for (int i = 0; i < listaObjetivosResultados.size(); i++) {
					ActividadObjetivo g = listaObjetivosResultados.get(i);
					proyectoActual.adicionarObjetivoResultado(g);
				}
			}
		}

		if (this.mostrarSiEventos || this.esEventosModUno_2019 || this.esEventosModDos_2019) {
			actualizarListaAreaGestionConocimiento();
		}

		if (validarCamposObligatorios()) {
			if (esConvCP2019) {
				boolean flag = false;
				EstadoProyecto[] ep = { new EstadoProyecto("P") };
				List<Proyecto> pry = servicioProyecto.obtenerProyectosConvocatoria(ep, proyectoActual.getModalidad());
				for (Proyecto proyecto : pry) {
					if (proyecto.getEjeTematico().equals(proyectoActual.getEjeTematico())) {
						mensajeError = "No pueden haber más de una propuesta asociada al mismo centro de pensamiento.";
						FacesContext.getCurrentInstance().addMessage("msgForm",
								new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, ""));
						flag = true;
					}
				}
				if (esListaVacia(valoresListasProyectos)) {
					mensajeError = "Debe ingresar al menos un área estratégica.";
					FacesContext.getCurrentInstance().addMessage("msgForm",
							new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, ""));
					flag = true;
				}
				if (esCadenaVacia(proyectoActual.getAplicabilidadJustificacionAEExtSol())) {
					mensajeError = "Debe relacionar el aporte del proyecto al cumplimiento de las áreas estratégicas de la Vicerrectoría de Investigación.";
					FacesContext.getCurrentInstance().addMessage("msgForm",
							new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, ""));
					flag = true;
				}
				if (esCadenaVacia(proyectoActual.getAplicabilidadJustificacionExtSol())) {
					mensajeError = "Debe relacionar el aporte del proyecto al cumplimiento de los Objetivos de Desarrollo Sostenible seleccionados.";
					FacesContext.getCurrentInstance().addMessage("msgForm",
							new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, ""));
					flag = true;
				}
				if (flag) {
					return "";
				}
			}
			if (proyectoActual.getModalidad().getId().equals(931L)) {
				EstadoProyecto[] ep = { new EstadoProyecto("P") };
				List<Proyecto> pry = servicioProyecto.obtenerProyectosConvocatoria(ep, proyectoActual.getModalidad());
				List<LaboratorioDetalleProyectos> llab = new ArrayList<LaboratorioDetalleProyectos>();
				for (Proyecto proyecto : pry) {
					String hqlLab = "select #id e.id, #laboratorio e.laboratorio from LaboratorioDetalleProyectos e where e.proyecto = "
							+ proyecto.getId();
					llab = servicioGeneral.obtenerObjetosLimitado(LaboratorioDetalleProyectos.class, hqlLab);
					for (LaboratorioDetalleProyectos l : llab) {
						for (LaboratorioDetalleProyectos l2 : listaLaboratoriosProyecto) {
							if (l2.getLaboratorio().getId().equals(l.getLaboratorio().getId())) {
								mensajeError = "No pueden haber más de una propuesta asociada al mismo laboratorio.";
								FacesContext.getCurrentInstance().addMessage("msgForm",
										new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, ""));
								return "";
							}
						}
					}
				}
			}
			servicioProyecto.ingresarProyecto(proyectoActual);
			if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0) {
				proyectoActual.getProgramaNacional().setId(proyectoActual.getId());
				servicioGeneral.guardarObjeto(proyectoActual.getProgramaNacional());
			}

			if (proyectoActual.getModalidad().getTipo().getId().compareTo("CEI") == 0) {
				Set<SesionCurso> setSesionCurso = new HashSet<SesionCurso>();
				setSesionCurso = proyectoActual.getSesionesECP();
				setSesionCurso.clear();
				setSesionCurso.addAll(listaSesiones);
				proyectoActual.setSesionesECP(setSesionCurso);
				proyectoActual.setAntecedentes(convertirCriteriosEvaluacion());
				servicioGeneral.guardarObjeto(proyectoActual);
			}

			if (proyectoActual.getId() != null) {
				// Ing. Wilver Alexander Martínez Martínez -wam²
				// Cambio - Registro de cambios
				Persona personaAux = new Persona();
				personaAux = (Persona) sesion.getAttribute("persona");

				Formulario formulario = new Formulario();
				List listaFormulario = new ArrayList();

				listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='100'");
				formulario = (Formulario) listaFormulario.get(0);

				HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
				historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
				historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
				historicoFormualrioProyecto.setFormulario(formulario);
				historicoFormualrioProyecto.setProyecto(proyectoActual);
				historicoFormualrioProyecto.setFechaCambio(new Date());
				servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
			}

			if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null) {
				// Se comenta el envio de correo por que en BD no esta creada la plantilla de
				// correo 326 y se desconoce el motivo,
				// lo cual genera excepcion al guardar con rol Estudiante Lider
//          	enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
			}

			sesion.setAttribute("proyecto", proyectoActual);

			sesion.removeAttribute("manejadorInformacionEspecifica");
			sesion.removeAttribute("manejadorBibliografia");
			sesion.removeAttribute("manejadorFichaMinimaHome");
			sesion.removeAttribute("manejadorActividades");
			sesion.removeAttribute("manejadorBibliografia");
			sesion.removeAttribute("manejadorArchivos");

			// ///////MODIFICADO GIOVANNI
			man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
			bandera = false;
			if (man != null && man.getItemProyecto() != null) {
				// NavigationMenuItem lis[] =
				// man.getItemProyecto()[0].getNavigationMenuItems();
				MenuItem lis[] = man.getMenuItemArray();
				if (lis != null) {
					for (int i = 0; i < lis.length; i++) {
						if (bandera) {
							if (lis[i].isRendered()) {
								sesion.removeAttribute("manejadorMenuFormularios");
								// borrarManejadoresInsercionProyecto();
								return lis[i].getOutcome();
							}
						}

						if (lis[i].getOutcome().equals("irInformacionEspecifica")) {
							bandera = true;
						}

					}
				}
			}
			// ////////////////
			sesion.removeAttribute("manejadorMenuFormularios");
			return "irActividades";
		} else {
			return "";
		}

	}

	public boolean getEsSena() {
		return proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.SENA);
	}

	public CreacionArtistica getCreacionArtistica() {
		return creacionArtistica;
	}

	public void setCreacionArtistica(CreacionArtistica creacionArtistica) {
		this.creacionArtistica = creacionArtistica;
	}

	public boolean getProyectoEsCreacionArtistica() {
		if (!(proyectoActual.getModalidad().getTipo().getId().compareTo("CFM") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CEQ") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CMP") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CPU") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CEI") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CJI") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("FMH") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CED") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("RPL") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CTV") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("CCT") == 0)
				&& !(proyectoActual.getModalidad().getTipo().getId().compareTo("RFE") == 0) && !(proyectoActual
						.getModalidad().getTipo().getId().equals(TipoModalidad.REGISTRO_PROYECTOS_LABORATORIOS))) {
			if (proyectoActual.getTipoInvestigacion().getId().equals(TipoInvestigacion.CREACION_ARTISTICA)) {
				return true;
			}
		}

		return false;
	}

	public class CriterioEvaluacionEscuelaInternacional {
		private String criterio;
		private int ponderacion;

		public String getCriterio() {
			return criterio;
		}

		public void setCriterio(String criterio) {
			this.criterio = criterio;
		}

		public int getPonderacion() {
			return ponderacion;
		}

		public void setPonderacion(int ponderacion) {
			this.ponderacion = ponderacion;
		}
	}

	protected List cargarCortes(List cortes) {
		String consulta = "select p from Parametro p where p.nombre = 'CONV_CORTE' and p.valor = '"
				+ convocatoriaActual.getPadre().getId() + "' and p.profesion = 'A' order by p.id";
		List listaCortes = servicioGeneral.obtenerObjetos(consulta);
		cortes = new Vector();
		SelectItem stemp = new SelectItem("", "Seleccione un corte");
		cortes.add(stemp);
		for (Iterator it = listaCortes.iterator(); it.hasNext();) {
			Parametro p = (Parametro) it.next();
			SelectItem s = new SelectItem(p.getId(), p.getDescripcion());
			cortes.add(s);
		}
		return cortes;
	}

	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getTitulo2() {
		return titulo2;
	}

	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}

	public String getTitulo3() {
		return titulo3;
	}

	public void setTitulo3(String titulo3) {
		this.titulo3 = titulo3;
	}

	public boolean isEsExtensionSolidaria() {
		return esExtensionSolidaria;
	}

	public void setEsExtensionSolidaria(boolean esExtensionSolidaria) {
		this.esExtensionSolidaria = esExtensionSolidaria;
	}

	public String getCodigoAsignatura() {
		return codigoAsignatura;
	}

	public void setCodigoAsignatura(String codigoAsignatura) {
		this.codigoAsignatura = codigoAsignatura;
	}

	public List<VAsignaturasSIA> getAsignaturasEncontradas() {
		return asignaturasEncontradas;
	}

	public void setAsignaturasEncontradas(List<VAsignaturasSIA> asignaturasEncontradas) {
		this.asignaturasEncontradas = asignaturasEncontradas;
	}

	public Boolean getCambiosEnAsignaturas() {
		return cambiosEnAsignaturas;
	}

	public void setCambiosEnAsignaturas(Boolean cambiosEnAsignaturas) {
		this.cambiosEnAsignaturas = cambiosEnAsignaturas;
	}

	public VAsignaturasSIA getvAsignaturasSIAseleccionada() {
		return vAsignaturasSIAseleccionada;
	}

	public void setvAsignaturasSIAseleccionada(VAsignaturasSIA vAsignaturasSIAseleccionada) {
		this.vAsignaturasSIAseleccionada = vAsignaturasSIAseleccionada;
	}

	public List<VAsignaturasSIA> getListaAsignaturas() {
		return listaAsignaturas;
	}

	public void setListaAsignaturas(List<VAsignaturasSIA> listaAsignaturas) {
		this.listaAsignaturas = listaAsignaturas;
	}

	public VAsignaturasSIA getLaboratorioDetalleDocenciaSeleccionado() {
		return laboratorioDetalleDocenciaSeleccionado;
	}

	public void setLaboratorioDetalleDocenciaSeleccionado(VAsignaturasSIA laboratorioDetalleDocenciaSeleccionado) {
		this.laboratorioDetalleDocenciaSeleccionado = laboratorioDetalleDocenciaSeleccionado;
	}

	public boolean isMostrarAsignaturasEncontradas() {
		return mostrarAsignaturasEncontradas;
	}

	public void setMostrarAsignaturasEncontradas(boolean mostrarAsignaturasEncontradas) {
		this.mostrarAsignaturasEncontradas = mostrarAsignaturasEncontradas;
	}

	public boolean isMostrarAsignaturaSeleccionada() {
		return mostrarAsignaturaSeleccionada;
	}

	public void setMostrarAsignaturaSeleccionada(boolean mostrarAsignaturaSeleccionada) {
		this.mostrarAsignaturaSeleccionada = mostrarAsignaturaSeleccionada;
	}

	public boolean isEsConvFichaMinima() {
		return esConvFichaMinima;
	}

	public void setEsConvFichaMinima(boolean esConvFichaMinima) {
		this.esConvFichaMinima = esConvFichaMinima;
	}

	public boolean isMateriasSIA() {
		return materiasSIA;
	}

	public void setMateriasSIA(boolean materiasSIA) {
		this.materiasSIA = materiasSIA;
	}

	public String getJustificacionLabel() {
		return justificacionLabel;
	}

	public void setJustificacionLabel(String justificacionLabel) {
		this.justificacionLabel = justificacionLabel;
	}

	public boolean isEsConvIni() {
		return esConvIni;
	}

	public void setEsConvIni(boolean esConvIni) {
		this.esConvIni = esConvIni;
	}

	public boolean isEsConvFichaMinimaPosgrados() {
		return esConvFichaMinimaPosgrados;
	}

	public void setEsConvFichaMinimaPosgrados(boolean esConvFichaMinimaPosgrados) {
		this.esConvFichaMinimaPosgrados = esConvFichaMinimaPosgrados;
	}

	public void setPropiedadIntectualLabel(String propiedadIntectualLabel) {
		this.propiedadIntectualLabel = propiedadIntectualLabel;
	}

	public String getPropiedadIntectualLabel() {
		return propiedadIntectualLabel;
	}

	public void setDependenciaId(String dependenciaId) {
		this.dependenciaId = dependenciaId;
	}

	public String getDependenciaId() {
		return dependenciaId;
	}

	public void setDependenciaItem(SelectItem[] dependenciaItem) {
		this.dependenciaItem = dependenciaItem;
	}

	public SelectItem[] getDependenciaItem() {
		return dependenciaItem;
	}

	public void setListaDependencia(List listaDependencia) {
		this.listaDependencia = listaDependencia;
	}

	public List getListaDependencia() {
		return listaDependencia;
	}

	public void setDependenciaAreaResponsabilidad(DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad) {
		this.dependenciaAreaResponsabilidad = dependenciaAreaResponsabilidad;
	}

	public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidad() {
		return dependenciaAreaResponsabilidad;
	}

	public void setDependenciaActual(Dependencia dependenciaActual) {
		this.dependenciaActual = dependenciaActual;
	}

	public Dependencia getDependenciaActual() {
		return dependenciaActual;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidadSeleccionada() {
		return dependenciaAreaResponsabilidadSeleccionada;
	}

	public void setDependenciaAreaResponsabilidadSeleccionada(
			DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada) {
		this.dependenciaAreaResponsabilidadSeleccionada = dependenciaAreaResponsabilidadSeleccionada;
	}

	public boolean isEsConvFichaMinimaPosgradosMod3() {
		return esConvFichaMinimaPosgradosMod3;
	}

	public void setEsConvFichaMinimaPosgradosMod3(boolean esConvFichaMinimaPosgradosMod3) {
		this.esConvFichaMinimaPosgradosMod3 = esConvFichaMinimaPosgradosMod3;
	}

	public boolean isEsEventosModUno() {
		return esEventosModUno;
	}

	public void setEsEventosModUno(boolean esEventosModUno) {
		this.esEventosModUno = esEventosModUno;
	}

	public boolean isEsEventosModDos() {
		return esEventosModDos;
	}

	public void setEsEventosModDos(boolean esEventosModDos) {
		this.esEventosModDos = esEventosModDos;
	}

	public boolean isMostrarSiEventos() {
		return mostrarSiEventos;
	}

	public void setMostrarSiEventos(boolean mostrarSiEventos) {
		this.mostrarSiEventos = mostrarSiEventos;
	}

	public boolean isMostrarSiEscuelaInternacional() {
		return mostrarSiEscuelaInternacional;
	}

	public void setMostrarSiEscuelaInternacional(boolean mostrarSiEscuelaInternacional) {
		this.mostrarSiEscuelaInternacional = mostrarSiEscuelaInternacional;
	}

	public boolean isMostrarSiFichaMinima() {
		return mostrarSiFichaMinima;
	}

	public void setMostrarSiFichaMinima(boolean mostrarSiFichaMinima) {
		this.mostrarSiFichaMinima = mostrarSiFichaMinima;
	}

	public Long getNumeroSesion() {
		return numeroSesion;
	}

	public void setNumeroSesion(Long numeroSesion) {
		this.numeroSesion = numeroSesion;
	}

	public String getTemaSesion() {
		return temaSesion;
	}

	public void setTemaSesion(String temaSesion) {
		this.temaSesion = temaSesion;
	}

	public String getContenidoSesion() {
		return contenidoSesion;
	}

	public void setContenidoSesion(String contenidoSesion) {
		this.contenidoSesion = contenidoSesion;
	}

	public List<SesionCurso> getListaSesiones() {
		return listaSesiones;
	}

	public void setListaSesiones(List<SesionCurso> listaSesiones) {
		this.listaSesiones = listaSesiones;
	}

	public SesionCurso getSesionSeleccionada() {
		return sesionSeleccionada;
	}

	public void setSesionSeleccionada(SesionCurso sesionSeleccionada) {
		this.sesionSeleccionada = sesionSeleccionada;
	}

	public String getCriterioEvaluacion() {
		return criterioEvaluacion;
	}

	public void setCriterioEvaluacion(String criterioEvaluacion) {
		this.criterioEvaluacion = criterioEvaluacion;
	}

	public String getPonderacionEvaluacion() {
		return ponderacionEvaluacion;
	}

	public void setPonderacionEvaluacion(String ponderacionEvaluacion) {
		this.ponderacionEvaluacion = ponderacionEvaluacion;
	}

	public List<CriterioEvaluacionEscuelaInternacional> getCriteriosEscuela() {
		return criteriosEscuela;
	}

	public void setCriteriosEscuela(List<CriterioEvaluacionEscuelaInternacional> criteriosEscuela) {
		this.criteriosEscuela = criteriosEscuela;
	}

	public CriterioEvaluacionEscuelaInternacional getCriterioSeleccionado() {
		return criterioSeleccionado;
	}

	public void setCriterioSeleccionado(CriterioEvaluacionEscuelaInternacional criterioSeleccionado) {
		this.criterioSeleccionado = criterioSeleccionado;
	}

	public boolean isEsProyectoInnoModDos() {
		return esProyectoInnoModDos;
	}

	public void setEsProyectoInnoModDos(boolean esProyectoInnoModDos) {
		this.esProyectoInnoModDos = esProyectoInnoModDos;
	}

	public void cargarConvocatoriaActual() {

		if (proyectoActual.getId() != null) {

			List listConvocatorias = servicioGeneral.obtenerObjetos(
					"select e from Convocatoria e where e.id = " + proyectoActual.getModalidad().getId());
			if (listConvocatorias.size() > 0 && listConvocatorias != null) {
				convocatoriaActual = (Convocatoria) listConvocatorias.get(0);
			}

//			if(convocatoriaActual.getMostrarLaboratorios() != null){
//				if(convocatoriaActual.getMostrarLaboratorios().equals("S")){
//					mostrarLabConv = true;
//					if(convocatoriaActual.getNumeroLaboratoriosValidacion() != null){
//						numLabsVal = convocatoriaActual.getNumeroLaboratoriosValidacion();
//					}else{
//						numLabsVal = 1L;
//					}
//					listaLaboratoriosProyecto = new ArrayList<LaboratorioDetalleProyectos>();
//					listaLaboratoriosProyectosBorrados = new ArrayList<LaboratorioDetalleProyectos>();
//					mUL = new ManejadorUtilidadesLaboratorios();
//					String hqlLab = "select #id e.id, #laboratorio e.laboratorio from LaboratorioDetalleProyectos e where e.proyecto = "
//							+ proyectoActual.getId();
//					listaLaboratoriosProyecto = servicioGeneral
//							.obtenerObjetosLimitado(
//									LaboratorioDetalleProyectos.class,
//									hqlLab);
//					cargarLaboratorios();
//				}else{
//					mostrarLabConv = false;
//				}
//			}else{
//				mostrarLabConv = false;
//			}

			if (convocatoriaActual.getRegistrosPorLaboratorio() != null) {
				numRegLab = convocatoriaActual.getRegistrosPorLaboratorio();
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_INI")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_POSG_3"))) {
				esConvIni = true;

			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_PROY_2016_2018")) {
				esConvProyectos2016_2018 = true;
			}

			if (convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion().getId()
					.equals(RestriccionConvocatoria.CONV_NAL_PRY_2017_18)) {
				esConvProyectos2017_2018 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("MAT_SIA")) {
				materiasSIA = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("FM")) {
				verFichaMin = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_POSG")) {
				esPosgrado1_2 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CE1")
							|| convocatoriaActual.getRestriccion().getId().equals("CE2"))) {
				mostrarSiConvocatoriaEventos = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_INNO_MOD_2_3"))) {
				esProyectoInnoModDos = true;
			}

		} else {
			Long idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");

			List listConvocatorias = servicioGeneral
					.obtenerObjetos("select e from Convocatoria e where e.id = " + idConvocatoria);
			if (listConvocatorias.size() > 0 && listConvocatorias != null) {
				convocatoriaActual = (Convocatoria) listConvocatorias.get(0);
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_INI")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_POSG_3"))) {
				esConvIni = true;

			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_PROY_2016_2018")) {
				esConvProyectos2016_2018 = true;
			}

			if (convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion().getId()
					.equals(RestriccionConvocatoria.CONV_NAL_PRY_2017_18)) {
				esConvProyectos2017_2018 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("MAT_SIA")) {
				materiasSIA = true;
			}
			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("FM")) {
				verFichaMin = true;
			}
			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CE1")
							|| convocatoriaActual.getRestriccion().getId().equals("CE2"))) {
				mostrarSiConvocatoriaEventos = true;
			}
			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_POSG")) {
				esPosgrado1_2 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_INNO_MOD_2_3"))) {
				esProyectoInnoModDos = true;
			}
		}

	}

	public class GruposExternosCorredor {

		private String nombreGrupo;
		private String entidadGrupo;

		public GruposExternosCorredor() {
			super();
		}

		public GruposExternosCorredor(String nombreGrupo, String entidadGrupo) {
			super();
			this.nombreGrupo = nombreGrupo;
			this.entidadGrupo = entidadGrupo;
		}

		public String getNombreGrupo() {
			return nombreGrupo;
		}

		public void setNombreGrupo(String nombreGrupo) {
			this.nombreGrupo = nombreGrupo;
		}

		public String getEntidadGrupo() {
			return entidadGrupo;
		}

		public void setEntidadGrupo(String entidadGrupo) {
			this.entidadGrupo = entidadGrupo;
		}

	}

	public String getLinkAreasEstrategicas() {
		String viewId = "/pages/descargas/AreasEstrategicas.pdf";
		viewId = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + viewId;
		return viewId;
	}

	public void agregarGrupoCCT() {

		GruposExternosCorredor gcct = new GruposExternosCorredor();
		gcct.setNombreGrupo(nombreGrupoCCT);
		gcct.setEntidadGrupo(entidadGrupoCCT);
		listaGruposCorredor.add(gcct);
		nombreGrupoCCT = "";
		entidadGrupoCCT = "";

	}

	public void eliminarGrupoCCT() {
		listaGruposCorredor.remove(grupoCCTSeleccionado);
		grupoCCTSeleccionado = new GruposExternosCorredor();
	}

	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
	}

	public List getCortes() {
		return cortes;
	}

	public void setCortes(List cortes) {
		this.cortes = cortes;
	}

	public boolean isEsConvJI_SEM() {
		return esConvJI_SEM;
	}

	public void setEsConvJI_SEM(boolean esConvJI_SEM) {
		this.esConvJI_SEM = esConvJI_SEM;
	}

	public boolean isEsConvSem_Col() {
		return esConvSem_Col;
	}

	public void setEsConvSem_Col(boolean esConvSem_Col) {
		this.esConvSem_Col = esConvSem_Col;
	}

	public boolean isEsConvAuxPos() {
		return esConvAuxPos;
	}

	public void setEsConvAuxPos(boolean esConvAuxPos) {
		this.esConvAuxPos = esConvAuxPos;
	}

	public boolean isEsProyectoInnoModDosUn() {
		return esProyectoInnoModDosUn;
	}

	public void setEsProyectoInnoModDosUn(boolean esProyectoInnoModDosUn) {
		this.esProyectoInnoModDosUn = esProyectoInnoModDosUn;
	}

	public boolean isEsConvDere() {
		return esConvDere;
	}

	public void setEsConvDere(boolean esConvDere) {
		this.esConvDere = esConvDere;
	}

	public boolean isEsConvJI_2014() {
		return esConvJI_2014;
	}

	public void setEsConvJI_2014(boolean esConvJI_2014) {
		this.esConvJI_2014 = esConvJI_2014;
	}

	public boolean isEsConvArtes() {
		return esConvArtes;
	}

	public void setEsConvArtes(boolean esConvArtes) {
		this.esConvArtes = esConvArtes;
	}

	public boolean isEsConvUnijus() {
		return esConvUnijus;
	}

	public void setEsConvUnijus(boolean esConvUnijus) {
		this.esConvUnijus = esConvUnijus;
	}

	public boolean isEsConvMan2014() {
		return esConvMan2014;
	}

	public void setEsConvMan2014(boolean esConvMan2014) {
		this.esConvMan2014 = esConvMan2014;
	}

	public boolean isEsTiempoVolver() {
		return esTiempoVolver;
	}

	public void setEsTiempoVolver(boolean esTiempoVolver) {
		this.esTiempoVolver = esTiempoVolver;
	}

	public List<Grupo> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public SelectItem[] getGruposInvItem() {
		return gruposInvItem;
	}

	public void setGruposInvItem(SelectItem[] gruposInvItem) {
		this.gruposInvItem = gruposInvItem;
	}

	public boolean isEsConvPurdue() {
		return esConvPurdue;
	}

	public void setEsConvPurdue(boolean esConvPurdue) {
		this.esConvPurdue = esConvPurdue;
	}

	public boolean isEsConvocatoriaMedicina2015() {
		return esConvocatoriaMedicina2015;
	}

	public void setEsConvocatoriaMedicina2015(boolean esConvocatoriaMedicina2015) {
		this.esConvocatoriaMedicina2015 = esConvocatoriaMedicina2015;
	}

	public List<Grupo> getListaGruposConvMedicina() {
		return listaGruposConvMedicina;
	}

	public void setListaGruposConvMedicina(List<Grupo> listaGruposConvMedicina) {
		this.listaGruposConvMedicina = listaGruposConvMedicina;
	}

	public Grupo getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	public void setGrupoSeleccionado(Grupo grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}

	public boolean isEsConvCienciasAgraEquipos2015() {
		return esConvCienciasAgraEquipos2015;
	}

	public void setEsConvCienciasAgraEquipos2015(boolean esConvCienciasAgraEquipos2015) {
		this.esConvCienciasAgraEquipos2015 = esConvCienciasAgraEquipos2015;
	}

	public SelectItem[] getLaboratoriosFacItem() {
		return laboratoriosFacItem;
	}

	public void setLaboratoriosFacItem(SelectItem[] laboratoriosFacItem) {
		this.laboratoriosFacItem = laboratoriosFacItem;
	}

	public List<Laboratorio> getListaLaboratorios() {
		return listaLaboratorios;
	}

	public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
		this.listaLaboratorios = listaLaboratorios;
	}

	public boolean isEsCorredorTecnologico() {
		return esCorredorTecnologico;
	}

	public void setEsCorredorTecnologico(boolean esCorredorTecnologico) {
		this.esCorredorTecnologico = esCorredorTecnologico;
	}

	public List<Ciudad> getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List<Ciudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}

	public SelectItem[] getCiudadItem() {
		return ciudadItem;
	}

	public void setCiudadItem(SelectItem[] ciudadItem) {
		this.ciudadItem = ciudadItem;
	}

	public boolean isEsCorredorTecnologicoExterno() {
		return esCorredorTecnologicoExterno;
	}

	public void setEsCorredorTecnologicoExterno(boolean esCorredorTecnologicoExterno) {
		this.esCorredorTecnologicoExterno = esCorredorTecnologicoExterno;
	}

	public List<GruposExternosCorredor> getListaGruposCorredor() {
		return listaGruposCorredor;
	}

	public void setListaGruposCorredor(List<GruposExternosCorredor> listaGruposCorredor) {
		this.listaGruposCorredor = listaGruposCorredor;
	}

	public String getNombreGrupoCCT() {
		return nombreGrupoCCT;
	}

	public void setNombreGrupoCCT(String nombreGrupoCCT) {
		this.nombreGrupoCCT = nombreGrupoCCT;
	}

	public String getEntidadGrupoCCT() {
		return entidadGrupoCCT;
	}

	public void setEntidadGrupoCCT(String entidadGrupoCCT) {
		this.entidadGrupoCCT = entidadGrupoCCT;
	}

	public GruposExternosCorredor getGrupoCCTSeleccionado() {
		return grupoCCTSeleccionado;
	}

	public void setGrupoCCTSeleccionado(GruposExternosCorredor grupoCCTSeleccionado) {
		this.grupoCCTSeleccionado = grupoCCTSeleccionado;
	}

	public List<Ciudad> getListaCiudadesCCT() {
		return listaCiudadesCCT;
	}

	public void setListaCiudadesCCT(List<Ciudad> listaCiudadesCCT) {
		this.listaCiudadesCCT = listaCiudadesCCT;
	}

	public Ciudad getCiudadSeleccionada() {
		return ciudadSeleccionada;
	}

	public void setCiudadSeleccionada(Ciudad ciudadSeleccionada) {
		this.ciudadSeleccionada = ciudadSeleccionada;
	}

	public String getCiudadCCT() {
		return ciudadCCT;
	}

	public void setCiudadCCT(String ciudadCCT) {
		this.ciudadCCT = ciudadCCT;
	}

	public boolean isEsFichaExterna() {
		return esFichaExterna;
	}

	public void setEsFichaExterna(boolean esFichaExterna) {
		this.esFichaExterna = esFichaExterna;
	}

	public boolean isEsConvIEU() {
		return esConvIEU;
	}

	public void setEsConvIEU(boolean esConvIEU) {
		this.esConvIEU = esConvIEU;
	}

	public SelectItem[] getListaObjetivos() {
		return listaObjetivos;
	}

	public void setListaObjetivos(SelectItem[] listaObjetivos) {
		this.listaObjetivos = listaObjetivos;
	}

	public ObjetivoEspecifico getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(ObjetivoEspecifico objetivo) {
		this.objetivo = objetivo;
	}

	public SelectItem[] getListaResultados() {
		return listaResultados;
	}

	public void setListaResultados(SelectItem[] listaResultados) {
		this.listaResultados = listaResultados;
	}

	public ResultadoProyecto getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoProyecto resultado) {
		this.resultado = resultado;
	}

	public SelectItem[] getListaParticipantes() {
		return listaParticipantes;
	}

	public void setListaParticipantes(SelectItem[] listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
	}

	public InvestigadorProyecto getInvParticipante() {
		return invParticipante;
	}

	public void setInvParticipante(InvestigadorProyecto invParticipante) {
		this.invParticipante = invParticipante;
	}

	public boolean isEsConvMan2015() {
		return esConvMan2015;
	}

	public void setEsConvMan2015(boolean esConvMan2015) {
		this.esConvMan2015 = esConvMan2015;
	}

	public List<ActividadObjetivo> getListaObjetivosResultados() {
		return listaObjetivosResultados;
	}

	public void setListaObjetivosResultados(List<ActividadObjetivo> listaObjetivosResultados) {
		this.listaObjetivosResultados = listaObjetivosResultados;
	}

	public ActividadObjetivo getObjetivoSeleccionado() {
		return objetivoSeleccionado;
	}

	public void setObjetivoSeleccionado(ActividadObjetivo objetivoSeleccionado) {
		this.objetivoSeleccionado = objetivoSeleccionado;
	}

	public String getIdInvPry() {
		return idInvPry;
	}

	public void setIdInvPry(String idInvPry) {
		this.idInvPry = idInvPry;
	}

	public String getIdObj() {
		return idObj;
	}

	public void setIdObj(String idObj) {
		this.idObj = idObj;
	}

	public String getIdRes() {
		return idRes;
	}

	public void setIdRes(String idRes) {
		this.idRes = idRes;
	}

	public boolean isEsConvCienciasMed2015() {
		return esConvCienciasMed2015;
	}

	public void setEsConvCienciasMed2015(boolean esConvCienciasMed2015) {
		this.esConvCienciasMed2015 = esConvCienciasMed2015;
	}

	public boolean isEsConvGruposCaribe2015() {
		return esConvGruposCaribe2015;
	}

	public void setEsConvGruposCaribe2015(boolean esConvGruposCaribe2015) {
		this.esConvGruposCaribe2015 = esConvGruposCaribe2015;
	}

	public boolean isEsConvInnoPedagoBog2015() {
		return esConvInnoPedagoBog2015;
	}

	public void setEsConvInnoPedagoBog2015(boolean esConvInnoPedagoBog2015) {
		this.esConvInnoPedagoBog2015 = esConvInnoPedagoBog2015;
	}

	public boolean isEsConvArtes2015() {
		return esConvArtes2015;
	}

	public void setEsConvArtes2015(boolean esConvArtes2015) {
		this.esConvArtes2015 = esConvArtes2015;
	}

	public boolean isEsConvArqMed2015() {
		return esConvArqMed2015;
	}

	public void setEsConvArqMed2015(boolean esConvArqMed2015) {
		this.esConvArqMed2015 = esConvArqMed2015;
	}

	public boolean isEsConvoOFB2015_MOD_1_2() {
		return esConvoOFB2015_MOD_1_2;
	}

	public void setEsConvoOFB2015_MOD_1_2(boolean esConvoOFB2015_MOD_1_2) {
		this.esConvoOFB2015_MOD_1_2 = esConvoOFB2015_MOD_1_2;
	}

	public boolean isEsConvoOFB2015_MOD_3_4() {
		return esConvoOFB2015_MOD_3_4;
	}

	public void setEsConvoOFB2015_MOD_3_4(boolean esConvoOFB2015_MOD_3_4) {
		this.esConvoOFB2015_MOD_3_4 = esConvoOFB2015_MOD_3_4;
	}

	public String getTitulo4() {
		return titulo4;
	}

	public void setTitulo4(String titulo4) {
		this.titulo4 = titulo4;
	}

	public String getTitulo5() {
		return titulo5;
	}

	public void setTitulo5(String titulo5) {
		this.titulo5 = titulo5;
	}

	public boolean isMostrarTitulo4() {
		return mostrarTitulo4;
	}

	public void setMostrarTitulo4(boolean mostrarTitulo4) {
		this.mostrarTitulo4 = mostrarTitulo4;
	}

	public boolean isMostrarTitulo5() {
		return mostrarTitulo5;
	}

	public void setMostrarTitulo5(boolean mostrarTitulo5) {
		this.mostrarTitulo5 = mostrarTitulo5;
	}

	public boolean isEsConvMaestrosArte2015() {
		return esConvMaestrosArte2015;
	}

	public void setEsConvMaestrosArte2015(boolean esConvMaestrosArte2015) {
		this.esConvMaestrosArte2015 = esConvMaestrosArte2015;
	}

	public boolean isEsConvoHumanasManizales2016() {
		return esConvoHumanasManizales2016;
	}

	public void setEsConvoHumanasManizales2016(boolean esConvoHumanasManizales2016) {
		this.esConvoHumanasManizales2016 = esConvoHumanasManizales2016;
	}

	public boolean isEsConvoBiotecManizales2016() {
		return esConvoBiotecManizales2016;
	}

	public void setEsConvoBiotecManizales2016(boolean esConvoBiotecManizales2016) {
		this.esConvoBiotecManizales2016 = esConvoBiotecManizales2016;
	}

	public SelectItem[] getListaAreaTematicaConvsMan2016() {
		return listaAreaTematicaConvsMan2016;
	}

	public void setListaAreaTematicaConvsMan2016(SelectItem[] listaAreaTematicaConvsMan2016) {
		this.listaAreaTematicaConvsMan2016 = listaAreaTematicaConvsMan2016;
	}

	/**
	 * @return the esProyectoLaboratorios
	 */
	public Boolean getEsProyectoLaboratorios() {
		return esProyectoLaboratorios;
	}

	public boolean isEsConvTraslaMed2016() {
		return esConvTraslaMed2016;
	}

	public void setEsConvTraslaMed2016(boolean esConvTraslaMed2016) {
		this.esConvTraslaMed2016 = esConvTraslaMed2016;
	}

	public boolean isEsConvTraslaMed2016ModDos() {
		return esConvTraslaMed2016ModDos;
	}

	public void setEsConvTraslaMed2016ModDos(boolean esConvTraslaMed2016ModDos) {
		this.esConvTraslaMed2016ModDos = esConvTraslaMed2016ModDos;
	}

	public boolean isEsConvCamposGenericos() {
		return esConvCamposGenericos;
	}

	public void setEsConvCamposGenericos(boolean esConvCamposGenericos) {
		this.esConvCamposGenericos = esConvCamposGenericos;
	}

	public boolean isEsConvBejarano() {
		return esConvBejarano;
	}

	public void setEsConvBejarano(boolean esConvBejarano) {
		this.esConvBejarano = esConvBejarano;
	}

	public boolean isEsConvAdmonMan() {
		return esConvAdmonMan;
	}

	public void setEsConvAdmonMan(boolean esConvAdmonMan) {
		this.esConvAdmonMan = esConvAdmonMan;
	}

	public boolean isMostrarTitulo1() {
		return mostrarTitulo1;
	}

	public void setMostrarTitulo1(boolean mostrarTitulo1) {
		this.mostrarTitulo1 = mostrarTitulo1;
	}

	public boolean isMostrarTitulo2() {
		return mostrarTitulo2;
	}

	public void setMostrarTitulo2(boolean mostrarTitulo2) {
		this.mostrarTitulo2 = mostrarTitulo2;
	}

	public boolean isMostrarTitulo3() {
		return mostrarTitulo3;
	}

	public void setMostrarTitulo3(boolean mostrarTitulo3) {
		this.mostrarTitulo3 = mostrarTitulo3;
	}

	public boolean isMostarAsignatura() {
		return mostarAsignatura;
	}

	public void setMostarAsignatura(boolean mostarAsignatura) {
		this.mostarAsignatura = mostarAsignatura;
	}

	public boolean isMostrarObjResulResp() {
		return mostrarObjResulResp;
	}

	public void setMostrarObjResulResp(boolean mostrarObjResulResp) {
		this.mostrarObjResulResp = mostrarObjResulResp;
	}

	public boolean isMostrarLugarEjecucion() {
		return mostrarLugarEjecucion;
	}

	public void setMostrarLugarEjecucion(boolean mostrarLugarEjecucion) {
		this.mostrarLugarEjecucion = mostrarLugarEjecucion;
	}

	public boolean isMostrarGrupos() {
		return mostrarGrupos;
	}

	public void setMostrarGrupos(boolean mostrarGrupos) {
		this.mostrarGrupos = mostrarGrupos;
	}

	public boolean isMostrarMarcoTeorico() {
		return mostrarMarcoTeorico;
	}

	public void setMostrarMarcoTeorico(boolean mostrarMarcoTeorico) {
		this.mostrarMarcoTeorico = mostrarMarcoTeorico;
	}

	public boolean isEsConvProyectos2016_2018() {
		return esConvProyectos2016_2018;
	}

	public void setEsConvProyectos2016_2018(boolean esConvProyectos2016_2018) {
		this.esConvProyectos2016_2018 = esConvProyectos2016_2018;
	}

	public boolean isEsConvoIEU2016() {
		return esConvoIEU2016;
	}

	public void setEsConvoIEU2016(boolean esConvoIEU2016) {
		this.esConvoIEU2016 = esConvoIEU2016;
	}

	public boolean isEsConvCienciasBasicasMan2016() {
		return esConvCienciasBasicasMan2016;
	}

	public void setEsConvCienciasBasicasMan2016(boolean esConvCienciasBasicasMan2016) {
		this.esConvCienciasBasicasMan2016 = esConvCienciasBasicasMan2016;
	}

	public String getTitulo6() {
		return titulo6;
	}

	public void setTitulo6(String titulo6) {
		this.titulo6 = titulo6;
	}

	public boolean isMostrarTitulo6() {
		return mostrarTitulo6;
	}

	public void setMostrarTitulo6(boolean mostrarTitulo6) {
		this.mostrarTitulo6 = mostrarTitulo6;
	}

	public boolean isMostrarListaSeleccion() {
		return mostrarListaSeleccion;
	}

	public void setMostrarListaSeleccion(boolean mostrarListaSeleccion) {
		this.mostrarListaSeleccion = mostrarListaSeleccion;
	}

	public String getTextoListaSeleccion() {
		return textoListaSeleccion;
	}

	public void setTextoListaSeleccion(String textoListaSeleccion) {
		this.textoListaSeleccion = textoListaSeleccion;
	}

	public String getDominioListaSeleccion() {
		return dominioListaSeleccion;
	}

	public void setDominioListaSeleccion(String dominioListaSeleccion) {
		this.dominioListaSeleccion = dominioListaSeleccion;
	}

	public boolean isMostrarListaSeleccionDos() {
		return mostrarListaSeleccionDos;
	}

	public void setMostrarListaSeleccionDos(boolean mostrarListaSeleccionDos) {
		this.mostrarListaSeleccionDos = mostrarListaSeleccionDos;
	}

	public String getTextoListaSeleccionDos() {
		return textoListaSeleccionDos;
	}

	public void setTextoListaSeleccionDos(String textoListaSeleccionDos) {
		this.textoListaSeleccionDos = textoListaSeleccionDos;
	}

	public String getDominioListaSeleccionDos() {
		return dominioListaSeleccionDos;
	}

	public void setDominioListaSeleccionDos(String dominioListaSeleccionDos) {
		this.dominioListaSeleccionDos = dominioListaSeleccionDos;
	}

	public SelectItem[] getListaSeleccionDos() {
		return listaSeleccionDos;
	}

	public void setListaSeleccionDos(SelectItem[] listaSeleccionDos) {
		this.listaSeleccionDos = listaSeleccionDos;
	}

	public boolean isMostrarTitulo1Obligatorio() {
		return mostrarTitulo1Obligatorio;
	}

	public void setMostrarTitulo1Obligatorio(boolean mostrarTitulo1Obligatorio) {
		this.mostrarTitulo1Obligatorio = mostrarTitulo1Obligatorio;
	}

	public boolean isMostrarTitulo2Obligatorio() {
		return mostrarTitulo2Obligatorio;
	}

	public void setMostrarTitulo2Obligatorio(boolean mostrarTitulo2Obligatorio) {
		this.mostrarTitulo2Obligatorio = mostrarTitulo2Obligatorio;
	}

	public boolean isMostrarTitulo3Obligatorio() {
		return mostrarTitulo3Obligatorio;
	}

	public void setMostrarTitulo3Obligatorio(boolean mostrarTitulo3Obligatorio) {
		this.mostrarTitulo3Obligatorio = mostrarTitulo3Obligatorio;
	}

	public boolean isMostrarTitulo4Obligatorio() {
		return mostrarTitulo4Obligatorio;
	}

	public void setMostrarTitulo4Obligatorio(boolean mostrarTitulo4Obligatorio) {
		this.mostrarTitulo4Obligatorio = mostrarTitulo4Obligatorio;
	}

	public boolean isMostrarTitulo5Obligatorio() {
		return mostrarTitulo5Obligatorio;
	}

	public void setMostrarTitulo5Obligatorio(boolean mostrarTitulo5Obligatorio) {
		this.mostrarTitulo5Obligatorio = mostrarTitulo5Obligatorio;
	}

	public boolean isMostrarTitulo6Obligatorio() {
		return mostrarTitulo6Obligatorio;
	}

	public void setMostrarTitulo6Obligatorio(boolean mostrarTitulo6Obligatorio) {
		this.mostrarTitulo6Obligatorio = mostrarTitulo6Obligatorio;
	}

	public UIComponent getProyectoActualTitulo1() {
		return proyectoActualTitulo1;
	}

	public void setProyectoActualTitulo1(UIComponent proyectoActualTitulo1) {
		this.proyectoActualTitulo1 = proyectoActualTitulo1;
	}

	public UIComponent getProyectoActualJustificacion() {
		return proyectoActualJustificacion;
	}

	public void setProyectoActualJustificacion(UIComponent proyectoActualJustificacion) {
		this.proyectoActualJustificacion = proyectoActualJustificacion;
	}

	public UIComponent getProyectoActualMetodologia() {
		return proyectoActualMetodologia;
	}

	public void setProyectoActualMetodologia(UIComponent proyectoActualMetodologia) {
		this.proyectoActualMetodologia = proyectoActualMetodologia;
	}

	public UIComponent getProyectoActualTitulo4() {
		return proyectoActualTitulo4;
	}

	public void setProyectoActualTitulo4(UIComponent proyectoActualTitulo4) {
		this.proyectoActualTitulo4 = proyectoActualTitulo4;
	}

	public UIComponent getProyectoActualTitulo5() {
		return proyectoActualTitulo5;
	}

	public void setProyectoActualTitulo5(UIComponent proyectoActualTitulo5) {
		this.proyectoActualTitulo5 = proyectoActualTitulo5;
	}

	public UIComponent getProyectoActualTitulo6() {
		return proyectoActualTitulo6;
	}

	public void setProyectoActualTitulo6(UIComponent proyectoActualTitulo6) {
		this.proyectoActualTitulo6 = proyectoActualTitulo6;
	}

	/**
	 * @return the titulo7
	 */
	public String getTitulo7() {
		return titulo7;
	}

	/**
	 * @param titulo7 the titulo7 to set
	 */
	public void setTitulo7(String titulo7) {
		this.titulo7 = titulo7;
	}

	/**
	 * @return the mostrarTitulo7
	 */
	public boolean isMostrarTitulo7() {
		return mostrarTitulo7;
	}

	/**
	 * @param mostrarTitulo7 the mostrarTitulo7 to set
	 */
	public void setMostrarTitulo7(boolean mostrarTitulo7) {
		this.mostrarTitulo7 = mostrarTitulo7;
	}

	/**
	 * @return the mostrarTitulo7Obligatorio
	 */
	public boolean isMostrarTitulo7Obligatorio() {
		return mostrarTitulo7Obligatorio;
	}

	/**
	 * @param mostrarTitulo7Obligatorio the mostrarTitulo7Obligatorio to set
	 */
	public void setMostrarTitulo7Obligatorio(boolean mostrarTitulo7Obligatorio) {
		this.mostrarTitulo7Obligatorio = mostrarTitulo7Obligatorio;
	}

	/**
	 * @return the proyectoActualTitulo7
	 */
	public UIComponent getProyectoActualTitulo7() {
		return proyectoActualTitulo7;
	}

	/**
	 * @param proyectoActualTitulo7 the proyectoActualTitulo7 to set
	 */
	public void setProyectoActualTitulo7(UIComponent proyectoActualTitulo7) {
		this.proyectoActualTitulo7 = proyectoActualTitulo7;
	}

	/**
	 * @return the esProyectoLaboratoriosMod2
	 */
	public boolean isEsProyectoLaboratoriosMod2() {
		return esProyectoLaboratoriosMod2;
	}

	/**
	 * @param esProyectoLaboratoriosMod2 the esProyectoLaboratoriosMod2 to set
	 */
	public void setEsProyectoLaboratoriosMod2(boolean esProyectoLaboratoriosMod2) {
		this.esProyectoLaboratoriosMod2 = esProyectoLaboratoriosMod2;
	}

	/**
	 * @param esProyectoLaboratorios the esProyectoLaboratorios to set
	 */
	public void setEsProyectoLaboratorios(boolean esProyectoLaboratorios) {
		this.esProyectoLaboratorios = esProyectoLaboratorios;
	}

	/**
	 * @return the esCoordinadorLaboratorio
	 */
	public boolean isEsCoordinadorLaboratorio() {
		return esCoordinadorLaboratorio;
	}

	/**
	 * @param esCoordinadorLaboratorio the esCoordinadorLaboratorio to set
	 */
	public void setEsCoordinadorLaboratorio(boolean esCoordinadorLaboratorio) {
		this.esCoordinadorLaboratorio = esCoordinadorLaboratorio;
	}

	/**
	 * @return the laboratoriosCoordinador
	 */
	public List<Laboratorio> getLaboratoriosCoordinador() {
		return laboratoriosCoordinador;
	}

	/**
	 * @param laboratoriosCoordinador the laboratoriosCoordinador to set
	 */
	public void setLaboratoriosCoordinador(List<Laboratorio> laboratoriosCoordinador) {
		this.laboratoriosCoordinador = laboratoriosCoordinador;
	}

	/**
	 * @return the idLaboratorio
	 */
	public Long getIdLaboratorio() {
		return idLaboratorio;
	}

	/**
	 * @param idLaboratorio the idLaboratorio to set
	 */
	public void setIdLaboratorio(Long idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	/**
	 * @return the listaLaboratoriosProyecto
	 */
	public List<LaboratorioDetalleProyectos> getListaLaboratoriosProyecto() {
		return listaLaboratoriosProyecto;
	}

	/**
	 * @param listaLaboratoriosProyecto the listaLaboratoriosProyecto to set
	 */
	public void setListaLaboratoriosProyecto(List<LaboratorioDetalleProyectos> listaLaboratoriosProyecto) {
		this.listaLaboratoriosProyecto = listaLaboratoriosProyecto;
	}

	/**
	 * @return the laboratorioSeleccionado
	 */
	public LaboratorioDetalleProyectos getLaboratorioSeleccionado() {
		return laboratorioSeleccionado;
	}

	/**
	 * @param laboratorioSeleccionado the laboratorioSeleccionado to set
	 */
	public void setLaboratorioSeleccionado(LaboratorioDetalleProyectos laboratorioSeleccionado) {
		this.laboratorioSeleccionado = laboratorioSeleccionado;
	}

	/**
	 * @return the listaLaboratoriosProyectosBorrados
	 */
	public List<LaboratorioDetalleProyectos> getListaLaboratoriosProyectosBorrados() {
		return listaLaboratoriosProyectosBorrados;
	}

	/**
	 * @param listaLaboratoriosProyectosBorrados the
	 *                                           listaLaboratoriosProyectosBorrados
	 *                                           to set
	 */
	public void setListaLaboratoriosProyectosBorrados(
			List<LaboratorioDetalleProyectos> listaLaboratoriosProyectosBorrados) {
		this.listaLaboratoriosProyectosBorrados = listaLaboratoriosProyectosBorrados;
	}

	/**
	 * @return the listaEnsayosAcredicion
	 */
	public List<LaboratorioEnsayoAcreditacion> getListaEnsayosAcredicion() {
		return listaEnsayosAcredicion;
	}

	/**
	 * @param listaEnsayosAcredicion the listaEnsayosAcredicion to set
	 */
	public void setListaEnsayosAcredicion(List<LaboratorioEnsayoAcreditacion> listaEnsayosAcredicion) {
		this.listaEnsayosAcredicion = listaEnsayosAcredicion;
	}

	/**
	 * @return the ensayoAcreditacionSeleccionado
	 */
	public LaboratorioEnsayoAcreditacion getEnsayoAcreditacionSeleccionado() {
		return ensayoAcreditacionSeleccionado;
	}

	/**
	 * @param ensayoAcreditacionSeleccionado the ensayoAcreditacionSeleccionado to
	 *                                       set
	 */
	public void setEnsayoAcreditacionSeleccionado(LaboratorioEnsayoAcreditacion ensayoAcreditacionSeleccionado) {
		this.ensayoAcreditacionSeleccionado = ensayoAcreditacionSeleccionado;
	}

	/**
	 * @return the listaEnsayosAcredicionBorrados
	 */
	public List<LaboratorioEnsayoAcreditacion> getListaEnsayosAcredicionBorrados() {
		return listaEnsayosAcredicionBorrados;
	}

	/**
	 * @param listaEnsayosAcredicionBorrados the listaEnsayosAcredicionBorrados to
	 *                                       set
	 */
	public void setListaEnsayosAcredicionBorrados(List<LaboratorioEnsayoAcreditacion> listaEnsayosAcredicionBorrados) {
		this.listaEnsayosAcredicionBorrados = listaEnsayosAcredicionBorrados;
	}

	/**
	 * @return the nombreEnsayo
	 */
	public String getNombreEnsayo() {
		return nombreEnsayo;
	}

	/**
	 * @param nombreEnsayo the nombreEnsayo to set
	 */
	public void setNombreEnsayo(String nombreEnsayo) {
		this.nombreEnsayo = nombreEnsayo;
	}

	/**
	 * @return the areaAcreditacion
	 */
	public String getAreaAcreditacion() {
		return areaAcreditacion;
	}

	/**
	 * @param areaAcreditacion the areaAcreditacion to set
	 */
	public void setAreaAcreditacion(String areaAcreditacion) {
		this.areaAcreditacion = areaAcreditacion;
	}

	/**
	 * @return the documentoReferencia
	 */
	public String getDocumentoReferencia() {
		return documentoReferencia;
	}

	/**
	 * @param documentoReferencia the documentoReferencia to set
	 */
	public void setDocumentoReferencia(String documentoReferencia) {
		this.documentoReferencia = documentoReferencia;
	}

	/**
	 * @return the laboratorioVistaSeleccionado
	 */
	public LaboratorioVista getLaboratorioVistaSeleccionado() {
		return laboratorioVistaSeleccionado;
	}

	/**
	 * @param laboratorioVistaSeleccionado the laboratorioVistaSeleccionado to set
	 */
	public void setLaboratorioVistaSeleccionado(LaboratorioVista laboratorioVistaSeleccionado) {
		this.laboratorioVistaSeleccionado = laboratorioVistaSeleccionado;
	}

	/**
	 * @return the esEventosModUno_2017
	 */
	public boolean isEsEventosModUno_2017() {
		return esEventosModUno_2017;
	}

	/**
	 * @param esEventosModUno_2017 the esEventosModUno_2017 to set
	 */
	public void setEsEventosModUno_2017(boolean esEventosModUno_2017) {
		this.esEventosModUno_2017 = esEventosModUno_2017;
	}

	/**
	 * @return the esEventosModDos_2017
	 */
	public boolean isEsEventosModDos_2017() {
		return esEventosModDos_2017;
	}

	/**
	 * @param esEventosModDos_2017 the esEventosModDos_2017 to set
	 */
	public void setEsEventosModDos_2017(boolean esEventosModDos_2017) {
		this.esEventosModDos_2017 = esEventosModDos_2017;
	}

	/**
	 * @return the esConvProyectos2017_2018
	 */
	public boolean isEsConvProyectos2017_2018() {
		return esConvProyectos2017_2018;
	}

	/**
	 * @param esConvProyectos2017_2018 the esConvProyectos2017_2018 to set
	 */
	public void setEsConvProyectos2017_2018(boolean esConvProyectos2017_2018) {
		this.esConvProyectos2017_2018 = esConvProyectos2017_2018;
	}

	/**
	 * @return the esConvocatoriaColecciones
	 */
	public boolean isEsConvocatoriaColecciones() {
		return esConvocatoriaColecciones;
	}

	/**
	 * @param esConvocatoriaColecciones the esConvocatoriaColecciones to set
	 */
	public void setEsConvocatoriaColecciones(boolean esConvocatoriaColecciones) {
		this.esConvocatoriaColecciones = esConvocatoriaColecciones;
	}

	/**
	 * @return the listaTrayectoriaColeccion
	 */
	public List<TrayectoriaColeccionProyecto> getListaTrayectoriaColeccion() {
		return listaTrayectoriaColeccion;
	}

	/**
	 * @param listaTrayectoriaColeccion the listaTrayectoriaColeccion to set
	 */
	public void setListaTrayectoriaColeccion(List<TrayectoriaColeccionProyecto> listaTrayectoriaColeccion) {
		this.listaTrayectoriaColeccion = listaTrayectoriaColeccion;
	}

	/**
	 * @return the listaTrayectoriaColeccionBorrados
	 */
	public List<TrayectoriaColeccionProyecto> getListaTrayectoriaColeccionBorrados() {
		return listaTrayectoriaColeccionBorrados;
	}

	/**
	 * @param listaTrayectoriaColeccionBorrados the
	 *                                          listaTrayectoriaColeccionBorrados to
	 *                                          set
	 */
	public void setListaTrayectoriaColeccionBorrados(
			List<TrayectoriaColeccionProyecto> listaTrayectoriaColeccionBorrados) {
		this.listaTrayectoriaColeccionBorrados = listaTrayectoriaColeccionBorrados;
	}

	/**
	 * @return the trayectoriaColeccionSeleccionada
	 */
	public TrayectoriaColeccionProyecto getTrayectoriaColeccionSeleccionada() {
		return trayectoriaColeccionSeleccionada;
	}

	/**
	 * @param trayectoriaColeccionSeleccionada the trayectoriaColeccionSeleccionada
	 *                                         to set
	 */
	public void setTrayectoriaColeccionSeleccionada(TrayectoriaColeccionProyecto trayectoriaColeccionSeleccionada) {
		this.trayectoriaColeccionSeleccionada = trayectoriaColeccionSeleccionada;
	}

	/**
	 * @return the listaSeleccionTrayectoriaColeccion
	 */
	public SelectItem[] getListaSeleccionTrayectoriaColeccion() {
		return listaSeleccionTrayectoriaColeccion;
	}

	/**
	 * @param listaSeleccionTrayectoriaColeccion the
	 *                                           listaSeleccionTrayectoriaColeccion
	 *                                           to set
	 */
	public void setListaSeleccionTrayectoriaColeccion(SelectItem[] listaSeleccionTrayectoriaColeccion) {
		this.listaSeleccionTrayectoriaColeccion = listaSeleccionTrayectoriaColeccion;
	}

	/**
	 * @return the descripcionTrayectoriaColeccion
	 */
	public String getDescripcionTrayectoriaColeccion() {
		return descripcionTrayectoriaColeccion;
	}

	/**
	 * @param descripcionTrayectoriaColeccion the descripcionTrayectoriaColeccion to
	 *                                        set
	 */
	public void setDescripcionTrayectoriaColeccion(String descripcionTrayectoriaColeccion) {
		this.descripcionTrayectoriaColeccion = descripcionTrayectoriaColeccion;
	}

	/**
	 * @param idTrayectoriaColeccion the idTrayectoriaColeccion to set
	 */
	public void setIdTrayectoriaColeccion(String idTrayectoriaColeccion) {
		this.idTrayectoriaColeccion = idTrayectoriaColeccion;
	}

	/**
	 * @return the idTrayectoriaColeccion
	 */
	public String getIdTrayectoriaColeccion() {
		return idTrayectoriaColeccion;
	}

	public boolean isMostrarListaSel1Obligatorio() {
		return mostrarListaSel1Obligatorio;
	}

	public void setMostrarListaSel1Obligatorio(boolean mostrarListaSel1Obligatorio) {
		this.mostrarListaSel1Obligatorio = mostrarListaSel1Obligatorio;
	}

	public boolean isMostrarListaSel2Obligatorio() {
		return mostrarListaSel2Obligatorio;
	}

	public void setMostrarListaSel2Obligatorio(boolean mostrarListaSel2Obligatorio) {
		this.mostrarListaSel2Obligatorio = mostrarListaSel2Obligatorio;
	}

	public UIComponent getIdListaSelDos() {
		return idListaSelDos;
	}

	public void setIdListaSelDos(UIComponent idListaSelDos) {
		this.idListaSelDos = idListaSelDos;
	}

	public UIComponent getIdListaSelUno() {
		return idListaSelUno;
	}

	public void setIdListaSelUno(UIComponent idListaSelUno) {
		this.idListaSelUno = idListaSelUno;
	}

	public boolean isEsConvocatoriaSue2017() {
		return esConvocatoriaSue2017;
	}

	public void setEsConvocatoriaSue2017(boolean esConvocatoriaSue2017) {
		this.esConvocatoriaSue2017 = esConvocatoriaSue2017;
	}

	public List<ValoresListasProyecto> getListaGruposSUE() {
		return listaGruposSUE;
	}

	public void setListaGruposSUE(List<ValoresListasProyecto> listaGruposSUE) {
		this.listaGruposSUE = listaGruposSUE;
	}

	public List<ValoresListasProyecto> getListaGruposSUEBorrados() {
		return listaGruposSUEBorrados;
	}

	public void setListaGruposSUEBorrados(List<ValoresListasProyecto> listaGruposSUEBorrados) {
		this.listaGruposSUEBorrados = listaGruposSUEBorrados;
	}

	public ValoresListasProyecto getGrupoSUESeleccionado() {
		return grupoSUESeleccionado;
	}

	public void setGrupoSUESeleccionado(ValoresListasProyecto grupoSUESeleccionado) {
		this.grupoSUESeleccionado = grupoSUESeleccionado;
	}

	public List<ValoresListasProyecto> getValoresListasProyectos() {
		return valoresListasProyectos;
	}

	public void setValoresListasProyectos(List<ValoresListasProyecto> valoresListasProyectos) {
		this.valoresListasProyectos = valoresListasProyectos;
	}

	public List<ValoresListasProyecto> getValoresListasProyectosBorrados() {
		return valoresListasProyectosBorrados;
	}

	public void setValoresListasProyectosBorrados(List<ValoresListasProyecto> valoresListasProyectosBorrados) {
		this.valoresListasProyectosBorrados = valoresListasProyectosBorrados;
	}

	public boolean isMostrarListaSeleccionMultiple() {
		return mostrarListaSeleccionMultiple;
	}

	public void setMostrarListaSeleccionMultiple(boolean mostrarListaSeleccionMultiple) {
		this.mostrarListaSeleccionMultiple = mostrarListaSeleccionMultiple;
	}

	public String getTextoListaSeleccionMultiple() {
		return textoListaSeleccionMultiple;
	}

	public void setTextoListaSeleccionMultiple(String textoListaSeleccionMultiple) {
		this.textoListaSeleccionMultiple = textoListaSeleccionMultiple;
	}

	public String getDominioValoresListaProyectos() {
		return dominioValoresListaProyectos;
	}

	public void setDominioValoresListaProyectos(String dominioValoresListaProyectos) {
		this.dominioValoresListaProyectos = dominioValoresListaProyectos;
	}

	public SelectItem[] getListaSeleccionValoresProyecto() {
		return listaSeleccionValoresProyecto;
	}

	public void setListaSeleccionValoresProyecto(SelectItem[] listaSeleccionValoresProyecto) {
		this.listaSeleccionValoresProyecto = listaSeleccionValoresProyecto;
	}

	public String getValorListaProyecto() {
		return valorListaProyecto;
	}

	public void setValorListaProyecto(String valorListaProyecto) {
		this.valorListaProyecto = valorListaProyecto;
	}

	public ValoresListasProyecto getValorListaProyectoSeleccionada() {
		return valorListaProyectoSeleccionada;
	}

	public void setValorListaProyectoSeleccionada(ValoresListasProyecto valorListaProyectoSeleccionada) {
		this.valorListaProyectoSeleccionada = valorListaProyectoSeleccionada;
	}

	public boolean isEsConvRepotenciacionLab2018() {
		return esConvRepotenciacionLab2018;
	}

	public void setEsConvRepotenciacionLab2018(boolean esConvRepotenciacionLab2018) {
		this.esConvRepotenciacionLab2018 = esConvRepotenciacionLab2018;
	}

	public String getTituloGruposInvestigacion() {
		return tituloGruposInvestigacion;
	}

	public void setTituloGruposInvestigacion(String tituloGruposInvestigacion) {
		this.tituloGruposInvestigacion = tituloGruposInvestigacion;
	}

	public boolean isEsConvocatoriaAlianzas2018() {
		return esConvocatoriaAlianzas2018;
	}

	public void setEsConvocatoriaAlianzas2018(boolean esConvocatoriaAlianzas2018) {
		this.esConvocatoriaAlianzas2018 = esConvocatoriaAlianzas2018;
	}

	public boolean isValidarBotonGuardar() {
		return validarBotonGuardarConvocatoria(proyectoActual.getId());
	}

	public Long getNumLabsVal() {
		return numLabsVal;
	}

	public void setNumLabsVal(Long numLabsVal) {
		this.numLabsVal = numLabsVal;
	}

	public void setMostrarLabConv(boolean mostrarLabConv) {
		this.mostrarLabConv = mostrarLabConv;
	}

	public UIComponent getProyectoActualTitulo8() {
		return proyectoActualTitulo8;
	}

	public void setProyectoActualTitulo8(UIComponent proyectoActualTitulo8) {
		this.proyectoActualTitulo8 = proyectoActualTitulo8;
	}

	public UIComponent getProyectoActualTitulo9() {
		return proyectoActualTitulo9;
	}

	public void setProyectoActualTitulo9(UIComponent proyectoActualTitulo9) {
		this.proyectoActualTitulo9 = proyectoActualTitulo9;
	}

	public UIComponent getProyectoActualTitulo10() {
		return proyectoActualTitulo10;
	}

	public void setProyectoActualTitulo10(UIComponent proyectoActualTitulo10) {
		this.proyectoActualTitulo10 = proyectoActualTitulo10;
	}

	public UIComponent getProyectoActualTitulo11() {
		return proyectoActualTitulo11;
	}

	public void setProyectoActualTitulo11(UIComponent proyectoActualTitulo11) {
		this.proyectoActualTitulo11 = proyectoActualTitulo11;
	}

	public String getTitulo8() {
		return titulo8;
	}

	public void setTitulo8(String titulo8) {
		this.titulo8 = titulo8;
	}

	public String getTitulo9() {
		return titulo9;
	}

	public void setTitulo9(String titulo9) {
		this.titulo9 = titulo9;
	}

	public String getTitulo10() {
		return titulo10;
	}

	public void setTitulo10(String titulo10) {
		this.titulo10 = titulo10;
	}

	public String getTitulo11() {
		return titulo11;
	}

	public void setTitulo11(String titulo11) {
		this.titulo11 = titulo11;
	}

	public boolean isMostrarTitulo8() {
		return mostrarTitulo8;
	}

	public void setMostrarTitulo8(boolean mostrarTitulo8) {
		this.mostrarTitulo8 = mostrarTitulo8;
	}

	public boolean isMostrarTitulo9() {
		return mostrarTitulo9;
	}

	public void setMostrarTitulo9(boolean mostrarTitulo9) {
		this.mostrarTitulo9 = mostrarTitulo9;
	}

	public boolean isMostrarTitulo10() {
		return mostrarTitulo10;
	}

	public void setMostrarTitulo10(boolean mostrarTitulo10) {
		this.mostrarTitulo10 = mostrarTitulo10;
	}

	public boolean isMostrarTitulo11() {
		return mostrarTitulo11;
	}

	public void setMostrarTitulo11(boolean mostrarTitulo11) {
		this.mostrarTitulo11 = mostrarTitulo11;
	}

	public boolean isMostrarTitulo8Obligatorio() {
		return mostrarTitulo8Obligatorio;
	}

	public void setMostrarTitulo8Obligatorio(boolean mostrarTitulo8Obligatorio) {
		this.mostrarTitulo8Obligatorio = mostrarTitulo8Obligatorio;
	}

	public boolean isMostrarTitulo9Obligatorio() {
		return mostrarTitulo9Obligatorio;
	}

	public void setMostrarTitulo9Obligatorio(boolean mostrarTitulo9Obligatorio) {
		this.mostrarTitulo9Obligatorio = mostrarTitulo9Obligatorio;
	}

	public boolean isMostrarTitulo10Obligatorio() {
		return mostrarTitulo10Obligatorio;
	}

	public void setMostrarTitulo10Obligatorio(boolean mostrarTitulo10Obligatorio) {
		this.mostrarTitulo10Obligatorio = mostrarTitulo10Obligatorio;
	}

	public boolean isMostrarTitulo11Obligatorio() {
		return mostrarTitulo11Obligatorio;
	}

	public void setMostrarTitulo11Obligatorio(boolean mostrarTitulo11Obligatorio) {
		this.mostrarTitulo11Obligatorio = mostrarTitulo11Obligatorio;
	}

	public boolean isEsEventosModUno_2019() {
		return esEventosModUno_2019;
	}

	public void setEsEventosModUno_2019(boolean esEventosModUno_2019) {
		this.esEventosModUno_2019 = esEventosModUno_2019;
	}

	public boolean isEsEventosModDos_2019() {
		return esEventosModDos_2019;
	}

	public void setEsEventosModDos_2019(boolean esEventosModDos_2019) {
		this.esEventosModDos_2019 = esEventosModDos_2019;
	}

	public boolean isEsConvocatoriaAlianzas2019() {
		return esConvocatoriaAlianzas2019;
	}

	public void setEsConvocatoriaAlianzas2019(boolean esConvocatoriaAlianzas2019) {
		this.esConvocatoriaAlianzas2019 = esConvocatoriaAlianzas2019;
	}

	public boolean isEsConvSedesPreNal2019() {
		return esConvSedesPreNal2019;
	}

	public void setEsConvSedesPreNal2019(boolean esConvSedesPreNal2019) {
		this.esConvSedesPreNal2019 = esConvSedesPreNal2019;
	}

	public Long getID_CONVOC_POSG() {
		return ID_CONVOC_POSG;
	}

	public void setID_CONVOC_POSG(Long iD_CONVOC_POSG) {
		ID_CONVOC_POSG = iD_CONVOC_POSG;
	}

	public boolean isMostrarSiConvocatoriaEventos() {
		return mostrarSiConvocatoriaEventos;
	}

	public void setMostrarSiConvocatoriaEventos(boolean mostrarSiConvocatoriaEventos) {
		this.mostrarSiConvocatoriaEventos = mostrarSiConvocatoriaEventos;
	}

	public boolean isEsPosgrado1_2() {
		return esPosgrado1_2;
	}

	public void setEsPosgrado1_2(boolean esPosgrado1_2) {
		this.esPosgrado1_2 = esPosgrado1_2;
	}

	public boolean isVerFichaMin() {
		return verFichaMin;
	}

	public void setVerFichaMin(boolean verFichaMin) {
		this.verFichaMin = verFichaMin;
	}

	public boolean isEsEventosHumanasModUno() {
		return esEventosHumanasModUno;
	}

	public void setEsEventosHumanasModUno(boolean esEventosHumanasModUno) {
		this.esEventosHumanasModUno = esEventosHumanasModUno;
	}

	public boolean isEsEventosHumanasModDos() {
		return esEventosHumanasModDos;
	}

	public void setEsEventosHumanasModDos(boolean esEventosHumanasModDos) {
		this.esEventosHumanasModDos = esEventosHumanasModDos;
	}

	public boolean isEsEventosFIAModUno() {
		return esEventosFIAModUno;
	}

	public void setEsEventosFIAModUno(boolean esEventosFIAModUno) {
		this.esEventosFIAModUno = esEventosFIAModUno;
	}

	public boolean isEsEventosFIAModDos() {
		return esEventosFIAModDos;
	}

	public void setEsEventosFIAModDos(boolean esEventosFIAModDos) {
		this.esEventosFIAModDos = esEventosFIAModDos;
	}

	public boolean isEsEventosMinasModUno() {
		return esEventosMinasModUno;
	}

	public void setEsEventosMinasModUno(boolean esEventosMinasModUno) {
		this.esEventosMinasModUno = esEventosMinasModUno;
	}

	public boolean isEsEventosMinasModDos() {
		return esEventosMinasModDos;
	}

	public void setEsEventosMinasModDos(boolean esEventosMinasModDos) {
		this.esEventosMinasModDos = esEventosMinasModDos;
	}

	public boolean isEsConvSemillero() {
		return esConvSemillero;
	}

	public void setEsConvSemillero(boolean esConvSemillero) {
		this.esConvSemillero = esConvSemillero;
	}

	public boolean isEsLaboratoriosNacional() {
		return esLaboratoriosNacional;
	}

	public void setEsLaboratoriosNacional(boolean esLaboratoriosNacional) {
		this.esLaboratoriosNacional = esLaboratoriosNacional;
	}

	public boolean isEsLaboratoriosSede() {
		return esLaboratoriosSede;
	}

	public void setEsLaboratoriosSede(boolean esLaboratoriosSede) {
		this.esLaboratoriosSede = esLaboratoriosSede;
	}

	public boolean isEsLaboratoriosFacultad() {
		return esLaboratoriosFacultad;
	}

	public void setEsLaboratoriosFacultad(boolean esLaboratoriosFacultad) {
		this.esLaboratoriosFacultad = esLaboratoriosFacultad;
	}

	public boolean isEsLaboratoriosDepto() {
		return esLaboratoriosDepto;
	}

	public void setEsLaboratoriosDepto(boolean esLaboratoriosDepto) {
		this.esLaboratoriosDepto = esLaboratoriosDepto;
	}

	public String getDOMINIO_AREA_TEMATICA_CONV_BIO_MAN() {
		return DOMINIO_AREA_TEMATICA_CONV_BIO_MAN;
	}

	public void setDOMINIO_AREA_TEMATICA_CONV_BIO_MAN(String dOMINIO_AREA_TEMATICA_CONV_BIO_MAN) {
		DOMINIO_AREA_TEMATICA_CONV_BIO_MAN = dOMINIO_AREA_TEMATICA_CONV_BIO_MAN;
	}

	public String getDOMINIO_AREA_TEMATICA_CONV_HUM_MAN() {
		return DOMINIO_AREA_TEMATICA_CONV_HUM_MAN;
	}

	public void setDOMINIO_AREA_TEMATICA_CONV_HUM_MAN(String dOMINIO_AREA_TEMATICA_CONV_HUM_MAN) {
		DOMINIO_AREA_TEMATICA_CONV_HUM_MAN = dOMINIO_AREA_TEMATICA_CONV_HUM_MAN;
	}

	public String getDOMINIO_AREA_TEMATICA_CONV_IEU() {
		return DOMINIO_AREA_TEMATICA_CONV_IEU;
	}

	public void setDOMINIO_AREA_TEMATICA_CONV_IEU(String dOMINIO_AREA_TEMATICA_CONV_IEU) {
		DOMINIO_AREA_TEMATICA_CONV_IEU = dOMINIO_AREA_TEMATICA_CONV_IEU;
	}

	public String getDOMINIO_TRAYECTORIA_COLECCION() {
		return DOMINIO_TRAYECTORIA_COLECCION;
	}

	public void setDOMINIO_TRAYECTORIA_COLECCION(String dOMINIO_TRAYECTORIA_COLECCION) {
		DOMINIO_TRAYECTORIA_COLECCION = dOMINIO_TRAYECTORIA_COLECCION;
	}

	public ManejadorUtilidadesLaboratorios getmUL() {
		return mUL;
	}

	public void setmUL(ManejadorUtilidadesLaboratorios mUL) {
		this.mUL = mUL;
	}

	public boolean isEsConvProyectosBogota2019() {
		return esConvProyectosBogota2019;
	}

	public boolean isEsConvMoots2019() {
		return esConvMoots2019;
	}

	public void setEsConvMoots2019(boolean esConvMoots2019) {
		this.esConvMoots2019 = esConvMoots2019;
	}

	public void setEsConvProyectosBogota2019(boolean esConvProyectosBogota2019) {
		this.esConvProyectosBogota2019 = esConvProyectosBogota2019;
	}

	public String getAREA_TEMATICA_CONV_CIENCIAS_BASICAS() {
		return AREA_TEMATICA_CONV_CIENCIAS_BASICAS;
	}

	public String getAREA_TEMATICA_CONV_CIENCIAS_SOCIALES() {
		return AREA_TEMATICA_CONV_CIENCIAS_SOCIALES;
	}

	public String getAREA_TEMATICA_CONV_INVESTIGACION_APLICADA() {
		return AREA_TEMATICA_CONV_INVESTIGACION_APLICADA;
	}

	public boolean isMostrarLabConv() {
		return mostrarLabConv;
	}

	public boolean isEsConvCP2019() {
		return esConvCP2019;
	}

	public void setEsConvCP2019(boolean esConvCP2019) {
		this.esConvCP2019 = esConvCP2019;
	}

	public void cargarCentrosPensamiento() {
		List listaCentrosPensamientoDom = new ArrayList<DominioDetalle>();
		listaCentrosPensamientoDom = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='CENTROS_PENSAMIENTO_CONV_CP_2019' order by to_number(dd.identificador.tipo)");
		setListaCentrosPensamiento(new SelectItem[listaCentrosPensamientoDom.size()]);
		for (int i = 0; i < listaCentrosPensamientoDom.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaCentrosPensamientoDom.get(i);
			getListaCentrosPensamiento()[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}
	}

	public SelectItem[] getListaCentrosPensamiento() {
		return listaCentrosPensamiento;
	}

	public void setListaCentrosPensamiento(SelectItem[] listaCentrosPensamiento) {
		this.listaCentrosPensamiento = listaCentrosPensamiento;
	}

	public String getTitulo12() {
		return titulo12;
	}

	public void setTitulo12(String titulo12) {
		this.titulo12 = titulo12;
	}

	public boolean isMostrarTitulo12() {
		return mostrarTitulo12;
	}

	public void setMostrarTitulo12(boolean mostrarTitulo12) {
		this.mostrarTitulo12 = mostrarTitulo12;
	}

	public boolean isMostrarTitulo12Obligatorio() {
		return mostrarTitulo12Obligatorio;
	}

	public void setMostrarTitulo12Obligatorio(boolean mostrarTitulo12Obligatorio) {
		this.mostrarTitulo12Obligatorio = mostrarTitulo12Obligatorio;
	}

	public boolean isEsConvCentroPensamiento2019() {
		return esConvCentroPensamiento2019;
	}

	public void setEsConvCentroPensamiento2019(boolean esConvCentroPensamiento2019) {
		this.esConvCentroPensamiento2019 = esConvCentroPensamiento2019;
	}

	public UIComponent getProyectoActualTitulo12() {
		return proyectoActualTitulo12;
	}

	public void setProyectoActualTitulo12(UIComponent proyectoActualTitulo12) {
		this.proyectoActualTitulo12 = proyectoActualTitulo12;
	}

	public UIComponent getProyectoActualTitulo1_CP() {
		return proyectoActualTitulo1_CP;
	}

	public void setProyectoActualTitulo1_CP(UIComponent proyectoActualTitulo1_CP) {
		this.proyectoActualTitulo1_CP = proyectoActualTitulo1_CP;
	}

	public UIComponent getProyectoActualJustificacion_CP() {
		return proyectoActualJustificacion_CP;
	}

	public void setProyectoActualJustificacion_CP(UIComponent proyectoActualJustificacion_CP) {
		this.proyectoActualJustificacion_CP = proyectoActualJustificacion_CP;
	}

	public UIComponent getProyectoActualMetodologia_CP() {
		return proyectoActualMetodologia_CP;
	}

	public void setProyectoActualMetodologia_CP(UIComponent proyectoActualMetodologia_CP) {
		this.proyectoActualMetodologia_CP = proyectoActualMetodologia_CP;
	}

	public UIComponent getProyectoActualTitulo4_CP() {
		return proyectoActualTitulo4_CP;
	}

	public void setProyectoActualTitulo4_CP(UIComponent proyectoActualTitulo4_CP) {
		this.proyectoActualTitulo4_CP = proyectoActualTitulo4_CP;
	}

	public UIComponent getProyectoActualTitulo5_CP() {
		return proyectoActualTitulo5_CP;
	}

	public void setProyectoActualTitulo5_CP(UIComponent proyectoActualTitulo5_CP) {
		this.proyectoActualTitulo5_CP = proyectoActualTitulo5_CP;
	}

	public UIComponent getProyectoActualTitulo6_CP() {
		return proyectoActualTitulo6_CP;
	}

	public void setProyectoActualTitulo6_CP(UIComponent proyectoActualTitulo6_CP) {
		this.proyectoActualTitulo6_CP = proyectoActualTitulo6_CP;
	}

	public UIComponent getProyectoActualTitulo7_CP() {
		return proyectoActualTitulo7_CP;
	}

	public void setProyectoActualTitulo7_CP(UIComponent proyectoActualTitulo7_CP) {
		this.proyectoActualTitulo7_CP = proyectoActualTitulo7_CP;
	}

	public UIComponent getProyectoActualTitulo8_CP() {
		return proyectoActualTitulo8_CP;
	}

	public void setProyectoActualTitulo8_CP(UIComponent proyectoActualTitulo8_CP) {
		this.proyectoActualTitulo8_CP = proyectoActualTitulo8_CP;
	}

	public UIComponent getProyectoActualTitulo9_CP() {
		return proyectoActualTitulo9_CP;
	}

	public void setProyectoActualTitulo9_CP(UIComponent proyectoActualTitulo9_CP) {
		this.proyectoActualTitulo9_CP = proyectoActualTitulo9_CP;
	}

	public UIComponent getProyectoActualTitulo10_CP() {
		return proyectoActualTitulo10_CP;
	}

	public void setProyectoActualTitulo10_CP(UIComponent proyectoActualTitulo10_CP) {
		this.proyectoActualTitulo10_CP = proyectoActualTitulo10_CP;
	}

	public UIComponent getProyectoActualTitulo11_CP() {
		return proyectoActualTitulo11_CP;
	}

	public void setProyectoActualTitulo11_CP(UIComponent proyectoActualTitulo11_CP) {
		this.proyectoActualTitulo11_CP = proyectoActualTitulo11_CP;
	}

	public UIComponent getProyectoActualTitulo12_CP() {
		return proyectoActualTitulo12_CP;
	}

	public void setProyectoActualTitulo12_CP(UIComponent proyectoActualTitulo12_CP) {
		this.proyectoActualTitulo12_CP = proyectoActualTitulo12_CP;
	}

	public boolean isEsConvUnInnova2019() {
		return esConvUnInnova2019;
	}

	public void setEsConvUnInnova2019(boolean esConvUnInnova2019) {
		this.esConvUnInnova2019 = esConvUnInnova2019;
	}

	public boolean isMostrarObjResulRespObligatorio() {
		return mostrarObjResulRespObligatorio;
	}

	public void setMostrarObjResulRespObligatorio(boolean mostrarObjResulRespObligatorio) {
		this.mostrarObjResulRespObligatorio = mostrarObjResulRespObligatorio;
		;
	}

	public boolean isMostrarlistaSeleccionMultiplObligatorio() {
		return mostrarlistaSeleccionMultiplObligatorio;
	}

	public void setMostrarlistaSeleccionMultiplObligatorio(boolean mostrarlistaSeleccionMultiplObligatorio) {
		this.mostrarlistaSeleccionMultiplObligatorio = mostrarlistaSeleccionMultiplObligatorio;
	}

	public int getNumRegLab() {
		return numRegLab;
	}

	public void setNumRegLab(int numRegLab) {
		this.numRegLab = numRegLab;
	}

	public String[] getSelectedAreaGestionConocimiento() {
		return selectedAreaGestionConocimiento;
	}

	public void setSelectedAreaGestionConocimiento(String[] selectedAreaGestionConocimiento) {
		this.selectedAreaGestionConocimiento = selectedAreaGestionConocimiento;
	}

	public SelectItem[] getAreaGestionConocimientoItem() {
		return areaGestionConocimientoItem;
	}

	public void setAreaGestionConocimientoItem(SelectItem[] areaGestionConocimientoItem) {
		this.areaGestionConocimientoItem = areaGestionConocimientoItem;
	}

	public UIComponent getProyectoActualTitulo8Eventos() {
		return proyectoActualTitulo8Eventos;
	}

	public void setProyectoActualTitulo8Eventos(UIComponent proyectoActualTitulo8Eventos) {
		this.proyectoActualTitulo8Eventos = proyectoActualTitulo8Eventos;
	}

	public String getResponsableEquipo() {
		return responsableEquipo;
	}

	public void setResponsableEquipo(String responsableEquipo) {
		this.responsableEquipo = responsableEquipo;
	}

	public String getEquipoNuevo() {
		return equipoNuevo;
	}

	public void setEquipoNuevo(String equipoNuevo) {
		this.equipoNuevo = equipoNuevo;
	}

	public String getUbicacionEquipo() {
		return ubicacionEquipo;
	}

	public void setUbicacionEquipo(String ubicacionEquipo) {
		this.ubicacionEquipo = ubicacionEquipo;
	}

	public String getServicioEquipo() {
		return servicioEquipo;
	}

	public void setServicioEquipo(String servicioEquipo) {
		this.servicioEquipo = servicioEquipo;
	}

	public ProyectoEquipoAdquisicion getEquipoSeleccionado() {
		return equipoSeleccionado;
	}

	public void setEquipoSeleccionado(ProyectoEquipoAdquisicion equipoSeleccionado) {
		this.equipoSeleccionado = equipoSeleccionado;
	}
	
	public void adicionarEquipoNuevo() {

		if (esCadenaVacia(equipoNuevo)) {
			mensajeError("Indique el nombre del equipo");
			return;
		}else if (esCadenaVacia(ubicacionEquipo)) {
			mensajeError("Indique la ubicación del equipo");
			return;
		}else if (esCadenaVacia(responsableEquipo)) {
			mensajeError("Indique el responsable del equipo");
			return;
		}else if(esCadenaVacia(servicioEquipo)) {
			mensajeError("Indique el o los servicios que prestará el equipo");
			return;
		}else if(esCadenaVacia(justificacionEquipo)) {
			mensajeError("Indique la justificación para la adquisición del equipo");
			return;
		}else if(valorEquipo==null) {
			mensajeError("Indique el valor de adquisición del equipo");
			return;
		}

		ProyectoEquipoAdquisicion equipo = new ProyectoEquipoAdquisicion();
		equipo.setNombreEquipo(equipoNuevo);
		equipo.setProyecto(proyectoActual);
		equipo.setResponsable(responsableEquipo);
		equipo.setServicio(servicioEquipo);
		equipo.setUbicacion(ubicacionEquipo);
		equipo.setJustificacion(justificacionEquipo);
		equipo.setValor(valorEquipo);
		proyectoActual.adicionarEquipoAdquisicion(equipo);
		
	}
	
	/**
	 * Eliminar equipo.
	 */
	public void eliminarEquipoNuevo() {
		proyectoActual.borrarEquipoAdquisicion(equipoSeleccionado);
	}

	public String getTitulo13() {
		return titulo13;
	}

	public void setTitulo13(String titulo13) {
		this.titulo13 = titulo13;
	}

	public String getTitulo14() {
		return titulo14;
	}

	public void setTitulo14(String titulo14) {
		this.titulo14 = titulo14;
	}

	public String getTitulo15() {
		return titulo15;
	}

	public void setTitulo15(String titulo15) {
		this.titulo15 = titulo15;
	}

	public String getTitulo16() {
		return titulo16;
	}

	public void setTitulo16(String titulo16) {
		this.titulo16 = titulo16;
	}

	public boolean isMostrarTitulo13() {
		return mostrarTitulo13;
	}

	public void setMostrarTitulo13(boolean mostrarTitulo13) {
		this.mostrarTitulo13 = mostrarTitulo13;
	}

	public boolean isMostrarTitulo14() {
		return mostrarTitulo14;
	}

	public void setMostrarTitulo14(boolean mostrarTitulo14) {
		this.mostrarTitulo14 = mostrarTitulo14;
	}

	public boolean isMostrarTitulo15() {
		return mostrarTitulo15;
	}

	public void setMostrarTitulo15(boolean mostrarTitulo15) {
		this.mostrarTitulo15 = mostrarTitulo15;
	}

	public boolean isMostrarTitulo16() {
		return mostrarTitulo16;
	}

	public void setMostrarTitulo16(boolean mostrarTitulo16) {
		this.mostrarTitulo16 = mostrarTitulo16;
	}

	public boolean isMostrarTitulo13Obligatorio() {
		return mostrarTitulo13Obligatorio;
	}

	public void setMostrarTitulo13Obligatorio(boolean mostrarTitulo13Obligatorio) {
		this.mostrarTitulo13Obligatorio = mostrarTitulo13Obligatorio;
	}

	public boolean isMostrarTitulo14Obligatorio() {
		return mostrarTitulo14Obligatorio;
	}

	public void setMostrarTitulo14Obligatorio(boolean mostrarTitulo14Obligatorio) {
		this.mostrarTitulo14Obligatorio = mostrarTitulo14Obligatorio;
	}

	public boolean isMostrarTitulo15Obligatorio() {
		return mostrarTitulo15Obligatorio;
	}

	public void setMostrarTitulo15Obligatorio(boolean mostrarTitulo15Obligatorio) {
		this.mostrarTitulo15Obligatorio = mostrarTitulo15Obligatorio;
	}

	public boolean isMostrarTitulo16Obligatorio() {
		return mostrarTitulo16Obligatorio;
	}

	public void setMostrarTitulo16Obligatorio(boolean mostrarTitulo16Obligatorio) {
		this.mostrarTitulo16Obligatorio = mostrarTitulo16Obligatorio;
	}

	public UIComponent getProyectoActualTitulo13() {
		return proyectoActualTitulo13;
	}

	public void setProyectoActualTitulo13(UIComponent proyectoActualTitulo13) {
		this.proyectoActualTitulo13 = proyectoActualTitulo13;
	}

	public UIComponent getProyectoActualTitulo14() {
		return proyectoActualTitulo14;
	}

	public void setProyectoActualTitulo14(UIComponent proyectoActualTitulo14) {
		this.proyectoActualTitulo14 = proyectoActualTitulo14;
	}

	public UIComponent getProyectoActualTitulo15() {
		return proyectoActualTitulo15;
	}

	public void setProyectoActualTitulo15(UIComponent proyectoActualTitulo15) {
		this.proyectoActualTitulo15 = proyectoActualTitulo15;
	}

	public UIComponent getProyectoActualTitulo16() {
		return proyectoActualTitulo16;
	}

	public void setProyectoActualTitulo16(UIComponent proyectoActualTitulo16) {
		this.proyectoActualTitulo16 = proyectoActualTitulo16;
	}

	public boolean isEsConvocatoriaRedes() {
		return esConvocatoriaRedes;
	}

	public void setEsConvocatoriaRedes(boolean esConvocatoriaRedes) {
		this.esConvocatoriaRedes = esConvocatoriaRedes;
	}

	public UIComponent getProyectoActualTitulo7a() {
		return proyectoActualTitulo7a;
	}

	public void setProyectoActualTitulo7a(UIComponent proyectoActualTitulo7a) {
		this.proyectoActualTitulo7a = proyectoActualTitulo7a;
	}

	public boolean isMostrarListaSeleccionMultiple2() {
		return mostrarListaSeleccionMultiple2;
	}

	public void setMostrarListaSeleccionMultiple2(boolean mostrarListaSeleccionMultiple2) {
		this.mostrarListaSeleccionMultiple2 = mostrarListaSeleccionMultiple2;
	}

	public String getTextoListaSeleccionMultiple2() {
		return textoListaSeleccionMultiple2;
	}

	public void setTextoListaSeleccionMultiple2(String textoListaSeleccionMultiple2) {
		this.textoListaSeleccionMultiple2 = textoListaSeleccionMultiple2;
	}

	public String getValorListaProyectoParametrizado2() {
		return valorListaProyectoParametrizado2;
	}

	public void setValorListaProyectoParametrizado2(String valorListaProyectoParametrizado2) {
		this.valorListaProyectoParametrizado2 = valorListaProyectoParametrizado2;
	}

	public List<ValoresListasProyecto> getValoresListasProyectosParametrizado2() {
		return valoresListasProyectosParametrizado2;
	}

	public void setValoresListasProyectosParametrizado2(List<ValoresListasProyecto> valoresListasProyectosParametrizado2) {
		this.valoresListasProyectosParametrizado2 = valoresListasProyectosParametrizado2;
	}

	public SelectItem[] getListaSeleccionMultipleValoresProyecto2() {
		return listaSeleccionMultipleValoresProyecto2;
	}

	public void setListaSeleccionMultipleValoresProyecto2(SelectItem[] listaSeleccionMultipleValoresProyecto2) {
		this.listaSeleccionMultipleValoresProyecto2 = listaSeleccionMultipleValoresProyecto2;
	}

	public ValoresListasProyecto getValorListaProyectoSeleccionadaParametrizada2() {
		return valorListaProyectoSeleccionadaParametrizada2;
	}

	public void setValorListaProyectoSeleccionadaParametrizada2(ValoresListasProyecto valorListaProyectoSeleccionadaParametrizada2) {
		this.valorListaProyectoSeleccionadaParametrizada2 = valorListaProyectoSeleccionadaParametrizada2;
	}

	public void adicionarValorProyectoParametrizado2() {
		List<DominioDetalle> listaDomDet = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select e from DominioDetalle e, Dominio d where e.identificador.tipo = '" + valorListaProyectoParametrizado2
						+ "' and e.identificador.id = d.id and d.tipo = '" + dominioValoresListaSelMul2 + "'");
		DominioDetalle g = obtenerDominioDetalleLista(valorListaProyectoParametrizado2, listaDomDet);

		ValoresListasProyecto vlp = new ValoresListasProyecto();
		vlp.setProyecto(proyectoActual);
		vlp.setTipo(dominioValoresListaSelMul2);
		vlp.setValor(g.getIdentificador().getTipo());
		vlp.setDescripcion(g.getDescripcion());

		if (!valoresListasProyectosParametrizado2.contains(vlp)) {
			valoresListasProyectosParametrizado2.add(vlp);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La opción seleccionada ya se encuentra asociada al proyecto",
					"La opción seleccionada ya se encuentra asociada al proyecto");
		}

	}
	
	public void eliminarValorProyectoParametrizado2() {
		valoresListasProyectosParametrizado2.remove(valorListaProyectoSeleccionadaParametrizada2);
		valoresListasProyectosBorrados.add(valorListaProyectoSeleccionada);
		valorListaProyectoSeleccionadaParametrizada2 = new ValoresListasProyecto();
	}

	public String getDominioValoresListaSelMul2() {
		return dominioValoresListaSelMul2;
	}

	public void setDominioValoresListaSelMul2(String dominioValoresListaSelMul2) {
		this.dominioValoresListaSelMul2 = dominioValoresListaSelMul2;
	}

	public String getJustificacionEquipo() {
		return justificacionEquipo;
	}

	public void setJustificacionEquipo(String justificacionEquipo) {
		this.justificacionEquipo = justificacionEquipo;
	}

	public Long getValorEquipo() {
		return valorEquipo;
	}

	public void setValorEquipo(Long valorEquipo) {
		this.valorEquipo = valorEquipo;
	}
}
