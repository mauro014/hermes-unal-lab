package co.edu.unal.hermes.bd;

import java.util.List;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.ModalidadCriterioTipoPregunta;
import co.edu.unal.hermes.modelo.OpcionMultiple;

/**
 * Interface para obtener atributos generales de la aplicacion.
 *
 * @trows lanza la excepcion cuando no se puede acceder a los datos
 */

public interface IEvaluacionDAO {
	
	/**
	 * Obtener modalidad tipo pregunta X modalidad Y criterio.
	 *
	 * @param idModalidad the id modalidad
	 * @param idCriterio the id criterio
	 * @return the modalidad criterio tipo pregunta
	 */
	public ModalidadCriterioTipoPregunta obtenerModalidadTipoPreguntaXModalidadYCriterio(Long idModalidad,Long idCriterio);
	
	/**
	 * Obtener opcion multiple X tipo pregunta Y valor.
	 *
	 * @param idTipoPregunta the id tipo pregunta
	 * @param valor the valor
	 * @return the opcion multiple
	 */
	public OpcionMultiple obtenerOpcionMultipleXTipoPreguntaYValor(Long idTipoPregunta,Double valor);
	
	/**
	 * Obtener opcion multiple por id.
	 *
	 * @param id the id
	 * @return the opcion multiple
	 */
	public OpcionMultiple obtenerOpcionMultiplePorId(Long id);
}
