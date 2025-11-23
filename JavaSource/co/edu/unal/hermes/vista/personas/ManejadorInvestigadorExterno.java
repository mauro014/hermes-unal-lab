package co.edu.unal.hermes.vista.personas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UICommand;
import javax.faces.component.UIOutput;
import javax.faces.component.UIPanel;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.EstadoCivil;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.utils.VariablesEstaticas;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.proyectos.manejadorproyecto.ManejadorInvestigadores;

//MODIF
public class ManejadorInvestigadorExterno extends ManejadorBase {

    // OBJETOS GRAFICOS
    private SelectItem[] generoItem = { new SelectItem(VariablesEstaticas.GENERO_FEMENINO, VariablesEstaticas.GENERO_FEMENINO),
	    new SelectItem(VariablesEstaticas.GENERO_MASCULINO, VariablesEstaticas.GENERO_MASCULINO) };
    private SelectItem[] estadoCivilItem;
    private SelectItem[] categoriaInvestigadorItem;
    private SelectItem[] tipoDocumentoItem;
    private SelectItem[] ciudadDomicilioItem;
    private SelectItem[] institucionItem;
    private SelectItem[] departamentoItem;
    private SelectItem[] ciudadItem;
    private UISelectOne manejadorDepartamentos;
    private UIPanel exitoInsercionInvestigador;// JASSAR
    private UIPanel formularioCompleto;// jassar
    private UIOutput noexitoInsercionInvestigador;// jassar
    private UICommand botonGuardar;// jassar guardar info formulario a base de
				   // datos
    private UICommand botonSalir;// cerrar el popup
    private ManejadorInvestigadores manejadorInvestigadores;

    // OBJETOS DE LA APLICACION
    private InvestigadorExterno investigadorExterno;
    // private Genero genero;
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
    private List listaTipoDocumento;
    private List listaCiudades;
    private List listaInstitucion;
    private List listaDepartamentos;

    private boolean investigadorExiste;
    private boolean mostrarBotonSalir  = false;

    public ManejadorInvestigadorExterno() {
	super();
	listaGenero = new ArrayList();
	listaEstadoCivil = new ArrayList();
	listaCategoriaInvestigador = new ArrayList();
	listaTipoDocumento = new ArrayList();
	listaDepartamentos = new ArrayList();
	listaInstitucion = new ArrayList();
	listaCiudades = new ArrayList();
	investigadorExterno = new InvestigadorExterno();
	documento = new String();
	// ini jassar CARGA DE COMPONENTES GRAFICOS
	exitoInsercionInvestigador = new UIPanel();
	formularioCompleto = new UIPanel();
	noexitoInsercionInvestigador = new UIOutput();
	botonGuardar = new UICommand();
	botonSalir = new UICommand();
	borrar_componentes_graficos();
	formularioCompleto.setRendered(true);
	botonGuardar.setRendered(true);
	botonSalir.setRendered(true);
	// fin jassar
	// SE CARGAN LOS ESTADOS CIVILES
	listaEstadoCivil = servicioGeneral.obtenerListaObjetos("EstadoCivil");
	estadoCivilItem = new SelectItem[listaEstadoCivil.size()];
	for (int i = 0; i < listaEstadoCivil.size(); i++) {
	    EstadoCivil ec = (EstadoCivil) listaEstadoCivil.get(i);
	    estadoCivilItem[i] = new SelectItem(ec.getId(), ec.getNombre());
	}
	estadoCivil = (EstadoCivil) listaEstadoCivil.get(0);

	// SE CARGA LA CATEGORIA DEL INVESTIGADOR
	listaCategoriaInvestigador = servicioGeneral.obtenerListaObjetos("CategoriaInvestigador");
	categoriaInvestigadorItem = new SelectItem[listaCategoriaInvestigador.size()];
	for (int i = 0; i < listaCategoriaInvestigador.size(); i++) {
	    CategoriaInvestigador cat = (CategoriaInvestigador) listaCategoriaInvestigador.get(i);
	    categoriaInvestigadorItem[i] = new SelectItem(cat.getId(), cat.getNombre());

	}
	categoriaInvestigador = (CategoriaInvestigador) listaCategoriaInvestigador.get(0);

	// SE CARGAN LOS TIPOS DE DOCUMENTO
	listaTipoDocumento = servicioGeneral.obtenerListaObjetos("TipoDocumento");
	tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
	for (int i = 0; i < listaTipoDocumento.size(); i++) {
	    TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
	    tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
	}
	tipoDocumento = (TipoDocumento) listaTipoDocumento.get(0);

	// VINCULACION DE LOS DEPARTAMENTOS CONTENIDOS EN LA BASE DE DATOS
	// CON EL COMPONENTE WEB QUE PERMITE LA ESCOGENCIA DE UN DEPARTAMENTO
	obtenerListaDepartamentos();
	obtenerListaCiudades();

	// SE CARGAN LAS INSTITUCIONES

	listaInstitucion = servicioGeneral.obtenerListaInstituciones();
	institucionItem = new SelectItem[listaInstitucion.size()];
	for (int i = 0; i < listaInstitucion.size(); i++) {
	    Institucion in = (Institucion) listaInstitucion.get(i);
	    institucionItem[i] = new SelectItem(in.getId(), in.getNombre());
	}
	institucion = (Institucion) listaInstitucion.get(0);
	manejadorInvestigadores = (ManejadorInvestigadores) sesion.getAttribute("manejadorInvestigadores");
    }

