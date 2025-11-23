package co.edu.unal.hermes.bd;

import java.util.List;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaTramiteBiodiversidad;
import co.edu.unal.hermes.modelo.TipoTramiteBiodiversidad;

/**
 * Interface para obtener atributos generales de la aplicacion
 * 
 * @trows lanza la excepcion cuando no se puede acceder a los datos
 */

public interface IBiodiversidadDAO {

    public List<TipoTramiteBiodiversidad> obtenerListaTramitesBiodiversidadPersona(Persona persona) throws DataAccessException;

    public TipoTramiteBiodiversidad obtenerRegistroTramiteBiodiversidad(Long id) throws DataAccessException;

    public List<PersonaTramiteBiodiversidad> obtenerPersonasAsignadasTramiteDependencia(Long tramite, String dependencia) throws DataAccessException;

    public Persona obtenerPersonaEncargadaBiodiversidadVicerrectoria() throws DataAccessException;
    
    public Persona obtenerPersonaControlBiodiversidad() throws DataAccessException;

    public Persona obtenerPersonaEncargadaSolicitudesEspecificasVicerrectoria() throws DataAccessException;

    public List<Parametro> obtenerPersonasEncargadasColeccionesBiologicas() throws DataAccessException;

    public List<Coleccion> obtenerColeccionesCandidatasAlertas();

}
