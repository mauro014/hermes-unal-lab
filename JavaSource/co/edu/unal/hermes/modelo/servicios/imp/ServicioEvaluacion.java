/*
 * Created on 09-ago-2005
 */
package co.edu.unal.hermes.modelo.servicios.imp;

import java.util.List;

import co.edu.unal.hermes.bd.IEvaluacionDAO;
import co.edu.unal.hermes.bd.IGeneralDAO;
import co.edu.unal.hermes.modelo.CalificacionEvaluacion;
import co.edu.unal.hermes.modelo.CriterioEvaluacion;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.ModalidadCriterioTipoPregunta;
import co.edu.unal.hermes.modelo.OpcionMultiple;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.TipoPregunta;
import co.edu.unal.hermes.modelo.servicios.IServicioEvaluacion;

/**
 * The Class ServicioEvaluacion.
 */
public class ServicioEvaluacion implements IServicioEvaluacion {

	/** The evaluacion dao. */
	private IEvaluacionDAO evaluacionDAO;

	/** The general dao. */
	private IGeneralDAO generalDAO;

	/**
	 * Gets the evaluacion dao.
	 *
	 * @return Returns the indicadoresDAO.
	 */
	public IEvaluacionDAO getEvaluacionDAO() {
		return evaluacionDAO;
	}

	/**
	 * Sets the evaluacion dao.
	 *
	 * @param evaluacionDAO
	 *            the new evaluacion dao
	 */
	public void setEvaluacionDAO(IEvaluacionDAO evaluacionDAO) {
		this.evaluacionDAO = evaluacionDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.modelo.servicioEvaluacion.IServicioEvaluacion#
	 * obtenerModalidadTipoPreguntaXModalidadYCriterio(java.lang.Long,
	 * java.lang.Long)
	 */
	public ModalidadCriterioTipoPregunta obtenerModalidadTipoPreguntaXModalidadYCriterio(Long idModalidad,
			Long idCriterio) {
		return evaluacionDAO.obtenerModalidadTipoPreguntaXModalidadYCriterio(idModalidad, idCriterio);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.modelo.servicioEvaluacion.IServicioEvaluacion#
	 * convertirCalificaion(co.edu.unal.hermes.modelo.CalificacionEvaluacion)
	 */
	public CalificacionEvaluacion convertirCalificaion(CalificacionEvaluacion ce) {
		CriterioEvaluacion criE = ce.getCriterio();
		ProyectoEvaluador pe = (ProyectoEvaluador) generalDAO.obtenerObjeto(new ProyectoEvaluador(),
				ce.getProyectoEvaluador().getId());
		Modalidad m = pe.getProyecto().getModalidad();
		System.out.println("modalidad " + m.getId());
		ModalidadCriterioTipoPregunta mctp = obtenerModalidadTipoPreguntaXModalidadYCriterio(m.getId(), criE.getId());
		System.out.println("mctp" + mctp.getId());
		if (mctp.getTipoPregunta().getId().longValue() == TipoPregunta.cualitaticacuantivativa) {
			System.out.println("cualitativa");
			return ce;
		}
		if (mctp.getTipoPregunta().getId().longValue() == TipoPregunta.cualitativa) {
			System.out.println("cualitativa");
			return ce;
		}

		if (mctp.getTipoPregunta().getId().longValue() == TipoPregunta.seleccionUnica) {
			if (ce != null && ce.getCuantitativa() != null) {
				System.out.println("opcion multiple" + ce.getCuantitativa());

				OpcionMultiple om = (OpcionMultiple) generalDAO.obtenerObjeto(new OpcionMultiple(),
						new Long(ce.getCuantitativa().longValue()));
				if (om != null) {

					ce.setCuantitativa(new Float(om.getValor().floatValue()));
					ce.setCualitativa(om.getNombre());
					return ce;
				}
			}
		}

		return ce;
	}

	/**
	 * Gets the general dao.
	 *
	 * @return the general dao
	 */
	public IGeneralDAO getGeneralDAO() {
		return generalDAO;
	}

	/**
	 * Sets the general dao.
	 *
	 * @param generalDAO
	 *            the new general dao
	 */
	public void setGeneralDAO(IGeneralDAO generalDAO) {
		this.generalDAO = generalDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.modelo.servicioEvaluacion.IServicioEvaluacion#
	 * obtenerOpcionMultipleXTipoPreguntaYValor(java.lang.Long,
	 * java.lang.Double)
	 */
	public OpcionMultiple obtenerOpcionMultipleXTipoPreguntaYValor(Long idTipoPreguenta, Double valor) {
		return evaluacionDAO.obtenerOpcionMultipleXTipoPreguntaYValor(idTipoPreguenta, valor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.modelo.servicioEvaluacion.IServicioEvaluacion#
	 * obtenerOpcionMultiplePorId(java.lang.Long)
	 */
	public OpcionMultiple obtenerOpcionMultiplePorId(Long id) {
		return evaluacionDAO.obtenerOpcionMultiplePorId(id);
	}

}
