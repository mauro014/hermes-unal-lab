package co.edu.unal.hermes.vista.laboratorios;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.MeterGaugeChartModel;

import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.modelo.Bien;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Edificio;
import co.edu.unal.hermes.modelo.Empresa;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioActividadEquipo;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioActividadMantenimiento;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetallePersona;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioEquipoActualizacionSoftware;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioEquipoConsumibleRepuesto;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioEquipoMetrologia;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioEquipoReporteDanio;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * @author dgbenitezc
 */
public class ManejadorHojaDeVidaEquipos extends ManejadorBase {

	private static final long serialVersionUID = 4916938143214244798L;
	private static final long SMMLV = 1423500L;
	private static final long VALOR_REFERENCIA_EQUIPO_ROBUSTO = 240L;

	private Boolean esLaboratoriosSede = false;
	private Boolean esLaboratoriosNacional = false;
	private Boolean esConsultaLaboratorios = false;
	private Boolean esCoordinadorLaboratorio = false;
	private Sede sedePersona;

	private String placaBuscar;
	private List<LaboratorioDetalleEquipos> listaEquiposEncontrados;

	private Boolean mostrarHV = false;
	private LaboratorioDetalleEquipos equipo;
	private LaboratorioDetalleEquipos equipoV = new LaboratorioDetalleEquipos();
	private Bien bien;
	private SelectItem[] mecanismoAdquisicionSelectItem;
	private SelectItem[] mantenimientoSelectItem;
	private SelectItem[] calibracionSelectItem;
	private SelectItem[] verificacionSelectItem;
	private SelectItem[] tipoDocumentoSelectItem;
	private String documentoPersona;
	private String tipoDocumentoId;
	private String personaNombre;
	private String personaCargo;
	private List<LaboratorioDetallePersona> listaPersonasEntrenamiento;
	private LaboratorioDetallePersona detallePersonaSeleccionado;
	private Boolean entrenamientoBrindado = false;
	private SelectItem[] entrenamientoBrindadoSelectItem;
	private SelectItem[] instrumentoMedicionSelectItem;

	private Boolean esEquipoSinLaboratorio = false;
	private SelectItem[] sedeSelectItem;
	private SelectItem[] facultadSelectItem;
	private SelectItem[] departamentoSelectItem;
	private SelectItem[] edificioSelectItem;

	private LinkedList<ArchivoLaboratorio> listaArchivos;
	private LinkedList<ArchivoLaboratorio> listaArchivosPlanMantto;
	private ArchivoLaboratorio archivoLaboratorioSeleccionado;
	
	private ArchivoLaboratorio archivoLaboratorioActividadSeleccionado;
	private ArchivoLaboratorio archivoLaboratorioReporteDanioSeleccionado;
	private ArchivoLaboratorio archivoLaboratorioActividadSeleccionadoCalib;
//	private Tipos tipoArchivoActividadSeleccionado;
	private SelectItem[] tipoArchivoActividadSelectItem;
	
	private ArrayList<ArchivoLaboratorio> listaArchivosActividades;
	private ArrayList<ArchivoLaboratorio> listaArchivosEliminadosActividades;
	private ArrayList<ArchivoLaboratorio> listaArchivosActividadVista;
	
	private ArrayList<ArchivoLaboratorio> listaArchivosReporteDanio;
	private ArrayList<ArchivoLaboratorio> listaArchivosEliminadosReporteDanio;
	private ArrayList<ArchivoLaboratorio> listaArchivosReporteDanioVista;
	
	private ArrayList<ArchivoLaboratorio> listaArchivosActividadesCalib;
	private ArrayList<ArchivoLaboratorio> listaArchivosEliminadosActividadesCalib;
	private ArrayList<ArchivoLaboratorio> listaArchivosActividadVistaCalib;

	private StreamedContent image;
	private List<ArchivoLaboratorio> listaFotosEquipo;
	private SelectItem[] tipoArchivoSelectItem;
	private SelectItem[] tipoArchivoSelectItemActividades;
	private SelectItem[] tipoArchivoSelectItemActividadesCalib;
	
	private Tipos tipoArchivoSeleccionado;
	private Tipos tipoArchivoSeleccionadoActividad;
	private Tipos tipoArchivoSeleccionadoActividadCalib;

	private ScheduleModel cronograma;
	private SelectItem[] frecManttoPrevSelectItem;
	// private List<LaboratorioActividadMantenimiento> listaActManttoPrev;

	private LaboratorioActividadMantenimiento actividadNueva;
	// private List<LaboratorioActividadMantenimiento>
	// listaActManttoPrevEliminadas;
	private LaboratorioActividadMantenimiento actividadSeleccionada;
	private Boolean cambiosEnActividades;
	private List<LaboratorioActividadEquipo> listaActividadesEquipo;
	private List<LaboratorioActividadEquipo> listaActividadesEquipoFiltrados;
	private List<LaboratorioActividadEquipo> listaActividadesMantto;
	private List<LaboratorioActividadEquipo> listaActividadesManttoFiltrados;
	private List<LaboratorioActividadEquipo> listaActividadesEquipoEliminar;
	private List<LaboratorioActividadEquipo> listaActividadesManttoEliminar;
	
	private List<LaboratorioEquipoConsumibleRepuesto> listaConsumibles;
	private List<LaboratorioEquipoConsumibleRepuesto> listaConsumiblesEliminar;
	private List<LaboratorioEquipoReporteDanio> listaReportesDanio;
	private List<LaboratorioEquipoReporteDanio> listaReportesDanioEliminar;
	private List<LaboratorioEquipoActualizacionSoftware> listaActualizcionesSoftware;
	private List<LaboratorioEquipoActualizacionSoftware> listaActualizcionesSoftwareEliminar;

	private LaboratorioActividadEquipo nuevaActividadEquipo;
	private LaboratorioActividadEquipo editaActividadEquipoCalib;
	private LaboratorioActividadEquipo consultaActividadEquipoCalib;
	
	private LaboratorioActividadEquipo nuevaActividadMantto;
	
	private LaboratorioEquipoConsumibleRepuesto nuevoConsumibleRepuesto;
	private LaboratorioEquipoReporteDanio nuevoReporteDanio;
	private LaboratorioEquipoActualizacionSoftware nuevaActualizacionSoftware;
	
	private String tipoAccionConsumible = "";
	private String tipoAccionReporteDanio = "";
	private String tipoAccionActSoftware = "";
	
	private String tipoAccionActCalib = "";

	private SelectItem[] estadoNuevaActividadSelectItem;
	private SelectItem[] estadoNuevaActividadManttoSelectItem;

	private SelectItem[] tipoNuevaActividadSelectItem;
	private SelectItem[] tipoNuevaActividadManttoSelectItem;
	private SelectItem[] tipoNuevaActividadManttoModSelectItem;

	private Boolean cambiosEnActividadEquipo;
	private SelectItem[] tipoActManttoPrevSelectItem;
	private String nuevaActividadEquipoNombreActividad;
	private LaboratorioActividadEquipo actividadSeleccionadaSchedule;
	private List<LaboratorioEquipoMetrologia> listaMagnitudes;
	private LaboratorioEquipoMetrologia nuevaEspecificacionMetrologica;
	private LaboratorioEquipoMetrologia magnitudSeleccionada;
	private List<LaboratorioEquipoMetrologia> listaMagnitudesEliminadas;
	private Boolean cambiosEspecificacionesMetrologicas;
	private Boolean soloLectura;
	private String where;
	private String tituloTablaBusqueda;
	private Boolean mostrarDiferencias;
	private String diferencias;
	private Boolean equipoExisteEnBDInv;

	private LaboratorioActividadEquipo actividadSeleccionadaEditar;
	private LaboratorioEquipoConsumibleRepuesto comsumibleSeleccionadoEditar;
	private LaboratorioEquipoReporteDanio reporteDanioSeleccionadoEditar;
	private LaboratorioEquipoActualizacionSoftware actualizacionSoftwareSeleccionadaEditar;
	
	private SelectItem[] estadoActividadEditarSelectItem;
	private String estadoActividadEditar;
	private String observacionesEA;
	private Date fechaEA;
	private Date fechaEAProgramada;
	private Long costoEA;
	private String responsableEA;
	private Tipos modalidadEA;
	private Tipos frecuenciaEA;
	private Boolean programarProxActividadEA;
	
	private Tipos frecuenciaEACalib;
	private Tipos modalidadEACalib;
	private String estadoEACalib;
	private Date fechaProgramadaEACalib;
	private Date fechaEjecutadaEACalib;
	private String responsableEACalib;
	private Long costoEACalib;
	private String observacionesEACalib;
	
	private Tipos tipoArchivoSelActMantto;
	private Tipos tipoArchivoSelActCalib;
	
	private String empresaEACalib;
	private Boolean empresaAcreditadaEACalib;
	private String codigoInformeEACalib;
	private String intervaloMedicionEACalib;
	private Boolean equipoAdecuadoUsoEACalib;
	private String equipoAdecuadoUsoNoDescEACalib;
	private Boolean programarProxActividadEACalib;
	

	private Date fechaActual = new Date();
	private String mensajeErrorEditarActividad;
	private Boolean poseeProcesoCompra;
	private SelectItem[] buscarSedeSelectItem;
	private String sedeSeleccionada;
	private SelectItem[] buscarFacultadSelectItem;
	private String facultadSeleccionada;
	private String departamentoSeleccionado;
	private String panelesActivos;

	private Boolean fromEquipos;

	private SelectItem[] magnitudSelectItem;
	private String idLabs;
	private Boolean tieneHVAlCargarFormulario;
	private List<String> listaInfoFaltante = new ArrayList<String>();
	protected SelectItem[] mantenimientoSI;
	protected SelectItem[] calibracionSI;
	private SelectItem[] requiereMantenimientoSelectItem;
	
	protected SelectItem[] tipoArchivoActividadMantto;
	protected SelectItem[] tipoArchivoActividadCalib;
	
	protected SelectItem[] tiposActSoftwareSelectItem;
	
	private Integer tabindex; 
	
	//METROLOGIA
	private SelectItem[] calibradoSelectItem;
	private SelectItem[] dondeCalibradoSelectItem;
	private SelectItem[] incertidumbreCalibradoSelectitem;
	private SelectItem[] tipoDocumentoItem;

	private String nombreArchivo;

	public ManejadorHojaDeVidaEquipos() {
		
		tabindex = 0;
		nombreArchivo = "Reporte_Medidas_Quimicas_";

		// viene desde la página de equipos?
		fromEquipos = (Boolean) sesion.getAttribute("fromEquipos");
		System.out.println("fromEquipos: " + fromEquipos);
		if (fromEquipos == null) {
			fromEquipos = false;
		}
		limpiarSesion();

		esLaboratoriosSede = (Boolean) sesion.getAttribute("esLaboratoriosSede");
		esLaboratoriosNacional = (Boolean) sesion.getAttribute("esLaboratorios");
		esConsultaLaboratorios = (Boolean) sesion.getAttribute("esConsultaLaboratorios");
		esCoordinadorLaboratorio = (Boolean) sesion.getAttribute("esCoordinadorLaboratorio");

		Boolean esLaboratoriosFacultad = (Boolean) sesion.getAttribute("esLaboratoriosFacultad");
		Dependencia dlf = (Dependencia) sesion.getAttribute("depLabsFacultad");
		if (dlf == null) {
			esLaboratoriosFacultad = false;
		}

		// laboratorios departamento
		Boolean esLaboratoriosDepartamento = (Boolean) sesion.getAttribute("esLaboratoriosDepto");
		Dependencia dld = (Dependencia) sesion.getAttribute("depLabsFacultad");
		if (dld == null) {
			esLaboratoriosDepartamento = false;
		}

		soloLectura = !(esLaboratoriosNacional || esLaboratoriosSede|| esCoordinadorLaboratorio || esLaboratoriosFacultad);


		personaActual = (Persona) sesion.getAttribute("persona");
		if (esLaboratoriosSede && personaActual instanceof InvestigadorInterno) {
			sedePersona = ((InvestigadorInterno) personaActual).getDependencia().getSede();
		}

		// Se genera el WHERE, dependiendo si es Coordinador de Laboratorio, o
		// Dirección de Laboratorios de Sede:
		where = null;
		idLabs = (String) sesion.getAttribute("idLabs");
		if (idLabs != null) {
			idLabs = idLabs.trim().replace(" ", ", ");
			where = "LDE.laboratorio.id in (" + idLabs + ")";
			tituloTablaBusqueda = " - Coordinador de Laboratorios: " + idLabs;
		}
		// sede:
		if (sedePersona != null) {
			where = "LDE.laboratorio.sede.id in (" + sedePersona.getId() + ")";
			tituloTablaBusqueda = " - Sede " + sedePersona.getNombre();
		}
		// facultad:
		if (esLaboratoriosFacultad) {
			tituloTablaBusqueda = " -  " + dlf.getNombre();
			where = "LDE.laboratorio.facultad.id in ('" + dlf.getId() + "')";
			sedeSeleccionada = dlf.getSede().getId().toString();
			facultadSeleccionada = dlf.getId().toString();
		}
		// departamento:
		departamentoSeleccionado = "";
		if (esLaboratoriosDepartamento) {
			tituloTablaBusqueda = " -  " + dld.getNombre();
			where = "LDE.laboratorio.departamento.id in ('" + dld.getId()
					+ "')";
			sedeSeleccionada = dld.getSede().getId().toString();
			facultadSeleccionada = dld.getFacultad().getId().toString();
			departamentoSeleccionado = dld.getDepartamento().toString();
		}

		mecanismoAdquisicionSelectItem = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_MECANISMOS_ADQUISICION_EQUIPOS);
		mantenimientoSelectItem = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.FRECUENCIA_MANTENIMIENTO);
		mantenimientoSI = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.FRECUENCIA_MANTENIMIENTO);
		calibracionSI = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.FRECUENCIA_MANTENIMIENTO);
		calibracionSelectItem = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.FRECUENCIA_MANTENIMIENTO);
		verificacionSelectItem = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.FRECUENCIA_MANTENIMIENTO);
		
		tipoArchivoActividadMantto = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_ARCHIVOS_ACTIVIDAD_LABORATORIO_MANTTO);
		tipoArchivoActividadCalib = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_ARCHIVOS_ACTIVIDAD_LABORATORIO_CALIB);
		
		tiposActSoftwareSelectItem = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_LAB_EQUIPOS_ACTUALIZACIONES_SOFTWARE);

		SelectItem[] magnitudSelectItem2 = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_MAGNITUDES_FISICAS);
		magnitudSelectItem = new SelectItem[magnitudSelectItem2.length + 1];
		Tipos tipo = new Tipos();
		tipo.setId(-1L);
		tipo.setNombre("Seleccione una...");
		magnitudSelectItem[0] = new SelectItem(tipo, tipo.getNombre());
		for (int i = 1; i <= magnitudSelectItem2.length; i++) {
			System.out.println("i:" + i);
			magnitudSelectItem[i] = magnitudSelectItem2[i - 1];
		}
		
		List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerTiposDeDocumento();
		tipoDocumentoSelectItem = new SelectItem[listaTipoDocumento.size()];
		int i = 0;
		for (TipoDocumento tD : listaTipoDocumento) {
			tipoDocumentoSelectItem[i] = new SelectItem(tD.getId(),
					tD.getNombre());
			i++;
		}

		entrenamientoBrindadoSelectItem = new SelectItem[2];
		entrenamientoBrindadoSelectItem[0] = new SelectItem(false, "NO");
		entrenamientoBrindadoSelectItem[1] = new SelectItem(true, "SI");

		instrumentoMedicionSelectItem = new SelectItem[2];
		instrumentoMedicionSelectItem[0] = new SelectItem(false, "NO");
		instrumentoMedicionSelectItem[1] = new SelectItem(true, "SI");
		
		requiereMantenimientoSelectItem = new SelectItem[2];
		requiereMantenimientoSelectItem[0] = new SelectItem(false, "NO");
		requiereMantenimientoSelectItem[1] = new SelectItem(true, "SI");
		
		calibradoSelectItem = new SelectItem[2];
		calibradoSelectItem[0] = new SelectItem(false, "NO");
		calibradoSelectItem[1] = new SelectItem(true, "SI");

		cambiaInstrumentoMedicion();

		frecManttoPrevSelectItem = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_FRECUENCIA_MANTENIMIENTO_PREVENTIVO);

		estadoNuevaActividadSelectItem = selectItemEstadosActividades();
		estadoNuevaActividadManttoSelectItem = selectItemEstadosActividades();

		tipoNuevaActividadSelectItem = selectItemTiposXids(LaboratorioActividadEquipo.TIPOS_ACT_CALIBR);
		tipoNuevaActividadManttoSelectItem = selectItemTiposXids(LaboratorioActividadEquipo.TIPOS_ACT_MANTTO);
		tipoNuevaActividadManttoModSelectItem = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPO_LAB_MODALIDAD_ACTIVIDAD);
		
		actividadSeleccionadaEditar = new LaboratorioActividadEquipo();
		comsumibleSeleccionadoEditar = new LaboratorioEquipoConsumibleRepuesto();
		reporteDanioSeleccionadoEditar = new LaboratorioEquipoReporteDanio();
		actualizacionSoftwareSeleccionadaEditar = new LaboratorioEquipoActualizacionSoftware();
		
		listaActividadesEquipoEliminar = new ArrayList<LaboratorioActividadEquipo>();
		listaActividadesManttoEliminar = new ArrayList<LaboratorioActividadEquipo>();
		
		listaConsumiblesEliminar = new ArrayList<LaboratorioEquipoConsumibleRepuesto>();
		listaReportesDanioEliminar = new ArrayList<LaboratorioEquipoReporteDanio>();
		listaActualizcionesSoftwareEliminar = new ArrayList<LaboratorioEquipoActualizacionSoftware>();

		buscarSedeSelectItem = selectItemSedes();
		buscarFacultadSelectItem = selectItemFacultades();
		
		tipoArchivoActividadSelectItem = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_ARCHIVOS_ACTIVIDAD_LABORATORIO_MANTTO);

		// siempre debe mostrarse el cronograma:
		//panelesActivos = "6";
		
		tieneHVAlCargarFormulario = false;
		
		// Si viene desde la página de equipos:
		if (fromEquipos) {
			equipo = (LaboratorioDetalleEquipos) sesion
					.getAttribute("equipoSeleccionado");
			soloLectura = !(Boolean) sesion.getAttribute("editarEquipo");
			//cambiarPanelesActivos("0");
			hojaDeVida();
			tieneHVAlCargarFormulario = equipo.getTieneHojaDeVida();
		}
		
		dondeCalibradoSelectItem = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPO_LAB_METRO_DONDE_CALIBRADO);
		incertidumbreCalibradoSelectitem = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPO_LAB_METRO_INCERTIDUMBRE_CALIBRADO);
		
		List<TipoDocumento> listaTipoDocumentoMetro = servicioGeneral.obtenerTiposDeDocumentoMetrologia();
		tipoDocumentoItem = new SelectItem[listaTipoDocumentoMetro.size()];
        for (int j = 0; j < listaTipoDocumentoMetro.size(); j++) {
            TipoDocumento td = listaTipoDocumentoMetro.get(j);
            tipoDocumentoItem[j] = new SelectItem(td.getId(), td.getNombre());
        }
		
        listaArchivosActividades = new ArrayList<ArchivoLaboratorio>();
        listaArchivosEliminadosActividades = new ArrayList<ArchivoLaboratorio>();
        listaArchivosActividadVista = new ArrayList<ArchivoLaboratorio>();
