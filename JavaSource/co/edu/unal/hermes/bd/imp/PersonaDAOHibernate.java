package co.edu.unal.hermes.bd.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

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
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.IPersonaDAO;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.CaracterPropiedadIntelectual;
import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.ClasificacionConocimiento;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Empresa;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Evaluador;
import co.edu.unal.hermes.modelo.EvaluadorCorreo;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.InvestigadorPuntaje;
import co.edu.unal.hermes.modelo.LineaInvestigacion;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.PosibleEvaluador;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.RestriccionConvocatoria;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.SemilleroIntegrante;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.utils.ReemplazaAcentos;

/**
 * Maneja el acceso a toda la información de los investigadores con Hibernate
 */
public class PersonaDAOHibernate extends HibernateDaoSupport implements IPersonaDAO {

	/**
	 * Obtiene la lista de todos los investigadores ordenados por apellidos y
	 * nombres
	 * 
	 */
	public Persona obtenerCoordinadorNoAsignadoAsesor(IdPersona idAsesor, String idCoordinador) {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		Persona coord = new Persona();
		try {
			IdPersona idPer = null;
			session = getSession();
			st = session.connection().createStatement();
			String query = "SELECT per.per_id doc, " + "per.tdo_id tipo, " + "per.per_nombre1 nombre1, "
					+ "per.per_nombre2 nombre2, " + "per.per_apellido1 apellido1, " + "per.per_apellido2 apellido2 "
					+ "FROM her_persona per, " + "her_persona_rol rol " + "WHERE per.tdo_id = rol.tdo_id "
					+ "AND per.per_id = rol.per_id " + "AND per.per_id = '" + idCoordinador + "' "
					+ "AND rol.rol_id = 'C' ";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				idPer = new IdPersona();
				idPer.setDocumento(rs.getString("doc"));
				idPer.setTipoDocumento(rs.getString("tipo"));
				coord.setId(idPer);
				coord.setNombre1(rs.getString("nombre1"));
				coord.setNombre2(rs.getString("nombre2"));
				coord.setApellido1(rs.getString("apellido1"));
				coord.setApellido2(rs.getString("apellido2"));
			}
			return coord;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return coord;

	}

	public Persona obtenerCoordinadorNoAsignadoAsesorEditorial(IdPersona idAsesor, String idCoordinador) {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		Persona coord = new Persona();
		try {
			IdPersona idPer = null;
			session = getSession();
			st = session.connection().createStatement();
			String query = "SELECT per.per_id doc, " + "per.tdo_id tipo, " + "per.per_nombre1 nombre1, "
					+ "per.per_nombre2 nombre2, " + "per.per_apellido1 apellido1, " + "per.per_apellido2 apellido2 "
					+ "FROM her_persona per, " + "her_persona_rol rol " + "WHERE per.tdo_id = rol.tdo_id "
					+ "AND per.per_id = rol.per_id " + "AND per.per_id = '" + idCoordinador + "' "
					+ "AND rol.rol_id = 'EC' ";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				idPer = new IdPersona();
				idPer.setDocumento(rs.getString("doc"));
				idPer.setTipoDocumento(rs.getString("tipo"));
				coord.setId(idPer);
				coord.setNombre1(rs.getString("nombre1"));
				coord.setNombre2(rs.getString("nombre2"));
				coord.setApellido1(rs.getString("apellido1"));
				coord.setApellido2(rs.getString("apellido2"));
			}
			return coord;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return coord;

	}

	public List obtenerPersona(String where, boolean isParametros, Object[] parametros) throws DataAccessException {
		if (isParametros) {
			return getHibernateTemplate().find("per from Persona per " + where, parametros);
		}

		return getHibernateTemplate().find("select per from Investigador per " + where);
	}

	public List obtenerInvestigadores(String name) throws DataAccessException {

		// Util util = Util.getInstace();
		// name = util.mayusculaSinTildes(name);

		Session session = getSession();

		Criterion crit_nombre1 = Restrictions.ilike("nombre1", name, MatchMode.ANYWHERE);
		Criterion crit_nombre2 = Restrictions.ilike("nombre2", name, MatchMode.ANYWHERE);
		Criterion crit_apellido1 = Restrictions.ilike("apellido1", name, MatchMode.ANYWHERE);
		Criterion crit_apellido2 = Restrictions.ilike("apellido2", name, MatchMode.ANYWHERE);

		Junction disjunction = Restrictions.disjunction();
		disjunction = disjunction.add(crit_nombre1);
		disjunction = disjunction.add(crit_nombre2);
		disjunction = disjunction.add(crit_apellido1);
		disjunction = disjunction.add(crit_apellido2);

		Criteria criteria = session.createCriteria(Investigador.class);
		criteria.add(disjunction);
		criteria.addOrder(Order.asc("apellido1"));
		criteria.addOrder(Order.asc("apellido2"));
		criteria.addOrder(Order.asc("nombre1"));

		List investigadores = criteria.list();

		session.close();
		return investigadores;

	}

	/**
	 * Obtiene la lista de todos los investigadores ordenados por apellidos y
	 * nombres
	 * 
	 */

	public List obtenerInvestigadores(String where, boolean isParametros, Object[] parametros)
			throws DataAccessException {
		if (isParametros) {
			return getHibernateTemplate().find("inv from Investigador inv " + where, parametros);
		}

		return getHibernateTemplate().find("select inv from Investigador inv " + where);
	}

	public List obtenerInvestigadoresInternos(String name) throws DataAccessException {

		// Util util = Util.getInstace();
		// name = util.mayusculaSinTildes(name);

		Session session = getSession();

		Criterion crit_nombre1 = Restrictions.ilike("nombre1", name, MatchMode.ANYWHERE);
		Criterion crit_nombre2 = Restrictions.ilike("nombre2", name, MatchMode.ANYWHERE);
		Criterion crit_apellido1 = Restrictions.ilike("apellido1", name, MatchMode.ANYWHERE);
		Criterion crit_apellido2 = Restrictions.ilike("apellido2", name, MatchMode.ANYWHERE);

		Junction disjunction = Restrictions.disjunction();
		disjunction = disjunction.add(crit_nombre1);
		disjunction = disjunction.add(crit_nombre2);
		disjunction = disjunction.add(crit_apellido1);
		disjunction = disjunction.add(crit_apellido2);

		Criteria criteria = session.createCriteria(InvestigadorInterno.class).setFetchMode("dependencia",
				FetchMode.JOIN);
		criteria.add(disjunction);
		criteria.addOrder(Order.asc("apellido1"));
		criteria.addOrder(Order.asc("apellido2"));
		criteria.addOrder(Order.asc("nombre1"));

		List investigadores = criteria.list();

		session.close();
		return investigadores;

	}

	/**
	 * Busca por Id de investigador
	 */
	public Investigador obtenerInvestigador(IdPersona id) throws DataAccessException {

		Session session = getSession();

		Investigador i = (Investigador) session.createCriteria(Investigador.class).add(Restrictions.idEq(id))
				.setFetchMode("ciudadNacimiento", FetchMode.JOIN).uniqueResult();
		if (i == null) {
			session.close();
			return null;
		}
		Hibernate.initialize(i.getCiudadNacimiento());
		session.close();
		return i;
	}

	/**
	 * Busca por Id de investigador con sus proyectos asosciados, recibe el id del
	 * investigador
	 */

	public Investigador obtenerDocente(IdPersona id) throws DataAccessException {
		Investigador i = obtenerInvestigador(id);
		if (i != null) {
			if (!i.getCategoriaInvestigador().getId().toString().equals(CategoriaInvestigador.IDDOCENTE)) {
				i = null;
			}
		}
		return i;

	}

	/**
	 * Obtiene un investigador con sus proyectos
	 */

	public Investigador obtenerInvestigadorProyectos(IdPersona id) throws DataAccessException {

		Session session = getSession();
		try {
			Investigador i = (Investigador) session.createCriteria(Investigador.class)
					.setFetchMode("proyectosInvestigador", FetchMode.JOIN).addOrder(Order.desc("id"))
					.add(Restrictions.idEq(id)).uniqueResult();
			session.close();
			return i;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Obtiene un investigador con sus proyectos
	 */

	public Investigador obtenerInvestigadorProyectosLiviano(IdPersona id) throws DataAccessException {

		Session session = getSession();
		String sql = " " + "select ip.inp_tipo tipo, ip.pry_id idProyecto, ti.tmo_id idTipoModalidad "
				+ "from her_investigador_proyecto ip, her_tipo_investigador ti "
				+ "where ip.inp_tipo = ti.tinv_id and ip.inv_id = '" + id.getDocumento() + "' and ip.tdo_id = '"
				+ id.getTipoDocumento() + "'";
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		List proyectosInvetigador = sqlQuery.addScalar("tipo", Hibernate.STRING)
				.addScalar("idProyecto", Hibernate.STRING).addScalar("idTipoModalidad", Hibernate.STRING).list();

		session.close();

		Investigador investigador = new Investigador();
		Iterator<Object> it = proyectosInvetigador.iterator();
		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			InvestigadorProyecto ip = new InvestigadorProyecto();
			TipoInvestigador tipoInvestigador = new TipoInvestigador();
			tipoInvestigador.setId((String) name[0]);
			tipoInvestigador.setTipoModalidad((String) name[2]);
			ip.setTipo(tipoInvestigador);
			Proyecto proyecto = new Proyecto();
			proyecto.setId(Long.parseLong((String) name[1]));
			ip.setProyecto(proyecto);
			investigador.getProyectosInvestigador().add(ip);
		}

		return investigador;
	}

	/**
	 * Obtiene un investigador con sus grupos
	 */

	public Investigador obtenerInvestigadorGruposLiviano(IdPersona id) throws DataAccessException {

		Session session = getSession();
		String sql = " " + "select ip.ing_tipo tipo, ip.gru_id idProyecto, ti.igt_id idTipoModalidad "
				+ "from her_investigador_grupo ip, HER_INVESTIGADOR_GRUPO_TIPO ti "
				+ "where ip.ing_tipo = ti.igt_id and ip.inv_id = '" + id.getDocumento() + "' and ip.tdo_id = '"
				+ id.getTipoDocumento() + "'";
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		List gruposInvestigador = sqlQuery.addScalar("tipo", Hibernate.STRING).addScalar("idProyecto", Hibernate.STRING)
				.addScalar("idTipoModalidad", Hibernate.STRING).list();

		session.close();

		Investigador investigador = new Investigador();
		Iterator<Object> it = gruposInvestigador.iterator();
		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			InvestigadorGrupo ig = new InvestigadorGrupo();
			ig.setTipo((String) name[0]);
			Grupo grupo = new Grupo();
			grupo.setId(Long.parseLong((String) name[1]));
			ig.setGrupo(grupo);
			investigador.getGruposInvestigador().add(ig);
		}

		return investigador;
	}

	/**
	 * Obtiene un investigador los proyectos que esta evaluando
	 */

