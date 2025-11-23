/*
 * Created on 09-ago-2005
 */
package co.edu.unal.hermes.modelo.servicios;

import co.edu.unal.hermes.modelo.CalificacionEvaluacion;
import co.edu.unal.hermes.modelo.ModalidadCriterioTipoPregunta;
import co.edu.unal.hermes.modelo.OpcionMultiple;

/**
 * The Interface IServicioEvaluacion.
 */
public interface IServicioEvaluacion {

	/**
	 * Obtener modalidad tipo pregunta x modalidad y criterio.
	 *
	 * @param idModalidad
	 *            the id modalidad
	 * @param idCriterio
	 *            the id criterio
	 * @return the modalidad criterio tipo pregunta
	 */
	public ModalidadCriterioTipoPregunta obtenerModalidadTipoPreguntaXModalidadYCriterio(Long idModalidad,
			Long idCriterio);

	/**
	 * Convertir calificaion.
	 *
	 * @param ce
	 *            the ce
	 * @return the calificacion evaluacion
	 */
	public CalificacionEvaluacion convertirCalificaion(CalificacionEvaluacion ce);

	/**
	 * Obtener opcion multiple x tipo pregunta y valor.
	 *
	 * @param idTipoPregunta
	 *            the id tipo pregunta
	 * @param valor
	 *            the valor
	 * @return the opcion multiple
	 */
	public OpcionMultiple obtenerOpcionMultipleXTipoPreguntaYValor(Long idTipoPregunta, Double valor);

	/**
	 * Obtener opcion multiple por id.
	 *
	 * @param id
	 *            the id
	 * @return the opcion multiple
	 */
	public OpcionMultiple obtenerOpcionMultiplePorId(Long id);
}
