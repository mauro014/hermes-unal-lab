package co.edu.unal.hermes.modelo.servicios.imp;

import java.util.List;

import co.edu.unal.hermes.bd.IAlertaProyectoDAO;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.seguimiento.AlertaProyecto;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.servicios.IServicioAlertas;

/**
 * The Class ServicioAlertas.
 */
public class ServicioAlertas implements IServicioAlertas {

	/** The alerta proyecto dao. */
	private IAlertaProyectoDAO alertaProyectoDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.modelo.servicioAlertas.IServicioAlertas#
	 * listarAlertasAsesorInbox(co.edu.unal.hermes.modelo.Persona)
	 */
	public List<AlertaProyecto> listarAlertasAsesorInbox(Persona asesor) {
		return alertaProyectoDao.obtenerAlertasAsesorInbox(asesor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.modelo.servicioAlertas.IServicioAlertas#
	 * obtenerAlertaProyectoSolicitud(co.edu.unal.hermes.modelo.Proyecto,
	 * co.edu.unal.hermes.modelo.seguimiento.Solicitud)
	 */
	public List<AlertaProyecto> obtenerAlertaProyectoSolicitud(Proyecto proyecto, Solicitud solicitud) {
		return alertaProyectoDao.obtenerAlertaProyectoSolicitud(proyecto, solicitud);
	}

	/**
	 * Gets the alerta proyecto dao.
	 *
	 * @return the alerta proyecto dao
	 */
	public IAlertaProyectoDAO getAlertaProyectoDao() {
		return alertaProyectoDao;
	}

	/**
	 * Sets the alerta proyecto dao.
	 *
	 * @param alertaProyectoDao
	 *            the new alerta proyecto dao
	 */
	public void setAlertaProyectoDao(IAlertaProyectoDAO alertaProyectoDao) {
		this.alertaProyectoDao = alertaProyectoDao;
	}
}
