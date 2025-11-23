package co.edu.unal.hermes.bd.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.IEvaluacionDAO;
import co.edu.unal.hermes.modelo.ModalidadCriterioTipoPregunta;
import co.edu.unal.hermes.modelo.OpcionMultiple;

/**
 * Maneja el acceso a los atributos generales del sistema con Hibernate
 */
public class EvaluacionDAOHibernate extends HibernateDaoSupport implements IEvaluacionDAO {

	public void guardarConceptoMesaSeleccionXProyecto() {

	}

	public ModalidadCriterioTipoPregunta obtenerModalidadTipoPreguntaXModalidadYCriterio(Long idModalidad,
			Long idCriterio) {
		Session session = null;
		Query q = null;

		ModalidadCriterioTipoPregunta mctp = null;
		try {
			session = getSession();

			q = session.createQuery("select mctp from ModalidadCriterioTipoPregunta mctp"
					+ " where mctp.criterio.id=:idCriterio and mctp.modalidad.id=:idModalidad");
			q.setLong("idModalidad", idModalidad.longValue());
			q.setLong("idCriterio", idCriterio.longValue());
			mctp = (ModalidadCriterioTipoPregunta) q.uniqueResult();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			if (session != null) {
				session.close();
			}
		}
		return mctp;
	}

	public OpcionMultiple obtenerOpcionMultipleXTipoPreguntaYValor(Long idTipoPregunta, Double valor) {
		Session session = null;
		Query q = null;

		OpcionMultiple om = null;
		try {
			session = getSession();

			q = session.createQuery("select om from TipoPregunta tp,OpcionMultiple om"
					+ " where tp.id=:idTipoPregunta and tp.opciones.id=om.id and om.valor=:valor"
					+ " order by om.id asc");
			q.setDouble("valor", valor.doubleValue());
			q.setLong("idTipoPregunta", idTipoPregunta.longValue());
			System.out.println("buscar tipoPregunt " + idTipoPregunta.longValue() + " y valor " + valor.longValue());
			om = (OpcionMultiple) q.uniqueResult();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			if (session != null) {
				session.close();
			}
		}
		return om;
	}

	public OpcionMultiple obtenerOpcionMultiplePorId(Long id) {

		OpcionMultiple opcionMultiple = null;

		Session session = getSession();

		Criteria criteria = session.createCriteria(OpcionMultiple.class);
		criteria.add(Restrictions.eq("id", id));

		try {
			opcionMultiple = (OpcionMultiple) criteria.uniqueResult();
		} finally {
			session.close();
		}
		return opcionMultiple;
	}

}
