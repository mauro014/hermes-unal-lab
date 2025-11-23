package co.edu.unal.hermes.vista.proyectos.base;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.ArchivoInforme;
import co.edu.unal.hermes.modelo.Bien;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoInforme;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProductoSara;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoCompromiso;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.ProyectoInformeActividad;
import co.edu.unal.hermes.modelo.ProyectoInformeObjetivoEspecifico;
import co.edu.unal.hermes.modelo.ProyectoInformeResultado;
import co.edu.unal.hermes.modelo.ProyectoProducto;
import co.edu.unal.hermes.modelo.ResultadoCompromiso;
import co.edu.unal.hermes.modelo.SolicitudInforme;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.seguimiento.AlertaProyecto;
import co.edu.unal.hermes.modelo.seguimiento.ProyectoProrroga;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;
import co.edu.unal.hermes.modelo.seguimiento.TipoViaSolicitud;
import co.edu.unal.hermes.utils.Fecha;
import co.edu.unal.hermes.vista.general.ManejadorGeneral;

public class BaseManejadorRegistroInforme extends ManejadorGeneral {

	private static final long serialVersionUID = 1078026122978033074L;
	protected static final String RUTA_ADJUNTO = "/pages/Proyectos/informes/";

	protected static final String DOMINIO_OBJETIVO_SOCIO_EC = "OBJETIVO_SOCIO_ECONOMICO";
	protected static final String DOMINIO_AREA_CIENCIA = "AREA_CIENCIA";
	protected static final String DOMINIO_SUB_AREA_CIENCIA = "SUB_AREA_CIENCIA_FM";
	protected static final String DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE = "OBJETIVOS_DESARROLLO_SOSTENIBLE";
	protected Proyecto proyecto;
	protected String nombreInvestigador = "";
	protected String facultad;
	protected String dependencia;
	protected String resumenResultados;
	protected String resumenAvances;
	protected String dificultades;
	protected String conclusiones;
	protected ProyectoInforme proyectoInformeActual;
	protected Long tipoAccion;
	protected Long tipoInformeAccion = 0L;
	protected Long idInforme;
	protected boolean editable = false;
	protected boolean informeAvance = false;
	protected Long idProyecto;
	protected String nombre1 = "";
	protected String nombre2 = "";
	protected String nombre3 = "";
	protected String resumenTecnico = "";
	protected boolean mostrarConclusiones = false;
	protected List<ArchivoInforme> listaArchivos;
	protected Date fechaEntrega;
	protected Investigador investigadorActual;
	protected List<ProyectoProducto> listaProductosEsperados;
	protected List<Object[]> listaProductosRestantes;
	protected List<SelectItem> listaOpciones;
	protected List<SelectItem> listaPorcentajesEjecucion;
	protected boolean[] panelRenderError;
	protected String[] errores;
	protected String docEstudiante;
	protected List listaFormacionEsperada;
	protected SelectItem[] listaObjetivos;
	protected List<ObjetivoEspecifico> listaObjetivosBaseDatos;
	protected Estudiante estudiante = new Estudiante();
	protected List<Estudiante> listaEstudiantes;
	protected ObjetivoEspecifico objetivo;
	protected ProyectoProducto resultadoEsp;
	protected ProyectoProducto resultadoEsperadoNew;
	protected String resultadoObt;
	protected List<SelectItem> listaProductosEsperadosItem;
	protected List<SelectItem> listaResultadosItem;
	protected TipoDocumento tipoDocumento;
	protected SelectItem[] tipoDocumentoItem;
	protected List<ResultadoCompromiso> listaCompromisos;
	protected String productoLlave;
	protected String nombreProductoDos;
	protected String tipoProductoDos;
	protected List<ProductoSara> listaProductosSara;

	protected String productoNivel1;
	protected String productoNivel2;
	protected String productoNivel3;

	protected ProductoTipo productoNivel1Actual;
	protected ProductoTipo productoNivel2Actual;
	protected ProductoTipo productoNivel3Actual;

	protected SelectItem[] productoNivel1Item;
	protected SelectItem[] productoNivel2Item;
	protected SelectItem[] productoNivel3Item;

	protected List listaProductosNivel1;
	protected List listaProductosNivel2;
	protected List listaProductosNivel3;
	protected List productosConvocatoria;

	protected String placaBien;
	protected Bien bien;
	protected String comentariosCoordinadorLectura;

	Long idAlertaInforme = 0L;

	// finaliza ReqBie01

	protected String nombreProducto;
	protected ProductoSara productoSara;
	protected List<Long> listaProdEliminar;

	protected SelectItem[] productosSaraItems = new SelectItem[0];
	protected CorreoPlantilla correoActual;
	String cuerpoCorreo = "";

	private String revisionInforme;

	private String comentariosVIF;
	private String resultadosRevisionInforme;

	protected String porcentajeEjecucion;
	protected String porcentajeEjecucionPresupuestal;
	protected Date fechaDesdeInforme;
	protected Date fechaHastaInforme;

	protected String objetivoDesarrolloSosteniblePrimarioProd;
	protected SelectItem[] objetivosDesarrolloSostenibleItems;
	protected SelectItem[] odsItems;
	protected String areaCienciaProd;
	protected SelectItem[] areaCienciaItems;
	protected String subAreaCienciaProd;
	protected SelectItem[] subAreaCienciaItems;
	protected Date fechaEntregaProd;
	protected String lugarDepositoProd;
	protected String susceptibleProteccionProd;
	protected String montoEjecutado;
	private List listaTipoArchivo;
	private SelectItem[] tipoArchivoItem;
	protected TipoArchivo tipoArchivo;
	protected boolean validaPorcentajeEjecutadoPresupuesto = true;
	
	protected boolean mostrarMontoEjecExt = false;
	protected String montoEjecutadoExt;
	protected String porcentajeEjecucionPresupuestalExt;
	
	protected List<SelectItem> listaObjetivosItem;
	protected SelectItem[] objetivosDesarrolloSostenibleImpactoItems;
	private Date fechaFinalProyecto;
	
	protected boolean esConvocatoriaJovInvestigadores = false;

	public BaseManejadorRegistroInforme() {
		cargarRevisionInforme();
		cargarDatosSolicitud();
	}

	// finaliza ReqBie01

	public boolean validarDatosInforme(ProyectoInforme informeRevision, boolean mostrarMensajes) {
		boolean resultado;
		resultado = true;
		List<ProyectoInforme> informes = null;
		if (informeRevision.getId() != null) {
			informes = servicioGeneral.obtenerObjetos(ProyectoInforme.class,
					"from ProyectoInforme p where p.id = '" + informeRevision.getId() + "'");
		} else {
			informes = new ArrayList<ProyectoInforme>();
			informes.add(informeRevision);
		}
		if (informes != null && informes.size() > 0) {
			ProyectoInforme informe = informes.get(0);
			informe.setBienes(new TreeSet<Bien>(servicioGeneral.obtenerListaObjetosWhere(Bien.class,
					" WHERE b.informe.id = " + informeRevision.getId())));
			// Se carga el tipo informe
			Convocatoria convocatoria = (Convocatoria) informe.getProyecto().getModalidad();
			String tipoFormularioInforme = "";
			String restriccionModalidad = "";
			try {
				tipoFormularioInforme = convocatoria.getPadre().getTipoFormularioInformes();
				if (tipoFormularioInforme == null
						|| (tipoFormularioInforme != null && tipoFormularioInforme.equals(""))) {
					tipoFormularioInforme = ConvocatoriaPadre.TIPO_FORMULARIO_COMPLETO;
				}
			} catch (ClassCastException cce) {
				cce.printStackTrace();
			}
			if (informe.getProyecto() != null) {
				if (convocatoria != null && convocatoria.getRestriccion() != null) {
					restriccionModalidad = convocatoria.getRestriccion().getId();
				}
			}
			if (!convocatoria.isEsConvBiodiversidad()) {
				if (fechaDesdeInforme == null) {
					if (mostrarMensajes) {
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Debe ingresar la fecha desde la que se realiza el informe", "");
						context.addMessage("datosGuardados", msg);
						resultado = false;
					}
					resultado = false;
				}
				if (fechaHastaInforme == null) {
					if (mostrarMensajes) {
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Debe ingresar la fecha hasta la que se realiza el informe", "");
						context.addMessage("datosGuardados", msg);
						resultado = false;
					}
					resultado = false;
				}
				if (porcentajeEjecucion == null || porcentajeEjecucion.equals("0")) {
					if (mostrarMensajes) {
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"El porcentaje de ejecución técnica debe ser mayor a 0.", "");
						context.addMessage("datosGuardados", msg);
						resultado = false;
					}
					resultado = false;
				}
				if (esCadenaVacia(montoEjecutado)) {
					if (mostrarMensajes) {
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"El monto ejecutado no puede ser nulo.", "");
						if (mostrarMontoEjecExt) {
							msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"El monto ejecutado interno no puede ser nulo.", "");
						}
						context.addMessage("datosGuardados", msg);
						resultado = false;
					}
					resultado = false;
				}