    public void cambiarDepartamento(ValueChangeEvent event) {

	// VINCULACION DE LOS DEPARTAMENTOS CONTENIDOS EN LA BASE DE DATOS CON
	// EL COMPONENTE
	// WEB QUE PERMITE LA ESCOGENCIA DE UN DEPARTAMENTO Y LA RECARGA DE LAS
	// CIUDADES
	// CONTEXTOS DE LA APLICACION
	departamentoActual.setId((manejadorDepartamentos.getValue()).toString());

	listaCiudades = servicioGeneral.obtenerUbicacion("Ciudad", "", "departamento", departamentoActual,false);
	ciudadItem = new SelectItem[listaCiudades.size()];
	for (int i = 0; i < listaCiudades.size(); i++) {
	    Ciudad ci = (Ciudad) listaCiudades.get(i);
	    ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
	    ci = null;
	}
	ciudadActual = (Ciudad) listaCiudades.get(0);
    }

    // REALIZA LA INSERCION DEL INVESTIGADOR EXTERNO EN LA BASE DE DATOS
    public void salir() {
	sesion.removeAttribute("manejadorInvestigadorExterno");
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
	    // investigadorExterno.setInstitucion(institucion);
	    if (insitucionNombre != null && !insitucionNombre.equals("")) {

		// String idIns=insitucionNombre.split("-")[0];
		// Institucion i=(Institucion) servicioGeneral.obtenerObjeto(new
		// Institucion(),idIns);
		// investigadorExterno.setInstitucion(i);
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
		try {// JASSAR: COLOQUÉ ESTE TRY CATCH PARA PINTTAR LOS PANELES
		     // JSF AVISOS EXITO O FRACASO
		     // Si la persona no existe se guarda como investigador
		    servicioPersona.guardarInvestigador(investigadorExterno);
		    // InvestigadorProyecto
		    // ipryext=(manejadorInvestigadores.getInvestigadorProyectoActual());
		    // ipryext.setInvestigador(servicioPersona.obtenerInvestigadorExterno(id));
		    // ipryext.setProyecto(manejadorInvestigadores.getProyectoActual());
		    // servicioGeneral.guardarObjeto(ipryext);
		    // manejadorInvestigadores.getProyectoActual().adicionarInvestigadorProyecto(ipryext);

		    // manejadorInvestigadores.setProyectoActual(servicioProyecto.obtenerProyecto(manejadorInvestigadores.getProyectoActual().getId(),
		    // ProyectoDAOHibernate.INVESTIGADORES));
		    // manejadorInvestigadores.setInvestigadorProyectoActual(ipryext);

		    // ipryext = new InvestigadorProyecto();
		    TipoInvestigador ti = new TipoInvestigador();
		    ti = (TipoInvestigador) servicioGeneral.obtenerObjeto(new TipoInvestigador(), "C");
		    // ipryext.setTipo(ti);
		    // ipryext.setInvestigador(new Investigador());
		    // ipryext.getInvestigador().setId(new IdPersona());
		    // manejadorInvestigadores.setErrorValidacion("");
		    // manejadorInvestigadores.setInvestigadorExiste(true);
		    borrar_componentes_graficos();
		    exitoInsercionInvestigador.setRendered(true);
		    botonSalir.setRendered(true);
		    //investigadorExiste = false;
		    investigadorExiste = true;
		    noexitoInsercionInvestigador.setRendered(false);
		    investigadorExterno = new InvestigadorExterno();
		    mostrarBotonSalir = true;
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El investigador externo se ha creado correctamente", ""));
		} catch (Exception h) {
		    System.out.println("no se pudo hacer: ((IServicioPersona) appCtx.getBean(servicioPersona)).guardarInvestigadorExterno(investigadorExterno)");
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debido a problemas técnicos el investigador no se pudo adicionar", ""));
		    h.printStackTrace();
		}
	    } else {
		investigadorExiste = true;
		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La persona con este numero de documento ya se encuentra registrada en la base de datos", ""));
	    }
	    // AL TERMINAR ESTE TRY DEBO HACER QUE TODOS LOS OBJETOS CREADOS
	    // SEAN
	    // NULOS Y LIMPIAR LA FORMA PRINCIPAL
	    // DE INSERCION DE CONVOCATORIA
	} catch (Exception e) {
	    System.out.println("Investigador Externo No Insertado" + e.getMessage());
	    noexitoInsercionInvestigador.setRendered(true);
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debido a problemas técnicos el investigador no se pudo adicionar", ""));
	}
	// return Navegacion.EXITO;
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

