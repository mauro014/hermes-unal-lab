package co.edu.unal.hermes.vista;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.imgscalr.Scalr;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import co.edu.unal.hermes.modelo.ActividadInforme;
import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.modelo.ArchivoAval;
import co.edu.unal.hermes.modelo.ArchivoColeccion;
import co.edu.unal.hermes.modelo.ArchivoConceptoContrato;
import co.edu.unal.hermes.modelo.ArchivoConvocatoriaExterna;
import co.edu.unal.hermes.modelo.ArchivoConvocatoriaPadre;
import co.edu.unal.hermes.modelo.ArchivoGestionColeccion;
import co.edu.unal.hermes.modelo.ArchivoInforme;
import co.edu.unal.hermes.modelo.ArchivoInstructivo;
import co.edu.unal.hermes.modelo.ArchivoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.ArchivoRequerimiento;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.ColeccionGestion;
import co.edu.unal.hermes.modelo.ConceptoContratoBiodiversidad;
import co.edu.unal.hermes.modelo.Convenio;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DependenciaAreaResponsabilidad;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoEstadoAval;
import co.edu.unal.hermes.modelo.HistoricoEstadoConceptoBio;
import co.edu.unal.hermes.modelo.HistoricoEstadoGrupo;
import co.edu.unal.hermes.modelo.HistoricoEstadoInforme;
import co.edu.unal.hermes.modelo.HistoricoEstadoMovilidad;
import co.edu.unal.hermes.modelo.HistoricoEstadoSolicitud;
import co.edu.unal.hermes.modelo.HistoricoFormularioGrupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MetaProyecto;
import co.edu.unal.hermes.modelo.MovilidadInvestigador;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.PlanEstudios;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoCarta;
import co.edu.unal.hermes.modelo.ProyectoCompromiso;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.Requerimiento;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.ResultadoInforme;
import co.edu.unal.hermes.modelo.ResultadoProyecto;
import co.edu.unal.hermes.modelo.SemilleroHistoricoCambios;
import co.edu.unal.hermes.modelo.SemilleroHistoricoInforme;
import co.edu.unal.hermes.modelo.SemilleroInforme;
import co.edu.unal.hermes.modelo.SolicitudFuente;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorioPersona;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioActividadEquipo;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioLogActividades;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioLogEquipos;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.modelo.seguimiento.DetalleAdicionPresupuesto;
import co.edu.unal.hermes.modelo.seguimiento.DetalleCambioRubro;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudAdicionPresupuestal;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudDocumento;
import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;
import co.edu.unal.hermes.modelo.servicioDependencia.IServicioDependencia;
import co.edu.unal.hermes.modelo.servicioGeneral.IServicioGeneral;
import co.edu.unal.hermes.modelo.servicioModalidad.IServicioModalidad;
import co.edu.unal.hermes.modelo.servicioMovilidad.IServicioMovilidad;
import co.edu.unal.hermes.modelo.servicioPersona.IServicioPersona;
import co.edu.unal.hermes.modelo.servicioProyecto.IServicioProyecto;
import co.edu.unal.hermes.modelo.servicios.IServicioAlertas;
import co.edu.unal.hermes.modelo.servicios.IServicioAval;
import co.edu.unal.hermes.modelo.servicios.IServicioBiodiversidad;
import co.edu.unal.hermes.modelo.servicios.IServicioCorreo;
import co.edu.unal.hermes.modelo.servicios.IServicioEvaluacion;
import co.edu.unal.hermes.modelo.servicios.IServicioFinanciacion;
import co.edu.unal.hermes.modelo.servicios.IServicioGrupo;
import co.edu.unal.hermes.modelo.servicios.IServicioLineaInvestigacion;
import co.edu.unal.hermes.modelo.servicios.IServicioPropiedadIntelectual;
import co.edu.unal.hermes.modelo.servicios.IServicioSolicitudes;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.utils.VariablesEstaticas;
import co.edu.unal.hermes.vista.utils.Util;
import sun.awt.image.ImageFormatException;

public abstract class ManejadorBase implements Serializable {

	private static final long serialVersionUID = -6516999166353671265L;
	public static final String CTX_PERSONA = "persona";
	public static final Long MODALIDAD_FICHA_MINIMA_ID = 2L;
	public static final long MODALIDAD_CONVOCATORIA_EXTERNA_ID = 10;
	public static final long MODALIDAD_PROYECTOS_CONVOCATORIA_EXTERNA_ID = 116;
	public static final String TIPO_MODALIDAD_PERMISO_MARCO = "PM";
	public static final String TIPO_MODALIDAD_PERMISO_MARCO_ASIGNATURA = "PMA";
	public static final String TIPO_MODALIDAD_CONTRATO_ACCESO = "CAR";
	public static final long MODALIDAD_PERMISO_MARCO = 150;
	public static final long MODALIDAD_PERMISO_MARCO_2024 = 1344;
	public static final long MODALIDAD_PERMISO_MARCO_ASIGNATURA = 652;
	public static final long MODALIDAD_PERMISO_MARCO_ASIGNATURA_2024 = 1345;
	public static final long PROYECTO_SOLICITUD_RENOVACION = 20898;
	public static final long TIPO_CARTA_DOMINIO = 105;
	public static final long MAXIMO_TAMANO_ARCHIVOS = 3145728;
	public static final Long TIPO_CONTRATO_INDIVIDUAL = (long) 21;
	public static final Long TIPO_CONTRATO_MARCO = (long) 22;
	protected static final String ID_TIPO_ARCHIVO_RECLAMACION_REQUISITOS = "87";
	/** The Constant DOMINIO_AREA_CIENCIA. */
	protected static final String DOMINIO_AREA_CIENCIA = "AREA_CIENCIA";

	/** The Constant DOMINIO_SUB_AREA_CIENCIA. */
	protected static final String DOMINIO_SUB_AREA_CIENCIA = "SUB_AREA_CIENCIA_FM";

	protected static final String VARIABLE_PROYECTO_SESION = "proyecto";

	protected final String CODIGO_PRODUCTOS_FICHA = "95";

	/** The Constant CARPETA_TEMPORAL_IMAGENES. */
	public static final String CARPETA_TEMPORAL_IMAGENES = "images";

	/** The Constant persona en sesion */
	public static final String VARIABLE_PERSONA_SESION_ACTUAL = "persona";

	// categorias de convocatorias
	public static final long PREGRADO = 289;
	public static final long POSGRADO = 290;
	public static final long PROYECTOS = 291;
	public static final long INNOVACION = 292;
	public static final long INTERNACIONALIZACION = 293;
	public static final long DIFUSION = 294;
	public static final long PUBLICACIONES = 295;
	public static final long SEDES_FACULTADES = 296;
	public static final long PROGRAMA_APOYO = 297;
	public static final long OTRAS = 298;

	// Mensajes estandar
	public static final String NO_GUARDADO = "La información NO ha sido guardada.";

	// fuente de financiacion regalias
	public static final long FUENTE_FINANCIACION_REGALIAS = 428;
	// id en la tabla HER_FUENTE_FINANCIACION
	public static final String FUENTE_FINANCIACION_UNAL = "1";

	protected final String CONSULTA_ENTIDADES_EXTERNAS = "select e from FuenteFinanciacion e where e.estado"
			+ " = 'V'";

	protected final String CONSULTA_ENTIDADES_EXTERNAS_SIN_UNAL = CONSULTA_ENTIDADES_EXTERNAS
			+ " order by e.descripcion asc";
	protected final String CONSULTA_ENTIDADES_EXTERNAS_CON_UNAL = CONSULTA_ENTIDADES_EXTERNAS
			+ " or e.id in ('280','1') order by e.descripcion asc";
	
	protected SelectItem[] generoItem = { new SelectItem(VariablesEstaticas.GENERO_FEMENINO, "Mujer - Femenino"),
			new SelectItem(VariablesEstaticas.GENERO_MASCULINO, "Hombre - Masculino") };
	

	/** The fuentes externas item. */
	private SelectItem[] fuentesExternasItem;

	// numero convocatoria jovenes investigadores
	public static final String CONVOCATORIA_JOVENES_INVESTIGADORES = "775";

	// numero de archivos por carpeta
	public static final Long NUMERO_ARCHIVOS_CARPETA = 10000L;

	// modalidades de registros de biodiversidad
	public static final String MODALIDADES_PERMISO_CONTRATO = "'" + MODALIDAD_PERMISO_MARCO + "','"
			+ MODALIDAD_PERMISO_MARCO_ASIGNATURA + "','" + TIPO_CONTRATO_INDIVIDUAL + "','" + TIPO_CONTRATO_MARCO + "','"+MODALIDAD_PERMISO_MARCO_ASIGNATURA_2024+"','"+MODALIDAD_PERMISO_MARCO_2024+"'";

	// Documentos de prueba
	public static final String DOCUMENTOS_PRUEBAS_DESARROLLO = "'19380666','52980878','79750255'";

	// Ruta para gestión de archivos PRODUCCION.
	public static final String RUTA_ARCHIVOS = "//data//archivos//";
	// Ruta para gestión de archivos PRUEBAS.
	public static final String RUTA_ARCHIVOS_PRUEBAS = "//data//archivos_pruebas//";
	// Ruta para gestión de archivos LOCAL.
//	public static final String RUTA_ARCHIVOS = "/home/camilo/WS/HERMES/1-ARCHIVOS/";
//	public static final String RUTA_ARCHIVOS_PRUEBAS = "/home/camilo/WS/HERMES/1-ARCHIVOS/";
	//public static final String RUTA_ARCHIVOS_PRUEBAS = "E://archivos//";
	//public static final String RUTA_ARCHIVOS = "C://ARCHIVOS//";
	
	
	 
	 //convocatorias externas, registro proyectos, jornada docente
	 public static final Long[] CONVOCATORIAS_RUBROS_COMPLETOS_2022 = {MODALIDAD_FICHA_MINIMA_ID,MODALIDAD_CONVOCATORIA_EXTERNA_ID,MODALIDAD_PROYECTOS_CONVOCATORIA_EXTERNA_ID,(long) 1081,(long) 1110};
	 
	protected FacesContext facesContext;
	protected ServletContext servletContext;
	protected ApplicationContext appCtx;
	protected HttpSession sesion;
	protected HttpServletRequest request;
	protected Persona personaActual;

	private final String TIPO_ESTADO_POSTULACION = "116";

	// Servicios
	protected IServicioGrupo servicioGrupo;
	protected IServicioGeneral servicioGeneral;
	protected IServicioLineaInvestigacion servicioLineaInvestigacion;
	protected IServicioModalidad servicioModalidad;
	protected IServicioPersona servicioPersona;
	protected IServicioProyecto servicioProyecto;
	protected IServicioCorreo servicioCorreo;
	protected IServicioEvaluacion servicioEvaluacion;
	protected IServicioFinanciacion servicioFinanciacion;
	protected IServicioSolicitudes servicioSolicitudes;
	protected IServicioAlertas servicioAlertas;
	protected IServicioMovilidad servicioMovilidad;
	protected IServicioPropiedadIntelectual servicioPropiedadIntelectual;
	protected IServicioDependencia servicioDependencia;
	protected IServicioBiodiversidad servicioBiodiversidad;
	protected IServicioAval servicioAval;

	protected String errorString;

	protected String cambiosGruposHistorico;
	protected String justificacionGrupoHistorico;
	
	//Variables requeridas para la solicitud de creación de fuente de financiación
	/** The nombre fuente. */
	protected String nombreFuente;

	/** The nit fuente. */
	protected String nitFuente;

	/** The naturaleza fuente. */
	protected String naturalezaFuente;

	/** The pais fuente. */
	protected String paisFuente;

	/** The caracter fuente. */
	protected String caracterFuente;

	/** The tipo fuente. */
	protected String tipoFuente;

	/** The telefono fuente. */
	protected String telefonoFuente;

	/** The direccion fuente. */
	protected String direccionFuente;

	protected SelectItem[] listaPaisesItem;

	/** The C B crear fuente. */
	public UIComponent CB_crearFuente;
	public UIComponent CB_crearFuente2;
	
	public List<ProductoTipo> productosConfiguradosConvocatoria;

	public ManejadorBase() {

		Persona p;
		if (sesion != null) {
			p = ((Persona) sesion.getAttribute("persona"));
			if (p == null) {
				String url = "pages/principal/Principal.xhtml";
				FacesContext fc = FacesContext.getCurrentInstance();

				try {
					fc.getExternalContext().redirect(url);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		facesContext = javax.faces.context.FacesContext.getCurrentInstance();
		servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		appCtx = FacesContextUtils.getWebApplicationContext(facesContext);
		sesion = (HttpSession) facesContext.getExternalContext().getSession(false);
		request = (HttpServletRequest) facesContext.getExternalContext().getRequest();

		// Borrar los manejadores que se cncuentren en sesion, para evitar
		// problemas

		servicioGrupo = (IServicioGrupo) appCtx.getBean("servicioGrupo");
		servicioGeneral = (IServicioGeneral) appCtx.getBean("servicioGeneral");
		servicioLineaInvestigacion = (IServicioLineaInvestigacion) appCtx.getBean("servicioLineaInvestigacion");
		servicioModalidad = (IServicioModalidad) appCtx.getBean("servicioModalidad");
		servicioPersona = (IServicioPersona) appCtx.getBean("servicioPersona");
		servicioProyecto = (IServicioProyecto) appCtx.getBean("servicioProyecto");
		servicioCorreo = (IServicioCorreo) appCtx.getBean("servicioCorreo");
		servicioEvaluacion = (IServicioEvaluacion) appCtx.getBean("servicioEvaluacion");
		servicioFinanciacion = (IServicioFinanciacion) appCtx.getBean("servicioFinanciacion");
		servicioSolicitudes = (IServicioSolicitudes) appCtx.getBean("servicioSolicitudes");
		servicioAlertas = (IServicioAlertas) appCtx.getBean("servicioAlertas");
		servicioMovilidad = (IServicioMovilidad) appCtx.getBean("servicioMovilidad");
		servicioPropiedadIntelectual = (IServicioPropiedadIntelectual) appCtx.getBean("servicioPropiedadIntelectual");
		servicioDependencia = (IServicioDependencia) appCtx.getBean("servicioDependencia");
		servicioBiodiversidad = (IServicioBiodiversidad) appCtx.getBean("servicioBiodiversidad");
		servicioAval = (IServicioAval) appCtx.getBean("servicioAval");

		personaActual = cargarPersonaActual();

	}
	
	private static final List<String> VINCULACIONES_DOCENTE_PLANTA = Arrays.asList("30", "16");
	private static final List<String> VINCULACIONES_DOCENTE_NO_PLANTA = Arrays.asList("41", "42", "38", "34", "44", "48", "107", "39", "32", "31", "40");
    private static final List<String> VINCULACIONES_ADMIN_PLANTA = Arrays.asList("1", "14", "10", "20", "22");
    private static final List<String> VINCULACIONES_ADMIN_NO_PLANTA = Arrays.asList("23", "26", "21", "68", "12");

    public Boolean validarDocentePlanta(InvestigadorInterno inv) {
        return VINCULACIONES_DOCENTE_PLANTA.contains(inv.getTipoVinculacion().getId());
    }
	
	public Boolean validarDocenteNoPlanta(InvestigadorInterno inv) {
		return VINCULACIONES_DOCENTE_NO_PLANTA.contains(inv.getTipoVinculacion().getId());
	}
	
	public Boolean validarAdministrativoPlanta(InvestigadorInterno inv) {
		return VINCULACIONES_ADMIN_PLANTA.contains(inv.getTipoVinculacion().getId());
	}
	
	public Boolean validarAdministrativoNoPlanta(InvestigadorInterno inv) {
		return VINCULACIONES_ADMIN_NO_PLANTA.contains(inv.getTipoVinculacion().getId());
	}
	
	public Boolean validarEmpleadoUN(InvestigadorInterno inv) {
		return validarDocentePlanta(inv) 
				|| validarDocenteNoPlanta(inv)
				|| validarAdministrativoPlanta(inv)
				|| validarAdministrativoNoPlanta(inv);
	}
	
	public Estudiante validarEstudianteUN(IdPersona idPersona) {
		Estudiante estudiante = servicioPersona.obtenerEstudiante(idPersona);
		return !esNulo(estudiante) && estudiante.esInterno() ? estudiante : null;
	}

	protected void borrarManejadoresInsercionProyecto() {
		sesion.removeAttribute("manejadorProyectosInvestigador");
		sesion.removeAttribute("manejadorActividades");
		sesion.removeAttribute("manejadorArchivos");
		sesion.removeAttribute("manejadorBibliografia");
		sesion.removeAttribute("manejadorDatosBasicos");
		sesion.removeAttribute("manejadorDetallesFinancieros");
		sesion.removeAttribute("manejadorEvaluadores");
		sesion.removeAttribute("manejadorInformacionEspecifica");
		sesion.removeAttribute("manejadorFuentesFinancieras");
		sesion.removeAttribute("manejadorInvestigadores");
		sesion.removeAttribute("manejadorLineasProyecto");
		sesion.removeAttribute("manejadorObjetivosResultados");
		sesion.removeAttribute("manejadorRubros");
		sesion.removeAttribute("manejadorVigencias");
		sesion.removeAttribute("manejadorMenuFormularios");
		sesion.removeAttribute("manejadorAsociacionEvaluadores");
		sesion.removeAttribute("manejadorProyectosConvocatoria");
		sesion.removeAttribute("manejadorAreasTematicas");
		sesion.removeAttribute("manejadorProductos");
		sesion.removeAttribute("manejadorEmpresas");
		sesion.removeAttribute("manejadorFichaMinima");
		sesion.removeAttribute("manejadorFichaMinimaHome");
		sesion.removeAttribute("manejadorFichaMinimaProyectos");
		sesion.removeAttribute("ManejadorConvocatoriaLibros");
		sesion.removeAttribute("ManejadorSolicitudISBN");
		sesion.removeAttribute("manejadorFichaMinimaHomeJovenes");
		sesion.removeAttribute("ManejadorConvocatoriaBancoProyectos");
		sesion.removeAttribute("manejadorInformacionMarco");
		sesion.removeAttribute("manejadorLineas");
		sesion.removeAttribute("ManejadorTrabajoPrevioProyectoES_Inno");
	}

	public void mensajeInfo(String mensaje) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * @param idComponente
	 * @param mensaje
	 */
	public void mensajeInfo(String idComponente, String mensaje) {
		UIComponent comp = FacesContext.getCurrentInstance().getViewRoot().findComponent(idComponente);
		if ((comp != null) && (comp instanceof UIInput)) {
			mensajeInfo(comp, mensaje);
		} else {
			System.out.println("Componente " + idComponente + " no encontrado, mensaje INFO no creado.");
		}
	}

	public void mensajeInfo(UIComponent component, String mensaje) {
		
		if (component != null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje);
			FacesContext.getCurrentInstance().addMessage(component.getClientId(), msg);
		} else {
			System.out.println("Componente no encontrado, mensaje INFO no creado.");
		}
		
	}

	public void mensajeError(String idComponente, String mensaje) {
		UIComponent comp = FacesContext.getCurrentInstance().getViewRoot().findComponent(idComponente);
		if ((comp != null) && (comp instanceof UIInput)) {
			((UIInput) comp).setValid(false);
			mensajeError(comp, mensaje);
		} else {
			System.out.println("Componente " + idComponente + " no encontrado, mensaje de error no creado.");
		}

	}

	public void mensajeError(UIComponent component, String mensaje) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje);
		try {
			FacesContext.getCurrentInstance().addMessage(component.getClientId(), msg);
		} catch (NullPointerException npe) {
			System.out.println("Componente ingresado no existe.");
		}
	}