//        tipoArchivoActividadSeleccionado = obtenerTipoXid(Tipos.TIPOS_ARCHIVOS_ACTIVIDAD_LABORATORIO_Informe_Seguimiento);
        
        listaArchivosReporteDanio = new ArrayList<ArchivoLaboratorio>();
        listaArchivosEliminadosReporteDanio = new ArrayList<ArchivoLaboratorio>();
        listaArchivosReporteDanioVista = new ArrayList<ArchivoLaboratorio>();
        
        listaArchivosActividadesCalib = new ArrayList<ArchivoLaboratorio>();
        listaArchivosEliminadosActividadesCalib = new ArrayList<ArchivoLaboratorio>();
        listaArchivosActividadVistaCalib = new ArrayList<ArchivoLaboratorio>();
        
	}
	
	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		wb.setSheetName(0, "REPORTE_BUSQUEDA_DE_EQUIPOS_" + getToday());
		int numeroColumnas = header.getPhysicalNumberOfCells();
		int numeroFilas = sheet.getPhysicalNumberOfRows();
		boolean filaTotalesCreada = false;

		// fija el estilo al encabezado
		for (int i = 0; i < numeroColumnas; i++) {
			header.getCell(i).setCellStyle(cellStyle);
			//sheet.autoSizeColumn(i);
		}

		// Inmovilizar fila superior:
		sheet.createFreezePane(0, 1);

	}

	public SelectItem[] selectItemEstadosActividades() {
		SelectItem[] enasi = new SelectItem[2];
		enasi[0] = new SelectItem(LaboratorioActividadEquipo.ESTADO_PROGRAMADA,
				LaboratorioActividadEquipo.NOMBRE_ESTADO_PROGRAMADA);
		enasi[1] = new SelectItem(LaboratorioActividadEquipo.ESTADO_EJECUTADA,
				LaboratorioActividadEquipo.NOMBRE_ESTADO_EJECUTADA);
		return enasi;
	}

	/**
	 * @param idsTipos
	 * @return SelectItem con objetos Tipo, cuyos ids están en idsTipos
	 */
	public SelectItem[] selectItemTiposXids(String idsTipos) {
		String hqlQuery = "FROM Tipos WHERE id IN (" + idsTipos
				+ ") ORDER BY id";

		// TODO: ordenar por id, sacar actividades mantto de la otra lista,
		// cambiar otro Selectitem

		System.out.println("hqlQuery:" + hqlQuery);
		List<Tipos> listaTipos = servicioGeneral.obtenerObjetos(Tipos.class,
				hqlQuery);
		int i = 0;
		SelectItem[] itemHijos = new SelectItem[listaTipos.size()];
		for (Tipos tipo : listaTipos) {
			System.out.println("i:" + i);
			itemHijos[i++] = new SelectItem(tipo, tipo.getNombre());
			System.out.println("i:" + i);
		}
		return itemHijos;
	}

	public String volverLaboratorio() {
		System.out.println("volverLaboratorio:");
		limpiarSesion();
		sesion.removeAttribute("fromEquipos");
		return "laboratorioEquipos";
	}

	public void cambiaEntrenamientoBrindado() {
		System.out.println("cambiaEntrenamientoBrindado:"
				+ entrenamientoBrindado);
	}

	public void cambiaInstrumentoMedicion() {
		if (equipo != null) {
			Boolean eim = equipo.getInstrumentoMedicion();
			System.out.println("cambiaInstrumentoMedicion:" + eim);
//			cambiarPanelesActivos("2");
			tabindex = 2;
		} else {
			System.out.println("cambiaInstrumentoMedicion: equipo null");
		}
	}

	public void buscarPersona() {
		personaCargo = "";
		personaNombre = "";
		System.out.println("buscarPersona");
		if (!documentoPersona.equals("")) {
			IdPersona idPersona = new IdPersona(documentoPersona,
					tipoDocumentoId);
			Persona persona = new Persona();
			persona = servicioPersona.obtenerPersona(idPersona);
			if (persona != null) {
				personaNombre = persona.getNombreCompleto();
				InvestigadorInterno investigadorInterno = servicioPersona
						.obtenerInvestigadorInterno(idPersona);
				if (investigadorInterno != null
						&& investigadorInterno.getTipoCargo() != null) {
					personaCargo = investigadorInterno.getTipoCargo()
							.getNombre();
				}
			}

			Estudiante estudiante = servicioPersona
					.obtenerEstudiante(idPersona);
			if (estudiante != null) {
				InvestigadorInterno ii = estudiante.convertirAInvestigador();
				personaNombre = ii.getNombreCompleto();
				if (personaCargo == "" && estudiante.esInterno()) {
					personaCargo = "ESTUDIANTE "
							+ estudiante.getNombreCarrera().toUpperCase();
				}
			}

			if (personaNombre == "") {
				mensajeError("No existe una persona con documento "
						+ documentoPersona);
			}

		}
	}

	public void agregarPersonaEntrenamiento() {
		System.out.println("agregarPersonaEntrenamiento inicio");
		System.out.println("listaPersonasEntrenamiento.size():"
				+ listaPersonasEntrenamiento.size());
		personaNombre = personaNombre.toUpperCase().trim();

		if (personaNombre != "") {
			LaboratorioDetallePersona lDP = new LaboratorioDetallePersona();
			lDP.setCargo(personaCargo.trim());
			lDP.setDocumento(documentoPersona);
			lDP.setFechaRegistro(new Date());
			lDP.setIdDetalle(equipo.getId());
			lDP.setNombrePersona(personaNombre);
			lDP.setTipoDocumento(tipoDocumentoId);

			if (!listaPersonasEntrenamiento.contains(lDP)) {
				listaPersonasEntrenamiento.add(lDP);
			} else {
				mensajeError("formHDVE:personaNombre",
						"Ya se agregó a esa persona.");
			}
			personaCargo = "";
			personaNombre = "";
			documentoPersona = "";
		} else {
			mensajeError("formHDVE:personaNombre",
					"El Nombre de la persona es obligatorio.");
		}

		System.out.println("listaPersonasEntrenamiento.size():"
				+ listaPersonasEntrenamiento.size());
		System.out.println("agregarPersonaEntrenamiento fin");
	}

	public void eliminarPersonaEntrenamiento() {
		listaPersonasEntrenamiento.remove(detallePersonaSeleccionado);
		System.out.println("eliminarPersonaEntrenamiento: "
				+ detallePersonaSeleccionado.getNombrePersona());
	}

	public void handleSelectEmpresaCalib(SelectEvent event) {
//		String nombre = (String) event.getObject();
//		Empresa empresaCalib = servicioGeneral.buscarEmpresaXNombre(nombre);
//		nuevaActividadEquipo.setCalibEmpresa(empresaCalib);
	}
	
	public void handleSelectEmpresaFabricante(SelectEvent event) {
		String nombre = (String) event.getObject();
		Empresa empresaFabricante = servicioGeneral.buscarEmpresaXNombre(nombre);
		equipo.setEmpresaFabricante(empresaFabricante);
		mensajeInfo("Selected empresaFabricante: "+ empresaFabricante.getNombre());
	}

	public void handleSelectEmpresaDistribuidora(SelectEvent event) {
		String nombre = (String) event.getObject();
		Empresa empresaDistribuidora = servicioGeneral.buscarEmpresaXNombre(nombre);
		equipo.setEmpresaDistribuidora(empresaDistribuidora);
		
		if(empresaDistribuidora != null)
			mensajeInfo("Selected empresaDistribuidora: "+ empresaDistribuidora.getNombre());
	}

	public void buscarEquipo() {
		placaBuscar = placaBuscar.trim();

		String hql = "from LaboratorioDetalleEquipos LDE WHERE ("
				+ ReemplazaAcentos.queryLike("LDE.placa", placaBuscar) + " OR "
				+ ReemplazaAcentos.queryLike("LDE.equipo", placaBuscar) + " AND LDE.laboratorio IS NOT NULL AND LDE.laboratorio.activo = 1) ";

		String hql2 = hql;
		String where2;
		String whereCoord = "";

		if (sedeSeleccionada != "" && facultadSeleccionada == "") {
			// Sede:
			where = "(LDE.sede IS NOT NULL AND LDE.laboratorio.sede.id IN ('"+ sedeSeleccionada + "'))";
			where2 = "(LDE.sede IS NULL AND LDE.laboratorio.sede.id IN ('"+ sedeSeleccionada + "'))";
		} else if (facultadSeleccionada != "" && departamentoSeleccionado == "") {
			// Facultad
			where = "(LDE.facultad IS NOT NULL AND LDE.laboratorio.facultad.id IN ('"+ facultadSeleccionada + "'))";
			where2 = "(LDE.facultad IS NULL AND LDE.laboratorio.facultad.id IN ('"+ facultadSeleccionada + "'))";
		} else if (departamentoSeleccionado != "") {
			// Departamento
			where = "(LDE.departamento IS NOT NULL AND LDE.laboratorio.departamento.id IN ('"+ departamentoSeleccionado + "'))";
			where2 = "(LDE.departamento IS NULL AND LDE.laboratorio.departamento.id IN ('"+ departamentoSeleccionado + "'))";
		} else {
			where = null;
			where2 = null;
		}

		if (where != null) {
			hql += " AND " + where;
			hql2 += " AND " + where2;
		}

		if (poseeProcesoCompra != null && poseeProcesoCompra) {
			hql += " AND LDE.proceso IS NOT NULL ";
			hql2 += " AND LDE.proceso IS NOT NULL ";
		}

		hql += " ORDER BY LDE.laboratorio.sede.id, LDE.laboratorio.facultad.id, LDE.laboratorio.departamento.id";
		hql2 += " ORDER BY LDE.laboratorio.sede.id, LDE.laboratorio.facultad.id, LDE.laboratorio.departamento.id";

		listaEquiposEncontrados = servicioGeneral.obtenerObjetos(LaboratorioDetalleEquipos.class, hql);

		if (sedeSeleccionada != "") {
			// se hacen las dos consultas:
			List<LaboratorioDetalleEquipos> listaEquiposEncontrados2;
			listaEquiposEncontrados2 = servicioGeneral.obtenerObjetos(LaboratorioDetalleEquipos.class, hql2);

			for (LaboratorioDetalleEquipos lde2 : listaEquiposEncontrados2) {
				if (!listaEquiposEncontrados.contains(lde2)) {
					listaEquiposEncontrados.add(lde2);
				}
			}

		}

		esEquipoSinLaboratorio = false;
		
		for (LaboratorioDetalleEquipos equipo : listaEquiposEncontrados) {
			equipo.setHermesQuipu("HERMES");
		}		

		if (listaEquiposEncontrados.size() < 1) {
			mensajeInfo("formHDVE:placaEquipo",
					"No se encontró ningún equipo con placa o nombre: '"
							+ placaBuscar + "' asociado a un Laboratorio de acuerdo a los parametros de búsqueda establecidos. Si desea asociar el equipo a un laboratorio, debe realizarse a través de la opción “administrar laboratorios” dar clic en editar el laboratorio, ir al formulario – “equipos”, "
									+ "recuerde que debe tener el rol correspondiente en el laboratorio para poder editar la información.");
		}	
//			if (ReemplazaAcentos.esNumero(placaBuscar)) {
				// Se busca si el equipo existe en el inventario, y se propone
				// crear uno nuevo:
		List<Bien> bienes = servicioGeneral.consultaEquipos(placaBuscar);
		if (!bienes.isEmpty()) {
			for (Bien bien : bienes) {
				LaboratorioDetalleEquipos equipoNuevo = new LaboratorioDetalleEquipos();
				try {
					BeanUtils.copyProperties(equipoNuevo, bien);
					equipoNuevo.setHermesQuipu("QUIPU");
					listaEquiposEncontrados.add(equipoNuevo);
					esEquipoSinLaboratorio = true;
					
				} catch (IllegalAccessException e) {

					e.printStackTrace();
				} catch (InvocationTargetException e) {

					e.printStackTrace();
				}
			}
		} else {
			mensajeInfo("No se encuentra ningún equipo en inventario con esa placa.");
		}

	}

	public void consultarHojaDeVida() {
		soloLectura = true;
		hojaDeVida();
		// otra forma de consultar???'

	}

	public void hojaDeVida() {
		esEquipoSinLaboratorio = (equipo.getLaboratorio() == null);

//		Boolean buscarEnBienes = true;
//		Date fechaBusquedaEnBienes = equipo.getFechaBusquedaEnBienes();
//		if (fechaBusquedaEnBienes != null) {
//			Long diff = (new Date().getTime() - fechaBusquedaEnBienes.getTime())
//					/ (1000 * 60 * 60 * 24);
//			System.out.println("diff: " + diff);
//			if (diff < 60) {
//				buscarEnBienes = false;
//			}
//		}
		
		Boolean buscarEnBienes = true;
		System.out.println("buscarEnBienes: " + buscarEnBienes);

		if (buscarEnBienes) {
			List<Bien> bienes;
			bienes = servicioGeneral.consultaEquipos(equipo.getPlaca());

			// no importa si la placa no está en Inventarios
			/*
			 * if (!bienes.isEmpty()) {
			 */

			try {
				if (!bienes.isEmpty()) {
					equipoExisteEnBDInv = true;
					equipo.setExisteEnBienes(true);
					bien = bienes.get(0);
					BeanUtils.copyProperties(equipoV, bien);
					
					// Se comenta ya que la informacion que se consulta en inventarios no es clara
					// y ya no estan separadas por punto y coma por lo que el método buscarEnDescripcion arroja excepcion
					//Actualizacion 25/10/17 - Se descomentan las 3 lineas ya que el problema fue solucionado.
					
					// 03/06/2021 - Se descomenta y se pone nuevamente en funcionamiento validacion frente a quipu para saber
					// que tan actualizada esta la info del equipo y mostrar diferencias.
					
					equipoV.setEquipo(buscarEnDescripcion("DESCRIPCION :"));
					equipoV.setMarca(buscarEnDescripcion("MARCA :"));
					equipoV.setModelo(buscarEnDescripcion("MODELO :"));

					if (equipo.getEquipo() == null) {
						equipo.setEquipo(equipoV.getEquipo());
					}
					if (equipo.getMarca() == null) {
						equipo.setMarca(equipoV.getMarca());
					}
					if (equipo.getModelo() == null) {
						equipo.setModelo(equipoV.getModelo());
					}
					if (equipo.getResponsable() == null) {
						equipo.setResponsable(equipoV.getResponsable());
					}
					if (equipo.getIdResponsable() == null) {
						equipo.setIdResponsable(equipoV.getIdResponsable());
					}
					if (equipo.getValor() == null) {
						equipo.setValor(equipoV.getValor());
					}
					if (equipo.getFechaServicio() == null) {
						equipo.setFechaServicio(equipoV.getFechaServicio());
					}
					if (equipo.getFechaAdquisicion() == null) {
						equipo.setFechaAdquisicion(equipoV
								.getFechaAdquisicion());
					}
					if (equipo.getSerial() == null) {
						equipo.setSerial(equipoV.getSerial());
					}

					// comparar información:
					mostrarDiferencias = false;
					String diferenciasMostrar = "Advertencia (Este es un mensaje de advertencia que no bloquea el proceso): Se identifican diferencias entre la información registrada en Hermes y la información en QUIPU. ";
					
					diferencias = "Por favor verificar los campos: ";
					try {
						if (!equipo.getEquipo().equals(equipoV.getEquipo())) {
							mostrarDiferencias = true;
							System.out.println("Nombre de equipo diferente");
							diferencias += " -Nombre Equipo ";
						}
						if (!equipo.getMarca().equals(equipoV.getMarca())) {
							mostrarDiferencias = true;
							System.out.println("Marca de equipo diferente");
							diferencias += " -Marca ";
						}
						if (!equipo.getModelo().equals(equipoV.getModelo())) {
							mostrarDiferencias = true;
							System.out.println("Modelo de equipo diferente");
							diferencias += " -Modelo ";
						}

						if (equipo.getSerial() != null
								&& equipoV.getSerial() != null) {
							if (!equipo.getSerial().equals(equipoV.getSerial())) {
								mostrarDiferencias = true;
								System.out.println("Serial de equipo diferente");
								diferencias += " -Serial ";
							}
						}

					} catch (Exception e) {

						System.out.println("mostrarDiferencias ERROR");
						mostrarDiferencias = true;
						diferencias += " -Error al Comparar ";
					}

					String diferenciasEquipo = new String(diferencias);
					equipo.setDiferenciasConBienes(diferenciasEquipo);
					diferencias = diferenciasMostrar + diferencias;
				} else {
					equipoExisteEnBDInv = false;
					equipo.setExisteEnBienes(false);
				}
				// siempre se guarda:
				equipo.setFechaBusquedaEnBienes(new Date());
				if (equipo.getId() != null) {
					servicioGeneral.guardarObjeto(equipo);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			equipoExisteEnBDInv = equipo.getExisteEnBienes();
		}

		if (equipo.getEmpresaFabricante() == null) {
			equipo.setEmpresaFabricante(new Empresa());
		}
		if (equipo.getEmpresaDistribuidora() == null) {
			equipo.setEmpresaDistribuidora(new Empresa());
		}

		sedeSelectItem = servicioGeneral.selectItemSedes();
		// equipo sin laboratorio nuevo:
		if (esEquipoSinLaboratorio && equipo.getId() == 0) {
			equipo.setSede(new Sede((Long) sedeSelectItem[0].getValue()));
			equipo.setEdificio(new Edificio());
			cambiarSede();
		}

		if (esEquipoSinLaboratorio) {
			// carga los selectitems, se supone que la sede y la
			// facultad no son nulas:
			facultadSelectItem = servicioGeneral.selectItemFacultades(equipo
					.getSede().getId());
			String idFacultad = equipo.getFacultad().getId();
			departamentoSelectItem = servicioGeneral
					.selectItemDepartamentos(idFacultad);
			edificioSelectItem = selectItemEdificios(equipo.getSede().getId());
		}

		// carga listaPersonasEntrenamiento
		String hql = "FROM LaboratorioDetallePersona WHERE idDetalle = '"+ equipo.getId() + "'";
		System.out.println("hql:" + hql);
		listaPersonasEntrenamiento = servicioGeneral.obtenerObjetos(LaboratorioDetallePersona.class, hql);
		if (listaPersonasEntrenamiento.size() > 0) {
			entrenamientoBrindado = true;
		}

		// carga listaArchivos
		String hql2 = "FROM ArchivoLaboratorio WHERE idDetalle = '"+ equipo.getId() + "' ORDER BY id DESC";
		System.out.println("hql2:" + hql2);
		listaArchivos = new LinkedList<ArchivoLaboratorio>();
		listaArchivos.addAll(servicioGeneral.obtenerObjetos(ArchivoLaboratorio.class, hql2));
		cargarListaArchivosPlanMantto();
		cargaListaFotosEquipo();

		tipoArchivoSelectItem = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_ARCHIVOS_LABORATORIOS);
		tipoArchivoSelectItemActividades = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_ARCHIVOS_ACTIVIDAD_LABORATORIO_MANTTO);
		tipoArchivoSelectItemActividadesCalib = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_ARCHIVOS_ACTIVIDAD_LABORATORIO_CALIB);
		
		tipoArchivoSeleccionado = (Tipos) tipoArchivoSelectItem[0].getValue();
		tipoArchivoSeleccionadoActividad = (Tipos) tipoArchivoSelectItemActividades[0].getValue();
		tipoArchivoSeleccionadoActividadCalib = (Tipos) tipoArchivoSelectItemActividadesCalib[0].getValue();
		
		if (esEquipoSinLaboratorio && equipo.getDepartamento() == null) {
			equipo.setDepartamento(new Dependencia());
		}

		// carga lista de actividades de mantenimiento
		String hql3 = "FROM LaboratorioActividadEquipo WHERE equipo = '"
				+ equipo.getId() + "' AND tipoActividad IN ("
				+ LaboratorioActividadEquipo.TIPOS_ACT_MANTTO + ")"
				+ " AND activa = '1' ORDER BY id";
		System.out.println("hql3:" + hql3);
		listaActividadesMantto = servicioGeneral.obtenerObjetos(
				LaboratorioActividadEquipo.class, hql3);
		
		// carga lista de CONSUMIBLES
		listaConsumibles = 
			servicioGeneral.obtenerObjetos(
				LaboratorioEquipoConsumibleRepuesto.class,
				"FROM LaboratorioEquipoConsumibleRepuesto WHERE equipo = '"+ equipo.getId() +"' ORDER BY id"
			);
		
		// carga lista de REPORTES DAÑO
		listaReportesDanio = 
				servicioGeneral.obtenerObjetos(
						LaboratorioEquipoReporteDanio.class,
					"FROM LaboratorioEquipoReporteDanio WHERE equipo = '"+ equipo.getId() +"' ORDER BY id"
				);
		
		// carga lista de ACT SOFTWARE	
		listaActualizcionesSoftware = 
				servicioGeneral.obtenerObjetos(
						LaboratorioEquipoActualizacionSoftware.class,
					"FROM LaboratorioEquipoActualizacionSoftware WHERE equipo = '"+ equipo.getId() +"' ORDER BY id"
				);
		
		String hql7 = "FROM LaboratorioActividadEquipo WHERE equipo = '"
				+ equipo.getId() + "' AND tipoActividad IN ("
				+ LaboratorioActividadEquipo.TIPOS_ACT_MANTTO + ")"
				+ " AND activa = '1' ORDER BY id";
		System.out.println("hql3:" + hql3);
		listaActividadesMantto = servicioGeneral.obtenerObjetos(
				LaboratorioActividadEquipo.class, hql3);
		
		String hql8 = "FROM LaboratorioActividadEquipo WHERE equipo = '"
				+ equipo.getId() + "' AND tipoActividad IN ("
				+ LaboratorioActividadEquipo.TIPOS_ACT_MANTTO + ")"
				+ " AND activa = '1' ORDER BY id";
		System.out.println("hql3:" + hql3);
		listaActividadesMantto = servicioGeneral.obtenerObjetos(
				LaboratorioActividadEquipo.class, hql3);

		actividadNueva = new LaboratorioActividadMantenimiento();
		cambiosEnActividades = false;

		// carga lista de actividades de calibración
		String hql4 = "FROM LaboratorioActividadEquipo WHERE equipo = '"
				+ equipo.getId() + "' AND tipoActividad IN ("
				+ LaboratorioActividadEquipo.TIPOS_ACT_CALIBR + ")"
				+ " AND activa = '1' ORDER BY id";
		System.out.println("hql4:" + hql4);
		listaActividadesEquipo = servicioGeneral.obtenerObjetos(
				LaboratorioActividadEquipo.class, hql4);

		actualizarCronograma();

		nuevaActividadEquipo = new LaboratorioActividadEquipo();
		consultaActividadEquipoCalib = new LaboratorioActividadEquipo();
		editaActividadEquipoCalib = new LaboratorioActividadEquipo();
		
		nuevaActividadMantto = new LaboratorioActividadEquipo();
		nuevoConsumibleRepuesto = new LaboratorioEquipoConsumibleRepuesto();
		nuevoReporteDanio = new LaboratorioEquipoReporteDanio();
		nuevaActualizacionSoftware = new LaboratorioEquipoActualizacionSoftware();
		
		nuevaActividadMantto.setEstadoActividad("P");
		nuevaActividadMantto.setProgramarProximaActividad(false);
		cambiosEnActividadEquipo = false;
		
		nuevaActividadEquipo.setEstadoActividad("P");
		nuevaActividadEquipo.setProgramarProximaActividad(false);
		nuevaActividadEquipo.setCalibAdecuadoUso(false);
		nuevaActividadEquipo.setCalibEmpresaAcreditada(false);

		// cargar lista de especificaciones metrológicas (magnitudes)
		String hql5 = "FROM LaboratorioEquipoMetrologia WHERE equipo = '"
				+ equipo.getId() + "' ORDER BY id";
		System.out.println("hql5:" + hql5);
		listaMagnitudes = servicioGeneral.obtenerObjetos(
				LaboratorioEquipoMetrologia.class, hql5);
		nuevaEspecificacionMetrologica = new LaboratorioEquipoMetrologia();
		listaMagnitudesEliminadas = new ArrayList<LaboratorioEquipoMetrologia>();
		cambiosEspecificacionesMetrologicas = false;

		mostrarHV = true;
		calcularCompletitudEquipo();
		convertirListaInformacionFaltante();
		actualizarEquipoRobusto();
	}
	
	public void convertirListaInformacionFaltante()
	{
		
		if(equipo != null)
		{
			String informacionFaltanteInicial = equipo.getInformacionFaltante();
			String[] lista = informacionFaltanteInicial.split("-");
			
			for (int i = 1; i < lista.length; i++) {
				listaInfoFaltante.add(lista[i]);
			}
			
		}
		else
			System.out.println("Equipo NULO");
	}

	public void agregarEspecificacionMetrologica() {
		Boolean validar = true;

		if (nuevaEspecificacionMetrologica.getTipoMagnitud().getId()
				.equals(-1L)) {
			mensajeError("formHDVE:tabViewHV:somMagnitud","Magnitud: debe seleccionar una opción");
			validar = false;
		}

		if (nuevaEspecificacionMetrologica.getTipoMagnitud().getId()
				.equals(Tipos.TIPO_MAGNITUD_FISICA_OTRA)) {
			if (nuevaEspecificacionMetrologica.getMagnitud().equals("")) {
				mensajeError("formHDVE:tabViewHV:itMagnitud","Magnitud: campo obligatorio");
				validar = false;
			}
		}

		if (nuevaEspecificacionMetrologica.getUnidad().equals("")) {
			mensajeError("formHDVE:tabViewHV:itUnidad","Unidad: campo obligatorio");
			validar = false;
		}

		if (validar) {
			nuevaEspecificacionMetrologica.setEquipo(equipo);
			listaMagnitudes.add(nuevaEspecificacionMetrologica);
			nuevaEspecificacionMetrologica = new LaboratorioEquipoMetrologia();
			cambiosEspecificacionesMetrologicas = true;
			calcularCompletitudEquipo();
			actualizarEquipoRobusto();
		}
//		System.out.println("agregarEspecificacionMetrologica OUT:"+ listaMagnitudes.size());
	}

	public void eliminarEspecificacionMetrologica() {
		listaMagnitudes.remove(magnitudSeleccionada);
		listaMagnitudesEliminadas.add(magnitudSeleccionada);
		cambiosEspecificacionesMetrologicas = true;
		calcularCompletitudEquipo();
		actualizarEquipoRobusto();
//		System.out.println("eliminarEspecificacionMetrologica OUT:"+ listaMagnitudes.size());
	}

	public void actualizarCronograma() {
		System.out.println("actualizarCronograma IN:");
		cronograma = new DefaultScheduleModel();
		Date fechaActual = new Date();

		List<LaboratorioActividadEquipo> listaTotalActividades = new ArrayList<LaboratorioActividadEquipo>();
		listaTotalActividades.addAll(listaActividadesEquipo);
		listaTotalActividades.addAll(listaActividadesMantto);

		for (LaboratorioActividadEquipo lae : listaTotalActividades) {
			Date fechaActividad = lae.getFechaActividad();
			Date fechaEjecucion = lae.getFechaEjecucion();
			System.out.println("actividadId: " + lae.getId());
			System.out.println("fechaActividad: " + fechaActividad);
			System.out.println("fechaEjecucion: " + fechaEjecucion);

			if (fechaActividad == null) {
				fechaActividad = fechaEjecucion;
				System.out.println("fechaEjecucion: " + fechaActividad);
			}

			// se quitan las tildes, aparecen con escape p.ej:&#243
			DefaultScheduleEvent evento = new DefaultScheduleEvent(
					ReemplazaAcentos.quitarTildes(lae.getTipoActividad()
							.getNombre()), fechaActividad, fechaActividad, true);
			String estadoActividad = lae.getEstadoActividad();

			// actividad ejecutada
			if (estadoActividad
					.equals(LaboratorioActividadEquipo.ESTADO_EJECUTADA)) {
				evento.setStyleClass("verde_schedule");
				System.out.println("verde");
			}

			// actividad ejecutada tarde:
			if (estadoActividad
					.equals(LaboratorioActividadEquipo.ESTADO_EJECUTADA)
					&& fechaEjecucion != null
					&& (fechaActividad.before(fechaEjecucion))) {
				evento.setStyleClass("naranja_schedule");
				System.out.println("naranja");
			}

			// actividad programada atrasada
			if (estadoActividad.equals(LaboratorioActividadEquipo.ESTADO_PROGRAMADA) && fechaActual.after(fechaActividad)) {
				evento.setStyleClass("rojo_schedule");
				System.out.println("rojo");

			}

			// daño y mal funcionamiento: ROJO OSCURO:
			if (lae.getTipoActividad().getId()
					.equals(Tipos.TIPO_ACTIVIDAD_EQUIPO_LAB_DANNIO)
					|| lae.getTipoActividad()
							.getId()
							.equals(Tipos.TIPO_ACTIVIDAD_EQUIPO_LAB_MAL_FUNCIONAMIENTO)) {
				evento.setStyleClass("rojo_oscuro_schedule");
				System.out.println("Daño rojo oscuro");
			}

			cronograma.addEvent(evento);

			// Se fija el id del evento, para poder relacionarlo con la
			// actividad:
			lae.setEventoId(evento.getId());

		}
		System.out.println("actualizarCronograma OUT.");
	}

	public String modificarArchivosActividades()
	{		
		tipoArchivoSeleccionadoActividad = (Tipos) tipoArchivoSelectItemActividades[0].getValue();
		listaArchivosActividadVista = (ArrayList<ArchivoLaboratorio>) servicioGeneral.obtenerArchivosLaboratorioXidActividad(actividadSeleccionadaEditar.getId());
		return "";
	}
	
	public String modificarArchivosActividadesCalib()
	{		
		tipoArchivoSeleccionadoActividadCalib = (Tipos) tipoArchivoSelectItemActividadesCalib[0].getValue();
		listaArchivosActividadVistaCalib = (ArrayList<ArchivoLaboratorio>) servicioGeneral.obtenerArchivosLaboratorioXidActividad(actividadSeleccionadaEditar.getId());
		return "";
	}
	
	public String modificarArchivosReporteDanio()
	{		
		listaArchivosReporteDanioVista = (ArrayList<ArchivoLaboratorio>) servicioGeneral.obtenerArchivosLaboratorioXidReporteDanio(nuevoReporteDanio.getId());
		return "";
	}

	public void cargaListaFotosEquipo() {
		String hql3 = "FROM ArchivoLaboratorio WHERE idDetalle = '"
				+ equipo.getId() + "' AND tipoArchivo = '"
				+ Tipos.TIPO_ARCHIVO_LABORATORIOS_FOTOGRAFIA + "' ORDER BY id";
		System.out.println("hql3:" + hql3);
		listaFotosEquipo = servicioGeneral.obtenerObjetos(
				ArchivoLaboratorio.class, hql3);
		System.out.println("listaFotosEquipo.size: " + listaFotosEquipo.size());
	}

	public void cargarListaArchivosPlanMantto() {
		listaArchivosPlanMantto = new LinkedList<ArchivoLaboratorio>();
		for (ArchivoLaboratorio al : listaArchivos) {
			if (al.getTipoArchivo().getId()
					.equals(Tipos.TIPO_ARCHIVO_LABORATORIOS_PLAN_MANTTO)) {
				listaArchivosPlanMantto.add(al);
			}
		}
		System.out.println("listaArchivosPlanMantto.size: "
				+ listaArchivosPlanMantto.size());
	}

	public String buscarEnDescripcion(String queBusca) {
		String descripcionBuscar = bien.getDescripcion();
		System.out.println("descripcionBuscar: " + descripcionBuscar);
		int inicioBusca = descripcionBuscar.indexOf(queBusca);
		if (inicioBusca >= 0) {
			int finalBusca = descripcionBuscar.indexOf(";", inicioBusca);
			descripcionBuscar = descripcionBuscar.substring(inicioBusca + queBusca.length(), finalBusca);
			System.out.println("descripcionBuscar: " + descripcionBuscar);
			descripcionBuscar = descripcionBuscar.trim();
			System.out.println("descripcionBuscar: " + descripcionBuscar);
		} else {
			descripcionBuscar = "";
		}
		return descripcionBuscar;
	}

	public void cambiarPanelesActivos(String nuevoPanel) {
		panelesActivos = "6," + nuevoPanel;
	}
	
	public void cambiarPotencia() {
		if(equipo.getNoAplicaPotencia())
			equipo.setPotenciaW(null);
	}
	
	public void cambiarVoltaje() {
		if(equipo.getNoAplicaVoltaje())
			equipo.setVoltajeV(null);
	}
	
	public void cambiarFechaInstalacion() {
		System.out.println("cambiarFechaInstalacion");
		int difencia = obtenerDiferenciaAnios(equipo.getFechaInstalacion(), new Date());
		if(difencia >= 10)
			equipo.setMayorDiezAnnios(true);
		else
			equipo.setMayorDiezAnnios(false);
	}
	
	public static int obtenerDiferenciaAnios(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
            (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) &&   
            a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }
	
