/**
 * Modificado por Mauricio
 */

package co.edu.unal.hermes.vista.personas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.component.UIPanel;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.myfaces.custom.datalist.HtmlDataList;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorBuscadorInvestigadores extends ManejadorBase {
	
	private String nombreLiderUsado; 
	
	private String apellidoLiderUsado; 
	
	private UIData tablaGrupos;
	
	private String nombreLider; 
	
	private String apellidoLider; 
	
	private String lineaInvestigacion;
	
	private Persona personaSeleccionada;
	
	private List listaInvestigadores;
	
	private boolean error = false;

	private SelectItem[] facultadesItem;
	
	private Investigador investigadorActual; // INVESTIGADOR ACTUALMENTE
	
	private List<Dependencia> listaFacultades;

	private SelectItem[] facultadesSedeItem;
	
	private SelectItem[] listaSedesItem;
	
	private String paginaActual;
	
	private String nombreGrupoBusqueda;
	
	private String nombreBotonBusqueda; 
	
	private HtmlDataList tablaConsultas;
	
	private boolean primeraVez;
	
	private List<Grupo> listaGrupos; 
	
	private String idSede;

	private List<Dependencia> listaSedes;
	
	private UIPanel panelSedeGrupo;
	
	private UIPanel panelFacultadGrupo;

	private UIPanel panelLineasGrupo;
	
	private UIPanel panelLiderGrupo;
	
	private UIPanel panelPaginaInicial;
	
	private UIPanel panelResultados;	
	
	private UIPanel panelBotonBuscar;	
	
	private UIPanel panelBotonAgregar;	
	
	private Grupo grupoSeleccionado;

	private List<Dependencia> sedesSeleccionadas;

	private List<Dependencia> facultadesSeleccionadas;

	private List<String> lineasSeleccionadas;

	private HtmlDataTable tablaSedesSeleccionadas;

	private HtmlDataTable tablaFacultadesSeleccionadas;

	private HtmlDataTable tablaLineasSeleccionadas;

	private String idFacultad;
	
	private Dependencia sedeSeleccionada;
	
	private Dependencia facultadSeleccionada;
	
	private String lineaSeleccionada; 
	
	private int opcion = 0;
	
	public ManejadorBuscadorInvestigadores() {

		sedesSeleccionadas = new ArrayList<Dependencia>();
		
		facultadesSeleccionadas = new ArrayList<Dependencia>();
		
		lineasSeleccionadas = new ArrayList<String>();
		
		nombreLider = "";
		
		apellidoLider = "";
		
		nombreLiderUsado = "";
		
		apellidoLiderUsado = "";
		
		nombreGrupoBusqueda = "";
		
		listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
		listaSedesItem = new SelectItem[listaSedes.size() + 1];
		listaSedesItem[0] = new SelectItem("", "");
		facultadesSedeItem = new SelectItem[1];
		facultadesSedeItem[0] = new SelectItem("", "");
		idSede = "";
		idFacultad = "";
		lineaInvestigacion = "";
		
		for (int i = 1; i < listaSedes.size() + 1; i++) {
			Dependencia sede = (Dependencia) listaSedes.get(i - 1);
			listaSedesItem[i] = new SelectItem(sede.getId(), sede.getNombre().trim().toUpperCase());
		}
		
		if(!error){
			cargarPaginaActual();
		}
		
	}

	public void ocultarPaneles(){
		idSede = "";
		lineaInvestigacion = "";
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
	

	public boolean getVisibleFiltros(){
		if(getVisibleApellidoLiderFiltro() ||
			getVisibleNombreLiderFiltro() ||
			getVisibleLineasFiltro() ||
			getVisibleSedesFiltro() || 
			getVisibleFacultadesFiltro()	){
			return true;
		}
		return false;
	}

	public boolean getVisibleNombreLiderFiltro(){
		if(nombreLiderUsado.trim().length() > 0)
			return true;
		return false;
	}
	
	public boolean getVisibleApellidoLiderFiltro(){
		if(apellidoLiderUsado.trim().length() > 0)
			return true;
		return false;
	}
	
	public boolean getVisibleLineasFiltro(){
		if(lineasSeleccionadas.size() > 0)
			return true;
		return false;
	}
	
	public void busqueda(){
		primeraVez = false;
		String fromSql = "";
		try {
			this.listaGrupos = null;
			
			this.listaInvestigadores = null;
			
			String sql = "";
			
			boolean opcionDependencia = false;
			
			if(this.sedesSeleccionadas.size() > 0){
				opcionDependencia = true;
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
				opcionDependencia = true;
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
					sql += " HER_INVESTIGADOR_INTERNO.DPN_ID= " + facultad.getId()
						+ " or HER_DEPENDENCIA.DPN_ID_2 = " + facultad.getId();
				}
				sql += " ) ";
			}
			
			boolean opcionLider = false;
			
			if (this.nombreLiderUsado.length() > 0 ) {
				opcionLider = true;
				if (sql.length() > 0) {
					sql = sql + " and ";
				}
				List<String> cadena = ReemplazaAcentos.listaPalabrasConTildes(this.nombreLiderUsado.toUpperCase());
				String palabra2 = ReemplazaAcentos.quitarTildes(this.nombreLiderUsado.toUpperCase());
				cadena.add(palabra2);
				if(cadena.size() > 0){
					sql += "(";
					boolean primera = true;
					for(String palabra : cadena){
						if(!primera){
							sql += " or ";
						}
						else primera = false;
						sql += " ( upper( HER_PERSONA.PER_NOMBRE1)  like  '%" + palabra + "%'" +
								" or ( upper( HER_PERSONA.PER_NOMBRE2)  like  '%" + palabra + "%' " +
								" or ( upper( HER_PERSONA.PER_NOMBRE1) || ' ' || upper( HER_PERSONA.PER_NOMBRE2)) like  '%" + palabra + "%') )";
					}
					sql += ")";
				}
				
			}
			
			if (this.apellidoLiderUsado.length() > 0 ) {
				if (sql.length() > 0) {
					sql = sql + " and ";
				}
				List<String> cadena = ReemplazaAcentos.listaPalabrasConTildes(this.apellidoLiderUsado.toUpperCase());
				opcionLider = true;String palabra2 = ReemplazaAcentos.quitarTildes(this.apellidoLiderUsado.toUpperCase());
				cadena.add(palabra2);
				if(cadena.size() > 0){
					sql += "(";
					boolean primera = true;
					for(String palabra : cadena){
						if(!primera){
							sql += " or ";
						}
						else primera = false;
						sql += " ( upper( HER_PERSONA.PER_APELLIDO1)  like  '%" + palabra + "%'" +
								" or ( upper( HER_PERSONA.PER_APELLIDO2)  like  '%" + palabra + "%' " +
								" or ( upper( HER_PERSONA.PER_APELLIDO1) || ' ' || upper( HER_PERSONA.PER_APELLIDO2)) like  '%" + palabra + "%') )";
					}
					sql += ")";
				}
				
			}
			
			boolean lineas = false;
			if (this.lineasSeleccionadas.size() > 0) {
				lineas = true;
				if (sql.length() > 0) {
					sql = sql + " and ";
				}
				sql += " ( ";
				boolean primero = true;
				for(String linea : lineasSeleccionadas){
					if(!primero) { 
						sql += " or ";
					}
					sql += "(";
					List<String> cadena = ReemplazaAcentos.listaPalabrasConTildes(linea.toUpperCase());
					opcionLider = true;String palabra2 = ReemplazaAcentos.quitarTildes(linea.toUpperCase());
					cadena.add(palabra2);
					boolean primera2 = true;
					for(String palabra : cadena){
						if(!primera2){
							sql += " or ";
						}
						else primera2 = false;
						sql += " ( upper( HER_PROYECTO.PRY_NOMBRE)  like  '%" + palabra + "%'" +
								" or ( upper( HER_PROYECTO.PRY_ABSTRACT)  like  '%" + palabra + "%' ) )";
					}
					primero = false;
					sql += ")";
				}
				sql += " ) ";
				sql = sql + " and HER_PROYECTO.PRY_ID = HER_INVESTIGADOR_PROYECTO.PRY_ID " +
						" AND HER_INVESTIGADOR_INTERNO.INV_ID = HER_INVESTIGADOR_PROYECTO.INV_ID " +
						" AND HER_INVESTIGADOR_INTERNO.TDO_ID = HER_INVESTIGADOR_PROYECTO.TDO_ID ";		
				fromSql += ", HER_PROYECTO, HER_INVESTIGADOR_PROYECTO";
			}
			
			if (sql.length() > 0) sql = fromSql + " where " + sql;
			
			this.listaInvestigadores = (List<Persona>) this.servicioPersona.obtenerInvestigadoresBuscador(sql);

			if (this.listaGrupos == null || this.listaGrupos.size() < 0) {
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		ocultarPaneles();		
	}	
	
	public void buscarGrupos(){
		nombreBotonBusqueda = "Buscar";
		this.setTablaConsultas(null);
		if (this.idSede.length() > 0 /*&& this.idFacultad.length() == 0*/) {
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
		if (this.lineaInvestigacion.trim().length() > 0) {
			boolean esta = false;
			for(String linea : lineasSeleccionadas ){
				if(linea.trim().equals(this.lineaInvestigacion)){
					esta = true;
				}
			}
			if(!esta){
				this.lineasSeleccionadas.add(lineaInvestigacion);
			}
		}
		if(nombreLider!= null){
			if(!nombreLider.trim().equals("")){
				nombreLiderUsado = nombreLider;
			}
		}
		if(apellidoLider!= null){
			if(!apellidoLider.trim().equals("")){
				apellidoLiderUsado = apellidoLider;
			}
		}
		busqueda();		
	}
	
	private void cargarPaginaActual(){
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		
		String viewId = "/pages/Consultas/";
		
		viewId = extContext.getRequestContextPath() + viewId ;
		
		this.paginaActual = context.getExternalContext().encodeActionURL(viewId);
	}
	
	public void cargarSede(){
		opcion = 1;
	}
	
	public void cargarFacultad(){
		opcion = 2;
		idSede = "";
		idFacultad = "";
		facultadesSedeItem = new SelectItem[1];
		facultadesSedeItem[0] = new SelectItem("", "");
	}

	public void cargarLineas(){
		opcion = 3;
		lineaInvestigacion = "";
	}

	public void cargarLider(){
		opcion = 4;
	}

	public void cargarPresincripcion(){
		opcion = 5;
	}

	public String getSede() {
		return this.paginaActual + "?opcion=1";
	}
	
	public String getFacultad() {
		return this.paginaActual + "?opcion=2";
	}

	public String getArea() {
		return this.paginaActual + "?opcion=3";
	}

	public String getLinea() {
		return this.paginaActual + "?opcion=4";
	}

	public String getLider() {
		return this.paginaActual + "?opcion=5";
	}
	
	public String getClave() {
		return this.paginaActual + "?opcion=6";
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public boolean isError() {
		return error;
	}

	public void setPaginaActual(String paginaActual) {
		this.paginaActual = paginaActual;
	}

	public String getPaginaActual() {
		return paginaActual;
	}

	public void setNombreGrupoBusqueda(String nombreGrupoBusqueda) {
		this.nombreGrupoBusqueda = nombreGrupoBusqueda.trim();
	}

	public String getNombreGrupoBusqueda() {
		return nombreGrupoBusqueda;
	}

	public void setListaGrupos(List<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public List<Grupo> getListaGrupos() {
		return listaGrupos;
	}

	public void setTablaConsultas(HtmlDataList tablaConsultas) {
		this.tablaConsultas = tablaConsultas;
	}

	public HtmlDataList getTablaConsultas() {
		return tablaConsultas;
	}

	public void setNombreBotonBusqueda(String nombreBotonBusqueda) {
		this.nombreBotonBusqueda = nombreBotonBusqueda;
	}

	public String getNombreBotonBusqueda() {
		return nombreBotonBusqueda;
	}

	public void setIdSede(String idSede) {
		this.idSede = idSede;
	}

	public String getIdSede() {
		return idSede;
	}

	public void setListaSedesItem(SelectItem[] listaSedesItem) {
		this.listaSedesItem = listaSedesItem;
	}

	public SelectItem[] getListaSedesItem() {
		return listaSedesItem;
	}

	public void setPanelSedeGrupo(UIPanel panelSedeGrupo) {
		this.panelSedeGrupo = panelSedeGrupo;
	}

	public UIPanel getPanelSedeGrupo() {
		return panelSedeGrupo;
	}

	public void setSedesSeleccionadas(List<Dependencia> sedesSeleccionadas) {
		this.sedesSeleccionadas = sedesSeleccionadas;
	}

	public List<Dependencia> getSedesSeleccionadas() {
		return sedesSeleccionadas;
	}
	
	public void limpiarFiltroSede(){
		sedesSeleccionadas = new ArrayList<Dependencia>();
		busqueda();
	}

	public void limpiarFiltroFacultad(){
		facultadesSeleccionadas = new ArrayList<Dependencia>();
		busqueda();
	}
	
	public void limpiarFiltroNombreLider(){
		nombreLiderUsado = "";
		nombreLider = "";
		busqueda();
	}
	
	public void limpiarFiltroApellidoLider(){
		apellidoLiderUsado = "";
		apellidoLider = "";
		busqueda();
	}
	
	public void limpiarFiltroLinea(){
		lineasSeleccionadas = new ArrayList<String>();
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
	
	public void eliminarFacultades(){
		Dependencia facultadActual = facultadSeleccionada;
		facultadesSeleccionadas.remove(facultadActual);
		busqueda();
	}
	
	public void eliminarLineas(){
		String lineaActual = lineaSeleccionada;
		for(String linea:lineasSeleccionadas){
			if(linea.trim().equals(lineaActual)){
				lineasSeleccionadas.remove(linea);
				break;
			}
		}
		busqueda();
	}

	public void setIdFacultad(String idFacultad) {
		this.idFacultad = idFacultad;
	}

	public String getIdFacultad() {
		return idFacultad;
	}

	public void setListaFacultades(List<Dependencia> listaFacultades) {
		this.listaFacultades = listaFacultades;
	}

	public List<Dependencia> getListaFacultades() {
		return listaFacultades;
	}

	public void setFacultadesSedeItem(SelectItem[] facultadesSedeItem) {
		this.facultadesSedeItem = facultadesSedeItem;
	}

	public SelectItem[] getFacultadesSedeItem() {
		return facultadesSedeItem;
	}

	public void setPanelFacultadGrupo(UIPanel panelFacultadGrupo) {
		this.panelFacultadGrupo = panelFacultadGrupo;
	}

	public UIPanel getPanelFacultadGrupo() {
		return panelFacultadGrupo;
	}

	public void setFacultadesSeleccionadas(List<Dependencia> facultadesSeleccionadas) {
		this.facultadesSeleccionadas = facultadesSeleccionadas;
	}

	public List<Dependencia> getFacultadesSeleccionadas() {
		return facultadesSeleccionadas;
	}

	public void setTablaFacultadesSeleccionadas(
			HtmlDataTable tablaFacultadesSeleccionadas) {
		this.tablaFacultadesSeleccionadas = tablaFacultadesSeleccionadas;
	}

	public HtmlDataTable getTablaFacultadesSeleccionadas() {
		return tablaFacultadesSeleccionadas;
	}

	public void setPanelLineasGrupo(UIPanel panelLineas) {
		this.panelLineasGrupo = panelLineas;
	}

	public UIPanel getPanelLineasGrupo() {
		return panelLineasGrupo;
	}

	public void setLineaInvestigacion(String lineaInvestigacion) {
		this.lineaInvestigacion = lineaInvestigacion;
	}

	public String getLineaInvestigacion() {
		return lineaInvestigacion;
	}
	
	public List<String> obtenerPalabraClaves(String nombre) {
		return (List<String>)servicioGeneral.obtenerLineasEmpezandoCon(nombre);
	}

	public void setTablaLineasSeleccionadas(HtmlDataTable tablaLineasSeleccionadas) {
		this.tablaLineasSeleccionadas = tablaLineasSeleccionadas;
	}

	public HtmlDataTable getTablaLineasSeleccionadas() {
		return tablaLineasSeleccionadas;
	}

	public List<String> getLineasSeleccionadas() {
		return lineasSeleccionadas;
	}

	public void setLineasSeleccionadas(List<String> lineasSeleccionadas) {
		this.lineasSeleccionadas = lineasSeleccionadas;
	}

	public void setNombreLider(String nombreLider) {
		this.nombreLider = nombreLider;
	}

	public String getNombreLider() {
		return nombreLider;
	}

	public void setApellidoLider(String apellidoLider) {
		this.apellidoLider = apellidoLider;
	}

	public String getApellidoLider() {
		return apellidoLider;
	}

	public void setPanelLiderGrupo(UIPanel panelLiderGrupo) {
		this.panelLiderGrupo = panelLiderGrupo;
	}

	public UIPanel getPanelLiderGrupo() {
		return panelLiderGrupo;
	}
	
	public String consultarPagina() {
		
		Persona persona = (Persona) personaSeleccionada;
		
		IdPersona idInvestigador = persona.getId();
		
		this.investigadorActual = servicioPersona
		.obtenerResumenInvestigador(idInvestigador);
		investigadorActual
		.setClasificacionesConocimiento(new HashSet());

		sesion.setAttribute("investigadorBusqueda", investigadorActual);

		return "successPersona";
	}

	public UIData getTablaGrupos() {
		return tablaGrupos;
	}

	public void setTablaGrupos(UIData tablaGrupos) {
		this.tablaGrupos = tablaGrupos;
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
	
	public boolean getOpcion4(){
		if(opcion == 4)return true;
		return false;
	}
	
	public boolean getOpcion5(){
		if(opcion == 4)return true;
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
		if(listaInvestigadores != null){
			if(listaInvestigadores.size() > 0)	{
				return false;
			}		
		}
		return true;
	}

	public int getNumeroResultados(){
		if(listaInvestigadores != null){
			return listaInvestigadores.size();
		}
		return 0;
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

	public String getNombreLiderUsado() {
		return nombreLiderUsado;
	}

	public void setNombreLiderUsado(String nombreLiderUsado) {
		this.nombreLiderUsado = nombreLiderUsado;
	}

	public String getApellidoLiderUsado() {
		return apellidoLiderUsado;
	}

	public void setApellidoLiderUsado(String apellidoLiderUsado) {
		this.apellidoLiderUsado = apellidoLiderUsado;
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

	public void setLineaSeleccionada(String lineaSeleccionada) {
		this.lineaSeleccionada = lineaSeleccionada;
	}

	public String getLineaSeleccionada() {
		return lineaSeleccionada;
	}

	public void setGrupoSeleccionado(Grupo grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}

	public Grupo getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	public void setPrimeraVez(boolean primeraVez) {
		this.primeraVez = primeraVez;
	}

	public boolean isPrimeraVez() {
		return primeraVez;
	}

	public List getListaInvestigadores() {
		return listaInvestigadores;
	}

	public void setListaInvestigadores(List listaInvestigadores) {
		this.listaInvestigadores = listaInvestigadores;
	}

	public void setPersonaSeleccionada(Persona personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public Persona getPersonaSeleccionada() {
		return personaSeleccionada;
	}
	
	
	
}
