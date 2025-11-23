package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.AreaTematica;
import co.edu.unal.hermes.modelo.AreaTematicaVista;
import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DependenciaAreaResponsabilidad;
import co.edu.unal.hermes.modelo.Dominio;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoCivil;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoCambioIntegrantes;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.IdDominioDetalle;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.ModalidadFuenteFinanciacion;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.PlanEstudios;
import co.edu.unal.hermes.modelo.PoblacionObjetivoEvento;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoProducto;
import co.edu.unal.hermes.modelo.ResultadoProyecto;
import co.edu.unal.hermes.modelo.RubroFinanciable;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoDuracion;
import co.edu.unal.hermes.modelo.TipoFormacion;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.modelo.TipoVinculacion;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.ValoresListasProyecto;
import co.edu.unal.hermes.modelo.mapeo.GastoFM;
import co.edu.unal.hermes.utils.VariablesEstaticas;
import co.edu.unal.hermes.vista.proyectos.ManejadorFichaMinimaBase;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;
import co.edu.unal.hermes.vista.proyectos.ManejadorFichaMinimaBase.EquipoTrabajoValidacion;
import co.edu.unal.hermes.vista.proyectos.ManejadorFichaMinimaBase.montoFinanciar;

public class ManejadorFichaMinimaProyectos extends ManejadorProyecto {

	private Convocatoria convocatoriaActual;
	private String categoria;
	private SelectItem[] categoriaItems;
	private List<DominioDetalle> listaCategorias;
	private String rolUniversidad;
	private SelectItem[] rolUniversidadItems;
	private List<DominioDetalle> listaRolUniversidad;
	private String mecanismoSolicitud;
	private SelectItem[] mecanismoSolicitudItems;
	/** The tipos fuente item. */
	private SelectItem[] tiposFuenteItem;
	private List<DominioDetalle> listaMecanismoSolicitud;
	private String nombreProyecto;
	private String objetivoGeneral;
	private String resumenProyecto;
	private String objetivoEspecifico;
	private List<ObjetivoEspecifico> listaObjetivos;
	private DataTable tablaObjetivos;
	private ObjetivoEspecifico objetivoTabla;
	private UIComponent objetoEspecifico;
	private UIComponent objetoEspecificoEven;
	private List programas;
	private List cortes;
	private String resultado;
	private List<ResultadoProyecto> listaResultados;
	private DataTable tablaResultados;
	private UIComponent uiResultado;
	private UIComponent uiCantidad;
	private UIComponent uiCantidadEventos;
	private UIComponent botonAgregarGasto;
	private UIComponent botonAgregarFinanciacionExterna;
	private boolean requiereOtraFuente;

	private String[] selectedPoblacionObjetivo;

	private ResultadoProyecto resultadoTabla;

	private String productoNivel1;
	private String productoNivel2;
	private String productoNivel3;
	private String maxAsistentes;

	private ProductoTipo productoNivel1Actual;
	private ProductoTipo productoNivel2Actual;
	private ProductoTipo productoNivel3Actual;

	private SelectItem[] productoNivel1Item;
	private SelectItem[] productoNivel2Item;
	private SelectItem[] productoNivel3Item;

	private List listaProductosNivel1;
	private List listaProductosNivel2;
	private List listaProductosNivel3;
	private List productosConvocatoria;

	private UISelectOne manejadorProductoNivel1;
	private UISelectOne manejadorProductoNivel2;
	private UISelectOne manejadorProductoNivel3;

	private String cantidad;
	private Integer tiempoProyectos;

	private String DOMINIO_CATEGORIA = "ACTIVIDAD_AVAL";
	private String DOMINIO_ROL = "ROL_UNIVERSIDAD_PROYECTOS";
	private String DOMINIO_MECANISMO = "MECANISMO_PROYECTOS";
	private String DOMINIO_TIPO_EVENTO = "TEVENTO";
//	private String DOMINIO_TIPO_EVENTO_MOOTS_2019_M1 = "TEVENTO_MOOTS_2019_M1";
//	private String DOMINIO_TIPO_EVENTO_MOOTS_2019_M2 = "TEVENTO_MOOTS_2019_M2";
	private String DOMINIO_POBLACION_OBJETIVO_EVENTO = "DOMINIO_POBLACION_OBJETIVO_EVENTO";
	private String DOMINIO_METODOLOGIA_EVENTO = "DOMINIO_METODOLOGIA_EVENTO";
	private String errorEstudiantes;

	private PalabraClave palabraClave; // PALABRA CLAVE ACTUAL
	private PalabraClave palabraClaveTabla; // PALABRA CLAVE ACTUAL
	private PalabraClave keyWord; // PALABRA CLAVE ACTUAL
	private EstadoProyecto estadoProyectoActual;// ESTADO DEL PROYECTO ACTUAL
	private TipoDuracion tipoDuracionActual; // TIPO DE DURACION DEL PROYECTO
	// (DIAS, SEMANAS, MESES, AÑOS)
	private UIData tablaPalabras; // TABLA DE PALABRAS CLAVE
	private UIData tablaKeyswords; // TABLA DE PALABRAS CLAVE

	private SelectItem siNoItem[];

	private ProyectoProducto productoSeleccionado;

	// lmom
	private List listaAreaCiencia;
	private List listaTipoVinculacion;
	private List listaObjSocioeconomico;
	private SelectItem[] areaCienciaItems;
	private SelectItem[] tipoVinculacionItems;
	private SelectItem[] objSocioeconomicoItems;
	private List<String> noDuracion;
	public List<PalabraClave> listaPalabrasClave;
	private String DOMINIO_OBJETIVO_SOCIO_EC = "OBJETIVO_SOCIO_ECONOMICO";
	private String DOMINIO_AREA_CIENCIA = "AREA_CIENCIA";
	private String DOMINIO_TIPO_VINCULACION = "TIPO_VINCULACION_PROYECTOS";
	public String documentoCoinv;
	public TipoDocumento tipoDocumentoCoInv;
	public SelectItem[] tipoDocumentoItem;
	private String unidadAcademicaEjecutora;
	private Integer horasDirector;
	public String documentoCoinv2;
	public TipoDocumento tipoDocumentoCoInv2;
	private String unidadAcademicaEjecutora2;
	private Integer horasParticipante;
	private Integer tiempoTotalParticipante;
	private List<InvestigadorProyecto> listaDirector;
	private List<InvestigadorProyecto> listaParticipantes;
	private List<InvestigadorProyecto> listaParticipantesBorrados;
	public List<TipoDocumento> listaTipoDocumento;
	public SelectItem[] categoriaItem;
	public String selItems = "A";
	protected long valorPersonalTotal;
	Investigador investigadorDirector = new Investigador();
	InvestigadorProyecto participante = new InvestigadorProyecto();
	private String areaCiencia = "2701";
	private String areaCienciaSec = "2701";
	protected boolean materiasSIA = false;
	protected boolean verFichaMin = false;
	private boolean esPosgrado1_2 = false;
	private InvestigadorExterno investigadorExterno = new InvestigadorExterno();
	private boolean esOtraVinculacion = false;
	private String unidadEjecutoraPart;
	protected SelectItem[] generoItem = { new SelectItem(VariablesEstaticas.GENERO_FEMENINO, "Mujer - Femenino"),
			new SelectItem(VariablesEstaticas.GENERO_MASCULINO, "Hombre - Masculino") };
	/** The fuentes internas item. */
	private SelectItem[] fuentesInternasItem;
	private String insitucionNombre;
	private List listaInstitucion;
	private SelectItem[] institucionItem;
	private Institucion institucion;
	private boolean proyectoExiste = false;
	private String linkLineas;
	private String linkObjetivos;
	private boolean esProyectoInnoModDos = false;

	private boolean mostrarSiConvocatoriaEventos = false;

	// INICIO INFORMACION FINANCIERA PROYECTO

	private String unidadEjecutora;
	private List<Dependencia> dependenciasUN;
	public SelectItem[] dependenciaItem;
	private boolean fuentesExternas = false;

	private SelectItem[] naturalezaRubroItem = { new SelectItem(VariablesEstaticas.RUBRO_EFECTIVO, "Efectivo"),
			new SelectItem(VariablesEstaticas.RUBRO_ESPECIE, "Especie") };
	public Long idtipoRubro;
	public Long idsubtipoRubro;
	/** The tipo fuente actual. */
	private int tipoFuenteActual; // TIPO DE FUENTE DE FINANCIACION

	public String anio = "1";

	public String tipoRecurso = "1";
	public Long valorGasto;

	public String tipoContrapartida = "1";
	public Long valorContrapartida;

	public Gasto gastoActual;

	public TipoRubro tipoRubro;
	public List listaRubrosFinanciables;
	public List listaRubrosContrapartida;
	public SelectItem[] tiposRubroItem;

	public TipoRubro subtipoRubro;
	public List<SelectItem> listaSubRubrosFinanciables;
	public SelectItem[] subtiposRubroItem;

	// FUENTES DE FINANCIACION
	private List listaFuentesInternas; // LISTA GENERAL DE FUENTES DE
	// FINANCIACION INTERNAS
	private List listaFuentesExternas; // LISTA GENERAL DE FUENTES DE
	// FINANCIACION EXTERNAS
	private List listaAuxiliarFuentes; // LISTA GENERAL DE FUENTES DE
	// FINANCIACION
	private FuenteFinanciacion fuenteFinancieraActual; // FUENTE FINANCIERA
	// ESCOGIDA
	private Financiacion financiacionActual; // DATOS DE LA FINANCIACION DADA
	// POR LA FUENTE FINANCIERA ACTUAL

	// PROYECTO
	private DataTable tablaGastos; // TABLA DE RUBROS
	private List listaTiposRubros; // LISTA GENERAL DE TIPOS DE RUBROS
	private Long idTipoRubro;
	private Gasto gastoSeleccionado;
	private int vigenciaGasto = 1;
	private String descripcionGasto = "";

	private List listaInvestigadoresVista;
	private List listaAreasPrimSec;
	private Proyecto proyectoAsociar;
	private boolean esConvIni = false;
	private boolean esConvPurdue = false;
	private boolean esConvEventos2019 = false;
	private boolean esConvMoots2019 = false;
	private boolean esConvMoots2019_M1 = false;
	private boolean esConvMoots2019_M2 = false;
	private boolean esConvocatoriaEventos = false;
	private boolean esConvocatoriaEventosInternacional = false;
	private boolean esConvocatoriaEventosNacional = false;

	private boolean esConvMoots2021_M1 = false;
	private boolean esConvMoots2021_M2 = false;

	private boolean esConvCienciasAgraEquipos2015 = false;
	private boolean esConvSemill3 = false;
	private boolean esConvSemill = false;
	private List listaFacultades;
	private String facultad;

	// FIN INFORMACION FINANCIERA PROYECTO

	private String serviciosAcademicos;
	private String nombreCompletoPersonaActual;
	private String nombreSede;
	private String nombreFacultad;
	private String nombreDepartamento;
	private String emailPersonaActual;
	private String telefono;
	private InvestigadorInterno ii;

	private String tipoEvento;
	private SelectItem[] tipoEventoItems;
	private List<DominioDetalle> listaTipoEvento;
	private boolean mostrarOtroTipoEvento = false;
	private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad;
	private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada;
	private String dependenciaAdicionada;
	private UIComponent buscarPer2;
	private Departamento departamentoActual; // DEPARTAMENTO ACTUAL
	private List listaDepartamentos; // LISTA DE DEPARTAMENTOS
	private Ciudad ciudadActual; // CIUDAD SELECCIONADA
	private List listaCiudades; // LISTA DE CIUDADES DEL DEPARTAMENTO
	private SelectItem[] departamentoItem; // DEPARTAMENTOS A MOSTRAR
	private SelectItem[] ciudadItem; // CIUDADES A MOSTRAR

	protected SelectItem[] objetivosDesarrolloSostenibleItems;
	protected String objetivoDesarrolloSosteniblePrimario;
	protected String objetivoDesarrolloSostenibleSecundario;
	protected ValoresListasProyecto objetivoDesarrolloSostenibleSeleccionado;
	protected List<ValoresListasProyecto> valoresObjetivosDesarrolloSostenible;
	protected List<ValoresListasProyecto> valoresObjetivosDesarrolloSostenibleBorrados;
	protected static final String DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE = "OBJETIVOS_DESARROLLO_SOSTENIBLE";
	protected UIComponent btnObjDesSosSec;

	private SelectItem[] poblacionObjetivoEventoItem;
	private SelectItem[] poblacionObjetivoEventoGrupoItem;
	private SelectItem[] poblacionObjetivoEventoPoblacionItem;
	private SelectItem[] metodologiaEventoItem;
	/** The fuentes externas item. */
	private SelectItem[] fuentesExternasItem;
	private SelectItem[] fuentesExternasFinanciacionItem;
	private String financiadoraExterna;
	private Long idRubroFinanciadoraExterna;
	private Long valorFinanciadoraExterna;
	private String descripcionRubroExterna;
	/** The entidad coejecutora. */
	private String entidadCoejecutora;
	private Long valorAporteEfectivoOrganizador = 0L;
	private Long valorAporteEspecieOrganizador = 0L;
	/** The entidad eliminar. */
	private Financiacion entidadEliminar;
	protected String idPaisEvento;
	protected boolean siColombia = false;
	protected SelectItem[] listaPaisesItem;
	protected boolean mostrarPaises = false;

	// INTEGRANTES CAZ
	private Boolean esConsulta = false;
	private String conoceDatos = "SI";
	private String tipoVinculacionId = "1";
	private SelectItem[] tipoVinculacionItemsNumero;
	private Long cantidadPart = 0L;
	private String funcionInvestigador;
	private UIComponent buscarPer3;

//	protected SelectItem[] tipoVinculacionItems;
	protected TipoDocumento tipoDocumentoCoInvEquipo;
//	private SelectItem[] tipoDocumentoItem;
	protected String documentoCoinvEquipo;
	private boolean mostrarOpcionBuscarInvestigador = false;
	protected UIComponent botonBuscarInvestigadorExterno;
	protected Persona investigadorExterno2 = new InvestigadorExterno();
	private boolean esOtraVinculacion2 = true;
//	protected SelectItem[] generoItem = { new SelectItem(VariablesEstaticas.GENERO_FEMENINO, "Mujer - Femenino"),
//			new SelectItem(VariablesEstaticas.GENERO_MASCULINO, "Hombre - Masculino") };
	protected String idTipoFormacion;
	protected SelectItem[] tipoFormacionItem;
	protected String idTipoEstadoCivil;
	protected SelectItem[] estadoCivilItem;
	protected String idPaisNacimiento;
	protected SelectItem[] listaPaisesItem2;
	protected Departamento departamentoActual2;
//	protected SelectItem[] departamentoItem;
	protected Ciudad ciudadActual2;
	private List<SelectItem> ciudadItemList2;
	private boolean mostrarDatosJoven = false;
	protected String institucionSeleccionada;
	protected SelectItem[] institucionesItem;
	protected String areaCienciaInv;
//	private SelectItem[] areaCienciaItems;
	protected String subAreaCienciaInv;
	private SelectItem[] subAreaCienciaItemsInv;
	private String lugarNacimiento;
	private List<SelectItem> ciudadItemList;
	private boolean mostrarProgramaAcademico = false;
	private boolean mostrarDependencia = false;
//	private boolean mostrarOpcionBuscarInvestigador = false;
	private UIComponent horasCoinv2;
//	protected Integer horasParticipante;
	private UIComponent horasCoinv3;
//	protected Integer tiempoTotalParticipante;
	protected boolean asignarGrupo;
	protected String grupo;
	protected List<SelectItem> gruposInvestigacionItem;
	protected UIComponent buscarPer4;
//	InvestigadorProyecto participante = new InvestigadorProyecto();

	protected SelectItem[] ciudadItem2;
	protected List listaCiudades2;
	protected boolean siColombia2 = false;
	protected List<TipoInvestigador> listaTipoVinculacion2;
	protected List<TipoInvestigador> listaTipoVinculacion2Numero;

	/** The fuente actual int id. */
	private String fuenteActualIntId;
	protected boolean mostrarMenuFormulario = true;
	protected static final String DOMINIO_SUB_AREA_CIENCIA = "SUB_AREA_CIENCIA_FM";
	private List<HistoricoCambioIntegrantes> historicoIntegrantes;
	protected List<TipoInvestigador> listaTipoVinculacionNumero;
	private boolean esConvEventos2019M1 = false;
	private boolean esConvEventos2019M2 = false;
	private String idNaturalezaRubro = "";

	//

