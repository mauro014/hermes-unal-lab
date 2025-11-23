package co.edu.unal.hermes.vista.asesor;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorCoordinadorAsesor extends ManejadorBase 
{
	private Persona asesor;
	private DataTable  tablaCoordinadoresAsesor;
	private List listaCoordinadoresAsesor;
	private String mensajeTransaccion = "";
	private List listaCoordinadores;
	private Persona coordinador;
	private String docCoordinador;
    private Persona persona;
    private boolean nivelNacional;
    private boolean mostrarSed;

	
	private SelectItem[] coordinadoresItem;
	private String idCoordinadorSeleccionado;
	private IdPersona idCoordinador;
	
	private String errores[];
	private boolean panelRender[];
	private boolean panelRenderError[];
	
	

	private boolean modificar = false;
	
	
	private String idNacional = "1";
	private String idSede="";
	private String idFacultad="";
	  
			
	private SelectItem[] nacionalItem;
	private SelectItem[] sedesItem;
	private SelectItem[] facultadesSedeItem;
	private List listaFacultades;
	private SelectItem[] facultadesItem;
	private List listaSedes;
	
	/*
	 * CONSTRUCTOR DE LA CLASE
	 */
	public ManejadorCoordinadorAsesor()
	{
		reiniciarVariables();
		asesor = (Persona)sesion.getAttribute("persona");
		coordinador = new Persona();
		nivelNacional = false;
		cargarCoordinadoresAsesor();
		//cargarCoordinadoresDisponibles();
		cargarDependencias();
		
		//this.errores[1] = "-";
		this.panelRenderError[1] = false;
	}
	
	/*
	 * CONTROL
	 */
	public void habilitarModificacion()
	{
		modificar = true;
		mensajeTransaccion = "";
		inicializarRegistro();
		limpiarBuscarCoordinador();
	}
	
	public void volver()	
	{
		modificar = false;
		mensajeTransaccion = "";
		inicializarRegistro();
	}
	
	private void cargarCoordinadoresAsesor()
	{
    	listaCoordinadoresAsesor = servicioPersona.obtenerListaCoordinadoresAsesor(asesor.getId());   	
    }
	
	private void cargarCoordinadoresDisponibles()
	{
    	listaCoordinadores = servicioPersona.obtenerListaCoordinadoresNoAsignadosAsesor(asesor.getId());
    	
    	coordinadoresItem = new SelectItem[listaCoordinadores.size()+1];
    	coordinadoresItem[0] = new SelectItem("","");
    	idCoordinadorSeleccionado = "";
    	
    	for(int i = 1; i < listaCoordinadores.size() + 1; i++)
    	{
    		Persona p = (Persona)listaCoordinadores.get(i - 1);
    	  	coordinadoresItem[i] = new SelectItem(p.getId().getTipoDocumento() + "-" + p.getId().getDocumento(), p.getNombre1()+" "+p.getApellido1());
    	}   	
    }
	
	private void cargarDependencias(){
		
		listaSedes = this.servicioGeneral.obtenerSedes();
		sedesItem = new SelectItem[listaSedes.size()];
		for(int i = 0; i < listaSedes.size(); i++)
		{
			Dependencia sede = (Dependencia) listaSedes.get(i);
			sedesItem[i] = new SelectItem(sede.getId(), sede.getNombre());
		}
		/*
		listaFacultades = this.servicioGeneral.obtenerFacultades();
		facultadesItem = new SelectItem[listaFacultades.size() + 1];		
		facultadesItem[0] = new SelectItem("","");
		this.idFacultad = "";
		for(int i = 1; i < listaFacultades.size() + 1; i++)
		{
			Dependencia facultad = (Dependencia) listaFacultades.get(i - 1);
			facultadesItem[i] = new SelectItem(facultad.getId(),facultad.getNombre());
		}		
		facultadesSedeItem = facultadesItem;
		*/
		nacionalItem = new SelectItem[1];
		nacionalItem[0] = new SelectItem("1","Nivel Nacional");
	}
	
	public IdPersona convertirCadenaAIdPersona(String cadena)
	{
		IdPersona id = new IdPersona();
		if(cadena != null && !cadena.equals(""))
		{
			id.setTipoDocumento(cadena.substring(0,cadena.indexOf("-")));
			id.setDocumento(cadena.substring(cadena.indexOf("-") + 1,cadena.length()));			
		}
		else
		{
			id.setTipoDocumento("");
			id.setDocumento("");
		}
		
		return id;
	}
	
	public void guardar()
	{
		try
		{
			if(coordinador.getId().getDocumento() != null && !coordinador.getId().getDocumento().equals(""))
			{
				idCoordinador = coordinador.getId();				
			}
			else
			{
				mensajeTransaccion = "Debe seleccionar el Coordinador a asociar";
				throw new Exception(mensajeTransaccion);
			}
			
			
			String dependencia = "";
			if(nivelNacional){
				dependencia  = idNacional;
			}
			else{
				dependencia  = idSede;
			}
			
			servicioPersona.insertarCoordinadorAsesor(asesor.getId(), idCoordinador, dependencia );
			
			if(mensajeTransaccion.equals(""))
			{
				mensajeTransaccion = "Registro guardado exitosamente";
				modificar = false;
				this.inicializarRegistro();				
			}
			this.cargarCoordinadoresAsesor();
			this.cargarCoordinadoresDisponibles();
			sesion.removeAttribute("manejadorAsignacionProyectos");
		}
		catch(Exception ex)
		{			
			ex.printStackTrace();
		}		
		
	}
	
		
	public void eliminar()
	{
		Persona coordinador = persona;
		servicioPersona.eliminarCoordinadorAsesor(asesor.getId(), coordinador.getId(), coordinador.getCoorIDDependencia());
		/*Persona coordinador = (Persona) tablaCoordinadoresAsesor.getRowIndex());
		servicioPersona.eliminarCoordinadorAsesor(asesor.getId(), coordinador.getId(), coordinador.getCoorIDDependencia());*/
		
	    //	listaCoordinadoresAsesor.remove(tablaCoordinadoresAsesor.getRowIndex());
		
		mensajeTransaccion = "Coordinador borrado con exito";
		
		this.cargarCoordinadoresAsesor();
		this.cargarCoordinadoresDisponibles();
	}
	
	public void inicializarRegistro()
	{
		idCoordinadorSeleccionado = null;
	}

	public void cargarFacultadesSede(ValueChangeEvent valorEvento)
	{
		this.idFacultad = "";
		if(valorEvento.getNewValue().toString() != null && !valorEvento.getNewValue().toString().equals(""))
		{
			this.idSede = valorEvento.getNewValue().toString();
			listaFacultades = this.servicioGeneral.obtenerFacultades(new Dependencia(this.idSede));
			facultadesSedeItem = new SelectItem[listaFacultades.size() + 1];		
			facultadesSedeItem[0] = new SelectItem("","");
			for(int i = 1; i < listaFacultades.size() + 1; i++)
			{
				Dependencia facultad = (Dependencia) listaFacultades.get(i - 1);
				facultadesSedeItem[i] = new SelectItem(facultad.getId(),facultad.getNombre());
			}
		}		
		else
			facultadesSedeItem = facultadesItem;
	}
	
	public void buscarCoordinador(){
		mensajeTransaccion = "";
		coordinador = servicioPersona.obtenerCoordinadorNoAsignadoAsesor(asesor.getId(), docCoordinador);
		if(coordinador.getId().getDocumento() != null){
			this.errores[1] = "Nombre Coordinador: " + coordinador.getNombre1() + " " + coordinador.getNombre2() + " " + coordinador.getApellido1() + " " + coordinador.getApellido2();
			this.panelRenderError[1] = true;
			mensajeTransaccion = "";
		}else{
			if(docCoordinador.equals("")){
			    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR," ", "Por favor ingresar el número de identificación del coordinador"));
				this.errores[1] = "";
				this.panelRenderError[1] = false;
				mensajeTransaccion = "Debe ingresar un documento";
			}else{
			    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR," ", "El coordinador con número de identificación " + docCoordinador + " no existe."));
				this.errores[1] = "";
				this.panelRenderError[1] = false;
				mensajeTransaccion = "No se ha encontrado ningun usuario con esta documento. Verifique el documento o solicite la creación del usuario y la asignación del rol Coordinador. ";
			}
		}
	}
	
	public void limpiarBuscarCoordinador(){
		//reiniciarVariables();
		coordinador = new Persona();
		docCoordinador="";
		mensajeTransaccion = "";
		errores[1] = "";
		this.panelRenderError[1] = false;
	}
	
	private void reiniciarVariables() {
		errores = new String[60];
		panelRender = new boolean[10];
		panelRenderError = new boolean[60];
	}
	
	public Persona getAsesor() {
		return asesor;
	}

	public void setAsesor(Persona asesor) {
		this.asesor = asesor;
	}

	public DataTable getTablaCoordinadoresAsesor() {
		return tablaCoordinadoresAsesor;
	}

	public void setTablaCoordinadoresAsesor(DataTable tablaCoordinadoresAsesor) {
		this.tablaCoordinadoresAsesor = tablaCoordinadoresAsesor;
	}

	public List getListaCoordinadoresAsesor() {
		return listaCoordinadoresAsesor;
	}

	public void setListaCoordinadoresAsesor(List listaCoordinadoresAsesor) {
		this.listaCoordinadoresAsesor = listaCoordinadoresAsesor;
	}

	public String getMensajeTransaccion() {
		return mensajeTransaccion;
	}

	public void setMensajeTransaccion(String mensajeTransaccion) {
		this.mensajeTransaccion = mensajeTransaccion;
	}

	public List getListaCoordinadores() {
		return listaCoordinadores;
	}

	public void setListaCoordinadores(List listaCoordinadores) {
		this.listaCoordinadores = listaCoordinadores;
	}

	public SelectItem[] getCoordinadoresItem() {
		return coordinadoresItem;
	}

	public void setCoordinadoresItem(SelectItem[] coordinadoresItem) {
		this.coordinadoresItem = coordinadoresItem;
	}

	public String getIdCoordinadorSeleccionado() {
		return idCoordinadorSeleccionado;
	}

	public void setIdCoordinadorSeleccionado(String idCoordinadorSeleccionado) {
		this.idCoordinadorSeleccionado = idCoordinadorSeleccionado;
	}

	public IdPersona getIdCoordinador() {
		return idCoordinador;
	}

	public void setIdCoordinador(IdPersona idCoordinador) {
		this.idCoordinador = idCoordinador;
	}

	public boolean isModificar() {
		return modificar;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public String getIdSede() {
		return idSede;
	}

	public void setIdSede(String idSede) {
		this.idSede = idSede;
	}

	public String getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(String idFacultad) {
		this.idFacultad = idFacultad;
	}

	public String getIdNacional() {
		return idNacional;
	}

	public void setIdNacional(String idNacional) {
		this.idNacional = idNacional;
	}

	public SelectItem[] getNacionalItem() {
		return nacionalItem;
	}

	public void setNacionalItem(SelectItem[] nacionalItem) {
		this.nacionalItem = nacionalItem;
	}

	public SelectItem[] getSedesItem() {
		return sedesItem;
	}

	public void setSedesItem(SelectItem[] sedesItem) {
		this.sedesItem = sedesItem;
	}

	public SelectItem[] getFacultadesSedeItem() {
		return facultadesSedeItem;
	}

	public void setFacultadesSedeItem(SelectItem[] facultadesSedeItem) {
		this.facultadesSedeItem = facultadesSedeItem;
	}
	
	public String[] getErrores() {
		return errores;
	}

	public void setErrores(String[] errores) {
		this.errores = errores;
	}

	public boolean[] getPanelRender() {
		return panelRender;
	}

	public void setPanelRender(boolean[] panelRender) {
		this.panelRender = panelRender;
	}

	public boolean[] getPanelRenderError() {
		return panelRenderError;
	}

	public void setPanelRenderError(boolean[] panelRenderError) {
		this.panelRenderError = panelRenderError;
	}
	
	public Persona getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Persona coordinador) {
		this.coordinador = coordinador;
	}
	
	public String getDocCoordinador() {
		return docCoordinador;
	}

	public void setDocCoordinador(String docCoordinador) {
		this.docCoordinador = docCoordinador;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public void setNivelNacional(boolean nivelNacional) {
		this.nivelNacional = nivelNacional;
	}

	public boolean isNivelNacional() {
		return nivelNacional;
	}
	
	public boolean getMostrarSedes(){
		return (coordinador.getId().getDocumento() != null && !coordinador.getId().getDocumento().equals("") && !nivelNacional);
	}
	
	public boolean getMostrarOpciones(){
		return (coordinador.getId().getDocumento() != null && !coordinador.getId().getDocumento().equals(""));
	}
	
	
}
