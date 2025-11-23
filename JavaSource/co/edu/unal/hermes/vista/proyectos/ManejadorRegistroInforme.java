package co.edu.unal.hermes.vista.proyectos;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.dao.DataIntegrityViolationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Actividad;
import co.edu.unal.hermes.modelo.ActividadEvento;
import co.edu.unal.hermes.modelo.ArchivoInforme;
import co.edu.unal.hermes.modelo.Bien;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dominio;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoInforme;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.IdDominioDetalle;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorAreaInteres;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PlanEstudios;
import co.edu.unal.hermes.modelo.ProductoSara;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.ProyectoInformeActividad;
import co.edu.unal.hermes.modelo.ProyectoInformeImpacto;
import co.edu.unal.hermes.modelo.ProyectoInformeObjetivoEspecifico;
import co.edu.unal.hermes.modelo.ProyectoInformeResultado;
import co.edu.unal.hermes.modelo.ProyectoProducto;
import co.edu.unal.hermes.modelo.ResultadoCompromiso;
import co.edu.unal.hermes.modelo.ResultadoProyecto;
import co.edu.unal.hermes.modelo.SolicitudInforme;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.VProductoSara;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.PersonaLaboratorio;
import co.edu.unal.hermes.modelo.seguimiento.AlertaProyecto;
import co.edu.unal.hermes.modelo.seguimiento.DetalleAdicionPresupuesto;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudAdicionPresupuestal;
import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;
import co.edu.unal.hermes.modelo.seguimiento.TipoViaSolicitud;
import co.edu.unal.hermes.utils.Fecha;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.evaluadores.ProyectoCoordinador;
import co.edu.unal.hermes.vista.proyectos.base.BaseManejadorRegistroInforme;
import co.edu.unal.hermes.vista.utils.Util;
import co.edu.unal.hermes.vista.utils.VistaBD;

public class ManejadorRegistroInforme extends BaseManejadorRegistroInforme {

	private static final long serialVersionUID = 3285910295596750240L;

	public static final String MANEJADOR_REGISTRO_INFORME_SESSION = "manejadorRegistroInforme";

	// Campos utilizados para formulario de solicitud de renovación
	private static final Integer VALOR_APOYO_TESIS = 1000000;

	private Integer valorApoyoTesis;
	private String valorMatricula = "";

	private List<VProductoSara> productosTmp;
	private boolean mostrarEquipos = false;
	private boolean mostrarBien = false;
	private Bien bienSeleccionado;
	private Estudiante estudianteSeleccionado;
	private boolean mostrarEstudiante = false;
	private ProductoSara productoSeleccionado;
	private ArchivoInforme archivoSeleccionado;
	private String calificacion;
	private String linkPublico;
	private String existeLinkPublico;

	private String actividad = "";
	private String tipoContrato = "";
	private String cantidad = "";
	private String valorUnitario = "";
	private long valorTotal = 0;
	private String descripcion = "";
	private String pasantia;

	private List listaActividades;
	private String mesesSostenimiento = "";
	private Integer valorSostenimiento = 0;
	private String mesesPasantia = "";
	private String valorPasantia = "";
	private Long proyectoRenovacion;

	private String observaciones = "";
	private long estadoInforme = 0;
	private boolean tienePasantia = false;
	private ProyectoInforme unProyectoInforme;

	private Integer anoActual = 0;
	private String apoyoTesis = "NO";
	private String semestreSolicitud = "";
	private String anoSolicitud = "";
	private List<VistaBD> pasantias;
	private VistaBD pasantiaSeleccionada;
	private boolean mostrarPasantia = false;
	private Document xmlPasantias;

	private Date fechaPago;
	private String valorPagado = "";
	private String nroResolucion = "";

	private Integer SMMLV;
	private Double mesesAprobados;

	private boolean esSolicitudRenovacion;
	private boolean esPryContrapartida = false;
	private boolean esJornadaDocente = false;
	private boolean esPermisoMarco = false;
	private boolean esPermisoMarcoAsignatura = false;
	private boolean esContratoBiodiversidad = false;
	CorreoPlantilla correoActual = new CorreoPlantilla();
	String cuerpoCorreo = "";
	Long idAlertaInforme = 0L;
	Long idSolicitud = 0L;
	boolean consultaRenovacionCoordindador = false;
	String pais;
	String ciudad;
	String institucion;
	String anio;
	String duracion;
	String tema;
	boolean esEstudianteLider = false;
	boolean mostrarEstudianteLider = false;
	Persona estudianteLiderInforme;
	private String tipoFormularioInforme;
	private String mensajeInicioConvocatoria;
	private boolean esFichaMinima;

	private String valorEjecucionPresupuestas;
	private String numeroTotalParticipantes;
	private String numeroTotalEstudiantes;
	private String numeroTotalExternos;

	private String restriccionModalidad;

	private boolean movilizacionEspecimenes;

	private String productoEsperadoId;

	private String objetivoId;

	private String nombreDocumentoRevisionFacultad;

	private UploadedFile archivoCargar;

	private boolean bloquearAprobacionInforme = false;
	private boolean validoPorcEjecTecnicaAcum = false;
	private boolean validoPorcEjecPresupuestoAcum = false;

	private SelectItem[] sinoitems = { new SelectItem(true, "SI"), new SelectItem(false, "NO") };
	private SelectItem[] tipoImpactoItems = { new SelectItem("Económico", "Económico"),
			new SelectItem("Científico", "Científico"), new SelectItem("Social", "Social"),
			new SelectItem("Académico", "Académico"), new SelectItem("Otro", "Otro") };
	private String tipoImpacto;
	private SelectItem[] medicionImpactoItems = { new SelectItem("Alto", "Alto"), new SelectItem("Medio", "Medio"),
			new SelectItem("Bajo", "Bajo") };
	private String medicionImpacto;
	private String detalleImpacto;
	private Long odsSeleccionado;

	private List<ProyectoInformeImpacto> listaImpacto;
	private List<ProyectoInformeImpacto> listaImpactoBorrados;
	private ProyectoInformeImpacto impactoSeleccionado;

	private ProductoTipo productoEsperadoSeleccionado;