	public void mensajeError(String mensaje) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String reglaDeNavegacionAnterior() {
		String anteriorManejador = null;
		anteriorManejador = constructorAnterior();
		if (anteriorManejador.length() > 0) {
			sesion.removeAttribute(anteriorManejador);
		}
		return reglaNavegacionAnterior();
	}

	public String accionVolver() {
		return reglaDeNavegacionAnterior();
	}

	public void inicilizarReglasNavegacion() {
		Stack<String> reglasNavegacionAnteriores = new Stack<String>();
		Stack<String> constructoresAnteriores = new Stack<String>();
		String reglaNavegacionPorAgregar = new String();
		String constructorPorAgregar = new String();

		sesion.setAttribute("reglasNavegacionAnteriores", reglasNavegacionAnteriores);
		sesion.setAttribute("constructoresAnteriores", constructoresAnteriores);
		sesion.setAttribute("reglaNavegacionPorAgregar", reglaNavegacionPorAgregar);
		sesion.setAttribute("constructorPorAgregar", constructorPorAgregar);
	}

	public void agregarReglaNavegacion(String reglaNavegacion, String constructor) {
		sesion.setAttribute("reglaNavegacionPorAgregar", reglaNavegacion);
		sesion.setAttribute("constructorPorAgregar", constructor);
	}

	public void agregarReglaNavegacionAnt() {
		Stack<String> reglasNavegacionAnteriores = new Stack<String>();
		Stack<String> constructoresAnteriores = new Stack<String>();
		String reglaNavegacionPorAgregar = new String();
		String constructorPorAgregar = new String();

		if (sesion.getAttribute("reglasNavegacionAnteriores") != null) {
			reglasNavegacionAnteriores = (Stack<String>) ((Stack<String>) sesion
					.getAttribute("reglasNavegacionAnteriores")).clone();
		}
		if (sesion.getAttribute("constructoresAnteriores") != null) {
			constructoresAnteriores = (Stack<String>) ((Stack<String>) sesion.getAttribute("constructoresAnteriores"))
					.clone();
		}
		if (sesion.getAttribute("reglaNavegacionPorAgregar") != null) {
			reglaNavegacionPorAgregar = (String) sesion.getAttribute("reglaNavegacionPorAgregar");
		}
		if (sesion.getAttribute("constructorPorAgregar") != null) {
			constructorPorAgregar = (String) sesion.getAttribute("constructorPorAgregar");
		}

		if (reglaNavegacionPorAgregar != null && reglaNavegacionPorAgregar.length() > 0) {
			reglasNavegacionAnteriores.push(reglaNavegacionPorAgregar);
			reglaNavegacionPorAgregar = "";
			constructoresAnteriores.push(constructorPorAgregar);
			constructorPorAgregar = "";

			sesion.setAttribute("reglasNavegacionAnteriores", reglasNavegacionAnteriores.clone());
			sesion.setAttribute("constructoresAnteriores", constructoresAnteriores.clone());
		}
	}

	public String reglaNavegacionAnterior() {
		Stack<String> reglasNavegacionAnteriores = new Stack<String>();
		if (sesion.getAttribute("reglasNavegacionAnteriores") != null) {
			reglasNavegacionAnteriores = (Stack<String>) sesion.getAttribute("reglasNavegacionAnteriores");
		}

		if (!reglasNavegacionAnteriores.empty()) {
			String ReglaNavegacion = reglasNavegacionAnteriores.pop();
			System.out.println("No empty ReglaNavegacion " + ReglaNavegacion);
			sesion.setAttribute("reglasNavegacionAnteriores", reglasNavegacionAnteriores.clone());
			return ReglaNavegacion;
		} else {
			// Ir a la pagina de Inicio
			return "proyectosInvestigador";
		}
	}

	public String constructorAnterior() {
		Stack<String> constructoresAnteriores = new Stack<String>();
		if (sesion.getAttribute("constructoresAnteriores") != null) {
			constructoresAnteriores = (Stack<String>) sesion.getAttribute("constructoresAnteriores");
		}
		if (!constructoresAnteriores.empty()) {
			String constructor = constructoresAnteriores.pop();
			sesion.setAttribute("constructoresAnteriores", constructoresAnteriores.clone());
			System.out.println("No empty constructor " + constructor);
			return constructor;
		} else {
			return "";
		}
	}

	public CorreoPlantilla cargarPlantilla(int cod_id) {
		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		List<CorreoPlantilla> lista = servicioGeneral.obtenerObjetos(CorreoPlantilla.class,
				"select c from CorreoPlantilla c where c.id='" + cod_id + "'");
		if (lista != null && lista.size() > 0) {
			correoActualAux = (CorreoPlantilla) lista.get(0);
		}
		return correoActualAux;
	}
	
	public String getRandomImageName() {
        int i = (int) (Math.random() * 100000);
        return String.valueOf(i);
    }
	
	public void enviarCorreoLaboratoriosProyecto(Laboratorio lab, Proyecto proyectoActual, Long tipo) {
		
		if(!servicioGeneral.consultaNotificacionEnviadaLabsProyecto(proyectoActual.getId(), lab.getId(), tipo)) {

			//Parametros
			String idProyecto = proyectoActual.getId().toString();
			String nombreProyecto = proyectoActual.getNombre();
			String nombreDirector = proyectoActual.getListaInvestigadorPrincipal().get(0).getInvestigador().getNombreCompleto();
			String nombreLaboratorio = lab.getNombre();
			Persona CoordinadorLab = servicioGeneral.obtenerCoordinadorLaboratorio(lab.getId());
			String nombreCoordinador = !esNulo(CoordinadorLab) ? CoordinadorLab.getNombreCompleto() : null;
			String emailCoordinador = !esNulo(CoordinadorLab) ? CoordinadorLab.getEmail() : null;
			
			//Construir objeto Correo
			CorreoPlantilla cp = servicioCorreo.obtenerPlantillaCorreoCompleta(tipo);
			String asunto = cp.getAsunto();
			String cuerpo = cp.getCuerpo();
			
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			
			asunto = asunto.replaceAll("<<ID_PROYECTO>>", idProyecto);
			
			cuerpo = cuerpo.replaceAll("<<ID_PROYECTO>>", idProyecto);
			cuerpo = cuerpo.replaceAll("<<NOMBRE_COORDINADOR>>", nombreCoordinador);
			cuerpo = cuerpo.replaceAll("<<NOMBRE_PROYECTO>>", nombreProyecto);
			cuerpo = cuerpo.replaceAll("<<NOMBRE_DIRECTOR>>", nombreDirector);
			cuerpo = cuerpo.replaceAll("<<NOMBRE_LABORATORIO>>", nombreLaboratorio);
	
			correo.adicionarDireccion(emailCoordinador);
			//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
			
			correo.setAsunto(asunto);
			correo.setCuerpo(cuerpo);
			
			if(servicioCorreo.enviarCorreo(correo)) {
				servicioGeneral.actualizarNotificacionLaboratoriosProyecto(proyectoActual.getId(), lab.getId(), 1L, tipo);
			}
		} else
			System.out.println("Alerta ya enviada para LAB = " + lab.getId() + " y PRY = " + proyectoActual.getId());
	}

	protected List<SelectItem> cargarPlanEstudios(List<SelectItem> programas) {
		String consulta = "select p from PlanEstudios p where p.tipo in (3,4,5,6,7)  order by p.tipo, p.id";
		List<PlanEstudios> listaPlanEstudios = servicioGeneral.obtenerObjetos(PlanEstudios.class, consulta);
		programas = new Vector<SelectItem>();
		SelectItem stemp = new SelectItem("0", "");
		programas.add(stemp);
		for (Iterator<PlanEstudios> it = listaPlanEstudios.iterator(); it.hasNext();) {
			PlanEstudios p = (PlanEstudios) it.next();
			String nombrePlan = "";
			String nombrePlan2 = "";
			if (p.getNombre() != null && p.getNombre().length() > 0) {
				if (p.getTipo() == 3) {
					nombrePlan2 = "Pregado - ";
				}
				if (p.getTipo() == 4) {
					nombrePlan2 = "Especialización - ";
				}
				if (p.getTipo() == 5) {
					nombrePlan2 = "Especialidad - ";
				}
				if (p.getTipo() == 6) {
					nombrePlan2 = "Maestría - ";
				}
				if (p.getTipo() == 7) {
					nombrePlan2 = "Doctorado - ";
				}
				String ini = p.getId().substring(0, 1);
				if (ini.equals("2")) {
					nombrePlan = "Sede Bogotá - ";
				}
				if (ini.equals("3")) {
					nombrePlan = "Sede Medellín - ";
				}
				if (ini.equals("4")) {
					nombrePlan = "Sede Manizales - ";
				}
				if (ini.equals("5")) {
					nombrePlan = "Sede Palmira - ";
				}
				if (ini.equals("6")) {
					nombrePlan = "Sede Amazonia - ";
				}
				if (ini.equals("7")) {
					nombrePlan = "Sede Orinoquia - ";
				}
				if (ini.equals("8")) {
					nombrePlan = "Sede Caribe - ";
				}
				if (ini.equals("9")) {
					nombrePlan = "Sede Tumaco - ";
				}
				if (ini.equals("L")) {
					nombrePlan = "Sede La Paz - ";
				}
				SelectItem s = new SelectItem(p.getId(), nombrePlan2 + nombrePlan + p.getNombre());
				programas.add(s);
			}
		}
		return programas;
	}

