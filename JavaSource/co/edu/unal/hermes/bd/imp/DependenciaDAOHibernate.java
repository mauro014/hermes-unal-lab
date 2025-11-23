

package co.edu.unal.hermes.bd.imp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.IDependenciaDAO;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;

/**
 * The Class DependenciaDAOHibernate.
 */
public class DependenciaDAOHibernate extends HibernateDaoSupport implements IDependenciaDAO {

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IDependenciaDAO#obtenerDependencia(java.lang.String)
     */
    public Dependencia obtenerDependencia(String id) {
        Session s = getSession();
        Dependencia d = (Dependencia) s.createCriteria(Dependencia.class).add(Restrictions.idEq(id)).uniqueResult();
        s.close();
        return d;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IDependenciaDAO#obtenerDependenciasHija(co.edu.unal
     * .hermes.modelo.Dependencia, java.util.List)
     */
    public void obtenerDependenciasHija(Dependencia d, List<Dependencia> listaHijas) {
        Session s = getSession();
        Query q = s.createQuery(" select d from Dependencia d where d.padre.id=:idD ");
        q.setString("idD", d.getId());
        @SuppressWarnings("unchecked")
        List<Dependencia> listaDependencias = q.list();
        listaHijas.addAll(listaDependencias);
        for (Iterator<Dependencia> iteradorConvocatorias = listaDependencias.iterator(); iteradorConvocatorias
                .hasNext();) {
            Dependencia dependencia = (Dependencia) iteradorConvocatorias.next();
            obtenerDependenciasHija(dependencia, listaHijas);
        }
        s.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.bd.IDependenciaDAO#
     * obtenerDependenciasActivasFacultadYSede()
     */
    public List<Dependencia> obtenerDependenciasActivasFacultadYSede() {
        Session s = getSession();
        Query q = s.createQuery("select e from Dependencia e where e.estado='A' and "
                + "(e.esFacultad = 'Y' or e.esSede = 'Y') and e.nombre not like 'Centro%' and e.nombre not like "
                + "'Con%' and e.nombre not like 'Di%' order by e.nombre");
        @SuppressWarnings("unchecked")
        List<Dependencia> listaDependencias = q.list();
        s.close();
        return listaDependencias;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IDependenciaDAO#obtenerDependencia(co.edu.unal.
     * hermes.modelo.IdPersona)
     */
    public Dependencia obtenerDependencia(IdPersona id) throws DataAccessException {

        Session session = getSession();

        InvestigadorInterno i = (InvestigadorInterno) session.createCriteria(InvestigadorInterno.class)
                .setFetchMode("dependencia", FetchMode.JOIN).setFetchMode("dependencia.facultad", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();

        session.close();
        if (i != null)
            return i.getDependencia();
        else
            return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IDependenciaDAO#obtenerDependencia2(co.edu.unal.
     * hermes.modelo.IdPersona)
     */
    public Dependencia obtenerDependencia2(IdPersona id) throws DataAccessException {

        Session session = getSession();

        InvestigadorInterno i = (InvestigadorInterno) session.createCriteria(InvestigadorInterno.class)
                .setFetchMode("dependencia2", FetchMode.JOIN).setFetchMode("dependencia2.facultad", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();

        session.close();
        if (i != null)
            return i.getDependencia2();
        else
            return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IDependenciaDAO#obtenerDependenciaPersona(co.edu.
     * unal.hermes.modelo.IdPersona)
     */
    public Dependencia obtenerDependenciaPersona(IdPersona id) throws DataAccessException {

        Session session = getSession();
        InvestigadorInterno i = (InvestigadorInterno) session.createCriteria(InvestigadorInterno.class)
                .setFetchMode("dependencia", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return i.getDependencia();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IDependenciaDAO#obtenerDependenciaXSede(java.lang.
     * Long)
     */
    public List<Dependencia> obtenerDependenciaXSede(Long sedeId) {
        Session s = getSession();
        Query q = s.createQuery(
                "select e from Dependencia e where e.sede.id = '" + sedeId + "' and e.estado='A' and e.id <> '" + sedeId + "' order by e.nombre");
        @SuppressWarnings("unchecked")
        List<Dependencia> listaDependencias = q.list();
        s.close();
        return listaDependencias;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IDependenciaDAO#obtenerFacultadesXSede(java.lang.
     * Long)
     */
    public List<Dependencia> obtenerFacultadesXSede(Long sedeId) {
        Session s = getSession();
        Query q = s.createQuery("select e from Dependencia e where e.sede.id = '" + sedeId
                + "' and e.esFacultad = 'Y' and e.estado = 'A' order by e.nombre");
        @SuppressWarnings("unchecked")
        List<Dependencia> listaDependencias = q.list();
        s.close();
        return listaDependencias;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IDependenciaDAO#obtenerDependenciasXFacultad(java.
     * lang.Long)
     */
    public List<Dependencia> obtenerDependenciasXFacultad(String facultadId) {
        Session s = getSession();
        Query q = s.createQuery("select e from Dependencia e where e.facultad.id = '" + facultadId
                + "' and e.estado = 'A' order by e.nombre");
        @SuppressWarnings("unchecked")
        List<Dependencia> listaDependencias = q.list();
        s.close();
        return listaDependencias;
    }

}