	public ManejadorRegistroInforme() {
		super();

		// Se obtiene año actual
		Calendar fecha = new GregorianCalendar();
		anoActual = fecha.get(Calendar.YEAR);

		valorApoyoTesis = VALOR_APOYO_TESIS;
		pasantias = new ArrayList<VistaBD>();
		listaArchivos = new ArrayList<ArchivoInforme>();
		proyectoInformeActual = new ProyectoInforme();
		conclusiones = "";
		fechaEntrega = new Date();
		listaCompromisos = new ArrayList<ResultadoCompromiso>();
		listaEstudiantes = new ArrayList<Estudiante>();

		listaProductosSara = new ArrayList<ProductoSara>();
		listaProdEliminar = new ArrayList<Long>();

		panelRenderError = new boolean[20];
		errores = new String[21];
		ProyectoInforme pryInf = new ProyectoInforme();
		movilizacionEspecimenes = true;

		cargarListas();

		try {

			if (sesion.getAttribute("esPryContrapartida") != null) {
				esPryContrapartida = (Boolean) sesion.getAttribute("esPryContrapartida");
			}

			if (sesion.getAttribute("esPermisoMarco") != null) {
				esPermisoMarco = (Boolean) sesion.getAttribute("esPermisoMarco");
			}

			if (sesion.getAttribute("esPermisoMarcoAsignatura") != null) {
				esPermisoMarcoAsignatura = (Boolean) sesion.getAttribute("esPermisoMarcoAsignatura");
			}

			if (sesion.getAttribute("esContratoBiodiversidad") != null) {
				esContratoBiodiversidad = (Boolean) sesion.getAttribute("esContratoBiodiversidad");
			}

			if (sesion.getAttribute("esJornadaDocente") != null) {
				try {
					setEsJornadaDocente((Boolean) sesion.getAttribute("esJornadaDocente"));
				} catch (Exception c) {
					setEsJornadaDocente(false);
					c.printStackTrace();
				}
			}

			if (sesion.getAttribute("solicitudRenovacion") != null) {
				esSolicitudRenovacion = (Boolean) sesion.getAttribute("solicitudRenovacion");
			}
			if (sesion.getAttribute("consultaInformeCoordinador") != null) {
				consultaRenovacionCoordindador = (Boolean) sesion.getAttribute("consultaInformeCoordinador");
			}
			if (sesion.getAttribute("proyectoRenovacion") != null) {
				proyectoRenovacion = (Long) sesion.getAttribute("proyectoRenovacion");
				esSolicitudRenovacion = true;
			}
			if (sesion.getAttribute("idProyecto") != null) {
				if (super.sesion.getAttribute("idProyecto") instanceof Long) {
					idProyecto = (Long) super.sesion.getAttribute("idProyecto");
				} else {
					idProyecto = Long.parseLong((String) super.sesion.getAttribute("idProyecto"));
				}
				if (idProyecto.equals("20898")) {
					esSolicitudRenovacion = true;
				}
			}
			if (consultaRenovacionCoordindador) {

				Long alertaId = 0L;
				Long solicitudId = 0L;

				if (sesion.getAttribute("consultaRenovacionCoordindador") != null
						&& (Boolean) sesion.getAttribute("consultaRenovacionCoordindador")) {
					alertaId = (Long) sesion.getAttribute("idAlertaRenovacion_");
					solicitudId = (Long) sesion.getAttribute("idSolicitudRenovacion_");
					idAlertaInforme = alertaId;
					idSolicitud = solicitudId;
					List listaSolicitudInforme = new ArrayList();
					listaSolicitudInforme = servicioGeneral
							.obtenerListaObjetos("SolicitudInforme  where solicitudID ='" + idSolicitud + "'");

					idInforme = ((SolicitudInforme) listaSolicitudInforme.get(0)).getInformeID();
				} else {
					idInforme = (Long) sesion.getAttribute("idInforme");

					List listaSolicitudInforme = new ArrayList();
					listaSolicitudInforme = servicioGeneral
							.obtenerListaObjetos("SolicitudInforme  where informeID ='" + idInforme + "'");
					if (!esListaVacia(listaSolicitudInforme)) {
						idSolicitud = ((SolicitudInforme) listaSolicitudInforme.get(0)).getSolicitudID();

						List ListaAlertas = new ArrayList();
						ListaAlertas = servicioGeneral.obtenerObjetosLimitado(AlertaProyecto.class,
								"select #id alert.id from AlertaProyecto alert where alert.solicitud.id='" + idSolicitud
										+ "'");
						idAlertaInforme = (Long) ((AlertaProyecto) ListaAlertas.get(0)).getId();
					}
					consultaRenovacionCoordindador = false;
				}
				List listaSolicitudAux;
				listaSolicitudAux = new ArrayList();

				listaSolicitudAux = servicioGeneral.obtenerObjetosLimitado(ProyectoInforme.class,
						"Select #id proyInf.id, #numeroActoAdministrativoFacultad proyInf.numeroActoAdministrativoFacultad, #pryTieneSaldoEjecucion proyInf.pryTieneSaldoEjecucion from ProyectoInforme proyInf  where proyInf.id ='"
								+ idInforme + "'");
				pryInf = (ProyectoInforme) listaSolicitudAux.get(0);
				sesion.removeAttribute("consultaRenovacionCoordindador");
			}

		} catch (Exception e) {
			e.printStackTrace();
			esSolicitudRenovacion = false;
		}
		Persona unaPersona = (Persona) sesion.getAttribute("persona");

		if (esSolicitudRenovacion) {
			idProyecto = proyectoRenovacion;

			if (!consultaRenovacionCoordindador) {
				SMMLV = Integer
						.parseInt(servicioGeneral
								.obtenerObjetosLimitado(Parametro.class,
										"Select #valor par.valor from Parametro par where par.id=43")
								.get(0).getValor());
				String sql2 = "select #horasJornadaDocente inv.horasJornadaDocente" + " from InvestigadorProyecto inv"
						+ " where inv.investigador.id.documento = '" + unaPersona.getId().getDocumento() + "' "
						+ "and inv.investigador.id.tipoDocumento = '" + unaPersona.getId().getTipoDocumento() + "'"
						+ "and inv.proyecto.id = '" + PROYECTO_SOLICITUD_RENOVACION + "'";
				mesesAprobados = servicioGeneral.obtenerObjetosLimitado(InvestigadorProyecto.class, sql2).get(0)
						.getHorasJornadaDocente().doubleValue();
				mesesAprobados = mesesAprobados / 10;
			}
		}
		if (idProyecto != null) {
			infoProyecto(idProyecto);
		}
		IdPersona idPersonaActual = unaPersona.getId();
		List listaInvestigadorActualAux;
		listaInvestigadorActualAux = servicioGeneral.obtenerObjetosLimitado(Investigador.class,
				"select #id inv.id from Investigador inv" + " where inv.id.documento='" + idPersonaActual.getDocumento()
						+ "' and" + " inv.id.tipoDocumento='" + idPersonaActual.getTipoDocumento() + "'");

		investigadorActual = (Investigador) listaInvestigadorActualAux.get(0);

		if (investigadorActual != null) {
			try {
				InvestigadorInterno investigadorInterno = servicioPersona
						.obtenerInvestigadorInterno(investigadorActual.getId());
				if (investigadorInterno != null && investigadorActual.getDependencia() != null) {
					proyectoInformeActual.setDependenciaInforme(investigadorInterno.getDependencia().getId());
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}

		if (pryInf != null && pryInf.getId() != null) {
			idInforme = pryInf.getId();
		} else {
			idInforme = (Long) sesion.getAttribute("idInforme");
		}
		tipoAccion = (Long) super.sesion.getAttribute("tipoAccion");

		if (tipoAccion == ProyectoInforme.TIPO_ACCION_CONSULTA) {
			editable = false;
		} else {
			editable = true;
		}
		tipoInformeAccion = (Long) super.sesion.getAttribute("tipoInformeParam");

		if (tipoInformeAccion != null) {
			if (tipoInformeAccion == 2) {
				mostrarConclusiones = true;
			} else {
				mostrarConclusiones = false;
			}

			if (tipoInformeAccion == 1) {
				informeAvance = true;
				nombre1 = "Avance de actividades: ";
				nombre2 = "Avance de los resultados o compromisos: ";
				nombre3 = "Dificultades: ";
			} else {
				informeAvance = false;
				nombre1 = "Sinopsis divulgativa:";
				nombre2 = "Resumen técnico: ";
				nombre3 = "Impacto (Económico, Científico, Social, Académico):";
			}
		}
		if (idInforme != null && idInforme > 0 && !esSolicitudRenovacion) {

			if (tipoInformeAccion == 1) {
				informeAvance = true;
				nombre1 = "Avance de actividades: ";
				nombre2 = "Avance de los resultados o compromisos: ";
				nombre3 = "Dificultades: ";
			} else {
				informeAvance = false;
				nombre1 = "Sinopsis divulgativa:";
				nombre2 = "Resumen técnico: ";
				nombre3 = "Impacto (Económico, Científico, Social, Académico):";
			}

			cargarInforme();

			sesion.removeAttribute("idInforme");
			sesion.removeAttribute("tipoAccion");
		} else {
			if (idInforme == null)
				idInforme = new Long("0");
			if (esSolicitudRenovacion) {
				cargarInforme();
			}
		}

		cargarListasProyecto();

		verificarInvestigadorLider(idProyecto);
		if (!esSolicitudRenovacion) {
			cargarProductos();
			cargarProductosEsperados();
			crearListaItemsProductosEsperados();
			cargarFormacionEsperada();
			cargarObjetivos();
		}
		cargarTiposDocumento();
		cargarEstudianteInforme(idInforme, proyectoInformeActual);

		listaOpciones = new ArrayList<SelectItem>();
		listaOpciones.add(new SelectItem("SI", "Si"));
		listaOpciones.add(new SelectItem("NO", "No"));

		listaPorcentajesEjecucion = new ArrayList<SelectItem>();
		for (int i = 0; i <= 100; i++) {
			listaPorcentajesEjecucion.add(new SelectItem(i, i + " %"));
			if (listaPorcentajesEjecucion == null || listaPorcentajesEjecucion.equals("")) {
				porcentajeEjecucion = "0";
				porcentajeEjecucionPresupuestal = "0";
				porcentajeEjecucionPresupuestalExt = "0";
			}
		}

		listaResultadosItem = new Vector<SelectItem>();
		listaResultadosItem.add(new SelectItem("0", " "));

		productosSaraItems = new SelectItem[1];
		productosSaraItems[0] = new SelectItem("0", "No hay producto asociado");

		esFichaMinima = false;
		Convocatoria conv = (Convocatoria) proyecto.getModalidad();
		if (conv != null) {
			if (conv.getId() != null && conv.getId().equals(MODALIDAD_FICHA_MINIMA_ID)
					|| conv.getId() != null && conv.getId().equals(MODALIDAD_CONVOCATORIA_EXTERNA_ID)) {
				esFichaMinima = true;
			}
			try {
				mensajeInicioConvocatoria = conv.getMensajeFormularioInforme();
			} catch (NullPointerException npe) {
				npe.printStackTrace();
				mensajeInicioConvocatoria = "";
			}
		}
		try {
			tipoFormularioInforme = conv.getPadre().getTipoFormularioInformes();
			if (tipoFormularioInforme == null || (tipoFormularioInforme != null && tipoFormularioInforme.equals(""))) {
				tipoFormularioInforme = ConvocatoriaPadre.TIPO_FORMULARIO_COMPLETO;
			}
		} catch (ClassCastException cce) {
			cce.printStackTrace();
		}
		
		System.out.println("!editable: " + !editable);
		System.out.println("!esNulo(unProyectoInforme): " + !esNulo(unProyectoInforme));
		System.out.println("!editable && !esNulo(unProyectoInforme): " + (!editable && !esNulo(unProyectoInforme)));
		
		if (!editable && !esNulo(unProyectoInforme)) {
			if (unProyectoInforme.getEstadoInforme().getId().equals(EstadoInforme.ACEPTADO_UAB)
					|| unProyectoInforme.getEstadoInforme().getId().equals(EstadoInforme.ACEPTADO_DIRECCION)
					|| unProyectoInforme.getEstadoInforme().getId().equals(EstadoInforme.ACEPTADO_FACULTAD)) {
				bloquearAprobacionInforme = false;
			} else {
				if (esPermisoMarco || esPermisoMarcoAsignatura || esJornadaDocente || esContratoBiodiversidad) {
					bloquearAprobacionInforme = false;
				} else {
					if (proyecto != null && proyecto.getCodigoQuipu() != null && !proyecto.getCodigoQuipu().isEmpty()) {
						bloquearAprobacionInforme = false;
					} else {
						bloquearAprobacionInforme = true;
					}
				}
			}
		}
		
		if (!proyecto.isConvUDEC() && !proyecto.isConvSUE() && !esPermisoMarco && !esPermisoMarcoAsignatura) {
			if (tipoInformeAccion == 2 && unProyectoInforme == null) {
				setMostrarMontoEjecExt(true);
			}
			if (unProyectoInforme != null && unProyectoInforme.getMostartMontoEjecutadoExt().equals("Y")) {
				setMostrarMontoEjecExt(true);
			}
		}
		proyectoInformeActual.setProductosEnSara("NO");
	}

	/**
	 * Se carga restriccion de convocatoria para ser usada en la vista Esta
	 * información se carga en variable global para que pueda ser accedida desde el
	 * manejador.
	 */
	private void cargarRestriccionModalidad() {
		if (proyecto != null) {
			Convocatoria convocatoria = (Convocatoria) proyecto.getModalidad();
			if (convocatoria != null && convocatoria.getRestriccion() != null) {
				restriccionModalidad = convocatoria.getRestriccion().getId();
			}
		}
	}

	public void verificarInvestigadorLider(long pr) {
		Investigador investProyecto = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(pr);
		Convocatoria conv = (Convocatoria) proyecto.getModalidad();
		String habilitarInformesEstudiante = conv.getHabilitarModEstudiantes();
		String habilitarInformesTutor = conv.getHabilitarModTutor();
		
		if (!investigadorActual.getId().getDocumento().equals(investProyecto.getId().getDocumento())) {
			try {
				if (habilitarInformesEstudiante != null && !habilitarInformesEstudiante.equals("")
						|| habilitarInformesTutor != null && !habilitarInformesTutor.equals("")) {
					Persona persona = (Persona) sesion.getAttribute("persona");
					List ip = servicioGeneral.obtenerListaObjetosWhere("InvestigadorProyecto ip",
							"where ip.investigador.id.documento = '" + persona.getId().getDocumento() + "'"
									+ " and ip.investigador.id.tipoDocumento = '" + persona.getId().getTipoDocumento()
									+ "' and ip.proyecto.id = '" + idProyecto + "'");

					if (ip != null && ip.size() > 0) {
						String tipoInvestigadorActual = ((InvestigadorProyecto) ip.get(0)).getTipo().getId();
						if (habilitarInformesEstudiante != null && habilitarInformesEstudiante.length() > 0
								&& habilitarInformesEstudiante.indexOf("-" + tipoInvestigadorActual + "-") != -1) {
							esEstudianteLider = true;
							if(habilitarInformesEstudiante.indexOf("-JIEU-") != -1 || habilitarInformesEstudiante.indexOf("-JIPO-") != -1) {
								esConvocatoriaJovInvestigadores  = true;
							}
						} else {
							editable = false;
						}
						if (habilitarInformesTutor != null && habilitarInformesTutor.length() > 0
								&& habilitarInformesTutor.indexOf("-" + tipoInvestigadorActual + "-") != -1) {
							editable = true && editable;
							if(habilitarInformesTutor.indexOf("-JIEU-") != -1 || habilitarInformesTutor.indexOf("-JIPO-") != -1) {
								esConvocatoriaJovInvestigadores  = true;
							}
						} else {
							if (!esEstudianteLider) {
								editable = false;
							}
						}
					} else {
						editable = false;
					}
				} else {
					if (!esSolicitudRenovacion)
						editable = false;
				}
			} catch (NullPointerException npe) {
				npe.printStackTrace();
				editable = false;
			}
		} else {
			editable = true && editable;
		}
	}

	public ProyectoInforme cargarEstudianteInforme(Long idProyectoInforme, ProyectoInforme pi) {
		if (pi == null)
			pi = new ProyectoInforme();
		List listaSolicitudAux = servicioGeneral
				.obtenerListaObjetos("ProyectoInforme  where id ='" + idProyectoInforme + "'");
		if (listaSolicitudAux != null && listaSolicitudAux.size() > 0) {
			pi = (ProyectoInforme) listaSolicitudAux.get(0);
		}
		if ((esEstudianteLider && pi != null && pi.getIdEstudiante() == null && pi.getTipoDocEstudiante() == null)
				|| (esEstudianteLider && pi == null)) {
			Persona persona = (Persona) sesion.getAttribute("persona");
			pi.setIdEstudiante(persona.getId().getDocumento());
			pi.setTipoDocEstudiante(persona.getId().getTipoDocumento());
			estudianteLiderInforme = persona;
			mostrarEstudianteLider = true;
		} else if (pi != null && pi.getIdEstudiante() != null && !pi.getIdEstudiante().equals("")
				&& pi.getTipoDocEstudiante() != null) {
			List lista = servicioGeneral.obtenerListaObjetosWhere("Persona p", " where p.id.documento = '"
					+ pi.getIdEstudiante() + "' and p.id.tipoDocumento = '" + pi.getTipoDocEstudiante() + "'");
			if (lista != null && lista.size() > 0) {
				estudianteLiderInforme = (Persona) lista.get(0);
			}
			mostrarEstudianteLider = true;
		}
		return pi;
	}

	private void cargarProductos() {
		listaProductosNivel1 = new ArrayList();
		listaProductosNivel2 = new ArrayList();
		listaProductosNivel3 = new ArrayList();

		listaProductosNivel1 = new ArrayList<ProductoTipo>();
		List<ProductoTipo> productosConvocatoria = null;

		String hql = "select p from ProductoTipo p where p.estado = 'A' and p.descripcion like 'LISTA_PRODUCTO_UNIFICADA_HERMES' and p.nivel = '0' order by p.nombre";
		productosConvocatoria = servicioGeneral.obtenerObjetos(ProductoTipo.class, hql);

		if (!esListaVacia(productosConvocatoria)) {
			productoNivel1 = productosConvocatoria.get(0).getId();
			cambiarProductoNivel1();
			for (Iterator<ProductoTipo> it = productosConvocatoria.iterator(); it.hasNext();) {
				ProductoTipo pt = it.next();
				if ("0".equals(pt.getNivel())) {
					listaProductosNivel1.add(pt);
				}
			}
		}

		Collections.sort(listaProductosNivel1, new Comparator<ProductoTipo>() {

			@Override
			public int compare(ProductoTipo o1, ProductoTipo o2) {
				ProductoTipo e1 = (ProductoTipo) o1;
				ProductoTipo e2 = (ProductoTipo) o2;
				return e1.getNombre().compareTo(e2.getNombre());
			}
		});

		try {
			if (tipoInformeAccion != 1) {
				productoNivel1Item = crearSelectItem(listaProductosNivel1);
				productoNivel1 = ((ProductoTipo) listaProductosNivel1.get(0)).getId();
				productoNivel1Actual = (ProductoTipo) listaProductosNivel1.get(0);
				// valores para el segundo nivel
				ProductoTipo pro2 = new ProductoTipo();
				pro2.setId(productoNivel1);
				List listaProductosNivel2Aux = servicioGeneral.obtenerHijos(pro2);
				if (productosConvocatoria != null && productosConvocatoria.size() != 0
						&& listaProductosNivel2Aux != null) {
					for (Iterator it = productosConvocatoria.iterator(); it.hasNext();) {
						ProductoTipo pt = (ProductoTipo) it.next();
						for (Iterator ite = listaProductosNivel2Aux.iterator(); ite.hasNext();) {
							ProductoTipo hijo = (ProductoTipo) ite.next();
							if (pt.getId().equals(hijo.getId())) {
								listaProductosNivel2.add(hijo);
							}
						}
					}
				}
				productoNivel2Item = crearSelectItem(listaProductosNivel2);
				if (listaProductosNivel2.size() != 0) {
					productoNivel2 = ((ProductoTipo) listaProductosNivel2.get(0)).getId();
					productoNivel2Actual = (ProductoTipo) listaProductosNivel2.get(0);
					ProductoTipo pro3 = new ProductoTipo();
					pro3.setId(productoNivel2);
					List listaProductosNivel3Aux = servicioGeneral.obtenerHijos(pro3);
					if (productosConvocatoria.size() != 0 && listaProductosNivel3Aux != null) {
						for (Iterator it = productosConvocatoria.iterator(); it.hasNext();) {
							ProductoTipo pt = (ProductoTipo) it.next();
							for (Iterator ite = listaProductosNivel3Aux.iterator(); ite.hasNext();) {
								ProductoTipo hijo = (ProductoTipo) ite.next();
								if (pt.getId().equals(hijo.getId())) {
									listaProductosNivel3.add(hijo);
								}
							}
						}
					}

					productoNivel3Item = crearSelectItem(listaProductosNivel3);
					if (listaProductosNivel3.size() != 0) {
						productoNivel3 = ((ProductoTipo) listaProductosNivel3.get(0)).getId();
						productoNivel3Actual = (ProductoTipo) listaProductosNivel3.get(0);
					}
				} else {
					listaProductosNivel3 = new ArrayList();
					productoNivel3Item = crearSelectItem(listaProductosNivel3);
				}

			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	private ProductoTipo buscarProductoTipoNivel3(String id) {
		ProductoTipo prT = new ProductoTipo();
		int i = 0;
		while (i < this.listaProductosNivel3.size()) {
			prT = (ProductoTipo) this.listaProductosNivel3.get(i);
			if (id.equals(prT.getId())) {
				break;
			}
			i = i + 1;
		}
		return prT;
	}

	public void consultarProductoSeleccionado() {
		productoEsperadoSeleccionado = new ProductoTipo();
		ProyectoProducto pp = buscarIdProductoRestantes(productoEsperadoId);
		productoEsperadoSeleccionado = pp.getProducto();
	}

	public void cambiarProductoNivel1() {

		// valores para el segundo nivel
		ProductoTipo pro2 = new ProductoTipo();
		pro2.setId(productoNivel1);
		listaProductosNivel2 = new ArrayList();
		String hql = "select p from ProductoTipo p where p.estado = 'A' and p.descripcion like 'LISTA_PRODUCTO_UNIFICADA_HERMES' and p.nivel = '1' and p.padre.id = '"
				+ productoNivel1 + "' order by p.nombre";
		productosConvocatoria = servicioGeneral.obtenerObjetos(ProductoTipo.class, hql);

		if (!esListaVacia(productosConvocatoria)) {

			for (Iterator<ProductoTipo> it = productosConvocatoria.iterator(); it.hasNext();) {
				ProductoTipo pt = it.next();
				if ("1".equals(pt.getNivel())) {
					listaProductosNivel2.add(pt);
				}
			}
		}

		Collections.sort(listaProductosNivel2, new Comparator<ProductoTipo>() {

			@Override
			public int compare(ProductoTipo o1, ProductoTipo o2) {
				ProductoTipo e1 = (ProductoTipo) o1;
				ProductoTipo e2 = (ProductoTipo) o2;
				return e1.getNombre().compareTo(e2.getNombre());
			}
		});

		productoNivel2Item = crearSelectItem(listaProductosNivel2);
		productoNivel2 = ((ProductoTipo) listaProductosNivel2.get(0)).getId();
		productoNivel2Actual = (ProductoTipo) listaProductosNivel2.get(0);
		cambiarProductoNivel2();

		// valores para el ultimo nivel
		ProductoTipo pro3 = new ProductoTipo();
		pro3.setId(productoNivel2);
		listaProductosNivel3 = new ArrayList();
		List listaProductosNivel3Aux = servicioGeneral.obtenerHijos(pro3);
		if (productosConvocatoria.size() != 0 && listaProductosNivel3Aux != null) {
			for (Iterator it = productosConvocatoria.iterator(); it.hasNext();) {
				ProductoTipo pt = (ProductoTipo) it.next();
				for (Iterator ite = listaProductosNivel3Aux.iterator(); ite.hasNext();) {
					ProductoTipo hijo = (ProductoTipo) ite.next();
					if (pt.getId().equals(hijo.getId())) {
						listaProductosNivel3.add(hijo);
					}
				}
			}
		}
		productoNivel3Item = crearSelectItem(listaProductosNivel3);
		if (productoNivel3Item.length != 0) {
			productoNivel3 = ((ProductoTipo) listaProductosNivel3.get(0)).getId();
			productoNivel3Actual = (ProductoTipo) listaProductosNivel3.get(0);
		}

	}

	public void cambiarProductoNivel2() {
		ProductoTipo pro3 = new ProductoTipo();
		pro3.setId(productoNivel2);
		listaProductosNivel3 = new ArrayList();
		String hql = "select p from ProductoTipo p where p.estado = 'A' and p.descripcion like 'LISTA_PRODUCTO_UNIFICADA_HERMES' and p.nivel = '2' and p.padre.id = '"
				+ productoNivel2 + "' order by p.nombre";
		productosConvocatoria = servicioGeneral.obtenerObjetos(ProductoTipo.class, hql);

		if (!esListaVacia(productosConvocatoria)) {
			for (Iterator<ProductoTipo> it = productosConvocatoria.iterator(); it.hasNext();) {
				ProductoTipo pt = it.next();
				if ("2".equals(pt.getNivel())) {
					listaProductosNivel3.add(pt);
				}
			}
		}

		Collections.sort(listaProductosNivel3, new Comparator<ProductoTipo>() {

			@Override
			public int compare(ProductoTipo o1, ProductoTipo o2) {
				ProductoTipo e1 = (ProductoTipo) o1;
				ProductoTipo e2 = (ProductoTipo) o2;
				return e1.getNombre().compareTo(e2.getNombre());
			}
		});

		productoNivel3Item = crearSelectItem(listaProductosNivel3);

		if (productoNivel3Item.length != 0) {
			productoNivel3 = ((ProductoTipo) listaProductosNivel3.get(0)).getId();
			productoNivel3Actual = (ProductoTipo) listaProductosNivel3.get(0);
		}
	}

	public void cambiarProductoNivel3(ValueChangeEvent event) {
		productoNivel3Actual = buscarProductoTipoNivel3((String) event.getNewValue());
	}

	private SelectItem[] crearSelectItem(List lista) {
		SelectItem[] elementos;
		elementos = new SelectItem[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			ProductoTipo pro = (ProductoTipo) lista.get(i);
			String nombre = pro.getNombre();

			elementos[i] = new SelectItem(pro.getId(), nombre);
		}
		return elementos;
	}

	private void cargarTiposDocumento() {
		List listaTipoDocumento = servicioGeneral.obtenerListaObjetos("TipoDocumento");
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
		tipoDocumento = (TipoDocumento) listaTipoDocumento.get(0);
	}

	public void cargarFormacionEsperada() {
		listaFormacionEsperada = servicioProyecto.getFormacionPorProyecto(this.proyecto.getId());
	}

	public void cargarObjetivos() {
		String hql = "select o from ObjetivoEspecifico o where o.proyecto.id = '" + this.proyecto.getId() + "'";
		listaObjetivosBaseDatos = servicioGeneral.obtenerObjetos(hql);

		if (listaObjetivosBaseDatos.size() > 0) {
			listaObjetivos = new SelectItem[listaObjetivosBaseDatos.size()];

			for (int i = 0; i < listaObjetivosBaseDatos.size(); i++) {
				ObjetivoEspecifico objetivo = new ObjetivoEspecifico();
				objetivo = (ObjetivoEspecifico) listaObjetivosBaseDatos.get(i);

				listaObjetivos[i] = new SelectItem(objetivo.getId(), objetivo.getNombre());
			}
			objetivo = listaObjetivosBaseDatos.get(0);
		} else {

			String sql2 = "select o from ObjetivoEspecifico o where o.id = 0";
			List lista2 = servicioGeneral.obtenerObjetos(sql2);
			objetivo = (ObjetivoEspecifico) lista2.get(0);

			listaObjetivos = new SelectItem[1];
			listaObjetivos[0] = new SelectItem(objetivo.getId(), objetivo.getNombre());

		}

	}

	public void asignarObjetivo(ValueChangeEvent event) {
		String obj = event.getNewValue().toString();

		String sql = "select o from ObjetivoEspecifico o where o.id = " + obj + " ";
		List lista = servicioGeneral.obtenerObjetos(sql);
		if (lista.size() > 0) {
			objetivo = (ObjetivoEspecifico) lista.get(0);
		} else {
			String sql2 = "select o from ObjetivoEspecifico o where o.id = 0";
			List lista2 = servicioGeneral.obtenerObjetos(sql2);
			objetivo = (ObjetivoEspecifico) lista2.get(0);
		}

	}

	public void mostrarPanelEquipos() {
		String compraEq = proyectoInformeActual.getCompraEquipos();
		if (compraEq.equals("SI")) {
			mostrarEquipos = true;
			mostrarBien = false;
		} else {
			mostrarEquipos = false;
			mostrarBien = false;
		}
	}

	public void buscarEstudiante() {
		try {
			List<Estudiante> estudiantes = servicioGeneral
					.obtenerObjetos("select e from Estudiante e where e.id.documento = '" + docEstudiante
							+ "' and e.id.tipoDocumento= '" + tipoDocumento.getId() + "'");

			if (estudiantes.size() > 0) {
				estudiante = estudiantes.get(0);

				errores[2] = "";
				panelRenderError[2] = false;
				setMostrarEstudiante(true);
			} else {
				errores[2] = "No se encontró ningún estudiante";
				panelRenderError[2] = true;
				setMostrarEstudiante(false);
			}

		} catch (Exception e) {
			errores[2] = "No se encontró ningún estudiante";
			panelRenderError[2] = true;
			setMostrarEstudiante(false);
			e.printStackTrace();
		}

	}

	public void agregarEstudiante() {
		if (estudiante != null) {
			listaEstudiantes.add(estudiante);
			docEstudiante = "";
			setMostrarEstudiante(false);
		}
	}

	public void eliminarEstudiante() {
		listaEstudiantes.remove(estudianteSeleccionado);

	}

	public void actualizarInformacionObjetivo() {
		if (!esCadenaVacia(objetivoId)) {
			if (!esListaVacia(listaObjetivosBaseDatos)) {
				Iterator<ObjetivoEspecifico> i = listaObjetivosBaseDatos.iterator();
				while (i.hasNext()) {
					ObjetivoEspecifico objetivoEspecifico = i.next();
					if (objetivoEspecifico.getId().toString().equals(objetivoId)) {
						objetivo = objetivoEspecifico;
					}
				}
			}
		}
	}

	public void agregarProductoNuevo() {

		ProductoSara productoSa = new ProductoSara();
		boolean bandera = true;

		if (this.productoSara != null) {
			productoSa = productoSara;
		}

		actualizarInformacionObjetivo();

		try {
			// OBJETIVOSs
			if (objetivo != null) {
				productoSa.setObjetivo(objetivo.getNombre());
			} else {
				productoSa.setObjetivo("Sin objetivo específico");
			}

			// RESULTADOS ESPERADOS
			if (productoEsperadoId.equals("0")) {
				productoSa.setProductoResultadoEsperado("Resultado adicional");
			} else {
				String sqlRes = "select o from ProyectoProducto o where o.id = '" + productoEsperadoId + "' ";
				List lista = servicioGeneral.obtenerObjetos(sqlRes);
				ProyectoProducto resultEsp = (ProyectoProducto) lista.get(0);
				productoSa.setProductoResultadoEsperado(resultEsp.getProducto().getNombre());
			}

			// RESULTADOS OBTENIDOS
			if (resultadoEsp.getCompletado().equals("SI")) {
				productoSa.setProductoCompletado("SI");
				if (proyectoInformeActual.getProductosEnSara().equals("SI")) {
					if (productoLlave != null && !productoLlave.equals(" ") && !productoLlave.equals("")) {
						if (productoLlave.equals("0")) {
							String nombreProd = "No hay producto asociado";
							productoSa.setNombreProducto(nombreProd);
						}
					} else {
						bandera = false;
					}
				} else {
					List<ProductoTipo> lista = servicioGeneral.obtenerObjetoXID("ProductoTipo", productoNivel3);

					if (esCadenaVacia(nombreProductoDos) || nombreProductoDos == null || nombreProductoDos.equals("")
							|| nombreProductoDos.equals(" ") || nombreProductoDos.length() <= 0) {
						errores[3] = "Por favor ingrese el nombre del producto";
						panelRenderError[3] = true;
						bandera = false;
					} else {
						productoSa.setNombreProducto(nombreProductoDos);

						if (lista != null) {
							ProductoTipo producto = (ProductoTipo) lista.get(0);
							productoSa.setTipoProduccion(producto.getNombre());
						}
						String invId = ((Persona) sesion.getAttribute("persona")).getId().getDocumento();
						productoSa.setIdInvestigador(Integer.parseInt(invId));

						errores[3] = "";
						panelRenderError[3] = false;
					}

					// set link
					if (!esCadenaVacia(existeLinkPublico)) {
						panelRenderError[3] = false;
						if (existeLinkPublico.equals("SI") && esCadenaVacia(linkPublico)) {
							errores[3] = "Ingrese el Link donde el producto puede ser consultado";
							bandera = false;
							panelRenderError[3] = true;
							return;
						} else {
							productoSa.setLinkPublico(linkPublico);
							panelRenderError[3] = false;

						}
					} else {
						errores[3] = "Indique si existe un Link público donde el producto pueda ser consultado.";
						bandera = false;
						panelRenderError[3] = true;
						return;
					}

					// set fecha entrega
					if (fechaEntregaProd != null) {
						productoSa.setFechaEntrega(fechaEntregaProd);
						panelRenderError[3] = false;
					} else {
						errores[3] = "Ingrese la fecha de entrega del producto.";
						panelRenderError[3] = true;
						bandera = false;
						return;
					}

					// set proteccion
					if (!esCadenaVacia(susceptibleProteccionProd)) {
						productoSa.setSusceptibleProteccion(susceptibleProteccionProd);
						panelRenderError[3] = false;
					} else {
						errores[3] = "Seleccione si el producto es susceptible de protección.";
						panelRenderError[3] = true;
						bandera = false;
						return;
					}

					// set lugar
					if (!esCadenaVacia(lugarDepositoProd)) {
						productoSa.setLugarDeposito(lugarDepositoProd);
						panelRenderError[3] = false;
					} else {
						errores[3] = "Ingrese el lugar en el cual se encuentra depositado el producto.";
						panelRenderError[3] = true;
						bandera = false;
						return;
					}

					// Área y subarea de la ciencia principal.
					if (!esCadenaVacia(subAreaCienciaProd)) {
						productoSa.setAreaOCDEppal(subAreaCienciaProd);
						panelRenderError[3] = false;
						try {
							String hqlOcde = "select dd from DominioDetalle dd where dd.identificador.id = '"
									+ Dominio.DOMINIO_AREA_CIENCIA + "' and dd.identificador.tipo = '"
									+ subAreaCienciaProd.toString() + "'";
							List ocde = servicioGeneral.obtenerObjetos(DominioDetalle.class, hqlOcde);

							if (!esListaVacia(ocde)) {
								DominioDetalle dd2 = (DominioDetalle) ocde.get(0);
								productoSa.setNombreOcde(dd2.getDescripcion());
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}

					} else {
						productoSa.setAreaOCDEppal(null);
						errores[3] = "Seleccione una área de la ciencia asociada al producto.";
						panelRenderError[3] = true;
						bandera = false;
						return;
					}

					// Objetivo de desarrollo sostenible principal
					if (!esCadenaVacia(objetivoDesarrolloSosteniblePrimarioProd)
							&& !"0".equals(objetivoDesarrolloSosteniblePrimarioProd)) {
						productoSa.setObjetivoDesarrolloSost(objetivoDesarrolloSosteniblePrimarioProd);
						panelRenderError[3] = false;
						try {
							String hql = "select dd from DominioDetalle dd where dd.identificador.id = '"
									+ Dominio.DOMINIO_ODS + "' and dd.identificador.tipo = '"
									+ objetivoDesarrolloSosteniblePrimarioProd.toString() + "'";
							List ods = servicioGeneral.obtenerObjetos(DominioDetalle.class, hql);

							if (!esListaVacia(ods)) {
								DominioDetalle dd = (DominioDetalle) ods.get(0);
								productoSa.setNombreOds(dd.getDescripcion());
							}

						} catch (Exception e) {
							// TODO: handle exception
						}
					} else {
						productoSa.setObjetivoDesarrolloSost(null);
						errores[3] = "Seleccione un objetivo de desarrollo sostenible asociado al producto.";
						panelRenderError[3] = true;
						bandera = false;
						return;
					}
					nombreProductoDos = "";
					lugarDepositoProd = "";
					fechaEntregaProd = null;
				}
			} else {
				productoSa.setProductoCompletado("NO");
				if (StringUtils.isNotEmpty(resultadoEsp.getMotivoNoEntrega())) {
					productoSa.setMotivoNoEntrega(resultadoEsp.getMotivoNoEntrega());
				} else {
					bandera = false;
					panelRenderError[3] = false;
					errores[3] = "Por favor ingrese los motivos por los cuales no completo con este producto.";
				}
			}

			// Adicionar Compromisos
			if (productoSa != null && bandera) {
				resultadoEsp.setCompletado("");
				listaProductosSara.add(productoSa);
				crearListaItemsProductosEsperados();
				productoSara = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error adicionar compromisos");
		}
	}

	public void cambiarCumplimientoProducto() {
		proyectoInformeActual.setProductosEnSara("NO");
		existeLinkPublico = "";
		linkPublico = "";
	}

	public void eliminarCompromisos() {
		listaProductosSara.remove(productoSeleccionado);
		crearListaItemsProductosEsperados();
	}

	public List<ProductoSara> getListaProductosEntregados() {
		List<ProductoSara> listaProductosEntregados = new ArrayList<ProductoSara>();
		if (!esListaVacia(listaProductosSara)) {
			Iterator<ProductoSara> i = listaProductosSara.iterator();
			while (i.hasNext()) {
				ProductoSara productoSara = i.next();
				if (StringUtils.isEmpty(productoSara.getProductoCompletado())
						|| (StringUtils.isNotEmpty(productoSara.getProductoCompletado())
								&& "SI".equals(productoSara.getProductoCompletado()))) {
					try {

						String hql = "select dd from DominioDetalle dd where dd.identificador.id = '"
								+ Dominio.DOMINIO_ODS + "' and dd.identificador.tipo = '"
								+ productoSara.getObjetivoDesarrolloSost().toString() + "'";
						List ods = servicioGeneral.obtenerObjetos(DominioDetalle.class, hql);

						if (!esListaVacia(ods)) {
							DominioDetalle dd = (DominioDetalle) ods.get(0);
							productoSara.setNombreOds(dd.getDescripcion());
						}

						String hqlOcde = "select dd from DominioDetalle dd where dd.identificador.id = '"
								+ Dominio.DOMINIO_AREA_CIENCIA + "' and dd.identificador.tipo = '"
								+ productoSara.getAreaOCDEppal().toString() + "'";
						List ocde = servicioGeneral.obtenerObjetos(DominioDetalle.class, hqlOcde);

						if (!esListaVacia(ocde)) {
							DominioDetalle dd2 = (DominioDetalle) ocde.get(0);
							productoSara.setNombreOcde(dd2.getDescripcion());
						}

					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						listaProductosEntregados.add(productoSara);
					}
				}
			}
		}
		return listaProductosEntregados;
	}

	public List<ProductoSara> getListaProductosNoEntregados() {
		List<ProductoSara> listaProductosNoEntregados = new ArrayList<ProductoSara>();
		if (!esListaVacia(listaProductosSara)) {
			Iterator<ProductoSara> i = listaProductosSara.iterator();
			while (i.hasNext()) {
				ProductoSara productoSara = i.next();
				if (StringUtils.isNotEmpty(productoSara.getProductoCompletado())
						&& "NO".equals(productoSara.getProductoCompletado())) {
					listaProductosNoEntregados.add(productoSara);
				}
			}
		}
		return listaProductosNoEntregados;
	}

	public ProyectoInforme cargarDatosEvento(ProyectoInforme proyectoInforme) {
		if (proyectoInforme.getTotalValorEjecutado() != null) {
			valorEjecucionPresupuestas = proyectoInforme.getTotalValorEjecutado().toString();
		}

		if (proyectoInforme.getNumeroParticipantesEvento() != null) {
			numeroTotalParticipantes = proyectoInforme.getNumeroParticipantesEvento().toString();
		}

		if (proyectoInforme.getNumeroEstudiantesEvento() != null) {
			numeroTotalEstudiantes = proyectoInforme.getNumeroEstudiantesEvento().toString();
		}

		if (proyectoInforme.getNumeroExternosEvento() != null) {
			numeroTotalExternos = proyectoInforme.getNumeroExternosEvento().toString();
		}
		return proyectoInforme;
	}

	private void cargarInforme() {
		listaArchivos = new ArrayList<ArchivoInforme>();
		List<ProyectoInforme> listaInformes = servicioGeneral.obtenerListaObjetosWhere(ProyectoInforme.class,
				" where p.id ='" + idInforme + "'");
		unProyectoInforme = new ProyectoInforme();
		if (listaInformes != null && listaInformes.size() > 0) {
			unProyectoInforme = listaInformes.get(0);

			listaArchivos = servicioGeneral.obtenerObjetos(ArchivoInforme.class,
					"select arch from ArchivoInforme arch where " + " (arch.estado is null or "
							+ " arch.estado <> 'B') and arch.informe.id=" + unProyectoInforme.getId().toString());

			if (tipoInformeAccion != null && tipoInformeAccion == 1) {
				resumenAvances = unProyectoInforme.getAvanceResumen();
				resumenResultados = unProyectoInforme.getAvanceResultados();
				dificultades = unProyectoInforme.getDificultades();
			} else {
				resumenAvances = unProyectoInforme.getSipnosis();
				resumenResultados = unProyectoInforme.getResumenTecnico();
				dificultades = unProyectoInforme.getImpacto();
				conclusiones = unProyectoInforme.getConclusiones();
			}

			if (esPermisoMarco) {
				observaciones = unProyectoInforme.getObservaciones();
				if (unProyectoInforme.getRecolectaEspecimenes() != null
						&& unProyectoInforme.getRecolectaEspecimenes().equals("S")) {
					movilizacionEspecimenes = true;
				} else {
					movilizacionEspecimenes = false;
				}
			}

			estadoInforme = unProyectoInforme.getEstadoInforme().getId();

			unProyectoInforme = cargarDatosEvento(unProyectoInforme);

			proyectoInformeActual.setId(unProyectoInforme.getId());
			fechaDesdeInforme = unProyectoInforme.getFechaDesde();
			fechaHastaInforme = unProyectoInforme.getFechaHasta();
			porcentajeEjecucion = unProyectoInforme.getPorcentajeEjecucion();
			porcentajeEjecucionPresupuestal = unProyectoInforme.getPorcentajeEjecucionPresupuestal();
			porcentajeEjecucionPresupuestalExt = unProyectoInforme.getPorcentajeEjecucionPresupuestalExterno();
			if (unProyectoInforme.getMostartMontoEjecutadoExt() == null) {
				unProyectoInforme.setMostartMontoEjecutadoExt("N");
			} else {
				unProyectoInforme.setMostartMontoEjecutadoExt("Y");
			}
			if (unProyectoInforme.getMontoTotalEjecutadoPeriodo() != null) {
				montoEjecutado = unProyectoInforme.getMontoTotalEjecutadoPeriodo().toString();
			}
			if (unProyectoInforme.getMontoTotalEjecutadoPeriodoExt() != null) {
				montoEjecutadoExt = unProyectoInforme.getMontoTotalEjecutadoPeriodoExt().toString();
			}
			proyectoInformeActual.setBienes(new TreeSet<Bien>(servicioGeneral.obtenerListaObjetosWhere(Bien.class,
					" WHERE b.informe.id = " + proyectoInformeActual.getId())));
			proyectoInformeActual
					.setProductos(new TreeSet<ProductoSara>(servicioGeneral.obtenerListaObjetosWhere(ProductoSara.class,
							" WHERE p.informe.id = " + proyectoInformeActual.getId())));
			proyectoInformeActual.setProyecto(unProyectoInforme.getProyecto());
			proyectoInformeActual.setTipoInforme(unProyectoInforme.getTipoInforme());
			proyectoInformeActual.setEstadoInforme(unProyectoInforme.getEstadoInforme());
			calificacion = unProyectoInforme.getCalificacion();
			proyectoInformeActual.setFechaGeneracion(unProyectoInforme.getFechaGeneracion());

			proyectoInformeActual.setCuadroResultados(unProyectoInforme.getCuadroResultados());
			proyectoInformeActual.setInformeFinanciero(unProyectoInforme.getInformeFinanciero());
			proyectoInformeActual.setCuadroNombre(unProyectoInforme.getCuadroNombre());
			proyectoInformeActual.setFinancieroNombre(unProyectoInforme.getFinancieroNombre());

			proyectoInformeActual.setFechaAprobacion(unProyectoInforme.getFechaAprobacion());

			proyectoInformeActual.setFechaLectura(unProyectoInforme.getFechaLectura());

			fechaEntrega = unProyectoInforme.getFechaGeneracion();

			proyectoInformeActual.setFormacionEstudiantes(unProyectoInforme.getFormacionEstudiantes());
			proyectoInformeActual.setCompromisos(unProyectoInforme.getCompromisos());
			proyectoInformeActual.setProductosEnSara(unProyectoInforme.getProductosEnSara());
			proyectoInformeActual.setObjetivos(unProyectoInforme.getObjetivos());
			proyectoInformeActual.setActividades(unProyectoInforme.getActividades());
			proyectoInformeActual.setResultados(unProyectoInforme.getResultados());

			// Compromisos
			listaProductosSara = new ArrayList<ProductoSara>();
			List<ProductoSara> listaProductoSara = servicioGeneral.obtenerObjetos(
					"select e from ProductoSara e where e.informe.id = '" + unProyectoInforme.getId() + "'");
			if (listaProductoSara != null && listaProductoSara.size() > 0) {
				for (int i = 0; i < listaProductoSara.size(); i++) {
					ProductoSara ps = (ProductoSara) listaProductoSara.get(i);
					listaProductosSara.add(ps);
				}
			}

			if (unProyectoInforme.getCompromisos() != null) {
				proyectoInformeActual.setCompromisos(unProyectoInforme.getCompromisos());
			}

			if (unProyectoInforme.getFormacionEstudiantes() != null) {
				proyectoInformeActual.setFormacionEstudiantes(unProyectoInforme.getFormacionEstudiantes());
			}

			if (proyectoInformeActual.getProductos() != null) {
				Set<ProductoSara> productos = new TreeSet<ProductoSara>();
				proyectoInformeActual.setProductos(productos);
			}

			// cargar lista de compromisos
			stringToEstudiantes();

			// mostrar panel equipos
			if (unProyectoInforme.getCompraEquipos() != null) {
				proyectoInformeActual.setCompraEquipos(unProyectoInforme.getCompraEquipos());

				if (proyectoInformeActual.getCompraEquipos().equals("SI")) {
					mostrarEquipos = true;
				} else {
					mostrarEquipos = true;
				}
			}

			if (esSolicitudRenovacion) {
				try {
					cargarSolicitudRenovacion();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			resumenAvances = "";
			resumenResultados = "";
			dificultades = "";
			conclusiones = "";
			fechaEntrega = new Date();
			listaInformes = new ArrayList<ProyectoInforme>();
			unProyectoInforme.setMostartMontoEjecutadoExt("Y");
		}
	}

	private void cargarListasProyecto() {
		try {

			boolean agregarActividadesInforme = false;

			if (proyectoInformeActual.getListaObjetivos() == null
					|| proyectoInformeActual.getListaObjetivos().isEmpty()) {
				agregarActividadesInforme = true;
			}

			List<ObjetivoEspecifico> lista = servicioGeneral
					.obtenerObjetos("select e from ObjetivoEspecifico e where e.proyecto.id = '" + proyecto.getId()
							+ "' order by e.numeroOrden asc");
			if (lista != null && lista.size() > 0) {
				listaObjetivosItem = new ArrayList<SelectItem>();
				for (int i = 0; i < lista.size(); i++) {
					ObjetivoEspecifico ps = (ObjetivoEspecifico) lista.get(i);
					if (agregarActividadesInforme) {
						proyectoInformeActual.adicionarObjetivo(ps);
					}
					listaObjetivosItem.add(new SelectItem(ps.getId(), ps.getNombre()));

				}
			}

			if (proyectoInformeActual.getListaActividades() == null
					|| proyectoInformeActual.getListaActividades().isEmpty()) {
				List<Actividad> listaA = servicioGeneral
						.obtenerObjetos("select e from Actividad e where e.proyecto.id = '" + proyecto.getId() + "'");
				if (listaA != null && listaA.size() > 0) {
					for (int i = 0; i < listaA.size(); i++) {
						Actividad ps = (Actividad) listaA.get(i);
						proyectoInformeActual.adicionarActividad(ps);
					}
				}
			}

			if (proyectoInformeActual.getListaResultados() == null
					|| proyectoInformeActual.getListaResultados().isEmpty()) {
				List<ResultadoProyecto> listaA = servicioGeneral.obtenerObjetos(
						"select e from ResultadoProyecto e where e.proyecto.id = '" + proyecto.getId() + "'");
				if (listaA != null && listaA.size() > 0) {
					for (int i = 0; i < listaA.size(); i++) {
						ResultadoProyecto ps = (ResultadoProyecto) listaA.get(i);
						proyectoInformeActual.adicionarResultado(ps);
					}
				}
			}

			if (tipoInformeAccion == 2 && (listaEstudiantes == null || listaEstudiantes.isEmpty())) {
				if (proyectoInformeActual.getProyecto() == null) {
					proyectoInformeActual.setProyecto(this.proyecto);
				}
				if (proyectoInformeActual.getProyecto().getListaEstudiantesProyecto() != null
						&& proyectoInformeActual.getProyecto().getListaEstudiantesProyecto().size() > 0) {
					for (int i = 0; i < proyectoInformeActual.getProyecto().getListaEstudiantesProyecto().size(); i++) {
						List<Estudiante> estudiantes = servicioGeneral
								.obtenerObjetos("select e from Estudiante e where e.id.documento = '"
										+ proyectoInformeActual.getProyecto().getListaEstudiantesProyecto().get(i)
												.getId().getDocumento()
										+ "' and e.id.tipoDocumento= '" + proyectoInformeActual.getProyecto()
												.getListaEstudiantesProyecto().get(i).getId().getTipoDocumento()
										+ "'");

						if (estudiantes.size() > 0) {
							Estudiante estudianteProyecto = estudiantes.get(0);
							listaEstudiantes.add(estudianteProyecto);
						}
					}
				}
			}

			listaImpacto = new ArrayList<ProyectoInformeImpacto>();
			listaImpactoBorrados = new ArrayList<ProyectoInformeImpacto>();
			if (proyectoInformeActual != null && proyectoInformeActual.getId() != null
					&& (listaImpacto == null || listaImpacto.isEmpty())) {
				listaImpacto = servicioGeneral
						.obtenerObjetos("select e from ProyectoInformeImpacto e where e.informe.id = '"
								+ proyectoInformeActual.getId() + "'");
				for (int i = 0; i < listaImpacto.size(); i++) {
					try {

						String hql = "select dd from DominioDetalle dd where dd.identificador.id = '"
								+ Dominio.DOMINIO_ODS + "' and dd.identificador.tipo = '"
								+ listaImpacto.get(i).getOds().toString() + "'";
						List ods = servicioGeneral.obtenerObjetos(DominioDetalle.class, hql);

						if (!esListaVacia(ods)) {
							DominioDetalle dd = (DominioDetalle) ods.get(0);
							listaImpacto.get(i).setNombreOds(dd.getDescripcion());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cargarSolicitudRenovacion() throws Exception {
		valorMatricula = unProyectoInforme.getAvanceResumen();
		mesesSostenimiento = unProyectoInforme.getCuadroNombre();
		if (unProyectoInforme.getDificultades() != null) {
			valorSostenimiento = Integer.parseInt(unProyectoInforme.getDificultades());
		}
		mesesPasantia = unProyectoInforme.getConclusiones();
		valorPasantia = unProyectoInforme.getSipnosis();
		valorApoyoTesis = Integer.parseInt(unProyectoInforme.getImpacto());

		if (valorApoyoTesis == null || valorApoyoTesis == 0) {
			valorApoyoTesis = VALOR_APOYO_TESIS;
		}
		observaciones = unProyectoInforme.getObservaciones();
		estadoInforme = unProyectoInforme.getEstadoInforme().getId();
		anoSolicitud = unProyectoInforme.getDependenciaAvalFacultad();
		semestreSolicitud = ciudad = unProyectoInforme.getNoAprobacion();
		apoyoTesis = unProyectoInforme.getProductosEnSara();

		setFechaPago(unProyectoInforme.getFechaAprobacion());
		setNroResolucion(unProyectoInforme.getFinancieroNombre());
		setValorPagado(unProyectoInforme.getNombreDocumentoFac());

		pasantias = new ArrayList<VistaBD>();
		String xmlRecords = unProyectoInforme.getSipnosisD();

		if (xmlRecords != null) {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlRecords));
			xmlPasantias = db.parse(is);
			NodeList nodes = xmlPasantias.getElementsByTagName("pasantia");
			for (int i = 0; i < nodes.getLength(); i++) {
				Node nNode = nodes.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					VistaBD unaPasantia = new VistaBD();

					unaPasantia.setLongitud(Long.parseLong(eElement.getAttribute("id").toString(), 10));
					unaPasantia.setTipoTexto(eElement.getElementsByTagName("pais").item(0).getNodeValue());
					unaPasantia.setTextoOid(eElement.getElementsByTagName("ciudad").item(0).getNodeValue());
					unaPasantia.setNombre(eElement.getElementsByTagName("institucion").item(0).getNodeValue());
					unaPasantia.setLongitudTipoTexto(
							Long.parseLong(eElement.getElementsByTagName("duracion").item(0).getNodeValue(), 10));
					unaPasantia.setTexto(eElement.getElementsByTagName("tema").item(0).getNodeValue());
					unaPasantia.setDuenoVista(eElement.getElementsByTagName("ano").item(0).getNodeValue());
					unaPasantia.setNombreSuperVista(unaPasantia.getTextoOid() + ", " + unaPasantia.getTipoTexto());
					pasantias.add(unaPasantia);
				}
			}
		}

		if (!consultaRenovacionCoordindador) {
			editable = (estadoInforme == EstadoInforme.DEVUELTO || estadoInforme == 0);
		}

		List<ProyectoInforme> listaInformes = servicioGeneral.obtenerObjetosLimitado(ProyectoInforme.class,
				"select #id p.id from ProyectoInforme p where p.tipoInforme =3 and "
						+ "p.numeroActoAdministrativoFacultad='"
						+ unProyectoInforme.getNumeroActoAdministrativoFacultad() + "' and "
						+ "p.pryTieneSaldoEjecucion='" + unProyectoInforme.getPryTieneSaldoEjecucion() + "' and "
						+ "p.id !='" + idInforme + "' and" + "(p.productosEnSara='SI' or p.productosEnSara='Si')");
		if (listaInformes != null) {
			tienePasantia = listaInformes.size() > 0;
		} else {
			tienePasantia = false;
		}
	}

	private boolean enviarCorreo(String id) {
		Boolean envio = false;
		correoActual = cargarPlantilla(35);
		personaActual = (Persona) sesion.getAttribute("persona");
		editarCorreo(personaActual, id);
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = personaActual.getEmail();
		correo.adicionarDireccion(dirCorreo);
		//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		try {
			if (servicioCorreo.enviarCorreo(correo)) {
				envio = true;
			} else
				envio = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return envio;
	}

	public String editarCorreo(Persona personaAux, String id) {
		try {
			String correo = correoActual.getCuerpo();
			correo = correo.replaceAll("<<INVESTIGADOR>>", personaActual.getNombreCompleto());
			correo = correo.replaceAll("<<IDSOLICITUD>>", id);
			cuerpoCorreo = correo;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return "";
	}

	public String editarCorreo2(Persona personaAux, String id) {
		try {
			String correo = correoActual.getCuerpo();
			correo = correo.replaceAll("<<INVESTIGADOR>>", personaActual.getNombreCompleto());
			correo = correo.replaceAll("<<ID_INVESTIGADOR>>",
					personaActual.getId().getTipoDocumento() + " - " + personaActual.getId().getDocumento());
			correo = correo.replaceAll("<<ID_SOLICITUD>>", id);
			cuerpoCorreo = correo;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return "";
	}

	public void stringToCompromisos() {
		String compromisos = this.proyectoInformeActual.getCompromisos();
		if (compromisos != null && !compromisos.equals("")) {
			String[] sdocentesList = compromisos.split("\r\n");
			for (int i = 0; i < sdocentesList.length; i++) {
				ResultadoCompromiso p = new ResultadoCompromiso();
				String[] sDocente = sdocentesList[i].split("~");
				p.setObjetivo(sDocente[0]);
				p.setResultadoEsperado(sDocente[1]);
				p.setResultadoObtenido(sDocente[2]);
				listaCompromisos.add(p);
			}
		}
	}

	public void stringToEstudiantes() {
		String estudiantes = this.proyectoInformeActual.getFormacionEstudiantes();
		if (estudiantes != null && !estudiantes.equals("")) {
			String[] sdocentesList = estudiantes.split("\r\n");
			for (int i = 0; i < sdocentesList.length; i++) {
				Estudiante p = new Estudiante();
				String[] sDocente = sdocentesList[i].split("~");
				IdPersona id = new IdPersona();
				id.setTipoDocumento(sDocente[0]);
				id.setDocumento(sDocente[1]);
				p.setId(id);
				p.setNombre1(sDocente[2]);
				PlanEstudios pl = new PlanEstudios();
				pl.setNombre(sDocente[3]);
				p.setPlan(pl);
				listaEstudiantes.add(p);
			}
		}
	}

	public String guardarSolicitudRenovacion()
			throws NumberFormatException, SQLException, ParserConfigurationException {
		boolean error = false;

		try {
			Long.parseLong(valorMatricula);
		} catch (NumberFormatException e) {
			error = true;
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El valor ingresado en matricula es incorrecto.", "");
			context.addMessage("datosGuardados", msg);
			e.printStackTrace();
		}
		try {
			Integer.parseInt(mesesSostenimiento);
		} catch (NumberFormatException e) {
			error = true;
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El valor ingresado en meses de sostenimiento es incorrecto.", "");
			context.addMessage("datosGuardados", msg);
			e.printStackTrace();
		}
		try {
			valorSostenimiento.longValue();
		} catch (NumberFormatException e) {
			error = true;
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El valor ingresado en valor sostenimiento es incorrecto.", "");
			context.addMessage("datosGuardados", msg);
			e.printStackTrace();
		}
		try {
			Integer.parseInt(mesesPasantia);
		} catch (NumberFormatException e) {
			error = true;
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El valor ingresado en meses de pasantia es incorrecto.", "");
			context.addMessage("datosGuardados", msg);
			e.printStackTrace();
		}
		try {
			if (listaArchivos.size() == 0) {
				error = true;
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No se ha adjuntado ningun documento en la solicitud", "");
				context.addMessage("datosGuardados", msg);
			}
		} catch (NumberFormatException e) {
			error = true;
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No se ha adjuntado ningun documento en la solicitud", "");
			context.addMessage("datosGuardados", msg);
			e.printStackTrace();
		}

		if (error) {
			return "";
		} else {
			List<EstadoInforme> listaEstadoInforme;
			boolean errorArchivos = false;
			List<TipoInforme> listaTipoInforme;
			EstadoInforme ein = new EstadoInforme();
			TipoInforme tip = new TipoInforme();
			mostrarError = false;
			listaTipoInforme = servicioGeneral.obtenerListaObjetosWhere(TipoInforme.class, " where t.id ='3'");
			listaEstadoInforme = servicioGeneral.obtenerListaObjetosWhere(EstadoInforme.class, " where e.id ='2'");
			tip = listaTipoInforme.get(0);
			ein = listaEstadoInforme.get(0);
			String xmlPasantiasStr = construirXml();
			Proyecto proyectoRenovacion = new Proyecto();
			proyectoRenovacion.setId(this.proyectoRenovacion);
			Persona persona = (Persona) sesion.getAttribute("persona");
			proyectoInformeActual.setProyecto(proyectoRenovacion);
			proyectoInformeActual.setEstadoInforme(ein);
			proyectoInformeActual.setFechaGeneracion(new Date());
			proyectoInformeActual.setTipoInforme(tip);
			proyectoInformeActual.setAvanceResumen(valorMatricula);
			proyectoInformeActual.setCuadroNombre(mesesSostenimiento);
			proyectoInformeActual.setDificultades(valorSostenimiento.toString());
			proyectoInformeActual.setConclusiones(mesesPasantia);
			proyectoInformeActual.setSipnosis(valorPasantia);
			proyectoInformeActual.setImpacto(valorApoyoTesis.toString());
			proyectoInformeActual.setSipnosisD(xmlPasantiasStr);

			proyectoInformeActual.setDependenciaAvalFacultad(anoSolicitud);
			proyectoInformeActual.setNoAprobacion(semestreSolicitud);
			proyectoInformeActual.setProductosEnSara(apoyoTesis);

			proyectoInformeActual.setNumeroActoAdministrativoFacultad(persona.getId().getDocumento());
			proyectoInformeActual.setPryTieneSaldoEjecucion(persona.getId().getTipoDocumento());
			servicioGeneral.guardarObjeto(proyectoInformeActual);
			if (proyectoInformeActual.getId() != null) {
				listaEstadoInforme = new ArrayList();
				Solicitud sol = new Solicitud();
				List listaProyecto = new ArrayList();
				Date fecha = new Date();
				sol.setFecha(fecha);
				sol.setRespuesta("");
				sol.setDescripcion("Solicitud de renovación");
				TipoSolicitud tipoSolicitud = servicioSolicitudes.buscarTipoSolicitudPorId(TipoSolicitud.RENOCACION);
				listaProyecto = servicioGeneral.obtenerListaObjetos("Proyecto where id ='" + 20898 + "'");
				Proyecto pr = (Proyecto) listaProyecto.get(0);
				TipoViaSolicitud tipoViaSolicitud = servicioSolicitudes
						.buscarTipoViaSolicitudPorId(TipoViaSolicitud.VIA_HERMES);
				sol.setProyecto(pr);
				sol.setTipoViaSolicitud(tipoViaSolicitud);
				sol.setTipoSolicitud(tipoSolicitud);
				servicioGeneral.guardarObjeto(sol);

				SolicitudInforme solInf = new SolicitudInforme();
				solInf.setInformeID(proyectoInformeActual.getId());
				solInf.setSolicitudID(sol.getId());

				servicioGeneral.guardarObjeto(solInf);

				Proyecto pr1 = new Proyecto();

				List personaSeguimiento = this.servicioGeneral
						.obtenerObjetos("FROM Parametro WHERE nombre = 'SEG_SOLICITUD_RENOVACION'");
				correoActual = cargarPlantilla(168);
				Correo correo = new Correo();
				correo.setAsunto(correoActual.getAsunto());
				correo.setCuerpo(cuerpoCorreo);
				if (!personaSeguimiento.isEmpty()) {
					for (int k = 0; k < personaSeguimiento.size(); k++) {
						Parametro parametro = (Parametro) personaSeguimiento.get(k);
						ProyectoCoordinador proyCoordinador = new ProyectoCoordinador();
						proyCoordinador.setProyecto(pr);
						proyCoordinador.setIdProyecto(pr.getId());
						proyCoordinador.setPerId(parametro.getValor());
						proyCoordinador.setTdoId(parametro.getProfesion());
						servicioGeneral.guardarObjeto(proyCoordinador);
						AlertaProyecto ale = new AlertaProyecto();
						ale.setEstado("P");
						ale.setFechaGenera(fecha);
						ale.setMensaje(sol.getDescripcion());
						ale.setProyecto(pr);
						ale.setSolicitud(sol);
						Persona persona2 = new Persona();
						IdPersona idPersona = new IdPersona();
						idPersona.setDocumento(parametro.getValor());
						idPersona.setTipoDocumento(parametro.getProfesion());
						correo.adicionarDireccion(parametro.getDescripcion());
						//correo.adicionarCopiaOculta("sisii_nal@unal.edu.co");
						persona2.setId(idPersona);
						ale.setAsesor(persona2);
						servicioGeneral.guardarObjeto(ale);
					}
					servicioCorreo.enviarCorreo(correo);

				}

				if (listaArchivos != null && listaArchivos.size() > 0) {
					try {
						for (int i = 0; i < listaArchivos.size(); i++) {
							ArchivoInforme amovAux1 = new ArchivoInforme();
							amovAux1 = listaArchivos.get(i);
							amovAux1.setInforme(proyectoInformeActual);
							servicioGeneral.guardarObjeto(amovAux1);
						}
					} catch (Exception e) {
						errorArchivos = true;
						e.printStackTrace();
					}

				}
				if (!errorArchivos) {
					enviarCorreo(proyectoInformeActual.getId().toString());
					FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Su informe ha sido guardado parcialmente", "");
					context.addMessage("datosGuardados", msg);

				} else {
					FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Ha ocurrido un error al guardar los archivos.", "");
					context.addMessage("datosGuardados", msg);
				}
			}
		}

		sesion.removeAttribute("manejadorPrincipalInforme");
		return "";
	}

	public void calcularValorTotalSostenimiento() {
		Integer valorSostenimientoTotal = (int) (SMMLV * mesesAprobados * Integer.parseInt(mesesSostenimiento));
		valorSostenimiento = valorSostenimientoTotal;
	}

	private ProyectoInforme cargarDatos(ProyectoInforme proyectoInforme) {
		try {
			Long valor = Long.parseLong(valorEjecucionPresupuestas);
			proyectoInforme.setTotalValorEjecutado(valor);
		} catch (NumberFormatException e) {
			proyectoInforme.setTotalValorEjecutado(0L);
		}
		try {
			Long valor = Long.parseLong(numeroTotalParticipantes);
			proyectoInforme.setNumeroParticipantesEvento(valor);
		} catch (NumberFormatException e) {
			proyectoInforme.setNumeroParticipantesEvento(0L);
		}
		try {
			Long valor = Long.parseLong(numeroTotalEstudiantes);
			proyectoInforme.setNumeroEstudiantesEvento(valor);
		} catch (NumberFormatException e) {
			proyectoInforme.setNumeroEstudiantesEvento(0L);
		}
		try {
			Long valor = Long.parseLong(numeroTotalExternos);
			proyectoInforme.setNumeroExternosEvento(valor);
		} catch (NumberFormatException e) {
			proyectoInforme.setNumeroExternosEvento(0L);
		}
		return proyectoInforme;
	}

	public String guardar() {
		return guardarParcialmente(true);
	}

	public String guardarParcialmente(boolean mostrarMensajes) {
		if (!esPermisoMarco && !((Convocatoria) this.proyecto.getModalidad()).getPadre().getId().equals(97L)
				&& !mostrarMensajes) {
			calculcarPorcEjecPresup();
		}
		if (mostrarMontoEjecExt) {
			calculcarPorcEjecPresupExt();
		}
		List<TipoInforme> listaTipoInforme;
		List<EstadoInforme> listaEstadoInforme;
		TipoInforme tip = new TipoInforme();

		EstadoInforme ein = new EstadoInforme();
		listaTipoInforme = new ArrayList<TipoInforme>();
		boolean isGuardado = false;

		if (esPermisoMarco) {
			proyectoInformeActual.setObservaciones(observaciones);
			if (movilizacionEspecimenes) {
				proyectoInformeActual.setRecolectaEspecimenes("S");
			} else {
				proyectoInformeActual.setRecolectaEspecimenes("N");
			}
		}

		if (calificacion != null && !calificacion.equals("")) {
			proyectoInformeActual.setCalificacion(calificacion);
		}
		if (idInforme == 0) {
			mostrarError = false;
			listaTipoInforme = servicioGeneral.obtenerListaObjetosWhere(TipoInforme.class, " where t.id ='3'");
			tip = listaTipoInforme.get(0);
			listaEstadoInforme = servicioGeneral.obtenerListaObjetosWhere(EstadoInforme.class, " where e.id ='1'");
			ein = listaEstadoInforme.get(0);

			proyectoInformeActual.setProyecto(this.proyecto);
			proyectoInformeActual.setEstadoInforme(ein);
			proyectoInformeActual.setFechaGeneracion(new Date());
			try {
				proyectoInformeActual.setIdEstudiante(estudianteLiderInforme.getId().getDocumento());
				proyectoInformeActual.setTipoDocEstudiante(estudianteLiderInforme.getId().getTipoDocumento());
			} catch (NullPointerException npe) {
				proyectoInformeActual.setIdEstudiante("");
				proyectoInformeActual.setTipoDocEstudiante("");
			}
			if (tipoInformeAccion == 1) {
				listaTipoInforme = servicioGeneral.obtenerListaObjetosWhere(TipoInforme.class, " where t.id ='1'");
				tip = listaTipoInforme.get(0);
				proyectoInformeActual.setTipoInforme(tip);
				proyectoInformeActual.setAvanceResultados(resumenResultados);
				proyectoInformeActual.setAvanceResumen(resumenAvances);
				proyectoInformeActual.setDificultades(dificultades);
				proyectoInformeActual.setFechaDesde(fechaDesdeInforme);
				proyectoInformeActual.setFechaHasta(fechaHastaInforme);
				proyectoInformeActual.setPorcentajeEjecucion(porcentajeEjecucion);
				proyectoInformeActual.setPorcentajeEjecucionPresupuestal(porcentajeEjecucionPresupuestal);
				proyectoInformeActual.setPorcentajeEjecucionPresupuestalExterno(porcentajeEjecucionPresupuestalExt);
				if (mostrarMontoEjecExt) {
					proyectoInformeActual.setMostartMontoEjecutadoExt("Y");
				}
				if (!((Convocatoria) this.proyecto.getModalidad()).getPadre().getId().equals(97L) && !esPermisoMarco) {
					if ((esCadenaVacia(montoEjecutado) || !cadenaEsValorNumerico(montoEjecutado)) && !mostrarMensajes) {
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Por favor, ingrese el monto total ejecutado.", "");
						if (mostrarMontoEjecExt) {
							msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Por favor, ingrese el monto total ejecutado.", "");
						}
						context.addMessage("datosGuardados", msg);
						return "";
					}
					if (montoEjecutado != null && !esCadenaVacia(montoEjecutado)
							&& cadenaEsValorNumerico(montoEjecutado)) {
						proyectoInformeActual.setMontoTotalEjecutadoPeriodo(Long.parseLong(montoEjecutado.trim()));
					} else {
						proyectoInformeActual.setMontoTotalEjecutadoPeriodo(0L);
					}
				}
				if (mostrarMontoEjecExt) {
					if ((esCadenaVacia(montoEjecutadoExt) || !cadenaEsValorNumerico(montoEjecutadoExt))
							&& !mostrarMensajes) {
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Por favor, ingrese el monto total ejecutado externo.", "");
						context.addMessage("datosGuardados", msg);
						return "";
					}
					if (montoEjecutadoExt != null && !esCadenaVacia(montoEjecutadoExt)
							&& cadenaEsValorNumerico(montoEjecutadoExt)) {
						proyectoInformeActual
								.setMontoTotalEjecutadoPeriodoExt(Long.parseLong(montoEjecutadoExt.trim()));
					} else {
						proyectoInformeActual.setMontoTotalEjecutadoPeriodoExt(0L);
					}
				}
			} else {
				listaTipoInforme = servicioGeneral.obtenerListaObjetosWhere(TipoInforme.class, " where t.id ='2'");
				tip = listaTipoInforme.get(0);
				proyectoInformeActual.setTipoInforme(tip);

				proyectoInformeActual.setSipnosis(resumenAvances);
				proyectoInformeActual.setResumenTecnico(resumenResultados);

				proyectoInformeActual.setImpacto(dificultades);
				proyectoInformeActual.setConclusiones(conclusiones);
				proyectoInformeActual.setFechaDesde(fechaDesdeInforme);
				proyectoInformeActual.setFechaHasta(fechaHastaInforme);
				proyectoInformeActual.setPorcentajeEjecucion(porcentajeEjecucion);
				proyectoInformeActual.setPorcentajeEjecucionPresupuestal(porcentajeEjecucionPresupuestal);
				proyectoInformeActual.setPorcentajeEjecucionPresupuestalExterno(porcentajeEjecucionPresupuestalExt);
				if (mostrarMontoEjecExt) {
					proyectoInformeActual.setMostartMontoEjecutadoExt("Y");
				}
				if (!((Convocatoria) this.proyecto.getModalidad()).getPadre().getId().equals(97L) && !esPermisoMarco) {
					if ((esCadenaVacia(montoEjecutado) || !cadenaEsValorNumerico(montoEjecutado)) && !mostrarMensajes) {
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Por favor, ingrese el monto total ejecutado.", "");
						if (mostrarMontoEjecExt) {
							msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Por favor, ingrese el monto total ejecutado interno.", "");
						}
						context.addMessage("datosGuardados", msg);
						return "";
					}
					if (montoEjecutado != null && !esCadenaVacia(montoEjecutado)
							&& cadenaEsValorNumerico(montoEjecutado)) {
						proyectoInformeActual.setMontoTotalEjecutadoPeriodo(Long.parseLong(montoEjecutado.trim()));
					} else {
						proyectoInformeActual.setMontoTotalEjecutadoPeriodo(0L);
					}
				}
				if (mostrarMontoEjecExt) {
					if ((esCadenaVacia(montoEjecutadoExt) || !cadenaEsValorNumerico(montoEjecutadoExt))
							&& !mostrarMensajes) {
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Por favor, ingrese el monto total ejecutado externo.", "");
						context.addMessage("datosGuardados", msg);
						return "";
					}
					if (montoEjecutadoExt != null && !esCadenaVacia(montoEjecutadoExt)
							&& cadenaEsValorNumerico(montoEjecutadoExt)) {
						proyectoInformeActual
								.setMontoTotalEjecutadoPeriodoExt(Long.parseLong(montoEjecutadoExt.trim()));
					} else {
						proyectoInformeActual.setMontoTotalEjecutadoPeriodoExt(0L);
					}
				}
				if (restriccionModalidad != null && (restriccionModalidad.equals(Convocatoria.TIPO_EVENTO_1)
						|| restriccionModalidad.equals(Convocatoria.TIPO_EVENTO_2))) {
					proyectoInformeActual = cargarDatos(proyectoInformeActual);
				}
			}

			try {

				if (listaEstudiantes.size() > 0) {
					String estudiante = "";

					for (int i = 0; i < listaEstudiantes.size(); i++) {
						Estudiante est = new Estudiante();
						String nombre = "";
						est = listaEstudiantes.get(i);

						if (est.getNombre1() != null) {
							nombre = est.getNombre1() + " ";
						}

						if (est.getNombre2() != null) {
							nombre = nombre + est.getNombre2() + " ";
						}

						if (est.getApellido1() != null) {
							nombre = nombre + est.getApellido1() + " ";
						}

						if (est.getApellido2() != null) {
							nombre = nombre + est.getApellido2() + " ";
						}

						estudiante = estudiante + est.getId().getTipoDocumento() + "~" + est.getId().getDocumento()
								+ "~" + nombre + "~" + est.getPlan().getNombre() + "\r\n";
					}
					proyectoInformeActual.setFormacionEstudiantes(estudiante);
				}
				servicioGeneral.guardarObjeto(proyectoInformeActual);
				try {
					servicioGeneral.guardarObjeto(crearHistoricoEstadoInforme(proyectoInformeActual, ""));
				} catch (NullPointerException npe) {
					npe.printStackTrace();
					System.out.println("Error al guardar historico");
				}

				if (idInforme != null) {
					try {
						servicioGeneral.eliminar("DELETE HER_BIEN WHERE PIN_ID = " + idInforme);
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}

				if (proyectoInformeActual.getBienes() != null) {
					for (Bien b : proyectoInformeActual.getBienes()) {
						b.setId(null);
						b.setInforme(proyectoInformeActual);
						servicioGeneral.insertarObjeto(b);
					}
				}

				if (idInforme != null) {

					try {
						servicioGeneral.eliminar("DELETE HER_PRODUCTO_SARA WHERE PIN_ID = " + idInforme);
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}

				if (this.listaProductosSara != null) {
					Set productoSet = new HashSet();
					for (int i = 0; i < listaProductosSara.size(); i++) {
						ProductoSara producto = new ProductoSara();
						producto = (ProductoSara) listaProductosSara.get(i);
						producto.setInforme(this.proyectoInformeActual);

						productoSet.add(producto);
					}

					this.proyectoInformeActual.setProductos(productoSet);
					servicioGeneral.guardarObjeto(this.proyectoInformeActual);
				}

				ArchivoInforme amovAux0;

				List<ArchivoInforme> listaArchivoMovilidad1 = new ArrayList<ArchivoInforme>();
				listaArchivoMovilidad1 = servicioGeneral.obtenerListaArchivosInformes(proyectoInformeActual.getId());

				boolean archivoCorreto = false;
				int contadora = 0;
				List<ArchivoInforme> listaArchivosBorrados = new ArrayList<ArchivoInforme>();

				if (listaArchivoMovilidad1 != null && listaArchivoMovilidad1.size() > 0) {
					for (int i = 0; i < listaArchivoMovilidad1.size(); i++) {
						amovAux0 = new ArchivoInforme();
						amovAux0 = listaArchivoMovilidad1.get(i);
						archivoCorreto = false;

						if (amovAux0.getId() != null) {
							for (int j = 0; j < listaArchivos.size(); j++) {
								ArchivoInforme amovAux3 = new ArchivoInforme();
								amovAux3 = listaArchivos.get(j);

								if (amovAux3.getId() != null) {
									if (amovAux3.getId().compareTo(amovAux0.getId()) == 0) {
										archivoCorreto = true;
									}
								}
							}
							if (!archivoCorreto) {
								servicioGeneral.eliminarObjeto(amovAux0);
								try {
									eliminarArchivoInformeGenerico(amovAux0.getId());
								} catch (Exception e) {
									e.printStackTrace();
								}
								listaArchivosBorrados.add(amovAux0);
								contadora = contadora + 1;
							}
						}
					}
				}

				if (listaArchivos != null && listaArchivos.size() > 0) {
					for (int i = 0; i < listaArchivos.size(); i++) {
						ArchivoInforme amovAux1 = new ArchivoInforme();
						amovAux1 = listaArchivos.get(i);
						amovAux1.setInforme(proyectoInformeActual);
						servicioGeneral.guardarObjeto(amovAux1);
					}
				}

				isGuardado = true;
				if (mostrarMensajes) {
					FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Su informe ha sido registrado con éxito", "");
					context.addMessage("datosGuardados", msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			try {
				proyectoInformeActual.setIdEstudiante(estudianteLiderInforme.getId().getDocumento());
				proyectoInformeActual.setTipoDocEstudiante(estudianteLiderInforme.getId().getTipoDocumento());
			} catch (NullPointerException npe) {
				System.out.println("Sin estudiante informe");
			}
			if (tipoInformeAccion == 1) {
				proyectoInformeActual.setAvanceResultados(resumenResultados);
				proyectoInformeActual.setAvanceResumen(resumenAvances);
				proyectoInformeActual.setDificultades(dificultades);
				proyectoInformeActual.setFechaDesde(fechaDesdeInforme);
				proyectoInformeActual.setFechaHasta(fechaHastaInforme);
				proyectoInformeActual.setPorcentajeEjecucion(porcentajeEjecucion);
				proyectoInformeActual.setPorcentajeEjecucionPresupuestal(porcentajeEjecucionPresupuestal);
				if (!((Convocatoria) this.proyecto.getModalidad()).getPadre().getId().equals(97L) && !esPermisoMarco) {
					if ((esCadenaVacia(montoEjecutado) || !cadenaEsValorNumerico(montoEjecutado)) && !mostrarMensajes) {
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Por favor, ingrese el monto total ejecutado.", "");
						context.addMessage("datosGuardados", msg);
						return "";
					}
					if (montoEjecutado != null && !esCadenaVacia(montoEjecutado)
							&& cadenaEsValorNumerico(montoEjecutado)) {
						proyectoInformeActual.setMontoTotalEjecutadoPeriodo(Long.parseLong(montoEjecutado.trim()));
					} else {
						proyectoInformeActual.setMontoTotalEjecutadoPeriodo(0L);
					}
				}
			} else {
				proyectoInformeActual.setSipnosis(resumenAvances);
				proyectoInformeActual.setResumenTecnico(resumenResultados);
				proyectoInformeActual.setImpacto(dificultades);
				proyectoInformeActual.setConclusiones(conclusiones);
				proyectoInformeActual.setFechaDesde(fechaDesdeInforme);
				proyectoInformeActual.setFechaHasta(fechaHastaInforme);
				proyectoInformeActual.setPorcentajeEjecucion(porcentajeEjecucion);
				proyectoInformeActual.setPorcentajeEjecucionPresupuestal(porcentajeEjecucionPresupuestal);
				proyectoInformeActual.setPorcentajeEjecucionPresupuestalExterno(porcentajeEjecucionPresupuestalExt);
				if (mostrarMontoEjecExt) {
					proyectoInformeActual.setMostartMontoEjecutadoExt("Y");
				}
				if (!((Convocatoria) this.proyecto.getModalidad()).getPadre().getId().equals(97L) && !esPermisoMarco) {
					if ((esCadenaVacia(montoEjecutado) || !cadenaEsValorNumerico(montoEjecutado)) && !mostrarMensajes) {
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Por favor, ingrese el monto total ejecutado.", "");
						if (mostrarMontoEjecExt) {
							msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Por favor, ingrese el monto total ejecutado interno.", "");
						}
						context.addMessage("datosGuardados", msg);
						return "";
					}
					if (montoEjecutado != null && !esCadenaVacia(montoEjecutado)
							&& cadenaEsValorNumerico(montoEjecutado)) {
						proyectoInformeActual.setMontoTotalEjecutadoPeriodo(Long.parseLong(montoEjecutado.trim()));
					} else {
						proyectoInformeActual.setMontoTotalEjecutadoPeriodo(0L);
					}
				}

				if (mostrarMontoEjecExt) {
					if ((esCadenaVacia(montoEjecutadoExt) || !cadenaEsValorNumerico(montoEjecutadoExt))
							&& !mostrarMensajes) {
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Por favor, ingrese el monto total ejecutado externo.", "");
						context.addMessage("datosGuardados", msg);
						return "";
					}
					if (montoEjecutadoExt != null && !esCadenaVacia(montoEjecutadoExt)
							&& cadenaEsValorNumerico(montoEjecutadoExt)) {
						proyectoInformeActual
								.setMontoTotalEjecutadoPeriodoExt(Long.parseLong(montoEjecutadoExt.trim()));
					} else {
						proyectoInformeActual.setMontoTotalEjecutadoPeriodoExt(0L);
					}
				}
			}

			// AGREGAR BIENES
			if (idInforme != null) {
				try {
					servicioGeneral.eliminar("DELETE HER_BIEN WHERE PIN_ID = " + idInforme);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (proyectoInformeActual.getBienes() != null) {
				for (Bien b : proyectoInformeActual.getBienes()) {
					b.setId(null);
					b.setInforme(proyectoInformeActual);
					servicioGeneral.insertarObjeto(b);
				}
			}

			// PRODUCTOS SARA
			// Eliminar productos
			if (idInforme != null) {

				try {
					servicioGeneral.eliminar("DELETE HER_PRODUCTO_SARA WHERE PIN_ID = " + idInforme);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			if (this.listaProductosSara != null) {

				Set productoSet = new HashSet();
				for (int i = 0; i < listaProductosSara.size(); i++) {
					ProductoSara producto = new ProductoSara();
					producto = (ProductoSara) listaProductosSara.get(i);
					producto.setInforme(this.proyectoInformeActual);

					if (producto.getId() != null) {
						producto.setId(null);
					}

					productoSet.add(producto);
				}

				this.proyectoInformeActual.setProductos(productoSet);
			}

			if (listaEstudiantes.size() > 0) {
				String estudiante = "";

				for (int i = 0; i < listaEstudiantes.size(); i++) {
					Estudiante est = new Estudiante();
					String nombre = "";
					est = listaEstudiantes.get(i);

					if (est.getNombre1() != null) {
						nombre = est.getNombre1() + " ";
					}

					if (est.getNombre2() != null) {
						nombre = nombre + est.getNombre2() + " ";
					}

					if (est.getApellido1() != null) {
						nombre = nombre + est.getApellido1() + " ";
					}

					if (est.getApellido2() != null) {
						nombre = nombre + est.getApellido2() + " ";
					}

					estudiante = estudiante + est.getId().getTipoDocumento() + "~" + est.getId().getDocumento() + "~"
							+ nombre + "~" + est.getPlan().getNombre() + "\r\n";
				}
				proyectoInformeActual.setFormacionEstudiantes(estudiante);
			}

			// GUARDAR INFORME
			try {

				servicioGeneral.guardarObjeto(proyectoInformeActual);

				if (!esListaVacia(listaImpacto)) {
					for (int i = 0; i < listaImpacto.size(); i++) {
						servicioGeneral.guardarObjeto(listaImpacto.get(i));
					}
				}

				if (!esListaVacia(listaImpactoBorrados)) {
					for (int i = 0; i < listaImpactoBorrados.size(); i++) {
						if (listaImpactoBorrados.get(i).getId() != null) {
							servicioGeneral.eliminarObjeto(listaImpactoBorrados.get(i));
						}
					}
				}

				ArchivoInforme amovAux0;

				List<ArchivoInforme> listaArchivoMovilidad1 = new ArrayList<ArchivoInforme>();
				listaArchivoMovilidad1 = servicioGeneral.obtenerListaArchivosInformes(proyectoInformeActual.getId());

				boolean archivoCorreto = false;
				int contadora = 0;
				List<ArchivoInforme> listaArchivosBorrados = new ArrayList<ArchivoInforme>();

				if (listaArchivoMovilidad1 != null && listaArchivoMovilidad1.size() > 0) {
					for (int i = 0; i < listaArchivoMovilidad1.size(); i++) {
						amovAux0 = new ArchivoInforme();
						amovAux0 = listaArchivoMovilidad1.get(i);
						archivoCorreto = false;

						if (amovAux0.getId() != null) {

							for (int j = 0; j < listaArchivos.size(); j++) {
								ArchivoInforme amovAux3 = new ArchivoInforme();
								amovAux3 = listaArchivos.get(j);

								if (amovAux3.getId() != null) {
									if (amovAux3.getId().compareTo(amovAux0.getId()) == 0) {
										archivoCorreto = true;
									}
								}
							}

							if (!archivoCorreto) {
								servicioGeneral.eliminarObjeto(amovAux0);
								listaArchivosBorrados.add(amovAux0);
								contadora = contadora + 1;
							}

						}
					}
				}

				if (listaArchivos != null && listaArchivos.size() > 0) {
					for (int i = 0; i < listaArchivos.size(); i++) {
						ArchivoInforme amovAux1 = new ArchivoInforme();
						amovAux1 = listaArchivos.get(i);

						amovAux1.setInforme(proyectoInformeActual);
						servicioGeneral.guardarObjeto(amovAux1);
					}
				}

				isGuardado = true;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (isGuardado) {
			Object atribCoord = super.sesion.getAttribute("esCoordinador");
			int esCoordinador = 0;

			if (atribCoord != null) {
				esCoordinador = Integer.parseInt(String.valueOf(atribCoord));
			}
			if (esCoordinador == 1) {
				return "volverPrincipalInformeCoordinador";
			} else {
				
				if(esEstudianteLider)
					enviarCorreoRegistroParcialEstLider();
				
				sesion.removeAttribute("manejadorPrincipalInforme");
				return "volverPrincipalInforme";
			}
		} else {
			return "";
		}
	}
	
	public void enviarCorreoRegistroParcialEstLider() {
		correoActual = cargarPlantilla(402);
		Correo correoElectronico = new Correo();
		
		Persona docente = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyecto.getId());
		
		try {
			String correo = correoActual.getCuerpo();
			correo = correo.replaceAll("<<NOMBRE_INVESTIGADOR>>", docente.getNombreCompleto());
			correo = correo.replaceAll("<<ID_PROYECTO>>", proyecto.getId().toString());
			correo = correo.replaceAll("<<NOMBRE_ESTUDIANTE>>", estudianteLiderInforme.getNombreCompleto());
						
			String asunto = correoActual.getAsunto();
			asunto = asunto.replaceAll("<<ID_PROYECTO>>", proyecto.getId().toString());
			
			correoElectronico.setOrigen(Correo.CORREO_HERMES);
			correoElectronico.adicionarDireccion(docente.getEmail());
			
			correoElectronico.setAsunto(asunto);
			correoElectronico.setCuerpo(correo);

		} catch (Exception e) {
		}		
		servicioCorreo.enviarCorreo(correoElectronico);
	}

	public void verArchivo() {
		descargarArchivoInformeGenerico(archivoSeleccionado);
	}

	private void infoProyecto(Long pIdProyecto) {
		this.proyecto = super.servicioProyecto.obtenerProyecto(pIdProyecto, ProyectoDAOHibernate.INFORMACION_GENERAL);
		Investigador investigadorPrincipal = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(pIdProyecto);

		nombreInvestigador = investigadorPrincipal.getNombre1() + " " + investigadorPrincipal.getNombre2() + " "
				+ investigadorPrincipal.getApellido1() + " " + investigadorPrincipal.getApellido2();

		// Se carga la restricción actual de la modalidad.
		cargarRestriccionModalidad();
		if (proyecto.getModalidad().getTipo().getId().equals(TIPO_MODALIDAD_PERMISO_MARCO)) {
			esPermisoMarco = true;
		}

		try {
			facultad = investigadorPrincipal.getDependencia().getPadre().getNombre();
		} catch (Exception e) {
			facultad = "";
		}

		try {
			dependencia = investigadorPrincipal.getDependencia().getNombre();
		} catch (Exception e) {
			dependencia = "";
		}

		setEsJornadaDocente(proyecto.getEsJornadaDocente().equals("Y"));

	}

	public void guardarArchivo() {
		ArchivoInforme ai;
		Persona persona = (Persona) sesion.getAttribute("persona");
		try {
			if (archivoCargar != null) {
				if (archivoCargar.getSize() <= ManejadorBase.MAXIMO_TAMANO_ARCHIVOS) {
					if (esSolicitudRenovacion) {
						ai = insertarArchivoInformeGenerico(3821, archivoCargar, 20898L, persona);
					} else {
						TipoArchivo tipoAr = new TipoArchivo();
						List listaAr = servicioGeneral.obtenerObjetoXID("TipoArchivo", tipoArchivo.getId() + "");
						tipoAr = (TipoArchivo) listaAr.get(0);
						ai = insertarArchivoInformeGenericoConTipos(3821, archivoCargar, idProyecto, persona, tipoAr);
					}
					listaArchivos.add(ai);
				} else {
					mensajeError("El tamaño del archivo excede el máximo permitido");
				}
			} else {
				mensajeError("Por favor seleccione un archivo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarArchivo() {
		listaArchivos.remove(archivoSeleccionado);
		try {

			eliminarArchivoInformeGenerico(archivoSeleccionado.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarPasantia() {
		pasantias.remove(pasantiaSeleccionada);
		mostrarPasantia = false;
	}

	public void verPasantia() {
		// pasantia = pasantiaSeleccionada.;
		tema = pasantiaSeleccionada.getTexto();
		anio = pasantiaSeleccionada.getDuenoVista();
		ciudad = pasantiaSeleccionada.getTextoOid();
		institucion = pasantiaSeleccionada.getNombre();
		duracion = pasantiaSeleccionada.getLongitudTipoTexto().toString();
		pais = pasantiaSeleccionada.getTipoTexto();
		mostrarPasantia = true;
	}

	public void nuevaPasantia() {
		tema = "";
		anio = "";
		ciudad = "";
		institucion = "";
		duracion = "";
		pais = "";
		mostrarPasantia = true;
	}

	public void agregarPasantia() {
		Long idPasantia = 1L;
		if (pasantias == null) {
			pasantias = new ArrayList<VistaBD>();
		} else if (pasantias.size() > 0) {
			idPasantia = (pasantias.get(pasantias.size() - 1).getLongitud() + 1);
		}
		VistaBD unaPasantia = new VistaBD();
		unaPasantia.setLongitud(idPasantia);
		unaPasantia.setTipoTexto(pais);
		unaPasantia.setTextoOid(ciudad);
		unaPasantia.setNombre(institucion);
		unaPasantia.setLongitudTipoTexto(Long.parseLong(duracion, 10));
		unaPasantia.setTexto(tema);
		unaPasantia.setDuenoVista(anio);
		unaPasantia.setNombreSuperVista(unaPasantia.getTextoOid() + ", " + unaPasantia.getTipoTexto());
		pasantias.add(unaPasantia);
		mostrarPasantia = false;
	}

	public String construirXml() throws ParserConfigurationException {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		xmlPasantias = docBuilder.newDocument();
		Element rootElement = xmlPasantias.createElement("pasantias");
		xmlPasantias.appendChild(rootElement);

		for (int i = 0; i < pasantias.size(); i++) {
			VistaBD unaPasantia = new VistaBD();
			unaPasantia = pasantias.get(i);

			Element pasantia = xmlPasantias.createElement("pasantia");
			rootElement.appendChild(pasantia);

			Attr attr = xmlPasantias.createAttribute("id");
			attr.setValue(unaPasantia.getLongitud().toString());
			pasantia.setAttributeNode(attr);

			Element pais = xmlPasantias.createElement("pais");
			pais.appendChild(xmlPasantias.createTextNode(unaPasantia.getTipoTexto()));
			pasantia.appendChild(pais);

			Element ciudad = xmlPasantias.createElement("ciudad");
			ciudad.appendChild(xmlPasantias.createTextNode(unaPasantia.getTextoOid()));
			pasantia.appendChild(ciudad);

			Element duracion = xmlPasantias.createElement("duracion");
			duracion.appendChild(xmlPasantias.createTextNode(unaPasantia.getLongitudTipoTexto().toString()));
			pasantia.appendChild(duracion);

			Element institucion = xmlPasantias.createElement("institucion");
			institucion.appendChild(xmlPasantias.createTextNode(unaPasantia.getNombre()));
			pasantia.appendChild(institucion);

			Element tema = xmlPasantias.createElement("tema");
			tema.appendChild(xmlPasantias.createTextNode(unaPasantia.getTexto()));
			pasantia.appendChild(tema);

			Element ano = xmlPasantias.createElement("ano");
			ano.appendChild(xmlPasantias.createTextNode(unaPasantia.getDuenoVista()));
			pasantia.appendChild(ano);
		}

		DOMImplementationLS domImplementation = (DOMImplementationLS) xmlPasantias.getImplementation();
		LSSerializer lsSerializer = domImplementation.createLSSerializer();
		return lsSerializer.writeToString(xmlPasantias);
	}

	public void cancelarNuevaPasantia() {
		mostrarPasantia = false;
	}

	public Persona obtenerInvestigadorPrincipal(List<InvestigadorProyecto> investigadores) {
		Persona persona_ = new Persona();
		Iterator<InvestigadorProyecto> i = investigadores.iterator();

		InvestigadorProyecto investigador_;
		for (; i.hasNext();) {
			investigador_ = i.next();
			if (investigador_.getTipo().getId().equals(InvestigadorProyecto.PRINCIPAL)) {
				persona_ = investigador_.getInvestigador();
				break;
			}
		}
		return persona_;
	}

	// ReqBien01 Miguel Cubides
	public void eliminarBien() {
		proyectoInformeActual.getBienes().remove(bienSeleccionado);
	}

	public void buscarBien() {
		if (!Util.validarNoVacio(placaBien)) {
			mostrarWarning("El campo Placa es obligatorio", "placaBien");
			return;
		}

		List<Bien> bienes = servicioGeneral.consultaEquipos(placaBien);
		bien = new Bien();
		if (bienes != null && !bienes.isEmpty()) {
			try {
				BeanUtils.copyProperties(bien, bienes.get(0));
			} catch (Exception e) {
				e.printStackTrace();
			}
			mostrarBien = true;
		}
	}

	public void agregarBien() {
		if (!Util.validarNoVacio(bien) || !Util.validarNoVacio(bien.getPlaca())) {
			mostrarWarning("Usted no puede agregar un bien vacío", "placaBien");
			return;
		}
		if (proyectoInformeActual.getBienes().contains(bien)) {
			mostrarWarning("Usted no puede agregar dos veces el mismo bien", "placaBien");
			return;
		}
		proyectoInformeActual.getBienes().add(bien);
		bien = new Bien();
		placaBien = "";
		mostrarBien = false;
	}

	public void dropdownPS_processValueChange() {
		Integer proInd = Integer.valueOf(productoLlave);
		productoSara = new ProductoSara();

		if (proInd != 0) {
			try {
				BeanUtils.copyProperties(productoSara, productosTmp.get(proInd - 1));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void buscarProductos() {
		String invId = ((Persona) sesion.getAttribute("persona")).getId().getDocumento();
		boolean error = false;
		productoLlave = "";
		try {
			productosTmp = servicioGeneral.obtenerObjetos(VProductoSara.class,
					"SELECT p FROM VProductoSara p WHERE p.idInvestigador = '" + invId + "' "
							+ " AND upper (p.nombreProducto) LIKE upper ('%" + nombreProducto + "%')");
		} catch (Exception e) {
			error = true;
		}
		productoSara = new ProductoSara();

		if (productosTmp != null && !productosTmp.isEmpty()) {
			try {
				BeanUtils.copyProperties(productoSara, productosTmp.get(0));
			} catch (Exception e) {
				e.printStackTrace();
			}
			productosSaraItems = new SelectItem[productosTmp.size() + 1];
			int i = 1;
			productosSaraItems[0] = new SelectItem("0", "Seleccione producto registrado en SARA");
			for (VProductoSara vps : productosTmp) {
				productosSaraItems[i] = new SelectItem("" + i++, vps.getNombreProducto());
			}
			productoLlave = "0";
		}
	}

	public void agregarProducto() {
		if (!Util.validarNoVacio(productoSara) || !Util.validarNoVacio(productoSara.getLlave())) {
			mostrarWarning("Usted no puede agregar un producto vacío", "nombreProducto");
			return;
		}
		proyectoInformeActual.getProductos().add(productoSara);

		listaResultadosItem.add(new SelectItem(productoSara.getLlave(), productoSara.getNombreProducto()));
	}

	public String guardarEnviar() {
		try {
			guardarParcialmente(false);
			boolean valida = true;
			if (esPermisoMarco) {
				if (movilizacionEspecimenes && (listaArchivos == null || listaArchivos.size() < 1)) {
					mensajeError("No se han encontrado archivos adjuntos, como mínimo debe incluir los indicados.");
					valida = false;
				}
				if (!movilizacionEspecimenes && (observaciones == null || "".equals(observaciones.trim()))) {
					mensajeError(
							"En el campo observaciones, debe indicar porqué no hizo uso del Permiso Marco de Recolección.");
					valida = false;
				}
				if (!valida) {
					return "";
				}
			}

			if (listaArchivos != null && listaArchivos.size() > 0) {
				int cantArchivosInfFinanciero = 0;
				int cantArchivosInfTecnico = 0;
				for (int i = 0; i < listaArchivos.size(); i++) {
					ArchivoInforme archivoInforme = new ArchivoInforme();
					archivoInforme = listaArchivos.get(i);
					if (archivoInforme.getTipoArchivo() == null) {
						mensajeError("Hay un documento adicional que no tiene un tipo de archivo definido.");
						// return "";
						valida = false;
					} else if (archivoInforme.getTipoArchivo().getId() == 100) {
						cantArchivosInfFinanciero++;
					} else if (archivoInforme.getTipoArchivo().getId() == 101) {
						cantArchivosInfTecnico++;
					}
				}

				if (cantArchivosInfFinanciero < 1 && !esCadenaVacia(porcentajeEjecucionPresupuestal)
						&& !porcentajeEjecucionPresupuestal.equals("0")
						&& proyectoInformeActual.getTipoInforme().getId().equals(ProyectoInforme.TIPO_INFORME_FINAL)) {
					mensajeError(
							"No se han encontrado archivos adjuntos relacionados con el Informe Financiero del Proyecto.");
					valida = false;
				}

				if (cantArchivosInfTecnico < 1
						&& proyectoInformeActual.getTipoInforme().getId().equals(ProyectoInforme.TIPO_INFORME_FINAL)) {
					mensajeError(
							"No se han encontrado archivos adjuntos relacionados con el Informe Técnico del Proyecto.");
					valida = false;
				}
			}
			if (listaArchivos.size() == 0
					&& proyectoInformeActual.getTipoInforme().getId().equals(ProyectoInforme.TIPO_INFORME_FINAL)) {
				mensajeError("Por favor, adjunte el Informe Técnico del Proyecto.");
				valida = false;
				if (!esCadenaVacia(porcentajeEjecucionPresupuestal) && !porcentajeEjecucionPresupuestal.equals("0")) {
					mensajeError("Por favor, adjunte el Informe Financiero del Proyecto.");
					valida = false;
				}

			}

			if (valida) {
				return cerrarInforme(proyectoInformeActual, proyecto, true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void CambiarEstadoSolicitudRenovacion(String estadoAlerta, String estadoSolicitud, long estadoPryInf) {
		List listaSolicitudAux;
		List listaAlertas;
		listaSolicitudAux = new ArrayList();

		if (idAlertaInforme != null) {
			listaAlertas = new ArrayList();
			listaAlertas = new ArrayList();
			listaAlertas = servicioGeneral.obtenerListaObjetos("AlertaProyecto  where id ='" + idAlertaInforme + "'");
			AlertaProyecto alertaProyecto = new AlertaProyecto();

			alertaProyecto = (AlertaProyecto) listaAlertas.get(0);
			alertaProyecto.setEstado(estadoAlerta);
			servicioGeneral.guardarObjeto(alertaProyecto);
		}

		List listaSolicitudes;
		listaSolicitudes = new ArrayList();
		listaSolicitudes = servicioGeneral.obtenerListaObjetos("Solicitud  where id ='" + idSolicitud + "'");
		Solicitud solicitud = new Solicitud();

		solicitud = (Solicitud) listaSolicitudes.get(0);
		solicitud.setRespuesta(estadoSolicitud);
		servicioGeneral.guardarObjeto(solicitud);

		ProyectoInforme pryInf = new ProyectoInforme();
		listaSolicitudAux = servicioGeneral.obtenerListaObjetos("ProyectoInforme  where id ='" + idInforme + "'");
		pryInf = (ProyectoInforme) listaSolicitudAux.get(0);

		Date fechaLectura = new Date();
		pryInf.setFechaLectura(fechaLectura);

		// dgbenitezc: se actualiza a ACEPTADO_DIRECCION (3) el estado del
		// informe
		if (estadoPryInf == EstadoInforme.PAGO) {
			pryInf.setFechaAprobacion(fechaPago);
			pryInf.setFinancieroNombre(nroResolucion);
			pryInf.setNombreDocumentoFac(valorPagado);
		}
		pryInf.setEstadoInforme(new EstadoInforme(estadoPryInf));
		pryInf.setObservaciones(observaciones);
		servicioGeneral.guardarObjeto(pryInf);
		String sql1 = "select #nombre1 per.nombre1, #nombre2 per.nombre2, #apellido1 per.apellido1, #apellido2 per.apellido2, #email per.email"
				+ " from Persona per" + " where per.id.documento = '"
				+ unProyectoInforme.getNumeroActoAdministrativoFacultad() + "' " + "and per.id.tipoDocumento = '"
				+ unProyectoInforme.getPryTieneSaldoEjecucion() + "'";

		String sql2 = "select #nombreEstado estInf.nombreEstado from EstadoInforme estInf where estInf.id="
				+ pryInf.getEstadoInforme().getId();

		Persona unaPersona = (Persona) servicioGeneral.obtenerObjetosLimitado(Persona.class, sql1).get(0);
		String nombreEstado = servicioGeneral.obtenerObjetosLimitado(EstadoInforme.class, sql2).get(0)
				.getNombreEstado();
		String variablesCorreo[] = { unaPersona.getNombreCompleto(), pryInf.getId().toString(), nombreEstado };
		String nombreVariablesCorreo[] = { "INVESTIGADOR", "IDSOLICITUD", "ESTADOSOLICITUD" };

		servicioGeneral.enviarCorreo(unaPersona.getEmail(), 193, variablesCorreo, nombreVariablesCorreo);

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"El estado de la solicitud se ha guardado satisfactoriamente.", "");
		context.addMessage("datosGuardados", msg);
	}

	// fin ReqProSar01
	public void adicionarActividad() {
		ActividadEvento actividadEv = new ActividadEvento();
		try {

			if (this.actividad == null || this.actividad.equals("") || this.actividad.length() > 1000) {
				this.errores[10] = "La actividad NO es válida";
				this.panelRenderError[10] = true;
			} else {
				actividadEv.setNombre(actividad);
			}
			if (this.tipoContrato == null || this.tipoContrato.equals("") || this.tipoContrato.length() > 1000) {
				this.errores[11] = "El tipo de contrato NO es válido";
				this.panelRenderError[11] = true;
			} else {
				actividadEv.setTipoContrato(tipoContrato);
			}

			if (this.cantidad == null || this.cantidad.length() == 0) {
				this.errores[12] = "La cantidad NO es válida";
				this.panelRenderError[12] = true;
			} else {
				Long cantidadL = Long.parseLong(cantidad);
				actividadEv.setCantidad(cantidadL);
				if (this.valorUnitario == null || this.valorUnitario.length() == 0) {
					this.errores[13] = "El valor unitario NO es válido";
					this.panelRenderError[13] = true;
				} else {
					long valorUnitarioL = Long.parseLong(valorUnitario);
					actividadEv.setValorUnitario(valorUnitarioL);
					this.valorTotal = (valorUnitarioL * cantidadL);
					actividadEv.setValor(valorTotal);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void guardarArchivoRevisionFacultad(FileUploadEvent event) {

		archivoCargar = event.getFile();
		try {

			Persona persona = (Persona) sesion.getAttribute("persona");

			ArchivoInforme ai = insertarArchivoInformeGenerico(proyectoInformeActual.getId(), archivoCargar, idProyecto,
					persona);
			if (ai != null) {
				nombreDocumentoRevisionFacultad = ai.getNombre();
			}

		} catch (DataIntegrityViolationException ex) {
			System.out.println(ex.toString());
			if (ex.getMessage().indexOf("AD_COMBINACION01_UK") > 0)
				errores[20] = "Ya existe un archivo con este nombre";
			else
				errores[20] = "Ocurrio un error inesperado al publicar el archivo";
		} catch (Exception ex) {
			System.out.println(ex.toString());
			errores[20] = "Ocurrio un error inesperado al publicar el archivo";
		}
	}

	public String asignarEstadoRevisionEntidad() {
		List<EstadoInforme> listaEstadoInforme = servicioGeneral.obtenerListaObjetosWhere(EstadoInforme.class,
				" where e.id ='" + EstadoInforme.REVISION_ENTIDAD_EXTERNA + "'");
		EstadoInforme ein = listaEstadoInforme.get(0);
		proyectoInformeActual.setEstadoInforme(ein);
		servicioGeneral.guardarObjeto(proyectoInformeActual);
		try {
			servicioGeneral.guardarObjeto(crearHistoricoEstadoInforme(proyectoInformeActual, ""));
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			System.out.println("Error al guardar historico");
		}
		return "volverPrincipalInformeProyectoCoordinador";
	}
	
	/**
	 * En estudio ANLA informe coordinador.
	 *
	 * @return the string
	 */
	public String estudioAnlaInformeCoordinador() {
		List<EstadoInforme> listaEstadoInforme = servicioGeneral.obtenerListaObjetosWhere(EstadoInforme.class,
				" where e.id ='" + EstadoInforme.ESTUDIO_ANLA + "'");
		EstadoInforme ein = listaEstadoInforme.get(0);
		
		List<ProyectoInforme> listaSolicitudAux = servicioGeneral.obtenerObjetos(ProyectoInforme.class,
				"from ProyectoInforme  where id ='" + idInforme + "'");
		ProyectoInforme pryInf = (ProyectoInforme) listaSolicitudAux.get(0);

		pryInf.setFechaLectura(new Date());
		pryInf.setNoLectura(comentariosCoordinadorLectura);
		pryInf.setEstadoInforme(ein);
		pryInf.setObservaciones(observaciones);
		if(movilizacionEspecimenes) {
			pryInf.getRecolectaEspecimenes().equals("S");
		}else {
			pryInf.getRecolectaEspecimenes().equals("N");
		}
		servicioGeneral.guardarObjeto(pryInf);
		try {
			servicioGeneral.guardarObjeto(crearHistoricoEstadoInforme(pryInf, comentariosCoordinadorLectura));
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			System.out.println("Error al guardar historico");
		}
		return "volverPrincipalInformeProyectoCoordinador";
	}

	public boolean calculcarPorcEjecPresup() {
		if (montoEjecutado != null && montoEjecutado.length() > 0 && !montoEjecutado.equals("")) {
			int tam = montoEjecutado.trim().length();
			Pattern numerico = Pattern.compile("^[0-9]{" + tam + "}+$");
			Matcher m = numerico.matcher(montoEjecutado.trim());
			if (m.find()) {
				if (!mostrarMontoEjecExt) {
					Long montoTotalProyecto = 0L;
					if (proyecto.isConvUDEC()) {
						montoTotalProyecto += proyecto.getContrapartidaEfectivo()
								+ proyecto.getContrapartidaExternaEspecieTotal()
								+ proyecto.getContrapartidaEspecieTotal() + proyecto.getValorPersonalTotal()
								+ proyecto.getValorAdministrativoTotal()
								+ proyecto.getMontoEspecieContrapartidaExternaUDEC();
					} else if (proyecto.isConvSUE()) {
						montoTotalProyecto += proyecto.getContrapartidaExternaEspecieTotal()
								+ proyecto.getContrapartidaEspecieTotal() + proyecto.getMontofinanciarCalculado();
					} else {
						String hqlFinanciacion = "select #tipoFinanciacion e.tipoFinanciacion from Convocatoria e, Proyecto p where e.id = p.modalidad.id and p.id = "
								+ proyecto.getId();
						List<Convocatoria> listaConvFin = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class,
								hqlFinanciacion);
						String tipoFin = listaConvFin.get(0).getTipoFinanciacion();
						montoTotalProyecto += servicioProyecto.obtenerMontoAprobadoProyecto(proyecto.getId(), tipoFin);

						Boolean esProyectoLaboratorios;
						try {
							esProyectoLaboratorios = proyecto.getModalidad().getTipo().getId()
									.equals(TipoModalidad.REGISTRO_PROYECTOS_LABORATORIOS);
						} catch (Exception e) {
							esProyectoLaboratorios = false;
						}
						if (esProyectoLaboratorios) {
							montoTotalProyecto += servicioProyecto.obtenerMontoAprobadoProyectoLaboratorios(
									proyecto.getId(), proyecto.getModalidad().getId());
						}
						montoTotalProyecto += proyecto.getMontofinanciarCalculado();
						Long total = 0L;
						Iterator<Financiacion> i = proyecto.getListaEntidadesParticipantes().iterator();
						while (i.hasNext()) {
							Financiacion f = i.next();
							Long valor = 0L;
							if (f.getValor() != null) {
								valor = f.getValor();
							}
							total += valor;
						}
						montoTotalProyecto += total;
					}
					List<DetalleAdicionPresupuesto> listaSolicitudAdicionPresupuesto = new ArrayList<DetalleAdicionPresupuesto>();
					Iterator<Solicitud> k = servicioProyecto.obtenerSolicitudesProyecto(proyecto, true, false)
							.iterator();
					while (k.hasNext()) {
						Solicitud solicitud = k.next();
						if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.ADICION_PRESPUESTAL)
								&& solicitud.getRespuesta() != null && solicitud.getRespuesta().equals("A")) {
							SolicitudAdicionPresupuestal adicion = servicioSolicitudes
									.obtenerSolicitudAdicionPresupuestal(solicitud);
							if (adicion.getDetalleAdicionPresupuesto().size() > 0) {
								listaSolicitudAdicionPresupuesto.addAll(adicion.getDetalleAdicionPresupuesto());
							}
						}
					}
					for (DetalleAdicionPresupuesto detalleAdicion : listaSolicitudAdicionPresupuesto) {
						montoTotalProyecto += detalleAdicion.getValorAdicionRubro();
					}

					Long montoTotalEjecutado = 0L;
					montoTotalEjecutado = Long.parseLong(montoEjecutado.trim());
					if (montoTotalProyecto > 0L) {
						double porcentajeDouble = Math
								.ceil(((double) montoTotalEjecutado * 100) / (double) montoTotalProyecto);
						int porcentajeInt = (int) porcentajeDouble;
						if (porcentajeInt <= 100) {
							porcentajeEjecucionPresupuestal = Integer.toString(porcentajeInt);
						} else {
							mensajeError("El 'Porcentaje de Ejecución Presupuestal' supera el 100%.");
							porcentajeEjecucionPresupuestal = null;
							montoEjecutado = null;
							validaPorcentajeEjecutadoPresupuesto = false;
						}
					} else if (montoTotalProyecto == 0L) {
						if (montoTotalEjecutado > montoTotalProyecto
								&& !((Convocatoria) proyecto.getModalidad()).getPadre().getId().equals(233)) {
							mensajeError(
									"El 'Monto total ejecutado' no puede ser mayor al 'Monto total del proyecto'.");
							validaPorcentajeEjecutadoPresupuesto = false;
						} else {
							porcentajeEjecucionPresupuestal = "0";
						}
					} else {
						mensajeError(
								"No se puede calcular el 'Porcentaje de Ejecución Presupuestal' ya que el monto total del proyecto es menor a cero.");
						validaPorcentajeEjecutadoPresupuesto = false;
					}
				} else {
					Long montoTotalProyecto = 0L;
					String hqlFinanciacion = "select #tipoFinanciacion e.tipoFinanciacion from Convocatoria e, Proyecto p where e.id = p.modalidad.id and p.id = "
							+ proyecto.getId();
					List<Convocatoria> listaConvFin = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class,
							hqlFinanciacion);
					String tipoFin = listaConvFin.get(0).getTipoFinanciacion();
					montoTotalProyecto += servicioProyecto.obtenerMontoAprobadoProyecto(proyecto.getId(), tipoFin);
					Boolean esProyectoLaboratorios;
					try {
						esProyectoLaboratorios = proyecto.getModalidad().getTipo().getId()
								.equals(TipoModalidad.REGISTRO_PROYECTOS_LABORATORIOS);
					} catch (Exception e) {
						esProyectoLaboratorios = false;
					}
					if (esProyectoLaboratorios) {
						montoTotalProyecto += servicioProyecto.obtenerMontoAprobadoProyectoLaboratorios(
								proyecto.getId(), proyecto.getModalidad().getId());
					}
					List<DetalleAdicionPresupuesto> listaSolicitudAdicionPresupuesto = new ArrayList<DetalleAdicionPresupuesto>();
					Iterator<Solicitud> k = servicioProyecto.obtenerSolicitudesProyecto(proyecto, true, false)
							.iterator();
					while (k.hasNext()) {
						Solicitud solicitud = k.next();
						if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.ADICION_PRESPUESTAL)
								&& solicitud.getRespuesta() != null && solicitud.getRespuesta().equals("A")) {
							SolicitudAdicionPresupuestal adicion = servicioSolicitudes
									.obtenerSolicitudAdicionPresupuestal(solicitud);
							if (adicion.getDetalleAdicionPresupuesto().size() > 0
									&& adicion.getFinanciacion().getFuente().esInterna()) {
								listaSolicitudAdicionPresupuesto.addAll(adicion.getDetalleAdicionPresupuesto());
							}
						}
					}
					for (DetalleAdicionPresupuesto detalleAdicion : listaSolicitudAdicionPresupuesto) {
						montoTotalProyecto += detalleAdicion.getValorAdicionRubro();
					}

					Long montoTotalEjecutado = 0L;
					montoTotalEjecutado = Long.parseLong(montoEjecutado.trim());
					if (montoTotalProyecto > 0L) {
						double porcentajeDouble = Math
								.ceil(((double) montoTotalEjecutado * 100) / (double) montoTotalProyecto);
						int porcentajeInt = (int) porcentajeDouble;
						if (porcentajeInt <= 100) {
							porcentajeEjecucionPresupuestal = Integer.toString(porcentajeInt);
						} else {
							mensajeError("El 'Porcentaje de Ejecución Presupuestal Interno' supera el 100%.");
							porcentajeEjecucionPresupuestal = null;
							montoEjecutado = null;
							validaPorcentajeEjecutadoPresupuesto = false;
						}
					} else if (montoTotalProyecto == 0L) {
						if (montoTotalEjecutado > montoTotalProyecto
								&& !((Convocatoria) proyecto.getModalidad()).getPadre().getId().equals(233)) {
							mensajeError(
									"El 'Monto total ejecutado interno' no puede ser mayor al 'Monto interno financiado'.");
							validaPorcentajeEjecutadoPresupuesto = false;
						} else {
							porcentajeEjecucionPresupuestal = "0";
						}
					} else {
						mensajeError(
								"No se puede calcular el 'Porcentaje de Ejecución Presupuestal Interno' ya que el Monto interno financiado es menor a cero.");
						validaPorcentajeEjecutadoPresupuesto = false;
					}
				}

			} else {
				mensajeError("El 'Monto Total Ejecutado Interno' debe contener solo números.");
				validaPorcentajeEjecutadoPresupuesto = false;
			}
		} else {
			mensajeError("Por favor ingresar el 'Monto Total Ejecutado Interno'.");
			validaPorcentajeEjecutadoPresupuesto = false;
		}
		return validaPorcentajeEjecutadoPresupuesto;
	}

	public boolean calculcarPorcEjecPresupExt() {
		if (montoEjecutadoExt != null && montoEjecutadoExt.length() > 0 && !montoEjecutadoExt.equals("")) {
			int tam = montoEjecutadoExt.trim().length();
			Pattern numerico = Pattern.compile("^[0-9]{" + tam + "}+$");
			Matcher m = numerico.matcher(montoEjecutadoExt.trim());
			if (m.find()) {
				Long montoTotalProyecto = 0L;
				try {
					montoTotalProyecto += proyecto.getMontofinanciarCalculado();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				List<DetalleAdicionPresupuesto> listaSolicitudAdicionPresupuesto = new ArrayList<DetalleAdicionPresupuesto>();
				Iterator<Solicitud> k = servicioProyecto.obtenerSolicitudesProyecto(proyecto, true, false).iterator();
				while (k.hasNext()) {
					Solicitud solicitud = k.next();
					if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.ADICION_PRESPUESTAL)
							&& solicitud.getRespuesta() != null && solicitud.getRespuesta().equals("A")) {
						SolicitudAdicionPresupuestal adicion = servicioSolicitudes
								.obtenerSolicitudAdicionPresupuestal(solicitud);
						if (adicion.getDetalleAdicionPresupuesto().size() > 0 && adicion.getFinanciacion().getFuente()
								.getInternaExterna().equals(FuenteFinanciacion.externa)) {
							listaSolicitudAdicionPresupuesto.addAll(adicion.getDetalleAdicionPresupuesto());
						}
					}
				}
				for (DetalleAdicionPresupuesto detalleAdicion : listaSolicitudAdicionPresupuesto) {
					montoTotalProyecto += detalleAdicion.getValorAdicionRubro();
				}

				Long montoTotalEjecutado = 0L;
				montoTotalEjecutado = Long.parseLong(montoEjecutadoExt.trim());
				if (montoTotalProyecto > 0L) {
					double porcentajeDouble = Math
							.ceil(((double) montoTotalEjecutado * 100) / (double) montoTotalProyecto);
					int porcentajeInt = (int) porcentajeDouble;
					if (porcentajeInt <= 100) {
						porcentajeEjecucionPresupuestalExt = Integer.toString(porcentajeInt);
					} else {
						mensajeError("El 'Porcentaje de Ejecución Presupuestal Externo' supera el 100%.");
						porcentajeEjecucionPresupuestalExt = null;
						montoEjecutadoExt = null;
						validaPorcentajeEjecutadoPresupuesto = false;
					}
				} else if (montoTotalProyecto == 0L) {
					if (montoTotalEjecutado > montoTotalProyecto
							&& !((Convocatoria) proyecto.getModalidad()).getPadre().getId().equals(233)) {
						mensajeError(
								"El 'Monto total ejecutado externo' no puede ser mayor al 'Monto externo financiado'.");
						validaPorcentajeEjecutadoPresupuesto = false;
					} else {
						porcentajeEjecucionPresupuestalExt = "0";
					}
				} else {
					mensajeError(
							"No se puede calcular el 'Porcentaje de Ejecución Presupuestal Externo' ya que el Monto externo financiado es menor a cero.");
					validaPorcentajeEjecutadoPresupuesto = false;
				}
			} else {
				mensajeError("El 'Monto Total Ejecutado externo' debe contener solo números.");
				validaPorcentajeEjecutadoPresupuesto = false;
			}
		} else {
			mensajeError("Por favor ingresar el 'Monto Total Ejecutado externo'.");
			validaPorcentajeEjecutadoPresupuesto = false;
		}
		return validaPorcentajeEjecutadoPresupuesto;
	}

	public boolean isMostrarEquipos() {
		return mostrarEquipos;
	}

	public void setMostrarEquipos(boolean mostrarEquipos) {
		this.mostrarEquipos = mostrarEquipos;
	}

	public boolean isTienePasantia() {
		return tienePasantia;
	}

	public void setTienePasantia(boolean tienePasantia) {
		this.tienePasantia = tienePasantia;
	}

	public void setMostrarBien(boolean mostrarBien) {
		this.mostrarBien = mostrarBien;
	}

	public boolean isMostrarBien() {
		return mostrarBien;
	}

	public void setBienSeleccionado(Bien bienSeleccionado) {
		this.bienSeleccionado = bienSeleccionado;
	}

	public Bien getBienSeleccionado() {
		return bienSeleccionado;
	}

	public void setEstudianteSeleccionado(Estudiante estudianteSeleccionado) {
		this.estudianteSeleccionado = estudianteSeleccionado;
	}

	public Estudiante getEstudianteSeleccionado() {
		return estudianteSeleccionado;
	}

	public void setMostrarEstudiante(boolean mostrarEstudiante) {
		this.mostrarEstudiante = mostrarEstudiante;
	}

	public boolean isMostrarEstudiante() {
		return mostrarEstudiante;
	}

	public void setProductoSeleccionado(ProductoSara productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

	public ProductoSara getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setArchivoSeleccionado(ArchivoInforme archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public ArchivoInforme getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getActividad() {
		return actividad;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getValorUnitario() {
		return valorUnitario;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String guardarLecturaRenovacion() {
		CambiarEstadoSolicitudRenovacion("C", "A", EstadoInforme.ACEPTADO_DIRECCION);
		return "";
	}

	public String guardarDevolverRenovacion() {
		CambiarEstadoSolicitudRenovacion("C", "R", EstadoInforme.DEVUELTO);
		return "";
	}

	public String guardarDarPagoRenovacion() {
		CambiarEstadoSolicitudRenovacion("C", "A", EstadoInforme.PAGO);
		return "";
	}

	public void setListaActividades(List listaActividades) {
		this.listaActividades = listaActividades;
	}

	public List getListaActividades() {
		return listaActividades;
	}

	public void setValorMatricula(String valorMatricula) {
		this.valorMatricula = valorMatricula;
	}

	public String getValorMatricula() {
		return valorMatricula;
	}

	public void setMesesSostenimiento(String mesesSostenimiento) {
		this.mesesSostenimiento = mesesSostenimiento;
	}

	public String getMesesSostenimiento() {
		return mesesSostenimiento;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public void setValorSostenimiento(Integer valorSostenimiento) {
		this.valorSostenimiento = valorSostenimiento;
	}

	public Integer getValorSostenimiento() {
		return valorSostenimiento;
	}

	public void setMesesPasantia(String mesesPasantia) {
		this.mesesPasantia = mesesPasantia;
	}

	public String getMesesPasantia() {
		return mesesPasantia;
	}

	public void setValorPasantia(String valorPasantia) {
		this.valorPasantia = valorPasantia;
	}

	public String getValorPasantia() {
		return valorPasantia;
	}

	public void setEsSolicitudRenovacion(boolean esSolicitudRenovacion) {
		this.esSolicitudRenovacion = esSolicitudRenovacion;
	}

	public boolean isEsSolicitudRenovacion() {
		return esSolicitudRenovacion;
	}

	public boolean isConsultaRenovacionCoordindador() {
		return consultaRenovacionCoordindador;
	}

	public void setConsultaRenovacionCoordindador(boolean consultaRenovacionCoordindador) {
		this.consultaRenovacionCoordindador = consultaRenovacionCoordindador;
	}

	public String getPasantia() {
		return pasantia;
	}

	public void setPasantia(String pasantia) {
		this.pasantia = pasantia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public boolean isEsEstudianteLider() {
		return esEstudianteLider;
	}

	public void setEsEstudianteLider(boolean esEstudianteLider) {
		this.esEstudianteLider = esEstudianteLider;
	}

	public Persona getEstudianteLiderInforme() {
		return estudianteLiderInforme;
	}

	public void setEstudianteLiderInforme(Persona estudianteLiderInforme) {
		this.estudianteLiderInforme = estudianteLiderInforme;
	}

	public boolean isMostrarEstudianteLider() {
		return mostrarEstudianteLider;
	}

	public void setMostrarEstudianteLider(boolean mostrarEstudianteLider) {
		this.mostrarEstudianteLider = mostrarEstudianteLider;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public long getEstadoInforme() {
		return estadoInforme;
	}

	public void setEstadoInforme(long estadoInforme) {
		this.estadoInforme = estadoInforme;
	}

	public Integer getAnoActual() {
		return anoActual;
	}

	public void setAnoActual(Integer anoActual) {
		this.anoActual = anoActual;
	}

	public VistaBD getPasantiaSeleccionada() {
		return pasantiaSeleccionada;
	}

	public boolean isMostrarPasantia() {
		return mostrarPasantia;
	}

	public void setMostrarPasantia(boolean mostrarPasantia) {
		this.mostrarPasantia = mostrarPasantia;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getValorPagado() {
		return valorPagado;
	}

	public void setValorPagado(String valorPagado) {
		this.valorPagado = valorPagado;
	}

	public String getNroResolucion() {
		return nroResolucion;
	}

	public void setNroResolucion(String nroResolucion) {
		this.nroResolucion = nroResolucion;
	}

	public Integer getValorApoyoTesis() {
		return valorApoyoTesis;
	}

	public void setValorApoyoTesis(Integer valorApoyoTesis) {
		this.valorApoyoTesis = valorApoyoTesis;
	}

	public List<VistaBD> getPasantias() {
		return pasantias;
	}

	public void setPasantias(List<VistaBD> pasantias) {
		this.pasantias = pasantias;
	}

	public String getApoyoTesis() {
		return apoyoTesis;
	}

	public void setApoyoTesis(String apoyoTesis) {
		this.apoyoTesis = apoyoTesis;
	}

	public String getSemestreSolicitud() {
		return semestreSolicitud;
	}

	public void setSemestreSolicitud(String semestreSolicitud) {
		this.semestreSolicitud = semestreSolicitud;
	}

	public String getAnoSolicitud() {
		return anoSolicitud;
	}

	public void setAnoSolicitud(String anoSolicitud) {
		this.anoSolicitud = anoSolicitud;
	}

	public ProyectoInforme getUnProyectoInforme() {
		return unProyectoInforme;
	}

	public void setUnProyectoInforme(ProyectoInforme unProyectoInforme) {
		this.unProyectoInforme = unProyectoInforme;
	}

	public String getTipoFormularioInforme() {
		return tipoFormularioInforme;
	}

	public String getMensajeInicioConvocatoria() {
		return mensajeInicioConvocatoria;
	}

	public void setMensajeInicioConvocatoria(String mensajeInicioConvocatoria) {
		this.mensajeInicioConvocatoria = mensajeInicioConvocatoria;
	}

	public boolean isEsPryContrapartida() {
		return esPryContrapartida;
	}

	public void setEsPryContrapartida(boolean esPryContrapartida) {
		this.esPryContrapartida = esPryContrapartida;
	}

	public boolean isEsFichaMinima() {
		return esFichaMinima;
	}

	public void setEsFichaMinima(boolean esFichaMinima) {
		this.esFichaMinima = esFichaMinima;
	}

	/**
	 * @return the numeroTotalExternos
	 */
	public String getNumeroTotalExternos() {
		return numeroTotalExternos;
	}

	/**
	 * @param numeroTotalExternos the numeroTotalExternos to set
	 */
	public void setNumeroTotalExternos(String numeroTotalExternos) {
		this.numeroTotalExternos = numeroTotalExternos;
	}

	/**
	 * @return the numeroTotalEstudiantes
	 */
	public String getNumeroTotalEstudiantes() {
		return numeroTotalEstudiantes;
	}

	/**
	 * @param numeroTotalEstudiantes the numeroTotalEstudiantes to set
	 */
	public void setNumeroTotalEstudiantes(String numeroTotalEstudiantes) {
		this.numeroTotalEstudiantes = numeroTotalEstudiantes;
	}

	/**
	 * @return the numeroTotalParticipantes
	 */
	public String getNumeroTotalParticipantes() {
		return numeroTotalParticipantes;
	}

	/**
	 * @param numeroTotalParticipantes the numeroTotalParticipantes to set
	 */
	public void setNumeroTotalParticipantes(String numeroTotalParticipantes) {
		this.numeroTotalParticipantes = numeroTotalParticipantes;
	}

	/**
	 * @return the valorEjecucionPresupuestas
	 */
	public String getValorEjecucionPresupuestas() {
		return valorEjecucionPresupuestas;
	}

	/**
	 * @param valorEjecucionPresupuestas the valorEjecucionPresupuestas to set
	 */
	public void setValorEjecucionPresupuestas(String valorEjecucionPresupuestas) {
		this.valorEjecucionPresupuestas = valorEjecucionPresupuestas;
	}

	/**
	 * @return the restriccionModalidad
	 */
	public String getRestriccionModalidad() {
		return restriccionModalidad;
	}

	public boolean isEsJornadaDocente() {
		return esJornadaDocente;
	}

	public void setEsJornadaDocente(boolean esJornadaDocente) {
		this.esJornadaDocente = esJornadaDocente;
	}

	public boolean isEsPermisoMarco() {
		return esPermisoMarco;
	}

	public void setEsPermisoMarco(boolean esPermisoMarco) {
		this.esPermisoMarco = esPermisoMarco;
	}

	public boolean isMovilizacionEspecimenes() {
		return movilizacionEspecimenes;
	}

	public void setMovilizacionEspecimenes(boolean movilizacionEspecimenes) {
		this.movilizacionEspecimenes = movilizacionEspecimenes;
	}

	public boolean getTieneInformeAvanceRecolecta() {
		try {
			List<ProyectoInforme> listaInformesAvance = servicioGeneral.obtenerObjetosLimitado(ProyectoInforme.class,
					"select #id pi.id from ProyectoInforme pi " + "where pi.proyecto.id = '" + proyecto.getId()
							+ "' and pi.tipoInforme.id = '" + TipoInforme.INFORME_AVANCE
							+ "' and pi.recolectaEspecimenes = 'S'");
			if (listaInformesAvance != null && listaInformesAvance.size() > 0) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @return the productoEsperadoId
	 */
	public String getProductoEsperadoId() {
		return productoEsperadoId;
	}

	/**
	 * @param productoEsperadoId the productoEsperadoId to set
	 */
	public void setProductoEsperadoId(String productoEsperadoId) {
		this.productoEsperadoId = productoEsperadoId;
	}

	public List<VProductoSara> getProductosTmp() {
		return productosTmp;
	}

	/**
	 * @return the linkPublico
	 */
	public String getLinkPublico() {
		return linkPublico;
	}

	/**
	 * @param linkPublico the linkPublico to set
	 */
	public void setLinkPublico(String linkPublico) {
		this.linkPublico = linkPublico;
	}

	public String getExisteLinkPublico() {
		return existeLinkPublico;
	}

	public void setExisteLinkPublico(String existeLinkPublico) {
		this.existeLinkPublico = existeLinkPublico;
	}

	/**
	 * @return the objetivoId
	 */
	public String getObjetivoId() {
		return objetivoId;
	}

	/**
	 * @param objetivoId the objetivoId to set
	 */
	public void setObjetivoId(String objetivoId) {
		this.objetivoId = objetivoId;
	}

	/**
	 * @return the nombreDocumentoRevisionFacultad
	 */
	public String getNombreDocumentoRevisionFacultad() {
		return nombreDocumentoRevisionFacultad;
	}

	/**
	 * @param nombreDocumentoRevisionFacultad the nombreDocumentoRevisionFacultad to
	 *                                        set
	 */
	public void setNombreDocumentoRevisionFacultad(String nombreDocumentoRevisionFacultad) {
		this.nombreDocumentoRevisionFacultad = nombreDocumentoRevisionFacultad;
	}

	public boolean isBloquearAprobacionInforme() {
		return bloquearAprobacionInforme;
	}

	public boolean isEsPermisoMarcoAsignatura() {
		return esPermisoMarcoAsignatura;
	}

	public void setEsPermisoMarcoAsignatura(boolean esPermisoMarcoAsignatura) {
		this.esPermisoMarcoAsignatura = esPermisoMarcoAsignatura;
	}

	public boolean isValidoPorcEjecTecnicaAcum() {
		return validoPorcEjecTecnicaAcum;
	}

	public void setValidoPorcEjecTecnicaAcum(boolean validoPorcEjecTecnicaAcum) {
		this.validoPorcEjecTecnicaAcum = validoPorcEjecTecnicaAcum;
	}

	public boolean isValidoPorcEjecPresupuestoAcum() {
		return validoPorcEjecPresupuestoAcum;
	}

	public void setValidoPorcEjecPresupuestoAcum(boolean validoPorcEjecPresupuestoAcum) {
		this.validoPorcEjecPresupuestoAcum = validoPorcEjecPresupuestoAcum;
	}

	public UploadedFile getArchivoCargar() {
		return archivoCargar;
	}

	public void setArchivoCargar(UploadedFile archivoCargar) {
		this.archivoCargar = archivoCargar;
	}

	public SelectItem[] getSinoitems() {
		return sinoitems;
	}

	public void setSinoitems(SelectItem[] sinoitems) {
		this.sinoitems = sinoitems;
	}

	public ProductoTipo getProductoEsperadoSeleccionado() {
		return productoEsperadoSeleccionado;
	}

	public void setProductoEsperadoSeleccionado(ProductoTipo productoEsperadoSeleccionado) {
		this.productoEsperadoSeleccionado = productoEsperadoSeleccionado;
	}

	public SelectItem[] getMedicionImpactoItems() {
		return medicionImpactoItems;
	}

	public void setMedicionImpactoItems(SelectItem[] medicionImpactoItems) {
		this.medicionImpactoItems = medicionImpactoItems;
	}

	public SelectItem[] getTipoImpactoItems() {
		return tipoImpactoItems;
	}

	public void setTipoImpactoItems(SelectItem[] tipoImpactoItems) {
		this.tipoImpactoItems = tipoImpactoItems;
	}

	public String getTipoImpacto() {
		return tipoImpacto;
	}

	public void setTipoImpacto(String tipoImpacto) {
		this.tipoImpacto = tipoImpacto;
	}

	public String getMedicionImpacto() {
		return medicionImpacto;
	}

	public void setMedicionImpacto(String medicionImpacto) {
		this.medicionImpacto = medicionImpacto;
	}

	public Long getOdsSeleccionado() {
		return odsSeleccionado;
	}

	public void setOdsSeleccionado(Long odsSeleccionado) {
		this.odsSeleccionado = odsSeleccionado;
	}

	public List<ProyectoInformeImpacto> getListaImpacto() {
		return listaImpacto;
	}

	public void setListaImpacto(List<ProyectoInformeImpacto> listaImpacto) {
		this.listaImpacto = listaImpacto;
	}

	public List<ProyectoInformeImpacto> getListaImpactoBorrados() {
		return listaImpactoBorrados;
	}

	public void setListaImpactoBorrados(List<ProyectoInformeImpacto> listaImpactoBorrados) {
		this.listaImpactoBorrados = listaImpactoBorrados;
	}

	public ProyectoInformeImpacto getImpactoSeleccionado() {
		return impactoSeleccionado;
	}

	public void setImpactoSeleccionado(ProyectoInformeImpacto impactoSeleccionado) {
		this.impactoSeleccionado = impactoSeleccionado;
	}

	public void agregarImpacto() {

		if (esCadenaVacia(tipoImpacto)) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar el tipo de impacto", "");
			context.addMessage("datosGuardados", msg);
			return;
		}

		if (esCadenaVacia(medicionImpacto)) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar la medida de impacto",
					"");
			context.addMessage("datosGuardados", msg);
			return;
		}

		if (odsSeleccionado == null || odsSeleccionado.equals(0L)) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe un objetivo de desarrollo sostenible.", "");
			context.addMessage("datosGuardados", msg);
			return;
		}

		if (esCadenaVacia(detalleImpacto)) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar el detalle del impacto.",
					"");
			context.addMessage("datosGuardados", msg);
			return;
		}

		ProyectoInformeImpacto impacto = new ProyectoInformeImpacto();
		impacto.setInforme(proyectoInformeActual);
		impacto.setTipo(tipoImpacto);
		impacto.setMedicion(medicionImpacto);
		impacto.setDetalleImpacto(detalleImpacto);
		impacto.setOds(odsSeleccionado);
		try {

			String hql = "select dd from DominioDetalle dd where dd.identificador.id = '" + Dominio.DOMINIO_ODS
					+ "' and dd.identificador.tipo = '" + odsSeleccionado + "'";
			List ods = servicioGeneral.obtenerObjetos(DominioDetalle.class, hql);

			if (!esListaVacia(ods)) {
				DominioDetalle dd = (DominioDetalle) ods.get(0);
				impacto.setNombreOds(dd.getDescripcion());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		listaImpacto.add(impacto);

		tipoImpacto = "";
		medicionImpacto = "";
		detalleImpacto = "";
		odsSeleccionado = 0L;
	}

	public void eliminarImpacto() {
		listaImpactoBorrados.add(impactoSeleccionado);
		listaImpacto.remove(impactoSeleccionado);
	}

	public String getDetalleImpacto() {
		return detalleImpacto;
	}

	public void setDetalleImpacto(String detalleImpacto) {
		this.detalleImpacto = detalleImpacto;
	}

}