    private void borrar_componentes_graficos() {
	System.out.println("borra graficos");
	exitoInsercionInvestigador.setRendered(false);
	formularioCompleto.setRendered(false);
	noexitoInsercionInvestigador.setRendered(false);
	botonGuardar.setRendered(false);
	botonSalir.setRendered(false);
    }

    public List obtenerListaInstituciones(String nombre) {
	List listaA = servicioGeneral.buscarListaDeInstitucionesPorNombre(nombre);
	System.out.println("encontrada " + listaA.size() + " con " + nombre);
	List listaAux = new Vector();
	for (Iterator i = listaA.iterator(); i.hasNext();) {
	    Institucion ins = (Institucion) i.next();
	    // String s= ins.getId()+"-"+ins.getNombre();
	    listaAux.add(ins.getNombre());
	}
	return listaAux;
    }

    public UICommand getBotonGuardar() {
	return botonGuardar;
    }

    public void setBotonGuardar(UICommand botonGuardar) {
	this.botonGuardar = botonGuardar;
    }

    public UICommand getBotonSalir() {
	return botonSalir;
    }

    public void setBotonSalir(UICommand botonSalir) {
	this.botonSalir = botonSalir;
    }

    public CategoriaInvestigador getCategoriaInvestigador() {
	return categoriaInvestigador;
    }

    public void setCategoriaInvestigador(CategoriaInvestigador categoriaInvestigador) {
	this.categoriaInvestigador = categoriaInvestigador;
    }

    public SelectItem[] getCategoriaInvestigadorItem() {
	return categoriaInvestigadorItem;
    }

    public void setCategoriaInvestigadorItem(SelectItem[] categoriaInvestigadorItem) {
	this.categoriaInvestigadorItem = categoriaInvestigadorItem;
    }

    public Ciudad getCiudadActual() {
	return ciudadActual;
    }

    public void setCiudadActual(Ciudad ciudadActual) {
	this.ciudadActual = ciudadActual;
    }

    public SelectItem[] getCiudadDomicilioItem() {
	return ciudadDomicilioItem;
    }

    public void setCiudadDomicilioItem(SelectItem[] ciudadDomicilioItem) {
	this.ciudadDomicilioItem = ciudadDomicilioItem;
    }