//	public void cambiarEqAltaPrec() {
//		System.out.println("nuevaEspecificacionMetrologica.precisionValor: " + nuevaEspecificacionMetrologica.getPrecisionValor());
//	}
    
    public Boolean esEquipoExcepcionChambonaDNIL() {
    	if(equipo.getPlaca().equals("2354805") || equipo.getPlaca().equals("2239674")) {
    		return true;
    	}
    	return false;
    }
	
	public void actualizarEquipoRobusto() {
		equipo.setRobustoMayorValorReferencia(false);
		equipo.setRobustoAltaPrecision(false);
		equipo.setRobustoAltaExactitud(false);
		
		//esRobustoMayorValorReferencia
		if(!esNulo(equipo.getValor()) && equipo.getValor() > 0) {
			if(equipo.getValor()/SMMLV >= VALOR_REFERENCIA_EQUIPO_ROBUSTO || esEquipoExcepcionChambonaDNIL())
				equipo.setRobustoMayorValorReferencia(true);
		}
		
		// Lógica solo para cuando carga la HV
		if(!listaMagnitudes.isEmpty()) {
			for (LaboratorioEquipoMetrologia lem : listaMagnitudes) {
				if(!esNulo(lem.getEquipoAltaPrecision()) && lem.getEquipoAltaPrecision())
					equipo.setRobustoAltaPrecision(true);
				if(!esNulo(lem.getEquipoAltaExactitud()) && lem.getEquipoAltaExactitud())
					equipo.setRobustoAltaExactitud(true);
			}
		}
		
		Boolean esRobusto = esNulo(equipo.getRobustoEspecialidadCalidadAnalitica()) ? false : equipo.getRobustoEspecialidadCalidadAnalitica()
				&& equipo.getRobustoMayorValorReferencia()
				&& equipo.getRobustoAltaPrecision()
				&& equipo.getRobustoAltaExactitud();
		
		equipo.setRobusto(esRobusto);
	}

	public void guardar() {

		calcularCompletitudEquipo();

		// Calcular TODOS: calcularCompletitudTODOS();

		if (validar()) {
			// nueva Empresa
			Empresa empresaF = equipo.getEmpresaFabricante();
			String nombreEmpresaFab = empresaF.getNombre();
			if (nombreEmpresaFab != null && nombreEmpresaFab != "") {
				Empresa empresaFab = servicioGeneral.buscarEmpresaXNombre(nombreEmpresaFab);
				if(equipo.getEmpresaFabricante() !=null && empresaFab !=null) {
					empresaFab.setDireccion(equipo.getEmpresaFabricante().getDireccion());
					empresaFab.setTelefono(equipo.getEmpresaFabricante().getTelefono());
					empresaFab.setEmail(equipo.getEmpresaFabricante().getEmail());
					empresaFab.setPaginaWeb(equipo.getEmpresaFabricante().getPaginaWeb());
				}

				if (empresaFab != null) {
					equipo.setEmpresaFabricante(empresaFab);
					empresaF = equipo.getEmpresaFabricante();
					servicioGeneral.guardarObjeto(empresaFab);
				}
				
				if (empresaFab == null || empresaF.getId() == null) {
					empresaF.setId(null);
					empresaF.setUbicacionCadenaProductiva("Equipos de Laboratorio");
					servicioGeneral.guardarObjeto(empresaF);
				}
			} else {
				// Empresa fabricante obligatoria:
				// equipo.setEmpresaFabricante(null);
				equipo.setEmpresaFabricante(new Empresa());
				mensajeError("formHDVE:tabViewHV:acEmpresas","Debe ingresar la empresa fabricante del equipo, en la sección Información del Proveedor.");
				cambiarPanelesActivos("1");
				return;
			}

			Empresa empresaD = equipo.getEmpresaDistribuidora();
			String nombreEmpresaDist = empresaD.getNombre();
			if (nombreEmpresaDist != null && nombreEmpresaDist != "") {
				Empresa empresaDist = servicioGeneral.buscarEmpresaXNombre(nombreEmpresaDist);
				
				if(equipo.getEmpresaDistribuidora() != null && empresaDist != null) {
					empresaDist.setDireccion(equipo.getEmpresaDistribuidora().getDireccion());
					empresaDist.setTelefono(equipo.getEmpresaDistribuidora().getTelefono());
					empresaDist.setEmail(equipo.getEmpresaDistribuidora().getEmail());
					empresaDist.setPaginaWeb(equipo.getEmpresaDistribuidora().getPaginaWeb());
				}
				if (empresaDist != null) {
					equipo.setEmpresaDistribuidora(empresaDist);
					empresaD = equipo.getEmpresaDistribuidora();
					servicioGeneral.guardarObjeto(empresaDist);
				}

				if (empresaDist == null || empresaD.getId() == null) {
					empresaD.setId(null);
					empresaD.setUbicacionCadenaProductiva("Equipos de Laboratorio");
					servicioGeneral.guardarObjeto(empresaD);
				}
			} else {
				equipo.setEmpresaDistribuidora(new Empresa());
				mensajeError("formHDVE:tabViewHV:acEmpresasDist","Debe ingresar la empresa distribuidora del equipo, en la sección Información del Proveedor.");
				cambiarPanelesActivos("1");
				return;
			}

			// listaPersonasEntrenamiento
			// Se eliminan todos los detalles, si existen:
			String sql = "DELETE HER_LABORATORIO_DET_PERSONA WHERE LDE_ID = "+ equipo.getId();
			try {
				servicioGeneral.eliminar(sql);
			} catch (SQLException e) {
				mensajeError("Error eliminando personas que recibieron entrenamiento.");
				e.printStackTrace();
			}

			equipo.setFechaRegistro(new Date());
			equipo.setDocumentoPersonaRegistro(personaActual.getId().getDocumento());
			equipo.setTipoDocumentoPersonaRegistro(personaActual.getId().getTipoDocumento());
			equipo.setTieneHojaDeVida(true);
			
			//Especificaciones técnicas
			if(equipo.getNoAplicaPotencia())
				equipo.setPotenciaW(null);
			
			if(equipo.getNoAplicaVoltaje())
				equipo.setVoltajeV(null);
			
			//Si no requiere calibracion o mantenimiento se setea las variables de frecuencia con NO APLICA
			if(!equipo.getRequiereMantenimiento())
				equipo.setMantenimiento(Tipos.FRECUENCIA_MANTENIMIENTO_NO_APLICA);
			
			if(!equipo.getInstrumentoMedicion())
				equipo.setCalibracion(Tipos.FRECUENCIA_MANTENIMIENTO_NO_APLICA);
			
			if(!equipo.getEstaCalibrado()){
				equipo.setFechaCalibrado(null);
				equipo.setResolucion(null);
				equipo.setValorCalibracion(null);
				equipo.setFactorCoberturaK(null);
				equipo.setDondeCalibracion(null);
				equipo.setIncertidumbre(null);
				equipo.setTipoDocEntidadCalibra(null);
				equipo.setNumDocumentoEntidadCalibra(null);
				equipo.setEntidadCalibra(null);
			} else if(equipo.getEstaCalibrado() && equipo.getDondeCalibracion().getId().equals(Tipos.TIPO_LAB_METRO_DONDE_CALIBRADO_Interno)){
				equipo.setTipoDocEntidadCalibra(null);
				equipo.setNumDocumentoEntidadCalibra(null);
				equipo.setEntidadCalibra(null);
			}

			Boolean labNulo = (equipo.getLaboratorio() == null);
			// ES COORDINADOR DE UN SOLO LAB:
			if (esCoordinadorLaboratorio && labNulo) {
				if (idLabs != null && !idLabs.contains(",")) {
					try {
						Laboratorio lab = (Laboratorio) servicioGeneral.obtenerObjeto(new Laboratorio(), new Long(idLabs));
						equipo.setLaboratorio(lab);
					} catch (Exception e) {
						System.out.println("*** error: " + e.getStackTrace());
					}
				}
			}

			// completitud:
			calcularCompletitudEquipo();
			if (esEquipoSinLaboratorio && equipo.getId() == 0) {
				// equipo sin laboratorio nuevo
				equipo.setId(null);
				// valores no nulos:
				equipo.setEspecializado(false);
				equipo.setMayorDiezAnnios(false);
				equipo.setEstadoFisico(Tipos.TIPO_ESTADO_BUENO);
				equipo.setEstadoFisicoCenso(Tipos.TIPO_ESTADO_BUENO);
//				equipo.setCalibracion(Tipos.FRECUENCIA_MANTENIMIENTO_NO_APLICA);
				equipo.setEnUso(true);
				servicioGeneral.guardarObjeto(equipo);
				guardarLogEquipos(!tieneHVAlCargarFormulario,false,equipo);
				mensajeInfo("Hoja de vida del nuevo equipo con placa "
						+ equipo.getPlaca() + " guardada exitosamente.");
			} else {
				
				servicioGeneral.guardarObjeto(equipo);
				guardarLogEquipos(!tieneHVAlCargarFormulario,false,equipo);
						
				mensajeInfo("Hoja de vida del equipo con placa "+ equipo.getPlaca() + " guardada exitosamente.");
			}
			if (entrenamientoBrindado) {
				// se guardan los detalles de listaPersonasEntrenamiento
				for (LaboratorioDetallePersona lDP : listaPersonasEntrenamiento) {
					lDP.setId(null);
					lDP.setIdDetalle(equipo.getId());
					servicioGeneral.guardarObjeto(lDP);
				}
			}
			if (cambiosEnActividades) {
			}
			// TODO: borrar?
			if (cambiosEnActividadEquipo) {
				for (LaboratorioActividadEquipo lae : listaActividadesEquipo) {
//					if (lae.getId() == null || lae.getEditada()) {
//						System.out.println("guardando listaActividadesEquipo lae id nulo o editada: "+ lae.getId());
//						servicioGeneral.guardarObjeto(lae);
//					}
					if(lae.getId() == null)
					{
						System.out.println("guardando listaActividadesEquipo lae id nulo: "+ lae.getId());
						servicioGeneral.guardarObjeto(lae);
						guardarLogActividades(lae, Tipos.TIPO_OPERACION_LOG_ACT_CREACION);
					}else if(lae.getEditada())
					{
						System.out.println("actualizando listaActividadesEquipo lae editada: "+ lae.getId());
						
						servicioGeneral.guardarObjeto(lae);
						guardarLogActividades(lae, Tipos.TIPO_OPERACION_LOG_ACT_ACTUALIZACION);
					}
				}
				
				System.out.println("eliminado listaActividadesEquipo");
				for (LaboratorioActividadEquipo lac : listaActividadesEquipoEliminar) {
					if (lac.getId() != null) {
						lac.setActiva(false);
						servicioGeneral.guardarObjeto(lac);
						guardarLogActividades(lac, Tipos.TIPO_OPERACION_LOG_ACT_BORRADO);
					}
				}

				for (LaboratorioActividadEquipo lae : listaActividadesMantto) {
//					if (lae.getId() == null || lae.getEditada()) {
//						System.out.println("guardando listaActividadesMantto lae id nulo o editada: "+ lae.getId());
//						
//						servicioGeneral.guardarObjeto(lae);
//					}
					if(lae.getId() == null) {
						servicioGeneral.guardarObjeto(lae);
						guardarLogActividades(lae, Tipos.TIPO_OPERACION_LOG_ACT_CREACION);
					}else if(lae.getEditada()) {
						servicioGeneral.guardarObjeto(lae);
						guardarLogActividades(lae, Tipos.TIPO_OPERACION_LOG_ACT_ACTUALIZACION);
					}
				}
				
				// Desactivar Act mantto
				for (LaboratorioActividadEquipo lam : listaActividadesManttoEliminar) {
					if (lam.getId() != null) {
						lam.setActiva(false);
						servicioGeneral.guardarObjeto(lam);
						guardarLogActividades(lam, Tipos.TIPO_OPERACION_LOG_ACT_BORRADO);
					}
				}				
			}
			
			//Guardar lista comsumibles
			for (LaboratorioEquipoConsumibleRepuesto lae : listaConsumibles)
				servicioGeneral.guardarObjeto(lae);
			//Eliminar consumibles repuestos
			for (LaboratorioEquipoConsumibleRepuesto lam : listaConsumiblesEliminar) {
				if (lam.getId() != null)
					servicioGeneral.eliminarObjeto(lam);
			}
			
			//Guardar lista reportes daño
			for (LaboratorioEquipoReporteDanio lae : listaReportesDanio)
				servicioGeneral.guardarObjeto(lae);
			//Eliminar reportes daño
			for (LaboratorioEquipoReporteDanio lam : listaReportesDanioEliminar) {
				if (lam.getId() != null){
					servicioGeneral.eliminarObjeto(lam);
				}
			}
			
			//Guardar lista comsumibles
			for (LaboratorioEquipoActualizacionSoftware lae : listaActualizcionesSoftware)
				servicioGeneral.guardarObjeto(lae);
			//Eliminar actualizaciones software
			for (LaboratorioEquipoActualizacionSoftware lam : listaActualizcionesSoftwareEliminar) {
				if (lam.getId() != null){
					servicioGeneral.eliminarObjeto(lam);
				}
			}

			if (cambiosEspecificacionesMetrologicas) {
				for (LaboratorioEquipoMetrologia lem : listaMagnitudes) {
					if (lem.getId() == null) {
						servicioGeneral.guardarObjeto(lem);
					}
				}

				for (LaboratorioEquipoMetrologia lem : listaMagnitudesEliminadas) {
					if (lem.getId() != null) {
						servicioGeneral.eliminarObjeto(lem);
					} else {
						System.out.println("NO se elimina lem BD: "+ lem.getId());
					}
				}

			}

			// Archivos: CAZ
			System.out.println("guardando archivos:");
			for (ArchivoLaboratorio al : listaArchivosActividades) {
				// lao.setIdLaboratorio(laboratorioActual.getId());
				Long id = al.getId();
//				al.setIdLab(laboratorioActual.getId());
//				al.setActividad(actividadSeleccionadaEditar);
				try {
//					servicioGeneral.insertarObjetoConIdLong(al, id);
//					servicioGeneral.guardarObjeto(al);
					System.out.println("guardado nuevo archivo: " + id);
				} catch (Exception e) {

					System.out.println("ya estaba guardado archivo: " + id);
				}
			}

			// Eliminando archivos actividades:
			for (ArchivoLaboratorio ale : listaArchivosEliminadosActividades)
				eliminarArchivoLaboratorios(ale);
			
			// Eliminando archivos reportes daño:
			for (ArchivoLaboratorio ale : listaArchivosEliminadosReporteDanio)
				eliminarArchivoLaboratorios(ale);
			
			// Eliminando archivos actividades calib:
			for (ArchivoLaboratorio ale : listaArchivosEliminadosActividadesCalib)
				eliminarArchivoLaboratorios(ale);
		}
	}
	
	public void eliminarArchivoLaboratorios(ArchivoLaboratorio archivoLaboratorioEliminar) {
		try {
			Long id = archivoLaboratorioEliminar.getId();
			eliminarArchivoLaboratorio(id);
			servicioGeneral.eliminarObjeto(archivoLaboratorioEliminar);
		} catch (Exception e) {
			
		}
	}

	public MeterGaugeChartModel getModeloMeter() {
		Integer epc = equipo.getPorcentajeCompletitud();
		List<Number> intervals = new ArrayList<Number>();
		intervals.add(60);
		intervals.add(90);
		intervals.add(100);
		MeterGaugeChartModel modeloMeter = new MeterGaugeChartModel(epc,
				intervals);
		return modeloMeter;
	}

	public void calcularCompletitudTODOS() {

		List<LaboratorioDetalleEquipos> listaEquiposActualizar;
		String busqueda = "FROM LaboratorioDetalleEquipos lde WHERE lde.tieneHojaDeVida = '1' AND lde.porcentajeCompletitud IS NULL ORDER BY lde.fechaRegistro ASC";
		System.out.println("busqueda: " + busqueda);
		listaEquiposActualizar = servicioGeneral.obtenerObjetos(
				LaboratorioDetalleEquipos.class, busqueda);
		System.out.println("listaEquiposActualizar: "
				+ listaEquiposActualizar.size());

		int equiposAct = 0;

		forActualizarEquipos: for (LaboratorioDetalleEquipos ldes : listaEquiposActualizar) {

			equipo = ldes;

			System.out.println("fechaRegistro: " + equipo);
			System.out.println("fechaRegistro: " + equipo.getFechaRegistro());

			String hql4 = "FROM LaboratorioActividadEquipo WHERE equipo = '"
					+ equipo.getId() + "' AND tipoActividad IN ("
					+ LaboratorioActividadEquipo.TIPOS_ACT_CALIBR
					+ ") ORDER BY id";
			System.out.println("hql4:" + hql4);
			listaActividadesEquipo = servicioGeneral.obtenerObjetos(
					LaboratorioActividadEquipo.class, hql4);

			// carga lista de actividades de mantenimiento
			String hql3 = "FROM LaboratorioActividadEquipo WHERE equipo = '"
					+ equipo.getId() + "' AND tipoActividad IN ("
					+ LaboratorioActividadEquipo.TIPOS_ACT_MANTTO
					+ ") ORDER BY id";
			System.out.println("hql3:" + hql3);
			listaActividadesMantto = servicioGeneral.obtenerObjetos(
					LaboratorioActividadEquipo.class, hql3);

			calcularCompletitudEquipo();

			System.out.println("guardando equipo #:" + equiposAct);
			servicioGeneral.guardarObjeto(equipo);
			equiposAct++;

			if (equiposAct >= 999) {
				break forActualizarEquipos;
			}
		}

	}

	public void calcularCompletitudEquipo() {
		Integer completitud = 100;
		String datosCompletitud = "";

		try {
			System.out.println("calcularCompletitudEquipo: " + equipo);

			if (equipo.getPlaca().equals("0")) {
				completitud += -10;
				datosCompletitud += "- La placa del Equipo es igual a Cero (Información General).\r\n";
			}
			if (esNulo(equipo.getEquipo())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre el nombre del Equipo (Información General).\r\n";
			}
			if (esNulo(equipo.getMarca())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre la marca del Equipo (Información General).\r\n";
			}
			if (esNulo(equipo.getModelo())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre el modelo del Equipo (Información General).\r\n";
			}
			if (esNulo(equipo.getSerial())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre el serial del Equipo (Información General).\r\n";
			}

			if (esNulo(equipo.getAnnioFabricacion())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre el año de fabricación (Información General).\r\n";
			}
			if (esNulo(equipo.getFechaInstalacion())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre la fecha de instalación (Información General).\r\n";
			}
			if (esNulo(equipo.getMesesGarantia())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre el tiempo de Garantía (Información General).\r\n";
			}
			if (esNulo(equipo.getVidaUtilAnnios())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre la vida útil estimada (Información General).\r\n";
			}

			// empresa fabricante:
			if (esNulo(equipo.getEmpresaFabricante())
					|| esNulo(equipo.getEmpresaFabricante().getNombre())) {
				completitud += -5;
				datosCompletitud += "- No existe información registrada sobre la empresa fabricante (Información del Proveedor).\r\n";
			}
			// empresa distribuidora
			if (esNulo(equipo.getEmpresaDistribuidora())
					|| esNulo(equipo.getEmpresaDistribuidora().getNombre())) {
				completitud += -5;
				datosCompletitud += "- No existe información registrada sobre la empresa distribuidora (Información del Proveedor).\r\n";
			}
			// vendedor:
			if (esNulo(equipo.getNombreVendendor())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre el nombre del vendedor (Información del Proveedor).\r\n";
			}
			if (esNulo(equipo.getTelefonoVendedor())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre el teléfono del vendedor (Información del Proveedor).\r\n";
			}
			if (esNulo(equipo.getEmailVendedor())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre el email del vendedor (Información del Proveedor).\r\n";
			}

			if (esNulo(equipo.getSistemaAlimentacion())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre el Sistema de alimentación (Especificaciones técnicas).\r\n";
			}
			if (esNulo(equipo.getPotenciaW()) && !equipo.getNoAplicaPotencia()) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre la Potencia (Especificaciones técnicas).\r\n";
			}
			if (esNulo(equipo.getVoltajeV()) && !equipo.getNoAplicaVoltaje()) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre el Voltaje (Especificaciones técnicas).\r\n";
			}
			if (esNulo(equipo.getLargoCm())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre el Largo (Especificaciones técnicas).\r\n";
			}
			if (esNulo(equipo.getAnchoCm())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre el Ancho (Especificaciones técnicas).\r\n";
			}
			if (esNulo(equipo.getAltoCm())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre el Alto (Especificaciones técnicas).\r\n";
			}
			if (esNulo(equipo.getPesoKg())) {
				completitud += -2;
				datosCompletitud += "- No existe información registrada sobre el Peso (Especificaciones técnicas).\r\n";
			}

			int actCalibr = 0;
			int actMantto = 0;
			// actividades calibración:
			for (LaboratorioActividadEquipo lae : listaActividadesEquipo) {
				if (lae.getTipoActividad().getId()
						.equals(Tipos.TIPO_ACTIVIDAD_EQUIPO_CALIBRACION)) {
					actCalibr++;
				}
			}

			// actividades mantto:
			for (LaboratorioActividadEquipo lae : listaActividadesMantto) {
				if (lae.getTipoActividad()
						.getId()
						.equals(Tipos.TIPO_ACTIVIDAD_EQUIPO_LABORATORIO_MANTTO_PREV)) {
					actMantto++;
				}
			}

			// equipos de medición:
			if (equipo.getInstrumentoMedicion()) {
				// especificaciones metrológicas
				if (listaMagnitudes.isEmpty()) {
					completitud += -4;																					// 100 - cazapatamar - 21/09/2016 
					datosCompletitud += "- No existe información registrada sobre Especificaciones Metrológicas.\r\n";
				}
				// actividades de calibración:
				if (actCalibr < 1) {
					completitud += -20;
					datosCompletitud += "- No se han registrado Actividades de Aseguramiento Metrológico (Calibración).\r\n";
				}
			}

			// mantenimiento preventivo:
			if (actMantto < 1) {
				completitud += -20;
				datosCompletitud += "- No se han registrado Actividades de Mantenimiento Preventivo (sección Mantenimiento).\r\n";
			}

			equipo.setPorcentajeCompletitud(completitud);

		} catch (Exception e) {
			datosCompletitud += "- Error calculando completitud.\r\n";
			equipo.setPorcentajeCompletitud(null);
			e.printStackTrace();
		}
		equipo.setPorcentajeCompletitud(completitud);
		equipo.setInformacionFaltante(datosCompletitud);
	}

	/**
	 * Método que comprueba si un objeto es nulo, una cadena está vacía, o un
	 * número es igual a cero.
	 * 
	 * @param obj
	 * @return
	 * @author dgbenitezc
	 */
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

	public boolean validar() {
		boolean validar = true;
		if (entrenamientoBrindado) {
			if (listaPersonasEntrenamiento.isEmpty()) {
				mensajeError("formHDVE:tabViewHV:documentoPersona","Debe agregar al menos una persona que haya recibido entrenamiento.");
				cambiarPanelesActivos("1");
				validar = false;
			}
			if (equipo.getNombreInstructor() == "") {
				mensajeError(
						"formHDVE:tabViewHV:nombreInstructor",
						"Debe ingresar el nombre del instructor.  Si no lo sabe, escriba 'Desconocido'.");
				cambiarPanelesActivos("1");
				validar = false;
			}
		}
		
		//Validar # Orden de compra es numérico
		if(!esCadenaVacia(equipo.getOrdenDeCompra()))
		{	
			if(!cadenaEsValorNumerico(equipo.getOrdenDeCompra()))
			{
				mensajeError("formHDVE:tabViewHV:ordenCompra","La orden de compra debe ser un valor numérico. Si no cuenta con este dato, deje el campo vacio.");
				cambiarPanelesActivos("1");
				validar = false;
			}
		}

		// validar especificaciones metrológicas:
		if (equipo.getInstrumentoMedicion() && listaMagnitudes.size() == 0) {
			mensajeError("Al tratarse de un instrumento de medición, debe agregar al menos una Especificación Metrológica.");
			cambiarPanelesActivos("4");
			validar = false;
		}

		// validar actividades de calibración:
		if (equipo.getInstrumentoMedicion() && listaMagnitudes.size() > 0) {
			int actCalib = 0;
			int actVerif = 0;
			for (LaboratorioActividadEquipo lae : listaActividadesEquipo) {
				if (lae.getTipoActividad().getId().equals(Tipos.TIPO_ACTIVIDAD_EQUIPO_CALIBRACION))
					actCalib++;
				if (lae.getTipoActividad().getId().equals(Tipos.TIPO_ACTIVIDAD_EQUIPO_LABORATORIO_VERIFICACION))
					actVerif++;
			}
			if (actCalib < 1 && actVerif < 1) {
				mensajeError("Al tratarse de un instrumento de medición, debe agregar al menos una actividad de tipo Calibración o Verificación");
				tabindex = 5;
				validar = false;
			}
		}
		
		if (equipo.getRequiereMantenimiento()) {
			int actMantto = 0;
			for (LaboratorioActividadEquipo lae : listaActividadesMantto) {
				if (lae.getTipoActividad().getId().equals(Tipos.TIPO_ACTIVIDAD_EQUIPO_LABORATORIO_MANTTO_PREV)
					|| lae.getTipoActividad().getId().equals(Tipos.TIPO_ACTIVIDAD_EQUIPO_LABORATORIO_MANTTO_CORR)
					|| lae.getTipoActividad().getId().equals(Tipos.TIPO_ACTIVIDAD_EQUIPO_LABORATORIO_REVISION_GARANTIA)
					|| lae.getTipoActividad().getId().equals(Tipos.TIPO_ACTIVIDAD_EQUIPO_LAB_DANNIO)
					|| lae.getTipoActividad().getId().equals(Tipos.TIPO_ACTIVIDAD_EQUIPO_LAB_MAL_FUNCIONAMIENTO)
				) {
					actMantto++;
				}
			}
			System.out.println("validar actividades de mantenimiento actCalib: "+ actMantto);
			if (actMantto < 1) {
				mensajeError("Al requerir mantenimiento, debe agregar al menos una actividad de mantenimiento.");
				tabindex = 3;
				validar = false;
			}
		}
		
		if (equipo.getEstaCalibrado() && equipo.getDondeCalibracion().getId().equals(Tipos.TIPO_LAB_METRO_DONDE_CALIBRADO_Externo)) {
			if(esNulo(equipo.getTipoDocEntidadCalibra()) || esCadenaVacia(equipo.getNumDocumentoEntidadCalibra()) || esCadenaVacia(equipo.getEntidadCalibra()))
			{
				mensajeError("Al estar calibrado por una entidad externa, debe ingresar los datos requeridos");
				tabindex = 5;
				validar = false;
			}
		}
		
		//Validar archivos obligatorios
		if(!esNulo(equipo.getOrdenDeCompra()) && !equipo.getOrdenDeCompra().equals("")){
			if(!validarTipoArchivoExiste(Tipos.TIPO_ARCHIVO_LABORATORIOS_Proceso_de_Compra)){
				mensajeError("Dado que ingresó el número de la orden de compra en la primera pestaña, debe adjuntar el soporte en la sección de archivos seleccionando el tipo de archivo PROCESO DE COMPRA");
				validar = false;
			}
		}
		
		if(equipo.getManualesOperacion()){
			if(!validarTipoArchivoExiste(Tipos.TIPO_ARCHIVO_LABORATORIOS_Manual_de_Operación)){
				mensajeError("Dado que seleccionó que el laboratorio cuenta con manuales de operacion, debe adjuntar el soporte en la sección de archivos seleccionando el tipo de archivo MANUAL DE OPERACIÓN");
				validar = false;
			}
		}
		
		if(equipo.getManualesInstalacion()){
			if(!validarTipoArchivoExiste(Tipos.TIPO_ARCHIVO_LABORATORIOS_manual_de_instalación)){
				mensajeError("Dado que seleccionó que el laboratorio cuenta con manuales de instalación, debe adjuntar el soporte en la sección de archivos seleccionando el tipo de archivo MANUAL DE INSTALACIÓN");
				validar = false;
			}
		}
		
		if(entrenamientoBrindado){
			if(!validarTipoArchivoExiste(Tipos.TIPO_ARCHIVO_LABORATORIOS_Lista_Asistencia_a_Entrenamiento)){
				mensajeError("Dado que seleccionó que el laboratorio recibió entrenamiento para el manejo del equipo, debe adjuntar el soporte en la sección de archivos seleccionando el tipo de archivo LISTA ASISTENCIA A ENTRENAMIENTO");
				validar = false;
			}
		}

		System.out.println("validar: " + validar);
		return validar;
	}
	
	public boolean validarTipoArchivoExiste(Long tipoArchivoId) {
		for (ArchivoLaboratorio archivo : listaArchivos) {
			if(archivo.getTipoArchivo().getId().equals(tipoArchivoId))
				return true;
		}
		return false;
	}

	public void cambiarSede() {

		System.out.println("cambiarSede:");
		facultadSelectItem = servicioGeneral.selectItemFacultades(equipo
				.getSede().getId());
		Dependencia facultad = new Dependencia(
				(String) facultadSelectItem[0].getValue());
		equipo.setFacultad(facultad);

		System.out.println("cambiarSede sede: " + equipo.getSede().getId());
		System.out.println("cambiarSede facultad: " + facultad.getId());

		edificioSelectItem = selectItemEdificios(equipo.getSede().getId());
		cambiarFacultad();
	}

	public void cambiarFacultad() {

		System.out.println("cambiarFacultad");

		String idFacultad = equipo.getFacultad().getId();
		departamentoSelectItem = servicioGeneral.selectItemDepartamentos(idFacultad);

		if (departamentoSelectItem.length > 0) {
			Dependencia departamento = new Dependencia((String) departamentoSelectItem[0].getValue());
			equipo.setDepartamento(departamento);
		} else {
			equipo.setDepartamento(new Dependencia());
		}

		System.out.println("cambiarFacultad depto: "+ equipo.getDepartamento().getId());
	}
	
	public void cambiarFacultadBusquedaEquipos() {

		System.out.println("cambiarFacultad");
		//String idFacultad = equipo.getFacultad().getId();
		departamentoSelectItem = servicioGeneral.selectItemDepartamentosBusquedaEquipos(facultadSeleccionada);

		if (departamentoSelectItem.length > 0) {
			Dependencia departamento = new Dependencia((String) departamentoSelectItem[0].getValue());
			departamentoSeleccionado = departamento.getId().toString();
		} else {
//			equipo.setDepartamento(new Dependencia());
		}

		System.out.println("cambiarFacultad depto: "+ departamentoSeleccionado);
	}

	public void nuevaBusqueda() {
		limpiarSesion();
	}

	public void limpiarSesion() {
		sesion.removeAttribute("ManejadorLaboratoriosInformacionGeneral");
		sesion.removeAttribute("ManejadorLaboratoriosRecursoHumano");
		sesion.removeAttribute("ManejadorLaboratoriosRiesgos");
		sesion.removeAttribute("ManejadorLaboratoriosGestion");
		
		sesion.removeAttribute("ManejadorLaboratoriosEquipos");
		sesion.removeAttribute("ManejadorMenuFormularioLaboratorios");
		if (!fromEquipos) {
			sesion.removeAttribute("Laboratorio");
			sesion.removeAttribute("soloLectura");
		}

		sesion.removeAttribute("ManejadorLaboratoriosInvestigacion");
		sesion.removeAttribute("ManejadorLaboratoriosProyectos");
		sesion.removeAttribute("ManejadorLaboratoriosDocencia");
		sesion.removeAttribute("ManejadorLaboratoriosEnsayosServicios");
		sesion.removeAttribute("ManejadorAdministrarLaboratorios");
		sesion.removeAttribute("manejadorHojaDeVidaEquipos");
	}

	public String salir() {
		limpiarSesion();
		return "misProyectos";
	}

	public void seleccionarTipos() {
		System.out.println("seleccionarTipos: - tipoArchivoSeleccionadoActividad" + tipoArchivoSeleccionadoActividad);
	}

	public void cambiarSedeBuscar() {
		System.out.println("cambiarSedeBuscar:");
		System.out.println("sedeSeleccionada:" + sedeSeleccionada);
		System.out.println("facultadSeleccionada:" + facultadSeleccionada);
		System.out.println("poseeProcesoCompra:" + poseeProcesoCompra);
		buscarFacultadSelectItem = selectItemFacultades();
	}

	public SelectItem[] selectItemSedes() {
//		if (esLaboratoriosSede) {
//			Sede sedePersonaActual;
//			// personaActual = (Persona) sesion.getAttribute("persona");
//			if (esLaboratoriosSede
//					&& personaActual instanceof InvestigadorInterno) {
//				sedePersonaActual = ((InvestigadorInterno) personaActual)
//						.getDependencia().getSede();
//				SelectItem[] sedeSelectItem = new SelectItem[1];
//				sedeSelectItem[0] = new SelectItem(sedePersonaActual.getId(),
//						sedePersonaActual.getNombre());
//				sedeSeleccionada = sedePersonaActual.getId().toString();
//				System.out.println("selectItemSedes: "
//						+ sedePersonaActual.getNombre());
//				return sedeSelectItem;
//			} else {
//				mensajeError("Error al determinar la Sede.");
//				return new SelectItem[0];
//			}
//		} else if (esLaboratoriosNacional) {
//			String hql = "from Sede WHERE id between 2 AND 9 ORDER BY id";
//			List<Sede> listaSedes = servicioGeneral.obtenerObjetos(Sede.class,
//					hql);
//			SelectItem[] sedeSelectItem = new SelectItem[listaSedes.size() + 1];
//			sedeSelectItem[0] = new SelectItem("", "Todas");
//			for (int i = 0; i < listaSedes.size(); i++) {
//				Sede sede = (Sede) listaSedes.get(i);
//				sedeSelectItem[i + 1] = new SelectItem(sede.getId().toString(),
//						sede.getNombre());
//			}
//			sedeSeleccionada = "";
//			return sedeSelectItem;
//		} else {
//			return new SelectItem[0];
//		}
		
		String hql = "from Sede WHERE id between 2 AND 9 ORDER BY id";
		List<Sede> listaSedes = servicioGeneral.obtenerObjetos(Sede.class,
				hql);
		SelectItem[] sedeSelectItem = new SelectItem[listaSedes.size() + 1];
		sedeSelectItem[0] = new SelectItem("", "Todas");
		for (int i = 0; i < listaSedes.size(); i++) {
			Sede sede = (Sede) listaSedes.get(i);
			sedeSelectItem[i + 1] = new SelectItem(sede.getId().toString(),
					sede.getNombre());
		}
		sedeSeleccionada = "";
		return sedeSelectItem;
	}

	public SelectItem[] selectItemFacultades() {

		if (sedeSeleccionada != "") {
			String hql = "from Dependencia WHERE sede = '"
					+ sedeSeleccionada
					+ "' AND (esFacultad = 'Y' OR UPPER(nombre) LIKE '%LABORATORIOS SEDE%') AND estado = 'A' ORDER BY nombre";
			System.out.println("hql:" + hql);

			List<Dependencia> listaFacultades = servicioGeneral.obtenerObjetos(
					Dependencia.class, hql);

			SelectItem[] selectItem = new SelectItem[listaFacultades.size() + 1];
			selectItem[0] = new SelectItem("", "Todas");
			departamentoSelectItem = new SelectItem[1];
			departamentoSelectItem[0] = new SelectItem("", "Todas");
			for (int i = 0; i < listaFacultades.size(); i++) {
				Dependencia facultad = listaFacultades.get(i);
				selectItem[i + 1] = new SelectItem(facultad.getId().toString(),facultad.getNombre());
			}
			// sedesellSec = "";
			return selectItem;
		} else {
			SelectItem[] selectItem = new SelectItem[1];
			selectItem[0] = new SelectItem("", "Seleccione una Sede");
			departamentoSelectItem = new SelectItem[1];
			departamentoSelectItem[0] = new SelectItem("", "Seleccione una Facultad");
			return selectItem;
		}

	}

	public void onEventSelect(ScheduleEntrySelectEvent e) {
		List<LaboratorioActividadEquipo> listaTotalActividades = new ArrayList<LaboratorioActividadEquipo>();
		listaTotalActividades.addAll(listaActividadesEquipo);
		listaTotalActividades.addAll(listaActividadesMantto);
		for (LaboratorioActividadEquipo lae : listaTotalActividades) {
			if (lae.getEventoId().equals(e.getScheduleEvent().getId())) {
				actividadSeleccionadaSchedule = lae;
			}
		}
	}

	public void editarActividad() {
		estadoActividadEditar = actividadSeleccionadaEditar.getEstadoActividad();
		fechaEA = actividadSeleccionadaEditar.getFechaEjecucion();
		fechaEAProgramada = actividadSeleccionadaEditar.getFechaActividad();
		observacionesEA = actividadSeleccionadaEditar.getObservaciones();
		costoEA = actividadSeleccionadaEditar.getCostoActividad();
		responsableEA = actividadSeleccionadaEditar.getResponsable();
		modalidadEA = actividadSeleccionadaEditar.getModalidad();
		frecuenciaEA = actividadSeleccionadaEditar.getFrecuencia();
		programarProxActividadEA = actividadSeleccionadaEditar.getProgramarProximaActividad();
		cargarSIAE();
		mensajeErrorEditarActividad = "";
	}
	
	public void cambiarEstadoActividadCalibracion(){
		
		if(tipoAccionActCalib.equals("C"))
		{
			if (nuevaActividadEquipo.getEstadoActividad().equals(LaboratorioActividadEquipo.ESTADO_EJECUTADA))
				nuevaActividadEquipo.setFechaActividad(null);
			else if (nuevaActividadEquipo.getEstadoActividad().equals(LaboratorioActividadEquipo.ESTADO_PROGRAMADA))
				nuevaActividadEquipo.setFechaEjecucion(null);
		}	
	}
	
