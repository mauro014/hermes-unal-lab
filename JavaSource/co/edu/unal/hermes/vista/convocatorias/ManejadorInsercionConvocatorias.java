package co.edu.unal.hermes.vista.convocatorias;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UISelectMany;
import javax.faces.component.UISelectOne;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.tabview.TabView;

import co.edu.unal.hermes.modelo.CategoriaGrupo;
import co.edu.unal.hermes.modelo.Ciudades;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CriterioEvaluacion;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DistribucionRecursos;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.FormularioInformacionEspecifica;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.ModalidadCriterioTipoPregunta;
import co.edu.unal.hermes.modelo.ModalidadFuenteFinanciacion;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.RubroFinanciable;
import co.edu.unal.hermes.modelo.RubroFinanciableArbol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.TipoArchivoMovilidad;
import co.edu.unal.hermes.modelo.TipoCompromiso;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.modelo.TipoPregunta;
import co.edu.unal.hermes.modelo.TipoRequisito;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.utils.StringUtils;
import co.edu.unal.hermes.vista.utils.ValidacionUtils;

public class ManejadorInsercionConvocatorias extends ManejadorBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4955515347317421478L;

	private Convocatoria convocatoriaActual;
	private UISelectOne manejadorSedes;
	private UISelectOne manejadorEsGrupos;
	private UISelectOne manejadorEsFacultades;
	private UISelectOne manejadorRubroPadre;
	private UISelectOne manejadorRubroHijo;
	private UISelectOne manejadorProductosNivel1;
	private UISelectOne manejadorProductosNivel2;
	private UISelectOne manejadorProductosNivel3;
	private UISelectOne manejadorCompromisoNivel1;
	private UISelectOne manejadorCompromisoNivel2;
	private UISelectOne manejadorRequisitoNivel2;

	private Sede sedeActual;
	// private Dependencia dependenciaActual;
	private EstadoConvocatoria estadoConvocatoria;
	private ConvocatoriaPadre convocatoriaPadre;
	private List listaSede;
	private List listaDependencia;
	private List listaDependenciaRestriccion;
	private List listaEstadosConvocatoria;
	private List listaConvocatoriasPadre;
	private DataTable tablaNivel1;
	private DataTable tablaNivel2;
	private DataTable tablaCompromisoNivel1;
	private ModalidadFuenteFinanciacion fuenteFinanciacionSeleccion;

	// rubros financiables
	private String avisoRubros;
	private RubroFinanciable rubroFinanciableActual;
	private RubroFinanciable rubroFinanciableActualHijo;
	private RubroFinanciable rubroFinanciableActualNivel3;
	private RubroFinanciable rubroSeleccionado;
	private List listaRubros;
	private List listaRubroPadres;
	private List listaRubroHijos;
	private List listaRubroNivel3;
	private DataTable tablaRubrosFinanciables;
	private boolean deshabilitarRubroHijo;

	// productos
	private List listaProductosItem;
	private UISelectMany selectProducto;
	private String avisoProductos;
	private String[] listaProductoSeleccionados;
	private Long[] listaRequisitos;
	private HashMap listaTemporalProductoSeleccionados;
	private HashMap listaTemporalCompromisosSeleccionados;
	private List listaproductosTipo;
	private List productoTipoNivel1;
	private List productoTipoNivel2;
	private List productoTipoNivel3;
	private boolean[] readOnlySelect;
	private String[] seleccionSelect;
	private ProductoTipo productoActual;
	private DataTable tablaProducto;

	// compromisos
	private List listaCompromisoItem;
	private UISelectMany selectCompromiso;
	private String avisoCompromisos;
	private TipoCompromiso compromisoActual;
	private DataTable tablaCompromiso;

	// requisitos
	private List listaRequisitoItem;
	private UISelectMany selectRequisito;
	private String avisoRequisitos;
	private String avisoCriterios;
	private String avisoInfoEspecifca;
	private TipoRequisito requisitoActual;
	private DataTable tablaRequisito;

	private String avisoArchivosMovilidad;
	private String nombreTipoArchivoMovilidad;
	private List<TipoArchivoMovilidad> listaTipoArchivoMovilidad;
	private List<MovilidadArchivo> listaMovilidadArchivo;
	private Long esArchivoObligatorioMovilidad;
	private Long idTipoMovilidadArchivo;

	// Convocatorias para grupos
	private String esParaGrupos;
	private boolean visiblePanelGrupos;
	private String[] listaTipoGrupos;

	// Convocatorias para sedes
	private String esParaSedes;
	private boolean visiblePanelSedes;
	private Dependencia dependenciaActualRestriccion;
	private SelectItem[] dependenciaItemRestriccion;

	private String avisoFormulario;
	private String avisoFechas;

	private List listaTiposInvestigacion;
	private List listaCriterios;
	private List listaCriteriosSeleccionados;

	public List listaRubrosFinanciablesHijos;
	public String idTipoRubroHijo;
	private List listaParametros;
	private Long idParametro;
	private List<ModalidadFuenteFinanciacion> listaModalidadFuenteFinanciacion;
	private List listaFuentesFinanciacion;
	private String idFuenteFinanciacion;
	private String idTipoFuenteFinanciacion;
	private String porcentaje;
	private DataTable tablaModalidadFuente;
	private String avisoFuentes;
	private List listaCriteriosTipoPregunta;
	private List listaTipoPregunta;
	HtmlPanelGrid panelGridCriteriosModalidadTipoPregunta;
	DataTable tablaModalidadCriterioTipoPregunta;
	private String convocatoria;
	private SelectItem[] convocatoriaItem;
	private SelectItem[] sedeItem;
	private List listaConvocatorias;
	private String nombreModalidad;
	private List listaTipoFuenteFinanciacion;
	private String idSede;

	private long nivelNal;
	private long bogota;
	private long medellin;
	private long manizales;
	private long caribe;
	private long palmira;
	private long amazonia;
	private long orinoquia;
	private long nivelNalApoyo;
	private long bogotaApoyo;
	private long medellinApoyo;
	private long manizalesApoyo;
	private long caribeApoyo;
	private long palmiraApoyo;
	private long amazoniaApoyo;
	private long orinoquiaApoyo;
	private String mensajeDistribucion;
	private boolean recursos;
	private boolean distribucion;
	private List valorCiudades;
	private String mensajeApoyos;
	private DataTable tablaProyecto;
	private int maxIdConvocatoria;

	// Activar Tabs
	boolean activarGeneralidades = false;
	boolean activarRequisitos = false;
	boolean activarRubros = false;
	boolean activarCompromisos = false;
	boolean activarProductos = false;
	boolean activarCriterios = false;
	boolean activarRecursos = false;
	boolean activarGuardar = false;
	boolean activarSiguiente = true;
	boolean activarInfoEspecifica = false;
	boolean activarArchivosMovilidad = false;
	private String esEdicion = "N";
	int estadoIngreso = 1;
	private TabView panelTab;
	TabView tabview = null;
	private TipoRequisito requisitoSeleccionado;
	private TipoCompromiso compromisoSeleccionado;

	// Manejo requisitos
	TipoRequisito requisito = new TipoRequisito();
	TipoCompromiso compromiso = new TipoCompromiso();
	List RequisitosEliminar = new ArrayList();
	List CompromisosEliminar = new ArrayList();

	private DataTable tablaRequisitosAdicionados;
	private DataTable tablaCompromisosAdicionados;

	private boolean mostrarGuardarAvanzar = true;
	private boolean mostrarGuardarSalir = true;

	private ProductoTipo productoNivel1Seleccionado;
	private ProductoTipo productoNivel2Seleccionado;

	private String opcionesTipoProducto;
	private boolean mostrarPgNuevoTipoProducto;
	private boolean mostrarPgNuevoProducto;
	private String selNivelTipoProducto;
	private String nombreProductoNivel1;
	private String nombreProductoNivel2;
	private boolean mostrarNivelProd1;
	private boolean mostrarNivelProd2;
	private String descripcionProductoNivel1;
	private String descripcionProductoNivel2;
	private String productoNivelUnoSel;

	private String nombreCriterio;
	private String pesoCriterio;
	private String descripcionCriterio;
	private String nombreProductoActual;
	private String descripcionProductoActual;

	// información específica
	private String mostrarCampoUno;
	private boolean visibleTextCampo1 = true;
	private String textoCampoUno;

	private String mostrarCampoDos;
	private boolean visibleTextCampo2 = true;
	private String textoCampoDos;

	private String mostrarCampoTres;
	private boolean visibleTextCampo3 = true;
	private String textoCampoTres;

	private String mostrarCampoCuatro;
	private boolean visibleTextCampo4 = true;
	private String textoCampoCuatro;

	private String mostrarCampoCinco;
	private boolean visibleTextCampo5 = true;
	private String textoCampoCinco;

	private String mostrarCampoSeis;
	private boolean visibleTextCampo6 = true;
	private String textoCampoSeis;

	private boolean campoUnoObligatorio = false;
	private boolean campoDosObligatorio = false;
	private boolean campoTresObligatorio = false;
	private boolean campoCuatroObligatorio = false;
	private boolean campoCincoObligatorio = false;
	private boolean campoSeisObligatorio = false;

	private String mostrarCampoAsignaturas;
	private String mostrarCampoObjetivosResponsable;
	private String mostrarCampoGrupos;
	private String mostrarLugarEjecucion;

	private boolean esConvocatoriaProyectos = false;
	private boolean esConvocatoriaMovilidades = false;

	private TipoModalidad tipoModalidadConvocatoria;
	private String idRestriccionMovilidad;
	private boolean mostrarCamposMovilidadDocente = false;
	private boolean mostrarCamposMovilidadVisitante = false;
	private boolean mostrarCamposMovilidadEstPonencia = false;
	private boolean mostrarCamposMovilidadEstPasantia = false;
	private String registroProyectoLiderGrupo;

	private String mostrarIntegrantesSinDatos;
	private String mostarActividadesInvestigador;
	private String mostrarEvaluacionesIndividuales;
	private String tipoFinanciación;
	private String mostrarProductos;
	private String mostrarBiodiversidad;
	private String mostarActivdadesIntegrantesSinDatos;

	/***************************************************************************
	 * CONSTRUCTOR DE LA CLASE
	 **************************************************************************/
	public ManejadorInsercionConvocatorias() {
		super();
		this.init();
	}

	private void init() {

		idSede = "";
		listaTemporalProductoSeleccionados = new HashMap();
		listaTemporalCompromisosSeleccionados = new HashMap();
		listaConvocatorias = new ArrayList();
		tablaModalidadFuente = new DataTable();
		tablaNivel1 = new DataTable();
		tablaNivel2 = new DataTable();
		tablaCompromisoNivel1 = new DataTable();
		convocatoriaPadre = new ConvocatoriaPadre();
		listaSede = new ArrayList();
		listaDependencia = new ArrayList();
		listaDependenciaRestriccion = new ArrayList();
		listaEstadosConvocatoria = new ArrayList();
		listaConvocatoriasPadre = new ArrayList();
		listaRubros = new ArrayList();
		listaRubroPadres = new ArrayList();
		listaRubroHijos = new ArrayList();
		listaRubroNivel3 = new ArrayList();
		valorCiudades = new ArrayList();
		tablaProyecto = new DataTable();

		requisito = new TipoRequisito();
		compromiso = new TipoCompromiso();

		compromisoActual = new TipoCompromiso();
		compromisoActual.setPadre(new TipoCompromiso());
		selectCompromiso = new UISelectMany();

		requisitoActual = new TipoRequisito();
		requisitoActual.setPadre(new TipoRequisito());
		selectCompromiso = new UISelectMany();

		this.listaproductosTipo = new ArrayList();
		productoActual = new ProductoTipo();
		productoActual.setPadre(new ProductoTipo());
		rubroFinanciableActual = new RubroFinanciable();
		rubroFinanciableActual.setPorcentajeMaximo(100);
		rubroFinanciableActualHijo = new RubroFinanciable();
		rubroFinanciableActualHijo.setPorcentajeMaximo(100);
		rubroFinanciableActualNivel3 = new RubroFinanciable();
		rubroFinanciableActualNivel3.setPorcentajeMaximo(100);
		List listaParametrosAux = servicioGeneral.obtenerListaObjetos("Parametro");
		listaParametros = new ArrayList();
		listaParametros.add(new Parametro());
		((Parametro) listaParametros.get(0)).setId(new Long("-1"));
		((Parametro) listaParametros.get(0)).setNombre("PORCENTAJE");
		((Parametro) listaParametros.get(0)).setDescripcion("porcentaje");

		System.out.print("E.....................:" + this.estadoIngreso);

		mostrarGuardarAvanzar = true;
		mostrarGuardarSalir = true;

		try {
			cargarListaProductosConvocatoria();
			obtenerListaSedes();
			cargarListaDependenciasRestriccion();
			cargarListaEstadosConvocatoria();
			cargarListaRubros();
			cargarListaFuenteFinanaciacion();

			convocatoriaActual = (Convocatoria) sesion.getAttribute("convocatoriaEdicion");
			esEdicion = (String) sesion.getAttribute("siEdicion");

			List listaCriteriosAux;
			if (convocatoriaActual != null) {
				listaCriteriosAux = servicioGeneral.obtenerListaObjetos("CriterioEvaluacion where activo = '" + convocatoriaActual.getId() + "'");
			} else {
				listaCriteriosAux = servicioGeneral.obtenerListaObjetos("CriterioEvaluacion where activo = 'A'");
			}
			listaCriterios = new Vector();
			listaTiposInvestigacion = new Vector();
			for (Iterator iteradorListaCriteriosAux = listaCriteriosAux.iterator(); iteradorListaCriteriosAux.hasNext();) {
				CriterioEvaluacion ce = (CriterioEvaluacion) iteradorListaCriteriosAux.next();
				listaCriterios.add(new SelectItem(ce.getId().toString(), ce.getNombre()));
				if (ce.getTipoInvestigacion() != null)
					listaTiposInvestigacion.add(ce.getTipoInvestigacion().getNombre());
				else
					listaTiposInvestigacion.add("");
			}
			listaCriteriosSeleccionados = new Vector();

			List listaTipoPreguntaAux = servicioGeneral.obtenerListaObjetos("TipoPregunta");
			listaTipoPregunta = new Vector();
			for (Iterator itLista = listaTipoPreguntaAux.iterator(); itLista.hasNext();) {
				TipoPregunta tp = (TipoPregunta) itLista.next();
				listaTipoPregunta.add(new SelectItem(tp.getId(), tp.getNombre()));
			}
			panelGridCriteriosModalidadTipoPregunta = new HtmlPanelGrid();
			tablaModalidadCriterioTipoPregunta = new DataTable();
			listaModalidadFuenteFinanciacion = new ArrayList<ModalidadFuenteFinanciacion>();

			listaTipoArchivoMovilidad = new ArrayList<TipoArchivoMovilidad>();
			listaMovilidadArchivo = new ArrayList<MovilidadArchivo>();

			if (convocatoriaActual == null || convocatoriaActual.getId() == null) {
				cargarValoresNuevos();
				establecerValoresPorDefecto();
			} else {
				this.estadoIngreso = convocatoriaActual.getEtapaCreacion().intValue();

				cargarDatosConvocatoriaActual();

				if (esConvocatoriaMovilidades) {
					if (estadoIngreso == 1 && !validarCamposFormularioMovilidad()) {
						return;
					} else if (estadoIngreso == 2 && !validarRequisitos()) {
						return;
					}
					if (estadoIngreso == 3) {
						this.activarGuardar = true;
						this.mostrarGuardarAvanzar = false;
						this.mostrarGuardarSalir = false;
					} else {
						this.mostrarGuardarAvanzar = true;
						this.mostrarGuardarSalir = true;
						this.activarGuardar = false;
					}
				} else {
					if (estadoIngreso == 1 && !validarCamposFormulario()) {
						return;
					} else if (estadoIngreso == 2 && !validarRequisitos()) {
						return;
					} else if (estadoIngreso == 3 && !validarRubros()) {
						return;
					} else if (estadoIngreso == 4 && !validarCompromisos()) {
						return;
					} else if (estadoIngreso == 5 && !validarProductos()) {
						return;
					} else if (estadoIngreso == 6 && !validarCriterios()) {
						return;
					}

					cargarCriterios();

					if (estadoIngreso == 7) {
						this.activarGuardar = true;
						this.mostrarGuardarAvanzar = false;
						this.mostrarGuardarSalir = false;
					} else {
						this.mostrarGuardarAvanzar = true;
						this.mostrarGuardarSalir = true;
						this.activarGuardar = false;
					}
				}

				actualizarIngreso(!esConvocatoriaMovilidades);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarIngreso(boolean proyectos) {
		/* 7 */activarInfoEspecifica = false;
		/* 6 */activarProductos = false;
		/* 5 */activarCriterios = false;
		/* 4 */activarCompromisos = false;
		/* 3 */activarRubros = false;
		/* 2 */activarRequisitos = false;
		/* 1 */activarGeneralidades = false;
		switch (estadoIngreso) {
		case 9:
		case 8:
		case 7:
			activarInfoEspecifica = true;
		case 6:
			activarProductos = true;
		case 5:
			activarCriterios = true;
		case 4:
			activarCompromisos = true;
		case 3:
			if (proyectos) {
				activarRubros = true;
			} else {
				activarArchivosMovilidad = true;
			}
		case 2:
			activarRequisitos = true;
		case 1:
			activarGeneralidades = true;
		}
	}

	private void cargarDatosConvocatoriaActual() {
		convocatoriaActual = servicioModalidad.obtenerConvocatoriaEdicion(convocatoriaActual.getId());

		tipoModalidadConvocatoria = convocatoriaActual.getTipo();

		if (convocatoriaActual.getTipo().getId().equals(TipoModalidad.CONVOCATORIA_MOVILIDAD)) {
			esConvocatoriaMovilidades = true;
			esConvocatoriaProyectos = false;
		} else {
			esConvocatoriaMovilidades = false;
			esConvocatoriaProyectos = true;
		}

		if (esConvocatoriaProyectos) {
			if (convocatoriaActual.getSede() != null)
				idSede = convocatoriaActual.getSede().getId().toString();
			else
				idSede = "";

			if (this.estadoIngreso == 1) {
				cargarValoresEdicion();
			}

			if (this.estadoIngreso == 2) {
				cargarValoresEdicion();
				cargarRequisitosSeleccionados();
			}

			if (this.estadoIngreso == 3) {
				cargarValoresEdicion();
				cargarRequisitosSeleccionados();
			}
			if (this.estadoIngreso == 4) {
				cargarValoresEdicion();
				consultarRecursos();
				cargarRequisitosSeleccionados();
				cargarCompromisosSeleccionados();
			}
			if (this.estadoIngreso == 5) {
				cargarValoresEdicion();
				cargarRequisitosSeleccionados();
				cargarCompromisosSeleccionados();
				cargarProductosSeleccionados();
			}
			if (this.estadoIngreso == 6) {
				cargarValoresEdicion();
				cargarRequisitosSeleccionados();
				cargarCompromisosSeleccionados();
				cargarProductosSeleccionados();
				cargarCriterios();
			}
			if (this.estadoIngreso == 7) {
				cargarValoresEdicion();
				cargarRequisitosSeleccionados();
				cargarCompromisosSeleccionados();
				cargarProductosSeleccionados();
				cargarCriterios();
				cargarInformacionEspecifica();
			}
		} else {
			if (convocatoriaActual.getSede() != null)
				idSede = convocatoriaActual.getSede().getId().toString();
			else
				idSede = "";

			if (this.estadoIngreso == 1) {
				cargarValoresEdicion();
			}

			if (this.estadoIngreso == 2) {
				cargarValoresEdicion();
				cargarRequisitosSeleccionados();
			}

			if (this.estadoIngreso == 3) {
				cargarValoresEdicion();
				cargarRequisitosSeleccionados();
				cargarTipoArchivosMovilidad();
			}

			idRestriccionMovilidad = convocatoriaActual.getRestriccion().getId();
			validarTipoMovilidad();
		}
		actualizarIngreso(esConvocatoriaProyectos);

	}

	public void cargarTipoArchivosMovilidad() {

		listaMovilidadArchivo = new ArrayList();
		String sqlArchivoMovilidad = "select e from MovilidadArchivo e where e.tipoMovilidad.id = '" + "CF_" + convocatoriaActual.getId() + "'";
		listaMovilidadArchivo = servicioGeneral.obtenerObjetos(sqlArchivoMovilidad);
	}

	public void cargarInformacionEspecifica() {

		String hqlFormEsp = "select e from FormularioInformacionEspecifica e where e.modalidad.id = " + convocatoriaActual.getId();
		List<FormularioInformacionEspecifica> listaFormInfoEsp = servicioGeneral.obtenerObjetos(FormularioInformacionEspecifica.class, hqlFormEsp);
		if (listaFormInfoEsp != null && listaFormInfoEsp.size() > 0) {
			FormularioInformacionEspecifica fie = listaFormInfoEsp.get(0);
			if (fie.getCampoUno()) {
				mostrarCampoUno = "S";
				textoCampoUno = fie.getCampoUnoTexto();
				campoUnoObligatorio = fie.getCampoUnoObligatorio();
			} else {
				mostrarCampoUno = "N";
				visibleTextCampo1 = false;
			}

			if (fie.getCampoDos()) {
				mostrarCampoDos = "S";
				textoCampoDos = fie.getCampoDosTexto();
				campoDosObligatorio = fie.getCampoDosObligatorio();

			} else {
				mostrarCampoDos = "N";
				visibleTextCampo2 = false;
			}

			if (fie.getCampoTres()) {
				mostrarCampoTres = "S";
				textoCampoTres = fie.getCampoTresTexto();
				campoTresObligatorio = fie.getCampoTresObligatorio();
			} else {
				mostrarCampoTres = "N";
				visibleTextCampo3 = false;
			}

			if (fie.getCampoCuatro()) {
				mostrarCampoCuatro = "S";
				textoCampoCuatro = fie.getCampoCuatroTexto();
				campoCuatroObligatorio = fie.getCampoCuatroObligatorio();
			} else {
				mostrarCampoCuatro = "N";
				visibleTextCampo4 = false;
			}

			if (fie.getCampoCinco()) {
				mostrarCampoCinco = "S";
				textoCampoCinco = fie.getCampoCincoTexto();
				campoCincoObligatorio = fie.getCampoCincoObligatorio();
			} else {
				mostrarCampoCinco = "N";
				visibleTextCampo5 = false;
			}

			if (fie.getCampoSeis()) {
				mostrarCampoSeis = "S";
				textoCampoSeis = fie.getCampoSeisTexto();
				campoSeisObligatorio = fie.getCampoSeisObligatorio();
			} else {
				mostrarCampoSeis = "N";
				visibleTextCampo6 = false;
			}

			if (fie.getAsignatura()) {
				mostrarCampoAsignaturas = "S";
			} else {
				mostrarCampoAsignaturas = "N";
			}

			if (fie.getObjetivosResutadosResponsables()) {
				mostrarCampoObjetivosResponsable = "S";
			} else {
				mostrarCampoObjetivosResponsable = "N";
			}

			if (fie.getGrupos()) {
				mostrarCampoGrupos = "S";
			} else {
				mostrarCampoGrupos = "N";
			}

			if (fie.getLugarEjecucion()) {
				mostrarLugarEjecucion = "S";
			} else {
				mostrarLugarEjecucion = "N";
			}
		}

	}

	public void crearTipoArchivoMovilidad() {

		if (nombreTipoArchivoMovilidad.trim().equals("")) {
			avisoArchivosMovilidad = "Por favor ingrese el nombre del tipo de archivo a crear.";
		} else {
			TipoArchivoMovilidad tam = new TipoArchivoMovilidad();
			tam.setNombre(nombreTipoArchivoMovilidad);
			servicioGeneral.guardarObjeto(tam);

			listaTipoArchivoMovilidad.add(tam);
		}

		nombreTipoArchivoMovilidad = "";
	}

	private void cargarCriterios() {

		listaCriteriosSeleccionados = new Vector();

		listaCriteriosTipoPregunta = new Vector();
		Iterator iteradorTiposInvestigacion = listaTiposInvestigacion.iterator();
		for (Iterator it = listaCriterios.iterator(); it.hasNext();) {
			VistaModalidadCriterioTipoPregunta vmctp = new VistaModalidadCriterioTipoPregunta();
			SelectItem ce = (SelectItem) it.next();
			String nombreTipoInvestigacion = (String) iteradorTiposInvestigacion.next();
			ModalidadCriterioTipoPregunta mctpActual = servicioEvaluacion.obtenerModalidadTipoPreguntaXModalidadYCriterio(convocatoriaActual.getId(), new Long((String) ce.getValue()));
			if (mctpActual != null) {
				vmctp.setIdTipoPregunta(mctpActual.getTipoPregunta().getId());
			} else {
				vmctp = new VistaModalidadCriterioTipoPregunta();
			}
			Long idCriterio = new Long(((String) ce.getValue()));
			if (servicioModalidad.modalidadContieneCriterio(convocatoriaActual, idCriterio)) {
				List listaC = new Vector();
				listaC.add(idCriterio.toString());

				vmctp.setCriterioSeleccionado(listaC);
			}
			vmctp.setCriterio(ce);
			vmctp.setNombreTipoInvestigacion(nombreTipoInvestigacion);

			listaCriteriosTipoPregunta.add(vmctp);
		}
	}

	public void agregarCriterioEvaluacion() {
		CriterioEvaluacion ce = new CriterioEvaluacion();
		ce.setNombre(nombreCriterio);
		ce.setDescripcion(descripcionCriterio);
		ce.setActivo(convocatoriaActual.getId().toString());
		Float fac = Float.parseFloat(pesoCriterio) / 100;
		ce.setFactor(fac);
		servicioGeneral.guardarObjeto(ce);
		setNombreCriterio("");
		setDescripcionCriterio("");
		setPesoCriterio("");
		ce = new CriterioEvaluacion();

		recargarListarCriterios();
	}

	public void recargarListarCriterios() {
		List listaCriteriosAux = servicioGeneral.obtenerListaObjetos("CriterioEvaluacion where activo = '" + convocatoriaActual.getId() + "'");
		listaCriterios = new Vector();
		listaTiposInvestigacion = new Vector();
		for (Iterator iteradorListaCriteriosAux = listaCriteriosAux.iterator(); iteradorListaCriteriosAux.hasNext();) {
			CriterioEvaluacion ce = (CriterioEvaluacion) iteradorListaCriteriosAux.next();
			listaCriterios.add(new SelectItem(ce.getId().toString(), ce.getNombre()));
			if (ce.getTipoInvestigacion() != null)
				listaTiposInvestigacion.add(ce.getTipoInvestigacion().getNombre());
			else
				listaTiposInvestigacion.add("");
		}

		listaCriteriosTipoPregunta = new Vector();
		Iterator iteradorTiposInvestigacion = listaTiposInvestigacion.iterator();
		for (Iterator it = listaCriterios.iterator(); it.hasNext();) {
			String nombreTipoInvestigacion = (String) iteradorTiposInvestigacion.next();
			VistaModalidadCriterioTipoPregunta vmctp = new VistaModalidadCriterioTipoPregunta();
			SelectItem ce = (SelectItem) it.next();
			vmctp = new VistaModalidadCriterioTipoPregunta();

			vmctp.setCriterio(ce);
			vmctp.setNombreTipoInvestigacion(nombreTipoInvestigacion);

			listaCriteriosTipoPregunta.add(vmctp);
		}
	}

	public void consultarRecursos() {
		List myList = new ArrayList();
		if (convocatoriaActual != null) {
			myList = servicioGeneral.seleccionarValores(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId());
		}

		if (myList.size() > 0) {
			DistribucionRecursos dist = new DistribucionRecursos();
			Ciudades ciudad = new Ciudades();
			int i = 0;
			while (i < myList.size()) {
				dist = (DistribucionRecursos) myList.get(i);
				if (dist.getSed_id().equals("1")) {
					ciudad.setNivelNal(dist.getValor());
					ciudad.setNivelNalApoyo(dist.getApoyo());
					this.nivelNal = dist.getValor().longValue();
					this.nivelNalApoyo = dist.getApoyo().longValue();
				}
				if (dist.getSed_id().equals("2")) {
					ciudad.setBogota(dist.getValor());
					ciudad.setBogotaApoyo(dist.getApoyo());
					this.bogota = dist.getValor().longValue();
					this.bogotaApoyo = dist.getApoyo().longValue();
				}
				if (dist.getSed_id().equals("3")) {
					ciudad.setMedellin(dist.getValor());
					ciudad.setMedellinApoyo(dist.getApoyo());
					this.medellin = dist.getValor().longValue();
					this.medellinApoyo = dist.getApoyo().longValue();
				}
				if (dist.getSed_id().equals("4")) {
					ciudad.setManizales(dist.getValor());
					ciudad.setManizalesApoyo(dist.getApoyo());
					this.manizales = dist.getValor().longValue();
					this.manizalesApoyo = dist.getApoyo().longValue();
				}
				if (dist.getSed_id().equals("5")) {
					ciudad.setPalmira(dist.getValor());
					ciudad.setPalmiraApoyo(dist.getApoyo());
					this.palmira = dist.getValor().longValue();
					this.palmiraApoyo = dist.getApoyo().longValue();
				}
				if (dist.getSed_id().equals("6")) {
					ciudad.setAmazonia(dist.getValor());
					ciudad.setAmazoniaApoyo(dist.getApoyo());
					this.amazonia = dist.getValor().longValue();
					this.amazoniaApoyo = dist.getApoyo().longValue();
				}
				if (dist.getSed_id().equals("7")) {
					ciudad.setOrinoquia(dist.getValor());
					ciudad.setOrinoquiaApoyo(dist.getApoyo());
					this.orinoquia = dist.getValor().longValue();
					this.orinoquiaApoyo = dist.getApoyo().longValue();
				}
				if (dist.getSed_id().equals("8")) {
					ciudad.setCaribe(dist.getValor());
					ciudad.setCaribeApoyo(dist.getApoyo());
					this.caribe = dist.getValor().longValue();
					this.caribeApoyo = dist.getApoyo().longValue();
				}
				i = i + 1;
			}
			valorCiudades.add(ciudad);
		}
	}

	public void guardarRecursos() {
		mensajeDistribucion = "";
		mensajeApoyos = "";
		long result = nivelNal + bogota + medellin + palmira + orinoquia + caribe + amazonia + manizales;
		long result1 = nivelNalApoyo + bogotaApoyo + medellinApoyo + palmiraApoyo + orinoquiaApoyo + amazoniaApoyo + manizalesApoyo;
		long recursos = Long.parseLong(convocatoriaActual.getRecursosFinancieros());
		long apoyos = Long.parseLong(convocatoriaActual.getNumeroApoyos());
		if (result > recursos) {
			mensajeDistribucion = "El valor de la distribucion ingresado que es " + result + " es mayor que el del presupuesto de la convocatoria de " + recursos;
		} else if (result1 > apoyos) {
			mensajeApoyos = "El numero de los apoyos ingresados que es " + result1 + " es mayor que el numero de apoyos de la convocatoria de " + apoyos;
		} else {
			if (nivelNal != 0 || nivelNalApoyo != 0) {
				Long nivel = new Long(nivelNal);
				Long nivelApoyo = new Long(nivelNalApoyo);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "1", nivel, nivelApoyo);
			} else {
				Long nivel = new Long(0);
				Long nivelApoyo = new Long(0);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "1", nivel, nivelApoyo);
			}
			if (bogota != 0 || bogotaApoyo != 0) {
				Long bog = new Long(bogota);
				Long bogApoyo = new Long(bogotaApoyo);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "2", bog, bogApoyo);
			} else {
				Long bog = new Long(0);
				Long bogApoyo = new Long(0);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "2", bog, bogApoyo);
			}
			if (medellin != 0 || medellinApoyo != 0) {
				Long med = new Long(medellin);
				Long medApoyo = new Long(medellinApoyo);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "3", med, medApoyo);
			} else {
				Long med = new Long(0);
				Long medApoyo = new Long(0);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "3", med, medApoyo);
			}
			if (manizales != 0 || manizalesApoyo != 0) {
				Long man = new Long(manizales);
				Long manApoyo = new Long(manizalesApoyo);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "4", man, manApoyo);
			} else {
				Long man = new Long(0);
				Long manApoyo = new Long(0);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "4", man, manApoyo);
			}
			if (palmira != 0 || palmiraApoyo != 0) {
				Long pal = new Long(palmira);
				Long palApoyo = new Long(palmiraApoyo);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "5", pal, palApoyo);
			} else {
				Long pal = new Long(0);
				Long palApoyo = new Long(0);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "5", pal, palApoyo);
			}
			if (amazonia != 0 || amazoniaApoyo != 0) {
				Long ama = new Long(amazonia);
				Long amaApoyo = new Long(amazoniaApoyo);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "6", ama, amaApoyo);
			} else {
				Long ama = new Long(0);
				Long amaApoyo = new Long(0);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "6", ama, amaApoyo);
			}
			if (orinoquia != 0 || orinoquiaApoyo != 0) {
				Long ori = new Long(orinoquia);
				Long oriApoyo = new Long(orinoquiaApoyo);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "7", ori, oriApoyo);
			} else {
				Long ori = new Long(0);
				Long oriApoyo = new Long(0);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "7", ori, oriApoyo);
			}
			if (caribe != 0 || caribeApoyo != 0) {
				Long car = new Long(caribe);
				Long carApoyo = new Long(caribeApoyo);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "8", car, carApoyo);
			} else {
				Long car = new Long(0);
				Long carApoyo = new Long(caribeApoyo);
				servicioGeneral.insertarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "8", car, carApoyo);
			}
			mensajeDistribucion = "Las sedes han sido distribuidas satisfactoriamente";
		}
	}

	public void actualizarRecursos() {
		mensajeDistribucion = "";
		mensajeApoyos = "";
		int i = 0;
		long result = nivelNal + bogota + medellin + palmira + orinoquia + caribe + amazonia + manizales;
		long result1 = nivelNalApoyo + bogotaApoyo + medellinApoyo + palmiraApoyo + orinoquiaApoyo + caribeApoyo + amazoniaApoyo + manizalesApoyo;
		long recursos = Long.parseLong(convocatoriaActual.getRecursosFinancieros());
		long apoyos = Long.parseLong(convocatoriaActual.getNumeroApoyos());
		if (result > recursos) {
			mensajeDistribucion = "El valor de la distribucion ingresado que es " + result + " es mayor que el del presupuesto de la convocatoria de " + recursos;
		} else if (result1 > apoyos) {
			mensajeApoyos = "El numero de los apoyos ingresados que es " + result1 + " es mayor que el numero de apoyos de la convocatoria de " + apoyos;
		} else {
			if (nivelNal != 0) {
				Long nivel = new Long(nivelNal);
				Long nivelApoyo = new Long(nivelNalApoyo);
				servicioGeneral.actualizarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "1", nivel, nivelApoyo);
			}
			if (bogota != 0) {
				Long bog = new Long(bogota);
				Long bogApoyo = new Long(bogotaApoyo);
				servicioGeneral.actualizarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "2", bog, bogApoyo);
			}
			if (medellin != 0) {
				Long med = new Long(medellin);
				Long medApoyo = new Long(medellinApoyo);
				servicioGeneral.actualizarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "3", med, medApoyo);
			}
			if (manizales != 0) {
				Long man = new Long(manizales);
				Long manApoyo = new Long(manizalesApoyo);
				servicioGeneral.actualizarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "4", man, manApoyo);
			}
			if (palmira != 0) {
				Long pal = new Long(palmira);
				Long palApoyo = new Long(palmiraApoyo);
				servicioGeneral.actualizarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "5", pal, palApoyo);
			}
			if (amazonia != 0) {
				Long ama = new Long(amazonia);
				Long amaApoyo = new Long(amazoniaApoyo);
				servicioGeneral.actualizarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "6", ama, amaApoyo);
			}
			if (orinoquia != 0) {
				Long ori = new Long(orinoquia);
				Long oriApoyo = new Long(orinoquiaApoyo);
				servicioGeneral.actualizarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "7", ori, oriApoyo);
			}
			if (caribe != 0) {
				Long car = new Long(caribe);
				Long carApoyo = new Long(caribeApoyo);
				servicioGeneral.actualizarRecursos(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId(), "8", car, carApoyo);
			}
			mensajeDistribucion = "Las sedes han sido actualizadas satisfactoriamente";
		}
	}

	private void establecerValoresPorDefecto() {

		if (this.convocatoriaActual.getFechaInicio() == null)
			this.convocatoriaActual.setFechaInicio(new Date());

		if (this.convocatoriaActual.getFechaFinal() == null)
			this.convocatoriaActual.setFechaFinal(new Date());

		if (this.convocatoriaActual.getFechaInicioReclamacion() == null)
			this.convocatoriaActual.setFechaInicioReclamacion(new Date());

		if (this.convocatoriaActual.getFechaFinalReclamacion() == null)
			this.convocatoriaActual.setFechaFinalReclamacion(new Date());

		if (this.convocatoriaActual.getFechaPublicacionResultados() == null)
			this.convocatoriaActual.setFechaPublicacionResultados(new Date());

		if (this.convocatoriaActual.getFechaInicioEval() == null)
			this.convocatoriaActual.setFechaInicioEval(new Date());

		if (this.convocatoriaActual.getFechaFinalEval() == null)
			this.convocatoriaActual.setFechaFinalEval(new Date());

		if (this.convocatoriaActual.getFechaInicioReclamacionEvaluacion() == null)
			this.convocatoriaActual.setFechaInicioReclamacionEvaluacion(new Date());

		if (this.convocatoriaActual.getFechaFinalReclamacionEvaluacion() == null)
			this.convocatoriaActual.setFechaFinalReclamacionEvaluacion(new Date());
	}

	private void cargarValoresEdicion() {
		// dependenciaActual = convocatoriaActual.getDependencia();
		// sedeActual = dependenciaActual.getSede();
		estadoConvocatoria = convocatoriaActual.getEstadoConvocatoria();

		// Si la convocatoria es para grupos
		if (convocatoriaActual.getEsParaGrupos() != null && convocatoriaActual.getEsParaGrupos().booleanValue() == true) {
			esParaGrupos = new String("S");
			visiblePanelGrupos = true;

			// se asignan los valores de grupos que trae la convocatoria a la
			// listaTipoGrupos
			listaTipoGrupos = new String[5];
			if (convocatoriaActual.getGruposRegistrados() != null && convocatoriaActual.getGruposRegistrados().booleanValue())
				listaTipoGrupos[0] = CategoriaGrupo.ACTIVO;
			if (convocatoriaActual.getGruposReconocidos() != null && convocatoriaActual.getGruposReconocidos().booleanValue())
				listaTipoGrupos[1] = CategoriaGrupo.INGRESANDO;
			if (convocatoriaActual.getGruposCategoriaA() != null && convocatoriaActual.getGruposCategoriaA().booleanValue())
				listaTipoGrupos[2] = CategoriaGrupo.SOLICITUD_AVAL;
			if (convocatoriaActual.getGruposCategoriaB() != null && convocatoriaActual.getGruposCategoriaB().booleanValue())
				listaTipoGrupos[3] = CategoriaGrupo.DEVUELTO_CORRECCION;
			if (convocatoriaActual.getGruposCategoriaC() != null && convocatoriaActual.getGruposCategoriaC().booleanValue())
				listaTipoGrupos[4] = CategoriaGrupo.REGISTRADO;

		} else {
			esParaGrupos = new String("N");
			visiblePanelGrupos = false;
		}

		// Si la convocatoria tiene restriccion
		if (convocatoriaActual.getDependenciaRestriccion() != null) {
			dependenciaActualRestriccion = convocatoriaActual.getDependenciaRestriccion();
			esParaSedes = new String("S");
			visiblePanelSedes = true;
		} else {
			esParaSedes = new String("N");
			visiblePanelSedes = false;
		}

		listaModalidadFuenteFinanciacion = servicioModalidad.listaModFuenteFinXModalidad(convocatoriaActual.getId());

	}

	private void cargarValoresNuevos() {
		TipoModalidad tipoModalidadConvocatoriaSeleccionada = (TipoModalidad) sesion.getAttribute("tipoModalidadSeleccionada");

		if (tipoModalidadConvocatoriaSeleccionada != null) {

			tipoModalidadConvocatoria = (TipoModalidad) sesion.getAttribute("tipoModalidadSeleccionada");

			if (tipoModalidadConvocatoriaSeleccionada.getId().equals(TipoModalidad.CONVOCATORIA_MOVILIDAD)) {
				esConvocatoriaMovilidades = true;
				esConvocatoriaProyectos = false;
			} else {
				esConvocatoriaMovilidades = false;
				esConvocatoriaProyectos = true;
			}
		} else {
			tipoModalidadConvocatoria = new TipoModalidad();
			tipoModalidadConvocatoria.setId(TipoModalidad.CONVOCATORIA_FICHA_MINIMA_HOME);
		}

		estadoConvocatoria = convocatoriaActual.getPadre().getEstadoConvocatoria();
		// cuando comienza la convocatoria no es para grupos y se oculta el
		// panel
		esParaGrupos = new String("N");
		visiblePanelGrupos = false;

		// cuando comienza la convocatoria no tiene restriccion de dependencia
		esParaSedes = new String("N");
		visiblePanelSedes = false;

		listaCriteriosTipoPregunta = new Vector();
		Iterator iteradorTiposInvestigacion = listaTiposInvestigacion.iterator();
		for (Iterator it = listaCriterios.iterator(); it.hasNext();) {
			String nombreTipoInvestigacion = (String) iteradorTiposInvestigacion.next();
			VistaModalidadCriterioTipoPregunta vmctp = new VistaModalidadCriterioTipoPregunta();
			SelectItem ce = (SelectItem) it.next();
			vmctp = new VistaModalidadCriterioTipoPregunta();

			vmctp.setCriterio(ce);
			vmctp.setNombreTipoInvestigacion(nombreTipoInvestigacion);

			listaCriteriosTipoPregunta.add(vmctp);
		}
	}

	public void cambioSiMostrarCampoUno() {
		if (mostrarCampoUno.equals("S")) {
			visibleTextCampo1 = true;
		} else {
			visibleTextCampo1 = false;
		}
	}

	public void cambioSiMostrarCampoDos() {
		if (mostrarCampoDos.equals("S")) {
			visibleTextCampo2 = true;
		} else {
			visibleTextCampo2 = false;
		}
	}

	public void cambioSiMostrarCampoTres() {
		if (mostrarCampoTres.equals("S")) {
			visibleTextCampo3 = true;
		} else {
			visibleTextCampo3 = false;
		}
	}

	public void cambioSiMostrarCampoCuatro() {
		if (mostrarCampoCuatro.equals("S")) {
			visibleTextCampo4 = true;
		} else {
			visibleTextCampo4 = false;
		}
	}

	public void cambioSiMostrarCampoCinco() {
		if (mostrarCampoCinco.equals("S")) {
			visibleTextCampo5 = true;
		} else {
			visibleTextCampo5 = false;
		}
	}

	public void cambioSiMostrarCampoSeis() {
		if (mostrarCampoSeis.equals("S")) {
			visibleTextCampo6 = true;
		} else {
			visibleTextCampo6 = false;
		}
	}

	public void cambioSiNoGrupos() {
		if (esParaGrupos.equals("S")) {
			visiblePanelGrupos = true;
		} else {
			visiblePanelGrupos = false;
		}
	}

	public void eliminarRequisito() {
		if (this.requisito.getHijos() != null) {
			this.requisito.getHijos().remove(requisitoSeleccionado);
			requisitoSeleccionado = new TipoRequisito();
		}
	}

	public void eliminarCompromiso() {
		if (this.compromiso.getHijos() != null) {
			this.compromiso.getHijos().remove(compromisoSeleccionado);
			compromisoSeleccionado = new TipoCompromiso();
		}
	}

	public void cambioSiNoSedes() {
		if (esParaSedes.equals("S")) {
			visiblePanelSedes = true;
		} else {
			visiblePanelSedes = false;
		}
	}

	private boolean validarCamposFormulario() {
		boolean resul = false;

		String[] camposAValidar = new String[11];

		camposAValidar[0] = this.convocatoriaActual.getTitulo();
		camposAValidar[1] = this.convocatoriaActual.getDirigidoA();
		camposAValidar[2] = this.convocatoriaActual.getOferente();
		camposAValidar[3] = this.convocatoriaActual.getObjetivo();

		if (this.convocatoriaActual.getRecursosFinancieros() == null)
			camposAValidar[4] = null;
		else
			camposAValidar[4] = this.convocatoriaActual.getRecursosFinancieros().toString();

		if (this.convocatoriaActual.getFechaInicio() == null)
			camposAValidar[5] = null;
		else
			camposAValidar[5] = this.convocatoriaActual.getFechaInicio().toGMTString();

		if (this.convocatoriaActual.getFechaFinal() == null)
			camposAValidar[6] = null;
		else
			camposAValidar[6] = this.convocatoriaActual.getFechaFinal().toGMTString();

		if (this.convocatoriaActual.getFechaPublicacionResultados() == null)
			camposAValidar[7] = null;
		else
			camposAValidar[7] = this.convocatoriaActual.getFechaPublicacionResultados().toGMTString();
		try {
			Integer.parseInt(this.convocatoriaActual.getNumeroApoyos());
		} catch (NumberFormatException e) {
			this.convocatoriaActual.setNumeroApoyos(null);
		}
		camposAValidar[8] = this.convocatoriaActual.getNumeroApoyos();
		camposAValidar[9] = this.convocatoriaActual.getMontoApoyoGanadores();

		if (this.convocatoriaActual.getTiempoEjecucionProyecto() == null)
			camposAValidar[10] = null;
		else
			camposAValidar[10] = this.convocatoriaActual.getTiempoEjecucionProyecto().toString();

		String[] nombresCamposAValidar = new String[] { "Nombre de la modalidad", "Dirigido a", "Oferente", "Objetivo", "Presupuesto", "Fecha inicio", "Fecha fin", "Fecha publicación resultados", "Número de apoyos", "Monto para ganadores", "Duración proyectos" };
		this.avisoFormulario = ValidacionUtils.validarCamposRequeridos(camposAValidar, nombresCamposAValidar);

		List avisosFecha = new ArrayList();

		avisoFechas = new String();

		if (!this.convocatoriaActual.getFechaInicio().before(this.convocatoriaActual.getFechaFinal())) {
			avisosFecha.add(" La fecha de Finalizacion debe ser posterior a la de Inicio del proyecto");
		}

		if (avisosFecha.isEmpty()) {
			avisoFechas = StringUtils.STRING_VACIO;
		} else {
			avisoFechas = StringUtils.colocarSeparador(avisosFecha, ", ");
		}

		if (this.avisoFormulario.equals(StringUtils.STRING_VACIO) && this.avisoFechas.equalsIgnoreCase(StringUtils.STRING_VACIO)) {
			resul = true;
		}

		return resul;
	}

	private boolean validarCamposFormularioMovilidad() {
		boolean resul = false;

		String[] camposAValidar = new String[6];

		camposAValidar[0] = this.convocatoriaActual.getTitulo();
		camposAValidar[1] = this.convocatoriaActual.getDirigidoA();
		camposAValidar[2] = this.convocatoriaActual.getOferente();
		camposAValidar[3] = this.convocatoriaActual.getObjetivo();

		if (this.convocatoriaActual.getFechaInicio() == null)
			camposAValidar[4] = null;
		else
			camposAValidar[4] = this.convocatoriaActual.getFechaInicio().toGMTString();

		if (this.convocatoriaActual.getFechaFinal() == null)
			camposAValidar[5] = null;
		else
			camposAValidar[5] = this.convocatoriaActual.getFechaFinal().toGMTString();

		String[] nombresCamposAValidar = new String[] { "Nombre de la modalidad", "Dirigido a", "Oferente", "Objetivo", "Presupuesto", "Fecha inicio", "Fecha fin" };
		this.avisoFormulario = ValidacionUtils.validarCamposRequeridos(camposAValidar, nombresCamposAValidar);

		List avisosFecha = new ArrayList();

		avisoFechas = new String();

		if (!this.convocatoriaActual.getFechaInicio().before(this.convocatoriaActual.getFechaFinal())) {
			avisosFecha.add(" La fecha de Finalizacion debe ser posterior a la de Inicio del proyecto");
		}

		if (avisosFecha.isEmpty()) {
			avisoFechas = StringUtils.STRING_VACIO;
		} else {
			avisoFechas = StringUtils.colocarSeparador(avisosFecha, ", ");
		}

		if (this.avisoFormulario.equals(StringUtils.STRING_VACIO) && this.avisoFechas.equalsIgnoreCase(StringUtils.STRING_VACIO)) {
			resul = true;
		}

		return resul;
	}

	private void limpiarAvisos() {
		this.avisoFormulario = "";
		this.avisoFechas = "";
		this.avisoProductos = "";
		this.avisoRubros = "";
		this.avisoCompromisos = "";
		this.avisoRequisitos = "";
		this.avisoCriterios = "";
	}

	private TipoRubro buscarTipoRubro(Long id) {
		// BUSCA EL TIPO DE RUBRO POR EL ID
		TipoRubro tr = new TipoRubro();
		int i = 0;
		while (i < listaRubros.size()) {
			tr = (TipoRubro) listaRubros.get(i);
			if (id.longValue() == (tr.getId()).longValue())
				break;
			i = i + 1;
		}
		return tr;
	}

	// Rubros financiables
	private void cargarListaRubros() {
		try {
			// creacion de los items de rubros financiables
			listaRubros = servicioGeneral.obtenerListaObjetos("TipoRubro");
			listaRubroPadres = servicioGeneral.obtenerListaTipoRubro1Nivel(true);
			TipoRubro r;
			for (int i = 0; i < listaRubroPadres.size(); i++) {
				r = (TipoRubro) listaRubroPadres.get(i);
			}
			rubroFinanciableActual.setTipoRubro(new TipoRubro());
			rubroFinanciableActual.getTipoRubro().setId(((TipoRubro) listaRubroPadres.get(0)).getId());
			cargarListaRubrosHijos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cargarListaRubrosHijos() {
		try {
			listaRubroHijos.clear();
			List aux = servicioGeneral.obtenerListaTipoRubroXPadre(rubroFinanciableActual.getTipoRubro());
			if (rubroFinanciableActual.getTipoRubro().getId().equals(53L)) {
				listaRubroNivel3.clear();
				aux = servicioGeneral.obtenerObjetos(TipoRubro.class,
						"from TipoRubro tr where tr.id in ('120','7','135','141','82','143','144','145','142') order by tr.id");
				for (int i = 0; i < aux.size(); i++) {
					listaRubroNivel3.add(aux.get(i));
				}
				rubroFinanciableActualNivel3.setTipoRubro(new TipoRubro());
				rubroFinanciableActualNivel3.getTipoRubro().setId(((TipoRubro) listaRubroNivel3.get(0)).getId());
			} else {
				for (int i = 0; i < aux.size(); i++) {
					listaRubroHijos.add(aux.get(i));
				}
				rubroFinanciableActualHijo.setTipoRubro(new TipoRubro());
				rubroFinanciableActualHijo.getTipoRubro().setId(((TipoRubro) listaRubroHijos.get(0)).getId());
				cargarListaRubrosNivel3();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargarListaRubrosNivel3() {
		try {
			listaRubroNivel3.clear();
			if (!rubroFinanciableActual.getTipoRubro().getId().equals(53L)) {
				List aux = servicioGeneral.obtenerListaTipoRubroXPadre(rubroFinanciableActualHijo.getTipoRubro());
				for (int i = 0; i < aux.size(); i++) {
					listaRubroNivel3.add(aux.get(i));
				}
				rubroFinanciableActualNivel3.setTipoRubro(new TipoRubro());
				rubroFinanciableActualNivel3.getTipoRubro().setId(((TipoRubro) listaRubroNivel3.get(0)).getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void agregarRubroFinanciable() {
		avisoRubros="";
		try {
			rubroFinanciableActualNivel3
					.setTipoRubro(buscarTipoRubro(rubroFinanciableActualNivel3.getTipoRubro().getId()));
			for (Object rubro : convocatoriaActual.getListaRubrosFinanciables()) {
				if (((RubroFinanciable) rubro).getTipoRubro().getId()
						.equals(rubroFinanciableActualNivel3.getTipoRubro().getId())) {
					avisoRubros = "Este rubro ya fue añadido a la convocatoria.";
					rubroFinanciableActualNivel3 = new RubroFinanciable();
					rubroFinanciableActualNivel3.setTipoRubro(new TipoRubro());
					rubroFinanciableActualNivel3.setPorcentajeMaximo(100);
					return;
				}
			}
			convocatoriaActual.adicionarRubroFinanciable(rubroFinanciableActualNivel3);
			rubroFinanciableActualNivel3 = new RubroFinanciable();
			rubroFinanciableActualNivel3.setTipoRubro(new TipoRubro());
			rubroFinanciableActualNivel3.setPorcentajeMaximo(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// PRODUCTOS
	private void cargarListaProductosConvocatoria() {
		this.productoTipoNivel1 = new ArrayList();
		this.productoTipoNivel2 = new ArrayList();
		this.productoTipoNivel3 = new ArrayList();
		this.readOnlySelect = new boolean[3];
		this.seleccionSelect = new String[3];
		this.readOnlySelect[0] = true;
		this.readOnlySelect[1] = true;
		this.listaproductosTipo = servicioGeneral.obtenerObjetos(ProductoTipo.class, "select p from ProductoTipo p where p.estado = 'A' and p.descripcion like 'LISTA_PRODUCTO_UNIFICADA_HERMES' order by p.nombre");
		ProductoTipo producto = new ProductoTipo();
		producto.setId(ProductoTipo.RAIZ_LISTA_UNIFICADA);
		ProductoTipo pt = new ProductoTipo();
		pt.setId("-");
		pt.setNombre("--Seleccione--");
		this.productoTipoNivel1.add(pt);
		this.listaProductosItem = obtenerHijos(producto.getId().toString(), (List) new ArrayList(), true);
		this.productoTipoNivel1.addAll(obtenerHijos(producto.getId().toString(), (List) new ArrayList(), false));
		ProductoTipo p;
		for (int i = 1; i < this.productoTipoNivel1.size(); i++) {
			p = (ProductoTipo) this.productoTipoNivel1.get(i);
			/*
			 * if (p.getNombre().length() > 73) {
			 * p.setNombre(p.getNombre().substring(0, 70)); }
			 */
			p.setTieneHijos(false);
			p.setMostrarHijos(false);
			p.setHijos(obtenerHijos(p.getId().trim(), new ArrayList(), false));
			if (p.getHijos().size() > 0) {
				p.setTieneHijos(true);
				for (Iterator it = p.getHijos().iterator(); it.hasNext();) {
					ProductoTipo hijo = (ProductoTipo) it.next();
					hijo.setTieneHijos(false);
					hijo.setMostrarHijos(false);
					hijo.setHijos(obtenerHijos(hijo.getId().trim(), new ArrayList(), false));
					if (hijo.getHijos().size() > 0) {
						hijo.setTieneHijos(true);
					}
				}
			}
		}
		p = new ProductoTipo();
		p.setId("-1");
		p.setNombre("--Seleccione--");
		this.productoTipoNivel2.add(p);
		this.productoTipoNivel2.addAll(obtenerHijos(((ProductoTipo) this.productoTipoNivel1.get(0)).getId(), (List) new ArrayList(), false));
		for (int i = 0; i < this.productoTipoNivel2.size(); i++) {
			p = (ProductoTipo) this.productoTipoNivel2.get(i);
			/*
			 * if (p.getNombre().length() > 73) {
			 * p.setNombre(p.getNombre().substring(0, 70)); }
			 */
		}
	}

	public void cargarProductosSeleccionados() {
		List listaProductoConvo = new ArrayList();
		listaProductoConvo.addAll(this.convocatoriaActual.getProductos());

		for (int i = 1; i < this.productoTipoNivel1.size(); i++) {
			ProductoTipo tp = (ProductoTipo) this.productoTipoNivel1.get(i);
			List listaProductoArray = new ArrayList();

			for (int j = 0; j < tp.getHijos().size(); j++) {
				ProductoTipo hijo = (ProductoTipo) tp.getHijos().get(j);

				for (int k = 0; k < hijo.getHijos().size(); k++) {
					ProductoTipo nieto = (ProductoTipo) hijo.getHijos().get(k);

					for (Iterator it = listaProductoConvo.iterator(); it.hasNext();) {
						ProductoTipo tipoProducto = (ProductoTipo) it.next();
						if (nieto.getId().equals(tipoProducto.getId())) {
							listaProductoArray.add(nieto);
						}
					}
				}

				this.listaProductoSeleccionados = new String[listaProductoArray.size()];
				if (listaProductoArray.size() > 0) {
					for (int l = 0; l < listaProductoArray.size(); l++) {
						ProductoTipo aux = (ProductoTipo) listaProductoArray.get(l);
						this.listaProductoSeleccionados[l] = aux.getId();
					}
					listaTemporalProductoSeleccionados.put(hijo.getId(), listaProductoSeleccionados);
				}
			}
		}
	}

	private boolean validarProductos() {
		this.mostrarHijosPorNivel(new ProductoTipo());
		List productos = new ArrayList();

		for (int i = 1; i < this.productoTipoNivel1.size(); i++) {
			ProductoTipo p = (ProductoTipo) this.productoTipoNivel1.get(i);

			for (int j = 0; j < p.getHijos().size(); j++) {
				ProductoTipo hijo = (ProductoTipo) p.getHijos().get(j);
				String[] productosSeleccionados = (String[]) listaTemporalProductoSeleccionados.get(hijo.getId());

				if (productosSeleccionados != null) {
					for (Iterator it = hijo.getHijos().iterator(); it.hasNext();) {
						ProductoTipo productoTipo = (ProductoTipo) it.next();
						for (int k = 0; k < productosSeleccionados.length; k++) {
							if (productoTipo.getId().equalsIgnoreCase(productosSeleccionados[k])) {
								productos.add(p);
								productos.add(hijo);
								productos.add(productoTipo);
							}
						}
					}
				}
			}
		}
		convocatoriaActual.setProductos(new HashSet(productos));

		if (productos.size() > 0) {
			productos = null;
			this.avisoProductos = "";
			return true;
		} else {
			productos = null;
			this.avisoProductos = "No han sido asociados productos a la convocatoria.";
			return false;
		}
	}

	private List obtenerHijos(String idPadre, List listaProductos, boolean multiNivel) {
		List listaHijos = servicioGeneral.obtenerListaProductoHijo(idPadre);
		if (multiNivel) {
			if (listaHijos.size() != 0) {
				for (int i = 0; i < listaHijos.size(); i++) {
					listaProductos.add((ProductoTipo) listaHijos.get(i));
					listaProductos = obtenerHijos(((ProductoTipo) listaHijos.get(i)).getId(), listaProductos, true);
				}
			}
		} else {
			listaProductos = listaHijos;
		}
		return listaProductos;
	}

	public void mostrarHijosNivel1() {
		mostrarHijosPorNivel(productoNivel1Seleccionado);
		return;
	}

	public void mostrarHijosNivel2() {
		mostrarHijosPorNivel(productoNivel2Seleccionado);
		return;
	}

	private void mostrarHijosPorNivel(ProductoTipo productoTipo) {

		String idPadreActual = "";
		String idPadreSiguiente = "";

		for (int i = 1; i < this.productoTipoNivel1.size(); i++) {
			ProductoTipo p = (ProductoTipo) this.productoTipoNivel1.get(i);
			if (p.getId().equalsIgnoreCase(productoTipo.getId())) {
				p.setMostrarHijos(true);
			} else {
				p.setMostrarHijos(false);
				if (p.getHijos().size() > 0) {
					for (int j = 0; j < p.getHijos().size(); j++) {
						ProductoTipo hijo = (ProductoTipo) p.getHijos().get(j);
						if (hijo.getId().equalsIgnoreCase(productoTipo.getId())) {
							productoTipo.setTieneHijos(true);
							p.setMostrarHijos(true);
							hijo.setMostrarHijos(true);
							idPadreSiguiente = hijo.getId();
						} else {
							if (hijo.isMostrarHijos()) {
								idPadreActual = hijo.getId();
								hijo.setMostrarHijos(false);
							}

						}
					}
				}
			}
		}
		listaTemporalProductoSeleccionados.put(idPadreActual, this.listaProductoSeleccionados);
		this.listaProductoSeleccionados = (String[]) listaTemporalProductoSeleccionados.get(idPadreSiguiente);
	}

	public void obtenerProductoHijo() {
		// String nivel = nivel1Seleccionado;// event.getComponent().getId();
		String idPadre = seleccionSelect[0];
		int nivelSelect = 1;
		List hijos = new ArrayList();
		ProductoTipo p = new ProductoTipo();
		p.setId("-1");
		p.setNombre("--Seleccione--");
		hijos.add(p);
		hijos.addAll(obtenerHijos(idPadre, (List) new ArrayList(), false));

		switch (nivelSelect) {
		case 1:
			this.productoTipoNivel2 = hijos;
			if (hijos.size() <= 1) {
				this.readOnlySelect[1] = false;
			} else {
				this.readOnlySelect[1] = true;
			}
			if (idPadre.equals("-1")) {
				this.productoActual.getPadre().setId("0");
			} else {
				this.productoActual.getPadre().setId(idPadre);
			}
			break;
		case 2:
			this.productoTipoNivel3 = hijos;
			if (hijos.size() <= 1) {
				this.readOnlySelect[2] = false;
			} else {
				this.readOnlySelect[2] = true;
			}
			if (idPadre.equals("-1")) {
				if (seleccionSelect[0] != null) {
					this.productoActual.getPadre().setId(seleccionSelect[0]);
				}
			} else {
				this.productoActual.getPadre().setId(idPadre);
			}
			break;
		}
	}

	public void obtenerProductoHijo2() {
		// String nivel = nivel1Seleccionado;// event.getComponent().getId();
		String idPadre = seleccionSelect[1];
		int nivelSelect = 2;
		List hijos = new ArrayList();
		ProductoTipo p = new ProductoTipo();
		p.setId("-1");
		p.setNombre("--Seleccione--");
		hijos.add(p);
		hijos.addAll(obtenerHijos(idPadre, (List) new ArrayList(), false));

		switch (nivelSelect) {
		case 1:
			this.productoTipoNivel2 = hijos;
			if (hijos.size() <= 1) {
				this.readOnlySelect[1] = false;
			} else {
				this.readOnlySelect[1] = true;
			}
			if (idPadre.equals("-1")) {
				this.productoActual.getPadre().setId("0");
			} else {
				this.productoActual.getPadre().setId(idPadre);
			}
			break;
		case 2:
			this.productoTipoNivel3 = hijos;
			if (hijos.size() <= 1) {
				this.readOnlySelect[2] = false;
			} else {
				this.readOnlySelect[2] = true;
			}
			if (idPadre.equals("-1")) {
				if (seleccionSelect[0] != null) {
					this.productoActual.getPadre().setId(seleccionSelect[0]);
				}
			} else {
				this.productoActual.getPadre().setId(idPadre);
			}
			break;
		}
	}

	public void agregarProductoNivel1() {

		ProductoTipo ptNivel1 = new ProductoTipo();
		ProductoTipo productoRaiz = new ProductoTipo();
		productoRaiz.setId(ProductoTipo.RAIZ);
		ptNivel1.setPadre(productoRaiz);
		ptNivel1.setNivel("0");
		ptNivel1.setNombre(nombreProductoNivel1);
		ptNivel1.setDescripcion(descripcionProductoNivel1);
		servicioGeneral.insertarObjeto(ptNivel1);
		// reseteo producto actual
		this.setNombreProductoNivel1("");
		this.setDescripcionProductoNivel1("");
		ptNivel1 = new ProductoTipo();
		// cargo de nuevo las listas de productos
		cargarListaProductosConvocatoria();
	}

	public void agregarProductoNivel2() {

		ProductoTipo ptNivel1 = new ProductoTipo();
		ProductoTipo productoRaiz = new ProductoTipo();
		productoRaiz.setId(productoNivelUnoSel);
		ptNivel1.setPadre(productoRaiz);
		ptNivel1.setNivel("1");
		ptNivel1.setNombre(nombreProductoNivel2);
		ptNivel1.setDescripcion(descripcionProductoNivel2);
		servicioGeneral.insertarObjeto(ptNivel1);
		// reseteo producto actual
		this.setNombreProductoNivel2("");
		this.setDescripcionProductoNivel2("");
		ptNivel1 = new ProductoTipo();
		// cargo de nuevo las listas de productos
		cargarListaProductosConvocatoria();
	}

	public void agregarProductoOriginal() {
		// se crea un nuevo producto
		for (int i = 0; i < this.listaproductosTipo.size(); i++) {
			if (((ProductoTipo) this.listaproductosTipo.get(i)).getId().equals(this.productoActual.getPadre().getId())) {
				ProductoTipo productoPadre = (ProductoTipo) this.listaproductosTipo.get(i);
				productoActual.setPadre(productoPadre);
				if (!productoPadre.getNivel().equalsIgnoreCase("0")) {
					productoActual.setNivel(Integer.toString((Integer.parseInt(((ProductoTipo) this.listaproductosTipo.get(i)).getNivel()) + 1)));
					break;
				} else {
					productoActual.setNivel("0");
					break;
				}
			}
		}
		servicioGeneral.insertarObjeto(this.productoActual);
		// reseteo producto actual
		this.productoActual.setNombre("");
		this.productoActual.setDescripcion("");
		// this.productoActual = new ProductoTipo();
		// cargo de nuevo las listas de productos
		cargarListaProductosConvocatoria();
	}

	public void agregarProducto() {
		ProductoTipo ptNivel1 = new ProductoTipo();
		ProductoTipo productoRaiz = new ProductoTipo();
		productoRaiz.setId(seleccionSelect[1]);
		ptNivel1.setPadre(productoRaiz);
		ptNivel1.setNivel("2");
		ptNivel1.setNombre(nombreProductoActual);
		ptNivel1.setDescripcion(descripcionProductoActual);
		servicioGeneral.insertarObjeto(ptNivel1);
		// reseteo producto actual
		setNombreProductoActual("");
		setDescripcionProductoActual("");
		ptNivel1 = new ProductoTipo();
		// cargo de nuevo las listas de productos
		cargarListaProductosConvocatoria();
	}

	public void cambiarTipoBusqueda() {
		if (opcionesTipoProducto.equals("nuevocategoria")) {
			mostrarPgNuevoTipoProducto = true;
			mostrarPgNuevoProducto = false;
		} else {
			mostrarPgNuevoTipoProducto = false;
			mostrarPgNuevoProducto = true;
		}

	}

	public void cambiarNivelTipoProducto() {

		if (selNivelTipoProducto.equals("1")) {
			mostrarNivelProd1 = true;
			mostrarNivelProd2 = false;
		} else {
			mostrarNivelProd1 = false;
			mostrarNivelProd2 = true;
		}
	}

	public void cargarRequisitosSeleccionados() {

		List listaRequisitoConvo = new ArrayList();
		listaRequisitoConvo.addAll(this.convocatoriaActual.getRequisitos());
		// requisito = new TipoRequisito();

		if (listaRequisitoConvo != null && listaRequisitoConvo.size() > 0) {
			requisito = (TipoRequisito) listaRequisitoConvo.get(0);
			requisito.setHijos(servicioGeneral.obtenerListaRequisitoHijo(requisito.getId()));
		}
	}

	public void cargarCompromisosSeleccionados() {

		List listacompromisoConvo = new ArrayList();
		// compromiso= new TipoCompromiso();

		listacompromisoConvo.addAll(this.convocatoriaActual.getCompromisos());
		if (listacompromisoConvo != null && listacompromisoConvo.size() > 0) {
			// compromiso= new TipoCompromiso();
			compromiso = (TipoCompromiso) listacompromisoConvo.get(0);
			compromiso.setHijos(servicioGeneral.obtenerListaCompromisoHijo(compromiso.getId()));
		}
	}

	public void agregarCompromiso() {
		TipoCompromiso com = new TipoCompromiso();
		com.setDescripcion(compromisoActual.getDescripcion());
		com.setNombre(compromisoActual.getNombre());
		com.setPadre(compromiso);

		if (compromiso.getHijos() == null) {
			List listaPrueba1 = new ArrayList();
			;
			compromiso.setHijos(listaPrueba1);
			compromiso.getHijos().add(com);
			this.compromisoActual.setNombre("");
			this.compromisoActual.setDescripcion("");

		} else {
			compromiso.getHijos().add(com);
			this.compromisoActual.setNombre("");
			this.compromisoActual.setDescripcion("");
		}

	}

	private boolean validarCamposInformacionEspecifica() {
		boolean ret = true;

		return ret;
	}

	private boolean validarCriterios() {

		List criterios = new ArrayList();

		if (listaCriteriosTipoPregunta != null) {

			for (int i = 0; i < listaCriteriosTipoPregunta.size(); i++) {

				VistaModalidadCriterioTipoPregunta vmt = (VistaModalidadCriterioTipoPregunta) listaCriteriosTipoPregunta.get(i);

				if (vmt.getCriterioSeleccionado() != null) {
					criterios.add(vmt);
				}
			}
		}
		if (criterios.size() <= 0) {
			this.avisoCriterios = "No han sido asociados criterios a la convocatoria";
			return false;
		}
		return true;

	}

	/** ********************************************************* */
	/** Arreglar validar registros con la nueva manera * */
	/** ********************************************************* */

	private boolean validarRequisitos() {
		ArrayList ReqConvocatoria = new ArrayList();
		ReqConvocatoria.add(requisito);

		convocatoriaActual.setRequisitos(new HashSet(ReqConvocatoria));

		if (requisito.getHijos() != null && requisito.getHijos().size() > 0) {
			this.avisoRequisitos = "";
			return true;
		} else {
			this.avisoRequisitos = "No han sido asociados requisitos a la convocatoria.";
			return false;
		}
	}

	private boolean validarArchivosMovilidad() {
		if (esListaVacia(listaMovilidadArchivo)) {
			this.avisoArchivosMovilidad = "No han sido asociado los archivos obligatorios de la modalidad";
			return false;
		} else {
			return true;
		}

	}

	private boolean validarCompromisos() {
		ArrayList ComproConvocatoria = new ArrayList();
		ComproConvocatoria.add(compromiso);

		convocatoriaActual.setCompromisos(new HashSet(ComproConvocatoria));

		if (compromiso.getHijos() != null && compromiso.getHijos().size() > 0) {
			this.avisoCompromisos = "";
			return true;
		} else {
			this.avisoCompromisos = "No han sido asociados compromisos a la convocatoria.";
			return false;
		}

	}

	public void agregarRequisito() {
		// se crea un nuevo requisito
		TipoRequisito req = new TipoRequisito();
		req.setDescripcion(requisitoActual.getDescripcion());
		req.setNombre(requisitoActual.getNombre());
		req.setPadre(requisito);

		if (requisito.getHijos() == null) {
			List listaPrueba1 = new ArrayList();
			requisito.setHijos(listaPrueba1);
			requisito.getHijos().add(req);
		} else {

			requisito.getHijos().add(req);
		}

		// reset campos
		this.requisitoActual.setNombre("");
		this.requisitoActual.setDescripcion("");
		// cargo de nuevo las listas de requisitos
		// cargarListaRequisito();
	}

	// ***************************************************************************
	// ***************************************************************************

	// Convocatoria Padre
	public void agregarConvocatoriaPadre() {
		// se crea la convocatoria padre como activa
		EstadoConvocatoria estado = new EstadoConvocatoria();
		estado.setId(EstadoConvocatoria.ACTIVA);
		convocatoriaPadre.setEstadoConvocatoria(estado);
		servicioGeneral.guardarObjeto(convocatoriaPadre);

		// se cargan las convocatorias padre
		cargarListaConvocatoriasPadre();
	}

	private boolean validarRubros() {
		System.out.println("A. validarRubros");
		if (convocatoriaActual.getRubrosFinanciables().size() == 0) {
			System.out.println("B. validarRubros");
			this.avisoRubros = "No han sido asociados rubros financiables a la convocatoria.";
			return false;
		}
		System.out.println("C. validarRubros");
		crearArbolFinanciacion();
		System.out.println("D. validarRubros");
		this.avisoRubros = "";
		return true;
	}

	private void crearArbolFinanciacion() {
		System.out.println("1. crearArbolFinanciacion");
		List<TipoRubro> rubrosContrapartidas = new ArrayList<TipoRubro>();
		List<TipoRubro> rubrosEfectivoN3 = new ArrayList<TipoRubro>();
		List<TipoRubro> rubrosEfectivoN2 = new ArrayList<TipoRubro>();
		List<TipoRubro> rubrosEfectivoN1 = new ArrayList<TipoRubro>();
		for (Object rf : convocatoriaActual.getListaRubrosFinanciables()) {
			System.out.println("1. crearArbolFinanciacion - RubrosFinanciables()");
			TipoRubro rubroActual = ((RubroFinanciable) rf).getTipoRubro();
			if (rubroActual.getDescripcion().equals("GASTOS_CP_2022")) {
				rubrosEfectivoN3.add(rubroActual);
				boolean existe = false;
				for (TipoRubro tipoRubro : rubrosEfectivoN2) {
					if (tipoRubro.getId().equals(rubroActual.getPadre().getId())) {
						existe = true;
						break;
					}
				}
				if (!existe) {
					rubrosEfectivoN2.add(rubroActual.getPadre());
				}
				existe = false;
				for (TipoRubro tipoRubro : rubrosEfectivoN1) {
					if (tipoRubro.getId().equals(rubroActual.getPadre().getPadre().getId())) {
						existe = true;
						break;
					}
				}
				if (!existe) {
					rubrosEfectivoN1.add(rubroActual.getPadre().getPadre());
				}
			} else {
				rubrosContrapartidas.add(rubroActual);
			}
		}
		for (ModalidadFuenteFinanciacion mff : servicioModalidad.listaModFuenteFinXModalidad(convocatoriaActual.getId())) {
			System.out.println(mff.getFuenteFinanciacion().getDescripcion());
			System.out.println("3. crearArbolFinanciacion - listaModFuenteFinXModalidad()");
			if(mff.getArbol()==null) {
				mff.setArbol(mff.getId());
				try {
					servicioGeneral.guardarObjeto(mff);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			List arbol = servicioModalidad.getRubroFinanciableArbol(mff.getArbol());
			if (arbol == null || (arbol != null && arbol.isEmpty())) {
				int index = 1;
				for (TipoRubro cuenta : rubrosEfectivoN1) {
					RubroFinanciableArbol rfaN1 = new RubroFinanciableArbol();
					rfaN1.setModalidadFuenteFinanciacion(mff);
					rfaN1.setPadre(0L);
					rfaN1.setOrden(Long.parseLong(String.valueOf(index)));
					rfaN1.setTipoRubro(cuenta);
					rfaN1.setTipo(0L);
					servicioGeneral.guardarObjeto(rfaN1);
					System.out.println(cuenta.getNombre());
					for (TipoRubro objeto : rubrosEfectivoN2) {
						if (objeto.getPadre().getId().equals(cuenta.getId())) {
							index++;
							RubroFinanciableArbol rfaN2 = new RubroFinanciableArbol();
							rfaN2.setModalidadFuenteFinanciacion(mff);
							rfaN2.setPadre(rfaN1.getId());
							rfaN2.setOrden(Long.parseLong(String.valueOf(index)));
							rfaN2.setTipoRubro(objeto);
							rfaN2.setTipo(0L);
							servicioGeneral.guardarObjeto(rfaN2);
							System.out.println("\t" + objeto.getNombre());
							for (TipoRubro subordinal : rubrosEfectivoN3) {
								if (subordinal.getPadre().getId().equals(objeto.getId())) {
									index++;
									RubroFinanciableArbol rfaN3 = new RubroFinanciableArbol();
									rfaN3.setModalidadFuenteFinanciacion(mff);
									rfaN3.setPadre(rfaN2.getId());
									rfaN3.setOrden(Long.parseLong(String.valueOf(index)));
									rfaN3.setTipoRubro(subordinal);
									rfaN3.setTipo(0L);
									servicioGeneral.guardarObjeto(rfaN3);
									System.out.println("\t\t" + subordinal.getNombre());
								}
							}
						}
					}
				}
				if(!rubrosContrapartidas.isEmpty()) {
					mff.setIncluirContrapartida(true);
					servicioGeneral.guardarObjeto(mff);
				}
				index++;
				RubroFinanciableArbol rfaN1 = new RubroFinanciableArbol();
				rfaN1.setModalidadFuenteFinanciacion(mff);
				rfaN1.setPadre(0L);
				rfaN1.setOrden(Long.parseLong(String.valueOf(index)));
				rfaN1.setTipoRubro(buscarTipoRubro(53L));
				rfaN1.setTipo(2L);
				servicioGeneral.guardarObjeto(rfaN1);
				System.out.println("Contrapartidas");
				for (TipoRubro rubro : rubrosContrapartidas) {
					index++;
					RubroFinanciableArbol rfaN2 = new RubroFinanciableArbol();
					rfaN2.setModalidadFuenteFinanciacion(mff);
					rfaN2.setPadre(rfaN1.getId());
					rfaN2.setOrden(Long.parseLong(String.valueOf(index)));
					rfaN2.setTipoRubro(rubro);
					rfaN2.setTipo(2L);
					servicioGeneral.guardarObjeto(rfaN2);
					System.out.println("\t" + rubro.getNombre());
				}
			}
		}
	}

	public void eliminarRubroFinanciable() {
		convocatoriaActual.getRubrosFinanciables().remove(rubroSeleccionado);
		if (rubroSeleccionado.getId() != null)
			servicioGeneral.eliminarObjeto(rubroSeleccionado);
		rubroSeleccionado = new RubroFinanciable();
	}

	private void cargarListaConvocatoriasPadre() {
		List listaIdEstados = new ArrayList();
		listaIdEstados.add(EstadoConvocatoria.ACTIVA);
		listaIdEstados.add(EstadoConvocatoria.CREACION);
		listaConvocatoriasPadre = servicioModalidad.obtenerConvocatoriasPadreEnEstados(listaIdEstados);
		ConvocatoriaPadre cp;
		int idMax = 0;
		for (int i = 0; i < listaConvocatoriasPadre.size(); i++) {

			cp = (ConvocatoriaPadre) listaConvocatoriasPadre.get(i);
			if (cp.getId().intValue() > idMax) {
				idMax = cp.getId().intValue();
			}
		}
		this.convocatoriaActual.getPadre().setId(new Long(idMax));

	}

	private void cargarListaEstadosConvocatoria() {

		listaEstadosConvocatoria = servicioGeneral.obtenerListaObjetos("EstadoConvocatoria");

	}

	private void cargarListaFuenteFinanaciacion() {
		/***********************************************************************
		 * idTipoFuenteFinanciacion = FuenteFinanciacion.externa;
		 * listaFuentesFinanciacion = servicioProyecto
		 * .obtenerFuenteFinanciacionExterna();
		 **********************************************************************/

		listaFuentesFinanciacion = new Vector();
		listaTipoFuenteFinanciacion = new Vector();
		listaTipoFuenteFinanciacion.add(new SelectItem(FuenteFinanciacion.externa, "Externa"));
		listaTipoFuenteFinanciacion.add(new SelectItem(FuenteFinanciacion.interna, "Interna"));
		idTipoFuenteFinanciacion = FuenteFinanciacion.externa;
		List listaFuenteFinanciacionAux = servicioProyecto.obtenerFuenteFinanciacionExterna();
		for (Iterator it = listaFuenteFinanciacionAux.iterator(); it.hasNext();) {
			FuenteFinanciacion ff = (FuenteFinanciacion) it.next();
			listaFuentesFinanciacion.add(new SelectItem(ff.getId(), ff.getDescripcion()));
		}

	}

	public void cambiaTipoFinanciacion() {

		List listaTipoFuenteFinanciacionAux = null;
		if (idTipoFuenteFinanciacion.equals(FuenteFinanciacion.externa)) {
			listaTipoFuenteFinanciacionAux = servicioProyecto.obtenerFuenteFinanciacionExterna();
		} else {
			listaTipoFuenteFinanciacionAux = servicioProyecto.obtenerFuenteFinanciacionInterna();
			listaTipoFuenteFinanciacionAux.add(new FuenteFinanciacion("1","UNIVERSIDAD NACIONAL DE COLOMBIA"));
		}
		listaFuentesFinanciacion = new Vector();
		for (Iterator it = listaTipoFuenteFinanciacionAux.iterator(); it.hasNext();) {
			FuenteFinanciacion ff = (FuenteFinanciacion) it.next();
			listaFuentesFinanciacion.add(new SelectItem(ff.getId(), ff.getDescripcion()));
		}

	}

	public void cambiarSede(ValueChangeEvent event) {
		// AL CAMBIAR UNA SEDE EN LA PAGINA WEB, SE CAMBIA IGUALMENTE LA LISTA
		// DE
		// DEPENDENCIAS A MOSTRAR
		sedeActual = BuscarSede((Long) (manejadorSedes.getValue()));
		listaDependencia = servicioGeneral.obtenerDependencias(sedeActual);
	}

	public String siguiente() {

		if (estadoIngreso == 1 && !validarCamposFormulario()) {
			return "";
		} else {
			if (estadoIngreso == 2 && !validarRequisitos()) {
				return "";
			} else {
				if (estadoIngreso == 3 && !validarRubros()) {
					return "";
				} else {
					if (estadoIngreso == 4 && !validarCompromisos()) {
						return "";
					} else {
						if (estadoIngreso == 5 && !validarProductos()) {
							return "";
						} else {
							if (estadoIngreso == 6 && !validarCriterios()) {
								return "";
							} else {
								if (estadoIngreso == 7 && !validarCamposInformacionEspecifica()) {
									return "";
								}
							}
						}
					}
				}
			}
		}

		if (estadoIngreso == 1 && validarCamposFormulario()) {
			requisito.setNombre(this.convocatoriaActual.getTitulo());
			requisito.setPadre(new TipoRequisito(new Long("0")));
			requisito.setDescripcion("convocatoria Padre de " + this.convocatoriaActual.getTitulo());
			requisito.setHijos(new ArrayList());
			compromiso.setNombre(this.convocatoriaActual.getTitulo());
			compromiso.setPadre(new TipoCompromiso(new Long("0")));
			compromiso.setDescripcion("convocatoria Padre de " + this.convocatoriaActual.getTitulo());
			compromiso.setHijos(new ArrayList());
		}

		this.estadoIngreso++;

		if (estadoIngreso == 2 && validarCamposFormulario()) {
			requisito.setNombre(this.convocatoriaActual.getTitulo());
			requisito.setPadre(new TipoRequisito(new Long("0")));
			requisito.setDescripcion("convocatoria Padre de " + this.convocatoriaActual.getTitulo());
			requisito.setHijos(new ArrayList());
		}

		if (estadoIngreso == 4 && validarCamposFormulario()) {
			compromiso.setNombre(this.convocatoriaActual.getTitulo());
			compromiso.setPadre(new TipoCompromiso(new Long("0")));
			compromiso.setDescripcion("convocatoria Padre de " + this.convocatoriaActual.getTitulo());
			compromiso.setHijos(new ArrayList());
		}

		esEdicion = (String) sesion.getAttribute("siEdicion");

		if (esEdicion.equals("S")) {
			guardarEdicionConvocatoria();
			this.actualizarIngreso(esConvocatoriaProyectos);
			if (estadoIngreso == 1) {
				return "irGeneralidadesConvocatoria";
			} else {
				if (estadoIngreso == 2) {
					return "irRequisitosConvocatoria";
				} else {
					if (estadoIngreso == 3) {
						return "irRubrosConvocatoria";
					} else {
						if (estadoIngreso == 4) {
							return "irCompromisosConvocatoria";
						} else {
							if (estadoIngreso == 5) {
								return "irProductosConvocatoria";
							} else {
								if (estadoIngreso == 6) {
									return "irCriteriosEvaluacionConvocatoria";
								} else {
									if (estadoIngreso == 7) {
										return "irDetallesInformacionEspecifica";
									}
								}
							}
						}
					}
				}
			}
		} else {
			guardarConvocatoria();
			this.actualizarIngreso(esConvocatoriaProyectos);
			if (estadoIngreso == 1) {
				return "irGeneralidadesConvocatoria";
			} else {
				if (estadoIngreso == 2) {
					return "irRequisitosConvocatoria";
				} else {
					if (estadoIngreso == 3) {
						return "irRubrosConvocatoria";
					} else {
						if (estadoIngreso == 4) {
							return "irCompromisosConvocatoria";
						} else {
							if (estadoIngreso == 5) {
								return "irProductosConvocatoria";
							} else {
								if (estadoIngreso == 6) {
									return "irCriteriosEvaluacionConvocatoria";
								} else {
									if (estadoIngreso == 7) {
										return "irDetallesInformacionEspecifica";
									}
								}
							}
						}
					}
				}
			}
		}

		if (estadoIngreso == 7) {
			this.activarGuardar = true;
			this.activarSiguiente = false;
			this.mostrarGuardarAvanzar = false;
			this.mostrarGuardarSalir = false;
		} else {
			this.mostrarGuardarAvanzar = true;
			this.mostrarGuardarSalir = true;
			this.activarGuardar = false;
		}

		return "";
	}

	public void validarTipoMovilidad() {
		if (idRestriccionMovilidad.equals("CF_MOV5")) {
			mostrarCamposMovilidadDocente = false;
			mostrarCamposMovilidadVisitante = false;
			mostrarCamposMovilidadEstPonencia = false;
			mostrarCamposMovilidadEstPasantia = true;
		}

		if (idRestriccionMovilidad.equals("CF_MOV6")) {
			mostrarCamposMovilidadDocente = false;
			mostrarCamposMovilidadVisitante = false;
			mostrarCamposMovilidadEstPonencia = true;
			mostrarCamposMovilidadEstPasantia = false;
		}

		if (idRestriccionMovilidad.equals("CF_MOV7")) {
			mostrarCamposMovilidadDocente = false;
			mostrarCamposMovilidadVisitante = true;
			mostrarCamposMovilidadEstPonencia = false;
			mostrarCamposMovilidadEstPasantia = false;
		}

		if (idRestriccionMovilidad.equals("CF_MOV8")) {
			mostrarCamposMovilidadDocente = true;
			mostrarCamposMovilidadVisitante = false;
			mostrarCamposMovilidadEstPonencia = false;
			mostrarCamposMovilidadEstPasantia = false;
		}

	}

	public String siguienteMovilidades() {

		if (estadoIngreso == 1 && !validarCamposFormularioMovilidad()) {
			return "";
		} else {
			if (estadoIngreso == 2 && !validarRequisitos()) {
				return "";
			} else {
				if (estadoIngreso == 3 && !validarArchivosMovilidad()) {
					return "";
				}
			}
		}

		if (estadoIngreso == 1 && validarCamposFormularioMovilidad()) {
			requisito.setNombre(this.convocatoriaActual.getTitulo());
			requisito.setPadre(new TipoRequisito(new Long("0")));
			requisito.setDescripcion("convocatoria Padre de " + this.convocatoriaActual.getTitulo());
			requisito.setHijos(new ArrayList());
		}

		this.estadoIngreso++;

		if (estadoIngreso == 2 && validarCamposFormularioMovilidad()) {
			requisito.setNombre(this.convocatoriaActual.getTitulo());
			requisito.setPadre(new TipoRequisito(new Long("0")));
			requisito.setDescripcion("convocatoria Padre de " + this.convocatoriaActual.getTitulo());
			requisito.setHijos(new ArrayList());
		}

		esEdicion = (String) sesion.getAttribute("siEdicion");

		if (esEdicion.equals("S")) {
			guardarConvocatoriaMovilidad();
			this.actualizarIngreso(!esConvocatoriaMovilidades);
			if (estadoIngreso == 1) {
				return "irGeneralidadesConvocatoria";
			} else {
				if (estadoIngreso == 2) {
					return "irRequisitosConvocatoria";
				} else {

					if (estadoIngreso == 3) {
						return "irArchivosConvocatoriaMovilidad";
					}
				}
			}
		} else {
			guardarConvocatoriaMovilidad();
			this.actualizarIngreso(!esConvocatoriaMovilidades);
			if (estadoIngreso == 1) {
				return "irGeneralidadesConvocatoria";
			} else {
				if (estadoIngreso == 2) {
					return "irRequisitosConvocatoria";
				} else {
					if (estadoIngreso == 3) {
						return "irArchivosConvocatoriaMovilidad";
					}
				}
			}
		}

		if (estadoIngreso == 3) {
			this.activarGuardar = true;
			this.activarSiguiente = false;
			this.mostrarGuardarAvanzar = false;
			this.mostrarGuardarSalir = false;
		} else {
			this.mostrarGuardarAvanzar = true;
			this.mostrarGuardarSalir = true;
			this.activarGuardar = false;
		}

		return "";

	}

	public String guardarEdicionConvocatoria() {
		this.limpiarAvisos();
		String resul = " ";
		try {

			if (estadoIngreso - 1 == 1) {
				if (validarCamposFormulario()) {
					// DARLE LA SEDE AL OBJETO CONVOCATORIA
					// dependenciaActual.setSede(sedeActual);
					convocatoriaActual.setEtapaCreacion(new Long("1"));

					boolean nuevaConvocatoria = convocatoriaActual.getId() == null;
					Sede sedeConvocatoria = new Sede();
					sedeConvocatoria.setId(new Long(idSede));
					convocatoriaActual.setSede(sedeConvocatoria);
					// DARLE LA DEPENDENCIA AL OBJETO CONVOCATORIA
					// convocatoriaActual.setDependencia(dependenciaActual);
					listaDependencia = servicioGeneral.obtenerDependencias(sedeConvocatoria);

					convocatoriaActual.setDependencia((Dependencia) listaDependencia.get(0));

					// DARLE EL ESTADO AL OBJETO CONVOCATORIA
					convocatoriaActual.setEstadoConvocatoria(estadoConvocatoria);

					// Se asigna el tipo de modalidad convocatoria
					convocatoriaActual.setTipo(tipoModalidadConvocatoria);
					/***************************************************************
					 * Long ide = new Long(convocatoria);
					 * convocatoriaActual.setId(ide); ConvocatoriaPadre conv =
					 * new ConvocatoriaPadre();
					 * conv.setId(convocatoriaActual.getPadre().getId());
					 * convocatoriaActual.setPadre(conv);
					 **************************************************************/

					// Se asignan las darle opciones cuando la convocatoria es
					// para
					// grupos
					convocatoriaActual.setGruposRegistrados(new Boolean(false));
					convocatoriaActual.setGruposReconocidos(new Boolean(false));
					convocatoriaActual.setGruposCategoriaA(new Boolean(false));
					convocatoriaActual.setGruposCategoriaB(new Boolean(false));
					convocatoriaActual.setGruposCategoriaC(new Boolean(false));
					if (esParaGrupos.equals("S")) {
						convocatoriaActual.setEsParaGrupos(new Boolean(true));
						for (int i = 0; i < listaTipoGrupos.length; i++) {
							if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.ACTIVO))
								convocatoriaActual.setGruposRegistrados(new Boolean(true));
							else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.INGRESANDO))
								convocatoriaActual.setGruposReconocidos(new Boolean(true));
							else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.SOLICITUD_AVAL))
								convocatoriaActual.setGruposCategoriaA(new Boolean(true));
							else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.DEVUELTO_CORRECCION))
								convocatoriaActual.setGruposCategoriaB(new Boolean(true));
							else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.REGISTRADO))
								convocatoriaActual.setGruposCategoriaC(new Boolean(true));
						}

						if (registroProyectoLiderGrupo.equals("S")) {
							convocatoriaActual.setGruposCategoriaD(new Boolean(true));
						}
					} else {
						convocatoriaActual.setEsParaGrupos(new Boolean(false));
					}

					if (mostrarIntegrantesSinDatos.equals("S")) {
						convocatoriaActual.setMostrarIntegrantesSinDatos(new Boolean(true));
					} else {
						convocatoriaActual.setMostrarIntegrantesSinDatos(new Boolean(false));
					}

					if (mostarActividadesInvestigador.equals("S")) {
						convocatoriaActual.setMostrarActividadesInvestigador(new Boolean(true));
					} else {
						convocatoriaActual.setMostrarActividadesInvestigador(new Boolean(false));
					}

					if (mostrarEvaluacionesIndividuales.equals("S")) {
						convocatoriaActual.setMostrarEvaluacionesIndividuales(new Boolean(true));
					} else {
						convocatoriaActual.setMostrarEvaluacionesIndividuales(new Boolean(false));
					}

					if (mostrarProductos.equals("S")) {
						convocatoriaActual.setMostrarProductosAcademicos(new Boolean(true));
					} else {
						convocatoriaActual.setMostrarProductosAcademicos(new Boolean(false));
					}

					if (mostrarBiodiversidad.equals("S")) {
						convocatoriaActual.setConMostrarBiodiversidad(1L);
					} else {
						convocatoriaActual.setConMostrarBiodiversidad(0L);
					}

					if (mostarActivdadesIntegrantesSinDatos.equals("S")) {
						convocatoriaActual.setMostrarActividadesInvestigadorNoConocido(new Boolean(false));
					} else {
						convocatoriaActual.setMostrarActividadesInvestigadorNoConocido(new Boolean(false));
					}

					// Si la convocatoria tiene reestriccion de dependencia
					if (esParaSedes.equals("S")) {
						convocatoriaActual.setDependenciaRestriccion(dependenciaActualRestriccion);
					} else {
						convocatoriaActual.setDependenciaRestriccion(null);
					}

					servicioModalidad.guardarConvocatoria(convocatoriaActual);

					for (Iterator itModalidadFuenteFinanciacion = listaModalidadFuenteFinanciacion.iterator(); itModalidadFuenteFinanciacion.hasNext();) {
						ModalidadFuenteFinanciacion mff = (ModalidadFuenteFinanciacion) itModalidadFuenteFinanciacion.next();
						mff.setModalidad(convocatoriaActual);

						if (mff.isBorrar() && mff.getId() != null) {
							servicioGeneral.eliminarObjeto(mff);
							listaModalidadFuenteFinanciacion.remove(mff);
						}
						if (mff.getId() != null && !mff.isBorrar()) {
							servicioGeneral.guardarObjeto(mff);
						}
						if (mff.getId() == null && !mff.isBorrar()) {
							servicioGeneral.insertarObjeto(mff);
						}

					}

				}
			}

			if (estadoIngreso - 1 == 2) {
				if (validarRequisitos()) {
					servicioGeneral.insertarObjeto(requisito);
					convocatoriaActual.setEtapaCreacion(new Long("2"));

					for (Iterator iteratorReq = requisito.getHijos().iterator(); iteratorReq.hasNext();) {
						TipoRequisito req = (TipoRequisito) iteratorReq.next();
						servicioGeneral.insertarObjeto(req);
					}
				}
				servicioModalidad.guardarConvocatoria(convocatoriaActual);
			}

			if (estadoIngreso - 1 == 3) {
				if (validarRubros()) {
					convocatoriaActual.setEtapaCreacion(new Long("3"));

					Set rf = servicioModalidad.actualizarPorcentajesRubrosFinanciables(convocatoriaActual);
					if (rf == null) {
						avisoRubros = "hay porcentajes que son mayores al 100%";
						return "";
					}
					convocatoriaActual.setRubrosFinanciables(rf);

					servicioModalidad.guardarConvocatoria(convocatoriaActual);

				}
			}
			if (estadoIngreso - 1 == 4) {
				if (validarCompromisos()) {
					convocatoriaActual.setEtapaCreacion(new Long("4"));

					servicioGeneral.insertarObjeto(compromiso);

					for (Iterator iteratorCom = compromiso.getHijos().iterator(); iteratorCom.hasNext();) {
						TipoCompromiso com = (TipoCompromiso) iteratorCom.next();
						servicioGeneral.insertarObjeto(com);
					}
					servicioModalidad.guardarConvocatoria(convocatoriaActual);
				}

			}

			if (estadoIngreso - 1 == 5) {
				if (validarProductos()) {
					convocatoriaActual.setEtapaCreacion(new Long("5"));
					servicioModalidad.guardarConvocatoria(convocatoriaActual);

				}
			}

			if (estadoIngreso - 1 == 6) {
				if (validarCriterios()) {
					convocatoriaActual.setEtapaCreacion(new Long("6"));

					// carga los criterios seleccionados
					List listaAux = new Vector();
					for (Iterator itCriteriosSeleccionados = listaCriteriosSeleccionados.iterator(); itCriteriosSeleccionados.hasNext();) {
						String idCriterioSeleccionado = (String) itCriteriosSeleccionados.next();
						listaAux.add(servicioGeneral.obtenerObjeto(new CriterioEvaluacion(), new Long(idCriterioSeleccionado)));
					}

					List l = new Vector();
					if (convocatoriaActual.getId() != null) {

						for (Iterator i = listaCriteriosTipoPregunta.iterator(); i.hasNext();) {
							VistaModalidadCriterioTipoPregunta v = (VistaModalidadCriterioTipoPregunta) i.next();

							List criterioS = v.getCriterioSeleccionado();
							if (criterioS != null && criterioS.size() > 0) {
								String idC = (String) criterioS.get(0);
								Long idCriterio = new Long(idC);
								CriterioEvaluacion ce = (CriterioEvaluacion) servicioGeneral.obtenerObjeto(new CriterioEvaluacion(), idCriterio);
								Long idTipoPregunta = v.getIdTipoPregunta();
								TipoPregunta tp = (TipoPregunta) servicioGeneral.obtenerObjeto(new TipoPregunta(), idTipoPregunta);
								if (servicioModalidad.modalidadContieneCriterio(convocatoriaActual, idCriterio)) {

									ModalidadCriterioTipoPregunta mctp = servicioEvaluacion.obtenerModalidadTipoPreguntaXModalidadYCriterio(convocatoriaActual.getId(), idCriterio);
									mctp.setCriterio(ce);
									mctp.setTipoPregunta(tp);
									l.add(mctp);
								} else {
									ModalidadCriterioTipoPregunta mctp = new ModalidadCriterioTipoPregunta();
									mctp.setTipoPregunta(tp);
									mctp.setCriterio(ce);
									mctp.setModalidad(convocatoriaActual);
									l.add(mctp);
								}

							}
						}
						convocatoriaActual.setCriteriosTipoPregunta(new HashSet(l));
					} else {
						for (Iterator i = listaCriteriosTipoPregunta.iterator(); i.hasNext();) {
							VistaModalidadCriterioTipoPregunta v = (VistaModalidadCriterioTipoPregunta) i.next();

							List criterioS = v.getCriterioSeleccionado();
							if (criterioS != null && criterioS.size() > 0) {
								String idC = (String) criterioS.get(0);
								Long idCriterio = new Long(idC);
								CriterioEvaluacion ce = (CriterioEvaluacion) servicioGeneral.obtenerObjeto(new CriterioEvaluacion(), idCriterio);
								Long idTipoPregunta = v.getIdTipoPregunta();
								TipoPregunta tp = (TipoPregunta) servicioGeneral.obtenerObjeto(new TipoPregunta(), idTipoPregunta);

								ModalidadCriterioTipoPregunta mctp = new ModalidadCriterioTipoPregunta();
								mctp.setTipoPregunta(tp);
								mctp.setCriterio(ce);
								mctp.setModalidad(convocatoriaActual);
								l.add(mctp);

							}
						}
						convocatoriaActual.setCriteriosTipoPregunta(new HashSet(l));
					}
					servicioModalidad.guardarConvocatoria(convocatoriaActual);
				}
			}

			if (estadoIngreso - 1 == 7) {

				String hqlFormEsp = "select e from FormularioInformacionEspecifica e where e.modalidad.id = " + convocatoriaActual.getId();
				List<FormularioInformacionEspecifica> listaFormInfoEsp = servicioGeneral.obtenerObjetos(FormularioInformacionEspecifica.class, hqlFormEsp);
				if (listaFormInfoEsp != null && listaFormInfoEsp.size() > 0) {
					FormularioInformacionEspecifica fie = listaFormInfoEsp.get(0);
					if (mostrarCampoUno.equals("S")) {
						fie.setCampoUno(true);
						fie.setCampoUnoTexto(textoCampoUno);
						fie.setCampoUnoObligatorio(campoUnoObligatorio);
					} else {
						fie.setCampoUno(false);
					}

					if (mostrarCampoDos.equals("S")) {
						fie.setCampoDos(true);
						fie.setCampoDosTexto(textoCampoDos);
						fie.setCampoDosObligatorio(campoDosObligatorio);
					} else {
						fie.setCampoDos(false);
					}

					if (mostrarCampoTres.equals("S")) {
						fie.setCampoTres(true);
						fie.setCampoTresTexto(textoCampoTres);
						fie.setCampoTresObligatorio(campoTresObligatorio);
					} else {
						fie.setCampoTres(false);
					}

					if (mostrarCampoCuatro.equals("S")) {
						fie.setCampoCuatro(true);
						fie.setCampoCuatroTexto(textoCampoCuatro);
						fie.setCampoCuatroObligatorio(campoCuatroObligatorio);
					} else {
						fie.setCampoCuatro(false);
					}

					if (mostrarCampoCinco.equals("S")) {
						fie.setCampoCinco(true);
						fie.setCampoCincoTexto(textoCampoCinco);
						fie.setCampoCincoObligatorio(campoCincoObligatorio);
					} else {
						fie.setCampoCinco(false);
					}

					if (mostrarCampoSeis.equals("S")) {
						fie.setCampoSeis(true);
						fie.setCampoSeisTexto(textoCampoSeis);
						fie.setCampoSeisObligatorio(campoSeisObligatorio);
					} else {
						fie.setCampoSeis(false);
					}

					if (mostrarCampoAsignaturas.equals("S")) {
						fie.setAsignatura(true);
					} else {
						fie.setAsignatura(false);
					}

					if (mostrarCampoObjetivosResponsable.equals("S")) {
						fie.setObjetivosResutadosResponsables(true);
					} else {
						fie.setObjetivosResutadosResponsables(false);
					}

					if (mostrarCampoGrupos.equals("S")) {
						fie.setGrupos(true);
					} else {
						fie.setGrupos(false);
					}

					if (mostrarLugarEjecucion.equals("S")) {
						fie.setLugarEjecucion(true);
					} else {
						fie.setLugarEjecucion(false);
					}
					servicioGeneral.guardarObjeto(fie);

				} else {
					FormularioInformacionEspecifica fie = new FormularioInformacionEspecifica();
					fie.setModalidad(convocatoriaActual);
					if (mostrarCampoUno.equals("S")) {
						fie.setCampoUno(true);
						fie.setCampoUnoTexto(textoCampoUno);
						fie.setCampoUnoObligatorio(campoUnoObligatorio);
					} else {
						fie.setCampoUno(false);
					}

					if (mostrarCampoDos.equals("S")) {
						fie.setCampoDos(true);
						fie.setCampoDosTexto(textoCampoDos);
						fie.setCampoDosObligatorio(campoDosObligatorio);
					} else {
						fie.setCampoDos(false);
					}

					if (mostrarCampoTres.equals("S")) {
						fie.setCampoTres(true);
						fie.setCampoTresTexto(textoCampoTres);
						fie.setCampoTresObligatorio(campoTresObligatorio);
					} else {
						fie.setCampoTres(false);
					}

					if (mostrarCampoCuatro.equals("S")) {
						fie.setCampoCuatro(true);
						fie.setCampoCuatroTexto(textoCampoCuatro);
						fie.setCampoCuatroObligatorio(campoCuatroObligatorio);
					} else {
						fie.setCampoCuatro(false);
					}

					if (mostrarCampoCinco.equals("S")) {
						fie.setCampoCinco(true);
						fie.setCampoCincoTexto(textoCampoCinco);
						fie.setCampoCincoObligatorio(campoCincoObligatorio);
					} else {
						fie.setCampoCinco(false);
					}

					if (mostrarCampoSeis.equals("S")) {
						fie.setCampoSeis(true);
						fie.setCampoSeisTexto(textoCampoSeis);
						fie.setCampoSeisObligatorio(campoSeisObligatorio);
					} else {
						fie.setCampoSeis(false);
					}

					if (mostrarCampoAsignaturas.equals("S")) {
						fie.setAsignatura(true);
					} else {
						fie.setAsignatura(false);
					}

					if (mostrarCampoObjetivosResponsable.equals("S")) {
						fie.setObjetivosResutadosResponsables(true);
					} else {
						fie.setObjetivosResutadosResponsables(false);
					}

					if (mostrarCampoGrupos.equals("S")) {
						fie.setGrupos(true);
					} else {
						fie.setGrupos(false);
					}

					if (mostrarLugarEjecucion.equals("S")) {
						fie.setLugarEjecucion(true);
					} else {
						fie.setLugarEjecucion(false);
					}
					servicioGeneral.guardarObjeto(fie);
				}

				this.guardarConvocatoria();
				sesion.removeAttribute("manejadorInsercionConvocatorias");
				sesion.removeAttribute("manejadorConsultaConvocatorias");
				sesion.removeAttribute("convocatoriaEdicion");
				resul = "consultarConvocatoria";
			}

		} catch (Exception e) {
			// AL FALLAR LA INSERCION DE LA CONVOCATORIA SE DEBE MOSTRAR LA
			// RAZON POR LA CUAL LA INSERCION DE LA
			// CONVOCATORIA FALLA Y MANTENER LA REFERENCIA AL OBJETO ACTUAL
			// INTACTA
			e.printStackTrace();
			resul = "failled";
		}

		return resul;

	}

	public String guardarConvocatoria() {
		this.limpiarAvisos();
		String resul = " ";
		try {

			if (estadoIngreso - 1 == 1) {
				if (validarCamposFormulario()) {
					// DARLE LA SEDE AL OBJETO CONVOCATORIA
					// dependenciaActual.setSede(sedeActual);
					convocatoriaActual.setEtapaCreacion(new Long("1"));

					boolean nuevaConvocatoria = convocatoriaActual.getId() == null;
					Sede sedeConvocatoria = new Sede();
					sedeConvocatoria.setId(new Long(idSede));
					convocatoriaActual.setSede(sedeConvocatoria);
					// DARLE LA DEPENDENCIA AL OBJETO CONVOCATORIA
					// convocatoriaActual.setDependencia(dependenciaActual);
					listaDependencia = servicioGeneral.obtenerDependencias(sedeConvocatoria);

					convocatoriaActual.setDependencia((Dependencia) listaDependencia.get(0));

					// DARLE EL ESTADO AL OBJETO CONVOCATORIA
					convocatoriaActual.setEstadoConvocatoria(estadoConvocatoria);

					// Se asigna el tipo de modalidad convocatoria
					convocatoriaActual.setTipo(tipoModalidadConvocatoria);
					/***************************************************************
					 * Long ide = new Long(convocatoria);
					 * convocatoriaActual.setId(ide); ConvocatoriaPadre conv =
					 * new ConvocatoriaPadre();
					 * conv.setId(convocatoriaActual.getPadre().getId());
					 * convocatoriaActual.setPadre(conv);
					 **************************************************************/

					// Se asignan lasdarle opciones cuando la convocatoria es
					// para
					// grupos
					convocatoriaActual.setGruposRegistrados(new Boolean(false));
					convocatoriaActual.setGruposReconocidos(new Boolean(false));
					convocatoriaActual.setGruposCategoriaA(new Boolean(false));
					convocatoriaActual.setGruposCategoriaB(new Boolean(false));
					convocatoriaActual.setGruposCategoriaC(new Boolean(false));
					if (esParaGrupos.equals("S")) {
						convocatoriaActual.setEsParaGrupos(new Boolean(true));

						for (int i = 0; i < listaTipoGrupos.length; i++) {
							if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.ACTIVO))
								convocatoriaActual.setGruposRegistrados(new Boolean(true));
							else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.INGRESANDO))
								convocatoriaActual.setGruposReconocidos(new Boolean(true));
							else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.SOLICITUD_AVAL))
								convocatoriaActual.setGruposCategoriaA(new Boolean(true));
							else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.DEVUELTO_CORRECCION))
								convocatoriaActual.setGruposCategoriaB(new Boolean(true));
							else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.REGISTRADO))
								convocatoriaActual.setGruposCategoriaC(new Boolean(true));
						}

						if (registroProyectoLiderGrupo.equals("S")) {
							convocatoriaActual.setGruposCategoriaD(new Boolean(true));
						}
					} else {
						convocatoriaActual.setEsParaGrupos(new Boolean(false));
					}
					
					if (mostrarIntegrantesSinDatos.equals("S")) {
						convocatoriaActual.setMostrarIntegrantesSinDatos(new Boolean(true));
					} else {
						convocatoriaActual.setMostrarIntegrantesSinDatos(new Boolean(false));
					}

					if (mostarActividadesInvestigador.equals("S")) {
						convocatoriaActual.setMostrarActividadesInvestigador(new Boolean(true));
					} else {
						convocatoriaActual.setMostrarActividadesInvestigador(new Boolean(false));
					}

					if (mostrarEvaluacionesIndividuales.equals("S")) {
						convocatoriaActual.setMostrarEvaluacionesIndividuales(new Boolean(true));
					} else {
						convocatoriaActual.setMostrarEvaluacionesIndividuales(new Boolean(false));
					}

					if (mostrarProductos.equals("S")) {
						convocatoriaActual.setMostrarProductosAcademicos(new Boolean(true));
					} else {
						convocatoriaActual.setMostrarProductosAcademicos(new Boolean(false));
					}

					if (mostrarBiodiversidad.equals("S")) {
						convocatoriaActual.setConMostrarBiodiversidad(1L);
					} else {
						convocatoriaActual.setConMostrarBiodiversidad(0L);
					}

					if (mostarActivdadesIntegrantesSinDatos.equals("S")) {
						convocatoriaActual.setMostrarActividadesInvestigadorNoConocido(new Boolean(false));
					} else {
						convocatoriaActual.setMostrarActividadesInvestigadorNoConocido(new Boolean(false));
					}

					// Si la convocatoria tiene reestriccion de dependencia
					if (esParaSedes.equals("S")) {
						convocatoriaActual.setDependenciaRestriccion(dependenciaActualRestriccion);
					} else {
						convocatoriaActual.setDependenciaRestriccion(null);
					}

					servicioModalidad.guardarConvocatoria(convocatoriaActual);

					for (Iterator itModalidadFuenteFinanciacion = listaModalidadFuenteFinanciacion.iterator(); itModalidadFuenteFinanciacion.hasNext();) {
						ModalidadFuenteFinanciacion mff = (ModalidadFuenteFinanciacion) itModalidadFuenteFinanciacion.next();
						mff.setModalidad(convocatoriaActual);

						if (mff.isBorrar() && mff.getId() != null) {
							servicioGeneral.eliminarObjeto(mff);
							listaModalidadFuenteFinanciacion.remove(mff);
						}
						if (mff.getId() != null && !mff.isBorrar()) {
							servicioGeneral.guardarObjeto(mff);
						}
						if (mff.getId() == null && !mff.isBorrar()) {
							servicioGeneral.insertarObjeto(mff);
						}

					}

				}
			}

			if (estadoIngreso - 1 == 2) {
				if (validarRequisitos()) {
					servicioGeneral.insertarObjeto(requisito);
					convocatoriaActual.setEtapaCreacion(new Long("2"));

					for (Iterator iteratorReq = requisito.getHijos().iterator(); iteratorReq.hasNext();) {
						TipoRequisito req = (TipoRequisito) iteratorReq.next();
						servicioGeneral.insertarObjeto(req);
					}
				}
				servicioModalidad.guardarConvocatoria(convocatoriaActual);
			}

			if (estadoIngreso - 1 == 3) {
				if (validarRubros()) {
					convocatoriaActual.setEtapaCreacion(new Long("3"));

					Set rf = servicioModalidad.actualizarPorcentajesRubrosFinanciables(convocatoriaActual);
					if (rf == null) {
						avisoRubros = "hay porcentajes que son mayores al 100%";
						return "";
					}
					convocatoriaActual.setRubrosFinanciables(rf);

					servicioModalidad.guardarConvocatoria(convocatoriaActual);
				}
			}
			if (estadoIngreso - 1 == 4) {
				if (validarCompromisos()) {
					convocatoriaActual.setEtapaCreacion(new Long("4"));
					// servicioModalidad.guardarConvocatoria(convocatoriaActual);

					servicioGeneral.insertarObjeto(compromiso);

					for (Iterator iteratorCom = compromiso.getHijos().iterator(); iteratorCom.hasNext();) {
						TipoCompromiso com = (TipoCompromiso) iteratorCom.next();
						servicioGeneral.insertarObjeto(com);
					}
					servicioModalidad.guardarConvocatoria(convocatoriaActual);
				}

			}

			if (estadoIngreso - 1 == 5) {
				if (validarProductos()) {
					convocatoriaActual.setEtapaCreacion(new Long("5"));
					servicioModalidad.guardarConvocatoria(convocatoriaActual);

				}
			}

			if (estadoIngreso - 1 == 6) {
				if (validarCriterios()) {
					convocatoriaActual.setEtapaCreacion(new Long("6"));

					// carga los criterios seleccionados
					List listaAux = new Vector();
					for (Iterator itCriteriosSeleccionados = listaCriteriosSeleccionados.iterator(); itCriteriosSeleccionados.hasNext();) {
						String idCriterioSeleccionado = (String) itCriteriosSeleccionados.next();
						listaAux.add(servicioGeneral.obtenerObjeto(new CriterioEvaluacion(), new Long(idCriterioSeleccionado)));
					}

					List l = new Vector();
					if (convocatoriaActual.getId() != null) {

						for (Iterator i = listaCriteriosTipoPregunta.iterator(); i.hasNext();) {
							VistaModalidadCriterioTipoPregunta v = (VistaModalidadCriterioTipoPregunta) i.next();

							List criterioS = v.getCriterioSeleccionado();
							if (criterioS != null && criterioS.size() > 0) {
								String idC = (String) criterioS.get(0);
								Long idCriterio = new Long(idC);
								CriterioEvaluacion ce = (CriterioEvaluacion) servicioGeneral.obtenerObjeto(new CriterioEvaluacion(), idCriterio);
								Long idTipoPregunta = v.getIdTipoPregunta();
								TipoPregunta tp = (TipoPregunta) servicioGeneral.obtenerObjeto(new TipoPregunta(), idTipoPregunta);
								if (servicioModalidad.modalidadContieneCriterio(convocatoriaActual, idCriterio)) {

									ModalidadCriterioTipoPregunta mctp = servicioEvaluacion.obtenerModalidadTipoPreguntaXModalidadYCriterio(convocatoriaActual.getId(), idCriterio);
									mctp.setCriterio(ce);
									mctp.setTipoPregunta(tp);
									l.add(mctp);
								} else {
									ModalidadCriterioTipoPregunta mctp = new ModalidadCriterioTipoPregunta();
									mctp.setTipoPregunta(tp);
									mctp.setCriterio(ce);
									mctp.setModalidad(convocatoriaActual);
									l.add(mctp);
								}

							}
						}
						convocatoriaActual.setCriteriosTipoPregunta(new HashSet(l));
					} else {
						for (Iterator i = listaCriteriosTipoPregunta.iterator(); i.hasNext();) {
							VistaModalidadCriterioTipoPregunta v = (VistaModalidadCriterioTipoPregunta) i.next();

							List criterioS = v.getCriterioSeleccionado();
							if (criterioS != null && criterioS.size() > 0) {
								String idC = (String) criterioS.get(0);
								Long idCriterio = new Long(idC);
								CriterioEvaluacion ce = (CriterioEvaluacion) servicioGeneral.obtenerObjeto(new CriterioEvaluacion(), idCriterio);
								Long idTipoPregunta = v.getIdTipoPregunta();
								TipoPregunta tp = (TipoPregunta) servicioGeneral.obtenerObjeto(new TipoPregunta(), idTipoPregunta);

								ModalidadCriterioTipoPregunta mctp = new ModalidadCriterioTipoPregunta();
								mctp.setTipoPregunta(tp);
								mctp.setCriterio(ce);
								mctp.setModalidad(convocatoriaActual);
								l.add(mctp);

							}
						}
						convocatoriaActual.setCriteriosTipoPregunta(new HashSet(l));
					}
					servicioModalidad.guardarConvocatoria(convocatoriaActual);
				}
			}

			if (estadoIngreso - 1 == 7) {
				convocatoriaActual.setEtapaCreacion(new Long("7"));

				String hqlFormEsp = "select e from FormularioInformacionEspecifica e where e.modalidad.id = " + convocatoriaActual.getId();
				List<FormularioInformacionEspecifica> listaFormInfoEsp = servicioGeneral.obtenerObjetos(FormularioInformacionEspecifica.class, hqlFormEsp);
				if (listaFormInfoEsp != null && listaFormInfoEsp.size() > 0) {
					FormularioInformacionEspecifica fie = listaFormInfoEsp.get(0);
					if (mostrarCampoUno.equals("S")) {
						fie.setCampoUno(true);
						fie.setCampoUnoTexto(textoCampoUno);
						fie.setCampoUnoObligatorio(campoUnoObligatorio);
					} else {
						fie.setCampoUno(false);
					}

					if (mostrarCampoDos.equals("S")) {
						fie.setCampoDos(true);
						fie.setCampoDosTexto(textoCampoDos);
						fie.setCampoDosObligatorio(campoDosObligatorio);
					} else {
						fie.setCampoDos(false);
					}

					if (mostrarCampoTres.equals("S")) {
						fie.setCampoTres(true);
						fie.setCampoTresTexto(textoCampoTres);
						fie.setCampoTresObligatorio(campoTresObligatorio);
					} else {
						fie.setCampoTres(false);
					}

					if (mostrarCampoCuatro.equals("S")) {
						fie.setCampoCuatro(true);
						fie.setCampoCuatroTexto(textoCampoCuatro);
						fie.setCampoCuatroObligatorio(campoCuatroObligatorio);
					} else {
						fie.setCampoCuatro(false);
					}

					if (mostrarCampoCinco.equals("S")) {
						fie.setCampoCinco(true);
						fie.setCampoCincoTexto(textoCampoCinco);
						fie.setCampoCincoObligatorio(campoCincoObligatorio);
					} else {
						fie.setCampoCinco(false);
					}

					if (mostrarCampoSeis.equals("S")) {
						fie.setCampoSeis(true);
						fie.setCampoSeisTexto(textoCampoSeis);
						fie.setCampoSeisObligatorio(campoSeisObligatorio);
					} else {
						fie.setCampoSeis(false);
					}

					if (mostrarCampoAsignaturas.equals("S")) {
						fie.setAsignatura(true);
					} else {
						fie.setAsignatura(false);
					}

					if (mostrarCampoObjetivosResponsable.equals("S")) {
						fie.setObjetivosResutadosResponsables(true);
					} else {
						fie.setObjetivosResutadosResponsables(false);
					}

					if (mostrarCampoGrupos.equals("S")) {
						fie.setGrupos(true);
					} else {
						fie.setGrupos(false);
					}

					if (mostrarLugarEjecucion.equals("S")) {
						fie.setLugarEjecucion(true);
					} else {
						fie.setLugarEjecucion(false);
					}
					servicioGeneral.guardarObjeto(fie);

				} else {
					FormularioInformacionEspecifica fie = new FormularioInformacionEspecifica();
					fie.setModalidad(convocatoriaActual);
					if (mostrarCampoUno.equals("S")) {
						fie.setCampoUno(true);
						fie.setCampoUnoTexto(textoCampoUno);
						fie.setCampoUnoObligatorio(campoUnoObligatorio);
					} else {
						fie.setCampoUno(false);
					}

					if (mostrarCampoDos.equals("S")) {
						fie.setCampoDos(true);
						fie.setCampoDosTexto(textoCampoDos);
						fie.setCampoDosObligatorio(campoDosObligatorio);
					} else {
						fie.setCampoDos(false);
					}

					if (mostrarCampoTres.equals("S")) {
						fie.setCampoTres(true);
						fie.setCampoTresTexto(textoCampoTres);
						fie.setCampoTresObligatorio(campoTresObligatorio);
					} else {
						fie.setCampoTres(false);
					}

					if (mostrarCampoCuatro.equals("S")) {
						fie.setCampoCuatro(true);
						fie.setCampoCuatroTexto(textoCampoCuatro);
						fie.setCampoCuatroObligatorio(campoCuatroObligatorio);
					} else {
						fie.setCampoCuatro(false);
					}

					if (mostrarCampoCinco.equals("S")) {
						fie.setCampoCinco(true);
						fie.setCampoCincoTexto(textoCampoCinco);
						fie.setCampoCincoObligatorio(campoCincoObligatorio);
					} else {
						fie.setCampoCinco(false);
					}

					if (mostrarCampoSeis.equals("S")) {
						fie.setCampoSeis(true);
						fie.setCampoSeisTexto(textoCampoSeis);
						fie.setCampoSeisObligatorio(campoSeisObligatorio);
					} else {
						fie.setCampoSeis(false);
					}

					if (mostrarCampoAsignaturas.equals("S")) {
						fie.setAsignatura(true);
					} else {
						fie.setAsignatura(false);
					}

					if (mostrarCampoObjetivosResponsable.equals("S")) {
						fie.setObjetivosResutadosResponsables(true);
					} else {
						fie.setObjetivosResutadosResponsables(false);
					}

					if (mostrarCampoGrupos.equals("S")) {
						fie.setGrupos(true);
					} else {
						fie.setGrupos(false);
					}

					if (mostrarLugarEjecucion.equals("S")) {
						fie.setLugarEjecucion(true);
					} else {
						fie.setLugarEjecucion(false);
					}
					servicioGeneral.guardarObjeto(fie);
				}

				servicioModalidad.guardarConvocatoria(convocatoriaActual);
				//this.guardarConvocatoria();
				sesion.removeAttribute("manejadorInsercionConvocatorias");
				sesion.removeAttribute("manejadorConsultaConvocatorias");
				sesion.removeAttribute("convocatoriaEdicion");
				resul = "consultarConvocatoria";
			}

		} catch (Exception e) {
			// AL FALLAR LA INSERCION DE LA CONVOCATORIA SE DEBE MOSTRAR LA
			// RAZON POR LA CUAL LA INSERCION DE LA
			// CONVOCATORIA FALLA Y MANTENER LA REFERENCIA AL OBJETO ACTUAL
			// INTACTA
			e.printStackTrace();
			resul = "failled";
		}

		return resul;

	}

	public String guardarConvocatoriaMovilidad() {
		this.limpiarAvisos();
		String resul = " ";
		try {

			if (estadoIngreso - 1 == 1) {
				if (validarCamposFormularioMovilidad()) {
					convocatoriaActual.setEtapaCreacion(new Long("1"));

					boolean nuevaConvocatoria = convocatoriaActual.getId() == null;
					Sede sedeConvocatoria = new Sede();
					sedeConvocatoria.setId(new Long(idSede));
					convocatoriaActual.setSede(sedeConvocatoria);

					listaDependencia = servicioGeneral.obtenerDependencias(sedeConvocatoria);

					convocatoriaActual.setDependencia((Dependencia) listaDependencia.get(0));

					convocatoriaActual.setEstadoConvocatoria(estadoConvocatoria);
					convocatoriaActual.setMontonMinimo(0L);
					convocatoriaActual.setTipo(tipoModalidadConvocatoria);
					convocatoriaActual.setRestriccion(new RestriccionConvocatoria(idRestriccionMovilidad));
					convocatoriaActual.setCriteriosCalificacionConv("CONFIGURADO_SISTEMA");

					if (esParaSedes.equals("S")) {
						convocatoriaActual.setDependenciaRestriccion(dependenciaActualRestriccion);
					} else {
						convocatoriaActual.setDependenciaRestriccion(null);
					}

					servicioModalidad.guardarConvocatoria(convocatoriaActual);

				}
			}

			if (estadoIngreso - 1 == 2) {
				if (validarRequisitos()) {
					servicioGeneral.insertarObjeto(requisito);
					convocatoriaActual.setEtapaCreacion(new Long("2"));

					for (Iterator iteratorReq = requisito.getHijos().iterator(); iteratorReq.hasNext();) {
						TipoRequisito req = (TipoRequisito) iteratorReq.next();
						servicioGeneral.insertarObjeto(req);
					}
				}
				servicioModalidad.guardarConvocatoria(convocatoriaActual);
			}

			if (estadoIngreso - 1 == 3) {
				if (validarArchivosMovilidad()) {
					TipoMovilidad tm = new TipoMovilidad();
					tm.setId("CF_" + convocatoriaActual.getId());
					tm.setNombre(convocatoriaActual.getTitulo());
					if (idRestriccionMovilidad.equals("CF_MOV5")) {
						tm.setTabla("HER_MOVILIDAD_ESTUDIANTE_POS");
					}

					if (idRestriccionMovilidad.equals("CF_MOV6")) {
						tm.setTabla("HER_MOVILIDAD_ESTUDIANTE_POS");
					}

					if (idRestriccionMovilidad.equals("CF_MOV7")) {
						tm.setTabla("HER_MOVILIDAD_VISITANTES_EXT");
					}

					if (idRestriccionMovilidad.equals("CF_MOV8")) {
						tm.setTabla("HER_MOVILIDAD_DOCENTES_EVENTOS");
					}

					servicioGeneral.insertarObjeto(tm);
					convocatoriaActual.setEtapaCreacion(new Long("3"));

					convocatoriaActual.setRequisitosConvTexto(tm.getId());

					servicioModalidad.guardarConvocatoria(convocatoriaActual);

					for (int i = 0; i < listaMovilidadArchivo.size(); i++) {
						MovilidadArchivo ma = listaMovilidadArchivo.get(i);
						ma.setTipoMovilidad(tm);
						servicioGeneral.guardarObjeto(ma);
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			resul = "failled";
		}

		return resul;

	}

	public void agregarMovilidadArchivo() {
		MovilidadArchivo ma = new MovilidadArchivo();
		ma.setEsObligatorio(esArchivoObligatorioMovilidad);
		String hqlTipoArchivoMovilidad = "select e from TipoArchivoMovilidad e where e.id = " + idTipoMovilidadArchivo;
		List listaTipoArchivosMovSel = servicioGeneral.obtenerObjetos(hqlTipoArchivoMovilidad);
		if (listaTipoArchivosMovSel != null) {
			if (listaTipoArchivosMovSel.size() > 0) {
				TipoArchivoMovilidad tamo = (TipoArchivoMovilidad) listaTipoArchivosMovSel.get(0);
				ma.setTipoArchivo(tamo);
			}
		}
		listaMovilidadArchivo.add(ma);
	}

	public String InsertarAux() {
		this.limpiarAvisos();
		String resul = " ";
		try {

			if (estadoIngreso - 1 == 1) {
				if (validarCamposFormulario()) {
					// DARLE LA SEDE AL OBJETO CONVOCATORIA
					// dependenciaActual.setSede(sedeActual);
					convocatoriaActual.setEtapaCreacion(new Long("1"));

					boolean nuevaConvocatoria = convocatoriaActual.getId() == null;
					Sede sedeConvocatoria = new Sede();
					sedeConvocatoria.setId(new Long(idSede));
					convocatoriaActual.setSede(sedeConvocatoria);
					// DARLE LA DEPENDENCIA AL OBJETO CONVOCATORIA
					// convocatoriaActual.setDependencia(dependenciaActual);
					listaDependencia = servicioGeneral.obtenerDependencias(sedeConvocatoria);

					convocatoriaActual.setDependencia((Dependencia) listaDependencia.get(0));

					// DARLE EL ESTADO AL OBJETO CONVOCATORIA
					convocatoriaActual.setEstadoConvocatoria(estadoConvocatoria);

					// Se asigna el tipo de modalidad convocatoria
					TipoModalidad tm = new TipoModalidad();
					tm.setId(TipoModalidad.CONVOCATORIA);
					convocatoriaActual.setTipo(tm);
					/***************************************************************
					 * Long ide = new Long(convocatoria);
					 * convocatoriaActual.setId(ide); ConvocatoriaPadre conv =
					 * new ConvocatoriaPadre();
					 * conv.setId(convocatoriaActual.getPadre().getId());
					 * convocatoriaActual.setPadre(conv);
					 **************************************************************/

					// Se asignan lasdarle opciones cuando la convocatoria es
					// para
					// grupos
					convocatoriaActual.setGruposRegistrados(new Boolean(false));
					convocatoriaActual.setGruposReconocidos(new Boolean(false));
					convocatoriaActual.setGruposCategoriaA(new Boolean(false));
					convocatoriaActual.setGruposCategoriaB(new Boolean(false));
					convocatoriaActual.setGruposCategoriaC(new Boolean(false));
					if (esParaGrupos.equals("S")) {
						convocatoriaActual.setEsParaGrupos(new Boolean(true));
						for (int i = 0; i < listaTipoGrupos.length; i++) {
							if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.ACTIVO))
								convocatoriaActual.setGruposRegistrados(new Boolean(true));
							else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.INGRESANDO))
								convocatoriaActual.setGruposReconocidos(new Boolean(true));
							else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.SOLICITUD_AVAL))
								convocatoriaActual.setGruposCategoriaA(new Boolean(true));
							else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.DEVUELTO_CORRECCION))
								convocatoriaActual.setGruposCategoriaB(new Boolean(true));
							else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.REGISTRADO))
								convocatoriaActual.setGruposCategoriaC(new Boolean(true));
						}

						if (registroProyectoLiderGrupo.equals("S")) {
							convocatoriaActual.setGruposCategoriaD(new Boolean(true));
						}
					} else {
						convocatoriaActual.setEsParaGrupos(new Boolean(false));
					}

					// Si la convocatoria tiene reestriccion de dependencia
					if (esParaSedes.equals("S")) {
						convocatoriaActual.setDependenciaRestriccion(dependenciaActualRestriccion);
					} else {
						convocatoriaActual.setDependenciaRestriccion(null);
					}

					servicioModalidad.guardarConvocatoria(convocatoriaActual);

					for (Iterator itModalidadFuenteFinanciacion = listaModalidadFuenteFinanciacion.iterator(); itModalidadFuenteFinanciacion.hasNext();) {
						ModalidadFuenteFinanciacion mff = (ModalidadFuenteFinanciacion) itModalidadFuenteFinanciacion.next();
						mff.setModalidad(convocatoriaActual);

						if (mff.isBorrar() && mff.getId() != null) {
							servicioGeneral.eliminarObjeto(mff);
							listaModalidadFuenteFinanciacion.remove(mff);
						}
						if (mff.getId() != null && !mff.isBorrar()) {
							servicioGeneral.guardarObjeto(mff);
						}
						if (mff.getId() == null && !mff.isBorrar()) {
							servicioGeneral.insertarObjeto(mff);
						}

					}

				}
			}

			if (estadoIngreso - 1 == 2) {
				if (validarRequisitos()) {
					servicioGeneral.insertarObjeto(requisito);
					convocatoriaActual.setEtapaCreacion(new Long("2"));

					for (Iterator iteratorReq = requisito.getHijos().iterator(); iteratorReq.hasNext();) {
						TipoRequisito req = (TipoRequisito) iteratorReq.next();
						servicioGeneral.insertarObjeto(req);
					}
				}
				servicioModalidad.guardarConvocatoria(convocatoriaActual);
			}

			if (estadoIngreso - 1 == 3) {
				if (validarRubros()) {
					convocatoriaActual.setEtapaCreacion(new Long("3"));

					Set rf = servicioModalidad.actualizarPorcentajesRubrosFinanciables(convocatoriaActual);
					if (rf == null) {
						avisoRubros = "hay porcentajes que son mayores al 100%";
						return "";
					}
					convocatoriaActual.setRubrosFinanciables(rf);

					servicioModalidad.guardarConvocatoria(convocatoriaActual);
					// servicioModalidad.guardarConvocatoria(convocatoriaActual);
					// if (nuevaConvocatoria) {
					// this.guardarRecursos();
					// } else {
					// this.actualizarRecursos();
					// }
				}
			}
			if (estadoIngreso - 1 == 4) {
				if (validarCompromisos()) {
					convocatoriaActual.setEtapaCreacion(new Long("4"));
					// servicioModalidad.guardarConvocatoria(convocatoriaActual);

					servicioGeneral.insertarObjeto(compromiso);

					for (Iterator iteratorCom = compromiso.getHijos().iterator(); iteratorCom.hasNext();) {
						TipoCompromiso com = (TipoCompromiso) iteratorCom.next();
						servicioGeneral.insertarObjeto(com);
					}
					servicioModalidad.guardarConvocatoria(convocatoriaActual);
				}

			}

			if (estadoIngreso - 1 == 5) {
				if (validarProductos()) {
					convocatoriaActual.setEtapaCreacion(new Long("5"));
					servicioModalidad.guardarConvocatoria(convocatoriaActual);

				}
			}

			if (estadoIngreso - 1 == 6) {
				if (validarCriterios()) {
					convocatoriaActual.setEtapaCreacion(new Long("6"));

					// carga los criterios seleccionados
					List listaAux = new Vector();
					for (Iterator itCriteriosSeleccionados = listaCriteriosSeleccionados.iterator(); itCriteriosSeleccionados.hasNext();) {
						String idCriterioSeleccionado = (String) itCriteriosSeleccionados.next();
						listaAux.add(servicioGeneral.obtenerObjeto(new CriterioEvaluacion(), new Long(idCriterioSeleccionado)));
					}

					List l = new Vector();
					if (convocatoriaActual.getId() != null) {

						for (Iterator i = listaCriteriosTipoPregunta.iterator(); i.hasNext();) {
							VistaModalidadCriterioTipoPregunta v = (VistaModalidadCriterioTipoPregunta) i.next();

							List criterioS = v.getCriterioSeleccionado();
							if (criterioS != null && criterioS.size() > 0) {
								String idC = (String) criterioS.get(0);
								Long idCriterio = new Long(idC);
								CriterioEvaluacion ce = (CriterioEvaluacion) servicioGeneral.obtenerObjeto(new CriterioEvaluacion(), idCriterio);
								Long idTipoPregunta = v.getIdTipoPregunta();
								TipoPregunta tp = (TipoPregunta) servicioGeneral.obtenerObjeto(new TipoPregunta(), idTipoPregunta);
								if (servicioModalidad.modalidadContieneCriterio(convocatoriaActual, idCriterio)) {

									ModalidadCriterioTipoPregunta mctp = servicioEvaluacion.obtenerModalidadTipoPreguntaXModalidadYCriterio(convocatoriaActual.getId(), idCriterio);
									mctp.setCriterio(ce);
									mctp.setTipoPregunta(tp);
									l.add(mctp);
								} else {
									ModalidadCriterioTipoPregunta mctp = new ModalidadCriterioTipoPregunta();
									mctp.setTipoPregunta(tp);
									mctp.setCriterio(ce);
									mctp.setModalidad(convocatoriaActual);
									l.add(mctp);
								}

							}
						}
						convocatoriaActual.setCriteriosTipoPregunta(new HashSet(l));
					} else {
						for (Iterator i = listaCriteriosTipoPregunta.iterator(); i.hasNext();) {
							VistaModalidadCriterioTipoPregunta v = (VistaModalidadCriterioTipoPregunta) i.next();

							List criterioS = v.getCriterioSeleccionado();
							if (criterioS != null && criterioS.size() > 0) {
								String idC = (String) criterioS.get(0);
								Long idCriterio = new Long(idC);
								CriterioEvaluacion ce = (CriterioEvaluacion) servicioGeneral.obtenerObjeto(new CriterioEvaluacion(), idCriterio);
								Long idTipoPregunta = v.getIdTipoPregunta();
								TipoPregunta tp = (TipoPregunta) servicioGeneral.obtenerObjeto(new TipoPregunta(), idTipoPregunta);

								ModalidadCriterioTipoPregunta mctp = new ModalidadCriterioTipoPregunta();
								mctp.setTipoPregunta(tp);
								mctp.setCriterio(ce);
								mctp.setModalidad(convocatoriaActual);
								l.add(mctp);

							}
						}
						convocatoriaActual.setCriteriosTipoPregunta(new HashSet(l));
					}
					servicioModalidad.guardarConvocatoria(convocatoriaActual);
				}
			}

			if (estadoIngreso - 1 == 7) {
				if (validarCriterios()) {
					convocatoriaActual.setEtapaCreacion(new Long("7"));
					servicioModalidad.guardarConvocatoria(convocatoriaActual);
					// this.actualizarRecursos();
					this.guardarConvocatoria();
					sesion.removeAttribute("manejadorInsercionConvocatorias");
					sesion.removeAttribute("manejadorConsultaConvocatorias");
					sesion.removeAttribute("convocatoriaEdicion");
					resul = "consultarConvocatoria";
				}
			}

		} catch (Exception e) {
			// AL FALLAR LA INSERCION DE LA CONVOCATORIA SE DEBE MOSTRAR LA
			// RAZON POR LA CUAL LA INSERCION DE LA
			// CONVOCATORIA FALLA Y MANTENER LA REFERENCIA AL OBJETO ACTUAL
			// INTACTA
			e.printStackTrace();
			resul = "failled";
		}

		return resul;

	}

	public String insertarConvocatoriaMovilidad() {
		this.limpiarAvisos();
		String resul = " ";
		try {
			if (validarCamposFormularioMovilidad() && validarRequisitos()) {

				// DARLE LA SEDE AL OBJETO CONVOCATORIA
				// dependenciaActual.setSede(sedeActual);
				boolean nuevaConvocatoria = convocatoriaActual.getId() == null;
				Sede sedeConvocatoria = new Sede();
				sedeConvocatoria.setId(new Long(idSede));
				convocatoriaActual.setSede(sedeConvocatoria);
				// DARLE LA DEPENDENCIA AL OBJETO CONVOCATORIA
				// convocatoriaActual.setDependencia(dependenciaActual);
				listaDependencia = servicioGeneral.obtenerDependencias(sedeConvocatoria);

				convocatoriaActual.setDependencia((Dependencia) listaDependencia.get(0));

				// DARLE EL ESTADO AL OBJETO CONVOCATORIA
				convocatoriaActual.setEstadoConvocatoria(estadoConvocatoria);

				// Si la convocatoria tiene reestriccion de dependencia
				if (esParaSedes.equals("S")) {
					convocatoriaActual.setDependenciaRestriccion(dependenciaActualRestriccion);
				} else {
					convocatoriaActual.setDependenciaRestriccion(null);
				}

				servicioGeneral.insertarObjeto(requisito);

				for (Iterator iteratorReq = requisito.getHijos().iterator(); iteratorReq.hasNext();) {
					TipoRequisito req = (TipoRequisito) iteratorReq.next();
					req.setPadre(requisito);
					servicioGeneral.insertarObjeto(req);

				}

				servicioModalidad.guardarConvocatoria(convocatoriaActual);

				if (validarArchivosMovilidad()) {
					TipoMovilidad tm = new TipoMovilidad();
					tm.setId("CF_" + convocatoriaActual.getId());

					for (int i = 0; i < listaMovilidadArchivo.size(); i++) {
						MovilidadArchivo ma = listaMovilidadArchivo.get(i);
						ma.setTipoMovilidad(tm);
						servicioGeneral.guardarObjeto(ma);
					}

				}

				sesion.removeAttribute("manejadorInsercionConvocatorias");
				sesion.removeAttribute("manejadorConsultaConvocatorias");
				sesion.removeAttribute("convocatoriaEdicion");
				resul = "irConsultarConvocatoriasPadre";
			}
		} catch (Exception e) {
			// AL FALLAR LA INSERCION DE LA CONVOCATORIA SE DEBE MOSTRAR LA
			// RAZON POR LA CUAL LA INSERCION DE LA
			// CONVOCATORIA FALLA Y MANTENER LA REFERENCIA AL OBJETO ACTUAL
			// INTACTA
			e.printStackTrace();
			resul = "failled";
		}

		return resul;
	}

	public String InsertarConvocatoria() {
		this.limpiarAvisos();
		String resul = " ";
		try {
			if (validarCamposFormulario() && validarRequisitos() && validarRubros() && validarCompromisos() && validarProductos() && validarCriterios()) {

				// DARLE LA SEDE AL OBJETO CONVOCATORIA
				// dependenciaActual.setSede(sedeActual);
				boolean nuevaConvocatoria = convocatoriaActual.getId() == null;
				Sede sedeConvocatoria = new Sede();
				sedeConvocatoria.setId(new Long(idSede));
				convocatoriaActual.setSede(sedeConvocatoria);
				// DARLE LA DEPENDENCIA AL OBJETO CONVOCATORIA
				// convocatoriaActual.setDependencia(dependenciaActual);
				listaDependencia = servicioGeneral.obtenerDependencias(sedeConvocatoria);

				convocatoriaActual.setDependencia((Dependencia) listaDependencia.get(0));

				// DARLE EL ESTADO AL OBJETO CONVOCATORIA
				convocatoriaActual.setEstadoConvocatoria(estadoConvocatoria);

				// Se asignan lasdarle opciones cuando la convocatoria es para
				// grupos
				convocatoriaActual.setGruposRegistrados(new Boolean(false));
				convocatoriaActual.setGruposReconocidos(new Boolean(false));
				convocatoriaActual.setGruposCategoriaA(new Boolean(false));
				convocatoriaActual.setGruposCategoriaB(new Boolean(false));
				convocatoriaActual.setGruposCategoriaC(new Boolean(false));
				if (esParaGrupos.equals("S")) {
					convocatoriaActual.setEsParaGrupos(new Boolean(true));
					for (int i = 0; i < listaTipoGrupos.length; i++) {
						if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.ACTIVO))
							convocatoriaActual.setGruposRegistrados(new Boolean(true));
						else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.INGRESANDO))
							convocatoriaActual.setGruposReconocidos(new Boolean(true));
						else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.SOLICITUD_AVAL))
							convocatoriaActual.setGruposCategoriaA(new Boolean(true));
						else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.DEVUELTO_CORRECCION))
							convocatoriaActual.setGruposCategoriaB(new Boolean(true));
						else if (listaTipoGrupos[i] != null && listaTipoGrupos[i].equals(CategoriaGrupo.REGISTRADO))
							convocatoriaActual.setGruposCategoriaC(new Boolean(true));
					}

					if (registroProyectoLiderGrupo.equals("S")) {
						convocatoriaActual.setGruposCategoriaD(new Boolean(true));
					}
				} else {
					convocatoriaActual.setEsParaGrupos(new Boolean(false));
				}

				// Si la convocatoria tiene reestriccion de dependencia
				if (esParaSedes.equals("S")) {
					convocatoriaActual.setDependenciaRestriccion(dependenciaActualRestriccion);
				} else {
					convocatoriaActual.setDependenciaRestriccion(null);
				}

				// carga los criterios seleccionados
				List listaAux = new Vector();
				for (Iterator itCriteriosSeleccionados = listaCriteriosSeleccionados.iterator(); itCriteriosSeleccionados.hasNext();) {
					String idCriterioSeleccionado = (String) itCriteriosSeleccionados.next();
					listaAux.add(servicioGeneral.obtenerObjeto(new CriterioEvaluacion(), new Long(idCriterioSeleccionado)));
				}
				// convocatoriaActual.setCriterios(new HashSet(listaAux));
				// INSERTAR LA CONVOCATORIA EN LA BASE DE DATOS
				Set rf = servicioModalidad.actualizarPorcentajesRubrosFinanciables(convocatoriaActual);
				if (rf == null) {
					avisoRubros = "hay porcentajes que son mayores al 100%";
					return "";
				}
				convocatoriaActual.setRubrosFinanciables(rf);

				List l = new Vector();
				if (convocatoriaActual.getId() != null) {

					for (Iterator i = listaCriteriosTipoPregunta.iterator(); i.hasNext();) {
						VistaModalidadCriterioTipoPregunta v = (VistaModalidadCriterioTipoPregunta) i.next();

						List criterioS = v.getCriterioSeleccionado();
						if (criterioS != null && criterioS.size() > 0) {
							String idC = (String) criterioS.get(0);
							Long idCriterio = new Long(idC);
							CriterioEvaluacion ce = (CriterioEvaluacion) servicioGeneral.obtenerObjeto(new CriterioEvaluacion(), idCriterio);
							Long idTipoPregunta = v.getIdTipoPregunta();
							TipoPregunta tp = (TipoPregunta) servicioGeneral.obtenerObjeto(new TipoPregunta(), idTipoPregunta);
							if (servicioModalidad.modalidadContieneCriterio(convocatoriaActual, idCriterio)) {

								ModalidadCriterioTipoPregunta mctp = servicioEvaluacion.obtenerModalidadTipoPreguntaXModalidadYCriterio(convocatoriaActual.getId(), idCriterio);
								mctp.setCriterio(ce);
								mctp.setTipoPregunta(tp);
								l.add(mctp);
							} else {
								ModalidadCriterioTipoPregunta mctp = new ModalidadCriterioTipoPregunta();
								mctp.setTipoPregunta(tp);
								mctp.setCriterio(ce);
								mctp.setModalidad(convocatoriaActual);
								l.add(mctp);
							}

						}
					}
					convocatoriaActual.setCriteriosTipoPregunta(new HashSet(l));
				} else {
					for (Iterator i = listaCriteriosTipoPregunta.iterator(); i.hasNext();) {
						VistaModalidadCriterioTipoPregunta v = (VistaModalidadCriterioTipoPregunta) i.next();

						List criterioS = v.getCriterioSeleccionado();
						if (criterioS != null && criterioS.size() > 0) {
							String idC = (String) criterioS.get(0);
							Long idCriterio = new Long(idC);
							CriterioEvaluacion ce = (CriterioEvaluacion) servicioGeneral.obtenerObjeto(new CriterioEvaluacion(), idCriterio);
							Long idTipoPregunta = v.getIdTipoPregunta();
							TipoPregunta tp = (TipoPregunta) servicioGeneral.obtenerObjeto(new TipoPregunta(), idTipoPregunta);

							ModalidadCriterioTipoPregunta mctp = new ModalidadCriterioTipoPregunta();
							mctp.setTipoPregunta(tp);
							mctp.setCriterio(ce);
							mctp.setModalidad(convocatoriaActual);
							l.add(mctp);
							// }

						}
					}
					convocatoriaActual.setCriteriosTipoPregunta(new HashSet(l));
				}

				servicioGeneral.insertarObjeto(requisito);
				servicioGeneral.insertarObjeto(compromiso);

				for (Iterator iteratorReq = requisito.getHijos().iterator(); iteratorReq.hasNext();) {
					TipoRequisito req = (TipoRequisito) iteratorReq.next();
					req.setPadre(requisito);
					servicioGeneral.insertarObjeto(req);

				}

				for (Iterator iteratorCom = compromiso.getHijos().iterator(); iteratorCom.hasNext();) {
					TipoCompromiso com = (TipoCompromiso) iteratorCom.next();
					com.setPadre(compromiso);
					servicioGeneral.insertarObjeto(com);
				}

				// ///
				// convocatoriaActual.setEtapaCreacion(new Long("7"));
				servicioModalidad.guardarConvocatoria(convocatoriaActual);
				int estadoIngresoAux = convocatoriaActual.getEtapaCreacion().intValue();

				List listaRecursos = new ArrayList();

				Long cuentaRecursos = new Long("0");
				cuentaRecursos = servicioGeneral.consultaRecursosConvocatoria(convocatoriaActual.getPadre().getId(), convocatoriaActual.getId());

				if (nuevaConvocatoria || estadoIngresoAux < 7) {
					this.guardarRecursos();
				} else {
					if (cuentaRecursos.intValue() > 0) {
						this.actualizarRecursos();
					} else {
						this.guardarRecursos();
					}
				}

				for (Iterator itModalidadFuenteFinanciacion = listaModalidadFuenteFinanciacion.iterator(); itModalidadFuenteFinanciacion.hasNext();) {
					ModalidadFuenteFinanciacion mff = (ModalidadFuenteFinanciacion) itModalidadFuenteFinanciacion.next();
					if (mff.isBorrar() && mff.getId() != null) {
						servicioGeneral.eliminarObjeto(mff);
						listaModalidadFuenteFinanciacion.remove(mff);
					}
					if (mff.getId() != null && !mff.isBorrar()) {
						servicioGeneral.guardarObjeto(mff);
					}
					if (mff.getId() == null && !mff.isBorrar()) {
						servicioGeneral.insertarObjeto(mff);
					}

				}

				convocatoriaActual.setEtapaCreacion(new Long("7"));
				servicioModalidad.guardarConvocatoria(convocatoriaActual);

				String hqlFormEsp = "select e from FormularioInformacionEspecifica e where e.modalidad.id = " + convocatoriaActual.getId();
				List<FormularioInformacionEspecifica> listaFormInfoEsp = servicioGeneral.obtenerObjetos(FormularioInformacionEspecifica.class, hqlFormEsp);
				if (listaFormInfoEsp != null && listaFormInfoEsp.size() > 0) {
					FormularioInformacionEspecifica fie = listaFormInfoEsp.get(0);
					Modalidad mod = servicioModalidad.obtenerModalidad(convocatoriaActual.getId());
					fie.setModalidad(mod);
					if (mostrarCampoUno.equals("S")) {
						fie.setCampoUno(true);
						fie.setCampoUnoTexto(textoCampoUno);
						fie.setCampoUnoObligatorio(campoUnoObligatorio);
					} else {
						fie.setCampoUno(false);
					}

					if (mostrarCampoDos.equals("S")) {
						fie.setCampoDos(true);
						fie.setCampoDosTexto(textoCampoDos);
						fie.setCampoDosObligatorio(campoDosObligatorio);
					} else {
						fie.setCampoDos(false);
					}

					if (mostrarCampoTres.equals("S")) {
						fie.setCampoTres(true);
						fie.setCampoTresTexto(textoCampoTres);
						fie.setCampoTresObligatorio(campoTresObligatorio);
					} else {
						fie.setCampoTres(false);
					}

					if (mostrarCampoCuatro.equals("S")) {
						fie.setCampoCuatro(true);
						fie.setCampoCuatroTexto(textoCampoCuatro);
						fie.setCampoCuatroObligatorio(campoCuatroObligatorio);
					} else {
						fie.setCampoCuatro(false);
					}

					if (mostrarCampoCinco.equals("S")) {
						fie.setCampoCinco(true);
						fie.setCampoCincoTexto(textoCampoCinco);
						fie.setCampoCincoObligatorio(campoCincoObligatorio);
					} else {
						fie.setCampoCinco(false);
					}

					if (mostrarCampoSeis.equals("S")) {
						fie.setCampoSeis(true);
						fie.setCampoSeisTexto(textoCampoSeis);
						fie.setCampoSeisObligatorio(campoSeisObligatorio);
					} else {
						fie.setCampoSeis(false);
					}

					if (mostrarCampoAsignaturas.equals("S")) {
						fie.setAsignatura(true);
					} else {
						fie.setAsignatura(false);
					}

					if (mostrarCampoObjetivosResponsable.equals("S")) {
						fie.setObjetivosResutadosResponsables(true);
					} else {
						fie.setObjetivosResutadosResponsables(false);
					}

					if (mostrarCampoGrupos.equals("S")) {
						fie.setGrupos(true);
					} else {
						fie.setGrupos(false);
					}

					if (mostrarLugarEjecucion.equals("S")) {
						fie.setLugarEjecucion(true);
					} else {
						fie.setLugarEjecucion(false);
					}
					servicioGeneral.guardarObjeto(fie);

				} else {
					FormularioInformacionEspecifica fie = new FormularioInformacionEspecifica();
					Modalidad mod = servicioModalidad.obtenerModalidad(convocatoriaActual.getId());
					fie.setModalidad(mod);
					if (mostrarCampoUno.equals("S")) {
						fie.setCampoUno(true);
						fie.setCampoUnoTexto(textoCampoUno);
						fie.setCampoUnoObligatorio(campoUnoObligatorio);
					} else {
						fie.setCampoUno(false);
					}

					if (mostrarCampoDos.equals("S")) {
						fie.setCampoDos(true);
						fie.setCampoDosTexto(textoCampoDos);
						fie.setCampoDosObligatorio(campoDosObligatorio);
					} else {
						fie.setCampoDos(false);
					}

					if (mostrarCampoTres.equals("S")) {
						fie.setCampoTres(true);
						fie.setCampoTresTexto(textoCampoTres);
						fie.setCampoTresObligatorio(campoTresObligatorio);
					} else {
						fie.setCampoTres(false);
					}

					if (mostrarCampoCuatro.equals("S")) {
						fie.setCampoCuatro(true);
						fie.setCampoCuatroTexto(textoCampoCuatro);
						fie.setCampoCuatroObligatorio(campoCuatroObligatorio);
					} else {
						fie.setCampoCuatro(false);
					}

					if (mostrarCampoCinco.equals("S")) {
						fie.setCampoCinco(true);
						fie.setCampoCincoTexto(textoCampoCinco);
						fie.setCampoCincoObligatorio(campoCincoObligatorio);
					} else {
						fie.setCampoCinco(false);
					}

					if (mostrarCampoSeis.equals("S")) {
						fie.setCampoSeis(true);
						fie.setCampoSeisTexto(textoCampoSeis);
						fie.setCampoSeisObligatorio(campoSeisObligatorio);
					} else {
						fie.setCampoSeis(false);
					}

					if (mostrarCampoAsignaturas.equals("S")) {
						fie.setAsignatura(true);
					} else {
						fie.setAsignatura(false);
					}

					if (mostrarCampoObjetivosResponsable.equals("S")) {
						fie.setObjetivosResutadosResponsables(true);
					} else {
						fie.setObjetivosResutadosResponsables(false);
					}

					if (mostrarCampoGrupos.equals("S")) {
						fie.setGrupos(true);
					} else {
						fie.setGrupos(false);
					}

					if (mostrarLugarEjecucion.equals("S")) {
						fie.setLugarEjecucion(true);
					} else {
						fie.setLugarEjecucion(false);
					}
					servicioGeneral.guardarObjeto(fie);
				}

				sesion.removeAttribute("manejadorInsercionConvocatorias");
				sesion.removeAttribute("manejadorConsultaConvocatorias");
				sesion.removeAttribute("convocatoriaEdicion");
				resul = "irConsultarConvocatoriasPadre";
			}
		} catch (Exception e) {
			// AL FALLAR LA INSERCION DE LA CONVOCATORIA SE DEBE MOSTRAR LA
			// RAZON POR LA CUAL LA INSERCION DE LA
			// CONVOCATORIA FALLA Y MANTENER LA REFERENCIA AL OBJETO ACTUAL
			// INTACTA
			e.printStackTrace();
			resul = "failled";
		}

		return resul;

	}
	
	//Guardar requisitos y continuar siguiente pestaña sin validar pestañas siguientes
	
	public String guardarRequisitos() {
		this.limpiarAvisos();
		String resul = " ";
		try {
			if (validarCamposFormulario() && validarRequisitos()) {
				servicioGeneral.guardarObjeto(requisito);

				for (Iterator iteratorReq = requisito.getHijos().iterator(); iteratorReq.hasNext();) {
					TipoRequisito req = (TipoRequisito) iteratorReq.next();
					if(req.getId()==null) {
						servicioGeneral.insertarObjeto(req);
					}else {
						servicioGeneral.guardarObjeto(req);
					}
				}
				servicioGeneral.guardarObjeto(convocatoriaActual);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resul= "Ocurrio un problema al guardar la convocatoria y los requisitos";
		}
		
		return resul;
	}
	
	// FUNCIONES MISCELANEAS
	private void obtenerListaSedes() {
		// SE CARGAN LAS SEDES
		this.listaSede = servicioGeneral.obtenerListaObjetos("Sede");

		sedeItem = new SelectItem[listaSede.size()];
		for (int i = 0; i < listaSede.size(); i++) {
			Sede se = (Sede) listaSede.get(i);
			sedeItem[i] = new SelectItem(se.getId().toString(), se.getNombre());
			se = null;
		}

		// sedeActual = (Sede) listaSede.get(0);
	}

	/*
	 * private void cargarListaDependencias() { obtenerListaSedes(); // SE
	 * CARGAN LAS DEPENDENCIAS ASOCIADAS A LA RESPECTIVA SEDE listaDependencia =
	 * servicioGeneral.obtenerDependencias(sedeActual); for (int i = 0; i <
	 * listaDependencia.size(); i++) { Dependencia d = (Dependencia)
	 * listaDependencia.get(i); if (d.getNombre().length() > 73) {
	 * d.setNombre(d.getNombre().substring(0, 70) + "..."); } } }
	 */

	private void cargarListaDependenciasRestriccion() {
		listaDependenciaRestriccion = servicioGeneral.obtenerObjetos(Dependencia.class,
					"select d from Dependencia d where (d.esSede='Y' or d.esFacultad='Y') and d.estado='A'");
		// Se cargan las dependencia que se necesite para la restriccion
		// en este caso cargaremos sedes pero se puede cargar cualquier
		// dependencia por
		// ejemplo cargar facultades asi:
		// listaDependenciaRestriccion = servicioGeneral.obtenerSedes();
		dependenciaItemRestriccion = new SelectItem[listaDependenciaRestriccion.size()];
		for (int i = 0; i < listaDependenciaRestriccion.size(); i++) {
			Dependencia dp = (Dependencia) listaDependenciaRestriccion.get(i);
			dependenciaItemRestriccion[i] = new SelectItem(dp.getId(), dp.getNombre());
			dp = null;
		}
		dependenciaActualRestriccion = (Dependencia) listaDependenciaRestriccion.get(0);
	}

	private Sede BuscarSede(Long id) {
		Sede s = new Sede();
		int i = 0;
		while (i < listaSede.size()) {
			s = (Sede) listaSede.get(i);
			if (id.longValue() == (s.getId()).longValue())
				break;
			i = i + 1;
		}
		return s;
	}

	public void cambiaTipoRubroPadre(ValueChangeEvent event) {
		Long nuevoTipoPadre = (Long) event.getNewValue();
		rubroFinanciableActual.getTipoRubro().setId(nuevoTipoPadre);
		cargarListaRubrosHijos();
	}

	public void mensajeErrorIdCampo(String idComponente, String mensaje) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(idComponente, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
	}

	public void agregaModalidadFuenteFinanciacion() {
		double total = 0;
		if (listaModalidadFuenteFinanciacion != null) {
			for (Iterator it = listaModalidadFuenteFinanciacion.iterator(); it.hasNext();) {
				ModalidadFuenteFinanciacion mffA = (ModalidadFuenteFinanciacion) it.next();
				if (!mffA.isBorrar()) {

					total += mffA.getPorcentaje().doubleValue();
				}
			}
		}

		total += new Double(porcentaje).doubleValue();
		if (total > 100) {
			avisoFuentes = "la suma de los porcentajes de las fuentes no pueden pasar del 100%";
			return;
		} else {
			avisoFuentes = "";
		}
		ModalidadFuenteFinanciacion mff = new ModalidadFuenteFinanciacion();
		FuenteFinanciacion ff = (FuenteFinanciacion) servicioGeneral.obtenerObjeto(new FuenteFinanciacion(), idFuenteFinanciacion);
		mff.setFuenteFinanciacion(ff);
		mff.setModalidad(convocatoriaActual);
		mff.setPorcentaje(new Double(porcentaje));
		listaModalidadFuenteFinanciacion.add(mff);
	}

	public void eliminarModalidadFuenteFinanciacion() {
		listaModalidadFuenteFinanciacion.remove(fuenteFinanciacionSeleccion);
		fuenteFinanciacionSeleccion = new ModalidadFuenteFinanciacion();
	}

	public void cambiaTipoProducto(ValueChangeEvent event) {
	}

	/***************************************************************************
	 * METODOS ACCESORES
	 **************************************************************************/
	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
	}

	public List getListaDependencia() {
		return listaDependencia;
	}

	public void setListaDependencia(List listaDependencia) {
		this.listaDependencia = listaDependencia;
	}

	public List getListaSede() {
		return listaSede;
	}

	public void setListaSede(List listaSede) {
		this.listaSede = listaSede;
	}

	public UISelectOne getManejadorSedes() {
		return manejadorSedes;
	}

	public void setManejadorSedes(UISelectOne manejadorSedes) {
		this.manejadorSedes = manejadorSedes;
	}

	public Sede getSedeActual() {
		return sedeActual;
	}

	public void setSedeActual(Sede sedeActual) {
		this.sedeActual = sedeActual;
	}

	public EstadoConvocatoria getEstadoConvocatoria() {
		return estadoConvocatoria;
	}

	public void setEstadoConvocatoria(EstadoConvocatoria estadoConvocatoria) {
		this.estadoConvocatoria = estadoConvocatoria;
	}

	public List getListaEstadosConvocatoria() {
		return listaEstadosConvocatoria;
	}

	public void setListaEstadosConvocatoria(List listaEstadosConvocatoria) {
		this.listaEstadosConvocatoria = listaEstadosConvocatoria;
	}

	public String getAvisoFormulario() {
		return avisoFormulario;
	}

	public void setAvisoFormulario(String avisoFormulario) {
		this.avisoFormulario = avisoFormulario;
	}

	public List getListaProductosItem() {
		return listaProductosItem;
	}

	public void setListaProductosItem(List listaProductosItem) {
		this.listaProductosItem = listaProductosItem;
	}

	public UISelectMany getSelectProducto() {
		return selectProducto;
	}

	public void setSelectProducto(UISelectMany selectProducto) {
		this.selectProducto = selectProducto;
	}

	public String getAvisoProductos() {
		return avisoProductos;
	}

	public void setAvisoProductos(String avisoProductos) {
		this.avisoProductos = avisoProductos;
	}

	public String getAvisoRubros() {
		return avisoRubros;
	}

	public void setAvisoRubros(String avisoRubros) {
		this.avisoRubros = avisoRubros;
	}

	public String[] getListaProductoSeleccionados() {
		return listaProductoSeleccionados;
	}

	public void setListaProductoSeleccionados(String[] listaProductoSeleccionados) {
		this.listaProductoSeleccionados = listaProductoSeleccionados;
	}

	public List getListaproductosTipo() {
		return listaproductosTipo;
	}

	public void setListaproductosTipo(List listaproductosTipo) {
		this.listaproductosTipo = listaproductosTipo;
	}

	public RubroFinanciable getRubroFinanciableActual() {
		return rubroFinanciableActual;
	}

	public void setRubroFinanciableActual(RubroFinanciable rubroFinanciableActual) {
		this.rubroFinanciableActual = rubroFinanciableActual;
	}

	public DataTable getTablaRubrosFinanciables() {
		return tablaRubrosFinanciables;
	}

	public void setTablaRubrosFinanciables(DataTable tablaRubrosFinanciables) {
		this.tablaRubrosFinanciables = tablaRubrosFinanciables;
	}

	public ConvocatoriaPadre getConvocatoriaPadre() {
		return convocatoriaPadre;
	}

	public void setConvocatoriaPadre(ConvocatoriaPadre convocatoriaPadre) {
		this.convocatoriaPadre = convocatoriaPadre;
	}

	public String getEsParaGrupos() {
		return esParaGrupos;
	}

	public void setEsParaGrupos(String esParaGrupos) {
		this.esParaGrupos = esParaGrupos;
	}

	public boolean isVisiblePanelGrupos() {
		return visiblePanelGrupos;
	}

	public void setVisiblePanelGrupos(boolean visiblePanelGrupos) {
		this.visiblePanelGrupos = visiblePanelGrupos;
	}

	public String[] getListaTipoGrupos() {
		return listaTipoGrupos;
	}

	public void setListaTipoGrupos(String[] listaTipoGrupos) {
		this.listaTipoGrupos = listaTipoGrupos;
	}

	public Dependencia getDependenciaActualRestriccion() {
		return dependenciaActualRestriccion;
	}

	public void setDependenciaActualRestriccion(Dependencia dependenciaActualRestriccion) {
		this.dependenciaActualRestriccion = dependenciaActualRestriccion;
	}

	public SelectItem[] getDependenciaItemRestriccion() {
		return dependenciaItemRestriccion;
	}

	public void setDependenciaItemRestriccion(SelectItem[] dependenciaItemRestriccion) {
		this.dependenciaItemRestriccion = dependenciaItemRestriccion;
	}

	public String getEsParaSedes() {
		return esParaSedes;
	}

	public void setEsParaSedes(String esParaSedes) {
		this.esParaSedes = esParaSedes;
	}

	public List getListaDependenciaRestriccion() {
		return listaDependenciaRestriccion;
	}

	public void setListaDependenciaRestriccion(List listaDependenciaRestriccion) {
		this.listaDependenciaRestriccion = listaDependenciaRestriccion;
	}

	public boolean isVisiblePanelSedes() {
		return visiblePanelSedes;
	}

	public void setVisiblePanelSedes(boolean visiblePanelSedes) {
		this.visiblePanelSedes = visiblePanelSedes;
	}

	public List getListaCriterios() {
		return listaCriterios;
	}

	public void setListaCriterios(List listaCriterios) {
		this.listaCriterios = listaCriterios;
	}

	public List getListaCriteriosSeleccionados() {
		return listaCriteriosSeleccionados;
	}

	public void setListaCriteriosSeleccionados(List listaCriteriosSeleccionados) {
		this.listaCriteriosSeleccionados = listaCriteriosSeleccionados;
	}

	public String getIdTipoRubroHijo() {
		return idTipoRubroHijo;
	}

	public void setIdTipoRubroHijo(String idTipoRubroHijo) {
		this.idTipoRubroHijo = idTipoRubroHijo;
	}

	public List getListaConvocatoriasPadre() {
		return listaConvocatoriasPadre;
	}

	public void setListaConvocatoriasPadre(List listaConvocatoriasPadre) {
		this.listaConvocatoriasPadre = listaConvocatoriasPadre;
	}

	public List getListaRubrosFinanciablesHijos() {
		return listaRubrosFinanciablesHijos;
	}

	public void setListaRubrosFinanciablesHijos(List listaRubrosFinanciablesHijos) {
		this.listaRubrosFinanciablesHijos = listaRubrosFinanciablesHijos;
	}

	public boolean getMuestraHijos() {
		return listaRubrosFinanciablesHijos != null && listaRubrosFinanciablesHijos.size() > 0;
	}

	public List getListaParametros() {
		return listaParametros;
	}

	public void setListaParametros(List listaParametros) {
		this.listaParametros = listaParametros;
	}

	public Long getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(Long idParametro) {
		this.idParametro = idParametro;
	}

	public String getIdFuenteFinanciacion() {
		return idFuenteFinanciacion;
	}

	public void setIdFuenteFinanciacion(String idFuenteFinanciacion) {
		this.idFuenteFinanciacion = idFuenteFinanciacion;
	}

	public String getIdTipoFuenteFinanciacion() {
		return idTipoFuenteFinanciacion;
	}

	public void setIdTipoFuenteFinanciacion(String idTipoFuenteFinanciacion) {
		this.idTipoFuenteFinanciacion = idTipoFuenteFinanciacion;
	}

	public List getListaFuentesFinanciacion() {
		return listaFuentesFinanciacion;
	}

	public void setListaFuentesFinanciacion(List listaFuentesFinanciacion) {
		this.listaFuentesFinanciacion = listaFuentesFinanciacion;
	}

	public List getListaModalidadFuenteFinanciacion() {
		return listaModalidadFuenteFinanciacion;
	}

	public void setListaModalidadFuenteFinanciacion(List listaModalidadFuenteFinanciacion) {
		this.listaModalidadFuenteFinanciacion = listaModalidadFuenteFinanciacion;
	}

	/*
	 * public List getListaTipoFuenteFinanciacion() { return
	 * listaTipoFuenteFinanciacion; } public void
	 * setListaTipoFuenteFinanciacion(List listaTipoFuenteFinanciacion) {
	 * this.listaTipoFuenteFinanciacion = listaTipoFuenteFinanciacion; }
	 */
	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;

	}

	public DataTable getTablaModalidadFuente() {
		return tablaModalidadFuente;
	}

	public void setTablaModalidadFuente(DataTable tablaModalidadFuente) {
		this.tablaModalidadFuente = tablaModalidadFuente;
	}

	public String getAvisoFuentes() {
		return avisoFuentes;
	}

	public void setAvisoFuentes(String avisoFuentes) {
		this.avisoFuentes = avisoFuentes;
	}

	public List getListaCriteriosTipoPregunta() {
		return listaCriteriosTipoPregunta;
	}

	public void setListaCriteriosTipoPregunta(List listaCriteriosTipoPregunta) {
		this.listaCriteriosTipoPregunta = listaCriteriosTipoPregunta;
	}

	public List getListaTipoPregunta() {

		return listaTipoPregunta;
	}

	public void setListaTipoPregunta(List listaTipoPregunta) {
		this.listaTipoPregunta = listaTipoPregunta;
	}

	public int getTamano() {
		return listaModalidadFuenteFinanciacion.size();
	}

	public DataTable getTablaModalidadCriterioTipoPregunta() {
		return tablaModalidadCriterioTipoPregunta;
	}

	public void setTablaModalidadCriterioTipoPregunta(DataTable tablaModalidadCriterioTipoPregunta) {
		this.tablaModalidadCriterioTipoPregunta = tablaModalidadCriterioTipoPregunta;
	}

	public boolean getDeshabilitarRubroHijo() {
		return deshabilitarRubroHijo;
	}

	public void setDeshabilitarRubroHijo(boolean deshabilitarRubroHijo) {
		this.deshabilitarRubroHijo = deshabilitarRubroHijo;
	}

	public DataTable getTablaProducto() {
		return tablaProducto;
	}

	public void setTablaProducto(DataTable tablaProducto) {
		this.tablaProducto = tablaProducto;
	}

	public UISelectOne getManejadorEsGrupos() {
		return manejadorEsGrupos;
	}

	public void setManejadorEsGrupos(UISelectOne manejadorEsGrupos) {
		this.manejadorEsGrupos = manejadorEsGrupos;
	}

	public UISelectOne getManejadorEsFacultades() {
		return manejadorEsFacultades;
	}

	public void setManejadorEsFacultades(UISelectOne manejadorEsFacultades) {
		this.manejadorEsFacultades = manejadorEsFacultades;
	}

	public UISelectOne getManejadorRubroPadre() {
		return manejadorRubroPadre;
	}

	public void setManejadorRubroPadre(UISelectOne manejadorRubroPadre) {
		this.manejadorRubroPadre = manejadorRubroPadre;
	}

	public List getListaRubros() {
		return listaRubros;
	}

	public void setListaRubros(List listaRubros) {
		this.listaRubros = listaRubros;
	}

	public List getListaRubroPadres() {
		return listaRubroPadres;
	}

	public void setListaRubroPadres(List listaRubroPadres) {
		this.listaRubroPadres = listaRubroPadres;
	}

	public List getListaRubroHijos() {
		return listaRubroHijos;
	}

	public void setListaRubroHijos(List listaRubroHijos) {
		this.listaRubroHijos = listaRubroHijos;
	}

	public RubroFinanciable getRubroFinanciableActualHijo() {
		return rubroFinanciableActualHijo;
	}

	public void setRubroFinanciableActualHijo(RubroFinanciable rubroFinanciableActualHijo) {
		this.rubroFinanciableActualHijo = rubroFinanciableActualHijo;
	}

	public UISelectOne getManejadorRubroHijo() {
		return manejadorRubroHijo;
	}

	public void setManejadorRubroHijo(UISelectOne manejadorRubroHijo) {
		this.manejadorRubroHijo = manejadorRubroHijo;
	}

	public UISelectOne getManejadorProductosNivel1() {
		return manejadorProductosNivel1;
	}

	public void setManejadorProductosNivel1(UISelectOne manejadorProductosNivel1) {
		this.manejadorProductosNivel1 = manejadorProductosNivel1;
	}

	public UISelectOne getManejadorProductosNivel2() {
		return manejadorProductosNivel2;
	}

	public void setManejadorProductosNivel2(UISelectOne manejadorProductosNivel2) {
		this.manejadorProductosNivel2 = manejadorProductosNivel2;
	}

	public UISelectOne getManejadorProductosNivel3() {
		return manejadorProductosNivel3;
	}

	public void setManejadorProductosNivel3(UISelectOne manejadorProductosNivel3) {
		this.manejadorProductosNivel3 = manejadorProductosNivel3;
	}

	public List getProductoTipoNivel1() {
		return productoTipoNivel1;
	}

	public void setProductoTipoNivel1(List productoTipoNivel1) {
		this.productoTipoNivel1 = productoTipoNivel1;
	}

	public List getProductoTipoNivel2() {
		return productoTipoNivel2;
	}

	public void setProductoTipoNivel2(List productoTipoNivel2) {
		this.productoTipoNivel2 = productoTipoNivel2;
	}

	public List getProductoTipoNivel3() {
		return productoTipoNivel3;
	}

	public void setProductoTipoNivel3(List productoTipoNivel3) {
		this.productoTipoNivel3 = productoTipoNivel3;
	}

	public boolean[] getReadOnlySelect() {
		return readOnlySelect;
	}

	public void setReadOnlySelect(boolean[] readOnlySelect) {
		this.readOnlySelect = readOnlySelect;
	}

	public String[] getSeleccionSelect() {
		return seleccionSelect;
	}

	public void setSeleccionSelect(String[] seleccionSelect) {
		this.seleccionSelect = seleccionSelect;
	}

	public ProductoTipo getProductoActual() {
		return productoActual;
	}

	public void setProductoActual(ProductoTipo productoActual) {
		this.productoActual = productoActual;
	}

	public List getListaCompromisoItem() {
		return listaCompromisoItem;
	}

	public void setListaCompromisoItem(List listaCompromisoItem) {
		this.listaCompromisoItem = listaCompromisoItem;
	}

	public UISelectMany getSelectCompromiso() {
		return selectCompromiso;
	}

	public void setSelectCompromiso(UISelectMany selectCompromiso) {
		this.selectCompromiso = selectCompromiso;
	}

	public String getAvisoCompromisos() {
		return avisoCompromisos;
	}

	public void setAvisoCompromisos(String avisoCompromisos) {
		this.avisoCompromisos = avisoCompromisos;
	}

	// public Long[] getListaCompromisoSeleccionados() {
	// return listaCompromisoSeleccionados;
	// }
	//
	// public void setListaCompromisoSeleccionados(
	// Long[] listaCompromisoSeleccionados) {
	// this.listaCompromisoSeleccionados = listaCompromisoSeleccionados;
	// }
	//
	// public List getListacompromisoTipo() {
	// return listacompromisoTipo;
	// }
	//
	// public void setListacompromisoTipo(List listacompromisoTipo) {
	// this.listacompromisoTipo = listacompromisoTipo;
	// }
	//
	// public List getCompromisoNivel1() {
	// return compromisoNivel1;
	// }
	//
	// public void setCompromisoNivel1(List compromisoNivel1) {
	// this.compromisoNivel1 = compromisoNivel1;
	// }
	//
	// public List getCompromisoNivel2() {
	// return compromisoNivel2;
	// }
	//
	// public void setCompromisoNivel2(List compromisoNivel2) {
	// this.compromisoNivel2 = compromisoNivel2;
	// }
	//
	// public boolean[] getReadOnlyCompromisoSelect() {
	// return readOnlyCompromisoSelect;
	// }
	//
	// public void setReadOnlyCompromisoSelect(boolean[]
	// readOnlyCompromisoSelect) {
	// this.readOnlyCompromisoSelect = readOnlyCompromisoSelect;
	// }
	//
	// public Long[] getSeleccionCompromisoSelect() {
	// return seleccionCompromisoSelect;
	// }
	//
	// public void setSeleccionCompromisoSelect(Long[]
	// seleccionCompromisoSelect) {
	// this.seleccionCompromisoSelect = seleccionCompromisoSelect;
	// }

	public TipoCompromiso getCompromisoActual() {
		return compromisoActual;
	}

	public void setCompromisoActual(TipoCompromiso compromisoActual) {
		this.compromisoActual = compromisoActual;
	}

	public DataTable getTablaCompromiso() {
		return tablaCompromiso;
	}

	public void setTablaCompromiso(DataTable tablaCompromiso) {
		this.tablaCompromiso = tablaCompromiso;
	}

	public HtmlPanelGrid getPanelGridCriteriosModalidadTipoPregunta() {
		return panelGridCriteriosModalidadTipoPregunta;
	}

	public void setPanelGridCriteriosModalidadTipoPregunta(HtmlPanelGrid panelGridCriteriosModalidadTipoPregunta) {
		this.panelGridCriteriosModalidadTipoPregunta = panelGridCriteriosModalidadTipoPregunta;
	}

	public UISelectOne getManejadorCompromisoNivel1() {
		return manejadorCompromisoNivel1;
	}

	public void setManejadorCompromisoNivel1(UISelectOne manejadorCompromisoNivel1) {
		this.manejadorCompromisoNivel1 = manejadorCompromisoNivel1;
	}

	public UISelectOne getManejadorCompromisoNivel2() {
		return manejadorCompromisoNivel2;
	}

	public void setManejadorCompromisoNivel2(UISelectOne manejadorCompromisoNivel2) {
		this.manejadorCompromisoNivel2 = manejadorCompromisoNivel2;
	}

	public List getListaRequisitoItem() {
		return listaRequisitoItem;
	}

	public void setListaRequisitoItem(List listaRequisitoItem) {
		this.listaRequisitoItem = listaRequisitoItem;
	}

	public UISelectMany getSelectRequisito() {
		return selectRequisito;
	}

	public void setSelectRequisito(UISelectMany selectRequisito) {
		this.selectRequisito = selectRequisito;
	}

	public String getAvisoRequisitos() {
		return avisoRequisitos;
	}

	public void setAvisoRequisitos(String avisoRequisitos) {
		this.avisoRequisitos = avisoRequisitos;
	}

	// public List getListarequisitoTipo() {
	// return listarequisitoTipo;
	// }
	//
	// public void setListarequisitoTipo(List listarequisitoTipo) {
	// this.listarequisitoTipo = listarequisitoTipo;
	// }
	//
	// public List getRequisitoNivel2() {
	// return requisitoNivel2;
	// }
	//
	// public void setRequisitoNivel2(List requisitoNivel2) {
	// this.requisitoNivel2 = requisitoNivel2;
	// }

	public TipoRequisito getRequisitoActual() {
		return requisitoActual;
	}

	public void setRequisitoActual(TipoRequisito requisitoActual) {
		this.requisitoActual = requisitoActual;
	}

	public DataTable getTablaRequisito() {
		return tablaRequisito;
	}

	public void setTablaRequisito(DataTable tablaRequisito) {
		this.tablaRequisito = tablaRequisito;
	}

	public UISelectOne getManejadorRequisitoNivel2() {
		return manejadorRequisitoNivel2;
	}

	public void setManejadorRequisitoNivel2(UISelectOne manejadorRequisitoNivel2) {
		this.manejadorRequisitoNivel2 = manejadorRequisitoNivel2;
	}

	public String getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}

	public SelectItem[] getConvocatoriaItem() {
		return convocatoriaItem;
	}

	public void setConvocatoriaItem(SelectItem[] convocatoriaItem) {
		this.convocatoriaItem = convocatoriaItem;
	}

	public List getListaConvocatorias() {
		return listaConvocatorias;
	}

	public void setListaConvocatorias(List listaConvocatorias) {
		this.listaConvocatorias = listaConvocatorias;
	}

	public String getNombreModalidad() {
		return nombreModalidad;
	}

	public void setNombreModalidad(String nombreModalidad) {
		this.nombreModalidad = nombreModalidad;
	}

	public DataTable getTablaNivel1() {
		return tablaNivel1;
	}

	public void setTablaNivel1(DataTable tablaNivel1) {
		this.tablaNivel1 = tablaNivel1;
	}

	/*
	 * public SelectItem[] getArregloRubrosPadre() { return arregloRubrosPadre;
	 * }
	 */
	public DataTable getTablaNivel2() {
		return tablaNivel2;
	}

	public void setTablaNivel2(DataTable tablaNivel2) {
		this.tablaNivel2 = tablaNivel2;
	}

	public HashMap getListaTemporalProductoSeleccionados() {
		return listaTemporalProductoSeleccionados;
	}

	public void setListaTemporalProductoSeleccionados(HashMap listaTemporalProductoSeleccionados) {
		this.listaTemporalProductoSeleccionados = listaTemporalProductoSeleccionados;
	}

	public Long[] getListaRequisitos() {
		return listaRequisitos;
	}

	public void setListaRequisitos(Long[] listaRequisitos) {
		this.listaRequisitos = listaRequisitos;
	}

	public DataTable getTablaCompromisoNivel1() {
		return tablaCompromisoNivel1;
	}

	public void setTablaCompromisoNivel1(DataTable tablaCompromisoNivel1) {
		this.tablaCompromisoNivel1 = tablaCompromisoNivel1;
	}

	public HashMap getListaTemporalCompromisosSeleccionados() {
		return listaTemporalCompromisosSeleccionados;
	}

	public void setListaTemporalCompromisosSeleccionados(HashMap listaTemporalCompromisosSeleccionados) {
		this.listaTemporalCompromisosSeleccionados = listaTemporalCompromisosSeleccionados;
	}

	public List getListaTipoFuenteFinanciacion() {
		return listaTipoFuenteFinanciacion;
	}

	public void setListaTipoFuenteFinanciacion(List listaTipoFuenteFinanciacion) {
		this.listaTipoFuenteFinanciacion = listaTipoFuenteFinanciacion;
	}

	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	public void setSedeItem(SelectItem[] sedeItem) {
		this.sedeItem = sedeItem;
	}

	public String getIdSede() {
		return idSede;
	}

	public void setIdSede(String idSede) {
		this.idSede = idSede;
	}

	public String getAvisoCriterios() {
		return avisoCriterios;
	}

	public void setAvisoCriterios(String avisoCriterios) {
		this.avisoCriterios = avisoCriterios;
	}

	public long getNivelNal() {
		return nivelNal;
	}

	public void setNivelNal(long nivelNal) {
		this.nivelNal = nivelNal;
	}

	public long getBogota() {
		return bogota;
	}

	public void setBogota(long bogota) {
		this.bogota = bogota;
	}

	public long getMedellin() {
		return medellin;
	}

	public void setMedellin(long medellin) {
		this.medellin = medellin;
	}

	public long getManizales() {
		return manizales;
	}

	public void setManizales(long manizales) {
		this.manizales = manizales;
	}

	public long getCaribe() {
		return caribe;
	}

	public void setCaribe(long caribe) {
		this.caribe = caribe;
	}

	public long getPalmira() {
		return palmira;
	}

	public void setPalmira(long palmira) {
		this.palmira = palmira;
	}

	public long getAmazonia() {
		return amazonia;
	}

	public void setAmazonia(long amazonia) {
		this.amazonia = amazonia;
	}

	public long getOrinoquia() {
		return orinoquia;
	}

	public void setOrinoquia(long orinoquia) {
		this.orinoquia = orinoquia;
	}

	public long getNivelNalApoyo() {
		return nivelNalApoyo;
	}

	public void setNivelNalApoyo(long nivelNalApoyo) {
		this.nivelNalApoyo = nivelNalApoyo;
	}

	public long getBogotaApoyo() {
		return bogotaApoyo;
	}

	public void setBogotaApoyo(long bogotaApoyo) {
		this.bogotaApoyo = bogotaApoyo;
	}

	public long getMedellinApoyo() {
		return medellinApoyo;
	}

	public void setMedellinApoyo(long medellinApoyo) {
		this.medellinApoyo = medellinApoyo;
	}

	public long getManizalesApoyo() {
		return manizalesApoyo;
	}

	public void setManizalesApoyo(long manizalesApoyo) {
		this.manizalesApoyo = manizalesApoyo;
	}

	public long getCaribeApoyo() {
		return caribeApoyo;
	}

	public void setCaribeApoyo(long caribeApoyo) {
		this.caribeApoyo = caribeApoyo;
	}

	public long getPalmiraApoyo() {
		return palmiraApoyo;
	}

	public void setPalmiraApoyo(long palmiraApoyo) {
		this.palmiraApoyo = palmiraApoyo;
	}

	public long getAmazoniaApoyo() {
		return amazoniaApoyo;
	}

	public void setAmazoniaApoyo(long amazoniaApoyo) {
		this.amazoniaApoyo = amazoniaApoyo;
	}

	public long getOrinoquiaApoyo() {
		return orinoquiaApoyo;
	}

	public void setOrinoquiaApoyo(long orinoquiaApoyo) {
		this.orinoquiaApoyo = orinoquiaApoyo;
	}

	public String getMensajeDistribucion() {
		return mensajeDistribucion;
	}

	public void setMensajeDistribucion(String mensajeDistribucion) {
		this.mensajeDistribucion = mensajeDistribucion;
	}

	public int getMaxIdConvocatoria() {
		return maxIdConvocatoria;
	}

	public void setMaxIdConvocatoria(int maxIdConvocatoria) {
		this.maxIdConvocatoria = maxIdConvocatoria;
	}

	public DataTable getTablaProyecto() {
		return tablaProyecto;
	}

	public void setTablaProyecto(DataTable tablaProyecto) {
		this.tablaProyecto = tablaProyecto;
	}

	public String getMensajeApoyos() {
		return mensajeApoyos;
	}

	public void setMensajeApoyos(String mensajeApoyos) {
		this.mensajeApoyos = mensajeApoyos;
	}

	public boolean isRecursos() {
		return recursos;
	}

	public void setRecursos(boolean recursos) {
		this.recursos = recursos;
	}

	public boolean isDistribucion() {
		return distribucion;
	}

	public void setDistribucion(boolean distribucion) {
		this.distribucion = distribucion;
	}

	public List getValorCiudades() {
		return valorCiudades;
	}

	public void setValorCiudades(List valorCiudades) {
		this.valorCiudades = valorCiudades;
	}

	public boolean isActivarGeneralidades() {
		return this.activarGeneralidades;
	}

	public boolean isActivarRequisitos() {
		return this.activarRequisitos;
	}

	public boolean isActivarRubros() {
		return this.activarRubros;
	}

	public boolean isActivarCompromisos() {
		return this.activarCompromisos;
	}

	public boolean isActivarProductos() {
		return this.activarProductos;
	}

	public boolean isActivarCriterios() {
		return this.activarCriterios;
	}

	public boolean isActivarRecursos() {
		return this.activarRecursos;
	}

	public void setActivarGeneralidades(boolean activarGeneralidades) {
		this.activarGeneralidades = activarGeneralidades;
	}

	public void setActivarRequisitos(boolean activarRequisitos) {
		this.activarRequisitos = activarRequisitos;
	}

	public void setActivarRubros(boolean activarRubros) {
		this.activarRubros = activarRubros;
	}

	public void setActivarCompromisos(boolean activarCompromisos) {
		this.activarCompromisos = activarCompromisos;
	}

	public void setActivarProductos(boolean activarProductos) {
		this.activarProductos = activarProductos;
	}

	public void setActivarCriterios(boolean activarCriterios) {
		this.activarCriterios = activarCriterios;
	}

	public void setActivarRecursos(boolean activarRecursos) {
		this.activarRecursos = activarRecursos;
	}

	public boolean isActivarGuardar() {
		return activarGuardar;
	}

	public void setActivarGuardar(boolean activarGuardar) {
		this.activarGuardar = activarGuardar;
	}

	public boolean isActivarSiguiente() {
		return activarSiguiente;
	}

	public void setActivarSiguiente(boolean activarSiguiente) {
		this.activarSiguiente = activarSiguiente;
	}

	public TipoRequisito getRequisito() {
		return requisito;
	}

	public void setRequisito(TipoRequisito requisito) {
		this.requisito = requisito;
	}

	public DataTable getTablaRequisitosAdicionados() {
		return tablaRequisitosAdicionados;
	}

	public void setTablaRequisitosAdicionados(DataTable tablaRequisitosAdicionados) {
		this.tablaRequisitosAdicionados = tablaRequisitosAdicionados;
	}

	public TipoCompromiso getCompromiso() {
		return compromiso;
	}

	public void setCompromiso(TipoCompromiso compromiso) {
		this.compromiso = compromiso;
	}

	public DataTable getTablaCompromisosAdicionados() {
		return tablaCompromisosAdicionados;
	}

	public void setTablaCompromisosAdicionados(DataTable tablaCompromisosAdicionados) {
		this.tablaCompromisosAdicionados = tablaCompromisosAdicionados;
	}

	public String getAvisoFechas() {
		return avisoFechas;
	}

	public void setAvisoFechas(String avisoFechas) {
		this.avisoFechas = avisoFechas;
	}

	public boolean isMostrarGuardarAvanzar() {
		return mostrarGuardarAvanzar;
	}

	public void setMostrarGuardarAvanzar(boolean mostrarGuardarAvanzar) {
		this.mostrarGuardarAvanzar = mostrarGuardarAvanzar;
	}

	public boolean isMostrarGuardarSalir() {
		return mostrarGuardarSalir;
	}

	public void setMostrarGuardarSalir(boolean mostrarGuardarSalir) {
		this.mostrarGuardarSalir = mostrarGuardarSalir;
	}

	public String getEsEdicion() {
		return esEdicion;
	}

	public void setEsEdicion(String esEdicion) {
		this.esEdicion = esEdicion;
	}

	public TabView getPanelTab() {
		return panelTab;
	}

	public void setPanelTab(TabView panelTab) {
		this.panelTab = panelTab;
	}

	public ModalidadFuenteFinanciacion getFuenteFinanciacionSeleccion() {
		return fuenteFinanciacionSeleccion;
	}

	public void setFuenteFinanciacionSeleccion(ModalidadFuenteFinanciacion fuenteFinanciacionSeleccion) {
		this.fuenteFinanciacionSeleccion = fuenteFinanciacionSeleccion;
	}

	public TipoRequisito getRequisitoSeleccionado() {
		return requisitoSeleccionado;
	}

	public void setRequisitoSeleccionado(TipoRequisito requisitoSeleccionado) {
		this.requisitoSeleccionado = requisitoSeleccionado;
	}

	public RubroFinanciable getRubroSeleccionado() {
		return rubroSeleccionado;
	}

	public void setRubroSeleccionado(RubroFinanciable rubroSeleccionado) {
		this.rubroSeleccionado = rubroSeleccionado;
	}

	public TipoCompromiso getCompromisoSeleccionado() {
		return compromisoSeleccionado;
	}

	public void setCompromisoSeleccionado(TipoCompromiso compromisoSeleccionado) {
		this.compromisoSeleccionado = compromisoSeleccionado;
	}

	public ProductoTipo getProductoNivel1Seleccionado() {
		return productoNivel1Seleccionado;
	}

	public void setProductoNivel1Seleccionado(ProductoTipo productoNivel1Seleccionado) {
		this.productoNivel1Seleccionado = productoNivel1Seleccionado;
	}

	public ProductoTipo getProductoNivel2Seleccionado() {
		return productoNivel2Seleccionado;
	}

	public void setProductoNivel2Seleccionado(ProductoTipo productoNivel2Seleccionado) {
		this.productoNivel2Seleccionado = productoNivel2Seleccionado;
	}

	public String getOpcionesTipoProducto() {
		return opcionesTipoProducto;
	}

	public void setOpcionesTipoProducto(String opcionesTipoProducto) {
		this.opcionesTipoProducto = opcionesTipoProducto;
	}

	public boolean isMostrarPgNuevoTipoProducto() {
		return mostrarPgNuevoTipoProducto;
	}

	public void setMostrarPgNuevoTipoProducto(boolean mostrarPgNuevoTipoProducto) {
		this.mostrarPgNuevoTipoProducto = mostrarPgNuevoTipoProducto;
	}

	public boolean isMostrarPgNuevoProducto() {
		return mostrarPgNuevoProducto;
	}

	public void setMostrarPgNuevoProducto(boolean mostrarPgNuevoProducto) {
		this.mostrarPgNuevoProducto = mostrarPgNuevoProducto;
	}

	public String getSelNivelTipoProducto() {
		return selNivelTipoProducto;
	}

	public void setSelNivelTipoProducto(String selNivelTipoProducto) {
		this.selNivelTipoProducto = selNivelTipoProducto;
	}

	public String getNombreProductoNivel1() {
		return nombreProductoNivel1;
	}

	public void setNombreProductoNivel1(String nombreProductoNivel1) {
		this.nombreProductoNivel1 = nombreProductoNivel1;
	}

	public String getNombreProductoNivel2() {
		return nombreProductoNivel2;
	}

	public void setNombreProductoNivel2(String nombreProductoNivel2) {
		this.nombreProductoNivel2 = nombreProductoNivel2;
	}

	public boolean isMostrarNivelProd1() {
		return mostrarNivelProd1;
	}

	public void setMostrarNivelProd1(boolean mostrarNivelProd1) {
		this.mostrarNivelProd1 = mostrarNivelProd1;
	}

	public boolean isMostrarNivelProd2() {
		return mostrarNivelProd2;
	}

	public void setMostrarNivelProd2(boolean mostrarNivelProd2) {
		this.mostrarNivelProd2 = mostrarNivelProd2;
	}

	public String getDescripcionProductoNivel1() {
		return descripcionProductoNivel1;
	}

	public void setDescripcionProductoNivel1(String descripcionProductoNivel1) {
		this.descripcionProductoNivel1 = descripcionProductoNivel1;
	}

	public String getDescripcionProductoNivel2() {
		return descripcionProductoNivel2;
	}

	public void setDescripcionProductoNivel2(String descripcionProductoNivel2) {
		this.descripcionProductoNivel2 = descripcionProductoNivel2;
	}

	public String getProductoNivelUnoSel() {
		return productoNivelUnoSel;
	}

	public void setProductoNivelUnoSel(String productoNivelUnoSel) {
		this.productoNivelUnoSel = productoNivelUnoSel;
	}

	public String getNombreCriterio() {
		return nombreCriterio;
	}

	public void setNombreCriterio(String nombreCriterio) {
		this.nombreCriterio = nombreCriterio;
	}

	public String getPesoCriterio() {
		return pesoCriterio;
	}

	public void setPesoCriterio(String pesoCriterio) {
		this.pesoCriterio = pesoCriterio;
	}

	public String getDescripcionCriterio() {
		return descripcionCriterio;
	}

	public void setDescripcionCriterio(String descripcionCriterio) {
		this.descripcionCriterio = descripcionCriterio;
	}

	public String getNombreProductoActual() {
		return nombreProductoActual;
	}

	public void setNombreProductoActual(String nombreProductoActual) {
		this.nombreProductoActual = nombreProductoActual;
	}

	public String getDescripcionProductoActual() {
		return descripcionProductoActual;
	}

	public void setDescripcionProductoActual(String descripcionProductoActual) {
		this.descripcionProductoActual = descripcionProductoActual;
	}

	public boolean isActivarInfoEspecifica() {
		return this.activarInfoEspecifica;
	}

	public void setActivarInfoEspecifica(boolean activarInfoEspecifica) {
		this.activarInfoEspecifica = activarInfoEspecifica;
	}

	public String getAvisoInfoEspecifca() {
		return avisoInfoEspecifca;
	}

	public void setAvisoInfoEspecifca(String avisoInfoEspecifca) {
		this.avisoInfoEspecifca = avisoInfoEspecifca;
	}

	public String getMostrarCampoUno() {
		return mostrarCampoUno;
	}

	public void setMostrarCampoUno(String mostrarCampoUno) {
		this.mostrarCampoUno = mostrarCampoUno;
	}

	public boolean isVisibleTextCampo1() {
		return visibleTextCampo1;
	}

	public void setVisibleTextCampo1(boolean visibleTextCampo1) {
		this.visibleTextCampo1 = visibleTextCampo1;
	}

	public String getTextoCampoUno() {
		return textoCampoUno;
	}

	public void setTextoCampoUno(String textoCampoUno) {
		this.textoCampoUno = textoCampoUno;
	}

	public String getMostrarCampoDos() {
		return mostrarCampoDos;
	}

	public void setMostrarCampoDos(String mostrarCampoDos) {
		this.mostrarCampoDos = mostrarCampoDos;
	}

	public boolean isVisibleTextCampo2() {
		return visibleTextCampo2;
	}

	public void setVisibleTextCampo2(boolean visibleTextCampo2) {
		this.visibleTextCampo2 = visibleTextCampo2;
	}

	public String getTextoCampoDos() {
		return textoCampoDos;
	}

	public void setTextoCampoDos(String textoCampoDos) {
		this.textoCampoDos = textoCampoDos;
	}

	public String getMostrarCampoTres() {
		return mostrarCampoTres;
	}

	public void setMostrarCampoTres(String mostrarCampoTres) {
		this.mostrarCampoTres = mostrarCampoTres;
	}

	public boolean isVisibleTextCampo3() {
		return visibleTextCampo3;
	}

	public void setVisibleTextCampo3(boolean visibleTextCampo3) {
		this.visibleTextCampo3 = visibleTextCampo3;
	}

	public String getTextoCampoTres() {
		return textoCampoTres;
	}

	public void setTextoCampoTres(String textoCampoTres) {
		this.textoCampoTres = textoCampoTres;
	}

	public String getMostrarCampoCuatro() {
		return mostrarCampoCuatro;
	}

	public void setMostrarCampoCuatro(String mostrarCampoCuatro) {
		this.mostrarCampoCuatro = mostrarCampoCuatro;
	}

	public boolean isVisibleTextCampo4() {
		return visibleTextCampo4;
	}

	public void setVisibleTextCampo4(boolean visibleTextCampo4) {
		this.visibleTextCampo4 = visibleTextCampo4;
	}

	public String getTextoCampoCuatro() {
		return textoCampoCuatro;
	}

	public void setTextoCampoCuatro(String textoCampoCuatro) {
		this.textoCampoCuatro = textoCampoCuatro;
	}

	public String getMostrarCampoCinco() {
		return mostrarCampoCinco;
	}

	public void setMostrarCampoCinco(String mostrarCampoCinco) {
		this.mostrarCampoCinco = mostrarCampoCinco;
	}

	public boolean isVisibleTextCampo5() {
		return visibleTextCampo5;
	}

	public void setVisibleTextCampo5(boolean visibleTextCampo5) {
		this.visibleTextCampo5 = visibleTextCampo5;
	}

	public String getTextoCampoCinco() {
		return textoCampoCinco;
	}

	public void setTextoCampoCinco(String textoCampoCinco) {
		this.textoCampoCinco = textoCampoCinco;
	}

	public String getMostrarCampoAsignaturas() {
		return mostrarCampoAsignaturas;
	}

	public void setMostrarCampoAsignaturas(String mostrarCampoAsignaturas) {
		this.mostrarCampoAsignaturas = mostrarCampoAsignaturas;
	}

	public String getMostrarCampoObjetivosResponsable() {
		return mostrarCampoObjetivosResponsable;
	}

	public void setMostrarCampoObjetivosResponsable(String mostrarCampoObjetivosResponsable) {
		this.mostrarCampoObjetivosResponsable = mostrarCampoObjetivosResponsable;
	}

	public String getMostrarCampoGrupos() {
		return mostrarCampoGrupos;
	}

	public void setMostrarCampoGrupos(String mostrarCampoGrupos) {
		this.mostrarCampoGrupos = mostrarCampoGrupos;
	}

	public String getMostrarLugarEjecucion() {
		return mostrarLugarEjecucion;
	}

	public void setMostrarLugarEjecucion(String mostrarLugarEjecucion) {
		this.mostrarLugarEjecucion = mostrarLugarEjecucion;
	}

	/**
	 * @return the esConvocatoriaProyectos
	 */
	public boolean isEsConvocatoriaProyectos() {
		return esConvocatoriaProyectos;
	}

	/**
	 * @param esConvocatoriaProyectos
	 *            the esConvocatoriaProyectos to set
	 */
	public void setEsConvocatoriaProyectos(boolean esConvocatoriaProyectos) {
		this.esConvocatoriaProyectos = esConvocatoriaProyectos;
	}

	/**
	 * @return the esConvocatoriaMovilidades
	 */
	public boolean isEsConvocatoriaMovilidades() {
		return esConvocatoriaMovilidades;
	}

	/**
	 * @param esConvocatoriaMovilidades
	 *            the esConvocatoriaMovilidades to set
	 */
	public void setEsConvocatoriaMovilidades(boolean esConvocatoriaMovilidades) {
		this.esConvocatoriaMovilidades = esConvocatoriaMovilidades;
	}

	/**
	 * @return the tipoModalidadConvocatoria
	 */
	public TipoModalidad getTipoModalidadConvocatoria() {
		return tipoModalidadConvocatoria;
	}

	/**
	 * @param tipoModalidadConvocatoria
	 *            the tipoModalidadConvocatoria to set
	 */
	public void setTipoModalidadConvocatoria(TipoModalidad tipoModalidadConvocatoria) {
		this.tipoModalidadConvocatoria = tipoModalidadConvocatoria;
	}

	/**
	 * @return the idRestriccionMovilidad
	 */
	public String getIdRestriccionMovilidad() {
		return idRestriccionMovilidad;
	}

	/**
	 * @param idRestriccionMovilidad
	 *            the idRestriccionMovilidad to set
	 */
	public void setIdRestriccionMovilidad(String idRestriccionMovilidad) {
		this.idRestriccionMovilidad = idRestriccionMovilidad;
	}

	/**
	 * @return the mostrarCamposMovilidadDocente
	 */
	public boolean isMostrarCamposMovilidadDocente() {
		return mostrarCamposMovilidadDocente;
	}

	/**
	 * @param mostrarCamposMovilidadDocente
	 *            the mostrarCamposMovilidadDocente to set
	 */
	public void setMostrarCamposMovilidadDocente(boolean mostrarCamposMovilidadDocente) {
		this.mostrarCamposMovilidadDocente = mostrarCamposMovilidadDocente;
	}

	/**
	 * @return the mostrarCamposMovilidadVisitante
	 */
	public boolean isMostrarCamposMovilidadVisitante() {
		return mostrarCamposMovilidadVisitante;
	}

	/**
	 * @param mostrarCamposMovilidadVisitante
	 *            the mostrarCamposMovilidadVisitante to set
	 */
	public void setMostrarCamposMovilidadVisitante(boolean mostrarCamposMovilidadVisitante) {
		this.mostrarCamposMovilidadVisitante = mostrarCamposMovilidadVisitante;
	}

	/**
	 * @return the mostrarCamposMovilidadEstPonencia
	 */
	public boolean isMostrarCamposMovilidadEstPonencia() {
		return mostrarCamposMovilidadEstPonencia;
	}

	/**
	 * @param mostrarCamposMovilidadEstPonencia
	 *            the mostrarCamposMovilidadEstPonencia to set
	 */
	public void setMostrarCamposMovilidadEstPonencia(boolean mostrarCamposMovilidadEstPonencia) {
		this.mostrarCamposMovilidadEstPonencia = mostrarCamposMovilidadEstPonencia;
	}

	/**
	 * @return the mostrarCamposMovilidadEstPasantia
	 */
	public boolean isMostrarCamposMovilidadEstPasantia() {
		return mostrarCamposMovilidadEstPasantia;
	}

	/**
	 * @param mostrarCamposMovilidadEstPasantia
	 *            the mostrarCamposMovilidadEstPasantia to set
	 */
	public void setMostrarCamposMovilidadEstPasantia(boolean mostrarCamposMovilidadEstPasantia) {
		this.mostrarCamposMovilidadEstPasantia = mostrarCamposMovilidadEstPasantia;
	}

	/**
	 * @return the mostrarCampoSeis
	 */
	public String getMostrarCampoSeis() {
		return mostrarCampoSeis;
	}

	/**
	 * @param mostrarCampoSeis
	 *            the mostrarCampoSeis to set
	 */
	public void setMostrarCampoSeis(String mostrarCampoSeis) {
		this.mostrarCampoSeis = mostrarCampoSeis;
	}

	/**
	 * @return the visibleTextCampo6
	 */
	public boolean isVisibleTextCampo6() {
		return visibleTextCampo6;
	}

	/**
	 * @param visibleTextCampo6
	 *            the visibleTextCampo6 to set
	 */
	public void setVisibleTextCampo6(boolean visibleTextCampo6) {
		this.visibleTextCampo6 = visibleTextCampo6;
	}

	/**
	 * @return the textoCampoSeis
	 */
	public String getTextoCampoSeis() {
		return textoCampoSeis;
	}

	/**
	 * @param textoCampoSeis
	 *            the textoCampoSeis to set
	 */
	public void setTextoCampoSeis(String textoCampoSeis) {
		this.textoCampoSeis = textoCampoSeis;
	}

	/**
	 * @return the campoUnoObligatorio
	 */
	public boolean isCampoUnoObligatorio() {
		return campoUnoObligatorio;
	}

	/**
	 * @param campoUnoObligatorio
	 *            the campoUnoObligatorio to set
	 */
	public void setCampoUnoObligatorio(boolean campoUnoObligatorio) {
		this.campoUnoObligatorio = campoUnoObligatorio;
	}

	/**
	 * @return the campoDosObligatorio
	 */
	public boolean isCampoDosObligatorio() {
		return campoDosObligatorio;
	}

	/**
	 * @param campoDosObligatorio
	 *            the campoDosObligatorio to set
	 */
	public void setCampoDosObligatorio(boolean campoDosObligatorio) {
		this.campoDosObligatorio = campoDosObligatorio;
	}

	/**
	 * @return the campoTrseObligatorio
	 */
	public boolean isCampoTresObligatorio() {
		return campoTresObligatorio;
	}

	/**
	 * @param campoTrseObligatorio
	 *            the campoTrseObligatorio to set
	 */
	public void setCampoTresObligatorio(boolean campoTresObligatorio) {
		this.campoTresObligatorio = campoTresObligatorio;
	}

	/**
	 * @return the campoCuatroObligatorio
	 */
	public boolean isCampoCuatroObligatorio() {
		return campoCuatroObligatorio;
	}

	/**
	 * @param campoCuatroObligatorio
	 *            the campoCuatroObligatorio to set
	 */
	public void setCampoCuatroObligatorio(boolean campoCuatroObligatorio) {
		this.campoCuatroObligatorio = campoCuatroObligatorio;
	}

	/**
	 * @return the campoCincoObligatorio
	 */
	public boolean isCampoCincoObligatorio() {
		return campoCincoObligatorio;
	}

	/**
	 * @param campoCincoObligatorio
	 *            the campoCincoObligatorio to set
	 */
	public void setCampoCincoObligatorio(boolean campoCincoObligatorio) {
		this.campoCincoObligatorio = campoCincoObligatorio;
	}

	/**
	 * @return the campoSeisObligatorio
	 */
	public boolean isCampoSeisObligatorio() {
		return campoSeisObligatorio;
	}

	/**
	 * @param campoSeisObligatorio
	 *            the campoSeisObligatorio to set
	 */
	public void setCampoSeisObligatorio(boolean campoSeisObligatorio) {
		this.campoSeisObligatorio = campoSeisObligatorio;
	}

	/**
	 * @return the activarArchivosMovilidad
	 */
	public boolean isActivarArchivosMovilidad() {
		return activarArchivosMovilidad;
	}

	/**
	 * @param activarArchivosMovilidad
	 *            the activarArchivosMovilidad to set
	 */
	public void setActivarArchivosMovilidad(boolean activarArchivosMovilidad) {
		this.activarArchivosMovilidad = activarArchivosMovilidad;
	}

	/**
	 * @return the avisoArchivosMovilidad
	 */
	public String getAvisoArchivosMovilidad() {
		return avisoArchivosMovilidad;
	}

	/**
	 * @param avisoArchivosMovilidad
	 *            the avisoArchivosMovilidad to set
	 */
	public void setAvisoArchivosMovilidad(String avisoArchivosMovilidad) {
		this.avisoArchivosMovilidad = avisoArchivosMovilidad;
	}

	/**
	 * @return the nombreTipoArchivoMovilidad
	 */
	public String getNombreTipoArchivoMovilidad() {
		return nombreTipoArchivoMovilidad;
	}

	/**
	 * @param nombreTipoArchivoMovilidad
	 *            the nombreTipoArchivoMovilidad to set
	 */
	public void setNombreTipoArchivoMovilidad(String nombreTipoArchivoMovilidad) {
		this.nombreTipoArchivoMovilidad = nombreTipoArchivoMovilidad;
	}

	/**
	 * @return the listaTipoArchivoMovilidad
	 */
	public List<TipoArchivoMovilidad> getListaTipoArchivoMovilidad() {
		return listaTipoArchivoMovilidad;
	}

	/**
	 * @param listaTipoArchivoMovilidad
	 *            the listaTipoArchivoMovilidad to set
	 */
	public void setListaTipoArchivoMovilidad(List<TipoArchivoMovilidad> listaTipoArchivoMovilidad) {
		this.listaTipoArchivoMovilidad = listaTipoArchivoMovilidad;
	}

	/**
	 * @return the listaMovilidadArchivo
	 */
	public List<MovilidadArchivo> getListaMovilidadArchivo() {
		return listaMovilidadArchivo;
	}

	/**
	 * @param listaMovilidadArchivo
	 *            the listaMovilidadArchivo to set
	 */
	public void setListaMovilidadArchivo(List<MovilidadArchivo> listaMovilidadArchivo) {
		this.listaMovilidadArchivo = listaMovilidadArchivo;
	}

	/**
	 * @return the esArchivoObligatorioMovilidad
	 */
	public Long getEsArchivoObligatorioMovilidad() {
		return esArchivoObligatorioMovilidad;
	}

	/**
	 * @param esArchivoObligatorioMovilidad
	 *            the esArchivoObligatorioMovilidad to set
	 */
	public void setEsArchivoObligatorioMovilidad(Long esArchivoObligatorioMovilidad) {
		this.esArchivoObligatorioMovilidad = esArchivoObligatorioMovilidad;
	}

	/**
	 * @return the idTipoMovilidadArchivo
	 */
	public Long getIdTipoMovilidadArchivo() {
		return idTipoMovilidadArchivo;
	}

	/**
	 * @param idTipoMovilidadArchivo
	 *            the idTipoMovilidadArchivo to set
	 */
	public void setIdTipoMovilidadArchivo(Long idTipoMovilidadArchivo) {
		this.idTipoMovilidadArchivo = idTipoMovilidadArchivo;
	}

	/**
	 * @return the registroProyectoLiderGrupo
	 */
	public String getRegistroProyectoLiderGrupo() {
		return registroProyectoLiderGrupo;
	}

	/**
	 * @param registroProyectoLiderGrupo
	 *            the registroProyectoLiderGrupo to set
	 */
	public void setRegistroProyectoLiderGrupo(String registroProyectoLiderGrupo) {
		this.registroProyectoLiderGrupo = registroProyectoLiderGrupo;
	}

	public String getMostrarIntegrantesSinDatos() {
		return mostrarIntegrantesSinDatos;
	}

	public void setMostrarIntegrantesSinDatos(String mostrarIntegrantesSinDatos) {
		this.mostrarIntegrantesSinDatos = mostrarIntegrantesSinDatos;
	}

	public String getMostarActividadesInvestigador() {
		return mostarActividadesInvestigador;
	}

	public void setMostarActividadesInvestigador(String mostarActividadesInvestigador) {
		this.mostarActividadesInvestigador = mostarActividadesInvestigador;
	}

	public String getMostrarEvaluacionesIndividuales() {
		return mostrarEvaluacionesIndividuales;
	}

	public void setMostrarEvaluacionesIndividuales(String mostrarEvaluacionesIndividuales) {
		this.mostrarEvaluacionesIndividuales = mostrarEvaluacionesIndividuales;
	}

	public String getTipoFinanciación() {
		return tipoFinanciación;
	}

	public void setTipoFinanciación(String tipoFinanciación) {
		this.tipoFinanciación = tipoFinanciación;
	}

	public String getMostrarProductos() {
		return mostrarProductos;
	}

	public void setMostrarProductos(String mostrarProductos) {
		this.mostrarProductos = mostrarProductos;
	}

	public String getMostrarBiodiversidad() {
		return mostrarBiodiversidad;
	}

	public void setMostrarBiodiversidad(String mostrarBiodiversidad) {
		this.mostrarBiodiversidad = mostrarBiodiversidad;
	}

	public String getMostarActivdadesIntegrantesSinDatos() {
		return mostarActivdadesIntegrantesSinDatos;
	}

	public void setMostarActivdadesIntegrantesSinDatos(String mostarActivdadesIntegrantesSinDatos) {
		this.mostarActivdadesIntegrantesSinDatos = mostarActivdadesIntegrantesSinDatos;
	}

	public List getListaRubroNivel3() {
		return listaRubroNivel3;
	}

	public void setListaRubroNivel3(List listaRubroNivel3) {
		this.listaRubroNivel3 = listaRubroNivel3;
	}

	public RubroFinanciable getRubroFinanciableActualNivel3() {
		return rubroFinanciableActualNivel3;
	}

	public void setRubroFinanciableActualNivel3(RubroFinanciable rubroFinanciableActualNivel3) {
		this.rubroFinanciableActualNivel3 = rubroFinanciableActualNivel3;
	}

}
