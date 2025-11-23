package co.edu.unal.hermes.bd.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.IGrupoDAO;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoGrupo;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.GrupoProductoSara;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Sede;

// TODO: Auto-generated Javadoc
/**
 * Maneja el acceso a todos los grupos con Hibernate.
 */
public class GrupoDAOHibernate extends HibernateDaoSupport implements IGrupoDAO {

    /**
     * Obtener grupos.
     *
     * @param name the name
     * @return the list
     * @throws DataAccessException the data access exception
     */
    public List obtenerGrupos(String name) throws DataAccessException {

        Session session = getSession();

        Criterion crit_nombre = Restrictions.ilike("nombre", name, MatchMode.ANYWHERE);

        Criteria criteria = session.createCriteria(Grupo.class);
        criteria.add(crit_nombre);
        criteria.addOrder(Order.asc("nombre"));

        List result = new ArrayList();
        result.addAll(criteria.list());
        System.out.print("");

        session.close();

        return result;
    }
    
    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerGrupos(java.lang.String, boolean, java.lang.Object[])
     */
    public List<Grupo> obtenerGrupos(String where, boolean isParametros, Object[] parametros) throws DataAccessException {
        if (isParametros) {
            return getHibernateTemplate().find("gru from Grupo gru " + where, parametros);
        }

        return getHibernateTemplate().find("select gru from Grupo gru " + where + " order by gru.id");
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerProyectosGrupoEdicion(java.lang.String)
     */
    public List<String[]> obtenerProyectosGrupoEdicion(String grupoId) {
        List<String[]> result = new ArrayList<String[]>();

        String query = "SELECT P.PRY_ID, P.PRY_NOMBRE, GP.GRP_OPCION_ELIMINAR, eP.EPR_nombre "
                + "FROM  HER_GRUPO_PROYECTO GP, HER_PROYECTO P left join her_estado_proyecto ep on p.epr_id = ep.epr_id " + "WHERE GP.PRY_ID = P.PRY_ID AND GP.GRU_ID = '"
                + grupoId + "' " + "AND P.EPR_ID <> 'B'";
        Session session = getSession();
        SQLQuery sqlQuery = session.createSQLQuery(query);

        List<Object[]> proyectos = sqlQuery.addScalar("PRY_ID", Hibernate.STRING)
                .addScalar("PRY_NOMBRE", Hibernate.STRING).addScalar("GRP_OPCION_ELIMINAR", Hibernate.STRING).addScalar("EPR_nombre", Hibernate.STRING).list();

        session.close();

        Iterator<Object[]> it = proyectos.iterator();

        while (it.hasNext()) {
            Object[] objeto =  it.next();

            String[] proyecto = new String[5];
            proyecto[0] = (String) objeto[0];
            proyecto[1] = (String) objeto[1];
            proyecto[2] = (String) objeto[2];
            proyecto[3] = "G";
            proyecto[4] = (String) objeto[3];

            result.add(proyecto);
        }
        return result;
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerProductosSaraGrupo(java.lang.String)
     */
    public List<GrupoProductoSara> obtenerProductosSaraGrupo(String grupoId) {

        List<GrupoProductoSara> result = new ArrayList<GrupoProductoSara>();

        String query = "SELECT GPS.GRU_ID AS idGrupo, GPS.GPS_TIPO AS TIPO, GPS.GPS_NOMBRE AS NOMBRE, GPS_ID as id "
                + "FROM HER_GRUPO_PRODUCTO_SARA GPS " + "WHERE GPS.GRU_ID = '" + grupoId + "'";
        Session session = getSession();
        SQLQuery sqlQuery = session.createSQLQuery(query);

        List<Object[]> productos = sqlQuery.addScalar("idGrupo", Hibernate.LONG).addScalar("TIPO", Hibernate.STRING)
                .addScalar("NOMBRE", Hibernate.STRING).addScalar("id", Hibernate.LONG).list();

        session.close();

        Iterator<Object[]> it = productos.iterator();

        while (it.hasNext()) {
            Object[] objeto =  it.next();

            GrupoProductoSara gps = new GrupoProductoSara();
            Grupo grupo = new Grupo();

            grupo.setId((Long) objeto[0]);
            gps.setGrupo(grupo);
            gps.setTipoProducto((String) objeto[1]);
            gps.setNombreProducto((String) objeto[2]);
            gps.setId((Long) objeto[3]);
            result.add(gps);
        }
        return result;
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerGruposParaAval()
     */
    public List obtenerGruposParaAval() throws DataAccessException {

        Session session = getSession();

        EstadoGrupo estadoGrupo = new EstadoGrupo();
        estadoGrupo.setId("S");
        List result = session.createCriteria(Grupo.class).add(Restrictions.eq("estadoGrupo", estadoGrupo)).list();

        session.close();
        return result;
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerGruposPorCriterio(co.edu.unal.hermes.modelo.Grupo)
     */
    public List obtenerGruposPorCriterio(Grupo grupo) throws DataAccessException {

        Session session = getSession();

        Investigador responsable = new Investigador();
        InvestigadorGrupo ig = new InvestigadorGrupo();

        responsable.setApellido1(grupo.getResponsable().getApellido1());
        responsable.setApellido2(grupo.getResponsable().getApellido1());
        responsable.setNombre1(grupo.getResponsable().getNombre1());
        responsable.setNombre2(grupo.getResponsable().getNombre1());
        ig.setTipo("L");
        ig.setInvestigador(responsable);

        Criteria criterio_grupo = session.createCriteria(Grupo.class);

        if (grupo.getCategoria().getId() != null && grupo.getCategoria().getId() != "") {
            criterio_grupo.add(Restrictions.eq("categoria", grupo.getCategoria()));
        }

        if (grupo.getEstadoGrupo() != null && grupo.getEstadoGrupo().getId() != null
                && !grupo.getEstadoGrupo().getId().equals("")) {
            criterio_grupo.add(Restrictions.eq("estadoGrupo", grupo.getEstadoGrupo()));
        }

        if (grupo.getDependencia().getId() != null && grupo.getDependencia().getId() != "") {
            criterio_grupo.add(Restrictions.eq("dependencia", grupo.getDependencia()));
        }

        if (grupo.getDependencia().getSede().getId() != null
                && grupo.getDependencia().getSede().getId() != new Long("0")) {
            Criteria criterio_sede = criterio_grupo.createCriteria("dependencia");
            criterio_sede.add(Expression.eq("sede", grupo.getDependencia().getSede()));
        }

        Criteria criterio_responsable = criterio_grupo.createCriteria("investigadoresGrupo");
        criterio_responsable.add(Restrictions.ilike("tipo", "L", MatchMode.ANYWHERE));

        Criteria criterio_investigador = criterio_responsable.createCriteria("investigador");
        Junction nombre = Restrictions.disjunction();
        Junction apellido = Restrictions.disjunction();
        Junction conjuction = Restrictions.conjunction();

        if (responsable.getNombre1() != null && !responsable.getNombre1().trim().equalsIgnoreCase("")) {
            Criterion crit_nombre1 = Restrictions.ilike("nombre1", responsable.getNombre1(), MatchMode.ANYWHERE);
            Criterion crit_nombre2 = Restrictions.ilike("nombre2", responsable.getNombre1(), MatchMode.ANYWHERE);
            nombre = nombre.add(crit_nombre1);
            nombre = nombre.add(crit_nombre2);
            conjuction.add(nombre);
        }
        if (responsable.getApellido1() != null && !responsable.getApellido1().trim().equalsIgnoreCase("")) {
            Criterion crit_apellido1 = Restrictions.ilike("apellido1", responsable.getApellido1(), MatchMode.ANYWHERE);
            Criterion crit_apellido2 = Restrictions.ilike("apellido2", responsable.getApellido1(), MatchMode.ANYWHERE);
            apellido = apellido.add(crit_apellido1);
            apellido = apellido.add(crit_apellido2);
            conjuction.add(apellido);
        }

        criterio_investigador.add(conjuction);
        criterio_grupo.add(Restrictions.ilike("nombre", grupo.getNombre(), MatchMode.ANYWHERE));
        criterio_grupo.addOrder(Order.asc("nombre"));

        List result = new ArrayList();
        result.addAll(criterio_grupo.list());

        session.close();

        return result;
    }

    /**
     * Realiza busquedas dentro de los grupos por el Id.
     *
     * @param id the id
     * @return the grupo
     * @throws DataAccessException the data access exception
     */

    public Grupo buscarPorId(Long id) throws DataAccessException {
        return buscarPorId(id,false);
    }
    
    /**
     * Buscar por id.
     *
     * @param id the id
     * @param incluirProyectos the incluir proyectos
     * @return the grupo
     * @throws DataAccessException the data access exception
     */
    public Grupo buscarPorId(Long id, boolean incluirProyectos) throws DataAccessException{
        Session session = getSession();

        Grupo g = (Grupo) session.createCriteria(Grupo.class)
                // .setFetchMode("planAccion", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();
        if(incluirProyectos){
            Hibernate.initialize(g.getProyectos());
        }

        session.close();
        return g;
    }

    /**
     * Obtiene un resumen del grupo, como lo es sus datos basicos y su
     * responsable.
     *
     * @param id the id
     * @return the grupo
     * @throws DataAccessException the data access exception
     */
    public Grupo obtenerResumenGrupo(Long id) throws DataAccessException {
        Session session = getSession();
        Grupo g = null;

        try {

            g = (Grupo) session.createCriteria(Grupo.class).setFetchMode("investigadoresGrupo", FetchMode.JOIN)
                    .setFetchMode("proyectos", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * Map mapInvestigadores = g.getInvestigadores(); Set entrys =
         * mapInvestigadores.entrySet(); Iterator it = entrys.iterator();
         * while(it.hasNext()){ Map.Entry entry = (Map.Entry)it.next(); String
         * value = (String)entry.getValue();
         * if(value.equals(Investigador.PRINCIPAL)){ Investigador inv =
         * (Investigador)entry.getKey(); inv.getApellido1();
         * g.setResponsable(inv); } }
         */

        session.close();
        return g;
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerGrupoDatosBasicos(java.lang.Long)
     */
    public Grupo obtenerGrupoDatosBasicos(Long id) throws DataAccessException {
        Session session = getSession();
        Grupo g = null;
        List proyectosGru;
        Set<Proyecto> result = new HashSet();

        try {

            g = (Grupo) session.createCriteria(Grupo.class).setFetchMode("investigadoresGrupo", FetchMode.JOIN)
                    .add(Restrictions.idEq(id)).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Recuperación solo de datos necesarios de los proyectos para página de
        // grupos y que la consulta sea más eficiente

        String query = "select p.PRY_ID as idProyecto, p.PRY_NOMBRE as nombreProyecto, p.EPR_ID as estadoProyecto"
                + " from HER_GRUPO_PROYECTO gp, HER_PROYECTO p " + " where gp.GRU_ID = '" + id
                + "' and gp.PRY_ID = p.PRY_ID and p.EPR_ID in ('A','AP','F') order by p.PRY_ID asc";

        SQLQuery sqlQuery1 = session.createSQLQuery(query);

        proyectosGru = sqlQuery1.addScalar("idProyecto", Hibernate.LONG).addScalar("nombreProyecto", Hibernate.STRING)
                .addScalar("estadoProyecto", Hibernate.STRING).list();

        session.close();

        Iterator it = proyectosGru.iterator();

        while (it.hasNext()) {
            Object[] name = (Object[]) it.next();
            Proyecto p = new Proyecto();
            EstadoProyecto ep = new EstadoProyecto();

            p.setId((Long) name[0]);
            p.setNombre((String) name[1]);
            ep.setId((String) name[2]);
            p.setEstadoProyecto(ep);

            result.add(p);

        }

        g.setProyectos(result);

        return g;
    }

    /**
     * Se guarda el grupo.
     *
     * @param grupo the grupo
     * @throws DataAccessException the data access exception
     */
    public void guardarGrupo(Grupo grupo) throws DataAccessException {
    	//getHibernateTemplate().flush();
        getHibernateTemplate().saveOrUpdate(grupo);
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#crearGrupo(co.edu.unal.hermes.modelo.Grupo)
     */
    public void crearGrupo(Grupo grupo) throws DataAccessException {
        getHibernateTemplate().save(grupo);
    }

    /**
     * Se obtiene el máximo GRU_ID.
     *
     * @return the long
     * @throws DataAccessException the data access exception
     */
	public Long obtenerMaximoId() throws DataAccessException {
		Session session = getSession();
		Long gru_id = null;
		try {
			String query = "select max(ID) as ID from (select cast (GRU_ID as number) as ID from her_grupo)";
			Double id = (Double) session.createSQLQuery(query).addScalar("ID", Hibernate.DOUBLE).uniqueResult();
			Integer nuevo_id = new Integer(id.intValue() + 1);
			gru_id = Long.valueOf(nuevo_id.longValue());
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}
		session.close();
		return gru_id;
	}

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerProyectosGrupo(java.lang.Long)
     */
    public Grupo obtenerProyectosGrupo(Long id) throws DataAccessException {
        Session session = getSession();

        Grupo g = (Grupo) session.createCriteria(Grupo.class).setFetchMode("proyectos", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();

        // Grupo g = (Grupo)session.get(Grupo.class, id);

        session.close();
        return g;

    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerDependenciasGrupo(java.lang.Long)
     */
    public Grupo obtenerDependenciasGrupo(Long id) throws DataAccessException {
        Session session = getSession();

        Grupo g = (Grupo) session.createCriteria(Grupo.class).setFetchMode("dependencias", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();

        session.close();
        return g;
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerInvestigadoresGrupo(java.lang.Long)
     */
    public Set obtenerInvestigadoresGrupo(Long id) {
        Session session = getSession();

        Grupo g = (Grupo) session.createCriteria(Grupo.class).setFetchMode("investigadoresGrupo", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();

        session.close();
        return g.getInvestigadoresGrupo();
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerGrupoPlanAccionFinanciacion(java.lang.Long)
     */
    public Grupo obtenerGrupoPlanAccionFinanciacion(Long id) {
        Session session = getSession();

        Grupo g = (Grupo) session.createCriteria(Grupo.class).setFetchMode("planAccion", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();

        session.close();
        return g;
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerGrupoInvestigadores(java.lang.Long)
     */
    public Grupo obtenerGrupoInvestigadores(Long id) {
        Session session = getSession();

        Grupo g = (Grupo) session.createCriteria(Grupo.class).setFetchMode("investigadoresGrupo", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();

        session.close();
        return g;
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerGrupoPlanAccion(java.lang.Long)
     */
    public Grupo obtenerGrupoPlanAccion(Long id) {
        Session session = getSession();

        Grupo g = (Grupo) session.createCriteria(Grupo.class).setFetchMode("planAccion", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();

        session.close();
        return g;
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerGrupoLineas(java.lang.Long)
     */
    public Grupo obtenerGrupoLineas(Long id) throws DataAccessException {
        Session session = getSession();

        Grupo g = (Grupo) session.createCriteria(Grupo.class).setFetchMode("lineas", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();

        session.close();
        return g;
    }
    
    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerIntegrantesGrupo(java.lang.Long)
     */
    public List obtenerIntegrantesGrupo(Long idGrupo) {
        List integrantes;
        Session session = getSession();
        Criteria criteria = session.createCriteria(InvestigadorGrupo.class);
        String alias = criteria.getAlias() + "_";
        criteria.add(Expression.sqlRestriction(alias + ".gru_id = " + idGrupo.longValue()));
        integrantes = criteria.list();
        session.close();
        return integrantes;
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerGruposXInvestigador(java.lang.String)
     */
    public List obtenerGruposXInvestigador(String documento) {
        List listaGpInv;
        Session s = getSession();
        Query q = s.createQuery(
                " select g " + " from Grupo g " + " where g.investigadoresGrupo.investigador.id.documento=:documento ");
        q.setString("documento", documento);
        listaGpInv = q.list();
        s.close();
        return listaGpInv;

    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.IGrupoDAO#obtenerGrupoBuscador(java.lang.String)
     */
    /* Mauricio */
    public List<Grupo> obtenerGrupoBuscador(String sql) throws DataAccessException {
        List<Object> grupos;
        List<Grupo> resultado = new ArrayList<Grupo>();

        Session session = getSession();

        if (sql.length() > 0) {
            sql += " and ";
        } else
            sql = " where ";

        String query = "SELECT HER_GRUPO.GRU_ID as idGrupo, GRU_NOMBRE as nombreGrupo, HER_DEPENDENCIA.DPN_ID as idDependencia, HER_DEPENDENCIA.DPN_NOMBRE as dependenciaNombre,"
                + " HER_SEDE.SED_ID as idSede,  HER_SEDE.SED_NOMBRE as nombreSede "
                + " FROM HER_GRUPO, HER_DEPENDENCIA , HER_SEDE " + sql
                + " HER_GRUPO.DPN_ID = HER_DEPENDENCIA.DPN_ID and HER_DEPENDENCIA.SED_ID = HER_SEDE.SED_ID "
                + " and HER_GRUPO.EGR_ID = 'A' ORDER BY GRU_NOMBRE ";

        SQLQuery sqlQuery = session.createSQLQuery(query);

        grupos = sqlQuery.addScalar("idGrupo", Hibernate.LONG).addScalar("nombreGrupo", Hibernate.STRING)
                .addScalar("idDependencia", Hibernate.STRING).addScalar("dependenciaNombre", Hibernate.STRING)
                .addScalar("idSede", Hibernate.LONG).addScalar("nombreSede", Hibernate.STRING).list();

        session.close();

        Iterator<Object> it = grupos.iterator();

        while (it.hasNext()) {
            Object[] name = (Object[]) it.next();
            Grupo grupo = new Grupo();
            Dependencia dependencia = new Dependencia();
            Sede sede = new Sede();

            sede.setId((Long) name[4]);
            sede.setNombre((String) name[5]);

            dependencia.setId((String) name[2]);
            dependencia.setNombre((String) name[3]);
            dependencia.setSede(sede);

            grupo.setId((Long) name[0]);
            String nombre = (String) name[1];
            nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1, nombre.length()).toLowerCase();
            grupo.setNombre(nombre);
            grupo.setDependencia(dependencia);

            resultado.add(grupo);
        }

        return resultado;
    }

}