//	public void cambiarEstadoActividadMantto(){
//		
//		if (nuevaActividadMantto.getEstadoActividad().equals(LaboratorioActividadEquipo.ESTADO_EJECUTADA))
//			nuevaActividadMantto.setFechaActividad(null);
//		else if (nuevaActividadMantto.getEstadoActividad().equals(LaboratorioActividadEquipo.ESTADO_PROGRAMADA))
//		{
//			nuevaActividadMantto.setFechaEjecucion(null);
//			nuevaActividadMantto.setManttoCodInforme(null);
//			nuevaActividadMantto.setProgramarProximaActividad(null);
//		}
//	}
	
	public void cargarSIAE() {
		// si la actividad está ejecutada, no permite cambiar el estado:
		if (estadoActividadEditar.equals(LaboratorioActividadEquipo.ESTADO_EJECUTADA)) {
			estadoActividadEditarSelectItem = new SelectItem[1];
			estadoActividadEditarSelectItem[0] = new SelectItem(
					LaboratorioActividadEquipo.ESTADO_EJECUTADA,
					LaboratorioActividadEquipo.NOMBRE_ESTADO_EJECUTADA);
		} else {
			estadoActividadEditarSelectItem = new SelectItem[2];
			estadoActividadEditarSelectItem[0] = new SelectItem(
					LaboratorioActividadEquipo.ESTADO_EJECUTADA,
					LaboratorioActividadEquipo.NOMBRE_ESTADO_EJECUTADA);
			estadoActividadEditarSelectItem[1] = new SelectItem(
					LaboratorioActividadEquipo.ESTADO_PROGRAMADA,
					LaboratorioActividadEquipo.NOMBRE_ESTADO_PROGRAMADA);
		}
	}
	
	public void cargarSIAECalib() {
		// si la actividad está ejecutada, no permite cambiar el estado:
		if (estadoEACalib.equals(LaboratorioActividadEquipo.ESTADO_EJECUTADA)) {
			estadoActividadEditarSelectItem = new SelectItem[1];
			estadoActividadEditarSelectItem[0] = new SelectItem(
					LaboratorioActividadEquipo.ESTADO_EJECUTADA,
					LaboratorioActividadEquipo.NOMBRE_ESTADO_EJECUTADA);
		} else {
			estadoActividadEditarSelectItem = new SelectItem[2];
			estadoActividadEditarSelectItem[0] = new SelectItem(
					LaboratorioActividadEquipo.ESTADO_EJECUTADA,
					LaboratorioActividadEquipo.NOMBRE_ESTADO_EJECUTADA);
			estadoActividadEditarSelectItem[1] = new SelectItem(
					LaboratorioActividadEquipo.ESTADO_PROGRAMADA,
					LaboratorioActividadEquipo.NOMBRE_ESTADO_PROGRAMADA);
		}
	}
	
	//Consumible
	public void consultarConsumible() {
		tipoAccionConsumible = "C";
	}
	
	public void editarConsumible() {
		tipoAccionConsumible = "E";
	}
	
	public void nuevoConsumible() {
		tipoAccionConsumible = "N";
	}
	
	//Reporte daño
	public void consultarReporteDanio() {
		tipoAccionReporteDanio = "C";
	}
	
	public void editarReporteDanio() {
		tipoAccionReporteDanio = "E";
	}
	
	public void nuevoReporteDanio() {
		tipoAccionReporteDanio = "N";
	}
	
	//Actualizacion software
	public void consultarActSoftware() {
		tipoAccionActSoftware = "C";
	}
	
	public void editarActSoftware() {
		tipoAccionActSoftware = "E";
	}
	
	public void nuevoActSoftware() {
		tipoAccionActSoftware = "N";
	}
	
	//Act Calib
	public void consultarActividadCalib() {
//		reiniciarActividadCalibSeleccionada();
//		nuevaActividadEquipo = consultaActividadEquipoCalib;
		tipoAccionActCalib = "C";
	}
	
	public void editarActividadCalib() {
//		reiniciarActividadCalibSeleccionada();
//		nuevaActividadEquipo = editaActividadEquipoCalib;
		tipoAccionActCalib = "E";
		
		frecuenciaEACalib = actividadSeleccionadaEditar.getFrecuencia();
		modalidadEACalib = actividadSeleccionadaEditar.getModalidad();
		estadoEACalib = actividadSeleccionadaEditar.getEstadoActividad();
		fechaProgramadaEACalib = actividadSeleccionadaEditar.getFechaActividad();
		fechaEjecutadaEACalib = actividadSeleccionadaEditar.getFechaEjecucion();
		responsableEACalib = actividadSeleccionadaEditar.getResponsable();
		costoEACalib = actividadSeleccionadaEditar.getCostoActividad();
		observacionesEACalib = actividadSeleccionadaEditar.getObservaciones();
		
		empresaEACalib = actividadSeleccionadaEditar.getCalibEmpresa();
		empresaAcreditadaEACalib = actividadSeleccionadaEditar.getCalibEmpresaAcreditada();
		codigoInformeEACalib = actividadSeleccionadaEditar.getCalibCodInforme();
		intervaloMedicionEACalib = actividadSeleccionadaEditar.getCalibIntervaloMed();
		equipoAdecuadoUsoEACalib = actividadSeleccionadaEditar.getCalibAdecuadoUso();
		equipoAdecuadoUsoNoDescEACalib = actividadSeleccionadaEditar.getCalibAdecuadoUsoNoDesc();
		programarProxActividadEACalib = actividadSeleccionadaEditar.getProgramarProximaActividad();

		cargarSIAECalib();
//		mensajeErrorEditarActividad = "";
	}
	
	public void nuevaActividadCalib() {
		tipoAccionActCalib = "N";
		reiniciarActividadCalibSeleccionada();
		cargarSIAECalib();
		
	}
	
	public void reiniciarComsumibleRepuestoSeleccionado() {
		nuevoConsumibleRepuesto = new LaboratorioEquipoConsumibleRepuesto();
	}
	
	public void reiniciarReporteDanioSeleccionado() {
		nuevoReporteDanio = new LaboratorioEquipoReporteDanio();
	}
	
	public void reiniciarActSoftwareSeleccionado() {
		nuevaActualizacionSoftware = new LaboratorioEquipoActualizacionSoftware();
	}
	
	public void reiniciarActividadManttoSeleccionada() {
		actividadSeleccionadaEditar = new LaboratorioActividadEquipo();
	}
	
	public void reiniciarActividadCalibSeleccionada() {
		nuevaActividadEquipo = new LaboratorioActividadEquipo();
		nuevaActividadEquipo.setEstadoActividad("P");
		nuevaActividadEquipo.setProgramarProximaActividad(false);
		nuevaActividadEquipo.setCalibAdecuadoUso(false);
		nuevaActividadEquipo.setCalibEmpresaAcreditada(false);
	}
	
