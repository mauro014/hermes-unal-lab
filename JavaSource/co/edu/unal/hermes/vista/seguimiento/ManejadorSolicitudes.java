/********************************************************************************
Autor 		: Mauricio Amaya Rios
Clase    	: co.edu.unal.hermes.vista.seguimiento.ManejadorSolicitudes
Objetivo 	: Clase para objeto bean para el manejo de to-do  lo  relacionado  con
			  manipulación de solicitudes para un proyecto de investigación.
Creación	: Septiembre 11 de 2007
Modificación:
Detalle		:
 ********************************************************************************/

package co.edu.unal.hermes.vista.seguimiento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.HistoricoEstadoSolicitud;
import co.edu.unal.hermes.modelo.IdDominioDetalle;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.RubroFinanciable;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.modelo.TipoVinculacion;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.seguimiento.AlertaProyecto;
import co.edu.unal.hermes.modelo.seguimiento.DetalleAdicionPresupuesto;
import co.edu.unal.hermes.modelo.seguimiento.DetalleCambioRubro;
import co.edu.unal.hermes.modelo.seguimiento.DetalleSolicitud;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudAdicionPresupuestal;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudCiudad;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudDocumento;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudInvestigador;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudProrrogaInvestigador;
import co.edu.unal.hermes.modelo.seguimiento.TipoCambioContenido;
import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;
import co.edu.unal.hermes.modelo.seguimiento.TipoViaSolicitud;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorSolicitudes.
 */
public class ManejadorSolicitudes extends ManejadorBase {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6987374658391065601L;

    /** The proyecto actual. */
    private Proyecto proyectoActual;

    /** The lista solicitudes proyecto. */
    private List<Solicitud> listaSolicitudesProyecto;

    /** The tipos solicitud item. */
    private SelectItem[] tiposSolicitudItem;

    /** The tipos rubro item. */
    private ArrayList<SelectItem> tiposRubroItem;

    /** The gastos proyecto item. */
    private SelectItem[] gastosProyectoItem;
    
    private Date fechaMinPermiso;
    private Date fechaMaximaPermiso;

    /** The fuentes proyecto item. */
    private ArrayList<SelectItem> fuentesProyectoItem;

    /** The lista sedes item. */
    private SelectItem[] listaSedesItem;

    /** The tipos solicitud item. */
    private SelectItem[] tiposInvestigadorItem;

    /** The tipos genero. */
    private SelectItem[] tiposGeneroItem;

    /** The tipos genero. */
    private SelectItem[] tiposCambioContenido;
    
    private SelectItem[] tipoSolicitudLabs;

    /** The lista tipos solicitud. */
    private List<TipoSolicitud> listaTiposSolicitud;

    /** The detalle cambio rubro seleccionado. */
    private DetalleCambioRubro detalleCambioRubroSeleccionado;

    /** The tipo solicitud usada. */
    private Long tipoSolicitudUsada = (long) 0;

    /** The archivo seleccionado. */
    private SolicitudDocumento archivoSeleccionado;

    /** The fecha actual. */
    private String fechaActual;

    /** The solicitud agregada. */
    private boolean solicitudAgregada = false;

    /** The lista final rubros. */
    private List<RubroFinanciable> listaFinalRubros;

    /** The solicitud seleccionada. */
    private Solicitud solicitudSeleccionada;

    /** The vista solicitud. */
    private Solicitud vistaSolicitud;

    /** The nueva solicitud. */
    private Solicitud nuevaSolicitud;

    /** The agregar solicitud. */
    private boolean agregarSolicitud;

    /** The lista tipos rubro. */
    private List<RubroFinanciable> listaTiposRubro;

    /** The lista detalle eliminado. */
    private List<DetalleCambioRubro> listaDetalleEliminado;

    /** The tipo documento item. */
    private List<SelectItem> tipoDocumentoItem;

    /** The tipo documento. */
    private String tipoDocumento;

    /** The tipo documento. */
    private String tipoInvestigador;

    /** The documento identidad. */
    private String documentoIdentidad;

    /** The tiempo dedicacion semanal. */
    private double tiempoDedicacionSemanal;

    /** The tiempo dedicacion total. */
    private String tiempoDedicacionTotal;
    
    /** The tiempo dedicacion total. */
    private int semanasAdicionalesInvProrroga;

    /** The tiempo dedicacion total. */
    private String descripcionCambioContenido;

    /** The solicitud investigador principal. */
    private SolicitudInvestigador solicitudInvestigadorPrincipal;

    /** The lista investigador eliminado. */
    private List<SolicitudInvestigador> listaInvestigadorEliminado;
    
    /** The lista investigador eliminado. */
    private List<SolicitudProrrogaInvestigador> listaInvestigadorProrrogaEliminado;
    
    /** The lista investigador eliminado. */
    private List<SolicitudProrrogaInvestigador> listaInvestigadoresProrroga;

    /** The lista investigador eliminado. */
    private List<SolicitudInvestigador> listaInvestigadoresNuevos;

    /** The lista investigador eliminado. */
    private List<SolicitudInvestigador> listaInvestigadoresConsulta;

    /** The lista investigador eliminado. */
    private List<SolicitudInvestigador> listaInvestigadoresEliminarProyecto;

    /** The lista tipos investigador. */
    private List<TipoInvestigador> listaTiposInvestigador;

    /** The solicitud investigador principal. */
    private SolicitudInvestigador solicitudInvestigadorSeleccionada;
    
    /** The solicitud investigador principal. */
    private SolicitudProrrogaInvestigador solicitudInvestigadorProSeleccionada;

    /** The investigadores proyecto. */
    private List<InvestigadorProyecto> investigadoresProyecto;

    /** The tipos solicitud item. */
    private List<SelectItem> investigadoresProyectoItem;
    
    /** The tipos solicitud item. */
    private List<SelectItem> investigadoresInternosProyectoItem;

    /** The investigador proyecto. */
    private String investigadorProyecto;
    
    /** The investigador proyecto. */
    private String investigadorInternoProyecto;

    /** The mostrar informacion externo. */
    private boolean mostrarInformacionExterno;

    /** The primer nombre. */
    private String primerNombre;

    /** The segundo nombre. */
    private String segundoNombre;

    /** The primer apellido. */
    private String primerApellido;

    /** The segundo apellido. */
    private String segundoApellido;

    /** The genero. */
    private String genero;

    /** The genero. */
    private String correoElectronico;

    /** The genero. */
    private String tipoCambioContenidoSeleccionado;

    /** The informacion finaciera. */
    protected List<Object[]> informacionFinaciera;

    /** The detalles cambios rubro. */
    List<DetalleCambioRubro> detallesCambiosRubro;

    /** The instituciones apoyo item. */
    private SelectItem[] institucionesApoyoItem;

    /** The tipo archivo item. */
    private SelectItem[] tipoArchivoItem;

    /** The tipo archivo. */
    private String tipoArchivo;

    /** The lista tipo archivo. */
    private List<TipoArchivo> listaTipoArchivo;

    /** The historico estado solicitudes. */
    List<HistoricoEstadoSolicitud> historicoEstadoSolicitudes;

    private List<Financiacion> listaFuentesProyecto;

	private ArrayList<SelectItem> fuentesExternasProyectoItem;
	
	private Financiacion financiacionSolicitudAdicionPresupuestal;
	
	private Long valorSolicitudAdicionPresupuestal;
	
	private SolicitudAdicionPresupuestal adicionPresupuesto;
	
	private TipoRubro tipoRubroAuxSolAdicionPresupuesto;
	
	private Long valorRubroSolAdicionPresupuesto;
	
	private List<DetalleAdicionPresupuesto> listaSolicitudesAdicionPresupuesto;
	
	private boolean bloquearValorTotalAdicion = false;
	
	private boolean bloquearAgregarAdicionRubro = false;
	
	private DetalleAdicionPresupuesto detalleAdicionPresupuestoSeleccionado;
	
	private DetalleAdicionPresupuesto detalleAdicion;
	
	private TipoRubro tipoRubroOrigenCambioRubros;
	private TipoRubro tipoRubroDestinoCambioRubros;
	
	private List<InvestigadorProyecto> investigadoresInternosProyecto;

	private String funcionNuevoInvestigador;
	private List<SelectItem> listaDepartamentos;
	private String departamentoSeleccionado;
	private SelectItem[] ciudadItem;
	private String ciudadSeleccionada;
	private SolicitudCiudad ciudad;

    /**
     * Instantiates a new manejador solicitudes.
     */
    public ManejadorSolicitudes() {
        agregarSolicitud = false;
        tipoArchivo = "";
        listaInvestigadorEliminado = new ArrayList<SolicitudInvestigador>();
        listaInvestigadorProrrogaEliminado = new ArrayList<SolicitudProrrogaInvestigador>();
        listaInvestigadoresNuevos = new ArrayList<SolicitudInvestigador>();
        listaInvestigadoresConsulta = new ArrayList<SolicitudInvestigador>();
        listaInvestigadoresProrroga = new ArrayList<SolicitudProrrogaInvestigador>();
        listaInvestigadoresEliminarProyecto = new ArrayList<SolicitudInvestigador>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        fechaMinPermiso = calendar.getTime();
        try {
            proyectoActual = this.servicioProyecto.obtenerProyecto((Long) super.sesion.getAttribute("idProyecto"),
                    ProyectoDAOHibernate.ASESORES);
        } catch (Exception e) {
            proyectoActual = this.servicioProyecto.obtenerProyecto((Long) super.sesion.getAttribute("idProyecto"),
                    ProyectoDAOHibernate.DATOS_BASICOS);
            Set<Persona> asesoresProyecto = new HashSet<Persona>();
            Persona coorPry = servicioProyecto.obtenerCoordinadorProyecto(proyectoActual.getId());
            asesoresProyecto.add(coorPry);
            proyectoActual.setAsesores(asesoresProyecto);
        }
        cargarListas();
        cargarSolicitudesProyecto();
        cargarOpcionesSedes();
        cargarDepartamentos();

        detallesCambiosRubro = cargarDetallesCambioRubrosAprobados(listaSolicitudesProyecto);
        listaSolicitudesAdicionPresupuesto = cargarDetallesAdicionesPresupuestoAprobadas(listaSolicitudesProyecto);
        informacionFinaciera = cargarFinanciacionActualProyecto(detallesCambiosRubro, proyectoActual, listaSolicitudesAdicionPresupuesto);
    }
    
    public void calcularFechaFinalPermiso() {
    	
    	if (nuevaSolicitud.getFechaInicioRecoleccion() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(nuevaSolicitud.getFechaInicioRecoleccion());
            cal.add(Calendar.MONTH, 6);
            fechaMaximaPermiso = cal.getTime();
        }    	
    }
    
	public void cargarDepartamentos() {
		listaDepartamentos = new ArrayList<SelectItem>();
		String consulta = "select d from Departamento d where d.id like 'CO%' " + "order by d.nombre";
		List<Departamento> listaDepartament = servicioGeneral.obtenerObjetos(Departamento.class, consulta);

		if (!esListaVacia(listaDepartament)) {
			for (int i = 0; i < listaDepartament.size(); i++) {
				Departamento departamento = (Departamento) listaDepartament.get(i);
				listaDepartamentos.add(new SelectItem(departamento.getId(), departamento.getNombre()));
			}
		}
	}
	
	public void cambiarCiudad() {
		ciudadItem = new SelectItem[0];
		if (esCadenaVacia(departamentoSeleccionado)) {
			mensajeError("Debe seleccionar un departamento para que sean listados los municipios.");
			return;
		} else {
			List<Ciudad> listaCiudades = servicioGeneral.obtenerObjetos(Ciudad.class,
					"select c from Ciudad c where c.departamento.id = '" + departamentoSeleccionado + "' or c.id = '-1' ORDER BY c.nombre asc");
			ciudadItem = new SelectItem[listaCiudades.size()];
			for (int i = 0; i < listaCiudades.size(); i++) {
				Ciudad ci = (Ciudad) listaCiudades.get(i);
				ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			}
		}
	}

    /**
     * Mostrar historico cambio estado solicitud.
     */
    public void mostrarHistoricoCambioEstadoSolicitud() {
        historicoEstadoSolicitudes = servicioGeneral.obtenerHistoricoSolicitud(solicitudSeleccionada);
    }

    /**
     * Cargar solicitudes proyecto.
     */
    private void cargarSolicitudesProyecto() {
        listaSolicitudesProyecto = servicioProyecto.obtenerSolicitudesProyecto(proyectoActual, true, true);
    }

    /**
     * Ver solicitud.
     */
    public void verSolicitud() {
        vistaSolicitud = solicitudSeleccionada;
        cargarDetalleCambioInvestigadorPrincipal(solicitudSeleccionada);
        cargarDetalleCambioIntegrantes(solicitudSeleccionada);
        cargarDetalleProrrogaIntegrantes(solicitudSeleccionada);
        
        if(vistaSolicitud.getTipoSolicitud().getId().equals(TipoSolicitud.ADICION_PRESPUESTAL)){
        	cargarDetalleAdicionPresupuestal(solicitudSeleccionada);
        }
        if (vistaSolicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CONTRATO_MARCO)) {
            cargarInstitucionesApoyoBiodiversidad();
        }
        if (vistaSolicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CERTIFICADO_MOVILIZACION)) {
        	cargarDetalleCertificadoEstudiantes(solicitudSeleccionada);
        }
    }

    /**
     * Ver agregar solicitud.
     */
    public void verAgregarSolicitud() {
        inicializarNuevaSolicitud(null);
        vistaSolicitud = null;
        agregarSolicitud = true;
    }

    /**
     * Inicializar nueva solicitud.
     *
     * @param solicitud
     *            the solicitud
     */
    private void inicializarNuevaSolicitud(Solicitud solicitud) {
        limpiarInvestigadorPrincipal();
        listaInvestigadorEliminado.clear();
        listaInvestigadoresNuevos.clear();
        listaInvestigadoresConsulta.clear();
        listaInvestigadoresEliminarProyecto.clear();
        listaInvestigadoresProrroga.clear();
        listaInvestigadorProrrogaEliminado.clear();
        mostrarInformacionExterno = false;
        if (solicitud != null) {
            nuevaSolicitud = solicitud;
            tipoSolicitudUsada = solicitud.getTipoSolicitud().getId();
            if (tipoSolicitudUsada.equals(TipoSolicitud.CAMBIO_CONTENIDO) && solicitud.getTipoCambioContenido() != null
                    && solicitud.getTipoCambioContenido().getId() != null) {
                tipoCambioContenidoSeleccionado = solicitud.getTipoCambioContenido().getId().toString();
            }else if(tipoSolicitudUsada.equals(TipoSolicitud.ADICION_PRESPUESTAL)){
            	cargarDetalleAdicionPresupuestal(solicitud);
            	financiacionSolicitudAdicionPresupuestal = adicionPresupuesto.getFinanciacion();
            	valorSolicitudAdicionPresupuestal = adicionPresupuesto.getValorAdicionSolicitud();
            	tipoRubroAuxSolAdicionPresupuesto = new TipoRubro();
            	valorRubroSolAdicionPresupuesto = 0L;
            	if(validarSumaAdicionRubros(adicionPresupuesto)){
            		bloquearValorTotalAdicion = true;
            		bloquearAgregarAdicionRubro = true;
            	}else{
            		bloquearAgregarAdicionRubro = false;
            	}
            }
            detallarSolicitud();
        } else {
            nuevaSolicitud = new Solicitud();
            tipoSolicitudUsada = null;
            tipoCambioContenidoSeleccionado = "";
            nuevaSolicitud.setFecha(new Date());
            TipoSolicitud tipoSolicitud = new TipoSolicitud();
            tipoSolicitud.setId(null);
            nuevaSolicitud.setTipoSolicitud(tipoSolicitud);
            nuevaSolicitud.setTipoViaSolicitud(new TipoViaSolicitud(TipoViaSolicitud.VIA_HERMES));
            nuevaSolicitud.setRespuesta("");
            /**
             * Se utiliza dominio detalle para institución de apoyo en
             * solicitudes de suscripcion de contrato de biodiversidad el
             * dominio que corresponde a las instituciones es el 138
             */
            nuevaSolicitud.setInstitucionApoyoBiodiversidad(new DominioDetalle());
            nuevaSolicitud.getInstitucionApoyoBiodiversidad().setIdentificador(new IdDominioDetalle());
            nuevaSolicitud.getInstitucionApoyoBiodiversidad().getIdentificador()
                    .setId(DominioDetalle.ID_IDENTIFICADOR_INA);
            
            financiacionSolicitudAdicionPresupuestal = new Financiacion();
            tipoRubroAuxSolAdicionPresupuesto = new TipoRubro();
            adicionPresupuesto = new SolicitudAdicionPresupuestal();
            bloquearValorTotalAdicion = false;
    		bloquearAgregarAdicionRubro = false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        fechaActual = dateFormat.format(new Date());
    }

    /**
     * Checks if is existe vista solicitud.
     *
     * @return true, if is existe vista solicitud
     */
    public boolean isExisteVistaSolicitud() {
        if (vistaSolicitud != null)
            return true;
        return false;
    }

    /**
     * Cargar tipos genero.
     */
    private void cargarTiposGenero() {
        tiposGeneroItem = new SelectItem[2];
        tiposGeneroItem[0] = new SelectItem("M", "Masculino");
        tiposGeneroItem[1] = new SelectItem("F", "Femenino");
    }

    /**
     * Cargar listas.
     */
    public void cargarListas() {
        String tipoSolicitudProyecto;
        boolean esPermisoMarco = false;
        boolean esPermisoAsignatura = false;
        if(proyectoActual.getModalidad().getTipo().getId().equals("PM")) {
        	esPermisoMarco = true;
        }
        if(proyectoActual.getModalidad().getTipo().getId().equals("PMA")) {
        	esPermisoAsignatura = true;
        }
        if (esPermisoMarco) {
            listaTiposSolicitud = (List<TipoSolicitud>) servicioGeneral.obtenerObjetos(TipoSolicitud.class,
                    "select ts from TipoSolicitud ts where ts.id in ('" + TipoSolicitud.CERTIFICADO_MOVILIZACION + "','"
                            + TipoSolicitud.EXPORTACION_IMPORTACION + "','" + TipoSolicitud.RECOLECTA_PNN + "')");
        } else if (proyectoActual != null && proyectoActual.getModalidad() != null
                && proyectoActual.getModalidad().getId().equals(TIPO_CONTRATO_INDIVIDUAL)) {
            if (proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.ACTIVO)) {
                tipoSolicitudProyecto = "'" + TipoSolicitud.EXPORTACION_IMPORTACION + "','"
                        + TipoSolicitud.RECOLECTA_PNN + "'";
            } else {
                tipoSolicitudProyecto = "'" + TipoSolicitud.CONTRATO_INDIVIDUAL + "'";
            }
            listaTiposSolicitud = (List<TipoSolicitud>) servicioGeneral.obtenerObjetos(TipoSolicitud.class,
                    "select ts from TipoSolicitud ts where ts.id in (" + tipoSolicitudProyecto + ")");
        } else if (proyectoActual != null && proyectoActual.getModalidad() != null
                && proyectoActual.getModalidad().getId().equals(TIPO_CONTRATO_MARCO)) {
            if (proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.ACTIVO)) {
                tipoSolicitudProyecto = "'" + TipoSolicitud.EXPORTACION_IMPORTACION + "','"
                        + TipoSolicitud.RECOLECTA_PNN + "'";
            } else {
                tipoSolicitudProyecto = "'" + TipoSolicitud.CONTRATO_MARCO + "'";
            }
            listaTiposSolicitud = (List<TipoSolicitud>) servicioGeneral.obtenerObjetos(TipoSolicitud.class,
                    "select ts from TipoSolicitud ts where ts.id in (" + tipoSolicitudProyecto + ")");
        } else if (esPermisoAsignatura) {
            tipoSolicitudProyecto = "'" + TipoSolicitud.CERTIFICADO_MOVILIZACION + "'";
            listaTiposSolicitud = (List<TipoSolicitud>) servicioGeneral.obtenerObjetos(TipoSolicitud.class,
                    "select ts from TipoSolicitud ts where ts.id in (" + tipoSolicitudProyecto + ")");
		}
         
        else if (!proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.SUSPENDIDO)) {
            // Se cargan los tipos de solicitud según la vigencia en la tabla.
            listaTiposSolicitud = this.servicioSolicitudes.listarTiposSolicitudActivos();
        } else {
            TipoSolicitud tipoSolicitud = new TipoSolicitud();
            tipoSolicitud.setId(TipoSolicitud.REACTIVACION);
            listaTiposSolicitud = servicioSolicitudes.buscarTipoSolicitud(tipoSolicitud);
        }

        // Se valida si el proyecto actual es permiso marco para agregar la
        // opción adicional.
        int cantidadItems;
        if ((proyectoActual.getModalidad() != null
                && (esPermisoMarco
                        || esPermisoAsignatura
                        || proyectoActual.getModalidad().getTipo().getId().equals(TIPO_MODALIDAD_CONTRATO_ACCESO)))
                || proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.SUSPENDIDO)) {
            // En este caso todas las opciones se van a poner y se agrega
            // uno para la opción en blanco
            cantidadItems = listaTiposSolicitud.size() + 1;
        } else {
            // En este caso no se muestra la opción de certificado de
            // movilización pero se deja el tamaño de
            // la lista para que se agregue el espacio en blanco.
            cantidadItems = listaTiposSolicitud.size();
        }
        tiposSolicitudItem = new SelectItem[cantidadItems];

        // Se agregan los items de las solicitudes.
        int j = 0;
        this.tiposSolicitudItem[j++] = new SelectItem("", "");
        for (int i = 1; i < listaTiposSolicitud.size() + 1; i++) {
            TipoSolicitud tipoSolicitud = (TipoSolicitud) listaTiposSolicitud.get(i - 1);
            if (!tipoSolicitud.getId().equals(TipoSolicitud.CERTIFICADO_MOVILIZACION)) {
                tiposSolicitudItem[j++] = new SelectItem(tipoSolicitud.getId(), tipoSolicitud.getNombre());
            } else if ((esPermisoMarco || esPermisoAsignatura)
                    && tipoSolicitud.getId().equals(TipoSolicitud.CERTIFICADO_MOVILIZACION)) {
                tiposSolicitudItem[j++] = new SelectItem(tipoSolicitud.getId(), tipoSolicitud.getNombre());
            }
        }

        // Se cargan las fuetens de financiación del proyecto en un select item
        listaFuentesProyecto = new ArrayList<Financiacion>();
        listaFuentesProyecto = this.servicioProyecto
                .obtenerFunetesFinanciacionProyecto(proyectoActual.getId());
        fuentesProyectoItem = new ArrayList<SelectItem>();
        fuentesProyectoItem.add(new SelectItem("", ""));
        for (int i = 1; i < listaFuentesProyecto.size() + 1; i++) {
            Financiacion financiacion = (Financiacion) listaFuentesProyecto.get(i - 1);
            fuentesProyectoItem.add(new SelectItem(financiacion.getId(), financiacion.getFuente().getDescripcion()));
        }
        gastosProyectoItem = new SelectItem[1];
        gastosProyectoItem[0] = new SelectItem("", "");
        if (proyectoActual.getModalidad() instanceof Convocatoria) {
            listaTiposRubro = this.servicioModalidad
                    .obtenerRubrosFinanciablesModalidad(proyectoActual.getModalidad().getId());
            tiposRubroItem = new ArrayList<SelectItem>();
            tiposRubroItem.add(new SelectItem("", ""));
            for (int i = 1; i < listaTiposRubro.size() + 1; i++) {
                RubroFinanciable rubro = (RubroFinanciable) listaTiposRubro.get(i - 1);
				if (rubro.getTipoRubro().getDescripcion() != null
						&& rubro.getTipoRubro().getDescripcion().equals("GASTOS_CP_2022")
						|| "142,145,144,143,82,141,135,7,120,".contains(rubro.getTipoRubro().getId() + ",")) {
					tiposRubroItem.add(new SelectItem(rubro.getTipoRubro().getId(), rubro.getTipoRubro().getNombre()));
				}
            }
        } else {
            listaTiposRubro = null;
            tiposRubroItem = new ArrayList<SelectItem>();
            tiposRubroItem.add(new SelectItem("", ""));
        }

        // Se cargan los tipos de documento de identidad.
