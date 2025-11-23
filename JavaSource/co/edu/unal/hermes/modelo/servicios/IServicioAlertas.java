/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Interface   : co.edu.unal.hermes.modelo.servicioAlertas.IServicioAlertas
Objetivo 	: Servicio de alertas  que  se pueden presentar  para  un proyecto de 
			  investigaciòn.
Creación	: Agosto 15 de 2007
Modificación:
Detalle		:
********************************************************************************/

package co.edu.unal.hermes.modelo.servicios;

import java.util.List;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.seguimiento.AlertaProyecto;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;

/**
 * The Interface IServicioAlertas.
 */
public interface IServicioAlertas {

	/**
	 * Listar alertas asesor inbox.
	 *
	 * @param asesor
	 *            the asesor
	 * @return the list
	 */
	public List<AlertaProyecto> listarAlertasAsesorInbox(Persona asesor);

	/**
	 * Obtener alerta proyecto solicitud.
	 *
	 * @param proyecto
	 *            the proyecto
	 * @param solicitud
	 *            the solicitud
	 * @return the alerta proyecto
	 */
	public List<AlertaProyecto> obtenerAlertaProyectoSolicitud(Proyecto proyecto, Solicitud solicitud);

}