//	public void reiniciarActividadCalibSeleccionadaConsultar() {
//		consultaActividadEquipoCalib = new LaboratorioActividadEquipo();
//	}
	
	public void borrarActividadMantto() {
		listaActividadesMantto.remove(actividadSeleccionadaEditar);
		listaActividadesManttoEliminar.add(actividadSeleccionadaEditar);
		cambiosEnActividadEquipo = true;
		reiniciarActividadManttoSeleccionada();
	}
	
	public void borrarConsumible() {
		listaConsumibles.remove(nuevoConsumibleRepuesto);
		listaConsumiblesEliminar.add(nuevoConsumibleRepuesto);
		reiniciarComsumibleRepuestoSeleccionado();
	}
	
	public void borrarReporteDanio() {
		listaReportesDanio.remove(nuevoReporteDanio);
		listaReportesDanioEliminar.add(nuevoReporteDanio);
		reiniciarReporteDanioSeleccionado();
	}
	
	public void borrarActualizacionSoftware() {
		listaActualizcionesSoftware.remove(nuevaActualizacionSoftware);
		listaActualizcionesSoftwareEliminar.add(nuevaActualizacionSoftware);
		reiniciarActSoftwareSeleccionado();
	}
	
	public void borrarActividadCalibracion() {
		listaActividadesEquipo.remove(nuevaActividadEquipo);
		listaActividadesEquipoEliminar.add(nuevaActividadEquipo);
		cambiosEnActividadEquipo = true;
		reiniciarActividadCalibSeleccionada();
	}

	public void aceptarCambiosActividad() {
		System.out.println("aceptarCambiosActividad IN #####################");
		System.out.println("aceptarCambiosActividad fechaEA:" + fechaEA);
		System.out.println("aceptarCambiosActividad costoEA:" + costoEA);
		
		boolean validar = true;
		RequestContext context = RequestContext.getCurrentInstance();
		mensajeErrorEditarActividad = "";
		
		if (estadoActividadEditar.equals(LaboratorioActividadEquipo.ESTADO_PROGRAMADA)) {
			
			//Fecha programada
			if (esNulo(fechaEAProgramada)) {
				mensajeErrorEditarActividad = "Debe seleccionar la fecha de programación";
				mensajeError("formHDVE:fechaProgActEdit", mensajeErrorEditarActividad);
				validar = false;
				return;
			}
		}
		
		if (estadoActividadEditar.equals(LaboratorioActividadEquipo.ESTADO_EJECUTADA)) {
			//Fecha ejecutada
			if (esNulo(fechaEA)) {
				mensajeErrorEditarActividad = "Debe seleccionar la fecha de ejecución";
				mensajeError("formHDVE:fechaActEdit", mensajeErrorEditarActividad);
				validar = false;
				return;
			}
		}
		
		if(!cadenaEsValorNumerico(costoEA+"")){
			mensajeErrorEditarActividad = "El costo debe ser un valor numérico.";
			mensajeError("formHDVE:costoEditarActividad", mensajeErrorEditarActividad);
			validar = false;
			return;
		}
		
		// validar costo positivo
		if (costoEA < 0) {
			mensajeErrorEditarActividad = "El costo debe ser un valor positivo.";
			mensajeError("formHDVE:costoEditarActividad", mensajeErrorEditarActividad);
			validar = false;
			return;
		}
		
		// validar
//		if (estadoActividadEditar.equals(LaboratorioActividadEquipo.ESTADO_EJECUTADA) && fechaEA == null) {
//			mensajeErrorEditarActividad = "Si se ejecutó la actividad, debe seleccionar una fecha.";
//			mensajeError("formHDVE:fechaActEdit", mensajeErrorEditarActividad);
//			// seguir mostrando el dialog:
//			context.execute("dialogEditarEstado.show();");
//
//		} else {
		
		if(validar){
			// se fijan los nuevos valores:
			if (estadoActividadEditar.equals(LaboratorioActividadEquipo.ESTADO_EJECUTADA)) {
				actividadSeleccionadaEditar.setFechaEjecucion(fechaEA);
				actividadSeleccionadaEditar.setProgramarProximaActividad(programarProxActividadEA);
			}
			
			if (estadoActividadEditar.equals(LaboratorioActividadEquipo.ESTADO_PROGRAMADA)) {
				actividadSeleccionadaEditar.setManttoCodInforme(null);
				actividadSeleccionadaEditar.setProgramarProximaActividad(null);
			}

			// actividadSeleccionadaEditar.setFechaEjecucion(fechaEA);
			actividadSeleccionadaEditar.setFechaActividad(fechaEAProgramada);
			actividadSeleccionadaEditar.setCostoActividad(costoEA);
			actividadSeleccionadaEditar.setEstadoActividad(estadoActividadEditar);
			actividadSeleccionadaEditar.setObservaciones(observacionesEA);
			actividadSeleccionadaEditar.setResponsable(responsableEA);
			actividadSeleccionadaEditar.setModalidad(modalidadEA);
			actividadSeleccionadaEditar.setFrecuencia(frecuenciaEA);
			actividadSeleccionadaEditar.setFechaActualizacion(new Date());
			actividadSeleccionadaEditar.setEditada(true);
			cambiosEnActividadEquipo = true;
			System.out.println("aceptarCambiosActividad estadoActividadEditar: "+ estadoActividadEditar);
			
			LaboratorioActividadEquipo nuevaActividadProgramada = programarSiguienteActividad(actividadSeleccionadaEditar);
			if(!esNulo(nuevaActividadProgramada))
				listaActividadesMantto.add(nuevaActividadProgramada);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actividad de mantenimiento editada con exito", "No obstante, para que esta informacion se guarde en el sistema, es necesario que pulse el boton de guardar al final del formulario");
			FacesContext.getCurrentInstance().addMessage("btnEditarActividad", msg);
			
			if(!esNulo(nuevaActividadProgramada)){
				FacesMessage msgActProg = new FacesMessage(
						FacesMessage.SEVERITY_INFO, 
							"De acuerdo a su seleccion, se ha programado el siguiente manteniento con los datos identicos a la actividad que la precede. "
							+ "Si por alguna razon desea cambiar esta actividad, puede hacerlo por medio de la opcion 'Editar' y recuerde dar clic en 'guardar' en la parte final del formulario.",
							"");
				FacesContext.getCurrentInstance().addMessage("btnEditarActividad", msgActProg);
			}
			
			actualizarCronograma();

			// cerrar dialog: onsucess="dialogEditarEstado.hide()"
			context.execute("dialogEditarEstado.hide();");
			
			//BORRAR VARIABLES
			fechaEA = null;
			costoEA = null;
			estadoActividadEditar = null;
			observacionesEA = null;
			responsableEA = null;
			modalidadEA = null;
			frecuenciaEA = null;
			programarProxActividadEA = null;
		}
		System.out.println("aceptarCambiosActividad OUT #####################");
	}
	
	public void agregarNuevaActividadMantto() {
		System.out.println("agregarNuevaActividadMantto:");
		RequestContext context = RequestContext.getCurrentInstance();
		
		boolean validar = true;
		Date fecha = nuevaActividadMantto.getFechaActividad();
		Date fechaActual = new Date();
		if (fecha == null) {
			mensajeError("formHDVE:fnaeMantto","La fecha no puede ser nula.");
			validar = false;
			return;
		}
		
		if(!cadenaEsValorNumerico(nuevaActividadMantto.getCostoActividad()+"")){
			mensajeError("formHDVE:costoNAMantto","El costo debe ser un valor numérico.");
			validar = false;
			return;
		}

		// validar costo positivo
		if (nuevaActividadMantto.getCostoActividad() < 0) {
			mensajeError("formHDVE:costoNAMantto","El costo debe ser un valor positivo.");
			validar = false;
			return;
		}

		// daño: siempre ejecutado
		Boolean esDannio = false;
		if (nuevaActividadMantto.getTipoActividad().getId()
				.equals(Tipos.TIPO_ACTIVIDAD_EQUIPO_LAB_DANNIO)
				|| nuevaActividadMantto
						.getTipoActividad()
						.getId()
						.equals(Tipos.TIPO_ACTIVIDAD_EQUIPO_LAB_MAL_FUNCIONAMIENTO)) {
			nuevaActividadMantto
					.setEstadoActividad(LaboratorioActividadEquipo.ESTADO_EJECUTADA);
			esDannio = true;
		}


		// no programar ni planear mantto correctivo:
//		if (nuevaActividadMantto.getTipoActividad().getId()
//				.equals(Tipos.TIPO_ACTIVIDAD_EQUIPO_LABORATORIO_MANTTO_CORR)
//				&& (nuevaActividadMantto.getEstadoActividad().equals(
//						LaboratorioActividadEquipo.ESTADO_PROGRAMADA) || nuevaActividadMantto
//						.getEstadoActividad().equals(
//								LaboratorioActividadEquipo.ESTADO_PLANEADA))) {
//			mensajeError("formHDVE:tabViewHV:fnaeMantto",
//					"No puede programarse ni planearse un Mantenimiento Correctivo.");
//			validar = false;
//			return;
//		}

		if (fechaActual.after(fecha)
				&& (nuevaActividadMantto.getEstadoActividad().equals(
						LaboratorioActividadEquipo.ESTADO_PROGRAMADA) || nuevaActividadMantto
						.getEstadoActividad().equals(
								LaboratorioActividadEquipo.ESTADO_PLANEADA))) {
			mensajeError(
					"formHDVE:fnaeMantto",
					"La fecha debe ser posterior a la actual, para una actividad Programada o Planeada.");
			validar = false;
			return;
		}

		if (fechaActual.before(fecha)
				&& nuevaActividadMantto.getEstadoActividad().equals(
						LaboratorioActividadEquipo.ESTADO_EJECUTADA)) {
			mensajeError("formHDVE:fnaeMantto",
					"La fecha debe ser anterior a la actual para una actividad Ejecutada.");
			validar = false;
			return;
		}

		// responsable:
		nuevaActividadMantto.setResponsable(nuevaActividadMantto.getResponsable().trim());
		if (nuevaActividadMantto.getResponsable().isEmpty()
				&& nuevaActividadMantto.getEstadoActividad().equals(
						LaboratorioActividadEquipo.ESTADO_EJECUTADA)
				&& !esDannio) {
			mensajeError("formHDVE:responsableNAMantto",
					"Responsable: campo obligatorio para actividades Ejecutadas.");
			validar = false;
		}

		if (validar) {
			System.out.println("agregarNuevaActividadMantto:");
			nuevaActividadMantto.setEquipo(equipo);

			if (nuevaActividadMantto.getEstadoActividad().equals(LaboratorioActividadEquipo.ESTADO_EJECUTADA)) {
				nuevaActividadMantto.setFechaEjecucion(fecha);
				nuevaActividadMantto.setFechaActividad(null);
			}
			
			if (nuevaActividadMantto.getEstadoActividad().equals(LaboratorioActividadEquipo.ESTADO_PROGRAMADA)) {
				nuevaActividadMantto.setManttoCodInforme(null);
				nuevaActividadMantto.setProgramarProximaActividad(false);
			}

			nuevaActividadMantto.setActiva(true);
			nuevaActividadMantto.setAlertaEnviada(false);
			listaActividadesMantto.add(nuevaActividadMantto);
			
			LaboratorioActividadEquipo nuevaActividadProgramada = programarSiguienteActividad(nuevaActividadMantto);
			if(!esNulo(nuevaActividadProgramada))
				listaActividadesMantto.add(nuevaActividadProgramada);
			
			nuevaActividadMantto = new LaboratorioActividadEquipo();
			actualizarCronograma();
			cambiosEnActividadEquipo = true;
			calcularCompletitudEquipo();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actividad de mantenimiento agregada con exito", "No obstante, para que esta informacion se guarde en el sistema, es necesario que pulse el boton de guardar al final del formulario");
			FacesContext.getCurrentInstance().addMessage("btnGuardarNuevaActividad", msg);
			
			if(!esNulo(nuevaActividadProgramada)){
				FacesMessage msgActProg = new FacesMessage(
						FacesMessage.SEVERITY_INFO, 
						"De acuerdo a su seleccion, se ha programado el siguiente manteniento con los datos identicos a la actividad que la precede. "
						+ "Si por alguna razon desea cambiar esta actividad, puede hacerlo por medio de la opcion 'Editar' y recuerde dar clic en 'guardar' en la parte final del formulario.",
						"");
				FacesContext.getCurrentInstance().addMessage("btnGuardarNuevaActividad", msgActProg);
			}
			context.execute("dialogCrearEstado.hide();");
		}
	}
	
	public void agregarNuevaActividad() {
		System.out.println("agregarNuevaActividad Calibración:");
		RequestContext context = RequestContext.getCurrentInstance();
		boolean validar = true;
		Date fechaActual = new Date();
		
//		if(!esCadenaVacia(equipo.getOrdenDeCompra()))
//		{	
//			if(!cadenaEsValorNumerico(equipo.getOrdenDeCompra()))
//			{
//				mensajeError("formHDVE:tabViewHV:ordenCompra","La orden de compra debe ser un valor numérico. Si no cuenta con este dato, deje el campo vacio.");
//				cambiarPanelesActivos("1");
//				validar = false;
//			}
//		}
		
		if(!cadenaEsValorNumerico(nuevaActividadEquipo.getCostoActividad()+"")){
			mensajeError("formHDVE:costoNA","El costo debe ser un valor numérico.");
			validar = false;
			return;
		}

		// validar costo positivo
		if (nuevaActividadEquipo.getCostoActividad() < 0) {
			mensajeError("formHDVE:costoNA","El costo debe ser un valor positivo.");
			validar = false;
			return;
		}
		
//		if(!cadenaEsValorNumerico(nuevaActividadEquipo.getCostoActividadCadena())){
//			mensajeError("formHDVE:costoNA","El costo debe ser un valor numérico.");
//			validar = false;
//			return;
//		} else {
//			// validar costo positivo
//			if (Long.parseLong(nuevaActividadEquipo.getCostoActividadCadena()) < 0) {
//				mensajeError("formHDVE:costoNA","El costo debe ser un valor positivo.");
//				validar = false;
//				return;
//			}
//		}
		
		//ESTADO_PROGRAMADA
		if(nuevaActividadEquipo.getEstadoActividad().equals(LaboratorioActividadEquipo.ESTADO_PROGRAMADA)){
			
			//Valida fecha programada (Nulo)
			if (esNulo(nuevaActividadEquipo.getFechaActividad())) {
				mensajeError("formHDVE:fnae","La fecha de programción no puede ser nula.");
				validar = false;
				return;
			}
			
			//Valida fecha programada (Antes / Despues)
			if (fechaActual.after(nuevaActividadEquipo.getFechaActividad())){
				mensajeError("formHDVE:fnae","La fecha debe ser posterior a la actual, para una actividad Programada o Planeada.");
				validar = false;
				return;
			}
		}
		
		//ESTADO_EJECUTADA
		if(nuevaActividadEquipo.getEstadoActividad().equals(LaboratorioActividadEquipo.ESTADO_EJECUTADA)){
			
			//Valida fecha ejecutada (Nulo)
			if (esNulo(nuevaActividadEquipo.getFechaEjecucion())) {
				mensajeError("formHDVE:fenae","La fecha de ejecución no puede ser nula.");
				validar = false;
				return;
			}
			
			//Valida fecha ejecutada (Antes / Despues)
			if (fechaActual.before(nuevaActividadEquipo.getFechaEjecucion())) {
				mensajeError("formHDVE:fenae","La fecha debe ser anterior a la actual para una actividad Ejecutada.");
				validar = false;
				return;
			}
			
			// Valida empresa calib
			if(esNulo(nuevaActividadEquipo.getCalibEmpresa()) || nuevaActividadEquipo.getCalibEmpresa().equals("")){
				mensajeError("formHDVE:empresaCalibNombre","Debe indicar el nombre de la empresa que realizó la calibración");
				validar = false;
				return;
			}
			
			//Valida intervalo medición calib
			if(nuevaActividadEquipo.getCalibIntervaloMed().isEmpty()){
				mensajeError("formHDVE:intMedCalib","Debe indicar el intervalo de medición de calibración");
				validar = false;
				return;
			}
			
			//Valida responsable
			if (nuevaActividadEquipo.getResponsable().isEmpty()) {
				mensajeError("formHDVE:responsableNA","Responsable: campo obligatorio para actividades Ejecutadas.");
				validar = false;
			}
		}

		if (validar) {

			if (nuevaActividadEquipo.getEstadoActividad().equals(LaboratorioActividadEquipo.ESTADO_PROGRAMADA)) {
				nuevaActividadEquipo.setCalibAdecuadoUso(null);
				nuevaActividadEquipo.setCalibAdecuadoUsoNoDesc(null);
				nuevaActividadEquipo.setCalibCodInforme(null);
				nuevaActividadEquipo.setCalibEmpresa(null);
				nuevaActividadEquipo.setCalibEmpresaAcreditada(null);
				nuevaActividadEquipo.setCalibIntervaloMed(null);
				nuevaActividadEquipo.setFechaEjecucion(null);
			}
			
			//Programar próximo mantenimiento
			LaboratorioActividadEquipo nuevaActividadProgramada = programarSiguienteActividad(nuevaActividadEquipo);
			if(!esNulo(nuevaActividadProgramada))
				listaActividadesEquipo.add(nuevaActividadProgramada);
			
			if(tipoAccionActCalib.equals("N")){
				nuevaActividadEquipo.setEquipo(equipo);
				nuevaActividadEquipo.setActiva(true);
				nuevaActividadEquipo.setAlertaEnviada(false);
				nuevaActividadEquipo.setEditada(false);
				
				listaActividadesEquipo.add(nuevaActividadEquipo);
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actividad se ha agregado con exito", "No obstante, para que esta informacion se guarde en el sistema, es necesario que pulse el boton de guardar al final del formulario");
				FacesContext.getCurrentInstance().addMessage("btnGuardarNuevaActividadCalib", msg);
							
			} else {
				nuevaActividadEquipo.setFechaActualizacion(new Date());
				nuevaActividadEquipo.setEditada(true);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actividad se ha editado con exito", "No obstante, para que esta informacion se guarde en el sistema, es necesario que pulse el boton de guardar al final del formulario");
				FacesContext.getCurrentInstance().addMessage("btnGuardarNuevaActividadCalib", msg);
			}
			
			//Envía notificación siguiente actividad programada automaticamente
			if(!esNulo(nuevaActividadProgramada)){
				FacesMessage msgActProg = new FacesMessage(
						FacesMessage.SEVERITY_INFO, 
						"De acuerdo a su seleccion, se ha programado la siguiente actividad con los datos iguales a la actividad previa. "
						+ "Si por alguna razon desea cambiar los datos de esta actividad, puede hacerlo por medio de la opcion 'editar' y recuerde dar clic en 'guardar' en la parte final del formulario.'",
						"");
				FacesContext.getCurrentInstance().addMessage("btnGuardarNuevaActividadCalib", msgActProg);
			}
			
			nuevaActividadEquipo = new LaboratorioActividadEquipo();
			actualizarCronograma();
			cambiosEnActividadEquipo = true;
			calcularCompletitudEquipo();
			
			context.execute("dialogCrearActCalib.hide();");
		}

	}
	
	public void agregarEditarActividad() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean validar = true;
		Date fechaActual = new Date();
		
		actividadSeleccionadaEditar.setFrecuencia(frecuenciaEACalib);
		actividadSeleccionadaEditar.setModalidad(modalidadEACalib);
		actividadSeleccionadaEditar.setEstadoActividad(estadoEACalib);
		actividadSeleccionadaEditar.setFechaActividad(fechaProgramadaEACalib);
		actividadSeleccionadaEditar.setFechaEjecucion(fechaEjecutadaEACalib);
		actividadSeleccionadaEditar.setResponsable(responsableEACalib);
		actividadSeleccionadaEditar.setCostoActividad(costoEACalib);
		actividadSeleccionadaEditar.setObservaciones(observacionesEACalib);
		
		actividadSeleccionadaEditar.setCalibEmpresa(empresaEACalib);
		actividadSeleccionadaEditar.setCalibEmpresaAcreditada(empresaAcreditadaEACalib);
		actividadSeleccionadaEditar.setCalibCodInforme(codigoInformeEACalib);
		actividadSeleccionadaEditar.setCalibIntervaloMed(intervaloMedicionEACalib);
		actividadSeleccionadaEditar.setCalibAdecuadoUso(equipoAdecuadoUsoEACalib);
		actividadSeleccionadaEditar.setCalibAdecuadoUsoNoDesc(equipoAdecuadoUsoNoDescEACalib);
		actividadSeleccionadaEditar.setProgramarProximaActividad(programarProxActividadEACalib);

		actividadSeleccionadaEditar.setFechaActualizacion(new Date());
		actividadSeleccionadaEditar.setEditada(true);
		
		if(!cadenaEsValorNumerico(actividadSeleccionadaEditar.getCostoActividad()+"")){
			mensajeError("formHDVE:costoNAEdit","El costo debe ser un valor positivo.");
			validar = false;
			return;
		}

		// validar costo positivo
		if (actividadSeleccionadaEditar.getCostoActividad() < 0) {
			mensajeError("formHDVE:costoNAEdit","El costo debe ser un valor positivo.");
			validar = false;
			return;
		}
		
		//ESTADO_PROGRAMADA
		if(actividadSeleccionadaEditar.getEstadoActividad().equals(LaboratorioActividadEquipo.ESTADO_PROGRAMADA)){
			
			//Valida fecha programada (Nulo)
			if (esNulo(actividadSeleccionadaEditar.getFechaActividad())) {
				mensajeError("formHDVE:fnaeEdit","La fecha de programción no puede ser nula.");
				validar = false;
				return;
			}
			
			//Valida fecha programada (Antes / Despues)
			if (fechaActual.after(actividadSeleccionadaEditar.getFechaActividad())){
				mensajeError("formHDVE:fnaeEdit","La fecha debe ser posterior a la actual, para una actividad Programada o Planeada.");
				validar = false;
				return;
			}
		}
		
		//ESTADO_EJECUTADA
		if(actividadSeleccionadaEditar.getEstadoActividad().equals(LaboratorioActividadEquipo.ESTADO_EJECUTADA)){
			
			//Valida fecha ejecutada (Nulo)
			if (esNulo(actividadSeleccionadaEditar.getFechaEjecucion())) {
				mensajeError("formHDVE:fenaeEdit","La fecha de ejecución no puede ser nula.");
				validar = false;
				return;
			}
			
			//Valida fecha ejecutada (Antes / Despues)
			if (fechaActual.before(actividadSeleccionadaEditar.getFechaEjecucion())) {
				mensajeError("formHDVE:fenaeEdit","La fecha debe ser anterior a la actual para una actividad Ejecutada.");
				validar = false;
				return;
			}
			
			// Valida empresa calib
			if(esNulo(actividadSeleccionadaEditar.getCalibEmpresa()) || actividadSeleccionadaEditar.getCalibEmpresa().equals("")){
				mensajeError("formHDVE:empresaCalibNombreEdit","Debe indicar el nombre de la empresa que realizó la calibración");
				validar = false;
				return;
			}
			
			//Valida intervalo medición calib
			if(actividadSeleccionadaEditar.getCalibIntervaloMed().isEmpty()){
				mensajeError("formHDVE:intMedCalibEdit","Debe indicar el intervalo de medición de calibración");
				validar = false;
				return;
			}
			
			//Valida responsable
			if (actividadSeleccionadaEditar.getResponsable().isEmpty()) {
				mensajeError("formHDVE:responsableNAEdit","Responsable: campo obligatorio para actividades Ejecutadas.");
				validar = false;
			}
		}

		if (validar) {
			
			//Programar próximo mantenimiento
			LaboratorioActividadEquipo nuevaActividadProgramada = programarSiguienteActividad(actividadSeleccionadaEditar);
			if(!esNulo(nuevaActividadProgramada))
				listaActividadesEquipo.add(nuevaActividadProgramada);
			
			actividadSeleccionadaEditar.setFechaActualizacion(new Date());
			actividadSeleccionadaEditar.setEditada(true);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actividad se ha editado con exito", "No obstante, para que esta informacion se guarde en el sistema, es necesario que pulse el boton de guardar al final del formulario");
			FacesContext.getCurrentInstance().addMessage("btnGuardarNuevaActividadCalibEdit", msg);
			
			//Envía notificación siguiente actividad programada automaticamente
			if(!esNulo(nuevaActividadProgramada)){
				FacesMessage msgActProg = new FacesMessage(
						FacesMessage.SEVERITY_INFO, 
						"De acuerdo a su seleccion, se ha programado la siguiente actividad con los datos iguales a la actividad previa. "
						+ "Si por alguna razon desea cambiar los datos de esta actividad, puede hacerlo por medio de la opcion 'editar' y recuerde dar clic en 'guardar' en la parte final del formulario.'",
						"");
				FacesContext.getCurrentInstance().addMessage("btnGuardarNuevaActividadCalibEdit", msgActProg);
			}
			
			actividadSeleccionadaEditar = new LaboratorioActividadEquipo();
			actualizarCronograma();
			cambiosEnActividadEquipo = true;
			calcularCompletitudEquipo();
			
			//BORRAR VARIABLES
			frecuenciaEACalib = null;
			modalidadEACalib = null; 
			estadoEACalib = null;
			fechaProgramadaEACalib = null;
			fechaEjecutadaEACalib = null;
			responsableEACalib = null;
			costoEACalib = null;
			observacionesEACalib = null;
			
			empresaEACalib = null;
			empresaAcreditadaEACalib = null;
			codigoInformeEACalib = null;
			intervaloMedicionEACalib = null;
			equipoAdecuadoUsoEACalib = null;
			equipoAdecuadoUsoNoDescEACalib = null;
			programarProxActividadEACalib = null;
			
			context.execute("dialogEditarActCalib.hide();");
		}
	}
	
	public void agregarNuevoConsumibleRepuesto() {
		RequestContext context = RequestContext.getCurrentInstance();
		
		boolean validar = true;
		
		if(esNulo(nuevoConsumibleRepuesto.getNombre()) || nuevoConsumibleRepuesto.getNombre().equals("")){
			mensajeError("formHDVE:nombreConsumible","El nombre es un campo obligatorio");
			validar = false;
			return;
		}
		
		if(esNulo(nuevoConsumibleRepuesto.getReferencia()) || nuevoConsumibleRepuesto.getReferencia().equals("")){
			mensajeError("formHDVE:referenciaConsumible","La referencia es un campo obligatorio");
			validar = false;
			return;
		}
		
		if(esNulo(nuevoConsumibleRepuesto.getCantidad()) || nuevoConsumibleRepuesto.getCantidad().equals("")){
			mensajeError("formHDVE:cantidadConsumible","La cantidad es un campo obligatorio");
			validar = false;
			return;
		}
		
		if(esNulo(nuevoConsumibleRepuesto.getTiempoEstimado()) || nuevoConsumibleRepuesto.getTiempoEstimado().equals("")){
			mensajeError("formHDVE:tiempoEstimadoConsumible","El tiempo estiimado es un campo obligatorio");
			validar = false;
			return;
		}
		
		// validar cantidad positiva
		if (nuevoConsumibleRepuesto.getCantidad() < 0) {
			mensajeError("formHDVE:cantidadConsumible","La cantidad debe ser un valor positivo.");
			validar = false;
			return;
		}
		
		// validar tiempo estimado positivo
		if (nuevoConsumibleRepuesto.getTiempoEstimado() < 0) {
			mensajeError("formHDVE:tiempoEstimadoConsumible","El tiempo estimado debe ser un valor positivo.");
			validar = false;
			return;
		}
		
		if (validar) {
			if(tipoAccionConsumible.equals("N")){
				
				nuevoConsumibleRepuesto.setEquipo(equipo);
				nuevoConsumibleRepuesto.setFechaRegistro(new Date());
				nuevoConsumibleRepuesto.setFechaActualizacion(new Date());
				
				listaConsumibles.add(nuevoConsumibleRepuesto);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Consumible / Repuesto agregado satisfactoriamente", "");
				FacesContext.getCurrentInstance().addMessage("btnGuardarNuevoConsumible", msg);
			} else {
				nuevoConsumibleRepuesto.setFechaActualizacion(new Date());
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Consumible / Repuesto editado satisfactoriamente", "");
				FacesContext.getCurrentInstance().addMessage("btnGuardarNuevoConsumible", msg);
			}
		}
		context.execute("dialogCrearConsumible.hide();");
	}
	
	public void agregarNuevoReporteDanio() {
		RequestContext context = RequestContext.getCurrentInstance();
		
		boolean validar = true;
		
		if(esNulo(nuevoReporteDanio.getFechaReporte()) || nuevoReporteDanio.getFechaReporte().equals("")){
			mensajeError("formHDVE:fechaReporte","La fecha es un campo obligatorio");
			validar = false;
			return;
		}
		
		if(esNulo(nuevoReporteDanio.getDescripcion()) || nuevoReporteDanio.getDescripcion().equals("")){
			mensajeError("formHDVE:descripcionReporteDanio","La descripción es un campo obligatorio");
			validar = false;
			return;
		}
		
		if (validar) {
			if(tipoAccionReporteDanio.equals("N")){
				
				nuevoReporteDanio.setEquipo(equipo);
				nuevoReporteDanio.setFechaRegistro(new Date());
				nuevoReporteDanio.setFechaActualizacion(new Date());
				
				listaReportesDanio.add(nuevoReporteDanio);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reporte de falla o mal funcionamiento agregado satisfactoriamente", "");
				FacesContext.getCurrentInstance().addMessage("btnGuardarNuevoReporteDanio", msg);
			} else {
				nuevoReporteDanio.setFechaActualizacion(new Date());
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reporte de falla o mal funcionamiento editado satisfactoriamente", "");
				FacesContext.getCurrentInstance().addMessage("btnGuardarNuevoReporteDanio", msg);
			}
		}
		context.execute("dialogCrearReporte.hide();");
	}
	
	public void agregarNuevaActualizacionSoftware() {
		RequestContext context = RequestContext.getCurrentInstance();
		
		boolean validar = true;
		
		if(esNulo(nuevaActualizacionSoftware.getTipo()) || nuevaActualizacionSoftware.getTipo().equals(0L)){
			mensajeError("formHDVE:listaTiposActSoftware","El tipo es un campo obligatorio");
			validar = false;
			return;
		}
		
		if(esNulo(nuevaActualizacionSoftware.getVersionAnterior()) || nuevaActualizacionSoftware.getVersionAnterior().equals("")){
			mensajeError("formHDVE:versionAntActSoftware","La versión anterior es un campo obligatorio");
			validar = false;
			return;
		}
		
		if(esNulo(nuevaActualizacionSoftware.getVersionNueva()) || nuevaActualizacionSoftware.getVersionNueva().equals("")){
			mensajeError("formHDVE:versionNuevaActSoftware","La versión nueva es un campo obligatorio");
			validar = false;
			return;
		}
		
		if(esNulo(nuevaActualizacionSoftware.getFechaActualizacionSoftware()) || nuevaActualizacionSoftware.getFechaActualizacionSoftware().equals("")){
			mensajeError("formHDVE:fechaActSoftware","La fecha es un campo obligatorio");
			validar = false;
			return;
		}
		
		if(esNulo(nuevaActualizacionSoftware.getResponsable()) || nuevaActualizacionSoftware.getResponsable().equals("")){
			mensajeError("formHDVE:responsableActSoftware","El responsable es un campo obligatorio");
			validar = false;
			return;
		}
		
		if (validar) {
			if(tipoAccionActSoftware.equals("N")){
				
				nuevaActualizacionSoftware.setEquipo(equipo);
				nuevaActualizacionSoftware.setFechaRegistro(new Date());
				nuevaActualizacionSoftware.setFechaActualizacion(new Date());
				
				listaActualizcionesSoftware.add(nuevaActualizacionSoftware);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizacion de software o firmware agregada satisfactoriamente", "");
				FacesContext.getCurrentInstance().addMessage("btnGuardarActSoftware", msg);
			} else {
				nuevaActualizacionSoftware.setFechaActualizacion(new Date());
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizacion de software o firmware editada satisfactoriamente", "");
				FacesContext.getCurrentInstance().addMessage("btnGuardarActSoftware", msg);
			}
		}
		context.execute("dialogCrearActSoftware.hide();");
	}
	
	public LaboratorioActividadEquipo programarSiguienteActividad(LaboratorioActividadEquipo actividadOriginal){
		
		try {
			if(actividadOriginal.getEstadoActividad().equals(LaboratorioActividadEquipo.ESTADO_EJECUTADA)
					&& actividadOriginal.getProgramarProximaActividad()
					&& !actividadOriginal.getFrecuencia().getId().equals(Tipos.TIPOS_FRECUENCIA_MANTENIMIENTO_NO_APLICA))
			{
				LaboratorioActividadEquipo nuevaActividadProgramada = new LaboratorioActividadEquipo();
				nuevaActividadProgramada = (LaboratorioActividadEquipo) actividadOriginal.clone();
				nuevaActividadProgramada.setId(null);
				nuevaActividadProgramada.setFechaRegistro(new Date());
				nuevaActividadProgramada.setEstadoActividad(LaboratorioActividadEquipo.ESTADO_PROGRAMADA);
				nuevaActividadProgramada.setProgramarProximaActividad(false);
				nuevaActividadProgramada.setFechaActividad(
						calcularFechaSiguienteActividad(
						!esNulo(actividadOriginal.getFechaEjecucion()) ? actividadOriginal.getFechaEjecucion() : actividadOriginal.getFechaActividad(),
								actividadOriginal.getFrecuencia().getId()
					)
				);
				nuevaActividadProgramada.setFechaEjecucion(null);
				nuevaActividadProgramada.setAlertaEnviada(false);
				nuevaActividadProgramada.setFechaAlerta(null);
				nuevaActividadProgramada.setTextoAlerta(null);
				nuevaActividadProgramada.setEditada(false);
				
				//Mantto
				nuevaActividadProgramada.setManttoCodInforme(null);
				
				//Calib
				nuevaActividadProgramada.setCalibEmpresa(null);
				nuevaActividadProgramada.setCalibEmpresaAcreditada(null);
				nuevaActividadProgramada.setCalibCodInforme(null);
				nuevaActividadProgramada.setCalibIntervaloMed(null);
				nuevaActividadProgramada.setCalibAdecuadoUso(null);
				nuevaActividadProgramada.setCalibAdecuadoUsoNoDesc(null);
				
				//listaActividadesMantto.add(nuevaActividadProgramada);
				return nuevaActividadProgramada;
			} else
				return null;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Date calcularFechaSiguienteActividad(Date fechaOriginal, Long idTipoFecuencia) {
		Integer meses = 0;
		Date fechaNueva = new Date();
		Integer id = idTipoFecuencia != null ? idTipoFecuencia.intValue() : null;
		
		switch(id) {
		  case 27:
			  meses = 1;
		    break;
		  case 380:
			  meses = 2;
		    break;
		  case 381:
			  meses = 3;
		    break;
		  case 157:
			  meses = 4;
		    break;
		  case 28:
			  meses = 6;
		    break;
		  case 58:
			  meses = 12;
		    break;
		  case 382:
			  meses = 24;
		    break;
		  case 383:
			  meses = 36;
		    break;
		  case 384:
			  meses = 48;
		    break;
		  case 385:
			  meses = 60;
		    break;  
		  default:
			  meses = 0;
		}
		
		if(meses != 0){
			Calendar c = Calendar.getInstance();
	        c.setTime(fechaOriginal);
	        c.add(Calendar.MONTH, meses);
	        fechaNueva = c.getTime();
		}
		
		return fechaNueva;
	}

	/**
	 * genera el SelectItem para el dialog EditarActividad, de acuerdo al estado
	 * actual de la actividad.
	 */
	
//	public void actualizarListaEstadosActividad(){
//		
//	}
//	
//	public void cargarSIAE() {
//		// si la actividad está ejecutada, no permite cambiar el estado:
//		if (estadoActividadEditar.equals(LaboratorioActividadEquipo.ESTADO_EJECUTADA)) {
//			estadoActividadEditarSelectItem = new SelectItem[1];
//			estadoActividadEditarSelectItem[0] = new SelectItem(
//					LaboratorioActividadEquipo.ESTADO_EJECUTADA,
//					LaboratorioActividadEquipo.NOMBRE_ESTADO_EJECUTADA);
//			System.out.println("cargarSIAE: solo PROGRAMADA");
//		} else {
//			estadoActividadEditarSelectItem = new SelectItem[2];
//			estadoActividadEditarSelectItem[0] = new SelectItem(
//					LaboratorioActividadEquipo.ESTADO_EJECUTADA,
//					LaboratorioActividadEquipo.NOMBRE_ESTADO_EJECUTADA);
//			estadoActividadEditarSelectItem[1] = new SelectItem(
//					LaboratorioActividadEquipo.ESTADO_PROGRAMADA,
//					LaboratorioActividadEquipo.NOMBRE_ESTADO_PROGRAMADA);
//			System.out.println("cargarSIAE: EJECUTADA, PROGRAMADA");
//		}
//	}

//	public StreamedContent getImagen() {
//		FacesContext context = FacesContext.getCurrentInstance();
//		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
//			// So, we're rendering the view. Return a stub StreamedContent so
//			// that it will generate right URL.
//			return new DefaultStreamedContent();
//		} else {
//			// So, browser is requesting the image. Get ID value from actual
//			// request param.
//			String id = context.getExternalContext().getRequestParameterMap()
//					.get("id");
//
//			String subCarpeta = obtenerSubCarpetaArchivo(new Long(id));
//			System.out.println("getImagen subCarpeta: " + subCarpeta);
//			String nombreArchivo = ArchivoLaboratorio.DIRECTORIO_ARCHIVOS
//					+ subCarpeta + "//" + id;
//			System.out.println("getImagen nombreArchivo: " + nombreArchivo);
//			File file = new File(nombreArchivo);
//			System.out.println("getImagen :exists() " + file.exists()
//					+ file.getName());
//
//			try {
//				image = new DefaultStreamedContent(new FileInputStream(file),
//						"image/jpeg");
//				return image;
//			} catch (FileNotFoundException e) {
//
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}

	public void subirArchivoPlanMantto(FileUploadEvent event) {
		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,new Tipos(Tipos.TIPO_ARCHIVO_LABORATORIOS_PLAN_MANTTO));
		archivoNuevo.setIdDetalle(equipo.getId());
		servicioGeneral.insertarObjetoConIdLong(archivoNuevo,archivoNuevo.getId());
		listaArchivos.addFirst(archivoNuevo);
		cargarListaArchivosPlanMantto();
		cargaListaFotosEquipo();
	}
	
