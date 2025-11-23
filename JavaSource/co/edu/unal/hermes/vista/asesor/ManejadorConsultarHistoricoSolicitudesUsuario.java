package co.edu.unal.hermes.vista.asesor;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.component.datatable.DataTable;

import co.edu.unal.hermes.modelo.SolicitudUsuario;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultarHistoricoSolicitudesUsuario extends
		ManejadorBase {

	List listaSolicitudes;
	List listaSolicitudesEspecifico;
	List objetosFiltrados;
	private SolicitudUsuario solicitudSeleccionada;
	private DataTable tablaSolicitudes;

	public ManejadorConsultarHistoricoSolicitudesUsuario() {
		try {
			listaSolicitudes = new ArrayList();

			// listaSolicitudes =
			// servicioGeneral.obtenerListaObjetos("SolicitudUsuario where id = '80' "
			// );
			listaSolicitudes = servicioGeneral
					.obtenerListaObjetos("SolicitudUsuario where estadoSol = 'A' or estadoSol = 'N' order by fechaSolicitud desc");
			// para visualizar los datos completos de la solicitud cuando se
			// despliega el acoerdeón
			listaSolicitudesEspecifico = new ArrayList();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public String administrar() {

		try {
			sesion.removeAttribute("manejadorVerSolicitudHistoricoUsuario");
			sesion.removeAttribute("solicitudId");
			sesion.setAttribute("solicitudId", solicitudSeleccionada.getId());
		} catch (Exception e) {
			// System.out.println(e.toString());
		}
		return "historicoSolicitud";
	}

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
	 * public List getListaHijo() { return listaHijo; }
	 * 
	 * public void setListaHijo(List listaHijo) { this.listaHijo = listaHijo; }
	 */

	public SolicitudUsuario getSolicitudSeleccionada() {
		return solicitudSeleccionada;
	}

	public void setSolicitudSeleccionada(SolicitudUsuario solicitudSeleccionada) {
		this.solicitudSeleccionada = solicitudSeleccionada;
	}

	public List getObjetosFiltrados() {
		return objetosFiltrados;
	}

	public void setObjetosFiltrados(List objetosFiltrados) {
		this.objetosFiltrados = objetosFiltrados;
	}

}
