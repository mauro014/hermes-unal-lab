/**
 * Modificado por Mauricio
 */

package co.edu.unal.hermes.vista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.component.UIPanel;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Proyecto;

public class ManejadorBuscadorCatalogoECP extends ManejadorBase {
	
	private boolean error = false;	
	private String paginaActual;
	private String nombreCursoBusqueda;
	private UIPanel panelBotonBuscar;	
	private UIPanel panelBotonAgregar;
	private HtmlDataTable tablaSedesSeleccionadas;
	private UIPanel panelPaginaInicial;
	private UIPanel panelSede;
	private UIData tablaCatalogo;
	private SelectItem[] facultadesItem;
	private String idSede;
	private UIPanel panelFacultadEnsayo;
	private UIPanel panelModalidad;
	private List<Dependencia> facultadesSeleccionadas;	
	private String idFacultad;	
	private String idModalidad;	
	private UIPanel panelResultados;	
	private List<Dependencia> sedesSeleccionadas;
	private List<Dependencia> listaFacultades;
	private SelectItem[] facultadesSedeItem;
	private SelectItem[] modalidadesItem;
	private SelectItem[] listaSedesItem;
	private List<Dependencia> listaSedes;
	private List<DominioDetalle> listaModalidad;
	private HtmlDataTable tablaFacultadesSeleccionadas;		 		
	int opcion;	
	private Dependencia sedeSeleccionada;	
	private Dependencia facultadSeleccionada;
	
	//private List<CatalogoECP> listaCatalogo;
	//private CatalogoECP catalogoSeleccionado;
	
	private List<Proyecto> listaCatalogo;
	private Proyecto catalogoSeleccionado;
	
