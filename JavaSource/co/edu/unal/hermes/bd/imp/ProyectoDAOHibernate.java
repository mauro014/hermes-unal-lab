package co.edu.unal.hermes.bd.imp;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.IProyectoDAO;
import co.edu.unal.hermes.modelo.Actividad;
import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.modelo.ArchivoRequerimiento;
import co.edu.unal.hermes.modelo.ArchivoResumen;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.CalificacionEvaluacion;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoCarta;
import co.edu.unal.hermes.modelo.EstadoInforme;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoAsignacionProyecto;
import co.edu.unal.hermes.modelo.HistoricoEstadoAval;
import co.edu.unal.hermes.modelo.HistoricoEstadoInforme;
import co.edu.unal.hermes.modelo.HistoricoEstadoLegalizacion;
import co.edu.unal.hermes.modelo.HistoricoEstadoProyecto;
import co.edu.unal.hermes.modelo.HistoricoHabilitacionEdicionProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.JornadaDocente;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.ObservacionSeguimiento;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoCarta;
import co.edu.unal.hermes.modelo.ProyectoCompromiso;
import co.edu.unal.hermes.modelo.ProyectoCoordinadorEditorial;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.ProyectoEvaluadorVistaCert;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.modelo.TipoInvestigacion;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleProyectos;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.modelo.seguimiento.DetalleSolicitud;
import co.edu.unal.hermes.modelo.seguimiento.ObservacionProyecto;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudInvestigador;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudProrrogaInvestigador;
import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.vista.evaluadores.ProyectoCoordinador;
import co.edu.unal.hermes.vista.proyectos.ProyectoVista;
import co.edu.unal.hermes.vista.proyectos.ProyectosVistaOficiosConvocatoria;

public class ProyectoDAOHibernate extends HibernateDaoSupport implements IProyectoDAO {

	public static final int INSERT = 1;
	public static final int UPDATE = 2;
	public static final int DELETE = 3;

	public static final short METODO_VIEJO = 0;
	public static final short DATOS_BASICOS = 1;
	public static final short INVESTIGADORES = 2;
	public static final short LINEAS = 3;
	public static final short OBJETIVOS_RESULTADOS = 4;
	public static final short ACTIVIDADES = 6;
	public static final short BIBLIOGRAFIAS = 7;
	public static final short POSIBLES_EVALUADORES = 8;
	public static final short FUENTES_FINANCIACION = 9;
	public static final short VIGENCIAS = 10;
	public static final short RUBROS = 11;
	public static final short CLASIFICACION_CONOCIMIENTO = 12;
	public static final short DEPENDENCIAS = 13;
	public static final short EQUIPOS = 14;
	public static final short FICHA_QUIPU = 15;
	public static final short PRODUCTOS = 16;
	public static final short LINEAS_DEPENDENCIAS = 18;
	public static final short AREAS_TEMATICAS = 17;
	public static final short ASIGNACION_EVALUADORES = 19;

	public static final short INFORMACION_GENERAL = 20;
	public static final short INFORMACION_FINANCIERA = 21;
	public static final short UNICO = 22;
	public static final short EVALUADORES = 23;
	public static final short CREACIONARTISTICA = 24;
	public static final short ASESORES = 25;

	public static final short PRORROGAS = 26;
	public static final short OBSERVACIONES = 27;
	public static final short COMPROMISOS = 28;
	public static final short EMPRESAS = 29;
	public static final short EMPRESASEINVESTIGADORES = 30;
	public static final short INVESTIGADORESYACTIVIDADES = 31;
	public static final short REQUISITOS = 32;

	public static final short PLANDEACCION = 33;
	public static final short GRUPOSEXTERNOS = 34;

	public static final short CARTAS = 35;
	public static final short AGENDAS = 36;
	public static final short LABORATORIO_CLASIF_CONOC = 37;
	public static final short TODO_POR_ID = 38;
	public static final short LINEAS_DEPENDENCIAS_UNICO = 39;
	public static final short LIMPIO = 40;
	public static final short LIMPIO_SEGUIMIENTO = 42;
	public static final short LIMPIO_SIN_CIUDAD = 41;
	public static final short DATOS_PARA_AVAL = 43;

	// Documentos de pruebas
	public static final String DOCUMENTOS_PERSONAS_PRUEBAS = "('19380666','41654902','41654904','41654903','41654906','41654907')";

	public List obtenerProyectos(String where, boolean isParametros, Object[] parametros) throws DataAccessException {
		if (isParametros) {
			return getHibernateTemplate().find("select pry from Proyecto pry " + where, parametros);
		}

		return getHibernateTemplate().find("select pry, inter from Proyecto pry " + where);
	}

	public List obtenerProyectosCoordinadorxModalidadxSede(Modalidad mod, String dep, String idEstPry)
			throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList();		
		
		Session session = getSession();
		
