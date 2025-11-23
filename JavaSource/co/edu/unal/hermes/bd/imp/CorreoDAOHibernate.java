package co.edu.unal.hermes.bd.imp;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.ICorreoDAO;
import co.edu.unal.hermes.modelo.ArchivoAdjunto;
import co.edu.unal.hermes.modelo.CorreoDB;
import co.edu.unal.hermes.modelo.CorreoPlantilla;

/**
 * Maneja el acceso a los atributos del correo del sistema Hermes con Hibernate.
 */
public class CorreoDAOHibernate extends HibernateDaoSupport implements ICorreoDAO {

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.ICorreoDAO#obtenerArchivoAdjunto(java.lang.Long)
     */
    public ArchivoAdjunto obtenerArchivoAdjunto(Long id) throws DataAccessException {

        Session session = null;
        try {

            session = getSession();

            CorreoPlantilla plantilla = (CorreoPlantilla) session.createCriteria(CorreoPlantilla.class)
                    .setFetchMode("adjunto", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

            return plantilla.getAdjunto();
        } catch (Exception e) {
        } finally {
            if (session != null)
                session.close();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.ICorreoDAO#obtenerPlantillaCorreoCompleta(java.lang.Long)
     */
    public CorreoPlantilla obtenerPlantillaCorreoCompleta(Long id) {
        Session session = getSession();

        CorreoPlantilla plantilla = (CorreoPlantilla) session.createCriteria(CorreoPlantilla.class)
                .setFetchMode("adjunto", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

        session.close();
        return plantilla;
    }

	@Override
	public void guardarMensajeNoEnviado(CorreoDB mensaje) {
		Session session = getSession();
		Transaction tr = session.beginTransaction();
		try {
			session.save(mensaje);
			session.saveOrUpdate(mensaje);
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		session.close();
	}

}
