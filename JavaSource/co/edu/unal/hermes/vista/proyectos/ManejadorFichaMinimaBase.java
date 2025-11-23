package co.edu.unal.hermes.vista.proyectos;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.TreeNode;

import com.sun.org.apache.xpath.internal.operations.And;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.AreaTematica;
import co.edu.unal.hermes.modelo.AreaTematicaVista;
import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DependenciaAportante;
import co.edu.unal.hermes.modelo.DependenciaAreaResponsabilidad;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoCivil;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.MetaProyecto;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.ModalidadFuenteFinanciacion;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.PlanEstudios;
import co.edu.unal.hermes.modelo.PlanGlobalDesarrollo;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoProducto;
import co.edu.unal.hermes.modelo.ProyectoPrograma;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.ResultadoProyecto;
import co.edu.unal.hermes.modelo.RubroFinanciable;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroProyecto;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoFormacion;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.modelo.TipoVinculacion;
import co.edu.unal.hermes.modelo.ValoresListasProyecto;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.mapeo.GastoFM;
import co.edu.unal.hermes.utils.VariablesEstaticas;
import co.edu.unal.hermes.vista.proyectos.manejadorproyecto.ManejadorProyecto;

/**
 * The Class ManejadorFichaMinimaBase.
 */
public abstract class ManejadorFichaMinimaBase extends ManejadorProyecto {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8131728536319150701L;

	/** The dominio categoria. */
	protected static final String DOMINIO_CATEGORIA = "TIPO_ACTIVIDAD_FICHA_MINIMA_BKUP";

	/** The Constant DOMINIO_CATEGORIA_INV. */
	private static final String DOMINIO_CATEGORIA_INV = "TIPOLOGIA_PROYECTOS";

	private static final String DOMINIO_TIPOLOGIA_PROYECTOS_CONV_SESQUIC = "TIPOLOGIA_PROYECTOS_CONV_SESQUIC";

	private static final String DOMINIO_TIPOLOGIA_PROYECTOS_CONV_SUE_2017 = "TIPOLOGIA_PROYECTOS_CONV_SUE_BOG";

	private static final String DOMINIO_TIPOLOGIA_PROYECTOS_EXT_SOL_2018 = "TIPOLOGIA_PROYECTOS_EXT_SOL_2018";

	private static final String TIPOLOGIA_PROYECTOS_UNINNOVA_2019 = "TIPOLOGIA_PROYECTOS_UNINNOVA_2019";

	private static final String TIPOLOGIA_PROYECTOS_UNINNOVA_2021 = "TIPOLOGIA_PROYECTOS_UNINNOVA_2021";

	private static final String DOMINIO_TIPOLOGIA_PROYECTOS_ALIANZAS_2018 = "TIPOLOGIA_PROYECTOS_ALIANZAS_2018";

	private static final String DOMINIO_TIPOLOGIA_PROYECTOS_ALIANZAS_2019 = "TIPOLOGIA_PROYECTOS_ALIANZAS_2019";

	private static final String DOMINIO_TIPOLOGIA_PROYECTOS_CONV_REPO_EQUIPOS_LAB = "TIPOLOGIA_PROYECTOS_CONV_REPO_EQUIPOS_LAB";

	private static final String DOMINIO_TIPOLOGIA_PROYECTOS_CONV_DOC_NAL_EMP_758_2018 = "TIPOLOGIA_PROYECTOS_CONV_DOC_NAL_EMP_758_2018";

	/** The Constant DOMINIO_ROL. */
	private static final String DOMINIO_ROL = "ROL_UNIVERSIDAD_PROYECTOS";

	/** The Constant DOMINIO_ACTIVO_PI. */
	private static final String DOMINIO_ACTIVO_PI = "ACTIVO_PI";

	/** The Constant DOMINIO_TIPO_FINANCIACION. */
	private static final String DOMINIO_TIPO_FINANCIACION = "TIPO_FINANCIACION";

	/** The Constant DOMINIO_OBJETIVO_SOCIO_EC. */
	private static final String DOMINIO_OBJETIVO_SOCIO_EC = "OBJETIVO_SOCIO_ECONOMICO";

	protected static final String DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE = "OBJETIVOS_DESARROLLO_SOSTENIBLE";

	/** The Constant DOMINIO_LINEA_ACCION. */
	protected static final String DOMINIO_LINEA_ACCION = "LINEAS_ACCION_FM";

	/** The Constant DOMINIO_PROGRAMA. */
	protected static final String DOMINIO_PROGRAMA = "PROGRAMA_FM";

	/** The objeto especifico. */
	private UIComponent objetoEspecifico;

	/** The objeto especifico. */
	private UIComponent uiDependenciaProyectoPrograma;

	/** The objeto especifico. */
	private UIComponent metaProyecto;

	/** The buscar per 2. */
	protected UIComponent buscarPer2;

	/** The buscar investigador externo. */
	protected UIComponent botonBuscarInvestigadorExterno;

	/** The horas coinv 3. */
	private UIComponent horasCoinv3;

	/** The horas coinv 2. */
	private UIComponent horasCoinv2;

	/** The palabra clave S. */
	private UIComponent palabraClaveS;

	/** The ui resultado. */
	private UIComponent uiResultado;

	/** The palabra clave. */
	private String palabraClave;

	/** The conoce datos. */
	// Información formulario coinvestiador
	private String conoceDatos = "SI";

	/** The boton areas tabla. */
	private UIComponent botonAreasTabla;

	/** The funcion investigador. */
	private String funcionInvestigador;

	/** The cantidad part. */
	private Long cantidadPart = 0L;

	/** The ui cantidad. */
	private UIComponent uiCantidad;

	/** The ui btn prod. */
	private UIComponent uiBtnProd;

	/** The buscar director. */
	// Objetos binding
	protected UIComponent buscarDirector;

	/** The boton agregar dependencia. */
	private UIComponent botonAgregarDependencia;

	private UIComponent botonAgregarDependenciaAlianzas;

	/** The buscar per 2. */
	private UIComponent buscarPer3;

	/** The horas coinv. */
	private UIComponent horasCoinv;

	protected UIComponent btnObjDesSosSec;

	/** The convocatoria actual. */
	protected Convocatoria convocatoriaActual;

	/** The lugar nacimiento. */
	private String lugarNacimiento;

	/** The es otra vinculacion. */
	private boolean esOtraVinculacion = true;

	/** The mostrar dependencia. */
	private boolean mostrarDependencia = false;

	/** The mostrar opcion buscar investigador. */
	private boolean mostrarOpcionBuscarInvestigador = false;

	/** The tipo vinculacion id. */
	private String tipoVinculacionId = "1";

	/** The dependencia area responsabilidad seleccionada. */
	private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada;

	/** The dependencia area responsabilidad seleccionada. */
	private DependenciaAportante dependenciaAportanteSeleccionada;

	/** The tiempo total participante. */
	protected Integer tiempoTotalParticipante;

	/** The es ficha externa. */
	protected boolean esFichaExterna = false;
	protected boolean editarVigencias = false;

	/** The area seleccionada. */
	private AreaTematicaVista areaSeleccionada;

	/** The es interno. */
	protected boolean esInterno = false;

	/** The mostrar menu formulario. */
	protected boolean mostrarMenuFormulario;

	/** The validar equipo trabajo. */
	protected boolean validarEquipoTrabajo = false;

	/** The unidad ejecutora. */
	protected String unidadEjecutora;

	/** The mostrar descripcion problema. */
	protected boolean mostrarDescripcionProblema = false;

	/** The es proyecto laboratorios. */
	protected boolean esProyectoLaboratorios;

	protected boolean esProyectoTesisPosgrado;

	/** The mostrar biodiversidad. */
	protected boolean mostrarBiodiversidad;

	protected boolean mostrarLaboratorios;

	protected boolean mostrarResumen;

	protected boolean mostrarResultados;

	/** The objetivo tabla. */
	private ObjetivoEspecifico objetivoTabla;

	/** The meta tabla. */
	private MetaProyecto metaTabla;

	/** The es conv proyectos 2016 2018. */
	protected boolean esConvProyectos2016_2018 = false;

	protected boolean esConvAdmonManizales2018 = false;

	protected boolean esConvAdmonManizalesGruposNoRec2018 = false;

	protected boolean esConvSemillerosDerecho2018 = false;

	protected boolean esConvArtes_2018_ModB = false;

	protected boolean esConvRepotenciacionLab2018 = false;

	protected boolean esConvDocNalEmp758_2018 = false;

	protected boolean esConvProyectos2017_2018 = false;

	protected boolean esConvAlianzasBog2017 = false;

	protected boolean esConvFortInvIngAdmonPal2018 = false;

	protected boolean esConvPosgradoCienciasAgrarias2018 = false;

	protected boolean esConvDiscDerecho2018 = false;

	protected boolean esConvObservatoriosDerecho2018 = false;

	protected boolean esConvCienciasAgrariasPal_1_2018 = false;

	protected boolean esConvCienciasAgrariasPal_2_2018 = false;

	protected boolean esConvCienciasAgrariasPal_3_2018 = false;

	protected boolean esConvOdontologia2017 = false;

	protected boolean esConvOdontologia2017_M2 = false;

	protected boolean esConvSesquicente = false;

	protected boolean esConvExtSol2017 = false;

	protected boolean esConvMedTraslacional2017 = false;

	protected boolean esConvCienciasAgrarias2017 = false;

	protected boolean esConvArtesModA2017 = false;

	protected boolean esConvArtesModB2017 = false;

	protected boolean esConvPurdue = false;

	/** The es conv cundinamarca. */
	protected boolean esConvCundinamarca = false;

	protected boolean esConvCundinamarca2019 = false;

	protected boolean esConvSUE2017 = false;
	
	protected boolean esConvGrupSemArtes2022 = false;

	protected boolean esConvExtSol2018 = false;

	protected boolean esConvMedLegal2018 = false;

	protected boolean esConvUnInnova2018 = false;

	protected boolean esConvUnInnova2019 = false;

	protected boolean esConvUnInnova2021 = false;

	protected boolean esConvSemillerosCienciasHumanas2018 = false;

	protected boolean esConvAlianzasPalmira2018 = false;

	protected boolean esConvocatoriaAlianzas2018 = false;

	protected boolean esConvocatoriaAlianzas2019 = false;

	/** The mostrar datos joven. */
	private boolean mostrarDatosJoven = false;

	/** The facultad sel. */
	private String facultadSel;

	private String facultadSelAportante;

	protected String facultadSelLabs;

	/** The es conv ext sol 2016. */
	protected boolean esConvExtSol2016 = false;

	/** The es conv fals borda 2017. */
	protected boolean esConvFalsBorda2017 = false;

	/** The es conv fals borda 2018. */
	protected boolean esConvFalsBorda2018 = false;

	/** The es tiempo volver. */
	protected boolean esTiempoVolver = false;

	/** The es convo inno ped DIE B 2. */
	protected boolean esConvoInnoPedDIEB_2 = false;

	/** The mostrar objetivos especificos. */
	protected boolean mostrarObjetivosEspecificos;

	/** The mostrar proyecto asociado. */
	private boolean mostrarProyectoAsociado = false;

	/** The politica. */
	protected String politica;
	protected boolean etiquetas2025PlanDllo = false;

	/** The sub area ciencia. */
	protected String subAreaCiencia;

	/** The area ciencia. */
	// Información basica del proyecto
	protected String areaCiencia;

	/** The sede sel. */
	protected String sedeSel;

	protected String sedeSelAportante;

	protected Long totalDependenciasAportantes;

	/** The tipo documento co inv. */
	// Información formulario lider
	private TipoDocumento tipoDocumentoCoInv;

	/** The horas director. */
	private double horasDirector;

	/** The mostrar programa academico. */
	private boolean mostrarProgramaAcademico = false;

	/** The mostrar facultades. */
	private boolean mostrarFacultades = false;

	/** The mostrar opcion tiempo dedicacion formulacion. */
	// Opciones vista
	protected boolean mostrarOpcionTiempoDedicacionFormulacion;

	/** The area ciencia sec. */
	protected String areaCienciaSec;

	/** The resultado tabla. */
	private ResultadoProyecto resultadoTabla;

	/** The valor gasto. */
	private Long valorGasto;

	/** The id tipo rubro. */
	private Long idTipoRubro;

	/** The gasto seleccionado. */
	private Gasto gastoSeleccionado;

	/** The horas participante. */
	protected double horasParticipante;

	/** The es consulta. */
	protected boolean esConsulta = false;

	/** The objetivo especifico. */
	private String objetivoEspecifico;
	
	/** The medio de verificacion objetivo especifico. */
	private String medioVerificacion;

	/** Meta */
	private String meta;

	private ObjetivoEspecifico objetivoSeleccionado = new ObjetivoEspecifico();

	/** The otro tipo. */
	private String otroTipo;

	/** The ver ficha min JI 2015. */
	protected boolean verFichaMinJI2015 = false;

	/** The dependencia proyecto. */
	protected String dependenciaProyecto;

	/** The dependencia proyecto. */
	protected String dependenciaAportanteProyecto;

	/** The dependencia proyecto. */
	protected String metaObjetivoSelectValue;

	/** The valor financiado investig. */
	protected Long valorFinanciadoInvestig = 0L;

	/** The valor total total proyecto. */
	protected Long valorTotalTotalProyecto = 0L;

	/** The cantidad. */
	private String cantidad;

	/** The descripcion gasto. */
	private String descripcionGasto = "";

	/** The resultado. */
	private String resultado;
	
	/** The entregable. */
	private String entregable;
	
	/** The entregable fecha. */
	private Date fechaEntregable;

	/** The producto nivel 3. */
	private String productoNivel3;
	
	/** The producto nivel 2. */
	private String productoNivel2;
	
	/** The producto nivel 1. */
	private String productoNivel1;

	/** The sub area ciencia sec. */
	protected String subAreaCienciaSec;

	/** The producto seleccionado. */
	private ProyectoProducto productoSeleccionado;

	/** The institucion nombre. */
	protected String institucionNombre;

	/** The documento coinv. */
	private String documentoCoinv;

	/** The investigador externo. */
	protected Persona investigadorExterno = new InvestigadorExterno();

	/** The funcion director. */
	private String funcionDirector;

	/** The documento coinv equipo. */
	protected String documentoCoinvEquipo;

	/** The tipo documento co inv equipo. */
	protected TipoDocumento tipoDocumentoCoInvEquipo;

	protected SelectItem[] tipoFormacionItem;

	protected SelectItem[] estadoCivilItem;

	/** The producto nivel 3 item. */
	private SelectItem[] productoNivel3Item;
	
	/** The producto nivel 2 item. */
	private SelectItem[] productoNivel2Item;
	
	/** The producto nivel 1 item. */
	private SelectItem[] productoNivel1Item;

	/** The tipos fuente item. */
	private SelectItem[] tiposFuenteItem = null;

	/** The categoria items ppal. */
	protected SelectItem[] categoriaItemsPpal;

	/** The categoria items ECP. */
	private SelectItem[] categoriaItemsECP;

	/** The subtipos financiacion. */
	protected SelectItem[] subtiposFinanciacion;

	/** The si tipos proy labs. */
	protected SelectItem[] siTiposProyLabs;

	/** The si subtipos proy labs. */
	protected SelectItem[] siSubtiposProyLabs;

	/** The rol universidad items. */
	private SelectItem[] rolUniversidadItems;

	/** The mecanismo solicitud items. */
	private SelectItem[] mecanismoSolicitudItems;

	/** The obj socioeconomico items. */
	private SelectItem[] objSocioeconomicoItems;

	/** The area ciencia items. */
	private SelectItem[] areaCienciaItems;

	/** The sub area ciencia items. */
	private SelectItem[] subAreaCienciaItems;

	private SelectItem[] subAreaCienciaItemsInv;

	/** The tipos activos PI item. */
	private SelectItem[] tiposActivosPIItem;

	/** The sede item. */
	private SelectItem[] sedeItem;

	protected String sedeFiltroLabs;

	protected String labSeleccionado;

	protected SelectItem[] labsItem;

	protected ArrayList<Laboratorio> laboratorios;

	protected ArrayList<Laboratorio> listaLaboratoriosOriginal;

	protected Laboratorio labEliminar;

	protected Dependencia lugarEjecEliminar;
	
	/** The componentes items. */
	private SelectItem[] componentesItems;

	/** The lineas accion items. */
	private SelectItem[] lineasAccionItems;

	/** The programa items. */
	private SelectItem[] programaItems;

	/** The tipo documento item. */
	private SelectItem[] tipoDocumentoItem;

	/** The sub area ciencia sec items. */
	private SelectItem[] subAreaCienciaSecItems;

	/** The fuentes internas item. */
	private SelectItem[] fuentesInternasItem;

	/** The tipo vinculacion items numero. */
	private SelectItem[] tipoVinculacionItemsNumero;

	/** The tipos rubro item. */
	private SelectItem[] tiposRubroItem;

	/** The tipo vinculacion items. */
	protected SelectItem[] tipoVinculacionItems;

	/** The facultad item. */
	private List<SelectItem> facultadItem;

	/** The facultad item. */
	private List<SelectItem> facultadAportanteItem;

	private List<SelectItem> facultadItemLabs;

	/** The lista productos nivel 3. */
	private List<ProductoTipo> listaProductosNivel3;

	/** The lista productos nivel 2. */
	private List<ProductoTipo> listaProductosNivel2;
	
	/** The lista productos nivel 1. */
	private List<ProductoTipo> listaProductosNivel1;
	
	/** The dependencias UN. */
	protected List<Dependencia> dependenciasUN;

	/** The dependencia item. */
	private List<SelectItem> dependenciaItem;

	private List<SelectItem> dependenciaAportanteItem;

	/** The dependencia item completa. */
	private List<SelectItem> dependenciaItemCompleta;

	/** The dependencia item. */
	private List<SelectItem> objetivosItem;

	/** The lista tipo vinculacion numero. */
	protected List<TipoInvestigador> listaTipoVinculacionNumero;

	/** The ciudad item list. */
	private List<SelectItem> ciudadItemList;

	/** The lista tipos rubros. */
	protected List<TipoRubro> listaTiposRubros;

	/** The lista tipo vinculacion. */
	protected List<TipoInvestigador> listaTipoVinculacion;

	/** The lista areas tematicas. */
	protected List<AreaTematicaVista> listaAreasTematicas;

	/** The grupo del integrante. */
	protected String grupo;

	/** indica si se asignará grupo al integrante o no. */
	protected boolean asignarGrupo;

	/** The lista de grupos de investigacion del integrante. */
	protected List<SelectItem> gruposInvestigacionItem;

	private String tipoInvestigadoresConvocatoria;

	private Long valorAporteAlianza = 0L;
	
	private Long valorAporteDependenciaEspecie = 0L;

	protected SelectItem[] objetivosDesarrolloSostenibleItems;

	protected String objetivoDesarrolloSosteniblePrimario;

	protected String objetivoDesarrolloSostenibleSecundario;

	protected ValoresListasProyecto objetivoDesarrolloSostenibleSeleccionado;

	protected List<ValoresListasProyecto> valoresObjetivosDesarrolloSostenible;

	protected List<ValoresListasProyecto> valoresObjetivosDesarrolloSostenibleBorrados;

	protected String idPaisNacimiento;

	protected SelectItem[] listaPaisesFuenteItem;

	protected boolean siColombia = false;

	protected Departamento departamentoActual;

	protected SelectItem[] departamentoItem;

	protected String ciudadNacimiento;

	protected SelectItem[] ciudadItem;

	protected List listaCiudades;

	protected Ciudad ciudadActual;

	protected List listaDepartamentos;

	protected String idTipoFormacion;

	protected String idTipoEstadoCivil;

	protected String subAreaCienciaInv;

	protected String areaCienciaInv;

	protected String ciudadNacimientoNoCol;

	protected SelectItem[] institucionesItem;

	protected String institucionSeleccionada;

	private String nombreProyectoPrograma;

	private String idEntidadProyectoPrograma;

	private String idDependenciaEntidadProyectoPrograma;

	private ProyectoPrograma proyectoProgramaSeleccionado;

	/** The mecanismo solicitud items. */
	private SelectItem[] entidadesProyectoProgramaItems;

	private String mensajeRubrosBasicos;

	private boolean mostrarMensajeRubros = false;

	protected boolean esConvInnovaSedesPresNal = false;

	protected boolean esConvocatoriaFortalecimientoLabs2024_M1 = false;
	protected boolean esConvocatoriaFortalecimientoLabs2024_M2 = false;
	
	public boolean esConvocatoriaRedes = false;
	public boolean esConvocatoriaPlanArmonizacion = false;

	protected boolean esConvProyectosBog2019 = false;

	protected boolean esConvProyectosPal2019 = false;

	protected boolean esConvCP2019 = false;

	private static final String DOMINIO_TIPOLOGIA_PROYECTOS_CONV_CP_2019 = "TIPOLOGIA_PROYECTOS_CONV_CP_2019";

	private boolean mostrarObjetivos;

	protected boolean esConvURosario = false;

	protected Long contrapEfectUNAL_ConvURosario = 0L;
	
	private List<ProductoTipo> productosConfiguradosConvocatoria;
	
	public boolean esDirector = false;

	public class montoFinanciar {

		public montoFinanciar() {
		}

		public montoFinanciar(String tipoVinculacion, String monto) {
			this.tipoVinculacion = tipoVinculacion;
			this.monto = monto;
		}

		// Atributo o variable miembro
		public String tipoVinculacion;
		public String monto;

		public String getTipoVinculacion() {
			return tipoVinculacion;
		}

		public void setTipoVinculacion(String tipoVinculacion) {
			this.tipoVinculacion = tipoVinculacion;
		}

		public String getMonto() {
			return monto;
		}

		public void setMonto(String monto) {
			this.monto = monto;
		}
	};

	protected List<montoFinanciar> listaMontosFinanciar;

	private SelectItem[] sedesItem;

	protected Departamento departamentoRegionImpacto;

	protected SelectItem[] departamentoItemRegionImpacto;

	protected SelectItem[] ciudadItemRegionImpacto;

	protected List listaCiudadesRegionImpacto;

	protected Ciudad ciudadActualRegionImpacto;

	protected List listaDepartamentosRegionImpacto;

	protected Ciudad ciudadRegionImpacto;

	protected boolean esEntidSedePresNal = false;

	protected String sedeSelPres;
	private SelectItem[] sedePresItem;
	private boolean mostrarFacultadesPres = false;
	protected List<Dependencia> dependenciasUNPres;
	private List<SelectItem> dependenciaPresItem;
	protected String dependenciaPresProyecto;
	private List<SelectItem> facultadPresItem;
	private String facultadSelPres;

	/**
	 * Instantiates a new manejador ficha minima base.
	 */
	public ManejadorFichaMinimaBase() {

		tipoVinculacionId = "PCD";

		esOtraVinculacion = false;

		esConsulta = false;

		personaActual = (Persona) sesion.getAttribute("persona");

		tipoDocumentoCoInv = new TipoDocumento();

		tipoDocumentoCoInvEquipo = new TipoDocumento();

		listaAreasTematicas = new ArrayList<AreaTematicaVista>();

		// Se carga documento y tipo de investigador principal
		tipoDocumentoCoInv.setId(personaActual.getId().getTipoDocumento());
		documentoCoinv = personaActual.getId().getDocumento();

		listaTiposRubros = new ArrayList<TipoRubro>();

		listaMontosFinanciar = new ArrayList<montoFinanciar>();

		// Cargar lista sedes
		List<Dependencia> listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
		if (!esListaVacia(listaSedes)) {
			sedesItem = new SelectItem[listaSedes.size()];
			for (int i = 0; i < listaSedes.size(); i++) {
				Dependencia sede = listaSedes.get(i);
				sedesItem[i] = new SelectItem(sede.getId(), sede.getNombre());
			}
		}

		valoresObjetivosDesarrolloSostenible = new ArrayList<ValoresListasProyecto>();

		valoresObjetivosDesarrolloSostenibleBorrados = new ArrayList<ValoresListasProyecto>();

		departamentoRegionImpacto = new Departamento();

		ciudadRegionImpacto = new Ciudad();

		listaLaboratoriosOriginal = new ArrayList<Laboratorio>();
	}

