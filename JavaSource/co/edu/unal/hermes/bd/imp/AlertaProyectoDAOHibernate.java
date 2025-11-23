/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Clase    	: co.edu.unal.hermes.bd.seguimiento.AlertaProyectoDAOHibernate
Objetivo 	: Clase DAO para el manejo de todo  lo  relacionado  con manipulación 
			  de datos de la tabla HER_ALERTA_PROYECTO, en  la  cual se almacenan 
			  las Alertas presentadas por los diferentes proyectos de investigación.
Creación	: Agosto 21 de 2007
Modificación:
Detalle		:
********************************************************************************/

package co.edu.unal.hermes.bd.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.IAlertaProyectoDAO;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.seguimiento.AlertaProyecto;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;

/**
 * The Class AlertaProyectoDAOHibernate.
 */
public class AlertaProyectoDAOHibernate extends HibernateDaoSupport implements IAlertaProyectoDAO {

	/**
	 * Obtener alertas asesor inbox.
	 *
	 * @param asesor
	 *            the asesor
	 * @return the list
	 */
	public List<AlertaProyecto> obtenerAlertasAsesorInbox(Persona asesor) {

		List<AlertaProyecto> listaAlertas = new ArrayList<AlertaProyecto>();

		Session session = getSession();
		SQLQuery sqlQuery1 = null;
		
		try {
		String query = "select AP.ALPR_ID as id, AP.ALPR_FECHA_GENERA as fecha, AP.PRY_ID as idProyecto, AP.ALPR_ESTADO estado,"
				+ " AP.ALPR_MENSAJE as mensaje , AP.SOL_ID as solId, S.TSO_ID as tipoSolicitud "
				+ " from HER_ALERTA_PROYECTO AP left join HER_SOLICITUD S on S.SOL_ID = AP.SOL_ID"
				+ " where AP.ALPR_ESTADO <> 'C' and AP.PER_ID = '" + asesor.getId().getDocumento()
				+ "' and AP.TDO_ID = '" + asesor.getId().getTipoDocumento() + "'" + " and S.TSO_ID <> '"
				+ TipoSolicitud.RENOCACION + "' order by AP.ALPR_ID desc";

		sqlQuery1 = session.createSQLQuery(query);

		List<Object[]> listaAlertasAuxiliar = sqlQuery1.addScalar("id", Hibernate.LONG)
				.addScalar("fecha", Hibernate.DATE).addScalar("idProyecto", Hibernate.LONG)
				.addScalar("estado", Hibernate.STRING).addScalar("mensaje", Hibernate.STRING)
				.addScalar("solId", Hibernate.LONG).addScalar("tipoSolicitud", Hibernate.LONG).list();

		Iterator<Object[]> it = listaAlertasAuxiliar.iterator();

		while (it.hasNext()) {
			Object[] object = (Object[]) it.next();
			AlertaProyecto ap = new AlertaProyecto();
			Proyecto p = new Proyecto();
			Solicitud s = new Solicitud();

			ap.setId((Long) object[0]);
			ap.setFechaGenera((Date) object[1]);
			p.setId((Long) object[2]);
			ap.setProyecto(p);
			ap.setEstado((String) object[3]);
			ap.setMensaje((String) object[4]);
			s.setId((Long) object[5]);
			TipoSolicitud ts = new TipoSolicitud();
			ts.setId((Long) object[6]);
			s.setTipoSolicitud(ts);
			ap.setSolicitud(s);
			ap.setAsesor(asesor);
			listaAlertas.add(ap);
		}
		
		} catch (Exception e) {
            e.printStackTrace();
        } finally {
        	sqlQuery1 = null;
            if (session != null)
                session.close();
            session = null;
        }
		
		return listaAlertas;
	}

	/**
	 * Obtener alerta proyecto solicitud.
	 *
	 * @param proyecto
	 *            the proyecto
	 * @param solicitud
	 *            the solicitud
	 * @return the alerta proyecto
	 */
	public List<AlertaProyecto> obtenerAlertaProyectoSolicitud(Proyecto proyecto, Solicitud solicitud) {
		Session session = getSession();
		List<AlertaProyecto> lista = new ArrayList<AlertaProyecto>();
		
		try {
		Criteria criteria = session.createCriteria(AlertaProyecto.class);
		criteria.add(Expression.eq("proyecto", proyecto));
		criteria.add(Expression.eq("solicitud", solicitud));
		criteria.addOrder(Order.desc("fechaGenera"));
		List<String> estados = new ArrayList<String>();
		estados.add("P");
		estados.add("E");
		criteria.add(Expression.in("estado", estados));
		criteria.setMaxResults(1);
		lista = criteria.list();
		
		} catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (session != null)
                session.close();
            session = null;
        }
		return lista;
	}
}