		try {

		String query = "select p.pry_id as id, p.pry_nombre as nombre, "
				+ "pp.per_id as perId, PP.TDO_ID as tdoId, pp.PER_REVISION as "
				+ "perRevision, PP.TDO_ID2 as tdoId2, pp.PER_EVALUACION as "
				+ "perEvaluacion, PP.TDO_ID3 as tdoId3, f.DPN_ID as idFacultad, " + "f.DPN_NOMBRE as facultad ,"
				+ "per_seg.per_nombre1 || ' ' || per_seg.per_nombre2 || ' ' ||"
				+ "per_seg.per_apellido1 || ' ' || per_seg.per_apellido2 nombreSeguimiento, "
				+ "per_req.per_nombre1 || ' ' || per_req.per_nombre2 || ' ' ||"
				+ "per_req.per_apellido1 || ' ' || per_req.per_apellido2 nombreRequisitos, "
				+ "per_eva.per_nombre1 || ' ' || per_eva.per_nombre2 || ' ' ||"
				+ "per_eva.per_apellido1 || ' ' || per_eva.per_apellido2 nombreEvaluacion " + "from  "
				+ "her_investigador_proyecto ip, her_investigador_interno ii, "
				+ "her_dependencia d left join her_dependencia f on  d.DPN_FACULTAD  = f.DPN_id, her_proyecto p left "
				+ " join her_seg_proyecto_persona pp on pp.PRY_ID = p.PRY_ID " + " left join her_persona per_seg "
				+ " on pp.per_id = per_seg.per_id and pp.tdo_id = per_seg.tdo_id" + " left join her_persona per_req "
				+ " on pp.per_Revision = per_req.per_id and pp.tdo_id2 = per_req.tdo_id"
				+ " left join her_persona per_eva "
				+ " on pp.per_Evaluacion = per_eva.per_id and pp.tdo_id3 = per_eva.tdo_id" + " where p.MOD_ID = "
				+ mod.getId().toString() + " and  p.EPR_ID in ('" + idEstPry + "') " + "and  p.EPR_ID not in ('B')"
				+ "and p.PRY_ID = ip.PRY_ID and " + "ip.INV_ID = ii.INV_ID and "
				+ "ip.TDO_ID = ii.TDO_ID and ip.INP_TIPO = 'P' " + "and ii.DPN_ID = d.DPN_ID " + "and d.SED_ID  = '"
				+ dep + "'" + " " + "order by p.pry_id";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING)
				.addScalar("perRevision", Hibernate.STRING).addScalar("tdoId2", Hibernate.STRING)
				.addScalar("perEvaluacion", Hibernate.STRING).addScalar("tdoId3", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.STRING).addScalar("facultad", Hibernate.STRING)
				.addScalar("nombreSeguimiento", Hibernate.STRING).addScalar("nombreRequisitos", Hibernate.STRING)
				.addScalar("nombreEvaluacion", Hibernate.STRING).list();
		
		}catch (Exception e) {
			
			e.printStackTrace();
			
		}finally {
			
			if(session!=null) {
				session.close();
			}
			
		}


		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinador pc = new ProyectoCoordinador();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			pc.setPerRevision((String) name[4]);
			pc.setTdoId2((String) name[5]);
			pc.setPerEvaluacion((String) name[6]);
			pc.setTdoId3((String) name[7]);
			pc.setIdFacultad((String) name[8]);
			pc.setNombreFacultad((String) name[9]);
			pc.setNombreSeguimiento((String) name[10]);
			pc.setNombreRevision((String) name[11]);
			pc.setNombreEvaluacion((String) name[12]);
			result.add(pc);
		}

		return result;
	}
	
	public List obtenerProyectosEditorialCoordinadorxModalidadxSede(Modalidad mod, String dep, String idEstPry)
			throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();

		try {
		String query = "select p.pry_id as id, p.pry_nombre as nombre, "
				+ "pp.per_id_seg as perId, PP.TDO_ID_seg as tdoId, pp.PER_ID_REQ as "
				+ "perRevision, PP.TDO_ID_REQ as tdoId2, pp.PER_ID_EV as "
				+ "perEvaluacion, PP.TDO_ID_EV as tdoId3, f.DPN_ID as idFacultad, " + "f.DPN_NOMBRE as facultad ,"
				+ "per_seg.per_nombre1 || ' ' || per_seg.per_nombre2 || ' ' ||"
				+ "per_seg.per_apellido1 || ' ' || per_seg.per_apellido2 nombreSeguimiento, "
				+ "per_req.per_nombre1 || ' ' || per_req.per_nombre2 || ' ' ||"
				+ "per_req.per_apellido1 || ' ' || per_req.per_apellido2 nombreRequisitos, "
				+ "per_eva.per_nombre1 || ' ' || per_eva.per_nombre2 || ' ' ||"
				+ "per_eva.per_apellido1 || ' ' || per_eva.per_apellido2 nombreEvaluacion " + "from  "
				+ "her_investigador_proyecto ip, her_investigador_interno ii, "
				+ "her_dependencia d left join her_dependencia f on  d.DPN_FACULTAD  = f.DPN_id, her_proyecto p left "
				+ " join her_proyecto_coordinador_ed pp on pp.PRY_ID = p.PRY_ID " + " left join her_persona per_seg "
				+ " on pp.per_id_seg = per_seg.per_id and pp.tdo_id_SEG = per_seg.tdo_id" + " left join her_persona per_req "
				+ " on pp.per_id_REQ = per_req.per_id and pp.tdo_id_REQ = per_req.tdo_id"
				+ " left join her_persona per_eva "
				+ " on pp.per_ID_EV = per_eva.per_id and pp.tdo_id_EV = per_eva.tdo_id" + " where p.MOD_ID = "
				+ mod.getId().toString() + " and  p.EPR_ID in ('" + idEstPry + "') " + "and  p.EPR_ID not in ('B')"
				+ "and p.PRY_ID = ip.PRY_ID and " + "ip.INV_ID = ii.INV_ID and "
				+ "ip.TDO_ID = ii.TDO_ID and ip.INP_TIPO = 'P' " + "and ii.DPN_ID = d.DPN_ID " + "and d.SED_ID  = '"
				+ dep + "'" + " " + "order by p.pry_id";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING)
				.addScalar("perRevision", Hibernate.STRING).addScalar("tdoId2", Hibernate.STRING)
				.addScalar("perEvaluacion", Hibernate.STRING).addScalar("tdoId3", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.STRING).addScalar("facultad", Hibernate.STRING)
				.addScalar("nombreSeguimiento", Hibernate.STRING).addScalar("nombreRequisitos", Hibernate.STRING)
				.addScalar("nombreEvaluacion", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinadorEditorial pc = new ProyectoCoordinadorEditorial();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			pc.setPerRevision((String) name[4]);
			pc.setTdoId2((String) name[5]);
			pc.setPerEvaluacion((String) name[6]);
			pc.setTdoId3((String) name[7]);
			pc.setIdFacultad((String) name[8]);
			pc.setNombreFacultad((String) name[9]);
			pc.setNombreSeguimiento((String) name[10]);
			pc.setNombreRevision((String) name[11]);
			pc.setNombreEvaluacion((String) name[12]);
			result.add(pc);
		}

		return result;
	}

	/**
	 * Obtener cartas x proyecto.
	 *
	 * @param idProyecto
	 *            the id proyecto
	 * @return the list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@SuppressWarnings("unchecked")
	public List<ProyectoCarta> obtenerCartasXProyecto(Long idProyecto) throws DataAccessException {

		List<ProyectoCarta> result = null;
		Session session = getSession();
		try {
		if (idProyecto != null) {
			

			Criteria criteria = session.createCriteria(ProyectoCarta.class);
			criteria.add(Restrictions.eq("proyecto.id", idProyecto));
			criteria.add(Restrictions.not(Restrictions.eq("estadoCarta.id", EstadoCarta.BORRADO)));
			criteria.add(Restrictions.not(Restrictions.eq("estadoCarta.id", EstadoCarta.SIN_ENVIAR)));
			criteria.addOrder(Order.desc("fechaGeneracion"));

			result = (List<ProyectoCarta>) criteria.list();
		}
		if (result == null) {
			result = new ArrayList<ProyectoCarta>();
		}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {

			session.close();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<SolicitudInvestigador> obtenerSolicitudInvestigador(Long idSolicitud) throws DataAccessException {

		List<SolicitudInvestigador> result = null;
		Session session = getSession();
		try {
		if (idSolicitud != null) {
			

			Criteria criteria = session.createCriteria(SolicitudInvestigador.class);
			criteria.add(Restrictions.eq("solicitud.id", idSolicitud));

			result = (List<SolicitudInvestigador>) criteria.list();
			
		}

		if (result == null) {
			result = new ArrayList<SolicitudInvestigador>();
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return result;
	}
	
	public List<SolicitudProrrogaInvestigador> obtenerSolicitudProrrogaInvestigadores(Long idSolicitud) throws DataAccessException {

		List<SolicitudProrrogaInvestigador> result = null;
		Session session = getSession();
		try {
		if (idSolicitud != null) {
			

			Criteria criteria = session.createCriteria(SolicitudProrrogaInvestigador.class);
			criteria.add(Restrictions.eq("solicitud.id", idSolicitud));

			result = (List<SolicitudProrrogaInvestigador>) criteria.list();
			
		}

		if (result == null) {
			result = new ArrayList<SolicitudProrrogaInvestigador>();
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return result;
	}


	/**
	 * Obtener observaciones seguimiento x proyecto.
	 *
	 * @param idProyecto
	 *            the id proyecto
	 * @return the list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@SuppressWarnings("unchecked")
	public List<ObservacionSeguimiento> obtenerObservacionesSeguimientoXProyecto(Long idProyecto)
			throws DataAccessException {

		List<ObservacionSeguimiento> result = null;

		if (idProyecto != null) {
			Session session = getSession();
			try {

			Criteria criteria = session.createCriteria(ObservacionSeguimiento.class);
			criteria.add(Restrictions.eq("proyecto.id", idProyecto));
			criteria.add(Restrictions.not(Restrictions.eq("estado", ObservacionSeguimiento.BORRADO)));
			criteria.addOrder(Order.desc("fecha"));

			result = (List<ObservacionSeguimiento>) criteria.list();

			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				session.close();
			}
		}

		if (result == null) {
			result = new ArrayList<ObservacionSeguimiento>();
		}

		return result;
	}

	public List obtenerProyectosCoordinadorAsesorxModalidadxFacultad(Modalidad mod, String idFac)
			throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();

		try {
		String query = "select p.pry_id as id, p.pry_nombre as nombre, pp.per_id as perId, PP.TDO_ID as tdoId, pp.PER_REVISION as perRevision, PP.TDO_ID2 as tdoId2, pp.PER_EVALUACION as perEvaluacion, PP.TDO_ID3 as tdoId3, f.DPN_ID as idFacultad, f.DPN_NOMBRE as facultad "
				+ "from her_proyecto p, her_seg_proyecto_persona pp, her_investigador_proyecto ip, her_investigador_interno ii, her_dependencia d, her_dependencia f "
				+ "where p.MOD_ID = " + mod.getId().toString()
				+ " and pp.PRY_ID (+)= p.PRY_ID and p.EPR_ID in ('AP','A','P') "
				+ "and p.PRY_ID = ip.PRY_ID(+) and ip.INV_ID = ii.INV_ID(+) and ip.TDO_ID = ii.TDO_ID(+) and ip.INP_TIPO(+) = 'P' "
				+ "and ii.DPN_ID = d.DPN_ID(+) " + "and d.DPN_FACULTAD  = f.DPN_id(+) " + "and d.DPN_FACULTAD  = '"
				+ idFac + "'" + " " + " order by f.DPN_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING)
				.addScalar("perRevision", Hibernate.STRING).addScalar("tdoId2", Hibernate.STRING)
				.addScalar("perEvaluacion", Hibernate.STRING).addScalar("tdoId3", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.STRING).addScalar("facultad", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinador pc = new ProyectoCoordinador();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			pc.setPerRevision((String) name[4]);
			pc.setTdoId2((String) name[5]);
			pc.setPerEvaluacion((String) name[6]);
			pc.setTdoId3((String) name[7]);
			pc.setIdFacultad((String) name[8]);
			pc.setNombreFacultad((String) name[9]);
			result.add(pc);
		}
		return result;
	}

	// Creado Liliana Olarte
	public List obtenerProyectosCoordinador(Modalidad mod, String documento, String tipodoc, boolean Elegibles)
			throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();

		try {
		String query = " select pp.PRY_ID as CODIGO_PROYECTO, p.PRY_NOMBRE as NOMBRE_PROYECTO, p.MOD_ID as MODALIDAD "
				+ "FROM her_seg_proyecto_persona pp, her_proyecto p " + "WHERE p.MOD_ID = '" + mod.getId() + "' "
				+ "AND pp.PER_EVALUACION = '" + documento + "' " + " AND pp.TDO_ID3 = '" + tipodoc + "' "
				+ "AND p.pry_id = pp.PRY_ID";
		if (Elegibles)
			query += " and p.epr_id = 'E' ";
		else
			query += " and ( p.epr_id = 'P' OR p.epr_id = 'E' )";
		query += " ORDER BY p.PRY_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("CODIGO_PROYECTO", Hibernate.INTEGER)
				.addScalar("NOMBRE_PROYECTO", Hibernate.STRING).addScalar("MODALIDAD", Hibernate.LONG).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Proyecto proyec = new Proyecto();
			proyec.setId(Long.parseLong(String.valueOf(name[0])));
			proyec.setNombre((String) name[1]);
			Modalidad modalidad = new Modalidad();
			modalidad.setId((Long) name[2]);
			proyec.setModalidad(modalidad);
			result.add(proyec);
		}
		return result;
	}

	public List obtenerProyectosPorCriterio(Proyecto criterio) throws DataAccessException {

		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();

		try {
		String query = "select p.pry_id as id, p.pry_nombre as nombre, d.DPN_NOMBRE as dependencia, "
				+ "s.SED_NOMBRE as sede, ep.EPR_NOMBRE as estado "
				+ "from her_proyecto p, her_investigador_proyecto ip, her_investigador_interno ii, "
				+ "her_dependencia d, her_sede s, her_estado_proyecto ep" + "where " +

				"p.PRY_ID = ip.PRY_ID(+) and ip.INV_ID = ii.INV_ID(+) and ip.INP_TIPO(+) = 'P' "
				+ "and ii.DPN_ID = d.DPN_ID(+) " + "and d.SED_ID  = s.SED_ID(+) " + "and p.EPR_ID  = ep.EPR_ID(+) "
				+ "and upper(p.pry_nombre) like upper('%" + criterio.getNombre() + "%') ";

		if (criterio.getDependenciaPrincipal().getId() != null && criterio.getDependenciaPrincipal().getId() != "") {
			query = query + " and d.DPN_ID = '" + criterio.getDependenciaPrincipal().getId() + "'";
		}

		if (criterio.getDependenciaPrincipal().getSede().getId() != null
				&& criterio.getDependenciaPrincipal().getSede().getId() != new Long("0")) {
			query = query + " and s.SED_ID = '" + criterio.getDependenciaPrincipal().getSede().getId() + "'";
		}

		query = query + " and ep.EPR_ID in ('AP','A','F')";
		query = query + " order by d.DPN_ID";
		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("dependencia", Hibernate.STRING).addScalar("sede", Hibernate.STRING)
				.addScalar("estado", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Proyecto proyecto = new Proyecto();
			Dependencia dependencia = new Dependencia();
			Sede sede = new Sede();
			EstadoProyecto estado = new EstadoProyecto();

			proyecto.setId((Long) name[0]);
			proyecto.setNombre((String) name[1]);
			dependencia.setNombre((String) name[2]);
			sede.setNombre((String) name[3]);
			estado.setNombre((String) name[4]);

			dependencia.setSede(sede);
			proyecto.setDependenciaPrincipal(dependencia);
			proyecto.setEstadoProyecto(estado);
			result.add(proyecto);
		}
		return result;
	}

	public List obtenerProyectosPorCriterio(String sql) throws DataAccessException {

		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();

		try {
		String query = "select p.pry_id as id, p.pry_nombre as nombre, d.DPN_NOMBRE as dependencia, "
				+ "s.SED_NOMBRE as sede, ep.EPR_NOMBRE as estado "
				+ "from her_proyecto p, her_investigador_proyecto ip, her_investigador_interno ii,  her_persona inves,"
				+ "her_dependencia d, her_sede s, her_estado_proyecto ep, " + "her_modalidad m "
				+ "where ii.TDO_ID = inves.TDO_ID and ip.TDO_ID = ii.TDO_ID and ii.INV_ID = inves.PER_ID and " + sql +

				"p.PRY_ID = ip.PRY_ID and ip.INV_ID = ii.INV_ID and ip.INP_TIPO = 'P' " + "and ii.DPN_ID = d.DPN_ID "
				+ "and p.MOD_ID = m.MOD_ID " + "and d.SED_ID  = s.SED_ID " + "and p.EPR_ID  = ep.EPR_ID ";

		query = query + " and ep.EPR_ID in ('AP','A','F')";
		query = query + " order by d.DPN_ID";
		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("dependencia", Hibernate.STRING).addScalar("sede", Hibernate.STRING)
				.addScalar("estado", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Proyecto proyecto = new Proyecto();
			Dependencia dependencia = new Dependencia();
			Sede sede = new Sede();
			EstadoProyecto estado = new EstadoProyecto();

			proyecto.setId((Long) name[0]);
			proyecto.setNombre((String) name[1]);
			dependencia.setNombre((String) name[2]);
			sede.setNombre((String) name[3]);
			estado.setNombre((String) name[4]);

			dependencia.setSede(sede);
			proyecto.setDependenciaPrincipal(dependencia);
			proyecto.setEstadoProyecto(estado);
			result.add(proyecto);
		}
		return result;
	}

	public List obtenerProyectos(String name) throws DataAccessException {

		List proyectos = new ArrayList();
		Session session = getSession();

		try {
		Criteria criteria = session.createCriteria(Proyecto.class);
		Criterion criterion = Restrictions.ilike("nombre", name, MatchMode.ANYWHERE);
		criteria.add(criterion);
		criteria.addOrder(Order.asc("nombre"));
		proyectos = criteria.list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return proyectos;
	}

	public List obtenerProyectosXId(Long id) throws DataAccessException {

		List proyectos = new ArrayList();
		Session session = getSession();

		try {
		Criteria criteria = session.createCriteria(Proyecto.class);
		// Criterion criterion = Restrictions.ilike("id", id,
		// MatchMode.ANYWHERE);
		Criterion criterion = Restrictions.idEq(id);
		criteria.add(criterion);
		criteria.addOrder(Order.asc("id"));
		proyectos = criteria.list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return proyectos;
	}

	public List<LaboratorioDetalleProyectos> obtenerDetalleProyectosPreAsociadosLaboratorioXIdLab(Long idLab)
			throws DataAccessException {
		String query = "from LaboratorioDetalleProyectos ldp where ldp.estado = 'I' AND ldp.laboratorio.id = " + idLab;
		// List<LaboratorioDetalleProyectos> lista =
		// getHibernateTemplate().find(query);
		return getHibernateTemplate().find(query);
	}

	public List<LaboratorioDetalleProyectos> obtenerDetalleProyectosPreAsociadosLaboratorioXIdProyecto(Long idPry)
			throws DataAccessException {
		String query = "from LaboratorioDetalleProyectos ldp where ldp.estado = 'I' AND ldp.proyecto.id = " + idPry;
		// List<LaboratorioDetalleProyectos> lista =
		// getHibernateTemplate().find(query);
		return getHibernateTemplate().find(query);
	}

	public List obtenerProyectosXNombre(String name) throws DataAccessException {

		List proyectos = new ArrayList();
		Session session = getSession();
		
		try {
		Criteria criteria = session.createCriteria(Proyecto.class);
		Criterion criterion = Restrictions.sqlRestriction(ReemplazaAcentos.queryQuitaTildes("pry_nombre") + " like '%"
				+ ReemplazaAcentos.quitarTildes(name).toLowerCase() + "%' and epr_id <> 'B' and epr_id <> 'BS' ");
		criteria.add(criterion);
		criteria.addOrder(Order.asc("nombre"));
		proyectos = criteria.list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return proyectos;
	}
	
	public List obtenerProyectosXNombreExacto(String name) throws DataAccessException {
		List proyectos = new ArrayList();
		Session session = getSession();

		try {
		Criteria criteria = session.createCriteria(Proyecto.class);
		Criterion criterion = Restrictions.sqlRestriction("REGEXP_REPLACE(pry_nombre, '\\s{2,}', ' ') like '"
				+ name + "' and epr_id <> 'B' and epr_id <> 'BS' ");
		criteria.add(criterion);
		criteria.addOrder(Order.asc("nombre"));
		proyectos = criteria.list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return proyectos;
	}

	public List obtenerProyectosXCodigoQuipu(String codigo) throws DataAccessException {

		Session session = getSession();
		List proyectos = new ArrayList();
		
		try {
		Criteria criteria = session.createCriteria(Proyecto.class);
		Criterion criterion = Restrictions.sqlRestriction(ReemplazaAcentos.queryQuitaTildes("pry_codigo_quipu")
				+ " like '%" + ReemplazaAcentos.quitarTildes(codigo).toLowerCase()
				+ "%' and epr_id <> 'B' and epr_id <> 'BS'");
		criteria.add(criterion);
		criteria.addOrder(Order.asc("nombre"));
		proyectos = criteria.list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return proyectos;
	}

	public List obtenerProyectosXNombreYEstadoDiferente(String name, String idEstado) throws DataAccessException {

		Session session = getSession();
		List proyectos = new ArrayList();
		
		try {
		Criteria criteria = session.createCriteria(Proyecto.class);
		Criterion criterion = Restrictions.sqlRestriction(ReemplazaAcentos.queryQuitaTildes("pry_nombre") + " like '%"
				+ ReemplazaAcentos.quitarTildes(name).toLowerCase() + "%'");
		criteria.add(criterion);
		criteria.add(Restrictions.ne("estadoProyecto.id", idEstado));
		criteria.addOrder(Order.asc("nombre"));
		proyectos = criteria.list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return proyectos;
	}

	public Laboratorio buscarLaboratorioPorId(Long id, short informacion) throws DataAccessException {

		Session session = getSession();
		Laboratorio p = null;
		
		try {
		switch (informacion) {
		case LABORATORIO_CLASIF_CONOC:
			System.out.println("LABORATORIO_CLASIF_CONOC");
			p = (Laboratorio) session.createCriteria(Laboratorio.class)
					.setFetchMode("clasificacionConocimientoLab", FetchMode.JOIN).add(Restrictions.idEq(id))
					.uniqueResult();
			break;
		}

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		return p;

	}

	public Proyecto buscarPorId(Long id, short informacion, boolean incluirGastos) throws DataAccessException {

		Session session = getSession();
		Proyecto p = null;
		try {
		switch (informacion) {
		case TODO_POR_ID:

			try {
				p = (Proyecto) session.createCriteria(Proyecto.class).add(Restrictions.idEq(id)).uniqueResult();
				Hibernate.initialize(p.getFinanciaciones());
				if (incluirGastos && p.getFinanciaciones() != null) {
					Iterator<Financiacion> i = p.getFinanciaciones().iterator();
					while (i.hasNext()) {
						Financiacion financiacionTemporal = i.next();
						Hibernate.initialize(financiacionTemporal.getGastos());
					}
				}
				Hibernate.initialize(p.getPalabrasClaves());
				Hibernate.initialize(p.getDependenciasAreaResponsabilidad());
				Hibernate.initialize(p.getDependenciasAportantes());
				Hibernate.initialize(p.getInvestigadoresProyecto());
				Hibernate.initialize(p.getProductosProyecto());
				Hibernate.initialize(p.getObjetivosEspecificos());
				Hibernate.initialize(p.getResultados());
				Hibernate.initialize(p.getHistorico());
				Hibernate.initialize(p.getClasificacionConocimiento());
				Hibernate.initialize(p.getActividades());
				Hibernate.initialize(p.getGrupos());
				Hibernate.initialize(p.getArchivos());
				Hibernate.initialize(p.getSolicitudes());
				Hibernate.initialize(p.getProrrogasProyecto());
				Hibernate.initialize(p.getCiudades());
				Hibernate.initialize(p.getObjetivosDesarrolloSostenible());
				Hibernate.initialize(p.getProyectosPrograma());
				Hibernate.initialize(p.getCompromisosProyecto());

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error al buscar el proyecto");
			}
			break;

		case METODO_VIEJO:
			try {
				p = (Proyecto) session.createCriteria(Proyecto.class)
						.setFetchMode("investigadoresProyecto", FetchMode.JOIN).add(Restrictions.idEq(id))
						.uniqueResult();
			} catch (Exception e) {
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("tipoInvestigacion", FetchMode.JOIN)
						.add(Restrictions.idEq(id)).uniqueResult();
			}
			break;
		case DATOS_BASICOS:
			System.out.println("DATOS_BASICOS");
			try {
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("tipoInvestigacion", FetchMode.JOIN)
						.setFetchMode("palabrasClaves", FetchMode.JOIN).setFetchMode("ciudades", FetchMode.JOIN)
						.add(Restrictions.idEq(id)).uniqueResult();
			} catch (Exception e) {
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("tipoInvestigacion", FetchMode.JOIN)
						.add(Restrictions.idEq(id)).uniqueResult();
			}
			break;
		case INFORMACION_GENERAL:
			System.out.println("INFORMACION_GENERAL");
			try {
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("palabrasClaves", FetchMode.JOIN)
						.setFetchMode("ciudades", FetchMode.JOIN)
						.setFetchMode("posiblesEvaluadoresExternos", FetchMode.JOIN).add(Restrictions.idEq(id))
						.uniqueResult();
				Hibernate.initialize(p.getFinanciaciones());
				if (incluirGastos && p.getFinanciaciones() != null) {
					Iterator<Financiacion> i = p.getFinanciaciones().iterator();
					while (i.hasNext()) {
						Financiacion financiacionTemporal = i.next();
						Hibernate.initialize(financiacionTemporal.getGastos());
					}
				}
			} catch (Exception e) {
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("tipoInvestigacion", FetchMode.JOIN)
						.add(Restrictions.idEq(id)).uniqueResult();
				Hibernate.initialize(p.getFinanciaciones());
				if (incluirGastos && p.getFinanciaciones() != null) {
					Iterator<Financiacion> i = p.getFinanciaciones().iterator();
					while (i.hasNext()) {
						Financiacion financiacionTemporal = i.next();
						Hibernate.initialize(financiacionTemporal.getGastos());
					}
				}
			}
			break;
		case INVESTIGADORES:
			System.out.println("INVESTIGADORES");
			try {
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("convocatoria", FetchMode.JOIN)
						.setFetchMode("grupos", FetchMode.JOIN).setFetchMode("modalidad", FetchMode.JOIN)
						.setFetchMode("investigadoresProyecto", FetchMode.JOIN).add(Restrictions.idEq(id))
						.uniqueResult();

			} catch (Exception e) {
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("tipoInvestigacion", FetchMode.JOIN)
						.add(Restrictions.idEq(id)).uniqueResult();
			}
			break;
		case LINEAS:
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("lineas", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;
		case AGENDAS:
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("agendas", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;
		case LINEAS_DEPENDENCIAS:
			System.out.println("LINEAS_DEPENDENCIAS");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("lineas", FetchMode.JOIN)
					.setFetchMode("dependenciasAreaResponsabilidad", FetchMode.JOIN).setFetchMode("dependenciasAportantes", FetchMode.JOIN).add(Restrictions.idEq(id))
					.uniqueResult();
			break;
		case PLANDEACCION:
			System.out.println("OBJETIVOS_RESULTADOS");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("objetivosEspecificos", FetchMode.JOIN)
					.setFetchMode("resultados", FetchMode.JOIN).setFetchMode("actividades", FetchMode.JOIN)
					.setFetchMode("convocatoria", FetchMode.JOIN).setFetchMode("grupos", FetchMode.JOIN)
					.setFetchMode("modalidad", FetchMode.JOIN).setFetchMode("investigadoresProyecto", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;

		case OBJETIVOS_RESULTADOS:
			System.out.println("OBJETIVOS_RESULTADOS");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("objetivosEspecificos", FetchMode.JOIN)
					.setFetchMode("resultados", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();
			break;

		case ACTIVIDADES:
			System.out.println("ACTIVIDADES");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("actividades", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;

		case BIBLIOGRAFIAS:
			System.out.println("BIBLIOGRAFIA");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("bibliografias", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;

		case POSIBLES_EVALUADORES:
			System.out.println("POSIBLES_EVALUADORES");
			p = (Proyecto) session.createCriteria(Proyecto.class)
					.setFetchMode("posiblesEvaluadoresExternos", FetchMode.JOIN)
					.setFetchMode("modalidad", FetchMode.JOIN).setFetchMode("grupos", FetchMode.JOIN)
					.setFetchMode("posiblesEvaluadoresInternos", FetchMode.JOIN)
					.setFetchMode("clasificacionConocimiento", FetchMode.JOIN)
					.setFetchMode("posiblesEvaluadoresInvestigadoresExternos", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;

		case CLASIFICACION_CONOCIMIENTO:
			System.out.println("CLASIFICACION_CONOCIMIENTO");
			p = (Proyecto) session.createCriteria(Proyecto.class)
					.setFetchMode("clasificacionConocimiento", FetchMode.JOIN).add(Restrictions.idEq(id))
					.uniqueResult();
			break;

		case DEPENDENCIAS:
			System.out.println("DEPENDENCIAS");
			p = (Proyecto) session.createCriteria(Proyecto.class)
					.setFetchMode("dependenciasAreaResponsabilidad", FetchMode.JOIN).add(Restrictions.idEq(id))
					.uniqueResult();
			break;

		case EVALUADORES:
			System.out.println("EVALUADORES");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("evaluadoresProyecto", FetchMode.JOIN)
					.setFetchMode("grupos", FetchMode.JOIN).setFetchMode("modalidad", FetchMode.JOIN)
					.setFetchMode("historico", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();
			break;

		case FUENTES_FINANCIACION:
			System.out.println("FUENTES_FINANCIACION");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("financiaciones", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;

		case RUBROS:
			System.out.println("RUBROS");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("historico", FetchMode.JOIN)
					.setFetchMode("convocatoria", FetchMode.JOIN).setFetchMode("modalidad", FetchMode.JOIN)
					.setFetchMode("financiaciones", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();
			break;

		case INFORMACION_FINANCIERA:
			System.out.println("INFORMACION_FINANCIERA");

			try {
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("historico", FetchMode.JOIN)
						.setFetchMode("rubros", FetchMode.JOIN).setFetchMode("financiaciones", FetchMode.JOIN)
						.setFetchMode("vigencias", FetchMode.JOIN)
						.setFetchMode("investigadoresProyecto", FetchMode.JOIN).add(Restrictions.idEq(id))
						.uniqueResult();
			} catch (Exception e) {
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("tipoInvestigacion", FetchMode.JOIN)
						.add(Restrictions.idEq(id)).uniqueResult();
			}

			break;

		case UNICO:
			System.out.println("UNICO");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("tipoInvestigacion", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;

		case EQUIPOS:
			System.out.println("EQUIPOS");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("equipos", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;

		case FICHA_QUIPU:
			System.out.println("FICHA_QUIPU");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("rubros", FetchMode.JOIN)
					.setFetchMode("financiaciones", FetchMode.JOIN).setFetchMode("vigencias", FetchMode.JOIN)
					.setFetchMode("investigadoresProyecto", FetchMode.JOIN)
					.setFetchMode("clasificacionConocimiento", FetchMode.JOIN)
					.setFetchMode("planGlobalDesarrollo", FetchMode.JOIN)
					.setFetchMode("objetivosEspecificos", FetchMode.JOIN).setFetchMode("resultados", FetchMode.JOIN)
					.setFetchMode("dependenciasAreaResponsabilidad", FetchMode.JOIN)
					.setFetchMode("tipoInvestigacion", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();
			break;

		case AREAS_TEMATICAS:
			System.out.println("AREAS_TEMATICAS");
			p = (Proyecto) session.createCriteria(Proyecto.class)
					.setFetchMode("clasificacionConocimiento", FetchMode.JOIN)
					.setFetchMode("planGlobalDesarrollo", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();
			break;

		case PRODUCTOS:
			System.out.println("PRODUCTOS");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("productosProyecto", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;

		case REQUISITOS:
			System.out.println("REQUISITOS");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("requisitosProyecto", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;

		case CREACIONARTISTICA:
			System.out.println("CREACIONARTISTICA");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("tipoInvestigacion", FetchMode.JOIN)
					.setFetchMode("creacionArtistica", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();
			break;

		case ASESORES:
			System.out.println("ASESORES");

			try {
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("asesores", FetchMode.JOIN)
						.add(Restrictions.idEq(id)).uniqueResult();
				Hibernate.initialize(p.getFinanciaciones());
				break;
			} catch (Exception e) {
				e.printStackTrace();
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("asesores", FetchMode.JOIN)
						.add(Restrictions.idEq(id)).uniqueResult();
				Hibernate.initialize(p.getFinanciaciones());
				break;
			}
			
		case DATOS_PARA_AVAL:
			System.out.println("DATOS PARA AVAL");

			try {
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("asesores", FetchMode.JOIN).setFetchMode("grupos", FetchMode.JOIN)
						.add(Restrictions.idEq(id)).uniqueResult();
				Hibernate.initialize(p.getFinanciaciones());
				break;
			} catch (Exception e) {
				e.printStackTrace();
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("asesores", FetchMode.JOIN).setFetchMode("grupos", FetchMode.JOIN)
						.add(Restrictions.idEq(id)).uniqueResult();
				Hibernate.initialize(p.getFinanciaciones());
				break;
			}

		case PRORROGAS:
			System.out.println("PRORROGAS");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("prorrogasProyecto", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;

		case OBSERVACIONES:
			System.out.println("OBSERVACIONES");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("observacionesProyecto", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;

		case COMPROMISOS:
			System.out.println("COMPROMISOS");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("compromisosProyecto", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;

		case EMPRESAS:
			System.out.println("EMPRESAS");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("empresas", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;
		case EMPRESASEINVESTIGADORES:
			System.out.println("EMPRESASEINVESTIGADOR");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("empresas", FetchMode.JOIN)
					.setFetchMode("convocatoria", FetchMode.JOIN).setFetchMode("grupos", FetchMode.JOIN)
					.setFetchMode("modalidad", FetchMode.JOIN).setFetchMode("investigadoresProyecto", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;
		case GRUPOSEXTERNOS:
			System.out.println("GRUPOSEXTERNOS");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("programaNacional", FetchMode.JOIN)
					.setFetchMode("modalidad", FetchMode.JOIN).setFetchMode("investigadoresProyecto", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;
		case INVESTIGADORESYACTIVIDADES:
			System.out.println("INVESTIGADORESYACTIVIDADES");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("actividades", FetchMode.JOIN)
					.setFetchMode("convocatoria", FetchMode.JOIN).setFetchMode("grupos", FetchMode.JOIN)
					.setFetchMode("modalidad", FetchMode.JOIN).setFetchMode("investigadoresProyecto", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).uniqueResult();
			break;
		case LINEAS_DEPENDENCIAS_UNICO:
			System.out.println("LINEAS_DEPENDENCIAS_UNICO");
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("lineas", FetchMode.JOIN)
					.setFetchMode("dependenciasAreaResponsabilidad", FetchMode.JOIN)
					.setFetchMode("equiposAdquisicion", FetchMode.JOIN)
					.setFetchMode("grupos", FetchMode.JOIN).setFetchMode("ciudades", FetchMode.JOIN)
					.setFetchMode("objetivosResultados", FetchMode.JOIN).add(Restrictions.idEq(id))
					.add(Restrictions.idEq(id)).uniqueResult();
			break;
		case LIMPIO:
			try {
				p = (Proyecto) session.createCriteria(Proyecto.class).add(Restrictions.idEq(id))
						.add(Restrictions.sqlRestriction("epr_id <> 'B'"))
						.add(Restrictions.sqlRestriction("epr_id <> 'BS'")).uniqueResult();
				Hibernate.initialize(p.getFinanciaciones());
				Hibernate.initialize(p.getCiudades());
				Hibernate.initialize(p.getDependenciasAreaResponsabilidad());
			} catch (Exception e) {
				e.printStackTrace();
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("tipoInvestigacion", FetchMode.JOIN)
						.add(Restrictions.idEq(id)).uniqueResult();
				Hibernate.initialize(p.getFinanciaciones());
			}

			break;
		case LIMPIO_SEGUIMIENTO:
			try {
				p = (Proyecto) session.createCriteria(Proyecto.class).add(Restrictions.idEq(id))
						.add(Restrictions.sqlRestriction("epr_id <> 'B'"))
						.add(Restrictions.sqlRestriction("epr_id <> 'BS'")).uniqueResult();
				// Hibernate.initialize(p.getFinanciaciones());
				Hibernate.initialize(p.getDependenciasAreaResponsabilidad());
				Hibernate.initialize(p.getInformes());
				Hibernate.initialize(p.getCompromisosProyecto());
				Hibernate.initialize(p.getProrrogasProyecto());
				Hibernate.initialize(p.getSolicitudes());
				Hibernate.initialize(p.getCiudades());
				if (p.getFinanciaciones() != null) {
					Iterator<Financiacion> i = p.getFinanciaciones().iterator();
					while (i.hasNext()) {
						Financiacion financiacionTemporal = i.next();
						Hibernate.initialize(financiacionTemporal.getGastos());
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("tipoInvestigacion", FetchMode.JOIN)
						.setFetchMode("modalidad", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();
				Hibernate.initialize(p.getFinanciaciones());
			}

			break;
		case LIMPIO_SIN_CIUDAD:
			try {
				p = (Proyecto) session.createCriteria(Proyecto.class).add(Restrictions.idEq(id)).uniqueResult();

			} catch (Exception e) {
				p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("tipoInvestigacion", FetchMode.JOIN)
						.add(Restrictions.idEq(id)).uniqueResult();
			}

			break;

		}
		Hibernate.initialize(p.getPalabrasClaves());
		Hibernate.initialize(p.getTitulosEditorial());
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		return p;

	}

	public Proyecto obtenerProyectoEditorialEvaluacion(Long id, IdPersona idCoordinador) throws DataAccessException {

		String consultaProyecto = "from ProyectoCoordinadorEditorial pc where pc.perEvaluacion = '" + idCoordinador.getDocumento()
				+ "' and pc.tdoId3 = '" + idCoordinador.getTipoDocumento() + "' and pc.idProyecto = '" + id + "'";
		List<ProyectoCoordinador> lista = getHibernateTemplate().find(consultaProyecto);
		if (lista != null && lista.size() > 0) {
			// Si se cumple se carga proyecto
			Proyecto p = new Proyecto();
			Session session = getSession();
			try {
			
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("grupos", FetchMode.JOIN)
					.setFetchMode("modalidad", FetchMode.JOIN).setFetchMode("historico", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).add(Restrictions.not(Restrictions.eq("estadoProyecto.id", "B")))
					.uniqueResult();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				session.close();
			}
			return p;
		}

		return null;
	}
	
	public Proyecto buscarPorIdAsignadosEvaluacion(Long id, IdPersona idAsesor) throws DataAccessException {

		String consultaProyecto = "from ProyectoCoordinador pc where pc.perEvaluacion = '" + idAsesor.getDocumento()
				+ "' and pc.tdoId3 = '" + idAsesor.getTipoDocumento() + "' and pc.idProyecto = '" + id + "'";
		List<ProyectoCoordinador> lista = getHibernateTemplate().find(consultaProyecto);
		if (lista != null && lista.size() > 0) {
			// Si se cumple se carga proyecto
			Proyecto p = new Proyecto();
			Session session = getSession();
			try {
			p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("grupos", FetchMode.JOIN)
					.setFetchMode("modalidad", FetchMode.JOIN).setFetchMode("historico", FetchMode.JOIN)
					.add(Restrictions.idEq(id)).add(Restrictions.not(Restrictions.eq("estadoProyecto.id", "B")))
					.uniqueResult();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				session.close();
			}
			return p;
		}
		return null;
	}

	public List obtenerProyectoEvaluadoresInternos(Long idProyecto, Modalidad mod) throws DataAccessException {
		List l = new ArrayList();
		String restriccion = "(TDO_ID,INV_ID) IN (SELECT TDO_ID,INV_ID FROM HER_POSIBLE_EVALUADOR_INTERNO WHERE PRY_ID="
				+ idProyecto.longValue()
				+ ") and (TDO_ID,INV_ID) NOT IN (SELECT TDO_ID,INV_ID FROM HER_INVESTIGADOR_PROYECTO where PRY_ID IN (SELECT PRY_ID from HER_PROYECTO where MOD_ID in (select CON_ID from HER_CONVOCATORIA where CNP_ID="
				+ mod.getId() + ")))";
		Session session = getSession();
		try {
			Criteria criteria = session.createCriteria(Investigador.class).add(Restrictions.sqlRestriction(restriccion));
			l = criteria.list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return l;
	}

	public void insertarNuevoEvaluadorExterno() {

		Session session = getSession();
		try {
			session = getSession();
			session.connection().createStatement();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
		session.close();
		}
	}

	public List obtenerProyectosxModalidad(Modalidad mod) throws DataAccessException {
		EstadoProyecto ep = new EstadoProyecto();
		ep.setId("P");
		List l = new ArrayList();
		Session session = getSession();
		try {
		Criteria criteria = session.createCriteria(Proyecto.class).add(Expression.eq("estadoProyecto", ep))
				.setFetchMode("investigadoresProyecto", FetchMode.JOIN)
				.setFetchMode("evaluadoresProyecto", FetchMode.JOIN).setFetchMode("asesores", FetchMode.JOIN);
		criteria.addOrder(Order.desc("nombre"));
		Criteria criteriaModalidad = criteria.createCriteria("modalidad");
		criteriaModalidad.add(Restrictions.idEq(mod.getId()));
		Set s = new HashSet();
		s.addAll(criteria.list());
		l.addAll(s);

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return l;
	}

	public List obtenerProyectosCoordinadorxModalidadxFacultadEstado(Modalidad mod, String idFac)
			throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();

		try {
		String query = "select p.pry_id as id, p.pry_nombre as nombre, pp.per_id as perId, PP.TDO_ID as tdoId, f.DPN_ID as idFacultad, f.DPN_NOMBRE as facultad"
				+ ", p.EPR_ID as estado , pp.per_revision as perRevision , pp.per_evaluacion as perEvaluacion "
				+ "from her_proyecto p, her_seg_proyecto_persona pp, her_investigador_proyecto ip, her_investigador_interno ii, her_dependencia d, her_dependencia f "
				+ "where p.MOD_ID = " + mod.getId().toString() + " and pp.PRY_ID (+)= p.PRY_ID "
				+ "and p.PRY_ID = ip.PRY_ID(+) and ip.INV_ID = ii.INV_ID(+) and ip.TDO_ID = ii.TDO_ID(+) and ip.INP_TIPO(+) = 'P' "
				+ "and ii.DPN_ID = d.DPN_ID(+) " + "and d.DPN_FACULTAD  = f.DPN_id(+) " +
				// "and d.DPN_FACULTAD = '"+idFac+"'" + " " +
				"group by p.pry_id, p.pry_nombre, pp.per_id, pp.per_revision, pp.per_evaluacion, PP.TDO_ID, f.DPN_ID, f.DPN_NOMBRE, p.EPR_ID "
				+ "order by p.pry_id";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.STRING).addScalar("facultad", Hibernate.STRING)
				.addScalar("estado", Hibernate.STRING).addScalar("perRevision", Hibernate.STRING)
				.addScalar("perEvaluacion", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinador pc = new ProyectoCoordinador();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			pc.setIdFacultad((String) name[4]);
			pc.setNombreFacultad((String) name[5]);
			pc.setEstadoProyecto((String) name[6]);
			pc.setPerRevision((String) name[7]);
			pc.setPerEvaluacion((String) name[8]);
			result.add(pc);
		}
		return result;
	}

	public List<ProyectoCoordinador> obtenerProyectosCoordinadorxIdProyecto(String id) throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();

		try {
		String query = "select p.pry_id as id, p.pry_nombre as nombre, pp.per_id "
				+ "as perId, PP.TDO_ID as tdoId, f.DPN_ID as idFacultad, "
				+ "f.DPN_NOMBRE as facultad, p.EPR_ID as estado ,"
				+ "per_seg.per_nombre1 || ' ' || per_seg.per_nombre2 || ' ' ||"
				+ "per_seg.per_apellido1 || ' ' || per_seg.per_apellido2 nombreSeguimiento, "
				+ "per_req.per_nombre1 || ' ' || per_req.per_nombre2 || ' ' ||"
				+ "per_req.per_apellido1 || ' ' || per_req.per_apellido2 nombreRequisitos, "
				+ "per_eva.per_nombre1 || ' ' || per_eva.per_nombre2 || ' ' ||"
				+ "per_eva.per_apellido1 || ' ' || per_eva.per_apellido2 nombreEvaluacion,"
				+ "e.epr_nombre estadoNombre, p.mod_id as modalidad "
				+ "from her_proyecto p left join her_seg_proyecto_persona pp on pp.PRY_ID = p.PRY_ID"
				+ " left join her_persona per_seg " + " on pp.per_id = per_seg.per_id and pp.tdo_id = per_seg.tdo_id"
				+ " left join her_persona per_req "
				+ " on pp.per_Revision = per_req.per_id and pp.tdo_id2 = per_req.tdo_id"
				+ " left join her_persona per_eva "
				+ " on pp.per_Evaluacion = per_eva.per_id and pp.tdo_id3 = per_eva.tdo_id, "
				+ "her_investigador_proyecto ip, her_investigador_interno ii, "
				+ "her_dependencia d, her_dependencia f, her_estado_proyecto e " + "where p.PRY_ID = '" + id
				+ "' and p.PRY_ID = ip.PRY_ID and ip.INV_ID = ii.INV_ID and "
				+ "ip.TDO_ID = ii.TDO_ID and ip.INP_TIPO = 'P' " + "and ii.DPN_ID = d.DPN_ID "
				+ "and d.DPN_FACULTAD  = f.DPN_id " + "and e.epr_id = p.epr_id" + " order by p.pry_id";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.STRING).addScalar("facultad", Hibernate.STRING)
				.addScalar("estado", Hibernate.STRING).addScalar("nombreSeguimiento", Hibernate.STRING)
				.addScalar("nombreRequisitos", Hibernate.STRING).addScalar("nombreEvaluacion", Hibernate.STRING)
				.addScalar("estadoNombre", Hibernate.STRING).addScalar("modalidad", Hibernate.LONG).list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinador pc = new ProyectoCoordinador();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			pc.setIdFacultad((String) name[4]);
			pc.setNombreFacultad((String) name[5]);
			pc.setEstadoProyecto((String) name[6]);
			pc.setEstadoProyectoAnterior((String) name[6]);
			pc.setNombreSeguimiento((String) name[7]);
			pc.setNombreRevision((String) name[8]);
			pc.setNombreEvaluacion((String) name[9]);
			pc.setNombreEstadoProyecto((String) name[10]);
			Modalidad modalidad = new Modalidad();
			modalidad.setId((Long) name[11]);
			Proyecto proyecto = new Proyecto();
			proyecto.setModalidad(modalidad);
			pc.setProyecto(proyecto);
			result.add(pc);
		}
		return result;
	}

	public List obtenerProyectosCoordinadorAsesorxModalidadxFacultad(Modalidad mod, String idFac, String idEstProy)
			throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();

		try {
		String query = "select p.pry_id as id, p.pry_nombre as nombre, " + "pp.per_id as perId, PP.TDO_ID as tdoId, "
				+ "pp.PER_REVISION as perRevision, PP.TDO_ID2 as tdoId2, "
				+ "pp.PER_EVALUACION as perEvaluacion, PP.TDO_ID3 as tdoId3, "
				+ "f.DPN_ID as idFacultad, f.DPN_NOMBRE as facultad ,"
				+ "per_seg.per_nombre1 || ' ' || per_seg.per_nombre2 || ' ' ||"
				+ "per_seg.per_apellido1 || ' ' || per_seg.per_apellido2 nombreSeguimiento, "
				+ "per_req.per_nombre1 || ' ' || per_req.per_nombre2 || ' ' ||"
				+ "per_req.per_apellido1 || ' ' || per_req.per_apellido2 nombreRequisitos, "
				+ "per_eva.per_nombre1 || ' ' || per_eva.per_nombre2 || ' ' ||"
				+ "per_eva.per_apellido1 || ' ' || per_eva.per_apellido2 nombreEvaluacion " + "from "
				+ "her_investigador_proyecto ip, her_investigador_interno ii, "
				+ "her_dependencia d left join her_dependencia f on d.DPN_FACULTAD  = f.DPN_id, her_proyecto p left join "
				+ " her_seg_proyecto_persona pp on pp.PRY_ID = p.PRY_ID  " + " left join her_persona per_seg "
				+ " on pp.per_id = per_seg.per_id and pp.tdo_id = per_seg.tdo_id" + " left join her_persona per_req "
				+ " on pp.per_Revision = per_req.per_id and pp.tdo_id2 = per_req.tdo_id"
				+ " left join her_persona per_eva "
				+ " on pp.per_Evaluacion = per_eva.per_id and pp.tdo_id3 = per_eva.tdo_id" + " where p.MOD_ID = "
				+ mod.getId().toString() + "and p.PRY_ID = ip.PRY_ID and ip.INV_ID = "
				+ "ii.INV_ID and ip.TDO_ID = ii.TDO_ID " + "and p.epr_id not in ('B') " + "and ip.INP_TIPO = 'P' "
				+ "and ii.DPN_ID = d.DPN_ID " + "and d.DPN_FACULTAD  = '" + idFac + "'" + " and p.EPR_ID in ('"
				+ idEstProy + "')" + " order by f.DPN_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING)
				.addScalar("perRevision", Hibernate.STRING).addScalar("tdoId2", Hibernate.STRING)
				.addScalar("perEvaluacion", Hibernate.STRING).addScalar("tdoId3", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.STRING).addScalar("facultad", Hibernate.STRING)
				.addScalar("nombreSeguimiento", Hibernate.STRING).addScalar("nombreRequisitos", Hibernate.STRING)
				.addScalar("nombreEvaluacion", Hibernate.STRING).list();
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinador pc = new ProyectoCoordinador();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			pc.setPerRevision((String) name[4]);
			pc.setTdoId2((String) name[5]);
			pc.setPerEvaluacion((String) name[6]);
			pc.setTdoId3((String) name[7]);
			pc.setIdFacultad((String) name[8]);
			pc.setNombreFacultad((String) name[9]);
			pc.setNombreSeguimiento((String) name[10]);
			pc.setNombreRevision((String) name[11]);
			pc.setNombreEvaluacion((String) name[12]);
			result.add(pc);
		}
		return result;
	}

	// CREADA POR ING. LILIANA OLARTE
	public List obtenerProyectosCoordinadorxModalidadxSede(Modalidad mod, String dep) throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();
		
		try {
		String query = "select p.pry_id as id, p.pry_nombre as nombre, " + "pp.per_id as perId, PP.TDO_ID as tdoId, "
				+ "pp.PER_REVISION as perRevision, PP.TDO_ID2 as tdoId2, "
				+ "pp.PER_EVALUACION as perEvaluacion, PP.TDO_ID3 as tdoId3, "
				+ "f.DPN_ID as idFacultad, f.DPN_NOMBRE as facultad "
				+ "from her_proyecto p, her_seg_proyecto_persona pp, "
				+ "her_investigador_proyecto ip, her_investigador_interno ii, "
				+ "her_dependencia d, her_dependencia f " + "where p.MOD_ID = " + mod.getId().toString()
				+ " and pp.PRY_ID (+)= p.PRY_ID and " + "p.EPR_ID in ('AP','A','P','E','R','S') "
				+ "and p.PRY_ID = ip.PRY_ID(+) and ip.INV_ID = ii.INV_ID(+) "
				+ "and ip.TDO_ID = ii.TDO_ID(+) and ip.INP_TIPO(+) = 'P' " + "and ii.DPN_ID = d.DPN_ID(+) "
				+ "and d.DPN_FACULTAD  = f.DPN_id(+) " + "and d.SED_ID  = '" + dep + "'" + " " + "order by p.pry_id";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);
		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING)
				.addScalar("perRevision", Hibernate.STRING).addScalar("tdoId2", Hibernate.STRING)
				.addScalar("perEvaluacion", Hibernate.STRING).addScalar("tdoId3", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.STRING).addScalar("facultad", Hibernate.STRING).list();
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinador pc = new ProyectoCoordinador();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			pc.setPerRevision((String) name[4]);
			pc.setTdoId2((String) name[5]);
			pc.setPerEvaluacion((String) name[6]);
			pc.setTdoId3((String) name[7]);
			pc.setIdFacultad((String) name[8]);
			pc.setNombreFacultad((String) name[9]);
			result.add(pc);
		}

		return result;
	}

	public List obtenerProyectosCoordinadorxModalidadxFacultad(Modalidad mod, String idFac) throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();

		try {
		String query = "select p.pry_id as id, p.pry_nombre as nombre, pp.per_id as perId, PP.TDO_ID as tdoId, f.DPN_ID as idFacultad, f.DPN_NOMBRE as facultad "
				+ "from her_proyecto p, her_seg_proyecto_persona pp, her_investigador_proyecto ip, her_investigador_interno ii, her_dependencia d, her_dependencia f "
				+ "where p.MOD_ID = " + mod.getId().toString()
				+ " and pp.PRY_ID (+)= p.PRY_ID and p.EPR_ID in ('AP','A','P') "
				+ "and p.PRY_ID = ip.PRY_ID(+) and ip.INV_ID = ii.INV_ID(+) and ip.TDO_ID = ii.TDO_ID(+) and ip.INP_TIPO(+) = 'P' "
				+ "and ii.DPN_ID = d.DPN_ID(+) " + "and d.DPN_FACULTAD  = f.DPN_id(+) " + "and d.DPN_FACULTAD  = '"
				+ idFac + "'" + " " + "group by p.pry_id, p.pry_nombre, pp.per_id, PP.TDO_ID, f.DPN_ID, f.DPN_NOMBRE "
				+ "order by f.DPN_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.STRING).addScalar("facultad", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinador pc = new ProyectoCoordinador();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			pc.setIdFacultad((String) name[4]);
			pc.setNombreFacultad((String) name[5]);
			result.add(pc);
		}
		return result;
	}

	public List obtenerProyectosCoordinadorxModalidadSede(Modalidad mod, Dependencia dep, Persona pep)
			throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();

		try {
		String query = "select p.pry_id as id, p.pry_nombre as nombre, pp.per_id as perId, PP.TDO_ID as tdoId, f.DPN_ID as idFacultad, f.DPN_NOMBRE as facultad "
				+ "from her_proyecto p, her_seg_proyecto_persona pp, her_investigador_proyecto ip, her_investigador_interno ii, her_dependencia d, her_dependencia f "
				+ "where p.MOD_ID = " + mod.getId().toString() + " and pp.PRY_ID (+)= p.PRY_ID and p.EPR_ID= 'P'"
				+ "and p.PRY_ID = ip.PRY_ID(+) and ip.INV_ID = ii.INV_ID(+) and ip.TDO_ID = ii.TDO_ID(+) and ip.INP_TIPO(+) = 'P' "
				+ "and ii.DPN_ID = d.DPN_ID(+) " + "and d.DPN_FACULTAD  = f.DPN_id(+) " +
				// "and d.SED_ID = '" + dep.getSede().getId() + "' " +
				"and pp.PER_ID  =  '" + pep.getId().getDocumento() + "' " + "and pp.TDO_ID  =  '"
				+ pep.getId().getTipoDocumento() + "' " +

				"group by p.pry_id, p.pry_nombre, pp.per_id, PP.TDO_ID, f.DPN_ID, f.DPN_NOMBRE " + "order by f.DPN_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.STRING).addScalar("facultad", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinador pc = new ProyectoCoordinador();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			pc.setIdFacultad((String) name[4]);
			pc.setNombreFacultad((String) name[5]);
			result.add(pc);
		}
		return result;
	}

	public List obtenerProyectosCoordinadorxModalidad(Modalidad mod) throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();

		try {
		String query = "select p.pry_id as id, p.pry_nombre as nombre, pp.per_id as perId, PP.TDO_ID as tdoId, f.DPN_ID as idFacultad, f.DPN_NOMBRE as facultad "
				+ "from her_proyecto p, her_seg_proyecto_persona pp, her_investigador_proyecto ip, her_investigador_interno ii, her_dependencia d, her_dependencia f "
				+ "where p.MOD_ID = " + mod.getId().toString() + " and pp.PRY_ID (+)= p.PRY_ID and p.EPR_ID= 'P'"
				+ "and p.PRY_ID = ip.PRY_ID(+) and ip.INV_ID = ii.INV_ID(+) and ip.TDO_ID = ii.TDO_ID(+) and ip.INP_TIPO(+) = 'P' "
				+ "and ii.DPN_ID = d.DPN_ID(+) " + "and d.DPN_FACULTAD  = f.DPN_id(+) "
				+ "group by p.pry_id, p.pry_nombre, pp.per_id, PP.TDO_ID, f.DPN_ID, f.DPN_NOMBRE "
				+ "order by f.DPN_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.STRING).addScalar("facultad", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinador pc = new ProyectoCoordinador();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			pc.setIdFacultad((String) name[4]);
			pc.setNombreFacultad((String) name[5]);
			result.add(pc);
		}
		return result;
	}

	public List<ProyectosVistaOficiosConvocatoria> obtenerProyectosXEstadoYConvocatoriaPadreYCorte(
			Long idConvocatoriaPadre, String idEstado, Long corte) throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList<ProyectosVistaOficiosConvocatoria>();

		Session session = getSession();

		try {
		String query = "select  pry.PRY_ID as id, pry.PRY_NOMBRE as nombre, per.PER_NOMBRE1 || ' ' || nvl(per.PER_NOMBRE2,'') || ' ' || per.PER_APELLIDO1 || ' ' || nvl(per.PER_APELLIDO2, '') AS principal, fac.DPN_NOMBRE as facultad, sed.SED_NOMBRE as sede, conv.CON_TITULO as modalidad "
				+ "from her_proyecto pry, her_investigador_proyecto invpry, her_persona per, her_dependencia dep, her_dependencia fac, her_sede sed, her_investigador_interno ii, her_convocatoria conv "
				+ "where pry.PRY_ID = invpry.PRY_ID " + "and invpry.INP_TIPO = 'P' " + "and invpry.INV_ID = per.PER_ID "
				+ "and invpry.TDO_ID = per.TDO_ID " + "and ii.INV_ID = per.PER_ID " + "and ii.TDO_ID = per.TDO_ID "
				+ "and ii.DPN_ID = dep.DPN_ID " + "and dep.SED_ID = sed.SED_ID " + "and dep.DPN_FACULTAD = fac.DPN_ID "
				+ "and pry.MOD_ID = conv.CON_ID " + "and conv.CNP_ID = " + idConvocatoriaPadre + " "
				+ "and pry.EPR_ID = '" + idEstado + "' " + "and pry.CRT_ID = " + corte + " " + "order by conv.CON_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("principal", Hibernate.STRING).addScalar("facultad", Hibernate.STRING)
				.addScalar("sede", Hibernate.STRING).addScalar("modalidad", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectosVistaOficiosConvocatoria pc = new ProyectosVistaOficiosConvocatoria();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setInvestigadorPrincipal((String) name[2]);
			pc.setFacultad((String) name[3]);
			pc.setSede((String) name[4]);
			pc.setModalidad((String) name[5]);
			result.add(pc);
		}
		return result;

	}

	public List<ProyectosVistaOficiosConvocatoria> obtenerProyectosXEstadoYConvocatoriaPadre(Long idConvocatoriaPadre,
			String idEstado) throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList<ProyectosVistaOficiosConvocatoria>();

		Session session = getSession();
		try {
		String query = "select  pry.PRY_ID as id, pry.PRY_NOMBRE as nombre, per.PER_NOMBRE1 || ' ' || nvl(per.PER_NOMBRE2,'') || ' ' || per.PER_APELLIDO1 || ' ' || nvl(per.PER_APELLIDO2, '') AS principal, fac.DPN_NOMBRE as facultad, sed.SED_NOMBRE as sede, conv.CON_TITULO as modalidad "
				+ "from her_proyecto pry, her_investigador_proyecto invpry, her_persona per, her_dependencia dep, her_dependencia fac, her_sede sed, her_investigador_interno ii, her_convocatoria conv "
				+ "where pry.PRY_ID = invpry.PRY_ID " + "and invpry.INP_TIPO = 'P' " + "and invpry.INV_ID = per.PER_ID "
				+ "and invpry.TDO_ID = per.TDO_ID " + "and ii.INV_ID = per.PER_ID " + "and ii.TDO_ID = per.TDO_ID "
				+ "and ii.DPN_ID = dep.DPN_ID " + "and dep.SED_ID = sed.SED_ID " + "and dep.DPN_FACULTAD = fac.DPN_ID "
				+ "and pry.MOD_ID = conv.CON_ID " + "and conv.CNP_ID = " + idConvocatoriaPadre + " "
				+ "and pry.EPR_ID = '" + idEstado + "' " + "order by conv.CON_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("principal", Hibernate.STRING).addScalar("facultad", Hibernate.STRING)
				.addScalar("sede", Hibernate.STRING).addScalar("modalidad", Hibernate.STRING).list();

		}catch (Exception e) {
		e.printStackTrace();
		}finally {
		session.close();
		}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectosVistaOficiosConvocatoria pc = new ProyectosVistaOficiosConvocatoria();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setInvestigadorPrincipal((String) name[2]);
			pc.setFacultad((String) name[3]);
			pc.setSede((String) name[4]);
			pc.setModalidad((String) name[5]);
			result.add(pc);
		}
		return result;

	}

	public List obtenerProyectosCoordinadorxModalidadxEstado(Modalidad mod, String idEstado)
			throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();

		try {
		String query = "select p.pry_id as id, p.pry_nombre as nombre, pp.per_id as perId, PP.TDO_ID as tdoId, f.DPN_ID as idFacultad, f.DPN_NOMBRE as facultad "
				+ "from her_proyecto p, her_seg_proyecto_persona pp, her_investigador_proyecto ip, her_investigador_interno ii, her_dependencia d, her_dependencia f "
				+ "where p.MOD_ID = " + mod.getId().toString() + " and pp.PRY_ID (+)= p.PRY_ID "
				+ "and p.PRY_ID = ip.PRY_ID(+) and ip.INV_ID = ii.INV_ID(+) and ip.TDO_ID = ii.TDO_ID(+) and ip.INP_TIPO(+) = 'P' "
				+ "and ii.DPN_ID = d.DPN_ID(+) " + "and d.DPN_FACULTAD  = f.DPN_id(+) " + "and p.EPR_ID  = '" + idEstado
				+ "' group by p.pry_id, p.pry_nombre, pp.per_id, PP.TDO_ID, f.DPN_ID, f.DPN_NOMBRE "
				+ "order by f.DPN_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.STRING).addScalar("facultad", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
			}finally {
			session.close();
			}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinador pc = new ProyectoCoordinador();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			pc.setIdFacultad((String) name[4]);
			pc.setNombreFacultad((String) name[5]);
			result.add(pc);
		}
		return result;
	}

	public List<ProyectoCoordinador> obtenerProyectosxId(String idDoc, String tipoDoc, String idPro)
			throws DataAccessException {
		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();
		try {
		String query = "select p.pry_id as id, p.pry_nombre as nombre, pp.per_id as perId, PP.TDO_ID as tdoId, f.DPN_ID as idFacultad, f.DPN_NOMBRE as facultad, pp.SEG_ID as idpc "
				+ "from her_proyecto p, her_seg_proyecto_persona pp, her_investigador_proyecto ip, her_investigador_interno ii, her_dependencia d, her_dependencia f "
				+ "where p.pry_id = " + idPro + " and pp.PRY_ID (+)= p.PRY_ID "
				+ "and p.PRY_ID = ip.PRY_ID(+) and ip.INV_ID = ii.INV_ID(+) and ip.TDO_ID = ii.TDO_ID(+) and ip.INP_TIPO(+) = 'P' "
				+ "and ii.DPN_ID = d.DPN_ID(+) " + "and d.DPN_FACULTAD  = f.DPN_id(+) "
				+ "group by p.pry_id, p.pry_nombre, pp.per_id, PP.TDO_ID, f.DPN_ID, f.DPN_NOMBRE, pp.SEG_ID "
				+ "order by f.DPN_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.STRING).addScalar("facultad", Hibernate.STRING)
				.addScalar("idpc", Hibernate.LONG).list();

		}catch (Exception e) {
		e.printStackTrace();
		}finally {
		session.close();
		}

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinador pc = new ProyectoCoordinador();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			pc.setIdFacultad((String) name[4]);
			pc.setNombreFacultad((String) name[5]);
			pc.setId((Long) name[6]);
			result.add(pc);
		}
		return result;
	}

	public List obtenerProyectoXId(String id) throws DataAccessException {

		List proyectos = new ArrayList();
		List result = new ArrayList();
		String resultado = "en minuscula";
		System.out.println(resultado.toUpperCase());

		Session session = getSession();
		try {
		String query = "select p.pry_id as id,p.pry_nombre as nombre,spp.PER_ID as perId,d.dpn_id as idFacultad,d.dpn_nombre as facultad "
				+ "from her_proyecto p,her_proyecto_dependencia pd,her_dependencia d,her_investigador_proyecto ini,her_seg_proyecto_persona spp "
				+ "where p.pry_id = pd.pry_id and " + "pd.dpn_id = d.dpn_id and " + "spp.PRY_ID = p.PRY_ID and "
				+ "p.PRY_ID = '" + id + "'" + " "
				+ "group by p.pry_id, p.pry_nombre, spp.PER_ID, d.dpn_id, d.dpn_nombre " + "order by d.dpn_id";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("idFacultad", Hibernate.STRING)
				.addScalar("facultad", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();
		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinador pc = new ProyectoCoordinador();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setIdFacultad((String) name[3]);
			pc.setNombreFacultad((String) name[4]);
			result.add(pc);
		}
		return result;

	}

	public List obtenerProyectosXEvaluador(IdPersona id) throws DataAccessException {
		// obtiene los proyectos con evaluaciones finalizadas
		List proyectos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();

		try {
		String query = "select p.pry_id as idProyecto " + ",p.PRY_NOMBRE as nombreProyecto "
				+ ",cp.CNP_TITULO as nombreConvocatoria " + ",cp.DNP_ID as dependenciaConvocatoria "
				+ ",pe.PRE_FECHA as fechaEvaluacion " + ",pe.PRE_ID as idEvaluacion "
				+ "from her_proyecto_evaluador pe " + "inner join her_proyecto p on p.PRY_ID = pe.PRY_ID "
				+ "inner join her_convocatoria c on c.con_id = p.mod_id  "
				+ "inner join her_convocatoria_padre cp on cp.CNP_ID = c.CNP_ID  " + "where pe.INV_ID = '"
				+ id.getDocumento() + "' " + "and pe.TDO_ID = '" + id.getTipoDocumento() + "' "
				+ "and pe.PRE_ESTADO_EVALUACION = 'S' " + "order by pe.pre_fecha desc";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("idProyecto", Hibernate.STRING).addScalar("nombreProyecto", Hibernate.STRING)
				.addScalar("nombreConvocatoria", Hibernate.STRING)
				.addScalar("dependenciaConvocatoria", Hibernate.STRING).addScalar("fechaEvaluacion", Hibernate.DATE)
				.addScalar("idEvaluacion", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();
		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoEvaluadorVistaCert pe = new ProyectoEvaluadorVistaCert();
			pe.setIdProyecto((String) name[0]);
			pe.setNombreProyecto((String) name[1]);
			pe.setInvId(id.getDocumento());
			pe.setInvTpDoc(id.getTipoDocumento());
			pe.setNombreConvocatoria((String) name[2]);
			pe.setDependenciaConvocatoria((String) name[3]);
			pe.setFechaEvaluacion((Date) name[4]);
			pe.setIdEvaluacion((String) name[5]);
			result.add(pe);
		}
		return result;

	}

	public List obtenerProyectoXCordinador(IdPersona id) throws DataAccessException {

		List proyectos = new ArrayList();
		List result = new ArrayList();
		String resultado = "en minuscula";
		System.out.println(resultado.toUpperCase());

		Session session = getSession();
		try {
		String query = "select p.pry_id as id, p.pry_nombre as nombre, spp.PER_ID as perId, spp.TDO_ID as tdoId "
				+ "from her_proyecto p, her_seg_proyecto_persona spp " + "where  spp.PRY_ID = p.PRY_ID and "
				+ "spp.PER_ID = '" + id.getDocumento() + "'" + " and " + "spp.TDO_ID = '" + id.getTipoDocumento() + "'"
				+ " " + "order by p.pry_id";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = proyectos.iterator();
		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinador pc = new ProyectoCoordinador();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			result.add(pc);
		}
		return result;

	}

	/**
	 * Se utiliza para hacer la modificacion en la tabla
	 * HER_SEG_PROYEXTO_PERSONA con el parametro tipo: 1: inserción 2:
	 * actualización 3: eliminación
	 * 
	 * De los campos campos de la tabla proyecto y persona
	 */
	public void modificarCoordinadorEnProyecto(int tipo, String idProyecto, IdPersona idPersonaCoordinador,
			Persona asesor, String tipoCoordinador, boolean guardarLog) throws DataAccessException {

		Session session = getSession();

		try {
		String insert = "insert into HER_SEG_PROYECTO_PERSONA values (" + idProyecto + ", '"
				+ idPersonaCoordinador.getDocumento() + "', '" + idPersonaCoordinador.getTipoDocumento()
				+ "','','','','' )";
		String update = "update HER_SEG_PROYECTO_PERSONA spp set spp.PER_ID = '" + idPersonaCoordinador.getDocumento()
				+ "', spp.TDO_ID = '" + idPersonaCoordinador.getTipoDocumento() + "' where spp.PRY_ID = " + idProyecto;
		String delete = "delete from HER_SEG_PROYECTO_PERSONA spp where spp.PRY_ID = " + idProyecto
				+ " and spp.TDO_ID = '" + idPersonaCoordinador.getTipoDocumento() + "' and spp.PER_ID = '"
				+ idPersonaCoordinador.getDocumento() + "'";
		Persona coordinador = new Persona();
		coordinador.setId(idPersonaCoordinador);
		Proyecto proyecto = new Proyecto();
		proyecto.setId(Long.parseLong(idProyecto));
		HistoricoAsignacionProyecto historicoAsignacionProyecto = new HistoricoAsignacionProyecto(proyecto, asesor,
				coordinador, tipoCoordinador);
		try {
			Statement st = session.connection().createStatement();

			switch (tipo) {
			case INSERT:
				st.execute(insert);
				break;
			case UPDATE:
				st.execute(update);
				break;
			case DELETE:
				st.execute(delete);
				historicoAsignacionProyecto.setCoordinador(null);
				break;
			}
			if (guardarLog) {
				getHibernateTemplate().saveOrUpdate(historicoAsignacionProyecto);
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

	}

	/**
	 * ING. LILIANA OLARTE Se utiliza para hacer la modificacion en la tabla
	 * HER_SEG_PROYEXTO_PERSONA con el parametro tipoModificacion: 1: inserción
	 * 2: actualización 3: eliminación y el parametro tipoPersona: R:Revision,
	 * E:Evaluacion, S:Seguimiento De los campos de la tabla proyecto y persona
	 */
	public void modificarCoordinadorEnProyecto(int tipoModificacion, String tipoPersona, String idProyecto,
			IdPersona idPersonaCoordinador, Persona asesor, String tipoCoordinador, boolean guardarLog)
			throws DataAccessException {

		Session session = getSession();
		String perSeguimiento = "41654907";
		String perSegTdo = "C";
		String insert = "", update = "", delete = "";
		Persona coordinador = new Persona();
		coordinador.setId(idPersonaCoordinador);
		Proyecto proyecto = new Proyecto();
		proyecto.setId(Long.parseLong(idProyecto));
		HistoricoAsignacionProyecto historicoAsignacionProyecto = new HistoricoAsignacionProyecto(proyecto, asesor,
				coordinador, tipoCoordinador);
		if (tipoPersona.equals("R")) {
			insert = "insert into HER_SEG_PROYECTO_PERSONA (seg_id, PRY_ID, PER_ID, TDO_ID, PER_REVISION, TDO_ID2) values ("
					+ "SEQ_SEG_PRY_PERSONA.nextval," + idProyecto + ", '" + perSeguimiento + "', '" + perSegTdo + "', '"
					+ idPersonaCoordinador.getDocumento() + "', '" + idPersonaCoordinador.getTipoDocumento() + "' )";
			update = "update HER_SEG_PROYECTO_PERSONA spp set spp.PER_REVISION = '"
					+ idPersonaCoordinador.getDocumento() + "', spp.TDO_ID2 = '"
					+ idPersonaCoordinador.getTipoDocumento() + "' where spp.PRY_ID = " + idProyecto;
			delete = "update HER_SEG_PROYECTO_PERSONA spp set spp.PER_REVISION = '', "
					+ "spp.TDO_ID2 = '' where spp.PRY_ID = " + idProyecto;
		}

		if (tipoPersona.equals("E")) {

			insert = "insert into HER_SEG_PROYECTO_PERSONA (SEG_ID, PRY_ID, PER_ID, TDO_ID, PER_EVALUACION, TDO_ID3) "
					+ "values (SEQ_SEG_PRY_PERSONA.nextval," + idProyecto + ", '" + perSeguimiento + "', '" + perSegTdo
					+ "', '" + idPersonaCoordinador.getDocumento() + "', '" + idPersonaCoordinador.getTipoDocumento()
					+ "' )";
			update = "update HER_SEG_PROYECTO_PERSONA spp set spp.PER_EVALUACION = '"
					+ idPersonaCoordinador.getDocumento() + "', spp.TDO_ID3 = '"
					+ idPersonaCoordinador.getTipoDocumento() + "' where spp.PRY_ID = " + idProyecto;

			delete = "update HER_SEG_PROYECTO_PERSONA spp set spp.PER_EVALUACION = '', spp.TDO_ID3 = '' "
					+ "where spp.PRY_ID = " + idProyecto;
		}

		if (tipoPersona.equals("S")) {
			insert = "insert into HER_SEG_PROYECTO_PERSONA (seg_id, PRY_ID, PER_ID, TDO_ID)  values ("
					+ "SEQ_SEG_PRY_PERSONA.nextval," + idProyecto + ", '" + idPersonaCoordinador.getDocumento() + "', '"
					+ idPersonaCoordinador.getTipoDocumento() + "' )";
			update = "update HER_SEG_PROYECTO_PERSONA spp set spp.PER_ID = '" + idPersonaCoordinador.getDocumento()
					+ "', spp.TDO_ID = '" + idPersonaCoordinador.getTipoDocumento() + "' where spp.PRY_ID = "
					+ idProyecto;
			delete = "update HER_SEG_PROYECTO_PERSONA spp set spp.PER_ID = '" + ProyectoCoordinador.COORDINADOR_PRUEBAS
					+ "', spp.TDO_ID = 'C' where spp.PRY_ID = " + idProyecto;
		}

		try {
			Statement st = session.connection().createStatement();

			switch (tipoModificacion) {
			case INSERT:
				st.execute(insert);
				break;
			case UPDATE:
				st.execute(update);
				break;
			case DELETE:
				st.execute(delete);
				historicoAsignacionProyecto.setCoordinador(null);
				break;
			}
			if (guardarLog) {
				getHibernateTemplate().saveOrUpdate(historicoAsignacionProyecto);
			}
			coordinador.setId(idPersonaCoordinador);
			proyecto.setId(Long.parseLong(idProyecto));

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			session.close();
		}

	}
	
	public void modificarCoordinadorEnProyectoEditorial(int tipoModificacion, String tipoPersona, String idProyecto,
			IdPersona idPersonaCoordinador, Persona asesor, String tipoCoordinador, boolean guardarLog)
			throws DataAccessException {

		Session session = getSession();
		String perSeguimiento = "41654907";
		String perSegTdo = "C";
		String insert = "", update = "", delete = "";
		Persona coordinador = new Persona();
		coordinador.setId(idPersonaCoordinador);
		Proyecto proyecto = new Proyecto();
		proyecto.setId(Long.parseLong(idProyecto));
		/*HistoricoAsignacionProyecto historicoAsignacionProyecto = new HistoricoAsignacionProyecto(proyecto, asesor,
				coordinador, tipoCoordinador);*/
		if (tipoPersona.equals("R")) {
			insert = "insert into HER_PROYECTO_COORDINADOR_ED (pce_id, PRY_ID, PER_ID_SEG, TDO_ID_SEG, PER_ID_REQ, TDO_ID_REQ) values ("
					+ "SEQ_PROYECTO_COORDINADOR_ED.nextval," + idProyecto + ", '" + perSeguimiento + "', '" + perSegTdo + "', '"
					+ idPersonaCoordinador.getDocumento() + "', '" + idPersonaCoordinador.getTipoDocumento() + "' )";
			update = "update HER_PROYECTO_COORDINADOR_ED spp set spp.PER_ID_REQ = '"
					+ idPersonaCoordinador.getDocumento() + "', spp.TDO_ID_REQ = '"
					+ idPersonaCoordinador.getTipoDocumento() + "' where spp.PRY_ID = " + idProyecto;
			delete = "update HER_PROYECTO_COORDINADOR_ED spp set spp.PER_ID_REQ = '', "
					+ "spp.TDO_ID_REQ = '' where spp.PRY_ID = " + idProyecto;
		}

		if (tipoPersona.equals("E")) {

			insert = "insert into HER_PROYECTO_COORDINADOR_ED (pce_ID, PRY_ID, PER_ID_SEG, TDO_ID_SEG, PER_ID_EV, TDO_ID_EV) "
					+ "values (SEQ_PROYECTO_COORDINADOR_ED.nextval," + idProyecto + ", '" + perSeguimiento + "', '" + perSegTdo
					+ "', '" + idPersonaCoordinador.getDocumento() + "', '" + idPersonaCoordinador.getTipoDocumento()
					+ "' )";
			update = "update HER_PROYECTO_COORDINADOR_ED spp set spp.PER_ID_EV = '"
					+ idPersonaCoordinador.getDocumento() + "', spp.TDO_ID_EV = '"
					+ idPersonaCoordinador.getTipoDocumento() + "' where spp.PRY_ID = " + idProyecto;

			delete = "update HER_PROYECTO_COORDINADOR_ED spp set spp.PER_ID_EV = '', spp.TDO_ID_EV = '' "
					+ "where spp.PRY_ID = " + idProyecto;
		}
		
		if (tipoPersona.equals("C")) {

			insert = "insert into HER_PROYECTO_COORDINADOR_ED (pce_ID, PRY_ID, PER_ID_SEG, TDO_ID_SEG, PER_ID_CED, TDO_ID_CED) "
					+ "values (SEQ_PROYECTO_COORDINADOR_ED.nextval," + idProyecto + ", '" + perSeguimiento + "', '" + perSegTdo
					+ "', '" + idPersonaCoordinador.getDocumento() + "', '" + idPersonaCoordinador.getTipoDocumento()
					+ "' )";
			update = "update HER_PROYECTO_COORDINADOR_ED spp set spp.PER_ID_CED = '"
					+ idPersonaCoordinador.getDocumento() + "', spp.TDO_ID_CED = '"
					+ idPersonaCoordinador.getTipoDocumento() + "' where spp.PRY_ID = " + idProyecto;

			delete = "update HER_PROYECTO_COORDINADOR_ED spp set spp.PER_ID_CED = '', spp.TDO_ID_CED = '' "
					+ "where spp.PRY_ID = " + idProyecto;
		}

		if (tipoPersona.equals("S")) {
			insert = "insert into HER_PROYECTO_COORDINADOR_ED (pce_id, PRY_ID, PER_ID_SEG, TDO_ID_SEG)  values ("
					+ "SEQ_PROYECTO_COORDINADOR_ED.nextval," + idProyecto + ", '" + idPersonaCoordinador.getDocumento() + "', '"
					+ idPersonaCoordinador.getTipoDocumento() + "' )";
			update = "update HER_PROYECTO_COORDINADOR_ED spp set spp.PER_ID_SEG = '" + idPersonaCoordinador.getDocumento()
					+ "', spp.TDO_ID_SEG = '" + idPersonaCoordinador.getTipoDocumento() + "' where spp.PRY_ID = "
					+ idProyecto;
			delete = "update HER_PROYECTO_COORDINADOR_ED spp set spp.PER_ID_SEG = '" + ProyectoCoordinador.COORDINADOR_PRUEBAS
					+ "', spp.TDO_ID_SEG = 'C' where spp.PRY_ID = " + idProyecto;
		}

		try {
			Statement st = session.connection().createStatement();

			switch (tipoModificacion) {
			case INSERT:
				st.execute(insert);
				break;
			case UPDATE:
				st.execute(update);
				break;
			case DELETE:
				st.execute(delete);
				//historicoAsignacionProyecto.setCoordinador(null);
				break;
			}
			if (guardarLog) {
				//getHibernateTemplate().saveOrUpdate(historicoAsignacionProyecto);
			}
			coordinador.setId(idPersonaCoordinador);
			proyecto.setId(Long.parseLong(idProyecto));

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

		session.close();
		}

	}

	/**
	 * ING LILIANA OLARTE Se utiliza para hacer la modificacion en la tabla
	 * HER_SEG_PROYEXTO_PERSONA con el parametro tipo: 1: inserción 2:
	 * actualización 3: eliminación
	 * 
	 * De los campos campos de la tabla proyecto y persona
	 * 
	 * FALTA TERMINAR
	 */
	public void modificarCoordinadoresEnProyecto(int tipo, String idProyecto, IdPersona idPersona,
			IdPersona idPersonaRev, IdPersona idPersonaEval) throws DataAccessException {

		Session session = getSession();

		String insert = "insert into HER_SEG_PROYECTO_PERSONA (PRY_ID, PER_ID, TDO_ID, PER_REVISION, TDO_ID2, PER_EVALUACION, TDO_ID3)values ("
				+ idProyecto + ", '" + idPersona.getDocumento() + "', '" + idPersona.getTipoDocumento()
				+ "','','','','' )";
		String update = "update HER_SEG_PROYECTO_PERSONA spp set spp.PER_REVISION = '" + idPersona.getDocumento()
				+ "', spp.TDO_ID = '" + idPersona.getTipoDocumento() + "' where spp.PRY_ID = " + idProyecto;
		String delete = "delete from HER_SEG_PROYECTO_PERSONA spp where spp.PRY_ID = " + idProyecto
				+ " and spp.TDO_ID = '" + idPersona.getTipoDocumento() + "' and spp.PER_ID = '"
				+ idPersona.getDocumento() + "'";

		try {
			Statement st = session.connection().createStatement();

			switch (tipo) {
			case INSERT:
				st.execute(insert);
				break;
			case UPDATE:
				System.out.println(update);
				st.execute(update);
				break;
			case DELETE:
				System.out.println(delete);
				st.execute(delete);
				break;
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

		session.close();
		}

	}

	public Proyecto obtenerResumenProyecto(Long id) throws DataAccessException {
		Session session = getSession();
		Proyecto p = new Proyecto();
		try {
		p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("convocatoria", FetchMode.JOIN)
				.setFetchMode("grupos", FetchMode.JOIN).setFetchMode("modalidad", FetchMode.JOIN)
				.setFetchMode("investigadoresProyecto", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();
		p.setEsInvestigacion(true);
		p.setEsExtension(false);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return p;
	}

	public Proyecto obtenerResumenProyectoExtension(Long id) throws DataAccessException {
		List<Object> proyectoExtension = null;
		Proyecto p = null;
		Session session = getSession();
		try {
			

			String query = "select p.id_proyecto as idProyecto, p.nombre as nombreProyecto, p.uab as uab, "
					+ "p.sede as sede, p.estado as estado, p.facultad as facultad, p.objeto as objeto, p.responsable as responsable,"
					+ "p.email as email, p.duracion as duracion, p.modalidad as modalidad, p.sub_modalidad as submodalidad "
					+ "from proyectos_ext@extension p where p.id_proyecto = '" + id + "'";

			SQLQuery sqlQuery = session.createSQLQuery(query);

			proyectoExtension = sqlQuery.addScalar("idProyecto", Hibernate.LONG)
					.addScalar("nombreProyecto", Hibernate.STRING).addScalar("uab", Hibernate.STRING)
					.addScalar("sede", Hibernate.STRING).addScalar("estado", Hibernate.STRING)
					.addScalar("facultad", Hibernate.STRING).addScalar("responsable", Hibernate.STRING)
					.addScalar("objeto", Hibernate.STRING).addScalar("email", Hibernate.STRING)
					.addScalar("duracion", Hibernate.STRING).addScalar("modalidad", Hibernate.STRING)
					.addScalar("submodalidad", Hibernate.STRING).list();

			session.close();

			Iterator<Object> it = proyectoExtension.iterator();

			while (it.hasNext()) {
				Object[] name = (Object[]) it.next();
				p = new Proyecto();
				Dependencia dependencia = new Dependencia();
				Dependencia dependencia2 = new Dependencia();
				Sede sede = new Sede();
				EstadoProyecto estado = new EstadoProyecto();
				Investigador responsable = new Investigador();

				p.setId((Long) name[0]);
				p.setNombre((String) name[1]);
				dependencia.setNombre((String) name[2]);
				sede.setNombre((String) name[3]);
				estado.setNombre((String) name[4]);
				dependencia2.setNombre((String) name[5]);
				dependencia.setSede(sede);
				dependencia.setFacultad(dependencia2);
				p.setDependenciaPrincipal(dependencia);
				p.setEstadoProyecto(estado);
				p.setEsExtension(true);
				p.setEsInvestigacion(false);
				p.setResumen((String) name[7]);
				p.setObservaciones((String) name[10]);
				p.setAntecedentes((String) name[11]);
				responsable.setNombre11((String) name[6]);
				responsable.setEmail((String) name[8]);
				p.setResponsable(responsable);
				p.setTipoEspacio((String) name[9]);

			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return p;
	}

	public List<HistoricoEstadoProyecto> obtenerHistoricosEstadoProyecto(Long id) throws DataAccessException {

		List historicos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();
		try {
		String query = "select distinct hep.hep_id as id, hep.epr_id idEstado, " + "hep.hep_fecha fecha,"
				+ " hep.hep_justificacion justificacion,"
				+ " hep.hep_responsable responsable, ep.epr_nombre nombreEstado,"
				+ " per.per_nombre1, per.per_nombre2, per.per_apellido1, per.per_apellido2 "
				+ " from her_historico_estado_proyecto hep left join her_persona per"
				+ " on per.per_id = hep.hep_responsable " + " and per.tdo_id = hep.her_tdo_id, "
				+ " her_estado_proyecto ep " + "where hep.pry_id = '" + id + "' and "
				+ "ep.epr_id = hep.epr_id order by hep.hep_fecha";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		historicos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("idEstado", Hibernate.STRING)
				.addScalar("fecha", Hibernate.DATE).addScalar("justificacion", Hibernate.STRING)
				.addScalar("responsable", Hibernate.STRING).addScalar("nombreEstado", Hibernate.STRING)
				.addScalar("per_nombre1", Hibernate.STRING).addScalar("per_nombre2", Hibernate.STRING)
				.addScalar("per_apellido1", Hibernate.STRING).addScalar("per_apellido2", Hibernate.STRING).list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = historicos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			HistoricoEstadoProyecto hep = new HistoricoEstadoProyecto();
			hep.setId((Long) name[0]);
			Proyecto proyecto = new Proyecto();
			proyecto.setId(id);
			hep.setProyecto(proyecto);
			EstadoProyecto ep = new EstadoProyecto();
			ep.setId((String) name[1]);
			ep.setNombre((String) name[5]);
			hep.setEstadoProyecto(ep);
			hep.setFecha((Date) name[2]);
			hep.setJustificacion((String) name[3]);
			Persona persona = new Persona();
			persona.setNombre1((String) name[6]);
			persona.setNombre2((String) name[7]);
			persona.setApellido1((String) name[8]);
			persona.setApellido2((String) name[9]);
			hep.setResponsable(persona);
			result.add(hep);
		}
		return result;
	}

	public List<HistoricoAsignacionProyecto> obtenerHistoricosAsignacionProyecto(Long pryId)
			throws DataAccessException {
		return getHibernateTemplate().find(
				"from HistoricoAsignacionProyecto hap " + "where hap.proyecto.id = '" + pryId + "' order by hap.fecha");
	}

	public List<HistoricoEstadoInforme> obtenerHistoricosEstadoInforme(Long informeId) throws DataAccessException {
		return getHibernateTemplate().find("from HistoricoEstadoInforme hei " + "where hei.proyectoInforme.id = '"
				+ informeId + "' order by hei.fecha");
	}

	public List<HistoricoHabilitacionEdicionProyecto> obtenerHistoricosHabilitacionEdicionProyecto(Long pryId)
			throws DataAccessException {
		return getHibernateTemplate().find("from HistoricoHabilitacionEdicionProyecto hhp "
				+ "where hhp.proyecto.id = '" + pryId + "' order by hhp.fecha");
	}

	public List<HistoricoEstadoLegalizacion> obtenerHistoricosEstadosLegalizacion(Long pryId)
			throws DataAccessException {
		return getHibernateTemplate().find(
				"from HistoricoEstadoLegalizacion hl " + "where hl.proyecto.id = '" + pryId + "' order by hl.fecha");
	}

	public List<HistoricoEstadoAval> obtenerHistoricosEstadoAval(Long id) throws DataAccessException {

		List historicos = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();
		try {
		String query = "select distinct hea.hea_id as id, hea.avi_id as idAval, hea.avi_estado as estado, "
				+ "hea.hea_fecha as fecha, hea.avi_avalfacultad as aviFacultad,"
				+ " hea.avi_avaldireccion as avalDireccion," + " hea.avi_avalvice as avalVice,"
				+ " hea.hea_justificacion as justificacion," + " hea.tdo_id as tipoDoc, hea.per_id as docPersona,"
				+ " per.per_nombre1, per.per_nombre2, per.per_apellido1, per.per_apellido2, a.avi_tipo as tipo, a.dpn_id as dependencia, a.avi_dpn_contrapartida as dpnContrapartida, hea.avi_avaluab as avalUab,"
				+ " hea.avi_avalDRE as avalDRE, hea.avi_avalRectoria as avalRectoria"
				+ " from her_historico_estado_aval hea left join her_persona per"
				+ " on per.per_id = hea.per_id and per.tdo_id = hea.tdo_id left join her_aval a on a.avi_id = hea.avi_id "
				+ "where hea.avi_id = '" + id + "'" + "order by hea.hea_fecha asc";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		historicos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("idAval", Hibernate.LONG)
				.addScalar("estado", Hibernate.STRING).addScalar("fecha", Hibernate.TIMESTAMP)
				.addScalar("aviFacultad", Hibernate.STRING).addScalar("avalDireccion", Hibernate.STRING)
				.addScalar("avalVice", Hibernate.STRING).addScalar("justificacion", Hibernate.STRING)
				.addScalar("tipoDoc", Hibernate.STRING).addScalar("docPersona", Hibernate.STRING)
				.addScalar("per_nombre1", Hibernate.STRING).addScalar("per_nombre2", Hibernate.STRING)
				.addScalar("per_apellido1", Hibernate.STRING).addScalar("per_apellido2", Hibernate.STRING)
				.addScalar("tipo", Hibernate.STRING).addScalar("dependencia", Hibernate.STRING)
				.addScalar("dpnContrapartida", Hibernate.STRING).addScalar("avalUab", Hibernate.STRING)
				.addScalar("avalDRE", Hibernate.STRING).addScalar("avalRectoria", Hibernate.STRING).list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = historicos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();

			Aval aval = new Aval();
			aval.setAviId((Long) name[1]);
			aval.setTipo((String) name[14]);
			Dependencia dep = new Dependencia((String) name[15]);
			aval.setDependencia(dep);
			aval.setDependenciaContrapartida((String) name[16]);

			HistoricoEstadoAval hea = new HistoricoEstadoAval();
			hea.setId((Long) name[0]);
			hea.setAval(aval);
			hea.setEstadoAval((String) name[2]);
			hea.setFecha((Date) name[3]);
			hea.setAvalFacultad((String) name[4]);
			hea.setAvalDireccion((String) name[5]);
			hea.setAvalVicerrectoria((String) name[6]);
			hea.setJustificacion((String) name[7]);
			hea.setAvalUab((String) name[17]);
			
			hea.setAvalDRE((String) name[18]);
			hea.setAvalRectoria((String) name[19]);

			IdPersona idPersona = new IdPersona();
			idPersona.setTipoDocumento((String) name[8]);
			idPersona.setDocumento((String) name[9]);

			Persona persona = new Persona();
			persona.setId(idPersona);
			persona.setNombre1((String) name[10]);
			persona.setNombre2((String) name[11]);
			persona.setApellido1((String) name[12]);
			persona.setApellido2((String) name[13]);
			hea.setRevisor(persona);
			result.add(hea);
		}
		return result;
	}

	public List obtenerTiposInvestigacion() throws DataAccessException {
		return getHibernateTemplate().find("from TipoInvestigacion");
	}

	public List<ObjetivoEspecifico> obtenerObjetivosEspeficicos(Proyecto pry) throws DataAccessException {
		Session session = getSession();
		List result = new ArrayList();
		try {
		String queryString = "from ObjetivoEspecifico where ( id.proyecto = :proyecto )";
		result = session.createQuery(queryString).setParameter("proyecto", pry, Hibernate.entity(Proyecto.class))
				.list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return result;
	}

	public void guardarProyecto(Proyecto proyecto) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(proyecto);
	}

	public void guardarActividad(Actividad actividad) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(actividad);
	}

	public List obtenerFuentesFinanciacionInternas() throws DataAccessException {
		return getHibernateTemplate().find("from FuenteFinanciacion where internaExterna = 'I' order by descripcion");
	}

	public List obtenerFuentesFinanciacionExternas() throws DataAccessException {
		return getHibernateTemplate().find("from FuenteFinanciacion where internaExterna = 'E' order by descripcion");
	}

	public Archivo obtenerArchivo(Long id) throws DataAccessException {
		Session session = getSession();
		Archivo a = null;
		try {
		a = (Archivo) session.createCriteria(Archivo.class).add(Restrictions.idEq(id)).uniqueResult();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return a;
	}

	public ArchivoRequerimiento obtenerArchivoRequerimiento(Long id) throws DataAccessException {
		Session session = getSession();
		ArchivoRequerimiento a = null;
		try {
		a = (ArchivoRequerimiento) session.createCriteria(ArchivoRequerimiento.class)
				.add(Restrictions.idEq(id)).uniqueResult();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return a;
	}

	public List<ArchivoRequerimiento> obtenerArchivosRequerimientosLista(Long id) throws DataAccessException {
		List resumenArchivo = new ArrayList();

		Session session = getSession();
		String query = "SELECT ARE_ID as id, ARE_NOMBRE as nombre, ARE_TIPO_ARCHIVO as tipo"
				+ " FROM HER_ARCHIVO_REQUERIMIENTO " + " WHERE REQ_ID = " + id + " order by ARE_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		List archivosConsulta = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING).list();

		session.close();

		Iterator it = archivosConsulta.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ArchivoRequerimiento arcR = new ArchivoRequerimiento();
			arcR.setId((Long) name[0]);
			arcR.setNombre((String) name[1]);
			resumenArchivo.add(arcR);
		}
		return resumenArchivo;
	}

	public List<Archivo> obtenerNombresArchivosReclamacion(Proyecto proyecto) {
		List resumenArchivo = new ArrayList();

		Session session = getSession();
		String query = "SELECT ARC_ID as id, ARC_NOMBRE as nombre, "
				+ "per_nombre1, per_nombre2, per_apellido1, per_apellido2, arc_fecha "
				+ " FROM HER_ARCHIVO LEFT JOIN HER_PERSONA PER ON "
				+ " PER.PER_ID = ARC_RESPONSABLE AND PER.TDO_ID = ARC_TDO_ID" + " WHERE PRY_ID = " + proyecto.getId()
				+ " AND TIA_ID = 87 order by ARC_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		List archivosConsulta = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("per_nombre1", Hibernate.STRING).addScalar("per_nombre2", Hibernate.STRING)
				.addScalar("per_apellido1", Hibernate.STRING).addScalar("per_apellido2", Hibernate.STRING)
				.addScalar("arc_fecha", Hibernate.DATE).list();

		session.close();

		Iterator it = archivosConsulta.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Archivo arcR = new Archivo();
			arcR.setId((Long) name[0]);
			arcR.setNombre((String) name[1]);
			Persona persona = new Persona();
			persona.setNombre1((String) name[2]);
			persona.setNombre2((String) name[3]);
			persona.setApellido1((String) name[4]);
			persona.setApellido2((String) name[5]);
			arcR.setResponsable(persona);
			arcR.setFecha((Date) name[6]);
			resumenArchivo.add(arcR);
		}
		return resumenArchivo;
	}

	public List<Archivo> obtenerNombresArchivos(Proyecto proyecto) {
		List resumenArchivo = new ArrayList();

		Session session = getSession();
		String query = "SELECT ARC_ID as id, ARC_NOMBRE as nombre, "
				+ "per_nombre1, per_nombre2, per_apellido1, per_apellido2, arc_fecha "
				+ " FROM HER_ARCHIVO LEFT JOIN HER_PERSONA PER ON "
				+ " PER.PER_ID = ARC_RESPONSABLE AND PER.TDO_ID = ARC_TDO_ID" + " WHERE PRY_ID = " + proyecto.getId()
				+ " order by ARC_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		List archivosConsulta = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("per_nombre1", Hibernate.STRING).addScalar("per_nombre2", Hibernate.STRING)
				.addScalar("per_apellido1", Hibernate.STRING).addScalar("per_apellido2", Hibernate.STRING)
				.addScalar("arc_fecha", Hibernate.DATE).list();

		session.close();

		Iterator it = archivosConsulta.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ArchivoResumen arcR = new ArchivoResumen();
			arcR.setId((Long) name[0]);
			arcR.setNombre((String) name[1]);
			Persona persona = new Persona();
			persona.setNombre1((String) name[2]);
			persona.setNombre2((String) name[3]);
			persona.setApellido1((String) name[4]);
			persona.setApellido2((String) name[5]);
			arcR.setResponsable(persona);
			arcR.setFecha((Date) name[6]);
			resumenArchivo.add(arcR);
		}
		return resumenArchivo;
	}

	@Deprecated
	public List obtenerNombresArchivosTipos(Proyecto proyecto) throws DataAccessException {

		List resumenArchivo = new ArrayList();

		Session session = getSession();

		SQLQuery sqlQuery1 = session.createSQLQuery(
				"SELECT ARC_NOMBRE as nombre FROM HER_ARCHIVO WHERE PRY_ID = " + proyecto.getId() + " order by ARC_ID");
		SQLQuery sqlQuery2 = session.createSQLQuery(
				"SELECT ARC_ID as id FROM HER_ARCHIVO WHERE PRY_ID = " + proyecto.getId() + " order by ARC_ID");
		SQLQuery sqlQuery3 = session.createSQLQuery(
				"SELECT TIA_ID as tipo FROM HER_ARCHIVO WHERE PRY_ID = " + proyecto.getId() + " order by ARC_ID");

		List nombres = sqlQuery1.addScalar("nombre", Hibernate.STRING).list();
		List ids = sqlQuery2.addScalar("id", Hibernate.LONG).list();
		List tipos = sqlQuery3.addScalar("tipo", Hibernate.LONG).list();

		session.close();

		Iterator it = nombres.iterator();
		Iterator it2 = ids.iterator();
		Iterator it3 = tipos.iterator();
		while (it.hasNext() && it2.hasNext() && it3.hasNext()) {
			Object name = it.next();
			Object id = it2.next();
			ArchivoResumen arcR = new ArchivoResumen();
			arcR.setNombre((String) name);
			arcR.setId((Long) id);

			resumenArchivo.add(arcR);
		}
		return resumenArchivo;
	}

	public List<Archivo> obtenerNombresArchivosConTipos(Proyecto proyecto) throws DataAccessException {
		List resumenArchivo = new ArrayList();

		Session session = getSession();

		SQLQuery sqlQuery1 = session.createSQLQuery(
				"SELECT ARC_NOMBRE as nombre FROM HER_ARCHIVO WHERE PRY_ID = " + proyecto.getId() + " order by ARC_ID");
		SQLQuery sqlQuery2 = session.createSQLQuery(
				"SELECT ARC_ID as id FROM HER_ARCHIVO WHERE PRY_ID = " + proyecto.getId() + " order by ARC_ID");
		SQLQuery sqlQuery3 = session.createSQLQuery(
				"SELECT TIA_ID as tipo FROM HER_ARCHIVO WHERE PRY_ID = " + proyecto.getId() + " order by ARC_ID");
		SQLQuery sqlQuery4 = session.createSQLQuery(
				"SELECT TARC.TIA_NOMBRE as tipoArchivo FROM HER_TIPO_ARCHIVO TARC, HER_ARCHIVO ARC WHERE TARC.TIA_ID = ARC.TIA_ID AND ARC.PRY_ID = "
						+ proyecto.getId() + " order by ARC.TIA_ID");

		List nombres = sqlQuery1.addScalar("nombre", Hibernate.STRING).list();
		List ids = sqlQuery2.addScalar("id", Hibernate.LONG).list();
		List tipos = sqlQuery3.addScalar("tipo", Hibernate.SHORT).list();
		List tipoArchivo = sqlQuery4.addScalar("tipoArchivo", Hibernate.STRING).list();

		session.close();

		Iterator it = nombres.iterator();
		Iterator it2 = ids.iterator();
		Iterator it3 = tipos.iterator();
		Iterator it4 = tipoArchivo.iterator();
		while (it.hasNext() && it2.hasNext() && it3.hasNext()) {
			Object name = it.next();
			Object id = it2.next();
			Object tipoArc = it3.next();
			Object nomTipoArc = it4.next();

			Archivo arcR = new Archivo();
			arcR.setNombre((String) name);
			arcR.setId((Long) id);

			TipoArchivo ta = new TipoArchivo();
			ta.setId((Short) tipoArc);
			ta.setNombre((String) nomTipoArc);

			arcR.setTipoArchivo(ta);

			resumenArchivo.add(arcR);
		}
		return resumenArchivo;
	}

	public void eliminarProyecto(Long id) throws DataAccessException {

		Session session = getSession();
		Transaction tr = session.beginTransaction();

		Proyecto proyecto;
		proyecto = (Proyecto) session.createCriteria(Proyecto.class).add(Restrictions.idEq(id)).uniqueResult();

		session.delete(proyecto);
		tr.commit();
		session.close();
	}

	public void eliminarInvestigadorProyecto(InvestigadorProyecto investigadorProyecto) throws DataAccessException {
		Session session = getSession();
		Transaction tr = session.beginTransaction();

		session.delete(investigadorProyecto);

		tr.commit();
		session.close();
	}

	public List obtenerProyectosTituloConvocatoria(String nombre, Modalidad mod) throws DataAccessException {
		Session session = getSession();

		String queryString = "from Proyecto where ( lower(nombre) like :nombre AND modalidad = :modalidad )";
		Query query = session.createQuery(queryString);
		query.setParameter("nombre", "%" + nombre.toLowerCase() + "%", Hibernate.CHARACTER);
		query.setParameter("modalidad", mod, Hibernate.entity(Modalidad.class));
		List result = query.list();

		session.close();
		return result;
	}

	public List obtenerProyectosPorNombreEstado(String nombreProyecto, EstadoProyecto estadoProyecto)
			throws DataAccessException {

		System.out.println("query prouectos estado");
		Session session = getSession();

		String queryString = "from Proyecto p where ( lower(p.nombre) like :nombre AND p.estadoProyecto.id != :estado )";
		Query query = session.createQuery(queryString);
		query.setString("nombre", "%" + nombreProyecto.toLowerCase() + "%");
		query.setString("estado", estadoProyecto.getId());
		List result = query.list();

		session.close();
		return result;
	}

	public Proyecto obtenerProyectoHistorico(Long id) throws DataAccessException {
		Session session = getSession();

		Proyecto p = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("historico", FetchMode.JOIN)
				.add(Restrictions.idEq(id)).uniqueResult();

		session.close();

		return p;
	}

	public ProyectoEvaluador obtenerProyectoEvaluador(ProyectoEvaluador pe) {
		Session session = getSession();

		ProyectoEvaluador p = (ProyectoEvaluador) session.createCriteria(ProyectoEvaluador.class)
				.setFetchMode("calificaciones", FetchMode.JOIN).add(Restrictions.idEq(pe.getId())).uniqueResult();

		session.close();

		return p;
	}

	public ProyectoEvaluador obtenerProyectoEvaluadorConTipoFinancioacion(ProyectoEvaluador pe) {
		Session session = getSession();

		ProyectoEvaluador p = (ProyectoEvaluador) session.createCriteria(ProyectoEvaluador.class)
				.setFetchMode("calificaciones", FetchMode.JOIN).setFetchMode("tipoFinanciacion", FetchMode.JOIN)
				.add(Restrictions.idEq(pe.getId())).uniqueResult();

		session.close();

		return p;
	}

	public void guardarEvaluacionProyecto(ProyectoEvaluador evaluacionProyecto) throws DataAccessException {

		/*
		 * No se puede guardar todo el objeto con su respectivo set de
		 * calificaciones porque bloquea la base de tados ya que se envian todos
		 * los updates de las calificaciones de una y estos al tener objetos
		 * CLOB con muchos datos, utiliza el mismo registro en oracle y se
		 * bloquea
		 */

		HibernateTemplate template = getHibernateTemplate();
		Set calificaciones = evaluacionProyecto.getCalificaciones();
		Iterator it = calificaciones.iterator();
		while (it.hasNext()) {
			CalificacionEvaluacion ce = (CalificacionEvaluacion) it.next();
			template.saveOrUpdate(ce);
		}
		
        ProyectoEvaluador pe = new ProyectoEvaluador();
        pe.setId(evaluacionProyecto.getId());
        pe.setCalificacionFinal(evaluacionProyecto.getCalificacionFinal());
        pe.setEvaluador(evaluacionProyecto.getEvaluador());
        pe.setObservaciones(evaluacionProyecto.getObservaciones());
        pe.setConcepto(evaluacionProyecto.getConcepto());
        pe.setProyecto(evaluacionProyecto.getProyecto());
        pe.setDocumentoEvaluador(evaluacionProyecto.getDocumentoEvaluador());
        pe.setTipoEvaluacion(evaluacionProyecto.getTipoEvaluacion());
        pe.setFecha(evaluacionProyecto.getFecha());
        //seleccion
        pe.setFechaSeleccion(evaluacionProyecto.getFechaSeleccion());
        pe.setEstadoSeleccion(evaluacionProyecto.getEstadoSeleccion());
        pe.setDocumentoSeleccion(evaluacionProyecto.getDocumentoSeleccion());
        pe.setCalificacionFinalSeleccion(evaluacionProyecto.getCalificacionFinalSeleccion());
        pe.setObservacionesSeleccion(evaluacionProyecto.getObservacionesSeleccion());
        pe.setConceptoSeleccion(evaluacionProyecto.getConceptoSeleccion());
        template.saveOrUpdate(pe);

	}

	public void guardarEvaluacionProyectoConTipoFinanciacion(ProyectoEvaluador evaluacionProyecto)
			throws DataAccessException {

		/*
		 * No se puede guardar todo el objeto con su respectivo set de
		 * calificaciones porque bloquea la base de tados ya que se envian todos
		 * los updates de las calificaciones de una y estos al tener objetos
		 * CLOB con muchos datos, utiliza el mismo registro en oracle y se
		 * bloquea
		 */

		HibernateTemplate template = getHibernateTemplate();
		Set calificaciones = evaluacionProyecto.getCalificaciones();
		Iterator it = calificaciones.iterator();
		while (it.hasNext()) {
			CalificacionEvaluacion ce = (CalificacionEvaluacion) it.next();
			template.saveOrUpdate(ce);
		}

		ProyectoEvaluador pe = new ProyectoEvaluador();
		pe.setId(evaluacionProyecto.getId());
		pe.setCalificacionFinal(evaluacionProyecto.getCalificacionFinal());
		pe.setEvaluador(evaluacionProyecto.getEvaluador());
		pe.setObservaciones(evaluacionProyecto.getObservaciones());
		pe.setConcepto(evaluacionProyecto.getConcepto());
		pe.setProyecto(evaluacionProyecto.getProyecto());
		pe.setTipoFinanciacion(evaluacionProyecto.getTipoFinanciacion());
		template.saveOrUpdate(pe);

	}

	public Proyecto obtenerProyectoCodigoDib(String codigoDib) throws DataAccessException {
		List lista = getHibernateTemplate().find("from Proyecto where codigoDib = '" + codigoDib + "' and estadoProyecto != 'B'");
		if (lista.size() == 0) {
			return null;
		} else {
			return (Proyecto) lista.get(0);
		}
	}

	public List obtenerProyectosxEjemplo(Proyecto pry) {
		List listaId = new ArrayList();
		Session session = getSession();
		Criteria criteria = session.createCriteria(Proyecto.class);
		if (pry.getModalidad() != null) {
			criteria.add(Expression.eq("modalidad", pry.getModalidad()));
		}
		if (pry.getNombre() != null && pry.getNombre().length() > 0) {
			criteria.add(Expression.ilike("nombre", pry.getNombre(), MatchMode.ANYWHERE));
		}
		if (pry.getEstadoProyecto() != null) {
			criteria.add(Expression.eq("estadoProyecto", pry.getEstadoProyecto()));
		}
		try {
			Query queryInvestigadores = session.createQuery(
					"select inv.proyecto.id from InvestigadorProyecto inv where inv.investigador.id.tipoDocumento=:tipoDocumento and inv.investigador.id.documento=:documento");
			Iterator it = pry.getInvestigadoresProyecto().iterator();
			if (it != null && it.hasNext()) {
				InvestigadorProyecto invpry = ((InvestigadorProyecto) it.next());
				if (invpry.getInvestigador().getId().getTipoDocumento() != null
						&& invpry.getInvestigador().getId().getDocumento() != null) {
					listaId.addAll(queryInvestigadores
							.setParameter("tipoDocumento", invpry.getInvestigador().getId().getTipoDocumento())
							.setParameter("documento", invpry.getInvestigador().getId().getDocumento()).list());
				}
				if (invpry.getInvestigador().getNombre1() != null || invpry.getInvestigador().getNombre2() != null
						|| invpry.getInvestigador().getApellido1() != null
						|| invpry.getInvestigador().getApellido2() != null) {

					Criteria criterioInvestigador = session.createCriteria(Investigador.class);

					if (invpry.getInvestigador().getNombre1() != null) {
						criterioInvestigador.add(Expression.ilike("nombre1", invpry.getInvestigador().getNombre1()));
					}
					if (invpry.getInvestigador().getNombre2() != null) {
						criterioInvestigador.add(Expression.ilike("nombre2", invpry.getInvestigador().getNombre2()));
					}
					if (invpry.getInvestigador().getApellido1() != null) {
						criterioInvestigador
								.add(Expression.ilike("apellido1", invpry.getInvestigador().getApellido1()));
					}
					if (invpry.getInvestigador().getApellido2() != null) {
						criterioInvestigador
								.add(Expression.ilike("apellido2", invpry.getInvestigador().getApellido2()));
					}

					List listaInvestigadores = criterioInvestigador.list();

					for (int i = 0; i < listaInvestigadores.size(); i++) {
						Investigador inv = (Investigador) listaInvestigadores.get(i);
						listaId.addAll(queryInvestigadores.setParameter("tipoDocumento", inv.getId().getTipoDocumento())
								.setParameter("documento", inv.getId().getDocumento()).list());
					}
				}
				criteria.add(Expression.in("id", listaId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List proyectos = criteria.list();
		session.close();
		return proyectos;
	}

	public List obtenerProyectosConvocatoria(EstadoProyecto[] estados, Modalidad mod) {
		Session session = getSession();

		String queryString = "from Proyecto where estadoProyecto in(:estados) AND modalidad = :modalidad ";
		Query query = session.createQuery(queryString);
		query.setParameter("modalidad", mod, Hibernate.entity(Modalidad.class));
		query.setParameterList("estados", estados);
		List result = query.list();

		session.close();
		return result;

	}

	public int obtenerNumeroProyectosConvocatoria(EstadoProyecto[] estados, Modalidad mod) throws DataAccessException {

		Session session = getSession();

		String queryString = "select count(*) from Proyecto where  estadoProyecto in(:estados) AND  modalidad = :modalidad ";
		Query query = session.createQuery(queryString);

		query.setParameter("modalidad", mod, Hibernate.entity(Modalidad.class));
		query.setParameterList("estados", estados);

		Integer count = (Integer) query.uniqueResult();

		session.close();
		return count.intValue();

	}

	// TODO ARREGLAR genera error cuando el año dea convocatoria es 2005, puede
	// ser el hecho de ue hace left join con invs externos y con invs internos,
	// no se hasta ahora cual se el problema. jassar
	public List obtenerProyectosConvocatoriaConInvestigadores(EstadoProyecto[] estados, Modalidad mod)
			throws DataAccessException {
		Session session = getSession();

		List p = (List) session.createCriteria(Proyecto.class).setFetchMode("investigadoresProyecto", FetchMode.JOIN)
				.add(Expression.eq("modalidad", mod)).add(Expression.in("estadoProyecto", estados)).list();

		session.close();

		return p;
	}

	public Financiacion obtenerFinanciacionGastos(Long id) throws DataAccessException {
		Session session = getSession();

		Financiacion financiacion = (Financiacion) session.createCriteria(Financiacion.class)
				.setFetchMode("gastos", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

		session.close();
		return financiacion;
	}

	public void adicionarInvestigadorExterno(Investigador inv) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(inv);
	}

	// Diana-- Probar cltas proyectos
	public List obtenerProyectosPorNombreDependencia(String nombreProyecto, Dependencia dependencia,
			String tipoDependencia) throws DataAccessException {

		Session session = getSession();

		List resultado;

		Criteria proyectoCriteria = session.createCriteria(Proyecto.class);

		proyectoCriteria.add(Expression.ilike("nombre", nombreProyecto, MatchMode.ANYWHERE));
		proyectoCriteria.add(Expression.ne("estadoProyecto.id", "P"));// / trae
		// solo
		// los
		// propuestos

		if (tipoDependencia.equals(Dependencia.TIPO_DEPENDENCIA_FACULTAD)) {
			Criteria dependenciasAreaCriteria = proyectoCriteria.createCriteria("dependenciasAreaResponsabilidad");
			Criteria dependenciaCriteria = dependenciasAreaCriteria.createCriteria("dependencia");

			dependenciaCriteria.add(Expression.eq("facultad", dependencia));

		} else if (tipoDependencia.equals(Dependencia.TIPO_DEPENDENCIA_SEDE)) {
			Sede sede = new Sede();
			sede.setId(new Long(dependencia.getId()));
			Criteria dependenciasAreaCriteria = proyectoCriteria.createCriteria("dependenciasAreaResponsabilidad");
			Criteria dependenciaCriteria = dependenciasAreaCriteria.createCriteria("dependencia");
			dependenciaCriteria.add(Expression.eq("sede", sede));
		}

		resultado = proyectoCriteria.list();

		session.close();
		return resultado;

	}

	public List obtenerProyectosPorNombreDependenciaIndeferenteTildesMayusculasDiferenteEstado(String nombreProyecto,
			Dependencia dependencia, String tipoDependencia, String idEstado) throws DataAccessException {

		Session session = getSession();

		List resultado;

		Criteria proyectoCriteria = session.createCriteria(Proyecto.class);

		proyectoCriteria.add(Expression.sqlRestriction(ReemplazaAcentos.queryQuitaTildes("pry_nombre") + " like '%"
				+ ReemplazaAcentos.quitarTildes(nombreProyecto.toLowerCase()) + "%'"));
		proyectoCriteria.add(Expression.ne("estadoProyecto.id", idEstado));
		if (tipoDependencia.equals(Dependencia.TIPO_DEPENDENCIA_FACULTAD)) {
			Criteria dependenciasAreaCriteria = proyectoCriteria.createCriteria("dependenciasAreaResponsabilidad");
			Criteria dependenciaCriteria = dependenciasAreaCriteria.createCriteria("dependencia");

			dependenciaCriteria.add(Expression.eq("facultad", dependencia));
			dependenciaCriteria.add(Expression.ilike("estado", "P"));// / trae
			// solo los
			// propuestos
			// -¿las
			// dependencias?

		} else if (tipoDependencia.equals(Dependencia.TIPO_DEPENDENCIA_SEDE)) {
			Sede sede = new Sede();
			sede.setId(new Long(dependencia.getId()));
			Criteria dependenciasAreaCriteria = proyectoCriteria.createCriteria("dependenciasAreaResponsabilidad");
			Criteria dependenciaCriteria = dependenciasAreaCriteria.createCriteria("dependencia");
			dependenciaCriteria.add(Expression.eq("sede", sede));
		}

		resultado = proyectoCriteria.list();

		session.close();
		return resultado;

	}

	public List obtenerAreasTematicas(Long id) {
		Session session = getSession();

		Proyecto proyecto = (Proyecto) session.createCriteria(Proyecto.class)
				.setFetchMode("clasificacionConocimiento", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

		session.close();

		return proyecto.getListaClasificacionConocimiento();
	}

	/**
	 * Se obtienen los integrantes de los proyectos segun los estados
	 * "estadosProyectos"
	 */
	public List obtenerIntegrantesProyectosXEstados(List estadosProyectos) {
		Session session = getSession();

		Criteria criteriaInvestigador = session.createCriteria(Investigador.class);
		Criteria criteriaProyectos = criteriaInvestigador.createCriteria("proyectosInvestigador");
		Criteria criteriaProyecto = criteriaProyectos.createCriteria("proyecto");
		criteriaProyecto.add(Restrictions.in("estadoProyecto", estadosProyectos));

		List l = criteriaInvestigador.list();

		session.close();

		return l;
	}

	public long obtenerFiananciacionXProyectoYFuente(Long idProyecto, String fuente) {
		Session session = getSession();
		ResultSet rs = null;
		Statement st = null;

		try {
			st = session.connection().createStatement();
			st.execute("select sum(f.FIN_VALOR) " + "from her_financiacion f,her_fuente_financiacion ff " + "where "
					+ "f.ffi_id=ff.ffi_id and f.pry_id=" + idProyecto.longValue() + " and ff.FFI_INTERNA_EXTERNA='"
					+ fuente + "'");
			rs = st.getResultSet();

			if (rs.next())
				return rs.getLong(1);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (session != null && session.isOpen())
					session.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}
		return 0;
	}

	public List obtenerDiferentesTipoRubroXProyecto(Long idProyecto) {
		Session session = null;
		List resultado = null;
		try {
			session = getSession();
			Query q = session.createQuery("select distinct p.financiaciones.gastos.tipoRubro " + " from Proyecto p"
					+ " where p.id=:idProyecto ");
			q.setLong("idProyecto", idProyecto.longValue());
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

	public List obtenerDiferentesVigenciasXProyecto(Long idProyecto) {
		Session session = null;
		List resultado = null;
		try {
			session = getSession();
			Query q = session.createQuery(
					" select distinct p.financiaciones.gastos.vigencia from Proyecto p where p.id=:idProyecto");
			q.setLong("idProyecto", idProyecto.longValue());
			resultado = q.list();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return resultado;
	}

	public Object obtenerSumaGastoXTipoRubroYVigenciaYProyecto(Long idProyecto, Long tipoRubro, Long vigencia) {
		Session session = null;
		Object resultado = null;
		try {
			session = getSession();
			session.beginTransaction();
			Query q = session
					.createQuery(" select sum(g.valor) from Gasto g where g.financiacion.proyecto.id=:idProyecto "
							+ "and g.tipoRubro.id = :tipoRubro and g.vigencia=:vigencia ");
			q.setLong("idProyecto", idProyecto.longValue());
			q.setLong("tipoRubro", tipoRubro.longValue());
			q.setLong("vigencia", vigencia.longValue());
			resultado = q.uniqueResult();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		System.out.println(resultado);
		return resultado;
	}

	public List obtenerActividadesPersonaPorActividad(Long idActividad) {
		Session session = null;
		List resultado = null;
		try {
			session = getSession();
			session.beginTransaction();
			Query q = session
					.createQuery(" select ap" + " from ActividadPersona ap" + " where ap.actividad.id=:idActividad ");
			q.setLong("idActividad", idActividad.longValue());
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

	public List obtenerListaDeProyectosXGrupoYEstado(Grupo g, EstadoProyecto e) {
		Session s = getSession();
		Query q = s.createQuery(" select p from Proyecto p left outer join p.financiaciones f"
				+ " where p.estadoProyecto.id=:idEstadoProyecto and p.grupos.id=:idGrupo");
		q.setString("idEstadoProyecto", e.getId());
		q.setLong("idGrupo", g.getId().longValue());
		List r = q.list();
		s.close();
		return r;
	}

	public List obtenerListaActividadesPersonaXActividadYPersona(Actividad a, IdPersona id) {
		Session session = getSession();
		Query q = session.createQuery(
				" select ap from ActividadPersona ap where" + " ap.investigador.id.tipoDocumento=:tipoDocumento"
						+ " and ap.investigador.id.tipoDocumento=:documento" + " and ap.actividad.id=:idActividad");
		q.setString("tipoDocumento", id.getTipoDocumento());
		q.setString("documento", id.getDocumento());
		q.setLong("idActividad", a.getId().longValue());
		List resultado = q.list();

		session.close();
		return resultado;
	}
	
	public List obtenerProyectosXModalidadEInvestigador(Modalidad tm, Investigador i, String estadosProyecto) {
		Session session = null;
		List resultado = null;
		try {
			session = getSession();
			session.beginTransaction();
			Query q = session.createQuery(" select p from Proyecto p, InvestigadorProyecto ip"
					+ " where p.modalidad.id=:idModalidad and p.estadoProyecto.id in (" + estadosProyecto + ")"
					+ " and ip.investigador.id.tipoDocumento=:tipoDocumentoInv"
					+ " and ip.investigador.id.documento=:documentoInv" + " and ip.proyecto.id in (p.id)");
			q.setLong("idModalidad", tm.getId());
			q.setString("tipoDocumentoInv", i.getId().getTipoDocumento());
			q.setString("documentoInv", i.getId().getDocumento());
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

	public List obtenerProyectosXModalidadEInvestigadorCortes(Modalidad tm, Investigador i,
			String estadosProyecto, Long idCorte) {
		Session session = null;
		List resultado = null;
		try {
			session = getSession();
			session.beginTransaction();
			Query q = session.createQuery(" select p from Proyecto p, InvestigadorProyecto ip"
					+ " where p.modalidad.id=:idModalidad and p.estadoProyecto.id in (" + estadosProyecto + ")"
					+ " and ip.investigador.id.tipoDocumento=:tipoDocumentoInv"
					+ " and ip.investigador.id.documento=:documentoInv" + " and p.corteConvocatoria.id = " + idCorte
					+ " and ip.proyecto.id in (p.id)");
			q.setLong("idModalidad", tm.getId());
			q.setString("tipoDocumentoInv", i.getId().getTipoDocumento());
			q.setString("documentoInv", i.getId().getDocumento());
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

	public List obtenerProyectosXModalidadEInvestigadorPrincipal(Modalidad tm, Investigador i, String estadosProyecto) {
		Session session = null;
		List resultado = null;
		try {
			session = getSession();
			session.beginTransaction();
			Query q = session.createQuery(" select p from Proyecto p, InvestigadorProyecto ip"
					+ " where p.modalidad.id=:idModalidad and p.estadoProyecto.id in (" + estadosProyecto + ")"
					+ " and ip.tipo.id=:idPrincipal " + " and ip.investigador.id.tipoDocumento=:tipoDocumentoInv"
					+ " and ip.investigador.id.documento=:documentoInv" + " and ip.proyecto.id in (p.id)");
			q.setLong("idModalidad", tm.getId());
			// q.setString("idEstado", estadosProyecto);
			q.setString("idPrincipal", TipoInvestigador.Principal);
			q.setString("tipoDocumentoInv", i.getId().getTipoDocumento());
			q.setString("documentoInv", i.getId().getDocumento());
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

	public List obtenerProyectosXModalidadEInvestigadorPrincipalCortes(Modalidad tm, Investigador i,
			String estadosProyecto, Long idCorte) {
		Session session = null;
		List resultado = null;
		try {
			session = getSession();
			session.beginTransaction();
			Query q = session.createQuery(" select p from Proyecto p, InvestigadorProyecto ip"
					+ " where p.modalidad.id=:idModalidad and p.estadoProyecto.id in (" + estadosProyecto + ")"
					+ " and ip.tipo.id=:idPrincipal " + " and ip.investigador.id.tipoDocumento=:tipoDocumentoInv"
					+ " and ip.investigador.id.documento=:documentoInv" + " and p.corteConvocatoria.id = " + idCorte
					+ " and ip.proyecto.id in (p.id)");
			q.setLong("idModalidad", tm.getId());
			// q.setString("idEstado", estadosProyecto);
			q.setString("idPrincipal", TipoInvestigador.Principal);
			q.setString("tipoDocumentoInv", i.getId().getTipoDocumento());
			q.setString("documentoInv", i.getId().getDocumento());
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

	public List obtenerProyectosXTipoModalidadYInvestigadorPrincipal(TipoModalidad tm, Investigador i) {
		Session session = null;
		List resultado = null;
		try {
			session = getSession();
			session.beginTransaction();
			Query q = session.createQuery(" select p" + " from Proyecto p"
					+ " where p.modalidad.tipo.id=:tipoModalidad and p.estadoProyecto.id!=:idEstadoBorrado"
					+ " and p.investigadoresProyecto.tipo.id=:idPrincipal "
					+ " and p.investigadoresProyecto.investigador.id.tipoDocumento=:tipoDocumento"
					+ " and p.investigadoresProyecto.investigador.id.documento=:documentoInv");
			q.setString("tipoModalidad", tm.getId());
			q.setString("idEstadoBorrado", EstadoProyecto.BORRADO);
			q.setString("idPrincipal", TipoInvestigador.Principal);
			q.setString("tipoDocumento", i.getId().getTipoDocumento());
			q.setString("documentoInv", i.getId().getDocumento());
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

	public List obtenerObservacionesProyecto(Proyecto proyecto) throws DataAccessException {
		Session session = getSession();
		List observaciones;
		Criteria criteria = session.createCriteria(ObservacionProyecto.class);
		criteria.add(Expression.eq("proyecto", proyecto));
		criteria.addOrder(Order.asc("id"));
		observaciones = criteria.list();
		session.close();
		return observaciones;
	}

	public List<Solicitud> obtenerSolicitudesProyecto(Proyecto proyecto, boolean incluirGuardados,
			boolean incluirDevueltos) throws DataAccessException {
		List solicitudes;
		Session session = getSession();
		Criteria criteria = session.createCriteria(Solicitud.class);
		criteria.add(Expression.eq("proyecto", proyecto));
		criteria.add(Expression.sql("TSO_ID != '" + TipoSolicitud.INFORME_AVANCE + "'"));
		criteria.add(Expression.sql("TSO_ID != '" + TipoSolicitud.INFORME_FINAL + "'"));
		String estadosIncluidos = "(SOL_RESPUESTA IN ('A','R','T'";
		if (incluirGuardados) {
			estadosIncluidos += ",'G'";
		}
		if (incluirDevueltos) {
			estadosIncluidos += ",'C'";
		}
		estadosIncluidos += ") OR SOL_RESPUESTA IS NULL)";
		criteria.add(Expression.sql(estadosIncluidos));
		criteria.addOrder(Order.desc("fecha"));
		solicitudes = criteria.list();
		for (int i = 0; i < solicitudes.size(); i++) {
			DetalleSolicitud detalleSolicitud;
			Solicitud solicitud = (Solicitud) solicitudes.get(i);
			criteria = session.createCriteria(DetalleSolicitud.class);
			criteria.add(Expression.eq("solicitud", solicitud));
			detalleSolicitud = (DetalleSolicitud) criteria.uniqueResult();
			solicitud.setDetalleSolicitud(detalleSolicitud);

			solicitudes.set(i, solicitud);
		}
		session.close();
		return solicitudes;
	}

	public List obtenerInvestigadoresProyecto(Long idProyecto) {
		Session session = null;
		List investigadores = null;
		try {
			session = getSession();
			session.beginTransaction();
			Query q = session
					.createQuery("select inv from InvestigadorProyecto inv where inv.proyecto.id=:idProyecto ");
			q.setLong("idProyecto", idProyecto.longValue());
			investigadores = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return investigadores;
	}

	public List obtenerGastosProyecto(Long idProyecto) {
		Session session = null;
		List gastos = null;
		try {
			session = getSession();
			session.beginTransaction();
			Query q = session.createQuery("select g from Gasto g where g.financiacion.proyecto.id=:idProyecto ");
			q.setLong("idProyecto", idProyecto.longValue());
			gastos = q.list();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return gastos;
	}

	public List obtenerEvaluadoresProyecto(Long idProyecto) {
		Session session = null;
		List evaluadores = null;
		try {
			session = getSession();
			Query q = session.createQuery("select e from ProyectoEvaluador e where e.proyecto.id=:idProyecto ");
			q.setLong("idProyecto", idProyecto.longValue());
			evaluadores = q.list();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return evaluadores;
	}

	public EstadoProyecto obtenerEstadoProyectoXProyecto(Long idProyecto) {
		Session session = null;
		EstadoProyecto resultado = null;
		try {
			session = getSession();
			Query q = session.createQuery(" select p.estadoProyecto" + " from Proyecto p" + " where p.id=:idProyecto");
			q.setLong("idProyecto", idProyecto.longValue());
			resultado = (EstadoProyecto) q.uniqueResult();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return resultado;
	}

	public List obtenerListaEquipos2XProyecto(Long idProyecto) {
		Session session = null;
		List resultado = null;
		try {
			session = getSession();
			Query q = session.createQuery(" select e" + " from Equipo2 e" + " where e.proyecto.id=:idProyecto");
			q.setLong("idProyecto", idProyecto.longValue());
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

	public Investigador obtenerInvestigadorPrincipalXProyecto(Long idProyecto) {
		Session session = null;
		Investigador resultado = null;
		try {
			session = getSession();
			Query q = session.createQuery(" select ip.investigador" + " from Proyecto p,InvestigadorProyecto ip"
					+ " where " + "  " + " p.investigadoresProyecto.id=ip.id" + "" + " and ip.tipo.id=:lider and "
					+ " p.id=:idProyecto order by ip.id desc");
			q.setLong("idProyecto", idProyecto.longValue());
			q.setString("lider", TipoInvestigador.Principal);
			resultado = (Investigador) q.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return resultado;

	}

	public Investigador obtenerInvestigadorPrincipalXCreador(String tipoDocumento, String documento) {
		Session session = null;
		Investigador resultado = null;
		try {
			session = getSession();

			Query q = session.createQuery(" select ip" + " from Investigador ip" + " where "
					+ " ip.id.documento=:documento and ip.id.tipoDocumento=:tipoDocumento");

			q.setString("tipoDocumento", tipoDocumento);
			q.setString("documento", documento);
			resultado = (Investigador) q.list().get(0);

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return resultado;

	}

	public Investigador estaInvestigadorEnProyecto(Investigador i, Long idProyecto) {

		Session session = null;
		Investigador resultado = null;
		try {
			session = getSession();
			Query q = session.createQuery(" select ip.investigador" + " from Proyecto p,InvestigadorProyecto ip"
					+ " where " + "  " + " p.investigadoresProyecto.id=ip.id" + ""
					+ " and ip.investigador.id.tipoDocumento=:tipoDocumento  "
					+ " and ip.investigador.id.documento=:documento and " + " p.id=:idProyecto");
			q.setLong("idProyecto", idProyecto.longValue());
			q.setString("tipoDocumento", i.getId().getTipoDocumento());
			q.setString("documento", i.getId().getDocumento());
			resultado = (Investigador) q.uniqueResult();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return resultado;

	}

	public List buscar(Persona asesor, Long idProyecto, String codigoDIBProyecto, String nombreProyecto,
			EstadoProyecto estadoProyecto, Long idConvocatoria, Long idModalidad, IdPersona idEvaluador,
			IdPersona idInvestigadorPrincipal, IdPersona idParticipante, String palabraClave, String idFacultad,
			Long ano, String idClasificacionConocimiento, String idDepartamento) {
		List proyectos = null;
		Session session;
		Statement sentencia = null;

		String condiciones = "";

		String query = "SELECT pry.pry_id codigo, " + "pry.epr_id estado, " + "pry.pry_codigo_dib dib, "
				+ "pry.pry_nombre nombre, " + "espry.epr_nombre nombre_estado, "
				+ "pInp.PER_APELLIDO1 || ' ' || pInp.PER_APELLIDO2 || ' ' || pInp.PER_nombre1 || ' ' || pInp.PER_nombre2 investigador_principal, "
				+ "depto.dpn_nombre departamento, " + "fac.dpn_nombre facultad, " + "cnp.cnp_ano ano, "
				+ "cnp.cnp_titulo " + "FROM her_proyecto pry, " + "her_estado_proyecto espry, "
				+ "her_convocatoria con, " + "her_convocatoria_padre cnp, " + "her_investigador_proyecto inp, "
				+ "her_persona pInp, " + "her_investigador_interno inin, " + "her_dependencia depto, "
				+ "her_dependencia fac " + "WHERE espry.epr_id = pry.epr_id " + "AND cnp.cnp_id(+) = con.cnp_id "
				+ "AND con.con_id(+) = pry.mod_id " + "AND pry.pry_id = inp.pry_id " + "AND pInp.tdo_id = inp.tdo_id "
				+ "AND pInp.per_id = inp.inv_id " + "AND inp.inp_tipo = '" + TipoInvestigador.Principal + "' "
				+ "AND inin.tdo_id (+)= inp.tdo_id " + "AND inin.inv_id (+)= inp.inv_id "
				+ "AND inin.dpn_id = depto.dpn_id(+) " + "AND depto.dpn_facultad = fac.dpn_id(+) AND ";

		;

		boolean ejecutaConsulta = false;

		if (asesor != null) {
			condiciones += " EXISTS (SELECT 1 FROM her_seg_proyecto_persona sp WHERE sp.per_id = '"
					+ asesor.getId().getDocumento() + "' AND sp.tdo_id = '" + asesor.getId().getTipoDocumento()
					+ "' AND pry.pry_id = sp.pry_id) AND ";
			if (asesor.getNombre1().equals("S")) {
				ejecutaConsulta = true;
			}
		}

		if (idProyecto != null) {
			condiciones += " pry.pry_id = " + idProyecto.longValue() + " AND ";
			ejecutaConsulta = true;
		}

		if (codigoDIBProyecto != null && !codigoDIBProyecto.equals("")) {
			condiciones += " pry.pry_codigo_dib LIKE '" + codigoDIBProyecto + "' AND ";
			ejecutaConsulta = true;
		}

		if (nombreProyecto != null && !nombreProyecto.equals("")) {
			condiciones += " UPPER(pry.pry_nombre) LIKE '" + nombreProyecto.toUpperCase() + "' AND ";
			ejecutaConsulta = true;
		}

		if (estadoProyecto.getId() != null && !estadoProyecto.getId().equals("")) {
			condiciones += " pry.epr_id = '" + estadoProyecto.getId() + "' AND ";
			ejecutaConsulta = true;
		}

		if (idConvocatoria.longValue() != -1) {
			condiciones += " pry.mod_id = ANY (SELECT con.con_id " + "FROM her_convocatoria con "
					+ "WHERE con.cnp_id = " + idConvocatoria.longValue() + ") AND ";
			ejecutaConsulta = true;
		}

		if (idModalidad.longValue() != -1) {
			condiciones += " pry.mod_id = " + idModalidad.longValue() + " AND ";
			ejecutaConsulta = true;
		}

		if (!idEvaluador.getTipoDocumento().equals("") || !idEvaluador.getDocumento().equals("")) {
			condiciones += " pry.pry_id = ANY (SELECT pre.pry_id " + "FROM her_proyecto_evaluador pre "
					+ "WHERE pre.tdo_id = NVL('" + idEvaluador.getTipoDocumento() + "',pre.tdo_id) "
					+ "AND pre.inv_id = NVL('" + idEvaluador.getDocumento() + "',pre.inv_id)) AND ";
			ejecutaConsulta = true;
		}

		if (!idInvestigadorPrincipal.getTipoDocumento().equals("")
				|| !idInvestigadorPrincipal.getDocumento().equals("")) {
			condiciones += " pry.pry_id = ANY (SELECT inp.pry_id " + "FROM her_investigador_proyecto inp "
					+ "WHERE inp.inp_tipo = 'P' " + "AND inp.tdo_id = NVL('"
					+ idInvestigadorPrincipal.getTipoDocumento() + "',inp.tdo_id) " + "AND inp.inv_id = NVL('"
					+ idInvestigadorPrincipal.getDocumento() + "',inp.inv_id)) AND ";
			ejecutaConsulta = true;
		}

		if (!idParticipante.getTipoDocumento().equals("") || !idParticipante.getDocumento().equals("")) {
			condiciones += " pry.pry_id = ANY (SELECT par.pry_id " + "FROM her_investigador_proyecto par "
					+ "WHERE par.tdo_id = NVL('" + idParticipante.getTipoDocumento() + "',par.tdo_id) "
					+ "AND par.inv_id = NVL('" + idParticipante.getDocumento() + "',par.inv_id)) AND ";
			ejecutaConsulta = true;
		}

		if (palabraClave != null && !palabraClave.equals("")) {
			condiciones += " pry.pry_id = ANY (SELECT prpcl.pry_id " + "FROM her_proyecto_palabra_clave prpcl, "
					+ "her_palabra_clave pcl " + "WHERE prpcl.pcl_id = pcl.pcl_id " + "AND UPPER(pcl.pcl_palabra) = '"
					+ palabraClave.toUpperCase() + "') AND ";
			ejecutaConsulta = true;
		}

		if (idDepartamento != null && !idDepartamento.equals("")) {
			condiciones += " pry.pry_id = ANY (SELECT inp3.pry_id " + "FROM her_investigador_proyecto inp3, "
					+ "her_investigador_interno inin " + "WHERE inp3.inp_tipo = 'P' " + "AND inp3.tdo_id = inin.tdo_id "
					+ "AND inp3.inv_id = inin.inv_id " + "AND inin.dpn_id = '" + idDepartamento + "') AND ";
			ejecutaConsulta = true;
		} else if (idFacultad != null && !idFacultad.equals("")) {
			condiciones += " pry.pry_id = ANY (SELECT inp3.pry_id " + "FROM her_investigador_proyecto inp3, "
					+ "her_investigador_interno inin, " + "her_dependencia depe " + "WHERE inp3.inp_tipo = 'P' "
					+ "AND inp3.tdo_id = inin.tdo_id " + "AND inp3.inv_id = inin.inv_id "
					+ "AND inin.dpn_id = depe.dpn_id " + "AND depe.dpn_facultad = '" + idFacultad + "') AND ";
			ejecutaConsulta = true;
		}

		if (ano != null) {
			condiciones += " pry.mod_id = ANY (SELECT con3.con_id " + "FROM her_convocatoria con3, "
					+ "her_convocatoria_padre cnp3 " + "WHERE con3.cnp_id = cnp3.cnp_id " + "AND cnp3.cnp_ano = '"
					+ ano.toString() + "') AND ";
			ejecutaConsulta = true;
		}

		if (idClasificacionConocimiento != null && !idClasificacionConocimiento.equals("")) {
			condiciones += " pry.pry_id = ANY (SELECT clcon.pry_id " + "FROM her_proyecto_clas_con clcon "
					+ "WHERE clcon.clc_id = '" + idClasificacionConocimiento + "') AND ";
			ejecutaConsulta = true;
		}

		if (ejecutaConsulta) {
			proyectos = new ArrayList();
			condiciones = condiciones.trim().substring(0, condiciones.trim().length() - 3);
			query += condiciones + " ORDER BY 9 DESC, 2";
			session = getSession();

			try {
				sentencia = session.connection().createStatement();
				sentencia.execute(query);

				ResultSet rs = sentencia.getResultSet();
				while (rs.next()) {
					Proyecto proyecto = new Proyecto();
					EstadoProyecto estado = new EstadoProyecto();
					proyecto.setId(new Long(rs.getLong(1)));
					proyecto.setNombre(rs.getString(4));
					proyecto.setCodigoDib(rs.getString(3));
					estado.setId(rs.getString(2));
					estado.setNombre(rs.getString(5));
					proyecto.setEstadoProyecto(estado);
					proyecto.setConsideracionesEticas(rs.getString(6));
					proyecto.setCodigoQuipu(rs.getString(7));
					proyecto.setJustificacion(rs.getString(8));
					proyecto.setResumen(rs.getString(9));

					proyecto.setNombreConvocatoriaPadre(rs.getString(10));

					if (!idParticipante.getTipoDocumento().equals("") || !idParticipante.getDocumento().equals("")) {
						Statement sentenciaRolParticipante = null;
						String queryRolParticipante = "SELECT tinv.tinv_nombre "
								+ "FROM her_investigador_proyecto inp, " + " her_tipo_investigador tinv "
								+ "WHERE inp.inp_tipo = tinv.tinv_id " + "AND inp.inv_id = '"
								+ idParticipante.getDocumento() + "' " + "AND inp.tdo_id = '"
								+ idParticipante.getTipoDocumento() + "' " + "AND inp.pry_id = "
								+ proyecto.getId().longValue();
						try {
							sentenciaRolParticipante = session.connection().createStatement();
							sentenciaRolParticipante.execute(queryRolParticipante);
							ResultSet rsRol = sentenciaRolParticipante.getResultSet();

							if (rsRol.next()) {
								proyecto.setObjetivoGeneral(rsRol.getString(1));
							}
						} catch (Exception ex) {
							System.out.println("Excepcion en rol de participante");
						}
					}

					proyectos.add(proyecto);
				}
			} catch (Exception e) {
				proyectos = null;
				System.out.println("Excepcion en consulta de proyectos");
				e.printStackTrace();
			} finally {
				session.close();
			}
		}

		return proyectos;
	}

	public List<Financiacion> obtenerFunetesFinanciacionProyecto(Long idProyecto) {
		List fuentes;
		Session session = getSession();

		Criteria criteria = session.createCriteria(Financiacion.class);
		String alias = criteria.getAlias() + "_";
		criteria.add(Expression.sqlRestriction(alias + ".pry_id = " + idProyecto.longValue()));
		criteria.add(Restrictions.or(Expression.not(Expression.eq("esLegalizacion", "SI")),
				Expression.isNull("esLegalizacion")));
		fuentes = criteria.list();
		session.close();
		return fuentes;
	}

	public List<Financiacion> obtenerFunetesFinanciacionConGastosProyecto(Long idProyecto) {
		List fuentes;
		Session session = getSession();

		Criteria criteria = session.createCriteria(Financiacion.class);
		String alias = criteria.getAlias() + "_";
		criteria.add(Expression.sqlRestriction(alias + ".pry_id = " + idProyecto.longValue()));
		criteria.add(Expression.isNotEmpty("gastos"));
		criteria.add(Restrictions.or(Expression.not(Expression.eq("esLegalizacion", "SI")),
				Expression.isNull("esLegalizacion")));
		criteria.add(Restrictions.or(Expression.not(Expression.eq("tipoEntidad", "P")),
				Expression.isNull("tipoEntidad")));
		
		
		fuentes = criteria.list();
		session.close();
		return fuentes;
	}

	public List<Gasto> obtenerGastosProyectoFinanciacion(Long idFinanciacion) {
		Session session = null;
		List gastos = null;
		try {
			session = getSession();
			Query q = session.createQuery("select g from Gasto g where g.financiacion.id=:idFinanciacion order by g.tipoRubro.id desc");
			q.setLong("idFinanciacion", idFinanciacion.longValue());
			gastos = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return gastos;
	}

	public String generarCodigoDi(String codigoDiTipoModalidad, Long idProyecto) {
		Session session = getSession();
		Statement sentencia = null;
		ResultSet resultado = null;
		String codigoDi = codigoDiTipoModalidad;

		try {
			/**************************************************************
			 * BUSCA CODIGO DI PARA LA FACULTAD A LA QUE PERTENECE EL PROYECTO
			 **************************************************************/
			sentencia = session.connection().createStatement();
			sentencia.execute("SELECT fac.dpn_codigo_di " + "FROM her_investigador_proyecto inp, "
					+ "her_investigador_interno inin, " + "her_dependencia dep, " + "her_dependencia fac "
					+ "WHERE fac.dpn_id = dep.dpn_facultad " + "AND dep.dpn_id = inin.dpn_id "
					+ "AND inin.tdo_id = inp.tdo_id " + "AND inin.inv_id = inp.inv_id " + "AND inp.inp_tipo = 'P' "
					+ "AND inp.pry_id = " + idProyecto.longValue());
			resultado = sentencia.getResultSet();

			if (resultado.next())
				codigoDi += resultado.getString(1);

			/**************************************************************
			 * GENERA CODIGO DI POR TIPO MODALIDAD Y FACULTAD
			 **************************************************************/
			sentencia.execute(
					"SELECT NVL(MAX(LPAD(SUBSTR(pry.pry_codigo_dib,5,LENGTH(pry.pry_codigo_dib))+1,4,'0')),'0001') "
							+ "FROM her_proyecto pry " + "WHERE pry.pry_codigo_dib LIKE '" + codigoDi + "%'");
			resultado = sentencia.getResultSet();

			if (resultado.next())
				codigoDi += resultado.getString(1);
		} catch (Exception ex) {
			ex.printStackTrace();
			codigoDi = "";
		} finally {
			try {
				if (resultado != null)
					resultado.close();

				if (sentencia != null)
					sentencia.close();

				sentencia = null;
				resultado = null;
				session.close();
				session = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return codigoDi;
	}

	public Ciudad obtenerCiudadProyecto(Long idProyecto) {
		Ciudad ciudad = null;
		Session session;
		Statement sentencia = null;
		session = getSession();
		String query = "SELECT ciudad.CIU_ID, " + "ciudad.CIU_NOMBRE " + "FROM her_ciudad ciudad "
				+ "WHERE ciudad.CIU_ID = (SELECT sp.CIU_ID " + "FROM her_proyecto_ciudad sp " + "WHERE sp.PRY_ID = "
				+ idProyecto.longValue() + ")";

		try {
			sentencia = session.connection().createStatement();
			sentencia.execute(query);
			ResultSet rs = sentencia.getResultSet();

			if (rs.next()) {
				ciudad = new Ciudad();
				ciudad.setId(rs.getString(1));
				ciudad.setNombre(rs.getString(2));
			}
		} catch (Exception ex) {
		} finally {
			if (session.isOpen())
				session.close();
		}

		return ciudad;
	}

	public Persona obtenerCoordinadorProyecto(Long idProyecto) {
		Persona coordinador = null;
		Session session;
		Statement sentencia = null;
		session = getSession();
		String query = "SELECT per.per_id, " + "per.tdo_id, " + "per.per_apellido1, " + "per.per_apellido2, "
				+ "per.per_nombre1, " + "per.per_nombre2, " + "per.per_email," + "sp.seg_aprobacion,"
				+ "ii.ini_textension " + "FROM her_persona per left join her_investigador_interno ii on"
				+ " per.per_id = ii.inv_id and per.tdo_id = ii.tdo_id , " + " her_seg_proyecto_persona sp WHERE"
				+ " sp.per_id not in "+DOCUMENTOS_PERSONAS_PRUEBAS+" and per.per_id = sp.per_id " + " and per.tdo_id = sp.tdo_id " + " and sp.pry_id = "
				+ idProyecto.longValue();

		try {
			sentencia = session.connection().createStatement();
			sentencia.execute(query);
			ResultSet rs = sentencia.getResultSet();

			if (rs.next()) {
				coordinador = new Persona();
				IdPersona idPersona = new IdPersona();
				idPersona.setDocumento(rs.getString(1));
				idPersona.setTipoDocumento(rs.getString(2));
				coordinador.setId(idPersona);
				coordinador.setApellido1(rs.getString(3));
				coordinador.setApellido2(rs.getString(4));
				coordinador.setNombre1(rs.getString(5));
				coordinador.setNombre2(rs.getString(6));
				coordinador.setEmail(rs.getString(7));
				coordinador.setPaisOrigen(rs.getString(8));
				coordinador.setTelefono(rs.getString(9));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return coordinador;
	}

	public Persona obtenerCoordinadorEvaluacionProyecto(Long idProyecto) {
		Persona coordinador = null;
		Session session;
		Statement sentencia = null;
		session = getSession();
		String query = "SELECT per.per_id, " + "per.tdo_id, " + "per.per_apellido1, " + "per.per_apellido2, "
				+ "per.per_nombre1, " + "per.per_nombre2, " + "per.per_email," + "ii.ini_textension "
				+ "FROM her_persona per left join her_investigador_interno ii on"
				+ " per.per_id = ii.inv_id and per.tdo_id = ii.tdo_id "
				+ "WHERE per.tdo_id||per.per_id = (SELECT sp.tdo_id3||sp.per_evaluacion "
				+ "FROM her_seg_proyecto_persona sp " + "WHERE sp.pry_id = " + idProyecto.longValue() + " and sp.per_evaluacion not in "+DOCUMENTOS_PERSONAS_PRUEBAS+")";

		try {
			sentencia = session.connection().createStatement();
			sentencia.execute(query);
			ResultSet rs = sentencia.getResultSet();

			if (rs.next()) {
				coordinador = new Persona();
				IdPersona idPersona = new IdPersona();
				idPersona.setDocumento(rs.getString(1));
				idPersona.setTipoDocumento(rs.getString(2));
				coordinador.setId(idPersona);
				coordinador.setApellido1(rs.getString(3));
				coordinador.setApellido2(rs.getString(4));
				coordinador.setNombre1(rs.getString(5));
				coordinador.setNombre2(rs.getString(6));
				coordinador.setEmail(rs.getString(7));
				coordinador.setTelefono(rs.getString(8));
			}
		} catch (Exception ex) {
		} finally {
			if (session.isOpen())
				session.close();
		}

		return coordinador;
	}

	public Persona obtenerCoordinadorRequisitosProyecto(Long idProyecto) {
		Persona coordinador = null;
		Session session;
		Statement sentencia = null;
		session = getSession();
		String query = "SELECT per.per_id, " + "per.tdo_id, " + "per.per_apellido1, " + "per.per_apellido2, "
				+ "per.per_nombre1, " + "per.per_nombre2, " + "per.per_email," + "ii.ini_textension "
				+ "FROM her_persona per left join her_investigador_interno ii on"
				+ " per.per_id = ii.inv_id and per.tdo_id = ii.tdo_id "
				+ "WHERE per.tdo_id||per.per_id = (SELECT sp.tdo_id2||sp.per_revision "
				+ "FROM her_seg_proyecto_persona sp " + "WHERE sp.per_revision not in "+DOCUMENTOS_PERSONAS_PRUEBAS+" and sp.pry_id = " + idProyecto.longValue() + ")";

		try {
			sentencia = session.connection().createStatement();
			sentencia.execute(query);
			ResultSet rs = sentencia.getResultSet();

			if (rs.next()) {
				coordinador = new Persona();
				IdPersona idPersona = new IdPersona();
				idPersona.setDocumento(rs.getString(1));
				idPersona.setTipoDocumento(rs.getString(2));
				coordinador.setId(idPersona);
				coordinador.setApellido1(rs.getString(3));
				coordinador.setApellido2(rs.getString(4));
				coordinador.setNombre1(rs.getString(5));
				coordinador.setNombre2(rs.getString(6));
				coordinador.setEmail(rs.getString(7));
				coordinador.setTelefono(rs.getString(8));
			}
		} catch (Exception ex) {
		} finally {
			if (session.isOpen())
				session.close();
		}

		return coordinador;
	}
	
	public Persona obtenerCoordinadorRequisitosProyectoEditorial(Long idProyecto) {
		Persona coordinador = null;
		Session session;
		Statement sentencia = null;
		session = getSession();
		String query = "SELECT per.per_id, " + "per.tdo_id, " + "per.per_apellido1, " + "per.per_apellido2, "
				+ "per.per_nombre1, " + "per.per_nombre2, " + "per.per_email," + "ii.ini_textension "
				+ "FROM her_persona per left join her_investigador_interno ii on"
				+ " per.per_id = ii.inv_id and per.tdo_id = ii.tdo_id "
				+ "WHERE per.tdo_id||per.per_id = (SELECT sp.tdo_id2||sp.per_revision "
				+ "FROM HER_PROYECTO_COORDINADOR_ED sp " + "WHERE sp.per_revision not in "+DOCUMENTOS_PERSONAS_PRUEBAS+" and sp.pry_id = " + idProyecto.longValue() + ")";

		try {
			sentencia = session.connection().createStatement();
			sentencia.execute(query);
			ResultSet rs = sentencia.getResultSet();

			if (rs.next()) {
				coordinador = new Persona();
				IdPersona idPersona = new IdPersona();
				idPersona.setDocumento(rs.getString(1));
				idPersona.setTipoDocumento(rs.getString(2));
				coordinador.setId(idPersona);
				coordinador.setApellido1(rs.getString(3));
				coordinador.setApellido2(rs.getString(4));
				coordinador.setNombre1(rs.getString(5));
				coordinador.setNombre2(rs.getString(6));
				coordinador.setEmail(rs.getString(7));
				coordinador.setTelefono(rs.getString(8));
			}
		} catch (Exception ex) {
		} finally {
			if (session.isOpen())
				session.close();
		}

		return coordinador;
	}

	public Long obtenerMontoContrapartidaPersonal(Long idProyecto) {
		Long monto = new Long(0);
		Session session = getSession();
		String query = "select sum(totales.TOTALactual)+sum(totales.totalpro)+sum(totales.totalcambios) as contrapartidaPersonal\r\n"
				+ "from(\r\n"
				+ "select sum(ip.inp_valor_pagar)AS TOTALactual, 0 totalpro, 0 totalcambios\r\n"
				+ "from her_investigador_proyecto ip \r\n"
				+ "where ip.pry_id = "+idProyecto.toString()+"\r\n"
				+ "group by ip.pry_id\r\n"
				+ "union\r\n"
				+ "select 0 AS TOTALactual, 0 totalpro, sum(si.sin_valor_pagar) totalcambios\r\n"
				+ "from her_solicitud s,\r\n"
				+ "her_solicitud_investigador si\r\n"
				+ "where s.pry_id = "+idProyecto.toString()+" and s.sol_id = si.sol_id and s.sol_respuesta in ('A') and s.tso_id in ('5','24') and si.sin_estado not in ('B','N') and si.sin_tipo_solicitud in ('E')\r\n"
				+ "group by s.pry_id\r\n"
				+ "union\r\n"
				+ "select 0 AS TOTALactual, sum(spi.spi_valor_pagar) totalpro, 0 totalcambios\r\n"
				+ "from her_solicitud s,\r\n"
				+ "her_solicitud_prorroga_inv spi\r\n"
				+ "where s.pry_id = "+idProyecto.toString()+" and s.sol_id = spi.sol_id and s.sol_respuesta in ('A') and s.tso_id in ('22') and spi.spi_estado not in ('B','N')\r\n"
				+ "group by s.pry_id)totales";
				
		Statement sentencia = null;
		ResultSet resultado = null;

		try {
			sentencia = session.connection().createStatement();
			sentencia.execute(query);
			resultado = sentencia.getResultSet();

			if (resultado.next()) {
				monto = new Long(resultado.getLong(1));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return monto;
	}
	
	public Long obtenerMontoAprobadoProyecto(Long idProyecto, String tipoFinanciacion) {
		Long monto = new Long(0);
		Session session = getSession();
		String nombreCampoProyecto;
		if (tipoFinanciacion.equals("MINIMA")) {
			nombreCampoProyecto = "SELECT SUM (gst_valor) + SUM (gst_valor2) + SUM (gst_valor3) + SUM (gst_valor4) + SUM (gst_valor5) + SUM (gst_valor6) ";
		} else {
			nombreCampoProyecto = "SELECT SUM (gst_valor) ";
		}
		String complementoConsulta = "FROM her_gasto gst, " + "her_financiacion fin, her_fuente_financiacion ff, her_tipo_rubro tp "
				+ "WHERE gst.fin_id = fin.fin_id " + "AND fin.pry_id = " + idProyecto.longValue() + " and tp.tru_id = gst.tru_id and tp.TRU_CONTRAPARTIDA not in ('1') "
				+ "AND fin.ffi_id = ff.ffi_id and ff.ffi_interna_externa in ('I','O') and (fin.FIN_ES_LEGALIZACION IS NULL OR fin.FIN_ES_LEGALIZACION = 'NO')";
		Statement sentencia = null;
		ResultSet resultadoNoFicha = null;

		String queryFicha = nombreCampoProyecto + complementoConsulta;

		try {
			sentencia = session.connection().createStatement();
			sentencia.execute(queryFicha);
			resultadoNoFicha = sentencia.getResultSet();

			if (resultadoNoFicha.next()) {
				monto = new Long(resultadoNoFicha.getLong(1));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return monto;
	}

	public Long obtenerMontoAprobadoProyectoLaboratorios(Long idProyecto, Long idModalidad) {
		Long monto = new Long(0);
		Session session = getSession();
		Statement sentencia = null;
		ResultSet resultado = null;
		String query = "SELECT SUM(gst.gst_valor2) FROM her_gasto gst, her_financiacion fin "
				+ "WHERE gst.fin_id = fin.fin_id " + "AND fin.pry_id = " + idProyecto.longValue();
		System.out.println("obtenerMontoAprobadoProyectoLaboratorios: " + query);
		try {
			sentencia = session.connection().createStatement();
			sentencia.execute(query);
			resultado = sentencia.getResultSet();

			if (resultado.next())
				monto = new Long(resultado.getLong(1));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return monto;
	}

	public Date obtenerFechaInicioProyecto(Long idProyecto) {
		Date fechaInicio = null;
		Session session;
		Statement sentencia = null;
		session = getSession();
		String query = "SELECT MIN(hep_fecha) " + "FROM her_historico_estado_proyecto " + "WHERE epr_id = 'A' "
				+ "AND pry_id = " + idProyecto.longValue();

		try {
			sentencia = session.connection().createStatement();
			sentencia.execute(query);
			ResultSet rs = sentencia.getResultSet();

			if (rs.next()) {
				fechaInicio = rs.getDate(1);
			}
		} catch (Exception ex) {
		} finally {
			if (session.isOpen())
				session.close();
		}

		return fechaInicio;
	}

	public boolean tieneProyectosRefinanciar(IdPersona idInvestigador) {
		boolean proyectosFinanciados = false;
		Session session = getSession();
		Statement sentencia = null;
		ResultSet resultado = null;
		String query = "SELECT 1 " + "FROM her_proyecto_refi " + "WHERE per_id = '" + idInvestigador.getDocumento()
				+ "' " + "AND tdo_id = '" + idInvestigador.getTipoDocumento() + "'";
		try {
			sentencia = session.connection().createStatement();
			sentencia.execute(query);
			resultado = sentencia.getResultSet();

			if (resultado.next())
				proyectosFinanciados = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return proyectosFinanciados;
	}

	public List obtenerProyectosRefinanciar(IdPersona idInvestigador) {
		List proyectos = null;
		Session session;
		Statement sentencia = null;

		String query = "SELECT pry.pry_id codigo, " + "pry.pry_nombre nombre " + "FROM her_proyecto pry "
				+ "WHERE pry_id = ANY(SELECT pry_id " + "FROM her_proyecto_refi " + "WHERE per_id = '"
				+ idInvestigador.getDocumento() + "' " + "AND tdo_id = '" + idInvestigador.getTipoDocumento() + "')";

		proyectos = new ArrayList();
		session = getSession();

		try {
			sentencia = session.connection().createStatement();
			sentencia.execute(query);
			ResultSet rs = sentencia.getResultSet();
			while (rs.next()) {
				Proyecto proyecto = new Proyecto();
				proyecto.setId(new Long(rs.getLong(1)));
				proyecto.setNombre(rs.getString(2));
				proyectos.add(proyecto);
			}
		} catch (Exception e) {
			proyectos = null;
			e.printStackTrace();
		} finally {
			session.close();
		}

		return proyectos;
	}

	// CREADA POR ING. LILIANA OLARTE
	public List obtenerProyectosCoordinadorRevisionxModalidadSede(Modalidad mod, Dependencia dep, Persona pep)
			throws DataAccessException {
		List proyectos;
		List result = new ArrayList();

		Session session = getSession();

		String query = "select p.pry_id as id, p.pry_nombre as nombre, pp.per_id as perId, PP.TDO_ID as tdoId, f.DPN_ID as idFacultad, f.DPN_NOMBRE as facultad "
				+ "from her_proyecto p, her_seg_proyecto_persona pp, her_investigador_proyecto ip, her_investigador_interno ii, her_dependencia d, her_dependencia f "
				+ "where p.MOD_ID = " + mod.getId().toString() + " and pp.PRY_ID (+)= p.PRY_ID and p.EPR_ID= 'P'"
				+ "and p.PRY_ID = ip.PRY_ID(+) and ip.INV_ID = ii.INV_ID(+) and ip.TDO_ID = ii.TDO_ID(+) and ip.INP_TIPO(+) = 'P' "
				+ "and ii.DPN_ID = d.DPN_ID(+) " + "and d.DPN_FACULTAD  = f.DPN_id(+) " + "and pp.PER_REVISION  =  '"
				+ pep.getId().getDocumento() + "' " + "and pp.TDO_ID2  =  '" + pep.getId().getTipoDocumento() + "' "
				+ "order by f.DPN_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.STRING).addScalar("facultad", Hibernate.STRING).list();

		session.close();

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinador pc = new ProyectoCoordinador();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			pc.setIdFacultad((String) name[4]);
			pc.setNombreFacultad((String) name[5]);
			result.add(pc);
		}
		return result;
	}
	
	public List obtenerProyectosCoordinadorEditorialRevisionxModalidadSede(Modalidad mod, Dependencia dep, Persona pep)
			throws DataAccessException {
		List proyectos;
		List result = new ArrayList();

		Session session = getSession();

		String query = "select p.pry_id as id, p.pry_nombre as nombre, pp.per_id_SEG as perId, PP.TDO_ID_SEG as tdoId, f.DPN_ID as idFacultad, f.DPN_NOMBRE as facultad "
				+ "from her_proyecto p, HER_PROYECTO_COORDINADOR_ED pp, her_investigador_proyecto ip, her_investigador_interno ii, her_dependencia d, her_dependencia f "
				+ "where p.MOD_ID = " + mod.getId().toString() + " and pp.PRY_ID (+)= p.PRY_ID and p.EPR_ID= 'P'"
				+ "and p.PRY_ID = ip.PRY_ID(+) and ip.INV_ID = ii.INV_ID(+) and ip.TDO_ID = ii.TDO_ID(+) and ip.INP_TIPO(+) = 'P' "
				+ "and ii.DPN_ID = d.DPN_ID(+) " + "and d.DPN_FACULTAD  = f.DPN_id(+) " + "and pp.PER_ID_REQ  =  '"
				+ pep.getId().getDocumento() + "' " + "and pp.TDO_ID_REQ  =  '" + pep.getId().getTipoDocumento() + "' "
				+ "order by f.DPN_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("perId", Hibernate.STRING).addScalar("tdoId", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.STRING).addScalar("facultad", Hibernate.STRING).list();

		session.close();

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoCoordinadorEditorial pc = new ProyectoCoordinadorEditorial();
			pc.setIdProyecto((Long) name[0]);
			pc.setNombreProyecto((String) name[1]);
			pc.setPerId((String) name[2]);
			pc.setTdoId((String) name[3]);
			pc.setIdFacultad((String) name[4]);
			pc.setNombreFacultad((String) name[5]);
			result.add(pc);
		}
		return result;
	}

	// CREADA POR ING. LILIANA OLARTE
	public ProyectoCoordinador obtenerProyectoCoordinadorxCodigo(String codigo) {

		ProyectoCoordinador proyecto;
		Session session;
		Statement sentencia = null;

		String query = "select p.pry_id, p.pry_nombre as nombre, "
				+ " pp.per_id as perId, PP.TDO_ID as tdoId, pp.per_revision as "
				+ " perRevision, PP.TDO_ID2 as tdoId2, pp.per_evaluacion as "
				+ " perEvaluacion, PP.TDO_ID3 as tdoId3,f.DPN_ID as idFacultad, " + " f.DPN_NOMBRE as facultad,"
				+ "per_seg.per_nombre1 || ' ' || per_seg.per_nombre2 || ' ' ||"
				+ "per_seg.per_apellido1 || ' ' || per_seg.per_apellido2 nombreSeguimiento, "
				+ "per_req.per_nombre1 || ' ' || per_req.per_nombre2 || ' ' ||"
				+ "per_req.per_apellido1 || ' ' || per_req.per_apellido2 nombreERequisitos, "
				+ "per_eva.per_nombre1 || ' ' || per_eva.per_nombre2 || ' ' ||"
				+ "per_eva.per_apellido1 || ' ' || per_eva.per_apellido2 nombreEvaluacion " + " from  "
				+ " her_investigador_proyecto ip left join her_investigador_interno ii on ip.INP_TIPO='P' "
				+ "and ip.INV_ID =ii.INV_ID and ip.TDO_ID =ii.TDO_ID left join her_dependencia d on ii.DPN_ID = d.DPN_ID "
				+ "LEFT join her_dependencia f on d.DPN_FACULTAD  = f.DPN_id ,her_proyecto p left join "
				+ "her_seg_proyecto_persona pp on " + " p.PRY_ID=pp.PRY_ID" + " left join her_persona per_seg "
				+ " on pp.per_id = per_seg.per_id and pp.tdo_id = per_seg.tdo_id" + " left join her_persona per_req "
				+ " on pp.per_Revision = per_req.per_id and pp.tdo_id2 = per_req.tdo_id"
				+ " left join her_persona per_eva "
				+ " on pp.per_Evaluacion = per_eva.per_id and pp.tdo_id3 = per_eva.tdo_id" + " WHERE p.pry_id = '"
				+ codigo.toString() + "'" + " and p.PRY_ID=ip.PRY_ID order by f.DPN_ID";
		System.out.println(query);
		proyecto = new ProyectoCoordinador();
		session = getSession();

		try {
			sentencia = session.connection().createStatement();
			sentencia.execute(query);
			ResultSet rs = sentencia.getResultSet();

			while (rs.next()) {
				proyecto.setIdProyecto(new Long(rs.getLong(1)));
				proyecto.setNombreProyecto(rs.getString(2));
				proyecto.setPerId(rs.getString(3));
				proyecto.setTdoId(rs.getString(4));
				proyecto.setPerRevision(rs.getString(5));
				proyecto.setTdoId2(rs.getString(6));
				proyecto.setPerEvaluacion(rs.getString(7));
				proyecto.setTdoId3(rs.getString(8));
				proyecto.setIdFacultad(rs.getString(9));
				proyecto.setNombreFacultad(rs.getString(10));
				proyecto.setNombreSeguimiento(rs.getString(11));
				proyecto.setNombreRevision(rs.getString(12));
				proyecto.setNombreEvaluacion(rs.getString(13));
			}
		} catch (Exception e) {
			proyecto = null;
			e.printStackTrace();
		} finally {
			session.close();
		}

		return proyecto;
	}
	
	public ProyectoCoordinadorEditorial obtenerProyectoCoordinadorEditorialCodigo(String codigo) {

		ProyectoCoordinadorEditorial proyecto;
		Session session;
		Statement sentencia = null;

		String query = "select p.pry_id, p.pry_nombre as nombre, "
				+ " pce.per_id_seg as perId, pce.TDO_ID_seg as tdoId, pce.per_id_req as "
				+ " perRevision, pce.TDO_ID_req as tdoId2, pce.per_id_ev as "
				+ " perEvaluacion, Pce.TDO_ID_ev as tdoId3,f.DPN_ID as idFacultad, " + " f.DPN_NOMBRE as facultad,"
				+ "per_seg.per_nombre1 || ' ' || per_seg.per_nombre2 || ' ' ||"
				+ "per_seg.per_apellido1 || ' ' || per_seg.per_apellido2 nombreSeguimiento, "
				+ "per_req.per_nombre1 || ' ' || per_req.per_nombre2 || ' ' ||"
				+ "per_req.per_apellido1 || ' ' || per_req.per_apellido2 nombreERequisitos, "
				+ "per_eva.per_nombre1 || ' ' || per_eva.per_nombre2 || ' ' ||"
				+ "per_eva.per_apellido1 || ' ' || per_eva.per_apellido2 nombreEvaluacion " + " from  "
				+ " her_investigador_proyecto ip left join her_investigador_interno ii on ip.INP_TIPO='P' "
				+ "and ip.INV_ID =ii.INV_ID and ip.TDO_ID =ii.TDO_ID left join her_dependencia d on ii.DPN_ID = d.DPN_ID "
				+ "LEFT join her_dependencia f on d.DPN_FACULTAD  = f.DPN_id ,her_proyecto p left join "
				+ "HER_PROYECTO_COORDINADOR_ED pce on " + " p.PRY_ID=pce.PRY_ID" + " left join her_persona per_seg "
				+ " on pce.per_id_seg = per_seg.per_id and pce.tdo_id_seg = per_seg.tdo_id" + " left join her_persona per_req "
				+ " on pce.per_id_req = per_req.per_id and pce.tdo_id_req = per_req.tdo_id"
				+ " left join her_persona per_eva "
				+ " on pce.per_id_ev = per_eva.per_id and pce.tdo_id_ev = per_eva.tdo_id" + " WHERE p.pry_id = '"
				+ codigo.toString() + "'" + " and p.PRY_ID=ip.PRY_ID order by f.DPN_ID";
		System.out.println(query);
		proyecto = new ProyectoCoordinadorEditorial();
		session = getSession();

		try {
			sentencia = session.connection().createStatement();
			sentencia.execute(query);
			ResultSet rs = sentencia.getResultSet();

			while (rs.next()) {
				proyecto.setIdProyecto(new Long(rs.getLong(1)));
				proyecto.setNombreProyecto(rs.getString(2));
				proyecto.setPerId(rs.getString(3));
				proyecto.setTdoId(rs.getString(4));
				proyecto.setPerRevision(rs.getString(5));
				proyecto.setTdoId2(rs.getString(6));
				proyecto.setPerEvaluacion(rs.getString(7));
				proyecto.setTdoId3(rs.getString(8));
				proyecto.setIdFacultad(rs.getString(9));
				proyecto.setNombreFacultad(rs.getString(10));
				proyecto.setNombreSeguimiento(rs.getString(11));
				proyecto.setNombreRevision(rs.getString(12));
				proyecto.setNombreEvaluacion(rs.getString(13));
			}
		} catch (Exception e) {
			proyecto = null;
			e.printStackTrace();
		} finally {
			session.close();
		}

		return proyecto;
	}

	public List<ConvocatoriaExterna> obtenerConvocatorias(String sqlInternas, String sqlExternas)
			throws DataAccessException {
		List<Object> convocatorias;
		List<Object> convocatorias2;
		List<ConvocatoriaExterna> resultado = new ArrayList<ConvocatoriaExterna>();

		if (sqlInternas.length() > 0) {
			sqlInternas += " and ";

			Session session = getSession();

			String query = "select distinct * from (SELECT HER_CONVOCATORIA_PADRE.CNP_ID as idConvocatoria, CNP_TITULO as tituloConvocatoria, CNP_ESTADO as estadoConvocatoria,"
					+ " HER_DEPENDENCIA.DPN_NOMBRE as nombreDependenciaConvocatoria, HER_CONVOCATORIA_PADRE.CNP_VINCULO_CONVOCATORIA as vinculo"
					+ " FROM HER_CONVOCATORIA_PADRE, HER_DEPENDENCIA " + " where " + sqlInternas
					+ " (CNP_ESTADO = 'A' or CNP_ESTADO = 'I')"
					+ " and HER_DEPENDENCIA.DPN_ID = HER_CONVOCATORIA_PADRE.DNP_ID) ORDER BY idConvocatoria DESC ";

			SQLQuery sqlQuery = session.createSQLQuery(query);
			convocatorias = sqlQuery.addScalar("idConvocatoria", Hibernate.LONG)
					.addScalar("tituloConvocatoria", Hibernate.STRING).addScalar("estadoConvocatoria", Hibernate.STRING)
					.addScalar("nombreDependenciaConvocatoria", Hibernate.STRING).addScalar("vinculo", Hibernate.STRING)
					.list();

			session.close();

			Iterator<Object> it = convocatorias.iterator();

			while (it.hasNext()) {

				Object[] name = (Object[]) it.next();

				ConvocatoriaExterna convocatoria = new ConvocatoriaExterna();

				convocatoria.setId((Long) name[0]);
				String nombre = (String) name[1];
				nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1, nombre.length()).toUpperCase();

				convocatoria.setNombre(nombre);

				String estado = ((String) name[2]);
				if (estado.equals("A")) {
					convocatoria.setEstado("Activa");
				} else
					convocatoria.setEstado("Inactiva");

				convocatoria.setVinculo((String) name[4]);

				FuenteFinanciacion fuente = new FuenteFinanciacion();
				fuente.setDescripcion((String) name[3]);
				convocatoria.setEntidad(fuente);
				convocatoria.setTipo("I");
				resultado.add(convocatoria);
			}

		}

		if (sqlExternas.length() > 0) {
			//sqlExternas += " and ";

			Session session2 = getSession();

			String query2 = "SELECT DISTINCT CEX_ID as idConvocatoria, CEX_NOMBRE as tituloConvocatoria, CEX_ESTADO as estadoConvocatoria,"
					+ " FFI_DESCRIPCION as nombreEntidadConvocatoria, CEX_NUMERO as numConvocatoria"
					+ " FROM HER_CONVOCATORIA_EXTERNA ,HER_FUENTE_FINANCIACION WHERE "
					+ " HER_FUENTE_FINANCIACION.FFI_ID = HER_CONVOCATORIA_EXTERNA.FFI_ID"
					+ " and CEX_ID <> '0' and CEX_ESTADO IN ('A','I') and ("
					+ sqlExternas
					+ ") ORDER BY idConvocatoria DESC";

			SQLQuery sqlQuery2 = session2.createSQLQuery(query2);
			convocatorias2 = sqlQuery2.addScalar("idConvocatoria", Hibernate.LONG)
					.addScalar("tituloConvocatoria", Hibernate.STRING).addScalar("estadoConvocatoria", Hibernate.STRING)
					.addScalar("nombreEntidadConvocatoria", Hibernate.STRING)
					.addScalar("numConvocatoria", Hibernate.STRING).list();

			session2.close();

			Iterator<Object> it2 = convocatorias2.iterator();

			while (it2.hasNext()) {

				Object[] name = (Object[]) it2.next();

				ConvocatoriaExterna convocatoria = new ConvocatoriaExterna();

				convocatoria.setId((Long) name[0]);
				String nombre = (String) name[1];
				nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1, nombre.length()).toUpperCase();

				convocatoria.setNombre(nombre);

				String estado = ((String) name[2]);
				if (estado.equals("A")) {
					convocatoria.setEstado("Activa");
				} else
					convocatoria.setEstado("Inactiva");

				FuenteFinanciacion fuente = new FuenteFinanciacion();
				fuente.setDescripcion((String) name[3]);
				convocatoria.setEntidad(fuente);
				convocatoria.setTipo("E");
				convocatoria.setVinculo(null);

				String numConv = (String) name[4];
				convocatoria.setNumero(numConv);

				resultado.add(convocatoria);
			}
		}

		return resultado;
	}

	public List<Proyecto> obtenerProyectoBuscador(String sql, String facultad, String sedep, String investigador,
			String area, String palabra) throws DataAccessException {

		List<Object> proyectos;
		List<Proyecto> resultado = new ArrayList<Proyecto>();
		List proyectosExtension;

		Session session = getSession();

		session.beginTransaction();
		if (sql.length() > 0) {
			sql += " and ";
		}
		String query = "select p.pry_id as idProyecto, p.pry_nombre as nombreProyecto, d.DPN_NOMBRE as dependencia, "
				+ "s.SED_NOMBRE as sede, ep.EPR_NOMBRE as estado, p.pry_tipo_actividad as tipo "
				+ "from her_proyecto p, her_investigador_proyecto ip, her_investigador_interno ii,  her_persona inves,"
				+ "her_dependencia d, her_sede s, her_estado_proyecto ep, " + "her_modalidad m "
				+ "where ii.TDO_ID = inves.TDO_ID and ip.TDO_ID = ii.TDO_ID and ii.INV_ID = inves.PER_ID and " + sql +

				"p.PRY_ID = ip.PRY_ID and ip.INV_ID = ii.INV_ID and ip.INP_TIPO = 'P' " + "and ii.DPN_ID = d.DPN_ID "
				+ "and p.MOD_ID = m.MOD_ID " + "and d.SED_ID  = s.SED_ID "
				+ "and p.EPR_ID  = ep.EPR_ID and ip.INV_ID not in " + DOCUMENTOS_PERSONAS_PRUEBAS;

		query = query + " and ep.EPR_ID in ('AP','A','F')";
		query = query + " order by p.pry_nombre asc";

		SQLQuery sqlQuery = session.createSQLQuery(query);

		proyectos = sqlQuery.addScalar("idProyecto", Hibernate.LONG).addScalar("nombreProyecto", Hibernate.STRING)
				.addScalar("dependencia", Hibernate.STRING).addScalar("sede", Hibernate.STRING)
				.addScalar("estado", Hibernate.STRING).addScalar("tipo", Hibernate.STRING).list();

		session.close();
		Session session2 = getSession();
		try {

			String query2 = "select p.id_proyecto as idProyecto, p.nombre as nombreProyecto, p.uab as uab, "
					+ "p.sede as sede, p.estado as estado " + "from proyectos_ext@extension p ";

			if ((palabra != null && !palabra.equals("")) || (facultad != null && !facultad.equals(""))
					|| (sedep != null && !sedep.equals("")) || (investigador != null && !investigador.equals(""))
					|| (area != null && !area.equals(""))) {
				query2 = query2 + "where";
			}

			if (palabra != null && !palabra.equals("")) {
				query2 = query2 + " upper(p.nombre) like '%" + palabra + "%' or upper(p.objeto) like '%" + palabra
						+ "%'";
				if ((facultad != null && !facultad.equals("")) || (sedep != null && !sedep.equals(""))
						|| (investigador != null && !investigador.equals("")) || (area != null && !area.equals(""))) {
					query2 = query2 + " or ";
				}
			}
			if (facultad != null && !facultad.equals("")) {
				query2 = query2 + " upper(p.facultad) like '%" + facultad + "%' ";
				if ((sedep != null && !sedep.equals("")) || (investigador != null && !investigador.equals(""))
						|| (area != null && !area.equals(""))) {
					query2 = query2 + " or ";
				}
			}
			if (sedep != null && !sedep.equals("")) {
				query2 = query2 + " upper(p.sede) like '%" + sedep + "%'";
				if ((investigador != null && !investigador.equals("")) || (area != null && !area.equals(""))) {
					query2 = query2 + " or ";
				}
			}
			if (investigador != null && !investigador.equals("")) {
				query2 = query2 + " upper(p.responsable) like '%" + investigador + "%'";
				if ((area != null && !area.equals(""))) {
					query2 = query2 + " or ";
				}
			}
			if (area != null && !area.equals("")) {
				query2 = query2 + " upper(p.nombre) like '%" + area + "%' or upper(p.objeto) like '%" + area + "%'";
			}

			query2 = query2 + " order by nombreProyecto asc";

			SQLQuery sqlQuery2 = session2.createSQLQuery(query2);

			proyectosExtension = sqlQuery2.addScalar("idProyecto", Hibernate.LONG)
					.addScalar("nombreProyecto", Hibernate.STRING).addScalar("uab", Hibernate.STRING)
					.addScalar("sede", Hibernate.STRING).addScalar("estado", Hibernate.STRING).list();

			session2.close();

			Iterator<Object> it2 = proyectosExtension.iterator();

			while (it2.hasNext()) {
				Object[] name = (Object[]) it2.next();
				Proyecto proyecto = new Proyecto();
				Dependencia dependencia = new Dependencia();
				Sede sede = new Sede();
				EstadoProyecto estado = new EstadoProyecto();

				proyecto.setId((Long) name[0]);
				proyecto.setNombre((String) name[1]);
				dependencia.setNombre(((String) name[2]).trim());
				sede.setNombre((String) name[3]);
				estado.setNombre((String) name[4]);

				dependencia.setSede(sede);
				proyecto.setDependenciaPrincipal(dependencia);
				proyecto.setEstadoProyecto(estado);
				proyecto.setEsExtension(true);
				proyecto.setEsInvestigacion(false);
				proyecto.setAntecedentes("1");

				resultado.add(proyecto);
			}

		} catch (Exception e) {
			session2.close();
		}

		Iterator<Object> it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Proyecto proyecto = new Proyecto();
			Dependencia dependencia = new Dependencia();
			Sede sede = new Sede();
			EstadoProyecto estado = new EstadoProyecto();

			proyecto.setId((Long) name[0]);
			proyecto.setNombre(((String) name[1]).trim());
			dependencia.setNombre(((String) name[2]).trim());
			sede.setNombre((String) name[3]);
			estado.setNombre((String) name[4]);

			dependencia.setSede(sede);
			proyecto.setDependenciaPrincipal(dependencia);
			proyecto.setEstadoProyecto(estado);

			if ((String) name[5] == null) {
				proyecto.setEsInvestigacion(true);
				proyecto.setEsExtension(false);
			} else {
				if (((String) name[5]).equals("FM_CI") || ((String) name[5]).equals("FM_CA")
						|| ((String) name[5]).equals("FM_ECP") || ((String) name[5]).equals("FM_ES")
						|| ((String) name[5]).equals("FM_PINN") || ((String) name[5]).equals("FM_C")
						|| ((String) name[5]).equals("FM_SE")) {
					proyecto.setEsInvestigacion(false);
					proyecto.setEsExtension(true);
				} else {
					proyecto.setEsInvestigacion(true);
					proyecto.setEsExtension(false);
				}
			}
			proyecto.setAntecedentes("0");
			resultado.add(proyecto);
		}

		return resultado;
	}

	public void imprimirReporteProyecto(Proyecto proyectoActual, HttpSession sesion, Boolean esEvaluador) {

		ReporteBirt r = new ReporteBirt();

		boolean formato_proyecto = false;
		boolean formato_funcytca = false;
		boolean formato_evento = false;
		boolean formato_articulo_libro = false;
		boolean formato_alianzas = false;
		boolean formato_ecosistemas_colciencias = false;

		boolean ReporteConvocatoriaEscuelaInternacional = false;
		boolean ReportePermisoMarco = false;

		boolean ReporteSolicitudISBN = false;
		boolean ReporteProyectoLaboratorios = false;
		boolean ReporteProyectoTesisPosgrado = false;
		boolean ReportePropuestaAlianzas = false;
		boolean reportePropuestaAlianzas2019 = false;

		if (proyectoActual.getModalidad() instanceof Convocatoria) {

			// Reporte Generico Ficha Mínima
			if (proyectoActual.getModalidad().getId() == 2 || proyectoActual.getModalidad().getId() == 10) {
				formato_proyecto = true;
			} else {

				Convocatoria convocatoria = (Convocatoria) proyectoActual.getModalidad();
				RestriccionConvocatoria r2 = convocatoria.getRestriccion();

				if (convocatoria.getTipo().getId().compareTo("FMH") == 0 && r2 != null
						&& (r2.getId().equals("CONV_086_DERECHO") || r2.getId().equals("CONV_MED_LEGAL")
								|| r2.getId().equals(RestriccionConvocatoria.CONV_MED_INMLCF_2)
								|| r2.getId().equals("CONV_DER") || r2.getId().equals("CONV_PROY_2016_2018")
								|| r2.getId().equals("CONV_ODON_2015") || r2.getId().equals("CONV_IEU")
								|| r2.getId().equals(RestriccionConvocatoria.CONV_INNO_PEDAGOGICA_BOG_2015)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_ADMON_MAN_2015)
								|| r2.getId().equals("CONV_BEJARANO")
								|| r2.getId().equals(RestriccionConvocatoria.CONV_ART_2015_2_MD)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_TRAS_MED)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_TRAS_MED_MOD2)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_UNIJUS_2014)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_FM_GENERICO)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_IEU_1_2016)
								|| r2.getId().equals(RestriccionConvocatoria.CON_HUM_MAN_2016)
								|| r2.getId().equals(RestriccionConvocatoria.CON_BIO_MAN_2016)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_ODON_2016)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_MAESTROS_ART)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_FCH_OFB_3)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_FCH_OFB_4)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_FCH_OFB_1)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_FCH_OFB_2)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_ART_2015_2)
								|| r2.getId().equals("CONV_ART_2014") || r2.getId().equals("CONV_ART_2014_MD")
								|| r2.getId().equals("FMHP")
								|| r2.getId().equals(RestriccionConvocatoria.CONV_GRU_CARIBE)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_GRUPOS_ARQUITECTURA_MEDELLIN)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_GRU_CIEN_MED)
								|| r2.getId().equals("CONV_CONJ_MAN")
								|| r2.getId().equals(RestriccionConvocatoria.CONV_MAN_INV_APL)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_MAN_CIEN_BA)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_MAN_CIEN_SO)
								|| r2.getId().equals(RestriccionConvocatoria.CON_INNO_PED_DIEB_2)
								|| r2.getId().equals(RestriccionConvocatoria.CONV_CUND))) {
					formato_proyecto = true;
				} else if (convocatoria.getTipo().getId().compareTo("CFM") == 0 && r2 != null
						&& (r2.getId().equals("CONV_ADMON_MAN") || r2.getId().equals("CONV_INNO")
								|| r2.getId().equals("CONV_INNO_MOD_2_3") || r2.getId().equals("CONV_POSG_3")
								|| r2.getId().equals("CONV_POSG") || r2.getId().equals("CONV_INI")
								|| r2.getId().equals("MAT_SIA"))) {
					formato_proyecto = true;
				} else if (proyectoActual.getModalidad().getTipo().getId()
						.equals(TipoModalidad.REGISTRO_PROYECTOS_LABORATORIOS)) {
					ReporteProyectoLaboratorios = true;
				} else if (r2 != null && r2.getId().equals(RestriccionConvocatoria.CONV_ALI_NAL_2018)) {
					ReportePropuestaAlianzas = true;
				}else if (r2 != null && r2.getId().equals(RestriccionConvocatoria.CONV_ALI_NAL_2019) && convocatoria.getPadre().getAno().equals("2019")) {
					reportePropuestaAlianzas2019 = true;
				} else if (proyectoActual.getModalidad().getTipo().getId()
						.equals(TipoModalidad.CONVOCATORIA_PROYECTOS_TESIS_POSGRADO)) {
					ReporteProyectoTesisPosgrado = true;
				} else if (convocatoria.getTipo().getId().compareTo("CFM") == 0 && r2 != null
						&& (r2.getId().equals("EVENTOS_GENERICO_NACIONAL")
				        		|| r2.getId().equals("EVENTOS_GENERICO_INTERNACIONAL") || r2.getId().equals("CE1") || r2.getId().equals("CE2") || r2.getId().equals("CE1-N2017")
								|| r2.getId().equals("CE2-N2017") || r2.getId().equals("CONV_EVENT_2019_M1") || r2.getId().equals("CONV_EVENT_2019_M2") || r2.getId().equals("CONV_MOOTS_2019_M1") || r2.getId().equals("CONV_MOOTS_2019_M2")
								|| r2.getId().equals("CONV_MOOTS_2021_M1") || r2.getId().equals("CONV_MOOTS_2021_M2"))) {
					formato_evento = true;
				} else if (proyectoActual.getTipoInvestigacion() != null
						&& proyectoActual.getTipoInvestigacion().getId().equals(TipoInvestigacion.CREACION_ARTISTICA)) {
					formato_proyecto = true;
				} else if (convocatoria.getTipoFinanciacion() != null
						&& convocatoria.getTipoFinanciacion().equals(Convocatoria.SIN_TIPO_FINANCIACION)) {
					formato_proyecto = true;
				} else if (convocatoria.getTipo().getId().compareTo("ESI") == 0) {
					formato_proyecto = true;
				} else if (convocatoria.getTipo().getId().compareTo("ES7") == 0) {
					formato_proyecto = true;
				} else if (r2 != null && r2.getId().equals(RestriccionConvocatoria.CONV_EXT_SOL)) {
					formato_proyecto = true;
				} else if (convocatoria.getTipo().getId().compareTo("CJI") == 0 && r2 != null
						&& (r2.getId().equals("CONV_SEM_COL") || r2.getId().equals("CONV_JI_COL_2014")
								|| r2.getId().equals("CONV_JI_COL_2014") || r2.getId().equals("CONV_JI_COL"))) {
					formato_proyecto = true;
				} else if (convocatoria.getTipo().getId().compareTo("CCT") == 0 && r2 != null
						&& r2.getId().equals("CON_COR_TEC_AGRO")) {
					formato_proyecto = true;
				} else if (convocatoria.getTipo().getId().compareTo("CTV") == 0 && r2 != null
						&& r2.getId().equals("TIEMPO_VOLVER")) {
					formato_proyecto = true;
				} else if (convocatoria.getTipo().getId().compareTo("CEQ") == 0 && r2 != null
						&& r2.getId().equals("CONV_CA_EQUIPOS")) {
					formato_proyecto = true;
				} else if (convocatoria.getTipo().getId().compareTo("CMP") == 0 && r2 != null
						&& r2.getId().equals("CONV_MED_POSD")) {
					formato_proyecto = true;
				} else if (convocatoria.getTipo().getId().compareTo("CPU") == 0 && r2 != null
						&& r2.getId().equals("CONV_PRD")) {
					formato_proyecto = true;
				} else if (convocatoria.getTipo().getId().compareTo("CSF") == 0 && r2 != null
						&& r2.getId().equals("TIEMPO_VOLVER")) {
					formato_proyecto = true;
				} else if (convocatoria.getTipo().getId().compareTo("CBP") == 0) {
					formato_proyecto = true;
				} else if (convocatoria.getTipo().getId().compareTo("PN") == 0) {
					formato_proyecto = true;
				} else if (convocatoria.getTipo().getId().compareTo("CL") == 0
						|| convocatoria.getTipo().getId().compareTo("CLN") == 0) {
					formato_articulo_libro = true;
				} else if (proyectoActual.getModalidad().getId().equals(Convocatoria.ID_CONVOCATORIA_ALIANZAS)) {
					formato_alianzas = true;
				} else if (convocatoria.getTipo().getId().compareTo("ECO") == 0) {
					formato_ecosistemas_colciencias = true;
				}

			}
		}

		if (proyectoActual.getTipoActividad() != null && (proyectoActual.getTipoActividad().equals("523")
				|| proyectoActual.getTipoActividad().equals("524") || proyectoActual.getTipoActividad().equals("525")
				|| proyectoActual.getTipoActividad().equals("524-2"))) {
			formato_funcytca = true;
		}

		if (proyectoActual.getModalidad().getTipo().getId().equals("PM")
				|| proyectoActual.getModalidad().getTipo().getId().equals("PMA")) {
			ReportePermisoMarco = true;
		}

		if (proyectoActual.getModalidad().getTipo().getId().compareTo("CEI") == 0) {
			ReporteConvocatoriaEscuelaInternacional = true;
		}

		if (proyectoActual.getModalidad().getTipo().getId().compareTo("SIS") == 0) {
			ReporteSolicitudISBN = true;
		}

		if (proyectoActual.getModalidad().getTipo().getId().compareTo("CED") == 0) {
			RestriccionConvocatoria r2 = ((Convocatoria) proyectoActual.getModalidad()).getRestriccion();
			if (r2 != null && r2.getId().equals("CONV_DNIPI_2017")) {
				formato_proyecto = true;
			}
		}

		r.adicionarParametro("id", proyectoActual.getId().toString());
		r.setFormato(ReporteBirt.FORMATO_PDF);
		if (esEvaluador) {
			r.adicionarParametro("c", "s");
		} else {
			r.adicionarParametro("c", "i");
		}

		if (formato_proyecto) {
			r.setNombreReporte("/proyecto/formato-proyecto");
		} else if (formato_alianzas) {
			r.setNombreReporte("/proyecto/formato-alianzas");
		} else if (formato_ecosistemas_colciencias) {
			r.setNombreReporte("/proyecto/formato-ecosistemas-colciencias");
		} else if (formato_funcytca) {
			r.setNombreReporte("/proyecto/formato-funcytca");
		} else if (formato_articulo_libro) {
			r.setNombreReporte("/proyecto/formato-articulo-libro");
		} else if (formato_evento) {
			r.setNombreReporte("/proyecto/formato-evento");
		} else if (ReportePermisoMarco) {
			r.setNombreReporte("/proyecto/formato-permiso-marco");
		} else if (ReporteProyectoLaboratorios) {
			r.setNombreReporte("/proyecto/ReporteProyectoLaboratorios");
		} else if (ReportePropuestaAlianzas) {
			r.setNombreReporte("/proyecto/formato-propuesta-alianza");
		}else if (reportePropuestaAlianzas2019) {
			r.setNombreReporte("/proyecto/formato-propuesta-alianza-2019");
		} else if (ReporteProyectoTesisPosgrado) {
			r.setNombreReporte("/proyecto/formato-proyecto-tesis-posgrado");
		} else if (ReporteConvocatoriaEscuelaInternacional) {
			r.setNombreReporte("/proyecto/formato-escuela-internacional");
		} else if (ReporteSolicitudISBN) {
			r.setNombreReporte("/proyecto/ReporteSolicitudISBN");
		} else {
			r.setNombreReporte("/proyecto/formato-proyecto");
		}

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	public void imprimirReporteProyectoLegalizacion(Proyecto proyectoActual, HttpSession sesion) {
		Long id = proyectoActual.getId();
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", id.toString());
		r.setFormato(ReporteBirt.FORMATO_PDF);

		if (proyectoActual.getModalidad().isEsConvocatoriaLegalizacion()) {
			if (proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.ACTIVO)
					|| proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.APROBADO)
					|| proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.FINALIZADO)) {

				r.setNombreReporte("proyecto/FICHA_LEGALIZACION");
			} else {
				r.setNombreReporte("proyecto/FICHA_LEGALIZACION_NAP");
			}
		} else {
			r.setNombreReporte("proyecto/FICHA_FORMALIZACION");
		}

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	public String consultarProyectoGenerico(Proyecto proyecto, HttpSession sesion, Convocatoria conv) {

		sesion.setAttribute("proyecto", proyecto);
		sesion.removeAttribute("manejadorDatosBasicos");
		sesion.removeAttribute("esProyectoFichaMinimaNueva");
		sesion.removeAttribute("proyectoFichaMinimaNueva");

		Modalidad modPry = proyecto.getModalidad();

		if (modPry instanceof JornadaDocente) {
			return "consultarAdmProyecto";
		} else {
			if (proyecto.getModalidad().getId().toString().equals("2")) {
				sesion.removeAttribute("manejadorFichaMinimaHome");
				sesion.setAttribute("consultaFichaMinina", true);
				sesion.removeAttribute("idConvocatoriaActual");
				ProyectoVista proyectoVista2 = new ProyectoVista();
				proyectoVista2.setId(proyecto.getId());
				sesion.setAttribute("proyectoFichaMinina", proyectoVista2);
				return "fichaMinimaHome";
			} else if ((proyecto.getModalidad().getTipo().getId().compareTo("CFM") == 0)) {
				return "consultarAdmProyectoFichaMinima";
			} else if ((proyecto.getModalidad().getTipo().getId().compareTo("CL") == 0)
					|| (proyecto.getModalidad().getTipo().getId().compareTo("CLN") == 0)) {
				return "consultarAdmProyectoConvLibros";
			} else if ((proyecto.getModalidad().getTipo().getId().compareTo("CLN") == 0)) {
				return "irSolicitudLibrosConsultar";
			} else if ((proyecto.getModalidad().getTipo().getId().compareTo("CPU") == 0)) {
				sesion.removeAttribute("manejadorFichaMinimaHome");
				sesion.setAttribute("consultaFichaMinina", true);
				sesion.removeAttribute("idConvocatoriaActual");
				return "consultarAdmProyectoFichaMinimaPurdue";
			} else if ((proyecto.getModalidad().getTipo().getId().compareTo("CMP") == 0)
					|| (proyecto.getModalidad().getTipo().getId().compareTo("CEQ") == 0)) {
				return "consultarAdmProyectoFichaMinimaOtraConv";
			} else if ((proyecto.getModalidad().getTipo().getId().compareTo("CEI") == 0)) {
				return "consultarAdmProyectoEscuelaInternacional";
			} else if ((proyecto.getModalidad().getTipo().getId().compareTo("CBP") == 0)) {
				return "consultarAdmProyectoBanPro";
			} else if ((proyecto.getModalidad().getTipo().getId().compareTo("RFE") == 0)
					|| (proyecto.getModalidad().getTipo().getId().compareTo("SEB") == 0)
					|| (proyecto.getModalidad().getTipo().getId().compareTo("CTV") == 0)
					|| (proyecto.getModalidad().getTipo().getId().compareTo("BDC") == 0)
					|| (proyecto.getModalidad().getTipo().getId().compareTo("CSF") == 0)
					|| (proyecto.getModalidad().getTipo().getId().compareTo("FMH") == 0)
					|| (proyecto.getModalidad().getTipo().getId().compareTo("CTP") == 0)
					|| (proyecto.getModalidad().getTipo().getId().compareTo("CED") == 0)
					|| (proyecto.getModalidad().getTipo().getId().compareTo("ESI") == 0)
					|| (proyecto.getModalidad().getTipo().getId().compareTo("ES7") == 0)
					|| (proyecto.getModalidad().getTipo().getId().compareTo("CCT") == 0)
					|| (proyecto.getModalidad().getTipo().getId().compareTo("RPL") == 0)) {
				sesion.removeAttribute("manejadorFichaMinimaHome");
				sesion.setAttribute("consultaFichaMinina", true);
				sesion.removeAttribute("idConvocatoriaActual");
				return "consultarFichaMinimaHome";
			} else if ((proyecto.getModalidad().getTipo().getId().compareTo("CJI") == 0)) {
				ProyectoVista proyectoVista = new ProyectoVista();
				proyectoVista.setId(proyecto.getId());
				sesion.setAttribute("proyectoFichaMinina", proyectoVista);
				return "consultarFichaMinimaHome_JI_SEM";
			} else if ((proyecto.getModalidad().getTipo().getId().compareTo("PM") == 0)
					|| (proyecto.getModalidad().getTipo().getId().compareTo("PMA") == 0)) {
				sesion.setAttribute("idConvocatoriaActual", proyecto.getModalidad().getId());
				sesion.setAttribute("consultaPermisoMarco", true);
				return "irInformacionEspecificaMarcoConsulta";
			} else if ((proyecto.getModalidad().getTipo().getId().compareTo("SIS") == 0)) {
				return "irSolicitudISBNConsultar";
			} else {
				if (conv.getRestriccion() != null) {
					RestriccionConvocatoria resConv = conv.getRestriccion();
					if (resConv.getId().contains("VRI")) {
						return resConv.getNombre();
					} else {
						return "consultarAdmProyecto";
					}
				} else {
					return "consultarAdmProyecto";
				}
			}
		}
	}

	public boolean validarVinculacionPersona(InvestigadorInterno invI, boolean permitirDocentesCatedra) {

		if (invI != null && invI.getTipoVinculacion() != null && (invI.getTipoVinculacion().getId().equals("30")
				|| invI.getTipoVinculacion().getId().equals("29") || invI.getTipoVinculacion().getId().equals("16"))) {
			return true;
		}
		return false;
	}

	public boolean validarGanadorSemilleros(String idPersona, String tipoDocumento, String id) {

		List<Object> proyectos;
		Session session = getSession();

		session.beginTransaction();
		String query = "select p.PRY_ID AS idProyecto " + "from her_proyecto p, her_investigador_proyecto ip "
				+ "where ip.TDO_ID = '" + tipoDocumento + "' and ip.INV_ID = '" + idPersona + "' and "
				+ " p.pry_id = ip.pry_id and p.mod_id = '" + id + "' and p.epr_id IN ('A','AP')";
		SQLQuery sqlQuery = session.createSQLQuery(query);

		proyectos = sqlQuery.addScalar("idProyecto", Hibernate.LONG).list();

		session.close();

		if (proyectos.size() > 0) {
			return true;
		}

		return false;
	}

	public List<ProyectoEvaluador> obtenerProyectosEvaluador(Long idProyecto) throws DataAccessException {

		List<Object> proyectos;
		List<ProyectoEvaluador> resultado = new ArrayList<ProyectoEvaluador>();

		Session session = getSession();

		session.beginTransaction();
		String query = "SELECT PE.PRE_ID idProyectoEvaluador, PE.INV_ID idEvaluador, PE.TDO_ID idTipoDocumento, P.PER_NOMBRE1 nombre1, P.PER_NOMBRE2 nombre2"
				+ ", P.PER_APELLIDO1 apellido1, P.PER_APELLIDO2 apellido2, PE.PRE_FECHA fechaCalifiacion, PE.PRE_CALIFICACION_FINAL calificacionaFinal, PRE_ESTADO_EVALUACION estado, CONV.CON_RESTRICCION restriccion, conv.CON_ID conID, pry.PRY_ID proyecto"
				+ " from HER_PROYECTO_EVALUADOR PE, HER_PERSONA P, HER_PROYECTO PRY, HER_CONVOCATORIA CONV"
				+ " WHERE PE.PRY_ID = '" + idProyecto
				+ "' AND PE.INV_ID = P.PER_ID AND PE.TDO_ID = P.TDO_ID AND PRY.PRY_ID = PE.PRY_ID AND PRY.MOD_ID = CONV.CON_ID";
		SQLQuery sqlQuery = session.createSQLQuery(query);

		proyectos = sqlQuery.addScalar("idProyectoEvaluador", Hibernate.LONG).addScalar("idEvaluador", Hibernate.STRING)
				.addScalar("idTipoDocumento", Hibernate.STRING).addScalar("nombre1", Hibernate.STRING)
				.addScalar("nombre2", Hibernate.STRING).addScalar("apellido1", Hibernate.STRING)
				.addScalar("apellido2", Hibernate.STRING).addScalar("fechaCalifiacion", Hibernate.DATE)
				.addScalar("calificacionaFinal", Hibernate.FLOAT).addScalar("estado", Hibernate.STRING)
				.addScalar("restriccion", Hibernate.STRING).addScalar("conID", Hibernate.LONG)
				.addScalar("proyecto", Hibernate.LONG).list();

		session.close();

		Iterator<Object> it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoEvaluador proyectoEvaluador = new ProyectoEvaluador();
			proyectoEvaluador.setId((Long) name[0]);
			IdPersona idPersona = new IdPersona();
			idPersona.setDocumento((String) name[1]);
			idPersona.setTipoDocumento((String) name[2]);
			Investigador investigador = new Investigador();
			investigador.setNombre1((String) name[3]);
			investigador.setNombre2((String) name[4]);
			investigador.setApellido1((String) name[5]);
			investigador.setApellido2((String) name[6]);
			investigador.setId(idPersona);
			proyectoEvaluador.setFecha((Date) name[7]);
			proyectoEvaluador.setCalificacionFinal((Float) name[8]);
			proyectoEvaluador.setEvaluador(investigador);
			proyectoEvaluador.setEstado((String) name[9]);

			Convocatoria conv = new Convocatoria();
			conv.setId((Long) name[11]);
			RestriccionConvocatoria rc = new RestriccionConvocatoria();
			rc.setId((String) name[10]);
			conv.setRestriccion(rc);
			Proyecto pry = new Proyecto();
			pry.setId((Long) name[12]);
			pry.setModalidad(conv);
			proyectoEvaluador.setProyecto(pry);
			resultado.add(proyectoEvaluador);
		}

		return resultado;
	}

	public List<ProyectoInforme> obtenerProyectoInforme(Long idProyecto) throws DataAccessException {

		List<Object> proyectos;
		List<ProyectoInforme> resultado = new ArrayList<ProyectoInforme>();

		Session session = getSession();

		session.beginTransaction();
		String query = "SELECT PIN.PIN_ID id, PIN.PIN_FECHA fechaGeneracion, TI.TIN_ID idTipoInforme,"
				+ " TI.TIN_NOMBRE nombreTipoInforme, PC.COM_ID  idCompromiso, PC.COM_CUMPLIDO cumplidoCompromiso,"
				+ "PC.COM_FECHA_VENCIMIENTO vencimientoCompromiso,PC.COM_FECHA_VENC_PRORROGA vencimientoCompromisoProrroga,"
				+ "EI.EIN_NOMBRE nombreEstado, pin.PIN_MOTIVO_NO_APROBACION noAprobacion, pin.PIN_CUADRO_RESULTADOS cuadroResultados,"
				+ "per.per_nombre1, per.per_nombre2 per_nombre2, per.per_apellido1, per.per_apellido2,"
				+ "perCoo.per_nombre1 per_nombre1_coordinador, perCoo.per_nombre2 per_nombre2_coordinador"
				+ ", perCoo.per_apellido1 per_apellido1_coordinador, perCoo.per_apellido2 per_apellido2_coordinador,"
				+ "EI.EIN_ID" + " from HER_PROYECTO_INFORME PIN left join HER_PROYECTO_COMPROMISO PC ON "
				+ " PIN.COM_ID = PC.COM_ID left join HER_PERSONA per on per.per_id = pin.per_id_revision_facultad"
				+ " and per.tdo_id = tdo_id_revision_facultad "
				+ " left join HER_PERSONA perCoo on perCoo.per_id = pin.per_id_revision_coordinador "
				+ " and perCoo.tdo_id = tdo_id_revision_coordinador, "
				+ " HER_TIPO_INFORME TI, HER_ESTADO_INFORME EI WHERE " + " PIN.PRY_ID = '" + idProyecto
				+ "' AND TI.TIN_ID = PIN.TIN_ID and EI.EIN_ID = PIN.EIN_ID" + " and PIN.EIN_ID <> '"
				+ ProyectoInforme.ESTADO_BORRADO + "'";
		SQLQuery sqlQuery = session.createSQLQuery(query);

		proyectos = sqlQuery.addScalar("id", Hibernate.LONG).addScalar("fechaGeneracion", Hibernate.DATE)
				.addScalar("idTipoInforme", Hibernate.LONG).addScalar("nombreTipoInforme", Hibernate.STRING)
				.addScalar("idCompromiso", Hibernate.LONG).addScalar("cumplidoCompromiso", Hibernate.STRING)
				.addScalar("vencimientoCompromiso", Hibernate.DATE)
				.addScalar("vencimientoCompromisoProrroga", Hibernate.DATE).addScalar("nombreEstado", Hibernate.STRING)
				.addScalar("noAprobacion", Hibernate.STRING).addScalar("cuadroResultados", Hibernate.BLOB)
				.addScalar("per_nombre1", Hibernate.STRING).addScalar("per_nombre2", Hibernate.STRING)
				.addScalar("per_apellido1", Hibernate.STRING).addScalar("per_apellido2", Hibernate.STRING)
				.addScalar("per_nombre1_coordinador", Hibernate.STRING)
				.addScalar("per_nombre2_coordinador", Hibernate.STRING)
				.addScalar("per_apellido1_coordinador", Hibernate.STRING)
				.addScalar("per_apellido2_coordinador", Hibernate.STRING).addScalar("EIN_ID", Hibernate.LONG).list();

		session.close();

		Iterator<Object> it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoInforme proyectoInforme = new ProyectoInforme();
			proyectoInforme.setId((Long) name[0]);
			proyectoInforme.setFechaGeneracion((Date) name[1]);
			TipoInforme tipoInforme = new TipoInforme();
			tipoInforme.setId((Long) name[2]);
			tipoInforme.setNombre((String) name[3]);
			if (name[4] != null) {
				ProyectoCompromiso proyectoCompromiso = new ProyectoCompromiso();
				proyectoCompromiso.setId((Long) name[4]);
				proyectoCompromiso.setCumplido((String) name[5]);
				proyectoCompromiso.setFechaVencimiento((Date) name[6]);
				if (name[7] != null) {
					proyectoCompromiso.setFechaVencimiento((Date) name[7]);
				}
				proyectoInforme.setProyectoCompromiso(proyectoCompromiso);
			}
			EstadoInforme estadoInforme = new EstadoInforme();
			estadoInforme.setNombreEstado((String) name[8]);
			estadoInforme.setId((Long) name[19]);
			proyectoInforme.setEstadoInforme(estadoInforme);
			proyectoInforme.setNoAprobacion((String) name[9]);
			proyectoInforme.setTipoInforme(tipoInforme);
			proyectoInforme.setCuadroResultados((Blob) name[10]);
			Persona personaFacultad = new Persona();
			personaFacultad.setNombre1((String) name[11]);
			personaFacultad.setNombre2((String) name[12]);
			personaFacultad.setApellido1((String) name[13]);
			personaFacultad.setApellido2((String) name[14]);
			proyectoInforme.setRevisorFacultad(personaFacultad);
			Persona personaCoordinador = new Persona();
			personaCoordinador.setNombre1((String) name[15]);
			personaCoordinador.setNombre2((String) name[16]);
			personaCoordinador.setApellido1((String) name[17]);
			personaCoordinador.setApellido2((String) name[18]);
			proyectoInforme.setRevisorCoordinador(personaCoordinador);

			resultado.add(proyectoInforme);
		}

		return resultado;
	}

	public List<Proyecto> obtenerProyectosCompromisosPendientes(Persona persona, String modalidad)
			throws DataAccessException {
		List<Object> proyectos;
		List<Proyecto> resultados = new ArrayList<Proyecto>();

		Session session = getSession();

		if (!"".equals(modalidad.trim())) {
			modalidad = " and p.mod_id = '" + modalidad + "'";
		}

		String query = "select distinct comp.*, decode(ei.ein_nombre,null,'SIN REGISTRAR',ei.ein_nombre)estado_compromiso from "
				+ "(select distinct p.pry_id as idHermes, p.pry_nombre as nombre, ep.EPR_NOMBRE as estado_proyecto, "
				+ "PER.PER_NOMBRE1||' '||DECODE(PER.PER_NOMBRE2, PER.PER_NOMBRE2,PER.PER_NOMBRE2,NULL,' ')||' '||PER.PER_APELLIDO1||' '||DECODE(PER.PER_APELLIDO2, PER.PER_APELLIDO2,PER.PER_APELLIDO2,NULL,' ') profesor, "
				+ "ti.TIN_NOMBRE as tipo_informe, ti.tin_id "
				+ "from her_proyecto p left join her_proyecto_compromiso pc on pc.pry_id = p.pry_id and (pc.COM_CUMPLIDO = 'N' and pc.COM_FECHA_VENCIMIENTO < SYSDATE and (pc.COM_FECHA_VENC_PRORROGA < SYSDATE or pc.COM_FECHA_VENC_PRORROGA is null)) "
				+ "left join HER_SEG_PROYECTO_PERSONA seg on p.pry_id = seg.PRY_ID, her_investigador_proyecto ip, her_persona per, her_estado_proyecto ep, HER_TIPO_informe ti "
				+ "where p.pry_id = ip.pry_id and ip.inv_id = per.per_id and ip.tdo_id = per.tdo_id and ip.inp_tipo = 'P' and p.epr_id in ('A','AP') "
				+ "and ep.epr_id = p.epr_id and ti.tin_id = pc.tin_id and seg.per_id = '"
				+ persona.getId().getDocumento() + "' and seg.tdo_id = '" + persona.getId().getTipoDocumento() + "'"
				+ modalidad + ")comp "
				+ "left join her_proyecto_informe pi on pi.pry_id = comp.idHermes and pi.tin_id = comp.tin_id left join her_estado_informe ei on ei.ein_id = pi.ein_id "
				+ "order by comp.idHermes asc";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectos = sqlQuery1.addScalar("idHermes", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("estado_proyecto", Hibernate.STRING).addScalar("profesor", Hibernate.STRING)
				.addScalar("tipo_informe", Hibernate.STRING).addScalar("estado_compromiso", Hibernate.STRING).list();
		session.close();

		Iterator it = proyectos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Proyecto pry = new Proyecto();
			pry.setId((Long) name[0]);
			pry.setNombre((String) name[1]);
			EstadoProyecto ep = new EstadoProyecto();
			ep.setNombre((String) name[2]);
			pry.setEstadoProyecto(ep);
			pry.setResumen((String) name[3]);
			pry.setObservaciones((String) name[4]);
			pry.setAntecedentes((String) name[5]);
			resultados.add(pry);
		}
		return resultados;
	}

	/**
	 * Metodo que obtiene los productos que se deben entregar en un proyecto
	 * 
	 * @param pIdProyecto
	 * @return List de objetos 'ProyectoProducto'
	 */
	public List getProductosProyectoAEntregar(Long pIdProyecto) {

		StringBuffer filtros_ = new StringBuffer();
		if (pIdProyecto != null) {
			filtros_.append(" AND pp.proyecto.id=:idProyecto ");
		}

		Session session_ = getSession();

		StringBuffer querySB_ = new StringBuffer();
		querySB_.append(" select pp ");
		querySB_.append(" from ProyectoProducto pp ");
		querySB_.append(" where pp.id > -1 ");
		querySB_.append(filtros_.toString());

		Query query_ = session_.createQuery(querySB_.toString());

		if (pIdProyecto != null) {
			query_.setLong("idProyecto", pIdProyecto.longValue());
		}

		List proyectoProductosList_ = query_.list();
		session_.close();
		return proyectoProductosList_;
	}
	
	public List getCantidadesMaterialGraficoProyecto(Long pIdProyecto) {

		 List listaCantidadMaterialGrafico;
		 Session s = getSession();
	     Query q = s.createQuery(
	                " select cmg from CantidadMaterialGrafico cmg " 
                    + " where cmg.proyecto.id=:idProyecto ");
	     q.setParameter("idProyecto", pIdProyecto);
	     listaCantidadMaterialGrafico = q.list();
	     s.close();
	     return listaCantidadMaterialGrafico;
	     
	}
	
	public List getTiposMaterialGrafico() {

		 List listaTipoMaterialGrafico;
		 Session s = getSession();
	     Query q = s.createQuery(
	                " select tmg from TipoMaterialGrafico tmg " 
                    + " where tmg.estado = true ");
	     listaTipoMaterialGrafico = q.list();
	     s.close();
	     return listaTipoMaterialGrafico;
	     
	}
	
	public void imprimirReporteProyectoEditorial(Long idProyecto, HttpSession sesion,
			Boolean esEvaluador) {
        ReporteBirt r = new ReporteBirt();
        r.adicionarParametro("id", idProyecto.toString());
        r.setNombreReporte("/editorial/formato-proyecto-editorial");
        r.setFormato(ReporteBirt.FORMATO_PDF);
        sesion.setAttribute("reporte", r);

        FacesContext context = FacesContext.getCurrentInstance();
        r.run(context);
		
	}
}
