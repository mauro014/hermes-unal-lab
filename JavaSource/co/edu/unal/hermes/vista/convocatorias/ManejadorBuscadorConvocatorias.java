/**
 * Modificado por Mauricio
 */

package co.edu.unal.hermes.vista.convocatorias;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.component.UIPanel;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.panelgrid.PanelGrid;

import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.HistoricoBusqueda;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorBuscadorConvocatorias extends ManejadorBase {
	
	private boolean error = false;
	
	private List<ConvocatoriaPadre> listaConvocatorias; 
	
	private int convocatoriaEstado = 0;
	
	private ConvocatoriaPadre convocatoriaSeleccionada;
	
	private int convocatoriaEstadoUsado = 0;

	private Date fechaInicioConvocatoriaUsada;
	
	private Date fechaFinConvocatoriaUsada;

	private UIData tablaConvocatorias;
	
	private String paginaActual;
	
	private PanelGrid panelEstadoConvocatoria;
	
	private UIPanel panelPaginaInicial;
	
	private UIPanel panelResultados;	
	
	private UIPanel panelBotonBuscar;	
	
	private UIPanel panelBotonAgregar;	
	
	private String nombreConvocatoriaBusqueda;
	
	private int opcion = 0;
	
	//Opciones
	private String sedeAgregar;
	private String facultadAgregar;
	private String estadoAgregar;
	
	private List<Dependencia> listaSedes;
	private List<Dependencia> listaFacultades;
	private SelectItem[] listaSedesItem;
	private SelectItem[] listaFacultadesItem;
	
	
	private List<Dependencia> listaFacultadesFiltro;
	private List<Dependencia> listaSedesFiltro;
	private List<String> listaEstadosFiltro;
	
	private Dependencia sedeSeleccionada;
	private Dependencia facultadSeleccionada;
	private String estadoSeleccionado;
	
	private Boolean mostrarAgregarSede;
	private Boolean mostrarAgregarFacultad;
	private Boolean mostrarAgregarEstado;
	private Boolean visibleResultados;
	
	private HistoricoBusqueda historicoBusqueda;
	private final int TIPOCONVOCATORIA = 6;

	
	public ManejadorBuscadorConvocatorias() {		
		
		mostrarAgregarSede = false;
		mostrarAgregarFacultad = false;	
		mostrarAgregarEstado = false;
		this.nombreConvocatoriaBusqueda = "";
		visibleResultados = false;

		if(!error){
			cargarPaginaActual();
		}
	}
	
	public void desactivarAgregarGeneral(){
		mostrarAgregarFacultad = false;
		mostrarAgregarSede = false;
		mostrarAgregarEstado = false;
		
	}
	
	public void cargarNuevaSede(){
		desactivarAgregarGeneral();
		mostrarAgregarSede = true;
		sedeAgregar = "";
		cargarOpcionesSedes();
	}
	
	public void cargarNuevaFacultad(){
		desactivarAgregarGeneral();
		mostrarAgregarFacultad = true;
		facultadAgregar = "";
		cargarOpcionesSedes();
	}
	
	public void cargarFacultadesSede() {
		facultadAgregar  = "";
		if (sedeAgregar != null && !sedeAgregar.toString().equals("")) {
			listaFacultades = this.servicioGeneral.obtenerFacultades(new Dependencia(sedeAgregar));
			listaFacultadesItem = new SelectItem[listaFacultades.size() + 1];
			listaFacultadesItem[0] = new SelectItem("", "Seleccione facultad");
			for (int i = 1; i < listaFacultades.size() + 1; i++) {
				Dependencia facultad = (Dependencia) listaFacultades.get(i - 1);
				listaFacultadesItem[i] = new SelectItem(facultad.getId(),facultad.getNombre());
			}
		} else
			listaFacultadesItem = null;
	}
	
	public void cargarNuevoEstado(){
		desactivarAgregarGeneral();
		mostrarAgregarEstado = true;
		estadoAgregar = "";
	}
	
	private void cargarOpcionesSedes(){
		if(listaSedesItem != null && listaSedesItem.length > 0){
			return;
		}
		else{
			listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
			listaSedesItem = new SelectItem[listaSedes.size() + 1];
			listaSedesItem[0] = new SelectItem("", "Seleccione sede");
			for (int i = 1; i < listaSedes.size() +1 ; i++) {
				Dependencia sede = (Dependencia) listaSedes.get(i - 1);
				String nombreSede = sede.getNombre().substring(0, 1).toUpperCase() + sede.getNombre().substring(1, sede.getNombre().length());
				listaSedesItem[i] = new SelectItem(sede.getId(), nombreSede);
			}
		}
	}
	
	public void reiniciarBusqueda(){
		sedeAgregar = "";
		facultadAgregar = "";
		estadoAgregar="";
		visibleResultados = false;
		nombreConvocatoriaBusqueda = "";
		listaSedesFiltro = new ArrayList<Dependencia>();
		listaFacultadesFiltro = new ArrayList<Dependencia>();
		listaEstadosFiltro = new ArrayList<String>();
		desactivarAgregarGeneral();
		cargarPaginaActual();
	}

	public int getConvocatoriaEstado() {
		return convocatoriaEstado;
	}

	public void setConvocatoriaEstado(int convocatoriaEstado) {
		this.convocatoriaEstado = convocatoriaEstado;
	}

	public UIData getTablaConvocatorias() {
		return tablaConvocatorias;
	}

	public void setTablaConvocatorias(UIData tablaConvocatorias) {
		this.tablaConvocatorias = tablaConvocatorias;
	}

	private SelectItem[] estadoItem = { 
			new SelectItem(new String("0"), "Seleccione estado"),
			new SelectItem(new String("Activa"), "Activa"),
			new SelectItem(new String("Inactiva"), "Inactiva")
	};

	public void setEstadoItem(SelectItem[] estadoItem) {
		this.estadoItem = estadoItem;
	}

	public SelectItem[] getEstadoItem() {
		return estadoItem;
	}
	
	public void setError(boolean error) {
		this.error = error;
	}

	public boolean isError() {
		return error;
	}
	
	public UIData getTablaGrupos() {
		return tablaConvocatorias;
	}

	public void setTablaGrupos(UIData tablaGrupos) {
		this.tablaConvocatorias = tablaGrupos;
	}
	
	public void setPanelBotonBuscar(UIPanel panelBotonBuscar) {
		this.panelBotonBuscar = panelBotonBuscar;
	}

	public UIPanel getPanelBotonBuscar() {
		return panelBotonBuscar;
	}

	public void setPanelBotonAgregar(UIPanel panelBotonAgregar) {
		this.panelBotonAgregar = panelBotonAgregar;
	}

	public UIPanel getPanelBotonAgregar() {
		return panelBotonAgregar;
	}
	
	private void cargarPaginaActual(){
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		
		String viewId = "/pages/Consultas/";
		
		viewId = extContext.getRequestContextPath() + viewId ;
		
		this.paginaActual = context.getExternalContext().encodeActionURL(viewId);
	}

	public void setPaginaActual(String paginaActual) {
		this.paginaActual = paginaActual;
	}

	public String getPaginaActual() {
		return paginaActual;
	}
	
	public void setPanelPaginaInicial(UIPanel panelPaginaInicial) {
		this.panelPaginaInicial = panelPaginaInicial;
	}

	public UIPanel getPanelPaginaInicial() {
		return panelPaginaInicial;
	}

	public void setPanelResultados(UIPanel panelResultados) {
		this.panelResultados = panelResultados;
	}

	public UIPanel getPanelResultados() {
		return panelResultados;
	}

	public PanelGrid  getPanelEstadoConvocatoria() {
		return panelEstadoConvocatoria;
	}

	public void setPanelEstadoConvocatoria(PanelGrid  panelEstadoConvocatoria) {
		this.panelEstadoConvocatoria = panelEstadoConvocatoria;
	}
	
	public String getNombreConvocatoriaBusqueda() {
		return nombreConvocatoriaBusqueda;
	}

	public void setNombreConvocatoriaBusqueda(String nombreConvocatoriaBusqueda) {
		this.nombreConvocatoriaBusqueda = nombreConvocatoriaBusqueda;
	}
	
	public void busqueda(){
		
		historicoBusqueda = new HistoricoBusqueda();
		historicoBusqueda.setFechaBusqueda(new Date());
		historicoBusqueda.setCategoria(String.valueOf(TIPOCONVOCATORIA));
		
		if(mostrarAgregarSede){
			if(!sedeAgregar.trim().equals("")){
				boolean esta = false;
				if(listaSedesFiltro == null){
					listaSedesFiltro = new ArrayList<Dependencia>();
				}
				else{
					for(Dependencia sedeBucle: listaSedesFiltro){
						if(sedeBucle.getId().trim().equals(sedeAgregar.trim())){
							esta = true;
							break;
						}
					}
				}
				if(!esta){
					Dependencia sede = encontrarSede(sedeAgregar);
					if(sede != null){
						listaSedesFiltro.add(sede);
					}
				}
			}
			mostrarAgregarSede = false;
		}
		
		if(mostrarAgregarFacultad){
			if(!facultadAgregar.trim().equals("")){
				boolean esta = false;
				if(listaFacultadesFiltro == null){
					listaFacultadesFiltro = new ArrayList<Dependencia>();
				}
				else{
					for(Dependencia facultadBucle: listaFacultadesFiltro){
						if(facultadBucle.getId().trim().equals(facultadAgregar.trim())){
							esta = true;
							break;
						}
					}
				}
				if(!esta){
					Dependencia facultad = encontrarFacultad(facultadAgregar);
					if(facultad != null){
						listaFacultadesFiltro.add(facultad);
					}
				}
			}
			mostrarAgregarFacultad = false;
		}
		
		if(mostrarAgregarEstado){
			if(!estadoAgregar.trim().equals("") && !estadoAgregar.trim().equals("0")){
				boolean esta = false;
				if(listaEstadosFiltro == null){
					listaEstadosFiltro = new ArrayList<String>();
				}
				else{
					for(String areaBucle: listaEstadosFiltro){
						if(areaBucle.trim().equals(estadoAgregar.trim())){
							esta = true;
							break;
						}
					}
				}
				if(!esta){
					String nuevoEstado = estadoAgregar;
					listaEstadosFiltro.add(nuevoEstado);
				}
				mostrarAgregarEstado = false;
				estadoAgregar = "";
			}
		}
		
		visibleResultados = true;
		String fromSql = "";
		String sql = "";
		try {
			
			if(listaEstadosFiltro != null && listaEstadosFiltro.size() > 0){
				if (sql.length() > 0) {
					sql = sql + " and ";
				}
				sql += " ( ";
				boolean primero = true;
				for(String estadoBusqueda : listaEstadosFiltro ){ 
					if(!primero) {
						sql += " or ";
					}
					primero = false;
					String estado = "";
					if(estadoBusqueda.equals("Activa"))
						estado = "A";
					if(estadoBusqueda.equals("Inactiva"))
						estado = "I";
					sql += " HER_CONVOCATORIA_PADRE.CNP_ESTADO = '"+ estado +"' ";
				}
				sql += " ) ";
			}

			if(this.nombreConvocatoriaBusqueda.length() > 0){
				historicoBusqueda.setPalabra(nombreConvocatoriaBusqueda);
				if(sql.length()>0){
					sql += " and ";
				}
				sql += " upper(HER_CONVOCATORIA_PADRE.CNP_TITULO) LIKE '%"+this.nombreConvocatoriaBusqueda.toUpperCase()+"%' ";
			}
			
			if(listaSedesFiltro != null && listaSedesFiltro.size() > 0 && (listaFacultadesFiltro == null || listaFacultadesFiltro.size() == 0)){
				if (sql.length() > 0) {
					sql = sql + " and ";
				}
				sql += " ( ";
				boolean primero = true;
				for(Dependencia sede : listaSedesFiltro ){ 
					if(!primero) {
						sql += " or ";
					}
					primero = false;
					sql += " HER_CONVOCATORIA_PADRE.DNP_ID = '" + sede.getId()+"'";
				}
				sql += " ) ";
			}
			
			if(listaFacultadesFiltro != null && listaFacultadesFiltro.size() > 0){
				if (sql.length() > 0) {
					sql = sql + " and ";
				}
				sql += " ( ";
				boolean primero = true;
				for(Dependencia facultad : listaFacultadesFiltro ){
					if(!primero) {
						sql += " or ";
					}
					primero = false;
					sql += " HER_CONVOCATORIA_PADRE.DNP_ID = '" + facultad.getId()+"'";
				}
				sql += " ) ";
			}

			this.listaConvocatorias = servicioGeneral.obtenerConvocatoriasBuscador(sql,fromSql,false);
			visibleResultados = true;
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		servicioGeneral.guardarObjeto(historicoBusqueda);
		
	}
	
	public Dependencia encontrarSede(String id){
		if(listaSedes != null){
			for(Dependencia sede: listaSedes){
				if(sede.getId().equals(id)){
					return sede;
				}
			}
		}
		return null;
	}
	
	public Dependencia encontrarFacultad(String id){
		if(listaFacultades != null){
			for(Dependencia facultad: listaFacultades){
				if(facultad.getId().equals(id)){
					return facultad;
				}
			}
		}
		return null;
	}
	
	public void eliminarSede(){
		reiniciarBusqueda();
		Dependencia sedeActual = sedeSeleccionada;
		listaSedesFiltro.remove(sedeActual);
		busqueda();
	}
	
	public void eliminarFacultad(){
		reiniciarBusqueda();
		Dependencia facultadActual = facultadSeleccionada;
		listaFacultadesFiltro.remove(facultadActual);
		busqueda();
	}
	
	public void eliminarEstado(){
		reiniciarBusqueda();
		listaEstadosFiltro.remove(estadoSeleccionado);
		busqueda();
	}

	public void setListaConvocatorias(List<ConvocatoriaPadre> listaConvocatorias) {
		this.listaConvocatorias = listaConvocatorias;
	}

	public List<ConvocatoriaPadre> getListaConvocatorias() {
		return listaConvocatorias;
	}
	
	public String consultarPagina() {
		
		//ConvocatoriaPadre convocatoriaPadre = (ConvocatoriaPadre)tablaConvocatorias.getRowData();
		sesion.removeAttribute("manejadorConvocatoriaBusqueda");
		ConvocatoriaPadre convocatoriaPadre = convocatoriaSeleccionada;
		
		if(convocatoriaPadre.getVinculo()!=null){
			String url = convocatoriaPadre.getVinculo()+"#convocatoria";

			    try {
					FacesContext.getCurrentInstance().getExternalContext().redirect(url);
				} catch (IOException e) {
					e.printStackTrace();
					return "";
				}
			    return "";
		}
		else{
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext extContext = context.getExternalContext();
			String viewId = "/pages/Consultas/Convocatoria.xhtml";
			
			try {
				viewId = extContext.getRequestContextPath() + viewId + '?'
				+ "idConvocatoria" + "=" + convocatoriaPadre.getId();
				String urlLink = context.getExternalContext().encodeActionURL(
						viewId);
				extContext.redirect(urlLink);
			} catch (IOException e) {
				extContext.log(getClass().getName() + ".invokeRedirect", e);
			}
		}
		
		return null;
	}

	public String getEstadoConvocatoriaUsado(){
		if(convocatoriaEstadoUsado == 1) return "Activo";
		else if(convocatoriaEstadoUsado == 2) return "Inactivo";
		else return "";
	}
	

	public int getNumeroResultados(){
		if(listaConvocatorias != null){
			return listaConvocatorias.size();
		}
		return 0;
	}
	
	public boolean getOpcion1(){
		if(opcion == 1)return true;
		return false;
	}
	
	public boolean getOpcion2(){
		if(opcion == 2)return true;
		return false;
	}
	
	public boolean getOpcion3(){
		if(opcion == 3)return true;
		return false;
	}
	
	public boolean getOpcionAgregar(){
		if(opcion != 0)return true;
		return false;
	}
	
	public boolean getOpcionBuscar(){
		if(opcion == 0)return true;
		return false;
	}

	

	public Date getFechaInicioConvocatoriaUsada() {
		return fechaInicioConvocatoriaUsada;
	}

	public void setFechaInicioConvocatoriaUsada(Date fechaInicioConvocatoriaUsada) {
		this.fechaInicioConvocatoriaUsada = fechaInicioConvocatoriaUsada;
	}

	public Date getFechaFinConvocatoriaUsada() {
		return fechaFinConvocatoriaUsada;
	}

	public void setFechaFinConvocatoriaUsada(Date fechaFinConvocatoriaUsada) {
		this.fechaFinConvocatoriaUsada = fechaFinConvocatoriaUsada;
	}

	public void setConvocatoriaSeleccionada(ConvocatoriaPadre convocatoriaSeleccionada) {
		this.convocatoriaSeleccionada = convocatoriaSeleccionada;
	}

	public ConvocatoriaPadre getConvocatoriaSeleccionada() {
		return convocatoriaSeleccionada;
	}

	public String getSedeAgregar() {
		return sedeAgregar;
	}

	public void setSedeAgregar(String sedeAgregar) {
		this.sedeAgregar = sedeAgregar;
	}

	public String getFacultadAgregar() {
		return facultadAgregar;
	}

	public void setFacultadAgregar(String facultadAgregar) {
		this.facultadAgregar = facultadAgregar;
	}

	public SelectItem[] getListaSedesItem() {
		return listaSedesItem;
	}

	public void setListaSedesItem(SelectItem[] listaSedesItem) {
		this.listaSedesItem = listaSedesItem;
	}

	public SelectItem[] getListaFacultadesItem() {
		return listaFacultadesItem;
	}

	public void setListaFacultadesItem(SelectItem[] listaFacultadesItem) {
		this.listaFacultadesItem = listaFacultadesItem;
	}

	public List<Dependencia> getListaFacultadesFiltro() {
		return listaFacultadesFiltro;
	}

	public void setListaFacultadesFiltro(List<Dependencia> listaFacultadesFiltro) {
		this.listaFacultadesFiltro = listaFacultadesFiltro;
	}

	public List<Dependencia> getListaSedesFiltro() {
		return listaSedesFiltro;
	}

	public void setListaSedesFiltro(List<Dependencia> listaSedesFiltro) {
		this.listaSedesFiltro = listaSedesFiltro;
	}

	public List<String> getListaEstadosFiltro() {
		return listaEstadosFiltro;
	}

	public void setListaEstadosFiltro(List<String> listaEstadosFiltro) {
		this.listaEstadosFiltro = listaEstadosFiltro;
	}

	public Dependencia getSedeSeleccionada() {
		return sedeSeleccionada;
	}

	public void setSedeSeleccionada(Dependencia sedeSeleccionada) {
		this.sedeSeleccionada = sedeSeleccionada;
	}

	public Dependencia getFacultadSeleccionada() {
		return facultadSeleccionada;
	}

	public void setFacultadSeleccionada(Dependencia facultadSeleccionada) {
		this.facultadSeleccionada = facultadSeleccionada;
	}

	public String getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	public void setEstadoSeleccionado(String estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	public Boolean getMostrarAgregarSede() {
		return mostrarAgregarSede;
	}

	public void setMostrarAgregarSede(Boolean mostrarAgregarSede) {
		this.mostrarAgregarSede = mostrarAgregarSede;
	}

	public Boolean getMostrarAgregarFacultad() {
		return mostrarAgregarFacultad;
	}

	public void setMostrarAgregarFacultad(Boolean mostrarAgregarFacultad) {
		this.mostrarAgregarFacultad = mostrarAgregarFacultad;
	}

	public Boolean getMostrarAgregarEstado() {
		return mostrarAgregarEstado;
	}

	public void setMostrarAgregarEstado(Boolean mostrarAgregarEstado) {
		this.mostrarAgregarEstado = mostrarAgregarEstado;
	}

	public List<Dependencia> getListaFacultades() {
		return listaFacultades;
	}

	public void setListaFacultades(List<Dependencia> listaFacultades) {
		this.listaFacultades = listaFacultades;
	}
	
	public List<Dependencia> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(List<Dependencia> listaSedes) {
		this.listaSedes = listaSedes;
	}

	public String getEstadoAgregar() {
		return estadoAgregar;
	}

	public void setEstadoAgregar(String estadoAgregar) {
		this.estadoAgregar = estadoAgregar;
	}

	public Boolean getVisibleResultados() {
		return visibleResultados;
	}

	public void setVisibleResultados(Boolean visibleResultados) {
		this.visibleResultados = visibleResultados;
	}

	public HistoricoBusqueda getHistoricoBusqueda() {
		return historicoBusqueda;
	}

	public void setHistoricoBusqueda(HistoricoBusqueda historicoBusqueda) {
		this.historicoBusqueda = historicoBusqueda;
	}

	
}
