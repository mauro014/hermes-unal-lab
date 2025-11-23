package co.edu.unal.hermes.bd;

import java.util.List;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.CaracterPropiedadIntelectual;
import co.edu.unal.hermes.modelo.ClasificacionEstadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.EstadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.HistoricoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.ObraFonogramaPropiedadIntelectual;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PropiedadIntelectual;
import co.edu.unal.hermes.modelo.SubEstadoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.SubTipoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoPersonaPropiedadIntelectual;
import co.edu.unal.hermes.modelo.TipoPropiedadIntelectual;

/**
 * Interface para obtener atributos generales de la aplicacion
 * 
 * @trows lanza la excepcion cuando no se puede acceder a los datos
 */

public interface IPropiedadIntelectualDAO {

    public List<TipoPropiedadIntelectual> obtenerTiposPropiedadIntelectual(String estado) throws DataAccessException;

    public List<SubTipoPropiedadIntelectual> obtenerSubTiposPropiedadIntelectual(Long tipo, String estado, Boolean esGestor)
            throws DataAccessException;

    public PropiedadIntelectual obtenerRegistroPropiedadIntelectual(Long id) throws DataAccessException;

    public List<PropiedadIntelectual> obtenerPropiedadesRegistradasPersona(Persona persona) throws DataAccessException;

    public CaracterPropiedadIntelectual obtenerCaracterPropiedadIntelectual(Long id) throws DataAccessException;

    public List<TipoPersonaPropiedadIntelectual> obtenerTiposPersonaPropiedadIntelectual(String subTipoPropiedad)
            throws DataAccessException;
    
    public List<TipoPersonaPropiedadIntelectual> obtenerTiposPersonaPropiedadIntelectual(TipoPropiedadIntelectual tipo)
            throws DataAccessException;

    public TipoPersonaPropiedadIntelectual obtenerTipoPersonaPropiedadIntelectual(String id) throws DataAccessException;

    public List<PropiedadIntelectual> obtenerPropiedadesPendientesNacional() throws DataAccessException;

    public List<PropiedadIntelectual> obtenerPropiedadesPendientesSede(Long id) throws DataAccessException;

    public List<ClasificacionEstadoPropiedadIntelectual> obtenerClasificacionEstadosPropiedadIntelectual(Long numeroOrden)
            throws DataAccessException;

    public List<EstadoPropiedadIntelectual> obtenerEstadosPropiedadIntelectual(String clasificacion, Long tipoPropiedad)
            throws DataAccessException;

    public List<SubEstadoPropiedadIntelectual> obtenerSubEstadosPropiedadIntelectual(String estado)
            throws DataAccessException;
    
    public List<HistoricoPropiedadIntelectual> obtenerHistoricoPropiedadIntelectual(Long id)
            throws DataAccessException;

    public ObraFonogramaPropiedadIntelectual obtenerObraFijadaFonograma(Long id) throws DataAccessException;
    
    public List<SubTipoPropiedadIntelectual> obtenerSubTiposPropiedadIntelectual()
            throws DataAccessException;

    public SubEstadoPropiedadIntelectual obtenerSubEstadoPropiedadIntelectual(Long id) throws DataAccessException;

    public List<Object[]> obtenerIndicadoresRapidos();

    public List<TipoArchivo> obtenerTiposArchivosPropiedadIntelectual();
    
    public EstadoPropiedadIntelectual obtenerEstadoPropiedadIntelectual(Long id) throws DataAccessException;

}
