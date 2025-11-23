/**
 * @author  Ing Hernán Darío Bernal Parra
 */

package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.menuitem.MenuItem;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Contrapartida;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DependenciaAreaResponsabilidad;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.JornadaDocente;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PlanGlobalDesarrollo;
import co.edu.unal.hermes.modelo.ProgramaNacionalPRN;
import co.edu.unal.hermes.modelo.Registro;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoDuracion;
import co.edu.unal.hermes.modelo.TipoInvestigacion;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorDatosBasicos extends ManejadorProyecto {

    // OBJETOS GRAFICOS
    private SelectItem[] departamentoItem; // DEPARTAMENTOS A MOSTRAR
    private SelectItem[] ciudadItem; // CIUDADES A MOSTRAR
    private SelectItem[] tipoInvestigacionItem; // TIPOS DE INVESTIGACION
    private UIData tablaPalabras; // TABLA DE PALABRAS CLAVE
    private UIData tablaKeyswords; // TABLA DE PALABRAS CLAVE
    private EstadoProyecto estadoProyectoActual;// ESTADO DEL PROYECTO ACTUAL
    private TipoDuracion tipoDuracionActual; // TIPO DE DURACION DEL PROYECTO
					     // (DIAS, SEMANAS, MESES, AÑOS)

    // COMPONENTES CARGADOS DE LA BASE DE DATOS A SER MOSTRADOS EN LA PAGINA JSF
    private Departamento departamentoActual; // DEPARTAMENTO ACTUAL
    private List listaDepartamentos; // LISTA DE DEPARTAMENTOS
    private Ciudad ciudadActual; // CIUDAD SELECCIONADA
    private List listaCiudades; // LISTA DE CIUDADES DEL DEPARTAMENTO
    private List listaTiposInvestigacion; // LISTA DE LINEAS DE INVESTIGACION
    private PalabraClave palabraClave; // PALABRA CLAVE ACTUAL
    private PalabraClave palabraClaveTabla; // PALABRA CLAVE ACTUAL
    private PalabraClave keyWord; // PALABRA CLAVE ACTUAL
    private String mensajeErrorConvocatoria = ""; // MENSAJE DE ERROR PARA
						  // VALIDACION DE LA DURACION
						  // DE LA CONVOCATORIA
    private String mensajeErrorPalabraClave = ""; // MENSAJE DE ERROR PARA
						  // VALIDACION DE LA LISTA DE
						  // PALABRAS CLAVE
    private String mensajeErrorEntidadParticipante = "";
    private boolean editarTitulo;
    private boolean proyectoRefinanciado = false;

    private SelectItem[] listaEmpresasItem;

    private String idRestriccion;
    private SelectItem siNoItem[];
    private boolean esRestriccionBice = false;
    private boolean esEntidadesParticipantes = false;
    private String codigoLabelTipologia;

    private boolean esProgramaNacional = false;
    private boolean esExtensionSolidaria = false;
    
    private List listaIniciativaDe; // LISTA INICIATIVAS DE
    private List listaIniciativa; // LISTA INICIATIVAS    
    private List listaZonas;
    private List listaDirigidoA;
    
    private String iniciativa;
    private String iniciativaDe;
    private boolean esEcosistemaColciencias = false;
    protected String sedeSel;
    private SelectItem[] sedeItem;
    private boolean mostrarFacultades = false;
    private String facultadSel;
    private List<SelectItem> facultadItem;
    protected String dependenciaProyecto;
    private List<SelectItem> dependenciaItem;
    private UIComponent botonAgregarDependencia;
    private DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada;
    protected List<Dependencia> dependenciasUN;

    public ManejadorDatosBasicos() {
	super();

	idManejador = DATOS_BASICOS;
	palabraClave = new PalabraClave();
	keyWord = new PalabraClave();
	estadoProyectoActual = new EstadoProyecto();
	tipoDuracionActual = new TipoDuracion();
	tablaPalabras = new UIData();
	tablaKeyswords = new UIData();

	obtenerListaTiposInvestigacion();
	obtenerListaDepartamentos();
	obtenerListaCiudades();
	// En la siguiente linea obtenemos el valor por defecto del codigo de
	// Tipologia de investigacion
	// codigoLabelTipologia = new
	// String(proyectoActual.getTipoInvestigacion().getId());

	siNoItem = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(new Long(Tipos.SINO));
	sesion.removeAttribute("manejadorDatosBasicos");

	if (proyectoActual.getId() != null) {
	    // SI SE ESTAN EDITANDO LOS DATOS BASICOS SE DEBE REVISAR QUE EL
	    // PROYECTO YA HAYA PASADO POR LA PAGINA DE INVESTIGADORES
	    // ELLO SE SABE POR QUE EN ESE CASO LA FASE ES DOS Y ES POSIBLE
	    // CARGAR LOS DATOS BASICOS DESDE LA BD
	    proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.TODO_POR_ID,false);

	    cargarValoresIniciales();
	    // editarTitulo=true;
	} else {
	    // ASIGNACION DE VALORES POR DEFECTO EN LA CREACION DEL PROYECTO
	    // editarTitulo=false;
	    if (proyectoActual.getProyectoRefinanciado() != null)
		proyectoRefinanciado = true;
	    proyectoActual.cambiarEstadoPersona(EstadoProyecto.INGRESANDO,cargarPersonaActual());
	    // dgbenitezc 20120228: Se fija por omisión PGD 211,
	    // DESARROLLO Y GESTION DE LA INVESTIGACION, LA CREACION ARTISTICA Y
	    // LA EXTENSION.
	    proyectoActual.setPlanGlobalDesarrollo(new PlanGlobalDesarrollo("211"));
	    proyectoActual.setFase(new Integer(0));
	    proyectoActual.adicionarCiudad(ciudadActual);
	    tipoDuracionActual.setId("M");
	    proyectoActual.setTipoDuracion(tipoDuracionActual);
	    // ASIGNACION DEL INVESTIGADOR PRINCIPAL POR DEFECTO
	    InvestigadorProyecto investigadorProyecto = new InvestigadorProyecto();
	    investigadorProyecto.setInvestigador(servicioPersona.obtenerInvestigador(((Persona) sesion.getAttribute("persona")).getId()));
	    investigadorProyecto.setProyecto(proyectoActual);
	    TipoInvestigador ti = new TipoInvestigador();
	    ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), InvestigadorProyecto.PRINCIPAL);
	    investigadorProyecto.setTipo(ti);
	    proyectoActual.adicionarInvestigadorProyecto(investigadorProyecto);
	    TipoInvestigacion tipoInvestigacion = new TipoInvestigacion();
	    // if(proyectoActual.getModalidad().getTipo().getId().compareTo("PN")
	    // == 0){
	    // proyectoActual.setTipoInvestigacion(null);
	    // }else{
	    tipoInvestigacion.setId(((TipoInvestigacion) listaTiposInvestigacion.get(0)).getId());
	    proyectoActual.setTipoInvestigacion(tipoInvestigacion);
	    // }
	    if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0 || proyectoActual.getModalidad().getTipo().getId().compareTo("CV") == 0) {
		proyectoActual.setProgramaNacional(new ProgramaNacionalPRN());
	    }
	}
	if (proyectoActual.getModalidad() instanceof Convocatoria) {
	    System.out.println("es bice");
	    RestriccionConvocatoria r = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();
	    if (r != null) {
		System.out.println(r.getId());
		if (r.getId().equals(RestriccionConvocatoria.BICE)) {

		    esRestriccionBice = true;

		    if (proyectoActual.getRelacionBicentenario() != null && proyectoActual.getRelacionBicentenario().getId() != null) {
			// Tipos si=(Tipos) servicioGeneral.obtenerObjeto(new
			// Tipos(),new Long(Tipos.SI));
			// proyectoActual.setRelacionBicentenario(si);
			idRestriccion = proyectoActual.getRelacionBicentenario().getIdString();
		    } else {
			idRestriccion = new Long(Tipos.NO).toString();
		    }
		}
		System.out.println(idRestriccion);
	    }
	} else {
	    System.out.println("no es bice");
	}

	if (proyectoActual.getModalidad() instanceof Convocatoria) {
	    System.out.println("entidades participantes");
	    RestriccionConvocatoria r = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();
	    if (r != null) {
		System.out.println(r.getId());
		if (r.getId().equals(RestriccionConvocatoria.ENT_PARTICIPANTES)) {

		    esEntidadesParticipantes = true;
		}
	    }
	} else {
	    System.out.println("no es esEntidadesParticipantes");
	}
	
	/*
	 * 
	 * 
	 */
	
	if (proyectoActual.getModalidad() instanceof Convocatoria) {
	    RestriccionConvocatoria r = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();
	    if (r != null) {
		System.out.println(r.getId());
		if (r.getId().equals(RestriccionConvocatoria.ECOS_COLCIENCIAS)) {

			esEcosistemaColciencias = true;
			
		}
	    }
	} 
	
	// Se carga listado de sedes
    List<Sede> sedesUN = servicioGeneral.obtenerObjetos(Sede.class, "select e from Sede e where  e.id<>0 ");
    sedeItem = new SelectItem[sedesUN.size()];
    for (int i = 0; i < sedesUN.size(); i++) {
        Sede sede = (Sede) sedesUN.get(i);
        sedeItem[i] = new SelectItem(sede.getId(), sede.getNombre());
    }
    sedeSel = "";
    cambiarSede();
	/*
	 * 
	 */

	if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0) {
	    this.esProgramaNacional = true;
	}
	
	if (proyectoActual.getModalidad() instanceof Convocatoria) {
	    System.out.println("Extensión Solidaria");
	    RestriccionConvocatoria r = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();
	    if (r != null) {
		System.out.println(r.getId());
		if (r.getId().equals(RestriccionConvocatoria.CONV_EXT_SOL)) {

		    esExtensionSolidaria = true;
		    cargarZonas();
		}
	    }
	} else {
	    System.out.println("no es Extensión Solidaria");
	}

	try {
		codigoLabelTipologia = new String(proyectoActual.getTipoInvestigacion().getId());
	} catch (Exception e) {
		codigoLabelTipologia = "";
	}
	
    
    }

    // DEFINICION DE FUNCIONES BASICAS
    protected void cargarValoresIniciales() {
	System.out.println("manejadorDatosBasicos:cargarValoresIniciales:CARGANDO");
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
    
    public void cambiarSede() {

        if (!esCadenaVacia(sedeSel)) {
            // Si es sede de presencia nacional
            if ((new Sede(sedeSel)).isEsSedePresenciaNacional()) {
                mostrarFacultades = false;
                dependenciasUN = servicioDependencia.obtenerDependenciaXSede(sedeSel);
                dependenciaItem = servicioDependencia.crearSelectItem(dependenciasUN);
                dependenciaProyecto = "";
            } else {
                // Si es una sede con facultad.
                mostrarFacultades = true;
                List<Dependencia> facultadesUN = servicioDependencia.obtenerFacultadesXSede(sedeSel);
                facultadItem = servicioDependencia.crearSelectItem(facultadesUN);
                facultadSel = ((Dependencia) facultadesUN.get(0)).getId().toString();
                cambiarFacultad();
            }
        } else {
            dependenciaItem = new ArrayList<SelectItem>();
            dependenciaProyecto = "";
            mostrarFacultades = false;
        }
    }
    
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
    
    public void adicionarDependencia() {

        boolean existeDep = false;
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

    public String atras() {
	return salir();
    }

    public String salir() {
	sesion.removeAttribute("proyecto");
	borrarManejadoresInsercionProyecto();
	return "misProyectos";
    }
    
    public void cortarCadenasInfoEspecificaGen(){

		if(proyectoActual.getResumen() != null){
			proyectoActual.setResumen(controlTamanoCadena(proyectoActual.getResumen(), 3950));
		}
		if(proyectoActual.getObjetivoGeneral() != null){
			proyectoActual.setObjetivoGeneral(controlTamanoCadena(proyectoActual.getObjetivoGeneral(), 3950));
		}
		if(proyectoActual.getEntidadesParticipantes() != null){
			proyectoActual.setEntidadesParticipantes(controlTamanoCadena(proyectoActual.getEntidadesParticipantes(), 3950));
		}
	}

    public String salirGuardar() {
    	cortarCadenasInfoEspecificaGen();
	try {
	    // AL IR AL SIGUIENTE FORMULARIO SE ACTUALIZA LA CIUDAD DEL PROYECTO
	    ciudadActual = buscarCiudad(ciudadActual.getId());
	    ciudadActual.setDepartamento(departamentoActual);
	    Set ciudades = new HashSet();
	    proyectoActual.setCiudades(ciudades);
	    proyectoActual.adicionarCiudad(ciudadActual);

	    if (esEcosistemaColciencias) {
	    	proyectoActual.setDuracion(1);
	    }
	    // se validan el tiempo de la convocatoria y las palabras clave
	    if (validarTiempoConvocatoria() && validarPalabrasClave()) {
		if (idManejador == (proyectoActual.getFase()).intValue() && (proyectoActual.getEstadoProyecto().getId()).equals("I")) {
		    proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
		}
		servicioProyecto.ingresarProyecto(proyectoActual);
		if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0 || proyectoActual.getModalidad().getTipo().getId().compareTo("CV") == 0) {
		    if (proyectoActual.getProgramaNacional().getId() == null) {
			proyectoActual.getProgramaNacional().setId(proyectoActual.getId());
			Iterator ite = proyectoActual.getGrupos().iterator();
			if (ite != null) {
			    while (ite.hasNext()) {
				Grupo gru = (Grupo) ite.next();
				proyectoActual.getProgramaNacional().setGrupoPrincipal(gru.getId());
			    }
			}
		    }
		    servicioGeneral.guardarObjeto(proyectoActual.getProgramaNacional());

		}

		if (proyectoActual.getId() != null) {
		    // Ing. Wilver Alexander Martínez Martínez -wam²
		    // Cambio - Registro de cambios
		    Persona personaAux = new Persona();
		    personaAux = (Persona) sesion.getAttribute("persona");

		    Formulario formulario = new Formulario();
		    List listaFormulario = new ArrayList();

		    listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='1'");
		    formulario = (Formulario) listaFormulario.get(0);

		    HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
            historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
            historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
            historicoFormualrioProyecto.setFormulario(formulario);
            historicoFormualrioProyecto.setProyecto(proyectoActual);
            historicoFormualrioProyecto.setFechaCambio(new Date());
            servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
		}

		sesion.removeAttribute("proyecto");
		sesion.removeAttribute("manejadorMenuFormularios");
		borrarManejadoresInsercionProyecto();
		return "misProyectos";
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return "";
    }

    public String siguiente() {
    	cortarCadenasInfoEspecificaGen();
	try {

	    if (esRestriccionBice) {
		System.out.println("guardar proyecto con bice" + idRestriccion);
		Tipos rb = (Tipos) servicioGeneral.obtenerObjeto(new Tipos(), new Long(idRestriccion));
		proyectoActual.setRelacionBicentenario(rb);
	    }
	    String mensaje = "";
	    if (proyectoActual.getResumen() == null || proyectoActual.getResumen().equals("")) {
		mensaje = "El resumen no puede ser nulo ";
		//FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El resumen no puede ser nulo ", ""));
		

	    }
	    if (!esEcosistemaColciencias) {
	    	if (proyectoActual.getDuracion() == null || proyectoActual.getDuracion()<=0 ) {
	    		mensaje = "La duración no puede ser nula y debe ser mayor a cero";
	    		//FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El resumen no puede ser nulo ", ""));
	    		}
	    }
	    
	    if (proyectoActual.getNombre() == null || proyectoActual.getNombre().equals("")) {
		mensaje += "El nombre no puede ser nulo ";
		//FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre no puede ser nulo ", ""));
		
	    }

	    if (!mensaje.equals("")) {
		System.out.println("mensaje" + mensaje);
		//sesion.setAttribute("mensaje", mensaje);
		return "";
	    }
	    // AL IR AL SIGUIENTE FORMULARIO SE ACTUALIZA LA CIUDAD DEL PROYECTO
	    ciudadActual = buscarCiudad(ciudadActual.getId());
	    ciudadActual.setDepartamento(departamentoActual);
	    Set ciudades = new HashSet();
	    proyectoActual.setCiudades(ciudades);
	    proyectoActual.adicionarCiudad(ciudadActual);

	    // se validan el tiempo de la convocatoria y las palabras clave
	    if (esEcosistemaColciencias) {
	    	proyectoActual.setDuracion(1);
	    }
	    if (validarTiempoConvocatoria() && validarPalabrasClave()) {
		if (idManejador == (proyectoActual.getFase()).intValue() && (proyectoActual.getEstadoProyecto().getId()).equals("I")) {
		    proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
		}
		servicioProyecto.ingresarProyecto(proyectoActual);

		if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0 || proyectoActual.getModalidad().getTipo().getId().compareTo("CV") == 0) {
		    if (proyectoActual.getProgramaNacional().getId() == null) {
			proyectoActual.getProgramaNacional().setId(proyectoActual.getId());
			Iterator ite = proyectoActual.getGrupos().iterator();
			if (ite != null) {
			    while (ite.hasNext()) {
				Grupo gru = (Grupo) ite.next();
				proyectoActual.getProgramaNacional().setGrupoPrincipal(gru.getId());
			    }
			}
		    }
		    servicioGeneral.guardarObjeto(proyectoActual.getProgramaNacional());
		}

		sesion.setAttribute("proyecto", proyectoActual);
		sesion.removeAttribute("manejadorDatosBasicos");
		sesion.removeAttribute("manejadorInvestigadores");

		// ///////MODIFICADO GIOVANNI
		ManejadorMenuFormularios man = (ManejadorMenuFormularios) sesion.getAttribute("manejadorMenuFormularios");

		boolean bandera = false;

		/*if (man.getItemProyecto() != null) {
		    NavigationMenuItem lis[] = man.getItemProyecto()[0].getNavigationMenuItems();
		    if (lis != null) {
			for (int i = 0; i < lis.length; i++) {
			    if (bandera) {
				if (lis[i].isRendered()) {
				    sesion.removeAttribute("manejadorMenuFormularios");
				    borrarManejadoresInsercionProyecto();
				    return lis[i].getAction();
				}
			    }

			    if (lis[i].getAction().equals("irDatosBasicos")) {
				bandera = true;
			    }

			}
		    }
		}*/
		
		if (man.getMenuItemArray() != null) {
		    MenuItem lis[] = man.getMenuItemArray();
		    if (lis != null) {
			for (int i = 0; i < lis.length; i++) {
			    if (bandera) {
				if (lis[i].isRendered()) {
				    sesion.removeAttribute("manejadorMenuFormularios");
				    borrarManejadoresInsercionProyecto();
				    return lis[i].getOutcome();
				}
			    }

			    if (lis[i].getOutcome().equals("irDatosBasicos")) {
				bandera = true;
			    }

			}
		    }
		}

		if (proyectoActual.getId() != null) {
		    // Ing. Wilver Alexander Martínez Martínez -wam²
		    // Cambio - Registro de cambios
		    Persona personaAux = new Persona();
		    personaAux = (Persona) sesion.getAttribute("persona");

		    Formulario formulario = new Formulario();
		    List listaFormulario = new ArrayList();

		    listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='1'");
		    formulario = (Formulario) listaFormulario.get(0);

		    HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
            historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
            historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
            historicoFormualrioProyecto.setFormulario(formulario);
            historicoFormualrioProyecto.setProyecto(proyectoActual);
            historicoFormualrioProyecto.setFechaCambio(new Date());
            servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
		}

		// ////////////////
		sesion.removeAttribute("manejadorMenuFormularios");

		if (proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.SENA)) {
		    sesion.removeAttribute("manejadorEmpresas");
		    return "irEmpresas";
		}
		return "irInvestigadores";
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return "";
    }

    public void reinit() {
	//palabraClave = new PalabraClave();
	
	System.out.println("lista palabras = " + this.palabraClave.getPalabra());
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
		mensajeErrorPalabraClave = "";
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	palabraClave = new PalabraClave();
    }

    public void insertarKeyWord() {
	System.out.println("inserta " + keyWord.getPalabra());
	boolean existePalabra = keyWord.existePalabraEnSetEN(proyectoActual.getPalabrasClaves());
	if ((!keyWord.getPalabra().equals("")) && (!existePalabra)) {
	    PalabraClave pc = new PalabraClave();
	    pc.setPalabra(keyWord.getPalabra().toUpperCase());
	    pc.setPalabraOriginal(keyWord.getPalabra());
	    try {
		PalabraClave pc1 = servicioGeneral.obtenerPalabraClaveIngles(pc.getPalabra());

		if (pc1 == null) {
		    pc.setIdioma("EN");
		    servicioGeneral.guardarObjeto(pc);
		} else {
		    pc = (PalabraClave) pc1.clone();
		}

		proyectoActual.adicionarPalabraClave(pc);
		keyWord.setPalabra("");
		pc1 = null;
		pc = null;
		mensajeErrorPalabraClave = "";
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    public void eliminarPalabraClave() {
	proyectoActual.borrarPalabraClave(palabraClaveTabla);
	palabraClaveTabla = new PalabraClave();
    }

    public void eliminarKeyWord() {
	proyectoActual.borrarPalabraClave((PalabraClave) tablaKeyswords.getRowData());
    }

    public List obtenerPalabraClavesSugeridas(String nombre) {
	return servicioGeneral.obtenerPalabrasClaveEmpezandoCon(nombre);
    }

    public List obtenerkeywordSugeridas(String nombre) {
	return servicioGeneral.obtenerKeyWordEmpezandoCon(nombre);
    }

    // EVENTOS
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

    public void cambiarTipoInvestigacion(ValueChangeEvent event) {
	// VINCULACION DE LOS DEPARTAMENTOS CONTENIDOS EN LA BASE DE DATOS CON
	// EL COMPONENTE
	// WEB QUE PERMITE LA ESCOGENCIA DE UN DEPARTAMENTO Y LA RECARGA DE LAS
	// CIUDADES

	System.out.println("tipologia codigo");
	codigoLabelTipologia = "";
	int i = 0;
	while (i < listaTiposInvestigacion.size()) {
	    TipoInvestigacion ti = (TipoInvestigacion) listaTiposInvestigacion.get(i);
	    if ((event.getNewValue()).toString().equals(ti.getId())) {
		codigoLabelTipologia = ti.getId();
		break;
	    }
	    i = i + 1;
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

    private Dependencia buscarDependenciaAreaxId(String idDependencia) {
	List listaAuxiliar = new ArrayList();
	Dependencia d = null;
	if (proyectoActual.getListaDependenciasAreaResponsabilidad().size() > 0) {
	    listaAuxiliar.addAll(proyectoActual.getListaDependenciasAreaResponsabilidad());
	    Iterator it = listaAuxiliar.iterator();
	    boolean dependenciaEncontrada = false;
	    while (it.hasNext() && !dependenciaEncontrada) {
		DependenciaAreaResponsabilidad dar = (DependenciaAreaResponsabilidad) it.next();
		d = dar.getDependencia();
		if (d.getId().equals(idDependencia)) {
		    dependenciaEncontrada = true;
		}
	    }
	    listaAuxiliar = null;
	}
	return d;
    }

    private void obtenerListaTiposInvestigacion() {
	listaTiposInvestigacion = new Vector(proyectoActual.getModalidad().getTipo().getTiposInvestigacion());// servicioProyecto.obtenerTiposInvestigacion();
	Collections.sort(listaTiposInvestigacion);
	tipoInvestigacionItem = new SelectItem[listaTiposInvestigacion.size()];
	for (int i = 0; i < listaTiposInvestigacion.size(); i++) {
	    TipoInvestigacion ti = (TipoInvestigacion) listaTiposInvestigacion.get(i);
	    tipoInvestigacionItem[i] = new SelectItem(ti.getId(), ti.getNombre());
	    ti = null;
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
	listaCiudades = servicioGeneral.obtenerUbicacion("Ciudad", "", "departamento", (Departamento) listaDepartamentos.get(0),false);
	ciudadItem = new SelectItem[listaCiudades.size()];
	for (int i = 0; i < listaCiudades.size(); i++) {
	    Ciudad ci = (Ciudad) listaCiudades.get(i);
	    ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
	    ci = null;
	}
	ciudadActual = (Ciudad) listaCiudades.get(0);
    }

    // VALIDADORES
    private boolean validarTiempoConvocatoria() {
	// VALIDA EL TIEMPOD DE DURACION DEL PROYECTO CONTRA LA DURACION DE LA
	// CONVOCATORIA
	// Se pregunta primero si la modalidad es una convocatoria
	Modalidad modalidad = proyectoActual.getModalidad();
	if (modalidad instanceof Convocatoria) {
	    Convocatoria c = (Convocatoria) modalidad;
	    int tiempo = c.getTiempoEjecucionProyecto().intValue();
	    System.out.println("Duracion Proyecto Convocatoria: " + tiempo);
	    if (proyectoActual.getDuracion().intValue() > tiempo) {
		mensajeErrorConvocatoria = "El tiempo de duración del proyecto debe ser de máximo " + tiempo + " meses";
		return false;
	    }
	}
	mensajeErrorConvocatoria = "";
	return true;
    }

    private boolean validarPalabrasClave() {
	// VALIDA QUE LA LISTA DE PALABRAS CLAVE CONTENGA OBJETOS
	if (proyectoActual.getListaPalabras().isEmpty()) {
	    mensajeErrorPalabraClave = "No se encuentran palabras clave asociadas al proyecto";
	    mensajeError("No se encuentran palabras clave asociadas al proyecto");
	  // FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre no puede ser nulo ", ""));
	    return false;
	}
	mensajeErrorPalabraClave = " ";
	return true;
    }

    public void siPoseeRelacionConBicentenario() {
	Tipos si = (Tipos) servicioGeneral.obtenerObjeto(new Tipos(), new Long(Tipos.SI));
	proyectoActual.setRelacionBicentenario(si);
    }

    public void noPoseeRelacionConBicentenario() {
	Tipos no = (Tipos) servicioGeneral.obtenerObjeto(new Tipos(), new Long(Tipos.NO));
	proyectoActual.setRelacionBicentenario(no);

    }
	
    public void cargarZonas(){
	listaZonas = new ArrayList<SelectItem>();
	String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = 24";
	List lista = servicioGeneral.obtenerObjetos(consulta);	    

	for (int i=0; i<lista.size();i++){
	    DominioDetalle dominio = (DominioDetalle) lista.get(i);
	    listaZonas.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
	}
    }

    // METODOS SET Y GET
    public Ciudad getCiudadActual() {
	return ciudadActual;
    }

    public void setCiudadActual(Ciudad ciudadActual) {
	this.ciudadActual = ciudadActual;
    }

    public SelectItem[] getCiudadItem() {
	System.out.println("ciudd");
	return ciudadItem;
    }

    public void setCiudadItem(SelectItem[] ciudadItem) {
	this.ciudadItem = ciudadItem;
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

    public EstadoProyecto getEstadoProyectoActual() {
	return estadoProyectoActual;
    }

    public void setEstadoProyectoActual(EstadoProyecto estadoProyectoActual) {
	this.estadoProyectoActual = estadoProyectoActual;
    }

    public List getListaCiudades() {
	return listaCiudades;
    }

    public void setListaCiudades(List listaCiudades) {
	this.listaCiudades = listaCiudades;
    }

    public List getListaDepartamentos() {
	return listaDepartamentos;
    }

    public void setListaDepartamentos(List listaDepartamentos) {
	this.listaDepartamentos = listaDepartamentos;
    }

    public List getListaTiposInvestigacion() {
	return listaTiposInvestigacion;
    }

    public void setListaTiposInvestigacion(List listaTiposInvestigacion) {
	this.listaTiposInvestigacion = listaTiposInvestigacion;
    }

    public PalabraClave getPalabraClave() {
	return palabraClave;
    }

    public void setPalabraClave(PalabraClave palabraClave) {
	this.palabraClave = palabraClave;
    }

    public TipoDuracion getTipoDuracionActual() {
	return tipoDuracionActual;
    }

    public void setTipoDuracionActual(TipoDuracion tipoDuracionActual) {
	this.tipoDuracionActual = tipoDuracionActual;
    }

    public SelectItem[] getTipoInvestigacionItem() {
	return tipoInvestigacionItem;
    }

    public void setTipoInvestigacionItem(SelectItem[] tipoInvestigacionItem) {
	this.tipoInvestigacionItem = tipoInvestigacionItem;
    }

    public String getMensajeErrorConvocatoria() {
	return mensajeErrorConvocatoria;
    }

    public void setMensajeErrorConvocatoria(String mensajeErrorConvocatoria) {
	this.mensajeErrorConvocatoria = mensajeErrorConvocatoria;
    }

    public String getMensajeErrorPalabraClave() {
	return mensajeErrorPalabraClave;
    }

    public void setMensajeErrorPalabraClave(String mensajeErrorPalabraClave) {
	this.mensajeErrorPalabraClave = mensajeErrorPalabraClave;
    }

    public UIData getTablaPalabras() {
	return tablaPalabras;
    }

    public void setTablaPalabras(UIData tablaPalabras) {
	this.tablaPalabras = tablaPalabras;
    }

    // public boolean isEditarTitulo() {
    // return editarTitulo;
    // }

    // public void setEditarTitulo(boolean editarTitulo) {
    // this.editarTitulo = editarTitulo;
    // }
    public String getTipologiaNombre() {
	String nombreTipologia = "";
	int i = 0;
	while (i < listaTiposInvestigacion.size()) {
	    TipoInvestigacion ti = (TipoInvestigacion) listaTiposInvestigacion.get(i);
	    if (proyectoActual.getTipoInvestigacion().getId().equals(ti.getId())) {
		nombreTipologia = ti.getNombre();
		break;
	    }
	    i = i + 1;
	}
	return nombreTipologia;
    }

    public String getTipologiaCodigo() {
	System.out.println("tipologia codigo");
	String codigoTipologia = "";
	int i = 0;
	while (i < listaTiposInvestigacion.size()) {
	    TipoInvestigacion ti = (TipoInvestigacion) listaTiposInvestigacion.get(i);
	    if (proyectoActual.getTipoInvestigacion().getId().equals(ti.getId())) {
		codigoTipologia = ti.getId();
		break;
	    }
	    i = i + 1;
	}
	System.out.println(codigoTipologia);
	return codigoTipologia;
    }

    public boolean getEsSena() {
	return proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.SENA);
    }

    public String getIdRestriccion() {
	return idRestriccion;
    }

    public void setIdRestriccion(String idRestriccion) {
	this.idRestriccion = idRestriccion;
    }

    public SelectItem[] getSiNoItem() {
	return siNoItem;
    }

    public void setSiNoItem(SelectItem[] siNoItem) {
	this.siNoItem = siNoItem;
    }

    public boolean isEsRestriccionBice() {
	return esRestriccionBice;
    }

    public void setEsRestriccionBice(boolean esRestriccionBice) {
	this.esRestriccionBice = esRestriccionBice;
    }

    public boolean getEditarTitulo() {
	if (proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO) || proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.INGRESANDO)) {

	    if (proyectoActual.getModalidad() instanceof Convocatoria) {
		Convocatoria c = (Convocatoria) proyectoActual.getModalidad();
		return c.getEstadoConvocatoria().getId().equals(EstadoConvocatoria.ACTIVA);
	    }
	    if (proyectoActual.getModalidad() instanceof JornadaDocente) {
		JornadaDocente c = (JornadaDocente) proyectoActual.getModalidad();
		return c.getEstadoJornadaDocente().getId().equals(EstadoConvocatoria.ACTIVA);
	    }
	    if (proyectoActual.getModalidad() instanceof Registro) {
		Registro c = (Registro) proyectoActual.getModalidad();
		return c.getEstadoRegistro().getId().equals(EstadoConvocatoria.ACTIVA);
	    }
	    if (proyectoActual.getModalidad() instanceof Contrapartida) {
		Contrapartida c = (Contrapartida) proyectoActual.getModalidad();
		return c.getEstadoContrapartida().getId().equals(EstadoConvocatoria.ACTIVA);
	    }

	}
	return false;
    }

    public String getCodigoLabelTipologia() {
	return codigoLabelTipologia;
    }

    public void setCodigoLabelTipologia(String codigoLabelTipologia) {
	this.codigoLabelTipologia = codigoLabelTipologia;
    }

    public boolean isProyectoRefinanciado() {
	return proyectoRefinanciado;
    }

    public void setProyectoRefinanciado(boolean proyectoRefinanciado) {
	this.proyectoRefinanciado = proyectoRefinanciado;
    }

    public boolean isEsProgramaNacional() {
	return esProgramaNacional;
    }

    public void setEsProgramaNacional(boolean esProgramaNacional) {
	this.esProgramaNacional = esProgramaNacional;
    }

    public PalabraClave getKeyWord() {
	return keyWord;
    }

    public void setKeyWord(PalabraClave keyWord) {
	this.keyWord = keyWord;
    }

    public UIData getTablaKeyswords() {
	return tablaKeyswords;
    }

    public void setTablaKeyswords(UIData tablaKeyswords) {
	this.tablaKeyswords = tablaKeyswords;
    }

    public boolean isEsEntidadesParticipantes() {
	return esEntidadesParticipantes;
    }

    public void setEsEntidadesParticipantes(boolean esEntidadesParticipantes) {
	this.esEntidadesParticipantes = esEntidadesParticipantes;
    }

    public String getMensajeErrorEntidadParticipante() {
	return mensajeErrorEntidadParticipante;
    }

    public void setMensajeErrorEntidadParticipante(String mensajeErrorEntidadParticipante) {
	this.mensajeErrorEntidadParticipante = mensajeErrorEntidadParticipante;
    }

    public PalabraClave getPalabraClaveTabla() {
        return palabraClaveTabla;
    }

    public void setPalabraClaveTabla(PalabraClave palabraClaveTabla) {
        this.palabraClaveTabla = palabraClaveTabla;
    }

    public boolean isEsExtensionSolidaria() {
        return esExtensionSolidaria;
    }

    public void setEsExtensionSolidaria(boolean esExtensionSolidaria) {
        this.esExtensionSolidaria = esExtensionSolidaria;
    }

	public List getListaIniciativaDe() {
		return listaIniciativaDe;
	}

	public void setListaIniciativaDe(List listaIniciativaDe) {
		this.listaIniciativaDe = listaIniciativaDe;
	}

	public List getListaIniciativa() {
		return listaIniciativa;
	}

	public void setListaIniciativa(List listaIniciativa) {
		this.listaIniciativa = listaIniciativa;
	}

	public List getListaZonas() {
		return listaZonas;
	}

	public void setListaZonas(List listaZonas) {
		this.listaZonas = listaZonas;
	}

	public List getListaDirigidoA() {
		return listaDirigidoA;
	}

	public void setListaDirigidoA(List listaDirigidoA) {
		this.listaDirigidoA = listaDirigidoA;
	}

	public String getIniciativa() {
		return iniciativa;
	}

	public void setIniciativa(String iniciativa) {
		this.iniciativa = iniciativa;
	}

	public String getIniciativaDe() {
		return iniciativaDe;
	}

	public void setIniciativaDe(String iniciativaDe) {
		this.iniciativaDe = iniciativaDe;
	}

	/**
	 * @return the esEcosistemaColciencias
	 */
	public boolean isEsEcosistemaColciencias()
	{
		return esEcosistemaColciencias;
	}

	/**
	 * @param esEcosistemaColciencias the esEcosistemaColciencias to set
	 */
	public void setEsEcosistemaColciencias(boolean esEcosistemaColciencias)
	{
		this.esEcosistemaColciencias = esEcosistemaColciencias;
	}

	/**
	 * @return the sedeSel
	 */
	public String getSedeSel()
	{
		return sedeSel;
	}

	/**
	 * @param sedeSel the sedeSel to set
	 */
	public void setSedeSel(String sedeSel)
	{
		this.sedeSel = sedeSel;
	}

	/**
	 * @return the sedeItem
	 */
	public SelectItem[] getSedeItem()
	{
		return sedeItem;
	}

	/**
	 * @param sedeItem the sedeItem to set
	 */
	public void setSedeItem(SelectItem[] sedeItem)
	{
		this.sedeItem = sedeItem;
	}

	/**
	 * @return the mostrarFacultades
	 */
	public boolean isMostrarFacultades()
	{
		return mostrarFacultades;
	}

	/**
	 * @param mostrarFacultades the mostrarFacultades to set
	 */
	public void setMostrarFacultades(boolean mostrarFacultades)
	{
		this.mostrarFacultades = mostrarFacultades;
	}

	/**
	 * @return the facultadSel
	 */
	public String getFacultadSel()
	{
		return facultadSel;
	}

	/**
	 * @param facultadSel the facultadSel to set
	 */
	public void setFacultadSel(String facultadSel)
	{
		this.facultadSel = facultadSel;
	}

	/**
	 * @return the facultadItem
	 */
	public List<SelectItem> getFacultadItem()
	{
		return facultadItem;
	}

	/**
	 * @param facultadItem the facultadItem to set
	 */
	public void setFacultadItem(List<SelectItem> facultadItem)
	{
		this.facultadItem = facultadItem;
	}

	/**
	 * @return the dependenciaProyecto
	 */
	public String getDependenciaProyecto()
	{
		return dependenciaProyecto;
	}

	/**
	 * @param dependenciaProyecto the dependenciaProyecto to set
	 */
	public void setDependenciaProyecto(String dependenciaProyecto)
	{
		this.dependenciaProyecto = dependenciaProyecto;
	}

	/**
	 * @return the dependenciaItem
	 */
	public List<SelectItem> getDependenciaItem()
	{
		return dependenciaItem;
	}

	/**
	 * @param dependenciaItem the dependenciaItem to set
	 */
	public void setDependenciaItem(List<SelectItem> dependenciaItem)
	{
		this.dependenciaItem = dependenciaItem;
	}

	/**
	 * @return the botonAgregarDependencia
	 */
	public UIComponent getBotonAgregarDependencia()
	{
		return botonAgregarDependencia;
	}

	/**
	 * @param botonAgregarDependencia the botonAgregarDependencia to set
	 */
	public void setBotonAgregarDependencia(UIComponent botonAgregarDependencia)
	{
		this.botonAgregarDependencia = botonAgregarDependencia;
	}

	/**
	 * @return the dependenciaAreaResponsabilidadSeleccionada
	 */
	public DependenciaAreaResponsabilidad getDependenciaAreaResponsabilidadSeleccionada()
	{
		return dependenciaAreaResponsabilidadSeleccionada;
	}

	/**
	 * @param dependenciaAreaResponsabilidadSeleccionada the dependenciaAreaResponsabilidadSeleccionada to set
	 */
	public void setDependenciaAreaResponsabilidadSeleccionada(DependenciaAreaResponsabilidad dependenciaAreaResponsabilidadSeleccionada)
	{
		this.dependenciaAreaResponsabilidadSeleccionada = dependenciaAreaResponsabilidadSeleccionada;
	}

	/**
	 * @return the dependenciasUN
	 */
	public List<Dependencia> getDependenciasUN()
	{
		return dependenciasUN;
	}

	/**
	 * @param dependenciasUN the dependenciasUN to set
	 */
	public void setDependenciasUN(List<Dependencia> dependenciasUN)
	{
		this.dependenciasUN = dependenciasUN;
	}
    
	
    

}
	