	public Investigador obtenerInvestigadorProyectosAEvaluar(IdPersona id) throws DataAccessException {

		Session session = getSession();

		Investigador i = (Investigador) session.createCriteria(Investigador.class)
				.setFetchMode("proyectosEvaluador", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

		session.close();
		return i;
	}

	/**
	 * Obtiene un investigador los proyectos que esta evaluando
	 */

	public Investigador obtenerInvestigadorProyectosPropuestosAEvaluar(IdPersona id) throws DataAccessException {

		Session session = getSession();

		Investigador investigador = new Investigador();
		List listaR = new Vector();
		Statement st = null;
		ResultSet re = null;
		try {
			st = session.connection().createStatement();
			String sql = " " + "select pr.pry_id,pe.pre_id "
					+ "from her_proyecto_evaluador pe,her_proyecto pr, her_convocatoria c, "
					+ "her_convocatoria_padre cp "
					+ "where pr.pry_id=pe.pry_id and (pr.epr_id='E' or (cp.cnp_permanente = 'Y' and (pr.epr_id in ('E','P')))) and pe.inv_id='"
					+ id.getDocumento() + "' and pe.tdo_id='" + id.getTipoDocumento()
					+ "' and pr.mod_id = c.con_id and c.cnp_id = cp.cnp_id " + " order by pr.pry_id asc";
			st.execute(sql);
			re = st.getResultSet();
			while (re.next()) {
				ProyectoEvaluador ep = new ProyectoEvaluador();
				ep.setProyecto((Proyecto) obtenerObjeto(new Proyecto(), new Long(re.getLong(1)), "modalidad"));
				ep.setId(new Long(re.getLong(2)));
				listaR.add(ep);
			}
			// ORIGINAL investigador.setProyectosEvaluador(new HashSet());
			investigador.setProyectosEvaluador(new TreeSet());
			investigador.getProyectosEvaluador().addAll(listaR);
		} catch (DataAccessException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		session.close();
		return investigador;
	}

	/**
	 * Obtiene un resumen del investigador con sus proyectos y sus grupos
	 */

	public Investigador obtenerResumenInvestigador(IdPersona id) throws DataAccessException {

		Session session = getSession();

		Investigador i = (Investigador) session.createCriteria(Investigador.class)
				.setFetchMode("proyectosInvestigador", FetchMode.JOIN)
				.setFetchMode("gruposInvestigador", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

		session.close();
		return i;
	}

	/**
	 * Obtiene un investigador y su categoria
	 */
	public Investigador obtenerInvestigadorCategoria(IdPersona id) throws DataAccessException {
		Session session = getSession();

		Investigador i = (Investigador) session.createCriteria(Investigador.class)
				.setFetchMode("categoriaInvestigador", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

		session.close();
		return i;
	}

	/**
	 * Guarda información del investigador
	 */
	public void guardarInvestigador(Investigador inv) throws DataAccessException {
		Session session = getSession();
		Transaction tr = session.beginTransaction();
		try {
			//session.save(inv);
			session.saveOrUpdate(inv);
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}

		session.close();
		// getHibernateTemplate().save(inv);
	}

	/**
	 * Guarda una persona
	 */
	public void guardarPersona(Persona persona) throws DataAccessException {
		getHibernateTemplate().save(persona);
	}

	public void guardarInvInterno(InvestigadorInterno interno) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(interno);
	}

	public String guardarRol(Rol rol) throws DataAccessException {
		String mensajeRol = "";
		getHibernateTemplate().save(rol);
		return mensajeRol;
	}

	public void crearRoles(PersonaRol perRol) throws DataAccessException {

		getHibernateTemplate().saveOrUpdate(perRol);
	}

	public void borrarRoles(PersonaRol perRol) throws DataAccessException {

		Session s = getSession();
		Statement st = null;
		try {
			st = s.connection().createStatement();
			String sa = "DELETE her_persona_rol " + "WHERE per_id = '" + perRol.getDocumento() + "' " + "AND tdo_id = '"
					+ perRol.getTipoDocumento() + "' " + "AND rol_id = '" + perRol.getNombre() + "' ";
			st.execute(sa);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}

	}

	public List buscarRoles(String documento) {

		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		List myList = new ArrayList();
		String nombre = new String();
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "select rol_id " + " from her_persona_rol " + " where per_id = '" + documento + "'";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				nombre = rs.getString("rol_id");
				myList.add(nombre);
			}
			return myList;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return myList;
	}

	// ***

	public List buscarRoles2(String documento, String tipoDocumento) {

		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		List myList = new ArrayList();
		Rol rol1 = new Rol();
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "select r.rol_id, r.rol_nombre from her_persona_rol pr , her_rol r where r.rol_id = pr.rol_id "
					+ " and  pr.per_id = '" + documento + "' and  pr.tdo_id = '" + tipoDocumento
					+ "' and  r.rol_visible = 'SI'";

			
			rs = st.executeQuery(query);
			while (rs.next()) {
				rol1 = new Rol();
				rol1.setId(rs.getString("rol_id"));
				rol1.setNombre(rs.getString("rol_nombre"));
				myList.add(rol1);
			}
			return myList;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return myList;
	}

	// ***
	public String obtenerUltimoConsecutivo() {

		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		String pos = new String();
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "select seq_posible_evaluador.nextval as ultimoValor " + "from dual";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				pos = rs.getString("ultimoValor");
				return pos;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return pos;
	}

	/**
	 * Obtiene una persona por su documento
	 */
	public Persona obtenerPersona(IdPersona id) throws DataAccessException {
		Session session = getSession();
		Persona p = (Persona) session.createCriteria(Persona.class).setFetchMode("ciudadExpedicion", FetchMode.JOIN)
				.setFetchMode("ciudadDomicilio", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();
		session.close();
		return p;
	}

	public Persona obtenerPosibleEvaluador(IdPersona id) throws DataAccessException {

		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		Persona pos = new Persona();
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "select per_id,pev_nombre,pev_email,pev_telefono,pev_apellido1,pev_apellido2 from her_posible_evaluador where per_id = '"
					+ id.getDocumento() + "'";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				pos.setId(id);
				pos.setNombre1(rs.getString("pev_nombre"));
				pos.setEmail(rs.getString("pev_email"));
				pos.setTelefono(rs.getString("pev_telefono"));
				pos.setApellido1(rs.getString("pev_apellido1"));
				pos.setApellido2(rs.getString("pev_apellido2"));
			}
			return pos;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return pos;
	}

	public List obtenerRols(IdPersona id) throws DataAccessException {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		List obtRol = new ArrayList();
		try {
			Rol rols = null;
			session = getSession();
			st = session.connection().createStatement();
			String query = "select r.rol_id,r.rol_nombre, pr.HPR_FECHA_FIN " + "from her_rol r,her_persona_rol pr "
					+ "where r.rol_id = pr.rol_id " + "and pr.per_id = '" + id.getDocumento() + "'";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				rols = new Rol();
				rols.setId(rs.getString("rol_id"));
				rols.setNombre(rs.getString("rol_nombre"));
				rols.setFechaVencimientoPersonaRol(rs.getDate("HPR_FECHA_FIN"));
				obtRol.add(rols);
				rols = null;
			}
			return obtRol;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return obtRol;
	}

	public List obtenerRolesSolicitud() {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		List obtRolSol = new ArrayList();
		try {
			Rol rols = null;
			session = getSession();
			st = session.connection().createStatement();
			String query = "select r.rol_id,r.rol_nombre " + "from her_rol r " + "where r.rol_visible = "
					+ " 'SI' order by r.rol_nombre";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				rols = new Rol();
				rols.setId(rs.getString("rol_id"));
				rols.setNombre(rs.getString("rol_nombre"));
				obtRolSol.add(rols);
				rols = null;
			}
			return obtRolSol;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return obtRolSol;

	}

	public List obtenerRolesDependencia(String dep, String rol) {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		List obtRolDep = new ArrayList();
		try {
			Persona pers = null;

			session = getSession();
			st = session.connection().createStatement();
			String query = "select pr.rol_id, pr.per_id, p.per_nombre1, p.per_nombre2, p.per_apellido1, p.per_apellido2 ,p.per_email from her_persona_rol pr, her_investigador_interno ii, her_dependencia d, her_persona p where pr.PER_ID = ii.INV_ID and (ii.DPN_ID2= d.DPN_ID) and pr.per_id = p.per_id and d.DPN_FACULTAD = '"
					+ dep + "'  and pr.ROL_ID = '" + rol + "'"
					+ "union select pr.rol_id, pr.per_id, p.per_nombre1, p.per_nombre2, p.per_apellido1, p.per_apellido2 ,p.per_email from her_persona_rol pr, her_investigador_interno ii, her_dependencia d, her_persona p where pr.PER_ID = ii.INV_ID and (ii.DPN_ID2= d.DPN_ID) and pr.per_id = p.per_id and d.DPN_ID = '"
					+ dep + "' and pr.ROL_ID = '" + rol + "'";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				pers = new Persona();
				pers.setNombre1(rs.getString("per_nombre1"));
				pers.setNombre2(rs.getString("per_nombre2"));
				pers.setApellido1(rs.getString("per_apellido1"));
				pers.setApellido2(rs.getString("per_apellido2"));
				pers.setEmail(rs.getString("per_email"));
				IdPersona idPersona = new IdPersona();
				idPersona.setDocumento(rs.getString("per_id"));
				pers.setId(idPersona);
				obtRolDep.add(pers);
				pers = null;

			}
			return obtRolDep;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return obtRolDep;

	}

	public List obtenerTodosLosRoles() {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		List obtRol = new ArrayList();
		try {
			Rol rols = null;
			session = getSession();
			st = session.connection().createStatement();
			String query = "select r.rol_id,r.rol_nombre " + "from her_rol r";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				rols = new Rol();
				rols.setId(rs.getString("rol_id"));
				rols.setNombre(rs.getString("rol_nombre"));
				obtRol.add(rols);
				rols = null;
			}
			return obtRol;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return obtRol;
	}

	// fecha fin de un rol de una persona
	public Date obtenerFechaFinRol(IdPersona id, String idRol) throws DataAccessException {

		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		String pos = "";
		Date fecha = new Date();

		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "select hpr_fecha_fin from her_persona_rol where per_id = '" + id.getDocumento()
					+ "' and tdo_id = '" + id.getTipoDocumento() + "' and rol_id = '" + idRol + "'";

			rs = st.executeQuery(query);
			while (rs.next()) {
				fecha = (rs.getDate("hpr_fecha_fin"));
			}
			return fecha;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return fecha;
	}

	public InvestigadorExterno obtenerInvestigadorExterno(IdPersona id) throws DataAccessException {
		Session session = getSession();

		InvestigadorExterno investigadorExterno = (InvestigadorExterno) session
				.createCriteria(InvestigadorExterno.class).setFetchMode("institucion", FetchMode.JOIN)
				.add(Restrictions.idEq(id)).uniqueResult();

		session.close();
		return investigadorExterno;
	}

	public InvestigadorInterno obtenerInvestigadorInterno(IdPersona id) throws DataAccessException {

		Session session = getSession();
		//
		InvestigadorInterno investigadorInterno = (InvestigadorInterno) session
				.createCriteria(InvestigadorInterno.class).setFetchMode("dependencia", FetchMode.JOIN)
				.add(Restrictions.idEq(id)).uniqueResult();

		session.close();
		return investigadorInterno;
	}

	public Persona obtenerPersonaRoles(IdPersona id) throws DataAccessException {
		HttpSession sesion;
		FacesContext facesContext;
		facesContext = javax.faces.context.FacesContext.getCurrentInstance();
		sesion = (HttpSession) facesContext.getExternalContext().getSession(false);
		sesion.setAttribute("esEstudianteLider", false);
		Session session = getSession();
		Persona p = null;
		
		try {
		 p = (Persona) session.createCriteria(Persona.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(p.getRoles());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No se cargaron los roles");
		} finally {
			session.close();
		}
		// Este procedimiendo es para verificar si el investigador es un
		// investigador activo.
		if (p != null) {
			Set<Rol> roles = p.getRoles();
			Iterator<Rol> it = roles.iterator();
			Rol rol;
			boolean isInvestigador = false;
			boolean isEvaluador = false;
			boolean isLaboratorios = false;
			Rol rolInvestigador = null;
			while (it.hasNext()) {
				rol = it.next();
				Date fechaRol=null;
				try {
					fechaRol = obtenerFechaFinRol(p.getId(), rol.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (rol.getId().equals(Rol.INVESTIGADOR) && fechaRol !=null && fechaRol.after(new Date())) {
					isInvestigador = true;
					rolInvestigador = rol;
				} else if (rol.getId().equals("E") && fechaRol !=null && fechaRol.after(new Date())) {
					isEvaluador = true;
				} else if ((rol.getId().equals(Rol.COORDINADOR_LABORATORIO)
						|| rol.getId().equals(Rol.COORDINADOR_TECNICO_LABORATORIO)) && fechaRol !=null && fechaRol.before(new Date())) {
					isLaboratorios = true;
				}
			}
//			if (isInvestigador) {
			if (true) {
				Investigador investigador = null;
				try {
					investigador = (Investigador) p;
				} catch (ClassCastException cce) {
					cce.printStackTrace();
				}
				if (investigador != null && investigador.getInterno().equals("N") && investigador.getEsFuncionario().equals("N")) {
					boolean isEstudianteLider = false;
					Investigador investigadorProyectos = obtenerInvestigadorProyectosLiviano(investigador.getId());
					if (investigadorProyectos != null) {
						if (investigadorProyectos.getProyectosInvestigador().size() > 0) {
							Iterator<InvestigadorProyecto> itp = investigadorProyectos.getProyectosInvestigador().iterator();
							while (itp.hasNext()) {
								InvestigadorProyecto investigProyectos = itp.next();
								if (investigProyectos.getTipo().getId().equals("AL")
										|| investigProyectos.getTipo().getId().equals("ADL")
										|| investigProyectos.getTipo().getTipoModalidad().indexOf("CJI") > -1
										|| investigProyectos.getTipo().getTipoModalidad().indexOf("FHP") > -1
										|| investigProyectos.getTipo().getTipoModalidad().indexOf("CTV") > -1
										|| investigProyectos.getTipo().getId().equals("ALL")
										|| investigProyectos.getTipo().getId().equals("PLDC")
										|| investigProyectos.getTipo().getId().equals("IPUR")
										|| investigProyectos.getTipo().getId().equals("DUCMC")
										|| investigProyectos.getTipo().getId().equals("DUDFJC")
										|| investigProyectos.getTipo().getId().equals("DUMNG")
										|| investigProyectos.getTipo().getId().equals("DUPN")
										|| investigProyectos.getTipo().getId().equals("POSD")
										|| investigProyectos.getTipo().getId().equals("DINMLCF")) {
									isEstudianteLider = true;
//									sesion.setAttribute("esEstudianteLider", true);
									break;
								}
							}
						}
					}
					Investigador investigadorGrupos = obtenerInvestigadorGruposLiviano(investigador.getId());
					if (investigadorGrupos != null) {
						if (investigadorGrupos.getGruposInvestigador().size() > 0) {
							Iterator<InvestigadorGrupo> itp = investigadorGrupos.getGruposInvestigador().iterator();
							while (itp.hasNext()) {
								InvestigadorGrupo investigGrupos = itp.next();
								if (investigGrupos.getTipo().equals("AL")) {
									isEstudianteLider = true;
//									sesion.setAttribute("esEstudianteLider", true);
									break;
								}
							}
						}
					}
					List<SemilleroIntegrante> investigadorSemilleros = obtenerSemillerosInvestigador(
							investigador.getId());
					if (investigadorSemilleros != null) {
						for (SemilleroIntegrante si : investigadorSemilleros) {
							if (si.esEstudianteLider()) {
								isEstudianteLider = true;
//								sesion.setAttribute("esEstudianteLider", true);
								break;
							}
						}
					}
					if (!isEstudianteLider) {
						if (isEvaluador || isLaboratorios) {
							p.getRoles().remove(rolInvestigador);
						} else
							return null;
					}
				}
			}
		}
		return p;
	}

	@SuppressWarnings("unchecked")
	private List<SemilleroIntegrante> obtenerSemillerosInvestigador(IdPersona id) {
		return getHibernateTemplate().find("from SemilleroIntegrante si where si.integrante.id.documento='"
				+ id.getDocumento() + "' and si.integrante.id.tipoDocumento='" + id.getTipoDocumento() + "'");
	}

	public Rol obtenerRol(String id) throws DataAccessException {
		return (Rol) getHibernateTemplate().get(Rol.class, id);
	}

	public List<InvestigadorGrupo> obtenerGruposInvestigador(Investigador investigador) throws DataAccessException {
		Session session = getSession();
		String hql = "select ig from InvestigadorGrupo ig where ig.investigador.id.documento = '"
				+ investigador.getId().getDocumento() + "' and ig.investigador.id.tipoDocumento = '"
				+ investigador.getId().getTipoDocumento()
				+ "' and ig.grupo.estadoGrupo.id not in ('D')  order by ig.id asc";
		List resultado = getHibernateTemplate().find(hql);
		session.close();
		List<InvestigadorGrupo> listGrupos = resultado;
		return listGrupos;
	}

	public List obtenerGruposInvestigadorMovilidades(Investigador investigador) throws DataAccessException {

		List result = new ArrayList();

		Session session = getSession();

		String query = "Select G.GRU_ID idGrupo, G.GRU_NOMBRE nombreGrupo, IG.ING_TIPO tipoInvestigador"
				+ " from HER_GRUPO G, HER_INVESTIGADOR_GRUPO IG " + "WHERE G.GRU_ID = IG.GRU_ID AND IG.INV_ID = '"
				+ investigador.getId().getDocumento() + "' and " + "IG.TDO_ID = '"
				+ investigador.getId().getTipoDocumento() + "' and G.EGR_ID = 'A' order by ig.ing_id";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		List investigadoresGrupo = sqlQuery1.addScalar("idGrupo", Hibernate.LONG)
				.addScalar("nombreGrupo", Hibernate.STRING).addScalar("tipoInvestigador", Hibernate.STRING).list();

		session.close();

		Iterator it = investigadoresGrupo.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			InvestigadorGrupo investigadorGrupo = new InvestigadorGrupo();
			Grupo grupo = new Grupo();
			grupo.setId((Long) name[0]);
			grupo.setNombre((String) name[1]);
			investigadorGrupo.setGrupo(grupo);
			investigadorGrupo.setTipo((String) name[2]);
			result.add(investigadorGrupo);
		}
		return result;
	}

	public List obtenerDocenteinvestigador(String persona) {

		Session session = getSession();
		Statement st = null;
		ResultSet rs;

		List myList = new ArrayList();
		try {
			Investigador inv;
			CategoriaInvestigador cat = new CategoriaInvestigador();
			session = getSession();
			st = session.connection().createStatement();
			String query = "select cat_id " + "from her_investigador " + "where inv_id = '" + persona + "'";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				inv = new Investigador();
				cat.setId(new Long(rs.getString("cat_id")));
				inv.setCategoriaInvestigador(cat);
				myList.add(inv);
			}
			return myList;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return myList;
	}

	public Estudiante obtenerEstudiante(IdPersona id) throws DataAccessException {
		Session session = getSession();

		Estudiante e = (Estudiante) session.createCriteria(Estudiante.class)
				.setFetchMode("categoriaInvestigador", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

		session.close();
		return e;
	}

	public List obtenerGruposInvestigadorPrincipal(IdPersona id) throws DataAccessException {
		List listGrupos = new ArrayList();
		Session session = getSession();

		Investigador i = (Investigador) session.createCriteria(Investigador.class).add(Restrictions.idEq(id))
				.uniqueResult();

		Set setGruposInvestigador = i.getGruposInvestigador();
		Iterator itSet = setGruposInvestigador.iterator();
		while (itSet.hasNext()) {
			InvestigadorGrupo invG = (InvestigadorGrupo) itSet.next();
			if (invG.getTipo().equals(InvestigadorGrupo.LIDER)) {
				listGrupos.add(invG.getGrupo());
			}
		}

		session.close();
		return listGrupos;
	}

	public List<Grupo> obtenerGruposInvestigadorLider(IdPersona id) throws DataAccessException {
		List<Grupo> listGrupos = new ArrayList<Grupo>();
		Session session = getSession();

		Investigador i = (Investigador) session.createCriteria(Investigador.class).add(Restrictions.idEq(id))
				.uniqueResult();

		Set<InvestigadorGrupo> setGruposInvestigador = i.getGruposInvestigador();
		Iterator<InvestigadorGrupo> itSet = setGruposInvestigador.iterator();
		while (itSet.hasNext()) {
			InvestigadorGrupo invG = itSet.next();
			if (invG.getTipo().equals(InvestigadorGrupo.LIDER)) {
				listGrupos.add(invG.getGrupo());
			}
		}

		session.close();
		return listGrupos;
	}

	public List obtenerGruposInvestigadorLiderIntersede(IdPersona id) throws DataAccessException {
		List listGrupos = new ArrayList();
		Session session = getSession();

		Investigador i = (Investigador) session.createCriteria(Investigador.class).add(Restrictions.idEq(id))
				.uniqueResult();

		Set setGruposInvestigador = i.getGruposInvestigador();
		Iterator itSet = setGruposInvestigador.iterator();
		while (itSet.hasNext()) {
			InvestigadorGrupo invG = (InvestigadorGrupo) itSet.next();
			if (invG.getTipo().equals(InvestigadorGrupo.LIDER)) {
				listGrupos.add(invG.getGrupo());
			}
		}

		session.close();
		return listGrupos;
	}

	public Evaluador obtenerEvaluadorProyectos(IdPersona id) throws DataAccessException {
		Session session = getSession();

		Evaluador i = (Evaluador) session.createCriteria(Evaluador.class)
				.setFetchMode("proyectosEvaluador", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

		session.close();

		return i;
	}

	/****
	 * metodo que retorna una lista con los datos del posible evaluador para un
	 * proyecto dado a quien se le ha enviado ya un correo
	 ****/
	public List obtenerEvaluadoresCorreoProyecto(Proyecto id) {

		Session s = getSession();
		Statement st = null;
		ResultSet rs;
		List listaEvaluadores = new ArrayList();

		try {
			EvaluadorCorreo evaco = null;
			s = getSession();
			st = s.connection().createStatement();
			String query = "select pry_id,inv_id,contactado,nombre,respuesta from her_evaluador_correo e where e.pry_id = "
					+ id.getId();
			
			rs = st.executeQuery(query);

			while (rs.next()) {
				evaco = new EvaluadorCorreo();
				evaco.setInvestigador(rs.getString("inv_id"));
				evaco.setContactado(rs.getString("contactado"));
				evaco.setNombre(rs.getString("nombre"));
				evaco.setProyecto(rs.getLong("pry_id"));
				evaco.setRespuesta(rs.getString("respuesta"));
				listaEvaluadores.add(evaco);
				evaco = null;
			}
			return listaEvaluadores;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
		return listaEvaluadores;
	}

	public Evaluador obtenerEvaluador(IdPersona id) throws DataAccessException {
		Session session = getSession();

		Evaluador i = (Evaluador) session.createCriteria(Evaluador.class).add(Restrictions.idEq(id)).uniqueResult();

		session.close();

		return i;
	}

	/**
	 * Obtiene los inve3stigadores con el criterio de busqueda de nombre1, nombre2,
	 * apellido1, apellido2 y los devuelve en una lista
	 * 
	 * @param nombre1
	 * @param nombre2
	 * @param apellido1
	 * @param apellido2
	 * @return
	 */
	public List obtenerInvestigadores(String nombre1, String nombre2, String apellido1, String apellido2,
			Boolean exact) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Investigador.class);
		criteria = criteria.setFetchMode("dependencia", FetchMode.JOIN);
		MatchMode mm = MatchMode.ANYWHERE;
		if (exact) {
			mm = MatchMode.EXACT;
		}
		if (!nombre1.equals("")) {
			criteria.add(Expression.ilike("nombre1", nombre1, mm));
		}
		if (!nombre2.equals("")) {
			criteria.add(Expression.ilike("nombre2", nombre2, mm));
		}
		if (!apellido1.equals("")) {
			criteria.add(Expression.ilike("apellido1", apellido1, mm));
		}
		if (!apellido2.equals("")) {
			criteria.add(Expression.ilike("apellido2", apellido2, mm));
		}
		criteria.addOrder(Order.asc("apellido1"));
		criteria.addOrder(Order.asc("apellido2"));
		criteria.addOrder(Order.asc("nombre1"));
		List investigadores = criteria.list();
		session.close();
		return investigadores;
	}

	/**
	 * Obtiene los inve3stigadores con el criterio de busqueda de nombre1, nombre2,
	 * apellido1, apellido2 y los devuelve en una lista
	 * 
	 * @param nombre1
	 * @param nombre2
	 * @param apellido1
	 * @param apellido2
	 * @return
	 */
	public List obtenerInvestigadoresIndeferenteTildesYMayusculas(String nombre1, String nombre2, String apellido1,
			String apellido2) {
		Session session = getSession();

		Criteria criteria = session.createCriteria(Investigador.class);
		criteria = criteria.setFetchMode("dependencia", FetchMode.JOIN);

		if (!nombre1.equals("")) {
			// criteria.add(Expression.ilike("nombre1", nombre1,
			// MatchMode.ANYWHERE));
			criteria.add(Expression.sqlRestriction(ReemplazaAcentos.queryQuitaTildes("per_nombre1") + " like '%"
					+ ReemplazaAcentos.quitarTildes(nombre1).toLowerCase() + "%' "));
		}
		if (!nombre2.equals("")) {
			// criteria.add(Expression.ilike("nombre2", nombre2,
			// MatchMode.ANYWHERE));
			criteria.add(Expression.sqlRestriction(ReemplazaAcentos.queryQuitaTildes("per_nombre2") + " like '%"
					+ ReemplazaAcentos.quitarTildes(nombre2).toLowerCase() + "%' "));
		}
		if (!apellido1.equals("")) {
			criteria.add(Expression.sqlRestriction(ReemplazaAcentos.queryQuitaTildes("per_apellido1") + " like '%"
					+ ReemplazaAcentos.quitarTildes(apellido1).toLowerCase() + "%' "));
			// criteria.add(Expression.ilike("apellido1", apellido1,
			// MatchMode.ANYWHERE));
		}
		if (!apellido2.equals("")) {
			// criteria.add(Expression.ilike("apellido2", apellido2,
			// MatchMode.ANYWHERE));
			criteria.add(Expression.sqlRestriction(ReemplazaAcentos.queryQuitaTildes("per_apellido2") + " like '%"
					+ ReemplazaAcentos.quitarTildes(apellido2).toLowerCase() + "%' "));
		}
		// criteria.add(conjuction);
		criteria.addOrder(Order.asc("apellido1"));
		criteria.addOrder(Order.asc("apellido2"));
		criteria.addOrder(Order.asc("nombre1"));

		List investigadores = criteria.list();

		session.close();
		return investigadores;

	}

	public int obtenerNumeroDeInvestigadoresCategoriaDeProyecto(Proyecto proyecto,
			CategoriaInvestigador categoriaInvestigador) throws DataAccessException {

		Session session = getSession();

		String queryString = "select count(*) from InvestigadorProyecto  i where  i.proyecto = :proyecto AND i.investigador.categoriaInvestigador=:categoriaInvestigador ";
		Query query = session.createQuery(queryString);

		query.setEntity("proyecto", proyecto);
		query.setEntity("categoriaInvestigador", categoriaInvestigador);

		Integer count = (Integer) query.uniqueResult();

		session.close();
		return count.intValue();

	}

	public InvestigadorInterno obtenerInvestigadorClasificacionConocimiento(IdPersona id) throws DataAccessException {
		Session session = getSession();

		InvestigadorInterno i = (InvestigadorInterno) session.createCriteria(InvestigadorInterno.class)
				.setFetchMode("clasificacionesConocimiento", FetchMode.JOIN).setFetchMode("dependencia", FetchMode.JOIN)
				.add(Restrictions.idEq(id)).uniqueResult();

		session.close();

		return i;
	}

	public List obtenerInvestigadoresPorNombresApellidosDependencia(String nombre1, String nombre2, String apellido1,
			String apellido2, Dependencia dependencia, String tipoDependencia) {
		Session session = getSession();

		Criterion crit_nombre1 = Restrictions.ilike("nombre1", nombre1, MatchMode.ANYWHERE);
		Criterion crit_nombre2 = Restrictions.ilike("nombre2", nombre2, MatchMode.ANYWHERE);
		Criterion crit_apellido1 = Restrictions.ilike("apellido1", apellido1, MatchMode.ANYWHERE);
		Criterion crit_apellido2 = Restrictions.ilike("apellido2", apellido2, MatchMode.ANYWHERE);

		Junction conjuction = Restrictions.conjunction();

		conjuction = conjuction.add(crit_nombre1);
		conjuction = conjuction.add(crit_nombre2);
		conjuction = conjuction.add(crit_apellido1);
		conjuction = conjuction.add(crit_apellido2);

		Criteria criteria = session.createCriteria(Investigador.class);

		criteria.add(conjuction);

		if (tipoDependencia.equals(Dependencia.TIPO_DEPENDENCIA_FACULTAD)) {
			Criteria facultadCriteria = criteria.createCriteria("dependencia");
			facultadCriteria.add(Expression.eq("facultad", dependencia));

		} else if (tipoDependencia.equals(Dependencia.TIPO_DEPENDENCIA_SEDE)) {
			Sede sede = new Sede();
			sede.setId(new Long(dependencia.getId()));
			Criteria sedeCriteria = criteria.createCriteria("dependencia");
			sedeCriteria.add(Expression.eq("sede", sede));

		}

		criteria.addOrder(Order.asc("apellido1"));
		criteria.addOrder(Order.asc("apellido2"));
		criteria.addOrder(Order.asc("nombre1"));

		List investigadores = criteria.list();

		session.close();
		return investigadores;
	}

	public List obtenerInvestigadoresPorNombresApellidosDependenciaIndiferenteTildesYMayusculas(String nombre1,
			String nombre2, String apellido1, String apellido2, Dependencia dependencia, String tipoDependencia) {
		Session session = getSession();

		Criterion crit_nombre1 = Expression.sqlRestriction(ReemplazaAcentos.queryQuitaTildes("per_nombre1") + " like '%"
				+ ReemplazaAcentos.quitarTildes(nombre1).toLowerCase() + "%' ");
		Criterion crit_nombre2 = Expression.sqlRestriction(ReemplazaAcentos.queryQuitaTildes("per_nombre2") + " like '%"
				+ ReemplazaAcentos.quitarTildes(nombre2).toLowerCase() + "%' ");
		Criterion crit_apellido1 = Expression.sqlRestriction(ReemplazaAcentos.queryQuitaTildes("per_apellido1")
				+ " like '%" + ReemplazaAcentos.quitarTildes(apellido1).toLowerCase() + "%' ");
		Criterion crit_apellido2 = Expression.sqlRestriction(ReemplazaAcentos.queryQuitaTildes("per_apellido2")
				+ " like '%" + ReemplazaAcentos.quitarTildes(apellido2).toLowerCase() + "%' ");
		Junction conjuction = Restrictions.conjunction();

		conjuction = conjuction.add(crit_nombre1);
		conjuction = conjuction.add(crit_nombre2);
		conjuction = conjuction.add(crit_apellido1);
		conjuction = conjuction.add(crit_apellido2);

		Criteria criteria = session.createCriteria(Investigador.class);

		criteria.add(conjuction);

		if (tipoDependencia.equals(Dependencia.TIPO_DEPENDENCIA_FACULTAD)) {
			Criteria facultadCriteria = criteria.createCriteria("dependencia");
			facultadCriteria.add(Expression.eq("facultad", dependencia));

		} else if (tipoDependencia.equals(Dependencia.TIPO_DEPENDENCIA_SEDE)) {
			Sede sede = new Sede();
			sede.setId(new Long(dependencia.getId()));
			Criteria sedeCriteria = criteria.createCriteria("dependencia");
			sedeCriteria.add(Expression.eq("sede", sede));

		}

		criteria.addOrder(Order.asc("apellido1"));
		criteria.addOrder(Order.asc("apellido2"));
		criteria.addOrder(Order.asc("nombre1"));

		List investigadores = criteria.list();

		session.close();
		return investigadores;
	}

	public List obtenerPersonasxRol(Rol r) throws DataAccessException {
		Session session = getSession();

		Criteria criteria = session.createCriteria(Persona.class);
		Criteria criteriaRoles = criteria.createCriteria("roles");
		criteriaRoles.add(Restrictions.idEq(r.getId()));
		criteria.addOrder(Order.asc("nombre1"));
		criteria.addOrder(Order.asc("apellido1"));
		criteria.addOrder(Order.asc("apellido2"));
		List listaPersonas = criteria.list();

		session.close();
		return listaPersonas;
	}

	public List obtenerPersonasxRolId(Rol r, String id, String tipodoc) throws DataAccessException {
		Session session = getSession();

		Criteria criteria = session.createCriteria(Persona.class);
		Criteria criteriaRoles = criteria.createCriteria("roles");
		criteriaRoles.add(Restrictions.idEq(r.getId()));

		IdPersona idoc = new IdPersona(id, tipodoc);
		criteria.add(Expression.eq("id", idoc));

		criteria.addOrder(Order.asc("nombre1"));
		criteria.addOrder(Order.asc("apellido1"));
		criteria.addOrder(Order.asc("apellido2"));
		List listaPersonas = criteria.list();

		session.close();
		return listaPersonas;
	}

	public List obtenerPersonasxRolIdxDpnId(String rol, String dpn) throws DataAccessException {
		Session s = getSession();
		Statement st = null;
		ResultSet rs;
		List listaPersonasXrolXDpn = new ArrayList();
		try {
			Persona per = null;
			IdPersona idPer = null;
			s = getSession();
			st = s.connection().createStatement();
			String query = "select per.per_id doc,per.tdo_id tipo,per.per_nombre1 nombre1,per.per_nombre2 nombre2,per.per_apellido1 apellido1,per.per_apellido2 apellido2, per.per_email email "
					+ "from her_persona per inner join her_persona_rol pr on per.tdo_id = pr.tdo_id and per.per_id = pr.PER_ID inner join her_investigador_interno ii on pr.TDO_ID = ii.TDO_ID and pr.PER_ID = ii.INV_ID inner join her_dependencia d on d.DPN_ID = ii.DPN_ID2 "
					+ "where pr.ROL_ID  like '" + rol + "' and d.dpn_facultad ='" + dpn + "' AND (pr.HPR_FECHA_FIN IS NULL OR pr.HPR_FECHA_FIN >= CURRENT_DATE)";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				per = new Persona();
				idPer = new IdPersona();
				idPer.setDocumento(rs.getString("doc"));
				idPer.setTipoDocumento(rs.getString("tipo"));
				per.setId(idPer);
				per.setNombre1(rs.getString("nombre1"));
				per.setNombre2(rs.getString("nombre2"));
				per.setApellido1(rs.getString("apellido1"));
				per.setApellido2(rs.getString("apellido2"));
				per.setEmail(rs.getString("email"));
				listaPersonasXrolXDpn.add(per);
				per = null;
			}
			return listaPersonasXrolXDpn;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
		return listaPersonasXrolXDpn;
	}

	public List<Persona> obtenerPersonasxRolIdxSedeId(String rol, Long sedeId) throws DataAccessException {
		Session s = getSession();
		Statement st = null;
		ResultSet rs;
		List<Persona> listaPersonasXrolxSedeId = new ArrayList<Persona>();
		try {
			Persona per = null;
			IdPersona idPer = null;
			s = getSession();
			st = s.connection().createStatement();
			String query = "select per.per_id doc,per.tdo_id tipo,per.per_nombre1 nombre1,per.per_nombre2 nombre2,per.per_apellido1 apellido1,per.per_apellido2 apellido2, per.per_email email "
					+ "from her_persona per inner join her_persona_rol pr on per.tdo_id = pr.tdo_id and per.per_id = pr.PER_ID inner join her_investigador_interno ii on pr.TDO_ID = ii.TDO_ID and pr.PER_ID = ii.INV_ID inner join her_dependencia d on d.DPN_ID = ii.DPN_ID "
					+ "where pr.ROL_ID  like '" + rol + "' and d.SED_ID =" + sedeId;
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				per = new Persona();
				idPer = new IdPersona();
				idPer.setDocumento(rs.getString("doc"));
				idPer.setTipoDocumento(rs.getString("tipo"));
				per.setId(idPer);
				per.setNombre1(rs.getString("nombre1"));
				per.setNombre2(rs.getString("nombre2"));
				per.setApellido1(rs.getString("apellido1"));
				per.setApellido2(rs.getString("apellido2"));
				per.setEmail(rs.getString("email"));
				listaPersonasXrolxSedeId.add(per);
				per = null;
			}
			return listaPersonasXrolxSedeId;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
		return listaPersonasXrolxSedeId;
	}

	public List obtenerPersonasUnidadAdministrativa(String dpn, String sede) throws DataAccessException {
		Session s = getSession();
		Statement st = null;
		ResultSet rs;
		List listaPersonasXrolXDpn = new ArrayList();
		try {
			Persona per = null;
			IdPersona idPer = null;
			s = getSession();
			st = s.connection().createStatement();
			String query = "select per.per_id doc,per.tdo_id tipo,per.per_nombre1 nombre1,per.per_nombre2 nombre2,per.per_apellido1 apellido1,per.per_apellido2 apellido2, per.per_email email "
					+ "from her_persona per inner join her_persona_rol pr on per.tdo_id = pr.tdo_id and per.per_id = pr.PER_ID inner join her_investigador_interno ii on pr.TDO_ID = ii.TDO_ID and pr.PER_ID = ii.INV_ID inner join her_dependencia d on d.DPN_ID = ii.DPN_ID2"
					+ " where pr.ROL_ID  like 'UA' and d.dpn_facultad ='" + dpn + "' " + "union "
					+ "select per.per_id doc, per.tdo_id tipo, per.per_nombre1 nombre1, per.per_nombre2 nombre2, per.per_apellido1 apellido1, per.per_apellido2 apellido2, per.per_email email "
					+ "from her_persona per inner join her_persona_rol pr on per.tdo_id = pr.tdo_id and per.per_id = pr.PER_ID "
					+ "inner join her_investigador_interno ii on pr.TDO_ID = ii.TDO_ID and pr.PER_ID = ii.INV_ID "
					+ "inner join her_dependencia d on d.DPN_ID = ii.DPN_ID2 " + "where pr.ROL_ID  like 'UA' "
					+ "and d.dpn_ID in (decode (d.SED_ID, '5','5125')) " + "and d.SED_ID ='" + sede + "'";

			
			rs = st.executeQuery(query);
			while (rs.next()) {
				per = new Persona();
				idPer = new IdPersona();
				idPer.setDocumento(rs.getString("doc"));
				idPer.setTipoDocumento(rs.getString("tipo"));
				per.setId(idPer);
				per.setNombre1(rs.getString("nombre1"));
				per.setNombre2(rs.getString("nombre2"));
				per.setApellido1(rs.getString("apellido1"));
				per.setApellido2(rs.getString("apellido2"));
				per.setEmail(rs.getString("email"));
				listaPersonasXrolXDpn.add(per);
				per = null;
			}
			return listaPersonasXrolXDpn;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
		return listaPersonasXrolXDpn;
	}

	public List obtenerListaCoordinadores(String persona) {

		Session s = getSession();
		Statement st = null;
		ResultSet rs;
		List listaCoordinadores = new ArrayList();
		try {
			Persona per = null;
			IdPersona idPer = null;
			s = getSession();
			st = s.connection().createStatement();
			String query = "select per.per_id doc,per.tdo_id tipo,per.per_nombre1 nombre1,per.per_nombre2 nombre2,per.per_apellido1 apellido1,per.per_apellido2 apellido2 "
					+ "from her_coordinador_asesor coo,her_persona per " + "where coo.per_id = '" + persona + "' and "
					+ "coo.coor_id = per.per_id order by per.per_nombre1 asc";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				per = new Persona();
				idPer = new IdPersona();
				idPer.setDocumento(rs.getString("doc"));
				idPer.setTipoDocumento(rs.getString("tipo"));
				per.setId(idPer);
				per.setNombre1(rs.getString("nombre1"));
				per.setNombre2(rs.getString("nombre2"));
				per.setApellido1(rs.getString("apellido1"));
				per.setApellido2(rs.getString("apellido2"));
				listaCoordinadores.add(per);
				per = null;
			}
			return listaCoordinadores;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
		return listaCoordinadores;
	}

	public List obtenerListaCoordinadoresEditorial(String persona) {

		Session s = getSession();
		Statement st = null;
		ResultSet rs;
		List listaCoordinadores = new ArrayList();
		try {
			Persona per = null;
			IdPersona idPer = null;
			s = getSession();
			st = s.connection().createStatement();
			String query = "select per.per_id doc,per.tdo_id tipo,per.per_nombre1 nombre1,per.per_nombre2 nombre2,per.per_apellido1 apellido1,per.per_apellido2 apellido2 "
					+ "from her_coordinador_asesor_ed coo,her_persona per " + "where coo.per_id = '" + persona
					+ "' and " + "coo.coor_id = per.per_id ";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				per = new Persona();
				idPer = new IdPersona();
				idPer.setDocumento(rs.getString("doc"));
				idPer.setTipoDocumento(rs.getString("tipo"));
				per.setId(idPer);
				per.setNombre1(rs.getString("nombre1"));
				per.setNombre2(rs.getString("nombre2"));
				per.setApellido1(rs.getString("apellido1"));
				per.setApellido2(rs.getString("apellido2"));
				listaCoordinadores.add(per);
				per = null;
			}
			return listaCoordinadores;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
		return listaCoordinadores;
	}

	public List obtenerProyectosAsesor(IdPersona id) {
		Session session = getSession();

		Persona p = (Persona) session.createCriteria(Persona.class).setFetchMode("proyectosAsesor", FetchMode.JOIN)
				.add(Restrictions.idEq(id)).uniqueResult();

		session.close();
		List l = new ArrayList(p.getProyectosAsesor());
		// TODO: POSTERIORMENTE REALIZAR LA CONSULTA COMO DEBE SER
		for (int i = 0; i < l.size(); i++) {
			Proyecto pr = (Proyecto) l.get(i);
			if (!pr.getEstadoProyecto().getId().equals("P")) {
				l.remove(i);
			}
		}
		return l;
	}

	public List obtenerProyectosAsesor(IdPersona id, Modalidad m) {
		// FUNCIONA
		Session session = getSession();

		Persona p = (Persona) session.createCriteria(Persona.class).setFetchMode("proyectosAsesor", FetchMode.JOIN)
				.setFetchMode("proyectosAsesor.evaluadoresProyecto", FetchMode.JOIN).add(Restrictions.idEq(id))
				.uniqueResult();

		session.close();
		List l = new ArrayList(p.getProyectosAsesor());
		List laux = new ArrayList();
		laux.addAll(l);
		// TODO: POSTERIORMENTE REALIZAR LA CONSULTA COMO DEBE SER
		Iterator it = laux.iterator();
		while (it.hasNext()) {
			Proyecto pr = (Proyecto) it.next();
			if (!pr.getEstadoProyecto().getId().equals("P")
					|| !(pr.getModalidad().getId().longValue() == m.getId().longValue())) {
				l.remove(pr);
			}
		}
		return l;
	}

	public List obtenerProyectosAsesorConListaEvaluadoresProyecto(IdPersona id, Modalidad m) {
		// FUNCIONA
		Session session = getSession();

		Persona p = (Persona) session.createCriteria(Persona.class).setFetchMode("proyectosAsesor", FetchMode.JOIN)
				.setFetchMode("proyectosAsesor.evaluadoresProyecto", FetchMode.JOIN).add(Restrictions.idEq(id))
				.uniqueResult();

		session.close();
		List l = new ArrayList(p.getProyectosAsesor());
		List laux = new ArrayList();
		laux.addAll(l);
		// TODO: POSTERIORMENTE REALIZAR LA CONSULTA COMO DEBE SER
		Iterator it = laux.iterator();

		while (it.hasNext()) {
			Proyecto pr = (Proyecto) it.next();
			if (!pr.getEstadoProyecto().getId().equals("P")
					|| !(pr.getModalidad().getId().longValue() == m.getId().longValue())) {
				l.remove(pr);
				System.out.println("Salida2");
			}
			if (pr.getEstadoProyecto().equals(EstadoProyecto.BORRADO)) {
				l.remove(pr);
				System.out.println("Salida3");
			}
		}
		System.out.println("Tamaño lista :" + l.size());
		return l;
	}

	public List obtenerPosiblesEvaluadoresxPalabraClave(Proyecto p, Modalidad m) {
		Session session = getSession();

		Proyecto p1 = (Proyecto) session.createCriteria(Proyecto.class).setFetchMode("palabrasClaves", FetchMode.JOIN)
				.add(Restrictions.idEq(p.getId())).uniqueResult();
		Set posiblesEvaluadores = new HashSet();
		List l = p1.getListaPalabras();

		Iterator it = l.iterator();
		while (it.hasNext()) {
			PalabraClave pc = (PalabraClave) it.next();
			String restriccion = "(TDO_ID,INV_ID) IN " + "(SELECT TDO_ID, INV_ID FROM HER_INVESTIGADOR_PROYECTO WHERE "
					+ "((TDO_ID,INV_ID) IN (SELECT TDO_ID,INV_ID FROM HER_INVESTIGADOR_PROYECTO where PRY_ID NOT IN (SELECT PRY_ID from HER_PROYECTO where MOD_ID in (select CON_ID from HER_CONVOCATORIA where CNP_ID="
					+ m.getId() + ")))) AND PRY_ID IN (select PRY_ID FROM her_proyecto where PRY_ABSTRACT LIKE LOWER('%"
					+ pc.getPalabra() + "%') or PRY_ABSTRACT LIKE LOWER('%" + pc.getPalabra() + "%')))";
			List l1 = session.createCriteria(Investigador.class).add(Restrictions.sqlRestriction(restriccion)).list();
			posiblesEvaluadores.addAll(l1);
		}

		session.close();
		return new ArrayList(posiblesEvaluadores);
	}

	public List obtenerPosiblesEvaluadoresInternos(ClasificacionConocimiento cla, Modalidad m) {
		String restriccion = "(TDO_ID,INV_ID) IN (SELECT TDO_ID,INV_ID FROM HER_INVESTIGADOR_CLA_CON where CLC_ID='"
				+ cla.getId()
				+ "' AND (TDO_ID,INV_ID) NOT IN (SELECT TDO_ID,INV_ID FROM HER_INVESTIGADOR_PROYECTO where PRY_ID IN (SELECT PRY_ID from HER_PROYECTO where MOD_ID in (select CON_ID from HER_CONVOCATORIA where CNP_ID="
				+ m.getId() + "))))";
		System.out.println("buscndo investigadores interno para " + cla.getId());
		Session session = getSession();

		Criteria criteria = session.createCriteria(Investigador.class).add(Restrictions.sqlRestriction(restriccion));
		// " select " +
		// " from Investigador i" +
		// " where i.clasificacionConocimiento.id"

		Set s = new HashSet();
		s.addAll(criteria.list());
		System.out.println("se encontraron +" + s.size());

		session.close();
		return new ArrayList(s);
	}

	public List obtenerPosiblesEvaluadoresInternos(ClasificacionConocimiento cla, Set grupos) {
		Session session = getSession();

		Criteria criteria = session.createCriteria(Investigador.class);
		Criteria criteriaClasificacion = criteria.createCriteria("clasificacionesConocimiento");
		criteriaClasificacion.add(Restrictions.idEq(cla.getId()));
		List l = criteria.list();

		session.close();

		return l;
	}

	public List obtenerPosiblesEvaluadoresExternos(ClasificacionConocimiento cla) {
		Session session = getSession();

		Criteria criteria = session.createCriteria(PosibleEvaluador.class);
		criteria.add(Restrictions.eq("clasificacionConocimiento", cla));
		Set s = new HashSet();
		s.addAll(criteria.list());

		session.close();

		return new ArrayList(s);
	}

	public InvestigadorExterno obtenerInvestigadorExternoCompleto(IdPersona id) {
		Session session = getSession();

		InvestigadorExterno p = (InvestigadorExterno) session.createCriteria(InvestigadorExterno.class)
				.setFetchMode("roles", FetchMode.JOIN).setFetchMode("ciudadDomicilio", FetchMode.JOIN)
				.setFetchMode("institucion", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

		session.close();
		return p;
	}

	public InvestigadorInterno obtenerInvestigadorInternoCompleto(IdPersona id) {
		Session session = getSession();

		InvestigadorInterno p = (InvestigadorInterno) session.createCriteria(InvestigadorInterno.class)
				.setFetchMode("roles", FetchMode.JOIN).setFetchMode("ciudadDomicilio", FetchMode.JOIN)
				.setFetchMode("dependencia", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

		session.close();
		return p;
	}

	public List obtenerInvestigadoresXDependencia(Dependencia dependencia) {
		Session session = getSession();

		Criteria criteria = session.createCriteria(InvestigadorInterno.class)
				.add(Restrictions.eq("dependencia", dependencia));
		// Criterion uno = Restrictions.eq("dependencia",dependencia);
		// Criterion dos =
		// Restrictions.eq("dependencia",dependencia.getPadre());
		// LogicalExpression orExp = Restrictions.or(uno,dos);
		// criteria.add(orExp);
		// criteria.addOrder( Order.asc("apellido1"));
		List l = criteria.list();

		session.close();

		return l;
	}

	/**
	 * Se obtienen los participantes de proyectos de la convocatoria dada y que sus
	 * estados esten en: Propuesto, Aprobado, Ingresando o Activo
	 */
	public List obtenerParticipantesConvocatoria(Modalidad modalidad) {
		Session session = getSession();

		// Estados del proyecto
		EstadoProyecto ePropuesto, eAprobado, eIngresando, eActivo;
		ePropuesto = new EstadoProyecto();
		ePropuesto.setId(EstadoProyecto.PROPUESTO);
		eAprobado = new EstadoProyecto();
		eAprobado.setId(EstadoProyecto.APROBADO);
		eIngresando = new EstadoProyecto();
		eIngresando.setId(EstadoProyecto.INGRESANDO);
		eActivo = new EstadoProyecto();
		eActivo.setId(EstadoProyecto.ACTIVO);
		List estadosProyecto = new ArrayList();
		estadosProyecto.add(ePropuesto);
		estadosProyecto.add(eAprobado);
		estadosProyecto.add(eIngresando);
		estadosProyecto.add(eActivo);

		Criteria criteriaInvestigador = session.createCriteria(Investigador.class);
		Criteria criteriaProyectos = criteriaInvestigador.createCriteria("proyectosInvestigador");
		Criteria criteriaProyecto = criteriaProyectos.createCriteria("proyecto");
		criteriaProyecto.add(Restrictions.eq("modalidad", modalidad));
		criteriaProyecto.add(Restrictions.in("estadoProyecto", estadosProyecto));

		List l = criteriaInvestigador.list();

		session.close();

		return l;
	}

	public Long obtenerNumeroProyectosEvaluador(Persona p, Modalidad m) {
		String restriccion = "(TDO_ID='" + p.getId().getTipoDocumento() + "' AND INV_ID='" + p.getId().getDocumento()
				+ "' AND PRY_ID IN (SELECT PRY_ID from HER_PROYECTO where MOD_ID in (select CON_ID from HER_CONVOCATORIA where CNP_ID="
				+ m.getId() + ")))";
		Session session = getSession();

		Criteria criteria = session.createCriteria(ProyectoEvaluador.class)
				.add(Restrictions.sqlRestriction(restriccion));
		List l = criteria.list();

		session.close();
		return new Long(l.size());
	}

	public List obtenerlistaProyectosEvaluador(Proyecto p) {
		String restriccion = "(PRY_ID='" + p.getId().toString() + "')";
		Session session = getSession();

		Criteria criteria = session.createCriteria(ProyectoEvaluador.class)
				.add(Restrictions.sqlRestriction(restriccion));
		List l = criteria.list();

		session.close();
		return l;
	}

	public long generarClaveExterno() {
		long c = 0;

		Session session = getSession();
		Statement st;
		try {
			st = session.connection().createStatement();
			ResultSet rs;
			do {
				c = (long) (Math.random() * 100000) + 100000;
				st.execute(" select ine_clave from her_investigador_externo where ine_clave = " + c + "");
				rs = st.getResultSet();
				System.out.println(c);
			} while (rs.next());
		} catch (HibernateException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		session.close();
		return c;
	}

	public InvestigadorExterno buscarInvestigadorExternoId(String documento, String tipo) {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		InvestigadorExterno ie = new InvestigadorExterno();
		try {
			IdPersona idPer = null;
			session = getSession();
			st = session.connection().createStatement();
			String query = "SELECT per.inv_id doc, per.tdo_id tipo, per.ins_id ins " 
					+ "FROM her_investigador_externo per WHERE "
					+ "per.tdo_id = '"+tipo+"' AND per.inv_id = '" + documento + "'";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				idPer = new IdPersona();
				idPer.setDocumento(rs.getString("doc"));
				idPer.setTipoDocumento(rs.getString("tipo"));
				Institucion i = new Institucion();
				i.setId(rs.getString("ins"));
				ie.setInstitucion(i);
				ie.setId(idPer);
			}
			return ie;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return ie;
	}

	public InvestigadorInterno obtenerInvestigadorInternoDependenciaYFacultad(IdPersona id) {
		Session session = getSession();
		System.out.println("buscando facualtas");

		InvestigadorInterno p = (InvestigadorInterno) session.createCriteria(InvestigadorInterno.class)
				.setFetchMode("dependencia.facultad", FetchMode.JOIN)
				.setFetchMode("dependencia.departamento", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

		if (p != null) {

			System.out.println(p.getDependencia() == null ? ""
					: (p.getDependencia().getFacultad() == null ? "" : p.getDependencia().getFacultad().getNombre()));
			// p.getDependencia().getDepartamento();

			Hibernate.initialize(p.getDependencia());
		}
		session.close();
		return p;
	}

	public List<Persona> buscarPersonaInvestigadoresExternos(long clave) {
		Session session = getSession();

		Query q = session.createQuery(" select p " + " from Persona p, InvestigadorExterno ie "
				+ " where ie.contrasena=:contrasena and ie.id.tipoDocumento = p.id.tipoDocumento and "
				+ "p.id.documento=ie.id.documento");
		q.setLong("contrasena", clave);
		List<Persona> personas = q.list();

		if (personas != null) {
			Iterator<Persona> i = personas.iterator();
			while (i.hasNext()) {
				Persona personaTemporal = i.next();
				Hibernate.initialize(personaTemporal.getRoles());
				Hibernate.initialize(personaTemporal.getCiudadDomicilio());
			}
		}

		session.close();
		return personas;
	}

	public Object obtenerObjeto(Object clase, Object id, String f) throws DataAccessException {

		Session session = getSession();

		Object object;

		if (f != null && !f.equals("")) {
			object = session.createCriteria(clase.getClass()).add(Restrictions.idEq(id)).setFetchMode(f, FetchMode.JOIN)
					.uniqueResult();
		} else {
			object = session.createCriteria(clase.getClass()).add(Restrictions.idEq(id)).uniqueResult();
		}

		session.close();
		return object;
	}

	public List obtenerInvestigadoresPorAreaDeConocimiento(String nombreAreaConocimiento) {

		// Statement st=null;

		Session session = getSession();

		Query q = session.createQuery("select distinct i from Investigador i, ClasificacionConocimiento cc where "
				+ ReemplazaAcentos.queryQuitaTildes("cc.nombre")
				+ " like :palabra and i.clasificacionesConocimiento.id like (cc.id || '%') ");
		q.setString("palabra", "%" + ReemplazaAcentos.quitarTildes(nombreAreaConocimiento.toLowerCase()) + "%");

		List listaResultado = q.list();

		session.close();
		// }
		System.out.println(listaResultado.size());
		return listaResultado;
	}

	public List obtenerInvestigadoresPorDependenciaAreaDeConocimiento(String nombreAreaConocimiento,
			Dependencia dependencia, String tipo_dependencia) {
		System.out.println("Facultad!!" + dependencia.getNombre());

		Session session = getSession();

		Query q = null;
		if (tipo_dependencia.equals(Dependencia.TIPO_DEPENDENCIA_FACULTAD)) {

			q = session.createQuery("select distinct i from InvestigadorInterno i, ClasificacionConocimiento cc where "
					+ ReemplazaAcentos.queryQuitaTildes("cc.nombre")
					+ " like :palabra and i.clasificacionesConocimiento.id like (cc.id || '%')"
					+ "and i.dependencia.facultad = :f  ");
			q.setString("palabra", "%" + ReemplazaAcentos.quitarTildes(nombreAreaConocimiento.toLowerCase()) + "%");
			q.setEntity("f", dependencia);

		}
		List listaResultado = q.list();

		session.close();
		System.out.println("tamaño del resultado " + listaResultado.size());
		return listaResultado;
	}

	public Estudiante obtenerEstudianteXPersona(Persona p) {
		Session s = null;
		Estudiante estudiante = null;
		try {

			s = getSession();

			Query q = s.createQuery(" select e from Estudiante e where e.id.tipoDocumento=:tipoDocumento"
					+ " and e.id.documento=:documento");
			q.setString("tipoDocumento", p.getId().getTipoDocumento());
			q.setString("documento", p.getId().getDocumento());
			estudiante = (Estudiante) q.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
		return estudiante;
	}

	public Long obtenerIdCategoriaInvestigador(IdPersona id) {
		Session session = getSession();
		Query q = session.createQuery("" + "select i.categoriaInvestigador.id " + " from Investigador i"
				+ " where i.id.tipoDocumento=:tipoD and i.id.documento=:documento" + "");
		q.setString("tipoD", id.getTipoDocumento());
		q.setString("documento", id.getDocumento());
		Long resultado = (Long) q.uniqueResult();
		session.close();
		return resultado;

	}

	public String obtenerEsInterno(IdPersona id) {
		Session session = getSession();
		Query q = session.createQuery("" + "select i.interno " + " from Investigador i"
				+ " where i.id.tipoDocumento=:tipoD and i.id.documento=:documento" + "");
		q.setString("tipoD", id.getTipoDocumento());
		q.setString("documento", id.getDocumento());
		String resultado = (String) q.uniqueResult();
		session.close();
		return resultado;

	}

	public void insertarInvestigador(Investigador i) {
		Session s = getSession();
		Statement st = null;
		try {
			st = s.connection().createStatement();
			String sa = "insert into her_investigador (inv_id,tdo_id,cat_id,inv_interno,inv_evaluador,INV_FUNCIONARIO"
					+ (i.getAreaOcde() == null ? "" : ",INV_AREA_OCDE")
					+ (i.getSubareaOcde() == null ? "" : ",INV_SUB_AREA_OCDE") + ") " + "values(" + "'"
					+ i.getId().getDocumento() + "','" + i.getId().getTipoDocumento() + "' ,'"
					+ i.getCategoriaInvestigador().getId() + "' " + " , '" + i.getInterno() + "' ,'" + i.getEvaluador()
					+ "'" + (i.getEsFuncionario() == null ? ", 'N'" : ",'" + i.getEsFuncionario() + "'")
					+ (i.getAreaOcde() == null ? "" : " , '" + i.getAreaOcde() + "'")
					+ (i.getSubareaOcde() == null ? "" : " , '" + i.getSubareaOcde() + "'") + ")";
			st.execute(sa);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}

	}

	public void insertarNuevoEvaluadorExterno(PosibleEvaluador posv, String consecutivo) {

		Session s = getSession();
		Statement st = null;
		String idClasificacionConocimiento = "";
		if (posv.getClasificacionConocimiento() != null)
			idClasificacionConocimiento = posv.getClasificacionConocimiento().getId();
		try {
			st = s.connection().createStatement();
			String query = "insert into her_posible_evaluador(pry_id,pev_id,pev_nombre,"
					+ "pev_email,pev_telefono,pev_institucion,pev_experticia,pev_apellido1,"
					+ "pev_apellido2,per_id,tdo_id,clc_id) values (" + posv.getProyecto().getId() + "," + consecutivo
					+ ",'" + posv.getNombre() + "','" + posv.getEmail() + "','" + posv.getTelefono() + "'," + "'"
					+ posv.getInstitucion() + "','" + posv.getClasificacionConocimiento() + "','" + posv.getApellido1()
					+ "','" + posv.getApellido2() + "'," + "'" + posv.getDocumento() + "','" + posv.getTipoDocumento()
					+ "','" + idClasificacionConocimiento + "')";
			
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	public void actualizarEvaluadorExterno(PosibleEvaluador posv) {

		Session s = getSession();
		Statement st = null;
		String idClasificacionConocimiento = "";
		if (posv.getClasificacionConocimiento() != null)
			idClasificacionConocimiento = posv.getClasificacionConocimiento().getId();
		try {
			st = s.connection().createStatement();
			String sentencia = "UPDATE her_posible_evaluador " + "SET pev_nombre = '" + posv.getNombre() + "', "
					+ "pev_email = '" + posv.getEmail() + "', " + "pev_telefono = '" + posv.getTelefono() + "', "
					+ "pev_institucion = '" + posv.getInstitucion() + "', " + "pev_apellido1 = '" + posv.getApellido1()
					+ "', " + "pev_apellido2 = '" + posv.getApellido2() + "', " + "pev_experticia = '"
					+ posv.getExperticia() + "', " + "per_id = '" + posv.getDocumento() + "', " + "tdo_id = '"
					+ posv.getTipoDocumento() + "', " + "clc_id = '" + idClasificacionConocimiento + "' "
					+ "WHERE pev_id = " + posv.getId().longValue();
			st.execute(sentencia);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	public void insertarNuevoInvestigador(Investigador inv) {

		Session s = getSession();
		Statement st = null;
		try {
			st = s.connection().createStatement();
			String query = "insert into her_investigador(inv_id,tdo_id,inv_interno,inv_evaluador,inv_funcionario) values " + "('"
					+ inv.getId().getDocumento() + "','" 
					+ inv.getId().getTipoDocumento() + "','" 
					+ inv.getInterno() + "','" 
					+ inv.getEvaluador() + "','" 
					+ inv.getEsFuncionario()
					+ "')";
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	public void insertarNuevoEvaluador(Evaluador evaluador) {

		Session s = getSession();
		Statement st = null;
		try {
			st = s.connection().createStatement();

			String query = "insert into her_evaluador(inv_id,tdo_id "
					+ (evaluador.getAreaExperticia() == null ? "" : ",eva_area_experticia")
					+ (evaluador.getTesis() == null ? "" : ",eva_tesis")
					+ (evaluador.getFormacion() == null ? "" : ",eva_formacion")
					+ (evaluador.getAreaCiencia() == null ? "" : ", eva_area_ciencia")
					+ (evaluador.getSubAreaCiencia() == null ? "" : ", eva_subarea_ciencia")
					+ (evaluador.getCvlac() == null ? "" : ", eva_cvlac")
					+ (evaluador.getMinciencias() == null ? "" : ", eva_minciencias")
					+ (evaluador.getInstitucionLabora() == null ? "" : ", ffi_id_labora")
					+ (evaluador.getInstitucionEstudio() == null ? "" : ", ffi_id_estudio")
					+ (evaluador.getTipo() == null ? "" : ", eva_tipo)")
					+" values " + "('"
					+ evaluador.getId().getDocumento() + "','" 
					+ evaluador.getId().getTipoDocumento() + "'"
					+(evaluador.getAreaExperticia() == null ? "" : ",'"+evaluador.getAreaExperticia()+"'")
					+(evaluador.getTesis() == null ? "" : ",'"+evaluador.getTesis()+"'")
					+(evaluador.getFormacion() == null ? "" : ",'"+evaluador.getFormacion()+"'")
					+(evaluador.getAreaCiencia() == null ? "" : ",'"+evaluador.getAreaCiencia()+"'")
					+(evaluador.getSubAreaCiencia() == null ? "" : ",'"+evaluador.getSubAreaCiencia()+"'")
					+(evaluador.getCvlac() == null ? "" : ",'"+evaluador.getCvlac()+"'")
					+(evaluador.getMinciencias() == null ? "" : ",'"+evaluador.getMinciencias()+"'")
					+(evaluador.getInstitucionLabora().getId() == null ? "" : ",'"+evaluador.getInstitucionLabora().getId()+"'")
					+(evaluador.getInstitucionEstudio().getId() == null ? "" : ",'"+evaluador.getInstitucionEstudio().getId()+"'")
					+(evaluador.getTipo() == null ? "" : ",'"+evaluador.getTipo()+"'")+ ")";
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<LineaInvestigacion> obtenerLineasInvestigacionEvaluadorExterno(IdPersona id) {

		List lineas;
		List result = new ArrayList();
		Session session = getSession();
		try {

			String query = "select " + "EL.INV_ID as idInvestigador, " + "EL.TDO_ID as tipoDoc, "
					+ "L.LIN_ID as LINEA, " + "L.LIN_NOMBRE AS NOMBRE_LINEA " + "FROM "
					+ "HER_EVALUADOR_LINEA_INV EL, HER_LINEA_INVESTIGACION L  " + "WHERE EL.INV_ID = '"
					+ id.getDocumento() + "' and el.tdo_id = '" + id.getTipoDocumento() + "' and el.lin_id = l.lin_id "
					+ "order by l.LIN_ID";

			SQLQuery sqlQuery1 = session.createSQLQuery(query);

			lineas = sqlQuery1.addScalar("idInvestigador", Hibernate.STRING).addScalar("tipoDoc", Hibernate.STRING)
					.addScalar("LINEA", Hibernate.LONG).addScalar("NOMBRE_LINEA", Hibernate.STRING).list();

			session.close();

			Iterator it = lineas.iterator();

			while (it.hasNext()) {
				Object[] name = (Object[]) it.next();
				LineaInvestigacion li = new LineaInvestigacion();

				li.setId((Long) name[2]);
				li.setNombre((String) name[3]);

				result.add(li);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return result;

	}

	public void insertarNuevaPersona(Persona person) {
		Session s = getSession();
		Statement st = null;
		String fecha;
		try {
			try {
				Date fechaNacimiento = person.getFechaNacimiento();
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
				fecha = sdf.format(fechaNacimiento);
			} catch (Exception e) {
				fecha = null;
			}
			st = s.connection().createStatement();
			String query = "insert into her_persona(per_id,tdo_id,per_nombre1"
					+ (person.getNombre2() == null ? "" : ",per_nombre2") + ",per_apellido1"
					+ (person.getApellido2() == null ? "" : ",per_apellido2")
					+ (person.getGenero() == null ? "" : ",per_genero")
					+ (person.getEstadoCivil() == null ? "" : ", eci_id")
					+ (person.getFechaNacimiento() == null ? "" : ", PER_FNACIMIENTO")
					+ (person.getPaisOrigen() == null ? "" : ", PER_SIGLA_PAIS")
					+ (person.getCiudadNacimiento() == null ? "" : ", CIU_NACIMIENTO_ID")
					+ (person.getCiudadExpedicion() == null ? "" : ", CIU_EXPEDICION_ID")
					+ (person.getCiudadDomicilio() == null ? "" : ", CIU_DOMICILIO_ID")
					+ (person.getEmail() == null ? "" : ", PER_EMAIL")
					+ (person.getDireccion() == null ? "" : ", PER_DIRECCION")
					+ (person.getProfesion() == null ? "" : ", PER_PROFESION")
					+ (person.getTelefono() == null ? "" : ", PER_TELEFONO") + ") values " + "('"
					+ person.getId().getDocumento() + "','" + person.getId().getTipoDocumento() + "','"
					+ person.getNombre1() + "'"
					+ (person.getNombre2() == null ? "" : " , '" + person.getNombre2() + "'") + ", '"
					+ person.getApellido1() + "'"
					+ (person.getApellido2() == null ? "" : " , '" + person.getApellido2() + "'")
					+ (person.getGenero() == null ? "" : " , '" + person.getGenero() + "'")
					+ (person.getEstadoCivil() == null ? "" : " , '" + person.getEstadoCivil().getId() + "'")
					+ (person.getFechaNacimiento() == null ? "" : " , to_date('" + fecha + "', 'dd/mm/yyyy')")
					+ (person.getPaisOrigen() == null ? "" : " , '" + person.getPaisOrigen() + "'")
					+ (person.getCiudadNacimiento() == null ? "" : " , '" + person.getCiudadNacimiento().getId() + "'")
					+ (person.getCiudadExpedicion() == null ? "" : " , '" + person.getCiudadExpedicion().getId() + "'")
					+ (person.getCiudadDomicilio() == null ? "" : " , '" + person.getCiudadDomicilio().getId() + "'")
					+ (person.getEmail() == null ? "" : " , '" + person.getEmail() + "'")
					+ (person.getDireccion() == null ? "" : " , '" + person.getDireccion() + "'")
					+ (person.getProfesion() == null ? "" : " , '" + person.getProfesion() + "'")
					+ (person.getTelefono() == null ? "" : " , '" + person.getTelefono() + "'") + ")";
			
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	public boolean insertarNuevaPersonaDatosCompletos(Persona person) {
		System.out.println("insertarNuevaPersonaDatosCompletos:");
		Date fechaNacimiento = person.getFechaNacimiento();
		if (fechaNacimiento == null) {
			return (insertarNuevaPersonaDatosBasicos(person));
		} else {
			boolean exito = true;
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd");
			String fecha = sdf.format(fechaNacimiento);
			Session s = getSession();
			Statement st = null;

			try {
				st = s.connection().createStatement();
				String query = "insert into her_persona(per_id,tdo_id,per_apellido1,per_nombre1,per_genero,per_email,per_apellido2,per_nombre2,per_direccion,per_fnacimiento,per_telefono) values "
						+ "('" + person.getId().getDocumento() + "','" + person.getId().getTipoDocumento() + "','"
						+ person.getApellido1() + "','" + person.getNombre1() + "','" + person.getGenero() + "','"
						+ person.getEmail() + "','" + person.getApellido2() + "','" + person.getNombre2() + "','"
						+ person.getDireccion() + "',to_date('" + fecha + "', 'yyyy/mm/dd'),'" + person.getTelefono()
						+ "')";
				
				st.execute(query);
			} catch (Exception ex) {
				ex.printStackTrace();
				exito = false;
			} finally {
				if (s != null)
					s.close();
			}
			return exito;
		}
	}

	public boolean actualizarPersonaFuncytcaCompleto(Persona posv) {
		boolean exito = true;
		Session s = getSession();
		Statement st = null;
		String fecha = "";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd");
		if (posv.getEdad() == null) {
			fecha = "1900/01/01";
			posv.setEdad("0");

		} else {
			fecha = sdf.format(posv.getFechaNacimiento());
		}

		try {
			st = s.connection().createStatement();
			String sentencia = "UPDATE her_persona " + "SET PER_CELULAR = '" + posv.getCelular() + "', "
					+ "PER_FNACIMIENTO = to_date('" + fecha + "', 'yyyy/mm/dd'), " + "PER_EDAD = '" + posv.getEdad()
					+ "', " + "PER_FACULTAD_PREGRADO = '" + posv.getFacultadPregrado() + "', " + "PER_PROFESION = '"
					+ posv.getProfesion() + "', " + "PER_RESUMEN_HDV = '" + posv.getResumenHojaDeVida()
					+ "' WHERE PER_ID = '" + posv.getId().getDocumento() + "' and TDO_ID = '"
					+ posv.getId().getTipoDocumento() + "'";
			st.execute(sentencia);
		} catch (Exception ex) {
			ex.printStackTrace();
			exito = false;
		} finally {
			if (s != null)
				s.close();
		}

		return exito;
	}

	public boolean actualizarPersonaFuncytca(Persona posv) {
		boolean exito = true;
		Session s = getSession();
		Statement st = null;
		String idClasificacionConocimiento = "";

		try {
			st = s.connection().createStatement();
			String sentencia = "UPDATE her_persona " + "SET PER_NOMBRE1 = '" + posv.getNombre1() + "', "
					+ "PER_NOMBRE2 = '" + posv.getNombre2() + "', " + "PER_APELLIDO1 = '" + posv.getApellido1() + "', "
					+ "PER_APELLIDO2 = '" + posv.getApellido2() + "', " + "PER_EMAIL = '" + posv.getEmail() + "', "
					+ "PER_TELEFONO = '" + posv.getTelefono() + "', " + "PER_DIRECCION = '" + posv.getDireccion()
					+ "', " + "PER_TELEFONO_HV = '" + posv.getTelefonoHojaVida() + "' WHERE PER_ID = '"
					+ posv.getId().getDocumento() + "' and TDO_ID = '" + posv.getId().getTipoDocumento() + "'";
			st.execute(sentencia);
		} catch (Exception ex) {
			ex.printStackTrace();
			exito = false;
		} finally {
			if (s != null)
				s.close();
		}

		return exito;
	}

	public boolean actualizarPersonaDatosBasicos(Persona posv) {
		boolean exito = true;
		Session s = getSession();
		Statement st = null;
		String idClasificacionConocimiento = "";
		try {
			st = s.connection().createStatement();
			String sentencia = "UPDATE her_persona " + "SET PER_NOMBRE1 = '" + posv.getNombre1() + "', "
					+ "PER_NOMBRE2 = '" + posv.getNombre2() + "', " + "PER_APELLIDO1 = '" + posv.getApellido1() + "', "
					+ "PER_APELLIDO2 = '" + posv.getApellido2() + "', " + "PER_EMAIL = '" + posv.getEmail() + "', "
					+ "PER_GENERO = '" + posv.getGenero() + "', " + "PER_TELEFONO = '" + posv.getTelefono() + "', "
					+ "PER_DIRECCION = '" + posv.getDireccion() + "', " + " CIU_EXPEDICION_ID = '"
					+ posv.getCiudadExpedicion().getId() + "', " + " CIU_DOMICILIO_ID = '"
					+ posv.getCiudadDomicilio().getId() + "' WHERE PER_ID = '" + posv.getId().getDocumento()
					+ "' and TDO_ID = '" + posv.getId().getTipoDocumento() + "'";
			st.execute(sentencia);
		} catch (Exception ex) {
			ex.printStackTrace();
			exito = false;
		} finally {
			if (s != null)
				s.close();
		}
		return exito;
	}

	public boolean insertarNuevaPersonaDatosBasicosFuncytca(Persona person) {
		boolean exito = true;
		Session s = getSession();
		Statement st = null;

		try {
			st = s.connection().createStatement();
			String query = "insert into her_persona(per_id,tdo_id,per_apellido1,per_nombre1,per_genero,per_email,per_apellido2,per_nombre2,per_direccion,per_telefono, per_telefono_hv) values "
					+ "('" + person.getId().getDocumento() + "','" + person.getId().getTipoDocumento() + "','"
					+ person.getApellido1() + "','" + person.getNombre1() + "','" + person.getGenero() + "','"
					+ person.getEmail() + "','" + person.getApellido2() + "','" + person.getNombre2() + "','"
					+ person.getDireccion() + "','" + person.getTelefono() + "','" + person.getTelefonoHojaVida()
					+ "')";
			
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
			exito = false;
		} finally {
			if (s != null)
				s.close();
		}

		return exito;
	}

	public boolean insertarNuevaPersonaDatosBasicos(Persona person) {

		System.out.println("insertarNuevaPersonaDatosBasicos:");
		boolean exito = true;
		Session s = getSession();
		Statement st = null;

		try {
			st = s.connection().createStatement();
			String query = "insert into her_persona(per_id,tdo_id,per_apellido1,per_nombre1,per_genero,per_email,per_apellido2,per_nombre2,per_direccion,per_telefono) values "
					+ "('" + person.getId().getDocumento() + "','" + person.getId().getTipoDocumento() + "','"
					+ person.getApellido1() + "','" + person.getNombre1() + "','" + person.getGenero() + "','"
					+ person.getEmail() + "','" + person.getApellido2() + "','" + person.getNombre2() + "','"
					+ person.getDireccion() + "','" + person.getTelefono() + "')";
			
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
			exito = false;
		} finally {
			if (s != null)
				s.close();
		}

		return exito;
	}

	public boolean insertarNuevaPersonaDatosBasicosPI(Persona person) {

		// Copia de metodo insertarNuevaPersonaDatosBasicos,
		// no se tienen en cuenta género ni teléfono por que no se piden en el
		// formulario
		System.out.println("insertarNuevaPersonaDatosBasicosPI:");
		boolean exito = true;
		Session s = getSession();
		Statement st = null;

		try {
			st = s.connection().createStatement();
			String query = "insert into her_persona(per_id,tdo_id,per_apellido1,per_nombre1,per_email,per_apellido2,per_nombre2,per_direccion) values "
					+ "('" + person.getId().getDocumento() + "','" + person.getId().getTipoDocumento() + "','"
					+ person.getApellido1() + "','" + person.getNombre1() + "','" + person.getEmail() + "','"
					+ person.getApellido2() + "','" + person.getNombre2() + "','" + person.getDireccion() + "')";
			
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
			exito = false;
		} finally {
			if (s != null)
				s.close();
		}

		return exito;
	}

	public void insertarNuevoInvestigadorExterno(InvestigadorExterno externo) {

		Session s = getSession();
		Statement st = null;
		try {
			st = s.connection().createStatement();
			String query = "insert into her_investigador_externo(tdo_id,inv_id,ffi_id"
					+ (externo.getTipoFormacion() == null ? "" : ",tfo_id") + ") values " + "('"
					+ externo.getId().getTipoDocumento() + "','" + externo.getId().getDocumento() + "','"
					+ externo.getInstitucionInvestigador().getId() + "'"
					+ (externo.getTipoFormacion() == null ? "" : " , '" + externo.getTipoFormacion().getId() + "'")
					+ ")";
			
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	public String buscarUltimoConsecutivo() {

		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		String consec = new String();
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "select max(pev_id)+1 " + "from her_posible_evaluador";
			
			rs = st.executeQuery(query);
			while (rs.next()) {
				consec = rs.getString("max(pev_id)+1");
				return consec;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return consec;
	}

	public void insertaEvaluadorCorreo(Proyecto p, TipoDocumento td, String contactado[]) {

		Session s = getSession();
		Statement st = null;
		try {
			s = getSession();
			st = s.connection().createStatement();
			String ins = " insert into her_evaluador_correo (pry_id,inv_id,tdo_id,contactado,respuesta,nombre)"
					+ " values (" + p.getId() + ",'" + contactado[2] + "','" + td.getId() + "' " + ",'" + contactado[0]
					+ "','" + contactado[1] + "', '" + contactado[3] + "'" + ")";
			System.out.println(ins);
			st.execute(ins);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
	}

	public void insertaRespuestaEvaluadorCorreo(EvaluadorCorreo eval) {

		Session s = getSession();
		Statement st = null;
		try {
			s = getSession();
			st = s.connection().createStatement();
			String ins = "update her_evaluador_correo set respuesta = '" + eval.getRespuesta() + "'"
					+ " where pry_id = " + eval.getProyecto() + " and inv_id = '" + eval.getInvestigador() + "'";
			st.execute(ins);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
	}

	public void insertaInterno(InvestigadorInterno ii) {
		Session s = null;
		Statement st = null;
		try {
			s = getSession();
			st = s.connection().createStatement();
			String sa = " insert into her_investigador_interno (inv_id,tdo_id,dpn_id"
					+ (ii.getTipoDedicacion() == null ? "" : ",tde_id")
					+ (ii.getTipoFormacion() == null ? "" : ",tfo_id")
					+ (ii.getTelExtension() == null ? "" : ",ini_textension") + ")" + " values ('"
					+ ii.getId().getDocumento() + "'" + ",'" + ii.getId().getTipoDocumento() + "' , '"
					+ ii.getDependencia().getId() + "' "
					+ (ii.getTipoDedicacion() == null ? "" : " , '" + ii.getTipoDedicacion().getId() + "'")
					+ (ii.getTipoFormacion() == null ? "" : ",'" + ii.getTipoFormacion().getId() + "'")
					+ (ii.getTelExtension() == null ? "" : ", " + ii.getTelExtension() + "") + ")";
			st.execute(sa);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}

	}

	public void insertarExterno(InvestigadorExterno ie) {

		Session s = null;
		Statement st = null;
		try {
			s = getSession();
			st = s.connection().createStatement();
			String sa = " insert into her_investigador_externo (inv_id,tdo_id,ins_id) values ( '"
					+ ie.getId().getDocumento() + "','" + ie.getId().getTipoDocumento() + "'," + "'"
					+ ie.getInstitucion().getId() + "')";
			st.execute(sa);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
	}

	public void insertarExternoContraseña(InvestigadorExterno ie) {

		Session s = null;
		Statement st = null;
		try {
			s = getSession();
			st = s.connection().createStatement();
			String sa = " insert into her_investigador_externo (inv_id,tdo_id,ins_id,ine_clave) values ( '"
					+ ie.getId().getDocumento() + "','" + ie.getId().getTipoDocumento() + "'," + "'"
					+ ie.getInstitucion().getId() + "'," + "'" + ie.getContrasena() + "')";
			st.execute(sa);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
	}

	public List obtenerInvestigadoresPrincipales() {
		List investigadores;
		Session session = getSession();
		Criteria criteria = session.createCriteria(Persona.class);
		criteria.add(Expression.sqlRestriction(
				"EXISTS (SELECT 1 FROM her_investigador_proyecto ip WHERE per_id = ip.inv_id AND ip.tdo_id = tdo_id AND inp_tipo = 'P')"));
		criteria.addOrder(Order.asc("apellido1"));
		criteria.addOrder(Order.asc("apellido2"));
		criteria.addOrder(Order.asc("nombre1"));
		investigadores = criteria.list();
		session.close();
		return investigadores;
	}

	public List obtenerEvaluadores() {
		List evaluadores;
		Session session = getSession();
		Criteria criteria = session.createCriteria(Persona.class);
		criteria.add(Expression.sqlRestriction(
				"EXISTS (SELECT 1 FROM her_proyecto_evaluador pe WHERE per_id = pe.inv_id AND pe.tdo_id = tdo_id)"));
		criteria.addOrder(Order.asc("apellido1"));
		criteria.addOrder(Order.asc("apellido2"));
		criteria.addOrder(Order.asc("nombre1"));
		evaluadores = criteria.list();
		session.close();
		return evaluadores;
	}

	public List obtenerInvestigadoresProyectos() {
		List investigadores;
		Session session = getSession();
		Criteria criteria = session.createCriteria(Persona.class);
		criteria.add(Expression.sqlRestriction(
				"EXISTS (SELECT 1 FROM her_investigador_proyecto ip WHERE per_id = ip.inv_id AND ip.tdo_id = tdo_id)"));
		criteria.addOrder(Order.asc("apellido1"));
		criteria.addOrder(Order.asc("apellido2"));
		criteria.addOrder(Order.asc("nombre1"));
		investigadores = criteria.list();
		session.close();
		return investigadores;
	}

	/**
	 * Obtiene un investigador con los proyectos en los cuales es el Investigador
	 * Principal
	 */

	public List obtenerInvestigadorPrincipalProyectos(IdPersona id) throws DataAccessException {

		Session session = getSession();

		TipoInvestigador tipoInvestigador = new TipoInvestigador();
		tipoInvestigador.setId("P");
		Investigador investigador = new Investigador();
		investigador.setId(id);

		List result = new ArrayList();
		result.addAll(session.createCriteria(InvestigadorProyecto.class).add(Restrictions.eq("tipo", tipoInvestigador))
				.add(Restrictions.eq("investigador", investigador)).list());

		session.close();
		return result;
	}

	public List obtenerInvestigadoresGrupos() {
		List investigadores;
		Session session = getSession();
		Criteria criteria = session.createCriteria(Persona.class);
		criteria.add(Expression.sqlRestriction(
				"EXISTS (SELECT 1 FROM her_investigador_grupo ig WHERE per_id = ig.inv_id AND ig.tdo_id = tdo_id)"));
		criteria.addOrder(Order.asc("apellido1"));
		criteria.addOrder(Order.asc("apellido2"));
		criteria.addOrder(Order.asc("nombre1"));
		investigadores = criteria.list();
		session.close();
		return investigadores;
	}

	public InvestigadorExterno obtenerInvestigadorExternoClasificacionConocimiento(IdPersona id)
			throws DataAccessException {
		Session session = getSession();

		InvestigadorExterno i = (InvestigadorExterno) session.createCriteria(InvestigadorExterno.class)
				.setFetchMode("clasificacionesConocimiento", FetchMode.JOIN)
				// .setFetchMode("dependencia", FetchMode.JOIN)
				.add(Restrictions.idEq(id)).uniqueResult();

		session.close();

		return i;
	}

	public boolean esInvestigadorInterno(IdPersona id) {

		Session s = null;
		Query q = null;
		InvestigadorInterno ii = null;
		try {
			s = getSession();
			q = s.createQuery("select ii from InvestigadorInterno ii where ii.id.tipoDocumento=:tipoDocumento"
					+ " and ii.id.documento=:documento  ");
			q.setString("tipoDocumento", id.getTipoDocumento());
			q.setString("documento", id.getDocumento());
			System.out.println("busca interno");
			ii = (InvestigadorInterno) q.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return ii != null;

	}

	public boolean esInvestigadorExterno(IdPersona id) {

		Session s = null;
		Query q = null;
		InvestigadorExterno ii = null;
		try {
			s = getSession();
			q = s.createQuery("select ii from InvestigadorExterno ii where ii.id.tipoDocumento=:tipoDocumento"
					+ " and ii.id.documento=:documento" + "");
			q.setString("tipoDocumento", id.getTipoDocumento());
			q.setString("documento", id.getDocumento());
			System.out.println("busca externo");
			ii = (InvestigadorExterno) q.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return ii != null;

	}

	public void agregarRolPersona(IdPersona idPersona, Rol rol) {
		Session session = getSession();
		String insertar = "INSERT INTO her_persona_rol(tdo_id,per_id,rol_id) " + "VALUES('"
				+ idPersona.getTipoDocumento() + "', '" + idPersona.getDocumento() + "', '" + rol.getId() + "')";

		try {
			Statement sentencia = session.connection().createStatement();
			sentencia.execute(insertar);
		} catch (SQLException e) {
		} catch (Exception ex) {
		}

		session.close();
	}

	public boolean validarInvestigadorModalidad(Long idModalidad, IdPersona idPersona) {
		Session session = getSession();
		Statement statement = null;
		ResultSet resultSet;
		boolean investigadorModalidad = true;
		try {
			session = getSession();
			statement = session.connection().createStatement();
			String query = "SELECT 1 " + "FROM her_investigador_proyecto inp, " + "her_proyecto pry "
					+ "WHERE pry.pry_id = inp.pry_id " + "AND pry.mod_id = " + idModalidad.longValue() + " "
					+ "AND inv_id = '" + idPersona.getDocumento() + "' " + "AND tdo_id = '"
					+ idPersona.getTipoDocumento() + "' and pry.epr_id in ('E','P')";

			resultSet = statement.executeQuery(query);
			investigadorModalidad = resultSet.next();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (session != null) {
				session.close();
			}
		}
		return investigadorModalidad;
	}

	public void agregarClasificacionConocimientoInvestigador(IdPersona idPersona, String idClasificacionConocimiento) {
		Session session = getSession();
		String insertar = "INSERT INTO her_investigador_cla_con(tdo_id,inv_id,clc_id) " + "VALUES('"
				+ idPersona.getTipoDocumento() + "', '" + idPersona.getDocumento() + "', '"
				+ idClasificacionConocimiento + "')";
		System.out.println(insertar);

		try {
			Statement sentencia = session.connection().createStatement();
			sentencia.execute(insertar);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		session.close();
	}

	public List obtenerListaCoordinadoresAsesor(IdPersona idAsesor) {
		Session s = getSession();
		Statement st = null;
		ResultSet rs;
		List listaCoordinadores = new ArrayList();
		try {
			Persona per = null;
			IdPersona idPer = null;
			s = getSession();
			st = s.connection().createStatement();
			String query = "select per.per_id doc,per.tdo_id tipo,per.per_nombre1 nombre1,per.per_nombre2 nombre2,per.per_apellido1 apellido1,per.per_apellido2 apellido2, "
					+ "d.dpn_id iddpn , d.dpn_nombre dependencia "
					+ "from her_coordinador_asesor coo,her_persona per, her_dependencia d  " + "where coo.per_id = '"
					+ idAsesor.getDocumento() + "' and " + "coo.tdo_id = '" + idAsesor.getTipoDocumento() + "' AND "
					+ "coo.coor_id = per.per_id and " + "coor_tpo_id = per.tdo_id and " + "coo.dpn_id = d.dpn_id "
					+ "order by per.per_nombre1, per.per_apellido1, d.dpn_nombre";

			rs = st.executeQuery(query);
			while (rs.next()) {
				per = new Persona();
				idPer = new IdPersona();
				idPer.setDocumento(rs.getString("doc"));
				idPer.setTipoDocumento(rs.getString("tipo"));
				per.setId(idPer);
				per.setNombre1(rs.getString("nombre1"));
				per.setNombre2(rs.getString("nombre2"));
				per.setApellido1(rs.getString("apellido1"));
				per.setApellido2(rs.getString("apellido2"));
				per.setCoorIDDependencia(rs.getString("iddpn"));
				per.setCoorNombreDependencia(rs.getString("dependencia"));
				listaCoordinadores.add(per);
				per = null;
			}
			return listaCoordinadores;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
		return listaCoordinadores;
	}

	public List obtenerListaCoordinadoresAsesorEditorial(IdPersona idAsesor) {
		Session s = getSession();
		Statement st = null;
		ResultSet rs;
		List listaCoordinadores = new ArrayList();
		try {
			Persona per = null;
			IdPersona idPer = null;
			s = getSession();
			st = s.connection().createStatement();
			String query = "select per.per_id doc,per.tdo_id tipo,per.per_nombre1 nombre1,per.per_nombre2 nombre2,per.per_apellido1 apellido1,per.per_apellido2 apellido2, "
					+ "d.dpn_id iddpn , d.dpn_nombre dependencia "
					+ "from her_coordinador_asesor_ed coo,her_persona per, her_dependencia d  " + "where coo.per_id = '"
					+ idAsesor.getDocumento() + "' and " + "coo.tdo_id = '" + idAsesor.getTipoDocumento() + "' AND "
					+ "coo.coor_id = per.per_id and " + "coor_tpo_id = per.tdo_id and " + "coo.dpn_id = d.dpn_id "
					+ "order by per.per_nombre1, per.per_apellido1, d.dpn_nombre";

			rs = st.executeQuery(query);
			while (rs.next()) {
				per = new Persona();
				idPer = new IdPersona();
				idPer.setDocumento(rs.getString("doc"));
				idPer.setTipoDocumento(rs.getString("tipo"));
				per.setId(idPer);
				per.setNombre1(rs.getString("nombre1"));
				per.setNombre2(rs.getString("nombre2"));
				per.setApellido1(rs.getString("apellido1"));
				per.setApellido2(rs.getString("apellido2"));
				per.setCoorIDDependencia(rs.getString("iddpn"));
				per.setCoorNombreDependencia(rs.getString("dependencia"));
				listaCoordinadores.add(per);
				per = null;
			}
			return listaCoordinadores;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
		return listaCoordinadores;
	}

	public List obtenerListaDependenciasAsesor(IdPersona idAsesor) {
		Session s = getSession();
		Statement st = null;
		ResultSet rs;
		List listaCoordinadores = new ArrayList();
		try {
			Persona per = null;
			IdPersona idPer = null;
			s = getSession();
			st = s.connection().createStatement();
			String query = "select per.per_id doc,per.tdo_id tipo,per.per_nombre1 nombre1,per.per_nombre2 nombre2,per.per_apellido1 apellido1,per.per_apellido2 apellido2, "
					+ "d.dpn_id iddpn , d.dpn_nombre dependencia "
					+ "from her_coordinador_asesor coo,her_persona per, her_dependencia d  " + "where coo.per_id = '"
					+ idAsesor.getDocumento() + "' and " + "coo.tdo_id = '" + idAsesor.getTipoDocumento() + "' AND "
					+ "coo.per_id = per.per_id and " + "coo.tdo_id = per.tdo_id and " + "coo.coor_id = coo.per_id and "
					+ "coor_tpo_id = coo.tdo_id AND " + "coo.dpn_id = d.dpn_id "
					+ "order by per.per_nombre1, per.per_apellido1, d.dpn_nombre";

			rs = st.executeQuery(query);
			while (rs.next()) {
				per = new Persona();
				idPer = new IdPersona();
				idPer.setDocumento(rs.getString("doc"));
				idPer.setTipoDocumento(rs.getString("tipo"));
				per.setId(idPer);
				per.setNombre1(rs.getString("nombre1"));
				per.setNombre2(rs.getString("nombre2"));
				per.setApellido1(rs.getString("apellido1"));
				per.setApellido2(rs.getString("apellido2"));
				per.setCoorIDDependencia(rs.getString("iddpn"));
				per.setCoorNombreDependencia(rs.getString("dependencia"));
				listaCoordinadores.add(per);
				per = null;
			}
			return listaCoordinadores;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
		return listaCoordinadores;
	}

	public List obtenerListaDependenciasAsesorEditorial(IdPersona idAsesor) {
		Session s = getSession();
		Statement st = null;
		ResultSet rs;
		List listaCoordinadores = new ArrayList();
		try {
			Persona per = null;
			IdPersona idPer = null;
			s = getSession();
			st = s.connection().createStatement();
			String query = "select per.per_id doc,per.tdo_id tipo,per.per_nombre1 nombre1,per.per_nombre2 nombre2,per.per_apellido1 apellido1,per.per_apellido2 apellido2, "
					+ "d.dpn_id iddpn , d.dpn_nombre dependencia "
					+ "from her_coordinador_asesor_ed coo,her_persona per, her_dependencia d  " + "where coo.per_id = '"
					+ idAsesor.getDocumento() + "' and " + "coo.tdo_id = '" + idAsesor.getTipoDocumento() + "' AND "
					+ "coo.per_id = per.per_id and " + "coo.tdo_id = per.tdo_id and " + "coo.coor_id = coo.per_id and "
					+ "coor_tpo_id = coo.tdo_id AND " + "coo.dpn_id = d.dpn_id "
					+ "order by per.per_nombre1, per.per_apellido1, d.dpn_nombre";

			rs = st.executeQuery(query);
			while (rs.next()) {
				per = new Persona();
				idPer = new IdPersona();
				idPer.setDocumento(rs.getString("doc"));
				idPer.setTipoDocumento(rs.getString("tipo"));
				per.setId(idPer);
				per.setNombre1(rs.getString("nombre1"));
				per.setNombre2(rs.getString("nombre2"));
				per.setApellido1(rs.getString("apellido1"));
				per.setApellido2(rs.getString("apellido2"));
				per.setCoorIDDependencia(rs.getString("iddpn"));
				per.setCoorNombreDependencia(rs.getString("dependencia"));
				listaCoordinadores.add(per);
				per = null;
			}
			return listaCoordinadores;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
		return listaCoordinadores;
	}

	public List obtenerListaCoordinadoresNoAsignados() {
		Session s = getSession();
		Statement st = null;
		ResultSet rs;
		List listaCoordinadores = new ArrayList();
		try {
			Persona per = null;
			IdPersona idPer = null;
			s = getSession();
			st = s.connection().createStatement();
			String query = "SELECT per.per_id doc, " + "per.tdo_id tipo, " + "per.per_nombre1 nombre1, "
					+ "per.per_nombre2 nombre2, " + "per.per_apellido1 apellido1, " + "per.per_apellido2 apellido2 "
					+ "FROM her_persona per, " + "her_persona_rol rol " + "WHERE per.tdo_id = rol.tdo_id "
					+ "AND per.per_id = rol.per_id " + "AND rol.rol_id = 'C' " + "AND NOT EXISTS(SELECT 1 "
					+ "FROM her_coordinador_asesor ca " + "WHERE ca.coor_tpo_id = per.tdo_id "
					+ "AND ca.coor_id = per.per_id) ";
			rs = st.executeQuery(query);
			while (rs.next()) {
				per = new Persona();
				idPer = new IdPersona();
				idPer.setDocumento(rs.getString("doc"));
				idPer.setTipoDocumento(rs.getString("tipo"));
				per.setId(idPer);
				per.setNombre1(rs.getString("nombre1"));
				per.setNombre2(rs.getString("nombre2"));
				per.setApellido1(rs.getString("apellido1"));
				per.setApellido2(rs.getString("apellido2"));
				listaCoordinadores.add(per);
				per = null;
			}
			return listaCoordinadores;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
		return listaCoordinadores;
	}

	public List obtenerListaCoordinadoresNoAsignadosAsesor(IdPersona idAsesor) {
		Session s = getSession();
		Statement st = null;
		ResultSet rs;
		List listaCoordinadores = new ArrayList();
		try {
			Persona per = null;
			IdPersona idPer = null;
			s = getSession();
			st = s.connection().createStatement();
			String query = "SELECT per.per_id doc, " + "per.tdo_id tipo, " + "per.per_nombre1 nombre1, "
					+ "per.per_nombre2 nombre2, " + "per.per_apellido1 apellido1, " + "per.per_apellido2 apellido2 "
					+ "FROM her_persona per, " + "her_persona_rol rol " + "WHERE per.tdo_id = rol.tdo_id "
					+ "AND per.per_id = rol.per_id " + "AND rol.rol_id = 'C' " + "AND NOT EXISTS(SELECT 1 "
					+ "FROM her_coordinador_asesor ca " + "WHERE ca.coor_tpo_id = per.tdo_id "
					+ "AND ca.coor_id = per.per_id " + "AND ca.per_id <> '" + idAsesor.getDocumento() + "' "
					+ "AND ca.tdo_id <> '" + idAsesor.getTipoDocumento() + "' )" + "ORDER BY  per.per_nombre1 ";
			rs = st.executeQuery(query);
			while (rs.next()) {
				per = new Persona();
				idPer = new IdPersona();
				idPer.setDocumento(rs.getString("doc"));
				idPer.setTipoDocumento(rs.getString("tipo"));
				per.setId(idPer);
				per.setNombre1(rs.getString("nombre1"));
				per.setNombre2(rs.getString("nombre2"));
				per.setApellido1(rs.getString("apellido1"));
				per.setApellido2(rs.getString("apellido2"));
				listaCoordinadores.add(per);
				per = null;
			}
			return listaCoordinadores;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (s != null) {
				s.close();
			}
		}
		return listaCoordinadores;
	}

	public void insertarCoordinadorAsesor(IdPersona idAsesor, IdPersona idCoordinador, String idDependencia) {
		Session s = getSession();
		Statement st = null;
		String query;
		ResultSet rs;
		String cantidad = "";
		try {
			st = s.connection().createStatement();

			query = "select Count (1) cantidad  from her_coordinador_asesor " + "where per_id = '"
					+ idAsesor.getDocumento() + "' " + "and tdo_id = '" + idAsesor.getTipoDocumento() + "' "
					+ "and coor_id = '" + idCoordinador.getDocumento() + "' " + "and coor_tpo_id = '"
					+ idCoordinador.getTipoDocumento() + "' " + "and dpn_id = '" + idDependencia + "' ";
			rs = st.executeQuery(query);
			while (rs.next()) {
				cantidad = rs.getString("cantidad");
			}

			if (cantidad == null || cantidad.equals("") || cantidad.equals("0")) {

				String sa = "insert into her_coordinador_asesor (dpn_id, per_id,tdo_id,coor_id,coor_tpo_id) "
						+ "values('" + idDependencia + "', " + "'" + idAsesor.getDocumento() + "', " + "'"
						+ idAsesor.getTipoDocumento() + "', " + "'" + idCoordinador.getDocumento() + "', " + "'"
						+ idCoordinador.getTipoDocumento() + "' " + ")";
				st.execute(sa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	public void insertarCoordinadorAsesorEditorial(IdPersona idAsesor, IdPersona idCoordinador, String idDependencia) {
		Session s = getSession();
		Statement st = null;
		String query;
		ResultSet rs;
		String cantidad = "";
		try {
			st = s.connection().createStatement();

			query = "select Count (1) cantidad  from her_coordinador_asesor_ed " + "where per_id = '"
					+ idAsesor.getDocumento() + "' " + "and tdo_id = '" + idAsesor.getTipoDocumento() + "' "
					+ "and coor_id = '" + idCoordinador.getDocumento() + "' " + "and coor_tpo_id = '"
					+ idCoordinador.getTipoDocumento() + "' " + "and dpn_id = '" + idDependencia + "' ";
			rs = st.executeQuery(query);
			while (rs.next()) {
				cantidad = rs.getString("cantidad");
			}

			if (cantidad == null || cantidad.equals("") || cantidad.equals("0")) {

				String sa = "insert into her_coordinador_asesor_ed (dpn_id, per_id,tdo_id,coor_id,coor_tpo_id) "
						+ "values('" + idDependencia + "', " + "'" + idAsesor.getDocumento() + "', " + "'"
						+ idAsesor.getTipoDocumento() + "', " + "'" + idCoordinador.getDocumento() + "', " + "'"
						+ idCoordinador.getTipoDocumento() + "' " + ")";
				st.execute(sa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	public void insertarDependenciaAsesor(IdPersona idAsesor, String idDependencia) {
		Session s = getSession();
		Statement st = null;
		String query;
		ResultSet rs;
		String cantidad = "";
		try {
			st = s.connection().createStatement();

			query = "select Count (1) cantidad  from her_coordinador_asesor " + "where per_id = '"
					+ idAsesor.getDocumento() + "' " + "and tdo_id = '" + idAsesor.getTipoDocumento() + "' "
					+ "and coor_id = per_id " + "and coor_tpo_id = tdo_id " + "and dpn_id = '" + idDependencia + "' ";
			rs = st.executeQuery(query);
			while (rs.next()) {
				cantidad = rs.getString("cantidad");
			}

			if (cantidad == null || cantidad.equals("") || cantidad.equals("0")) {

				String sa = "insert into her_coordinador_asesor (dpn_id, per_id,tdo_id,coor_id,coor_tpo_id) "
						+ "values('" + idDependencia + "', " + "'" + idAsesor.getDocumento() + "', " + "'"
						+ idAsesor.getTipoDocumento() + "', " + "'" + idAsesor.getDocumento() + "', " + "'"
						+ idAsesor.getTipoDocumento() + "' " + ")";
				st.execute(sa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	public void eliminarCoordinadorAsesor(IdPersona idAsesor, IdPersona idCoordinador, String idDependencia) {
		Session s = getSession();
		Statement st = null;
		try {
			st = s.connection().createStatement();
			String sa = "DELETE her_coordinador_asesor " + "WHERE per_id = '" + idAsesor.getDocumento() + "' "
					+ "AND tdo_id = '" + idAsesor.getTipoDocumento() + "' " + "AND coor_id = '"
					+ idCoordinador.getDocumento() + "' " + "AND coor_tpo_id = '" + idCoordinador.getTipoDocumento()
					+ "' " + "AND dpn_id  = '" + idDependencia + "'";
			st.execute(sa);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	public void eliminarDependenciaAsesor(IdPersona idAsesor, String idDependencia) {
		Session s = getSession();
		Statement st = null;
		try {
			st = s.connection().createStatement();
			String sa = "DELETE her_coordinador_asesor " + "WHERE per_id = '" + idAsesor.getDocumento() + "' "
					+ "AND tdo_id = '" + idAsesor.getTipoDocumento() + "' " + "AND coor_id = per_id "
					+ "AND coor_tpo_id = tdo_id " + "AND dpn_id  = '" + idDependencia + "'";
			st.execute(sa);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	public List obtenerPuntajesInvestigador(IdPersona idInvestigador) {
		List puntajes = null;
		Session session;
		Statement sentencia = null;

		String query = "SELECT descripcion descripcion, " + "fecha_obtencion fecha_obtencion, "
				+ "puntaje_real puntaje_real " + "FROM her_investigador_puntaje " + "WHERE inv_id = '"
				+ idInvestigador.getDocumento() + "' " + "AND tdo_id = '" + idInvestigador.getTipoDocumento() + "' "
				+ "ORDER BY 2";

		puntajes = new ArrayList();
		session = getSession();

		try {
			sentencia = session.connection().createStatement();
			sentencia.execute(query);
			ResultSet rs = sentencia.getResultSet();
			while (rs.next()) {
				InvestigadorPuntaje puntaje = new InvestigadorPuntaje();
				puntaje.setDescripcion(rs.getString(1));
				puntaje.setFechaObtencion(rs.getDate(2));
				puntaje.setPuntajeReal(rs.getFloat(3));
				puntajes.add(puntaje);
			}
		} catch (Exception e) {
			puntajes = null;
			e.printStackTrace();
		} finally {
			session.close();
		}

		return puntajes;
	}

	public List<Persona> obtenerInvestigadoresBuscador(String sql) throws DataAccessException {
		List<Object> personas;
		List<Persona> resultado = new ArrayList<Persona>();

		Session session = getSession();

		if (sql.length() > 0) {
			sql += " and ";
		} else
			sql = " where ";
		String query = "SELECT distinct HER_PERSONA.PER_ID as idPersona, HER_PERSONA.TDO_ID as tipoDocumento,"
				+ " HER_PERSONA.PER_NOMBRE1 as nombre1, HER_PERSONA.PER_NOMBRE2 as nombre2, HER_PERSONA.PER_APELLIDO1 as apellido1,"
				+ " HER_PERSONA.PER_APELLIDO2 as apellido2, HER_DEPENDENCIA.DPN_NOMBRE as depNombre, HER_SEDE.SED_NOMBRE as sedNombre, HER_PERSONA.PER_EMAIL as Email"
				+ " FROM HER_INVESTIGADOR_INTERNO, HER_INVESTIGADOR LEFT JOIN HER_INVESTIGADOR_GRUPO ON HER_INVESTIGADOR.INV_ID = HER_INVESTIGADOR_GRUPO.INV_ID and HER_INVESTIGADOR.TDO_ID "
				+ "= HER_INVESTIGADOR_GRUPO.TDO_ID left join HER_GRUPO_LINEA on HER_GRUPO_LINEA.GRU_ID = HER_INVESTIGADOR_GRUPO.GRU_ID left join HER_LINEA_INVESTIGACION on "
				+ "HER_LINEA_INVESTIGACION.LIN_ID = HER_GRUPO_LINEA.LIN_ID LEFT JOIN HER_GRUPO ON HER_GRUPO.GRU_ID = HER_INVESTIGADOR_GRUPO.GRU_ID, HER_PERSONA, HER_DEPENDENCIA, HER_SEDE "
				+ sql + " HER_PERSONA.PER_ID = HER_INVESTIGADOR_INTERNO.INV_ID AND"
				+ " HER_PERSONA.TDO_ID = HER_INVESTIGADOR_INTERNO.TDO_ID AND"
				+ " HER_DEPENDENCIA.DPN_ID = HER_INVESTIGADOR_INTERNO.DPN_ID AND"
				+ " HER_DEPENDENCIA.SED_ID = HER_SEDE.SED_ID and HER_INVESTIGADOR_INTERNO.INV_ID = HER_INVESTIGADOR.INV_ID and HER_INVESTIGADOR_INTERNO.TDO_ID = HER_INVESTIGADOR.TDO_ID and HER_INVESTIGADOR.INV_INTERNO = 'S'"
				+ " ORDER BY nombre1, nombre2, apellido1, apellido2";

		SQLQuery sqlQuery = session.createSQLQuery(query);

		personas = sqlQuery.addScalar("idPersona", Hibernate.STRING).addScalar("tipoDocumento", Hibernate.STRING)
				.addScalar("nombre1", Hibernate.STRING).addScalar("nombre2", Hibernate.STRING)
				.addScalar("apellido1", Hibernate.STRING).addScalar("apellido2", Hibernate.STRING)
				.addScalar("depNombre", Hibernate.STRING).addScalar("sedNombre", Hibernate.STRING)
				.addScalar("Email", Hibernate.STRING).list();

		session.close();

		Iterator<Object> it = personas.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();

			Persona persona = new Persona();
			IdPersona idPer = new IdPersona();
			idPer.setDocumento((String) name[0]);
			idPer.setTipoDocumento((String) name[1]);
			persona.setId(idPer);
			persona.setNombre1((String) name[2]);
			persona.setNombre2((String) name[3]);
			persona.setApellido1((String) name[4]);
			persona.setApellido2((String) name[5]);
			persona.setDireccion((String) name[6]);
			persona.setEdad((String) name[7]);
			persona.setEmail((String) name[8]);

			resultado.add(persona);
		}

		return resultado;
	}

	public Investigador obtenerInvestigadorSinProyectos(IdPersona id) throws DataAccessException {

		Session session = getSession();
		//
		try {
			Investigador i = (Investigador) session.createCriteria(Investigador.class).add(Restrictions.idEq(id))
					.uniqueResult();
			Hibernate.initialize(i.getProyectosInvestigador());
			session.close();
			return i;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Investigador obtenerProyectosInvestigador(IdPersona id) throws DataAccessException {
		return obtenerProyectosInvestigador(id, false, -1);
	}

	public Investigador obtenerProyectosInvestigador(IdPersona id, boolean incluirCreadosPor, int conv_padre_id)
			throws DataAccessException {

		List proyectosInv;
		Set result = new HashSet();

		Session session = getSession();

		String query = "select p.PRY_ID as idProyecto, p.PRY_NOMBRE as nombreProyecto, p.PRY_FASE as faseProyecto, mod.MOD_ID as idModalidad, tm.TMO_ID as tipoModalidad, tm.TMO_NOMBRE as nombreModalidad,"
				+ " p.PRY_PERMITIR_MODIFICACION as permitirModificacion, p.PRY_CODIGO_DIB as codigoDib, ep.EPR_ID as idEstado, ep.EPR_NOMBRE as nombreEstado, p.PRY_PADRE as proyectoPadre,"
				+ " ec.ECO_ID as estadoConvocatoria, ec.ECO_NOMBRE as nombreEstadoConv, c.CON_TITULO as tituloConvoca, cp.CNP_ID as idConvocaPadre, cp.CNP_TITULO as tituloConvocaPadre,"
				+ " cr.TRE_ID as idRestriccion, cr.TRE_NOMBRE as nombreRestriccion, c.CON_HAB_INFORMES_ESTUDIAN as habilitarInfoEst, c.CON_HAB_INFORMES_TUTOR as habilitarInfoTutor, c.CON_VARIOS_INFORMES_AVANCE as variosInformesAvance,"
				+ " p.PRY_ES_JORN_DOCENTE as esJornadaDocente, p.PRY_CODIGO_QUIPU as codigoQuipu, ip.INP_ID as idInvProy, ip.INP_TIPO as rol, p.PRY_TIPO_ACTIVIDAD as tipoActividad, d.dpn_id idDependencia, s.sed_nombre nombreSede, p.PRY_FECHA_TENTATIVA_INICIO as fechaInicioTentativa, "
				+ "p.PRY_DURACION as duracion, p.PRY_DURACION_ACUMULADA as duracionAcumulada, p.PRY_DURACION_DIAS_ACUMULADA as diasAcumulados, p.PRY_ES_CONTRAPARTIDA as esContrapartida, p.PRY_TIENE_COMPROM_PEND as tieneCompromisosPendientes, p.PRY_ESTADO_RECLAMACION as estadoReclamacion, p.PRY_ESTADO_RECLAMACION_EVAL as estadoReclamacionEva "
				+ " from HER_INVESTIGADOR_PROYECTO ip left join HER_INVESTIGADOR_INTERNO ii on ip.inv_id = ii.inv_id and ip.tdo_id = ii.tdo_id left join HER_DEPENDENCIA D on ii.dpn_id = d.dpn_id left join HER_SEDE s on d.sed_id = s.sed_id,"
				+ " HER_PROYECTO p, HER_MODALIDAD mod left join HER_CONVOCATORIA c on mod.MOD_ID = c.CON_ID left join HER_CONVOCATORIA_PADRE cp on cp.CNP_ID "
				+ "= c.CNP_ID left join HER_ESTADO_CONVOCATORIA ec on c.ECO_ID = ec.ECO_ID left join HER_CONVOCATORIA_RESTRICCION cr on cr.TRE_ID = c.CON_RESTRICCION, HER_TIPO_MODALIDAD tm, HER_ESTADO_PROYECTO ep"
				+ " where " + "(( ip.TDO_ID = '" + id.getTipoDocumento() + "' and ip.INV_ID = '" + id.getDocumento()
				+ "') " + "or (p.pry_creador_tdo_id = '" + id.getTipoDocumento() + "' and p.pry_creador_id = '"
				+ id.getDocumento() + "' and 'true' = '" + incluirCreadosPor + "'  and ip.inp_tipo = 'P' ))"
				+ " and mod.MOD_ID = p.MOD_ID and mod.TMO_ID = tm.TMO_ID and p.EPR_ID = ep.EPR_ID and p.PRY_ID = ip.PRY_ID and p.EPR_ID <> "
				+ "'B' order by p.PRY_ID desc";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		proyectosInv = sqlQuery1.addScalar("idProyecto", Hibernate.LONG).addScalar("nombreProyecto", Hibernate.STRING)
				.addScalar("faseProyecto", Hibernate.LONG).addScalar("idModalidad", Hibernate.LONG)
				.addScalar("tipoModalidad", Hibernate.STRING).addScalar("nombreModalidad", Hibernate.STRING)
				.addScalar("permitirModificacion", Hibernate.STRING).addScalar("codigoDib", Hibernate.STRING)
				.addScalar("idEstado", Hibernate.STRING).addScalar("nombreEstado", Hibernate.STRING)
				.addScalar("proyectoPadre", Hibernate.LONG).addScalar("estadoConvocatoria", Hibernate.STRING)
				.addScalar("nombreEstadoConv", Hibernate.STRING).addScalar("tituloConvoca", Hibernate.STRING)
				.addScalar("idConvocaPadre", Hibernate.LONG).addScalar("tituloConvocaPadre", Hibernate.STRING)
				.addScalar("idRestriccion", Hibernate.STRING).addScalar("nombreRestriccion", Hibernate.STRING)
				.addScalar("habilitarInfoEst", Hibernate.STRING).addScalar("habilitarInfoTutor", Hibernate.STRING)
				.addScalar("variosInformesAvance", Hibernate.STRING).addScalar("esJornadaDocente", Hibernate.STRING)
				.addScalar("codigoQuipu", Hibernate.STRING).addScalar("idInvProy", Hibernate.LONG)
				.addScalar("rol", Hibernate.STRING).addScalar("tipoActividad", Hibernate.STRING)
				.addScalar("idDependencia", Hibernate.STRING).addScalar("nombreSede", Hibernate.STRING)
				.addScalar("fechaInicioTentativa", Hibernate.DATE).addScalar("duracion", Hibernate.INTEGER)
				.addScalar("duracionAcumulada", Hibernate.INTEGER).addScalar("diasAcumulados", Hibernate.INTEGER)
				.addScalar("esContrapartida", Hibernate.STRING)
				.addScalar("tieneCompromisosPendientes", Hibernate.STRING)
				.addScalar("estadoReclamacion", Hibernate.STRING).addScalar("estadoReclamacionEva", Hibernate.STRING)
				.list();

		session.close();

		Iterator it = proyectosInv.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Proyecto p = new Proyecto();
			Convocatoria m = new Convocatoria();
			EstadoConvocatoria ec = new EstadoConvocatoria();
			ConvocatoriaPadre cp = new ConvocatoriaPadre();
			TipoModalidad tm = new TipoModalidad();
			RestriccionConvocatoria rc = new RestriccionConvocatoria();
			EstadoProyecto ep = new EstadoProyecto();

			p.setId((Long) name[0]);
			p.setNombre((String) name[1]);

			m.setId((Long) name[3]);
			tm.setId((String) name[4]);
			tm.setNombre((String) name[5]);
			m.setTipo(tm);
			ec.setId((String) name[11]);
			ec.setNombre((String) name[12]);
			m.setEstadoConvocatoria(ec);
			m.setTitulo((String) name[13]);
			cp.setId((Long) name[14]);
			cp.setTitulo((String) name[15]);
			m.setPadre(cp);
			rc.setId((String) name[16]);
			rc.setNombre((String) name[17]);
			m.setRestriccion(rc);
			m.setHabilitarModEstudiantes((String) name[18]);
			m.setHabilitarModTutor((String) name[19]);
			m.setVariosInformesAvance((String) name[20]);
			p.setModalidad(m);
			p.setEsJornadaDocente((String) name[21]);
			p.setEsPryContrapartida((String) name[32]);
			p.setPermitirModificacion((String) name[6]);
			p.setCodigoDib((String) name[7]);

			ep.setId((String) name[8]);
			ep.setNombre((String) name[9]);
			p.setEstadoProyecto(ep);
			p.setCodigoQuipu((String) name[22]);
			p.setProyectoPadre((Long) name[10]);
			p.setTipoActividad((String) name[25]);
			p.setFechaTentativaInicio((Date) name[28]);
			p.setDuracion((Integer) name[29]);
			p.setDuracionAcumulada((Integer) name[30]);
			p.setDuracionDiasAcumulada((Integer) name[31]);
			p.setTieneCompromisosPendientes((String) name[33]);
			p.setEstadoReclamacion((String) name[34]);
			p.setEstadoReclamacionEvaluacion((String) name[35]);

			InvestigadorProyecto ip = new InvestigadorProyecto();
			TipoInvestigador ti = new TipoInvestigador();
			Investigador investigador = new Investigador();
			Dependencia dependencia = new Dependencia();
			dependencia.setId((String) name[26]);
			Sede sede = new Sede();
			sede.setNombre((String) name[27]);
			dependencia.setSede(sede);
			investigador.setDependencia(dependencia);
			ip.setInvestigador(investigador);
			ti.setId((String) name[24]);
			if (ti.getId().equals("P")) {
				Investigador inv = new Investigador();
				inv.setId(id);
				p.setResponsable(inv);
			}

			ip.setProyecto(p);
			ip.setId((Long) name[23]);
			ip.setTipo(ti);
			p.adicionarInvestigadorProyecto(ip);

			result.add(ip);

		}
		Investigador i = new Investigador();
		i.setId(id);
		i.setProyectosInvestigador(result);

		return i;
	}

	public Investigador obtenerProyectosGruposInvestigador(IdPersona id) throws DataAccessException {

		List proyectosInv;
		List gruposInv;
		List investigador;

		Set result = new HashSet();
		Set result2 = new HashSet();

		Session session = getSession();

		String query = "select p.PRY_ID as idProyecto, p.PRY_NOMBRE as nombreProyecto, ip.INP_VISIBLE as visible, ip.INP_ID as invPry, ip.TDO_ID as tipoDoc, ip.INV_ID as doc,ip.INP_DEDICACION_HORAS_SEMANA as dedicSemana,"
				+ " ip.INP_TOTAL_HORAS_VINCULACION as totalHoras, ip.INP_HORAS_JORNADA_DOCENTE as horasJornadaDoc, ip.INP_FUNCION as funcion, ip.INP_VALOR_PAGAR as valorPagar, tp.TINV_ID as idTipoInv, e.EMP_ID as idEmpresa,"
				+ " p.PER_NOMBRE1 as nombre1, p.PER_NOMBRE2 as nombre2, p.PER_APELLIDO1 as apellido1, p.PER_APELLIDO2 as apellido2, p.PER_EMAIL as email, i.INV_URL_COLCIENCIAS as colciencias, p.EPR_ID as estado, p.PRY_FECHA_TENTATIVA_INICIO as fechaInicio"
				+ " from HER_INVESTIGADOR_PROYECTO ip left join HER_EMPRESA e on ip.EMP_ID = e.EMP_ID, HER_PROYECTO p, HER_TIPO_INVESTIGADOR tp, HER_PERSONA p, HER_INVESTIGADOR i"
				+ " where  p.TDO_ID = i.TDO_ID and p.PER_ID = i.INV_ID and ip.TDO_ID = '" + id.getTipoDocumento()
				+ "' and ip.INV_ID = '" + id.getDocumento()
				+ "' and p.PRY_ID = ip.PRY_ID and p.EPR_ID <> 'B' and (p.EPR_ID = 'A' or p.EPR_ID = 'AP' or p.EPR_ID = 'F') and tp.TINV_ID = ip.INP_TIPO and p.TDO_ID = ip.TDO_ID and p.PER_ID = ip.INV_ID order by p.PRY_ID desc";

		String nombre1 = "";
		String nombre2 = "";
		String apellido1 = "";
		String apellido2 = "";
		String email = "";
		String colciencias = "";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);
		boolean esPrimeravez = true;

		proyectosInv = sqlQuery1.addScalar("idProyecto", Hibernate.LONG).addScalar("nombreProyecto", Hibernate.STRING)
				.addScalar("visible", Hibernate.STRING).addScalar("invPry", Hibernate.LONG)
				.addScalar("tipoDoc", Hibernate.STRING).addScalar("doc", Hibernate.STRING)
				.addScalar("dedicSemana", Hibernate.SHORT).addScalar("totalHoras", Hibernate.DOUBLE)
				.addScalar("horasJornadaDoc", Hibernate.INTEGER).addScalar("funcion", Hibernate.STRING)
				.addScalar("valorPagar", Hibernate.LONG).addScalar("idTipoInv", Hibernate.STRING)
				.addScalar("idEmpresa", Hibernate.LONG).addScalar("nombre1", Hibernate.STRING)
				.addScalar("nombre2", Hibernate.STRING).addScalar("apellido1", Hibernate.STRING)
				.addScalar("apellido2", Hibernate.STRING).addScalar("email", Hibernate.STRING)
				.addScalar("colciencias", Hibernate.STRING).addScalar("estado", Hibernate.STRING)
				.addScalar("fechaInicio", Hibernate.DATE).list();

		session.close();

		Session session2 = getSession();

		String query2 = "select g.GRU_ID as idGrupo, g.GRU_NOMBRE as nombreGrupo, ig.ING_ID as idInvg"
				+ " from HER_INVESTIGADOR_GRUPO ig, HER_GRUPO g" + " where  ig.TDO_ID = '" + id.getTipoDocumento()
				+ "' and ig.INV_ID = '" + id.getDocumento()
				+ "' and g.GRU_ID = ig.GRU_ID and g.EGR_ID in ('A') order by g.GRU_ID desc";

		SQLQuery sqlQuery2 = session2.createSQLQuery(query2);

		gruposInv = sqlQuery2.addScalar("idGrupo", Hibernate.LONG).addScalar("nombreGrupo", Hibernate.STRING)
				.addScalar("idInvg", Hibernate.LONG).list();

		session2.close();

		Iterator it = proyectosInv.iterator();
		Iterator it2 = gruposInv.iterator();

		if (proyectosInv == null || proyectosInv.size() == 0) {

			Session session3 = getSession();

			String query3 = "select p.PER_NOMBRE1 as nombre1, p.PER_NOMBRE2 as nombre2, p.PER_APELLIDO1 as apellido1, p.PER_APELLIDO2 as apellido2, p.PER_EMAIL as email, i.INV_URL_COLCIENCIAS as colciencias"
					+ " from HER_PERSONA p, HER_INVESTIGADOR i"
					+ " where p.TDO_ID = i.TDO_ID and p.PER_ID = i.INV_ID and p.TDO_ID = '" + id.getTipoDocumento()
					+ "' and p.PER_ID = '" + id.getDocumento() + "'";

			SQLQuery sqlQuery3 = session3.createSQLQuery(query3);

			investigador = sqlQuery3.addScalar("nombre1", Hibernate.STRING).addScalar("nombre2", Hibernate.STRING)
					.addScalar("apellido1", Hibernate.STRING).addScalar("apellido2", Hibernate.STRING)
					.addScalar("email", Hibernate.STRING).addScalar("colciencias", Hibernate.STRING).list();

			session3.close();

			Iterator it3 = investigador.iterator();
			while (it3.hasNext()) {
				Object[] name = (Object[]) it3.next();
				nombre1 = (String) name[0];
				nombre2 = (String) name[1];
				apellido1 = (String) name[2];
				apellido2 = (String) name[3];
				email = (String) name[4];
				colciencias = (String) name[5];
			}

		}

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Proyecto p = new Proyecto();
			Investigador i = new Investigador();
			TipoInvestigador tp = new TipoInvestigador();
			EstadoProyecto ep = new EstadoProyecto();
			Empresa e = new Empresa();
			e.setId((Long) name[12]);
			InvestigadorProyecto ip = new InvestigadorProyecto();
			i.setId(id);
			tp.setId((String) name[11]);
			p.setId((Long) name[0]);
			p.setNombre((String) name[1]);
			ep.setId((String) name[19]);
			p.setEstadoProyecto(ep);
			p.setFechaTentativaInicio((Date) name[20]);
			ip.setId((Long) name[3]);
			ip.setInvestigador(i);
			ip.setVisible((String) name[2]);
			ip.setDedicacionHorasSemana((Short) name[6]);
			ip.setTotalHorasVinculacion((Double) name[7]);
			ip.setHorasJornadaDocente((Integer) name[8]);
			ip.setFuncion((String) name[9]);
			ip.setValorPagar((Long) name[10]);
			ip.setTipo(tp);
			if (e.getId() != null) {
				ip.setEmpresa(e);
			} else {
				ip.setEmpresa(null);
			}
			ip.setProyecto(p);

			result.add(ip);
			if (esPrimeravez) {
				nombre1 = (String) name[13];
				nombre2 = (String) name[14];
				apellido1 = (String) name[15];
				apellido2 = (String) name[16];
				email = (String) name[17];
				colciencias = (String) name[18];
				esPrimeravez = false;
			}

		}

		while (it2.hasNext()) {
			Object[] name = (Object[]) it2.next();
			InvestigadorGrupo ig = new InvestigadorGrupo();
			Grupo g = new Grupo();

			g.setId((Long) name[0]);
			g.setNombre((String) name[1]);
			ig.setId((Long) name[2]);
			ig.setGrupo(g);

			result2.add(ig);

		}

		Investigador i = new Investigador();
		i.setId(id);
		i.setNombre1(nombre1);
		i.setNombre2(nombre2);
		i.setApellido1(apellido1);
		i.setApellido2(apellido2);
		i.setEmail(email);
		i.setUrlColciencias(colciencias);
		i.setProyectosInvestigador(result);
		i.setGruposInvestigador(result2);

		return i;
	}

	@Override
	public void guardarLineasInvestigacionEvaluadorExterno(List<LineaInvestigacion> list, IdPersona id) {
		Session s = getSession();
		Statement stDelete = null;
		Statement stInsert = null;
		try {
			stDelete = s.connection().createStatement();
			String sa = "DELETE her_evaluador_linea_inv " + "WHERE inv_id = '" + id.getDocumento() + "' "
					+ "AND tdo_id = '" + id.getTipoDocumento() + "'";
			stDelete.execute(sa);
			
			stInsert = s.connection().createStatement();
			for(int i=0; i<list.size();i++) {
				String insert = "INSERT INTO HER_EVALUADOR_LINEA_INV (TDO_ID, INV_ID, LIN_ID) VALUES "
						+ "('"+id.getTipoDocumento()+"', '"+id.getDocumento()+"', '"+list.get(i).getId()+"')";
				stInsert.execute(insert);
			}			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
		
	}

	@Override
	public void actualizarEvaluador(Evaluador evaluador) {
		// TODO Auto-generated method stub
		Session s = getSession();
		Statement st = null;
		try {
			st = s.connection().createStatement();
			String sentencia = "UPDATE her_evaluador " + "SET eva_area_experticia = '" + evaluador.getAreaExperticia() + "', "
					+ "eva_tesis = '" + evaluador.getTesis() + "', " + "eva_formacion = '" + evaluador.getFormacion() + "', "
					+ "eva_area_ciencia = '" + evaluador.getAreaCiencia() + "', " + "eva_subarea_ciencia = '" + evaluador.getSubAreaCiencia()
					+ "', " + "eva_cvlac = '" + evaluador.getCvlac() + "', " + "eva_minciencias = '"
					+ evaluador.getMinciencias() + "', " + "ffi_id_labora = '" + evaluador.getInstitucionLabora().getId() + "', " + "ffi_id_estudio = '"
					+ evaluador.getInstitucionEstudio().getId() + "', " + "eva_tipo = '" + evaluador.getTipo() + "' "
					+ "WHERE tdo_id = '"+evaluador.getId().getTipoDocumento()+"' and inv_id = '" + evaluador.getId().getDocumento()+"'";
			System.out.println(sentencia);
			st.execute(sentencia);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}
	
	public void actualizarFechaVencimientoRol(IdPersona persona, Rol rol, Date fechaVencimiento) {

		Session s = getSession();
		Statement st = null;
		try {
			st = s.connection().createStatement();
			java.sql.Date sqlDate = new java.sql.Date(fechaVencimiento.getTime());
			String sentencia = "UPDATE her_persona_rol SET HPR_FECHA_FIN = TO_DATE('" + sqlDate
					+ "', 'YYYY-MM-DD') WHERE per_id = '" + persona.getDocumento() +"' and tdo_id = '"+persona.getTipoDocumento()+"' and rol_id = '"+rol.getId()+"'" ;
			System.out.println(sentencia);
			st.execute(sentencia);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}


}
