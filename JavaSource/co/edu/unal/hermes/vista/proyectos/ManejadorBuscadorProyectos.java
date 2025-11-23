/**
 * Modificado por Mauricio
 */

package co.edu.unal.hermes.vista.proyectos;

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

import org.apache.myfaces.custom.datalist.HtmlDataList;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorBuscadorProyectos extends ManejadorBase {
	
	private boolean error = false;
	
	private Proyecto proyectoSeleccionado;
	
	private String nombreProyectoBusqueda;
	
	private int convocatoriaModalidad = 0;
	
	private Dependencia sedeSeleccionada;
	
	private Dependencia facultadSeleccionada;
	
	private UIPanel panelSede;
	
	private String idSede;
	
	private List<Dependencia> listaFacultades;

	private SelectItem[] facultadesSedeItem;
	
	private SelectItem[] facultadesItem;
	
	private List<Dependencia> listaSedes;
	
	private SelectItem[] listaSedesItem;

	private UIPanel panelFacultad;
	
	private String idFacultad;

	private UIPanel panelLider;
	
	private String nombreLider; 
	
	private String apellidoLider; 
	
	private UIPanel panelBotonBuscar;	
	
	private UIPanel panelBotonAgregar;	
	
	private UIPanel panelResultados;	
	
	private UIPanel panelPaginaInicial;
	
	private UIPanel panelConvocatoriaModalidad;

	private List<Dependencia> facultadesSeleccionadas;

	private HtmlDataTable tablaFacultadesSeleccionadas;

	private List<Dependencia> sedesSeleccionadas;

	private HtmlDataTable tablaSedesSeleccionadas;

	private UIPanel panelAreasConocimiento;
	
	private String areaConocimiento;

	private List<String> areasSeleccionadas;
	
	private String areaSeleccionada;

	private HtmlDataTable tablaAreasSeleccionadas;
	
	private List<Proyecto> listaProyecto; 

	private UIData tablaProyectos;

	private UIData tablaGrupos;
	
	private int opcion = 0;
	
	private SelectItem[] modalidadItem = { new SelectItem(new Integer(0), ""),
			new SelectItem(new Integer(1), "Convocatoria"),
			new SelectItem(new Integer(2), "Contrapartida"),
			new SelectItem(new Integer(3), "Jornada Docente"),
			new SelectItem(new Integer(4), "Registro"),
			new SelectItem(new Integer(5), "Facultad"),
			new SelectItem(new Integer(6), "Programa Nacional"), };
	
	public ManejadorBuscadorProyectos() {
		
		facultadesSedeItem = new SelectItem[1];
		facultadesSedeItem[0] = new SelectItem("", "");
		
		listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
		listaSedesItem = new SelectItem[listaSedes.size() + 1];
		listaSedesItem[0] = new SelectItem("", "");

		idSede = "";
		
		nombreLider = "";
		
		apellidoLider = "";

		idFacultad = "";
		
		areaConocimiento = "";
		
		for (int i = 1; i < listaSedes.size() + 1; i++) {
			Dependencia sede = (Dependencia) listaSedes.get(i - 1);
			listaSedesItem[i] = new SelectItem(sede.getId(), sede.getNombre().trim().toUpperCase());
		}
			
		sedesSeleccionadas = new ArrayList<Dependencia>();
		
		facultadesSeleccionadas = new ArrayList<Dependencia>();
		
		areasSeleccionadas = new ArrayList<String>();
		
/*
		
		
		lineasSeleccionadas = new ArrayList<String>();
		
		
		
		lineaInvestigacion = "";
		
		
		
		if(!error){
			cargarPaginaActual();
		}*/
		
	}


	public void setError(boolean error) {
		this.error = error;
	}

	public boolean isError() {
		return error;
	}
	
	public String getNombreProyectoBusqueda() {
		return nombreProyectoBusqueda;
	}

	public void setNombreProyectoBusqueda(String nombreProyectoBusqueda) {
		this.nombreProyectoBusqueda = nombreProyectoBusqueda;
	}
	
	public void setPanelSede(UIPanel panelSede) {
		this.panelSede = panelSede;
	}

	public UIPanel getPanelSede() {
		return panelSede;
	}
	
	public void setIdSede(String idSede) {
		this.idSede = idSede;
	}

	public String getIdSede() {
		return idSede;
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
	
	public void setListaSedesItem(SelectItem[] listaSedesItem) {
		this.listaSedesItem = listaSedesItem;
	}

	public SelectItem[] getListaSedesItem() {
		return listaSedesItem;
	}
	
	public void setPanelFacultad(UIPanel panelFacultad) {
		this.panelFacultad = panelFacultad;
	}

	public UIPanel getPanelFacultad() {
		return panelFacultad;
	}

	public void setIdFacultad(String idFacultad) {
		this.idFacultad = idFacultad;
	}

	public String getIdFacultad() {
		return idFacultad;
	}

	public void setPanelLider(UIPanel panelLider) {
		this.panelLider = panelLider;
	}

	public UIPanel getPanelLider() {
		return panelLider;
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

	public void setPanelResultados(UIPanel panelResultados) {
		this.panelResultados = panelResultados;
	}

	public UIPanel getPanelResultados() {
		return panelResultados;
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
		opcion = 2;
		idSede = "";
		idFacultad = "";
		facultadesSedeItem = new SelectItem[1];
		facultadesSedeItem[0] = new SelectItem("", "");
	}

	public void cargarLider(){
		opcion = 3;
	}
	
	public void cargarAreas(){
		opcion = 4;
	}
	
	public void cargarModalidad(){
		opcion = 5;
	}
	
	public boolean getVisibleApellidoLiderFiltro(){
		if(apellidoLider.trim().length() > 0)
			return true;
		return false;
	}
	
	public boolean getVisibleConvocatoriaFiltro(){
		if(convocatoriaModalidad > 0)
			return true;
		return false;
	}
	
	public void limpiarFiltroApellidoLider(){
		apellidoLider = "";
		busqueda();
	}
	
	public void limpiarFiltroConvocatoria(){
		convocatoriaModalidad = 0;
		busqueda();
	}

	public boolean getVisibleNombreLiderFiltro(){
		if(nombreLider.trim().length() > 0)
			return true;
		return false;
	}
	
	public void limpiarFiltroNombreLider(){
		nombreLider = "";
		busqueda();
	}

	public boolean getVisibleFacultadesFiltro(){
		if(facultadesSeleccionadas.size() > 0)
			return true;
		return false;
	}

	public void limpiarFiltroFacultad(){
		facultadesSeleccionadas = new ArrayList<Dependencia>();
		busqueda();
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
	
	public void eliminarFacultades(){
		Dependencia facultadActual = facultadSeleccionada;
		facultadesSeleccionadas.remove(facultadActual);
		busqueda();
	}
	
	public boolean getVisibleSedesFiltro(){
		if(sedesSeleccionadas.size() > 0)
			return true;
		return false;
	}
	
	public void limpiarFiltroSede(){
		sedesSeleccionadas = new ArrayList<Dependencia>();
		busqueda();
	}
	
	public void setSedesSeleccionadas(List<Dependencia> sedesSeleccionadas) {
		this.sedesSeleccionadas = sedesSeleccionadas;
	}

	public List<Dependencia> getSedesSeleccionadas() {
		return sedesSeleccionadas;
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
	
	public void setPanelAreasConocimiento(UIPanel panelAreasConocimiento) {
		this.panelAreasConocimiento = panelAreasConocimiento;
	}

	public UIPanel getPanelAreasConocimiento() {
		return panelAreasConocimiento;
	}

	public String getAreaConocimiento() {
		return areaConocimiento;
	}

	public void setAreaConocimiento(String areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}
	
	public List<String> obtenerAreasConocimiento(String nombre) {
		return (List<String>) servicioGeneral.obtenerAreasConocimientoEmpezandoCon(nombre);
	}
	
	public boolean getVisibleAreasFiltro(){
		if(areasSeleccionadas.size() > 0)
			return true;
		return false;
	}

	public void limpiarFiltroAreas(){
		areasSeleccionadas = new ArrayList<String>();
		busqueda();
	}
	
	public List<String> getAreasSeleccionadas() {
		return areasSeleccionadas;
	}

	public void setAreasSeleccionadas(List<String> areasSeleccionadas) {
		this.areasSeleccionadas = areasSeleccionadas;
	}
	
	public HtmlDataTable getTablaAreasSeleccionadas() {
		return tablaAreasSeleccionadas;
	}

	public void setTablaAreasSeleccionadas(HtmlDataTable tablaAreasSeleccionadas) {
		this.tablaAreasSeleccionadas = tablaAreasSeleccionadas;
	}
	
	public void eliminarAreas(){
		String areaActual = areaSeleccionada;
		for(String area:areasSeleccionadas){
			if(area.trim().equals(areaActual)){
				areasSeleccionadas.remove(area);
				break;
			}
		}
		busqueda();
	}
	
	public void buscarProyectos(){
		this.setTablaConsultas(null);
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
		if (this.areaConocimiento.trim().length() > 0) {
			boolean esta = false;
			for(String area : areasSeleccionadas ){
				if(area.trim().equals(this.areaConocimiento)){
					esta = true;
				}
			}
			if(!esta){
				this.areasSeleccionadas.add(areaConocimiento);
			}
		}
		busqueda();		
	}
	
	public void busqueda(){
		try{
			this.listaProyecto = null;
			String sql = "";
			if (this.nombreProyectoBusqueda.length() > 0) {
				

				String tempKey = this.nombreProyectoBusqueda.toUpperCase();
				String[] keyArray = tempKey.split(" ");
				
				ArrayList<String> Array = eliminarPalabrasComunes(keyArray);

				String likeNombre = "";
				String likeAbstract = "";

				for (int i = 0; i < Array.size(); i++) {
					if (i == 0) {
						likeNombre = likeNombre
								+ " UPPER(p.PRY_NOMBRE) like '%"
								+ (String) Array.get(i) + "%'";
					} else {
						likeNombre = likeNombre
								+ " or UPPER(p.PRY_NOMBRE) like '%"
								+ (String) Array.get(i) + "%'";
					}

				}

				sql = sql + "(UPPER(p.PRY_NOMBRE) like '%" + tempKey
						+ "%'OR " + likeAbstract + likeNombre + ")";

			}
			
			
			if (this.apellidoLider.length() > 0 || this.nombreLider.length() > 0) {

				if (this.nombreLider.length() > 0) {
					this.nombreLider = this.nombreLider.trim();
					if (this.nombreLider.indexOf(" ") > 0) {
						String cadenaNombres[] = this.nombreLider.split(" ");
						String tempNombre1 = "";
						String tempNombre2 = "";
						if (cadenaNombres != null && cadenaNombres.length > 0) {
							if (sql.length() > 0) {
								sql = sql + " and ";
							}
							if (cadenaNombres.length == 1) {
								tempNombre1 = cadenaNombres[0];
								tempNombre1 = tempNombre1.trim();
								sql = sql + " (upper(inves.per_nombre1) like '%" + tempNombre1.toUpperCase() + "%'"
										+ " or upper(inves.per_nombre2) like '%" + tempNombre1.toUpperCase() + "%') ";
							}
							if (cadenaNombres.length == 2) {
								tempNombre1 = cadenaNombres[0];
								tempNombre2 = cadenaNombres[1];
								tempNombre1 = tempNombre1.trim();
								tempNombre2 = tempNombre2.trim();
								sql = sql + " (upper(inves.per_nombre1) like '%" + tempNombre1.toUpperCase()
										+ "%' or upper(inves.per_nombre2) like '%" + tempNombre2.toUpperCase() + "%' )";
							}
						}
					}
					else {
						if (sql.length() > 0) {
							sql = sql + " and ";
						}
						sql = sql + " (upper(inves.per_nombre1) like '%" + nombreLider.toUpperCase() + "%'"
								+ " or upper(inves.per_nombre2) like '%" + nombreLider.toUpperCase() + "%') ";
					}
				}

				if (this.apellidoLider.length() > 0) {
					this.apellidoLider = this.apellidoLider.trim();
					if (this.apellidoLider.indexOf(" ") > 0) {
						String cadenaApellidos[] = this.apellidoLider.split(" ");
						String tempApellido1 = "";
						String tempApellido2 = "";
						if (cadenaApellidos != null	&& cadenaApellidos.length > 0) {
							if (sql.length() > 0) {
								sql = sql + " and ";
							}
							if (cadenaApellidos.length == 1) {
								tempApellido1 = cadenaApellidos[0];
								tempApellido1 = tempApellido1.trim();
								sql = sql + "  (upper(inves.per_apellido1) like '%" + tempApellido1.toUpperCase() + "%' "
										+ " or upper(inves.per_apellido2) like '%" + tempApellido1.toUpperCase() + "%') ";
							}
							if (cadenaApellidos.length == 2) {
								tempApellido1 = cadenaApellidos[0];
								tempApellido2 = cadenaApellidos[1];
								tempApellido1 = tempApellido1.trim();
								tempApellido2 = tempApellido2.trim();
								sql = sql + " (upper(inves.per_apellido1) like '%" + tempApellido1.toUpperCase() + "%'"
										+ " or upper(inves.per_apellido2) like '%" + tempApellido2.toUpperCase() + "%') ";
							}
						}
					} 
					else {
						if (sql.length() > 0) {
							sql = sql + " and ";
						}
						sql = sql + " (upper(inves.per_apellido1) like '%"	+ apellidoLider.toUpperCase()+ "%'"
								+ " or upper(inves.per_apellido2) like '%" + apellidoLider.toUpperCase() + "%') ";
					}

				}
			}
			
			if (this.convocatoriaModalidad != 0) {
				if (sql.length() > 0) {
					sql = sql + " and ";
				}
				switch (this.convocatoriaModalidad) {
				case 1:
					sql = sql + " m.TMO_ID = 'C'";
					break;
				case 2:
					sql = sql + " m.TMO_ID = 'T'";
					break;
				case 3:
					sql = sql + " m.TMO_ID = 'J'";
					break;
				case 4:
					sql = sql + " m.TMO_ID = 'R'";
					break;
				case 5:
					sql = sql + " m.TMO_ID = 'FC'";
					break;
				case 6:
					sql = sql + " m.TMO_ID = 'PN'";
					break;
				}
			}
			
			if(this.sedesSeleccionadas.size() > 0 ){
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
					sql += " s.SED_ID = '" + sede.getId() + "'";
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
					sql += " d.DPN_ID= '" + facultad.getId() + "' "
						+ " or d.DPN_ID_2 = '" + facultad.getId() + "' "; 
				}
				sql += " ) ";
			}
			
			if(this.areaConocimiento.length() > 0){
				if (sql.length() > 0) {
					sql = sql + " and ";
				}						
				sql = sql + " Exists ( select * from her_proyecto_clas_con pclc,her_clasificacion_conocimiento clc where p.PRY_ID  = pclc.PRY_ID and clc.CLC_ID = pclc.CLC_ID and clc.CLC_NOMBRE  like '%" + this.areaConocimiento + "%') ";
			}
			
			this.listaProyecto = (List<Proyecto>) servicioProyecto.obtenerProyectoBuscador(sql, null, null, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		ocultarPaneles();	
	}	
	
	public ArrayList<String> eliminarPalabrasComunes(String[] arreglo) {
		
		ArrayList<String> palabrasComunes = new ArrayList<String>();
		String fuente = ("a adonde al ante así aunque bajo bien cabe como con contra cuando de desde donde durante e el en entre ésta éstas éste esto éstos hacia hasta la las los luego más mediante ni o para pero por porque puesto que según si sin sino so sobre tan tras u un una unas unos y ya");
		String[] arregloComunes = fuente.split(" ");
		for (int i = 0; i < arregloComunes.length; i++) {
			palabrasComunes.add(arregloComunes[i].toUpperCase());
		}
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < arreglo.length; i++) {
			if (!palabrasComunes.contains(arreglo[i])) {
				result.add(arreglo[i]);
			}
		}
		return result;
	}

	public boolean getVisibleFiltros(){
		if(getVisibleApellidoLiderFiltro() ||
			getVisibleNombreLiderFiltro() ||
			getVisibleAreasFiltro() ||
			getVisibleSedesFiltro() || 
			getVisibleConvocatoriaFiltro() || 
			getVisibleFacultadesFiltro()	){
			return true;
		}
		return false;
	}
	
	public void ocultarPaneles(){
		opcion = 0;
		idSede = "";
		areaConocimiento = "";
		idFacultad = "";
	}
	
	public List<Proyecto> getListaProyecto() {
		return listaProyecto;
	}

	public void setListaProyecto(List<Proyecto> listaProyecto) {
		this.listaProyecto = listaProyecto;
	}
	
	public UIData getTablaProyectos() {
		return tablaProyectos;
	}

	public void setTablaProyectos(UIData tablaProyectos) {
		this.tablaProyectos = tablaProyectos;
	}
	
	public SelectItem[] getModalidadItem() {
		return modalidadItem;
	}

	public void setModalidadItem(SelectItem[] modalidadItem) {
		this.modalidadItem = modalidadItem;
	}
	
	public UIPanel getPanelConvocatoriaModalidad() {
		return panelConvocatoriaModalidad;
	}

	public void setPanelConvocatoriaModalidad(UIPanel panelConvocatoriaModalidad) {
		this.panelConvocatoriaModalidad = panelConvocatoriaModalidad;
	}

	public int getConvocatoriaModalidad() {
		return convocatoriaModalidad;
	}

	public String getConvocatoriaModalidadNombre() {
		String nombre = "";
		switch (convocatoriaModalidad) {
		case 1:
			nombre = "Convocatoria";
			break;
		case 2:
			nombre = "Contrapartida";
			break;
		case 3:
			nombre = "Jornada Docente";
			break;
		case 4:
			nombre = "Registro";
			break;
		case 5:
			nombre = "Facultad";
			break;
		case 6:
			nombre = "Programa Nacional";
			break;

		}
		return nombre;
	}

	public void setConvocatoriaModalidad(int convocatoriaModalidad) {
		this.convocatoriaModalidad = convocatoriaModalidad;
	}






	
	
	
	
	
	
	
	
	
	
	
	
	
	
	








	private List<String> lineasSeleccionadas;
	
	private String lineaInvestigacion;
	
	private String paginaActual;
	
	private String nombreGrupoBusqueda;
	
	private HtmlDataList tablaConsultas;
	
	private List<Grupo> listaGrupos; 

	private HtmlDataTable tablaLineasSeleccionadas;

	
	

	
	
	public boolean getVisibleLineasFiltro(){
		if(lineasSeleccionadas.size() > 0)
			return true;
		return false;
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
	
	public void limpiarFiltroLinea(){
		lineasSeleccionadas = new ArrayList<String>();
		busqueda();
	}
	
	public void eliminarLineas(){
		String lineaActual = (String) tablaLineasSeleccionadas.getRowData();
		for(String linea:lineasSeleccionadas){
			if(linea.trim().equals(lineaActual)){
				lineasSeleccionadas.remove(linea);
				break;
			}
		}
		busqueda();
	}

	

	public void setLineaInvestigacion(String lineaInvestigacion) {
		this.lineaInvestigacion = lineaInvestigacion;
	}

	public String getLineaInvestigacion() {
		return lineaInvestigacion;
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
	
	public String consultarPagina() {
		
		Proyecto proyectoActual = proyectoSeleccionado;

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		String viewId = "/pages/Consultas/Proyecto.xhtml";
		try {
			viewId = extContext.getRequestContextPath() + viewId + '?'
					+ "idProyecto" + "=" + proyectoActual.getId() + "&opcion=1";
			String urlLink = context.getExternalContext().encodeActionURL(
					viewId);
			extContext.redirect(urlLink);
		} catch (IOException e) {
			extContext.log(getClass().getName() + ".invokeRedirect", e);
		}
		return null;

	}

	public UIData getTablaGrupos() {
		return tablaGrupos;
	}

	public void setTablaGrupos(UIData tablaGrupos) {
		this.tablaGrupos = tablaGrupos;
	}
	
	public int getNumeroResultados(){
		if(listaProyecto != null){
			return listaProyecto.size();
		}
		return 0;
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
		if(opcion == 5)return true;
		return false;
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
	
	public boolean getVisibleResultados(){
		if(listaProyecto != null){
			if(listaProyecto.size() > 0)	{
				return false;
			}		
		}
		return true;
	}
	
	public boolean getOpcionAgregar(){
		if(opcion != 0)return true;
		return false;
	}
	
	public boolean getOpcionBuscar(){
		if(opcion == 0)return true;
		return false;
	}


	public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}


	public Proyecto getProyectoSeleccionado() {
		return proyectoSeleccionado;
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


	public void setAreaSeleccionada(String areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}


	public String getAreaSeleccionada() {
		return areaSeleccionada;
	}
	
}