	public void descargarArchivoGenerico(String nombreTabla, String nombreArchivoDescarga,
			String nombreArchivoGuardar) {
		try {
			FacesContext ctx = FacesContext.getCurrentInstance();

			if (archivosEnSubcarpetas(nombreTabla)) {
				nombreTabla = nombreTabla + obtenerSubCarpetaArchivo(Long.parseLong(nombreArchivoDescarga));
			}
			
			String path = RUTA_ARCHIVOS + nombreTabla + "//" + nombreArchivoDescarga;
			if (!servicioGeneral.esAmbienteProduccion()) {
				path = RUTA_ARCHIVOS_PRUEBAS + nombreTabla + "//" + nombreArchivoDescarga;
			}
			

			if (nombreTabla.equals("HER_EXT_ARC_PREINS")) {
				nombreTabla = nombreTabla + "//" + nombreArchivoDescarga;
				path = RUTA_ARCHIVOS + nombreTabla + "//" + nombreArchivoGuardar;
			}

			File fichero = new File(path);
			FileInputStream fis;
			try {
				fis = new FileInputStream(fichero);

				byte[] bytes = new byte[1000];
				int read = 0;

				if (!ctx.getResponseComplete()) {
					String fileName = nombreArchivoGuardar;
					String contentType = "text/plain";
					HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
					response.setContentType(contentType);
					response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
					ServletOutputStream out = response.getOutputStream();
					while ((read = fis.read(bytes)) != -1) {
						out.write(bytes, 0, read);
					}
					out.flush();
					out.close();
					ctx.responseComplete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String removeCaractEspeciales(String input) {

		String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ ";
		String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC_";
		String output = input;

		if (output != null) {
			for (int i = 0; i < original.length(); i++) {
				output = output.replace(original.charAt(i), ascii.charAt(i));
			}
		}
		return output;
	}

	public boolean compararStrings(String s1, String s2) {
		if (s1 == null && s2 == null) {
			return true;
		}
		if (s1 == null && s2 != null) {
			return false;
		}
		if (s2 == null && s1 != null) {
			return false;
		}

		String copia1 = removeCaractEspeciales(s1).trim();
		String copia2 = removeCaractEspeciales(s2).trim();
		return copia1.equalsIgnoreCase(copia2);
	}

	// Obtiene el subdirectorio en el que se debe guardar un archivo, si el
	// subdirectorio no existe lo crea.
	public String obtenerSubCarpetaArchivo(Long idArchivo, String directorio) {

		try {
			Long subdirectorio = idArchivo / NUMERO_ARCHIVOS_CARPETA;
			File folder = new File(RUTA_ARCHIVOS + directorio + "//" + subdirectorio);
			if (!servicioGeneral.esAmbienteProduccion()) {
				folder = new File(RUTA_ARCHIVOS_PRUEBAS + directorio + "//" + subdirectorio);
			}
			
			if (!folder.isDirectory()) {
				folder.mkdirs();
			}
			
			return "//" + subdirectorio.toString();
		} catch (HibernateException e) {
			e.printStackTrace();
			return "";
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}

	// obtiene el subdirectorio en el que se encuentra un archivo.
	public String obtenerSubCarpetaArchivo(Long idArchivo) {

		Long subdirectorio = idArchivo / NUMERO_ARCHIVOS_CARPETA;
		return "//" + subdirectorio.toString();
	}

	/**
	 * @param nombreTabla
	 *            nombre de la tabla donde se guarda el archivo.
	 * @return true si el directorio correspondiente a esa tabla está organizado
	 *         en subcarpetas.
	 */
	public Boolean archivosEnSubcarpetas(String nombreTabla) {
		if (nombreTabla.equals("HER_AVAL") || nombreTabla.equals("HER_ARCHIVO_AVAL")
				|| nombreTabla.equals("HER_ARCHIVO") || nombreTabla.equals("HER_ARCHIVO_INFORME")
				|| nombreTabla.equals("HER_SOLICITUD_DOCUMENTO") || nombreTabla.equals("HER_ARCHIVO_CONVOCATORIA_PADRE")
				|| nombreTabla.equals("HER_ARCHIVO_COLECCION") || nombreTabla.equals("HER_CONVENIO")
				|| nombreTabla.equals("HER_PROYECTO_CARTA") || nombreTabla.equals("HER_ARCHIVO_MOVILIDAD")
				|| nombreTabla.equals("HER_ARCHIVO_MOVILIDAD_EP") || nombreTabla.equals("HER_ARCHIVO_MOVILIDAD_VE")
				|| nombreTabla.equals("HER_ARCHIVO_CONVOCATORIA_EXTERNA")
				|| nombreTabla.equals("HER_ARCHIVO_MOVILIDAD_DE") || nombreTabla.equals("HER_ARCHIVO_GRUPO")
				|| nombreTabla.equals("HER_ARCHIVO_INSTRUCTIVO") || nombreTabla.equals("HER_ARCHIVO_CONCEPTO_CONTRATO")
				|| nombreTabla.equals("HER_SEG_PROYECTO_PERSONA//SEG_CARTAFINALIZACION")
				|| nombreTabla.equals("HER_SEG_PROYECTO_PERSONA//SEG_CARTAINICIO")
				|| nombreTabla.equals(ArchivoLaboratorio.TABLA_ARCHIVOS_LABORATORIOS)
				|| nombreTabla.equals("HER_ARCHIVO_PROPIEDAD_INTELECTUAL")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean cargarArchivoDisco(org.primefaces.model.UploadedFile archivo, String ruta, String nombreNuevo) {
		try {
			if (archivo != null) {
				try {
					// Para archivos organizados en subcarpetas
					if (archivosEnSubcarpetas(ruta)) {
						ruta = ruta + obtenerSubCarpetaArchivo(Long.parseLong(nombreNuevo), ruta);
						if (cargarArchivoDisco(archivo.getInputstream(), ruta, nombreNuevo)) {
							return true;
						}
					} else {

						if (cargarArchivoDisco(archivo.getInputstream(), ruta, nombreNuevo)) {
							return true;
						}
					}
				} catch (IOException e) {
					errorString = e.getStackTrace().toString();
					e.printStackTrace();
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public boolean cargarArchivoDisco(InputStream inputStream, String ruta, String nombreNuevo) {
		try {
			if (inputStream != null) {
				String directorio = RUTA_ARCHIVOS + ruta + "//";
				if (!servicioGeneral.esAmbienteProduccion()) {
					directorio = RUTA_ARCHIVOS_PRUEBAS + ruta + "//";
				}
				String destination = directorio + nombreNuevo;

				if (copyFileBoolean(destination, inputStream)) {
					System.out.println("Archivo creado: " + destination);
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public boolean copyFileBoolean(String fileName, InputStream in) {
		try {
			OutputStream out = new FileOutputStream(new File(fileName));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			in.close();
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String insertarArchivoProyectoGenerico(UploadedFile archivo, Proyecto proyectoActual,
			List<Archivo> listaArchivos) {
		try {
			if (archivo != null) {
				Long id = null;
				int ii = archivo.getFileName().lastIndexOf("\\");
				Archivo archivoFu = new Archivo();
				String name = removeCaractEspeciales(archivo.getFileName().substring(ii + 1));
				archivoFu.setNombre(name);

				TipoArchivo tipoAr = new TipoArchivo();
				List listaAr = servicioGeneral.obtenerObjetoXID("TipoArchivo", "5");
				tipoAr = (TipoArchivo) listaAr.get(0);

				archivoFu.setTipoArchivo(tipoAr);
				archivoFu.setProyecto(proyectoActual);
				Persona persona = (Persona) sesion.getAttribute("persona");
				archivoFu.setResponsable(persona);
				archivoFu.setFecha(new Date());
				servicioGeneral.guardarObjeto(archivoFu);
				id = archivoFu.getId();
				if (id != null) {

					if (cargarArchivoDisco(archivo, "HER_ARCHIVO", id.toString())) {
						listaArchivos.add(archivoFu);
					} else {
						try {
							servicioGeneral.eliminarObjeto(archivoFu);
							Correo correo = new Correo();
							correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
							correo.adicionarDireccion("sisii_nal@unal.edu.co");
							correo.setAsunto("Error adjunto correo");
							correo.setCuerpo(errorString);
							servicioCorreo.enviarCorreo(correo);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return "FAILED";
					}
					return "archivos";
				}

			}
		} catch (Exception e) {
			return "FAILED";
		}

		return "FAILED";
	}

	public void descargarArchivoProyectoGenerico(Long id, Long proyectoId) {

		FacesContext ctx = FacesContext.getCurrentInstance();
		Archivo archivo = servicioProyecto.obtenerArchivo(id);

		try {

			if (archivo != null && archivo.getDatos() != null && archivo.getBytes().length > 1) {
				if (!ctx.getResponseComplete()) {
					HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition", "attachment;filename=\"" + archivo.getNombre() + "\"");
					ServletOutputStream out = response.getOutputStream();
					out.write(archivo.getBytes());
					out.flush();
					ctx.responseComplete();
				}
			} else {

				String ext = obtenerExtensionArchivo(archivo.getNombre());
				descargarArchivoGenerico("HER_ARCHIVO", archivo.getId().toString(), archivo.getNombre());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// LMOM
	public void descargarArchivoRequerimientoGenerico(Long id, Long requerimientoId) {

		FacesContext ctx = FacesContext.getCurrentInstance();
		ArchivoRequerimiento archivo = servicioProyecto.obtenerArchivoRequerimiento(id);

		try {

			if (archivo != null && archivo.getArchivo() != null && archivo.getBytes().length > 1) {
				if (!ctx.getResponseComplete()) {
					HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition", "attachment;filename=\"" + archivo.getNombre() + "\"");
					ServletOutputStream out = response.getOutputStream();
					out.write(archivo.getBytes());
					out.flush();
					ctx.responseComplete();
				}
			} else {

				String ext = obtenerExtensionArchivo(archivo.getNombre());
				descargarArchivoGenerico("HER_ARCHIVO_REQUERIMIENTO", archivo.getId().toString(),
						archivo.getFileName() + ext);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void descargarArchivoAvalGenerico(Long idArchivo) {

		List<ArchivoAval> archivos = servicioGeneral.obtenerObjetoXID(ArchivoAval.class, idArchivo.toString());

		ArchivoAval archivo = archivos.get(0);
		String ext = obtenerExtensionArchivo(archivo.getNombre());
		descargarArchivoGenerico("HER_ARCHIVO_AVAL", archivo.getId().toString(), archivo.getId() + ext);

	}

	public void descargarArchivoConvocatoriaExterna(ArchivoConvocatoriaExterna archivoExterno) {

		try {
			String ext = obtenerExtensionArchivo(archivoExterno.getNombre());
			descargarArchivoGenerico("HER_ARCHIVO_CONVOCATORIA_EXTERNA", archivoExterno.getId().toString(),
					archivoExterno.getId() + ext);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void descargarArchivoPropiedadIntelectual(ArchivoPropiedadIntelectual archivoExterno) {

		try {
			String ext = obtenerExtensionArchivo(archivoExterno.getNombre());
			descargarArchivoGenerico("HER_ARCHIVO_PROPIEDAD_INTELECTUAL", archivoExterno.getId().toString(),
					archivoExterno.getId() + ext);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void descargarArchivoConceptoContrato(ArchivoConceptoContrato archivoExterno) {

		try {
			String ext = obtenerExtensionArchivo(archivoExterno.getNombre());
			descargarArchivoGenerico("HER_ARCHIVO_CONCEPTO_CONTRATO", archivoExterno.getId().toString(),
					archivoExterno.getId() + ext);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void descargarArchivoInstructivo(ArchivoInstructivo archivoExterno) {

		try {
			String ext = obtenerExtensionArchivo(archivoExterno.getNombre());
			descargarArchivoGenerico("HER_ARCHIVO_INSTRUCTIVO", archivoExterno.getId().toString(),
					archivoExterno.getId() + ext);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void descargarArchivoInformeGenerico(ArchivoInforme archivoInforme) {

		FacesContext ctx = FacesContext.getCurrentInstance();

		if (archivoInforme != null && archivoInforme.getArchivo() != null && archivoInforme.getBytes().length > 0) {

			try {
				if (!ctx.getResponseComplete()) {
					HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"" + archivoInforme.getNombre() + "\"");
					ServletOutputStream out = response.getOutputStream();
					out.write(archivoInforme.getBytes());
					out.flush();
					ctx.responseComplete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (archivoInforme.getId() > 0) {
				String ext = obtenerExtensionArchivo(archivoInforme.getNombre());
				descargarArchivoGenerico("HER_ARCHIVO_INFORME", archivoInforme.getId().toString(),
						archivoInforme.getId() + ext);
			}
		}
	}

	public void descargarArchivoSolicitudGenerico(SolicitudDocumento solicitudDocumento) {

		FacesContext ctx = FacesContext.getCurrentInstance();

		if (solicitudDocumento.getId() > 0) {
			String ext = obtenerExtensionArchivo(solicitudDocumento.getNombre());
			descargarArchivoGenerico("HER_SOLICITUD_DOCUMENTO", solicitudDocumento.getId().toString(),
					solicitudDocumento.getId() + ext);
		}
	}

	public void descargarArchivoDocumentoAvalGenerico(Aval aval) {
		String ext = obtenerExtensionArchivo(aval.getArchivoAval());
		descargarArchivoGenerico("HER_AVAL", aval.getAviId().toString(), aval.getAviId() + ext);
	}
	
	public void descargarArchivoDocumentoAvalGrupo(Long idGrupo, String nombreArchivo) {
		String ext = obtenerExtensionArchivo(nombreArchivo);
		descargarArchivoGenerico("HER_GRUPO_AVAL", idGrupo.toString(), idGrupo.toString() + ext);
	}

	public void descargarArchivoDocumentoConvenioGenerico(Convenio convenio) {

		FacesContext ctx = FacesContext.getCurrentInstance();

		try {
			String ext = obtenerExtensionArchivo(convenio.getArchivo());
			descargarArchivoGenerico("HER_CONVENIO", convenio.getId().toString(), convenio.getId() + ext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void descargarArchivoRequerimientoGenerico(ArchivoRequerimiento arRequerimiento) {

		FacesContext ctx = FacesContext.getCurrentInstance();

		try {
			String ext = obtenerExtensionArchivo(arRequerimiento.getNombre());
			descargarArchivoGenerico("HER_ARCHIVO_REQUERIMIENTO", arRequerimiento.getId().toString(),
					arRequerimiento.getId() + ext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean eliminarArchivoProyectoGenerico(Long id, Proyecto proyectoActual, String nombreArchivo) {
		String ruta = "HER_ARCHIVO" + obtenerSubCarpetaArchivo(id) + "//" + id;
		return eliminarArchivoGenerico(ruta);
	}

	public boolean eliminarArchivoAvalGenerico(Long id) {
		String ruta = "HER_ARCHIVO_AVAL" + obtenerSubCarpetaArchivo(id) + "//" + id;
		return eliminarArchivoGenerico(ruta);
	}

	public boolean eliminarArchivoSolicitudGenerico(Long id) {
		String ruta = "HER_SOLICITUD_DOCUMENTO" + obtenerSubCarpetaArchivo(id) + "//" + id;
		return eliminarArchivoGenerico(ruta);
	}

	public boolean eliminarArchivoInformeGenerico(Long id) {
		String ruta = "HER_ARCHIVO_INFORME" + obtenerSubCarpetaArchivo(id) + "//" + id;
		return eliminarArchivoGenerico(ruta);
	}

	public boolean eliminarArchivoColeccionGenerico(Long id) {
		String ruta = "HER_ARCHIVO_COLECCION" + obtenerSubCarpetaArchivo(id) + "//" + id;
		return eliminarArchivoGenerico(ruta);
	}

	public boolean eliminarArchivoLaboratorio(Long id) {
		String ruta = ArchivoLaboratorio.TABLA_ARCHIVOS_LABORATORIOS + obtenerSubCarpetaArchivo(id) + "//" + id;
		System.out.println("eliminarArchivoLaboratorio:" + ruta);
		return eliminarArchivoGenerico(ruta);
	}

	public boolean eliminarArchivoLaboratorioPersona(Long id) {
		// String ruta =
		// ArchivoLaboratorioPersona.TABLA_ARCHIVOS_LABORATORIOS_PERSONA+
		// obtenerSubCarpetaArchivo(id) + "//" + id;
		String ruta = ArchivoLaboratorioPersona.TABLA_ARCHIVOS_LABORATORIOS_PERSONA + "//" + id;
		System.out.println("eliminarArchivoLaboratorio:" + ruta);
		return eliminarArchivoGenerico(ruta);
	}

	public void descargarArchivoLaboratorios(ArchivoLaboratorio archivoLaboratorioDescargar) {
		String idArchivo = archivoLaboratorioDescargar.getId().toString();
		String nombreArchivo = archivoLaboratorioDescargar.getNombreArchivo();
		descargarArchivoGenerico(ArchivoLaboratorio.TABLA_ARCHIVOS_LABORATORIOS, idArchivo, nombreArchivo);
	}

	public void descargarArchivoPersonaLaboratorios(ArchivoLaboratorioPersona archivoLaboratorioDescargar) {
		System.out.println("descargarArchivoLaboratorios IN:");

		String idArchivo = archivoLaboratorioDescargar.getId().toString();
		String nombreArchivo = archivoLaboratorioDescargar.getNombreArchivo();

		System.out.println("idArchivo: " + idArchivo);
		System.out.println("nombreArchivo: " + nombreArchivo);

		descargarArchivoGenerico(ArchivoLaboratorioPersona.TABLA_ARCHIVOS_LABORATORIOS_PERSONA, idArchivo,
				nombreArchivo);

		System.out.println("descargarArchivoLaboratorios OUT.");
	}

	public ArchivoLaboratorio subirArchivoLaboratorios(FileUploadEvent event, Tipos tipoArchivoLaboratorios) {
		UploadedFile archivoSubir = event.getFile();
		Long id = null;
		ArchivoLaboratorio archivoNuevo = null;
		if (archivoSubir != null) {
			archivoNuevo = new ArchivoLaboratorio();
			archivoNuevo.setNombreArchivo(archivoSubir.getFileName());
			archivoNuevo.setFechaArchivo(new Date());
			archivoNuevo.setTipoArchivo(tipoArchivoLaboratorios);

			// obtener secuencia:
			Long seq = servicioGeneral.consecutivoSecuencia("SEQ_ARCHIVO");
			archivoNuevo.setId(seq);
			id = archivoNuevo.getId();

			if (id != null) {
				Boolean exitoSubir = cargarArchivoDisco(archivoSubir, ArchivoLaboratorio.TABLA_ARCHIVOS_LABORATORIOS,
						id.toString());
				if (exitoSubir) {
					System.out.println("Nuevo archivo creado.");
					return archivoNuevo;
				} else {
					mensajeError("Error al subir archivo.");
					return null;
				}
			}

		}

		mensajeError("Error al subir archivo.");
		return null;
	}

	public ArchivoLaboratorioPersona subirArchivoLaboratoriosPersona(FileUploadEvent event,
			Tipos tipoArchivoLaboratorios) {
		UploadedFile archivoSubir = event.getFile();
		System.out.println("subirArchivoLaboratorios:" + archivoSubir.getFileName());
		Long id = null;
		ArchivoLaboratorioPersona archivoNuevo = null;
		if (archivoSubir != null) {
			archivoNuevo = new ArchivoLaboratorioPersona();
			archivoNuevo.setNombreArchivo(archivoSubir.getFileName());
			archivoNuevo.setFechaRegistro(new Date());

			System.out.println("tipoArchivoLaboratorios:" + tipoArchivoLaboratorios.getId());
			System.out.println("tipoArchivoLaboratorios:" + tipoArchivoLaboratorios.getNombre());
			System.out.println("tipoArchivoLaboratorios:" + tipoArchivoLaboratorios.getId());
			System.out.println("tipoArchivoLaboratorios:" + tipoArchivoLaboratorios.getNombre());

			archivoNuevo.setTipoArchivo(tipoArchivoLaboratorios);

			System.out.println("archivoNuevo.getId():" + archivoNuevo.getId());

			// obtener secuencia:
			Long seq = servicioGeneral.consecutivoSecuencia("SEQ_ARCH_LABORATORIO_PERSONA");
			System.out.println("consecutivoSecuencia:" + seq);
			archivoNuevo.setId(seq);
			id = archivoNuevo.getId();
			System.out.println("archivoNuevo.getId():" + id);

			if (id != null) {
				/* Descomentar para subir a producción */
				Boolean exitoSubir = cargarArchivoDisco(archivoSubir,
						ArchivoLaboratorioPersona.TABLA_ARCHIVOS_LABORATORIOS_PERSONA, id.toString());
				if (exitoSubir) {
					System.out.println("Nuevo archivo creado.");
					return archivoNuevo;
				} else {
					mensajeError("Error al subir archivo.");
					return null;
				}
			}

		}

		mensajeError("Error al subir archivo.");
		return null;
	}

	public boolean eliminarArchivoRequerimientoGenerico(Long id) {
		String ruta = "HER_ARCHIVO_REQUERIMIENTO//" + id;
		return eliminarArchivoGenerico(ruta);
	}

	public String obtenerExtensionArchivo(String nombre) {
		String ext = "";
		int index = nombre.lastIndexOf('.');
		if (index > 0) {
			ext = nombre.substring(index + 1);
		} else {
			ext = "_";
		}
		ext = "." + ext;
		return ext;
	}

	public Boolean eliminarArchivoGenerico(String archivoBorrar) {
		try {
			archivoBorrar = RUTA_ARCHIVOS + archivoBorrar;
			if (!servicioGeneral.esAmbienteProduccion()) {
				archivoBorrar = RUTA_ARCHIVOS_PRUEBAS + archivoBorrar;
			}
			File file = new File(archivoBorrar);
			if (file.delete()) {
				return true;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String insertarArchivoProyectoGenericoConTipos(UploadedFile archivo, Proyecto proyectoActual,
			List<Archivo> listaArchivos, TipoArchivo tipoArchivo) {
		try {
			if (archivo != null) {
				Long id = null;
				int ii = archivo.getFileName().lastIndexOf("\\");
				Archivo archivoFu = new Archivo();
				String name = removeCaractEspeciales(archivo.getFileName().substring(ii + 1));
				archivoFu.setNombre(name);

				archivoFu.setTipoArchivo(tipoArchivo);
				archivoFu.setProyecto(proyectoActual);
				Persona responsable = (Persona) sesion.getAttribute("persona");
				archivoFu.setResponsable(responsable);
				archivoFu.setFecha(new Date());
				servicioGeneral.guardarObjeto(archivoFu);
				id = archivoFu.getId();
				if (id != null) {
					if (cargarArchivoDisco(archivo, "HER_ARCHIVO", id.toString())) {
						if (listaArchivos != null) {
							listaArchivos.add(archivoFu);
						}
					} else {
						servicioGeneral.eliminarObjeto(archivoFu);
						return "FAILED";
					}
					return "archivos";
				}

			}
		} catch (Exception e) {
			return "FAILED";
		}

		return "FAILED";
	}

	public String insertarArchivoRequerimiento(UploadedFile archivo, Requerimiento requerimiento,
			List<ArchivoRequerimiento> listaArchivos, TipoArchivo tipoArchivo) {
		try {
			if (archivo != null) {
				Long id = null;
				int ii = archivo.getFileName().lastIndexOf("\\");
				ArchivoRequerimiento archivoFu = new ArchivoRequerimiento();
				String name = removeCaractEspeciales(archivo.getFileName().substring(ii + 1));
				archivoFu.setNombre(name);

				TipoArchivo tipoAr = new TipoArchivo();
				tipoAr = tipoArchivo;
				archivoFu.setTipoArchivoReq(tipoAr.getNombre());

				archivoFu.setRequerimiento(requerimiento);

				servicioGeneral.guardarObjeto(archivoFu);
				id = archivoFu.getId();

				if (id != null) {
					if (cargarArchivoDisco(archivo, "HER_ARCHIVO_REQUERIMIENTO", id.toString())) {
						listaArchivos.add(archivoFu);
					} else {
						servicioGeneral.eliminarObjeto(archivoFu);
						return "FAILED";
					}
					return "archivos";
				}

			}
		} catch (Exception e) {
			return "FAILED";
		}

		return "FAILED";
	}

	protected void eliminarManejadoresMovilidades() {
		sesion.removeAttribute("ManejadorCrearEditarMovilidadVisitante");
		sesion.removeAttribute("ManejadorCrearEditarMovilidadEvento");
		sesion.removeAttribute("ManejadorCrearEditarMovilidadDocArt");
		sesion.removeAttribute("ManejadorCrearMovilidadPosgradoInvModTres");
		sesion.removeAttribute("ManejadorCrearEditarMovilidadPosgradoInvestigacion");
		sesion.removeAttribute("ManejadorCrearEditarMovilidadEstArt");
	}

	public static int getRedondeado(double valor) {
		double decimal = valor % 1;
		int valorEntero = (int) valor;
		int aproximacion = (int) (decimal * 10);
		if (aproximacion >= 5 || aproximacion <= -5) {
			return valorEntero + 1;
		} else
			return valorEntero;
	}

	public static Long getRedondeadoToLong(double valor) {
		double decimal = valor % 1;
		Long valorEntero = (long) valor;
		Long aproximacion = (long) (decimal * 10);
		if (aproximacion >= 5 || aproximacion <= -5) {
			return valorEntero + 1;
		} else
			return valorEntero;
	}

	public Persona cargarPersonaActual() {
		Persona persona = null;
		try {
			if (sesion.getAttribute("persona") != null) {
				persona = (Persona) sesion.getAttribute("persona");
			}
		} catch (Exception e) {
			persona = null;
		}
		return persona;
	}

	public String eliminarCaracterSinReplace(String caracter, String cadena) {
		int valor;
		if (cadena != null) {
			do {
				String nuevaCadena = "";
				valor = cadena.indexOf(caracter);
				if (valor != -1) {
					nuevaCadena = cadena.substring(0, valor);
					if (valor < cadena.length())
						nuevaCadena += cadena.substring(valor + 1, cadena.length());
					cadena = nuevaCadena;
				}
			} while (valor != -1);
		} else {
			return "";
		}
		return cadena;
	}

	public String reemplazarCadenaSinReplace(String cadenaOriginal, String cadenaAEliminar, String cadenaAUsar) {
		int valor;
		String nuevaCadena = "";
		if (cadenaOriginal != null) {
			do {
				valor = cadenaOriginal.indexOf(cadenaAEliminar);
				if (valor != -1) {
					nuevaCadena = cadenaOriginal.substring(0, valor);
					if (valor < cadenaOriginal.length()) {
						nuevaCadena += cadenaAUsar
								+ cadenaOriginal.substring(valor + cadenaAEliminar.length(), cadenaOriginal.length());
					}
				}
			} while (valor != -1);
		} else {
			return "";
		}
		return nuevaCadena;
	}

	public List<Aval> obtenerAvalesProyecto(Long id) {
		List<Aval> listaAvalesPry = null;

		try {
			String hqlavales = "select #aviId a.aviId, #aviAvalfacultad a.aviAvalfacultad, #aviAvaldireccion a.aviAvaldireccion, #avalVice a.avalVice, #aviAvalUab a.aviAvalUab, #avalDRE a.avalDRE, #avalRectoria a.avalRectoria,"
					+ " #fechaUltimoEnvio a.fechaUltimoEnvio, " + " #tipo a.tipo, #aviEstado a.aviEstado "
					+ " from Aval a where a.idProyecto ='" + id + "' and a.aviEstado <> 'B' order by a.aviId asc";
			listaAvalesPry = servicioGeneral.obtenerObjetosLimitado(Aval.class, hqlavales);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (listaAvalesPry == null) {
			return new ArrayList<Aval>();
		} else {
			return listaAvalesPry;
		}
	}

	public List<Proyecto> autorizacionesBiodiversidadProyecto(Long id) {
		List<Proyecto> listaPry = null;

		try {
			String hqlbiodiversidad = "select #id p.id, #nombre p.nombre, #estadoProyecto p.estadoProyecto"
					+ " from Proyecto p where p.codigoDib ='" + id
					+ "' and p.estadoProyecto.id <> 'B' and p.modalidad.id in (" + MODALIDADES_PERMISO_CONTRATO
					+ ") order by p.id asc";
			listaPry = servicioGeneral.obtenerObjetosLimitado(Proyecto.class, hqlbiodiversidad);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaPry;
	}

	public ArchivoAval insertarArchivoAvalGenerico(long avalId, UploadedFile archivo, boolean direccion, String tipoArchivo) {
		boolean guardo = true;
		ArchivoAval archivoAval = null;
		try {
			if (archivo.getContents() != null) {

				int i = archivo.getFileName().lastIndexOf("\\");

				archivoAval = new ArchivoAval();
				archivoAval.setNombre(archivo.getFileName().substring(i + 1));
				archivoAval.setNombre(ReemplazaAcentos.quitarTildes(archivoAval.getNombre()));
				if (direccion) {
					archivoAval.setAvalCoor(avalId);
				} else {
					archivoAval.setAval(avalId);
				}
				TipoArchivo tipoArchivoAval = new TipoArchivo();			
				
				if(tipoArchivo != null){
					tipoArchivoAval.setId(Short.parseShort(tipoArchivo));
				}else {
					tipoArchivoAval.setId((short) 0);
				}
				archivoAval.setTipoArchivo(tipoArchivoAval);
				Persona persona = cargarPersonaActual();
				String id = "";
				if (persona != null && persona.getId() != null) {
					id = persona.getId().getDocumento() + "-" + persona.getId().getTipoDocumento();
				}
				archivoAval.setDescripcion(id);
				archivoAval.setFechaCreacion(new Date());
				servicioGeneral.guardarObjeto(archivoAval);
				if (archivoAval.getId() != null) {
					guardo = cargarArchivoDisco(archivo, "HER_ARCHIVO_AVAL", archivoAval.getId().toString());
				}
				if (guardo) {
					return archivoAval;
				}
			}
		} catch (Exception x) {
			x.printStackTrace();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
					x.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);

			guardo = false;
		}
		if (!guardo) {
			String nombreArchivo;

			if (archivo.getContents() != null) {
				nombreArchivo = archivo.getFileName();
			} else {
				nombreArchivo = "";
			}

			if (sesion.getAttribute("persona") != null) {
				try {
					Persona persona = (Persona) sesion.getAttribute("persona");
					Correo correo = new Correo();
					correo.setOrigen(Correo.CORREO_HERMES);
					//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
					correo.adicionarDireccion(persona.getEmail());
					correo.setAsunto("Problema al cargar archivo a solicitud de aval");
					correo.setCuerpo("Ha ocurrido un inconveniente al adjuntar el archivo " + nombreArchivo
							+ " a la solicitud de aval: " + avalId
							+ ". Recuerde que el tamaño máximo de los archivos es 3MB. Por favor inténtelo de nuevo, en caso de que el"
							+ " problema persista, por favor comuníquese con el equipo de soporte Hermes. Extensión 11111");
					servicioCorreo.enviarCorreo(correo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (archivoAval != null) {
				servicioGeneral.eliminarObjeto(archivoAval);
			}
		}
		return null;
	}

	public ArchivoConvocatoriaExterna insertarArchivoConvocatoriaExterna(long id, UploadedFile archivo) {
		boolean guardo = true;
		ArchivoConvocatoriaExterna archivoConvocatoriaExterna = null;
		try {
			if (archivo.getContents() != null) {

				int i = archivo.getFileName().lastIndexOf("\\");

				archivoConvocatoriaExterna = new ArchivoConvocatoriaExterna();
				archivoConvocatoriaExterna.setNombre(archivo.getFileName().substring(i + 1));

				Persona persona = cargarPersonaActual();

				if (persona != null && persona.getId() != null) {
					archivoConvocatoriaExterna.setPersonaCarga(persona);
				}
				archivoConvocatoriaExterna.setFechaCarga(new Date());
				servicioGeneral.guardarObjeto(archivoConvocatoriaExterna);
				if (archivoConvocatoriaExterna.getId() != null) {
					guardo = cargarArchivoDisco(archivo, "HER_ARCHIVO_CONVOCATORIA_EXTERNA",
							archivoConvocatoriaExterna.getId().toString());
				}
				if (guardo) {
					return archivoConvocatoriaExterna;
				}
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
		return null;
	}

	public ArchivoPropiedadIntelectual insertarArchivoPropiedadIntelectual(UploadedFile archivo) {
		boolean guardo = true;
		ArchivoPropiedadIntelectual archivoPropiedad = null;
		try {
			if (archivo.getContents() != null) {

				int i = archivo.getFileName().lastIndexOf("\\");

				archivoPropiedad = new ArchivoPropiedadIntelectual();
				archivoPropiedad.setNombre(archivo.getFileName().substring(i + 1));
				servicioGeneral.guardarObjeto(archivoPropiedad);
				if (archivoPropiedad.getId() != null) {
					guardo = cargarArchivoDisco(archivo, "HER_ARCHIVO_PROPIEDAD_INTELECTUAL",
							archivoPropiedad.getId().toString());
				}
				if (guardo) {
					return archivoPropiedad;
				}
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
		return null;
	}

	public ArchivoInstructivo insertarArchivoInstructivo(long id, UploadedFile archivo) {
		boolean guardo = true;
		ArchivoInstructivo archivoInstructivo = null;
		try {
			if (archivo.getContents() != null) {

				int i = archivo.getFileName().lastIndexOf("\\");

				archivoInstructivo = new ArchivoInstructivo();
				archivoInstructivo.setNombre(archivo.getFileName().substring(i + 1));

				Persona persona = cargarPersonaActual();

				if (persona != null && persona.getId() != null) {
					archivoInstructivo.setPersonaCarga(persona);
				}
				archivoInstructivo.setFechaCarga(new Date());
				servicioGeneral.guardarObjeto(archivoInstructivo);
				if (archivoInstructivo.getId() != null) {
					guardo = cargarArchivoDisco(archivo, "HER_ARCHIVO_INSTRUCTIVO",
							archivoInstructivo.getId().toString());
				}
				if (guardo) {
					return archivoInstructivo;
				}
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
		return null;
	}

	public ArchivoConceptoContrato insertarArchivoConceptoContrato(long id, UploadedFile archivo) {
		boolean guardo = true;
		ArchivoConceptoContrato archivoConceptoContrato = null;
		try {
			if (archivo.getContents() != null) {

				int i = archivo.getFileName().lastIndexOf("\\");

				archivoConceptoContrato = new ArchivoConceptoContrato();
				archivoConceptoContrato.setNombre(archivo.getFileName().substring(i + 1));

				Persona persona = cargarPersonaActual();

				if (persona != null && persona.getId() != null) {
					archivoConceptoContrato.setPersonaCarga(persona);
				}
				archivoConceptoContrato.setFechaCarga(new Date());
				servicioGeneral.guardarObjeto(archivoConceptoContrato);
				if (archivoConceptoContrato.getId() != null) {
					guardo = cargarArchivoDisco(archivo, "HER_ARCHIVO_CONCEPTO_CONTRATO",
							archivoConceptoContrato.getId().toString());
				}
				if (guardo) {
					return archivoConceptoContrato;
				}
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
		return null;
	}

	public ArchivoInforme insertarArchivoInformeGenerico(long informeId, UploadedFile archivo, Long idProyecto,
			Persona persona) {
		try {
			if (archivo.getContents() != null) {

				int i = archivo.getFileName().lastIndexOf("\\");

				ArchivoInforme archivoInforme = new ArchivoInforme();
				if (persona != null) {
					archivoInforme.setResponsable(persona);
				}
				archivoInforme.setNombre(archivo.getFileName().substring(i + 1));
				archivoInforme.setFecha(new Date());
				String mensaje = "Pry: " + idProyecto;
				archivoInforme.setDescripcion(mensaje);
				ProyectoInforme pi = new ProyectoInforme();
				pi.setId(informeId);
				archivoInforme.setInforme(pi);

				servicioGeneral.guardarObjeto(archivoInforme);
				if (archivoInforme.getId() != null) {
					cargarArchivoDisco(archivo, "HER_ARCHIVO_INFORME", archivoInforme.getId().toString());
				}
				return archivoInforme;
			}
		} catch (Exception x) {
			x.printStackTrace();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
					x.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return null;
	}
	
	public ArchivoInforme insertarArchivoInformeGenericoConTipos(long informeId, UploadedFile archivo, Long idProyecto,
			Persona persona, TipoArchivo tipoArchivo) {
		try {
			if (archivo.getContents() != null) {

				int i = archivo.getFileName().lastIndexOf("\\");

				ArchivoInforme archivoInforme = new ArchivoInforme();
				if (persona != null) {
					archivoInforme.setResponsable(persona);
				}
				archivoInforme.setNombre(archivo.getFileName().substring(i + 1));
				archivoInforme.setFecha(new Date());
				String mensaje = "Pry: " + idProyecto;
				archivoInforme.setDescripcion(mensaje);
				ProyectoInforme pi = new ProyectoInforme();
				pi.setId(informeId);
				archivoInforme.setInforme(pi);
				archivoInforme.setTipoArchivo(tipoArchivo);
				servicioGeneral.guardarObjeto(archivoInforme);
				if (archivoInforme.getId() != null) {
					cargarArchivoDisco(archivo, "HER_ARCHIVO_INFORME", archivoInforme.getId().toString());
				}
				return archivoInforme;
			}
		} catch (Exception x) {
			x.printStackTrace();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
					x.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return null;
	}


	public SolicitudDocumento insertarArchivoSolicitudGenerico(long solicitudId, UploadedFile archivo,
			TipoArchivo tipoArchivo) {
		try {
			if (archivo.getContents() != null) {

				int i = archivo.getFileName().lastIndexOf("\\");

				SolicitudDocumento solicitudDocumento = new SolicitudDocumento();
				solicitudDocumento.setNombre(archivo.getFileName().substring(i + 1));
				solicitudDocumento.setFechaGeneracion(new Date());
				Solicitud sol = new Solicitud();
				sol.setId(solicitudId);
				solicitudDocumento.setSolicitud(sol);

				if (tipoArchivo != null && tipoArchivo.getId() != null) {
					solicitudDocumento.setTipo(tipoArchivo);
				} else {
					solicitudDocumento.setTipo(null);
				}

				servicioGeneral.guardarObjeto(solicitudDocumento);
				if (solicitudDocumento.getId() != null) {
					cargarArchivoDisco(archivo, "HER_SOLICITUD_DOCUMENTO", solicitudDocumento.getId().toString());
				}
				return solicitudDocumento;
			}
		} catch (Exception x) {
			x.printStackTrace();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
					x.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return null;
	}

	public ArchivoConvocatoriaPadre insertarArchivoConvocatoriaPadre(String idConvocatoriaPadre, UploadedFile archivo) {
		try {
			if (archivo.getContents() != null) {

				int i = archivo.getFileName().lastIndexOf("\\");

				ArchivoConvocatoriaPadre archivoConvocatoriaPadre = new ArchivoConvocatoriaPadre();
				archivoConvocatoriaPadre.setConvocatoriaPadre(idConvocatoriaPadre);
				archivoConvocatoriaPadre.setNombre(archivo.getFileName());
				archivoConvocatoriaPadre.setEtiqueta(archivo.getFileName());
				archivoConvocatoriaPadre.setTerminos("Y");
				archivoConvocatoriaPadre.setTipoArchivo("S");

				servicioGeneral.guardarObjeto(archivoConvocatoriaPadre);

				if (archivoConvocatoriaPadre.getId() != null) {
					cargarArchivoDisco(archivo, "HER_ARCHIVO_CONVOCATORIA_PADRE",
							archivoConvocatoriaPadre.getId().toString());
				}
				return archivoConvocatoriaPadre;
			}
		} catch (Exception x) {
			x.printStackTrace();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
					x.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return null;
	}

	public ArchivoConvocatoriaPadre insertarArchivoOficioConvocatoriaPadre(String idConvocatoriaPadre,
			UploadedFile archivo, String tipoArchivo) {
		try {
			if (archivo.getContents() != null) {

				int i = archivo.getFileName().lastIndexOf("\\");

				ArchivoConvocatoriaPadre archivoConvocatoriaPadre = new ArchivoConvocatoriaPadre();
				archivoConvocatoriaPadre.setConvocatoriaPadre(idConvocatoriaPadre);
				archivoConvocatoriaPadre.setNombre(archivo.getFileName());
				archivoConvocatoriaPadre.setEtiqueta(archivo.getFileName());
				archivoConvocatoriaPadre.setTerminos("N");
				archivoConvocatoriaPadre.setTipoArchivo(tipoArchivo);

				servicioGeneral.guardarObjeto(archivoConvocatoriaPadre);

				if (archivoConvocatoriaPadre.getId() != null) {
					cargarArchivoDisco(archivo, "HER_ARCHIVO_CONVOCATORIA_PADRE",
							archivoConvocatoriaPadre.getId().toString());
				}
				return archivoConvocatoriaPadre;
			}
		} catch (Exception x) {
			x.printStackTrace();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
					x.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return null;
	}

	public ArchivoColeccion insertarArchivoColeccionGenerico(long colId, UploadedFile archivo, String tipo) {
		try {
			if (archivo.getContents() != null) {

				int i = archivo.getFileName().lastIndexOf("\\");

				ArchivoColeccion archivoColeccion = new ArchivoColeccion();
				archivoColeccion.setNombre(archivo.getFileName().substring(i + 1));
				archivoColeccion.setFecha(new Date());
				archivoColeccion.setTipo(tipo);
				Coleccion co = new Coleccion();
				co.setId(colId);
				archivoColeccion.setColeccion(co);

				servicioGeneral.guardarObjeto(archivoColeccion);
				if (archivoColeccion.getId() != null) {
					cargarArchivoDisco(archivo, "HER_ARCHIVO_COLECCION", archivoColeccion.getId().toString());
				}
				return archivoColeccion;
			}
		} catch (Exception x) {
			x.printStackTrace();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
					x.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return null;
	}

	public void descargarArchivoColeccionGenerico(ArchivoColeccion archivoColeccion) {
		if (archivoColeccion.getId() > 0) {
			String ext = obtenerExtensionArchivo(archivoColeccion.getNombre());
			descargarArchivoGenerico("HER_ARCHIVO_COLECCION", archivoColeccion.getId().toString(),
					archivoColeccion.getId() + ext);
		}
	}

	public void guardarHistoricoFormularioGrupo(Grupo grupoActual, Persona personaActual, String numeroFormulario) {
		HistoricoFormularioGrupo historicoFormularioGrupo = new HistoricoFormularioGrupo();
		historicoFormularioGrupo.setResponsable(personaActual);
		historicoFormularioGrupo.setGrupo(grupoActual);
		historicoFormularioGrupo.setNumero(numeroFormulario);
		historicoFormularioGrupo.setFecha(new Date());
		servicioGeneral.guardarObjeto(historicoFormularioGrupo);
	}

	public void guardarHistoricoEstadoGrupo(Grupo grupoActual, Persona personaActual, String nombreArchivo) {
		HistoricoEstadoGrupo historicoEstadoGrupo = new HistoricoEstadoGrupo();
		historicoEstadoGrupo.setResponsable(personaActual);
		historicoEstadoGrupo.setFecha(new Date());
		historicoEstadoGrupo.setGrupo(grupoActual);
		String[] params = cambiosGruposHistorico.split(",");
		for (String item : params) {
			if (item.equals("eg")) {
				historicoEstadoGrupo.setEstadoGrupo(grupoActual.getEstadoGrupo());
			} else if (item.equals("ec")) {
				historicoEstadoGrupo.setEstadoGrupoColciencias(grupoActual.getEstadoGrupoColciencias());
			}
		}
		historicoEstadoGrupo.setJustificacion(justificacionGrupoHistorico);
		historicoEstadoGrupo.setArchivoCambio(nombreArchivo);
		servicioGeneral.guardarObjeto(historicoEstadoGrupo);
		//Renombra el archivo del histórico
		try {
		if(!esCadenaVacia(nombreArchivo)) {
			File archivoActual = new File(RUTA_ARCHIVOS + "HER_HISTORICO_ESTADO_GRUPO" + "//" + grupoActual.getId().toString());
	        File nuevoArchivo = new File(RUTA_ARCHIVOS + "HER_HISTORICO_ESTADO_GRUPO" + "//" + historicoEstadoGrupo.getId().toString());

	        if (archivoActual.exists()) {
	            if (archivoActual.renameTo(nuevoArchivo)) {
	                System.out.println("El archivo se ha renombrado exitosamente.");
	            } else {
	                System.out.println("No se pudo renombrar el archivo.");
	            }
	        } else {
	            System.out.println("El archivo no existe en la ruta especificada.");
	        }
		}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void guardarHistoricoEstadoGrupo(Grupo grupoActual, Persona personaActual) {
		HistoricoEstadoGrupo historicoEstadoGrupo = new HistoricoEstadoGrupo();
		historicoEstadoGrupo.setResponsable(personaActual);
		historicoEstadoGrupo.setFecha(new Date());
		historicoEstadoGrupo.setGrupo(grupoActual);
		String[] params = cambiosGruposHistorico.split(",");
		for (String item : params) {
			if (item.equals("eg")) {
				historicoEstadoGrupo.setEstadoGrupo(grupoActual.getEstadoGrupo());
			} else if (item.equals("ec")) {
				historicoEstadoGrupo.setEstadoGrupoColciencias(grupoActual.getEstadoGrupoColciencias());
			}
		}
		historicoEstadoGrupo.setJustificacion(justificacionGrupoHistorico);
		servicioGeneral.guardarObjeto(historicoEstadoGrupo);
	}

	public void descargarArchivoBytes(String idArchivo, byte[] bytes, String nombreArchivo, String nombreTabla) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		try {
			if (bytes != null && bytes.length > 1) {
				if (!ctx.getResponseComplete()) {
					HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition", "attachment;filename=\"" + nombreArchivo + "\"");
					ServletOutputStream out = response.getOutputStream();
					out.write(bytes);
					out.flush();
					ctx.responseComplete();
				}
			} else {
				String ext = obtenerExtensionArchivo(nombreArchivo);
				descargarArchivoGenerico(nombreTabla, idArchivo.toString(), idArchivo + ext);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void imprimirInforme(ProyectoInforme informe) {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", informe.getId().toString());
		if (informe.getTipoInforme().getId().equals(TipoInforme.INFORME_AVANCE)) {
			r.setNombreReporte("/informes/informedeavance");
		} else if (informe.getTipoInforme().getId().equals(TipoInforme.INFORME_FINAL)) {
			if (informe.getProyecto().getModalidad() != null && informe.getProyecto().getModalidad().getTipo() != null
					&& informe.getProyecto().getModalidad().getTipo().getId().equals(TipoModalidad.BANCO_PROYECTOS)) {
				r.setNombreReporte("/informes/informeFinalBancoProyectos");
			} else {
				r.setNombreReporte("/informes/informeFinal");
			}
		}
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {

		} finally {
			context.responseComplete();
		}
	}

	public List<ActividadInforme> cargarActividadesInforme(Long idInforme) {
		List<ActividadInforme> listaActividades = null;
		if (idInforme != null) {
			listaActividades = servicioGeneral.obtenerObjetos(ActividadInforme.class,
					"select ai from ActividadInforme ai " + "where ai.proyectoInforme.id = '" + idInforme.toString()
							+ "'");
		}
		return listaActividades;
	}

	public List<ResultadoInforme> cargarResultadosInforme(Long idInforme) {
		List<ResultadoInforme> listaResultados = null;
		if (idInforme != null) {
			listaResultados = servicioGeneral.obtenerObjetos(ResultadoInforme.class,
					"select ri from ResultadoInforme ri " + "where ri.proyectoInforme.id = '" + idInforme.toString()
							+ "'");
		}
		return listaResultados;
	}

	public List<FuenteFinanciacion> cargarEntidadesExternas(String sqlConSinUnal) {
		List<FuenteFinanciacion> listaEntidadesExternas = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class,
				sqlConSinUnal);
		return listaEntidadesExternas;
	}

	public List<DominioDetalle> cargarEstadosPostulacionInforme() {
		List<DominioDetalle> listaResultadosProyecto = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select dd from DominioDetalle dd where " + "dd.identificador.id = '" + TIPO_ESTADO_POSTULACION + "'");
		return listaResultadosProyecto;
	}

	public String cortarCadena(String cadena, int max) {
		if (cadena != null && cadena.length() > max) {
			cadena = cadena.replaceAll("\r\n", "\n");
			return cadena.substring(0, cadena.length() - 1);
		} else if (cadena != null) {
			return cadena;
		}
		return "";
	}

	public static long getMaximoTamanoArchivos() {
		return MAXIMO_TAMANO_ARCHIVOS;
	}

	protected String obtenerValorMapContext(String cadena) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> map = context.getExternalContext().getRequestParameterMap();
		Object o = (Object) map.get(cadena);
		return (String) o;
	}

	/**
	 * Imprimir reporte individual de grupo
	 * 
	 * @return
	 */
	public void imprimirReporteGrupo(String id) {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("gru", id);
		r.setNombreReporte("/portafolio/Grupo");
		r.setFormato(ReporteBirt.FORMATO_PDF);
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
	
	public void imprimirReporteFormatoGrupo(String id) {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", id);
		Boolean esBDPruebas = false;
		try {
			esBDPruebas = !servicioGeneral.esAmbienteProduccion();
		} catch (HibernateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		r.adicionarParametro("BDPruebas",esBDPruebas.toString());
		r.setNombreReporte("/grupo/formato-grupo");
		r.setFormato(ReporteBirt.FORMATO_PDF);
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

	public ApplicationContext getAppCtx() {
		return appCtx;
	}

	public void setAppCtx(ApplicationContext appCtx) {
		this.appCtx = appCtx;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public Persona getPersonaActual() {
		return personaActual;
	}

	public void setPersonaActual(Persona personaActual) {
		this.personaActual = personaActual;
	}

	public HttpSession getSesion() {
		return sesion;
	}

	public void setSesion(HttpSession sesion) {
		this.sesion = sesion;
	}

	// Setter y getters de los servicios

	public IServicioGeneral getServicioGeneral() {
		return servicioGeneral;
	}

	public void setServicioGeneral(IServicioGeneral servicioGeneral) {
		this.servicioGeneral = servicioGeneral;
	}

	public IServicioGrupo getServicioGrupo() {
		return servicioGrupo;
	}

	public void setServicioGrupo(IServicioGrupo servicioGrupo) {
		this.servicioGrupo = servicioGrupo;
	}

	public IServicioModalidad getServicioModalidad() {
		return servicioModalidad;
	}

	public void setServicioModalidad(IServicioModalidad servicioModalidad) {
		this.servicioModalidad = servicioModalidad;
	}

	public IServicioPersona getServicioPersona() {
		return servicioPersona;
	}

	public void setServicioPersona(IServicioPersona servicioPersona) {
		this.servicioPersona = servicioPersona;
	}

	public IServicioProyecto getServicioProyecto() {
		return servicioProyecto;
	}

	public void setServicioProyecto(IServicioProyecto servicioProyecto) {
		this.servicioProyecto = servicioProyecto;
	}

	public IServicioCorreo getServicioCorreo() {
		return servicioCorreo;
	}

	public void setServicioCorreo(IServicioCorreo servicioCorreo) {
		this.servicioCorreo = servicioCorreo;
	}

	public IServicioSolicitudes getServicioSolicitudes() {
		return servicioSolicitudes;
	}

	public void setServicioSolicitudes(IServicioSolicitudes servicioSolicitudes) {
		this.servicioSolicitudes = servicioSolicitudes;
	}

	public IServicioAlertas getServicioAlertas() {
		return servicioAlertas;
	}

	public void setServicioAlertas(IServicioAlertas servicioAlertas) {
		this.servicioAlertas = servicioAlertas;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void agregaParametroAlRequest(String nombreP, String m) {
		request.setAttribute(nombreP, m);
	}

	public IServicioMovilidad getServicioMovilidad() {
		return servicioMovilidad;
	}

	public void setServicioMovilidad(IServicioMovilidad servicioMovilidad) {
		this.servicioMovilidad = servicioMovilidad;
	}

	/* Convertir números a letras hasta -- */

	private final String[] unidades = { "", "uno ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ",
			"nueve " };
	private final String[] decenas = { "diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
			"diecisiete ", "dieciocho ", "diecinueve ", "veinte ", "treinta ", "cuarenta ", "cincuenta ", "sesenta ",
			"setenta ", "ochenta ", "noventa " };
	private final String[] centenas = { "", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ",
			"seiscientos ", "setecientos ", "ochocientos ", "novecientos " };
	private String numConvExterna; // Almacena el número de la convocatoria
									// externa para ser consultada en el enlace
									// de avales

	public String convertir(String numero, boolean mayusculas) {
		String literal = "";

		numero = numero.replace(".", ",");

		if (numero.indexOf(",") == -1) {
			numero = numero + ",00";
		}

		// numeros desde 0 hasta 999.999.999.999
		if (Pattern.matches("\\d{1,12},\\d{1,2}", numero)) {
			// separa la parte decimal de la entera
			String num[] = numero.split(",");

			// selecciona el tipo de transformacion a realizar
			// segun la cantidad numerica
			try {
				if (Long.parseLong(num[0]) == 0) {
					literal = "cero";
				} else if (Long.parseLong(num[0]) > 999999999) {
					literal = getMilesMillones(num[0]); // milmillones
				} else if (Long.parseLong(num[0]) > 999999) {
					literal = getMillones(num[0]); // millones
				} else if (Long.parseLong(num[0]) > 999) {
					literal = getMiles(num[0]); // miles
				} else if (Long.parseLong(num[0]) > 99) {
					literal = getCentenas(num[0]); // centenas

				} else if (Long.parseLong(num[0]) > 20 && Integer.parseInt(num[0]) < 30) {
					literal = getVeintes(num[0]); // veintenas

				} else if (Long.parseLong(num[0]) > 9) {
					literal = getDecenas(num[0]); // decenas
				} else {
					literal = getUnidades(num[0]); // unidades
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// si la variable es true se devuelve en mayusculas
			// si la variable es false se devuelve en minusculas
			if (mayusculas) {
				return (literal).toUpperCase();
			} else {
				return (literal);
			}
		} else {
			return literal = null;
		}
	}

	// Trasforma las "veintenas"
	private String getVeintes(String numero) {
		String num = numero.substring(numero.length() - 1);
		return "veinti" + unidades[Integer.parseInt(num)];
	}

	// transforma las unidades
	private String getUnidades(String numero) {
		String num = numero.substring(numero.length() - 1);
		return unidades[Integer.parseInt(num)];
	}

	// transforma las decenas
	private String getDecenas(String num) {
		int n = Integer.parseInt(num);
		if (n < 10) {
			return getUnidades(num);

		} else if (n > 20 && n < 30) {
			String u = getVeintes(num);
			return u;

		} else if (n > 19) {
			String u = getUnidades(num);
			if (u.equals("")) {
				return decenas[Integer.parseInt(num.substring(0, 1)) + 8];
			} else {
				return decenas[Integer.parseInt(num.substring(0, 1)) + 8] + "y " + u;
			}
		} else {
			return decenas[n - 10];
		}
	}

	// transforma las centenas
	private String getCentenas(String num) {
		if (Integer.parseInt(num) > 99) {
			if (Integer.parseInt(num) == 100) {
				return "cien ";
			} else {
				return centenas[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1));
			}
		} else {
			return getDecenas(Integer.parseInt(num) + "");
		}
	}

	// transforma los miles
	private String getMiles(String num) {
		String c = num.substring(num.length() - 3);
		String m = num.substring(0, num.length() - 3);
		String n = "";

		if (Integer.parseInt(m) > 0) {
			n = getCentenas(m);

			if (n.equals("uno ")) {
				n = "";
			}

			n = n.replace("veintiuno", "veintiun");
			n = n.replace("y uno", "y un");
			n = n.replace("uno", "un");

			return n + "mil " + getCentenas(c);
		} else {
			return "" + getCentenas(c);
		}
	}

	// transforma los millones
	private String getMillones(String num) {
		String miles = num.substring(num.length() - 6);
		String millon = num.substring(0, num.length() - 6);
		String n = "";

		if (millon.length() > 1) {
			n = getCentenas(millon) + "millones ";
		} else {
			n = getUnidades(millon) + "millones ";
			// n="un millon ";
		}

		n = n.replace("uno millones", "un millon");

		return n + getMiles(miles);
	}

	// transforma los miles millones
	private String getMilesMillones(String num) {

		String milesMillones = num.substring(0, num.length() - 9);
		String millon = num.substring(num.length() - 9);
		String n = "";

		if (milesMillones.length() > 1) {
			n = getCentenas(milesMillones) + "mil ";
		} else {
			n = getUnidades(milesMillones) + "mil ";
			// n="mil millones ";
		}

		n = n.replace("uno mil ", "un mil ");

		return n + getMillones(millon);
	}

	public String getNombreMes(int mes) {

		String result = "";

		switch (mes) {
		case 0: {
			result = "Enero";
			break;
		}
		case 1: {
			result = "Febrero";
			break;
		}
		case 2: {
			result = "Marzo";
			break;
		}
		case 3: {
			result = "Abril";
			break;
		}
		case 4: {
			result = "Mayo";
			break;
		}
		case 5: {
			result = "Junio";
			break;
		}
		case 6: {
			result = "Julio";
			break;
		}
		case 7: {
			result = "Agosto";
			break;
		}
		case 8: {
			result = "Septiembre";
			break;
		}
		case 9: {
			result = "Octubre";
			break;
		}
		case 10: {
			result = "Noviembre";
			break;
		}
		case 11: {
			result = "Diciembre";
			break;
		}
		default: {
			result = "Error";
			break;
		}
		}

		return result;

	}

	protected HistoricoEstadoInforme crearHistoricoEstadoInforme(ProyectoInforme proyectoInforme,
			String observaciones) {
		HistoricoEstadoInforme historicoEstadoInforme = new HistoricoEstadoInforme();
		historicoEstadoInforme.setProyectoInforme(proyectoInforme);
		historicoEstadoInforme.setEstadoInforme(proyectoInforme.getEstadoInforme());
		historicoEstadoInforme.setComentarios(observaciones);
		historicoEstadoInforme.setFecha(new Date());
		Persona persona = (Persona) sesion.getAttribute("persona");
		historicoEstadoInforme.setResponsable(persona);
		return historicoEstadoInforme;
	}

	protected HistoricoEstadoSolicitud crearHistoricoEstadoSolicitud(Solicitud solicitud, String observaciones) {
		HistoricoEstadoSolicitud historicoEstadoSolicitud = new HistoricoEstadoSolicitud();
		historicoEstadoSolicitud.setSolicitud(solicitud);
		historicoEstadoSolicitud.setRespuesta(solicitud.getRespuesta());
		historicoEstadoSolicitud.setComentarios(observaciones);
		historicoEstadoSolicitud.setFecha(new Date());
		Persona persona = (Persona) sesion.getAttribute("persona");
		historicoEstadoSolicitud.setResponsable(persona);
		return historicoEstadoSolicitud;
	}

	protected void crearHistoricoEstadoMovilidadInvestigador(MovilidadInvestigador movilidad, String justifiacion) {
		HistoricoEstadoMovilidad historicoEstadoMovilidad = new HistoricoEstadoMovilidad();
		historicoEstadoMovilidad.setIdMovilidad(movilidad.getId());
		historicoEstadoMovilidad.setAceptacion(movilidad.getAceptacion());
		historicoEstadoMovilidad.setAprobacion(movilidad.getAprobacion());
		historicoEstadoMovilidad.setFecha(new Date());
		historicoEstadoMovilidad.setJustificacion(justifiacion);
		Persona persona = (Persona) sesion.getAttribute("persona");
		historicoEstadoMovilidad.setResponsable(persona);
		servicioGeneral.guardarObjeto(historicoEstadoMovilidad);
	}

	/**
	 * Crear histórico de estado de aval, requiere el aval, la persona
	 * responsable y desde donde se realiza el cambio
	 * 
	 * @param aval
	 * @param persona
	 * @param origen
	 */
	public void crearHistoricoEstadoAval(Aval aval, Persona persona, String origen ) {
		try {

			HistoricoEstadoAval cambioEstadoAval = new HistoricoEstadoAval();

			cambioEstadoAval.setFecha(new Date());
			cambioEstadoAval.setAval(aval);
			cambioEstadoAval.setEstadoAval(aval.getAviEstado());
			cambioEstadoAval.setAvalUab(aval.getAviAvalUab());
			cambioEstadoAval.setAvalFacultad(aval.getAviAvalfacultad());
			cambioEstadoAval.setAvalDireccion(aval.getAviAvaldireccion());
			cambioEstadoAval.setAvalVicerrectoria(aval.getAvalVice());
			cambioEstadoAval.setAvalDRE(aval.getAvalDRE());
			cambioEstadoAval.setAvalRectoria(aval.getAvalRectoria());
			cambioEstadoAval.setRevisor(persona);

			if (origen!=null && origen.equals("D")) {
				cambioEstadoAval.setJustificacion("Docente");
			} else if (origen.equals("FA")) {
				if (aval.getDescripcionfacultad() != null && !"".equals(aval.getDescripcionfacultad().trim())) {
					cambioEstadoAval
							.setJustificacion("Vicedecanatura de investigación - " + aval.getDescripcionfacultad());
				}
			} else if (origen.equals("DI")) {
				if (aval.getAviTextoCompCoor() != null && !"".equals(aval.getAviTextoCompCoor().trim())) {
					cambioEstadoAval.setJustificacion("Dirección de investigación - " + aval.getAviTextoCompCoor());
				}
			} else if (origen.equals("VI")) {
				if (aval.getTextoVicerrectoria() != null && !"".equals(aval.getTextoVicerrectoria().trim())) {
					cambioEstadoAval
							.setJustificacion("Vicerrectoría de Investigación - " + aval.getTextoVicerrectoria());
				}
			} else if (origen.equals("DE")) {
				if (aval.getComentariosUab() != null && !"".equals(aval.getComentariosUab().trim())) {
					cambioEstadoAval.setJustificacion("Unidad académica básica - " + aval.getComentariosUab());
				}
			} else if (origen.equals("BI")) {
				if (aval.getDescripcionfacultad() != null && !"".equals(aval.getDescripcionfacultad().trim())) {
					cambioEstadoAval.setJustificacion(aval.getDescripcionfacultad());
				}
			} else if (origen.equals("DRE")) {
				if (aval.getTextoDRE() != null && !"".equals(aval.getTextoDRE().trim())) {
					cambioEstadoAval.setJustificacion("DRE - " + aval.getTextoDRE());
				}
				if(aval.getAviEstado().equals(Aval.REVISADO_FACULTAD) || aval.getAviEstado().equals(Aval.REVISADO_DIRECCION)) {
					cambioEstadoAval.setEstadoAval(Aval.DEVUELTO_DRE);
				}
			} else if (origen.equals("CEPI")) {
				if (aval.getTextoCEPI() != null && !"".equals(aval.getTextoCEPI().trim())) {
					cambioEstadoAval.setJustificacion("CEPI - " + aval.getTextoCEPI());
				}
			} else if (origen.equals("CEPI-Recurso")) {
				if (aval.getTextoCEPI() != null && !"".equals(aval.getTextoCEPI().trim())) {
					cambioEstadoAval.setJustificacion("CEPI (Revisión recurso) - " + aval.getTextoCEPI());
				}
			} else if (origen.equals("CESI")) {
				if (aval.getTextoCESI() != null && !"".equals(aval.getTextoCESI().trim())) {
					cambioEstadoAval.setJustificacion("CESI - " + aval.getTextoCESI());
				}
			} else if (origen.equals("CESI-Queja")) {
				if (aval.getTextoCESIQueja() != null && !"".equals(aval.getTextoCESIQueja().trim())) {
					cambioEstadoAval.setJustificacion("CESI (Revisión Queja) - " + aval.getTextoCESIQueja());
				}
			} else if (origen.equals("REC")) {
				if (aval.getTextoRectoria() != null && !"".equals(aval.getTextoRectoria().trim())) {
					cambioEstadoAval.setJustificacion("Rectoría - " + aval.getTextoRectoria());
				}
				if(aval.getAviEstado().equals(Aval.REVISADO_VICERRECTORIA) || aval.getAviEstado().equals(Aval.REVISADO_DIRECCION)) {
					cambioEstadoAval.setEstadoAval(Aval.DEVUELTO_RECTORIA);
				}
			}else {
				cambioEstadoAval.setJustificacion(origen);
			}

			this.servicioGeneral.guardarObjeto(cambioEstadoAval);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Devuelte true si tiene compromisos pendientes del tipo de informe enviado
	 * y false en caso contrario
	 * 
	 * @param codigoProyecto
	 * @return
	 */
	public boolean proyectoTieneCompromisoPendiente(Long codigoProyecto, Long tipoCompromiso) {
		try {
			List<ProyectoCompromiso> listaCompromisos = servicioGeneral.obtenerObjetosLimitado(ProyectoCompromiso.class,
					"select #id pc.id from ProyectoCompromiso pc " + "where pc.proyecto.id = '" + codigoProyecto
							+ "' and pc.cumplido = 'N' and pc.tipoInforme.id = '" + tipoCompromiso + "' "
							+ "and pc.fechaVencimiento < SYSDATE");

			if (listaCompromisos != null && listaCompromisos.size() > 0) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Devuelte true si tiene compromisos asignados del tipo de informe enviado
	 * y false en caso contrario
	 * 
	 * @param codigoProyecto
	 * @return
	 */
	public boolean proyectoTieneCompromiso(Long codigoProyecto, Long tipoCompromiso) {
		try {
			List<ProyectoCompromiso> listaCompromisos = servicioGeneral.obtenerObjetosLimitado(ProyectoCompromiso.class,
					"select #id pc.id from ProyectoCompromiso pc " + "where pc.proyecto.id = '" + codigoProyecto
							+ "' and pc.cumplido = 'N' and pc.tipoInforme.id = '" + tipoCompromiso + "' ");
			if (listaCompromisos != null && listaCompromisos.size() > 0) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Devuelte true si tiene cartas asociadas del tipo indicado en el parametro
	 * 
	 * @param codigoProyecto
	 * @return
	 */
	public boolean proyectoTieneCarta(Long codigoProyecto, Long tipoCarta) {
		try {
			List<ProyectoCarta> listaCartas = servicioGeneral.obtenerObjetosLimitado(ProyectoCarta.class,
					"select #id pc.id from ProyectoCarta pc " + "where pc.proyecto.id = '" + codigoProyecto
							+ "' and pc.estadoCarta.id != 'B' and pc.carta.id = '" + tipoCarta + "' ");
			if (listaCartas != null && listaCartas.size() > 0) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param id
	 * @return objeto del tipo Tipos
	 */
	public Tipos obtenerTipoXid(Long id) {
		return (Tipos) servicioGeneral.obtenerObjeto(new Tipos(), id);
	}

	protected List<Object[]> cargarFinanciacionActualProyecto(List<DetalleCambioRubro> detallesCambioRubros,
			Proyecto proyecto, List<DetalleAdicionPresupuesto> listaDetalleAdicionesPresupuesto) {

		List<Financiacion> financiaciones = proyecto.getListaFinancionesFicha();

		List<Object[]> informacionFinaciera = new ArrayList<Object[]>();
		Iterator<Financiacion> i = financiaciones.iterator();
		while (i.hasNext()) {
			Financiacion financiacion = i.next();
			Object[] objetoFinanciacion = new Object[2];
			objetoFinanciacion[0] = financiacion;

			List<Gasto> listaGastos = servicioGeneral.obtenerObjetos(Gasto.class,
					"from Gasto g " + "where g.financiacion.id = '" + financiacion.getId() + "'");

			if (listaGastos != null) {
				List<Object[]> listaGastoObjeto = new ArrayList<Object[]>();
				Iterator<Gasto> j = listaGastos.iterator();

				while (j.hasNext()) {
					Object[] objetoGasto = new Object[3];
					Gasto gasto = j.next();
					objetoGasto[0] = gasto;
					// Entradas
					objetoGasto[1] = 0L;
					// Salidas
					objetoGasto[2] = 0L;

					// Se recorren los cambios de rubros para saber si se aplica
					Iterator<DetalleCambioRubro> l = detallesCambioRubros.iterator();
					while (l.hasNext()) {
						DetalleCambioRubro detalleCambioRubro = l.next();
						if (gasto.getId().equals(detalleCambioRubro.getGasto().getId())
								&& !detalleCambioRubro.isAplicadoOrigen()) {
							Long valor = (Long) objetoGasto[2];
							objetoGasto[2] = valor + detalleCambioRubro.getValor();
							detalleCambioRubro.setAplicadoOrigen(true);
						} else if (gasto.getFinanciacion().getId().equals(detalleCambioRubro.getGasto().getFinanciacion().getId()) && gasto.getTipoRubro().getId().equals(detalleCambioRubro.getTipoRubro().getId())
								&& !detalleCambioRubro.isAplicadoDestino()) {
							Long valor = (Long) objetoGasto[1];
							objetoGasto[1] = valor + detalleCambioRubro.getValor();
							detalleCambioRubro.setAplicadoDestino(true);
						}
					}

					// Se recorren los adiciones de presupuesto para saber si se
					// aplica
					if (listaDetalleAdicionesPresupuesto != null && listaDetalleAdicionesPresupuesto.size() > 0) {
						for (Iterator iterator = listaDetalleAdicionesPresupuesto.iterator(); iterator.hasNext();) {
							DetalleAdicionPresupuesto detalleAdicion = (DetalleAdicionPresupuesto) iterator.next();
							if (detalleAdicion.getGasto() != null) {
								if (gasto.getId().equals(detalleAdicion.getGasto().getId())) {
									Long valor = (Long) objetoGasto[1];
									objetoGasto[1] = valor + detalleAdicion.getValorAdicionRubro();
								}
							}
						}
					}

					listaGastoObjeto.add(objetoGasto);
				}

				objetoFinanciacion[1] = listaGastoObjeto;
			}

			informacionFinaciera.add(objetoFinanciacion);
		}

		// Se verifica si cambio algún cambio de rubro
		return informacionFinaciera;

	}

	protected List<DetalleCambioRubro> cargarDetallesCambioRubrosAprobados(List<Solicitud> listaSolicitudes) {
		// Se obtienen todos los detalles de cambio de rubro realizados
		List<DetalleCambioRubro> detallesCambioRubros = new ArrayList<DetalleCambioRubro>();

		if (listaSolicitudes != null) {
			Iterator<Solicitud> k = listaSolicitudes.iterator();
			while (k.hasNext()) {
				Solicitud solicitud = k.next();
				if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CAMBIO_RUBROS)
						&& solicitud.getDetalleCambioRubro() != null && solicitud.getRespuesta() != null
						&& solicitud.getRespuesta().equals("A") && solicitud.getTamañoListaDetalleRubro() > 0) {
					detallesCambioRubros.addAll(solicitud.getDetalleCambioRubro());
				}
			}
		}
		return detallesCambioRubros;
	}

	protected List<DetalleAdicionPresupuesto> cargarDetallesAdicionesPresupuestoAprobadas(
			List<Solicitud> listaSolicitudes) {
		List<DetalleAdicionPresupuesto> detallesAdicionPresupuesto = new ArrayList<DetalleAdicionPresupuesto>();

		if (listaSolicitudes != null) {
			Iterator<Solicitud> k = listaSolicitudes.iterator();
			while (k.hasNext()) {
				Solicitud solicitud = k.next();
				if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.ADICION_PRESPUESTAL)
						&& solicitud.getRespuesta() != null && solicitud.getRespuesta().equals("A")) {
					SolicitudAdicionPresupuestal adicion = servicioSolicitudes
							.obtenerSolicitudAdicionPresupuestal(solicitud);
					if (adicion.getDetalleAdicionPresupuesto().size() > 0) {
						detallesAdicionPresupuesto.addAll(adicion.getDetalleAdicionPresupuesto());
					}
				}
			}
		}
		return detallesAdicionPresupuesto;
	}

	public Date getToday() {
		return new Date();
	}

	protected boolean esListaVacia(List lista) {
		return lista == null || (lista != null && lista.isEmpty());
	}

	protected boolean esArrayVacio(Object[] array) {
		return array == null || (array != null && array.length == 0);
	}

	protected void cambiarArchivoUbicacionDisco(String currentPath, String fileSendPath) {

		// Original file
		File dataInputFile = new File(currentPath);

		// New path
		File fileSend = new File(fileSendPath);

		// Moving the file.
		if (fileSend.exists()) {
			fileSend.delete();
		}
		dataInputFile.renameTo(fileSend);

	}

	/**
	 * Metodo para recortar imagen.
	 * 
	 * @param CroppedImage
	 *            objeto de recorte de imagen,
	 * @param Ruta
	 *            en donde debe quedar la imagen
	 * @param ServletContext
	 *            actual.
	 * @return boolean true si la imagen pudo ser cortada y copiada a la ruta
	 *         indicada. false si no se pudo cortar la imagen.
	 * @author Mauricio Amaya Rios
	 * @since 04/05/2016
	 */
	protected boolean recortarImagen(CroppedImage croppedImage, String newFilePathDestino,
			ServletContext servletContext) throws ImageFormatException {
		if (croppedImage != null) {
			// Se cargan las rutas de los archivos temporales y destino
			String newFilePathTemporal = servletContext.getRealPath("") + File.separator + CARPETA_TEMPORAL_IMAGENES
					+ File.separator + Util.getRandomImageName() + ".jpg";

			FileImageOutputStream imageOutput;

			try {

				/**
				 * Inicialmente se trabaja con la imagen que se corta y se pone
				 * en una carpeta temporal para luego pasarla a la carpeta
				 * definitiva en donde se guardara la imagen del grupo.
				 */
				// Se recorta la imagen y se pone en la carpeta temporal
				imageOutput = new FileImageOutputStream(new File(newFilePathTemporal));
				imageOutput.write(croppedImage.getBytes(), 0, croppedImage.getBytes().length);
				imageOutput.close();

				/**
				 * Se crea el archivo en la carpeta definitiva
				 */
				// Se crear archivo de en donde se pondra la imagen cortada
				// definitiva
				File fileDestino = new File(newFilePathDestino);
				// Se carga la imagen cortada en el archivo destino
				ImageIO.write(ImageIO.read(new ByteArrayInputStream(croppedImage.getBytes())), "jpg", fileDestino);

				return true;

			} catch (FileNotFoundException e) {
				return false;
			} catch (IOException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Metodo para recortar imagen.
	 * 
	 * @param CroppedImage
	 *            objeto de recorte de imagen,
	 * @param Ruta
	 *            en donde debe quedar la imagen
	 * @param ServletContext
	 *            actual.
	 * @return boolean true si la imagen pudo ser cortada y copiada a la ruta
	 *         indicada. false si no se pudo cortar la imagen.
	 * @author Mauricio Amaya Rios
	 * @since 04/05/2016
	 */
	protected boolean cargarImagenDisco(UploadedFile archivoSubir, ServletContext servletContext,
			String nombreArchivoDestino, int tamanioMinimo, int tamanioEscala, int tamanioMaximoLado) {

		boolean error = false;
		if (archivoSubir != null) {
			try {
				// Se crean las variables para la carga y redimensionar
				InputStream in = archivoSubir.getInputstream();
				BufferedImage bufferedImagen = ImageIO.read(in);

				// Se verifica si se carga una imagen mas pequenia que el
				// tamanio minimo.
				if (bufferedImagen.getWidth() < tamanioMinimo || bufferedImagen.getHeight() < tamanioMinimo) {
					error = true;
					mensajeError("La imagen ingresada debe tener minimo " + tamanioMinimo + " pixeles de ancho y " + tamanioMinimo + " pixeles de alto.");
				} else {

					// Se analiza si es necesario redimensionar la imagen
					File archivoDestino = new File(nombreArchivoDestino);
					OutputStream out;
					BufferedImage imagen;

					// Se verifica si la imagen es mas grande que el tamanio al
					// que se estala en los dos lados
					if (bufferedImagen.getWidth() > tamanioEscala && bufferedImagen.getHeight() > tamanioEscala) {

						// Si es mas grande que el tamanio de estala por los dos
						// lados, se escala al tamnio de escala
						imagen = Scalr.resize(bufferedImagen, tamanioEscala);
						ImageIO.write(imagen, "jpg", archivoDestino);
						out = new FileOutputStream(new File(archivoDestino.getName()));

					} else if (bufferedImagen.getWidth() > tamanioMaximoLado
							|| bufferedImagen.getHeight() > tamanioMaximoLado) {

						// Si no, se verifica si uno de los dados es superior al
						// tamanio maximo de lado, en este caso
						// se escala.

						if (bufferedImagen.getWidth() > tamanioEscala) {
							imagen = Scalr.resize(bufferedImagen, tamanioEscala, tamanioEscala);
						} else {
							imagen = Scalr.resize(bufferedImagen, tamanioEscala, tamanioEscala);
						}
						out = new FileOutputStream(new File(archivoDestino.getName()));

					} else {

						// si no hay problemas con el tamanio, entonces se carga
						// la imagen en el archivo destino.
						imagen = Scalr.resize(bufferedImagen, bufferedImagen.getWidth(), bufferedImagen.getHeight());
						ImageIO.write(imagen, "jpg", archivoDestino);
						out = new FileOutputStream(new File(archivoDestino.getName()));

					}

					// Se evalua si despues del escalado la imagen por algudo de
					// los lados quedo con el tamanio menor
					// que el tamanio minimo.
					if (imagen.getWidth() < tamanioMinimo || imagen.getHeight() < tamanioMinimo) {
						// Se indica al usuario que debido a que la imagen tiene
						// una forma rara no se puede redimensionar.
						mensajeError("Por restricciones de diseño la imagen debe tener forma cuadrada, "
								+ "en eeste momento no es posible redimensionar su imagen. "
								+ " Esta situación puede ocurrir si la imagen es muy alta o muy ancha, por favor intente con una diferente.");
						error = true;

					} else {
						ImageIO.write(imagen, "jpg", archivoDestino);
					}

					int read;
					byte[] bytes = new byte[1024];
					while ((read = in.read(bytes)) != -1) {
						out.write(bytes, 0, read);
					}
					in.close();
					out.flush();
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return !error;
	}

	public Date sumarRestarDiasFecha(Date fecha, int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, dias); // numero de días a añadir, o
													// restar en caso de días<0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos
									// días añadidos

	}

	/**
	 * Crear histórico de estado de concepto de contrato de biodiversidad,
	 * requiere el concepto, la persona responsable.
	 * 
	 * @param concepto
	 * @param persona
	 */
	public void crearHistoricoEstadoConcepto(ConceptoContratoBiodiversidad concepto, Persona persona) {
		HistoricoEstadoConceptoBio cambioEstadoConcepto = new HistoricoEstadoConceptoBio();
		cambioEstadoConcepto.setFecha(new Date());
		cambioEstadoConcepto.setConcepto(concepto);
		cambioEstadoConcepto.setEstadoConcepto(concepto.getEstado());
		cambioEstadoConcepto.setRevisor(persona);
		cambioEstadoConcepto.setJustificacion(concepto.getObservaciones());
		this.servicioGeneral.guardarObjeto(cambioEstadoConcepto);

	}

	protected boolean esCadenaVacia(String cadena) {
		return cadena == null || (cadena != null && cadena.trim().length() == 0);
	}

	public String controlTamanoCadena(String cadena, int tamanoAceptado) {
		if (cadena != null) {
			cadena = cadena.trim();
			if (cadena.length() > tamanoAceptado) {
				cadena = (cadena.substring(0, tamanoAceptado - 1));
			}
			return cadena;
		}
		return "";
	}

	public String obtenerDireccion() {
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			return ((HttpServletRequest) request).getRequestURL().toString();
		} else {
			return "";
		}
	}

	/**
	 * Método para quitar los elementos null de un array de String
	 * 
	 * @param array
	 *            de String[] con posibles elementos en null
	 * @return array de String[] sin elementos null
	 */
	public static String[] cleanArrayString(String[] arrayString) {
		List<String> list = new ArrayList<String>(Arrays.asList(arrayString));
		list.removeAll(Collections.singleton(null));
		return list.toArray(new String[list.size()]);
	}

	protected void eliminarManejadoresAval(String idAvalSesion) {
		sesion.setAttribute(idAvalSesion, null);
		sesion.removeAttribute("manejadorSolicitudAval");
		sesion.removeAttribute("manejadorConsultarAval");
		sesion.removeAttribute("manejadorSolicitarAval");
		sesion.removeAttribute("manejadorSolicitarAvalFacultad");
		sesion.removeAttribute("manejadorSolicitarAvalDireccion");
		sesion.removeAttribute("manejadorSolicitudAvalHome");
		sesion.setAttribute("esConsulta", false);
		sesion.setAttribute("esEdicion", false);
	}

	protected List<TipoInvestigador> obtenerTipoVinculacionConvocatoria(Convocatoria convocatoriaActual,
			boolean esConvocatoriaInterna) {

		// Tipo vinculacion
		String tipoMod = "FM";

		Parametro parametroTipoVinculacion = null;

		// tipologías Investigación
		if (convocatoriaActual != null && convocatoriaActual.getTipo() != null) {
			List<Parametro> parametros = servicioGeneral.obtenerObjetos(Parametro.class,
					"select p from Parametro p where p.nombre = '" + Parametro.TIPO_VINCULACION_CONVOCATORIA
							+ "' and p.profesion = '" + convocatoriaActual.getId() + "'");
			if (!esListaVacia(parametros)) {
				parametroTipoVinculacion = parametros.get(0);
				tipoMod = parametroTipoVinculacion.getValor();
			}
		}

		if (parametroTipoVinculacion == null && esConvocatoriaInterna) {
			if (convocatoriaActual.getRestriccion() != null) {
				if (convocatoriaActual.getRestriccion().getId()
						.equals(RestriccionConvocatoria.FICHA_MINIMA_PROYECTOS_2013_2015)) {
					tipoMod = "FHP";
				}
			}
			if (convocatoriaActual.getRestriccion() != null) {
				if (convocatoriaActual.getRestriccion().getId().equals("TIEMPO_VOLVER")
						&& convocatoriaActual.getId().equals(542L)) {
					tipoMod = "CTV";
				}
			}
			if (convocatoriaActual.getRestriccion() != null) {
				if (convocatoriaActual.getRestriccion().getId().equals("EST_ASIGNATURA_PM")) {
					tipoMod = "PMA";
				}
			}

			if (convocatoriaActual.getRestriccion() != null) {
				if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_NAL_PRY_2017_18)) {
					tipoMod = "CNP2017_18";
				}
			}

			if (convocatoriaActual.getRestriccion() != null) {
				if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_NAL_SESQUICENTE)) {
					tipoMod = "CSESQUI";
				}
			}

			if (convocatoriaActual.getRestriccion() != null) {
				if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_INNO_ES_2017)
						|| convocatoriaActual.getRestriccion().getId()
								.equals(RestriccionConvocatoria.CONV_INNO_ES_2017_M2)) {
					tipoMod = "ES7";
				}
			}

			if (convocatoriaActual.getRestriccion() != null) {
				if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_CIEN_AGRA_BOG)) {
					tipoMod = "CCAB17";
				}
			}

			if (convocatoriaActual.getRestriccion() != null) {
				if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_DNIPI_2017)) {
					tipoMod = "CED";
				}
			}

			if (convocatoriaActual.getRestriccion() != null) {
				if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_MED_TRAS_17)) {
					tipoMod = "CFMT17";
				}
			}

			if (convocatoriaActual.getRestriccion() != null) {
				if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ARTES_MOD_A)
						|| convocatoriaActual.getRestriccion().getId()
								.equals(RestriccionConvocatoria.CONV_ARTES_MOD_B)) {
					tipoMod = "CFA17";
				}
			}

			if (convocatoriaActual.getRestriccion() != null) {
				if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ODONTO_2017)
						|| convocatoriaActual.getRestriccion().getId()
								.equals(RestriccionConvocatoria.CONV_ODONTO_2017_M2)) {
					tipoMod = "COD17";
				}
			}

			if (convocatoriaActual.getTipo().getId().equals("CTP")) {
				tipoMod = "CTP";
			}

			if (convocatoriaActual.getRestriccion() != null) {
				if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ALI_BOG_17)) {
					tipoMod = "CAB17";
				}
			}

			if (convocatoriaActual.getRestriccion() != null) {
				if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_SUE_2017)) {
					tipoMod = "SUE17";
				}
			}
			// cundinamarca aqui.
		}

		// Se cargan los tipos de vinculación para cuando se conocen los datos.
		String consultaTipoVinculacion = "select ti from TipoInvestigador ti where ti.tipoModalidad like '%" + tipoMod
				+ "%' ";

		if (convocatoriaActual.isMostrarEstudianteLider()) {
			consultaTipoVinculacion += " or ti.id = 'AL' ";
		}

		if (convocatoriaActual.getTipoInvestigadoresConvocatoria() != null) {
			consultaTipoVinculacion = "select ti from TipoInvestigador ti where ti.id in ("
					+ convocatoriaActual.getTipoInvestigadoresConvocatoria() + ") ";
		}

		consultaTipoVinculacion += " order by ti.nombre desc";

		return servicioGeneral.obtenerObjetos(TipoInvestigador.class, consultaTipoVinculacion);
	}

	public void guardarLogEquipos(Boolean esEquipoNuevo, Boolean esinformacioBasica, LaboratorioDetalleEquipos equipo) {
		Tipos tipoOperacion = null;
		List<Tipos> listaTipos;
		if (esEquipoNuevo) {
			if (esinformacioBasica)
				listaTipos = servicioGeneral.obtenerObjetoXID(Tipos.class,
						Tipos.TIPO_OPERACION_LOG_EQU_CREACION_DATOS_BASICOS.toString());
			else
				listaTipos = servicioGeneral.obtenerObjetoXID(Tipos.class,
						Tipos.TIPO_OPERACION_LOG_EQU_CREACION_HOJA_DE_VIDA.toString());
		} else {
			if (esinformacioBasica)
				listaTipos = servicioGeneral.obtenerObjetoXID(Tipos.class,
						Tipos.TIPO_OPERACION_LOG_EQU_ACTUALIZACION_DATOS_BASICOS.toString());
			else
				listaTipos = servicioGeneral.obtenerObjetoXID(Tipos.class,
						Tipos.TIPO_OPERACION_LOG_EQU_ACTUALIZACION_HOJA_DE_VIDA.toString());
		}

		if (listaTipos != null)
			tipoOperacion = listaTipos.get(0);

		LaboratorioLogEquipos logEquipos = new LaboratorioLogEquipos();
		logEquipos.setEquipo(equipo);
		logEquipos.setTipoOperacion(tipoOperacion);
		logEquipos.setFechaRegistro(new Date());
		logEquipos.setResponsable(personaActual);

		servicioGeneral.guardarObjeto(logEquipos);

	}
	
	public void guardarLogCriticidadEquipos(LaboratorioDetalleEquipos equipo) {
		Tipos tipoOperacion = null;
		List<Tipos> listaTipos;
		listaTipos = servicioGeneral.obtenerObjetoXID(Tipos.class,
						Tipos.TIPO_OPERACION_LOG_EQU_CALCULO_CRITICIDAD.toString());

		if (listaTipos != null)
			tipoOperacion = listaTipos.get(0);

		LaboratorioLogEquipos logEquipos = new LaboratorioLogEquipos();
		logEquipos.setEquipo(equipo);
		logEquipos.setTipoOperacion(tipoOperacion);
		logEquipos.setFechaRegistro(new Date());
		logEquipos.setResponsable(personaActual);

		servicioGeneral.guardarObjeto(logEquipos);

	}

	public void guardarLogEquiposBorrar(LaboratorioDetalleEquipos equipo, Boolean tieneHojaVida) {
		Tipos tipoOperacion = null;
		List<Tipos> listaTipos;

		if (tieneHojaVida)
			listaTipos = servicioGeneral.obtenerObjetoXID(Tipos.class,
					Tipos.TIPO_OPERACION_LOG_EQU_BORRADO_HOJA_DE_VIDA.toString());
		else
			listaTipos = servicioGeneral.obtenerObjetoXID(Tipos.class,
					Tipos.TIPO_OPERACION_LOG_EQU_BORRADO_DATOS_BASICOS.toString());

		if (listaTipos != null)
			tipoOperacion = listaTipos.get(0);

		LaboratorioLogEquipos logEquipos = new LaboratorioLogEquipos();
		logEquipos.setEquipo(equipo);
		logEquipos.setTipoOperacion(tipoOperacion);
		logEquipos.setFechaRegistro(new Date());
		logEquipos.setResponsable(personaActual);

		servicioGeneral.guardarObjeto(logEquipos);
	}

	public void guardarLogActividades(LaboratorioActividadEquipo actividad, Long tipoOperacionParametro) {
		Tipos tipoOperacion = null;
		List<Tipos> listaTipos = null;

		if (tipoOperacionParametro.equals(Tipos.TIPO_OPERACION_LOG_ACT_CREACION))
			listaTipos = servicioGeneral.obtenerObjetoXID(Tipos.class,
					Tipos.TIPO_OPERACION_LOG_ACT_CREACION.toString());
		else if (tipoOperacionParametro.equals(Tipos.TIPO_OPERACION_LOG_ACT_ACTUALIZACION))
			listaTipos = servicioGeneral.obtenerObjetoXID(Tipos.class,
					Tipos.TIPO_OPERACION_LOG_ACT_ACTUALIZACION.toString());
		else if (tipoOperacionParametro.equals(Tipos.TIPO_OPERACION_LOG_ACT_BORRADO))
			listaTipos = servicioGeneral.obtenerObjetoXID(Tipos.class, Tipos.TIPO_OPERACION_LOG_ACT_BORRADO.toString());
		else if (tipoOperacionParametro.equals(Tipos.TIPO_OPERACION_LOG_ACT_CONSULTA))
			listaTipos = servicioGeneral.obtenerObjetoXID(Tipos.class,
					Tipos.TIPO_OPERACION_LOG_ACT_CONSULTA.toString());

		if (listaTipos != null)
			tipoOperacion = listaTipos.get(0);

		LaboratorioLogActividades logActividad = new LaboratorioLogActividades();
		logActividad.setActividad(actividad);
		logActividad.setTipoOperacion(tipoOperacion);
		logActividad.setFechaRegistro(new Date());
		logActividad.setResponsable(personaActual);

		servicioGeneral.guardarObjeto(logActividad);
	}

	/**
	 * Busca una dependencia de acuerdo a su id en una lista dada.
	 *
	 * @param id
	 *            the id
	 * @param dependencias
	 *            the dependencias
	 * @return the dependencia area responsabilidad
	 */
	protected DependenciaAreaResponsabilidad buscarDependenciaAreaResponsabilidad(String id,
			List<DependenciaAreaResponsabilidad> dependencias) {

		int i = 0;
		while (i < dependencias.size()) {
			DependenciaAreaResponsabilidad dependencia = (DependenciaAreaResponsabilidad) dependencias.get(i);
			if (id.equals(dependencia.getDependencia().getId())) {
				return dependencia;
			}
			i = i + 1;
		}
		return null;

	}

	/**
	 * Busca una dependencia de acuerdo a su id en una lista dada.
	 *
	 * @param id
	 *            the id
	 * @param dependencias
	 *            the dependencias
	 * @return the dependencia
	 */
	protected Dependencia buscarDependencia(String id, List<Dependencia> dependencias) {

		Dependencia dependencia = null;
		int i = 0;
		while (i < dependencias.size()) {
			dependencia = (Dependencia) dependencias.get(i);
			if (id.equals(dependencia.getId())) {
				return dependencia;
			}
			i = i + 1;
		}
		return dependencia;

	}

	protected ObjetivoEspecifico buscarObjetivo(String orden, List<ObjetivoEspecifico> objetivos) {

		ObjetivoEspecifico objetivo = null;
		int i = 0;
		while (i < objetivos.size()) {
			objetivo = (ObjetivoEspecifico) objetivos.get(i);
			if (orden.equals(objetivo.getNumeroOrden().toString())) {
				return objetivo;
			}
			i = i + 1;
		}
		return objetivo;

	}

	protected MetaProyecto buscarMeta(String id, List<MetaProyecto> metas) {

		MetaProyecto meta = null;
		int i = 0;
		while (i < metas.size()) {
			meta = (MetaProyecto) metas.get(i);
			if (id.equals(meta.getId().toString())) {
				return meta;
			}
			i = i + 1;
		}
		return meta;

	}

	protected ResultadoProyecto buscarResultado(String id, List<ResultadoProyecto> resultados) {
		ResultadoProyecto resultado = null;
		int i = 0;
		while (i < resultados.size()) {
			resultado = (ResultadoProyecto) resultados.get(i);
			if (id.equals(resultado.getId().toString())) {
				return resultado;
			}
			i = i + 1;
		}
		return resultado;
	}

	/**
	 * Se consulta el valor tipo dominio en la base de datos y se crea el
	 * listado de items.
	 *
	 * @param valorTipoDominio
	 *            the valor tipo dominio
	 * @return the select item[]
	 */
	protected SelectItem[] crearListaItemDominioDetalle(String valorTipoDominio) {
		List<DominioDetalle> lista = servicioGeneral.obtenerDominioDetalle(valorTipoDominio);
		return crearListaItems(lista);
	}
	
	protected SelectItem[] crearListaItemDominioDetalle(String valorTipoDominio, boolean estado) {
		List<DominioDetalle> lista = servicioGeneral.obtenerDominioDetalle(valorTipoDominio, estado);
		return crearListaItems(lista);
	}
	
	protected SelectItem[] crearListaItemDominioDetalle(String valorDominio, String domDetTipo) {
		List<DominioDetalle> lista = servicioGeneral.obtenerDominioDetalleListaUnico(valorDominio, domDetTipo);
		return crearListaItems(lista);
	}

	/**
	 * Se obtiene una lista de dominio detalle y se crea el tipo de items.
	 *
	 * @param lista
	 *            the lista
	 * @return the select item[]
	 */
	protected SelectItem[] crearListaItems(List<DominioDetalle> lista) {
		SelectItem[] categoriaItems = null;
		if (!esListaVacia(lista)) {
			categoriaItems = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				DominioDetalle dd = (DominioDetalle) lista.get(i);
				categoriaItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			}
		}
		return categoriaItems;
	}

	protected DominioDetalle obtenerDominioDetalleLista(String tipo, List<DominioDetalle> listaDominioDetalle) {
		if (listaDominioDetalle != null) {
			Iterator<DominioDetalle> i = listaDominioDetalle.iterator();
			while (i.hasNext()) {
				DominioDetalle dominioDetalle = i.next();
				if (dominioDetalle.getIdentificador().getTipo().equals(tipo)) {
					return dominioDetalle;
				}
			}
		}
		return null;
	}

	/**
	 * Obtener movilidad.
	 *
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the type
	 * @param codigoMovilidad
	 *            the codigo movilidad
	 * @return the object
	 */
	protected <T> Object obtenerMovilidad(Class<T> type, String codigoMovilidad) {
		List<T> lista = servicioGeneral.obtenerObjetos(type,
				"from " + type.getSimpleName() + " m where m.id = '" + codigoMovilidad + "'");
		if (!esListaVacia(lista)) {
			return lista.get(0);
		}
		return null;
	}

	public boolean validarDependenciaRolAccesoProyectos(final Dependencia depInvPrincipalProyecto,
			final Dependencia depPersona) {
		if (depInvPrincipalProyecto != null) {
			boolean esPersonaRolNacional = false;
			boolean esPersonaRolSede = false;
			boolean esPersonaRolFacultad = false;

			if (validarSiNacional(depPersona)) {
				esPersonaRolNacional = true;
				esPersonaRolSede = false;
				esPersonaRolFacultad = false;
			}

			if (validarSiSede(depPersona)) {
				esPersonaRolNacional = false;
				esPersonaRolSede = true;
				esPersonaRolFacultad = false;
			}

			if (validarSiFacultad(depPersona)) {
				esPersonaRolNacional = false;
				esPersonaRolSede = false;
				esPersonaRolFacultad = true;
			}

			if (esPersonaRolNacional) {
				return true;
			} else {
				if (esPersonaRolSede) {
					if (depInvPrincipalProyecto.getSede().getId().equals(depPersona.getSede().getId())) {
						return true;
					} else {
						return false;
					}
				} else {
					if (esPersonaRolFacultad) {
						if (depInvPrincipalProyecto.getFacultad().getId().equals(depPersona.getFacultad().getId())) {
							return true;
						} else {
							return false;
						}
					}
				}
			}
		} else {
			mensajeError("Por favor verificar la dependencia del investigador principal del proyecto");
			return false;
		}

		return false;
	}

	public boolean validarSiNacional(final Dependencia dep) {
		boolean ret = false;

		if ("1".equals(dep.getSede().getId().toString())) {
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}

	public boolean validarSiSede(final Dependencia dep) {
		boolean ret = false;
		
		if(dep.getSede().getId().equals(0L)) {
			return true;
		}

		int opcion;

		try {
			opcion = Integer.parseInt(dep.getFacultad().getId());
		} catch (Exception e) {
			opcion = 10;
		}

		switch (opcion) {
		case 1:
			ret = false;
			break;

		case 2:
			ret = true;
			break;

		case 3:
			ret = true;
			break;

		case 4:
			ret = true;
			break;

		case 5:
			ret = true;
			break;

		case 6:
			ret = true;
			break;

		case 7:
			ret = true;
			break;

		case 8:
			ret = true;
			break;

		case 9:
			ret = true;
			break;

		default:
			ret = false;
			break;
		}

		return ret;
	}

	public boolean validarSiFacultad(final Dependencia dep) {
		boolean ret = false;
		int opcion;

		try {
			opcion = Integer.parseInt(dep.getFacultad().getId());
		} catch (Exception e) {
			opcion = 10;
		}

		switch (opcion) {
		case 1:
			ret = false;
			break;

		case 2:
			ret = false;
			break;

		case 3:
			ret = false;
			break;

		case 4:
			ret = false;
			break;

		case 5:
			ret = false;
			break;

		case 6:
			ret = false;
			break;

		case 7:
			ret = false;
			break;

		case 8:
			ret = false;
			break;

		case 9:
			ret = false;
			break;

		default:
			ret = true;
			break;
		}

		return ret;
	}
	
	public String getLinkLineas() {
		return "http://www.hermes.unal.edu.co/pages/descargas/AreasCiencia.pdf";
	}
	
	public String getLinkProductos() {
		return "http://www.hermes.unal.edu.co/pages/descargas/productos-investigacion.pdf";
	}

	public String getLinkObjetivosDesarrolloSostenible() {
		return "http://www.undp.org/content/undp/es/home/sustainable-development-goals.html";
	}

	public String getLinkObjetivos() {
		return "http://www.hermes.unal.edu.co/pages/descargas/ObjetivoSocioeconomico.pdf";
	}

	public String getLinkClasificadoresISBN() {
		String viewId = "/pages/descargas/Clasificadores_ISBN.pdf";
		viewId = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + viewId;
		return viewId;
	}
	
	public String getLinkClasificacionDEWEY() {
		String viewId = "/pages/descargas/Clasificacion_DEWEY.pdf";
        viewId = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + viewId;
		return viewId;
	}

	// Requerimiento #2226 y #2370 - Angela Devia
	public String consultarPaginaConvocatoriaExterna() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		@SuppressWarnings("rawtypes")
		Map map = extContext.getRequestParameterMap();
		String viewId = "/pages/Consultas/ConvocatoriaExterna.xhtml";
		try {
			Object o = (Object) map.get("aviNumeroConvocatoria");
			numConvExterna = (String) o;
			viewId = extContext.getRequestContextPath() + viewId + '?' + "idConvocatoria" + "=" + numConvExterna
					+ "&tipo=" + "E";
			String urlLink = context.getExternalContext().encodeActionURL(viewId);
			extContext.redirect(urlLink); 
		} catch (IOException e) {
			extContext.log(getClass().getName() + ".invokeRedirect", e);
		}
		numConvExterna = null;
		return null;
	}
	// Fin Requerimiento #2226 y #2370 - Angela Devia
	
	public Boolean cadenaEsValorNumerico(String cadena)
	{
		if(cadena == null) {
			return false;
		}
		
		Boolean esNumerico = false;
		
		Pattern numerico = Pattern.compile("^[0-9]+$");
		Matcher m = numerico.matcher(cadena);
		if (m.find()) 
			esNumerico = true;
		else 
			esNumerico = false;
		
		return esNumerico;
	}

	public boolean validarDocumentoParticipante(final String tipoDocumento, final String numeroDocumento) {
		boolean ret = true;
		if (tipoDocumento.equals(TipoDocumento.CEDULA)
				|| tipoDocumento.equals(TipoDocumento.DOCUMENTO_IDENTIDAD_EXTRANJERA)
				|| tipoDocumento.equals(TipoDocumento.CEDULA_EXTRANJERIA)
				|| tipoDocumento.equals(TipoDocumento.TARJETA_IDENTIDAD)
				|| tipoDocumento.equals(TipoDocumento.CERTIFICADO_CABILDO)) {
			if (cadenaEsValorNumerico(numeroDocumento)) {
				if (tipoDocumento.equals(TipoDocumento.CEDULA)) {
					if (numeroDocumento.length() > 10) {
						ret = false;
					} else {
						ret = true;
					}
				} else {
					ret = true;
				}
			} else {
				ret = false;
			}
		} else {
			if (tipoDocumento.equals(TipoDocumento.PASAPORTE)) {
				Pattern alfanumerico = Pattern.compile("^[a-zA-Z0-9]+$");
				Matcher m = alfanumerico.matcher(numeroDocumento);
				if (m.find()) {
					ret = true;
				} else {
					ret = false;
				}
			}
		}
		return ret;
	}

	public boolean validarTextoSinNumeros(final String texto) {
		boolean ret = true;
		Pattern textoSinNumeros = Pattern.compile("^[A-Za-zäÄëËïÏöÖüÜáéíóúáéíóúÁÉÍÓÚÂÊÎÔÛâêîôûàèìòùÀÈÌÒÙñÑ]+$");
		Matcher m = textoSinNumeros.matcher(texto);
		if (m.find()) {
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}

	public boolean validarEmail(final String texto) {
		boolean ret = true;
		Pattern textoSinNumeros = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$");
		Matcher m = textoSinNumeros.matcher(texto);
		if (m.find()) {
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}
	
	public Boolean esNulo(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			String str = (String) obj;
			return str.trim().length() == 0;
		}
		if (obj instanceof Number) {
			Number n = (Number) obj;
			return n.doubleValue() == 0.0;
		}
		return false;
	}

	public List<Pais> cargarPaises(boolean conColombia) {
		String hql;
		if (conColombia) {
			hql = "select #id e.id, #nombre e.nombre from Pais e where e.sigla is not null order by e.nombre asc";
		} else {
			hql = "select #id e.id, #nombre e.nombre from Pais e where e.id not in ('CO') and e.sigla is not null order by e.nombre asc";
		}
		List<Pais> listaPaises = servicioGeneral.obtenerObjetosLimitado(Pais.class, hql);
		return listaPaises;
	}

	public SelectItem[] getGeneroItem() {
		return generoItem;
	}

	public void setGeneroItem(SelectItem[] generoItem) {
		this.generoItem = generoItem;
	}
	
	/**
	 * Metodo para cargar todas las fuentes externas.
	 */
	public void cargarFuentesFinanciacionExternas() {
		String hql = "select ff from FuenteFinanciacion ff where ff.estado in ('V') and ff.internaExterna = 'E' and ff.descripcion not like '%CODIGO%' and ff.observaciones = 'ENT_REG_PRY' and ff.quipu = 'S' order by ff.descripcion)";
		List<FuenteFinanciacion> lista = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class, hql);

		fuentesExternasItem = new SelectItem[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			FuenteFinanciacion d = (FuenteFinanciacion) lista.get(i);
			String nombre = d.getDescripcion().toUpperCase();
			fuentesExternasItem[i] = new SelectItem(d.getId(), nombre);
		}
	}

	public SelectItem[] getFuentesExternasItem() {
		return fuentesExternasItem;
	}

	public void setFuentesExternasItem(SelectItem[] fuentesExternasItem) {
		this.fuentesExternasItem = fuentesExternasItem;
	}
	
	
	public boolean validarVinculacionDocente(InvestigadorInterno inv) {
		return false;
	}
	
	public boolean validarVinculacionAdministrativo(InvestigadorInterno inv) {
		return false;
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

	public String getPaisFuente() {
		return paisFuente;
	}

	public void setPaisFuente(String paisFuente) {
		this.paisFuente = paisFuente;
	}

	public String getCaracterFuente() {
		return caracterFuente;
	}

	public void setCaracterFuente(String caracterFuente) {
		this.caracterFuente = caracterFuente;
	}

	public String getTipoFuente() {
		return tipoFuente;
	}

	public void setTipoFuente(String tipoFuente) {
		this.tipoFuente = tipoFuente;
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

	public void solicitudIngresoFuente() {
		
		boolean valida = true;

		if (!esCadenaVacia(nombreFuente) && !esCadenaVacia(nitFuente) && !esCadenaVacia(direccionFuente)
				&& !esCadenaVacia(naturalezaFuente) && !naturalezaFuente.equals("0") && !esCadenaVacia(caracterFuente)  && !caracterFuente.equals("0") && !esCadenaVacia(tipoFuente) && !tipoFuente.equals("0")) {
			
			if ((naturalezaFuente.equals("NAT_INTERN") && esCadenaVacia(paisFuente))) {
				valida = false;
			}
			
			if(valida) {

			// Guardar en la bd
			FuenteFinanciacion fte = new FuenteFinanciacion();
			String descripcionFuente = ReemplazaAcentos.quitarTildes(nombreFuente != null ? nombreFuente : "")
					.toUpperCase();

			fte.setNaturaleza(naturalezaFuente);
			fte.setDescripcion(descripcionFuente);
			fte.setInternaExterna("OE");
			fte.setNit(nitFuente != null ? nitFuente : "");
			fte.setDireccion(direccionFuente != null ? direccionFuente : "");
			fte.setTelefono(telefonoFuente != null ? telefonoFuente : "");
			fte.setObservaciones("ENT_REG_PRY");
			fte.setQuipu("S");
			fte.setCaracter(caracterFuente);
			fte.setTipoFuente(tipoFuente);
			fte.setVisibleAval(true);
			if (naturalezaFuente.equals("NAT_INTERN")) {
				Pais paisSel = new Pais();
				paisSel.setId(paisFuente);
				fte.setPais(paisSel);
			} else {
				Pais paisSel = new Pais();
				paisSel.setId("CO");
				fte.setPais(paisSel);
			}

			try {
				servicioGeneral.guardarObjeto(fte);

				// Guardar solicitud usuario-fuente
				SolicitudFuente solicitud = new SolicitudFuente();
				solicitud.setNombreSolicitante(personaActual.getNombre1());
				solicitud.setApellidoSolicitante(personaActual.getApellido1());
				solicitud.setEstado("Ingresado");
				solicitud.setIdSolicitante(personaActual.getId().getDocumento());
				solicitud.setTdoIdSolicitante(personaActual.getId().getTipoDocumento());
				solicitud.setFechaRegistro(new Date());
				solicitud.setIdFuenteFinanciacion(Long.parseLong(fte.getId()));

				servicioGeneral.guardarObjeto(solicitud);

				// Correo a Hermes
				Correo correo = new Correo();
				CorreoPlantilla cp = cargarPlantilla(200);
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
				correo.adicionarDireccion(Correo.CORREO_HERMES_COMUNICACIONES);
				servicioCorreo.enviarCorreo(correo);
				mensajeInfo(CB_crearFuente, "La información de creación de la fuente ha sido enviada correctamente.");
				mensajeInfo(CB_crearFuente2, "La información de creación de la fuente ha sido enviada correctamente.");
				} catch (Exception e) {
				mensajeInfo(CB_crearFuente,
						"La información de la fuente NO ha sido enviada, revise la información ingresada. Debe diligenciar todos los campos.");
				mensajeInfo(CB_crearFuente2,
						"La información de la fuente NO ha sido enviada, revise la información ingresada. Debe diligenciar todos los campos.");
			}
			}else {
				mensajeInfo(CB_crearFuente,
						"La información de la fuente NO ha sido enviada, revise la información ingresada. Debe diligenciar todos los campos.");
				mensajeInfo(CB_crearFuente2,
						"La información de la fuente NO ha sido enviada, revise la información ingresada. Debe diligenciar todos los campos.");
		
			}
		} else {
			mensajeInfo(CB_crearFuente,
					"La información de la fuente NO ha sido enviada, revise la información ingresada. Debe diligenciar todos los campos.");
			mensajeInfo(CB_crearFuente2,
					"La información de la fuente NO ha sido enviada, revise la información ingresada. Debe diligenciar todos los campos.");
		}
	}
	
	public void cambiarNaturalezaFuente() {
		if (naturalezaFuente.equals("NAT_INTERN")) {
			List<Pais> listaPaises;
			listaPaises = cargarPaises(false);
			listaPaisesItem = new SelectItem[listaPaises.size()];
			for (int i = 0; i < listaPaises.size(); i++) {
				Pais p = (Pais) listaPaises.get(i);
				listaPaisesItem[i] = new SelectItem(p.getId(), p.getNombre());
			}
		}
	}

	public SelectItem[] getListaPaisesItem() {
		return listaPaisesItem;
	}

	public void setListaPaisesItem(SelectItem[] listaPaisesItem) {
		this.listaPaisesItem = listaPaisesItem;
	}
	
	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public UIComponent getCB_crearFuente() {
		return CB_crearFuente;
	}

	public void setCB_crearFuente(UIComponent cB_crearFuente) {
		CB_crearFuente = cB_crearFuente;
	}

	public List<ProductoTipo> getProductosConfiguradosConvocatoria() {
		return productosConfiguradosConvocatoria;
	}

	public void setProductosConfiguradosConvocatoria(List<ProductoTipo> productosConfiguradosConvocatoria) {
		this.productosConfiguradosConvocatoria = productosConfiguradosConvocatoria;
	}
	
	public SelectItem[] cargarListaTipoProductosUnificados() {

		List<ProductoTipo> listaProductosNivel1 = new ArrayList<ProductoTipo>();

			String hql = "select p from ProductoTipo p where p.estado = 'A' and p.descripcion like 'LISTA_PRODUCTO_UNIFICADA_HERMES' and p.nivel = '0' order by p.nombre";
			
			listaProductosNivel1 = servicioGeneral.obtenerObjetos(ProductoTipo.class, hql);

		if (!esListaVacia(listaProductosNivel1)) {
			for (Iterator<ProductoTipo> it = listaProductosNivel1.iterator(); it.hasNext();) {
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

		return crearSelectItem(listaProductosNivel1);
	}
	
	private SelectItem[] crearSelectItem(List<ProductoTipo> listaProductos) {
		SelectItem[] elementos;
		elementos = new SelectItem[listaProductos.size()];
		for (int i = 0; i < listaProductos.size(); i++) {
			ProductoTipo pro = listaProductos.get(i);
			String nombre = pro.getNombre();

			elementos[i] = new SelectItem(pro.getId(), nombre);
		}
		return elementos;
	}
	
	public void guardarHistoricoInformeSemilleros(SemilleroInforme inf, String comentarios) {
		SemilleroHistoricoInforme historico = new SemilleroHistoricoInforme();
		try {
			
			historico.setFechaAjuste(getToday());
			historico.setEstado(inf.getRespuesta());
			historico.setInforme(inf);
			historico.setComentarios(comentarios);
			historico.setResponsable(personaActual);
			historico.setFechaCompromiso(inf.getFechaCompromiso());
			servicioGeneral.guardarObjeto(historico);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ConvocatoriaExterna obtenerConvocatoriaExterna(Long idConvocatoria) {
		ConvocatoriaExterna conv = new ConvocatoriaExterna();
		try {
			conv = (ConvocatoriaExterna) servicioGeneral.obtenerObjeto(new ConvocatoriaExterna(), idConvocatoria);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return conv;
	}

	public UIComponent getCB_crearFuente2() {
		return CB_crearFuente2;
	}

	public void setCB_crearFuente2(UIComponent cB_crearFuente2) {
		CB_crearFuente2 = cB_crearFuente2;
	}
	
	/**
	 * Calcular valor funcionario.
	 * 
	 * @param numeroHoras the numero horas
	 * @param numeroMeses the numero meses
	 * @param valorHora   the valor hora
	 * @return the long
	 */
	public Long calcularValorFuncionario(double numeroHoras, Integer numeroMeses, Long valorHora) {

		Long valorTotal = 0L;
		Long duracionAnnio;

		if (numeroMeses > 12) {
			duracionAnnio = numeroMeses / 12L;
			double res = numeroMeses % 12;
			Long resid = (long) (res);

			for (int i = 0; i < duracionAnnio; i++) {
				valorTotal = valorTotal + (long)(numeroHoras * valorHora * 4 * 12);
				valorHora += (long) (valorHora * 0.05);
			}

			if (resid > 0) {
				valorTotal = valorTotal + (long) (numeroHoras * valorHora * 4 * resid);
			}

		} else {
			valorTotal = (long) (numeroHoras * valorHora * 4 * numeroMeses);
		}
		return valorTotal;
	}
	
	public Long calcularValorFuncionarioSemanas(double numeroHoras, Integer semanas, Long valorHora) {

		Long valorTotal = 0L;
		Long duracionAnnio;

		if (semanas > 52) {
			duracionAnnio = semanas / 52L;
			double res = semanas % 52;
			Long resid = (long) (res);

			for (int i = 0; i < duracionAnnio; i++) {
				valorTotal = valorTotal + (long)(numeroHoras * valorHora * 52);
				valorHora += (long) (valorHora * 0.05);
			}

			if (resid > 0) {
				valorTotal = valorTotal + (long) (numeroHoras * valorHora * resid);
			}

		} else {
			valorTotal = (long) (numeroHoras * valorHora * semanas);
		}
		return valorTotal;
	}
	
	public long calcularMesesEntreFechas(Date fechaFinalizacionActual, Date fechaFinalizacionPropuesta) {
		long meses = 0;
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaFinalizacionActual);
        long startTimeInMillis = calendar.getTimeInMillis();
        
        calendar.setTime(fechaFinalizacionPropuesta);
        long endTimeInMillis = calendar.getTimeInMillis();
        
        long diffInMillis = endTimeInMillis - startTimeInMillis;
        meses = TimeUnit.MILLISECONDS.toDays(diffInMillis) / 30;
        if(meses<0) {
        	return -meses;
        }
        return meses;
	}
	
	
	public ArchivoGestionColeccion insertarArchivoGestionColeccion(long gestionColId, UploadedFile archivo, String tipo) {
		try {
			if (archivo.getContents() != null) {

				int i = archivo.getFileName().lastIndexOf("\\");

				ArchivoGestionColeccion archivoColeccion = new ArchivoGestionColeccion();
				archivoColeccion.setNombre(archivo.getFileName().substring(i + 1));
				archivoColeccion.setFecha(new Date());
				archivoColeccion.setTipoUsuario(tipo);
				archivoColeccion.setResponsable(personaActual);
				ColeccionGestion co = new ColeccionGestion();
				co.setId(gestionColId);
				archivoColeccion.setGestion(co);

				servicioGeneral.guardarObjeto(archivoColeccion);
				if (archivoColeccion.getId() != null) {
					cargarArchivoDisco(archivo, "HER_ARCHIVO_GESTION_COL", archivoColeccion.getId().toString());
				}
				return archivoColeccion;
			}
		} catch (Exception x) {
			x.printStackTrace();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
					x.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return null;
	}

	public void descargarArchivoGestionColeccion(ArchivoGestionColeccion archivoColeccion) {
		if (archivoColeccion.getId() > 0) {
			String ext = obtenerExtensionArchivo(archivoColeccion.getNombre());
			descargarArchivoGenerico("HER_ARCHIVO_GESTION_COL", archivoColeccion.getId().toString(),
					archivoColeccion.getId() + ext);
		}
	}
	
	protected DominioDetalle cargarEstadoGestion(String estado) {
		List<DominioDetalle> lista;
		String consulta = "select dd from Dominio d, DominioDetalle dd where "
				+ "d.id = dd.identificador.id and d.tipo like 'ESTADO_GESTION_SOLICITUD' "
				+ " and dd.estado in ('A') and dd.identificador.tipo = '" + estado + "' order by dd.descripcion";

		lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		if (lista != null && lista.size() > 0) {
			return lista.get(0);
		}

		return null;

	}
	
	protected DominioDetalle cargarEstadoGestionAutoridad(String estado, String tipoTramiteAudotoridad) {
		List<DominioDetalle> lista;
		String consulta = "select dd from Dominio d, DominioDetalle dd where "
				+ "d.id = dd.identificador.id and d.tipo like '"+tipoTramiteAudotoridad+"' "
				+ " and dd.estado in ('A') and dd.identificador.tipo = '" + estado + "' order by dd.descripcion";

		lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		if (lista != null && lista.size() > 0) {
			return lista.get(0);
		}

		return null;

	}
	
	protected void enviarCorreoCoordinadorBiodiversidad(int plantilla, Proyecto pry) {
		Correo correo = new Correo();
		CorreoPlantilla cp = cargarPlantilla(plantilla);
		correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
		correo.setAsunto(cp.getAsunto());
		correo.setCuerpo(cp.getCuerpo().replaceAll("<<TITULO_PROYECTO>>", pry.getNombre())
				.replaceAll("<<ID>>", pry.getId().toString())
				.replaceAll("<<INVESTIGADOR>>", pry.getResponsable().getNombreCompletoMinusculas())
				.replaceAll("<<FECHA>>", getToday().toString()));
		correo.adicionarDireccion(pry.getResponsable().getEmail());
		correo.adicionarDireccion(Correo.CORREO_BIODIVERSIDAD_UNAL);
		servicioCorreo.enviarCorreo(correo);

	}
	
	protected boolean tieneRolVigente(Persona persona, String rolId) {
	    for (PersonaRol pr : persona.getPersonaRoles()) {
	        if (rolId.equals(pr.getNombre()) &&
	            (pr.getFechaFinRol() == null || !pr.getFechaFinRol().before(new Date()))) {
	            return true;
	        }
	    }
	    return false;
	}
}
