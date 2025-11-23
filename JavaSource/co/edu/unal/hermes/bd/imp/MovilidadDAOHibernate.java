package co.edu.unal.hermes.bd.imp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.IMovilidadDAO;
import co.edu.unal.hermes.modelo.ActividadMovilidadVE;
import co.edu.unal.hermes.modelo.ArchivoResumen;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.HistoricoEstadoMovilidad;
import co.edu.unal.hermes.modelo.MovilidadDocentesArtes;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesArtes;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Programa;
import co.edu.unal.hermes.modelo.TipoArchivo;

/**
 * Maneja el acceso a toda la información de los investigadores con Hibernate.
 */
public class MovilidadDAOHibernate extends HibernateDaoSupport implements IMovilidadDAO {

    /** The Constant SEDE. */
    public static final Long SEDE = 1L;

    /** The Constant FACULTAD. */
    public static final Long FACULTAD = 2L;

    /**
     * Gets the nombre tabla.
     *
     * @param tipo
     *            the tipo
     * @return the nombre tabla
     */
    private String getNombreTabla(String tipo) {
        if (tipo.equals("B1")) {
            return "MovilidadDocentesExterior";
        }

        if (tipo.equals("A1")) {
            return "MovilidadVisitanteExterior";
        }

        if (tipo.equals("D1") || tipo.equals("MOV3_IN")) {
            return "MovilidadEstudiantesPosgrado";
        }

        if (tipo.equals("C1")) {
            return "MovilidadDocentesArtes";
        }

        if (tipo.equals("C2")) {
            return "MovilidadEstudiantesArtes";
        }

        if (tipo.equals("C3")) {
            return "MovilidadVisitantesArtes";
        }
        return "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerMovilidadesRevision(java.lang.
     * String, co.edu.unal.hermes.modelo.Persona,
     * co.edu.unal.hermes.modelo.Dependencia, java.lang.String, java.lang.Long)
     */
    public List obtenerMovilidadesRevision(String tipo, Persona persona, Dependencia dependencia, String idConvocatoria,
            Long nivelConsulta) throws DataAccessException {

        String consultaEspecifica = "";

        String filtroNoConvocatoriaFacultad = " and (mv.movilidadConvocatoriaFacultad is null or mv.movilidadConvocatoriaFacultad = 'N') ";

        String filtroConvocatoriaFacultad = " or (mv.movilidadConvocatoriaFacultad = 'S' and mv.convocatoria.padre.dependencia.id = '"
                + dependencia.getFacultad().getId() + "')) and mv.aceptacion IS NULL ";

        if (tipo.equals("C3")) {
            if (nivelConsulta.equals(MovilidadDAOHibernate.FACULTAD)) {
                consultaEspecifica = ", InvestigadorInterno ii"
                        + " where mv.idFacultad = ii.id.documento and mv.idFacultad = ii.id.documento and ii.dependencia.facultad.id ='"
                        + dependencia.getFacultad().getId() + "'";
            } else {
                consultaEspecifica = ", Dependencia d " + " where mv.dependencia = d.id and d.sede.id = '"
                        + dependencia.getSede().getId()
                        + "' and mv.aceptacionFacultad = 'SI' and mv.aprobacionSede IS NULL ";
            }

        } else if (tipo.equals("D1") || tipo.equals("MOV3_IN")) {

			consultaEspecifica = ", Estudiante e"
					+ " where mv.estudianteInv.id.documento = e.id.documento and mv.estudianteInv.id.tipoDocumento = e.id.tipoDocumento";
            if (nivelConsulta.equals(MovilidadDAOHibernate.FACULTAD)) {
                consultaEspecifica += " and ((mv.dependenciaRevisionMovilidadesEst.facultad.id ='"
                        + dependencia.getFacultad().getId() + "' " + filtroNoConvocatoriaFacultad + ") "
                        + filtroConvocatoriaFacultad;
            } else {
                consultaEspecifica += filtroNoConvocatoriaFacultad
                        + " and mv.aceptacion = 'SI' and mv.aprobacion IS NULL and mv.dependenciaRevisionMovilidadesEst.sede.id ='"
                        + dependencia.getSede().getId() + "' ";
            }


        } else if (tipo.equals("B1")) {
            consultaEspecifica = ", InvestigadorInterno ii"
                    + " where mv.personaInv.id.documento = ii.id.documento and mv.personaInv.id.tipoDocumento = ii.id.tipoDocumento and ";
            if (nivelConsulta.equals(MovilidadDAOHibernate.FACULTAD)) {
                consultaEspecifica += "((ii.dependencia.facultad.id ='" + dependencia.getFacultad().getId() + "' "
                        + filtroNoConvocatoriaFacultad + ") " + filtroConvocatoriaFacultad/* + ")"*/;
            } else {
                consultaEspecifica += "ii.dependencia.sede.id ='" + dependencia.getSede().getId()
                        + "' and mv.aceptacion = 'SI' and mv.aprobacion IS NULL " + filtroNoConvocatoriaFacultad;
            }
        } else if (tipo.equals("A1")) {
            consultaEspecifica = "where ";

            if (nivelConsulta.equals(MovilidadDAOHibernate.FACULTAD)) {
                consultaEspecifica += "((mv.dependencia.facultad.id ='" + dependencia.getFacultad().getId() + "' "
                        + filtroNoConvocatoriaFacultad + ") " + filtroConvocatoriaFacultad/* + ")"*/;
            } else {
                consultaEspecifica += "mv.dependencia.sede.id ='" + dependencia.getSede().getId() + "' "
                        + filtroNoConvocatoriaFacultad + " and mv.aceptacion = 'SI' and mv.aprobacion IS NULL";
            }
        } else {
            consultaEspecifica = ", InvestigadorInterno ii"
                    + " where mv.personaInv.id.documento = ii.id.documento and  mv.personaInv.id.tipoDocumento = ii.id.tipoDocumento "
                    + "and ";
            if (nivelConsulta.equals(MovilidadDAOHibernate.FACULTAD)) {
                consultaEspecifica += "ii.dependencia.facultad.id ='" + dependencia.getFacultad().getId()
                        + "' and mv.aceptacion IS NULL";
            } else {
                consultaEspecifica += "ii.dependencia.sede.id ='" + dependencia.getSede().getId()
                        + "' and mv.aceptacionFacultad = 'SI' and mv.aprobacionSede IS NULL ";
            }
        }

        // Se obtiene el nombre de la tabla de acuerdo al tipo.
        String nombreTabla = this.getNombreTabla(tipo);

        // Filtro adicional id convocatoria especifica
        String filtroIdConvocatoriaEspecifica = "";
        if (StringUtils.isNotBlank(idConvocatoria)) {
            filtroIdConvocatoriaEspecifica = " and mv.convocatoria.id = '" + idConvocatoria + "'";
        }

        // Filtro adicional para mostrar solo movilidades que han sido enviadas.
        String filtroEstadoEnviado = " and (mv.estado is null or mv.estado = 'P') ";

        // Se unen todos los trosos de consulta.
        String consultaCompleta =
                // Información basica de la consulta.
                "select mv from " + nombreTabla + " mv "
                // Consulta especifica de acuerdo al tipo de movilidad
                        + consultaEspecifica
                        // Filtros generedados previamente
                        + filtroIdConvocatoriaEspecifica + filtroEstadoEnviado
                        // Orden por fecha de solicitud
                        + " order by mv.fechasolicitud asc";

        // Se realiza la consulta.
        Session session = getSession();
        Query q = session.createQuery(consultaCompleta);
        List movilidades = q.list();
        session.close();

        return movilidades;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerMovilidadesXTipoConvFacultad(java.
     * lang.String, co.edu.unal.hermes.modelo.Persona,
     * co.edu.unal.hermes.modelo.Dependencia)
     */
    public List obtenerMovilidadesXTipoConvFacultad(String tipo, Persona persona, Dependencia dependencia)
            throws DataAccessException {

        Session session = getSession();

        String nombreTabla = "";

        if (tipo.equals("CF_MOV1")) {
            nombreTabla = "MovilidadEstudiantesPosgrado";
        }

        if (tipo.equals("CF_MOV2")) {
            nombreTabla = "MovilidadDocentesExterior";
        }

        if (tipo.equals("CF_MOV3")) {
            nombreTabla = "MovilidadVisitanteExterior";
            // bandera = false;
        }

        if (tipo.equals("CF_MOV4")) {
            nombreTabla = "MovilidadDocentesExterior";
        }
        Query q;
        if (tipo.equals("CF_MOV1")) {
            q = session.createQuery("select mv from " + nombreTabla + " mv"
                    + " where mv.movilidadConvocatoriaFacultad = 'S' and mv.tipoMovilidad.id = 'CF_MOV1' and mv.aceptacion IS NULL order by mv.fechasolicitud asc");

            List movilidades = q.list();// criteria.list();
            session.close();
            return movilidades;
        } else {

            if (tipo.equals("CF_MOV2")) {

                q = session.createQuery("select mv from " + nombreTabla + " mv"
                        + " where mv.movilidadConvocatoriaFacultad = 'S' and mv.tipoMovilidad.id = 'CF_MOV2' and mv.aceptacion IS NULL order by mv.fechasolicitud asc");

                List movilidades = q.list();// criteria.list();
                session.close();
                return movilidades;

            } else {
                if (tipo.equals("CF_MOV4")) {

                    q = session.createQuery("select mv from " + nombreTabla + " mv"
                            + " where mv.movilidadConvocatoriaFacultad = 'S' and mv.tipoMovilidad.id = 'CF_MOV4' and mv.aceptacion IS NULL order by mv.fechasolicitud asc");

                    List movilidades = q.list();// criteria.list();
                    session.close();
                    return movilidades;

                } else {
                    q = session.createQuery("select mv from " + nombreTabla + " mv"
                            + " where mv.movilidadConvocatoriaFacultad = 'S' and mv.tipoMovilidad.id = 'CF_MOV3' and mv.aceptacion IS NULL order by mv.fechasolicitud asc");

                    List movilidades = q.list();// criteria.list();
                    session.close();
                    return movilidades;
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerMovilidadesArtes(java.lang.String,
     * co.edu.unal.hermes.modelo.Persona, co.edu.unal.hermes.modelo.Dependencia,
     * java.lang.String)
     */
    public List obtenerMovilidadesArtes(String tipo, Persona persona, Dependencia dependencia, String idConvocatoria)
            throws DataAccessException {

        Session session = getSession();

        String nombreTabla = "";

        if (tipo.equals("C1")) {
            nombreTabla = "MovilidadDocentesArtes";
        }

        if (tipo.equals("C2")) {
            nombreTabla = "MovilidadEstudiantesArtes";
        }

        String consulta = "select mv from " + nombreTabla + " mv, InvestigadorInterno ii"
                + " where mv.personaInv.id.documento = ii.id.documento and ii.dependencia.facultad.id ='"
                + dependencia.getFacultad().getId() + "' and mv.aceptacionFacultad IS NULL and mv.estado = 'P' ";

        if (StringUtils.isNotBlank(idConvocatoria)) {
            consulta += " and mv.convocatoria.id = '" + idConvocatoria + "'";
        }

        consulta += " order by mv.fechasolicitud asc";

        Query q = session.createQuery(consulta);

        List movilidades = q.list();// criteria.list();
        session.close();
        return movilidades;

    }

    /* consulta */

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerMovilidadesVisExt(java.lang.Long)
     */
    public MovilidadVisitanteExterior obtenerMovilidadesVisExt(Long id) throws DataAccessException {

        Session session = getSession();
        MovilidadVisitanteExterior m = (MovilidadVisitanteExterior) session
                .createCriteria(MovilidadVisitanteExterior.class).setFetchMode("actividades", FetchMode.JOIN)
                .setFetchMode("archivos", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

        session.close();

        return m;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerMovilidadesEstudiantes(java.lang.
     * Long)
     */
    public MovilidadEstudiantesPosgrado obtenerMovilidadesEstudiantes(Long id) throws DataAccessException {
        Session session = getSession();
        MovilidadEstudiantesPosgrado m = (MovilidadEstudiantesPosgrado) session
                .createCriteria(MovilidadEstudiantesPosgrado.class).setFetchMode("actividades", FetchMode.JOIN)
                .setFetchMode("archivos", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

        session.close();

        return m;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerMovilidadesDocentes(java.lang.
     * Long)
     */
    public MovilidadDocentesExterior obtenerMovilidadesDocentes(Long id) throws DataAccessException {
        Session session = getSession();
        MovilidadDocentesExterior m = (MovilidadDocentesExterior) session
                .createCriteria(MovilidadDocentesExterior.class).setFetchMode("ponencias", FetchMode.JOIN)
                .setFetchMode("archivos", FetchMode.JOIN).setFetchMode("actividades", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();
        session.close();

        return m;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerMovilidadesVisExtRequisitos(java.
     * lang.Long)
     */
    public MovilidadVisitanteExterior obtenerMovilidadesVisExtRequisitos(Long id) throws DataAccessException {

        Session session = getSession();
        MovilidadVisitanteExterior m = (MovilidadVisitanteExterior) session
                .createCriteria(MovilidadVisitanteExterior.class).setFetchMode("requisitosMovilidad", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();

        session.close();

        return m;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerMovilidadesEstudiantesRequisitos(
     * java.lang.Long)
     */
    public MovilidadEstudiantesPosgrado obtenerMovilidadesEstudiantesRequisitos(Long id) throws DataAccessException {
        Session session = getSession();
        MovilidadEstudiantesPosgrado m = (MovilidadEstudiantesPosgrado) session
                .createCriteria(MovilidadEstudiantesPosgrado.class).setFetchMode("requisitosMovilidad", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();

        session.close();

        return m;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerMovilidadesDocentesRequisitos(java
     * .lang.Long)
     */
    public MovilidadDocentesExterior obtenerMovilidadesDocentesRequisitos(Long id) throws DataAccessException {
        Session session = getSession();
        MovilidadDocentesExterior m = (MovilidadDocentesExterior) session
                .createCriteria(MovilidadDocentesExterior.class).setFetchMode("requisitosMovilidad", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();

        session.close();

        return m;
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.bd.IMovilidadDAO#
     * obtenerMovilidadesEstudiantesArtesRequisitos(java.lang.Long)
     */
    public MovilidadEstudiantesArtes obtenerMovilidadesEstudiantesArtesRequisitos(Long id) throws DataAccessException {
        Session session = getSession();
        MovilidadEstudiantesArtes m = (MovilidadEstudiantesArtes) session
                .createCriteria(MovilidadEstudiantesArtes.class).setFetchMode("requisitosMovilidad", FetchMode.JOIN)
                .add(Restrictions.idEq(id)).uniqueResult();

        session.close();

        return m;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerMovilidadesDocentesArtesRequisitos
     * (java.lang.Long)
     */
    public MovilidadDocentesArtes obtenerMovilidadesDocentesArtesRequisitos(Long id) throws DataAccessException {
        Session session = getSession();
        MovilidadDocentesArtes m = (MovilidadDocentesArtes) session.createCriteria(MovilidadDocentesArtes.class)
                .setFetchMode("requisitosMovilidad", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

        session.close();

        return m;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerMovilidadesXTipoCons(java.lang.
     * String, co.edu.unal.hermes.modelo.Persona,
     * co.edu.unal.hermes.modelo.Dependencia)
     */
    public List obtenerMovilidadesXTipoCons(String tipo, Persona persona, Dependencia dependencia)
            throws DataAccessException {

        Session session = getSession();

        Query q;
        String nombreTabla = "";

        if (tipo.equals("B1")) {
            nombreTabla = "MovilidadDocentesExterior";
        }

        if (tipo.equals("A1")) {
            nombreTabla = "MovilidadVisitanteExterior";
        }

        if (tipo.equals("D1") || tipo.equals("MOV3_IN")) {
            nombreTabla = "MovilidadEstudiantesPosgrado";
        }

        if (tipo.equals("C1")) {
            nombreTabla = "MovilidadDocentesArtes";
        }

        if (tipo.equals("C2")) {
            nombreTabla = "MovilidadEstudiantesArtes";
        }

        if (tipo.equals("C3")) {
            nombreTabla = "MovilidadVisitantesArtes";
        }

        /*
         * q = session .createQuery("select mv from " + nombreTabla +
         * " mv, InvestigadorInterno ii" +
         * " where mv.personaInv.id.documento = ii.id.documento and ii.dependencia.padre.id ='"
         * + dependencia.getPadre().getId()+
         * "' and mv.aceptacion IS NULL order by mv.fechasolicitud asc");
         */
        if (!tipo.equals("C3")) {

        }
        if (tipo.equals("C3")) {

            q = session.createQuery("select mv from " + nombreTabla + " mv, InvestigadorInterno ii"
                    + " where mv.idFacultad = ii.id.documento and ii.dependencia.facultad.id ='"
                    + dependencia.getFacultad().getId()
                    + "' and mv.aceptacionFacultad IS NOT NULL and mv.convocatoria IS NOT NULL order by mv.fechasolicitud asc");

            List movilidades = q.list();// criteria.list();
            session.close();
            return movilidades;

        } else if (tipo.equals("D1")) {

            q = session.createQuery("select mv from " + nombreTabla + " mv, Estudiante e"
                    + " where mv.estudianteInv.id.documento = e.id.documento and e.dependencia.facultad.id ='"
                    + dependencia.getFacultad().getId()
                    + "' and mv.aceptacion IS NOT NULL and mv.convocatoria.id IS NOT NULL and mv.tipoMovilidad = 'D1' order by mv.fechasolicitud asc");

            List movilidades = q.list();// criteria.list();
            session.close();
            return movilidades;

        } else if (tipo.equals("MOV3_IN")) {

            q = session.createQuery("select mv from " + nombreTabla + " mv, Estudiante e"
                    + " where mv.estudianteInv.id.documento = e.id.documento and e.dependencia.facultad.id ='"
                    + dependencia.getFacultad().getId()
                    + "' and mv.aceptacion IS NOT NULL and mv.convocatoria.id IS NOT NULL and mv.tipoMovilidad = 'MOV3_IN' order by mv.fechasolicitud asc");

            List movilidades = q.list();// criteria.list();
            session.close();
            return movilidades;

        } else if (tipo.equals("A2")) {
            q = session.createQuery("select mv from " + nombreTabla + " mv, InvestigadorInterno ii"
                    + " where mv.personaInv.id.documento = ii.id.documento and ii.dependencia.facultad.id ='"
                    + dependencia.getFacultad().getId()
                    + "' and mv.aceptacion IS NOT NULL order by mv.fechasolicitud asc");

            List movilidades = q.list();// criteria.list();
            session.close();
            return movilidades;

        } else if (tipo.equals("A1")) {

            q = session.createQuery("select mv from " + nombreTabla + " mv " + " where mv.dependencia.facultad.id ='"
                    + dependencia.getFacultad().getId()
                    + "' and mv.aceptacion IS NOT NULL and mv.convocatoria.id IS NOT NULL order by mv.fechasolicitud asc");

            List movilidades = q.list();// criteria.list();
            session.close();
            return movilidades;

        } else {
            q = session.createQuery("select mv from " + nombreTabla + " mv, InvestigadorInterno ii"
                    + " where mv.personaInv.id.documento = ii.id.documento and ii.dependencia.facultad.id ='"
                    + dependencia.getFacultad().getId()
                    + "' and mv.aceptacion IS NOT NULL and mv.convocatoria.id IS NOT NULL order by mv.fechasolicitud asc");

            List movilidades = q.list();// criteria.list();
            session.close();
            return movilidades;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerMovilidadesArtesCons(java.lang.
     * String, co.edu.unal.hermes.modelo.Persona,
     * co.edu.unal.hermes.modelo.Dependencia)
     */
    public List obtenerMovilidadesArtesCons(String tipo, Persona persona, Dependencia dependencia)
            throws DataAccessException {

        Session session = getSession();

        String nombreTabla = "";

        if (tipo.equals("C1")) {
            nombreTabla = "MovilidadDocentesArtes";
        }

        if (tipo.equals("C2")) {
            nombreTabla = "MovilidadEstudiantesArtes";
        }

        Query q = session.createQuery("select mv from " + nombreTabla + " mv, InvestigadorInterno ii"
                + " where mv.personaInv.id.documento = ii.id.documento and ii.dependencia.facultad.id ='"
                + dependencia.getFacultad().getId()
                + "' and mv.aceptacionFacultad IS NOT NULL  and mv.convocatoria.id IS NOT NULL order by mv.fechasolicitud asc");

        List movilidades = q.list();// criteria.list();
        session.close();
        return movilidades;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerMovilidadesXTipoConsSede(java.lang
     * .String, co.edu.unal.hermes.modelo.Persona,
     * co.edu.unal.hermes.modelo.Dependencia)
     */
    public List obtenerMovilidadesXTipoConsSede(String tipo, Persona persona, Dependencia dependencia)
            throws DataAccessException {

        Session session = getSession();

        Query q;
        String nombreTabla = "";

        if (tipo.equals("B1")) {
            nombreTabla = "MovilidadDocentesExterior";
        }

        if (tipo.equals("A1")) {
            nombreTabla = "MovilidadVisitanteExterior";
        }

        if (tipo.equals("D1") || tipo.equals("MOV3_IN")) {
            nombreTabla = "MovilidadEstudiantesPosgrado";
        }

        if (tipo.equals("C1")) {
            nombreTabla = "MovilidadDocentesArtes";
        }

        if (tipo.equals("C2")) {
            nombreTabla = "MovilidadEstudiantesArtes";
        }

        if (tipo.equals("C3")) {
            nombreTabla = "MovilidadVisitantesArtes";
        }

        if (!tipo.equals("C3")) {

        }
        if (tipo.equals("C3")) {

            q = session.createQuery("select mv from " + nombreTabla + " mv, InvestigadorInterno ii"
                    + " where mv.idFacultad = ii.id.documento and ii.dependencia.sede.id ='"
                    + dependencia.getSede().getId()
                    + "'and mv.aprobacionSede IS NOT NULL and mv.convocatoria IS NOT NULL order by mv.fechasolicitud asc");

            List movilidades = q.list();// criteria.list();
            session.close();
            return movilidades;

        } else if (tipo.equals("D1")) {

            q = session.createQuery("select mv from " + nombreTabla + " mv, Estudiante e"
                    + " where mv.estudianteInv.id.documento = e.id.documento and e.dependencia.sede.id ='"
                    + dependencia.getSede().getId()
                    + "' and mv.aprobacion IS NOT NULL and mv.convocatoria.id IS NOT NULL and mv.tipoMovilidad = 'D1' order by mv.fechasolicitud asc");

            List movilidades = q.list();// criteria.list();
            session.close();
            return movilidades;

        } else if (tipo.equals("MOV3_IN")) {

            q = session.createQuery("select mv from " + nombreTabla + " mv, Estudiante e"
                    + " where mv.estudianteInv.id.documento = e.id.documento and e.dependencia.sede.id ='"
                    + dependencia.getSede().getId()
                    + "' and mv.aprobacion IS NOT NULL and mv.convocatoria.id IS NOT NULL and mv.tipoMovilidad = 'MOV3_IN' order by mv.fechasolicitud asc");

            List movilidades = q.list();// criteria.list();
            session.close();
            return movilidades;

        } else if (tipo.equals("A2")) {
            q = session.createQuery("select mv from " + nombreTabla + " mv, InvestigadorInterno ii"
                    + " where mv.personaInv.id.documento = ii.id.documento and ii.dependencia.sede.id ='"
                    + dependencia.getSede().getId() + "' and mv.aprobacion IS NOT NULL order by mv.fechasolicitud asc");

            List movilidades = q.list();// criteria.list();
            session.close();
            return movilidades;

        } else if (tipo.equals("A1")) {
            q = session.createQuery("select mv from " + nombreTabla + " mv " + " where mv.dependencia.sede.id ='"
                    + dependencia.getSede().getId()
                    + "' and mv.aprobacion IS NOT NULL and mv.convocatoria.id IS NOT NULL order by mv.fechasolicitud asc");

            List movilidades = q.list();// criteria.list();
            session.close();
            return movilidades;
        } else {
            q = session.createQuery("select mv from " + nombreTabla + " mv, InvestigadorInterno ii"
                    + " where mv.personaInv.id.documento = ii.id.documento and ii.dependencia.sede.id ='"
                    + dependencia.getSede().getId()
                    + "' and mv.aprobacion IS NOT NULL and mv.convocatoria.id IS NOT NULL order by mv.fechasolicitud asc");

            List movilidades = q.list();// criteria.list();
            session.close();
            return movilidades;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerMovilidadesArtesConsSede(java.lang
     * .String, co.edu.unal.hermes.modelo.Persona,
     * co.edu.unal.hermes.modelo.Dependencia)
     */
    public List obtenerMovilidadesArtesConsSede(String tipo, Persona persona, Dependencia dependencia)
            throws DataAccessException {

        Session session = getSession();

        String nombreTabla = "";

        if (tipo.equals("C1")) {
            nombreTabla = "MovilidadDocentesArtes";
        }

        if (tipo.equals("C2")) {
            nombreTabla = "MovilidadEstudiantesArtes";
        }

        Query q = session.createQuery("select mv from " + nombreTabla + " mv, InvestigadorInterno ii"
                + " where mv.personaInv.id.documento = ii.id.documento and ii.dependencia.sede.id ='"
                + dependencia.getSede().getId()
                + "' and mv.aprobacionSede IS NOT NULL  and mv.convocatoria.id IS NOT NULL order by mv.fechasolicitud asc");

        List movilidades = q.list();// criteria.list();
        session.close();
        return movilidades;

    }

    /* consulta */

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.bd.IMovilidadDAO#obtenerActividades(java.lang.Long)
     */
    public List obtenerActividades(Long id) throws DataAccessException {

        Session session = getSession();

        Criteria criteria = session.createCriteria(ActividadMovilidadVE.class);
        Criterion criterion = Restrictions.eq("movilidad.id", id);
        criteria.add(criterion);
        List movilidades = criteria.list();

        session.close();
        return movilidades;
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.bd.IMovilidadDAO#obtenerPais(java.lang.String)
     */
    public Pais obtenerPais(String id) throws DataAccessException {

        Session session = getSession();

        Criteria criteria = session.createCriteria(Pais.class);
        Pais p = (Pais) criteria.add(Restrictions.ilike("id", id, MatchMode.EXACT)).uniqueResult();

        session.close();
        return p;
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.bd.IMovilidadDAO#obtenerPrograma(java.lang.String)
     */
    public Programa obtenerPrograma(String id) throws DataAccessException {

        Session session = getSession();

        Criteria criteria = session.createCriteria(Programa.class);
        Programa p = (Programa) criteria.add(Restrictions.ilike("id", id, MatchMode.EXACT)).uniqueResult();

        session.close();
        return p;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.bd.IMovilidadDAO#obtenerNombresArchivosMovilidadEA(co.edu.
     * unal.hermes.modelo.MovilidadEstudiantesArtes)
     */
    public List obtenerNombresArchivosMovilidadEA(MovilidadEstudiantesArtes movilidadEA) {
        List resumenArchivo = new ArrayList();

        Session session = getSession();
        Transaction tr = session.beginTransaction();

        SQLQuery sqlQuery1 = session
                .createSQLQuery("SELECT AMV_NOMBRE AS nombre FROM HER_ARCHIVO_MOVILIDAD WHERE MOV_ID = '"
                        + movilidadEA.getId() + "' ORDER BY AMV_ID");
        SQLQuery sqlQuery2 = session.createSQLQuery("SELECT AMV_ID AS id FROM HER_ARCHIVO_MOVILIDAD WHERE MOV_ID = '"
                + movilidadEA.getId() + "' ORDER BY AMV_ID");
        SQLQuery sqlQuery3 = session
                .createSQLQuery("SELECT TAM_ID AS tipoArchivo FROM HER_ARCHIVO_MOVILIDAD WHERE MOV_ID = '"
                        + movilidadEA.getId() + "' ORDER BY AMV_ID");

        List nombres = sqlQuery1.addScalar("nombre", Hibernate.STRING).list();
        List ids = sqlQuery2.addScalar("id", Hibernate.LONG).list();
        List tipoArchivo = sqlQuery3.addScalar("tipoArchivo", Hibernate.LONG).list();

        tr.commit();
        session.close();

        Iterator it = nombres.iterator();
        Iterator it2 = ids.iterator();
        Iterator it3 = tipoArchivo.iterator();
        while (it.hasNext() && it2.hasNext()) {
            Object name = it.next();
            Object id = it2.next();
            Object ta = it3.next();
            ArchivoResumen arcR = new ArchivoResumen();
            arcR.setNombre((String) name);
            arcR.setId((Long) id);
            Long tipoArchId = (Long) ta;

            Session session2 = getSession();
            Transaction tr2 = session2.beginTransaction();
            SQLQuery sqlQuery4 = session2.createSQLQuery(
                    "SELECT TAM_NOMBRE as nombre FROM HER_TIPO_ARCHIVO_MOVILIDAD WHERE TAM_ID = '" + tipoArchId + "'");
            List nTipoArchivo = sqlQuery4.addScalar("nombre", Hibernate.STRING).list();
            tr2.commit();
            session2.close();
            TipoArchivo tar = new TipoArchivo();
            tar.setNombre((String) nTipoArchivo.get(0));
            arcR.setTipoArchivo(tar);
            resumenArchivo.add(arcR);
        }
        return resumenArchivo;
    }

    /**
     * Gets the historico estado movilidad.
     *
     * @param id
     *            the id
     * @return the historico estado movilidad
     */
    @SuppressWarnings("unchecked")
    public List<HistoricoEstadoMovilidad> getHistoricoEstadoMovilidad(Long id) {
        Session session = getSession();

        String consulta = "from HistoricoEstadoMovilidad hem where hem.idMovilidad = '" + id + "' order by hem.fecha ";

        Query q = session.createQuery(consulta);

        List<HistoricoEstadoMovilidad> movilidades = q.list();

        session.close();
        return movilidades;
    }

}
