package co.edu.unal.hermes.vista.asesor;

import java.util.ArrayList;
import java.util.List;

import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.SolicitudUsuario;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorVerSolicitudHistoricoUsuario extends ManejadorBase {

	List listaSolicitudesAdministrar;
	List listaRoles;
	List investigadorInterno;
	List investigador;
	List personaSeleccionada;
	private SolicitudUsuario solicitudadmin;
	private Persona solicitudadminSol;
	private Persona solicitudResponsable;

	private Long id;

	public ManejadorVerSolicitudHistoricoUsuario() {
		try {

			listaSolicitudesAdministrar = new ArrayList();
			listaRoles = new ArrayList();

			/*listaSolicitudesAdministrar = servicioGeneral
					.obtenerListaObjetos("SolicitudUsuario where id = '" + id
							+ "'");
			listaRoles = servicioGeneral
					.obtenerListaObjetos("SolicitudPersonaRol where idSolicitud = '"
							+ id + "'");*/
			
			id = (Long) sesion.getAttribute("solicitudId");
			if(id!=null){
				listaSolicitudesAdministrar = servicioGeneral
						.obtenerListaObjetos("SolicitudUsuario where id = '" + id + "'");
				if(listaSolicitudesAdministrar.size()>0){
					solicitudadmin = (SolicitudUsuario) listaSolicitudesAdministrar.get(0);
					if(solicitudadmin!=null && solicitudadmin.getSolicDocumento()!=null){
						IdPersona idPer = new IdPersona(solicitudadmin.getSolicDocumento(), solicitudadmin.getSolicTipoDocumento());
						solicitudadminSol = servicioPersona.obtenerPersona(idPer);
						
						//Responsable
						IdPersona idPerResp = new IdPersona(solicitudadmin.getResponsableDocumento(), solicitudadmin.getResponsableTipoDocumento());
						solicitudResponsable = servicioPersona.obtenerPersona(idPerResp);
						
						listaRoles = servicioGeneral
								.obtenerListaObjetos("SolicitudPersonaRol where idSolicitud = '"	+ id + "'");
						
					}
					
				}	
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// metodod redireccionar a la página de historico solicitudes
	public String regresarSolicitud() {
		try {
			sesion.removeAttribute("manejadorConsultarHistoricoSolicitudesUsuario");
			sesion.removeAttribute("manejadorVerSolicitudHistoricoUsuario");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "historicoVerSolicitud";
	}

	public List getlistaRoles() {
		return listaRoles;
	}

	public void setlistaRoles(List listaRoles) {
		this.listaRoles = listaRoles;
	}

	public List getListaSolicitudesAdministrar() {
		return listaSolicitudesAdministrar;
	}

	public void setListaSolicitudesAdministrar(List listaSolicitudesAdministrar) {
		this.listaSolicitudesAdministrar = listaSolicitudesAdministrar;
	}

	public SolicitudUsuario getSolicitudadmin() {
		return solicitudadmin;
	}

	public void setSolicitudadmin(SolicitudUsuario solicitudadmin) {
		this.solicitudadmin = solicitudadmin;
	}

	public Persona getSolicitudadminSol() {
		return solicitudadminSol;
	}

	public void setSolicitudadminSol(Persona solicitudadminSol) {
		this.solicitudadminSol = solicitudadminSol;
	}

	public List getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List listaRoles) {
		this.listaRoles = listaRoles;
	}

	public Persona getSolicitudResponsable() {
		return solicitudResponsable;
	}

	public void setSolicitudResponsable(Persona solicitudResponsable) {
		this.solicitudResponsable = solicitudResponsable;
	}
	

}