//	public void subirArchivoAct(FileUploadEvent event) {
//		System.out.println("tipoArchivoActividadSeleccionado " + tipoArchivoActividadSeleccionado);
//		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,tipoArchivoActividadSeleccionado);
//		servicioGeneral.guardarObjeto(actividadSeleccionadaEditar);
//		if (archivoNuevo != null) {
//			archivoNuevo.setActividad(actividadSeleccionadaEditar);
//			listaArchivosActividades.add(archivoNuevo);
//			servicioGeneral.insertarObjetoConIdLong(archivoNuevo, archivoNuevo.getId());
//			listaArchivosActividadVista.add(archivoNuevo);
//		} else {
//			mensajeError("Error al subir archivo.");
//			System.out.println("Error");
//		}
//	}
	
	public void subirArchivoActMantto(FileUploadEvent event) {
		System.out.println("tipoArchivoSeleccionadoActividad " + tipoArchivoSeleccionadoActividad);
		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,tipoArchivoSeleccionadoActividad);
		servicioGeneral.guardarObjeto(actividadSeleccionadaEditar);
		if (archivoNuevo != null) {
			archivoNuevo.setActividad(actividadSeleccionadaEditar);
			listaArchivosActividades.add(archivoNuevo);
			servicioGeneral.insertarObjetoConIdLong(archivoNuevo, archivoNuevo.getId());
			listaArchivosActividadVista.add(archivoNuevo);
		} else {
			mensajeError("Error al subir archivo.");
			System.out.println("Error");
		}
	}
	
	public void subirArchivoActCalib(FileUploadEvent event) {
		System.out.println("tipoArchivoSeleccionadoActividad " + tipoArchivoSeleccionadoActividadCalib);
		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,tipoArchivoSeleccionadoActividadCalib);
		servicioGeneral.guardarObjeto(actividadSeleccionadaEditar);
		if (archivoNuevo != null) {
			archivoNuevo.setActividad(actividadSeleccionadaEditar);
			listaArchivosActividadesCalib.add(archivoNuevo);
			servicioGeneral.insertarObjetoConIdLong(archivoNuevo, archivoNuevo.getId());
			listaArchivosActividadVistaCalib.add(archivoNuevo);
		} else {
			mensajeError("Error al subir archivo.");
			System.out.println("Error");
		}
	}
	
//	public void subirArchivoActMantto(FileUploadEvent event) {
//		UploadedFile archivoSubir = event.getFile();
//		String nombreArchivo = archivoSubir.getFileName();
//		System.out.println("nombreArchivo:" + nombreArchivo);
//
//		String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1, nombreArchivo.length()).toUpperCase();
//		System.out.println("extensión:" + extension);
//
//		System.out.println("subirArchivoActMantto tipoArchivoSeleccionadoid:"+ tipoArchivoSeleccionadoActividad.getId());
//		System.out.println("subirArchivoActMantto tipoArchivoSeleccionadonombre:"+ tipoArchivoSeleccionadoActividad.getNombre());
//		tipoArchivoSeleccionadoActividad = (Tipos) servicioGeneral.obtenerObjeto(new Tipos(), tipoArchivoSeleccionadoActividad.getId());
//		System.out.println("subirArchivoActMantto tipoArchivoSeleccionadoid:"+ tipoArchivoSeleccionadoActividad.getId());
//		System.out.println("subirArchivoActMantto tipoArchivoSeleccionadonombre:"+ tipoArchivoSeleccionadoActividad.getNombre());
//		
//		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,tipoArchivoSeleccionadoActividad);
//		servicioGeneral.guardarObjeto(actividadSeleccionadaEditar);
//		if (archivoNuevo != null) {
//			archivoNuevo.setActividad(actividadSeleccionadaEditar);
//			listaArchivosActividades.add(archivoNuevo);
//			servicioGeneral.insertarObjetoConIdLong(archivoNuevo, archivoNuevo.getId());
//			listaArchivosActividadVista.add(archivoNuevo);
//		} else {
//			mensajeError("Error al subir archivo.");
//			System.out.println("Error");
//		}		
//	}
	
	public void subirArchivoReporteDanio(FileUploadEvent event) {
		Tipos tipoArchivo = obtenerTipoXid(Tipos.TIPOS_ARCHIVOS_EQUIPO_REPORTE_DANIO_Reporte_danio);
		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,tipoArchivo);
		servicioGeneral.guardarObjeto(nuevoReporteDanio);
		if (archivoNuevo != null) {
			archivoNuevo.setReporteDanio(nuevoReporteDanio);
			listaArchivosReporteDanio.add(archivoNuevo);
			servicioGeneral.insertarObjetoConIdLong(archivoNuevo, archivoNuevo.getId());
			listaArchivosReporteDanioVista.add(archivoNuevo);
		} else {
			mensajeError("Error al subir archivo.");
			System.out.println("Error");
		}
	}

	public void subirArchivo(FileUploadEvent event) {
		UploadedFile archivoSubir = event.getFile();
		String nombreArchivo = archivoSubir.getFileName();
		System.out.println("nombreArchivo:" + nombreArchivo);

		String extension = nombreArchivo.substring(
				nombreArchivo.lastIndexOf(".") + 1, nombreArchivo.length())
				.toUpperCase();
		System.out.println("extensión:" + extension);

		if (tipoArchivoSeleccionado.getId().equals(
				Tipos.TIPO_ARCHIVO_LABORATORIOS_FOTOGRAFIA)
				&& !(extension.startsWith("JP"))) {
			mensajeError("formHDVE:panelArchivos:somTipoArchivo",
					"La fotografía debe ser formato jpg.");
			return;
		}

		System.out.println("subirArchivo tipoArchivoSeleccionadoid:"+ tipoArchivoSeleccionado.getId());
		System.out.println("subirArchivo tipoArchivoSeleccionadonombre:"+ tipoArchivoSeleccionado.getNombre());
		tipoArchivoSeleccionado = (Tipos) servicioGeneral.obtenerObjeto(new Tipos(), tipoArchivoSeleccionado.getId());
		System.out.println("subirArchivo tipoArchivoSeleccionadoid:"+ tipoArchivoSeleccionado.getId());
		System.out.println("subirArchivo tipoArchivoSeleccionadonombre:"+ tipoArchivoSeleccionado.getNombre());

		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,tipoArchivoSeleccionado);
		archivoNuevo.setIdDetalle(equipo.getId());
		servicioGeneral.insertarObjetoConIdLong(archivoNuevo,archivoNuevo.getId());
		listaArchivos.add(archivoNuevo);
		cargaListaFotosEquipo();
		
	}

	public void descargarArchivo() {
		descargarArchivoLaboratorios(archivoLaboratorioSeleccionado);
	}
	
	public void descargarArchivoActividad() {
//		descargarArchivoActividadLaboratorios(archivoLaboratorioActividadSeleccionado);
		descargarArchivoLaboratorios(archivoLaboratorioActividadSeleccionado);
	}
	
	public void descargarArchivoReporteDanio() {
		descargarArchivoLaboratorios(archivoLaboratorioReporteDanioSeleccionado);
	}
	
	public void descargarArchivoActividadCalib() {
		descargarArchivoLaboratorios(archivoLaboratorioActividadSeleccionadoCalib);
	}

//	public void subirArchivoBORRAR_METODO(FileUploadEvent event) {
//		UploadedFile archivoSubir = event.getFile();
//		System.out.println("subirArchivo:" + archivoSubir.getFileName());
//		Long id = null;
//		if (archivoSubir != null) {
//
//			String nombreArchivo = archivoSubir.getFileName();
//			String extension = nombreArchivo.substring(
//					nombreArchivo.lastIndexOf(".") + 1, nombreArchivo.length())
//					.toUpperCase();
//			System.out.println("subirArchivo extension:" + extension);
//
//			if (tipoArchivoSeleccionado.getId().equals(
//					Tipos.TIPO_ARCHIVO_LABORATORIOS_FOTOGRAFIA)
//					&& !(extension.startsWith("JP"))) {
//				mensajeError("formHDVE:somTipoArchivo",
//						"La fotografía debe ser formato jpg.");
//				return;
//			}
//
//			ArchivoLaboratorio archivoNuevo = new ArchivoLaboratorio();
//			archivoNuevo.setNombreArchivo(archivoSubir.getFileName());
//			archivoNuevo.setFechaArchivo(new Date());
//			archivoNuevo.setIdDetalle(equipo.getId());
//
//			System.out.println("subirArchivo tipoArchivoSeleccionadoid:"
//					+ tipoArchivoSeleccionado.getId());
//			System.out.println("subirArchivo tipoArchivoSeleccionadonombre:"
//					+ tipoArchivoSeleccionado.getNombre());
//			tipoArchivoSeleccionado = (Tipos) servicioGeneral.obtenerObjeto(
//					new Tipos(), tipoArchivoSeleccionado.getId());
//			System.out.println("subirArchivo tipoArchivoSeleccionadoid:"
//					+ tipoArchivoSeleccionado.getId());
//			System.out.println("subirArchivo tipoArchivoSeleccionadonombre:"
//					+ tipoArchivoSeleccionado.getNombre());
//
//			archivoNuevo.setTipoArchivo(tipoArchivoSeleccionado);
//			servicioGeneral.guardarObjeto(archivoNuevo);
//			id = archivoNuevo.getId();
//			if (id != null) {
//				String directorio;
//				directorio = ArchivoLaboratorio.DIRECTORIO_ARCHIVOS;
//				System.out.println("directorio:" + directorio);
//				String fileName = directorio + archivoNuevo.getId();
//				System.out.println("fileName:" + fileName);
//				new File(directorio).mkdirs();
//				try {
//					InputStream in = archivoSubir.getInputstream();
//					OutputStream out = new FileOutputStream(new File(fileName));
//					int read = 0;
//					byte[] bytes = new byte[1024];
//					while ((read = in.read(bytes)) != -1) {
//						out.write(bytes, 0, read);
//					}
//					in.close();
//					out.flush();
//					out.close();
//					listaArchivos.add(archivoNuevo);
//					cargaListaFotosEquipo();
//					System.out.println("Nuevo archivo creado.");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

