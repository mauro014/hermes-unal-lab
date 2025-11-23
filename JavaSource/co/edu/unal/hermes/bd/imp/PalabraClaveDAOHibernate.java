package co.edu.unal.hermes.bd.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.IPalabraClaveDAO;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.utils.ReemplazaAcentos;


/**
 * Maneja el acceso a los atributos generales del sistema con Hibernate
 */
public class PalabraClaveDAOHibernate extends HibernateDaoSupport implements IPalabraClaveDAO {

    /**
     * Obtiene una lista con todos los proyectos relacionados con la palabra clave dada
     * @param palabraClave objeto con la palabra clave  
     */
    public Set obtenerProyectosPalabraClave(String s_palabraClave) throws DataAccessException {
        Session session = getSession();
        
        
        Criteria criteria = session.createCriteria(PalabraClave.class);
        criteria.setFetchMode("proyectos", FetchMode.JOIN);
        
        //Criterion criterion = Restrictions.ilike("palabra", s_palabraClave, MatchMode.ANYWHERE);
        Criterion criterion = Restrictions.sqlRestriction(ReemplazaAcentos.queryQuitaTildes("pcl_palabra")
                + " like   '%" + ReemplazaAcentos.quitarTildes(s_palabraClave).toLowerCase() + "%' and epr_id <> 'B' and epr_id <> 'BS'");

        criteria.add(criterion);
        List palabrasClaves;
        try{
            palabrasClaves = criteria.list();
        }
        catch(GenericJDBCException gje){
            palabrasClaves = new ArrayList();
        }  
      
        
        session.close();
        
        Set set_final = new HashSet();
        
        Iterator it_palabras = palabrasClaves.iterator();
        while(it_palabras.hasNext()){
            PalabraClave palabraClave = (PalabraClave)it_palabras.next();
            Set set_proyectos = palabraClave.getProyectos();
            set_final.addAll(set_proyectos);            
        }           
        return set_final;
    }

    /**
     * Obtiene una lista con todos los proyectos relacionados con la palabra clave dada, y no en estado de proyecto dado
     * @param palabraClave objeto con la palabra clave  
     */
    public Set obtenerProyectosPalabraClaveEstadoProyectoDiferente(String s_palabraClave,String idEstado) throws DataAccessException {
        Session session = getSession();
       
        
        Criteria criteria = session.createCriteria(PalabraClave.class);
        Criteria proyecto= criteria.createCriteria("proyectos");
        proyecto.add(Restrictions.ne("estadoProyecto.id",idEstado));
        criteria.setFetchMode("proyectos", FetchMode.JOIN);
        
//        criteria.add(Restrictions.ne("proyectos.estadoProyecto.id",idEstado));
        
        //Criterion criterion = Restrictions.ilike("palabra", s_palabraClave, MatchMode.ANYWHERE);
        Criterion criterion = Restrictions.sqlRestriction( ReemplazaAcentos.queryQuitaTildes("pcl_palabra")+" like   '%"+ ReemplazaAcentos.quitarTildes( s_palabraClave).toLowerCase()+"%' ");
        
        criteria.add(criterion);
        List palabrasClaves = criteria.list();
      
        
        
        Set set_final = new HashSet();
        
        Iterator it_palabras = palabrasClaves.iterator();
        while(it_palabras.hasNext()){
            PalabraClave palabraClave = (PalabraClave)it_palabras.next();
            Set set_proyectos = palabraClave.getProyectos();
            set_final.addAll(set_proyectos);            
        }           
        session.close();
        return set_final;
    }
    
}
