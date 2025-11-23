package co.edu.unal.hermes.vista.movilidad;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.html.HtmlDataTable;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultarCoordinadoresMovilidadesFacultad extends ManejadorBase {

	private SelectItem[] facultadItem;
	private String facultad;
	private String mensajeFacultad = "Coordinadores asociados a: ";
	private String facultadConsultada = "";
	private List listaFacultad;
	private String tipoBusqueda;
	private boolean  mostrarTabla;
	private String sede;
	private PersonaRol perRol;
	
	private String errores[];
	private boolean panelRender[];
	private boolean panelRenderError[];
	private HtmlDataTable   tablaCoordinadores;
	private HtmlDataTable   tablaCoordinadores2; 
	private List  listaCoordinadores;
	private List  listaCoordinadores2;
	private SelectItem[] tipoDocumentoItem;
	private TipoDocumento tipoDocumento;
	private String documentoCoordinadorB;
	private String mensajeError2 = "";
	private String  mensajeBusCoord = "";
	private String investigadorRes = "";
	private String tipoDocumentoB;
	private InvestigadorInterno inv = new InvestigadorInterno();
	private Persona person = new Persona();

	
	public ManejadorConsultarCoordinadoresMovilidadesFacultad() {

		personaActual = (Persona) sesion.getAttribute("persona");
		IdPersona id = personaActual.getId();
		InvestigadorInterno iiActual = servicioPersona.obtenerInvestigadorInterno(id);
		Sede sSede = iiActual.getDependencia2().getSede();
		sede = sSede.getId().toString();
		
		errores = new String[60];
		panelRender = new boolean[10];
		panelRenderError = new boolean[60];
    	panelRender[1]=false;
    	panelRender[2]=false;
		
		facultadItem = new SelectItem[1];
		facultadItem[0] = new SelectItem("0", "---");
		
		listaFacultad = servicioGeneral.obtenerObjetos("select f from Dependencia f" +
				" where f.esFacultad = 'Y' and f.sede= '" +
				 sede + "'  order by f.nombre");

				facultadItem = new SelectItem[listaFacultad.size()];
				for (int i = 0; i < listaFacultad.size(); i++) {
					Dependencia d = (Dependencia) listaFacultad.get(i);
					facultadItem[i] = new SelectItem(d.getId(), d.getNombre());
				}
		listaCoordinadores = new ArrayList();
		listaCoordinadores2 = new ArrayList();
		
		tipoBusqueda="Todas";
		buscar();
		panelRender[2]=true;
	}

	
	public void cambiarForm(ValueChangeEvent tipoBusq){
		String  tipoB = (String) tipoBusq.getNewValue();
		if(tipoB.equals("Facultad")){
			panelRender[1]=true;
			panelRender[2]=false;
		}
		if(tipoB.equals("Todas")){
			panelRender[2]=true;
			panelRender[1]=false;
			
			String consult = "select i from InvestigadorInterno i, " +
					" PersonaRol pr " +
					" where i.id.documento= pr.documento " +
					" and i.id.tipoDocumento= pr.tipoDocumento " +
					" and pr.nombre = 'MF' and i.dependencia.sede.id = '" + sede + "'" +
					" and i.dependencia.esFacultad = 'Y' and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE) " +
					" order by i.dependencia.nombre";
			
			listaCoordinadores2 = servicioGeneral.obtenerObjetos(consult);
		}
	}
	

	public void cambiarListaFac(ValueChangeEvent facultad){
		String fac = (String) facultad.getNewValue();
		String consult = "select i from InvestigadorInterno i, " +
			" PersonaRol pr " +
			" where i.id.documento= pr.documento " +
			" and i.id.tipoDocumento= pr.tipoDocumento " +
			" and pr.nombre = 'MF' and i.dependencia.id = '" + fac  + "' and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE) ";

			listaCoordinadores = servicioGeneral.obtenerObjetos(consult);
			if(listaCoordinadores.size()>0){
				mostrarTabla = true;
				InvestigadorInterno  facultadConsult = (InvestigadorInterno)listaCoordinadores.get(0);
				facultadConsultada =  mensajeFacultad + facultadConsult.getDependencia().getNombre();
			}else{
				facultadConsultada = "";
			}
	  }
	
	
	public void buscar(){
		
		String consult = "select i from InvestigadorInterno i, " +
		" PersonaRol pr " +
		" where i.id.documento= pr.documento " +
		" and i.id.tipoDocumento= pr.tipoDocumento " +
		" and pr.nombre = 'MF' and i.dependencia.sede.id = '" + sede + "'" +
		" and i.dependencia.esFacultad = 'Y' and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE) " +
		" order by i.dependencia.nombre";

		listaCoordinadores2 = servicioGeneral.obtenerObjetos(consult);
	}
	
	
	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}


	public String getFacultad() {
		return facultad;
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

	public SelectItem[] getFacultadItem() {
		return facultadItem;
	}


	public void setFacultadItem(SelectItem[] facultadItem) {
		this.facultadItem = facultadItem;
	}


	public List getListaFacultad() {
		return listaFacultad;
	}


	public void setListaFacultad(List listaFacultad) {
		this.listaFacultad = listaFacultad;
	}


	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}


	public String getTipoBusqueda() {
		return tipoBusqueda;
	}


	public void setMostrarTabla(boolean mostrarTabla) {
		this.mostrarTabla = mostrarTabla;
	}


	public boolean isMostrarTabla() {
		return mostrarTabla;
	}


	public void setTablaCoordinadores(HtmlDataTable tablaCoordinadores) {
		this.tablaCoordinadores = tablaCoordinadores;
	}


	public HtmlDataTable getTablaCoordinadores() {
		return tablaCoordinadores;
	}


	public void setListaCoordinadores(List listaCoordinadores) {
		this.listaCoordinadores = listaCoordinadores;
	}


	public List getListaCoordinadores() {
		return listaCoordinadores;
	}


	public void setTablaCoordinadores2(HtmlDataTable tablaCoordinadores2) {
		this.tablaCoordinadores2 = tablaCoordinadores2;
	}


	public HtmlDataTable getTablaCoordinadores2() {
		return tablaCoordinadores2;
	}


	public List getListaCoordinadores2() {
		return listaCoordinadores2;
	}


	public void setListaCoordinadores2(List listaCoordinadores2) {
		this.listaCoordinadores2 = listaCoordinadores2;
	}


	public String getFacultadConsultada() {
		return facultadConsultada;
	}


	public void setFacultadConsultada(String facultadConsultada) {
		this.facultadConsultada = facultadConsultada;
	}


	public String getSede() {
		return sede;
	}


	public void setSede(String sede) {
		this.sede = sede;
	}


	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}


	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}


	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}


	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}


	public String getDocumentoCoordinadorB() {
		return documentoCoordinadorB;
	}


	public void setDocumentoCoordinadorB(String documentoCoordinadorB) {
		this.documentoCoordinadorB = documentoCoordinadorB;
	}


	public String getMensajeBusCoord() {
		return mensajeBusCoord;
	}


	public void setMensajeBusCoord(String mensajeBusCoord) {
		this.mensajeBusCoord = mensajeBusCoord;
	}


	public String getInvestigadorRes() {
		return investigadorRes;
	}


	public void setInvestigadorRes(String investigadorRes) {
		this.investigadorRes = investigadorRes;
	}


	public String getTipoDocumentoB() {
		return tipoDocumentoB;
	}


	public void setTipoDocumentoB(String tipoDocumentoB) {
		this.tipoDocumentoB = tipoDocumentoB;
	}


	public InvestigadorInterno getInv() {
		return inv;
	}


	public void setInv(InvestigadorInterno inv) {
		this.inv = inv;
	}


	public void setPerRol(PersonaRol perRol) {
		this.perRol = perRol;
	}


	public PersonaRol getPerRol() {
		return perRol;
	}


	public void setPerson(Persona person) {
		this.person = person;
	}


	public Persona getPerson() {
		return person;
	}

	

}

