package co.edu.unal.hermes.vista.asesor;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import co.edu.unal.hermes.modelo.SolicitudUsuario;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultarSolicitudesUsuario extends ManejadorBase {

	List listaSolicitudes;
	List listaSolicitudesEspecifico;
	private SolicitudUsuario  solicitudSeleccionada;
	private DataTable tablaSolicitudes;
	private boolean mensajeError;

	public ManejadorConsultarSolicitudesUsuario() {
		try{
		String mostrar = (String) sesion.getAttribute("mensajeTramite");
        
		if(mostrar!=null){
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, mostrar,""));
			mensajeError=true;
			System.out.println(mostrar);
		}
		else{
			mensajeError=false;
		}
		
		listaSolicitudes = new ArrayList<SolicitudUsuario>();
		
		listaSolicitudes = servicioGeneral.obtenerObjetos("select s from SolicitudUsuario s where s.estadoSol = 'P' order by s.id desc" );
		//listaSolicitudes = servicioGeneral.obtenerObjetos(SolicitudUsuario.class,"from SolicitudUsuario r  order by r.id" );
		

		
		//para visualizar los datos completos de la solicitud cuando se despliega el acoerdeón
		listaSolicitudesEspecifico = new ArrayList();
		} catch (Exception e) {
    	    System.out.println(e.toString());
        }
	}

	
    
	public String administrar(){
	  try{
		sesion.removeAttribute("manejadorAdministrarSolicitudesUsuario");
		sesion.removeAttribute("solicitudId");
		sesion.setAttribute("solicitudId", solicitudSeleccionada.getId());
	   } catch (Exception e) {
  	    System.out.println(e.toString());
        }	
	  return "admSolUsuario";
	}
		

	public String editarSolicitudU(){
		sesion.removeAttribute("ManejadorConsultarSolicitudesUsuario");
		sesion.setAttribute("solicitudEditar", true);
		sesion.setAttribute("solicitudEditable", solicitudSeleccionada.getId());
		return "editarSolicitudUsuario";
	}
	
	public String consultarSolicitudU(){
		sesion.removeAttribute("ManejadorConsultarSolicitudesUsuario");
		sesion.setAttribute("solicitudEditar", false);
		sesion.setAttribute("solicitudEditable", solicitudSeleccionada.getId());
		return "editarSolicitudUsuario";
	}
	
	
	//
	public List getListaSolicitudesEspecifico() {
		return listaSolicitudesEspecifico;
	}


	public void setListaSolicitudesEspecifico(List listaSolicitudesEspecifico) {
		this.listaSolicitudesEspecifico = listaSolicitudesEspecifico;
	}


	public List getlistaSolicitudes() {
		return listaSolicitudes;
	}

	public void setlistaSolicitudes(List listaSolicitudes) {
		this.listaSolicitudes = listaSolicitudes;
	}
/*
	public List getListaHijo() {
		return listaHijo;
	}

	public void setListaHijo(List listaHijo) {
		this.listaHijo = listaHijo;
	}*/



	public SolicitudUsuario getSolicitudSeleccionada() {
		return solicitudSeleccionada;
	}



	public void setSolicitudSeleccionada(SolicitudUsuario solicitudSeleccionada) {
		this.solicitudSeleccionada = solicitudSeleccionada;
	}

	public boolean isMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(boolean mensajeError) {
		this.mensajeError = mensajeError;
	}

}
