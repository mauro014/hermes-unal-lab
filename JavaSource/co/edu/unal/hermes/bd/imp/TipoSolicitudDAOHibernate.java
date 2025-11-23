/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Clase    	: co.edu.unal.hermes.bd.seguimiento.TipoSolicitudDAOHibernate
Objetivo 	: Clase DAO para el manejo de to-do  lo  relacionado  con manipulación 
			  de datos de la tabla HER_TIPO_SOLICITUD, en  la  cual  se almacenan 
			  los tipos de solicitudes que  se  pueden  realizar por parte de los 
			  investigadores para un proyecto  de investigaciòn.
Creación	: Agosto 15 de 2007
Modificación:
Detalle		:
********************************************************************************/
package co.edu.unal.hermes.bd.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.ITipoSolicitudDAO;
import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;

public class TipoSolicitudDAOHibernate extends HibernateDaoSupport implements ITipoSolicitudDAO {

    public List<TipoSolicitud> buscar(TipoSolicitud tipoSolicitud) {
        
        Session session = getSession();
        
        Criteria criteria = session.createCriteria(TipoSolicitud.class);
        
        boolean ejecutaConsulta = false;
        if (tipoSolicitud.getId() != null && !tipoSolicitud.getId().equals(0L)) {
            criteria.add(Expression.like("id", tipoSolicitud.getId()));
            ejecutaConsulta = true;
        }

        if (StringUtils.isNotBlank(tipoSolicitud.getNombre())) {
            criteria.add(Expression.like("nombre", tipoSolicitud.getNombre()));
            ejecutaConsulta = true;
        }

        if (StringUtils.isNotBlank(tipoSolicitud.getDescripcion())) {
            criteria.add(Expression.like("descripcion", tipoSolicitud.getDescripcion()));
            ejecutaConsulta = true;
        }

        if (StringUtils.isNotBlank(tipoSolicitud.getMovimientoRubros())) {
            criteria.add(Expression.eq("movimientoRubros", tipoSolicitud.getMovimientoRubros()));
            ejecutaConsulta = true;
        }

        if (StringUtils.isNotBlank(tipoSolicitud.getVigencia())) {
            criteria.add(Expression.eq("vigencia", tipoSolicitud.getVigencia()));
            ejecutaConsulta = true;
        }

        if (tipoSolicitud.getDesdeVigencia() != null) {
            criteria.add(Expression.eq("desdeVigencia", tipoSolicitud.getDesdeVigencia()));
            ejecutaConsulta = true;
        }

        if (tipoSolicitud.getHastaVigencia() != null) {
            criteria.add(Expression.eq("hastaVigencia", tipoSolicitud.getHastaVigencia()));
            ejecutaConsulta = true;
        }
        
        List<TipoSolicitud> tiposSolicitud;

        if (ejecutaConsulta) {
            criteria.addOrder(Order.asc("nombre"));
            tiposSolicitud = criteria.list();
        } else {
            tiposSolicitud = new ArrayList<TipoSolicitud>();
        }

        session.close();
        
        return tiposSolicitud;
    }
    
    public TipoSolicitud buscarPorId(Long id){
        
        Session session = getSession();
        
        Criteria criteria = session.createCriteria(TipoSolicitud.class);
        criteria.add(Expression.like("id", id));
        
        TipoSolicitud tipoSolicitud = (TipoSolicitud) criteria.uniqueResult();

        session.close();
        
        return tipoSolicitud;
    }

    public List<TipoSolicitud> buscarPorVariosIds(Set<String> ids){
        
        Session session = getSession();
        
        Criteria criteria = session.createCriteria(TipoSolicitud.class);
        criteria.add(Expression.in("id", ids));
        
        List<TipoSolicitud> tiposSolicitud = criteria.list();

        session.close();
        
        return tiposSolicitud;
    }

    public List<TipoSolicitud> listarActivos() {
        Session session = getSession();
        
        List<TipoSolicitud> tiposSolicitud;
        
        Criteria criteria = session.createCriteria(TipoSolicitud.class);
        criteria.add(Expression.eq("vigencia", "S"));
        criteria.add(Expression.sqlRestriction(
                "SYSDATE BETWEEN NVL(tso_desde_vigencia, SYSDATE) AND NVL(tso_hasta_vigencia, SYSDATE) and TSO_ID <> '30' "
                + "and TSO_ID <> '31' and TSO_ID <> '32' and TSO_ID <> '33' and TSO_ID <> '34' and TSO_ID <> '35'"));
        criteria.addOrder(Order.asc("nombre"));
        
        tiposSolicitud = criteria.list();
        session.close();
        
        return tiposSolicitud;
    }

    public void guardarTipoSolicitud(TipoSolicitud tipoSolicitud) {
        getHibernateTemplate().saveOrUpdate(tipoSolicitud);
    }

    public void eliminarTipoSolicitud(TipoSolicitud tipoSolicitud) {
        getHibernateTemplate().delete(tipoSolicitud);
    }

}