				if (esCadenaVacia(montoEjecutadoExt) && mostrarMontoEjecExt) {
					if (mostrarMensajes) {
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"El monto ejecutado externo no puede ser nulo.", "");
						context.addMessage("datosGuardados", msg);
						resultado = false;
					}
					resultado = false;
				}
				
				if (mostrarMensajes) {
					if (informeRevision.getListaActividades() != null
							&& informeRevision.getListaActividades().size() > 0 
							&& informeRevision.getTipoInforme().getId().equals(ProyectoInforme.TIPO_INFORME_FINAL)) {
						for (int i = 0; i < informeRevision.getListaActividades().size(); i++) {
							ProyectoInformeActividad actividad = new ProyectoInformeActividad();
							actividad = informeRevision.getListaActividades().get(i);
							if (esCadenaVacia(actividad.getJustificacionAvance())) {
								resultado = false;
								
								FacesContext context = FacesContext.getCurrentInstance();
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"No todas las actividades tienen la justificación del avance, por favor diligencie los todos los campos.",
										"");
								context.addMessage("datosGuardados", msg);
								break;
							}
						}
					}

					if (informeRevision.getListaObjetivos() != null
							&& informeRevision.getListaObjetivos().size() > 0) {
						for (int i = 0; i < informeRevision.getListaObjetivos().size(); i++) {
							ProyectoInformeObjetivoEspecifico objetivo = new ProyectoInformeObjetivoEspecifico();
							objetivo = informeRevision.getListaObjetivos().get(i);
							if (esCadenaVacia(objetivo.getJustificacionAvance())) {
								resultado = false;
								FacesContext context = FacesContext.getCurrentInstance();
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"No todos los objetivos tienen la justificación del avance, por favor diligencie los todos los campos.",
										"");
								context.addMessage("datosGuardados", msg);
								break;
							}
						}
					}

					if (informeRevision.getListaResultados() != null
							&& informeRevision.getListaResultados().size() > 0) {
						for (int i = 0; i < informeRevision.getListaResultados().size(); i++) {
							ProyectoInformeResultado resultadoInf = new ProyectoInformeResultado();
							resultadoInf = informeRevision.getListaResultados().get(i);
							if (resultadoInf.getResultado()!=null && resultadoInf.getResultado().getFechaEntregable()!=null && resultadoInf.getResultado().getFechaEntregable().compareTo(getToday()) <= 0
									&& esCadenaVacia(resultadoInf.getJustificacion())) {
								resultado = false;
								FacesContext context = FacesContext.getCurrentInstance();
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Por favor diligencie el avance de los resultados cuya fecha ya se ha cumplido.",
										"");
								context.addMessage("datosGuardados", msg);
								break;
							}
						}
					}
				}
				
				if (!convocatoria.getId().equals(10L) && !convocatoria.getId().equals(2L) && !isConvocatoriasExcepcionSoporte()) {
					Long montoTotalProyecto = 0L;
					Long montoTotalProyectoExt = 0L;
					if(proyecto.getModalidad().getId().equals(Convocatoria.MODALIDAD_CONVOCATORIA_EXTERNA)){
						montoTotalProyecto = proyecto.getMontofinanciarCalculado();
					}else{
						String hqlFinanciacion = "select #tipoFinanciacion e.tipoFinanciacion from Convocatoria e, Proyecto p where e.id = p.modalidad.id and p.id = "
								+ proyecto.getId();
						List<Convocatoria> listaConvFin = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class, hqlFinanciacion);
						String tipoFin = listaConvFin.get(0).getTipoFinanciacion();
						montoTotalProyecto = servicioProyecto.obtenerMontoAprobadoProyecto(proyecto.getId(), tipoFin).longValue();
					}
					if ((porcentajeEjecucionPresupuestal == null || porcentajeEjecucionPresupuestal.equals("0")) && !montoTotalProyecto.equals(0L)) {
						if (mostrarMensajes) {
							FacesContext context = FacesContext.getCurrentInstance();
							FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"El porcentaje de ejecución presupuestal debe ser mayor a 0.", "");
							if(mostrarMontoEjecExt) {
								msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"El porcentaje de ejecución presupuestal interno debe ser mayor a 0.", "");
							}
							context.addMessage("datosGuardados", msg);
							resultado = false;
						}
						resultado = false;
					}
					montoTotalProyectoExt += proyecto.getMontofinanciarCalculado();
					if ((porcentajeEjecucionPresupuestalExt == null || porcentajeEjecucionPresupuestalExt.equals("0")) && !montoTotalProyectoExt.equals(0L) && mostrarMontoEjecExt) {
						if (mostrarMensajes) {
							FacesContext context = FacesContext.getCurrentInstance();
							FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"El porcentaje de ejecución presupuestal exerno debe ser mayor a 0.", "");
							context.addMessage("datosGuardados", msg);
							resultado = false;
						}
						resultado = false;
					}
				}
				if (validaPorcentajeEjecutadoPresupuesto == false) {
					resultado = false;
				}
			}
			// Si es informe de avance
			if (informe.getTipoInforme().getId() == 1
					&& tipoFormularioInforme == ConvocatoriaPadre.TIPO_FORMULARIO_COMPLETO) {

				//Pendiente validaciones
				
			} else {
				// Si es informe final
				if (tipoFormularioInforme == ConvocatoriaPadre.TIPO_FORMULARIO_COMPLETO
						&& !(informeRevision.getProyecto().getModalidad() != null
								&& informeRevision.getProyecto().getModalidad().getTipo() != null
								&& informeRevision.getProyecto().getModalidad().getTipo().getId().equals(TipoModalidad.BANCO_PROYECTOS))) {
					if (informe.getSipnosis() == null
							|| (informe.getSipnosis() != null && informe.getSipnosis().trim().equals(""))) {
						if (mostrarMensajes) {
							FacesContext context = FacesContext.getCurrentInstance();
							FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"La sipnosis del proyecto no puede estar en blanco", "");
							context.addMessage("datosGuardados", msg);
						}
						resultado = false;
					}
					if (informe.getResumenTecnico() == null
							|| (informe.getResumenTecnico() != null && informe.getResumenTecnico().trim().equals(""))) {
						if (mostrarMensajes) {
							FacesContext context = FacesContext.getCurrentInstance();
							FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"El resumen técnico no puede estar en blanco", "");
							context.addMessage("datosGuardados", msg);
						}
						resultado = false;
					}
					/*if (informe.getImpacto() == null
							|| (informe.getImpacto() != null && informe.getImpacto().trim().equals(""))) {
						if (mostrarMensajes) {
							FacesContext context = FacesContext.getCurrentInstance();
							FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"El impacto del proyecto no puede estar en blanco", "");
							context.addMessage("datosGuardados", msg);
						}
						resultado = false;
					}*/
					/*if (informe.getConclusiones() == null
							|| (informe.getConclusiones() != null && informe.getConclusiones().trim().equals(""))) {
						if (mostrarMensajes) {
							FacesContext context = FacesContext.getCurrentInstance();
							FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Las conclusiones del proyecto no pueden estar en blanco", "");
							context.addMessage("datosGuardados", msg);
						}
						resultado = false;
					}*/
					if (informe.getCompraEquipos().equals("SI") && informe.getBienesList().size() == 0) {
						if (mostrarMensajes) {
							FacesContext context = FacesContext.getCurrentInstance();
							FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Ingrese los equipos adquiridos", "");
							context.addMessage("datosGuardados", msg);
						}
						resultado = false;
					}
					if (restriccionModalidad != null && (restriccionModalidad.equals(Convocatoria.TIPO_EVENTO_1)
							|| restriccionModalidad.equals(Convocatoria.TIPO_EVENTO_2))) {
						if (informe.getTotalValorEjecutado() == null && (informe.getTotalValorEjecutado() != null
								&& informe.getTotalValorEjecutado() <= 0L)) {
							if (mostrarMensajes) {
								FacesContext context = FacesContext.getCurrentInstance();
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Debe ingresar un valor de ejecución positivo.", "");
								context.addMessage("datosGuardados", msg);
							}
							resultado = false;
						}
						if (informe.getNumeroParticipantesEvento() == null
								&& (informe.getNumeroParticipantesEvento() != null
										&& informe.getNumeroParticipantesEvento() <= 0L)) {
							if (mostrarMensajes) {
								FacesContext context = FacesContext.getCurrentInstance();
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Debe ingresar un numero de participantes positivo.", "");
								context.addMessage("datosGuardados", msg);
							}
							resultado = false;
						}
						if (informe.getNumeroEstudiantesEvento() == null
								&& (informe.getNumeroEstudiantesEvento() != null
										&& informe.getNumeroEstudiantesEvento() <= 0L)) {
							if (mostrarMensajes) {
								FacesContext context = FacesContext.getCurrentInstance();
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Debe ingresar un numero de estudiantes positivo.", "");
								context.addMessage("datosGuardados", msg);
							}
							resultado = false;
						}
						if (informe.getNumeroExternosEvento() == null && (informe.getNumeroExternosEvento() != null
								&& informe.getNumeroExternosEvento() <= 0L)) {
							if (mostrarMensajes) {
								FacesContext context = FacesContext.getCurrentInstance();
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Debe ingresar un numero de externos positivo.", "");
								context.addMessage("datosGuardados", msg);
							}
							resultado = false;
						}
					}
				}
			}
		}
		return resultado;
	}

	private void cargarDatosSolicitud() {

		if (sesion.getAttribute("idAlertaInforme") != null) {
			idAlertaInforme = (Long) sesion.getAttribute("idAlertaInforme");
		}
	}

	/**
	 * Se carga variable de sesión para saber si el informe esta siendo revisado
	 * por alguien en la UAB, VIF o sede.
	 */
	private void cargarRevisionInforme() {
		if (sesion.getAttribute("revisionInformes") != null) {
			String valorRevision = (String) sesion.getAttribute("revisionInformes");
			if (StringUtils.isNotEmpty(valorRevision)) {
				this.revisionInforme = valorRevision;
				sesion.removeAttribute("revisionInformes");
			} else {
				this.revisionInforme = "";
			}
		}
	}

	private boolean validarCalificacionInforme(ProyectoInforme pinAux) {
		if (pinAux.getIdEstudiante() != null && !pinAux.getIdEstudiante().equals("") && pinAux.getCalificacion() == null
				|| (pinAux.getCalificacion() != null && pinAux.getCalificacion().equals(""))) {
			return false;
		}
		return true;
	}

	public String cerrarInforme(ProyectoInforme informeSeleccionado, Proyecto proyectoActual, boolean mostrarMensajes)
			throws SQLException {

		TipoViaSolicitud tipoViaSolicitud = servicioSolicitudes
				.buscarTipoViaSolicitudPorId(TipoViaSolicitud.VIA_HERMES);

		ProyectoInforme pin = informeSeleccionado;
		List listaEstadoInforme;
		List listaProyecto = new ArrayList();
		listaEstadoInforme = new ArrayList();
		EstadoInforme ein = new EstadoInforme();
		Date fecha = new Date();
		Solicitud sol = new Solicitud();
		String mensajeCreacionInforme = "";

		listaEstadoInforme = servicioGeneral
				.obtenerListaObjetos("EstadoInforme where id ='" + EstadoInforme.ENVIADO + "'");

		ein = (EstadoInforme) listaEstadoInforme.get(0);
		boolean irPrincipal = true;
		if (validarDatosInforme(pin, mostrarMensajes)) {
			if (pin.getTipoInforme().getId().equals(TipoInforme.INFORME_AVANCE)) {
				if (pin.getEstadoInforme().getId().equals(EstadoInforme.INGRESANDO)
						|| pin.getEstadoInforme().getId().equals(EstadoInforme.DEVUELTO)) {
					List listaProyectoInforme = servicioGeneral
							.obtenerListaObjetos("ProyectoInforme where id ='" + pin.getId() + "'");
					ProyectoInforme pinAux = (ProyectoInforme) listaProyectoInforme.get(0);
					if (!validarCalificacionInforme(pinAux) && esConvocatoriaJovInvestigadores) {
						mensajeCreacionInforme = "No se puede enviar el informe de avance debido a que aun no ha realizado la calificación del estudiante."
								+ "\nPor favor seleccione la opción editar y califique al estudiante dentro del formulario de registro.";
						mensajeError(mensajeCreacionInforme);
						return "";
					}
					// Se da por cumplido el compromiso
					String hql = "from ProyectoCompromiso WHERE proyecto = " + pin.getProyecto().getId()
							+ " AND tipoInforme = " + pin.getTipoInforme().getId() + " AND cumplido = '"
							+ ProyectoCompromiso.NO_CUMPLIDO + "' order by fechaVencimiento asc";
					List<ProyectoCompromiso> listaProyectoCompromiso = servicioGeneral
							.obtenerObjetos(ProyectoCompromiso.class, hql);
					if (listaProyectoCompromiso != null && listaProyectoCompromiso.size() > 0) {
						ProyectoCompromiso proyectoCompromisoExistente = (ProyectoCompromiso) listaProyectoCompromiso
								.get(0);
						proyectoCompromisoExistente.setCumplido(ProyectoCompromiso.CUMPLIDO);
						servicioGeneral.guardarObjeto(proyectoCompromisoExistente);
						pinAux.setProyectoCompromiso(proyectoCompromisoExistente);
					}
					pin.setEstadoInforme(ein);
					pin.setFechaGeneracion(fecha);
					pin.setFechaLectura(null);

					pinAux.setEstadoInforme(pin.getEstadoInforme());
					pinAux.setFechaGeneracion(pin.getFechaGeneracion());

					pin.setDependenciaInforme(pinAux.getDependenciaInforme());
					if (pinAux.getDependenciaInforme() == null) {
						Persona pe = this.servicioProyecto
								.obtenerInvestigadorPrincipalXProyecto(pin.getProyecto().getId());
						InvestigadorInterno ii = (InvestigadorInterno) pe;
						pin.setDependenciaInforme(ii.getDependencia().getFacultad().getId());
						pinAux.setDependenciaInforme(ii.getDependencia().getFacultad().getId());
					}
					try {
						Persona personaActual = (Persona) sesion.getAttribute("persona");
						pinAux.setIdResponsable(personaActual.getId().getDocumento());
						pinAux.setTipoDocResponsable(personaActual.getId().getTipoDocumento());
					} catch (Exception e) {
						e.printStackTrace();
					}
					servicioGeneral.guardarObjeto(pinAux);

					try {
						servicioGeneral.guardarObjeto(crearHistoricoEstadoInforme(pinAux, ""));
					} catch (NullPointerException npe) {
						npe.printStackTrace();
						System.out.println("Error al guardar historico");
					}

					listaEstadoInforme = new ArrayList();
					sol.setFecha(fecha);
					sol.setRespuesta("");

					sol.setDescripcion("Informe de Avance");
					TipoSolicitud tipoSolicitud = servicioSolicitudes
							.buscarTipoSolicitudPorId(TipoSolicitud.INFORME_AVANCE);
					sol.setTipoSolicitud(tipoSolicitud);

					listaProyecto = servicioGeneral
							.obtenerListaObjetos("Proyecto where id ='" + pin.getProyecto().getId() + "'");
					Proyecto pr = (Proyecto) listaProyecto.get(0);

					sol.setProyecto(pr);
					sol.setTipoViaSolicitud(tipoViaSolicitud);
					servicioGeneral.guardarObjeto(sol);

					SolicitudInforme solInf = new SolicitudInforme();
					Long idSol = new Long("0");

					SimpleDateFormat spd = new SimpleDateFormat("dd");
					SimpleDateFormat spm = new SimpleDateFormat("MM");
					SimpleDateFormat spy = new SimpleDateFormat("yyyy");
					idSol = sol.getId();
					solInf.setInformeID(pin.getId());
					solInf.setSolicitudID(idSol);

					servicioGeneral.guardarObjeto(solInf);

					Proyecto pr1 = new Proyecto();

					pr1 = this.servicioProyecto.obtenerProyecto(pin.getProyecto().getId(),
							ProyectoDAOHibernate.ASESORES);

					List asesores = new ArrayList();
					asesores.addAll(pr1.getAsesores());

					if (!asesores.isEmpty()) {
						AlertaProyecto ale = new AlertaProyecto();
						ale.setEstado("P");
						ale.setFechaGenera(fecha);
						ale.setMensaje(sol.getDescripcion());
						ale.setProyecto(pr);
						ale.setSolicitud(sol);
						ale.setAsesor((Persona) asesores.get(0));
						servicioGeneral.guardarObjeto(ale);
					}

					correoActual = cargarPlantilla(61);// 42
					editarCorreoCierre(pin.getProyecto());
					Correo correo = new Correo();
					correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
					Persona pe = servicioProyecto.obtenerCoordinadorProyecto(pin.getProyecto().getId());
					String dirCorreo = pe.getEmail();
					correo.adicionarDireccion(dirCorreo);
					//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
					correo.adicionarCopiaOculta(new String(pe.getEmail()));
					correo.setAsunto(correoActual.getAsunto());
					correo.setCuerpo(cuerpoCorreo);
					servicioCorreo.enviarCorreoSolicitud(correo);

					mensajeCreacionInforme = "Informe de avance enviado correctamente.";
					FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage mensaje = new FacesMessage("Informe de avance enviado correctamente");
					context.addMessage("datosGuardados", mensaje);
				} else {
					mensajeCreacionInforme = "No se puede enviar el informe de avance, debido a que el informe ya esta cerrado.";
					FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"El coinvestigador ya se encuentra en la lista", "");
					context.addMessage("datosGuardados", msg);
				}
			}

			if (pin.getTipoInforme().getId().equals(TipoInforme.INFORME_FINAL)) {
				boolean informeFinalPasaPorFacultad = false;
				InvestigadorInterno ii = null;
				/**
				 * Se controla que no se hagan consultas innecesarias, puesto
				 * que los proyectos de jornada docente no pasan por la facultad
				 */
				if (proyectoActual.getEsJornadaDocente() == null || proyectoActual.getEsJornadaDocente().equals("N")) {
					List<Parametro> listaParametro = this.servicioGeneral.obtenerListaObjetosWhere(Parametro.class,
							"WHERE p.nombre = 'INFORME_FINAL_PASA_FACULTAD'");

					try {
						if (listaParametro != null && listaParametro.size() == 1) {
							Parametro paActual = listaParametro.get(0);
							if (paActual != null) {
								Persona pe;
								pe = this.servicioProyecto
										.obtenerInvestigadorPrincipalXProyecto(pin.getProyecto().getId());
								ii = (InvestigadorInterno) pe;
								String sede = String.valueOf(ii.getDependencia().getSede().getId());
								String[] sedes = paActual.getValor().split(",");
								if (sedes != null && sedes.length > 0) {
									for (int i = 0; i < sedes.length; i++) {
										if (sedes[i].equals(sede)) {
											informeFinalPasaPorFacultad = true;
											break;
										}
									}
								}
							}
						}
					} catch (Exception e) {
						System.out.println(e.toString());
					}
					if (informeFinalPasaPorFacultad) {
						Convocatoria convocatoria = (Convocatoria) pin.getProyecto().getModalidad();
						if (convocatoria.getPadre().getInformeCoordinador() != null) {
							String informeCoordinador = convocatoria.getPadre().getInformeCoordinador();
							if (informeCoordinador.equals("S")) {
								informeFinalPasaPorFacultad = false;
							}
						}
					}
				}
				if (pin.getEstadoInforme().getId().equals(EstadoInforme.INGRESANDO)
						|| pin.getEstadoInforme().getId().equals(EstadoInforme.DEVUELTO)) {
					this.proyecto = pin.getProyecto();

					if (esListaVacia(listaProductosSara)) {
						listaProductosSara = new ArrayList<ProductoSara>();
						List<ProductoSara> listaProductoSara = servicioGeneral.obtenerObjetos(
								"select e from ProductoSara e where e.informe.id = '" + pin.getId() + "'");
						if (listaProductoSara != null && listaProductoSara.size() > 0) {
							for (int i = 0; i < listaProductoSara.size(); i++) {
								ProductoSara ps = (ProductoSara) listaProductoSara.get(i);
								listaProductosSara.add(ps);
							}
						}
					}

					cargarProductosEsperados();
					crearListaItemsProductosEsperados();
					if (!esListaVacia(listaProductosRestantes) && listaProductosRestantes.size() > 1) {
						mensajeCreacionInforme = "No se puede enviar el informe debido a que no se ha registrado toda la"
								+ "información relacionada con los productos académicos del proyecto.";
						mensajeError(mensajeCreacionInforme);
						return "";
					}

					List listaProyectoInforme = servicioGeneral
							.obtenerListaObjetos("ProyectoInforme where id ='" + pin.getId() + "'");
					ProyectoInforme pinAux = (ProyectoInforme) listaProyectoInforme.get(0);
					if (!validarCalificacionInforme(pinAux) && esConvocatoriaJovInvestigadores) {
						mensajeCreacionInforme = "No se puede enviar el informe debido a que aun no ha realizado la calificación del estudiante."
								+ "\nPor favor seleccione la opción editar y califique al estudiante dentro del formulario de registro.";
						mensajeError(mensajeCreacionInforme);
						return "";
					}
					// Se da por cumplido el compromiso
					String hql = "from ProyectoCompromiso WHERE proyecto = " + pin.getProyecto().getId()
							+ " AND tipoInforme = " + pin.getTipoInforme().getId() + " AND cumplido = '"
							+ ProyectoCompromiso.NO_CUMPLIDO + "' order by fechaVencimiento asc";
					List<ProyectoCompromiso> listaProyectoCompromiso = servicioGeneral
							.obtenerObjetos(ProyectoCompromiso.class, hql);
					if (listaProyectoCompromiso != null && listaProyectoCompromiso.size() > 0) {
						ProyectoCompromiso proyectoCompromisoExistente = (ProyectoCompromiso) listaProyectoCompromiso
								.get(0);
						proyectoCompromisoExistente.setCumplido(ProyectoCompromiso.CUMPLIDO);
						servicioGeneral.guardarObjeto(proyectoCompromisoExistente);
						pinAux.setProyectoCompromiso(proyectoCompromisoExistente);
					}
					pin.setEstadoInforme(ein);
					pin.setFechaGeneracion(fecha);
					pinAux.setEstadoInforme(pin.getEstadoInforme());
					pinAux.setFechaGeneracion(pin.getFechaGeneracion());
					pin.setDependenciaInforme(pinAux.getDependenciaInforme());
					try {
						Persona personaActual = (Persona) sesion.getAttribute("persona");
						pinAux.setIdResponsable(personaActual.getId().getDocumento());
						pinAux.setTipoDocResponsable(personaActual.getId().getTipoDocumento());
					} catch (Exception e) {
						e.printStackTrace();
					}
					servicioGeneral.guardarObjeto(pinAux);

					try {
						servicioGeneral.guardarObjeto(crearHistoricoEstadoInforme(pinAux, ""));
					} catch (NullPointerException npe) {
						npe.printStackTrace();
						System.out.println("Error al guardar historico");
					}

					if (!informeFinalPasaPorFacultad) {
						listaEstadoInforme = new ArrayList();

						if (pin.getEstadoInforme().getId().equals(EstadoInforme.ENVIADO)) {
							sol.setFecha(fecha);
							sol.setRespuesta("");
							sol.setDescripcion("Informe Final");
							TipoSolicitud tipoSolicitud = servicioSolicitudes
									.buscarTipoSolicitudPorId(TipoSolicitud.INFORME_FINAL);
							sol.setTipoSolicitud(tipoSolicitud);

							listaProyecto = servicioGeneral
									.obtenerListaObjetos("Proyecto where id ='" + pin.getProyecto().getId() + "'");
							Proyecto pr = (Proyecto) listaProyecto.get(0);

							sol.setProyecto(pr);
							sol.setTipoViaSolicitud(tipoViaSolicitud);
							servicioGeneral.guardarObjeto(sol);

							SolicitudInforme solInf = new SolicitudInforme();
							Long idSol = new Long("0");

							SimpleDateFormat spd = new SimpleDateFormat("dd");
							SimpleDateFormat spm = new SimpleDateFormat("MM");
							SimpleDateFormat spy = new SimpleDateFormat("yyyy");

							idSol = servicioGeneral.consultaUltimoSequenciaSolicitud(sol.getTipoSolicitud().getId(),
									sol.getProyecto().getId(), Long.parseLong(spy.format(sol.getFecha())),
									Long.parseLong(spm.format(sol.getFecha())),
									Long.parseLong(spd.format(sol.getFecha())));
							solInf.setInformeID(pin.getId());
							solInf.setSolicitudID(idSol);

							servicioGeneral.guardarObjeto(solInf);

							Proyecto pr1 = new Proyecto();

							pr1 = this.servicioProyecto.obtenerProyecto(pin.getProyecto().getId(),
									ProyectoDAOHibernate.ASESORES);

							List asesores = new ArrayList();
							asesores.addAll(pr1.getAsesores());

							if (!asesores.isEmpty()) {
								AlertaProyecto ale = new AlertaProyecto();
								ale.setEstado("P");
								ale.setFechaGenera(fecha);
								ale.setMensaje(sol.getDescripcion());
								ale.setProyecto(pr);
								ale.setSolicitud(sol);
								ale.setAsesor((Persona) asesores.get(0));
								servicioGeneral.guardarObjeto(ale);
							}

							correoActual = cargarPlantilla(62); // Correo
																// informe
																// final
																// Coordinador
							editarCorreoCierre(pin.getProyecto());
							Correo correo = new Correo();
							correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
							Persona pe = servicioProyecto.obtenerCoordinadorProyecto(pin.getProyecto().getId());
							String dirCorreo = pe.getEmail();
							correo.adicionarDireccion(dirCorreo);
							//correo.adicionarDireccion(Correo.CORREO_HERMES);
							correo.adicionarCopiaOculta(new String(pe.getEmail()));
							correo.setAsunto(correoActual.getAsunto());
							correo.setCuerpo(cuerpoCorreo.replaceAll("<<ID>>", pin.getProyecto().getId().toString()));
							servicioCorreo.enviarCorreo(correo);
							String investigador = "";
							if (ii != null) {
								investigador = ii.getNombre1() + " " + ii.getApellido1() + " " + ii.getApellido2();
							}
							Proyecto solicitudBiodiversidad = servicioProyecto
									.obtenerProyectoCodigoDib(proyectoActual.getId().toString());
							enviarCorreoBiodiversidad(proyectoActual, solicitudBiodiversidad, investigador);
						}
					} else {
						try {
							if (ii != null && ii.getDependencia() != null) {
								String consult = "select i from InvestigadorInterno i, " + " PersonaRol pr "
										+ " where i.id.documento= pr.documento "
										+ " and i.id.tipoDocumento= pr.tipoDocumento "
										+ " and pr.nombre = 'AF' and i.dependencia2.facultad.id = '"
										+ ii.getDependencia().getFacultad().getId() + "' "
										+ " and i.dependencia2.facultad.esFacultad = 'Y' ";
								List listaCorreoEncargado = servicioGeneral.obtenerObjetos(consult);
								if (listaCorreoEncargado != null) {
									correoActual = cargarPlantilla(191); // Correo
																			// informe
																			// final
																			// rol
																			// VIF
									editarCorreoCierre(pin.getProyecto());
									Correo correo = new Correo();
									correo.setOrigen(Correo.CORREO_HERMES);
									Iterator it = listaCorreoEncargado.iterator();
									while (it.hasNext()) {
										InvestigadorInterno asesor = (InvestigadorInterno) it.next();
										String dirCorreo = asesor.getEmail();
										correo.adicionarDireccion(dirCorreo);
									}
									//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
									correo.setCuerpo(cuerpoCorreo);
									String investigador = "";
									if (ii != null) {
										investigador = ii.getNombre1() + " " + ii.getApellido1() + " "
												+ ii.getApellido2();
										correo.getCuerpo().replaceAll("<<INVESTIGADOR>>", investigador);
									}

									correo.setAsunto(correoActual.getAsunto());
									servicioCorreo.enviarCorreo(correo);

									correoActual = cargarPlantilla(228); // Correo
																			// informe
									// final coordinador
									editarCorreoCierre(pin.getProyecto());
									correo = new Correo();
									correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
									Persona pe = servicioProyecto.obtenerCoordinadorProyecto(pin.getProyecto().getId());
									String dirCorreo = pe.getEmail();
									correo.adicionarDireccion(dirCorreo);
									//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
									correo.adicionarCopiaOculta(new String(pe.getEmail()));
									correo.setAsunto(correoActual.getAsunto());
									correo.setCuerpo(cuerpoCorreo);
									servicioCorreo.enviarCorreo(correo);

									correoActual = cargarPlantilla(289); // Correo
									// informe
									// final investigador
									editarCorreoCierre(pin.getProyecto());
									correo = new Correo();
									correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
									Investigador inv = servicioProyecto
											.obtenerInvestigadorPrincipalXProyecto(pin.getProyecto().getId());
									String dirCorreoInvPrincipal = inv.getEmail();
									correo.adicionarDireccion(dirCorreoInvPrincipal);
									//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
									correo.adicionarCopiaOculta(new String(pe.getEmail()));
									correo.setAsunto(correoActual.getAsunto());
									correo.setCuerpo(cuerpoCorreo);
									servicioCorreo.enviarCorreo(correo);

									Proyecto solicitudBiodiversidad = servicioProyecto
											.obtenerProyectoCodigoDib(proyectoActual.getId().toString());
									enviarCorreoBiodiversidad(proyectoActual, solicitudBiodiversidad, investigador);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					mensajeCreacionInforme = "Informe final enviado correctamente.";
					FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage("Informe final enviado correctamente");
					context.addMessage("datosGuardados", msg);
				} else {
					mensajeCreacionInforme = "No se puede enviar el informe final, debido a que el informe ya esta cerrado.";
					FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"No se puede enviar el informe final, debido a que el informe ya esta cerrado", "");
					context.addMessage("datosGuardados", msg);
				}
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El informe no puede ser enviado dado que no se ha registrado toda la información obligatoria.",
					"");
			context.addMessage("datosGuardados", msg);
			irPrincipal = false;
		}
		if (irPrincipal) {
			sesion.removeAttribute("manejadorPrincipalInforme");
			return "principalInformes";
		} else {
			return "";
		}
	}

	public void enviarCorreoBiodiversidad(Proyecto proyectoActual, Proyecto solicitudBiodiversidad,
			String investigador) {

		if (solicitudBiodiversidad != null) {
			if (solicitudBiodiversidad.getEsPermisoMarco() || solicitudBiodiversidad.getEsPermisoMarcoAsignatura()) {
				Convocatoria conv = new Convocatoria();
				conv = servicioModalidad.obtenerConvocatoria(proyectoActual.getModalidad().getId());
				if (conv != null) {
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
						//correoCoordBio.adicionarCopiaOculta(Correo.CORREO_HERMES);

						CorreoPlantilla correoPlantillaBio = cargarPlantilla(311);
						correoCoordBio.setAsunto(
								correoPlantillaBio.getAsunto().replaceAll("<<ID>>", proyectoActual.getId().toString()));

						cuerpo = correoPlantillaBio.getCuerpo();

						String nombre = Matcher.quoteReplacement(proyectoActual.getNombre());
						cuerpo = cuerpo.replaceAll("<<INVESTIGADOR>>", investigador);
						cuerpo = cuerpo.replaceAll("<<TITULO>>", nombre);
						cuerpo = cuerpo.replaceAll("<<ID>>", String.valueOf(proyectoActual.getId()));
						cuerpo = cuerpo.replaceAll("<<CONVOCATORIA>>", conv.getTitulo());

						// proyecto que corresponde a la solicitud de
						// biodiversidad
						cuerpo = cuerpo.replaceAll("<<CODIGO_SOLICITUD_BIODIVERSIDAD>>",
								solicitudBiodiversidad.getId().toString());
						cuerpo = cuerpo.replaceAll("<<NOMBRE_SOLICITUD_BIODIVERSIDAD>>",
								solicitudBiodiversidad.getNombre());
						cuerpo = cuerpo.replaceAll("<<TIPO_SOLICITUD_BIODIVERSIDAD>>", tipoSolicitudBiodiversidad);

						correoCoordBio.setCuerpo(cuerpo);
						servicioCorreo.enviarCorreo(correoCoordBio);
					}
				}
			}
		}
	}

	public String editarCorreoCierre(Proyecto proyectoUno) {

		try {
			String correo = correoActual.getCuerpo().replaceAll("<<FECHA>>", Fecha.fechaActual());
			Persona pe = this.servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoUno.getId());
			List inv = servicioGeneral.obtenerListaObjetosWhere("Persona p", "where p.id.documento='"
					+ pe.getId().getDocumento() + "' and p.id.tipoDocumento='" + pe.getId().getTipoDocumento() + "'");
			String investigador = "";
			if (inv != null && inv.size() > 0) {
				Persona e = (Persona) inv.get(0);
				investigador = e.getNombre1() + " " + e.getApellido1() + " " + e.getApellido2();
				correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);
			}

			correo = correo.replaceAll("<<TITULO>>", proyectoUno.getNombre());
			correo = correo.replaceAll("<<CODIGO>>", proyectoUno.getId().toString());

			ProyectoInforme proyectoInforme = servicioGeneral.obtenerObjetos(ProyectoInforme.class,
					"from ProyectoInforme where id ='" + proyectoInformeActual.getId().toString() + "'").get(0);

			correo = correo.replaceAll("<<INFORME>>", proyectoInforme.getId().toString());
			if (proyectoInforme.getNoLectura() != null) {
				correo = correo.replaceAll("<<OBSERVACIONES>>", proyectoInforme.getNoLectura());
			}

			cuerpoCorreo = correo;

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "";
	}

	protected void cargarProductosEsperados() {
		listaProductosEsperados = servicioProyecto.getProductosPorProyecto(this.proyecto.getId());
		listaProductosEsperadosItem = new ArrayList<SelectItem>();

		ProyectoProducto productoSin = new ProyectoProducto();

		// Producto adicional
		productoSin.setId(0L);
		productoSin.setProyecto(this.proyecto);
		ProductoTipo pt = new ProductoTipo();
		pt.setId("0");
		pt.setNombre("Resultado Adicional");
		productoSin.setProducto(pt);
		productoSin.setDescripcion("Resultado adicional");
		listaProductosEsperados.add(productoSin);

	}
	
	private void cargarListaProductosRestantes() {

		listaProductosRestantes = new ArrayList<Object[]>();

		// Se suman los productos del listado de productos esperados
		if (!esListaVacia(listaProductosEsperados)) {
			Iterator<ProyectoProducto> i = listaProductosEsperados.iterator();
			while (i.hasNext()) {
				ProyectoProducto proyectoProducto = i.next();
				Object[] productoCuenta = buscarEnListadoRestantes(proyectoProducto.getId().toString());

				if (productoCuenta != null) {
					int cuenta = (Integer) productoCuenta[2];
					cuenta += proyectoProducto.getCantidad();
					productoCuenta[2] = cuenta;
				} else {
					Object[] productoCuentaNuevo = new Object[4];
					productoCuentaNuevo[0] = proyectoProducto.getId().toString();
					productoCuentaNuevo[1] = proyectoProducto.getProducto().getNombre();
					productoCuentaNuevo[2] = proyectoProducto.getCantidad();
					productoCuentaNuevo[3] = proyectoProducto;
					listaProductosRestantes.add(productoCuentaNuevo);
				}
			}
		}

		// Se restan los que ya fueron agregados
		if (!esListaVacia(listaProductosSara)) {
			Iterator<ProductoSara> i = listaProductosSara.iterator();
			while (i.hasNext()) {
				ProductoSara productoSara = i.next();

				Object[] productoCuenta = buscarEnListadoRestantes(productoSara.getProductoResultadoEsperado());
				if (productoCuenta != null) {
					int cuenta = (Integer) productoCuenta[2];
					cuenta--;
					if (cuenta > 0) {
						productoCuenta[2] = cuenta;
					} else {
						listaProductosRestantes.remove(productoCuenta);
					}
				}
			}
		}

	}

	private Object[] buscarEnListadoRestantes(String valor) {
		Iterator<Object[]> j = listaProductosRestantes.iterator();
		while (j.hasNext()) {
			Object[] productoCuenta = j.next();
			String idCuenta = (String) productoCuenta[0];
			String nombreCuenta = (String) productoCuenta[1];
			if (idCuenta.equals(valor) || nombreCuenta.equals(valor)) {
				return productoCuenta;
			}
		}
		return null;
	}
	
	protected ProyectoProducto buscarIdProductoRestantes(String valor) {
		Iterator<Object[]> j = listaProductosRestantes.iterator();
		while (j.hasNext()) {
			Object[] productoCuenta = j.next();
			String idCuenta = (String) productoCuenta[0];
			if (idCuenta.equals(valor)) {
				return ProyectoProducto.class.cast(productoCuenta[3]);
			}
		}
		return null;
	}

	protected void crearListaItemsProductosEsperados() {
		cargarListaProductosRestantes();

		listaProductosEsperadosItem.clear();

		if (!esListaVacia(listaProductosRestantes)) {
			for (int i = 0; i < listaProductosRestantes.size(); i++) {
				Object[] productoCuenta = listaProductosRestantes.get(i);
				String id = (String) productoCuenta[0];
				String nombre = (String) productoCuenta[1];
				int cuenta = (Integer) productoCuenta[2];
				listaProductosEsperadosItem.add(new SelectItem(id, nombre + " - (" + cuenta + " por agregar)"));
			}
			Object[] productoCuenta = listaProductosRestantes.get(0);
			resultadoEsp = (ProyectoProducto) productoCuenta[3];
		}

	}

	public String consultarHistoricoEstadoInforme(ProyectoInforme pi) {
		sesion.setAttribute("pin_id", pi.getId());
		sesion.setAttribute("buscarHistoricoInforme", true);
		sesion.removeAttribute("manejadorConsultaHistoricos");
		return "consultarHistoricoInforme";
	}

	public String atras() {

		Long idEsCoordinador = (Long) super.sesion.getAttribute("esCoordinador");

		if (StringUtils.isNotEmpty(revisionInforme)) {
			if ("UAB".equals(revisionInforme)) {
				sesion.removeAttribute("manejadorAvalInformeUab");
				return "informesJornadaDocente";
			} else if ("VIF".equals(revisionInforme)) {
				sesion.removeAttribute("manejadorAvalInformeFacultad");
				return "consultaInformesAval";
			} else if ("C".equals(revisionInforme)) {
				return "inbox";
			}
		} else if (idEsCoordinador == null) {
			sesion.removeAttribute("manejadorPrincipalInforme");
			return "volverPrincipalInforme";
		} else {
			if (idEsCoordinador.compareTo(new Long("1")) == 0) {
				return "volverPrincipalInformeProyectoCoordinador";

			} else {
				sesion.removeAttribute("manejadorPrincipalInforme");
				return "volverPrincipalInforme";
			}
		}
		return "";
	}

	public String revisarAvalVIF() {

		ProyectoInforme proyectoInformeDisco = new ProyectoInforme();

		TipoViaSolicitud tipoViaSolicitud = servicioSolicitudes
				.buscarTipoViaSolicitudPorId(TipoViaSolicitud.VIA_HERMES);

		// Estado informe para asignar al informe
		EstadoInforme ein = new EstadoInforme(EstadoInforme.ACEPTADO_FACULTAD);

		// Se consulta toda la información del informe actual
		List<ProyectoInforme> listaConsultaInformes = servicioGeneral.obtenerObjetos(ProyectoInforme.class,
				"from ProyectoInforme  where id ='" + proyectoInformeActual.getId() + "'");

		proyectoInformeDisco = (ProyectoInforme) listaConsultaInformes.get(0);

		// Se asigna estado al informe actual
		proyectoInformeDisco.setEstadoInforme(ein);
		proyectoInformeDisco.setFechaAprobacion(new Date());
		
		if (montoEjecutado != null && !esCadenaVacia(montoEjecutado)
				&& cadenaEsValorNumerico(montoEjecutado)) {
			proyectoInformeDisco.setMontoTotalEjecutadoPeriodo(Long.parseLong(montoEjecutado.trim()));
		} else {
			proyectoInformeDisco.setMontoTotalEjecutadoPeriodo(0L);
		}
		
		if (montoEjecutadoExt != null && !esCadenaVacia(montoEjecutadoExt)
				&& cadenaEsValorNumerico(montoEjecutadoExt)) {
			proyectoInformeDisco.setMontoTotalEjecutadoPeriodoExt(Long.parseLong(montoEjecutadoExt.trim()));
		} else {
			proyectoInformeDisco.setMontoTotalEjecutadoPeriodoExt(0L);
		}

		proyectoInformeDisco.setPorcentajeEjecucion(porcentajeEjecucion);
		proyectoInformeDisco.setPorcentajeEjecucionPresupuestal(porcentajeEjecucionPresupuestal);
		proyectoInformeDisco.setPorcentajeEjecucionPresupuestalExterno(porcentajeEjecucionPresupuestalExt);

		Persona revisorFacultad = (Persona) sesion.getAttribute("persona");
		proyectoInformeDisco.setRevisorFacultad(revisorFacultad);

		// Se guarda informe con nueva información
		servicioGeneral.guardarObjeto(proyectoInformeDisco);

		// Se guarda historico del estado del informe
		try {
			servicioGeneral.guardarObjeto(crearHistoricoEstadoInforme(proyectoInformeDisco, comentariosVIF));
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			System.out.println("Error al guardar historico");
		}

		// Informe final
		if (proyectoInformeDisco.getTipoInforme().getId() == 2) {

			if (proyectoInformeDisco.getEstadoInforme().getId().equals(EstadoInforme.ACEPTADO_FACULTAD)) {

				// Se crea nueva solicitud
				Solicitud sol = new Solicitud();

				sol.setFecha(new Date());
				sol.setRespuesta("");

				int numeroSolicitud = 0;

				// Se carga la información de la solicitud
				List<TipoSolicitud> listaSolicitud;
				if (proyectoInformeDisco.getTipoInforme().getId() == TipoInforme.INFORME_AVANCE) {
					sol.setDescripcion("Informe de Avance");
					numeroSolicitud = 25;

				} else {
					sol.setDescripcion("Informe Final");
					numeroSolicitud = 25;
				}

				// Se obtiene el tipo de solicitud dependiendo del tipo de
				// informe
				listaSolicitud = servicioGeneral.obtenerObjetos(TipoSolicitud.class,
						"from TipoSolicitud where id ='" + numeroSolicitud + "'");
				TipoSolicitud ts = (TipoSolicitud) listaSolicitud.get(0);
				sol.setTipoSolicitud(ts);

				// Se guarda la solicitud en base de datos
				sol.setProyecto(proyectoInformeDisco.getProyecto());
				sol.setTipoViaSolicitud(tipoViaSolicitud);
				servicioGeneral.guardarObjeto(sol);

				if (sol.getId() != null) {

					// Crear asociación de informe y solicitud
					SolicitudInforme solInf = new SolicitudInforme();

					solInf.setInformeID(proyectoInformeDisco.getId());
					solInf.setSolicitudID(sol.getId());
					servicioGeneral.guardarObjeto(solInf);

					Proyecto proyectoConAsesores = new Proyecto();

					proyectoConAsesores = this.servicioProyecto
							.obtenerProyecto(proyectoInformeDisco.getProyecto().getId(), ProyectoDAOHibernate.ASESORES);

					// Se obtiene los coordinadores del proyecto, tipicamente
					// debe ser uno
					List<Persona> coordinadores = new ArrayList<Persona>();
					coordinadores.addAll(proyectoConAsesores.getAsesores());

					if (coordinadores != null && coordinadores.size() > 0) {

						// Se crea la alerta del proyecto
						AlertaProyecto alerta = new AlertaProyecto();
						alerta.setEstado("P");
						alerta.setFechaGenera(new Date());
						alerta.setMensaje(sol.getDescripcion());
						alerta.setProyecto(proyectoInformeDisco.getProyecto());
						alerta.setSolicitud(sol);
						alerta.setAsesor((Persona) coordinadores.get(0));
						servicioGeneral.guardarObjeto(alerta);
					}

					correoActual = cargarPlantilla(63);

					editarCorreo(proyectoInformeDisco.getProyecto());
					Correo correo = new Correo();
					correo.setOrigen(Correo.CORREO_HERMES);
					Persona pe = this.servicioProyecto
							.obtenerInvestigadorPrincipalXProyecto(proyectoInformeDisco.getProyecto().getId());
					String dirCorreo = pe.getEmail();

					correo.adicionarDireccion(dirCorreo);
					//correo.adicionarDireccion(Correo.CORREO_HERMES);
					correo.setAsunto(correoActual.getAsunto());

					correo.setCuerpo(cuerpoCorreo);
					servicioCorreo.enviarCorreoSolicitud(correo);

					// Enviar correo de notificacion al coordinador del proyecto
					correoActual = cargarPlantilla(292);

					editarCorreoCierre(proyectoInformeDisco.getProyecto());
					Correo correoCoord = new Correo();
					correoCoord.setOrigen(Correo.CORREO_HERMES);
					Persona coord = this.servicioProyecto
							.obtenerCoordinadorProyecto(proyectoInformeDisco.getProyecto().getId());
					String dirCorreoCoord = coord.getEmail();

					correoCoord.adicionarDireccion(dirCorreoCoord);
					//correoCoord.adicionarDireccion(Correo.CORREO_HERMES);
					correoCoord.setAsunto(correoActual.getAsunto());
					correoCoord.setCuerpo(cuerpoCorreo);

					servicioCorreo.enviarCorreoSolicitud(correoCoord);

					resultadosRevisionInforme = "El informe se ha aprobado con éxito";

					sesion.removeAttribute("manejadorAvalInformeFacultad");
					return "consultaInformesAval";
				}
			}
		}

		return "";
	}

	public String revisarAvalUAB() {

		ProyectoInforme proyectoInformeDisco = new ProyectoInforme();

		// Estado informe para asignar al informe
		EstadoInforme ein = new EstadoInforme(EstadoInforme.ACEPTADO_UAB);

		// Se consulta toda la información del informe actual
		List<ProyectoInforme> listaConsultaInformes = servicioGeneral.obtenerObjetos(ProyectoInforme.class,
				"from ProyectoInforme  where id ='" + proyectoInformeActual.getId() + "'");

		proyectoInformeDisco = (ProyectoInforme) listaConsultaInformes.get(0);

		// Se asigna estado al informe actual
		proyectoInformeDisco.setEstadoInforme(ein);
		proyectoInformeDisco.setFechaAprobacion(new Date());

		Persona revisorFacultad = (Persona) sesion.getAttribute("persona");
		proyectoInformeDisco.setRevisorFacultad(revisorFacultad);

		Date fechaLectura = new Date();
		proyectoInformeDisco.setFechaLectura(fechaLectura);

		// Se guarda informe con nueva información
		servicioGeneral.guardarObjeto(proyectoInformeDisco);

		// Se guarda historico del estado del informe
		try {
			servicioGeneral.guardarObjeto(crearHistoricoEstadoInforme(proyectoInformeDisco, comentariosVIF));
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			System.out.println("Error al guardar historico");
		}

		if (proyecto.getModalidad().getId().equals(MODALIDAD_FICHA_MINIMA_ID)) {
			proyecto.cambiarEstadoPersona(EstadoProyecto.FINALIZADO, cargarPersonaActual());
			servicioGeneral.guardarObjeto(proyecto);
		}

		correoActual = cargarPlantilla(257);

		editarCorreo(proyectoInformeDisco.getProyecto());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		Persona pe = this.servicioProyecto
				.obtenerInvestigadorPrincipalXProyecto(proyectoInformeDisco.getProyecto().getId());
		String dirCorreo = pe.getEmail();

		correo.adicionarDireccion(dirCorreo);
		//correo.adicionarDireccion(Correo.CORREO_HERMES);
		correo.setAsunto(correoActual.getAsunto());

		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreoSolicitud(correo);

		resultadosRevisionInforme = "El informe se ha aprobado.";
		sesion.removeAttribute("manejadorAvalInformeFacultad");
		sesion.removeAttribute("manejadorAvalInformeUab");
		return "informesJornadaDocente";

	}

	// Metodo no aprobar en unidad academica basica
	public String devolverCorreccionesVIF() {

		// Se carga el proyecto informe desde la base de datos
		ProyectoInforme proyectoInformeDisco;
		List<ProyectoInforme> listaSolicitudAux = servicioGeneral.obtenerObjetos(ProyectoInforme.class,
				"from ProyectoInforme  where id ='" + proyectoInformeActual.getId() + "'");
		proyectoInformeDisco = (ProyectoInforme) listaSolicitudAux.get(0);

		// Cargar nuevo estado DEVUELTO
		List<EstadoInforme> listaEstadoInforme = servicioGeneral.obtenerObjetos(EstadoInforme.class,
				"from EstadoInforme where id ='5'");
		EstadoInforme ein = (EstadoInforme) listaEstadoInforme.get(0);

		// Se guarda el nuevo estado con los comentarios ingresados.
		proyectoInformeDisco.setEstadoInforme(ein);
		proyectoInformeDisco.setNoAprobacion(comentariosVIF);

		// Se guarda el responsable de la revisión del informe
		Persona revisorFacultad = (Persona) sesion.getAttribute("persona");
		proyectoInformeDisco.setRevisorFacultad(revisorFacultad);

		// Se guarda en base de datos
		servicioGeneral.guardarObjeto(proyectoInformeDisco);

		// Se guarda el historico del estado del informe
		try {
			servicioGeneral.guardarObjeto(crearHistoricoEstadoInforme(proyectoInformeDisco, comentariosVIF));
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			System.out.println("Error al guardar historico");
		}

		Persona pe = this.servicioProyecto
				.obtenerInvestigadorPrincipalXProyecto(proyectoInformeDisco.getProyecto().getId());
		String dirCorreo = pe.getEmail();
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
		//correo.adicionarDireccion(Correo.CORREO_HERMES);
		correo.adicionarDireccion(dirCorreo);

		if (proyecto.getEsJornadaDocente() == null || proyecto.getEsJornadaDocente().equals("N")) {
			// Se carga la plantilla
			correoActual = cargarPlantilla(64);
			editarCorreo(proyectoInformeDisco.getProyecto());
			correo.setAsunto(correoActual.getAsunto());
			correo.setCuerpo(cuerpoCorreo);
			servicioCorreo.enviarCorreoSolicitud(correo);
			resultadosRevisionInforme = "El informe se ha rechazado.";
			sesion.removeAttribute("manejadorAvalInformeFacultad");
			sesion.removeAttribute("manejadorAvalInformeUab");
			return "consultaInformesAval";

		} else {
			// Se carga la plantilla
			correoActual = cargarPlantilla(256);
			correo.setAsunto(correoActual.getAsunto());
			String cuerpoCorreoCargado = correoActual.getCuerpo().replaceAll("<<FECHA>>", Fecha.fechaActual());
			cuerpoCorreoCargado = cuerpoCorreoCargado.replaceAll("<<INVESTIGADOR>>", pe.getNombreCompletoMinusculas());
			// Se reemplaza el nombre del proyecto
			cuerpoCorreoCargado = cuerpoCorreoCargado.replaceAll("<<TITULO>>", proyecto.getNombre());

			// Se reemplaza las observaciones cargadas, se hace diferente porque
			// es un campo libre y
			// Puede incluir valores o caracteres especiales.
			String[] parts = cuerpoCorreoCargado.split("<<OBSERVACIONES>>");
			String part1 = parts[0];
			String part2 = parts[1];
			if (comentariosVIF == null || (comentariosVIF != null && comentariosVIF.trim().length() == 0)) {
				comentariosVIF = "No se ingresaron comentarios.";
			}
			cuerpoCorreoCargado = part1 + comentariosVIF + part2;

			// Se carga el id del proyecto y el id del informe
			cuerpoCorreoCargado = cuerpoCorreoCargado.replaceAll("<<ID>>", proyecto.getId().toString());
			cuerpoCorreoCargado = cuerpoCorreoCargado.replaceAll("<<IDINFORME>>",
					proyectoInformeActual.getId().toString());
			correo.setCuerpo(cuerpoCorreoCargado);
			servicioCorreo.enviarCorreoSolicitud(correo);

			resultadosRevisionInforme = "El informe se ha rechazado.";
			sesion.removeAttribute("manejadorAvalInformeFacultad");
			sesion.removeAttribute("manejadorAvalInformeUab");
			return "informesJornadaDocente";
		}

	}

	public void editarCorreo(Proyecto proyecto) {
		try {
			String cuerpoCorreoCargado = correoActual.getCuerpo().replaceAll("<<FECHA>>", Fecha.fechaActual());

			// Se carga investigador principal
			Persona persona = this.servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyecto.getId());
			List<Persona> investidoesPrincipales = servicioGeneral.obtenerObjetos(Persona.class,
					"from Persona p where p.id.documento='" + persona.getId().getDocumento()
							+ "' and p.id.tipoDocumento='" + persona.getId().getTipoDocumento() + "'");

			// Se carfa el nombre del investigador principal
			if (investidoesPrincipales != null && investidoesPrincipales.size() > 0) {
				String investigador = "";
				Persona e = (Persona) investidoesPrincipales.get(0);
				investigador = e.getNombreCompletoMinusculas();
				cuerpoCorreoCargado = cuerpoCorreoCargado.replaceAll("<<INVESTIGADOR>>", investigador);
			}

			// Se reemplaza el nombre del proyecto
			cuerpoCorreoCargado = cuerpoCorreoCargado.replaceAll("<<TITULO>>", proyecto.getNombre());

			// Se reemplaza las observaciones cargadas, se hace diferente porque
			// es un campo libre y
			// Puede incluir valores o caracteres especiales.
			String[] parts = cuerpoCorreoCargado.split("<<OBSERVACIONES>>");
			String part1 = parts[0];
			String part2 = parts[1];
			if (comentariosVIF == null || (comentariosVIF != null && comentariosVIF.trim().length() == 0)) {
				comentariosVIF = "No se ingresaron comentarios.";
			}
			cuerpoCorreoCargado = part1 + comentariosVIF + part2;

			// Se carga el id del proyecto y el id del informe
			cuerpoCorreoCargado = cuerpoCorreoCargado.replaceAll("<<CODIGO>>", proyecto.getId().toString());
			cuerpoCorreoCargado = cuerpoCorreoCargado.replaceAll("<<INFORME>>",
					proyectoInformeActual.getId().toString());

			cuerpoCorreo = cuerpoCorreoCargado;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Aprobar informe coordinador.
	 *
	 * @return the string
	 */
	public String aprobarInformeCoordinador() {
		return lecturaCoordinador("A", EstadoInforme.ACEPTADO_DIRECCION);
	}

	/**
	 * Rechazar informe coordinador.
	 *
	 * @return the string
	 */
	public String rechazarInformeCoordinador() {
		return lecturaCoordinador("R", EstadoInforme.DEVUELTO);
	}

	/**
	 * Lectura coordinador.
	 *
	 * @param estadoSolicitud
	 *            the estado solicitud
	 * @param estadoInforme
	 *            the estado informe
	 * @return the string
	 */
	private String lecturaCoordinador(String estadoSolicitud, Long estadoInforme) {

		List<AlertaProyecto> listaAlertas = servicioGeneral.obtenerObjetos(AlertaProyecto.class,
				"from AlertaProyecto  where id ='" + idAlertaInforme + "'");
		AlertaProyecto alertaProyecto = new AlertaProyecto();

		alertaProyecto = (AlertaProyecto) listaAlertas.get(0);
		alertaProyecto.setEstado("C");
		servicioGeneral.guardarObjeto(alertaProyecto);

		List<Solicitud> listaSolicitudes = servicioGeneral.obtenerObjetos(Solicitud.class,
				"from Solicitud  where id ='" + alertaProyecto.getSolicitud().getId() + "'");
		Solicitud solicitud = new Solicitud();

		solicitud = (Solicitud) listaSolicitudes.get(0);
		solicitud.setRespuesta(estadoSolicitud);
		servicioGeneral.guardarObjeto(solicitud);

		List<ProyectoInforme> listaSolicitudAux = servicioGeneral.obtenerObjetos(ProyectoInforme.class,
				"from ProyectoInforme  where id ='" + idInforme + "'");
		ProyectoInforme pryInf = (ProyectoInforme) listaSolicitudAux.get(0);

		pryInf.setFechaLectura(new Date());
		pryInf.setNoLectura(comentariosCoordinadorLectura);
		pryInf.setFechaDesde(fechaDesdeInforme);
		pryInf.setFechaHasta(fechaHastaInforme);
		if (montoEjecutado != null && !esCadenaVacia(montoEjecutado)
				&& cadenaEsValorNumerico(montoEjecutado)) {
			pryInf.setMontoTotalEjecutadoPeriodo(Long.parseLong(montoEjecutado.trim()));
		} else {
			pryInf.setMontoTotalEjecutadoPeriodo(0L);
		}
		
		if (montoEjecutadoExt != null && !esCadenaVacia(montoEjecutadoExt)
				&& cadenaEsValorNumerico(montoEjecutadoExt)) {
			pryInf.setMontoTotalEjecutadoPeriodoExt(Long.parseLong(montoEjecutadoExt.trim()));
		} else {
			pryInf.setMontoTotalEjecutadoPeriodoExt(0L);
		}

		pryInf.setPorcentajeEjecucion(porcentajeEjecucion);
		pryInf.setPorcentajeEjecucionPresupuestal(porcentajeEjecucionPresupuestal);
		pryInf.setPorcentajeEjecucionPresupuestalExterno(porcentajeEjecucionPresupuestalExt);
		
		pryInf.setActividades(proyectoInformeActual.getActividades());
		pryInf.setResultados(proyectoInformeActual.getResultados());
		pryInf.setObjetivos(proyectoInformeActual.getObjetivos());

		Persona personaCoordinador = (Persona) sesion.getAttribute("persona");
		pryInf.setRevisorCoordinador(personaCoordinador);

		pryInf.setEstadoInforme(new EstadoInforme(estadoInforme));
		servicioGeneral.guardarObjeto(pryInf);

		try {
			servicioGeneral.guardarObjeto(crearHistoricoEstadoInforme(pryInf, comentariosCoordinadorLectura));
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			System.out.println("Error al guardar historico");
		}

		if (estadoSolicitud.equals("A") && pryInf.getTipoInforme().getId().equals(TipoInforme.INFORME_FINAL)) {
			if (proyecto.getEsPermisoMarco()) {
				proyecto.cambiarEstadoPersona(EstadoProyecto.FINALIZADO, cargarPersonaActual(),
						"Aprobación de informe final de permiso de recolecta");
				servicioGeneral.guardarObjeto(proyecto);
			} else if (proyecto.getEsContratoBiodiversidad()) {
				proyecto.cambiarEstadoPersona(EstadoProyecto.FINALIZADO, cargarPersonaActual(),
						"Aprobación de informe final de contrato de acceso a recurso genético");
				servicioGeneral.guardarObjeto(proyecto);
			} else if (proyecto.getEsPermisoMarcoAsignatura()) {
				proyecto.cambiarEstadoPersona(EstadoProyecto.FINALIZADO, cargarPersonaActual(),
						"Aprobación de informe final de permiso de recolecta en asignatura");
				servicioGeneral.guardarObjeto(proyecto);
			}
			// crear correo para el docente aprobado

			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);

			correoActual = cargarPlantilla(291);
			editarCorreoCierre(proyecto);
			correo.setAsunto(correoActual.getAsunto());
			correo.setCuerpo(cuerpoCorreo);

			Persona pe = this.servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyecto.getId());
			String dirCorreo = pe.getEmail();
			correo.adicionarDireccion(dirCorreo);
			//correo.adicionarDireccion(Correo.CORREO_HERMES);

			servicioCorreo.enviarCorreoSolicitud(correo);

		} else if (estadoSolicitud.equals("R") && pryInf.getTipoInforme().getId().equals(TipoInforme.INFORME_FINAL)) {
			// crear correo para el docente rechazado

			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);

			correoActual = cargarPlantilla(290);
			editarCorreoCierre(proyecto);
			correo.setAsunto(correoActual.getAsunto());
			correo.setCuerpo(cuerpoCorreo);

			Persona pe = this.servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyecto.getId());
			String dirCorreo = pe.getEmail();
			correo.adicionarDireccion(dirCorreo);
			//correo.adicionarDireccion(Correo.CORREO_HERMES);

			servicioCorreo.enviarCorreoSolicitud(correo);

		} else if (estadoSolicitud.equals("A") && pryInf.getTipoInforme().getId().equals(TipoInforme.INFORME_AVANCE)) {
			// crear correo para el docente rechazado

			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);

			correoActual = cargarPlantilla(294);
			editarCorreoCierre(proyecto);
			correo.setAsunto(correoActual.getAsunto());
			correo.setCuerpo(cuerpoCorreo);

			Persona pe = this.servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyecto.getId());
			String dirCorreo = pe.getEmail();
			correo.adicionarDireccion(dirCorreo);
			//correo.adicionarDireccion(Correo.CORREO_HERMES);

			servicioCorreo.enviarCorreoSolicitud(correo);

		} else if (estadoSolicitud.equals("R") && pryInf.getTipoInforme().getId().equals(TipoInforme.INFORME_AVANCE)) {
			// crear correo para el docente rechazado

			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);

			correoActual = cargarPlantilla(293);
			editarCorreoCierre(proyecto);
			correo.setAsunto(correoActual.getAsunto());
			correo.setCuerpo(cuerpoCorreo);

			Persona pe = this.servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyecto.getId());
			String dirCorreo = pe.getEmail();
			correo.adicionarDireccion(dirCorreo);
			//correo.adicionarDireccion(Correo.CORREO_HERMES);

			servicioCorreo.enviarCorreoSolicitud(correo);
		}
		
		if(estadoSolicitud.equals("R")) {
			// Se da por cumplido el compromiso
			String hql = "from ProyectoCompromiso WHERE id = '" + pryInf.getProyectoCompromiso().getId()
					+ "'";
			List<ProyectoCompromiso> listaProyectoCompromiso = servicioGeneral
					.obtenerObjetos(ProyectoCompromiso.class, hql);
			if (listaProyectoCompromiso != null && listaProyectoCompromiso.size() > 0) {
				ProyectoCompromiso proyectoCompromisoExistente = (ProyectoCompromiso) listaProyectoCompromiso
						.get(0);
				proyectoCompromisoExistente.setCumplido(ProyectoCompromiso.NO_CUMPLIDO);
				servicioGeneral.guardarObjeto(proyectoCompromisoExistente);
			}
		}

		sesion.removeAttribute("manejadorSeguimiento");

		return "inbox";
	}

	public void cargarListas() {
		boolean validarEstado = false;
		List<DominioDetalle> listaAreaCiencia = servicioGeneral.obtenerDominioDetalle(DOMINIO_AREA_CIENCIA,
				validarEstado);
		areaCienciaItems = crearListaItems(listaAreaCiencia);
		cambiarArea();
		List<DominioDetalle> listaObjetivosDesarrolloSostenible = servicioGeneral
				.obtenerDominioDetalle(DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE);
		objetivosDesarrolloSostenibleItems = crearListaItems(listaObjetivosDesarrolloSostenible);
		objetivosDesarrolloSostenibleImpactoItems = crearListaItems(listaObjetivosDesarrolloSostenible);
		odsItems = crearListaItems(listaObjetivosDesarrolloSostenible);

		listaTipoArchivo = new ArrayList();
		listaTipoArchivo = servicioGeneral
				.obtenerListaObjetos("TipoArchivo e where e.parametro = 'INFORMES_PRY' order by e.id");
		tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
		for (int i = 0; i < listaTipoArchivo.size(); i++) {
			TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
			tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
		}
		tipoArchivo = (TipoArchivo) listaTipoArchivo.get(0);
	}

	public void cambiarArea() {
		subAreaCienciaItems = cambiarAreaCiencia(areaCienciaProd);
		subAreaCienciaProd = "";
	}

	protected SelectItem[] cambiarAreaCiencia(String areaCiencia) {
		String consultaAreasSec = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
				+ DOMINIO_SUB_AREA_CIENCIA + "' and  dd.estado = '" + areaCiencia + "' order by dd.descripcion";
		List<DominioDetalle> listaSubAreaCiencia = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				consultaAreasSec);
		return crearListaItems(listaSubAreaCiencia);
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getNombreInvestigador() {
		return nombreInvestigador;
	}

	public void setNombreInvestigador(String nombreInvestigador) {
		this.nombreInvestigador = nombreInvestigador;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public String getResumenResultados() {
		return substringTamanoMaximo(resumenResultados, 4000);
	}

	public void setResumenResultados(String resumenResultados) {
		this.resumenResultados = resumenResultados;
	}

	public String getResumenAvances() {
		return substringTamanoMaximo(resumenAvances, 4000);
	}

	public void setResumenAvances(String resumenAvances) {
		this.resumenAvances = resumenAvances;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public String getDificultades() {
		return substringTamanoMaximo(dificultades, 4000);
	}

	public void setDificultades(String dificultades) {
		this.dificultades = dificultades;
	}

	public Long getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(Long tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public Long getTipoInformeAccion() {
		return tipoInformeAccion;
	}

	public void setTipoInformeAccion(Long tipoInformeAccion) {
		this.tipoInformeAccion = tipoInformeAccion;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isInformeAvance() {
		return informeAvance;
	}

	public void setInformeAvance(boolean informeAvance) {
		this.informeAvance = informeAvance;
	}

	public String getConclusiones() {
		return substringTamanoMaximo(conclusiones, 4000);
	}

	public void setConclusiones(String conclusiones) {
		this.conclusiones = conclusiones;
	}

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}

	public String getNombre2() {
		return nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public boolean isMostrarConclusiones() {
		return mostrarConclusiones;
	}

	public void setMostrarConclusiones(boolean mostrarConclusiones) {
		this.mostrarConclusiones = mostrarConclusiones;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public List<ArchivoInforme> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<ArchivoInforme> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public String getNombre3() {
		return nombre3;
	}

	public void setNombre3(String nombre3) {
		this.nombre3 = nombre3;
	}

	public String getResumenTecnico() {
		return resumenTecnico;
	}

	public void setResumenTecnico(String resumenTecnico) {
		this.resumenTecnico = resumenTecnico;
	}

	public Bien getBien() {
		return bien;
	}

	public void setBien(Bien bien) {
		this.bien = bien;
	}

	public String getPlacaBien() {
		return placaBien;
	}

	public void setPlacaBien(String placaBien) {
		this.placaBien = placaBien;
	}

	public ProyectoInforme getProyectoInformeActual() {
		return proyectoInformeActual;
	}

	public Investigador getInvestigadorActual() {
		return investigadorActual;
	}

	public void setInvestigadorActual(Investigador investigadorActual) {
		this.investigadorActual = investigadorActual;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public ProductoSara getProductoSara() {
		return productoSara;
	}

	public void setProductoSara(ProductoSara productoSara) {
		this.productoSara = productoSara;
	}

	public SelectItem[] getProductosSaraItems() {
		return productosSaraItems;
	}

	public void setProductosSaraItems(SelectItem[] productosSaraItems) {
		this.productosSaraItems = productosSaraItems;
	}

	public void setProyectoInformeActual(ProyectoInforme proyectoInformeActual) {
		this.proyectoInformeActual = proyectoInformeActual;
	}

	public List<SelectItem> getListaOpciones() {
		return listaOpciones;
	}

	public void setListaOpciones(List<SelectItem> listaOpciones) {
		this.listaOpciones = listaOpciones;
	}

	public String getDocEstudiante() {
		return docEstudiante;
	}

	public void setDocEstudiante(String docEstudiante) {
		this.docEstudiante = docEstudiante;
	}

	public List getListaFormacionEsperada() {
		return listaFormacionEsperada;
	}

	public void setListaFormacionEsperada(List listaFormacionEsperada) {
		this.listaFormacionEsperada = listaFormacionEsperada;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public List<Estudiante> getListaEstudiantes() {
		return listaEstudiantes;
	}

	public void setListaEstudiantes(List<Estudiante> listaEstudiantes) {
		this.listaEstudiantes = listaEstudiantes;
	}

	public ProyectoProducto getResultadoEsp() {
		return resultadoEsp;
	}

	public void setResultadoEsp(ProyectoProducto resultadoEsp) {
		this.resultadoEsp = resultadoEsp;
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

	public List<SelectItem> getListaResultadosItem() {
		return listaResultadosItem;
	}

	public void setListaResultadosItem(List<SelectItem> listaResultadosItem) {
		this.listaResultadosItem = listaResultadosItem;
	}

	public String getResultadoObt() {
		return resultadoObt;
	}

	public void setResultadoObt(String resultadoObt) {
		this.resultadoObt = resultadoObt;
	}

	public boolean[] getPanelRenderError() {
		return panelRenderError;
	}

	public void setPanelRenderError(boolean[] panelRenderError) {
		this.panelRenderError = panelRenderError;
	}

	public String[] getErrores() {
		return errores;
	}

	public void setErrores(String[] errores) {
		this.errores = errores;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public List<ResultadoCompromiso> getListaCompromisos() {
		return listaCompromisos;
	}

	public void setListaCompromisos(List<ResultadoCompromiso> listaCompromisos) {
		this.listaCompromisos = listaCompromisos;
	}

	public String getProductoLlave() {
		return productoLlave;
	}

	public void setProductoLlave(String productoLlave) {
		this.productoLlave = productoLlave;
	}

	public ProyectoProducto getResultadoEsperadoNew() {
		return resultadoEsperadoNew;
	}

	public void setResultadoEsperadoNew(ProyectoProducto resultadoEsperadoNew) {
		this.resultadoEsperadoNew = resultadoEsperadoNew;
	}

	public String getNombreProductoDos() {
		return nombreProductoDos;
	}

	public void setNombreProductoDos(String nombreProductoDos) {
		this.nombreProductoDos = nombreProductoDos;
	}

	public String getTipoProductoDos() {
		return tipoProductoDos;
	}

	public void setTipoProductoDos(String tipoProductoDos) {
		this.tipoProductoDos = tipoProductoDos;
	}

	public String getProductoNivel1() {
		return productoNivel1;
	}

	public void setProductoNivel1(String productoNivel1) {
		this.productoNivel1 = productoNivel1;
	}

	public String getProductoNivel2() {
		return productoNivel2;
	}

	public void setProductoNivel2(String productoNivel2) {
		this.productoNivel2 = productoNivel2;
	}

	public String getProductoNivel3() {
		return productoNivel3;
	}

	public void setProductoNivel3(String productoNivel3) {
		this.productoNivel3 = productoNivel3;
	}

	public ProductoTipo getProductoNivel1Actual() {
		return productoNivel1Actual;
	}

	public void setProductoNivel1Actual(ProductoTipo productoNivel1Actual) {
		this.productoNivel1Actual = productoNivel1Actual;
	}

	public ProductoTipo getProductoNivel2Actual() {
		return productoNivel2Actual;
	}

	public void setProductoNivel2Actual(ProductoTipo productoNivel2Actual) {
		this.productoNivel2Actual = productoNivel2Actual;
	}

	public ProductoTipo getProductoNivel3Actual() {
		return productoNivel3Actual;
	}

	public void setProductoNivel3Actual(ProductoTipo productoNivel3Actual) {
		this.productoNivel3Actual = productoNivel3Actual;
	}

	public SelectItem[] getProductoNivel1Item() {
		return productoNivel1Item;
	}

	public void setProductoNivel1Item(SelectItem[] productoNivel1Item) {
		this.productoNivel1Item = productoNivel1Item;
	}

	public SelectItem[] getProductoNivel2Item() {
		return productoNivel2Item;
	}

	public void setProductoNivel2Item(SelectItem[] productoNivel2Item) {
		this.productoNivel2Item = productoNivel2Item;
	}

	public SelectItem[] getProductoNivel3Item() {
		return productoNivel3Item;
	}

	public void setProductoNivel3Item(SelectItem[] productoNivel3Item) {
		this.productoNivel3Item = productoNivel3Item;
	}

	public List getListaProductosNivel1() {
		return listaProductosNivel1;
	}

	public void setListaProductosNivel1(List listaProductosNivel1) {
		this.listaProductosNivel1 = listaProductosNivel1;
	}

	public List getListaProductosNivel2() {
		return listaProductosNivel2;
	}

	public void setListaProductosNivel2(List listaProductosNivel2) {
		this.listaProductosNivel2 = listaProductosNivel2;
	}

	public List getListaProductosNivel3() {
		return listaProductosNivel3;
	}

	public void setListaProductosNivel3(List listaProductosNivel3) {
		this.listaProductosNivel3 = listaProductosNivel3;
	}

	public List getProductosConvocatoria() {
		return productosConvocatoria;
	}

	public void setProductosConvocatoria(List productosConvocatoria) {
		this.productosConvocatoria = productosConvocatoria;
	}

	public List<ProductoSara> getListaProductosSara() {
		return listaProductosSara;
	}

	public void setListaProductosSara(List<ProductoSara> listaProductosSara) {
		this.listaProductosSara = listaProductosSara;
	}

	public List<Long> getListaProdEliminar() {
		return listaProdEliminar;
	}

	public void setListaProdEliminar(List<Long> listaProdEliminar) {
		this.listaProdEliminar = listaProdEliminar;
	}

	public List<SelectItem> getListaPorcentajesEjecucion() {
		return listaPorcentajesEjecucion;
	}

	public void setListaPorcentajesEjecucion(List<SelectItem> listaPorcentajesEjecucion) {
		this.listaPorcentajesEjecucion = listaPorcentajesEjecucion;
	}

	public List<SelectItem> getListaProductosEsperadosItem() {
		return listaProductosEsperadosItem;
	}

	public void setListaProductosEsperadosItem(List<SelectItem> listaProductosEsperadosItem) {
		this.listaProductosEsperadosItem = listaProductosEsperadosItem;
	}

	/**
	 * @return the revisionInforme
	 */
	public String getRevisionInforme() {
		return revisionInforme;
	}

	/**
	 * @return the resultadosRevisionInforme
	 */
	public String getResultadosRevisionInforme() {
		return resultadosRevisionInforme;
	}

	/**
	 * @return the comentariosVIF
	 */
	public String getComentariosVIF() {
		return comentariosVIF;
	}

	/**
	 * @param comentariosVIF
	 *            the comentariosVIF to set
	 */
	public void setComentariosVIF(String comentariosVIF) {
		this.comentariosVIF = comentariosVIF;
	}

	public String getComentariosCoordinadorLectura() {
		return comentariosCoordinadorLectura;
	}

	public void setComentariosCoordinadorLectura(String comentariosCoordinadorLectura) {
		this.comentariosCoordinadorLectura = comentariosCoordinadorLectura;
	}

	// Impresión de informe desde listado de informes
	public void imprimirImforme() {
		imprimirInforme(proyectoInformeActual);
	}

	public String getLinkLineas() {
		return "http://www.hermes.unal.edu.co/pages/descargas/AreasCiencia.pdf";
	}

	public String getLinkObjetivosDesarrolloSostenible() {
		return "http://www.undp.org/content/undp/es/home/sustainable-development-goals.html";
	}

	/**
	 * Gets the link objetivos.
	 * 
	 * @return the link objetivos
	 */
	public String getLinkObjetivos() {
		return "http://www.hermes.unal.edu.co/pages/descargas/ObjetivoSocioeconomico.pdf";
	}

	public String getObjetivoDesarrolloSosteniblePrimarioProd() {
		return objetivoDesarrolloSosteniblePrimarioProd;
	}

	public void setObjetivoDesarrolloSosteniblePrimarioProd(String objetivoDesarrolloSosteniblePrimarioProd) {
		this.objetivoDesarrolloSosteniblePrimarioProd = objetivoDesarrolloSosteniblePrimarioProd;
	}

	public SelectItem[] getObjetivosDesarrolloSostenibleItems() {
		return objetivosDesarrolloSostenibleItems;
	}

	public void setObjetivosDesarrolloSostenibleItems(SelectItem[] objetivosDesarrolloSostenibleItems) {
		this.objetivosDesarrolloSostenibleItems = objetivosDesarrolloSostenibleItems;
	}

	public String getAreaCienciaProd() {
		return areaCienciaProd;
	}

	public void setAreaCienciaProd(String areaCienciaProd) {
		this.areaCienciaProd = areaCienciaProd;
	}

	public SelectItem[] getAreaCienciaItems() {
		return areaCienciaItems;
	}

	public void setAreaCienciaItems(SelectItem[] areaCienciaItems) {
		this.areaCienciaItems = areaCienciaItems;
	}

	public String getSubAreaCienciaProd() {
		return subAreaCienciaProd;
	}

	public void setSubAreaCienciaProd(String subAreaCienciaProd) {
		this.subAreaCienciaProd = subAreaCienciaProd;
	}

	public SelectItem[] getSubAreaCienciaItems() {
		return subAreaCienciaItems;
	}

	public void setSubAreaCienciaItems(SelectItem[] subAreaCienciaItems) {
		this.subAreaCienciaItems = subAreaCienciaItems;
	}

	public Date getFechaEntregaProd() {
		return fechaEntregaProd;
	}

	public void setFechaEntregaProd(Date fechaEntregaProd) {
		this.fechaEntregaProd = fechaEntregaProd;
	}

	public String getLugarDepositoProd() {
		return lugarDepositoProd;
	}

	public void setLugarDepositoProd(String lugarDepositoProd) {
		this.lugarDepositoProd = lugarDepositoProd;
	}

	public String getSusceptibleProteccionProd() {
		return susceptibleProteccionProd;
	}

	public void setSusceptibleProteccionProd(String susceptibleProteccionProd) {
		this.susceptibleProteccionProd = susceptibleProteccionProd;
	}

	public String getPorcentajeEjecucion() {
		return porcentajeEjecucion;
	}

	public void setPorcentajeEjecucion(String porcentajeEjecucion) {
		this.porcentajeEjecucion = porcentajeEjecucion;
	}

	public String getPorcentajeEjecucionPresupuestal() {
		return porcentajeEjecucionPresupuestal;
	}

	public void setPorcentajeEjecucionPresupuestal(String porcentajeEjecucionPresupuestal) {
		this.porcentajeEjecucionPresupuestal = porcentajeEjecucionPresupuestal;
	}

	public Date getFechaDesdeInforme() {
		return fechaDesdeInforme;
	}

	public void setFechaDesdeInforme(Date fechaDesdeInforme) {
		this.fechaDesdeInforme = fechaDesdeInforme;
	}

	public Date getFechaHastaInforme() {
		return fechaHastaInforme;
	}

	public void setFechaHastaInforme(Date fechaHastaInforme) {
		this.fechaHastaInforme = fechaHastaInforme;
	}

	public List getListaTipoArchivo() {
		return listaTipoArchivo;
	}

	public void setListaTipoArchivo(List listaTipoArchivo) {
		this.listaTipoArchivo = listaTipoArchivo;
	}

	public SelectItem[] getTipoArchivoItem() {
		return tipoArchivoItem;
	}

	public void setTipoArchivoItem(SelectItem[] tipoArchivoItem) {
		this.tipoArchivoItem = tipoArchivoItem;
	}

	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public String getMontoEjecutado() {
		return montoEjecutado;
	}

	public void setMontoEjecutado(String montoEjecutado) {
		this.montoEjecutado = montoEjecutado;
	}

	public boolean isValidaPorcentajeEjecutadoPresupuesto() {
		return validaPorcentajeEjecutadoPresupuesto;
	}

	public void setValidaPorcentajeEjecutadoPresupuesto(boolean validaPorcentajeEjecutadoPresupuesto) {
		this.validaPorcentajeEjecutadoPresupuesto = validaPorcentajeEjecutadoPresupuesto;
	}

	public boolean isConvocatoriasExcepcionSoporte() {
		Long[] cnpNoValidacion = { 12L, 97L, 233L, 236L, 248L, 258L, 262L, 310L, 314L, 326L, 343L, 349L, 353L, 368L, 396L,
				397L, 400L, 418L, 419L, 422L, 450L, 451L };
		return Arrays.asList(cnpNoValidacion)
				.indexOf(((Convocatoria) proyecto.getModalidad()).getPadre().getId()) != -1;
	}
	
	public boolean isMostrarMontoEjecExt() {
		return mostrarMontoEjecExt;
	}

	public void setMostrarMontoEjecExt(boolean mostrarMontoEjecExt) {
		this.mostrarMontoEjecExt = mostrarMontoEjecExt;
	}

	public String getMontoEjecutadoExt() {
		return montoEjecutadoExt;
	}

	public void setMontoEjecutadoExt(String montoEjecutadoExt) {
		this.montoEjecutadoExt = montoEjecutadoExt;
	}

	public String getPorcentajeEjecucionPresupuestalExt() {
		return porcentajeEjecucionPresupuestalExt;
	}

	public void setPorcentajeEjecucionPresupuestalExt(String porcentajeEjecucionPresupuestalExt) {
		this.porcentajeEjecucionPresupuestalExt = porcentajeEjecucionPresupuestalExt;
	}

	public List<SelectItem> getListaObjetivosItem() {
		return listaObjetivosItem;
	}

	public void setListaObjetivosItem(List<SelectItem> listaObjetivosItem) {
		this.listaObjetivosItem = listaObjetivosItem;
	}

	public SelectItem[] getOdsItems() {
		return odsItems;
	}

	public void setOdsItems(SelectItem[] odsItems) {
		this.odsItems = odsItems;
	}

	public SelectItem[] getObjetivosDesarrolloSostenibleImpactoItems() {
		return objetivosDesarrolloSostenibleImpactoItems;
	}

	public void setObjetivosDesarrolloSostenibleImpactoItems(SelectItem[] objetivosDesarrolloSostenibleImpactoItems) {
		this.objetivosDesarrolloSostenibleImpactoItems = objetivosDesarrolloSostenibleImpactoItems;
	}
	
	 public void cargarFechasProyecto() {
		 try {

		         int acumuladoDuracion = 0;
		         int acumuladoDuracionDias = 0;
		         
		        /* if (proyecto.getListaProrrogas() != null) {
		             for (int j = 0; j < proyecto.getListaProrrogas().size(); j++) {
		                 ProyectoProrroga prorroga = (ProyectoProrroga) proyecto.getListaProrrogas().get(j);
		                 acumuladoDuracion += prorroga.getDuracion().intValue();
		                 acumuladoDuracionDias += prorroga.getDias().intValue();
		                 if (proyecto.getFechaTentativaInicio() != null) {
		                     Date fechaFinal = new Date(proyecto.getFechaTentativaInicio().getTime());

		                     fechaFinal.setMonth(fechaFinal.getMonth() + proyecto.getDuracion() + acumuladoDuracion);

		                     Calendar calendar = Calendar.getInstance();
		                     calendar.setTime(fechaFinal);
		                     calendar.add(Calendar.DAY_OF_YEAR, acumuladoDuracionDias);
		                     fechaFinal = calendar.getTime();

		                     prorroga.setNuevaFechaFinal(fechaFinal);
		                 }
		             }
		         }*/

		         if (proyecto.getFechaTentativaInicio() != null) {

		             // Se calcula la nueva fecha final del proyecto
		             fechaFinalProyecto = new Date(proyecto.getFechaTentativaInicio().getTime());
		             fechaFinalProyecto
		                     .setMonth(fechaFinalProyecto.getMonth() + proyecto.getDuracionAcumulada().intValue());

		             // Se agregan los días
		             Calendar calendar = Calendar.getInstance();
		             calendar.setTime(fechaFinalProyecto);
		             calendar.add(Calendar.DAY_OF_YEAR, proyecto.getDuracionDiasAcumulada());
		             fechaFinalProyecto = calendar.getTime();
		         }else {
		        	 fechaFinalProyecto = proyecto.getFechaFinalizacion();
		         }
		     }catch (Exception e){
		         	
		         }
		     }

	public Date getFechaFinalProyecto() {
		cargarFechasProyecto();
		return fechaFinalProyecto;
	}

	public void setFechaFinalProyecto(Date fechaFinalProyecto) {
		this.fechaFinalProyecto = fechaFinalProyecto;
	}

	public boolean isEsConvocatoriaJovInvestigadores() {
		return esConvocatoriaJovInvestigadores;
	}

	public void setEsConvocatoriaJovInvestigadores(boolean esConvocatoriaJovInvestigadores) {
		this.esConvocatoriaJovInvestigadores = esConvocatoriaJovInvestigadores;
	}

}