//	public void descargarArchivoBORRAR_METODO() {
//		FacesContext ctx = FacesContext.getCurrentInstance();
//		String path;
//		path = ArchivoLaboratorio.DIRECTORIO_ARCHIVOS;
//		path += archivoLaboratorioSeleccionado.getId();
//		System.out.println("descargarArchivo path:" + path);
//		File ficheroXLS = new File(path);
//		FileInputStream fis;
//		try {
//			fis = new FileInputStream(ficheroXLS);
//			byte[] bytes = new byte[1000];
//			int read = 0;
//			if (!ctx.getResponseComplete()) {
//				String fileName = archivoLaboratorioSeleccionado
//						.getNombreArchivo();
//				String extension = fileName.substring(
//						fileName.lastIndexOf("."), fileName.length());
//				System.out.println("descargarArchivo extension:" + extension);
//				// String contentType = "application/msword";
//				String contentType = "text/plain";
//				System.out.println("descargarArchivo contentType:"
//						+ contentType);
//				HttpServletResponse response = (HttpServletResponse) ctx
//						.getExternalContext().getResponse();
//				response.setContentType(contentType);
//				response.setHeader("Content-Disposition",
//						"attachment;filename=\"" + fileName + "\"");
//				ServletOutputStream out = response.getOutputStream();
//				while ((read = fis.read(bytes)) != -1) {
//					out.write(bytes, 0, read);
//				}
//				out.flush();
//				out.close();
//				ctx.responseComplete();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			mensajeError("Error al descargar el archivo.");
//		}
//	}

	public void eliminarArchivo() {
		if (eliminarArchivoLaboratorio(archivoLaboratorioSeleccionado.getId())) {
			listaArchivos.remove(archivoLaboratorioSeleccionado);
			servicioGeneral.eliminarObjeto(archivoLaboratorioSeleccionado);
			cargaListaFotosEquipo();
			cargarListaArchivosPlanMantto();

		} else {
			System.out.println("Delete operation is failed.");
		}
	}
	
	public void eliminarArchivoActividad() {
		if (eliminarArchivoLaboratorio(archivoLaboratorioActividadSeleccionado.getId())) {
			listaArchivosActividades.remove(archivoLaboratorioActividadSeleccionado);
			listaArchivosActividadVista.remove(archivoLaboratorioActividadSeleccionado);		
			servicioGeneral.eliminarObjeto(archivoLaboratorioActividadSeleccionado);
		} else
			System.out.println("Delete operation is failed: " + archivoLaboratorioActividadSeleccionado.getId());
	}
	
	public void eliminarArchivoReporteDanio() {
		if (eliminarArchivoLaboratorio(archivoLaboratorioReporteDanioSeleccionado.getId())) {
			listaArchivosReporteDanio.remove(archivoLaboratorioReporteDanioSeleccionado);
			listaArchivosReporteDanioVista.remove(archivoLaboratorioReporteDanioSeleccionado);		
			servicioGeneral.eliminarObjeto(archivoLaboratorioReporteDanioSeleccionado);
		} else
			System.out.println("Delete operation is failed: " + archivoLaboratorioReporteDanioSeleccionado.getId());
	}
	
	public void eliminarArchivoActividadCalib() {
		if (eliminarArchivoLaboratorio(archivoLaboratorioActividadSeleccionadoCalib.getId())) {
			listaArchivosActividadesCalib.remove(archivoLaboratorioActividadSeleccionadoCalib);
			listaArchivosActividadVistaCalib.remove(archivoLaboratorioActividadSeleccionadoCalib);		
			servicioGeneral.eliminarObjeto(archivoLaboratorioActividadSeleccionadoCalib);
		} else
			System.out.println("Delete operation is failed: " + archivoLaboratorioActividadSeleccionadoCalib.getId());
	}

	public SelectItem[] selectItemEdificios(Long idSede) {
		String hql = "from Edificio WHERE sede = '" + idSede
				+ "' ORDER BY codigo";
		System.out.println("hql:" + hql);
		List<Edificio> listaEdificios = servicioGeneral.obtenerObjetos(
				Edificio.class, hql);
		SelectItem[] edificiosItem = new SelectItem[listaEdificios.size()];
		for (int i = 0; i < listaEdificios.size(); i++) {
			Edificio e = (Edificio) listaEdificios.get(i);
			edificiosItem[i] = new SelectItem(e.getId(), e.getNombreEdificio());
		}
		return edificiosItem;
	}

	/**
	 * @return the placaBuscar
	 */
	public String getPlacaBuscar() {
		return placaBuscar;
	}

	/**
	 * @param placaBuscar
	 *            the placaBuscar to set
	 */
	public void setPlacaBuscar(String placaBuscar) {
		this.placaBuscar = placaBuscar;
	}

	/**
	 * @return the listaEquiposEncontrados
	 */
	public List<LaboratorioDetalleEquipos> getListaEquiposEncontrados() {
		return listaEquiposEncontrados;
	}

	/**
	 * @return the mostrarHV
	 */
	public Boolean getMostrarHV() {
		return mostrarHV;
	}

	/**
	 * @return the equipo
	 */
	public LaboratorioDetalleEquipos getEquipo() {
		return equipo;
	}

	/**
	 * @param equipo
	 *            the equipo to set
	 */
	public void setEquipo(LaboratorioDetalleEquipos equipo) {
		this.equipo = equipo;
	}

	/**
	 * @return the equipoV
	 */
	public LaboratorioDetalleEquipos getEquipoV() {
		return equipoV;
	}

	/**
	 * @return the mecanismoAdquisicionSelectItem
	 */
	public SelectItem[] getMecanismoAdquisicionSelectItem() {
		return mecanismoAdquisicionSelectItem;
	}

	/**
	 * @return the mantenimientoSelectItem
	 */
	public SelectItem[] getMantenimientoSelectItem() {
		return mantenimientoSelectItem;
	}

	/**
	 * @return the calibracionSelectItem
	 */
	public SelectItem[] getCalibracionSelectItem() {
		return calibracionSelectItem;
	}

	/**
	 * @return the tipoDocumentoSelectItem
	 */
	public SelectItem[] getTipoDocumentoSelectItem() {
		return tipoDocumentoSelectItem;
	}

	/**
	 * @return the documentoPersona
	 */
	public String getDocumentoPersona() {
		return documentoPersona;
	}

	/**
	 * @param documentoPersona
	 *            the documentoPersona to set
	 */
	public void setDocumentoPersona(String documentoPersona) {
		this.documentoPersona = documentoPersona;
	}

	/**
	 * @return the tipoDocumentoId
	 */
	public String getTipoDocumentoId() {
		return tipoDocumentoId;
	}

	/**
	 * @param tipoDocumentoId
	 *            the tipoDocumentoId to set
	 */
	public void setTipoDocumentoId(String tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}

	/**
	 * @return the personaNombre
	 */
	public String getPersonaNombre() {
		return personaNombre;
	}

	/**
	 * @param personaNombre
	 *            the personaNombre to set
	 */
	public void setPersonaNombre(String personaNombre) {
		this.personaNombre = personaNombre;
	}

	/**
	 * @return the personaCargo
	 */
	public String getPersonaCargo() {
		return personaCargo;
	}

	/**
	 * @param personaCargo
	 *            the personaCargo to set
	 */
	public void setPersonaCargo(String personaCargo) {
		this.personaCargo = personaCargo;
	}

	/**
	 * @return the listaPersonasEntrenamiento
	 */
	public List<LaboratorioDetallePersona> getListaPersonasEntrenamiento() {
		return listaPersonasEntrenamiento;
	}

	/**
	 * @return the detallePersonaSeleccionado
	 */
	public LaboratorioDetallePersona getDetallePersonaSeleccionado() {
		return detallePersonaSeleccionado;
	}

	/**
	 * @param detallePersonaSeleccionado
	 *            the detallePersonaSeleccionado to set
	 */
	public void setDetallePersonaSeleccionado(
			LaboratorioDetallePersona detallePersonaSeleccionado) {
		this.detallePersonaSeleccionado = detallePersonaSeleccionado;
	}

	/**
	 * @return the entrenamientoBrindado
	 */
	public Boolean getEntrenamientoBrindado() {
		return entrenamientoBrindado;
	}

	/**
	 * @param entrenamientoBrindado
	 *            the entrenamientoBrindado to set
	 */
	public void setEntrenamientoBrindado(Boolean entrenamientoBrindado) {
		this.entrenamientoBrindado = entrenamientoBrindado;
	}

	/**
	 * @return the entrenamientoBrindadoSelectItem
	 */
	public SelectItem[] getEntrenamientoBrindadoSelectItem() {
		return entrenamientoBrindadoSelectItem;
	}

	/**
	 * @return the sedeSelectItem
	 */
	public SelectItem[] getSedeSelectItem() {
		return sedeSelectItem;
	}

	/**
	 * @return the facultadSelectItem
	 */
	public SelectItem[] getFacultadSelectItem() {
		return facultadSelectItem;
	}

	/**
	 * @return the departamentoSelectItem
	 */
	public SelectItem[] getDepartamentoSelectItem() {
		return departamentoSelectItem;
	}

	/**
	 * @return the edificioSelectItem
	 */
	public SelectItem[] getEdificioSelectItem() {
		return edificioSelectItem;
	}

	/**
	 * @return the esEquipoSinLaboratorio
	 */
	public Boolean getEsEquipoSinLaboratorio() {
		return esEquipoSinLaboratorio;
	}

	/**
	 * @param esEquipoSinLaboratorio
	 *            the esEquipoSinLaboratorio to set
	 */
	public void setEsEquipoSinLaboratorio(Boolean esEquipoSinLaboratorio) {
		this.esEquipoSinLaboratorio = esEquipoSinLaboratorio;
	}

	/**
	 * @return the listaArchivos
	 */
	public List<ArchivoLaboratorio> getListaArchivos() {
		return listaArchivos;
	}

	/**
	 * @return the archivoLaboratorioSeleccionado
	 */
	public ArchivoLaboratorio getArchivoLaboratorioSeleccionado() {
		return archivoLaboratorioSeleccionado;
	}

	/**
	 * @param archivoLaboratorioSeleccionado
	 *            the archivoLaboratorioSeleccionado to set
	 */
	public void setArchivoLaboratorioSeleccionado(
			ArchivoLaboratorio archivoLaboratorioSeleccionado) {
		this.archivoLaboratorioSeleccionado = archivoLaboratorioSeleccionado;
	}

	/**
	 * @return the listaFotosEquipo
	 */
	public List<ArchivoLaboratorio> getListaFotosEquipo() {
		return listaFotosEquipo;
	}

	/**
	 * @return the tipoArchivoSelectItem
	 */
	public SelectItem[] getTipoArchivoSelectItem() {
		return tipoArchivoSelectItem;
	}

	/**
	 * @return the tipoArchivoSeleccionado
	 */
	public Tipos getTipoArchivoSeleccionado() {
		return tipoArchivoSeleccionado;
	}

	/**
	 * @param tipoArchivoSeleccionado
	 *            the tipoArchivoSeleccionado to set
	 */
	public void setTipoArchivoSeleccionado(Tipos tipoArchivoSeleccionado) {
		this.tipoArchivoSeleccionado = tipoArchivoSeleccionado;
	}

	/**
	 * @return the instrumentoMedicionSelectItem
	 */
	public SelectItem[] getInstrumentoMedicionSelectItem() {
		return instrumentoMedicionSelectItem;
	}

	/**
	 * @return the cronograma
	 */
	public ScheduleModel getCronograma() {
		System.out.println("getCronograma *****************************");
		return cronograma;
	}

	/**
	 * @return the frecManttoPrevSelectItem
	 */
	public SelectItem[] getFrecManttoPrevSelectItem() {
		return frecManttoPrevSelectItem;
	}

	/**
	 * @return the actividadNueva
	 */
	public LaboratorioActividadMantenimiento getActividadNueva() {
		return actividadNueva;
	}

	/**
	 * @param actividadNueva
	 *            the actividadNueva to set
	 */
	public void setActividadNueva(
			LaboratorioActividadMantenimiento actividadNueva) {
		this.actividadNueva = actividadNueva;
	}

	/**
	 * @return the actividadSeleccionada
	 */
	public LaboratorioActividadMantenimiento getActividadSeleccionada() {
		return actividadSeleccionada;
	}

	/**
	 * @param actividadSeleccionada
	 *            the actividadSeleccionada to set
	 */
	public void setActividadSeleccionada(
			LaboratorioActividadMantenimiento actividadSeleccionada) {
		this.actividadSeleccionada = actividadSeleccionada;
	}

	/**
	 * @return the listaActividadesEquipo
	 */
	public List<LaboratorioActividadEquipo> getListaActividadesEquipo() {
		return listaActividadesEquipo;
	}

	/**
	 * @return the nuevaActividadEquipo
	 */
	public LaboratorioActividadEquipo getNuevaActividadEquipo() {
		return nuevaActividadEquipo;
	}

	/**
	 * @param nuevaActividadEquipo
	 *            the nuevaActividadEquipo to set
	 */
	public void setNuevaActividadEquipo(
			LaboratorioActividadEquipo nuevaActividadEquipo) {
		this.nuevaActividadEquipo = nuevaActividadEquipo;
	}

	/**
	 * @return the estadoNuevaActividadSelectItem
	 */
	public SelectItem[] getEstadoNuevaActividadSelectItem() {
		return estadoNuevaActividadSelectItem;
	}

	/**
	 * @return the tipoNuevaActividadSelectItem
	 */
	public SelectItem[] getTipoNuevaActividadSelectItem() {
		return tipoNuevaActividadSelectItem;
	}

	/**
	 * @return the tipoActManttoPrevSelectItem
	 */
	public SelectItem[] getTipoActManttoPrevSelectItem() {
		return tipoActManttoPrevSelectItem;
	}

	/**
	 * @return the nuevaActividadEquipoNombreActividad
	 */
	public String getNuevaActividadEquipoNombreActividad() {
		return nuevaActividadEquipoNombreActividad;
	}

	/**
	 * @param nuevaActividadEquipoNombreActividad
	 *            the nuevaActividadEquipoNombreActividad to set
	 */
	public void setNuevaActividadEquipoNombreActividad(
			String nuevaActividadEquipoNombreActividad) {
		this.nuevaActividadEquipoNombreActividad = nuevaActividadEquipoNombreActividad;
	}

	/**
	 * @return the actividadSeleccionadaSchedule
	 */
	public LaboratorioActividadEquipo getActividadSeleccionadaSchedule() {
		return actividadSeleccionadaSchedule;
	}

	/**
	 * @param actividadSeleccionadaSchedule
	 *            the actividadSeleccionadaSchedule to set
	 */
	public void setActividadSeleccionadaSchedule(
			LaboratorioActividadEquipo actividadSeleccionadaSchedule) {
		this.actividadSeleccionadaSchedule = actividadSeleccionadaSchedule;
	}

	/**
	 * @return the listaMagnitudes
	 */
	public List<LaboratorioEquipoMetrologia> getListaMagnitudes() {
		return listaMagnitudes;
	}

	/**
	 * @return the nuevaEspecificacionMetrologica
	 */
	public LaboratorioEquipoMetrologia getNuevaEspecificacionMetrologica() {
		return nuevaEspecificacionMetrologica;
	}

	/**
	 * @param nuevaEspecificacionMetrologica
	 *            the nuevaEspecificacionMetrologica to set
	 */
	public void setNuevaEspecificacionMetrologica(
			LaboratorioEquipoMetrologia nuevaEspecificacionMetrologica) {
		this.nuevaEspecificacionMetrologica = nuevaEspecificacionMetrologica;
	}

	/**
	 * @return the soloLectura
	 */
	public Boolean getSoloLectura() {
		return soloLectura;
	}

	/**
	 * @return the tituloTablaBusqueda
	 */
	public String getTituloTablaBusqueda() {
		return tituloTablaBusqueda;
	}

	/**
	 * @param magnitudSeleccionada
	 *            the magnitudSeleccionada to set
	 */
	public void setMagnitudSeleccionada(
			LaboratorioEquipoMetrologia magnitudSeleccionada) {
		this.magnitudSeleccionada = magnitudSeleccionada;
	}

	/**
	 * @return the mostrarDiferencias
	 */
	public Boolean getMostrarDiferencias() {
		return mostrarDiferencias;
	}

	/**
	 * @return the diferencias
	 */
	public String getDiferencias() {
		return diferencias;
	}

	/**
	 * @return the verificacionSelectItem
	 */
	public SelectItem[] getVerificacionSelectItem() {
		return verificacionSelectItem;
	}

	/**
	 * @return the equipoExisteEnBDInv
	 */
	public Boolean getEquipoExisteEnBDInv() {
		return equipoExisteEnBDInv;
	}

	/**
	 * @param actividadSeleccionadaEditar
	 *            the actividadSeleccionadaEditar to set
	 */
	public void setActividadSeleccionadaEditar(
			LaboratorioActividadEquipo actividadSeleccionadaEditar) {
		this.actividadSeleccionadaEditar = actividadSeleccionadaEditar;

	}

	/**
	 * @return the actividadSeleccionadaEditar
	 */
	public LaboratorioActividadEquipo getActividadSeleccionadaEditar() {
		return actividadSeleccionadaEditar;
	}

	/**
	 * @return the estadoActividadEditarSelectItem
	 */
	public SelectItem[] getEstadoActividadEditarSelectItem() {
		return estadoActividadEditarSelectItem;
	}

	/**
	 * @return the estadoActividadEditar
	 */
	public String getEstadoActividadEditar() {
		return estadoActividadEditar;
	}

	/**
	 * @param estadoActividadEditar
	 *            the estadoActividadEditar to set
	 */
	public void setEstadoActividadEditar(String estadoActividadEditar) {
		this.estadoActividadEditar = estadoActividadEditar;
	}

	/**
	 * @return the observacionesEA
	 */
	public String getObservacionesEA() {
		return observacionesEA;
	}

	/**
	 * @param observacionesEA
	 *            the observacionesEA to set
	 */
	public void setObservacionesEA(String observacionesEA) {
		this.observacionesEA = observacionesEA;
	}

	/**
	 * @return the fechaEA
	 */
	public Date getFechaEA() {
		return fechaEA;
	}

	/**
	 * @param fechaEA
	 *            the fechaEA to set
	 */
	public void setFechaEA(Date fechaEA) {
		this.fechaEA = fechaEA;
	}

	/**
	 * @return the fechaActual
	 */
	public Date getFechaActual() {
		return fechaActual;
	}

	/**
	 * @return the mensajeErrorEditarActividad
	 */
	public String getMensajeErrorEditarActividad() {
		return mensajeErrorEditarActividad;
	}

	/**
	 * @return the poseeProcesoCompra
	 */
	public Boolean getPoseeProcesoCompra() {
		return poseeProcesoCompra;
	}

	/**
	 * @param poseeProcesoCompra
	 *            the poseeProcesoCompra to set
	 */
	public void setPoseeProcesoCompra(Boolean poseeProcesoCompra) {
		this.poseeProcesoCompra = poseeProcesoCompra;
	}

	/**
	 * @return the buscarSedeSelectItem
	 */
	public SelectItem[] getBuscarSedeSelectItem() {
		return buscarSedeSelectItem;
	}

	/**
	 * @return the sedeSeleccionada
	 */
	public String getSedeSeleccionada() {
		return sedeSeleccionada;
	}

	/**
	 * @param sedeSeleccionada
	 *            the sedeSeleccionada to set
	 */
	public void setSedeSeleccionada(String sedeSeleccionada) {
		System.out.println("setSedeSeleccionada:" + sedeSeleccionada);
		this.sedeSeleccionada = sedeSeleccionada;
	}

	/**
	 * @return the buscarFacultadSelectItem
	 */
	public SelectItem[] getBuscarFacultadSelectItem() {
		return buscarFacultadSelectItem;
	}

	/**
	 * @return the facultadSeleccionada
	 */
	public String getFacultadSeleccionada() {
		return facultadSeleccionada;
	}

	/**
	 * @param facultadSeleccionada
	 *            the facultadSeleccionada to set
	 */
	public void setFacultadSeleccionada(String facultadSeleccionada) {
		this.facultadSeleccionada = facultadSeleccionada;
	}

	/**
	 * @return the esLaboratoriosNacional
	 */
	public Boolean getEsLaboratoriosNacional() {
		return esLaboratoriosNacional;
	}

	/**
	 * @return the esLaboratoriosSede
	 */
	public Boolean getEsLaboratoriosSede() {
		return esLaboratoriosSede;
	}

	/**
	 * @return the panelesActivos
	 */
	public String getPanelesActivos() {
		System.out.println("getPanelesActivos: " + panelesActivos);
		return panelesActivos;
	}

	/**
	 * @param panelesActivos
	 *            the panelesActivos to set
	 */
	public void setPanelesActivos(String panelesActivos) {
		System.out.println("SetPanelesActivos: " + panelesActivos);

		this.panelesActivos = panelesActivos;
	}

	/**
	 * @return the fromEquipos
	 */
	public Boolean getFromEquipos() {
		return fromEquipos;
	}

	/**
	 * @return the magnitudSelectItem
	 */
	public SelectItem[] getMagnitudSelectItem() {
		return magnitudSelectItem;
	}

	/**
	 * @return the costoEA
	 */
	public Long getCostoEA() {
		return costoEA;
	}

	/**
	 * @param costoEA
	 *            the costoEA to set
	 */
	public void setCostoEA(Long costoEA) {
		this.costoEA = costoEA;
	}

	/**
	 * @return the listaActividadesMantto
	 */
	public List<LaboratorioActividadEquipo> getListaActividadesMantto() {
		return listaActividadesMantto;
	}

	/**
	 * @return the tipoNuevaActividadManttoSelectItem
	 */
	public SelectItem[] getTipoNuevaActividadManttoSelectItem() {
		return tipoNuevaActividadManttoSelectItem;
	}

	/**
	 * @return the nuevaActividadMantto
	 */
	public LaboratorioActividadEquipo getNuevaActividadMantto() {
		return nuevaActividadMantto;
	}

	/**
	 * @return the estadoNuevaActividadManttoSelectItem
	 */
	public SelectItem[] getEstadoNuevaActividadManttoSelectItem() {
		return estadoNuevaActividadManttoSelectItem;
	}

	/**
	 * @return the responsableEA
	 */
	public String getResponsableEA() {
		return responsableEA;
	}

	/**
	 * @param responsableEA
	 *            the responsableEA to set
	 */
	public void setResponsableEA(String responsableEA) {
		this.responsableEA = responsableEA;
	}

	/**
	 * @return the listaArchivosPlanMantto
	 */
	public List<ArchivoLaboratorio> getListaArchivosPlanMantto() {
		return listaArchivosPlanMantto;
	}

	public List<String> getListaInfoFaltante() {
		return listaInfoFaltante;
	}

	public void setListaInfoFaltante(List<String> listaInfoFaltante) {
		this.listaInfoFaltante = listaInfoFaltante;
	}

	public SelectItem[] getMantenimientoSI() {
		return mantenimientoSI;
	}

	public void setMantenimientoSI(SelectItem[] mantenimientoSI) {
		this.mantenimientoSI = mantenimientoSI;
	}
	
	public SelectItem[] getDondeCalibradoSelectItem() {
		return dondeCalibradoSelectItem;
	}

	public void setDondeCalibradoSelectItem(SelectItem[] dondeCalibradoSelectItem) {
		this.dondeCalibradoSelectItem = dondeCalibradoSelectItem;
	}

	public SelectItem[] getIncertidumbreCalibradoSelectitem() {
		return incertidumbreCalibradoSelectitem;
	}

	public void setIncertidumbreCalibradoSelectitem(SelectItem[] incertidumbreCalibradoSelectitem) {
		this.incertidumbreCalibradoSelectitem = incertidumbreCalibradoSelectitem;
	}

	public void setTipoDocumentoSelectItem(SelectItem[] tipoDocumentoSelectItem) {
		this.tipoDocumentoSelectItem = tipoDocumentoSelectItem;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public SelectItem[] getCalibracionSI() {
		return calibracionSI;
	}

	public void setCalibracionSI(SelectItem[] calibracionSI) {
		this.calibracionSI = calibracionSI;
	}

	public SelectItem[] getRequiereMantenimientoSelectItem() {
		return requiereMantenimientoSelectItem;
	}

	public void setRequiereMantenimientoSelectItem(
			SelectItem[] requiereMantenimientoSelectItem) {
		this.requiereMantenimientoSelectItem = requiereMantenimientoSelectItem;
	}

	public Integer getTabindex() {
		return tabindex;
	}

	public void setTabindex(Integer tabindex) {
		this.tabindex = tabindex;
	}

	public SelectItem[] getCalibradoSelectItem() {
		return calibradoSelectItem;
	}

	public void setCalibradoSelectItem(SelectItem[] calibradoSelectItem) {
		this.calibradoSelectItem = calibradoSelectItem;
	}

	public SelectItem[] getTipoNuevaActividadManttoModSelectItem() {
		return tipoNuevaActividadManttoModSelectItem;
	}

	public void setTipoNuevaActividadManttoModSelectItem(SelectItem[] tipoNuevaActividadManttoModSelectItem) {
		this.tipoNuevaActividadManttoModSelectItem = tipoNuevaActividadManttoModSelectItem;
	}

	public Tipos getModalidadEA() {
		return modalidadEA;
	}

	public void setModalidadEA(Tipos modalidadEA) {
		this.modalidadEA = modalidadEA;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosActividades() {
		return listaArchivosActividades;
	}

	public void setListaArchivosActividades(ArrayList<ArchivoLaboratorio> listaArchivosActividades) {
		this.listaArchivosActividades = listaArchivosActividades;
	}

	public Boolean getEsConsultaLaboratorios() {
		return esConsultaLaboratorios;
	}

	public void setEsConsultaLaboratorios(Boolean esConsultaLaboratorios) {
		this.esConsultaLaboratorios = esConsultaLaboratorios;
	}

	public Boolean getEsCoordinadorLaboratorio() {
		return esCoordinadorLaboratorio;
	}

	public void setEsCoordinadorLaboratorio(Boolean esCoordinadorLaboratorio) {
		this.esCoordinadorLaboratorio = esCoordinadorLaboratorio;
	}

	public Sede getSedePersona() {
		return sedePersona;
	}

	public void setSedePersona(Sede sedePersona) {
		this.sedePersona = sedePersona;
	}

	public Bien getBien() {
		return bien;
	}

	public void setBien(Bien bien) {
		this.bien = bien;
	}

	public ArchivoLaboratorio getArchivoLaboratorioActividadSeleccionado() {
		return archivoLaboratorioActividadSeleccionado;
	}

	public void setArchivoLaboratorioActividadSeleccionado(ArchivoLaboratorio archivoLaboratorioActividadSeleccionado) {
		this.archivoLaboratorioActividadSeleccionado = archivoLaboratorioActividadSeleccionado;
	}

//	public Tipos getTipoArchivoActividadSeleccionado() {
//		return tipoArchivoActividadSeleccionado;
//	}
//
//	public void setTipoArchivoActividadSeleccionado(Tipos tipoArchivoActividadSeleccionado) {
//		this.tipoArchivoActividadSeleccionado = tipoArchivoActividadSeleccionado;
//	}

	public SelectItem[] getTipoArchivoActividadSelectItem() {
		return tipoArchivoActividadSelectItem;
	}

	public void setTipoArchivoActividadSelectItem(SelectItem[] tipoArchivoActividadSelectItem) {
		this.tipoArchivoActividadSelectItem = tipoArchivoActividadSelectItem;
	}

	public StreamedContent getImage() {
		return image;
	}

	public void setImage(StreamedContent image) {
		this.image = image;
	}

	public Boolean getCambiosEnActividades() {
		return cambiosEnActividades;
	}

	public void setCambiosEnActividades(Boolean cambiosEnActividades) {
		this.cambiosEnActividades = cambiosEnActividades;
	}

	public List<LaboratorioActividadEquipo> getListaActividadesEquipoEliminar() {
		return listaActividadesEquipoEliminar;
	}

	public void setListaActividadesEquipoEliminar(List<LaboratorioActividadEquipo> listaActividadesEquipoEliminar) {
		this.listaActividadesEquipoEliminar = listaActividadesEquipoEliminar;
	}

	public List<LaboratorioActividadEquipo> getListaActividadesManttoEliminar() {
		return listaActividadesManttoEliminar;
	}

	public void setListaActividadesManttoEliminar(List<LaboratorioActividadEquipo> listaActividadesManttoEliminar) {
		this.listaActividadesManttoEliminar = listaActividadesManttoEliminar;
	}

	public Boolean getCambiosEnActividadEquipo() {
		return cambiosEnActividadEquipo;
	}

	public void setCambiosEnActividadEquipo(Boolean cambiosEnActividadEquipo) {
		this.cambiosEnActividadEquipo = cambiosEnActividadEquipo;
	}

	public List<LaboratorioEquipoMetrologia> getListaMagnitudesEliminadas() {
		return listaMagnitudesEliminadas;
	}

	public void setListaMagnitudesEliminadas(List<LaboratorioEquipoMetrologia> listaMagnitudesEliminadas) {
		this.listaMagnitudesEliminadas = listaMagnitudesEliminadas;
	}

	public Boolean getCambiosEspecificacionesMetrologicas() {
		return cambiosEspecificacionesMetrologicas;
	}

	public void setCambiosEspecificacionesMetrologicas(Boolean cambiosEspecificacionesMetrologicas) {
		this.cambiosEspecificacionesMetrologicas = cambiosEspecificacionesMetrologicas;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getDepartamentoSeleccionado() {
		return departamentoSeleccionado;
	}

	public void setDepartamentoSeleccionado(String departamentoSeleccionado) {
		this.departamentoSeleccionado = departamentoSeleccionado;
	}

	public String getIdLabs() {
		return idLabs;
	}

	public void setIdLabs(String idLabs) {
		this.idLabs = idLabs;
	}

	public Boolean getTieneHVAlCargarFormulario() {
		return tieneHVAlCargarFormulario;
	}

	public void setTieneHVAlCargarFormulario(Boolean tieneHVAlCargarFormulario) {
		this.tieneHVAlCargarFormulario = tieneHVAlCargarFormulario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public LaboratorioEquipoMetrologia getMagnitudSeleccionada() {
		return magnitudSeleccionada;
	}

	public void setEsLaboratoriosSede(Boolean esLaboratoriosSede) {
		this.esLaboratoriosSede = esLaboratoriosSede;
	}

	public void setEsLaboratoriosNacional(Boolean esLaboratoriosNacional) {
		this.esLaboratoriosNacional = esLaboratoriosNacional;
	}

	public void setListaEquiposEncontrados(List<LaboratorioDetalleEquipos> listaEquiposEncontrados) {
		this.listaEquiposEncontrados = listaEquiposEncontrados;
	}

	public void setMostrarHV(Boolean mostrarHV) {
		this.mostrarHV = mostrarHV;
	}

	public void setEquipoV(LaboratorioDetalleEquipos equipoV) {
		this.equipoV = equipoV;
	}

	public void setMecanismoAdquisicionSelectItem(SelectItem[] mecanismoAdquisicionSelectItem) {
		this.mecanismoAdquisicionSelectItem = mecanismoAdquisicionSelectItem;
	}

	public void setMantenimientoSelectItem(SelectItem[] mantenimientoSelectItem) {
		this.mantenimientoSelectItem = mantenimientoSelectItem;
	}

	public void setCalibracionSelectItem(SelectItem[] calibracionSelectItem) {
		this.calibracionSelectItem = calibracionSelectItem;
	}

	public void setVerificacionSelectItem(SelectItem[] verificacionSelectItem) {
		this.verificacionSelectItem = verificacionSelectItem;
	}

	public void setListaPersonasEntrenamiento(List<LaboratorioDetallePersona> listaPersonasEntrenamiento) {
		this.listaPersonasEntrenamiento = listaPersonasEntrenamiento;
	}

	public void setEntrenamientoBrindadoSelectItem(SelectItem[] entrenamientoBrindadoSelectItem) {
		this.entrenamientoBrindadoSelectItem = entrenamientoBrindadoSelectItem;
	}

	public void setInstrumentoMedicionSelectItem(SelectItem[] instrumentoMedicionSelectItem) {
		this.instrumentoMedicionSelectItem = instrumentoMedicionSelectItem;
	}

	public void setSedeSelectItem(SelectItem[] sedeSelectItem) {
		this.sedeSelectItem = sedeSelectItem;
	}

	public void setFacultadSelectItem(SelectItem[] facultadSelectItem) {
		this.facultadSelectItem = facultadSelectItem;
	}

	public void setDepartamentoSelectItem(SelectItem[] departamentoSelectItem) {
		this.departamentoSelectItem = departamentoSelectItem;
	}

	public void setEdificioSelectItem(SelectItem[] edificioSelectItem) {
		this.edificioSelectItem = edificioSelectItem;
	}

	public void setListaArchivos(LinkedList<ArchivoLaboratorio> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public void setListaArchivosPlanMantto(LinkedList<ArchivoLaboratorio> listaArchivosPlanMantto) {
		this.listaArchivosPlanMantto = listaArchivosPlanMantto;
	}

	public void setListaFotosEquipo(List<ArchivoLaboratorio> listaFotosEquipo) {
		this.listaFotosEquipo = listaFotosEquipo;
	}

	public void setTipoArchivoSelectItem(SelectItem[] tipoArchivoSelectItem) {
		this.tipoArchivoSelectItem = tipoArchivoSelectItem;
	}

	public void setCronograma(ScheduleModel cronograma) {
		this.cronograma = cronograma;
	}

	public void setFrecManttoPrevSelectItem(SelectItem[] frecManttoPrevSelectItem) {
		this.frecManttoPrevSelectItem = frecManttoPrevSelectItem;
	}

	public void setListaActividadesEquipo(List<LaboratorioActividadEquipo> listaActividadesEquipo) {
		this.listaActividadesEquipo = listaActividadesEquipo;
	}

	public void setListaActividadesMantto(List<LaboratorioActividadEquipo> listaActividadesMantto) {
		this.listaActividadesMantto = listaActividadesMantto;
	}

	public void setNuevaActividadMantto(LaboratorioActividadEquipo nuevaActividadMantto) {
		this.nuevaActividadMantto = nuevaActividadMantto;
	}

	public void setEstadoNuevaActividadSelectItem(SelectItem[] estadoNuevaActividadSelectItem) {
		this.estadoNuevaActividadSelectItem = estadoNuevaActividadSelectItem;
	}

	public void setEstadoNuevaActividadManttoSelectItem(SelectItem[] estadoNuevaActividadManttoSelectItem) {
		this.estadoNuevaActividadManttoSelectItem = estadoNuevaActividadManttoSelectItem;
	}

	public void setTipoNuevaActividadSelectItem(SelectItem[] tipoNuevaActividadSelectItem) {
		this.tipoNuevaActividadSelectItem = tipoNuevaActividadSelectItem;
	}

	public void setTipoNuevaActividadManttoSelectItem(SelectItem[] tipoNuevaActividadManttoSelectItem) {
		this.tipoNuevaActividadManttoSelectItem = tipoNuevaActividadManttoSelectItem;
	}

	public void setTipoActManttoPrevSelectItem(SelectItem[] tipoActManttoPrevSelectItem) {
		this.tipoActManttoPrevSelectItem = tipoActManttoPrevSelectItem;
	}

	public void setListaMagnitudes(List<LaboratorioEquipoMetrologia> listaMagnitudes) {
		this.listaMagnitudes = listaMagnitudes;
	}

	public void setSoloLectura(Boolean soloLectura) {
		this.soloLectura = soloLectura;
	}

	public void setTituloTablaBusqueda(String tituloTablaBusqueda) {
		this.tituloTablaBusqueda = tituloTablaBusqueda;
	}

	public void setMostrarDiferencias(Boolean mostrarDiferencias) {
		this.mostrarDiferencias = mostrarDiferencias;
	}

	public void setDiferencias(String diferencias) {
		this.diferencias = diferencias;
	}

	public void setEquipoExisteEnBDInv(Boolean equipoExisteEnBDInv) {
		this.equipoExisteEnBDInv = equipoExisteEnBDInv;
	}

	public void setEstadoActividadEditarSelectItem(SelectItem[] estadoActividadEditarSelectItem) {
		this.estadoActividadEditarSelectItem = estadoActividadEditarSelectItem;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public void setMensajeErrorEditarActividad(String mensajeErrorEditarActividad) {
		this.mensajeErrorEditarActividad = mensajeErrorEditarActividad;
	}

	public void setBuscarSedeSelectItem(SelectItem[] buscarSedeSelectItem) {
		this.buscarSedeSelectItem = buscarSedeSelectItem;
	}

	public void setBuscarFacultadSelectItem(SelectItem[] buscarFacultadSelectItem) {
		this.buscarFacultadSelectItem = buscarFacultadSelectItem;
	}

	public void setFromEquipos(Boolean fromEquipos) {
		this.fromEquipos = fromEquipos;
	}

	public void setMagnitudSelectItem(SelectItem[] magnitudSelectItem) {
		this.magnitudSelectItem = magnitudSelectItem;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosEliminadosActividades() {
		return listaArchivosEliminadosActividades;
	}

	public void setListaArchivosEliminadosActividades(ArrayList<ArchivoLaboratorio> listaArchivosEliminadosActividades) {
		this.listaArchivosEliminadosActividades = listaArchivosEliminadosActividades;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosActividadVista() {
		return listaArchivosActividadVista;
	}

	public void setListaArchivosActividadVista(ArrayList<ArchivoLaboratorio> listaArchivosActividadVista) {
		this.listaArchivosActividadVista = listaArchivosActividadVista;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Tipos getFrecuenciaEA() {
		return frecuenciaEA;
	}

	public void setFrecuenciaEA(Tipos frecuenciaEA) {
		this.frecuenciaEA = frecuenciaEA;
	}

	public Boolean getProgramarProxActividadEA() {
		return programarProxActividadEA;
	}

	public void setProgramarProxActividadEA(Boolean programarProxActividadEA) {
		this.programarProxActividadEA = programarProxActividadEA;
	}

	public SelectItem[] getTipoArchivoSelectItemActividades() {
		return tipoArchivoSelectItemActividades;
	}

	public void setTipoArchivoSelectItemActividades(SelectItem[] tipoArchivoSelectItemActividades) {
		this.tipoArchivoSelectItemActividades = tipoArchivoSelectItemActividades;
	}

	public Tipos getTipoArchivoSeleccionadoActividad() {
		return tipoArchivoSeleccionadoActividad;
	}

	public void setTipoArchivoSeleccionadoActividad(Tipos tipoArchivoSeleccionadoActividad) {
		this.tipoArchivoSeleccionadoActividad = tipoArchivoSeleccionadoActividad;
	}

	public List<LaboratorioEquipoConsumibleRepuesto> getListaConsumibles() {
		return listaConsumibles;
	}

	public void setListaConsumibles(List<LaboratorioEquipoConsumibleRepuesto> listaConsumibles) {
		this.listaConsumibles = listaConsumibles;
	}

	public List<LaboratorioEquipoConsumibleRepuesto> getListaConsumiblesEliminar() {
		return listaConsumiblesEliminar;
	}

	public void setListaConsumiblesEliminar(List<LaboratorioEquipoConsumibleRepuesto> listaConsumiblesEliminar) {
		this.listaConsumiblesEliminar = listaConsumiblesEliminar;
	}

	public List<LaboratorioEquipoReporteDanio> getListaReportesDanio() {
		return listaReportesDanio;
	}

	public void setListaReportesDanio(List<LaboratorioEquipoReporteDanio> listaReportesDanio) {
		this.listaReportesDanio = listaReportesDanio;
	}

	public List<LaboratorioEquipoReporteDanio> getListaReportesDanioEliminar() {
		return listaReportesDanioEliminar;
	}

	public void setListaReportesDanioEliminar(List<LaboratorioEquipoReporteDanio> listaReportesDanioEliminar) {
		this.listaReportesDanioEliminar = listaReportesDanioEliminar;
	}

	public List<LaboratorioEquipoActualizacionSoftware> getListaActualizcionesSoftware() {
		return listaActualizcionesSoftware;
	}

	public void setListaActualizcionesSoftware(List<LaboratorioEquipoActualizacionSoftware> listaActualizcionesSoftware) {
		this.listaActualizcionesSoftware = listaActualizcionesSoftware;
	}

	public List<LaboratorioEquipoActualizacionSoftware> getListaActualizcionesSoftwareEliminar() {
		return listaActualizcionesSoftwareEliminar;
	}

	public void setListaActualizcionesSoftwareEliminar(
			List<LaboratorioEquipoActualizacionSoftware> listaActualizcionesSoftwareEliminar) {
		this.listaActualizcionesSoftwareEliminar = listaActualizcionesSoftwareEliminar;
	}

	public LaboratorioEquipoConsumibleRepuesto getNuevoConsumibleRepuesto() {
		return nuevoConsumibleRepuesto;
	}

	public void setNuevoConsumibleRepuesto(LaboratorioEquipoConsumibleRepuesto nuevoConsumibleRepuesto) {
		this.nuevoConsumibleRepuesto = nuevoConsumibleRepuesto;
	}

	public LaboratorioEquipoReporteDanio getNuevoReporteDanio() {
		return nuevoReporteDanio;
	}

	public void setNuevoReporteDanio(LaboratorioEquipoReporteDanio nuevoReporteDanio) {
		this.nuevoReporteDanio = nuevoReporteDanio;
	}

	public LaboratorioEquipoActualizacionSoftware getNuevaActualizacionSoftware() {
		return nuevaActualizacionSoftware;
	}

	public void setNuevaActualizacionSoftware(LaboratorioEquipoActualizacionSoftware nuevaActualizacionSoftware) {
		this.nuevaActualizacionSoftware = nuevaActualizacionSoftware;
	}

	public LaboratorioEquipoConsumibleRepuesto getComsumibleSeleccionadoEditar() {
		return comsumibleSeleccionadoEditar;
	}

	public void setComsumibleSeleccionadoEditar(LaboratorioEquipoConsumibleRepuesto comsumibleSeleccionadoEditar) {
		this.comsumibleSeleccionadoEditar = comsumibleSeleccionadoEditar;
	}

	public LaboratorioEquipoReporteDanio getReporteDanioSeleccionadoEditar() {
		return reporteDanioSeleccionadoEditar;
	}

	public void setReporteDanioSeleccionadoEditar(LaboratorioEquipoReporteDanio reporteDanioSeleccionadoEditar) {
		this.reporteDanioSeleccionadoEditar = reporteDanioSeleccionadoEditar;
	}

	public LaboratorioEquipoActualizacionSoftware getActualizacionSoftwareSeleccionadaEditar() {
		return actualizacionSoftwareSeleccionadaEditar;
	}

	public void setActualizacionSoftwareSeleccionadaEditar(
			LaboratorioEquipoActualizacionSoftware actualizacionSoftwareSeleccionadaEditar) {
		this.actualizacionSoftwareSeleccionadaEditar = actualizacionSoftwareSeleccionadaEditar;
	}

	public String getTipoAccionConsumible() {
		return tipoAccionConsumible;
	}

	public void setTipoAccionConsumible(String tipoAccionConsumible) {
		this.tipoAccionConsumible = tipoAccionConsumible;
	}

	public String getTipoAccionReporteDanio() {
		return tipoAccionReporteDanio;
	}

	public void setTipoAccionReporteDanio(String tipoAccionReporteDanio) {
		this.tipoAccionReporteDanio = tipoAccionReporteDanio;
	}

	public String getTipoAccionActSoftware() {
		return tipoAccionActSoftware;
	}

	public void setTipoAccionActSoftware(String tipoAccionActSoftware) {
		this.tipoAccionActSoftware = tipoAccionActSoftware;
	}

	public SelectItem[] getTiposActSoftwareSelectItem() {
		return tiposActSoftwareSelectItem;
	}

	public void setTiposActSoftwareSelectItem(SelectItem[] tiposActSoftwareSelectItem) {
		this.tiposActSoftwareSelectItem = tiposActSoftwareSelectItem;
	}

	public ArchivoLaboratorio getArchivoLaboratorioReporteDanioSeleccionado() {
		return archivoLaboratorioReporteDanioSeleccionado;
	}

	public void setArchivoLaboratorioReporteDanioSeleccionado(
			ArchivoLaboratorio archivoLaboratorioReporteDanioSeleccionado) {
		this.archivoLaboratorioReporteDanioSeleccionado = archivoLaboratorioReporteDanioSeleccionado;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosReporteDanio() {
		return listaArchivosReporteDanio;
	}

	public void setListaArchivosReporteDanio(ArrayList<ArchivoLaboratorio> listaArchivosReporteDanio) {
		this.listaArchivosReporteDanio = listaArchivosReporteDanio;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosEliminadosReporteDanio() {
		return listaArchivosEliminadosReporteDanio;
	}

	public void setListaArchivosEliminadosReporteDanio(ArrayList<ArchivoLaboratorio> listaArchivosEliminadosReporteDanio) {
		this.listaArchivosEliminadosReporteDanio = listaArchivosEliminadosReporteDanio;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosReporteDanioVista() {
		return listaArchivosReporteDanioVista;
	}

	public void setListaArchivosReporteDanioVista(ArrayList<ArchivoLaboratorio> listaArchivosReporteDanioVista) {
		this.listaArchivosReporteDanioVista = listaArchivosReporteDanioVista;
	}

	public List<LaboratorioActividadEquipo> getListaActividadesEquipoFiltrados() {
		return listaActividadesEquipoFiltrados;
	}

	public void setListaActividadesEquipoFiltrados(List<LaboratorioActividadEquipo> listaActividadesEquipoFiltrados) {
		this.listaActividadesEquipoFiltrados = listaActividadesEquipoFiltrados;
	}

	public List<LaboratorioActividadEquipo> getListaActividadesManttoFiltrados() {
		return listaActividadesManttoFiltrados;
	}

	public void setListaActividadesManttoFiltrados(List<LaboratorioActividadEquipo> listaActividadesManttoFiltrados) {
		this.listaActividadesManttoFiltrados = listaActividadesManttoFiltrados;
	}

	public String getTipoAccionActCalib() {
		return tipoAccionActCalib;
	}

	public void setTipoAccionActCalib(String tipoAccionActCalib) {
		this.tipoAccionActCalib = tipoAccionActCalib;
	}

	public LaboratorioActividadEquipo getEditaActividadEquipoCalib() {
		return editaActividadEquipoCalib;
	}

	public void setEditaActividadEquipoCalib(LaboratorioActividadEquipo editaActividadEquipoCalib) {
		this.editaActividadEquipoCalib = editaActividadEquipoCalib;
	}

	public LaboratorioActividadEquipo getConsultaActividadEquipoCalib() {
		return consultaActividadEquipoCalib;
	}

	public void setConsultaActividadEquipoCalib(LaboratorioActividadEquipo consultaActividadEquipoCalib) {
		this.consultaActividadEquipoCalib = consultaActividadEquipoCalib;
	}

	public Tipos getFrecuenciaEACalib() {
		return frecuenciaEACalib;
	}

	public void setFrecuenciaEACalib(Tipos frecuenciaEACalib) {
		this.frecuenciaEACalib = frecuenciaEACalib;
	}

	public Tipos getModalidadEACalib() {
		return modalidadEACalib;
	}

	public void setModalidadEACalib(Tipos modalidadEACalib) {
		this.modalidadEACalib = modalidadEACalib;
	}

	public String getEstadoEACalib() {
		return estadoEACalib;
	}

	public void setEstadoEACalib(String estadoEACalib) {
		this.estadoEACalib = estadoEACalib;
	}

	public Date getFechaProgramadaEACalib() {
		return fechaProgramadaEACalib;
	}

	public void setFechaProgramadaEACalib(Date fechaProgramadaEACalib) {
		this.fechaProgramadaEACalib = fechaProgramadaEACalib;
	}

	public Date getFechaEjecutadaEACalib() {
		return fechaEjecutadaEACalib;
	}

	public void setFechaEjecutadaEACalib(Date fechaEjecutadaEACalib) {
		this.fechaEjecutadaEACalib = fechaEjecutadaEACalib;
	}

	public String getResponsableEACalib() {
		return responsableEACalib;
	}

	public void setResponsableEACalib(String responsableEACalib) {
		this.responsableEACalib = responsableEACalib;
	}

	public Long getCostoEACalib() {
		return costoEACalib;
	}

	public void setCostoEACalib(Long costoEACalib) {
		this.costoEACalib = costoEACalib;
	}

	public String getObservacionesEACalib() {
		return observacionesEACalib;
	}

	public void setObservacionesEACalib(String observacionesEACalib) {
		this.observacionesEACalib = observacionesEACalib;
	}

	public String getEmpresaEACalib() {
		return empresaEACalib;
	}

	public void setEmpresaEACalib(String empresaEACalib) {
		this.empresaEACalib = empresaEACalib;
	}

	

	public Boolean getEmpresaAcreditadaEACalib() {
		return empresaAcreditadaEACalib;
	}

	public void setEmpresaAcreditadaEACalib(Boolean empresaAcreditadaEACalib) {
		this.empresaAcreditadaEACalib = empresaAcreditadaEACalib;
	}

	public String getCodigoInformeEACalib() {
		return codigoInformeEACalib;
	}

	public void setCodigoInformeEACalib(String codigoInformeEACalib) {
		this.codigoInformeEACalib = codigoInformeEACalib;
	}

	public String getIntervaloMedicionEACalib() {
		return intervaloMedicionEACalib;
	}

	public void setIntervaloMedicionEACalib(String intervaloMedicionEACalib) {
		this.intervaloMedicionEACalib = intervaloMedicionEACalib;
	}

	public Boolean getEquipoAdecuadoUsoEACalib() {
		return equipoAdecuadoUsoEACalib;
	}

	public void setEquipoAdecuadoUsoEACalib(Boolean equipoAdecuadoUsoEACalib) {
		this.equipoAdecuadoUsoEACalib = equipoAdecuadoUsoEACalib;
	}

	public String getEquipoAdecuadoUsoNoDescEACalib() {
		return equipoAdecuadoUsoNoDescEACalib;
	}

	public void setEquipoAdecuadoUsoNoDescEACalib(String equipoAdecuadoUsoNoDescEACalib) {
		this.equipoAdecuadoUsoNoDescEACalib = equipoAdecuadoUsoNoDescEACalib;
	}

	public Boolean getProgramarProxActividadEACalib() {
		return programarProxActividadEACalib;
	}

	public void setProgramarProxActividadEACalib(Boolean programarProxActividadEACalib) {
		this.programarProxActividadEACalib = programarProxActividadEACalib;
	}

	public Date getFechaEAProgramada() {
		return fechaEAProgramada;
	}

	public void setFechaEAProgramada(Date fechaEAProgramada) {
		this.fechaEAProgramada = fechaEAProgramada;
	}

	public SelectItem[] getTipoArchivoActividadMantto() {
		return tipoArchivoActividadMantto;
	}

	public void setTipoArchivoActividadMantto(SelectItem[] tipoArchivoActividadMantto) {
		this.tipoArchivoActividadMantto = tipoArchivoActividadMantto;
	}

	public SelectItem[] getTipoArchivoActividadCalib() {
		return tipoArchivoActividadCalib;
	}

	public void setTipoArchivoActividadCalib(SelectItem[] tipoArchivoActividadCalib) {
		this.tipoArchivoActividadCalib = tipoArchivoActividadCalib;
	}

	public Tipos getTipoArchivoSelActMantto() {
		return tipoArchivoSelActMantto;
	}

	public void setTipoArchivoSelActMantto(Tipos tipoArchivoSelActMantto) {
		this.tipoArchivoSelActMantto = tipoArchivoSelActMantto;
	}

	public Tipos getTipoArchivoSelActCalib() {
		return tipoArchivoSelActCalib;
	}

	public void setTipoArchivoSelActCalib(Tipos tipoArchivoSelActCalib) {
		this.tipoArchivoSelActCalib = tipoArchivoSelActCalib;
	}

	public ArchivoLaboratorio getArchivoLaboratorioActividadSeleccionadoCalib() {
		return archivoLaboratorioActividadSeleccionadoCalib;
	}

	public void setArchivoLaboratorioActividadSeleccionadoCalib(
			ArchivoLaboratorio archivoLaboratorioActividadSeleccionadoCalib) {
		this.archivoLaboratorioActividadSeleccionadoCalib = archivoLaboratorioActividadSeleccionadoCalib;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosActividadesCalib() {
		return listaArchivosActividadesCalib;
	}

	public void setListaArchivosActividadesCalib(ArrayList<ArchivoLaboratorio> listaArchivosActividadesCalib) {
		this.listaArchivosActividadesCalib = listaArchivosActividadesCalib;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosEliminadosActividadesCalib() {
		return listaArchivosEliminadosActividadesCalib;
	}

	public void setListaArchivosEliminadosActividadesCalib(
			ArrayList<ArchivoLaboratorio> listaArchivosEliminadosActividadesCalib) {
		this.listaArchivosEliminadosActividadesCalib = listaArchivosEliminadosActividadesCalib;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosActividadVistaCalib() {
		return listaArchivosActividadVistaCalib;
	}

	public void setListaArchivosActividadVistaCalib(ArrayList<ArchivoLaboratorio> listaArchivosActividadVistaCalib) {
		this.listaArchivosActividadVistaCalib = listaArchivosActividadVistaCalib;
	}

	public SelectItem[] getTipoArchivoSelectItemActividadesCalib() {
		return tipoArchivoSelectItemActividadesCalib;
	}

	public void setTipoArchivoSelectItemActividadesCalib(SelectItem[] tipoArchivoSelectItemActividadesCalib) {
		this.tipoArchivoSelectItemActividadesCalib = tipoArchivoSelectItemActividadesCalib;
	}

	public Tipos getTipoArchivoSeleccionadoActividadCalib() {
		return tipoArchivoSeleccionadoActividadCalib;
	}

	public void setTipoArchivoSeleccionadoActividadCalib(Tipos tipoArchivoSeleccionadoActividadCalib) {
		this.tipoArchivoSeleccionadoActividadCalib = tipoArchivoSeleccionadoActividadCalib;
	}
	
	
	
}