package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.ApropiacionES;
import co.edu.unal.hermes.modelo.AreaTematica;
import co.edu.unal.hermes.modelo.AreaTematicaVista;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.FormularioInformacionEspecifica;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Preinscripcion_ECP;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.ValoresListasProyecto;
import co.edu.unal.hermes.modelo.ViabiliadES;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorTrabajoPrevioProyectoES_Inno extends ManejadorProyecto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3102544026800293396L;
	private List<DominioDetalle> listaNivelSostenibilidad;
	private SelectItem[] nivelSostenibilidadItem;
	private List<DominioDetalle> listaNaturalezaEntidad;
	private SelectItem[] naturalezaEntidadItem;
	private NaturalezaIniciativas natIni;
	private NaturalezaIniciativas iniciativaAct;
	private ArrayList<NaturalezaIniciativas> listaIniciativas;
	private UIComponent pqNivSos;
	private UIComponent pqContinuidad;
	private UIComponent idDescrInicia;
	private UIComponent idDescrIniciaArtiSoste;
	private UIComponent botonLineas;

	private ApropiacionES aproPry;
	private ApropiacionES apropAct;
	private ArrayList<ApropiacionES> listaApropiacion;
	private UIComponent idDescrAprobacion;

	private ViabiliadES viabPry;
	private ViabiliadES viabAct;
	private ArrayList<ViabiliadES> listaViabilidad;
	private UIComponent idDescrViabiliad;
	private Departamento departamentoActual; // DEPARTAMENTO ACTUAL
	private List listaDepartamentos; // LISTA DE DEPARTAMENTOS
	private Ciudad ciudadActual; // CIUDAD SELECCIONADA
	private List listaCiudades; // LISTA DE CIUDADES DEL DEPARTAMENTO
	private SelectItem[] departamentoItem; // DEPARTAMENTOS A MOSTRAR
	private SelectItem[] ciudadItem; // CIUDADES A MOSTRAR
	private List listaZonas;
	private String problemaSeleccionado;
	private SelectItem[] problemasItem;
	private SelectItem[] problemasContinuidadItem;
	private SelectItem[] enfoquesItem;
	private List listaProblemas;
	private boolean esInnoSocial2015 = false;
	private boolean esInnoSocial2016 = false;
	private boolean esInnoSocial2016_M3 = false;
	private boolean esInnoSocial2017 = false;
	private boolean esInnoSocial2017_M2 = false;
	private boolean esInnoSocial2018 = false;
	private boolean esInnoSocial2022 = false;
	private Convocatoria convocatoriaActual;
	private String areaCienciaSec;
	private String subAreaCienciaSec;
	private SelectItem[] areaCienciaItems;
	private SelectItem[] subAreaCienciaSecItems;
	private List listaAreaCiencia;
	private List listaSubAreaCiencia;
	private List<AreaTematicaVista> listaAreasTematicas;
	private AreaTematicaVista areaSeleccionada;
	private SelectItem[] lineasProyectos2017;
	private String lineaProyecto;
	private List<ValoresListasProyecto> listaLineasProyecto;
	private List<ValoresListasProyecto> listaLineasProyectoBorradas;
	private ValoresListasProyecto lineaProyectoSeleccionada;
	private SelectItem[] listaCentrosPensamiento;
	private SelectItem[] listaComunidadesEtnicas;
	private SelectItem[] listaCicloVital;
	private SelectItem[] listaCondicionPoblacion;
	private SelectItem[] listaGrupoPoblacion;
	private SelectItem[] listaAreasEst;
	private String tieneCentrosPensamiento;
	private boolean siTieneCentrosPensamiento = false;
	private String indicadoresApropiacion;
	private List<ValoresListasProyecto> listaindicadoresApropiacion;
	private List<ValoresListasProyecto> listaindicadoresApropiacionBorradas;
	private ValoresListasProyecto indicadoresApropiacionSeleccionada;
	private String comunidadEtnica;
	private String condicionPoblacion;
	private String grupoPoblacion;
	private String cicloVital;
	private String areaEst;
	private List<ValoresListasProyecto> listaComunidadesEtnicasProyecto;
	private List<ValoresListasProyecto> listaComunidadesEtnicasProyectoBorradas;
	private ValoresListasProyecto comunidadEtnicaSeleccionada;
	private String numeroPoblacion;
	private List<ValoresListasProyecto> areasEst;
	private List<ValoresListasProyecto> areasEstBorradas;
	private ValoresListasProyecto areaEstSeleccionada;

	private String valorListaProyecto;
	private SelectItem[] listaSeleccionValoresProyecto;
	private List<ValoresListasProyecto> valoresListasProyectos;
	private List<ValoresListasProyecto> valoresListasProyectosBorrados;
	private ValoresListasProyecto valorListaProyectoSeleccionada;
	private ValoresListasProyecto valorListaProyectoSeleccionadaParametrizada;
	private ValoresListasProyecto valorListaProyectoSeleccionadaParametrizada2;
	private FormularioInformacionEspecifica formularioEspecifica;
	private String valorListaProyectoParametrizado;
	private String valorListaProyectoParametrizado2;
	private List<ValoresListasProyecto> valoresListasProyectosParametrizado;
	private List<ValoresListasProyecto> valoresListasProyectosParametrizado2;
	private SelectItem[] listaSeleccionMultipleValoresProyecto;
	private SelectItem[] listaSeleccionMultipleValoresProyecto2;
	private SelectItem[] listaSeleccion1valoresProyecto;

	public ManejadorTrabajoPrevioProyectoES_Inno() {
		// super();
		obtenerListaDepartamentos();
		obtenerListaCiudades();
		areaEstSeleccionada = new ValoresListasProyecto();
		listaAreasTematicas = new ArrayList<AreaTematicaVista>();
		listaLineasProyecto = new ArrayList<ValoresListasProyecto>();
		listaLineasProyectoBorradas = new ArrayList<ValoresListasProyecto>();
		listaindicadoresApropiacion = new ArrayList<ValoresListasProyecto>();
		listaindicadoresApropiacionBorradas = new ArrayList<ValoresListasProyecto>();
		listaComunidadesEtnicasProyecto = new ArrayList<ValoresListasProyecto>();
		listaComunidadesEtnicasProyectoBorradas = new ArrayList<ValoresListasProyecto>();
		areasEst = new ArrayList<ValoresListasProyecto>();
		areasEstBorradas = new ArrayList<ValoresListasProyecto>();
		listaSeleccionValoresProyecto = crearListaItemDominioDetalle("LISTAVAL_CONV1034");
		formularioEspecifica = new FormularioInformacionEspecifica();

		if (proyectoActual.getId() != null) {

			List listConvocatorias = servicioGeneral.obtenerObjetos(
					"select e from Convocatoria e where e.id = " + proyectoActual.getModalidad().getId());
			if (listConvocatorias.size() > 0 && listConvocatorias != null) {
				convocatoriaActual = (Convocatoria) listConvocatorias.get(0);
			}
			
			cargarFormularioInfoEspecifica();

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CON_INNO_ES_2015"))) {
				esInnoSocial2015 = true;
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CON_INNO_ES_2016"))) {
				esInnoSocial2015 = true;
				esInnoSocial2016 = true;
				cargarEnfoque();
				cargarTemas();
				cambiarAreaSec();
				cargarTemaProyectos();
			}

			if (convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals("CON_INNO_ES_2016_M3"))) {
				esInnoSocial2015 = true;
				esInnoSocial2016 = true;
				esInnoSocial2016_M3 = true;
				cargarProyectosContinuidad();
				cargarEnfoque();
				cargarTemas();
				cambiarAreaSec();
				cargarTemaProyectos();
			}

			if (convocatoriaActual.getRestriccion() != null && (convocatoriaActual.getRestriccion().getId()
					.equals(RestriccionConvocatoria.CONV_INNO_ES_2017))) {
				esInnoSocial2015 = false;
				esInnoSocial2016 = false;
				esInnoSocial2016_M3 = false;
				esInnoSocial2017 = true;
				esInnoSocial2017_M2 = false;

				cargarProyectosContinuidad();
				cargarLineasProyectos();
				cargarLineasSeleccionadasProyecto();
				cargarIndicadoresApropiacion();
				cargarCentrosPensamiento();
			}

			if (convocatoriaActual.getRestriccion() != null && (convocatoriaActual.getRestriccion().getId()
					.equals(RestriccionConvocatoria.CONV_INNO_ES_2017_M2))) {
				esInnoSocial2015 = false;
				esInnoSocial2016 = false;
				esInnoSocial2016_M3 = false;
				esInnoSocial2017 = false;
				esInnoSocial2017_M2 = true;
				cargarProyectosContinuidad();
				cargarCentrosPensamiento();
				cargarIndicadoresApropiacion();
			}

			if (!convocatoriaActual.getPadre().getId().equals(706L) && !convocatoriaActual.getPadre().getId().equals(636L) && ((convocatoriaActual.getRestriccion() != null
					&& (convocatoriaActual.getRestriccion().getId().equals(RestriccionConvocatoria.EXT_SOL_2018)))
					|| (convocatoriaActual.getTipo() != null && convocatoriaActual.getTipo().getId().equals("ES7")))) {
				esInnoSocial2018 = true;
				cargarComunidadesEtnicas();
				cargarComunidadesEtnicasProyecto();
				cargarCentrosPensamiento();
				cargarIndicadoresApropiacion();
				cargarCondicionPoblacion();
				cargarGrupoPoblacion();
				cargarCicloVital();
				cargarAreasEst();
				cargarListaAreasEst();
			}
			if (convocatoriaActual.getPadre().getId().equals(636L) || convocatoriaActual.getPadre().getId().equals(706L)) {
				esInnoSocial2022 = true;
				cargarComunidadesEtnicas();
				cargarComunidadesEtnicasProyecto();
				cargarCentrosPensamiento();
				cargarIndicadoresApropiacion();
				cargarCondicionPoblacion();
				cargarGrupoPoblacion();
				cargarCicloVital();
			}

		}
		cargarValoresIniciales();
		cargarValoresSeleccionadosProyecto();
		cargarValoresSeleccionadosProyectoParametrizado();
		cargarValoresSeleccionadosProyectoParametrizado2();
		valoresListasProyectosBorrados = new ArrayList<ValoresListasProyecto>();
	}
	
	public void cargarFormularioInfoEspecifica() {
		String hqlFormEsp = "select e from FormularioInformacionEspecifica e where e.modalidad.id = "
				+ convocatoriaActual.getId();
		List<FormularioInformacionEspecifica> listaFormInfoEsp = servicioGeneral
				.obtenerObjetos(FormularioInformacionEspecifica.class, hqlFormEsp);
		if (listaFormInfoEsp != null && listaFormInfoEsp.size() > 0) {
			formularioEspecifica = listaFormInfoEsp.get(0);
		}
		
		if(formularioEspecifica!=null && formularioEspecifica.getId()!=null && formularioEspecifica.getListaSeleccionMultiple()) {
			listaSeleccionMultipleValoresProyecto = crearListaItemDominioDetalle(formularioEspecifica.getDominioListaSeleccionMultiple());
		}
		
		if(formularioEspecifica!=null && formularioEspecifica.getId()!=null && formularioEspecifica.getListaSeleccionMultiple2()) {
			setListaSeleccionMultipleValoresProyecto2(crearListaItemDominioDetalle(formularioEspecifica.getDominioListaSeleccionMultiple2()));
		}
		
		if (formularioEspecifica!=null && formularioEspecifica.getId()!=null && formularioEspecifica.getListaSeleccion()) {
			listaSeleccion1valoresProyecto = crearListaItemDominioDetalle(formularioEspecifica.getDominioListaSeleccion());
		}
	}

	public void cargarValoresSeleccionadosProyecto() {
		valoresListasProyectos = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
				"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
						+ " and e.tipo = 'LISTAVAL_CONV1034'");
		if (valoresListasProyectos == null) {
			valoresListasProyectos = new ArrayList<ValoresListasProyecto>();
		}
	}
	
	public void cargarValoresSeleccionadosProyectoParametrizado() {
		valoresListasProyectosParametrizado = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
				"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
						+ " and e.tipo = '"+formularioEspecifica.getDominioListaSeleccionMultiple()+"'");
		if (valoresListasProyectosParametrizado == null) {
			valoresListasProyectosParametrizado = new ArrayList<ValoresListasProyecto>();
		}
	}
	
	public void cargarValoresSeleccionadosProyectoParametrizado2() {
		valoresListasProyectosParametrizado2 = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
				"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
						+ " and e.tipo = '"+formularioEspecifica.getDominioListaSeleccionMultiple2()+"'");
		if (valoresListasProyectosParametrizado2 == null) {
			valoresListasProyectosParametrizado2 = new ArrayList<ValoresListasProyecto>();
		}
	}
	
	public void adicionarValorProyecto() {
		List<DominioDetalle> listaDomDet = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select e from DominioDetalle e, Dominio d where e.identificador.tipo = '" + valorListaProyecto
						+ "' and e.identificador.id = d.id and d.tipo = 'LISTAVAL_CONV1034'");
		DominioDetalle g = obtenerDominioDetalleLista(valorListaProyecto, listaDomDet);

		ValoresListasProyecto vlp = new ValoresListasProyecto();
		vlp.setProyecto(proyectoActual);
		vlp.setTipo("LISTAVAL_CONV1034");
		vlp.setValor(g.getIdentificador().getTipo());
		vlp.setDescripcion(g.getDescripcion());

		if (!valoresListasProyectos.contains(vlp)) {
			valoresListasProyectos.add(vlp);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La opción seleccionada ya se encuentra asociada al proyecto",
					"La opción seleccionada ya se encuentra asociada al proyecto");
		}

	}
	
	public void adicionarValorProyectoParametrizado() {
		List<DominioDetalle> listaDomDet = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select e from DominioDetalle e, Dominio d where e.identificador.tipo = '" + valorListaProyectoParametrizado
						+ "' and e.identificador.id = d.id and d.tipo = '" + formularioEspecifica.getDominioListaSeleccionMultiple() + "'");
		DominioDetalle g = obtenerDominioDetalleLista(valorListaProyectoParametrizado, listaDomDet);

		ValoresListasProyecto vlp = new ValoresListasProyecto();
		vlp.setProyecto(proyectoActual);
		vlp.setTipo(formularioEspecifica.getDominioListaSeleccionMultiple());
		vlp.setValor(g.getIdentificador().getTipo());
		vlp.setDescripcion(g.getDescripcion());

		if (!valoresListasProyectosParametrizado.contains(vlp)) {
			valoresListasProyectosParametrizado.add(vlp);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La opción seleccionada ya se encuentra asociada al proyecto",
					"La opción seleccionada ya se encuentra asociada al proyecto");
		}

	}
	
	public void adicionarValorProyectoParametrizado2() {
		List<DominioDetalle> listaDomDet = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select e from DominioDetalle e, Dominio d where e.identificador.tipo = '" + valorListaProyectoParametrizado2
						+ "' and e.identificador.id = d.id and d.tipo = '" + formularioEspecifica.getDominioListaSeleccionMultiple2() + "'");
		DominioDetalle g = obtenerDominioDetalleLista(valorListaProyectoParametrizado2, listaDomDet);

		ValoresListasProyecto vlp = new ValoresListasProyecto();
		vlp.setProyecto(proyectoActual);
		vlp.setTipo(formularioEspecifica.getDominioListaSeleccionMultiple2());
		vlp.setValor(g.getIdentificador().getTipo());
		vlp.setDescripcion(g.getDescripcion());

		if (!valoresListasProyectosParametrizado2.contains(vlp)) {
			valoresListasProyectosParametrizado2.add(vlp);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La opción seleccionada ya se encuentra asociada al proyecto",
					"La opción seleccionada ya se encuentra asociada al proyecto");
		}

	}
	
	public void eliminarValorProyecto() {
		valoresListasProyectos.remove(valorListaProyectoSeleccionada);
		valoresListasProyectosBorrados.add(valorListaProyectoSeleccionada);
		valorListaProyectoSeleccionada = new ValoresListasProyecto();
	}
	
	public void eliminarValorProyectoParametrizado() {
		valoresListasProyectosParametrizado.remove(valorListaProyectoSeleccionadaParametrizada);
		valoresListasProyectosBorrados.add(valorListaProyectoSeleccionada);
		valorListaProyectoSeleccionadaParametrizada = new ValoresListasProyecto();
	}
	
	public void eliminarValorProyectoParametrizado2() {
		valoresListasProyectosParametrizado2.remove(valorListaProyectoSeleccionadaParametrizada2);
		valoresListasProyectosBorrados.add(valorListaProyectoSeleccionada);
		valorListaProyectoSeleccionadaParametrizada2 = new ValoresListasProyecto();
	}


	public void cargarCondicionPoblacion() {
		List listaComunidadesEtnicasDom = new ArrayList<DominioDetalle>();
		listaComunidadesEtnicasDom = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='CONDICION_POBLACION_EXT_SOL_2018' order by dd.descripcion");
		setListaCondicionPoblacion(new SelectItem[listaComunidadesEtnicasDom.size()]);
		for (int i = 0; i < listaComunidadesEtnicasDom.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaComunidadesEtnicasDom.get(i);
			listaCondicionPoblacion[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}
	}

	public void cargarGrupoPoblacion() {
		List listaComunidadesEtnicasDom = new ArrayList<DominioDetalle>();
		listaComunidadesEtnicasDom = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='GRUPO_POBLACION_EXT_SOL_2018' order by dd.descripcion");
		setListaGrupoPoblacion(new SelectItem[listaComunidadesEtnicasDom.size()]);
		for (int i = 0; i < listaComunidadesEtnicasDom.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaComunidadesEtnicasDom.get(i);
			listaGrupoPoblacion[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}
	}

	public void cargarCicloVital() {
		List listaCicloVitalDom = new ArrayList<DominioDetalle>();
		listaCicloVitalDom = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='CICLO_VITAL_EXT_SOL_2018' order by dd.descripcion");
		setListaCicloVital(new SelectItem[listaCicloVitalDom.size()]);
		for (int i = 0; i < listaCicloVitalDom.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaCicloVitalDom.get(i);
			listaCicloVital[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}
	}

	public void cargarAreasEst() {
		List listaAreasEstDom = new ArrayList<DominioDetalle>();
		listaAreasEstDom = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='DOM_AREAS_ESTRATEGICAS_VRI' order by dd.descripcion");
		setListaAreasEst(new SelectItem[listaAreasEstDom.size()]);
		for (int i = 0; i < listaAreasEstDom.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaAreasEstDom.get(i);
			listaAreasEst[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}
	}

	public void cargarComunidadesEtnicas() {
		List listaComunidadesEtnicasDom = new ArrayList<DominioDetalle>();
		listaComunidadesEtnicasDom = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='COMUNIDADES_ETNICAS_EXT_SOL_2018' order by dd.descripcion");
		setListaComunidadesEtnicas(new SelectItem[listaComunidadesEtnicasDom.size()]);
		for (int i = 0; i < listaComunidadesEtnicasDom.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaComunidadesEtnicasDom.get(i);
			listaComunidadesEtnicas[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}
	}

	public void cargarCentrosPensamiento() {
		List listaCentrosPensamientoDom = new ArrayList<DominioDetalle>();
		listaCentrosPensamientoDom = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='CENTROS_PENSAMIENTO_EXT_SOL_2017' order by dd.descripcion");
		setListaCentrosPensamiento(new SelectItem[listaCentrosPensamientoDom.size()]);
		for (int i = 0; i < listaCentrosPensamientoDom.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaCentrosPensamientoDom.get(i);
			listaCentrosPensamiento[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}

		if (proyectoActual.getEjeTematico() != null) {
			if (!proyectoActual.getEjeTematico().equals("")) {
				tieneCentrosPensamiento = "S";
				cambioSiCentroPensamiento();
			}
		}
	}

	public void cargarLineasSeleccionadasProyecto() {
		listaLineasProyecto = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
				"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
						+ " and e.tipo = 'LINEAS_PROYECTO_EXT_SOL'");
		if (listaLineasProyecto == null) {
			listaLineasProyecto = new ArrayList<ValoresListasProyecto>();
		}
	}

	public void cargarLineasProyectos() {
		lineasProyectos2017 = crearListaItemDominioDetalle("LINEAS_PROYECTOS_EXT_SOL_2017");
	}

	public void adicionarLineaProyecto() {
		List<DominioDetalle> listaDomDet = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select e from DominioDetalle e where e.identificador.tipo = '" + lineaProyecto
						+ "' and e.identificador.id = 174");
		DominioDetalle g = obtenerDominioDetalleLista(lineaProyecto, listaDomDet);

		ValoresListasProyecto vlp = new ValoresListasProyecto();
		vlp.setProyecto(proyectoActual);
		vlp.setTipo("LINEAS_PROYECTO_EXT_SOL");
		vlp.setValor(g.getIdentificador().getTipo());
		vlp.setDescripcion(g.getDescripcion());

		if (!listaLineasProyecto.contains(vlp)) {
			listaLineasProyecto.add(vlp);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La línea ya se encuentra asociada al proyecto", "La línea ya se encuentra asociada al proyecto");
		}

	}

	public void eliminarLineaProyecto() {
		listaLineasProyecto.remove(lineaProyectoSeleccionada);
		listaLineasProyectoBorradas.add(lineaProyectoSeleccionada);
		lineaProyectoSeleccionada = new ValoresListasProyecto();
	}

	public void cambioSiCentroPensamiento() {
		if (tieneCentrosPensamiento.equals("S")) {
			siTieneCentrosPensamiento = true;
		} else {
			siTieneCentrosPensamiento = false;
		}
	}

	public void cargarIndicadoresApropiacion() {
		listaindicadoresApropiacion = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
				"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
						+ " and e.tipo = 'INDICADORES_APROPIACION_EXT_SOL'");
		if (listaindicadoresApropiacion == null) {
			listaindicadoresApropiacion = new ArrayList<ValoresListasProyecto>();
		}
	}

	public Long obtenerMaxOrderIndicadoresApropiacion(List<ValoresListasProyecto> indicadores) {
		Long max = 0L;
		if (indicadores != null) {
			Iterator<ValoresListasProyecto> i = indicadores.iterator();
			while (i.hasNext()) {
				ValoresListasProyecto ind = i.next();
				if (ind.getValor() != null && Long.parseLong(ind.getValor()) > max) {
					max = Long.parseLong(ind.getValor());
				}
			}
		}
		return max + 1;
	}

	public void eliminarIndicadoresApropiacion() {
		listaindicadoresApropiacion.remove(indicadoresApropiacionSeleccionada);
		listaindicadoresApropiacionBorradas.add(indicadoresApropiacionSeleccionada);
		indicadoresApropiacionSeleccionada = new ValoresListasProyecto();
	}

	public void adicionarIndicadoresApropiacion() {
		if (esCadenaVacia(indicadoresApropiacion)) {
			mensajeError("Por favor, ingrese la descripción del indicador.");
			return;
		}
		ValoresListasProyecto vlp = new ValoresListasProyecto();
		vlp.setProyecto(proyectoActual);
		vlp.setTipo("INDICADORES_APROPIACION_EXT_SOL");
		vlp.setValor(String.valueOf(obtenerMaxOrderIndicadoresApropiacion(listaindicadoresApropiacion)));
		vlp.setDescripcion(indicadoresApropiacion);

		if (!listaindicadoresApropiacion.contains(vlp)) {
			listaindicadoresApropiacion.add(vlp);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El indicador ya se encuentra asociado al proyecto",
					"El indicador ya se encuentra asociado al proyecto");
		}

	}

	public void cargarComunidadesEtnicasProyecto() {
		listaComunidadesEtnicasProyecto = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
				"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
						+ " and e.tipo = 'COMUNIDAD_ETNICA_EXT_SOL'");
		if (listaComunidadesEtnicasProyecto == null) {
			listaComunidadesEtnicasProyecto = new ArrayList<ValoresListasProyecto>();
		}
	}

	public void cargarListaAreasEst() {
		areasEst = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
				"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
						+ " and e.tipo = 'DOM_AREAS_ESTRATEGICAS_VRI'");
		if (areasEst == null) {
			areasEst = new ArrayList<ValoresListasProyecto>();
		}
	}

	public void adicionarComunidadEtnica() {
		DominioDetalle dd = null;
		DominioDetalle dd2 = null;
		DominioDetalle dd3 = null;
		DominioDetalle dd4 = null;
		boolean valido = true;
		if (comunidadEtnica.equals("")) {
			FacesContext.getCurrentInstance().addMessage("messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe definir la comunidad étnica a ingresar.", ""));
			valido = false;
		} else {
			String hqlComunidadEtnicaDomDet = "select e from DominioDetalle e, Dominio d where d.tipo = 'COMUNIDADES_ETNICAS_EXT_SOL_2018' and d.id = e.identificador.id and e.identificador.tipo = '"
					+ comunidadEtnica + "'";
			List listComunidadEtnicaDomDet = servicioGeneral.obtenerObjetos(hqlComunidadEtnicaDomDet);
			dd = (DominioDetalle) listComunidadEtnicaDomDet.get(0);
		}
		if (cicloVital.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe definir el ciclo vital principal.", ""));
			valido = false;
		} else {
			String hqlCicloVitalDomDet = "select e from DominioDetalle e, Dominio d where d.tipo = 'CICLO_VITAL_EXT_SOL_2018' and d.id = e.identificador.id and e.identificador.tipo = '"
					+ cicloVital + "'";
			List listCicloVitalDomDet = servicioGeneral.obtenerObjetos(hqlCicloVitalDomDet);
			dd4 = (DominioDetalle) listCicloVitalDomDet.get(0);
		}
		if (condicionPoblacion.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe definir la población principal a la que se dirige.", ""));
			valido = false;
		} else {
			String hqlCondicionPoblacionDomDet = "select e from DominioDetalle e, Dominio d where d.tipo = 'CONDICION_POBLACION_EXT_SOL_2018' and d.id = e.identificador.id and e.identificador.tipo = '"
					+ condicionPoblacion + "'";
			List listCondicionPoblacionDomDet = servicioGeneral.obtenerObjetos(hqlCondicionPoblacionDomDet);
			dd2 = (DominioDetalle) listCondicionPoblacionDomDet.get(0);
		}
		if (grupoPoblacion.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe definir el grupo principal al que se dirige.", ""));
			valido = false;
		} else {
			String hqlGrupoPoblacionDomDet = "select e from DominioDetalle e, Dominio d where d.tipo = 'GRUPO_POBLACION_EXT_SOL_2018' and d.id = e.identificador.id and e.identificador.tipo = '"
					+ grupoPoblacion + "'";
			List listGrupoPoblacionDomDet = servicioGeneral.obtenerObjetos(hqlGrupoPoblacionDomDet);
			dd3 = (DominioDetalle) listGrupoPoblacionDomDet.get(0);
		}
		try {
			Long cant = Long.parseLong(numeroPoblacion);
			if (cant == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe definir la cantidad estimada de población beneficiaria del proyecto.", ""));
				valido = false;
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La cantidad estimada de población beneficiaria del proyecto no es un número válido.", ""));
			valido = false;
		}
		if (valido) {
			ValoresListasProyecto vlp = new ValoresListasProyecto();
			vlp.setProyecto(proyectoActual);
			vlp.setTipo("COMUNIDAD_ETNICA_EXT_SOL");
			vlp.setValor(comunidadEtnica);
			vlp.setDescripcion(dd.getDescripcion());
			vlp.setValorDos(condicionPoblacion);
			vlp.setDescripcionDos(dd2.getDescripcion());
			vlp.setValorTres(grupoPoblacion);
			vlp.setDescripcionTres(dd3.getDescripcion());
			vlp.setNombreValor(numeroPoblacion);
			vlp.setDescripcionCuatro(dd4.getDescripcion());
			vlp.setValorCuatro(cicloVital);

			if (vlp.getValor().equals("0")) // Opción de la lista: No aplica
				listaComunidadesEtnicasProyecto.add(vlp);
			else {
				if (!listaComunidadesEtnicasProyecto.contains(vlp)) {
					listaComunidadesEtnicasProyecto.add(vlp);
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"La comunidad étnica ya se encuentra asociada al proyecto", ""));
				}
			}
		}
	}

	public void eliminarComunidadEtnica() {
		listaComunidadesEtnicasProyecto.remove(comunidadEtnicaSeleccionada);
		listaComunidadesEtnicasProyectoBorradas.add(comunidadEtnicaSeleccionada);
		comunidadEtnicaSeleccionada = new ValoresListasProyecto();
	}

	@Override
	protected void cargarValoresIniciales() {
		aproPry = new ApropiacionES();
		viabPry = new ViabiliadES();
		cargarListas();
		natIni = new NaturalezaIniciativas();
		listaIniciativas = new ArrayList<NaturalezaIniciativas>();
		listaApropiacion = new ArrayList<ApropiacionES>();
		listaViabilidad = new ArrayList<ViabiliadES>();

		proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.TODO_POR_ID);

		if (esInnoSocial2016 || esInnoSocial2018) {
			if (proyectoActual.getArticulacionIniciativas() != null
					&& !proyectoActual.getArticulacionIniciativas().equals("")) {
				String[] filIni = proyectoActual.getArticulacionIniciativas().split("<>");
				if ((!esInnoSocial2016 && !esInnoSocial2018)
						|| !proyectoActual.getArticulacionIniciativas().contains("<>")) {
					filIni = proyectoActual.getArticulacionIniciativas().split("&");
					for (int i = 0; i < filIni.length; i++) {
						NaturalezaIniciativas fsToNatIni = new NaturalezaIniciativas();
						String[] colIni = filIni[i].split("~");
						if (colIni.length != 1) {
							fsToNatIni.setNaturalezaIniciativa(colIni[0]);
							fsToNatIni.setDescripcion(colIni[1]);
							this.listaIniciativas.add(fsToNatIni);
						}
					}
				} else {
					for (int i = 0; i < filIni.length; i++) {
						NaturalezaIniciativas fsToNatIni = new NaturalezaIniciativas();
						String[] colIni = filIni[i].split("><");
						if (colIni.length != 1) {
							fsToNatIni.setNaturalezaIniciativa(colIni[0]);
							fsToNatIni.setDescripcion(colIni[1]);
							this.listaIniciativas.add(fsToNatIni);
						}
					}
				}
			}
			System.out.println("viabilidad " + proyectoActual.getViabilidad());
			if (proyectoActual.getViabilidad() != null && !proyectoActual.getViabilidad().equals("")) {
				String[] filIni = proyectoActual.getViabilidad().split("<>");
				if ((!esInnoSocial2016 && !esInnoSocial2018) || !proyectoActual.getViabilidad().contains("<>")) {
					filIni = proyectoActual.getViabilidad().split("&");
					for (int i = 0; i < filIni.length; i++) {
						ViabiliadES fsToNatIni = new ViabiliadES();
						String[] colIni = filIni[i].split("~");
						if (colIni.length != 1) {
							fsToNatIni.setDescripcion(colIni[0]);
							fsToNatIni.setEnlace(colIni[1]);
							this.listaViabilidad.add(fsToNatIni);
						}
					}
				} else {
					for (int i = 0; i < filIni.length; i++) {
						ViabiliadES fsToNatIni = new ViabiliadES();
						String[] colIni = filIni[i].split("><");
						if (colIni.length != 1) {
							fsToNatIni.setDescripcion(colIni[0]);
							fsToNatIni.setEnlace(colIni[1]);
							this.listaViabilidad.add(fsToNatIni);
						}
					}
				}
			}
			if (proyectoActual.getApropiacion() != null && !proyectoActual.getApropiacion().equals("")) {
				String[] filIni = proyectoActual.getApropiacion().split("<>");
				for (int i = 0; i < filIni.length; i++) {
					ApropiacionES fsToNatIni = new ApropiacionES();
					String[] colIni = filIni[i].split("><");
					fsToNatIni.setDescripcionApro(colIni[0]);
					fsToNatIni.setComunidad(colIni[1]);
					fsToNatIni.setEnlace(colIni[2]);
					this.listaApropiacion.add(fsToNatIni);
				}
			}
		} else {
			if (proyectoActual.getArticulacionIniciativas() != null
					&& !proyectoActual.getArticulacionIniciativas().equals("")) {
				String[] filIni = proyectoActual.getArticulacionIniciativas().split("&");
				for (int i = 0; i < filIni.length; i++) {
					NaturalezaIniciativas fsToNatIni = new NaturalezaIniciativas();
					String[] colIni = filIni[i].split("~");
					fsToNatIni.setNaturalezaIniciativa(colIni[0]);
					fsToNatIni.setDescripcion(colIni[1]);
					this.listaIniciativas.add(fsToNatIni);
				}
			}
			System.out.println("viabilidad " + proyectoActual.getViabilidad());
			if (proyectoActual.getViabilidad() != null && !proyectoActual.getViabilidad().equals("")) {
				String[] filIni = proyectoActual.getViabilidad().split("&");
				for (int i = 0; i < filIni.length; i++) {
					ViabiliadES fsToNatIni = new ViabiliadES();
					String[] colIni = filIni[i].split("~");
					if(colIni.length>0) {
						fsToNatIni.setDescripcion(colIni[0]);
						fsToNatIni.setEnlace("");
						if(colIni.length>=1) {
							fsToNatIni.setEnlace(colIni[1]);
						}
						this.listaViabilidad.add(fsToNatIni);
					}
					
				}
			}
			if (proyectoActual.getApropiacion() != null && !proyectoActual.getApropiacion().equals("")) {
				String[] filIni = proyectoActual.getApropiacion().split("&");
				for (int i = 0; i < filIni.length; i++) {
					ApropiacionES fsToNatIni = new ApropiacionES();
					String[] colIni = filIni[i].split("~");
					fsToNatIni.setDescripcionApro(colIni[0]);
					fsToNatIni.setComunidad(colIni[1]);
					fsToNatIni.setEnlace(colIni[2]);
					this.listaApropiacion.add(fsToNatIni);
				}
			}
		}

		// SE CARGAN LA CIUDAD Y EL DEPARTAMENTO
		Set ciudades = proyectoActual.getCiudades();
		Iterator it = ciudades.iterator();
		if (it.hasNext()) {
			ciudadActual = (Ciudad) it.next();
			departamentoActual = servicioGeneral.obtenerDepartamento(ciudadActual);
			departamentoActual = buscarDepartamento(departamentoActual.getId());
			listaCiudades = servicioGeneral.obtenerUbicacion("Ciudad", "", "departamento", departamentoActual,false);
			ciudadItem = new SelectItem[listaCiudades.size()];
			for (int i = 0; i < listaCiudades.size(); i++) {
				Ciudad ci = (Ciudad) listaCiudades.get(i);
				ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
				ci = null;
			}
		}

	}

	public void cargarTemaProyectos() {
		List listaAreasSec = servicioGeneral.obtenerObjetos(
				"select e from AreaTematica e where e.proyecto.id = " + proyectoActual.getId() + "and e.tipo = 3");

		if (listaAreasSec.size() > 0) {

			for (int i = 0; i < listaAreasSec.size(); i++) {
				AreaTematica atDos = (AreaTematica) listaAreasSec.get(i);

				DominioDetalle arDos = new DominioDetalle();
				List listaDomDetarDos = new ArrayList<DominioDetalle>();
				listaDomDetarDos = servicioGeneral.obtenerObjetos(
						"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
								+ "SUBTEMAS_EXT_SOL_2016" + "' and dd.identificador.tipo = '"
								+ atDos.getProyectoAreaTematica().getIdentificador().getTipo() + "'");
				if (listaDomDetarDos.size() > 0) {
					arDos = (DominioDetalle) listaDomDetarDos.get(0);

					System.out.println("consulta uno ***************************======= "
							+ "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and dd.identificador.tipo ='"
							+ arDos.getIdentificador().getTipo() + "'");
					List listaAreasSecTemp = new ArrayList<DominioDetalle>();
					listaAreasSecTemp = servicioGeneral.obtenerObjetos(
							"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and dd.identificador.tipo ='"
									+ arDos.getIdentificador().getTipo() + "'");
					DominioDetalle dd = (DominioDetalle) listaAreasSecTemp.get(0);

					DominioDetalle arUno = new DominioDetalle();
					List listaDomDetarUno = new ArrayList<DominioDetalle>();
					System.out.println("consulta:  ====== "
							+ "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
							+ "TEMATICAS_EXT_SOL_2016" + "' and dd.identificador.tipo = '" + dd.getEstado() + "'");
					listaDomDetarUno = servicioGeneral.obtenerObjetos(
							"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
									+ "TEMATICAS_EXT_SOL_2016" + "' and dd.identificador.tipo = '" + dd.getEstado()
									+ "'");
					arUno = (DominioDetalle) listaDomDetarUno.get(0);

					AreaTematicaVista arTem = new AreaTematicaVista();
					arTem.setProyecto(proyectoActual);
					arTem.setNombreArea(arUno.getDescripcion());
					arTem.setAreaTematica(arUno);
					arTem.setNombreSubArea(arDos.getDescripcion());
					arTem.setSubAreaTematica(arDos);
					arTem.setTipo(3l);
					dd = null;

					listaAreasTematicas.add(arTem);
				}

			}

		}
	}

	public void cargarTemas() {
		String consulta1 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
				+ "TEMATICAS_EXT_SOL_2016" + "'  order by dd.descripcion";
		listaAreaCiencia = servicioGeneral.obtenerObjetos(consulta1);

		if (listaAreaCiencia.size() > 0) {
			areaCienciaItems = new SelectItem[listaAreaCiencia.size()];
			for (int i = 0; i < listaAreaCiencia.size(); i++) {
				DominioDetalle dominio = (DominioDetalle) listaAreaCiencia.get(i);
				areaCienciaItems[i] = new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion());
			}
			DominioDetalle dominio = (DominioDetalle) listaAreaCiencia.get(0);
			areaCienciaSec = dominio.getIdentificador().getTipo();
		} else {
			areaCienciaItems = new SelectItem[1];
			areaCienciaItems[0] = new SelectItem("0", " - ");

		}
	}

	public void cambiarAreaSec() {
		String consulta3 = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
				+ "SUBTEMAS_EXT_SOL_2016" + "' and  dd.estado = '" + areaCienciaSec + "' order by dd.descripcion";
		listaSubAreaCiencia = servicioGeneral.obtenerObjetos(consulta3);

		if (listaSubAreaCiencia.size() > 0) {
			subAreaCienciaSecItems = new SelectItem[listaSubAreaCiencia.size()];
			for (int i = 0; i < listaSubAreaCiencia.size(); i++) {
				DominioDetalle dominio = (DominioDetalle) listaSubAreaCiencia.get(i);
				String nombre = dominio.getDescripcion();
				if (nombre != null && nombre.length() > 140) {
					nombre = nombre.substring(0, 140) + "...";
				}
				subAreaCienciaSecItems[i] = new SelectItem(dominio.getIdentificador().getTipo(), nombre);
			}
		} else {
			subAreaCienciaSecItems = new SelectItem[1];
			subAreaCienciaSecItems[0] = new SelectItem("0", " - ");
		}
	}

	public void agregarArea() {

		boolean existeArea = false;

		if (this.areaCienciaSec.equals("Seleccione una opción")) {
			existeArea = true;
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Seleccione el área científica y tecnológica principal",
					"Seleccione el área científica y tecnológica principal");
			// mostrarMensaje(message, botonAreasTabla);
		} else {

			DominioDetalle arUno = new DominioDetalle();
			List listaDomDetarUno = new ArrayList<DominioDetalle>();
			listaDomDetarUno = servicioGeneral.obtenerObjetos(
					"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
							+ "TEMATICAS_EXT_SOL_2016" + "' and dd.identificador.tipo = '" + areaCienciaSec + "'");
			arUno = (DominioDetalle) listaDomDetarUno.get(0);

			DominioDetalle arDos = new DominioDetalle();
			List listaDomDetarDos = new ArrayList<DominioDetalle>();
			listaDomDetarDos = servicioGeneral.obtenerObjetos(
					"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
							+ "SUBTEMAS_EXT_SOL_2016" + "' and dd.identificador.tipo = '" + subAreaCienciaSec + "'");
			arDos = (DominioDetalle) listaDomDetarDos.get(0);

			AreaTematicaVista arTem = new AreaTematicaVista();
			arTem.setProyecto(proyectoActual);
			arTem.setNombreArea(arUno.getDescripcion());
			arTem.setAreaTematica(arUno);
			arTem.setNombreSubArea(arDos.getDescripcion());
			arTem.setSubAreaTematica(arDos);
			arTem.setTipo(2l);

			for (int i = 0; i < listaAreasTematicas.size(); i++) {
				AreaTematicaVista atv = listaAreasTematicas.get(i);
				if (arTem.getSubAreaTematica().getIdentificador().getTipo()
						.equals(atv.getSubAreaTematica().getIdentificador().getTipo())) {
					existeArea = true;
				}
			}

			if (!existeArea) {
				listaAreasTematicas.add(arTem);
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"El área científica y tecnológica seleccionada ya se encuentra vinculada al proyecto",
						"El área científica y tecnológica seleccionada ya se encuentra vinculada al proyecto");
				// mostrarMensaje(message, botonAreasTabla);
			}
		}
	}

	public void eliminarArea() {
		try {
			listaAreasTematicas.remove(areaSeleccionada);
			// proyectoActual.getAreasTematicas().remove(areaSeleccionada);
			areaSeleccionada = new AreaTematicaVista();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void cargarListas() {

		listaNivelSostenibilidad = new ArrayList<DominioDetalle>();
		listaNivelSostenibilidad = servicioGeneral
				.obtenerObjetos("select e from DominioDetalle e where e.identificador.id = '108'");
		nivelSostenibilidadItem = new SelectItem[listaNivelSostenibilidad.size()];
		for (int i = 0; i < listaNivelSostenibilidad.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaNivelSostenibilidad.get(i);
			nivelSostenibilidadItem[i] = new SelectItem(dd.getDescripcion(), dd.getDescripcion());
			dd = null;
		}

		listaNaturalezaEntidad = new ArrayList<DominioDetalle>();
		listaNaturalezaEntidad = servicioGeneral
				.obtenerObjetos("select e from DominioDetalle e where e.identificador.id = '26'");
		naturalezaEntidadItem = new SelectItem[listaNaturalezaEntidad.size()];
		for (int i = 0; i < listaNaturalezaEntidad.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaNaturalezaEntidad.get(i);
			naturalezaEntidadItem[i] = new SelectItem(dd.getDescripcion(), dd.getDescripcion());
			dd = null;
		}

		listaZonas = new ArrayList<SelectItem>();
		String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = 24";
		List lista = servicioGeneral.obtenerObjetos(consulta);

		for (int i = 0; i < lista.size(); i++) {
			DominioDetalle dominio = (DominioDetalle) lista.get(i);
			listaZonas.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
		}

		// listaProblemas =
		// servicioGeneral.obtenerObjetos("select e from Preinscripcion_ECP e
		// where e.curso = 22717 and e.estado = 'AP'");
		listaProblemas = servicioGeneral.obtenerObjetos("select e from Preinscripcion_ECP e where e.curso = 39232");

		if (listaProblemas != null && listaProblemas.size() > 0) {
			problemasItem = new SelectItem[listaProblemas.size()];
			for (int i = 0; i < listaProblemas.size(); i++) {
				Preinscripcion_ECP pro = (Preinscripcion_ECP) listaProblemas.get(i);
				problemasItem[i] = new SelectItem(pro.getId_pre(), pro.getId_pre() + " - " + pro.getNombreProblema());
				pro = null;
			}

			Preinscripcion_ECP p = (Preinscripcion_ECP) listaProblemas.get(0);
			problemaSeleccionado = p.getId_pre().toString();
		}

	}

	public void cargarProyectosContinuidad() {

		List<DominioDetalle> listaProblemasContinuidad = new ArrayList<DominioDetalle>();
		listaProblemasContinuidad = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ "PROY_CONVS_EXT_SOL_2016" + "' order by dd.descripcion");

		if (listaProblemasContinuidad != null && listaProblemasContinuidad.size() > 0) {
			problemasContinuidadItem = new SelectItem[listaProblemasContinuidad.size()];
			for (int i = 0; i < listaProblemasContinuidad.size(); i++) {
				DominioDetalle dd = (DominioDetalle) listaProblemasContinuidad.get(i);
				problemasContinuidadItem[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
				dd = null;
			}
		}
	}

	public void cargarEnfoque() {
		List<DominioDetalle> listaEnfoques = new ArrayList<DominioDetalle>();
		listaEnfoques = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
						+ "ENFO_CONV_EXT_SOL_2016" + "' order by dd.descripcion");

		if (listaEnfoques != null && listaEnfoques.size() > 0) {
			enfoquesItem = new SelectItem[listaEnfoques.size()];
			for (int i = 0; i < listaEnfoques.size(); i++) {
				DominioDetalle dd = (DominioDetalle) listaEnfoques.get(i);
				enfoquesItem[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
				dd = null;
			}
		}
	}

	private void obtenerListaDepartamentos() {
		// VINCULACION DE LOS DEPARTAMENTOS CONTENIDOS EN LA BASE DE DATOS CON
		// EL COMPONENTE
		// WEB QUE PERMITE LA ESCOGENCIA DE UN DEPARTAMENTO
		listaDepartamentos = servicioGeneral.obtenerUbicacion("Departamento", "", "", null,false);
		departamentoItem = new SelectItem[listaDepartamentos.size()];
		for (int i = 0; i < listaDepartamentos.size(); i++) {
			Departamento dep = (Departamento) listaDepartamentos.get(i);
			departamentoItem[i] = new SelectItem(dep.getId(), dep.getNombre());
			dep = null;
		}
		departamentoActual = (Departamento) listaDepartamentos.get(0);
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
		ciudadActual = (Ciudad) listaCiudades.get(0);
	}

	public void cambiarDepartamento() {
		// VINCULACION DE LOS DEPARTAMENTOS CONTENIDOS EN LA BASE DE DATOS CON
		// EL COMPONENTE
		// WEB QUE PERMITE LA ESCOGENCIA DE UN DEPARTAMENTO Y LA RECARGA DE LAS
		// CIUDADES
		departamentoActual = buscarDepartamento((departamentoActual.getId()));
		listaCiudades = servicioGeneral.obtenerUbicacion("Ciudad", "", "departamento", departamentoActual,false);
		ciudadItem = new SelectItem[listaCiudades.size()];
		for (int i = 0; i < listaCiudades.size(); i++) {
			Ciudad ci = (Ciudad) listaCiudades.get(i);
			ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
		ciudadActual = (Ciudad) listaCiudades.get(0);
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

	public boolean validarIniciativas() {
		boolean val = true;

		if (this.natIni.getDescripcion().trim().equals("") || this.natIni.getDescripcion() == null) {
			val = false;
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Por favor ingresese la descripción de la iniciativa",
					"Por favor ingresese la descripción de la iniciativa");
			if (!esInnoSocial2018) {
				mostrarMensaje(message, idDescrInicia);
			} else {
				mostrarMensaje(message, idDescrIniciaArtiSoste);
			}
		}

		return val;
	}

	public boolean validarCampos() {

		boolean val = true;

		if (this.proyectoActual.getPorqueNivelSostenibilidad().trim().equals("")
				|| this.proyectoActual.getPorqueNivelSostenibilidad() == null) {
			val = false;
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Por favor ingresese la razón por la cual seleccionó el nivel de sostenibilidad",
					"Por favor ingresese la razón por la cual seleccionó el nivel de sostenibilidad");
			mostrarMensaje(message, pqNivSos);
		}

		if (this.proyectoActual.getPorqueContinuidadProyecto() != null) {
			if (this.proyectoActual.getPorqueContinuidadProyecto().trim().equals("")) {
				val = false;
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Por favor ingresese la razón por la cual seleccionó la continuidad del proyecto",
						"Por favor ingresese la razón por la cual seleccionó la continuidad del proyecto");
				mostrarMensaje(message, pqContinuidad);
			}
		}
		
		
		if(formularioEspecifica.getId()!=null) {
			if (formularioEspecifica.getListaSel1Obligatorio()) {
				if (proyectoActual.getEjeTematico() == null || proyectoActual.getEjeTematico().equals("")) {
					val = false;
					mensajeError(formularioEspecifica.getTextoListaSeleccion() + ": Campo obligatorio");
				}
			}
			if (formularioEspecifica.getListaSeleccionMultiple() && valoresListasProyectosParametrizado.isEmpty()) {
				val = false;
				mensajeError(
						"Por favor especificar, al menos, un elemento de la lista '" + formularioEspecifica.getTextoListaSeleccionmultiple() + "'.");
			}
			if (formularioEspecifica.getListaSeleccionMultiple2() && valoresListasProyectosParametrizado2.isEmpty()) {
				val = false;
				mensajeError(
						"Por favor especificar, al menos, un elemento de la lista '" + formularioEspecifica.getTextoListaSeleccionmultiple2() + "'.");
			}
			
			if (formularioEspecifica.getCampoUnoObligatorio() && esCadenaVacia(proyectoActual.getCampoGenericoUno())) {
				val = false;
				mensajeError(
						"El campo " + formularioEspecifica.getCampoUnoTexto() + " es obligatorio.");
			
			}
			
			if (formularioEspecifica.getCampoDosObligatorio() && esCadenaVacia(proyectoActual.getCampoGenericoDos())) {
				val = false;
				mensajeError(
						"El campo " + formularioEspecifica.getCampoDosTexto() + " es obligatorio.");
			
			}
			
			if (formularioEspecifica.getCampoTresObligatorio() && esCadenaVacia(proyectoActual.getCampoGenericoTres())) {
				val = false;
				mensajeError(
						"El campo " + formularioEspecifica.getCampoTresTexto() + " es obligatorio.");
			}
			
			if (formularioEspecifica.getCampoCuatroObligatorio() && esCadenaVacia(proyectoActual.getCampoGenericoCuatro())) {
				val = false;
				mensajeError(
						"El campo " + formularioEspecifica.getCampoCuatroTexto() + " es obligatorio.");
			}
			
			if (formularioEspecifica.getCampoCincoObligatorio() && esCadenaVacia(proyectoActual.getCampoGenericoCinco())) {
				val = false;
				mensajeError(
						"El campo " + formularioEspecifica.getCampoCincoTexto() + " es obligatorio.");
			}
			
			if (formularioEspecifica.getCampoSeisObligatorio() && esCadenaVacia(proyectoActual.getCampoGenericoSeis())) {
				val = false;
				mensajeError(
						"El campo " + formularioEspecifica.getCampoSeisTexto() + " es obligatorio.");
			}
			
			if (formularioEspecifica.getCampoSieteObligatorio() && esCadenaVacia(proyectoActual.getCampoGenericoSiete())) {
				val = false;
				mensajeError(
						"El campo " + formularioEspecifica.getCampoSieteTexto() + " es obligatorio.");
			}
			
			if (formularioEspecifica.getCampoOchoObligatorio() && esCadenaVacia(proyectoActual.getCampoGenericoOcho())) {
				val = false;
				mensajeError(
						"El campo " + formularioEspecifica.getCampoOchoTexto() + " es obligatorio.");
			}
			
			if (formularioEspecifica.getCampoDiezObligatorio() && esCadenaVacia(proyectoActual.getCampoGenericoDiez())) {
				val = false;
				mensajeError(
						"El campo " + formularioEspecifica.getCampoDiezTexto() + " es obligatorio.");
			}
					
		}
		
		if (esInnoSocial2022) {
			if (listaViabilidad.isEmpty()) {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Por favor registre la información relacionada con trabajos previos realizados por alguno de los integrantes del grupo proponente.",
						"Por favor registre la información relacionada con trabajos previos realizados por alguno de los integrantes del grupo proponente."));
			}
			if (listaComunidadesEtnicasProyecto.isEmpty()) {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Por favor registre la información relacionada con las comunidades étnicas con las que se relacionará el proyecto.",
						"Por favor registre la información relacionada con las comunidades étnicas con las que se relacionará el proyecto."));
			}
			if (this.proyectoActual.getMetodologia() != null) {
				if (this.proyectoActual.getMetodologia().trim().equals("")) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor describa la metodología que usará para desarrollar su proyecto.",
									"Por favor describa la metodología que usará para desarrollar su proyecto."));
				}
			} else {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor describa la metodología que usará para desarrollar su proyecto.",
								"Por favor describa la metodología que usará para desarrollar su proyecto."));
			}

			if (this.proyectoActual.getCondicionesEntornoExtSol() != null) {
				if (this.proyectoActual.getCondicionesEntornoExtSol().trim().equals("")) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre las condiciones del entorno del proyecto.",
									"Por favor registre las condiciones del entorno del proyecto."));
				}
			} else {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor registre las condiciones del entorno del proyecto",
								"Por favor registre las condiciones del entorno del proyecto."));
			}
			if (this.proyectoActual.getSolucionAlternaExtSol() != null) {
				if (this.proyectoActual.getSolucionAlternaExtSol().trim().equals("")) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre la alternativa de solución del proyecto.",
									"Por favor registre la alternativa de solución del proyecto."));
				}
			} else {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor registre la alternativa de solución del proyecto.",
								"Por favor registre la alternativa de solución del proyecto."));
			}
			if (this.proyectoActual.getNivelSostenibilidad() != null) {
				if (this.proyectoActual.getNivelSostenibilidad().trim().equals("")) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre el nivel de sostenibilidad del proyecto.",
									"Por favor registre el nivel de sostenibilidad del proyecto."));
				}
			} else {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor registre el nivel de sostenibilidad del proyecto.",
								"Por favor registre el nivel de sostenibilidad del proyecto."));
			}

			if (this.proyectoActual.getPorqueNivelSostenibilidad() != null) {
				if (this.proyectoActual.getPorqueNivelSostenibilidad().trim().equals("")) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre el por qué del nivel de sostenibilidad del proyecto.",
									"Por favor registre el por qué del nivel de sostenibilidad del proyecto."));
				}
			} else {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor registre el por qué del nivel de sostenibilidad del proyecto.",
								"Por favor registre el por qué del nivel de sostenibilidad del proyecto."));
			}
			if (this.proyectoActual.getDescripcion() != null) {
				if (this.proyectoActual.getDescripcion().trim().equals("")) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre las estrategias que harán sostenible el proyecto una vez finalizada la participación de la UN.",
							"Por favor registre las estrategias que harán sostenible el proyecto una vez finalizada la participación de la UN."));
				}
			} else {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Por favor registre las estrategias que harán sostenible el proyecto una vez finalizada la participación de la UN.",
						"Por favor registre las estrategias que harán sostenible el proyecto una vez finalizada la participación de la UN."));
			}
			if (this.proyectoActual.getAliados().equals("Si") && esCadenaVacia(proyectoActual.getDescripcion())) {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor indique cuales son sus aliados estratégicos y cómo aportarán al proyecto.",
								"Por favor indique cuales son sus aliados estratégicos y cómo aportarán al proyecto."));
			}
			if (this.proyectoActual.getConsideracionesEticas() != null) {
				if (this.proyectoActual.getConsideracionesEticas().trim().equals("")) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre si el proyecto cuenta con cofinanciación por parte de entidades externas y/o comunidades.",
							"Por favor registre si el proyecto cuenta con cofinanciación por parte de entidades externas y/o comunidades."));
				}
			} else {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Por favor registre si el proyecto cuenta con cofinanciación por parte de entidades externas y/o comunidades.",
						"Por favor registre si el proyecto cuenta con cofinanciación por parte de entidades externas y/o comunidades."));
			}
			if (this.proyectoActual.getProcesosTransferenciaExtSol() != null) {
				if (this.proyectoActual.getProcesosTransferenciaExtSol().trim().equals("")) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre el planteamiento de procesos de intercambio de conocimiento con la comunidad.",
							"Por favor registre el planteamiento de procesos de intercambio de conocimiento con la comunidad."));
				}
			} else {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Por favor registre el planteamiento de procesos de intercambio de conocimiento con la comunidad.",
						"Por favor registre el planteamiento de procesos de intercambio de conocimiento con la comunidad."));
			}

			if (this.proyectoActual.getParticipacionComunidadExtSol() != null) {
				if (this.proyectoActual.getParticipacionComunidadExtSol().trim().equals("")) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre el grado de participación de la comunidad en la construcción de la propuesta y planteamiento de roles de la comunidad en la ejecución del proyecto.",
							"Por favor registre el grado de participación de la comunidad en la construcción de la propuesta y planteamiento de roles de la comunidad en la ejecución del proyecto."));
				}
			} else {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Por favor registre el grado de participación de la comunidad en la construcción de la propuesta y planteamiento de roles de la comunidad en la ejecución del proyecto.",
						"Por favor registre el grado de participación de la comunidad en la construcción de la propuesta y planteamiento de roles de la comunidad en la ejecución del proyecto."));
			}
			if (this.proyectoActual.getAplicabilidadJustificacionAEExtSol() != null) {
				if (this.proyectoActual.getAplicabilidadJustificacionAEExtSol().trim().equals("")) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre el Fomento de la innovación pedagógica mediante el desarrollo del proyecto de extensión postulado.",
							"Por favor registre el Fomento de la innovación pedagógica mediante el desarrollo del proyecto de extensión postulado"));
				}
			} else {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Por favor registre el Fomento de la innovación pedagógica mediante el desarrollo del proyecto de extensión postulado",
						"Por favor registre el Fomento de la innovación pedagógica mediante el desarrollo del proyecto de extensión postulado"));
			}
			if (this.proyectoActual.getImpactoEsperado() != null) {
				if (this.proyectoActual.getImpactoEsperado().trim().equals("")) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre la Articulación con grupos de investigación y semilleros de investigación, creación, extensión solidaria o innovación para el desarrollo del proyecto de extensión postulado.",
							"Por favor registre la Articulación con grupos de investigación y semilleros de investigación, creación, extensión solidaria o innovación para el desarrollo del proyecto de extensión postulado."));
				}
			} else {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Por favor registre la Articulación con grupos de investigación y semilleros de investigación, creación, extensión solidaria o innovación para el desarrollo del proyecto de extensión postulado.",
						"Por favor registre la Articulación con grupos de investigación y semilleros de investigación, creación, extensión solidaria o innovación para el desarrollo del proyecto de extensión postulado."));
			}
			if (proyectoActual.getCampoGenericoOcho() == null || proyectoActual.getCampoGenericoOcho().equals("")) {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Por favor registre la Articulación con resultados de investigación previamente obtenidos, para el desarrollo del proyecto de extensión postulado.",
						"Por favor registre la Articulación con resultados de investigación previamente obtenidos, para el desarrollo del proyecto de extensión postulado."));
			}
			if (this.proyectoActual.getCampoGenericoNueve() != null) {
				if (this.proyectoActual.getCampoGenericoNueve().trim().equals("")) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre las consideraciones éticas del proyecto.",
									"Por favor registre las consideraciones éticas del proyecto."));
				}
			} else {
				val = false;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor registre las consideraciones éticas del proyecto.",
								"Por favor registre las consideraciones éticas del proyecto."));
			}
		} else {
			if (esInnoSocial2017) {
				if (listaLineasProyecto.isEmpty()) {
					val = false;
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Por favor asocie al menos una línea al proyecto",
							"Por favor asocie al menos una línea al proyecto");
					mostrarMensaje(message, botonLineas);
				}
			}

			if (esInnoSocial2017 || esInnoSocial2017_M2) {

				if (this.proyectoActual.getDescripcion() != null) {
					if (this.proyectoActual.getDescripcion().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor registre las estrategias que harán sostenible el proyecto una vez finalizada la participación de la UN.",
								"Por favor registre las estrategias que harán sostenible el proyecto una vez finalizada la participación de la UN."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre las estrategias que harán sostenible el proyecto una vez finalizada la participación de la UN.",
							"Por favor registre las estrategias que harán sostenible el proyecto una vez finalizada la participación de la UN."));
				}

				if (this.proyectoActual.getObservacionesSostenibilidad() != null) {
					if (this.proyectoActual.getObservacionesSostenibilidad().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										"Por favor registre las configuraciones sociales y nuevas metodologías.",
										"Por favor registre las configuraciones sociales y nuevas metodologías."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre las configuraciones sociales y nuevas metodologías.",
									"Por favor registre las configuraciones sociales y nuevas metodologías."));
				}

				if (listaIniciativas.isEmpty()) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre la articulación del proyecto con otras iniciativas.",
									"Por favor registre la articulación del proyecto con otras iniciativas."));
				}

				if (this.proyectoActual.getConsideracionesEticas() != null) {
					if (this.proyectoActual.getConsideracionesEticas().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor registre si el proyecto cuenta cofinanciación por parte de entidades externas y/o comunidades.",
								"Por favor registre si el proyecto cuenta cofinanciación por parte de entidades externas y/o comunidades."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre si el proyecto cuenta cofinanciación por parte de entidades externas y/o comunidades.",
							"Por favor registre si el proyecto cuenta cofinanciación por parte de entidades externas y/o comunidades."));
				}

				if (tieneCentrosPensamiento.equals("S")) {
					if (this.proyectoActual.getEjeTematico().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										"Por favor registre el centro de pensamiento.",
										"Por favor registre el centro de pensamiento."));
					}
				}

				if (this.proyectoActual.getMetodologia() != null) {
					if (this.proyectoActual.getMetodologia().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										"Por favor registre la Metodología y estrategia.",
										"Por favor registre la Metodología y estrategia."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre la Metodología y estrategia.",
									"Por favor registre la Metodología y estrategia."));
				}

				if (this.proyectoActual.getMarcoConceptualExtSol() != null) {
					if (this.proyectoActual.getMarcoConceptualExtSol().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor registre el marco conceptual.", "Por favor registre el marco conceptual."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre el marco conceptual.", "Por favor registre el marco conceptual."));
				}

				if (this.proyectoActual.getCondicionesEntornoExtSol() != null) {
					if (this.proyectoActual.getCondicionesEntornoExtSol().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										"Por favor registre las condiciones del entorno del proyecto.",
										"Por favor registre las condiciones del entorno del proyecto."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre las condiciones del entorno del proyecto.",
									"Por favor registre las condiciones del entorno del proyecto."));
				}

				if (this.proyectoActual.getSolucionAlternaExtSol() != null) {
					if (this.proyectoActual.getSolucionAlternaExtSol().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										"Por favor registre la alternativa de solución del proyecto.",
										"Por favor registre la alternativa de solución del proyecto."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre la alternativa de solución del proyecto.",
									"Por favor registre la alternativa de solución del proyecto."));
				}

				if (this.proyectoActual.getProcesosTransferenciaExtSol() != null) {
					if (this.proyectoActual.getProcesosTransferenciaExtSol().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor registre el planteamiento de procesos de transferencia de conocimiento comunidad.",
								"Por favor registre el planteamiento de procesos de transferencia de conocimiento comunidad."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre el planteamiento de procesos de transferencia de conocimiento comunidad.",
							"Por favor registre el planteamiento de procesos de transferencia de conocimiento comunidad."));
				}

				if (this.proyectoActual.getPlanteamientoRolesExtSol() != null) {
					if (this.proyectoActual.getPlanteamientoRolesExtSol().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor registre el planteamiento de roles de la comunidad en la ejecución del proyecto.",
								"Por favor registre el planteamiento de roles de la comunidad en la ejecución del proyecto."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre el planteamiento de roles de la comunidad en la ejecución del proyecto.",
							"Por favor registre el planteamiento de roles de la comunidad en la ejecución del proyecto."));
				}

				if (this.proyectoActual.getObservacionesSostenibilidad() != null) {
					if (this.proyectoActual.getObservacionesSostenibilidad().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										"Por favor registre las configuraciones sociales y nuevas metodologías.",
										"Por favor registre las configuraciones sociales y nuevas metodologías."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre las configuraciones sociales y nuevas metodologías.",
									"Por favor registre las configuraciones sociales y nuevas metodologías."));
				}

				if (listaindicadoresApropiacion.isEmpty()) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre los indicadores de apropiación de la innovación por la comunidad.",
							"Por favor registre los indicadores de apropiación de la innovación por la comunidad."));
				}

				if (this.proyectoActual.getParticipacionComunidadExtSol() != null) {
					if (this.proyectoActual.getParticipacionComunidadExtSol().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor registre el grado de participación de la comunidad en la construcción de la propuesta.",
								"Por favor registre el grado de participación de la comunidad en la construcción de la propuesta."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre el grado de participación de la comunidad en la construcción de la propuesta.",
							"Por favor registre el grado de participación de la comunidad en la construcción de la propuesta."));
				}

			}
			if (esInnoSocial2018) {
				if (this.ciudadActual != null) {
					if (this.ciudadActual.getId().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										"Por favor registre la ciudad o municipio en donde se ejecutará el proyecto.",
										"Por favor registre la ciudad o municipio en donde se ejecutará el proyecto."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre la ciudad o municipio en donde se ejecutará el proyecto.",
									"Por favor registre la ciudad o municipio en donde se ejecutará el proyecto."));
				}

				if (listaViabilidad.isEmpty()) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre la información relacionada con trabajos previos realizados por alguno de los integrantes del grupo proponente.",
							"Por favor registre la información relacionada con trabajos previos realizados por alguno de los integrantes del grupo proponente."));
				}

				if (listaComunidadesEtnicasProyecto.isEmpty()) {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre la información relacionada con las comunidades étnicas con las que se relacionará el proyecto.",
							"Por favor registre la información relacionada con las comunidades étnicas con las que se relacionará el proyecto."));
				}

				if (this.proyectoActual.getNivelSostenibilidad() != null) {
					if (this.proyectoActual.getNivelSostenibilidad().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										"Por favor registre el nivel de sostenibilidad del proyecto.",
										"Por favor registre el nivel de sostenibilidad del proyecto."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre el nivel de sostenibilidad del proyecto.",
									"Por favor registre el nivel de sostenibilidad del proyecto."));
				}

				if (this.proyectoActual.getPorqueNivelSostenibilidad() != null) {
					if (this.proyectoActual.getPorqueNivelSostenibilidad().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										"Por favor registre el por qué del nivel de sostenibilidad del proyecto.",
										"Por favor registre el por qué del nivel de sostenibilidad del proyecto."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre el por qué del nivel de sostenibilidad del proyecto.",
									"Por favor registre el por qué del nivel de sostenibilidad del proyecto."));
				}

				if (this.proyectoActual.getDescripcion() != null) {
					if (this.proyectoActual.getDescripcion().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor registre las estrategias que harán sostenible el proyecto una vez finalizada la participación de la UN.",
								"Por favor registre las estrategias que harán sostenible el proyecto una vez finalizada la participación de la UN."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre las estrategias que harán sostenible el proyecto una vez finalizada la participación de la UN.",
							"Por favor registre las estrategias que harán sostenible el proyecto una vez finalizada la participación de la UN."));
				}

					if (this.tieneCentrosPensamiento != null && this.tieneCentrosPensamiento.equals("S")) {
						if (this.proyectoActual.getEjeTematico() != null) {
							if (this.proyectoActual.getEjeTematico().trim().equals("")) {
								val = false;
								FacesContext.getCurrentInstance().addMessage(null,
										new FacesMessage(FacesMessage.SEVERITY_FATAL,
												"Por favor seleccione el centro de pensamiento.",
												"Por favor seleccione el centro de pensamiento."));
							}
						} else {
							val = false;
							FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_FATAL,
											"Por favor seleccione el centro de pensamiento.",
											"Por favor seleccione el centro de pensamiento."));
						}
					}
				if (this.proyectoActual.getMetodologia() != null) {
					if (this.proyectoActual.getMetodologia().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										"Por favor describa la metodología que usará para desarrollar su proyecto.",
										"Por favor describa la metodología que usará para desarrollar su proyecto."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor describa la metodología que usará para desarrollar su proyecto.",
									"Por favor describa la metodología que usará para desarrollar su proyecto."));
				}

				if (this.proyectoActual.getMarcoConceptualExtSol() != null) {
					if (this.proyectoActual.getMarcoConceptualExtSol().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor describa el marco teórico y conceptual en que se basará el proyecto.",
								"Por favor describa el marco teórico y conceptual en que se basará el proyecto."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor describa el marco teórico y conceptual en que se basará el proyecto.",
									"Por favor describa el marco teórico y conceptual en que se basará el proyecto."));
				}

				if (this.proyectoActual.getCondicionesEntornoExtSol() != null) {
					if (this.proyectoActual.getCondicionesEntornoExtSol().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										"Por favor registre las condiciones del entorno del proyecto.",
										"Por favor registre las condiciones del entorno del proyecto."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre las condiciones del entorno del proyecto",
									"Por favor registre las condiciones del entorno del proyecto."));
				}

				if (this.proyectoActual.getProcesosTransferenciaExtSol() != null) {
					if (this.proyectoActual.getProcesosTransferenciaExtSol().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor registre el planteamiento de procesos de intercambio de conocimiento con la comunidad.",
								"Por favor registre el planteamiento de procesos de intercambio de conocimiento con la comunidad."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre el planteamiento de procesos de intercambio de conocimiento con la comunidad.",
							"Por favor registre el planteamiento de procesos de intercambio de conocimiento con la comunidad."));
				}

				if (this.proyectoActual.getParticipacionComunidadExtSol() != null) {
					if (this.proyectoActual.getParticipacionComunidadExtSol().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor registre el grado de participación de la comunidad en la construcción de la propuesta y planteamiento de roles de la comunidad en la ejecución del proyecto.",
								"Por favor registre el grado de participación de la comunidad en la construcción de la propuesta y planteamiento de roles de la comunidad en la ejecución del proyecto."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Por favor registre el grado de participación de la comunidad en la construcción de la propuesta y planteamiento de roles de la comunidad en la ejecución del proyecto.",
							"Por favor registre el grado de participación de la comunidad en la construcción de la propuesta y planteamiento de roles de la comunidad en la ejecución del proyecto."));
				}

				if (this.proyectoActual.getSolucionAlternaExtSol() != null) {
					if (this.proyectoActual.getSolucionAlternaExtSol().trim().equals("")) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										"Por favor registre la alternativa de solución del proyecto.",
										"Por favor registre la alternativa de solución del proyecto."));
					}
				} else {
					val = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Por favor registre la alternativa de solución del proyecto.",
									"Por favor registre la alternativa de solución del proyecto."));
				}
				if (convocatoriaActual.getPadre().getId().equals(581L)) {
					if (valoresListasProyectos.isEmpty()) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										"Por favor indique al menos una Área de Atención prioritaria.",
										"Por favor indique al menos una Área de Atención prioritaria."));
					}
					if (esCadenaVacia(proyectoActual.getCampoGenericoDoce())) {
						val = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Por favor indique la justificación sobre asociación de Áreas de Atención Prioritaria.",
								"Por favor indique la justificación sobre asociación de Áreas de Atención Prioritaria."));
					}
				}
			}
		}
		return val;
	}

	protected void mostrarMensaje(FacesMessage msg, UIComponent component) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (component == null) {
			context.addMessage(null, msg);
		} else {
			context.addMessage(component.getClientId(context), msg);
		}

	}

	public void agregarIniciativa() {
		if (validarIniciativas()) {
			listaIniciativas.add(natIni);
			natIni = new NaturalezaIniciativas();
		}
	}

	public void eliminarIniciativa() {
		listaIniciativas.remove(iniciativaAct);
		iniciativaAct = new NaturalezaIniciativas();
	}

	public void agregarApropiacion() {
		if (validarApropiacion()) {
			listaApropiacion.add(aproPry);
			aproPry = new ApropiacionES();
		}
	}

	public void eliminarApropiacion() {
		listaApropiacion.remove(apropAct);
		apropAct = new ApropiacionES();
	}

	public void agregarViabilidad() {
		if (validarViabilidad()) {
			listaViabilidad.add(viabPry);
			viabPry = new ViabiliadES();
		}
	}

	public void eliminarViabilidad() {
		listaViabilidad.remove(viabAct);
		viabAct = new ViabiliadES();
	}

	public boolean validarApropiacion() {
		boolean val = true;

		if (this.aproPry.getDescripcionApro().trim().equals("") || this.aproPry.getDescripcionApro() == null) {
			val = false;
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Por favor ingresese la descripción del trabajo",
					"Por favor ingresese la descripción del trabajo.");

			mostrarMensaje(message, idDescrAprobacion);
		}
		if (this.aproPry.getComunidad().trim().equals("") || this.aproPry.getComunidad() == null) {
			this.aproPry.setComunidad("No registra información");
		}
		if (this.aproPry.getEnlace().trim().equals("") || this.aproPry.getEnlace() == null) {
			this.aproPry.setEnlace("No registra información");
		}

		return val;
	}

	public boolean validarViabilidad() {
		boolean val = true;

		if (this.viabPry.getDescripcion().trim().equals("") || this.viabPry.getDescripcion() == null) {
			val = false;
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Por favor ingresese la descripción del trabajo.",
					"Por favor ingresese la descripción del trabajo.");

			mostrarMensaje(message, idDescrViabiliad);
		}

		if (this.viabPry.getEnlace().trim().equals("") || this.viabPry.getEnlace() == null) {
			val = false;
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Por favor ingresese el link en donde se encuentra el respectivo soporte.",
					"Por favor ingresese el link en donde se encuentra el respectivo soporte.");

			mostrarMensaje(message, idDescrViabiliad);
		}

		return val;
	}

	public String toStringArticulacionIniciativas(ArrayList<NaturalezaIniciativas> ini) {
		String artIni = "";
		for (int i = 0; i < ini.size(); i++) {
			NaturalezaIniciativas natur = ini.get(i);
			artIni += natur.getNaturalezaIniciativa() + "~" + natur.getDescripcion() + "&";
		}
		return artIni;
	}

	public String toStringViabilidad(ArrayList<ViabiliadES> ini) {
		String viabilidad = "";
		for (int i = 0; i < ini.size(); i++) {
			ViabiliadES viab = ini.get(i);
			viabilidad += viab.getDescripcion() + "~" + viab.getEnlace() + "&";
		}
		return viabilidad;
	}

	public String toStringApropiacion(ArrayList<ApropiacionES> ini) {
		String apropiacion = "";
		for (int i = 0; i < ini.size(); i++) {
			ApropiacionES aprob = ini.get(i);
			apropiacion += aprob.getDescripcionApro() + "~" + aprob.getComunidad() + "~" + aprob.getEnlace() + "&";
		}
		return apropiacion;
	}

	public String toStringArticulacionIniciativas_verDos(ArrayList<NaturalezaIniciativas> ini) {
		String artIni = "";
		for (int i = 0; i < ini.size(); i++) {
			NaturalezaIniciativas natur = ini.get(i);
			artIni += natur.getNaturalezaIniciativa() + "><" + natur.getDescripcion() + "<>";
		}
		return artIni;
	}

	public String toStringViabilidad_verDos(ArrayList<ViabiliadES> ini) {
		String viabilidad = "";
		for (int i = 0; i < ini.size(); i++) {
			ViabiliadES viab = ini.get(i);
			viabilidad += viab.getDescripcion() + "><" + viab.getEnlace() + "<>";
		}
		return viabilidad;
	}

	public String toStringApropiacion_verDos(ArrayList<ApropiacionES> ini) {
		String apropiacion = "";
		for (int i = 0; i < ini.size(); i++) {
			ApropiacionES aprob = ini.get(i);
			apropiacion += aprob.getDescripcionApro() + "><" + aprob.getComunidad() + "><" + aprob.getEnlace() + "<>";
		}
		return apropiacion;
	}

	@Override
	public String atras() {
		// ///////MODIFICADO GIOVANNI
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
		boolean bandera = false;
		if (man.getItemProyecto() != null) {
			MenuItem lis[] = man.getMenuItemArray();
			if (lis != null) {
				for (int i = lis.length - 1; i >= 0; i--) {
					if (bandera) {
						if (lis[i].isRendered()) {
							return lis[i].getOutcome();
						}
					}

					if (lis[i].getOutcome().equals("irTrabajoPrevioConES_IS")) {
						bandera = true;
					}

				}
			}
		}
		// ////////////////

		return "irObjetivosResultados";
	}

	@Override
	public String salir() {
		sesion.removeAttribute("proyecto");
		borrarManejadoresInsercionProyecto();
		return "misProyectos";
	}

	public void cortarCadenasInfoEspecificaGen() {

		if (proyectoActual.getLugar() != null) {
			proyectoActual.setLugar(controlTamanoCadena(proyectoActual.getLugar(), 950));
		}

		if (proyectoActual.getNivelSostenibilidad() != null) {
			proyectoActual.setNivelSostenibilidad(controlTamanoCadena(proyectoActual.getNivelSostenibilidad(), 3950));
		}

		if (proyectoActual.getPorqueNivelSostenibilidad() != null) {
			proyectoActual.setPorqueNivelSostenibilidad(
					controlTamanoCadena(proyectoActual.getPorqueNivelSostenibilidad(), 3950));
		}

		if (proyectoActual.getPorqueContinuidadProyecto() != null) {
			proyectoActual.setPorqueContinuidadProyecto(
					controlTamanoCadena(proyectoActual.getPorqueContinuidadProyecto(), 3950));
		}

		if (proyectoActual.getDescripcion() != null) {
			proyectoActual.setDescripcion(controlTamanoCadena(proyectoActual.getDescripcion(), 3950));
		}

		if (proyectoActual.getObservacionesSostenibilidad() != null) {
			proyectoActual.setObservacionesSostenibilidad(
					controlTamanoCadena(proyectoActual.getObservacionesSostenibilidad(), 3950));
		}

		if (proyectoActual.getTransferencia() != null) {
			proyectoActual.setTransferencia(controlTamanoCadena(proyectoActual.getTransferencia(), 3950));
		}

		if (proyectoActual.getConsideracionesEticas() != null) {
			proyectoActual
					.setConsideracionesEticas(controlTamanoCadena(proyectoActual.getConsideracionesEticas(), 3950));
		}

		if (proyectoActual.getMetodologia() != null) {
			proyectoActual.setMetodologia(controlTamanoCadena(proyectoActual.getMetodologia(), 3950));
		}

		if (proyectoActual.getMarcoConceptualExtSol() != null) {
			proyectoActual
					.setMarcoConceptualExtSol(controlTamanoCadena(proyectoActual.getMarcoConceptualExtSol(), 3950));
		}

		if (proyectoActual.getCondicionesEntornoExtSol() != null) {
			proyectoActual.setCondicionesEntornoExtSol(
					controlTamanoCadena(proyectoActual.getCondicionesEntornoExtSol(), 3950));
		}

		if (proyectoActual.getProcesosTransferenciaExtSol() != null) {
			proyectoActual.setProcesosTransferenciaExtSol(
					controlTamanoCadena(proyectoActual.getProcesosTransferenciaExtSol(), 3950));
		}

		if (proyectoActual.getPlanteamientoRolesExtSol() != null) {
			proyectoActual.setPlanteamientoRolesExtSol(
					controlTamanoCadena(proyectoActual.getPlanteamientoRolesExtSol(), 3950));
		}

		if (proyectoActual.getParticipacionComunidadExtSol() != null) {
			proyectoActual.setParticipacionComunidadExtSol(
					controlTamanoCadena(proyectoActual.getParticipacionComunidadExtSol(), 3950));
		}

		if (proyectoActual.getJustificacion() != null) {
			proyectoActual.setJustificacion(controlTamanoCadena(proyectoActual.getJustificacion(), 3950));
		}

		if (proyectoActual.getAtributosSolInnoExtSol() != null) {
			proyectoActual
					.setAtributosSolInnoExtSol(controlTamanoCadena(proyectoActual.getAtributosSolInnoExtSol(), 3950));
		}

		if (proyectoActual.getImpactoEsperado() != null) {
			proyectoActual.setImpactoEsperado(controlTamanoCadena(proyectoActual.getImpactoEsperado(), 3950));
		}
		if (!esInnoSocial2016) {
			String viabilidadString = toStringViabilidad(listaViabilidad);
			proyectoActual.setViabilidad(viabilidadString);

		}

		if (listaLineasProyecto != null && listaLineasProyecto.size() > 0) {
			for (int i = 0; i < listaLineasProyecto.size(); i++) {
				ValoresListasProyecto g = listaLineasProyecto.get(i);
				servicioGeneral.guardarObjeto(g);
			}
		}

		if (listaLineasProyectoBorradas != null && listaLineasProyectoBorradas.size() > 0) {
			for (int i = 0; i < listaLineasProyectoBorradas.size(); i++) {
				ValoresListasProyecto g = listaLineasProyectoBorradas.get(i);
				if (g.getId() != null) {
					List<ValoresListasProyecto> listaLineasProyectoAux;
					listaLineasProyectoAux = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
							"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
									+ " and e.tipo = 'LINEAS_PROYECTO_EXT_SOL'");
					if (listaLineasProyectoAux != null) {
						if (listaLineasProyectoAux.contains(g)) {
							servicioGeneral.eliminarObjeto(g);
						}
					}
				}
			}
		}
		if (!esInnoSocial2016) {
			String artiIniString = toStringArticulacionIniciativas(listaIniciativas);
			proyectoActual.setArticulacionIniciativas(artiIniString);
		} else {
			String artiIniString = toStringArticulacionIniciativas_verDos(listaIniciativas);
			proyectoActual.setArticulacionIniciativas(artiIniString);
		}

		if (areasEst != null && areasEst.size() > 0) {
			for (int i = 0; i < areasEst.size(); i++) {
				ValoresListasProyecto g = areasEst.get(i);
				servicioGeneral.guardarObjeto(g);
			}
		}

		if (areasEstBorradas != null && areasEstBorradas.size() > 0) {
			for (int i = 0; i < areasEstBorradas.size(); i++) {
				ValoresListasProyecto g = areasEstBorradas.get(i);
				if (g.getId() != null) {
					List<ValoresListasProyecto> listaLineasProyectoAux;
					listaLineasProyectoAux = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
							"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
									+ " and e.tipo = 'DOM_AREAS_ESTRATEGICAS_VRI'");
					if (listaLineasProyectoAux != null) {
						if (listaLineasProyectoAux.contains(g)) {
							servicioGeneral.eliminarObjeto(g);
						}
					}
				}
			}
		}

		if (listaComunidadesEtnicasProyecto != null && listaComunidadesEtnicasProyecto.size() > 0) {
			for (int i = 0; i < listaComunidadesEtnicasProyecto.size(); i++) {
				ValoresListasProyecto g = listaComunidadesEtnicasProyecto.get(i);
				servicioGeneral.guardarObjeto(g);
			}
		}

		if (listaComunidadesEtnicasProyectoBorradas != null && listaComunidadesEtnicasProyectoBorradas.size() > 0) {
			for (int i = 0; i < listaComunidadesEtnicasProyectoBorradas.size(); i++) {
				ValoresListasProyecto g = listaComunidadesEtnicasProyectoBorradas.get(i);
				if (g.getId() != null) {
					List<ValoresListasProyecto> listaLineasProyectoAux;
					listaLineasProyectoAux = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
							"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
									+ " and e.tipo = 'COMUNIDAD_ETNICA_EXT_SOL'");
					if (listaLineasProyectoAux != null) {
						if (listaLineasProyectoAux.contains(g)) {
							servicioGeneral.eliminarObjeto(g);
						}
					}
				}
			}
		}

		if (listaindicadoresApropiacion != null && listaindicadoresApropiacion.size() > 0) {
			for (int i = 0; i < listaindicadoresApropiacion.size(); i++) {
				ValoresListasProyecto g = listaindicadoresApropiacion.get(i);
				servicioGeneral.guardarObjeto(g);
			}
		}

		if (listaindicadoresApropiacionBorradas != null && listaindicadoresApropiacionBorradas.size() > 0) {
			for (int i = 0; i < listaindicadoresApropiacionBorradas.size(); i++) {
				ValoresListasProyecto g = listaindicadoresApropiacionBorradas.get(i);
				if (g.getId() != null) {
					List<ValoresListasProyecto> listaLineasProyectoAux;
					listaLineasProyectoAux = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
							"select e from ValoresListasProyecto e where e.proyecto.id = " + proyectoActual.getId()
									+ " and e.tipo = 'INDICADORES_APROPIACION_EXT_SOL'");
					if (listaLineasProyectoAux != null) {
						if (listaLineasProyectoAux.contains(g)) {
							servicioGeneral.eliminarObjeto(g);
						}
					}
				}
			}
		}

	}

	public void establecerValoresVerDos() {
		String artiIniString = toStringArticulacionIniciativas_verDos(listaIniciativas);
		proyectoActual.setArticulacionIniciativas(artiIniString);

		String viabilidadString = toStringViabilidad_verDos(listaViabilidad);
		proyectoActual.setViabilidad(viabilidadString);

		String apropiacionString = toStringApropiacion_verDos(listaApropiacion);
		proyectoActual.setApropiacion(apropiacionString);
	}
	
	public void guardarListas() {
		if (valoresListasProyectos != null && valoresListasProyectos.size() > 0) {
			for (int i = 0; i < valoresListasProyectos.size(); i++) {
				ValoresListasProyecto g = valoresListasProyectos.get(i);
				servicioGeneral.guardarObjeto(g);
			}
		}
		
		if (valoresListasProyectosParametrizado != null && valoresListasProyectosParametrizado.size() > 0) {
			for (int i = 0; i < valoresListasProyectosParametrizado.size(); i++) {
				ValoresListasProyecto g = valoresListasProyectosParametrizado.get(i);
				servicioGeneral.guardarObjeto(g);
			}
		}
		
		if (valoresListasProyectosParametrizado2 != null && valoresListasProyectosParametrizado2.size() > 0) {
			for (int i = 0; i < valoresListasProyectosParametrizado2.size(); i++) {
				ValoresListasProyecto g = valoresListasProyectosParametrizado2.get(i);
				servicioGeneral.guardarObjeto(g);
			}
		}
		if (valoresListasProyectosBorrados != null && valoresListasProyectosBorrados.size() > 0) {
			for (int i = 0; i < valoresListasProyectosBorrados.size(); i++) {
				ValoresListasProyecto g = valoresListasProyectosBorrados.get(i);
				if (g!=null && g.getId() != null) {
					List<ValoresListasProyecto> listaLineasProyectoAux;
					listaLineasProyectoAux = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
							"select e from ValoresListasProyecto e where e.proyecto.id = "
									+ proyectoActual.getId() + " and e.tipo = '"+formularioEspecifica.getDominioListaSeleccionMultiple()+"'");
					if (listaLineasProyectoAux != null) {
						if (listaLineasProyectoAux.contains(g)) {
							servicioGeneral.eliminarObjeto(g);
						}
					}
				}
			}
		}
	}

	@Override
	public String salirGuardar() {
		cortarCadenasInfoEspecificaGen();
		guardarListas();
		servicioProyecto.ingresarProyecto(proyectoActual);
		if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null) {
			enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
		}
		return "misProyectos";
	}

	@Override
	public String siguiente() {
		cortarCadenasInfoEspecificaGen();

		String link = "";
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");
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

					if (lis[i].getOutcome().equals("irTrabajoPrevioConES_IS")) {
						bandera = true;
					}
					if (lis[i].isRendered()) {
						pos++;
					}
				}
			}
		}
		// ////////////////

		if (validarCampos()) {
			if ((proyectoActual.getEstadoProyecto().getId()).equals("I")
					&& (pos - 1) == proyectoActual.getFase().intValue()) {
				proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
			}

			if (proyectoActual.getId() != null) {

				ciudadActual = buscarCiudad(ciudadActual.getId());
				ciudadActual.setDepartamento(departamentoActual);
				Set ciudades = new HashSet();
				proyectoActual.setCiudades(ciudades);
				proyectoActual.adicionarCiudad(ciudadActual);

				if (esInnoSocial2018) {
					establecerValoresVerDos();
				} else {
					if (!esInnoSocial2016) {
						String artiIniString = toStringArticulacionIniciativas(listaIniciativas);
						proyectoActual.setArticulacionIniciativas(artiIniString);

						String viabilidadString = toStringViabilidad(listaViabilidad);
						proyectoActual.setViabilidad(viabilidadString);

						String apropiacionString = toStringApropiacion(listaApropiacion);
						proyectoActual.setApropiacion(apropiacionString);
					} else {
						String artiIniString = toStringArticulacionIniciativas_verDos(listaIniciativas);
						proyectoActual.setArticulacionIniciativas(artiIniString);

						String viabilidadString = toStringViabilidad_verDos(listaViabilidad);
						proyectoActual.setViabilidad(viabilidadString);

						String apropiacionString = toStringApropiacion_verDos(listaApropiacion);
						proyectoActual.setApropiacion(apropiacionString);

						Set<AreaTematica> seAt = new HashSet<AreaTematica>();
						// áreas de la ciencia dos
						for (int i = 0; i < listaAreasTematicas.size(); i++) {

							AreaTematicaVista atv = listaAreasTematicas.get(i);

							DominioDetalle areaSecDefault = (DominioDetalle) listaAreaCiencia.get(0);
							String areaCienciaSecVista = atv.getSubAreaTematica().getIdentificador().getTipo() != null
									? atv.getSubAreaTematica().getIdentificador().getTipo()
									: areaSecDefault.getIdentificador().getTipo();

							DominioDetalle arDos = new DominioDetalle();
							List listaDomDetarDos = new ArrayList<DominioDetalle>();
							listaDomDetarDos = servicioGeneral.obtenerObjetos(
									"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
											+ "SUBTEMAS_EXT_SOL_2016" + "' and dd.identificador.tipo = '"
											+ areaCienciaSecVista + "'");
							arDos = (DominioDetalle) listaDomDetarDos.get(0);

							AreaTematica arTemDos = new AreaTematica();
							arTemDos.setProyecto(proyectoActual);
							arTemDos.setProyectoAreaTematica(arDos);
							arTemDos.setTipo(3l);
							seAt.add(arTemDos);
						}

						try {
							servicioGeneral.eliminar(
									"DELETE HER_PROYECTO_AREA_TEMATICA WHERE ART_TIPO NOT IN (1,2) AND PRY_ID ="
											+ proyectoActual.getId());
						} catch (SQLException e) {
							e.printStackTrace();
							bandera = false;
						}

						proyectoActual.setAreasTematicas(seAt);
					}
				}

				if (listaLineasProyecto != null && listaLineasProyecto.size() > 0) {
					for (int i = 0; i < listaLineasProyecto.size(); i++) {
						ValoresListasProyecto g = listaLineasProyecto.get(i);
						servicioGeneral.guardarObjeto(g);
					}
				}

				if (listaLineasProyectoBorradas != null && listaLineasProyectoBorradas.size() > 0) {
					for (int i = 0; i < listaLineasProyectoBorradas.size(); i++) {
						ValoresListasProyecto g = listaLineasProyectoBorradas.get(i);
						if (g.getId() != null) {
							List<ValoresListasProyecto> listaLineasProyectoAux;
							listaLineasProyectoAux = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
									"select e from ValoresListasProyecto e where e.proyecto.id = "
											+ proyectoActual.getId() + " and e.tipo = 'LINEAS_PROYECTO_EXT_SOL'");
							if (listaLineasProyectoAux != null) {
								if (listaLineasProyectoAux.contains(g)) {
									servicioGeneral.eliminarObjeto(g);
								}
							}
						}
					}
				}

				if (listaindicadoresApropiacion != null && listaindicadoresApropiacion.size() > 0) {
					for (int i = 0; i < listaindicadoresApropiacion.size(); i++) {
						ValoresListasProyecto g = listaindicadoresApropiacion.get(i);
						servicioGeneral.guardarObjeto(g);
					}
				}

				if (listaindicadoresApropiacionBorradas != null && listaindicadoresApropiacionBorradas.size() > 0) {
					for (int i = 0; i < listaindicadoresApropiacionBorradas.size(); i++) {
						ValoresListasProyecto g = listaindicadoresApropiacionBorradas.get(i);
						if (g.getId() != null) {
							List<ValoresListasProyecto> listaLineasProyectoAux;
							listaLineasProyectoAux = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
									"select e from ValoresListasProyecto e where e.proyecto.id = "
											+ proyectoActual.getId()
											+ " and e.tipo = 'INDICADORES_APROPIACION_EXT_SOL'");
							if (listaLineasProyectoAux != null) {
								if (listaLineasProyectoAux.contains(g)) {
									servicioGeneral.eliminarObjeto(g);
								}
							}
						}
					}
				}

				if (listaComunidadesEtnicasProyecto != null && listaComunidadesEtnicasProyecto.size() > 0) {
					for (int i = 0; i < listaComunidadesEtnicasProyecto.size(); i++) {
						ValoresListasProyecto g = listaComunidadesEtnicasProyecto.get(i);
						servicioGeneral.guardarObjeto(g);
					}
				}

				if (listaComunidadesEtnicasProyectoBorradas != null
						&& listaComunidadesEtnicasProyectoBorradas.size() > 0) {
					for (int i = 0; i < listaComunidadesEtnicasProyectoBorradas.size(); i++) {
						ValoresListasProyecto g = listaComunidadesEtnicasProyectoBorradas.get(i);
						if (g.getId() != null) {
							List<ValoresListasProyecto> listaLineasProyectoAux;
							listaLineasProyectoAux = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
									"select e from ValoresListasProyecto e where e.proyecto.id = "
											+ proyectoActual.getId() + " and e.tipo = 'COMUNIDAD_ETNICA_EXT_SOL'");
							if (listaLineasProyectoAux != null) {
								if (listaLineasProyectoAux.contains(g)) {
									servicioGeneral.eliminarObjeto(g);
								}
							}
						}
					}
				}

				if (areasEst != null && areasEst.size() > 0) {
					for (int i = 0; i < areasEst.size(); i++) {
						ValoresListasProyecto g = areasEst.get(i);
						servicioGeneral.guardarObjeto(g);
					}
				}

				if (areasEstBorradas != null && areasEstBorradas.size() > 0) {
					for (int i = 0; i < areasEstBorradas.size(); i++) {
						ValoresListasProyecto g = areasEstBorradas.get(i);
						if (g.getId() != null) {
							List<ValoresListasProyecto> listaLineasProyectoAux;
							listaLineasProyectoAux = servicioGeneral.obtenerObjetos(ValoresListasProyecto.class,
									"select e from ValoresListasProyecto e where e.proyecto.id = "
											+ proyectoActual.getId() + " and e.tipo = 'DOM_AREAS_ESTRATEGICAS_VRI'");
							if (listaLineasProyectoAux != null) {
								if (listaLineasProyectoAux.contains(g)) {
									servicioGeneral.eliminarObjeto(g);
								}
							}
						}
					}
				}

				guardarListas();

				servicioProyecto.ingresarProyecto(proyectoActual);

				if (proyectoActual.getId() != null) {
					// Ing. Wilver Alexander Martínez Martínez -wam²
					// Cambio - Registro de cambios
					Persona personaAux = new Persona();
					personaAux = (Persona) sesion.getAttribute("persona");

					Formulario formulario = new Formulario();
					List listaFormulario = new ArrayList();

					listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='91'");
					formulario = (Formulario) listaFormulario.get(0);

					HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
					historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
					historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
					historicoFormualrioProyecto.setFormulario(formulario);
					historicoFormualrioProyecto.setProyecto(proyectoActual);
					historicoFormualrioProyecto.setFechaCambio(new Date());
					servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
				}

				if (validarSiEsEstudianteLiderProyecto(proyectoActual.getId()) && proyectoActual.getId() != null) {
					enviarCorreoEdicionInfoPryEstLider(proyectoActual.getId());
				}

				sesion.setAttribute("proyecto", proyectoActual);

				sesion.removeAttribute("ManejadorTrabajoPrevioProyectoES_Inno");
				sesion.removeAttribute("manejadorMenuFormularios");
				borrarManejadoresInsercionProyecto();
				return link;
			} else {
				return "";
			}

		} else {
			return "";
		}

	}

	public List<DominioDetalle> getListaNivelSostenibilidad() {
		return listaNivelSostenibilidad;
	}

	public void setListaNivelSostenibilidad(List<DominioDetalle> listaNivelSostenibilidad) {
		this.listaNivelSostenibilidad = listaNivelSostenibilidad;
	}

	public SelectItem[] getNivelSostenibilidadItem() {
		return nivelSostenibilidadItem;
	}

	public void setNivelSostenibilidadItem(SelectItem[] nivelSostenibilidadItem) {
		this.nivelSostenibilidadItem = nivelSostenibilidadItem;
	}

	public List<DominioDetalle> getListaNaturalezaEntidad() {
		return listaNaturalezaEntidad;
	}

	public void setListaNaturalezaEntidad(List<DominioDetalle> listaNaturalezaEntidad) {
		this.listaNaturalezaEntidad = listaNaturalezaEntidad;
	}

	public SelectItem[] getNaturalezaEntidadItem() {
		return naturalezaEntidadItem;
	}

	public void setNaturalezaEntidadItem(SelectItem[] naturalezaEntidadItem) {
		this.naturalezaEntidadItem = naturalezaEntidadItem;
	}

	public NaturalezaIniciativas getNatIni() {
		return natIni;
	}

	public void setNatIni(NaturalezaIniciativas natIni) {
		this.natIni = natIni;
	}

	public ArrayList<NaturalezaIniciativas> getListaIniciativas() {
		return listaIniciativas;
	}

	public void setListaIniciativas(ArrayList<NaturalezaIniciativas> listaIniciativas) {
		this.listaIniciativas = listaIniciativas;
	}

	public NaturalezaIniciativas getIniciativaAct() {
		return iniciativaAct;
	}

	public void setIniciativaAct(NaturalezaIniciativas iniciativaAct) {
		this.iniciativaAct = iniciativaAct;
	}

	public class NaturalezaIniciativas {

		private String naturalezaIniciativa;
		private String descripcion;

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public String getNaturalezaIniciativa() {
			return naturalezaIniciativa;
		}

		public void setNaturalezaIniciativa(String naturalezaIniciativa) {
			this.naturalezaIniciativa = naturalezaIniciativa;
		}

	}

	public UIComponent getPqNivSos() {
		return pqNivSos;
	}

	public void setPqNivSos(UIComponent pqNivSos) {
		this.pqNivSos = pqNivSos;
	}

	public UIComponent getPqContinuidad() {
		return pqContinuidad;
	}

	public void setPqContinuidad(UIComponent pqContinuidad) {
		this.pqContinuidad = pqContinuidad;
	}

	public UIComponent getIdDescrInicia() {
		return idDescrInicia;
	}

	public void setIdDescrInicia(UIComponent idDescrInicia) {
		this.idDescrInicia = idDescrInicia;
	}

	public ApropiacionES getAproPry() {
		return aproPry;
	}

	public void setAproPry(ApropiacionES aproPry) {
		this.aproPry = aproPry;
	}

	public ApropiacionES getApropAct() {
		return apropAct;
	}

	public void setApropAct(ApropiacionES apropAct) {
		this.apropAct = apropAct;
	}

	public ArrayList<ApropiacionES> getListaApropiacion() {
		return listaApropiacion;
	}

	public void setListaApropiacion(ArrayList<ApropiacionES> listaApropiacion) {
		this.listaApropiacion = listaApropiacion;
	}

	public UIComponent getIdDescrAprobacion() {
		return idDescrAprobacion;
	}

	public void setIdDescrAprobacion(UIComponent idDescrAprobacion) {
		this.idDescrAprobacion = idDescrAprobacion;
	}

	public ViabiliadES getViabPry() {
		return viabPry;
	}

	public void setViabPry(ViabiliadES viabPry) {
		this.viabPry = viabPry;
	}

	public ViabiliadES getViabAct() {
		return viabAct;
	}

	public void setViabAct(ViabiliadES viabAct) {
		this.viabAct = viabAct;
	}

	public ArrayList<ViabiliadES> getListaViabilidad() {
		return listaViabilidad;
	}

	public void setListaViabilidad(ArrayList<ViabiliadES> listaViabilidad) {
		this.listaViabilidad = listaViabilidad;
	}

	public UIComponent getIdDescrViabiliad() {
		return idDescrViabiliad;
	}

	public void setIdDescrViabiliad(UIComponent idDescrViabiliad) {
		this.idDescrViabiliad = idDescrViabiliad;
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

	public List getListaZonas() {
		return listaZonas;
	}

	public void setListaZonas(List listaZonas) {
		this.listaZonas = listaZonas;
	}

	public String getProblemaSeleccionado() {
		return problemaSeleccionado;
	}

	public void setProblemaSeleccionado(String problemaSeleccionado) {
		this.problemaSeleccionado = problemaSeleccionado;
	}

	public SelectItem[] getProblemasItem() {
		return problemasItem;
	}

	public void setProblemasItem(SelectItem[] problemasItem) {
		this.problemasItem = problemasItem;
	}

	public List getListaProblemas() {
		return listaProblemas;
	}

	public void setListaProblemas(List listaProblemas) {
		this.listaProblemas = listaProblemas;
	}

	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
	}

	public boolean isEsInnoSocial2015() {
		return esInnoSocial2015;
	}

	public void setEsInnoSocial2015(boolean esInnoSocial2015) {
		this.esInnoSocial2015 = esInnoSocial2015;
	}

	public boolean isEsInnoSocial2016() {
		return esInnoSocial2016;
	}

	public void setEsInnoSocial2016(boolean esInnoSocial2016) {
		this.esInnoSocial2016 = esInnoSocial2016;
	}

	public boolean isEsInnoSocial2016_M3() {
		return esInnoSocial2016_M3;
	}

	public void setEsInnoSocial2016_M3(boolean esInnoSocial2016_M3) {
		this.esInnoSocial2016_M3 = esInnoSocial2016_M3;
	}

	public SelectItem[] getProblemasContinuidadItem() {
		return problemasContinuidadItem;
	}

	public void setProblemasContinuidadItem(SelectItem[] problemasContinuidadItem) {
		this.problemasContinuidadItem = problemasContinuidadItem;
	}

	public SelectItem[] getEnfoquesItem() {
		return enfoquesItem;
	}

	public void setEnfoquesItem(SelectItem[] enfoquesItem) {
		this.enfoquesItem = enfoquesItem;
	}

	public String getAreaCienciaSec() {
		return areaCienciaSec;
	}

	public void setAreaCienciaSec(String areaCienciaSec) {
		this.areaCienciaSec = areaCienciaSec;
	}

	public String getSubAreaCienciaSec() {
		return subAreaCienciaSec;
	}

	public void setSubAreaCienciaSec(String subAreaCienciaSec) {
		this.subAreaCienciaSec = subAreaCienciaSec;
	}

	public SelectItem[] getAreaCienciaItems() {
		return areaCienciaItems;
	}

	public void setAreaCienciaItems(SelectItem[] areaCienciaItems) {
		this.areaCienciaItems = areaCienciaItems;
	}

	public SelectItem[] getSubAreaCienciaSecItems() {
		return subAreaCienciaSecItems;
	}

	public void setSubAreaCienciaSecItems(SelectItem[] subAreaCienciaSecItems) {
		this.subAreaCienciaSecItems = subAreaCienciaSecItems;
	}

	public List getListaAreaCiencia() {
		return listaAreaCiencia;
	}

	public void setListaAreaCiencia(List listaAreaCiencia) {
		this.listaAreaCiencia = listaAreaCiencia;
	}

	public List getListaSubAreaCiencia() {
		return listaSubAreaCiencia;
	}

	public void setListaSubAreaCiencia(List listaSubAreaCiencia) {
		this.listaSubAreaCiencia = listaSubAreaCiencia;
	}

	public List<AreaTematicaVista> getListaAreasTematicas() {
		return listaAreasTematicas;
	}

	public void setListaAreasTematicas(List<AreaTematicaVista> listaAreasTematicas) {
		this.listaAreasTematicas = listaAreasTematicas;
	}

	public AreaTematicaVista getAreaSeleccionada() {
		return areaSeleccionada;
	}

	public void setAreaSeleccionada(AreaTematicaVista areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	/**
	 * @return the esInnoSocial2017
	 */
	public boolean isEsInnoSocial2017() {
		return esInnoSocial2017;
	}

	/**
	 * @param esInnoSocial2017 the esInnoSocial2017 to set
	 */
	public void setEsInnoSocial2017(boolean esInnoSocial2017) {
		this.esInnoSocial2017 = esInnoSocial2017;
	}

	/**
	 * @return the esInnoSocial2017_M2
	 */
	public boolean isEsInnoSocial2017_M2() {
		return esInnoSocial2017_M2;
	}

	/**
	 * @param esInnoSocial2017_M2 the esInnoSocial2017_M2 to set
	 */
	public void setEsInnoSocial2017_M2(boolean esInnoSocial2017_M2) {
		this.esInnoSocial2017_M2 = esInnoSocial2017_M2;
	}

	/**
	 * @return the lineasProyectos2017
	 */
	public SelectItem[] getLineasProyectos2017() {
		return lineasProyectos2017;
	}

	/**
	 * @param lineasProyectos2017 the lineasProyectos2017 to set
	 */
	public void setLineasProyectos2017(SelectItem[] lineasProyectos2017) {
		this.lineasProyectos2017 = lineasProyectos2017;
	}

	/**
	 * @return the lineaProyecto
	 */
	public String getLineaProyecto() {
		return lineaProyecto;
	}

	/**
	 * @param lineaProyecto the lineaProyecto to set
	 */
	public void setLineaProyecto(String lineaProyecto) {
		this.lineaProyecto = lineaProyecto;
	}

	/**
	 * @return the listaLineasProyecto
	 */
	public List<ValoresListasProyecto> getListaLineasProyecto() {
		return listaLineasProyecto;
	}

	/**
	 * @param listaLineasProyecto the listaLineasProyecto to set
	 */
	public void setListaLineasProyecto(List<ValoresListasProyecto> listaLineasProyecto) {
		this.listaLineasProyecto = listaLineasProyecto;
	}

	/**
	 * @return the lineaProyectoSeleccionada
	 */
	public ValoresListasProyecto getLineaProyectoSeleccionada() {
		return lineaProyectoSeleccionada;
	}

	/**
	 * @param lineaProyectoSeleccionada the lineaProyectoSeleccionada to set
	 */
	public void setLineaProyectoSeleccionada(ValoresListasProyecto lineaProyectoSeleccionada) {
		this.lineaProyectoSeleccionada = lineaProyectoSeleccionada;
	}

	/**
	 * @return the listaCentrosPensamiento
	 */
	public SelectItem[] getListaCentrosPensamiento() {
		return listaCentrosPensamiento;
	}

	/**
	 * @param listaCentrosPensamiento the listaCentrosPensamiento to set
	 */
	public void setListaCentrosPensamiento(SelectItem[] listaCentrosPensamiento) {
		this.listaCentrosPensamiento = listaCentrosPensamiento;
	}

	/**
	 * @return the tieneCentrosPensamiento
	 */
	public String getTieneCentrosPensamiento() {
		return tieneCentrosPensamiento;
	}

	/**
	 * @param tieneCentrosPensamiento the tieneCentrosPensamiento to set
	 */
	public void setTieneCentrosPensamiento(String tieneCentrosPensamiento) {
		this.tieneCentrosPensamiento = tieneCentrosPensamiento;
	}

	/**
	 * @return the siTieneCentrosPensamiento
	 */
	public boolean isSiTieneCentrosPensamiento() {
		return siTieneCentrosPensamiento;
	}

	/**
	 * @param siTieneCentrosPensamiento the siTieneCentrosPensamiento to set
	 */
	public void setSiTieneCentrosPensamiento(boolean siTieneCentrosPensamiento) {
		this.siTieneCentrosPensamiento = siTieneCentrosPensamiento;
	}

	/**
	 * @return the listaLineasProyectoBorradas
	 */
	public List<ValoresListasProyecto> getListaLineasProyectoBorradas() {
		return listaLineasProyectoBorradas;
	}

	/**
	 * @param listaLineasProyectoBorradas the listaLineasProyectoBorradas to set
	 */
	public void setListaLineasProyectoBorradas(List<ValoresListasProyecto> listaLineasProyectoBorradas) {
		this.listaLineasProyectoBorradas = listaLineasProyectoBorradas;
	}

	/**
	 * @return the indicadoresApropiacion
	 */
	public String getIndicadoresApropiacion() {
		return indicadoresApropiacion;
	}

	/**
	 * @param indicadoresApropiacion the indicadoresApropiacion to set
	 */
	public void setIndicadoresApropiacion(String indicadoresApropiacion) {
		this.indicadoresApropiacion = indicadoresApropiacion;
	}

	/**
	 * @return the listaindicadoresApropiacion
	 */
	public List<ValoresListasProyecto> getListaindicadoresApropiacion() {
		return listaindicadoresApropiacion;
	}

	/**
	 * @param listaindicadoresApropiacion the listaindicadoresApropiacion to set
	 */
	public void setListaindicadoresApropiacion(List<ValoresListasProyecto> listaindicadoresApropiacion) {
		this.listaindicadoresApropiacion = listaindicadoresApropiacion;
	}

	/**
	 * @return the listaindicadoresApropiacionBorradas
	 */
	public List<ValoresListasProyecto> getListaindicadoresApropiacionBorradas() {
		return listaindicadoresApropiacionBorradas;
	}

	/**
	 * @param listaindicadoresApropiacionBorradas the
	 *                                            listaindicadoresApropiacionBorradas
	 *                                            to set
	 */
	public void setListaindicadoresApropiacionBorradas(
			List<ValoresListasProyecto> listaindicadoresApropiacionBorradas) {
		this.listaindicadoresApropiacionBorradas = listaindicadoresApropiacionBorradas;
	}

	/**
	 * @return the indicadoresApropiacionSeleccionada
	 */
	public ValoresListasProyecto getIndicadoresApropiacionSeleccionada() {
		return indicadoresApropiacionSeleccionada;
	}

	/**
	 * @param indicadoresApropiacionSeleccionada the
	 *                                           indicadoresApropiacionSeleccionada
	 *                                           to set
	 */
	public void setIndicadoresApropiacionSeleccionada(ValoresListasProyecto indicadoresApropiacionSeleccionada) {
		this.indicadoresApropiacionSeleccionada = indicadoresApropiacionSeleccionada;
	}

	/**
	 * @return the botonLineas
	 */
	public UIComponent getBotonLineas() {
		return botonLineas;
	}

	/**
	 * @param botonLineas the botonLineas to set
	 */
	public void setBotonLineas(UIComponent botonLineas) {
		this.botonLineas = botonLineas;
	}

	public boolean isEsInnoSocial2018() {
		return esInnoSocial2018;
	}

	public void setEsInnoSocial2018(boolean esInnoSocial2018) {
		this.esInnoSocial2018 = esInnoSocial2018;
	}

	public SelectItem[] getListaComunidadesEtnicas() {
		return listaComunidadesEtnicas;
	}

	public void setListaComunidadesEtnicas(SelectItem[] listaComunidadesEtnicas) {
		this.listaComunidadesEtnicas = listaComunidadesEtnicas;
	}

	public String getComunidadEtnica() {
		return comunidadEtnica;
	}

	public void setComunidadEtnica(String comunidadEtnica) {
		this.comunidadEtnica = comunidadEtnica;
	}

	public List<ValoresListasProyecto> getListaComunidadesEtnicasProyecto() {
		return listaComunidadesEtnicasProyecto;
	}

	public void setListaComunidadesEtnicasProyecto(List<ValoresListasProyecto> listaComunidadesEtnicasProyecto) {
		this.listaComunidadesEtnicasProyecto = listaComunidadesEtnicasProyecto;
	}

	public List<ValoresListasProyecto> getListaComunidadesEtnicasProyectoBorradas() {
		return listaComunidadesEtnicasProyectoBorradas;
	}

	public void setListaComunidadesEtnicasProyectoBorradas(
			List<ValoresListasProyecto> listaComunidadesEtnicasProyectoBorradas) {
		this.listaComunidadesEtnicasProyectoBorradas = listaComunidadesEtnicasProyectoBorradas;
	}

	public ValoresListasProyecto getComunidadEtnicaSeleccionada() {
		return comunidadEtnicaSeleccionada;
	}

	public void setComunidadEtnicaSeleccionada(ValoresListasProyecto comunidadEtnicaSeleccionada) {
		this.comunidadEtnicaSeleccionada = comunidadEtnicaSeleccionada;
	}

	public UIComponent getIdDescrIniciaArtiSoste() {
		return idDescrIniciaArtiSoste;
	}

	public void setIdDescrIniciaArtiSoste(UIComponent idDescrIniciaArtiSoste) {
		this.idDescrIniciaArtiSoste = idDescrIniciaArtiSoste;
	}

	public SelectItem[] getListaCondicionPoblacion() {
		return listaCondicionPoblacion;
	}

	public void setListaCondicionPoblacion(SelectItem[] listaCondicionPoblacion) {
		this.listaCondicionPoblacion = listaCondicionPoblacion;
	}

	public SelectItem[] getListaGrupoPoblacion() {
		return listaGrupoPoblacion;
	}

	public void setListaGrupoPoblacion(SelectItem[] listaGrupoPoblacion) {
		this.listaGrupoPoblacion = listaGrupoPoblacion;
	}

	public String getCondicionPoblacion() {
		return condicionPoblacion;
	}

	public void setCondicionPoblacion(String condicionPoblacion) {
		this.condicionPoblacion = condicionPoblacion;
	}

	public String getGrupoPoblacion() {
		return grupoPoblacion;
	}

	public void setGrupoPoblacion(String grupoPoblacion) {
		this.grupoPoblacion = grupoPoblacion;
	}

	public String getNumeroPoblacion() {
		return numeroPoblacion;
	}

	public void setNumeroPoblacion(String numeroPoblacion) {
		this.numeroPoblacion = numeroPoblacion;
	}

	public String getCicloVital() {
		return cicloVital;
	}

	public void setCicloVital(String cicloVital) {
		this.cicloVital = cicloVital;
	}

	public SelectItem[] getListaCicloVital() {
		return listaCicloVital;
	}

	public void setListaCicloVital(SelectItem[] listaCicloVital) {
		this.listaCicloVital = listaCicloVital;
	}

	public SelectItem[] getListaAreasEst() {
		return listaAreasEst;
	}

	public void setListaAreasEst(SelectItem[] listaAreasEst) {
		this.listaAreasEst = listaAreasEst;
	}

	public String getAreaEst() {
		return areaEst;
	}

	public void setAreaEst(String areaEst) {
		this.areaEst = areaEst;
	}

	public void adicionarAreaEst() {
		if (areaEst.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La opción seleccionada no es válida.", "La opción seleccionada no es válida."));
			return;
		}
		List<DominioDetalle> listaDomDet = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"select e from DominioDetalle e, Dominio d where e.identificador.tipo = '" + areaEst
						+ "' and e.identificador.id = d.id and d.tipo = 'DOM_AREAS_ESTRATEGICAS_VRI'");
		DominioDetalle g = obtenerDominioDetalleLista(areaEst, listaDomDet);
		ValoresListasProyecto vlp = new ValoresListasProyecto();
		vlp.setProyecto(proyectoActual);
		vlp.setTipo("DOM_AREAS_ESTRATEGICAS_VRI");
		vlp.setValor(g.getIdentificador().getTipo());
		vlp.setDescripcion(g.getDescripcion());
		if (!areasEst.contains(vlp)) {
			areasEst.add(vlp);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"La opción seleccionada ya se encuentra asociada al proyecto",
							"La opción seleccionada ya se encuentra asociada al proyecto"));
		}
	}

	public void eliminarAreaEst() {
		areasEst.remove(areaEstSeleccionada);
		areasEstBorradas.add(areaEstSeleccionada);
		areaEstSeleccionada = new ValoresListasProyecto();
	}

	public List<ValoresListasProyecto> getAreasEst() {
		return areasEst;
	}

	public void setAreasEst(List<ValoresListasProyecto> areasEst) {
		this.areasEst = areasEst;
	}

	public ValoresListasProyecto getAreaEstSeleccionada() {
		return areaEstSeleccionada;
	}

	public void setAreaEstSeleccionada(ValoresListasProyecto areaEstSeleccionada) {
		this.areaEstSeleccionada = areaEstSeleccionada;
	}

	public List<ValoresListasProyecto> getAreasEstBorradas() {
		return areasEstBorradas;
	}

	public void setAreasEstBorradas(List<ValoresListasProyecto> areasEstBorradas) {
		this.areasEstBorradas = areasEstBorradas;
	}

	public String getValorListaProyecto() {
		return valorListaProyecto;
	}

	public void setValorListaProyecto(String valorListaProyecto) {
		this.valorListaProyecto = valorListaProyecto;
	}

	public SelectItem[] getListaSeleccionValoresProyecto() {
		return listaSeleccionValoresProyecto;
	}

	public void setListaSeleccionValoresProyecto(SelectItem[] listaSeleccionValoresProyecto) {
		this.listaSeleccionValoresProyecto = listaSeleccionValoresProyecto;
	}

	public List<ValoresListasProyecto> getValoresListasProyectos() {
		return valoresListasProyectos;
	}

	public void setValoresListasProyectos(List<ValoresListasProyecto> valoresListasProyectos) {
		this.valoresListasProyectos = valoresListasProyectos;
	}

	public List<ValoresListasProyecto> getValoresListasProyectosBorrados() {
		return valoresListasProyectosBorrados;
	}

	public void setValoresListasProyectosBorrados(List<ValoresListasProyecto> valoresListasProyectosBorrados) {
		this.valoresListasProyectosBorrados = valoresListasProyectosBorrados;
	}

	public ValoresListasProyecto getValorListaProyectoSeleccionada() {
		return valorListaProyectoSeleccionada;
	}

	public void setValorListaProyectoSeleccionada(ValoresListasProyecto valorListaProyectoSeleccionada) {
		this.valorListaProyectoSeleccionada = valorListaProyectoSeleccionada;
	}

	public boolean isEsInnoSocial2022() {
		return esInnoSocial2022;
	}

	public void setEsInnoSocial2022(boolean esInnoSocial2022) {
		this.esInnoSocial2022 = esInnoSocial2022;
	}

	public FormularioInformacionEspecifica getFormularioEspecifica() {
		return formularioEspecifica;
	}

	public void setFormularioEspecifica(FormularioInformacionEspecifica formularioEspecifica) {
		this.formularioEspecifica = formularioEspecifica;
	}

	public String getValorListaProyectoParametrizado() {
		return valorListaProyectoParametrizado;
	}

	public void setValorListaProyectoParametrizado(String valorListaProyectoParametrizado) {
		this.valorListaProyectoParametrizado = valorListaProyectoParametrizado;
	}

	public SelectItem[] getListaSeleccionMultipleValoresProyecto() {
		return listaSeleccionMultipleValoresProyecto;
	}

	public void setListaSeleccionMultipleValoresProyecto(SelectItem[] listaSeleccionMultipleValoresProyecto) {
		this.listaSeleccionMultipleValoresProyecto = listaSeleccionMultipleValoresProyecto;
	}

	public List<ValoresListasProyecto> getValoresListasProyectosParametrizado() {
		return valoresListasProyectosParametrizado;
	}

	public ValoresListasProyecto getValorListaProyectoSeleccionadaParametrizada() {
		return valorListaProyectoSeleccionadaParametrizada;
	}

	public void setValorListaProyectoSeleccionadaParametrizada(
			ValoresListasProyecto valorListaProyectoSeleccionadaParametrizada) {
		this.valorListaProyectoSeleccionadaParametrizada = valorListaProyectoSeleccionadaParametrizada;
	}

	public void setValoresListasProyectosParametrizado(List<ValoresListasProyecto> valoresListasProyectosParametrizado) {
		this.valoresListasProyectosParametrizado = valoresListasProyectosParametrizado;
	}

	public SelectItem[] getListaSeleccion1valoresProyecto() {
		return listaSeleccion1valoresProyecto;
	}

	public void setListaSeleccion1valoresProyecto(SelectItem[] listaSeleccion1valoresProyecto) {
		this.listaSeleccion1valoresProyecto = listaSeleccion1valoresProyecto;
	}

	public SelectItem[] getListaSeleccionMultipleValoresProyecto2() {
		return listaSeleccionMultipleValoresProyecto2;
	}

	public void setListaSeleccionMultipleValoresProyecto2(SelectItem[] listaSeleccionMultipleValoresProyecto2) {
		this.listaSeleccionMultipleValoresProyecto2 = listaSeleccionMultipleValoresProyecto2;
	}

	public List<ValoresListasProyecto> getValoresListasProyectosParametrizado2() {
		return valoresListasProyectosParametrizado2;
	}

	public void setValoresListasProyectosParametrizado2(List<ValoresListasProyecto> valoresListasProyectosParametrizado2) {
		this.valoresListasProyectosParametrizado2 = valoresListasProyectosParametrizado2;
	}

	public ValoresListasProyecto getValorListaProyectoSeleccionadaParametrizada2() {
		return valorListaProyectoSeleccionadaParametrizada2;
	}

	public void setValorListaProyectoSeleccionadaParametrizada2(ValoresListasProyecto valorListaProyectoSeleccionadaParametrizada2) {
		this.valorListaProyectoSeleccionadaParametrizada2 = valorListaProyectoSeleccionadaParametrizada2;
	}

	public String getValorListaProyectoParametrizado2() {
		return valorListaProyectoParametrizado2;
	}

	public void setValorListaProyectoParametrizado2(String valorListaProyectoParametrizado2) {
		this.valorListaProyectoParametrizado2 = valorListaProyectoParametrizado2;
	}
}
