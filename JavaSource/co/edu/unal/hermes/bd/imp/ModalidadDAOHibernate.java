
package co.edu.unal.hermes.bd.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.IModalidadDAO;
import co.edu.unal.hermes.modelo.CalificacionEvaluacion;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.ConvocatoriaPadreParametrizacion;
import co.edu.unal.hermes.modelo.ConvocatoriaParametrizacion;
import co.edu.unal.hermes.modelo.ConvocatoriaTerminosReferencia;
import co.edu.unal.hermes.modelo.CorteConvocatoria;
import co.edu.unal.hermes.modelo.CriterioEvaluacion;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.JornadaDocente;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.ModalidadCriterioTipoPregunta;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.RubroFinanciable;
import co.edu.unal.hermes.modelo.RubroFinanciableArbol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.TipoRubro;

/**
 * Maneja el acceso a todas las convocatorias con Hibernate
 */
public class ModalidadDAOHibernate extends HibernateDaoSupport implements IModalidadDAO {

    public static String Contrapartida = "T";

    public List resumenEstadoProyectosMovilidad(String idConvocatoria, String tipoConvocatoria) throws SQLException {
        Session session = getSession();
        Statement st = null;
        ResultSet rs;
        List result = new ArrayList();

        try {
            String nombreTabla = "";
            if (tipoConvocatoria.equals("11")) {
                nombreTabla = "her_movilidad_visitantes_ext";
            }
            if (tipoConvocatoria.equals("21")) {
                nombreTabla = "her_movilidad_docentes_eventos";
            }
            if (tipoConvocatoria.equals("3")) {
                nombreTabla = "her_movilidad_estudiante_pos";
            }
            if (tipoConvocatoria.equals("41")) {
                nombreTabla = "her_movilidad_estudiante_pos";
            }
            st = session.connection().createStatement();
            if (!nombreTabla.equals("")) {
                String sql = "select count(mov.mov_id), dd.domdet_descripcion as estado_descripcion, mov.estado_id "
                	    + "from "
                	    + "(select mov.mov_id, "
                	    + "case when (mov.mov_aprob is null or mov.mov_aprob = '') and (mov.mov_acept is null or mov.mov_acept = '') "
                	    + "then mov.mov_estado "
                	    + "else decode(mov.mov_aprob, '', '','SI','A','NO','R','') || "
                	    + "decode(mov.mov_acept, '', 'I','SI','AP','NO','N','') end as estado_id "
                	    + "from " + nombreTabla + " mov "
                	    + "where mov.con_id = '" + idConvocatoria + "' "
                	    + "and mov.mov_id_per not in ('19380666')) mov, "
                	    + "her_dominio_detalle dd "
                	    + "where dd.dom_id = '58' and dd.domdet_tipo = mov.estado_id "
                	    + "group by dd.domdet_descripcion, mov.estado_id ";

                rs = st.executeQuery(sql);
                while (rs.next()) {
                    String sal[] = new String[4];
                    sal[0] = rs.getString(1);
                    sal[1] = rs.getString(2);
                    sal[2] = idConvocatoria;
                    sal[3] = rs.getString(3);
                    result.add(sal);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    public List<RubroFinanciableArbol> getRubroFinanciableArbol(Long idModalidadFuenteFinanciacion) throws DataAccessException {
        Session session = getSession();

        String sql = "select rfa from RubroFinanciableArbol rfa where rfa.modalidadFuenteFinanciacion.arbol = '"
                + idModalidadFuenteFinanciacion + "' and rfa.padre = '0' order by rfa.orden asc";
        Query query = session.createQuery(sql);
        @SuppressWarnings("unchecked")
        List<RubroFinanciableArbol> result = query.list();

        session.close();
        return result;
    }

    public List resumenEstadoProyectos(String idConvocatoria) throws SQLException {
        Session session = getSession();
        Statement st = null;
        ResultSet rs;
        List result = new ArrayList();

        try {

            st = session.connection().createStatement();
            rs = st.executeQuery(
                    "Select COUNT(P.PRY_ID), e.EPR_NOMBRE, e.epr_id  from her_proyecto p, her_estado_proyecto e, her_investigador_proyecto ip where p.EPR_ID = e.EPR_ID and p.MOD_ID = "
                            + idConvocatoria + "and ip.PRY_ID=p.PRY_ID and ip.INP_TIPO='P' and ip.INV_ID not in (select * from vu_usuariosprueba) and ip.INV_ID not in ('52015719', '52585723', '52116940', '1023876575', '1032380386', '1053799116', '19380666', '52205496', '41654902', '1022389470', '52980878') and p.EPR_ID <> 'B' group by P.EPR_ID, e.EPR_NOMBRE, e.epr_id");

            while (rs.next()) {
                String sal[] = new String[4];
                sal[0] = rs.getString(1);
                sal[1] = rs.getString(2);
                sal[2] = idConvocatoria;
                sal[3] = rs.getString(3);
                result.add(sal);
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    public List<TipoRubro> obtenerRubros(Long idFiltro, int opcion) throws DataAccessException {
        List filtroRubro;
        List result = new ArrayList();

        Session session = getSession();
        
        String filtro;
        if (opcion == 1) {
            filtro = "where TRU_ID != " + idFiltro;
        } else {
            filtro = "where TRU_ID_PADRE != " + idFiltro;
        }

        String query = "select TRU_ID as id,TRU_NOMBRE as nombre, TRU_DESCRIPCION AS descripcion, TRU_ID_PADRE as padre "
                + "from HER_TIPO_RUBRO " + filtro + " order by TRU_NOMBRE ASC";

        SQLQuery sqlQuery1 = session.createSQLQuery(query);

        filtroRubro = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
                .addScalar("descripcion", Hibernate.STRING).addScalar("padre", Hibernate.LONG).list();

        session.close();

        Iterator it = filtroRubro.iterator();

        while (it.hasNext()) {
            Object[] name = (Object[]) it.next();
            TipoRubro tipoRubro = new TipoRubro();
            TipoRubro tipoRubroPadre = new TipoRubro();

            try {
                if ((Long) name[3] != null && ((Long) name[3]) != 0) {
                    List listaPadreTipoRubro = obtenerListaObjetos("TipoRubro where id ='" + (Long) name[3] + "'");
                    tipoRubroPadre = (TipoRubro) listaPadreTipoRubro.get(0);
                } else {
                    tipoRubroPadre = null;
                }
            } catch (Exception iob) {
                tipoRubroPadre = null;
            }

            tipoRubro.setId((Long) name[0]);
            tipoRubro.setNombre((String) name[1]);
            tipoRubro.setDescripcion((String) name[2]);
            tipoRubro.setPadre(tipoRubroPadre);

            result.add(tipoRubro);
        }
        return result;
    }

    public List obtenerListaObjetos(String clase) throws DataAccessException {
        return getHibernateTemplate().find("from " + clase);
    }

    public List obtenerConvocatorias(String where, boolean isParametros, Object[] parametros)
            throws DataAccessException {
        if (isParametros) {
            return getHibernateTemplate().find("select con from Convocatoria con " + where, parametros);
        }

        return getHibernateTemplate().find("select con from Convocatoria con " + where);
    }

    public void guardarConvocatoria(Convocatoria convocatoria) throws DataAccessException {
        getHibernateTemplate().saveOrUpdate(convocatoria);
    }

    /**
     * Obtiene una convocatoria por su llave primaria
     */
    public Convocatoria obtenerConvocatoria(Long id) throws DataAccessException {
        return (Convocatoria) getHibernateTemplate().get(Convocatoria.class, id);
    }

    public List obtenerConvocatorias(EstadoConvocatoria estado) throws DataAccessException {
        Session session = getSession();

        Query query = session.createQuery("from Convocatoria where estadoConvocatoria = :estado");
        query = query.setEntity("estado", estado);
        List result = query.list();

        session.close();
        return result;

    }

    public Modalidad obtenerModalidad(Long id) throws DataAccessException {
        return (Modalidad) getHibernateTemplate().get(Modalidad.class, id);
    }

    public Convocatoria obtenerConvocatoriaEdicion(Long id) throws DataAccessException {
        Session session = getSession();

        Convocatoria convocatoria = (Convocatoria) session.createCriteria(Convocatoria.class)
                .setFetchMode("rubrosFinanciables", FetchMode.JOIN)
                .setFetchMode("productosConvocatoria", FetchMode.JOIN).setFetchMode("dependencia", FetchMode.JOIN)
                .setFetchMode("dependenciaRestriccion", FetchMode.JOIN).setFetchMode("productos", FetchMode.JOIN)
                .setFetchMode("compromisos", FetchMode.JOIN).setFetchMode("requisitos", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();

        session.close();
        return convocatoria;
    }

    public Convocatoria obtenerConvocatoriaRequisitos(Long id) throws DataAccessException {
        Session session = getSession();

        Convocatoria convocatoria = (Convocatoria) session.createCriteria(Convocatoria.class)
                .setFetchMode("requisitos", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

        session.close();
        return convocatoria;
    }

    public List<Convocatoria> obtenerConvocatoriasxPadre(ConvocatoriaPadre padre) throws DataAccessException {
        Session session = getSession();

        Criteria criteria = session.createCriteria(Convocatoria.class).add(Expression.eq("padre", padre))
                .addOrder(Order.asc("id"));
        List<Convocatoria> result = criteria.list();

        session.close();
        return result;
    }
    
    

    /**
     * @param idPadre
     *            : Identificador de la convocatoria Padre
     * @param idCoordinador
     *            : Identificador del coordinador
     * @return Convocatorias para sedes validas para el coordinador
     * @throws DataAccessException
     */
    public List obtenerConvocatoriasXPadreYCoordinador(Long idPadre, IdPersona idCoordinador)
            throws DataAccessException {
        List listaConvocatorias = new ArrayList();
        Session session;
        Statement sentencia = null;
        session = getSession();
        String query = "SELECT con_id id, con_titulo, dpn_restriccion, c.sed_id "
                + "FROM her_convocatoria c,  her_dependencia hdp  " + "WHERE  c.dpn_id = hdp.dpn_id "
                + "And c.dpn_id = ANY(SELECT DISTINCT dpn_id id_dependencia " + "FROM her_dependencia "
                + "WHERE dpn_estado = 'A' " + "START WITH dpn_id = ANY(SELECT dpn_id "
                + "FROM her_seg_facultad_persona " + "WHERE per_id = '" + idCoordinador.getDocumento() + "' "
                + "AND tdo_id = '" + idCoordinador.getTipoDocumento() + "') "
                + "CONNECT BY NOCYCLE PRIOR dpn_id = ANY(dpn_departamento,dpn_facultad,sed_id) " + ") "
                + "AND cnp_id = " + idPadre.longValue();
        try {
            sentencia = session.connection().createStatement();
            sentencia.execute(query);
            ResultSet rs = sentencia.getResultSet();

            while (rs.next()) {
                Convocatoria convocatoria = new Convocatoria();
                convocatoria.setId(new Long(rs.getLong(1)));
                convocatoria.setTitulo(rs.getString(2));
                convocatoria.setDependenciaRestriccion(new Dependencia(rs.getString(3)));
                convocatoria.setSede(new Sede(Long.valueOf(rs.getString(4))));
                listaConvocatorias.add(convocatoria);
            }
        } catch (Exception ex) {
        } finally {
            if (session.isOpen())
                session.close();
        }

        return listaConvocatorias;
    }

    public List obtenerRubrosFinanciables(Modalidad conv) throws DataAccessException {
        Modalidad modalidad = null;
        Session session = getSession();

        List lmodalidad = session.createCriteria(Modalidad.class).setFetchMode("rubrosFinanciables", FetchMode.JOIN)
                .add(Restrictions.idEq(conv.getId())).list();

        // .uniqueResult();

        if (lmodalidad != null && lmodalidad.size() > 0) {
            for (int i = 0; i < lmodalidad.size(); i++) {
                modalidad = (Modalidad) lmodalidad.get(i);
                if (modalidad instanceof ConvocatoriaPadre) {

                } else {
                    break;
                }
            }
        }

        session.close();
        if (modalidad != null) {
            return modalidad.getListaRubrosFinanciables();
        } else {
            return null;
        }

    }

    public List obtenerProductosConvocatoria(Convocatoria conv) throws DataAccessException {
        Session session = getSession();

        Convocatoria convocatoria = (Convocatoria) session.createCriteria(Convocatoria.class)
                .setFetchMode("productos", FetchMode.JOIN).add(Restrictions.idEq(conv.getId())).uniqueResult();

        session.close();
        List l = new ArrayList();
        l.addAll(convocatoria.getProductos());
        return l;
    }

    public List<ProductoTipo> obtenerProductosFormularioInforme() {
        try {
            Session session = getSession();
            String productosInformes = "'1','2','3','61','70'";
            Query query = session.createQuery("select pt from ProductoTipo pt where pt.id in (" + productosInformes
                    + ") or pt.padre.id in (" + productosInformes + ") or pt.padre.padre.id in (" + productosInformes
                    + ") order by pt.nombre");
            List<ProductoTipo> resultado = query.list();
            session.close();
            return resultado;
        } catch (DataAccessException dae) {
            return null;
        }
    }

    public List listaCriterios(Modalidad m) {
        Session session = null;
        Query q = null;
        List resultado = null;
        try {
            session = getSession();
            q = session.createQuery("select mctp.criterio from ModalidadCriterioTipoPregunta mctp"
                    + " where mctp.modalidad.id=:idModalidad order by mctp.orden ");
            q.setLong("idModalidad", m.getId().longValue());
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resultado;
    }
    
    public List<ConvocatoriaPadreParametrizacion> obtenerParametrosConvocatoriasPadre(ConvocatoriaPadre convPadre, Long tipoParam) {
        Session session = null;
        Query q = null;
        List<ConvocatoriaPadreParametrizacion> resultado = null;
        try {
            session = getSession();
            q = session.createQuery("select cpp from ConvocatoriaPadreParametrizacion cpp"
                    + " where cpp.convocatoriaPadre.id=:idConvPadre AND cpp.tipoParametro.id = :idTipoParam order by cpp.dependencia.id");
            q.setLong("idConvPadre", convPadre.getId().longValue());
            q.setLong("idTipoParam", tipoParam.longValue());
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resultado;
    }
    
    public List<ConvocatoriaParametrizacion> obtenerParametrosConvocatorias(Convocatoria convocatoria, Long tipoParam) {
        Session session = null;
        Query q = null;
        List<ConvocatoriaParametrizacion> resultado = null;
        try {
            session = getSession();
            q = session.createQuery("select cpp from ConvocatoriaParametrizacion cpp"
                    + " where cpp.convocatoria.id=:idConv AND cpp.tipoParametro.id = :idTipoParam");
            q.setLong("idConv", convocatoria.getId().longValue());
            q.setLong("idTipoParam", tipoParam.longValue());
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resultado;
    }

    public List listaCriteriosResumen(Modalidad m, String pryId) {

        Session session1;
        Statement sentencia1 = null;
        session1 = getSession();
        Hashtable lista = new Hashtable();
        String query1 = "select distinct m.CRI_ID as cri_id "
                + "from HER_MODALIDAD_CRITERIO m , HER_CALIFICACION_EVALUACION c, HER_PROYECTO_EVALUADOR e "
                + "where mod_id = '" + m.getId().toString() + "' " + "and m.CRI_ID = c.CRI_ID "
                + "and c.pre_id = e.pre_id " + "and c.cae_cuantitativa != 0 " + "and pry_id = '" + pryId + "' "
                + "order by  m.CRI_ID ";

        try {
            sentencia1 = session1.connection().createStatement();
            sentencia1.execute(query1);
            ResultSet rs1 = sentencia1.getResultSet();

            while (rs1.next()) {
                lista.put(rs1.getString(1), rs1.getString(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session1.isOpen())
                session1.close();
        }

        Session session = null;
        Query q = null;
        List resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select mctp.criterio from ModalidadCriterioTipoPregunta mctp"
                    + " where mctp.modalidad.id=:idModalidad order by mctp.orden ");
            q.setLong("idModalidad", m.getId().longValue());
            resultado = q.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            q = null;
            session = null;
        }

        List resultadofinal = new ArrayList();

        for (int i = 0; i < resultado.size(); i++) {

            CriterioEvaluacion cri = (CriterioEvaluacion) resultado.get(i);
            if (lista.contains(cri.getId().toString())) {
                resultadofinal.add(cri);
            }
        }
        return resultadofinal;
    }

    public CalificacionEvaluacion obtenrCalificacionEvaluacionXCriterioYProyectoYEvaluador(CriterioEvaluacion c,
            Proyecto pr, Persona p) throws DataAccessException {
        Session session = getSession();
        Query q = session.createQuery(" " + " select ce " + "from CalificacionEvaluacion ce "
                + "where ce.proyectoEvaluador.proyecto.id=:proyecto and "
                + " ce.proyectoEvaluador.evaluador.id.tipoDocumento=:tipo and "
                + " ce.proyectoEvaluador.evaluador.id.documento=:documento"
                + " and  ce.criterio.id=:criterio order by ce.criterio.id");
        q.setString("documento", p.getId().getDocumento());
        q.setString("tipo", p.getId().getTipoDocumento());
        q.setLong("proyecto", pr.getId().longValue());
        q.setLong("criterio", c.getId().longValue());
        CalificacionEvaluacion ce = (CalificacionEvaluacion) q.uniqueResult();
        session.close();
        return ce;
    }

    public void eliminarObjeto(Object objeto) {

        Session session = getSession();

        session.delete(objeto);

        session.close();
    }

    public List listaModFuenteFinXModalidad(Long idModalidad) {
        Session session = getSession();
        Query q = session.createQuery(" select mff from ModalidadFuenteFinanciacion mff where mff.modalidad.id=:idModalidad");
        q.setLong("idModalidad", idModalidad.longValue());
        List resultado = q.list();
        session.close();
        return resultado;
    }

    public List obtenerListaFuentesFinanciacionXConvocatoria(Long id) {
        Session session = getSession();
        Query q = session.createQuery(
                " select mff.fuenteFinanciacion from ModalidadFuenteFinanciacion mff where mff.modalidad.id=:idModalidad");
        q.setLong("idModalidad", id.longValue());
        List resultado = q.list();
        session.close();
        return resultado;
    }

    public List listaModalidadCriterioTipoPregunta(Modalidad m) {
        Session session = null;
        Query q = null;
        List resultado = null;
        try {
            session = getSession();
            q = session.createQuery("select mctp from ModalidadCriterioTipoPregunta mctp"
                    + " where mctp.modalidad.id=:idModalidad order by mctp.orden ");
            q.setLong("idModalidad", m.getId().longValue());
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resultado;
    }

    public boolean modalidadContieneCriterio(Modalidad m, Long idCriterio) {
        Session session = null;
        Query q = null;
        boolean resultado = false;

        try {
            session = getSession();
            q = session.createQuery("select mctp.criterio from ModalidadCriterioTipoPregunta mctp"
                    + " where mctp.modalidad.id=:idModalidad and mctp.criterio.id=:idCriterio");
            q.setLong("idModalidad", m.getId().longValue());
            q.setLong("idCriterio", idCriterio.longValue());
            CriterioEvaluacion c = (CriterioEvaluacion) q.uniqueResult();
            if (c != null) {
                resultado = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resultado;

    }

    public List<ConvocatoriaPadre> obtenerConvocatoriasPadreEnEstados(List listaIdEstados) {
        Session session = null;
        Query q = null;
        List<ConvocatoriaPadre> resultado = null;
        try {
            session=getSession();
            q=session.createQuery("select cp from ConvocatoriaPadre cp" +" where cp.estadoConvocatoria.id in (:listaEstados)" +"" );
            q.setParameterList("listaEstados", listaIdEstados);
            resultado=q.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            q = null;
            session = null;
        }
        return resultado;
    }

    public List<Convocatoria> obtenerConvocatoriasXPadreYListaEstado(Long idConvocatoriaPadre, List estados) {

        Session session = null;
        Query q = null;
        List resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select c from Convocatoria c"
                    + " where c.estadoConvocatoria.id in (:listaEstados) and c.padre.id=:idPadre"
                    + " order by c.id asc");
            q.setParameterList("listaEstados", estados);

            q.setLong("idPadre", idConvocatoriaPadre.longValue());
            resultado = q.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            q = null;
            session = null;
        }
        return resultado;

    }

    public boolean validarModalidadFuenteFinanciacion(Long idModalidad, String idFuenteFinanciacion) {
        Session session = getSession();
        Query query = session.createQuery(
                "SELECT mff.id FROM ModalidadFuenteFinanciacion mff WHERE mff.modalidad.id = :idModalidad AND mff.fuenteFinanciacion.id = :idFuenteFinanciacion");
        query.setLong("idModalidad", idModalidad.longValue());
        query.setString("idFuenteFinanciacion", idFuenteFinanciacion);
        List aux = query.list();
        session.close();
        return aux.size() > 0;
    }

    public List<RubroFinanciable> obtenerRubrosFinanciablesModalidad(Long idModalidad) {
        List rubros;
        Session session = getSession();
        Criteria criteria = session.createCriteria(RubroFinanciable.class);
        String alias = criteria.getAlias() + "_";
        criteria.add(Expression.sqlRestriction(alias + ".mod_id = " + idModalidad.longValue()));
        Criteria tipoRubroFinanciable = criteria.createCriteria("tipoRubro");
        tipoRubroFinanciable.add(Restrictions.eq("descripcion",TipoRubro.DESCRIPCION_CCP2022));
        tipoRubroFinanciable.addOrder(Order.asc("nombre"));
        rubros = criteria.list();
        session.close();
        return rubros;
    }

    public ModalidadCriterioTipoPregunta obtenerModalidadCriterioTipoPreguntaXProyectoEvaluadorYCriterio(
            Long idModalidad, Long idCriterio) {

        Session session = null;
        Query q = null;
        ModalidadCriterioTipoPregunta resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select mctp from ModalidadCriterioTipoPregunta mctp"
                    + " where mctp.criterio.id=:idCriterio and mctp.modalidad.id=:idModalidad " + "");
            q.setLong("idModalidad", idModalidad.longValue());
            q.setLong("idCriterio", idCriterio.longValue());
            resultado = (ModalidadCriterioTipoPregunta) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            q = null;
            session = null;
        }
        return resultado;
    }

    public ConvocatoriaPadre obtenerConvocatoriaPadre(Long id) throws DataAccessException {
        Session session = getSession();

        ConvocatoriaPadre c = (ConvocatoriaPadre) session.createCriteria(ConvocatoriaPadre.class)
                .setFetchMode("dependencia", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

        session.close();
        return c;
    }
    
    public CorteConvocatoria obtenerCorteConvocatoriaPadre(Long idConvPadre, Long numeroCorte, Sede sede, String estadoCorte) throws DataAccessException {
        Session session = getSession();

        CorteConvocatoria c = (CorteConvocatoria) session.createCriteria(CorteConvocatoria.class).add(Restrictions.eq("convocatoriaPadre.id",idConvPadre)).add(Restrictions.eq( "id", numeroCorte)).add(Restrictions.eq( "sede", sede)).add(Restrictions.eq( "estado", estadoCorte)).uniqueResult();

        session.close();
        return c;
    }

    public ConvocatoriaExterna obtenerConvocatoriaExterna(Long id) throws DataAccessException {
        Session session = getSession();

        ConvocatoriaExterna c = (ConvocatoriaExterna) session.createCriteria(ConvocatoriaExterna.class)
                .setFetchMode("entidad", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

        session.close();
        return c;
    }

    public TipoModalidad obtenerTipoModalidadConvocatoria(String id) throws DataAccessException {
        Session session = getSession();
        TipoModalidad c = (TipoModalidad) session.createCriteria(TipoModalidad.class)
                .setFetchMode("formularios", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return c;
    }
    
    public ConvocatoriaTerminosReferencia obtenerConvocatoriaTerminosReferenciaPorId(Long id) throws DataAccessException {
    	Session session = getSession();
    	ConvocatoriaTerminosReferencia c = null;
    	try{
    		c = (ConvocatoriaTerminosReferencia) session.createCriteria(ConvocatoriaTerminosReferencia.class).add(Restrictions.idEq(id)).uniqueResult();
    		Hibernate.initialize(c.getDependenciasConvoctoriaTR());
    		Hibernate.initialize(c.getModalidadesConvoctoriaTR());
    		Hibernate.initialize(c.getRequisitosConvoctoriaTR());
    		Hibernate.initialize(c.getDocumentacionConvoctoriaTR());
    		Hibernate.initialize(c.getCompromisosConvoctoriaTR());
    		Hibernate.initialize(c.getIncompatibilidadesConvoctoriaTR());
    		Hibernate.initialize(c.getRubrosConvoctoriaTR());
    		Hibernate.initialize(c.getCriteriosEvaluacionSeleccionConvoctoriaTR());
    		Hibernate.initialize(c.getProductosConvoctoriaTR());
    		Hibernate.initialize(c.getCamposAdicionalesConvoctoriaTR());
    		Hibernate.initialize(c.getObjetivosEspecificosConvoctoriaTR());
    	}catch(Exception e){
    		e.getMessage();
    	}
    	session.close();
    	return c;
    }
    
    
}
