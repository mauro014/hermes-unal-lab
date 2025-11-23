package co.edu.unal.hermes.bd;

import java.util.List;
import java.util.Set;

import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;

/**
 * The Interface ITipoSolicitudDAO.
 */
public interface ITipoSolicitudDAO {

    /**
     * Buscar.
     *
     * @param tipoSolicitud
     *            the tipo solicitud
     * @return the list
     */
    public List<TipoSolicitud> buscar(TipoSolicitud tipoSolicitud);

    /**
     * Listar activos.
     *
     * @return the list
     */
    public List<TipoSolicitud> listarActivos();

    /**
     * Buscar por id.
     *
     * @param id
     *            the id
     * @return the tipo solicitud
     */
    public TipoSolicitud buscarPorId(Long id);

    /**
     * Guardar tipo solicitud.
     *
     * @param tipoSolicitud
     *            the tipo solicitud
     */
    public void guardarTipoSolicitud(TipoSolicitud tipoSolicitud);

    /**
     * Eliminar tipo solicitud.
     *
     * @param tipoSolicitud
     *            the tipo solicitud
     */
    public void eliminarTipoSolicitud(TipoSolicitud tipoSolicitud);

    /**
     * Buscar por varios ids.
     *
     * @param ids
     *            the ids
     * @return the list
     */
    public List<TipoSolicitud> buscarPorVariosIds(Set<String> ids);

}
