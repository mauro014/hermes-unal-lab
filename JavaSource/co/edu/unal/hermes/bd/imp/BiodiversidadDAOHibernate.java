package co.edu.unal.hermes.bd.imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import co.edu.unal.hermes.bd.IBiodiversidadDAO;
import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaTramiteBiodiversidad;
import co.edu.unal.hermes.modelo.TipoTramiteBiodiversidad;

/**
 * Maneja el acceso a los atributos generales del sistema con Hibernate
 */
public class BiodiversidadDAOHibernate extends GeneralDAOHibernate implements IBiodiversidadDAO {

    public List<TipoTramiteBiodiversidad> obtenerListaTramitesBiodiversidadPersona(Persona persona) {
        Session session = null;
        Query q = null;
        List<TipoTramiteBiodiversidad> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select ttb from TipoTramiteBiodiversidad ttb, PersonaTramiteBiodiversidad ptb "
                    + "where ttb.id = ptb.tipoTramite.id and ptb.personaEncargada.id.tipoDocumento = '" + persona.getId().getTipoDocumento()
                    + "' and ptb.personaEncargada.id.documento = '" + persona.getId().getDocumento() + "'");
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

    public TipoTramiteBiodiversidad obtenerRegistroTramiteBiodiversidad(Long id) {
        Session session = null;
        Query q = null;
        List<TipoTramiteBiodiversidad> resultado = null;

        try {
            session = getSession();
            q = session.createQuery("select ttb from TipoTramiteBiodiversidad ttb where ttb.id = '" + id + "'");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        if (resultado!=null && !resultado.isEmpty()) {
            return (TipoTramiteBiodiversidad) resultado.get(0);
        } else {
            return null;
        }
    }

    public List<PersonaTramiteBiodiversidad> obtenerPersonasAsignadasTramiteDependencia(Long tramite, String dependencia) {
        Session session = null;
        Query q = null;
        List<PersonaTramiteBiodiversidad> resultado = new ArrayList<PersonaTramiteBiodiversidad>();

        try {
            session = getSession();
            q = session.createQuery(
                    "select ptb from PersonaTramiteBiodiversidad ptb where ptb.dependencia.id='"+dependencia+"' and ptb.tipoTramite.id='"+tramite+"'");
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
    
    public Persona obtenerPersonaEncargadaBiodiversidadVicerrectoria() {
        Session session = null;
        Query q = null;
        List<Parametro> resultado = null;
        Persona persona = null;

        try {
            session = getSession();
            q = session.createQuery(
                    "select p from Parametro p where p.nombre = 'SEG_BIODIVERSIDAD'");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        
        if(resultado!=null && !resultado.isEmpty()){
            persona = new Persona();
            Parametro parametro = resultado.get(0);
            IdPersona id = new IdPersona();
            id.setTipoDocumento(parametro.getProfesion().trim());
            id.setDocumento(parametro.getValor().trim());
            persona.setId(id);
            persona.setEmail(parametro.getProfesion().trim());
        }
        
        return persona;
    }
    
    public Persona obtenerPersonaControlBiodiversidad() {
        Session session = null;
        Query q = null;
        List<Parametro> resultado = null;
        Persona persona = null;

        try {
            session = getSession();
            q = session.createQuery(
                    "select p from Parametro p where p.nombre = 'ENCARGADOS_BIO'");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        
        if(resultado!=null && !resultado.isEmpty()){
            persona = new Persona();
            Parametro parametro = resultado.get(0);
            IdPersona id = new IdPersona();
            id.setTipoDocumento(parametro.getProfesion().trim());
            id.setDocumento(parametro.getValor().trim());
            persona.setId(id);
            persona.setEmail(parametro.getProfesion().trim());
        }
        
        return persona;
    }
    
    public Persona obtenerPersonaEncargadaSolicitudesEspecificasVicerrectoria(){
        Session session = null;
        Query q = null;
        List<PersonaTramiteBiodiversidad> resultado = null;
        Persona persona = null;

        try {
            session = getSession();
            q = session.createQuery(
                    "select p from PersonaTramiteBiodiversidad p where p.tipoTramite.id = '"+TipoTramiteBiodiversidad.PNN_CERT_MOV_EXP_ESPECIMENES+"'");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
            persona = new Persona();
        if(resultado!=null && !resultado.isEmpty()){
            persona =resultado.get(0).getPersonaEncargada();
        }
        
        return persona;
    }
    
    public List<Parametro> obtenerPersonasEncargadasColeccionesBiologicas() {
        Session session = null;
        Query q = null;
        List<Parametro> resultado = null;

        try {
            session = getSession();
            q = session.createQuery(
                    "select p from Parametro p where p.nombre = 'COORD_BIODIVERSIDAD' and (p.fechaFinal > current_date OR p.fechaFinal IS NULL)");
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
    
    public List<Coleccion> obtenerColeccionesCandidatasAlertas(){
        Session session = null;
        Query q = null;
        List<Coleccion> resultado = null;

        try {
            session = getSession();
            q = session.createQuery(
                    "select c from Coleccion c where c.estado not in ('"+Coleccion.BORRADA+"','"+Coleccion.EN_PROCESO_ACT+"','"+Coleccion.DEVUELTA_CORRECCIONES+"')");
            resultado = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            q = null;
            if (session != null)
                session.close();
            session = null;
        }
        if(resultado == null){
            resultado = new ArrayList<Coleccion>();
        }
        return resultado;
    }
}
