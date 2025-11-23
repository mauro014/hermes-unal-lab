package co.edu.unal.hermes.bd.imp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import co.edu.unal.hermes.bd.IFinanciacionDAO;
import co.edu.unal.hermes.modelo.TipoRubro;

/**
 * The Class FinanciacionDAOHibernate.
 */
public class FinanciacionDAOHibernate extends GeneralDAOHibernate implements IFinanciacionDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.unal.hermes.bd.IFinanciacionDAO#obtenerIngresos(java.lang.String)
	 */
	@Override
	public List<TipoRubro> obtenerTipoIngresos() {

		Session session = getSession();

		Criteria criteria = session.createCriteria(TipoRubro.class);
		criteria.add(Restrictions.eq("descripcion", "INGRESOS_CP_2022"));
		criteria.add(Restrictions.eq("nombreVRI", "1"));
		
		@SuppressWarnings("unchecked")
		List<TipoRubro> tipoRubros = criteria.list();

		session.close();
		return tipoRubros;
	}
	
	public TipoRubro obtenerTipoRubro(Long id){
		
		Session session = getSession();

		Criteria criteria = session.createCriteria(TipoRubro.class);
		criteria.add(Restrictions.eq("id", id));
		
		TipoRubro tipoRubro = (TipoRubro) criteria.uniqueResult();
		
		session.close();
		
		return tipoRubro;
		
	}

}
