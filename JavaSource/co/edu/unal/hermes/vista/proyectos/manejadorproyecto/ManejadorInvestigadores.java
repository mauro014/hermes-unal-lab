package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

/*SE DEBE VALIDAR
 * 1. QUE AL INICIAR SE CARGUE EL PRINCIPAL
 * 2. AL INICIAR NO DEJE PASAR AL SIGUIENTE FORMULARIO SINO SE HAN ACTUALIZADO LOS DATOS DEL PRINCIPAL
 * 3. NO DEJAR PASAR AL SIGUIENTE FORMULARIO SI ALGUNO DE LOS INVESTIGADORES NO TIENE TODOS LOS DATOS
 * 4. EN LA INSERCION DE INVESTIGADORES QUE NO PERMITA ADICIONAR EL INVESTIGADOR SI NO TIENE LOS DATOS COMPLETOS
 * 5. SI ES PRINCIPAL ADICIONALMENTE QUE NO DEJE PASAR SI NO PERTENECE AL GRUPO ASOCIADO AL PROYECTO  
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.event.RowEditEvent;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Empresa;
import co.edu.unal.hermes.modelo.EstadoCivil;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoFormularioProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.utils.VariablesEstaticas;
import co.edu.unal.hermes.vista.proyectos.ManejadorMenuFormularios;

public class ManejadorInvestigadores extends ManejadorProyecto {

    private InvestigadorProyecto copiaValidacionInvestigador;
    private InvestigadorProyecto investigadorProyectoActual;
    private String errorValidacion;
    private boolean edicion = false;
    private boolean investigadorExiste = true;
    private Grupo grupoActual;
    private String facultad;
    private List listaTipoDocumento;
    private SelectItem[] tipoDocumentoItem;
    private SelectItem[] facultadItem;
    private List listaFacultades;
    private SelectItem[] tipoInvestigadorItem;
    private SelectItem[] tipoInvestigadorItemNuevo;

    private DataTable dataTableIntegrantes;

    private InvestigadorProyectoVista invPryVis;

    private boolean mostrarMensajePrincipal;

    private String titulo1;
    private String titulo2;

    private DataTable tablaInvestigadoresProyecto;
    List listaTipoInvestigador;
    private String idEmpresa;
    private SelectItem[] listaEmpresasItem;
    private InvestigadorProyecto investigadorProyectoNuevo;
    public List listaInvestigadoresVista;

    // Investigador Externo
    // OBJETOS GRAFICOS
    private SelectItem[] generoItem = { new SelectItem(VariablesEstaticas.GENERO_FEMENINO, VariablesEstaticas.GENERO_FEMENINO),
	    new SelectItem(VariablesEstaticas.GENERO_MASCULINO, VariablesEstaticas.GENERO_MASCULINO) };
    private SelectItem[] estadoCivilItem;
    private SelectItem[] categoriaInvestigadorItem;
    private SelectItem[] ciudadDomicilioItem;
    private SelectItem[] institucionItem;
    private SelectItem[] departamentoItem;
    private SelectItem[] ciudadItem;
    private SelectItem[] listaTipoInvestigadorEditar;

    private InvestigadorExterno investigadorExterno;
    private EstadoCivil estadoCivil;
    private TipoDocumento tipoDocumento;
    private Ciudad ciudadActual;
    private Institucion institucion;
    private Departamento departamentoActual;
    private CategoriaInvestigador categoriaInvestigador;
    private String insitucionNombre;
    private String documento;

    // LISTAS
    private List listaGenero;
    private List listaEstadoCivil;
    private List listaCategoriaInvestigador;
    private List listaCiudades;
    private List listaInstitucion;
    private List listaDepartamentos;
    private InvestigadorProyectoVista ipv;

    private String etiquetaEditar = "";

    private String nombresInvestigador;
    private String numDocumentoInvestigador;
    private String tipoRolInvestigador;
    private double horasDedicacionInvestigador;
    private String funcionInvestigador;

    private String etiquetaRolInvestigador;

    private boolean esExtensionSolidaria = false;

    public ManejadorInvestigadores() {

	titulo1 = "Proyecto:";
	titulo2 = "Búsqueda de Integrantes del Proyecto";

	mostrarMensajePrincipal = true;

	listaTipoInvestigador = servicioGeneral.obtenerListaObjetos("TipoInvestigador");

	tipoInvestigadorItem = new SelectItem[listaTipoInvestigador.size()];
	int j = 0;
	for (Iterator itTInv = listaTipoInvestigador.iterator(); itTInv.hasNext(); j++) {
	    TipoInvestigador ti = (TipoInvestigador) itTInv.next();
	    tipoInvestigadorItem[j] = new SelectItem(ti.getId(), ti.getNombre());
	}

	idManejador = INVESTIGADORES;
	tablaInvestigadoresProyecto = new DataTable();
	cargarTiposDocumento();
	listaFacultades = servicioGeneral.obtenerFacultades();
	facultadItem = new SelectItem[listaFacultades.size()];
	for (int i = 0; i < listaFacultades.size(); i++) {
	    Dependencia in = (Dependencia) listaFacultades.get(i);
	    String nombre = in.getNombre();
	    if (in.getNombre().length() > 50) {
		nombre = in.getNombre().substring(0, 50) + "...";
	    }
	    facultadItem[i] = new SelectItem(in.getId(), nombre);
	}
	facultad = ((Dependencia) listaFacultades.get(0)).getId();
	proyectoActual = servicioProyecto.obtenerProyecto(proyectoActual.getId(), ProyectoDAOHibernate.EMPRESASEINVESTIGADORES);

	// ///////////////// investigadores proyecto
	listaInvestigadoresVista = proyectoActual.getObtenerListaInvestigadoresVista();

	// /////////
	if (proyectoActual.getFase().intValue() == INVESTIGADORES) {
	    // LA EDICION INICIA EN VERDADERO, YA QUE EN EL PRIMER PASO SIEMRPE
	    // SE PIDEN LOS DATOS DEL INVESTIGADOR PRINCIPAL
	    investigadorProyectoActual = obtenerInvestigadorPrincipal();
	    copiaValidacionInvestigador = new InvestigadorProyecto();
	    copiaValidacionInvestigador.copiarDatos(investigadorProyectoActual);
	    edicion = true;
	    investigadorProyectoNuevo = new InvestigadorProyecto();
	    TipoInvestigador tc = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), TipoInvestigador.coinvestigador);
	    investigadorProyectoNuevo.setInvestigador(new Investigador());
	    TipoDocumento tDocumento = new TipoDocumento();
	    tDocumento = (TipoDocumento) servicioGeneral.obtenerObjeto(new TipoDocumento(), TipoDocumento.CEDULA);

	    investigadorProyectoNuevo.getInvestigador().setId(new IdPersona());

	    investigadorProyectoNuevo.setTipo(tc);
	    obtenerInvestigadorPrincipalVista().setEdicion(true);
	} else {
	    // LA EDICION INICIA EN FALSO CUANDO YA SE HA PASADO POR ESTE
	    // FORMULARIO
	    investigadorProyectoActual = new InvestigadorProyecto();
	    TipoInvestigador ti = new TipoInvestigador();
	    ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), "C");
	    investigadorProyectoActual.setTipo(ti);
	    investigadorProyectoActual.setInvestigador(new Investigador());
	    investigadorProyectoActual.getInvestigador().setId(new IdPersona());
	    if (proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.SENA)) {
		investigadorProyectoActual.setEmpresa(new Empresa());
	    } else {
		investigadorProyectoActual.setEmpresa(null);
	    }

	    // nuevo
	    investigadorProyectoNuevo = new InvestigadorProyecto();
	    ti = new TipoInvestigador();
	    ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), "C");
	    investigadorProyectoNuevo.setTipo(ti);
	    investigadorProyectoNuevo.setInvestigador(new Investigador());
	    investigadorProyectoNuevo.getInvestigador().setId(new IdPersona());
	    if (proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.SENA)) {
		investigadorProyectoNuevo.setEmpresa(new Empresa());
	    } else {
		investigadorProyectoNuevo.setEmpresa(null);
	    }

	    // /
	    edicion = false;
	}
	listaEmpresasItem = new SelectItem[proyectoActual.getListaEmpresas().size()];
	int i = 0;
	for (Iterator itEmpresas = proyectoActual.getListaEmpresas().iterator(); itEmpresas.hasNext();) {
	    Empresa empresa = (Empresa) itEmpresas.next();
	    listaEmpresasItem[i] = new SelectItem(empresa.getId().toString(), empresa.getNombre());
	    i++;
	}

	listaTipoInvestigadorEditar = new SelectItem[2];

	if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0) {
	    titulo1 = "Programa:";
	    titulo2 = "Búsqueda de Integrantes del Programa";
	    mostrarMensajePrincipal = false;
	} else {
	    titulo1 = "Proyecto:";
	    titulo2 = "Búsqueda de Integrantes del Proyecto";
	    mostrarMensajePrincipal = true;
	}

	if (proyectoActual.getModalidad() instanceof Convocatoria) {
	    System.out.println("Extensión Solidaria");
	    RestriccionConvocatoria r = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();
	    if (r != null) {
		System.out.println(r.getId());
		if (r.getId().equals(RestriccionConvocatoria.CONV_EXT_SOL)) {

		    esExtensionSolidaria = true;

		}
	    }
	} else {
	    System.out.println("no es Extensión Solidaria");
	}

	invPryVis = new InvestigadorProyectoVista();

	// investigador externo

	listaGenero = new ArrayList();
	listaEstadoCivil = new ArrayList();
	listaCategoriaInvestigador = new ArrayList();
	listaTipoDocumento = new ArrayList();
	listaDepartamentos = new ArrayList();
	listaInstitucion = new ArrayList();
	listaCiudades = new ArrayList();
	investigadorExterno = new InvestigadorExterno();
	documento = new String();

	// SE CARGAN LOS ESTADOS CIVILES
	listaEstadoCivil = servicioGeneral.obtenerListaObjetos("EstadoCivil");
	estadoCivilItem = new SelectItem[listaEstadoCivil.size()];
	for (int k = 0; k < listaEstadoCivil.size(); k++) {
	    EstadoCivil ec = (EstadoCivil) listaEstadoCivil.get(k);
	    estadoCivilItem[k] = new SelectItem(ec.getId(), ec.getNombre());
	}
	estadoCivil = (EstadoCivil) listaEstadoCivil.get(0);

	// SE CARGA LA CATEGORIA DEL INVESTIGADOR
	listaCategoriaInvestigador = servicioGeneral.obtenerListaObjetos("CategoriaInvestigador");
	categoriaInvestigadorItem = new SelectItem[listaCategoriaInvestigador.size()];
	for (int k = 0; k < listaCategoriaInvestigador.size(); k++) {
	    CategoriaInvestigador cat = (CategoriaInvestigador) listaCategoriaInvestigador.get(k);
	    categoriaInvestigadorItem[k] = new SelectItem(cat.getId(), cat.getNombre());

	}
	categoriaInvestigador = (CategoriaInvestigador) listaCategoriaInvestigador.get(0);
	tipoDocumento = new TipoDocumento();

	// SE CARGAN LOS TIPOS DE DOCUMENTO

	// VINCULACION DE LOS DEPARTAMENTOS CONTENIDOS EN LA BASE DE DATOS
	// CON EL COMPONENTE WEB QUE PERMITE LA ESCOGENCIA DE UN DEPARTAMENTO
	obtenerListaDepartamentos();
	obtenerListaCiudades();

    }

    // FUNCION ENCARGADA DE TRAER EL INVESTIGADOR SEGUN NUMERO DE CC
    public void adicionarInvestigador() {
	Investigador nuevoInvestigador = new Investigador();
	if (idEmpresa != null) {
	    investigadorProyectoNuevo.setEmpresa((Empresa) servicioGeneral.obtenerObjeto(new Empresa(), new Long((String) idEmpresa)));

	}
	if (investigadorProyectoNuevo.getTipo().getId().equals(TipoInvestigador.Principal)) {
	    if (yaHayPrincipal()) {
		errorValidacion = "No puede haber mas de 1 principal";
		FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No puede haber mas de 1 director0", ""));
		return;
	    }
	}
	// //////////////////////////////////////
	if (investigadorProyectoNuevo.getTipo().getId().equals(TipoInvestigador.CODIRECTOR)) {
	    if (yaHayCodirector()) {
		errorValidacion = "No puede haber mas de 1 codirector";
		FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No puede haber mas de 1 codirector", ""));
		return;
	    }
	}
	// ////////////////////////////////////
	try {
	    // SE VERIFICA SI EL INVESTIGADOR ES ESTUDIANTE O DOCENTE Y SE CREA
	    // UN NUEVO INVESTIGADOR

	    boolean doc;
	    Long documento = Long.parseLong(investigadorProyectoNuevo.getInvestigador().getId().getDocumento());
	    if (documento > 0) {
		doc = true;
	    } else {
		doc = false;
	    }

	    if (doc) {

		if (investigadorProyectoNuevo.getTipo().getId().equals("P")
			|| (investigadorProyectoNuevo.getTipo().getId().equals("C"))
			|| (investigadorProyectoNuevo.getTipo().getId().equals("CES"))
			|| (investigadorProyectoNuevo.getTipo().getId().equals("PRES"))
			|| (investigadorProyectoNuevo.getTipo().getId().equals("PEES"))

			&& (!investigadorProyectoNuevo.getTipo().getId().equals("A") && !investigadorProyectoNuevo.getTipo().getId().equals("AL")
				&& !investigadorProyectoNuevo.getTipo().getId().equals("EGES") && !investigadorProyectoNuevo.getTipo().getId().equals("ESPR") && !investigadorProyectoNuevo.getTipo()
				.getId().equals("ESPO"))) {
		    // SI EL INVESTIGADOR ES INTERNO
		    nuevoInvestigador = servicioPersona.obtenerInvestigador(investigadorProyectoNuevo.getInvestigador().getId());
		} else {
		    // SI EL INVESTIGADOR ES UN ESTUDIANTE
		    if (investigadorProyectoNuevo.getTipo().getId().equals("A") || investigadorProyectoNuevo.getTipo().getId().equals("AL")
			    || investigadorProyectoNuevo.getTipo().getId().equals("EGES") || investigadorProyectoNuevo.getTipo().getId().equals("ESPR")
			    || investigadorProyectoNuevo.getTipo().getId().equals("ESPO")) {
			nuevoInvestigador = servicioPersona.obtenerInvestigador(investigadorProyectoNuevo.getInvestigador().getId());
			if (nuevoInvestigador == null) {

			    Estudiante e = servicioPersona.obtenerEstudiante(investigadorProyectoNuevo.getInvestigador().getId());
			    // Se busca como estudiante
			    if (e != null) {
				nuevoInvestigador = servicioPersona.obtenerInvestigador(investigadorProyectoNuevo.getInvestigador().getId());
				if (nuevoInvestigador == null) {
				    InvestigadorInterno nvoinv = e.convertirAInvestigador();
				   try {
					   nvoinv.setDependencia(e.getDependencia());
					} catch (Exception e2) {
						
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
			    }
			}

		    }
		}
		// SI EL NUEVO INVESTIGADOR NO ES NULO Y NO SE ENCUENTRA YA
		// INGRESADO AL PROYECTO
		if (nuevoInvestigador != null && nuevoInvestigador.getId() != null && nuevoInvestigador.getId().getTipoDocumento() != null && !buscarInvestigador(nuevoInvestigador.getId())) {
		    investigadorExiste = true;
		    investigadorProyectoNuevo.setInvestigador(nuevoInvestigador);
		    // VALIDAR EL OBJETO INVESTIGADORPROYECTOACTUAL
		    // SI LA CONVOCATORIA ES 2017 VALIDAR QUE EL INVESTIGADOR
		    // PRINCIPAL PERTENEZCA A LA CONVOCATORIA
		    if (investigadorProyectoNuevo.getTipo().getId().equals("P")) {
			if (validaDuplicidadPrincipal(investigadorProyectoNuevo) && investigadorPerteneceGrupo(investigadorProyectoNuevo.getInvestigador())) {
			    if (validarInvestigadorIndividual(investigadorProyectoNuevo)) {
				investigadorProyectoNuevo.setInvestigador(nuevoInvestigador);
				investigadorProyectoNuevo.setProyecto(proyectoActual);
				proyectoActual.getInvestigadoresProyecto().add(investigadorProyectoNuevo);
				listaInvestigadoresVista = proyectoActual.getObtenerListaInvestigadoresVista();
				investigadorProyectoNuevo = new InvestigadorProyecto();
				copiaValidacionInvestigador = new InvestigadorProyecto();
				TipoInvestigador ti = new TipoInvestigador();
				ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), "C");
				investigadorProyectoNuevo.setTipo(ti);
				investigadorProyectoNuevo.setInvestigador(new Investigador());
				investigadorProyectoNuevo.getInvestigador().setId(new IdPersona());
				errorValidacion = "";
			    }
			}
		    } else {
			if (validarInvestigadorIndividual(investigadorProyectoNuevo)) {
			    investigadorProyectoNuevo.setInvestigador(nuevoInvestigador);
			    investigadorProyectoNuevo.setProyecto(proyectoActual);
			    proyectoActual.getInvestigadoresProyecto().add(investigadorProyectoNuevo);
			    listaInvestigadoresVista = proyectoActual.getObtenerListaInvestigadoresVista();
			    investigadorProyectoNuevo = new InvestigadorProyecto();
			    copiaValidacionInvestigador = new InvestigadorProyecto();
			    TipoInvestigador ti = new TipoInvestigador();
			    ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), "C");
			    investigadorProyectoNuevo.setTipo(ti);
			    investigadorProyectoNuevo.setInvestigador(new Investigador());
			    investigadorProyectoNuevo.getInvestigador().setId(new IdPersona());
			    errorValidacion = "";
			}
		    }
		} else {

		    if (nuevoInvestigador == null || nuevoInvestigador.getId() == null || nuevoInvestigador.getId().getTipoDocumento() == null) {
			// EN ESTE CASO EL INVESTIGADOR NO HA SIDO ENCONTRADO
			// ENTONCES POSIBLEMENTE ES EXTERNO --(alvaro) entra si
			// se
			// encuentra
			// CREAR LA ADICION DE INVESTIGADORES EXTERNOS
			if (validarInvestigadorIndividual(investigadorProyectoNuevo)) {
			    errorValidacion = "No ha sido encontrado el investigador";
			    if (investigadorProyectoNuevo.getTipo().getId().equals("P")) {
				errorValidacion += " , si va a adicionar como externo no se permite el tipo 'Principal'";
				FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "No ha sido encontrado el investigado, si va a adicionar como externo no se permite el tipo 'Principal'", ""));
				return;
			    }
			    if (investigadorProyectoNuevo.getTipo().getId().equals("A") || investigadorProyectoNuevo.getTipo().getId().equals("AL")
				    || investigadorProyectoNuevo.getTipo().getId().equals("ESPR") || investigadorProyectoNuevo.getTipo().getId().equals("ESPO")) {
				errorValidacion += " , si va a adicionar como externo no se permite el tipo 'Estudiante'";
				FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "La fuente se agrego satisfactoriamente, si va a adicionar como externo no se permite el tipo 'Estudiante'", ""));
				return;
			    }
			    investigadorExiste = false;
			}
		    }
		}
	    } else {
		errorValidacion = "Por favor ingrese un número de documento";
		FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No puede haber mas de 1 principal", ""));
		return;
	    }

	} catch (Exception e) {
	    e.printStackTrace();

	    errorValidacion = "Ha ocurrido un error durante la adicion del investigador, vuelva a intentar";
	}
    }

    public void cambiarDepartamento() {

	// VINCULACION DE LOS DEPARTAMENTOS CONTENIDOS EN LA BASE DE DATOS CON
	// EL COMPONENTE
	// WEB QUE PERMITE LA ESCOGENCIA DE UN DEPARTAMENTO Y LA RECARGA DE LAS
	// CIUDADES
	// CONTEXTOS DE LA APLICACION
	departamentoActual.setId((departamentoActual.getId()));

	listaCiudades = servicioGeneral.obtenerUbicacion("Ciudad", "", "departamento", departamentoActual,false);
	ciudadItem = new SelectItem[listaCiudades.size()];
	for (int i = 0; i < listaCiudades.size(); i++) {
	    Ciudad ci = (Ciudad) listaCiudades.get(i);
	    ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
	    ci = null;
	}
	ciudadActual = (Ciudad) listaCiudades.get(0);
    }

    public void insertarInvestigadorExterno() {
	System.out.println("Insertando Investigador Externo");
	// FUNCION ENCARGADA DE LA ASOCIACION DE LA INSERCION DEL PROYECTO
	// ACTUAL EN LA BASE
	// DE DATOS
	departamentoActual.setNombre("");
	ciudadActual.setNombre("");
	try {
	    System.out.println("Insertando Investigador Externo");
	    IdPersona id = new IdPersona();
	    id.setTipoDocumento(tipoDocumento.getId());
	    id.setDocumento(documento);
	    investigadorExterno.setId(id);

	    ciudadActual.setDepartamento(departamentoActual);
	    investigadorExterno.setCiudadDomicilio(ciudadActual);
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
		throw new Exception("institucion requerida");
	    }
	    investigadorExterno.setEstadoCivil(estadoCivil);
	    investigadorExterno.setCategoriaInvestigador(categoriaInvestigador);
	    investigadorExterno.setInterno(Investigador.EXTERNO);
	    investigadorExterno.setEvaluador(Investigador.NO_EVALUADOR);
	    InvestigadorExterno persona = servicioPersona.obtenerInvestigadorExterno(id);
	    if (persona == null) {
		try {
		    servicioPersona.guardarInvestigador(investigadorExterno);

		    TipoInvestigador ti = new TipoInvestigador();
		    ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), "C");
		    investigadorExterno = new InvestigadorExterno();
		    investigadorExiste = true;
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El investigador externo se ha creado correctamente", ""));
		} catch (Exception h) {
		    System.out.println("no se pudo hacer: ((IServicioPersona) appCtx.getBean(servicioPersona)).guardarInvestigadorExterno(investigadorExterno)");
		    h.printStackTrace();
		}
	    } else {
		investigadorExiste = true;
	    }
	    // AL TERMINAR ESTE TRY DEBO HACER QUE TODOS LOS OBJETOS CREADOS
	    // SEAN
	    // NULOS Y LIMPIAR LA FORMA PRINCIPAL
	    // DE INSERCION DE CONVOCATORIA
	} catch (Exception e) {
	    System.out.println("Investigador Externo No Insertado" + e.getMessage());
	}

	// return Navegacion.EXITO;
    }

    public List obtenerListaInstituciones(String nombre) {
	List listaA = servicioGeneral.buscarListaDeInstitucionesPorNombre(nombre);
	System.out.println("encontrada " + listaA.size() + " con " + nombre);
	List listaAux = new Vector();
	for (Iterator i = listaA.iterator(); i.hasNext();) {
	    Institucion ins = (Institucion) i.next();
	    listaAux.add(ins.getNombre());
	}
	return listaAux;
    }

    public void editarInvestigadorVista() {

	editarInvestigador();
    }

    public void actualizarInvestigadorVista() {
	actualizarInvestigador();
    }

    public void detActuEdiInv() {
	InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) tablaInvestigadoresProyecto.getRowData();
	if (ipv.edicion) {
	    actualizarInvestigador();
	    etiquetaEditar = "Actualizar Investigador";
	} else {
	    editarInvestigador();
	    etiquetaEditar = "Editar";
	}
    }

    public void eliminarInvestigador() {
	try {

	    proyectoActual.getInvestigadoresProyecto().remove(ipv.getIp());
	    listaInvestigadoresVista = proyectoActual.getObtenerListaInvestigadoresVista();
	    ipv = new InvestigadorProyectoVista();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }


    public void editarInvestigador() {

	investigadorProyectoActual = ipv.getIp();
	nombresInvestigador = investigadorProyectoActual.getInvestigador().getNombre1() + " " + investigadorProyectoActual.getInvestigador().getNombre2() + " "
		+ investigadorProyectoActual.getInvestigador().getApellido1() + " " + investigadorProyectoActual.getInvestigador().getApellido2();
	numDocumentoInvestigador = investigadorProyectoActual.getInvestigador().getId().getDocumento();

	listaTipoInvestigadorEditar = getListaTipoInvestigadorEditarFuncion();
	tipoRolInvestigador = investigadorProyectoActual.getTipo().getId();
	horasDedicacionInvestigador = investigadorProyectoActual.getDedicacionHorasSemana();
	funcionInvestigador = investigadorProyectoActual.getFuncion();

	ipv.setEdicion(true);
	copiaValidacionInvestigador = new InvestigadorProyecto();
	copiaValidacionInvestigador.copiarDatos(investigadorProyectoActual);
	edicion = true;

    }

    public void actualizarInvestigador() {


	investigadorProyectoActual = ipv.getIp();
	investigadorProyectoActual.setDedicacionHorasSemana(horasDedicacionInvestigador);
	investigadorProyectoActual.setFuncion(funcionInvestigador);
	TipoInvestigador tinAct = new TipoInvestigador();
	tinAct = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), tipoRolInvestigador);
	investigadorProyectoActual.setTipo(tinAct);

	if ((copiaValidacionInvestigador != null && copiaValidacionInvestigador.getTipo() != null && (copiaValidacionInvestigador.getTipo().getId().equals("C") || copiaValidacionInvestigador
		.getTipo().getId().equals("P")))
		&& investigadorProyectoActual.getTipo().getId().equals("A") && edicion) {
	    FacesContext.getCurrentInstance().addMessage("msgsAct", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No es posible cambiar a tipo 'Estudiante'", ""));
	    investigadorProyectoActual.setTipo(copiaValidacionInvestigador.getTipo());
	    return;
	}
	if ((investigadorProyectoActual.getTipo().getId().equals("C") || investigadorProyectoActual.getTipo().getId().equals("P")) && copiaValidacionInvestigador != null
		&& copiaValidacionInvestigador.getTipo() != null && copiaValidacionInvestigador.getTipo().getId().equals("A") && edicion) {
	    FacesContext.getCurrentInstance().addMessage("msgsAct", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No es posible cambiar el tipo del investigador", ""));
	    investigadorProyectoActual.setTipo(copiaValidacionInvestigador.getTipo());
	    return;
	}

	// /ES
	if ((copiaValidacionInvestigador != null && copiaValidacionInvestigador.getTipo() != null && (copiaValidacionInvestigador.getTipo().getId().equals("C")
		|| copiaValidacionInvestigador.getTipo().getId().equals("P") || copiaValidacionInvestigador.getTipo().getId().equals("CES")
		|| copiaValidacionInvestigador.getTipo().getId().equals("PRES") || copiaValidacionInvestigador.getTipo().getId().equals("PEES")
		|| copiaValidacionInvestigador.getTipo().getId().equals("EGES") || copiaValidacionInvestigador.getTipo().getId().equals("EXES")))
		&& (investigadorProyectoActual.getTipo().getId().equals("ESPR") || investigadorProyectoActual.getTipo().getId().equals("ESPO")) && edicion) {
	    FacesContext.getCurrentInstance().addMessage("msgsAct", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No es posible cambiar a tipo 'Estudiante'", ""));
	    investigadorProyectoActual.setTipo(copiaValidacionInvestigador.getTipo());
	    return;
	}

	if ((investigadorProyectoActual.getTipo().getId().equals("C") || investigadorProyectoActual.getTipo().getId().equals("P") || investigadorProyectoActual.getTipo().getId().equals("CES")
		|| investigadorProyectoActual.getTipo().getId().equals("PRES") || investigadorProyectoActual.getTipo().getId().equals("PEES")
		|| investigadorProyectoActual.getTipo().getId().equals("EGES") || investigadorProyectoActual.getTipo().getId().equals("EXES"))
		&& copiaValidacionInvestigador != null
		&& copiaValidacionInvestigador.getTipo() != null
		&& (copiaValidacionInvestigador.getTipo().getId().equals("A") || copiaValidacionInvestigador.getTipo().getId().equals("ESPO") || copiaValidacionInvestigador.getTipo().getId()
			.equals("ESPR")) && edicion) {
	    FacesContext.getCurrentInstance().addMessage("msgsAct", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No es posible cambiar el tipo del investigador", ""));
	    investigadorProyectoActual.setTipo(copiaValidacionInvestigador.getTipo());
	    return;
	}

	// /
	// VALIDA QUE NO HAYAN DOS INVESTIGADORES PRINCIPALES, QUE EL PRINCIPAL
	// PERTENEZCA AL GRUPO
	// Y SI LA CONVOCATORIA ES 2017 EL INVESTIGADOR PERTENEZCA A ELLA
	if (investigadorProyectoActual.getTipo().getId().equals("P") && edicion) {
	    if (!validaDuplicidadPrincipal(investigadorProyectoActual) || !validarGrupoPrincipal()) {
		investigadorProyectoActual.copiarDatos(copiaValidacionInvestigador);
		return;
	    }
	}

	if (!investigadorExiste) {
	    // ESTE CASO MANEJA EL HECHO QUE EL INVESTIGADOR EXTERNO NO EXISTA
	    errorValidacion = "";
	    investigadorProyectoActual = new InvestigadorProyecto();
	    copiaValidacionInvestigador = new InvestigadorProyecto();
	    investigadorProyectoActual.setInvestigador(new Investigador());
	    TipoInvestigador ti = new TipoInvestigador();
	    ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), "C");

	    investigadorProyectoActual.setTipo(ti);
	    investigadorProyectoActual.getInvestigador().setId(new IdPersona());
	    investigadorExiste = true;
	    sesion.removeAttribute("manejadorInvestigadorExterno");
	    edicion = false;
	    ipv.setEdicion(false);
	    return;
	} else {
	    errorValidacion = "";
	}
	if (validarInvestigadorIndividual(investigadorProyectoActual)) {
	    investigadorProyectoActual = new InvestigadorProyecto();
	    investigadorProyectoActual.setInvestigador(new Investigador());
	    investigadorProyectoActual.getInvestigador().setId(new IdPersona());
	    copiaValidacionInvestigador = new InvestigadorProyecto();
	    edicion = false;
	    ipv.setEdicion(false);
	    FacesContext.getCurrentInstance().addMessage("msgsAct", new FacesMessage(FacesMessage.SEVERITY_INFO, "El investigador se ha actualizado correctamente", ""));
	} else {
	    investigadorProyectoActual.copiarDatos(copiaValidacionInvestigador);
	}
    }

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

		    if (lis[i].getOutcome().equals("irInvestigadores")) {
			bandera = true;
		    }

		}
	    }
	}
	// ////////////////

	if (proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.SENA))
	    return "irEmpresas";
	return "irDatosBasicos";
    }

    public String salir() {
	sesion.removeAttribute("proyecto");
	borrarManejadoresInsercionProyecto();
	return "misProyectos";
    }

    public String salirGuardar() {
	if (proyectoActual.getListaInvestigadoresProyecto().size() > 0) {
	    if (hayEnEdicion()) {
		errorValidacion = "Por favor, actualice los datos de los investigadores antes de pasar al siguiente formulario.";
		FacesContext.getCurrentInstance().addMessage("msgs",
			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor, actualice los datos de los investigadores antes de pasar al siguiente formulario.", ""));
		return "";
	    }
	    if (validarInvestigadoresProyecto()) {

		// ///////MODIFICADO GIOVANNI
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

			    if (lis[i].getOutcome().equals("irInvestigadores")) {
				bandera = true;
			    }
			    if (lis[i].isRendered()) {
				pos++;
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

		    listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='40'");
		    formulario = (Formulario) listaFormulario.get(0);

		    HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
            historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
            historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
            historicoFormualrioProyecto.setFormulario(formulario);
            historicoFormualrioProyecto.setProyecto(proyectoActual);
            historicoFormualrioProyecto.setFechaCambio(new Date());
            servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
		}

		if ((proyectoActual.getEstadoProyecto().getId()).equals("I") && (pos - 1) >= proyectoActual.getFase().intValue()) {
		    proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
		}
		servicioProyecto.ingresarProyecto(proyectoActual);
		sesion.removeAttribute("proyecto");
		sesion.removeAttribute("manejadorMenuFormularios");
		this.borrarManejadoresInsercionProyecto();
		return "misProyectos";
	    }

	} else {
	    errorValidacion = "No hay investigadores asociados al proyecto";
	    FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No hay investigadores asociados al proyecto", ""));
	}
	return "";
    }

    public String siguiente() {
	if (proyectoActual.getListaInvestigadoresProyecto().size() > 0) {

	    if (hayEnEdicion()) {
		errorValidacion = "Por favor, actualice los datos de los investigadores antes de pasar al siguiente formulario.";
		FacesContext.getCurrentInstance().addMessage("msgs",
			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor, actualice los datos de los investigadores antes de pasar al siguiente formulario.", ""));
		return "";
	    }

	    if (validarInvestigadoresProyecto()) {

		// ///////MODIFICADO GIOVANNI
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

			    if (lis[i].getOutcome().equals("irInvestigadores")) {
				bandera = true;
			    }
			    if (lis[i].isRendered()) {
				pos++;
			    }
			}
		    }
		}

		if ((proyectoActual.getEstadoProyecto().getId()).equals("I") && (pos - 1) == proyectoActual.getFase().intValue()) {
		    proyectoActual.setFase(new Integer((proyectoActual.getFase()).intValue() + 1));
		}

		if (proyectoActual.getId() != null) {
		    // Ing. Wilver Alexander Martínez Martínez -wam²
		    // Cambio - Registro de cambios
		    Persona personaAux = new Persona();
		    personaAux = (Persona) sesion.getAttribute("persona");

		    Formulario formulario = new Formulario();
		    List listaFormulario = new ArrayList();

		    listaFormulario = servicioGeneral.obtenerListaObjetos("Formulario where id ='40'");
		    formulario = (Formulario) listaFormulario.get(0);

		    HistoricoFormularioProyecto historicoFormualrioProyecto = new HistoricoFormularioProyecto();
            historicoFormualrioProyecto.setDocPersona(personaAux.getId().getDocumento());
            historicoFormualrioProyecto.setTipoDocumentoPersona(personaAux.getId().getTipoDocumento());
            historicoFormualrioProyecto.setFormulario(formulario);
            historicoFormualrioProyecto.setProyecto(proyectoActual);
            historicoFormualrioProyecto.setFechaCambio(new Date());
            servicioGeneral.guardarObjeto(historicoFormualrioProyecto);
		}

		servicioProyecto.ingresarProyecto(proyectoActual);
		sesion.setAttribute("proyecto", proyectoActual);

		sesion.removeAttribute("manejadorInvestigadores");
		sesion.removeAttribute("manejadorMenuFormularios");
		borrarManejadoresInsercionProyecto();
		return link;

	    }// if validar investigadores
	} else {
	    errorValidacion = "No hay investigadores asociados al proyecto";
	    FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No hay investigadores asociados al proyecto", ""));
	}
	return "";
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

    private void cargarTiposDocumento() {
	listaTipoDocumento = servicioGeneral.obtenerListaObjetos("TipoDocumento");
	tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
	for (int i = 0; i < listaTipoDocumento.size(); i++) {
	    TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
	    tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
	}
    }

    private InvestigadorProyecto obtenerInvestigadorPrincipal() {
	for (int i = 0; i < listaInvestigadoresVista.size(); i++) {
	    InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) listaInvestigadoresVista.get(i);
	    if (ipv.getIp().getTipo().getId().equals("P")) {
		return ipv.getIp();
	    }
	}
	return null;
    }

    private InvestigadorProyectoVista obtenerInvestigadorPrincipalVista() {
	for (int i = 0; i < listaInvestigadoresVista.size(); i++) {
	    InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) listaInvestigadoresVista.get(i);
	    if (ipv.getIp().getTipo().getId().equals("P")) {
		return ipv;
	    }
	}
	return null;
    }

    private boolean buscarInvestigador(IdPersona id) {
	// BUSCA UN INVESTIGADOR DE ACUERDO A SU ID
	boolean investigadorPresente = false;
	int i = 0;
	List listaInvestigadoresProyecto = listaInvestigadoresVista;
	while (i < listaInvestigadoresProyecto.size()) {
	    InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) listaInvestigadoresVista.get(i);
	    InvestigadorProyecto d = ipv.getIp();
	    if (id.getDocumento().equals(d.getInvestigador().getId().getDocumento()) && id.getTipoDocumento().equals(d.getInvestigador().getId().getTipoDocumento())) {
		investigadorPresente = true;
		errorValidacion = "El investigador ya se encuentra asociado al proyecto";
		break;
	    }
	    i = i + 1;
	}
	return investigadorPresente;
    }

    public void cambiarTipoInvestigador(ValueChangeEvent event) {
    }

    private boolean proyectoTienePrincipal() {
	// Valida que el proyecto tenga investigador principal
	boolean conPrincipal = false;
	List listaInvestigadoresProyecto = listaInvestigadoresVista;
	for (int i = 0; i < listaInvestigadoresProyecto.size(); i++) {
	    InvestigadorProyectoVista ipc = (InvestigadorProyectoVista) listaInvestigadoresProyecto.get(i);

	    InvestigadorProyecto ip = ipc.getIp();
	    if (ip.getTipo().getId().equals("P")) {
		conPrincipal = true;
	    }
	}
	return conPrincipal;
    }

    private boolean proyectoTieneCodirector() {
	// Valida que el proyecto tenga investigador principal
	boolean conCod = false;
	int con = 0;
	List listaInvestigadoresProyecto = listaInvestigadoresVista;
	for (int i = 0; i < listaInvestigadoresProyecto.size(); i++) {
	    InvestigadorProyectoVista ipc = (InvestigadorProyectoVista) listaInvestigadoresProyecto.get(i);

	    InvestigadorProyecto ip = ipc.getIp();
	    if (ip.getTipo().getId().equals("CES")) {
		conCod = true;
		con++;
	    }
	}

	if (con > 1) {
	    conCod = false;
	}

	if (!conCod) {
	    FacesContext.getCurrentInstance().addMessage("msgs",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor verificar si el proyecto no tiene codirector o si hay más de una persona con el rol de codirector.", ""));
	}
	return conCod;
    }

    private boolean validarPrincipal() {
	boolean conPrincipal = proyectoTienePrincipal();
	if (!conPrincipal) {
	    errorValidacion = "El proyecto no posee investigador principal";
	} else {
	    errorValidacion = "";
	}
	return conPrincipal;
    }

    private boolean validaDuplicidadPrincipal(InvestigadorProyecto ipry) {
	boolean conPrincipal = validarPrincipal();
	boolean conDoblePrincipal = false;
	if (conPrincipal) {
	    List listaInvestigadoresProyecto = listaInvestigadoresVista;
	    for (int i = 0; i < listaInvestigadoresProyecto.size(); i++) {
		InvestigadorProyectoVista ipc = (InvestigadorProyectoVista) listaInvestigadoresProyecto.get(i);
		InvestigadorProyecto ip = ipc.getIp();
		if (ip.getTipo().getId().equals("P")) {
		    if (!ipry.getInvestigador().getId().getDocumento().equals(ip.getInvestigador().getId().getDocumento())) {
			errorValidacion = "El proyecto ya posee un investigador 'Principal'";
			conDoblePrincipal = true;
			return !conDoblePrincipal;
		    } else {
			errorValidacion = "";
		    }
		}
	    }
	} else {
	}
	return !conDoblePrincipal;
    }

    private boolean investigadorPerteneceGrupo(Investigador ipry) {
	// DADO UN INVESTIGADOR SE VALIDA QUE PERTENEZCA AL GRUPO ASIGNADO AL
	// PROYECTO
	if (grupoActual != null) {
	    Set invgr = servicioGrupo.obtenerInvestigadoresGrupo(grupoActual.getId());
	    Iterator it = invgr.iterator();
	    while (it.hasNext()) {
		Investigador inv = ((InvestigadorGrupo) it.next()).getInvestigador();
		if (inv.getId().getTipoDocumento().equals(ipry.getId().getTipoDocumento()) && inv.getId().getDocumento().equals(ipry.getId().getDocumento())) {
		    errorValidacion = "";
		    return true;
		}
	    }
	    errorValidacion = "El investigador no pertenece al grupo asignado al proyecto";
	    return false;
	}
	return true;
    }

    private boolean principalTieneGrupos() {
	// valida que el investigador principal pertenezca al grupo si la
	// convocatoria es para grupos
	if (proyectoActual.getModalidad() instanceof Convocatoria && ((Convocatoria) proyectoActual.getModalidad()).getEsParaGrupos() != null
		&& ((Convocatoria) proyectoActual.getModalidad()).getEsParaGrupos().booleanValue()) {
	    if (proyectoTienePrincipal()) {
		if (!investigadorPerteneceGrupo(obtenerInvestigadorPrincipal().getInvestigador())) {
		    errorValidacion = "El investigador principal no pertenece al grupo asignado al proyecto";
		    return false;
		}
	    } else {
		return false;
	    }
	}
	errorValidacion = "";
	return true;
    }

    private boolean validarDedicacionExclusiva(InvestigadorInterno invI) {
	if (invI != null && invI.getTipoDedicacion() != null && invI.getTipoDedicacion().getId().equals("3")) {
	    errorValidacion = "";
	    return true;
	}
	errorValidacion = "El investigador 2017 no es de dedicación exclusiva";
	return false;
    }

    private boolean validarGrupoPrincipal() {
	if (proyectoActual.getModalidad() instanceof Convocatoria && ((Convocatoria) proyectoActual.getModalidad()).getEsParaGrupos() != null
		&& ((Convocatoria) proyectoActual.getModalidad()).getEsParaGrupos().booleanValue()) {
	    boolean conPrincipal = principalTieneGrupos();
	    if (!conPrincipal) {
		errorValidacion = "El investigador principal no pertenece al grupo asignado al proyecto";
	    } else {
		errorValidacion = "";
	    }
	    return conPrincipal;
	}
	return true;
    }

    private boolean validarInvestigadorIndividual(InvestigadorProyecto ipry) {

	// SE VALIDA EL CAMPO FUNCION
	if (ipry.getInvestigador().getId().getDocumento() == null || ipry.getInvestigador().getId().getDocumento().length() == 0 || ipry.getInvestigador().getId().getDocumento().equals("")) {
	    errorValidacion = "El campo 'No. documento' es requerido";
	    FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El campo 'No. documento' es requerido", ""));
	    return false;
	}

	// SE VALIDAN LA DEDICACION SEMANAL
	if (ipry.getDedicacionHorasSemana() <= 0) {
	    errorValidacion = "El campo de dedicacion semanal es requerido";
	    FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El campo de dedicacion semanal es requerido", ""));
	    return false;
	} else {
	    try {
		int t = (int) ipry.getDedicacionHorasSemana();
		if (ipry.getDedicacionHorasSemana() > 44.0) {
		    errorValidacion = "El número máximo de horas semanales es 44";
		    FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El número máximo de horas semanales es 44", ""));
		    return false;
		}
	    } catch (NumberFormatException e) {
		errorValidacion = "El campo de dedicación semanal no es válido";
		FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El campo de dedicación semanal no es válido", ""));
		return false;
	    }
	}
	// SE VALIDA EL CAMPO FUNCION
	if (ipry.getFuncion().length() == 0) {
	    errorValidacion = "El campo 'función' es requerido";
	    FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El campo 'función' es requerido", ""));
	    return false;
	}

	// 20110103
	// wam²
	// SE VALIDA SI ES ESTUDIANTE REALMENTE
	if (ipry.getTipo().getId().equals("A") || ipry.getTipo().getId().equals("AL") || ipry.getTipo().getId().equals("ESPR") || ipry.getTipo().getId().equals("ESPO")) {
	    Estudiante eAux = servicioPersona.obtenerEstudiante(ipry.getInvestigador().getId());
	    if (eAux == null) {
		errorValidacion = "El investigador no es un estudiante válido.";
		FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El investigador no es un estudiante válido.", ""));
		return false;
	    }
	}

	// validar si director y codirector son interno con tipo vinc 30

	if (ipry.getTipo().getId().equals("P") || ipry.getTipo().getId().equals("CES")) {
	    InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInternoCompleto(ipry.getInvestigador().getId());
	    if (ii == null) {
		errorValidacion = "El investigador no es interno.";
		FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El investigador no es un investigador interno.", ""));
		return false;
	    }
	}

	errorValidacion = "";
	return true;
    }

    public boolean hayEnEdicion() {
	boolean bandera = false;
	List li = getListaInvestigadoresVista();
	if (li != null && li.size() > 0) {
	    for (Iterator i = li.iterator(); i.hasNext();) {

		InvestigadorProyectoVista ipv = (InvestigadorProyectoVista) i.next();
		InvestigadorProyecto invProy = ipv.getIp();
		if (invProy.getDedicacionHorasSemana() <= 0 || invProy.getFuncion().trim().equals("") || invProy.getFuncion() == null) {
		    bandera = true;
		}
	    }
	}
	return bandera;

    }

    private boolean validarInvestigadoresProyecto() {
	// SE VALIDA EL INVESTIGADOR PRINCIPAL
	if (!esExtensionSolidaria && (!validarPrincipal() || !validarGrupoPrincipal())) {
	    return false;
	}

	if (esExtensionSolidaria && (!validarPrincipal() || !validarGrupoPrincipal() || !proyectoTieneCodirector())) {
	    return false;
	}

	boolean investigadorValido = true;
	// REVISA QUE TODOS LOS DATOS DE TODOS LOS INVESTIGADORES SEAN VALIDOS
	try {
	    Iterator it = (proyectoActual.getListaInvestigadoresProyecto()).iterator();
	    while (it.hasNext() && investigadorValido) {

		InvestigadorProyecto ipv = (InvestigadorProyecto) it.next();
		investigadorValido = validarInvestigadorIndividual(ipv);// validarInvestigadorIndividual((InvestigadorProyecto)it.next());
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}
	return investigadorValido;
    }

    public void cambiaTipoInvestigador(ValueChangeEvent event) {
	String tipoInvestigadorSeleccionado = (String) event.getNewValue();
	for (Iterator itTipoInvestigador = listaTipoInvestigador.iterator(); itTipoInvestigador.hasNext();) {
	    TipoInvestigador ti = (TipoInvestigador) itTipoInvestigador.next();
	    if (ti.getId().equals(tipoInvestigadorSeleccionado)) {
		InvestigadorProyecto ip = ipv.getIp();
		ip.setTipo(ti);
		break;
	    }
	}
    }

    public void cambiaActualTipoInvestigador(ValueChangeEvent event) {

	String tipoInvestigadorSeleccionado = (String) event.getNewValue();
	for (Iterator itTipoInvestigador = listaTipoInvestigador.iterator(); itTipoInvestigador.hasNext();) {
	    TipoInvestigador ti = (TipoInvestigador) itTipoInvestigador.next();
	    if (ti.getId().equals(tipoInvestigadorSeleccionado)) {
		InvestigadorProyecto ip = investigadorProyectoActual;
		ip.setTipo(ti);
		break;
	    }
	}
    }

    public void cambiarEmpresa(ValueChangeEvent event) {
	Object objecto = event.getNewValue();
	System.out.println(objecto.getClass().getName());
	Empresa em = (Empresa) servicioGeneral.obtenerObjeto(new Empresa(), new Long((String) objecto));
	investigadorProyectoNuevo.setEmpresa(em);
	idEmpresa = em.getId().toString();
    }

    public void cambiarEmpresaActual(ValueChangeEvent event) {
	Object objecto = event.getNewValue();
	System.out.println(objecto.getClass().getName());
	Empresa em = (Empresa) servicioGeneral.obtenerObjeto(new Empresa(), new Long((String) objecto));
	investigadorProyectoActual.setEmpresa(em);
	idEmpresa = em.getId().toString();
    }

    public void onEdit(RowEditEvent event) {
	FacesMessage msg = new FacesMessage("Participante editado", ((InvestigadorProyectoVista) event.getObject()).getInvestigadorProyecto().getInvestigador().getNombre1());

	FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
	FacesMessage msg = new FacesMessage("Edición cancelada", ((InvestigadorProyectoVista) event.getObject()).getInvestigadorProyecto().getInvestigador().getNombre1());

	FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public SelectItem[] getListaTipoInvestigadorEditarFuncion() {

	List listaTipoInvestigadorAux = new Vector();
	SelectItem[] tipoInvItem;
	if (ipv.getInvestigadorProyecto().getTipo().getId().equals(TipoInvestigador.Principal)) {
	    TipoInvestigador principal = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), TipoInvestigador.Principal);
	    listaTipoInvestigadorAux.add(principal);
	    tipoInvItem = new SelectItem[listaTipoInvestigadorAux.size()];
	    int j = 0;
	    for (Iterator itTInv = listaTipoInvestigadorAux.iterator(); itTInv.hasNext(); j++) {
		TipoInvestigador ti = (TipoInvestigador) itTInv.next();
		tipoInvItem[j] = new SelectItem(ti.getId(), ti.getNombre());
	    }
	    System.out.println("tipo investigador editar " + tipoInvItem.length);
	    return tipoInvItem;
	}

	if (yaHayPrincipal()) {

	    listaTipoInvestigadorAux = servicioGeneral.listaDeObjetosYDiferenteElIdStringAMod("TipoInvestigador", TipoInvestigador.Principal, "C");

	    tipoInvItem = new SelectItem[listaTipoInvestigadorAux.size()];
	    int j = 0;
	    for (Iterator itTInv = listaTipoInvestigadorAux.iterator(); itTInv.hasNext(); j++) {
		TipoInvestigador ti = (TipoInvestigador) itTInv.next();
		tipoInvItem[j] = new SelectItem(ti.getId(), ti.getNombre());
	    }
	    System.out.println("tipo investigador editar " + tipoInvItem.length);
	    // ///
	    if (esExtensionSolidaria) {
		listaTipoInvestigadorAux = servicioGeneral.listaDeObjetosYDiferenteElIdStringAMod("TipoInvestigador", TipoInvestigador.Principal, "ES");

		tipoInvItem = new SelectItem[listaTipoInvestigadorAux.size()];

		int l = 0;
		for (Iterator itTInv = listaTipoInvestigadorAux.iterator(); itTInv.hasNext(); l++) {
		    TipoInvestigador ti = (TipoInvestigador) itTInv.next();
		    tipoInvItem[l] = new SelectItem(ti.getId(), ti.getNombre());
		}

	    }
	    // ///
	    return tipoInvItem;
	} else {
	    TipoInvestigador principal = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), TipoInvestigador.Principal);
	    listaTipoInvestigadorAux.add(principal);
	    tipoInvItem = new SelectItem[listaTipoInvestigadorAux.size()];
	    int j = 0;
	    for (Iterator itTInv = listaTipoInvestigadorAux.iterator(); itTInv.hasNext(); j++) {
		TipoInvestigador ti = (TipoInvestigador) itTInv.next();
		tipoInvItem[j] = new SelectItem(ti.getId(), ti.getNombre());
	    }
	    System.out.println("tipo investigador editar " + tipoInvItem.length);
	    return tipoInvItem;
	}

    }

    public SelectItem[] getListaTipoInvestigadorIngresar() {
	List listaTipoInvestigadorAux = new Vector();

	SelectItem[] tipoInv;
	if (!yaHayPrincipal()) {
	    TipoInvestigador principal = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), TipoInvestigador.Principal);
	    listaTipoInvestigadorAux.add(principal);
	    tipoInv = new SelectItem[listaTipoInvestigadorAux.size()];
	    int j = 0;
	    for (Iterator itTInv = listaTipoInvestigadorAux.iterator(); itTInv.hasNext(); j++) {

		TipoInvestigador ti = (TipoInvestigador) itTInv.next();
		tipoInv[j] = new SelectItem(ti.getId(), ti.getNombre());

	    }
	    System.out.println("tipo investigador editar " + tipoInv.length);
	    return tipoInv;
	}

	listaTipoInvestigadorAux = servicioGeneral.listaDeObjetosYDiferenteElIdStringAMod("TipoInvestigador", TipoInvestigador.Principal, "C");
	List listaAuxiliar = new ArrayList();

	if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0 || proyectoActual.getModalidad().getTipo().getId().compareTo("CV") == 0) {
	    for (int i = 0; i < listaTipoInvestigadorAux.size(); i++) {
		TipoInvestigador tipAux = new TipoInvestigador();
		tipAux = (TipoInvestigador) listaTipoInvestigadorAux.get(i);
		if (!tipAux.getId().equals("AL")) {
		    listaAuxiliar.add(tipAux);
		}
	    }
	    listaTipoInvestigadorAux = listaAuxiliar;
	}

	tipoInv = new SelectItem[listaTipoInvestigadorAux.size()];
	int j = 0;
	for (Iterator itTInv = listaTipoInvestigadorAux.iterator(); itTInv.hasNext(); j++) {
	    TipoInvestigador ti = (TipoInvestigador) itTInv.next();
	    tipoInv[j] = new SelectItem(ti.getId(), ti.getNombre());
	}
	System.out.println("tipo investigador editar " + tipoInv.length);

	// extensión solidaria

	if (esExtensionSolidaria) {

	    listaTipoInvestigadorAux = servicioGeneral.listaDeObjetosYDiferenteElIdStringAMod("TipoInvestigador", TipoInvestigador.Principal, "ES");
	    List listaAuxiliarES = new ArrayList();

	    for (int i = 0; i < listaTipoInvestigadorAux.size(); i++) {
		TipoInvestigador tipAux = new TipoInvestigador();
		tipAux = (TipoInvestigador) listaTipoInvestigadorAux.get(i);
		listaAuxiliarES.add(tipAux);
	    }
	    listaTipoInvestigadorAux = listaAuxiliarES;

	    tipoInv = new SelectItem[listaTipoInvestigadorAux.size()];
	    int l = 0;
	    for (Iterator itTInv = listaTipoInvestigadorAux.iterator(); itTInv.hasNext(); l++) {
		TipoInvestigador ti = (TipoInvestigador) itTInv.next();
		tipoInv[l] = new SelectItem(ti.getId(), ti.getNombre());
	    }
	    System.out.println("tipo investigador editar " + tipoInv.length);

	}

	return tipoInv;
    }

    public boolean yaHayPrincipal() {
	if (listaInvestigadoresVista.size() > 0) {
	    for (Iterator it = listaInvestigadoresVista.iterator(); it.hasNext();) {
		InvestigadorProyectoVista ipc = (InvestigadorProyectoVista) it.next();
		InvestigadorProyecto ip = ipc.getIp();
		if (ip.getTipo().getId().equals(TipoInvestigador.Principal)) {
		    return true;
		}
	    }
	}
	return false;
    }

    public boolean yaHayCodirector() {
	if (listaInvestigadoresVista.size() > 0) {
	    for (Iterator it = listaInvestigadoresVista.iterator(); it.hasNext();) {
		InvestigadorProyectoVista ipc = (InvestigadorProyectoVista) it.next();
		InvestigadorProyecto ip = ipc.getIp();
		if (ip.getTipo().getId().equals(TipoInvestigador.CODIRECTOR)) {
		    return true;
		}
	    }
	}
	return false;
    }

    // FUNCIONES MISCELANEAS

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

    public boolean getEsSena() {
	return proyectoActual.getModalidad().getTipo().getId().equals(TipoModalidad.SENA);
    }

    public String getErrorValidacion() {
	return errorValidacion;
    }

    public void setErrorValidacion(String errorValidacion) {
	this.errorValidacion = errorValidacion;
    }

    public List getListaTipoDocumento() {
	return listaTipoDocumento;
    }

    public void setListaTipoDocumento(List listaTipoDocumento) {
	this.listaTipoDocumento = listaTipoDocumento;
    }

    public DataTable getTablaInvestigadoresProyecto() {
	return tablaInvestigadoresProyecto;
    }

    public void setTablaInvestigadoresProyecto(DataTable tablaInvestigadoresProyecto) {
	this.tablaInvestigadoresProyecto = tablaInvestigadoresProyecto;
    }

    public SelectItem[] getTipoDocumentoItem() {
	return tipoDocumentoItem;
    }

    public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
	this.tipoDocumentoItem = tipoDocumentoItem;
    }

    public InvestigadorProyecto getInvestigadorProyectoActual() {
	return investigadorProyectoActual;
    }

    public void setInvestigadorProyectoActual(InvestigadorProyecto investigadorProyectoActual) {
	this.investigadorProyectoActual = investigadorProyectoActual;
    }

    public boolean isEdicion() {
	return edicion;
    }

    public void setEdicion(boolean edicion) {
	this.edicion = edicion;
    }

    public Grupo getGrupoActual() {
	return grupoActual;
    }

    public void setGrupoActual(Grupo grupoActual) {
	this.grupoActual = grupoActual;
    }

    public boolean isInvestigadorExiste() {
	return investigadorExiste;
    }

    public void setInvestigadorExiste(boolean investigadorExiste) {
	this.investigadorExiste = investigadorExiste;
    }

    public boolean getPrincipalTieneGrupos() {
	return principalTieneGrupos();
    }

    public SelectItem[] getFacultadItem() {
	return facultadItem;
    }

    public void setFacultadItem(SelectItem[] facultadItem) {
	this.facultadItem = facultadItem;
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

    public SelectItem[] getTipoInvestigadorItem() {
	return tipoInvestigadorItem;
    }

    public void setTipoInvestigadorItem(SelectItem[] tipoInvestigadorItem) {
	this.tipoInvestigadorItem = tipoInvestigadorItem;
    }

    protected void cargarValoresIniciales() {
	

    }

    public SelectItem[] getListaEmpresasItem() {
	return listaEmpresasItem;
    }

    public void setListaEmpresasItem(SelectItem[] listaEmpresasItem) {
	this.listaEmpresasItem = listaEmpresasItem;
    }

    public String getIdEmpresa() {
	if (investigadorProyectoActual != null && investigadorProyectoActual.getEmpresa() != null && investigadorProyectoActual.getEmpresa().getId() != null) {
	    idEmpresa = investigadorProyectoActual.getEmpresa().getId().toString();
	}
	return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
	this.idEmpresa = idEmpresa;
    }

    public SelectItem[] getTipoInvestigadorItemNuevo() {
	return tipoInvestigadorItemNuevo;
    }

    public void setTipoInvestigadorItemNuevo(SelectItem[] tipoInvestigadorItemNuevo) {
	this.tipoInvestigadorItemNuevo = tipoInvestigadorItemNuevo;
    }

    public InvestigadorProyecto getInvestigadorProyectoNuevo() {
	return investigadorProyectoNuevo;
    }

    public void setInvestigadorProyectoNuevo(InvestigadorProyecto investigadorProyectoNuevo) {
	this.investigadorProyectoNuevo = investigadorProyectoNuevo;
    }

    public List getListaInvestigadoresVista() {
	return listaInvestigadoresVista;
    }

    public void setListaInvestigadoresVista(List listaInvestigadoresVista) {
	this.listaInvestigadoresVista = listaInvestigadoresVista;
    }

    public String getTitulo1() {
	return titulo1;
    }

    public void setTitulo1(String titulo1) {
	this.titulo1 = titulo1;
    }

    public String getTitulo2() {
	return titulo2;
    }

    public void setTitulo2(String titulo2) {
	this.titulo2 = titulo2;
    }

    public boolean isMostrarMensajePrincipal() {
	return mostrarMensajePrincipal;
    }

    public void setMostrarMensajePrincipal(boolean mostrarMensajePrincipal) {
	this.mostrarMensajePrincipal = mostrarMensajePrincipal;
    }

    public InvestigadorProyectoVista getInvPryVis() {
	return invPryVis;
    }

    public void setInvPryVis(InvestigadorProyectoVista invPryVis) {
	this.invPryVis = invPryVis;
    }

    public DataTable getDataTableIntegrantes() {
	return dataTableIntegrantes;
    }

    public void setDataTableIntegrantes(DataTable dataTableIntegrantes) {
	this.dataTableIntegrantes = dataTableIntegrantes;
    }

    public String getInsitucionNombre() {
	return insitucionNombre;
    }

    public void setInsitucionNombre(String insitucionNombre) {
	this.insitucionNombre = insitucionNombre;
    }

    public SelectItem[] getGeneroItem() {
	return generoItem;
    }

    public void setGeneroItem(SelectItem[] generoItem) {
	this.generoItem = generoItem;
    }

    public SelectItem[] getEstadoCivilItem() {
	return estadoCivilItem;
    }

    public void setEstadoCivilItem(SelectItem[] estadoCivilItem) {
	this.estadoCivilItem = estadoCivilItem;
    }

    public SelectItem[] getCategoriaInvestigadorItem() {
	return categoriaInvestigadorItem;
    }

    public void setCategoriaInvestigadorItem(SelectItem[] categoriaInvestigadorItem) {
	this.categoriaInvestigadorItem = categoriaInvestigadorItem;
    }

    public SelectItem[] getCiudadDomicilioItem() {
	return ciudadDomicilioItem;
    }

    public void setCiudadDomicilioItem(SelectItem[] ciudadDomicilioItem) {
	this.ciudadDomicilioItem = ciudadDomicilioItem;
    }

    public SelectItem[] getInstitucionItem() {
	return institucionItem;
    }

    public void setInstitucionItem(SelectItem[] institucionItem) {
	this.institucionItem = institucionItem;
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

    public InvestigadorExterno getInvestigadorExterno() {
	return investigadorExterno;
    }

    public void setInvestigadorExterno(InvestigadorExterno investigadorExterno) {
	this.investigadorExterno = investigadorExterno;
    }

    public EstadoCivil getEstadoCivil() {
	return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
	this.estadoCivil = estadoCivil;
    }

    public TipoDocumento getTipoDocumento() {
	return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
	this.tipoDocumento = tipoDocumento;
    }

    public Ciudad getCiudadActual() {
	return ciudadActual;
    }

    public void setCiudadActual(Ciudad ciudadActual) {
	this.ciudadActual = ciudadActual;
    }

    public Institucion getInstitucion() {
	return institucion;
    }

    public void setInstitucion(Institucion institucion) {
	this.institucion = institucion;
    }

    public Departamento getDepartamentoActual() {
	return departamentoActual;
    }

    public void setDepartamentoActual(Departamento departamentoActual) {
	this.departamentoActual = departamentoActual;
    }

    public CategoriaInvestigador getCategoriaInvestigador() {
	return categoriaInvestigador;
    }

    public void setCategoriaInvestigador(CategoriaInvestigador categoriaInvestigador) {
	this.categoriaInvestigador = categoriaInvestigador;
    }

    public String getDocumento() {
	return documento;
    }

    public void setDocumento(String documento) {
	this.documento = documento;
    }

    public List getListaGenero() {
	return listaGenero;
    }

    public void setListaGenero(List listaGenero) {
	this.listaGenero = listaGenero;
    }

    public List getListaEstadoCivil() {
	return listaEstadoCivil;
    }

    public void setListaEstadoCivil(List listaEstadoCivil) {
	this.listaEstadoCivil = listaEstadoCivil;
    }

    public List getListaCategoriaInvestigador() {
	return listaCategoriaInvestigador;
    }

    public void setListaCategoriaInvestigador(List listaCategoriaInvestigador) {
	this.listaCategoriaInvestigador = listaCategoriaInvestigador;
    }

    public List getListaCiudades() {
	return listaCiudades;
    }

    public void setListaCiudades(List listaCiudades) {
	this.listaCiudades = listaCiudades;
    }

    public List getListaInstitucion() {
	return listaInstitucion;
    }

    public void setListaInstitucion(List listaInstitucion) {
	this.listaInstitucion = listaInstitucion;
    }

    public List getListaDepartamentos() {
	return listaDepartamentos;
    }

    public void setListaDepartamentos(List listaDepartamentos) {
	this.listaDepartamentos = listaDepartamentos;
    }

    public String getEtiquetaEditar() {
	return etiquetaEditar;
    }

    public void setEtiquetaEditar(String etiquetaEditar) {
	this.etiquetaEditar = etiquetaEditar;
    }

    public InvestigadorProyectoVista getIpv() {
	return ipv;
    }

    public void setIpv(InvestigadorProyectoVista ipv) {
	this.ipv = ipv;
    }

    public String getNombresInvestigador() {
	return nombresInvestigador;
    }

    public void setNombresInvestigador(String nombresInvestigador) {
	this.nombresInvestigador = nombresInvestigador;
    }

    public String getNumDocumentoInvestigador() {
	return numDocumentoInvestigador;
    }

    public void setNumDocumentoInvestigador(String numDocumentoInvestigador) {
	this.numDocumentoInvestigador = numDocumentoInvestigador;
    }

    public String getTipoRolInvestigador() {
	return tipoRolInvestigador;
    }

    public void setTipoRolInvestigador(String tipoRolInvestigador) {
	this.tipoRolInvestigador = tipoRolInvestigador;
    }

    public double getHorasDedicacionInvestigador() {
	return horasDedicacionInvestigador;
    }

    public void setHorasDedicacionInvestigador(double horasDedicacionInvestigador) {
	this.horasDedicacionInvestigador = horasDedicacionInvestigador;
    }

    public String getFuncionInvestigador() {
	return funcionInvestigador;
    }

    public void setFuncionInvestigador(String funcionInvestigador) {
	this.funcionInvestigador = funcionInvestigador;
    }

    public SelectItem[] getListaTipoInvestigadorEditar() {
	return listaTipoInvestigadorEditar;
    }

    public void setListaTipoInvestigadorEditar(SelectItem[] listaTipoInvestigadorEditar) {
	this.listaTipoInvestigadorEditar = listaTipoInvestigadorEditar;
    }

    public String getEtiquetaRolInvestigador() {
	return etiquetaRolInvestigador;
    }

    public void setEtiquetaRolInvestigador(String etiquetaRolInvestigador) {
	this.etiquetaRolInvestigador = etiquetaRolInvestigador;
    }

    public boolean isEsExtensionSolidaria() {
	return esExtensionSolidaria;
    }

    public void setEsExtensionSolidaria(boolean esExtensionSolidaria) {
	this.esExtensionSolidaria = esExtensionSolidaria;
    }

}