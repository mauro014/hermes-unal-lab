package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.AreaTematica;
import co.edu.unal.hermes.modelo.AreaTematicaVista;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DependenciaAreaResponsabilidad;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.ModalidadFuenteFinanciacion;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.RubroFinanciable;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.utils.VariablesEstaticas;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorConvocatoriaLibros extends ManejadorProyecto {

	private Convocatoria convocatoriaActual;
	private PalabraClave palabraClave; // PALABRA CLAVE ACTUAL
	private PalabraClave palabraClaveTabla; // PALABRA CLAVE ACTUAL
	private PalabraClave keyWord; // PALABRA CLAVE ACTUAL
	public List<PalabraClave> listaPalabrasClave;
	private String linkLineas;
	private String areaCiencia;
	private String areaCienciaSec;
	protected static final String DOMINIO_AREA_CIENCIA = "AREA_CIENCIA";
	protected static final String DOMINIO_SUB_AREA_CIENCIA = "SUB_AREA_CIENCIA_FM";
	private List listaAreaCiencia;
	private SelectItem[] areaCienciaItems;
	private String dependenciaAdicionada;
	private List<Dependencia> dependenciasUN;
	public SelectItem[] dependenciaItem;
	public SelectItem[] autores;
	private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad;
	private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada;
	private InvestigadorProyecto investigadorProyectoNuevo;
	private List listaTipoDocumento;
	private SelectItem[] tipoDocumentoItem;
	public List listaInvestigadoresVista;
	private boolean investigadorExiste = true;
	private InvestigadorProyecto copiaValidacionInvestigador;
	private InvestigadorProyecto investigadorProyectoActual;
	private TipoDocumento tipoDocumentoCoInv2;
	private String documentoCoinv2;
	private String tipoInvestigador;
	private List<InvestigadorProyecto> listaParticipantes;
	private List<InvestigadorProyecto> listaParticipantesBorrados;
	private InvestigadorExterno investigadorExterno = new InvestigadorExterno();
	private String insitucionNombre;
	private boolean esOtraVinculacion = false;
	private SelectItem[] generoItem = {
			new SelectItem(VariablesEstaticas.GENERO_FEMENINO, VariablesEstaticas.GENERO_FEMENINO),
			new SelectItem(VariablesEstaticas.GENERO_MASCULINO, VariablesEstaticas.GENERO_MASCULINO) };
	private InvestigadorProyecto participante = new InvestigadorProyecto();
	private boolean proyectoExiste = false;
	private List listaAreasPrimSec;
	private SelectItem[] tipoEventoItems;
	private List<DominioDetalle> listaTipoEvento;
	private boolean mostrarOtroTipoEvento = false;
	private String DOMINIO_TIPO_COLECCION = "COLECCION_CONVOCATORIA_LIBROS";
	private boolean mostrarSiConvLibros = false;
	private boolean mostrarSiArticulosUno = false;
	private boolean mostrarSiArticulosDos = false;
	private boolean mostrarSiConvocatoriaMedicina = false;
	private boolean esConvMinas = false;
	private boolean esConvCienciasMed = false;
	private boolean mostrarODCEMed = false;
	private UIComponent botonAgregarDependencia;
	private String dependenciaProyecto;
	private String sedeSel;
	private String facultadSel;
	private List<Dependencia> facultadesUN;
	private boolean mostrarFacultades = false;
	private SelectItem[] facultadItem;
	// Listas de vista
	private List<Sede> sedesUN;
	private SelectItem[] sedeItem;

	// CONV ARTICULOS
	public String documentoCoinv;
	public TipoDocumento tipoDocumentoCoInv;

	private String nombreCompletoPersonaActual;
	private String nombreSede;
	private String nombreFacultad;
	private String nombreDepartamento;
	private String emailPersonaActual;
	private String telefono;
	private InvestigadorInterno ii;

	private SelectItem[] gruposInvItem;
	private List<Grupo> listaGruposConvMedicina;
	private Grupo grupoSeleccionado;
	private List<Grupo> listaGrupos;
	/** The sub area ciencia. */
	protected String subAreaCiencia;
	/** The sub area ciencia sec items. */
	private SelectItem[] subAreaCienciaSecItems;
	/** The sub area ciencia sec. */
	protected String subAreaCienciaSec;
	/** The sub area ciencia items. */
	private SelectItem[] subAreaCienciaItems;
	/** The lista areas tematicas. */
	protected List<AreaTematicaVista> listaAreasTematicas;
	/** The boton areas tabla. */
	private UIComponent botonAreasTabla;
	/** The area seleccionada. */
	private AreaTematicaVista areaSeleccionada;
	private List listaAreas;

	private Persona personaActual;

	private SelectItem[] objSocioeconomicoItems;
	
	private Long idTipoRubro;
	private SelectItem[] tiposRubroItem;
	private List<TipoRubro> listaTiposRubros;
	private Long valorGasto;
	private String descripcionGasto = "";
	private Gasto gastoSeleccionado;
	
	private Boolean mostrarTituloRevista;

	public ManejadorConvocatoriaLibros() {
		super();
		idManejador = CONVOCATORIA_LIBROS;
		palabraClave = new PalabraClave();
		keyWord = new PalabraClave();
		investigadorProyectoNuevo = new InvestigadorProyecto();
		tipoDocumentoCoInv2 = new TipoDocumento();
		cargarTiposDocumento();
		cargarValoresIniciales();
		listaParticipantes = new ArrayList<InvestigadorProyecto>();
		listaParticipantesBorrados = new ArrayList<InvestigadorProyecto>();
		dependenciaAreaResponsabilidad = new DependenciaAreaResponsabilidad();
		listaAreasPrimSec = new ArrayList<AreaTematica>();
		listaGruposConvMedicina = new ArrayList<Grupo>();
		setObjSocioeconomicoItems(crearListaItemDominioDetalle("OBJETIVO_SOCIO_ECONOMICO"));
		listaAreas = new Vector();
		personaActual = new Persona();

		if ((proyectoActual.getModalidad().getTipo().getId().compareTo("CL") == 0)
				|| (proyectoActual.getModalidad().getTipo().getId().compareTo("CLN") == 0)) {
			mostrarSiConvLibros = true;
			mostrarSiArticulosUno = false;
			mostrarSiArticulosDos = false;
			mostrarSiConvocatoriaMedicina = false;
			
			constructorConvocatoriaMedicina();

			if (proyectoActual.getModalidad() instanceof Convocatoria) {

				RestriccionConvocatoria r = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();
				if (r != null) {
					System.out.println(r.getId());
					if (r.getId().equals("ART_MOD_1")) {
						mostrarSiConvLibros = false;
						mostrarSiArticulosUno = true;
						mostrarSiArticulosDos = false;
						mostrarSiConvocatoriaMedicina = false;
						cargarListaAreas();
						constructorArticulosUno();
						cargarGruposInvestigacion();
						if (((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(675L) 
								|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(795L)
								|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(836L)) {
							setMostrarODCEMed(true);
							cargarAreaPrincipal();
							cargarAreasTematicasSecundarias();
						} else {
							setMostrarODCEMed(false);
							cargarListaAreas();
						}
						if (((Convocatoria) proyectoActual.getModalidad()).getMostrarTituloRevista().equals(1)) {
							setMostrarTituloRevista(true);
						} else {
							setMostrarTituloRevista(false);
						}
					}
					if (r.getId().equals("ART_MOD_2")) {
						mostrarSiConvLibros = false;
						mostrarSiArticulosDos = true;
						mostrarSiArticulosUno = false;
						mostrarSiConvocatoriaMedicina = false;
						cargarListaAreas();
						constructorArticulosDos();
						cargarGruposInvestigacion();
						if (((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(675L) 
								|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(795L)
								|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(836L)) {
							proyectoActual.setTipoZona("ART_COMPLETO");
							setMostrarODCEMed(true);
							cargarAreaPrincipal();
							cargarAreasTematicasSecundarias();
						} else {
							setMostrarODCEMed(false);
							cargarListaAreas();
						}
						if (((Convocatoria) proyectoActual.getModalidad()).getMostrarTituloRevista().equals(1)) {
							setMostrarTituloRevista(true);
						} else {
							setMostrarTituloRevista(false);
						}
					}
					if (r.getId().equals("CONV_EVE_MEDI")) {
						mostrarSiConvLibros = false;
						mostrarSiArticulosDos = false;
						mostrarSiArticulosUno = false;
						mostrarSiConvocatoriaMedicina = true;
						constructorConvocatoriaMedicina();
						cargarGruposInvestigacion();
						if (((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(530L)
								|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(566L)
								|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(568L)
								|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(569L)
								|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(603L)
								|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(634L)
								|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(734L)
								|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(725L)
								|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(795L)
								|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(836L)) {
							setMostrarODCEMed(true);
							cargarAreaPrincipal();
							cargarAreasTematicasSecundarias();
						} else {
							setMostrarODCEMed(false);
							cargarListaAreas();
						}
						if (((Convocatoria) proyectoActual.getModalidad()).getMostrarTituloRevista().equals(1)) {
							setMostrarTituloRevista(true);
						} else {
							setMostrarTituloRevista(false);
						}
					}
					if (r.getId().equals("ARTI_MINAS_2017")) {
						esConvMinas = true;
						mostrarSiConvLibros = false;
						mostrarSiArticulosUno = true;
						mostrarSiArticulosDos = false;
						mostrarSiConvocatoriaMedicina = false;
						cargarListaAreas();
						constructorArticulosUno();
					}
					if (r.getId().equals(RestriccionConvocatoria.CONV_ART_CIEN_MED)) {
						esConvCienciasMed = true;
						esConvMinas = false;
						mostrarSiConvLibros = false;
						mostrarSiArticulosUno = true;
						mostrarSiArticulosDos = false;
						mostrarSiConvocatoriaMedicina = false;
						cargarListaAreas();
						constructorArticulosUno();
						cargarGruposInvestigacion();
					}
				}
			}
		}

		if (proyectoActual.getId() != null) {

			proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.TODO_POR_ID);

			proyectoExiste = true;

			if (mostrarSiConvocatoriaMedicina || ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(675L)) {
				listaGruposConvMedicina.addAll(proyectoActual.getGrupos());
			}
			// // cargar area ciencia principal
			// listaAreasPrimSec =
			// servicioGeneral.obtenerObjetos("select e from AreaTematica e
			// where e.proyecto.id = "
			// + proyectoActual.getId());
			//
			// for (int i = 0; i < listaAreasPrimSec.size(); i++)
			// {
			// AreaTematica at = (AreaTematica) listaAreasPrimSec.get(i);
			// if (at.getTipo() == 1)
			// {
			// areaCiencia =
			// at.getProyectoAreaTematica().getIdentificador().getTipo();
			// }
			// else
			// {
			// areaCienciaSec =
			// at.getProyectoAreaTematica().getIdentificador().getTipo();
			// }
			// }

			// cargar area ciencia secundaria

			// Se cargan areas tematicas
			if (proyectoActual.getId() != null) {
				cargarAreaPrincipal();
				cargarAreasTematicasSecundarias();
			}

			// cargar director del proyecto y participantes del proyecto

			listaInvestigadoresVista = proyectoActual.getObtenerListaInvestigadoresVista();

			if (listaInvestigadoresVista != null) {
				for (int i = 0; i < listaInvestigadoresVista.size(); i++) {
					InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) listaInvestigadoresVista.get(i);
					InvestigadorProyecto ip = ipv.getInvestigadorProyecto();
					listaParticipantes.add(ip);
				}
			} else {
				listaInvestigadoresVista = new ArrayList();
			}

			cambiarTipoEvento();

		} else {
			proyectoExiste = false;
			proyectoActual.cambiarEstadoPersona(EstadoProyecto.INGRESANDO, cargarPersonaActual());
			proyectoActual.setFase(new Integer(0));
			proyectoActual.setDuracion(6);
			// ASIGNACION DEL INVESTIGADOR PRINCIPAL POR DEFECTO
			InvestigadorProyecto investigadorProyecto = new InvestigadorProyecto();
			investigadorProyecto.setInvestigador(
					servicioPersona.obtenerInvestigador(((Persona) sesion.getAttribute("persona")).getId()));
			investigadorProyecto.setProyecto(proyectoActual);
			TipoInvestigador ti = new TipoInvestigador();
			ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(),
					InvestigadorProyecto.PRINCIPAL);
			investigadorProyecto.setTipo(ti);
			proyectoActual.adicionarInvestigadorProyecto(investigadorProyecto);
			listaParticipantes.add(investigadorProyecto);
		}

		try {
			sedeSel = proyectoActual.getSedeEjecucion().getId().toString();
		} catch (Exception e) {
			sedeSel = "";
		}

		try {
			facultadSel = proyectoActual.getFacultadEjecucion().getId().toString();
		} catch (Exception e) {
			facultadSel = "";

		}

		// Se carga listado de sedes
		sedesUN = servicioGeneral.obtenerObjetos(Sede.class, "select e from Sede e where  e.id<>0 ");
		sedeItem = new SelectItem[sedesUN.size()];
		for (int i = 0; i < sedesUN.size(); i++) {
			Sede sede = (Sede) sedesUN.get(i);
			sedeItem[i] = new SelectItem(sede.getId(), sede.getNombre());
		}
		sedeSel = ((Sede) sedesUN.get(0)).getId().toString();
		cambiarSede();
		
		if (isMostrarFinanciacion()) {
			setListaTiposRubros(new ArrayList<TipoRubro>());
			List<FuenteFinanciacion> lf = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class,
					"select e from FuenteFinanciacion e where e.id = 1");
			if (!esListaVacia(lf)) {
				FuenteFinanciacion ff = lf.get(0);
				ingresarFuenteUniversidad(ff, null);
			}
			try {
				Modalidad mod = proyectoActual.getModalidad();
				List<RubroFinanciable> listaRubrosFinanciables = servicioModalidad.obtenerRubrosFinanciables(mod);
				if (!esListaVacia(listaRubrosFinanciables)) {
					setTiposRubroItem(new SelectItem[listaRubrosFinanciables.size()]);
				} else {
					setTiposRubroItem(new SelectItem[0]);
				}
				for (int i = 0; listaRubrosFinanciables != null && i < listaRubrosFinanciables.size(); i++) {
					RubroFinanciable rf = (RubroFinanciable) listaRubrosFinanciables.get(i);
					TipoRubro tr = rf.getTipoRubro();
					getListaTiposRubros().add(tr);
					String nombre = tr.getNombre();
					if (nombre != null && nombre.length() > 120) {
						nombre = nombre.substring(0, 120) + "...";
					}
					getTiposRubroItem()[i] = new SelectItem(tr.getId(), nombre);
					idTipoRubro = 0L;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

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
	 * Cambiar area sec.
	 */
	// Se actualizan el listado de areas de la ciencia segundarias.
	public void cambiarAreaSec() {
		subAreaCienciaSecItems = cambiarAreaCiencia(areaCienciaSec);
		subAreaCienciaSec = "";
	}

	/**
	 * Cambiar area.
	 */
	// Se actualizan el listado de areas de la ciencia primarias.
	public void cambiarArea() {
		subAreaCienciaItems = cambiarAreaCiencia(areaCiencia);
		subAreaCiencia = "";
	}

	/**
	 * Cambiar area ciencia.
	 * 
	 * @param areaCiencia
	 *            the area ciencia
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
	 * Metodo para eliminar una area y agregarla al listado de areas a eliminar
	 * en el boton de guardado.
	 */
	public void eliminarArea() {
		listaAreasTematicas.remove(areaSeleccionada);
		proyectoActual.eliminarAreaTematica(areaSeleccionada.getAreaTematicaProyecto());
	}

	public void cargarGruposInvestigacion() {
		String grupos = "select #id e.id, #nombre e.nombre from Grupo e where e.estadoGrupo.id = 'A'";
		listaGrupos = servicioGeneral.obtenerObjetosLimitado(Grupo.class, grupos);

		gruposInvItem = new SelectItem[listaGrupos.size()];
		for (int i = 0; i < listaGrupos.size(); i++) {
			Grupo dd = (Grupo) listaGrupos.get(i);
			gruposInvItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}
	}

	public void adicionarGrupoInv() {
		Grupo g = servicioGrupo.obtenerGrupo(Long.parseLong(proyectoActual.getAliados()));
		listaGruposConvMedicina.add(g);
	}

	public void eliminarGrupoInv() {
		listaGruposConvMedicina.remove(grupoSeleccionado);
		proyectoActual.borrarGrupo(grupoSeleccionado);
		grupoSeleccionado = new Grupo();
	}

	public void constructorConvocatoriaMedicina() {
		Investigador investigadorActual;

		if (proyectoActual.getId() != null) {
			investigadorActual = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
		} else {
			investigadorActual = servicioPersona
					.obtenerInvestigador(((Persona) sesion.getAttribute("persona")).getId());
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
	}

	public void constructorArticulosDos() {
		Investigador investigadorActual;

		if (proyectoActual.getId() != null) {
			investigadorActual = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
		} else {
			investigadorActual = servicioPersona
					.obtenerInvestigador(((Persona) sesion.getAttribute("persona")).getId());
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
	}

	public void constructorArticulosUno() {

		Investigador investigadorActual;

		if (proyectoActual.getId() != null) {
			investigadorActual = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
		} else {
			investigadorActual = servicioPersona
					.obtenerInvestigador(((Persona) sesion.getAttribute("persona")).getId());
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
	}

	private void cargarTiposDocumento() {
		listaTipoDocumento = servicioGeneral.obtenerListaObjetos("TipoDocumento");
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
	}

	public boolean yaHayPrincipal() {
		// if(proyectoActual.getListaInvestigadoresProyecto().size()>0)
		if (listaParticipantes.size() > 0) {
			// for(Iterator
			// it=proyectoActual.getInvestigadoresProyecto().iterator();it.hasNext();)
			for (Iterator it = listaParticipantes.iterator(); it.hasNext();) {
				InvestigadorProyecto ipc = (InvestigadorProyecto) it.next();
				// it.next();
				if (ipc.getTipo().getId().equals(TipoInvestigador.Principal)) {
					return true;
				}
			}
		}
		return false;
		// return
		// servicioProyecto.tienePrincipalProyecto(proyectoActual.getId());
	}

	public void cambiarVinculacion() {
		System.out.println("otra vinculacion");

		if (tipoInvestigador.equals("AEL")) {
			esOtraVinculacion = true;
		} else {
			esOtraVinculacion = false;
		}

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

	/**
	 * Metodo que se ejecuta cuando cambia alguna sede en la vista.
	 */
	public void cambiarSede() {

		// Si es sede de presencia nacional
		if ("1".equals(sedeSel) || "6".equals(sedeSel) || "7".equals(sedeSel) || "8".equals(sedeSel)
				|| "9".equals(sedeSel)) {

			mostrarFacultades = false;
			dependenciasUN = servicioGeneral.obtenerObjetos(Dependencia.class,
					"select e from Dependencia e where e.sede.id = '" + sedeSel
							+ "' and e.estado='A' order by e.nombre");
			dependenciaItem = cargarListadoDependencia(dependenciasUN);
			dependenciaProyecto = "";

		} else {
			// Si es una sede con facultad.
			mostrarFacultades = true;
			facultadesUN = servicioGeneral.obtenerObjetos(Dependencia.class,
					"select e from Dependencia e where e.sede.id = '" + sedeSel
							+ "' and e.esFacultad = 'Y'  order by e.nombre");
			facultadItem = cargarListadoDependencia(facultadesUN);
			facultadSel = ((Dependencia) facultadesUN.get(0)).getId().toString();
			cambiarFacultad();
		}
	}

	// Se cargan las dependneicas cuando cambia una facultad en la vista.
	public void cambiarFacultad() {
		dependenciasUN = servicioGeneral.obtenerObjetos(Dependencia.class,
				"select e from Dependencia e where e.facultad.id = '" + facultadSel
						+ "' and e.estado = 'A' order by e.nombre");

		// Si tiene mas dependencias la facultad.
		if (!esListaVacia(dependenciasUN)) {
			dependenciaItem = cargarListadoDependencia(dependenciasUN);
		} else {

			// Si no tiene mas dependencias se carga la misma.
			dependenciasUN = servicioGeneral.obtenerObjetos(Dependencia.class,
					"select e from Dependencia e where e.id = '" + facultadSel + "'");
			dependenciaItem = cargarListadoDependencia(dependenciasUN);

		}
		dependenciaProyecto = "";
	}

	/**
	 * Se crea select de dependencias de acuerdo a listado enviados.
	 * 
	 * @param listaDependencias
	 * @return
	 */
	private SelectItem[] cargarListadoDependencia(List<Dependencia> listaDependencias) {
		SelectItem[] dependenciasItemTemporal = new SelectItem[0];
		if (!esListaVacia(listaDependencias)) {
			dependenciasItemTemporal = new SelectItem[listaDependencias.size()];
			for (int i = 0; i < listaDependencias.size(); i++) {
				Dependencia dependencia = (Dependencia) listaDependencias.get(i);
				dependenciasItemTemporal[i] = new SelectItem(dependencia.getId(), dependencia.getNombre());
			}
		}
		return dependenciasItemTemporal;
	}

	public void adicionarDependencia() {
		boolean existeDep = false;
		if (!dependenciaProyecto.equals("")) {
			// Se verifica que no exista.
			if (proyectoActual.getListaDependenciasAreaResponsabilidad().size() > 0) {
				DependenciaAreaResponsabilidad dep = buscarDependenciaAreaResponsabilidad(dependenciaProyecto,
						proyectoActual.getListaDependenciasAreaResponsabilidad());
				if (dep != null) {
					existeDep = true;
				}
			}
			if (!existeDep) {
				// Se busca dependencia en listado de dependencias.
				Dependencia dep = buscarDependencia(dependenciaProyecto, dependenciasUN);

				if (dep != null) {
					DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad = new DependenciaAreaResponsabilidad();
					dependenciaAreaResponsabilidad.setDependencia(dep);
					proyectoActual.adicionarDependencia(dependenciaAreaResponsabilidad);

				} else {
					mensajeError(botonAgregarDependencia, "La dependencia seleccionada no se encuentra disponible.");
				}
			} else {
				mensajeError(botonAgregarDependencia, "La dependencia seleccionada ya se encuentra registrada.");
			}
		} else {
			mensajeError(botonAgregarDependencia, "Por favor seleccione la dependencia a registrar.");
		}
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

	public void cargarListaAreas() {
		listaAreas.add(new SelectItem("ASTRONOMY AND PLANETARY SCIENCE  - Astronomy",
				"ASTRONOMY AND PLANETARY SCIENCE  - Astronomy"));
		listaAreas.add(new SelectItem("ASTRONOMY AND PLANETARY SCIENCE  - Cosmology  ",
				"ASTRONOMY AND PLANETARY SCIENCE  - Cosmology  "));
		listaAreas.add(new SelectItem("ASTRONOMY AND PLANETARY SCIENCE  - Planetary Science  ",
				"ASTRONOMY AND PLANETARY SCIENCE  - Planetary Science  "));
		listaAreas.add(new SelectItem("ASTRONOMY AND PLANETARY SCIENCE  - Theoretical Astrophysics ",
				"ASTRONOMY AND PLANETARY SCIENCE  - Theoretical Astrophysics "));
		listaAreas.add(new SelectItem("ASTRONOMY AND PLANETARY SCIENCE  - Other Astronomy and Planetary Science  ",
				"ASTRONOMY AND PLANETARY SCIENCE  - Other Astronomy and Planetary Science  "));

		listaAreas.add(new SelectItem("BIOLOGY - Agriculture", "BIOLOGY - Agriculture"));
		listaAreas.add(new SelectItem("BIOLOGY - Biochemistry  ", "BIOLOGY - Biochemistry  "));
		listaAreas.add(new SelectItem("BIOLOGY - Bioinformatics  ", "BIOLOGY - Bioinformatics  "));
		listaAreas.add(new SelectItem("BIOLOGY - Biological Chemistry  ", "BIOLOGY - Biological Chemistry  "));
		listaAreas.add(new SelectItem("BIOLOGY - Biological Systematics", "BIOLOGY - Biological Systematics"));
		listaAreas.add(new SelectItem("BIOLOGY - Biophysics  ", "BIOLOGY - Biophysics  "));
		listaAreas.add(new SelectItem("BIOLOGY - Biotechnology  ", "BIOLOGY - Biotechnology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Cancer Research  ", "BIOLOGY - Cancer Research  "));
		listaAreas.add(new SelectItem("BIOLOGY - Cardiovascular Biology  ", "BIOLOGY - Cardiovascular Biology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Cell Biology  ", "BIOLOGY - Cell Biology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Computational Biology  ", "BIOLOGY - Computational Biology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Conservation Biology  ", "BIOLOGY - Conservation Biology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Developmental Biology  ", "BIOLOGY - Developmental Biology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Developmental Genetics", "BIOLOGY - Developmental Genetics"));
		listaAreas.add(new SelectItem("BIOLOGY - Drug Discovery  ", "BIOLOGY - Drug Discovery  "));
		listaAreas.add(new SelectItem("BIOLOGY - Ecology  ", "BIOLOGY - Ecology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Entomology", "BIOLOGY - Entomology"));
		listaAreas.add(new SelectItem("BIOLOGY - Epigenetics", "BIOLOGY - Epigenetics"));
		listaAreas.add(new SelectItem("BIOLOGY - Evolution  ", "BIOLOGY - Evolution  "));
		listaAreas.add(new SelectItem("BIOLOGY - Genetics  ", "BIOLOGY - Genetics  "));
		listaAreas.add(new SelectItem("BIOLOGY - Genomics  ", "BIOLOGY - Genomics  "));
		listaAreas.add(new SelectItem("BIOLOGY - Immunology  ", "BIOLOGY - Immunology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Industrial Microbiology  ", "BIOLOGY - Industrial Microbiology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Marine Biology  ", "BIOLOGY - Marine Biology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Microbiology  ", "BIOLOGY - Microbiology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Molecular Biology", "BIOLOGY - Molecular Biology"));
		listaAreas.add(new SelectItem("BIOLOGY - Molecular Epidemiology", "BIOLOGY - Molecular Epidemiology"));
		listaAreas.add(new SelectItem("BIOLOGY - Mycology  ", "BIOLOGY - Mycology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Neuroscience", "BIOLOGY - Neuroscience"));
		listaAreas.add(new SelectItem("BIOLOGY - Neuroscience – Behavioral / Systems / Cognitive",
				"BIOLOGY - Neuroscience – Behavioral / Systems / Cognitive"));
		listaAreas.add(new SelectItem("BIOLOGY - Neuroscience – Cellular / Molecular",
				"BIOLOGY - Neuroscience – Cellular / Molecular"));
		listaAreas.add(
				new SelectItem("BIOLOGY - Neuroscience – Computational", "BIOLOGY - Neuroscience – Computational"));
		listaAreas.add(new SelectItem("BIOLOGY - Neuroscience – Development / Plasticity / Repair",
				"BIOLOGY - Neuroscience – Development / Plasticity / Repair"));
		listaAreas.add(new SelectItem("BIOLOGY - Neuroscience – Neurobiology of disease",
				"BIOLOGY - Neuroscience – Neurobiology of disease"));
		listaAreas.add(new SelectItem("BIOLOGY - Ornithology", "BIOLOGY - Ornithology"));
		listaAreas.add(new SelectItem("BIOLOGY - Parasitology  ", "BIOLOGY - Parasitology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Pharmacology  ", "BIOLOGY - Pharmacology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Plant Science – cellular/morphological ",
				"BIOLOGY - Plant Science – cellular/morphological "));
		listaAreas.add(new SelectItem("BIOLOGY - Plant Science – molecular/genetics",
				"BIOLOGY - Plant Science – molecular/genetics"));
		listaAreas.add(new SelectItem("BIOLOGY - Proteomics  ", "BIOLOGY - Proteomics  "));
		listaAreas.add(new SelectItem("BIOLOGY - Stem Cell Biology", "BIOLOGY - Stem Cell Biology"));
		listaAreas.add(new SelectItem("BIOLOGY - Structural Biology  ", "BIOLOGY - Structural Biology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Systems Biology  ", "BIOLOGY - Systems Biology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Taxonomy", "BIOLOGY - Taxonomy"));
		listaAreas.add(new SelectItem("BIOLOGY - Zoology  ", "BIOLOGY - Zoology  "));
		listaAreas.add(new SelectItem("BIOLOGY - Other Biology", "BIOLOGY - Other Biology"));

		listaAreas.add(new SelectItem("BUSINESS - Accounting  ", "BUSINESS - Accounting  "));
		listaAreas.add(new SelectItem("BUSINESS - Finance  ", "BUSINESS - Finance  "));
		listaAreas.add(new SelectItem("BUSINESS - Hospitality/Tourism", "BUSINESS - Hospitality/Tourism"));
		listaAreas.add(new SelectItem("BUSINESS - International Business  ", "BUSINESS - International Business  "));
		listaAreas.add(new SelectItem("BUSINESS - Management  ", "BUSINESS - Management  "));
		listaAreas.add(new SelectItem("BUSINESS - Marketing/PR  ", "BUSINESS - Marketing/PR  "));
		listaAreas.add(new SelectItem("BUSINESS - Patents  ", "BUSINESS - Patents  "));
		listaAreas.add(new SelectItem("BUSINESS - Other Business  ", "BUSINESS - Other Business  "));

		listaAreas.add(new SelectItem("CHEMISTRY - Analytical Chemistry  ", "CHEMISTRY - Analytical Chemistry  "));
		listaAreas.add(new SelectItem("CHEMISTRY - Applied Chemistry  ", "CHEMISTRY - Applied Chemistry  "));
		listaAreas.add(new SelectItem("CHEMISTRY - Biotechnology", "CHEMISTRY - Biotechnology"));
		listaAreas.add(new SelectItem("CHEMISTRY - Catalysis  ", "CHEMISTRY - Catalysis  "));
		listaAreas.add(new SelectItem("CHEMISTRY - Chemical Biology  ", "CHEMISTRY - Chemical Biology  "));
		listaAreas.add(new SelectItem("CHEMISTRY - Chemical Engineering", "CHEMISTRY - Chemical Engineering"));
		listaAreas
				.add(new SelectItem("CHEMISTRY - Computational Chemistry  ", "CHEMISTRY - Computational Chemistry  "));
		listaAreas.add(new SelectItem("CHEMISTRY - Crystallography", "CHEMISTRY - Crystallography"));
		listaAreas.add(new SelectItem("CHEMISTRY - Drug Discovery", "CHEMISTRY - Drug Discovery"));
		listaAreas.add(new SelectItem("CHEMISTRY - Electrochemistry", "CHEMISTRY - Electrochemistry"));
		listaAreas
				.add(new SelectItem("CHEMISTRY - Environmental Chemistry  ", "CHEMISTRY - Environmental Chemistry  "));
		listaAreas.add(new SelectItem("CHEMISTRY - Food Science", "CHEMISTRY - Food Science"));
		listaAreas.add(new SelectItem("CHEMISTRY - Inorganic Chemistry  ", "CHEMISTRY - Inorganic Chemistry  "));
		listaAreas.add(new SelectItem("CHEMISTRY - Mass Spectrometry", "CHEMISTRY - Mass Spectrometry"));
		listaAreas.add(new SelectItem("CHEMISTRY - Materials Chemistry  ", "CHEMISTRY - Materials Chemistry  "));
		listaAreas.add(new SelectItem("CHEMISTRY - Materials Science", "CHEMISTRY - Materials Science"));
		listaAreas.add(new SelectItem("CHEMISTRY - Medicinal & Pharmaceutical Chemistry  ",
				"CHEMISTRY - Medicinal & Pharmaceutical Chemistry  "));
		listaAreas.add(new SelectItem("CHEMISTRY - Nanoscience", "CHEMISTRY - Nanoscience"));
		listaAreas.add(new SelectItem("CHEMISTRY - Nuclear Chemistry  ", "CHEMISTRY - Nuclear Chemistry  "));
		listaAreas.add(new SelectItem("CHEMISTRY - Organic Chemistry  ", "CHEMISTRY - Organic Chemistry  "));
		listaAreas.add(new SelectItem("CHEMISTRY - Physical Chemistry  ", "CHEMISTRY - Physical Chemistry  "));
		listaAreas.add(new SelectItem("CHEMISTRY - Polymer Science", "CHEMISTRY - Polymer Science"));
		listaAreas.add(new SelectItem("CHEMISTRY - Spectroscopy  ", "CHEMISTRY - Spectroscopy  "));
		listaAreas.add(new SelectItem("CHEMISTRY - Theoretical Chemistry  ", "CHEMISTRY - Theoretical Chemistry  "));
		listaAreas.add(new SelectItem("CHEMISTRY - Other Chemistry  ", "CHEMISTRY - Other Chemistry  "));

		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Agriculture",
				"EARTH AND ENVIRONMENTAL SCIENCE - Agriculture"));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Agronomy",
				"EARTH AND ENVIRONMENTAL SCIENCE - Agronomy"));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Atmospheric Science/Climate ",
				"EARTH AND ENVIRONMENTAL SCIENCE - Atmospheric Science/Climate "));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Forestry",
				"EARTH AND ENVIRONMENTAL SCIENCE - Forestry"));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Geochemistry",
				"EARTH AND ENVIRONMENTAL SCIENCE - Geochemistry"));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Geographic Information Systems",
				"EARTH AND ENVIRONMENTAL SCIENCE - Geographic Information Systems"));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Geography ",
				"EARTH AND ENVIRONMENTAL SCIENCE - Geography "));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Geology  ",
				"EARTH AND ENVIRONMENTAL SCIENCE - Geology  "));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Geophysics  ",
				"EARTH AND ENVIRONMENTAL SCIENCE - Geophysics  "));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Geoscience  ",
				"EARTH AND ENVIRONMENTAL SCIENCE - Geoscience  "));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Meteorology",
				"EARTH AND ENVIRONMENTAL SCIENCE - Meteorology"));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Oceanography  ",
				"EARTH AND ENVIRONMENTAL SCIENCE - Oceanography  "));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Palaeoclimate  ",
				"EARTH AND ENVIRONMENTAL SCIENCE - Palaeoclimate  "));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Palaeontology  ",
				"EARTH AND ENVIRONMENTAL SCIENCE - Palaeontology  "));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Planetary Geology  ",
				"EARTH AND ENVIRONMENTAL SCIENCE - Planetary Geology  "));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Solid Earth Science  ",
				"EARTH AND ENVIRONMENTAL SCIENCE - Solid Earth Science  "));
		listaAreas.add(new SelectItem("EARTH AND ENVIRONMENTAL SCIENCE - Other Environmental Science  ",
				"EARTH AND ENVIRONMENTAL SCIENCE - Other Environmental Science  "));

		listaAreas.add(new SelectItem("ENGINEERING - Acoustics", "ENGINEERING - Acoustics"));
		listaAreas.add(
				new SelectItem("ENGINEERING - Aeronautical Engineering  ", "ENGINEERING - Aeronautical Engineering  "));
		listaAreas.add(new SelectItem("ENGINEERING - Biomechanics", "ENGINEERING - Biomechanics"));
		listaAreas.add(
				new SelectItem("ENGINEERING - Biomedical Engineering  ", "ENGINEERING - Biomedical Engineering  "));
		listaAreas.add(new SelectItem("ENGINEERING - Chemical Engineering  ", "ENGINEERING - Chemical Engineering  "));
		listaAreas.add(new SelectItem("ENGINEERING - Civil Engineering  ", "ENGINEERING - Civil Engineering  "));
		listaAreas.add(new SelectItem("ENGINEERING - Electrical/Electronic Engineering  ",
				"ENGINEERING - Electrical/Electronic Engineering  "));
		listaAreas.add(new SelectItem("ENGINEERING - Environmental Engineering  ",
				"ENGINEERING - Environmental Engineering  "));
		listaAreas
				.add(new SelectItem("ENGINEERING - Materials Engineering  ", "ENGINEERING - Materials Engineering  "));
		listaAreas.add(new SelectItem("ENGINEERING - Materials Science", "ENGINEERING - Materials Science"));
		listaAreas.add(
				new SelectItem("ENGINEERING - Mechanical Engineering  ", "ENGINEERING - Mechanical Engineering  "));
		listaAreas.add(new SelectItem("ENGINEERING - Nuclear Engineering  ", "ENGINEERING - Nuclear Engineering  "));
		listaAreas.add(new SelectItem("ENGINEERING - Robotics", "ENGINEERING - Robotics"));
		listaAreas.add(new SelectItem("ENGINEERING - Software Engineering  ", "ENGINEERING - Software Engineering  "));
		listaAreas.add(new SelectItem("ENGINEERING - Systems/Industrial Engineering  ",
				"ENGINEERING - Systems/Industrial Engineering  "));
		listaAreas.add(new SelectItem("ENGINEERING - Tissue Engineering", "ENGINEERING - Tissue Engineering"));
		listaAreas.add(new SelectItem("ENGINEERING - Other Engineering  ", "ENGINEERING - Other Engineering  "));

		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Anthropology",
				"HUMANITIES/SOCIAL SCIENCES - Anthropology"));
		listaAreas.add(
				new SelectItem("HUMANITIES/SOCIAL SCIENCES - Archaeology", "HUMANITIES/SOCIAL SCIENCES - Archaeology"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Architecture",
				"HUMANITIES/SOCIAL SCIENCES - Architecture"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Communications",
				"HUMANITIES/SOCIAL SCIENCES - Communications"));
		listaAreas.add(
				new SelectItem("HUMANITIES/SOCIAL SCIENCES - Criminology", "HUMANITIES/SOCIAL SCIENCES - Criminology"));
		listaAreas.add(
				new SelectItem("HUMANITIES/SOCIAL SCIENCES - Economics", "HUMANITIES/SOCIAL SCIENCES - Economics"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Economics – International",
				"HUMANITIES/SOCIAL SCIENCES - Economics – International"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Economics – Micro",
				"HUMANITIES/SOCIAL SCIENCES - Economics – Micro"));
		listaAreas.add(
				new SelectItem("HUMANITIES/SOCIAL SCIENCES - Education", "HUMANITIES/SOCIAL SCIENCES - Education"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Environmental Economics",
				"HUMANITIES/SOCIAL SCIENCES - Environmental Economics"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Environmental Policy",
				"HUMANITIES/SOCIAL SCIENCES - Environmental Policy"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Ethics", "HUMANITIES/SOCIAL SCIENCES - Ethics"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - History", "HUMANITIES/SOCIAL SCIENCES - History"));
		listaAreas.add(
				new SelectItem("HUMANITIES/SOCIAL SCIENCES - Humanities", "HUMANITIES/SOCIAL SCIENCES - Humanities"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Information/Library Science",
				"HUMANITIES/SOCIAL SCIENCES - Information/Library Science"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Language/Linguistics",
				"HUMANITIES/SOCIAL SCIENCES - Language/Linguistics"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Law", "HUMANITIES/SOCIAL SCIENCES - Law"));
		listaAreas.add(
				new SelectItem("HUMANITIES/SOCIAL SCIENCES - Philosophy", "HUMANITIES/SOCIAL SCIENCES - Philosophy"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Policy & Social Sciences",
				"HUMANITIES/SOCIAL SCIENCES - Policy & Social Sciences"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Political Science",
				"HUMANITIES/SOCIAL SCIENCES - Political Science"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Political Science – Comparative",
				"HUMANITIES/SOCIAL SCIENCES - Political Science – Comparative"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Political Science – International",
				"HUMANITIES/SOCIAL SCIENCES - Political Science – International"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Publishing/Media",
				"HUMANITIES/SOCIAL SCIENCES - Publishing/Media"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Religious Studies",
				"HUMANITIES/SOCIAL SCIENCES - Religious Studies"));
		listaAreas.add(
				new SelectItem("HUMANITIES/SOCIAL SCIENCES - Sociology", "HUMANITIES/SOCIAL SCIENCES - Sociology"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Urban Studies",
				"HUMANITIES/SOCIAL SCIENCES - Urban Studies"));
		listaAreas.add(new SelectItem("HUMANITIES/SOCIAL SCIENCES - Women´s Studies",
				"HUMANITIES/SOCIAL SCIENCES - Women´s Studies"));

		listaAreas.add(new SelectItem("MATHEMATICS/COMPUTER SCIENCE - Applied Mathematics",
				"MATHEMATICS/COMPUTER SCIENCE - Applied Mathematics"));
		listaAreas.add(new SelectItem("MATHEMATICS/COMPUTER SCIENCE - Computer Science",
				"MATHEMATICS/COMPUTER SCIENCE - Computer Science"));
		listaAreas.add(new SelectItem("MATHEMATICS/COMPUTER SCIENCE - Pure Mathematics",
				"MATHEMATICS/COMPUTER SCIENCE - Pure Mathematics"));
		listaAreas.add(new SelectItem("MATHEMATICS/COMPUTER SCIENCE - Statistics",
				"MATHEMATICS/COMPUTER SCIENCE - Statistics"));

		listaAreas.add(new SelectItem("MEDICINE - Allergy  ", "MEDICINE - Allergy  "));
		listaAreas.add(new SelectItem("MEDICINE - Anatomy", "MEDICINE - Anatomy"));
		listaAreas.add(new SelectItem("MEDICINE - Anesthesiology  ", "MEDICINE - Anesthesiology  "));
		listaAreas.add(new SelectItem("MEDICINE - Audiology", "MEDICINE - Audiology"));
		listaAreas.add(new SelectItem("MEDICINE - Cancer/Oncology  ", "MEDICINE - Cancer/Oncology  "));
		listaAreas.add(
				new SelectItem("MEDICINE - Cardiac Electrophysiology  ", "MEDICINE - Cardiac Electrophysiology  "));
		listaAreas.add(
				new SelectItem("MEDICINE - Cardiology - Interventional  ", "MEDICINE - Cardiology - Interventional  "));
		listaAreas
				.add(new SelectItem("MEDICINE - Cardiology - Noninvasive  ", "MEDICINE - Cardiology - Noninvasive  "));
		listaAreas.add(
				new SelectItem("MEDICINE - Cardiology and Circulation  ", "MEDICINE - Cardiology and Circulation  "));
		listaAreas.add(new SelectItem("MEDICINE - Childhood Development", "MEDICINE - Childhood Development"));
		listaAreas.add(new SelectItem("MEDICINE - Clinical Genetics  ", "MEDICINE - Clinical Genetics  "));
		listaAreas.add(new SelectItem("MEDICINE - Clinical Immunology  ", "MEDICINE - Clinical Immunology  "));
		listaAreas.add(new SelectItem("MEDICINE - Clinical Pharmacology  ", "MEDICINE - Clinical Pharmacology  "));
		listaAreas.add(new SelectItem("MEDICINE - Clinical Psychology  ", "MEDICINE - Clinical Psychology  "));
		listaAreas.add(new SelectItem("MEDICINE - Clinical Trials  ", "MEDICINE - Clinical Trials  "));
		listaAreas.add(new SelectItem("MEDICINE - Critical Care Medicine  ", "MEDICINE - Critical Care Medicine  "));
		listaAreas.add(new SelectItem("MEDICINE - Dentistry /Oral Surgery", "MEDICINE - Dentistry /Oral Surgery"));
		listaAreas.add(new SelectItem("MEDICINE - Dermatology  ", "MEDICINE - Dermatology  "));
		listaAreas.add(new SelectItem("MEDICINE - Dermatopathology  ", "MEDICINE - Dermatopathology  "));
		listaAreas.add(new SelectItem("MEDICINE - Developmental Psychology", "MEDICINE - Developmental Psychology"));
		listaAreas.add(new SelectItem("MEDICINE - Diabetes  ", "MEDICINE - Diabetes  "));
		listaAreas.add(new SelectItem("MEDICINE - Emergency Medicine  ", "MEDICINE - Emergency Medicine  "));
		listaAreas.add(new SelectItem("MEDICINE - Endocrinology  ", "MEDICINE - Endocrinology  "));
		listaAreas.add(new SelectItem("MEDICINE - Epidemiology  ", "MEDICINE - Epidemiology  "));
		listaAreas.add(new SelectItem("MEDICINE - Family Practice  ", "MEDICINE - Family Practice  "));
		listaAreas.add(new SelectItem("MEDICINE - Gastroenterology  ", "MEDICINE - Gastroenterology  "));
		listaAreas.add(new SelectItem("MEDICINE - General Practice  ", "MEDICINE - General Practice  "));
		listaAreas.add(new SelectItem("MEDICINE - Geriatrics  ", "MEDICINE - Geriatrics  "));
		listaAreas.add(new SelectItem("MEDICINE - Gynecology  ", "MEDICINE - Gynecology  "));
		listaAreas.add(new SelectItem("MEDICINE - Health Economics and Outcomes Research  ",
				"MEDICINE - Health Economics and Outcomes Research  "));
		listaAreas.add(new SelectItem("MEDICINE - Hematology  ", "MEDICINE - Hematology  "));
		listaAreas.add(new SelectItem("MEDICINE - Hematology - Oncology  ", "MEDICINE - Hematology - Oncology  "));
		listaAreas.add(new SelectItem("MEDICINE - Hepatology  ", "MEDICINE - Hepatology  "));
		listaAreas.add(new SelectItem("MEDICINE - Hypertension  ", "MEDICINE - Hypertension  "));
		listaAreas.add(new SelectItem("MEDICINE - Infectious Diseases  ", "MEDICINE - Infectious Diseases  "));
		listaAreas.add(new SelectItem("MEDICINE - Internal Medicine  ", "MEDICINE - Internal Medicine  "));
		listaAreas.add(
				new SelectItem("MEDICINE - Maternal & Fetal Medicine  ", "MEDICINE - Maternal & Fetal Medicine  "));
		listaAreas.add(new SelectItem("MEDICINE - Medical Physics", "MEDICINE - Medical Physics"));
		listaAreas.add(new SelectItem("MEDICINE - Metabolism", "MEDICINE - Metabolism"));
		listaAreas.add(new SelectItem("MEDICINE - Molecular Epidemiology", "MEDICINE - Molecular Epidemiology"));
		listaAreas.add(
				new SelectItem("MEDICINE - Neonatal-Perinatal Medicine  ", "MEDICINE - Neonatal-Perinatal Medicine  "));
		listaAreas.add(new SelectItem("MEDICINE - Nephrology  ", "MEDICINE - Nephrology  "));
		listaAreas.add(new SelectItem("MEDICINE - Neurology  ", "MEDICINE - Neurology  "));
		listaAreas.add(new SelectItem("MEDICINE - Neurology - Child  ", "MEDICINE - Neurology - Child  "));
		listaAreas.add(new SelectItem("MEDICINE - Nuclear Medicine  ", "MEDICINE - Nuclear Medicine  "));
		listaAreas.add(new SelectItem("MEDICINE - Nursing  ", "MEDICINE - Nursing  "));
		listaAreas.add(new SelectItem("MEDICINE - Nutrition  ", "MEDICINE - Nutrition  "));
		listaAreas.add(new SelectItem("MEDICINE - Obstetrics & Gynecology  ", "MEDICINE - Obstetrics & Gynecology  "));
		listaAreas.add(new SelectItem("MEDICINE - Occupational Medicine  ", "MEDICINE - Occupational Medicine  "));
		listaAreas.add(new SelectItem("MEDICINE - Oncology - Medical  ", "MEDICINE - Oncology - Medical  "));
		listaAreas.add(new SelectItem("MEDICINE - Oncology - Radiation  ", "MEDICINE - Oncology - Radiation  "));
		listaAreas.add(new SelectItem("MEDICINE - Oncology - Surgical  ", "MEDICINE - Oncology - Surgical  "));
		listaAreas.add(new SelectItem("MEDICINE - Ophthalmology  ", "MEDICINE - Ophthalmology  "));
		listaAreas.add(new SelectItem("MEDICINE - Orthopedics", "MEDICINE - Orthopedics"));
		listaAreas.add(new SelectItem("MEDICINE - Otorhinolaryngology  ", "MEDICINE - Otorhinolaryngology  "));
		listaAreas.add(new SelectItem("MEDICINE - Pain Medicine  ", "MEDICINE - Pain Medicine  "));
		listaAreas.add(new SelectItem("MEDICINE - Palliative Medicine  ", "MEDICINE - Palliative Medicine  "));
		listaAreas.add(new SelectItem("MEDICINE - Pathology  ", "MEDICINE - Pathology  "));
		listaAreas.add(new SelectItem("MEDICINE - Pediatrics  ", "MEDICINE - Pediatrics  "));
		listaAreas.add(
				new SelectItem("MEDICINE - Physical Medicine & Rehab  ", "MEDICINE - Physical Medicine & Rehab  "));
		listaAreas.add(new SelectItem("MEDICINE - Physiology  ", "MEDICINE - Physiology  "));
		listaAreas.add(new SelectItem("MEDICINE - Preventive Medicine  ", "MEDICINE - Preventive Medicine  "));
		listaAreas.add(new SelectItem("MEDICINE - Psychiatry  ", "MEDICINE - Psychiatry  "));
		listaAreas.add(new SelectItem("MEDICINE - Psychiatry - Addiction /Substance Abuse",
				"MEDICINE - Psychiatry - Addiction /Substance Abuse"));
		listaAreas.add(new SelectItem("MEDICINE - Psychiatry - Child  ", "MEDICINE - Psychiatry - Child  "));
		listaAreas.add(new SelectItem("MEDICINE - Psychiatry - General  ", "MEDICINE - Psychiatry - General  "));
		listaAreas.add(new SelectItem("MEDICINE - Psychiatry - Geriatric  ", "MEDICINE - Psychiatry - Geriatric  "));
		listaAreas.add(new SelectItem("MEDICINE - Psychology  ", "MEDICINE - Psychology  "));
		listaAreas.add(new SelectItem("MEDICINE - Public Health  ", "MEDICINE - Public Health  "));
		listaAreas.add(new SelectItem("MEDICINE - Pulmonary Disease  ", "MEDICINE - Pulmonary Disease  "));
		listaAreas.add(new SelectItem("MEDICINE - Radiation  ", "MEDICINE - Radiation  "));
		listaAreas.add(new SelectItem("MEDICINE - Radiology  ", "MEDICINE - Radiology  "));
		listaAreas.add(
				new SelectItem("MEDICINE - Reproductive Endocrinology  ", "MEDICINE - Reproductive Endocrinology  "));
		listaAreas.add(new SelectItem("MEDICINE - Rheumatology  ", "MEDICINE - Rheumatology  "));
		listaAreas.add(new SelectItem("MEDICINE - Sexual Dysfunction  ", "MEDICINE - Sexual Dysfunction  "));
		listaAreas.add(new SelectItem("MEDICINE - Social Work", "MEDICINE - Social Work"));
		listaAreas.add(new SelectItem("MEDICINE - Speech/Language Pathology", "MEDICINE - Speech/Language Pathology"));
		listaAreas.add(new SelectItem("MEDICINE - Spinal Cord Injury ", "MEDICINE - Spinal Cord Injury "));
		listaAreas.add(new SelectItem("MEDICINE - Sports Medicine", "MEDICINE - Sports Medicine"));
		listaAreas.add(new SelectItem("MEDICINE - Stem Cell Biology ", "MEDICINE - Stem Cell Biology "));
		listaAreas.add(new SelectItem("MEDICINE - Surgery - General  ", "MEDICINE - Surgery - General  "));
		listaAreas.add(new SelectItem("MEDICINE - Surgery - Specialist  ", "MEDICINE - Surgery - Specialist  "));
		listaAreas.add(new SelectItem("MEDICINE - Toxicology  ", "MEDICINE - Toxicology  "));
		listaAreas.add(new SelectItem("MEDICINE - Transplantation", "MEDICINE - Transplantation"));
		listaAreas.add(new SelectItem("MEDICINE - Tropical Medicine", "MEDICINE - Tropical Medicine"));
		listaAreas.add(new SelectItem("MEDICINE - Urology  ", "MEDICINE - Urology  "));
		listaAreas.add(new SelectItem("MEDICINE - Veterinary Science  ", "MEDICINE - Veterinary Science  "));
		listaAreas.add(new SelectItem("MEDICINE - Virology  ", "MEDICINE - Virology  "));
		listaAreas.add(new SelectItem("MEDICINE - Other Clinical Medicine  ", "MEDICINE - Other Clinical Medicine  "));

		listaAreas.add(
				new SelectItem("PHYSICS - Atomic and Molecular Physics  ", "PHYSICS - Atomic and Molecular Physics  "));
		listaAreas.add(new SelectItem("PHYSICS - Biological Physics  ", "PHYSICS - Biological Physics  "));
		listaAreas.add(new SelectItem("PHYSICS - Computational Physics  ", "PHYSICS - Computational Physics  "));
		listaAreas.add(new SelectItem("PHYSICS - Condensed-matter Physics  ", "PHYSICS - Condensed-matter Physics  "));
		listaAreas.add(new SelectItem("PHYSICS - High-energy Physics  ", "PHYSICS - High-energy Physics  "));
		listaAreas.add(new SelectItem("PHYSICS - Materials Physics  ", "PHYSICS - Materials Physics  "));
		listaAreas.add(new SelectItem("PHYSICS - Materials Science", "PHYSICS - Materials Science"));
		listaAreas.add(new SelectItem("PHYSICS - Medical Physics", "PHYSICS - Medical Physics"));
		listaAreas.add(new SelectItem("PHYSICS - Nanoscience", "PHYSICS - Nanoscience"));
		listaAreas.add(new SelectItem("PHYSICS - Nuclear Physics  ", "PHYSICS - Nuclear Physics  "));
		listaAreas.add(new SelectItem("PHYSICS - Optics/Lasers  ", "PHYSICS - Optics/Lasers  "));
		listaAreas.add(new SelectItem("PHYSICS - Plasma and Fluids  ", "PHYSICS - Plasma and Fluids  "));
		listaAreas.add(new SelectItem("PHYSICS - Other Physics  ", "PHYSICS - Other Physics  "));

		listaAreas.add(new SelectItem("OTHER FIELDS - General science (non-professional)  ",
				"OTHER FIELDS - General science (non-professional)  "));
		listaAreas.add(new SelectItem("OTHER FIELDS - Other", "OTHER FIELDS - Other"));
	}

	@Override
	protected void cargarValoresIniciales() {

		// linkLineas =
		// "http://www.hermes.unal.edu.co/pages/descargas/AreasCiencia.pdf";
		//
		// String consulta1 =
		// "select dd from Dominio d, DominioDetalle dd where d.id =
		// dd.identificador.id and d.tipo ='"
		// + DOMINIO_AREA_CIENCIA + "' order by dd.descripcion";
		// listaAreaCiencia = servicioGeneral.obtenerObjetos(consulta1);
		//
		// if (listaAreaCiencia.size() > 0)
		// {
		// areaCienciaItems = new SelectItem[listaAreaCiencia.size()];
		// for (int i = 0; i < listaAreaCiencia.size(); i++)
		// {
		// DominioDetalle dominio = (DominioDetalle) listaAreaCiencia.get(i);
		// areaCienciaItems[i] = new
		// SelectItem(dominio.getIdentificador().getTipo(),
		// dominio.getDescripcion());
		// }
		// }
		// else
		// {
		// areaCienciaItems = new SelectItem[1];
		// areaCienciaItems[0] = new SelectItem("0", " - ");
		//
		// }

		listaAreasTematicas = new ArrayList<AreaTematicaVista>();

		boolean validarEstado = false;
		List<DominioDetalle> listaAreaCiencia = servicioGeneral.obtenerDominioDetalle(DOMINIO_AREA_CIENCIA,
				validarEstado);
		areaCienciaItems = crearListaItems(listaAreaCiencia);

		cambiarArea();
		cambiarAreaSec();

		dependenciasUN = new ArrayList<Dependencia>();
		dependenciasUN = servicioGeneral.obtenerObjetos("select e from Dependencia e");
		dependenciaItem = new SelectItem[dependenciasUN.size() + 1];
		for (int i = 0; i < dependenciasUN.size(); i++) {
			Dependencia dd = (Dependencia) dependenciasUN.get(i);
			dependenciaItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}
		dependenciaItem[dependenciasUN.size()] = new SelectItem("--", "Por favor seleccione la dependencia");
		dependenciaAdicionada = "--";

		List listaAutores = servicioGeneral
				.obtenerObjetos("select e from TipoInvestigador e where e.tipoModalidad = 'CL'");
		autores = new SelectItem[listaAutores.size()];
		for (int i = 0; i < listaAutores.size(); i++) {
			TipoInvestigador ta = (TipoInvestigador) listaAutores.get(i);
			autores[i] = new SelectItem(ta.getId(), ta.getNombre());
			ta = null;
		}

		listaTipoEvento = new ArrayList<DominioDetalle>();
		listaTipoEvento = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ DOMINIO_TIPO_COLECCION + "' order by dd.descripcion");
		tipoEventoItems = new SelectItem[listaTipoEvento.size()];
		for (int i = 0; i < listaTipoEvento.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaTipoEvento.get(i);
			tipoEventoItems[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}

	}

	public void cambiarTipoEvento() {

		if (proyectoActual.getClaseEvento() != null) {
			if (proyectoActual.getClaseEvento().equals("COL_OTRA")) {
				mostrarOtroTipoEvento = true;
			} else {
				mostrarOtroTipoEvento = false;
			}
		}

	}

	public void agregarParticipante() {
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

			if (!encuentraParticipante) {

				try {

					if (tipoInvestigador.equals("AI")) { // si es docente

						InvestigadorInterno investigadorInterno = servicioPersona
								.obtenerInvestigadorInterno(new IdPersona

						(this.documentoCoinv2, this.tipoDocumentoCoInv2.getId()));

						if (investigadorInterno != null) {

							// Agregar participante a la listaparticipante
							InvestigadorProyecto participante = new InvestigadorProyecto();
							participante.setInvestigador(investigadorInterno);
							participante.setDedicacionHorasSemana(Short.parseShort("0"));
							participante.setFuncion("Autor interno");
							participante.setProyecto(proyectoActual);
							TipoInvestigador ti = new TipoInvestigador();
							ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(),
									tipoInvestigador);
							participante.setTipo(ti);

							listaParticipantes.add(participante);

						} else { // No se encontró como investigador interno
									// si es estudiante
							IdPersona idEst = new IdPersona(this.documentoCoinv2, this.tipoDocumentoCoInv2.getId());
							Estudiante e = servicioPersona.obtenerEstudiante(idEst);

							if (e != null) {

								nuevoInvestigador = servicioPersona.obtenerInvestigador(idEst);
								if (nuevoInvestigador == null) {
									InvestigadorInterno nvoinv = e.convertirAInvestigador();
									// nvoinv.setDependencia(buscarFacultad(facultad));
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
								participante.setDedicacionHorasSemana(Short.parseShort("0"));
								participante.setFuncion("Participante");
								participante.setProyecto(proyectoActual);
								TipoInvestigador ti = new TipoInvestigador();
								ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(),
										tipoInvestigador.toString());
								participante.setTipo(ti);

								listaParticipantes.add(participante);

							} else { // si no se encontró como estudiante

							}

						}

					} else { // si es otro

						if (investigadorExterno.getNombre1() != null && !investigadorExterno.getNombre1().equals("")
								&& investigadorExterno.getApellido1() != null
								&& !investigadorExterno.getApellido1().equals("") && insitucionNombre != null
								&& !insitucionNombre.equals("")) {

							IdPersona id = new IdPersona(this.documentoCoinv2, this.tipoDocumentoCoInv2.getId());
							investigadorExterno.setId(id);
							investigadorExterno.setInterno(Investigador.EXTERNO);
							investigadorExterno.setEvaluador(Investigador.NO_EVALUADOR);
							// Dependencia dep = servicioGeneral
							// .obtenerDependencia(unidadEjecutoraPart);
							// investigadorExterno.setDependencia(dep);

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
							participante.setDedicacionHorasSemana(Short.parseShort("0"));
							participante.setFuncion("Participante");
							participante.setProyecto(proyectoActual);
							TipoInvestigador ti = new TipoInvestigador();
							ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(),
									tipoInvestigador.toString());
							participante.setTipo(ti);
							listaParticipantes.add(participante);
							investigadorExterno = new InvestigadorExterno();
							esOtraVinculacion = false;

						} else {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
									FacesMessage.SEVERITY_FATAL,
									"Por favor ingresar nombres, apellidos e institución de la persona a registrar",
									"Por favor ingresar nombres, apellidos e institución de la persona a registrar"));
						}

					}

					documentoCoinv2 = "";
					// tipoInvestigador = "AI";

				} catch (Exception e) {
					e.printStackTrace();
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

	private boolean buscarInvestigador(IdPersona id) {
		// BUSCA UN INVESTIGADOR DE ACUERDO A SU ID
		boolean investigadorPresente = false;
		int i = 0;
		List listaInvestigadoresProyecto = listaInvestigadoresVista;// proyectoActual.getListaInvestigadoresProyecto();
		while (i < listaInvestigadoresProyecto.size()) {
			InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) listaInvestigadoresVista.get(i);
			InvestigadorProyecto d = ipv.getIp();// (InvestigadorProyecto)listaInvestigadoresProyecto.get(i);
			if (id.getDocumento().equals(d.getInvestigador().getId().getDocumento())
					&& id.getTipoDocumento().equals(d.getInvestigador().getId().getTipoDocumento())) {
				investigadorPresente = true;
				// errorValidacion =
				// "El investigador ya se encuentra asociado al proyecto";
				break;
			}
			i = i + 1;
		}
		return investigadorPresente;
	}

	public boolean validarNombre_InvPpal() {
		boolean val = true;
		boolean valestud = false;

		if (!yaHayPrincipal()) {
			val = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre la información del director del proyecto",
							"Por favor registre la información del director del proyecto"));
		}
		if (proyectoActual.getModalidad().getId().equals(917L)
				||proyectoActual.getModalidad().getId().equals(915L)
				|| proyectoActual.getModalidad().getId().equals(1046L)) {
			if(esNulo(proyectoActual.getDiaSesion())) {
				proyectoActual.setDiaSesion("0");
			}
			try {
				Integer x = Integer.parseInt(proyectoActual.getDiaSesion());
			} catch (Exception e) {
				val = false;
				mensajeError("El número de palabras no es válido, debe ser un valor numérico");
			}
		}
		if (proyectoActual.getModalidad().getId().equals(1046L)
				||((Convocatoria)proyectoActual.getModalidad()).getPadre().getId().equals(725L)) {
			Long montoApoyoGanadores = Long.parseLong(((Convocatoria) proyectoActual.getModalidad()).getMontoApoyoGanadores());
			if(proyectoActual.getValorTotalFinal() <= 0 || proyectoActual.getValorTotalFinal() > montoApoyoGanadores) {
				val = false;
				mensajeError("Costo de la publicación es invalido de acuerdo a los montos de esta convocatoria");
			}
		}
		if ((((Convocatoria)proyectoActual.getModalidad()).getPadre().getId().equals(530L) ||((Convocatoria)proyectoActual.getModalidad()).getPadre().getId().equals(734L)
				||((Convocatoria)proyectoActual.getModalidad()).getPadre().getId().equals(725L))
				&& esCadenaVacia(proyectoActual.getMarcoTeorico())){
			val = false;
			mensajeError("Por favor, registre el título de la revista.");
		}
		if(proyectoActual.getModalidad().getId().equals(917L)||((Convocatoria)proyectoActual.getModalidad()).getPadre().getId().equals(530L)
				||((Convocatoria)proyectoActual.getModalidad()).getPadre().getId().equals(603L)
				||((Convocatoria)proyectoActual.getModalidad()).getPadre().getId().equals(734L)
				||((Convocatoria)proyectoActual.getModalidad()).getPadre().getId().equals(725L)
				||((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(795L)
				|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(836L)) {
			try {
				if (esCadenaVacia(proyectoActual.getPorqueNivelSostenibilidad())) {
					val = false;
					if(isMostrarODCEMed()) {
						mensajeError("Por favor, registre el ISSN.");
					}else {
						mensajeError("Por favor, registre el ISBN de la revista que aceptó la publicación.");
					}
				} else {
					Integer x = Integer.parseInt(proyectoActual.getPorqueNivelSostenibilidad());
				}
			} catch (Exception e) {
				val = false;
				if(isMostrarODCEMed()) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL, "El ISSN no es válido", "El ISSN no es válido"));
				}else {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL, "El ISBN no es válido", "El ISBN no es válido"));
				}
				
			}
		}
		if (this.proyectoActual.getNombre() == null || this.proyectoActual.getNombre().equals("--")
				|| this.proyectoActual.getNombre().equals("")) {
			val = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"Por favor registre el título del libro o artículo", "Por favor registre el título del libro o artículo"));
		}

		if (mostrarSiArticulosUno || mostrarSiArticulosDos) {
			if (esCadenaVacia(subAreaCiencia)) {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Por favor registre las áreas OCDE", "Por favor registre las áreas OCDE"));
			}
			if (esCadenaVacia(proyectoActual.getObjetivoSocioeconomico())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Por favor registre el objetivo socioeconómico",
								"Por favor registre el objetivo socioeconómico"));
			}
		}

		if (mostrarSiConvLibros  || isMostrarODCEMed()) {
			// Area OCDE
			if (esCadenaVacia(areaCiencia)) {
				val = false;
				mensajeError("Por favor seleccione 'Area científica y tecnológica principal'");
			}

			// SubArea OCDE
			if (esCadenaVacia(subAreaCiencia)) {
				val = false;
				mensajeError("Por favor seleccione 'Sub-área de la ciencia'");
			}
		}
		if(esCadenaVacia(proyectoActual.getResumen())){
			val = false;
			mensajeError("Por favor, registre el resumen.");
		}
		if (((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(836L)
				&& proyectoActual.getValorSolicitado() > Long.parseLong(((Convocatoria) proyectoActual.getModalidad()).getMontoApoyoGanadores())) {
			val = false;
			mensajeError("El monto solicitado supera el máximo de la convocatoria ($"
					+ ((Convocatoria) proyectoActual.getModalidad()).getMontoApoyoGanadores() + ")");
		}
		if ((((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(568L)
				|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(569L))
				&& listaGruposConvMedicina.isEmpty()) {
			val = false;
			mensajeError("Por favor, añadir al menos un grupo.");
		}
		if (((Convocatoria) proyectoActual.getModalidad()).getMostrarObjetivos().equals(1)
				&& esCadenaVacia(proyectoActual.getObjetivoGeneral())) {
			val = false;
			mensajeError("Por favor, registre el objetivo general.");
		}
		if (proyectoActual.getListaPalabrasES().size() < 3 && (proyectoActual.getModalidad().getId().equals(1015L)
				|| proyectoActual.getModalidad().getId().equals(1017L)
				|| proyectoActual.getModalidad().getId().equals(1028L)
				|| proyectoActual.getModalidad().getId().equals(1052L)
				|| proyectoActual.getModalidad().getId().equals(1053L)
				|| proyectoActual.getModalidad().getId().equals(1054L)
				|| proyectoActual.getModalidad().getId().equals(1065L)
				|| proyectoActual.getModalidad().getId().equals(1066L)
				|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(603L)
				|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(617L)
				|| proyectoActual.getModalidad().getId().equals(1017L))) {
			val = false;
			mensajeError("Por favor, registre mínimo 3 palabras clave.");
		}
		if (proyectoActual.getListaDependenciasAreaResponsabilidad().isEmpty() && (
				proyectoActual.getModalidad().getId().equals(1015L) ||
				proyectoActual.getModalidad().getId().equals(1028L) ||
				proyectoActual.getModalidad().getId().equals(1052L) ||
				proyectoActual.getModalidad().getId().equals(1053L) ||
				proyectoActual.getModalidad().getId().equals(1054L) ||
				proyectoActual.getModalidad().getId().equals(1065L) ||
				proyectoActual.getModalidad().getId().equals(1066L)
				|| ((Convocatoria) proyectoActual.getModalidad()).getPadre().getId().equals(617L)
				)
			) {
			val = false;
			mensajeError("Por favor, registre una dependencia relacionada.");
		}
		return val;
	}

	public boolean isValidarBotonGuardar() {
		return validarBotonGuardarConvocatoria(proyectoActual.getId());
	}

	private void recortarCampo() {
		if (!esCadenaVacia(proyectoActual.getResumen())) {
			proyectoActual.setResumen(proyectoActual.getResumen().trim());
			if (proyectoActual.getResumen().length() > 4000) {
				proyectoActual.setResumen(proyectoActual.getResumen().substring(0, 3999));
			}
		}
		if (!esCadenaVacia(proyectoActual.getMarcoTeorico())) {
			proyectoActual.setMarcoTeorico(proyectoActual.getMarcoTeorico().trim());
			if (proyectoActual.getMarcoTeorico().length() > 4000) {
				proyectoActual.setMarcoTeorico(proyectoActual.getMarcoTeorico().substring(0, 3999));
			}
		}
		if (!esCadenaVacia(proyectoActual.getObjetivoGeneral())) {
			proyectoActual.setObjetivoGeneral(proyectoActual.getObjetivoGeneral().trim());
			if (proyectoActual.getObjetivoGeneral().length() > 4000) {
				proyectoActual.setObjetivoGeneral(proyectoActual.getObjetivoGeneral().substring(0, 3999));
			}
		}
		if (!esCadenaVacia(proyectoActual.getOtroClaseEvento())) {
			proyectoActual.setOtroClaseEvento(proyectoActual.getOtroClaseEvento().trim());
			if (proyectoActual.getOtroClaseEvento().length() > 100) {
				proyectoActual.setOtroClaseEvento(proyectoActual.getOtroClaseEvento().substring(0, 99));
			}
		}

	}

	public void guardarProyectoConvLibro() {
		System.out.println("<========== GUARDAR CONVOCATORIA LIBROS ==========>");

		recortarCampo();

		if (proyectoExiste) {

			for (InvestigadorProyecto inv : listaParticipantesBorrados) {
				servicioGeneral.eliminarObjeto(inv);
			}

			servicioProyecto.ingresarProyecto(proyectoActual);
		}

		// // áreas de la ciencia
		// DominioDetalle arUno = new DominioDetalle();
		// List listaDomDetUno = new ArrayList<DominioDetalle>();
		// // String consulta1 =
		// // "select dd from Dominio d, DominioDetalle dd where d.id =
		// dd.identificador.id and d.tipo ='"
		// // + DOMINIO_AREA_CIENCIA + "' and dd.identificador.tipo = '" +
		// // areaCiencia + "'";
		// listaDomDetUno = servicioGeneral
		// .obtenerObjetos("select dd from Dominio d, DominioDetalle dd where
		// d.id = dd.identificador.id and d.tipo ='"
		// + DOMINIO_AREA_CIENCIA
		// + "' and dd.identificador.tipo = '" + areaCiencia + "'");
		// arUno = (DominioDetalle) listaDomDetUno.get(0);
		//
		// AreaTematica arTemUno = new AreaTematica();
		// arTemUno.setProyecto(proyectoActual);
		// arTemUno.setProyectoAreaTematica(arUno);
		// arTemUno.setTipo(1l);
		//
		// // áreas de la ciencia
		// DominioDetalle arDos = new DominioDetalle();
		// List listaDomDetarDos = new ArrayList<DominioDetalle>();
		// listaDomDetarDos = servicioGeneral
		// .obtenerObjetos("select dd from Dominio d, DominioDetalle dd where
		// d.id = dd.identificador.id and d.tipo ='"
		// + DOMINIO_AREA_CIENCIA
		// + "' and dd.identificador.tipo = '"
		// + areaCienciaSec
		// + "'");
		// arDos = (DominioDetalle) listaDomDetarDos.get(0);
		//
		// AreaTematica arTemDos = new AreaTematica();
		// arTemDos.setProyecto(proyectoActual);
		// arTemDos.setProyectoAreaTematica(arDos);
		// arTemDos.setTipo(2l);
		//
		// Set<AreaTematica> seAt = new HashSet<AreaTematica>();
		// seAt.add(arTemUno);
		// seAt.add(arTemDos);
		//
		// try {
		// if (proyectoActual != null && proyectoActual.getId() != null) {
		// servicioGeneral
		// .eliminar("DELETE HER_PROYECTO_AREA_TEMATICA WHERE PRY_ID = "
		// + proyectoActual.getId());
		// }
		//
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		//
		// proyectoActual.setAreasTematicas(seAt);

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

		if (listaParticipantes.size() > 0) {
			for (int i = 0; i < listaParticipantes.size(); i++) {
				proyectoActual.adicionarInvestigadorProyecto(listaParticipantes.get(i));
			}
		}

		servicioProyecto.ingresarProyecto(proyectoActual);
	}

	@Override
	public String atras() {
		sesion.removeAttribute("ManejadorConvocatoriaLibros");

		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");

		boolean bandera = false;
		sesion.removeAttribute("manejadorMenuFormularios");

		return "misProyectos";
	}

	@Override
	public String salir() {

		return null;
	}

	@Override
	public String salirGuardar() {
		proyectoActual.setResumen(controlTamanoCadena(proyectoActual.getResumen(), 4000));
		if (validarNombre_InvPpal()) {

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

			if (mostrarSiConvLibros) {
				guardarProyectoConvLibro();
			} else if (mostrarSiArticulosUno) {
				guardarProyectoConvArticulos();
			} else if (mostrarSiArticulosDos) {
				guardarProyectoConvArticulos();
			} else {
				if (mostrarSiConvocatoriaMedicina) {
					guardarProyectoConvMedicina();
				}
			}

			if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null) {
				enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
			}

			sesion.removeAttribute("manejadorFichaMinimaProyectos");

			ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");

			boolean bandera = false;
			sesion.removeAttribute("manejadorMenuFormularios");

			return "misProyectos";

		} else {
			return "";
		}
	}

	@Override
	public String siguiente() {
		if (validarNombre_InvPpal()) {

			String link = "";
			ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
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

						if (lis[i].getOutcome().equals("irConvocatoriaLibros")) {
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
			// System.out.println("fase
			// "+proyectoActual.getFase().intValue()+"modalidad"+proyectoActual.getModalidad().getId().toString()+"pgd"+(proyectoActual.getPlanGlobalDesarrollo()==null?"nulo:":proyectoActual.getPlanGlobalDesarrollo().getId().toString())+"resumen"+proyectoActual.getResumen()+"duracion"+(proyectoActual.getDuracion()==null?"nulo":String.valueOf(proyectoActual.getDuracion().intValue()))+"valor"+(proyectoActual.getValorSolicitado()==null?"nulo":String.valueOf(proyectoActual.getValorSolicitado().longValue()))+"otros"+(proyectoActual.getOtrosAportes()==null?"nulo":String.valueOf(proyectoActual.getOtrosAportes().longValue()))+"fech"+(proyectoActual.getFechaTentativaInicio()==null?"nulo":proyectoActual.getFechaTentativaInicio().toString()));

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

			if (mostrarSiConvLibros) {
				guardarProyectoConvLibro();
			} else if (mostrarSiArticulosUno) {
				guardarProyectoConvArticulos();
			} else if (mostrarSiArticulosDos) {
				guardarProyectoConvArticulos();
			} else {
				if (mostrarSiConvocatoriaMedicina) {
					guardarProyectoConvMedicina();
				}
			}

			if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null) {
				enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
			}

			return "irSubirArchivo";

		} else {
			return "";
		}
	}

	public void guardarProyectoConvMedicina() {

		if (proyectoExiste) {

			for (InvestigadorProyecto inv : listaParticipantesBorrados) {
				servicioGeneral.eliminarObjeto(inv);
			}

			servicioProyecto.ingresarProyecto(proyectoActual);
		}

		if (listaParticipantes.size() > 0) {
			for (int i = 0; i < listaParticipantes.size(); i++) {
				proyectoActual.adicionarInvestigadorProyecto(listaParticipantes.get(i));
			}
		}
		
		if(isMostrarODCEMed()) {
			if (!esCadenaVacia(subAreaCiencia)) {
				List<DominioDetalle> listaDomDetUno = servicioGeneral.obtenerObjetos(DominioDetalle.class,
						"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
								+ DOMINIO_SUB_AREA_CIENCIA + "' and dd.identificador.tipo = '" + subAreaCiencia + "'");
				DominioDetalle arUno = (DominioDetalle) listaDomDetUno.get(0);
				proyectoActual.setAreaPrimaria(arUno);
			} else {
				proyectoActual.setAreaPrimaria(null);
			}
		}

		if (listaGruposConvMedicina != null && listaGruposConvMedicina.size() > 0) {
			for (int i = 0; i < listaGruposConvMedicina.size(); i++) {
				Grupo g = listaGruposConvMedicina.get(i);
				proyectoActual.adicionarGrupo(g);
			}
		}

		// if(telefono != null && !telefono.equals("")){
		// ii.setTelefono(telefono);
		// servicioPersona.guardarInvestigador(ii);
		// }

		servicioProyecto.ingresarProyecto(proyectoActual);
	}

	public void guardarProyectoConvArticulos() {
		System.out.println("<========== GUARDAR CONVOCATORIA ARTICULOS UNO ==========>");

		if (proyectoExiste) {

			for (InvestigadorProyecto inv : listaParticipantesBorrados) {
				servicioGeneral.eliminarObjeto(inv);
			}

			servicioProyecto.ingresarProyecto(proyectoActual);
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

		if (listaParticipantes.size() > 0) {
			for (int i = 0; i < listaParticipantes.size(); i++) {
				proyectoActual.adicionarInvestigadorProyecto(listaParticipantes.get(i));
			}
		}

		servicioProyecto.ingresarProyecto(proyectoActual);
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

	public List<PalabraClave> getListaPalabrasClave() {
		return listaPalabrasClave;
	}

	public void setListaPalabrasClave(List<PalabraClave> listaPalabrasClave) {
		this.listaPalabrasClave = listaPalabrasClave;
	}

	public String getLinkLineas() {
		return linkLineas;
	}

	public void setLinkLineas(String linkLineas) {
		this.linkLineas = linkLineas;
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

	public List getListaAreaCiencia() {
		return listaAreaCiencia;
	}

	public void setListaAreaCiencia(List listaAreaCiencia) {
		this.listaAreaCiencia = listaAreaCiencia;
	}

	public SelectItem[] getAreaCienciaItems() {
		return areaCienciaItems;
	}

	public void setAreaCienciaItems(SelectItem[] areaCienciaItems) {
		this.areaCienciaItems = areaCienciaItems;
	}

	public String getDependenciaAdicionada() {
		return dependenciaAdicionada;
	}

	public void setDependenciaAdicionada(String dependenciaAdicionada) {
		this.dependenciaAdicionada = dependenciaAdicionada;
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

	public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidad() {
		return dependenciaAreaResponsabilidad;
	}

	public void setDependenciaAreaResponsabilidad(DependenciaAreaResponsabilidad dependenciaAreaResponsabilidad) {
		this.dependenciaAreaResponsabilidad = dependenciaAreaResponsabilidad;
	}

	public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidadSeleccionada() {
		return dependenciaAreaResponsabilidadSeleccionada;
	}

	public void setDependenciaAreaResponsabilidadSeleccionada(
			DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada) {
		this.dependenciaAreaResponsabilidadSeleccionada = dependenciaAreaResponsabilidadSeleccionada;
	}

	public InvestigadorProyecto getInvestigadorProyectoNuevo() {
		return investigadorProyectoNuevo;
	}

	public void setInvestigadorProyectoNuevo(InvestigadorProyecto investigadorProyectoNuevo) {
		this.investigadorProyectoNuevo = investigadorProyectoNuevo;
	}

	public List getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public List getListaInvestigadoresVista() {
		return listaInvestigadoresVista;
	}

	public void setListaInvestigadoresVista(List listaInvestigadoresVista) {
		this.listaInvestigadoresVista = listaInvestigadoresVista;
	}

	public boolean isInvestigadorExiste() {
		return investigadorExiste;
	}

	public void setInvestigadorExiste(boolean investigadorExiste) {
		this.investigadorExiste = investigadorExiste;
	}

	public SelectItem[] getAutores() {
		return autores;
	}

	public void setAutores(SelectItem[] autores) {
		this.autores = autores;
	}

	public InvestigadorProyecto getCopiaValidacionInvestigador() {
		return copiaValidacionInvestigador;
	}

	public void setCopiaValidacionInvestigador(InvestigadorProyecto copiaValidacionInvestigador) {
		this.copiaValidacionInvestigador = copiaValidacionInvestigador;
	}

	public InvestigadorProyecto getInvestigadorProyectoActual() {
		return investigadorProyectoActual;
	}

	public void setInvestigadorProyectoActual(InvestigadorProyecto investigadorProyectoActual) {
		this.investigadorProyectoActual = investigadorProyectoActual;
	}

	public TipoDocumento getTipoDocumentoCoInv2() {
		return tipoDocumentoCoInv2;
	}

	public void setTipoDocumentoCoInv2(TipoDocumento tipoDocumentoCoInv2) {
		this.tipoDocumentoCoInv2 = tipoDocumentoCoInv2;
	}

	public String getDocumentoCoinv2() {
		return documentoCoinv2;
	}

	public void setDocumentoCoinv2(String documentoCoinv2) {
		this.documentoCoinv2 = documentoCoinv2;
	}

	public String getTipoInvestigador() {
		return tipoInvestigador;
	}

	public void setTipoInvestigador(String tipoInvestigador) {
		this.tipoInvestigador = tipoInvestigador;
	}

	public List<InvestigadorProyecto> getListaParticipantes() {
		return listaParticipantes;
	}

	public void setListaParticipantes(List<InvestigadorProyecto> listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
	}

	public List<InvestigadorProyecto> getListaParticipantesBorrados() {
		return listaParticipantesBorrados;
	}

	public void setListaParticipantesBorrados(List<InvestigadorProyecto> listaParticipantesBorrados) {
		this.listaParticipantesBorrados = listaParticipantesBorrados;
	}

	public InvestigadorExterno getInvestigadorExterno() {
		return investigadorExterno;
	}

	public void setInvestigadorExterno(InvestigadorExterno investigadorExterno) {
		this.investigadorExterno = investigadorExterno;
	}

	public String getInsitucionNombre() {
		return insitucionNombre;
	}

	public void setInsitucionNombre(String insitucionNombre) {
		this.insitucionNombre = insitucionNombre;
	}

	public boolean isEsOtraVinculacion() {
		return esOtraVinculacion;
	}

	public void setEsOtraVinculacion(boolean esOtraVinculacion) {
		this.esOtraVinculacion = esOtraVinculacion;
	}

	public SelectItem[] getGeneroItem() {
		return generoItem;
	}

	public void setGeneroItem(SelectItem[] generoItem) {
		this.generoItem = generoItem;
	}

	public InvestigadorProyecto getParticipante() {
		return participante;
	}

	public void setParticipante(InvestigadorProyecto participante) {
		this.participante = participante;
	}

	public boolean isProyectoExiste() {
		return proyectoExiste;
	}

	public void setProyectoExiste(boolean proyectoExiste) {
		this.proyectoExiste = proyectoExiste;
	}

	public List getListaAreasPrimSec() {
		return listaAreasPrimSec;
	}

	public void setListaAreasPrimSec(List listaAreasPrimSec) {
		this.listaAreasPrimSec = listaAreasPrimSec;
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

	public boolean isMostrarSiConvLibros() {
		return mostrarSiConvLibros;
	}

	public void setMostrarSiConvLibros(boolean mostrarSiConvLibros) {
		this.mostrarSiConvLibros = mostrarSiConvLibros;
	}

	public boolean isMostrarSiArticulosUno() {
		return mostrarSiArticulosUno;
	}

	public void setMostrarSiArticulosUno(boolean mostrarSiArticulosUno) {
		this.mostrarSiArticulosUno = mostrarSiArticulosUno;
	}

	public boolean isMostrarSiArticulosDos() {
		return mostrarSiArticulosDos;
	}

	public void setMostrarSiArticulosDos(boolean mostrarSiArticulosDos) {
		this.mostrarSiArticulosDos = mostrarSiArticulosDos;
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

	public List getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(List listaAreas) {
		this.listaAreas = listaAreas;
	}

	public Persona getPersonaActual() {
		return personaActual;
	}

	public void setPersonaActual(Persona personaActual) {
		this.personaActual = personaActual;
	}

	public boolean isMostrarSiConvocatoriaMedicina() {
		return mostrarSiConvocatoriaMedicina;
	}

	public void setMostrarSiConvocatoriaMedicina(boolean mostrarSiConvocatoriaMedicina) {
		this.mostrarSiConvocatoriaMedicina = mostrarSiConvocatoriaMedicina;
	}

	public SelectItem[] getGruposInvItem() {
		return gruposInvItem;
	}

	public void setGruposInvItem(SelectItem[] gruposInvItem) {
		this.gruposInvItem = gruposInvItem;
	}

	public List<Grupo> getListaGruposConvMedicina() {
		return listaGruposConvMedicina;
	}

	public void setListaGruposConvMedicina(List<Grupo> listaGruposConvMedicina) {
		this.listaGruposConvMedicina = listaGruposConvMedicina;
	}

	public Grupo getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	public void setGrupoSeleccionado(Grupo grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}

	public List<Grupo> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public UIComponent getBotonAgregarDependencia() {
		return botonAgregarDependencia;
	}

	public void setBotonAgregarDependencia(UIComponent botonAgregarDependencia) {
		this.botonAgregarDependencia = botonAgregarDependencia;
	}

	public String getDependenciaProyecto() {
		return dependenciaProyecto;
	}

	public void setDependenciaProyecto(String dependenciaProyecto) {
		this.dependenciaProyecto = dependenciaProyecto;
	}

	public String getSedeSel() {
		return sedeSel;
	}

	public void setSedeSel(String sedeSel) {
		this.sedeSel = sedeSel;
	}

	public String getFacultadSel() {
		return facultadSel;
	}

	public void setFacultadSel(String facultadSel) {
		this.facultadSel = facultadSel;
	}

	public List<Dependencia> getFacultadesUN() {
		return facultadesUN;
	}

	public void setFacultadesUN(List<Dependencia> facultadesUN) {
		this.facultadesUN = facultadesUN;
	}

	public boolean isMostrarFacultades() {
		return mostrarFacultades;
	}

	public void setMostrarFacultades(boolean mostrarFacultades) {
		this.mostrarFacultades = mostrarFacultades;
	}

	public SelectItem[] getFacultadItem() {
		return facultadItem;
	}

	public void setFacultadItem(SelectItem[] facultadItem) {
		this.facultadItem = facultadItem;
	}

	public List<Sede> getSedesUN() {
		return sedesUN;
	}

	public void setSedesUN(List<Sede> sedesUN) {
		this.sedesUN = sedesUN;
	}

	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	public void setSedeItem(SelectItem[] sedeItem) {
		this.sedeItem = sedeItem;
	}

	/**
	 * @return the esConvMinas
	 */
	public boolean isEsConvMinas() {
		return esConvMinas;
	}

	/**
	 * @param esConvMinas
	 *            the esConvMinas to set
	 */
	public void setEsConvMinas(boolean esConvMinas) {
		this.esConvMinas = esConvMinas;
	}

	/**
	 * @return the esConvCienciasMed
	 */
	public boolean isEsConvCienciasMed() {
		return esConvCienciasMed;
	}

	/**
	 * @param esConvCienciasMed
	 *            the esConvCienciasMed to set
	 */
	public void setEsConvCienciasMed(boolean esConvCienciasMed) {
		this.esConvCienciasMed = esConvCienciasMed;
	}

	public String getSubAreaCiencia() {
		return subAreaCiencia;
	}

	public void setSubAreaCiencia(String subAreaCiencia) {
		this.subAreaCiencia = subAreaCiencia;
	}

	public SelectItem[] getSubAreaCienciaSecItems() {
		return subAreaCienciaSecItems;
	}

	public void setSubAreaCienciaSecItems(SelectItem[] subAreaCienciaSecItems) {
		this.subAreaCienciaSecItems = subAreaCienciaSecItems;
	}

	public String getSubAreaCienciaSec() {
		return subAreaCienciaSec;
	}

	public void setSubAreaCienciaSec(String subAreaCienciaSec) {
		this.subAreaCienciaSec = subAreaCienciaSec;
	}

	public SelectItem[] getSubAreaCienciaItems() {
		return subAreaCienciaItems;
	}

	public void setSubAreaCienciaItems(SelectItem[] subAreaCienciaItems) {
		this.subAreaCienciaItems = subAreaCienciaItems;
	}

	public List<AreaTematicaVista> getListaAreasTematicas() {
		return listaAreasTematicas;
	}

	public void setListaAreasTematicas(List<AreaTematicaVista> listaAreasTematicas) {
		this.listaAreasTematicas = listaAreasTematicas;
	}

	public UIComponent getBotonAreasTabla() {
		return botonAreasTabla;
	}

	public void setBotonAreasTabla(UIComponent botonAreasTabla) {
		this.botonAreasTabla = botonAreasTabla;
	}

	public AreaTematicaVista getAreaSeleccionada() {
		return areaSeleccionada;
	}

	public void setAreaSeleccionada(AreaTematicaVista areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	public String getLinkObjetivos() {
		return "http://www.hermes.unal.edu.co/pages/descargas/ObjetivoSocioeconomico.pdf";
	}

	public SelectItem[] getObjSocioeconomicoItems() {
		return objSocioeconomicoItems;
	}

	public void setObjSocioeconomicoItems(SelectItem[] objSocioeconomicoItems) {
		this.objSocioeconomicoItems = objSocioeconomicoItems;
	}
	
	public boolean isMostrarFinanciacion() {
		return ((Convocatoria) proyectoActual.getModalidad()).getMostrarFinanciacion() != null
				&& ((Convocatoria) proyectoActual.getModalidad()).getMostrarFinanciacion().equals("1");
	}
	
	public void ingresarFuenteUniversidad(FuenteFinanciacion ff,
			ModalidadFuenteFinanciacion modalidadFuenteFinanciacion) {
		Financiacion f = new Financiacion();
		f.setProyecto(proyectoActual);
		f.setFuente(ff);
		f.setValor(0L);
		f.setRol("ROL_FT_FIN");
		f.setModalidadFuenteFinanciacion(modalidadFuenteFinanciacion);
		if (proyectoActual.getFinanciaciones().isEmpty()) {
			proyectoActual.getFinanciaciones().add(f);
		}
	}

	public Long getIdTipoRubro() {
		return idTipoRubro;
	}

	public void setIdTipoRubro(Long idTipoRubro) {
		this.idTipoRubro = idTipoRubro;
	}

	public SelectItem[] getTiposRubroItem() {
		return tiposRubroItem;
	}

	public void setTiposRubroItem(SelectItem[] tiposRubroItem) {
		this.tiposRubroItem = tiposRubroItem;
	}

	public List<TipoRubro> getListaTiposRubros() {
		return listaTiposRubros;
	}

	public void setListaTiposRubros(List<TipoRubro> listaTiposRubros) {
		this.listaTiposRubros = listaTiposRubros;
	}

	public Long getValorGasto() {
		return valorGasto;
	}

	public void setValorGasto(Long valorGasto) {
		this.valorGasto = valorGasto;
	}

	public String getDescripcionGasto() {
		return descripcionGasto;
	}

	public void setDescripcionGasto(String descripcionGasto) {
		this.descripcionGasto = descripcionGasto;
	}
	
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
					fg.calcularValorEfectivoFinanciacion();
				} else {
					mensajeError("No se ha podido agregar el rubro seleccionado.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean validarMontoFuente() {
		boolean val = true;
		Modalidad mod = proyectoActual.getModalidad();
		if (proyectoActual.getListaFinanciones().size() > 0) {
			Financiacion fin = proyectoActual.getListaFinanciones().get(0);
			if (fin.getValor() != null
					&& fin.getValor() + valorGasto > Long.parseLong(((Convocatoria) mod).getMontoApoyoGanadores())) {
				val = false;
				mensajeError("El monto solicitado supera el máximo de la convocatoria");
			}
		}
		return val;
	}

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
	
	public List<Gasto> getListaGastos() {
		List<Gasto> lista = new ArrayList<Gasto>();
		if (!proyectoActual.getListaFinanciones().isEmpty()) {
			Financiacion f = (Financiacion) proyectoActual.getListaFinanciones().get(0);
			if (f.getGastos() != null) {
				Iterator<Gasto> i = f.getGastos().iterator();
				while (i.hasNext()) {
					Gasto gasto = i.next();
					if (gasto.getSumaCampos() > 0) {
						lista.add(gasto);
					}
				}
			}
		}
		return lista;
	}
	
	public void eliminarGastoConv() {
		Financiacion financiacion = getGastoSeleccionado().getFinanciacion();
		financiacion.borrarGasto(getGastoSeleccionado());
	}

	public Gasto getGastoSeleccionado() {
		return gastoSeleccionado;
	}

	public void setGastoSeleccionado(Gasto gastoSeleccionado) {
		this.gastoSeleccionado = gastoSeleccionado;
	}

	public boolean isMostrarODCEMed() {
		return mostrarODCEMed;
	}

	public void setMostrarODCEMed(boolean mostrarODCEMed) {
		this.mostrarODCEMed = mostrarODCEMed;
	}

	public Boolean getMostrarTituloRevista() {
		return mostrarTituloRevista;
	}

	public void setMostrarTituloRevista(Boolean mostrarTituloRevista) {
		this.mostrarTituloRevista = mostrarTituloRevista;
	}
}