	public ManejadorFichaMinimaProyectos() {
		super();

		proyectoAsociar = (Proyecto) sesion.getAttribute("proyectoAsociar");
		sesion.removeAttribute("proyectoAsociar");
		ii = new InvestigadorInterno();
		listaRubrosFinanciables = new ArrayList();
		listaSubRubrosFinanciables = new ArrayList<SelectItem>();

		dependenciaAreaResponsabilidad = new DependenciaAreaResponsabilidad();

		listaFacultades = servicioGeneral.obtenerFacultades();
		facultad = ((Dependencia) listaFacultades.get(0)).getId();

		// lmom
		listaAreaCiencia = new ArrayList<SelectItem>();
		listaObjSocioeconomico = new ArrayList<SelectItem>();
		listaTipoVinculacion = new ArrayList<TipoInvestigador>();
		tipoDocumentoCoInv = new TipoDocumento();
		tipoDocumentoCoInv2 = new TipoDocumento();
		listaDirector = new ArrayList<InvestigadorProyecto>();
		listaParticipantes = new ArrayList<InvestigadorProyecto>();
		listaParticipantesBorrados = new ArrayList<InvestigadorProyecto>();
		listaAreasPrimSec = new ArrayList<AreaTematica>();

		idManejador = FICHA_MINIMA;
		palabraClave = new PalabraClave();
		keyWord = new PalabraClave();
		estadoProyectoActual = new EstadoProyecto();
		tipoDuracionActual = new TipoDuracion();
		tablaPalabras = new UIData();
		tablaKeyswords = new UIData();
		siNoItem = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(new Long(Tipos.SINO));
		listaTiposRubros = new ArrayList();
		gastoActual = new Gasto();
		idNaturalezaRubro = VariablesEstaticas.RUBRO_EFECTIVO;
		cargarRubrosModalidad();

		cargarConvocatoriaActual();
		if (!convocatoriaActual.isIncluirFinanciacionExterna()) {
			tipoFuenteActual = 1;
		} else {
			String hql = "select ff from FuenteFinanciacion ff where ff.internaExterna like 'E' and ff.descripcion not like '%CODIGO%' and ff.observaciones = 'ENT_REG_PRY' and ff.quipu = 'S' order by ff.descripcion)";
			List<FuenteFinanciacion> lista = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class, hql);
			fuentesExternasFinanciacionItem = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				FuenteFinanciacion d = (FuenteFinanciacion) lista.get(i);
				String nombre = d.getDescripcion().toUpperCase();
				fuentesExternasFinanciacionItem[i] = new SelectItem(d.getId(), nombre);
			}
		}
		cargarListas();
		// cargarTipoProductos();
		cargarListaProductos();
		departamentoActual = new Departamento();
		ciudadActual = new Ciudad();
		obtenerListaDepartamentos();
		obtenerListaCiudades();

		valoresObjetivosDesarrolloSostenible = new ArrayList<ValoresListasProyecto>();

		valoresObjetivosDesarrolloSostenibleBorrados = new ArrayList<ValoresListasProyecto>();

		// INICIO FINANCIERO
		fuenteFinancieraActual = new FuenteFinanciacion();
		financiacionActual = new Financiacion();
		listaFuentesExternas = new ArrayList();
		listaFuentesInternas = new ArrayList();
		listaAuxiliarFuentes = new ArrayList();

		linkLineas = "http://www.hermes.unal.edu.co/pages/descargas/AreasCiencia.pdf";
		linkObjetivos = "http://www.hermes.unal.edu.co/pages/descargas/ObjetivoSocioeconomico.pdf";

		selectedPoblacionObjetivo = new String[4];

		// Verificar si el proyecto existe

		if (proyectoActual.getId() != null) {

			if (proyectoActual.getClaseEvento() != null && proyectoActual.getClaseEvento().equals("OT")) {
				mostrarOtroTipoEvento = true;
			}

			proyectoExiste = true;

			try {
				boolean incluirGastos = true;
				proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(),
						ProyectoDAOHibernate.TODO_POR_ID, incluirGastos);

				listaObjetivos.addAll(proyectoActual.getObjetivosEspecificos());

				// cargar lista de resultados

				listaResultados.addAll(proyectoActual.getResultados());

			} catch (Exception e) {

			}

			// cargar area ciencia principal
			listaAreasPrimSec = servicioGeneral
					.obtenerObjetos("select e from AreaTematica e where e.proyecto.id = " + proyectoActual.getId());

			for (int i = 0; i < listaAreasPrimSec.size(); i++) {
				AreaTematica at = (AreaTematica) listaAreasPrimSec.get(i);
				if (at.getTipo() == 1) {
					areaCiencia = at.getProyectoAreaTematica().getIdentificador().getTipo();
				} else {
					areaCienciaSec = at.getProyectoAreaTematica().getIdentificador().getTipo();
				}
			}

			// se cargan objetivos de desarrollo sostenible
			cargarObjetivoDesarrolloSosteniblePrincipal();
			cargarObjetivoDesarrolloSostenibleSecundarios();

			asignarValoresListas();

			// cargar director del proyecto y participantes del proyecto

			listaInvestigadoresVista = proyectoActual.getObtenerListaInvestigadoresVista();

			if (listaInvestigadoresVista != null) {
				for (int i = 0; i < listaInvestigadoresVista.size(); i++) {
					InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) listaInvestigadoresVista.get(i);
					InvestigadorProyecto ip = ipv.getInvestigadorProyecto();
					if (ip.getTipo().getId().equals("P")) {
						listaDirector.add(ip);
					} else {
						listaParticipantes.add(ip);
					}
				}
			} else {
				listaInvestigadoresVista = new ArrayList();
			}

			if (proyectoActual.getPaisEvento() != null) {
				revisarPais();
			} else {
				if (!mostrarPaises) {
					proyectoActual.setPaisEvento("CO");
					revisarPais();
				}
			}

			// SE CARGAN LA CIUDAD Y EL DEPARTAMENTO
			Set ciudades = proyectoActual.getCiudades();
			Iterator it = ciudades.iterator();
			if (it.hasNext()) {
				ciudadActual = (Ciudad) it.next();
				departamentoActual = servicioGeneral.obtenerDepartamento(ciudadActual);
				departamentoActual = buscarDepartamento(departamentoActual.getId());
			}
			if (siColombia) {
				cambiarDepartamento();
			}

		} else {

			proyectoExiste = false;

			listaInvestigadoresVista = proyectoActual.getObtenerListaInvestigadoresVista();

			if (proyectoAsociar != null) {
				proyectoActual.setCodigoDib(proyectoAsociar.getId().toString());
				proyectoActual.setNombre(proyectoAsociar.getNombre());
			}

			proyectoActual.setFase(0);
			proyectoActual.cambiarEstadoPersona(EstadoProyecto.INGRESANDO, cargarPersonaActual());
			proyectoActual.setDuracion(convocatoriaActual.getTiempoEjecucionProyecto());

			if (!mostrarPaises) {
				proyectoActual.setPaisEvento("CO");
				revisarPais();
			}
		}

		// cargar funentes financieras

		// Si tiene financiación normal.
		// cargar fuentes financieras Pendiente por revisar. martha
		/*
		 * if (proyectoActual.getListaFinancionesFicha() == null
		 * 
		 * || (proyectoActual.getListaFinancionesFicha() != null &&
		 * proyectoActual.getListaFinancionesFicha().isEmpty())) {
		 * 
		 * List<ModalidadFuenteFinanciacion> lista = servicioModalidad
		 * .listaModFuenteFinXModalidad(convocatoriaActual.getId());
		 * 
		 * 
		 * if (!esListaVacia(lista)) {
		 * 
		 * FuenteFinanciacion ff = lista.get(0).getFuenteFinanciacion();
		 * ingresarFuenteUniversidad(ff, null); } }
		 */

		// lmom
		List<ModalidadFuenteFinanciacion> lista = servicioModalidad
				.listaModFuenteFinXModalidad(convocatoriaActual.getId());
		fuentesInternasItem = new SelectItem[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			FuenteFinanciacion d = (FuenteFinanciacion) lista.get(i).getFuenteFinanciacion();
			String nombre = d.getDescripcion().toUpperCase();
			if (nombre != null && nombre.length() > 80) {
				nombre = nombre.substring(0, 80) + "...";
			}
			fuentesInternasItem[i] = new SelectItem(d.getId(), nombre);
		}

		Investigador investigadorActual = servicioPersona
				.obtenerInvestigadorInterno(((Persona) sesion.getAttribute("persona")).getId());

		if (proyectoActual.getId() != null) {

			InvestigadorProyecto ipry = new InvestigadorProyecto();
			String sqlIp = "select e from InvestigadorProyecto e where e.proyecto.id = " + proyectoActual.getId()
					+ " and e.tipo.id = 'P'";
			List listaInvPrincipal = servicioGeneral.obtenerObjetos(sqlIp);

			if (listaInvPrincipal != null && listaInvPrincipal.size() > 0) {
				ipry = (InvestigadorProyecto) listaInvPrincipal.get(0);
			}

			investigadorActual = ipry.getInvestigador();
		} else {

		}

		if (investigadorActual != null) {

			this.documentoCoinv = investigadorActual.getId().getDocumento();

			List listaDoc = servicioGeneral.obtenerObjetoXID("TipoDocumento",
					investigadorActual.getId().getTipoDocumento());
			this.tipoDocumentoCoInv = (TipoDocumento) listaDoc.get(0);
			nombreCompletoPersonaActual = investigadorActual.getNombre1() + " " + investigadorActual.getNombre2() + " "
					+ investigadorActual.getApellido1() + " " + investigadorActual.getApellido2();

			ii = servicioPersona.obtenerInvestigadorInterno(investigadorActual.getId());
			if (ii != null) {
				nombreSede = ii.getDependencia().getSede().getNombre();
				try {
					nombreFacultad = ii.getDependencia().getFacultad().getNombre();
				} catch (Exception e) {
					nombreFacultad = "Sin dependencia asignada";
				}
				nombreDepartamento = ii.getDependencia().getNombre();
				emailPersonaActual = ii.getEmail();
				telefono = ii.getTelefono();
			} else {
				nombreSede = "";
				nombreFacultad = "";
				nombreDepartamento = "";
				emailPersonaActual = "";
				telefono = "";
			}

		}

		if (convocatoriaActual.getProgramaAcademico() != null
				&& convocatoriaActual.getProgramaAcademico().equals("Y")) {
			programas = cargarPlanEstudios(programas);
		}

		if (convocatoriaActual.getPadre().getEsCorte() != null
				&& convocatoriaActual.getPadre().getEsCorte().equals("Y")) {
			cortes = cargarCortes(cortes);
		}

		if (esConvEventos2019 || esConvMoots2019 || esConvocatoriaEventos) {
			inicializarIntegrantesFicha();
			cargarListasSeccionIntegrantes();
		}
	}

	protected void cargarListasSeccionIntegrantes() {

		ciudadItemList2 = new ArrayList<SelectItem>();
		String consulta = "select cc from Ciudad cc where cc.id not in ('CO25', 'CO11', '00', 'CO02999', 'CO68')";
		List<Ciudad> lista = servicioGeneral.obtenerObjetos(Ciudad.class, consulta);
		for (int i = 0; i < lista.size(); i++) {
			Ciudad ciudad = (Ciudad) lista.get(i);
			ciudadItemList2.add(new SelectItem(ciudad.getId(), ciudad.getNombre()));
		}

		obtenerListaInstituciones();

		departamentoActual2 = new Departamento();
		ciudadActual2 = new Ciudad();

		// Areas de la ciencia
		boolean validarEstado = false;
		List<DominioDetalle> listaAreaCiencia = servicioGeneral.obtenerDominioDetalle(DOMINIO_AREA_CIENCIA,
				validarEstado);
		areaCienciaItems = crearListaItems(listaAreaCiencia);

		List<DominioDetalle> listaObjetivosDesarrolloSostenible = servicioGeneral
				.obtenerDominioDetalle(DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE);
		objetivosDesarrolloSostenibleItems = crearListaItems(listaObjetivosDesarrolloSostenible);

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

	}

	private void cargarTipoVinculacionInvestigador() {

		// Tipo vinculacion
		listaTipoVinculacion2 = obtenerTipoVinculacionConvocatoria(convocatoriaActual, mostrarMenuFormulario);

		tipoVinculacionItems = crearListaTipoInvestigador(listaTipoVinculacion2);

		listaTipoVinculacion2Numero = new ArrayList<TipoInvestigador>();
		listaTipoVinculacion2Numero.addAll(listaTipoVinculacion2);
		tipoVinculacionItemsNumero = tipoVinculacionItems;

		if (convocatoriaActual.getTipoInvestigadoresConvocatoria() != null) {
			String consultaVinNoDato = "select ti from TipoInvestigador ti where ti.id in ("
					+ convocatoriaActual.getTipoInvestigadoresConvocatoria() + ") order by ti.nombre desc";
			listaTipoVinculacion2Numero = servicioGeneral.obtenerObjetos(TipoInvestigador.class, consultaVinNoDato);
			tipoVinculacionItemsNumero = crearListaTipoInvestigador(listaTipoVinculacion2Numero);
		}
	}

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

	public void inicializarIntegrantesFicha() {
		tipoVinculacionId = "PCD";
		esOtraVinculacion2 = false;
		esConsulta = false;
		personaActual = (Persona) sesion.getAttribute("persona");
//		tipoDocumentoCoInv = new TipoDocumento();
		tipoDocumentoCoInvEquipo = new TipoDocumento();

		// Se carga documento y tipo de investigador principal
//		tipoDocumentoCoInv.setId(personaActual.getId().getTipoDocumento());
//		documentoCoinv = personaActual.getId().getDocumento();

		// listaTiposRubros = new ArrayList<TipoRubro>();

		// Cargar lista sedes
//		List<Dependencia> listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
//		if (!esListaVacia(listaSedes)) {
//            sedesItem = new SelectItem[listaSedes.size()];
//            for (int i = 0; i < listaSedes.size(); i++) {
//                Dependencia sede = listaSedes.get(i);
//                sedesItem[i] = new SelectItem(sede.getId(), sede.getNombre());
//            }
//        }
		if (esNulo(valoresObjetivosDesarrolloSostenible))
			valoresObjetivosDesarrolloSostenible = new ArrayList<ValoresListasProyecto>();
		valoresObjetivosDesarrolloSostenibleBorrados = new ArrayList<ValoresListasProyecto>();
	}

	// INTEGRANTES CAZ PROYECTOS -> EVENTOS

	public void mostrarPanelConoceDatos() {
		tipoVinculacionId = "1";
	}

	public void agregarParticipanteNum() {

		if (!esCadenaVacia(tipoVinculacionId)) {
			TipoInvestigador tipoInvestigador = buscarTipoInvestigador(tipoVinculacionId, listaTipoVinculacion2Numero);

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
						if (tiempoTotalParticipante == null) {
							tiempoTotalParticipante = 0;
						}
						participanteNuevo.setTotalHorasVinculacion(Double.valueOf(tiempoTotalParticipante));

						if (convocatoriaActual.isMostrarActividadesInvestigador()) {
							participanteNuevo.setFuncion(funcionInvestigador);
						} else {
							participanteNuevo.setFuncion(InvestigadorProyecto.FUNCION_PARTICIPANTE_BASICO);
						}

						participanteNuevo.setTipo(tipoInvestigador);
						participanteNuevo.setValorPagar(0L);

						proyectoActual.adicionarInvestigadorProyecto(participanteNuevo);

					} else {
						mensajeError(buscarPer3, "La cantidad de participantes debe ser mayor a cero.");
					}
				} else {
					mensajeError(buscarPer3, "Este tipo de vinculación ya fue agregado.");
				}
			}
		}

	}

	public void cambiarVinculacion2() {
		asignarGrupo = false;
		esOtraVinculacion2 = false;
		mostrarDatosJoven = false;
		mostrarOpcionBuscarInvestigador = false;
		mostrarProgramaAcademico = false;
		mostrarDependencia = false;
		if (!esCadenaVacia(tipoVinculacionId)) {
			TipoInvestigador tipoInvestigador = buscarTipoInvestigador(tipoVinculacionId, listaTipoVinculacion2);
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

	public void buscarParticipanteExterno() {

		boolean encontrado = false;
		mostrarDatosJoven = false;
		esOtraVinculacion2 = false;

		// Se carga la opción de mostrar programa academico
		TipoInvestigador tipoInvestigador = buscarTipoInvestigador(tipoVinculacionId, listaTipoVinculacion2);
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
					InvestigadorExterno investigadorExterno2Existente = servicioPersona.obtenerInvestigadorExterno(
							new IdPersona(documentoCoinvEquipo, tipoDocumentoCoInvEquipo.getId()));

					if (investigadorExterno2Existente != null) {
						personaExistente = investigadorExterno2Existente;

						idTipoFormacion = (investigadorExterno2Existente.getTipoFormacion() != null
								? investigadorExterno2Existente.getTipoFormacion().getId()
								: "");
						idTipoEstadoCivil = (investigadorExterno2Existente.getEstadoCivil() != null
								? investigadorExterno2Existente.getEstadoCivil().getId()
								: "");
						idPaisNacimiento = (investigadorExterno2Existente.getPaisOrigen() != null
								? investigadorExterno2Existente.getPaisOrigen()
								: "");
						if (!idPaisNacimiento.equals("")) {
							revisarPais2();
						}

						Ciudad ciu = investigadorExterno2Existente.getCiudadNacimiento();

						if (ciu != null) {
							ciudadActual2 = new Ciudad();
							ciudadActual2.setId(ciu.getId());
							String hqlDepto = "select e from Ciudad e where e.id = '" + ciu.getId() + "'";
							List<Ciudad> listaCiudad = servicioGeneral.obtenerObjetos(hqlDepto);
							Ciudad ciudadAux = listaCiudad.get(0);
							departamentoActual2 = ciudadAux.getDepartamento();
							cambiarDepartamento2();
						}

						institucionSeleccionada = (investigadorExterno2Existente.getInstitucion() != null
								? investigadorExterno2Existente.getInstitucion().getId()
								: "");
						areaCienciaInv = (investigadorExterno2Existente.getAreaOcde() != null
								? investigadorExterno2Existente.getAreaOcde()
								: "");
						cambiarAreaInv();
						subAreaCienciaInv = (investigadorExterno2Existente.getSubareaOcde() != null
								? investigadorExterno2Existente.getSubareaOcde()
								: "");
					}

					this.investigadorExterno2 = personaExistente;

					if (estudiante != null) {
						cargarDatosEstudianteaPersona(estudiante);
					}
					encontrado = true;
				} else if (estudiante != null) {
					investigadorExterno2 = estudiante.convertirAPersona();
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
				listaPaisesItem2 = new SelectItem[listaPaises.size()];
				for (int i = 0; i < listaPaises.size(); i++) {
					Pais p = (Pais) listaPaises.get(i);
					listaPaisesItem2[i] = new SelectItem(p.getId(), p.getNombre());
				}

				if (!encontrado) {
					mensajeInfo(buscarPer4,
							"Persona no encontrada. Nuevo registro por favor ingresar los datos del participante.");
				}

				mostrarOpcionBuscarInvestigador = false;
				esOtraVinculacion2 = true;
			}
		} else {
			mensajeError(botonBuscarInvestigadorExterno, "Por favor ingrese el número y el tipo de documento.");
		}

	}

	public void limpiar() {
		investigadorExterno2 = new InvestigadorExterno();
		cambiarVinculacion2();
	}

	public void revisarPais2() {
		if (idPaisNacimiento != null && !idPaisNacimiento.equals("") && idPaisNacimiento.equals("CO")) {
			siColombia2 = true;
			obtenerListaDepartamentos();
			obtenerListaCiudades2();
		} else {
			siColombia2 = false;
			obtenerListaCiudadesDiferentesColombia2();
		}
	}

	private void obtenerListaCiudadesDiferentesColombia2() {
		String idPaisBus = idPaisNacimiento.substring(0, 2);
		String hql = "select #id e.id, #nombre e.nombre from Ciudad e where e.id like ('" + idPaisBus + "%')";
		listaCiudades2 = servicioGeneral.obtenerObjetosLimitado(Ciudad.class, hql);
		ciudadItem2 = new SelectItem[listaCiudades2.size()];
		for (int i = 0; i < listaCiudades2.size(); i++) {
			Ciudad ci = (Ciudad) listaCiudades2.get(i);
			ciudadItem2[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	public void cambiarDepartamento2() {
		setListaCiudades2(servicioGeneral.obtenerObjetosLimitado(Ciudad.class,
				"select #id e.id, #nombre e.nombre from Ciudad e where e.departamento.id like '"
						+ departamentoActual2.getId() + "' and e.sigla is not null order by e.nombre asc"));
		setCiudadItem2(new SelectItem[getListaCiudades2().size()]);
		for (int i = 0; i < getListaCiudades2().size(); i++) {
			Ciudad ci = (Ciudad) getListaCiudades2().get(i);
			getCiudadItem2()[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	public void cambiarAreaInv() {
		subAreaCienciaItemsInv = cambiarAreaCiencia(areaCienciaInv);
		subAreaCienciaInv = "";
	}

//	public String getLinkLineas() {
//		return "http://www.hermes.unal.edu.co/pages/descargas/AreasCiencia.pdf";
//	}

	public void obtenerListaInstituciones() {
		List<Institucion> listaInstituciones = servicioGeneral.obtenerListaInstituciones();
		institucionesItem = new SelectItem[listaInstituciones.size()];
		for (int i = 0; i < listaInstituciones.size(); i++) {
			Institucion ins = (Institucion) listaInstituciones.get(i);
			institucionesItem[i] = new SelectItem(ins.getId(), ins.getNombre());
			ins = null;
		}
	}

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

	public void agregarParticipante3() {
		// Se valida tipo de vinculación.
		if (esCadenaVacia(tipoVinculacionId)) {
			mensajeError(buscarPer4, "Por favor seleccione un tipo de vinculación.");
			return;
		}
		if (tipoDocumentoCoInvEquipo.getId().equals("")) {
			mensajeError(buscarPer4, "Por favor ingrese el tipo de documento del participante.");
			return;
		}
		if (esCadenaVacia(documentoCoinvEquipo)) {
			mensajeError(buscarPer4, "Por favor ingrese el número de documento del participante.");
			return;
		}
		if (documentoCoinvEquipo.contains(".")) {
			mensajeError(buscarPer4, "Por favor ingrese el número de documento del participante sin puntos.");
			return;
		}
		agregarParticipanteGeneral();
	}

	public void eliminarParticipanteNum() {
		proyectoActual.getInvestigadoresProyecto().remove(participante);
		proyectoActual.borrarInvestigadorProyecto(participante);
	}

	public void eliminarParticipante2() {

		proyectoActual.getInvestigadoresProyecto().remove(participante);
		proyectoActual.borrarInvestigadorProyecto(participante);

		calcularValorDocente();

		if (!mostrarMenuFormulario) {
			calcularTotalesFicha();
		}
	}

	public void cargarHistorico() {
		setHistoricoIntegrantes(servicioGeneral.obtenerObjetos(HistoricoCambioIntegrantes.class,
				"from HistoricoCambioIntegrantes h where h.proyecto.id = '" + proyectoActual.getId()
						+ "' order by h.id asc"));
		for (HistoricoCambioIntegrantes item : historicoIntegrantes) {
			TipoDocumento td = servicioGeneral
					.obtenerObjetoXID(TipoDocumento.class, item.getIntegrante().getId().getTipoDocumento()).get(0);
			item.setDocumento(td.getNombre());
			if (item.getTipoInvestigadorProyecto().getId().equals("P")
					|| item.getTipoInvestigadorProyecto().getId().equals("C")
					|| (!item.getTipoInvestigadorProyecto().getId().equals("P")
							&& !item.getTipoInvestigadorProyecto().getId().equals("C")
							&& item.getTipoInvestigadorProyecto().getTipo().equals("F"))) {
				item.setTipoVinculacionGrupo("Interno");
			} else if (item.getTipoInvestigadorProyecto().getTipo().equals("A")) {
				item.setTipoVinculacionGrupo("Interno");
			} else {
				item.setTipoVinculacionGrupo("Externo");
			}
		}
	}

	// Dependencias métodos
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

	private TipoInvestigador buscarTipoInvestigador(String idTipoInvestigador,
			List<TipoInvestigador> listaTipoVinculacion2) {
		if (!esListaVacia(listaTipoVinculacion2)) {
			Iterator<TipoInvestigador> i = listaTipoVinculacion2.iterator();
			while (i.hasNext()) {
				TipoInvestigador tipoInvestigador = i.next();
				if (tipoInvestigador.getId().equals(idTipoInvestigador)) {
					return tipoInvestigador;
				}
			}
		}
		return null;
	}

	private void cargarDatosEstudianteaPersona(Estudiante estudiante) {
		if (esCadenaVacia(investigadorExterno2.getEmail())) {
			investigadorExterno2.setEmail(estudiante.getEmail());
		}
		if (esCadenaVacia(investigadorExterno2.getTelefono())) {
			investigadorExterno2.setTelefono(estudiante.getTelefono());
		}
		if (investigadorExterno2.getFechaNacimiento() == null) {
			investigadorExterno2.setFechaNacimiento(estudiante.getFechaNacimiento());
		}
		if (esCadenaVacia(investigadorExterno2.getGenero())) {
			investigadorExterno2.setGenero(estudiante.getGenero());
		}
		if (esCadenaVacia(investigadorExterno2.getProfesion()) && estudiante.getPlan().getTipo() != null
				&& estudiante.getPlan().getTipo().equals(PlanEstudios.PREGRADO)) {
			investigadorExterno2.setProfesion(estudiante.getPlan().getNombre());
		}
		if (esCadenaVacia(investigadorExterno2.getPromedioPregrado()) && estudiante.getPapa() != null
				&& estudiante.getPlan().getTipo().equals(PlanEstudios.PREGRADO)) {
			investigadorExterno2.setPromedioPregrado(estudiante.getPapa().toString());
		}
		if (esCadenaVacia(investigadorExterno2.getResumenHojaDeVida())
				&& estudiante.getPlan().getTipo().equals(PlanEstudios.PREGRADO)) {
			investigadorExterno2.setResumenHojaDeVida("UNIVERSIDAD NACIONALDE COLOMBIA");
		}
		if (esCadenaVacia(investigadorExterno2.getFacultadPregrado())
				&& estudiante.getPlan().getTipo().equals(PlanEstudios.PREGRADO)
				&& estudiante.getDependencia() != null) {
			investigadorExterno2.setFacultadPregrado(estudiante.getDependencia().getFacultad().getNombre());
		}
		if (esCadenaVacia(investigadorExterno2.getNacionalidad())) {
			investigadorExterno2.setNacionalidad("Colombiano/a");
		}
	}

	protected SelectItem[] cambiarAreaCiencia(String areaCiencia) {
		String consultaAreasSec = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
				+ DOMINIO_SUB_AREA_CIENCIA + "' and  dd.estado = '" + areaCiencia + "' order by dd.descripcion";
		List<DominioDetalle> listaSubAreaCiencia = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				consultaAreasSec);
		return crearListaItems(listaSubAreaCiencia);
	}

	private void agregarParticipanteGeneral() {

		if (esRepetidoEquipoConDatos()) {
			return;
		}

		if (convocatoriaActual.isMostrarActividadesInvestigador() && esCadenaVacia(funcionInvestigador)) {
			mensajeError(buscarPer4, "Por favor ingrese las funciones del investigador.");
			return;
		}

//		if (esConvSUE2017) {
//			if ("DUCMC".equals(tipoVinculacionId) || "DUDFJC".equals(tipoVinculacionId)
//					|| "DUMNG".equals(tipoVinculacionId) || "DUPN".equals(tipoVinculacionId)) {
//				if (validarAgregadoEquipoSUE(proyectoActual.getListaInvestigadoresProyectoConDatos())) {
//					mensajeError(buscarPer4, "No es permitido agregar más de un profesor director.");
//					return;
//				}
//			}
//		}

		if (mostrarDependencia && esCadenaVacia(investigadorExterno2.getFacultadPregrado())) {
			if (TipoInvestigador.JOVEN_INVESTIGADOR_POSGRADO.equals(tipoVinculacionId)
					|| TipoInvestigador.JOVEN_INVESTIGADOR_EGRESADO.equals(tipoVinculacionId)) {
				mensajeError(buscarPer4,
						"Por favor ingrese la Facultad en la cual realizó el pregrado del investigador.");
			} else {
				mensajeError(buscarPer4, "Por favor ingrese la dependencia a la que esta asociada al investigador.");
			}
			return;
		}

		if (mostrarProgramaAcademico && esCadenaVacia(investigadorExterno2.getProfesion())) {
			if (TipoInvestigador.JOVEN_INVESTIGADOR_POSGRADO.equals(tipoVinculacionId)
					|| TipoInvestigador.JOVEN_INVESTIGADOR_EGRESADO.equals(tipoVinculacionId)) {
				mensajeError(buscarPer4, "Por favor ingrese el título pregrado obtenido.");
			} else {
				mensajeError(buscarPer4, "Por favor ingrese el programa académico.");
			}
			return;
		}

		TipoInvestigador tipoInvestigador = buscarTipoInvestigador(tipoVinculacionId, listaTipoVinculacion2);

		if (tipoInvestigador != null && !esCadenaVacia(tipoInvestigador.getTipo()) && validarTiempoParticipantes()) {
			asignarGrupo = false;
			if (esOtraVinculacion2) {
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

	public boolean validarTiempoParticipantes() {
		boolean ret = true;

		Double duracionTotalM = (proyectoActual.getDuracion() != null && proyectoActual.getDuracion() > 0)
				? proyectoActual.getDuracion()
				: 0.0;
		if (duracionTotalM <= 0) {
			ret = false;
			mensajeError(buscarPer4, "Por favor ingrese primero la duración del proyecto.");
		}

		String sDura = String.valueOf(proyectoActual.getDuracion());
		if (sDura.length() > 2 || proyectoActual.getDuracion() < 0) {
			ret = false;
			mensajeError(buscarPer4, "La duración del proyecto debe ser de máximo 2 dígitos y mayor a 0");
		}

		if (horasParticipante <= 0 || String.valueOf(horasParticipante).length() > 2) {
			ret = false;
			mensajeError(buscarPer4,
					"Tiempo de dedicación al proyecto (Horas semanales) del participante debe ser mayor a 0 y máximo de dos dígitos");
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

	private boolean validarVinculacionInvestigador(String tipoVinculacion, TipoInvestigador tipoInvestigador) {
		if (!esCadenaVacia(tipoInvestigador.getTipoVinculacion())) {
			int encuentra = tipoInvestigador.getTipoVinculacion().indexOf("-" + tipoVinculacion + "-");
			return encuentra > -1;
		}
		return false;
	}

	private void agregarEstudiante(TipoInvestigador tipoInvestigador) {

		IdPersona idEst = new IdPersona(this.documentoCoinvEquipo, this.tipoDocumentoCoInvEquipo.getId());
		Estudiante e = servicioPersona.obtenerEstudiante(idEst);

		if (e != null) { // Se encontró como estudiante

			if (!validarVinculacionInvestigador(e.getPlan().getTipo().toString(), tipoInvestigador)) {
				mensajeError(buscarPer4,
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

				if (per != null) {
					servicioPersona.insertarNuevoInvestigador(nvoinv);
					servicioPersona.insertaInterno(nvoinv);
				} else {
					servicioPersona.guardarInvestigador(nvoinv);
				}
				nuevoInvestigador = servicioPersona.obtenerInvestigador(nvoinv.getId());
			}

			// Agregar participante a la listaparticipante
			InvestigadorProyecto participanteNuevo = new InvestigadorProyecto();
			participanteNuevo.setInvestigador(nuevoInvestigador);
			participanteNuevo.setDedicacionHorasSemana(Short.parseShort(horasParticipante.toString()));
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
			cambiarVinculacion2();
			crearUsuarioInvestigador(tipoInvestigador, nuevoInvestigador);

		} else { // No se encontró como estudiante
			mensajeError(buscarPer4, "El documento ingresado no pertenece a un estudiante.");
		}
	}

	private void crearUsuarioInvestigador(TipoInvestigador tipoInvestigador, Investigador inv) {
		if (tipoInvestigador.isCrearInvestigador()) {
			try {
				PersonaRol pr = new PersonaRol();
				pr.setDocumento(inv.getId().getDocumento());
				pr.setTipoDocumento(inv.getId().getTipoDocumento());
				pr.setNombre("I");
				servicioGeneral.guardarObjeto(pr);
			} catch (Exception ex) {
				System.out.println("Ya tenía el rol investigador");
			}

		}
	}

	private Long calcularValorFuncionario(Integer numeroHoras, Integer numeroMeses, Long valorHora) {

		Long valorTotal = 0L;
		Long duracionAnnio;

		if (numeroMeses > 12) {
			duracionAnnio = numeroMeses / 12L;
			double res = numeroMeses % 12;
			Long resid = (long) (res);

			for (int i = 0; i < duracionAnnio; i++) {
				valorTotal = valorTotal + numeroHoras * valorHora * 4 * 12;
				valorHora += (long) (valorHora * 0.05);
			}

			if (resid > 0) {
				valorTotal = valorTotal + numeroHoras * valorHora * 4 * resid;
			}

		} else {
			valorTotal = numeroHoras * valorHora * 4 * numeroMeses;
		}
		return valorTotal;
	}

	private void agregarFuncionario(TipoInvestigador tipoInvestigador) {

		// búsqueda en investigador interno
		InvestigadorInterno investigadorInterno = servicioPersona
				.obtenerInvestigadorInterno(new IdPersona(documentoCoinvEquipo, tipoDocumentoCoInvEquipo.getId()));

		if (investigadorInterno != null) {

			// Se valida que la vincuación sea correcta con los valores
			// ingresados.
			boolean encuentraInternoVinculacion = false;
			if (investigadorInterno.getTipoVinculacion() != null && validarVinculacionInvestigador(
					investigadorInterno.getTipoVinculacion().getId(), tipoInvestigador)) {
				encuentraInternoVinculacion = true;
			} else if (tipoVinculacionId.equals(TipoInvestigador.PROFESOR_CARRERA_DOCENTE)) {
				mensajeError(buscarPer4, "La persona ingresada no es de de carrera docente.");
			} else if (tipoVinculacionId.equals(TipoInvestigador.ADMINISTRATIVO)) {
				mensajeError(buscarPer4, "La persona ingresada no es administrativa.");
			} else if (tipoVinculacionId.equals(TipoInvestigador.PROFESOR_NO_CARRERA_DOCENTE)) {
				mensajeError(buscarPer4, "La persona adicionada no es un profesor sin carrera docente.");
			}

			if (encuentraInternoVinculacion) {

				Long valorTotal = 0L;
				// Calcular valor dedicación del funcionario si es
				// Docente o Administrativo.
				if (!tipoVinculacionId.equals(TipoInvestigador.PROFESOR_NO_CARRERA_DOCENTE)) {
					valorTotal = calcularValorFuncionario(horasParticipante, tiempoTotalParticipante,
							investigadorInterno.getValorHoraValidado());
				}

				// Agregar participante a la listaparticipante
				InvestigadorProyecto participanteNuevo = new InvestigadorProyecto();
				participanteNuevo.setInvestigador(investigadorInterno);
				participanteNuevo.setDedicacionHorasSemana(Short.parseShort(horasParticipante.toString()));
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
				cambiarVinculacion2();

				// CALCULAR TOTAL PERSONAL Y PROYECTO
//				crearUsuarioInvestigador(tipoInvestigador, investigadorInterno);
			}

		} else {
			mensajeError(buscarPer4, "La persona ingresada no se encuentra registrada.");
		}
	}

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

		if (esCadenaVacia(investigadorExterno2.getNombre1())) {
			verifica = false;
			mensajeErrorInv += "Por favor ingresar el primer nombre.";
		} else {
			if (!validarTextoSinNumeros(investigadorExterno2.getNombre1())) {
				verifica = false;
				mensajeErrorInv += "- El primer nombre no debe contener números ni espacios.";
			}
		}

		if (!esCadenaVacia(investigadorExterno2.getNombre2())) {
			if (!validarTextoSinNumeros(investigadorExterno2.getNombre2())) {
				verifica = false;
				mensajeErrorInv += "- El segundo nombre no debe contener números ni espacios.";
			}
		}

		if (esCadenaVacia(investigadorExterno2.getApellido1())) {
			verifica = false;
			mensajeErrorInv += "- Por favor ingresar el primer apellido.";
		} else {
			if (!validarTextoSinNumeros(investigadorExterno2.getApellido1())) {
				verifica = false;
				mensajeErrorInv += "- El primer apellido no debe contener números ni espacios.";
			}
		}

		if (!esCadenaVacia(investigadorExterno2.getApellido2())) {
			if (!validarTextoSinNumeros(investigadorExterno2.getApellido2())) {
				verifica = false;
				mensajeErrorInv += "- El segundo apellido no debe contener números ni espacios.";
			}
		}

		if (esCadenaVacia(investigadorExterno2.getGenero())) {
			verifica = false;
			mensajeErrorInv += "- Por favor seleccionar el sexo biológico del investigador.";
		}
		if (esCadenaVacia(investigadorExterno2.getEmail())) {
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

		if (investigadorExterno2.getFechaNacimiento() == null) {
			verifica = false;
			mensajeErrorInv += "- Por favor verificar la fecha de nacimiento.";
		} else {
			if (tipoDocumentoCoInvEquipo.getId().equals("T")) {
				int edadDoc = calcularEdad(investigadorExterno2.getFechaNacimiento());
				if (edadDoc > 17) {
					verifica = false;
					mensajeErrorInv += "Por favor verificar el tipo de documento del investigador.";
				}
			} else {
				if (tipoDocumentoCoInvEquipo.getId().equals("C")) {
					int edadDoc = calcularEdad(investigadorExterno2.getFechaNacimiento());
					if (edadDoc < 18) {
						verifica = false;
						mensajeErrorInv += "Por favor verificar el tipo de documento del investigador.";
					}
				}
			}
		}

		if (esCadenaVacia(idPaisNacimiento)) {
			verifica = false;
			mensajeErrorInv += "- Por favor seleccionar el país de nacimiento del investigador.";
		}

		if (siColombia2) {
			if (esCadenaVacia(departamentoActual2.getId())) {
				verifica = false;
				mensajeErrorInv += "- Por favor seleccionar el departamento de nacimiento del investigador.";
			}
		}

		if (ciudadItem2 != null && ciudadItem2.length > 0 && esCadenaVacia(ciudadActual2.getId())) {
			verifica = false;
			mensajeErrorInv += "- Por favor seleccionar la ciudad de nacimiento del investigador.";
		}

		if (esCadenaVacia(investigadorExterno2.getTelefono())) {
			verifica = false;
			mensajeErrorInv += "- Por favor ingrese el número de teléfono del investigador.";
		} else {
			if (investigadorExterno2.getTelefono().length() < 7 || investigadorExterno2.getTelefono().length() > 20) {
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
			if (esCadenaVacia(investigadorExterno2.getTelefono())) {
				verifica = false;
				mensajeErrorInv += "- Por favor verificar el télefono fijo.";
			}
			if (esCadenaVacia(investigadorExterno2.getNacionalidad())) {
				verifica = false;
				mensajeErrorInv += "- Por favor verificar la nacionalidad.";
			}
			if (investigadorExterno2.getFechaNacimiento() == null) {
				verifica = false;
				mensajeErrorInv += "- Por favor verificar la fecha de nacimiento.";
			} else {
				// int ed =
				// calcularEdad(investigadorExterno2.getFechaNacimiento());
				// int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				// if (ed >= 29) {
				// mensajeError("- Tenga en cuenta que el joven investigador
				// debe tener 28 años a 31 de Diciembre del " + currentYear);
				// }
			}
			if (esCadenaVacia(investigadorExterno2.getProfesion())) {
				verifica = false;
				mensajeErrorInv += "- Por favor verificar el título de pregrado.";
			}
			if (investigadorExterno2.getFechaTituloPregrado() == null) {
				verifica = false;
				mensajeErrorInv += "- Por favor verificar la fecha del título de pregrado.";
			}

			// else if
			// (!investigadorExterno2.validarFechaAnios(investigadorExterno2.getFechaTituloPregrado(),
			// 3)) {
			// verifica = false;
			// mensajeErrorInv += "- La fecha de grado no debe superar tres años
			// de antigüedad.";
			// }

			if (esCadenaVacia(investigadorExterno2.getPromedioPregrado())) {
				verifica = false;
				mensajeErrorInv += "- Por favor verificar el promedio de pregrado.";
			} else {
				// try {
				// float prom =
				// Float.parseFloat(investigadorExterno2.getPromedioPregrado());
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
			if (esCadenaVacia(investigadorExterno2.getResumenHojaDeVida())) {
				verifica = false;
				mensajeErrorInv += "- Por favor ingrese la universidad en la cual realizó los estudios de pregrado.";
			}
			if (esCadenaVacia(investigadorExterno2.getFacultadPregrado())) {
				verifica = false;
				mensajeErrorInv += "- Por favor ingrese la acultad en la cual realizó el pregrado.";
			}
		}
		if (!verifica) {
			mensajeError(buscarPer4, mensajeErrorInv);
		}

		return verifica;
	}

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
					investigadorExterno2.setId(idPersona);

					// estadocivil
					EstadoCivil ec = new EstadoCivil();
					ec.setId(idTipoEstadoCivil);
					investigadorExterno2.setEstadoCivil(ec);
					investigadorExterno2.setPaisOrigen(idPaisNacimiento);

					if (ciudadActual2 != null) {
						investigadorExterno2.setCiudadNacimiento(ciudadActual2);
					}
					servicioPersona.insertarNuevaPersona(investigadorExterno2);
					nuevaPersona = servicioPersona.obtenerPersona(investigadorExterno2.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				investigadorExterno2.validarActualizacionNombres(nuevaPersona);
				nuevaPersona = investigadorExterno2;
				// estadocivil
				EstadoCivil ec = new EstadoCivil();
				ec.setId(idTipoEstadoCivil);
				nuevaPersona.setEstadoCivil(ec);
				nuevaPersona.setPaisOrigen(idPaisNacimiento);

				if (ciudadActual2 != null) {
					nuevaPersona.setCiudadNacimiento(ciudadActual2);
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
				// area y subarea cientifica y tecnológica
				investigador.setAreaOcde(areaCienciaInv);
				investigador.setSubareaOcde(subAreaCienciaInv);
				investigador.setEsFuncionario("N");
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
			Institucion institucionExterno = new Institucion();
			if (institucionSeleccionada != null) {
				institucionExterno.setId(institucionSeleccionada);
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
					nuevoInvestigadorExterno.setInstitucion(institucionExterno);
					nuevoInvestigadorExterno.setTipoFormacion(tf);
					servicioPersona.insertarNuevoInvestigadorExterno(nuevoInvestigadorExterno);
				} else {
					nuevoInvestigadorExterno.setInstitucion(institucionExterno);
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
			participanteExternoNuevo.setDedicacionHorasSemana(Short.parseShort(horasParticipante.toString()));
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
				participanteExternoNuevo.setInstitucion(institucionExterno);
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
			investigadorExterno2 = new Persona();
			cambiarVinculacion2();
			if (tipoInvestigador.getId().equals("ADL")) {
				crearUsuarioAsistenteLider(tipoInvestigador, investigador);
			} else {
				crearUsuarioInvestigador(tipoInvestigador, investigador);
			}

		} else {
			mensajeError(buscarPer4,
					"La persona indicada no se encuentra registrada. Por favor diligencie los campos correspondientes y a continuación haga clic nuevamente sobre el botón 'Agregar'.");
		}
	}

	private void crearUsuarioAsistenteLider(TipoInvestigador tipoInvestigador, Investigador inv) {
		if (tipoInvestigador.isCrearInvestigador()) {
			try {
				PersonaRol pr = new PersonaRol();
				pr.setDocumento(inv.getId().getDocumento());
				pr.setTipoDocumento(inv.getId().getTipoDocumento());
				pr.setNombre("AL");
				servicioGeneral.guardarObjeto(pr);
			} catch (Exception ex) {
				System.out.println("Ya tenía el rol Asistente Líder");
			}

		}
	}

	public void calcularValorDocente() {

		Long valor = 0L;
		Long valorAdm = 0L;

		if (!esListaVacia(proyectoActual.getListaInvestigadorPrincipal())) {
			InvestigadorProyecto inv = proyectoActual.getListaInvestigadorPrincipal().get(0);
			if (!esNulo(inv.getValorPagar()))
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

	public void calcularTotalesFicha() {

		// siempre, asi no hayan fuentes para que coloque 0.

		Long contrapartidaEfectivo = 0L;
		Long contrapartidaEspecieTotal = 0L;
		Long contrapartidaExternaEspecieTotal = 0L;
//		valorFinanciadoInvestig = 0L;
		Long valorTotalFuentesFinanciacion = 0L;

		// inicio
		if (!esListaVacia(proyectoActual.getListaFuentesFinancacionFicha())) {

			Iterator<Financiacion> i = proyectoActual.getListaFuentesFinancacionFicha().iterator();
			while (i.hasNext()) {

				Financiacion financ = i.next();

				Long[] totales = new Long[] { null, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L };
				Long contrapartidaEspecieFuente = 0L;

				TreeNode tree = financ.getTreeFinanciacion();
				if (tree != null) {
					List<TreeNode> treeChildPpal = tree.getChildren();

					for (int k = 0; k < treeChildPpal.size(); k++) {

						List<TreeNode> treeChild = treeChildPpal.get(k).getChildren();
						GastoFM rubroPadre = (GastoFM) treeChildPpal.get(k).getData();

						// Nombre de los rubros padre
						if (rubroPadre.getIdRubro() != 146) {

							Long totalxAnio1C = 0L;
							Long totalxAnio2 = 0L;
							Long totalxAnio3 = 0L;
							Long totalxAnio4 = 0L;
							Long totalxAnio5 = 0L;
							Long totalxAnio6 = 0L;

							for (int j = 0; j < treeChild.size(); j++) {

								TreeNode treeActual = treeChild.get(j);
								GastoFM gastofm = (GastoFM) treeActual.getData();

								// Se actualiza la información de los rubros

								gastofm.setTotal(gastofm.getSumaAnios());

								Gasto gastoNuevo = gastofm.getGasto();
								if (gastofm.getSumaAnios() > 0L || gastoNuevo != null) {
									if (gastoNuevo == null) {
										gastoNuevo = new Gasto();
										gastoNuevo.setCantidad(1);
										gastoNuevo.setVigencia(1);
										if (gastofm.isEsContrapartida()) {
											gastoNuevo.setDescripcion("Contrapartida");
										} else {
											gastoNuevo.setDescripcion("Gasto");
										}
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

									gastoNuevo.setValor(gastofm.getAnio1());
									gastoNuevo.setValor2(gastofm.getAnio2());
									gastoNuevo.setValor3(gastofm.getAnio3());
									gastoNuevo.setValor4(gastofm.getAnio4());
									gastoNuevo.setValor5(gastofm.getAnio5());
									gastoNuevo.setValor6(gastofm.getAnio6());
								}

								// Se suman a los totales acumulados por
								// anio
								totalxAnio1C += gastofm.getAnio1();
								totalxAnio2 += gastofm.getAnio2();
								totalxAnio3 += gastofm.getAnio3();
								totalxAnio4 += gastofm.getAnio4();
								totalxAnio5 += gastofm.getAnio5();
								totalxAnio6 += gastofm.getAnio6();

							}

							// Se consolidan los totales.
							rubroPadre.setAnio1(totalxAnio1C);
							rubroPadre.setAnio2(totalxAnio2);
							rubroPadre.setAnio3(totalxAnio3);
							rubroPadre.setAnio4(totalxAnio4);
							rubroPadre.setAnio5(totalxAnio5);
							rubroPadre.setAnio6(totalxAnio6);
							rubroPadre.setTotal(rubroPadre.getSumaAnios());// Subtotal

							totales[1] += totalxAnio1C;
							totales[2] += totalxAnio2;
							totales[3] += totalxAnio3;
							totales[4] += totalxAnio4;
							totales[5] += totalxAnio5;
							totales[6] += totalxAnio6;

						}
					}

					// Se actualizan los totales.
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

				if (financ.getFuente().getInternaExterna().equals("E")
						|| financ.getFuente().getInternaExterna().equals("A")) {

					contrapartidaExternaEspecieTotal += contrapartidaEspecieFuente;
//					valorFinanciadoInvestig += financ.getValor() - contrapartidaExternaEspecieTotal;
//					proyectoActual.setContrapartidaExternaEspecieTotal(contrapartidaExternaEspecieTotal);

				} else {

					// Contrapartida especie en proyecto.
					contrapartidaEspecieTotal += contrapartidaEspecieFuente;
//					proyectoActual.setContrapartidaEspecieTotal(contrapartidaEspecieTotal);

					// Contrapartida efectivo en proyecto.
					contrapartidaEfectivo += (financ.getValor() - contrapartidaEspecieFuente);
				}

				// Valores totales proyecto
				valorTotalFuentesFinanciacion += financ.getValor();
			}
		}

//		valorTotalTotalProyecto = valorTotalFuentesFinanciacion + proyectoActual.getValorAdministrativoTotal()
//				+ proyectoActual.getValorPersonalTotal() + proyectoActual.getValorEntidadesParticipantesCalculado()
//				+ proyectoActual.getValorEspecieCalculado();

		// Valor especie entidades
//		proyectoActual.setCostoFacultad(proyectoActual.getValorEspecieCalculado());
//		proyectoActual.setContrapartidaEfectivo(contrapartidaEfectivo);
		// valor costos indirectos
//		if (esConvExtSol2018) {
//			proyectoActual.setValorTotalCostosIndirectos((Math.round(proyectoActual.getContrapartidaEfectivo() * 1.05))
//					- (proyectoActual.getContrapartidaEfectivo()));
//		}
	}

	protected boolean esRepetidoEquipoConDatos() {
		// Validar que no se encuentre en participantes
		if (validarAgregadoEquipo(documentoCoinvEquipo, tipoDocumentoCoInvEquipo.getId(),
				proyectoActual.getListaInvestigadoresProyectoConDatos())) {
			mensajeError(buscarPer4, "La persona ingresada ya se encuentra vinculada al proyecto.");
			return true;
		}

		if (validarAgregadoEquipo(documentoCoinvEquipo, tipoDocumentoCoInvEquipo.getId(),
				proyectoActual.getListaInvestigadorPrincipal())) {
			mensajeError(buscarPer4, "El director del proyecto no se debe agregar como equipo de trabajo.");
			return true;
		}

		// Validar que no se encuentre como Director (persona en sesión)
		if ((tipoDocumentoCoInv.getId().equals(tipoDocumentoCoInvEquipo.getId()))
				&& documentoCoinv.equals(documentoCoinvEquipo)) {
			mensajeError(buscarPer4, "El director del proyecto no se debe agregar como equipo de trabajo.");
			return true;
		}
		return false;
	}

	// FIN INTEGRANTES CAZ

	public void asignarValoresListas() {

//		selectedPoblacionObjetivo = new String[4];

		if (!esListaVacia(proyectoActual.getListaPoblacionObjetivo())) {
			for (int i = 0; i < proyectoActual.getListaPoblacionObjetivo().size(); i++) {
				PoblacionObjetivoEvento poblacionObj = (PoblacionObjetivoEvento) proyectoActual
						.getListaPoblacionObjetivo().get(i);
				selectedPoblacionObjetivo[i] = poblacionObj.getPoblacionObjetivo().getIdentificador().getTipo();
//                if (caract.getCaracter().getIdentificador().getTipo().equals(DominioDetalle.OTRO_CARACTER_OBRA)) {
//                    otroCaracter = caract.getObservaciones();
//                }
			}
		}
	}

	public void actualizarListaPoblacionObjetivo() {
		List<PoblacionObjetivoEvento> listaAlmacenar = new ArrayList<PoblacionObjetivoEvento>();
		List<String> listaOficial = Arrays.asList(cleanArrayString(selectedPoblacionObjetivo));

		for (int i = 0; i < listaOficial.size(); i++) {
			boolean existe = false;
			for (int a = 0; a < proyectoActual.getListaPoblacionObjetivo().size(); a++) {
				if (listaOficial.get(i).equals(proyectoActual.getListaPoblacionObjetivo().get(a).getPoblacionObjetivo()
						.getIdentificador().getTipo())) {
					existe = true;
//                    if (listaOficial.get(i).equals(DominioDetalle.OTRO_CARACTER_OBRA)) {
//                    	proyectoActual.getListaPoblacionObjetivo().get(a).setObservaciones(otroCaracter);
//                    }
					listaAlmacenar.add(proyectoActual.getListaPoblacionObjetivo().get(a));
					proyectoActual.borrarPoblacionObjetivo(proyectoActual.getListaPoblacionObjetivo().get(a));
					break;
				}
			}
			if (!existe) {
				PoblacionObjetivoEvento caracterNuevo = new PoblacionObjetivoEvento();
				DominioDetalle caracterDominioDetalle = new DominioDetalle();
				IdDominioDetalle id = new IdDominioDetalle();
				id.setId(Dominio.DOMINIO_POBLACION_OBJETIVO_EVENTO);
				id.setTipo(listaOficial.get(i));
				caracterDominioDetalle.setIdentificador(id);
				caracterNuevo.setPoblacionObjetivo(caracterDominioDetalle);
				caracterNuevo.setProyecto(proyectoActual);
//                if (listaOficial.get(i).equals(DominioDetalle.OTRO_CARACTER_OBRA)) {
//                    caracterNuevo.setObservaciones(otroCaracter);
//                }
				listaAlmacenar.add(caracterNuevo);
			}
		}
		proyectoActual.borrarListaPoblacionObjetivo(proyectoActual.getListaPoblacionObjetivo());
		proyectoActual.setListaPoblacionObjetivo(listaAlmacenar);
	}

	public void cambioPoblacionObjetivo() {
		System.out.println("Cambio :");
	}

	public Boolean getMostrarPoblacionExterno() {
//		String[] selectedPoblacionObjetivo;

		if (!esNulo(selectedPoblacionObjetivo)) {
			for (String poblacionObjetivo : selectedPoblacionObjetivo) {
				if (poblacionObjetivo != null && poblacionObjetivo.equals("4")) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Metodo para cargar todas las fuentes externas.
	 */
	public void cargarFuentesFinanciacionExternas() {
		String hql = "select ff from FuenteFinanciacion ff where ff.internaExterna like 'E' and ff.descripcion not like '%CODIGO%' and ff.observaciones = 'ENT_REG_PRY' and ff.quipu = 'S' order by ff.descripcion)";
		List<FuenteFinanciacion> lista = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class, hql);

		fuentesExternasItem = new SelectItem[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			FuenteFinanciacion d = (FuenteFinanciacion) lista.get(i);
			String nombre = d.getDescripcion().toUpperCase();
			fuentesExternasItem[i] = new SelectItem(d.getId(), nombre);
		}
	}

	public void agregarEntidadCoejecutora() {

		if (esCadenaVacia(entidadCoejecutora)) {
			mensajeError("Seleccione la institución organizadora.");
			return;
		}

		try {

			FuenteFinanciacion fuenteFinanciacionSeleccionada = (FuenteFinanciacion) servicioGeneral
					.obtenerObjeto(new FuenteFinanciacion(), entidadCoejecutora);

			boolean existeEntidadParticipante = true;
			if (proyectoActual.isExisteEntidadParticipante(entidadCoejecutora)) {
				existeEntidadParticipante = false;
				mensajeError("La entidad participante ya se encuentra vinculada al proyecto");
			}

			if (existeEntidadParticipante) {

				TipoRubro tipoRubro = new TipoRubro();
				tipoRubro.setId(TipoRubro.ADMINISTRACION_PROYECTO);

				Financiacion nuevaFinanciacion = new Financiacion();
				nuevaFinanciacion.setFuente(fuenteFinanciacionSeleccionada);
				nuevaFinanciacion.setValorPendiente(0L);
				nuevaFinanciacion.setValor(0L);
				nuevaFinanciacion.setValorEspecie(0L);
				nuevaFinanciacion.setTipoEntidad(FuenteFinanciacion.ENTIDAD_PARTICIPANTE);

				proyectoActual.adicionarFinanciacion(nuevaFinanciacion);

				entidadCoejecutora = null;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Eliminar entidad coejecutora.
	 */
	public void eliminarEntidadCoejecutora() {

		proyectoActual.eliminarFuente(entidadEliminar);

	}

	public void agregarInsitucionOrganizadora() {

		if (esCadenaVacia(entidadCoejecutora)) {
			mensajeError("Seleccione la institución organizadora.");
			return;
		}

		try {
			FuenteFinanciacion fuenteFinanciacionSeleccionada = (FuenteFinanciacion) servicioGeneral
					.obtenerObjeto(new FuenteFinanciacion(), entidadCoejecutora);

			if (proyectoActual.isExisteEntidadParticipante(entidadCoejecutora)) {
				mensajeError("La entidad participante ya se encuentra vinculada al proyecto");
			} else {
				Financiacion nuevaFinanciacion = new Financiacion();
				nuevaFinanciacion.setFuente(fuenteFinanciacionSeleccionada);
				nuevaFinanciacion.setValorPendiente(0L);
				nuevaFinanciacion.setValor(valorAporteEfectivoOrganizador);
				nuevaFinanciacion.setValorEspecie(valorAporteEspecieOrganizador);
				nuevaFinanciacion.setTipoEntidad(FuenteFinanciacion.ENTIDAD_PARTICIPANTE);
				proyectoActual.adicionarFinanciacion(nuevaFinanciacion);
				entidadCoejecutora = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo para cargar el objetivo de desarrollo sostenible principal.
	 */
	private void cargarObjetivoDesarrolloSosteniblePrincipal() {
		List<ValoresListasProyecto> listaObjetivoDesarrolloSosteniblePrincipal = proyectoActual
				.getObjetivoDesarrolloSostenibleTipo("OBJETIVO_DESARROLLO_SOSTENIBLE_PRINCIPAL");

		if (!esListaVacia(listaObjetivoDesarrolloSosteniblePrincipal)) {
			ValoresListasProyecto objetivoDesarrolloSosteniblePrincipalGuardad = listaObjetivoDesarrolloSosteniblePrincipal
					.get(0);
			objetivoDesarrolloSosteniblePrimario = objetivoDesarrolloSosteniblePrincipalGuardad.getValor();
		}
	}

	/**
	 * Cargar areas tematicas secundarias.
	 */
	private void cargarObjetivoDesarrolloSostenibleSecundarios() {
		// valoresObjetivosDesarrolloSostenible =
		// servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
		// "select e from ValoresListasProyecto e where e.proyecto.id = " +
		// proyectoActual.getId() +
		// " and e.descripcion = 'OBJETIVO_DESARROLLO_SOSTENIBLE_SECUNDARIO' and e.tipo
		// = '"
		// + DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE + "'");
		valoresObjetivosDesarrolloSostenible = proyectoActual
				.getObjetivoDesarrolloSostenibleTipo("OBJETIVO_DESARROLLO_SOSTENIBLE_SECUNDARIO");
		if (valoresObjetivosDesarrolloSostenible == null) {
			valoresObjetivosDesarrolloSostenible = new ArrayList<ValoresListasProyecto>();
		}
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
		Departamento depto = (Departamento) listaDepartamentos.get(0);
		String hql = "select #id e.id, #nombre e.nombre from Ciudad e where e.departamento.id like ('" + depto.getId()
				+ "%') and e.sigla is not null order by e.nombre asc";
		listaCiudades = servicioGeneral.obtenerObjetosLimitado(Ciudad.class, hql);
		ciudadItem = new SelectItem[listaCiudades.size()];
		for (int i = 0; i < listaCiudades.size(); i++) {
			Ciudad ci = (Ciudad) listaCiudades.get(i);
			ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	private void obtenerListaCiudades2() {
		Departamento depto = (Departamento) listaDepartamentos.get(0);
		String hql = "select #id e.id, #nombre e.nombre from Ciudad e where e.departamento.id like ('" + depto.getId()
				+ "%') and e.sigla is not null order by e.nombre asc";
		listaCiudades = servicioGeneral.obtenerObjetosLimitado(Ciudad.class, hql);
		ciudadItem2 = new SelectItem[listaCiudades.size()];
		for (int i = 0; i < listaCiudades.size(); i++) {
			Ciudad ci = (Ciudad) listaCiudades.get(i);
			ciudadItem2[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
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

	// DEFINICION DE FUNCIONES MISCELANEAS
	private Departamento buscarDepartamento(String id) {
		// BUSCA UN DEPARTAMENTO DE ACUERDO A SU ID
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

	private Ciudad buscarCiudad(String id) {
		// BUSCA UNA CIUDAD DE ACUERDO A SU ID
		Ciudad c = new Ciudad();
		int i = 0;
		while (i < listaCiudades.size()) {
			c = (Ciudad) listaCiudades.get(i);
			if (id.equals(c.getId()))
				break;
			i = i + 1;
		}
		return c;
	}

	public boolean isValidarBotonGuardar() {
		return validarBotonGuardarConvocatoria(proyectoActual.getId());
	}

	public void ingresarFuenteUniversidad(FuenteFinanciacion ff,
			ModalidadFuenteFinanciacion modalidadFuenteFinanciacion) {

		Financiacion f = new Financiacion();
		f.setProyecto(proyectoActual);
		f.setFuente(ff);
		f.setValor(0L);
		f.setRol("ROL_FT_FIN");
		f.setModalidadFuenteFinanciacion(modalidadFuenteFinanciacion);
		proyectoActual.getFinanciaciones().add(f);

	}

	public Financiacion calcularValorFinanciacion(Financiacion financiacion) {
		if (financiacion != null) {
			Long valorTotal = 0L;
			List gastosList = financiacion.getGastosListados();
			if (gastosList != null && gastosList.size() > 0) {
				for (Object gasto : gastosList) {
					Gasto gast = (Gasto) gasto;
					valorTotal += gast.getValor();
				}
			}
			financiacion.setValor(valorTotal);
			return financiacion;
		} else
			return null;
	}

	private FuenteFinanciacion buscarFuente(String id) {
		// BUSCA UNA FUENTE DE FINANCIACION DE ACUERDO AL ID
		int i = 0;
		FuenteFinanciacion ff = new FuenteFinanciacion();
		while (i < listaAuxiliarFuentes.size()) {
			ff = (FuenteFinanciacion) listaAuxiliarFuentes.get(i);
			if (id.equals(ff.getId()))
				break;
			else
				ff = null;
			i = i + 1;
		}
		return ff;
	}

	public void cargarFuentesFinanciacion() {
		Modalidad modalidad = proyectoActual.getModalidad();
		List listaFinanciacionesDeConvocatoria = servicioModalidad.listaModFuenteFinXModalidad(modalidad.getId());

		if (listaFinanciacionesDeConvocatoria.size() > 0) {

			for (Iterator it = listaFinanciacionesDeConvocatoria.iterator(); it.hasNext();) {
				ModalidadFuenteFinanciacion mff = (ModalidadFuenteFinanciacion) it.next();
				FuenteFinanciacion ff = (FuenteFinanciacion) (servicioGeneral.obtenerObjeto(new FuenteFinanciacion(),
						mff.getFuenteFinanciacion().getId()));

				if (ff.getInternaExterna() != null) {
					if (ff.getInternaExterna().equalsIgnoreCase(FuenteFinanciacion.interna))
						listaFuentesInternas.add(ff);
					else
						listaFuentesExternas.add(ff);
				}
			}

			listaAuxiliarFuentes.addAll(listaFuentesInternas);
			listaAuxiliarFuentes.addAll(listaFuentesExternas);

		}
	}

	public void cargarListas() {

		poblacionObjetivoEventoItem = crearListaItemDominioDetalle(DOMINIO_POBLACION_OBJETIVO_EVENTO);
		poblacionObjetivoEventoGrupoItem = crearListaItemDominioDetalle(Dominio.DOMINIO_GRUPO_DIRIGE_EVENTOS);
		poblacionObjetivoEventoPoblacionItem = crearListaItemDominioDetalle(Dominio.DOMINIO_POBLACION_DIRIGE_EVENTOS);
		metodologiaEventoItem = crearListaItemDominioDetalle(DOMINIO_METODOLOGIA_EVENTO);

		dependenciasUN = new ArrayList<Dependencia>();
		dependenciasUN = servicioGeneral.obtenerObjetos("select e from Dependencia e where e.estado = 'A'");
		dependenciaItem = new SelectItem[dependenciasUN.size()];
		for (int i = 0; i < dependenciasUN.size(); i++) {
			Dependencia dd = (Dependencia) dependenciasUN.get(i);
			dependenciaItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}

		listaCategorias = new ArrayList<DominioDetalle>();
		listaCategorias = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_CATEGORIA + "' order by dd.descripcion");
		categoriaItems = new SelectItem[listaCategorias.size()];
		for (int i = 0; i < listaCategorias.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaCategorias.get(i);
			categoriaItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}

		listaRolUniversidad = new ArrayList<DominioDetalle>();
		listaRolUniversidad = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_ROL + "' order by dd.descripcion");
		rolUniversidadItems = new SelectItem[listaRolUniversidad.size()];
		for (int i = 0; i < listaRolUniversidad.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaRolUniversidad.get(i);
			rolUniversidadItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}

		listaMecanismoSolicitud = new ArrayList<DominioDetalle>();
		listaMecanismoSolicitud = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_MECANISMO + "' order by dd.descripcion");
		mecanismoSolicitudItems = new SelectItem[listaMecanismoSolicitud.size()];
		for (int i = 0; i < listaMecanismoSolicitud.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaMecanismoSolicitud.get(i);
			mecanismoSolicitudItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}

		listaTipoEvento = new ArrayList<DominioDetalle>();

		if (esConvMoots2019_M1)
			DOMINIO_TIPO_EVENTO = "TEVENTO_MOOTS_2019_M1";

		if (esConvMoots2019_M2)
			DOMINIO_TIPO_EVENTO = "TEVENTO_MOOTS_2019_M2";

		if (esConvMoots2021_M1)
			DOMINIO_TIPO_EVENTO = "TEVENTO_MOOTS_2021_M1";

		if (esConvMoots2021_M2)
			DOMINIO_TIPO_EVENTO = "TEVENTO_MOOTS_2021_M2";

		listaTipoEvento = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_TIPO_EVENTO + "' order by dd.descripcion");

		tipoEventoItems = new SelectItem[listaTipoEvento.size()];
		for (int i = 0; i < listaTipoEvento.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaTipoEvento.get(i);
			tipoEventoItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}

		listaObjetivos = new ArrayList<ObjetivoEspecifico>();
		listaResultados = new ArrayList<ResultadoProyecto>();

		// cargarObjetivoSocioEconomico lmom
		String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
				+ DOMINIO_OBJETIVO_SOCIO_EC + "' order by dd.descripcion";
		listaObjSocioeconomico = servicioGeneral.obtenerObjetos(consulta);

		if (listaObjSocioeconomico.size() > 0) {
			objSocioeconomicoItems = new SelectItem[listaObjSocioeconomico.size()];
			for (int i = 0; i < listaObjSocioeconomico.size(); i++) {
				DominioDetalle dominio = (DominioDetalle) listaObjSocioeconomico.get(i);
				objSocioeconomicoItems[i] = new SelectItem(dominio.getIdentificador().getTipo(),
						dominio.getDescripcion());
			}
		} else {
			objSocioeconomicoItems = new SelectItem[1];
			objSocioeconomicoItems[0] = new SelectItem("0", " - ");
		}

		// cargarAreaCiencia lmom
		String consulta1 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
				+ DOMINIO_AREA_CIENCIA + "'  order by dd.descripcion";
		listaAreaCiencia = servicioGeneral.obtenerObjetos(consulta1);

		if (listaAreaCiencia.size() > 0) {
			areaCienciaItems = new SelectItem[listaAreaCiencia.size()];
			for (int i = 0; i < listaAreaCiencia.size(); i++) {
				DominioDetalle dominio = (DominioDetalle) listaAreaCiencia.get(i);
				areaCienciaItems[i] = new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion());
			}
		} else {
			areaCienciaItems = new SelectItem[1];
			areaCienciaItems[0] = new SelectItem("0", " - ");

		}

		String tipoMod = "FM";

		String consulta2 = "select ti from TipoInvestigador ti where ti.tipoModalidad like '%" + tipoMod
				+ "%' order by ti.nombre desc";
		listaTipoVinculacion = servicioGeneral.obtenerObjetos(consulta2);
		int tamLista = listaTipoVinculacion.size();
		if (tamLista > 0) {

			List lista = servicioGeneral.obtenerObjetoXID("TipoInvestigador", "AL");
			TipoInvestigador tipoEstudLider = (TipoInvestigador) lista.get(0);

			if (esPosgrado1_2) {
				if (listaTipoVinculacion.contains(tipoEstudLider)) {
					listaTipoVinculacion.remove(tipoEstudLider);
					tamLista = tamLista - 1;
				}
			}
			if (!convocatoriaActual.getId().equals(416L)) {
				lista = servicioGeneral.obtenerObjetoXID("TipoInvestigador", "ESEX");
				tipoEstudLider = (TipoInvestigador) lista.get(0);
				if (listaTipoVinculacion.contains(tipoEstudLider)) {
					listaTipoVinculacion.remove(tipoEstudLider);
					tamLista = tamLista - 1;
				}
			}

			tipoVinculacionItems = new SelectItem[tamLista];

			for (int i = 0; i < listaTipoVinculacion.size(); i++) {

				TipoInvestigador dominio = (TipoInvestigador) listaTipoVinculacion.get(i);

				tipoVinculacionItems[i] = new SelectItem(dominio.getId(), dominio.getNombre());
				// }

			}
		} else {
			tipoVinculacionItems = new SelectItem[1];
			tipoVinculacionItems[0] = new SelectItem("0", " - ");

		}

		// cargarTiposDocumento lmom
		listaTipoDocumento = servicioGeneral.obtenerListaObjetos(TipoDocumento.class);

		TipoDocumento tdAux = listaTipoDocumento.get(0);

		tipoDocumentoCoInv.setId(tdAux.getId());
		tipoDocumentoCoInv.setNombre(tdAux.getNombre());

		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];

		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}

		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}

		List<DominioDetalle> listaObjetivosDesarrolloSostenible = servicioGeneral
				.obtenerDominioDetalle(DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE);
		objetivosDesarrolloSostenibleItems = crearListaItems(listaObjetivosDesarrolloSostenible);

		cargarFuentesFinanciacionExternas();

		List<Pais> listaPaises;
		listaPaises = cargarPaises(true);

		listaPaisesItem = new SelectItem[listaPaises.size()];
		for (int i = 0; i < listaPaises.size(); i++) {
			Pais p = (Pais) listaPaises.get(i);
			listaPaisesItem[i] = new SelectItem(p.getId(), p.getNombre());
		}

		tiposFuenteItem = new SelectItem[2];
		tiposFuenteItem[0] = new SelectItem(new Integer(1), "Interna");
		tiposFuenteItem[1] = new SelectItem(new Integer(2), "Externa");

	}

	// lmom
	public void cambiarVinculacion() {

		if (selItems.toString().equals("AD") || selItems.toString().equals("CT") || selItems.toString().equals("EGES")
				|| selItems.toString().equals("PEES") || selItems.toString().equals("CNE")
				|| selItems.toString().equals("PEX") || selItems.toString().equals("ESEX")
				|| selItems.toString().equals("ADP")) {
			esOtraVinculacion = true;
		} else {
			esOtraVinculacion = false;
		}

	}

	// lmom
	public void agregarDirector() {

		boolean encuentraParticipante = false;

		if (documentoCoinv != null && !documentoCoinv.equals("") && !documentoCoinv.equals(" ")) {

			if (listaParticipantes.size() > 0) {

				for (int i = 0; i < listaParticipantes.size(); i++) {
					InvestigadorProyecto invpry = listaParticipantes.get(i);
					if (invpry.getInvestigador().getId().getDocumento().equals(documentoCoinv)
							&& invpry.getInvestigador().getId().getTipoDocumento().equals(tipoDocumentoCoInv.getId())) {

						encuentraParticipante = true;
						break;
					} else {

					}
				}

			}

			if (!encuentraParticipante) {

				if (listaDirector.size() < 1) {

					try {

						InvestigadorInterno investigadorInterno = servicioPersona.obtenerInvestigadorInterno(
								new IdPersona(this.documentoCoinv, this.tipoDocumentoCoInv.getId()));

						if (investigadorInterno != null) {

							double duracionTotalMeses = proyectoActual.getDuracion();
							double duracionSemanas = 0.0;
							double duracionDias = 0.0;
							double duracionHoras = 0.0;
							double duracionAnnos = 0.0;
							long valorTotal = 0;

							// Valor hora del docente por dedicación al proyecto
							Long valorHora = investigadorInterno.getValorHora();
							valorTotal = (long) (this.horasDirector * valorHora * 4 * duracionTotalMeses);

							if (this.proyectoActual.getValorPersonalTotal() == null
									|| this.proyectoActual.getValorPersonalTotal().equals("0.0")
									|| this.proyectoActual.getValorPersonalTotal().equals("")) {
								this.proyectoActual.setValorPersonalTotal(0L);
							}

							valorPersonalTotal = this.proyectoActual.getValorPersonalTotal() + valorTotal;
							this.proyectoActual.setValorPersonalTotal(valorPersonalTotal);

							// Agregar director a la listaDirector
							InvestigadorProyecto director = new InvestigadorProyecto();
							director.setInvestigador(investigadorInterno);
							director.setDedicacionHorasSemana(Short.parseShort(horasDirector.toString()));
							director.setFuncion("Director");
							director.setProyecto(proyectoActual);
							TipoInvestigador ti = new TipoInvestigador();
							ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), "P");
							director.setTipo(ti);
							director.setValorPagar(valorTotal);

							listaDirector.add(director);

						} else {

						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"No se puede registrar más de un director", "No se puede registrar más de un director"));

				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"El investigador ya se encuentra registrado en el equipo de trabajo",
								"El investigador ya se encuentra registrado en el equipo de trabajo"));

			}

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor ingrese el número de identificación del director a registrar",
							"Por favor ingrese el número de identificación del director a registrar"));

		}

	}

	public void agregarParticipante() {

		errorEstudiantes = "";
		Investigador nuevoInvestigador = new Investigador();
		boolean encuentraParticipante = false;

		if (documentoCoinv2 != null && !documentoCoinv2.equals("") && !documentoCoinv2.equals(" ")) {

			if (listaParticipantes.size() > 0) {

				for (int i = 0; i < listaParticipantes.size(); i++) {
					InvestigadorProyecto invpry = listaParticipantes.get(i);
					if (invpry.getInvestigador().getId().getDocumento().equals(documentoCoinv2) && invpry
							.getInvestigador().getId().getTipoDocumento().equals(tipoDocumentoCoInv2.getId())) {

						encuentraParticipante = true;
						break;
					} else {

					}
				}

			}

			if (!encuentraParticipante && listaDirector.size() > 0) {

				for (int i = 0; i < listaDirector.size(); i++) {
					InvestigadorProyecto invpry = listaDirector.get(i);
					if (invpry.getInvestigador().getId().getDocumento().equals(documentoCoinv2) && invpry
							.getInvestigador().getId().getTipoDocumento().equals(tipoDocumentoCoInv2.getId())) {

						encuentraParticipante = true;
						break;
					} else {

					}
				}

			}

			if (!encuentraParticipante) {

				try {

					Long documento = Long.parseLong(documentoCoinv2);

					double duracionTotalMeses = tiempoTotalParticipante;
					long valorTotal = 0;
					horasParticipante = horasParticipante != null ? this.horasParticipante : 0;

					if (this.proyectoActual.getValorPersonalTotal() == null
							|| this.proyectoActual.getValorPersonalTotal().equals

							("0.0") || this.proyectoActual.getValorPersonalTotal().equals("")) {
						this.proyectoActual.setValorPersonalTotal(0L);
					}

					if (selItems.toString().equals("PCD") || selItems.toString().equals("PSCD")) { // si
						// es
						// docente

						InvestigadorInterno investigadorInterno = servicioPersona
								.obtenerInvestigadorInterno(new IdPersona

								(this.documentoCoinv2, this.tipoDocumentoCoInv2.getId()));

						if (investigadorInterno != null) {

							// Calcular valor dedicación docente
							double duracionSemanas = 0.0;
							double duracionDias = 0.0;
							double duracionHoras = 0.0;
							double duracionAnnos = 0.0;

							if (selItems.toString().equals("PCD")) {

								// Valor hora del docente por dedicación al
								// proyecto
								Long valorHora = investigadorInterno.getValorHora();
								valorTotal = (long) (this.horasParticipante * valorHora * 4 * duracionTotalMeses);

								valorPersonalTotal = this.proyectoActual.getValorPersonalTotal() +

										valorTotal;
								this.proyectoActual.setValorPersonalTotal(valorPersonalTotal);
							}

							// Agregar participante a la listaparticipante
							InvestigadorProyecto participante = new InvestigadorProyecto();
							participante.setInvestigador(investigadorInterno);
							participante.setDedicacionHorasSemana(Short.parseShort(horasParticipante.toString()));
							participante.setFuncion("Participante");
							participante.setProyecto(proyectoActual);
							TipoInvestigador ti = new TipoInvestigador();
							ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(),
									selItems.toString());
							participante.setTipo(ti);
							participante.setValorPagar(valorTotal);

							listaParticipantes.add(participante);

						} else { // No se encontró como investigador interno

						}

					} else if (selItems.toString().equals("ESPO") || selItems.toString().equals("ESPR")
							|| selItems.toString().equals("AL") || selItems.toString().equals("A")) { // si

						// es

						// estudiante
						if (esConvSemill3) {
							if (servicioProyecto.validarGanadorSemilleros(this.documentoCoinv2,
									this.tipoDocumentoCoInv2.getId(), convocatoriaActual.getId().toString())) {
								errorEstudiantes = "El estudiante ya ha sido ganador en un corte anterior";
								return;
							}
						}

						IdPersona idEst = new IdPersona(this.documentoCoinv2, this.tipoDocumentoCoInv2.getId());
						Estudiante e = servicioPersona.obtenerEstudiante(idEst);

						if (e != null) {

							nuevoInvestigador = servicioPersona.obtenerInvestigador(idEst);
							if (nuevoInvestigador == null) {
								InvestigadorInterno nvoinv = e.convertirAInvestigador();
								if (e != null && e.getDependencia() != null) {
									nvoinv.setDependencia(e.getDependencia());
								}
								Persona per = servicioPersona.obtenerPersona(nvoinv.getId());

								if (per != null) {
									servicioPersona.insertarNuevoInvestigador(nvoinv);
									servicioPersona.insertaInterno(nvoinv);
								} else {
									servicioPersona.guardarInvestigador(nvoinv);
								}
								nuevoInvestigador = servicioPersona.obtenerInvestigador(nvoinv.getId());
							}

							InvestigadorInterno nvoinv = e.convertirAInvestigador();
							nvoinv.setDependencia(e.getDependencia());

							// Agregar participante a la listaparticipante
							InvestigadorProyecto participante = new InvestigadorProyecto();
							participante.setInvestigador(nvoinv);
							participante.setDedicacionHorasSemana(Short.parseShort(horasParticipante.toString()));
							participante.setFuncion("Participante");
							participante.setProyecto(proyectoActual);
							TipoInvestigador ti = new TipoInvestigador();
							ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(),
									selItems.toString());
							participante.setTipo(ti);
							participante.setValorPagar(valorTotal);

							listaParticipantes.add(participante);

						} else { // si no se encontró como estudiante

						}

					} else { // si es otro

						IdPersona id = new IdPersona(this.documentoCoinv2, this.tipoDocumentoCoInv2.getId());
						investigadorExterno.setId(id);
						investigadorExterno.setInterno(Investigador.EXTERNO);
						investigadorExterno.setEvaluador(Investigador.NO_EVALUADOR);
						Dependencia dep = servicioDependencia.obtenerDependencia(unidadEjecutoraPart);
						investigadorExterno.setDependencia(dep);

						if (insitucionNombre != null && !insitucionNombre.equals("")) {
							Institucion i = servicioGeneral.obtenerinstitucionPorNombre(insitucionNombre);

							if (i == null) {
								Institucion institucionNueva = new Institucion();
								institucionNueva.setNombre(insitucionNombre);
								servicioGeneral.guardarObjeto(institucionNueva);
								investigadorExterno.setInstitucion(institucionNueva);
							} else {
								investigadorExterno.setInstitucion(i);
							}
						} else {
							Institucion i = servicioGeneral.obtenerinstitucionPorNombre("--");
							investigadorExterno.setInstitucion(i);
						}

						Persona nuevaPersona = servicioPersona.obtenerPersona(id);

						if (nuevaPersona == null) {
							try {
								// Si la persona no existe se guarda como
								// investigador
								servicioPersona.guardarInvestigador(investigadorExterno);

							} catch (Exception e) {

							}

						} else {

							InvestigadorExterno persona = servicioPersona.obtenerInvestigadorExterno(id);
							if (persona == null) {
								servicioPersona.insertarExterno(investigadorExterno);
							}
						}

						// Agregar participante a la listaparticipante
						InvestigadorProyecto participante = new InvestigadorProyecto();
						participante.setInvestigador(investigadorExterno);
						participante.setDedicacionHorasSemana(Short.parseShort(horasParticipante.toString()));
						participante.setFuncion("Participante");
						participante.setProyecto(proyectoActual);
						TipoInvestigador ti = new TipoInvestigador();
						ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(),
								selItems.toString());
						participante.setTipo(ti);
						participante.setValorPagar(valorTotal);

						listaParticipantes.add(participante);

					}

				} catch (Exception e) {
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Por favor ingrese un documento válido para el participante. Ingrese solo números y verifique que no queden espacios.",
							"Por favor ingrese un documento válido para el participante. Ingrese solo números y verifique que no queden espacios.");
					mostrarMensaje(msg, buscarPer2);
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "La persona indicada ya se encuentra registrada.",
								"La persona indicada ya se encuentra registrada."));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor ingrese el número de identificación de la persona a registrar",
							"Por favor ingrese el número de identificación de la persona a registrar"));
		}

	}

	private boolean buscarInvestigador(IdPersona id) {
		// BUSCA UN INVESTIGADOR DE ACUERDO A SU ID
		boolean investigadorPresente = false;
		int i = 0;
		List listaInvestigadoresProyecto = listaInvestigadoresVista;
		while (i < listaInvestigadoresProyecto.size()) {
			InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) listaInvestigadoresVista.get(i);
			InvestigadorProyecto d = ipv.getIp();
			if (id.getDocumento().equals(d.getInvestigador().getId().getDocumento())
					&& id.getTipoDocumento().equals(d.getInvestigador().getId().getTipoDocumento())) {
				investigadorPresente = true;
				break;
			}
			i = i + 1;
		}
		return investigadorPresente;
	}

	private Dependencia buscarFacultad(String idFacultad) {
		// BUSCA UNA FACULTAD DE ACUERDO A SU ID
		int i = 0;
		while (i < listaFacultades.size()) {
			Dependencia d = (Dependencia) listaFacultades.get(i);
			if (d.getId().equals(idFacultad)) {
				return d;
			}
			i = i + 1;
		}
		return null;
	}

	// lmom
	public void agregarParticipante2() {

		if (documentoCoinv2 != null && !documentoCoinv2.equals("") && !documentoCoinv2.equals(" ")) {

			try {

				IdPersona idEst = new IdPersona(this.documentoCoinv2, this.tipoDocumentoCoInv2.getId());
				Estudiante e = servicioPersona.obtenerEstudiante(idEst);
				InvestigadorInterno nvoinv = e.convertirAInvestigador();
				nvoinv.setDependencia(e.getDependencia());

				if (e != null) {

					long valorTotal = 0;

					// Valor hora del docente por dedicación al proyecto
					Long valorHora = 0L;
					Short horas = 0;
					if (this.proyectoActual.getValorPersonalTotal() == null
							|| this.proyectoActual.getValorPersonalTotal().equals("0.0")
							|| this.proyectoActual.getValorPersonalTotal().equals("")) {
						this.proyectoActual.setValorPersonalTotal(0L);
					}

					// Agregar participante a la listaparticipante
					InvestigadorProyecto participante = new InvestigadorProyecto();
					participante.setInvestigador(nvoinv);
					participante.setDedicacionHorasSemana(Short.parseShort(horasParticipante.toString()));
					participante.setFuncion("Participante");
					participante.setProyecto(proyectoActual);
					TipoInvestigador ti = new TipoInvestigador();
					ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), selItems.toString());
					participante.setTipo(ti);
					participante.setValorPagar(valorTotal);

					listaParticipantes.add(participante);

				} else {

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			// mensaje de error
		}

	}

	// lmom
	public void eliminarDirector() {

		try {
			InvestigadorProyecto invesPry = listaDirector.get(0);

			listaDirector = new ArrayList<InvestigadorProyecto>();

			Long valor = 0L;

			if (proyectoActual.getValorPersonalTotal() != null) {
				if (proyectoActual.getValorPersonalTotal() > 0) {
					valor = proyectoActual.getValorPersonalTotal();
					valor = valor - invesPry.getValorPagar();
					proyectoActual.setValorPersonalTotal(valor);
				} else {
					proyectoActual.setValorPersonalTotal(valor);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// lmom
	public void eliminarParticipante() {

		System.out.println("id del participante: " + participante.getInvestigador().getId().getDocumento());

		try {

			listaParticipantes.remove(participante);
			proyectoActual.getInvestigadoresProyecto().remove(participante);

			listaParticipantesBorrados.add(participante);

			participante = new InvestigadorProyecto();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void adicionarDependencia() {

		Dependencia dep = buscarDependencia(dependenciaAdicionada);
		dependenciaAreaResponsabilidad.setDependencia(dep);
		proyectoActual.adicionarDependencia(dependenciaAreaResponsabilidad);
		dependenciaAreaResponsabilidad = new DependenciaAreaResponsabilidad();
	}

	public void eliminarDependencia() {
		proyectoActual.borrarDependencia(dependenciaAreaResponsabilidadSeleccionada);
	}

	private Dependencia buscarDependencia(String id) {
		// BUSCA UNA DEPENDENCIA DE ACUERDO A SU ID
		Dependencia d = new Dependencia();
		int i = 0;
		while (i < dependenciasUN.size()) {
			d = (Dependencia) dependenciasUN.get(i);
			if (id.equals(d.getId()))
				break;
			i = i + 1;
		}
		return d;
	}

	public void agregarObjetivoDesarrolloSostenible() {
		if (objetivoDesarrolloSostenibleSecundario.equals("0")) {
			mensajeError(btnObjDesSosSec, "Por favor seleccione el objetivo de desarrollo sostenible.");
		} else {
			List<DominioDetalle> listaDomDet = servicioGeneral.obtenerObjetos(DominioDetalle.class,
					"select e from DominioDetalle e, Dominio d where e.identificador.tipo = '"
							+ objetivoDesarrolloSostenibleSecundario + "' and e.identificador.id = d.id and d.tipo = '"
							+ DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE + "'");
			DominioDetalle g = obtenerDominioDetalleLista(objetivoDesarrolloSostenibleSecundario, listaDomDet);

			ValoresListasProyecto vlp = new ValoresListasProyecto();
			// vlp.setProyecto(proyectoActual);
			vlp.setTipo(DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE);
			vlp.setValor(g.getIdentificador().getTipo());
			vlp.setNombreValor(g.getDescripcion());
			vlp.setDescripcion("OBJETIVO_DESARROLLO_SOSTENIBLE_SECUNDARIO");

			if (!valoresObjetivosDesarrolloSostenible.contains(vlp)) {
				proyectoActual.adicionarObjetivoDesarrolloSostenible(vlp);
				valoresObjetivosDesarrolloSostenible.add(vlp);
				objetivoDesarrolloSostenibleSecundario = "0";
			} else {
				mensajeError(btnObjDesSosSec, "La opción seleccionada ya se encuentra asociada al proyecto");
			}
		}

	}

	public void eliminarObjetivoDesarrolloSostenible() {
		valoresObjetivosDesarrolloSostenible.remove(objetivoDesarrolloSostenibleSeleccionado);
		proyectoActual.eliminarObjetivoDesarrolloSostenible(objetivoDesarrolloSostenibleSeleccionado);
	}

	public void cargarConvocatoriaActual() {
		esConvSemill3 = false;

		if (proyectoActual.getId() != null) {

			List listConvocatorias = servicioGeneral.obtenerObjetos(
					"select e from Convocatoria e where e.id = " + proyectoActual.getModalidad().getId());
			if (listConvocatorias.size() > 0 && listConvocatorias != null) {
				convocatoriaActual = (Convocatoria) listConvocatorias.get(0);
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_NACIONAL")
							|| convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_INTERNACIONAL"))) {
				esConvocatoriaEventos = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_NACIONAL"))) {
				esConvocatoriaEventosNacional = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_INTERNACIONAL"))) {
				esConvocatoriaEventosInternacional = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_INI")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_POSG_3"))) {
				esConvIni = true;

			}
			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_PRD"))) {
				esConvPurdue = true;

			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M1")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M2"))) {
				esConvEventos2019 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2019_M1")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2019_M2")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2021_M1")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2021_M2"))) {
				esConvMoots2019 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2021_M1")) {
				esConvMoots2021_M1 = true;
			}
			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2021_M2")) {
				esConvMoots2021_M2 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2019_M2")) {
				esConvMoots2019_M2 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M1")) {
				esConvEventos2019M1 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M2")) {
				esConvEventos2019M2 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_CA_EQUIPOS"))) {
				esConvCienciasAgraEquipos2015 = true;

			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_NACIONAL")
							|| convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_INTERNACIONAL"))) {
				esConvocatoriaEventos = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_NACIONAL"))) {
				esConvocatoriaEventosNacional = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_INTERNACIONAL"))) {
				esConvocatoriaEventosInternacional = true;
			}

			if (convocatoriaActual.getId() == 419) {
				esConvSemill3 = true;
			}
			if (convocatoriaActual.getPadre() != null && convocatoriaActual.getPadre().getId() == 226) {
				esConvSemill = true;
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
					&& (convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_NACIONAL")
							|| convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_INTERNACIONAL")
							|| convocatoriaActual.getRestriccion().getId().equals("CE1")
							|| convocatoriaActual.getRestriccion().getId().equals("CE2")
							|| convocatoriaActual.getRestriccion().getId().equals("CE1-N2017")
							|| convocatoriaActual.getRestriccion().getId().equals("CE2-N2017")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M1")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M2")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2019_M1")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2019_M2")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2021_M1")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2021_M2"))) {
				mostrarSiConvocatoriaEventos = true;
				if (convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M1")
						|| convocatoriaActual.getRestriccion().getId().equals("CE1-N2017")
						|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2019_M1")
						|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2021_M1")
						|| convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_INTERNACIONAL")) {
					mostrarPaises = true;
				}
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
					&& (convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_NACIONAL")
							|| convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_INTERNACIONAL"))) {
				esConvocatoriaEventos = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_NACIONAL"))) {
				esConvocatoriaEventosNacional = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_INTERNACIONAL"))) {
				esConvocatoriaEventosInternacional = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_INI")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_POSG_3"))) {
				esConvIni = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M1")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M2"))) {
				esConvEventos2019 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M1")) {
				esConvEventos2019M1 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M2")) {
				esConvEventos2019M2 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2019_M1")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2019_M2")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2021_M1")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2021_M2"))) {
				esConvMoots2019 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2021_M1")) {
				esConvMoots2021_M1 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2021_M2")) {
				esConvMoots2021_M2 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2019_M1")) {
				esConvMoots2019_M1 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2019_M2")) {
				esConvMoots2019_M2 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_PRD"))) {
				esConvPurdue = true;

			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_CA_EQUIPOS"))) {
				esConvCienciasAgraEquipos2015 = true;

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
					&& (convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_NACIONAL")
							|| convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_INTERNACIONAL")
							|| convocatoriaActual.getRestriccion().getId().equals("CE1")
							|| convocatoriaActual.getRestriccion().getId().equals("CE2")
							|| convocatoriaActual.getRestriccion().getId().equals("CE1-N2017")
							|| convocatoriaActual.getRestriccion().getId().equals("CE2-N2017")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M1")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M2")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2019_M1")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2019_M2")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2021_M1")
							|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2021_M2"))) {
				mostrarSiConvocatoriaEventos = true;
				if (convocatoriaActual.getRestriccion().getId().equals("CONV_EVENT_2019_M1")
						|| convocatoriaActual.getRestriccion().getId().equals("CE1-N2017")
						|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2019_M1")
						|| convocatoriaActual.getRestriccion().getId().equals("CONV_MOOTS_2021_M1")
						|| convocatoriaActual.getRestriccion().getId().equals("EVENTOS_GENERICO_INTERNACIONAL")) {
					mostrarPaises = true;
				}
			}
			if (convocatoriaActual.getRestriccion() != null
					&& convocatoriaActual.getRestriccion().getId().equals("CONV_POSG")) {
				esPosgrado1_2 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CONV_INNO_MOD_2_3"))) {
				esProyectoInnoModDos = true;
			}

			if (convocatoriaActual.getId() == 419) {
				esConvSemill3 = true;
			}
			if (convocatoriaActual.getPadre() != null && convocatoriaActual.getPadre().getId() == 226) {
				esConvSemill = true;
			}
		}

	}

	public void cargarListaProductos() {

		manejadorProductoNivel1 = new UISelectOne();
		manejadorProductoNivel2 = new UISelectOne();
		manejadorProductoNivel3 = new UISelectOne();

		listaProductosNivel1 = new ArrayList();
		listaProductosNivel2 = new ArrayList();
		listaProductosNivel3 = new ArrayList();

		// Cuando la modalidad del proyecto es una convocatoria, se buscan los
		// productos asociados a ella
		Modalidad mod = proyectoActual.getModalidad();

		if (mod instanceof Convocatoria) {
			Convocatoria con = (Convocatoria) mod;
			productosConvocatoria = servicioModalidad.obtenerProductosConvocatoria(con);
			if (productosConvocatoria != null && productosConvocatoria.size() != 0) {
				for (Iterator it = productosConvocatoria.iterator(); it.hasNext();) {
					ProductoTipo pt = (ProductoTipo) it.next();
					if (pt.getNivel().equals("2")) {
						listaProductosNivel3.add(pt);
					}
				}
			}

		}

		Collections.sort(listaProductosNivel1, new Comparator() {

			public int compare(Object o1, Object o2) {
				ProductoTipo e1 = (ProductoTipo) o1;
				ProductoTipo e2 = (ProductoTipo) o2;
				return e1.getNombre().compareTo(e2.getNombre());
			}
		});

		productoNivel3Item = crearSelectItem(listaProductosNivel3);

	}

	public void cargarTipoProductos() {

		manejadorProductoNivel1 = new UISelectOne();
		manejadorProductoNivel2 = new UISelectOne();
		manejadorProductoNivel3 = new UISelectOne();

		listaProductosNivel1 = new ArrayList();
		listaProductosNivel2 = new ArrayList();
		listaProductosNivel3 = new ArrayList();

		// Cuando la modalidad del proyecto es una convocatoria, se buscan los
		// productos asociados a ella
		Modalidad mod = proyectoActual.getModalidad();

		if (mod instanceof Convocatoria) {

			if (mod.getTipo().getId().equals("J")) {
				// Se carga el valor del primer nivel
				ProductoTipo pro1 = new ProductoTipo();
				pro1.setId(ProductoTipo.RAIZ);
				listaProductosNivel1 = servicioGeneral.obtenerHijos(pro1);
				productosConvocatoria = servicioGeneral.obtenerListaObjetos("ProductoTipo");

			} else {
				Convocatoria con = (Convocatoria) mod;
				productosConvocatoria = servicioModalidad.obtenerProductosConvocatoria(con);
				if (productosConvocatoria != null && productosConvocatoria.size() != 0) {
					for (Iterator it = productosConvocatoria.iterator(); it.hasNext();) {
						ProductoTipo pt = (ProductoTipo) it.next();
						if (pt.getNivel().equals("0")) {
							listaProductosNivel1.add(pt);
						}
					}
				}
			}

		} else {
			// Se carga el valor del primer nivel
			ProductoTipo pro1 = new ProductoTipo();
			pro1.setId(ProductoTipo.RAIZ);
			listaProductosNivel1 = servicioGeneral.obtenerHijos(pro1);
			productosConvocatoria = servicioGeneral.obtenerListaObjetos("ProductoTipo");
		}

		Collections.sort(listaProductosNivel1, new Comparator() {

			public int compare(Object o1, Object o2) {
				ProductoTipo e1 = (ProductoTipo) o1;
				ProductoTipo e2 = (ProductoTipo) o2;
				return e1.getNombre().compareTo(e2.getNombre());
			}
		});

		productoNivel1Item = crearSelectItem(listaProductosNivel1);
		productoNivel1 = ((ProductoTipo) listaProductosNivel1.get(0)).getId();
		productoNivel1Actual = (ProductoTipo) listaProductosNivel1.get(0);

		manejadorProductoNivel1.setValue(productoNivel1);

		// valores para el segundo nivel
		ProductoTipo pro2 = new ProductoTipo();
		pro2.setId(productoNivel1);
		List listaProductosNivel2Aux = servicioGeneral.obtenerHijos(pro2);
		if (productosConvocatoria != null && productosConvocatoria.size() != 0 && listaProductosNivel2Aux != null) {
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

		Collections.sort(listaProductosNivel2, new Comparator() {

			public int compare(Object o1, Object o2) {
				ProductoTipo e1 = (ProductoTipo) o1;
				ProductoTipo e2 = (ProductoTipo) o2;
				return e1.getNombre().compareTo(e2.getNombre());
			}
		});

		productoNivel2Item = crearSelectItem(listaProductosNivel2);
		if (listaProductosNivel2.size() != 0) {
			productoNivel2 = ((ProductoTipo) listaProductosNivel2.get(0)).getId();
			productoNivel2Actual = (ProductoTipo) listaProductosNivel2.get(0);
			manejadorProductoNivel2.setValue(productoNivel2);

			// valores iniciales para el ultimo nivel
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

			Collections.sort(listaProductosNivel3, new Comparator() {

				public int compare(Object o1, Object o2) {
					ProductoTipo e1 = (ProductoTipo) o1;
					ProductoTipo e2 = (ProductoTipo) o2;
					return e1.getNombre().compareTo(e2.getNombre());
				}
			});

			productoNivel3Item = crearSelectItem(listaProductosNivel3);
			if (listaProductosNivel3.size() != 0) {
				productoNivel3 = ((ProductoTipo) listaProductosNivel3.get(0)).getId();
				productoNivel3Actual = (ProductoTipo) listaProductosNivel3.get(0);
				manejadorProductoNivel3.setValue(productoNivel3);
			}
		} else {
			listaProductosNivel3 = new ArrayList();
			productoNivel3Item = crearSelectItem(listaProductosNivel3);
		}

	}

	public void cambiarTipoEvento() {

		if (proyectoActual.getClaseEvento().equals("OT")) {
			mostrarOtroTipoEvento = true;
		} else {
			mostrarOtroTipoEvento = false;
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

	public void insertarObjetivo() {
		if (!esCadenaVacia(objetivoEspecifico)) {
			objetivoEspecifico = controlTamanoCadena(objetivoEspecifico, 3000);
			ObjetivoEspecifico objetivoEspecificoNuevo = new ObjetivoEspecifico();
			objetivoEspecificoNuevo.setNombre(objetivoEspecifico);
			objetivoEspecificoNuevo.setNumeroOrden(obtenerMaxOrderObjetivo(proyectoActual.getListaObjetivos()));
			proyectoActual.adicionarObjetivoEspecifico(objetivoEspecificoNuevo);
			this.objetivoEspecifico = "";
		} else {
			if (mostrarSiConvocatoriaEventos) {
				mensajeError(objetoEspecificoEven, "Por favor ingrese el objetivo específico (Máx. 2000 caracteres).");
			} else {
				mensajeError(objetoEspecifico, "Por favor ingrese el objetivo específico (Máx. 2000 caracteres).");
			}

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

	public void eliminarObjetivo() {
		if (objetivoTabla.getListaMetas().isEmpty()) {
			proyectoActual.borrarObjetivoEspecifico(objetivoTabla);
		} else
			mensajeError(objetoEspecifico, "No puede eliminar el objetivo seleccionado dado que tiene "
					+ objetivoTabla.getListaMetas().size() + " metas asociadas");

	}

	private boolean validarObjetivos() {
		// VALIDA LA EXISTENCIA DE OBJETIVOS EN LA RESPECTIVA LISTA
		List listaAuxiliar = new ArrayList();
		for (int i = 0; i < listaObjetivos.size(); i++) {
			ObjetivoEspecifico oe = (ObjetivoEspecifico) listaObjetivos.get(i);
			if (oe.isBorrable()) {
				listaAuxiliar.add(oe);
				proyectoActual.borrarObjetivoEspecifico(oe);
			} else {
				proyectoActual.adicionarObjetivoEspecifico(oe);
			}
		}
		listaObjetivos.removeAll(listaAuxiliar);
		if (listaObjetivos.isEmpty()) {

			return false;
		}
		return true;
	}

	public void insertarResultado() {
		if (this.resultado != null && !this.resultado.equals("")) {
			System.out.println("resultado: " + this.resultado);
			ResultadoProyecto re = new ResultadoProyecto();
			re.setDescripcion(this.resultado);
			listaResultados.add(re);
			this.resultado = "";
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor escriba el resultado",
					"Por favor escriba el resultado");
			mostrarMensaje(message, uiResultado);
		}

	}

	public void eliminarResultado() {
		listaResultados.remove(resultadoTabla);
		proyectoActual.borrarResultado(resultadoTabla);
		resultadoTabla = new ResultadoProyecto();
	}

	public void cambiarProductoNivel1(ValueChangeEvent event) {
		productoNivel1Actual = buscarProductoTipoNivel1((String) event.getNewValue());
		// valores para el segundo nivel
		ProductoTipo pro2 = new ProductoTipo();
		pro2.setId(event.getNewValue().toString());
		listaProductosNivel2 = new ArrayList();
		List listaProductosNivel2Aux = servicioGeneral.obtenerHijos(pro2);
		if (productosConvocatoria.size() != 0 && listaProductosNivel2Aux != null) {
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

		Collections.sort(listaProductosNivel2, new Comparator() {

			public int compare(Object o1, Object o2) {
				ProductoTipo e1 = (ProductoTipo) o1;
				ProductoTipo e2 = (ProductoTipo) o2;
				return e1.getNombre().compareTo(e2.getNombre());
			}
		});

		productoNivel2Item = crearSelectItem(listaProductosNivel2);
		productoNivel2 = ((ProductoTipo) listaProductosNivel2.get(0)).getId();
		productoNivel2Actual = (ProductoTipo) listaProductosNivel2.get(0);
		manejadorProductoNivel2.setValue(productoNivel2);

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

		Collections.sort(listaProductosNivel3, new Comparator() {

			public int compare(Object o1, Object o2) {
				ProductoTipo e1 = (ProductoTipo) o1;
				ProductoTipo e2 = (ProductoTipo) o2;
				return e1.getNombre().compareTo(e2.getNombre());
			}
		});

		productoNivel3Item = crearSelectItem(listaProductosNivel3);
		productoNivel3 = ((ProductoTipo) listaProductosNivel3.get(0)).getId();
		productoNivel3Actual = (ProductoTipo) listaProductosNivel3.get(0);

	}

	public void cambiarProductoNivel2(ValueChangeEvent event) {
		productoNivel2Actual = buscarProductoTipoNivel2((String) event.getNewValue());

		ProductoTipo pro3 = new ProductoTipo();
		pro3.setId(event.getNewValue().toString());
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

		Collections.sort(listaProductosNivel3, new Comparator() {

			public int compare(Object o1, Object o2) {
				ProductoTipo e1 = (ProductoTipo) o1;
				ProductoTipo e2 = (ProductoTipo) o2;
				return e1.getNombre().compareTo(e2.getNombre());
			}
		});

		productoNivel3Item = crearSelectItem(listaProductosNivel3);
		productoNivel3 = ((ProductoTipo) listaProductosNivel3.get(0)).getId();
		productoNivel3Actual = (ProductoTipo) listaProductosNivel3.get(0);
		manejadorProductoNivel3.setValue(productoNivel3);
	}

	public void cambiarProductoNivel3(ValueChangeEvent event) {
		productoNivel3Actual = buscarProductoTipoNivel3((String) event.getNewValue());
	}

	private ProductoTipo buscarProductoTipoNivel1(String id) {
		ProductoTipo prT = new ProductoTipo();
		int i = 0;
		while (i < this.listaProductosNivel1.size()) {
			prT = (ProductoTipo) this.listaProductosNivel1.get(i);
			if (id.equals(prT.getId())) {
				break;
			}
			i = i + 1;
		}
		return prT;
	}

	private ProductoTipo buscarProductoTipoNivel2(String id) {
		ProductoTipo prT = new ProductoTipo();
		int i = 0;
		while (i < this.listaProductosNivel2.size()) {
			prT = (ProductoTipo) this.listaProductosNivel2.get(i);
			if (id.equals(prT.getId())) {
				break;
			}
			i = i + 1;
		}
		return prT;
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

	private SelectItem[] crearSelectItem(List lista) {
		SelectItem[] elementos;
		elementos = new SelectItem[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			ProductoTipo pro = (ProductoTipo) lista.get(i);
			String nombre = pro.getNombre();

			if (pro.getNombre().length() > 50) {
				nombre = pro.getNombre().substring(0, 50) + "...";
				if (pro.getNombre().length() < 115 && pro.getNombre().length() > 75)
					pro.setNombrePop(pro.getNombre() + "_____________________________________________________________");
				else
					pro.setNombrePop(pro.getNombre() + " ");
			}

			elementos[i] = new SelectItem(pro.getId(), nombre);
		}
		return elementos;
	}

	public void adicionarProducto() {
		int cantidadIngresada;
		boolean existeProducto = false;

		if ("".equals(productoNivel3)) {
			mensajeError(uiCantidad, "Seleccione el tipo de producto.");
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

			if (!existeProducto) {
				proyectoActual.adicionarProductoProyecto(productoProyecto);
				cantidad = "1";
			}
		}
	}

	public void adicionarProductoEvento() {
		int cantidadIngresada;
		boolean existeProducto = false;

		if ("".equals(productoNivel3)) {
			mensajeError(uiCantidadEventos, "Seleccione el tipo de producto.");
		} else {
			try {
				cantidadIngresada = Integer.parseInt(cantidad);
			} catch (NumberFormatException nfe) {
				mensajeError(uiCantidadEventos, "La cantidad no es un número valido.");
				return;
			}

			ProyectoProducto productoProyecto = new ProyectoProducto();
			ProductoTipo p = buscarProductoTipoNivel3(productoNivel3);
			productoProyecto.setProducto(p);
			productoProyecto.setCantidad(cantidadIngresada);

			existeProducto = existeProductoEnSet(proyectoActual.getProductosProyecto(), productoProyecto);

			if (!existeProducto) {
				proyectoActual.adicionarProductoProyecto(productoProyecto);
				cantidad = "1";
			} else {
				mensajeError(uiCantidadEventos, "El producto ya se encuentra registrado.");
			}

		}
	}

	public void eliminarProducto() {
		proyectoActual.borrarProductoProyecto(productoSeleccionado);
	}

	public boolean existeProductoEnSet(Set productos, ProyectoProducto productoProyecto) {
		Iterator it = productos.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof ProyectoProducto) {
				if (((ProyectoProducto) obj).getProducto().getNombre().toUpperCase()
						.equals(productoProyecto.getProducto().getNombre().toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}

	public List obtenerPalabraClavesSugeridas(String nombre) {
		return servicioGeneral.obtenerPalabrasClaveEmpezandoCon(nombre);
	}

	// DEFINICION DE FUNCIONES ESPECIFICAS DE LA CLASE
	public void insertarPalabraClave() {
		System.out.println("inserta " + palabraClave.getPalabra());
		boolean existePalabra = palabraClave.existePalabraEnSet(proyectoActual.getPalabrasClaves());
		if ((!palabraClave.getPalabra().equals("")) && (!existePalabra)) {
			PalabraClave pc = new PalabraClave();
			pc.setPalabra(palabraClave.getPalabra().toUpperCase());
			pc.setPalabraOriginal(palabraClave.getPalabra());
			try {
				PalabraClave pc1 = servicioGeneral.obtenerPalabraClave(pc.getPalabra());
				if (pc1 == null) {
					pc.setIdioma("ES");
					servicioGeneral.guardarObjeto(pc);
				} else {
					pc = (PalabraClave) pc1.clone();
				}
				proyectoActual.adicionarPalabraClave(pc);
				palabraClave.setPalabra("");
				pc1 = null;
				pc = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		palabraClave = new PalabraClave();
	}

	public void eliminarPalabraClave() {
		proyectoActual.borrarPalabraClave(palabraClaveTabla);
		palabraClaveTabla = new PalabraClave();
	}

	public boolean validarNombre_InvPpal() {
		boolean val = true;
		boolean valestud = false;

		if (listaDirector.size() <= 0 || listaDirector == null) {
			val = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre la información del director del proyecto",
							"Por favor registre la información del director del proyecto"));
		}

		if (convocatoriaActual.getRestriccion() != null
				&& convocatoriaActual.getRestriccion().getId().equals("CONV_POSG_3")) {
			if (listaParticipantes.size() <= 0 || listaParticipantes == null) {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Por favor registre la información de los estudiantes participantes en el proyecto", ""));
			} else {
				for (int i = 0; i < listaParticipantes.size(); i++) {
					InvestigadorProyecto inv = listaParticipantes.get(i);
					if (inv.getTipo().getId().equals("ESPO") || inv.getTipo().getId().equals("AL")) {
						valestud = true;
					}
				}
			}

			if (!valestud) {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Por favor registre la información de los estudiantes participantes en el proyecto.  Puede agregar a un estudiante como 'Estudiante Líder' para continuar con el diligenciamento del proyecto",
						""));

			}

		}

		if (this.proyectoActual.getNombre() == null || this.proyectoActual.getNombre().equals("--")
				|| this.proyectoActual.getNombre().equals("")) {
			val = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"Por favor registre el título del proyecto", "Por favor registre el título del proyecto"));
		}

		if (this.proyectoActual.getDuracion() != null) {
			Convocatoria con = (Convocatoria) this.proyectoActual.getModalidad();

			if (con.getTiempoEjecucionProyecto() != null) {

				if (this.proyectoActual.getDuracion() > con.getTiempoEjecucionProyecto()) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"La duración del proyecto no debe superar la duración máxima de la convocatoria", ""));
				}

			}

		} else {
			val = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor registre la duración del proyecto", ""));

		}

		return val;
	}

	public void guardarConvocatoriaEventos() {
		System.out.println(
				"<================================== GUARDAR CONVOCATORIA EVENTOS ==================================>");
		if (this.telefono != null && this.telefono.length() > 0) {
			ii.setTelefono(this.telefono);
			servicioGeneral.guardarObjeto(ii);
		}

		if (ciudadActual.getId() != null) {
			ciudadActual = buscarCiudad(ciudadActual.getId());
			ciudadActual.setDepartamento(departamentoActual);
			Set ciudades = new HashSet();
			proyectoActual.setCiudades(ciudades);
			proyectoActual.adicionarCiudad(ciudadActual);
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

		if (proyectoActual.getId() != null) {

		} else {
			// ASIGNACION DEL INVESTIGADOR PRINCIPAL POR DEFECTO
			InvestigadorProyecto investigadorProyecto = new InvestigadorProyecto();
			investigadorProyecto.setInvestigador(
					servicioPersona.obtenerInvestigador(((Persona) sesion.getAttribute("persona")).getId()));
			investigadorProyecto.setProyecto(proyectoActual);
			TipoInvestigador ti = new TipoInvestigador();
			ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(),
					InvestigadorProyecto.PRINCIPAL);
			investigadorProyecto.setTipo(ti);

//			if (proyectoActual.getInvestigadoresProyecto().size() <= 0) {obtenerInvestigadorPrincipalXProyecto
			
			/*			if (servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId()) == null) {
				proyectoActual.adicionarInvestigadorProyecto(investigadorProyecto);
			}*/
			
			if(proyectoActual.getResponsable()==null) {
				proyectoActual.adicionarInvestigadorProyecto(investigadorProyecto);
			}

		}

		if (!proyectoActual.getClaseEvento().equals("OT")) {
			proyectoActual.setOtroClaseEvento("");
		}

		if (esConvEventos2019 || esConvocatoriaEventos)
			actualizarListaPoblacionObjetivo();

//		if (listaParticipantes.size() > 0) {
//			for (int i = 0; i < listaParticipantes.size(); i++) {
//				proyectoActual.adicionarInvestigadorProyecto(listaParticipantes.get(i));
//			}
//		}

		servicioProyecto.ingresarProyecto(proyectoActual);
	}

	public void guardarFichaMinimaProyecto() {
		proyectoActual.setTipoActividad("PI");
		// proyectoActual.setRolUniversidad("ROL_GESTOR");
		// proyectoActual.setMecanismoParticipacion("MEC_NO_APLICA");

		for (int i = 0; i < listaObjetivos.size(); i++) {

			proyectoActual.adicionarObjetivoEspecifico(listaObjetivos.get(i));
		}

		for (int i = 0; i < listaResultados.size(); i++) {

			proyectoActual.adicionarResultado(listaResultados.get(i));
		}

		if (proyectoExiste) {

			for (InvestigadorProyecto inv : listaParticipantesBorrados) {
				try {
					servicioGeneral.eliminarObjeto(inv);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// áreas de la ciencia
		DominioDetalle arUno = new DominioDetalle();
		List listaDomDetUno = new ArrayList<DominioDetalle>();

		listaDomDetUno = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_AREA_CIENCIA + "' and dd.identificador.tipo = '" + areaCiencia + "'");

		AreaTematica arTemUno = new AreaTematica();
		if (listaDomDetUno != null && listaDomDetUno.size() > 0) {
			arUno = (DominioDetalle) listaDomDetUno.get(0);
			arTemUno.setProyecto(proyectoActual);
			arTemUno.setProyectoAreaTematica(arUno);
			arTemUno.setTipo(1l);
		}

		// áreas de la ciencia
		DominioDetalle arDos = new DominioDetalle();
		List listaDomDetarDos = new ArrayList<DominioDetalle>();
		listaDomDetarDos = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_AREA_CIENCIA + "' and dd.identificador.tipo = '" + areaCienciaSec + "'");

		AreaTematica arTemDos = new AreaTematica();
		if (listaDomDetarDos != null && listaDomDetarDos.size() > 0) {
			arDos = (DominioDetalle) listaDomDetarDos.get(0);
			arTemDos.setProyecto(proyectoActual);
			arTemDos.setProyectoAreaTematica(arDos);
			arTemDos.setTipo(2l);
		}

		Set<AreaTematica> seAt = new HashSet<AreaTematica>();
		if (listaDomDetUno != null && listaDomDetUno.size() > 0)
			seAt.add(arTemUno);
		if (listaDomDetarDos != null && listaDomDetarDos.size() > 0)
			seAt.add(arTemDos);

		try {
			if (proyectoActual != null && proyectoActual.getId() != null) {
				servicioGeneral.eliminar("DELETE HER_PROYECTO_AREA_TEMATICA WHERE PRY_ID = " + proyectoActual.getId());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		proyectoActual.setAreasTematicas(seAt);

		if (listaDirector.size() > 0) {
			if (proyectoActual.getId() != null) {
				try {
					System.out.println("DELETE FROM HER_INVESTIGADOR_PROYECTO WHERE PRY_ID = '" + proyectoActual.getId()
							+ "' AND INV_ID = '" + documentoCoinv + "'");

					servicioGeneral.eliminar("DELETE FROM HER_INVESTIGADOR_PROYECTO WHERE PRY_ID = '"
							+ proyectoActual.getId() + "' AND INV_ID = '" + documentoCoinv + "'");

					InvestigadorProyecto investigadorProyecto = new InvestigadorProyecto();

					investigadorProyecto.copiarDatos(listaDirector.get(0));

					servicioGeneral.insertarObjeto(investigadorProyecto);

				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else {
				proyectoActual.adicionarInvestigadorProyecto(listaDirector.get(0));
			}

		} else {

		}

		if (listaParticipantes.size() > 0) {
			for (int i = 0; i < listaParticipantes.size(); i++) {
				proyectoActual.adicionarInvestigadorProyecto(listaParticipantes.get(i));
			}
		}

		servicioProyecto.ingresarProyecto(proyectoActual);

	}

	// INICIO INFORMACIÓN FINANCIERA PROYECTO

	public void cargarRubrosModalidad() {
		// TODO ESTE CARGUE HAY QUE REVISARLO YA QUE NO TIENE EN CEUNTA LOS
		// RUBROS DE CUANDO
		// LA MODALIDAD NO ES UNA CONVOCATORIA
		try {
			Object o = proyectoActual.getModalidad();
			System.out.println(o.getClass().getName());
			listaRubrosFinanciables = servicioModalidad
					.obtenerRubrosFinanciablesModalidad(proyectoActual.getModalidad().getId());
			// Se utiliza el campo descripción "Contrapartida FM" para identificar los
			// rubros autorizados de contrapartida
			listaRubrosContrapartida = servicioGeneral.obtenerObjetos(TipoRubro.class,
					"select e from TipoRubro e where e.descripcion = 'Contrapartida FM'");
			cambiarTiposRubros();
			gastoActual.setTipoRubro(new TipoRubro());
			if (listaTiposRubros != null && listaTiposRubros.size() > 0) {
				gastoActual.getTipoRubro().setId(((TipoRubro) listaTiposRubros.get(0)).getId());
			}
		} catch (Exception e) {
			System.out.println("ManejadorInfoFinanciera:cargarTiposRubro:Error Cargando Los Tipos de Rubro");
			e.printStackTrace();
		}
	}
	
	public void cambiarTiposRubros() {
		if(tipoFuenteActual == 1) {
			naturalezaRubroItem = new SelectItem[2];
			naturalezaRubroItem[0] = new SelectItem(VariablesEstaticas.RUBRO_EFECTIVO, "Efectivo");
			naturalezaRubroItem[1] = new SelectItem(VariablesEstaticas.RUBRO_ESPECIE, "Especie");
			idNaturalezaRubro = VariablesEstaticas.RUBRO_EFECTIVO;
			cambiarRubros();
		}else if(tipoFuenteActual == 2) {
			naturalezaRubroItem = new SelectItem[1];
			naturalezaRubroItem[0] = new SelectItem(VariablesEstaticas.RUBRO_EFECTIVO, "Efectivo");
			idNaturalezaRubro = VariablesEstaticas.RUBRO_EFECTIVO;
			cambiarRubros();
		}else {
			naturalezaRubroItem = new SelectItem[0];
		}
	}

	public void cambiarRubros() {
		if (!esCadenaVacia(idNaturalezaRubro)) {
			if (idNaturalezaRubro.equals(VariablesEstaticas.RUBRO_EFECTIVO)) {

				if (listaRubrosFinanciables != null) {
					tiposRubroItem = new SelectItem[listaRubrosFinanciables.size()];
				} else {
					tiposRubroItem = new SelectItem[0];
				}
				listaTiposRubros = new ArrayList();
				for (int i = 0; listaRubrosFinanciables != null && i < listaRubrosFinanciables.size(); i++) {
					RubroFinanciable rf = (RubroFinanciable) listaRubrosFinanciables.get(i);
					TipoRubro tr = rf.getTipoRubro();
					listaTiposRubros.add(tr);
					String nombre = tr.getNombre();
					if (nombre != null && nombre.length() > 150) {
						nombre = nombre.substring(0, 150) + "...";
					}
					tiposRubroItem[i] = new SelectItem(tr.getId(), nombre);
					tr = null;
				}

			} else {

				if (tipoFuenteActual == 2) {
					tiposRubroItem = new SelectItem[0];
					mensajeError(botonAgregarGasto, "No hay rubros de contrapartida para las entidades externas");
				} else {

					if (listaRubrosContrapartida != null) {
						tiposRubroItem = new SelectItem[listaRubrosContrapartida.size()];
					} else {
						tiposRubroItem = new SelectItem[0];
					}

					listaTiposRubros = new ArrayList();
					for (int i = 0; listaRubrosContrapartida != null && i < listaRubrosContrapartida.size(); i++) {
						TipoRubro tr = (TipoRubro) listaRubrosContrapartida.get(i);
						listaTiposRubros.add(tr);
						String nombre = tr.getNombre();
						if (nombre != null && nombre.length() > 150) {
							nombre = nombre.substring(0, 150) + "...";
						}
						tiposRubroItem[i] = new SelectItem(tr.getId(), nombre);
						tr = null;
					}
				}
			}

		} else {
			mensajeError(
					"Seleccione un tipo de rubro para listar los rubros permitidos, ya se para financiar en efectivo o especie");
		}

	}

	public void cargarTiposRubro() {
		try {
			String hql = "select r from TipoRubro r where r.quipu= 'S' and r.padre.id= 0";
			System.out.print(hql);
			List lista = servicioGeneral.obtenerObjetos(hql);

			if (lista.size() > 0) {
				for (int i = 0; i < lista.size(); i++) {
					TipoRubro dominio = (TipoRubro) lista.get(i);
					String nombre = dominio.getNombre();
					if (nombre != null && nombre.length() > 120) {
						nombre = nombre.substring(0, 120) + "...";
					}
					listaRubrosFinanciables.add(new SelectItem(dominio.getId(), nombre));
					idtipoRubro = dominio.getId();
				}
			} else {
				listaRubrosFinanciables.add(new SelectItem("0", " - "));
				idtipoRubro = 3L;
			}

		} catch (Exception e) {
			System.out.println("Error cargando tipos de Rubro");
			e.printStackTrace();
		}
	}

	public void cargarSubTiposRubro() {
		System.out.println("El rubro seleccionado es: " + idtipoRubro);
		listaSubRubrosFinanciables = new ArrayList<SelectItem>();

		try {
			String hql = "select r from TipoRubro r where r.quipu= 'S' and r.padre.id= " + idtipoRubro;
			System.out.print(hql);
			List lista = servicioGeneral.obtenerObjetos(hql);

			if (lista.size() > 0) {
				for (int i = 0; i < lista.size(); i++) {
					TipoRubro dominio = (TipoRubro) lista.get(i);
					String nombre = dominio.getNombre();
					if (nombre != null && nombre.length() > 120) {
						nombre = nombre.substring(0, 120) + "...";
					}
					listaSubRubrosFinanciables.add(new SelectItem(dominio.getId(), nombre));
					idsubtipoRubro = dominio.getId();
				}
			} else {
				listaSubRubrosFinanciables.add(new SelectItem("0", " - "));
				idsubtipoRubro = 1L;
			}

		} catch (Exception e) {
			System.out.println("Error cargando subtipos de Rubro prev");
			e.printStackTrace();
		}
	}

	private void cargarSubTiposRubroPrev() {
		try {
			String hql = "select r from TipoRubro r where r.quipu= 'S' and r.padre.id= " + idtipoRubro;
			System.out.print(hql);
			List lista = servicioGeneral.obtenerObjetos(hql);

			if (lista.size() > 0) {
				for (int i = 0; i < lista.size(); i++) {
					TipoRubro dominio = (TipoRubro) lista.get(i);
					String nombre = dominio.getNombre();
					if (nombre != null && nombre.length() > 120) {
						nombre = nombre.substring(0, 120) + "...";
					}
					listaSubRubrosFinanciables.add(new SelectItem(dominio.getId(), nombre));
					idsubtipoRubro = dominio.getId();
				}
			} else {
				listaSubRubrosFinanciables.add(new SelectItem("0", " - "));
				idsubtipoRubro = 1L;
			}
		} catch (Exception e) {
			System.out.println("Error cargando subtipos de Rubro prev");
			e.printStackTrace();
		}
	}

	public int contarNumeroEstudiates() {
		int valor = 0;
		if (listaParticipantes != null && listaParticipantes.size() > 0) {
			for (InvestigadorProyecto ip : listaParticipantes) {
				if (ip != null && (ip.getTipo().getId().equals("A") || ip.getTipo().getId().equals("AL")
						|| ip.getTipo().getId().equals("ESPO") || ip.getTipo().getId().equals("ESPR")
						|| ip.getTipo().getId().equals("ESEX"))) {
					valor++;
				}
			}
		}
		return valor;
	}

	/**
	 * Validar monto fuente.
	 * 
	 * @return true, if successful
	 */
	public boolean validarMontoFuente() {
		boolean val = true;

		if (proyectoActual.getListaFuentesFinancacionFicha()!=null && proyectoActual.getListaFuentesFinancacionFicha().size() > 0) {
			Financiacion fin = proyectoActual.getListaFuentesFinancacionFicha().get(0);
			if (fin != null && fin.getFuente().esInterna() && fin.calcularValorEfectivoFinanciacion() > Long.parseLong(convocatoriaActual.getMontoApoyoGanadores())) {
				val = false;
				mensajeError("El monto solicitado supera el máximo de la convocatoria");
			}
		}

		return val;
	}
	
	public boolean validarMontoFuente(Financiacion fin, Long value) {
		boolean val = true;
			if (fin != null && fin.getFuente().esInterna() && (fin.calcularValorEfectivoFinanciacion() + value) > Long.parseLong(convocatoriaActual.getMontoApoyoGanadores())) {
				val = false;
				mensajeError("El monto solicitado supera el máximo de la convocatoria");
			}
		return val;
	}

	/**
	 * Eliminar gasto conv.
	 */
	public void eliminarGastoConv() {
		Financiacion financiacion = gastoSeleccionado.getFinanciacion();
		financiacion.borrarGasto(gastoSeleccionado);
		if(gastoSeleccionado.getFinanciacion().getListaGastos().isEmpty()) {
			proyectoActual.eliminarFuente(gastoSeleccionado.getFinanciacion());
		}
	}

	/**
	 * Metodo para agregar un gasto en formulario sencillo.
	 */
	public void agregarGasto() {

		boolean error = false;
		
		if (tipoFuenteActual <= 0) {
			error = true;
			mensajeError(botonAgregarGasto, "Debe seleccionar un tipo de fuente de financiación.");
		}

		if (idTipoRubro <= 0) {
			error = true;
			mensajeError(botonAgregarGasto, "Debe seleccionar un tipo de rubro.");
		}

		if (valorGasto <= 0) {
			error = true;
			mensajeError(botonAgregarGasto, "El valor del rubro debe ser mayor a cero.");
		}

		if (esCadenaVacia(descripcionGasto)) {
			error = true;
			mensajeError(botonAgregarGasto, "La descripción se encuentra vacio.");
		} else {
			if (descripcionGasto.length() > 3000) {
				error = true;
				mensajeError(botonAgregarGasto, "La descripción supera el número de caracteres permitidos.");
			}
		}

		if (!error) {
			try {
				Financiacion finSeleccionada = new Financiacion();
				FuenteFinanciacion fuenteSeleccionada = new FuenteFinanciacion();
				if (tipoFuenteActual == 1) {
					fuenteSeleccionada = (FuenteFinanciacion) servicioGeneral.obtenerObjeto(new FuenteFinanciacion(), fuenteActualIntId);
				} else {
					fuenteSeleccionada = (FuenteFinanciacion) servicioGeneral.obtenerObjeto(new FuenteFinanciacion(), financiadoraExterna);

				}
				if(!validarFinanciacionRubro(fuenteSeleccionada)) {
					return;
				}
				boolean encontroFuente = false;
				if (proyectoActual.getListaFinancionesFicha() != null
						&& !proyectoActual.getListaFinancionesFicha().isEmpty()) {
					for (int i = 0; i < proyectoActual.getListaFinancionesFicha().size(); i++) {
						if (proyectoActual.getListaFinancionesFicha().get(i).getTipoEntidad()==null &&
								(proyectoActual.getListaFinancionesFicha().get(i).getFuente().getId()
								.equals(financiadoraExterna)
								|| proyectoActual.getListaFinancionesFicha().get(i).getFuente().getId()
										.equals(fuenteActualIntId))) {
							finSeleccionada = proyectoActual.getListaFinancionesFicha().get(i);
							encontroFuente = true;
							break;
						} 
					}
					if(!encontroFuente) {
						finSeleccionada.setFuente(fuenteSeleccionada);
						proyectoActual.adicionarFinanciacion(finSeleccionada);
					}
				} else {
					finSeleccionada.setFuente(fuenteSeleccionada);
					proyectoActual.adicionarFinanciacion(finSeleccionada);
				}
				
				if (idNaturalezaRubro.equals(VariablesEstaticas.RUBRO_EFECTIVO)){
						if(!validarMontoFuente(finSeleccionada,valorGasto)) {
							mensajeError(botonAgregarGasto, "El monto solicitado supera el máximo de la convocatoria.");
							return;
						}
				}
				
				Gasto gastoActual = new Gasto();
				gastoActual.setValor(valorGasto);
				// Se asigna por defecto 1 a la cantidad y la vigencia
				gastoActual.setCantidad(1);
				gastoActual.setVigencia(1);
				descripcionGasto = controlTamanoCadena(descripcionGasto, 3000);
				if (idNaturalezaRubro.equals(VariablesEstaticas.RUBRO_EFECTIVO)) {
					gastoActual.setDescripcion(descripcionGasto);
				} else {
					gastoActual.setDescripcion("[Contrapartida] " + descripcionGasto);
				}
				gastoActual.setFinanciacion(finSeleccionada);
				TipoRubro tipoRubro = buscarTipoRubro(idTipoRubro);
				if (tipoRubro != null) {
					gastoActual.setTipoRubro(buscarTipoRubro(idTipoRubro));
					finSeleccionada.adicionarGasto(gastoActual);
					descripcionGasto = "";
					valorGasto = 0L;
					idTipoRubro = 0L;
					tipoFuenteActual = 0;
					fuenteActualIntId = "";
					financiadoraExterna = "";
					finSeleccionada.calcularValorEfectivoFinanciacion();
					finSeleccionada.calcularValorEspecieFinanciacion();
					if (!convocatoriaActual.isIncluirFinanciacionExterna()) {
						tipoFuenteActual = 1;
					}
				} else {
					mensajeError(botonAgregarGasto, "No se ha podido agregar el rubro seleccionado.");
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
	private boolean validarFinanciacionRubro(FuenteFinanciacion fuente) {
		boolean val = true;

		if (proyectoActual.getListaFinancionesFicha() != null && !proyectoActual.getListaFinancionesFicha().isEmpty()) {
			
			int numeroRubros = getListaGastos().size();
			for (int i = 0; i < numeroRubros; i++) {
				Gasto g = (Gasto) getListaGastos().get(i);
				if (g.getTipoRubro().getId().equals(this.idTipoRubro) && g.getFinanciacion().getFuente().getId().equals(fuente.getId())) {
					val = false;
					mensajeError(botonAgregarGasto, "El rubro ya se encuentra registrado para la fuente seleccionada");
					break;
				}
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
		List<Gasto> lista = new ArrayList<Gasto>();

		if (proyectoActual.getListaFuentesFinancacionFicha() != null
				&& !proyectoActual.getListaFuentesFinancacionFicha().isEmpty()) {
			for (int i = 0; i < proyectoActual.getListaFuentesFinancacionFicha().size(); i++) {
				Financiacion f = (Financiacion) proyectoActual.getListaFuentesFinancacionFicha().get(i);
				if (f.getGastos() != null) {
					Iterator<Gasto> g = f.getGastos().iterator();
					while (g.hasNext()) {
						Gasto gasto = g.next();
						if (gasto.getSumaCampos() > 0) {
							lista.add(gasto);
						}
					}
				}
			}
		}
		return lista;
	}

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

	private RubroFinanciable buscarRubroFinanciable(TipoRubro tr) {
		for (int i = 0; i < listaRubrosFinanciables.size(); i++) {
			RubroFinanciable rf = (RubroFinanciable) listaRubrosFinanciables.get(i);
			TipoRubro traux = rf.getTipoRubro();
			if (tr.getId().longValue() == traux.getId().longValue()) {
				return rf;
			}
		}
		return null;
	}

	private boolean validarResultados() {
		// VALIDA LA EXISTENCIA DE RESULTADOS EN LA RESPECTIVA LISTA
		List listaAuxiliar = new ArrayList();
		for (int i = 0; i < listaResultados.size(); i++) {
			ResultadoProyecto rp = (ResultadoProyecto) listaResultados.get(i);
			if (rp.isBorrable()) {
				listaAuxiliar.add(rp);
				proyectoActual.borrarResultado(rp);
			} else {
				proyectoActual.adicionarResultado(rp);
			}
		}
		listaResultados.removeAll(listaAuxiliar);
		if (listaResultados.isEmpty()) {
			return false;
		}
		return true;
	}

	public void revisarPais() {
		if (proyectoActual.getPaisEvento() != null && !proyectoActual.getPaisEvento().equals("")
				&& proyectoActual.getPaisEvento().equals("CO")) {
			siColombia = true;
			obtenerListaDepartamentos();
			obtenerListaCiudades();
		} else {
			siColombia = false;
			obtenerListaCiudadesDiferentesColombia();
		}
	}

	private void obtenerListaCiudadesDiferentesColombia() {
		String idPaisBus = proyectoActual.getPaisEvento().substring(0, 2);
		String hql = "select #id e.id, #nombre e.nombre from Ciudad e where e.id like ('" + idPaisBus + "%')";
		listaCiudades = servicioGeneral.obtenerObjetosLimitado(Ciudad.class, hql);
		ciudadItem = new SelectItem[listaCiudades.size()];
		for (int i = 0; i < listaCiudades.size(); i++) {
			Ciudad ci = (Ciudad) listaCiudades.get(i);
			ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	// FIN INFORMACIÓN FINANCIERA PROYECTO

	public boolean validarConvocatoriaEventos() {
		boolean val = true;
		Date hoy = new Date();
		if (this.proyectoActual.getFechaTentativaInicio() == null) {
			FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar la fecha de inicio", ""));
			return false;

		}
		if (this.proyectoActual.getFechaFinalizacion() == null) {
			FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar la fecha de terminación", ""));
			return false;

		}

		if (StringUtils.isNotBlank(this.proyectoActual.getMaxAsistentes())) {
			Long valor = 0L;
			boolean error = false;
			try {
				valor = Long.parseLong(proyectoActual.getMaxAsistentes());
			} catch (NumberFormatException e) {
				valor = 0L;
				error = true;
			}
			if (error) {
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar un numero correcto de estudiantes", ""));
			}
		}
		if (!proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.ELEGIBLE)
				&& !proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.APROBADO)) {
			if (hoy.after(this.proyectoActual.getFechaTentativaInicio())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fecha de inicio incorrecta", ""));
			} else {
				if (this.proyectoActual.getFechaFinalizacion().before(this.proyectoActual.getFechaTentativaInicio())) {
					val = false;
					FacesContext.getCurrentInstance().addMessage("msgs",
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fecha de terminación incorrecta", ""));
				} else {
					if (convocatoriaActual.getRestriccion().getId().contains("CE1")) {
						int diasFechaAplicacion;

						if (convocatoriaActual.getRequisitosConvTexto() != null) {
							diasFechaAplicacion = Integer.parseInt(convocatoriaActual.getRequisitosConvTexto());
						} else {
							diasFechaAplicacion = 60;
						}

						Calendar cal1 = Calendar.getInstance();
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(this.proyectoActual.getFechaTentativaInicio());
						cal2.add(Calendar.DATE, -diasFechaAplicacion);

						if (cal2.before(cal1)) {
							val = false;
							FacesContext.getCurrentInstance().addMessage("msgs",
									new FacesMessage(FacesMessage.SEVERITY_ERROR,
											"La fecha del evento debe ser superior que la fecha actual en "
													+ diasFechaAplicacion + " días calendario.",
											""));
						} else {
						}

					} else {
						int diasFechaAplicacion;

						if (convocatoriaActual.getPadre().getId().equals(484L)
								&& (this.proyectoActual.getLogistica().equals("1")
										|| this.proyectoActual.getClaseEvento().equals("EV"))) {
							diasFechaAplicacion = 20;
						} else if (convocatoriaActual.getRequisitosConvTexto() != null) {
							diasFechaAplicacion = Integer.parseInt(convocatoriaActual.getRequisitosConvTexto());
						} else {
							diasFechaAplicacion = 45;
						}

						Calendar cal1 = Calendar.getInstance();
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(this.proyectoActual.getFechaTentativaInicio());
						cal2.add(Calendar.DATE, -diasFechaAplicacion);

						if (cal2.before(cal1)) {
							val = false;
							FacesContext.getCurrentInstance().addMessage("msgs",
									new FacesMessage(FacesMessage.SEVERITY_ERROR,
											"La fecha del evento debe ser superior que la fecha actual en "
													+ diasFechaAplicacion + " días calendario.",
											""));
						} else {
						}

					}
				}
			}
		}

		if (esCadenaVacia(proyectoActual.getNombre())) {
			val = false;
			FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar el nombre del evento.", ""));
		}

		if (esCadenaVacia(proyectoActual.getPaisEvento())) {
			val = false;
			FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe ingresar el país donde se realizará el evento.", ""));
		}

		if (siColombia) {
			if (esCadenaVacia(ciudadActual.getId())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar la ciudad donde se realizará el evento.", ""));
			}
		}

		if (esCadenaVacia(proyectoActual.getJustificacion())) {
			val = false;
			if (esConvMoots2021_M1 || esConvMoots2021_M2) {
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar la Justificación de la participación y estrategia pedagógica.", ""));
			} else {
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar la justificación del evento.", ""));
			}
		}

		if (proyectoActual.getClaseEvento().equals("0")) {
			val = false;
			FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar el tipo de evento.", ""));
		}

		if (esCadenaVacia(proyectoActual.getMaxAsistentes())) {
			val = false;
			FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe ingresar el número de estudiantes que apoyan el evento.", ""));
		}

		if (esCadenaVacia(proyectoActual.getLugar())) {
			val = false;
			FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe ingresar el lugar de realización del evento.", ""));
		}

		if (!esConvMoots2019) {

			if (esListaVacia(this.getListaGastos())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar la información del presupuesto del evento.", ""));
			}

			if (esListaVacia(proyectoActual.getListaDependenciasAreaResponsabilidad())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar las dependencias de la UN organizadoras del evento.", ""));
			}
		}

		if (esConvMoots2019) {

			if (objetivoDesarrolloSosteniblePrimario.equals("0")) {
				val = false;
				mensajeError(btnObjDesSosSec,
						"Debe asociar al proyecto el objetivo de desarrollo sostenible principal.");
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
						val = false;
						mensajeError(btnObjDesSosSec,
								"El objetivo de desarrollo sostenible se encuentra vinculado como principal y secundario.");
					}
				}
			}

			if (esCadenaVacia(proyectoActual.getDescripcion())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar la relación del evento con los Objetivos de Desarrollo Sostenible", ""));
			}

			if (esCadenaVacia(proyectoActual.getObjetivoGeneral())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar el objetivo general del evento.", ""));
			}

			if (esListaVacia(proyectoActual.getListaObjetivos())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar los objetivos específicos del evento.", ""));
			}

			if (convocatoriaActual.getNumeroProductosObligatorios() != null) {
				if (convocatoriaActual.getNumeroProductosObligatorios() > 0) {
					if (proyectoActual.getListaProductosProyecto().isEmpty()) {
						val = false;
						mensajeError("Debe registrar como mínimo " + convocatoriaActual.getNumeroProductosObligatorios()
								+ " productos académicos.");
					}
				}
			}
		}

		if (esConvEventos2019 || esConvocatoriaEventos) {

			if (objetivoDesarrolloSosteniblePrimario.equals("0")) {
				val = false;
				mensajeError(btnObjDesSosSec,
						"Debe asociar al proyecto el objetivo de desarrollo sostenible principal.");
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
						val = false;
						mensajeError(btnObjDesSosSec,
								"El objetivo de desarrollo sostenible se encuentra vinculado como principal y secundario.");
					}
				}
			}

			if (esCadenaVacia(proyectoActual.getDescripcion())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar la relación del evento con los Objetivos de Desarrollo Sostenible.", ""));
			}

			if (esCadenaVacia(proyectoActual.getObjetivoGeneral())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar el objetivo general del evento.", ""));
			}

			if (esListaVacia(proyectoActual.getListaObjetivos())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar los objetivos específicos del evento.", ""));
			}

			if (esCadenaVacia(proyectoActual.getCuposDescuento().toString())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar el número total de cupos del evento.", ""));
			}

			if (proyectoActual.getDescripcionLogistica() != null
					&& proyectoActual.getDescripcionLogistica().equals("0")) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar la población objetivo del evento.", ""));
			}

			if (esCadenaVacia(proyectoActual.getLogistica())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar la metodología del evento.", ""));
			}

			if (esCadenaVacia(proyectoActual.getHoraSesion())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar la intensidad horaria del evento.", ""));
			}

			if (esCadenaVacia(proyectoActual.getCampoGenericoOcho())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar la estrategia de divulgación para asegurar la participación y difusión de resultados y memorias.",
						""));
			}

			if (esCadenaVacia(proyectoActual.getTipoCertificacion())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar si se otorgará certificación a los asistentes.", ""));
			}

			if (esCadenaVacia(proyectoActual.getSistCalificacion())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar si el evento tendrá sistema de calificación.", ""));
			}

			Boolean opcionExternoSel = false;
			for (String opcion : selectedPoblacionObjetivo) {
				if (opcion.equals("4")) {
					opcionExternoSel = true;
				}
			}

			if (!opcionExternoSel) {
				proyectoActual.setGrupoDirige(null);
				proyectoActual.setPoblacionDirige(null);
			}

			if (opcionExternoSel) {
				if (esNulo(proyectoActual.getGrupoDirige()) || proyectoActual.getGrupoDirige().equals("0")) {
					val = false;
					FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Debe seleccionar el grupo al que se dirige el evento.", ""));
				}

				if (esNulo(proyectoActual.getPoblacionDirige()) || proyectoActual.getPoblacionDirige().equals("0")) {
					val = false;
					FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Debe seleccionar la población a la que se dirige el evento.", ""));
				}
			}
		}

		if (convocatoriaActual.getRestriccionEquipoTrabajo() != null) {
			String[] valEquipoTrabajo = convocatoriaActual.getRestriccionEquipoTrabajo().split("-");
			String[] equipoTrabajoO = valEquipoTrabajo[0].split(",");
			String[] equipoTrabajoY = valEquipoTrabajo[1].split(",");
			ArrayList<EquipoTrabajoValidacion> listaEquipoTrabajoValidacionO = new ArrayList<ManejadorFichaMinimaProyectos.EquipoTrabajoValidacion>();
			ArrayList<EquipoTrabajoValidacion> listaEquipoTrabajoValidacionY = new ArrayList<ManejadorFichaMinimaProyectos.EquipoTrabajoValidacion>();
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

				val = false;
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
							}
						}
					}
				}

				int valFalO = 0;
				int valFalY = 0;

				if (validarO) {
					for (int j = 0; j < listaEquipoTrabajoValidacionO.size(); j++) {
						if (listaEquipoTrabajoValidacionO.get(j).getTi().equals("A") && (listaEquipoTrabajoValidacionO
								.get(j)
								.getNumObligatorio() > listaEquipoTrabajoValidacionO.get(j).getNumVinculados())) {
							valFalO++;
						} else if (listaEquipoTrabajoValidacionO.get(j)
								.getNumObligatorio() > listaEquipoTrabajoValidacionO.get(j).getNumVinculados()) {
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
					val = false;
					mensajeError(convocatoriaActual.getTextoValidacionEquipoTrabajo());
				}

			}
		}

		return val;
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

	@Override
	protected void cargarValoresIniciales() {

	}

	@Override
	public String atras() {

		return null;
	}

	@Override
	public String salir() {

		return null;
	}

	@Override
	public String salirGuardar() {

		if (!mostrarSiConvocatoriaEventos) {

			if (validarNombre_InvPpal() && validarMontoFuente()) {

				if (proyectoActual.getId() != null) {
					// Ing. Wilver Alexander Martínez Martínez -wam²
					// Cambio - Registro de cambios
					Persona personaAux = new Persona();
					personaAux = (Persona) sesion.getAttribute("persona");

					Formulario formulario = new Formulario();
					List listaFormulario = new ArrayList();

					listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='0'");
					formulario = (Formulario) listaFormulario.get(0);

					HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
					historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
					historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
					historicoFormualrioProyecto.setFormulario(formulario);
					historicoFormualrioProyecto.setProyecto(proyectoActual);
					historicoFormualrioProyecto.setFechaCambio(new Date());
					servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
				}

				guardarFichaMinimaProyecto();

				if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null) {
					// Se comenta el envio de correo por que en BD no esta creada la plantilla de
					// correo 326 y se desconoce el motivo,
					// lo cual genera excepcion al guardar con rol Estudiante Lider
//					enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
				}

				sesion.removeAttribute("manejadorFichaMinimaProyectos");

				ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
						.getAttribute("manejadorMenuFormularios");

				boolean bandera = false;

				sesion.removeAttribute("manejadorMenuFormularios");

				return "misProyectos";

			}

		} else {

			if (validarConvocatoriaEventos() && validarMontoFuente()) {

				if (proyectoActual.getId() != null) {
					Persona personaAux = new Persona();
					personaAux = (Persona) sesion.getAttribute("persona");

					Formulario formulario = new Formulario();
					List listaFormulario = new ArrayList();

					listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='0'");
					formulario = (Formulario) listaFormulario.get(0);

					HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
					historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
					historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
					historicoFormualrioProyecto.setFormulario(formulario);
					historicoFormualrioProyecto.setProyecto(proyectoActual);
					historicoFormualrioProyecto.setFechaCambio(new Date());
					servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
				}

				guardarConvocatoriaEventos();

				mensajeInfo(
						"Su proyecto ha sido registrado correctamente con el código: " + proyectoActual.getId() + ".");

				if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null) {
					// Se comenta el envio de correo por que en BD no esta creada la plantilla de
					// correo 326 y se desconoce el motivo,
					// lo cual genera excepcion al guardar con rol Estudiante Lider
//					enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
				}

//				sesion.removeAttribute("manejadorFichaMinimaProyectos");
//
//				ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
//
//				boolean bandera = false;
//				sesion.removeAttribute("manejadorMenuFormularios");

				return "";
			}

		}

		return "";
	}

	@Override
	public String siguiente() {

		if (!mostrarSiConvocatoriaEventos) {

			if (validarNombre_InvPpal() && validarMontoFuente()) {

				String link = "";
				ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
						.getAttribute("manejadorMenuFormularios");
				boolean bandera = false;
				int pos = 0;
				if (man != null && man.getItemProyecto() != null) {
					MenuItem lis[] = man.getMenuItemArray();
					if (lis != null) {
						for (int i = 0; i < lis.length; i++) {
							if (bandera) {
								if (lis[i].isRendered()) {
									sesion.removeAttribute("manejadorMenuFormularios");

									link = lis[i].getOutcome();
									break;
								}
							}

							if (lis[i].getOutcome().equals("irFichaMinima")
									|| lis[i].getOutcome().equals("irFichaMinimaOtraConv")
									|| lis[i].getOutcome().equals("irFichaMinimaPurdue")) {
								bandera = true;
							}
							if (lis[i].isRendered()) {
								pos++;
							}
						}
					}
				}

				if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
						&& (pos - 1) == proyectoActual.getFase().intValue()) {
					proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
				}

				if (proyectoActual.getId() != null) {
					// Ing. Wilver Alexander Martínez Martínez -wam²
					// Cambio - Registro de cambios
					Persona personaAux = new Persona();
					personaAux = (Persona) sesion.getAttribute("persona");

					Formulario formulario = new Formulario();
					List listaFormulario = new ArrayList();

					listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='0'");
					formulario = (Formulario) listaFormulario.get(0);

					HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
					historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
					historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
					historicoFormualrioProyecto.setFormulario(formulario);
					historicoFormualrioProyecto.setProyecto(proyectoActual);
					historicoFormualrioProyecto.setFechaCambio(new Date());
					servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
				}

				guardarFichaMinimaProyecto();

				if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null) {
					// Se comenta el envio de correo por que en BD no esta creada la plantilla de
					// correo 326 y se desconoce el motivo,
					// lo cual genera excepcion al guardar con rol Estudiante Lider
//					enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
				}

				if (proyectoActual.getModalidad().getTipo().getId().equals("CFM")
						|| proyectoActual.getModalidad().getTipo().getId().equals("CPU")
						|| proyectoActual.getModalidad().getTipo().getId().equals("CMP")
						|| proyectoActual.getModalidad().getTipo().getId().equals("CEQ")) {
					return "irInformacionEspecifica";
				}
				return "irInvestigadores";

			}

		} else {

			if (validarConvocatoriaEventos() && validarMontoFuente()) {

				String link = "";
				ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
						.getAttribute("manejadorMenuFormularios");
				boolean bandera = false;
				int pos = 0;
				if (man.getItemProyecto() != null) {
					MenuItem lis[] = man.getMenuItemArray();
					if (lis != null) {
						for (int i = 0; i < lis.length; i++) {
							if (bandera) {
								if (lis[i].isRendered()) {
									sesion.removeAttribute("manejadorMenuFormularios");

									link = lis[i].getOutcome();
									break;
								}
							}

							if (lis[i].getOutcome().equals("irFichaMinima")) {
								bandera = true;
							}
							if (lis[i].isRendered()) {
								pos++;
							}
						}
					}
				}

				if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
						&& (pos - 1) == proyectoActual.getFase().intValue()) {
					proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
				}

				if (proyectoActual.getId() != null) {
					// Ing. Wilver Alexander Martínez Martínez -wam²
					// Cambio - Registro de cambios
					Persona personaAux = new Persona();
					personaAux = (Persona) sesion.getAttribute("persona");

					Formulario formulario = new Formulario();
					List listaFormulario = new ArrayList();

					listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='0'");
					formulario = (Formulario) listaFormulario.get(0);

					HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
					historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
					historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
					historicoFormualrioProyecto.setFormulario(formulario);
					historicoFormualrioProyecto.setProyecto(proyectoActual);
					historicoFormualrioProyecto.setFechaCambio(new Date());
					servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
				}

				guardarConvocatoriaEventos();

				if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null) {
					// Se comenta el envio de correo por que en BD no esta creada la plantilla de
					// correo 326 y se desconoce el motivo,
					// lo cual genera excepcion al guardar con rol Estudiante Lider
//					enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
				}

				if (proyectoActual.getModalidad().getTipo().getId().equals("CFM")
						|| proyectoActual.getModalidad().getTipo().getId().equals("CPU")) {
					return "irInformacionEspecifica";
				}
				return "irInvestigadores";

			}

		}

		return "";

	}

	protected List cargarCortes(List cortes) {
		String consulta = "select p from Parametro p where p.nombre = 'CONV_CORTE' and p.valor = '"
				+ convocatoriaActual.getPadre().getId() + "' and p.profesion = 'A' order by p.id";
		List listaCortes = servicioGeneral.obtenerObjetos(consulta);
		cortes = new Vector();
		SelectItem stemp = new SelectItem("0", "Seleccione un corte");
		cortes.add(stemp);
		for (Iterator it = listaCortes.iterator(); it.hasNext();) {
			Parametro p = (Parametro) it.next();
			SelectItem s = new SelectItem(p.getId(), p.getDescripcion());
			cortes.add(s);
		}
		return cortes;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public SelectItem[] getCategoriaItems() {
		return categoriaItems;
	}

	public void setCategoriaItems(SelectItem[] categoriaItems) {
		this.categoriaItems = categoriaItems;
	}

	public String getMecanismoSolicitud() {
		return mecanismoSolicitud;
	}

	public void setMecanismoSolicitud(String mecanismoSolicitud) {
		this.mecanismoSolicitud = mecanismoSolicitud;
	}

	public SelectItem[] getMecanismoSolicitudItems() {
		return mecanismoSolicitudItems;
	}

	public void setMecanismoSolicitudItems(SelectItem[] mecanismoSolicitudItems) {
		this.mecanismoSolicitudItems = mecanismoSolicitudItems;
	}

	public List<DominioDetalle> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<DominioDetalle> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public List<DominioDetalle> getListaMecanismoSolicitud() {
		return listaMecanismoSolicitud;
	}

	public void setListaMecanismoSolicitud(List<DominioDetalle> listaMecanismoSolicitud) {
		this.listaMecanismoSolicitud = listaMecanismoSolicitud;
	}

	public SelectItem[] getRolUniversidadItems() {
		return rolUniversidadItems;
	}

	public void setRolUniversidadItems(SelectItem[] rolUniversidadItems) {
		this.rolUniversidadItems = rolUniversidadItems;
	}

	public List<DominioDetalle> getListaRolUniversidad() {
		return listaRolUniversidad;
	}

	public void setListaRolUniversidad(List<DominioDetalle> listaRolUniversidad) {
		this.listaRolUniversidad = listaRolUniversidad;
	}

	public String getRolUniversidad() {
		return rolUniversidad;
	}

	public void setRolUniversidad(String rolUniversidad) {
		this.rolUniversidad = rolUniversidad;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public String getObjetivoGeneral() {
		return objetivoGeneral;
	}

	public void setObjetivoGeneral(String objetivoGeneral) {
		this.objetivoGeneral = objetivoGeneral;
	}

	public String getResumenProyecto() {
		return resumenProyecto;
	}

	public void setResumenProyecto(String resumenProyecto) {
		this.resumenProyecto = resumenProyecto;
	}

	public DataTable getTablaObjetivos() {
		return tablaObjetivos;
	}

	public void setTablaObjetivos(DataTable tablaObjetivos) {
		this.tablaObjetivos = tablaObjetivos;
	}

	public List<ObjetivoEspecifico> getListaObjetivos() {
		return listaObjetivos;
	}

	public void setListaObjetivos(List<ObjetivoEspecifico> listaObjetivos) {
		this.listaObjetivos = listaObjetivos;
	}

	public List<ResultadoProyecto> getListaResultados() {
		return listaResultados;
	}

	public void setListaResultados(List<ResultadoProyecto> listaResultados) {
		this.listaResultados = listaResultados;
	}

	public DataTable getTablaResultados() {
		return tablaResultados;
	}

	public void setTablaResultados(DataTable tablaResultados) {
		this.tablaResultados = tablaResultados;
	}

	public String getObjetivoEspecifico() {
		return objetivoEspecifico;
	}

	public void setObjetivoEspecifico(String objetivoEspecifico) {
		this.objetivoEspecifico = objetivoEspecifico;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public ObjetivoEspecifico getObjetivoTabla() {
		return objetivoTabla;
	}

	public void setObjetivoTabla(ObjetivoEspecifico objetivoTabla) {
		this.objetivoTabla = objetivoTabla;
	}

	public ResultadoProyecto getResultadoTabla() {
		return resultadoTabla;
	}

	public void setResultadoTabla(ResultadoProyecto resultadoTabla) {
		this.resultadoTabla = resultadoTabla;
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

	public UISelectOne getManejadorProductoNivel1() {
		return manejadorProductoNivel1;
	}

	public void setManejadorProductoNivel1(UISelectOne manejadorProductoNivel1) {
		this.manejadorProductoNivel1 = manejadorProductoNivel1;
	}

	public UISelectOne getManejadorProductoNivel2() {
		return manejadorProductoNivel2;
	}

	public void setManejadorProductoNivel2(UISelectOne manejadorProductoNivel2) {
		this.manejadorProductoNivel2 = manejadorProductoNivel2;
	}

	public UISelectOne getManejadorProductoNivel3() {
		return manejadorProductoNivel3;
	}

	public void setManejadorProductoNivel3(UISelectOne manejadorProductoNivel3) {
		this.manejadorProductoNivel3 = manejadorProductoNivel3;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
	}

	public PalabraClave getPalabraClave() {
		return palabraClave;
	}

	public void setPalabraClave(PalabraClave palabraClave) {
		this.palabraClave = palabraClave;
	}

	public PalabraClave getPalabraClaveTabla() {
		return palabraClaveTabla;
	}

	public void setPalabraClaveTabla(PalabraClave palabraClaveTabla) {
		this.palabraClaveTabla = palabraClaveTabla;
	}

	public PalabraClave getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(PalabraClave keyWord) {
		this.keyWord = keyWord;
	}

	public EstadoProyecto getEstadoProyectoActual() {
		return estadoProyectoActual;
	}

	public void setEstadoProyectoActual(EstadoProyecto estadoProyectoActual) {
		this.estadoProyectoActual = estadoProyectoActual;
	}

	public TipoDuracion getTipoDuracionActual() {
		return tipoDuracionActual;
	}

	public void setTipoDuracionActual(TipoDuracion tipoDuracionActual) {
		this.tipoDuracionActual = tipoDuracionActual;
	}

	public UIData getTablaPalabras() {
		return tablaPalabras;
	}

	public void setTablaPalabras(UIData tablaPalabras) {
		this.tablaPalabras = tablaPalabras;
	}

	public UIData getTablaKeyswords() {
		return tablaKeyswords;
	}

	public void setTablaKeyswords(UIData tablaKeyswords) {
		this.tablaKeyswords = tablaKeyswords;
	}

	public SelectItem[] getSiNoItem() {
		return siNoItem;
	}

	public void setSiNoItem(SelectItem[] siNoItem) {
		this.siNoItem = siNoItem;
	}

	public ProyectoProducto getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setProductoSeleccionado(ProyectoProducto productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

	public List getListaAreaCiencia() {
		return listaAreaCiencia;
	}

	public void setListaAreaCiencia(List listaAreaCiencia) {
		this.listaAreaCiencia = listaAreaCiencia;
	}

	public List getListaTipoVinculacion() {
		return listaTipoVinculacion;
	}

	public void setListaTipoVinculacion(List listaTipoVinculacion) {
		this.listaTipoVinculacion = listaTipoVinculacion;
	}

	public List getListaObjSocioeconomico() {
		return listaObjSocioeconomico;
	}

	public void setListaObjSocioeconomico(List listaObjSocioeconomico) {
		this.listaObjSocioeconomico = listaObjSocioeconomico;
	}

	public SelectItem[] getAreaCienciaItems() {
		return areaCienciaItems;
	}

	public void setAreaCienciaItems(SelectItem[] areaCienciaItems) {
		this.areaCienciaItems = areaCienciaItems;
	}

	public SelectItem[] getTipoVinculacionItems() {
		return tipoVinculacionItems;
	}

	public void setTipoVinculacionItems(SelectItem[] tipoVinculacionItems) {
		this.tipoVinculacionItems = tipoVinculacionItems;
	}

	public SelectItem[] getObjSocioeconomicoItems() {
		return objSocioeconomicoItems;
	}

	public void setObjSocioeconomicoItems(SelectItem[] objSocioeconomicoItems) {
		this.objSocioeconomicoItems = objSocioeconomicoItems;
	}

	public List<String> getNoDuracion() {
		return noDuracion;
	}

	public void setNoDuracion(List<String> noDuracion) {
		this.noDuracion = noDuracion;
	}

	public List<PalabraClave> getListaPalabrasClave() {
		return listaPalabrasClave;
	}

	public void setListaPalabrasClave(List<PalabraClave> listaPalabrasClave) {
		this.listaPalabrasClave = listaPalabrasClave;
	}

	public String getDocumentoCoinv() {
		return documentoCoinv;
	}

	public void setDocumentoCoinv(String documentoCoinv) {
		this.documentoCoinv = documentoCoinv;
	}

	public TipoDocumento getTipoDocumentoCoInv() {
		return tipoDocumentoCoInv;
	}

	public void setTipoDocumentoCoInv(TipoDocumento tipoDocumentoCoInv) {
		this.tipoDocumentoCoInv = tipoDocumentoCoInv;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public String getUnidadAcademicaEjecutora() {
		return unidadAcademicaEjecutora;
	}

	public void setUnidadAcademicaEjecutora(String unidadAcademicaEjecutora) {
		this.unidadAcademicaEjecutora = unidadAcademicaEjecutora;
	}

	public Integer getHorasDirector() {
		return horasDirector;
	}

	public void setHorasDirector(Integer horasDirector) {
		this.horasDirector = horasDirector;
	}

	public String getDocumentoCoinv2() {
		return documentoCoinv2;
	}

	public void setDocumentoCoinv2(String documentoCoinv2) {
		this.documentoCoinv2 = documentoCoinv2;
	}

	public TipoDocumento getTipoDocumentoCoInv2() {
		return tipoDocumentoCoInv2;
	}

	public void setTipoDocumentoCoInv2(TipoDocumento tipoDocumentoCoInv2) {
		this.tipoDocumentoCoInv2 = tipoDocumentoCoInv2;
	}

	public String getUnidadAcademicaEjecutora2() {
		return unidadAcademicaEjecutora2;
	}

	public void setUnidadAcademicaEjecutora2(String unidadAcademicaEjecutora2) {
		this.unidadAcademicaEjecutora2 = unidadAcademicaEjecutora2;
	}

	public Integer getHorasParticipante() {
		return horasParticipante;
	}

	public void setHorasParticipante(Integer horasParticipante) {
		this.horasParticipante = horasParticipante;
	}

	public Integer getTiempoTotalParticipante() {
		return tiempoTotalParticipante;
	}

	public void setTiempoTotalParticipante(Integer tiempoTotalParticipante) {
		this.tiempoTotalParticipante = tiempoTotalParticipante;
	}

	public List<InvestigadorProyecto> getListaDirector() {
		return listaDirector;
	}

	public void setListaDirector(List<InvestigadorProyecto> listaDirector) {
		this.listaDirector = listaDirector;
	}

	public List<InvestigadorProyecto> getListaParticipantes() {
		return listaParticipantes;
	}

	public void setListaParticipantes(List<InvestigadorProyecto> listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
	}

	public List<TipoDocumento> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public SelectItem[] getCategoriaItem() {
		return categoriaItem;
	}

	public void setCategoriaItem(SelectItem[] categoriaItem) {
		this.categoriaItem = categoriaItem;
	}

	public long getValorPersonalTotal() {
		return valorPersonalTotal;
	}

	public void setValorPersonalTotal(long valorPersonalTotal) {
		this.valorPersonalTotal = valorPersonalTotal;
	}

	public String getUnidadEjecutora() {
		return unidadEjecutora;
	}

	public void setUnidadEjecutora(String unidadEjecutora) {
		this.unidadEjecutora = unidadEjecutora;
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

	public String getServiciosAcademicos() {
		return serviciosAcademicos;
	}

	public void setServiciosAcademicos(String serviciosAcademicos) {
		this.serviciosAcademicos = serviciosAcademicos;
	}

	public Long getIdtipoRubro() {
		return idtipoRubro;
	}

	public void setIdtipoRubro(Long idtipoRubro) {
		this.idtipoRubro = idtipoRubro;
	}

	public Long getIdsubtipoRubro() {
		return idsubtipoRubro;
	}

	public void setIdsubtipoRubro(Long idsubtipoRubro) {
		this.idsubtipoRubro = idsubtipoRubro;
	}

	public String getTipoRecurso() {
		return tipoRecurso;
	}

	public void setTipoRecurso(String tipoRecurso) {
		this.tipoRecurso = tipoRecurso;
	}

	public Long getValorGasto() {
		return valorGasto;
	}

	public void setValorGasto(Long valorGasto) {
		this.valorGasto = valorGasto;
	}

	public String getTipoContrapartida() {
		return tipoContrapartida;
	}

	public void setTipoContrapartida(String tipoContrapartida) {
		this.tipoContrapartida = tipoContrapartida;
	}

	public Long getValorContrapartida() {
		return valorContrapartida;
	}

	public void setValorContrapartida(Long valorContrapartida) {
		this.valorContrapartida = valorContrapartida;
	}

	public TipoRubro getTipoRubro() {
		return tipoRubro;
	}

	public void setTipoRubro(TipoRubro tipoRubro) {
		this.tipoRubro = tipoRubro;
	}

	public List getListaRubrosFinanciables() {
		return listaRubrosFinanciables;
	}

	public void setListaRubrosFinanciables(List listaRubrosFinanciables) {
		this.listaRubrosFinanciables = listaRubrosFinanciables;
	}

	public SelectItem[] getTiposRubroItem() {
		return tiposRubroItem;
	}

	public void setTiposRubroItem(SelectItem[] tiposRubroItem) {
		this.tiposRubroItem = tiposRubroItem;
	}

	public TipoRubro getSubtipoRubro() {
		return subtipoRubro;
	}

	public void setSubtipoRubro(TipoRubro subtipoRubro) {
		this.subtipoRubro = subtipoRubro;
	}

	public List<SelectItem> getListaSubRubrosFinanciables() {
		return listaSubRubrosFinanciables;
	}

	public void setListaSubRubrosFinanciables(List<SelectItem> listaSubRubrosFinanciables) {
		this.listaSubRubrosFinanciables = listaSubRubrosFinanciables;
	}

	public SelectItem[] getSubtiposRubroItem() {
		return subtiposRubroItem;
	}

	public void setSubtiposRubroItem(SelectItem[] subtiposRubroItem) {
		this.subtiposRubroItem = subtiposRubroItem;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public List getListaFuentesInternas() {
		return listaFuentesInternas;
	}

	public void setListaFuentesInternas(List listaFuentesInternas) {
		this.listaFuentesInternas = listaFuentesInternas;
	}

	public List getListaFuentesExternas() {
		return listaFuentesExternas;
	}

	public void setListaFuentesExternas(List listaFuentesExternas) {
		this.listaFuentesExternas = listaFuentesExternas;
	}

	public List getListaAuxiliarFuentes() {
		return listaAuxiliarFuentes;
	}

	public void setListaAuxiliarFuentes(List listaAuxiliarFuentes) {
		this.listaAuxiliarFuentes = listaAuxiliarFuentes;
	}

	public FuenteFinanciacion getFuenteFinancieraActual() {
		return fuenteFinancieraActual;
	}

	public void setFuenteFinancieraActual(FuenteFinanciacion fuenteFinancieraActual) {
		this.fuenteFinancieraActual = fuenteFinancieraActual;
	}

	public Financiacion getFinanciacionActual() {
		return financiacionActual;
	}

	public void setFinanciacionActual(Financiacion financiacionActual) {
		this.financiacionActual = financiacionActual;
	}

	public Gasto getGastoActual() {
		return gastoActual;
	}

	public void setGastoActual(Gasto gastoActual) {
		this.gastoActual = gastoActual;
	}

	public List getListaTiposRubros() {
		return listaTiposRubros;
	}

	public void setListaTiposRubros(List listaTiposRubros) {
		this.listaTiposRubros = listaTiposRubros;
	}

	public DataTable getTablaGastos() {
		return tablaGastos;
	}

	public void setTablaGastos(DataTable tablaGastos) {
		this.tablaGastos = tablaGastos;
	}

	public Long getIdTipoRubro() {
		return idTipoRubro;
	}

	public void setIdTipoRubro(Long idTipoRubro) {
		this.idTipoRubro = idTipoRubro;
	}

	public int getVigenciaGasto() {
		return vigenciaGasto;
	}

	public void setVigenciaGasto(int vigenciaGasto) {
		this.vigenciaGasto = vigenciaGasto;
	}

	public String getDescripcionGasto() {
		return descripcionGasto;
	}

	public void setDescripcionGasto(String descripcionGasto) {
		this.descripcionGasto = descripcionGasto;
	}

	public Gasto getGastoSeleccionado() {
		return gastoSeleccionado;
	}

	public void setGastoSeleccionado(Gasto gastoSeleccionado) {
		this.gastoSeleccionado = gastoSeleccionado;
	}

	public UIComponent getObjetoEspecifico() {
		return objetoEspecifico;
	}

	public void setObjetoEspecifico(UIComponent objetoEspecifico) {
		this.objetoEspecifico = objetoEspecifico;
	}

	public UIComponent getUiResultado() {
		return uiResultado;
	}

	public void setUiResultado(UIComponent uiResultado) {
		this.uiResultado = uiResultado;
	}

	public UIComponent getUiCantidad() {
		return uiCantidad;
	}

	public void setUiCantidad(UIComponent uiCantidad) {
		this.uiCantidad = uiCantidad;
	}

	public InvestigadorProyecto getParticipante() {
		return participante;
	}

	public void setParticipante(InvestigadorProyecto participante) {
		this.participante = participante;
	}

	public void setSelItems(String selItems) {
		this.selItems = selItems;
	}

	public String getSelItems() {
		return selItems;
	}

	public String getAreaCiencia() {
		return areaCiencia;
	}

	public void setAreaCiencia(String areaCiencia) {
		this.areaCiencia = areaCiencia;
	}

	public String getAreaCienciaSec() {
		return areaCienciaSec;
	}

	public void setAreaCienciaSec(String areaCienciaSec) {
		this.areaCienciaSec = areaCienciaSec;
	}

	public List getListaInvestigadoresVista() {
		return listaInvestigadoresVista;
	}

	public void setListaInvestigadoresVista(List listaInvestigadoresVista) {
		this.listaInvestigadoresVista = listaInvestigadoresVista;
	}

	public List getListaAreasPrimSec() {
		return listaAreasPrimSec;
	}

	public void setListaAreasPrimSec(List listaAreasPrimSec) {
		this.listaAreasPrimSec = listaAreasPrimSec;
	}

	public Integer getTiempoProyectos() {
		return tiempoProyectos;
	}

	public void setTiempoProyectos(Integer tiempoProyectos) {
		this.tiempoProyectos = tiempoProyectos;
	}

	public Proyecto getProyectoAsociar() {
		return proyectoAsociar;
	}

	public void setProyectoAsociar(Proyecto proyectoAsociar) {
		this.proyectoAsociar = proyectoAsociar;
	}

	public boolean isEsConvIni() {
		return esConvIni;
	}

	public void setEsConvIni(boolean esConvIni) {
		this.esConvIni = esConvIni;
	}

	public boolean isMateriasSIA() {
		return materiasSIA;
	}

	public void setMateriasSIA(boolean materiasSIA) {
		this.materiasSIA = materiasSIA;
	}

	public boolean isVerFichaMin() {
		return verFichaMin;
	}

	public void setVerFichaMin(boolean verFichaMin) {
		this.verFichaMin = verFichaMin;
	}

	public List getListaFacultades() {
		return listaFacultades;
	}

	public void setListaFacultades(List listaFacultades) {
		this.listaFacultades = listaFacultades;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public Investigador getInvestigadorDirector() {
		return investigadorDirector;
	}

	public void setInvestigadorDirector(Investigador investigadorDirector) {
		this.investigadorDirector = investigadorDirector;
	}

	public boolean isEsPosgrado1_2() {
		return esPosgrado1_2;
	}

	public void setEsPosgrado1_2(boolean esPosgrado1_2) {
		this.esPosgrado1_2 = esPosgrado1_2;
	}

	public InvestigadorExterno getInvestigadorExterno() {
		return investigadorExterno;
	}

	public void setInvestigadorExterno(InvestigadorExterno investigadorExterno) {
		this.investigadorExterno = investigadorExterno;
	}

	public boolean isEsOtraVinculacion() {
		return esOtraVinculacion;
	}

	public void setEsOtraVinculacion(boolean esOtraVinculacion) {
		this.esOtraVinculacion = esOtraVinculacion;
	}

	public String getUnidadEjecutoraPart() {
		return unidadEjecutoraPart;
	}

	public void setUnidadEjecutoraPart(String unidadEjecutoraPart) {
		this.unidadEjecutoraPart = unidadEjecutoraPart;
	}

	public SelectItem[] getGeneroItem() {
		return generoItem;
	}

	public void setGeneroItem(SelectItem[] generoItem) {
		this.generoItem = generoItem;
	}

	public String getInsitucionNombre() {
		return insitucionNombre;
	}

	public void setInsitucionNombre(String insitucionNombre) {
		this.insitucionNombre = insitucionNombre;
	}

	public List getListaInstitucion() {
		return listaInstitucion;
	}

	public void setListaInstitucion(List listaInstitucion) {
		this.listaInstitucion = listaInstitucion;
	}

	public SelectItem[] getInstitucionItem() {
		return institucionItem;
	}

	public void setInstitucionItem(SelectItem[] institucionItem) {
		this.institucionItem = institucionItem;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public String getLinkLineas() {
		return linkLineas;
	}

	public void setLinkLineas(String linkLineas) {
		this.linkLineas = linkLineas;
	}

	public String getLinkObjetivos() {
		return linkObjetivos;
	}

	public void setLinkObjetivos(String linkObjetivos) {
		this.linkObjetivos = linkObjetivos;
	}

	public boolean isMostrarSiConvocatoriaEventos() {
		return mostrarSiConvocatoriaEventos;
	}

	public void setMostrarSiConvocatoriaEventos(boolean mostrarSiConvocatoriaEventos) {
		this.mostrarSiConvocatoriaEventos = mostrarSiConvocatoriaEventos;
	}

	public String getNombreCompletoPersonaActual() {
		return nombreCompletoPersonaActual;
	}

	public void setNombreCompletoPersonaActual(String nombreCompletoPersonaActual) {
		this.nombreCompletoPersonaActual = nombreCompletoPersonaActual;
	}

	public String getNombreSede() {
		return nombreSede;
	}

	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
	}

	public String getNombreFacultad() {
		return nombreFacultad;
	}

	public void setNombreFacultad(String nombreFacultad) {
		this.nombreFacultad = nombreFacultad;
	}

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public String getEmailPersonaActual() {
		return emailPersonaActual;
	}

	public void setEmailPersonaActual(String emailPersonaActual) {
		this.emailPersonaActual = emailPersonaActual;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public InvestigadorInterno getIi() {
		return ii;
	}

	public void setIi(InvestigadorInterno ii) {
		this.ii = ii;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public SelectItem[] getTipoEventoItems() {
		return tipoEventoItems;
	}

	public void setTipoEventoItems(SelectItem[] tipoEventoItems) {
		this.tipoEventoItems = tipoEventoItems;
	}

	public List<DominioDetalle> getListaTipoEvento() {
		return listaTipoEvento;
	}

	public void setListaTipoEvento(List<DominioDetalle> listaTipoEvento) {
		this.listaTipoEvento = listaTipoEvento;
	}

	public boolean isMostrarOtroTipoEvento() {
		return mostrarOtroTipoEvento;
	}

	public void setMostrarOtroTipoEvento(boolean mostrarOtroTipoEvento) {
		this.mostrarOtroTipoEvento = mostrarOtroTipoEvento;
	}

	public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidad() {
		return dependenciaAreaResponsabilidad;
	}

	public void setDependenciaAreaResponsabilidad(DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad) {
		this.dependenciaAreaResponsabilidad = dependenciaAreaResponsabilidad;
	}

	public String getDependenciaAdicionada() {
		return dependenciaAdicionada;
	}

	public void setDependenciaAdicionada(String dependenciaAdicionada) {
		this.dependenciaAdicionada = dependenciaAdicionada;
	}

	public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidadSeleccionada() {
		return dependenciaAreaResponsabilidadSeleccionada;
	}

	public void setDependenciaAreaResponsabilidadSeleccionada(
			DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada) {
		this.dependenciaAreaResponsabilidadSeleccionada = dependenciaAreaResponsabilidadSeleccionada;
	}

	public boolean isEsProyectoInnoModDos() {
		return esProyectoInnoModDos;
	}

	public void setEsProyectoInnoModDos(boolean esProyectoInnoModDos) {
		this.esProyectoInnoModDos = esProyectoInnoModDos;
	}

	public List getProgramas() {
		return programas;
	}

	public void setProgramas(List programas) {
		this.programas = programas;
	}

	public void setCortes(List cortes) {
		this.cortes = cortes;
	}

	public List getCortes() {
		return cortes;
	}

	public boolean isEsConvSemill3() {
		return esConvSemill3;
	}

	public void setEsConvSemill3(boolean esConvSemill3) {
		this.esConvSemill3 = esConvSemill3;
	}

	public String getErrorEstudiantes() {
		return errorEstudiantes;
	}

	public void setErrorEstudiantes(String errorEstudiantes) {
		this.errorEstudiantes = errorEstudiantes;
	}

	public String getMaxAsistentes() {
		return maxAsistentes;
	}

	public void setMaxAsistentes(String maxAsistentes) {
		this.maxAsistentes = maxAsistentes;
	}

	public boolean isEsConvPurdue() {
		return esConvPurdue;
	}

	public void setEsConvPurdue(boolean esConvPurdue) {
		this.esConvPurdue = esConvPurdue;
	}

	public boolean isEsConvCienciasAgraEquipos2015() {
		return esConvCienciasAgraEquipos2015;
	}

	public void setEsConvCienciasAgraEquipos2015(boolean esConvCienciasAgraEquipos2015) {
		this.esConvCienciasAgraEquipos2015 = esConvCienciasAgraEquipos2015;
	}

	public UIComponent getBuscarPer2() {
		return buscarPer2;
	}

	public void setBuscarPer2(UIComponent buscarPer2) {
		this.buscarPer2 = buscarPer2;
	}

	/**
	 * @return the uiCantidadEventos
	 */
	public UIComponent getUiCantidadEventos() {
		return uiCantidadEventos;
	}

	/**
	 * @param uiCantidadEventos the uiCantidadEventos to set
	 */
	public void setUiCantidadEventos(UIComponent uiCantidadEventos) {
		this.uiCantidadEventos = uiCantidadEventos;
	}

	/**
	 * @return the botonAgregarGasto
	 */
	public UIComponent getBotonAgregarGasto() {
		return botonAgregarGasto;
	}

	/**
	 * @param botonAgregarGasto the botonAgregarGasto to set
	 */
	public void setBotonAgregarGasto(UIComponent botonAgregarGasto) {
		this.botonAgregarGasto = botonAgregarGasto;
	}

	public Departamento getDepartamentoActual() {
		return departamentoActual;
	}

	public void setDepartamentoActual(Departamento departamentoActual) {
		this.departamentoActual = departamentoActual;
	}

	public List getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public Ciudad getCiudadActual() {
		return ciudadActual;
	}

	public void setCiudadActual(Ciudad ciudadActual) {
		this.ciudadActual = ciudadActual;
	}

	public List getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List listaCiudades) {
		this.listaCiudades = listaCiudades;
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

	public SelectItem[] getObjetivosDesarrolloSostenibleItems() {
		return objetivosDesarrolloSostenibleItems;
	}

	public void setObjetivosDesarrolloSostenibleItems(SelectItem[] objetivosDesarrolloSostenibleItems) {
		this.objetivosDesarrolloSostenibleItems = objetivosDesarrolloSostenibleItems;
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

	public String getLinkObjetivosDesarrolloSostenible() {
		return "http://www.undp.org/content/undp/es/home/sustainable-development-goals.html";
	}

	public UIComponent getBtnObjDesSosSec() {
		return btnObjDesSosSec;
	}

	public void setBtnObjDesSosSec(UIComponent btnObjDesSosSec) {
		this.btnObjDesSosSec = btnObjDesSosSec;
	}

	public SelectItem[] getPoblacionObjetivoEventoItem() {
		return poblacionObjetivoEventoItem;
	}

	public void setPoblacionObjetivoEventoItem(SelectItem[] poblacionObjetivoEventoItem) {
		this.poblacionObjetivoEventoItem = poblacionObjetivoEventoItem;
	}

	public SelectItem[] getMetodologiaEventoItem() {
		return metodologiaEventoItem;
	}

	public void setMetodologiaEventoItem(SelectItem[] metodologiaEventoItem) {
		this.metodologiaEventoItem = metodologiaEventoItem;
	}

	public SelectItem[] getFuentesExternasItem() {
		return fuentesExternasItem;
	}

	public void setFuentesExternasItem(SelectItem[] fuentesExternasItem) {
		this.fuentesExternasItem = fuentesExternasItem;
	}

	public String getEntidadCoejecutora() {
		return entidadCoejecutora;
	}

	public void setEntidadCoejecutora(String entidadCoejecutora) {
		this.entidadCoejecutora = entidadCoejecutora;
	}

	public Financiacion getEntidadEliminar() {
		return entidadEliminar;
	}

	public void setEntidadEliminar(Financiacion entidadEliminar) {
		this.entidadEliminar = entidadEliminar;
	}

	public UIComponent getObjetoEspecificoEven() {
		return objetoEspecificoEven;
	}

	public void setObjetoEspecificoEven(UIComponent objetoEspecificoEven) {
		this.objetoEspecificoEven = objetoEspecificoEven;
	}

	public String getIdPaisEvento() {
		return idPaisEvento;
	}

	public void setIdPaisEvento(String idPaisEvento) {
		this.idPaisEvento = idPaisEvento;
	}

	public boolean isSiColombia() {
		return siColombia;
	}

	public void setSiColombia(boolean siColombia) {
		this.siColombia = siColombia;
	}

	public SelectItem[] getListaPaisesItem() {
		return listaPaisesItem;
	}

	public void setListaPaisesItem(SelectItem[] listaPaisesItem) {
		this.listaPaisesItem = listaPaisesItem;
	}

	public boolean isMostrarPaises() {
		return mostrarPaises;
	}

	public void setMostrarPaises(boolean mostrarPaises) {
		this.mostrarPaises = mostrarPaises;
	}

	public String[] getSelectedPoblacionObjetivo() {
		return selectedPoblacionObjetivo;
	}

	public void setSelectedPoblacionObjetivo(String[] selectedPoblacionObjetivo) {
		this.selectedPoblacionObjetivo = selectedPoblacionObjetivo;
	}

	public SelectItem[] getPoblacionObjetivoEventoGrupoItem() {
		return poblacionObjetivoEventoGrupoItem;
	}

	public void setPoblacionObjetivoEventoGrupoItem(SelectItem[] poblacionObjetivoEventoGrupoItem) {
		this.poblacionObjetivoEventoGrupoItem = poblacionObjetivoEventoGrupoItem;
	}

	public SelectItem[] getPoblacionObjetivoEventoPoblacionItem() {
		return poblacionObjetivoEventoPoblacionItem;
	}

	public void setPoblacionObjetivoEventoPoblacionItem(SelectItem[] poblacionObjetivoEventoPoblacionItem) {
		this.poblacionObjetivoEventoPoblacionItem = poblacionObjetivoEventoPoblacionItem;
	}

	public String getDOMINIO_CATEGORIA() {
		return DOMINIO_CATEGORIA;
	}

	public void setDOMINIO_CATEGORIA(String dOMINIO_CATEGORIA) {
		DOMINIO_CATEGORIA = dOMINIO_CATEGORIA;
	}

	public String getDOMINIO_ROL() {
		return DOMINIO_ROL;
	}

	public void setDOMINIO_ROL(String dOMINIO_ROL) {
		DOMINIO_ROL = dOMINIO_ROL;
	}

	public String getDOMINIO_MECANISMO() {
		return DOMINIO_MECANISMO;
	}

	public void setDOMINIO_MECANISMO(String dOMINIO_MECANISMO) {
		DOMINIO_MECANISMO = dOMINIO_MECANISMO;
	}

	public String getDOMINIO_TIPO_EVENTO() {
		return DOMINIO_TIPO_EVENTO;
	}

	public void setDOMINIO_TIPO_EVENTO(String dOMINIO_TIPO_EVENTO) {
		DOMINIO_TIPO_EVENTO = dOMINIO_TIPO_EVENTO;
	}

	public String getDOMINIO_POBLACION_OBJETIVO_EVENTO() {
		return DOMINIO_POBLACION_OBJETIVO_EVENTO;
	}

	public void setDOMINIO_POBLACION_OBJETIVO_EVENTO(String dOMINIO_POBLACION_OBJETIVO_EVENTO) {
		DOMINIO_POBLACION_OBJETIVO_EVENTO = dOMINIO_POBLACION_OBJETIVO_EVENTO;
	}

	public String getDOMINIO_METODOLOGIA_EVENTO() {
		return DOMINIO_METODOLOGIA_EVENTO;
	}

	public void setDOMINIO_METODOLOGIA_EVENTO(String dOMINIO_METODOLOGIA_EVENTO) {
		DOMINIO_METODOLOGIA_EVENTO = dOMINIO_METODOLOGIA_EVENTO;
	}

	public String getDOMINIO_OBJETIVO_SOCIO_EC() {
		return DOMINIO_OBJETIVO_SOCIO_EC;
	}

	public void setDOMINIO_OBJETIVO_SOCIO_EC(String dOMINIO_OBJETIVO_SOCIO_EC) {
		DOMINIO_OBJETIVO_SOCIO_EC = dOMINIO_OBJETIVO_SOCIO_EC;
	}

	public String getDOMINIO_AREA_CIENCIA() {
		return DOMINIO_AREA_CIENCIA;
	}

	public void setDOMINIO_AREA_CIENCIA(String dOMINIO_AREA_CIENCIA) {
		DOMINIO_AREA_CIENCIA = dOMINIO_AREA_CIENCIA;
	}

	public String getDOMINIO_TIPO_VINCULACION() {
		return DOMINIO_TIPO_VINCULACION;
	}

	public void setDOMINIO_TIPO_VINCULACION(String dOMINIO_TIPO_VINCULACION) {
		DOMINIO_TIPO_VINCULACION = dOMINIO_TIPO_VINCULACION;
	}

	public List<InvestigadorProyecto> getListaParticipantesBorrados() {
		return listaParticipantesBorrados;
	}

	public void setListaParticipantesBorrados(List<InvestigadorProyecto> listaParticipantesBorrados) {
		this.listaParticipantesBorrados = listaParticipantesBorrados;
	}

	public boolean isProyectoExiste() {
		return proyectoExiste;
	}

	public void setProyectoExiste(boolean proyectoExiste) {
		this.proyectoExiste = proyectoExiste;
	}

	public boolean isEsConvSemill() {
		return esConvSemill;
	}

	public void setEsConvSemill(boolean esConvSemill) {
		this.esConvSemill = esConvSemill;
	}

	public Boolean getEsConsulta() {
		return esConsulta;
	}

	public void setEsConsulta(Boolean esConsulta) {
		this.esConsulta = esConsulta;
	}

	public String getConoceDatos() {
		return conoceDatos;
	}

	public void setConoceDatos(String conoceDatos) {
		this.conoceDatos = conoceDatos;
	}

	public String getTipoVinculacionId() {
		return tipoVinculacionId;
	}

	public void setTipoVinculacionId(String tipoVinculacionId) {
		this.tipoVinculacionId = tipoVinculacionId;
	}

	public SelectItem[] getTipoVinculacionItemsNumero() {
		return tipoVinculacionItemsNumero;
	}

	public void setTipoVinculacionItemsNumero(SelectItem[] tipoVinculacionItemsNumero) {
		this.tipoVinculacionItemsNumero = tipoVinculacionItemsNumero;
	}

	public Long getCantidadPart() {
		return cantidadPart;
	}

	public void setCantidadPart(Long cantidadPart) {
		this.cantidadPart = cantidadPart;
	}

	public String getFuncionInvestigador() {
		return funcionInvestigador;
	}

	public void setFuncionInvestigador(String funcionInvestigador) {
		this.funcionInvestigador = funcionInvestigador;
	}

	public UIComponent getBuscarPer3() {
		return buscarPer3;
	}

	public void setBuscarPer3(UIComponent buscarPer3) {
		this.buscarPer3 = buscarPer3;
	}

	public TipoDocumento getTipoDocumentoCoInvEquipo() {
		return tipoDocumentoCoInvEquipo;
	}

	public void setTipoDocumentoCoInvEquipo(TipoDocumento tipoDocumentoCoInvEquipo) {
		this.tipoDocumentoCoInvEquipo = tipoDocumentoCoInvEquipo;
	}

	public String getDocumentoCoinvEquipo() {
		return documentoCoinvEquipo;
	}

	public void setDocumentoCoinvEquipo(String documentoCoinvEquipo) {
		this.documentoCoinvEquipo = documentoCoinvEquipo;
	}

	public boolean isMostrarOpcionBuscarInvestigador() {
		return mostrarOpcionBuscarInvestigador;
	}

	public void setMostrarOpcionBuscarInvestigador(boolean mostrarOpcionBuscarInvestigador) {
		this.mostrarOpcionBuscarInvestigador = mostrarOpcionBuscarInvestigador;
	}

	public UIComponent getBotonBuscarInvestigadorExterno() {
		return botonBuscarInvestigadorExterno;
	}

	public void setBotonBuscarInvestigadorExterno(UIComponent botonBuscarInvestigadorExterno) {
		this.botonBuscarInvestigadorExterno = botonBuscarInvestigadorExterno;
	}

	public String getIdTipoFormacion() {
		return idTipoFormacion;
	}

	public void setIdTipoFormacion(String idTipoFormacion) {
		this.idTipoFormacion = idTipoFormacion;
	}

	public SelectItem[] getTipoFormacionItem() {
		return tipoFormacionItem;
	}

	public void setTipoFormacionItem(SelectItem[] tipoFormacionItem) {
		this.tipoFormacionItem = tipoFormacionItem;
	}

	public String getIdTipoEstadoCivil() {
		return idTipoEstadoCivil;
	}

	public void setIdTipoEstadoCivil(String idTipoEstadoCivil) {
		this.idTipoEstadoCivil = idTipoEstadoCivil;
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

	public boolean isMostrarDatosJoven() {
		return mostrarDatosJoven;
	}

	public void setMostrarDatosJoven(boolean mostrarDatosJoven) {
		this.mostrarDatosJoven = mostrarDatosJoven;
	}

	public String getInstitucionSeleccionada() {
		return institucionSeleccionada;
	}

	public void setInstitucionSeleccionada(String institucionSeleccionada) {
		this.institucionSeleccionada = institucionSeleccionada;
	}

	public SelectItem[] getInstitucionesItem() {
		return institucionesItem;
	}

	public void setInstitucionesItem(SelectItem[] institucionesItem) {
		this.institucionesItem = institucionesItem;
	}

	public String getAreaCienciaInv() {
		return areaCienciaInv;
	}

	public void setAreaCienciaInv(String areaCienciaInv) {
		this.areaCienciaInv = areaCienciaInv;
	}

	public String getSubAreaCienciaInv() {
		return subAreaCienciaInv;
	}

	public void setSubAreaCienciaInv(String subAreaCienciaInv) {
		this.subAreaCienciaInv = subAreaCienciaInv;
	}

	public SelectItem[] getSubAreaCienciaItemsInv() {
		return subAreaCienciaItemsInv;
	}

	public void setSubAreaCienciaItemsInv(SelectItem[] subAreaCienciaItemsInv) {
		this.subAreaCienciaItemsInv = subAreaCienciaItemsInv;
	}

	public String getLugarNacimiento() {
		return lugarNacimiento;
	}

	public void setLugarNacimiento(String lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}

	public List<SelectItem> getCiudadItemList() {
		return ciudadItemList;
	}

	public void setCiudadItemList(List<SelectItem> ciudadItemList) {
		this.ciudadItemList = ciudadItemList;
	}

	public boolean isMostrarProgramaAcademico() {
		return mostrarProgramaAcademico;
	}

	public void setMostrarProgramaAcademico(boolean mostrarProgramaAcademico) {
		this.mostrarProgramaAcademico = mostrarProgramaAcademico;
	}

	public boolean isMostrarDependencia() {
		return mostrarDependencia;
	}

	public void setMostrarDependencia(boolean mostrarDependencia) {
		this.mostrarDependencia = mostrarDependencia;
	}

	public UIComponent getHorasCoinv2() {
		return horasCoinv2;
	}

	public void setHorasCoinv2(UIComponent horasCoinv2) {
		this.horasCoinv2 = horasCoinv2;
	}

	public UIComponent getHorasCoinv3() {
		return horasCoinv3;
	}

	public void setHorasCoinv3(UIComponent horasCoinv3) {
		this.horasCoinv3 = horasCoinv3;
	}

	public boolean isAsignarGrupo() {
		return asignarGrupo;
	}

	public void setAsignarGrupo(boolean asignarGrupo) {
		this.asignarGrupo = asignarGrupo;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public List<SelectItem> getGruposInvestigacionItem() {
		return gruposInvestigacionItem;
	}

	public void setGruposInvestigacionItem(List<SelectItem> gruposInvestigacionItem) {
		this.gruposInvestigacionItem = gruposInvestigacionItem;
	}

	public static String getDominioObjetivosDesarrolloSostenible() {
		return DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE;
	}

	public Persona getInvestigadorExterno2() {
		return investigadorExterno2;
	}

	public void setInvestigadorExterno2(Persona investigadorExterno2) {
		this.investigadorExterno2 = investigadorExterno2;
	}

	public boolean isMostrarMenuFormulario() {
		return mostrarMenuFormulario;
	}

	public void setMostrarMenuFormulario(boolean mostrarMenuFormulario) {
		this.mostrarMenuFormulario = mostrarMenuFormulario;
	}

	public List<HistoricoCambioIntegrantes> getHistoricoIntegrantes() {
		return historicoIntegrantes;
	}

	public void setHistoricoIntegrantes(List<HistoricoCambioIntegrantes> historicoIntegrantes) {
		this.historicoIntegrantes = historicoIntegrantes;
	}

	public List<TipoInvestigador> getListaTipoVinculacionNumero() {
		return listaTipoVinculacionNumero;
	}

	public void setListaTipoVinculacionNumero(List<TipoInvestigador> listaTipoVinculacionNumero) {
		this.listaTipoVinculacionNumero = listaTipoVinculacionNumero;
	}

	public static String getDominioSubAreaCiencia() {
		return DOMINIO_SUB_AREA_CIENCIA;
	}

	public UIComponent getBuscarPer4() {
		return buscarPer4;
	}

	public void setBuscarPer4(UIComponent buscarPer4) {
		this.buscarPer4 = buscarPer4;
	}

	public boolean isEsConvEventos2019() {
		return esConvEventos2019;
	}

	public void setEsConvEventos2019(boolean esConvEventos2019) {
		this.esConvEventos2019 = esConvEventos2019;
	}

	public SelectItem[] getListaPaisesItem2() {
		return listaPaisesItem2;
	}

	public void setListaPaisesItem2(SelectItem[] listaPaisesItem2) {
		this.listaPaisesItem2 = listaPaisesItem2;
	}

	public Departamento getDepartamentoActual2() {
		return departamentoActual2;
	}

	public void setDepartamentoActual2(Departamento departamentoActual2) {
		this.departamentoActual2 = departamentoActual2;
	}

	public Ciudad getCiudadActual2() {
		return ciudadActual2;
	}

	public void setCiudadActual2(Ciudad ciudadActual2) {
		this.ciudadActual2 = ciudadActual2;
	}

	public List<SelectItem> getCiudadItemList2() {
		return ciudadItemList2;
	}

	public void setCiudadItemList2(List<SelectItem> ciudadItemList2) {
		this.ciudadItemList2 = ciudadItemList2;
	}

	public SelectItem[] getCiudadItem2() {
		return ciudadItem2;
	}

	public void setCiudadItem2(SelectItem[] ciudadItem2) {
		this.ciudadItem2 = ciudadItem2;
	}

	public List getListaCiudades2() {
		return listaCiudades2;
	}

	public void setListaCiudades2(List listaCiudades2) {
		this.listaCiudades2 = listaCiudades2;
	}

	public boolean isSiColombia2() {
		return siColombia2;
	}

	public void setSiColombia2(boolean siColombia2) {
		this.siColombia2 = siColombia2;
	}

	public boolean isEsOtraVinculacion2() {
		return esOtraVinculacion2;
	}

	public void setEsOtraVinculacion2(boolean esOtraVinculacion2) {
		this.esOtraVinculacion2 = esOtraVinculacion2;
	}

	public List<TipoInvestigador> getListaTipoVinculacion2() {
		return listaTipoVinculacion2;
	}

	public void setListaTipoVinculacion2(List<TipoInvestigador> listaTipoVinculacion2) {
		this.listaTipoVinculacion2 = listaTipoVinculacion2;
	}

	public List<TipoInvestigador> getListaTipoVinculacion2Numero() {
		return listaTipoVinculacion2Numero;
	}

	public void setListaTipoVinculacion2Numero(List<TipoInvestigador> listaTipoVinculacion2Numero) {
		this.listaTipoVinculacion2Numero = listaTipoVinculacion2Numero;
	}

	public boolean isEsConvEventos2019M1() {
		return esConvEventos2019M1;
	}

	public boolean isEsConvMoots2019() {
		return esConvMoots2019;
	}

	public void setEsConvMoots2019(boolean esConvMoots2019) {
		this.esConvMoots2019 = esConvMoots2019;
	}

	public boolean isEsConvMoots2019_M1() {
		return esConvMoots2019_M1;
	}

	public void setEsConvMoots2019_M1(boolean esConvMoots2019_M1) {
		this.esConvMoots2019_M1 = esConvMoots2019_M1;
	}

	public boolean isEsConvMoots2019_M2() {
		return esConvMoots2019_M2;
	}

	public void setEsConvMoots2019_M2(boolean esConvMoots2019_M2) {
		this.esConvMoots2019_M2 = esConvMoots2019_M2;
	}

	public void setEsConvEventos2019M1(boolean esConvEventos2019M1) {
		this.esConvEventos2019M1 = esConvEventos2019M1;
	}

	public boolean isEsConvEventos2019M2() {
		return esConvEventos2019M2;
	}

	public void setEsConvEventos2019M2(boolean esConvEventos2019M2) {
		this.esConvEventos2019M2 = esConvEventos2019M2;
	}

	public boolean isEsConvMoots2021_M1() {
		return esConvMoots2021_M1;
	}

	public void setEsConvMoots2021_M1(boolean esConvMoots2021_M1) {
		this.esConvMoots2021_M1 = esConvMoots2021_M1;
	}

	public boolean isEsConvMoots2021_M2() {
		return esConvMoots2021_M2;
	}

	public void setEsConvMoots2021_M2(boolean esConvMoots2021_M2) {
		this.esConvMoots2021_M2 = esConvMoots2021_M2;
	}

	public boolean isEsConvocatoriaEventos() {
		return esConvocatoriaEventos;
	}

	public void setEsConvocatoriaEventos(boolean esConvocatoriaEventos) {
		this.esConvocatoriaEventos = esConvocatoriaEventos;
	}

	public boolean isEsConvocatoriaEventosNacional() {
		return esConvocatoriaEventosNacional;
	}

	public void setEsConvocatoriaEventosNacional(boolean esConvocatoriaEventosNacional) {
		this.esConvocatoriaEventosNacional = esConvocatoriaEventosNacional;
	}

	public boolean isEsConvocatoriaEventosInternacional() {
		return esConvocatoriaEventosInternacional;
	}

	public void setEsConvocatoriaEventosInternacional(boolean esConvocatoriaEventosInternacional) {
		this.esConvocatoriaEventosInternacional = esConvocatoriaEventosInternacional;
	}

	public Long getValorAporteEfectivoOrganizador() {
		return valorAporteEfectivoOrganizador;
	}

	public void setValorAporteEfectivoOrganizador(Long valorAporteEfectivoOrganizador) {
		this.valorAporteEfectivoOrganizador = valorAporteEfectivoOrganizador;
	}

	public Long getValorAporteEspecieOrganizador() {
		return valorAporteEspecieOrganizador;
	}

	public void setValorAporteEspecieOrganizador(Long valorAporteEspecieOrganizador) {
		this.valorAporteEspecieOrganizador = valorAporteEspecieOrganizador;
	}

	public SelectItem[] getNaturalezaRubroItem() {
		return naturalezaRubroItem;
	}

	public void setNaturalezaRubroItem(SelectItem[] naturalezaRubroItem) {
		this.naturalezaRubroItem = naturalezaRubroItem;
	}

	public String getIdNaturalezaRubro() {
		return idNaturalezaRubro;
	}

	public void setIdNaturalezaRubro(String idNaturalezaRubro) {
		this.idNaturalezaRubro = idNaturalezaRubro;
	}

	public List getListaRubrosContrapartida() {
		return listaRubrosContrapartida;
	}

	public void setListaRubrosContrapartida(List listaRubrosContrapartida) {
		this.listaRubrosContrapartida = listaRubrosContrapartida;
	}

	public boolean isFuentesExternas() {
		return fuentesExternas;
	}

	public void setFuentesExternas(boolean fuentesExternas) {
		this.fuentesExternas = fuentesExternas;
	}

	public void cargarDatosFinanciacionExterna() {
		if (fuentesExternas) {

		}
	}

	public SelectItem[] getFuentesExternasFinanciacionItem() {
		return fuentesExternasFinanciacionItem;
	}

	public void setFuentesExternasFinanciacionItem(SelectItem[] fuentesExternasFinanciacionItem) {
		this.fuentesExternasFinanciacionItem = fuentesExternasFinanciacionItem;
	}

	public String getFinanciadoraExterna() {
		return financiadoraExterna;
	}

	public void setFinanciadoraExterna(String financiadoraExterna) {
		this.financiadoraExterna = financiadoraExterna;
	}

	public Long getValorFinanciadoraExterna() {
		return valorFinanciadoraExterna;
	}

	public void setValorFinanciadoraExterna(Long valorFinanciadoraExterna) {
		this.valorFinanciadoraExterna = valorFinanciadoraExterna;
	}

	public Long getIdRubroFinanciadoraExterna() {
		return idRubroFinanciadoraExterna;
	}

	public void setIdRubroFinanciadoraExterna(Long idRubroFinanciadoraExterna) {
		this.idRubroFinanciadoraExterna = idRubroFinanciadoraExterna;
	}

	public String getDescripcionRubroExterna() {
		return descripcionRubroExterna;
	}

	public void setDescripcionRubroExterna(String descripcionRubroExterna) {
		this.descripcionRubroExterna = descripcionRubroExterna;
	}

	public UIComponent getBotonAgregarFinanciacionExterna() {
		return botonAgregarFinanciacionExterna;
	}

	public void setBotonAgregarFinanciacionExterna(UIComponent botonAgregarFinanciacionExterna) {
		this.botonAgregarFinanciacionExterna = botonAgregarFinanciacionExterna;
	}

	public int getTipoFuenteActual() {
		return tipoFuenteActual;
	}

	public void setTipoFuenteActual(int tipoFuenteActual) {
		this.tipoFuenteActual = tipoFuenteActual;
	}

	public SelectItem[] getTiposFuenteItem() {
		return tiposFuenteItem;
	}

	public void setTiposFuenteItem(SelectItem[] tiposFuenteItem) {
		this.tiposFuenteItem = tiposFuenteItem;
	}

	public SelectItem[] getFuentesInternasItem() {
		return fuentesInternasItem;
	}

	public void setFuentesInternasItem(SelectItem[] fuentesInternasItem) {
		this.fuentesInternasItem = fuentesInternasItem;
	}

	public String getFuenteActualIntId() {
		return fuenteActualIntId;
	}

	public void setFuenteActualIntId(String fuenteActualIntId) {
		this.fuenteActualIntId = fuenteActualIntId;
	}

	public boolean isRequiereOtraFuente() {
		return requiereOtraFuente;
	}

	public void setRequiereOtraFuente(boolean requiereOtraFuente) {
		this.requiereOtraFuente = requiereOtraFuente;
	}
	
	public SelectItem[] getTiposNaturalezaFuenteItem() {
		return crearListaItemDominioDetalle(Dominio.NAT_ENTIDAD);
	}
	
	public SelectItem[] getCaracterFuenteFinanciacionItem() {
		return crearListaItemDominioDetalle(Dominio.CARACTER_FUENTE_FINANCIACION);
	}

	public SelectItem[] getTiposFuenteFinanciacionItem() {
		return crearListaItemDominioDetalle(Dominio.TIPO_FUENTE_FINANCIACION);
	}

}
