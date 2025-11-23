package co.edu.unal.hermes.bd;

import java.util.List;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.seguimiento.AlertaProyecto;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;

/**
 * The Interface IAlertaProyectoDAO.
 */
public interface IAlertaProyectoDAO {

    /**
     * Obtener alertas asesor inbox.
     *
     * @param asesor
     *            the asesor
     * @return the list
     */
    public List<AlertaProyecto> obtenerAlertasAsesorInbox(Persona asesor);

    /**
     * Obtener alerta proyecto solicitud.
     *
     * @param proyecto
     *            the proyecto
     * @param solicitud
     *            the solicitud
     * @return the list
     */
    public List<AlertaProyecto> obtenerAlertaProyectoSolicitud(Proyecto proyecto, Solicitud solicitud);
    
}
