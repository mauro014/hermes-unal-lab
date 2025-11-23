package co.edu.unal.hermes.bd;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.ArchivoAdjunto;
import co.edu.unal.hermes.modelo.CorreoDB;
import co.edu.unal.hermes.modelo.CorreoPlantilla;


public interface ICorreoDAO {	
	
    public ArchivoAdjunto obtenerArchivoAdjunto(Long id) throws DataAccessException;

    public CorreoPlantilla obtenerPlantillaCorreoCompleta(Long id);
    
    public void guardarMensajeNoEnviado(CorreoDB mensaje);
	
}
