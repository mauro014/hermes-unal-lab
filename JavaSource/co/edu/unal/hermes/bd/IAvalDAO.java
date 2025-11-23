package co.edu.unal.hermes.bd;

import java.util.List;

import co.edu.unal.hermes.modelo.Reporte;

/**
 * Interface para obtener atributos generales de la aplicacion
 * 
 * @trows lanza la excepcion cuando no se puede acceder a los datos
 */

public interface IAvalDAO {

    List<Reporte> obtenerListaCartas(String tipo);

}
