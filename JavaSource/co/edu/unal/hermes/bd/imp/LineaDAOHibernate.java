package co.edu.unal.hermes.bd.imp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.ILineaDAO;
import co.edu.unal.hermes.modelo.LineaInvestigacion;
import co.edu.unal.hermes.utils.ReemplazaAcentos;

/**
 * Maneja el acceso a todas las Líneas con Hibernate.
 */
public class LineaDAOHibernate extends HibernateDaoSupport implements ILineaDAO {

    /**
     * Método que obtiene las líneas de investigacion por el nombre.
     *
     * @param name the name
     * @return the list
     * @throws DataAccessException the data access exception
     */
    public List<LineaInvestigacion> obtenerLineas(String name) throws DataAccessException {

        Session session = getSession();

        Criterion criterion = Restrictions.ilike("nombre", name, MatchMode.ANYWHERE);
        Criteria criteria = session.createCriteria(LineaInvestigacion.class);
        criteria.add(criterion);

        List<LineaInvestigacion> lineas = criteria.list();

        session.close();
        return lineas;
    }

    /**
     * Busqueda por Id de líneas de investigación.
     *
     * @param id the id
     * @return the linea investigacion
     * @throws DataAccessException the data access exception
     */
    public LineaInvestigacion buscarPorId(Long id) throws DataAccessException {

        Session session = getSession();

        LineaInvestigacion li = (LineaInvestigacion) session.createCriteria(LineaInvestigacion.class)
                .add(Restrictions.idEq(id)).uniqueResult();

        session.close();

        return li;

    }

    /**
     * Guarda línea de investigación cuando el investigador no encuentra una
     * para asociar, propone una nueva.
     *
     * @param linea the linea
     * @return the linea investigacion
     * @throws DataAccessException the data access exception
     */
    public LineaInvestigacion guardarLinea(LineaInvestigacion linea) throws DataAccessException {
        getHibernateTemplate().saveOrUpdate(linea);
        return linea;
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.ILineaDAO#obtenerLinea(java.lang.String)
     */
    public LineaInvestigacion obtenerLinea(String lineaInvestigacion) throws DataAccessException {
        Session session = getSession();

        Criterion criterion = Restrictions.ilike("nombre", lineaInvestigacion);
        Criteria criteria = session.createCriteria(LineaInvestigacion.class);
        criteria.add(criterion);

        List lineas = criteria.list();

        session.close();
        if (lineas.size() > 0) {
            return (LineaInvestigacion) lineas.get(0);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.ILineaDAO#listaNombreLineaXEmpienzaCon(java.lang.String)
     */
    public List listaNombreLineaXEmpienzaCon(String nombreLinea) {
        Session session = null;
        Query q = null;
        List resultado = null;

        try {
            session = getSession();

            q = session.createQuery("select l.nombre " + " from  LineaInvestigacion l where "
                    + ReemplazaAcentos.queryQuitaTildes("l.nombre") + " like :nombre" + " order by l.nombre");
            q.setString("nombre", "%" + ReemplazaAcentos.quitarTildes(nombreLinea.toLowerCase()) + "%");

            resultado = q.list();

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;

        }

        return resultado;

    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.ILineaDAO#listaLineasInvestigacionNombreContiene(java.lang.String)
     */
    public List<LineaInvestigacion> listaLineasInvestigacionNombreContiene(String nombreLinea) {
        Session session = null;
        Query q = null;
        List<LineaInvestigacion> resultado = null;
        try {
            session = getSession();
            String stringQuery = "SELECT li FROM LineaInvestigacion li WHERE "
                    + ReemplazaAcentos.queryLike("li.nombre", nombreLinea) + " order by li.nombre";

            q = session.createQuery(stringQuery);
            resultado = q.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        return resultado;
    }

}