	public ManejadorBuscadorCatalogoECP() {
		
		opcion = 0;

		sedesSeleccionadas = new ArrayList<Dependencia>();
		
		idSede = "";

		idFacultad = "";
		
		idModalidad = "";
		
		facultadesSeleccionadas = new ArrayList<Dependencia>();
		
		listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
		listaSedesItem = new SelectItem[listaSedes.size() + 1];
		listaSedesItem[0] = new SelectItem("", "");
		
		cargarDominios();
		
		modalidadesItem = new SelectItem[ listaModalidad.size() + 1];
		
		modalidadesItem[0] = new SelectItem("", "");
		
		if(listaModalidad != null){
			for (int i = 1; i < listaModalidad.size()+1; i++) {
				modalidadesItem[i] = new SelectItem(listaModalidad.get(i-1).getIdentificador().getTipo(), listaModalidad.get(i-1).getDescripcion());
			}
		}
		
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
	
	public void ocultarPaneles(){
		idSede = "";
		idFacultad = "";
		opcion = 0;
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
	
	public void limpiarFiltroModalidad(){
		idModalidad = "";
		busqueda();
	}

	public void limpiarFiltroFacultad(){
		facultadesSeleccionadas = new ArrayList<Dependencia>();
		busqueda();
	}
	
	public void eliminarFacultades(){
		Dependencia facultadActual = facultadSeleccionada;
		facultadesSeleccionadas.remove(facultadActual);
		busqueda();
	}
		
	public void cargarModalidad(){
		opcion = 3;
	}
	
	

	public void eliminarSede(){
		Dependencia sedeActual = sedeSeleccionada;
		sedesSeleccionadas.remove(sedeActual);
		busqueda();
	}
	
	public boolean getVisibleFiltros(){
		if(getVisibleSedesFiltro() || getVisibleFacultadesFiltro() || getVisibleModalidadFiltro() ){
			return true;
		}
		return false;
	}

	public void buscarCursos(){
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
		String fromSql = "";
		String sql = "";
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
					sql += " HER_DEPENDENCIA.SED_ID = " + sede.getId();
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
					/*sql += " HER_EXT_ECP_CATALOGO.DEP_ID = " + facultad.getId()
						+ " or HER_DEPENDENCIA.DPN_ID_2 = " + facultad.getId();*/
					sql += " HER_PROYECTO.DPN_ID = " + facultad.getId()
							+ " or HER_DEPENDENCIA.DPN_ID_2 = " + facultad.getId();
				}
				sql += " ) ";
			}
			if (this.idModalidad.trim().length() > 0) {
				if (sql.length() > 0) sql = sql + " and ";
				fromSql = ", HER_DOMINIO_DETALLE";
				/*sql = " HER_EXT_ECP_CATALOGO.COD_SUBMODALIDAD = HER_DOMINIO_DETALLE.DOMDET_TIPO and " +
						" HER_EXT_ECP_CATALOGO.COD_SUBMODALIDAD = '" + this.idModalidad+ "'";*/
				
				sql = " HER_PROYECTO.PRY_CLASE_EVENTO = HER_DOMINIO_DETALLE.DOMDET_TIPO and " +
						" HER_PROYECTO.PRY_CLASE_EVENTO = '" + this.idModalidad+ "'";
				
			}
			if(this.nombreCursoBusqueda.length() > 0){
				if(sql.length()>0){
					sql += " and ";
				}
				/*sql += "( upper(HER_EXT_ECP_CATALOGO.NOM_ACTIVIDAD) LIKE '%"+this.nombreCursoBusqueda.toUpperCase()+"%' or " +
						"upper(HER_EXT_ECP_CATALOGO.OBJETO_ACTIVIDAD) LIKE '%"+this.nombreCursoBusqueda.toUpperCase()+"%' ) ";*/
				
				sql += "( upper(HER_PROYECTO.PRY_NOMBRE) LIKE '%"+this.nombreCursoBusqueda.toUpperCase()+"%' or " +
						"upper(HER_PROYECTO.PRY_OBJETIVO_GENERAL) LIKE '%"+this.nombreCursoBusqueda.toUpperCase()+"%' ) ";
			}

			this.listaCatalogo = servicioGeneral.obtenerECPCatalogoBuscador(sql, fromSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ocultarPaneles();		
	}
	
	private void cargarPaginaActual(){
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		
		String viewId = "/pages/Consultas/";
		
		viewId = extContext.getRequestContextPath() + viewId ;
		
		this.setPaginaActual(context.getExternalContext().encodeActionURL(viewId));
	}

	public void setPanelResultados(UIPanel panelResultados) {
		this.panelResultados = panelResultados;
	}

	public UIPanel getPanelResultados() {
		return panelResultados;
	}

	public int getNumeroResultados(){
		if(listaCatalogo != null){
			return listaCatalogo.size();
		}
		return 0;
	}
	
	public UIData getTablaCatalogo() {
		return tablaCatalogo;
	}

	public void setTablaCatalogo(UIData tablaCatalogo) {
		this.tablaCatalogo = tablaCatalogo;
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
		
		//CatalogoECP cursoAcual = catalogoSeleccionado;
		Proyecto cursoAcual = catalogoSeleccionado;

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		String viewId = "/pages/Consultas/CursoECP.xhtml";
		try {
			viewId = extContext.getRequestContextPath() + viewId + '?'
					+ "idCurso" + "=" + cursoAcual.getId() + "&opcion=1";
			String urlLink = context.getExternalContext().encodeActionURL(
					viewId);
			extContext.redirect(urlLink);
		} catch (IOException e) {
			extContext.log(getClass().getName() + ".invokeRedirect", e);
		}
		return null;

	}

	public void setPanelModalidad(UIPanel panelModalidad) {
		this.panelModalidad = panelModalidad;
	}

	public UIPanel getPanelModalidad() {
		return panelModalidad;
	}

	public void setIdModalidad(String idModalidad) {
		this.idModalidad = idModalidad;
	}

	public String getIdModalidad() {
		return idModalidad;
	}

	public void setModalidadesItem(SelectItem[] modalidadesItem) {
		this.modalidadesItem = modalidadesItem;
	}

	public SelectItem[] getModalidadesItem() {
		return modalidadesItem;
	}

	public void setListaModalidad(List<DominioDetalle> listaModalidad) {
		this.listaModalidad = listaModalidad;
	}

	public List<DominioDetalle> getListaModalidad() {
		return listaModalidad;
	}
	
	public void cargarDominios(){
	    
	    String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id = 26";
	    listaModalidad = servicioGeneral.obtenerObjetos(consulta);	    
	    
	}
	public String getModalidad(){
		if(this.idModalidad.equals("")){
			return "";
		}
		else{
			Iterator<DominioDetalle> it = listaModalidad.iterator();
			while(it.hasNext()){
				DominioDetalle modalidad = it.next();
				if(modalidad.getIdentificador().getTipo().equals(idModalidad)){
					return modalidad.getDescripcion();
				}
			}
		}
		return "";
	}
	
	public boolean getVisibleModalidadFiltro(){
		if(this.idModalidad.equals("")) return false;
		else return true;
	}

	public List<Proyecto> getListaCatalogo() {
		return listaCatalogo;
	}

	public void setListaCatalogo(List<Proyecto> listaCatalogo) {
		this.listaCatalogo = listaCatalogo;
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
		if(listaCatalogo != null){
			if(listaCatalogo.size() > 0)	{
				return false;
			}		
		}
		return true;
	}
	
	public boolean getOpcion1(){
		if(opcion == 1 || opcion == 2)return true;
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

	public void setCatalogoSeleccionado(Proyecto catalogoSeleccionado) {
		this.catalogoSeleccionado = catalogoSeleccionado;
	}

	public Proyecto getCatalogoSeleccionado() {
		return catalogoSeleccionado;
	}
	
	public String getNombreCursoBusqueda() {
		return nombreCursoBusqueda;
	}

	public void setNombreCursoBusqueda(String nombreCursoBusqueda) {
		this.nombreCursoBusqueda = nombreCursoBusqueda;
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
	
	public UIPanel getPanelSede() {
		return panelSede;
	}

	public void setPanelSede(UIPanel panelSede) {
		this.panelSede = panelSede;
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
	
	public HtmlDataTable getTablaFacultadesSeleccionadas() {
		return tablaFacultadesSeleccionadas;
	}

	public void setTablaFacultadesSeleccionadas(
			HtmlDataTable tablaFacultadesSeleccionadas) {
		this.tablaFacultadesSeleccionadas = tablaFacultadesSeleccionadas;
	}		
	
	public HtmlDataTable getTablaSedesSeleccionadas() {
		return tablaSedesSeleccionadas;
	}

	public void setTablaSedesSeleccionadas(HtmlDataTable tablaSedesSeleccionadas) {
		this.tablaSedesSeleccionadas = tablaSedesSeleccionadas;
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
	
}
