package co.edu.unal.hermes.bd;

import java.util.List;

import co.edu.unal.hermes.modelo.seguimiento.TipoViaSolicitud;

/**
 * The Interface ITipoViaSolicitudDAO.
 */
public interface ITipoViaSolicitudDAO {

    /**
     * Buscar.
     *
     * @param tipoViaSolicitudBusqueda
     *            the tipo via solicitud busqueda
     * @return the list
     */
    public List<TipoViaSolicitud> buscar(TipoViaSolicitud tipoViaSolicitudBusqueda);

    /**
     * Buscar por id.
     *
     * @param id
     *            the id
     * @return the tipo via solicitud
     */
    public TipoViaSolicitud buscarPorId(Long id);

    /**
     * Listar activos.
     *
     * @return the list
     */
    public List<TipoViaSolicitud> listarActivos();

    /**
     * Guardar tipo via solicitud.
     *
     * @param tipoViaSolicitud
     *            the tipo via solicitud
     */
    public void guardarTipoViaSolicitud(TipoViaSolicitud tipoViaSolicitud);

    /**
     * Eliminar tipo via solicitud.
     *
     * @param tipoViaSolicitud
     *            the tipo via solicitud
     */
    public void eliminarTipoViaSolicitud(TipoViaSolicitud tipoViaSolicitud);

}