//        List<TipoDocumento> tipos = servicioGeneral.obtenerObjetos(TipoDocumento.class, "from TipoDocumento");
        
        List<TipoDocumento> tipos = servicioGeneral.obtenerTiposDeDocumento();
        
        Iterator<TipoDocumento> i = tipos.iterator();
        tipoDocumentoItem = new ArrayList<SelectItem>();
        while (i.hasNext()) {
            TipoDocumento tipoDocumentoNuevo = i.next();
            tipoDocumentoItem.add(new SelectItem(tipoDocumentoNuevo.getId(), tipoDocumentoNuevo.getNombre()));
        }

        cargarOpcionesTipoInvestigador();

        cargarInvestigadoresProyecto();
        
        cargarInvestigadoresInternosProyecto();

        cargarTiposGenero();

        cargarTiposCambioContenido();
        
        tipoSolicitudLabs = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPO_SOLICITUD_PROYECTOS_LABS);
    }

    /**
     * Cargar instituciones apoyo biodiversidad.
     */
    private void cargarInstitucionesApoyoBiodiversidad() {
        List<DominioDetalle> lista;
        String consulta = "select dd from Dominio d, DominioDetalle dd where "
                + "d.id = dd.identificador.id and d.tipo like 'INSTITUCIONES_APOYO_CONTRATO_BIODIVERSIDAD' order by dd.descripcion";
        lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

        if (lista != null && !lista.isEmpty()) {
            institucionesApoyoItem = new SelectItem[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                DominioDetalle dominioDetalle = (DominioDetalle) lista.get(i);
                institucionesApoyoItem[i] = new SelectItem(dominioDetalle.getIdentificador().getTipo(),
                        dominioDetalle.getDescripcion());
            }
        }
    }

    /**
     * Cargar tipos de archivo obligatorios para la solicitud.
     *
     * @param tipoSolicitud
     *            the tipo solicitud
     */
    private void cargarTiposArchivo(Long tipoSolicitud) {
        listaTipoArchivo = new ArrayList<TipoArchivo>();
        listaTipoArchivo = servicioGeneral.obtenerListaObjetos(
                "TipoArchivo e where e.id = '0' or e.parametro = 'SOLICITUD_" + tipoSolicitud + "'");
        tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
        for (int i = 0; i < listaTipoArchivo.size(); i++) {
            TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
            tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
        }
    }

    /**
     * Cargar tipos cambio contenido.
     */
    private void cargarTiposCambioContenido() {
        if (tiposCambioContenido == null) {
            List<TipoCambioContenido> listaTiposCambio = servicioGeneral.obtenerObjetos(TipoCambioContenido.class,
                    "from TipoCambioContenido");
            if (listaTiposCambio != null) {
                Iterator<TipoCambioContenido> i = listaTiposCambio.iterator();
                tiposCambioContenido = new SelectItem[listaTiposCambio.size()];
                int j = 0;
                while (i.hasNext()) {
                    TipoCambioContenido tipoCambioContenido = i.next();
                    tiposCambioContenido[j++] = new SelectItem(tipoCambioContenido.getId(),
                            tipoCambioContenido.getNombre());
                }
            }
        }
    }

    /**
     * Cargar investigadores proyecto.
     */
    private void cargarInvestigadoresProyecto() {
        investigadoresProyecto = servicioProyecto.obtenerInvestigadoresProyecto(proyectoActual.getId());
        if (investigadoresProyecto != null) {
            investigadoresProyectoItem = new ArrayList<SelectItem>();
            Iterator<InvestigadorProyecto> i = investigadoresProyecto.iterator();
            while (i.hasNext()) {
                InvestigadorProyecto investigadorProyectolista = i.next();
                if (!investigadorProyectolista.getTipo().getId().equals(TipoInvestigador.DIRECTOR)) {
                    investigadoresProyectoItem.add(new SelectItem(investigadorProyectolista.getId(),
                            investigadorProyectolista.getInvestigador().getNombreCompletoMinusculas()));
                }
            }
        }
    }
    
    /**
     * Cargar investigadores proyecto.
     */
    private void cargarInvestigadoresInternosProyecto() {
       investigadoresInternosProyecto = servicioProyecto.obtenerInvestigadoresProyecto(proyectoActual.getId());
        if (investigadoresInternosProyecto != null) {
            investigadoresInternosProyectoItem = new ArrayList<SelectItem>();
            Iterator<InvestigadorProyecto> i = investigadoresProyecto.iterator();
            while (i.hasNext()) {
            	InvestigadorProyecto investigadorProyectolista = i.next();
            	
 
	                if (!investigadorProyectolista.getTipo().getId().equals(TipoInvestigador.DIRECTOR)) {
	                	//if(investigadorProyectolista.getValorPagar()!=null && investigadorProyectolista.getValorPagar()>0) {
	                	investigadoresInternosProyectoItem.add(new SelectItem(investigadorProyectolista.getInvestigador().getId().getDocumento(),
	                            investigadorProyectolista.getInvestigador().getNombreCompletoMinusculas()));
	                	//}
	                }else {
	                	proyectoActual.setInvestigadorPrincipalVista(investigadorProyectolista);
	                }
            	
            }
        }
    }

    /**
     * Agregar solicitud proyecto.
     */
    public void agregarSolicitudProyecto() {
        Date fecha = new Date();
        Solicitud solicitud = new Solicitud();
        solicitud.setProyecto(proyectoActual);
        solicitud.setFecha(fecha);
        TipoSolicitud tipoSolicitud = new TipoSolicitud();
        tipoSolicitud.setId(null);
        solicitud.setTipoSolicitud(tipoSolicitud);
        solicitud.setTipoViaSolicitud(new TipoViaSolicitud(TipoViaSolicitud.VIA_HERMES));
        listaSolicitudesProyecto.add(solicitud);
    }

    /**
     * Agregar nuevo detalle solicitud proyecto.
     */
    public void agregarNuevoDetalleSolicitudProyecto() {
        Date fecha = new Date();
        Solicitud solicitud = new Solicitud();
        solicitud.setProyecto(proyectoActual);
        solicitud.setFecha(fecha);
        TipoSolicitud tipoSolicitud = new TipoSolicitud();
        tipoSolicitud.setId(null);
        solicitud.setTipoSolicitud(tipoSolicitud);
        solicitud.setTipoViaSolicitud(new TipoViaSolicitud(TipoViaSolicitud.VIA_HERMES));
        listaSolicitudesProyecto.add(solicitud);
    }
    
    public void buscarNuevoInvestigadorPrincipal(){
    	
    	if(esCadenaVacia(documentoIdentidad)||esCadenaVacia(tipoDocumento)||tiempoDedicacionSemanal==0) {
    		mensajeError("Debe indicar el tipo y número de documento de identidad del nuevo investigador, asú como las horas semanales de dedicación.");
    		return;
    	}else {
    		try {
                // Se valida que sea un valor numerico menor a 48 horas
                // semanales.
                double valor = tiempoDedicacionSemanal;
                if (valor < 1 || valor > 48) {
                    mensajeError(
                            "Debe ingresar el tiempo de dedicación al proyecto (Horas semanales) menor o igual a 48.");
                    return;
                }
            } catch (NumberFormatException e) {
                mensajeError("Debe ingresar el tiempo de dedicación al proyecto (Horas semanales) válido.");
                return;
            }
    		IdPersona id = new IdPersona();
    		id.setDocumento(documentoIdentidad.trim());
    		id.setTipoDocumento(tipoDocumento); 
    		
    		InvestigadorInterno investigadorInterno = servicioPersona.obtenerInvestigadorInterno(id);
            if (investigadorInterno == null) {
                mensajeError("No se ha encontrado ningún investigador con el número de documento ingresado.");
                return;
            }else {
            	InvestigadorProyecto ip = null;
	    		if (verificarExisteInvestigadorListInvestigadores(id,investigadoresProyecto)) {
	    			ip = new InvestigadorProyecto();
	    			for(int i=0; i<investigadoresProyecto.size(); i++) {
	    				if(investigadoresProyecto.get(i).getInvestigador().getId().getDocumento().equals(documentoIdentidad)){
	    					ip = investigadoresProyecto.get(i);
	    					break;
	    				}
	    				
	    			}
	    		}else {
	    			boolean permitirCatedra = false;
	                if (!servicioProyecto.validarVinculacionPersona(investigadorInterno, permitirCatedra)) {
	                	mensajeError("El tipo de vinculación del investigador no es válido.");
	                	return;
	                }
	    		}
	    		InvestigadorProyecto pp = proyectoActual.getInvestigadorPrincipalVista();
	    		 SolicitudInvestigador solicitudInvestigador = new SolicitudInvestigador();
	    		 long mesesFaltantesProyecto = calcularMesesEntreFechas(getFechaFinalProyecto(), getToday());
	    		 solicitudInvestigador.setNumeroMeses((short) mesesFaltantesProyecto);
	    		 solicitudInvestigador.setDedicacionHorasSemana(tiempoDedicacionSemanal);
                 solicitudInvestigador.setInvestigador(investigadorInterno);
                 solicitudInvestigador.setFuncion(pp.getFuncion()==null?"":pp.getFuncion());
                 if(ip!=null) {
                	 Date fechaVinculacion = null;
                	 if(ip.getFechaVinculacion()!=null) {
                		 fechaVinculacion = ip.getFechaVinculacion();
                	 }else {
                		 fechaVinculacion = proyectoActual.getFechaTentativaInicio();
                	 }
                	 long mesesEjecucion = calcularMesesEntreFechas(getToday(), fechaVinculacion);
                	 long valorDedicacionProyectoHoy = calcularValorFuncionario(ip.getDedicacionHorasSemana(), (int)mesesEjecucion, investigadorInterno.getValorHoraValidado());
                	 solicitudInvestigador.setValorPagar(valorDedicacionProyectoHoy + calcularValorFuncionario(tiempoDedicacionSemanal, (int)mesesFaltantesProyecto, investigadorInterno.getValorHoraValidado()));
                	 solicitudInvestigador.setTipoSolicitud(SolicitudInvestigador.CAMBIAR_DIRECTOR_A_COINVESTIGADOR);
                 }else {
                	 solicitudInvestigador.setValorPagar(calcularValorFuncionario(tiempoDedicacionSemanal, (int)mesesFaltantesProyecto, investigadorInterno.getValorHoraValidado()));
                	 solicitudInvestigador.setTipoSolicitud(SolicitudInvestigador.AGREGAR); 
                 }
                 solicitudInvestigador.setEstado(SolicitudInvestigador.NO_TRAMITADO);

                 // Se agregar el tipo investigador
                 TipoInvestigador tipoInvestigadorNuevo = new TipoInvestigador();
                 tipoInvestigadorNuevo.setId(TipoInvestigador.DIRECTOR);
                 solicitudInvestigador.setTipo(tipoInvestigadorNuevo);
                 solicitudInvestigadorPrincipal = solicitudInvestigador;
	    		
	    		
            }
    		
    	}
    }

    /**
     * Agregar principal.
     */
    public void agregarPrincipal() {
        boolean isPrincipal = true;
        documentoIdentidad = documentoIdentidad.trim();
        if (validarInvestigadorNuevo(isPrincipal)) {
            double numeroHoras = tiempoDedicacionSemanal;
            Short numeroMeses = Short.parseShort(tiempoDedicacionTotal.trim());
            IdPersona id = new IdPersona();
            id.setDocumento(documentoIdentidad);
            id.setTipoDocumento(tipoDocumento);
            InvestigadorInterno investigadorInterno = servicioPersona.obtenerInvestigadorInterno(id);
            if (investigadorInterno == null) {
                mensajeError("No se ha encontrado ningún investigador con el numero de documento ingresado.");
                return;
            } else {
                boolean permitirCatedra = false;
                if (servicioProyecto.validarVinculacionPersona(investigadorInterno, permitirCatedra)) {
                    SolicitudInvestigador solicitudInvestigador = new SolicitudInvestigador();
                    solicitudInvestigador.setDedicacionHorasSemana(numeroHoras);
                    solicitudInvestigador.setNumeroMeses(numeroMeses);
                    solicitudInvestigador.setInvestigador(investigadorInterno);
                    solicitudInvestigador.setFuncion(funcionNuevoInvestigador);
                    solicitudInvestigador.setValorPagar(calcularValorFuncionario(numeroHoras, (int)numeroMeses, investigadorInterno.getValorHoraValidado()));
                    solicitudInvestigador.setTipoSolicitud(SolicitudInvestigador.CAMBIAR_DIRECTOR_A_COINVESTIGADOR);
                    solicitudInvestigador.setEstado(SolicitudInvestigador.NO_TRAMITADO);

                    // Se agregar el tipo investigador
                    TipoInvestigador tipoInvestigadorNuevo = new TipoInvestigador();
                    tipoInvestigadorNuevo.setId(TipoInvestigador.DIRECTOR);
                    solicitudInvestigador.setTipo(tipoInvestigadorNuevo);
                    solicitudInvestigadorPrincipal = solicitudInvestigador;
                } else {
                    mensajeError("El tipo de vinculación del investigador no es válido.");
                }
            }
        }
    }

    /**
     * Agregar integrante.
     */
    public void agregarIntegrante() {
        boolean isPrincipal = false;
        if (validarInvestigadorNuevo(isPrincipal)) {

            double numeroHoras = tiempoDedicacionSemanal;
            Short numeroMeses = Short.parseShort(tiempoDedicacionTotal.trim());

            IdPersona id = new IdPersona();
            id.setDocumento(documentoIdentidad);
            id.setTipoDocumento(tipoDocumento);
            Investigador investigador = servicioPersona.obtenerInvestigador(id);
            boolean esEstudianteInterno = false;

            // Se verifica si es un estudiante, en este caso si no se ha creado
            // el investigador se crea
            if (tipoInvestigador != null && TipoVinculacion.ESTUDIANTE
                    .indexOf(TipoVinculacion.CONECTOR + tipoInvestigador + TipoVinculacion.CONECTOR) != -1) {
                Estudiante estudiante = servicioPersona.obtenerEstudiante(id);
                if (estudiante != null) {
                    esEstudianteInterno = true;
                    if (investigador == null) {
                        InvestigadorInterno investigadorInternoNuevo = estudiante.convertirAInvestigador();
                        Persona personaNueva = servicioPersona.obtenerPersona(investigadorInternoNuevo.getId());
                        if (personaNueva == null) {
                            servicioPersona.insertarNuevaPersona(investigadorInternoNuevo);
                        }
                        servicioPersona.insertarInvestigador(investigadorInternoNuevo);
                        servicioPersona.insertaInterno(investigadorInternoNuevo);
                        investigador = investigadorInternoNuevo;
                    }
                } else {
                    mensajeError("El documento ingresado no corresponde a un estudiante UN.");
                    return;
                }
            }
            // En caso de que a este punto no sea un investigador se verifica si
            // se crea.
            if (investigador == null && !esEstudianteInterno) {
                if (tipoInvestigador != null && tipoInvestigador.equals(TipoVinculacion.PROFESOR_CARRERA_DOCENTE)) {
                    mensajeError("El documento ingresado no corresponde a un profesor carrera docente UN.");
                    return;
                }

                mostrarInformacionExterno = true;
                return;
            }
            if (investigador != null && tipoInvestigador != null
                    && tipoInvestigador.equals(TipoVinculacion.PROFESOR_CARRERA_DOCENTE)) {
                InvestigadorInterno investigadorInterno = servicioPersona
                        .obtenerInvestigadorInterno(investigador.getId());
                if (investigadorInterno != null) {
                    servicioProyecto.validarVinculacionPersona(investigadorInterno, false);
                } else {
                    mensajeError("El documento ingresado no corresponde a un profesor carrera docente UN.");
                    return;
                }
            }

            if (investigador != null) {
                if (!verificarExisteInvestigadorListInvestigadores(investigador.getId(), investigadoresProyecto)
                        && !verificarExisteInvestigadorListaSolicitudes(investigador.getId(),
                                listaInvestigadoresNuevos)) {
                    SolicitudInvestigador solicitudInvestigador = new SolicitudInvestigador();
                    solicitudInvestigador.setDedicacionHorasSemana(numeroHoras);
                    solicitudInvestigador.setNumeroMeses(numeroMeses);
                    solicitudInvestigador.setInvestigador(investigador);
                    solicitudInvestigador.setFuncion(funcionNuevoInvestigador);
                    solicitudInvestigador.setTipoSolicitud(SolicitudInvestigador.AGREGAR);
                    solicitudInvestigador.setEstado(SolicitudInvestigador.NO_TRAMITADO);
                    // búsqueda en investigador interno
                    InvestigadorInterno investigadorInterno = servicioPersona
            				.obtenerInvestigadorInterno(new IdPersona(investigador.getId().getDocumento(), investigador.getId().getTipoDocumento()));
                    
					if(investigadorInterno!=null && (investigadorInterno.getInterno().equals("S") || investigadorInterno.getEsFuncionario().equals("S"))) {
						solicitudInvestigador.setValorPagar(calcularValorFuncionario(numeroHoras, (int) numeroMeses,
								investigadorInterno.getValorHoraValidado()));
					}else {
						solicitudInvestigador.setValorPagar(0L);
					}


                    // Se agregar el tipo investigador
                    TipoInvestigador tipoInvestigadorIntegrante = obtenerTipoInvestigadorActualSeleccionado(
                            this.tipoInvestigador);
                    solicitudInvestigador.setTipo(tipoInvestigadorIntegrante);

                    listaInvestigadoresNuevos.add(solicitudInvestigador);

                    // Se desactiva mostrar informacion de externo
                    mostrarInformacionExterno = false;

                    limpiarInvestigadorNuevo();

                } else if (verificarExisteInvestigadorListInvestigadores(investigador.getId(),
                        investigadoresProyecto)) {
                    mensajeError("El investigador que intenta agregar ya se encuentra vinculado al proyecto.");
                } else {
                    mensajeError(
                            "El investigador que intenta agregar ya se encuentra en la lista de nuevos investigadores.");
                }
            }
        }
    }

    /**
     * Agregar integrante externo.
     */
    public void agregarIntegranteExterno() {
        documentoIdentidad = documentoIdentidad.trim();
        if (validarNuevoInvestigador()) {
            IdPersona id = new IdPersona();
            id.setDocumento(documentoIdentidad);
            id.setTipoDocumento(tipoDocumento);
            InvestigadorExterno investigador = new InvestigadorExterno();
            investigador.setId(id);
            investigador.setApellido1(primerApellido.trim());
            investigador.setApellido2(segundoApellido.trim());
            investigador.setNombre1(primerNombre.trim());
            investigador.setNombre2(segundoNombre.trim());
            investigador.setGenero(genero);
            investigador.setInterno("N");
            investigador.setEvaluador("N");
            investigador.setEsFuncionario("N");
            investigador.setEmail(correoElectronico);
            Institucion institucion = new Institucion();
            institucion.setId("0");
            investigador.setInstitucion(institucion);
            servicioPersona.insertarNuevaPersona(investigador);
            servicioPersona.insertarNuevoInvestigador(investigador);
            servicioPersona.insertarNuevoInvestigadorExterno(investigador);
            agregarIntegrante();
        }
    }

    /**
     * Cancelar externo.
     */
    public void cancelarExterno() {
        mostrarInformacionExterno = false;
        limpiarInvestigadorNuevo();
    }

    /**
     * Validar nuevo investigador.
     *
     * @return true, if successful
     */
    private boolean validarNuevoInvestigador() {
        boolean valido = true;
        if (StringUtils.isEmpty(primerApellido)) {
            mensajeError("Debe ingresar el primer apellido del investigador.");
            valido = false;
        }
        if (StringUtils.isEmpty(primerNombre)) {
            mensajeError("Debe ingresar el primer nombre del investigador.");
            valido = false;
        }
        if (StringUtils.isEmpty(genero)) {
            mensajeError("Debe ingresar el genero del investigador.");
            valido = false;
        }
        if (StringUtils.isEmpty(correoElectronico)) {
            mensajeError("Debe ingresar el correo electrónico del investigador.");
            valido = false;
        }
        return valido;
    }

    /**
     * Agregar integrante eliminar.
     */
    public void agregarIntegranteEliminar() {
        if (investigadorProyecto != null && !investigadorProyecto.trim().isEmpty()) {
            Long idInvestigadorProyecto = Long.parseLong(investigadorProyecto);
            InvestigadorProyecto investigadorProyectoAgregar = obtenerInvestigadorActualSeleccionado(
                    idInvestigadorProyecto);
            if (investigadorProyectoAgregar != null && !verificarExisteInvestigadorListaSolicitudes(
                    investigadorProyectoAgregar.getInvestigador().getId(), listaInvestigadoresEliminarProyecto)) {
                SolicitudInvestigador solicitudInvestigador = new SolicitudInvestigador();
                solicitudInvestigador.setInvestigador(investigadorProyectoAgregar.getInvestigador());
                solicitudInvestigador.setDedicacionHorasSemana(investigadorProyectoAgregar.getDedicacionHorasSemana());
                solicitudInvestigador.setEstado(SolicitudInvestigador.NO_TRAMITADO);
                solicitudInvestigador.setFuncion(investigadorProyectoAgregar.getFuncion());
                Date fechaVinculacion = null;
	           	 if(investigadorProyectoAgregar.getFechaVinculacion()!=null) {
	           		 fechaVinculacion = investigadorProyectoAgregar.getFechaVinculacion();
	           	 }else {
	           		 fechaVinculacion = proyectoActual.getFechaTentativaInicio();
	           	 }
                short mesesVinculadosProyecto = (short) calcularMesesEntreFechas(new Date(), fechaVinculacion);
                solicitudInvestigador.setNumeroMeses(mesesVinculadosProyecto);
                InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(investigadorProyectoAgregar.getInvestigador().getId());
                if(ii!=null) {
                	solicitudInvestigador.setValorPagar(calcularValorFuncionario(investigadorProyectoAgregar.getDedicacionHorasSemana(), (int) mesesVinculadosProyecto, ii.getValorHoraValidado()));
                }else {
                	solicitudInvestigador.setValorPagar((long) 0);
                }
                solicitudInvestigador.setTipo(investigadorProyectoAgregar.getTipo());
                solicitudInvestigador.setTipoSolicitud(SolicitudInvestigador.RETIRAR);
                listaInvestigadoresEliminarProyecto.add(solicitudInvestigador);
            } else {
                mensajeError("El investigador a eliminar ya fue agreado a la lista.");
            }
        } else {
            mensajeError("Debe seleccionar un investigador a eliminar para relizar esta acción.");
        }
        investigadorProyecto = "";
    }

    /**
     * Con este metodo se valida que si se esta agregando un investigador para
     * eliminar este no exista ya en el listado de investigadores a eliminar del
     * proyecto.
     *
     * @param id
     *            the id
     * @param listaSolicitudesInvestigador
     *            the lista solicitudes investigador
     * @return boolean si existe o no
     */
    private boolean verificarExisteInvestigadorListaSolicitudes(IdPersona id,
            List<SolicitudInvestigador> listaSolicitudesInvestigador) {
        if (listaSolicitudesInvestigador != null) {
            Iterator<SolicitudInvestigador> i = listaSolicitudesInvestigador.iterator();
            while (i.hasNext()) {
                SolicitudInvestigador solicitudInvestigador = i.next();
                if (id.equals(solicitudInvestigador.getInvestigador().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Con este metodo se valida que si se esta agregando un investigador para
     * eliminar este no exista ya en el listado de investigadores a eliminar del
     * proyecto.
     *
     * @param id
     *            the id
     * @param listaInvestigador
     *            the lista investigador
     * @return boolean si existe o no
     */
    private boolean verificarExisteInvestigadorListInvestigadores(IdPersona id,
            List<InvestigadorProyecto> listaInvestigador) {
        if (listaInvestigador != null) {
            Iterator<InvestigadorProyecto> i = listaInvestigador.iterator();
            while (i.hasNext()) {
                InvestigadorProyecto investigadorProyectoVerificar = i.next();
                if (id.equals(investigadorProyectoVerificar.getInvestigador().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Eliminar integrante.
     */
    public void eliminarIntegrante() {
        listaInvestigadoresNuevos.remove(solicitudInvestigadorSeleccionada);
        if (solicitudInvestigadorSeleccionada != null) {
            if (solicitudInvestigadorSeleccionada.getId() != null) {
                listaInvestigadorEliminado.add(solicitudInvestigadorSeleccionada);
            }
            solicitudInvestigadorSeleccionada = null;
        }
    }

    /**
     * Eliminar integrante.
     */
    public void eliminarIntegranteAEliminar() {
        listaInvestigadoresEliminarProyecto.remove(solicitudInvestigadorSeleccionada);
        if (solicitudInvestigadorSeleccionada != null) {
            if (solicitudInvestigadorSeleccionada.getId() != null) {
                listaInvestigadorEliminado.add(solicitudInvestigadorSeleccionada);
            }
            solicitudInvestigadorSeleccionada = null;
        }
    }
    

    /**
     * Obtener investigador actual seleccionado.
     *
     * @param tipoId
     *            the tipo id
     * @return the tipo investigador
     */
    public TipoInvestigador obtenerTipoInvestigadorActualSeleccionado(String tipoId) {
        if (listaTiposInvestigador != null) {
            Iterator<TipoInvestigador> i = listaTiposInvestigador.iterator();
            while (i.hasNext()) {
                TipoInvestigador tipoInvestigadorBusqueda = i.next();
                if (tipoInvestigadorBusqueda.getId().equals(tipoId)) {
                    return tipoInvestigadorBusqueda;
                }
            }
        }
        return null;
    }

    /**
     * Obtener investigador actual seleccionado.
     *
     * @param idInvestigadorProyecto
     *            the id investigador proyecto
     * @return the tipo investigador
     */
    public InvestigadorProyecto obtenerInvestigadorActualSeleccionado(Long idInvestigadorProyecto) {
        if (investigadoresProyecto != null) {
            Iterator<InvestigadorProyecto> i = investigadoresProyecto.iterator();
            while (i.hasNext()) {
                InvestigadorProyecto investigadorProyectoBusqueda = i.next();
                if (investigadorProyectoBusqueda.getId().equals(idInvestigadorProyecto)) {
                    return investigadorProyectoBusqueda;
                }
            }
        }
        return null;
    }

    /**
     * Limpiar investigador principal.
     */
    public void limpiarInvestigadorPrincipal() {
        tiempoDedicacionSemanal = 0;
        tiempoDedicacionTotal = null;
        documentoIdentidad = null;
        tipoDocumento = null;
        if (solicitudInvestigadorPrincipal != null) {
            if (solicitudInvestigadorPrincipal.getId() != null) {
                listaInvestigadorEliminado.add(solicitudInvestigadorPrincipal);
            }
            solicitudInvestigadorPrincipal = null;
        }
    }

    /**
     * Limpiar investigador nuevo.
     */
    public void limpiarInvestigadorNuevo() {
        tipoInvestigador = "";
        tipoDocumento = "";
        documentoIdentidad = "";
        tiempoDedicacionSemanal = 0;
        tiempoDedicacionTotal = "";
        primerNombre = "";
        segundoNombre = "";
        primerApellido = "";
        segundoApellido = "";
        genero = "";
    }

    /**
     * Validar investigador nuevo.
     *
     * @param isPrincipal
     *            the is principal
     * @return true, if successful
     */
    private boolean validarInvestigadorNuevo(boolean isPrincipal) {
        boolean valido = true;

        // Se valida que se haya seleccionado un tipo de documento
        if (StringUtils.isEmpty(tipoInvestigador) && !isPrincipal) {
            mensajeError("Debe seleccionar un tipo de vinculacion.");
            valido = false;
        }
        // Se valida que se haya seleccionado un tipo de documento
        if (StringUtils.isEmpty(tipoDocumento)) {
            mensajeError("Debe seleccionar un tipo de documento.");
            valido = false;
        }

        // Se valida que se haya ingresado un documento
        if (StringUtils.isEmpty(documentoIdentidad)) {
            mensajeError("Debe ingresar el número de documento.");
            valido = false;
        }

        if (!proyectoActual.getEsPermisoMarcoAsignatura()) {
            // Se valida que se haya ingresado un tiempo de dedicación semanal
        	if(esCadenaVacia(funcionNuevoInvestigador)) {
        		mensajeError("Debe ingresar la función del nuevo investigador.");
                valido = false;
        	}
            if (tiempoDedicacionSemanal==0) {
                mensajeError("Debe ingresar el tiempo de dedicación al proyecto (Horas semanales).");
                valido = false;
            } else {
                try {
                    // Se valida que sea un valor numerico menor a 48 horas
                    // semanales.
                    double valor = tiempoDedicacionSemanal;
                    if (valor < 1 || valor > 48) {
                        mensajeError(
                                "Debe ingresar el tiempo de dedicación al proyecto (Horas semanales) menor o igual a 48.");
                        valido = false;
                    }
                } catch (NumberFormatException e) {
                    mensajeError("Debe ingresar el tiempo de dedicación al proyecto (Horas semanales) válido.");
                    valido = false;
                }
            }

            // Se valida que haya ingresado un numero de meses de dedicacion.
            if (StringUtils.isEmpty(tiempoDedicacionTotal)) {
                mensajeError("Debe ingresar el tiempo total de dedicación al proyecto (meses).");
                valido = false;
            } else {
                try {
                    // Se valida que sea un valor numerico
                    Short valor = Short.parseShort(tiempoDedicacionTotal.trim());
                    Short tiempoRestanteProyecto = (short) calcularMesesEntreFechas(getToday(),getFechaFinalProyecto());
                    if (valor < 1) {
                        mensajeError("Debe ingresar el tiempo total de dedicación al proyecto valido.");
                        valido = false;
                    }else if(valor > tiempoRestanteProyecto) {
                    	mensajeError("El tiempo de dedicación es mayor a la duración restante del proyecto: "+tiempoRestanteProyecto+" meses. Fecha de finalización identificada: "+getFechaFinalProyecto()+".");
                        valido = false;
                    }
                } catch (NumberFormatException e) {
                    mensajeError("Debe ingresar el tiempo total de dedicación al proyecto (meses) válido.");
                    valido = false;
                }
            }
            
            

            // Se valida que no haya ingresado el investigador principal actual.
            Investigador investigador = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
            if (valido && investigador.getId().getDocumento().equals(documentoIdentidad)
                    && investigador.getId().getTipoDocumento().equals(tipoDocumento)) {
                mensajeError("Debe ingresar un investigador diferente al investigador principal actual.");
                valido = false;
            }
        } else {
            tiempoDedicacionSemanal = 0;
            tiempoDedicacionTotal = "0";
        }
        return valido;
    }
    
    public void agregarAdicionRubro(){
    	detalleAdicion = new DetalleAdicionPresupuesto();
    	
    	TipoRubro rubro = new TipoRubro();
    	Long valorActual = 0L;
    	if(validarCamposAdicionPresupuestal()){
    		rubro = (TipoRubro) servicioGeneral.obtenerObjetos("select r from TipoRubro r where r.id = " + tipoRubroAuxSolAdicionPresupuesto.getId()).get(0);
    		if(rubro != null){
    			for (int i = 0; i < listaFuentesProyecto.size(); i++) {
    				Financiacion fin = listaFuentesProyecto.get(i);
        			if(fin.getId().equals(financiacionSolicitudAdicionPresupuestal.getId())){
        				adicionPresupuesto.setFinanciacion(fin);
        				adicionPresupuesto.setValorAdicionSolicitud(valorSolicitudAdicionPresupuestal);
        				adicionPresupuesto.setSolicitud(nuevaSolicitud);
        		    	
        		    	detalleAdicion.setValorAdicionRubro(valorRubroSolAdicionPresupuesto);
        		    	detalleAdicion.setTipoRubro(rubro);
        		    	detalleAdicion.setSolicitudAdicion(adicionPresupuesto);
        		    	
        		    	if (informacionFinaciera != null && informacionFinaciera.size() > 0) {
        		    		Iterator<Object[]> j = informacionFinaciera.iterator();
        		    		while (j.hasNext()) {
        		    			Object[] financiacionObject = j.next();
        		    			Financiacion finAux = (Financiacion) financiacionObject[0];
        		    			if(finAux.getId().equals(fin.getId())){
        		    				List<Object[]>gastosFinAux = (List<Object[]>) financiacionObject[1];
        		    				if(gastosFinAux.size() > 0){
        		    					Iterator<Object[]> k = gastosFinAux.iterator();
        		                        while (k.hasNext()) {
        		                        	Object[] gastoObjeto = k.next();
        		                            Gasto gasto = (Gasto) gastoObjeto[0];
        		                            valorActual = gasto.getSumaCampos() + (Long) gastoObjeto[1] - (Long) gastoObjeto[2];
        		                            if(gasto != null){
        		                            	if(gasto.getTipoRubro().getId().equals(rubro.getId())){
        		                            		detalleAdicion.setGasto(gasto);
        		                            		detalleAdicion.setValorRubroAntesAdicion(valorActual);
        		                            		detalleAdicion.setValorRubroDespuesAdicion(valorActual + valorRubroSolAdicionPresupuesto);
        		                            		break;
        		                            	}else{
        		                            		detalleAdicion.setValorRubroAntesAdicion(0L);
        		                            		detalleAdicion.setValorRubroDespuesAdicion(valorRubroSolAdicionPresupuesto);
        		                            	}
        		                            }
        		                        }
        		    				}
        		    			}
        		    		}
        		    	}
        			}
        		}
            	
            	if(validarAdicionPrespuestal(detalleAdicion)){
            		adicionPresupuesto.adicionarDetalleAdicionPresupuesto(detalleAdicion);
                	valorRubroSolAdicionPresupuesto = 0L;
                	detalleAdicion = null;
                	if(adicionPresupuesto.getListaDetallesAdicionPresupuesto().size() > 0){
                		bloquearValorTotalAdicion = true;
                	}
            	}
    		}else{
        		mensajeError("No se ha agregado la adición de presupuesto para el rubro seleccionado.");
        	}
    	}else{
    		mensajeError("No se ha agregado la adición de presupuesto para el rubro seleccionado.");
    	}
    }
    
    public boolean validarCamposAdicionPresupuestal(){
    	boolean valido = true;
    	if(tipoRubroAuxSolAdicionPresupuesto == null 
    			|| tipoRubroAuxSolAdicionPresupuesto.getId().equals("")
    			|| tipoRubroAuxSolAdicionPresupuesto.getId().equals(0L)){
    		mensajeError("Debe seleccionar un rubro.");
    		valido = false;
    	}else if(financiacionSolicitudAdicionPresupuestal == null
    			|| financiacionSolicitudAdicionPresupuestal.getId().equals(0L)
    			|| financiacionSolicitudAdicionPresupuestal.getId().equals("")){
    		mensajeError("Debe seleccionar una fuente de financiación.");
    		valido = false;
    	}else if(valorRubroSolAdicionPresupuesto == null
    			|| valorRubroSolAdicionPresupuesto.equals("")){
    		mensajeError("Debe ingresar el valor adicionado para un rubro.");
    		valido = false;
    	}else if(valorRubroSolAdicionPresupuesto <= 0L){
    		mensajeError("El valor adicionado para un rubro debe ser mayor a 0.");
    		valido = false;
    	}else if(valorSolicitudAdicionPresupuestal == null
    			|| valorSolicitudAdicionPresupuestal.equals("")){
    		mensajeError("Debe ingresar el valor de la adición de presupuesto.");
    		valido = false;
    	}else if(valorSolicitudAdicionPresupuestal <= 0L){
    		mensajeError("El valor de la adición de presupuesto debe ser mayor a 0.");
    		valido = false;
    	}    	
    	return valido;
    }
    
    public boolean validarAdicionPrespuestal(DetalleAdicionPresupuesto adicion){
    	//validar que campos no sean nulos y que los valores numericos no sean negativos ni letras
    	boolean valido = true;
    	if(adicion != null){
    		if(adicion.getSolicitudAdicion().getFinanciacion()== null){
        		mensajeError("Debe seleccionar una fuente de financiación.");
        		valido = false;
        	}else if(adicion.getTipoRubro()== null){
        		mensajeError("Debe seleccionar un rubro.");
        		valido = false;
        	}else if(adicion.getSolicitudAdicion().getValorAdicionSolicitud()==null){
        		mensajeError("Debe ingresar el valor de la adición de presupuesto.");
        		valido = false;
        	}else if(adicion.getSolicitudAdicion().getValorAdicionSolicitud()<=0L){
        		mensajeError("El valor de la adición de presupuesto debe ser mayor a 0.");
        		valido = false;
        	}else if(adicion.getValorAdicionRubro()==null){
        		mensajeError("Debe ingresar el valor adicionado para un rubro.");
        		valido = false;
        	}else if(adicion.getValorAdicionRubro()<=0L){
        		mensajeError("El valor adicionado para un rubro debe ser mayor a 0.");
        		valido = false;
        	}else if(adicion.getValorAdicionRubro() > adicion.getSolicitudAdicion().getValorAdicionSolicitud()){
        		mensajeError("El valor adicionado en el rubro no puede exceder el valor total de la solicitud.");
        		valido = false;
        	}        
    		
    		if(adicionPresupuesto.getListaDetallesAdicionPresupuesto().size() > 0){
        		Long totalAdicion = 0L;
        		
        		for (Iterator iterator = adicionPresupuesto.getListaDetallesAdicionPresupuesto().iterator(); iterator.hasNext();) {
        			DetalleAdicionPresupuesto adicionLista = (DetalleAdicionPresupuesto) iterator.next();
        			
    				totalAdicion += adicionLista.getValorAdicionRubro();
    				if(adicion.getTipoRubro().getId().equals(adicionLista.getTipoRubro().getId())){
    					mensajeError("No se debe ingresar dos veces la adición presupuestal de un mismo rubro.");
    		    		valido = false;
    		    		break;
    				}else if(!adicion.getSolicitudAdicion().getFinanciacion().getId().equals(adicionLista.getSolicitudAdicion().getFinanciacion().getId())){
    					mensajeError("La fuente de financiación no corresponde con la seleccionada.");
    		    		valido = false;
    		    		break;
    				}else if(adicion.getSolicitudAdicion().getValorAdicionSolicitud() < adicionLista.getValorAdicionRubro()){
    					mensajeError("El valor adicionado en el rubro no puede exceder el valor total de la solicitud.");
    		    		valido = false;
    		    		break;
    				}
    			}
        		if(valido == false){
        			return valido;
        		}else{
        			if(totalAdicion > adicion.getSolicitudAdicion().getValorAdicionSolicitud()){
    					mensajeError("La suma de adiciones realizadas en los rubros excede el valor total de la solicitud.");
    		    		valido = false;
    				}else if(totalAdicion + adicion.getValorAdicionRubro() > adicion.getSolicitudAdicion().getValorAdicionSolicitud()){
    					mensajeError("La suma de adiciones realizadas en los rubros excede el valor total de la solicitud.");
    		    		valido = false;
    				}else if(totalAdicion + adicion.getValorAdicionRubro() == adicion.getSolicitudAdicion().getValorAdicionSolicitud()){
    					valido = true;
    					bloquearAgregarAdicionRubro = true;
    				}	
        		}        		
        	}
    	}else{
    		mensajeError("No se ha podido adicionar la adición de presupuesto al rubro seleccionado.");
    		valido = false;
    	}
    	return valido;
    }
    
    public void eliminarDetalleAdicionPresupuesto(){
    	if (detalleAdicionPresupuestoSeleccionado != null) {
    		adicionPresupuesto.getDetalleAdicionPresupuesto().remove(detalleAdicionPresupuestoSeleccionado);
    		bloquearAgregarAdicionRubro = false;
    		if(adicionPresupuesto.getDetalleAdicionPresupuesto().size()==0){
    			bloquearValorTotalAdicion = false;
    		}
        }
    }
    
    public boolean validarSumaAdicionRubros(SolicitudAdicionPresupuestal adicion){
    	boolean valido = true;
    	Long total = 0L;
    	if(adicion != null){
    		for (Iterator iterator = adicion.getDetalleAdicionPresupuesto().iterator(); iterator.hasNext();) {
    			DetalleAdicionPresupuesto detalleAdicion = (DetalleAdicionPresupuesto) iterator.next();
    			total += detalleAdicion.getValorAdicionRubro();
    		}
        	
        	if(total.equals(adicion.getValorAdicionSolicitud())){
        		valido = true;
        	}else{
        		valido = false;
        		mensajeError("La suma de las adiciones realizadas en los rubros no es igual al valor total de la solicitud.");
        	}	
    	}else{
    		valido = false;
    	}
    	return valido; 
    }

    /**
     * Detallar solicitud.
     */
    public void detallarSolicitud() {
        if (tipoSolicitudUsada == 0) {
            TipoSolicitud tipoSolicitud = new TipoSolicitud();
            tipoSolicitud.setId(null);
            nuevaSolicitud.setTipoSolicitud(tipoSolicitud);
        } else {
            for (int i = 0; i < listaTiposSolicitud.size(); i++) {
                // Se recorre el listado de solicitudes.
                TipoSolicitud tipoSolicitud = (TipoSolicitud) listaTiposSolicitud.get(i);
                // Si se encuentra la solicitud
                if (tipoSolicitud.getId().equals(tipoSolicitudUsada)) {
                    // Se asigna el tipo a la solicitud
                    nuevaSolicitud.setTipoSolicitud(tipoSolicitud);
                    if (tipoSolicitud.getDetallaRubros()) {
                        inicializarDetalleSolicitud();
                    } else {
                        nuevaSolicitud.setDetalleSolicitud(null);
                    }
                    break;
                }
            }
            if (tipoSolicitudUsada.equals(TipoSolicitud.CAMBIO_INVESTIGADOR_PRINCIPAL)
                    && nuevaSolicitud.getId() != null) {
                cargarDetalleCambioInvestigadorPrincipal(nuevaSolicitud);
            } else if (tipoSolicitudUsada.equals(TipoSolicitud.CONTRATO_INDIVIDUAL)) {
                cargarTiposArchivo(TipoSolicitud.CONTRATO_INDIVIDUAL);
                cargarInstitucionesApoyoBiodiversidad();
            } else if (tipoSolicitudUsada.equals(TipoSolicitud.CONTRATO_MARCO)) {
                cargarTiposArchivo(TipoSolicitud.CONTRATO_MARCO);
                cargarInstitucionesApoyoBiodiversidad();
                if(nuevaSolicitud.getInstitucionApoyoBiodiversidad()==null){
                    nuevaSolicitud.setInstitucionApoyoBiodiversidad(new DominioDetalle());
                    nuevaSolicitud.getInstitucionApoyoBiodiversidad().setIdentificador(new IdDominioDetalle());
                    nuevaSolicitud.getInstitucionApoyoBiodiversidad().getIdentificador()
                            .setId(DominioDetalle.ID_IDENTIFICADOR_INA);
                }
            } else if (tipoSolicitudUsada.equals(TipoSolicitud.EXPORTACION_IMPORTACION)) {
                cargarTiposArchivo(TipoSolicitud.EXPORTACION_IMPORTACION);
            } else if (tipoSolicitudUsada.equals(TipoSolicitud.RECOLECTA_PNN)) {
                cargarTiposArchivo(TipoSolicitud.RECOLECTA_PNN);
            }
            if (tipoSolicitudUsada.equals(TipoSolicitud.CAMBIO_INTEGRANTES) && nuevaSolicitud.getId() != null) {
                cargarDetalleCambioIntegrantes(nuevaSolicitud);
            }
            if (tipoSolicitudUsada.equals(TipoSolicitud.PRORROGA) && nuevaSolicitud.getId() != null) {
                cargarDetalleProrrogaIntegrantes(nuevaSolicitud);
            }
            if (tipoSolicitudUsada.equals(TipoSolicitud.CERTIFICADO_MOVILIZACION) && nuevaSolicitud.getId() != null) {
            	 cargarDetalleCertificadoEstudiantes(solicitudSeleccionada);
            }
           
        }
    }

    /**
     * Cargar detalle cambio investigador principal.
     *
     * @param sol
     *            the sol
     */
    private void cargarDetalleCambioInvestigadorPrincipal(Solicitud sol) {
        List<SolicitudInvestigador> solicitudInvestigadores = servicioGeneral
                .obtenerObjetos(SolicitudInvestigador.class, "from SolicitudInvestigador si where si.solicitud.id = '"
                        + sol.getId() + "'" + "and si.estado != '" + SolicitudInvestigador.BORRADO + "'");
        if (solicitudInvestigadores != null && solicitudInvestigadores.size() > 0) {
            solicitudInvestigadorPrincipal = solicitudInvestigadores.get(0);
        }
    }

    /**
     * Cargar detalle cambio investigador principal.
     *
     * @param sol
     *            the sol
     */
    private void cargarDetalleCambioIntegrantes(Solicitud sol) {
        listaInvestigadoresConsulta.clear();
        List<SolicitudInvestigador> solicitudInvestigadores = servicioGeneral
                .obtenerObjetos(SolicitudInvestigador.class, "from SolicitudInvestigador si where si.solicitud.id = '"
                        + sol.getId() + "'" + "and si.estado != '" + SolicitudInvestigador.BORRADO + "'");
        if (solicitudInvestigadores != null && solicitudInvestigadores.size() > 0) {
            Iterator<SolicitudInvestigador> i = solicitudInvestigadores.iterator();
            while (i.hasNext()) {
                SolicitudInvestigador solicitudInvestigador = i.next();
                listaInvestigadoresConsulta.add(solicitudInvestigador);
                if (solicitudInvestigador.getTipoSolicitud().equals(SolicitudInvestigador.AGREGAR)) {
                    listaInvestigadoresNuevos.add(solicitudInvestigador);
                } else if (solicitudInvestigador.getTipoSolicitud().equals(SolicitudInvestigador.RETIRAR)) {
                    listaInvestigadoresEliminarProyecto.add(solicitudInvestigador);
                }
            }
        }
    }
    
    private void cargarDetalleCertificadoEstudiantes(Solicitud sol) {
        listaInvestigadoresConsulta.clear();
        List<SolicitudInvestigador> solicitudInvestigadores = servicioGeneral
                .obtenerObjetos(SolicitudInvestigador.class, "from SolicitudInvestigador si where si.solicitud.id = '"
                        + sol.getId() + "'" + "and si.estado != '" + SolicitudInvestigador.BORRADO + "'");
        if (solicitudInvestigadores != null && solicitudInvestigadores.size() > 0) {
            Iterator<SolicitudInvestigador> i = solicitudInvestigadores.iterator();
            while (i.hasNext()) {
                SolicitudInvestigador solicitudInvestigador = i.next();
                listaInvestigadoresConsulta.add(solicitudInvestigador);
                if (solicitudInvestigador.getTipoSolicitud().equals(SolicitudInvestigador.AGREGAR)) {
                    listaInvestigadoresNuevos.add(solicitudInvestigador);
                } 
            }
        }
    }
    
    private void cargarDetalleProrrogaIntegrantes(Solicitud sol) {
        List<SolicitudProrrogaInvestigador> solicitudInvestigadores = servicioGeneral
                .obtenerObjetos(SolicitudProrrogaInvestigador.class, "from SolicitudProrrogaInvestigador si where si.solicitud.id = '"
                        + sol.getId() + "'" + "and si.estado != '" + SolicitudProrrogaInvestigador.BORRADO + "'");
        if (solicitudInvestigadores != null && solicitudInvestigadores.size() > 0) {
            Iterator<SolicitudProrrogaInvestigador> i = solicitudInvestigadores.iterator();
            while (i.hasNext()) {
            	SolicitudProrrogaInvestigador solicitudInvestigador = i.next();
            	listaInvestigadoresProrroga.add(solicitudInvestigador);
            }
        }
    }
    
    private void cargarDetalleAdicionPresupuestal(Solicitud sol){
    	adicionPresupuesto = null;
    	if(sol != null){
    		adicionPresupuesto = servicioSolicitudes.obtenerSolicitudAdicionPresupuestal(sol);
    	}    	
    }

    /**
     * Gets the ver resultados.
     *
     * @return the ver resultados
     */
    public boolean getVerResultados() {
        return nuevaSolicitud.getTipoSolicitud().getDetallaRubros();
    }

    /**
     * Guardar enviar.
     */
    public void guardarEnviar() {
        boolean enviarDefinitivamente = true;
        guardar(enviarDefinitivamente);
    }

    /**
     * Guardar despues.
     */
    public void guardarDespues() {
        boolean enviarDefinitivamente = false;
        guardar(enviarDefinitivamente);
    }

    /**
     * Guardar.
     *
     * @param enviarDefinitivamente
     *            the enviar definitivamente
     */
    public void guardar(boolean enviarDefinitivamente) {
        boolean guardaCambios = false;
        boolean valoresMinimosValidos = true;

        // Si es solicitud de cambio de contenido se asigna el tipo de cambio de
        // contenido asignado.
        if (nuevaSolicitud.getTipoSolicitud() != null && nuevaSolicitud.getTipoSolicitud().getId() != null
                && nuevaSolicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CAMBIO_CONTENIDO)) {
            if (tipoCambioContenidoSeleccionado != null && !tipoCambioContenidoSeleccionado.trim().equals("")) {
                Long id = 0L;
                try {
                    id = Long.parseLong(tipoCambioContenidoSeleccionado);
                } catch (NumberFormatException f) {
                    id = 0L;
                }
                if (!id.equals(0L)) {
                    TipoCambioContenido tipoCambioContenido = new TipoCambioContenido();
                    tipoCambioContenido.setId(id);
                    nuevaSolicitud.setTipoCambioContenido(tipoCambioContenido);
                } else {
                    nuevaSolicitud.setTipoCambioContenido(null);
                }
            } else {
                nuevaSolicitud.setTipoCambioContenido(null);
            }
        }

        // Se procede a guardar la solicitud.
        if (!existeProrrogaPendiente()) {
            if (nuevaSolicitud.getTipoSolicitud() == null || nuevaSolicitud.getTipoSolicitud().getId() == null
                    || (nuevaSolicitud.getTipoSolicitud().getId() != null
                            && nuevaSolicitud.getTipoSolicitud().getId() == 0)) {
                mensajeError("El campo tipo solicitud es obligatorio.");
                valoresMinimosValidos = false;
            }
            if ((nuevaSolicitud.getDescripcion() == null || nuevaSolicitud.getDescripcion().trim().equals(""))
                    && enviarDefinitivamente) {
                mensajeError("El campo justificación es obligatorio.");
                valoresMinimosValidos = false;
            }
			if ((nuevaSolicitud.getTipoCambioContenido() != null
					&& nuevaSolicitud.getTipoCambioContenido().getId().equals(5L)
					&& esNulo(nuevaSolicitud.getTipoSolLab()))
                    && enviarDefinitivamente) {
                mensajeError("Debe seleccionar si desea incluir o desasociar laboratorios");
                valoresMinimosValidos = false;
            }
            if (valoresMinimosValidos
                    && nuevaSolicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CAMBIO_CONTENIDO)
                    && enviarDefinitivamente) {
                if (nuevaSolicitud.getTipoCambioContenido() == null
                        || nuevaSolicitud.getTipoCambioContenido().getId() == null
                        || (nuevaSolicitud.getTipoCambioContenido().getId() != null
                                && nuevaSolicitud.getTipoCambioContenido().getId().equals(0L))) {
                    mensajeError("Debe seleccionar el contenido que desea cambiar.");
                    valoresMinimosValidos = false;
                }
                if (nuevaSolicitud.getDescripcionCambioContenido() == null
                        || (nuevaSolicitud.getDescripcionCambioContenido() != null
                                && nuevaSolicitud.getDescripcionCambioContenido().trim().equals(""))) {
                    mensajeError("Debe ingresar la descripción del cambio de contenido.");
                    valoresMinimosValidos = false;
                }
            }
            if (valoresMinimosValidos) {
                try {
                    boolean detalleCambioValido = true;
                    boolean solicitudValida = true;
                    String mensajeTransaccion = "";
                    Solicitud solicitud = (Solicitud) nuevaSolicitud;
                    solicitud.setProyecto(proyectoActual);
                    if (enviarDefinitivamente) {
                        mensajeTransaccion = servicioSolicitudes.validarSolicitud(solicitud);
                    }
                    solicitudValida = mensajeTransaccion.equals("");

                    // Se valida si es cambio de rubro y si ya se agrego
                    // detalle.
                    if (enviarDefinitivamente && solicitud.getTipoSolicitud().getId() != null
                            && solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CAMBIO_RUBROS)
                            && (solicitud.getDetalleCambioRubro() == null
                                    || solicitud.getDetalleCambioRubro().size() == 0)) {
                        detalleCambioValido = false;
                        mensajeTransaccion = "La solicitud de cambio de rubros "
                                + "no puede guardarse. Debe ingresar al menos un"
                                + " detalle de solicitud correctamente.";
                        mensajeError(mensajeTransaccion);
                    }

                    // Se valida el cambio de investigador principal
                    if (enviarDefinitivamente && solicitud.getTipoSolicitud().getId() != null
                            && solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CAMBIO_INVESTIGADOR_PRINCIPAL)
                            && solicitudInvestigadorPrincipal == null) {
                        detalleCambioValido = false;
                        mensajeTransaccion = "La solicitud de cambio de investigador principal "
                                + "no puede guardarse. Debe ingresar el nuevo investigador principal.";
                        mensajeError(mensajeTransaccion);
                    }

                    // Se valida el cambio de investigador principal
                    if (enviarDefinitivamente && solicitud.getTipoSolicitud().getId() != null
                            && solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CAMBIO_INTEGRANTES)
                            && listaInvestigadoresNuevos.isEmpty() && listaInvestigadoresEliminarProyecto.isEmpty()) {
                        detalleCambioValido = false;
                        mensajeTransaccion = "La solicitud de cambio de integrantes "
                                + "no puede guardarse. Debe ingresar al menos un detalle de cambio de integrantes.";
                        mensajeError(mensajeTransaccion);
                    }

                    // Se valida el cambio de investigador principal
                    if (enviarDefinitivamente && solicitud.getTipoSolicitud().getId() != null
                            && solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.PRORROGA)
                            && solicitudInvestigadorPrincipal == null) {
                    	
                        if (solicitud.getFechaEsperadaTerminacion() == null) {
                            mensajeTransaccion = "La solicitud de prorroga "
                                    + "no puede guardarse. Debe ingresar la fecha propuesta para la finalización.";
                            mensajeError(mensajeTransaccion);
                            solicitudValida = false;
                        }else {
                    		boolean existeDirector = false;
                    		if(listaInvestigadoresProrroga!=null && listaInvestigadoresProrroga.size()>0) {
                    			for(int i=0; i < listaInvestigadoresProrroga.size(); i++) {
                    				SolicitudProrrogaInvestigador s = listaInvestigadoresProrroga.get(i);
                    				if(s.getTipo().getId().equals(InvestigadorProyecto.PRINCIPAL)) {
                    					existeDirector = true;
                    					break;
                    				}
                    			}
                    		}
                    		if(!existeDirector) {
                    			agregarDirectorProrroga();
                    		}
                    		
                        }
                        
                    }
                    
                 // Se valida la adición presupuestal
                    if (enviarDefinitivamente && solicitud.getTipoSolicitud().getId() != null
                            && solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.ADICION_PRESPUESTAL)
                            && (adicionPresupuesto == null || adicionPresupuesto.getDetalleAdicionPresupuesto().size() == 0)
                            && adicionPresupuesto != null
                            && !validarSumaAdicionRubros(adicionPresupuesto)) {
                    	solicitudValida = false;
                        mensajeTransaccion = "La solicitud de adición presupuestal "
                                + "no puede guardarse. Debe ingresar asignarse esta adición en los diferentes rubros del proyecto.";
                        mensajeError(mensajeTransaccion);
                    }

                    // valida la completitud de la solicitud de certificado de
                    // movilizacion
                    if (enviarDefinitivamente && solicitud.getTipoSolicitud().getId() != null
                            && solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CERTIFICADO_MOVILIZACION)) {
                        if (solicitud.getNumeroEspecimenes() == null || solicitud.getNumeroEspecimenes() == 0L) {
                            mensajeTransaccion = "Debe indicar el número aproximado de especímenes que va a movilizar.";
                            mensajeError(mensajeTransaccion);
                            solicitudValida = false;
                        }
                        if (solicitud.getFamiliaTaxonomica() == null || (solicitud.getFamiliaTaxonomica() != null
                                && solicitud.getFamiliaTaxonomica().trim().equals(""))) {
                            mensajeTransaccion = "Debe ingresar los especímenes objeto de recolección y movilización.";
                            mensajeError(mensajeTransaccion);
                            solicitudValida = false;
                        }
                        /*if (solicitud.getUbicacion() == null
                                || (solicitud.getUbicacion() != null && solicitud.getUbicacion().trim().equals(""))) {
                            mensajeTransaccion = "Debe ingresar la ubicación de recolecta.";
                            mensajeError(mensajeTransaccion);
                            solicitudValida = false;
                        }*/
                        //Se cambia validación de lugar de recolecta
                        if(esListaVacia(solicitud.getListaCiudades())) {
                        	mensajeTransaccion = "Debe ingresar la ubicación de recolecta.";
                            mensajeError(mensajeTransaccion);
                            solicitudValida = false;
                        }
                        
                        if (solicitud.getFechaInicioRecoleccion()==null || solicitud.getFechaEsperadaTerminacion() == null) {
                            mensajeTransaccion = "Debe ingresar el periodo de recolección.";
                            mensajeError(mensajeTransaccion);
                            solicitudValida = false;
                        }
                        
                        if (solicitud.getSede() == null
                                || (solicitud.getSede() != null && solicitud.getSede().trim().equals(""))) {
                            mensajeTransaccion = "Debe ingresar la sede destino de los especímenes.";
                            mensajeError(mensajeTransaccion);
                            solicitudValida = false;
                        }
                        if (listaInvestigadoresNuevos.isEmpty() && proyectoActual.getEsPermisoMarcoAsignatura()) {
                            mensajeTransaccion = "La solicitud no puede guardarse. Debe ingresar los estudiantes que recolectarán en la salida.";
                            mensajeError(mensajeTransaccion);
                            solicitudValida = false;
                        }
                        if (proyectoTieneCompromisoPendiente(proyectoActual.getId(), TipoInforme.INFORME_AVANCE)) {
                            mensajeTransaccion = "Usted tiene un compromiso pendiente de entrega de informe semestral,"
                                    + " hasta que el compromiso no sea cumplido NO podrá solicitar una nueva carta de movilización. ";
                            mensajeError(mensajeTransaccion);
                            mensajeError(
                                    "Guarde la información diligenciada a través del botón 'Guardar y continuar después'.");
                            solicitudValida = false;
                        }
                    }

                    // valida la completitud de la solicitud de suscripcion de
                    // contrato u otrosi
                    if (enviarDefinitivamente && solicitud.getTipoSolicitud().getId() != null
                            && (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CONTRATO_INDIVIDUAL)
                                    || solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CONTRATO_MARCO))) {

                        if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CONTRATO_MARCO)) {
                            if (solicitud.getInstitucionApoyoBiodiversidad() == null || "".equals(
                                    solicitud.getInstitucionApoyoBiodiversidad().getIdentificador().getTipo())) {
                                mensajeTransaccion = "Debe indicar la institución nacional de apoyo.";
                                mensajeError(mensajeTransaccion);
                                solicitudValida = false;
                            }
                        } else if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CONTRATO_INDIVIDUAL)) {
                            if (solicitud.getOtraInstitucionNacionalApoyo() == null
                                    || "".equals(solicitud.getOtraInstitucionNacionalApoyo())) {
                                mensajeTransaccion = "Debe indicar la institución nacional de apoyo.";
                                mensajeError(mensajeTransaccion);
                                solicitudValida = false;
                            }
                        }
                    }

                    if (enviarDefinitivamente && solicitud.getTipoSolicitud().getId() != null
                            && (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CONTRATO_INDIVIDUAL)
                                    || solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CONTRATO_MARCO)
                                    || solicitud.getTipoSolicitud().getId()
                                            .equals(TipoSolicitud.EXPORTACION_IMPORTACION)
                            || solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.RECOLECTA_PNN))) {
                        if (solicitud.getDocumentos() == null || solicitud.getDocumentos().isEmpty()) {
                            mensajeTransaccion = "Debe adjuntar los documentos requeridos.";
                            mensajeError(mensajeTransaccion);
                            solicitudValida = false;
                        } else if (!solicitud.getDocumentos().isEmpty()) {
                            if (listaTipoArchivo != null && !listaTipoArchivo.isEmpty()) {
                                int total;
                                total = 0;
                                int cantidadObligatorio = listaTipoArchivo.size() - 1;
                                List<TipoArchivo> listaTemporal = new ArrayList<TipoArchivo>();
                                listaTemporal.addAll(listaTipoArchivo);

                                Iterator<SolicitudDocumento> j = solicitud.getDocumentos().iterator();
                                while(j.hasNext()) {
                                    SolicitudDocumento solDoc = j.next();
                                    for (int i = 0; i < listaTemporal.size(); i++) {
                                        TipoArchivo tipo = (TipoArchivo) listaTemporal.get(i);
                                        if (!tipo.getId().toString().equals("0")) {
                                            if (tipo.getId().toString().equals(solDoc.getTipo().getId().toString())) {
                                                listaTemporal.remove(tipo);
                                                total = total + 1;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (total < cantidadObligatorio) {
                                    String error = "Debe adjuntar los documentos obligatorios (mínimo "
                                            + cantidadObligatorio + ", todos ellos de un tipo distinto a 'Otro').";
                                    mensajeError(error);
                                    solicitudValida = false;
                                }
                            }
                        }
                    }

                    if (detalleCambioValido && solicitudValida) {
                        solicitud.setFecha(new Date());
                        if (enviarDefinitivamente) {
                            solicitud.setRespuesta("");
                        } else {
                            if (!solicitud.getRespuesta().equals(Solicitud.DEVUELTO_CORRECCIONES)) {
                                solicitud.setRespuesta(Solicitud.GUARDADA);
                            }
                            if (solicitud.getDetalleCambioRubro() == null
                                    || (solicitud.getDetalleCambioRubro() != null
                                            && solicitud.getDetalleCambioRubro().size() == 0)) {
                                solicitud.setDetalleSolicitud(null);
                            }
							if (adicionPresupuesto != null && (adicionPresupuesto.getDetalleAdicionPresupuesto() == null
									|| (adicionPresupuesto.getDetalleAdicionPresupuesto() != null
											&& adicionPresupuesto.getDetalleAdicionPresupuesto().size() == 0))) {
								adicionPresupuesto = null;
							}
                        }
                        servicioGeneral.guardarObjeto(solicitud);
                        if (solicitud.getDocumentos() != null && solicitud.getDocumentos().size() > 0) {
                            Iterator<SolicitudDocumento> j = solicitud.getDocumentos().iterator();
                            while(j.hasNext()) {
                                SolicitudDocumento solicitudDocumento = j.next();
                                solicitudDocumento.setSolicitud(solicitud);
                                servicioGeneral.guardarObjeto(solicitudDocumento);
                            }
                        }

                        // Si es solicitud de prorroga se elimina la fecha que
                        // se haya ingresado
                        if (solicitud.getTipoSolicitud().getId().intValue() != TipoSolicitud.PRORROGA) {
                            solicitud.setFechaEsperadaTerminacion(null);
                        }

                        // Se guardan los detalles de cambio de rubros
                        if (solicitud.getTipoSolicitud().getId().intValue() == TipoSolicitud.CAMBIO_RUBROS
                                && solicitud.getDetalleCambioRubro() != null) {
                            
                            Iterator<DetalleCambioRubro> i = solicitud.getDetalleCambioRubro().iterator();
                            while(i.hasNext()) {
                                DetalleCambioRubro detalleCambioRubro = i.next();
                                servicioGeneral.guardarObjeto(detalleCambioRubro);
                            }
                        }
                        
                     // Se guarda solicitud adicion presupuesto
                        if (solicitud.getTipoSolicitud().getId().intValue() == TipoSolicitud.ADICION_PRESPUESTAL) {
                        	if(adicionPresupuesto != null && adicionPresupuesto.getDetalleAdicionPresupuesto() != null){
                        		servicioGeneral.guardarObjeto(adicionPresupuesto);
                        		valorRubroSolAdicionPresupuesto = 0L;
                        		valorSolicitudAdicionPresupuestal = 0L;
                        		adicionPresupuesto = null;
                        		financiacionSolicitudAdicionPresupuestal = new Financiacion();
                        		tipoRubroAuxSolAdicionPresupuesto = new TipoRubro();
                        		bloquearAgregarAdicionRubro = false;
                        		bloquearValorTotalAdicion = false;
                        	}
                        }

                        if (solicitud.getDocumentos() != null) {
                            Iterator<SolicitudDocumento> j = solicitud.getDocumentos().iterator();
                            while(j.hasNext()) {
                                SolicitudDocumento solicitudDocumento = j.next();
                                this.servicioGeneral.guardarObjeto(solicitudDocumento);
                            }
                        }

                        // Si es cambio de investigador principal
                        if (solicitud.getTipoSolicitud().getId()
                                .intValue() == TipoSolicitud.CAMBIO_INVESTIGADOR_PRINCIPAL) {
                            if (solicitudInvestigadorPrincipal != null) {
                                solicitudInvestigadorPrincipal.setSolicitud(solicitud);
                                servicioGeneral.guardarObjeto(solicitudInvestigadorPrincipal);
                            }
                        }

                        // Si es cambio de integrantes
                        if ((solicitud.getTipoSolicitud().getId().intValue() == TipoSolicitud.CAMBIO_INTEGRANTES)
                                || (solicitud.getTipoSolicitud().getId()
                                        .intValue() == TipoSolicitud.CERTIFICADO_MOVILIZACION
                                        && proyectoActual.getEsPermisoMarcoAsignatura())) {
                            List<SolicitudInvestigador> solicitudesInvestigador = new ArrayList<SolicitudInvestigador>();
                            solicitudesInvestigador.addAll(listaInvestigadoresNuevos);
                            solicitudesInvestigador.addAll(listaInvestigadoresEliminarProyecto);
                            Iterator<SolicitudInvestigador> i = solicitudesInvestigador.iterator();
                            while (i.hasNext()) {
                                SolicitudInvestigador solicitudInvestigador = i.next();
                                solicitudInvestigador.setSolicitud(solicitud);
                                servicioGeneral.guardarObjeto(solicitudInvestigador);
                            }
                        }
                        
                     // Si es prorroga
                        if (solicitud.getTipoSolicitud().getId().intValue() == TipoSolicitud.PRORROGA) {
                            List<SolicitudProrrogaInvestigador> solicitudesInvestigador = new ArrayList<SolicitudProrrogaInvestigador>();
                            solicitudesInvestigador.addAll(listaInvestigadoresProrroga);
                            Iterator<SolicitudProrrogaInvestigador> i = solicitudesInvestigador.iterator();
                            while (i.hasNext()) {
                            	SolicitudProrrogaInvestigador solicitudInvestigador = i.next();
                                solicitudInvestigador.setSolicitud(solicitud);
                                servicioGeneral.guardarObjeto(solicitudInvestigador);
                            }
                        }

                        // Si no es cambio de rubros se eliminan los detalles
                        if (solicitud.getTipoSolicitud().getId().intValue() != TipoSolicitud.CAMBIO_RUBROS) {
                            // Se borran los detalles de cambio de otras
                            // solicitudes
                            String eliminarDetallesCambioRubro = "delete from HER_DETALLE_CAMBIO_RUBRO where sol_id = '"
                                    + solicitud.getId() + "'";
                            servicioGeneral.ejecutarSentencia(eliminarDetallesCambioRubro);
                        }
                        
                     // Si no es adición de presupuesto se eliminan los detalles
                        if (solicitud.getTipoSolicitud().getId().intValue() != TipoSolicitud.ADICION_PRESPUESTAL) {
                            // Se borran los detalles de cambio de otras
                            // solicitudes
                            String eliminarAdicionPresupuesto = "delete from HER_SOLICITUD_ADICION where sol_id = '"
                                    + solicitud.getId() + "'";
                            servicioGeneral.ejecutarSentencia(eliminarAdicionPresupuesto);
                        }

                        // Si no es cambio de investigador principal se borra el
                        // detalle
                        if (solicitud.getTipoSolicitud().getId()
                                .intValue() != TipoSolicitud.CAMBIO_INVESTIGADOR_PRINCIPAL) {
                            if (solicitudInvestigadorPrincipal != null
                                    && solicitudInvestigadorPrincipal.getId() != null) {
                                borrarSolicitudInvestigador(solicitudInvestigadorPrincipal);
                                solicitudInvestigadorPrincipal = null;
                            }
                        }

                        // Se borran las solicitudes de investigador que se
                        // eliminaron en la vista
                        if (listaInvestigadorEliminado.size() > 0) {
                            Iterator<SolicitudInvestigador> i = listaInvestigadorEliminado.iterator();
                            while (i.hasNext()) {
                                borrarSolicitudInvestigador(i.next());
                            }
                        }
                        
                        if (listaInvestigadorProrrogaEliminado.size() > 0) {
                            Iterator<SolicitudProrrogaInvestigador> i = listaInvestigadorProrrogaEliminado.iterator();
                            while (i.hasNext()) {
                                borrarSolicitudProrrogaInvestigador(i.next());
                            }
                        }

                        listaInvestigadorEliminado.clear();

                        if (enviarDefinitivamente) {
                            
                            if(solicitud.getTipoSolicitud().isEsSolicitudBiodiversidadVicerrectoria()){
                            	Persona coordinadorSeguimiento = this.servicioProyecto
                                        .obtenerCoordinadorProyecto(proyectoActual.getId());
                                if(coordinadorSeguimiento!=null){
                                    enviarSolicitud(solicitud, coordinadorSeguimiento);
                                }else {
                                	Persona coordinadorEvaluacion = this.servicioProyecto
                                            .obtenerCoordinadorEvaluacionProyecto(proyectoActual.getId());
                                	 if(coordinadorEvaluacion!=null){
                                         enviarSolicitud(solicitud, coordinadorEvaluacion);
                                     }
                                }
                                
                            }else{
                                List<Persona> asesores = new ArrayList<Persona>();
                                asesores.addAll(proyectoActual.getAsesores());
                                if (!asesores.isEmpty()) {
                                    Persona asesor = (Persona) asesores.get(0);
                                    enviarSolicitud(solicitud, asesor);
                                } 
                            }
                        }
                        if (solicitud.getId() != null) {
                            guardaCambios = true;
                        }
                    }
                    if (mensajeTransaccion.equals("") && guardaCambios) {
                        cargarSolicitudesProyecto();
                        mensajeInfo("Solicitud guardada con éxito.");
                        if (enviarDefinitivamente) {
                            try {
                                servicioGeneral.guardarObjeto(crearHistoricoEstadoSolicitud(solicitud, ""));
                            } catch (NullPointerException npe) {
                                npe.printStackTrace();
                                System.out.println("Error al guardar historico");
                            }
                        }
                        agregarSolicitud = false;
                        solicitudAgregada = true;
                        tipoSolicitudUsada = (long) 0;
                        inicializarNuevaSolicitud(null);
                        cargarSolicitudesProyecto();
                    } else {
                        mensajeError("No ha sido guardada su solicitud.");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            eliminarDetallesDefinitivamente();
        } else {
            mensajeError("Ya existe una solicitud de prorroga pendiente por revisión.");
        }
    }

    /**
     * Borrar solicitud investigador.
     *
     * @param solicitudInvestigador
     *            the solicitud investigador
     */
    public void borrarSolicitudInvestigador(SolicitudInvestigador solicitudInvestigador) {
        solicitudInvestigador.setEstado(SolicitudInvestigador.BORRADO);
        solicitudInvestigador.setFechaBorrado(new Date());
        servicioGeneral.guardarObjeto(solicitudInvestigador);
    }
    
    public void borrarSolicitudProrrogaInvestigador(SolicitudProrrogaInvestigador solicitudInvestigador) {
        servicioGeneral.eliminarObjeto(solicitudInvestigador);
    }

    /**
     * Existe prorroga pendiente.
     *
     * @return true, if successful
     */
    private boolean existeProrrogaPendiente() {
        if (tipoSolicitudUsada.equals(TipoSolicitud.PRORROGA) && listaSolicitudesProyecto != null
                && listaSolicitudesProyecto.size() > 0) {
            Iterator<Solicitud> i = listaSolicitudesProyecto.iterator();
            boolean existe = false;
            while (i.hasNext()) {
                Solicitud solicitud = i.next();
                if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.PRORROGA)
                        && solicitud.getRespuesta() == null) {
                    existe = true;
                }
            }
            return existe;
        } else {
            return false;
        }
    }

    /**
     * Eliminar detalles definitivamente.
     */
    private void eliminarDetallesDefinitivamente() {
        if (listaDetalleEliminado != null && listaDetalleEliminado.size() > 0) {
            Iterator<DetalleCambioRubro> i = listaDetalleEliminado.iterator();
            while (i.hasNext()) {
                DetalleCambioRubro detalleCambioRubro = i.next();
                if (detalleCambioRubro.getId() != null) {
                    servicioGeneral.eliminarObjeto(detalleCambioRubro);
                }
            }
            cargarSolicitudesProyecto();
            listaDetalleEliminado = null;
        }
    }

    /**
     * Enviar solicitud.
     *
     * @param solicitud
     *            the solicitud
     * @param asesor
     *            the asesor
     * @return true, if successful
     */
    private boolean enviarSolicitud(Solicitud solicitud, Persona asesor) {
        AlertaProyecto alertaProyecto = new AlertaProyecto();
        alertaProyecto.setAsesor(asesor);
        alertaProyecto.setEstado("P");
        alertaProyecto.setFechaGenera(solicitud.getFecha());
        alertaProyecto.setMensaje("Solicitud " + solicitud.getTipoSolicitud().getNombre());
        alertaProyecto.setProyecto(proyectoActual);
        alertaProyecto.setSolicitud(solicitud);
        this.servicioGeneral.guardarObjeto(alertaProyecto);
        CorreoPlantilla correoPlantilla = servicioCorreo.obtenerPlantillaCorreoCompleta(137L);
        String cuerpo = correoPlantilla.getCuerpo();
        String idSolicitud = solicitud.getId().toString();
        String asunto = correoPlantilla.getAsunto().replaceAll("<<ID_SOLICITUD>>", idSolicitud);
        Correo correo = new Correo();
        correo.setOrigen(Correo.CORREO_HERMES);
        correo.adicionarDireccion(asesor.getEmail());
        //correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
        correo.setAsunto(asunto);
        String cuerpoCorreo = cuerpo.replaceAll("<<COORDINADOR>>", asesor.getNombreCompleto());
        cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID_PROYECTO>>", proyectoActual.getId().toString());
        cuerpoCorreo = cuerpoCorreo.replaceAll("<<TIPO_SOLICITUD>>", solicitud.getTipoSolicitud().getNombre());
        cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID_SOLICITUD>>", idSolicitud);
        correo.setCuerpo(cuerpoCorreo);
        servicioCorreo.enviarCorreo(correo);
        return false;
    }

    /**
     * Eliminar documento.
     */
    public void eliminarDocumento() {
        if (archivoSeleccionado != null) {
            nuevaSolicitud.getDocumentos().remove(archivoSeleccionado);
            servicioGeneral.eliminarObjeto(archivoSeleccionado);
            try {
                eliminarArchivoSolicitudGenerico(archivoSeleccionado.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Inicializar detalle solicitud.
     */
    public void inicializarDetalleSolicitud() {
        Financiacion financiacion = new Financiacion();
        financiacion.setId(null);
        Gasto gasto = new Gasto();
        gasto.setId(null);
        gasto.setFinanciacion(financiacion);
        TipoRubro tipoRubro = new TipoRubro();
        tipoRubro.setId(null);
        DetalleSolicitud detalleSolicitud = new DetalleSolicitud();
        detalleSolicitud.setGasto(gasto);
        detalleSolicitud.setTipoRubro(tipoRubro);
        detalleSolicitud.setSolicitud(nuevaSolicitud);
        nuevaSolicitud.setDetalleSolicitud(detalleSolicitud);
    }

    /**
     * Eliminar cambio rubro.
     */
    public void eliminarCambioRubro() {
        if (detalleCambioRubroSeleccionado != null) {
            nuevaSolicitud.getDetalleCambioRubro().remove(detalleCambioRubroSeleccionado);
            if (listaDetalleEliminado == null) {
                listaDetalleEliminado = new ArrayList<DetalleCambioRubro>();
            }
            listaDetalleEliminado.add(detalleCambioRubroSeleccionado);
        }
    }

    /**
     * Cargar rubros destino.
     */
    public void cargarRubrosDestino() {
		tipoRubroOrigenCambioRubros = null;
        listaFinalRubros = new ArrayList<RubroFinanciable>();
        List<RubroFinanciable> listaTiposRubroConvocatoria;
        if (proyectoActual.getModalidad() instanceof Convocatoria) {
            
                listaTiposRubroConvocatoria = this.servicioModalidad
                        .obtenerRubrosFinanciablesModalidad(proyectoActual.getModalidad().getId());
                tiposRubroItem = new ArrayList<SelectItem>();
                tiposRubroItem.add(new SelectItem("", ""));
                for (int i = 1; i < listaTiposRubroConvocatoria.size() + 1; i++) {
                    RubroFinanciable rubro = (RubroFinanciable) listaTiposRubroConvocatoria.get(i - 1);
					if ((rubro.getTipoRubro().getDescripcion() != null
							&& rubro.getTipoRubro().getDescripcion().equals("GASTOS_CP_2022"))
							|| "142,145,144,143,82,141,135,7,120,".contains(rubro.getTipoRubro().getId() + ",")) {
						tiposRubroItem
								.add(new SelectItem(rubro.getTipoRubro().getId(), rubro.getTipoRubro().getNombre()));
					}
                }
            

                Long idGastoSeleccionado = nuevaSolicitud.getDetalleSolicitud().getGasto().getId();
                List<Gasto> listaGastoSeleccionado = servicioGeneral.obtenerObjetos(Gasto.class,
                        "from Gasto where id ='" + idGastoSeleccionado + "'");
                if(!esListaVacia(listaGastoSeleccionado)){
					Gasto gastoSeleccionado = (Gasto) listaGastoSeleccionado.get(0);
					if (gastoSeleccionado.getTipoRubro().getDescripcion() != null
							&& gastoSeleccionado.getTipoRubro().getDescripcion().equals("GASTOS_CP_2022")) {
						tipoRubroOrigenCambioRubros = (TipoRubro) servicioGeneral.obtenerObjetoYPadre(new TipoRubro(),
								gastoSeleccionado.getTipoRubro().getId());
						tipoRubroOrigenCambioRubros.setPadre((TipoRubro) servicioGeneral
								.obtenerObjetoYPadre(new TipoRubro(), tipoRubroOrigenCambioRubros.getPadre().getId()));
					} else {
						tipoRubroOrigenCambioRubros = gastoSeleccionado.getTipoRubro();
						if ("142,145,144,143,82,141,135,7,120,"
								.contains(gastoSeleccionado.getTipoRubro().getId() + ",")) {
							tipoRubroOrigenCambioRubros.setPadre(
									(TipoRubro) servicioGeneral.obtenerObjetoXID(TipoRubro.class, "53").get(0));
						}
					}
					
					
					List<TipoRubro> listaTiposRubro = this.servicioModalidad
                        .obtenerRubros(gastoSeleccionado.getTipoRubro().getId(), 1);
                    for (int i = 0; i < listaTiposRubro.size(); i++) {
                        TipoRubro tipoRubro1 = (TipoRubro) listaTiposRubro.get(i);
                        for (int j = 0; j < listaTiposRubroConvocatoria.size(); j++) {
                            RubroFinanciable rubroFinanciable1 = (RubroFinanciable) listaTiposRubroConvocatoria.get(j);
                            if (tipoRubro1.getId().intValue() == rubroFinanciable1.getTipoRubro().getId().intValue()) {
                                listaFinalRubros.add(rubroFinanciable1);
                            }
                        }
                    }
                    if (listaFinalRubros.size() > 0) {
                        tiposRubroItem = new ArrayList<SelectItem>();
                        tiposRubroItem.add(new SelectItem("", ""));
						for (int i = 1; i < listaFinalRubros.size() + 1; i++) {
							RubroFinanciable rubro = (RubroFinanciable) listaFinalRubros.get(i - 1);
							if ((rubro.getTipoRubro().getDescripcion()!=null && rubro.getTipoRubro().getDescripcion().equals("GASTOS_CP_2022"))
									|| "142,145,144,143,82,141,135,7,120,"
											.contains(rubro.getTipoRubro().getId() + ",")) {
								tiposRubroItem.add(
										new SelectItem(rubro.getTipoRubro().getId(), rubro.getTipoRubro().getNombre()));
							}
						}
                    }
                    
                    
                }
            
        } else {
            listaTiposRubro = null;
            tiposRubroItem = new ArrayList<SelectItem>();
            tiposRubroItem.add(new SelectItem("", ""));
        }

        /**
         * Cargar gastos proyecto.
         */
    }
    
	public void cargarInfoRubroDestinoAP() {
		if (!esCadenaVacia(tipoRubroAuxSolAdicionPresupuesto.getId().toString())
				&& !tipoRubroAuxSolAdicionPresupuesto.getId().equals(0L)) {
			tipoRubroAuxSolAdicionPresupuesto = (TipoRubro) servicioGeneral
					.obtenerObjetoXID(TipoRubro.class, tipoRubroAuxSolAdicionPresupuesto.getId().toString()).get(0);
			if (tipoRubroAuxSolAdicionPresupuesto.getDescripcion() != null
					&& tipoRubroAuxSolAdicionPresupuesto.getDescripcion().equals("GASTOS_CP_2022")) {
				tipoRubroAuxSolAdicionPresupuesto = (TipoRubro) servicioGeneral.obtenerObjetoYPadre(new TipoRubro(),
						tipoRubroAuxSolAdicionPresupuesto.getId());
				tipoRubroAuxSolAdicionPresupuesto.setPadre((TipoRubro) servicioGeneral
						.obtenerObjetoYPadre(new TipoRubro(), tipoRubroAuxSolAdicionPresupuesto.getPadre().getId()));
			} else if ("142,145,144,143,82.141,135,7,120,".contains(tipoRubroAuxSolAdicionPresupuesto.getId() + ",")) {
				tipoRubroAuxSolAdicionPresupuesto
						.setPadre((TipoRubro) servicioGeneral.obtenerObjetoXID(TipoRubro.class, "53").get(0));
			}
		}
	}
    
	public void cargarInfoRubroDestino() {
		if (!esCadenaVacia(nuevaSolicitud.getDetalleSolicitud().getTipoRubro().getId().toString())
				&& !nuevaSolicitud.getDetalleSolicitud().getTipoRubro().getId().equals(0L)) {
			tipoRubroDestinoCambioRubros = (TipoRubro) servicioGeneral.obtenerObjetoXID(TipoRubro.class,
					nuevaSolicitud.getDetalleSolicitud().getTipoRubro().getId().toString()).get(0);
			if (tipoRubroDestinoCambioRubros.getDescripcion() != null
					&& tipoRubroDestinoCambioRubros.getDescripcion().equals("GASTOS_CP_2022")) {
				tipoRubroDestinoCambioRubros = (TipoRubro) servicioGeneral.obtenerObjetoYPadre(new TipoRubro(),
						tipoRubroDestinoCambioRubros.getId());
				tipoRubroDestinoCambioRubros.setPadre((TipoRubro) servicioGeneral.obtenerObjetoYPadre(new TipoRubro(),
						tipoRubroDestinoCambioRubros.getPadre().getId()));
			} else if ("142,145,144,143,82,141,135,7,120,".contains(tipoRubroDestinoCambioRubros.getId() + ",")) {
				tipoRubroDestinoCambioRubros
						.setPadre((TipoRubro) servicioGeneral.obtenerObjetoXID(TipoRubro.class, "53").get(0));
			}
		} else {
			tipoRubroDestinoCambioRubros = null;
		}
	}

    /**
     * Cargar gastos proyecto.
     */
    public void cargarGastosProyecto() {
        List<Gasto> listaGastosProyecto;
        Solicitud solicitud = nuevaSolicitud;
        Long idFinacinacion = nuevaSolicitud.getDetalleSolicitud().getGasto().getFinanciacion().getId();
        if (idFinacinacion != null && idFinacinacion != 0) {
            listaGastosProyecto = this.servicioProyecto.obtenerGastosProyectoFinanciacion(idFinacinacion);
            gastosProyectoItem = new SelectItem[listaGastosProyecto.size() + 1];
            gastosProyectoItem[0] = new SelectItem("", "");
            for (int i = 1; i < listaGastosProyecto.size() + 1; i++) {
                Gasto gasto = (Gasto) listaGastosProyecto.get(i - 1);
                String nombreGasto = gasto.getTipoRubro().getNombre();
                /*if (gasto.getDescripcion() != null) {
                    nombreGasto += " - " + obtenerPrimerosCaracters(gasto.getDescripcion(), 40);
                }*/
                gastosProyectoItem[i] = new SelectItem(gasto.getId(), nombreGasto);
            }
        } else {
            gastosProyectoItem = new SelectItem[1];
            gastosProyectoItem[0] = new SelectItem("", "");
        }
        if (solicitud.getDetalleSolicitud() != null) {
            Gasto gasto = solicitud.getDetalleSolicitud().getGasto();
            gasto.setId(null);
            TipoRubro tipoRubro = new TipoRubro();
            tipoRubro.setId(null);
            DetalleSolicitud detalleSolicitud = solicitud.getDetalleSolicitud();
            detalleSolicitud.setGasto(gasto);
            detalleSolicitud.setTipoRubro(tipoRubro);
            detalleSolicitud.setSolicitud(solicitud);
            solicitud.setDetalleSolicitud(detalleSolicitud);
        }

        /**
         * Obtener primeros caracters.
         *
         * @param cadena
         *            the cadena
         * @param maximo
         *            the maximo
         * @return the string
         */
    }

    /**
     * Obtener primeros caracters.
     *
     * @param cadena
     *            the cadena
     * @param maximo
     *            the maximo
     * @return the string
     */
    private String obtenerPrimerosCaracters(String cadena, int maximo) {
        if (cadena != null) {
            int numeroCaracteres = 0;
            if (cadena.length() > maximo) {
                numeroCaracteres = maximo;
            } else {
                numeroCaracteres = cadena.length();
            }
            return cadena.substring(0, numeroCaracteres);
        } else
            return "";

        /**
         * Guardar archivo.
         *
         * @param event
         *            the event
         */
    }
    
    public void agregarCiudad() {
    	
    	if(esCadenaVacia(ciudadSeleccionada)) {
    		mensajeError("Debe seleccionar una ciudad para agregar");
    		return;
    	}else {
    		boolean yaExiste = false;
    		if(nuevaSolicitud.getListaCiudades()!=null && nuevaSolicitud.getListaCiudades().size()>0) {
    			for(int i=0; i<nuevaSolicitud.getListaCiudades().size();i++) {
    				SolicitudCiudad temp = new SolicitudCiudad();
    				temp = nuevaSolicitud.getListaCiudades().get(i);
    				if(temp.getCiudad().getId().equals(ciudadSeleccionada)) {
    					yaExiste=true;
    					mensajeError("El municipio ya se encuentra en la lista");
    		    		return;
    				}
    			}
    			
    		}

    		if(!yaExiste){
	    		Ciudad city = new Ciudad();
	    		city = (Ciudad) servicioGeneral.obtenerObjetoXID(Ciudad.class, ciudadSeleccionada).get(0);
	    		SolicitudCiudad sc = new SolicitudCiudad();
	    		sc.setSolicitud(nuevaSolicitud);
	    		sc.setCiudad(city);
	    		sc.setFecha(getToday());
	    		nuevaSolicitud.getCiudades().add(sc);
	    		ciudadSeleccionada="";
	    		departamentoSeleccionado ="";
	    	}
    	}
    }
    
    public void eliminarCiudad() {
    	if(ciudad==null) {
    		mensajeError("Debe seleccionar una ciudad para eliminar");
    		return;
    	}else {
    		nuevaSolicitud.getCiudades().remove(ciudad);
    	}
    }

    /**
     * Guardar archivo.
     *
     * @param event
     *            the event
     */
    public void guardarArchivo(FileUploadEvent event) {
        boolean seleccionadotipoArchivo = true;
        if (nuevaSolicitud.getTipoSolicitud().getId() != null
                && (nuevaSolicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CONTRATO_INDIVIDUAL)
                        || nuevaSolicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CONTRATO_MARCO)
                        || nuevaSolicitud.getTipoSolicitud().getId().equals(TipoSolicitud.EXPORTACION_IMPORTACION)
                        || nuevaSolicitud.getTipoSolicitud().getId().equals(TipoSolicitud.RECOLECTA_PNN))) {
            if (tipoArchivo == null || "".equals(tipoArchivo.trim())) {
                mensajeError("Debe indicar el tipo de archivo que está adjuntando.");
                seleccionadotipoArchivo = false;
            }
        }

        if (seleccionadotipoArchivo) {
            UploadedFile archivoCargarUno = event.getFile();
            if (archivoCargarUno != null) {

                String nombreArchivo = "";
                nombreArchivo = archivoCargarUno.getFileName();
                if (nombreArchivo.length() <= 100) {

                    if (nombreArchivo.endsWith(".pdf") || nombreArchivo.endsWith(".doc")
                            || nombreArchivo.endsWith(".docx") || nombreArchivo.endsWith(".rar")
                            || nombreArchivo.endsWith(".xls") || nombreArchivo.endsWith(".xlsx")
                            || nombreArchivo.endsWith(".7z") || nombreArchivo.endsWith(".zip")
                            || nombreArchivo.endsWith(".jpg") || nombreArchivo.endsWith(".png")
                            || nombreArchivo.endsWith(".PDF") || nombreArchivo.endsWith(".DOC")
                            || nombreArchivo.endsWith(".DOCX") || nombreArchivo.endsWith(".RAR")
                            || nombreArchivo.endsWith(".XLS") || nombreArchivo.endsWith(".XLSX")
                            || nombreArchivo.endsWith(".7Z") || nombreArchivo.endsWith(".ZIP")
                            || nombreArchivo.endsWith(".JPG") || nombreArchivo.endsWith(".PNG")
                            || nombreArchivo.endsWith(".JPEG") || nombreArchivo.endsWith(".jpeg")) {

                        SolicitudDocumento solDoc2 = new SolicitudDocumento();
                        TipoArchivo tipo = new TipoArchivo();
                        if (tipoArchivo != null && !"".equals(tipoArchivo.trim())) {
                            List listaAr = servicioGeneral.obtenerObjetoXID("TipoArchivo", tipoArchivo + "");
                            tipo = (TipoArchivo) listaAr.get(0);
                        }
                        solDoc2 = insertarArchivoSolicitudGenerico(1, archivoCargarUno, tipo);
                        
                        nuevaSolicitud.getDocumentos().add(solDoc2);

                    }
                }
            }
        }
        /**
         * Descargar documento.
         */
    }

    /**
     * Descargar documento.
     */
    public void descargarDocumento() {
        SolicitudDocumento solDoc = archivoSeleccionado;
        try {

            descargarArchivoSolicitudGenerico(solDoc);

        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * Certificado movilidacion.
         */
    }

    /**
     * Cargar opciones sedes.
     */
    private void cargarOpcionesSedes() {
        List<Dependencia> listaSedes;
        if (listaSedesItem != null && listaSedesItem.length > 0) {
            return;
        } else {
            listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
            listaSedesItem = new SelectItem[listaSedes.size()];
            for (int i = 0; i < listaSedes.size(); i++) {
                Dependencia sede = (Dependencia) listaSedes.get(i);
                String nombreSede = sede.getNombre().substring(0, 1).toUpperCase()
                        + sede.getNombre().substring(1, sede.getNombre().length());
                listaSedesItem[i] = new SelectItem(sede.getId(), nombreSede);
            }
        }
        /**
         * Cargar opciones tipo investigador.
         */
    }

    /**
     * Cargar opciones sedes.
     */
    private void cargarOpcionesTipoInvestigador() {
        if (tiposInvestigadorItem != null && tiposInvestigadorItem.length > 0) {
            return;
        } else {
            Convocatoria convocatoria = (Convocatoria) proyectoActual.getModalidad();
            // Tipo de vinculación
            listaTiposInvestigador = obtenerTipoVinculacionConvocatoria(convocatoria,true);
            tiposInvestigadorItem = new SelectItem[listaTiposInvestigador.size()];
            for (int i = 0; i < listaTiposInvestigador.size(); i++) {
                TipoInvestigador tipoInvestigador = (TipoInvestigador) listaTiposInvestigador.get(i);
                tiposInvestigadorItem[i] = new SelectItem(tipoInvestigador.getId(), tipoInvestigador.getNombre());
            }
        }
        /**
         * Agregar detalle cambio rubro.
         */
    }

    /**
     * Agregar detalle cambio rubro.
     */
    public void agregarDetalleCambioRubro() {

        String resultado = "";

        if (resultado.equals("")) {

            Solicitud solicitud = nuevaSolicitud;

            if (solicitud.getDetalleSolicitud().getValor() != null
                    && solicitud.getDetalleSolicitud().getValor().intValue() > 0) {
                DetalleCambioRubro detalleCambioRubro = new DetalleCambioRubro();
                List<Gasto> listaGasto = servicioGeneral.obtenerObjetos(Gasto.class,
                        "from Gasto where id ='" + solicitud.getDetalleSolicitud().getGasto().getId() + "'");
                if (listaGasto != null && listaGasto.size() > 0) {
                    Gasto gastoActual = (Gasto) listaGasto.get(0);
                    boolean suficienteDinero = true;
                    Long valorActual = 0L;
                    if (informacionFinaciera != null && informacionFinaciera.size() > 0) {
                        Iterator<Object[]> j = informacionFinaciera.iterator();
                        while (j.hasNext()) {
                            Object[] financiacionObject = j.next();
                            if (financiacionObject[1] != null) {
                                List<Object[]> gastosObject = (List<Object[]>) financiacionObject[1];
                                if (gastosObject.size() > 0) {
                                    Iterator<Object[]> k = gastosObject.iterator();
                                    while (k.hasNext()) {
                                        Object[] gastoObjeto = k.next();
                                        Gasto gasto = (Gasto) gastoObjeto[0];
                                        if (gastoActual.getId().equals(gasto.getId())) {
                                            valorActual = gasto.getSumaCampos() + (Long) gastoObjeto[1]
                                                    - (Long) gastoObjeto[2];
                                            if (solicitud.getDetalleSolicitud().getValor() > valorActual) {
                                                suficienteDinero = false;
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(suficienteDinero){
                        List<TipoRubro> listaTipoRubro = servicioGeneral.obtenerObjetos(TipoRubro.class,
                                "from TipoRubro where id ='" + solicitud.getDetalleSolicitud().getTipoRubro().getId()
                                        + "'");
                        if (listaTipoRubro != null && listaTipoRubro.size() > 0) {
                            TipoRubro tipoRubroActual = (TipoRubro) listaTipoRubro.get(0);
                            detalleCambioRubro.setSolicitud(solicitud);
                            detalleCambioRubro.setGasto(gastoActual);
                            detalleCambioRubro.setTipoRubro(tipoRubroActual);
                            detalleCambioRubro.setValor(solicitud.getDetalleSolicitud().getValor());
                            solicitud.getDetalleCambioRubro().add(detalleCambioRubro);
    
                            gastosProyectoItem = null;
                            nuevaSolicitud.getDetalleSolicitud().getGasto().getFinanciacion().setId((long) 0);
                            nuevaSolicitud.getDetalleSolicitud().getTipoRubro().setId(0L);
                            nuevaSolicitud.getDetalleSolicitud().setValor(0L);
                            
                        } else {
                            mensajeError("Debe seleccionar un rubro destino.");
                        }
                    }
                    else{
                        mensajeError("El valor actual del rubro " + gastoActual.getTipoRubro().getNombre() + " es $"+ valorActual + ". Por favor ingrese un numero igual o menor.");
                    }
                } else {
                    mensajeError("Debe seleccionar un rubro origen.");
                }
            } else {
                mensajeError("El valor a transferir entre los rubros debe ser mayor a cero..");
            }
        }

        /**
         * Editar solicitud tabla.
         */
    }

    /**
     * Editar solicitud tabla.
     */
    public void editarSolicitudTabla() {
        editarSolicitud(solicitudSeleccionada);
        /**
         * Eliminar solicitud tabla.
         */
    }

    /**
     * Eliminar solicitud tabla.
     */
    public void eliminarSolicitudTabla() {
        solicitudSeleccionada.setRespuesta(Solicitud.BORRADA);
        solicitudSeleccionada.setFechaEliminacion(new Date());
        servicioGeneral.guardarObjeto(solicitudSeleccionada);
        agregarSolicitud = false;
        cargarSolicitudesProyecto();
        /**
         * Editar solicitud.
         *
         * @param solicitud
         *            the solicitud
         */
    }

    /**
     * Editar solicitud.
     *
     * @param solicitud
     *            the solicitud
     */
    private void editarSolicitud(Solicitud solicitud) {
        inicializarNuevaSolicitud(solicitud);
        vistaSolicitud = null;
        agregarSolicitud = true;
        /**
         * Gets the lista solicitudes proyecto.
         *
         * @return the lista solicitudes proyecto
         */
    }

    /**
     * Gets the lista solicitudes proyecto.
     *
     * @return the lista solicitudes proyecto
     */
    public List<Solicitud> getListaSolicitudesProyecto() {
        return listaSolicitudesProyecto;
        /**
         * Gets the tipos solicitud item.
         *
         * @return the tipos solicitud item
         */
    }

    /**
     * Gets the tipos solicitud item.
     *
     * @return the tipos solicitud item
     */
    public SelectItem[] getTiposSolicitudItem() {
        return tiposSolicitudItem;
        /**
         * Gets the tipos rubro item.
         *
         * @return the tipos rubro item
         */
    }

    /**
     * Gets the tipos rubro item.
     *
     * @return the tipos rubro item
     */
    public ArrayList<SelectItem> getTiposRubroItem() {
        return tiposRubroItem;
        /**
         * Gets the gastos proyecto item.
         *
         * @return the gastos proyecto item
         */
    }

    /**
     * Gets the gastos proyecto item.
     *
     * @return the gastos proyecto item
     */
    public SelectItem[] getGastosProyectoItem() {
        return gastosProyectoItem;
        /**
         * Gets the fuentes proyecto item.
         *
         * @return the fuentes proyecto item
         */
    }

    /**
     * Gets the fuentes proyecto item.
     *
     * @return the fuentes proyecto item
     */
    public ArrayList<SelectItem> getFuentesProyectoItem() {
        return fuentesProyectoItem;
        /**
         * Gets the lista sedes item.
         *
         * @return the lista sedes item
         */
    }

    /**
     * Gets the lista sedes item.
     *
     * @return the lista sedes item
     */
    public SelectItem[] getListaSedesItem() {
        return listaSedesItem;
        /**
         * Sets the solicitud seleccionada.
         *
         * @param solicitudSeleccionada
         *            the new solicitud seleccionada
         */
    }

    /**
     * Sets the solicitud seleccionada.
     *
     * @param solicitudSeleccionada
     *            the new solicitud seleccionada
     */
    public void setSolicitudSeleccionada(Solicitud solicitudSeleccionada) {
        this.solicitudSeleccionada = solicitudSeleccionada;
        /**
         * Gets the solicitud seleccionada.
         *
         * @return the solicitud seleccionada
         */
    }

    /**
     * Gets the solicitud seleccionada.
     *
     * @return the solicitud seleccionada
     */
    public Solicitud getSolicitudSeleccionada() {
        return solicitudSeleccionada;
        /**
         * Gets the vista solicitud.
         *
         * @return the vista solicitud
         */
    }

    /**
     * Gets the vista solicitud.
     *
     * @return the vista solicitud
     */
    public Solicitud getVistaSolicitud() {
        return vistaSolicitud;
        /**
         * Checks if is agregar solicitud.
         *
         * @return true, if is agregar solicitud
         */
    }

    /**
     * Checks if is agregar solicitud.
     *
     * @return true, if is agregar solicitud
     */
    public boolean isAgregarSolicitud() {
        return (agregarSolicitud && !isExisteVistaSolicitud());
        /**
         * Sets the nueva solicitud.
         *
         * @param nuevaSolicitud
         *            the new nueva solicitud
         */
    }

    /**
     * Sets the nueva solicitud.
     *
     * @param nuevaSolicitud
     *            the new nueva solicitud
     */
    public void setNuevaSolicitud(Solicitud nuevaSolicitud) {
        this.nuevaSolicitud = nuevaSolicitud;
        /**
         * Gets the nueva solicitud.
         *
         * @return the nueva solicitud
         */
    }

    /**
     * Gets the nueva solicitud.
     *
     * @return the nueva solicitud
     */
    public Solicitud getNuevaSolicitud() {
        return nuevaSolicitud;
        /**
         * Gets the fecha actual.
         *
         * @return the fecha actual
         */
    }

    /**
     * Gets the fecha actual.
     *
     * @return the fecha actual
     */
    public String getFechaActual() {
        return fechaActual;
        /**
         * Sets the tipo solicitud usada.
         *
         * @param tipoSolicitudUsada
         *            the new tipo solicitud usada
         */
    }

    /**
     * Sets the tipo solicitud usada.
     *
     * @param tipoSolicitudUsada
     *            the new tipo solicitud usada
     */
    public void setTipoSolicitudUsada(Long tipoSolicitudUsada) {
        this.tipoSolicitudUsada = tipoSolicitudUsada;
        /**
         * Gets the tipo solicitud usada.
         *
         * @return the tipo solicitud usada
         */
    }

    /**
     * Gets the tipo solicitud usada.
     *
     * @return the tipo solicitud usada
     */
    public Long getTipoSolicitudUsada() {
        return tipoSolicitudUsada;
        /**
         * Sets the archivo seleccionado.
         *
         * @param archivoSeleccionado
         *            the new archivo seleccionado
         */
    }

    /**
     * Sets the archivo seleccionado.
     *
     * @param archivoSeleccionado
     *            the new archivo seleccionado
     */
    public void setArchivoSeleccionado(SolicitudDocumento archivoSeleccionado) {
        this.archivoSeleccionado = archivoSeleccionado;
        /**
         * Gets the archivo seleccionado.
         *
         * @return the archivo seleccionado
         */
    }

    /**
     * Gets the archivo seleccionado.
     *
     * @return the archivo seleccionado
     */
    public SolicitudDocumento getArchivoSeleccionado() {
        return archivoSeleccionado;
        /**
         * Sets the detalle cambio rubro seleccionado.
         *
         * @param detalleCambioRubroSeleccionado
         *            the new detalle cambio rubro seleccionado
         */
    }

    /**
     * Sets the detalle cambio rubro seleccionado.
     *
     * @param detalleCambioRubroSeleccionado
     *            the new detalle cambio rubro seleccionado
     */
    public void setDetalleCambioRubroSeleccionado(DetalleCambioRubro detalleCambioRubroSeleccionado) {
        this.detalleCambioRubroSeleccionado = detalleCambioRubroSeleccionado;
        /**
         * Gets the detalle cambio rubro seleccionado.
         *
         * @return the detalle cambio rubro seleccionado
         */
    }

    /**
     * Gets the detalle cambio rubro seleccionado.
     *
     * @return the detalle cambio rubro seleccionado
     */
    public DetalleCambioRubro getDetalleCambioRubroSeleccionado() {
        return detalleCambioRubroSeleccionado;
        /**
         * Checks if is solicitud agregada.
         *
         * @return true, if is solicitud agregada
         */
    }

    /**
     * Checks if is solicitud agregada.
     *
     * @return true, if is solicitud agregada
     */
    public boolean isSolicitudAgregada() {
        if (solicitudAgregada == true) {
            solicitudAgregada = false;
            return true;
        }
        return solicitudAgregada;
        /**
         * Gets the proyecto actual.
         *
         * @return the proyecto actual
         */
    }

    /**
     * Gets the proyecto actual.
     *
     * @return the proyecto actual
     */
    public Proyecto getProyectoActual() {
        return proyectoActual;
        /**
         * Gets the tipo documento item.
         *
         * @return the tipo documento item
         */
    }

    /**
     * Gets the tipo documento item.
     *
     * @return the tipoDocumentoItem
     */
    public List<SelectItem> getTipoDocumentoItem() {
        return tipoDocumentoItem;
        /**
         * Gets the tipo documento.
         *
         * @return the tipo documento
         */
    }

    /**
     * Gets the tipo documento.
     *
     * @return the tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
        /**
         * Sets the tipo documento.
         *
         * @param tipoDocumento
         *            the new tipo documento
         */
    }

    /**
     * Sets the tipo documento.
     *
     * @param tipoDocumento
     *            the tipoDocumento to set
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        /**
         * Gets the documento identidad.
         *
         * @return the documento identidad
         */
    }

    /**
     * Gets the documento identidad.
     *
     * @return the documentoIdentidad
     */
    public String getDocumentoIdentidad() {
        return documentoIdentidad;
        /**
         * Sets the documento identidad.
         *
         * @param documentoIdentidad
         *            the new documento identidad
         */
    }

    /**
     * Sets the documento identidad.
     *
     * @param documentoIdentidad
     *            the documentoIdentidad to set
     */
    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
        /**
         * Gets the tiempo dedicacion semanal.
         *
         * @return the tiempo dedicacion semanal
         */
    }

    /**
     * Gets the tiempo dedicacion semanal.
     *
     * @return the tiempoDedicacionSemanal
     */
    public double getTiempoDedicacionSemanal() {
        return tiempoDedicacionSemanal;
        /**
         * Sets the tiempo dedicacion semanal.
         *
         * @param tiempoDedicacionSemanal
         *            the new tiempo dedicacion semanal
         */
    }

    /**
     * Sets the tiempo dedicacion semanal.
     *
     * @param tiempoDedicacionSemanal
     *            the tiempoDedicacionSemanal to set
     */
    public void setTiempoDedicacionSemanal(double tiempoDedicacionSemanal) {
        this.tiempoDedicacionSemanal = tiempoDedicacionSemanal;
        /**
         * Gets the tiempo dedicacion total.
         *
         * @return the tiempo dedicacion total
         */
    }

    /**
     * Gets the tiempo dedicacion total.
     *
     * @return the tiempoDedicacionTotal
     */
    public String getTiempoDedicacionTotal() {
        return tiempoDedicacionTotal;
        /**
         * Sets the tiempo dedicacion total.
         *
         * @param tiempoDedicacionTotal
         *            the new tiempo dedicacion total
         */
    }

    /**
     * Sets the tiempo dedicacion total.
     *
     * @param tiempoDedicacionTotal
     *            the tiempoDedicacionTotal to set
     */
    public void setTiempoDedicacionTotal(String tiempoDedicacionTotal) {
        this.tiempoDedicacionTotal = tiempoDedicacionTotal;
        /**
         * Gets the solicitud investigador principal.
         *
         * @return the solicitud investigador principal
         */
    }

    /**
     * Gets the solicitud investigador principal.
     *
     * @return the solicitudInvestigadorPrincipal
     */
    public SolicitudInvestigador getSolicitudInvestigadorPrincipal() {
        return solicitudInvestigadorPrincipal;
        /**
         * Sets the solicitud investigador principal.
         *
         * @param solicitudInvestigadorPrincipal
         *            the new solicitud investigador principal
         */
    }

    /**
     * Sets the solicitud investigador principal.
     *
     * @param solicitudInvestigadorPrincipal
     *            the solicitudInvestigadorPrincipal to set
     */
    public void setSolicitudInvestigadorPrincipal(SolicitudInvestigador solicitudInvestigadorPrincipal) {
        this.solicitudInvestigadorPrincipal = solicitudInvestigadorPrincipal;
        /**
         * Gets the tipo investigador.
         *
         * @return the tipo investigador
         */
    }

    /**
     * Gets the tipo investigador.
     *
     * @return the tipoInvestigador
     */
    public String getTipoInvestigador() {
        return tipoInvestigador;
        /**
         * Sets the tipo investigador.
         *
         * @param tipoInvestigador
         *            the new tipo investigador
         */
    }

    /**
     * Sets the tipo investigador.
     *
     * @param tipoInvestigador
     *            the tipoInvestigador to set
     */
    public void setTipoInvestigador(String tipoInvestigador) {
        this.tipoInvestigador = tipoInvestigador;
        /**
         * Gets the tipos investigador item.
         *
         * @return the tipos investigador item
         */
    }

    /**
     * Gets the tipos investigador item.
     *
     * @return the tiposInvestigadorItem
     */
    public SelectItem[] getTiposInvestigadorItem() {
        return tiposInvestigadorItem;
        /**
         * Gets the lista investigadores nuevos.
         *
         * @return the lista investigadores nuevos
         */
    }

    /**
     * Gets the lista investigadores nuevos.
     *
     * @return the listaInvestigadoresNuevos
     */
    public List<SolicitudInvestigador> getListaInvestigadoresNuevos() {
        return listaInvestigadoresNuevos;
        /**
         * Gets the solicitud investigador seleccionada.
         *
         * @return the solicitud investigador seleccionada
         */
    }

    /**
     * Gets the solicitud investigador seleccionada.
     *
     * @return the solicitudInvestigadorSeleccionada
     */
    public SolicitudInvestigador getSolicitudInvestigadorSeleccionada() {
        return solicitudInvestigadorSeleccionada;
        /**
         * Sets the solicitud investigador seleccionada.
         *
         * @param solicitudInvestigadorSeleccionada
         *            the new solicitud investigador seleccionada
         */
    }

    /**
     * Sets the solicitud investigador seleccionada.
     *
     * @param solicitudInvestigadorSeleccionada
     *            the solicitudInvestigadorSeleccionada to set
     */
    public void setSolicitudInvestigadorSeleccionada(SolicitudInvestigador solicitudInvestigadorSeleccionada) {
        this.solicitudInvestigadorSeleccionada = solicitudInvestigadorSeleccionada;
        /**
         * Gets the investigador proyecto.
         *
         * @return the investigador proyecto
         */
    }

    /**
     * Gets the investigador proyecto.
     *
     * @return the investigadorProyecto
     */
    public String getInvestigadorProyecto() {
        return investigadorProyecto;
        /**
         * Sets the investigador proyecto.
         *
         * @param investigadorProyecto
         *            the new investigador proyecto
         */
    }

    /**
     * Sets the investigador proyecto.
     *
     * @param investigadorProyecto
     *            the investigadorProyecto to set
     */
    public void setInvestigadorProyecto(String investigadorProyecto) {
        this.investigadorProyecto = investigadorProyecto;
        /**
         * Gets the investigadores proyecto item.
         *
         * @return the investigadores proyecto item
         */
    }

    /**
     * Gets the investigadores proyecto item.
     *
     * @return the investigadores proyecto item
     */
    public List<SelectItem> getInvestigadoresProyectoItem() {
        return investigadoresProyectoItem;
        /**
         * Gets the lista investigadores eliminar proyecto.
         *
         * @return the lista investigadores eliminar proyecto
         */
    }

    /**
     * Gets the lista investigadores eliminar proyecto.
     *
     * @return the listaInvestigadoresEliminarProyecto
     */
    public List<SolicitudInvestigador> getListaInvestigadoresEliminarProyecto() {
        return listaInvestigadoresEliminarProyecto;
        /**
         * Checks if is mostrar informacion externo.
         *
         * @return true, if is mostrar informacion externo
         */
    }

    /**
     * Checks if is mostrar informacion externo.
     *
     * @return the mostrarInformacionExterno
     */
    public boolean isMostrarInformacionExterno() {
        return mostrarInformacionExterno;
        /**
         * Sets the mostrar informacion externo.
         *
         * @param mostrarInformacionExterno
         *            the new mostrar informacion externo
         */
    }

    /**
     * Sets the mostrar informacion externo.
     *
     * @param mostrarInformacionExterno
     *            the mostrarInformacionExterno to set
     */
    public void setMostrarInformacionExterno(boolean mostrarInformacionExterno) {
        this.mostrarInformacionExterno = mostrarInformacionExterno;
        /**
         * Gets the primer nombre.
         *
         * @return the primer nombre
         */
    }

    /**
     * Gets the primer nombre.
     *
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
        /**
         * Sets the primer nombre.
         *
         * @param primerNombre
         *            the new primer nombre
         */
    }

    /**
     * Sets the primer nombre.
     *
     * @param primerNombre
     *            the primerNombre to set
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
        /**
         * Gets the segundo nombre.
         *
         * @return the segundo nombre
         */
    }

    /**
     * Gets the segundo nombre.
     *
     * @return the segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
        /**
         * Sets the segundo nombre.
         *
         * @param segundoNombre
         *            the new segundo nombre
         */
    }

    /**
     * Sets the segundo nombre.
     *
     * @param segundoNombre
     *            the segundoNombre to set
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
        /**
         * Gets the primer apellido.
         *
         * @return the primer apellido
         */
    }

    /**
     * Gets the primer apellido.
     *
     * @return the primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
        /**
         * Sets the primer apellido.
         *
         * @param primerApellido
         *            the new primer apellido
         */
    }

    /**
     * Sets the primer apellido.
     *
     * @param primerApellido
     *            the primerApellido to set
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
        /**
         * Gets the segundo apellido.
         *
         * @return the segundo apellido
         */
    }

    /**
     * Gets the segundo apellido.
     *
     * @return the segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
        /**
         * Sets the segundo apellido.
         *
         * @param segundoApellido
         *            the new segundo apellido
         */
    }

    /**
     * Sets the segundo apellido.
     *
     * @param segundoApellido
     *            the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
        /**
         * Gets the genero.
         *
         * @return the genero
         */
    }

    /**
     * Gets the genero.
     *
     * @return the genero
     */
    public String getGenero() {
        return genero;
        /**
         * Sets the genero.
         *
         * @param genero
         *            the new genero
         */
    }

    /**
     * Sets the genero.
     *
     * @param genero
     *            the genero to set
     */
    public void setGenero(String genero) {
        this.genero = genero;
        /**
         * Gets the tipos genero item.
         *
         * @return the tipos genero item
         */
    }

    /**
     * Gets the tipos genero item.
     *
     * @return the tipos genero item
     */
    public SelectItem[] getTiposGeneroItem() {
        return tiposGeneroItem;
        /**
         * Gets the correo electronico.
         *
         * @return the correo electronico
         */
    }

    /**
     * Gets the correo electronico.
     *
     * @return the correoElenctronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
        /**
         * Sets the correo electronico.
         *
         * @param correoElenctronico
         *            the new correo electronico
         */
    }

    /**
     * Sets the correo electronico.
     *
     * @param correoElenctronico
     *            the correoElenctronico to set
     */
    public void setCorreoElectronico(String correoElenctronico) {
        this.correoElectronico = correoElenctronico;
        /**
         * Gets the lista investigadores consulta.
         *
         * @return the lista investigadores consulta
         */
    }

    /**
     * Gets the lista investigadores consulta.
     *
     * @return the lista investigadores consulta
     */
    public List<SolicitudInvestigador> getListaInvestigadoresConsulta() {
        return listaInvestigadoresConsulta;
        /**
         * Gets the tipos cambio contenido.
         *
         * @return the tipos cambio contenido
         */
    }

    /**
     * Gets the tipos cambio contenido.
     *
     * @return the tiposCambioContenido
     */
    public SelectItem[] getTiposCambioContenido() {
        return tiposCambioContenido;
        /**
         * Gets the tipo cambio contenido seleccionado.
         *
         * @return the tipo cambio contenido seleccionado
         */
    }

    /**
     * Gets the tipo cambio contenido seleccionado.
     *
     * @return the tipoCambioContenidoSeleccionado
     */
    public String getTipoCambioContenidoSeleccionado() {
        return tipoCambioContenidoSeleccionado;
        /**
         * Sets the tipo cambio contenido seleccionado.
         *
         * @param tipoCambioContenidoSeleccionado
         *            the new tipo cambio contenido seleccionado
         */
    }

    /**
     * Sets the tipo cambio contenido seleccionado.
     *
     * @param tipoCambioContenidoSeleccionado
     *            the tipoCambioContenidoSeleccionado to set
     */
    public void setTipoCambioContenidoSeleccionado(String tipoCambioContenidoSeleccionado) {
        this.tipoCambioContenidoSeleccionado = tipoCambioContenidoSeleccionado;
        /**
         * Gets the descripcion cambio contenido.
         *
         * @return the descripcion cambio contenido
         */
    }

    /**
     * Gets the descripcion cambio contenido.
     *
     * @return the descripcionCambioContenido
     */
    public String getDescripcionCambioContenido() {
        return descripcionCambioContenido;
        /**
         * Sets the descripcion cambio contenido.
         *
         * @param descripcionCambioContenido
         *            the new descripcion cambio contenido
         */
    }

    /**
     * Sets the descripcion cambio contenido.
     *
     * @param descripcionCambioContenido
     *            the descripcionCambioContenido to set
     */
    public void setDescripcionCambioContenido(String descripcionCambioContenido) {
        this.descripcionCambioContenido = descripcionCambioContenido;
        /**
         * Gets the detalles cambios rubro.
         *
         * @return the detalles cambios rubro
         */
    }

    /**
     * Gets the detalles cambios rubro.
     *
     * @return the detalles cambios rubro
     */
    public List<DetalleCambioRubro> getDetallesCambiosRubro() {
        return detallesCambiosRubro;
        /**
         * Gets the informacion finaciera.
         *
         * @return the informacion finaciera
         */
    }

    /**
     * Gets the informacion finaciera.
     *
     * @return the informacion finaciera
     */
    public List<Object[]> getInformacionFinaciera() {
        return informacionFinaciera;
        /**
         * Gets the instituciones apoyo item.
         *
         * @return the instituciones apoyo item
         */
    }

    /**
     * Gets the instituciones apoyo item.
     *
     * @return the instituciones apoyo item
     */
    public SelectItem[] getInstitucionesApoyoItem() {
        return institucionesApoyoItem;
        /**
         * Sets the instituciones apoyo item.
         *
         * @param institucionesApoyoItem
         *            the new instituciones apoyo item
         */
    }

    /**
     * Sets the instituciones apoyo item.
     *
     * @param institucionesApoyoItem
     *            the new instituciones apoyo item
     */
    public void setInstitucionesApoyoItem(SelectItem[] institucionesApoyoItem) {
        this.institucionesApoyoItem = institucionesApoyoItem;
        /**
         * Gets the tipo archivo item.
         *
         * @return the tipo archivo item
         */
    }

    /**
     * Gets the tipo archivo item.
     *
     * @return the tipo archivo item
     */
    public SelectItem[] getTipoArchivoItem() {
        return tipoArchivoItem;
        /**
         * Sets the tipo archivo item.
         *
         * @param tipoArchivoItem
         *            the new tipo archivo item
         */
    }

    /**
     * Sets the tipo archivo item.
     *
     * @param tipoArchivoItem
     *            the new tipo archivo item
     */
    public void setTipoArchivoItem(SelectItem[] tipoArchivoItem) {
        this.tipoArchivoItem = tipoArchivoItem;
        /**
         * Gets the tipo archivo.
         *
         * @return the tipo archivo
         */
    }

    /**
     * Gets the tipo archivo.
     *
     * @return the tipo archivo
     */
    public String getTipoArchivo() {
        return tipoArchivo;
        /**
         * Sets the tipo archivo.
         *
         * @param tipoArchivo
         *            the new tipo archivo
         */
    }

    /**
     * Sets the tipo archivo.
     *
     * @param tipoArchivo
     *            the new tipo archivo
     */
    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
        /**
         * Gets the historico estado solicitudes.
         *
         * @return the historico estado solicitudes
         */
    }

    /**
     * Gets the historico estado solicitudes.
     *
     * @return the historico estado solicitudes
     */
    public List<HistoricoEstadoSolicitud> getHistoricoEstadoSolicitudes() {
        return historicoEstadoSolicitudes;
    }
	
	public ArrayList<SelectItem> getFuentesExternasProyectoItem() {
		return fuentesExternasProyectoItem;
	}

	public void setFuentesExternasProyectoItem(
			ArrayList<SelectItem> fuentesExternasProyectoItem) {
		this.fuentesExternasProyectoItem = fuentesExternasProyectoItem;
	}
	
	public SolicitudAdicionPresupuestal getSolicitudAdicionPresupuesto() {
		return adicionPresupuesto;
	}

	public void setSolicitudAdicionPresupuesto(
			SolicitudAdicionPresupuestal adicionPresupuesto) {
		this.adicionPresupuesto = adicionPresupuesto;
	}
	
	public Long getValorSolicitudAdicionPresupuestal() {
		return valorSolicitudAdicionPresupuestal;
	}

	public void setValorSolicitudAdicionPresupuestal(
			Long valorSolicitudAdicionPresupuestal) {
		this.valorSolicitudAdicionPresupuestal = valorSolicitudAdicionPresupuestal;
	}

	public TipoRubro getTipoRubroAuxSolAdicionPresupuesto() {
		return tipoRubroAuxSolAdicionPresupuesto;
	}

	public void setTipoRubroAuxSolAdicionPresupuesto(
			TipoRubro tipoRubroAuxSolAdicionPresupuesto) {
		this.tipoRubroAuxSolAdicionPresupuesto = tipoRubroAuxSolAdicionPresupuesto;
	}

	public Long getValorRubroSolAdicionPresupuesto() {
		return valorRubroSolAdicionPresupuesto;
	}

	public void setValorRubroSolAdicionPresupuesto(
			Long valorRubroSolAdicionPresupuesto) {
		this.valorRubroSolAdicionPresupuesto = valorRubroSolAdicionPresupuesto;
	}

	public Financiacion getFinanciacionSolicitudAdicionPresupuestal() {
		return financiacionSolicitudAdicionPresupuestal;
	}

	public void setFinanciacionSolicitudAdicionPresupuestal(
			Financiacion financiacionSolicitudAdicionPresupuestal) {
		this.financiacionSolicitudAdicionPresupuestal = financiacionSolicitudAdicionPresupuestal;
	}

	public List<DetalleAdicionPresupuesto> getListaSolicitudesAdicionPresupuesto() {
		return listaSolicitudesAdicionPresupuesto;
	}

	public void setListaSolicitudesAdicionPresupuesto(
			List<DetalleAdicionPresupuesto> listaSolicitudesAdicionPresupuesto) {
		this.listaSolicitudesAdicionPresupuesto = listaSolicitudesAdicionPresupuesto;
	}

	public boolean isBloquearValorTotalAdicion() {
		return bloquearValorTotalAdicion;
	}

	public void setBloquearValorTotalAdicion(boolean bloquearValorTotalAdicion) {
		this.bloquearValorTotalAdicion = bloquearValorTotalAdicion;
	}

	public boolean isBloquearAgregarAdicionRubro() {
		return bloquearAgregarAdicionRubro;
	}

	public void setBloquearAgregarAdicionRubro(boolean bloquearAgregarAdicionRubro) {
		this.bloquearAgregarAdicionRubro = bloquearAgregarAdicionRubro;
	}

	public SolicitudAdicionPresupuestal getAdicionPresupuesto() {
		return adicionPresupuesto;
	}

	public void setAdicionPresupuesto(
			SolicitudAdicionPresupuestal adicionPresupuesto) {
		this.adicionPresupuesto = adicionPresupuesto;
	}
	
	public DetalleAdicionPresupuesto getDetalleAdicionPresupuestoSeleccionado() {
		return detalleAdicionPresupuestoSeleccionado;
	}

	public void setDetalleAdicionPresupuestoSeleccionado(
			DetalleAdicionPresupuesto detalleAdicionPresupuestoSeleccionado) {
		this.detalleAdicionPresupuestoSeleccionado = detalleAdicionPresupuestoSeleccionado;
	}

	public SelectItem[] getTipoSolicitudLabs() {
		return tipoSolicitudLabs;
	}

	public void setTipoSolicitudLabs(SelectItem[] tipoSolicitudLabs) {
		this.tipoSolicitudLabs = tipoSolicitudLabs;
	}

	public DetalleAdicionPresupuesto getDetalleAdicion() {
		return detalleAdicion;
	}

	public void setDetalleAdicion(DetalleAdicionPresupuesto detalleAdicion) {
		this.detalleAdicion = detalleAdicion;
	}

	public TipoRubro getTipoRubroOrigenCambioRubros() {
		return tipoRubroOrigenCambioRubros;
	}

	public void setTipoRubroOrigenCambioRubros(TipoRubro tipoRubroOrigenCambioRubros) {
		this.tipoRubroOrigenCambioRubros = tipoRubroOrigenCambioRubros;
	}

	public TipoRubro getTipoRubroDestinoCambioRubros() {
		return tipoRubroDestinoCambioRubros;
	}

	public void setTipoRubroDestinoCambioRubros(TipoRubro tipoRubroDestinoCambioRubros) {
		this.tipoRubroDestinoCambioRubros = tipoRubroDestinoCambioRubros;
	}

	public List<SelectItem> getInvestigadoresInternosProyectoItem() {
		return investigadoresInternosProyectoItem;
	}

	public void setInvestigadoresInternosProyectoItem(List<SelectItem> investigadoresInternosProyectoItem) {
		this.investigadoresInternosProyectoItem = investigadoresInternosProyectoItem;
	}

	public String getInvestigadorInternoProyecto() {
		return investigadorInternoProyecto;
	}

	public void setInvestigadorInternoProyecto(String investigadorInternoProyecto) {
		this.investigadorInternoProyecto = investigadorInternoProyecto;
	}

	public List<SolicitudProrrogaInvestigador> getListaInvestigadoresProrroga() {
		return listaInvestigadoresProrroga;
	}

	public void setListaInvestigadoresProrroga(List<SolicitudProrrogaInvestigador> listaInvestigadoresProrroga) {
		this.listaInvestigadoresProrroga = listaInvestigadoresProrroga;
	}

	public int getSemanasAdicionalesInvProrroga() {
		return semanasAdicionalesInvProrroga;
	}

	public void setSemanasAdicionalesInvProrroga(int semanasAdicionalesInvProrroga) {
		this.semanasAdicionalesInvProrroga = semanasAdicionalesInvProrroga;
	}	
	
	 /**
     * Agregar integrante.
     */
    public void agregarIntegranteProrroga() {
		boolean existeDirector = false;
		if(listaInvestigadoresProrroga!=null && listaInvestigadoresProrroga.size()>0) {
			for(int i=0; i < listaInvestigadoresProrroga.size(); i++) {
				SolicitudProrrogaInvestigador s = listaInvestigadoresProrroga.get(i);
				if(s.getTipo().getId().equals(InvestigadorProyecto.PRINCIPAL)) {
					existeDirector = true;
					break;
				}
			}
		}
		if(!existeDirector) {
			agregarDirectorProrroga();
		}

    	if(nuevaSolicitud.getFechaEsperadaTerminacion() == null) {
   		 mensajeError("Debe indicar primero la nueva fecha de terminación del proyecto.");
            return;
        }
    	
    	if(semanasAdicionalesInvProrroga <= 0 || esCadenaVacia(investigadorInternoProyecto)) {
    		 mensajeError("Debe seleccionar un investigador e indicar las semanas adicional que el investigador estará vinculado al proyecto.");
             return;
         }
    	
    	if(semanasAdicionalesInvProrroga > calcularSemanasAdicionalesProrroga(getFechaFinalProyecto(), nuevaSolicitud.getFechaEsperadaTerminacion())) {
   		 mensajeError("El número de semanas adicional que el investigador estará vinculado al proyecto no puede ser superior al número de semanas adicionales del proyecto.");
            return;
        }
    	
    	if(listaInvestigadoresProrroga !=null) {
    		for (int i=0; i<listaInvestigadoresProrroga.size(); i++) {
    			if(listaInvestigadoresProrroga.get(i).getInvestigador().getId().getDocumento().equals(investigadorInternoProyecto)) {
    				 mensajeError("Ya indicó para ese investigador el número de semanas adicionales");
    	             return;
    			}
    		}
    	}
    	
    	
    	InvestigadorProyecto investigadorSeleccionado = new InvestigadorProyecto();
    	if(investigadoresInternosProyecto !=null) {
    		for (int i=0; i<investigadoresInternosProyecto.size(); i++) {
    			if(investigadoresInternosProyecto.get(i).getInvestigador().getId().getDocumento().equals(investigadorInternoProyecto)) {
    				investigadorSeleccionado = investigadoresInternosProyecto.get(i);
    				break;
    			}
    		}
    	}
    	
    	SolicitudProrrogaInvestigador investigadorNuevoTiempo = new SolicitudProrrogaInvestigador();
    	investigadorNuevoTiempo.setDedicacionHorasSemana(investigadorSeleccionado.getDedicacionHorasSemana());
    	investigadorNuevoTiempo.setNumeroSemanasAdicional((short) semanasAdicionalesInvProrroga);
    	investigadorNuevoTiempo.setInvestigador(investigadorSeleccionado.getInvestigador());
    	investigadorNuevoTiempo.setTipo(investigadorSeleccionado.getTipo());
    	investigadorNuevoTiempo.setEstado(SolicitudInvestigador.NO_TRAMITADO);
    	investigadorNuevoTiempo.setFechaProrroga(nuevaSolicitud.getFechaEsperadaTerminacion());
    	InvestigadorInterno investigadorInterno = servicioPersona
                .obtenerInvestigadorInterno(investigadorSeleccionado.getInvestigador().getId());
    	if(investigadorInterno !=null && investigadorInterno.getId()!=null) {
    		investigadorNuevoTiempo.setValorPagar(calcularValorFuncionarioSemanas(investigadorSeleccionado.getDedicacionHorasSemana(),semanasAdicionalesInvProrroga,investigadorInterno.getValorHoraValidado()));
    	}else {
    		investigadorNuevoTiempo.setValorPagar(0L);
    	}
    	listaInvestigadoresProrroga.add(investigadorNuevoTiempo);
    }
    
    /**
     * Eliminar integrante prorroga.
     */
    public void eliminarIntegranteProrroga() {
        listaInvestigadoresProrroga.remove(solicitudInvestigadorProSeleccionada);
        if (solicitudInvestigadorProSeleccionada != null) {
            if (solicitudInvestigadorProSeleccionada.getId() != null) {
                listaInvestigadorProrrogaEliminado.add(solicitudInvestigadorProSeleccionada);
            }
            solicitudInvestigadorProSeleccionada = null;
        }
    }

	public List<InvestigadorProyecto> getInvestigadoresInternosProyecto() {
		return investigadoresInternosProyecto;
	}

	public void setInvestigadoresInternosProyecto(List<InvestigadorProyecto> investigadoresInternosProyecto) {
		this.investigadoresInternosProyecto = investigadoresInternosProyecto;
	}

	public SolicitudProrrogaInvestigador getSolicitudInvestigadorProSeleccionada() {
		return solicitudInvestigadorProSeleccionada;
	}

	public void setSolicitudInvestigadorProSeleccionada(SolicitudProrrogaInvestigador solicitudInvestigadorProSeleccionada) {
		this.solicitudInvestigadorProSeleccionada = solicitudInvestigadorProSeleccionada;
	}

	public List<SolicitudProrrogaInvestigador> getListaInvestigadorProrrogaEliminado() {
		return listaInvestigadorProrrogaEliminado;
	}

	public void setListaInvestigadorProrrogaEliminado(List<SolicitudProrrogaInvestigador> listaInvestigadorProrrogaEliminado) {
		this.listaInvestigadorProrrogaEliminado = listaInvestigadorProrrogaEliminado;
	}
	
	public long calcularSemanasAdicionalesProrroga(Date fechaFinalizacionActual, Date fechaFinalizacionPropuesta) {
		long weeks = 0;
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaFinalizacionActual);
        long startTimeInMillis = calendar.getTimeInMillis();
        
        calendar.setTime(fechaFinalizacionPropuesta);
        long endTimeInMillis = calendar.getTimeInMillis();
        
        long diffInMillis = endTimeInMillis - startTimeInMillis;
        weeks = TimeUnit.MILLISECONDS.toDays(diffInMillis) / 7;
        
        return weeks;
	}	
	
	public void agregarDirectorProrroga() {		
		if(nuevaSolicitud.getFechaEsperadaTerminacion()!=null) {
			SolicitudProrrogaInvestigador investigadorNuevoTiempo = new SolicitudProrrogaInvestigador();
				investigadorNuevoTiempo.setDedicacionHorasSemana(proyectoActual.getInvestigadorPrincipalVista().getDedicacionHorasSemana());
		    	investigadorNuevoTiempo.setNumeroSemanasAdicional((short) 0);
		    	investigadorNuevoTiempo.setValorPagar(0L);
		    	investigadorNuevoTiempo.setInvestigador(proyectoActual.getInvestigadorPrincipalVista().getInvestigador());
		    	investigadorNuevoTiempo.setTipo(proyectoActual.getInvestigadorPrincipalVista().getTipo());
		    	investigadorNuevoTiempo.setEstado(SolicitudInvestigador.NO_TRAMITADO);
		    	investigadorNuevoTiempo.setFechaProrroga(nuevaSolicitud.getFechaEsperadaTerminacion());
		    	if(nuevaSolicitud.getFechaEsperadaTerminacion()!=null){
			    	investigadorNuevoTiempo.setNumeroSemanasAdicional((short) calcularSemanasAdicionalesProrroga(getFechaFinalProyecto(), nuevaSolicitud.getFechaEsperadaTerminacion()));
			    	InvestigadorInterno investigadorInterno = servicioPersona
			                .obtenerInvestigadorInterno(proyectoActual.getInvestigadorPrincipalVista().getInvestigador().getId());
			    	investigadorNuevoTiempo.setValorPagar(calcularValorFuncionarioSemanas(proyectoActual.getInvestigadorPrincipalVista().getDedicacionHorasSemana(),(int)calcularSemanasAdicionalesProrroga(getFechaFinalProyecto(), nuevaSolicitud.getFechaEsperadaTerminacion()),investigadorInterno.getValorHoraValidado()));
			    	listaInvestigadoresProrroga.add(investigadorNuevoTiempo);
		    	}
		}
		
		
	}
	
	public void calcularVinculacionAdicionalDirector(InvestigadorProyecto director) {
		
		if(listaInvestigadoresProrroga!=null && listaInvestigadoresProrroga.size()>0 && nuevaSolicitud.getFechaEsperadaTerminacion() !=null) {
			for(int i=0; i<listaInvestigadoresProrroga.size(); i++) {
				if(listaInvestigadoresProrroga.get(i).getInvestigador().getId().getDocumento().equals(proyectoActual.getInvestigadorPrincipalVista().getInvestigador().getId().getDocumento())) {
					listaInvestigadoresProrroga.get(i).setNumeroSemanasAdicional((short) calcularSemanasAdicionalesProrroga(getFechaFinalProyecto(), nuevaSolicitud.getFechaEsperadaTerminacion()));
					InvestigadorInterno investigadorInterno = servicioPersona
			                .obtenerInvestigadorInterno(proyectoActual.getInvestigadorPrincipalVista().getInvestigador().getId());
					listaInvestigadoresProrroga.get(i).setValorPagar(calcularValorFuncionarioSemanas(proyectoActual.getInvestigadorPrincipalVista().getDedicacionHorasSemana(),(int)calcularSemanasAdicionalesProrroga(getFechaFinalProyecto(), nuevaSolicitud.getFechaEsperadaTerminacion()),investigadorInterno.getValorHoraValidado()));
				}
			}
		}
		
	}
	
	public Date getFechaFinalProyecto() {
		
		Date fechaFinalProyecto;

		         if (proyectoActual.getFechaTentativaInicio() != null) {

		             // Se calcula la nueva fecha final del proyecto
		             fechaFinalProyecto = new Date(proyectoActual.getFechaTentativaInicio().getTime());
		             if(proyectoActual.getDuracionAcumulada()!=null) {
		            	 fechaFinalProyecto
		                     .setMonth(fechaFinalProyecto.getMonth() + proyectoActual.getDuracionAcumulada().intValue());
		             }else {
		            	 fechaFinalProyecto
	                     .setMonth(fechaFinalProyecto.getMonth() + proyectoActual.getDuracion().intValue());
		             }

		             // Se agregan los días
		             Calendar calendar = Calendar.getInstance();
		             calendar.setTime(fechaFinalProyecto);
		             calendar.add(Calendar.DAY_OF_YEAR, proyectoActual.getDuracionDiasAcumulada());
		             fechaFinalProyecto = calendar.getTime();
		         }else {
		        	 fechaFinalProyecto = proyectoActual.getFechaFinalizacion();
		         }
		 return fechaFinalProyecto;
	}

	public String getFuncionNuevoInvestigador() {
		return funcionNuevoInvestigador;
	}

	public void setFuncionNuevoInvestigador(String funcionNuevoInvestigador) {
		this.funcionNuevoInvestigador = funcionNuevoInvestigador;
	}

	public List<SelectItem> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<SelectItem> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public SelectItem[] getCiudadItem() {
		return ciudadItem;
	}

	public void setCiudadItem(SelectItem[] ciudadItem) {
		this.ciudadItem = ciudadItem;
	}

	public String getDepartamentoSeleccionado() {
		return departamentoSeleccionado;
	}

	public void setDepartamentoSeleccionado(String departamentoSeleccionado) {
		this.departamentoSeleccionado = departamentoSeleccionado;
	}

	public String getCiudadSeleccionada() {
		return ciudadSeleccionada;
	}

	public void setCiudadSeleccionada(String ciudadSeleccionada) {
		this.ciudadSeleccionada = ciudadSeleccionada;
	}

	public SolicitudCiudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(SolicitudCiudad ciudad) {
		this.ciudad = ciudad;
	}

	public Date getFechaMinPermiso() {
		return fechaMinPermiso;
	}

	public void setFechaMinPermiso(Date fechaMinPermiso) {
		this.fechaMinPermiso = fechaMinPermiso;
	}

	public Date getFechaMaximaPermiso() {
		return fechaMaximaPermiso;
	}

	public void setFechaMaximaPermiso(Date fechaMaximaPermiso) {
		this.fechaMaximaPermiso = fechaMaximaPermiso;
	}
		
}
