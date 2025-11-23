package co.edu.unal.hermes.bd;


import java.util.Set;

import org.springframework.dao.DataAccessException;


/**
 * Interface para obtener atributos generales de la aplicacion
 * @trows lanza la excepcion cuando no se puede acceder a los datos  
 */

public interface IPalabraClaveDAO {	
	
    public Set obtenerProyectosPalabraClave(String palabraClave) throws DataAccessException;
    public Set obtenerProyectosPalabraClaveEstadoProyectoDiferente(String palabraClave,String idEstado) throws DataAccessException;
}
