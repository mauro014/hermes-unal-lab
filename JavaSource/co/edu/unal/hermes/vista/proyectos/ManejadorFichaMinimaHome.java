package co.edu.unal.hermes.vista.proyectos;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.AreaTematica;
import co.edu.unal.hermes.modelo.AreaTematicaVista;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.CorteConvocatoria;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Dominio;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.ModalidadFuenteFinanciacion;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.PlanGlobalDesarrollo;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.RubroFinanciableArbol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.SemilleroLaboratorio;
import co.edu.unal.hermes.modelo.SolicitudFuente;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.ValoresListasProyecto;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.vista.aval.ManejadorAvalMenu;
import co.edu.unal.hermes.vista.proyectos.manejadorproyecto.ManejadorBibliografia;

/**
 * The Class ManejadorFichaMinimaHome.
 */
public class ManejadorFichaMinimaHome extends ManejadorFichaMinimaBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4435529172544469088L;

	public static final String MANEJADOR_FICHA_MINIMA_HOME_SESSION = "manejadorFichaMinimaHome";

	/** The Constant VAR_SESION_CONSULTA_FICHA. */
	private static final String VAR_SESION_CONSULTA_FICHA = "consultaFichaMinina";

	/** The Constant MENSAJE_PROYECTO_NO_GUARDADO. */
	private static final String MENSAJE_PROYECTO_NO_GUARDADO = "Su proyecto NO ha sido guardado";

	/** The clase items ppal. */
	// Items formulario en mismo orden
	private SelectItem[] claseItemsPpal = { new SelectItem("Externo", "Externo"),
			new SelectItem(Proyecto.PROYECTO_INTERNO, "Interno") };

	private SelectItem[] caracteristicaItemsPpal = { new SelectItem(Proyecto.PROYECTO, "Proyecto"),
			new SelectItem(Proyecto.PROGRAMA, "Programa") };

	/** The subtipo financiacion. */
	private Tipos subtipoFinanciacion;

	/** The resumen. */
	private UIComponent resumen;

	/** The objeto. */
	private UIComponent objeto;

	/** The ui entidades. */
	private UIComponent uiEntidades;

	/** The C B crear entidad. */
	private UIComponent CB_crearEntidad;

	/** The entidad coeje. */
	private UIComponent entidadCoeje;
	
	
	
	//labels registro unico
	private String labelFinanciacion = "Fuentes de Financiación";
	private String labelInformacionAcademica = "I. INFORMACIÓN ACADÉMICO - ADMINISTRATIVA";
	private String labelInformacionGeneral = "Información general";
	
	private String nombreProyectoAsociado;
	
	
	/** The mostrar si guardar finalizar. */
	private boolean mostrarSiGuardarFinalizar = false;

	/** The palabra clave tabla. */
	private PalabraClave palabraClaveTabla;

	/** The participante. */
	InvestigadorProyecto participante = new InvestigadorProyecto();

	/** The tipo fuente actual. */
	private int tipoFuenteActual; // TIPO DE FUENTE DE FINANCIACION

	/** The fuente actual int id. */
	private String fuenteActualIntId;

	/** The fuente actual ext id. */
	private String fuenteActualExtId;

	/** The requiere otra fuente. */
	private boolean requiereOtraFuente;

	/** The requiere otra entidad. */
	private boolean requiereOtraEntidad;

	/** The nombre fuente. */
	private String nombreEntidad;

	/** The nit fuente. */
	private String nitEntidad;

	/** The naturaleza fuente. */
	private String naturalezaEntidad;

	/** The pais fuente. */
	private String paisEntidad;

	/** The caracter fuente. */
	private String caracterEntidad;

	/** The tipo fuente. */
	private String tipoEntidad;

	/** The telefono fuente. */
	private String telefonoEntidad;

	/** The direccion fuente. */
	private String direccionEntidad;

	/** The tipos naturaleza fuente item. */
	public SelectItem[] tiposNaturalezaFuenteItem;

	/** The tipos tiposFuenteFinanciacionItem. */
	public SelectItem[] tiposFuenteFinanciacionItem;

	/** The tipos caracterFuenteFinanciacionItem. */
	public SelectItem[] caracterFuenteFinanciacionItem;

	/** The entidad eliminar. */
	private Financiacion entidadEliminar;

	/** The mas entidades coejecutoras. */
	private boolean masEntidadesCoejecutoras = false;

	/** The entidad seleccionada. */
	private String entidadSeleccionada;

	/** The entidad items. */
	private List<SelectItem> entidadItems;

	/** The entidad coejecutora. */
	private String entidadCoejecutora;

	/** The monto entidad coejecutora. */
	private Long montoEntidadCoejecutora = 0L;

	/** The monto entidad coejecutora especie. */
	private Long montoEntidadCoejecutoraEspecie = 0L;

	/** The contrapartida personal. */
	private Long contrapartidaPersonal = 0L;

	/** The valor especie. */
	private Long valorEspecie;

	/** The linea accion. */
	private String lineaAccion;

	/** The programa. */
	private String programa;
	
	private String componentePlanDesarrolloNombre;

	/** The vercod linea accion. */
	private boolean vercodLineaAccion;

	/** The vercod programa. */
	private boolean vercodPrograma;

	/** The mostrar contrapartida. */
	private boolean mostrarContrapartida;

	/** The modalidad fuente financiacion generico interna. */
	private ModalidadFuenteFinanciacion modalidadFuenteFinanciacionGenericoInterna;

	/** The modalidad fuente financiacion generico externa. */
	private ModalidadFuenteFinanciacion modalidadFuenteFinanciacionGenericoExterna;
	
	private boolean tieneFinanciacionGenerica;

	/**
	 * Instantiates a new manejador ficha minima home.
	 */
	// Constructor
	public ManejadorFichaMinimaHome() {

		super();

		activarVistaFormulariosInicial();

		// Cargar info basica.
		cargarConvocatoriaActual();
		cargarListas();
		obtenerListaDepartamentosRegionImpacto();
		obtenerListaCiudadesRegionImpacto();
		modalidadFuenteFinanciacionGenericoInterna = null;
		modalidadFuenteFinanciacionGenericoExterna = null;
		tieneFinanciacionGenerica=false;

		esConsulta = false;
		setAsignarGrupo(false);

		if (mostrarMenuFormulario && !esTiempoVolver) {
			cargarRubrosModalidad();
		}

		// Se carga variable que indica si es consulta.
		if (sesion.getAttribute(VAR_SESION_CONSULTA_FICHA) != null) {
			esConsulta = (Boolean) sesion.getAttribute(VAR_SESION_CONSULTA_FICHA);
			sesion.removeAttribute(VAR_SESION_CONSULTA_FICHA);
		}

		Grupo gSel = (Grupo) sesion.getAttribute("grupoJI");
		String idBancoProblemas = (String) sesion.getAttribute("idProblemaInnovacionSocial2018");

		@SuppressWarnings("unchecked")
		List<Grupo> listaGruposAlianzas = (List<Grupo>) sesion.getAttribute("gruposAlianzas");

		Long idProyecto = null;

		// TODO: Creo que esto se puede borrar.
		Boolean esProyectoFichaNueva = (Boolean) sesion.getAttribute("esProyectoFichaMinimaNueva");

		if (esProyectoFichaNueva != null && esProyectoFichaNueva) {
			Proyecto proyectoNuevo = (Proyecto) sesion.getAttribute("proyectoFichaMinimaNueva");
			idProyecto = proyectoNuevo.getId();
		} else {
			ProyectoVista proyecto = (ProyectoVista) sesion.getAttribute("proyectoFichaMinina");
			if (proyecto != null) {
				idProyecto = proyecto.getId();
			}
		}

		if (idProyecto == null) {
			Set s = proyectoActual.getSemilleros();
			proyectoActual = new Proyecto();
			proyectoActual.setDuracion(0);
			proyectoActual.setModalidad(convocatoriaActual);
			proyectoActual.cambiarEstado(EstadoProyecto.INGRESANDO);
			proyectoActual.setSemilleros(s);
			proyectoActual.setFase(0);
			proyectoActual.setTienePryAsociado(false);
			
			if(convocatoriaActual.getPadre().getEsConvocatoriaProyectos2022_4() 
					|| convocatoriaActual.getPadre().getEsConvocatoriaAlianzas2022_4()
					|| convocatoriaActual.getPadre().getEsConvocatoriaExcelencia2022_4()
					|| esConvocatoriaRedes || convocatoriaActual.getPadre().getEsConvocatoriaLaboratorios2023() || esConvocatoriaPlanArmonizacion ) {
				proyectoActual.setCodLineaAccion(7116); //Armonización
				cambiarPrograma();
				proyectoActual.setCodPrograma(6854); //
			}

			Long corteId = (Long) sesion.getAttribute(CorteConvocatoria.ID_CORTE_CONVOCATORIA_SESSION);
			if (corteId != null) {
				CorteConvocatoria corteConvocatoria = new CorteConvocatoria(corteId);
				proyectoActual.setCorteConvocatoria(corteConvocatoria);
				sesion.removeAttribute(CorteConvocatoria.ID_CORTE_CONVOCATORIA_SESSION);
			}

			if (gSel != null) {
				proyectoActual.adicionarGrupo(gSel);
			}

			if (listaGruposAlianzas != null) {
				for (int i = 0; i < listaGruposAlianzas.size(); i++) {
					Grupo g = listaGruposAlianzas.get(i);
					proyectoActual.adicionarGrupo(g);
				}
			}

			if (idBancoProblemas != null) {
				proyectoActual.setAsignatura(idBancoProblemas);
			}

			if (mostrarMenuFormulario) {
				this.proyectoActual.setTipoActividad("FM_PINV");
			}

		} else {

			boolean incluirGastos = true;
			proyectoActual = servicioProyecto.obtenerProyecto(idProyecto, ProyectoDAOHibernate.TODO_POR_ID,
					incluirGastos);

			unidadEjecutora = proyectoActual.getLugar();

//			if (proyectoActual.getTienePryAsociado() == null) {
//				proyectoActual.setTienePryAsociado(false);
//			}

			if (proyectoActual.getTienePryAsociado() == null
					|| (proyectoActual.getTienePryAsociado() != null && !proyectoActual.getTienePryAsociado())) {
				proyectoActual.setTienePryAsociado(false);
			} else {
				proyectoActual.setTienePryAsociado(true);
			}

			// Se carga en el formulario si es interno o externo.
			cambiarClaseProyecto();

			try {
				sedeSel = proyectoActual.getSedeEjecucion().getId().toString();
			} catch (Exception e) {
				sedeSel = "";
			}

			// Se cargan areas tematicas
			if (proyectoActual.getId() != null) {
				cargarAreaPrincipal();
				cargarAreasTematicasSecundarias();
			}

			// se cargan objetivos de desarrollo sostenible
			cargarObjetivoDesarrolloSosteniblePrincipal();
			cargarObjetivoDesarrolloSostenibleSecundarios();

			// lineasAccion
			if (proyectoActual.getCodLineaAccion() != null && proyectoActual.getCodLineaAccion() != 0) {
				List<DominioDetalle> listaAccion = new ArrayList<DominioDetalle>();
				listaAccion = servicioGeneral.obtenerObjetos(
						"select dd from DominioDetalle dd, Dominio d where d.id = dd.identificador.id and dd.identificador.tipo ='"
								+ proyectoActual.getCodLineaAccion() + "' and d.tipo ='" + DOMINIO_LINEA_ACCION + "' ");
				DominioDetalle dd = (DominioDetalle) listaAccion.get(0);
				lineaAccion = dd.getDescripcion();
				if (lineaAccion != null && esConsulta)
					vercodLineaAccion = true;
				// Politica
				List<PlanGlobalDesarrollo> listaPolitica = new ArrayList<PlanGlobalDesarrollo>();
				listaPolitica = servicioGeneral.obtenerObjetos(
						"select dd from PlanGlobalDesarrollo dd where dd.periodo = '" + dd.getObservacion() + "' ");
				PlanGlobalDesarrollo plan = (PlanGlobalDesarrollo) listaPolitica.get(0);
				politica = plan.getNombre();
				if(plan.getId()==24) {
					etiquetas2025PlanDllo = true;
				}else {
					etiquetas2025PlanDllo = false;
				}

				cambiarPrograma();

				// Programa
				if (proyectoActual.getCodPrograma() != null && proyectoActual.getCodPrograma() != 0) {
					List listaProg = new ArrayList<DominioDetalle>();
					listaProg = servicioGeneral.obtenerObjetos(
							"select dd from DominioDetalle dd, Dominio d where d.id = dd.identificador.id and dd.identificador.tipo ='"
									+ proyectoActual.getCodPrograma() + "' and d.tipo ='" + DOMINIO_PROGRAMA + "' ");
					dd = (DominioDetalle) listaProg.get(0);
					programa = dd.getDescripcion();
					if (programa != null && esConsulta) {
						vercodPrograma = true;
					}
				}
				
				// Componente
				if (proyectoActual.getComponentePlanDesarrollo() != null && proyectoActual.getComponentePlanDesarrollo() != 0) {
					List listaComp = new ArrayList<PlanGlobalDesarrollo>();
					listaComp = servicioGeneral.obtenerObjetos(
							"select p from PlanGlobalDesarrollo p where p.id = '"
									+ proyectoActual.getComponentePlanDesarrollo() + "' ");
					PlanGlobalDesarrollo pp = (PlanGlobalDesarrollo) listaComp.get(0);
					componentePlanDesarrolloNombre = pp.getNombre();
					
				}

				// Inicializar arreglo con lista labs original
				listaLaboratoriosOriginal = (ArrayList<Laboratorio>) proyectoActual.getListaLaboratorios();
			}
		}
		
		if(convocatoriaActual.getEsProyectoObligagorio()!=null && convocatoriaActual.getEsProyectoObligagorio().equals("Y")) {
			proyectoActual.setTienePryAsociado(true);
			buscarProyectoAsociado();
		}

		// Si tiene financiación avanzada.
		if (convocatoriaActual.getTipoFinanciacion() != null
				&& convocatoriaActual.getTipoFinanciacion().equals(Convocatoria.TIPO_FINANCIACION_MINIMA)) {

			List<ModalidadFuenteFinanciacion> lista = servicioModalidad
					.listaModFuenteFinXModalidad(convocatoriaActual.getId());

			if (!esListaVacia(lista)) {

				// Se valida si el unico arbol que tiene la fuente de
				// financiación es el de tipo sin financiacion,
				// eso quiere decir que todas las fuentes agregadas deben tener
				// un arbol generico.
				if (lista.size() <= 2) {

					Iterator<ModalidadFuenteFinanciacion> i = lista.iterator();

					while (i.hasNext()) {

						ModalidadFuenteFinanciacion modalidadFuenteFinanciacion = i.next();
						if (modalidadFuenteFinanciacion.getFuenteFinanciacion().getId()
								.equals(FuenteFinanciacion.FINANCIACION_GENERICA_INTERNA)) {
							modalidadFuenteFinanciacionGenericoInterna = modalidadFuenteFinanciacion;
							tieneFinanciacionGenerica=true;
							tipoFuenteActual=1;
						} else if (modalidadFuenteFinanciacion.getFuenteFinanciacion().getId()
								.equals(FuenteFinanciacion.FINANCIACION_GENERICA_EXTERNA)) {
							modalidadFuenteFinanciacionGenericoExterna = modalidadFuenteFinanciacion;
							tieneFinanciacionGenerica=true;
							tipoFuenteActual=2;
						}
					}

				}

				if (!convocatoriaActual.isPermitirCrearFinanciacion()
						&& proyectoActual.getListaFuentesFinancacionFicha().isEmpty()) {

					Iterator<ModalidadFuenteFinanciacion> i = lista.iterator();
					while (i.hasNext()) {
						ModalidadFuenteFinanciacion modalidadFuenteFinanciacion = i.next();
						ingresarFuenteUniversidad(modalidadFuenteFinanciacion.getFuenteFinanciacion(),
								modalidadFuenteFinanciacion);
					}
				}
				
				if(convocatoriaActual.isPermitirCrearFinanciacion() && proyectoActual.getListaFuentesFinancacionFicha().isEmpty() 
						&& !convocatoriaActual.getId().equals(Convocatoria.MODALIDAD_FICHA_MINIMA_ID) && !convocatoriaActual.getId().equals(Convocatoria.MODALIDAD_CONVOCATORIA_EXTERNA)) {
					Iterator<ModalidadFuenteFinanciacion> i = lista.iterator();
					while (i.hasNext()) {
						ModalidadFuenteFinanciacion modalidadFuenteFinanciacion = i.next();
						if(!modalidadFuenteFinanciacion.getFuenteFinanciacion().getId().toString().equals(FuenteFinanciacion.FINANCIACION_GENERICA_EXTERNA) && 
								!modalidadFuenteFinanciacion.getFuenteFinanciacion().getId().toString().equals(FuenteFinanciacion.FINANCIACION_GENERICA_INTERNA)) {
								ingresarFuenteUniversidad(modalidadFuenteFinanciacion.getFuenteFinanciacion(),
								modalidadFuenteFinanciacion);
						}
						
					}
				}
			}
			

			Iterator<Financiacion> i = proyectoActual.getListaFuentesFinancacionFicha().iterator();

			// Se crean los treeNode de las fuentes de financiación.
			while (i.hasNext()) {
				Financiacion f = i.next();
				f.setRubrosFinanciableArbol(obtenerRubroFinanciableArbol(f.getModalidadFuenteFinanciacion()));
				if ((f.getModalidadFuenteFinanciacion() == null && proyectoActual.getId() != null)
						|| (f.getRubrosFinanciableArbol() == null || f.getRubrosFinanciableArbol().isEmpty())) {
					ModalidadFuenteFinanciacion mff = new ModalidadFuenteFinanciacion();
					mff.setId(889L);
					mff.setArbol(889L);					if (f.getFuente().getInternaExterna().equals(FuenteFinanciacion.externa)
							|| f.getFuente().getInternaExterna().equals(FuenteFinanciacion.OCULTA_EXTERNA)) {
						mff.setIncluirContrapartida(true);
					}
					f.setRubrosFinanciableArbol(obtenerRubroFinanciableArbol(mff));
				}
				f.construirTreeGasto();
				f.actualizarValoresTreeTableDesdeGastos();
			}

			cargarListaFuentesEspecie();
			calcularTotalesFicha();

		} else if (convocatoriaActual.getTipoFinanciacion() == null
				|| convocatoriaActual.getTipoFinanciacion().equals(Convocatoria.TIPO_FINANCIACION_BASICA)) {
			// Si tiene financiación normal.
			// cargar fuentes financieras
			if (proyectoActual.getFinanciaciones() == null

					|| (proyectoActual.getFinanciaciones() != null && proyectoActual.getFinanciaciones().isEmpty())) {

				List<FuenteFinanciacion> lf = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class,
						"select e from FuenteFinanciacion e where e.id = 1");

				if (!esListaVacia(lf)) {
					FuenteFinanciacion ff = lf.get(0);
					ingresarFuenteUniversidad(ff, null);
				}
			}
		}

		if (convocatoriaActual.getVigenciaPredeterminada() != null
				&& !convocatoriaActual.getVigenciaPredeterminada().equals(-1L)
				&& proyectoActual.getVigencia() == null) {
			proyectoActual.setVigencia(convocatoriaActual.getVigenciaPredeterminada());
		}

		sesion.setAttribute("proyecto", proyectoActual);
		sesion.removeAttribute("manejadorProyectosInvestigador");
		sesion.removeAttribute("manejadorActividades");
		sesion.removeAttribute("manejadorArchivos");
		sesion.removeAttribute(ManejadorBibliografia.MANEJADOR_BIBLIOGRAFIA_SESSION);
		sesion.removeAttribute("manejadorDatosBasicos");
		sesion.removeAttribute("manejadorDetallesFinancieros");
		sesion.removeAttribute("manejadorEvaluadores");
		sesion.removeAttribute("manejadorFuentesFinancieras");
		sesion.removeAttribute("manejadorInformacionEspecifica");
		sesion.removeAttribute("manejadorInvestigadores");
		sesion.removeAttribute("manejadorLineas");
		sesion.removeAttribute("manejadorObjetivosResultados");
		sesion.removeAttribute("manejadorRubros");
		sesion.removeAttribute("manejadorVigencias");
		sesion.removeAttribute(ManejadorMenuFormularios.MANEJADOR_MENU_FORMULARIOS_SESSION);

		if (esProyectoLaboratorios) {
			mostrarBiodiversidad = false;
		}

		if (convocatoriaActual.getPadre().getTipoConvocatoria()
				.equals(ConvocatoriaPadre.TIPO_CONVOCATORIA_LABORATORIOS)) {
			proyectoActual.setTieneLaboratorios("Si");
			labelFinanciacion = "Sede donde se ejecutarán los recursos";
		}
		
		if (esConvocatoriaRedes) {
			labelInformacionAcademica = "I. INFORMACIÓN ACADÉMICO - ADMINISTRATIVA DE LA RED";
			labelInformacionGeneral = "Información general de la PROPUESTA COLECTIVA DE INVESTIGACIÓN que desarrollará la RED";
		}

		if (esConvExtSol2018) {
			if (proyectoActual.getDependenciaPresentacion() != null) {
				sedeSelPres = proyectoActual.getDependenciaPresentacion().getSede().getId().toString();
				cambiarSedePres();
				if ((new Sede(sedeSelPres)).isEsSedePresenciaNacional()) {
					dependenciaPresProyecto = proyectoActual.getDependenciaPresentacion().getId();
				} else {
					setFacultadSelPres(proyectoActual.getDependenciaPresentacion().getFacultad().getId());
					cambiarFacultadPres();
					dependenciaPresProyecto = proyectoActual.getDependenciaPresentacion().getId();
				}
			}
		}
		if (proyectoActual.getModalidad().getId().equals(912L) || esConvCP2019) {
			proyectoActual.setTienePryAsociado(true);
		}
		
		//Se agrega item al selectItem de tipo de programa o proyecto para incluir aquellos tipos inactivos que se requieren para consulta histórica
		if(esConsulta) {
			categoriaItemsPpal = null;
			categoriaItemsPpal = crearListaItemDominioDetalle(DOMINIO_CATEGORIA,false);
		}
		
		Investigador invesPry = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
		if (!esNulo(invesPry) && personaActual.getId().equals(invesPry.getId())) {
			esDirector = true;
		}
	}// Fin constructor

	/**
	 * Obtener rubro financiable arbol.
	 *
	 * @param modalidadFuenteFinanciacion the modalidad fuente financiacion
	 * @return the list
	 */
	private List<RubroFinanciableArbol> obtenerRubroFinanciableArbol(
			ModalidadFuenteFinanciacion modalidadFuenteFinanciacion) {
		if (modalidadFuenteFinanciacion != null) {
			return servicioModalidad.getRubroFinanciableArbol(modalidadFuenteFinanciacion.getArbol());
		}
		return null;
	}

	/******************** PROYECTOS LABS *******************/

	public void agregarLab() {
		if (StringUtils.isNotEmpty(getLabSeleccionado())) {
			boolean existe = false;
			for (Laboratorio s : (ArrayList<Laboratorio>) proyectoActual.getListaLaboratorios()) {
				if (s.getId().toString().equals(getLabSeleccionado())) {
					existe = true;
					generarMsgModal(2, "El Laboratorio ya se encuentra vinculado.");
					break;
				}
			}
			if (!existe) {
				Laboratorio l = servicioGeneral
						.obtenerObjetos(Laboratorio.class, "from Laboratorio s where s.id=" + getLabSeleccionado())
						.get(0);
				proyectoActual.adicionarLaboratorio(l);
			}
		} else {
			generarMsgModal(2, "Por favor seleccione la sede, la facultad y el laboratorio");
		}
	}

	public void eliminarLab() {
		proyectoActual.borrarLaboratorio(labEliminar);
	}

	/* LUGARES DE EJECUCION */
	public void agregarLugarEjecucion() {
		if (StringUtils.isNotEmpty(unidadEjecutora)) {
			boolean existe = false;
			for (Dependencia s : (ArrayList<Dependencia>) proyectoActual.getListaLugaresEjecucion()) {
				if (s.getId().toString().equals(unidadEjecutora)) {
					existe = true;
					generarMsgModal(2, "El lugar de ejecución ya se encuentra vinculado.");
					break;
				}
			}
			if (!existe) {
				String hql = "select s from Dependencia s where s.id='" + unidadEjecutora + "'";
				Dependencia l = servicioGeneral.obtenerObjetos(Dependencia.class, hql).get(0);
				proyectoActual.adicionarLugarEjecucion(l);
			}
		} else {
			generarMsgModal(2, "Por favor seleccione la dependencia de ejecución");
		}
	}

	public void eliminarLugarEjecucion() {
		proyectoActual.borrarLugarEjecucion(lugarEjecEliminar);
	}

	protected void generarMsgModal(int tipo, String mensaje) {
		switch (tipo) {
		case 1:
			FacesContext.getCurrentInstance().addMessage("msgsModal",
					new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null));
			break;
		case 2:
			FacesContext.getCurrentInstance().addMessage("msgsModal",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
			break;
		}
	}

	/******************** PROYECTOS LABS *******************/

	/**
	 * Metodo para cargar las areas temáticas ya guardadas.
	 */
	private void cargarAreaPrincipal() {

		// Cargar area ciencia principal hija
		List<AreaTematica> listaAreasPrimHija = proyectoActual.getAreasTematicasNivel(1L);

		if (!esListaVacia(listaAreasPrimHija)) {
			AreaTematica areaTematicaGuardada = listaAreasPrimHija.get(0);
			areaCiencia = areaTematicaGuardada.getProyectoAreaTematica().getEstado();
			cambiarArea();
			cambiarAreaSec();
			subAreaCiencia = areaTematicaGuardada.getProyectoAreaTematica().getIdentificador().getTipo();
		}

	}

	/**
	 * Cargar areas tematicas secundarias.
	 */
	private void cargarAreasTematicasSecundarias() {

		List<AreaTematica> listaAreasSec = proyectoActual.getAreasTematicasNivel(2L);

		if (!esListaVacia(listaAreasSec)) {

			for (int i = 0; i < listaAreasSec.size(); i++) {

				AreaTematica areaTematicaSecundaria = (AreaTematica) listaAreasSec.get(i);

				// Consulta para obtener el padre.
				List<DominioDetalle> listaDomDetarUno = servicioGeneral.obtenerObjetos(DominioDetalle.class,
						"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
								+ DOMINIO_AREA_CIENCIA + "' and dd.identificador.tipo = '"
								+ areaTematicaSecundaria.getProyectoAreaTematica().getEstado() + "'");
				if (!esListaVacia(listaDomDetarUno)) {
					DominioDetalle areaSecundariaPadre = (DominioDetalle) listaDomDetarUno.get(0);

					// Se crea objeto de vista con la información cargada.
					AreaTematicaVista areaTematicaGuardada = new AreaTematicaVista();
					areaTematicaGuardada.setProyecto(proyectoActual);
					areaTematicaGuardada.setNombreArea(areaSecundariaPadre.getDescripcion());
					areaTematicaGuardada.setAreaTematica(areaSecundariaPadre);
					areaTematicaGuardada
							.setNombreSubArea(areaTematicaSecundaria.getProyectoAreaTematica().getDescripcion());
					areaTematicaGuardada.setSubAreaTematica(areaTematicaSecundaria.getProyectoAreaTematica());
					areaTematicaGuardada.setTipo(2L);
					areaTematicaGuardada.setAreaTematicaProyecto(areaTematicaSecundaria);
					areaTematicaGuardada.setId(areaTematicaSecundaria.getId());

					listaAreasTematicas.add(areaTematicaGuardada);
				}
			}
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
		// servicioGeneral.obtenerObjetos(ValoresListasProyecto.class, "select e
		// from ValoresListasProyecto e where e.proyecto.id = " +
		// proyectoActual.getId() + " and e.descripcion =
		// 'OBJETIVO_DESARROLLO_SOSTENIBLE_SECUNDARIO' and e.tipo = '" +
		// DOMINIO_OBJETIVOS_DESARROLLO_SOSTENIBLE + "'");
		valoresObjetivosDesarrolloSostenible = proyectoActual
				.getObjetivoDesarrolloSostenibleTipo("OBJETIVO_DESARROLLO_SOSTENIBLE_SECUNDARIO");
		if (!esListaVacia(valoresObjetivosDesarrolloSostenible)) {
			objetivoDesarrolloSostenibleSecundario = valoresObjetivosDesarrolloSostenible.get(0).getValor();
		}
	}

	/**
	 * Metodo que cambia los elementos visuales cuando se cambia de interno a
	 * externo.
	 */
	public void cambiarClaseProyecto() {
		if ((proyectoActual.getSubTipoActividadECP() != null
				&& proyectoActual.getSubTipoActividadECP().equals(Proyecto.PROYECTO_INTERNO))
				|| (proyectoActual.getRolUniversidad() != null
						&& proyectoActual.getRolUniversidad().equals(Proyecto.ROL_UNIVERSIDAD_PARTICIPANTE))) {
			esInterno = true;
			tipoFuenteActual = 1;
		} else {
			esInterno = false;
			tipoFuenteActual = 0;
		}
	}

	/**
	 * Metodo en el que se validan los campos minimos para guardar el proyecto.
	 *
	 * @return true, if successful
	 */
	public boolean validarDatosMinimosGuardarParcial() {
		boolean valido = true;

		// Se valida que se registre mínimo el nombre del proyecto.
		if (esCadenaVacia(proyectoActual.getNombre()) || "--".equals(proyectoActual.getNombre())) {
			valido = false;
			mensajeError("Por favor registre el nombre del proyecto");
		}

		// dgbenitezc
		if (!esArrayVacio(siTiposProyLabs) && proyectoActual.getTipoProyectoLaboratorios() == null) {
			mensajeError("form:panelUnoInfAcad:tipoProyectoLaboratorios",
					"Por favor seleccione el tipo de proyecto de Laboratorios.");
		}
		if (!esArrayVacio(siSubtiposProyLabs) && proyectoActual.getSubtipoProyectoLaboratorios() == null) {
			mensajeError("form:panelUnoInfAcad:subtipoProyectoLaboratorios",
					"Por favor seleccione el subtipo de proyecto de Laboratorios.");
		}

		// Se que se haya ingresado la duración del proyecto.
		double duracionTotalMeses = (proyectoActual.getDuracion() != null && proyectoActual.getDuracion() > 0)
				? proyectoActual.getDuracion() : 0.0;

		if (duracionTotalMeses <= 0) {
			valido = false;
			mensajeError(buscarDirector, "Por favor ingrese la duración del proyecto.");
		}

		// Se valida que el valor ingresado en el campo de duración sea
		// correcto.
		String sDura = String.valueOf(proyectoActual.getDuracion());
		if (valido && (sDura.length() > 3 || proyectoActual.getDuracion() <= 0)) {
			valido = false;
			if (mostrarMenuFormulario) {
				mensajeError(
						"La duración del proyecto debe ser mayor a 0 y no superar la duración máxima permitida en la convocatoria.");
			} else {
				mensajeError("La duración del proyecto debe ser de máximo 3 dígitos y mayor a 0");
			}
		}

		// Se valida que si es de convocatoria el valor ingresado sea correcto.
		if (mostrarMenuFormulario && !esFichaExterna
				&& proyectoActual.getDuracion() > convocatoriaActual.getTiempoEjecucionProyecto()) {
			valido = false;
			mensajeError("La duración del proyecto supera lo permitido");
		}

		// Se valida que se agregue minimo el director.
		if (esListaVacia(proyectoActual.getListaInvestigadorPrincipal())) {
			valido = false;
			mensajeError("Por favor registre la información del director del proyecto");
		}
		
		if(convocatoriaActual.getNumeroLaboratoriosValidacion()!=null && convocatoriaActual.getNumeroLaboratoriosValidacion()>0) {
			if(proyectoActual.getListaLaboratorios()==null || (proyectoActual.getListaLaboratorios()!=null && proyectoActual.getListaLaboratorios().size()<convocatoriaActual.getNumeroLaboratoriosValidacion())) {
				valido = false;
				mensajeError("Recuerde que debe agregar mínimo "+convocatoriaActual.getNumeroLaboratoriosValidacion()+" laboratorios.");
			}
		}
		
		if(convocatoriaActual.getPadre().getEsConvocatoriaLaboratorios2024() || esConvocatoriaFortalecimientoLabs2024_M1) {
			if(proyectoActual.getListaLaboratorios()!=null && proyectoActual.getListaLaboratorios().size()<convocatoriaActual.getNumeroLaboratoriosValidacion()) {
				valido = false;
				mensajeError("Recuerde que debe agregar mínimo "+convocatoriaActual.getNumeroLaboratoriosValidacion()+" laboratorios.");
			} /*else {
				boolean isPresencia = false;
				for(int i=0; i<proyectoActual.getListaLaboratorios().size();i++) {
					Laboratorio lab = (Laboratorio) proyectoActual.getListaLaboratorios().get(i);
					if(lab.getSede().isEsSedePresenciaNacional()) {
						isPresencia = true;	
						break;
					}
				}
				if(!isPresencia) {
					valido = false;
					mensajeError("Recuerde que la propuesta debe contar con la participación de al menos un laboratorio de Sede de Presencia Nacional. Por favor revise la información.");
				}
			}*/
		}
		

	return valido;
	}

	/**
	 * Ingresar fuente universidad.
	 *
	 * @param ff                          the ff
	 * @param modalidadFuenteFinanciacion the modalidad fuente financiacion
	 */
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

	/**
	 * Limpiar.
	 */
	public void limpiar() {
		investigadorExterno = new InvestigadorExterno();
		cambiarVinculacion();
	}

	/**
	 * Agregar entidad coejecutora.
	 */
	public void agregarEntidadCoejecutora() {

		// Se valida que se hayan ingresado los valores
		if (montoEntidadCoejecutora == null || montoEntidadCoejecutora < 0) {
			montoEntidadCoejecutora = 0L;
		}
		if (montoEntidadCoejecutoraEspecie == null || montoEntidadCoejecutoraEspecie < 0) {
			montoEntidadCoejecutoraEspecie = 0L;
		}

		if ((montoEntidadCoejecutora + montoEntidadCoejecutoraEspecie) == 0) {
			mensajeError("Ingrese los montos de la entidad participante.");
			return;
		}

		if (esCadenaVacia(entidadCoejecutora)) {
			mensajeError("Seleccione la entidad participante.");
			return;
		}

		try {

			FuenteFinanciacion fuenteFinanciacionSeleccionada = (FuenteFinanciacion) servicioGeneral
					.obtenerObjeto(new FuenteFinanciacion(), entidadCoejecutora);

			boolean existeFuenteFinanciacion = true;
			if (proyectoActual.isExisteFuenteFinancionFicha(entidadCoejecutora)) {
				existeFuenteFinanciacion = false;
				mensajeError("Una entidad participante no puede ser a la vez fuente de financiación");
			}

			boolean existeEntidadParticipante = true;
			if (proyectoActual.isExisteEntidadParticipante(entidadCoejecutora)) {
				existeEntidadParticipante = false;
				mensajeError("La entidad participante ya se encuentra vinculada al proyecto");
			}

			if (existeFuenteFinanciacion && existeEntidadParticipante) {

				TipoRubro tipoRubro = new TipoRubro();
				tipoRubro.setId(TipoRubro.ADMINISTRACION_PROYECTO);

				Gasto gastoNuevo = new Gasto();
				gastoNuevo.setTipoRubro(tipoRubro);
				gastoNuevo.setValor(montoEntidadCoejecutoraEspecie);
				gastoNuevo.setValor2(montoEntidadCoejecutora);

				Financiacion nuevaFinanciacion = new Financiacion();
				nuevaFinanciacion.setFuente(fuenteFinanciacionSeleccionada);
				nuevaFinanciacion.setValorPendiente(0L);
				nuevaFinanciacion.setValor(montoEntidadCoejecutora);
				nuevaFinanciacion.setValorEspecie(montoEntidadCoejecutoraEspecie);
				nuevaFinanciacion.setTipoEntidad(FuenteFinanciacion.ENTIDAD_PARTICIPANTE);
				nuevaFinanciacion.adicionarGasto(gastoNuevo);

				proyectoActual.adicionarFinanciacion(nuevaFinanciacion);

				entidadCoejecutora = null;
				montoEntidadCoejecutora = 0L;
				montoEntidadCoejecutoraEspecie = 0L;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		calcularTotalesFicha();

	}

	/**
	 * Agregar entidad especie.
	 */
	// entidades con aportes en especie
	public void agregarEntidadEspecie() {

		if (valorEspecie == null || (valorEspecie != null && valorEspecie < 0)) {
			mensajeError(uiEntidades, "Ingrese el valor en especie.");
			return;
		}

		if (esCadenaVacia(entidadSeleccionada)) {
			mensajeError(uiEntidades, "Seleccione la entidad.");
			return;
		}

		try {

			FuenteFinanciacion fuenteFinanciacionSeleccionada = (FuenteFinanciacion) servicioGeneral
					.obtenerObjeto(new FuenteFinanciacion(), entidadSeleccionada);

			if (fuenteFinanciacionSeleccionada != null) {

				if (proyectoActual.isExisteEntidadParticipante(entidadSeleccionada)) {
					mensajeError(uiEntidades, "La entidad ya se encuentra vinculada con su aporte en especie.");
				} else {

					TipoRubro tipoRubro = new TipoRubro();
					tipoRubro.setId(TipoRubro.ADMINISTRACION_PROYECTO);

					Gasto gastoNuevo = new Gasto();
					gastoNuevo.setTipoRubro(tipoRubro);
					gastoNuevo.setValor2(0L);
					gastoNuevo.setValor(valorEspecie);

					Financiacion nuevaFinanciacion = new Financiacion();
					nuevaFinanciacion.setFuente(fuenteFinanciacionSeleccionada);
					nuevaFinanciacion.setValor(0L);
					nuevaFinanciacion.setValorEspecie(valorEspecie);
					nuevaFinanciacion.setTipoEntidad(FuenteFinanciacion.ENTIDAD_ESPECIE);
					nuevaFinanciacion.adicionarGasto(gastoNuevo);

					proyectoActual.adicionarFinanciacion(nuevaFinanciacion);

					entidadSeleccionada = "";
					valorEspecie = 0L;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		calcularTotalesFicha();
	}

	/**
	 * Eliminar entidad especie.
	 */
	// Eliminar entidad de contrapartida en especie.
	public void eliminarEntidadEspecie() {
		proyectoActual.eliminarFuente(entidadEliminar);

		calcularTotalesFicha();
	}

	/**
	 * Cargar lista fuentes especie.
	 */
	private void cargarListaFuentesEspecie() {

		entidadItems = new ArrayList<SelectItem>();

		Iterator<Financiacion> i = proyectoActual.getListaFuentesFinancacionFicha().iterator();
		while (i.hasNext()) {
			Financiacion financ = i.next();
			if (financ.getFuente().getInternaExterna().equals(FuenteFinanciacion.externa)) {
				entidadItems.add(new SelectItem(financ.getFuente().getId(), financ.getFuente().getDescripcion()));
			}
		}
	}

	/**
	 * Eliminar financiacion.
	 */
	public void eliminarFinanciacion() {

		proyectoActual.eliminarFuente(entidadEliminar);

		calcularTotalesFicha();

	}

	/**
	 * Eliminar entidad coejecutora.
	 */
	public void eliminarEntidadCoejecutora() {

		proyectoActual.eliminarFuente(entidadEliminar);

		calcularTotalesFicha();

	}

	/**
	 * Eliminar participante.
	 */
	public void eliminarParticipante() {

		proyectoActual.getInvestigadoresProyecto().remove(participante);

		calcularValorDocente();

		if (!mostrarMenuFormulario) {
			calcularTotalesFicha();
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

	public void cambiarNaturalezaEntidad() {
		if (naturalezaEntidad.equals("NAT_INTERN")) {
			List<Pais> listaPaises;
			listaPaises = cargarPaises(false);
			listaPaisesItem = new SelectItem[listaPaises.size()];
			for (int i = 0; i < listaPaises.size(); i++) {
				Pais p = (Pais) listaPaises.get(i);
				listaPaisesItem[i] = new SelectItem(p.getId(), p.getNombre());
			}
		}
	}

	/**
	 * Eliminar participante num.
	 */
	public void eliminarParticipanteNum() {
		proyectoActual.getInvestigadoresProyecto().remove(participante);
	}

	/**
	 * Ingresar financiacion interna.
	 */
	public void ingresarFinanciacionInterna() {

		Financiacion financiacionInternaNueva = new Financiacion();
		if (!esInterno) {
			if (tipoFuenteActual == 0) {
				mensajeError("Seleccione el tipo de fuente de financiación.");
				return;
			}
		} else {
			tipoFuenteActual = 1;
		}
		if (!esArrayVacio(subtiposFinanciacion)) {
			if (subtipoFinanciacion == null) {
				mensajeError("Seleccione un subtipo de financiación.");
				return;
			}
			financiacionInternaNueva.setSubtipoFinanciacion(subtipoFinanciacion);
		}
		if ("".equals(fuenteActualIntId)) {
			mensajeError("Seleccione la fuente de financiación.");
			return;
		}
		String hql = "select ff from FuenteFinanciacion ff where ff.id = " + fuenteActualIntId;
		List<FuenteFinanciacion> lista = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class, hql);
		FuenteFinanciacion fuenteFinancieraActual = (FuenteFinanciacion) lista.get(0);
		Long valor = 0L;

		try {
			financiacionInternaNueva.setFuente(fuenteFinancieraActual);
			financiacionInternaNueva.setValor(new Long(valor));
			financiacionInternaNueva.setValorPendiente(new Long(valor));
			financiacionInternaNueva.setProyecto(proyectoActual);

			if (modalidadFuenteFinanciacionGenericoInterna != null) {
				financiacionInternaNueva.setModalidadFuenteFinanciacion(modalidadFuenteFinanciacionGenericoInterna);
			} else if (convocatoriaActual.getId().equals(2L) || convocatoriaActual.getId().equals(10L)
					|| (modalidadFuenteFinanciacionGenericoInterna == null && convocatoriaActual.getTipoFinanciacion()
							.equals(Convocatoria.TIPO_FINANCIACION_MINIMA))) {
				ModalidadFuenteFinanciacion mdf = new ModalidadFuenteFinanciacion();
				mdf.setId(889L);
				mdf.setArbol(889L);
				financiacionInternaNueva.setModalidadFuenteFinanciacion(mdf);
				financiacionInternaNueva.getModalidadFuenteFinanciacion().setIncluirContrapartida(true);
			}

			financiacionInternaNueva.setRubrosFinanciableArbol(
					obtenerRubroFinanciableArbol(financiacionInternaNueva.getModalidadFuenteFinanciacion()));
			financiacionInternaNueva.construirTreeGasto();
			if (proyectoActual.isExisteFuenteFinancionFicha(fuenteActualIntId)) {
				mensajeError("La fuente seleccionada ya se encuentra en la lista");
			} else {
				proyectoActual.adicionarFinanciacion(financiacionInternaNueva);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Agregar fuentes externas.
	 */
	public void agregarFuentesExternas() {

		if (tipoFuenteActual == 0) {
			mensajeError("Seleccione el tipo de fuente de financiación.");
			return;
		}

		if ("0".equals(fuenteActualExtId)) {
			mensajeError("Seleccione la fuente de financiación.");
			return;
		}

		try {

			String hql = "select ff from FuenteFinanciacion ff where ff.id = " + fuenteActualExtId;
			List<FuenteFinanciacion> lista = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class, hql);
			FuenteFinanciacion fuenteFinancieraActual = (FuenteFinanciacion) lista.get(0);

			Financiacion f = new Financiacion();
			f.setFuente(fuenteFinancieraActual);
			f.setValor(0L);
			f.setProyecto(proyectoActual);

			if (modalidadFuenteFinanciacionGenericoExterna != null) {
				f.setModalidadFuenteFinanciacion(modalidadFuenteFinanciacionGenericoExterna);
			} else if (convocatoriaActual.getId().equals(2L) || convocatoriaActual.getId().equals(10L)) {
				ModalidadFuenteFinanciacion mdf = new ModalidadFuenteFinanciacion();
				mdf.setId(889L);
				mdf.setArbol(889L);
				f.setModalidadFuenteFinanciacion(mdf);
				f.getModalidadFuenteFinanciacion().setIncluirContrapartida(true);
			}

			f.setRubrosFinanciableArbol(obtenerRubroFinanciableArbol(f.getModalidadFuenteFinanciacion()));

			f.construirTreeGasto();

			if (proyectoActual.isExisteFuenteFinancionFicha(fuenteActualExtId)) {
				mensajeError("La fuente seleccionada ya se encuentra en la lista");
			} else {
				proyectoActual.adicionarFinanciacion(f);
				cargarListaFuentesEspecie();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Guardar parcialmente.
	 *
	 * @param esRegistroUnico the es registro unico
	 * @return the string
	 */
	public String guardarParcialmente(boolean esRegistroUnico) {
		boolean error = false;
		try {
			if (validarDatosMinimosGuardarParcial()) {
				boolean guardarParcialmente = true;
				if (guardarFichaMinimaProyecto(guardarParcialmente, esRegistroUnico)) {
					if (proyectoActual.getId() != null) {
						guardarHistoricoFormulario(true);
					}
					if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null) {
						enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
					}

					if (!esListaVacia(proyectoActual.getListaInvestigadoresProyecto())) {
						enviarCorreoAsistentesLideres(proyectoActual.getListaInvestigadoresProyecto());
					}
				} else {
					error = true;
				}
			} else {
				error = true;
			}
		} catch (Exception es) {
			es.printStackTrace();
			mensajeError("Excepcion de codigo: " + es.toString());
			mensajeError("Hora de Excepcion: " + getToday().toString());
			mensajeError("Por favor genere una captura de pantalla y enviela a hermes@unal.edu.co");
			error = true;
		}
		if (error) {
			mensajeError(MENSAJE_PROYECTO_NO_GUARDADO);
		}
		return "";
	}

	/**
	 * Guardar FM.
	 *
	 * @return the string
	 */
	public String guardarFM() {
		boolean esRegistroUnico = true;
		return guardarParcialmente(esRegistroUnico);
	}

	/**
	 * Terminar despues.
	 *
	 * @return the string
	 */
	public String terminarDespues() {
		boolean esRegistroUnico = false;
		return guardarParcialmente(esRegistroUnico);
	}

	/**
	 * Guardar finalizar.
	 *
	 * @return the string
	 */
	// Solo modalidad 2
	public String guardarFinalizar() {
		try {
			if(editarVigencias) {

				if(!proyectoActual.getMontoInicialAprobado().equals(proyectoActual.getContrapartidaEfectivo())) {
					mensajeError("Recuerde que el total por vigencias debe corresponder con el total aprobado inicialmente para el proyecto");
					return "";
				}
			}
			
			if (validarDatosMinimosGuardarParcial()) {
				if (validarNombre_InvPpal()) {

					mostrarSiGuardarFinalizar = true;

					EstadoProyecto estadoAnterior = proyectoActual.getEstadoProyecto();

					// Si esta en estado ingresando se cambia a estado
					// propuesto.
					if (estadoAnterior != null && !esCadenaVacia(estadoAnterior.getId())
							&& estadoAnterior.getId().equals(EstadoProyecto.INGRESANDO)) {
						proyectoActual.cambiarEstadoPersona(EstadoProyecto.PROPUESTO, cargarPersonaActual());
					}

					// Se guarda ficha
					boolean guardarParcialmente = false;
					boolean esRegistroUnico = true;

					if (!guardarFichaMinimaProyecto(guardarParcialmente, esRegistroUnico)) {
						if (estadoAnterior != null) {
							proyectoActual.cambiarEstadoPersona(estadoAnterior.getId(), cargarPersonaActual());
						}
						mensajeError(MENSAJE_PROYECTO_NO_GUARDADO);
					} else {
						if (proyectoActual.getId() != null) {
							guardarHistoricoFormulario(false);
						}
						if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId())
								&& proyectoActual.getId() != null) {
							enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
						}

						if (!esListaVacia(proyectoActual.getListaInvestigadoresProyecto())) {
							enviarCorreoAsistentesLideres(proyectoActual.getListaInvestigadoresProyecto());
						}
					}

				} else {
					mensajeError(MENSAJE_PROYECTO_NO_GUARDADO);
				}

			} else {
				mensajeError(MENSAJE_PROYECTO_NO_GUARDADO);
			}
		} catch (Exception es) {
			es.printStackTrace();
			return "ManejadorFichaMinimaHome:guardarFinalizar Error";
		}

		return "";
	}

	public Boolean laboratorioExisteEnLista(Long idLab, ArrayList<Laboratorio> lista) {
		for (Laboratorio laboratorio : lista) {
			if (idLab.equals(laboratorio.getId()))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.vista.proyectos.manejadorproyecto.ManejadorProyecto#
	 * siguiente()
	 */
	// NO modalidad 2
	public String siguiente() {
		
		if(editarVigencias) {
			
			Long montoAprobado = servicioProyecto.obtenerMontoAprobadoProyecto(proyectoActual.getId(), "MINIMA");
			if(proyectoActual.getMontoInicialAprobado()==null) {
				proyectoActual.setMontoInicialAprobado(montoAprobado);
			}

			if(!proyectoActual.getMontoInicialAprobado().equals(proyectoActual.getContrapartidaEfectivo())) {
				mensajeError("Recuerde que el total por vigencias debe corresponder con el total aprobado inicialmente para el proyecto: "+proyectoActual.getMontoInicialAprobado());
				return "";
			}
		}

		if (validarDatosMinimosGuardarParcial()) {

			boolean guardarParcialmente = false;
			boolean esRegistroUnico = false;
			if (guardarFichaMinimaProyecto(guardarParcialmente, esRegistroUnico)) {
				ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion
						.getAttribute(ManejadorMenuFormularios.MANEJADOR_MENU_FORMULARIOS_SESSION);

				
				
				boolean bandera = false;
				int pos = 0;
				if (man.getItemProyecto() != null) {
					MenuItem[] lis = man.getMenuItemArray();
					if (lis != null) {
						for (int i = 0; i < lis.length; i++) {
							if (bandera) {
								if (lis[i].isRendered()) {
									sesion.removeAttribute(ManejadorMenuFormularios.MANEJADOR_MENU_FORMULARIOS_SESSION);
									break;
								}

								if (lis[i].getOutcome().equals("fichaMinimaHome")) {
									bandera = true;
								}
								if (lis[i].isRendered()) {
									pos++;
								}
							}
						}
					}

					if ((proyectoActual.getEstadoProyecto().getId()).equals(EstadoProyecto.INGRESANDO)
							&& (pos - 1) == proyectoActual.getFase().intValue()) {
						proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
					}

					if (proyectoActual.getId() != null) {
						guardarHistoricoFormulario(false);
					}

					if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId())) {
						// Se comenta el envio de correo por que en BD no esta creada la plantilla de
						// correo 326 y se desconoce el motivo,
						// lo cual genera excepcion al guardar con rol Estudiante Lider
//						enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
					}

					if (!esListaVacia(proyectoActual.getListaInvestigadoresProyecto())) {
						enviarCorreoAsistentesLideres(proyectoActual.getListaInvestigadoresProyecto());
					}

					String permiteMod = !esNulo(proyectoActual.getPermitirModificacion())
							? proyectoActual.getPermitirModificacion()
							: "N";
					if (proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.ACTIVO)
							&& permiteMod.equals("S")) {

						ArrayList<Laboratorio> listaLaboratorios = (ArrayList<Laboratorio>) proyectoActual
								.getListaLaboratorios();
//						ArrayList<Laboratorio> listaLaboratoriosAgregados = new ArrayList<Laboratorio>();
//						ArrayList<Laboratorio> listaLaboratoriosEliminados = new ArrayList<Laboratorio>();

						// Agregados
						for (Laboratorio lab : listaLaboratorios) {
							Boolean existe = laboratorioExisteEnLista(lab.getId(), listaLaboratoriosOriginal);
							if (!existe) {
								System.out.println(" * * * Agregado: " + lab.getId());
//								listaLaboratoriosAgregados.add(lab);
								enviarCorreoLaboratoriosProyecto(lab, proyectoActual,
										CorreoPlantilla.CORREO_NOTIFICACION_ASOCIACION_LAB_PROYECTO_ACTIVO);
							}
						}

						// Eliminados
						for (Laboratorio lab : listaLaboratoriosOriginal) {
							Boolean existe = laboratorioExisteEnLista(lab.getId(), listaLaboratorios);
							if (!existe) {
								System.out.println(" * * * Eliminado: " + lab.getId());
//								listaLaboratoriosOriginal.remove(lab);
//								listaLaboratoriosEliminados.add(lab);
								enviarCorreoLaboratoriosProyecto(lab, proyectoActual,
										CorreoPlantilla.CORREO_NOTIFICACION_ELIMINACION_LAB_PROYECTO_ACTIVO);
							}
						}
					}

					listaLaboratoriosOriginal = (ArrayList<Laboratorio>) proyectoActual.getListaLaboratorios();

					sesion.removeAttribute(ManejadorMenuFormularios.MANEJADOR_MENU_FORMULARIOS_SESSION);
					if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()))
						mensajeInfo("Su proyecto ha sido registrado correctamente con el código: " + proyectoActual.getId() + ". Debe comunicarse con el director del proyecto para que realice revisión y guardado final o envío a la convocatoria.", "");
					else
						mensajeInfo("Su proyecto ha sido registrado correctamente con el código: " + proyectoActual.getId() + ".", "");

					// boolean bandera = false;
					if (convocatoriaActual.getTipo().getId().equals("PM")) {
						sesion.removeAttribute(ManejadorMenuFormularios.MANEJADOR_MENU_FORMULARIOS_SESSION);
						return "irInformacionEspecificaMarco";
					} else {
						if (convocatoriaActual.getTipo().getId().equals("CJI")
								|| convocatoriaActual.getTipo().getId().equals("FMH")
								|| convocatoriaActual.getTipo().getId().equals("CED")
								|| convocatoriaActual.getTipo().getId().equals("CPU")
								|| convocatoriaActual.getTipo().getId().equals("CTV")
								|| convocatoriaActual.getTipo().getId().equals("CCT")
								|| convocatoriaActual.getTipo().getId().equals("RFE")
								|| convocatoriaActual.getTipo().getId().equals("RPL") || esFichaExterna) {
							sesion.removeAttribute("manejadorInformacionEspecifica");
							sesion.removeAttribute("manejadorActividades");
							return "irInformacionEspecifica";
						} else {
							if (convocatoriaActual.getTipo().getId().equals("SEB")) {
								return "irSubirArchivo";
							} else {
								if (convocatoriaActual.getTipo().getId().equals("ESI")
										|| convocatoriaActual.getTipo().getId().equals("ES7")) {
									return "irTrabajoPrevioConES_IS";
								} else {
									if (convocatoriaActual.getTipo().getId().equals("CSF")) {
										return "irSubirArchivo";
									} else {
										if (convocatoriaActual.getTipo().getId().equals("CTP")) {
											return "irActividades";
										}
									}
								}
							}
						}
					}
				}
			} else {
				mensajeError(MENSAJE_PROYECTO_NO_GUARDADO, MENSAJE_PROYECTO_NO_GUARDADO);
			}
		} else {
			mensajeError(MENSAJE_PROYECTO_NO_GUARDADO, MENSAJE_PROYECTO_NO_GUARDADO);
		}
		sesion.removeAttribute(ManejadorBibliografia.MANEJADOR_BIBLIOGRAFIA_SESSION);
		sesion.removeAttribute("manejadorActividades");
		sesion.removeAttribute("manejadorArchivos");
		return "";

	}

	/**
	 * Guardar historico formulario.
	 *
	 * @param parcial the parcial
	 */
	private void guardarHistoricoFormulario(boolean parcial) {

		List<Formulario> listaFormulario = servicioGeneral.obtenerObjetos(Formulario.class,
				"from Formulario where id ='5'");
		Formulario formulario = (Formulario) listaFormulario.get(0);

		HistoricoFormularioProyecto historicoFormularioProyecto = new HistoricoFormularioProyecto();
		historicoFormularioProyecto.setDocPersona(getPersonaActual().getId().getDocumento());
		historicoFormularioProyecto.setTipoDocumentoPersona(getPersonaActual().getId().getTipoDocumento());
		historicoFormularioProyecto.setFormulario(formulario);
		historicoFormularioProyecto.setProyecto(proyectoActual);
		historicoFormularioProyecto.setFechaCambio(new Date());
		if (parcial) {
			historicoFormularioProyecto.setEsParcial("S");
		} else {
			historicoFormularioProyecto.setEsParcial("N");
		}
		servicioGeneral.guardarObjeto(historicoFormularioProyecto);
	}

	/**
	 * Validar nombre inv ppal.
	 *
	 * @return true, if successful
	 */
	public boolean validarNombre_InvPpal() {
		boolean esRegistroUnico = true;
		return validarDatosMinimosFinalizarEdicion(esRegistroUnico);
	}

	/**
	 * Atras pry.
	 *
	 * @return the string
	 */
	public String atrasPry() {

		sesion.removeAttribute("manejadorFichaMinimaHome");
		return "successProyectosProyecto";
	}

	public void solicitudIngresoEntidadParticipante() {

		if (!esCadenaVacia(nombreEntidad) && !esCadenaVacia(nitEntidad) && !esCadenaVacia(direccionEntidad)) {

			// Guardar en la bd
			FuenteFinanciacion fte = new FuenteFinanciacion();
			String descripcionFuente = ReemplazaAcentos.quitarTildes(nombreEntidad != null ? nombreEntidad : "")
					.toUpperCase();

			fte.setNaturaleza(naturalezaEntidad);
			fte.setDescripcion(descripcionFuente);
			fte.setInternaExterna("OE");
			fte.setNit(nitEntidad != null ? nitEntidad : "");
			fte.setDireccion(direccionEntidad != null ? direccionEntidad : "");
			fte.setTelefono(telefonoEntidad != null ? telefonoEntidad : "");
			fte.setObservaciones("ENT_REG_PRY");
			fte.setQuipu("S");
			fte.setCaracter(caracterEntidad);
			fte.setTipoFuente(tipoEntidad);
			if (naturalezaEntidad.equals("NAT_INTERN")) {
				Pais paisSel = new Pais();
				paisSel.setId(paisEntidad);
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
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<NIT>>", nitEntidad != null ? nitEntidad : ""));
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<NATURALEZA>>",
						naturalezaEntidad != null ? naturalezaEntidad : ""));
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<DIRECCION>>",
						direccionEntidad != null ? direccionEntidad : ""));
				correo.setCuerpo(
						correo.getCuerpo().replaceAll("<<TELEFONO>>", telefonoEntidad != null ? telefonoEntidad : ""));
				String personaCorreo = "(" + personaActual.getId().getTipoDocumento() + "-"
						+ personaActual.getId().getDocumento() + ") " + personaActual.getNombre1() + " "
						+ personaActual.getApellido1();
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<CEDULA>>", personaCorreo));
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<CORREO>>", personaActual.getEmail()));
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<ID>>", fte.getId()));
				correo.adicionarDireccion(Correo.CORREO_HERMES_COMUNICACIONES);
				servicioCorreo.enviarCorreo(correo);
				mensajeInfo(CB_crearEntidad, "La información de creación de la entidad ha sido enviada correctamente.");
			} catch (Exception e) {
				mensajeInfo(CB_crearEntidad,
						"La información de la entidad NO ha sido enviada, revise la información ingresada.");
			}
		} else {
			mensajeInfo(CB_crearEntidad,
					"La información de la entidad NO ha sido enviada, revise la información ingresada.");
		}
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

	/**
	 * Gets the avales proyecto.
	 *
	 * @return the avales proyecto
	 */
	// Avales Proyecto Martha Liliana
	public List<Aval> getAvalesProyecto() {
		List<Aval> avalesAsociadosProyecto = servicioGeneral.obtenerAvalesProyecto(proyectoActual.getId().toString());
		if (!esListaVacia(avalesAsociadosProyecto)) {
			return avalesAsociadosProyecto;
		}
		return new ArrayList<Aval>();
	}

	/**
	 * Editar movilidad aval.
	 *
	 * @return the string
	 */
	public String editarMovilidadAval() {
		eliminarManejadoresAval(ManejadorAvalMenu.ID_AVAL_SESION);
		String valor = obtenerValorMapContext("idAvalConsulta");
		Long id = Long.valueOf(valor);
		sesion.setAttribute(ManejadorAvalMenu.ID_AVAL_SESION, id);
		sesion.setAttribute("esConsulta", true);

		if ((Long) sesion.getAttribute(ManejadorAvalMenu.ID_AVAL_SESION) <= 4855) {
			return "avalarConsulta";
		} else {
			return "avalarConsultaHome";
		}

	}

	/**
	 * Editar aval proyecto.
	 *
	 * @return the string
	 */
	public String editarAvalProyecto() {
		eliminarManejadoresAval(ManejadorAvalMenu.ID_AVAL_SESION);
		String valor = obtenerValorMapContext("idAvalConsulta");
		Long id = Long.valueOf(valor);
		sesion.setAttribute(ManejadorAvalMenu.ID_AVAL_SESION, id);
		try {
			List<Aval> lista = servicioGeneral.obtenerObjetosLimitado(Aval.class,
					"select #documento a.documento from Aval a where " + " a.id = '" + id + "'");
			Aval aval = null;
			if (!esListaVacia(lista)) {
				aval = (Aval) lista.get(0);
				if (aval != null && aval.getDocumento().equals(personaActual.getId().getDocumento())) {
					sesion.setAttribute("esEdicion", true);
					return "avalarEditarHome";

				} else {
					sesion.setAttribute("esConsulta", true);
				}
			}
			if ((Long) sesion.getAttribute(ManejadorAvalMenu.ID_AVAL_SESION) <= 4855) {
				return "avalarConsulta";
			} else {
				return "avalarConsultaHome";
			}
		} catch (Exception e) {
			sesion.setAttribute("esEdicion", true);
			return "avalarEditarHome";
		}
	}

	/**
	 * Eliminar palabra clave.
	 */
	public void eliminarPalabraClave() {
		proyectoActual.borrarPalabraClave(palabraClaveTabla);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.vista.proyectos.manejadorproyecto.ManejadorProyecto#
	 * atras()
	 */
	public String atras() {
		sesion.removeAttribute("manejadorFichaMinimaHome");
		return "successProyectosProyecto";
	}

	// Fin Avales Proyecto Martha Liliana

	/**
	 * Checks if is mostrar si guardar finalizar.
	 *
	 * @return true, if is mostrar si guardar finalizar
	 */
	public boolean isMostrarSiGuardarFinalizar() {
		return mostrarSiGuardarFinalizar;
	}

	/**
	 * Sets the mostrar si guardar finalizar.
	 *
	 * @param mostrarSiGuardarFinalizar the new mostrar si guardar finalizar
	 */
	public void setMostrarSiGuardarFinalizar(boolean mostrarSiGuardarFinalizar) {
		this.mostrarSiGuardarFinalizar = mostrarSiGuardarFinalizar;
	}

	/**
	 * Gets the link definicion rubros.
	 *
	 * @return the link definicion rubros
	 */
	public String getLinkDefinicionRubros() {
		// return
		// "http://gerencia.unal.edu.co/fileadmin/user_upload/PTO_GU_Catalogo_Plan_Cuentas__Presupuestal_V0.pdf";
		return "http://www.hermes.unal.edu.co/pages/html/descargas/instructivo.xhtml?id=15";
	}

	/**
	 * Gets the area ciencia sec.
	 *
	 * @return the area ciencia sec
	 */
	public String getAreaCienciaSec() {
		return areaCienciaSec;
	}

	/**
	 * Sets the area ciencia sec.
	 *
	 * @param areaCienciaSec the new area ciencia sec
	 */
	public void setAreaCienciaSec(String areaCienciaSec) {
		this.areaCienciaSec = areaCienciaSec;
	}

	/**
	 * Gets the palabra clave tabla.
	 *
	 * @return the palabra clave tabla
	 */
	public PalabraClave getPalabraClaveTabla() {
		return palabraClaveTabla;
	}

	/**
	 * Sets the palabra clave tabla.
	 *
	 * @param palabraClaveTabla the new palabra clave tabla
	 */
	public void setPalabraClaveTabla(PalabraClave palabraClaveTabla) {
		this.palabraClaveTabla = palabraClaveTabla;
	}

	/**
	 * Gets the participante.
	 *
	 * @return the participante
	 */
	public InvestigadorProyecto getParticipante() {
		return participante;
	}

	/**
	 * Sets the participante.
	 *
	 * @param participante the new participante
	 */
	public void setParticipante(InvestigadorProyecto participante) {
		this.participante = participante;
	}

	/**
	 * Gets the tipo fuente actual.
	 *
	 * @return the tipo fuente actual
	 */
	public int getTipoFuenteActual() {
		return tipoFuenteActual;
	}

	/**
	 * Sets the tipo fuente actual.
	 *
	 * @param tipoFuenteActual the new tipo fuente actual
	 */
	public void setTipoFuenteActual(int tipoFuenteActual) {
		this.tipoFuenteActual = tipoFuenteActual;
	}

	/**
	 * Gets the fuente actual int id.
	 *
	 * @return the fuente actual int id
	 */
	public String getFuenteActualIntId() {
		return fuenteActualIntId;
	}

	/**
	 * Sets the fuente actual int id.
	 *
	 * @param fuenteActualId the new fuente actual int id
	 */
	public void setFuenteActualIntId(String fuenteActualId) {
		this.fuenteActualIntId = fuenteActualId;
	}

	/**
	 * Gets the fuente actual ext id.
	 *
	 * @return the fuente actual ext id
	 */
	public String getFuenteActualExtId() {
		return fuenteActualExtId;
	}

	/**
	 * Sets the fuente actual ext id.
	 *
	 * @param fuenteActualExtId the new fuente actual ext id
	 */
	public void setFuenteActualExtId(String fuenteActualExtId) {
		this.fuenteActualExtId = fuenteActualExtId;
	}

	/**
	 * Checks if is requiere otra fuente.
	 *
	 * @return true, if is requiere otra fuente
	 */
	public boolean isRequiereOtraFuente() {
		return requiereOtraFuente;
	}

	/**
	 * Sets the requiere otra fuente.
	 *
	 * @param requiereOtraFuente the new requiere otra fuente
	 */
	public void setRequiereOtraFuente(boolean requiereOtraFuente) {
		this.requiereOtraFuente = requiereOtraFuente;
	}

	/**
	 * Gets the nombre fuente.
	 *
	 * @return the nombre fuente
	 */
	public String getNombreFuente() {
		return nombreFuente;
	}

	/**
	 * Sets the nombre fuente.
	 *
	 * @param nombreFuente the new nombre fuente
	 */
	public void setNombreFuente(String nombreFuente) {
		this.nombreFuente = nombreFuente;
	}

	/**
	 * Gets the nit fuente.
	 *
	 * @return the nit fuente
	 */
	public String getNitFuente() {
		return nitFuente;
	}

	/**
	 * Sets the nit fuente.
	 *
	 * @param nitFuente the new nit fuente
	 */
	public void setNitFuente(String nitFuente) {
		this.nitFuente = nitFuente;
	}

	/**
	 * Gets the naturaleza fuente.
	 *
	 * @return the naturaleza fuente
	 */
	public String getNaturalezaFuente() {
		return naturalezaFuente;
	}

	/**
	 * Sets the naturaleza fuente.
	 *
	 * @param naturalezaFuente the new naturaleza fuente
	 */
	public void setNaturalezaFuente(String naturalezaFuente) {
		this.naturalezaFuente = naturalezaFuente;
	}

	/**
	 * Gets the telefono fuente.
	 *
	 * @return the telefono fuente
	 */
	public String getTelefonoFuente() {
		return telefonoFuente;
	}

	/**
	 * Sets the telefono fuente.
	 *
	 * @param telefonoFuente the new telefono fuente
	 */
	public void setTelefonoFuente(String telefonoFuente) {
		this.telefonoFuente = telefonoFuente;
	}

	/**
	 * Gets the direccion fuente.
	 *
	 * @return the direccion fuente
	 */
	public String getDireccionFuente() {
		return direccionFuente;
	}

	/**
	 * Sets the direccion fuente.
	 *
	 * @param direccionFuente the new direccion fuente
	 */
	public void setDireccionFuente(String direccionFuente) {
		this.direccionFuente = direccionFuente;
	}

	/**
	 * Gets the tipos naturaleza fuente item.
	 *
	 * @return the tipos naturaleza fuente item
	 */
	public SelectItem[] getTiposNaturalezaFuenteItem() {
		return crearListaItemDominioDetalle(Dominio.NAT_ENTIDAD);
	}

	/**
	 * Gets the entidad eliminar.
	 *
	 * @return the entidad eliminar
	 */
	public Financiacion getEntidadEliminar() {
		return entidadEliminar;
	}

	/**
	 * Sets the entidad eliminar.
	 *
	 * @param entidadEliminar the new entidad eliminar
	 */
	public void setEntidadEliminar(Financiacion entidadEliminar) {
		this.entidadEliminar = entidadEliminar;
	}

	/**
	 * Checks if is mas entidades coejecutoras.
	 *
	 * @return true, if is mas entidades coejecutoras
	 */
	public boolean isMasEntidadesCoejecutoras() {
		return masEntidadesCoejecutoras;
	}

	/**
	 * Sets the mas entidades coejecutoras.
	 *
	 * @param masEntidadesCoejecutoras the new mas entidades coejecutoras
	 */
	public void setMasEntidadesCoejecutoras(boolean masEntidadesCoejecutoras) {
		this.masEntidadesCoejecutoras = masEntidadesCoejecutoras;
	}

	/**
	 * Gets the entidad coejecutora.
	 *
	 * @return the entidad coejecutora
	 */
	public String getEntidadCoejecutora() {
		return entidadCoejecutora;
	}

	/**
	 * Sets the entidad coejecutora.
	 *
	 * @param entidadCoejecutora the new entidad coejecutora
	 */
	public void setEntidadCoejecutora(String entidadCoejecutora) {
		this.entidadCoejecutora = entidadCoejecutora;
	}

	/**
	 * Gets the monto entidad coejecutora.
	 *
	 * @return the monto entidad coejecutora
	 */
	public Long getMontoEntidadCoejecutora() {
		return montoEntidadCoejecutora;
	}

	/**
	 * Sets the monto entidad coejecutora.
	 *
	 * @param montoEntidadCoejecutora the new monto entidad coejecutora
	 */
	public void setMontoEntidadCoejecutora(Long montoEntidadCoejecutora) {
		this.montoEntidadCoejecutora = montoEntidadCoejecutora;
	}

	/**
	 * Gets the monto entidad coejecutora especie.
	 *
	 * @return the monto entidad coejecutora especie
	 */
	public Long getMontoEntidadCoejecutoraEspecie() {
		return montoEntidadCoejecutoraEspecie;
	}

	/**
	 * Sets the monto entidad coejecutora especie.
	 *
	 * @param montoEntidadCoejecutoraEspecie the new monto entidad coejecutora
	 *                                       especie
	 */
	public void setMontoEntidadCoejecutoraEspecie(Long montoEntidadCoejecutoraEspecie) {
		this.montoEntidadCoejecutoraEspecie = montoEntidadCoejecutoraEspecie;
	}

	/**
	 * Gets the entidad coeje.
	 *
	 * @return the entidad coeje
	 */
	public UIComponent getEntidadCoeje() {
		return entidadCoeje;
	}

	/**
	 * Sets the entidad coeje.
	 *
	 * @param entidadCoeje the new entidad coeje
	 */
	public void setEntidadCoeje(UIComponent entidadCoeje) {
		this.entidadCoeje = entidadCoeje;
	}

	/**
	 * Gets the contrapartida personal.
	 *
	 * @return the contrapartida personal
	 */
	public Long getContrapartidaPersonal() {
		return contrapartidaPersonal;
	}

	/**
	 * Sets the contrapartida personal.
	 *
	 * @param contrapartidaPersonal the new contrapartida personal
	 */
	public void setContrapartidaPersonal(Long contrapartidaPersonal) {
		this.contrapartidaPersonal = contrapartidaPersonal;
	}

	/**
	 * Gets the valor financiado investig.
	 *
	 * @return the valor financiado investig
	 */
	public Long getValorFinanciadoInvestig() {
		return valorFinanciadoInvestig;
	}

	/**
	 * Gets the valor total total proyecto.
	 *
	 * @return the valor total total proyecto
	 */
	public Long getValorTotalTotalProyecto() {
		return valorTotalTotalProyecto;
	}

	/**
	 * Sets the valor total total proyecto.
	 *
	 * @param valorTotalTotalProyecto the new valor total total proyecto
	 */
	public void setValorTotalTotalProyecto(Long valorTotalTotalProyecto) {
		this.valorTotalTotalProyecto = valorTotalTotalProyecto;
	}

	/**
	 * Gets the objeto.
	 *
	 * @return the objeto
	 */
	public UIComponent getObjeto() {
		return objeto;
	}

	/**
	 * Sets the objeto.
	 *
	 * @param objeto the new objeto
	 */
	public void setObjeto(UIComponent objeto) {
		this.objeto = objeto;
	}

	/**
	 * Gets the resumen.
	 *
	 * @return the resumen
	 */
	public UIComponent getResumen() {
		return resumen;
	}

	/**
	 * Sets the resumen.
	 *
	 * @param resumen the new resumen
	 */
	public void setResumen(UIComponent resumen) {
		this.resumen = resumen;
	}

	/**
	 * Gets the clase items ppal.
	 *
	 * @return the clase items ppal
	 */
	public SelectItem[] getClaseItemsPpal() {
		return claseItemsPpal;
	}

	/**
	 * Gets the entidad seleccionada.
	 *
	 * @return the entidad seleccionada
	 */
	public String getEntidadSeleccionada() {
		return entidadSeleccionada;
	}

	/**
	 * Sets the entidad seleccionada.
	 *
	 * @param entidadSeleccionada the new entidad seleccionada
	 */
	public void setEntidadSeleccionada(String entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}

	/**
	 * Gets the entidad items.
	 *
	 * @return the entidad items
	 */
	public List<SelectItem> getEntidadItems() {
		return entidadItems;
	}

	/**
	 * Gets the valor especie.
	 *
	 * @return the valor especie
	 */
	public Long getValorEspecie() {
		return valorEspecie;
	}

	/**
	 * Sets the valor especie.
	 *
	 * @param valorEspecie the new valor especie
	 */
	public void setValorEspecie(Long valorEspecie) {
		this.valorEspecie = valorEspecie;
	}

	/**
	 * Gets the ui entidades.
	 *
	 * @return the ui entidades
	 */
	public UIComponent getUiEntidades() {
		return uiEntidades;
	}

	/**
	 * Sets the ui entidades.
	 *
	 * @param uiEntidades the new ui entidades
	 */
	public void setUiEntidades(UIComponent uiEntidades) {
		this.uiEntidades = uiEntidades;
	}

	/**
	 * Gets the linea accion.
	 *
	 * @return the linea accion
	 */
	public String getLineaAccion() {
		return lineaAccion;
	}

	/**
	 * Sets the linea accion.
	 *
	 * @param lineaAccion the new linea accion
	 */
	public void setLineaAccion(String lineaAccion) {
		this.lineaAccion = lineaAccion;
	}

	/**
	 * Gets the programa.
	 *
	 * @return the programa
	 */
	public String getPrograma() {
		return programa;
	}

	/**
	 * Sets the programa.
	 *
	 * @param programa the new programa
	 */
	public void setPrograma(String programa) {
		this.programa = programa;
	}

	/**
	 * Checks if is vercod linea accion.
	 *
	 * @return true, if is vercod linea accion
	 */
	public boolean isVercodLineaAccion() {
		return vercodLineaAccion;
	}

	/**
	 * Sets the vercod linea accion.
	 *
	 * @param vercodLineaAccion the new vercod linea accion
	 */
	public void setVercodLineaAccion(boolean vercodLineaAccion) {
		this.vercodLineaAccion = vercodLineaAccion;
	}

	/**
	 * Checks if is vercod programa.
	 *
	 * @return true, if is vercod programa
	 */
	public boolean isVercodPrograma() {
		return vercodPrograma;
	}

	/**
	 * Sets the vercod programa.
	 *
	 * @param vercodPrograma the new vercod programa
	 */
	public void setVercodPrograma(boolean vercodPrograma) {
		this.vercodPrograma = vercodPrograma;
	}

	/**
	 * Checks if is mostrar contrapartida.
	 *
	 * @return true, if is mostrar contrapartida
	 */
	public boolean isMostrarContrapartida() {
		return mostrarContrapartida;
	}

	/**
	 * Gets the subtipos financiacion.
	 *
	 * @return the subtipos financiacion
	 */
	public SelectItem[] getSubtiposFinanciacion() {
		return subtiposFinanciacion;
	}

	/**
	 * Gets the subtipo financiacion.
	 *
	 * @return the subtipo financiacion
	 */
	public Tipos getSubtipoFinanciacion() {
		return subtipoFinanciacion;
	}

	/**
	 * Sets the subtipo financiacion.
	 *
	 * @param suptipoFinanciacion the new subtipo financiacion
	 */
	public void setSubtipoFinanciacion(Tipos suptipoFinanciacion) {
		this.subtipoFinanciacion = suptipoFinanciacion;
	}

	public boolean isValidarBotonGuardar() {
		return validarBotonGuardarConvocatoria(proyectoActual.getId());
	}

	public SelectItem[] getTiposFuenteFinanciacionItem() {
		return crearListaItemDominioDetalle(Dominio.TIPO_FUENTE_FINANCIACION);
	}

	public SelectItem[] getCaracterFuenteFinanciacionItem() {
		return crearListaItemDominioDetalle(Dominio.CARACTER_FUENTE_FINANCIACION);
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

	public String getPaisFuente() {
		return paisFuente;
	}

	public void setPaisFuente(String paisFuente) {
		this.paisFuente = paisFuente;
	}

	public boolean isRequiereOtraEntidad() {
		return requiereOtraEntidad;
	}

	public void setRequiereOtraEntidad(boolean requiereOtraEntidad) {
		this.requiereOtraEntidad = requiereOtraEntidad;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public String getNitEntidad() {
		return nitEntidad;
	}

	public void setNitEntidad(String nitEntidad) {
		this.nitEntidad = nitEntidad;
	}

	public String getNaturalezaEntidad() {
		return naturalezaEntidad;
	}

	public void setNaturalezaEntidad(String naturalezaEntidad) {
		this.naturalezaEntidad = naturalezaEntidad;
	}

	public String getPaisEntidad() {
		return paisEntidad;
	}

	public void setPaisEntidad(String paisEntidad) {
		this.paisEntidad = paisEntidad;
	}

	public String getCaracterEntidad() {
		return caracterEntidad;
	}

	public void setCaracterEntidad(String caracterEntidad) {
		this.caracterEntidad = caracterEntidad;
	}

	public String getTipoEntidad() {
		return tipoEntidad;
	}

	public void setTipoEntidad(String tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	public String getTelefonoEntidad() {
		return telefonoEntidad;
	}

	public void setTelefonoEntidad(String telefonoEntidad) {
		this.telefonoEntidad = telefonoEntidad;
	}

	public String getDireccionEntidad() {
		return direccionEntidad;
	}

	public void setDireccionEntidad(String direccionEntidad) {
		this.direccionEntidad = direccionEntidad;
	}

	public UIComponent getCB_crearEntidad() {
		return CB_crearEntidad;
	}

	public void setCB_crearEntidad(UIComponent cB_crearEntidad) {
		CB_crearEntidad = cB_crearEntidad;
	}

	public SelectItem[] getCaracteristicaItemsPpal() {
		return caracteristicaItemsPpal;
	}

	public void setCaracteristicaItemsPpal(SelectItem[] caracteristicaItemsPpal) {
		this.caracteristicaItemsPpal = caracteristicaItemsPpal;
	}

	public String getLabelFinanciacion() {
		return labelFinanciacion;
	}

	public void setLabelFinanciacion(String labelFinanciacion) {
		this.labelFinanciacion = labelFinanciacion;
	}

	public boolean isTieneFinanciacionGenerica() {
		return tieneFinanciacionGenerica;
	}

	public void setTieneFinanciacionGenerica(boolean tieneFinanciacionGenerica) {
		this.tieneFinanciacionGenerica = tieneFinanciacionGenerica;
	}

	public String getLabelInformacionAcademica() {
		return labelInformacionAcademica;
	}

	public void setLabelInformacionAcademica(String labelInformacionAcademica) {
		this.labelInformacionAcademica = labelInformacionAcademica;
	}

	public String getLabelInformacionGeneral() {
		return labelInformacionGeneral;
	}

	public void setLabelInformacionGeneral(String labelInformacionGeneral) {
		this.labelInformacionGeneral = labelInformacionGeneral;
	}
	
	public void buscarProyectoAsociado() {
		if(proyectoActual.getProyectoAsociado()!=null && proyectoActual.getProyectoAsociado()>0) {
			List<Proyecto> lista = servicioGeneral.obtenerObjetosLimitado(Proyecto.class,
					"select #id a.id, #nombre a.nombre from Proyecto a where " + " a.id = '" + proyectoActual.getProyectoAsociado() + "'");
			Proyecto pry = null;
			if (!esListaVacia(lista)) {
				pry = (Proyecto) lista.get(0);
				nombreProyectoAsociado = pry.getNombre();
				return;
			}else {
				mensajeError("El proyecto a asociar no fue encontrado.");
				nombreProyectoAsociado = "PROYECTO NO ENCONTRADO";
				return;
			}
		}else {
			nombreProyectoAsociado = "PROYECTO NO ENCONTRADO";
			return;
		}
	}

	public String getNombreProyectoAsociado() {
		return nombreProyectoAsociado;
	}

	public void setNombreProyectoAsociado(String nombreProyectoAsociado) {
		this.nombreProyectoAsociado = nombreProyectoAsociado;
	}

	public String getComponentePlanDesarrolloNombre() {
		return componentePlanDesarrolloNombre;
	}

	public void setComponentePlanDesarrolloNombre(String componentePlanDesarrolloNombre) {
		this.componentePlanDesarrolloNombre = componentePlanDesarrolloNombre;
	}

}