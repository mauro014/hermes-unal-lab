/**
 * Modificado por Mauricio
 */

package co.edu.unal.hermes.vista.laboratorios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.component.UIPanel;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEnsayosServicios;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorBuscadorEnsayos extends ManejadorBase {
	
	private LaboratorioDetalleEnsayosServicios ensayoSeleccionado; 
	
	private boolean error = false;
	
	private String paginaActual;
	
	private String nombreEnsayoBusqueda;
	
	private UIPanel panelBotonBuscar;	
	
	private UIPanel panelBotonAgregar;	
	
	private boolean primeraVez;

	private HtmlDataTable tablaSedesSeleccionadas;
	
	private UIPanel panelPaginaInicial;
	
	private UIPanel panelSedeEnsayo;
	
	private UIData tablaEnsayos;
	
	private SelectItem[] facultadesItem;
	
	private Dependencia sedeSeleccionada;
	
	private Dependencia facultadSeleccionada;
	
	private String idSede;
	
	private UIPanel panelFacultadEnsayo;

	private List<Dependencia> facultadesSeleccionadas;
	
	private String idFacultad;
	
	private UIPanel panelResultados;	

	private List<Dependencia> sedesSeleccionadas;
	
	private List<Dependencia> listaFacultades;

	private SelectItem[] facultadesSedeItem;
	
	private SelectItem[] listaSedesItem;

	private List<Dependencia> listaSedes;

	private HtmlDataTable tablaFacultadesSeleccionadas;
	
	private List<LaboratorioDetalleEnsayosServicios> listaEnsayos; 
	
	int opcion;
	
	public ManejadorBuscadorEnsayos() {
		
		opcion = 0;

		sedesSeleccionadas = new ArrayList<Dependencia>();
		
		idSede = "";
	
		primeraVez = true;
		
		idFacultad = "";
		
		facultadesSedeItem = new SelectItem[1];
		
		facultadesSedeItem[0] = new SelectItem("", "");
		
		facultadesSeleccionadas = new ArrayList<Dependencia>();
		
		listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
		listaSedesItem = new SelectItem[listaSedes.size() + 1];
		listaSedesItem[0] = new SelectItem("", "");
		
		for (int i = 1; i < listaSedes.size() + 1; i++) {
			Dependencia sede = (Dependencia) listaSedes.get(i - 1);
			listaSedesItem[i] = new SelectItem(sede.getId(), sede.getNombre().trim().toUpperCase());
		}
		
		this.panelPaginaInicial = new UIPanel();
		this.panelResultados = new UIPanel();
		this.panelBotonBuscar = new UIPanel();
		this.panelBotonAgregar = new UIPanel();

		if(!error){
			cargarPaginaActual();
		}
		
	}
	
	public String getNombreEnsayoBusqueda() {
		return nombreEnsayoBusqueda;
	}

	public void setNombreEnsayoBusqueda(String nombreEnsayoBusqueda) {
		this.nombreEnsayoBusqueda = nombreEnsayoBusqueda;
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

	public void setPanelPaginaInicial(UIPanel panelPaginaInicial) {
		this.panelPaginaInicial = panelPaginaInicial;
	}

	public UIPanel getPanelPaginaInicial() {
		return panelPaginaInicial;
	}

	public void cargarSede(){
		opcion = 1;
	}
	
	public void cargarFacultad(){
		idSede = "";
		idFacultad = "";
		facultadesSedeItem = new SelectItem[1];
		facultadesSedeItem[0] = new SelectItem("", "");
		opcion = 2;
	}
	
	public UIPanel getPanelSedeEnsayo() {
		return panelSedeEnsayo;
	}

	public void setPanelSedeEnsayo(UIPanel panelSedeEnsayo) {
		this.panelSedeEnsayo = panelSedeEnsayo;
	}

	public String getIdSede() {
		return idSede;
	}

	public void setIdSede(String idSede) {
		this.idSede = idSede;
	}
	
	public void cargarFacultadesSede(ValueChangeEvent valorEvento) {
		this.setIdFacultad("");
		if (valorEvento.getNewValue().toString() != null && !valorEvento.getNewValue().toString().equals("")) {
			this.idSede = valorEvento.getNewValue().toString();
			listaFacultades = this.servicioGeneral.obtenerFacultades(new Dependencia(this.idSede));
			facultadesSedeItem = new SelectItem[listaFacultades.size() + 1];
			facultadesSedeItem[0] = new SelectItem("", "");
			for (int i = 1; i < listaFacultades.size() + 1; i++) {
				Dependencia facultad = (Dependencia) listaFacultades.get(i - 1);
				facultadesSedeItem[i] = new SelectItem(facultad.getId(),facultad.getNombre());
			}
		} else
			facultadesSedeItem = facultadesItem;
	}
	
	public String getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(String idFacultad) {
		this.idFacultad = idFacultad;
	}
	
	public List<Dependencia> getListaFacultades() {
		return listaFacultades;
	}

	public void setListaFacultades(List<Dependencia> listaFacultades) {
		this.listaFacultades = listaFacultades;
	}
	
	public SelectItem[] getFacultadesItem() {
		return facultadesItem;
	}

	public void setFacultadesItem(SelectItem[] facultadesItem) {
		this.facultadesItem = facultadesItem;
	}

	public SelectItem[] getFacultadesSedeItem() {
		return facultadesSedeItem;
	}

	public void setFacultadesSedeItem(SelectItem[] facultadesSedeItem) {
		this.facultadesSedeItem = facultadesSedeItem;
	}

	public SelectItem[] getListaSedesItem() {
		return listaSedesItem;
	}

	public void setListaSedesItem(SelectItem[] listaSedesItem) {
		this.listaSedesItem = listaSedesItem;
	}

	public List<Dependencia> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(List<Dependencia> listaSedes) {
		this.listaSedes = listaSedes;
	}
	
	public UIPanel getPanelFacultadGrupo() {
		return panelFacultadEnsayo;
	}

	public void setPanelFacultadGrupo(UIPanel panelFacultadGrupo) {
		this.panelFacultadEnsayo = panelFacultadGrupo;
	}
	
	public void ocultarPaneles(){
		idSede = "";
		idFacultad = "";
		opcion = 0;
	}
	
	public void eliminarFacultades(){
		Dependencia facultadActual = facultadSeleccionada;
		facultadesSeleccionadas.remove(facultadActual);
		busqueda();
	}
	
	public HtmlDataTable getTablaFacultadesSeleccionadas() {
		return tablaFacultadesSeleccionadas;
	}

	public void setTablaFacultadesSeleccionadas(
			HtmlDataTable tablaFacultadesSeleccionadas) {
		this.tablaFacultadesSeleccionadas = tablaFacultadesSeleccionadas;
	}
	
	public boolean getVisibleSedesFiltro(){
		if(sedesSeleccionadas.size() > 0)
			return true;
		return false;
	}

	public boolean getVisibleFacultadesFiltro(){
		if(facultadesSeleccionadas.size() > 0)
			return true;
		return false;
	}
	
	public void limpiarFiltroSede(){
		sedesSeleccionadas = new ArrayList<Dependencia>();
		busqueda();
	}

	public void limpiarFiltroFacultad(){
		facultadesSeleccionadas = new ArrayList<Dependencia>();
		busqueda();
	}
	
	public HtmlDataTable getTablaSedesSeleccionadas() {
		return tablaSedesSeleccionadas;
	}

	public void setTablaSedesSeleccionadas(HtmlDataTable tablaSedesSeleccionadas) {
		this.tablaSedesSeleccionadas = tablaSedesSeleccionadas;
	}

	public void eliminarSede(){
		Dependencia sedeActual = sedeSeleccionada;
		sedesSeleccionadas.remove(sedeActual);
		busqueda();
	}
	
	public boolean getVisibleFiltros(){
		if(getVisibleSedesFiltro() || getVisibleFacultadesFiltro() ){
			return true;
		}
		return false;
	}

	public List<Dependencia> getSedesSeleccionadas() {
		return sedesSeleccionadas;
	}

	public void setSedesSeleccionadas(List<Dependencia> sedesSeleccionadas) {
		this.sedesSeleccionadas = sedesSeleccionadas;
	}
	
	public List<Dependencia> getFacultadesSeleccionadas() {
		return facultadesSeleccionadas;
	}

	public void setFacultadesSeleccionadas(List<Dependencia> facultadesSeleccionadas) {
		this.facultadesSeleccionadas = facultadesSeleccionadas;
	}

	public void buscarEnsayos(){
		if (this.idSede.length() > 0 ) {
			for(Dependencia sede : listaSedes ){
				if(sede.getId().trim().equals(this.idSede.trim())){
					boolean esta = false;
					for(Dependencia sedeBucle: sedesSeleccionadas){
						if(sedeBucle.getId().trim().equals(sede.getId().trim())){
							esta = true;
						}
					}
					if(!esta){
						this.sedesSeleccionadas.add(sede);
					}
				}
			}
		}
		if(this.idFacultad.length() > 0 ){
			for(Dependencia facultad : listaFacultades ){
				if(facultad.getId().trim().equals(this.idFacultad.trim())){
					boolean esta = false;
					for(Dependencia facultadBucle: facultadesSeleccionadas){
						if(facultadBucle.getId().trim().equals(facultad.getId().trim())){
							esta = true;
						}
					}
					if(!esta){
						this.facultadesSeleccionadas.add(facultad);
					}
				}
			}
		}
		busqueda();		
	}
	
	public void busqueda(){
		String sql = "";
		String sql2 = "";
		primeraVez = false;
		try {
			if(this.sedesSeleccionadas.size() > 0){
				if (sql.length() > 0) {
					sql = sql + " and ";
				}
				sql += " ( ";
				boolean primero = true;
				for(Dependencia sede : sedesSeleccionadas ){ 
					if(!primero) {
						sql += " or ";
					}
					primero = false;
					sql += " l.SED_ID = '" + sede.getId()+"' ";
				}
				sql += " ) ";
			}
			if(this.facultadesSeleccionadas.size() > 0){
				if (sql.length() > 0) {
					sql = sql + " and ";
				}
				sql += " ( ";
				boolean primero = true;
				for(Dependencia facultad : facultadesSeleccionadas ){
					if(!primero) {
						sql += " or ";
					}
					primero = false;
					sql += " l.LAB_FACULTAD= " + facultad.getId();
				}
				sql += " ) ";
			}
			if(this.nombreEnsayoBusqueda.length() > 0){
				if(sql.length()>0){
					sql += " and ";
				}
				List<String> cadena = ReemplazaAcentos.listaPalabrasConTildes(this.nombreEnsayoBusqueda.toUpperCase());
				String palabra2 = ReemplazaAcentos.quitarTildes(this.nombreEnsayoBusqueda.toUpperCase());
				cadena.add(palabra2);
				sql2 = sql;
				if(cadena.size() > 0){
					sql += "(";
					sql2 += "(de.LDE_ESPECIALIZADO = 1 AND de.LDE_EN_USO = 1 AND (";
					boolean primera = true;
					for(String palabra : cadena){
						if(!primera){
							sql += " or ";
							sql2 += " or ";
						}
						else primera = false;
						sql += " upper(de.LDENS_NOMBRE) LIKE '%"+palabra+"%' OR " +
								"upper(l.LAB_NOMBRE) LIKE '%"+palabra+"%' OR "+
								"upper(l.LAB_DESCRIPCION) LIKE '%"+palabra+"%' ";
						sql2 += " upper(de.LDE_EQUIPO) LIKE '%"+palabra+"%'";
					}
					sql += ")";
					sql2 += "))";
				}
			}
			this.listaEnsayos = servicioGeneral.obtenerEnsayosLaboratorioBuscador(sql,sql2,"",this.sedesSeleccionadas,this.facultadesSeleccionadas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ocultarPaneles();		
	}	

	public List<LaboratorioDetalleEnsayosServicios> getListaEnsayos() {
		return listaEnsayos;
	}

	public void setListaEnsayos(
			List<LaboratorioDetalleEnsayosServicios> listaEnsayos) {
		this.listaEnsayos = listaEnsayos;
	}

	public void setPanelResultados(UIPanel panelResultados) {
		this.panelResultados = panelResultados;
	}

	public UIPanel getPanelResultados() {
		return panelResultados;
	}

	public int getNumeroResultados(){
		if(listaEnsayos != null){
			return listaEnsayos.size();
		}
		return 0;
	}
	
	public UIData getTablaEnsayos() {
		return tablaEnsayos;
	}

	public void setTablaEnsayos(UIData tablaEnsayos) {
		this.tablaEnsayos = tablaEnsayos;
	}
	
	private void cargarPaginaActual(){
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		
		String viewId = "/pages/Consultas/";
		
		viewId = extContext.getRequestContextPath() + viewId ;
		
		this.setPaginaActual(context.getExternalContext().encodeActionURL(viewId));
	}

	public void setPaginaActual(String paginaActual) {
		this.paginaActual = paginaActual;
	}

	public String getPaginaActual() {
		return paginaActual;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public UIPanel getPanelFacultadEnsayo() {
		return panelFacultadEnsayo;
	}

	public void setPanelFacultadEnsayo(UIPanel panelFacultadEnsayo) {
		this.panelFacultadEnsayo = panelFacultadEnsayo;
	}
	
	public String consultarPagina() {
		
		LaboratorioDetalleEnsayosServicios ensayoAcual = ensayoSeleccionado;

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		String viewId = "/pages/Consultas/EnsayoLaboratorio.xhtml";
		try {
			viewId = extContext.getRequestContextPath() + viewId + '?'
					+ "idEnsayo" + "=" + ensayoAcual.getId() + "&opcion=1";
			String urlLink = context.getExternalContext().encodeActionURL(
					viewId);
			extContext.redirect(urlLink);
		} catch (IOException e) {
			extContext.log(getClass().getName() + ".invokeRedirect", e);
		}
		return null;

	}
	
	public void cargarFacultadesSede() {
		this.setIdFacultad("");
		if (idSede.toString() != null && !idSede.toString().equals("")) {
			listaFacultades = this.servicioGeneral.obtenerFacultades(new Dependencia(this.idSede));
			facultadesSedeItem = new SelectItem[listaFacultades.size() + 1];
			facultadesSedeItem[0] = new SelectItem("", "");
			for (int i = 1; i < listaFacultades.size() + 1; i++) {
				Dependencia facultad = (Dependencia) listaFacultades.get(i - 1);
				facultadesSedeItem[i] = new SelectItem(facultad.getId(),facultad.getNombre());
			}
		} else
			facultadesSedeItem = facultadesItem;
	}
	
	public boolean getOpcion1(){
		if(opcion == 1 || opcion == 2)return true;
		return false;
	}
	
	public boolean getOpcion2(){
		if(opcion == 2)return true;
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
	
	public boolean getVisibleResultados(){
		if(listaEnsayos != null){
			if(listaEnsayos.size() > 0)	{
				return false;
			}		
		}
		return true;
	}

	public void setSedeSeleccionada(Dependencia sedeSeleccionada) {
		this.sedeSeleccionada = sedeSeleccionada;
	}

	public Dependencia getSedeSeleccionada() {
		return sedeSeleccionada;
	}

	public void setFacultadSeleccionada(Dependencia facultadSeleccionada) {
		this.facultadSeleccionada = facultadSeleccionada;
	}

	public Dependencia getFacultadSeleccionada() {
		return facultadSeleccionada;
	}

	public void setEnsayoSeleccionado(LaboratorioDetalleEnsayosServicios ensayoSeleccionado) {
		this.ensayoSeleccionado = ensayoSeleccionado;
	}

	public LaboratorioDetalleEnsayosServicios getEnsayoSeleccionado() {
		return ensayoSeleccionado;
	}

	public void setPrimeraVez(boolean primeraVez) {
		this.primeraVez = primeraVez;
	}

	public boolean isPrimeraVez() {
		return primeraVez;
	}
	
}
