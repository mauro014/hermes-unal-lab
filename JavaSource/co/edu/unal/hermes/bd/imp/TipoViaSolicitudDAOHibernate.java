/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Clase    	: co.edu.unal.hermes.bd.seguimiento.TipoViaSolicitudDAOHibernate
Objetivo 	: Clase DAO para el manejo de to-do  lo  relacionado  con manipulación 
			  de datos de la tabla HER_TIPO_VIA_SOLICITUD, en la cual se almacenan 
			  los  tipos  de  vias por las que se pueden realizar solicitudes para 
			  los proyectos de investigación.
Creación	: Septiembre 21 de 2007
Modificación:
Detalle		:
********************************************************************************/

package co.edu.unal.hermes.bd.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.ITipoViaSolicitudDAO;
import co.edu.unal.hermes.modelo.seguimiento.TipoViaSolicitud;

/**
 * The Class TipoViaSolicitudDAOHibernate.
 */
public class TipoViaSolicitudDAOHibernate extends HibernateDaoSupport implements ITipoViaSolicitudDAO {

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.ITipoViaSolicitudDAO#buscar(co.edu.unal.hermes.modelo.seguimiento.TipoViaSolicitud)
     */
    public List<TipoViaSolicitud> buscar(TipoViaSolicitud tipoViaSolicitudBusqueda) {
        
        Session session = getSession();
        Criteria criteria = session.createCriteria(TipoViaSolicitud.class);
        
        boolean ejecutaConsulta = false;
        if (tipoViaSolicitudBusqueda.getId() != null && !tipoViaSolicitudBusqueda.getId().equals(0L)) {
            criteria.add(Expression.like("id", tipoViaSolicitudBusqueda.getId()));
            ejecutaConsulta = true;
        }
        if (StringUtils.isNotBlank(tipoViaSolicitudBusqueda.getNombre())) {
            criteria.add(Expression.like("nombre", tipoViaSolicitudBusqueda.getNombre()));
            ejecutaConsulta = true;
        }
        if (StringUtils.isNotBlank(tipoViaSolicitudBusqueda.getDescripcion())) {
            criteria.add(Expression.like("descripcion", tipoViaSolicitudBusqueda.getDescripcion()));
            ejecutaConsulta = true;
        }
        if (StringUtils.isNotBlank(tipoViaSolicitudBusqueda.getVigencia())) {
            criteria.add(Expression.eq("vigencia", tipoViaSolicitudBusqueda.getVigencia()));
            ejecutaConsulta = true;
        }
        if (tipoViaSolicitudBusqueda.getDesdeVigencia() != null) {
            criteria.add(Expression.eq("desdeVigencia", tipoViaSolicitudBusqueda.getDesdeVigencia()));
            ejecutaConsulta = true;
        }
        if (tipoViaSolicitudBusqueda.getHastaVigencia() != null) {
            criteria.add(Expression.eq("hastaVigencia", tipoViaSolicitudBusqueda.getHastaVigencia()));
            ejecutaConsulta = true;
        }
        
        List<TipoViaSolicitud> tiposViaSolicitud;
        if (ejecutaConsulta) {
            criteria.addOrder(Order.asc("nombre"));
            tiposViaSolicitud = criteria.list();
        } else {
            tiposViaSolicitud = new ArrayList<TipoViaSolicitud>();
        }

        session.close();
        return tiposViaSolicitud;
    }
    
    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.ITipoViaSolicitudDAO#buscarPorId(java.lang.Long)
     */
    public TipoViaSolicitud buscarPorId(Long id){
        
        Session session = getSession();
        
        Criteria criteria = session.createCriteria(TipoViaSolicitud.class);
        criteria.add(Expression.like("id", id));
        
        TipoViaSolicitud tipoViaSolicitud = (TipoViaSolicitud) criteria.uniqueResult();

        session.close();
        
        return tipoViaSolicitud;
    }


    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.ITipoViaSolicitudDAO#listarActivos()
     */
    public List<TipoViaSolicitud> listarActivos() {
        
        Session session = getSession();
        Criteria criteria = session.createCriteria(TipoViaSolicitud.class);
        
        criteria.add(Expression.eq("vigencia", "S"));
        criteria.add(Expression.sqlRestriction(
                "SYSDATE BETWEEN NVL(tvso_desde_vigencia, SYSDATE) AND NVL(tvso_hasta_vigencia, SYSDATE)"));
        criteria.addOrder(Order.asc("nombre"));

        List<TipoViaSolicitud> tiposViaSolicitud = criteria.list();
        
        session.close();
        return tiposViaSolicitud;
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.ITipoViaSolicitudDAO#guardarTipoViaSolicitud(co.edu.unal.hermes.modelo.seguimiento.TipoViaSolicitud)
     */
    public void guardarTipoViaSolicitud(TipoViaSolicitud tipoViaSolicitud) {
        getHibernateTemplate().saveOrUpdate(tipoViaSolicitud);
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.bd.ITipoViaSolicitudDAO#eliminarTipoViaSolicitud(co.edu.unal.hermes.modelo.seguimiento.TipoViaSolicitud)
     */
    public void eliminarTipoViaSolicitud(TipoViaSolicitud tipoViaSolicitud) {
        getHibernateTemplate().delete(tipoViaSolicitud);
    }

}
