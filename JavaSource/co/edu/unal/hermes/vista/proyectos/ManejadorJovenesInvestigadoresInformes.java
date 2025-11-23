package co.edu.unal.hermes.vista.proyectos;

import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorJovenesInvestigadoresInformes  extends ManejadorBase {
	private List<ConvocatoriaPadre>  listadoConvocatoriaPadre;
	private Persona personaSeleccionada;
	private List<Sede> listaSedes;
	private boolean mostrarFacultades = false;
	private String sedeSel;
	private List<Dependencia> dependenciasUN;
	private SelectItem[] dependenciaItem;
	private List<Dependencia> facultadesUN;
	private SelectItem[] facultadItem;
	private String facultadSel;
	private SelectItem[] sedeItem;
	private String convocatoriaSel;
	private SelectItem[] convocatoriaItem;
	private List<ProyectoInforme>  listadoInformes;
	




	public ManejadorJovenesInvestigadoresInformes() {
		super();	
		
		IdPersona idEstudiante=new IdPersona();
		Long idConvocatoria=new Long(0);
		Long idProyecto=new Long(0);
		
		idEstudiante=(IdPersona) sesion.getAttribute("idEstudiante");
		idConvocatoria=(Long) sesion.getAttribute("idConvocatoria");
		idProyecto=(Long) sesion.getAttribute("idProyecto");
		
		String sql="select  pi.idEstudiante as documento, pi.tipoDocEstudiante as tipoDocumento, concat(invInt.apellido1,' ', invInt.apellido2,' ', invInt.nombre1) as nombre, count(pi.proyecto) as numero, pi.proyecto.id as numProy, pi.proyecto.nombre  as nombreProy"+ 
				" from ProyectoInforme pi, Convocatoria conv, InvestigadorInterno invInt"+
				" where conv.padre="+idConvocatoria+" and pi.proyecto.id="+idProyecto+" conv.id= pi.proyecto.modalidad.id and pi.idEstudiante = invInt.id.documento and pi.tipoDocEstudiante= invInt.id.tipoDocumento"+
				"and pi.idEstudiante = '"+idEstudiante.getDocumento()+"' and pi.tipoDocEstudiante='"+idEstudiante.getTipoDocumento()+"'";
					
		try {	
			
			listadoInformes=servicioGeneral.obtenerObjetosLimitado(ProyectoInforme.class, sql);
			
			
//			String sql1 = "select e from Sede e where  e.id<>0";			
//					
//			sedeItem = new SelectItem[sedesUN.size()];
//			convocatoriaItem= new SelectItem[convocatoriasPadre.size()];
//			
//			for (int i = 0; i < convocatoriasPadre.size(); i++) {
//				ConvocatoriaPadre dd = (ConvocatoriaPadre) convocatoriasPadre.get(i);
//				convocatoriaItem[i] = new SelectItem(dd.getId(), dd.getTitulo());
//				dd = null;
//			}
//			
//			for (int i = 0; i < sedesUN.size(); i++) {
//				Sede dd = (Sede) sedesUN.get(i);
//				sedeItem[i] = new SelectItem(dd.getId(), dd.getNombre());
//				dd = null;
//			}
			
//			setListadoConvocatoriaPadre(servicioGeneral.obtenerObjetos(sql));
//			setListaSedes(servicioGeneral.obtenerObjetos(sql1));
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
	}	
	
	
	public String cargarInforme(){
		IdPersona idPer = personaSeleccionada.getId();
		sesion.setAttribute("idPersonaRenovacion", idPer);
		sesion.setAttribute("consultaFacultad", true);
		sesion.removeAttribute("manejadorPrincipalInforme");
		return "principalInforme";
	}

	public List<ConvocatoriaPadre> getListadoConvocatoriaPadre() {
		return listadoConvocatoriaPadre;
	}

	public void setListadoConvocatoriaPadre(List<ConvocatoriaPadre> listadoConvocatoriaPadre) {
		this.listadoConvocatoriaPadre = listadoConvocatoriaPadre;
	}

	public List<Sede> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(List<Sede> listaSedes) {
		this.listaSedes = listaSedes;
	}
	
	public boolean isMostrarFacultades() {
		return mostrarFacultades;
	}

	public void setMostrarFacultades(boolean mostrarFacultades) {
		this.mostrarFacultades = mostrarFacultades;
	}

	public String getSedeSel() {
		return sedeSel;
	}

	public void setSedeSel(String sedeSel) {
		this.sedeSel = sedeSel;
	}

	public List<Dependencia> getDependenciasUN() {
		return dependenciasUN;
	}

	public void setDependenciasUN(List<Dependencia> dependenciasUN) {
		this.dependenciasUN = dependenciasUN;
	}

	public SelectItem[] getDependenciaItem() {
		return dependenciaItem;
	}

	public void setDependenciaItem(SelectItem[] dependenciaItem) {
		this.dependenciaItem = dependenciaItem;
	}

	public List<Dependencia> getFacultadesUN() {
		return facultadesUN;
	}

	public void setFacultadesUN(List<Dependencia> facultadesUN) {
		this.facultadesUN = facultadesUN;
	}

	public SelectItem[] getFacultadItem() {
		return facultadItem;
	}

	public void setFacultadItem(SelectItem[] facultadItem) {
		this.facultadItem = facultadItem;
	}

	public String getFacultadSel() {
		return facultadSel;
	}

	public void setFacultadSel(String facultadSel) {
		this.facultadSel = facultadSel;
	}

	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	public void setSedeItem(SelectItem[] sedeItem) {
		this.sedeItem = sedeItem;
	}

	

	public String getConvocatoriaSel() {
		return convocatoriaSel;
	}

	public void setConvocatoriaSel(String convocatoriaSel) {
		this.convocatoriaSel = convocatoriaSel;
	}
	
	public Persona getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(Persona personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}


	public SelectItem[] getConvocatoriaItem() {
		return convocatoriaItem;
	}


	public void setConvocatoriaItem(SelectItem[] convocatoriaItem) {
		this.convocatoriaItem = convocatoriaItem;
	}


	public List<ProyectoInforme> getListadoInformes() {
		return listadoInformes;
	}


	public void setListadoInformes(List<ProyectoInforme> listadoInformes) {
		this.listadoInformes = listadoInformes;
	}


}
