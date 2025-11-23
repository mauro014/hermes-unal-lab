package co.edu.unal.hermes.vista.seguimiento;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.dao.DataIntegrityViolationException;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.AgendaConocimiento;
import co.edu.unal.hermes.modelo.AgendaProyecto;
import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.modelo.ArchivoResumen;
import co.edu.unal.hermes.modelo.AreaTematica;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Contrapartida;
import co.edu.unal.hermes.modelo.Convenio;
import co.edu.unal.hermes.modelo.ConvenioObligacion;
import co.edu.unal.hermes.modelo.ConvenioOtrosi;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DependenciaAreaResponsabilidad;
import co.edu.unal.hermes.modelo.Dominio;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoCarta;
import co.edu.unal.hermes.modelo.EstadoInforme;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoBorrarFecha;
import co.edu.unal.hermes.modelo.HistoricoCambioIntegrantes;
import co.edu.unal.hermes.modelo.HistoricoEstadoEvaluacion;
import co.edu.unal.hermes.modelo.HistoricoEstadoLegalizacion;
import co.edu.unal.hermes.modelo.HistoricoEstadoProyecto;
import co.edu.unal.hermes.modelo.HistoricoEstadoSolicitud;
import co.edu.unal.hermes.modelo.HistoricoHabilitacionEdicionProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.JornadaDocente;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.ObservacionSeguimiento;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoCarta;
import co.edu.unal.hermes.modelo.ProyectoCompromiso;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.ProyectoLegalizacionOcad;
import co.edu.unal.hermes.modelo.ProyectoProducto;
import co.edu.unal.hermes.modelo.ProyectoSaldoFinanciacion;
import co.edu.unal.hermes.modelo.Registro;
import co.edu.unal.hermes.modelo.ResultadoProyecto;
import co.edu.unal.hermes.modelo.SolicitudInforme;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoCarta;
import co.edu.unal.hermes.modelo.TipoConvenio;
import co.edu.unal.hermes.modelo.TipoDuracion;
import co.edu.unal.hermes.modelo.TipoFinanciacion;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.modelo.seguimiento.AlertaProyecto;
import co.edu.unal.hermes.modelo.seguimiento.DetalleAdicionPresupuesto;
import co.edu.unal.hermes.modelo.seguimiento.DetalleCambioRubro;
import co.edu.unal.hermes.modelo.seguimiento.ObservacionProyecto;
import co.edu.unal.hermes.modelo.seguimiento.ProyectoProrroga;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudAdicionPresupuestal;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudDocumento;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudInvestigador;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudProrrogaInvestigador;
import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;
import co.edu.unal.hermes.modelo.seguimiento.TramiteSolicitud;
import co.edu.unal.hermes.utils.Fecha;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.vista.asesor.ManejadorConsultaHistoricos;
import co.edu.unal.hermes.vista.evaluadores.ProyectoCoordinador;
import co.edu.unal.hermes.vista.proyectos.ManejadorFichaMinimaHome;
import co.edu.unal.hermes.vista.proyectos.ManejadorRegistroInforme;
import co.edu.unal.hermes.vista.proyectos.ProyectoVista;

import com.lowagie.text.pdf.codec.postscript.ParseException;

public class ManejadorSeguimiento extends ManejadorBaseSeguimiento {

	private static final long serialVersionUID = 9049201866673489215L;
	private static final String TIPO_DURACION_DEFECTO = "meses";
	private static final String DOMINIO_PROGRAMA = "PROGRAMA NACIONAL DE CyT";
	private static final String DOMINIO_TIPO_ENTIDAD = "TIPO_ENTIDADES_FINANCIACION";

	private static final String DOMINIO_MOD_CONTRATACION = "MODALIDAD_CONTRATACION";
	private static final int NUMERO_PLANTILLA_INICIO_PROYECTO = 208;
	private static final String RUTA_ADJUNTO = "/pages/Evaluadores/";
	private static final SelectItem[] EXONERACION_COSTOS_ITEMS = { new SelectItem("SI", "SI"),
			new SelectItem("NO", "No") };
	private static final SelectItem[] CAMBIOS_PROYECTO_ITEMS = { new SelectItem(true, "SI"),
			new SelectItem(false, "No") };
	private static final SelectItem[] ACUERDO_ITEMS = { new SelectItem("convenio", "convenio"),
			new SelectItem("contrato", "contrato"), new SelectItem("acta", "acta"), };

	private List<AlertaProyecto> alertasAsesor;
	private Persona asesor;
	private List<ProyectoInforme> listaInformes;
	private AlertaProyecto alertaSeleccionada;
	private String mensajeActualizacion;
	private Long montoAprobadoProyecto;
	private Persona investigadorPrincipal;
	private SelectItem[] permitirEditarItem;
	private SelectItem[] tiposCarta;
	String tipoCartSeleccionado;
	private SelectItem[] plantillas;
	private String avalId;
	CorreoPlantilla correoActual = new CorreoPlantilla();
	String correoAnterior;
	private Date fechaSesion;
	List<Aval> avalesAsociadosProyecto;
	List<Proyecto> autorizacionesBiodiversidadProyecto;
	ProyectoCoordinador proyectoCoordinadorUno;
	private Map dominioEstadoAlerta;

	private Long idProyectoalertas;
	private Persona coordinadorSeguimiento;
	private ProyectoEvaluador proyectoEvaluadorSeleccionado;
	private ProyectoProrroga prorrogaSeleccionada;
	private Map<String, String> dominioRespuestaSolicitud;
	private List<SelectItem> opcionesDominioRespuestaSolicitud;
	private ProyectoInforme informeSeleccionado;
	private boolean esCoordinador;
	private boolean esCoordinadorEvaluacion;
	private boolean esCoordinadorRequisitos;
	private boolean mostrarCompromisos = false;
	private SelectItem[] tiposViaSolicitudItem;
	private SelectItem[] gastosProyectoItem;
	private String tipoActividadNombre;

	private Ciudad ciudadProyecto;
	private List<AlertaProyecto> filteredAlertas;
	private String cedulaEstudiante;
	private UIData tablaTramites;
	private Solicitud solicitudSeleccionada;
	private Solicitud solicitudSeleccionadaIntegrantes;
	private Solicitud solicitudSeleccionadaIntegrantesProrroga;
	private SolicitudDocumento documentoSeleccionado;
	private TramiteSolicitud tramiteSeleccionado;

	private List<Archivo> listaArchivosProyecto;
	private UIData tablaObservaciones;
	private UIData tablaProductos;
	private SelectItem[] fuentesProyectoItem;
	private SelectItem[] alertasProyectoItem;
	private String asuntoAlertaCorreo;
	private String destinoCorreo;
	private String cuerpoCorreo;
	private String asuntoCorreo;
	private SelectItem[] plantillasActaItem;
	private String copiaCorreo;
	private String origenCorreo;

	private org.primefaces.model.UploadedFile archivo;

	private Dependencia dependenciaProyecto;
	private String codigoQuipu = "";
	private boolean tieneVariosCodigoQuipu = false;
	private String segundoCodigoQuipu = "";
	private boolean mostrarCodigoQuipu;
	private String codigoQuipuAux;
	private String segundoCodigoQuipuAux;
	private String mensajeTransaccionRubro = "";
	private String tipoDuracion;

	/****************************
	 * ACTAS PROYECTO
	 ****************************/
	private SelectItem[] estudianteItem;
	private SelectItem[] jovenInvExternoItem;

	private SelectItem[] facultadInstItem;
	private String facInstitutoSel;

	// por facultad
	private List<ProyectoCoordinador> listaProyectosCoordinador;
	private String estadoProyectoLegalizacion;
	private boolean mostrarEstudiantes;
	private boolean mostrarJovenInvExterno;
	private String errorValidacion = "";
	private String dependenciaRes;
	private boolean docenteYaFirmo;
	private SelectItem[] tipoActoAdministrativoItem;
	private List<DominioDetalle> listaTiposActoAdministrativo;
	private int nPlantilla = 0;
	String fechaS = "";
	String fechaF = "";
	String nombreParametro = "Sesión: ";
	private String mensajeCartas = "";
	private boolean mostrarGenerarPDF = true;
	private ProyectoCarta proyectoCarta;
	private boolean mostrarFechaSesion = false;
	private boolean mostrarCambioRubro = false;
	private boolean mostrarParametrosCertificadoMovilizacion = false;
	private boolean mostrarCodigoSolicitudJustificacion = false;
	private boolean mostrarSaldoFinanciacion = false;
	private Long nConsecutivoCarta;
	private String saldoFinanciacion;
	private String idParDepRes;
	private SelectItem[] codigoSolicitudItem;
	private SelectItem[] incluirJustificacionItem;
	private String incluirJustificacionSolicitud;
	private String codigoSolicitud;
	private String codigoSolicitudCertificado;
	private SelectItem[] codigoSolicitudProrrogaItem;
	private String codigoSolicitudProrroga;
	private String mensajeEnvioCarta = "";
	private String mensajeSubidaArchivo = "";
	ProyectoEvaluador evaluacionMesaSeleccion = null;
	private boolean mostrarPanelCorreo;
	private List<Archivo> listaArchivos;
	private org.primefaces.model.UploadedFile archivoCargado;
	private List<ProyectoEvaluador> listaProyectoEvaluador;
	private List<ProyectoEvaluador> listaProyectoSeleccion;
	private ArchivoResumen archivoResumenSeleccionado;
	private SelectItem[] recomendaciones;

	/** The dependencia area responsabilidad seleccionada. */
	private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada;

	private SelectItem[] estadoItems;
	private SelectItem[] tipologiaItems;
	private String unidadEjecutora;
	private String unidadEjecutoraInicial;
	private SelectItem[] unidadEjecItems;
	private String actoEntidad;
	private String entidadContratante;
	private SelectItem[] entidadItems;
	private SelectItem[] entidadItemsOcad;
	private String tipofechaPry;
	private SelectItem[] tipofechaItems;
	private String desembolsos;
	private SelectItem[] desembolsoItems;
	private String informes;
	private SelectItem[] informesItems;
	private SelectItem[] trasladoItems;
	private String vinculaFacultades = "0";
	private SelectItem[] vinculacionItems;
	private String vinculaSedes = "0";
	private String sedeSel;
	private SelectItem[] sedesItems;
	private SelectItem[] vigenciaItems;
	private SelectItem[] programaItems;
	private List<Dependencia> dependenciasUN;
	private SelectItem[] dependenciaItem;
	private String entidadSel;
	private Financiacion entidadSeleccionada;
	private String vinculaEntidades = "0";
	private Long valorEntidad = 0L;
	private int nPlantillaUsuario = 200;
	private boolean mostrarFuenteExterna;
	private boolean requiereOtraFuente;
	private String nombreFuente;
	private String nitFuente;
	private String naturalezaFuente;
	private String telefonoFuente;
	private String direccionFuente;
	private UIComponent cBCrearFuente;
	private UIComponent codExterna;
	private SelectItem[] tipologiaSecItems;
	private SelectItem[] periodoInformesItems;
	private boolean verPeriodosInforme = true;
	private boolean tieneVersiones;
	private List<Proyecto> listaVersiones = new ArrayList<Proyecto>();
	private String tipoEntidadSel;
	private SelectItem[] tipoEntidadItems;
	private List<SelectItem> entidadDesembolsosItems;
	private Long valorEntidadEsp = 0L;
	private UIComponent uiEntidades;
	private UIComponent uiObligaciones;
	private UIComponent uiDesembolsos;
	private SelectItem[] motivoEstadoItems;
	private SelectItem[] clausulasItems;
	private Aval avalAsociadoPry;
	private List<Aval> listaAvales;
	private Long avalProy;
	private SelectItem[] compromisoEjecItems;
	private Long numActaInicioExt;
	private Date fechaActaInicioExt;
	private String obligacionesUN;
	private Date fechaObligacion;
	private ConvenioObligacion obligacionSeleccionada;
	private Long valorDesembolso;
	private String desembolso = "0";
	private int numDesembolso = 1;
	private Date fechaInformeParcial;
	private UIComponent uiInformes;
	private UIComponent uiInformesInternas;
	private ProyectoCompromiso compromisoProyectoSeleccionado;
	private Convenio convenioActual;
	private ProyectoCarta actaProyectoActual = new ProyectoCarta();
	private String entidadDesembolso;
	private Gasto desembolsoSeleccionado;
	private Gasto recursoSeleccionado;
	private String entidadRecurso;
	private String categoria;
	private String recurso;
	private SelectItem[] categoriaIngresoItems;
	private SelectItem[] recursoIngresoItems;
	private SelectItem[] modContratacionItems;
	private int anio;
	private SelectItem[] anioItems;
	private Long valorIngreso;
	private Long valorIngreso2;
	private Long valorIngreso3;
	private Long anioVigIngreso = 2013L;
	private UploadedFile archivoLeg;
	private TipoArchivo tipoArchivo;
	private SelectItem[] tipoArchivoItem;
	private List<Archivo> listaArchivosLeg = new ArrayList<Archivo>();
	private DataTable tablaArchivosLeg;
	private Archivo archivoSeleccionadoLeg;
	private String asignacionActual;
	private Date fechaDesembolso;
	private boolean edicionFormalizacionHabilitada;
	private int nPlantillaDocente = 253;
	private Date fechaInicioConvenio;
	private Date fechaFinConvenio;

	private String nuevaObservacion;
	private String tipoObservacion;
	ObservacionSeguimiento observacionSeguimientoSeleccionada;
	private UIComponent btnCopiaProyecto;
	private Long idPryConsulta;
	private String agendaConocimientoId = "";
	private AgendaProyecto agendaProyectoActual;
	private SelectItem[] agendaConocimientoItem;

	/**
	 * Variables para parametrizar firma certificado de movilizacion
	 */
	private String nombreVicerrector = "";
	private String nombreCargoVicerrector = "";
	private boolean cambiarFirma = false;
	private SolicitudInvestigador solicitudInvestigadorSeleccionada;

	private Date fechaCalculaProrroga;
	private String mesesCalculo;
	private String diasCalculo;

	// Asignacion manual de compromisos
	private String tipoCompromiso;
	private Date fechaVencimientoCompromiso;
	private ProyectoCompromiso compromisoSeleccionado;

	List<HistoricoEstadoSolicitud> historicoEstadoSolicitudes;

	// Reclamaciones
	private SelectItem[] opcionesReclamacionesItem;
	private boolean esRespuestaReclamacion = false;
	private boolean esReclamacionProfesor = false;

	private boolean esRespuestaReclamacionEvaluacion = false;
	private boolean esReclamacionProfesorEvaluacion = false;

	private String errorDevolucion = "";

	private boolean habilitarEdicionProyecto;

	// form resolucion legalizacion
	private String numeroResolucion;
	private Date fechaResolucion;
	private String ordenadorTipoResolucion;
	private String ordenadorNombreResolucion;
	private String facultadResolucion;
	private SelectItem[] facultadResolucionItems;
	private List<DominioDetalle> ordenadorTipoLista;
	private SelectItem[] ordenadorTipoItems;

	private List<ProyectoCompromiso> listaCompromisosInformesParcialesYFinales;
	private List<ProyectoCompromiso> listaCompromisosDesembolsos;
	private List<ProyectoCompromiso> listaCompromisosObligaciones;
	private boolean esVacialistaDesembolsos;
	private boolean esVacialistaCompromisosObligaciones;

	Boolean esConvocatoriaLibros = false;

	private String otrosi;
	private String otrosiNum;
	private Date otrosiFecha;

	private char requiereConsiderando;
	private String considerando;
	private Boolean esConvocatoriaEventos = false;
	private Boolean bloquearSolicitudes = false;
	private List<DetalleAdicionPresupuesto> listaSolicitudAdicionPresupuesto;
	private SolicitudAdicionPresupuestal adicionPresupuesto;
	// Archivos reclamaciones requisitos
	private List<Archivo> listaArchivosReclamacionRequisitos;
	private Archivo archivoRecReqSeleccionado;
	private boolean noAplicaCodigoQuipu = false;

	private SelectItem[] facultadInstFormaItem;
	private String mesesCalculoVista;
	private String diasCalculoVista;

	private HistoricoCambioIntegrantes hci;

	private Long sumaTotalDesembolsos = 0L;
	private Long sumaTotalIngresos = 0L;

	private String facHist;
	private String sedHist;

	private String sedesProyecto = "";

	private String justificacionBancoFinanciable;

	private Long montoAdicion;
	private boolean tieneAvalRegaliasAprobado;
	private ProyectoLegalizacionOcad legalizacionOcad;
	private boolean esCoejecutorCooperante;
	private boolean tieneAvalConvocatoriaRegaliasAprobado;

	/********************************************************************************
	 * CONSTRUCTOR DE LA CLASE
	 ********************************************************************************/

	public ManejadorSeguimiento() {

		asesor = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		alertasAsesor = servicioAlertas.listarAlertasAsesorInbox(asesor);
		listaProyectosCoordinador = new ArrayList<ProyectoCoordinador>();
		autorizacionesBiodiversidadProyecto = new ArrayList<Proyecto>();
		legalizacionOcad = new ProyectoLegalizacionOcad();
		tieneAvalRegaliasAprobado = false;
		cargarListas();

	}

	public void cargarDatosAdicionalesProyecto() {

		if (proyectoActual.getPresentaInformeParcial() == null) {
			proyectoActual.setPresentaInformeParcial("NO");
		}

		asignarSedesProyecto();

		autorizacionesBiodiversidadProyecto = new ArrayList<Proyecto>();
		agendaProyectoActual = null;

		listaCompromisosObligaciones = new ArrayList<ProyectoCompromiso>();
		listaCompromisosInformesParcialesYFinales = new ArrayList<ProyectoCompromiso>();

		coordinadorSeguimiento = servicioProyecto.obtenerCoordinadorProyecto(proyectoActual.getId());

		String hqlFinanciacion = "select #tipoFinanciacion e.tipoFinanciacion from Convocatoria e, Proyecto p where e.id = p.modalidad.id and p.id = "
				+ proyectoActual.getId();
		List<Convocatoria> listaConvFin = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class, hqlFinanciacion);
		String tipoFin = listaConvFin.get(0).getTipoFinanciacion();
		montoAprobadoProyecto = servicioProyecto.obtenerMontoAprobadoProyecto(proyectoActual.getId(), tipoFin);

		Boolean esProyectoLaboratorios;
		try {
			esProyectoLaboratorios = proyectoActual.getModalidad().getTipo().getId()
					.equals(TipoModalidad.REGISTRO_PROYECTOS_LABORATORIOS);
		} catch (Exception e) {
			esProyectoLaboratorios = false;
		}
		if (esProyectoLaboratorios) {
			montoAprobadoProyecto = servicioProyecto.obtenerMontoAprobadoProyectoLaboratorios(proyectoActual.getId(),
					proyectoActual.getModalidad().getId());
		}

		if (proyectoActual.getModalidad().getTipo().getId().equals("CL")) {
			esConvocatoriaLibros = true;
		}

		if (proyectoActual.getModalidad().getTipo().getId().equals("CFM")) {
			Convocatoria convocatoria = servicioModalidad.obtenerConvocatoria(proyectoActual.getModalidad().getId());
			if (convocatoria != null && (convocatoria.getRestriccion().getId().equals("CE1-N2017")
					|| convocatoria.getRestriccion().getId().equals("CE2-N2017"))) { // CONVOCATORIA
																						// DE
																						// EVENTOS
																						// 2017-2018
				esConvocatoriaEventos = true;
			}
		}

		errorDevolucion = "";

		cargarFormatoFecha();
		calcularPeriodoSuspension();

		// Se carga la información de las solicitudes.
		cargarSolicitudes();

		investigadorPrincipal = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());

		List<Financiacion> listaFuentesProyecto = servicioProyecto
				.obtenerFunetesFinanciacionProyecto(proyectoActual.getId());

		fuentesProyectoItem = new SelectItem[listaFuentesProyecto.size() + 1];
		fuentesProyectoItem[0] = new SelectItem("", "");
		for (int i = 1; i < listaFuentesProyecto.size() + 1; i++) {
			Financiacion financiacion = (Financiacion) listaFuentesProyecto.get(i - 1);
			fuentesProyectoItem[i] = new SelectItem(financiacion.getId(), financiacion.getFuente().getDescripcion());
		}

		gastosProyectoItem = new SelectItem[1];
		gastosProyectoItem[0] = new SelectItem("", "");

		cargarFechasProyecto(proyectoActual);

		mensajeActualizacion = "";

		inicializarCuerpoCorreo();

		// Revisar si el proyecto tiene versiones
		tieneVersiones = revisarTieneVersiones();

		// Jovenes investigadores ysv
		if (proyectoActual.getAvalAsociado() != null) {
			listaAvales = servicioGeneral.obtenerObjetos(Aval.class,
					"from Aval where aviId = '" + proyectoActual.getAvalAsociado() + "'");
			avalAsociadoPry = (Aval) listaAvales.get(0);
			avalProy = avalAsociadoPry.getIdProyecto();

		}

		autorizacionesBiodiversidadProyecto = autorizacionesBiodiversidadProyecto(proyectoActual.getId());

		avalesAsociadosProyecto = obtenerAvalesProyecto(proyectoActual.getId());

		Iterator<Aval> a = avalesAsociadosProyecto.iterator();

		tieneAvalConvocatoriaRegaliasAprobado = false;
		while (a.hasNext()) {
			Aval avalr = a.next();
			if (avalr.isEsConvocatoriaRegalias() && avalr.isEsAvalAprobadoParaLegalizacion()) {
				tieneAvalConvocatoriaRegaliasAprobado = true;
				break;
			}
		}

		/************************************************************************
		 * LEGALIZACION
		 *************************************************************************/

		if (proyectoActual.getDesembolsosAdicionados().size() > 0) {
			List<Gasto> desembolsosAux = proyectoActual.getDesembolsosAdicionados();
			for (Iterator iterator = desembolsosAux.iterator(); iterator.hasNext();) {
				Gasto gasto = (Gasto) iterator.next();
				sumaTotalDesembolsos += gasto.getValor();
			}
		}

		if (proyectoActual.getRecursosAdicionados().size() > 0) {
			List<Gasto> ingresosAux = proyectoActual.getRecursosAdicionados();
			for (Iterator iterator = ingresosAux.iterator(); iterator.hasNext();) {
				Gasto gasto = (Gasto) iterator.next();
				if (gasto.getTipoRubro().getDescripcion().equals("INGRESOS_CP_2022")) {
					gasto.setTipoRubro((TipoRubro) servicioGeneral.obtenerObjetoYPadre(new TipoRubro(),
							gasto.getTipoRubro().getId()));
				} else if ("142,145,144,143,82,141,135,7,120,".contains(gasto.getTipoRubro().getId() + ",")) {
					gasto.getTipoRubro()
							.setPadre((TipoRubro) servicioGeneral.obtenerObjetoXID(TipoRubro.class, "53").get(0));
				} else {
					gasto.setTipoRubro(servicioGeneral
							.obtenerObjetoXID(TipoRubro.class, gasto.getTipoRubro().getId().toString()).get(0));
					TipoRubro sinPadre = new TipoRubro();
					sinPadre.setDescripcion("Sin categoría");
					gasto.getTipoRubro().setPadre(sinPadre);
				}
				gasto.setValor(esNulo(gasto.getValor()) ? 0L : gasto.getValor());
				gasto.setValor2(esNulo(gasto.getValor2()) ? 0L : gasto.getValor2());
				gasto.setValor3(esNulo(gasto.getValor3()) ? 0L : gasto.getValor3());
				sumaTotalIngresos += gasto.getValor() + gasto.getValor2() + gasto.getValor3();
			}
		}

		boolean convocatoriaExterna = false;

		if (proyectoActual.isHabilitadoParaLegalizacionExterna()) {

			// validar si se habilita el formulario de legalización teniendo en
			// cuenta los avales aprobados

			proyectoActual.setTieneAvalesAprobados(false);
			setTieneAvalRegaliasAprobado(false);

			legalizacionOcad = new ProyectoLegalizacionOcad();

			Iterator<Aval> i = avalesAsociadosProyecto.iterator();

			while (i.hasNext()) {
				Aval aval = i.next();
				if (aval.isEsAvalAprobadoParaLegalizacion()) {
					proyectoActual.setTieneAvalesAprobados(true);
					if (aval.isEsConvocatoriaRegalias()
							&& "S".equals(proyectoActual.getPermitirAvalRegaliasRequisitos())) {
						proyectoActual.setTieneAvalesAprobados(false);
					}
					if (aval.isEsRegalias() || aval.isEsPaedRegalias() || aval.isEsRequisitosRegalias()) {
						setTieneAvalRegaliasAprobado(true);
						cargarListaEstadosConvExterna();
						List<ProyectoLegalizacionOcad> legalizacionRegalias = servicioGeneral.obtenerObjetos(
								ProyectoLegalizacionOcad.class,
								"from ProyectoLegalizacionOcad where idProyecto = '" + proyectoActual.getId() + "' ");
						if (legalizacionRegalias != null && legalizacionRegalias.size() > 0) {
							legalizacionOcad = new ProyectoLegalizacionOcad(legalizacionRegalias.get(0));
							if (legalizacionRegalias.get(0).getDirectorProyecto() != null) {
								legalizacionOcad.setDirectorProyecto(legalizacionRegalias.get(0).getDirectorProyecto());
							} else {
								legalizacionOcad.setDirectorProyecto(new Persona());
							}
						} else {
							legalizacionOcad.setIdProyecto(proyectoActual.getId());
							legalizacionOcad
									.setValorParticipantes(proyectoActual.getValorEntidadesParticipantesCalculado());
							legalizacionOcad.setValorAprobadoSgr(proyectoActual.getTotalSgr());
							legalizacionOcad.setValorOtraFinanciacion(proyectoActual.getTotalFuentesNoRegalias());
						}
					}
					break;
				}
			}

			cargarDatosGuardadosLegalizacion();
			convocatoriaExterna = true;

		}

		List<Financiacion> financiaciones = proyectoActual.getListaEntidadesLegalizacion();

		if (!convocatoriaExterna && esListaVacia(financiaciones)) {

			// Se crean las nuevas fuentes.
			List<Financiacion> financiacionesInterna = proyectoActual.getListaFinanciones();
			Iterator<Financiacion> i = financiacionesInterna.iterator();

			while (i.hasNext()) {
				Financiacion financiacionIntera = i.next();
				FuenteFinanciacion fuente = (FuenteFinanciacion) financiacionIntera.getFuente();
				Financiacion nuevaFinancacion = new Financiacion();
				nuevaFinancacion.setFuente(fuente);
				nuevaFinancacion.setValor(0L);
				nuevaFinancacion.setValorEspecie(0L);
				nuevaFinancacion.setTipoEntidad(FuenteFinanciacion.FINANCIADORA);
				nuevaFinancacion.setEsLegalizacion(Financiacion.SI_ES_LEGALIZACION);

				if (!proyectoActual.isExisteEntidadesLegalizacion(fuente.getId())) {
					proyectoActual.adicionarFinanciacion(nuevaFinancacion);
				}
			}

			financiaciones = proyectoActual.getListaEntidadesLegalizacion();
		}

		cargarListadoEntidadesItem(financiaciones, convocatoriaExterna);

		cargarCompromisosInformes();
		cargarCompromisosObligaciones();
		cargarCompromisosInformesParcialesYFinales();

		// CARTAS

		List<DominioDetalle> tiposCartaList = new ArrayList<DominioDetalle>();

		if (!proyectoActual.getModalidad().getId().equals(10L)
				|| (proyectoActual.getModalidad().getId().equals(10L) && proyectoActual.getTipologiaProyecto() != null
						&& proyectoActual.getTipologiaProyecto().equals("ET"))) {
			tiposCartaList = servicioGeneral.obtenerObjetos(DominioDetalle.class,
					"select dd from Dominio d, DominioDetalle dd where (d.id =" + " dd.identificador.id and d.id ='"
							+ TIPO_CARTA_DOMINIO
							+ "' and dd.estado='A') and dd.identificador.tipo <> '17' order by dd.identificador.tipo");
		} else {
			tiposCartaList = servicioGeneral.obtenerObjetos(DominioDetalle.class,
					"select dd from Dominio d, DominioDetalle dd where (d.id =" + " dd.identificador.id and d.id ='"
							+ TIPO_CARTA_DOMINIO
							+ "' and dd.estado='A') and dd.identificador.tipo not in ('7','3','4','11') order by dd.identificador.tipo");
		}

		if (!esListaVacia(tiposCartaList)) {
			tiposCarta = new SelectItem[tiposCartaList.size()];

			if (!esListaVacia(tiposCartaList)) {
				for (int i = 0; i < tiposCartaList.size(); i++) {
					DominioDetalle dd = (DominioDetalle) tiposCartaList.get(i);
					tiposCarta[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
				}
				tipoCartSeleccionado = ((DominioDetalle) tiposCartaList.get(0)).getIdentificador().getTipo();

			}
		}

		/************************************************************************
		 *** FIN LEGALIZACION
		 *****************************************************/
		// archivos reclamaciones requisitos
		listaArchivosReclamacionRequisitos = servicioProyecto.obtenerNombresArchivosReclamacion(proyectoActual);

		// respuesta reclamación
		if (proyectoActual.getEstadoReclamacion() != null) {
			if (proyectoActual.getEstadoReclamacion().equals(Proyecto.RECLAMACION_APROBADO)
					|| proyectoActual.getEstadoReclamacion().equals(Proyecto.RECLAMACION_RECHAZADO)) {
				esRespuestaReclamacion = true;
				esReclamacionProfesor = true;
			} else {
				esReclamacionProfesor = true;
			}
		}

		// respuesta reclamación evaluación
		if (proyectoActual.getEstadoReclamacionEvaluacion() != null) {
			if (proyectoActual.getEstadoReclamacionEvaluacion().equals(Proyecto.RECLAMACION_APROBADO)
					|| proyectoActual.getEstadoReclamacionEvaluacion().equals(Proyecto.RECLAMACION_RECHAZADO)) {
				esRespuestaReclamacionEvaluacion = true;
				esReclamacionProfesorEvaluacion = true;
			} else {
				esReclamacionProfesorEvaluacion = true;
			}
		}
		isEmptylistaDesembolsos();
		isEmptylistaCompromisosObligaciones();
		if ((proyectoActual.getEsJornadaDocente() != null && proyectoActual.getEsJornadaDocente().equals("Y"))
				|| proyectoActual.isEsRegistroBiodiversidad()) {
			bloquearSolicitudes = false;
		} else {
			if (proyectoActual.getCodigoQuipu() != null && !proyectoActual.getCodigoQuipu().isEmpty()) {
				bloquearSolicitudes = false;
				codigoQuipuAux = proyectoActual.getCodigoQuipu();
			} else {
				bloquearSolicitudes = true;
				codigoQuipuAux = "";
			}
		}

		if (!proyectoActual.getEstadoProyecto().getId().equals("I")) {
			List<HistoricoCambioIntegrantes> listaHCI = servicioGeneral.obtenerObjetos(HistoricoCambioIntegrantes.class,
					"from HistoricoCambioIntegrantes h where h.proyecto.id = '" + proyectoActual.getId()
							+ "' and h.integrante.id.documento='" + investigadorPrincipal.getId().getDocumento()
							+ "' and h.integrante.id.tipoDocumento='" + investigadorPrincipal.getId().getTipoDocumento()
							+ "' and h.tipoInvestigadorProyecto.id='P' order by h.id asc");
			System.out.println(listaHCI.size());
			if (listaHCI.size() != 0) {
				setFacHist(listaHCI.get(0).getDependencia().getFacultad().getNombre());
				setSedHist(listaHCI.get(0).getDependencia().getSede().getNombre());
			} else {
				setFacHist(((Investigador) investigadorPrincipal).getDependencia().getFacultad().getNombre());
				setSedHist(((Investigador) investigadorPrincipal).getDependencia().getSede().getNombre());
			}
		}

	}

	public void guardarSeguimientoDesembolsos() {
		try {
			servicioGeneral.guardarObjeto(proyectoActual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void descargarArchivoReclamacionRequisitos() {
		Long id = archivoRecReqSeleccionado.getId();
		descargarArchivoProyectoGenerico(id, proyectoActual.getId());
	}

	public void asignarSedesProyecto() {
		sedesProyecto = "";
		if (!proyectoActual.getListaDependenciasAreaResponsabilidad().isEmpty()) {
			ArrayList<DependenciaAreaResponsabilidad> dependenciasAreaResp = (ArrayList<DependenciaAreaResponsabilidad>) proyectoActual
					.getListaDependenciasAreaResponsabilidad();
			for (int i = 0; i < dependenciasAreaResp.size(); i++) {
				DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad = (DependenciaAreaResponsabilidad) dependenciasAreaResp
						.get(i);
				if (dependenciasAreaResp.size() == 1) {
					sedesProyecto = sedesProyecto
							+ dependenciaAreaResponsabilidad.getDependencia().getSede().getNombre();
				} else {
					if (i == dependenciasAreaResp.size() - 1) {
						if (!sedesProyecto
								.contains(dependenciaAreaResponsabilidad.getDependencia().getSede().getNombre())) {
							sedesProyecto = sedesProyecto
									+ dependenciaAreaResponsabilidad.getDependencia().getSede().getNombre();
						} else {
							String tmp = sedesProyecto.substring(0, sedesProyecto.length() - 2);
							sedesProyecto = tmp;
						}
					} else {
						if (!sedesProyecto
								.contains(dependenciaAreaResponsabilidad.getDependencia().getSede().getNombre())) {
							sedesProyecto = sedesProyecto
									+ dependenciaAreaResponsabilidad.getDependencia().getSede().getNombre() + ", ";
						}
					}
				}
			}
		}
	}

	public void actualizarCorreoPlantillaInicio() {
		if (docenteYaFirmo) {
			correoActual = cargarPlantilla(CorreoPlantilla.INICIO_FIRMADO);
			String cuerpo = correoActual.getCuerpo();

			Persona pe = this.servicioProyecto.obtenerInvestigadorPrincipalXProyecto(this.proyectoActual.getId());
			List inv = servicioGeneral.obtenerListaObjetosWhere("Persona p", "where p.id.documento='"
					+ pe.getId().getDocumento() + "' and p.id.tipoDocumento='" + pe.getId().getTipoDocumento() + "'");

			if (!esListaVacia(inv)) {
				Persona e = (Persona) inv.get(0);
				String investigador = e.getNombre1() + " " + e.getApellido1() + " " + e.getApellido2();
				cuerpo = cuerpo.replaceAll("<<INVESTIGADOR>>", investigador);
			}
			String nombre = Matcher.quoteReplacement(this.proyectoActual.getNombre());
			cuerpo = cuerpo.replaceAll("<<TITULO>>", nombre);

			cuerpo = cuerpo.replaceAll("<<ID>>", String.valueOf(this.proyectoActual.getId()));

			Persona coordinador = this.servicioProyecto.obtenerCoordinadorProyecto(this.proyectoActual.getId());
			if (coordinador != null) {
				cuerpo = cuerpo.replaceAll("<<COORDINADOR>>",
						coordinador.getNombre1() + " " + coordinador.getApellido1() + " " + coordinador.getApellido2());
			} else {
				cuerpo = cuerpo.replaceAll("<<COORDINADOR>>", "");
			}
			correoAnterior = cuerpoCorreo;

			correoActual.setCuerpo(cuerpo);

			cuerpoCorreo = correoActual.getCuerpo();
		} else {
			cuerpoCorreo = correoAnterior;
		}
	}

	private void cargarDatosGuardadosLegalizacion() {
		// Cargar datos guardados de legalización

		if (proyectoActual != null && proyectoActual.getId() != null) {
			// Estado
			if (proyectoActual.getEstadoProyecto() != null) {
				estadoProyecto = proyectoActual.getEstadoProyecto().getId();
				estadoProyectoLegalizacion = proyectoActual.getEstadoProyectoLegalizacion().getId();
			}

			List<AgendaProyecto> agendasProyecto = servicioGeneral.obtenerObjetos(AgendaProyecto.class,
					"from AgendaProyecto a where a.proyecto.id = '" + proyectoActual.getId() + "'");
			if (!esListaVacia(agendasProyecto)) {
				agendaProyectoActual = agendasProyecto.get(0);
			}
			if (agendaProyectoActual != null) {
				agendaConocimientoId = agendaProyectoActual.getAgenda().getId();
			}

			// Convenio
			String hqlConvenio = "select c from Convenio c where c.idProyecto = " + proyectoActual.getId();
			List<Convenio> listaConvenio = servicioGeneral.obtenerObjetos(Convenio.class, hqlConvenio);
			if (!esListaVacia(listaConvenio)) {
				if (listaConvenio.size() <= 1) {
					convenioActual = (Convenio) listaConvenio.get(0);
				}
			} else {
				convenioActual = new Convenio();
			}

			String consultaCartas = "select c from ProyectoCarta c where c.proyecto.id = " + proyectoActual.getId();
			List<ProyectoCarta> listaCartas = servicioGeneral.obtenerObjetos(ProyectoCarta.class, consultaCartas);
			if (!esListaVacia(listaCartas)) {
				actaProyectoActual = (ProyectoCarta) listaCartas.get(0);
			}

			// Archivos
			listaArchivosLeg = servicioProyecto.obtenerNombresArchivosConTipos(proyectoActual);
		}

	}

	private void cargarCompromisosInformes() {

		// Se carga informe final.
		if (!esListaVacia(proyectoActual.getListaCompromisoInformesFinales())) {
			ProyectoCompromiso proyectoCompromiso = proyectoActual.getListaCompromisoInformesFinales().get(0);
			proyectoActual.setFechaInformeFinal(proyectoCompromiso.getFechaVencimiento());
		} else {
			proyectoActual.setFechaInformeFinal(null);
		}
	}

	private void cargarCompromisosObligaciones() {
		// Se cargan los compromisos de las obligaciones
		if (!esListaVacia(proyectoActual.getListaCompromisoObligaciones())) {
			listaCompromisosObligaciones = proyectoActual.getListaCompromisoObligaciones();
		}
	}

	private void cargarCompromisosInformesParcialesYFinales() {
		// Se cargan los compromisos que no son de obligaciones
		if (!esListaVacia(proyectoActual.getListaCompromisoInformes())) {
			listaCompromisosInformesParcialesYFinales = proyectoActual.getListaCompromisoInformes();
		}
	}

	public void guardarCompromisosObligacionesSeguimiento() {
		if (listaCompromisosObligaciones.size() > 0) {
			for (Iterator iterator = listaCompromisosObligaciones.iterator(); iterator.hasNext();) {
				ProyectoCompromiso proyectoCompromiso = (ProyectoCompromiso) iterator.next();
				proyectoCompromiso.setFechaSeguimiento(new Date());
				servicioGeneral.guardarObjeto(proyectoCompromiso);
			}
		}
	}

	private void cargarSolicitudes() {

		// Se carga la información adicional de las solicitudes.
		cargarDatosAdicionalesSolicitud(proyectoActual.getListaSolicitudesEnviadas());

		// Se cargan las respuestas anteriores para saber si hubo algún cambio y
		// guardar el historico
		Iterator<Solicitud> i = proyectoActual.getSolicitudes().iterator();
		while (i.hasNext()) {
			Solicitud solicitud = i.next();
			if (solicitud.getRespuesta() != null) {
				solicitud.setRespuestaAnterior(solicitud.getRespuesta());
			} else {
				solicitud.setRespuestaAnterior("");
			}
		}
	}

	/**
	 * Se cargan todas las agendas de conocimiento activas en la base de datos.
	 */
	private void cargarAgendaConocimientoLista() {
		if (esArrayVacio(agendaConocimientoItem)) {
			List<AgendaConocimiento> listaArea = servicioGeneral.obtenerObjetos(AgendaConocimiento.class,
					"from AgendaConocimiento where padre.id.padre.id = '0' and estado='A'");
			agendaConocimientoItem = new SelectItem[listaArea.size() + 1];
			agendaConocimientoItem[0] = new SelectItem("", "seleccione una opción");
			for (int i = 0; i < listaArea.size(); i++) {
				AgendaConocimiento ac = (AgendaConocimiento) listaArea.get(i);
				agendaConocimientoItem[i + 1] = new SelectItem(ac.getId(), ac.getNombre());
			}
		}
	}

	public String consultarHistoricoEstadoProyecto() {
		sesion.setAttribute("pry_id", proyectoActual.getId());
		sesion.setAttribute("buscarHistoricoEstado", true);
		sesion.removeAttribute(ManejadorConsultaHistoricos.MANEJADOR_CONSULTA_HISTORICOS_SESSION);
		return "consultarHistoricoProyecto";
	}

	public String consultarHistoricoLegalizacionProyecto() {
		sesion.setAttribute("pry_id", proyectoActual.getId());
		sesion.setAttribute("buscarHistoricoEstadosLegalizacion", true);
		sesion.removeAttribute(ManejadorConsultaHistoricos.MANEJADOR_CONSULTA_HISTORICOS_SESSION);
		return "consultarHistoricoLegalizacionProyecto";
	}

	public String consultarHistoricoAsignacionProyecto() {
		sesion.setAttribute("pry_id", proyectoActual.getId());
		sesion.setAttribute("buscarHistoricoAsignacion", true);
		sesion.removeAttribute(ManejadorConsultaHistoricos.MANEJADOR_CONSULTA_HISTORICOS_SESSION);
		return "consultarHistoricoAsignacionProyecto";
	}

	public String consultarHistoricoEdicionProyecto() {
		sesion.setAttribute("pry_id", proyectoActual.getId());
		sesion.setAttribute("buscarHistoricoEdicion", true);
		sesion.removeAttribute(ManejadorConsultaHistoricos.MANEJADOR_CONSULTA_HISTORICOS_SESSION);
		return "consultarHistoricoEdicionProyecto";
	}

	public String consultarHistoricoEstadoInforme() {
		sesion.setAttribute("pin_id", informeSeleccionado.getId());
		sesion.setAttribute("buscarHistoricoInforme", true);
		sesion.removeAttribute(ManejadorConsultaHistoricos.MANEJADOR_CONSULTA_HISTORICOS_SESSION);
		return "consultarHistoricoInforme";
	}

	private InvestigadorProyecto obtenerInvestigadorPrincipal(List<InvestigadorProyecto> investigadoresProyecto) {
		if (!investigadoresProyecto.isEmpty()) {

			Iterator<InvestigadorProyecto> i = investigadoresProyecto.iterator();

			// Se verifica si ya es un investigador existente
			while (i.hasNext()) {
				InvestigadorProyecto investigadorProyecto = i.next();

				if (investigadorProyecto.getTipo().getId().equals(InvestigadorProyecto.PRINCIPAL)) {
					return investigadorProyecto;
				}
			}
		}
		return null;
	}

	private InvestigadorProyecto obtenerInvestigadorLista(List<InvestigadorProyecto> investigadoresProyecto,
			IdPersona idPersona) {
		if (investigadoresProyecto != null) {

			Iterator<InvestigadorProyecto> i = investigadoresProyecto.iterator();

			// Se verifica si ya es un investigador existente
			while (i.hasNext()) {
				InvestigadorProyecto investigadorProyecto = i.next();
				if (investigadorProyecto.getInvestigador().getId().getDocumento().equals(idPersona.getDocumento())
						&& investigadorProyecto.getInvestigador().getId().getTipoDocumento()
								.equals(idPersona.getTipoDocumento())) {
					return investigadorProyecto;
				}
			}
		}
		return null;
	}

	public void aplicarCambiosSolicitudInvestigadorPrincipal() {
		if (solicitudInvestigadorSeleccionada != null) {
			if (!esCadenaVacia(solicitudInvestigadorSeleccionada.getAccionInvestigadorAnterior())) {
				long mesesEjecucionProyecto = calcularMesesEntreFechas(new Date(),
						proyectoActual.getFechaTentativaInicio());
				List<InvestigadorProyecto> investigadoresProyecto = servicioProyecto
						.obtenerInvestigadoresProyecto(proyectoActual.getId());

				InvestigadorProyecto investigadorPrincipalAnterior = obtenerInvestigadorPrincipal(
						investigadoresProyecto);

				InvestigadorProyecto investigadorNuevo = obtenerInvestigadorLista(investigadoresProyecto,
						new IdPersona(solicitudInvestigadorSeleccionada.getInvestigador().getId().getDocumento(),
								solicitudInvestigadorSeleccionada.getInvestigador().getId().getTipoDocumento()));

				// Si el nuevo investigador no existe dentro del proyecto se
				// crea
				if (investigadorNuevo == null) {
					investigadorNuevo = new InvestigadorProyecto();
					investigadorNuevo.setInvestigador(solicitudInvestigadorSeleccionada.getInvestigador());
					investigadorNuevo
							.setDependencia(solicitudInvestigadorSeleccionada.getInvestigador().getDependencia());
					investigadorNuevo.setFechaVinculacion(getToday());
				} else {
					solicitudInvestigadorSeleccionada.setFuncion(solicitudInvestigadorSeleccionada.getFuncion()
							+ " - Función anterior: " + investigadorNuevo.getFuncion() + ", td: "
							+ investigadorNuevo.getDedicacionHorasSemana() + ", tt: "
							+ investigadorNuevo.getTotalHorasVinculacion() + ", valor: "
							+ investigadorNuevo.getValorPagar());
				}

				// Se agrega la información de investigador para un detalle
				// solicitud investigador borrado
				if (investigadorPrincipalAnterior != null) {
					SolicitudInvestigador solicitudInvestigador = new SolicitudInvestigador();
					solicitudInvestigador.setEstado(SolicitudInvestigador.PROCESADO);
					solicitudInvestigador.setInvestigador(investigadorPrincipalAnterior.getInvestigador());
					solicitudInvestigador.setFechaBorrado(new Date());
					solicitudInvestigador.setFechaProcesamiento(new Date());
					solicitudInvestigador.setFuncion(investigadorPrincipalAnterior.getFuncion());

					double horasSemanales = 0;
					if (investigadorPrincipalAnterior.getDedicacionHorasSemana() > 0) {
						horasSemanales = investigadorPrincipalAnterior.getDedicacionHorasSemana();
					}
					solicitudInvestigador.setDedicacionHorasSemana(horasSemanales);
					solicitudInvestigador.setSolicitud(solicitudInvestigadorSeleccionada.getSolicitud());
					TipoInvestigador tipoInvestigadorPrincipal = new TipoInvestigador();
					tipoInvestigadorPrincipal.setId(InvestigadorProyecto.PRINCIPAL);
					solicitudInvestigador.setTipo(tipoInvestigadorPrincipal);
					solicitudInvestigador.setValorDedicacionOriginal(investigadorPrincipalAnterior.getValorPagar());

					// Se setean los datos del nuevo investigador principal
					TipoInvestigador tipoInvestigador = new TipoInvestigador();
					tipoInvestigador.setId(InvestigadorProyecto.PRINCIPAL);
					investigadorNuevo.setTipo(tipoInvestigador);
					investigadorNuevo.setFuncion(solicitudInvestigadorSeleccionada.getFuncion());
					investigadorNuevo.setProyecto(proyectoActual);
					investigadorNuevo
							.setDedicacionHorasSemana(solicitudInvestigadorSeleccionada.getDedicacionHorasSemana());
					investigadorNuevo
							.setTotalHorasVinculacion(new Double(solicitudInvestigadorSeleccionada.getNumeroMeses()));
					solicitudInvestigadorSeleccionada.setValorDedicacionOriginal(investigadorNuevo.getValorPagar());
					investigadorNuevo.setValorPagar(solicitudInvestigadorSeleccionada.getValorPagar());
					servicioGeneral.guardarObjeto(investigadorNuevo);

					solicitudInvestigadorSeleccionada.setEstado(SolicitudInvestigador.PROCESADO);
					solicitudInvestigadorSeleccionada.setFechaProcesamiento(new Date());

					HistoricoCambioIntegrantes hci = new HistoricoCambioIntegrantes();
					hci.setProyecto(proyectoActual);
					hci.setFechaIngreso(getToday());
					hci.setIntegrante(investigadorNuevo.getInvestigador());
					hci.setTipoInvestigadorProyecto(investigadorNuevo.getTipo());
					hci.setMesesDedidacion(investigadorNuevo.getTotalHorasVinculacion());
					hci.setHorasDedidacion(investigadorNuevo.getDedicacionHorasSemana());
					hci.setEstadoCivil(investigadorNuevo.getInvestigador().getEstadoCivil());
					hci.setTipoVinculacion(investigadorNuevo.getInvestigador().getTipoVinculacion());
					hci.setTipoDedicacion(investigadorNuevo.getInvestigador().getTipoDedicacion());
					hci.setTipoFormacion(investigadorNuevo.getInvestigador().getTipoFormacion());
					hci.setDependencia(investigadorNuevo.getInvestigador().getDependencia().getFacultad());
					hci.setSede(investigadorNuevo.getInvestigador().getDependencia().getFacultad().getSede());
					hci.setValorDedicacion(investigadorNuevo.getValorPagar());
					servicioGeneral.guardarObjeto(hci);

					if (solicitudInvestigadorSeleccionada.getAccionInvestigadorAnterior()
							.equals(SolicitudInvestigador.AGREGAR_COINVESTIGADOR)) {
						// Si se agrega como coinvestigador
						solicitudInvestigador.setTipoSolicitud(SolicitudInvestigador.CAMBIAR_DIRECTOR_A_COINVESTIGADOR);
						Short totalVinculacion = 0;
						if (investigadorPrincipalAnterior.getTotalHorasVinculacion() != null) {
							totalVinculacion = investigadorPrincipalAnterior.getTotalHorasVinculacion().shortValue();
						}
						solicitudInvestigador.setNumeroMeses(totalVinculacion);
						TipoInvestigador tipoInvestigadorCoinvestigador = new TipoInvestigador();
						tipoInvestigadorCoinvestigador.setId(InvestigadorProyecto.CONINVESTIGADOR);
						investigadorPrincipalAnterior.setTipo(tipoInvestigadorCoinvestigador);
						investigadorPrincipalAnterior.setFuncion("Coinvestigador");
						servicioGeneral.guardarObjeto(investigadorPrincipalAnterior);

					} else {
						// Si no se agrega como coinvestigador, se elimina, pero
						// se // agrega una detalle de solicitud investigador
						// borrado para que quede el registro anterior.
						solicitudInvestigador.setTipoSolicitud(SolicitudInvestigador.ELIMINAR_DIRECTOR);
						solicitudInvestigador.setNumeroMeses((short) mesesEjecucionProyecto);
						solicitudInvestigador.setValorDedicacionOriginal(investigadorPrincipalAnterior.getValorPagar());
						InvestigadorInterno ii = servicioPersona
								.obtenerInvestigadorInterno(investigadorPrincipalAnterior.getInvestigador().getId());
						long valorDevengado = calcularValorFuncionario(horasSemanales, (int) mesesEjecucionProyecto,
								ii.getValorHoraValidado());
						solicitudInvestigador.setValorPagar(valorDevengado);

						List<HistoricoCambioIntegrantes> historicoIntegrantes = servicioGeneral.obtenerObjetos(
								HistoricoCambioIntegrantes.class,
								"from HistoricoCambioIntegrantes h where h.proyecto.id = '" + proyectoActual.getId()
										+ "' and h.integrante.id.documento='"
										+ investigadorPrincipalAnterior.getInvestigador().getId().getDocumento()
										+ "' and h.fechaRetiro is null order by h.id asc");

						if (historicoIntegrantes != null && historicoIntegrantes.size() > 0) {
							hci = historicoIntegrantes.get(0);
							hci.setFechaRetiro(getToday());
							servicioGeneral.guardarObjeto(hci);
						} else {
							hci = new HistoricoCambioIntegrantes();
							hci.setProyecto(proyectoActual);
							hci.setIntegrante(investigadorPrincipalAnterior.getInvestigador());
							hci.setTipoInvestigadorProyecto(investigadorPrincipalAnterior.getTipo());
							hci.setMesesDedidacion((double) mesesEjecucionProyecto);
							hci.setHorasDedidacion(investigadorPrincipalAnterior.getDedicacionHorasSemana());
							hci.setEstadoCivil(investigadorPrincipalAnterior.getInvestigador().getEstadoCivil());
							hci.setTipoVinculacion(
									investigadorPrincipalAnterior.getInvestigador().getTipoVinculacion());
							hci.setTipoDedicacion(investigadorPrincipalAnterior.getInvestigador().getTipoDedicacion());
							hci.setTipoFormacion(investigadorPrincipalAnterior.getInvestigador().getTipoFormacion());
							hci.setDependencia(
									investigadorPrincipalAnterior.getInvestigador().getDependencia().getFacultad());
							hci.setSede(investigadorPrincipalAnterior.getInvestigador().getDependencia().getFacultad()
									.getSede());
							hci.setValorDedicacion(solicitudInvestigador.getValorPagar());
							hci.setFechaRetiro(getToday());
							servicioGeneral.guardarObjeto(hci);
						}
						servicioGeneral.eliminarObjeto(investigadorPrincipalAnterior);

					}
					servicioGeneral.guardarObjeto(solicitudInvestigadorSeleccionada);
					servicioGeneral.guardarObjeto(solicitudInvestigador);
					solicitudInvestigadorSeleccionada.getSolicitud()
							.setInvestigadorPrincipalAnterior(solicitudInvestigador);
					mensajeInfo("Se han aplicado los cambios al proyecto investigador.");

					investigadorPrincipal = servicioProyecto
							.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
				} else {
					System.out.println("CAMBIO INVESTIGADOR: Investigador principal nulo");
					mensajeError("Se presentó un problema durante el cambio de investigador principal del proyecto.");
				}
			} else {
				mensajeError("Debe seleccionar la acción a realizar con el director anterior.");
			}
		}
	}

	public void aplicarCambiosSolicitudCertificado() {
		try {

			servicioGeneral.guardarObjeto(solicitudSeleccionada);
			mensajeInfo("Se ha guardado la información del radicado.");

		} catch (Exception e) {
			e.printStackTrace();
			mensajeError("Se presentó un problema al momento de guardar la información del radicado.");
		}

	}

	public void aplicarCambiosReactivacion() {
		if (solicitudSeleccionada.getFechaReactivacion() != null && fechaFinalProyecto != null) {

			Date fechaCalculaProrrogaTemporal = solicitudSeleccionada.getFechaReactivacion();

			int diferenciaMeses = 0;
			if (fechaCalculaProrrogaTemporal.getYear() > fechaFinalProyecto.getYear()) {
				diferenciaMeses = (fechaCalculaProrrogaTemporal.getYear() - fechaFinalProyecto.getYear()) * 12;
			}
			diferenciaMeses += fechaCalculaProrrogaTemporal.getMonth() - fechaFinalProyecto.getMonth();
			int diaCalculo = fechaCalculaProrrogaTemporal.getDate();

			if (fechaCalculaProrrogaTemporal.getDate() < fechaFinalProyecto.getDate()) {
				diferenciaMeses--;
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, fechaCalculaProrrogaTemporal.getYear());
				int mes;
				if (fechaCalculaProrrogaTemporal.getMonth() > 0) {
					mes = fechaCalculaProrrogaTemporal.getMonth() - 1;
				} else {
					mes = 12;
				}
				calendar.set(Calendar.MONTH, mes);
				diaCalculo += calendar.getActualMaximum(Calendar.DATE);
			}

			// Se creo una prorroga de tipo suspención
			ProyectoProrroga prorrogaSuspencion = new ProyectoProrroga();
			prorrogaSuspencion.setEsPeriodoSuspension("S");
			prorrogaSuspencion.setDiasVista("0");
			prorrogaSuspencion.setMesesVista("0");
			prorrogaSuspencion.setFecha(new Date());
			Persona persona = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
			prorrogaSuspencion.setResponsable(persona);
			prorrogaSuspencion.setDuracion(new Long(diferenciaMeses));
			int diferenciaDias = diaCalculo - fechaFinalProyecto.getDate();
			prorrogaSuspencion.setDias(new Long(diferenciaDias));
			if (prorrogaSuspencion.getDuracion() > 0L || prorrogaSuspencion.getDias() > 0L) {
				proyectoActual.adicionarProyectoProrroga(prorrogaSuspencion);

				// Se crea actiliza la solicitud
				solicitudSeleccionada.setEstadoProcesamientoSolicitud("P");
				solicitudSeleccionada.setFechaProcesamiento(new Date());
				servicioGeneral.guardarObjeto(solicitudSeleccionada);

				guardarCambiosProyecto();

				cargarFechasProyecto(proyectoActual);

				// Se guarda el estado activo
				EstadoProyecto estadoProyectoTemporal = new EstadoProyecto();
				estadoProyectoTemporal.setId(EstadoProyecto.ACTIVO);
				estadoProyectoTemporal.setNombre("Activo");

				HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
				Date fechaHoy = new Date();
				hepry.setEstadoProyecto(estadoProyectoTemporal);
				hepry.setFecha(fechaHoy);
				hepry.setJustificacion("Reactivación proyecto.");
				hepry.setResponsable(cargarPersonaActual());
				proyectoActual.adicionarHistorico(hepry);
				proyectoActual.setEstadoProyecto(estadoProyectoTemporal);
				servicioGeneral.guardarObjeto(proyectoActual);
			}

		} else if (fechaFinalProyecto == null) {
			solicitudSeleccionada.setMensajeReactivacion(
					"El proyecto no tiene fecha de inicio y/o de terminación, la reactivación NO puede ser aplicada al proyecto.");
		}
	}

	public void aplicarCambiosSolicitudIntegrantes() {

		// Se cargan los investigadores actuales del proyecto
		List<InvestigadorProyecto> investigadoresProyecto = servicioProyecto
				.obtenerInvestigadoresProyecto(proyectoActual.getId());

		if (solicitudSeleccionadaIntegrantes != null) {
			Iterator<SolicitudInvestigador> i = solicitudSeleccionadaIntegrantes
					.getLitaSolicitudesInvestigadorPendientes().iterator();
			while (i.hasNext()) {
				SolicitudInvestigador solicitudInvestigador = i.next();
				// Si ese cambio se debe aplicar.
				if (solicitudInvestigador.isAplicar()) {
					if (solicitudInvestigador.getTipoSolicitud().equals(SolicitudInvestigador.AGREGAR)) {

						boolean investigadorYaExisteProyecto = false;
						// Se valida que el investigador no este actualmente en
						// el proyecto
						Iterator<InvestigadorProyecto> j = investigadoresProyecto.iterator();
						while (j.hasNext()) {
							InvestigadorProyecto investigadorProyecto = j.next();
							if (investigadorProyecto.getInvestigador().getId().getDocumento()
									.equals(solicitudInvestigador.getInvestigador().getId().getDocumento())
									&& investigadorProyecto.getInvestigador().getId().getTipoDocumento().equals(
											solicitudInvestigador.getInvestigador().getId().getTipoDocumento())) {
								investigadorYaExisteProyecto = true;
							}
						}

						if (!investigadorYaExisteProyecto) {

							// Se crea nuevo investigador asociado al proyecto
							InvestigadorProyecto investigadorNuevo = new InvestigadorProyecto();
							investigadorNuevo.setInvestigador(solicitudInvestigador.getInvestigador());
							investigadorNuevo.setProyecto(proyectoActual);
							investigadorNuevo.setFuncion(solicitudInvestigador.getFuncion());
							investigadorNuevo.setFechaVinculacion(getToday());
							investigadorNuevo.setTipo(solicitudInvestigador.getTipo());
							investigadorNuevo
									.setDedicacionHorasSemana(solicitudInvestigador.getDedicacionHorasSemana());
							investigadorNuevo
									.setTotalHorasVinculacion(new Double(solicitudInvestigador.getNumeroMeses()));
							servicioGeneral.guardarObjeto(investigadorNuevo);

							// Se guardan datos de procesamiento en la solicitud
							// de cambio de integrante
							solicitudInvestigador.setEstado(SolicitudInvestigador.PROCESADO);
							solicitudInvestigador.setFechaProcesamiento(new Date());
							servicioGeneral.guardarObjeto(solicitudInvestigador);

							// CREACIÓN DE HISTÓRICO
							hci = new HistoricoCambioIntegrantes();
							hci.setProyecto(proyectoActual);
							hci.setFechaIngreso(getToday());
							hci.setIntegrante(investigadorNuevo.getInvestigador());
							hci.setTipoInvestigadorProyecto(investigadorNuevo.getTipo());
							hci.setMesesDedidacion(investigadorNuevo.getTotalHorasVinculacion());
							hci.setHorasDedidacion(investigadorNuevo.getDedicacionHorasSemana());
							hci.setValorDedicacion(investigadorNuevo.getValorPagar());
							if (investigadorNuevo.getTipo().getTipo() != null) {
								if (investigadorNuevo.getTipo().getTipo().equals("F")) {
									hci.setEstadoCivil(investigadorNuevo.getInvestigador().getEstadoCivil());
									hci.setTipoVinculacion(investigadorNuevo.getInvestigador().getTipoVinculacion());
									hci.setTipoDedicacion(investigadorNuevo.getInvestigador().getTipoDedicacion());
									hci.setTipoFormacion(investigadorNuevo.getInvestigador().getTipoFormacion());
									hci.setDependencia(
											investigadorNuevo.getInvestigador().getDependencia().getFacultad());
									hci.setSede(investigadorNuevo.getInvestigador().getDependencia().getFacultad()
											.getSede());
								} else if (investigadorNuevo.getTipo().getTipo().equals("A")) {
									hci.setEstadoCivil(investigadorNuevo.getInvestigador().getEstadoCivil());
									Estudiante e = servicioPersona
											.obtenerEstudiante(investigadorNuevo.getInvestigador().getId());
									hci.setPlanEstudios(e.getPlan());
									hci.setSemestreActual(e.getSemestreActual());
									hci.setDependencia(e.getDependencia());
									hci.setSede(e.getDependencia().getSede());
								}
							}
							servicioGeneral.guardarObjeto(hci);
						}

					}
					if (solicitudInvestigador.getTipoSolicitud().equals(SolicitudInvestigador.RETIRAR)) {

						InvestigadorProyecto investigadorProyectoEliminar = null;
						// Se busca el investigador en el listado de
						// investiagdores actuales del proyecto
						Iterator<InvestigadorProyecto> j = investigadoresProyecto.iterator();
						while (j.hasNext()) {
							InvestigadorProyecto investigadorProyecto = j.next();
							if (investigadorProyecto.getInvestigador().getId().getDocumento()
									.equals(solicitudInvestigador.getInvestigador().getId().getDocumento())
									&& investigadorProyecto.getInvestigador().getId().getTipoDocumento().equals(
											solicitudInvestigador.getInvestigador().getId().getTipoDocumento())) {
								investigadorProyectoEliminar = investigadorProyecto;
							}
						}

						if (investigadorProyectoEliminar != null) {

							// Se agrega la información de investigador para un
							// detalle solicitud investigador borrado
							SolicitudInvestigador solicitudInvestigadorCambioIntegrante = new SolicitudInvestigador();
							solicitudInvestigadorCambioIntegrante.setEstado(SolicitudInvestigador.BORRADO);
							solicitudInvestigadorCambioIntegrante
									.setInvestigador(investigadorProyectoEliminar.getInvestigador());
							solicitudInvestigadorCambioIntegrante.setFechaBorrado(new Date());
							solicitudInvestigadorCambioIntegrante.setFechaProcesamiento(new Date());
							solicitudInvestigadorCambioIntegrante.setFuncion(investigadorProyectoEliminar.getFuncion());
							Short totalVinculacion = 0;
							if (investigadorProyectoEliminar.getTotalHorasVinculacion() != null) {
								totalVinculacion = investigadorProyectoEliminar.getTotalHorasVinculacion().shortValue();
							}
							solicitudInvestigadorCambioIntegrante.setNumeroMeses(totalVinculacion);
							double horasSemanales = 0;
							if (investigadorProyectoEliminar.getDedicacionHorasSemana() > 0) {
								horasSemanales = investigadorProyectoEliminar.getDedicacionHorasSemana();
							}
							solicitudInvestigadorCambioIntegrante.setDedicacionHorasSemana(horasSemanales);
							solicitudInvestigadorCambioIntegrante.setSolicitud(solicitudInvestigador.getSolicitud());
							solicitudInvestigadorCambioIntegrante.setTipo(investigadorProyectoEliminar.getTipo());
							solicitudInvestigadorCambioIntegrante
									.setTipoSolicitud(SolicitudInvestigador.ELIMINAR_INTEGRANTE);
							servicioGeneral.guardarObjeto(solicitudInvestigadorCambioIntegrante);

							// Si no se agrega como coinvestigador, se elimina,
							// pero se agrega una detalle de solicitud
							// investigador
							// borrado para que quede el registro anterior.
							servicioGeneral.eliminarObjeto(investigadorProyectoEliminar);

							// Se actualizan los datos en el detalle de
							// solicitud de cambio de integrante.
							solicitudInvestigador.setEstado(SolicitudInvestigador.PROCESADO);
							solicitudInvestigador.setFechaProcesamiento(new Date());
							servicioGeneral.guardarObjeto(solicitudInvestigador);

							// CREACION HISTÓRICO
							List objetos = servicioGeneral
									.obtenerObjetos("select h from HistoricoCambioIntegrantes h where h.proyecto.id = '"
											+ proyectoActual.getId() + "'");
							if (objetos != null && objetos.size() > 0) {
								for (Object object : objetos) {
									hci = (HistoricoCambioIntegrantes) object;
									if (hci.getIntegrante().equals(investigadorProyectoEliminar.getInvestigador())
											&& hci.getFechaRetiro() == null) {
										hci.setFechaRetiro(new Date());
										servicioGeneral.guardarObjeto(hci);
										break;
									}
								}
							}

						}

					}
				}
			}
			cargarDatosAdicionalesSolicitud(proyectoActual.getListaSolicitudesEnviadas());
		}
	}

	public void aplicarCambiosSolicitudProrrogaIntegrantes() {

		if (solicitudSeleccionadaIntegrantesProrroga != null) {
			Iterator<SolicitudProrrogaInvestigador> i = solicitudSeleccionadaIntegrantesProrroga
					.getListaSolicitudesProrrogaInvestigadorPendientes().iterator();
			while (i.hasNext()) {
				SolicitudProrrogaInvestigador solicitudInvestigador = i.next();
				if (solicitudInvestigador.isAplicar()) {
					// Se guardan datos de procesamiento en la solicitud
					// de cambio de integrante
					solicitudInvestigador.setEstado(SolicitudProrrogaInvestigador.PROCESADO);
					solicitudInvestigador.setFechaProcesamiento(new Date());
					servicioGeneral.guardarObjeto(solicitudInvestigador);
					mensajeInfo("Se han aplicado los cambios al proyecto investigador.");
				}
			}
		}
	}

	// Carga inicial de informes
	private void cargarInformes() {
		listaInformes = servicioProyecto.obtenerProyectoInforme(proyectoActual.getId());
		if (!esListaVacia(listaInformes)) {
			for (int i = 0; i < listaInformes.size(); i++) {
				ProyectoInforme pinAux = (ProyectoInforme) listaInformes.get(i);
				pinAux.setProyecto(proyectoActual);
				if (pinAux.getProyectoCompromiso() != null) {
					pinAux.setMostrarCompromisos(true);
				} else {
					pinAux.setMostrarCompromisos(false);
				}
			}
		}
	}

	// Carga inicial de proyecto
	private void cargarProyectos(Long idProyecto) {
		sesion.setAttribute("idProyectoSeguimientoCoordinador", idProyecto);
		cargarProyectos();
	}

	// Carga inicial de proyecto
	private void cargarProyectos() {
		Long idProyecto = (Long) sesion.getAttribute("idProyectoSeguimientoCoordinador");
		if (idProyecto != null) {
			setMontoAdicion(0L);
			fechaFinalProyecto = null;
			dependenciaRes = "";
			tipoDuracion = TIPO_DURACION_DEFECTO;
			esCoordinador = false;
			esCoordinadorEvaluacion = false;
			esCoordinadorRequisitos = false;
			proyectoActual = servicioProyecto.obtenerProyecto(idProyecto, ProyectoDAOHibernate.LIMPIO_SEGUIMIENTO);
			estadoProyecto = proyectoActual.getEstadoProyecto().getId();
			asignacionActual = proyectoActual.getPermitirModificacion();
			noAplicaCodigoQuipu = proyectoActual.getCodigoQuipu() != null
					&& proyectoActual.getCodigoQuipu().equals("No Aplica");
			tieneVariosCodigoQuipu = proyectoActual.getSegundoCodigoQuipu() != null;
			segundoCodigoQuipuAux = proyectoActual.getSegundoCodigoQuipu();
			cargarCartasProyecto(proyectoActual.getId());
			cargarDatosAdicionalesProyecto();
			listaInformes = new ArrayList<ProyectoInforme>();
			cargarInformes();
			mostrarPanelCorreo = false;

			// se utiliza en la legalizacion de proyectos
			setEsCoejecutorCooperante(false);
			if (!esCadenaVacia(proyectoActual.getRolUniversidad())
					&& proyectoActual.getRolUniversidad().equals("ROL_COOPER")) {
				setEsCoejecutorCooperante(true);
			}
			String permitir = proyectoActual.getPermitirModificacion();
			if (esCadenaVacia(permitir)) {
				proyectoActual.setPermitirModificacion("N");
			}
			listaArchivosProyecto = servicioProyecto.obtenerNombresArchivos(proyectoActual);
			if (proyectoActual.getDuracionTipo() != null && proyectoActual.getDuracionTipo().length() > 0) {
				if (proyectoActual.getDuracionTipo().equals(TipoDuracion.ANIOS)) {
					tipoDuracion = "años";
				} else if (proyectoActual.getDuracionTipo().equals(TipoDuracion.SEMANAS)) {
					tipoDuracion = "semanas";
				} else if (proyectoActual.getDuracionTipo().equals(TipoDuracion.HORA)) {
					tipoDuracion = "horas";
				} else {
					tipoDuracion = TIPO_DURACION_DEFECTO;
				}
			}

			tipoActividadNombre = "";
			Modalidad mod = proyectoActual.getModalidad();
			if (mod instanceof Convocatoria) {
				if (mod.getId().equals(2L)) {
					String tipoActividad = proyectoActual.getTipoActividad();
					if (tipoActividad != null) {
						List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class,
								"from DominioDetalle d where d.identificador.tipo='" + tipoActividad + "'");
						if (!esListaVacia(lista)) {
							DominioDetalle dd = (DominioDetalle) lista.get(0);
							tipoActividadNombre = dd.getDescripcion();
						}
					}
				}
			}

			cargarObservacionesSeguimiento(proyectoActual.getId());
			obtenerCoordinadorSeguimiento(proyectoActual.getId());

			if (coordinadorSeguimientoProyecto != null) {
				personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
				if (coordinadorSeguimientoProyecto.getId().getDocumento().equals(personaActual.getId().getDocumento())
						&& coordinadorSeguimientoProyecto.getId().getTipoDocumento()
								.equals(personaActual.getId().getTipoDocumento()))
					esCoordinador = true;
			}
			obtenerCoordinadorEvaluacion(proyectoActual.getId());
			if (coordinadorEvaluacion != null) {
				personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
				if (coordinadorEvaluacion.getId().getDocumento().equals(personaActual.getId().getDocumento())
						&& coordinadorEvaluacion.getId().getTipoDocumento()
								.equals(personaActual.getId().getTipoDocumento()))

					esCoordinadorEvaluacion = true;
			}
			cargarEvaluacion();

			// Se carga información de coordinador de requisitos.
			obtenerCoordinadorRequisitos(proyectoActual.getId());
			if (coordinadorRequisitos != null) {
				personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
				esCoordinadorRequisitos = coordinadorRequisitos.getId().getDocumento()
						.equals(personaActual.getId().getDocumento())
						&& coordinadorRequisitos.getId().getTipoDocumento()
								.equals(personaActual.getId().getTipoDocumento());
			}

			edicionFormalizacionHabilitada = (proyectoActual.getFechaLegalizacion() == null)
					&& ((esCoordinadorRequisitos && proyectoActual.isHabilitadoParaLegalizacionExterna())
							|| (esCoordinador && proyectoActual.isHabilitadoParaFormalizacionInterna()));

			listaProyectosCoordinador.addAll(servicioProyecto.obtenerProyectosxId(personaActual.getId().getDocumento(),
					personaActual.getId().getTipoDocumento(), proyectoActual.getId().toString()));

			listaArchivos = new ArrayList<Archivo>();
			sesion.removeAttribute("idProyectoSeguimientoCoordinador");

			if (esCoordinadorEvaluacion
					&& proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO)) {

				// Programa
				List listaPrograma = servicioGeneral.obtenerObjetos(
						"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
								+ DOMINIO_PROGRAMA + "' order by dd.descripcion");
				programaItems = new SelectItem[listaPrograma.size()];
				for (int i = 0; i < listaPrograma.size(); i++) {
					DominioDetalle dd = (DominioDetalle) listaPrograma.get(i);
					programaItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
				}

			}
			detallesCambiosRubro = cargarDetallesCambioRubrosAprobados(proyectoActual.getListaSolicitudesEnviadas());
			listaSolicitudAdicionPresupuesto = cargarDetallesAdicionesPresupuestoAprobadas(
					proyectoActual.getListaSolicitudesEnviadas());
			informacionFinaciera = cargarFinanciacionActualProyecto(getDetallesCambiosRubro(), proyectoActual,
					listaSolicitudAdicionPresupuesto);

			List<String[]> consulta = servicioGeneral.consultaValorEjecutado(proyectoActual.getId().toString(),
					proyectoActual.getListaInvestigadorPrincipal().get(0).getInvestigador().getId().getDocumento());
			for (Object f : informacionFinaciera) {
				@SuppressWarnings("unchecked")
				ArrayList<Object[]> g = (ArrayList<Object[]>) ((Object[]) f)[1];
				for (Object[] lista : g) {
					for (String[] record : consulta) {
						if (((Gasto) lista[0]).getTipoRubro().getCodigoQuipu() != null
								&& ((Gasto) lista[0]).getTipoRubro().getCodigoQuipu().contains(record[0] + ";")) {
							if (((Gasto) lista[0]).getValorEjecutado() == null) {

								((Gasto) lista[0]).setValorEjecutado(Float.parseFloat(record[1]));
							} else {
								((Gasto) lista[0]).setValorEjecutado(
										((Gasto) lista[0]).getValorEjecutado() + Long.parseLong(record[1]));
							}
						}
					}
				}
			}

			Long adicionesInternas = 0L;
			for (DetalleAdicionPresupuesto detalleAdicion : listaSolicitudAdicionPresupuesto) {
				setMontoAdicion(getMontoAdicion() + detalleAdicion.getValorAdicionRubro());
				if (detalleAdicion.getSolicitudAdicion().getFinanciacion().getFuente().esInterna()) {
					adicionesInternas += detalleAdicion.getValorAdicionRubro();
				}
			}
			montoAprobadoProyecto = montoAprobadoProyecto + adicionesInternas;
			cargarListas();
			cargarHabilitarEdicionProyecto();
		}
	}

	private void cargarHabilitarEdicionProyecto() {
		habilitarEdicionProyecto = esCoordinador && !proyectoActual.isEsRegistroBiodiversidad()
				&& ((proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.ACTIVO)
						&& proyectoActual.isTieneSolicitudCambioContenidoAprobada()) ||
				// Convocatoria interna
						(!proyectoActual.getModalidad().isEsConvocatoriaLegalizacion() && (
						// es estado elegible
						proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.ELEGIBLE) ||
						// O estado aprobado
								proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.APROBADO)))
						|| (proyectoActual.getModalidad().isEsConvocatoriaLegalizacion()
								&& proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO))
						|| (proyectoActual.getModalidad().isEsConvocatoriaLegalizacion()
								&& proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.APROBADO)));
	}

	// TODO: Ojo Revisar esto
	// actualizar sede a partir de unidad ejecutora
	public void actualizarSede() {
		if (this.proyectoActual.getUnidadEjecutora() != null) {
			String idFac = this.proyectoActual.getUnidadEjecutora();
			List<Dependencia> facultades = servicioGeneral.obtenerObjetos(Dependencia.class,
					"select d from Dependencia d  where d.id ='" + idFac + "'");

			if (!esListaVacia(facultades)) {
				Dependencia fac = (Dependencia) facultades.get(0);

				Long idSed = fac.getSede().getId();
				this.proyectoActual.setSedeEjecutora(idSed);
			}
		} else {
			this.proyectoActual.setSedeEjecutora(1L);
		}
	}

	// Lista Estados Convocatoria Externa
	private void cargarListaEstadosConvExterna() {
		// ******* pry conv externa *********
		estadoItems = new SelectItem[4];
		estadoItems[0] = new SelectItem(EstadoProyecto.PROPUESTO, EstadoProyecto.PROPUESTO_NOMBRE);
		estadoItems[1] = new SelectItem(EstadoProyecto.ELEGIBLE, EstadoProyecto.ELEGIBLE_NOMBRE);
		estadoItems[2] = new SelectItem(EstadoProyecto.NO_APROBADO, EstadoProyecto.NO_APROBADO_NOMBRE);

		if (tieneAvalRegaliasAprobado) {
			estadoItems[3] = new SelectItem(EstadoProyecto.APROBADO_OCAD, EstadoProyecto.APROBADO_OCAD_NOMBRE);
		} else {
			estadoItems[3] = new SelectItem(EstadoProyecto.APROBADO, EstadoProyecto.APROBADO_NOMBRE);
		}

	}

	// Cargar listas en la creación del constructor
	private void cargarListas() {

		if (permitirEditarItem == null) {
			permitirEditarItem = new SelectItem[2];
			permitirEditarItem[0] = new SelectItem("S", "SI");
			permitirEditarItem[1] = new SelectItem("N", "NO");
		}
		if (incluirJustificacionItem == null) {
			incluirJustificacionItem = new SelectItem[2];
			incluirJustificacionItem[0] = new SelectItem("S", "SI");
			incluirJustificacionItem[1] = new SelectItem("N", "NO");
		}
		if (tiposCarta == null) {
			List<DominioDetalle> tiposCartaList = servicioGeneral.obtenerObjetos(DominioDetalle.class,
					"select dd from Dominio d, DominioDetalle dd where (d.id =" + " dd.identificador.id and d.id ='"
							+ TIPO_CARTA_DOMINIO
							+ "' and dd.estado='A') and dd.identificador.tipo <> '17' order by dd.identificador.tipo");
			if (!esListaVacia(tiposCartaList)) {
				tiposCarta = new SelectItem[tiposCartaList.size()];

				if (!esListaVacia(tiposCartaList)) {
					for (int i = 0; i < tiposCartaList.size(); i++) {
						DominioDetalle dd = (DominioDetalle) tiposCartaList.get(i);
						tiposCarta[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
					}
					tipoCartSeleccionado = ((DominioDetalle) tiposCartaList.get(0)).getIdentificador().getTipo();
				}
			}

		}

		cargarCartasEspecifica();
		fechaSesion = new Date();

		// ******* pry conv externa *********

		if (estadoItems == null) {
			estadoItems = new SelectItem[4];
			estadoItems[0] = new SelectItem(EstadoProyecto.PROPUESTO, EstadoProyecto.PROPUESTO_NOMBRE);
			estadoItems[1] = new SelectItem(EstadoProyecto.ELEGIBLE, EstadoProyecto.ELEGIBLE_NOMBRE);
			estadoItems[2] = new SelectItem(EstadoProyecto.NO_APROBADO, EstadoProyecto.NO_APROBADO_NOMBRE);

			if (tieneAvalRegaliasAprobado) {
				estadoItems[3] = new SelectItem(EstadoProyecto.APROBADO_OCAD, EstadoProyecto.APROBADO_OCAD_NOMBRE);
			} else {
				estadoItems[3] = new SelectItem(EstadoProyecto.APROBADO, EstadoProyecto.APROBADO_NOMBRE);
			}

		}
		if (tipologiaItems == null) {
			tipologiaItems = new SelectItem[2];
			tipologiaItems[0] = new SelectItem("ET", "Ejecución técnica");
			tipologiaItems[1] = new SelectItem("ETF", "Ejecución técnica y financiera");
		}

		if (compromisoEjecItems == null) {
			compromisoEjecItems = new SelectItem[4];
			compromisoEjecItems[0] = new SelectItem("Convenio", "Convenio");
			compromisoEjecItems[1] = new SelectItem("Contrato", "Contrato");
			compromisoEjecItems[2] = new SelectItem("Acta de compromiso", "Acta de compromiso");
			compromisoEjecItems[3] = new SelectItem("Otro", "Otro");
		}

		if (tipofechaItems == null) {
			tipofechaItems = new SelectItem[4];
			tipofechaItems[0] = new SelectItem("1", "Desembolso de recursos");
			tipofechaItems[1] = new SelectItem("2", "Acta de inicio");
			tipofechaItems[2] = new SelectItem("3", "Legalización o suscripción del contrato");
			tipofechaItems[3] = new SelectItem("4", "Otro");
		}
		if (desembolsoItems == null) {
			desembolsoItems = new SelectItem[2];
			desembolsoItems[0] = new SelectItem("Parciales", "Parciales");
			desembolsoItems[1] = new SelectItem("Unico", "Único");
		}
		if (informesItems == null) {
			informesItems = new SelectItem[2];
			informesItems[0] = new SelectItem("Parciales", "Parciales");
			informesItems[1] = new SelectItem("Final", "Final");
		}
		if (trasladoItems == null) {
			trasladoItems = new SelectItem[6];
			trasladoItems[0] = new SelectItem("0", "No se autorizó");
			trasladoItems[1] = new SelectItem("1", "10%");
			trasladoItems[2] = new SelectItem("2", "20%");
			trasladoItems[3] = new SelectItem("3", "30%");
			trasladoItems[4] = new SelectItem("4", "40%");
			trasladoItems[5] = new SelectItem("5", "Otro");
		}
		if (vinculacionItems == null) {
			vinculacionItems = new SelectItem[2];
			vinculacionItems[0] = new SelectItem("SI", "SI");
			vinculacionItems[1] = new SelectItem("NO", "NO");
		}

		if (tipologiaSecItems == null) {
			tipologiaSecItems = new SelectItem[4];
			tipologiaSecItems[0] = new SelectItem("1", "Universidad Nacional Ejecutora - única entidad");
			/*
			 * tipologiaSecItems[1] = new SelectItem("2",
			 * "Universidad Nacional Ejecutora - única entidad designada por el OCAD");
			 */
			tipologiaSecItems[1] = new SelectItem("3",
					"Universidad Nacional Ejecutora - aportes adicionales de entidades participantes");
			tipologiaSecItems[2] = new SelectItem("4",
					"Universidad Nacional Ejecutora – administradora de recursos con entidades participantes ");
			tipologiaSecItems[3] = new SelectItem("5",
					"Universidad Nacional como entidad co-ejecutora o ejecutora técnica (en Regalías)");
			if (!edicionFormalizacionHabilitada) {
				tipologiaSecItems = new SelectItem[5];
				tipologiaSecItems[0] = new SelectItem("1", "Universidad Nacional Ejecutora - única entidad");
				tipologiaSecItems[1] = new SelectItem("2",
						"Universidad Nacional Ejecutora - única entidad designada por el OCAD");
				tipologiaSecItems[2] = new SelectItem("3",
						"Universidad Nacional Ejecutora - aportes adicionales de entidades participantes");
				tipologiaSecItems[3] = new SelectItem("4",
						"Universidad Nacional Ejecutora – administradora de recursos con entidades participantes ");
				tipologiaSecItems[4] = new SelectItem("5",
						"Universidad Nacional como entidad co-ejecutora o ejecutora técnica (en Regalías)");
			}
		}

		// Periodos de los informes
		if (periodoInformesItems == null) {

			periodoInformesItems = new SelectItem[48];
			for (int i = 0; i < 48; i++) {
				periodoInformesItems[i] = new SelectItem(i + 1, String.valueOf(i + 1));
			}
		}
		// Tipos de entidad

		if (tipoEntidadItems == null) {

			List lista = servicioGeneral.obtenerObjetos(
					"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
							+ DOMINIO_TIPO_ENTIDAD + "' and dd.estado ='A' order by dd.descripcion");
			tipoEntidadItems = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				DominioDetalle dd = (DominioDetalle) lista.get(i);
				tipoEntidadItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());

			}
		}

		if (motivoEstadoItems == null) {
			// estados no aprobación
			motivoEstadoItems = new SelectItem[6];
			motivoEstadoItems[0] = new SelectItem("", "Seleccione una opción");
			motivoEstadoItems[1] = new SelectItem("NCR", "No cumplió requisitos");
			motivoEstadoItems[2] = new SelectItem("NED", "No entregó documentos");
			motivoEstadoItems[3] = new SelectItem("NOCMA", "No obtuvo calificación mínima de aprobación");
			motivoEstadoItems[4] = new SelectItem("EE", "Entrega extemporánea");
			motivoEstadoItems[5] = new SelectItem("O", "Otra");
		}
		if (clausulasItems == null) {
			// clausulas contrato
			clausulasItems = new SelectItem[3];
			clausulasItems[0] = new SelectItem("CP", "Clausula penal");
			clausulasItems[1] = new SelectItem("PI", "Propiedad intelectual");
			clausulasItems[2] = new SelectItem("O", "Otra");
		}

		cargarTipoIngresos();

		cargarTiposActosAdministrativos();

		cargarTiposOrdenadores();

		if (modContratacionItems == null) {
			List lista = servicioGeneral.obtenerObjetos(
					"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
							+ DOMINIO_MOD_CONTRATACION + "' order by dd.descripcion");
			modContratacionItems = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				DominioDetalle dd = (DominioDetalle) lista.get(i);
				modContratacionItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());

			}
		}

		if (anioItems == null) {
			anioItems = new SelectItem[5];
			anioItems[0] = new SelectItem("1", "1");
			anioItems[1] = new SelectItem("2", "2");
			anioItems[2] = new SelectItem("3", "3");
			anioItems[3] = new SelectItem("4", "4");
			anioItems[4] = new SelectItem("5", "5");
		}

		if (entidadItems == null || entidadItemsOcad == null) {
			// entidades contratantes
			String hql = "select ff from FuenteFinanciacion ff where ff.internaExterna like "
					+ "'E' and ff.descripcion not like '%CODIGO%' and ff.observaciones = 'ENT_REG_PRY' "
					+ "and ff.quipu = 'S' order by ff.descripcion)";
			List<FuenteFinanciacion> lista = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class, hql);

			entidadItems = new SelectItem[lista.size()];
			entidadItemsOcad = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				FuenteFinanciacion d = (FuenteFinanciacion) lista.get(i);
				String nombre = d.getDescripcion().toUpperCase();
				if (nombre != null && nombre.length() > 80) {
					nombre = nombre.substring(0, 80) + "...";
				}
				entidadItems[i] = new SelectItem(d.getId(), nombre);
				entidadItemsOcad[i] = new SelectItem(d.getId(), nombre);
			}
		}
		if (facultadInstItem == null) {
			// facultades / institutos
			List<Dependencia> facultadInstituto = servicioGeneral.obtenerObjetos(Dependencia.class,
					"select d from Dependencia d  where d.estado ='A' and d.esFacultad = 'Y' order by d.nombre");
			facultadInstItem = new SelectItem[facultadInstituto.size()];
			for (int i = 0; i < facultadInstituto.size(); i++) {
				Dependencia dd = (Dependencia) facultadInstituto.get(i);
				facultadInstItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			}
			unidadEjecutoraInicial = ((Dependencia) facultadInstituto.get(0)).getId().toString();
		}
		unidadEjecutora = unidadEjecutoraInicial;
		if (sedesItems == null) {
			// Sedes
			List<Dependencia> sedes = servicioGeneral.obtenerObjetos(Dependencia.class,
					"select d from Dependencia d where d.esSede = 'Y' or d.id = '" + Dependencia.NIVEL_NACIONAL
							+ "' order by d.nombre");
			sedesItems = new SelectItem[sedes.size()];
			for (int i = 0; i < sedes.size(); i++) {
				Dependencia dd = (Dependencia) sedes.get(i);
				sedesItems[i] = new SelectItem(dd.getId(), dd.getNombre());
			}
		}

		// dependencias

		if (esArrayVacio(dependenciaItem)) {
			dependenciasUN = servicioGeneral.obtenerObjetos(Dependencia.class,
					"select e from Dependencia e where e.estado='A' and e.nombre not like 'Comite%' and e.nombre not like 'Fondo%'  and e.nombre not like 'Especial%' and e.nombre not like 'Maestr%' order by e.nombre");
			dependenciaItem = new SelectItem[dependenciasUN.size()];
			for (int i = 0; i < dependenciasUN.size(); i++) {
				Dependencia dd = (Dependencia) dependenciasUN.get(i);
				dependenciaItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			}
		}

		vigenciaItems = new SelectItem[91];
		for (int i = 0; i <= 90; i++) {
			vigenciaItems[i] = new SelectItem(i, String.valueOf(i));
		}

		List<DominioDetalle> listaPrograma = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_PROGRAMA + "' order by dd.descripcion");
		programaItems = new SelectItem[listaPrograma.size()];
		for (int i = 0; i < listaPrograma.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaPrograma.get(i);
			programaItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
		}

		if (esArrayVacio(tipoArchivoItem)) {
			List<TipoArchivo> listaTipoArchivo = servicioGeneral.obtenerObjetos(TipoArchivo.class,
					"from TipoArchivo e where e.id in (48,49,50,51,5,67,68) order by e.nombre");
			tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
			for (int i = 0; i < listaTipoArchivo.size(); i++) {
				TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
				tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
			}
			tipoArchivo = (TipoArchivo) listaTipoArchivo.get(0);
		}

		// ******* fin pry conv externa *********
		listaArchivos = new ArrayList<Archivo>();
		if (recomendaciones == null) {
			recomendaciones = new SelectItem[4];
			recomendaciones[0] = new SelectItem(EstadoProyecto.APROBADO, EstadoProyecto.APROBADO_NOMBRE);
			recomendaciones[1] = new SelectItem(EstadoProyecto.ELEGIBLE, EstadoProyecto.ELEGIBLE_NOMBRE);
			recomendaciones[2] = new SelectItem(EstadoProyecto.BANCO_FINANCIABLE,
					EstadoProyecto.BANCO_FINANCIABLE_NOMBRE);
			recomendaciones[3] = new SelectItem(EstadoProyecto.NO_APROBADO, EstadoProyecto.NO_APROBADO_NOMBRE);
		}

		// Agendas
		cargarAgendaConocimientoLista();

		if (opcionesReclamacionesItem == null) {
			opcionesReclamacionesItem = new SelectItem[2];
			opcionesReclamacionesItem[0] = new SelectItem("A", "Aprobada");
			opcionesReclamacionesItem[1] = new SelectItem("R", "Rechazada");
		}

	}

	private void cargarTipoIngresos() {
		List<TipoRubro> tipoRubroIngresos = servicioFinanciacion.obtenerTipoIngresos();
		if (categoriaIngresoItems == null) {
			categoriaIngresoItems = new SelectItem[tipoRubroIngresos.size()];
			for (int i = 0; i < tipoRubroIngresos.size(); i++) {
				TipoRubro tipoRubro = tipoRubroIngresos.get(i);
				categoriaIngresoItems[i] = new SelectItem(tipoRubro.getId(), tipoRubro.getNombre());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void cargarRubrosTipoIngresos() {
		TipoRubro rubroCategoria = new TipoRubro();
		rubroCategoria.setId(Long.parseLong(categoria));
		List<TipoRubro> tipoRubroIngresos = servicioGeneral.obtenerListaTipoRubroXPadre(rubroCategoria);
		recursoIngresoItems = new SelectItem[tipoRubroIngresos.size()];
		for (int i = 0; i < tipoRubroIngresos.size(); i++) {
			TipoRubro tipoRubro = tipoRubroIngresos.get(i);
			recursoIngresoItems[i] = new SelectItem(tipoRubro.getId(), tipoRubro.getNombre());
		}
	}

	public void actualizarTipofecha() {
		System.out.println("tipofecha=" + this.proyectoActual.getTipofechaPry());
	}

	// crear entidades
	public void solicitudIngresoFuente() {

		if (nombreFuente != null && nombreFuente.length() > 2 && nitFuente != null && nitFuente.length() > 2
				&& direccionFuente != null && direccionFuente.length() > 2) {

			// Guardar en la bd
			FuenteFinanciacion fte = new FuenteFinanciacion();
			String descripcionFuente = ReemplazaAcentos.quitarTildes(nombreFuente != null ? nombreFuente : "")
					.toUpperCase();

			fte.setNaturaleza(naturalezaFuente);
			fte.setDescripcion(descripcionFuente);
			fte.setInternaExterna("O");
			fte.setNit(nitFuente != null ? nitFuente : "");
			fte.setDireccion(direccionFuente != null ? direccionFuente : "");
			fte.setTelefono(telefonoFuente != null ? telefonoFuente : "");
			fte.setObservaciones("ENT_REG_PRY");
			fte.setQuipu("S");

			try {
				servicioGeneral.guardarObjeto(fte);

				// Correo a Hermes
				Correo correo = new Correo();
				CorreoPlantilla cp = cargarPlantilla(nPlantillaUsuario);
				correo.setOrigen(Correo.CORREO_HERMES);
				correo.setAsunto(cp.getAsunto().replaceAll("<<NOMBRE>>", descripcionFuente));
				correo.setCuerpo(cp.getCuerpo().replaceAll("<<NOMBRE>>", descripcionFuente));
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<NIT>>", nitFuente != null ? nitFuente : ""));
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<NATURALEZA>>",
						naturalezaFuente != null ? naturalezaFuente : ""));
				correo.setCuerpo(
						correo.getCuerpo().replaceAll("<<DIRECCION>>", direccionFuente != null ? direccionFuente : ""));
				correo.setCuerpo(
						correo.getCuerpo().replaceAll("<<TELEFONO>>", telefonoFuente != null ? telefonoFuente : ""));
				String personaCorreo = "(" + personaActual.getId().getTipoDocumento() + "-"
						+ personaActual.getId().getDocumento() + ") " + personaActual.getNombre1() + " "
						+ personaActual.getApellido1();
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<CEDULA>>", personaCorreo));
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<CORREO>>", personaActual.getEmail()));
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<ID>>", fte.getId()));

				System.out.println("correo ===" + correo.getCuerpo());

				// correo.adicionarDireccion(Correo.CORREO_HERMES_COMUNICACIONES);
				servicioCorreo.enviarCorreo(correo);

				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO,
						"La información de creación de la fuente ha sido enviada correctamente.",
						"La información de creación de la fuente ha sido enviada correctamente, el sistema verificará y en un periodo de una hora hábil estará activa en el sistema."));

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("msgs",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"La información de la fuente NO ha sido enviada, revise la información ingresada.",
								"La información de la fuente NO ha sido enviada, revise la información ingresada."));
			}

		} else {
			FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"La información de la fuente NO ha sido enviada, revise la información ingresada.",
							"La información de la fuente NO ha sido enviada, revise la información ingresada."));

		}

	}

	public boolean revisarTieneVersiones() {

		if (proyectoActual != null) {
			String hql = "select p from Proyecto p where p.proyectoPadre=" + proyectoActual.getId()
					+ " and p.estadoProyecto.id not in ('" + EstadoProyecto.BORRADO + "')";
			listaVersiones = servicioGeneral.obtenerObjetos(Proyecto.class, hql);

			return !esListaVacia(listaVersiones);
		}

		return false;
	}

	// Cargar listas cartas
	public void cargarCartasEspecifica() {
		Convocatoria convocatoria = (Convocatoria) proyectoActual.getModalidad();
		List<CorreoPlantilla> listaplantilla = null;
		if (convocatoria != null) {
			String consulta = "from CorreoPlantilla co where co.tipo = 'S'" + "and co.padre = '" + tipoCartSeleccionado
					+ "' and co.tipoModalidad = '" + convocatoria.getTipo().getId() + "'";
			listaplantilla = servicioGeneral.obtenerObjetos(CorreoPlantilla.class, consulta);
		}
		if (esListaVacia(listaplantilla)) {
			String consulta = "from CorreoPlantilla co where co.tipo = 'S'" + "and co.padre = '" + tipoCartSeleccionado
					+ "'";
			listaplantilla = servicioGeneral.obtenerObjetos(CorreoPlantilla.class, consulta);
		}
		if (listaplantilla == null) {
			mensajeCartas = "";
		} else {
			plantillas = new SelectItem[listaplantilla.size()];
			int i = 0;
			for (Iterator<CorreoPlantilla> ic = listaplantilla.iterator(); ic.hasNext(); i++) {
				CorreoPlantilla p = (CorreoPlantilla) ic.next();
				plantillas[i] = new SelectItem(p.getId().toString(), p.getNombre());
			}
			if (!esListaVacia(listaplantilla)) {
				avalId = ((CorreoPlantilla) (listaplantilla.get(0))).getId().toString();
				correoActual = (CorreoPlantilla) (listaplantilla.get(0));
			} else {
				avalId = "0";
				correoActual = new CorreoPlantilla();
			}
		}
	}

	// inicio 2628
	public void enviarCorreoCoordBiodiversidadProrroga(Proyecto solicitudBiodiversidad, Convocatoria convocatoria) {
		Correo correoCoordBio = new Correo();
		correoCoordBio.setOrigen(Correo.CORREO_HERMES);
		String cuerpo = "";
		String tipoSolicitudBiodiversidad = "";

		if (solicitudBiodiversidad.getEsPermisoMarco()) {
			tipoSolicitudBiodiversidad = "Proyecto de investigación";
		} else if (solicitudBiodiversidad.getEsPermisoMarcoAsignatura()) {
			tipoSolicitudBiodiversidad = "Asignatura";
		}

		if (solicitudBiodiversidad != null && solicitudBiodiversidad.getId() != null) {
			Persona coordinadorBiodiversidad = servicioProyecto
					.obtenerCoordinadorProyecto(new Long(solicitudBiodiversidad.getId()));
			correoCoordBio.adicionarDireccion(coordinadorBiodiversidad.getEmail());
			// correoCoordBio.adicionarCopiaOculta(Correo.CORREO_HERMES);

			CorreoPlantilla correoPlantillaBio = cargarPlantilla(310);
			correoCoordBio
					.setAsunto(correoPlantillaBio.getAsunto().replaceAll("<<ID>>", proyectoActual.getId().toString()));

			cuerpo = correoPlantillaBio.getCuerpo();

			String nombre = Matcher.quoteReplacement(this.proyectoActual.getNombre());
			cuerpo = cuerpo.replaceAll("<<TITULO>>", nombre);
			cuerpo = cuerpo.replaceAll("<<ID>>", String.valueOf(this.proyectoActual.getId()));
			cuerpo = cuerpo.replaceAll("<<CONVOCATORIA>>", convocatoria.getTitulo());
			// si se guarda la prorroga antes de generar la carta.
			DateFormat fechafinPry = new SimpleDateFormat("dd/MM/yyyy");
			String fechaFinString = fechafinPry.format(fechaFinalProyecto);
			cuerpo = cuerpo.replaceAll("<<FECHA_FIN>>", fechaFinString);

			// proyecto que corresponde a la solicitud de biodiversidad
			cuerpo = cuerpo.replaceAll("<<CODIGO_SOLICITUD_BIODIVERSIDAD>>", solicitudBiodiversidad.getId().toString());
			cuerpo = cuerpo.replaceAll("<<NOMBRE_SOLICITUD_BIODIVERSIDAD>>", solicitudBiodiversidad.getNombre());
			cuerpo = cuerpo.replaceAll("<<TIPO_SOLICITUD_BIODIVERSIDAD>>", tipoSolicitudBiodiversidad);

			correoCoordBio.setCuerpo(cuerpo);
			servicioCorreo.enviarCorreo(correoCoordBio);
		}
	}
	// fin 2628

	// Guardar y enviar carta proyecto
	public void guardarCartaProyecto(FileUploadEvent event) throws SQLException {
		if (docenteYaFirmo) {
			guardarDatosCarta();
		}
		mensajeEnvioCarta = "";
		boolean esCartaInicio = false;
		boolean esCartafinalizacion = false;
		boolean existeUnidadAcademicaBasica = true;
		Convocatoria conv = servicioModalidad.obtenerConvocatoria(this.proyectoActual.getModalidad().getId());
		if (conv != null) {
			boolean isJornadaDocente = definirJornadaDocente();
			Integer nduracion = new Integer(0);
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			Persona investigadorPrincipalTemporal = servicioProyecto
					.obtenerInvestigadorPrincipalXProyecto(new Long(proyectoActual.getId()));
			String dirCorreoInvestigadorPrincipal = investigadorPrincipalTemporal.getEmail();

			InvestigadorInterno investigadorInterno = servicioPersona
					.obtenerInvestigadorInterno(cargarPersonaActual().getId());
			Dependencia dependenciaEnvio = cargarDependenciaEnvio(isJornadaDocente, conv.getId(), investigadorInterno);
			try {
				// Adicionar al correo a personas con rol Unidad Administrativa
				if (Integer.parseInt(avalId) == CorreoPlantilla.CAMBIO_RUBRO
						|| Integer.parseInt(avalId) == CorreoPlantilla.ADICION_PRESUPUESTAL
						|| Integer.parseInt(avalId) == CorreoPlantilla.PRORROGA
						|| Integer.parseInt(avalId) == CorreoPlantilla.SUSPENSION
						|| Integer.parseInt(avalId) == CorreoPlantilla.CANCELACION_PRINCIPAL
						|| Integer.parseInt(avalId) == CorreoPlantilla.CANCELACION_ESTUDIANTE
						|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_PRINCIPAL
						|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_ESTUDIANTE
						|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_GRUPO
						|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_JOVENES_INVESTIGADOR_TUTOR
						|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_JOVENES_INVESTIGADOR_TUTOR_2014
						|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_JOVENES_INVESTIGADORES
						|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_SEMILLEROS
						|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_TRADUCCION_ARTICULOS
						|| Integer.parseInt(avalId) == CorreoPlantilla.CAMBIO_INVESTIGADOR_PRINCIPAL
						|| Integer.parseInt(avalId) == CorreoPlantilla.REACTIVACION
						|| Integer.parseInt(avalId) == CorreoPlantilla.COMPROMISO_EXTERNAS
						|| Integer.parseInt(avalId) == CorreoPlantilla.RESOLUCION_EXTERNOS
						|| Integer.parseInt(avalId) == CorreoPlantilla.RESOLUCION_EXTERNOS_CONTRAPARTIDA) {

					Dependencia dependenciaUnidadAdministrativa = servicioDependencia
							.obtenerDependencia(investigadorPrincipalTemporal.getId());

					List listaPersonasUAI = servicioPersona.obtenerPersonasUnidadAdministrativa(
							dependenciaUnidadAdministrativa.getFacultad().getId(),
							dependenciaUnidadAdministrativa.getSede().getId().toString());

					for (Iterator iterator = listaPersonasUAI.iterator(); iterator.hasNext();) {
						Persona p = (Persona) iterator.next();
						correo.adicionarDireccion(p.getEmail());
					}
					correo.adicionarCopiaOculta(dirCorreoInvestigadorPrincipal);
				}

				// Verificar si el consecutivo esta vigente
				if (Integer.valueOf(avalId) == CorreoPlantilla.CERTIFICADO_MOVILIZACION) {
					List<ProyectoCarta> cartasSinEnviar = servicioGeneral.obtenerObjetosLimitado(ProyectoCarta.class,
							"select #seq pc.seq from ProyectoCarta pc where pc.proyecto.id = '" + proyectoActual.getId()
									+ "' and pc.idSolicitud = '" + codigoSolicitud + "' " + "and pc.estadoCarta.id = '"
									+ EstadoCarta.SIN_ENVIAR + "'");
					if (esListaVacia(cartasSinEnviar)) {
						nConsecutivoCarta = servicioGeneral.consultaUltimoIdPorDependenciaTipoCarta(
								dependenciaEnvio.getId(), new Date(),
								String.valueOf(TipoCarta.CERTIFICADO_MOVILIZACION));
					} else {
						nConsecutivoCarta = Long.parseLong(cartasSinEnviar.get(0).getSeq());
					}
				} else {
					nConsecutivoCarta = servicioGeneral.consultaUltimoIdCarta(dependenciaEnvio.getId(), new Date());
				}
				// Adicionar correo de coordinador de seguimiento
				correo.adicionarDireccion(dirCorreoInvestigadorPrincipal);
				Persona coordinadorSeguimientoTemporal = servicioProyecto
						.obtenerCoordinadorProyecto(new Long(proyectoActual.getId()));
				correo.adicionarCopiaOculta(coordinadorSeguimientoTemporal.getEmail());

				// Adicionar correo persona actual
				Persona personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
				correo.adicionarDireccion(personaActual.getEmail());

				// Cargar información plantillla a correo
				nPlantilla = Integer.valueOf(avalId).intValue();
				CorreoPlantilla cp = cargarPlantilla(nPlantilla);
				correo.setAsunto(cp.getAsunto().replace("<<ID>>", proyectoActual.getId().toString()));
				correo.setCuerpo(cuerpoCorreo);

				if (Integer.valueOf(avalId) == CorreoPlantilla.CERTIFICADO_MOVILIZACION) {
					correo.setAsunto(correoActual.getAsunto());
					cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID_CERTIFICADO>>", nConsecutivoCarta.toString());
					correo.setCuerpo(cuerpoCorreo);
					if (servicioGeneral.esAmbienteProduccion()) {
						correo.adicionarDireccion(Correo.CORREO_ANLA);
					}
					correo.adicionarDireccion(Correo.CORREO_BIODIVERSIDAD_UNAL);

					Proyecto pryAsociado = new Proyecto();
					pryAsociado = servicioProyecto.obtenerProyecto(proyectoActual.getId(), (short) 2); // proyecto con
																										// investigadores

					if (proyectoActual.getEsPermisoMarco()) {
						if (pryAsociado.getListaInvestigadoresProyecto() != null
								&& pryAsociado.getListaInvestigadoresProyecto().size() > 0) {
							for (int i = 0; i < pryAsociado.getListaInvestigadoresProyecto().size(); i++) {
								InvestigadorProyecto ip = pryAsociado.getListaInvestigadoresProyecto().get(i);
								if (ip.getInvestigador() != null && ip.getInvestigador().getEmail() != null) {
									correo.adicionarDireccion(ip.getInvestigador().getEmail());
								}
							}
						}

					}

				}

				// Alerta cuando se realiza prorroga a un proyecto que tiene una
				// solicitud biodiversidad asociada
				if (Integer.parseInt(avalId) == CorreoPlantilla.PRORROGA) {
					Proyecto solicitudBiodiversidad = servicioProyecto
							.obtenerProyectoCodigoDib(proyectoActual.getId().toString());

					if (solicitudBiodiversidad != null) {
						if (solicitudBiodiversidad.getEsPermisoMarco()
								|| solicitudBiodiversidad.getEsPermisoMarcoAsignatura()
								|| solicitudBiodiversidad.getEsContratoBiodiversidad()) {
							enviarCorreoCoordBiodiversidadProrroga(solicitudBiodiversidad, conv);
						}
					}
				}

				// Cargar archivo a lista de archivos adjuntados
				archivoCargado = event.getFile();
				int ii = archivoCargado.getFileName().lastIndexOf("\\");
				Archivo archivoTemporal = new Archivo();
				archivoTemporal.setBytes(archivoCargado.getContents());

				archivoTemporal.setNombre(archivoCargado.getFileName().substring(ii + 1));

				// Cargar archivo a correo
				if (archivoCargado != null) {
					listaArchivos.add(archivoTemporal);
					String nombreArchivo = archivoTemporal.getNombre();
					correo.setNombreAdjunto(nombreArchivo);
					FacesContext ctx = FacesContext.getCurrentInstance();
					ServletContext sc = (ServletContext) ctx.getExternalContext().getContext();
					String rutaArchivo = sc.getRealPath(RUTA_ADJUNTO);
					rutaArchivo += "//" + nombreArchivo;
					servicioCorreo.crearArchivo(rutaArchivo, archivoCargado.getContents());
					correo.setAdjunto(rutaArchivo);
				}

				if (sesion.getAttribute("nConsecutivo") != null
						&& !(Integer.valueOf(avalId) == CorreoPlantilla.CERTIFICADO_MOVILIZACION)) {
					if (nConsecutivoCarta.compareTo((Long) sesion.getAttribute("nConsecutivo")) != 0) {
						mensajeEnvioCarta = "El consecutivo de la carta ha cambiado, por favor genere nuevamente la carta.";
					}
				}

				if (esCadenaVacia(dependenciaRes)
						&& Integer.parseInt(avalId) != CorreoPlantilla.CERTIFICADO_MOVILIZACION) {
					mensajeEnvioCarta = "La carta aún no sido enviada: por favor ingrese los datos correspondientes y seleccione"
							+ " la opcion 'Generar carta (PDF)'.";
				}

				// Carga de tipo carta
				if (esCadenaVacia(mensajeEnvioCarta)) {

					long tipoCartaId;

					SimpleDateFormat spy = new SimpleDateFormat("yyyy");
					String fechaSTemporal = spy.format(fechaSesion);

					proyectoCarta = new ProyectoCarta();
					Date fechaGeneracion = new Date();

					nduracion = proyectoActual.getDuracion();
					List<TipoCarta> listaTipoCarta;
					Long idCarta;

					if (Integer.parseInt(avalId) == CorreoPlantilla.PRORROGA) {
						tipoCartaId = TipoCarta.PRORROGA;
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.CAMBIO_RUBRO) {
						tipoCartaId = TipoCarta.CAMBIO_RUBRO;
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.ADICION_PRESUPUESTAL) {
						tipoCartaId = TipoCarta.ADICION_PRESUPUESTAL;
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_PRINCIPAL
							|| Long.parseLong(avalId) == CorreoPlantilla.FINALIZACION_PRINCIPAL_EXTERNA
							|| Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_ESTUDIANTE
							|| Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_SIN_FINANCIACION
							|| Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_JOVENES_INVESTIGADORES) {
						tipoCartaId = TipoCarta.FINALIZACION;
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.CANCELACION_PRINCIPAL
							|| Integer.parseInt(avalId) == CorreoPlantilla.CANCELACION_ESTUDIANTE) {
						tipoCartaId = TipoCarta.CANCELACION;
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.CERTIFICADO_MOVILIZACION) {
						tipoCartaId = TipoCarta.CERTIFICADO_MOVILIZACION;
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.REACTIVACION) {
						tipoCartaId = TipoCarta.REACTIVACION;
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.CAMBIO_INVESTIGADOR_PRINCIPAL) {
						tipoCartaId = TipoCarta.CAMBIO_INVESTIGADOR;
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.CAMBIO_INTEGRANTES) {
						tipoCartaId = TipoCarta.CAMBIO_INTEGRANTES;
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.CAMBIO_CONTENIDO) {
						tipoCartaId = TipoCarta.CAMBIO_CONTENIDO;
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.RESOLUCION_EXTERNOS
							|| Integer.parseInt(avalId) == CorreoPlantilla.RESOLUCION_EXTERNOS_CONTRAPARTIDA) {
						tipoCartaId = TipoCarta.RESOLUCION_EXTERNA;
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.RESOLUCION_MODIFICACION) {
						tipoCartaId = TipoCarta.RESOLUCION_MODIFICATORIAS;
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.SUSPENSION) {
						tipoCartaId = TipoCarta.SUSPENSION;
					} else {
						tipoCartaId = TipoCarta.INICIO;
					}

					listaTipoCarta = servicioGeneral.obtenerObjetos(TipoCarta.class,
							"from TipoCarta where id ='" + tipoCartaId + "'");
					TipoCarta tipoCarta = (TipoCarta) listaTipoCarta.get(0);
					proyectoCarta.setCarta(tipoCarta);
					proyectoCarta.setSubtipo(Long.parseLong(avalId));

					if (Integer.parseInt(avalId) == CorreoPlantilla.CERTIFICADO_MOVILIZACION) {
						dependenciaRes = Dependencia.ID_VICERRECTORIA.toString();
					}

					idCarta = servicioGeneral.consultaIdCarta(proyectoActual.getId(), tipoCarta.getId(), dependenciaRes,
							fechaSTemporal);

					if (Integer.parseInt(avalId) == CorreoPlantilla.CERTIFICADO_MOVILIZACION) {
						idCarta = nConsecutivoCarta;
					}

					// Actualizar información carta
					List<EstadoCarta> listaEstadoCarta = new ArrayList<EstadoCarta>();
					EstadoCarta estadoCarta = new EstadoCarta();
					if (docenteYaFirmo) {
						listaEstadoCarta = servicioGeneral.obtenerObjetos(EstadoCarta.class,
								"from EstadoCarta where id ='AP'");
						estadoCarta = (EstadoCarta) listaEstadoCarta.get(0);
					} else {
						listaEstadoCarta = servicioGeneral.obtenerObjetos(EstadoCarta.class,
								"from EstadoCarta where id ='G'");
						estadoCarta = (EstadoCarta) listaEstadoCarta.get(0);
					}

					Long codigoSol = 0L;
					if (mostrarCodigoSolicitudJustificacion) {
						codigoSol = new Long(codigoSolicitud);
					}
					proyectoCarta.setProyecto(proyectoActual);
					proyectoCarta.setEstadoCarta(estadoCarta);
					proyectoCarta.setFechaGeneracion(fechaGeneracion);
					proyectoCarta.setIdSolicitud(codigoSol);

					if (tipoCartSeleccionado.equals(String.valueOf(TipoCarta.RESOLUCION_EXTERNA))
							|| tipoCartSeleccionado.equals(String.valueOf(TipoCarta.RESOLUCION_MODIFICATORIAS))
							|| Long.parseLong(avalId) == CorreoPlantilla.FINALIZACION_PRINCIPAL_EXTERNA) {
						if (proyectoActual.getUnidadEjecutora() != null) {
							Dependencia dependencia = new Dependencia(proyectoActual.getUnidadEjecutora());
							proyectoCarta.setDependencia(dependencia);
						}
						if (StringUtils.isNotBlank(ordenadorTipoResolucion)) {
							proyectoCarta.setTipoOrdenador(ordenadorTipoResolucion);
						}

						if (StringUtils.isNotBlank(numeroResolucion)) {
							proyectoCarta.setNumeroActoAdministrativo(numeroResolucion);
						} else {
							mensajeEnvioCarta = "Por favor diligencia el número de resolución.";
							return;
						}

						if (proyectoActual.getActoEntidad() != null) {
							List<DominioDetalle> dominiosDetalle = servicioGeneral.obtenerObjetos(DominioDetalle.class,
									"select dd from DominioDetalle dd, Dominio d where dd.identificador.tipo = '"
											+ proyectoActual.getActoEntidad() + "'"
											+ "and dd.identificador.id = d.id and d.tipo = '"
											+ Dominio.TIPOS_ACTO_ADMINISTRATIVO + "'");
							if (dominiosDetalle != null && dominiosDetalle.size() > 0) {
								proyectoCarta.setTipoActoAdministrativo(dominiosDetalle.get(0));
							}
						}

						proyectoCarta.setFechaResolucion(fechaResolucion);

						if (requiereConsiderando == 'S') {
							if (!esCadenaVacia(considerando)) {
								proyectoCarta.setNuevoConsiderando(considerando);
							} else {
								mensajeEnvioCarta = "Por favor ingrese el texto del considerando.";
							}
						}

						if (tipoCartSeleccionado.equals(String.valueOf(TipoCarta.RESOLUCION_MODIFICATORIAS))) {
							if (otrosi.equals("S")) {
								if (!esCadenaVacia(otrosiNum) && otrosiFecha != null) {
									Convenio convenio = new Convenio();
									List<Convenio> listaConvenios = servicioGeneral.obtenerObjetos(
											"select c from Convenio c where c.idProyecto = " + proyectoActual.getId());
									convenio = listaConvenios.get(0);

									for (Iterator iterator = convenio.getOtrosi().iterator(); iterator.hasNext();) {
										ConvenioOtrosi otroSi = (ConvenioOtrosi) iterator.next();
										if (otroSi.getNumero().equals(otrosiNum)
												&& otroSi.getFecha().equals(otrosiFecha)) {
											return;
										}
									}
									ConvenioOtrosi convenioOtrosi = new ConvenioOtrosi();
									convenioOtrosi.setNumero(otrosiNum);
									convenioOtrosi.setFecha(otrosiFecha);
									convenio.adicionarOtrosi(convenioOtrosi);
									servicioGeneral.guardarObjeto(convenio);
								} else {
									mensajeEnvioCarta = "Por favor diligenciar el número y fecha del otrosí.";
									return;
								}
							}
						}
					}

					List<ProyectoCarta> listaCartaProyecto;

					if (idCarta.intValue() > 0) {
						listaCartaProyecto = servicioGeneral.obtenerObjetos(ProyectoCarta.class,
								"from ProyectoCarta P where P.seq ='" + idCarta + "' and P.sede ='" + dependenciaRes
										+ "' and P.carta.id ='" + tipoCartaId + "' and  P.proyecto.id= "
										+ proyectoActual.getId());
						ProyectoCarta proyectoCartaTemporal = (ProyectoCarta) listaCartaProyecto.get(0);

						String sql = "update HER_PROYECTO_CARTA set ETC_ID = 'F' where PCA_ID = '"
								+ proyectoCartaTemporal.getId() + "'";
						servicioGeneral.ejecutarSentencia(sql);
						if (Integer.parseInt(avalId) == CorreoPlantilla.CERTIFICADO_MOVILIZACION) {
							proyectoCarta.setId(proyectoCartaTemporal.getId());
						}
					}

					if (idCarta.intValue() > 0
							&& Integer.parseInt(avalId) == CorreoPlantilla.CERTIFICADO_MOVILIZACION) {
						// No se realiza ninguna actualización
					} else {
						proyectoCarta.setAño(spy.format(new Date()));
						proyectoCarta.setSeq(String.valueOf(sesion.getAttribute("nConsecutivo")));
						proyectoCarta.setSede(dependenciaRes.toString());
						Persona persona = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
						proyectoCarta.setResponsable(persona);
						this.servicioGeneral.guardarObjeto(proyectoCarta);
					}

					cargarArchivoDisco(archivoCargado, "HER_PROYECTO_CARTA", proyectoCarta.getId().toString());

					if (Integer.parseInt(avalId) == CorreoPlantilla.INICIO_GRUPO
							|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_PRINCIPAL
							|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_ESTUDIANTE
							|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_JOVENES_INVESTIGADORES
							|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_JOVENES_INVESTIGADOR_TUTOR_2014
							|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_SEMILLEROS
							|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_JOVENES_INVESTIGADOR_TUTOR
							|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_SIN_FINANCIACION
							|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_TRADUCCION_ARTICULOS
							|| Integer.parseInt(avalId) == CorreoPlantilla.COMPROMISO_EXTERNAS
							|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_PROYECTO_LABORATORIOS) {

						cargarArchivoDisco(archivoCargado, "HER_SEG_PROYECTO_PERSONA//SEG_CARTAINICIO",
								proyectoCoordinadorUno.getId().toString());

						esCartaInicio = true;
						List<ProyectoCoordinador> proyectosCoordinador = servicioGeneral.obtenerObjetos(
								ProyectoCoordinador.class,
								"from ProyectoCoordinador p where p.idProyecto='" + proyectoActual.getId()
										+ "' and p.perId = '" + proyectoCoordinadorUno.getPerId() + "' and p.tdoId='"
										+ proyectoCoordinadorUno.getTdoId() + "' ");
						ProyectoCoordinador proyectoCoordinador = null;
						if (!esListaVacia(proyectosCoordinador)) {
							proyectoCoordinador = (ProyectoCoordinador) proyectosCoordinador.get(0);
							if (proyectoCoordinador != null && proyectoCoordinador.getCartaFinalizacion() != null
									&& proyectoCoordinador.getBytesCartaFinalizacion() != null) {
								proyectoCoordinadorUno
										.setBytesCartaFinalizacion(proyectoCoordinador.getBytesCartaFinalizacion());
							}
						}
						if (proyectoCoordinador != null) {
							proyectoCoordinadorUno.setPerRevision(proyectoCoordinador.getPerRevision());
							proyectoCoordinadorUno.setTdoId2(proyectoCoordinador.getTdoId2());
							proyectoCoordinadorUno.setPerEvaluacion(proyectoCoordinador.getPerEvaluacion());
							proyectoCoordinadorUno.setTdoId3(proyectoCoordinador.getTdoId3());
						}

						if (docenteYaFirmo) {
							proyectoCoordinadorUno.setSiAprobacion("AP");

							// Se guarda el estado activo
							EstadoProyecto estadoProyectoTemporal = new EstadoProyecto();
							estadoProyectoTemporal.setId(EstadoProyecto.ACTIVO);
							estadoProyectoTemporal.setNombre("Activo");

							HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
							Date fechaHoy = new Date();
							hepry.setEstadoProyecto(estadoProyectoTemporal);
							hepry.setFecha(fechaHoy);
							hepry.setJustificacion("Activación por carga de acta firmada por el docente.");
							hepry.setResponsable(cargarPersonaActual());
							proyectoActual.adicionarHistorico(hepry);
							proyectoActual.setEstadoProyecto(estadoProyectoTemporal);
							servicioGeneral.guardarObjeto(proyectoActual);
						} else {
							proyectoCoordinadorUno.setSiAprobacion("G");
						}

						servicioGeneral.guardarObjeto(proyectoCoordinadorUno);
						coordinadorSeguimientoProyecto.setPaisOrigen("G");

					} else if (Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_PRINCIPAL
							|| Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_ESTUDIANTE
							|| Long.parseLong(avalId) == CorreoPlantilla.FINALIZACION_PRINCIPAL_EXTERNA
							|| Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_JOVENES_INVESTIGADORES
							|| Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_SIN_FINANCIACION) {
						esCartafinalizacion = true;

						cargarArchivoDisco(archivoCargado, "HER_SEG_PROYECTO_PERSONA//SEG_CARTAFINALIZACION",
								proyectoCoordinadorUno.getId().toString());

						List<ProyectoCoordinador> proyectosCoordinador = servicioGeneral.obtenerObjetos(
								ProyectoCoordinador.class,
								"from ProyectoCoordinador p where p.idProyecto='" + proyectoActual.getId()
										+ "' and p.perId = '" + proyectoCoordinadorUno.getPerId() + "' and p.tdoId='"
										+ proyectoCoordinadorUno.getTdoId() + "' ");
						ProyectoCoordinador proyectoCoordinador = null;
						if (!esListaVacia(proyectosCoordinador)) {
							proyectoCoordinador = (ProyectoCoordinador) proyectosCoordinador.get(0);
							if (proyectoCoordinador != null && proyectoCoordinador.getCartaInicio() != null
									&& proyectoCoordinador.getBytesCartaInicio() != null) {

								proyectoCoordinadorUno.setBytesCartaInicio(proyectoCoordinador.getBytesCartaInicio());
							}
						}
						if (proyectoCoordinador != null) {
							proyectoCoordinadorUno.setPerRevision(proyectoCoordinador.getPerRevision());
							proyectoCoordinadorUno.setTdoId2(proyectoCoordinador.getTdoId2());
							proyectoCoordinadorUno.setPerEvaluacion(proyectoCoordinador.getPerEvaluacion());
							proyectoCoordinadorUno.setTdoId3(proyectoCoordinador.getTdoId3());
						}
						proyectoCoordinadorUno.setSiFinalizacion("G");
						proyectoCoordinadorUno.setSiAprobacion(proyectoCoordinador.getSiAprobacion());
						servicioGeneral.guardarObjeto(proyectoCoordinadorUno);
					}

					if (esCartafinalizacion) {
						if (saldoFinanciacion != null && Integer.parseInt(saldoFinanciacion) > 0) {
							ProyectoSaldoFinanciacion proyectosaldofinanciacion = new ProyectoSaldoFinanciacion();
							proyectosaldofinanciacion.setIdProyecto(new Long(proyectoActual.getId()));
							proyectosaldofinanciacion.setSaldo(saldoFinanciacion);
							servicioGeneral.guardarObjeto(proyectosaldofinanciacion);

							if (proyectoActual.getEstadoProyecto() != null) {
								String sql = "update HER_PROYECTO set EPR_ID = '"
										+ proyectoActual.getEstadoProyecto().FINALIZADO + "' where pry_id = '"
										+ String.valueOf(proyectoActual.getId() + "'");
								HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
								Date fechaHoy = new Date();
								EstadoProyecto ep = new EstadoProyecto();
								ep.setId(proyectoActual.getEstadoProyecto().FINALIZADO);
								hepry.setEstadoProyecto(ep);
								hepry.setProyecto(proyectoActual);
								hepry.setFecha(fechaHoy);
								hepry.setResponsable(cargarPersonaActual());
								hepry.setJustificacion("Proyecto Finalizado");
								proyectoActual.setEstadoProyecto(ep);
								try {
									servicioProyecto.actualizarProyecto(proyectoActual);
									servicioGeneral.guardarObjeto(hepry);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}

					if (servicioCorreo.enviarCorreo(correo)) {
						if (Integer.parseInt(this.avalId) == 58 || Integer.parseInt(this.avalId) == 60
								|| Integer.parseInt(this.avalId) == 92 || Integer.parseInt(this.avalId) == 93) {
							if (!existeUnidadAcademicaBasica) {
								mensajeEnvioCarta = "El documento se ha enviado correctamente. Pero no existe un Jefe de Unidad Administrativo a la cual informar.";
							} else {
								mensajeEnvioCarta = "El documento se ha enviado correctamente.";
							}
						} else {
							mensajeEnvioCarta = "El documento se ha enviado correctamente.";
						}
					} else {
						mensajeEnvioCarta = "El documento ha sido adjuntado al proyecto, pero ha existido un problema inesperado en el envio de correo electrónico.";
					}
				}
			} catch (DataIntegrityViolationException ex) {
				System.out.println(ex.toString());
				if (ex.getMessage().indexOf("AD_COMBINACION01_UK") > 0) {
					errorValidacion = "Ya existe un archivo con este nombre";
				} else {
					errorValidacion = "Ocurrio un error inesperado al publicar el archivo";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				errorValidacion = "Ocurrio un error inesperado al publicar el archivo";
			} catch (Exception e) {
				e.printStackTrace();
			}
			cargarCartasProyecto(proyectoActual.getId());
			cargarFechasProyecto(proyectoActual);
		} else {
			errorValidacion = "Ocurrio un error inesperado al publicar"
					+ " el archivo, intente nuevamente en unos minutos.";
		}
	}

	// Insertar archivo proyecto
	public void insertarArchivo(FileUploadEvent event) {
		archivo = event.getFile();
		insertarArchivoProyectoGenerico(archivo, proyectoActual, listaArchivosProyecto);
		listaArchivosProyecto = servicioProyecto.obtenerNombresArchivos(proyectoActual);
	}

	// Cargar dependencia segun tipo dependencia, tipo convocatoria e
	// investigador
	public Dependencia cargarDependenciaEnvio(boolean isJornadaDocente, long idModalidad,
			InvestigadorInterno coordinador) {

		Dependencia dependenciaResponsable = null;

		Convocatoria conv = servicioModalidad.obtenerConvocatoria(idModalidad);
		ConvocatoriaPadre cp = conv.getPadre();

		// Esto se debe quitar cuando entre a producción
		boolean usarDependenciaCoordinador = false;
		List<Parametro> parametros = servicioGeneral.obtenerObjetos(Parametro.class,
				"from Parametro p where p.nombre = '" + Parametro.HAB_CART_DPN_COOR + "'");

		if (parametros != null && !parametros.isEmpty()) {
			Parametro parametro = parametros.get(0);
			if (!esCadenaVacia(parametro.getValor()) && "SI".equals(parametro.getValor())) {
				usarDependenciaCoordinador = true;
			}
			if (usarDependenciaCoordinador) {
				String cartasVicedecanatura = conv.getHabilitarCartasFirmaVicedecanatura();
				// Se verifica el parametro de la convocatoria, si en la
				// convocatoria dice que se habilita carta con firma de la
				// vicecanatura,
				// se deshabilita la opción de la dependencia del coordinador.
				usarDependenciaCoordinador = !(cartasVicedecanatura != null && "S".equals(cartasVicedecanatura));
			}
		}

		if (usarDependenciaCoordinador) {
			if (coordinador != null) {

				// if(!cp.getId().equals(418L)) //Si no es convocatoria
				// repotenciacion equipos
				if (conv.getRestriccion() != null && conv.getRestriccion().getId().equals("CONV_REP_EQU_LAB")) { // Si
																													// es
																													// convocatoria
																													// repotenciacion
																													// equiposconv

					List listaBogota = servicioGeneral.obtenerObjetoXID(Dependencia.class,
							Dependencia.ID_DIR_LABS_BOGOTA);
					List listaMedellin = servicioGeneral.obtenerObjetoXID(Dependencia.class,
							Dependencia.ID_DIR_LABS_MEDELLIN);
					List listaPalmira = servicioGeneral.obtenerObjetoXID(Dependencia.class,
							Dependencia.ID_DIR_INV_PALMIRA);

					if (coordinador.getDependencia().getSede().getId().equals(2L)) {
						if (listaBogota != null)
							dependenciaResponsable = (Dependencia) listaBogota.get(0);
					} else if (coordinador.getDependencia().getSede().getId().equals(3L)) {
						if (listaMedellin != null)
							dependenciaResponsable = (Dependencia) listaMedellin.get(0);
					} else if (coordinador.getDependencia().getSede().getId().equals(5L)) {
						if (listaPalmira != null)
							dependenciaResponsable = (Dependencia) listaPalmira.get(0);
					}
				} else {
					dependenciaResponsable = coordinador.getDependencia().getFacultad();
				}
			}
		} else {

			Dependencia dependenciaResTemporal;
			Investigador investigadorPrincipalTemporal = servicioProyecto
					.obtenerInvestigadorPrincipalXProyecto(new Long(proyectoActual.getId()));
			if (!esCadenaVacia(cp.getEsPermanente()) && "Y".equals(cp.getEsPermanente())) {
				if (isJornadaDocente) {
					parametros = this.servicioGeneral.obtenerListaObjetosWhere(Parametro.class,
							"WHERE p.nombre = 'SEG_JORNADA_DOCENTE_SEDE' AND p.valor like '%-"
									+ investigadorPrincipalTemporal.getDependencia().getSede().getId() + "-%'");
					if (!esListaVacia(parametros)) {
						dependenciaResTemporal = servicioDependencia.obtenerDependencia(
								investigadorPrincipalTemporal.getDependencia().getSede().getId().toString());
					} else {
						dependenciaResTemporal = investigadorPrincipalTemporal.getDependencia().getFacultad();
					}
				} else {
					dependenciaResTemporal = investigadorPrincipalTemporal.getDependencia().getFacultad();
				}
			} else {
				dependenciaResTemporal = cp.getDependencia();
			}
			if ("1".equals(dependenciaResTemporal.getId())) {
				dependenciaResTemporal = servicioDependencia.obtenerDependencia(
						investigadorPrincipalTemporal.getDependencia().getSede().getId().toString());
			}
			dependenciaResponsable = dependenciaResTemporal;
		}

		// Aqui se verifica si hay parametro para la firma
		return dependenciaResponsable;
	}

	public boolean validarParametroFirma(Dependencia dependencia) {
		if (dependencia != null) {
			List<Parametro> parametrosFirma = this.servicioGeneral.obtenerObjetos(Parametro.class,
					"from Parametro p where p.nombre = '" + Parametro.SEG_FIRMA + "' and p.profesion = '"
							+ dependencia.getId() + "'");
			return parametrosFirma != null && parametrosFirma.size() == 1;
		}
		return false;
	}

	// Definir si convocatoria actual es jornada docente
	public boolean definirJornadaDocente() {
		Long idModalidad = proyectoActual.getModalidad().getId();
		boolean isJornadaDocente = false;
		if (idModalidad >= 10 && idModalidad <= 13) {
			isJornadaDocente = true;
		}
		if (idModalidad >= 17 && idModalidad <= 20) {
			isJornadaDocente = true;
		}
		if (idModalidad == 15) {
			isJornadaDocente = true;
		}
		if (idModalidad == 116) {
			isJornadaDocente = true;
		}
		if ("S".equals(proyectoActual.getEsJornadaDocente())) {
			isJornadaDocente = true;
		}
		return isJornadaDocente;
	}

	public void generarCarta() throws SQLException {
		guardarDatosCarta(true);
	}

	public void guardarDatosCarta() throws SQLException {
		guardarDatosCarta(false);
	}

	// Generar carta segun parametros
	public void guardarDatosCarta(boolean generarCarta) throws SQLException {
		ReporteBirt r = new ReporteBirt();

		mensajeEnvioCarta = "";

		// Definir si es jornada docente
		boolean isJornadaDocente = definirJornadaDocente();

		Long idModalidad = proyectoActual.getModalidad().getId();
		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);

		// Obtener dependencia responsable de las cartas
		InvestigadorInterno investigadorInterno = servicioPersona
				.obtenerInvestigadorInterno(cargarPersonaActual().getId());
		Dependencia dependenciaCartas = cargarDependenciaEnvio(isJornadaDocente, idModalidad, investigadorInterno);
		dependenciaRes = dependenciaCartas.getId();

		// Obtener parametro para firma de las cartas
		idParDepRes = obtenerParametroFirma(dependenciaRes);

		// Resolución para externos
		if (avalId != null && CorreoPlantilla.isEsResolucion(correoActual.getId())) {
			if (esCadenaVacia(ordenadorNombreResolucion)) {
				mensajeEnvioCarta = "Debe ingresar el nombre del ordenador del gasto para "
						+ " generar esta resolución.";
			} else if (esCadenaVacia(numeroResolucion)) {
				mensajeEnvioCarta = "Debe ingresar el número de la resolución para generar este documento.";
			} else {
				r.adicionarParametro("Ordenador", ordenadorNombreResolucion);
				r.adicionarParametro("Num", numeroResolucion);
				r.adicionarParametro("saldoFinanciacion", saldoFinanciacion.toString());

				if (fechaResolucion != null) {
					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
					String DateToStr = format.format(fechaResolucion);
					r.adicionarParametro("Fecha", DateToStr);
				} else {
					r.adicionarParametro("Fecha", "");
				}

				r.adicionarParametro("Ordenador", ordenadorNombreResolucion);
				// Se carga el genero del tipo.
				DominioDetalle dominioDetalleTipoOrdenador = obtenerDominioDetalleLista(ordenadorTipoResolucion,
						ordenadorTipoLista);

				r.adicionarParametro("TipoOrdenador", dominioDetalleTipoOrdenador.getDescripcion());
				r.adicionarParametro("GeneroOrdenador", dominioDetalleTipoOrdenador.getObservacion());

				if (avalId.equals(CorreoPlantilla.RESOLUCION_MODIFICACION.toString())
						|| avalId.equals(CorreoPlantilla.RESOLUCION_EXTERNOS.toString())
						|| avalId.equals(CorreoPlantilla.RESOLUCION_EXTERNOS_CONTRAPARTIDA.toString())
						|| avalId.equals(CorreoPlantilla.FINALIZACION_PRINCIPAL_EXTERNA.toString())) {
					if (!esCadenaVacia(considerando)) {
						r.adicionarParametro("considerando", considerando);
					}
				}

				if (avalId.equals(CorreoPlantilla.RESOLUCION_EXTERNOS_CONTRAPARTIDA.toString())) {
					r.adicionarParametro("Contrapartida", "S");
				}

				if (avalId.equals(CorreoPlantilla.RESOLUCION_EXTERNOS.toString())
						|| avalId.equals(CorreoPlantilla.RESOLUCION_EXTERNOS_CONTRAPARTIDA.toString())
						|| avalId.equals(CorreoPlantilla.FINALIZACION_PRINCIPAL_EXTERNA.toString())) {
					r.adicionarParametro("Facultad", facultadResolucion);
					r.adicionarParametro("articuloDpn",
							ProyectoCarta.obtenerArticuloDependencia(ordenadorTipoResolucion));

				} else if (avalId.equals(CorreoPlantilla.RESOLUCION_MODIFICACION.toString())) {
					r.adicionarParametro("Facultad", dependenciaRes);
					r.adicionarParametro("articuloDpn",
							ProyectoCarta.obtenerArticuloDependencia(ordenadorTipoResolucion));
					r.adicionarParametro("idSol", codigoSolicitud);

					if (otrosi.equals("S")) {
						if (esCadenaVacia(otrosiNum)) {
							mensajeEnvioCarta = "Debe ingresar el número del otrosí del convenio.";
						} else {
							r.adicionarParametro("otrosiNum", otrosiNum);
						}

						if (otrosiFecha == null) {
							mensajeEnvioCarta = "Debe ingresar la fecha del otrosí del convenio.";
						} else {
							SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
							String DateToStr = format.format(otrosiFecha);
							r.adicionarParametro("otrosiFecha", DateToStr);
						}
					} else {
						r.adicionarParametro("otrosiNum", null);
						r.adicionarParametro("otrosiFecha", null);
					}
				}

				r.adicionarParametro("Id", String.valueOf(proyectoActual.getId()));
				if (avalId.equals(CorreoPlantilla.FINALIZACION_PRINCIPAL_EXTERNA.toString())) {
					r.setNombreReporte("/cartas/ResolucionLiquidacion");
				} else {
					r.setNombreReporte(correoActual.getReporte().getRuta());
				}
				r.setFormato(ReporteBirt.FORMATO_PDF);
				sesion.setAttribute("reporte", r);

				FacesContext context = FacesContext.getCurrentInstance();
				r.run(context);
			}

		} else {

			// Obtener ultimo consecutivo de la dependencia
			Long nConsecutivoCartaTemp = servicioGeneral.consultaUltimoIdCarta(dependenciaRes, new Date());

			// Cargar consecutivo en sesion
			sesion.setAttribute("nConsecutivo", nConsecutivoCartaTemp);

			// Agregar parametros que son comunes a todos las cartas
			r.adicionarParametro("idParam", idParDepRes);
			r.adicionarParametro("Dep", dependenciaRes);
			r.adicionarParametro("Seq", String.valueOf(nConsecutivoCartaTemp));
			r.adicionarParametro("Id", String.valueOf(proyectoActual.getId()));

			SimpleDateFormat spd = new SimpleDateFormat("dd");
			SimpleDateFormat spm = new SimpleDateFormat("MM");
			SimpleDateFormat spy = new SimpleDateFormat("yyyy");
			r.adicionarParametro("anioSecuencia", spy.format(new Date()));

			if (fechaSesion != null) {
				fechaS = fechaParametro(Integer.parseInt(spd.format(fechaSesion)),
						Integer.parseInt(spm.format(fechaSesion)), Integer.parseInt(spy.format(fechaSesion)));
			}
			if (Integer.parseInt(avalId) == CorreoPlantilla.PRORROGA) {
				// Carta de prorroga
				if (esCadenaVacia(codigoSolicitud)) {
					mensajeCartas = "Debe seleccionar una solicitud de prorroga aprobada para "
							+ "poder generar esta carta.";
					return;
				} else {
					r.adicionarParametro("IdSol", String.valueOf(codigoSolicitud));
					r.adicionarParametro("justificacion", incluirJustificacionSolicitud);
				}
			} else if (Integer.parseInt(avalId) == CorreoPlantilla.CAMBIO_RUBRO) {
				// Carta de cambio de rubro
				r.adicionarParametro("justificacion", incluirJustificacionSolicitud);
				if (this.fechaSesion != null) {
					r.adicionarParametro("Plazo", fechaS);
				}
				r.adicionarParametro("IdSol", String.valueOf(codigoSolicitud));
			} else if (Integer.parseInt(avalId) == CorreoPlantilla.ADICION_PRESUPUESTAL) {
				r.adicionarParametro("justificacion", incluirJustificacionSolicitud);
				String interno;
				if (proyectoActual.getModalidad().getId().equals(10L)) {
					interno = "N";
				} else {
					interno = "S";
				}
				r.adicionarParametro("Interno", interno);
				if (this.fechaSesion != null) {
					r.adicionarParametro("Plazo", fechaS);
				}
				r.adicionarParametro("IdSol", String.valueOf(codigoSolicitud));
			} else if (Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_PRINCIPAL
					|| Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_ESTUDIANTE
					|| Long.parseLong(avalId) == CorreoPlantilla.FINALIZACION_PRINCIPAL_EXTERNA
					|| Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_SIN_FINANCIACION) {
				if (proyectoActual.getModalidad().getId().equals(10L)
						&& CorreoPlantilla.isEsResolucion(correoActual.getId())) {
					r.setNombreReporte("/cartas/ResolucionLiquidacion");
				} else {
					r.setNombreReporte("/cartas/ActadeFinalizacion");
				}
				// Carta de finalización
				if (fechaSesion != null) {
					r.adicionarParametro("saldoFinanciacion", String.valueOf(saldoFinanciacion));
					r.adicionarParametro("Plazo", fechaS);
				}
				if (Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_ESTUDIANTE) {
					r.adicionarParametro("idEstudiante", cedulaEstudiante);
					r.adicionarParametro("tipo", "Est");
				} else if (Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_PRINCIPAL) {
					r.adicionarParametro("tipo", "Inv");
				} else if (Long.parseLong(avalId) == CorreoPlantilla.FINALIZACION_PRINCIPAL_EXTERNA) {
					r.adicionarParametro("tipo", "InvExt");
					r.adicionarParametro("saldoFinanciacion", String.valueOf(saldoFinanciacion));
				} else if (Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_SIN_FINANCIACION) {
					r.adicionarParametro("tipo", "InvSinFinanc");
				}
			} else if (Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_JOVENES_INVESTIGADORES) {
				r.setNombreReporte("/cartas/ActadeFinalizacionJovenInvestigador");
				r.adicionarParametro("idEstudiante", cedulaEstudiante);
				if (fechaSesion != null) {
					r.adicionarParametro("saldoFinanciacion", String.valueOf(saldoFinanciacion));
					r.adicionarParametro("Plazo", fechaS);
				}

			} else if (Integer.parseInt(avalId) == CorreoPlantilla.CANCELACION_PRINCIPAL
					|| Integer.parseInt(avalId) == CorreoPlantilla.CANCELACION_ESTUDIANTE
					|| Integer.parseInt(avalId) == CorreoPlantilla.SUSPENSION) {
				// Carta de cancelacion y Carta suspension
				r.adicionarParametro("IdSol", String.valueOf(codigoSolicitud));
				r.adicionarParametro("justificacion", incluirJustificacionSolicitud);
				if (fechaSesion != null) {
					r.adicionarParametro("Ejec", String.valueOf(saldoFinanciacion));
					if (Integer.parseInt(avalId) == CorreoPlantilla.CANCELACION_PRINCIPAL
							|| Integer.parseInt(avalId) == CorreoPlantilla.CANCELACION_ESTUDIANTE) {
						r.adicionarParametro("Res", fechaS);
					}
				}
			} else if (Integer.parseInt(avalId) == CorreoPlantilla.CERTIFICADO_MOVILIZACION) {
				// Certificado de movilización
				if (cambiarFirma) {
					if ("".equals(nombreVicerrector.trim())) {
						nombreVicerrector = "Sin nombre";
					}
					if ("".equals(nombreCargoVicerrector.trim())) {
						nombreCargoVicerrector = "Sin nombre";
					}
				} else {
					nombreVicerrector = "N";
					nombreCargoVicerrector = "N";
				}

				Long nConsecutivoCartaTempCertificado = servicioGeneral.consultaUltimoIdPorDependenciaTipoCarta(
						dependenciaRes, new Date(), String.valueOf(TipoCarta.CERTIFICADO_MOVILIZACION));

				List<ProyectoCarta> cartasSinEnviar = servicioGeneral.obtenerObjetosLimitado(ProyectoCarta.class,
						"select #seq pc.seq from ProyectoCarta pc where pc.proyecto.id = '" + proyectoActual.getId()
								+ "' and pc.idSolicitud = '" + codigoSolicitud + "' " + "and pc.estadoCarta.id = '"
								+ EstadoCarta.SIN_ENVIAR + "'");
				if (esListaVacia(cartasSinEnviar)) {
					ProyectoCarta proyectoCarta = new ProyectoCarta();
					proyectoCarta.setProyecto(proyectoActual);
					EstadoCarta ec = new EstadoCarta();
					ec.setId(EstadoCarta.SIN_ENVIAR);
					proyectoCarta.setEstadoCarta(ec);
					proyectoCarta.setFechaGeneracion(getToday());
					proyectoCarta.setIdSolicitud(Long.parseLong(codigoSolicitud));
					Dependencia dep = new Dependencia();
					dep.setId("1096");
					proyectoCarta.setDependencia(dep);
					proyectoCarta.setSede("1096");
					TipoCarta tc = new TipoCarta();
					tc.setId(TipoCarta.CERTIFICADO_MOVILIZACION);
					proyectoCarta.setCarta(tc);
					proyectoCarta.setSubtipo(164L);
					proyectoCarta.setResponsable(getPersonaActual());
					SimpleDateFormat year = new SimpleDateFormat("yyyy");
					proyectoCarta.setAño(year.format(getToday()));
					proyectoCarta.setSeq(nConsecutivoCartaTempCertificado.toString());
					servicioGeneral.guardarObjeto(proyectoCarta);
				} else {
					nConsecutivoCartaTempCertificado = Long.parseLong(cartasSinEnviar.get(0).getSeq());
				}

				// Cargar consecutivo en sesion
				sesion.setAttribute("nConsecutivo", nConsecutivoCartaTempCertificado);

				r.adicionarParametro("Seq", String.valueOf(nConsecutivoCartaTempCertificado));

				r.adicionarParametro("IdSol", String.valueOf(codigoSolicitud));
				r.adicionarParametro("vice", nombreVicerrector);
				r.adicionarParametro("cargo", nombreCargoVicerrector);

				if (proyectoActual.getEsPermisoMarco()) {
					r.setNombreReporte("/cartas/CertificadoMovilizacion2");
				} else {
					r.setNombreReporte("/cartas/CertificadoMovilizacionAsignatura");
				}
			} else if (Integer.parseInt(avalId) == CorreoPlantilla.CAMBIO_INVESTIGADOR_PRINCIPAL) {

				// Carta de cambio de investigador principal
				r.adicionarParametro("IdSol", String.valueOf(codigoSolicitud));
				r.adicionarParametro("justificacion", incluirJustificacionSolicitud);

			} else if (Integer.parseInt(avalId) == CorreoPlantilla.CAMBIO_INTEGRANTES) {

				// Carta de cambio de integrantes
				r.adicionarParametro("IdSol", String.valueOf(codigoSolicitud));
				r.adicionarParametro("justificacion", incluirJustificacionSolicitud);

			} else if (Integer.parseInt(avalId) == CorreoPlantilla.CAMBIO_CONTENIDO) {

				// Carta de cambio de integrantes
				r.adicionarParametro("IdSol", String.valueOf(codigoSolicitud));
				r.adicionarParametro("justificacion", incluirJustificacionSolicitud);

			} else if (Integer.parseInt(avalId) == CorreoPlantilla.REACTIVACION) {

				// Carta de cambio de integrantes
				r.adicionarParametro("IdSol", String.valueOf(codigoSolicitud));
				r.adicionarParametro("justificacion", incluirJustificacionSolicitud);

			} else {
				// Cartas de inicio
				r.adicionarParametro("Sesion", fechaS);
				Calendar c = Calendar.getInstance();
				c.setTime(proyectoActual.getFechaTentativaInicio());
				c.add(Calendar.MONTH, proyectoActual.getDuracion().intValue());
				SimpleDateFormat spd1 = new SimpleDateFormat("dd");
				SimpleDateFormat spm1 = new SimpleDateFormat("MM");
				SimpleDateFormat spy1 = new SimpleDateFormat("yyyy");
				fechaF = fechaParametro(Integer.parseInt(spd1.format(c.getTime())),
						Integer.parseInt(spm1.format(c.getTime())), Integer.parseInt(spy1.format(c.getTime())));
				r.adicionarParametro("FechaFin", fechaF);
				if (Integer.parseInt(avalId) == CorreoPlantilla.INICIO_PRINCIPAL) {
					r.setNombreReporte("/cartas/ActadeInicioInv");
				} else if (Integer.parseInt(avalId) == CorreoPlantilla.INICIO_SIN_FINANCIACION) {
					fechaS = fechaParametro(Integer.parseInt(spd.format(proyectoActual.getFechaAti())),
							Integer.parseInt(spm.format(proyectoActual.getFechaAti())),
							Integer.parseInt(spy.format(proyectoActual.getFechaAti())));
					r.adicionarParametro("Sesion", fechaS);
					r.setNombreReporte("/cartas/ActadeInicioInv1");
					r.adicionarParametro("tipo", "InvSinFinanc");
					r.adicionarParametro("FechaIni", fechaF);
					r.adicionarParametro("EntregaFin", fechaF);
				} else if (Integer.parseInt(avalId) == CorreoPlantilla.INICIO_ESTUDIANTE) {
					if (esCadenaVacia(proyectoActual.getPresentaInformeParcial())
							|| "S".equals(proyectoActual.getPresentaInformeParcial())) {
						r.adicionarParametro("idEstudiante", cedulaEstudiante);
						r.setNombreReporte("/cartas/ActadeInicioInv1");
						r.adicionarParametro("tipo", "Estudiante");
					} else {
						r.adicionarParametro("idEstudiante", cedulaEstudiante);
						r.setNombreReporte("/cartas/ActadeInicioInv1");
						r.adicionarParametro("tipo", "Estudiante1");
					}
				} else if (Integer.parseInt(avalId) == CorreoPlantilla.INICIO_PROYECTO_LABORATORIOS) {
					r.adicionarParametro("FechaIni", fechaF);
					r.adicionarParametro("EntregaPar", fechaF);
					r.adicionarParametro("EntregaFin", fechaF);
				} else if (Integer.parseInt(avalId) == CorreoPlantilla.INICIO_TRADUCCION_ARTICULOS) {
					r.adicionarParametro("FechaIni", fechaF);
				} else if (Integer.parseInt(avalId) == CorreoPlantilla.INICIO_CONVOCATORIA_REPO_EQUIPOS) {
					r.setNombreReporte("/cartas/ActadeInicioInvConvoRepoEquipos");
				}
			}
			mensajeEnvioCarta = "";
			if (generarCarta) {
				if (correoActual.getReporte() != null) {
					r.setNombreReporte(correoActual.getReporte().getRuta());

					if (Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_PRINCIPAL
							|| Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_ESTUDIANTE
							|| Long.parseLong(avalId) == CorreoPlantilla.FINALIZACION_PRINCIPAL_EXTERNA
							|| Integer.parseInt(avalId) == CorreoPlantilla.FINALIZACION_SIN_FINANCIACION) {
						if (proyectoActual.getModalidad().getId().equals(10L)
								&& CorreoPlantilla.isEsResolucion(correoActual.getId())) {
							r.setNombreReporte("/cartas/ResolucionLiquidacion");
						} else {
							r.setNombreReporte("/cartas/ActadeFinalizacion");
						}
					}
				}

				r.setFormato(ReporteBirt.FORMATO_PDF);
				sesion.setAttribute("reporte", r);
				FacesContext context = FacesContext.getCurrentInstance();
				r.run(context);
			} else {
				if (Integer.parseInt(avalId) == CorreoPlantilla.INICIO_PRINCIPAL
						|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_ESTUDIANTE
						|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_GRUPO
						|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_JOVENES_INVESTIGADOR_TUTOR
						|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_JOVENES_INVESTIGADOR_TUTOR_2014
						|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_JOVENES_INVESTIGADORES
						|| Integer.parseInt(avalId) == CorreoPlantilla.INICIO_SEMILLEROS) {
					if (esCadenaVacia(proyectoActual.getActoEntidad()) || proyectoActual.getCuposDescuento().equals(0L)
							|| esCadenaVacia(proyectoActual.getUnidadEjecutora())) {
						mensajeEnvioCarta = "Por favor ingrese los datos correspondientes al acto administrativo de aprobación.";
					}
					r.setNombreReporte("/cartas/ActadeInicioInv1");
					if (Integer.parseInt(avalId) == CorreoPlantilla.INICIO_PRINCIPAL) {
						r.adicionarParametro("tipo", "Inv");

					} else if (Integer.parseInt(avalId) == CorreoPlantilla.INICIO_ESTUDIANTE) {
						r.adicionarParametro("tipo", "Estudiante");
						r.adicionarParametro("idEstudiante", cedulaEstudiante);
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.INICIO_JOVENES_INVESTIGADOR_TUTOR) {
						r.adicionarParametro("tipo", "Tutor");
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.INICIO_JOVENES_INVESTIGADOR_TUTOR_2014) {
						r.adicionarParametro("tipo", "Tutor2014");
						r.setNombreReporte("/cartas/ActadeInicioJovInv");
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.INICIO_JOVENES_INVESTIGADORES) {
						r.adicionarParametro("tipo", "Tutor");
						r.setNombreReporte("/cartas/ActadeInicioJovInv");
					} else if (Integer.parseInt(avalId) == CorreoPlantilla.INICIO_SEMILLEROS) {
						r.adicionarParametro("tipo", "Semillero");
						r.setNombreReporte("/cartas/ActadeInicioJovInv");

					}

				}
			}
		}
	}

	private ProyectoCarta buscarProyectoCartaxTipo(Long tipo) {
		Iterator<ProyectoCarta> i = listaCartasProyecto.iterator();
		while (i.hasNext()) {
			ProyectoCarta proyectoCartaTemporal = i.next();
			if (proyectoCartaTemporal.getSubtipo().equals(tipo)) {
				return proyectoCartaTemporal;
			}
		}
		return null;
	}

	// Obtener fecha correcta segun parametro
	public String fechaParametro(int dia, int mes, int ano) {
		String[] descripcionMes = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto",
				"Septiembre", "Octubre", "Noviembre", "Diciembre" };
		String fechaReporte = String.valueOf(dia) + " de " + descripcionMes[mes - 1] + " de " + String.valueOf(ano);
		return fechaReporte;
	}

	// obtener parametro segun dependencia asi no exista
	public String obtenerParametroFirma(String dependenciaRes) {
		Parametro param;
		String idParDepResTemporal = "";
		if (dependenciaRes == null) {
			List<Parametro> l = cargarListaParametro("-1");
			param = (Parametro) l.get(0);
			idParDepResTemporal = param.getId().toString();
		} else {
			List<Parametro> l2 = cargarListaParametro(dependenciaRes);
			if (!esListaVacia(l2)) {
				param = (Parametro) l2.get(0);
				idParDepResTemporal = param.getId().toString();
			} else {
				l2 = cargarListaParametro("-1");
				if (!esListaVacia(l2)) {
					param = (Parametro) l2.get(0);
					idParDepResTemporal = param.getId().toString();
				}
			}
		}
		return idParDepResTemporal;
	}

	// Obtener persona para firma de cartas de acuerdo a dependencia
	public List<Parametro> cargarListaParametro(String dependencia) {
		String consulta = "select par from Parametro par where " + "par.profesion= '" + dependencia
				+ "' and par.nombre= '" + "SEG_FIRMA'";
		return servicioGeneral.obtenerObjetos(Parametro.class, consulta);
	}

	public void reporteEvaluacion() {
		ReporteBirt r = new ReporteBirt();
		ProyectoEvaluador pe = proyectoEvaluadorSeleccionado;
		r.adicionarParametro("id", pe.getId().toString());
		r.setFormato(ReporteBirt.FORMATO_PDF);
		if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0) {
			r.setNombreReporte("/evaluacion/EvaluacionPrograma");
		} else {
			r.setNombreReporte("/evaluacion/Evaluacion");
		}
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	public void reporteSeleccion() {
		ReporteBirt r = new ReporteBirt();
		ProyectoEvaluador pe = proyectoEvaluadorSeleccionado;
		r.adicionarParametro("id", pe.getId().toString());
		r.setFormato(ReporteBirt.FORMATO_PDF);
		r.setNombreReporte("/evaluacion/ComiteSeleccion");
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	// Consultar resumen evaluación
	public String consultarResumenEvaluacion() {
		ProyectoEvaluador e = proyectoEvaluadorSeleccionado;
		sesion.setAttribute("proyectoEvaluador", e);
		sesion.removeAttribute("manejadorResumenEvaluacion");
		if (e.getEvaluador().getId().getDocumento().equals(ProyectoEvaluador.MESA_TRABAJO_ID)) {
			sesion.setAttribute("lecturaEvaluadorMesa", true);
			sesion.removeAttribute("manejadorEvaluacionMesas");
			return "evaluarProyectoMesa";
		} else
			return "evaluarProyectoResumen";
	}

	// Ingresar a evaluación por parte del coordiandor de evaluación
	public String ingresarEvaluar() {
		ProyectoEvaluador e = proyectoEvaluadorSeleccionado;
		sesion.setAttribute("proyectoEvaluador", e);
		sesion.removeAttribute("manejadorEvaluacion");
		if (e.getEvaluador().getId().getDocumento().equals(ProyectoEvaluador.MESA_TRABAJO_ID)) {
			sesion.setAttribute("lecturaEvaluadorMesa", false);
			sesion.removeAttribute("manejadorEvaluacionMesas");
			return "evaluarProyectoMesa";
		} else {
			sesion.setAttribute("evaluadorCoordinador", new Boolean(true));
			return "evaluarProyecto";
		}
	}

	public String ingresarSeleccionComite() {
		System.out.println("ENTRA A INGRESAR SELECCION COMITE");
		ProyectoEvaluador e = proyectoEvaluadorSeleccionado;
		sesion.setAttribute("proyectoEvaluador", e);
		sesion.removeAttribute("manejadorEvaluacion");
		sesion.removeAttribute("manejadorEvaluacionMesas");
		sesion.removeAttribute("manejadorSeleccionProyectosComite");
		if (e.getEvaluador().getId().getDocumento().equals(ProyectoEvaluador.MESA_TRABAJO_ID)) {
			sesion.setAttribute("seleccionComite", false);
			return "seleccionProyectoComite";
		} else {
			return "";
		}
	}

	// Consultar resumen evaluación
	public String consultarResumenSeleccionComite() {
		ProyectoEvaluador e = proyectoEvaluadorSeleccionado;
		sesion.setAttribute("proyectoEvaluador", e);
		sesion.removeAttribute("manejadorSeleccionProyectosComite");
		if (e.getEvaluador().getId().getDocumento().equals(ProyectoEvaluador.MESA_TRABAJO_ID)) {
			sesion.setAttribute("seleccionComite", true);
			return "seleccionProyectoComite";
		} else
			return "";
	}

	public String getObtenerTipoEvaluacionConvocatoria() {
		String hql = "select #id c.id, #tipoEvaluacionProyectos c.tipoEvaluacionProyectos from Convocatoria c where c.id = "
				+ proyectoActual.getModalidad().getId();
		List<Convocatoria> listaConvocatoria = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class, hql);
		if (listaConvocatoria.get(0).getTipoEvaluacionProyectos() == null) {
			return "E";
		} else {
			return listaConvocatoria.get(0).getTipoEvaluacionProyectos();
		}
	}

	// Ingresar a evaluación por parte del coordiandor de evaluación
	public void devolverEvaluacion() {
		Convocatoria convocatoria = (Convocatoria) proyectoActual.getModalidad();
		if (convocatoria.getFechaFinalEval().after(new Date())) {
			ProyectoEvaluador proyectoEvaluador = servicioProyecto
					.obtenerProyectoEvaluador(proyectoEvaluadorSeleccionado);
			if (proyectoEvaluador != null && proyectoEvaluador.getEstado().equals(EstadoProyecto.SUSPENDIDO)) {
				proyectoEvaluador.setEstado(ProyectoEvaluador.ESTADO_PARCIAL);
				servicioGeneral.guardarObjeto(proyectoEvaluador);
				HistoricoEstadoEvaluacion historicoEstadoEvaluacion = new HistoricoEstadoEvaluacion();
				historicoEstadoEvaluacion.asignarProyectoEvaluador(proyectoEvaluador);
				historicoEstadoEvaluacion.setResponsable(getPersonaActual());
				historicoEstadoEvaluacion.setObservaciones("Devolución por coordinador de evaluación.");
				servicioGeneral.guardarObjeto(historicoEstadoEvaluacion);
				cargarEvaluacion();
			}
		} else {
			errorDevolucion = "La evaluacion no puede ser devuelta para correcciones. Máxima fecha de evaluación: "
					+ convocatoria.getFechaFinalEval().toLocaleString();
		}
	}

	public int getTamañoListaSolicitudes() {
		return proyectoActual.getListaSolicitudesEnviadas().size();
	}

	// Consultar detalle informe
	public String consultarInforme() {
		ProyectoInforme pin = informeSeleccionado;
		super.sesion.setAttribute("idInforme", pin.getId());
		super.sesion.setAttribute("consultaInformeCoordinador", true);
		super.sesion.setAttribute("tipoInformeParam", pin.getTipoInforme().getId());
		super.sesion.setAttribute("tipoAccion", ProyectoInforme.TIPO_ACCION_CONSULTA);
		super.sesion.setAttribute("idProyecto", proyectoActual.getId());
		super.sesion.setAttribute("esCoordinador", new Long("1"));
		super.sesion.setAttribute("esPermisoMarco", proyectoActual.getEsPermisoMarco());
		super.sesion.setAttribute("esPermisoMarcoAsignatura", proyectoActual.getEsPermisoMarcoAsignatura());
		super.sesion.setAttribute("esContratoBiodiversidad", proyectoActual.getEsContratoBiodiversidad());
		if (proyectoActual.getModalidad() != null && proyectoActual.getModalidad().getTipo() != null
				&& proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.BANCO_PROYECTOS)
				&& pin.getTipoInforme().getId().equals(TipoInforme.INFORME_FINAL)) {
			sesion.setAttribute("informeBancoProyectos", true);
			super.sesion.setAttribute("tipoAccionBancoProyectos", new Long("1"));
			return "detalleInformeBancoProyectos";
		} else {
			sesion.setAttribute("informeBancoProyectos", false);
		}
		sesion.removeAttribute(ManejadorRegistroInforme.MANEJADOR_REGISTRO_INFORME_SESSION);
		return "detalleInformeCoordinador";
	}

	public String administrarArchivosInforme() {
		ProyectoInforme pin = informeSeleccionado;
		sesion.setAttribute("informe", pin);
		sesion.setAttribute("proyecto", proyectoActual);
		sesion.removeAttribute("manejadorAdministrarArchivosInforme");
		return "administrarArchivosInforme";
	}

	// Descargar cuadro de resultado de informes
	public void descargarCuadroResultados() {
		ProyectoInforme pin = informeSeleccionado;
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (pin != null && pin.getCuadroResultados() != null) {

			try {
				if (!ctx.getResponseComplete()) {
					HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition", "attachment;filename=\"" + pin.getCuadroNombre() + "\"");
					ServletOutputStream out = response.getOutputStream();
					out.write(pin.getBytesCuadroResultados());
					out.flush();
					ctx.responseComplete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Descargar archivo proyecto
	public void descargarArchivoProyecto() {
		Long id = archivoResumenSeleccionado.getId();
		descargarArchivoProyectoGenerico(id, proyectoActual.getId());
	}

	// Consultar formulario de proyecto
	public String constularProyectoActivo() {
		Long id = proyectoActual.getId();
		Convocatoria conv = servicioModalidad.obtenerConvocatoria(proyectoActual.getModalidad().getId());
		if (conv.isUsarFormularioProyectoEditorial()) {
			sesion.setAttribute("Pro_Editorial_ID", id);
			sesion.removeAttribute("ManejadorEditorialProyectosRegistro");
			return "registroProyectoEditorial";
		}

		Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.LIMPIO_SEGUIMIENTO);
		if (proyectoActual != null) {
			ProyectoVista pv = new ProyectoVista();
			pv.asignarValores(proyectoActual);
			borrarManejadoresInsercionProyecto();
			boolean consultaFichaMinina = true;
			sesion.removeAttribute(ManejadorFichaMinimaHome.MANEJADOR_FICHA_MINIMA_HOME_SESSION);
			sesion.setAttribute("proyectoFichaMinina", pv);
			sesion.setAttribute("consultaFichaMiniºna", consultaFichaMinina);
			return servicioProyecto.consultarProyectoGenerico(proyectoActual, sesion, conv);
		}
		return null;
	}

	// Reporte evaluación proyecto
	public void reporteProyectoEvaluador() {
		Long id = proyectoActual.getId();

		if (((Convocatoria) proyectoActual.getModalidad()).isUsarFormularioProyectoEditorial()) {
			servicioProyecto.imprimirReporteProyectoEditorial(id, sesion, false); // false, no es evaluador
		} else {
			Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.LIMPIO_SEGUIMIENTO);
			servicioProyecto.imprimirReporteProyecto(proyectoActual, sesion, false);
		}
	}

	// Reporte de requisitos de proyectos
	public void reporteRequisitos() {
		Long id = proyectoActual.getId();
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", id.toString());
		r.setFormato(ReporteBirt.FORMATO_PDF);

		r.setNombreReporte("proyecto/ReporteRequisitos");

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	public boolean getExisteProrrogaSinGuardar() {
		Iterator<ProyectoProrroga> i = proyectoActual.getListaProrrogas().iterator();
		while (i.hasNext()) {
			ProyectoProrroga prorroga = i.next();
			if (prorroga != null && prorroga.getId() == null) {
				return true;
			}
		}

		return false;
	}

	// Impresión ficha quipua
	public void reporteFichaQuipu() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", proyectoActual.getId().toString());
		r.setFormato(ReporteBirt.FORMATO_PDF);
		r.setNombreReporte("/quipu/REPORTE_FICHA_QUIPU_CONVOCATORIAS");
		if (proyectoActual.getModalidad().isEsConvocatoriaLegalizacion()) {
			r.adicionarParametro("ext", "S");
		} else {
			r.adicionarParametro("ext", "N");
		}
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	// Habilitar la opción de editar proyecto a investigador principal
	public void permitirEditar() {
		guardarHistoricoEdicionProyecto();
		servicioProyecto.actualizarProyecto(proyectoActual);

		if (tieneAvalRegaliasAprobado && proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO)) {
			CorreoPlantilla correoPlantillaTemporal = cargarPlantilla(CorreoPlantilla.CORREO_EDICION_PROYECTO_REGALIAS);
			Correo correo = new Correo();

			correo.setOrigen(Correo.CORREO_HERMES);
			Persona persona = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
			InvestigadorInterno coordinador = servicioPersona.obtenerInvestigadorInterno(persona.getId());

			correo.adicionarDireccion(persona.getEmail());
			correo.adicionarDireccion(investigadorPrincipal.getEmail());
			correo.setAsunto(correoPlantillaTemporal.getAsunto());
			correo.setAsunto(correo.getAsunto().replaceAll("<<ID>>", proyectoActual.getId().toString()));
			correo.setCuerpo(correoPlantillaTemporal.getCuerpo());
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<ID>>", proyectoActual.getId().toString()));
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<PRY>>", proyectoActual.getNombre()));
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<COORDINADOR>>", persona.getNombreCompleto()));
			if (!esCadenaVacia(coordinador.getTelExtension())) {
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<EXT>>", coordinador.getTelExtension()));
			} else {
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<EXT>>", ""));
			}
			correo.setCuerpo(
					correo.getCuerpo().replaceAll("<<FAC>>", coordinador.getDependencia().getFacultad().getNombre()));
			correo.setCuerpo(
					correo.getCuerpo().replaceAll("<<SED>>", coordinador.getDependencia().getSede().getNombre()));

			Iterator<Aval> i = avalesAsociadosProyecto.iterator();

			while (i.hasNext()) {
				Aval aval = i.next();
				if (aval.isEsAvalAprobadoParaLegalizacion() && aval.isEsConvocatoriaRegalias()) {

					try {

						String hql = "select #ce.entidad, #ce.nombre  from ConvocatoriaExterna ce where CE.id = "
								+ aval.getAviConvocatoria();
						List<ConvocatoriaExterna> listaConvocatoria = servicioGeneral
								.obtenerObjetosLimitado(ConvocatoriaExterna.class, hql);
						if (listaConvocatoria.get(0) != null) {
							correo.setCuerpo(correo.getCuerpo().replaceAll("<<CONVOCATORIA>>",
									listaConvocatoria.get(0).getNombre()));
							correo.setCuerpo(correo.getCuerpo().replaceAll("<<ENTIDAD>>",
									listaConvocatoria.get(0).getEntidad().getDescripcion()));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			servicioCorreo.enviarCorreo(correo);
		}
	}

	// Habilitar la opción de editar proyecto a investigador principal
	public void permitirSegundoAvalRegalias() {
		servicioProyecto.actualizarProyecto(proyectoActual);
	}

	private void guardarHistoricoEdicionProyecto() {
		if (!proyectoActual.getPermitirModificacion().equals(asignacionActual)) {
			HistoricoHabilitacionEdicionProyecto historicoHabilitacionEdicionProyecto = new HistoricoHabilitacionEdicionProyecto(
					proyectoActual, coordinadorSeguimientoProyecto, proyectoActual.getPermitirModificacion());
			servicioGeneral.guardarObjeto(historicoHabilitacionEdicionProyecto);
			asignacionActual = proyectoActual.getPermitirModificacion();
		}
	}

	// Imprimir proyecto asociado a proyecto actual
	public void imprimirProyectoAsociado() {
		Proyecto proyectoActual2 = servicioProyecto.obtenerProyecto(Long.parseLong(proyectoActual.getCodigoDib()),
				ProyectoDAOHibernate.INFORMACION_GENERAL);
		servicioProyecto.imprimirReporteProyecto(proyectoActual2, sesion, false);
	}

	public boolean isCheckProyectoAsociado() {
		try {
			Proyecto proyectoActual2 = servicioProyecto.obtenerProyecto(Long.parseLong(proyectoActual.getCodigoDib()),
					ProyectoDAOHibernate.INFORMACION_GENERAL);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Imprimir aval genérico
	public void imprimirAval() {
		if (avalAsociadoPry != null) {
			servicioAval.imprimirReporteAval(avalAsociadoPry.getAviId(), sesion);
		}
	}

	// Imprimir proyecto asociado al aval
	public void verReporteProyectoAsociadoAval() throws SQLException {
		if (proyectoActual.getAvalAsociado() <= 4855L) {
			ReporteBirt r = new ReporteBirt();
			r.adicionarParametro("Id", avalAsociadoPry.getAviId().toString());
			r.setNombreReporte("/aval/reporteAval_FichaMin");
			r.setFormato(ReporteBirt.FORMATO_PDF);
			sesion.setAttribute("reporte", r);
			FacesContext context = FacesContext.getCurrentInstance();
			try {
				context.getExternalContext().dispatch("/ReporteEngineServlet");
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				context.responseComplete();
			}
		} else {
			Long id = avalAsociadoPry.getIdProyecto();
			Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.LIMPIO_SEGUIMIENTO);
			if (proyectoActual != null) {
				servicioProyecto.imprimirReporteProyecto(proyectoActual, sesion, false);
			}
		}
	}

	public void descargarDocumentoEvaluacion() {
		descargarArchivoGenerico("HER_PROYECTO_EVALUADOR", proyectoEvaluadorSeleccionado.getId().toString(),
				proyectoEvaluadorSeleccionado.getDocumentoEvaluador());
	}

	public void descargarDocumentoSeleccion() {
		descargarArchivoGenerico("HER_PROYECTO_EVALUADOR", proyectoEvaluadorSeleccionado.getId().toString(),
				proyectoEvaluadorSeleccionado.getDocumentoSeleccion());
	}

	public void reporteGastosQUIPU() {
		String codigoQuipuTemporal = proyectoActual.getCodigoQuipu().toString();
		String nombreReporte = "/quipu/ReporteQuipuEjecucionSEDE";
		Investigador inv = proyectoActual.getResponsable();
		ReporteBirt r = new ReporteBirt();

		if (null != inv.getDependencia()) {
			String sedeId = inv.getDependencia().getSede().getId().toString();
			r.adicionarParametro("idSede", sedeId);
			String invId = inv.getId().getDocumento();
			r.adicionarParametro("invID", invId);
		}

		r.adicionarParametro("codigoQuipu", codigoQuipuTemporal);
		r.setFormato(ReporteBirt.FORMATO_PDF);
		r.setNombreReporte(nombreReporte);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			context.responseComplete();
		}
	}

	// Impresión de informe desde listado de informes
	public void imprimirImforme() {
		imprimirInforme(informeSeleccionado);
	}

	// Agregar sede
	public void agregarSede() {
		adicionarDependencia(DependenciaAreaResponsabilidad.SEDE, sedeSel);
	}

	// Agregar facultad legalización
	public void agregarFacultad() {
		adicionarDependencia(DependenciaAreaResponsabilidad.FACULTAD, facInstitutoSel);
	}

	/**
	 * Adicionar dependencia.
	 */
	public void adicionarDependencia(String tipo, String idDependencia) {

		boolean existeDep = false;
		if (!esCadenaVacia(idDependencia)) {
			// Se verifica que no exista.
			if (proyectoActual.getListaDependenciasAreaResponsabilidad().size() > 0) {
				DependenciaAreaResponsabilidad dep = buscarDependenciaAreaResponsabilidad(idDependencia,
						proyectoActual.getListaDependenciasAreaResponsabilidad());
				if (dep != null) {
					existeDep = true;
				}
			}
			if (!existeDep) {
				// Se busca dependencia en listado de dependencias.
				Dependencia dep = buscarDependencia(idDependencia, dependenciasUN);

				if (dep != null) {
					DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad = new DependenciaAreaResponsabilidad();
					dependenciaAreaResponsabilidad.setDependencia(dep);
					dependenciaAreaResponsabilidad.setAreaResponsabilidad(tipo);
					proyectoActual.adicionarDependencia(dependenciaAreaResponsabilidad);

				} else {
					mensajeError("La dependencia seleccionada no se encuentra disponible.");
				}
			} else {
				mensajeError("La dependencia seleccionada ya se encuentra registrada.");
			}
		} else {
			mensajeError("Por favor seleccione la dependencia a registrar.");
		}
	}

	// entidades:
	public void agregarEntidad() {

		if (!esCadenaVacia(tipoEntidadSel) && !esCadenaVacia(entidadSel)) {

			if (valorEntidad == null || (valorEntidad != null && valorEntidad < 0)) {
				mensajeError(uiEntidades, "El valor en efectivo de la entidad debe ser numérico.");
				return;
			}

			if (valorEntidadEsp == null || (valorEntidadEsp != null && valorEntidadEsp < 0)) {
				mensajeError(uiEntidades, "El valor en especie de la entidad debe ser numérico.");
				return;
			}

			List<FuenteFinanciacion> fuentes = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class,
					"select d from FuenteFinanciacion d  where d.id =" + entidadSel);

			FuenteFinanciacion fuente = (FuenteFinanciacion) fuentes.get(0);
			Financiacion fin = new Financiacion();
			fin.setFuente(fuente);
			fin.setValor(valorEntidad);
			fin.setValorEspecie(valorEntidadEsp);
			fin.setTipoEntidad(tipoEntidadSel);
			fin.setEsLegalizacion(Financiacion.SI_ES_LEGALIZACION);

			if (proyectoActual.isExisteEntidadesLegalizacion(fuente.getId())) {
				mensajeError("La entidad ya se encuentra relacionada.");
			} else {
				proyectoActual.adicionarFinanciacion(fin);
				valorEntidad = 0L;
				valorEntidadEsp = 0L;
				tipoEntidadSel = "";
				entidadSel = "";
			}

			cargarListadoEntidadesItem(proyectoActual.getListaEntidadesLegalizacion(), true);

		} else {
			mensajeError(uiEntidades, "Seleccione primero el tipo de entidad y la entidad.");
		}

	}

	private void cargarListadoEntidadesItem(List<Financiacion> financiaciones, boolean convocatoriaExterna) {
		if (financiaciones == null) {
			return;
		}
		entidadDesembolsosItems = new ArrayList<SelectItem>();
		Iterator<Financiacion> i = financiaciones.iterator();
		while (i.hasNext()) {
			Financiacion financiacion = i.next();
			if (!convocatoriaExterna || financiacion.getTipoEntidad().equals(FuenteFinanciacion.FINANCIADORA)
					|| financiacion.getTipoEntidad().equals(FuenteFinanciacion.FINANCIADORA_M)) {
				entidadDesembolsosItems.add(
						new SelectItem(financiacion.getFuente().getId(), financiacion.getFuente().getDescripcion()));
			}
		}
	}

	// ver proyecto padre
	public String consultarProyectoPadre() {

		sesion.removeAttribute("proyectoFichaMinina");

		Long idNewPry = proyectoActual.getProyectoPadre();
		String hql = "select p from Proyecto p where p.id =" + idNewPry;
		List<Proyecto> proyectos = servicioGeneral.obtenerObjetos(Proyecto.class, hql);
		Proyecto proyectoNew = (Proyecto) proyectos.get(0); // pry padre -
															// versión posterior

		sesion.removeAttribute(ManejadorFichaMinimaHome.MANEJADOR_FICHA_MINIMA_HOME_SESSION);
		sesion.setAttribute("proyectoFichaMinimaNueva", proyectoNew);
		sesion.setAttribute("consultaFichaMinina", true);
		sesion.removeAttribute("idConvocatoriaActual");
		sesion.setAttribute("esProyectoFichaMinimaNueva", true);

		return "fichaMinimaHome";
	}

	// ver proyecto hijo
	public String consultarProyectoHijo() {
		sesion.removeAttribute("proyectoFichaMinimaNueva");
		sesion.removeAttribute("proyectoFichaMinina");

		Proyecto proyectoNew = (Proyecto) listaVersiones.get(0); // pry hijo -
																	// versión
																	// original
																	// del
																	// proyecto

		sesion.removeAttribute(ManejadorFichaMinimaHome.MANEJADOR_FICHA_MINIMA_HOME_SESSION);
		sesion.setAttribute("proyectoFichaMinimaNueva", proyectoNew);
		sesion.setAttribute("consultaFichaMinina", true);
		sesion.removeAttribute("idConvocatoriaActual");
		sesion.setAttribute("esProyectoFichaMinimaNueva", true);

		return "fichaMinimaHome";
	}

	// Se guarda cambio de estado de evaluación
	public String guardarConceptoMesaSeleccion() {
		try {
			String v = evaluacionMesaSeleccion.getTipoFinanciacion().getId();
			if (v != null && v.length() > 0) {
				/*
				 * System.out.println("guardando concepto" + v); TipoFinanciacion tc =
				 * (TipoFinanciacion) servicioGeneral.obtenerObjeto(new TipoFinanciacion(), v);
				 * evaluacionMesaSeleccion.setTipoFinanciacion(tc);
				 * evaluacionMesaSeleccion.setCalificaciones(null);
				 * evaluacionMesaSeleccion.setJustificacionBancoFinanciable(
				 * justificacionBancoFinanciable);
				 * servicioGeneral.guardarObjeto(evaluacionMesaSeleccion);
				 */
				// Se cambia el estado del proyecto y se crea un nuevo historico
				EstadoProyecto estadoProyectoActual = new EstadoProyecto();
				if (estadoProyecto.equals(EstadoProyecto.APROBADO)) {
					estadoProyectoActual.setId(EstadoProyecto.APROBADO);
					estadoProyectoActual.setNombre(EstadoProyecto.APROBADO_NOMBRE);
				}
				if (estadoProyecto.equals(EstadoProyecto.ELEGIBLE)) {
					estadoProyectoActual.setId(EstadoProyecto.ELEGIBLE);
					estadoProyectoActual.setNombre(EstadoProyecto.ELEGIBLE_NOMBRE);
				}
				if (estadoProyecto.equals(EstadoProyecto.BANCO_FINANCIABLE)) {
					estadoProyectoActual.setId(EstadoProyecto.BANCO_FINANCIABLE);
					estadoProyectoActual.setNombre(EstadoProyecto.BANCO_FINANCIABLE_NOMBRE);
				}
				if (estadoProyecto.equals(EstadoProyecto.NEGADO)) {
					estadoProyectoActual.setId(EstadoProyecto.NEGADO);
					estadoProyectoActual.setNombre(EstadoProyecto.NO_APROBADO_NOMBRE);
				}

				// Si no hay un cambio de estado, no se actualiza el historico
				if (!proyectoActual.getEstadoProyecto().getId().equals(estadoProyectoActual.getId())) {
					// Historico del proyecto para convertir proyecto a
					// propuesto
					HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
					Date fechaHoy = new Date();
					hepry.setEstadoProyecto(estadoProyectoActual);
					hepry.setFecha(fechaHoy);
					hepry.setResponsable(cargarPersonaActual());
					if (estadoProyecto.equals(EstadoProyecto.BANCO_FINANCIABLE)
							&& justificacionBancoFinanciable.length() < 255) {
						hepry.setJustificacion(justificacionBancoFinanciable);
					}
					proyectoActual.adicionarHistorico(hepry);
					proyectoActual.setEstadoProyecto(estadoProyectoActual);

					if (proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.APROBADO)
							|| proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.BANCO_FINANCIABLE)) {
						proyectoActual.setMontoInicialAprobado(montoAprobadoProyecto);

					}
					if (proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.APROBADO)) {
						if ("Si".equals(proyectoActual.getTieneBiodiversidad())) {
							enviarCorreoCoordinadorBiodiversidad(414, proyectoActual);
						}
					}

					servicioGeneral.guardarObjeto(proyectoActual);

					// Notificacion labs asociados
					if (estadoProyecto.equals(EstadoProyecto.APROBADO) && !proyectoActual.getLaboratorios().isEmpty()) {
						for (Laboratorio lab : (ArrayList<Laboratorio>) proyectoActual.getListaLaboratorios()) {
							enviarCorreoLaboratoriosProyecto(lab, proyectoActual,
									CorreoPlantilla.CORREO_NOTIFICACION_ASOCIACION_LAB_PROYECTO_APROBADO);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// Las condiciones para que este método pueda ser ejecudado son:
	// -EL proyecto debe estar en estado elegible o no cumplió requisitos.
	// -Debe haber un proyecto en proyectoActual
	// -Debe haber una persona guardada en sesión
	public void devolverPorReclamacion() {
		String estadoId = "P";
		EstadoProyecto estadoProyectoTemporal = new EstadoProyecto();
		estadoProyectoTemporal.setId(estadoId);
		estadoProyectoTemporal.setNombre("Propuesto");
		HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
		hepry.setEstadoProyecto(estadoProyectoTemporal);
		hepry.setFecha(new Date());
		hepry.setResponsable(cargarPersonaActual());
		hepry.setJustificacion("Proceso de reclamación");
		hepry.setProyecto(proyectoActual);
		servicioGeneral.guardarObjeto(hepry);
		proyectoActual.setEstadoProyecto(estadoProyectoTemporal);
		servicioGeneral.guardarObjeto(proyectoActual);
		cargarProyectos();
	}

	// respuesta reclamacion
	public void enviarRespuestaReclamacion() {
		proyectoActual.setFechaRespuestaReclamacionReq(new Date());
		servicioGeneral.guardarObjeto(proyectoActual);

		Investigador ipal = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());

		String dirCorreoConfirmacion = ipal.getEmail();
		if (!esCadenaVacia(dirCorreoConfirmacion)) {
			correoActual = cargarPlantilla(267);
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			correo.adicionarDireccion(dirCorreoConfirmacion);
			// correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
			correo.setAsunto(correoActual.getAsunto().replace("<<ID_PROYECTO>>", proyectoActual.getId().toString()));
			String cuerpoCorreoTemporal = correoActual.getCuerpo();

			cuerpoCorreoTemporal = cuerpoCorreoTemporal.replace("<<NOMBRE_PROYECTO>>", proyectoActual.getNombre());
			cuerpoCorreoTemporal = cuerpoCorreoTemporal.replace("<<ID_PROYECTO>>", proyectoActual.getId().toString());
			cuerpoCorreoTemporal = cuerpoCorreoTemporal.replace("<<RECLAMACION_PROYECTO>>",
					proyectoActual.getRespuestaReclamacion());

			System.out.println(cuerpoCorreoTemporal);

			correo.setCuerpo(cuerpoCorreoTemporal);
			servicioCorreo.enviarCorreo(correo);

			esRespuestaReclamacion = true;

			FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La respuesta a la reclamación se ha registrado correctamente en el sistema.", ""));
		} else {

			FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La respuesta a la reclamación se ha registrado correctamente en el sistema.", ""));

			FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_WARN,
					"El investigador principal del proyecto no tiene el correo electrónico registrado en el sistema, por favor comunicarse con él directamente",
					""));
		}
	}

	// respuesta reclamacion
	public void enviarRespuestaReclamacionEvaluacion() {

		if (!esCadenaVacia(proyectoActual.getEstadoReclamacionEvaluacion())) {
			proyectoActual.setFechaRespuestaReclamacionEva(new Date());
			proyectoActual.setRespuestaReclamacionEvaluacion(
					controlTamanoCadena(proyectoActual.getRespuestaReclamacionEvaluacion(), 4000));
			servicioGeneral.guardarObjeto(proyectoActual);

			Investigador ipal = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());

			String dirCorreoConfirmacion = ipal.getEmail();
			if (!esCadenaVacia(dirCorreoConfirmacion)) {
				correoActual = cargarPlantilla(283);
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				correo.adicionarDireccion(dirCorreoConfirmacion);
				// correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
				correo.setAsunto(
						correoActual.getAsunto().replace("<<ID_PROYECTO>>", proyectoActual.getId().toString()));
				String cuerpoCorreoTemporal = correoActual.getCuerpo();

				cuerpoCorreoTemporal = cuerpoCorreoTemporal.replace("<<NOMBRE_PROYECTO>>", proyectoActual.getNombre());
				cuerpoCorreoTemporal = cuerpoCorreoTemporal.replace("<<ID_PROYECTO>>",
						proyectoActual.getId().toString());
				cuerpoCorreoTemporal = cuerpoCorreoTemporal.replace("<<RECLAMACION_PROYECTO>>",
						proyectoActual.getRespuestaReclamacionEvaluacion());

				System.out.println(cuerpoCorreoTemporal);

				correo.setCuerpo(cuerpoCorreoTemporal);
				servicioCorreo.enviarCorreo(correo);

				esRespuestaReclamacion = true;

				FacesContext.getCurrentInstance().addMessage("msgsEva", new FacesMessage(FacesMessage.SEVERITY_INFO,
						"La respuesta a la reclamación se ha registrado correctamente en el sistema.", ""));
			} else {

				FacesContext.getCurrentInstance().addMessage("msgsEva", new FacesMessage(FacesMessage.SEVERITY_INFO,
						"La respuesta a la reclamación se ha registrado correctamente en el sistema.", ""));

				FacesContext.getCurrentInstance().addMessage("msgsEva", new FacesMessage(FacesMessage.SEVERITY_WARN,
						"El investigador principal del proyecto no tiene el correo electrónico registrado en el sistema, por favor comunicarse con él directamente",
						""));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage("msgsEva", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Por favor seleccione el estado de la aclaración o reclamación.", ""));
		}

	}

	public void reporteReclamacionRequisitos() {
		Long id = proyectoActual.getId();
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", id.toString());
		r.setFormato(ReporteBirt.FORMATO_PDF);

		r.setNombreReporte("proyecto/ReporteReclamaciones");

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	public void reporteReclamacionEvaluacion() {
		Long id = proyectoActual.getId();
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", id.toString());
		r.setFormato(ReporteBirt.FORMATO_PDF);

		r.setNombreReporte("proyecto/ReporteReclamacionesEvaluacion");

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	// Las condiciones para que este método pueda ser ejecudado son:
	public void borrarFechaInicio() {
		proyectoActual.setFechaTentativaInicio(null);
		servicioGeneral.guardarObjeto(proyectoActual);
		if (!proyectoActual.getModalidad().isEsConvocatoriaLegalizacion()) {
			habilitarModificacionesLegalizacion();
		}
		HistoricoBorrarFecha historicoBorrarFecha = new HistoricoBorrarFecha();
		historicoBorrarFecha.setProyecto(proyectoActual);
		historicoBorrarFecha.setFechaBorrada(new Date());
		historicoBorrarFecha.setResponsable(cargarPersonaActual());
		historicoBorrarFecha.setFecha(new Date());
		servicioGeneral.guardarObjeto(historicoBorrarFecha);
		cargarProyectos();
	}

	// Cargar evaluaciones del proyecto
	private void cargarEvaluacion() {
		try {
			listaProyectoEvaluador = new ArrayList<ProyectoEvaluador>();
			listaProyectoSeleccion = new ArrayList<ProyectoEvaluador>();
			List<ProyectoEvaluador> listaProyectoEvaluadorTemp = servicioProyecto
					.obtenerProyectosEvaluador(proyectoActual.getId());
			boolean mesaTrabajo = false;
			if (listaProyectoEvaluadorTemp != null) {
				for (int i = 0; i < listaProyectoEvaluadorTemp.size(); i++) {
					ProyectoEvaluador pe = (ProyectoEvaluador) listaProyectoEvaluadorTemp.get(i);
					if (pe.getEvaluador().getId().getDocumento().equals(ProyectoEvaluador.MESA_TRABAJO_ID)) {
						evaluacionMesaSeleccion = pe;
						mesaTrabajo = true;
						break;
					}
				}

			}
			// Si el proyecto no ha sido evaluado por la mesa de
			// trabajo, se
			// guarda la respectiva evaluacion asociada
			if (!mesaTrabajo) {
				IdPersona idEvaluador = new IdPersona();
				idEvaluador.setDocumento(ProyectoEvaluador.MESA_TRABAJO_ID);
				idEvaluador.setTipoDocumento("C");
				Investigador e = servicioPersona.obtenerInvestigador(idEvaluador);
				ProyectoEvaluador evaluacionMesaTrabajo = new ProyectoEvaluador();
				evaluacionMesaTrabajo.setEvaluador(e);
				evaluacionMesaTrabajo.setProyecto(proyectoActual);
				servicioProyecto.guardarEvaluacionProyecto(evaluacionMesaTrabajo);
				evaluacionMesaSeleccion = evaluacionMesaTrabajo;
				listaProyectoEvaluadorTemp = servicioProyecto.obtenerProyectosEvaluador(proyectoActual.getId());
			}
			// Se carga el conceto de la mesa de trabajo que es donde se guarda
			// si el proyecto se aprueba o no.
			if (evaluacionMesaSeleccion.getTipoFinanciacion() == null
					|| evaluacionMesaSeleccion.getTipoFinanciacion().getId() == null) {
				TipoFinanciacion tc = (TipoFinanciacion) servicioGeneral.obtenerObjeto(new TipoFinanciacion(), "N");
				evaluacionMesaSeleccion.setTipoFinanciacion(tc);
			}

			for (ProyectoEvaluador pe : listaProyectoEvaluadorTemp) {
				Convocatoria convocatoria = (Convocatoria) proyectoActual.getModalidad();
				if (convocatoria.isMostrarEvaluacionesIndividuales() || this.esCoordinadorEvaluacion) {
					listaProyectoEvaluador.add(pe);
				} else if (pe.getEvaluador().getId().getDocumento().equals(ProyectoEvaluador.COMITE_SELECCION_ID)
						|| pe.getEvaluador().getId().getDocumento().equals(ProyectoEvaluador.MESA_TRABAJO_ID)) {
					listaProyectoEvaluador.add(pe);
				}
				if (pe.getEvaluador().getId().getDocumento().equals(ProyectoEvaluador.COMITE_SELECCION_ID)
						|| pe.getEvaluador().getId().getDocumento().equals(ProyectoEvaluador.MESA_TRABAJO_ID)) {
					listaProyectoSeleccion.add(pe);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Descargar documento de solicitud
	public void descargarDocumento() {
		SolicitudDocumento solDoc = documentoSeleccionado;
		descargarArchivoSolicitudGenerico(solDoc);
	}

	public String getCargarProyecto() {
		cargarProyectos();
		return "";
	}

	public void agregarObservacion() {
		if (!esCadenaVacia(nuevaObservacion)) {
			ObservacionSeguimiento observacionSeguimiento = new ObservacionSeguimiento();
			observacionSeguimiento.setDescripcion(nuevaObservacion);
			observacionSeguimiento.setProyecto(proyectoActual);
			observacionSeguimiento.setFecha(new Date());
			observacionSeguimiento.setResponsable(personaActual);
			observacionSeguimiento.setEstado(ObservacionSeguimiento.REGISTRADO);
			observacionSeguimiento.setTipo(tipoObservacion);
			servicioGeneral.guardarObjeto(observacionSeguimiento);
			if ("M".equals(tipoObservacion) && observacionSeguimiento.getId() != null) {
				CorreoPlantilla correoPlantillaTemporal = cargarPlantilla(248);
				String cuerpoCorreo = editarCorreo(investigadorPrincipal, correoPlantillaTemporal, nuevaObservacion,
						proyectoActual);
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				Persona persona = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
				correo.adicionarDireccion(persona.getEmail());
				correo.adicionarDireccion(investigadorPrincipal.getEmail());
				// correo.adicionarDireccion(Correo.CORREO_HERMES);
				correo.setAsunto(correoPlantillaTemporal.getAsunto());
				correo.setCuerpo(cuerpoCorreo);
				servicioCorreo.enviarCorreo(correo);
			}
			cargarObservacionesSeguimiento(proyectoActual.getId());
			nuevaObservacion = "";
		}
	}

	public String editarCorreo(Persona personaAux, CorreoPlantilla correo, String observacion, Proyecto proyecto) {
		try {
			String correoAux = correo.getCuerpo();
			String investigador = personaAux.getNombreCompleto();
			correoAux = correoAux.replaceAll("<<INVESTIGADOR>>", investigador);
			correo.setAsunto(correo.getAsunto().replaceAll("<<PRY_ID>>", proyecto.getId().toString()));
			correoAux = correoAux.replaceAll("<<PRY_ID>>", proyecto.getId().toString());
			String[] parts = correoAux.split("<<COMENTARIOS>>");
			if (parts.length > 1) {
				String concepto;
				if (esCadenaVacia(observacion)) {
					concepto = "No se ingresaron comentarios.";
				} else {
					concepto = observacion;
				}
				String part1 = parts[0];
				String part2 = parts[1];
				correoAux = part1 + concepto + part2;
			}
			return correoAux;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "";
	}

	public void eliminarObservacion() {
		observacionSeguimientoSeleccionada.setEstado(ObservacionSeguimiento.BORRADO);
		observacionSeguimientoSeleccionada.setResponsableEliminacion(personaActual);
		observacionSeguimientoSeleccionada.setFechaEliminacion(new Date());
		servicioGeneral.guardarObjeto(observacionSeguimientoSeleccionada);
		cargarObservacionesSeguimiento(proyectoActual.getId());
		observacionSeguimientoSeleccionada = null;
	}

	/**
	 * Metodo para eliminar obligaciones del convenio actual.
	 */
	public void eliminarObligacion() {
		convenioActual.getObligaciones().remove(obligacionSeleccionada);
	}

	/**
	 * Actualización fecha de finalización de proyecto.
	 */
	public void cambiarFechas() {
		if (proyectoActual.getFechaTentativaInicio() != null) {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(proyectoActual.getFechaTentativaInicio());
			gregorianCalendar.add(GregorianCalendar.MONTH, proyectoActual.getDuracion().intValue());
			proyectoActual.setFechaFinalizacion(gregorianCalendar.getTime());
		}
	}

	// Duplicar proyecto para legalización
	public void crearVersionProyecto() {

		// Crear copia del proyecto con el procedimiento BD
		personaActual = cargarPersonaActual();

		try {
			if (!tieneVersiones
					|| (proyectoActual.getProyectoPadre() != null && !proyectoActual.getProyectoPadre().equals(0L))) {

				// Nuevo Proyecto
				Proyecto pryDuplicado = proyectoActual.crearVersionBase();
				pryDuplicado.setCreadorId(personaActual.getId().getDocumento());
				pryDuplicado.setCreadorDocumento(personaActual.getId().getTipoDocumento());

				// Actualizar datos proyecto actual
				proyectoActual.cambiarEstadoPersona(EstadoProyecto.EN_LEGALIZACION, personaActual,
						"Generación de copia proyecto para legalizar");
				proyectoActual.setProyectoPadre(0L);
				proyectoActual.setModalidad(servicioModalidad.obtenerModalidad(10L));
				servicioGeneral.guardarObjeto(proyectoActual);

				// Guardar histórico pry duplicado
				HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
				Date fechaHoy = new Date();
				hepry.setEstadoProyecto(pryDuplicado.getEstadoProyecto());
				hepry.setFecha(fechaHoy);
				hepry.setResponsable(cargarPersonaActual());
				hepry.setJustificacion("Creación de version en legalización.");
				pryDuplicado.adicionarHistorico(hepry);
				servicioGeneral.guardarObjeto(pryDuplicado);

				// Investigadores
				List<InvestigadorProyecto> listaInvestigadores = proyectoActual.getListaInvestigadoresProyecto();
				for (int i = 0; i < listaInvestigadores.size(); i++) {
					InvestigadorProyecto ip = (InvestigadorProyecto) listaInvestigadores.get(i);
					InvestigadorProyecto ipNew = ip.crearCopia();
					ipNew.setProyecto(pryDuplicado);
					pryDuplicado.adicionarInvestigadorProyecto(ipNew);
				}

				// ResultadoProyecto
				List<ResultadoProyecto> listaResultados = servicioGeneral.obtenerObjetos(ResultadoProyecto.class,
						"select p from ResultadoProyecto p where p.proyecto.id = '" + proyectoActual.getId() + "'");
				if (!esListaVacia(listaResultados)) {
					for (int i = 0; i < listaResultados.size(); i++) {
						ResultadoProyecto resultadoProyecto = (ResultadoProyecto) listaResultados.get(i);
						resultadoProyecto.setProyecto(pryDuplicado);
						resultadoProyecto.setId(null);
						pryDuplicado.adicionarResultado(resultadoProyecto);
					}
				}

				// Objetivos especificos
				List<ObjetivoEspecifico> listaObjetivosEspecificos = servicioProyecto
						.obtenerObjetivosEspeficicos(proyectoActual);
				if (!esListaVacia(listaObjetivosEspecificos)) {
					for (int i = 0; i < listaObjetivosEspecificos.size(); i++) {
						ObjetivoEspecifico objetivoEspecifico = (ObjetivoEspecifico) listaObjetivosEspecificos.get(i);
						objetivoEspecifico.setProyecto(pryDuplicado);
						objetivoEspecifico.setId(null);
						objetivoEspecifico.setMetasProyecto(null);
						pryDuplicado.adicionarObjetivoEspecifico(objetivoEspecifico);
					}
				}

				// ProyectoProducto
				List<ProyectoProducto> lista1Productos = servicioGeneral.obtenerObjetos(ProyectoProducto.class,
						"select p from ProyectoProducto p where p.proyecto.id = '" + proyectoActual.getId() + "'");
				if (!esListaVacia(lista1Productos)) {
					for (int i = 0; i < lista1Productos.size(); i++) {
						ProyectoProducto proyectoProducto = (ProyectoProducto) lista1Productos.get(i);
						proyectoProducto.setProyecto(pryDuplicado);
						proyectoProducto.setId(null);
						pryDuplicado.adicionarProductoProyecto(proyectoProducto);
					}
				}

				// DependenciaAreaResponsabilidad
				List<DependenciaAreaResponsabilidad> listaDependencias = servicioGeneral.obtenerObjetos(
						DependenciaAreaResponsabilidad.class,
						"select p from DependenciaAreaResponsabilidad p where p.proyecto.id = '"
								+ proyectoActual.getId() + "'");
				if (!esListaVacia(listaDependencias)) {
					for (int i = 0; i < listaDependencias.size(); i++) {
						DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad = (DependenciaAreaResponsabilidad) listaDependencias
								.get(i);
						dependenciaAreaResponsabilidad.setProyecto(pryDuplicado);
						dependenciaAreaResponsabilidad.setId(null);
						pryDuplicado.adicionarDependencia(dependenciaAreaResponsabilidad);
					}
				}

				// Areas Tematicas
				List<AreaTematica> lista = servicioProyecto.obtenerAreasTematicas(proyectoActual);
				if (!esListaVacia(lista)) {
					for (int i = 0; i < lista.size(); i++) {
						AreaTematica areaTematica = (AreaTematica) lista.get(i);
						pryDuplicado.adicionarAreaTematica(areaTematica);
					}
				}

				Proyecto pryActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(),
						ProyectoDAOHibernate.TODO_POR_ID, true); // Incluye gastos

				// Financiacion
				List<Financiacion> listaFinanciaciones = pryActual.getListaFinanciones();
				if (!esListaVacia(listaFinanciaciones)) {
					for (int i = 0; i < listaFinanciaciones.size(); i++) {

						Financiacion fin = (Financiacion) listaFinanciaciones.get(i);

						Financiacion finNueva = fin.crearCopia();
						finNueva.setProyecto(pryDuplicado);

						List<Gasto> listaGastos = finNueva.getListaGastos();
						if (!esListaVacia(listaGastos)) {
							for (int l = 0; l < listaGastos.size(); l++) {
								Gasto g = (Gasto) listaGastos.get(l);
								Gasto gas = g.crearCopia();
								gas.setFinanciacion(finNueva);
								finNueva.adicionarGasto(gas);
							}
						}

						pryDuplicado.adicionarFinanciacion(finNueva);
					}
				}

				// palabrasClaves
				List<PalabraClave> listaPal = pryActual.getListaPalabrasES();
				if (!esListaVacia(listaPal)) {
					for (int i = 0; i < listaPal.size(); i++) {
						PalabraClave palabraClave = (PalabraClave) listaPal.get(i);
						pryDuplicado.adicionarPalabraClave(palabraClave);
					}
				}

				// OCDE
				// cargar area ciencia principal
				List<AreaTematica> listaAreasPrimSec = servicioGeneral.obtenerObjetos(AreaTematica.class,
						"select e from AreaTematica e where e.proyecto.id = " + proyectoActual.getId()
								+ "and e.tipo = 1");
				if (!esListaVacia(listaAreasPrimSec)) {
					AreaTematica at = (AreaTematica) listaAreasPrimSec.get(0);
					AreaTematica atNew = new AreaTematica();
					atNew.setProyecto(pryDuplicado);
					atNew.setTipo(at.getTipo());

					List<DominioDetalle> listaAreasPrim = servicioGeneral.obtenerObjetos(DominioDetalle.class,
							"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and dd.identificador.tipo ='"
									+ at.getProyectoAreaTematica().getIdentificador().getTipo() + "'");

					if (!esListaVacia(listaAreasPrimSec)) {
						DominioDetalle dd = (DominioDetalle) listaAreasPrim.get(0);
						atNew.setProyectoAreaTematica(dd);
						servicioGeneral.guardarObjeto(atNew);
					}
				}

				// cargar areas ciencia secundarias
				List<AreaTematica> listaAreasSec = servicioGeneral.obtenerObjetos(AreaTematica.class,
						"select e from AreaTematica e where e.proyecto.id = " + proyectoActual.getId()
								+ "and e.tipo = 2");

				if (!esListaVacia(listaAreasSec)) {
					AreaTematica at = (AreaTematica) listaAreasSec.get(0);

					AreaTematica atNew = new AreaTematica();
					atNew.setProyecto(pryDuplicado);
					atNew.setTipo(at.getTipo());

					List<DominioDetalle> listaAreasPrim = servicioGeneral.obtenerObjetos(DominioDetalle.class,
							"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and dd.identificador.tipo ='"
									+ at.getProyectoAreaTematica().getIdentificador().getTipo() + "'");

					if (!esListaVacia(listaAreasPrim)) {
						for (int j = 0; j < listaAreasPrim.size(); j++) {
							DominioDetalle dd = (DominioDetalle) listaAreasPrim.get(j);
							atNew.setProyectoAreaTematica(dd);
							servicioGeneral.guardarObjeto(atNew);
						}

					}
				}

				// Guardar
				servicioGeneral.guardarObjeto(pryDuplicado);

				// Enviar correo al docente - notificacion de pry duplicado
				if (enviarCorreoDocente()) {
					mensajeInfo(btnCopiaProyecto,
							"Ha sido generada la copia de la versión aprobada del proyecto y se ha notificado al docente.");
					revisarTieneVersiones();
				} else {
					mensajeInfo(btnCopiaProyecto,
							"Ha sido generada la copia de la versión aprobada del proyecto sin notificación al docente.");
					revisarTieneVersiones();
				}
			} else { // si tiene versiones
				mensajeInfo(btnCopiaProyecto,
						"No es posible generar la copia del proyecto. Este ya tiene una copia asociada o es la copia de un proyecto aprobado.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mensajeError(btnCopiaProyecto, "NO ha sido posible generar la copia del proyecto.");
		}
	}

	protected void mostrarMensaje(FacesMessage msg, UIComponent component) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (component == null) {
			context.addMessage(null, msg);
		} else {
			context.addMessage(component.getClientId(context), msg);
		}

	}

	public void cambiarEstado() {
		System.out.println(estadoProyecto);
	}

	// lmom
	public boolean enviarCorreoDocente() {
		boolean band = true;

		// Correo a Hermes
		Correo correo = new Correo();
		CorreoPlantilla cp = cargarPlantilla(nPlantillaDocente);
		correo.setOrigen(Correo.CORREO_HERMES);
		correo.setAsunto(cp.getAsunto());
		correo.setCuerpo(cp.getCuerpo().replaceAll("<<ID>>", proyectoActual.getId().toString()));
		correo.setCuerpo(correo.getCuerpo().replaceAll("<<NOMBRE>>", proyectoActual.getNombre().toString()));

		// docente
		Investigador investigador = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
		if (investigador != null) {
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<USUARIO>>",
					investigador.getNombre1() + " " + investigador.getApellido1()));

			if (investigador.getEmail() != null) {
				correo.adicionarDireccion(investigador.getEmail());
			}

			// Coordinador
			if (personaActual.getEmail() != null) {
				correo.adicionarDireccion(personaActual.getEmail());
			}
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<COORDINADOR>>",
					personaActual.getNombre1() + " " + personaActual.getApellido1()));

			// ver correo
			System.out
					.println("Correo al docente ******** " + investigador.getEmail() + " ****** " + correo.getCuerpo());

			// enviar correo
			// correo.adicionarDireccion(Correo.CORREO_HERMES);
			servicioCorreo.enviarCorreo(correo);

		} else {
			band = false;
		}

		return band;

	}

	// lmom
	public void imprimirProyectoHijo() {

		Long id = idPryConsulta;

		// imprimir hijo
		Proyecto proyectoActual = servicioProyecto.obtenerProyecto(id, ProyectoDAOHibernate.LIMPIO_SEGUIMIENTO);
		if (proyectoActual != null) {
			servicioProyecto.imprimirReporteProyecto(proyectoActual, sesion, false);
		}

	}

	// lmom
	public String consultarFichaMinimaInicial() {
		sesion.removeAttribute("consultaFichaMinina");
		sesion.removeAttribute("esProyectoFichaMinimaNueva");
		sesion.removeAttribute("proyectoFichaMinimaNueva");
		sesion.removeAttribute("proyectoFichaMinina");
		Long id = idPryConsulta;
		// obtener hijo
		String hql = "select p from Proyecto p where p.id =" + id;
		List proyectos = servicioGeneral.obtenerObjetos(hql);
		Proyecto proyectoNew = (Proyecto) proyectos.get(0); // Pry hijo

		// Consultar hijo
		sesion.removeAttribute(ManejadorFichaMinimaHome.MANEJADOR_FICHA_MINIMA_HOME_SESSION);
		sesion.setAttribute("proyectoFichaMinimaNueva", proyectoNew);
		sesion.setAttribute("consultaFichaMinina", true);
		sesion.setAttribute("esProyectoFichaMinimaNueva", true);
		sesion.removeAttribute("idConvocatoriaActual");
		return "consultarFichaMinimaHome";

	}

	// lmom
	public void validarCodigoExt() {

		try {
			Long codigo = this.convenioActual.getCodEntidadExterna();
			if (codigo == null || codigo.equals("")) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"El código externo del proyecto es obligatorio.",
						"El código externo del proyecto es obligatorio.");
				mostrarMensaje(message, codExterna);
			} else if (codigo <= 0L) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"El código externo del proyecto debe ser numérico y mayor a cero.",
						"El código externo del proyecto debe ser numérico y mayor a cero.");
				mostrarMensaje(message, codExterna);
			}

		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El código externo del proyecto debe ser numérico.",
					"El código externo del proyecto debe ser numérico.");
			mostrarMensaje(message, codExterna);
		}

	}

	public String examinarInformacion() {
		sesion.removeAttribute("idProyectoSeguimientoCoordinador");
		AlertaProyecto alertaProyecto = alertaSeleccionada;

		proyectoActual = alertaProyecto.getProyecto();
		proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(),
				ProyectoDAOHibernate.LIMPIO_SEGUIMIENTO);

		if (!alertaProyecto.getEstado().equals(AlertaProyecto.EN_PROCESO)) {
			alertaProyecto.setEstado("E");
			if ((proyectoActual.getEsPermisoMarco() || proyectoActual.getEsPermisoMarcoAsignatura()) && (alertaProyecto
					.getSolicitud().getTipoSolicitud().getId() == TipoSolicitud.INFORME_AVANCE
					|| alertaProyecto.getSolicitud().getTipoSolicitud().getId() == TipoSolicitud.INFORME_FINAL)) {
				alertaProyecto.setEstado(AlertaProyecto.EN_PROCESO_ANLA);
			}
			this.servicioGeneral.guardarObjeto(alertaProyecto);
		}

		if (alertaProyecto.getSolicitud() != null) {

			if (alertaProyecto.getSolicitud().getTipoSolicitud().getId() == TipoSolicitud.INFORME_AVANCE
					|| alertaProyecto.getSolicitud().getTipoSolicitud().getId() == TipoSolicitud.INFORME_FINAL) {

				List<SolicitudInforme> listaSolicitudInforme = servicioGeneral.obtenerObjetos(SolicitudInforme.class,
						"from SolicitudInforme  where solicitudID ='" + alertaProyecto.getSolicitud().getId() + "'");

				if (!esListaVacia(listaSolicitudInforme)) {

					SolicitudInforme solicitudInforme = listaSolicitudInforme.get(0);

					List<ProyectoInforme> listaInformesTemporal = servicioGeneral.obtenerObjetos(ProyectoInforme.class,
							"from ProyectoInforme  where id ='" + solicitudInforme.getInformeID() + "'");

					if (!esListaVacia(listaInformesTemporal)) {
						ProyectoInforme pin = (ProyectoInforme) listaInformesTemporal.get(0);

						sesion.setAttribute("tipoInformeParam", pin.getTipoInforme().getId());
						sesion.setAttribute("idProyecto", pin.getProyecto().getId());
						sesion.setAttribute("idInforme", pin.getId());
						sesion.setAttribute("tipoAccion", ProyectoInforme.TIPO_ACCION_CONSULTA);
						sesion.setAttribute("tipoAccionBancoProyectos", ProyectoInforme.TIPO_ACCION_CONSULTA);
						sesion.setAttribute("esVIF", new Long("0"));
						sesion.setAttribute("solicitudRenovacion", false);
						sesion.setAttribute("idAlertaInforme", alertaProyecto.getId());
						sesion.setAttribute("esPermisoMarco", proyectoActual.getEsPermisoMarco());
						sesion.setAttribute("esPermisoMarcoAsignatura", proyectoActual.getEsPermisoMarcoAsignatura());
						sesion.setAttribute("esContratoBiodiversidad", proyectoActual.getEsContratoBiodiversidad());
						sesion.removeAttribute("proyectoRenovacion");
						sesion.setAttribute("revisionInformes", "C");
						sesion.removeAttribute("manejadorAvalInformeFacultad");
						sesion.removeAttribute("manejadorAvalInformeUab");
						sesion.removeAttribute("manejadorAvalInformeUab");
						agregarReglaNavegacion("detalleInforme", "ManejadorPrincipalInforme");

						super.sesion.setAttribute("anteriorManejador", "ManejadorAvalInformeFacultad");

						if (pin.getProyecto() != null && pin.getProyecto().getModalidad() != null
								&& pin.getProyecto().getModalidad().getTipo() != null
								&& pin.getProyecto().getModalidad().getTipo().getId()
										.equals(TipoModalidad.BANCO_PROYECTOS)
								&& pin.getTipoInforme().getId().equals(TipoInforme.INFORME_FINAL)) {
							sesion.removeAttribute("manejadorRegistroInformeBancoProyectos");
							return "detalleInformeBancoProyectos";
						} else {
							sesion.removeAttribute(ManejadorRegistroInforme.MANEJADOR_REGISTRO_INFORME_SESSION);
							return "detalleInforme";
						}
					}
				}
				return "";
			}

			if (alertaProyecto.getSolicitud().getTipoSolicitud().getId() == 30) {
				super.sesion.setAttribute("proyectoRenovacion", alertaProyecto.getProyecto().getId());
				super.sesion.setAttribute("tipoAccion", new Long("1"));
				super.sesion.setAttribute("esCoordinador", new Long("0"));
				super.sesion.setAttribute("consultaRenovacionCoordindador", true);
				sesion.setAttribute("idSolicitudRenovacion_", alertaProyecto.getSolicitud().getId());
				sesion.setAttribute("idAlertaRenovacion_", alertaProyecto.getId());
				sesion.removeAttribute("consultaInformeCoordinador");
				sesion.removeAttribute(ManejadorRegistroInforme.MANEJADOR_REGISTRO_INFORME_SESSION);

				return "detalleInforme";
			}
		}

		cargarProyectos(proyectoActual.getId());
		return "informacionProyecto";
	}

	/********************************************************************************
	 * DML PROYECTO
	 ********************************************************************************/
	public boolean guardarCodigoQuipu() {
		boolean valido = false;
		if (!noAplicaCodigoQuipu) {
			if ((codigoQuipuAux != null && codigoQuipuAux.equals(proyectoActual.getCodigoQuipu()))
					&& !tieneVariosCodigoQuipu) {
				return false;
			} else {
				if (proyectoActual != null) {
					if (codigoQuipuAux != null && !codigoQuipuAux.equals(proyectoActual.getCodigoQuipu())) {
						if (validarCodigoQuipu(codigoQuipuAux, 1)) {
							proyectoActual.setCodigoQuipu(codigoQuipuAux);
							servicioGeneral.guardarObjeto(proyectoActual);
							enviarCorreoCodigoQuipu("codQuipu");
							if (bloquearSolicitudes) {
								bloquearSolicitudes = false;
							}
							valido = true;
						} else {
							valido = false;
						}
					}
					if (tieneVariosCodigoQuipu) {
						if (segundoCodigoQuipuAux != null
								&& !segundoCodigoQuipuAux.equals(proyectoActual.getSegundoCodigoQuipu())) {
							if (validarCodigoQuipu(segundoCodigoQuipuAux, 2)) {
								if (!segundoCodigoQuipuAux.equals(proyectoActual.getCodigoQuipu())) {
									proyectoActual.setSegundoCodigoQuipu(segundoCodigoQuipuAux);
									servicioGeneral.guardarObjeto(proyectoActual);
									enviarCorreoCodigoQuipu("segCodQuipu");
									valido = true;
								} else {
									mensajeError("El segundo código Quipú debe ser diferente al primero.");
									valido = false;
								}
							}
						}
					}
					return valido;
				} else {
					return false;
				}
			}
		} else {
			if (!esCadenaVacia(proyectoActual.getNotaCodigoQuipu())) {
				if (proyectoActual.getNotaCodigoQuipu().length() < 301) {
					proyectoActual.setCodigoQuipu("No Aplica");
					servicioGeneral.guardarObjeto(proyectoActual);
					if (bloquearSolicitudes) {
						bloquearSolicitudes = false;
					}
					return true;
				} else {
					mensajeError(
							"El 'Motivo' por la cual el código Quipú 'No Aplica' debe contener máximo 300 caracteres.");
					return false;
				}
			} else {
				mensajeError("Debe ingresar la razón por la cual el código Quipú 'No Aplica'.");
				return false;
			}
		}
	}

	public boolean validarCodigoQuipu(String codigo, int numeroCod) {
		if (codigo != null && !codigo.equals("") && codigo.trim().length() > 10 && codigo.trim().length() < 14) {
			int tam = codigo.trim().length();
			Pattern numerico = Pattern.compile("^[0-9]{" + tam + "}+$");
			Matcher m = numerico.matcher(codigo.trim());
			if (m.find()) {
				return true;
			} else {
				if (numeroCod == 1) {
					mensajeError("El código Quipú debe contener solo números.");
				} else {
					mensajeError("El segundo código Quipú debe contener solo números.");
				}
				return false;
			}
		} else {
			if (numeroCod == 1) {
				mensajeError("El código Quipú debe entre 11 y 13 caracteres.");
			} else {
				mensajeError("El segundo código Quipú debe entre 11 y 13 caracteres.");
			}
			return false;
		}
	}

	public void enviarCorreoCodigoQuipu(String codigo) {
		Correo correo = new Correo();
		CorreoPlantilla cp = cargarPlantilla(251);
		correo.setOrigen(Correo.CORREO_HERMES);
		correo.setAsunto(cp.getAsunto());
		investigadorPrincipal = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
		correo.setCuerpo(
				cp.getCuerpo().replaceAll("<<INVESTIGADOR>>", investigadorPrincipal.getNombreCompletoMinusculas()));
		if (codigo.equals("codQuipu")) {
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<QUIPU>>", proyectoActual.getCodigoQuipu()));
		} else if (codigo.equals("segCodQuipu")) {
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<QUIPU>>", proyectoActual.getSegundoCodigoQuipu()));
		}
		correo.setCuerpo(correo.getCuerpo().replaceAll("<<ID>>",
				proyectoActual.getId().toString() + " - " + proyectoActual.getNombre()));
		// correo.adicionarDireccion(Correo.CORREO_HERMES);
		correo.adicionarDireccion(investigadorPrincipal.getEmail());
		servicioCorreo.enviarCorreo(correo);
	}

	public static Date obtenerProximaFechaBioInforme(Date fechaActual) {
	    Calendar calendarioActual = Calendar.getInstance();
	    calendarioActual.setTime(fechaActual);

	    // Crear calendarios para 2 de mayo y 2 de octubre del año actual
	    Calendar calendarioMayo = Calendar.getInstance();
	    calendarioMayo.set(Calendar.YEAR, calendarioActual.get(Calendar.YEAR));
	    calendarioMayo.set(Calendar.MONTH, Calendar.MAY);
	    calendarioMayo.set(Calendar.DAY_OF_MONTH, 2);

	    Calendar calendarioOctubre = Calendar.getInstance();
	    calendarioOctubre.set(Calendar.YEAR, calendarioActual.get(Calendar.YEAR));
	    calendarioOctubre.set(Calendar.MONTH, Calendar.OCTOBER);
	    calendarioOctubre.set(Calendar.DAY_OF_MONTH, 2);

	    // Próxima fecha estricta:
	    // - antes del 2/may -> 2/may
	    // - 2/may o antes del 2/oct -> 2/oct
	    // - 2/oct o después -> 2/may del año siguiente
	    if (calendarioActual.before(calendarioMayo)) {
	        return calendarioMayo.getTime();
	    } else if (calendarioActual.before(calendarioOctubre)) {
	        return calendarioOctubre.getTime();
	    } else {
	        calendarioMayo.add(Calendar.YEAR, 1); // sumar un año a mayo antes de devolverlo
	        return calendarioMayo.getTime();
	    }
	}



	public void guardarCambiosProyecto() {
		guardarHistoricoEdicionProyecto();
		mensajeActualizacion = "";
		Date fechaHistorico = new Date();
		List<HistoricoEstadoSolicitud> historicosEstadoSolicitud = new ArrayList<HistoricoEstadoSolicitud>();

		try {

			Iterator<Solicitud> i = proyectoActual.getListaSolicitudesEnviadas().iterator();
			while (i.hasNext()) {
				Solicitud solicitud = (Solicitud) i.next();

				if (StringUtils.isNotBlank(mensajeActualizacion)) {
					mensajeActualizacion += "\n";
				}

				Iterator<TramiteSolicitud> k = solicitud.getTramites().iterator();
				while (k.hasNext()) {

					TramiteSolicitud tramite = k.next();

					if (esCadenaVacia(tramite.getDescripcion())) {
						mensajeActualizacion += "La solicitud " + solicitud.getId()
								+ " no fue guardada. Debe ingresar la descripción del trámite de la solicitud. ";
					}

					if (esCadenaVacia(solicitud.getRespuesta())) {
						mensajeActualizacion += "La solicitud " + solicitud.getId()
								+ " no fue guardada. Debe seleccionar una respuesta para la solicitud. ";
					}

					if (!esCadenaVacia(tramite.getDescripcion()) && StringUtils.isBlank(mensajeActualizacion)) {

						// Se valida que se haya cambiado la respuesta, en
						// este caso se guarda el historico cambio de estado
						if (!solicitud.getRespuesta().equals(solicitud.getRespuestaAnterior())) {
							historicosEstadoSolicitud
									.add(crearHistoricoEstadoSolicitud(solicitud, tramite.getDescripcion()));
						}

						if (tramite.getNueva()) {
							if (!esCadenaVacia(codigoQuipu)) {
								proyectoActual.setCodigoQuipu(codigoQuipu);
							}

						}

						if (tramite.getNueva() && solicitud.getRespuesta().equals(Solicitud.APROBADA)) {
							if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CERTIFICADO_MOVILIZACION)) {

								if (proyectoActual.getEsPermisoMarcoAsignatura()
										&& !proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.ACTIVO)) {
									proyectoActual.cambiarEstadoPersona(EstadoProyecto.ACTIVO, cargarPersonaActual(),
											"Aprobación primer certificado de movilización para la asignatura");
									servicioGeneral.guardarObjeto(proyectoActual);
								}

								/**
								 * Si no tiene se asigna compromiso de informe de avance para la vigencia del
								 * certificado de movilizacion
								 */
								if (!proyectoTieneCompromiso(proyectoActual.getId(), TipoInforme.INFORME_AVANCE)
										&& !proyectoActual.getEsPermisoMarcoAsignatura()) {
									GregorianCalendar gregorianCalendar = new GregorianCalendar();
									String fechaEntrega = "";
									DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
									int ano = gregorianCalendar.get(Calendar.YEAR);
									int mes = gregorianCalendar.get(Calendar.MONTH);

									try {
										List<Parametro> parametros = this.servicioGeneral.obtenerListaObjetosWhere(
												Parametro.class, "WHERE p.nombre = 'FEC_INFORMES_PERMISO_MARCO'");
										Parametro parametro = parametros.get(0);
										GregorianCalendar fechaEstimada = new GregorianCalendar();

										if (mes == 0 || mes == 1 || mes == 2) {
											fechaEstimada.setTime(parametro.getFechaInicial());
											int fechaDia = fechaEstimada.get(Calendar.DAY_OF_MONTH);
											int fechaMes = fechaEstimada.get(Calendar.MONTH);
											fechaEntrega = fechaDia + "/" + (fechaMes + 1) + "/" + ano;
										} else if (mes == 9 || mes == 10 || mes == 11) {
											fechaEstimada.setTime(parametro.getFechaInicial());
											int fechaDia = fechaEstimada.get(Calendar.DAY_OF_MONTH);
											int fechaMes = fechaEstimada.get(Calendar.MONTH);
											fechaEntrega = fechaDia + "/" + (fechaMes + 1) + "/" + (ano + 1);
										} else {
											fechaEstimada.setTime(parametro.getFechaFinal());
											int fechaDia = fechaEstimada.get(Calendar.DAY_OF_MONTH);
											int fechaMes = fechaEstimada.get(Calendar.MONTH);
											fechaEntrega = fechaDia + "/" + (fechaMes + 1) + "/" + ano;
										}
									} catch (Exception e) {
										e.printStackTrace();
									}

									Date fechaInforme = df.parse(fechaEntrega);

									TipoInforme informe = new TipoInforme();
									informe.setId(TipoInforme.INFORME_AVANCE);
									ProyectoCompromiso compromisoInforme = new ProyectoCompromiso();
									compromisoInforme.setProyecto(proyectoActual);
									compromisoInforme.setCumplido(ProyectoCompromiso.NO_CUMPLIDO);
									compromisoInforme.setTipoInforme(informe);
									compromisoInforme.setNumeroNotificaciones(0);
									compromisoInforme.setFechaVencimiento(fechaInforme);
									servicioGeneral.guardarObjeto(compromisoInforme);
								}

								if (!proyectoTieneCompromiso(proyectoActual.getId(), TipoInforme.INFORME_FINAL)
										&& proyectoActual.getEsPermisoMarcoAsignatura()) {

									Date fechaInforme = obtenerProximaFechaBioInforme(getToday());

									TipoInforme informe = new TipoInforme();
									informe.setId(TipoInforme.INFORME_FINAL);
									ProyectoCompromiso compromisoInforme = new ProyectoCompromiso();
									compromisoInforme.setProyecto(proyectoActual);
									compromisoInforme.setCumplido(ProyectoCompromiso.NO_CUMPLIDO);
									compromisoInforme.setTipoInforme(informe);
									compromisoInforme.setNumeroNotificaciones(0);
									compromisoInforme.setFechaVencimiento(fechaInforme);
									servicioGeneral.guardarObjeto(compromisoInforme);
								}

							} else if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CONTRATO_INDIVIDUAL)
									|| solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CONTRATO_MARCO)) {
								/*
								 * Determinar que hacer, solo correo o cambio a estado activo y compromisos
								 */
								proyectoActual.cambiarEstadoPersona(EstadoProyecto.APROBADO, cargarPersonaActual(),
										"Suscripción de contrato de acceso en el MADS");
								servicioGeneral.guardarObjeto(proyectoActual);
								/*
								 * Se envia correo informativo.
								 */
								Correo correo = new Correo();
								correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
								Persona pe = this.servicioProyecto
										.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
								correo.adicionarDireccion(pe.getEmail());
								Persona pe1 = servicioProyecto.obtenerCoordinadorProyecto(proyectoActual.getId());
								correo.adicionarCopiaOculta(pe1.getEmail());
								// correo.adicionarDireccion(Correo.CORREO_HERMES);
								String asunto = "Respuesta solicitud";
								correo.setAsunto(asunto);
								String mensaje = "Cordial saludo,\n\n" + "Amablemente informamos que se ha dado "
										+ "tramite a su solicitud " + solicitud.getId() + " asociada al " + "proyecto "
										+ proyectoActual.getNombre() + " con código " + proyectoActual.getId()
										+ ". \n\n El Ministerio de Ambiente y Desarrollo Sostenible (MADS) ha aprobado la solicitud de otrosí al contrato marco / suscripción de contrato individual "
										+ "a través de la resolución adjunta a su proyecto. \n\n La Vicerrectoría de Investigación suscribió el otrosí/contrato con el cual puede realizar "
										+ "las actividades de acceso. \n\n En el proyecto " + proyectoActual.getId()
										+ " en la opción 'Información de seguimiento', podrá consultar los archivos que "
										+ "soportan la decisión y los compromisos adquiridos, los cuales deberán ser reportados a través del Sistema Hermes. \n\n"
										+ "Los siguientes fueron los comentarios realizados por el coordinador: \n\n";
								mensaje += tramite.getDescripcion();
								correo.setCuerpo(mensaje);
								servicioCorreo.enviarCorreoSolicitud(correo);
							}
						}

						if (tramite.getNueva() && solicitud.getRespuesta().equals(Solicitud.TRAMITE)) {

							/*
							 * Se envia correo informativo, no hay cambios en los datos
							 */
							Correo correo = new Correo();
							correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
							Persona pe = this.servicioProyecto
									.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
							correo.adicionarDireccion(pe.getEmail());
							Persona pe1 = servicioProyecto.obtenerCoordinadorProyecto(proyectoActual.getId());
							correo.adicionarCopiaOculta(pe1.getEmail());
							// correo.adicionarDireccion(Correo.CORREO_HERMES);
							String asunto = "Respuesta solicitud";
							correo.setAsunto(asunto);
							String mensaje = "Cordial saludo,\n\n" + "Amablemente informamos que se está dando "
									+ "tramite a su solicitud '" + solicitud.getId() + "' asociada al " + "proyecto '"
									+ proyectoActual.getNombre() + "' con código '" + proyectoActual.getId()
									+ "' y esta ha cambiado de estado a 'En estudio/En trámite'. \n \n"
									+ "Los siguientes fueron los comentarios realizados: \n\n";
							mensaje += tramite.getDescripcion();
							correo.setCuerpo(mensaje);
							servicioCorreo.enviarCorreoSolicitud(correo);
						}

						if (tramite.getNueva() && solicitud.getRespuesta().equals(Solicitud.DEVUELTO_CORRECCIONES)) {
							/*
							 * Se envíe correo indicando que debe diligenciar nuevamente la información de
							 * la solicitud.
							 */
							Correo correo = new Correo();
							correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
							Persona pe = this.servicioProyecto
									.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
							correo.adicionarDireccion(pe.getEmail());
							Persona pe1 = servicioProyecto.obtenerCoordinadorProyecto(proyectoActual.getId());
							correo.adicionarCopiaOculta(pe1.getEmail());
							// correo.adicionarDireccion(Correo.CORREO_HERMES);
							String asunto = "Solicitud devuelta para correcciones";
							correo.setAsunto(asunto);
							String mensaje = "Cordial saludo,\n\n" + "Amablemente informamos que su solicitud '"
									+ solicitud.getId() + "' asociada al " + "proyecto '" + proyectoActual.getNombre()
									+ "' con código '" + proyectoActual.getId()
									+ "' ha sido devuelta para correcciones. \n \n"
									+ "Los siguientes fueron los comentarios realizados: \n\n";
							mensaje += tramite.getDescripcion();
							correo.setCuerpo(mensaje);
							servicioCorreo.enviarCorreo(correo);
						}

						if (tramite.getNueva() && solicitud.getRespuesta().equals(Solicitud.RESUELTO)) {
							if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CONTRATO_INDIVIDUAL)
									|| solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CONTRATO_MARCO)) {
								proyectoActual.cambiarEstadoPersona(EstadoProyecto.NO_APROBADO, cargarPersonaActual(),
										"No aprobación de suscripción de contrato de acceso en el MADS");
								servicioGeneral.guardarObjeto(proyectoActual);
								/*
								 * Se envia correo informativo.
								 */
								Correo correo = new Correo();
								correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
								Persona pe = this.servicioProyecto
										.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
								correo.adicionarDireccion(pe.getEmail());
								Persona pe1 = servicioProyecto.obtenerCoordinadorProyecto(proyectoActual.getId());
								correo.adicionarCopiaOculta(pe1.getEmail());
								// correo.adicionarDireccion(Correo.CORREO_HERMES);
								String asunto = "Respuesta solicitud";
								correo.setAsunto(asunto);
								String mensaje = "Cordial saludo,\n\n" + "Amablemente informamos que se ha dado "
										+ "tramite a su solicitud '" + solicitud.getId() + "' asociada al "
										+ "proyecto '" + proyectoActual.getNombre() + "' con código '"
										+ proyectoActual.getId()
										+ "'. \n\n El Ministerio de Ambiente y Desarrollo Sostenible (MADS) NO ha aprobado la solicitud de otrosí al contrato marco / suscripción de contrato individual "
										+ "a través del Auto adjunto a su proyecto. \n\n En el proyecto "
										+ proyectoActual.getId()
										+ " en la opción 'Información de seguimiento', podrá consultar los archivos que "
										+ "soportan la decisión. \n\n"
										+ "Los siguientes fueron los comentarios realizados por el coordinador: \n\n";
								mensaje += tramite.getDescripcion();
								correo.setCuerpo(mensaje);
								servicioCorreo.enviarCorreoSolicitud(correo);
							} else {
								Correo correo = new Correo();
								correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
								Persona pe = this.servicioProyecto
										.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
								String dirCorreo = pe.getEmail();
								correo.adicionarDireccion(dirCorreo);
								Persona pe1 = servicioProyecto.obtenerCoordinadorProyecto(proyectoActual.getId());
								String dirCorreo1 = pe1.getEmail();
								correo.adicionarCopiaOculta(dirCorreo1);
								// correo.adicionarDireccion(Correo.CORREO_HERMES);
								String asunto = "Respuesta solicitud";
								correo.setAsunto(asunto);
								String mensaje = "Cordial saludo,\n\n" + "Amablemente informamos que se ha dado "
										+ "tramite a su solicitud " + solicitud.getId() + " asociada al " + "proyecto "
										+ proyectoActual.getNombre() + " con código " + proyectoActual.getId()
										+ " y esta ha sido rechazada. \n \n"
										+ "Los siguientes fueron los comentarios realizados: \n\n";
								mensaje += tramite.getDescripcion();
								correo.setCuerpo(mensaje);
								servicioCorreo.enviarCorreoSolicitud(correo);
							}
						}

						if (tramite.getNueva() && (!solicitud.getRespuesta().equals(Solicitud.RESUELTO)
								|| !esCadenaVacia(solicitud.getRespuesta()))) {
							if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.ADICION_PRESPUESTAL)) {
								SolicitudAdicionPresupuestal adicion = solicitud.getAdicionPresupuesto();

								if (adicion != null) {
									if (adicion.getListaDetallesAdicionPresupuesto().size() > 0) {
										for (Iterator iterator = adicion.getDetalleAdicionPresupuesto()
												.iterator(); iterator.hasNext();) {
											DetalleAdicionPresupuesto detalleAdicion = (DetalleAdicionPresupuesto) iterator
													.next();
											if (detalleAdicion.getGasto() == null) {
												Gasto nuevoGasto = new Gasto();
												nuevoGasto.setFinanciacion(adicion.getFinanciacion());
												nuevoGasto.setTipoRubro(detalleAdicion.getTipoRubro());
												nuevoGasto.setValor(Long.parseLong(String.valueOf(0)));
												nuevoGasto.setVigencia(1);
												nuevoGasto.setCantidad(1);
												nuevoGasto.setDescripcion("Solicitud de adición de presupuesto "
														+ solicitudSeleccionada.getId());
												servicioGeneral.guardarObjeto(nuevoGasto);

												detalleAdicion.setGasto(nuevoGasto);
												servicioGeneral.guardarObjeto(detalleAdicion);
											}
										}
									}
								}
							}
						}

						// Cambio de rubros múltiples
						if (solicitud.getDetalleCambioRubro() != null && solicitud.getDetalleCambioRubro().size() > 0
								&& (!solicitud.getRespuesta().equals(Solicitud.RESUELTO)
										|| !esCadenaVacia(solicitud.getRespuesta()))) {
							Iterator<DetalleCambioRubro> j = solicitud.getDetalleCambioRubro().iterator();
							while (j.hasNext()) {
								DetalleCambioRubro detalleCambioRubro = j.next();
								if (detalleCambioRubro.getApropiacionOrigen() == null
										|| detalleCambioRubro.getApropiacionDestino() == null
										|| detalleCambioRubro.getSaldoDestino() == null
										|| detalleCambioRubro.getSaldoOrigen() == null) {
									mensajeActualizacion = "Debe ingresar los valores de las apropiaciones y saldos para cada uno de los rubros en esta solicitud.";

									throw new Exception(mensajeActualizacion);
								}
							}

							Iterator<DetalleCambioRubro> l = solicitud.getDetalleCambioRubro().iterator();
							while (l.hasNext()) {
								DetalleCambioRubro detalleCambioRubroAux = l.next();
								servicioGeneral.guardarObjeto(detalleCambioRubroAux);
								Financiacion financiacion = detalleCambioRubroAux.getGasto().getFinanciacion();
								financiacion = servicioProyecto.obtenerFinanciacionGastos(financiacion);
								Set gastosFinanciacion = financiacion.getGastos();
								Iterator<Gasto> gastosIterator = gastosFinanciacion.iterator();
								boolean existeGasto = false;
								while (gastosIterator.hasNext()) {
									Gasto gasto = gastosIterator.next();
									if (gasto.getTipoRubro().getId()
											.equals(detalleCambioRubroAux.getTipoRubro().getId())) {
										existeGasto = true;
										break;
									}
								}
								if (!existeGasto) {
									Gasto gastoNuevo = new Gasto();
									gastoNuevo.setFinanciacion(financiacion);
									gastoNuevo.setTipoRubro(detalleCambioRubroAux.getTipoRubro());
									gastoNuevo.setVigencia(1);
									gastoNuevo.setCantidad(1);
									gastoNuevo.setDescripcion("Solicitud de cambio de rubros " + solicitud.getId());
									gastoNuevo.setValor(Long.parseLong(String.valueOf(0)));
									financiacion.adicionarGasto(gastoNuevo);
									servicioGeneral.guardarObjeto(financiacion);
								}
							}

						}
					}
				}
			}

			EstadoProyecto estadoNuevo = (EstadoProyecto) servicioGeneral.obtenerObjeto(new EstadoProyecto(),
					proyectoActual.getEstadoProyecto().getId());
			proyectoActual.setEstadoProyecto(estadoNuevo);

			if (!esListaVacia(historicosEstadoSolicitud)) {
				Iterator<HistoricoEstadoSolicitud> j = historicosEstadoSolicitud.iterator();
				// Se valida que se haya cambiado la respuesta, en este caso se
				// guarda el historico cambio de estado
				while (i.hasNext()) {
					HistoricoEstadoSolicitud historicoEstadoSolicitud = j.next();
					try {
						servicioGeneral.guardarObjeto(historicoEstadoSolicitud);
						historicoEstadoSolicitud.getSolicitud()
								.setRespuestaAnterior(historicoEstadoSolicitud.getSolicitud().getRespuesta());
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Error al guardar historico de estado de solicitud.");
					}
				}
			}

			Set observacionesProyecto = new HashSet();
			proyectoActual.setObservacionesProyecto(observacionesProyecto);

			guardarProrrogas();

			String mensajeActualizacionBD = this.servicioProyecto.guardaProyectoConHistoricoEstadoDeBD(
					proyectoActual, "Cambio realizado por el coordinador " + asesor.getNombre1() + ""
							+ asesor.getNombre2() + " " + asesor.getApellido1() + " " + asesor.getApellido2(),
					fechaHistorico);
			if ("Proyecto guardado sin cambio de estado".equals(mensajeActualizacionBD)
					|| "Guardado satisfactorio".equals(mensajeActualizacionBD)) {
				if (StringUtils.isNotBlank(mensajeActualizacion)) {
					mensajeActualizacion += "\n";
				}
				mensajeActualizacion += " El proyecto ha sido guardado.";

			}

			/******************************************************************************
			 * MANEJO ALERTAS POR SOLICITUDES
			 ******************************************************************************/
			Iterator<Solicitud> l = proyectoActual.getListaSolicitudesEnviadas().iterator();
			while (l.hasNext()) {
				Solicitud solicitud = l.next();
				// CREACION ALERTAS POR SOLICITUDES SIN ATENDER
				List<AlertaProyecto> listaAlertaProyecto = this.servicioAlertas
						.obtenerAlertaProyectoSolicitud(proyectoActual, solicitud);
				if (listaAlertaProyecto != null) {
					Iterator<AlertaProyecto> m = listaAlertaProyecto.iterator();
					while (m.hasNext()) {
						AlertaProyecto alertaProyecto = m.next();
						if (alertaProyecto == null) {
							alertaProyecto = new AlertaProyecto();
							alertaProyecto.setAsesor(this.coordinadorSeguimiento);
							alertaProyecto.setFechaGenera(solicitud.getFecha());
							alertaProyecto.setMensaje("Solicitud " + solicitud.getTipoSolicitud().getNombre());
							alertaProyecto.setProyecto(proyectoActual);
							alertaProyecto.setSolicitud(solicitud);
						}
						if (esCadenaVacia(solicitud.getRespuesta())
								|| solicitud.getRespuesta().equals(Solicitud.TRAMITE)) {
							alertaProyecto.setEstado(Solicitud.PENDIENTE);
						}
						// CERRAR ALERTAS
						else {
							alertaProyecto.setEstado(Solicitud.ALERTA_CERRADA);
							alertaProyecto.setFechaCierre(new Date());
						}

						this.servicioGeneral.guardarObjeto(alertaProyecto);
					}
				}
			}

			alertasAsesor = servicioAlertas.listarAlertasAsesorInbox(asesor);

			cargarSolicitudes();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		for (int i = 0; i < proyectoActual.getListaSolicitudesEnviadas().size(); i++) {
			Solicitud solicitud = (Solicitud) proyectoActual.getListaSolicitudesEnviadas().get(i);
			if (solicitud != null && solicitud.getRespuesta() == null) {
				solicitud.setRespuesta("");
			}
			if (solicitud != null && solicitud.getRespuestaAnterior() == null) {
				solicitud.setRespuestaAnterior("");
			}
		}
	}

	private void guardarProrrogas() throws Exception {

		// Se calcula la duración acumulada del proyecto en meses y dias
		int acumuladoMesesDuracion = 0;
		int acumuladoDiasDuracion = 0;

		boolean hayProrrogasNuevas = false;

		if (proyectoActual.getListaProrrogas() != null && proyectoActual.getFechaTentativaInicio() != null) {
			for (int k = 0; k < proyectoActual.getListaProrrogas().size(); k++) {
				ProyectoProrroga prorroga = (ProyectoProrroga) proyectoActual.getListaProrrogas().get(k);

				// Validar prrogoga
				mensajeActualizacion = servicioProyecto.validarProrrogaProyecto(prorroga);

				if (!esCadenaVacia(mensajeActualizacion)) {
					throw new Exception(mensajeActualizacion);
				}

				hayProrrogasNuevas = true;

				// Se suman los meses de las prorrogas en meses y dias.
				acumuladoMesesDuracion += prorroga.getDuracion().intValue();
				acumuladoDiasDuracion += prorroga.getDias().intValue();

				// Se calcula la nueva fecha con cada prorroga, y se asigna a
				// esta para ser consultada.
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(proyectoActual.getFechaTentativaInicio());
				calendar.add(Calendar.MONTH, proyectoActual.getDuracion() + acumuladoMesesDuracion);
				calendar.add(Calendar.DAY_OF_YEAR, acumuladoDiasDuracion);

				prorroga.setNuevaFechaFinal(calendar.getTime());

				if (prorroga.getId() == null) {
					if (verificarPermisoMarcoAsignarProrroga(prorroga, acumuladoMesesDuracion, acumuladoDiasDuracion)) {
						prorroga.setAplicadaABiodiversidad(1L);
					}
				}

			}
		}

		// Se actualizan valores en el proyectos
		proyectoActual.setDuracionAcumulada(new Integer(proyectoActual.getDuracion() + acumuladoMesesDuracion));
		proyectoActual.setDuracionDiasAcumulada(acumuladoDiasDuracion);

		if (hayProrrogasNuevas) {

			if (acumuladoMesesDuracion > 0 || acumuladoDiasDuracion > 0) {

				if (!esListaVacia(proyectoActual.getListaCompromisos())) {
					Iterator<ProyectoCompromiso> i = proyectoActual.getListaCompromisos().iterator();
					while (i.hasNext()) {
						ProyectoCompromiso compromiso = i.next();
						if (compromiso.getCumplido().equals(ProyectoCompromiso.NO_CUMPLIDO)) {
							Date fechaVencProrroga = (Date) compromiso.getFechaVencimiento().clone();

							// Se agregan los meses de prorroga.
							fechaVencProrroga.setMonth(fechaVencProrroga.getMonth() + acumuladoMesesDuracion);

							// Se agregan los dias de prorroga
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(fechaVencProrroga);
							calendar.add(Calendar.DAY_OF_YEAR, acumuladoDiasDuracion);
							fechaVencProrroga = calendar.getTime();

							// Se asgina nuevo valor al compromiso y se guarda
							compromiso.setFechaVencProrroga(new Date(fechaVencProrroga.getTime()));
							compromiso.setNumeroNotificaciones(0);
						}
					}
				}
			}
		}

		cargarFechasProyecto(proyectoActual);

	}

	public boolean verificarPermisoMarcoAsignarProrroga(ProyectoProrroga prorroga, int meses, int dias) {
		try {
			String hql = "select #id p.id from Proyecto p where p.codigoDib =  '" + proyectoActual.getId() + "'";
			List<Proyecto> proyectos = servicioGeneral.obtenerObjetosLimitado(Proyecto.class, hql);
			if (!esListaVacia(proyectos)) {
				for (int i = 0; i < proyectos.size(); i++) {
					Proyecto proyectoPermiso = servicioProyecto.obtenerProyecto(proyectos.get(i).getId(),
							ProyectoDAOHibernate.TODO_POR_ID);

					ProyectoProrroga prorrogaPermisoMarco = new ProyectoProrroga();
					prorrogaPermisoMarco.setId(null);
					prorrogaPermisoMarco.setProyecto(proyectoPermiso);
					prorrogaPermisoMarco.setDias(prorroga.getDias());
					prorrogaPermisoMarco.setFecha(new Date());
					prorrogaPermisoMarco.setDuracion(prorroga.getDuracion());
					prorrogaPermisoMarco.setResponsable(prorroga.getResponsable());
					prorrogaPermisoMarco.setDiasVista(prorroga.getDiasVista());
					prorrogaPermisoMarco.setMesesVista(prorroga.getMesesVista());
					prorrogaPermisoMarco.setNuevaFechaFinal(prorroga.getNuevaFechaFinal());

					proyectoPermiso.setDuracionAcumulada(new Integer(proyectoPermiso.getDuracion() + meses));
					proyectoPermiso.setDuracionDiasAcumulada(dias);
					servicioGeneral.guardarObjeto(prorrogaPermisoMarco);

					if (!esListaVacia(proyectoPermiso.getListaCompromisos())) {
						Iterator<ProyectoCompromiso> a = proyectoPermiso.getListaCompromisos().iterator();
						while (a.hasNext()) {
							ProyectoCompromiso compromiso = a.next();
							if (compromiso.getCumplido().equals(ProyectoCompromiso.NO_CUMPLIDO)
									&& compromiso.getTipoInforme().getId().equals(TipoInforme.INFORME_FINAL)) {
								Date fechaVencProrroga = (Date) compromiso.getFechaVencimiento().clone();

								// Se agregan los meses de prorroga.
								fechaVencProrroga.setMonth(fechaVencProrroga.getMonth() + meses);

								// Se agregan los dias de prorroga
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(fechaVencProrroga);
								calendar.add(Calendar.DAY_OF_YEAR, dias);
								fechaVencProrroga = calendar.getTime();

								// Se asgina nuevo valor al compromiso y se guarda
								compromiso.setFechaVencProrroga(new Date(fechaVencProrroga.getTime()));
								compromiso.setNumeroNotificaciones(0);
							}
						}
					}
					servicioGeneral.guardarObjeto(proyectoPermiso);
				}
				return true;
			}

		} catch (Exception e) {
			System.out.print(e);
		}
		return false;
	}

	/**
	 * Método para agregar obligaciones al convenio.
	 */
	public void agregarObligacion() {

		if (!esCadenaVacia(obligacionesUN)) {
			ConvenioObligacion convenioObligacionNuevo = new ConvenioObligacion();
			convenioObligacionNuevo.setObligacion(obligacionesUN);
			convenioObligacionNuevo.setFecha(fechaObligacion);
			convenioActual.adicionarObligacion(convenioObligacionNuevo);

			obligacionesUN = "";
			fechaObligacion = null;
		} else {
			mensajeError(uiObligaciones, "Por favor ingrese toda la información correspondiente a la obligación.");
		}

	}

	// Agregar Informe Final
	public void agregarInformeFinal(boolean esConvocatoriaExterna) {
		boolean valido = true;

		// Se validan los datos ingresados.
		if (!esCadenaVacia(proyectoActual.getPresentaInformeFinal())
				&& "SI".equals(proyectoActual.getPresentaInformeFinal()) || !esConvocatoriaExterna) {
			if (proyectoActual.getFechaInformeFinal() != null && proyectoActual.getFechaTentativaInicio() != null) {
				if (proyectoActual.getFechaInformeFinal().before(proyectoActual.getFechaTentativaInicio())) {
					mensajeError(uiInformes,
							"La fecha del informe final debe ser posterior a la fecha de inicio del proyecto.");
					valido = false;
				}
			} else {
				mensajeError(uiInformes,
						"Debe ingresar la fecha de entrega del informe final y la fecha de inicio del proyecto.");
				valido = false;
			}
		} else if (!esCadenaVacia(proyectoActual.getPresentaInformeFinal())
				&& "NO".equals(proyectoActual.getPresentaInformeFinal())) {
			proyectoActual.eliminarCompromisoFinal();
			valido = false;
		}

		// Agregar a la lista de informes
		if (valido) {

			ProyectoCompromiso proyectoCompromiso;

			List<ProyectoCompromiso> proyectoCompromisos = proyectoActual.getListaCompromisoInformesFinales();
			if (!esListaVacia(proyectoCompromisos)) {
				proyectoCompromiso = proyectoActual.getListaCompromisoInformesFinales().get(0);
			} else {
				proyectoCompromiso = new ProyectoCompromiso();
				TipoInforme tipoInformeFinal = new TipoInforme(TipoInforme.INFORME_FINAL);
				proyectoCompromiso.setTipoInforme(tipoInformeFinal);
				proyectoCompromiso.setCumplido(ProyectoCompromiso.NO_CUMPLIDO);
				proyectoCompromiso.setNumeroNotificaciones(0);
				proyectoActual.adicionarCompromisoProyecto(proyectoCompromiso);
			}

			proyectoCompromiso.setFechaVencimiento(proyectoActual.getFechaInformeFinal());
		}

	}

	public void agregarInformeParcial() {

		boolean valido = true;
		// Fecha inicio
		if (proyectoActual.getFechaTentativaInicio() != null) {
			if (proyectoActual.getFechaTentativaInicio() != null && fechaInformeParcial != null
					&& fechaInformeParcial.before(proyectoActual.getFechaTentativaInicio())) {
				mensajeError(uiInformes,
						"La fecha del informe parcial debe ser posterior a la fecha de inicio del proyecto.");
				valido = false;
			}
			if (proyectoActual.getFechaFinalizacion() == null) {
				cambiarFechas();
			}
			if (fechaInformeParcial == null) {
				mensajeError(uiInformes, "Por favor seleccione una fecha de informe parcial.");
				valido = false;
			} else if (fechaInformeParcial.after(proyectoActual.getFechaFinalizacion())) {
				mensajeError(uiInformes,
						"La fecha del informe parcial debe ser anterior a la fecha de finalización del proyecto.");
				valido = false;
			}
		} else {
			mensajeError(uiInformes, "Ingrese primero la fecha de inicio del proyecto.");
			valido = false;
		}

		// Agregar a la lista de informes
		if (valido) {
			ProyectoCompromiso proyectoCompromiso = new ProyectoCompromiso();
			TipoInforme tipoInformeAvance = new TipoInforme(TipoInforme.INFORME_AVANCE);
			proyectoCompromiso.setTipoInforme(tipoInformeAvance);
			proyectoCompromiso.setCumplido(ProyectoCompromiso.NO_CUMPLIDO);
			proyectoCompromiso.setNumeroNotificaciones(0);
			proyectoCompromiso.setFechaVencimiento(fechaInformeParcial);
			proyectoActual.adicionarCompromisoProyecto(proyectoCompromiso);
			fechaInformeParcial = null;
		}

	}

	// Eliminar informe parcial de la lista
	public void eliminarInformeParcial() {
		proyectoActual.getCompromisosProyecto().remove(compromisoProyectoSeleccionado);
	}

	public void agregarDesembolso() {

		boolean valido = true;

		if (esCadenaVacia(desembolsos)) {
			valido = false;
			mensajeError(uiDesembolsos, "Seleccione el tipo de desembolsos.");
		}

		if (esCadenaVacia(entidadDesembolso)) {
			valido = false;
			mensajeError(uiDesembolsos, "Primero agregue las entidades relacionadas y seleccione una.");
		}

		if (valorDesembolso != null && valorDesembolso <= 0) {
			valido = false;
			mensajeError(uiDesembolsos, "El valor del desembolso debe ser un valor numérico mayor a cero.");
		}

		if (valorDesembolso <= 0) {
			valido = false;
			mensajeError(uiDesembolsos, "El número del desembolso debe ser numérico y mayor a cero.");
		}

		if (fechaDesembolso == null) {
			valido = false;
			mensajeError(uiDesembolsos, "Ingrese la fecha del desembolso.");
		}

		List<Gasto> desembolsosExistentes = proyectoActual.getDesembolsosAdicionados();
		Iterator<Gasto> i = desembolsosExistentes.iterator();
		while (i.hasNext() && valido) {
			Gasto desembolsoExistente = i.next();
			if (desembolsoExistente.getFinanciacion().getFuente().getId().equals(entidadDesembolso)) {
				if ("Unico".equalsIgnoreCase(desembolsos)
						|| "Unico".equalsIgnoreCase(desembolsoExistente.getDescripcion())) {
					valido = false;
					mensajeError(uiDesembolsos,
							"El desembolso es único y ya hay desembolsos agregados para esta entidad.");
					break;
				}
				if (desembolsoExistente.getFechaDesembolso().equals(fechaDesembolso)) {
					valido = false;
					mensajeError(uiDesembolsos,
							"El desembolso ya se encuentra relacionado, por favor seleccione otra fecha.");
					break;
				}
				if ("Parciales".equalsIgnoreCase(desembolsos) && desembolsoExistente.getCantidad() == numDesembolso) {
					valido = false;
					mensajeError(uiDesembolsos,
							"El desembolso ya se encuentra relacionado, por favor seleccione otro numero.");
					break;
				}
			}
		}

		if ((sumaTotalDesembolsos + valorDesembolso) > convenioActual.getMontoEjecutarUN()) {
			valido = false;
			mensajeError(uiDesembolsos,
					"La suma de los desembolsos excede el 'Valor total a desembolsar a la Universidad.'");
			System.out.println("suma desembolsos: " + (sumaTotalDesembolsos + valorDesembolso));
		}

		if (valido) {

			Gasto gastoNuevo = new Gasto();

			Financiacion financiacion = proyectoActual.obtenerFinanciacionLegalizacion(entidadDesembolso);

			gastoNuevo.setValor(valorDesembolso);
			gastoNuevo.setCantidad(numDesembolso);
			gastoNuevo.setDescripcion(desembolsos);
			gastoNuevo.setFechaDesembolso(fechaDesembolso);
			gastoNuevo.setFinanciacion(financiacion);

			// Se agrega tipo rubro desembolso.
			TipoRubro tipoRubro = new TipoRubro();
			tipoRubro.setId(TipoRubro.DESEMBOLSO);
			gastoNuevo.setTipoRubro(tipoRubro);

			financiacion.adicionarGasto(gastoNuevo);

			sumaTotalDesembolsos += valorDesembolso;
			valorDesembolso = 0L;
			desembolsos = "0";
			numDesembolso = 1;
			fechaDesembolso = null;
		}

	}

	public boolean isEmptylistaDesembolsos() {
		if (proyectoActual.getDesembolsosAdicionados() != null) {
			if (proyectoActual.getDesembolsosAdicionados().size() > 0) {
				esVacialistaDesembolsos = false;
				return esVacialistaDesembolsos;
			} else {
				esVacialistaDesembolsos = true;
				return esVacialistaDesembolsos;
			}
		} else {
			esVacialistaDesembolsos = true;
			return esVacialistaDesembolsos;
		}
	}

	public boolean isEmptylistaCompromisosObligaciones() {
		if (listaCompromisosObligaciones != null) {
			if (listaCompromisosObligaciones.size() > 0) {
				esVacialistaCompromisosObligaciones = false;
				return esVacialistaCompromisosObligaciones;
			} else {
				esVacialistaCompromisosObligaciones = true;
				return esVacialistaCompromisosObligaciones;
			}
		} else {
			esVacialistaCompromisosObligaciones = true;
			return esVacialistaCompromisosObligaciones;
		}
	}

	public void cambiarSedeIngreso() {
		List<Dependencia> listaDependencias;
		if (sedeSel.equals(Dependencia.NIVEL_NACIONAL)) {
			listaDependencias = servicioDependencia.obtenerDependenciaXSede(sedeSel);

		} else {
			listaDependencias = servicioGeneral.obtenerListaObjetos("Dependencia d where d.sede.id in (" + sedeSel
					+ ") AND d.esFacultad = 'Y' ORDER BY d.sede.nombre, d.nombre");
		}
		if (!esListaVacia(listaDependencias)) {
			facultadInstFormaItem = new SelectItem[listaDependencias.size()];
			for (int i = 0; i < listaDependencias.size(); i++) {
				Dependencia dep = (Dependencia) listaDependencias.get(i);
				facultadInstFormaItem[i] = new SelectItem(dep.getId(), dep.getNombre());
			}
		}
	}

	public void agregarIngreso() {

		boolean valido = true;
		Long sumaIngresosTmp = 0L;
		Long subTotalIngresos = 0L;

		if (valorIngreso == null || (valorIngreso != null && valorIngreso <= 0L)) {
			valido = false;
			mensajeError("El valor del primer ingreso debe ser mayor a cero");
		}

		if (esCadenaVacia(recurso)) {
			valido = false;
			mensajeError("El tipo de ingreso se encuentra vacío");
		}

		if (esCadenaVacia(entidadRecurso)) {
			valido = false;
			mensajeError("Seleccione primero la entidad antes de agregar el ingreso");
		}

		sumaIngresosTmp += (valorIngreso + (valorIngreso2 > 0 ? valorIngreso2 : 0L)
				+ (valorIngreso3 > 0 ? valorIngreso3 : 0L));
		subTotalIngresos += sumaIngresosTmp + sumaTotalIngresos;

		if (proyectoActual.isHabilitadoParaFormalizacionInterna()) {
			if (esCadenaVacia(sedeSel)) {
				valido = false;
				mensajeError("Seleccione una sede antes de agregar el ingreso");
			}

			if (esCadenaVacia(facInstitutoSel)) {
				valido = false;
				mensajeError("Seleccione una facultad o instituto antes de agregar el ingreso");
			}

			Date fechaActual = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaActual);
			Long anioActual = Long.valueOf(cal.get(Calendar.YEAR));

			System.out.println("Anio Actual: " + anioActual);
			System.out.println("Fac: " + facInstitutoSel);

			if (anioVigIngreso == null) {
				valido = false;
				mensajeError("Debe ingresar un valor en el año de inicio de la vigencia");
			} else if (anioVigIngreso < 2013L) {
				valido = false;
				mensajeError("El año de inicio de la vigencia debe ser igual o superior a 2013");
			} else if (anioVigIngreso > anioActual) {
				valido = false;
				mensajeError("El año de inicio de la vigencia no puede ser superior al año actual");
			} /*
				 * if (!((Convocatoria)
				 * proyectoActual.getModalidad()).getPadre().getId().equals(392L) &&
				 * !((Convocatoria)
				 * proyectoActual.getModalidad()).getPadre().getId().equals(479L)) { if
				 * (subTotalIngresos > montoAprobadoProyecto) { valido = false;
				 * mensajeError("La suma de los ingresos (" + subTotalIngresos +
				 * ") no debe exceder el valor del 'Monto interno financiado' (" +
				 * montoAprobadoProyecto + ")"); } } else { String intern =
				 * proyectoActual.obtenerFinanciacionLegalizacion(entidadRecurso).getFuente()
				 * .getInternaExterna(); if (intern.equals("I") || intern.equals("O")) { if
				 * (subTotalIngresos > montoAprobadoProyecto) { valido = false;
				 * mensajeError("La suma de los ingresos (" + subTotalIngresos +
				 * ") no debe exceder el valor del 'Monto interno financiado' (" +
				 * montoAprobadoProyecto + ")"); } } else { if (subTotalIngresos >
				 * proyectoActual.getMontofinanciarCalculado()) { valido = false;
				 * mensajeError("La suma de los ingresos (" + subTotalIngresos +
				 * ") no debe exceder el valor del 'Monto Externo financiado' (" +
				 * proyectoActual.getMontofinanciarCalculado() + ")"); } } }
				 */
		} else {
			if (subTotalIngresos != null && convenioActual.getValorEjecucionUN() != null
					&& subTotalIngresos > convenioActual.getValorEjecucionUN()) {
				valido = false;
				mensajeError(uiDesembolsos,
						"La suma de los ingresos (" + subTotalIngresos
								+ ") excede el 'Valor total de la ejecución de recursos' ("
								+ convenioActual.getValorEjecucionUN() + ") ");
				System.out.println("subTotalIngresos: " + subTotalIngresos);
			}
		}

		if (valido) {

			Gasto gastoNuevo = new Gasto();
			gastoNuevo.setValor(valorIngreso);
			gastoNuevo.setValor2(valorIngreso2 > 0 ? valorIngreso2 : 0L);
			gastoNuevo.setValor3(valorIngreso3 > 0 ? valorIngreso3 : 0L);
			gastoNuevo.setCantidad(1);
			gastoNuevo.setVigencia(anio);
			gastoNuevo.setAnioVigencia(anioVigIngreso);
			Financiacion financiacion = null;
			financiacion = proyectoActual.obtenerFinanciacionLegalizacion(entidadRecurso);
			gastoNuevo.setFinanciacion(financiacion);

			Long id = Long.parseLong(recurso);
			TipoRubro tipoRubro = servicioFinanciacion.obtenerTipoRubro(id);
			tipoRubro.setPadre(servicioFinanciacion.obtenerTipoRubro(Long.parseLong(categoria)));

			gastoNuevo.setTipoRubro(tipoRubro);
			financiacion.adicionarGasto(gastoNuevo);

			Dependencia dep = servicioDependencia.obtenerDependencia(facInstitutoSel);
			gastoNuevo.setDependenciaIngreso(dep);

			sumaTotalIngresos = subTotalIngresos;
			valorIngreso = 0L;
			valorIngreso2 = 0L;
			valorIngreso3 = 0L;
			desembolso = "";
			anioVigIngreso = 2013L;
			entidadRecurso = null;
			recurso = null;
			sedeSel = null;
			facInstitutoSel = null;
		}

	}

	// subir archivos leg
	public String insertarArchivoLeg() {
		boolean band = true;
		List listaAr = servicioGeneral.obtenerObjetoXID("TipoArchivo", tipoArchivo.getId() + "");
		TipoArchivo tipoAr = (TipoArchivo) listaAr.get(0);

		if (!esListaVacia(listaArchivosLeg)) {
			for (int i = 0; i < listaArchivosLeg.size(); i++) {
				Archivo arc = listaArchivosLeg.get(i);
				if (arc.getTipoArchivo().getId().equals(tipoAr.getId())) {
					band = false;
				}
			}
		}

		if (band) {
			return insertarArchivoProyectoGenericoConTipos(archivoLeg, proyectoActual, listaArchivosLeg, tipoAr);
		} else {
			mensajeError("Ya se encuentra registrado un documento con ese tipo de archivo.");
			return "";
		}

	}

	public String eliminarArchivoLeg() {
		try {
			Long id = archivoSeleccionadoLeg.getId();
			Archivo archivoTemporal = servicioProyecto.obtenerArchivo(id);
			boolean entra = false;
			boolean elimina = false;
			if (archivoTemporal != null
					&& (archivoTemporal.getDatos() == null || archivoTemporal.getBytes().length <= 1)) {
				entra = true;
				elimina = eliminarArchivoProyectoGenerico(archivoTemporal.getId(), proyectoActual,
						archivoTemporal.getNombre());
			}
			if (!entra) {
				servicioGeneral.eliminarObjeto(archivoTemporal);
			} else if (elimina) {
				servicioGeneral.eliminarObjeto(archivoTemporal);
			}

			listaArchivosLeg.remove(archivoSeleccionadoLeg);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "archivos";
	}

	public void descargarArchivoLeg() {
		Long id = archivoSeleccionadoLeg.getId();
		descargarArchivoProyectoGenerico(id, proyectoActual.getId());
	}

	public void guardarFormalizacionParcial() {
		try {
			proyectoActual.setFechaLegalizacionParcial(new Date());
			if (guardarFormalizacion()) {
				mensajeInfo("La formalización ha sido guardada parcialmente.");
			} else {
				mensajeError(NO_GUARDADO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mensajeError(NO_GUARDADO);
		}
	}

	// Guardar final sin edición
	public void guardarFormalizacionFinal() {
		try {
			if (validarFormalizacion() && guardarFormalizacion()) {
				proyectoActual.setFechaLegalizacion(new Date());
				servicioGeneral.guardarObjeto(proyectoActual);
				edicionFormalizacionHabilitada = false;
				mensajeInfo("La formalización ha sido guardada correctamente.");
			} else {
				mensajeError(NO_GUARDADO + " Por favor verifique la información ingresada.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mensajeError(NO_GUARDADO);
		}
	}

	public boolean guardarFormalizacion() {
		try {

			if (!esCadenaVacia(proyectoActual.getPresentaInformeParcial())
					&& "NO".equals(proyectoActual.getPresentaInformeParcial())) {
				proyectoActual.eliminarCompromisoParciales();
			}

			agregarInformeFinal(false);
			servicioGeneral.guardarObjeto(proyectoActual);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean validarFormalizacion() {
		boolean valido = true;

		// Se validan los datos de informe parcial.
		if (!esCadenaVacia(proyectoActual.getPresentaInformeParcial())
				&& ProyectoInforme.PRESENTACION_INFORMES_SI.equals(proyectoActual.getPresentaInformeParcial())
				&& proyectoActual.getListaCompromisoInformesParciales().isEmpty()) {
			mensajeError(uiInformes, "Debe agregar al menos un informe parcial al proyecto.");
			valido = false;
		}

		if (this.proyectoActual.getFechaAti() == null && !"Y".equals(proyectoActual.getEsJornadaDocente())) {
			valido = false;
			mensajeError("Ingrese la fecha del acto administrativo o comunicación externa.");
		}

		if (esCadenaVacia(proyectoActual.getActoEntidad()) && !"Y".equals(proyectoActual.getEsJornadaDocente())) {
			valido = false;
			mensajeError("Seleccione el acto administrativo de aprobacion.");
		}

		if ((proyectoActual.getCuposDescuento() == null
				|| (proyectoActual.getCuposDescuento() != null && proyectoActual.getCuposDescuento().equals(0L)))
				&& !"Y".equals(proyectoActual.getEsJornadaDocente())
				&& !proyectoActual.getModalidad().getTipo().getId().equals("CL")) {
			valido = false;
			mensajeError("Por favor ingrese el numero de acto administrativo.");
		}

		if (esCadenaVacia(proyectoActual.getUnidadEjecutora())) {
			valido = false;
			mensajeError("Seleccione la dependencia que realizó la aprobación.");
		}

		if (this.proyectoActual.getFechaTentativaInicio() == null) {
			valido = false;
			mensajeError("Ingrese la fecha de inicio del proyecto.");
		}

		if (this.proyectoActual.getFechaInformeFinal() == null) {
			valido = false;
			mensajeError("Ingrese la fecha de presentación del informe final.");
		}

		if (esListaVacia(this.proyectoActual.getRecursosAdicionados())
				&& !"Y".equals(proyectoActual.getEsJornadaDocente())
				&& !proyectoActual.getModalidad().getTipo().getId().equals("CL")) {
			valido = false;
			mensajeError("Ingrese los ingresos del proyecto.");
		}

		if (StringUtils.isBlank(proyectoActual.getEmpresaEjecutora())
				&& !"Y".equals(proyectoActual.getEsJornadaDocente())
				&& !proyectoActual.getModalidad().getTipo().getId().equals("CL")) {
			valido = false;
			mensajeError("Ingrese el código empresa QUIPU de la Unidad Ejecutora de recursos.");
		}

		if (!StringUtils.isBlank(proyectoActual.getEmpresaEjecutora())
				&& (proyectoActual.getEmpresaEjecutora().length() < 3
						|| proyectoActual.getEmpresaEjecutora().length() > 5)) {
			valido = false;
			mensajeError(
					"El código empresa QUIPU de la Unidad Ejecutora de recursos debe tener entre 3 y 5 caracteres.");
		}
		if (!StringUtils.isBlank(proyectoActual.getEmpresaEjecutora())) {
			int tam = proyectoActual.getEmpresaEjecutora().trim().length();
			Pattern numerico = Pattern.compile("^[0-9]{" + tam + "}+$");
			Matcher m = numerico.matcher(proyectoActual.getEmpresaEjecutora().trim());
			if (!m.find()) {
				valido = false;
				mensajeError("El código empresa QUIPU de la Unidad Ejecutora de recursos debe ser numérico.");
			}
		}
		/*
		 * if (!((Convocatoria)
		 * proyectoActual.getModalidad()).getPadre().getId().equals(392L) &&
		 * !((Convocatoria)
		 * proyectoActual.getModalidad()).getPadre().getId().equals(479L)) { if
		 * (!sumaTotalIngresos.equals(montoAprobadoProyecto)) { valido = false;
		 * mensajeError("La suma de los ingresos debe ser igual al monto interno financiado."
		 * ); } } else if
		 * (!sumaTotalIngresos.equals(proyectoActual.getMontofinanciarCalculado())) {
		 * valido = false;
		 * mensajeError("La suma de los ingresos debe ser igual al monto interno financiado."
		 * ); }
		 */

		if (proyectoActual.getFechaFinalizacion() == null || proyectoActual.getFechaFinalizacion().equals("")) {
			mensajeError(uiInformes,
					"La fecha de finalización del proyecto no ha sido asignada, por favor hacer clic en el botón 'Calcular fechas' para asignarla.");
			valido = false;
		} else if (proyectoActual.getFechaInformeFinal().after(proyectoActual.getFechaFinalizacion())) {
			long diff = proyectoActual.getFechaInformeFinal().getTime() - fechaFinalProyecto.getTime();
			long diffDias = diff / 86400000;
			int dias = (int) Math.abs(diffDias);
			if (dias > 63) {
				mensajeError(uiInformes,
						"La fecha de entrega del informe final debe ser máximo 62 días después de la fecha de finalización del proyecto.");
				valido = false;
			}
		} else {
			mensajeError(uiInformes,
					"La fecha de entrega del informe final debe ser posterior a la fecha de finalización del proyecto.");
			valido = false;
		}

		if (esConvocatoriaEventos) {
			if (this.proyectoActual.getFechaInicioEjecucionEvento() == null) {
				valido = false;
				mensajeError("Ingrese la fecha de inicio de la ejecución del evento.");
			}
			if (this.proyectoActual.getFechaFinEjecucionEvento() == null) {
				valido = false;
				mensajeError("Ingrese la fecha de finalización de la ejecución del evento.");
			}

			Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(proyectoActual.getFechaInicioEjecucionEvento());
			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(proyectoActual.getFechaFinEjecucionEvento());

			if (proyectoActual.getFechaFinalizacion() == null || proyectoActual.getFechaFinalizacion().equals("")) {
				mensajeError(uiInformes,
						"La fecha de finalización del proyecto no ha sido asignada, por favor hacer clic en calcular fechas para asignarla.");
				valido = false;
			} else if (proyectoActual.getFechaInformeFinal().after(proyectoActual.getFechaFinalizacion())) {
				long diff = proyectoActual.getFechaInformeFinal().getTime() - fechaFinalProyecto.getTime();
				long diffDias = diff / 86400000;
				int dias = (int) Math.abs(diffDias);
				if (dias > 62) {
					mensajeError(uiInformes,
							"La fecha de entrega del informe final debe ser máximo 62 días después de la fecha de finalización del proyecto.");
					valido = false;
				}
			} else {
				mensajeError(uiInformes,
						"La fecha de entrega del informe final debe ser posterior a la fecha de finalización del proyecto.");
				valido = false;
			}
			int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
			int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

			if (diffMonth > 12) {
				valido = false;
				mensajeError("La fecha de finalización de la ejecución del evento debe ser menor a 12 meses. 1");
			}

			Calendar cal = new GregorianCalendar();
			cal.setTime(proyectoActual.getFechaInicioEjecucionEvento());
			cal.add(Calendar.YEAR, 1);

			Date fechafinAnioDespues = cal.getTime();

			if (proyectoActual.getFechaFinEjecucionEvento().compareTo(fechafinAnioDespues) > 0) {
				valido = false;
				mensajeError("La fecha de finalización de la ejecución del evento debe ser menor a 12 meses. 2");
			}
			if (proyectoActual.getFechaFinEjecucionEvento()
					.compareTo(proyectoActual.getFechaInicioEjecucionEvento()) < 0) {
				valido = false;
				mensajeError(
						"La fecha de finalización de la ejecución del evento debe ser anterior a la fecha de inicio de la ejecución del evento.");
			}
		}

		return valido;
	}

	/******************************************************************************
	 * GUARDAR LEGALIZACION DE PROYECTOS CONVOCATORIAS EXTERNAS
	 ******************************************************************************/
	// Guardar parcialmente
	public void guardarLegalizacionParcial() {
		try {
			if (!esCadenaVacia(estadoProyectoLegalizacion)) {
				// fecha legalizac parcial
				proyectoActual.setFechaLegalizacionParcial(new Date());

				// Estado
				EstadoProyecto nuevoEstadoProyecto = (EstadoProyecto) servicioGeneral
						.obtenerObjeto(new EstadoProyecto(), estadoProyectoLegalizacion);

				proyectoActual.setEstadoProyectoLegalizacion(nuevoEstadoProyecto);
				HistoricoEstadoLegalizacion helega = new HistoricoEstadoLegalizacion();
				helega.setEstadoProyecto(nuevoEstadoProyecto);
				helega.setFecha(new Date());
				helega.setResponsable(cargarPersonaActual());
				helega.setJustificacion(proyectoActual.getJustificacionExoneracionCostosInd());
				proyectoActual.adicionarHistoricoEstLega(helega);

				if (estadoProyectoLegalizacion.equals(EstadoProyecto.APROBADO)) {

					// Guardar legalización
					if (guardarLegalizacion()) {
						mensajeInfo("La información ha sido guardada correctamente.");
					} else {
						mensajeError("La información NO ha sido guardada.");
					}

				} else {
					try {
						// Guardar legalización
						if (estadoProyectoLegalizacion.equals(EstadoProyecto.APROBADO_OCAD)) {
							servicioGeneral.guardarObjeto(legalizacionOcad);
							if ("NO".equals(legalizacionOcad.getTieneProyectoExtension())) {
								guardarLegalizacion();
							}
						}
						servicioGeneral.guardarObjeto(proyectoActual);

						mensajeInfo("La información ha sido guardada correctamente.");

					} catch (Exception e) {
						e.printStackTrace();
						mensajeError("La información NO ha sido guardada.");
					}
				}

			} else {// es nulo el estado
				mensajeError("Seleccione primero el estado del proyecto. La información NO ha sido guardada.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			mensajeError("La información NO ha sido guardada.");
		}
	}

	public void habilitarModificacionesLegalizacion() {
		try {
			if (proyectoActual != null) {
				if ((proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.APROBADO)
						|| proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.ELEGIBLE)
						|| proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.APROBADO_OCAD))
						&& ((proyectoActual.getFechaLegalizacionParcial() != null)
								|| (proyectoActual.getFechaLegalizacion() != null))) {
					proyectoActual.setFechaLegalizacion(null);
					proyectoActual.setFechaLegalizacionParcial(null);
					servicioGeneral.guardarObjeto(proyectoActual);

					if (proyectoActual.getModalidad().isEsConvocatoriaLegalizacion()) {
						// Mensaje
						mensajeInfo("Se ha habilitado la modificación de la legalización.");
						edicionFormalizacionHabilitada = true;
					} else {
						// Mensaje
						mensajeInfo("Se ha habilitado la modificación de la formalización.");
						edicionFormalizacionHabilitada = true;
					}
				} else {
					if (proyectoActual.getModalidad().isEsConvocatoriaLegalizacion()) {
						// Mensaje
						mensajeInfo("No se ha habilitado la modificación de la legalización.");
						edicionFormalizacionHabilitada = false;
					} else {
						// Mensaje
						mensajeInfo("No se ha habilitado la modificación de la formalización.");
						edicionFormalizacionHabilitada = false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Guardar final sin edición
	public void guardarLegalizacionFinal() {
		if (proyectoActual != null) {

			// Se verifica que si es solo ejecución tecnica no se posibles ingresos o
			// desembolsos
			if (proyectoActual.getTipologiaProyecto() != null && proyectoActual.getTipologiaProyecto().equals("ET")) {
				if (!proyectoActual.getRecursosAdicionados().isEmpty()) {
					proyectoActual.getRecursosAdicionados().removeAll(proyectoActual.getRecursosAdicionados());
					sumaTotalIngresos = (long) 0;
				}
				if (!proyectoActual.getDesembolsosAdicionados().isEmpty()) {
					proyectoActual.getDesembolsosAdicionados().removeAll(proyectoActual.getDesembolsosAdicionados());
					sumaTotalDesembolsos = (long) 0;
				}
			}

			try {

				if (!esCadenaVacia(estadoProyectoLegalizacion)) {

					// Estado
					EstadoProyecto nuevoEstadoProyecto = (EstadoProyecto) servicioGeneral
							.obtenerObjeto(new EstadoProyecto(), estadoProyectoLegalizacion);

					proyectoActual.setEstadoProyectoLegalizacion(nuevoEstadoProyecto);

					if (!proyectoActual.getEstadoProyecto().getId().equals(nuevoEstadoProyecto.getId())) {
						proyectoActual.setEstadoProyecto(nuevoEstadoProyecto);

						HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
						EstadoProyecto ep = new EstadoProyecto();
						ep.setId(this.estadoProyectoLegalizacion);
						hepry.setEstadoProyecto(ep);
						hepry.setFecha(new Date());
						hepry.setResponsable(cargarPersonaActual());
						hepry.setJustificacion("Legalización");

						proyectoActual.adicionarHistorico(hepry);

						HistoricoEstadoLegalizacion helega = new HistoricoEstadoLegalizacion();
						helega.setEstadoProyecto(ep);
						helega.setFecha(new Date());
						helega.setResponsable(cargarPersonaActual());
						if (estadoProyectoLegalizacion.equals(EstadoProyecto.APROBADO)) {
							helega.setJustificacion("Legalización Aprobada");
							if ("Si".equals(proyectoActual.getTieneBiodiversidad())) {
								enviarCorreoCoordinadorBiodiversidad(414, proyectoActual);
							}
						} else if (estadoProyectoLegalizacion.equals(EstadoProyecto.NO_APROBADO)) {
							helega.setJustificacion(proyectoActual.getJustificacionExoneracionCostosInd());
						} else {
							helega.setJustificacion(proyectoActual.getJustificacionExoneracionCostosInd());
						}
						proyectoActual.adicionarHistoricoEstLega(helega);

					}

					if (estadoProyectoLegalizacion.equals(EstadoProyecto.APROBADO_OCAD)) {
						if (validarOcad()) {
							proyectoActual.setFechaLegalizacion(new Date());
							proyectoActual.setModalidad(servicioModalidad.obtenerModalidad(10L));
							edicionFormalizacionHabilitada = false;
							// 5. GUARDAR PROYECTO
							try {
								servicioGeneral.guardarObjeto(legalizacionOcad);
								servicioGeneral.guardarObjeto(proyectoActual);
							} catch (Exception e) {
								e.printStackTrace();
								System.out.println("ManejadorSeguimiento::guardarLegalizacion::GuardarProyectoActual");
								mensajeError("La información NO ha sido guardada.");
							}
						}

					}

					if (estadoProyectoLegalizacion.equals(EstadoProyecto.APROBADO)
							|| (estadoProyectoLegalizacion.equals(EstadoProyecto.APROBADO_OCAD)
									&& "NO".equals(legalizacionOcad.getTieneProyectoExtension()))) {
						if (validarLegalizacionAP()) {
							// fecha legalizac final
							proyectoActual.setFechaLegalizacion(new Date());
							proyectoActual.setPresentaInformeFinal("SI");
							// Guardar legalización
							if (guardarLegalizacion()) {

								// Histórico de estado proyecto
								try {
									servicioGeneral.guardarObjeto(proyectoActual);

									// OBLIGACIONES COMO COMPROMISOS
									if (convenioActual.getObligaciones() != null) {
										Iterator<ConvenioObligacion> i = convenioActual.getObligaciones().iterator();
										while (i.hasNext()) {
											ConvenioObligacion convenioObligacion = i.next();
											if (convenioObligacion.getFecha() != null && convenioObligacion.getFecha()
													.before(proyectoActual.getFechaFinalizacion())) {

												ProyectoCompromiso proyectoCompromisoObligacion = new ProyectoCompromiso();
												TipoInforme tipoInformeObligacion = new TipoInforme(
														TipoInforme.OBLIGACIONES);
												proyectoCompromisoObligacion.setTipoInforme(tipoInformeObligacion);
												proyectoCompromisoObligacion
														.setFechaVencimiento(convenioObligacion.getFecha());
												proyectoCompromisoObligacion
														.setCumplido(ProyectoCompromiso.NO_CUMPLIDO);
												proyectoCompromisoObligacion.setNumeroNotificaciones(0);

												proyectoCompromisoObligacion.setConvenioObligacion(convenioObligacion);
												proyectoActual
														.adicionarCompromisoProyecto(proyectoCompromisoObligacion);
												servicioGeneral.guardarObjeto(proyectoActual);
											}
										}
									}
									// se suspende en el envio de este correo el criterio empleado es que en el
									// campo funcionario esté en null. 21/09/2023
									// enviarCorreoInvestigadoresProyectoAP();

									// Notificacion labs asociados
									if (!proyectoActual.getLaboratorios().isEmpty()) {
										for (Laboratorio lab : (ArrayList<Laboratorio>) proyectoActual
												.getListaLaboratorios()) {
											enviarCorreoLaboratoriosProyecto(lab, proyectoActual,
													CorreoPlantilla.CORREO_NOTIFICACION_ASOCIACION_LAB_PROYECTO_APROBADO);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								// Mensaje
								mensajeInfo("La información ha sido guardada correctamente.");
								edicionFormalizacionHabilitada = false;

								// Alertas inicio de proyecto
								mensajeUsuario(personaActual, proyectoActual);

							} else {
								mensajeError("La información NO ha sido guardada.");
							}

						}
					} else if (estadoProyectoLegalizacion.equals(EstadoProyecto.NO_APROBADO)) {
						if (validarLegalizacionNoAprobado()) {
							try {
								proyectoActual.setFechaLegalizacion(new Date());

								// Guardar legalización
								servicioGeneral.guardarObjeto(proyectoActual);
								enviarCorreoNoAprobacionRegalias();

								// Mensaje
								mensajeInfo("La información ha sido guardada correctamente.");
								edicionFormalizacionHabilitada = false;
								mensajeInfo("");

							} catch (Exception e) {
								e.printStackTrace();
								mensajeError("La información NO ha sido guardada.");
							}
						}
					} else if (estadoProyectoLegalizacion.equals(EstadoProyecto.ELEGIBLE)) {
						try {
							// Guardar legalización
							proyectoActual.setFechaLegalizacion(new Date());
							servicioGeneral.guardarObjeto(proyectoActual);
							edicionFormalizacionHabilitada = false;
						} catch (Exception e) {
							e.printStackTrace();
							mensajeError("La información NO ha sido guardada.");
						}
					} else {
						mensajeError("Seleccione primero el estado del proyecto. La información NO ha sido guardada.");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				mensajeError("La información NO ha sido guardada.");
			}
		}
	}

	public void enviarCorreoNoAprobacionRegalias() {
		CorreoPlantilla correoPlantillaTemporal = cargarPlantilla(CorreoPlantilla.CORREO_EDICION_PROYECTO_REGALIAS);
		Correo correo = new Correo();

		correo.setOrigen(Correo.CORREO_HERMES);
		Persona persona = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		InvestigadorInterno coordinador = servicioPersona.obtenerInvestigadorInterno(persona.getId());

		correo.adicionarDireccion(persona.getEmail());
		correo.adicionarDireccion(investigadorPrincipal.getEmail());
		correo.setAsunto(correoPlantillaTemporal.getAsunto());
		correo.setAsunto(correo.getAsunto().replaceAll("<<ID>>", proyectoActual.getId().toString()));
		correo.setCuerpo(correoPlantillaTemporal.getCuerpo());
		correo.setCuerpo(correo.getCuerpo().replaceAll("<<ID>>", proyectoActual.getId().toString()));
		correo.setCuerpo(correo.getCuerpo().replaceAll("<<PRY>>", proyectoActual.getNombre()));
		correo.setCuerpo(correo.getCuerpo().replaceAll("<<COORDINADOR>>", persona.getNombreCompleto()));
		correo.setCuerpo(correo.getCuerpo().replaceAll("<<EXT>>", coordinador.getTelExtension()));
		correo.setCuerpo(
				correo.getCuerpo().replaceAll("<<FAC>>", coordinador.getDependencia().getFacultad().getNombre()));
		correo.setCuerpo(correo.getCuerpo().replaceAll("<<SED>>", coordinador.getDependencia().getSede().getNombre()));

		Iterator<Aval> i = avalesAsociadosProyecto.iterator();

		while (i.hasNext()) {
			Aval aval = i.next();
			if (aval.isEsAvalAprobadoParaLegalizacion() && aval.isEsConvocatoriaRegalias()) {

				try {

					String hql = "select #ce.entidad, #ce.nombre  from ConvocatoriaExterna ce where CE.id = "
							+ aval.getAviConvocatoria();
					List<ConvocatoriaExterna> listaConvocatoria = servicioGeneral
							.obtenerObjetosLimitado(ConvocatoriaExterna.class, hql);
					if (listaConvocatoria.get(0) != null) {
						correo.setCuerpo(correo.getCuerpo().replaceAll("<<CONVOCATORIA>>",
								listaConvocatoria.get(0).getNombre()));
						correo.setCuerpo(correo.getCuerpo().replaceAll("<<ENTIDAD>>",
								listaConvocatoria.get(0).getEntidad().getDescripcion()));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		servicioCorreo.enviarCorreo(correo);
	}

	public boolean validarOcad() {
		boolean valido = true;
		if (esCadenaVacia(legalizacionOcad.getRolUniversidad())) {
			valido = false;
			mensajeError("Indique si la Universidad actuará como ejecutora u operadora");
		} else {
			if (legalizacionOcad.getRolUniversidad().equals("EJ")) {
				if (esCadenaVacia(legalizacionOcad.getCodigoSigp())) {
					valido = false;
					mensajeError("Ingrese el Código SIGP – ACTeI");
				} else {
					try {
						double d = Double.parseDouble(legalizacionOcad.getCodigoSigp());
					} catch (NumberFormatException nfe) {
						valido = false;
						mensajeError("El Código SIGP – ACTeI debe ser un número válido");
					}
				}
			} else {
				if (esCadenaVacia(legalizacionOcad.getEntidadEjecutora())) {
					valido = false;
					mensajeError("Seleccione la entidad ejecutora");
				}
			}
		}

		if (esCadenaVacia(legalizacionOcad.getCodigoBpin())) {
			valido = false;
			mensajeError("Ingrese el Código BPIN");
		} else {
			try {
				double d = Double.parseDouble(legalizacionOcad.getCodigoBpin());
			} catch (NumberFormatException nfe) {
				valido = false;
				mensajeError("El Código BPIN debe ser un número válido");
			}
		}

		if (esCadenaVacia(legalizacionOcad.getOcadAprobo())) {
			valido = false;
			mensajeError("Ingrese OCAD que aprobó");
		}

		if (esCadenaVacia(legalizacionOcad.getOcadAprobo())) {
			valido = false;
			mensajeError("Ingrese OCAD que aprobó");
		}

		if (esCadenaVacia(legalizacionOcad.getNumeroAprobacionOcad())) {
			valido = false;
			mensajeError("Número de acuerdo mediante el cual se aprobó el proyecto en el OCAD");
		} else {
			try {
				double d = Double.parseDouble(legalizacionOcad.getNumeroAprobacionOcad());
			} catch (NumberFormatException nfe) {
				valido = false;
				mensajeError("El Número del acuerdo debe ser un número válido");
			}
		}

		if (legalizacionOcad.getFechaAprobacionOcad() == null
				|| esCadenaVacia(legalizacionOcad.getFechaAprobacionOcad().toString())) {
			valido = false;
			mensajeError("Ingrese la fecha del acuerdo de aprobación");
		}

		if (legalizacionOcad.getDirectorProyecto() == null || legalizacionOcad.getDirectorProyecto().getId() == null
				|| esCadenaVacia(legalizacionOcad.getDirectorProyecto().getId().getDocumento())) {
			valido = false;
			mensajeError("Debe ingresar el director del proyecto");
		}
		return valido;
	}

	// guardar básico
	public boolean guardarLegalizacion() throws ParseException {

		boolean band = true;

		// 2. INFORMACIÓN GENERAL
		// Agenda de conocimiento
		if (agendaProyectoActual == null || (agendaProyectoActual != null
				&& !agendaProyectoActual.getAgenda().getId().equals(agendaConocimientoId))) {
			List<AgendaConocimiento> listaAgendaConocimiento = servicioGeneral.obtenerObjetos(AgendaConocimiento.class,
					"from AgendaConocimiento where id = '" + agendaConocimientoId + "' ");
			if (!esListaVacia(listaAgendaConocimiento)) {

				AgendaConocimiento agendaConocimiento = (AgendaConocimiento) listaAgendaConocimiento.get(0);
				AgendaProyecto agendaProyectoNueva = new AgendaProyecto();
				agendaProyectoNueva.setOrden("1");
				agendaProyectoNueva.setProyecto(proyectoActual);
				agendaProyectoNueva.setAgenda(agendaConocimiento);

				// Se agrega nuevo objeto.
				servicioGeneral.guardarObjeto(agendaProyectoNueva);

				// Se elimina el anterior objeto.
				if (agendaProyectoActual != null && agendaProyectoActual.getAgenda() != null
						&& agendaProyectoActual.getAgenda().getId() != null
						&& agendaProyectoActual.getProyecto() != null) {
					servicioGeneral.eliminarObjeto(agendaProyectoActual);
				}

				agendaProyectoActual = agendaProyectoNueva;

			}
		}

		try {

			convenioActual.setIdProyecto(proyectoActual.getId());

			TipoConvenio tipo = new TipoConvenio();
			tipo.setId(2L);
			convenioActual.setTipo(tipo);

			List listaF = servicioGeneral.obtenerObjetos("select f from FuenteFinanciacion f where f.id = 1");
			if (listaF != null) {
				FuenteFinanciacion fte = (FuenteFinanciacion) listaF.get(0);
				if (fte != null) {
					convenioActual.setEntidad(fte);
				}
			}

			// Guardar convenio
			servicioGeneral.guardarObjeto(convenioActual);

		} catch (Exception e) {
			mensajeError("Ha ocurrido un error al momento de guardar la información del convenio.");
			e.printStackTrace();
			System.out.println("ManejadorSeguimiento::guardarLegalizacion::GuardarConvenio");
			band = false;
			proyectoActual.setFechaLegalizacion(null);
		}

		agregarInformeFinal(true);

		proyectoActual.limpiarCompromisos();

		// 5. GUARDAR PROYECTO
		try {
			servicioGeneral.guardarObjeto(proyectoActual);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ManejadorSeguimiento::guardarLegalizacion::GuardarProyectoActual");
			band = false;
		}

		return band;
	}

	/**
	 * Valida datos básicos para legalización de proyectos no aprobados.
	 * 
	 * @return
	 */
	public boolean validarLegalizacionNoAprobado() {
		boolean valido = true;

		if (estadoProyectoLegalizacion != null && "N".equals(estadoProyectoLegalizacion)) {

			if (esCadenaVacia(proyectoActual.getJustificacionExoneracionTransferencias())) {
				valido = false;
				mensajeError("Seleccione el motivo de la no aprobación.");
			}

			if ("O".equals(proyectoActual.getJustificacionExoneracionTransferencias())
					&& esCadenaVacia(proyectoActual.getMotivoEstadoProyectoOtro())) {
				valido = false;
				mensajeError("Ingrese el motivo de la no aprobación.");
			}

			if (esCadenaVacia(proyectoActual.getJustificacionExoneracionCostosInd())) {
				valido = false;
				mensajeError("Ingrese la justificación de la no aprobación.");
			}

		} else if (esCadenaVacia(proyectoActual.getJustificacionExoneracionCostosInd())) {
			valido = false;
			mensajeError("Ingrese el detalle del estado.");
		}
		return valido;
	}

	/**
	 * Validación de datos si se selecciona la opción de guardar definitivamente.
	 * 
	 * @return si es valido.
	 * @throws ParseException
	 */
	public boolean validarLegalizacionAP() throws ParseException {
		boolean valido = true;
		boolean banArc1;
		boolean banArc2;

		if (esCadenaVacia(proyectoActual.getTipologiaProyecto())) {
			valido = false;
			mensajeError("Seleccione el tipo de ejecución.");
		} else if ("ETF".equals(proyectoActual.getTipologiaProyecto())) {
			if (StringUtils.isBlank(proyectoActual.getEmpresaEjecutora())) {
				valido = false;
				mensajeError("Ingrese el código empresa QUIPU de la Unidad Ejecutora de recursos.");
			}
			if (!StringUtils.isBlank(proyectoActual.getEmpresaEjecutora())
					&& (proyectoActual.getEmpresaEjecutora().length() < 3
							|| proyectoActual.getEmpresaEjecutora().length() > 5)) {
				valido = false;
				mensajeError(
						"El código empresa QUIPU de la Unidad Ejecutora de recursos debe tener entre 3 y 5 caracteres.");
			}
			if (!StringUtils.isBlank(proyectoActual.getEmpresaEjecutora())) {
				int tam = proyectoActual.getEmpresaEjecutora().trim().length();
				Pattern numerico = Pattern.compile("^[0-9]{" + tam + "}+$");
				Matcher m = numerico.matcher(proyectoActual.getEmpresaEjecutora().trim());
				if (!m.find()) {
					valido = false;
					mensajeError("El código empresa QUIPU de la Unidad Ejecutora de recursos debe ser numérico.");
				}
			}
			if (esCadenaVacia(proyectoActual.getTipologia_sec())) {
				valido = false;
				mensajeError("Seleccione la tipología del proyecto.");
			}
			if (convenioActual.getValorTotalContrato().equals(0L)) {
				valido = false;
				mensajeError("Ingrese el valor total del convenio o contrato.");
			}
			if (esCadenaVacia(convenioActual.getSupervisorEntidad())) {
				System.out.println(convenioActual.getSupervisorEntidad());
				valido = false;
				mensajeError("Por favor seleccione la entidad que realiza supervisión.");
			}
			if (convenioActual.getValorEfectivoContrato().equals(0L)) {
				valido = false;
				mensajeError("Ingrese el valor en efectivo del convenio o contrato.");
			}
			if (convenioActual.getMontoEjecutarUN().equals(0L)) {
				valido = false;
				mensajeError("Ingrese el valor total a desembolsar a la Universidad.");
			}
			if (convenioActual.getValorEjecucionUN().equals(0L)) {
				valido = false;
				mensajeError("Ingrese el valor total de ejecución de recursos en la Universidad.");
			}
			if ("ETF".equals(proyectoActual.getTipologiaProyecto()) && !convenioActual.getValorEjecucionUN().equals(proyectoActual.getValorExternoEfectivoCalculado())) {
				valido = false;
				mensajeError(
						"El 'Valor total de ejecución de recursos en la Universidad' es diferente al monto externo financiado.");
			}
			if (!convenioActual.getMontoEjecutarUN().equals(sumaTotalIngresos)) {
				valido = false;
				mensajeError("El 'Valor total a desembolsar a la Universidad' es diferente a la suma de los ingresos.");
			}
			if (!proyectoActual.getValorExternoEfectivoCalculado().equals(sumaTotalIngresos)) {
				valido = false;
				mensajeError("El 'Monto Externo Financiado' es diferente a la suma de los ingresos.");
			}
			if (!convenioActual.getMontoEjecutarUN().equals(sumaTotalDesembolsos)) {
				valido = false;
				mensajeError("La suma de los desembolsos es diferente al valor total a desembolsar.");
			}
			if (!sumaTotalIngresos.equals(sumaTotalDesembolsos)) {
				valido = false;
				mensajeError("La suma de los desembolsos es diferente a la suma de los ingresos");
			}
			if (convenioActual.getCodEntidadExterna() == null || convenioActual.getCodEntidadExterna().equals("")
					|| convenioActual.getCodEntidadExterna().equals(0L)) {
				valido = false;
				mensajeError("Ingrese el código externo del proyecto.");
			}
		}

		if (esCadenaVacia(proyectoActual.getProgramaCYT())) {
			valido = false;
			mensajeError("Seleccione programa nacional de Ciencia y Tecnología del proyecto.");
		} else if ("120_11".equals(proyectoActual.getProgramaCYT())
				&& esCadenaVacia(proyectoActual.getOtroServicio())) {
			valido = false;
			mensajeError("Ingrese el programa nacional de Ciencia y Tecnología del proyecto.");
		}

		if (esCadenaVacia(proyectoActual.getExoneraInd())) {
			valido = false;
			mensajeError("Seleccione si el proyecto está relacionado con un programa macro.");
		} else if (this.proyectoActual.getExoneraInd() != null
				&& this.proyectoActual.getExoneraInd().equals(Proyecto.PROGRAMA_MARCO_SI)
				&& esCadenaVacia(proyectoActual.getProgramaMacro())) {
			mensajeError("Ingrese el nombre del programa macro.");
		}

		if (esCadenaVacia(proyectoActual.getUnidadEjecutora())) {
			valido = false;
			mensajeError("Seleccione la dependencia ejecutora de los recursos.");
		}

		if (esCadenaVacia(convenioActual.getDependenciaFirma())) {
			valido = false;
			mensajeError("Seleccione la dependencia competente para la firma del convenio.");
		}

		if (esCadenaVacia(proyectoActual.getActoEntidad())) {
			valido = false;
			mensajeError("Seleccione el acto administrativo de la entidad financiadora.");
		}

		if (this.proyectoActual.getFechaAti() == null) {
			valido = false;
			mensajeError("Ingrese la fecha del acto administrativo o comunicación externa.");
		}

		if (esCadenaVacia(convenioActual.getNombre())) {
			valido = false;
			mensajeError("Ingrese el nombre del contrato o convenio.");
		}

		if (esCadenaVacia(convenioActual.getObjeto())) {
			valido = false;
			mensajeError("Ingrese el objeto del convenio o contrato.");
		}

		if (esCadenaVacia(convenioActual.getDesignadoFirma())) {
			valido = false;
			mensajeError("Ingrese el nombre del designado para la firma del convenio.");
		}

		if (esCadenaVacia(convenioActual.getCargoDesignadoFirma())) {
			valido = false;
			mensajeError("Ingrese el cargo del designado para la firma del convenio.");
		}

		if (this.convenioActual.getVigenciaLegalizacion() == null
				|| this.convenioActual.getVigenciaLegalizacion().equals(0)
				|| this.convenioActual.getVigenciaLegalizacion() == 0L) {
			valido = false;
			mensajeError("Seleccione la vigencia del contrato o convenio.");
		}

		if (this.convenioActual.getFechaInicio() == null) {
			valido = false;
			mensajeError("Ingrese la fecha de legalización del contrato o convenio.");
		}

		if (this.proyectoActual.getFechaTentativaInicio() == null) {
			valido = false;
			mensajeError("Ingrese la fecha de inicio del proyecto.");
		}

		if (esCadenaVacia(convenioActual.getModalidadContratacion())) {
			valido = false;
			mensajeError("Seleccione la modalidad de contratación.");
		}

		if (this.proyectoActual.getFechaInformeFinal() == null) {
			valido = false;
			mensajeError("Ingrese la fecha de presentación del informe final.");
		}

		if (esListaVacia(proyectoActual.getRecursosAdicionados())
				&& proyectoActual.getTipologiaProyecto().equals("ETF")) {
			valido = false;
			mensajeError("Registre los ingresos del proyecto.");
		}

		if (esListaVacia(proyectoActual.getDesembolsosAdicionados())
				&& proyectoActual.getTipologiaProyecto().equals("ETF")) {
			valido = false;
			mensajeError("Registre los desembolsos del proyecto.");
		}

		if (proyectoActual.getTipologiaProyecto().equals("ETF")) {
			if (!this.convenioActual.getMontoEjecutarUN().equals(proyectoActual.getValorSolicitado())) {
				valido = false;
				mensajeError(
						"El Valor total a desembolsar a la universidad es diferente al valor de la fuente externa registrada en el proyecto, recomendamos realizar una revisión al proyecto antes de finalizar la legalización");
			}
		}

		if (!esListaVacia(proyectoActual.getListaEntidadesLegalizacion())) {
			FuenteFinanciacion fuente = proyectoActual.obtenerFirmanteConvenio();
			convenioActual.setEntidad(fuente);
			if (fuente == null) {
				valido = false;
				mensajeError("Ingrese la entidad firmante del convenio.");
			}
		} else {
			valido = false;
			mensajeError("Ingrese las entidades relacionadas.");
		}

		// Archivos 48,49,50,51,5,67,68
		if (!esListaVacia(listaArchivosLeg)) {
			banArc1 = banArc2 = false;
			for (int i = 0; i < listaArchivosLeg.size(); i++) {
				Archivo arc = listaArchivosLeg.get(i);
				if (arc.getTipoArchivo().getId() == 48) {
					banArc1 = true;
				}
				if (arc.getTipoArchivo().getId() == 50) {
					banArc2 = true;
				}
			}

			if (!banArc1) {
				valido = false;
				mensajeError("El documento 'propuesta aprobada por la entidad externa' es obligatorio.");
			}

			if (!banArc2) {
				valido = false;
				mensajeError(
						"El documento 'Acuerdo de voluntades, convenio o contrato con la entidad' es obligatorio.");
			}

		} else {
			valido = false;
			mensajeError(
					"Los documentos 'contrato o convenio con la entidad' y 'propuesta aprobada por la entidad externa' son obligatorios.");
		}

		return valido;
	}

	/******************************************************************************
	 * FIN GUARDAR LEGALIZACION DE PROYECTOS CONVOCATORIAS EXTERNAS
	 ******************************************************************************/

	public void mostrarCalculoProrroga() {
		fechaCalculaProrroga = null;
		mesesCalculo = "";
		diasCalculo = "";
		mesesCalculoVista = "";
		diasCalculoVista = "";
	}

	public void mostrarHistoricoCambioEstadoSolicitud() {
		historicoEstadoSolicitudes = servicioGeneral.obtenerHistoricoSolicitud(solicitudSeleccionada);
	}

	public void calcularProrroga() {
		if (fechaFinalProyecto != null && fechaCalculaProrroga != null) {
			long diferenciaDias = 0;
			int diferenciaMeses = 0;

			diferenciaDias = (fechaCalculaProrroga.getTime() - fechaFinalProyecto.getTime()) / (60 * 60 * 1000 * 24);

			int mesesAdicionales = 0;
			double mesesAdicionalesFraccion;
			double diasAdicionales = 0;

			if (diferenciaDias > 30) {
				if (diferenciaDias % 30 == 0) {
					mesesAdicionalesFraccion = diferenciaDias / 30;
					mesesAdicionales = (int) Math.floor(mesesAdicionalesFraccion);
				} else {
					mesesAdicionalesFraccion = (double) diferenciaDias / 30.0;
					mesesAdicionales = (int) Math.floor(mesesAdicionalesFraccion);
					diasAdicionales = Math.ceil((mesesAdicionalesFraccion - mesesAdicionales) * 30);
				}
			} else {
				mesesAdicionales = diferenciaMeses;
				diasAdicionales = diferenciaDias;
			}

			mesesCalculoVista = String.valueOf(mesesAdicionales);
			diasCalculoVista = String.valueOf((int) diasAdicionales);

			mesesCalculo = String.valueOf(diferenciaMeses);
			diasCalculo = String.valueOf((int) diferenciaDias);
		}
	}

	public void mensajeUsuario(Persona coordinador, Proyecto pry) {

		try {

			Correo correo = new Correo();
			CorreoPlantilla cp = cargarPlantilla(NUMERO_PLANTILLA_INICIO_PROYECTO);
			correo.setOrigen(Correo.CORREO_HERMES);
			correo.setAsunto(cp.getAsunto());
			correo.setCuerpo(cp.getCuerpo().replaceAll("<<PRY>>", pry.getId().toString()));

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String dateString = dateFormat.format(pry.getFechaTentativaInicio());

			correo.setCuerpo(correo.getCuerpo().replaceAll("<<FECHA>>", dateString));

			// Email del coordinador
			if (coordinador.getEmail() != null) {
				correo.adicionarDireccion(coordinador.getEmail());
			}

			// Email del investigador Principal del proyecto
			Investigador inv = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(pry.getId());
			if (inv != null) {
				if (inv.getEmail() != null) {
					correo.adicionarDireccion(inv.getEmail());
				}
			}

			// Enviar correo
			// correo.adicionarCopiaOculta(Correo.CORREO_HERMES);

			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("Se ha notificado al investigador principal del proyecto.");
			context.addMessage("datosGuardados", mensaje);

		} catch (Exception e) {
			System.out.print(e);
		}
	}

	/**
	 * Eliminar dependencia.
	 */
	public void eliminarDependencia() {
		proyectoActual.borrarDependencia(dependenciaAreaResponsabilidadSeleccionada);
	}

	/**
	 * Se elimina entidad
	 */
	public void eliminarEntidad() {
		proyectoActual.eliminarFuente(entidadSeleccionada);
		cargarListadoEntidadesItem(proyectoActual.getListaEntidadesLegalizacion(), true);
	}

	// lmom
	public void eliminarDesembolso() {
		Financiacion financiacion = desembolsoSeleccionado.getFinanciacion();
		financiacion.getGastos().remove(desembolsoSeleccionado);
		Long sumaTmpValorDesembolVig = desembolsoSeleccionado.getValor()
				+ (desembolsoSeleccionado.getValor2() == null ? 0L : desembolsoSeleccionado.getValor2())
				+ (desembolsoSeleccionado.getValor3() == null ? 0L : desembolsoSeleccionado.getValor3());
		sumaTotalDesembolsos = sumaTotalDesembolsos - sumaTmpValorDesembolVig;
		System.out.println("sumaTotalDesembolsos " + sumaTotalDesembolsos);
	}

	// lmom
	public void eliminarRecurso() {
		Financiacion financiacion = recursoSeleccionado.getFinanciacion();
		financiacion.getGastos().remove(recursoSeleccionado);
		Long sumaTmpValorIngresoVig = recursoSeleccionado.getValor()
				+ (recursoSeleccionado.getValor2() == null ? 0L : recursoSeleccionado.getValor2())
				+ (recursoSeleccionado.getValor3() == null ? 0L : recursoSeleccionado.getValor3());
		sumaTotalIngresos = sumaTotalIngresos - sumaTmpValorIngresoVig;
		System.out.println("sumaTotalIngresos " + sumaTotalIngresos);
	}

	public void agregarTramite() {

		codigoQuipu = proyectoActual.getCodigoQuipu();

		solicitudSeleccionada.setResponsable(coordinadorSeguimiento);
		solicitudSeleccionada.setFechaAprobacion(new Date());
		if (solicitudSeleccionada.getTipoSolicitud().getId().intValue() == 23) {
			mostrarCodigoQuipu = true;
		} else {
			mostrarCodigoQuipu = false;
		}

		TramiteSolicitud tramite = new TramiteSolicitud();
		tramite.setSolicitud(solicitudSeleccionada);
		tramite.setAsesor(this.asesor);
		tramite.setFecha(new Date());

		solicitudSeleccionada.adicionarTramite(tramite);

	}

	public void agregarProrrogaProyecto() {

		ProyectoProrroga proyectoProrroga = new ProyectoProrroga();
		Persona responsable = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		proyectoProrroga.setProyecto(proyectoActual);
		proyectoProrroga.setResponsable(responsable);
		proyectoProrroga.setFecha(new Date());

		proyectoActual.getProrrogasProyecto().add(proyectoProrroga);

	}

	public void eliminarProrrogaProyecto() {

		mensajeActualizacion = "";
		if (prorrogaSeleccionada != null) {
			prorrogaSeleccionada.setEstado(ProyectoProrroga.BORRADO);
			prorrogaSeleccionada.setFechaEliminacion(new Date());
			prorrogaSeleccionada.setResponsableEliminacion(personaActual);
			verificarPermisoMarcoEliminarProrroga(prorrogaSeleccionada);
		}
	}

	public void verificarPermisoMarcoEliminarProrroga(ProyectoProrroga prorroga) {
		try {
			String hql = "select #id p.id from Proyecto p where p.codigoDib = " + proyectoActual.getId();
			List<Proyecto> proyectos = servicioGeneral.obtenerObjetosLimitado(Proyecto.class, hql);
			if (!esListaVacia(proyectos)) {
				for (int j = 0; j < proyectos.size(); j++) {
					Proyecto proyectoPermiso = servicioProyecto.obtenerProyecto(proyectos.get(j).getId(),
							ProyectoDAOHibernate.TODO_POR_ID);
					for (int i = 0; i < proyectoPermiso.getProrrogasProyecto().size(); i++) {
						ProyectoProrroga pp = proyectoPermiso.getListaProrrogas().get(i);
						if (prorroga != null && prorroga.getAplicadaABiodiversidad().equals(1L)) {
							if (pp.getDuracion().equals(prorroga.getDuracion())
									&& pp.getDias().equals(prorroga.getDias())) {
								pp.setEstado(ProyectoProrroga.BORRADO);
								pp.setFechaEliminacion(new Date());
								pp.setResponsableEliminacion(personaActual);
								Integer duracion = prorroga.getDuracion().intValue();
								Integer duracionDias = prorroga.getDias().intValue();
								proyectoPermiso.setDuracionAcumulada(proyectoPermiso.getDuracionAcumulada() - duracion);
								proyectoPermiso.setDuracionDiasAcumulada(
										proyectoPermiso.getDuracionDiasAcumulada() - duracionDias);

								if (!esListaVacia(proyectoPermiso.getListaCompromisos())) {
									Iterator<ProyectoCompromiso> a = proyectoPermiso.getListaCompromisos().iterator();
									while (a.hasNext()) {
										ProyectoCompromiso compromiso = a.next();
										if (compromiso.getCumplido().equals(ProyectoCompromiso.NO_CUMPLIDO)
												&& compromiso.getTipoInforme().getId()
														.equals(TipoInforme.INFORME_FINAL)) {
											Date fechaVencProrroga = (Date) compromiso.getFechaVencimiento().clone();

											// Se agregan los meses de prorroga.
											fechaVencProrroga.setMonth(fechaVencProrroga.getMonth() - duracion);
											fechaVencProrroga.setDate(fechaVencProrroga.getDate() - duracionDias);

											// Se asgina nuevo valor al compromiso y se guarda
											compromiso.setFechaVencProrroga(fechaVencProrroga);
											compromiso.setNumeroNotificaciones(0);
										}
									}
								}
								servicioGeneral.guardarObjeto(proyectoPermiso);
								servicioGeneral.guardarObjeto(pp);
								break;
							}
						}
					}
				}

			}

		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void eliminarArchivoProyecto() {
		// TODO: OJO: TOCA BORRAR LOS ARCHIVOS DEL DISCO
		Long id = archivoResumenSeleccionado.getId();
		Archivo archivoTemporal = servicioProyecto.obtenerArchivo(id);
		servicioGeneral.eliminarObjeto(archivoTemporal);
		listaArchivosProyecto.remove(archivoResumenSeleccionado);
		FacesMessage msg = new FacesMessage(
				"El archivo " + archivoTemporal.getNombre() + " fue eliminado exitosamente.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void enviarCorreo() {
		String observacion = "Envio de correo a las siguientes direcciones :" + '\n';
		Correo correo = new Correo();
		correo.setAsunto(asuntoCorreo);
		correo.setCuerpo(cuerpoCorreo);
		correo.setOrigen(origenCorreo);
		correo.setCopias(copiaCorreo);
		if ("P".equals(destinoCorreo)) {
			correo.adicionarPersona(investigadorPrincipal);
			observacion += investigadorPrincipal.getEmail() + '\n' + '\n';
		}
		observacion += "Tipo información : " + asuntoAlertaCorreo + '\n' + '\n';
		observacion += "Asunto : " + '\n' + asuntoCorreo + '\n' + '\n';
		observacion += "Detalle : " + '\n' + cuerpoCorreo;

		if (this.servicioCorreo.enviarCorreo(correo)) {
			mensajeActualizacion = "Correo enviado con éxito";

			/***************************************************************************
			 * AGREGA OBSERVACION REFERENTE AL ENVIO DEL CORREO
			 ***************************************************************************/
			ObservacionProyecto observacionProyecto = new ObservacionProyecto();
			observacionProyecto.setAsesor(this.asesor);
			observacionProyecto.setProyecto(proyectoActual);
			observacionProyecto.setFecha(new Date());
			observacionProyecto.setDescripcion(observacion);
			servicioGeneral.guardarObjeto(observacionProyecto);
			tablaObservaciones
					.setFirst(tablaObservaciones.getRowCount() == 0 ? 0 : tablaObservaciones.getRowCount() - 1);
		} else
			mensajeActualizacion = "Ocurrio un error al enviar el correo";

		destinoCorreo = "P";
		asuntoAlertaCorreo = "Informacion general";
		asuntoCorreo = "Proyecto " + proyectoActual.getId();

		inicializarCuerpoCorreo();
	}

	public void enviarCorreoInvestigadoresProyectoAP() {
		CorreoPlantilla cp = cargarPlantilla(CorreoPlantilla.CORREO_NOTIFICACION_VINCULACION_DOCENTES_PRY);
		String asunto = cp.getAsunto();
		String idProyecto = proyectoActual.getId().toString();
		String nombreDirector = proyectoActual.getListaInvestigadorPrincipal().get(0).getInvestigador()
				.getNombreCompleto();
		Convocatoria con = (Convocatoria) proyectoActual.getModalidad();
		if (!proyectoActual.getListaInvestigadoresProyecto().isEmpty()) {
			for (Iterator iterator = proyectoActual.getListaInvestigadoresProyecto().iterator(); iterator.hasNext();) {
				String cuerpo;
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				asunto = asunto.replaceAll("<<ID_PROYECTO>>", idProyecto);
				correo.setAsunto(asunto);
				cuerpo = cp.getCuerpo();
				cuerpo = cuerpo.replaceAll("<<ID_PROYECTO>>", idProyecto);
				cuerpo = cuerpo.replaceAll("<<DIRECTOR>>", nombreDirector);
				if (con != null && !con.getId().equals(Convocatoria.MODALIDAD_CONVOCATORIA_EXTERNA)
						&& con.getPadre() != null) {
					cuerpo = cuerpo.replaceAll("<<CONVOCATORIA>>", con.getPadre().getTitulo());
				} else {
					Aval avi = (Aval) servicioGeneral.obtenerAvalesProyecto(idProyecto).get(0);
					String hql = "select c from ConvocatoriaExterna c where c.numero =" + avi.getAviConvocatoria();
					ConvocatoriaExterna convExt = (ConvocatoriaExterna) servicioGeneral.obtenerObjetos(hql).get(0);
					cuerpo = cuerpo.replaceAll("<<CONVOCATORIA>>", convExt.getNombre());
				}
				InvestigadorProyecto invPry = (InvestigadorProyecto) iterator.next();
				if (invPry.getInvestigador().getEsFuncionario() == null) {
					String dirCorreoInv = invPry.getInvestigador().getEmail();
					String tipoInv = invPry.getTipo().getNombre();
					cuerpo = cuerpo.replaceAll("<<TIPO_INVESTIGADOR>>", tipoInv);
					correo.setCuerpo(cuerpo);
					// correo.adicionarDireccion(Correo.CORREO_HERMES);
					correo.adicionarDireccion(dirCorreoInv);
					servicioCorreo.enviarCorreo(correo);
				}
			}
		}
	}

	private void inicializarCuerpoCorreo() {

		/********************************************************************************
		 * CARGA EL LUGAR DE EJECUCION
		 ********************************************************************************/

		Set<Ciudad> ciudades = proyectoActual.getCiudades();
		if (ciudades != null) {

			Iterator<Ciudad> iterador = ciudades.iterator();
			while (iterador.hasNext()) {
				ciudadProyecto = (Ciudad) iterador.next();
			}

		} else {

			ciudadProyecto = servicioProyecto.obtenerCiudadProyecto(proyectoActual.getId());
		}

		if (ciudadProyecto != null)
			cuerpoCorreo = ciudadProyecto.getNombre().toUpperCase() + ", "
					+ formatoFecha.format(new Date()).toUpperCase() + '\n' + '\n';
		else {
			cuerpoCorreo = "CIUDAD" + ", " + formatoFecha.format(new Date()).toUpperCase() + '\n' + '\n';
		}

		cuerpoCorreo += "Proyecto " + proyectoActual.getId() + " : " + proyectoActual.getNombre() + '\n' + '\n';
		cuerpoCorreo += "Cordial saludo." + '\n' + '\n';
		cuerpoCorreo += "<<INGRESE EL DETALLE DEL CORREO>>" + '\n' + '\n';
		cuerpoCorreo += "Cordialmente : " + '\n' + '\n' + asesor.getNombre1() + " " + asesor.getNombre2() + " "
				+ asesor.getApellido1() + " " + asesor.getApellido2() + '\n';

		if (ciudadProyecto != null) {
			cuerpoCorreo += "División Investigación " + ciudadProyecto.getNombre();
		} else {
			cuerpoCorreo += "División Investigación";
		}
	}

	public List getOpcionesDominioRespuestaSolicitud() {
		if (opcionesDominioRespuestaSolicitud == null) {
			opcionesDominioRespuestaSolicitud = new ArrayList();
			Iterator i = dominioRespuestaSolicitud.entrySet().iterator();

			while (i.hasNext()) {
				Map.Entry entry = (Map.Entry) i.next();
				opcionesDominioRespuestaSolicitud.add(new SelectItem(entry.getValue(), (String) entry.getKey()));
			}
		}
		return opcionesDominioRespuestaSolicitud;
	}

	public String salir() {
		sesion.removeAttribute("manejadorSeguimiento");
		return "inbox";
	}

	public void cambiarOrdenador() {
		List<Dependencia> listaDependenciaOrdenador;

		if (ordenadorTipoResolucion.equals(ProyectoCarta.ORDENADOR_TIPO_DECANO)
				|| ordenadorTipoResolucion.equals(ProyectoCarta.ORDENADOR_TIPO_DECANA)
				|| ordenadorTipoResolucion.equals(ProyectoCarta.ORDENADOR_TIPO_DECANO_ENCARGADO)
				|| ordenadorTipoResolucion.equals(ProyectoCarta.ORDENADOR_TIPO_DECANA_ENCARGADA)) {
			// Facultades
			listaDependenciaOrdenador = servicioGeneral.obtenerObjetos(Dependencia.class,
					"from Dependencia e where e.estado='A' "
							+ " and ((e.esDepartamento='N' and (e.esFacultad = 'Y')) or (e.esInstitutoInterfacultad = 'Y')) "
							+ " and e.nombre not like '%Centro%' and e.nombre not like '%Consejo%' "
							+ " and e.nombre not like '%Direcc%' and e.nombre not like '%Institu%' order by e.nombre");

		} else if (ordenadorTipoResolucion.equals(ProyectoCarta.ORDENADOR_TIPO_VICERRECTOR)
				|| ordenadorTipoResolucion.equals(ProyectoCarta.ORDENADOR_TIPO_VICERRECTORA)) {
			// Sedes
			listaDependenciaOrdenador = servicioGeneral.obtenerObjetos(Dependencia.class,
					"from Dependencia e where e.estado='A' " + " and (e.esSede = 'Y') " + " order by e.nombre");

		} else if (ordenadorTipoResolucion.equals(ProyectoCarta.ORDENADOR_TIPO_DIRECTORA)
				|| ordenadorTipoResolucion.equals(ProyectoCarta.ORDENADOR_TIPO_DIRECTOR)) {
			// Sedes
			listaDependenciaOrdenador = servicioGeneral.obtenerObjetos(Dependencia.class,
					"from Dependencia e where e.estado='A' "
							+ " and e.nombre like '%Direcc%' and e.nombre like '%Institu%' order by e.nombre");

		} else {
			// Institutos.
			listaDependenciaOrdenador = servicioGeneral.obtenerObjetos(Dependencia.class,
					"from Dependencia e where e.estado='A'"
							+ " and (upper(e.nombre) like '%CENTRO%' or upper(e.nombre) like '%INST%' order by e.nombre");
		}

		// Se crea lista de dependencias de acuerdo al tipo de ordenador.
		if (!esListaVacia(listaDependenciaOrdenador)) {
			facultadResolucionItems = new SelectItem[listaDependenciaOrdenador.size()];
			for (int i = 0; i < listaDependenciaOrdenador.size(); i++) {
				Dependencia dd = (Dependencia) listaDependenciaOrdenador.get(i);
				facultadResolucionItems[i] = new SelectItem(dd.getId(), dd.getNombre());
			}
		}
	}

	private void cargarTiposActosAdministrativos() {
		// Cargar los tipos de actos administrativos
		listaTiposActoAdministrativo = servicioGeneral.obtenerDominioDetalle(Dominio.TIPOS_ACTO_ADMINISTRATIVO, true);
		tipoActoAdministrativoItem = crearListaItems(listaTiposActoAdministrativo);
	}

	private void cargarTiposOrdenadores() {
		ordenadorTipoLista = servicioGeneral.obtenerDominioDetalle(Dominio.TIPO_ORDENADOR_GASTO);
		if (ordenadorTipoLista != null) {
			DominioDetalle dominioDetalle = ordenadorTipoLista.get(0);
			ordenadorTipoResolucion = dominioDetalle.getIdentificador().getTipo();
			ordenadorTipoItems = crearListaItems(ordenadorTipoLista);
		}
	}

	private boolean cargarDatosEstudianteProyecto(String cuerpo) {

		List<Estudiante> listaEstudiantes = new ArrayList<Estudiante>();

		String nombreCarrera = "";
		String nombreEstudiante = "";

		// ADICIONAL COINVESTIGADOR
		Iterator<InvestigadorProyecto> inves = proyectoActual.getInvestigadoresProyecto().iterator();

		try {
			while (inves.hasNext()) {
				InvestigadorProyecto invP = (InvestigadorProyecto) inves.next();
				if (invP.getTipo() != null && servicioGeneral.esEstudiante(invP.getTipo().getId())) {

					List<Estudiante> est = servicioGeneral.obtenerObjetos(Estudiante.class,
							"from Estudiante e " + "where e.id.documento='"
									+ invP.getInvestigador().getId().getDocumento() + "' and e.id.tipoDocumento='"
									+ invP.getInvestigador().getId().getTipoDocumento() + "'");

					if (Boolean.FALSE.compareTo(esListaVacia(est)) == 0) {

						Estudiante e = (Estudiante) est.get(0);
						nombreEstudiante = nombreEstudiante + " " + e.getNombre1() + " " + e.getApellido1() + " "
								+ e.getApellido2();
						nombreCarrera = e.getNombreCarrera();
						listaEstudiantes.add(e);

					}
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		if (Boolean.FALSE.compareTo(esListaVacia(listaEstudiantes)) == 0) {
			estudianteItem = new SelectItem[listaEstudiantes.size()];
			int i = 0;
			for (Estudiante e : listaEstudiantes) {
				estudianteItem[i++] = new SelectItem(e.getId().getDocumento(),
						e.getId().getTipoDocumento() + "-" + e.getId().getDocumento() + " " + e.getNombre1() + " "
								+ e.getNombre2() + " " + e.getApellido1() + " " + e.getApellido2());
			}
			cuerpo = cuerpo.replaceAll("<<ESTUDIANTE>>", nombreEstudiante);
			cuerpo = cuerpo.replaceAll("<<PROGRAMA>>", nombreCarrera);

			return true;
		}

		return false;
	}

	private boolean cargarDatosJIExternosProyecto(String cuerpo) {

		List<Persona> listaExternos = new ArrayList<Persona>();

		String nombreCarrera = "";
		String nombreEstudiante = "";

		// ADICIONAL COINVESTIGADOR
		Iterator<InvestigadorProyecto> inves = proyectoActual.getInvestigadoresProyecto().iterator();

		try {
			while (inves.hasNext()) {
				InvestigadorProyecto invP = (InvestigadorProyecto) inves.next();
				if (invP.getTipo() != null
						&& invP.getTipo().getId().equals(TipoInvestigador.JOVEN_INVESTIGADOR_EGRESADO)) {

					List<Persona> per = servicioGeneral.obtenerObjetos(Persona.class,
							"from Persona p " + "where p.id.documento='" + invP.getInvestigador().getId().getDocumento()
									+ "' and p.id.tipoDocumento='" + invP.getInvestigador().getId().getTipoDocumento()
									+ "'");

					if (Boolean.FALSE.compareTo(esListaVacia(per)) == 0) {

						Persona perExt = (Persona) per.get(0);
						nombreEstudiante = nombreEstudiante + " " + perExt.getNombre1() + " " + perExt.getApellido1()
								+ " " + perExt.getApellido2();
						nombreCarrera = "Externo";
						listaExternos.add(perExt);

					}
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		if (Boolean.FALSE.compareTo(esListaVacia(listaExternos)) == 0) {
			jovenInvExternoItem = new SelectItem[listaExternos.size()];
			int i = 0;
			for (Persona p : listaExternos) {
				jovenInvExternoItem[i++] = new SelectItem(p.getId().getDocumento(),
						p.getId().getTipoDocumento() + "-" + p.getId().getDocumento() + " " + p.getNombre1() + " "
								+ p.getNombre2() + " " + p.getApellido1() + " " + p.getApellido2());
			}
			cuerpo = cuerpo.replaceAll("<<ESTUDIANTE>>", nombreEstudiante);
			cuerpo = cuerpo.replaceAll("<<PROGRAMA>>", nombreCarrera);

			return true;
		}

		return false;

	}

	private boolean cargarDatosAPlantillaCorreo(int id) {

		// Se carga plantilla
		correoActual = cargarPlantilla(id);

		String correo = correoActual.getCuerpo().replaceAll("<<FECHA>>", Fecha.fechaActual());
		String asunto = correoActual.getAsunto();

		if (listaProyectosCoordinador != null) {

			for (int i = 0; i < listaProyectosCoordinador.size(); i++) {
				proyectoCoordinadorUno = (ProyectoCoordinador) listaProyectosCoordinador.get(i);
			}
		}

		Persona pe = this.servicioProyecto.obtenerInvestigadorPrincipalXProyecto(this.proyectoActual.getId());
		List inv = servicioGeneral.obtenerListaObjetosWhere("Persona p", "where p.id.documento='"
				+ pe.getId().getDocumento() + "' and p.id.tipoDocumento='" + pe.getId().getTipoDocumento() + "'");

		if (!esListaVacia(inv)) {
			Persona e = (Persona) inv.get(0);
			String investigador = e.getNombre1() + " " + e.getApellido1() + " " + e.getApellido2();
			correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);
		}
		String nombre = Matcher.quoteReplacement(this.proyectoActual.getNombre());
		correo = correo.replaceAll("<<TITULO>>", nombre);

		correo = correo.replaceAll("<<ID>>", String.valueOf(this.proyectoActual.getId()));
		asunto = asunto.replaceAll("<<ID>>", String.valueOf(this.proyectoActual.getId()));

		if (id == CorreoPlantilla.CERTIFICADO_MOVILIZACION) {
			String radicado = "";
			try {

				String hql = "select #radicadoAnla s.radicadoAnla from Solicitud s where s.id = " + codigoSolicitud;
				List<Solicitud> listaSolicitud = servicioGeneral.obtenerObjetosLimitado(Solicitud.class, hql);
				if (listaSolicitud.get(0) != null) {
					radicado = listaSolicitud.get(0).getRadicadoAnla();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			asunto = asunto.replaceAll("<<RADICADO>>", radicado);
			int startPos = proyectoActual.getNombre().indexOf('\'');
			int endPos = proyectoActual.getNombre().indexOf('\'', startPos + 1);

			correo = correo.replaceAll("<<NOMBRE_ASOCIADO>>",
					proyectoActual.getNombre().substring(startPos + 1, endPos));
			String dependencia = (String.valueOf(Dependencia.ID_VICERRECTORIA));
			long consecutivoCertificado = 0;
			;
			try {
				consecutivoCertificado = servicioGeneral.consultaUltimoIdPorDependenciaTipoCarta(dependencia,
						new Date(), String.valueOf(TipoCarta.CERTIFICADO_MOVILIZACION));
				List<ProyectoCarta> cartasSinEnviar = servicioGeneral.obtenerObjetosLimitado(ProyectoCarta.class,
						"select #seq pc.seq from ProyectoCarta pc where pc.proyecto.id = '" + proyectoActual.getId()
								+ "' and pc.idSolicitud = '" + codigoSolicitud + "' " + "and pc.estadoCarta.id = '"
								+ EstadoCarta.SIN_ENVIAR + "'");
				if (!esListaVacia(cartasSinEnviar)) {
					consecutivoCertificado = Long.parseLong(cartasSinEnviar.get(0).getSeq());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			correo = correo.replaceAll("<<ID_CERTIFICADO>>", String.valueOf(consecutivoCertificado));

			if (proyectoActual.getEsPermisoMarco()) {
				asunto = asunto.replaceAll("<<TIPO>>", "proyecto");
				correo = correo.replaceAll("<<NOMBRE_ASOCIADO>>",
						"el proyecto " + proyectoActual.getNombre().substring(startPos + 1, endPos));
				correo = correo.replaceAll("<<ID_PROYECTO>>", String.valueOf(this.proyectoActual.getCodigoDib()));
			} else {
				asunto = asunto.replaceAll("<<TIPO>>", "asignatura");
				correo = correo.replaceAll("<<NOMBRE_ASOCIADO>>",
						"la asignatura " + proyectoActual.getNombre().substring(startPos + 1, endPos));
				correo = correo.replaceAll("<<ID_PROYECTO>>", String.valueOf(this.proyectoActual.getId()));
			}
		}

		correoActual.setAsunto(asunto);

		Persona coordinador = this.servicioProyecto.obtenerCoordinadorProyecto(this.proyectoActual.getId());
		if (coordinador != null) {
			correo = correo.replaceAll("<<COORDINADOR>>",
					coordinador.getNombre1() + " " + coordinador.getApellido1() + " " + coordinador.getApellido2());
		} else {
			correo = correo.replaceAll("<<COORDINADOR>>", "");
		}
		if (id == CorreoPlantilla.FINALIZACION_PRINCIPAL) {
			correo = correo.replaceAll("<<EXT>>",
					(esCadenaVacia(coordinador.getTelefono()) ? "11111 (Opción 1)" : coordinador.getTelefono()));
		}
		if (this.proyectoActual != null) {

			// Se cargan los datos de la convoclatoria.
			Modalidad mod = this.proyectoActual.getModalidad();
			if (mod instanceof Convocatoria) {

				correo = correo.replaceAll("<<MODALIDAD>>", ((Convocatoria) mod).getTitulo());
				Convocatoria con = (Convocatoria) mod;
				if (con != null && con.getPadre() != null) {
					correo = correo.replaceAll("<<CONVOCATORIA>>", con.getPadre().getTitulo());
				}
			} else if (mod instanceof JornadaDocente) {

				correo = correo.replaceAll("<<CONVOCATORIA>>", ((JornadaDocente) mod).getDescripcion());
				correo = correo.replaceAll("<<MODALIDAD>>", ((JornadaDocente) mod).getDescripcion());

			} else if (mod instanceof Contrapartida) {
				correo = correo.replaceAll("<<CONVOCATORIA>>", ((Contrapartida) mod).getNombre());
				correo = correo.replaceAll("<<MODALIDAD>>", ((Contrapartida) mod).getNombre());

			} else if (mod instanceof Registro) {
				correo = correo.replaceAll("<<CONVOCATORIA>>", ((Registro) mod).getNombre());
				correo = correo.replaceAll("<<MODALIDAD>>", ((Registro) mod).getNombre());
			} else {
				correo = correo.replaceAll("<<CONVOCATORIA>>", "sin nombre");
				correo = correo.replaceAll("<<MODALIDAD>>", "sin nombre");

			}

			// Se cargan los datos de financiacion
			saldoFinanciacion = String.valueOf(montoAprobadoProyecto + proyectoActual.getMontofinanciarCalculado());
			correo = correo.replaceAll("<<VALOR>>", String.valueOf(saldoFinanciacion));

			// Se cargan los datos de estudiantes
			if (CorreoPlantilla.isEsCartaEstudiante(correoActual.getId())) {
				if (cargarDatosEstudianteProyecto(correo)) {
					mostrarEstudiantes = true;
				} else {
					mensajeCartas = "La carta no se puede generar, no se tiene un estudiante asociado al proyecto.";
					mostrarEstudiantes = false;
					return false;
				}
			}

			// Se cargan los datos de jovenes investigadores
			if (CorreoPlantilla.isEsCartaEstudianteExterno(correoActual.getId())) {
				if (cargarDatosEstudianteProyecto(correo)) {
					mostrarEstudiantes = true;
				} else if (cargarDatosJIExternosProyecto(correo)) {
					mostrarJovenInvExterno = true;
				} else {
					mensajeCartas = "La carta no se puede generar, no se tiene un estudiante asociado al proyecto.";
					mostrarEstudiantes = false;
					mostrarJovenInvExterno = false;
					return false;
				}
			}

			if (this.proyectoActual.getFechaTentativaInicio() != null) {

				SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yyyy");
				String fechaInicioTemporal = sp.format(proyectoActual.getFechaTentativaInicio());
				correo = correo.replaceAll("<<FECHA_INICIO>>", fechaInicioTemporal);

				Calendar c = Calendar.getInstance();
				c.setTime(proyectoActual.getFechaTentativaInicio());
				c.add(Calendar.MONTH, proyectoActual.getDuracion().intValue());
				SimpleDateFormat sp5 = new SimpleDateFormat("dd/MM/yyyy");
				fechaF = sp5.format(c.getTime());
				correo = correo.replaceAll("<<FECHA_FINAL>>", fechaF);
			}

		}

		correo = correo.replaceAll("<<CODIGO_DIB>>", "");
		correo = correo.replaceAll("<<ENTREGA_PARCIAL>>", Fecha.fechaActual());
		correo = correo.replaceAll("<<ENTREGA_FINAL>>", Fecha.fechaActual());

		InvestigadorInterno invInterno = servicioPersona.obtenerInvestigadorInterno(pe.getId());

		List dep = servicioGeneral.obtenerListaObjetosWhere("Dependencia d",
				"where d.id='" + invInterno.getDependencia().getId() + "'");
		if (!esListaVacia(dep)) {
			Dependencia d = (Dependencia) dep.get(0);

			if (d != null && d.getNombre() != null) {
				correo = correo.replaceAll("<<DEPARTAMENTO>>", d.getNombre());
			}
			try {
				if (d != null && d.getId() != null) {
					String padre = servicioGeneral.dependenciaPadre(d.getId());
					List deppadre = servicioGeneral.obtenerListaObjetosWhere("Dependencia d",
							"where d.id='" + padre + "'");
					if (!esListaVacia(deppadre)) {
						Dependencia facultad = (Dependencia) deppadre.get(0);
						correo = correo.replaceAll("<<FACULTAD>>", facultad.getNombre());
					}

				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}
		if (this.fechaSesion != null) {
			DateFormat formateadorFecha = DateFormat.getDateInstance(DateFormat.MEDIUM);
			fechaS = formateadorFecha.format(fechaSesion);
			correo = correo.replaceAll("<<SESION>>", fechaS);

		}

		cuerpoCorreo = correo;

		return true;

	}

	/**
	 * Este metodo valida si la carta que se va a generar necesita firma y si el
	 * coordinador esta asociado a une dependencia valida que este en parametro con
	 * el nombre del responsable, el cargo y la firma (opcional).
	 * 
	 * @param tipoCarta
	 * @return
	 */
	private boolean validarFirma(String tipoCarta) {

		// Se valida si la carta necesita firma
		List<DominioDetalle> tiposCartaList = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select dd from Dominio d, DominioDetalle dd where d.id =" + " dd.identificador.id and d.id ='"
						+ TIPO_CARTA_DOMINIO + "' and dd.identificador.tipo = '" + tipoCartSeleccionado
						+ "' order by dd.identificador.tipo");

		boolean necesitaFirma = false;

		if (tiposCartaList != null && !tiposCartaList.isEmpty()) {
			DominioDetalle dominioDetalle = tiposCartaList.get(0);
			if (dominioDetalle.getObservacion() != null
					&& dominioDetalle.getObservacion().equals(TipoCarta.NECESITA_FIRMA)) {
				necesitaFirma = true;
			}
		}

		if (necesitaFirma) {
			// Se valida si la edpendencia de la persona actual tiene firma
			// asignada.
			InvestigadorInterno investigadorInterno = servicioPersona
					.obtenerInvestigadorInterno(cargarPersonaActual().getId());
			Dependencia dependenciaEnvio = null;
			if (investigadorInterno != null) {
				dependenciaEnvio = cargarDependenciaEnvio(false, proyectoActual.getModalidad().getId(),
						investigadorInterno);
			}
			if (!validarParametroFirma(dependenciaEnvio)) {
				mensajeCartas = "Usted no esta asociado a una dependencia válida para generar cartas.";
				return false;
			}
		}

		return true;
	}

	private boolean validarTipoCartaCorreo() {

		mensajeCartas = "";

		if (Integer.parseInt(this.avalId) == CorreoPlantilla.PRORROGA) {
			if (codigoSolicitudItem == null || (codigoSolicitudItem != null && codigoSolicitudItem.length == 0)) {
				mostrarCodigoSolicitudJustificacion = false;
				mensajeCartas = "No existe una solicitud de prorroga aprobada pendiente por carta.";
			}
		}
		if (Integer.parseInt(this.avalId) == CorreoPlantilla.CANCELACION_ESTUDIANTE
				|| Integer.parseInt(this.avalId) == CorreoPlantilla.CANCELACION_PRINCIPAL) {
			if (codigoSolicitudItem == null || (codigoSolicitudItem != null && codigoSolicitudItem.length == 0)) {
				mostrarCodigoSolicitudJustificacion = false;
				mensajeCartas = "No existe ninguna solicitud de cancelación aprobada pendiente por generación carta.";
			}
		}

		if (Integer.parseInt(this.avalId) == CorreoPlantilla.SUSPENSION) {
			if (codigoSolicitudItem == null || (codigoSolicitudItem != null && codigoSolicitudItem.length == 0)) {
				mostrarCodigoSolicitudJustificacion = false;
				mensajeCartas = "No existe ninguna solicitud de suspensión aprobada pendiente por generación carta.";
			}
		}

		if (Integer.parseInt(this.avalId) == CorreoPlantilla.CAMBIO_RUBRO) {
			if (codigoSolicitudItem == null || (codigoSolicitudItem != null && codigoSolicitudItem.length == 0)) {
				mostrarCodigoSolicitudJustificacion = false;
				mensajeCartas = "No existe ninguna solicitud de cambio de rubro aprobada pendiente por carta.";
			}
		}

		if (Integer.parseInt(this.avalId) == CorreoPlantilla.ADICION_PRESUPUESTAL) {
			if (codigoSolicitudItem == null || (codigoSolicitudItem != null && codigoSolicitudItem.length == 0)) {
				mostrarCodigoSolicitudJustificacion = false;
				mensajeCartas = "No existe ninguna solicitud de adición presupuestal aprobada pendiente por carta.";
			}
		}

		if (Integer.parseInt(this.avalId) == CorreoPlantilla.CAMBIO_INVESTIGADOR_PRINCIPAL) {
			if (codigoSolicitudItem == null || (codigoSolicitudItem != null && codigoSolicitudItem.length == 0)) {
				mostrarCodigoSolicitudJustificacion = false;
				mensajeCartas = "No existe ninguna solicitud de cambio de investigador principal aprobada pendiente por carta.";
			}
		}

		if (Integer.parseInt(this.avalId) == CorreoPlantilla.CAMBIO_INTEGRANTES) {
			if (codigoSolicitudItem == null || (codigoSolicitudItem != null && codigoSolicitudItem.length == 0)) {
				mostrarCodigoSolicitudJustificacion = false;
				mensajeCartas = "No existe una solicitud de cambio de integrantes aprobada pendiente por carta.";
			}
		}

		if (Integer.parseInt(this.avalId) == CorreoPlantilla.CAMBIO_CONTENIDO) {
			if (codigoSolicitudItem == null || (codigoSolicitudItem != null && codigoSolicitudItem.length == 0)) {
				mostrarCodigoSolicitudJustificacion = false;
				mensajeCartas = "No existe una solicitud de cambio de contenido aprobada pendiente por carta.";
			}
		}

		if (Integer.parseInt(this.avalId) == CorreoPlantilla.REACTIVACION) {
			if (codigoSolicitudItem == null || (codigoSolicitudItem != null && codigoSolicitudItem.length == 0)) {
				mostrarCodigoSolicitudJustificacion = false;
				mensajeCartas = "No existe una solicitud de reactivacion aprobada pendiente por carta.";
			}
		}

		if (Long.parseLong(this.avalId) == CorreoPlantilla.RESOLUCION_MODIFICACION) {
			if (codigoSolicitudItem == null || (codigoSolicitudItem != null && codigoSolicitudItem.length == 0)) {
				mostrarCodigoSolicitudJustificacion = false;
				mensajeCartas = "No existe una solicitud aprobada pendiente por resolución modificatoria.";
			}
			if (mostrarCodigoSolicitudJustificacion) {
				ProyectoCarta proyectoCarta = buscarProyectoCartaxTipo(CorreoPlantilla.RESOLUCION_EXTERNOS);
				if (proyectoCarta == null) {
					proyectoCarta = buscarProyectoCartaxTipo(CorreoPlantilla.RESOLUCION_EXTERNOS_CONTRAPARTIDA);
					if (proyectoCarta == null) {
						mostrarCodigoSolicitudJustificacion = false;
						mensajeCartas = "Debe existir una resolución aprobatoria para crear una resolución modificatoria.";
					}
				}
			}
		}

		// Se valida que si es un tipo de carta que tiene que ser unica
		// no se deje crear
		if (TipoCarta.CARTAS_UNICAS.indexOf(this.tipoCartSeleccionado) != -1) {
			Iterator<ProyectoCarta> i = listaCartasProyecto.iterator();
			while (i.hasNext()) {
				ProyectoCarta proyectoCartaTemporal = i.next();
				if (proyectoCartaTemporal.getCarta().getId().toString().equals(tipoCartSeleccionado)) {
					mensajeCartas = "El proyecto ya cuenta con una carta de este tipo.";
				}
			}
		}

		// Se valida que si es un tipo de carta necesita financiación.
		if (CorreoPlantilla.CARTAS_FINANCIACION_OBLIGATORIA.indexOf(this.avalId) != -1
				&& !avalId.equals(CorreoPlantilla.COMPROMISO_EXTERNAS)) {
			if (montoAprobadoProyecto == 0L && proyectoActual.getTipologiaProyecto() != null) {
				if (!proyectoActual.getTipologiaProyecto().equals("ET")) {
					mensajeCartas = "Para la generación de esta carta es necesario que el proyecto tenga una financiación registrada.";
				}
			}
		}

		// Se valida que si es un tipo de carta necesita financiación.
		if (CorreoPlantilla.CARTAS_GRUPO_OBLIGATORIA.indexOf(this.avalId) != -1) {
			List<Grupo> grupos = servicioGeneral.obtenerObjetosLimitado(Grupo.class,
					"from Grupo g where g.proyecto.id = '" + proyectoActual.getId() + "'");
			if (esListaVacia(grupos)) {
				mensajeCartas = "Para la generación de esta carta es necesario que el proyecto tenga un grupo de investigación asociado.";
			}
		}

		List listaConsultaEstadoProyecto = servicioGeneral.obtenerObjetos(Proyecto.class,
				"from Proyecto where id ='" + this.proyectoActual.getId() + "'");
		Proyecto pr = (Proyecto) listaConsultaEstadoProyecto.get(0);

		if (!pr.getEstadoProyecto().getId().equals(EstadoProyecto.APROBADO) && !pr.getEstadoProyecto().getId().equals(EstadoProyecto.APROBADO_OCAD)) {
			if (Integer.parseInt(this.avalId) == 52 || Integer.parseInt(this.avalId) == CorreoPlantilla.INICIO_PRINCIPAL
					|| Integer.parseInt(this.avalId) == CorreoPlantilla.INICIO_ESTUDIANTE
					|| Integer.parseInt(this.avalId) == CorreoPlantilla.INICIO_JOVENES_INVESTIGADORES
					|| Integer.parseInt(this.avalId) == CorreoPlantilla.INICIO_SIN_FINANCIACION
					|| Integer.parseInt(this.avalId) == CorreoPlantilla.INICIO_JOVENES_INVESTIGADOR_TUTOR_2014
					|| Integer.parseInt(this.avalId) == CorreoPlantilla.INICIO_TRADUCCION_ARTICULOS
					|| Integer.parseInt(this.avalId) == CorreoPlantilla.COMPROMISO_EXTERNAS) {
				mensajeCartas = "El acta de inicio no se puede generar debido a que el proyecto no se encuentra en estado aprobado.";
			}
		}

		// Se valida si ya se agrego una resolución.
		if (Integer.parseInt(this.avalId) == CorreoPlantilla.COMPROMISO_EXTERNAS) {
			Iterator<ProyectoCarta> i = listaCartasProyecto.iterator();
			boolean existeResolucion = false;
			while (i.hasNext()) {
				ProyectoCarta proyectoCartaTemporal = i.next();
				if (proyectoCartaTemporal.getSubtipo() != null
						&& (proyectoCartaTemporal.getSubtipo().equals(Long.valueOf(CorreoPlantilla.RESOLUCION_EXTERNOS))
								|| proyectoCartaTemporal.getSubtipo()
										.equals(Long.valueOf(CorreoPlantilla.RESOLUCION_EXTERNOS_CONTRAPARTIDA)))) {
					existeResolucion = true;
					break;
				}
			}
			if (!existeResolucion && StringUtils.isNotBlank(proyectoActual.getTipologiaProyecto())
					&& proyectoActual.getTipologiaProyecto().equals("ETF")) {
				mensajeCartas = "El acta de compromiso no puede ser generada, primero se debe generar la Resolución de Aprobación del proyecto.";
			}
		}

		if (!pr.getEstadoProyecto().getId().equals(EstadoProyecto.FINALIZADO)) {
			if (Integer.parseInt(this.avalId) == CorreoPlantilla.FINALIZACION_PRINCIPAL
					|| Long.parseLong(this.avalId) == CorreoPlantilla.FINALIZACION_PRINCIPAL_EXTERNA
					|| Integer.parseInt(this.avalId) == CorreoPlantilla.FINALIZACION_ESTUDIANTE
					|| Integer.parseInt(this.avalId) == CorreoPlantilla.FINALIZACION_JOVENES_INVESTIGADORES
					|| Integer.parseInt(this.avalId) == CorreoPlantilla.FINALIZACION_SIN_FINANCIACION) {

				if (!validarFinalizado(proyectoActual.getId())) {
					mensajeCartas = "Para generar el acta de finalización debe existir un informe final aceptado asociado al proyecto.";
				}
			}
		}

		// Se valida que la resolución aprobatoria solo se genere para
		// convocatorias externas
		if (Long.parseLong(this.avalId) == CorreoPlantilla.RESOLUCION_EXTERNOS
				&& proyectoActual.getSubTipoActividadECP() == null) {
			mensajeCartas = "La resolución de aprobación solo se debe generar cuando el proyecto es de financiación o convocatoria externa.";
		}

		if (Long.parseLong(this.avalId) == CorreoPlantilla.RESOLUCION_EXTERNOS_CONTRAPARTIDA
				&& proyectoActual.getSubTipoActividadECP() == null
				&& (proyectoActual.getContrapartidaEfectivoUN() != null
						&& proyectoActual.getContrapartidaEfectivoUN() > 0L)) {
			mensajeCartas = "La resolución de aprobación solo se debe generar cuando el proyecto es de financiación o convocatoria externa y el valor de la contrapartida en efectivo es mayor a 0.";
		}

		return StringUtils.isBlank(mensajeCartas);
	}

	public void generarCuerpoCorreo() {

		mostrarPanelCorreo = true;
		mostrarGenerarPDF = false;
		mensajeEnvioCarta = "";
		mensajeCartas = "";
		mostrarEstudiantes = false;
		mostrarCodigoSolicitudJustificacion = false;
		mostrarSaldoFinanciacion = false;
		mostrarFechaSesion = false;
		mostrarParametrosCertificadoMovilizacion = false;
		docenteYaFirmo = false;

		if (!validarFirma(tipoCartSeleccionado)) {
			return;
		}

		try {

			int avalIdInt = Integer.parseInt(avalId);

			if (avalIdInt == CorreoPlantilla.RESOLUCION_EXTERNOS
					|| avalIdInt == CorreoPlantilla.RESOLUCION_EXTERNOS_CONTRAPARTIDA) {
				cambiarOrdenador();
			}

			if (avalIdInt == CorreoPlantilla.PRORROGA || avalIdInt == CorreoPlantilla.CAMBIO_RUBRO
					|| avalIdInt == CorreoPlantilla.ADICION_PRESUPUESTAL
					|| avalIdInt == CorreoPlantilla.CAMBIO_INVESTIGADOR_PRINCIPAL
					|| avalIdInt == CorreoPlantilla.CAMBIO_INTEGRANTES || avalIdInt == CorreoPlantilla.CAMBIO_CONTENIDO
					|| avalIdInt == CorreoPlantilla.REACTIVACION
					|| avalIdInt == CorreoPlantilla.CERTIFICADO_MOVILIZACION
					|| avalIdInt == CorreoPlantilla.CANCELACION_ESTUDIANTE
					|| avalIdInt == CorreoPlantilla.CANCELACION_PRINCIPAL || avalIdInt == CorreoPlantilla.SUSPENSION
					|| avalIdInt == CorreoPlantilla.RESOLUCION_MODIFICACION) {
				mostrarCodigoSolicitudJustificacion = true;
				nombreParametro = "Fecha compromiso: ";

				// Se cargan los códigos de solicitud
				cargarCodigoSolicitud(proyectoActual.getId().toString(), avalIdInt);

				if (avalIdInt == CorreoPlantilla.CERTIFICADO_MOVILIZACION) {
					mostrarParametrosCertificadoMovilizacion = true;
				} else if (avalIdInt == CorreoPlantilla.CANCELACION_ESTUDIANTE
						|| avalIdInt == CorreoPlantilla.CANCELACION_PRINCIPAL
						|| avalIdInt == CorreoPlantilla.SUSPENSION) {
					nombreParametro = "Fecha finalización: ";
					mostrarSaldoFinanciacion = true;
					mostrarFechaSesion = true;
				}
			} else if (avalIdInt == CorreoPlantilla.FINALIZACION_PRINCIPAL
					|| avalIdInt == CorreoPlantilla.FINALIZACION_PRINCIPAL_EXTERNA
					|| avalIdInt == CorreoPlantilla.FINALIZACION_ESTUDIANTE
					|| avalIdInt == CorreoPlantilla.FINALIZACION_JOVENES_INVESTIGADORES
					|| avalIdInt == CorreoPlantilla.FINALIZACION_SIN_FINANCIACION) {
				nombreParametro = "Fecha finalización: ";
				mostrarSaldoFinanciacion = avalIdInt != CorreoPlantilla.FINALIZACION_SIN_FINANCIACION;
				mostrarFechaSesion = true;
			} else if (avalIdInt == CorreoPlantilla.INICIO_GRUPO || avalIdInt == CorreoPlantilla.INICIO_PRINCIPAL
					|| avalIdInt == CorreoPlantilla.INICIO_ESTUDIANTE
					|| avalIdInt == CorreoPlantilla.INICIO_JOVENES_INVESTIGADORES
					|| avalIdInt == CorreoPlantilla.INICIO_SEMILLEROS
					|| avalIdInt == CorreoPlantilla.INICIO_JOVENES_INVESTIGADOR_TUTOR
					|| avalIdInt == CorreoPlantilla.INICIO_JOVENES_INVESTIGADOR_TUTOR_2014
					|| avalIdInt == CorreoPlantilla.INICIO_SIN_FINANCIACION
					|| avalIdInt == CorreoPlantilla.INICIO_TRADUCCION_ARTICULOS
					|| avalIdInt == CorreoPlantilla.INICIO_PROYECTO_LABORATORIOS
					|| avalIdInt == CorreoPlantilla.COMPROMISO_EXTERNAS
					|| avalIdInt == CorreoPlantilla.RESOLUCION_EXTERNOS
					|| avalIdInt == CorreoPlantilla.RESOLUCION_EXTERNOS_CONTRAPARTIDA) {

				if (proyectoActual.getFechaLegalizacion() == null) {
					mensajeCartas = "La carta no se puede generar, por favor finalice primero el proceso de ";
					mostrarGenerarPDF = false;
					if (avalIdInt == CorreoPlantilla.COMPROMISO_EXTERNAS) {
						mensajeCartas += "legalización.";
					} else {
						mensajeCartas += "formalización.";
						cambiarFechaFormalizacion();
					}
					return;
				} else {
					Boolean tieneAvalEticoCualquierEstado = servicioGeneral
							.obtenerAvalesProyectoXEstatoAvalXTipoAval(proyectoActual.getId().toString(), "'ETICO'",
									"'P','SP','SS','EP','ES','CP','NP','NS'", false)
							.size() > 0;
					Boolean tieneAvalEticoAprobado = servicioGeneral.obtenerAvalesProyectoXEstatoAvalXTipoAval(
							proyectoActual.getId().toString(), "'ETICO'", "'SP','SS'", false).size() > 0;
					Boolean tieneAvalEticoEnviado = servicioGeneral.obtenerAvalesProyectoXEstatoAvalXTipoAval(
							proyectoActual.getId().toString(), "'ETICO'", "'EP','ES'", false).size() > 0;
					Boolean tieneAvalEticoDevuelto = servicioGeneral.obtenerAvalesProyectoXEstatoAvalXTipoAval(
							proyectoActual.getId().toString(), "'ETICO'", "'CP'", false).size() > 0;

					if (tieneAvalEticoCualquierEstado) {
						if (!tieneAvalEticoAprobado) {
							if (tieneAvalEticoEnviado || tieneAvalEticoDevuelto) {
								mensajeCartas = "Para activar el proyecto, el aval de tipo ÉTICO que tiene asociado debe estar aprobado, "
										+ "por lo que le recomendamos revisar el proyecto ya que cuenta con un aval ético "
										+ "devuelto para correcciones o enviado y que se recomienda aprobar antes de activar”";
								mostrarGenerarPDF = false;
								return;
							} else {
								mensajeCartas = "Para activar el proyecto, el aval de tipo ÉTICO que tiene asociado debe estar en estado aprobado";
								mostrarGenerarPDF = false;
								return;
							}
						}
					}
				}
			}
			if (avalIdInt == CorreoPlantilla.FINALIZACION_PRINCIPAL_EXTERNA) {
				cambiarOrdenador();
			}

			mostrarGenerarPDF = cargarDatosAPlantillaCorreo(avalIdInt) && validarTipoCartaCorreo();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	public void verificarCuerpoCorreo() {
		try {
			int avalIdInt = Integer.parseInt(avalId);

			if (avalIdInt == CorreoPlantilla.CERTIFICADO_MOVILIZACION) {
				mostrarCodigoSolicitudJustificacion = true;
				nombreParametro = "Fecha compromiso: ";
				mostrarParametrosCertificadoMovilizacion = true;
				mostrarGenerarPDF = cargarDatosAPlantillaCorreo(avalIdInt) && validarTipoCartaCorreo();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	private void cargarCodigoSolicitud(String idProyecto1, Integer tipoCarta) {
		Long tipoSolicitud = null;

		// Se obtiene el tipo de solicitud acorde a la carta seleccionada
		if (tipoCarta == CorreoPlantilla.PRORROGA) {
			tipoSolicitud = TipoSolicitud.PRORROGA;
		} else if (tipoCarta == CorreoPlantilla.CAMBIO_RUBRO) {
			tipoSolicitud = TipoSolicitud.CAMBIO_RUBROS;
		} else if (tipoCarta == CorreoPlantilla.ADICION_PRESUPUESTAL) {
			tipoSolicitud = TipoSolicitud.ADICION_PRESPUESTAL;
		} else if (tipoCarta == CorreoPlantilla.CANCELACION_ESTUDIANTE
				|| tipoCarta == CorreoPlantilla.CANCELACION_PRINCIPAL) {
			tipoSolicitud = TipoSolicitud.CANCELACION;
		} else if (tipoCarta == CorreoPlantilla.SUSPENSION) {
			tipoSolicitud = TipoSolicitud.SUSPENSION;
		} else if (tipoCarta == CorreoPlantilla.CERTIFICADO_MOVILIZACION) {
			tipoSolicitud = TipoSolicitud.CERTIFICADO_MOVILIZACION;
		} else if (tipoCarta == CorreoPlantilla.CAMBIO_INVESTIGADOR_PRINCIPAL) {
			tipoSolicitud = TipoSolicitud.CAMBIO_INVESTIGADOR_PRINCIPAL;
		} else if (tipoCarta == CorreoPlantilla.CAMBIO_INTEGRANTES) {
			tipoSolicitud = TipoSolicitud.CAMBIO_INTEGRANTES;
		} else if (tipoCarta == CorreoPlantilla.CAMBIO_CONTENIDO) {
			tipoSolicitud = TipoSolicitud.CAMBIO_CONTENIDO;
		} else if (tipoCarta == CorreoPlantilla.REACTIVACION) {
			tipoSolicitud = TipoSolicitud.REACTIVACION;
		}

		codigoSolicitudItem = new SelectItem[0];

		String solicitudesConCarta = "";
		String solicitudesConResolucion = "";

		Iterator<ProyectoCarta> j = listaCartasProyecto.iterator();
		while (j.hasNext()) {
			ProyectoCarta proyectoCartaTemporal = j.next();
			if (proyectoCartaTemporal.getCarta().getId().equals(TipoCarta.RESOLUCION_MODIFICATORIAS)) {
				if (solicitudesConResolucion.length() > 0) {
					solicitudesConResolucion += ",";
				}
				solicitudesConResolucion += proyectoCartaTemporal.getIdSolicitud();
			} else {
				if (solicitudesConCarta.length() > 0) {
					solicitudesConCarta += ",";
				}
				solicitudesConCarta += proyectoCartaTemporal.getIdSolicitud();
			}
		}

		List listaCodigoSolicitud;
		if (proyectoActual.getEsPermisoMarco() || proyectoActual.getEsPermisoMarcoAsignatura()) {

			String consulta = "select distinct #id s.id from Solicitud s " + "where s.proyecto.id = '" + idProyecto1
					+ "' and " + "s.tipoSolicitud.id = '" + tipoSolicitud + "' and " + " (s.respuesta in ('A'))";

			if (solicitudesConCarta.length() > 0) {
				solicitudesConCarta = consulta + "and s.id not in (" + solicitudesConCarta + ")";
			}

			consulta += " order by s.id asc";

			listaCodigoSolicitud = servicioGeneral.obtenerObjetosLimitado(Solicitud.class, consulta);
		} else if (Long.valueOf(tipoCarta) == CorreoPlantilla.RESOLUCION_MODIFICACION) {
			listaCodigoSolicitud = servicioGeneral.obtenerCodigoSolicitud(new Long(idProyecto1),
					TipoSolicitud.CAMBIO_INVESTIGADOR_PRINCIPAL + ", " + TipoSolicitud.PRORROGA + ", "
							+ TipoSolicitud.CAMBIO_RUBROS + ", " + +TipoSolicitud.CANCELACION + ", "
							+ TipoSolicitud.ADICION_PRESPUESTAL,
					solicitudesConResolucion);
		} else {
			listaCodigoSolicitud = servicioGeneral.obtenerCodigoSolicitud(new Long(idProyecto1),
					tipoSolicitud.toString(), solicitudesConCarta);
		}
		if (!esListaVacia(listaCodigoSolicitud)) {
			codigoSolicitudItem = new SelectItem[listaCodigoSolicitud.size()];
			int maximo = listaCodigoSolicitud.size();
			for (int i = 0; i < listaCodigoSolicitud.size(); i++) {
				Solicitud sol = (Solicitud) listaCodigoSolicitud.get(i);
				codigoSolicitudItem[i] = new SelectItem(sol.getId().toString(), sol.getId().toString());
				if (i == maximo - 1) {
					codigoSolicitud = sol.getId().toString();
				}
			}
		}
	}

	// Cambio de fecha de informe final y parcial
	public void cambiarFechaFormalizacion() {
		if (proyectoActual.getFechaTentativaInicio() != null) {
			GregorianCalendar gregorianCalendarParcial = new GregorianCalendar();
			gregorianCalendarParcial.setTime(proyectoActual.getFechaTentativaInicio());
			gregorianCalendarParcial.add(GregorianCalendar.MONTH, proyectoActual.getDuracion().intValue() / 2);

			fechaInformeParcial = gregorianCalendarParcial.getTime();

			GregorianCalendar gregorianCalendarInfFinal = new GregorianCalendar();
			gregorianCalendarInfFinal.setTime(proyectoActual.getFechaTentativaInicio());
			gregorianCalendarInfFinal.add(GregorianCalendar.MONTH, proyectoActual.getDuracion().intValue() + 2);

			GregorianCalendar gregorianCalendarFinal = new GregorianCalendar();
			gregorianCalendarFinal.setTime(proyectoActual.getFechaTentativaInicio());
			gregorianCalendarFinal.add(GregorianCalendar.MONTH, proyectoActual.getDuracion().intValue());

			fechaFinalProyecto = gregorianCalendarFinal.getTime();
			proyectoActual.setFechaInformeFinal(gregorianCalendarInfFinal.getTime());

			if (proyectoActual.getFechaFinalizacion() == null) {
				cambiarFechas();
			}
		}
	}

	public void cargarCaculoVista() {
		if (!esCadenaVacia(diasCalculoVista) && !esCadenaVacia(mesesCalculoVista)) {
			prorrogaSeleccionada.setDias(new Long(diasCalculo));
			prorrogaSeleccionada.setDuracion(new Long(mesesCalculo));
			prorrogaSeleccionada.setDiasVista(diasCalculoVista);
			prorrogaSeleccionada.setMesesVista(mesesCalculoVista);
		}
	}

	/**
	 * Tipos de compromiso que se pueden asignar al proyecto
	 * 
	 * @return
	 */
	public SelectItem[] getTiposCompromisoItem() {
		SelectItem[] tiposCompromiso = new SelectItem[3];
		tiposCompromiso[0] = new SelectItem("", "");
		tiposCompromiso[1] = new SelectItem(TipoInforme.INFORME_AVANCE, "Informe de avance");
		tiposCompromiso[2] = new SelectItem(TipoInforme.INFORME_FINAL, "Informe final");
		return tiposCompromiso;
	}

	public void agregarCompromisoProyecto() {
		boolean valido = true;
		if (tipoCompromiso == null || "".equals(tipoCompromiso.trim())) {
			mensajeError("Debe seleccionar el tipo de compromiso que desea asignar.");
		} else if (fechaVencimientoCompromiso == null) {
			mensajeError("Debe seleccionar la fecha de vencimiento para el compromiso que está asignando.");
		} else if (tipoCompromiso.equals(TipoInforme.INFORME_FINAL.toString())
				&& !esListaVacia(proyectoActual.getListaCompromisoInformesFinales())) {
			valido = false;
			mensajeError("Ya ha sido asignado un compromiso de informe final.");
		}

		if (valido) {
			TipoInforme informe = new TipoInforme();
			informe.setId(Long.parseLong(tipoCompromiso));
			ProyectoCompromiso compromiso = new ProyectoCompromiso();
			compromiso.setCumplido(ProyectoCompromiso.NO_CUMPLIDO);
			compromiso.setTipoInforme(informe);
			compromiso.setNumeroNotificaciones(0);
			compromiso.setFechaVencimiento(fechaVencimientoCompromiso);
			proyectoActual.adicionarCompromisoProyecto(compromiso);
		}
	}

	public void eliminarCompromisoProyecto() {
		if (compromisoSeleccionado != null) {
			proyectoActual.getCompromisosProyecto().remove(compromisoSeleccionado);
			servicioGeneral.eliminarObjeto(compromisoSeleccionado);
		}
	}

	public void activarProyecto() {
		if (!esListaVacia(proyectoActual.getListaCompromisos())) {
			proyectoActual.cambiarEstadoPersona(EstadoProyecto.ACTIVO, cargarPersonaActual(),
					"Asignación de compromisos con el contrato de acceso a recurso genético.");
			servicioGeneral.guardarObjeto(proyectoActual);
		} else {
			mensajeError("Debe asignar los compromisos para activar el contrato del proyecto.");
		}
	}

	public boolean validarFinalizado(Long ipProyecto) {
		List<ProyectoInforme> informes = servicioGeneral.obtenerObjetosLimitado(ProyectoInforme.class,
				"select #id pi.id from" + " ProyectoInforme pi " + "where pi.proyecto.id = '" + ipProyecto + "'"
						+ "and pi.estadoInforme.id in ('" + EstadoInforme.ACEPTADO_DIRECCION + "'," + "'"
						+ EstadoInforme.ACEPTADO_FACULTAD + "')" + "and pi.tipoInforme.id='" + TipoInforme.INFORME_FINAL
						+ "'");
		if (informes != null && informes.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public Map getDominioEstadoAlerta() {
		return dominioEstadoAlerta;
	}

	public void setDominioEstadoAlerta(Map dominioEstadoAlerta) {
		this.dominioEstadoAlerta = dominioEstadoAlerta;
	}

	public SelectItem[] getEntidadItems() {
		return entidadItems;
	}

	public void setEntidadItems(SelectItem[] entidadItems) {
		this.entidadItems = entidadItems;
	}

	public void setFacultadInstItem(SelectItem[] facultadInstItem) {
		this.facultadInstItem = facultadInstItem;
	}

	public void setTipologiaItems(SelectItem[] tipologiaItems) {
		this.tipologiaItems = tipologiaItems;
	}

	public void setTipofechaItems(SelectItem[] tipofechaItems) {
		this.tipofechaItems = tipofechaItems;
	}

	public void setDesembolsoItems(SelectItem[] desembolsoItems) {
		this.desembolsoItems = desembolsoItems;
	}

	public void setVinculacionItems(SelectItem[] vinculacionItems) {
		this.vinculacionItems = vinculacionItems;
	}

	public void setPeriodoInformesItems(SelectItem[] periodoInformesItems) {
		this.periodoInformesItems = periodoInformesItems;
	}

	public void setVerPeriodosInforme(boolean verPeriodosInforme) {
		this.verPeriodosInforme = verPeriodosInforme;
	}

	public void setTipoEntidadItems(SelectItem[] tipoEntidadItems) {
		this.tipoEntidadItems = tipoEntidadItems;
	}

	public SelectItem[] getProgramaItems() {
		return programaItems;
	}

	public void setProgramaItems(SelectItem[] programaItems) {
		this.programaItems = programaItems;
	}

	public SelectItem[] getExoneracionCostosItems() {
		return EXONERACION_COSTOS_ITEMS;
	}

	public SelectItem[] getcambiosProyectosItems() {
		return CAMBIOS_PROYECTO_ITEMS;
	}

	public SelectItem[] getCompromisoEjecItems() {
		return compromisoEjecItems;
	}

	public void setCompromisoEjecItems(SelectItem[] compromisoEjecItems) {
		this.compromisoEjecItems = compromisoEjecItems;
	}

	public Long getNumActaInicioExt() {
		return numActaInicioExt;
	}

	public void setNumActaInicioExt(Long numActaInicioExt) {
		this.numActaInicioExt = numActaInicioExt;
	}

	public Date getFechaActaInicioExt() {
		return fechaActaInicioExt;
	}

	public void setFechaActaInicioExt(Date fechaActaInicioExt) {
		this.fechaActaInicioExt = fechaActaInicioExt;
	}

	public void setFacInstitutoSel(String facInstitutoSel) {
		this.facInstitutoSel = facInstitutoSel;
	}

	public void setTipoEntidadSel(String tipoEntidadSel) {
		this.tipoEntidadSel = tipoEntidadSel;
	}

	public String getObligacionesUN() {
		return obligacionesUN;
	}

	public void setObligacionesUN(String obligacionesUN) {
		this.obligacionesUN = obligacionesUN;
	}

	public Long getValorDesembolso() {
		return valorDesembolso;
	}

	public void setValorDesembolso(Long valorDesembolso) {
		this.valorDesembolso = valorDesembolso;
	}

	public String getDesembolso() {
		return desembolso;
	}

	public void setDesembolso(String desembolso) {
		this.desembolso = desembolso;
	}

	public Financiacion getEntidadSeleccionada() {
		return entidadSeleccionada;
	}

	public void setEntidadSeleccionada(Financiacion entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}

	public Convenio getConvenioActual() {
		return convenioActual;
	}

	public void setConvenioActual(Convenio convenioActual) {
		this.convenioActual = convenioActual;
	}

	public ProyectoCarta getActaProyectoActual() {
		return actaProyectoActual;
	}

	public void setActaProyectoActual(ProyectoCarta actaProyectoActual) {
		this.actaProyectoActual = actaProyectoActual;
	}

	public List<SelectItem> getEntidadDesembolsosItems() {
		return entidadDesembolsosItems;
	}

	public String getEntidadDesembolso() {
		return entidadDesembolso;
	}

	public void setEntidadDesembolso(String entidadDesembolso) {
		this.entidadDesembolso = entidadDesembolso;
	}

	public Date getFechaObligacion() {
		return fechaObligacion;
	}

	public void setFechaObligacion(Date fechaObligacion) {
		this.fechaObligacion = fechaObligacion;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public SelectItem[] getAnioItems() {
		return anioItems;
	}

	public void setAnioItems(SelectItem[] anioItems) {
		this.anioItems = anioItems;
	}

	public Long getValorIngreso() {
		return valorIngreso;
	}

	public void setValorIngreso(Long valorIngreso) {
		this.valorIngreso = valorIngreso;
	}

	public String getEntidadRecurso() {
		return entidadRecurso;
	}

	public void setEntidadRecurso(String entidadRecurso) {
		this.entidadRecurso = entidadRecurso;
	}

	public UploadedFile getArchivoLeg() {
		return archivoLeg;
	}

	public void setArchivoLeg(UploadedFile archivoLeg) {
		this.archivoLeg = archivoLeg;
	}

	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public SelectItem[] getTipoArchivoItem() {
		return tipoArchivoItem;
	}

	public void setTipoArchivoItem(SelectItem[] tipoArchivoItem) {
		this.tipoArchivoItem = tipoArchivoItem;
	}

	public List<Archivo> getListaArchivosLeg() {
		return listaArchivosLeg;
	}

	public void setListaArchivosLeg(List<Archivo> listaArchivosLeg) {
		this.listaArchivosLeg = listaArchivosLeg;
	}

	public DataTable getTablaArchivosLeg() {
		return tablaArchivosLeg;
	}

	public void setTablaArchivosLeg(DataTable tablaArchivosLeg) {
		this.tablaArchivosLeg = tablaArchivosLeg;
	}

	public void setTiposCarta(SelectItem[] tiposCarta) {
		this.tiposCarta = tiposCarta;
	}

	public Archivo getArchivoSeleccionadoLeg() {
		return archivoSeleccionadoLeg;
	}

	public void setArchivoSeleccionadoLeg(Archivo archivoSeleccionadoLeg) {
		this.archivoSeleccionadoLeg = archivoSeleccionadoLeg;
	}

	public String getTipoDuracion() {
		return tipoDuracion;
	}

	public String getAsignacionActual() {
		return asignacionActual;
	}

	public Date getFechaDesembolso() {
		return fechaDesembolso;
	}

	public void setFechaDesembolso(Date fechaDesembolso) {
		this.fechaDesembolso = fechaDesembolso;
	}

	public boolean isEsCoordinadorRequisitos() {
		return esCoordinadorRequisitos;
	}

	public void setEsCoordinadorRequisitos(boolean esCoordinadorRequisitos) {
		this.esCoordinadorRequisitos = esCoordinadorRequisitos;
	}

	public boolean isExisteCartasInicio() {
		if (listaCartasProyecto != null) {
			Iterator<ProyectoCarta> i = listaCartasProyecto.iterator();
			while (i.hasNext()) {
				ProyectoCarta proyectoCartaTemporal = i.next();
				if (proyectoCartaTemporal.getCarta().getId().equals(1L)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Dependencia> getDependenciasUN() {
		return dependenciasUN;
	}

	public void setDependenciasUN(List<Dependencia> dependenciasUN) {
		this.dependenciasUN = dependenciasUN;
	}

	public SelectItem[] getDependenciaItem() {
		return dependenciaItem;
	}

	public void setDependenciaItem(SelectItem[] dependenciaItem) {
		this.dependenciaItem = dependenciaItem;
	}

	public SelectItem[] getVigenciaItems() {
		return vigenciaItems;
	}

	public void setVigenciaItems(SelectItem[] vigenciaItems) {
		this.vigenciaItems = vigenciaItems;
	}

	public boolean isMostrarFuenteExterna() {
		return mostrarFuenteExterna;
	}

	public void setMostrarFuenteExterna(boolean mostrarFuenteExterna) {
		this.mostrarFuenteExterna = mostrarFuenteExterna;
	}

	public boolean isRequiereOtraFuente() {
		return requiereOtraFuente;
	}

	public void setRequiereOtraFuente(boolean requiereOtraFuente) {
		this.requiereOtraFuente = requiereOtraFuente;
	}

	public String getNombreFuente() {
		return nombreFuente;
	}

	public void setNombreFuente(String nombreFuente) {
		this.nombreFuente = nombreFuente;
	}

	public String getNitFuente() {
		return nitFuente;
	}

	public void setNitFuente(String nitFuente) {
		this.nitFuente = nitFuente;
	}

	public String getNaturalezaFuente() {
		return naturalezaFuente;
	}

	public void setNaturalezaFuente(String naturalezaFuente) {
		this.naturalezaFuente = naturalezaFuente;
	}

	public String getTelefonoFuente() {
		return telefonoFuente;
	}

	public void setTelefonoFuente(String telefonoFuente) {
		this.telefonoFuente = telefonoFuente;
	}

	public String getDireccionFuente() {
		return direccionFuente;
	}

	public void setDireccionFuente(String direccionFuente) {
		this.direccionFuente = direccionFuente;
	}

	public SelectItem[] getTiposNaturalezaFuenteItem() {
		return crearListaItemDominioDetalle(Dominio.NAT_ENTIDAD);
	}

	public UIComponent getCBCrearFuente() {
		return cBCrearFuente;
	}

	public void setCBCrearFuente(UIComponent cBCrearFuente) {
		this.cBCrearFuente = cBCrearFuente;
	}

	public Long getValorIngreso2() {
		return valorIngreso2;
	}

	public void setValorIngreso2(Long valorIngreso2) {
		this.valorIngreso2 = valorIngreso2;
	}

	public Long getValorIngreso3() {
		return valorIngreso3;
	}

	public void setValorIngreso3(Long valorIngreso3) {
		this.valorIngreso3 = valorIngreso3;
	}

	public String getNuevaObservacion() {
		return nuevaObservacion;
	}

	public void setNuevaObservacion(String nuevaObservacion) {
		this.nuevaObservacion = nuevaObservacion;
	}

	public String getTipoObservacion() {
		return tipoObservacion;
	}

	public void setTipoObservacion(String tipoObservacion) {
		this.tipoObservacion = tipoObservacion;
	}

	public ObservacionSeguimiento getObservacionSeguimientoSeleccionada() {
		return observacionSeguimientoSeleccionada;
	}

	public void setObservacionSeguimientoSeleccionada(ObservacionSeguimiento observacionSeguimientoSeleccionada) {
		this.observacionSeguimientoSeleccionada = observacionSeguimientoSeleccionada;
	}

	public Long getIdPryConsulta() {
		return idPryConsulta;
	}

	public void setIdPryConsulta(Long idPryConsulta) {
		this.idPryConsulta = idPryConsulta;
	}

	public String getTipoActividadNombre() {
		return tipoActividadNombre;
	}

	public UIComponent getBtnCopiaProyecto() {
		return btnCopiaProyecto;
	}

	public void setBtnCopiaProyecto(UIComponent btnCopiaProyecto) {
		this.btnCopiaProyecto = btnCopiaProyecto;
	}

	public String getAgendaConocimientoId() {
		return agendaConocimientoId;
	}

	public void setAgendaConocimientoId(String agendaConocimiento1) {
		this.agendaConocimientoId = agendaConocimiento1;
	}

	public SelectItem[] getAgendaConocimientoItem() {
		return agendaConocimientoItem;
	}

	public void setAgendaConocimientoItem(SelectItem[] agendaConocimiento1Item) {
		this.agendaConocimientoItem = agendaConocimiento1Item;
	}

	public int getNumDesembolso() {
		return numDesembolso;
	}

	public void setNumDesembolso(int numDesembolso) {
		this.numDesembolso = numDesembolso;
	}

	public UIComponent getCodExterna() {
		return codExterna;
	}

	public void setCodExterna(UIComponent codExterna) {
		this.codExterna = codExterna;
	}

	public UIComponent getUiEntidades() {
		return uiEntidades;
	}

	public void setUiEntidades(UIComponent uiEntidades) {
		this.uiEntidades = uiEntidades;
	}

	public UIComponent getUiDesembolsos() {
		return uiDesembolsos;
	}

	public void setDesembolsoSeleccionado(Gasto desembolsoSeleccionado) {
		this.desembolsoSeleccionado = desembolsoSeleccionado;
	}

	public SelectItem[] getModContratacionItems() {
		return modContratacionItems;
	}

	public void setModContratacionItems(SelectItem[] modContratacionItems) {
		this.modContratacionItems = modContratacionItems;
	}

	public ConvenioObligacion getObligacionSeleccionada() {
		return obligacionSeleccionada;
	}

	public void setObligacionSeleccionada(ConvenioObligacion obligacion) {
		this.obligacionSeleccionada = obligacion;
	}

	public Gasto getRecursoSeleccionado() {
		return recursoSeleccionado;
	}

	public void setRecursoSeleccionado(Gasto recursoSeleccionado) {
		this.recursoSeleccionado = recursoSeleccionado;
	}

	public Date getFechaInicioConvenio() {
		return fechaInicioConvenio;
	}

	public void setFechaInicioConvenio(Date fechaInicioConvenio) {
		this.fechaInicioConvenio = fechaInicioConvenio;
	}

	public Date getFechaFinConvenio() {
		return fechaFinConvenio;
	}

	public void setFechaFinConvenio(Date fechaFinConvenio) {
		this.fechaFinConvenio = fechaFinConvenio;
	}

	public Date getFechaInformeParcial() {
		return fechaInformeParcial;
	}

	public void setFechaInformeParcial(Date fechaInformeParcial) {
		this.fechaInformeParcial = fechaInformeParcial;
	}

	public UIComponent getUiInformes() {
		return uiInformes;
	}

	public void setUiInformes(UIComponent uiInformes) {
		this.uiInformes = uiInformes;
	}

	public ProyectoCompromiso getCompromisoProyectoSeleccionado() {
		return compromisoProyectoSeleccionado;
	}

	public void setCompromisoProyectoSeleccionado(ProyectoCompromiso compromisoProyectoSeleccionado) {
		this.compromisoProyectoSeleccionado = compromisoProyectoSeleccionado;
	}

	public void setUiDesembolsos(UIComponent uiDesembolsos) {
		this.uiDesembolsos = uiDesembolsos;
	}

	public Gasto getDesembolsoSeleccionado() {
		return desembolsoSeleccionado;
	}

	public String getNombreVicerrector() {
		return nombreVicerrector;
	}

	public void setNombreVicerrector(String nombreVicerrector) {
		this.nombreVicerrector = nombreVicerrector;
	}

	public String getNombreCargoVicerrector() {
		return nombreCargoVicerrector;
	}

	public void setNombreCargoVicerrector(String nombreCargoVicerrector) {
		this.nombreCargoVicerrector = nombreCargoVicerrector;
	}

	public boolean isCambiarFirma() {
		return cambiarFirma;
	}

	public void setCambiarFirma(boolean cambiarFirma) {
		this.cambiarFirma = cambiarFirma;
	}

	public SolicitudInvestigador getSolicitudInvestigadorSeleccionada() {
		return solicitudInvestigadorSeleccionada;
	}

	public void setSolicitudInvestigadorSeleccionada(SolicitudInvestigador solicitudInvestigadorSeleccionada) {
		this.solicitudInvestigadorSeleccionada = solicitudInvestigadorSeleccionada;
	}

	/**
	 * @return the fechaCalculaProrroga
	 */
	public Date getFechaCalculaProrroga() {
		return fechaCalculaProrroga;
	}

	/**
	 * @param fechaCalculaProrroga the fechaCalculaProrroga to set
	 */
	public void setFechaCalculaProrroga(Date fechaCalculaProrroga) {
		this.fechaCalculaProrroga = fechaCalculaProrroga;
	}

	/**
	 * @return the mesesCalculo
	 */
	public String getMesesCalculo() {
		return mesesCalculo;
	}

	/**
	 * @param mesesCalculo the mesesCalculo to set
	 */
	public void setMesesCalculo(String mesesCalculo) {
		this.mesesCalculo = mesesCalculo;
	}

	/**
	 * @return the diasCalculo
	 */
	public String getDiasCalculo() {
		return diasCalculo;
	}

	/**
	 * @param diasCalculo the diasCalculo to set
	 */
	public void setDiasCalculo(String diasCalculo) {
		this.diasCalculo = diasCalculo;
	}

	public Date getMininaFechaCalculo() {
		if (fechaFinalProyecto != null) {
			return DateUtils.addDays(fechaFinalProyecto, 1);
		} else {
			return new Date();
		}
	}

	/**
	 * @return the mostrarCodigoSolicitudJustificacion
	 */
	public boolean isMostrarCodigoSolicitudJustificacion() {
		return mostrarCodigoSolicitudJustificacion;
	}

	/**
	 * @return the solicitudSeleccionadaIntegrantes
	 */
	public Solicitud getSolicitudSeleccionadaIntegrantes() {
		return solicitudSeleccionadaIntegrantes;
	}

	/**
	 * @param solicitudSeleccionadaIntegrantes the solicitudSeleccionadaIntegrantes
	 *                                         to set
	 */
	public void setSolicitudSeleccionadaIntegrantes(Solicitud solicitudSeleccionadaIntegrantes) {
		this.solicitudSeleccionadaIntegrantes = solicitudSeleccionadaIntegrantes;
	}

	public boolean isEdicionFormalizacionHabilitada() {
		return edicionFormalizacionHabilitada;
	}

	/**
	 * @return the totalMesesSuspencion
	 */
	public Long getTotalMesesSuspencion() {
		return totalMesesSuspencion;
	}

	/**
	 * @return the totalDiasSuspencion
	 */
	public Long getTotalDiasSuspencion() {
		return totalDiasSuspencion;
	}

	public void setTipoCompromiso(String tipoCompromiso) {
		this.tipoCompromiso = tipoCompromiso;
	}

	public void setFechaVencimientoCompromiso(Date fechaVencimientoCompromiso) {
		this.fechaVencimientoCompromiso = fechaVencimientoCompromiso;
	}

	public void setCompromisoSeleccionado(ProyectoCompromiso compromisoSeleccionado) {
		this.compromisoSeleccionado = compromisoSeleccionado;
	}

	public String getTipoCompromiso() {
		return tipoCompromiso;
	}

	public Date getFechaVencimientoCompromiso() {
		return fechaVencimientoCompromiso;
	}

	public List<HistoricoEstadoSolicitud> getHistoricoEstadoSolicitudes() {
		return historicoEstadoSolicitudes;
	}

	/**
	 * @return the tipoActoAdministrativoItem
	 */
	public SelectItem[] getTipoActoAdministrativoItem() {
		return tipoActoAdministrativoItem;
	}

	/**
	 * @return the docenteYaFirmo
	 */
	public boolean isDocenteYaFirmo() {
		return docenteYaFirmo;
	}

	/**
	 * @param docenteYaFirmo the docenteYaFirmo to set
	 */
	public void setDocenteYaFirmo(boolean docenteYaFirmo) {
		this.docenteYaFirmo = docenteYaFirmo;
	}

	public SelectItem[] getOpcionesReclamacionesItem() {
		return opcionesReclamacionesItem;
	}

	public void setOpcionesReclamacionesItem(SelectItem[] opcionesReclamacionesItem) {
		this.opcionesReclamacionesItem = opcionesReclamacionesItem;
	}

	public boolean isEsRespuestaReclamacion() {
		return esRespuestaReclamacion;
	}

	public void setEsRespuestaReclamacion(boolean esRespuestaReclamacion) {
		this.esRespuestaReclamacion = esRespuestaReclamacion;
	}

	public boolean isEsReclamacionProfesor() {
		return esReclamacionProfesor;
	}

	public void setEsReclamacionProfesor(boolean esReclamacionProfesor) {
		this.esReclamacionProfesor = esReclamacionProfesor;
	}

	public boolean isEsRespuestaReclamacionEvaluacion() {
		return esRespuestaReclamacionEvaluacion;
	}

	public void setEsRespuestaReclamacionEvaluacion(boolean esRespuestaReclamacionEvaluacion) {
		this.esRespuestaReclamacionEvaluacion = esRespuestaReclamacionEvaluacion;
	}

	public boolean isEsReclamacionProfesorEvaluacion() {
		return esReclamacionProfesorEvaluacion;
	}

	public void setEsReclamacionProfesorEvaluacion(boolean esReclamacionProfesorEvaluacion) {
		this.esReclamacionProfesorEvaluacion = esReclamacionProfesorEvaluacion;
	}

	public List<Proyecto> getAutorizacionesBiodiversidadProyecto() {
		return autorizacionesBiodiversidadProyecto;
	}

	public void setAutorizacionesBiodiversidadProyecto(List<Proyecto> autorizacionesBiodiversidadProyecto) {
		this.autorizacionesBiodiversidadProyecto = autorizacionesBiodiversidadProyecto;
	}

	public String getErrorDevolucion() {
		return errorDevolucion;
	}

	public String getNumeroResolucion() {
		return numeroResolucion;
	}

	public void setNumeroResolucion(String numeroResolucion) {
		this.numeroResolucion = numeroResolucion;
	}

	public Date getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(Date fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public String getOrdenadorTipoResolucion() {
		return ordenadorTipoResolucion;
	}

	public void setOrdenadorTipoResolucion(String ordenadorTipoResolucion) {
		this.ordenadorTipoResolucion = ordenadorTipoResolucion;
	}

	public String getOrdenadorNombreResolucion() {
		return ordenadorNombreResolucion;
	}

	public void setOrdenadorNombreResolucion(String ordenadorNombreResolucion) {
		this.ordenadorNombreResolucion = ordenadorNombreResolucion;
	}

	public String getFacultadResolucion() {
		return facultadResolucion;
	}

	public void setFacultadResolucion(String facultadResolucion) {
		this.facultadResolucion = facultadResolucion;
	}

	public SelectItem[] getFacultadResolucionItems() {
		return facultadResolucionItems;
	}

	public void setFacultadResolucionItems(SelectItem[] facultadResolucionItems) {
		this.facultadResolucionItems = facultadResolucionItems;
	}

	public SelectItem[] getOrdenadorTipoItems() {
		return ordenadorTipoItems;
	}

	public SelectItem[] getAcuerdoItems() {
		return ACUERDO_ITEMS;
	}

	public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidadSeleccionada() {
		return dependenciaAreaResponsabilidadSeleccionada;
	}

	public void setDependenciaAreaResponsabilidadSeleccionada(
			DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada) {
		this.dependenciaAreaResponsabilidadSeleccionada = dependenciaAreaResponsabilidadSeleccionada;
	}

	/**
	 * @return the uiObligaciones
	 */
	public UIComponent getUiObligaciones() {
		return uiObligaciones;
	}

	/**
	 * @param uiObligaciones the uiObligaciones to set
	 */
	public void setUiObligaciones(UIComponent uiObligaciones) {
		this.uiObligaciones = uiObligaciones;
	}

	/**
	 * @return the estadoProyectoLegalizacion
	 */
	public String getEstadoProyectoLegalizacion() {
		return estadoProyectoLegalizacion;
	}

	/**
	 * @param estadoProyectoLegalizacion the estadoProyectoLegalizacion to set
	 */
	public void setEstadoProyectoLegalizacion(String estadoProyectoLegalizacion) {
		this.estadoProyectoLegalizacion = estadoProyectoLegalizacion;
		EstadoProyecto estadoProyecto = new EstadoProyecto();
		estadoProyecto.setId(estadoProyectoLegalizacion);
		proyectoActual.setEstadoProyectoLegalizacion(estadoProyecto);
	}

	public boolean isHabilitarEdicionProyecto() {
		return habilitarEdicionProyecto;
	}

	public SelectItem[] getRecursoIngresoItems() {
		return recursoIngresoItems;
	}

	/**
	 * @return the uiInformesInternas
	 */
	public UIComponent getUiInformesInternas() {
		return uiInformesInternas;
	}

	/**
	 * @param uiInformesInternas the uiInformesInternas to set
	 */
	public void setUiInformesInternas(UIComponent uiInformesInternas) {
		this.uiInformesInternas = uiInformesInternas;
	}

	public Boolean getEsConvocatoriaLibros() {
		return esConvocatoriaLibros;
	}

	public void setEsConvocatoriaLibros(Boolean esConvocatoriaLibros) {
		this.esConvocatoriaLibros = esConvocatoriaLibros;
	}

	public List<ProyectoCompromiso> getListaCompromisosObligaciones() {
		return listaCompromisosObligaciones;
	}

	public void setListaCompromisosObligaciones(List<ProyectoCompromiso> listaCompromisosObligaciones) {
		this.listaCompromisosObligaciones = listaCompromisosObligaciones;
	}

	public List<ProyectoCompromiso> getListaCompromisosInformesParcialesYFinales() {
		return listaCompromisosInformesParcialesYFinales;
	}

	public void setListaCompromisosInformesParcialesYFinales(
			List<ProyectoCompromiso> listaCompromisosInformesParcialesYFinales) {
		this.listaCompromisosInformesParcialesYFinales = listaCompromisosInformesParcialesYFinales;
	}

	public List<ProyectoCompromiso> getListaCompromisosDesembolsos() {
		return listaCompromisosDesembolsos;
	}

	public void setListaCompromisosDesembolsos(List<ProyectoCompromiso> listaCompromisosDesembolsos) {
		this.listaCompromisosDesembolsos = listaCompromisosDesembolsos;
	}

	public boolean isEsVacialistaDesembolsos() {
		return esVacialistaDesembolsos;
	}

	public void setEsVacialistaDesembolsos(boolean esVacialistaDesembolsos) {
		this.esVacialistaDesembolsos = esVacialistaDesembolsos;
	}

	public boolean isEsVacialistaCompromisosObligaciones() {
		return esVacialistaCompromisosObligaciones;
	}

	public void setEsVacialistaCompromisosObligaciones(boolean esVacialistaCompromisosObligaciones) {
		this.esVacialistaCompromisosObligaciones = esVacialistaCompromisosObligaciones;
	}

	public Boolean getEsConvocatoriaEventos() {
		return esConvocatoriaEventos;
	}

	public void setEsConvocatoriaEventos(Boolean esConvocatoriaEventos) {
		this.esConvocatoriaEventos = esConvocatoriaEventos;
	}

	public String getOtrosi() {
		return otrosi;
	}

	public void setOtrosi(String otrosi) {
		this.otrosi = otrosi;
	}

	public String getOtrosiNum() {
		return otrosiNum;
	}

	public void setOtrosiNum(String otrosiNum) {
		this.otrosiNum = otrosiNum;
	}

	public Date getOtrosiFecha() {
		return otrosiFecha;
	}

	public void setOtrosiFecha(Date otrosiFecha) {
		this.otrosiFecha = otrosiFecha;
	}

	public char getRequiereConsiderando() {
		return requiereConsiderando;
	}

	public void setRequiereConsiderando(char requiereConsiderando) {
		this.requiereConsiderando = requiereConsiderando;
	}

	public String getConsiderando() {
		return considerando;
	}

	public void setConsiderando(String considerando) {
		this.considerando = considerando;
	}

	public List<DetalleAdicionPresupuesto> getListaSolicitudAdicionPresupuesto() {
		return listaSolicitudAdicionPresupuesto;
	}

	public void setListaSolicitudAdicionPresupuesto(List<DetalleAdicionPresupuesto> listaSolicitudAdicionPresupuesto) {
		this.listaSolicitudAdicionPresupuesto = listaSolicitudAdicionPresupuesto;
	}

	public SolicitudAdicionPresupuestal getAdicionPresupuesto() {
		return adicionPresupuesto;
	}

	public void setAdicionPresupuesto(SolicitudAdicionPresupuestal adicionPresupuesto) {
		this.adicionPresupuesto = adicionPresupuesto;
	}

	public boolean isBloquearSolicitudes() {
		return bloquearSolicitudes;
	}

	public List<Archivo> getListaArchivosReclamacionRequisitos() {
		return listaArchivosReclamacionRequisitos;
	}

	public void setListaArchivosReclamacionRequisitos(List<Archivo> listaArchivosReclamacionRequisitos) {
		this.listaArchivosReclamacionRequisitos = listaArchivosReclamacionRequisitos;
	}

	public Archivo getArchivoRecReqSeleccionado() {
		return archivoRecReqSeleccionado;
	}

	public void setArchivoRecReqSeleccionado(Archivo archivoRecReqSeleccionado) {
		this.archivoRecReqSeleccionado = archivoRecReqSeleccionado;
	}

	public boolean isNoAplicaCodigoQuipu() {
		return noAplicaCodigoQuipu;
	}

	public void setNoAplicaCodigoQuipu(boolean noAplicaCodigoQuipu) {
		this.noAplicaCodigoQuipu = noAplicaCodigoQuipu;
	}

	public String getCodigoQuipuAux() {
		return codigoQuipuAux;
	}

	public void setCodigoQuipuAux(String codigoQuipuAux) {
		this.codigoQuipuAux = codigoQuipuAux;
	}

	public String getMesesCalculoVista() {
		return mesesCalculoVista;
	}

	public void setMesesCalculoVista(String mesesCalculoVista) {
		this.mesesCalculoVista = mesesCalculoVista;
	}

	public String getDiasCalculoVista() {
		return diasCalculoVista;
	}

	public void setDiasCalculoVista(String diasCalculoVista) {
		this.diasCalculoVista = diasCalculoVista;
	}

	public SelectItem[] getFacultadInstFormaItem() {
		return facultadInstFormaItem;
	}

	public void setFacultadInstFormaItem(SelectItem[] facultadInstFormaItem) {
		this.facultadInstFormaItem = facultadInstFormaItem;
	}

	public Long getAnioVigIngreso() {
		return anioVigIngreso;
	}

	public void setAnioVigIngreso(Long anioVigIngreso) {
		this.anioVigIngreso = anioVigIngreso;
	}

	public Proyecto getProyectoActual() {
		return proyectoActual;
	}

	public void setProyectoActual(Proyecto proyectoActual) {
		this.proyectoActual = proyectoActual;
	}

	public String getEstadoProyecto() {
		return estadoProyecto;
	}

	public void setEstadoProyecto(String estadoProyecto) {
		this.estadoProyecto = estadoProyecto;
	}

	public String getAvalId() {
		return avalId;
	}

	public void setAvalId(String avalId) {
		this.avalId = avalId;
	}

	public SelectItem[] getPlantillas() {
		return plantillas;
	}

	public String getErrorValidacion() {
		return errorValidacion;
	}

	public Date getFechaSesion() {
		return fechaSesion;
	}

	public void setFechaSesion(Date fechaSesion) {
		this.fechaSesion = fechaSesion;
	}

	public String getNombreParametro() {
		return nombreParametro;
	}

	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}

	public String getMensajeCartas() {
		return mensajeCartas;
	}

	public boolean isMostrarGenerarPDF() {
		return mostrarGenerarPDF;
	}

	public boolean isMostrarFechaSesion() {
		return mostrarFechaSesion;
	}

	public boolean isMostrarCambioRubro() {
		return mostrarCambioRubro;
	}

	public boolean isMostrarSaldoFinanciacion() {
		return mostrarSaldoFinanciacion;
	}

	public String getSaldoFinanciacion() {
		return saldoFinanciacion;
	}

	public void setSaldoFinanciacion(String saldoFinanciacion) {
		this.saldoFinanciacion = saldoFinanciacion;
	}

	public SelectItem[] getCodigoSolicitudItem() {
		return codigoSolicitudItem;
	}

	public String getCodigoSolicitud() {
		return codigoSolicitud;
	}

	public void setCodigoSolicitud(String codigoSolicitud) {
		this.codigoSolicitud = codigoSolicitud;
	}

	public SelectItem[] getCodigoSolicitudProrrogaItem() {
		return codigoSolicitudProrrogaItem;
	}

	public String getCodigoSolicitudProrroga() {
		return codigoSolicitudProrroga;
	}

	public void setCodigoSolicitudProrroga(String codigoSolicitudProrroga) {
		this.codigoSolicitudProrroga = codigoSolicitudProrroga;
	}

	public String getMensajeEnvioCarta() {
		return mensajeEnvioCarta;
	}

	public boolean isMostrarPanelCorreo() {
		return mostrarPanelCorreo;
	}

	public List<Archivo> getListaArchivos() {
		return listaArchivos;
	}

	public void setFilteredAlertas(List<AlertaProyecto> filteredAlertas) {
		this.filteredAlertas = filteredAlertas;
	}

	public List<AlertaProyecto> getFilteredAlertas() {
		return filteredAlertas;
	}

	public void setAlertaSeleccionada(AlertaProyecto alertaSeleccionada) {
		this.alertaSeleccionada = alertaSeleccionada;
	}

	public AlertaProyecto getAlertaSeleccionada() {
		return alertaSeleccionada;
	}

	public List<ProyectoInforme> getListaInformes() {
		return listaInformes;
	}

	public boolean isMostrarCompromisos() {
		return mostrarCompromisos;
	}

	public void setInformeSeleccionado(ProyectoInforme informeSeleccionado) {
		this.informeSeleccionado = informeSeleccionado;
	}

	public ProyectoInforme getInformeSeleccionado() {
		return informeSeleccionado;
	}

	public void setArchivoResumenSeleccionado(ArchivoResumen archivoResumenSeleccionado) {
		this.archivoResumenSeleccionado = archivoResumenSeleccionado;
	}

	public ArchivoResumen getArchivoResumenSeleccionado() {
		return archivoResumenSeleccionado;
	}

	public List<Archivo> getListaArchivosProyecto() {
		return listaArchivosProyecto;
	}

	public boolean isEsCoordinador() {
		return esCoordinador;
	}

	public Persona getCoordinadorSeguimientoProyecto() {
		return coordinadorSeguimientoProyecto;
	}

	public SelectItem[] getPermitirEditarItem() {
		return permitirEditarItem;
	}

	public void setProrrogaSeleccionada(ProyectoProrroga prorrogaSeleccionada) {
		this.prorrogaSeleccionada = prorrogaSeleccionada;
	}

	public ProyectoProrroga getProrrogaSeleccionada() {
		return prorrogaSeleccionada;
	}

	public SelectItem[] getRecomendaciones() {
		return recomendaciones;
	}

	public List<ProyectoEvaluador> getListaProyectoEvaluador() {
		return listaProyectoEvaluador;
	}

	public void setProyectoEvaluadorSeleccionado(ProyectoEvaluador proyectoEvaluadorSeleccionado) {
		this.proyectoEvaluadorSeleccionado = proyectoEvaluadorSeleccionado;
	}

	public ProyectoEvaluador getProyectoEvaluadorSeleccionado() {
		return proyectoEvaluadorSeleccionado;
	}

	public boolean isEsCoordinadorEvaluacion() {
		return esCoordinadorEvaluacion;
	}

	public Persona getCoordinadorEvaluacion() {
		return coordinadorEvaluacion;
	}

	public org.primefaces.model.UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(org.primefaces.model.UploadedFile archivo) {
		this.archivo = archivo;
	}

	public String getMensajeSubidaArchivo() {
		return mensajeSubidaArchivo;
	}

	public boolean isMostrarEstudiantes() {
		return mostrarEstudiantes;
	}

	public boolean isMostrarJovenInvExterno() {
		return mostrarJovenInvExterno;
	}

	public void setCedulaEstudiante(String cedulaEstudiante) {
		this.cedulaEstudiante = cedulaEstudiante;
	}

	public String getCedulaEstudiante() {
		return cedulaEstudiante;
	}

	public SelectItem[] getEstudianteItem() {
		return estudianteItem;
	}

	public SelectItem[] getJovenInvExternoItem() {
		return jovenInvExternoItem;
	}

	public boolean isMostrarParametrosCertificadoMovilizacion() {
		return mostrarParametrosCertificadoMovilizacion;
	}

	public void setCodigoSolicitudCertificado(String codigoSolicitudCertificado) {
		this.codigoSolicitudCertificado = codigoSolicitudCertificado;
	}

	public String getCodigoSolicitudCertificado() {
		return codigoSolicitudCertificado;
	}

	public SelectItem[] getTiposCarta() {
		return tiposCarta;
	}

	public String getTipoCartSeleccionado() {
		return tipoCartSeleccionado;
	}

	public void setTipoCartSeleccionado(String tipoCartSeleccionado) {
		this.tipoCartSeleccionado = tipoCartSeleccionado;
	}

	public List<Aval> getAvalesAsociadosProyecto() {
		return avalesAsociadosProyecto;
	}

	public SelectItem[] getEstadoItems() {
		return estadoItems;
	}

	public SelectItem[] getTipologiaItems() {
		return tipologiaItems;
	}

	public String getUnidadEjecutora() {
		return unidadEjecutora;
	}

	public void setUnidadEjecutora(String unidadEjecutora) {
		this.unidadEjecutora = unidadEjecutora;
	}

	public SelectItem[] getUnidadEjecItems() {
		return unidadEjecItems;
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

	public SelectItem[] getTipofechaItems() {
		return tipofechaItems;
	}

	public String getDesembolsos() {
		return desembolsos;
	}

	public void setDesembolsos(String desembolsos) {
		this.desembolsos = desembolsos;
	}

	public SelectItem[] getDesembolsoItems() {
		return desembolsoItems;
	}

	public String getInformes() {
		return informes;
	}

	public void setInformes(String informes) {
		this.informes = informes;
	}

	public SelectItem[] getInformesItems() {
		return informesItems;
	}

	public String getVinculaFacultades() {
		return vinculaFacultades;
	}

	public void setVinculaFacultades(String vinculaFacultades) {
		this.vinculaFacultades = vinculaFacultades;
	}

	public SelectItem[] getVinculacionItems() {
		return vinculacionItems;
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

	public SelectItem[] getSedesItems() {
		return sedesItems;
	}

	public String getEntidadSel() {
		return entidadSel;
	}

	public void setEntidadSel(String entidadSel) {
		this.entidadSel = entidadSel;
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

	public SelectItem[] getTipologiaSecItems() {
		return tipologiaSecItems;
	}

	public SelectItem[] getPeriodoInformesItems() {
		return periodoInformesItems;
	}

	public boolean isVerPeriodosInforme() {
		return verPeriodosInforme;
	}

	public boolean isTieneVersiones() {
		return tieneVersiones;
	}

	public List<Proyecto> getListaVersiones() {
		return listaVersiones;
	}

	public String getTipoEntidadSel() {
		return tipoEntidadSel;
	}

	public SelectItem[] getTipoEntidadItems() {
		return tipoEntidadItems;
	}

	public Long getValorEntidadEsp() {
		return valorEntidadEsp;
	}

	public void setValorEntidadEsp(Long valorEntidadEsp) {
		this.valorEntidadEsp = valorEntidadEsp;
	}

	public SelectItem[] getFacultadInstItem() {
		return facultadInstItem;
	}

	public String getFacInstitutoSel() {
		return facInstitutoSel;
	}

	public SelectItem[] getMotivoEstadoItems() {
		return motivoEstadoItems;
	}

	public SelectItem[] getClausulasItems() {
		return clausulasItems;
	}

	public Persona getCoordinadorRequisitos() {
		return coordinadorRequisitos;
	}

	public Aval getAvalAsociadoPry() {
		return avalAsociadoPry;
	}

	public SelectItem[] getIncluirJustificacionItem() {
		return incluirJustificacionItem;
	}

	public String getIncluirJustificacionSolicitud() {
		return incluirJustificacionSolicitud;
	}

	public void setIncluirJustificacionSolicitud(String incluirJustificacionSolicitud) {
		this.incluirJustificacionSolicitud = incluirJustificacionSolicitud;
	}

	public Long getAvalProy() {
		return avalProy;
	}

	public SolicitudDocumento getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	public void setDocumentoSeleccionado(SolicitudDocumento documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}

	public TramiteSolicitud getTramiteSeleccionado() {
		return tramiteSeleccionado;
	}

	public void setTramiteSeleccionado(TramiteSolicitud tramiteSeleccionado) {
		this.tramiteSeleccionado = tramiteSeleccionado;
	}

	public String getMensajeTransaccionRubro() {
		return mensajeTransaccionRubro;
	}

	public boolean isMostrarCodigoQuipu() {
		return mostrarCodigoQuipu;
	}

	public String getCodigoQuipu() {
		return codigoQuipu;
	}

	public void setCodigoQuipu(String codigoQuipu) {
		this.codigoQuipu = codigoQuipu;
	}

	public Solicitud getSolicitudSeleccionada() {
		return solicitudSeleccionada;
	}

	public void setSolicitudSeleccionada(Solicitud solicitudSeleccionada) {
		this.solicitudSeleccionada = solicitudSeleccionada;
	}

	public int getTamañoListaAsesor() {
		return alertasAsesor.size();
	}

	public List<AlertaProyecto> getAlertasAsesor() {
		return alertasAsesor;
	}

	public String getMensajeActualizacion() {
		return mensajeActualizacion;
	}

	public Map getDominioRespuestaSolicitud() {
		return dominioRespuestaSolicitud;
	}

	public void setDominioRespuestaSolicitud(Map dominioRespuestaSolicitud) {
		this.dominioRespuestaSolicitud = dominioRespuestaSolicitud;
	}

	public void setOpcionesDominioRespuestaSolicitud(List opcionesDominioRespuestaSolicitud) {
		this.opcionesDominioRespuestaSolicitud = opcionesDominioRespuestaSolicitud;
	}

	public SelectItem[] getGastosProyectoItem() {
		return gastosProyectoItem;
	}

	public void setGastosProyectoItem(SelectItem[] gastosProyectoItem) {
		this.gastosProyectoItem = gastosProyectoItem;
	}

	public UIData getTablaTramites() {
		return tablaTramites;
	}

	public void setTablaTramites(UIData tablaTramites) {
		this.tablaTramites = tablaTramites;
	}

	public UIData getTablaObservaciones() {
		return tablaObservaciones;
	}

	public void setTablaObservaciones(UIData tablaObservaciones) {
		this.tablaObservaciones = tablaObservaciones;
	}

	public UIData getTablaProductos() {
		return tablaProductos;
	}

	public void setTablaProductos(UIData tablaProductos) {
		this.tablaProductos = tablaProductos;
	}

	public String getDestinoCorreo() {
		return destinoCorreo;
	}

	public void setDestinoCorreo(String destinoCorreo) {
		this.destinoCorreo = destinoCorreo;
	}

	public Long getIdProyectoalertas() {
		return idProyectoalertas;
	}

	public void setIdProyectoalertas(Long idProyectoalertas) {
		this.idProyectoalertas = idProyectoalertas;
	}

	public SelectItem[] getTiposViaSolicitudItem() {
		return tiposViaSolicitudItem;
	}

	public void setTiposViaSolicitudItem(SelectItem[] tiposViaSolicitudItem) {
		this.tiposViaSolicitudItem = tiposViaSolicitudItem;
	}

	public SelectItem[] getFuentesProyectoItem() {
		return fuentesProyectoItem;
	}

	public void setFuentesProyectoItem(SelectItem[] fuentesProyectoItem) {
		this.fuentesProyectoItem = fuentesProyectoItem;
	}

	public SelectItem[] getAlertasProyectoItem() {
		return alertasProyectoItem;
	}

	public void setAlertasProyectoItem(SelectItem[] alertasProyectoItem) {
		this.alertasProyectoItem = alertasProyectoItem;
	}

	public String getAsuntoAlertaCorreo() {
		return asuntoAlertaCorreo;
	}

	public void setAsuntoAlertaCorreo(String asuntoAlertaCorreo) {
		this.asuntoAlertaCorreo = asuntoAlertaCorreo;
	}

	public String getCuerpoCorreo() {
		return cuerpoCorreo;
	}

	public void setCuerpoCorreo(String cuerpoCorreo) {
		this.cuerpoCorreo = cuerpoCorreo;
	}

	public String getAsuntoCorreo() {
		return asuntoCorreo;
	}

	public void setAsuntoCorreo(String asuntoCorreo) {
		this.asuntoCorreo = asuntoCorreo;
	}

	public Persona getInvestigadorPrincipal() {
		return investigadorPrincipal;
	}

	public void setInvestigadorPrincipal(Persona investigadorPrincipal) {
		this.investigadorPrincipal = investigadorPrincipal;
	}

	public Locale getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Locale localidad) {
		this.localidad = localidad;
	}

	public SelectItem[] getPlantillasActaItem() {
		return plantillasActaItem;
	}

	public void setPlantillasActaItem(SelectItem[] plantillasActaItem) {
		this.plantillasActaItem = plantillasActaItem;
	}

	public Persona getCoordinadorSeguimiento() {
		return coordinadorSeguimiento;
	}

	public void setCoordinadorSeguimiento(Persona coordinadorSeguimiento) {
		this.coordinadorSeguimiento = coordinadorSeguimiento;
	}

	public Dependencia getDependenciaProyecto() {
		return dependenciaProyecto;
	}

	public void setDependenciaProyecto(Dependencia dependenciaProyecto) {
		this.dependenciaProyecto = dependenciaProyecto;
	}

	public Long getMontoAprobadoProyecto() {
		return montoAprobadoProyecto;
	}

	public void setMontoAprobadoProyecto(Long montoAprobadoProyecto) {
		this.montoAprobadoProyecto = montoAprobadoProyecto;
	}

	public String getCopiaCorreo() {
		return copiaCorreo;
	}

	public void setCopiaCorreo(String copiaCorreo) {
		this.copiaCorreo = copiaCorreo;
	}

	public String getOrigenCorreo() {
		return origenCorreo;
	}

	public void setOrigenCorreo(String origenCorreo) {
		this.origenCorreo = origenCorreo;
	}

	public String getFacHist() {
		return facHist;
	}

	public void setFacHist(String facHist) {
		this.facHist = facHist;
	}

	public String getSedHist() {
		return sedHist;
	}

	public void setSedHist(String sedHist) {
		this.sedHist = sedHist;
	}

	public boolean isTieneVariosCodigoQuipu() {
		return tieneVariosCodigoQuipu;
	}

	public void setTieneVariosCodigoQuipu(boolean tieneVariosCodigoQuipu) {
		this.tieneVariosCodigoQuipu = tieneVariosCodigoQuipu;
	}

	public String getSegundoCodigoQuipu() {
		return segundoCodigoQuipu;
	}

	public void setSegundoCodigoQuipu(String segundoCodigoQuipu) {
		this.segundoCodigoQuipu = segundoCodigoQuipu;
	}

	public String getSegundoCodigoQuipuAux() {
		return segundoCodigoQuipuAux;
	}

	public void setSegundoCodigoQuipuAux(String segundoCodigoQuipuAux) {
		this.segundoCodigoQuipuAux = segundoCodigoQuipuAux;
	}

	public String getSedesProyecto() {
		return sedesProyecto;
	}

	public void setSedesProyecto(String sedesProyecto) {
		this.sedesProyecto = sedesProyecto;
	}

	public String getJustificacionBancoFinanciable() {
		return justificacionBancoFinanciable;
	}

	public void setJustificacionBancoFinanciable(String justificacionBancoFinanciable) {
		this.justificacionBancoFinanciable = justificacionBancoFinanciable;
	}

	public Long getMontoAdicion() {
		return montoAdicion;
	}

	public void setMontoAdicion(Long montoAdicion) {
		this.montoAdicion = montoAdicion;
	}

	public boolean getPermisoUnidadAdministrativa() {
		return ((InvestigadorInterno) personaActual).getDependencia().getId()
				.equals(((Investigador) investigadorPrincipal).getDependencia().getId());
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public SelectItem[] getCategoriaIngresoItems() {
		return categoriaIngresoItems;
	}

	public void setCategoriaIngresoItems(SelectItem[] catgoriaIngresoItems) {
		this.categoriaIngresoItems = catgoriaIngresoItems;
	}

	public boolean isTieneAvalRegaliasAprobado() {
		return tieneAvalRegaliasAprobado;
	}

	public void setTieneAvalRegaliasAprobado(boolean tieneAvalRegaliasAprobado) {
		this.tieneAvalRegaliasAprobado = tieneAvalRegaliasAprobado;
	}

	public ProyectoLegalizacionOcad getLegalizacionOcad() {
		return legalizacionOcad;
	}

	public void setLegalizacionOcad(ProyectoLegalizacionOcad legalizacionOcad) {
		this.legalizacionOcad = legalizacionOcad;
	}

	public void consultarPersona() {
		if (esCadenaVacia(legalizacionOcad.getDirectorProyecto().getId().getDocumento())) {
			mensajeError("Ingrese el documento del director para consultarlo");
		} else {
			try {
				List<Persona> personas = servicioGeneral.obtenerObjetos(Persona.class,
						"from Persona where id.documento = '"
								+ legalizacionOcad.getDirectorProyecto().getId().getDocumento() + "'");
				if (personas != null && personas.size() > 0) {
					legalizacionOcad.setDirectorProyecto(personas.get(0));

				}
			} catch (Exception e) {
				e.printStackTrace();
				mensajeError("No se encontró una persona con el documento ingresado.");
			}
		}
	}

	public SelectItem[] getEntidadItemsOcad() {
		return entidadItemsOcad;
	}

	public void setEntidadItemsOcad(SelectItem[] entidadItemsOcad) {
		this.entidadItemsOcad = entidadItemsOcad;
	}

	public Solicitud getSolicitudSeleccionadaIntegrantesProrroga() {
		return solicitudSeleccionadaIntegrantesProrroga;
	}

	public void setSolicitudSeleccionadaIntegrantesProrroga(Solicitud solicitudSeleccionadaIntegrantesProrroga) {
		this.solicitudSeleccionadaIntegrantesProrroga = solicitudSeleccionadaIntegrantesProrroga;
	}

	public long getContrapartidaPersonal() {
		Long monto = 0l;
		monto = servicioProyecto.obtenerMontoContrapartidaPersonal(proyectoActual.getId());
		return monto;
	}

	public boolean isEsCoejecutorCooperante() {
		return esCoejecutorCooperante;
	}

	public void setEsCoejecutorCooperante(boolean esCoejecutorCooperante) {
		this.esCoejecutorCooperante = esCoejecutorCooperante;
	}

	public boolean isTieneAvalConvocatoriaRegaliasAprobado() {
		return tieneAvalConvocatoriaRegaliasAprobado;
	}

	public void setTieneAvalConvocatoriaRegaliasAprobado(boolean tieneAvalConvocatoriaRegaliasAprobado) {
		this.tieneAvalConvocatoriaRegaliasAprobado = tieneAvalConvocatoriaRegaliasAprobado;
	}

	public List<ProyectoEvaluador> getListaProyectoSeleccion() {
		return listaProyectoSeleccion;
	}

	public void setListaProyectoSeleccion(List<ProyectoEvaluador> listaProyectoSeleccion) {
		this.listaProyectoSeleccion = listaProyectoSeleccion;
	}

	public void guardarCambiosProyectoCompromisos() {
		Date fechaFinalProyecto = this.fechaFinalProyecto;
		boolean valido = true;

		for (ProyectoCompromiso c : proyectoActual.getCompromisosProyecto()) {
			// Validar compromiso de informe final
			if (c.getTipoInforme() != null && TipoInforme.INFORME_FINAL.equals(c.getTipoInforme().getId())) {

				if (c.getFechaVencProrroga() != null) {

					if (c.getFechaVencProrroga().before(fechaFinalProyecto)) {
						mensajeError(
								"La fecha del compromiso de informe final no puede ser menor a la fecha final del proyecto.");
						valido = false;
						break;
					}
				}
			}
		}

		if (valido) {
			for (ProyectoCompromiso c : proyectoActual.getCompromisosProyecto()) {
				c.setProyecto(proyectoActual);
				servicioGeneral.guardarObjeto(c);
				System.out.println("Guardando compromiso ID: " + c.getId() + " con nueva fecha prórroga: "
						+ c.getFechaVencProrroga());
			}
			mensajeInfo("Compromisos guardados exitosamente.");
		}
	}

}