    public SelectItem[] getCiudadItem() {
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

    public String getDocumento() {
	return documento;
    }

    public void setDocumento(String documento) {
	this.documento = documento;
    }

    public EstadoCivil getEstadoCivil() {
	return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
	this.estadoCivil = estadoCivil;
    }

    public SelectItem[] getEstadoCivilItem() {
	return estadoCivilItem;
    }

    public void setEstadoCivilItem(SelectItem[] estadoCivilItem) {
	this.estadoCivilItem = estadoCivilItem;
    }

    public UIPanel getExitoInsercionInvestigador() {
	return exitoInsercionInvestigador;
    }

    public void setExitoInsercionInvestigador(UIPanel exitoInsercionInvestigador) {
	this.exitoInsercionInvestigador = exitoInsercionInvestigador;
    }

    public UIPanel getFormularioCompleto() {
	return formularioCompleto;
    }

    public void setFormularioCompleto(UIPanel formularioCompleto) {
	this.formularioCompleto = formularioCompleto;
    }

    public SelectItem[] getGeneroItem() {
	return generoItem;
    }

    public void setGeneroItem(SelectItem[] generoItem) {
	this.generoItem = generoItem;
    }

    public Institucion getInstitucion() {
	return institucion;
    }

    public void setInstitucion(Institucion institucion) {
	this.institucion = institucion;
    }

    public SelectItem[] getInstitucionItem() {
	return institucionItem;
    }

    public void setInstitucionItem(SelectItem[] institucionItem) {
	this.institucionItem = institucionItem;
    }

    public InvestigadorExterno getInvestigadorExterno() {
	return investigadorExterno;
    }

    public void setInvestigadorExterno(InvestigadorExterno investigadorExterno) {
	this.investigadorExterno = investigadorExterno;
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

    public List getListaDepartamentos() {
	return listaDepartamentos;
    }

    public void setListaDepartamentos(List listaDepartamentos) {
	this.listaDepartamentos = listaDepartamentos;
    }

    public List getListaEstadoCivil() {
	return listaEstadoCivil;
    }

    public void setListaEstadoCivil(List listaEstadoCivil) {
	this.listaEstadoCivil = listaEstadoCivil;
    }

    public List getListaGenero() {
	return listaGenero;
    }

    public void setListaGenero(List listaGenero) {
	this.listaGenero = listaGenero;
    }

    public List getListaInstitucion() {
	return listaInstitucion;
    }

    public void setListaInstitucion(List listaInstitucion) {
	this.listaInstitucion = listaInstitucion;
    }

    public List getListaTipoDocumento() {
	return listaTipoDocumento;
    }

    public void setListaTipoDocumento(List listaTipoDocumento) {
	this.listaTipoDocumento = listaTipoDocumento;
    }

    public UISelectOne getManejadorDepartamentos() {
	return manejadorDepartamentos;
    }

    public void setManejadorDepartamentos(UISelectOne manejadorDepartamentos) {
	this.manejadorDepartamentos = manejadorDepartamentos;
    }

    public UIOutput getNoexitoInsercionInvestigador() {
	return noexitoInsercionInvestigador;
    }

    public void setNoexitoInsercionInvestigador(UIOutput noexitoInsercionInvestigador) {
	this.noexitoInsercionInvestigador = noexitoInsercionInvestigador;
    }

    public TipoDocumento getTipoDocumento() {
	return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
	this.tipoDocumento = tipoDocumento;
    }

    public SelectItem[] getTipoDocumentoItem() {
	return tipoDocumentoItem;
    }

    public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
	this.tipoDocumentoItem = tipoDocumentoItem;
    }

    public boolean isInvestigadorExiste() {
	return investigadorExiste;
    }

    public void setInvestigadorExiste(boolean investigadorExiste) {
	this.investigadorExiste = investigadorExiste;
    }

    public String getInsitucionNombre() {
	return insitucionNombre;
    }

    public void setInsitucionNombre(String insitucionNombre) {
	this.insitucionNombre = insitucionNombre;
    }

    public boolean isMostrarBotonSalir() {
        return mostrarBotonSalir;
    }

    public void setMostrarBotonSalir(boolean mostrarBotonSalir) {
        this.mostrarBotonSalir = mostrarBotonSalir;
    }
}
