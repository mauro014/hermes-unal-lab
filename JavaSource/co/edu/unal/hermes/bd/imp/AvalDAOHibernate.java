package co.edu.unal.hermes.bd.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import co.edu.unal.hermes.bd.IAvalDAO;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.Reporte;

/**
 * Maneja el acceso a los atributos generales del sistema con Hibernate
 */
public class AvalDAOHibernate extends GeneralDAOHibernate implements IAvalDAO {

    public List<Reporte> obtenerListaCartas(String tipo) {
        Session session = null;
        Query q = null;
        List<Reporte> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select r from AvalReporte ar, Reporte r where ar.reporte.id = r.id and ar.tipoAval.identificador.id = '" + Aval.DOMINIO_TIPO_AVAL
                    + "' and ar.tipoAval.identificador.tipo = '"+tipo+"' order by r.nombreExterno");
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