	/**
	 * Cargar convocatoria actual.
	 */
	protected void cargarConvocatoriaActual() {
		Long idConvocatoria;

		// Se carga la convocatoria del proyecto cargado.
		if (proyectoActual.getModalidad() != null) {
			idConvocatoria = proyectoActual.getModalidad().getId();
		} else {
			// Si no hay proyecto cargado se trae lo que esta en sesión de la
			// convocatoria.
			idConvocatoria = (Long) sesion.getAttribute("idConvocatoriaActual");
		}
		// Finalmente si no hay nada en sesión se carga la convocatoria por
		// defecto.
		if (idConvocatoria == null) {
			idConvocatoria = MODALIDAD_FICHA_MINIMA_ID;
		}

		convocatoriaActual = (Convocatoria) servicioGeneral.obtenerObjeto(new Convocatoria(), idConvocatoria);

		// Se verifica si es una versión de ficha de legalización para
		// modificar.
		if (convocatoriaActual.getId().compareTo(10L) == 0) {
			esFichaExterna = true;
			esInterno = false;
		}
		
		if(proyectoActual.getEstadoProyecto() != null && (!proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.INGRESANDO) && !proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO) && 
				!proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.ELEGIBLE) && !proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.RECHAZADO))) {
			editarVigencias = true;
			if(proyectoActual.getMontoInicialAprobado()==null) {
				proyectoActual.setMontoInicialAprobado(servicioProyecto.obtenerMontoAprobadoProyecto(proyectoActual.getId(), "MINIMA"));
			}
		}

		// Se verifica si se debe mostrar formulario.
		if (!convocatoriaActual.getId().equals(MODALIDAD_FICHA_MINIMA_ID)) {
			mostrarMenuFormulario = true;
		}

		// Se valida que sea un proyecto de laboratorios
		esProyectoLaboratorios = convocatoriaActual.getTipo().getId()
				.equals(TipoModalidad.REGISTRO_PROYECTOS_LABORATORIOS);

		esProyectoTesisPosgrado = convocatoriaActual.getTipo().getId()
				.equals(TipoModalidad.CONVOCATORIA_PROYECTOS_TESIS_POSGRADO);

		// Se validan las restricciones de las convocatorias
		if (convocatoriaActual.getRestriccion() != null) {

			if (convocatoriaActual.getRestriccion().getId().equals("CONV_PROY_2016_2018")) {
				esConvProyectos2016_2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_SEMI_DER_2018)) {
				esConvSemillerosDerecho2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ARTES_2018_M2)) {
				esConvArtes_2018_ModB = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_SEMI_CH_2018)) {
				esConvSemillerosCienciasHumanas2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_UNNOVA_18_1)) {
				esConvUnInnova2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_UNNOVA_19)) {
				esConvUnInnova2019 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_UNNOVA_21)) {
				esConvUnInnova2021 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_REP_EQU_LAB)) {
				esConvRepotenciacionLab2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals("CONV_NAL_SESQUICENTE")) {
				esConvSesquicente = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_NAL_PRY_2017_18)) {
				esConvProyectos2017_2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ALI_BOG_17)) {
				esConvAlianzasBog2017 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_FOR_INV_ING_PAL)) {
				esConvFortInvIngAdmonPal2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_DISCI_DER_2018)) {
				esConvDiscDerecho2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_CIENC_AGRA_18)) {
				esConvPosgradoCienciasAgrarias2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_OBS_DERE_18)) {
				esConvObservatoriosDerecho2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_CIEN_AGRO_1)) {
				esConvCienciasAgrariasPal_1_2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_CIEN_AGRO_2)) {
				esConvCienciasAgrariasPal_2_2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_CIEN_AGRO_3)) {
				esConvCienciasAgrariasPal_3_2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ODONTO_2017)) {
				esConvOdontologia2017 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ODONTO_2017_M2)) {
				esConvOdontologia2017_M2 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_PRD)) {
				esConvPurdue = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_CUND)) {
				esConvCundinamarca = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_CUND_2019)) {
				esConvCundinamarca2019 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_SUE_2017)) {
				esConvSUE2017 = true;
			}
			
			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_GRUP_SEM_ART_2022)) {
				esConvGrupSemArtes2022 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.EXT_SOL_2018)) {
				esConvExtSol2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_MED_LEGAL_18)) {
				esConvMedLegal2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals("CON_INNO_ES_2016")
					|| convocatoriaActual.getRestriccion().getId().equals("CON_INNO_ES_2016_M3")) {
				esConvExtSol2016 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_INNO_ES_2017)
					|| convocatoriaActual.getRestriccion().getId()
							.equals(RestriccionConvocatoria.CONV_INNO_ES_2017_M2)) {
				esConvExtSol2017 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_MED_TRAS_17)) {
				esConvMedTraslacional2017 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_CIEN_AGRA_BOG)) {
				esConvCienciasAgrarias2017 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ARTES_MOD_A)) {
				esConvArtesModA2017 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ARTES_MOD_B)) {
				esConvArtesModB2017 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_FALS_BORDA_2017)) {
				esConvFalsBorda2017 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_FALS_BORDA_2018)) {
				esConvFalsBorda2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals("TIEMPO_VOLVER")
					|| convocatoriaActual.getRestriccion().getId().equals("CON_COR_TEC_AGRO")) {
				esTiempoVolver = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CON_INNO_PED_DIEB_2)) {
				esConvoInnoPedDIEB_2 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals("CONV_JI_COL_2014")
					&& convocatoriaActual.getRequisitosConvTexto() != null
					&& convocatoriaActual.getRequisitosConvTexto().equals("2015")) {
				verFichaMinJI2015 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ALI_PAL_2018)) {
				esConvAlianzasPalmira2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ADMON_MAN_2018)) {
				esConvAdmonManizales2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_GRU_NO_REC_MAN)) {
				esConvAdmonManizalesGruposNoRec2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ALI_NAL_2018)) {
				esConvocatoriaAlianzas2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_ALI_NAL_2019)) {
				esConvocatoriaAlianzas2019 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_PRY_BOG_2019)) {
				esConvProyectosBog2019 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_PRY_PAL_2019)) {
				esConvProyectosPal2019 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_DOC_EM_758_2018)) {
				esConvDocNalEmp758_2018 = true;
			}

			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.SEDES_PRES_NAL_2019)) {
				esConvInnovaSedesPresNal = true;
			}
			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_CP_2019)) {
				setEsConvCP2019(true);
			}
			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_UROSARIO)) {
				setEsConvURosario(true);
			}
			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_LABS_2024_M1)) {
				esConvocatoriaFortalecimientoLabs2024_M1 = true;
			}
			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_LABS_2024_M2)) {
				esConvocatoriaFortalecimientoLabs2024_M2 = true;
			}
			if (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.CONV_REDES)) {
				esConvocatoriaRedes = true;
			}
		} else if (convocatoriaActual.getTipo().getId().equals("ES7")) {
			esConvExtSol2018 = true;
		}
		
		if (convocatoriaActual.getId() != null && convocatoriaActual.getId().equals(1288L)) {
			esConvocatoriaPlanArmonizacion = true;
		}

		if (convocatoriaActual.getAreaDirigida() != null && convocatoriaActual.getAreaDirigida().equals("N")) {
			mostrarObjetivosEspecificos = false;
		}

		if (convocatoriaActual.getTieneProyectoAsociado() != null) {
			if (!convocatoriaActual.getTieneProyectoAsociado()) {
				mostrarProyectoAsociado = false;
			} else {
				mostrarProyectoAsociado = true;
			}
		} else {
			mostrarProyectoAsociado = true;
		}

		if (convocatoriaActual.getConMostrarResumen() != null) {
			if (convocatoriaActual.getConMostrarResumen().equals(0L)) {
				mostrarResumen = false;
			} else {
				mostrarResumen = true;
			}
		} else {
			mostrarResumen = true;
		}

		if (convocatoriaActual.getConMostrarResultados() != null) {
			if (convocatoriaActual.getConMostrarResultados().equals(0L)) {
				mostrarResultados = false;
			} else {
				mostrarResultados = true;
			}
		} else {
			mostrarResultados = true;
		}

		if (convocatoriaActual.getConMostrarBiodiversidad() != null) {
			if (convocatoriaActual.getConMostrarBiodiversidad().equals(0L)) {
				mostrarBiodiversidad = false;
			} else {
				mostrarBiodiversidad = true;
			}
		} else {
			mostrarBiodiversidad = true;
		}

		if (!esNulo(convocatoriaActual.getMostrarLaboratorios())) {
			if (convocatoriaActual.getMostrarLaboratorios().equals("S"))
				mostrarLaboratorios = true;
			else
				mostrarLaboratorios = false;
		} else
			mostrarLaboratorios = true;

		if (convocatoriaActual.getConMostrarDescripcionProblema() != null) {
			if (convocatoriaActual.getConMostrarDescripcionProblema().equals(0L)) {
				mostrarDescripcionProblema = false;
			} else {
				mostrarDescripcionProblema = true;
			}
		} else {
			mostrarDescripcionProblema = true;
		}

		if (convocatoriaActual.getConValidarEquipoTrabajo() != null) {
			if (!convocatoriaActual.getConValidarEquipoTrabajo().equals(0L)) {
				validarEquipoTrabajo = true;
			} else {
				validarEquipoTrabajo = false;
			}
		} else {
			validarEquipoTrabajo = false;
		}

		if (convocatoriaActual.getTipoInvestigadoresConvocatoria() != null) {
			tipoInvestigadoresConvocatoria = convocatoriaActual.getTipoInvestigadoresConvocatoria();
		}

		if (convocatoriaActual.getMensajeRubrosBasicos() != null) {
			mostrarMensajeRubros = true;
			mensajeRubrosBasicos = convocatoriaActual.getMensajeRubrosBasicos();
		} else {
			mostrarMensajeRubros = false;
			mensajeRubrosBasicos = "";
		}

		if (convocatoriaActual.getMostrarObjetivos() != null && convocatoriaActual.getMostrarObjetivos() == 1) {
			mostrarObjetivos = true;
		} else {
			mostrarObjetivos = false;
		}
	}

	public void adicionarRegionImpacto() {
		if (esCadenaVacia(ciudadRegionImpacto.getId())) {
			mensajeError("Por favor, seleccione una ciudad valida para el registro.");
			return;
		}
		Ciudad ciudad = new Ciudad();
		String consultaCiudad = "select e from Ciudad e where e.id = '" + ciudadRegionImpacto.getId() + "'";
		List lisCiudad = servicioGeneral.obtenerObjetos(consultaCiudad);

		if (lisCiudad != null && lisCiudad.size() > 0) {
			ciudad = (Ciudad) lisCiudad.get(0);
		}
		for (Ciudad c : getProyectoActual().getListaCiudadesProyecto()) {
			if (c.getId().equals(ciudad.getId())) {
				mensajeError("La ciudad ya se encuentra registrada en el proyecto..");
				return;
			}
		}
		proyectoActual.adicionarCiudad(ciudad);
		ciudadRegionImpacto = new Ciudad();
		departamentoRegionImpacto = new Departamento();
	}

	public void eliminarRegionImpacto() {
		proyectoActual.borrarCiudad(ciudadActualRegionImpacto);
	}

	public void cambiarDepartamentoRegionImpacto() {
		departamentoRegionImpacto = buscarDepartamentoRegionImpacto((departamentoRegionImpacto.getId()));
		listaCiudadesRegionImpacto = servicioGeneral.obtenerUbicacion("Ciudad", "", "departamento",
				departamentoRegionImpacto,true);
		ciudadItemRegionImpacto = new SelectItem[listaCiudadesRegionImpacto.size()];
		for (int i = 0; i < listaCiudadesRegionImpacto.size(); i++) {
			Ciudad ci = (Ciudad) listaCiudadesRegionImpacto.get(i);
			ciudadItemRegionImpacto[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	// DEFINICION DE FUNCIONES MISCELANEAS
	private Departamento buscarDepartamentoRegionImpacto(String id) {
		// BUSCA UN DEPARTAMENTO DE ACUERDO A SU ID
		Departamento d = new Departamento();
		int i = 0;
		while (i < listaDepartamentosRegionImpacto.size()) {
			d = (Departamento) listaDepartamentosRegionImpacto.get(i);
			if (id.equals(d.getId()))
				break;
			i = i + 1;
		}
		return d;
	}

	public void obtenerListaDepartamentosRegionImpacto() {
		listaDepartamentosRegionImpacto = servicioGeneral.obtenerUbicacion("Departamento", "", "", null,true);
		departamentoItemRegionImpacto = new SelectItem[listaDepartamentosRegionImpacto.size()];
		for (int i = 0; i < listaDepartamentosRegionImpacto.size(); i++) {
			Departamento dep = (Departamento) listaDepartamentosRegionImpacto.get(i);
			departamentoItemRegionImpacto[i] = new SelectItem(dep.getId(), dep.getNombre());
			dep = null;
		}
	}

	public void obtenerListaCiudadesRegionImpacto() {
		listaCiudadesRegionImpacto = servicioGeneral.obtenerUbicacion("Ciudad", "", "departamento",
				(Departamento) listaDepartamentosRegionImpacto.get(0),true);
		ciudadItemRegionImpacto = new SelectItem[listaCiudadesRegionImpacto.size()];
		for (int i = 0; i < listaCiudadesRegionImpacto.size(); i++) {
			Ciudad ci = (Ciudad) listaCiudadesRegionImpacto.get(i);
			ciudadItemRegionImpacto[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	public void revisarPais() {
		if (idPaisNacimiento != null && !idPaisNacimiento.equals("") && idPaisNacimiento.equals("CO")) {
			siColombia = true;
			obtenerListaDepartamentos();
			obtenerListaCiudades();
		} else {
			siColombia = false;
			obtenerListaCiudadesDiferentesColombia();
		}
	}

	public void cambiarDepartamento() {
		setListaCiudades(servicioGeneral.obtenerObjetosLimitado(Ciudad.class,
				"select #id e.id, #nombre e.nombre from Ciudad e where e.departamento.id like '"
						+ departamentoActual.getId() + "' and e.sigla is not null order by e.nombre asc"));
		setCiudadItem(new SelectItem[getListaCiudades().size()]);
		for (int i = 0; i < getListaCiudades().size(); i++) {
			Ciudad ci = (Ciudad) getListaCiudades().get(i);
			getCiudadItem()[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	private Departamento buscarDepartamento(String id) {
		Departamento d = new Departamento();
		int i = 0;
		while (i < listaDepartamentos.size()) {
			d = (Departamento) listaDepartamentos.get(i);
			if (id.equals(d.getId()))
				break;
			i = i + 1;
		}
		return d;
	}

	private void obtenerListaDepartamentos() {
		listaDepartamentos = servicioGeneral.obtenerObjetosLimitado(Departamento.class,
				"select #id e.id, #nombre e.nombre from Departamento e where e.id like 'CO%' order by e.nombre asc");
		departamentoItem = new SelectItem[listaDepartamentos.size()];
		for (int i = 0; i < listaDepartamentos.size(); i++) {
			Departamento dep = (Departamento) listaDepartamentos.get(i);
			departamentoItem[i] = new SelectItem(dep.getId(), dep.getNombre());
			dep = null;
		}
	}

	private void obtenerListaCiudades() {
		listaCiudades = servicioGeneral.obtenerUbicacion("Ciudad", "", "departamento",
				(Departamento) listaDepartamentos.get(0),false);
		ciudadItem = new SelectItem[listaCiudades.size()];
		for (int i = 0; i < listaCiudades.size(); i++) {
			Ciudad ci = (Ciudad) listaCiudades.get(i);
			ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	private void obtenerListaCiudadesDiferentesColombia() {
		String idPaisBus = idPaisNacimiento.substring(0, 2);
		String hql = "select #id e.id, #nombre e.nombre from Ciudad e where e.id like ('" + idPaisBus + "%')";
		listaCiudades = servicioGeneral.obtenerObjetosLimitado(Ciudad.class, hql);
		ciudadItem = new SelectItem[listaCiudades.size()];
		for (int i = 0; i < listaCiudades.size(); i++) {
			Ciudad ci = (Ciudad) listaCiudades.get(i);
			ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	public void obtenerListaInstituciones() {
		List<FuenteFinanciacion> listaInstituciones = servicioGeneral.obtenerObjetos(
				FuenteFinanciacion.class,
				"select e from FuenteFinanciacion e where e.internaExterna = 'E' or e.id = 1 order by e.descripcion asc");
		institucionesItem = new SelectItem[listaInstituciones.size()];
		for (int i = 0; i < listaInstituciones.size(); i++) {
			FuenteFinanciacion ins = (FuenteFinanciacion) listaInstituciones.get(i);
			institucionesItem[i] = new SelectItem(ins.getId(), ins.getDescripcion());
			ins = null;
		}
	}

	/**
	 * Eliminar producto.
	 */
	public void eliminarProducto() {
		proyectoActual.borrarProductoProyecto(productoSeleccionado);
	}

	/**
	 * Buscar tipo rubro.
	 * 
	 * @param id the id
	 * @return the tipo rubro
	 */
	public TipoRubro buscarTipoRubro(Long id) {
		// BUSCA EL TIPO DE RUBRO POR EL ID
		int i = 0;
		while (i < listaTiposRubros.size()) {
			TipoRubro tr = (TipoRubro) listaTiposRubros.get(i);
			if (id.compareTo(tr.getId()) == 0) {
				return tr;
			}
			i = i + 1;
		}
		return null;
	}

	/**
	 * Cargar listas.
	 */
	// Cargar valores de listas
	protected void cargarListas() {

		cargarCiudad();
		// obtenerListaDepartamentos();
		// obtenerListaCiudades();
		obtenerListaInstituciones();

		if (esConvInnovaSedesPresNal) {
			listaMontosFinanciar.add(new montoFinanciar("Estudiante de Doctorado", "$10.000.000"));
			listaMontosFinanciar.add(
					new montoFinanciar("Estudiante de Maestría o especialidad en el área de la salud", "$8.000.000"));
			listaMontosFinanciar.add(new montoFinanciar(
					"Semillero de Investigación, creación o innovación (mínimo tres estudiantes de semillero)",
					"$5.000.000"));
			listaMontosFinanciar.add(new montoFinanciar("Estudiante PEAMA", "$7.000.000"));
		} else if (esConvProyectosBog2019) {
			listaMontosFinanciar.add(new montoFinanciar("Estudiante de Doctorado", "$15.000.000"));
			listaMontosFinanciar.add(
					new montoFinanciar("Estudiante de Maestría o especialidad en el área de la salud", "$10.000.000"));
			listaMontosFinanciar.add(new montoFinanciar(
					"Semillero de Investigación y extensión de estudiantes de pregrado", "$5.000.000"));
			listaMontosFinanciar.add(new montoFinanciar("Estudiante de pregrado", "$3.000.000"));
			listaMontosFinanciar
					.add(new montoFinanciar("Producto de apropiación social del conocimiento", "$5.000.000"));
			listaMontosFinanciar.add(new montoFinanciar(
					"Formulación de un proyecto de investigación para presentar a convocatorias financiadas por entidades externas nacionales o internacionales",
					"$5.000.000"));
		} else if (esConvProyectosPal2019) {
			listaMontosFinanciar.add(new montoFinanciar("Cada estudiante de doctorado", "$16.000.000"));
			listaMontosFinanciar.add(new montoFinanciar("Cada estudiante de maestría", "$10.000.000"));
			listaMontosFinanciar.add(new montoFinanciar("Un semillero de investigación", "$5.000.000"));
			listaMontosFinanciar.add(new montoFinanciar(
					"Un proyecto de investigación o innovación formulado para ser presentado a una convocatoria nacional o internacional",
					"$3.000.000"));
		}

		departamentoActual = new Departamento();
		ciudadActual = new Ciudad();

		tiposFuenteItem = new SelectItem[2];
		tiposFuenteItem[0] = new SelectItem(new Integer(1), "Interna");
		tiposFuenteItem[1] = new SelectItem(new Integer(2), "Externa");

		// Tipo proyecto
		categoriaItemsPpal = crearListaItemDominioDetalle(DOMINIO_CATEGORIA);

		// tipologías Investigación
		Parametro parametroTipologiaProyectos = null;
		if (convocatoriaActual != null && convocatoriaActual.getTipo() != null) {
			List<Parametro> parametros = servicioGeneral.obtenerObjetos(Parametro.class,
					"select p from Parametro p where p.nombre = '" + Parametro.PAR_TIPOLOGIA_PROYECTO
							+ "' and p.profesion = '" + convocatoriaActual.getTipo().getId() + "'");
			if (!esListaVacia(parametros)) {
				parametroTipologiaProyectos = parametros.get(0);
			}
		}

		if (parametroTipologiaProyectos != null) {
			categoriaItemsECP = crearListaItemDominioDetalle(parametroTipologiaProyectos.getValor());
		} else if (convocatoriaActual.getPadre().getId().equals(502L)) {
			categoriaItemsECP = crearListaItemDominioDetalle("TIPOLOGIA_CONV_LAB_MAN_2019");
		} else {
			if(esConsulta) {
				categoriaItemsECP = crearListaItemDominioDetalle(DOMINIO_CATEGORIA_INV, false);
			}else {
				categoriaItemsECP = crearListaItemDominioDetalle(DOMINIO_CATEGORIA_INV);
			}
		}

		if (esConvSesquicente) {
			categoriaItemsECP = crearListaItemDominioDetalle(DOMINIO_TIPOLOGIA_PROYECTOS_CONV_SESQUIC);
		}

		if (esConvSUE2017 || esConvProyectosBog2019 || esConvProyectosPal2019) {
			categoriaItemsECP = crearListaItemDominioDetalle(DOMINIO_TIPOLOGIA_PROYECTOS_CONV_SUE_2017);
		}

		if (esConvExtSol2018) {
			categoriaItemsECP = crearListaItemDominioDetalle(DOMINIO_TIPOLOGIA_PROYECTOS_EXT_SOL_2018);
		}

		if (esConvUnInnova2019) {
			categoriaItemsECP = crearListaItemDominioDetalle(TIPOLOGIA_PROYECTOS_UNINNOVA_2019);
		}

		if (esConvUnInnova2021) {
			categoriaItemsECP = crearListaItemDominioDetalle(TIPOLOGIA_PROYECTOS_UNINNOVA_2021);
		}

		if (esConvocatoriaAlianzas2018) {
			categoriaItemsECP = crearListaItemDominioDetalle(DOMINIO_TIPOLOGIA_PROYECTOS_ALIANZAS_2018);
		}

		if (esConvocatoriaAlianzas2019) {
			categoriaItemsECP = crearListaItemDominioDetalle(DOMINIO_TIPOLOGIA_PROYECTOS_ALIANZAS_2019);
		}

		if (esConvRepotenciacionLab2018) {
			categoriaItemsECP = crearListaItemDominioDetalle(DOMINIO_TIPOLOGIA_PROYECTOS_CONV_REPO_EQUIPOS_LAB);
		}

		if (esConvDocNalEmp758_2018) {
			categoriaItemsECP = crearListaItemDominioDetalle(DOMINIO_TIPOLOGIA_PROYECTOS_CONV_DOC_NAL_EMP_758_2018);
		}

		if (esConvCP2019) {
			categoriaItemsECP = crearListaItemDominioDetalle(DOMINIO_TIPOLOGIA_PROYECTOS_CONV_CP_2019);
		}

		if (convocatoriaActual.getDominioTipologiaProyectos() != null) {
			categoriaItemsECP = crearListaItemDominioDetalle(convocatoriaActual.getDominioTipologiaProyectos());
			if (categoriaItemsECP == null) {
				int idx = convocatoriaActual.getDominioTipologiaProyectos().split(",").length;
				categoriaItemsECP = new SelectItem[idx];
				int x = 0;
				for (SelectItem selectItem : crearListaItemDominioDetalle(DOMINIO_CATEGORIA_INV)) {
					if (convocatoriaActual.getDominioTipologiaProyectos().contains(selectItem.getValue().toString())) {
						categoriaItemsECP[x] = selectItem;
						x++;
					}
				}
			}
		}

		if (convocatoriaActual != null) {
			List<Parametro> parametrosTipoProyLab = servicioGeneral.obtenerObjetos(Parametro.class,
					"select p from Parametro p where p.nombre = '" + Parametro.PAR_SUBTIPO_FINANCIACION
							+ "' and p.profesion = '" + convocatoriaActual.getId() + "'");
			if (!esListaVacia(parametrosTipoProyLab)) {
				Parametro parametroTipoProyectoLaboratorio = parametrosTipoProyLab.get(0);
				subtiposFinanciacion = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione(
						Long.parseLong(parametroTipoProyectoLaboratorio.getValor()));
			}
		}

		// Tipos proyectos laboratorios
		if (convocatoriaActual != null && convocatoriaActual.getTipo() != null) {

			List<Parametro> parametrosTipoProyLab = servicioGeneral.obtenerObjetos(Parametro.class,
					"select p from Parametro p where p.nombre = '" + Parametro.PAR_TIPO_PROYECTO_LAB
							+ "' and p.profesion = '" + convocatoriaActual.getId() + "'");
			if (!esListaVacia(parametrosTipoProyLab)) {
				Parametro parametroTipoProyectoLaboratorio = parametrosTipoProyLab.get(0);
				siTiposProyLabs = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione(
						Long.parseLong(parametroTipoProyectoLaboratorio.getValor()));
			}

			List<Parametro> parametrosSubtipoProyLab = servicioGeneral.obtenerObjetos(Parametro.class,
					"select p from Parametro p where p.nombre = '" + Parametro.PAR_SUBTIPO_PROYECTO_LAB
							+ "' and p.profesion = '" + convocatoriaActual.getId() + "'");
			if (!esListaVacia(parametrosSubtipoProyLab)) {
				Parametro parametroSubtipoProyectoLaboratorio = parametrosSubtipoProyLab.get(0);
				siSubtiposProyLabs = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione(
						Long.parseLong(parametroSubtipoProyectoLaboratorio.getValor()));
			}

		}

		rolUniversidadItems = crearListaItemDominioDetalle(DOMINIO_ROL);

		// Tipo financiacion
		mecanismoSolicitudItems = crearListaItemDominioDetalle(DOMINIO_TIPO_FINANCIACION);

		// Tipo financiacion
		objSocioeconomicoItems = crearListaItemDominioDetalle(DOMINIO_OBJETIVO_SOCIO_EC);

		List<FuenteFinanciacion> listaEntidadesProyectoPrograma = servicioGeneral.obtenerObjetos(
				FuenteFinanciacion.class,
				"select e from FuenteFinanciacion e where e.internaExterna = 'E' or e.id = 1 order by e.descripcion asc");
		entidadesProyectoProgramaItems = new SelectItem[listaEntidadesProyectoPrograma.size()];
		for (int i = 0; i < listaEntidadesProyectoPrograma.size(); i++) {
			FuenteFinanciacion ff = (FuenteFinanciacion) listaEntidadesProyectoPrograma.get(i);
			entidadesProyectoProgramaItems[i] = new SelectItem(ff.getId(), ff.getDescripcion());
		}

		// Areas de la ciencia
		boolean validarEstado = false;
		List<DominioDetalle> listaAreaCiencia = servicioGeneral.obtenerDominioDetalle(DOMINIO_AREA_CIENCIA,
				validarEstado);
		areaCienciaItems = crearListaItems(listaAreaCiencia);

		cambiarArea();
		cambiarAreaSec();

		List<DominioDetalle> listaObjetivosDesarrolloSostenible = servicioGeneral
				.obtenerDominioDetalle(DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE);
		objetivosDesarrolloSostenibleItems = crearListaItems(listaObjetivosDesarrolloSostenible);

		// Activos Propiedad Intelectual
		tiposActivosPIItem = crearListaItemDominioDetalle(DOMINIO_ACTIVO_PI);

		// Se carga listado de sedes
		List<Sede> sedesUN = servicioGeneral.obtenerObjetos(Sede.class, "select e from Sede e");
		if (esConvSUE2017) {
			sedesUN = servicioGeneral.obtenerObjetos(Sede.class, "select e from Sede e where  e.id = 2 ");
		}
		sedeItem = new SelectItem[sedesUN.size()];
		for (int i = 0; i < sedesUN.size(); i++) {
			Sede sede = (Sede) sedesUN.get(i);
			sedeItem[i] = new SelectItem(sede.getId(), sede.getNombre());
		}
		sedeSel = "";
		cambiarSede();
		if (esConvExtSol2018) {
			sedesUN = servicioGeneral.obtenerObjetos(Sede.class, "select e from Sede e where  e.id != 1 ");
			setSedePresItem(new SelectItem[sedesUN.size()]);
			for (int i = 0; i < sedesUN.size(); i++) {
				Sede sede = (Sede) sedesUN.get(i);
				getSedePresItem()[i] = new SelectItem(sede.getId(), sede.getNombre());
			}
			sedeSelPres = "";
		}

		// cargarTiposDocumento
		// List<TipoDocumento> listaTipoDocumento =
		// servicioGeneral.obtenerObjetos(TipoDocumento.class,
		// "from TipoDocumento td where td.id not in ('N','D')");

		List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerTiposDeDocumento();

		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];

		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}

		List<TipoFormacion> listaTipoFormacion = servicioGeneral.obtenerTiposDeFormacion();
		tipoFormacionItem = new SelectItem[listaTipoFormacion.size()];

		for (int i = 0; i < listaTipoFormacion.size(); i++) {
			TipoFormacion td = listaTipoFormacion.get(i);
			tipoFormacionItem[i] = new SelectItem(td.getId(), td.getNombre());
		}

		List<EstadoCivil> listaEstadoCivil = servicioGeneral.obtenerTiposDeEstadoCivil();
		estadoCivilItem = new SelectItem[listaEstadoCivil.size()];

		for (int i = 0; i < listaEstadoCivil.size(); i++) {
			EstadoCivil td = listaEstadoCivil.get(i);
			estadoCivilItem[i] = new SelectItem(td.getId(), td.getNombre());
		}

		cargarTipoVinculacionInvestigador();

		if (esConvProyectos2016_2018 || esConvProyectos2017_2018 || esConvPurdue) {
			mostrarOpcionTiempoDedicacionFormulacion = false;
		}
		if (esConvoInnoPedDIEB_2) {
			mostrarObjetivosEspecificos = false;
		}

		// // PLAN GLOBAL DE DESARROLLO
		// String periodoActual = "2016-2017-2018";
		//
		// // Politica
		// politica = "Plan global de desarrollo 2016-2018: Autonomía
		// responsable y excelencia como hábito";
		//
		// // lineasAccion
		// List<DominioDetalle> listaAccion =
		// servicioGeneral.obtenerObjetos(DominioDetalle.class, "select dd from
		// Dominio d, DominioDetalle dd where d.id = dd.identificador.id and
		// d.tipo ='" + DOMINIO_LINEA_ACCION + "' and dd.observacion = '" +
		// periodoActual + "' order by dd.descripcion");
		//
		// lineasAccionItems = crearListaItems(listaAccion);
		//
		// String codLinea = "7106"; // 2016-2018
		// List<DominioDetalle> listaPrograma =
		// servicioGeneral.obtenerObjetos(DominioDetalle.class, "select dd from
		// Dominio d, DominioDetalle dd where d.id = dd.identificador.id and
		// d.tipo ='" + DOMINIO_PROGRAMA + "' and dd.estado='" + codLinea + "'
		// order by dd.descripcion");
		// programaItems = crearListaItems(listaPrograma);

		// PLAN GLOBAL DE DESARROLLO
		String periodoActual = "2025-2026-2027";
		String idPlanActual = "24"; //pendiente buscar una mejor estrategia en genera para el cambio de plan de desarrollo
		etiquetas2025PlanDllo = true;

	

		// Politica
		politica = "Plan Global de Desarrollo 2025-2027 ACCESO AL BIEN COMÚN DEL CONOCIMIENTO Y EL DERECHO 2025";
		
		List<DominioDetalle> listaAccion = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_LINEA_ACCION + "' and dd.observacion = '" + periodoActual
						+ "' order by dd.descripcion");

		lineasAccionItems = crearListaItems(listaAccion);
		

		// lineasAccion
		List<PlanGlobalDesarrollo> listaComponentes = servicioGeneral.obtenerObjetos(PlanGlobalDesarrollo.class,
				"select p from PlanGlobalDesarrollo p where p.padre.id =  '" + idPlanActual
						+ "' order by p.id");

		componentesItems = new SelectItem[listaComponentes.size()];
		for (int i = 0; i < listaComponentes.size(); i++) {
			PlanGlobalDesarrollo componente = listaComponentes.get(i);
			componentesItems[i] = new SelectItem(componente.getId(), componente.getNombre());
		}

		String codLinea = "0"; // 2022-2024
		List<DominioDetalle> listaPrograma = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_PROGRAMA + "' and dd.estado='" + codLinea + "' order by dd.descripcion");
		programaItems = crearListaItems(listaPrograma);

		// Dependencias completas para ejecución financiera
		List<Dependencia> listadoTodasDependencias = servicioDependencia.obtenerDependenciasActivasFacultadYSede();
		dependenciaItemCompleta = servicioDependencia.crearSelectItem(listadoTodasDependencias);

		if (mostrarMenuFormulario) {
			
			productosConfiguradosConvocatoria = servicioModalidad.obtenerProductosConvocatoria(convocatoriaActual);
			
		}
		
		cargarListaTipoProductos();
		cargarFuentesFinanciacionInternas();
		cargarFuentesFinanciacionExternas();

		// Se carga lista de objetivos
		actualizarListaObjetivosMetas();
	}

	/**
	 * Metodo para cargar todas las fuentes intermas.
	 */
	public void cargarFuentesFinanciacionInternas() {
		String hql = "select ff from FuenteFinanciacion ff where ff.estado in ('V') and ff.internaExterna = 'I' and ff.descripcion not like '%CODIGO%' order by ff.descripcion)";
		List<FuenteFinanciacion> lista = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class, hql);
		fuentesInternasItem = new SelectItem[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			FuenteFinanciacion d = (FuenteFinanciacion) lista.get(i);
			String nombre = d.getDescripcion().toUpperCase();
			if (nombre != null && nombre.length() > 80) {
				nombre = nombre.substring(0, 80) + "...";
			}
			fuentesInternasItem[i] = new SelectItem(d.getId(), nombre);
		}
	}

	/**
	 * Cambiar facultad.
	 */
	// Se cargan las dependneicas cuando cambia una facultad en la vista.
	public void cambiarFacultad() {
		dependenciasUN = servicioDependencia.obtenerDependenciasXFacultad(facultadSel);

		// Si tiene mas dependencias la facultad.
		if (!esListaVacia(dependenciasUN)) {
			dependenciaItem = servicioDependencia.crearSelectItem(dependenciasUN);
		} else {
			// Si no tiene mas dependencias se carga la misma.
			dependenciasUN = new ArrayList<Dependencia>();
			dependenciasUN.add(servicioDependencia.obtenerDependencia(facultadSel));
			dependenciaItem = servicioDependencia.crearSelectItem(dependenciasUN);

		}
		dependenciaProyecto = "";
	}

	public void cambiarFacultadAportante() {
		dependenciasUN = servicioDependencia.obtenerDependenciasXFacultad(facultadSelAportante);

		// Si tiene mas dependencias la facultad.
		if (!esListaVacia(dependenciasUN)) {
			dependenciaAportanteItem = servicioDependencia.crearSelectItem(dependenciasUN);
		} else {
			// Si no tiene mas dependencias se carga la misma.
			dependenciasUN = new ArrayList<Dependencia>();
			dependenciasUN.add(servicioDependencia.obtenerDependencia(facultadSelAportante));
			dependenciaAportanteItem = servicioDependencia.crearSelectItem(dependenciasUN);

		}
		dependenciaAportanteProyecto = "";
	}

//	public void cambiarFacultadLabs() {
//		dependenciasUN = servicioDependencia.obtenerDependenciasXFacultad(facultadSel);
//
//		// Si tiene mas dependencias la facultad.
//		if (!esListaVacia(dependenciasUN)) {
//			dependenciaItem = servicioDependencia.crearSelectItem(dependenciasUN);
//		} else {
//			// Si no tiene mas dependencias se carga la misma.
//			dependenciasUN = new ArrayList<Dependencia>();
//			dependenciasUN.add(servicioDependencia.obtenerDependencia(facultadSel));
//			dependenciaItem = servicioDependencia.crearSelectItem(dependenciasUN);
//		}
//
//		dependenciaProyecto = "";
//	}

	/**
	 * Cambiar area sec.
	 */
	// Se actualizan el listado de areas de la ciencia segundarias.
	public void cambiarAreaSec() {
		subAreaCienciaSecItems = cambiarAreaCiencia(areaCienciaSec);
		subAreaCienciaSec = "";
	}

	/**
	 * Cambiar programa.
	 */
	public void cambiarPrograma() {
		// Programa
		String codLinea = String.valueOf(proyectoActual.getCodLineaAccion()) != null
				? String.valueOf(proyectoActual.getCodLineaAccion())
				: "7101";
		List<DominioDetalle> listaPrograma = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_PROGRAMA + "' and dd.estado='" + codLinea + "' order by dd.descripcion");
		programaItems = new SelectItem[listaPrograma.size()];
		for (int i = 0; i < listaPrograma.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaPrograma.get(i);
			programaItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
		}
	}

	/**
	 * Activar vista formularios inicial.
	 */
	public void activarVistaFormulariosInicial() {

		// Opciones del formulario
		mostrarOpcionTiempoDedicacionFormulacion = true;
		mostrarObjetivosEspecificos = true;
		mostrarBiodiversidad = true;
	}

	public void eliminarProyectoPrograma() {
		proyectoActual.borrarProyectoPrograma(proyectoProgramaSeleccionado);
		proyectoProgramaSeleccionado = new ProyectoPrograma();
	}

	/**
	 * Eliminar objetivo.
	 */
	public void eliminarObjetivo() {
		if (objetivoTabla.getListaMetas().isEmpty()) {
			proyectoActual.borrarObjetivoEspecifico(objetivoTabla);
			actualizarListaObjetivosMetas();
		} else
			mensajeError(objetoEspecifico, "No puede eliminar el objetivo seleccionado dado que tiene "
					+ objetivoTabla.getListaMetas().size() + " metas asociadas");

	}

	/**
	 * Eliminar meta.
	 */
	public void eliminarMeta() {

		if (proyectoActual.getListaActividadesAsociadasAMeta(metaTabla).isEmpty()) {
			ObjetivoEspecifico objetivoObjeto = buscarObjetivo(metaTabla.getObjetivo().getNumeroOrden().toString(),
					proyectoActual.getListaObjetivos());
			objetivoObjeto.borrarMetaProyecto(metaTabla);
		} else
			mensajeError(metaProyecto, "La meta seleccionada se encuentra asociada a una actividad");

	}

	/**
	 * Cambiar area.
	 */
	// Se actualizan el listado de areas de la ciencia primarias.
	public void cambiarArea() {
		subAreaCienciaItems = cambiarAreaCiencia(areaCiencia);
		subAreaCiencia = "";
	}

	public void cambiarAreaInv() {
		subAreaCienciaItemsInv = cambiarAreaCiencia(areaCienciaInv);
		subAreaCienciaInv = "";
	}

	/**
	 * Cambiar area ciencia.
	 * 
	 * @param areaCiencia the area ciencia
	 * @return the select item[]
	 */
	protected SelectItem[] cambiarAreaCiencia(String areaCiencia) {
		String consultaAreasSec = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
				+ DOMINIO_SUB_AREA_CIENCIA + "' and  dd.estado = '" + areaCiencia + "' order by dd.descripcion";
		List<DominioDetalle> listaSubAreaCiencia = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				consultaAreasSec);
		return crearListaItems(listaSubAreaCiencia);
	}

	/**
	 * Cargar ciudad.
	 */
	public void cargarCiudad() {
		ciudadItemList = new ArrayList<SelectItem>();

		String consulta = "select cc from Ciudad cc where cc.id not in ('CO25', 'CO11', '00', 'CO02999', 'CO68')";
		List<Ciudad> lista = servicioGeneral.obtenerObjetos(Ciudad.class, consulta);
		for (int i = 0; i < lista.size(); i++) {
			Ciudad ciudad = (Ciudad) lista.get(i);
			ciudadItemList.add(new SelectItem(ciudad.getId(), ciudad.getNombre()));
		}
	}

	/**
	 * Metodo que se ejecuta cuando cambia alguna sede en la vista.
	 */
	public void cambiarSede() {
		if (!esCadenaVacia(sedeSel)) {
			// Si es sede de presencia nacional
			if ((new Sede(sedeSel)).isEsSedePresenciaNacional()) {
				mostrarFacultades = false;
				esEntidSedePresNal = true;
				dependenciasUN = servicioDependencia.obtenerDependenciaXSede(sedeSel);
				dependenciaItem = servicioDependencia.crearSelectItem(dependenciasUN);
				dependenciaAportanteItem = servicioDependencia.crearSelectItem(dependenciasUN);
				dependenciaProyecto = "";
			} else {
				// Si es una sede con facultad.
				mostrarFacultades = true;
				esEntidSedePresNal = false;
				List<Dependencia> facultadesUN = servicioDependencia.obtenerFacultadesXSede(sedeSel);
				facultadAportanteItem = servicioDependencia.crearSelectItem(facultadesUN);
				facultadItem = servicioDependencia.crearSelectItem(facultadesUN);
				facultadSel = ((Dependencia) facultadesUN.get(0)).getId().toString();
				cambiarFacultad();
			}
		} else {
			dependenciaItem = new ArrayList<SelectItem>();
			dependenciaProyecto = "";
			dependenciaAportanteProyecto = "";
			mostrarFacultades = false;
		}
	}

	public void cambiarSedeAportante() {
		if (!esCadenaVacia(sedeSelAportante)) {
			// Si es sede de presencia nacional
			if ((new Sede(sedeSelAportante)).isEsSedePresenciaNacional()) {
				mostrarFacultades = false;
				esEntidSedePresNal = true;
				dependenciasUN = servicioDependencia.obtenerDependenciaXSede(sedeSelAportante);
				dependenciaAportanteItem = servicioDependencia.crearSelectItem(dependenciasUN);
				dependenciaProyecto = "";
			} else {
				// Si es una sede con facultad.
				mostrarFacultades = true;
				esEntidSedePresNal = false;
				List<Dependencia> facultadesUN = servicioDependencia.obtenerFacultadesXSede(sedeSelAportante);
				facultadAportanteItem = servicioDependencia.crearSelectItem(facultadesUN);
				facultadSelAportante = ((Dependencia) facultadesUN.get(0)).getId().toString();
				cambiarFacultadAportante();
			}
		} else {
			dependenciaItem = new ArrayList<SelectItem>();
			dependenciaAportanteProyecto = "";
			mostrarFacultades = false;
		}
	}

	public void cambiarSedeUniversidad(String sede, String facultad) {

	}

	/******************** PROYECTOS LABS *******************/

	public void cambiarSedeLabs() {
		List<Dependencia> facultadesUN = servicioDependencia.obtenerFacultadesXSede(getSedeFiltroLabs());
		facultadItemLabs = servicioDependencia.crearSelectItem(facultadesUN);
		facultadSelLabs = ((Dependencia) facultadesUN.get(0)).getId().toString();
		actualizarLabs();
	}

	public void actualizarLabs() {
		List<Laboratorio> listaLabs = servicioGeneral.obtenerObjetos(Laboratorio.class,
				"from Laboratorio s where s.sede.id='" + getSedeFiltroLabs() + "' and s.facultad.id='" + facultadSelLabs
						+ "' and s.activo = '1' order by s.id");
		labsItem = new SelectItem[listaLabs.size()];
		for (int i = 0; i < listaLabs.size(); i++) {
			Laboratorio lab = listaLabs.get(i);
			labsItem[i] = new SelectItem(lab.getId(), lab.getId() + " - " + lab.getNombre());
		}
	}

	public void cambiarTieneLaboratorios() {
		if (proyectoActual.getTieneLaboratorios().equals("No"))
			proyectoActual.setLaboratorios(new HashSet());
	}

	/******************** PROYECTOS LABS *******************/

	/**
	 * Se cargan los tipos de vinculación para cuando se conocen los datos y cuando
	 * no.
	 */
	private void cargarTipoVinculacionInvestigador() {

		// Tipo vinculacion
		listaTipoVinculacion = obtenerTipoVinculacionConvocatoria(convocatoriaActual, mostrarMenuFormulario);

		tipoVinculacionItems = crearListaTipoInvestigador(listaTipoVinculacion);

		listaTipoVinculacionNumero = new ArrayList<TipoInvestigador>();
		listaTipoVinculacionNumero.addAll(listaTipoVinculacion);
		tipoVinculacionItemsNumero = tipoVinculacionItems;

		// Se cargan los tipos de vinculación para cuando NO se conocen.
		if (esConvProyectos2016_2018) {
			// Si es de la convocatoria nacional de proyectos.
			String consultaVinNoDato = "select ti from TipoInvestigador ti where ti.tipoModalidad like '%CP2016_2018"
					+ "%' order by ti.nombre desc";
			listaTipoVinculacionNumero = servicioGeneral.obtenerObjetos(TipoInvestigador.class, consultaVinNoDato);
			tipoVinculacionItemsNumero = crearListaTipoInvestigador(listaTipoVinculacionNumero);
		}

		if (esConvCundinamarca) {
			// Si es de la convocatoria nacional de proyectos.
			String consultaVinNoDato = "select ti from TipoInvestigador ti where ti.id in ('ESPR','EUDC','EPDC') order by ti.nombre desc";
			listaTipoVinculacionNumero = servicioGeneral.obtenerObjetos(TipoInvestigador.class, consultaVinNoDato);
			tipoVinculacionItemsNumero = crearListaTipoInvestigador(listaTipoVinculacionNumero);
		}

		if (esConvSesquicente) {
			String consultaVinNoDato = "select ti from TipoInvestigador ti where ti.tipoModalidad like '%CSESQUI"
					+ "%' order by ti.nombre desc";
			listaTipoVinculacionNumero = servicioGeneral.obtenerObjetos(TipoInvestigador.class, consultaVinNoDato);
			tipoVinculacionItemsNumero = crearListaTipoInvestigador(listaTipoVinculacionNumero);

		}

		if (esConvArtesModA2017 || esConvArtesModB2017) {
			String consultaVinNoDato = "select ti from TipoInvestigador ti where ti.tipoModalidad like '%CFA17"
					+ "%' order by ti.nombre desc";
			listaTipoVinculacionNumero = servicioGeneral.obtenerObjetos(TipoInvestigador.class, consultaVinNoDato);
			tipoVinculacionItemsNumero = crearListaTipoInvestigador(listaTipoVinculacionNumero);

		}

		if (esConvExtSol2017) {
			String consultaVinNoDato = "select ti from TipoInvestigador ti where ti.tipoModalidad like '%ES7"
					+ "%' order by ti.nombre desc";
			listaTipoVinculacionNumero = servicioGeneral.obtenerObjetos(TipoInvestigador.class, consultaVinNoDato);
			tipoVinculacionItemsNumero = crearListaTipoInvestigador(listaTipoVinculacionNumero);

		}

		if (esConvMedTraslacional2017) {
			String consultaVinNoDato = "select ti from TipoInvestigador ti where ti.tipoModalidad like '%CFMT17"
					+ "%' order by ti.nombre desc";
			listaTipoVinculacionNumero = servicioGeneral.obtenerObjetos(TipoInvestigador.class, consultaVinNoDato);
			tipoVinculacionItemsNumero = crearListaTipoInvestigador(listaTipoVinculacionNumero);

		}

		if (esConvAlianzasBog2017) {
			String consultaVinNoDato = "select ti from TipoInvestigador ti where ti.id = 'ESPR' order by ti.nombre desc";
			listaTipoVinculacionNumero = servicioGeneral.obtenerObjetos(TipoInvestigador.class, consultaVinNoDato);
			tipoVinculacionItemsNumero = crearListaTipoInvestigador(listaTipoVinculacionNumero);
		}

		if (esConvSUE2017) {
			String consultaVinNoDato = "select ti from TipoInvestigador ti where ti.tipoModalidad like 'SUE17"
					+ "%' order by ti.nombre desc";
			listaTipoVinculacionNumero = servicioGeneral.obtenerObjetos(TipoInvestigador.class, consultaVinNoDato);
			tipoVinculacionItemsNumero = crearListaTipoInvestigador(listaTipoVinculacionNumero);
		}

		if (convocatoriaActual.getTipoInvestigadoresConvocatoria() != null) {
			String consultaVinNoDato = "select ti from TipoInvestigador ti where ti.id in ("
					+ convocatoriaActual.getTipoInvestigadoresConvocatoria() + ") order by ti.nombre desc";
			listaTipoVinculacionNumero = servicioGeneral.obtenerObjetos(TipoInvestigador.class, consultaVinNoDato);
			tipoVinculacionItemsNumero = crearListaTipoInvestigador(listaTipoVinculacionNumero);
		}
	}

	/**
	 * Metodo para crear lista de select de tipos de investigador con listado de
	 * tipos.
	 * 
	 * @param listaVinculacion the lista vinculacion
	 * @return the select item[]
	 */
	private SelectItem[] crearListaTipoInvestigador(List<TipoInvestigador> listaVinculacion) {
		SelectItem[] tipoVinculacionItemsTemporal = null;
		if (!esListaVacia(listaVinculacion)) {
			tipoVinculacionItemsTemporal = new SelectItem[listaVinculacion.size()];
			for (int i = 0; i < listaVinculacion.size(); i++) {
				TipoInvestigador dominio = (TipoInvestigador) listaVinculacion.get(i);
				tipoVinculacionItemsTemporal[i] = new SelectItem(dominio.getId(), dominio.getNombre());
			}
			return tipoVinculacionItemsTemporal;
		}
		return tipoVinculacionItemsTemporal;
	}

	/**
	 * Cargar lista productos.
	 */
	
	public void cargarListaTipoProductos() {

		listaProductosNivel1 = new ArrayList<ProductoTipo>();
		List<ProductoTipo> productosConvocatoria = null;

			String hql = "select p from ProductoTipo p where p.estado = 'A' and p.descripcion like 'LISTA_PRODUCTO_UNIFICADA_HERMES' and p.nivel = '0' order by p.nombre";
			
			if (mostrarMenuFormulario && !convocatoriaActual.getId().equals(Convocatoria.MODALIDAD_CONVOCATORIA_EXTERNA)) {
				// Cuando la modalidad del proyecto es una convocatoria, se
				// buscan los productos asociados a ella
				String listaIdProducto = "('";
				if(productosConfiguradosConvocatoria!=null && productosConfiguradosConvocatoria.size()>0) {
					listaIdProducto = listaIdProducto + productosConfiguradosConvocatoria.get(0).getId();
					for(int i=1; i<productosConfiguradosConvocatoria.size();i++) {
						if("0".equals(productosConfiguradosConvocatoria.get(i).getNivel())) {
							listaIdProducto = listaIdProducto+"', '"+ productosConfiguradosConvocatoria.get(i).getId();
						}
					}
					listaIdProducto = listaIdProducto + "')";
				}
				
				hql = "select p from ProductoTipo p where p.id in "+listaIdProducto+" and p.nivel = '0' order by p.nombre";
			}
			productosConvocatoria = servicioGeneral.obtenerObjetos(ProductoTipo.class, hql);

		if (!esListaVacia(productosConvocatoria)) {
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

		productoNivel1Item = crearSelectItem(listaProductosNivel1);
	}
	
	public void cambiarProductoNivel1() {

		// valores para el segundo nivel
		List<ProductoTipo> productosConvocatoria = null;
		listaProductosNivel2 = new ArrayList();
		String hql = "select p from ProductoTipo p where p.estado = 'A' and p.descripcion like 'LISTA_PRODUCTO_UNIFICADA_HERMES' and p.nivel = '1' and p.padre.id = '"+productoNivel1+"' order by p.nombre";
		
		String listaIdProducto2 = "('";
		if(productosConfiguradosConvocatoria!=null && productosConfiguradosConvocatoria.size()>0 && !convocatoriaActual.getId().equals(Convocatoria.MODALIDAD_CONVOCATORIA_EXTERNA)) {
			listaIdProducto2 = listaIdProducto2 + productosConfiguradosConvocatoria.get(0).getId();
			for(int i=1; i<productosConfiguradosConvocatoria.size();i++) {
				if("1".equals(productosConfiguradosConvocatoria.get(i).getNivel())) {
					listaIdProducto2 = listaIdProducto2+"', '"+ productosConfiguradosConvocatoria.get(i).getId();
				}
			}
			listaIdProducto2 = listaIdProducto2+"')";
			hql = "select p from ProductoTipo p where p.id in "+listaIdProducto2+" and p.nivel = '1' and p.padre.id = '"+productoNivel1+"' order by p.nombre";
		}
		
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
		productoNivel2="";
		cargarListaProductos();
	}
	
	public void cargarListaProductos() {

		// valores para el segundo nivel
				List<ProductoTipo> productosConvocatoria = null;
				listaProductosNivel3 = new ArrayList();
				String hql = "select p from ProductoTipo p where p.estado = 'A' and p.descripcion like 'LISTA_PRODUCTO_UNIFICADA_HERMES' and p.nivel = '2' and p.padre.id = '"+productoNivel2+"' order by p.nombre";
				
				String listaIdProducto3 = "('";
				if(productosConfiguradosConvocatoria!=null && productosConfiguradosConvocatoria.size()>0  && !convocatoriaActual.getId().equals(Convocatoria.MODALIDAD_CONVOCATORIA_EXTERNA)) {
					listaIdProducto3 = listaIdProducto3 + productosConfiguradosConvocatoria.get(0).getId();
					for(int i=1; i<productosConfiguradosConvocatoria.size();i++) {
						if("2".equals(productosConfiguradosConvocatoria.get(i).getNivel())) {
							listaIdProducto3 = listaIdProducto3+"', '"+ productosConfiguradosConvocatoria.get(i).getId();
						}
					}
					listaIdProducto3 = listaIdProducto3+"')";
					hql = "select p from ProductoTipo p where p.id in "+listaIdProducto3+" and p.nivel = '2' and p.padre.id = '"+productoNivel2+"' order by p.nombre";
				}
				
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
	}

	/**
	 * Obtener lista instituciones.
	 * 
	 * @param nombre the nombre
	 * @return the list
	 */
	public List<String> obtenerListaInstituciones(String nombre) {
		List<Institucion> listaInstituciones = servicioGeneral.buscarListaDeInstitucionesPorNombre(nombre);
		List<String> listaInstitucionString = new ArrayList<String>();
		for (Iterator<Institucion> i = listaInstituciones.iterator(); i.hasNext();) {
			Institucion ins = (Institucion) i.next();
			listaInstitucionString.add(ins.getNombre());
		}
		return listaInstitucionString;
	}

	/**
	 * Metodo que valida si el valor ingresado ya esta en la base de datos o si es
	 * un valor nuevo.
	 */
	public void insertarPalabraClave() {
		if (!esCadenaVacia(palabraClave)) {
			PalabraClave palabraClaveNueva = new PalabraClave();
			palabraClaveNueva.setPalabra(palabraClave.trim());
			boolean existePalabra = palabraClaveNueva.existePalabraEnSet(proyectoActual.getPalabrasClaves());
			if ((palabraClave.trim().length() > 2 && palabraClave.trim().length() < 101) && (!existePalabra)) {

				palabraClaveNueva.setPalabraOriginal(palabraClave);
				try {
					PalabraClave palabraClaveExistente = servicioGeneral.obtenerPalabraClave(palabraClave);
					if (palabraClaveExistente == null) {
						palabraClaveNueva.setIdioma("ES");
						servicioGeneral.guardarObjeto(palabraClaveNueva);
					} else {
						palabraClaveNueva = (PalabraClave) palabraClaveExistente.clone();
					}
					proyectoActual.adicionarPalabraClave(palabraClaveNueva);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (existePalabra) {
				mensajeError(palabraClaveS, "La palabra ingresada ya se encuentra registrada.");
			} else {
				mensajeError(palabraClaveS, "La palabra ingresada debe ser mínimo de dos caracteres y máximo de 100");
			}
			palabraClave = "";
		}
	}

	/**
	 * Obtener palabra claves sugeridas.
	 * 
	 * @param nombre the nombre
	 * @return the list
	 */
	public List<String> obtenerPalabraClavesSugeridas(String nombre) {
		return servicioGeneral.obtenerPalabrasClaveEmpezandoCon(nombre);
	}

	/**
	 * Metodo para la inserción de los nuevos resultados.
	 */
	public void insertarResultado() {

		if (!esCadenaVacia(resultado) && !esCadenaVacia(entregable) && fechaEntregable!=null) {
			resultado = controlTamanoCadena(resultado, 3000);
			entregable = controlTamanoCadena(entregable, 3000);
			ResultadoProyecto resultadoNuevo = new ResultadoProyecto();
			resultadoNuevo.setDescripcion(resultado);
			resultadoNuevo.setEntregable(entregable);
			resultadoNuevo.setFechaEntregable(fechaEntregable);
			resultadoNuevo.setNumeroOrden(obtenerMaxOrderRes(proyectoActual.getListaResultados()));
			proyectoActual.adicionarResultado(resultadoNuevo);
			this.resultado = "";
			this.entregable="";
			this.fechaEntregable=null;
		} else {
			mensajeError(uiResultado, "Por favor escriba el resultado, el entregable y la fecha.");
		}

	}

	/**
	 * Buscar producto tipo nivel 3.
	 * 
	 * @param id the id
	 * @return the producto tipo
	 */
	protected ProductoTipo buscarProductoTipoNivel3(String id) {
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

	/**
	 * Crear select item.
	 * 
	 * @param lista the lista
	 * @return the select item[]
	 */
	private SelectItem[] crearSelectItem(List<ProductoTipo> lista) {
		SelectItem[] elementos;
		elementos = new SelectItem[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			ProductoTipo pro = lista.get(i);
			String nombre = pro.getNombre();

			if (pro.getNombre().length() > 100) {
				nombre = pro.getNombre().substring(0, 100) + "...";
				if (pro.getNombre().length() < 115 && pro.getNombre().length() > 75)
					pro.setNombrePop(pro.getNombre() + "_____________________________________________________________");
				else
					pro.setNombrePop(pro.getNombre() + " ");
			}

			elementos[i] = new SelectItem(pro.getId(), nombre);
		}
		return elementos;
	}

	/**
	 * Siguiente aval.
	 * 
	 * @return the string
	 */
	public String siguienteAval() {
		sesion.removeAttribute("manejadorSolicitudAvalHome");
		sesion.removeAttribute("idAval");

		boolean esCentroExtension = (Boolean) sesion.getAttribute("esCentroExtension");
		if (validarVinculacionPersona()) {
			sesion.setAttribute("idProyecto", proyectoActual.getId());
			if (esCentroExtension) {
				sesion.setAttribute("centroExtensionAval", true);
				sesion.setAttribute("esConsulta", false);
				sesion.setAttribute("esEdicion", false);

				return "solicitudAvalHome";
			} else {
				sesion.setAttribute("centroExtensionAval", false);
				sesion.setAttribute("esConsulta", false);
				sesion.setAttribute("esEdicion", false);
				return "solicitudAval";
			}
		}
		return "";
	}

	/**
	 * Cargar rubros modalidad.
	 */
	public void cargarRubrosModalidad() {
		try {
			List<RubroFinanciable> listaRubrosFinanciables = servicioModalidad
					.obtenerRubrosFinanciablesModalidad(convocatoriaActual.getId());

			if (!esListaVacia(listaRubrosFinanciables)) {
				tiposRubroItem = new SelectItem[listaRubrosFinanciables.size()];
			} else {
				tiposRubroItem = new SelectItem[0];
			}

			for (int i = 0; listaRubrosFinanciables != null && i < listaRubrosFinanciables.size(); i++) {
				RubroFinanciable rf = (RubroFinanciable) listaRubrosFinanciables.get(i);
				TipoRubro tr = rf.getTipoRubro();
				listaTiposRubros.add(tr);
				String nombre = tr.getNombre();
				if (nombre != null && nombre.length() > 120) {
					nombre = nombre.substring(0, 120) + "...";
				}
				tiposRubroItem[i] = new SelectItem(tr.getId(), nombre);
				idTipoRubro = 0L;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Validar vinculacion persona.
	 * 
	 * @return true, if successful
	 */
	protected boolean validarVinculacionPersona() {
		personaActual = (Persona) sesion.getAttribute("persona");
		InvestigadorInterno invI = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
		if (invI != null && invI.getTipoVinculacion() != null && (invI.getTipoVinculacion().getId().equals("30")
				|| invI.getTipoVinculacion().getId().equals("29") || invI.getTipoVinculacion().getId().equals("16"))) {
			return true;
		}
		return false;
	}

	/**
	 * Metodo para agregar un gasto en formulario sencillo.
	 */
	public void agregarGasto() {

		boolean error = false;

		if (idTipoRubro <= 0) {
			error = true;
			mensajeError("Debe seleccionar un tipo de rubro.");
		}

		if (valorGasto <= 0) {
			error = true;
			mensajeError("El valor del rubro debe ser mayor a cero.");
		}

		if (esCadenaVacia(descripcionGasto)) {
			error = true;
			mensajeError("La descripción se encuentra vacio.");
		}

		if (validarMontoFuente() && validarRubro() && !error) {
			try {
				Gasto gastoActual = new Gasto();
				gastoActual.setValor(valorGasto);

				// Se asigna por defecto 1 a la cantidad y la vigencia
				gastoActual.setCantidad(1);
				gastoActual.setVigencia(1);
				gastoActual.setDescripcion(descripcionGasto);
				Financiacion fg = (Financiacion) proyectoActual.getListaFinanciones().get(0);
				gastoActual.setFinanciacion(fg);
				TipoRubro tipoRubro = buscarTipoRubro(idTipoRubro);
				if (tipoRubro != null) {
					gastoActual.setTipoRubro(buscarTipoRubro(idTipoRubro));
					fg.adicionarGasto(gastoActual);
					descripcionGasto = "";
					valorGasto = 0L;
					idTipoRubro = 0L;
					//fg.calcularValorEfectivoFinanciacion();
				} else {
					mensajeError("No se ha podido agregar el rubro seleccionado.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Validar rubro.
	 * 
	 * @return true, if successful
	 */
	private boolean validarRubro() {
		boolean val = true;
		int numeroRubros = getListaGastos().size();
		for (int i = 0; i < numeroRubros; i++) {
			Gasto g = (Gasto) getListaGastos().get(i);
			if (g.getTipoRubro().getId().equals(this.idTipoRubro)) {
				val = false;
				mensajeError("El rubro ya se encuentra registrado");
				break;
			}
		}
		return val;
	}

	/**
	 * Gets the lista gastos.
	 * 
	 * @return the lista gastos
	 */
	public List<Gasto> getListaGastos() {
		if (!proyectoActual.getListaFinanciones().isEmpty()) {
			Financiacion f = (Financiacion) proyectoActual.getListaFinanciones().get(0);
			List<Gasto> lista = new ArrayList<Gasto>();
			if (f.getGastos() != null) {
				Iterator<Gasto> i = f.getGastos().iterator();
				while (i.hasNext()) {
					Gasto gasto = i.next();
					if (gasto.getSumaCampos() > 0) {
						lista.add(gasto);
					}
				}
			}
			return lista;
		} else {
			return null;
		}
	}

	/**
	 * Validar monto fuente.
	 * 
	 * @return true, if successful
	 */
	public boolean validarMontoFuente() {
		boolean val = true;

		if (convocatoriaActual.getRestriccion() != null && convocatoriaActual.getRestriccion().getId()
				.equals(RestriccionConvocatoria.FICHA_MINIMA_PROYECTOS_2013_2015)) {
			mensajeInfo(
					"Por favor recordar que el monto máximo para estudiantes de doctorado es de $19.330.500, y para estudiantes de maestría o especialidades en el área de la salud es de $12.887.000.");
		}

		if (esConvProyectos2016_2018) {
			mensajeInfo("Sedes Andinas:Por proyecto se financiará máximo hasta $40.000.000 y mínimo $8.000.000.");
			mensajeInfo(
					"Sede Caribe y Orinoquía: Por proyecto se financiará máximo hasta $40.000.000 y mínimo $3.000.000. Sede Amazonía: Por proyecto se financiará máximo hasta $25.000.000 y mínimo $3.000.000.");
		}

		if (esConvProyectos2017_2018) {
			mensajeInfo(
					"Recuerde que el monto solicitado para el proyecto, debe valorarse con base en lo establecido enla vinculación de los estudiantes ya sea de pregrado o posgrado del punto 3 de los términos de referencia.");
		}

		if (proyectoActual.getListaFinanciones().size() > 0) {
			Financiacion fin = proyectoActual.getListaFinanciones().get(0);
			if (Long
					.parseLong(convocatoriaActual.getMontoApoyoGanadores())>0 && fin.getValor() != null
					&& fin.getValor() + valorGasto > Long.parseLong(convocatoriaActual.getMontoApoyoGanadores())) {
				val = false;
				mensajeError("El monto solicitado supera el máximo de la convocatoria");
			}
		}

		return val;
	}

	/**
	 * Eliminar gasto conv.
	 */
	public void eliminarGastoConv() {
		Financiacion financiacion = gastoSeleccionado.getFinanciacion();
		financiacion.borrarGasto(gastoSeleccionado);
	}

	private void calcularTotales(TreeNode nodo, Financiacion financ) {
		if (nodo.getChildren() == null || (nodo.getChildren() != null && nodo.getChildren().isEmpty())) {
			GastoFM gastofm = (GastoFM) nodo.getData();
			gastofm.setTotal(gastofm.getSumaAnios());
			Gasto gastoNuevo = gastofm.getGasto();	
			if (gastofm.getSumaAnios() > 0L || gastoNuevo != null) {
				if (gastoNuevo == null || (gastofm.getSumaAnios() > 0L && gastoNuevo.getFinanciacion()==null)) {
					gastoNuevo = new Gasto();
					gastoNuevo.setCantidad(1);
					gastoNuevo.setVigencia(1);
					gastoNuevo.setFinanciacion(financ);

					// Se asigna el tipo rubro.
					List<TipoRubro> listRubros = servicioGeneral.obtenerObjetos(TipoRubro.class,
							"select r from TipoRubro r where r.id =" + gastofm.getIdRubro());
					if (!esListaVacia(listRubros)) {
						TipoRubro tipoRbr = (TipoRubro) listRubros.get(0);
						gastoNuevo.setTipoRubro(tipoRbr);
						financ.adicionarGasto(gastoNuevo);
						gastofm.setGasto(gastoNuevo);
					}
				}
				if(esCadenaVacia(gastoNuevo.getDescripcion()) && !convocatoriaActual.validarDetalleGasto()) {
					if (gastofm.isEsContrapartida()) {
						gastoNuevo.setDescripcion("Contrapartida");
					} else {
						gastoNuevo.setDescripcion("Gasto");
					}
				}
				gastoNuevo.setValor(gastofm.getAnio1());
				gastoNuevo.setValor2(gastofm.getAnio2());
				gastoNuevo.setValor3(gastofm.getAnio3());
				gastoNuevo.setValor4(gastofm.getAnio4());
				gastoNuevo.setValor5(gastofm.getAnio5());
				gastoNuevo.setValor6(gastofm.getAnio6());
			}
		} else {
			GastoFM rubroPadre = (GastoFM) nodo.getData();
			Long totalxAnio1C = 0L;
			Long totalxAnio2 = 0L;
			Long totalxAnio3 = 0L;
			Long totalxAnio4 = 0L;
			Long totalxAnio5 = 0L;
			Long totalxAnio6 = 0L;
			for (TreeNode child : nodo.getChildren()) {
				calcularTotales(child, financ);
				GastoFM registro = (GastoFM) child.getData();
				totalxAnio1C += registro.getAnio1();
				totalxAnio2 += registro.getAnio2();
				totalxAnio3 += registro.getAnio3();
				totalxAnio4 += registro.getAnio4();
				totalxAnio5 += registro.getAnio5();
				totalxAnio6 += registro.getAnio6();
			}
			rubroPadre.setAnio1(totalxAnio1C);
			rubroPadre.setAnio2(totalxAnio2);
			rubroPadre.setAnio3(totalxAnio3);
			rubroPadre.setAnio4(totalxAnio4);
			rubroPadre.setAnio5(totalxAnio5);
			rubroPadre.setAnio6(totalxAnio6);
			rubroPadre.setTotal(rubroPadre.getSumaAnios());
		}
	}

	/**
	 * Calcular totales ficha.
	 */
	// Ficha externa
	public void calcularTotalesFicha() {
		Long contrapartidaEfectivo = 0L;
		Long contrapartidaEspecieTotal = 0L;
		Long contrapartidaExternaEspecieTotal = 0L;
		valorFinanciadoInvestig = 0L;
		contrapEfectUNAL_ConvURosario = 0L;
		Long valorTotalFuentesFinanciacion = 0L;
		if (!esListaVacia(proyectoActual.getListaFuentesFinancacionFicha())) {
			Iterator<Financiacion> i = proyectoActual.getListaFuentesFinancacionFicha().iterator();
			while (i.hasNext()) {
				Financiacion financ = i.next();
				Long contrapartidaEspecieFuente = 0L;
				TreeNode tree = financ.getTreeFinanciacion();
				financ.getTreeFinanciacion().setExpanded(true);
				Long[] totales = new Long[] { null, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L };
				if (tree != null) {
					for (TreeNode treeChildPpal : tree.getChildren()) {
						if (((GastoFM) treeChildPpal.getData()).getIdRubro() != 146) {
							calcularTotales(treeChildPpal, financ);
							GastoFM registro = (GastoFM) treeChildPpal.getData();
							totales[1] += registro.getAnio1();
							totales[2] += registro.getAnio2();
							totales[3] += registro.getAnio3();
							totales[4] += registro.getAnio4();
							totales[5] += registro.getAnio5();
							totales[6] += registro.getAnio6();
						}
					}
					if (financ != null && financ.getNodeTotal() != null) {
						GastoFM gastofmTotal = (GastoFM) financ.getNodeTotal().getData();
						gastofmTotal.setAnio1(getRedondeadoToLong(totales[1]));
						gastofmTotal.setAnio2(getRedondeadoToLong(totales[2]));
						gastofmTotal.setAnio3(getRedondeadoToLong(totales[3]));
						gastofmTotal.setAnio4(getRedondeadoToLong(totales[4]));
						gastofmTotal.setAnio5(getRedondeadoToLong(totales[5]));
						gastofmTotal.setAnio6(getRedondeadoToLong(totales[6]));
						gastofmTotal.setTotal(gastofmTotal.getSumaAnios());
						contrapartidaEspecieFuente += gastofmTotal.getAnio1();
						financ.setValor(gastofmTotal.getSumaAnios());
					}
				}
				if ((financ.getFuente().getInternaExterna().equals("E")
						|| financ.getFuente().getInternaExterna().equals("OE")
						|| financ.getFuente().getInternaExterna().equals("A") )
						&& !financ.getFuente().getId().equals("3273")
						&& ((((Convocatoria) proyectoActual.getModalidad()).getRestriccion() != null
								&& !((Convocatoria) proyectoActual.getModalidad()).getRestriccion().getId()
										.equals("CONV_SUE_2017"))) 
						|| ( (financ.getFuente().getInternaExterna().equals("E")
								|| financ.getFuente().getInternaExterna().equals("OE")
								|| financ.getFuente().getInternaExterna().equals("A") ) && ((Convocatoria) proyectoActual.getModalidad()).getRestriccion() == null)) {

					contrapartidaExternaEspecieTotal += contrapartidaEspecieFuente;
					valorFinanciadoInvestig += financ.getValor() - contrapartidaEspecieFuente;
					if (proyectoActual.getModalidad().getId().equals(971L)) {
						proyectoActual.setCostoFacultad(contrapartidaExternaEspecieTotal);
					}
					proyectoActual.setContrapartidaExternaEspecieTotal(contrapartidaExternaEspecieTotal);

				} else {

					// Contrapartida especie en proyecto.
					contrapartidaEspecieTotal += contrapartidaEspecieFuente;
					proyectoActual.setContrapartidaEspecieTotal(contrapartidaEspecieTotal);

					// Contrapartida efectivo en proyecto.
					if (((Convocatoria) proyectoActual.getModalidad()).getRestriccion() != null) {
						if (((Convocatoria) proyectoActual.getModalidad()).getRestriccion().getId()
								.equals(RestriccionConvocatoria.CONV_UROSARIO)) {
							if (!financ.getFuente().getId().equals("1")) {
								contrapartidaEfectivo += (financ.getValor() - contrapartidaEspecieFuente);
							} else {
								contrapEfectUNAL_ConvURosario += (financ.getValor() - contrapartidaEspecieFuente);
							}
						}else {
							contrapartidaEfectivo += (financ.getValor() - contrapartidaEspecieFuente);
						}
					}else if(((Convocatoria) proyectoActual.getModalidad()).getId().equals(Convocatoria.ID_CONVOCATORIA_AGROSAVIA_2020)) {
						if (!financ.getFuente().getId().equals("1")) {
							valorFinanciadoInvestig += (financ.getValor() - contrapartidaEspecieFuente);	
						} else {
							contrapartidaEfectivo += (financ.getValor() - contrapartidaEspecieFuente);
						}
					} else {
						contrapartidaEfectivo += (financ.getValor() - contrapartidaEspecieFuente);
					}
				}

				// Valores totales proyecto
				valorTotalFuentesFinanciacion += financ.getValor();
			}
		}
		valorTotalTotalProyecto = valorTotalFuentesFinanciacion + proyectoActual.getValorAdministrativoTotal()
				+ proyectoActual.getValorPersonalTotal() + proyectoActual.getValorEntidadesParticipantesCalculado()
				+ proyectoActual.getValorEspecieCalculado();

// Valor especie entidades
		if (!proyectoActual.getModalidad().getId().equals(971L)) {
			proyectoActual.setCostoFacultad(proyectoActual.getValorEspecieCalculado());
		}
		proyectoActual.setContrapartidaEfectivo(contrapartidaEfectivo);
// valor costos indirectos
		if (esConvExtSol2018) {
			proyectoActual.setValorTotalCostosIndirectos((Math.round(proyectoActual.getContrapartidaEfectivo() * 1.05))
					- (proyectoActual.getContrapartidaEfectivo()));
		}
	}

	public void insertarProyectoPrograma() {
		if (!esCadenaVacia(nombreProyectoPrograma) && !idEntidadProyectoPrograma.equals("")) {

			ProyectoPrograma pp = new ProyectoPrograma();
			pp.setNombre(nombreProyectoPrograma);
			List<FuenteFinanciacion> listaFuentesSel = servicioGeneral.obtenerObjetosLimitado(FuenteFinanciacion.class,
					"select #id e.id, #descripcion e.descripcion from FuenteFinanciacion e where e.id = '"
							+ idEntidadProyectoPrograma + "'");
			FuenteFinanciacion ff = listaFuentesSel.get(0);
			pp.setFuente(ff);
			if (idDependenciaEntidadProyectoPrograma != null && !idDependenciaEntidadProyectoPrograma.equals("")) {
				List<Dependencia> listaDependenciaSel = servicioGeneral.obtenerObjetosLimitado(Dependencia.class,
						"select #id e.id, #nombre e.nombre from Dependencia e where e.id = '"
								+ idDependenciaEntidadProyectoPrograma + "'");
				Dependencia dep = listaDependenciaSel.get(0);
				pp.setDependencia(dep);
			}

			if (!proyectoActual.getListaProyectosPrograma().contains(pp)) {
				proyectoActual.adicionarProyectoPrograma(pp);
				this.nombreProyectoPrograma = "";
				this.idEntidadProyectoPrograma = "";
				this.idDependenciaEntidadProyectoPrograma = "";
			} else {
				mensajeError(uiDependenciaProyectoPrograma, "El proyecto ya se encuentra asociado al programa.");
			}

		} else {
			mensajeError(uiDependenciaProyectoPrograma,
					"Por favor ingrese el nombre del proyecto y la entidad que lo va a ejecutar.");
		}
	}

	/**
	 * Metodo para la inserción de los nuevos objetivos especificos.
	 */
	public void insertarObjetivo() {
		if (!esCadenaVacia(objetivoEspecifico) && !esCadenaVacia(medioVerificacion)) { //
			objetivoEspecifico = controlTamanoCadena(objetivoEspecifico, 3000);
			medioVerificacion = controlTamanoCadena(medioVerificacion, 3000);
			ObjetivoEspecifico objetivoEspecificoNuevo = new ObjetivoEspecifico();
			objetivoEspecificoNuevo.setNombre(objetivoEspecifico);
			objetivoEspecificoNuevo.setMedioVerificacion(medioVerificacion);
			objetivoEspecificoNuevo.setNumeroOrden(obtenerMaxOrderObjetivo(proyectoActual.getListaObjetivos()));
			proyectoActual.adicionarObjetivoEspecifico(objetivoEspecificoNuevo);
			actualizarListaObjetivosMetas();
			this.objetivoEspecifico = "";
			this.medioVerificacion = "";
		} else {
			mensajeError(objetoEspecifico, "Por favor ingrese el objetivo específico y su medio de verificación (Máx. 2000 caracteres).");
		}
	}

	public void actualizarListaObjetivosMetas() {
		objetivosItem = servicioProyecto.crearSelectItemObjetivos(proyectoActual.getListaObjetivos());
	}

	public void insertarMeta() {
		System.out.println("         -------------------------         insertarMeta() --------------------");
		if (!esCadenaVacia(meta) && !metaObjetivoSelectValue.equals("")) {
			meta = controlTamanoCadena(meta, 3000);
			ObjetivoEspecifico objetivoObjeto = buscarObjetivo(metaObjetivoSelectValue,
					proyectoActual.getListaObjetivos());
			MetaProyecto metaNueva = new MetaProyecto();
			metaNueva.setNombre(meta);
			metaNueva.setObjetivo(objetivoObjeto);
			metaNueva.setNumeroOrden(obtenerMaxOrderMeta(proyectoActual.getListaMetas()));
			objetivoObjeto.adicionarMeta(metaNueva);
			this.meta = "";
		} else {
			mensajeError(metaProyecto,
					"Por favor ingrese la meta (Máx. 2000 caracteres) y seleccione un objetivo de la lista.");
		}
	}

	public Long obtenerMaxOrderObjetivo(List<ObjetivoEspecifico> objetivoEspecificos) {
		Long max = 0L;
		if (objetivoEspecificos != null) {
			Iterator<ObjetivoEspecifico> i = objetivoEspecificos.iterator();
			while (i.hasNext()) {
				ObjetivoEspecifico objetivoEspecifico = i.next();
				if (objetivoEspecifico.getNumeroOrden() != null && objetivoEspecifico.getNumeroOrden() > max) {
					max = objetivoEspecifico.getNumeroOrden();
				}
			}
		}
		return max + 1;
	}

	public Long obtenerMaxOrderMeta(List<MetaProyecto> metasProyecto) {
		Long max = 0L;
		if (metasProyecto != null) {
			Iterator<MetaProyecto> i = metasProyecto.iterator();
			while (i.hasNext()) {
				MetaProyecto metaProyecto = i.next();
				if (metaProyecto.getNumeroOrden() != null && metaProyecto.getNumeroOrden() > max) {
					max = metaProyecto.getNumeroOrden();
				}
			}
		}
		return max + 1;
	}

	public Long obtenerMaxOrderRes(List<ResultadoProyecto> resultados) {
		Long max = 0L;
		if (resultados != null) {
			Iterator<ResultadoProyecto> i = resultados.iterator();
			while (i.hasNext()) {
				ResultadoProyecto resultadoProyecto = i.next();
				if (resultadoProyecto.getNumeroOrden() != null && resultadoProyecto.getNumeroOrden() > max) {
					max = resultadoProyecto.getNumeroOrden();
				}
			}
		}
		return max + 1;
	}

	/**
	 * Se adiciona un nuevo producto segun informacion ingresada.
	 */
	public void adicionarProducto() {
		int cantidadIngresada;
		boolean existeProducto = false;

		if ("".equals(productoNivel3)) {
			mensajeError(uiBtnProd, "Seleccione el tipo de producto.");
		} else {
			try {
				cantidadIngresada = Integer.parseInt(cantidad);
			} catch (NumberFormatException nfe) {
				mensajeError(uiCantidad, "La cantidad no es un número valido.");
				return;
			}

			ProyectoProducto productoProyecto = new ProyectoProducto();
			ProductoTipo p = buscarProductoTipoNivel3(productoNivel3);
			productoProyecto.setProducto(p);
			productoProyecto.setCantidad(cantidadIngresada);

			if (p.getNombre().contains("Otro")) {
				if (!esCadenaVacia(otroTipo)) {
					productoProyecto.setDescripcion(otroTipo);
					otroTipo = "";
				} else {
					existeProducto = true;
					mensajeError("Indique el tipo de producto.");
				}
			} else {
				existeProducto = existeProductoEnSet(proyectoActual.getProductosProyecto(), productoProyecto);
			}

			if (!existeProducto) {
				proyectoActual.adicionarProductoProyecto(productoProyecto);
				cantidad = "1";
			}
		}
	}

	/**
	 * Se valida si un producto ya esta incluida en la lista de producto.
	 * 
	 * @param productos        the productos
	 * @param productoProyecto the producto proyecto
	 * @return true, if successful
	 */
	public boolean existeProductoEnSet(Set<ProyectoProducto> productos, ProyectoProducto productoProyecto) {
		Iterator<ProyectoProducto> it = productos.iterator();
		while (it.hasNext()) {
			ProyectoProducto proyectoProducto = it.next();
			if (proyectoProducto.getProducto().getNombre()
					.equalsIgnoreCase(productoProyecto.getProducto().getNombre().toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adicionar dependencia.
	 */
	public void adicionarDependencia() {

		boolean existeDep = false;

		if (esConvocatoriaAlianzas2019 && !esEntidSedePresNal) {
			dependenciaProyecto = facultadSel;
		}

		if (!esCadenaVacia(dependenciaProyecto) && !esCadenaVacia(sedeSel)) {
			// Se verifica que no exista.
			if (proyectoActual.getListaDependenciasAreaResponsabilidad().size() > 0) {
				DependenciaAreaResponsabilidad dep = buscarDependenciaAreaResponsabilidad(dependenciaProyecto,
						proyectoActual.getListaDependenciasAreaResponsabilidad());
				if (dep != null) {
					existeDep = true;
				}
			}
			if (!existeDep) {
				Dependencia dep = null;
				// Se busca dependencia en listado de dependencias.
				if (esConvocatoriaAlianzas2019 && !esEntidSedePresNal) {
					dep = buscarDependencia(dependenciaProyecto, servicioDependencia.obtenerFacultadesXSede(sedeSel));
				} else {
					dep = buscarDependencia(dependenciaProyecto, dependenciasUN);
				}
				if (dep != null) {
					DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad = new DependenciaAreaResponsabilidad();
					dependenciaAreaResponsabilidad.setDependencia(dep);
					if (esConvocatoriaAlianzas2018) {
						dependenciaAreaResponsabilidad.setValorAporte(valorAporteAlianza);
						if (sedeSel.equals("1")) {
							if (valorAporteAlianza > 35000000L) {
								mensajeError(botonAgregarDependenciaAlianzas,
										"Monto de aporte de Nivel Nacional de máximo $35.000.000.");
							} else {
								proyectoActual.adicionarDependencia(dependenciaAreaResponsabilidad);
							}
						} else {
							proyectoActual.adicionarDependencia(dependenciaAreaResponsabilidad);
						}
					} else {
						if (esConvocatoriaAlianzas2019) {
							dependenciaAreaResponsabilidad.setValorAporte(valorAporteAlianza);
							if (sedeSel.equals("1")) {
								if (valorAporteAlianza > 80000000L) {
									mensajeError(botonAgregarDependenciaAlianzas,
											"Monto de aporte de Nivel Nacional de máximo $80.000.000.");
								} else {
									proyectoActual.adicionarDependencia(dependenciaAreaResponsabilidad);
								}
							} else {
								proyectoActual.adicionarDependencia(dependenciaAreaResponsabilidad);
							}
						} else {
							proyectoActual.adicionarDependencia(dependenciaAreaResponsabilidad);
						}
					}
				} else {
					if (esConvocatoriaAlianzas2018 || esConvocatoriaAlianzas2019) {
						mensajeError(botonAgregarDependenciaAlianzas,
								"La dependencia seleccionada no se encuentra disponible.");
					} else {
						mensajeError(botonAgregarDependencia,
								"La dependencia seleccionada no se encuentra disponible.");
					}
				}
			} else {
				if (esConvocatoriaAlianzas2018 || esConvocatoriaAlianzas2019) {
					mensajeError(botonAgregarDependenciaAlianzas,
							"La dependencia seleccionada ya se encuentra registrada.");
				} else {
					mensajeError(botonAgregarDependencia, "La dependencia seleccionada ya se encuentra registrada.");
				}
			}
		} else {
			if (esConvocatoriaAlianzas2018 || esConvocatoriaAlianzas2019) {
				mensajeError(botonAgregarDependenciaAlianzas, "Por favor seleccione la dependencia a registrar.");
			} else {
				mensajeError(botonAgregarDependencia, "Por favor seleccione la dependencia a registrar.");
			}
		}
	}

	public void adicionarDependenciaAportante() {

		boolean existeDep = false;

		if (esCadenaVacia(dependenciaAportanteProyecto)) {
			dependenciaAportanteProyecto = facultadSelAportante;
		}

		DependenciaAportante dependencia = new DependenciaAportante();

		if (!esCadenaVacia(dependenciaAportanteProyecto) && !esCadenaVacia(sedeSelAportante)) {
			// Se verifica que no exista.
			if (proyectoActual.getListaDependenciasAportantes().size() > 0) {

				int i = 0;
				while (i < proyectoActual.getListaDependenciasAportantes().size()) {
					dependencia = (DependenciaAportante) proyectoActual.getListaDependenciasAportantes().get(i);
					if (dependenciaAportanteProyecto.equals(dependencia.getDependencia().getId())) {
						existeDep = true;
					}
					i = i + 1;
				}

			}
			if (!existeDep) {
				Dependencia dep = servicioDependencia.obtenerDependencia(dependenciaAportanteProyecto);

				if (valorAporteAlianza < 0) {
					mensajeError(botonAgregarDependenciaAlianzas,
							"El valor del aporte debe ser mayor a cero, si no hay aporte por favor diligencie cero");
				} else if (proyectoActual.getListaDependenciasAportantes().size() > 0) {
					long totalNivelNacional = 0;
					//Se está usando para la facultad de enfermeria
					long totalDependenciaEspecifica=0;
					for (int i = 0; i < proyectoActual.getListaDependenciasAportantes().size(); i++) {
						DependenciaAportante depe = proyectoActual.getListaDependenciasAportantes().get(i);
						if (depe.getDependencia().getSede().isEsNivelNacional()) {
							totalNivelNacional = totalNivelNacional + depe.getValorAporte();
						}
						if (depe.getDependencia().getId().equals("2310")) { //Facultad de Enfermeria
							totalDependenciaEspecifica = totalDependenciaEspecifica + depe.getValorAporte();
						}

					}
					if (convocatoriaActual.getPadre().getEsConvocatoriaLaboratorios2024() && (dep.getSede().isEsNivelNacional() && (totalNivelNacional + valorAporteAlianza > 270000000))) {
						mensajeError(botonAgregarDependenciaAlianzas,
								"El valor del aporte del nivel nacional debe ser máximo de  270.000.000 COP");

					} else if (convocatoriaActual.getPadre().getEsConvocatoriaEnfermeriaAlianzas2023() && (totalDependenciaEspecifica + valorAporteAlianza > 30000000) ){
						mensajeError(botonAgregarDependenciaAlianzas,
								"El valor del aporte de la facultad de Enfermería debe ser máximo de 30.000.000 COP");
					}else {
						DependenciaAportante dependenciaAportante = new DependenciaAportante();
						dependenciaAportante.setDependencia(dep);
						dependenciaAportante.setValorAporte(valorAporteAlianza);
						dependenciaAportante.setValorAporteEspecie(valorAporteDependenciaEspecie);
						proyectoActual.adicionarDependenciaAportante(dependenciaAportante);
					}

				} else {
					DependenciaAportante dependenciaAportante = new DependenciaAportante();
					dependenciaAportante.setDependencia(dep);
					dependenciaAportante.setValorAporte(valorAporteAlianza);
					dependenciaAportante.setValorAporteEspecie(valorAporteDependenciaEspecie);
					proyectoActual.adicionarDependenciaAportante(dependenciaAportante);

				}

			} else {
				mensajeError(botonAgregarDependencia, "La dependencia seleccionada ya se encuentra registrada.");
			}
		} else {

			mensajeError(botonAgregarDependencia, "Por favor seleccione la dependencia a registrar.");
		}
	}

	public Long totalDependenciasAportantesNivelNacional() {
		totalDependenciasAportantes = 0L;
		Long totalAportantesNivelNacional = 0L;
		if (proyectoActual.getListaDependenciasAportantes().size() > 0) {
			for (int i = 0; i < proyectoActual.getListaDependenciasAportantes().size(); i++) {
				DependenciaAportante depe = proyectoActual.getListaDependenciasAportantes().get(i);
				totalDependenciasAportantes = totalDependenciasAportantes + depe.getValorAporte();
				if (depe.getDependencia().getSede().isEsNivelNacional()) {
					totalAportantesNivelNacional = totalAportantesNivelNacional + depe.getValorAporte();
				}

			}
		}
		return totalAportantesNivelNacional;
	}
	

	public Long totalDependenciasAportantesSedeFacultad() {
		totalDependenciasAportantes = 0L;
		Long totalAportantesNivelSedeFacultad = 0L;
		if (proyectoActual.getListaDependenciasAportantes().size() > 0) {
			for (int i = 0; i < proyectoActual.getListaDependenciasAportantes().size(); i++) {
				DependenciaAportante depe = proyectoActual.getListaDependenciasAportantes().get(i);
				totalDependenciasAportantes = totalDependenciasAportantes + depe.getValorAporte();
				if (!depe.getDependencia().getSede().isEsNivelNacional()) {
					totalAportantesNivelSedeFacultad = totalAportantesNivelSedeFacultad + depe.getValorAporte();
				}

			}
		}
		return totalAportantesNivelSedeFacultad;
	}

	public Long totalDependenciasAportantes() {
		Long totalDependenciasAportantes = 0L;

		if (proyectoActual.getListaDependenciasAportantes().size() > 0) {
			for (int i = 0; i < proyectoActual.getListaDependenciasAportantes().size(); i++) {
				DependenciaAportante depe = proyectoActual.getListaDependenciasAportantes().get(i);
				totalDependenciasAportantes = totalDependenciasAportantes + depe.getValorAporte();

			}
		}
		return totalDependenciasAportantes;
	}
	
	public Long totalEspecieDependenciasAportantes() {
		Long totalEspecieDependenciasAportantes = 0L;

		if (proyectoActual.getListaDependenciasAportantes().size() > 0) {
			for (int i = 0; i < proyectoActual.getListaDependenciasAportantes().size(); i++) {
				DependenciaAportante depe = proyectoActual.getListaDependenciasAportantes().get(i);
				totalEspecieDependenciasAportantes = totalEspecieDependenciasAportantes + depe.getValorAporteEspecie();

			}
		}
		return totalEspecieDependenciasAportantes;
	}

	/**
	 * Se valida si los integrantes ya fueron agregados o son el director en el
	 * listado de equipo con datos.
	 * 
	 * @return true, if successful
	 */
	protected boolean esRepetidoEquipoConDatos() {
		// Validar que no se encuentre en participantes
		if (validarAgregadoEquipo(documentoCoinvEquipo, tipoDocumentoCoInvEquipo.getId(),
				proyectoActual.getListaInvestigadoresProyectoConDatos())) {
			mensajeError(buscarPer2, "La persona ingresada ya se encuentra vinculada al proyecto.");
			return true;
		}

		if (validarAgregadoEquipo(documentoCoinvEquipo, tipoDocumentoCoInvEquipo.getId(),
				proyectoActual.getListaInvestigadorPrincipal())) {
			mensajeError(buscarPer2, "El director del proyecto no se debe agregar como equipo de trabajo.");
			return true;
		}

		// Validar que no se encuentre como Director (persona en sesión)
		if ((tipoDocumentoCoInv.getId().equals(tipoDocumentoCoInvEquipo.getId()))
				&& documentoCoinv.equals(documentoCoinvEquipo)) {
			mensajeError(buscarPer2, "El director del proyecto no se debe agregar como equipo de trabajo.");
			return true;
		}
		return false;
	}

	/**
	 * Metodo para validar si el tipo de vinculación que ya se esta agregando esta
	 * repetido.
	 * 
	 * @param id                 the id
	 * @param tipo               the tipo
	 * @param listaParticipantes the lista participantes
	 * @return true, if successful
	 */
	protected boolean validarAgregadoEquipo(String id, String tipo, List<InvestigadorProyecto> listaParticipantes) {
		if (!esListaVacia(listaParticipantes)) {
			for (InvestigadorProyecto invPry : listaParticipantes) {
				if (invPry.getInvestigador().getId().getDocumento().equals(id)
						&& invPry.getInvestigador().getId().getTipoDocumento().equals(tipo)) {
					return true;
				}
			}
		}
		return false;
	}

	protected boolean validarAgregadoEquipoSUE(List<InvestigadorProyecto> listaParticipantes) {
		if (!esListaVacia(listaParticipantes)) {
			for (InvestigadorProyecto invPry : listaParticipantes) {
				if ("DUCMC".equals(invPry.getTipo().getId()) || "DUDFJC".equals(invPry.getTipo().getId())
						|| "DUMNG".equals(invPry.getTipo().getId()) || "DUPN".equals(invPry.getTipo().getId())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Se elimina el director del proyecto que se ha agregado.
	 */
	public void eliminarDirector() {
		if (!esListaVacia(proyectoActual.getListaInvestigadorPrincipal())) {
			InvestigadorProyecto lider = proyectoActual.getListaInvestigadorPrincipal().get(0);
			proyectoActual.getInvestigadoresProyecto().remove(lider);
			calcularValorDocente();
		}
	}

	/**
	 * Agregar director.
	 */
	public void agregarDirector() {

		boolean esValido = true;
		Long valorTotal = 0L;
		double duracionTotalMeses = 0.0;

		// Se valida que se haya ingresado el documento.
		if (!esCadenaVacia(documentoCoinv)) {

			// Se valida que no se haya agregado un investigador aún.
			if (proyectoActual.getResponsable() == null) {

				// Se obtiene investigador interno.
				InvestigadorInterno investigadorInterno = servicioPersona
						.obtenerInvestigadorInterno(new IdPersona(documentoCoinv, tipoDocumentoCoInv.getId()));

				if (investigadorInterno != null) {
					
					String inputValue = String.valueOf(horasDirector); // Convert double to string
				    if(!inputValue.matches("\\d{1,2}(\\.\\d{0,1})?")){
				    	mensajeError(horasCoinv,
				    			"La dedicación del director debe tener máximo de dos digitos para la parte entera y un dígito para la parte decimal y debe estar separado por punto (.). Ej. 99.5");
				    }

					if (horasDirector <= 0 || horasDirector > 100) {
						esValido = false;
						mensajeError(horasCoinv,
								"Por favor verifique la información de las Horas semanales de dedicación (mayor a 0).");
					}

					duracionTotalMeses = (proyectoActual.getDuracion() != null && proyectoActual.getDuracion() > 0)
							? proyectoActual.getDuracion()
							: 0.0;

					if (duracionTotalMeses <= 0) {
						esValido = false;
						mensajeError(buscarDirector, "Por favor ingrese primero la duración del proyecto.");
					}

					String sDura = String.valueOf(proyectoActual.getDuracion());
					if (sDura.length() > 2 || proyectoActual.getDuracion() <= 0) {
						esValido = false;
						mensajeError(buscarDirector,
								"La duración del proyecto debe ser de máximo 2 dígitos y mayor a 0.");
					}

					// tiempo máximo.
					if (mostrarMenuFormulario && !esFichaExterna) {
						if (proyectoActual.getDuracion() > convocatoriaActual.getTiempoEjecucionProyecto()) {
							esValido = false;
							mensajeError(buscarDirector, "La duración del proyecto supera lo permitido");
						}
					}

				} else {
					esValido = false;
					mensajeError(horasCoinv, "Por favor verifique la información del director.");
				}

				// Si es valido, se agrega.
				if (esValido) {
					long duracionAnnio = 0L;
					try {
						// Valor hora del docente por dedicación al proyecto
						Long valorHora = investigadorInterno.getValorHora();
						if (proyectoActual.getValorPersonalTotal() == null) {
							this.proyectoActual.setValorPersonalTotal(0L);
						}
						if (duracionTotalMeses <= 12) {
							valorTotal = (long) (this.horasDirector * valorHora * 4 * duracionTotalMeses);
						} else {
							duracionAnnio = (long) (duracionTotalMeses / 12);
							double res = duracionTotalMeses % 12;
							Long resid = (long) (res);
							for (int i = 0; i < duracionAnnio; i++) {
								valorTotal = valorTotal + (long) (this.horasDirector * valorHora * 4 * 12);
								valorHora = valorHora + (long) (valorHora * 0.05);
							}
							if (resid > 0) {
								valorTotal = valorTotal + (long) (this.horasDirector * valorHora * 4 * resid);
							}
						}
						// Agregar director a la listaDirector
						InvestigadorProyecto director = new InvestigadorProyecto();
						director.setInvestigador(investigadorInterno);
						director.setDedicacionHorasSemana(horasDirector);
						director.setDependencia(investigadorInterno.getDependencia());
						director.setTotalHorasVinculacion(new Double(duracionTotalMeses));
						if (convocatoriaActual.isMostrarActividadesInvestigador()) {
							if (!StringUtils.isBlank(funcionDirector)) {
								director.setFuncion(funcionDirector);
							} else {
								mensajeError(buscarDirector,
										"Por favor, indicar las actividades del investigador principal.");
								return;
							}
						} else {
							director.setFuncion("Director");
						}
						director.setProyecto(proyectoActual);
						TipoInvestigador ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(),
								"P");
						director.setTipo(ti);
						director.setValorPagar(valorTotal);
						proyectoActual.adicionarInvestigadorProyecto(director);
						calcularValorDocente();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				// No puede existir más de un director
				mensajeError(horasCoinv, "El director ya se encuentra vinculado.)");
			}
		}
	}

	/**
	 * Se calcula el valor del docente sumando el investigador principal y lo de los
	 * investigadores y administrativos.
	 */
	public void calcularValorDocente() {

		Long valor = 0L;
		Long valorAdm = 0L;

		if (!esListaVacia(proyectoActual.getListaInvestigadorPrincipal())) {
			InvestigadorProyecto inv = proyectoActual.getListaInvestigadorPrincipal().get(0);
			if(inv.getValorPagar()==null) {
				inv.setValorPagar(0L);
			}
			valor = valor + inv.getValorPagar();
		}

		List<InvestigadorProyecto> listaParticipantes = proyectoActual.getListaInvestigadoresProyectoConDatos();
		if (!esListaVacia(listaParticipantes)) {
			for (int i = 0; i < listaParticipantes.size(); i++) {

				InvestigadorProyecto inv = listaParticipantes.get(i);
				if (inv.getValorPagar() != null) {
					if (!"AD".equals(inv.getTipo().getId())) {
						valor = valor + inv.getValorPagar();
					} else {
						valorAdm = valorAdm + inv.getValorPagar();
					}
				}
			}
		}

		proyectoActual.setValorPersonalTotal(valor);
		proyectoActual.setValorAdministrativoTotal(valorAdm);

		if (!mostrarMenuFormulario) {
			calcularTotalesFicha();
		}
	}

	/**
	 * Validar datos minimos finalizar edicion.
	 * 
	 * @param esRegistroUnico the es registro unico
	 * @return true, if successful
	 */
	protected boolean validarDatosMinimosFinalizarEdicion(boolean esRegistroUnico) {
		boolean valido = true;

		// Clase de proyecto
		if (esRegistroUnico && esCadenaVacia(proyectoActual.getTipoActividad())) {
			valido = false;
			mensajeError("Por favor seleccione el tipo de proyecto");
		}

		// Tipo de proyecto
		if (esRegistroUnico && esCadenaVacia(proyectoActual.getSubTipoActividadECP())) {
			valido = false;
			mensajeError("Por favor seleccione la clase de proyecto");
		}

		// Tipología de proyecto
		if (!esConvPurdue) {
			if (esCadenaVacia(proyectoActual.getTipoActividadECP())) {
				valido = false;
				mensajeError("Por favor seleccione la tipología del proyecto");
			}
		}

		// Rol de la universidad
		if (esRegistroUnico && esCadenaVacia(proyectoActual.getRolUniversidad())) {
			valido = false;
			mensajeError("Por favor seleccione el rol de la universidad");
		}

		// Tipo de financiación
		if (esRegistroUnico && esCadenaVacia(proyectoActual.getMecanismoParticipacion())
				&& !"FM_TES".equals(proyectoActual.getTipoActividad())) {
			valido = false;
			mensajeError("Por favor seleccione el tipo de financiación");
		}

		if (esRegistroUnico && valido && !"SIN_FINAN".equals(proyectoActual.getMecanismoParticipacion())
				&& esListaVacia(proyectoActual.getListaFuentesFinancacionFicha())
				&& esListaVacia(proyectoActual.getListaEntidadesParticipantes())) {
			valido = false;
			mensajeError("Por favor ingrese la financiación del proyecto.");
		}

		// se excluye la convocatoria de laboratorios 2022-2024
		if (!esRegistroUnico && mostrarMenuFormulario && convocatoriaActual.getMontoApoyoGanadores() != null && Long
				.parseLong(convocatoriaActual.getMontoApoyoGanadores())>0
				&& !esConvExtSol2018 && !convocatoriaActual.getPadre().getEsConvocatoriaLaboratorios2024()) { 
			Long montoApoyoGanadores = Long.parseLong(convocatoriaActual.getMontoApoyoGanadores());
			if (valorFinanciadoInvestig + proyectoActual.getContrapartidaEfectivo() > montoApoyoGanadores) {
				valido = false;
				mensajeError("El monto solicitado supera el máximo de la convocatoria: " + montoApoyoGanadores);
			}
		}

		// convocatorias laboratorios 2022-2024
		
		

		if (convocatoriaActual.isMostrarDependenciasAportantes()) {
			if (valorFinanciadoInvestig + proyectoActual.getContrapartidaEfectivo() != totalDependenciasAportantes()) {
				valido = false;
				mensajeError("El valor de las dependencias aportantes ("+ totalDependenciasAportantes() +") es diferente del valor "
					+ "de las fuentes de financiación("+ valorFinanciadoInvestig + proyectoActual.getContrapartidaEfectivo() +"). Por favor verifique.");
			}
			
			if(!proyectoActual.getContrapartidaEspecieTotal().equals(totalEspecieDependenciasAportantes())) {
				valido = false;
				mensajeError("El valor de especie de las dependencias aportantes ("+ totalEspecieDependenciasAportantes() +") es diferente del valor "
					+ "de las fuentes de financiación("+ proyectoActual.getContrapartidaEspecieTotal() +"). Por favor verifique.");
		
			}
			
			if (convocatoriaActual.getPadre().getEsConvocatoriaLaboratorios2024()) {
				if (totalDependenciasAportantesNivelNacional() < convocatoriaActual.getMontonMinimo()) {
					valido = false;
					mensajeError("El valor de las dependencias aportantes de nivel nacional debe " + "ser mínimo de "
							+ convocatoriaActual.getMontonMinimo());
				}
			}
			
			if(convocatoriaActual.getPadre().getEsConvocatoriaProyectos2022_4()) {
				InvestigadorInterno investigadorActualInterno = (InvestigadorInterno) servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
				if(investigadorActualInterno!=null && investigadorActualInterno.getDependencia().getSede().isEsSedeAndina()) {
					Double minimoContrapartida = (totalDependenciasAportantes() * 0.3);
					if(totalDependenciasAportantesSedeFacultad()<minimoContrapartida) {
						valido = false;
						DecimalFormat df = new DecimalFormat("#");
						df.setMinimumFractionDigits(2);
						mensajeError("El valor de las dependencias aportantes que no son de nivel nacional debe " + "ser mínimo de "
								+ (df.format(minimoContrapartida)));
					}
				}
				
			}
		}
		
		
		List<ModalidadFuenteFinanciacion> lista = servicioModalidad
				.listaModFuenteFinXModalidad(convocatoriaActual.getId());

		if (!esListaVacia(lista)) {
			Iterator<ModalidadFuenteFinanciacion> f = lista.iterator();
			while (f.hasNext()) {
				ModalidadFuenteFinanciacion fuente = f.next();
				if(fuente.getMontoMaximo()!=null && fuente.getMontoMaximo()>0) {
					for(int i=0; i<proyectoActual.getListaFinancionesFicha().size();i++) {
						Financiacion fin = proyectoActual.getListaFinancionesFicha().get(i);
						if(fin.getFuente().getId().equals(fuente.getFuenteFinanciacion().getId())) {
							if(fin.getValor()>fuente.getMontoMaximo() || fin.getValor()<fuente.getMontoMinimo()) {
								valido=false;
								mensajeError("Revise la fuente  "+fin.getFuente().getDescripcion() + " el valor mínimo de financiación es '"+fuente.getMontoMinimo()+"' y el "
										+ "monto máximo es de '"+fuente.getMontoMaximo()+"'");
								break;
							}
						}
					}
					if(!valido) {
						break;
					}
				}
				
			}			
		}
		
		if(convocatoriaActual.validarDetalleGasto()) {
			
			if(!proyectoActual.getListaFinancionesFicha().isEmpty()) {
				for(int i=0; i<proyectoActual.getListaFinancionesFicha().size();i++) {
					Financiacion fin = proyectoActual.getListaFinancionesFicha().get(i);
					if(!FuenteFinanciacion.ENTIDAD_PARTICIPANTE.equals(fin.getTipoEntidad()) && fin.getListaGastos()!=null && fin.getListaGastos().size()>0) {
						for(int j=0; j<fin.getListaGastos().size(); j++) {
							Gasto g = fin.getListaGastos().get(j);
							if(esCadenaVacia(g.getDescripcion())) {
								valido = false;
								mensajeError("Debe especificar el detalle del rubro para todos los rubros en los que especificó algún valor");
								break;
							}
						}
						if(!valido) {
							break;
						}
					}
				}
			}
			
		}

		// Objetivo socioecon
		if (esCadenaVacia(this.proyectoActual.getObjetivoSocioeconomico())) {
			valido = false;
			mensajeError("Por favor seleccione el objetivo socioeconómico");
		}

		// Area OCDE
		if (esCadenaVacia(areaCiencia)) {
			valido = false;
			mensajeError("Por favor seleccione 'Area científica y tecnológica principal'");
		}

		// SubArea OCDE
		if (esCadenaVacia(subAreaCiencia)) {
			valido = false;
			mensajeError("Por favor seleccione 'Sub-área de la ciencia'");
		}

		// Lineas acción
		if (!esProyectoTesisPosgrado && !esConvCP2019) {
			if (proyectoActual.getCodLineaAccion() == null || proyectoActual.getCodLineaAccion().equals(0)) {
				valido = false;
				mensajeError("Por favor seleccione 'Nodo de la propuesta'");
			}
		}

		// Programa
		if (!esProyectoTesisPosgrado && !esConvCP2019) {
			if (proyectoActual.getCodPrograma() == null || proyectoActual.getCodPrograma().equals(0)) {
				valido = false;
				mensajeError("Por favor seleccione 'Eje de la propuesta'");
			}
		}

		// Area OCDE Secundarias
		if (esConvCP2019 && esListaVacia(listaAreasTematicas)) {
			valido = false;
			mensajeError("Por favor agregue mínimo un área secundaria");
		}

		// dependencia
		if (!esConvPurdue) {
			if (esListaVacia(proyectoActual.getListaDependenciasAreaResponsabilidad())) {
				valido = false;
				mensajeError("Por favor agregue mínimo una dependencia responsable del proyecto");
			}
		}

		if ((proyectoActual.getId() == null || (proyectoActual.getId() != null
				&& proyectoActual.getId() > Proyecto.MAXIMO_SIN_PRODUCTOS_ACADEMICOS))
				&& esListaVacia(proyectoActual.getListaProductosProyecto())
				&& convocatoriaActual.isMostrarProductosAcademicos()
				&& !convocatoriaActual.getPadre().getEsConvocatoriaLaboratorios()) {
			valido = false;
			mensajeError("Por favor agregue mínimo un producto académico al proyecto.");
		}

		// Biodiversidad
		if (mostrarBiodiversidad) {
			if (esCadenaVacia(proyectoActual.getTieneBiodiversidad())) {
				valido = false;
				mensajeError("Por favor responda si hará uso de los recursos de la biodiversidad colombiana");
			} else if (proyectoActual.getTieneBiodiversidad().equals("Si")) {

				if (proyectoActual.getTieneComunidades() == null) {
					valido = false;
					mensajeError("Por favor responda si su proyecto se realizará en áreas de comunidades étnicas");
				}

				if (this.proyectoActual.getTieneParques() == null) {
					valido = false;
					mensajeError("Por favor responda si su proyecto se realizará en un área de Parques Nacionales");
				}

				if (this.proyectoActual.getTieneRecoleccion() == null) {
					valido = false;
					mensajeError("Por favor responda si cuenta con un permiso de recolección de especímenes");
				}

				if (this.proyectoActual.getTieneBioProspeccion() == null) {
					valido = false;
					mensajeError(
							"Por favor responda si su proyecto cuenta con actividades de bioprospección, industrialización,  comercialización o patentamiento");
				}
			}
		}

		// Entidad externa - valor en Especie
		if (esRegistroUnico && esCadenaVacia(proyectoActual.getAporteEconomico())
				&& "Si".equals(proyectoActual.getAporteEconomico())
				&& esListaVacia(proyectoActual.getListaEntidadesEspecie())) {
			valido = false;
			mensajeError(
					"Por favor ingrese el valor en especie de la entidad convocante y de clic sobre el botón 'Agregar'");
		}

		// Palabras Clave
		if (proyectoActual.getListaPalabrasES().size() < 3 && !esConvCP2019) {
			valido = false;
			mensajeError("Por favor agregue mínimo 3 palabras clave");
		} else if (proyectoActual.getListaPalabrasES().size() < 1 && esConvCP2019) {
			valido = false;
			mensajeError("Por favor agregue mínimo una palabra clave");
		}

		// Campos información general
		if (mostrarResumen) {
			if (esCadenaVacia(proyectoActual.getResumen()) || "--".equals(proyectoActual.getResumen())) {
				valido = false;
				mensajeError("Ingrese el resumen.");
			}
		}

		if (mostrarDescripcionProblema) {
			if (esCadenaVacia(proyectoActual.getMarcoTeorico()) || "--".equals(proyectoActual.getMarcoTeorico())) {
				valido = false;
				mensajeError("Ingrese la descripción del problema.");
			}
		}

		if (!esConvPurdue && mostrarObjetivos) {
			if (esCadenaVacia(proyectoActual.getObjetivoGeneral())
					|| "--".equals(proyectoActual.getObjetivoGeneral())) {
				valido = false;
				mensajeError("Ingrese el Objeto/Objetivo Principal.");
			}
		}

		if (mostrarObjetivosEspecificos) {
			if (esListaVacia(proyectoActual.getListaObjetivos())) {
				valido = false;
				mensajeError("Ingrese al menos un objetivo especifico.");
			}
		}

		if (mostrarResultados) {
			if (esListaVacia(proyectoActual.getListaResultados())
					&& !convocatoriaActual.getPadre().getEsConvocatoriaLaboratorios()) {
				valido = false;
				mensajeError("Ingrese al menos un resultado esperado.");
			}
		}

		// Proyecto asociado
		if(convocatoriaActual.getEsProyectoObligagorio()!=null && convocatoriaActual.getEsProyectoObligagorio().equals("Y")) {
			if (proyectoActual.getProyectoAsociado() != null && proyectoActual.getProyectoAsociado() >0) {
				try {
					if (proyectoActual.getProyectoAsociado() > 200000) {
						mensajeError("Por favor ingrese un código (numérico) hermes del proyecto asociado válido. ");
						valido = false;
					}
				} catch (Exception e) {
					mensajeError("Por favor ingrese el código (numérico) hermes del proyecto asociado. ");
					valido = false;
				}
			} else {
				mensajeError("Por favor ingrese el código (numérico) hermes del proyecto asociado. ");
				valido = false;
			}
		} else {
			proyectoActual.setProyectoAsociado(null);
		}

		if (esListaVacia(proyectoActual.getListaCiudadesProyecto())
				&& !proyectoActual.getModalidad().getId().equals(931L) && !esConvPurdue) {
			valido = false;
			mensajeError("Ingrese la región de impacto del proyecto o programa.");
		}

		if (!esConvocatoriaAlianzas2018 && !esConvocatoriaAlianzas2019 && mostrarOpcionTiempoDedicacionFormulacion
				&& !proyectoActual.getNaDuracionF()) {
			String sHorasSem = String.valueOf(proyectoActual.getHorasSemanaFormulacion());
			if (sHorasSem.length() > 2 || proyectoActual.getHorasSemanaFormulacion() < 0) {
				valido = false;
				mensajeError("Las horas semanales dedicadas a la formulación debe ser de máximo 2 dígitos y mayor a 0");
			}

			String sSemFor = String.valueOf(proyectoActual.getSemanasFormulacion());
			if (sSemFor.length() > 3 || proyectoActual.getSemanasFormulacion() < 0) {
				valido = false;
				mensajeError("Las semanas dedicadas a la formulación debe ser de máximo 3 dígitos y mayor a 0");
			}
		}

		if (mostrarMenuFormulario && validarEquipoTrabajo) {
			if (esListaVacia(proyectoActual.getListaInvestigadoresProyectoSinDatos())
					&& esListaVacia(proyectoActual.getListaInvestigadoresProyectoConDatos())) {
				valido = false;
				mensajeError("Por favor registre la información relacionada con el equipo de trabajo");
			} else {
				if ((proyectoActual.getListaInvestigadoresProyectoSinDatos().size()
						+ proyectoActual.getListaInvestigadoresProyectoConDatos().size()) < convocatoriaActual
								.getConValidarEquipoTrabajo()) {
					valido = false;
					mensajeError("Por favor registre la información de al menos "
							+ convocatoriaActual.getConValidarEquipoTrabajo() + " integrante del equipo de trabajo");
				}
			}
		}

		if (mostrarMenuFormulario && esConvProyectos2017_2018) {

			if (esListaVacia(proyectoActual.getListaProductosProyecto())
					|| proyectoActual.getListaProductosProyecto().size() < 2) {
				valido = false;
				mensajeError("Por favor seleccione al menos dos productos académicos");
			}

			personaActual = (Persona) sesion.getAttribute("persona");
			InvestigadorInterno invI = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());

			if (invI.getDependencia().getSede().getId().equals(2L) || invI.getDependencia().getSede().getId().equals(3L)
					|| invI.getDependencia().getSede().getId().equals(4L)
					|| invI.getDependencia().getSede().getId().equals(5L)) {
				if (esListaVacia(proyectoActual.getListaInvestigadoresProyectoSinDatos())
						&& esListaVacia(proyectoActual.getListaInvestigadoresProyectoConDatos())) {
					valido = false;
					mensajeError("Por favor registre la información de al menos un estudiante de pregrado o semillero");
				} else {
					boolean valEst = false;
					for (int i = 0; i < proyectoActual.getListaInvestigadoresProyectoConDatos().size(); i++) {
						InvestigadorProyecto ip = proyectoActual.getListaInvestigadoresProyectoConDatos().get(i);
						if ("EPRS".equals(ip.getTipo().getId()) || "ESPR".equals(ip.getTipo().getId())) {
							valEst = true;
						}
					}

					if (!valEst) {
						valido = false;
						mensajeError(
								"Por favor registre la información de al menos un estudiante de pregrado o semillero");
					}
				}
			}
		}

		// Validaciones especificas convocatorias
		if (esConvExtSol2018) {
			if (proyectoActual.getContrapartidaEfectivo() > Long
					.parseLong(convocatoriaActual.getMontoApoyoGanadores())) {
				valido = false;
				mensajeError("El monto solicitado supera el máximo de la convocatoria ($"
						+ Long.parseLong(convocatoriaActual.getMontoApoyoGanadores()) + ")");
			}
		}

		if (esConvocatoriaAlianzas2018) {
			int estudiantesPosgrado = 0;
			int estudiantesPregrado = 0;
			int estudiantesPosgradoSD = 0;
			int estudiantesPregradoSD = 0;
			// validar estudiantes pregrado y posgrado
			List<InvestigadorProyecto> listaParticipantes = proyectoActual.getListaInvestigadoresProyectoConDatos();
			List<InvestigadorProyecto> listaParticipantesSD = proyectoActual.getListaInvestigadoresProyectoSinDatos();
			if (esListaVacia(listaParticipantes) && esListaVacia(listaParticipantesSD)) {
				valido = false;
				mensajeError(
						"Debe registrar mínimo un estudiante de pregrado o de posgrado de la Universidad Nacional de Colombia.");

			} else {
				for (int i = 0; i < listaParticipantes.size(); i++) {
					InvestigadorProyecto invpry = listaParticipantes.get(i);
					if (invpry.getTipo().getId().equals("ESPO")) {
						estudiantesPosgrado++;
					} else {
						if (invpry.getTipo().getId().equals("ESPR")) {
							estudiantesPregrado++;
						}
					}
				}

				for (int i = 0; i < listaParticipantesSD.size(); i++) {
					InvestigadorProyecto invpry = listaParticipantesSD.get(i);
					if (invpry.getTipo().getId().equals("ESPO")) {
						estudiantesPosgradoSD += invpry.getDedicacionHorasSemana();
					} else {
						if (invpry.getTipo().getId().equals("ESPR")) {
							estudiantesPregradoSD += invpry.getDedicacionHorasSemana();
						}
					}
				}

				if (estudiantesPosgrado + estudiantesPosgradoSD + estudiantesPregrado + estudiantesPregradoSD < 1) {
					valido = false;
					mensajeError(
							"Debe registrar mínimo un estudiante de pregrado o de posgrado de la Universidad Nacional de Colombia.");
				}

			}

			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			String monApoyoGanadores = formatter.format(Long.parseLong(convocatoriaActual.getMontoApoyoGanadores()));

			if (Long
					.parseLong(convocatoriaActual.getMontoApoyoGanadores())>0 && proyectoActual.getContrapartidaEfectivo() > Long
					.parseLong(convocatoriaActual.getMontoApoyoGanadores())) {
				valido = false;
				mensajeError("El monto solicitado supera el máximo de la convocatoria (" + monApoyoGanadores + ")");
			}

			Long sumaAportes = 0L;
			Long sumaAportesNacional = 0L;
			Long sumaAportesSedesFac = 0L;

			for (int i = 0; i < proyectoActual.getListaDependenciasAreaResponsabilidad().size(); i++) {
				DependenciaAreaResponsabilidad dep = proyectoActual.getListaDependenciasAreaResponsabilidad().get(i);
				if (dep.getValorAporte() != null) {
					sumaAportes += dep.getValorAporte();
				}
				if (dep.getDependencia().getSede().getId().equals(1L)) {
					sumaAportesNacional += dep.getValorAporte();
				} else {
					sumaAportesSedesFac += dep.getValorAporte();
				}
			}

			if (sumaAportesNacional > 35000000L) {
				valido = false;
				mensajeError("Monto de aporte de Nivel Nacional de máximo $35.000.000");
			}

			// if(sumaAportesSedesFac < (sumaAportesNacional/2)){
			// valido = false;
			// mensajeError("Las facultades o sedes a las cuales pertenecen los
			// grupos que hacen parte de la alianza, deberán en conjunto,
			// aportar como mínimo el 50% de la cantidad a financiar por el
			// nivel nacional.");
			// }

			String montoSolicitado = formatter.format(proyectoActual.getContrapartidaEfectivo());
			String monSumaAportes = formatter.format(sumaAportes);

			if (!proyectoActual.getContrapartidaEfectivo().equals(sumaAportes)) {
				valido = false;
				mensajeError("El monto solicitado (" + montoSolicitado
						+ ") es diferente a la suma de los aportes de las dependencias participantes (" + monSumaAportes
						+ ").");
			}

			if (proyectoActual.getListaProductosProyecto().isEmpty()) {
				valido = false;
				mensajeError("Debe registrar los tres compromisos esperados.");
			} else {
				int proOblRegist = 0;
				for (int i = 0; i < proyectoActual.getListaProductosProyecto().size(); i++) {
					ProyectoProducto pt = (ProyectoProducto) proyectoActual.getListaProductosProyecto().get(i);
					if (pt.getProducto().getId().equals("854") || pt.getProducto().getId().equals("855")
							|| pt.getProducto().getId().equals("856")) {
						proOblRegist++;
					}
				}

				if (proOblRegist < 3) {
					valido = false;
					mensajeError("Debe registrar los tres compromisos esperados.");
				}
			}
		}

		Long valorFuente = 0L;
		if (proyectoActual.getListaFinanciones().size() > 0) {
			for (Object item : proyectoActual.getListaFinanciones()) {
				Financiacion fin = (Financiacion) item;
				if (fin.getValor() != null) {
					valorFuente += fin.getValor();
				}
			}
		}

		if (esConvProyectos2016_2018) {
			int estudiantesPosgrado = 0;
			int estudiantesPregrado = 0;
			// validar estudiantes pregrado y posgrado

			List<InvestigadorProyecto> listaParticipantes = proyectoActual.getListaInvestigadoresProyectoConDatos();
			List<InvestigadorProyecto> listaParticipantesNum = proyectoActual.getListaInvestigadoresProyectoSinDatos();
			if (esListaVacia(listaParticipantesNum) && esListaVacia(listaParticipantes)) {
				valido = false;
				mensajeError("Debe registrar la información del equipo de trabajo");
			}

			if (!esListaVacia(listaParticipantesNum)) {
				for (int i = 0; i < listaParticipantesNum.size(); i++) {
					InvestigadorProyecto invpry = listaParticipantesNum.get(i);
					if (invpry.getTipo().getId().equals("SEMC") || invpry.getTipo().getId().equals("EPRS")) {
						estudiantesPregrado++;
					}
				}
			}

			if (!esListaVacia(listaParticipantes)) {
				for (int i = 0; i < listaParticipantes.size(); i++) {
					InvestigadorProyecto invpry = listaParticipantes.get(i);
					if (invpry.getTipo().getId().equals("ESPO")) {
						estudiantesPosgrado++;
					} else {
						if (invpry.getTipo().getId().equals("ESPR") || invpry.getTipo().getId().equals("EPRS")) {
							estudiantesPregrado++;
						}
					}
				}
			}

			if (estudiantesPosgrado == 0 && estudiantesPregrado > 0 && proyectoActual.getDuracion() > 8) {
				valido = false;
				mensajeError(
						"La duración del proyecto supera lo permitido si incluye solamente estudiantes de TDG o semilleros.");
			}

			if (estudiantesPosgrado == 0 && estudiantesPregrado == 0) {
				valido = false;
				mensajeError("Debe registrar la información de los estudiantes a vincular en el proyecto.");
			}

			if (!esListaVacia(proyectoActual.getListaInvestigadorPrincipal())) {
				InvestigadorProyecto invpry = proyectoActual.getListaInvestigadorPrincipal().get(0);
				InvestigadorInterno ii = servicioPersona
						.obtenerInvestigadorInternoDependenciaYFacultad(invpry.getInvestigador().getId());
				if (ii != null) {
					// Valores para las sedes andinas.
					Long maximo = 40000000L;
					Long minimo = 8000000L;
					// Valores para otras sedes.
					if (ii.getDependencia().getSede().getId().equals(7L)
							|| ii.getDependencia().getSede().getId().equals(8L)) {
						minimo = 3000000L;
					} else if (ii.getDependencia().getSede().getId().equals(6L)) {
						maximo = 25000000L;
						minimo = 3000000L;
					}
					if (valorFuente > maximo) {
						valido = false;
						mensajeError("Por proyecto se financiará máximo hasta $" + maximo);
					} else if (valorFuente < minimo) {
						valido = false;
						mensajeError("Por proyecto se financiará mínimo $" + minimo);
					}
				}
			}
		}

		if (esConvCundinamarca) {

			int estudiantesUNal = 0;
			int estudiantesUdeC = 0;
			int docenUdeC = 0;

			List<InvestigadorProyecto> listaParticipantes = proyectoActual.getListaInvestigadoresProyecto();

			// Validación de estudiantes de ambas universidades
			if (!esListaVacia(listaParticipantes)) {
				for (int i = 0; i < listaParticipantes.size(); i++) {
					InvestigadorProyecto invpry = listaParticipantes.get(i);
					if (invpry.getTipo().getId().equals("ESPR") || invpry.getTipo().getId().equals("ESPO")) {
						estudiantesUNal++;
					} else if (invpry.getTipo().getId().equals("EUDC") || invpry.getTipo().getId().equals("EPDC")) {
						estudiantesUdeC++;
					} else if (invpry.getTipo().getId().equals("PLDC") || invpry.getTipo().getId().equals("PUDC")
							|| invpry.getTipo().getId().equals("PODC")) {
						docenUdeC++;
					}
				}
			}
			if (estudiantesUNal == 0 || estudiantesUdeC == 0) {
				mensajeError(
						"Deben estar vinculados estudiantes de La Universidad Nacional y de la Universidad de Cundinamarca.");
				valido = false;
			}
			if (docenUdeC == 0) {
				mensajeError(
						"Deben estar vinculados docentes de La Universidad Nacional y de la Universidad de Cundinamarca.");
				valido = false;
			}
		}

		if (esConvSUE2017) {

			int docenUCMC = 0;// DUCMC PUCMC
			int docenUDFJC = 0;// DUDFJC PUDFJC
			int docenUMNG = 0;// DUMNG PUMNG
			int docenUPN = 0;// DUPN PUPN

			List<InvestigadorProyecto> listaParticipantes = proyectoActual.getListaInvestigadoresProyecto();

			// Validación de estudiantes de ambas universidades
			if (!esListaVacia(listaParticipantes)) {
				for (int i = 0; i < listaParticipantes.size(); i++) {
					InvestigadorProyecto invpry = listaParticipantes.get(i);
					if (invpry.getTipo().getId().equals("DUCMC") || invpry.getTipo().getId().equals("PPCMC")) {
						docenUCMC++;
					} else if (invpry.getTipo().getId().equals("DUDFJC") || invpry.getTipo().getId().equals("PPDFJC")) {
						docenUDFJC++;
					} else if (invpry.getTipo().getId().equals("DUMNG") || invpry.getTipo().getId().equals("PPMNG")) {
						docenUMNG++;
					} else if (invpry.getTipo().getId().equals("DUPN") || invpry.getTipo().getId().equals("PPUPN")) {
						docenUPN++;
					}
				}
			}

			if (docenUCMC + docenUDFJC + docenUMNG + docenUPN < 1) {
				mensajeError(
						"La propuesta no cumple con los requisitos relacionados con los investigadores principales de cada una de las universidades.");
				valido = false;
			}

			if (proyectoActual.getContrapartidaEfectivo() < Long
					.parseLong(convocatoriaActual.getMontoApoyoGanadores())) {
				valido = false;
				mensajeError("El monto mínimo solicitado debe ser $"
						+ Long.parseLong(convocatoriaActual.getMontoApoyoGanadores()));
			}

			if (Long
					.parseLong(convocatoriaActual.getMontoApoyoGanadores())>0 && proyectoActual.getContrapartidaEfectivo() > Long
					.parseLong(convocatoriaActual.getMontoApoyoGanadores())) {
				valido = false;
				mensajeError("El monto solicitado supera el máximo de la convocatoria ($"
						+ Long.parseLong(convocatoriaActual.getMontoApoyoGanadores()) + ")");
			}
		}

		if (esConvocatoriaAlianzas2019) {
			
			boolean tieneSedesAndinas = false;
			boolean tieneSedesPresenciaNal = false;
			boolean tieneInsitutosInterfac = false;
			boolean valGrupoVsDependencias = true;
			ArrayList<String> listaDependenciasAsociadas = new ArrayList<String>();

			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			// String monApoyoGanadores =
			// formatter.format(Long.parseLong(convocatoriaActual.getMontoApoyoGanadores()));
			//
			// if (proyectoActual.getContrapartidaEfectivo() > Long
			// .parseLong(convocatoriaActual.getMontoApoyoGanadores())) {
			// valido = false;
			// mensajeError("El monto solicitado supera el máximo de la
			// convocatoria (" + monApoyoGanadores + ")");
			// }

			Long sumaAportes = 0L;
			Long sumaAportesNacional = 0L;
			Long sumaAportesSedesFac = 0L;

			for (int i = 0; i < proyectoActual.getListaDependenciasAreaResponsabilidad().size(); i++) {
				DependenciaAreaResponsabilidad dar = proyectoActual.getListaDependenciasAreaResponsabilidad().get(i);
				if (dar.getValorAporte() == null) {
					dar.setValorAporte(0L);
				}
				
				sumaAportes += dar.getValorAporte();
				
				if (dar.getDependencia().getSede().getId().equals(1L)) {
					sumaAportesNacional += dar.getValorAporte();
				} else {
					sumaAportesSedesFac += dar.getValorAporte();
				}

				if (dar.getDependencia().getFacultad() != null) {
					listaDependenciasAsociadas.add(dar.getDependencia().getFacultad().getId().toString());
				} else {
					listaDependenciasAsociadas.add(dar.getDependencia().getSede().getId().toString());
				}

				if (dar.getDependencia().getSede().getId().equals(Sede.BOGOTA)
						|| dar.getDependencia().getSede().getId().equals(Sede.MEDELLIN)
						|| dar.getDependencia().getSede().getId().equals(Sede.MANIZALES)
						|| dar.getDependencia().getSede().getId().equals(Sede.PALMIRA)) {
					tieneSedesAndinas = true;
				}
				if (dar.getDependencia().getSede().getId().equals(Sede.CARIBE)
						|| dar.getDependencia().getSede().getId().equals(Sede.AMAZONIA)
						|| dar.getDependencia().getSede().getId().equals(Sede.ORINOQUIA)
						|| dar.getDependencia().getSede().getId().equals(Sede.TUMACO)) {
					tieneSedesPresenciaNal = true;
				}

				if (dar.getDependencia().getEsInstitutoInterfacultad() != null) {
					if (dar.getDependencia().getEsInstitutoInterfacultad()) {
						tieneInsitutosInterfac = true;
					}
				}
			}

			// validar montos de acuerdo a la dependencia de los grupos
			/* Se comenta este código de validación de la convocatoria de alianzas 2019 teniendo en cuenta que no aplica a la convocatoria
			 * 2022-2024 y la convocatoria 2019 está inactiva por lo cual no se registran nuevas propuestas.
			if (tieneSedesAndinas && !tieneSedesPresenciaNal && !tieneInsitutosInterfac) {
				if (sumaAportesNacional > 70000000) {
					valido = false;
					mensajeError("Monto de aporte de Nivel Nacional de máximo $70.000.000");
				}

				if (sumaAportesSedesFac < 10000000) {
					valido = false;
					mensajeError("El aporte de la alianza debe ser como mínimo $10.000.000");
				}
			} else {
				if (sumaAportesNacional > 80000000) {
					valido = false;
					mensajeError("Monto de aporte de Nivel Nacional de máximo $80.000.000");
				}
			}

			String montoSolicitado = formatter.format(proyectoActual.getContrapartidaEfectivo());
			String monSumaAportes = formatter.format(sumaAportes);

			if (!proyectoActual.getContrapartidaEfectivo().equals(sumaAportes)) {
				valido = false;
				mensajeError("El monto solicitado (" + montoSolicitado
						+ ") es diferente a la suma de los aportes de las dependencias participantes (" + monSumaAportes
						+ ").");
			}*/

			// validar que la dependencia de los grupos sea la misma que de las
			// dependencias agregadas
			/*for (int i = 0; i < proyectoActual.getListaGrupos().size(); i++) {
				Grupo g = (Grupo) proyectoActual.getListaGrupos().get(i);
				if (g.getDependencia().getFacultad() != null) {
					if (!listaDependenciasAsociadas.contains(g.getDependencia().getFacultad().getId().toString())) {
						valGrupoVsDependencias = false;
					}
				} else {
					if (!listaDependenciasAsociadas.contains(g.getDependencia().getSede().getId().toString())) {
						valGrupoVsDependencias = false;
					}
				}

			}

			if (!valGrupoVsDependencias) {
				valido = false;
				mensajeError(
						"Las dependencias de los grupos asociados no coinciden con las sedes y/o facultades y/o institutos que conforman la alianza");
			}*/

		}

		if (esConvDocNalEmp758_2018) {
			if (valorFinanciadoInvestig + proyectoActual.getContrapartidaEfectivo() > 50000000) {
				valido = false;
				mensajeError("El monto solicitado supera el máximo en efectivo por proyecto  ($"
						+ Long.parseLong(convocatoriaActual.getMontoApoyoGanadores()) + ")");
			}
		}

		if (esConvMedLegal2018) {
			if (proyectoActual.getContrapartidaEfectivo() > Long
					.parseLong(convocatoriaActual.getMontoApoyoGanadores())) {
				valido = false;
				mensajeError("El monto solicitado supera el máximo de la convocatoria ($"
						+ Long.parseLong(convocatoriaActual.getMontoApoyoGanadores()) + ")");
			}
		}

		if (esConvURosario) {
			int docentesUN = 0;
			int docentesUR = 0;
			int estudiantesUN = 0;
			int estudiantesUR = 0;
			List<InvestigadorProyecto> listaParticipantes = proyectoActual.getListaInvestigadoresProyecto();
			if (!esListaVacia(listaParticipantes)) {
				for (int i = 0; i < listaParticipantes.size(); i++) {
					InvestigadorProyecto invpry = listaParticipantes.get(i);
					if (invpry.getTipo().getId().equals("P") || invpry.getTipo().getId().equals("PCD")
							|| invpry.getTipo().getId().equals("PSCD") || invpry.getTipo().getId().equals("PEES")) {
						docentesUN++;
					} else if (invpry.getTipo().getId().equals("IPUR") || invpry.getTipo().getId().equals("PPUR")
							|| invpry.getTipo().getId().equals("PPEUR")) {
						docentesUR++;
					} else if (invpry.getTipo().getId().equals("ESPO") || invpry.getTipo().getId().equals("ESPR")) {
						estudiantesUN++;
					} else if (invpry.getTipo().getId().equals("EPRUR") || invpry.getTipo().getId().equals("EPOUR")) {
						estudiantesUR++;
					}
				}
			}
			if (docentesUN == 0 || docentesUR == 0 || estudiantesUN == 0 || estudiantesUR == 0) {
				mensajeError("La propuesta debe contar con un docente y un estudiante de cada universidad.");
				valido = false;
			}
		}

		if (convocatoriaActual.getRestriccionEquipoTrabajo() != null) {
			String[] valEquipoTrabajo = convocatoriaActual.getRestriccionEquipoTrabajo().split("-");
			String[] equipoTrabajoO = valEquipoTrabajo[0].split(",");
			String[] equipoTrabajoY = valEquipoTrabajo[1].split(",");
			ArrayList<EquipoTrabajoValidacion> listaEquipoTrabajoValidacionO = new ArrayList<ManejadorFichaMinimaBase.EquipoTrabajoValidacion>();
			ArrayList<EquipoTrabajoValidacion> listaEquipoTrabajoValidacionY = new ArrayList<ManejadorFichaMinimaBase.EquipoTrabajoValidacion>();
			boolean validarO = true;
			boolean validarY = true;

			if (equipoTrabajoO.length == 1 && equipoTrabajoO[0].equals("NA")) {
				validarO = false;
			} else {
				for (int i = 0; i < equipoTrabajoO.length; i++) {
					String[] infoEqTr = equipoTrabajoO[i].split("=");
					EquipoTrabajoValidacion etv = new EquipoTrabajoValidacion();
					etv.setTi(infoEqTr[0]);
					etv.setNumObligatorio(Integer.parseInt(infoEqTr[1]));
					listaEquipoTrabajoValidacionO.add(etv);
				}
			}

			if (equipoTrabajoY.length == 1 && equipoTrabajoY[0].equals("NA")) {
				validarY = false;
			} else {
				for (int i = 0; i < equipoTrabajoY.length; i++) {
					String[] infoEqTr = equipoTrabajoY[i].split("=");
					EquipoTrabajoValidacion etv = new EquipoTrabajoValidacion();
					etv.setTi(infoEqTr[0]);
					etv.setNumObligatorio(Integer.parseInt(infoEqTr[1]));
					listaEquipoTrabajoValidacionY.add(etv);
				}
			}

			List<InvestigadorProyecto> listaParticipantes = proyectoActual.getListaInvestigadoresProyectoConDatos();
			List<InvestigadorProyecto> listaParticipantesSD = proyectoActual.getListaInvestigadoresProyectoSinDatos();
			if (esListaVacia(listaParticipantes) && esListaVacia(listaParticipantesSD)) {

				valido = false;
				mensajeError(convocatoriaActual.getTextoValidacionEquipoTrabajo());
			} else {

				if (validarO) {
					for (int i = 0; i < listaParticipantes.size(); i++) {
						InvestigadorProyecto invpry = listaParticipantes.get(i);
						for (int j = 0; j < listaEquipoTrabajoValidacionO.size(); j++) {
							if (listaEquipoTrabajoValidacionO.get(j).getTi().equals(invpry.getTipo().getTipo())) {
								listaEquipoTrabajoValidacionO.get(j)
										.setNumVinculados(listaEquipoTrabajoValidacionO.get(j).getNumVinculados() + 1);
							} else if (listaEquipoTrabajoValidacionO.get(j).getTi().equals(invpry.getTipo().getId())) {
								listaEquipoTrabajoValidacionO.get(j)
										.setNumVinculados(listaEquipoTrabajoValidacionO.get(j).getNumVinculados() + 1);
							}
						}
					}

					for (int i = 0; i < listaParticipantesSD.size(); i++) {
						InvestigadorProyecto invpry = listaParticipantesSD.get(i);
						for (int j = 0; j < listaEquipoTrabajoValidacionO.size(); j++) {
							if (listaEquipoTrabajoValidacionO.get(j).getTi().equals(invpry.getTipo().getTipo())) {
								listaEquipoTrabajoValidacionO.get(j)
										.setNumVinculados(listaEquipoTrabajoValidacionO.get(j).getNumVinculados()
												+ (int) invpry.getDedicacionHorasSemana());
							} else if (listaEquipoTrabajoValidacionO.get(j).getTi().equals(invpry.getTipo().getId())) {
								listaEquipoTrabajoValidacionO.get(j)
										.setNumVinculados(listaEquipoTrabajoValidacionO.get(j).getNumVinculados()
												+ (int) invpry.getDedicacionHorasSemana());
							}
						}
					}
				}

				if (validarY) {
					for (int i = 0; i < listaParticipantes.size(); i++) {
						InvestigadorProyecto invpry = listaParticipantes.get(i);
						for (int j = 0; j < listaEquipoTrabajoValidacionY.size(); j++) {
							if (listaEquipoTrabajoValidacionY.get(j).getTi().equals(invpry.getTipo().getId())) {
								listaEquipoTrabajoValidacionY.get(j)
										.setNumVinculados(listaEquipoTrabajoValidacionY.get(j).getNumVinculados() + 1);
							} else if (listaEquipoTrabajoValidacionY.get(j).getTi()
									.equals(invpry.getTipo().getTipo())) {
								listaEquipoTrabajoValidacionY.get(j)
										.setNumVinculados(listaEquipoTrabajoValidacionY.get(j).getNumVinculados() + 1);
							}
						}
					}

					for (int i = 0; i < listaParticipantesSD.size(); i++) {
						InvestigadorProyecto invpry = listaParticipantesSD.get(i);
						for (int j = 0; j < listaEquipoTrabajoValidacionY.size(); j++) {
							if (listaEquipoTrabajoValidacionY.get(j).getTi().equals(invpry.getTipo().getId())) {
								listaEquipoTrabajoValidacionY.get(j)
										.setNumVinculados(listaEquipoTrabajoValidacionY.get(j).getNumVinculados()
												+ (int) invpry.getDedicacionHorasSemana());
							} else if (listaEquipoTrabajoValidacionY.get(j).getTi()
									.equals(invpry.getTipo().getTipo())) {
								listaEquipoTrabajoValidacionY.get(j)
										.setNumVinculados(listaEquipoTrabajoValidacionY.get(j).getNumVinculados()
												+ (int) invpry.getDedicacionHorasSemana());
							}
						}
					}
				}

				int valFalO = 0;
				int valFalY = 0;

				if (validarO) {
					for (int j = 0; j < listaEquipoTrabajoValidacionO.size(); j++) {
						if (listaEquipoTrabajoValidacionO.get(j).getNumObligatorio() > listaEquipoTrabajoValidacionO
								.get(j).getNumVinculados()) {
							valFalO++;
						}
					}
				}

				if (validarY) {
					for (int j = 0; j < listaEquipoTrabajoValidacionY.size(); j++) {
						if (listaEquipoTrabajoValidacionY.get(j).getNumObligatorio() > listaEquipoTrabajoValidacionY
								.get(j).getNumVinculados()) {
							valFalY++;
						}
					}
				}

				if ((validarO && valFalO == listaEquipoTrabajoValidacionO.size()) || (validarY && valFalY > 0)) {
					valido = false;
					mensajeError(convocatoriaActual.getTextoValidacionEquipoTrabajo());
				}

			}
		}

		if (convocatoriaActual.getNumeroProductosObligatorios() != null) {
			if (convocatoriaActual.getNumeroProductosObligatorios() > 0) {
				if (proyectoActual.getListaProductosProyecto().isEmpty()) {
					valido = false;
					mensajeError("Debe registrar como mínimo " + convocatoriaActual.getNumeroProductosObligatorios()
							+ " productos académicos.");
				} else {
					int numProductos = 0;
					for (int i = 0; i < proyectoActual.getListaProductosProyecto().size(); i++) {
						ProyectoProducto pp = (ProyectoProducto) proyectoActual.getListaProductosProyecto().get(i);
						numProductos += pp.getCantidad();
					}
					if (numProductos < convocatoriaActual.getNumeroProductosObligatorios()) {
						valido = false;
						mensajeError("Debe registrar como mínimo " + convocatoriaActual.getNumeroProductosObligatorios()
								+ " productos académicos.");
					}
				}
			}
		}

		if (convocatoriaActual.getProductosAValidar() != null) {
			String[] prodVal = convocatoriaActual.getProductosAValidar().split(",");
			boolean[] prodRegi = new boolean[prodVal.length];
			boolean valProdTot = true;
			for (int i = 0; i < prodVal.length; i++) {
				if (validarProductosRegistrados(prodVal[i])) {
					prodRegi[i] = true;
				} else {
					prodRegi[i] = false;
				}
			}

			for (int i = 0; i < prodRegi.length; i++) {
				if (prodRegi[i] == false) {
					valProdTot = false;
				}
			}

			if (valProdTot == false) {
				valido = false;
				mensajeError(
						"Por favor registre los productos obligatorios establecidos en los términos de referencia de la convocatoria: "
								+ convocatoriaActual.getMensajeProductosAValidar());
			}

		}

		if (convocatoriaActual.getMontonMinimo() > 0) {
			if (valorFuente < convocatoriaActual.getMontonMinimo()) {
				valido = false;
				mensajeError("El monto mínimo de la convocatoria es " + convocatoriaActual.getMontonMinimo());
			}
		}

		// Se validan metas
		if (convocatoriaActual.isMostrarMetas() && proyectoActual.getListaMetas().isEmpty()) {
			valido = false;
			mensajeError("Ingrese al menos una meta.");
		}

		if (objetivoDesarrolloSosteniblePrimario.equals("0")) {
			valido = false;
			mensajeError(btnObjDesSosSec, "Debe asociar al proyecto el objetivo de desarrollo sostenible principal.");
		} else {
			if (!valoresObjetivosDesarrolloSostenible.isEmpty()) {

				List<DominioDetalle> listaDomDet = servicioGeneral.obtenerObjetos(DominioDetalle.class,
						"select e from DominioDetalle e, Dominio d where e.identificador.tipo = '"
								+ objetivoDesarrolloSosteniblePrimario
								+ "' and e.identificador.id = d.id and d.tipo = '"
								+ DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE + "'");
				DominioDetalle g = obtenerDominioDetalleLista(objetivoDesarrolloSosteniblePrimario, listaDomDet);

				ValoresListasProyecto vlp = new ValoresListasProyecto();
				vlp.setProyecto(proyectoActual);
				vlp.setTipo(DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE);
				vlp.setValor(g.getIdentificador().getTipo());
				vlp.setNombreValor(g.getDescripcion());
				vlp.setDescripcion("OBJETIVO_DESARROLLO_SOSTENIBLE_PRINCIPAL");

				if (valoresObjetivosDesarrolloSostenible.contains(vlp)) {
					valido = false;
					mensajeError(btnObjDesSosSec,
							"El objetivo de desarrollo sostenible se encuentra vinculado como principal y secundario.");
				}
			}
		}

		if (proyectoActual.getRolUniversidad() != null) {
			// se valida si la universidad es ejecutura
			if (proyectoActual.getRolUniversidad().equals("ROL_EJECUT")
					|| proyectoActual.getRolUniversidad().equals("ROL_EJEC_UNIC")
					|| proyectoActual.getRolUniversidad().equals("ROL_EJEC_VARI")) {

				if (proyectoActual.getSubTipoActividadECP()!=null && proyectoActual.getSubTipoActividadECP().equals("Externo")) {
					if (proyectoActual.getMecanismoParticipacion().equals("FIN_EXTERNA")
							|| proyectoActual.getMecanismoParticipacion().equals("FIN_EXT_CONV")) {
						int tieneFinExt = 0;
						if (proyectoActual.getFinanciaciones().isEmpty()) {
							valido = false;
							mensajeError("Por favor vincule al menos una fuente de financiación externa al proyecto.");
						} else {
							Iterator<Financiacion> i = proyectoActual.getListaFuentesFinancacionFicha().iterator();
							while (i.hasNext()) {
								Financiacion financ = i.next();
								if (financ.getFuente().getInternaExterna().equals(FuenteFinanciacion.externa) || financ
										.getFuente().getInternaExterna().equals(FuenteFinanciacion.OCULTA_EXTERNA)) {
									tieneFinExt++;
								}
							}
							if (tieneFinExt < 1) {
								valido = false;
								mensajeError(
										"Por favor vincule al menos una fuente de financiación externa al proyecto.");
							}

							// if(convocatoriaActual.isIncluirContrapartidaFinanacion()){
							// if(proyectoActual.getContrapartidaEfectivo() +
							// proyectoActual.getContrapartidaEspecieTotal() <=
							// 0){
							// valido = false;
							// mensajeError(
							// "Por favor ingresar el valor de la contrapartida
							// en efectivo o en especie de la universidad.");
							// }
							// }else{
							// if(proyectoActual.getContrapartidaEfectivo() <=
							// 0){
							// valido = false;
							// mensajeError(
							// "Por favor ingresar el valor de la contrapartida
							// en efectivo de la universidad.");
							// }
							// }

						}
					} else {
						if (proyectoActual.getMecanismoParticipacion().equals("FIN_INTERNA")) {
							int tieneFinInt = 0;
							if (proyectoActual.getFinanciaciones().isEmpty()) {
								valido = false;
								mensajeError(
										"Por favor vincule al menos una fuente de financiación interna al proyecto.");
							} else {
								Iterator<Financiacion> i = proyectoActual.getListaFuentesFinancacionFicha().iterator();
								while (i.hasNext()) {
									Financiacion financ = i.next();
									if (financ.getFuente().getInternaExterna().equals(FuenteFinanciacion.interna)) {
										tieneFinInt++;
									}
								}
								if (tieneFinInt < 1) {
									valido = false;
									mensajeError(
											"Por favor vincule al menos una fuente de financiación interna al proyecto.");
								}
							}
						}
					}
				} else {
					if (proyectoActual.getMecanismoParticipacion().equals("FIN_INTERNA")) {
						int tieneFinInt = 0;
						if (proyectoActual.getFinanciaciones().isEmpty()) {
							valido = false;
							mensajeError("Por favor vincule al menos una fuente de financiación interna al proyecto.");
						} else {
							Iterator<Financiacion> i = proyectoActual.getListaFuentesFinancacionFicha().iterator();
							while (i.hasNext()) {
								Financiacion financ = i.next();
								if (financ.getFuente().getInternaExterna().equals(FuenteFinanciacion.interna)) {
									tieneFinInt++;
								}
							}
							if (tieneFinInt < 1) {
								valido = false;
								mensajeError(
										"Por favor vincule al menos una fuente de financiación interna al proyecto.");
							}
						}
					}
				}
			}
		}

		if (esConvExtSol2018) {
			if (proyectoActual.getDependenciaPresentacion() == null) {
				valido = false;
				mensajeError("Por favor indique la dependencia a la cual de presentará el proyecto.");
			}
			if (proyectoActual.getLugarEspecifico() == null
					|| (proyectoActual.getLugarEspecifico() != null && (proyectoActual.getLugarEspecifico().equals("")
							|| proyectoActual.getLugarEspecifico().equals("0")))) {
				valido = false;
				mensajeError("Por favor indique el lugar específico del proyecto.");
			}
			/*if (proyectoActual.getListaEntidadesParticipantes().isEmpty()
					&& convocatoriaActual.getTitulo().toLowerCase().contains("modalidad 2")) {
				valido = false;
				mensajeError("Por favor ingrese una entidad participante.");
			}*/
			if (unidadEjecutora.isEmpty()) {
				valido = false;
				mensajeError("Por favor ingrese el lugar de ejecución financiera del proyecto.");
			}
		}

		return valido;
	}

	public boolean validarProductosRegistrados(final String idProducto) {
		int proOblRegist = 0;
		for (int i = 0; i < proyectoActual.getListaProductosProyecto().size(); i++) {
			ProyectoProducto pt = (ProyectoProducto) proyectoActual.getListaProductosProyecto().get(i);
			if (pt.getProducto().getId().equals(idProducto)) {
				proOblRegist++;
			}
		}

		if (proOblRegist > 0) {
			return true;
		} else {
			return false;
		}
	}

	public class EquipoTrabajoValidacion {
		String ti;
		int numObligatorio;
		int numVinculados;

		public EquipoTrabajoValidacion() {
			super();
			// TODO Auto-generated constructor stub
		}

		public String getTi() {
			return ti;
		}

		public void setTi(String ti) {
			this.ti = ti;
		}

		public int getNumObligatorio() {
			return numObligatorio;
		}

		public void setNumObligatorio(int numObligatorio) {
			this.numObligatorio = numObligatorio;
		}

		public int getNumVinculados() {
			return numVinculados;
		}

		public void setNumVinculados(int numVinculados) {
			this.numVinculados = numVinculados;
		}

		public boolean equals(Object object) {
			if (object instanceof EquipoTrabajoValidacion) {
				EquipoTrabajoValidacion rt = (EquipoTrabajoValidacion) object;
				if (this.ti.equals(rt.getTi())) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

	}

	/**
	 * Se busca en la lista de tipos de investigador el tipo investiador
	 * seleccionado.
	 * 
	 * @param idTipoInvestigador   the id tipo investigador
	 * @param listaTipoVinculacion the lista tipo vinculacion
	 * @return the tipo investigador
	 */
	private TipoInvestigador buscarTipoInvestigador(String idTipoInvestigador,
			List<TipoInvestigador> listaTipoVinculacion) {
		if (!esListaVacia(listaTipoVinculacion)) {
			Iterator<TipoInvestigador> i = listaTipoVinculacion.iterator();
			while (i.hasNext()) {
				TipoInvestigador tipoInvestigador = i.next();
				if (tipoInvestigador.getId().equals(idTipoInvestigador)) {
					return tipoInvestigador;
				}
			}
		}
		return null;
	}

	/**
	 * Buscar participante externo.
	 */
	public void buscarParticipanteExterno() {

		boolean encontrado = false;
		mostrarDatosJoven = false;
		esOtraVinculacion = false;

		// Se carga la opción de mostrar programa academico
		TipoInvestigador tipoInvestigador = buscarTipoInvestigador(tipoVinculacionId, listaTipoVinculacion);
		mostrarProgramaAcademico = tipoInvestigador.isMostrarProgramaAcademico();
		mostrarDependencia = tipoInvestigador.isMostrarDependencia();

		if (!esCadenaVacia(documentoCoinvEquipo) && !esCadenaVacia(tipoDocumentoCoInvEquipo.getId())) {
			if (!validarDocumentoParticipante(tipoDocumentoCoInvEquipo.getId(), documentoCoinvEquipo)) {
				mensajeError(botonBuscarInvestigadorExterno,
						"Por favor verificar el número de documento ingresado (Cédula de ciudadanía (entre 8 y 10 dígitos), Documento de Identidad Extranjera, Cédula de Extranjería, Tarjeta de Identidad y Certificado cabildo deben ser númericos. El Pasaporte debe ser alfanumérico con las letras en mayúscula.).");
			} else {
				// Se verifica si existe en persona
				Persona personaExistente = servicioPersona
						.obtenerPersona(new IdPersona(documentoCoinvEquipo, tipoDocumentoCoInvEquipo.getId()));

				// Se verifica si existe en base de datos de estudiantes
				Estudiante estudiante = servicioPersona
						.obtenerEstudiante(new IdPersona(documentoCoinvEquipo, tipoDocumentoCoInvEquipo.getId()));

				if (personaExistente != null) {

					// Se verifica si ya existe en externo
					InvestigadorExterno investigadorExternoExistente = servicioPersona.obtenerInvestigadorExterno(
							new IdPersona(documentoCoinvEquipo, tipoDocumentoCoInvEquipo.getId()));

					if (investigadorExternoExistente != null) {
						personaExistente = investigadorExternoExistente;

						idTipoFormacion = (investigadorExternoExistente.getTipoFormacion() != null
								? investigadorExternoExistente.getTipoFormacion().getId()
								: "");
						idTipoEstadoCivil = (investigadorExternoExistente.getEstadoCivil() != null
								? investigadorExternoExistente.getEstadoCivil().getId()
								: "");
						idPaisNacimiento = (investigadorExternoExistente.getPaisOrigen() != null
								? investigadorExternoExistente.getPaisOrigen()
								: "");
						if (!idPaisNacimiento.equals("")) {
							revisarPais();
						}

						Ciudad ciu = investigadorExternoExistente.getCiudadNacimiento();

						if (ciu != null) {
							ciudadActual = new Ciudad();
							ciudadActual.setId(ciu.getId());
							String hqlDepto = "select e from Ciudad e where e.id = '" + ciu.getId() + "'";
							List<Ciudad> listaCiudad = servicioGeneral.obtenerObjetos(hqlDepto);
							Ciudad ciudadAux = listaCiudad.get(0);
							departamentoActual = ciudadAux.getDepartamento();
							cambiarDepartamento();
						}

						institucionSeleccionada = (investigadorExternoExistente.getInstitucion() != null
								? investigadorExternoExistente.getInstitucion().getId()
								: "");
						areaCienciaInv = (investigadorExternoExistente.getAreaOcde() != null
								? investigadorExternoExistente.getAreaOcde()
								: "");
						cambiarAreaInv();
						subAreaCienciaInv = (investigadorExternoExistente.getSubareaOcde() != null
								? investigadorExternoExistente.getSubareaOcde()
								: "");
					}

					this.investigadorExterno = personaExistente;

					if (estudiante != null) {
						cargarDatosEstudianteaPersona(estudiante);
					}
					encontrado = true;
				} else if (estudiante != null) {
					investigadorExterno = estudiante.convertirAPersona();
					encontrado = true;
				}

				if (tipoVinculacionId.equals(TipoInvestigador.JOVEN_INVESTIGADOR_POSGRADO)
						|| tipoVinculacionId.equals(TipoInvestigador.JOVEN_INVESTIGADOR_EGRESADO)) {
					mostrarDatosJoven = true;
				}
				List<Pais> listaPaises;
				if (tipoDocumentoCoInvEquipo.getId().equals("DE") || tipoDocumentoCoInvEquipo.getId().equals("E")) {
					listaPaises = cargarPaises(false);
				} else {
					listaPaises = cargarPaises(true);
				}
				listaPaisesItem = new SelectItem[listaPaises.size()];
				for (int i = 0; i < listaPaises.size(); i++) {
					Pais p = (Pais) listaPaises.get(i);
					listaPaisesItem[i] = new SelectItem(p.getId(), p.getNombre());
				}

				if (!encontrado) {
					mensajeInfo(buscarPer2,
							"Persona no encontrada. Nuevo registro por favor ingresar los datos del participante.");
				}

				mostrarOpcionBuscarInvestigador = false;
				esOtraVinculacion = true;
			}
		} else {
			mensajeError(botonBuscarInvestigadorExterno, "Por favor ingrese el número y el tipo de documento.");
		}

	}

	/**
	 * Metodo para agregar un area y subarea secundaria.
	 */
	public void agregarArea() {

		boolean existeArea = false;

		if (esCadenaVacia(areaCienciaSec)) {
			// Se valida que se haya seleccionado un area de la ciencia padre.
			mensajeError(botonAreasTabla, "Seleccione el área científica y tecnológica secundaria.");
		} else if (esCadenaVacia(subAreaCienciaSec)) {
			// Se valida que se haya seleccionado un sub area de la ciencia
			mensajeError(botonAreasTabla, "Seleccione la sub-área de la ciencia secundaria.");
		} else {

			// Se carga el area de la ciencia seleccionada.
			List<DominioDetalle> listaDomDetarUno = servicioGeneral.obtenerObjetos(DominioDetalle.class,
					"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
							+ DOMINIO_AREA_CIENCIA + "' and dd.identificador.tipo = '" + areaCienciaSec + "'");
			DominioDetalle areaDominioSecundariaPadre = (DominioDetalle) listaDomDetarUno.get(0);

			// Se carga la subarea de la ciencia seleccionada.
			List<DominioDetalle> listaDomDetarDos = servicioGeneral.obtenerObjetos(DominioDetalle.class,
					"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
							+ DOMINIO_SUB_AREA_CIENCIA + "' and dd.identificador.tipo = '" + subAreaCienciaSec + "'");
			DominioDetalle areaDominioSecundariaHija = (DominioDetalle) listaDomDetarDos.get(0);

			// Se crea el objeto del area tematica
			AreaTematicaVista arTem = new AreaTematicaVista();
			arTem.setProyecto(proyectoActual);
			arTem.setNombreArea(areaDominioSecundariaPadre.getDescripcion());
			arTem.setAreaTematica(areaDominioSecundariaPadre);
			arTem.setNombreSubArea(areaDominioSecundariaHija.getDescripcion());
			arTem.setSubAreaTematica(areaDominioSecundariaHija);
			arTem.setTipo(2L);

			// Se valida si no existe en la lista
			for (int i = 0; i < listaAreasTematicas.size(); i++) {
				AreaTematicaVista atv = listaAreasTematicas.get(i);
				if (arTem.getSubAreaTematica().getIdentificador().getTipo()
						.equals(atv.getSubAreaTematica().getIdentificador().getTipo())) {
					existeArea = true;
				}
			}

			// Si no existe se agrea, si existe se desplega mensaje.
			if (!existeArea) {

				AreaTematica areaTematica = new AreaTematica();
				areaTematica.setProyectoAreaTematica(areaDominioSecundariaHija);
				areaTematica.setTipo(2L);

				arTem.setAreaTematicaProyecto(areaTematica);

				proyectoActual.adicionarAreaTematica(areaTematica);
				listaAreasTematicas.add(arTem);

				areaCienciaSec = "";
				subAreaCienciaSec = "";
				cambiarAreaSec();
			} else {
				mensajeError(botonAreasTabla,
						"El área científica y tecnológica seleccionada ya se encuentra vinculada al proyecto");
			}
		}
	}

	/**
	 * Calcular edad.
	 * 
	 * @param fechaNac the fecha nac
	 * @return the integer
	 */
	private static Integer calcularEdad(Date fechaNac) {

		Calendar fechaNacimiento = Calendar.getInstance();

		// Se crea un objeto con la fecha actual
		Calendar fechaActual = Calendar.getInstance();

		// Se asigna la fecha recibida a la fecha de nacimiento.
		fechaNacimiento.setTime(fechaNac);

		// Se restan la fecha actual y la fecha de nacimiento
		int anio = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
		int mes = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH);
		int dia = fechaActual.get(Calendar.DATE) - fechaNacimiento.get(Calendar.DATE);
		// Se ajusta el año dependiendo el mes y el día
		if (mes < 0 || (mes == 0 && dia < 0)) {
			anio--;
		}
		// Regresa la edad en base a la fecha de nacimiento
		return anio;
	}

	/**
	 * Cargar datos estudiantea persona.
	 * 
	 * @param estudiante the estudiante
	 */
	private void cargarDatosEstudianteaPersona(Estudiante estudiante) {
		if (esCadenaVacia(investigadorExterno.getEmail())) {
			investigadorExterno.setEmail(estudiante.getEmail());
		}
		if (esCadenaVacia(investigadorExterno.getTelefono())) {
			investigadorExterno.setTelefono(estudiante.getTelefono());
		}
		if (investigadorExterno.getFechaNacimiento() == null) {
			investigadorExterno.setFechaNacimiento(estudiante.getFechaNacimiento());
		}
		if (esCadenaVacia(investigadorExterno.getGenero())) {
			investigadorExterno.setGenero(estudiante.getGenero());
		}
		if (esCadenaVacia(investigadorExterno.getProfesion()) && estudiante.getPlan().getTipo() != null
				&& estudiante.getPlan().getTipo().equals(PlanEstudios.PREGRADO)) {
			investigadorExterno.setProfesion(estudiante.getPlan().getNombre());
		}
		if (esCadenaVacia(investigadorExterno.getPromedioPregrado()) && estudiante.getPapa() != null
				&& estudiante.getPlan().getTipo().equals(PlanEstudios.PREGRADO)) {
			investigadorExterno.setPromedioPregrado(estudiante.getPapa().toString());
		}
		if (esCadenaVacia(investigadorExterno.getResumenHojaDeVida())
				&& estudiante.getPlan().getTipo().equals(PlanEstudios.PREGRADO)) {
			investigadorExterno.setResumenHojaDeVida("UNIVERSIDAD NACIONALDE COLOMBIA");
		}
		if (esCadenaVacia(investigadorExterno.getFacultadPregrado())
				&& estudiante.getPlan().getTipo().equals(PlanEstudios.PREGRADO)
				&& estudiante.getDependencia() != null) {
			investigadorExterno.setFacultadPregrado(estudiante.getDependencia().getFacultad().getNombre());
		}
		if (esCadenaVacia(investigadorExterno.getNacionalidad())) {
			investigadorExterno.setNacionalidad("Colombiano/a");
		}
	}

	/**
	 * Este metodo permite verificar si la vinculación de un investigador esta
	 * asociada al tipo de investigador que se esta agregando.
	 * 
	 * @param tipoVinculacion  the tipo vinculacion
	 * @param tipoInvestigador the tipo investigador
	 * @return true, if successful
	 */
	private boolean validarVinculacionInvestigador(String tipoVinculacion, TipoInvestigador tipoInvestigador) {
		if (!esCadenaVacia(tipoInvestigador.getTipoVinculacion())) {
			int encuentra = tipoInvestigador.getTipoVinculacion().indexOf("-" + tipoVinculacion + "-");
			return encuentra > -1;
		}
		return false;
	}

	/**
	 * Crear usuario investigador.
	 * 
	 * @param crearInvestigadorInterno the crear investigador interno
	 * @param inv                      the per
	 */
//	private void crearUsuarioInvestigador(TipoInvestigador tipoInvestigador, Investigador inv) {
//		if (tipoInvestigador.isCrearInvestigador()) {
//			try {
//				PersonaRol pr = new PersonaRol();
//				pr.setDocumento(inv.getId().getDocumento());
//				pr.setTipoDocumento(inv.getId().getTipoDocumento());
//				pr.setNombre("I");
//				pr.setFechaInicioRol(getToday());
//				Calendar c = Calendar.getInstance();
//				c.add(Calendar.YEAR, 1);
//				pr.setFechaFinRol(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/" + c.get(Calendar.YEAR)));
//				servicioGeneral.guardarObjeto(pr);
//			} catch (Exception ex) {
//				System.out.println("Ya tenía el rol investigador");
//			}
//
//		}
//	}
	
	private void crearUsuarioEstudianteLider(TipoInvestigador tipoInvestigador, Investigador inv) {
		if (tipoInvestigador.isCrearInvestigador()) {
			try {
				PersonaRol pr = new PersonaRol();
				pr.setDocumento(inv.getId().getDocumento());
				pr.setTipoDocumento(inv.getId().getTipoDocumento());
				pr.setNombre("AL");
				pr.setFechaInicioRol(getToday());
				Calendar c = Calendar.getInstance();
				c.add(Calendar.YEAR, 1);
				pr.setFechaFinRol(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/" + c.get(Calendar.YEAR)));
				servicioGeneral.guardarObjeto(pr);
			} catch (Exception ex) {
				System.out.println("Ya tenía el rol Estudiante Líder");
			}

		}
	}

	private void crearUsuarioAsistenteLider(TipoInvestigador tipoInvestigador, Investigador inv) {
		if (tipoInvestigador.isCrearInvestigador()) {
			try {
				PersonaRol pr = new PersonaRol();
				pr.setDocumento(inv.getId().getDocumento());
				pr.setTipoDocumento(inv.getId().getTipoDocumento());
				pr.setNombre("ADL");
				pr.setFechaInicioRol(getToday());
				Calendar c = Calendar.getInstance();
				c.add(Calendar.YEAR, 1);
				pr.setFechaFinRol(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/" + c.get(Calendar.YEAR)));
				servicioGeneral.guardarObjeto(pr);
			} catch (Exception ex) {
				System.out.println("Ya tenía el rol Asistente Líder");
			}

		}
	}

	public void enviarCorreoAsistentesLideres(List<InvestigadorProyecto> investigadores) {

		Iterator<InvestigadorProyecto> i = proyectoActual.getListaInvestigadoresProyecto().iterator();
		while (i.hasNext()) {
			InvestigadorProyecto investigadorProyecto = i.next();

			TipoInvestigador tipoInvestigador = investigadorProyecto.getTipo();
			Investigador inv = investigadorProyecto.getInvestigador();

			if (tipoInvestigador.getId().equals("ADL")) {
				enviarCorreoAsistenteLider(proyectoActual.getId(), inv);

			}
		}

	}

	private void enviarCorreoUsuarioContrasenia(List<InvestigadorProyecto> investigadores) {

		Iterator<InvestigadorProyecto> i = proyectoActual.getListaInvestigadoresProyecto().iterator();
		while (i.hasNext()) {
			InvestigadorProyecto investigadorProyecto = i.next();

			TipoInvestigador tipoInvestigador = investigadorProyecto.getTipo();
			Investigador inv = investigadorProyecto.getInvestigador();

			// Se valida si para este tipo investigador se debe enviar correo
			// con usuario y contraseña.
			if (tipoInvestigador.isEnviarCorreoContrasenia() && inv.isEnviarContrasenia()) {
				InvestigadorExterno investigadorExterno = crearContraseniaExterno(inv);

				// Se verifica si ya existe el investigador externo.
				if (investigadorExterno != null) {

					// Se carga la plantilla.
					CorreoPlantilla correoPlantilla = cargarPlantilla(CorreoPlantilla.USUARIO_CONTRASENIA);

					if (correoPlantilla != null) {

						// Se ajusta el usuario y contraseña.
						String cuerpo = correoPlantilla.getCuerpo();
						cuerpo = cuerpo.replaceAll("<<usuario>>", servicioPersona.login(investigadorExterno));
						cuerpo = cuerpo.replaceAll("<<clave>>", investigadorExterno.getContrasena().toString());

						Correo correo = new Correo();
						correo.setOrigen(Correo.CORREO_HERMES);
						correo.adicionarDireccion(inv.getEmail());
						//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
						correo.setAsunto(correoPlantilla.getAsunto());
						correo.setCuerpo(cuerpo);

						if (!servicioCorreo.enviarCorreo(correo)) {
							System.out.println("Hubo un problema con el envío del correo a nuevo investigador.");
						} else {
							investigadorExterno.setEnviarContrasenia(false);
							servicioGeneral.guardarObjeto(investigadorExterno);
						}
					}
				}
			}
		}

	}

	private InvestigadorExterno crearContraseniaExterno(Persona persona) {
		InvestigadorExterno ie = servicioPersona.obtenerInvestigadorExterno(persona.getId());
		if (ie != null) {
			if (ie.getContrasena() == null) {
				ie.setContrasena(new Long(servicioPersona.generarClaveExterno()));
			}
			servicioGeneral.guardarObjeto(ie);
			return ie;
		} else {
			return null;
		}
	}

	/**
	 * Se verifica al cambiar la vinculación si se debe mostrar toda la informacion
	 * de un investigador.
	 */
	public void cambiarVinculacion() {
		asignarGrupo = false;
		esOtraVinculacion = false;
		mostrarDatosJoven = false;
		mostrarOpcionBuscarInvestigador = false;
		mostrarProgramaAcademico = false;
		mostrarDependencia = false;
		if (!esCadenaVacia(tipoVinculacionId)) {
			TipoInvestigador tipoInvestigador = buscarTipoInvestigador(tipoVinculacionId, listaTipoVinculacion);
			if (tipoInvestigador != null && !esCadenaVacia(tipoInvestigador.getTipo())
					&& TipoVinculacion.TIPO_EXTERNO.equals(tipoInvestigador.getTipo())) {
				mostrarOpcionBuscarInvestigador = true;
			} else if (tipoVinculacionId.equals(TipoInvestigador.JOVEN_INVESTIGADOR_POSGRADO)
					|| tipoVinculacionId.equals(TipoInvestigador.JOVEN_INVESTIGADOR_EGRESADO)) {
				mostrarOpcionBuscarInvestigador = true;
			} else if ("'ADL','DINMLCF','DUCMC','DUDFJC','DUMNG','DUPN','PUCMC','PUDFJC','PUMNG','PUPN','EUCMC','EUDFJC','EUMNG','EUPN','EPUCMC','EPUDFJC','EPUMNG','EPUPN','PPCMC','PPDFJC','PPMNG','PPUPN'"
					.contains(tipoVinculacionId) && !tipoVinculacionId.equals("AD")) {
				mostrarOpcionBuscarInvestigador = true;
			}
		}
	}

	/**
	 * Si es un funcionario se ejecuta este metodo en donde se valida si las
	 * vinculaciones son correctas, en caso de que sea correcto se calcula el valor
	 * de contrapartida.
	 * 
	 * @param tipoInvestigador the tipo investigador
	 */
	private void agregarFuncionario(TipoInvestigador tipoInvestigador) {

		// búsqueda en investigador interno
		InvestigadorInterno investigadorInterno = servicioPersona
				.obtenerInvestigadorInterno(new IdPersona(documentoCoinvEquipo, tipoDocumentoCoInvEquipo.getId()));

		if (investigadorInterno != null) {

			// Se valida que la vincuación sea correcta con los valores
			// ingresados.
			boolean encuentraInternoVinculacion = false;
			boolean esProfesorCarreraDocenteActivo = false;
			if (investigadorInterno.getTipoVinculacion() != null && validarVinculacionInvestigador(
					investigadorInterno.getTipoVinculacion().getId(), tipoInvestigador)) {
				encuentraInternoVinculacion = true;
			} else if (tipoVinculacionId.equals(TipoInvestigador.PROFESOR_CARRERA_DOCENTE)) {
				mensajeError(buscarPer2, "La persona ingresada no es de de carrera docente.");
			} else if (tipoVinculacionId.equals(TipoInvestigador.ADMINISTRATIVO)) {
				mensajeError(buscarPer2, "La persona ingresada no es administrativa.");
			} else if (tipoVinculacionId.equals(TipoInvestigador.PROFESOR_NO_CARRERA_DOCENTE)) {
				mensajeError(buscarPer2, "La persona adicionada no es un profesor sin carrera docente.");
			}
			
//			if(tipoVinculacionId.equals(TipoInvestigador.PROFESOR_CARRERA_DOCENTE)
//					&& !esNulo(investigadorInterno.getInterno()) 
//					&& investigadorInterno.getInterno().equals("S") 
//					&& !investigadorInterno.getPeriodoPrueba()) {
//				esProfesorCarreraDocenteActivo = true;
//			}
			
			if(tipoVinculacionId.equals(TipoInvestigador.PROFESOR_CARRERA_DOCENTE)
					&& !esNulo(investigadorInterno.getInterno()) 
					&& investigadorInterno.getInterno().equals("S")) {
				esProfesorCarreraDocenteActivo = true;
			}
			
//			if(tipoVinculacionId.equals(TipoInvestigador.PROFESOR_CARRERA_DOCENTE) && !esProfesorCarreraDocenteActivo){
//				mensajeError(buscarPer2, "La persona ingresada no es de de carrera docente o no está activo o está en periodo de prueba.");
//				return;
//			}
			
			if(tipoVinculacionId.equals(TipoInvestigador.PROFESOR_CARRERA_DOCENTE) && !esProfesorCarreraDocenteActivo){
				mensajeError(buscarPer2, "La persona ingresada no es de de carrera docente o no está activo.");
				return;
			}
			
			if (encuentraInternoVinculacion) {

				Long valorTotal = 0L;
				// Calcular valor dedicación del funcionario si es
				// Docente o Administrativo.
				if (!tipoVinculacionId.equals(TipoInvestigador.PROFESOR_NO_CARRERA_DOCENTE) || (tipoVinculacionId.equals(TipoInvestigador.PROFESOR_NO_CARRERA_DOCENTE) && investigadorInterno.getTipoVinculacion().getId().equals(TipoVinculacion.DOCENTE_ESP_CON_PREST))) {
					valorTotal = calcularValorFuncionario(horasParticipante, tiempoTotalParticipante,
							investigadorInterno.getValorHoraValidado());
				}

				// Agregar participante a la listaparticipante
				InvestigadorProyecto participanteNuevo = new InvestigadorProyecto();
				participanteNuevo.setInvestigador(investigadorInterno);
				participanteNuevo.setDedicacionHorasSemana(horasParticipante);
				participanteNuevo.setTotalHorasVinculacion(Double.valueOf(tiempoTotalParticipante));
				if (convocatoriaActual.isMostrarActividadesInvestigador()) {
					participanteNuevo.setFuncion(funcionInvestigador);
				} else {
					participanteNuevo.setFuncion(InvestigadorProyecto.FUNCION_PARTICIPANTE_BASICO);
				}
				participanteNuevo.setProyecto(proyectoActual);
				participanteNuevo.setTipo(tipoInvestigador);
				participanteNuevo.setValorPagar(valorTotal);
				participanteNuevo.setDependencia(investigadorInterno.getDependencia());
				if (StringUtils.isNotBlank(grupo)) {
					participanteNuevo.setGrupo(servicioGrupo.obtenerGrupo(Long.parseLong(grupo)));
				}
				proyectoActual.adicionarInvestigadorProyecto(participanteNuevo);

				documentoCoinvEquipo = "";
				horasParticipante = 0;
				tiempoTotalParticipante = 0;
				funcionInvestigador = "";
				tipoVinculacionId = "";
				cambiarVinculacion();

				// CALCULAR TOTAL PERSONAL Y PROYECTO
//				crearUsuarioInvestigador(tipoInvestigador, investigadorInterno);
			}

		} else {
			mensajeError(buscarPer2, "La persona ingresada no se encuentra registrada.");
		}
	}


	/**
	 * Agregar estudiante.
	 * 
	 * @param tipoInvestigador the tipo investigador
	 */
	private void agregarEstudiante(TipoInvestigador tipoInvestigador) {

		IdPersona idEst = new IdPersona(this.documentoCoinvEquipo, this.tipoDocumentoCoInvEquipo.getId());
		Estudiante e = servicioPersona.obtenerEstudiante(idEst);

		if (e != null) { // Se encontró como estudiante
			
			if(!esNulo(e.getInterno()) && !e.esInterno()) {
				mensajeError(buscarPer2,
						"El estudiante ingresado no está activo para el plan de estudios: " + e.getPlan().getNombre());
				return;
			}

			if(!validarVinculacionInvestigador(e.getPlan().getTipo().toString(), tipoInvestigador)) {
				mensajeError(buscarPer2,
						"El estudiante ingresado no pertenece al nivel del tipo de vinculación seleccionado. El plan de estudios al que pertenece el estudiante es: "
								+ e.getPlan().getNombre());
				return;
			}

			Investigador nuevoInvestigador = servicioPersona.obtenerInvestigador(idEst);

			if (nuevoInvestigador == null) {
				InvestigadorInterno nvoinv = e.convertirAInvestigador();

				if (e.getDependencia() != null) {
					nvoinv.setDependencia(e.getDependencia());
				} else {
					Dependencia dependencia = servicioDependencia.obtenerDependencia("1");
					e.setDependencia(dependencia);
					nvoinv.setDependencia(e.getDependencia());
				}

				Persona per = servicioPersona.obtenerPersona(nvoinv.getId());
				
				if(esNulo(per)) {
					Persona persona = e.convertirAPersona();
					servicioPersona.insertarNuevaPersona(persona);
				}
				
				servicioPersona.insertarNuevoInvestigador(nvoinv);
				servicioPersona.insertaInterno(nvoinv);
				
				nuevoInvestigador = servicioPersona.obtenerInvestigador(nvoinv.getId());
			}

			// Agregar participante a la listaparticipante
			InvestigadorProyecto participanteNuevo = new InvestigadorProyecto();
			participanteNuevo.setInvestigador(nuevoInvestigador);
			participanteNuevo.setDedicacionHorasSemana(horasParticipante);
			participanteNuevo.setTotalHorasVinculacion(Double.valueOf(tiempoTotalParticipante));
			if (convocatoriaActual.isMostrarActividadesInvestigador()) {
				participanteNuevo.setFuncion("Estudiante de: " + e.getPlan().getNombre() + "\n " + funcionInvestigador);
			} else {
				participanteNuevo.setFuncion(InvestigadorProyecto.FUNCION_PARTICIPANTE_BASICO);
			}
			participanteNuevo.setProyecto(proyectoActual);
			participanteNuevo.setTipo(tipoInvestigador);
			participanteNuevo.setDependencia(e.getDependencia());
			participanteNuevo.setPlan(e.getPlan());
			participanteNuevo.setValorPagar(0L);
			if (StringUtils.isNotBlank(grupo)) {
				participanteNuevo.setGrupo(servicioGrupo.obtenerGrupo(Long.parseLong(grupo)));
			}
			proyectoActual.adicionarInvestigadorProyecto(participanteNuevo);

			documentoCoinvEquipo = "";
			horasParticipante = 0;
			tiempoTotalParticipante = 0;
			funcionInvestigador = "";
			tipoVinculacionId = "";
			cambiarVinculacion();
//			crearUsuarioInvestigador(tipoInvestigador, nuevoInvestigador);
			if (tipoInvestigador.getId().equals("AL")) {
				crearUsuarioEstudianteLider(tipoInvestigador, nuevoInvestigador);
			}

		} else { // No se encontró como estudiante
			mensajeError(buscarPer2, "El documento ingresado no pertenece a un estudiante.");
		}
	}

	/**
	 * Se crea externo. Si no existe se crea en base de datos.
	 * 
	 * @param tipoInvestigador the tipo investigador
	 */
	private void agregarExterno(TipoInvestigador tipoInvestigador) {

		// Si es externo o no está registrado.
		if (validarDatosMinimosExterno()) {

			IdPersona idPersona = new IdPersona();
			idPersona.setDocumento(documentoCoinvEquipo);
			idPersona.setTipoDocumento(tipoDocumentoCoInvEquipo.getId());

			Persona nuevaPersona = servicioPersona.obtenerPersona(idPersona);
			if (nuevaPersona == null) {
				try {// Si la persona no existe se guarda como
						// investigador
					investigadorExterno.setId(idPersona);

					// estadocivil
					EstadoCivil ec = new EstadoCivil();
					ec.setId(idTipoEstadoCivil);
					investigadorExterno.setEstadoCivil(ec);
					investigadorExterno.setPaisOrigen(idPaisNacimiento);

					if (ciudadActual != null) {
						investigadorExterno.setCiudadNacimiento(ciudadActual);
					}
					servicioPersona.insertarNuevaPersona(investigadorExterno);
					nuevaPersona = servicioPersona.obtenerPersona(investigadorExterno.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				investigadorExterno.validarActualizacionNombres(nuevaPersona);
				nuevaPersona = investigadorExterno;
				// estadocivil
				EstadoCivil ec = new EstadoCivil();
				ec.setId(idTipoEstadoCivil);
				nuevaPersona.setEstadoCivil(ec);
				nuevaPersona.setPaisOrigen(idPaisNacimiento);

				if (ciudadActual != null) {
					nuevaPersona.setCiudadNacimiento(ciudadActual);
				}
				servicioGeneral.guardarObjeto(nuevaPersona);
			}

			Investigador investigador = servicioPersona.obtenerInvestigador(nuevaPersona.getId());
			if (investigador == null) {
				investigador = new Investigador();
				investigador.setId(nuevaPersona.getId());
				CategoriaInvestigador categoriaInvestigador = new CategoriaInvestigador();
				categoriaInvestigador.setId(3L);
				investigador.setCategoriaInvestigador(categoriaInvestigador);
				investigador.setInterno(Investigador.EXTERNO);
				investigador.setEvaluador(Investigador.NO_EVALUADOR);
				investigador.setEsFuncionario(Investigador.NO_FUNCIONARIO);
				// area y subarea cientifica y tecnológica
				investigador.setAreaOcde(areaCienciaInv);
				investigador.setSubareaOcde(subAreaCienciaInv);
				servicioPersona.insertarInvestigador(investigador);
				investigador = servicioPersona.obtenerInvestigador(nuevaPersona.getId());
				investigador.setEnviarContrasenia(true);
			} else {
				// area y subarea cientifica y tecnológica
				investigador.setAreaOcde(areaCienciaInv);
				investigador.setSubareaOcde(subAreaCienciaInv);
				investigador.setEnviarContrasenia(true);
				servicioGeneral.guardarObjeto(investigador);
			}

			// Si no existe la institucion se asigna en blanco.
			FuenteFinanciacion institucionExterno = new FuenteFinanciacion();
			if (institucionSeleccionada != null) {
				institucionExterno = (FuenteFinanciacion) servicioGeneral.obtenerObjeto(new FuenteFinanciacion(), institucionSeleccionada);
//				institucionExterno.setId(institucionSeleccionada);
			} else {
				institucionExterno.setId("0");
			}

			// máximo nivel de estudio
			TipoFormacion tf = new TipoFormacion();
			tf.setId(idTipoFormacion);
			InvestigadorInterno investigadorInterno = servicioPersona.obtenerInvestigadorInterno(nuevaPersona.getId());

			if (investigadorInterno == null) {

				InvestigadorExterno nuevoInvestigadorExterno = servicioPersona
						.obtenerInvestigadorExterno(investigador.getId());
				if (nuevoInvestigadorExterno == null) {
					nuevoInvestigadorExterno = new InvestigadorExterno();
					nuevoInvestigadorExterno.setId(investigador.getId());
					nuevoInvestigadorExterno.setInstitucionInvestigador(institucionExterno);
					nuevoInvestigadorExterno.setTipoFormacion(tf);
					servicioPersona.insertarNuevoInvestigadorExterno(nuevoInvestigadorExterno);
				} else {
					nuevoInvestigadorExterno.setInstitucionInvestigador(institucionExterno);
					nuevoInvestigadorExterno.setTipoFormacion(tf);
					servicioGeneral.guardarObjeto(nuevoInvestigadorExterno);
				}
			}

			if (tipoVinculacionId.equals(TipoInvestigador.JOVEN_INVESTIGADOR_POSGRADO)
					|| tipoVinculacionId.equals(TipoInvestigador.JOVEN_INVESTIGADOR_EGRESADO)) {

				String consultaCiudadNacimiento = "from Ciudad cc where  cc.id = '" + this.lugarNacimiento + "'";
				List<Ciudad> listaLugarExpedicion = servicioGeneral.obtenerObjetos(Ciudad.class,
						consultaCiudadNacimiento);
				if (!esListaVacia(listaLugarExpedicion)) {
					Ciudad ciudadNacimiento = (Ciudad) listaLugarExpedicion.get(0);
					investigador.setCiudadNacimiento(ciudadNacimiento);
					servicioGeneral.guardarObjeto(investigador);
				}

			}

			// Agregar participante a la listaparticipante
			InvestigadorProyecto participanteExternoNuevo = new InvestigadorProyecto();
			participanteExternoNuevo.setInvestigador(investigador);
			participanteExternoNuevo.setDedicacionHorasSemana(horasParticipante);
			participanteExternoNuevo.setTotalHorasVinculacion(Double.valueOf(tiempoTotalParticipante));
			if (convocatoriaActual.isMostrarActividadesInvestigador()) {
				participanteExternoNuevo.setFuncion(funcionInvestigador);
			} else {
				participanteExternoNuevo.setFuncion(InvestigadorProyecto.FUNCION_PARTICIPANTE_BASICO);
			}
			participanteExternoNuevo.setProyecto(proyectoActual);
			participanteExternoNuevo.setTipo(tipoInvestigador);
			participanteExternoNuevo.setValorPagar(0L);
			if (institucionExterno != null) {
				participanteExternoNuevo.setInstitucionInvestigador(institucionExterno);
			}
			if (StringUtils.isNotBlank(grupo)) {
				participanteExternoNuevo.setGrupo(servicioGrupo.obtenerGrupo(Long.parseLong(grupo)));
			}
			proyectoActual.adicionarInvestigadorProyecto(participanteExternoNuevo);

			documentoCoinvEquipo = "";
			horasParticipante = 0;
			tiempoTotalParticipante = 0;
			funcionInvestigador = "";
			tipoVinculacionId = "";
			investigadorExterno = new Persona();
			cambiarVinculacion();
			if (tipoInvestigador.getId().equals("ADL")) {
				crearUsuarioAsistenteLider(tipoInvestigador, investigador);
			} /*else {
				crearUsuarioInvestigador(tipoInvestigador, investigador);
			}*/

		} else {
			mensajeError(buscarPer2,
					"La persona indicada no se encuentra registrada. Por favor diligencie los campos correspondientes y a continuación haga clic nuevamente sobre el botón 'Agregar'.");
		}
	}

	public void enviarCorreoAsistenteLider(final Long idProyecto, Investigador investigadorAL) {
		/*
		 * Investigador inv =
		 * servicioProyecto.obtenerInvestigadorPrincipalXProyecto(idProyecto); Correo
		 * correo = new Correo(); CorreoPlantilla cp = cargarPlantilla(328);
		 * correo.setOrigen(Correo.CORREO_HERMES);
		 * correo.setAsunto(cp.getAsunto().replaceAll("<<ID_PROYECTO>>",
		 * idProyecto.toString()));
		 * correo.setCuerpo(cp.getCuerpo().replaceAll("<<ID_PROYECTO>>",
		 * idProyecto.toString()));
		 * correo.adicionarDireccion(investigadorAL.getEmail());
		 * correo.adicionarDireccion(inv.getEmail());
		 * correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
		 * servicioCorreo.enviarCorreo(correo);
		 */
	}

	/**
	 * Metodo para agregar un nuevo tipo de vinculación solo con el número de
	 * integrantes.
	 */
	public void agregarParticipanteNum() {

		if (!esCadenaVacia(tipoVinculacionId)) {
			TipoInvestigador tipoInvestigador = buscarTipoInvestigador(tipoVinculacionId, listaTipoVinculacionNumero);

			InvestigadorInterno investigadorInterno = servicioPersona.obtenerInvestigadorInterno(
					new IdPersona(tipoInvestigador.getDocumento(), tipoInvestigador.getTipoDocumento()));

			// Agregar participante a la listaparticipante
			if (investigadorInterno != null) {

				// Se valida que no se haya agregado.
				boolean yaEsta = validarAgregadoEquipo(investigadorInterno.getId().getDocumento(),
						investigadorInterno.getId().getTipoDocumento(),
						proyectoActual.getListaInvestigadoresProyectoSinDatos());

				if (!yaEsta) {

					if (cantidadPart != null && cantidadPart > 0) {

						// Se crea participante
						InvestigadorProyecto participanteNuevo = new InvestigadorProyecto();
						participanteNuevo.setInvestigador(investigadorInterno);
						participanteNuevo.setDedicacionHorasSemana(Short.parseShort(cantidadPart.toString()));
						if (tiempoTotalParticipante != null) {
							participanteNuevo.setTotalHorasVinculacion(Double.valueOf(tiempoTotalParticipante));
						} else {
							participanteNuevo.setTotalHorasVinculacion(Double.valueOf(0));
						}

						if (convocatoriaActual.isMostrarActividadesInvestigador()) {
							if ((convocatoriaActual.getPadre().getId().equals(508L)
									|| convocatoriaActual.getPadre().getId().equals(555L))
									&& esCadenaVacia(funcionInvestigador)) {
								mensajeError(
										"Por favor, indique las actividades y responsabilidades del(de los) investigador(es) a registrar.");
								return;
							}
							participanteNuevo.setFuncion(funcionInvestigador);
						} else {
							participanteNuevo.setFuncion(InvestigadorProyecto.FUNCION_PARTICIPANTE_BASICO);
						}

						participanteNuevo.setTipo(tipoInvestigador);
						participanteNuevo.setValorPagar(0L);

						proyectoActual.adicionarInvestigadorProyecto(participanteNuevo);
						setAsignarGrupo(false);
						this.grupo = "";

					} else {
						mensajeError(buscarPer3, "La cantidad de participantes debe ser mayor a cero.");
					}
				} else {
					mensajeError(buscarPer3, "Este tipo de vinculación ya fue agregado.");
				}
			}
		}

	}

	/**
	 * Validar datos minimos externo.
	 * 
	 * @return true, if successful
	 */
	private boolean validarDatosMinimosExterno() {

		boolean verifica = true;
		String mensajeErrorInv = "";

		if (!validarDocumentoParticipante(tipoDocumentoCoInvEquipo.getId(), documentoCoinvEquipo)) {
			verifica = false;
			mensajeErrorInv += "- Por favor verificar el número de documento que registrado (Cédula de ciudadanía, Documento de Identidad Extranjera, Cédula de Extranjería, Tarjeta de Identidad y Certificado cabildo deben ser númericos. Pasaporte debe ser alfanumérico).";
		}

		if (tipoDocumentoCoInvEquipo.getId().equals("C")) {
			if (documentoCoinvEquipo.length() > 11) {
				verifica = false;
				mensajeErrorInv += "Longitud del documento de identificación incorrecta, máximo 11 dígitos.";
			}
		}

		if (esCadenaVacia(investigadorExterno.getNombre1())) {
			verifica = false;
			mensajeErrorInv += "Por favor ingresar el primer nombre.";
		} else {
			if (!validarTextoSinNumeros(investigadorExterno.getNombre1())) {
				verifica = false;
				mensajeErrorInv += "- El primer nombre no debe contener números ni espacios.";
			}
		}

		if (!esCadenaVacia(investigadorExterno.getNombre2())) {
			if (!validarTextoSinNumeros(investigadorExterno.getNombre2())) {
				verifica = false;
				mensajeErrorInv += "- El segundo nombre no debe contener números ni espacios.";
			}
		}

		if (esCadenaVacia(investigadorExterno.getApellido1())) {
			verifica = false;
			mensajeErrorInv += "- Por favor ingresar el primer apellido.";
		} else {
			if (!validarTextoSinNumeros(investigadorExterno.getApellido1())) {
				verifica = false;
				mensajeErrorInv += "- El primer apellido no debe contener números ni espacios.";
			}
		}

		if (!esCadenaVacia(investigadorExterno.getApellido2())) {
			if (!validarTextoSinNumeros(investigadorExterno.getApellido2())) {
				verifica = false;
				mensajeErrorInv += "- El segundo apellido no debe contener números ni espacios.";
			}
		}

		if (esCadenaVacia(investigadorExterno.getGenero())) {
			verifica = false;
			mensajeErrorInv += "- Por favor seleccionar el sexo biológico del investigador.";
		}
		if (esCadenaVacia(investigadorExterno.getEmail())) {
			verifica = false;
			mensajeErrorInv += "- Por favor ingresar el Email del investigador.";
		}

		if (esCadenaVacia(idTipoFormacion)) {
			verifica = false;
			mensajeErrorInv += "- Por favor ingresar el máximo nivel de estudio del investigador.";
		}

		if (esCadenaVacia(idTipoEstadoCivil)) {
			verifica = false;
			mensajeErrorInv += "- Por favor ingresar el estado civil del investigador.";
		}

		if (investigadorExterno.getFechaNacimiento() == null) {
			verifica = false;
			mensajeErrorInv += "- Por favor verificar la fecha de nacimiento.";
		} else {
			if (tipoDocumentoCoInvEquipo.getId().equals("T")) {
				int edadDoc = calcularEdad(investigadorExterno.getFechaNacimiento());
				if (edadDoc > 17) {
					verifica = false;
					mensajeErrorInv += "Por favor verificar el tipo de documento del investigador.";
				}
			} else {
				if (tipoDocumentoCoInvEquipo.getId().equals("C")) {
					int edadDoc = calcularEdad(investigadorExterno.getFechaNacimiento());
					if (edadDoc < 18) {
						verifica = false;
						mensajeErrorInv += "Por favor verificar el tipo de documento o la fecha de nacimiento del investigador.";
					}
				}
			}
		}

		if (esCadenaVacia(idPaisNacimiento)) {
			verifica = false;
			mensajeErrorInv += "- Por favor seleccionar el país de nacimiento del investigador.";
		}

		if (siColombia) {
			if (esCadenaVacia(departamentoActual.getId())) {
				verifica = false;
				mensajeErrorInv += "- Por favor seleccionar el departamento de nacimiento del investigador.";
			}
		}

		if (esCadenaVacia(ciudadActual.getId())) {
			verifica = false;
			mensajeErrorInv += "- Por favor seleccionar la ciudad de nacimiento del investigador.";
		}

		if (esCadenaVacia(investigadorExterno.getTelefono())) {
			verifica = false;
			mensajeErrorInv += "- Por favor ingrese el número de teléfono del investigador.";
		} else {
			if (investigadorExterno.getTelefono().length() < 7 || investigadorExterno.getTelefono().length() > 20) {
				verifica = false;
				mensajeErrorInv += "- El número de teléfono del investigador debe contener entre 7 y 20 dígitos.";
			}
		}
		if (!tipoVinculacionId.equals(TipoInvestigador.JOVEN_INVESTIGADOR_POSGRADO)
				&& !tipoVinculacionId.equals(TipoInvestigador.JOVEN_INVESTIGADOR_EGRESADO)) {
			if (esCadenaVacia(institucionSeleccionada)) {
				verifica = false;
				mensajeErrorInv += "- Por favor seleccione la institución del investigador.";
			}
		}

		if (esCadenaVacia(areaCienciaInv)) {
			verifica = false;
			mensajeErrorInv += "- Por favor seleccione la área de la ciencia del investigador.";
		}

		if (esCadenaVacia(subAreaCienciaInv)) {
			verifica = false;
			mensajeErrorInv += "- Por favor seleccione la sub-área de la ciencia del investigador.";
		}

		if (tipoVinculacionId.equals(TipoInvestigador.JOVEN_INVESTIGADOR_POSGRADO)
				|| tipoVinculacionId.equals(TipoInvestigador.JOVEN_INVESTIGADOR_EGRESADO)) {
			if (esCadenaVacia(investigadorExterno.getTelefono())) {
				verifica = false;
				mensajeErrorInv += "- Por favor verificar el télefono fijo.";
			}
			if (esCadenaVacia(investigadorExterno.getNacionalidad())) {
				verifica = false;
				mensajeErrorInv += "- Por favor verificar la nacionalidad.";
			}
			if (investigadorExterno.getFechaNacimiento() == null) {
				verifica = false;
				mensajeErrorInv += "- Por favor verificar la fecha de nacimiento.";
			} else {
				// int ed =
				// calcularEdad(investigadorExterno.getFechaNacimiento());
				// int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				// if (ed >= 29) {
				// mensajeError("- Tenga en cuenta que el joven investigador
				// debe tener 28 años a 31 de Diciembre del " + currentYear);
				// }
			}
			if (esCadenaVacia(investigadorExterno.getProfesion())) {
				verifica = false;
				mensajeErrorInv += "- Por favor verificar el título de pregrado.";
			}
			if (investigadorExterno.getFechaTituloPregrado() == null) {
				verifica = false;
				mensajeErrorInv += "- Por favor verificar la fecha del título de pregrado.";
			}

			// else if
			// (!investigadorExterno.validarFechaAnios(investigadorExterno.getFechaTituloPregrado(),
			// 3)) {
			// verifica = false;
			// mensajeErrorInv += "- La fecha de grado no debe superar tres años
			// de antigüedad.";
			// }

			if (esCadenaVacia(investigadorExterno.getPromedioPregrado())) {
				verifica = false;
				mensajeErrorInv += "- Por favor verificar el promedio de pregrado.";
			} else {
				// try {
				// float prom =
				// Float.parseFloat(investigadorExterno.getPromedioPregrado());
				// if (prom < 3.8f || prom > 5f) {
				// verifica = false;
				// mensajeErrorInv += "- El valor del promedio debe ser mínimo
				// 3.8 y máximo 5.0.";
				// }
				// } catch (Exception e) {
				// verifica = false;
				// mensajeErrorInv += "- El valor del promedio debe ser
				// numérico.";
				// }
			}
			if (esCadenaVacia(investigadorExterno.getResumenHojaDeVida())) {
				verifica = false;
				mensajeErrorInv += "- Por favor ingrese la universidad en la cual realizó los estudios de pregrado.";
			}
			if (esCadenaVacia(investigadorExterno.getFacultadPregrado())) {
				verifica = false;
				mensajeErrorInv += "- Por favor ingrese la acultad en la cual realizó el pregrado.";
			}
		}
		if (!verifica) {
			mensajeError(buscarPer2, mensajeErrorInv);
		}

		return verifica;
	}

	/**
	 * Metodo general para agregar integrantes.
	 */
	public void agregarParticipante() {
		// Se valida tipo de vinculación.
		if (esCadenaVacia(tipoVinculacionId)) {
			mensajeError(buscarPer2, "Por favor seleccione un tipo de vinculación.");
			return;
		}
		if (tipoDocumentoCoInvEquipo.getId().equals("")) {
			mensajeError(buscarPer2, "Por favor ingrese el tipo de documento del participante.");
			return;
		}
		if (esCadenaVacia(documentoCoinvEquipo)) {
			mensajeError(buscarPer2, "Por favor ingrese el número de documento del participante.");
			return;
		}
		if (documentoCoinvEquipo.contains(".")) {
			mensajeError(buscarPer2, "Por favor ingrese el número de documento del participante sin puntos.");
			return;
		}
		String inputValue = String.valueOf(horasParticipante); // Convert double to string
	    if(!inputValue.matches("\\d{1,2}(\\.\\d{0,1})?")){
	    	mensajeError(horasCoinv2,
	    			"La dedicación del participante debe tener máximo dos digitos para la parte entera y un dígito para la parte decimal y debe estar separado por punto (.) Ej. 99.5");
	    	return;
	    }

		if (horasParticipante <= 0 || horasParticipante >= 100) {
			mensajeError(horasCoinv2,
					"Por favor verifique la información de las Horas semanales de dedicación (mayor a 0).");
			return;
		}
		agregarParticipanteGeneral();
		setAsignarGrupo(false);
		this.grupo = "";
	}

	/**
	 * Metodo generico con el que se agregan los tipos de vinculación normales.
	 */
	private void agregarParticipanteGeneral() {

		if (esRepetidoEquipoConDatos()) {
			return;
		}

		if (convocatoriaActual.isMostrarActividadesInvestigador() && esCadenaVacia(funcionInvestigador)) {
			mensajeError(buscarPer2, "Por favor ingrese las funciones del investigador.");
			return;
		}

		if (esConvSUE2017) {
			if ("DUCMC".equals(tipoVinculacionId) || "DUDFJC".equals(tipoVinculacionId)
					|| "DUMNG".equals(tipoVinculacionId) || "DUPN".equals(tipoVinculacionId)) {
				if (validarAgregadoEquipoSUE(proyectoActual.getListaInvestigadoresProyectoConDatos())) {
					mensajeError(buscarPer2, "No es permitido agregar más de un profesor director.");
					return;
				}
			}
		}

		if (mostrarDependencia && esCadenaVacia(investigadorExterno.getFacultadPregrado())) {
			if (TipoInvestigador.JOVEN_INVESTIGADOR_POSGRADO.equals(tipoVinculacionId)
					|| TipoInvestigador.JOVEN_INVESTIGADOR_EGRESADO.equals(tipoVinculacionId)) {
				mensajeError(buscarPer2,
						"Por favor ingrese la Facultad en la cual realizó el pregrado del investigador.");
			} else {
				mensajeError(buscarPer2, "Por favor ingrese la dependencia a la que esta asociada al investigador.");
			}
			return;
		}

		if (mostrarProgramaAcademico && esCadenaVacia(investigadorExterno.getProfesion())) {
			if (TipoInvestigador.JOVEN_INVESTIGADOR_POSGRADO.equals(tipoVinculacionId)
					|| TipoInvestigador.JOVEN_INVESTIGADOR_EGRESADO.equals(tipoVinculacionId)) {
				mensajeError(buscarPer2, "Por favor ingrese el título pregrado obtenido.");
			} else {
				mensajeError(buscarPer2, "Por favor ingrese el programa académico.");
			}
			return;
		}

		TipoInvestigador tipoInvestigador = buscarTipoInvestigador(tipoVinculacionId, listaTipoVinculacion);

		if (tipoInvestigador != null && !esCadenaVacia(tipoInvestigador.getTipo()) && validarTiempoParticipantes()) {
			asignarGrupo = false;
			if (esOtraVinculacion) {
				agregarExterno(tipoInvestigador);
			} else if (TipoVinculacion.TIPO_FUNCIONARIO.equals(tipoInvestigador.getTipo())
					|| tipoInvestigador.getTipo().equals("D")) {
				agregarFuncionario(tipoInvestigador);
			} else if (TipoVinculacion.TIPO_ESTUDIANTE.equals(tipoInvestigador.getTipo())) {
				agregarEstudiante(tipoInvestigador);
			}
		}

		calcularValorDocente();

	}

	/**
	 * Se valida que la informacion ingresada para los participantes este correcta.
	 * 
	 * @return true, if successful
	 */
	public boolean validarTiempoParticipantes() {
		boolean ret = true;

		Double duracionTotalM = (proyectoActual.getDuracion() != null && proyectoActual.getDuracion() > 0)
				? proyectoActual.getDuracion()
				: 0.0;
		if (duracionTotalM <= 0) {
			ret = false;
			mensajeError(buscarPer2, "Por favor ingrese primero la duración del proyecto.");
		}

		String sDura = String.valueOf(proyectoActual.getDuracion());
		if (sDura.length() > 2 || proyectoActual.getDuracion() < 0) {
			ret = false;
			mensajeError(buscarPer2, "La duración del proyecto debe ser de máximo 2 dígitos y mayor a 0");
		}

		if (horasParticipante <= 0 || horasParticipante >= 100) {
			ret = false;
			mensajeError(buscarPer2,
					"Tiempo de dedicación al proyecto (Horas semanales) del participante debe ser mayor a 0 y menor a 100");
		}

		if (duracionTotalM != 0) {
			if (tiempoTotalParticipante > proyectoActual.getDuracion()
					|| String.valueOf(tiempoTotalParticipante).length() > 2 || tiempoTotalParticipante <= 0) {
				ret = false;
				mensajeError(horasCoinv3,
						"Tiempo total de dedicación al proyecto (meses) del participante debe ser mayor a 0, máximo de dos dígitos y no debe ser superior al total del proyecto.");
			}

			return ret;
		} else {
			ret = false;
			mensajeError(horasCoinv3,
					"El Tiempo total de dedicación al proyecto (meses) del participante no debe ser mayor al tiempo de duración del proyecto.");
			return ret;
		}

	}

	/**
	 * Guardar ficha minima proyecto.
	 * 
	 * @param guardarParcialmente the guardar parcialmente
	 * @param esFicha             the es ficha
	 * @return true, if successful
	 */
	protected boolean guardarFichaMinimaProyecto(boolean guardarParcialmente, boolean esFicha) {

		// Se recortan los campos que pueden sobrepasar lo permitido en base de
		// datos.
		proyectoActual.setResumen(controlTamanoCadena(proyectoActual.getResumen(), 4000));
		proyectoActual.setMarcoTeorico(controlTamanoCadena(proyectoActual.getMarcoTeorico(), 4000));
		proyectoActual.setObjetivoGeneral(controlTamanoCadena(proyectoActual.getObjetivoGeneral(), 4000));
		proyectoActual.setNombre(controlTamanoCadena(proyectoActual.getNombre(), 1000));
		proyectoActual.setDependenciaPresentacion(servicioGeneral.cargaDependencia(dependenciaPresProyecto));

		// Se valida que se hayan ingresado todos los datos para poder cerrar la
		// ficha
		boolean camposValidos = true;
		if (!guardarParcialmente && !validarDatosMinimosFinalizarEdicion(esFicha)) {
			camposValidos = false;
		}

		if (mostrarMenuFormulario) {
			if (this.convocatoriaActual != null && this.convocatoriaActual.getPadre().getTipoProyecto() != null) {
				proyectoActual.setTipoActividad(this.convocatoriaActual.getPadre().getTipoProyecto());
			} else {
				proyectoActual.setTipoActividad("FM_PINV");
			}

			// proyectoActual.setRolUniversidad("ROL_GESTOR");
			// proyectoActual.setMecanismoParticipacion("MEC_NO_APLICA");
		}

		if ("PM".equals(convocatoriaActual.getTipo().getId()) && proyectoActual.getOpcionesPermisoMarco() == null
				|| (proyectoActual.getOpcionesPermisoMarco() != null
						&& proyectoActual.getOpcionesPermisoMarco().length() == 0)) {
			proyectoActual.setOpcionesPermisoMarco("NNNN");
		}

		// Creador
		Persona persona = getPersonaActual();
		if (persona != null && proyectoActual != null) {
			proyectoActual.setCreadorDocumento(persona.getId().getTipoDocumento());
			proyectoActual.setCreadorId(persona.getId().getDocumento());
		}

		// Unidad ejecutora
		if (!esCadenaVacia(unidadEjecutora) && !"Seleccione una opción".equals(unidadEjecutora)
				&& !"Ninguno".equals(unidadEjecutora)) {
			proyectoActual.setLugar(unidadEjecutora);
		} else {
			proyectoActual.setLugar("");
		}

		// Totales
		if (valorFinanciadoInvestig != null && valorFinanciadoInvestig >= 0) {
			proyectoActual.setValorSolicitado(valorFinanciadoInvestig);
			proyectoActual.setValorTotal(valorTotalTotalProyecto);
		}

		// Área y subarea de la ciencia principal.
		if (!esCadenaVacia(subAreaCiencia)) {
			List<DominioDetalle> listaDomDetUno = servicioGeneral.obtenerObjetos(DominioDetalle.class,
					"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
							+ DOMINIO_SUB_AREA_CIENCIA + "' and dd.identificador.tipo = '" + subAreaCiencia + "'");
			DominioDetalle arUno = (DominioDetalle) listaDomDetUno.get(0);
			proyectoActual.setAreaPrimaria(arUno);
		} else {
			proyectoActual.setAreaPrimaria(null);
		}

		// Objetivo de desarrollo sostenible principal
		if (!esCadenaVacia(objetivoDesarrolloSosteniblePrimario)) {
			// Objetivos de desarrollo sostenible
			List<DominioDetalle> listaDomDet = servicioGeneral.obtenerObjetos(DominioDetalle.class,
					"select e from DominioDetalle e, Dominio d where e.identificador.tipo = '"
							+ objetivoDesarrolloSosteniblePrimario + "' and e.identificador.id = d.id and d.tipo = '"
							+ DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE + "'");
			DominioDetalle dd = obtenerDominioDetalleLista(objetivoDesarrolloSosteniblePrimario, listaDomDet);
			proyectoActual.setObjetivoDesarrolloSosteniblePrincipal(dd);
		} else {
			proyectoActual.setObjetivoDesarrolloSosteniblePrincipal(null);
		}

		// Se limpian las fuentes de los gastos que no tienen los campos en
		// cero.
		if (!esListaVacia(proyectoActual.getListaFinanciones())) {
			Iterator<Financiacion> i = proyectoActual.getFinanciaciones().iterator();
			while (i.hasNext()) {
				Financiacion financiacionTemporal = i.next();
				financiacionTemporal.limpiarGastosCeroListaTemporal();
			}
		}

		// Se guarda la fase del proyecto.
		if ((proyectoActual.getFase() == null || proyectoActual.getFase() == 0) && !guardarParcialmente) {
			if (camposValidos) {
				proyectoActual.setFase(1);
			} else {
				proyectoActual.setFase(0);
			}
		} else if (proyectoActual.getFase() == null && guardarParcialmente) {
			proyectoActual.setFase(0);
		}

		if (esProyectoTesisPosgrado) {
			proyectoActual.setTipoActividad("FM_PT");
		}

		if (camposValidos) {
			try {
				boolean proyectoNuevo = proyectoActual.getId() == null;
				servicioProyecto.ingresarProyecto(proyectoActual);

				boolean error = false;
				if (proyectoActual.getId() != null) {
					if (proyectoNuevo) {
						Proyecto proyectoDisco = servicioProyecto.obtenerProyecto(proyectoActual.getId(),
								ProyectoDAOHibernate.DATOS_BASICOS);
						if (proyectoDisco == null) {
							error = true;
							proyectoActual.setId(null);
						}
					}
					if (!error) {
						if (this.proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO)) {
							generarHistoricoProyecto();
							mensajeInfo("Su proyecto ha sido registrado correctamente con el código: "
									+ proyectoActual.getId()
									+ ".  En caso de requerir un aval para la aprobación de su proyecto (no aplica para convocatorias internas). Por favor siga la siguiente ruta:  'inicio->avales->solicitar aval y seleccione el tipo de aval necesario.'");
						} else {
							if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()))
								mensajeInfo("Su proyecto ha sido registrado correctamente con el código: " + proyectoActual.getId() + ". Debe comunicarse con el director del proyecto para que realice revisión y guardado final o envío a la convocatoria.");
							else
								mensajeInfo("Su proyecto ha sido registrado correctamente con el código: " + proyectoActual.getId() + ".");
						}
						if (proyectoNuevo) {
							ProyectoVista proyectoVista = new ProyectoVista();
							proyectoVista.setId(proyectoActual.getId());
							sesion.setAttribute("proyectoFichaMinina", proyectoVista);
							if (((Convocatoria) proyectoActual.getModalidad()).getEsParaSemilleros()) {
								for (Semillero s : proyectoActual.getSemilleros()) {
									SemilleroProyecto sp = new SemilleroProyecto();
									sp.setSemillero(s);
									sp.setProyecto(proyectoActual);
									s.getProyectos().add(sp);
									servicioGeneral.guardarObjeto(s);
								}
							}
						}

						// Se envia correo con contraseña a los nuevos usuarios
						if (!esListaVacia(proyectoActual.getListaInvestigadoresProyecto())) {
							enviarCorreoUsuarioContrasenia(proyectoActual.getListaInvestigadoresProyecto());
						}
					}
				} else {
					error = true;
				}
				if (error) {
					mensajeError("Su proyecto no ha sido guardado.");
				}

			} catch (Exception e) {
				e.printStackTrace();
				mensajeError("Excepcion de codigo: " + e.toString());
				mensajeError("Hora de Excepcion: " + getToday().toString());
				mensajeError("Por favor genere una captura de pantalla y enviela a hermes@unal.edu.co");
				camposValidos = false;
			}

		} else {
			camposValidos = false;
			mensajeInfo("Su proyecto NO ha sido registrado. Por favor revisar la información ingresada. ");
		}
		return camposValidos;
	}

	/**
	 * Metodo para eliminar una area y agregarla al listado de areas a eliminar en
	 * el boton de guardado.
	 */
	public void eliminarArea() {
		listaAreasTematicas.remove(areaSeleccionada);
		proyectoActual.eliminarAreaTematica(areaSeleccionada.getAreaTematicaProyecto());
	}

	/**
	 * Eliminar dependencia.
	 */
	public void eliminarDependencia() {
		proyectoActual.borrarDependencia(dependenciaAreaResponsabilidadSeleccionada);
	}

	/**
	 * Eliminar dependencia.
	 */
	public void eliminarDependenciaAportante() {
		proyectoActual.borrarDependenciaAportante(dependenciaAportanteSeleccionada);
	}

	/**
	 * Consulta grupos de investigación a los que pertenece el investigador
	 */
	public void cargarGruposInvestigacion() {
		gruposInvestigacionItem = new ArrayList<SelectItem>();
		gruposInvestigacionItem.clear();
		List<Grupo> grupos = servicioGeneral.obtenerObjetosLimitado(Grupo.class,
				"select #id g.id, #nombre g.nombre from Grupo g, " + "InvestigadorGrupo ig where "
						+ " ig.investigador.id.tipoDocumento = '" + tipoDocumentoCoInvEquipo.getId()
						+ "' and ig.investigador.id.documento = '" + documentoCoinvEquipo
						+ "' and (g.estadoGrupo.id in ('S','P','A')) " + "and g.id = ig.grupo.id");

		if (!esListaVacia(grupos)) {
			for (int i = 0; i < grupos.size(); i++) {
				Grupo grupo = (Grupo) grupos.get(i);
				gruposInvestigacionItem.add(new SelectItem(grupo.getId(), grupo.getNombre()));
			}
		}
	}

	/**
	 * Gets the ciudad item list.
	 * 
	 * @return the ciudad item list
	 */
	public List<SelectItem> getCiudadItemList() {
		return ciudadItemList;
	}

	/**
	 * Gets the convocatoria actual.
	 * 
	 * @return the convocatoria actual
	 */
	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	/**
	 * Checks if is mostrar menu formulario.
	 * 
	 * @return true, if is mostrar menu formulario
	 */
	public boolean isMostrarMenuFormulario() {
		return mostrarMenuFormulario;
	}

	/**
	 * Checks if is es interno.
	 * 
	 * @return true, if is es interno
	 */
	public boolean isEsInterno() {
		return esInterno;
	}

	/**
	 * Checks if is es ficha externa.
	 * 
	 * @return true, if is es ficha externa
	 */
	public boolean isEsFichaExterna() {
		return esFichaExterna;
	}

	/**
	 * Checks if is mostrar descripcion problema.
	 * 
	 * @return the mostrarDescripcionProblema
	 */
	public boolean isMostrarDescripcionProblema() {
		return mostrarDescripcionProblema;
	}

	/**
	 * Gets the es proyecto laboratorios.
	 * 
	 * @return the es proyecto laboratorios
	 */
	public boolean isEsProyectoLaboratorios() {
		return esProyectoLaboratorios;
	}

	/**
	 * Checks if is mostrar biodiversidad.
	 * 
	 * @return true, if is mostrar biodiversidad
	 */
	public boolean isMostrarBiodiversidad() {
		return mostrarBiodiversidad;
	}

	public boolean isMostrarLaboratorios() {
		return mostrarLaboratorios;
	}

	public void setMostrarLaboratorios(boolean mostrarLaboratorios) {
		this.mostrarLaboratorios = mostrarLaboratorios;
	}

	/**
	 * Checks if is es conv proyectos 2016 2018.
	 * 
	 * @return true, if is es conv proyectos 2016 2018
	 */
	public boolean isEsConvProyectos2016_2018() {
		return esConvProyectos2016_2018;
	}

	/**
	 * Checks if is es tiempo volver.
	 * 
	 * @return true, if is es tiempo volver
	 */
	public boolean isEsTiempoVolver() {
		return esTiempoVolver;
	}

	/**
	 * Checks if is mostrar objetivos especificos.
	 * 
	 * @return true, if is mostrar objetivos especificos
	 */
	public boolean isMostrarObjetivosEspecificos() {
		return mostrarObjetivosEspecificos;
	}

	/**
	 * Checks if is mostrar proyecto asociado.
	 * 
	 * @return the mostrarProyectoAsociado
	 */
	public boolean isMostrarProyectoAsociado() {
		return mostrarProyectoAsociado;
	}

	/**
	 * Checks if is ver ficha min JI 2015.
	 * 
	 * @return true, if is ver ficha min JI 2015
	 */
	public boolean isVerFichaMinJI2015() {
		return verFichaMinJI2015;
	}

	/**
	 * Gets the tipos fuente item.
	 * 
	 * @return the tipos fuente item
	 */
	public SelectItem[] getTiposFuenteItem() {
		return tiposFuenteItem;
	}

	/**
	 * Gets the categoria items ppal.
	 * 
	 * @return the categoria items ppal
	 */
	public SelectItem[] getCategoriaItemsPpal() {
		return categoriaItemsPpal;
	}

	/**
	 * Gets the categoria items ECP.
	 * 
	 * @return the categoria items ECP
	 */
	public SelectItem[] getCategoriaItemsECP() {
		return categoriaItemsECP;
	}

	/**
	 * Gets the si tipos proy labs.
	 * 
	 * @return the si tipos proy labs
	 */
	public SelectItem[] getSiTiposProyLabs() {
		return siTiposProyLabs;
	}

	/**
	 * Gets the si subtipos proy labs.
	 * 
	 * @return the si subtipos proy labs
	 */
	public SelectItem[] getSiSubtiposProyLabs() {
		return siSubtiposProyLabs;
	}

	/**
	 * Gets the rol universidad items.
	 * 
	 * @return the rol universidad items
	 */
	public SelectItem[] getRolUniversidadItems() {
		return rolUniversidadItems;
	}

	/**
	 * Gets the mecanismo solicitud items.
	 * 
	 * @return the mecanismo solicitud items
	 */
	public SelectItem[] getMecanismoSolicitudItems() {
		return mecanismoSolicitudItems;
	}

	/**
	 * Gets the obj socioeconomico items.
	 * 
	 * @return the obj socioeconomico items
	 */
	public SelectItem[] getObjSocioeconomicoItems() {
		return objSocioeconomicoItems;
	}

	/**
	 * Gets the area ciencia items.
	 * 
	 * @return the area ciencia items
	 */
	public SelectItem[] getAreaCienciaItems() {
		return areaCienciaItems;
	}

	/**
	 * Gets the sub area ciencia items.
	 * 
	 * @return the sub area ciencia items
	 */
	public SelectItem[] getSubAreaCienciaItems() {
		return subAreaCienciaItems;
	}

	/**
	 * Gets the sub area ciencia.
	 * 
	 * @return the sub area ciencia
	 */
	public String getSubAreaCiencia() {
		return subAreaCiencia;
	}

	/**
	 * Sets the sub area ciencia.
	 * 
	 * @param subAreaCiencia the new sub area ciencia
	 */
	public void setSubAreaCiencia(String subAreaCiencia) {
		this.subAreaCiencia = subAreaCiencia;
	}

	/**
	 * Gets the area ciencia.
	 * 
	 * @return the area ciencia
	 */
	public String getAreaCiencia() {
		return areaCiencia;
	}

	/**
	 * Sets the area ciencia.
	 * 
	 * @param areaCiencia the new area ciencia
	 */
	public void setAreaCiencia(String areaCiencia) {
		this.areaCiencia = areaCiencia;
	}

	/**
	 * Gets the tipos activos PI item.
	 * 
	 * @return the tipos activos PI item
	 */
	public SelectItem[] getTiposActivosPIItem() {
		return tiposActivosPIItem;
	}

	/**
	 * Gets the sede item.
	 * 
	 * @return the sede item
	 */
	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	/**
	 * Gets the sede sel.
	 * 
	 * @return the sede sel
	 */
	public String getSedeSel() {
		return sedeSel;
	}

	/**
	 * Sets the sede sel.
	 * 
	 * @param sedeSel the new sede sel
	 */
	public void setSedeSel(String sedeSel) {
		this.sedeSel = sedeSel;
	}

	/**
	 * Checks if is mostrar facultades.
	 * 
	 * @return true, if is mostrar facultades
	 */
	public boolean isMostrarFacultades() {
		return mostrarFacultades;
	}

	/**
	 * Gets the dependencia item.
	 * 
	 * @return the dependencia item
	 */
	public List<SelectItem> getDependenciaItem() {
		return dependenciaItem;
	}

	/**
	 * Gets the tipo documento item.
	 * 
	 * @return the tipo documento item
	 */
	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public List<SelectItem> getObjetivosItem() {
		return objetivosItem;
	}

	public void setObjetivosItem(List<SelectItem> objetivosItem) {
		this.objetivosItem = objetivosItem;
	}

	/**
	 * Checks if is mostrar opcion tiempo dedicacion formulacion.
	 * 
	 * @return true, if is mostrar opcion tiempo dedicacion formulacion
	 */
	public boolean isMostrarOpcionTiempoDedicacionFormulacion() {
		return mostrarOpcionTiempoDedicacionFormulacion;
	}

	/**
	 * Gets the politica.
	 * 
	 * @return the politica
	 */
	public String getPolitica() {
		return politica;
	}

	/**
	 * Sets the politica.
	 * 
	 * @param politica the new politica
	 */
	public void setPolitica(String politica) {
		this.politica = politica;
	}

	/**
	 * Gets the lineas accion items.
	 * 
	 * @return the lineas accion items
	 */
	public SelectItem[] getLineasAccionItems() {
		return lineasAccionItems;
	}

	/**
	 * Gets the programa items.
	 * 
	 * @return the programa items
	 */
	public SelectItem[] getProgramaItems() {
		return programaItems;
	}

	/**
	 * Gets the dependencia item completa.
	 * 
	 * @return the dependencia item completa
	 */
	public List<SelectItem> getDependenciaItemCompleta() {
		return dependenciaItemCompleta;
	}

	/**
	 * Gets the producto nivel 3 item.
	 * 
	 * @return the producto nivel 3 item
	 */
	public SelectItem[] getProductoNivel3Item() {
		return productoNivel3Item;
	}

	/**
	 * Gets the fuentes internas item.
	 * 
	 * @return the fuentes internas item
	 */
	public SelectItem[] getFuentesInternasItem() {
		return fuentesInternasItem;
	}

	/**
	 * Gets the facultad sel.
	 * 
	 * @return the facultad sel
	 */
	public String getFacultadSel() {
		return facultadSel;
	}

	/**
	 * Sets the facultad sel.
	 * 
	 * @param facultadSel the new facultad sel
	 */
	public void setFacultadSel(String facultadSel) {
		this.facultadSel = facultadSel;
	}

	/**
	 * Gets the dependencia proyecto.
	 * 
	 * @return the dependencia proyecto
	 */
	public String getDependenciaProyecto() {
		return dependenciaProyecto;
	}

	/**
	 * Sets the dependencia proyecto.
	 * 
	 * @param dependenciaProyecto the new dependencia proyecto
	 */
	public void setDependenciaProyecto(String dependenciaProyecto) {
		this.dependenciaProyecto = dependenciaProyecto;
	}

	public String getMetaObjetivoSelectValue() {
		return metaObjetivoSelectValue;
	}

	public void setMetaObjetivoSelectValue(String metaObjetivoSelectValue) {
		this.metaObjetivoSelectValue = metaObjetivoSelectValue;
	}

	/**
	 * Gets the sub area ciencia sec items.
	 * 
	 * @return the sub area ciencia sec items
	 */
	public SelectItem[] getSubAreaCienciaSecItems() {
		return subAreaCienciaSecItems;
	}

	/**
	 * Gets the facultad item.
	 * 
	 * @return the facultad item
	 */
	public List<SelectItem> getFacultadItem() {
		return facultadItem;
	}

	/**
	 * Gets the tipo vinculacion items.
	 * 
	 * @return the tipo vinculacion items
	 */
	public SelectItem[] getTipoVinculacionItems() {
		return tipoVinculacionItems;
	}

	/**
	 * Gets the tipo vinculacion items numero.
	 * 
	 * @return the tipo vinculacion items numero
	 */
	public SelectItem[] getTipoVinculacionItemsNumero() {
		return tipoVinculacionItemsNumero;
	}

	/**
	 * Eliminar resultado.
	 */
	public void eliminarResultado() {
		if (esProyectoLaboratorios) {
			if (proyectoActual.getListaActividadesAsociadasAResultado(resultadoTabla).isEmpty()) {
				proyectoActual.borrarResultado(resultadoTabla);
			} else
				mensajeError(uiResultado, "El resultado seleccionado se encuentra asociado a una actividad");
		} else
			proyectoActual.borrarResultado(resultadoTabla);
	}

	/**
	 * Gets the resultado tabla.
	 * 
	 * @return the resultado tabla
	 */
	public ResultadoProyecto getResultadoTabla() {
		return resultadoTabla;
	}

	/**
	 * Sets the resultado tabla.
	 * 
	 * @param resultadoTabla the new resultado tabla
	 */
	public void setResultadoTabla(ResultadoProyecto resultadoTabla) {
		this.resultadoTabla = resultadoTabla;
	}

	/**
	 * Gets the tipos rubro item.
	 * 
	 * @return the tipos rubro item
	 */
	public SelectItem[] getTiposRubroItem() {
		return tiposRubroItem;
	}

	/**
	 * Gets the descripcion gasto.
	 * 
	 * @return the descripcion gasto
	 */
	public String getDescripcionGasto() {
		return descripcionGasto;
	}

	/**
	 * Sets the descripcion gasto.
	 * 
	 * @param descripcionGasto the new descripcion gasto
	 */
	public void setDescripcionGasto(String descripcionGasto) {
		this.descripcionGasto = descripcionGasto;
	}

	/**
	 * Gets the id tipo rubro.
	 * 
	 * @return the id tipo rubro
	 */
	public Long getIdTipoRubro() {
		return idTipoRubro;
	}

	/**
	 * Sets the id tipo rubro.
	 * 
	 * @param idTipoRubro the new id tipo rubro
	 */
	public void setIdTipoRubro(Long idTipoRubro) {
		this.idTipoRubro = idTipoRubro;
	}

	/**
	 * Sets the gasto seleccionado.
	 * 
	 * @param gastoSeleccionado the new gasto seleccionado
	 */
	public void setGastoSeleccionado(Gasto gastoSeleccionado) {
		this.gastoSeleccionado = gastoSeleccionado;
	}

	/**
	 * Gets the gasto seleccionado.
	 * 
	 * @return the gasto seleccionado
	 */
	public Gasto getGastoSeleccionado() {
		return gastoSeleccionado;
	}

	/**
	 * Gets the horas participante.
	 * 
	 * @return the horas participante
	 */
	public double getHorasParticipante() {
		return horasParticipante;
	}

	/**
	 * Sets the horas participante.
	 * 
	 * @param horasParticipante the new horas participante
	 */
	public void setHorasParticipante(double horasParticipante) {
		this.horasParticipante = horasParticipante;
	}

	/**
	 * Checks if is es consulta.
	 * 
	 * @return true, if is es consulta
	 */
	public boolean isEsConsulta() {
		return esConsulta;
	}

	/**
	 * Gets the objetivo especifico.
	 * 
	 * @return the objetivo especifico
	 */
	public String getObjetivoEspecifico() {
		return objetivoEspecifico;
	}

	/**
	 * Sets the objetivo especifico.
	 * 
	 * @param objetivoEspecifico the new objetivo especifico
	 */
	public void setObjetivoEspecifico(String objetivoEspecifico) {
		this.objetivoEspecifico = objetivoEspecifico;
	}

	/**
	 * Gets the objeto especifico.
	 * 
	 * @return the objeto especifico
	 */
	public UIComponent getObjetoEspecifico() {
		return objetoEspecifico;
	}

	/**
	 * Sets the objeto especifico.
	 * 
	 * @param objetoEspecifico the new objeto especifico
	 */
	public void setObjetoEspecifico(UIComponent objetoEspecifico) {
		this.objetoEspecifico = objetoEspecifico;
	}

	/**
	 * Gets the tiempo total participante.
	 * 
	 * @return the tiempo total participante
	 */
	public Integer getTiempoTotalParticipante() {
		return tiempoTotalParticipante;
	}

	/**
	 * Sets the tiempo total participante.
	 * 
	 * @param tiempoTotalParticipante the new tiempo total participante
	 */
	public void setTiempoTotalParticipante(Integer tiempoTotalParticipante) {
		this.tiempoTotalParticipante = tiempoTotalParticipante;
	}

	/**
	 * Gets the unidad ejecutora.
	 * 
	 * @return the unidad ejecutora
	 */
	public String getUnidadEjecutora() {
		return unidadEjecutora;
	}

	/**
	 * Sets the unidad ejecutora.
	 * 
	 * @param unidadEjecutora the new unidad ejecutora
	 */
	public void setUnidadEjecutora(String unidadEjecutora) {
		this.unidadEjecutora = unidadEjecutora;
	}

	/**
	 * Gets the objetivo tabla.
	 * 
	 * @return the objetivo tabla
	 */
	public ObjetivoEspecifico getObjetivoTabla() {
		return objetivoTabla;
	}

	/**
	 * Sets the objetivo tabla.
	 * 
	 * @param objetivoTabla the new objetivo tabla
	 */
	public void setObjetivoTabla(ObjetivoEspecifico objetivoTabla) {
		this.objetivoTabla = objetivoTabla;
	}

	public MetaProyecto getMetaTabla() {
		return metaTabla;
	}

	public void setMetaTabla(MetaProyecto metaTabla) {
		this.metaTabla = metaTabla;
	}

	/**
	 * Gets the resultado.
	 * 
	 * @return the resultado
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * Sets the resultado.
	 * 
	 * @param resultado the new resultado
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	/**
	 * Gets the ui resultado.
	 * 
	 * @return the ui resultado
	 */
	public UIComponent getUiResultado() {
		return uiResultado;
	}

	/**
	 * Sets the ui resultado.
	 * 
	 * @param uiResultado the new ui resultado
	 */
	public void setUiResultado(UIComponent uiResultado) {
		this.uiResultado = uiResultado;
	}

	/**
	 * Gets the producto nivel 3.
	 * 
	 * @return the producto nivel 3
	 */
	public String getProductoNivel3() {
		return productoNivel3;
	}

	/**
	 * Sets the producto nivel 3.
	 * 
	 * @param productoNivel3 the new producto nivel 3
	 */
	public void setProductoNivel3(String productoNivel3) {
		this.productoNivel3 = productoNivel3;
	}

	/**
	 * Gets the cantidad.
	 * 
	 * @return the cantidad
	 */
	public String getCantidad() {
		return cantidad;
	}

	/**
	 * Sets the cantidad.
	 * 
	 * @param cantidad the new cantidad
	 */
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Gets the otro tipo.
	 * 
	 * @return the otro tipo
	 */
	public String getOtroTipo() {
		return otroTipo;
	}

	/**
	 * Sets the otro tipo.
	 * 
	 * @param otroTipo the new otro tipo
	 */
	public void setOtroTipo(String otroTipo) {
		this.otroTipo = otroTipo;
	}

	/**
	 * Gets the ui cantidad.
	 * 
	 * @return the ui cantidad
	 */
	public UIComponent getUiCantidad() {
		return uiCantidad;
	}

	/**
	 * Sets the ui cantidad.
	 * 
	 * @param uiCantidad the new ui cantidad
	 */
	public void setUiCantidad(UIComponent uiCantidad) {
		this.uiCantidad = uiCantidad;
	}

	/**
	 * Gets the ui btn prod.
	 * 
	 * @return the ui btn prod
	 */
	public UIComponent getUiBtnProd() {
		return uiBtnProd;
	}

	/**
	 * Sets the ui btn prod.
	 * 
	 * @param uiBtnProd the new ui btn prod
	 */
	public void setUiBtnProd(UIComponent uiBtnProd) {
		this.uiBtnProd = uiBtnProd;
	}

	/**
	 * Gets the producto seleccionado.
	 * 
	 * @return the producto seleccionado
	 */
	public ProyectoProducto getProductoSeleccionado() {
		return productoSeleccionado;
	}

	/**
	 * Sets the producto seleccionado.
	 * 
	 * @param productoSeleccionado the new producto seleccionado
	 */
	public void setProductoSeleccionado(ProyectoProducto productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}
	/**
	 * Gets the institucion nombre.
	 * 
	 * @return the institucion nombre
	 */
	public String getInstitucionNombre() {
		return institucionNombre;
	}

	/**
	 * Sets the institucion nombre.
	 * 
	 * @param insitucionNombre the new institucion nombre
	 */
	public void setInstitucionNombre(String insitucionNombre) {
		this.institucionNombre = insitucionNombre;
	}

	/**
	 * Gets the boton agregar dependencia.
	 * 
	 * @return the boton agregar dependencia
	 */
	public UIComponent getBotonAgregarDependencia() {
		return botonAgregarDependencia;
	}

	/**
	 * Sets the boton agregar dependencia.
	 * 
	 * @param botonAgregarDependencia the new boton agregar dependencia
	 */
	public void setBotonAgregarDependencia(UIComponent botonAgregarDependencia) {
		this.botonAgregarDependencia = botonAgregarDependencia;
	}

	/**
	 * Gets the dependencia area responsabilidad seleccionada.
	 * 
	 * @return the dependencia area responsabilidad seleccionada
	 */
	public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidadSeleccionada() {
		return dependenciaAreaResponsabilidadSeleccionada;
	}

	/**
	 * Sets the dependencia area responsabilidad seleccionada.
	 * 
	 * @param dependenciaAreaResponsabilidadSeleccionada the new dependencia area
	 *                                                   responsabilidad
	 *                                                   seleccionada
	 */
	public void setDependenciaAreaResponsabilidadSeleccionada(
			DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada) {
		this.dependenciaAreaResponsabilidadSeleccionada = dependenciaAreaResponsabilidadSeleccionada;
	}

	/**
	 * Gets the area seleccionada.
	 * 
	 * @return the area seleccionada
	 */
	public AreaTematicaVista getAreaSeleccionada() {
		return areaSeleccionada;
	}

	/**
	 * Sets the area seleccionada.
	 * 
	 * @param areaSeleccionada the new area seleccionada
	 */
	public void setAreaSeleccionada(AreaTematicaVista areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	/**
	 * Gets the lista areas tematicas.
	 * 
	 * @return the lista areas tematicas
	 */
	public List<AreaTematicaVista> getListaAreasTematicas() {
		return listaAreasTematicas;
	}

	/**
	 * Sets the lista areas tematicas.
	 * 
	 * @param listaAreasTematicas the new lista areas tematicas
	 */
	public void setListaAreasTematicas(List<AreaTematicaVista> listaAreasTematicas) {
		this.listaAreasTematicas = listaAreasTematicas;
	}

	/**
	 * Gets the investigador externo.
	 * 
	 * @return the investigador externo
	 */
	public Persona getInvestigadorExterno() {
		return investigadorExterno;
	}

	/**
	 * Sets the investigador externo.
	 * 
	 * @param investigadorExterno the new investigador externo
	 */
	public void setInvestigadorExterno(InvestigadorExterno investigadorExterno) {
		this.investigadorExterno = investigadorExterno;
	}

	/**
	 * Gets the documento coinv.
	 * 
	 * @return the documento coinv
	 */
	public String getDocumentoCoinv() {
		return documentoCoinv;
	}

	/**
	 * Sets the documento coinv.
	 * 
	 * @param documentoCoinv the new documento coinv
	 */
	public void setDocumentoCoinv(String documentoCoinv) {
		this.documentoCoinv = documentoCoinv;
	}

	/**
	 * Gets the tipo documento co inv.
	 * 
	 * @return the tipo documento co inv
	 */
	public TipoDocumento getTipoDocumentoCoInv() {
		return tipoDocumentoCoInv;
	}

	/**
	 * Sets the tipo documento co inv.
	 * 
	 * @param tipoDocumentoCoInv the new tipo documento co inv
	 */
	public void setTipoDocumentoCoInv(TipoDocumento tipoDocumentoCoInv) {
		this.tipoDocumentoCoInv = tipoDocumentoCoInv;
	}

	/**
	 * Gets the horas director.
	 * 
	 * @return the horas director
	 */
	public double getHorasDirector() {
		return horasDirector;
	}

	/**
	 * Sets the horas director.
	 * 
	 * @param horasDirector the new horas director
	 */
	public void setHorasDirector(double horasDirector) {
		this.horasDirector = horasDirector;
	}

	/**
	 * Gets the horas coinv.
	 * 
	 * @return the horas coinv
	 */
	public UIComponent getHorasCoinv() {
		return horasCoinv;
	}

	/**
	 * Sets the horas coinv.
	 * 
	 * @param horasCoinv the new horas coinv
	 */
	public void setHorasCoinv(UIComponent horasCoinv) {
		this.horasCoinv = horasCoinv;
	}

	/**
	 * Gets the buscar director.
	 * 
	 * @return the buscar director
	 */
	public UIComponent getBuscarDirector() {
		return buscarDirector;
	}

	/**
	 * Sets the buscar director.
	 * 
	 * @param buscarDirector the new buscar director
	 */
	public void setBuscarDirector(UIComponent buscarDirector) {
		this.buscarDirector = buscarDirector;
	}

	/**
	 * Gets the funcion director.
	 * 
	 * @return the funcion director
	 */
	public String getFuncionDirector() {
		return funcionDirector;
	}

	/**
	 * Sets the funcion director.
	 * 
	 * @param funcionDirector the new funcion director
	 */
	public void setFuncionDirector(String funcionDirector) {
		this.funcionDirector = funcionDirector;
	}

	/**
	 * Checks if is mostrar datos joven.
	 * 
	 * @return true, if is mostrar datos joven
	 */
	public boolean isMostrarDatosJoven() {
		return mostrarDatosJoven;
	}

	/**
	 * Checks if is es otra vinculacion.
	 * 
	 * @return true, if is es otra vinculacion
	 */
	public boolean isEsOtraVinculacion() {
		return esOtraVinculacion;
	}

	/**
	 * Checks if is mostrar programa academico.
	 * 
	 * @return true, if is mostrar programa academico
	 */
	public boolean isMostrarProgramaAcademico() {
		return mostrarProgramaAcademico;
	}

	/**
	 * Checks if is mostrar dependencia.
	 * 
	 * @return true, if is mostrar dependencia
	 */
	public boolean isMostrarDependencia() {
		return mostrarDependencia;
	}

	/**
	 * Gets the tipo vinculacion id.
	 * 
	 * @return the tipo vinculacion id
	 */
	public String getTipoVinculacionId() {
		return tipoVinculacionId;
	}

	/**
	 * Sets the tipo vinculacion id.
	 * 
	 * @param tipoVinculacionId the new tipo vinculacion id
	 */
	public void setTipoVinculacionId(String tipoVinculacionId) {
		this.tipoVinculacionId = tipoVinculacionId;
	}

	/**
	 * Mostrar panel conoce datos.
	 */
	public void mostrarPanelConoceDatos() {
		tipoVinculacionId = "1";
	}

	/**
	 * Checks if is mostrar opcion buscar investigador.
	 * 
	 * @return true, if is mostrar opcion buscar investigador
	 */
	public boolean isMostrarOpcionBuscarInvestigador() {
		return mostrarOpcionBuscarInvestigador;
	}

	/**
	 * Gets the funcion investigador.
	 * 
	 * @return the funcion investigador
	 */
	public String getFuncionInvestigador() {
		return funcionInvestigador;
	}

	/**
	 * Sets the funcion investigador.
	 * 
	 * @param funcionInvestigador the new funcion investigador
	 */
	public void setFuncionInvestigador(String funcionInvestigador) {
		this.funcionInvestigador = funcionInvestigador;
	}

	/**
	 * Gets the cantidad part.
	 * 
	 * @return the cantidad part
	 */
	public Long getCantidadPart() {
		return cantidadPart;
	}

	/**
	 * Sets the cantidad part.
	 * 
	 * @param cantidadPart the new cantidad part
	 */
	public void setCantidadPart(Long cantidadPart) {
		this.cantidadPart = cantidadPart;
	}

	/**
	 * Gets the lugar nacimiento.
	 * 
	 * @return the lugar nacimiento
	 */
	public String getLugarNacimiento() {
		return lugarNacimiento;
	}

	/**
	 * Sets the lugar nacimiento.
	 * 
	 * @param lugarNacimiento the new lugar nacimiento
	 */
	public void setLugarNacimiento(String lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}

	/**
	 * Gets the horas coinv 3.
	 * 
	 * @return the horas coinv 3
	 */
	public UIComponent getHorasCoinv3() {
		return horasCoinv3;
	}

	/**
	 * Sets the horas coinv 3.
	 * 
	 * @param horasCoinv3 the new horas coinv 3
	 */
	public void setHorasCoinv3(UIComponent horasCoinv3) {
		this.horasCoinv3 = horasCoinv3;
	}

	/**
	 * Gets the buscar per 2.
	 * 
	 * @return the buscar per 2
	 */
	public UIComponent getBuscarPer3() {
		return buscarPer3;
	}

	/**
	 * Sets the buscar per 2.
	 * 
	 * @param buscarPer_2 the new buscar per 2
	 */
	public void setBuscarPer3(UIComponent buscarPer_2) {
		this.buscarPer3 = buscarPer_2;
	}

	/**
	 * Gets the buscar per 2.
	 * 
	 * @return the buscar per 2
	 */
	public UIComponent getBuscarPer2() {
		return buscarPer2;
	}

	/**
	 * Sets the buscar per 2.
	 * 
	 * @param buscarPer2 the new buscar per 2
	 */
	public void setBuscarPer2(UIComponent buscarPer2) {
		this.buscarPer2 = buscarPer2;
	}

	/**
	 * Gets the boton areas tabla.
	 * 
	 * @return the boton areas tabla
	 */
	public UIComponent getBotonAreasTabla() {
		return botonAreasTabla;
	}

	/**
	 * Sets the boton areas tabla.
	 * 
	 * @param botonAreasTabla the new boton areas tabla
	 */
	public void setBotonAreasTabla(UIComponent botonAreasTabla) {
		this.botonAreasTabla = botonAreasTabla;
	}

	/**
	 * Gets the horas coinv 2.
	 * 
	 * @return the horas coinv 2
	 */
	public UIComponent getHorasCoinv2() {
		return horasCoinv2;
	}

	/**
	 * Sets the horas coinv 2.
	 * 
	 * @param horasCoinv2 the new horas coinv 2
	 */
	public void setHorasCoinv2(UIComponent horasCoinv2) {
		this.horasCoinv2 = horasCoinv2;
	}

	/**
	 * Gets the valor gasto.
	 * 
	 * @return the valor gasto
	 */
	public Long getValorGasto() {
		return valorGasto;
	}

	/**
	 * Sets the valor gasto.
	 * 
	 * @param valorGasto the new valor gasto
	 */
	public void setValorGasto(Long valorGasto) {
		this.valorGasto = valorGasto;
	}

	/**
	 * Gets the palabra clave.
	 * 
	 * @return the palabra clave
	 */
	public String getPalabraClave() {
		return palabraClave;
	}

	/**
	 * Sets the palabra clave.
	 * 
	 * @param palabraClave the new palabra clave
	 */
	public void setPalabraClave(String palabraClave) {
		this.palabraClave = palabraClave;
	}

	/**
	 * Gets the palabra clave S.
	 * 
	 * @return the palabra clave S
	 */
	public UIComponent getPalabraClaveS() {
		return palabraClaveS;
	}

	/**
	 * Sets the palabra clave S.
	 * 
	 * @param palabraClaveS the new palabra clave S
	 */
	public void setPalabraClaveS(UIComponent palabraClaveS) {
		this.palabraClaveS = palabraClaveS;
	}

	/**
	 * Gets the conoce datos.
	 * 
	 * @return the conoce datos
	 */
	public String getConoceDatos() {
		return conoceDatos;
	}

	/**
	 * Sets the conoce datos.
	 * 
	 * @param conoceDatos the new conoce datos
	 */
	public void setConoceDatos(String conoceDatos) {
		this.conoceDatos = conoceDatos;
	}

	/**
	 * Gets the tipo documento co inv equipo.
	 * 
	 * @return the tipo documento co inv equipo
	 */
	public TipoDocumento getTipoDocumentoCoInvEquipo() {
		return tipoDocumentoCoInvEquipo;
	}

	/**
	 * Sets the tipo documento co inv equipo.
	 * 
	 * @param tipoDocumentoCoInv2 the new tipo documento co inv equipo
	 */
	public void setTipoDocumentoCoInvEquipo(TipoDocumento tipoDocumentoCoInv2) {
		this.tipoDocumentoCoInvEquipo = tipoDocumentoCoInv2;
	}

	/**
	 * Gets the documento coinv equipo.
	 * 
	 * @return the documento coinv equipo
	 */
	public String getDocumentoCoinvEquipo() {
		return documentoCoinvEquipo;
	}

	/**
	 * Sets the documento coinv equipo.
	 * 
	 * @param documentoCoinvEquipo the new documento coinv equipo
	 */
	public void setDocumentoCoinvEquipo(String documentoCoinvEquipo) {
		this.documentoCoinvEquipo = documentoCoinvEquipo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.vista.proyectos.manejadorproyecto.ManejadorProyecto#
	 * cargarValoresIniciales()
	 */
	// *****Funciones propias ********
	@Override
	protected void cargarValoresIniciales() {
		/**
		 * Metodo vacio porque no se carga nada aqui.
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.vista.proyectos.manejadorproyecto.ManejadorProyecto#
	 * salir()
	 */
	@Override
	public String salir() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.vista.proyectos.manejadorproyecto.ManejadorProyecto#
	 * salirGuardar()
	 */
	@Override
	public String salirGuardar() {

		return null;
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

	public UIComponent getMetaProyecto() {
		return metaProyecto;
	}

	public void setMetaProyecto(UIComponent metaProyecto) {
		this.metaProyecto = metaProyecto;
	}

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}

	public ObjetivoEspecifico getObjetivoSeleccionado() {
		return objetivoSeleccionado;
	}

	public void setObjetivoSeleccionado(ObjetivoEspecifico objetivoSeleccionado) {
		this.objetivoSeleccionado = objetivoSeleccionado;
	}

	/**
	 * @return the mostrarResumen
	 */
	public boolean isMostrarResumen() {
		return mostrarResumen;
	}

	/**
	 * @param mostrarResumen the mostrarResumen to set
	 */
	public void setMostrarResumen(boolean mostrarResumen) {
		this.mostrarResumen = mostrarResumen;
	}

	/**
	 * @return the mostrarResultados
	 */
	public boolean isMostrarResultados() {
		return mostrarResultados;
	}

	/**
	 * @param mostrarResultados the mostrarResultados to set
	 */
	public void setMostrarResultados(boolean mostrarResultados) {
		this.mostrarResultados = mostrarResultados;
	}

	public boolean isEsConvPurdue() {
		return esConvPurdue;
	}

	public void setEsConvPurdue(boolean esConvPurdue) {
		this.esConvPurdue = esConvPurdue;
	}

	/**
	 * @return the esConvFalsBorda2017
	 */
	public boolean isEsConvFalsBorda2017() {
		return esConvFalsBorda2017;
	}

	/**
	 * @param esConvFalsBorda2017 the esConvFalsBorda2017 to set
	 */
	public void setEsConvFalsBorda2017(boolean esConvFalsBorda2017) {
		this.esConvFalsBorda2017 = esConvFalsBorda2017;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public boolean isAsignarGrupo() {
		return asignarGrupo;
	}

	public void setAsignarGrupo(boolean asignarGrupo) {
		this.asignarGrupo = asignarGrupo;
	}

	public List<SelectItem> getGruposInvestigacionItem() {
		return gruposInvestigacionItem;
	}

	public void setGruposInvestigacionItem(List<SelectItem> gruposInvestigacionItem) {
		this.gruposInvestigacionItem = gruposInvestigacionItem;
	}

	/**
	 * Gets the sub area ciencia sec.
	 * 
	 * @return the sub area ciencia sec
	 */
	public String getSubAreaCienciaSec() {
		return subAreaCienciaSec;
	}

	/**
	 * Sets the sub area ciencia sec.
	 * 
	 * @param subAreaCienciaSec the new sub area ciencia sec
	 */
	public void setSubAreaCienciaSec(String subAreaCienciaSec) {
		this.subAreaCienciaSec = subAreaCienciaSec;
	}

	/**
	 * @return the esConvSesquicente
	 */
	public boolean isEsConvSesquicente() {
		return esConvSesquicente;
	}

	/**
	 * @param esConvSesquicente the esConvSesquicente to set
	 */
	public void setEsConvSesquicente(boolean esConvSesquicente) {
		this.esConvSesquicente = esConvSesquicente;
	}

	/**
	 * @return the esConvExtSol2017
	 */
	public boolean isEsConvExtSol2017() {
		return esConvExtSol2017;
	}

	/**
	 * @param esConvExtSol2017 the esConvExtSol2017 to set
	 */
	public void setEsConvExtSol2017(boolean esConvExtSol2017) {
		this.esConvExtSol2017 = esConvExtSol2017;
	}

	/**
	 * @return the esConvCienciasAgrarias2017
	 */
	public boolean isEsConvCienciasAgrarias2017() {
		return esConvCienciasAgrarias2017;
	}

	/**
	 * @param esConvCienciasAgrarias2017 the esConvCienciasAgrarias2017 to set
	 */
	public void setEsConvCienciasAgrarias2017(boolean esConvCienciasAgrarias2017) {
		this.esConvCienciasAgrarias2017 = esConvCienciasAgrarias2017;
	}

	/**
	 * @return the esConvMedTraslacional2017
	 */
	public boolean isEsConvMedTraslacional2017() {
		return esConvMedTraslacional2017;
	}

	/**
	 * @param esConvMedTraslacional2017 the esConvMedTraslacional2017 to set
	 */
	public void setEsConvMedTraslacional2017(boolean esConvMedTraslacional2017) {
		this.esConvMedTraslacional2017 = esConvMedTraslacional2017;
	}

	/**
	 * @return the esConvArtesModA2017
	 */
	public boolean isEsConvArtesModA2017() {
		return esConvArtesModA2017;
	}

	/**
	 * @param esConvArtesModA2017 the esConvArtesModA2017 to set
	 */
	public void setEsConvArtesModA2017(boolean esConvArtesModA2017) {
		this.esConvArtesModA2017 = esConvArtesModA2017;
	}

	/**
	 * @return the esConvArtesModB2017
	 */
	public boolean isEsConvArtesModB2017() {
		return esConvArtesModB2017;
	}

	/**
	 * @param esConvArtesModB2017 the esConvArtesModB2017 to set
	 */
	public void setEsConvArtesModB2017(boolean esConvArtesModB2017) {
		this.esConvArtesModB2017 = esConvArtesModB2017;
	}

	/**
	 * @return the esConvOdontologia2017
	 */
	public boolean isEsConvOdontologia2017() {
		return esConvOdontologia2017;
	}

	/**
	 * @param esConvOdontologia2017 the esConvOdontologia2017 to set
	 */
	public void setEsConvOdontologia2017(boolean esConvOdontologia2017) {
		this.esConvOdontologia2017 = esConvOdontologia2017;
	}

	/**
	 * @return the esConvOdontologia2017_M2
	 */
	public boolean isEsConvOdontologia2017_M2() {
		return esConvOdontologia2017_M2;
	}

	/**
	 * @param esConvOdontologia2017_M2 the esConvOdontologia2017_M2 to set
	 */
	public void setEsConvOdontologia2017_M2(boolean esConvOdontologia2017_M2) {
		this.esConvOdontologia2017_M2 = esConvOdontologia2017_M2;
	}

	/**
	 * @return the esProyectoTesisPosgrado
	 */
	public boolean isEsProyectoTesisPosgrado() {
		return esProyectoTesisPosgrado;
	}

	/**
	 * @param esProyectoTesisPosgrado the esProyectoTesisPosgrado to set
	 */
	public void setEsProyectoTesisPosgrado(boolean esProyectoTesisPosgrado) {
		this.esProyectoTesisPosgrado = esProyectoTesisPosgrado;
	}

	/**
	 * @return the esConvAlianzasBog2017
	 */
	public boolean isEsConvAlianzasBog2017() {
		return esConvAlianzasBog2017;
	}

	/**
	 * @param esConvAlianzasBog2017 the esConvAlianzasBog2017 to set
	 */
	public void setEsConvAlianzasBog2017(boolean esConvAlianzasBog2017) {
		this.esConvAlianzasBog2017 = esConvAlianzasBog2017;
	}

	public boolean isEsConvSUE2017() {
		return esConvSUE2017;
	}

	public void setEsConvSUE2017(boolean esConvSUE2017) {
		this.esConvSUE2017 = esConvSUE2017;
	}

	public boolean isEsConvGrupSemArtes2022() {
		return esConvGrupSemArtes2022;
	}

	public void setEsConvGrupSemArtes2022(boolean esConvGrupSemArtes2022) {
		this.esConvGrupSemArtes2022 = esConvGrupSemArtes2022;
	}

	public String getTipoInvestigadoresConvocatoria() {
		return tipoInvestigadoresConvocatoria;
	}

	public void setTipoInvestigadoresConvocatoria(String tipoInvestigadoresConvocatoria) {
		this.tipoInvestigadoresConvocatoria = tipoInvestigadoresConvocatoria;
	}

	public boolean isEsConvCienciasAgrariasPal_1_2018() {
		return esConvCienciasAgrariasPal_1_2018;
	}

	public void setEsConvCienciasAgrariasPal_1_2018(boolean esConvCienciasAgrariasPal_1_2018) {
		this.esConvCienciasAgrariasPal_1_2018 = esConvCienciasAgrariasPal_1_2018;
	}

	public boolean isEsConvCienciasAgrariasPal_2_2018() {
		return esConvCienciasAgrariasPal_2_2018;
	}

	public void setEsConvCienciasAgrariasPal_2_2018(boolean esConvCienciasAgrariasPal_2_2018) {
		this.esConvCienciasAgrariasPal_2_2018 = esConvCienciasAgrariasPal_2_2018;
	}

	public boolean isEsConvCienciasAgrariasPal_3_2018() {
		return esConvCienciasAgrariasPal_3_2018;
	}

	public void setEsConvCienciasAgrariasPal_3_2018(boolean esConvCienciasAgrariasPal_3_2018) {
		this.esConvCienciasAgrariasPal_3_2018 = esConvCienciasAgrariasPal_3_2018;
	}

	public boolean isEsConvFortInvIngAdmonPal2018() {
		return esConvFortInvIngAdmonPal2018;
	}

	public void setEsConvFortInvIngAdmonPal2018(boolean esConvFortInvIngAdmonPal2018) {
		this.esConvFortInvIngAdmonPal2018 = esConvFortInvIngAdmonPal2018;
	}

	public boolean isEsConvDiscDerecho2018() {
		return esConvDiscDerecho2018;
	}

	public void setEsConvDiscDerecho2018(boolean esConvDiscDerecho2018) {
		this.esConvDiscDerecho2018 = esConvDiscDerecho2018;
	}

	public boolean isEsConvRepotenciacionLab2018() {
		return esConvRepotenciacionLab2018;
	}

	public void setEsConvRepotenciacionLab2018(boolean esConvRepotenciacionLab2018) {
		this.esConvRepotenciacionLab2018 = esConvRepotenciacionLab2018;
	}

	public boolean isEsConvObservatoriosDerecho2018() {
		return esConvObservatoriosDerecho2018;
	}

	public void setEsConvObservatoriosDerecho2018(boolean esConvObservatoriosDerecho2018) {
		this.esConvObservatoriosDerecho2018 = esConvObservatoriosDerecho2018;
	}

	public boolean isEsConvUnInnova2018() {
		return esConvUnInnova2018;
	}

	public void setEsConvUnInnova2018(boolean esConvUnInnova2018) {
		this.esConvUnInnova2018 = esConvUnInnova2018;
	}

	public boolean isEsConvMedLegal2018() {
		return esConvMedLegal2018;
	}

	public void setEsConvMedLegal2018(boolean esConvMedLegal2018) {
		this.esConvMedLegal2018 = esConvMedLegal2018;
	}

	public boolean isEsConvExtSol2018() {
		return esConvExtSol2018;
	}

	public void setEsConvExtSol2018(boolean esConvExtSol2018) {
		this.esConvExtSol2018 = esConvExtSol2018;
	}

	public boolean isEsConvPosgradoCienciasAgrarias2018() {
		return esConvPosgradoCienciasAgrarias2018;
	}

	public void setEsConvPosgradoCienciasAgrarias2018(boolean esConvPosgradoCienciasAgrarias2018) {
		this.esConvPosgradoCienciasAgrarias2018 = esConvPosgradoCienciasAgrarias2018;
	}

	public boolean isEsConvArtes_2018_ModB() {
		return esConvArtes_2018_ModB;
	}

	public void setEsConvArtes_2018_ModB(boolean esConvArtes_2018_ModB) {
		this.esConvArtes_2018_ModB = esConvArtes_2018_ModB;
	}

	public boolean isEsConvFalsBorda2018() {
		return esConvFalsBorda2018;
	}

	public void setEsConvFalsBorda2018(boolean esConvFalsBorda2018) {
		this.esConvFalsBorda2018 = esConvFalsBorda2018;
	}

	public boolean isEsConvSemillerosCienciasHumanas2018() {
		return esConvSemillerosCienciasHumanas2018;
	}

	public void setEsConvSemillerosCienciasHumanas2018(boolean esConvSemillerosCienciasHumanas2018) {
		this.esConvSemillerosCienciasHumanas2018 = esConvSemillerosCienciasHumanas2018;
	}

	public boolean isEsConvSemillerosDerecho2018() {
		return esConvSemillerosDerecho2018;
	}

	public void setEsConvSemillerosDerecho2018(boolean esConvSemillerosDerecho2018) {
		this.esConvSemillerosDerecho2018 = esConvSemillerosDerecho2018;
	}

	public boolean isEsConvAlianzasPalmira2018() {
		return esConvAlianzasPalmira2018;
	}

	public void setEsConvAlianzasPalmira2018(boolean esConvAlianzasPalmira2018) {
		this.esConvAlianzasPalmira2018 = esConvAlianzasPalmira2018;
	}

	public boolean isEsConvAdmonManizales2018() {
		return esConvAdmonManizales2018;
	}

	public void setEsConvAdmonManizales2018(boolean esConvAdmonManizales2018) {
		this.esConvAdmonManizales2018 = esConvAdmonManizales2018;
	}

	public boolean isEsConvocatoriaAlianzas2018() {
		return esConvocatoriaAlianzas2018;
	}

	public void setEsConvocatoriaAlianzas2018(boolean esConvocatoriaAlianzas2018) {
		this.esConvocatoriaAlianzas2018 = esConvocatoriaAlianzas2018;
	}

	public Long getValorAporteAlianza() {
		return valorAporteAlianza;
	}

	public void setValorAporteAlianza(Long valorAporteAlianza) {
		this.valorAporteAlianza = valorAporteAlianza;
	}

	public UIComponent getBotonAgregarDependenciaAlianzas() {
		return botonAgregarDependenciaAlianzas;
	}

	public void setBotonAgregarDependenciaAlianzas(UIComponent botonAgregarDependenciaAlianzas) {
		this.botonAgregarDependenciaAlianzas = botonAgregarDependenciaAlianzas;
	}

	public boolean isEsConvAdmonManizalesGruposNoRec2018() {
		return esConvAdmonManizalesGruposNoRec2018;
	}

	public void setEsConvAdmonManizalesGruposNoRec2018(boolean esConvAdmonManizalesGruposNoRec2018) {
		this.esConvAdmonManizalesGruposNoRec2018 = esConvAdmonManizalesGruposNoRec2018;
	}

	public boolean isEsConvDocNalEmp758_2018() {
		return esConvDocNalEmp758_2018;
	}

	public void setEsConvDocNalEmp758_2018(boolean esConvDocNalEmp758_2018) {
		this.esConvDocNalEmp758_2018 = esConvDocNalEmp758_2018;
	}

	public String getObjetivoDesarrolloSosteniblePrimario() {
		return objetivoDesarrolloSosteniblePrimario;
	}

	public void setObjetivoDesarrolloSosteniblePrimario(String objetivoDesarrolloSosteniblePrimario) {
		this.objetivoDesarrolloSosteniblePrimario = objetivoDesarrolloSosteniblePrimario;
	}

	public String getObjetivoDesarrolloSostenibleSecundario() {
		return objetivoDesarrolloSostenibleSecundario;
	}

	public void setObjetivoDesarrolloSostenibleSecundario(String objetivoDesarrolloSostenibleSecundario) {
		this.objetivoDesarrolloSostenibleSecundario = objetivoDesarrolloSostenibleSecundario;
	}

	public ValoresListasProyecto getObjetivoDesarrolloSostenibleSeleccionado() {
		return objetivoDesarrolloSostenibleSeleccionado;
	}

	public void setObjetivoDesarrolloSostenibleSeleccionado(
			ValoresListasProyecto objetivoDesarrolloSostenibleSeleccionado) {
		this.objetivoDesarrolloSostenibleSeleccionado = objetivoDesarrolloSostenibleSeleccionado;
	}

	public SelectItem[] getObjetivosDesarrolloSostenibleItems() {
		return objetivosDesarrolloSostenibleItems;
	}

	public void setObjetivosDesarrolloSostenibleItems(SelectItem[] objetivosDesarrolloSostenibleItems) {
		this.objetivosDesarrolloSostenibleItems = objetivosDesarrolloSostenibleItems;
	}

	public List<ValoresListasProyecto> getValoresObjetivosDesarrolloSostenible() {
		return valoresObjetivosDesarrolloSostenible;
	}

	public void setValoresObjetivosDesarrolloSostenible(
			List<ValoresListasProyecto> valoresObjetivosDesarrolloSostenible) {
		this.valoresObjetivosDesarrolloSostenible = valoresObjetivosDesarrolloSostenible;
	}

	public List<ValoresListasProyecto> getValoresObjetivosDesarrolloSostenibleBorrados() {
		return valoresObjetivosDesarrolloSostenibleBorrados;
	}

	public void setValoresObjetivosDesarrolloSostenibleBorrados(
			List<ValoresListasProyecto> valoresObjetivosDesarrolloSostenibleBorrados) {
		this.valoresObjetivosDesarrolloSostenibleBorrados = valoresObjetivosDesarrolloSostenibleBorrados;
	}

	public UIComponent getBtnObjDesSosSec() {
		return btnObjDesSosSec;
	}

	public void setBtnObjDesSosSec(UIComponent btnObjDesSosSec) {
		this.btnObjDesSosSec = btnObjDesSosSec;
	}

	public UIComponent getBotonBuscarInvestigadorExterno() {
		return botonBuscarInvestigadorExterno;
	}

	public void setBotonBuscarInvestigadorExterno(UIComponent botonBuscarInvestigadorExterno) {
		this.botonBuscarInvestigadorExterno = botonBuscarInvestigadorExterno;
	}

	public SelectItem[] getTipoFormacionItem() {
		return tipoFormacionItem;
	}

	public void setTipoFormacionItem(SelectItem[] tipoFormacionItem) {
		this.tipoFormacionItem = tipoFormacionItem;
	}

	public SelectItem[] getEstadoCivilItem() {
		return estadoCivilItem;
	}

	public void setEstadoCivilItem(SelectItem[] estadoCivilItem) {
		this.estadoCivilItem = estadoCivilItem;
	}

	public String getIdPaisNacimiento() {
		return idPaisNacimiento;
	}

	public void setIdPaisNacimiento(String idPaisNacimiento) {
		this.idPaisNacimiento = idPaisNacimiento;
	}

	public boolean isSiColombia() {
		return siColombia;
	}

	public void setSiColombia(boolean siColombia) {
		this.siColombia = siColombia;
	}

	public Departamento getDepartamentoActual() {
		return departamentoActual;
	}

	public void setDepartamentoActual(Departamento departamentoActual) {
		this.departamentoActual = departamentoActual;
	}

	public SelectItem[] getDepartamentoItem() {
		return departamentoItem;
	}

	public void setDepartamentoItem(SelectItem[] departamentoItem) {
		this.departamentoItem = departamentoItem;
	}

	public SelectItem[] getCiudadItem() {
		return ciudadItem;
	}

	public void setCiudadItem(SelectItem[] ciudadItem) {
		this.ciudadItem = ciudadItem;
	}

	public List getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List listaCiudades) {
		this.listaCiudades = listaCiudades;
	}

	public Ciudad getCiudadActual() {
		return ciudadActual;
	}

	public void setCiudadActual(Ciudad ciudadActual) {
		this.ciudadActual = ciudadActual;
	}

	public List getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public String getIdTipoFormacion() {
		return idTipoFormacion;
	}

	public void setIdTipoFormacion(String idTipoFormacion) {
		this.idTipoFormacion = idTipoFormacion;
	}

	public String getIdTipoEstadoCivil() {
		return idTipoEstadoCivil;
	}

	public void setIdTipoEstadoCivil(String idTipoEstadoCivil) {
		this.idTipoEstadoCivil = idTipoEstadoCivil;
	}

	public String getSubAreaCienciaInv() {
		return subAreaCienciaInv;
	}

	public void setSubAreaCienciaInv(String subAreaCienciaInv) {
		this.subAreaCienciaInv = subAreaCienciaInv;
	}

	public String getAreaCienciaInv() {
		return areaCienciaInv;
	}

	public void setAreaCienciaInv(String areaCienciaInv) {
		this.areaCienciaInv = areaCienciaInv;
	}

	public String getCiudadNacimientoNoCol() {
		return ciudadNacimientoNoCol;
	}

	public void setCiudadNacimientoNoCol(String ciudadNacimientoNoCol) {
		this.ciudadNacimientoNoCol = ciudadNacimientoNoCol;
	}

	public SelectItem[] getInstitucionesItem() {
		return institucionesItem;
	}

	public void setInstitucionesItem(SelectItem[] institucionesItem) {
		this.institucionesItem = institucionesItem;
	}

	public String getInstitucionSeleccionada() {
		return institucionSeleccionada;
	}

	public void setInstitucionSeleccionada(String institucionSeleccionada) {
		this.institucionSeleccionada = institucionSeleccionada;
	}

	public SelectItem[] getSubAreaCienciaItemsInv() {
		return subAreaCienciaItemsInv;
	}

	public void setSubAreaCienciaItemsInv(SelectItem[] subAreaCienciaItemsInv) {
		this.subAreaCienciaItemsInv = subAreaCienciaItemsInv;
	}

	public SelectItem[] getListaPaisesFuenteItem() {
		return listaPaisesFuenteItem;
	}

	public void setListaPaisesFuenteItem(SelectItem[] listaPaisesFuenteItem) {
		this.listaPaisesFuenteItem = listaPaisesFuenteItem;
	}

	public String getMensajeRubrosBasicos() {
		return mensajeRubrosBasicos;
	}

	public void setMensajeRubrosBasicos(String mensajeRubrosBasicos) {
		this.mensajeRubrosBasicos = mensajeRubrosBasicos;
	}

	public boolean isMostrarMensajeRubros() {
		return mostrarMensajeRubros;
	}

	public void setMostrarMensajeRubros(boolean mostrarMensajeRubros) {
		this.mostrarMensajeRubros = mostrarMensajeRubros;
	}

	public boolean isEsConvCundinamarca() {
		return esConvCundinamarca;
	}

	public void setEsConvCundinamarca(boolean esConvCundinamarca) {
		this.esConvCundinamarca = esConvCundinamarca;
	}

	public String getNombreProyectoPrograma() {
		return nombreProyectoPrograma;
	}

	public void setNombreProyectoPrograma(String nombreProyectoPrograma) {
		this.nombreProyectoPrograma = nombreProyectoPrograma;
	}

	public String getIdEntidadProyectoPrograma() {
		return idEntidadProyectoPrograma;
	}

	public void setIdEntidadProyectoPrograma(String idEntidadProyectoPrograma) {
		this.idEntidadProyectoPrograma = idEntidadProyectoPrograma;
	}

	public String getIdDependenciaEntidadProyectoPrograma() {
		return idDependenciaEntidadProyectoPrograma;
	}

	public void setIdDependenciaEntidadProyectoPrograma(String idDependenciaEntidadProyectoPrograma) {
		this.idDependenciaEntidadProyectoPrograma = idDependenciaEntidadProyectoPrograma;
	}

	public SelectItem[] getEntidadesProyectoProgramaItems() {
		return entidadesProyectoProgramaItems;
	}

	public void setEntidadesProyectoProgramaItems(SelectItem[] entidadesProyectoProgramaItems) {
		this.entidadesProyectoProgramaItems = entidadesProyectoProgramaItems;
	}

	public ProyectoPrograma getProyectoProgramaSeleccionado() {
		return proyectoProgramaSeleccionado;
	}

	public void setProyectoProgramaSeleccionado(ProyectoPrograma proyectoProgramaSeleccionado) {
		this.proyectoProgramaSeleccionado = proyectoProgramaSeleccionado;
	}

	public UIComponent getUiDependenciaProyectoPrograma() {
		return uiDependenciaProyectoPrograma;
	}

	public void setUiDependenciaProyectoPrograma(UIComponent uiDependenciaProyectoPrograma) {
		this.uiDependenciaProyectoPrograma = uiDependenciaProyectoPrograma;
	}

	public boolean isEsConvocatoriaAlianzas2019() {
		return esConvocatoriaAlianzas2019;
	}

	public void setEsConvocatoriaAlianzas2019(boolean esConvocatoriaAlianzas2019) {
		this.esConvocatoriaAlianzas2019 = esConvocatoriaAlianzas2019;
	}

	public Departamento getDepartamentoRegionImpacto() {
		return departamentoRegionImpacto;
	}

	public void setDepartamentoRegionImpacto(Departamento departamentoRegionImpacto) {
		this.departamentoRegionImpacto = departamentoRegionImpacto;
	}

	public SelectItem[] getDepartamentoItemRegionImpacto() {
		return departamentoItemRegionImpacto;
	}

	public void setDepartamentoItemRegionImpacto(SelectItem[] departamentoItemRegionImpacto) {
		this.departamentoItemRegionImpacto = departamentoItemRegionImpacto;
	}

	public SelectItem[] getCiudadItemRegionImpacto() {
		return ciudadItemRegionImpacto;
	}

	public void setCiudadItemRegionImpacto(SelectItem[] ciudadItemRegionImpacto) {
		this.ciudadItemRegionImpacto = ciudadItemRegionImpacto;
	}

	public List getListaCiudadesRegionImpacto() {
		return listaCiudadesRegionImpacto;
	}

	public void setListaCiudadesRegionImpacto(List listaCiudadesRegionImpacto) {
		this.listaCiudadesRegionImpacto = listaCiudadesRegionImpacto;
	}

	public Ciudad getCiudadActualRegionImpacto() {
		return ciudadActualRegionImpacto;
	}

	public void setCiudadActualRegionImpacto(Ciudad ciudadActualRegionImpacto) {
		this.ciudadActualRegionImpacto = ciudadActualRegionImpacto;
	}

	public List getListaDepartamentosRegionImpacto() {
		return listaDepartamentosRegionImpacto;
	}

	public void setListaDepartamentosRegionImpacto(List listaDepartamentosRegionImpacto) {
		this.listaDepartamentosRegionImpacto = listaDepartamentosRegionImpacto;
	}

	public void setListaMontosFinanciar(List<montoFinanciar> listaMontosFinanciar) {
		this.listaMontosFinanciar = listaMontosFinanciar;
	}

	public boolean isEsConvInnovaSedesPresNal() {
		return esConvInnovaSedesPresNal;
	}

	public void setEsConvInnovaSedesPresNal(boolean esConvInnovaSedesPresNal) {
		this.esConvInnovaSedesPresNal = esConvInnovaSedesPresNal;
	}

	public boolean isEsConvProyectosBog2019() {
		return esConvProyectosBog2019;
	}

	public void setEsConvProyectosBog2019(boolean esConvProyectosBog2019) {
		this.esConvProyectosBog2019 = esConvProyectosBog2019;
	}

	public Ciudad getCiudadRegionImpacto() {
		return ciudadRegionImpacto;
	}

	public void setCiudadRegionImpacto(Ciudad ciudadRegionImpacto) {
		this.ciudadRegionImpacto = ciudadRegionImpacto;
	}

	public List<montoFinanciar> getListaMontosFinanciar() {
		return listaMontosFinanciar;
	}

	public boolean isEsEntidSedePresNal() {
		return esEntidSedePresNal;
	}

	public void setEsEntidSedePresNal(boolean esEntidSedePresNal) {
		this.esEntidSedePresNal = esEntidSedePresNal;
	}

	public String getSedeSelPres() {
		return sedeSelPres;
	}

	public void setSedeSelPres(String sedeSelPres) {
		this.sedeSelPres = sedeSelPres;
	}

	public void cambiarSedePres() {
		if (!esCadenaVacia(sedeSelPres)) {
			// Si es sede de presencia nacional
			if ((new Sede(sedeSelPres)).isEsSedePresenciaNacional()) {
				mostrarFacultadesPres = false;
				// esEntidSedePresNal = true;
				dependenciasUNPres = servicioDependencia.obtenerDependenciaXSede(sedeSelPres);
				setDependenciaPresItem(servicioDependencia.crearSelectItem(dependenciasUNPres));
				dependenciaPresProyecto = "";
			} else {
				// Si es una sede con facultad.
				mostrarFacultadesPres = true;
				// esEntidSedePresNal = false;
				List<Dependencia> facultadesUN = servicioDependencia.obtenerFacultadesXSede(sedeSelPres);
				facultadPresItem = servicioDependencia.crearSelectItem(facultadesUN);
				facultadSelPres = ((Dependencia) facultadesUN.get(0)).getId().toString();
				cambiarFacultadPres();
			}
		} else {
			setDependenciaPresItem(new ArrayList<SelectItem>());
			dependenciaPresProyecto = "";
			mostrarFacultadesPres = false;
		}
	}

	public void cambiarFacultadPres() {
		dependenciasUNPres = servicioDependencia.obtenerDependenciasXFacultad(facultadSelPres);
		// Si tiene mas dependencias la facultad.
		if (!esListaVacia(dependenciasUNPres)) {
			setDependenciaPresItem(servicioDependencia.crearSelectItem(dependenciasUNPres));
		} else {
			// Si no tiene mas dependencias se carga la misma.
			dependenciasUNPres = new ArrayList<Dependencia>();
			dependenciasUNPres.add(servicioDependencia.obtenerDependencia(facultadSelPres));
			setDependenciaPresItem(servicioDependencia.crearSelectItem(dependenciasUNPres));
		}
		dependenciaPresProyecto = "";
	}

	public SelectItem[] getSedePresItem() {
		return sedePresItem;
	}

	public void setSedePresItem(SelectItem[] sedePresItem) {
		this.sedePresItem = sedePresItem;
	}

	public boolean isMostrarFacultadesPres() {
		return mostrarFacultadesPres;
	}

	public void setMostrarFacultadesPres(boolean mostrarFacultadesPres) {
		this.mostrarFacultadesPres = mostrarFacultadesPres;
	}

	public List<SelectItem> getDependenciaPresItem() {
		return dependenciaPresItem;
	}

	public void setDependenciaPresItem(List<SelectItem> dependenciaPresItem) {
		this.dependenciaPresItem = dependenciaPresItem;
	}

	public String getDependenciaPresProyecto() {
		return dependenciaPresProyecto;
	}

	public void setDependenciaPresProyecto(String dependenciaPresProyecto) {
		this.dependenciaPresProyecto = dependenciaPresProyecto;
	}

	public List<SelectItem> getFacultadPresItem() {
		return facultadPresItem;
	}

	public void setFacultadPresItem(List<SelectItem> facultadPresItem) {
		this.facultadPresItem = facultadPresItem;
	}

	public boolean isEsConvCundinamarca2019() {
		return esConvCundinamarca2019;
	}

	public void setEsConvCundinamarca2019(boolean esConvCundinamarca2019) {
		this.esConvCundinamarca2019 = esConvCundinamarca2019;
	}

	public String getFacultadSelPres() {
		return facultadSelPres;
	}

	public void setFacultadSelPres(String facultadSelPres) {
		this.facultadSelPres = facultadSelPres;
	}

	public boolean isEsConvCP2019() {
		return esConvCP2019;
	}

	public boolean isEsConvUnInnova2019() {
		return esConvUnInnova2019;
	}

	public void setEsConvUnInnova2019(boolean esConvUnInnova2019) {
		this.esConvUnInnova2019 = esConvUnInnova2019;
	}

	public boolean isEsConvUnInnova2021() {
		return esConvUnInnova2021;
	}

	public void setEsConvUnInnova2021(boolean esConvUnInnova2021) {
		this.esConvUnInnova2021 = esConvUnInnova2021;
	}

	public boolean isEsConvProyectosPal2019() {
		return esConvProyectosPal2019;
	}

	public void setEsConvProyectosPal2019(boolean esConvProyectosPal2019) {
		this.esConvProyectosPal2019 = esConvProyectosPal2019;
	}

	public void setEsConvCP2019(boolean esConvCP2019) {
		this.esConvCP2019 = esConvCP2019;
	}

	public static String getDominioTipologiaProyectosConvCp2019() {
		return DOMINIO_TIPOLOGIA_PROYECTOS_CONV_CP_2019;
	}

	public boolean isMostrarObjetivos() {
		return mostrarObjetivos;
	}

	public void setMostrarObjetivos(boolean mostrarObjetivos) {
		this.mostrarObjetivos = mostrarObjetivos;
	}

	public String getSedeFiltroLabs() {
		return sedeFiltroLabs;
	}

	public void setSedeFiltroLabs(String sedeFiltroLabs) {
		this.sedeFiltroLabs = sedeFiltroLabs;
	}

	public String getLabSeleccionado() {
		return labSeleccionado;
	}

	public void setLabSeleccionado(String labSeleccionado) {
		this.labSeleccionado = labSeleccionado;
	}

	public SelectItem[] getLabsItem() {
		return labsItem;
	}

	public void setLabsItem(SelectItem[] labsItem) {
		this.labsItem = labsItem;
	}

	public ArrayList<Laboratorio> getLaboratorios() {
		return laboratorios;
	}

	public void setLaboratorios(ArrayList<Laboratorio> laboratorios) {
		this.laboratorios = laboratorios;
	}

	public String getFacultadSelLabs() {
		return facultadSelLabs;
	}

	public void setFacultadSelLabs(String facultadSelLabs) {
		this.facultadSelLabs = facultadSelLabs;
	}

	public List<SelectItem> getFacultadItemLabs() {
		return facultadItemLabs;
	}

	public void setFacultadItemLabs(List<SelectItem> facultadItemLabs) {
		this.facultadItemLabs = facultadItemLabs;
	}

	public Laboratorio getLabEliminar() {
		return labEliminar;
	}

	public void setLabEliminar(Laboratorio labEliminar) {
		this.labEliminar = labEliminar;
	}

	public boolean isEsConvURosario() {
		return esConvURosario;
	}

	public void setEsConvURosario(boolean esConvURosario) {
		this.esConvURosario = esConvURosario;
	}

	public Long getContrapEfectUNAL_ConvURosario() {
		return contrapEfectUNAL_ConvURosario;
	}

	public void setContrapEfectUNAL_ConvURosario(Long contrapEspecialUNAL_ConvURosario) {
		this.contrapEfectUNAL_ConvURosario = contrapEspecialUNAL_ConvURosario;
	}

	public Dependencia getLugarEjecEliminar() {
		return lugarEjecEliminar;
	}

	public void setLugarEjecEliminar(Dependencia lugarEjecEliminar) {
		this.lugarEjecEliminar = lugarEjecEliminar;
	}

	public DependenciaAportante getDependenciaAportanteSeleccionada() {
		return dependenciaAportanteSeleccionada;
	}

	public void setDependenciaAportanteSeleccionada(DependenciaAportante dependenciaAportanteSeleccionada) {
		this.dependenciaAportanteSeleccionada = dependenciaAportanteSeleccionada;
	}

	public String getDependenciaAportanteProyecto() {
		return dependenciaAportanteProyecto;
	}

	public void setDependenciaAportanteProyecto(String dependenciaAportanteProyecto) {
		this.dependenciaAportanteProyecto = dependenciaAportanteProyecto;
	}

	public String getFacultadSelAportante() {
		return facultadSelAportante;
	}

	public void setFacultadSelAportante(String facultadSelAportante) {
		this.facultadSelAportante = facultadSelAportante;
	}

	public String getSedeSelAportante() {
		return sedeSelAportante;
	}

	public void setSedeSelAportante(String sedeSelAportante) {
		this.sedeSelAportante = sedeSelAportante;
	}

	public List<SelectItem> getFacultadAportanteItem() {
		return facultadAportanteItem;
	}

	public void setFacultadAportanteItem(List<SelectItem> facultadAportanteItem) {
		this.facultadAportanteItem = facultadAportanteItem;
	}

	public List<SelectItem> getDependenciaAportanteItem() {
		return dependenciaAportanteItem;
	}

	public Long getTotalDependenciasAportantes() {
		return totalDependenciasAportantes;
	}

	public void setTotalDependenciasAportantes(Long totalDependenciasAportantes) {
		this.totalDependenciasAportantes = totalDependenciasAportantes;
	}

	public void setDependenciaAportanteItem(List<SelectItem> dependenciaAportanteItem) {
		this.dependenciaAportanteItem = dependenciaAportanteItem;
	}

	public List<ProductoTipo> getListaProductosNivel2() {
		return listaProductosNivel2;
	}

	public void setListaProductosNivel2(List<ProductoTipo> listaProductosNivel2) {
		this.listaProductosNivel2 = listaProductosNivel2;
	}

	public List<ProductoTipo> getListaProductosNivel1() {
		return listaProductosNivel1;
	}

	public void setListaProductosNivel1(List<ProductoTipo> listaProductosNivel1) {
		this.listaProductosNivel1 = listaProductosNivel1;
	}

	public SelectItem[] getProductoNivel2Item() {
		return productoNivel2Item;
	}

	public void setProductoNivel2Item(SelectItem[] productoNivel2Item) {
		this.productoNivel2Item = productoNivel2Item;
	}

	public SelectItem[] getProductoNivel1Item() {
		return productoNivel1Item;
	}

	public void setProductoNivel1Item(SelectItem[] productoNivel1Item) {
		this.productoNivel1Item = productoNivel1Item;
	}

	public String getProductoNivel2() {
		return productoNivel2;
	}

	public void setProductoNivel2(String productoNivel2) {
		this.productoNivel2 = productoNivel2;
	}

	public String getProductoNivel1() {
		return productoNivel1;
	}

	public void setProductoNivel1(String productoNivel1) {
		this.productoNivel1 = productoNivel1;
	}

	public List<ProductoTipo> getProductosConfiguradosConvocatoria() {
		return productosConfiguradosConvocatoria;
	}

	public void setProductosConfiguradosConvocatoria(List<ProductoTipo> productosConfiguradosConvocatoria) {
		this.productosConfiguradosConvocatoria = productosConfiguradosConvocatoria;
	}

	public boolean isEsConvocatoriaFortalecimientoLabs2024_M1() {
		return esConvocatoriaFortalecimientoLabs2024_M1;
	}

	public void setEsConvocatoriaFortalecimientoLabs2024_M1(boolean esConvocatoriaFortalecimientoLabs2024_M1) {
		this.esConvocatoriaFortalecimientoLabs2024_M1 = esConvocatoriaFortalecimientoLabs2024_M1;
	}

	public boolean isEsConvocatoriaFortalecimientoLabs2024_M2() {
		return esConvocatoriaFortalecimientoLabs2024_M2;
	}

	public void setEsConvocatoriaFortalecimientoLabs2024_M2(boolean esConvocatoriaFortalecimientoLabs2024_M2) {
		this.esConvocatoriaFortalecimientoLabs2024_M2 = esConvocatoriaFortalecimientoLabs2024_M2;
	}

	public boolean isEsConvocatoriaRedes() {
		return esConvocatoriaRedes;
	}

	public void setEsConvocatoriaRedes(boolean esConvocatoriaRedes) {
		this.esConvocatoriaRedes = esConvocatoriaRedes;
	}

	public String getMedioVerificacion() {
		return medioVerificacion;
	}

	public void setMedioVerificacion(String medioVerificacion) {
		this.medioVerificacion = medioVerificacion;
	}

	public String getEntregable() {
		return entregable;
	}

	public void setEntregable(String entregable) {
		this.entregable = entregable;
	}

	public Date getFechaEntregable() {
		return fechaEntregable;
	}

	public void setFechaEntregable(Date fechaEntregable) {
		this.fechaEntregable = fechaEntregable;
	}

	public Long getValorAporteDependenciaEspecie() {
		return valorAporteDependenciaEspecie;
	}

	public void setValorAporteDependenciaEspecie(Long valorAporteDependenciaEspecie) {
		this.valorAporteDependenciaEspecie = valorAporteDependenciaEspecie;
	}

	public boolean isEditarVigencias() {
		return editarVigencias;
	}

	public void setEditarVigencias(boolean editarVigencias) {
		this.editarVigencias = editarVigencias;
	}

	public boolean isEsConvocatoriaPlanArmonizacion() {
		return esConvocatoriaPlanArmonizacion;
	}

	public void setEsConvocatoriaPlanArmonizacion(boolean esConvocatoriaPlanArmonizacion) {
		this.esConvocatoriaPlanArmonizacion = esConvocatoriaPlanArmonizacion;
	}

	public boolean isEsDirector() {
		return esDirector;
	}

	public void setEsDirector(boolean esDirector) {
		this.esDirector = esDirector;
	}

	public SelectItem[] getComponentesItems() {
		return componentesItems;
	}

	public void setComponentesItems(SelectItem[] componentesItems) {
		this.componentesItems = componentesItems;
	}

	public boolean isEtiquetas2025PlanDllo() {
		return etiquetas2025PlanDllo;
	}

	public void setEtiquetas2025PlanDllo(boolean etiquetas2025PlanDllo) {
		this.etiquetas2025PlanDllo = etiquetas2025PlanDllo;
	}
}