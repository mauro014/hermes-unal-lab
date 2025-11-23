package co.edu.unal.hermes.bd.imp;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.unal.hermes.bd.IGeneralDAO;
import co.edu.unal.hermes.modelo.ArchivoInforme;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.Bien;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorreoPersonaBoletin;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DistribucionRecursos;
import co.edu.unal.hermes.modelo.DocumentoVice;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Empresa;
import co.edu.unal.hermes.modelo.EstadoCivil;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.EstadoInforme;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.GrupoLaboratorioVista;
import co.edu.unal.hermes.modelo.HistoricoEstadoSolicitud;
import co.edu.unal.hermes.modelo.IdDominioDetalle;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Instructivo;
import co.edu.unal.hermes.modelo.InstructivoClasificacion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorAreaInteres;
import co.edu.unal.hermes.modelo.InvestigadorAreaInvestigacion;
import co.edu.unal.hermes.modelo.InvestigadorAsignatura;
import co.edu.unal.hermes.modelo.InvestigadorEnlace;
import co.edu.unal.hermes.modelo.InvestigadorEvento;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorLineaInvestigacion;
import co.edu.unal.hermes.modelo.InvestigadorObraExposicion;
import co.edu.unal.hermes.modelo.InvestigadorPublicacion;
import co.edu.unal.hermes.modelo.LineaInvestigacion;
import co.edu.unal.hermes.modelo.LogAlertasAutomaticas;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.MovilidadAlertaAutomatica;
import co.edu.unal.hermes.modelo.MovilidadArchivo;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.ParametroMaestro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.PosibleEvaluador;
import co.edu.unal.hermes.modelo.Pregunta;
import co.edu.unal.hermes.modelo.PreguntaClasificacion;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.ProyectoLaboratorioVista;
import co.edu.unal.hermes.modelo.Reporte;
import co.edu.unal.hermes.modelo.Requerimiento;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroActividad;
import co.edu.unal.hermes.modelo.SemilleroInforme;
import co.edu.unal.hermes.modelo.SemilleroInformeActividad;
import co.edu.unal.hermes.modelo.SemilleroLaboratorioVista;
import co.edu.unal.hermes.modelo.Servicio;
import co.edu.unal.hermes.modelo.TipoArchivoMovilidad;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoFormacion;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.VAsignaturasSIA;
import co.edu.unal.hermes.modelo.alertasAutomaticas.AlertasAutoProyectos;
import co.edu.unal.hermes.modelo.alertasAutomaticas.AlertasAutoProyectosConvenios;
import co.edu.unal.hermes.modelo.alertasAutomaticas.AlertasAutomaticasAval;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioActividadEquipoInterfazAlerta;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioAreasSecundariasOCDE;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioCostosServicio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEnsayosServicios;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioLogActividades;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioLogEquipos;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioLogLaboratorios;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioSolicitud;
import co.edu.unal.hermes.modelo.laboratorios.PersonaLaboratorio;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.vista.utils.Util;

/**
 * Maneja el acceso a los atributos generales del sistema con Hibernate
 */
public class GeneralDAOHibernate extends HibernateDaoSupport implements IGeneralDAO {

	public List<Bien> consultaEquipos(String placa, Long idSede) throws DataAccessException {

		List<?> documentos = new ArrayList<Object>();
		Session session = getSession();
		try {
			String select = 
				"SELECT " + 
				"placa, " + 
				"serial, " + 
				"ficha_tecnica, " + 
				"componentes, " + 
				"valor, " + 
				"ubicacion, " + 
				"nombre_ubicacion, " + 
				"responsable, " + 
				"nombre_responsable, " + 
				"estado, " + 
				"nombre_estado, "  + 
				"fecha_servicio, " + 
				"fecha_adquisicion, " + 
				"est_fisico, " + 
				"nombre_estado_fisico";
			String from = " FROM V_BIENES@HERMES_INTEGRA";
			String fromRg = " FROM v_bienes_regalias@hermes_integra";
			String union = " UNION ";
			String where = " WHERE placa = '" + placa + "'";
			String query = select + from + where + union + select + fromRg + where;
			SQLQuery sqlQuery1 = session.createSQLQuery(query);
			documentos = sqlQuery1.addScalar("placa", Hibernate.STRING).addScalar("serial", Hibernate.STRING)
					.addScalar("ficha_tecnica", Hibernate.STRING).addScalar("componentes", Hibernate.STRING)
					.addScalar("valor", Hibernate.STRING).addScalar("ubicacion", Hibernate.STRING)
					.addScalar("nombre_ubicacion", Hibernate.STRING).addScalar("responsable", Hibernate.STRING)
					.addScalar("nombre_responsable", Hibernate.STRING).addScalar("estado", Hibernate.STRING)
					.addScalar("nombre_estado", Hibernate.STRING).addScalar("fecha_servicio", Hibernate.DATE)
					.addScalar("fecha_adquisicion", Hibernate.DATE).addScalar("est_fisico", Hibernate.STRING)
					.addScalar("nombre_estado_fisico", Hibernate.STRING).list();
		} catch (Exception e) {
			System.out.println("Error: " + e.getStackTrace());
			documentos = new ArrayList<Object>();
		} finally {
			
			if(session!=null) {
				session.close();
			}
		}

		if (documentos.size() > 0) {
			System.out.println("Equipo encontrado.");
			// break;
		} else {
			System.out.println("Equipo NO encontrado.");
		}
		// }

		List<Bien> result = new ArrayList<Bien>();
		Iterator<?> it = documentos.iterator();
		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Bien bien = new Bien();
			String valor = name[4].toString();
			System.out.println("valor: " + valor);
			if (valor.contains(".")) {
				valor = valor.substring(0, valor.indexOf("."));
				System.out.println("valor sin punto: " + valor);
			}

			bien.setPlaca((String) name[0]);
			bien.setSerial((String) name[1]);
			bien.setDescripcion((String) name[2]);
			bien.setEquipo(buscarEnDescripcion((String) name[2], "DESCRIPCION :"));
			bien.setMarca(buscarEnDescripcion((String) name[2], "MARCA :"));
			bien.setModelo(buscarEnDescripcion((String) name[2], "MODELO :"));
			bien.setValor(Long.parseLong(valor));
			bien.setIdUbicacion((String) name[5]);
			bien.setUbicacion((String) name[6]);
			bien.setIdResponsable(Integer.parseInt(name[7].toString()));
			bien.setResponsable((String) name[8]);
			bien.setIdEstado(Integer.parseInt(name[9].toString()));
			bien.setEstado((String) name[10]);
			bien.setFechaServicio((Date) name[11]);
			bien.setFechaAdquisicion((Date) name[12]);
			bien.setIdEstadoFisico(Integer.parseInt(name[13].toString()));
			bien.setEstadoFisico((String) name[14]);
			result.add(bien);
		}
		return result;
	}

	public List<ProyectoLaboratorioVista> consultaProyectosAsociadosLaboratorio(Long idLab) throws DataAccessException {
		Session session = getSession();
		List<?> registros = new ArrayList<Object>();
		try {
			String query = "SELECT \r\n"
					+ "PRY.PRY_ID AS ID, \r\n"
					+ "DD_TIPOLOGIA.DOMDET_DESCRIPCION AS TIPOLOGIA, \r\n"
					+ "SED.SED_NOMBRE AS SEDE, \r\n"
					+ "FAC.DPN_NOMBRE AS FACULTAD, \r\n"
					+ "DECODE(CON.CON_ID, 10, 'Externa', 'Interna') AS TIPO_CONVOCATORIA, \r\n"
					+ "CNP.CNP_TITULO AS CONVOCATORIA, \r\n"
					+ "CON.CON_TITULO AS MODALIDAD, \r\n"
					+ "PRY.PRY_NOMBRE AS NOMBRE, \r\n"
					+ "(DIRECTOR.PER_NOMBRE1 || ' ' || DIRECTOR.PER_NOMBRE2 ||' '|| DIRECTOR.PER_APELLIDO1||' '||DIRECTOR.PER_APELLIDO2 ) as DIRECTOR, \r\n"
					+ "EP.EPR_NOMBRE AS ESTADO, \r\n"
					+ "TO_CHAR(PRY.PRY_FECHA_TENTATIVA_INICIO, 'YYYY-MM-DD') as FECHA_INICIO, \r\n"
					+ "TO_CHAR(add_months(PRY.PRY_FECHA_TENTATIVA_INICIO,decode(PRY.PRY_DURACION_ACUMULADA,NULL,0,PRY.PRY_DURACION_ACUMULADA)) + decode(PRY.PRY_DURACION_DIAS_ACUMULADA,NULL,0,PRY.PRY_DURACION_DIAS_ACUMULADA), 'YYYY-MM-DD') AS FECHA_FIN, \r\n"
					+ "(select listagg(GR.GRU_NOMBRE,'; ') WITHIN GROUP (ORDER BY GR.GRU_NOMBRE) from HER_GRUPO GR, HER_GRUPO_PROYECTO GP where GR.GRU_ID = GP.GRU_ID and GP.PRY_ID = PRY.PRY_ID group by GP.PRY_ID) as GRUPOS, \r\n"
					+ "(select listagg(DOM.DOMDET_DESCRIPCION || ' (' || DET.DOMDET_DESCRIPCION || ')','; ') WITHIN GROUP (ORDER BY DOM.DOMDET_DESCRIPCION) from HER_PROYECTO_AREA_TEMATICA PAT, HER_DOMINIO_DETALLE DET, HER_DOMINIO_DETALLE DOM WHERE PAT.PRY_ID = PRY.PRY_ID AND PAT.DOMDET_TIPO = DET.DOMDET_TIPO AND DET.DOMDET_ESTADO = DOM.DOMDET_TIPO AND PAT.ART_TIPO = 1 GROUP BY PAT.PRY_ID) as AREA_OCDE_PRINCIPAL, \r\n"
					+ "(select listagg(DOM.DOMDET_DESCRIPCION || ' (' || DET.DOMDET_DESCRIPCION || ')','; ') WITHIN GROUP (ORDER BY DOM.DOMDET_DESCRIPCION) from HER_PROYECTO_AREA_TEMATICA PAT, HER_DOMINIO_DETALLE DET, HER_DOMINIO_DETALLE DOM WHERE PAT.PRY_ID = PRY.PRY_ID AND PAT.DOMDET_TIPO = DET.DOMDET_TIPO AND DET.DOMDET_ESTADO = DOM.DOMDET_TIPO AND PAT.ART_TIPO = 2 GROUP BY PAT.PRY_ID) as AREAS_OCDE_SECUNDARIA, \r\n"
//					+ "--'GRUPOS' as GRUPOS, \r\n"
//					+ "--'AREA_OCDE_PRINCIPAL' as AREA_OCDE_PRINCIPAL, \r\n"
//					+ "--'AREAS_OCDE_SECUNDARIA' as AREAS_OCDE_SECUNDARIA, \r\n"
					+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'ESTUDIANTES_PREGRADO') AS ESTUDIANTES_PREGRADO, \r\n"
					+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'ESTUDIANTES_POSGRADO') AS ESTUDIANTES_POSGRADO, \r\n"
					+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'ESTUDIANTES_LIDERES') AS ESTUDIANTES_LIDERES, \r\n"
					+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'ESTUDIANTES_VISITANTES') AS ESTUDIANTES_VISITANTES, \r\n"
					+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'DOCENTES') AS DOCENTES, \r\n"
					+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'EGRESADOS') AS EGRESADOS, \r\n"
					+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'CONTRATISTAS') AS CONTRATISTAS, \r\n"
					+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'ADMINISTRATIVOS') AS ADMINISTRATIVOS, \r\n"
					+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'EXTERNOS') AS EXTERNOS \r\n"
					+ "FROM HER_PROYECTO PRY \r\n"
					+ "INNER JOIN HER_LABORATORIO_PROYECTO LAP ON PRY.PRY_ID = LAP.PRY_ID \r\n"
					+ "LEFT JOIN HER_ESTADO_PROYECTO EP on EP.EPR_ID = PRY.EPR_ID \r\n"
					+ "LEFT JOIN HER_DOMINIO_DETALLE DD_TIPOLOGIA ON DD_TIPOLOGIA.DOMDET_TIPO = PRY.PRY_TIPO_ACTIVIDAD AND DD_TIPOLOGIA.DOM_ID = 117 \r\n"
					+ "LEFT JOIN HER_CONVOCATORIA CON ON CON.CON_ID = PRY.MOD_ID \r\n"
					+ "LEFT JOIN HER_CONVOCATORIA_PADRE CNP ON CNP.CNP_ID = CON.CNP_ID \r\n"
					+ "LEFT JOIN HER_INVESTIGADOR_PROYECTO INVP ON INVP.PRY_ID = PRY.PRY_ID AND INVP.INP_TIPO ='P' \r\n"
					+ "LEFT JOIN HER_PERSONA DIRECTOR ON DIRECTOR.PER_ID = INVP.INV_ID \r\n"
					+ "LEFT JOIN HER_INVESTIGADOR_INTERNO II on INVP.INV_ID = II.INV_ID and INVP.TDO_ID = II.TDO_ID \r\n"
					+ "LEFT JOIN HER_DEPENDENCIA DEP on DEP.DPN_ID = II.DPN_ID \r\n"
					+ "LEFT JOIN HER_DEPENDENCIA FAC on FAC.DPN_ID = DEP.DPN_FACULTAD \r\n"
					+ "LEFT JOIN HER_SEDE SED on DEP.SED_ID = SED.SED_ID \r\n"
					+ "WHERE LAP.LAB_ID = " + idLab + " \r\n"
					+ "AND PRY.EPR_ID IN ('AP', 'A', 'CN', 'S', 'PF', 'F')";
			SQLQuery sqlQuery1 = session.createSQLQuery(query);
			registros = sqlQuery1
					.addScalar("ID", Hibernate.STRING)						//0
					.addScalar("TIPOLOGIA", Hibernate.STRING)				//1
					.addScalar("SEDE", Hibernate.STRING)					//2
					.addScalar("FACULTAD", Hibernate.STRING)				//3
					.addScalar("TIPO_CONVOCATORIA", Hibernate.STRING)		//4
					.addScalar("CONVOCATORIA", Hibernate.STRING)			//5
					.addScalar("MODALIDAD", Hibernate.STRING)				//6
					.addScalar("NOMBRE", Hibernate.STRING)					//7
					.addScalar("DIRECTOR", Hibernate.STRING)				//8
					.addScalar("ESTADO", Hibernate.STRING)					//9
					.addScalar("FECHA_INICIO", Hibernate.STRING)			//10
					.addScalar("FECHA_FIN", Hibernate.STRING)				//11
					.addScalar("GRUPOS", Hibernate.STRING)					//12 -------
					.addScalar("AREA_OCDE_PRINCIPAL", Hibernate.STRING)		//13 ---------
					.addScalar("AREAS_OCDE_SECUNDARIA", Hibernate.STRING)	//14 ----------
					.addScalar("ESTUDIANTES_PREGRADO", Hibernate.STRING)	//15
					.addScalar("ESTUDIANTES_POSGRADO", Hibernate.STRING)	//16
					.addScalar("ESTUDIANTES_LIDERES", Hibernate.STRING)		//17
					.addScalar("ESTUDIANTES_VISITANTES", Hibernate.STRING)	//18
					.addScalar("DOCENTES", Hibernate.STRING)				//19
					.addScalar("EGRESADOS", Hibernate.STRING)				//20
					.addScalar("CONTRATISTAS", Hibernate.STRING)			//21
					.addScalar("ADMINISTRATIVOS", Hibernate.STRING)			//22
					.addScalar("EXTERNOS", Hibernate.STRING)				//23
					.list();

			

		} catch (Exception e) {
			System.out.println("Error: " + e.getStackTrace());
			registros = new ArrayList<Object>();
		}finally {
			session.close();	
		}

		List<ProyectoLaboratorioVista> result = new ArrayList<ProyectoLaboratorioVista>();
		Iterator<?> it = registros.iterator();
		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoLaboratorioVista proyecto = new ProyectoLaboratorioVista();

			proyecto.setId((String) name[0]);
			proyecto.setTipologia((String) name[1]);
			proyecto.setSede((String) name[2]);
			proyecto.setFacultad((String) name[3]);
			proyecto.setTipoConvocatoria((String) name[4]);
			proyecto.setConvocatoria((String) name[5]);
			proyecto.setModalidad((String) name[6]);
			proyecto.setNombre((String) name[7]);
			proyecto.setDirector((String) name[8]);
			proyecto.setEstado((String) name[9]);
			proyecto.setFechaInicio((String) name[10]);
			proyecto.setFechaFin((String) name[11]);
			proyecto.setGrupos((String) name[12]);
			proyecto.setAreaOCDEPrincipal((String) name[13]);
			proyecto.setAreasOCDESecundarias((String) name[14]);
			proyecto.setEstudiantesPregrado((String) name[15]);
			proyecto.setEstudiantesPosgrado((String) name[16]);
			proyecto.setEstudiantesLideres((String) name[17]);
			proyecto.setEstudiantesVisitantes((String) name[18]);
			proyecto.setDocentes((String) name[19]);
			proyecto.setEgresados((String) name[20]);
			proyecto.setContratistas((String) name[21]);
			proyecto.setAdministrativos((String) name[22]);
			proyecto.setExternos((String) name[23]);
			result.add(proyecto);
		}
		return result;
	}
	
	public List<GrupoLaboratorioVista> consultaGruposAsociadosLaboratorio(Long idLab) throws DataAccessException {
		Session session = getSession();
		List<?> registros = new ArrayList<Object>();
		try {
			
			String query = "SELECT \r\n"
					+ "GRUPO.GRU_ID AS ID, \r\n"
					+ "GRUPO.GRU_NOMBRE AS NOMBRE, \r\n"
					+ "(LIDER.PER_NOMBRE1 || ' ' || LIDER.PER_NOMBRE2 ||' '|| LIDER.PER_APELLIDO1||' '||LIDER.PER_APELLIDO2 ) as LIDER, \r\n"
					+ "SED.SED_NOMBRE AS SEDE, \r\n"
					+ "FAC.DPN_NOMBRE AS FACULTAD, \r\n"
					+ "EGRU.EGR_NOMBRE AS ESTADO_UN, \r\n"
					+ "EGC.EGC_NOMBRE AS ESTADO_SCIENTI, \r\n"
					+ "CAT.GRC_NOMBRE AS CATEGORIA_SCIENTI, \r\n"
					+ "AREA.DOMDET_DESCRIPCION || ' (' || SUB_AREA.DOMDET_DESCRIPCION || ')' AS AREA_OCDE_PRINCIPAL, \r\n"
					+ "(SELECT LISTAGG(DD_PADRE2.DOMDET_DESCRIPCION || ' (' || DD2.DOMDET_DESCRIPCION || ')','; ') WITHIN GROUP (ORDER BY DD_PADRE2.DOMdET_DESCRIPCION) FROM HER_GRUPO_AREA_TEMATICA GAT LEFT JOIN HER_DOMINIO_DETALLE DD2 ON GAT.DOMDET_TIPO = DD2.DOMDET_TIPO AND GAT.DOM_ID = DD2.DOM_ID LEFT JOIN HER_DOMINIO_DETALLE DD_PADRE2 ON DD2.DOMDET_ESTADO = DD_PADRE2.DOMDET_TIPO WHERE GAT.GRU_ID = GRUPO.GRU_ID GROUP BY GAT.GRU_ID) AS AREAS_OCDE_SECUNDARIA, \r\n"
//					+ "--'AREAS_OCDE_SECUNDARIA' AS AREAS_OCDE_SECUNDARIA, \r\n"
					+ "NUMERO_INTEGRANTES_GRUPO(GRUPO.GRU_ID, 'ESTUDIANTES_PREGRADO') AS ESTUDIANTES_PREGRADO, \r\n"
					+ "NUMERO_INTEGRANTES_GRUPO(GRUPO.GRU_ID, 'ESTUDIANTES_POSGRADO') AS ESTUDIANTES_POSGRADO, \r\n"
					+ "NUMERO_INTEGRANTES_GRUPO(GRUPO.GRU_ID, 'ESTUDIANTES_VISITANTES') AS ESTUDIANTES_VISITANTES, \r\n"
					+ "NUMERO_INTEGRANTES_GRUPO(GRUPO.GRU_ID, 'DOCENTES') AS DOCENTES, \r\n"
					+ "NUMERO_INTEGRANTES_GRUPO(GRUPO.GRU_ID, 'ADMINISTRATIVOS') AS ADMINISTRATIVOS, \r\n"
					+ "NUMERO_INTEGRANTES_GRUPO(GRUPO.GRU_ID, 'EGRESADOS') AS EGRESADOS, \r\n"
					+ "NUMERO_INTEGRANTES_GRUPO(GRUPO.GRU_ID, 'EXTERNOS') AS EXTERNOS, \r\n"
					+ "PRY.PRY_ID AS ID_PROYECTO, \r\n"
					+ "PRY.PRY_NOMBRE AS NOMBRE_PROYECTO \r\n"
					+ "FROM HER_PROYECTO PRY \r\n"
					+ "INNER JOIN HER_LABORATORIO_PROYECTO LAP ON PRY.PRY_ID = LAP.PRY_ID \r\n"
					+ "INNER JOIN HER_GRUPO_PROYECTO GRP ON GRP.PRY_ID = PRY.PRY_ID \r\n"
					+ "INNER JOIN HER_GRUPO GRUPO ON GRUPO.GRU_ID = GRP.GRU_ID \r\n"
					+ "LEFT JOIN HER_INVESTIGADOR_GRUPO ING ON ING.GRU_ID = GRUPO.GRU_ID AND ING.ING_TIPO = 'L'   \r\n"
					+ "LEFT JOIN HER_GRUPO_CATEGORIA CAT ON CAT.GRC_ID = GRUPO.GRU_CATEGORIA_COLCIENCIAS \r\n"
					+ "LEFT JOIN HER_ESTADO_GRUPO_COLCIENCIAS EGC ON EGC.EGC_ID = GRUPO.EGC_ID \r\n"
					+ "LEFT JOIN HER_ESTADO_GRUPO EGRU ON EGRU.EGR_ID = GRUPO.EGR_ID \r\n"
					+ "LEFT JOIN HER_PERSONA LIDER ON LIDER.PER_ID = ING.INV_ID AND LIDER.TDO_ID = ING.TDO_ID \r\n"
					+ "LEFT JOIN HER_INVESTIGADOR_INTERNO II on ING.INV_ID = II.INV_ID and ING.TDO_ID = II.TDO_ID \r\n"
					+ "LEFT JOIN HER_DEPENDENCIA DEP on DEP.DPN_ID = II.DPN_ID \r\n"
					+ "LEFT JOIN HER_DEPENDENCIA FAC on FAC.DPN_ID = DEP.DPN_FACULTAD \r\n"
					+ "LEFT JOIN HER_SEDE SED on DEP.SED_ID = SED.SED_ID \r\n"
					+ "LEFT JOIN HER_DOMINIO_DETALLE SUB_AREA on GRUPO.DOM_ID_SUBAREA = SUB_AREA.DOM_ID and GRUPO.DOMDET_TIPO_SUBAREA = SUB_AREA.DOMDET_TIPO  \r\n"
					+ "LEFT JOIN HER_DOMINIO_DETALLE AREA on SUB_AREA.DOMDET_ESTADO = AREA.DOMDET_TIPO \r\n"
					+ "WHERE LAP.LAB_ID = " + idLab + " \r\n"
					+ "AND PRY.EPR_ID IN ('AP', 'A', 'CN', 'S', 'PF', 'F') \r\n"
					+ "AND GRUPO.EGR_ID = 'A' \r\n"
					+ "ORDER BY LAP.LAB_ID, PRY.PRY_ID, GRUPO.GRU_NOMBRE";
			SQLQuery sqlQuery1 = session.createSQLQuery(query);
			registros = sqlQuery1
					.addScalar("ID", Hibernate.STRING)						//0
					.addScalar("NOMBRE", Hibernate.STRING)					//1
					.addScalar("LIDER", Hibernate.STRING)					//2
					.addScalar("SEDE", Hibernate.STRING)					//3
					.addScalar("FACULTAD", Hibernate.STRING)				//4
					.addScalar("ESTADO_UN", Hibernate.STRING)				//5
					.addScalar("ESTADO_SCIENTI", Hibernate.STRING)			//6
					.addScalar("CATEGORIA_SCIENTI", Hibernate.STRING)		//7
					.addScalar("AREA_OCDE_PRINCIPAL", Hibernate.STRING)		//8
					.addScalar("AREAS_OCDE_SECUNDARIA", Hibernate.STRING)	//9
					.addScalar("ESTUDIANTES_PREGRADO", Hibernate.STRING)	//10
					.addScalar("ESTUDIANTES_POSGRADO", Hibernate.STRING)	//11
					.addScalar("ESTUDIANTES_VISITANTES", Hibernate.STRING)	//12 -------
					.addScalar("DOCENTES", Hibernate.STRING)				//13 ---------
					.addScalar("ADMINISTRATIVOS", Hibernate.STRING)			//14 ----------
					.addScalar("EGRESADOS", Hibernate.STRING)				//15
					.addScalar("EXTERNOS", Hibernate.STRING)				//16
					.addScalar("ID_PROYECTO", Hibernate.STRING)				//17
					.addScalar("NOMBRE_PROYECTO", Hibernate.STRING)			//18
					.list();

			

		} catch (Exception e) {
			System.out.println("Error: " + e.getStackTrace());
			registros = new ArrayList<Object>();
		}finally {
			session.close();
		}

		List<GrupoLaboratorioVista> result = new ArrayList<GrupoLaboratorioVista>();
		Iterator<?> it = registros.iterator();
		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			GrupoLaboratorioVista grupo = new GrupoLaboratorioVista();
			grupo.setId((String) name[0]);
			grupo.setNombre((String) name[1]);
			grupo.setLider((String) name[2]);
			grupo.setSede((String) name[3]);
			grupo.setFacultad((String) name[4]);
			grupo.setEstadoUn((String) name[5]);
			grupo.setEstadoScienti((String) name[6]);
			grupo.setCategoriaScienti((String) name[7]);
			grupo.setAreaOCDEPrincipal((String) name[8]);
			grupo.setAreasOCDESecundarias((String) name[9]);
			grupo.setEstudiantesPregrado((String) name[10]);
			grupo.setEstudiantesPosgrado((String) name[11]);
			grupo.setEstudiantesVisitantes((String) name[12]);
			grupo.setDocentes((String) name[13]);
			grupo.setAdministrativos((String) name[14]);
			grupo.setEgresados((String) name[15]);
			grupo.setExternos((String) name[16]);
			grupo.setIdProyecto((String) name[17]);
			grupo.setNombreProyecto((String) name[18]);		
			result.add(grupo);
		}
		return result;
	}
	
	public List<SemilleroLaboratorioVista> consultaSemillerosAsociadosLaboratorio(Long idLab) throws DataAccessException {
		Session session = getSession();
		List<?> registros = new ArrayList<Object>();
		try {
			
			String query = "SELECT \r\n"
					+ "SEM.SEM_ID AS ID, \r\n"
					+ "SEM.SEM_NOMBRE AS NOMBRE,\r\n"
					+ "(LIDER.PER_NOMBRE1 || ' ' || LIDER.PER_NOMBRE2 ||' '|| LIDER.PER_APELLIDO1||' '||LIDER.PER_APELLIDO2 ) as LIDER, \r\n"
					+ "EST_ACTUAL.NOMBRE_ESTADO AS ESTADO,\r\n"
					+ "SED.SED_NOMBRE AS SEDE, \r\n"
					+ "FAC.DPN_NOMBRE AS FACULTAD,\r\n"
					+ "AREA.DOMDET_DESCRIPCION || ' (' || SUB_AREA.DOMDET_DESCRIPCION || ')' AS AREA_OCDE_PRINCIPAL, \r\n"
					+ "(SELECT LISTAGG(DD_PADRE2.DOMDET_DESCRIPCION || ' (' || DD2.DOMDET_DESCRIPCION || ')','; ') WITHIN GROUP (ORDER BY DD_PADRE2.DOMdET_DESCRIPCION) FROM HER_SEMILLERO_AREA_OCDE GAT LEFT JOIN HER_DOMINIO_DETALLE DD2 ON GAT.DOMDET_TIPO = DD2.DOMDET_TIPO AND GAT.DOM_ID = DD2.DOM_ID LEFT JOIN HER_DOMINIO_DETALLE DD_PADRE2 ON DD2.DOMDET_ESTADO = DD_PADRE2.DOMDET_TIPO WHERE GAT.SEM_ID = SEM.SEM_ID GROUP BY GAT.SEM_ID) AS AREAS_OCDE_SECUNDARIA, \r\n"
//					+ "--'AREAS_OCDE_SECUNDARIA' AS AREAS_OCDE_SECUNDARIA, \r\n"
					+ "NUMERO_INTEGRANTES_SEMILLERO(SEM.SEM_ID, 'ESTUDIANTES_PREGRADO') AS ESTUDIANTES_PREGRADO, \r\n"
					+ "NUMERO_INTEGRANTES_SEMILLERO(SEM.SEM_ID, 'ESTUDIANTES_POSGRADO') AS ESTUDIANTES_POSGRADO, \r\n"
					+ "NUMERO_INTEGRANTES_SEMILLERO(SEM.SEM_ID, 'ESTUDIANTES_LIDERES') AS ESTUDIANTES_LIDERES, \r\n"
					+ "NUMERO_INTEGRANTES_SEMILLERO(SEM.SEM_ID, 'ESTUDIANTES_VISITANTES') AS ESTUDIANTES_VISITANTES, \r\n"
					+ "NUMERO_INTEGRANTES_SEMILLERO(SEM.SEM_ID, 'DOCENTES') AS DOCENTES, \r\n"
					+ "NUMERO_INTEGRANTES_SEMILLERO(SEM.SEM_ID, 'EGRESADOS') AS EGRESADOS, \r\n"
					+ "NUMERO_INTEGRANTES_SEMILLERO(SEM.SEM_ID, 'EXTERNOS') AS EXTERNOS \r\n"
					+ "FROM HER_SEMILLERO SEM \r\n"
					+ "INNER JOIN HER_SEMILLERO_LABORATORIO SLA ON SLA.SEM_ID = SEM.SEM_ID \r\n"
					+ "LEFT JOIN HER_SEMILLERO_INTEGRANTE INTS ON INTS.SEM_ID = SEM.SEM_ID AND INTS.SIT_ID ='DD' \r\n"
					+ "LEFT JOIN HER_PERSONA LIDER ON LIDER.PER_ID = INTS.INV_ID \r\n"
					+ "LEFT JOIN HER_INVESTIGADOR_INTERNO II ON INTS.INV_ID = II.INV_ID and INTS.TDO_ID = II.TDO_ID \r\n"
					+ "LEFT JOIN HER_DEPENDENCIA DEP ON DEP.DPN_ID = II.DPN_ID \r\n"
					+ "LEFT JOIN HER_DEPENDENCIA FAC ON FAC.DPN_ID = DEP.DPN_FACULTAD \r\n"
					+ "LEFT JOIN HER_SEDE SED ON DEP.SED_ID = SED.SED_ID \r\n"
					+ "LEFT JOIN HER_DOMINIO_DETALLE SUB_AREA ON SUB_AREA.DOMDET_TIPO = SEM.SEM_SUBAREA_OCDE_PRINCIPAL  \r\n"
					+ "LEFT JOIN HER_DOMINIO_DETALLE AREA ON AREA.DOMDET_TIPO = SEM.SEM_AREA_OCDE_PRINCIPAL \r\n"
					+ "LEFT JOIN (SELECT DISTINCT HSHE.SEM_ID AS SEM_ID, HSE.SEE_ID AS ID_ESTADO, HSE.SEE_NOMBRE AS NOMBRE_ESTADO \r\n"
					+ "FROM HER_SEMILLERO_HISTORICO_ESTADO HSHE \r\n"
					+ "INNER JOIN ( \r\n"
					+ "SELECT sem_id, max(HSE_ID) id \r\n"
					+ "FROM HER_SEMILLERO_HISTORICO_ESTADO \r\n"
					+ "GROUP BY sem_id) EA \r\n"
					+ "ON EA.SEM_ID = HSHE.SEM_ID \r\n"
					+ "INNER JOIN HER_SEMILLERO_HISTORICO_ESTADO HSHE2 ON EA.ID = HSHE2.HSE_ID \r\n"
					+ "INNER JOIN HER_SEMILLERO_ESTADO HSE ON HSHE2.SEE_ID = HSE.SEE_ID) EST_ACTUAL ON EST_ACTUAL.SEM_ID = SEM.SEM_ID \r\n"
					+ "WHERE SLA.LAB_ID = " + idLab + " \r\n"
					+ "AND EST_ACTUAL.ID_ESTADO IN (5)";
			SQLQuery sqlQuery1 = session.createSQLQuery(query);
			registros = sqlQuery1
					.addScalar("ID", Hibernate.STRING)						//0
					.addScalar("NOMBRE", Hibernate.STRING)					//1
					.addScalar("LIDER", Hibernate.STRING)					//2
					.addScalar("ESTADO", Hibernate.STRING)					//3
					.addScalar("SEDE", Hibernate.STRING)					//4
					.addScalar("FACULTAD", Hibernate.STRING)				//5
					.addScalar("AREA_OCDE_PRINCIPAL", Hibernate.STRING)		//6
					.addScalar("AREAS_OCDE_SECUNDARIA", Hibernate.STRING)	//7
					.addScalar("ESTUDIANTES_PREGRADO", Hibernate.STRING)	//8
					.addScalar("ESTUDIANTES_POSGRADO", Hibernate.STRING)	//9
					.addScalar("ESTUDIANTES_LIDERES", Hibernate.STRING)		//10
					.addScalar("ESTUDIANTES_VISITANTES", Hibernate.STRING)	//11 -------
					.addScalar("DOCENTES", Hibernate.STRING)				//12 ---------
					.addScalar("EGRESADOS", Hibernate.STRING)				//13
					.addScalar("EXTERNOS", Hibernate.STRING)				//14
					.list();

			

		} catch (Exception e) {
			System.out.println("Error: " + e.getStackTrace());
			registros = new ArrayList<Object>();
		}finally {
			session.close();
		}

		List<SemilleroLaboratorioVista> result = new ArrayList<SemilleroLaboratorioVista>();
		Iterator<?> it = registros.iterator();
		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			SemilleroLaboratorioVista semillero = new SemilleroLaboratorioVista();
			semillero.setId((String) name[0]);
			semillero.setNombre((String) name[1]);
			semillero.setLider((String) name[2]);
			semillero.setEstado((String) name[3]);
			semillero.setSede((String) name[4]);
			semillero.setFacultad((String) name[5]);
			semillero.setAreaOCDEPrincipal((String) name[6]);
			semillero.setAreasOCDESecundarias((String) name[7]);
			semillero.setEstudiantesPregrado((String) name[8]);
			semillero.setEstudiantesPosgrado((String) name[9]);
			semillero.setEstudiantesLideres((String) name[10]);
			semillero.setEstudiantesVisitantes((String) name[11]);
			semillero.setDocentes((String) name[12]);
			semillero.setEgresados((String) name[13]);
			semillero.setExternos((String) name[14]);
			result.add(semillero);
		}
		return result;
	}
	
	public List<String[]> consultaValorEjecutado(String idProyecto, String director) throws DataAccessException {
		List<?> documentos = new ArrayList<Object>();
		Session session = getSession();
		try {
			
			String query = "SELECT x.imputacion, sum(x.Obligaciones + x.Reservas) ValorEjecutado FROM (SELECT proyecto, imputacion, nvl(VR_OBLIGA_VIG_ACT, 0) Obligaciones, nvl(VR_OBLIGA_RESERVAS, 0) Reservas FROM ppto_proy_ACUM_invext_HIST@HERMES_INTEGRA WHERE vr_aprop_vigencia > 0 UNION ALL SELECT proyecto, imputacion, nvl(VR_OBLIGA_VIG_ACT, 0) Obligaciones, nvl(VR_OBLIGA_RESERVAS, 0) Reservas FROM ppto_proy_ACUM_invext_PROD@HERMES_INTEGRA WHERE vr_aprop_vigencia > 0 )x WHERE x.proyecto = (SELECT pry_codigo_quipu FROM her_proyecto WHERE pry_id = '"
					+ idProyecto + "') GROUP BY x.imputacion";
			SQLQuery sqlQuery1 = session.createSQLQuery(query);
			documentos = sqlQuery1.addScalar("imputacion", Hibernate.STRING)
					.addScalar("ValorEjecutado", Hibernate.STRING).list();
			

		} catch (Exception e) {
			System.out.println("Error: " + e.getStackTrace());
			documentos = new ArrayList<Object>();
		}finally {
			session.close();
		}

		List<String[]> result = new ArrayList<String[]>();
		Iterator<?> it = documentos.iterator();
		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			String[] record = new String[2];
			record[0] = name[0].toString();
			record[1] = name[1].toString();
			result.add(record);
		}
		return result;
	}
	
	public List<Bien> consultaEquiposPorPlacaYNombre(String placa) throws DataAccessException {
		Session session = getSession();
		List<?> documentos = new ArrayList<Object>();
		try {
			
			String select = "SELECT " + "placa, " + "serial, " + "ficha_tecnica, " + "componentes, " + "valor, "
					+ "ubicacion, " + "nombre_ubicacion, " + "responsable, " + "nombre_responsable, " + "estado, "
					+ "nombre_estado, " + "fecha_servicio, " + "fecha_adquisicion, " + "est_fisico, "
					+ "nombre_estado_fisico";
			String from = " FROM V_BIENES@HERMES_INTEGRA";
			String where = " WHERE placa = '" + placa + "' OR UPPER(ficha_tecnica) LIKE UPPER('%"+placa+"%')";
			String query = select + from + where;
			SQLQuery sqlQuery1 = session.createSQLQuery(query);
			documentos = sqlQuery1.addScalar("placa", Hibernate.STRING).addScalar("serial", Hibernate.STRING)
					.addScalar("ficha_tecnica", Hibernate.STRING).addScalar("componentes", Hibernate.STRING)
					.addScalar("valor", Hibernate.STRING).addScalar("ubicacion", Hibernate.STRING)
					.addScalar("nombre_ubicacion", Hibernate.STRING).addScalar("responsable", Hibernate.STRING)
					.addScalar("nombre_responsable", Hibernate.STRING).addScalar("estado", Hibernate.STRING)
					.addScalar("nombre_estado", Hibernate.STRING).addScalar("fecha_servicio", Hibernate.DATE)
					.addScalar("fecha_adquisicion", Hibernate.DATE).addScalar("est_fisico", Hibernate.STRING)
					.addScalar("nombre_estado_fisico", Hibernate.STRING).list();
			// }

			

		} catch (Exception e) {
			System.out.println("Error: " + e.getStackTrace());
			documentos = new ArrayList<Object>();
		}finally {
			session.close();
		}

		if (documentos.size() > 0) {
			System.out.println("Equipo encontrado.");
			// break;
		} else {
			System.out.println("Equipo NO encontrado.");
		}
		// }

		List<Bien> result = new ArrayList<Bien>();
		Iterator<?> it = documentos.iterator();
		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Bien bien = new Bien();
			String valor = name[4].toString();
			System.out.println("valor: " + valor);
			if (valor.contains(".")) {
				valor = valor.substring(0, valor.indexOf("."));
				System.out.println("valor sin punto: " + valor);
			}

			bien.setPlaca((String) name[0]);
			bien.setSerial((String) name[1]);
			bien.setDescripcion((String) name[2]);
			bien.setEquipo(buscarEnDescripcion((String) name[2], "DESCRIPCION :"));
			bien.setMarca(buscarEnDescripcion((String) name[2], "MARCA :"));
			bien.setModelo(buscarEnDescripcion((String) name[2], "MODELO :"));
			//bien.setValor(Integer.parseInt(valor));
			bien.setIdUbicacion((String) name[5]);
			bien.setUbicacion((String) name[6]);
			bien.setIdResponsable(Integer.parseInt(name[7].toString()));
			bien.setResponsable((String) name[8]);
			bien.setIdEstado(Integer.parseInt(name[9].toString()));
			bien.setEstado((String) name[10]);
			bien.setFechaServicio((Date) name[11]);
			bien.setFechaAdquisicion((Date) name[12]);
			bien.setIdEstadoFisico(Integer.parseInt(name[13].toString()));
			bien.setEstadoFisico((String) name[14]);
			result.add(bien);
		}
		return result;
	}

	public String buscarEnDescripcion(String descripcionBien, String queBusca) {
		
		if(esNulo(descripcionBien)) {
			return "Dato no disponible en QUIPU";
		}
		
		String descripcionBuscar = descripcionBien;
		int inicioBusca = descripcionBuscar.indexOf(queBusca);
		if (inicioBusca >= 0) {
			int finalBusca = descripcionBuscar.indexOf(";", inicioBusca);
			descripcionBuscar = descripcionBuscar.substring(inicioBusca + queBusca.length(), finalBusca);
			descripcionBuscar = descripcionBuscar.trim();
		} else
			descripcionBuscar = "";
		return descripcionBuscar;
	}

	public List<Bien> consultaEquipos(String placa) throws DataAccessException {
		return consultaEquipos(placa, Sede.NIVEL_NACIONAL);
	}
	
	/**
	 * Retorna los IDs de actividades que ya han sido reportadas en informes del semillero
	 * con el porcentaje especificado (por ejemplo, "100").
	 *
	 * @param idSemillero ID del semillero
	 * @param porcentaje Porcentaje de avance (ej. "100")
	 * @return Lista de IDs de actividades ya reportadas con ese porcentaje
	 */
	public List<Integer> obtenerIdsActividadesRegistradasPorSemilleroYPorcentaje(
	        String idSemillero, String porcentaje) throws DataAccessException {

	    Session session = getSession();
	    List<Integer> resultado = new ArrayList<Integer>();

	    String hql = "select distinct sia.actividad.id " +
	             "from SemilleroInformeActividad sia " +
	             "join sia.informe inf " + 
	             "where inf.semillero.id = :idSemillero and sia.porcentajeAvance = :porcentaje";


	    try {
	        Query query = session.createQuery(hql);
	        query.setParameter("idSemillero", idSemillero);
	        query.setParameter("porcentaje", porcentaje);

	        resultado = query.list();

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }

	    return resultado;
	}


	public long consultaRecursosConvocatoria(Long padre, Long modalidad) throws SQLException {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		String idSol = "0";
		try {

			st = session.connection().createStatement();

			String query = "SELECT COUNT(1) FROM her_distribucion_recursos  where cnp_id = " + padre + " and con_id = "
					+ modalidad;

			rs = st.executeQuery(query);
			while (rs.next()) {
				idSol = rs.getString(1);
			}
			rs.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
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
		Long id = new Long(idSol);

		return id;
	}

	public Object[] ejecutarQuerySql(String sql) throws SQLException {

		Session session = getSession();
		Statement statement = null;
		ResultSet resultSet = null;

		String[] header = null;
		List<Object[]> data = new LinkedList<Object[]>();

		try {

			statement = session.connection().createStatement();
			resultSet = statement.executeQuery(sql);

			if (resultSet != null) {

				// Header
				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
				int columCount = resultSetMetaData.getColumnCount();
				header = new String[columCount];
				for (int i = 0; i < columCount; i++) {
					header[i] = resultSetMetaData.getColumnName(i + 1);
				}

				// Data
				while (resultSet.next()) {
					Object[] rowData = new Object[columCount];
					for (int i = 0; i < columCount; i++) {
						Object value = resultSet.getObject(i + 1);
						rowData[i] = value;
					}
					data.add(rowData);
				}

			}

			resultSet.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
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

		Object[] objects = new Object[2];
		objects[0] = header;
		objects[1] = data;

		return objects;
	}

	public List<MovilidadArchivo> obtenerListaArchivosMovilidad(String idMovilidad) throws DataAccessException {
		String consultaAdicional = "";
		if(!esNulo(idMovilidad)) {
			if (idMovilidad.equals("SME1") || idMovilidad.equals("SME2")) {
				if (idMovilidad.equals("SME1")) {
					consultaAdicional = " and TAM.TAM_ID <> '30'";
				}
				if (idMovilidad.equals("SME2")) {
					consultaAdicional = " and TAM.TAM_ID <> '29'";
				}
				idMovilidad = "SME";
			}
		}
		List listaInformes = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();
		try {
		String query = "select MA.MAR_ID as id, MA.TAM_ID as tipoArchivo, MA.MOV_ID_TIPO as tipoMovilidad, MA.MAR_OBLIGATORIO as obligatorio, TAM_NOMBRE as tipoArchivoNombre, MA.MAR_SUBMODALIDAD AS submodalidad"
				+ " from HER_MOVILIDAD_ARCHIVO MA, HER_TIPO_ARCHIVO_MOVILIDAD TAM "
				+ " where TAM.TAM_ID = MA.TAM_ID AND MOV_ID_TIPO = '" + idMovilidad + "'" + consultaAdicional
				+ " order by MAR_ID ASC";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		listaInformes = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("tipoArchivo", Hibernate.LONG)
				.addScalar("tipoMovilidad", Hibernate.STRING).addScalar("obligatorio", Hibernate.LONG)
				.addScalar("tipoArchivoNombre", Hibernate.STRING).addScalar("submodalidad", Hibernate.STRING).list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = listaInformes.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();

			MovilidadArchivo mva = new MovilidadArchivo();

			TipoArchivoMovilidad tipoArchivo = new TipoArchivoMovilidad();
			tipoArchivo.setId((Long) name[1]);
			tipoArchivo.setNombre((String) name[4]);

			TipoMovilidad tipoMovilidad = new TipoMovilidad();

			tipoMovilidad.setId(idMovilidad);

			mva.setId((Long) name[0]);
			mva.setTipoArchivo(tipoArchivo);
			mva.setEsObligatorio((Long) name[3]);
			mva.setTipoMovilidad(tipoMovilidad);
			mva.setSubmodalidad((String) name[5]);

			result.add(mva);
		}
		return result;
	}

	public List obtenerListaArchivosMovilidad(String idMovilidad, Long idArchivo) throws DataAccessException {
		List listaInformes = new ArrayList();;
		List result = new ArrayList();

		Session session = getSession();
		try {
		String query = "select MAR_ID as id, TAM_ID as tipoArchivo, MOV_ID_TIPO as tipoMovilidad"
				+ " from HER_MOVILIDAD_ARCHIVO " + " where MOV_ID_TIPO = '" + idMovilidad + "'" + " and TAM_ID = "
				+ idArchivo + " order by MAR_ID ASC";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		listaInformes = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("tipoArchivo", Hibernate.LONG)
				.addScalar("tipoMovilidad", Hibernate.STRING).list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {

			session.close();
		}

		Iterator it = listaInformes.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();

			MovilidadArchivo mva = new MovilidadArchivo();

			List listaTipoArchivo = obtenerListaObjetos("TipoArchivoMovilidad where id ='" + name[1] + "'");
			;

			TipoArchivoMovilidad tipoArchivo = (TipoArchivoMovilidad) listaTipoArchivo.get(0);

			List listaTipoMovilidad = obtenerListaObjetos("TipoMovilidad where id ='" + (String) name[2] + "'");
			;

			TipoMovilidad tipoMovilidad = (TipoMovilidad) listaTipoMovilidad.get(0);

			mva.setId((Long) name[0]);
			mva.setTipoArchivo(tipoArchivo);

			mva.setTipoMovilidad(tipoMovilidad);

			result.add(mva);
		}
		return result;
	}

	// Ing. Wilver Alexander Martínez Martínez - wam²
	// Clase creada para recuperar el tamaño de la existencia de movilidades
	// de un investigador por año
	public int existeMovilidad(String sSql) throws SQLException {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		int tam = 0;

		try {

			st = session.connection().createStatement();
			String query = sSql;

			st.executeQuery(query);

			rs = st.executeQuery(query);
			rs.next();
			tam = rs.getInt(1);

		} catch (Exception ex) {
			System.out.println(ex.toString());
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
		return tam;
	}

	public List<Aval> consultaAvalesFacultadSede(Long sede) throws DataAccessException {
		List<?> documentos = null;
		List<Aval> result = new ArrayList<Aval>();

		Session session = getSession();
		try {
		String query = "  SELECT INI.AVI_ID as id, INI.AVI_TIPO as tipo, INI.AVI_ESTADO as estado,"
				+ " DPN1.DPN_NOMBRE as facultad, AVI_FECHAREGISTRO as fecha, AVI_TITULO as titulo, SED.SED_NOMBRE sede,  PER.PER_APELLIDO1 || ' ' || PER.PER_APELLIDO2 || ' ' || PER.PER_NOMBRE1 profesor, PER.PER_EMAIL mail, ce.cex_numero as nroConv, "
				+ " INI.DPN_ID as dependencia, INI.AVI_FECHA_CIERRE_CONV as aviFechaCierreConv, INI.AVI_CONVOCATORIA as aviConvocatoria FROM "
				+ " HER_AVAL INI left join her_convocatoria_externa ce on ce.cex_id = ini.avi_convocatoria, HER_DEPENDENCIA DPN, HER_DEPENDENCIA DPN1, HER_SEDE SED, HER_PERSONA PER "
				+ " WHERE " + " DPN.DPN_ID = INI.DPN_ID " + " AND  DPN.DPN_FACULTAD = DPN1.DPN_ID "
				+ " AND ini.AVI_TIPO NOT IN ('ECP','ES','C','JD','BI','AR') AND ini.AVI_ESTADO IN ('I','C','P') AND SED.SED_ID = DPN1.SED_ID AND PER.PER_ID = INI.INV_ID AND PER.TDO_ID = INI.TDO_ID AND PER.PER_ID != '19380666' AND SED.SED_ID = '"
				+ sede + "' ORDER BY DPN1.SED_ID, INI.AVI_ID DESC";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		documentos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("tipo", Hibernate.STRING)
				.addScalar("estado", Hibernate.STRING).addScalar("facultad", Hibernate.STRING)
				.addScalar("fecha", Hibernate.DATE).addScalar("titulo", Hibernate.STRING)
				.addScalar("sede", Hibernate.STRING).addScalar("profesor", Hibernate.STRING)
				.addScalar("mail", Hibernate.STRING).addScalar("nroConv", Hibernate.STRING)
				.addScalar("dependencia", Hibernate.STRING)
				// Requerimiento #2218, #2226 - Angela Devia
				.addScalar("aviConvocatoria", Hibernate.STRING).addScalar("AviFechaCierreConv", Hibernate.DATE).list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
		session.close();
		}

		Iterator<?> it = documentos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Aval aval = new Aval();

			aval.setAviId((Long) name[0]);
			aval.setTipo((String) name[1]);
			aval.setAviEstado((String) name[2]);
			aval.setNombreFacultad((String) name[3]);
			aval.setFecha((Date) name[4]);
			aval.setAviTitulo((String) name[5]);
			aval.setEvento((String) name[6]);
			aval.setNombreInvestigador((String) name[7]);
			aval.setAviCiudad((String) name[8]);
			aval.setAviNumeroConvocatoria((String) name[9]);
			Dependencia dep = new Dependencia((String) name[10]);
			aval.setDependencia(dep);
			// Requerimiento #2218, #2226 - Angela Devia
			aval.setAviFechaCierreConv((Date) name[4]);
			aval.setAviConvocatoria((String) name[5]);

			result.add(aval);
		}
		return result;
	}

	public List<Aval> consultaAvalesVice() throws DataAccessException {
		List<?> documentos = null;
		List<Aval> result = new ArrayList<Aval>();

		Session session = getSession();
		try {
		String query = "  SELECT INI.AVI_ID as id, INI.AVI_TIPO as tipo, INI.AVI_ESTADO as estado,"
				+ " DPN1.DPN_NOMBRE as facultad, AVI_FECHAREGISTRO as fecha, AVI_TITULO as titulo, SED.SED_NOMBRE sede,  PER.PER_APELLIDO1 || ' ' || PER.PER_APELLIDO2 || ' ' || PER.PER_NOMBRE1 profesor, PER.PER_EMAIL mail "
				+ " FROM " + " HER_AVAL INI, HER_DEPENDENCIA DPN, HER_DEPENDENCIA DPN1, HER_SEDE SED, HER_PERSONA PER "
				+ " WHERE " + " DPN.DPN_ID = INI.DPN_ID " + " AND  DPN.DPN_ID_2 = DPN1.DPN_ID "
				+ " AND ini.AVI_ESTADO IN ('I','C','P','F') AND ini.AVI_TIPO = 'SR' AND SED.SED_ID = DPN1.SED_ID AND PER.PER_ID = INI.INV_ID AND PER.TDO_ID = INI.TDO_ID AND PER.PER_ID != '19380666' ORDER BY DPN1.SED_ID, INI.AVI_ID DESC";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		documentos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("tipo", Hibernate.STRING)
				.addScalar("estado", Hibernate.STRING).addScalar("facultad", Hibernate.STRING)
				.addScalar("fecha", Hibernate.DATE).addScalar("titulo", Hibernate.STRING)
				.addScalar("sede", Hibernate.STRING).addScalar("profesor", Hibernate.STRING)
				.addScalar("mail", Hibernate.STRING).list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
		session.close();
		}
		
		Iterator<?> it = documentos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Aval aval = new Aval();

			aval.setAviId((Long) name[0]);
			aval.setTipo((String) name[1]);
			aval.setAviEstado((String) name[2]);
			aval.setNombreFacultad((String) name[3]);
			aval.setFecha((Date) name[4]);
			aval.setAviTitulo((String) name[5]);
			aval.setEvento((String) name[6]);
			aval.setNombreInvestigador((String) name[7]);
			aval.setAviCiudad((String) name[8]);

			result.add(aval);
		}
		return result;
	}
	
	public List<Aval> consultaAvalesDRE() throws DataAccessException {
		List<?> documentos = null;
		List<Aval> result = new ArrayList<Aval>();

		Session session = getSession();
		try {
		String query = "  SELECT INI.AVI_ID as id, INI.AVI_TIPO as tipo, INI.AVI_ESTADO as estado,"
				+ " DPN1.DPN_NOMBRE as facultad, AVI_FECHAREGISTRO as fecha, AVI_TITULO as titulo, SED.SED_NOMBRE sede,  PER.PER_APELLIDO1 || ' ' || PER.PER_APELLIDO2 || ' ' || PER.PER_NOMBRE1 profesor, PER.PER_EMAIL mail "
				+ " FROM " + " HER_AVAL INI, HER_CONVOCATORIA_EXTERNA CEX, HER_DEPENDENCIA DPN, HER_DEPENDENCIA DPN1, HER_SEDE SED, HER_PERSONA PER "
				+ " WHERE INI.AVI_CONVOCATORIA=to_char(CEX.CEX_ID) AND" + " DPN.DPN_ID = INI.DPN_ID " + " AND  DPN.DPN_ID_2 = DPN1.DPN_ID "
				+ " AND ini.AVI_ESTADO IN ('I','C','P','F','D') AND CEX.NAT_ID='376' AND SED.SED_ID = DPN1.SED_ID AND PER.PER_ID = INI.INV_ID AND PER.TDO_ID = INI.TDO_ID AND PER.PER_ID != '19380666' ORDER BY DPN1.SED_ID, INI.AVI_ID DESC";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		documentos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("tipo", Hibernate.STRING)
				.addScalar("estado", Hibernate.STRING).addScalar("facultad", Hibernate.STRING)
				.addScalar("fecha", Hibernate.DATE).addScalar("titulo", Hibernate.STRING)
				.addScalar("sede", Hibernate.STRING).addScalar("profesor", Hibernate.STRING)
				.addScalar("mail", Hibernate.STRING).list();
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator<?> it = documentos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Aval aval = new Aval();

			aval.setAviId((Long) name[0]);
			aval.setTipo((String) name[1]);
			aval.setAviEstado((String) name[2]);
			aval.setNombreFacultad((String) name[3]);
			aval.setFecha((Date) name[4]);
			aval.setAviTitulo((String) name[5]);
			aval.setEvento((String) name[6]);
			aval.setNombreInvestigador((String) name[7]);
			aval.setAviCiudad((String) name[8]);

			result.add(aval);
		}
		return result;
	}

	public long consultaUltimoSequenciaSolicitud(Long idTipoSolicitud, Long idProyecto, Long ano, Long mes, Long dia)
			throws SQLException {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		String idSol = "0";
		try {

			st = session.connection().createStatement();
			String query = "select SOL_ID FROM HER_SOLICITUD WHERE TSO_ID = " + idTipoSolicitud
					+ " AND SOL_RESPUESTA IS NULL" + " AND PRY_ID=" + idProyecto + " AND EXTRACT(YEAR FROM SOL_FECHA) ="
					+ ano + " AND EXTRACT(MONTH FROM SOL_FECHA)=" + mes + " AND EXTRACT(DAY FROM SOL_FECHA)=" + dia
					+ " ORDER BY SOL_FECHA DESC";

			rs = st.executeQuery(query);
			while (rs.next()) {
				idSol = rs.getString(1);
			}
			rs.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
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
		Long id = new Long(idSol);

		return id;
	}

	public long consultaTotalLaboratoriosActivos() throws SQLException {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		String idSol = "0";
		try {

			st = session.connection().createStatement();

			String query = "SELECT COUNT(*) FROM HER_LABORATORIO L WHERE L.LAB_ACTIVO=1 AND L.LAB_ID<>49";

			rs = st.executeQuery(query);
			while (rs.next()) {
				idSol = rs.getString(1);
			}
			rs.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
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
		Long id = new Long(idSol);

		return id;
	}

	public List<ArchivoInforme> obtenerListaArchivosInformes(Long idInforme) throws DataAccessException {
		List listaInformes = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();
		try {
		String query = "select AIN_ID as idArchivoInforme, " + " AIN_NOMBRE as nombre, AIN_ARCHIVO as archivo, "
				+ " AIN_DESCRIPCION as descripcion," + " AIN_FECHA as fecha, PIN_ID as idInforme "
				+ " from HER_ARCHIVO_INFORME " + " where PIN_ID = " + idInforme + " and (AIN_ESTADO is null or "
				+ " AIN_ESTADO <> 'B') order by PIN_ID ASC";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		listaInformes = sqlQuery1.addScalar("idArchivoInforme", Hibernate.LONG).addScalar("nombre", Hibernate.STRING)
				.addScalar("archivo", Hibernate.BLOB).addScalar("descripcion", Hibernate.STRING)
				.addScalar("fecha", Hibernate.DATE).addScalar("idInforme", Hibernate.LONG).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
		session.close();
		}

		Iterator it = listaInformes.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();

			ArchivoInforme ain = new ArchivoInforme();

			List listaProyectoInforme = obtenerListaObjetos("ProyectoInforme where id ='" + name[5] + "'");
			ProyectoInforme proyectoInforme = (ProyectoInforme) listaProyectoInforme.get(0);

			ain.setId((Long) name[0]);
			ain.setNombre((String) name[1]);
			ain.setArchivo((Blob) name[2]);
			ain.setDescripcion((String) name[3]);
			ain.setFecha((Date) name[4]);
			ain.setInforme(proyectoInforme);

			result.add(ain);
		}
		return result;
	}

	public List<ProyectoInforme> obtenerListaSolicitudesRenovacion(Long idProyecto, String idPersona,
			String tipoDocumentoPersona) throws DataAccessException {
		List listaInformes = new ArrayList();
		List<ProyectoInforme> result = new ArrayList();

		Session session = getSession();
		try {
		String query = "select PIN_ID as idInforme, PRY_ID as proyecto, TIN_ID as tipoInforme, "
				+ "EIN_ID as estadoInforme, PIN_FECHA as fechaInforme, PIN_AVANCE as avances, "
				+ "PIN_RESULTADOS as resultados, "
				+ "PIN_MOTIVO_NO_APROBACION as noAprobacion, PIN_DEP_AVAL_FAC as dependenciaAvalFacultad"
				+ " from HER_PROYECTO_INFORME " + " where EIN_ID <> '" + ProyectoInforme.ESTADO_BORRADO
				+ "' AND PRY_ID = " + idProyecto + " and PIN_ACTO_ADM_FAC = '" + idPersona
				+ "' and PIN_TIENE_SALDO_EJEC = '" + tipoDocumentoPersona + "' order by PIN_ID ASC";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		listaInformes = sqlQuery1.addScalar("idInforme", Hibernate.LONG).addScalar("proyecto", Hibernate.LONG)
				.addScalar("tipoInforme", Hibernate.LONG).addScalar("estadoInforme", Hibernate.LONG)
				.addScalar("fechaInforme", Hibernate.DATE).addScalar("avances", Hibernate.STRING)
				.addScalar("resultados", Hibernate.STRING).addScalar("noAprobacion", Hibernate.STRING)
				.addScalar("dependenciaAvalFacultad", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = listaInformes.iterator();
		List listaEstadoInforme;

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoInforme pin = new ProyectoInforme();

			TipoInforme tip = new TipoInforme();

			listaEstadoInforme = obtenerListaObjetos("EstadoInforme where id ='" + name[3] + "'");
			EstadoInforme ein = (EstadoInforme) listaEstadoInforme.get(0);

			pin.setId((Long) name[0]);
			Proyecto proyecto = new Proyecto();
			proyecto.setId(idProyecto);
			pin.setProyecto(proyecto);
			tip.setId((Long) name[2]);
			pin.setTipoInforme(tip);
			ein.setId((Long) name[3]);
			pin.setEstadoInforme(ein);
			pin.setFechaGeneracion((Date) name[4]);
			pin.setAvanceResultados((String) name[5]);
			pin.setCuadroResultados((Blob) name[6]);
			pin.setNoAprobacion((String) name[7]);
			pin.setDependenciaAvalFacultad((String) name[8]);

			result.add(pin);
		}
		return result;
	}

	public List<ProyectoInforme> obtenerListaInformes(Long idProyecto) throws DataAccessException {
		List listaInformes = new ArrayList();
		List<ProyectoInforme> result = new ArrayList<ProyectoInforme>();

		Session session = getSession();
		try {
		String query = "select PIN_ID as idInforme, PRY_ID as proyecto, TIN_ID as tipoInforme, EIN_ID as estadoInforme, PIN_FECHA as fechaInforme, PIN_AVANCE as avances, PIN_RESULTADOS as resultados, "
				+ "PIN_DIFICULTADES as dificultades, PIN_CUADRO_RESULTADOS as cuadro, PIN_INFORME_FINANCIERO as financiero, PIN_CUADRO_NOMBRE as nombreCuadro, PIN_NOMBRE_FINANCIERO as nombreFinan, PIN_MOTIVO_NO_APROBACION as noAprobacion,"
				+ "per_nombre1 || ' ' || per_nombre2 || ' ' || per_apellido1 || ' ' || per_apellido2 as nombreCompleto, PIN_MOTIVO_NO_LECTURA as motivoNoLectura, PIN_EST_ID as idEstudiante"
				+ " from HER_PROYECTO_INFORME " + " left join HER_PERSONA on per_id = PIN_EST_ID "
				+ " and HER_PERSONA.tdo_id = HER_PROYECTO_INFORME.PIN_EST_TDO_ID" + " where PRY_ID = " + idProyecto
				+ " and HER_PROYECTO_INFORME.EIN_ID <> '" + ProyectoInforme.ESTADO_BORRADO + "' order by PIN_ID ASC";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		listaInformes = sqlQuery1.addScalar("idInforme", Hibernate.LONG).addScalar("proyecto", Hibernate.LONG)
				.addScalar("tipoInforme", Hibernate.LONG).addScalar("estadoInforme", Hibernate.LONG)
				.addScalar("fechaInforme", Hibernate.DATE).addScalar("avances", Hibernate.STRING)
				.addScalar("resultados", Hibernate.STRING).addScalar("dificultades", Hibernate.STRING)
				.addScalar("cuadro", Hibernate.BLOB).addScalar("financiero", Hibernate.BLOB)
				.addScalar("nombreCuadro", Hibernate.STRING).addScalar("nombreFinan", Hibernate.STRING)
				.addScalar("noAprobacion", Hibernate.STRING).addScalar("nombreCompleto", Hibernate.STRING)
				.addScalar("motivoNoLectura", Hibernate.STRING).addScalar("idEstudiante", Hibernate.STRING).list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = listaInformes.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoInforme pin = new ProyectoInforme();
			List listaEstadoInforme;
			List listaProyectoActual;
			listaProyectoActual = obtenerListaObjetos("Proyecto where id ='" + name[1] + "'");
			Proyecto proyectoActual = (Proyecto) listaProyectoActual.get(0);

			List<TipoInforme> listaTipoInforme = obtenerListaObjetos("TipoInforme where id ='" + name[2] + "'");
			TipoInforme tip = (TipoInforme) listaTipoInforme.get(0);

			listaEstadoInforme = obtenerListaObjetos("EstadoInforme where id ='" + name[3] + "'");
			EstadoInforme ein = (EstadoInforme) listaEstadoInforme.get(0);

			pin.setId((Long) name[0]);
			pin.setProyecto(proyectoActual);
			pin.setTipoInforme(tip);
			pin.setEstadoInforme(ein);
			pin.setFechaGeneracion((Date) name[4]);
			pin.setAvanceResumen((String) name[5]);
			pin.setAvanceResultados((String) name[6]);
			pin.setDificultades((String) name[7]);
			pin.setCuadroResultados((Blob) name[8]);
			pin.setInformeFinanciero((Blob) name[9]);
			pin.setCuadroNombre((String) name[10]);
			pin.setFinancieroNombre((String) name[11]);
			pin.setNoAprobacion((String) name[12]);
			pin.setNombreEstudiante((String) name[13]);
			pin.setNoLectura((String) name[14]);
			if (name[15] != null) {
				pin.setIdEstudiante((String) name[15]);
			}
			result.add(pin);
		}
		return result;
	}

	public List obtenerCorreoBoletin(int tipo, String sede) throws DataAccessException {
		List correosBoletin = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();

		String query = "";

		if (sede.equals("1")) {
			if (tipo == 0) {
				query = "select PER_ID as idPersona, PER_EMAIL as emailPersona "
						+ "from HER_CORREO_PROFESORES order by PER_ID ASC";
			}

			if (tipo == 1) {
				query = "select PER_ID as idPersona, PER_EMAIL as emailPersona "
						+ "from HER_CORREO_POSGRADO order by PER_ID ASC";
			}

			if (tipo == 2) {
				query = "select PER_ID as idPersona, PER_EMAIL as emailPersona "
						+ "from HER_CORREO_ADMINISTRATIVOS order by PER_ID ASC";
			}

			if (tipo == 3) {
				query = "select PER_ID as idPersona, PER_EMAIL as emailPersona "
						+ "from HER_CORREO_PREGRADO order by PER_ID ASC";
			}

			if (tipo == 4) {
				query = "select PER_ID as idPersona, PER_EMAIL as emailPersona "
						+ "from HER_CORREO_OTROS order by PER_ID ASC";
			}
		} else {
			if (tipo == 0) {
				query = "select PER_ID as idPersona, PER_EMAIL as emailPersona "
						+ "from HER_CORREO_PROFESORES WHERE PER_SEDE  = '" + sede + "'" + " order by PER_ID ASC";
			}

			if (tipo == 1) {
				query = "select PER_ID as idPersona, PER_EMAIL as emailPersona "
						+ "from HER_CORREO_POSGRADO WHERE PER_SEDE  = '" + sede + "'" + " order by PER_ID ASC";
			}

			if (tipo == 2) {
				query = "select PER_ID as idPersona, PER_EMAIL as emailPersona "
						+ "from HER_CORREO_ADMINISTRATIVOS WHERE PER_SEDE  = '" + sede + "'" + " order by PER_ID ASC";
			}

			if (tipo == 3) {
				query = "select PER_ID as idPersona, PER_EMAIL as emailPersona "
						+ "from HER_CORREO_PREGRADO WHERE PER_SEDE  = '" + sede + "'" + " order by PER_ID ASC";
			}

			if (tipo == 4) {
				query = "select PER_ID as idPersona, PER_EMAIL as emailPersona "
						+ "from HER_CORREO_OTROS WHERE PER_SEDE  = '" + sede + "'" + " order by PER_ID ASC";
			}
		}
		try {
		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		correosBoletin = sqlQuery1.addScalar("idPersona", Hibernate.LONG).addScalar("emailPersona", Hibernate.STRING)
				.list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = correosBoletin.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			CorreoPersonaBoletin correoPersonaBoletin = new CorreoPersonaBoletin();

			correoPersonaBoletin.setId((Long) name[0]);
			correoPersonaBoletin.setCorreoPersona((String) name[1]);

			result.add(correoPersonaBoletin);
		}
		return result;
	}

	public String envioCorreoBoletin() throws SQLException {

		Connection c = DriverManager.getConnection("jdbc:oracle:thin:@unbdpp.unal.edu.co:1521:bdpP02", "hermes",
				"1nv35T1g4C1oNsl3l");
		CallableStatement cs = null;

		try {
			cs = c.prepareCall("{call cont_test}");

			c.commit();

			cs.execute();
			cs.cancel();
			cs.close();
			c.close();
			return "El boletín se ha enviado con éxito";

		} catch (SQLException e) {
			cs.close();
			e.printStackTrace();
			System.out.println(e.toString());
			return "El boletín no se ha enviado con éxito";

		}

	}

	public List obtenerCodigoSolicitud(Long idProyecto, String idTipoSol, String solicitudesExistentes)
			throws DataAccessException {
		List solicitudes = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();
		try {
		String sqlAdicional = "";
		if (solicitudesExistentes != null && solicitudesExistentes.length() > 0) {
			sqlAdicional = "AND sol_id not in (" + solicitudesExistentes + ") ";
		}

		String query = "select SOL_ID as idSolicitud,PRY_ID as proyecto,SOL_FECHA as fecha " + "from HER_SOLICITUD "
				+ "where PRY_ID = '" + idProyecto + "' and TSO_ID in (" + idTipoSol + ") and SOL_RESPUESTA ='A' "
				+ sqlAdicional + "order by SOL_FECHA";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		solicitudes = sqlQuery1.addScalar("idSolicitud", Hibernate.LONG).addScalar("proyecto", Hibernate.LONG)
				.addScalar("fecha", Hibernate.DATE).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = solicitudes.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Solicitud sol = new Solicitud();

			List listaProyecto = obtenerListaObjetos("Proyecto where id ='" + name[1] + "'");
			Proyecto proyectoActual = (Proyecto) listaProyecto.get(0);

			sol.setId((Long) name[0]);
			sol.setProyecto(proyectoActual);
			sol.setFecha((Date) name[2]);

			result.add(sol);
		}
		return result;
	}

	// wam²
	// Clase creada para la eliminación de registros
	public void eliminar(String sSql) throws SQLException {
		Session session = getSession();
		Statement st = null;
		Transaction tr = session.beginTransaction();

		try {

			st = session.connection().createStatement();
			String query = sSql;
			st.getConnection().setAutoCommit(true);
			st.executeQuery(query);
			session.connection().commit();
			st.getConnection().setAutoCommit(true);

			tr.commit();
			session.close();

		} catch (Exception ex) {
			tr.rollback();
			System.out.println(ex.toString());
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
	}

	public String consultaDescripcionSolicitud(Long idProyecto) throws SQLException {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		String descripcion = "";
		try {

			st = session.connection().createStatement();
			String query = "select SOL_DESCRIPCION FROM HER_SOLICITUD WHERE TSO_ID = '1' AND SOL_RESPUESTA IS NULL AND PRY_ID="
					+ idProyecto;

			rs = st.executeQuery(query);
			while (rs.next()) {
				descripcion = rs.getString(1);
			}

			rs.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
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
		return descripcion;
	}

	public Long consultaIdCarta(Long idProyecto, Long idTipoCarta, String sede, String año) throws SQLException {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		String idSol = "0";
		try {
			st = session.connection().createStatement();
			String query = "select PCA_SEQ FROM HER_PROYECTO_CARTA  WHERE ETC_ID in ('G','SE') AND TPC_ID =" + idTipoCarta
					+ "  AND PRY_ID=" + idProyecto + "  AND PCA_SEDE=" + sede;

			rs = st.executeQuery(query);
			if (rs.next()) {
				String result = rs.getString(1);
				if (result != null && !result.equalsIgnoreCase("null") && !result.trim().isEmpty()) {
		            idSol = result;
		        } else {
		            idSol = "0";  // or set to null depending on your logic
		        }
			}else {
				idSol = "0";
			}
			rs.close();

		} catch (Exception ex) {
			
			System.out.println(ex.toString());
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
		Long id = new Long(idSol);

		return id;
	}

	public Long consultaUltimoIdCarta(String sede, Date fecha) throws SQLException {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		String idSol = "0";
		Date fechaSesion = new Date();
		String fechaS = "";
		try {
			st = session.connection().createStatement();
			fechaSesion = fecha;

			if (fechaSesion != null) {

				SimpleDateFormat spy = new SimpleDateFormat("yyyy");
				fechaS = spy.format(fechaSesion);

			}

			String query = "select to_number(PCA_SEQ) FROM HER_PROYECTO_CARTA WHERE  PCA_AÑO = '" + fechaS
					+ "'  AND  PCA_SEDE = '" + sede + "'  AND PCA_SEQ <> 'null' ORDER BY to_number(PCA_SEQ) DESC ";

			rs = st.executeQuery(query);

			if (rs.next()) {
				idSol = rs.getString(1);
			}
			rs.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
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

		int id = Integer.parseInt(idSol);

		id = id + 1;

		Long idRetorna = new Long(id);
		return idRetorna;
	}
	
	public Long consultaUltimoIdPorDependenciaTipoCarta(String sede, Date fecha, String tipoCarta) throws SQLException {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		String idSol = "0";
		Date fechaSesion = new Date();
		String fechaS = "";
		try {
			st = session.connection().createStatement();
			fechaSesion = fecha;

			if (fechaSesion != null) {

				SimpleDateFormat spy = new SimpleDateFormat("yyyy");
				fechaS = spy.format(fechaSesion);

			}

			String query = "select to_number(PCA_SEQ) FROM HER_PROYECTO_CARTA WHERE to_number(PCA_AÑO) > 2023  AND  PCA_SEDE = '" + sede + "' and TPC_ID = '"+tipoCarta+"' ORDER BY to_number(PCA_SEQ) DESC ";

			rs = st.executeQuery(query);

			if (rs.next()) {
				idSol = rs.getString(1);
			}
			rs.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
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

		int id = Integer.parseInt(idSol);

		id = id + 1;

		Long idRetorna = new Long(id);
		return idRetorna;
	}

	public Long consultaIdSolicitud(Long idProyecto) throws SQLException {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		String idSol = "0";
		try {

			st = session.connection().createStatement();
			String query = "select SOL_ID FROM HER_SOLICITUD WHERE TSO_ID = '1' AND SOL_RESPUESTA IS NULL AND PRY_ID="
					+ idProyecto;

			rs = st.executeQuery(query);
			while (rs.next()) {
				idSol = rs.getString(1);
			}
			rs.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
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
		Long id = new Long(idSol);

		return id;
	}

	public String dependenciaPadre(String idDependencia) throws SQLException {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		String id = "";
		try {

			st = session.connection().createStatement();
			String query = "select dep.dpn_id_2 from her_dependencia dep where dep.dpn_id= '" + idDependencia + "'";

			rs = st.executeQuery(query);
			while (rs.next()) {
				id = rs.getString(1);
			}
			rs.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
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
		return id;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Aval> obtenerAvales(String dependencia) throws DataAccessException {

		List AvalesInv = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();
		try {
		String query = "select "
				+ "AVI_ID as idAval, "
				+ "decode(CE.CEX_NUMERO,NULL,AV.AVI_NUM_CONV,CE.CEX_NUMERO) as numConvoca, "
				+ "AVI_TIPO as tipoAval, "
				+ "av.INV_ID as idInvestigador, "
				+ "av.TDO_ID as tipoDoc, "
				+ "AVI_FECHAREGISTRO as fechaSolicitud, "
				+ "AVI_ULTIMO_ENVIO_SOLICIT as ultimoEnvio, "
				+ "PER.PER_NOMBRE1||' '||DECODE(PER.PER_NOMBRE2, PER.PER_NOMBRE2,PER.PER_NOMBRE2,NULL,' ')||' '||PER.PER_APELLIDO1||' '||DECODE(PER.PER_APELLIDO2, PER.PER_APELLIDO2,PER.PER_APELLIDO2,NULL,' ')InvP, "
				+ "natConv.nombre as naturalezaConvocatoria "
				+ "FROM "
				+ "HER_AVAL av "
				+ "LEFT JOIN HER_CONVOCATORIA_EXTERNA ce on TO_CHAR(ce.CEX_ID) = AV.AVI_CONVOCATORIA "
				+ "LEFT JOIN HER_TIPOS natConv ON natConv.ID = ce.NAT_ID, "
				+ "HER_DEPENDENCIA dep, "
				+ "HER_DOMINIO_DETALLE dd, "
				+ "HER_PERSONA per "
				+ "WHERE av.DPN_ID = dep.DPN_ID and dep.DPN_FACULTAD = '" + dependencia
				+ "' and dd.DOM_ID = '81' and av.AVI_TIPO = dd.DOMDET_TIPO and "
				+ "((dd.DOMDET_ESTADO like 'FAC-%' or dd.DOMDET_ESTADO = 'FAC') and (av.AVI_ESTADO = 'I' or av.AVI_ESTADO = 'IF') or (dd.DOMDET_ESTADO like '%-FAC' and av.AVI_ESTADO = 'U' and av.AVI_AVALUAB = 'S' and av.AVI_AVALFACULTAD is null)) "
				+ "and (av.AVI_DPN_CONTRAPARTIDA <> 'D' or av.AVI_DPN_CONTRAPARTIDA is null) and av.inv_id = per.per_id and av.tdo_id = per.tdo_id"
				+ " order by AVI_ID desc";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		AvalesInv = sqlQuery1
				.addScalar("idAval", Hibernate.LONG)
				.addScalar("numConvoca", Hibernate.STRING)
				.addScalar("tipoAval", Hibernate.STRING)
				.addScalar("idInvestigador", Hibernate.STRING)
				.addScalar("tipoDoc", Hibernate.STRING)
				.addScalar("fechaSolicitud", Hibernate.DATE)
				.addScalar("ultimoEnvio", Hibernate.DATE)
				.addScalar("InvP", Hibernate.STRING)
				.addScalar("naturalezaConvocatoria", Hibernate.STRING)
				.list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = AvalesInv.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Aval av = new Aval();

			av.setAviId((Long) name[0]);
			av.setAviNumeroConvocatoria((String) name[1]);
			av.setTipo((String) name[2]);
			av.setDocumento((String) name[3]);
			av.setTipoDocumento((String) name[4]);
			av.setFecha((Date) name[5]);
			av.setFechaUltimoEnvio((Date) name[6]);
			av.setNombreInvestigador((String) name[7]);
			av.setAviNaturalezaConvocatoria((String) name[8]);

			result.add(av);
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ProyectoInforme> obtenerAvalInformeFacultad(String dependencia) throws DataAccessException {

		List informes = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();
		try {
		String query = "select pi.PIN_ID as idInforme, pi.PRY_ID as idProyecto, TI.tin_id as idTipoInfo, ti.TIN_NOMBRE as nombreTipoInfo, pi.PIN_FECHA as fecha,"
				+ " PER.PER_NOMBRE1||' '||DECODE(PER.PER_NOMBRE2, PER.PER_NOMBRE2,PER.PER_NOMBRE2,NULL,' ')||' '||PER.PER_APELLIDO1||' '||DECODE(PER.PER_APELLIDO2, PER.PER_APELLIDO2,PER.PER_APELLIDO2,NULL,' ')InvP"
				+ " from HER_PROYECTO_INFORME pi left join HER_PERSONA per on pi.PIN_RESP_ENVIO_ID = per.per_id and pi.PIN_RESP_ENVIO_TDO_ID = per.tdo_id, HER_TIPO_INFORME ti, HER_PROYECTO p"
				+ " left join her_convocatoria c on p.mod_id = c.con_id left join her_convocatoria_padre cp on c.cnp_id = cp.cnp_id,"
				+ " HER_DEPENDENCIA d " + " where  pi.DPN_ID = d.dpn_id and d.dpn_facultad = '" + dependencia
				+ "' and pi.TIN_ID IN ('2') and pi.TIN_ID = ti.TIN_ID and "
				+ " pi.EIN_ID = '2' and pi.PRY_ID = p.PRY_ID AND p.MOD_ID not in ('150','652','21','22') and (cp.CNP_INFORME_COORDINADOR = 'N' or cp.CNP_INFORME_COORDINADOR is null) and p.pry_id not in (select avl.pry_id from her_aval avl where avl.avi_tipo = 'JD' and avl.avi_avaluab is null and avl.avi_estado != 'B')"
				+ " order by pi.PIN_FECHA desc";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		informes = sqlQuery1.addScalar("idInforme", Hibernate.LONG).addScalar("idProyecto", Hibernate.LONG)
				.addScalar("idTipoInfo", Hibernate.LONG).addScalar("nombreTipoInfo", Hibernate.STRING)
				.addScalar("InvP", Hibernate.STRING).addScalar("fecha", Hibernate.DATE).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = informes.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoInforme pi = new ProyectoInforme();
			Proyecto p = new Proyecto();
			TipoInforme ti = new TipoInforme();

			pi.setId((Long) name[0]);
			p.setId((Long) name[1]);
			p.setResumen((String) name[4]);
			pi.setProyecto(p);
			ti.setId((Long) name[2]);
			ti.setNombre((String) name[3]);
			pi.setTipoInforme(ti);
			pi.setFechaGeneracion((Date) name[5]);

			result.add(pi);
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ProyectoInforme> obtenerAvalInformeUab(String dependencia) throws DataAccessException {

		List informes = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();
		try {
		String query = "select distinct pi.PIN_ID as idInforme, pi.PRY_ID as idProyecto, TI.tin_id as idTipoInfo, ti.TIN_NOMBRE as nombreTipoInfo, pi.PIN_FECHA as fecha, "
				+ " PER.PER_NOMBRE1||' '||DECODE(PER.PER_NOMBRE2, PER.PER_NOMBRE2,PER.PER_NOMBRE2,NULL,' ')||' '||PER.PER_APELLIDO1||' '||DECODE(PER.PER_APELLIDO2, PER.PER_APELLIDO2,PER.PER_APELLIDO2,NULL,' ')InvP"
				+ " from HER_PROYECTO_INFORME pi left join HER_PERSONA per on pi.PIN_RESP_ENVIO_ID = per.per_id and pi.PIN_RESP_ENVIO_TDO_ID = per.tdo_id, HER_TIPO_INFORME ti, HER_PROYECTO p, her_aval avl "
				+ " where  pi.DPN_ID = '" + dependencia + "' and pi.TIN_ID IN ('1','2') and pi.TIN_ID = ti.TIN_ID and "
				+ " pi.EIN_ID = '2' and pi.PRY_ID = p.PRY_ID AND p.PRY_ES_JORN_DOCENTE = 'Y' and p.pry_id = avl.pry_id and avl.avi_avaluab is null and avl.avi_estado != 'B'"
				+ " order by pi.PIN_ID desc";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		informes = sqlQuery1.addScalar("idInforme", Hibernate.LONG).addScalar("idProyecto", Hibernate.LONG)
				.addScalar("idTipoInfo", Hibernate.LONG).addScalar("nombreTipoInfo", Hibernate.STRING)
				.addScalar("InvP", Hibernate.STRING).addScalar("fecha", Hibernate.DATE).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = informes.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			ProyectoInforme pi = new ProyectoInforme();
			Proyecto p = new Proyecto();
			TipoInforme ti = new TipoInforme();

			pi.setId((Long) name[0]);
			p.setId((Long) name[1]);
			p.setResumen((String) name[4]);
			pi.setProyecto(p);
			ti.setId((Long) name[2]);
			ti.setNombre((String) name[3]);
			pi.setTipoInforme(ti);
			pi.setFechaGeneracion((Date) name[5]);

			result.add(pi);
		}
		return result;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Aval> obtenerAvalesDireccion(String dependencia) throws DataAccessException {

		List AvalesInv = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();
		try {
		String query = "select "
				+ "AVI_ID as idAval, "
				+ "decode(CE.CEX_NUMERO,NULL,AV.AVI_NUM_CONV,CE.CEX_NUMERO) as numConvoca, "
				+ "AVI_TIPO as tipoAval, "
				+ "INV_ID as idInvestigador, "
				+ "TDO_ID as tipoDoc, "
				+ "AVI_FECHAREGISTRO as fechaSolicitud, "
				+ "AVI_FECHA_AVAL_FAC as fechaAvalFac, "
				+ "AVI_ULTIMO_ENVIO_SOLICIT as ultimoEnvio, "
				+ "d2.dpn_id as idFac, "
				+ "d2.dpn_nombre as facultad, "
				+ "natConv.nombre as naturalezaConvocatoria "
				+ "FROM "
				+ "HER_AVAL av "
				+ "LEFT JOIN HER_CONVOCATORIA_EXTERNA ce on TO_CHAR(ce.CEX_ID) = AV.AVI_CONVOCATORIA "
				+ "LEFT JOIN HER_TIPOS natConv ON natConv.ID = ce.NAT_ID, "
				+ "HER_DEPENDENCIA dep, "
				+ "HER_DOMINIO_DETALLE dd, "
				+ "her_dependencia d2 "
				+ "WHERE av.DPN_ID = dep.DPN_ID and dep.SED_ID = '" + dependencia
				+ "' and dd.DOM_ID = '81' and av.AVI_TIPO = dd.DOMDET_TIPO and "
				+ "(((dd.DOMDET_ESTADO like 'DI-%' or dd.DOMDET_ESTADO like '%-DI' or dd.DOMDET_ESTADO = 'DI')"
				+ " and (av.AVI_ESTADO = 'F' or av.AVI_ESTADO = 'IS') and av.AVI_AVALFACULTAD = 'S' and (av.AVI_DPN_CONTRAPARTIDA <> 'F' or av.AVI_DPN_CONTRAPARTIDA is null) "
				+ "and d2.dpn_id = dep.dpn_facultad) or (av.avi_tipo = 'JD' and dep.SED_ID in ('6','7','8','9') and av.avi_estado = 'U' and av.avi_avaluab = 'S' and av.avi_avaldireccion is null and (d2.dpn_id = dep.dpn_facultad or dep.dpn_facultad is null))) "
				+ "order by AVI_ID desc";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		AvalesInv = sqlQuery1.addScalar("idAval", Hibernate.LONG)
				.addScalar("numConvoca", Hibernate.STRING)
				.addScalar("tipoAval", Hibernate.STRING)
				.addScalar("idInvestigador", Hibernate.STRING)
				.addScalar("tipoDoc", Hibernate.STRING)
				.addScalar("fechaSolicitud", Hibernate.DATE)
				.addScalar("fechaAvalFac", Hibernate.DATE)
				.addScalar("ultimoEnvio", Hibernate.DATE)
				.addScalar("idFac", Hibernate.STRING)
				.addScalar("facultad", Hibernate.STRING)
				.addScalar("naturalezaConvocatoria", Hibernate.STRING)
				.list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = AvalesInv.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Aval av = new Aval();

			av.setAviId((Long) name[0]);
			av.setAviNumeroConvocatoria((String) name[1]);
			av.setTipo((String) name[2]);
			av.setDocumento((String) name[3]);
			av.setTipoDocumento((String) name[4]);
			av.setFecha((Date) name[5]);
			av.setAviFechaAvalFac((Date) name[6]);
			av.setFechaUltimoEnvio((Date) name[7]);
			Dependencia dep = new Dependencia((String) name[8]);
			av.setDependencia(dep);
			dep.setNombre((String) name[9]);
			av.setAviNaturalezaConvocatoria((String) name[10]);
			
			result.add(av);
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Aval> obtenerAvalesVice() throws DataAccessException {

		Session session = null;
		List AvalesInv;
		List result = new ArrayList();
		try {
			session = getSession();
			
			String query = "select "
					+ "AVI_ID as idAval, "
					+ "decode(CE.CEX_NUMERO,NULL,AV.AVI_NUM_CONV,CE.CEX_NUMERO) as numConvoca, "
					+ "AVI_TIPO as tipoAval, "
					+ "INV_ID as idInvestigador, "
					+ "TDO_ID as tipoDoc, "
					+ "AVI_FECHAREGISTRO as fechaSolicitud, "
					+ "AVI_FECHA_AVAL_FAC as fechaAvalFac, "
					+ "AVI_FECHA_AVAL_COOR as fechaAvalCoor, "
					+ "AVI_ULTIMO_ENVIO_SOLICIT as ultimoEnvio, "
					+ "natConv.nombre as naturalezaConvocatoria, "
					+ "av.DPN_ID as dependenciaAval "
					+ "FROM "
					+ "HER_AVAL av "
					+ "LEFT JOIN HER_CONVOCATORIA_EXTERNA ce on TO_CHAR(ce.CEX_ID) = AV.AVI_CONVOCATORIA "
					+ "LEFT JOIN HER_TIPOS natConv ON natConv.ID = ce.NAT_ID "
					+ "LEFT JOIN HER_DEPENDENCIA dep ON av.DPN_ID = dep.DPN_ID "
					+ "WHERE "
					+ "av.AVI_ESTADO = 'D' "
					+ "and av.AVI_AVALDIRECCION = 'S' "
					+ "and ((av.AVI_TIPO in ('SR','PSR','PINV') and av.AVI_INTERSEDES = 'S') or av.AVI_ES_PARA_VICE = 'S') "
					+ "order by AVI_ID desc";
			
			SQLQuery sqlQuery1 = session.createSQLQuery(query);

			AvalesInv = sqlQuery1.addScalar("idAval", Hibernate.LONG)
					.addScalar("numConvoca", Hibernate.STRING)
					.addScalar("tipoAval", Hibernate.STRING)
					.addScalar("idInvestigador", Hibernate.STRING)
					.addScalar("tipoDoc", Hibernate.STRING)
					.addScalar("fechaSolicitud", Hibernate.DATE)
					.addScalar("fechaAvalFac", Hibernate.DATE)
					.addScalar("fechaAvalCoor", Hibernate.DATE)
					.addScalar("ultimoEnvio", Hibernate.DATE)
					.addScalar("naturalezaConvocatoria", Hibernate.STRING)
					.addScalar("dependenciaAval", Hibernate.STRING)
					.list();

			session.close();

			Iterator it = AvalesInv.iterator();

			while (it.hasNext()) {
				Object[] name = (Object[]) it.next();
				Aval av = new Aval();

				av.setAviId((Long) name[0]);
				av.setAviNumeroConvocatoria((String) name[1]);
				av.setTipo((String) name[2]);
				av.setDocumento((String) name[3]);
				av.setTipoDocumento((String) name[4]);
				av.setFecha((Date) name[5]);
				av.setAviFechaAvalFac((Date) name[6]);
				av.setAviFechaAvalCoor((Date) name[7]);
				av.setFechaUltimoEnvio((Date) name[8]);
				av.setAviNaturalezaConvocatoria((String) name[9]);
				Dependencia dep = (Dependencia) obtenerObjeto(new Dependencia(), (String) name[10]);
				av.setDependencia(dep);
				
				result.add(av);
			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
			session = null;
		}
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Aval> obtenerAvalesDRE() throws DataAccessException {
		Session session = null;
		Query q = null;
		List resultado = null;
		
		try {
			session = getSession();
			q = session.createQuery(
					"select av from Aval av" + " where "
							+ " av.esAvalParaRevisionDRE = 'S' and ((av.esAvalParaRevisionVice='N' and av.aviAvaldireccion = 'S') or (av.esAvalParaRevisionVice='S' and av.avalVice = 'S')) and av.avalDRE is null and av.aviEstado != 'B' order by av.aviId desc");
			resultado = q.list();
			session.close();
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Aval> obtenerAvalesCEPI(String dependenciaRev) throws DataAccessException {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();
			q = session.createQuery(
					"select av from Aval av"
				  + " where "
				  + " av.tipo = 'ETICO'"
				  + " and (av.aviEstado = 'EP' or (av.aviEstado = 'NP' and av.avalTieneRecurso = 'SI' and av.avalCEPIRecurso = null))"
				  + " and av.cepi.dependencia.id = '"+dependenciaRev+"'"
				  + " order by av.aviId desc");
			resultado = q.list();
			session.close();
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Aval> obtenerAvalesCESI(String dependenciaRev) throws DataAccessException {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();
			q = session.createQuery(
					"select av from Aval av"
				  + " where"
				  + " av.tipo = 'ETICO'"
				  + " and (av.aviEstado IN ('ES') and av.avalTieneRecurso = 'SI' and av.avalCESI = null)"
				  + " and av.cepi.cesi.dependencia.id = '"+dependenciaRev+"'"
				  + " order by av.aviId desc");
			resultado = q.list();
			session.close();
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Aval> obtenerAvalesCESIQueja(String dependenciaRev) throws DataAccessException {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();
			q = session.createQuery(
					"select av from Aval av"
				  + " where"
				  + " av.tipo = 'ETICO'"
				  + " and (av.aviEstado IN ('NP') and av.avalTieneQueja = 'SI' and av.textoCESIQueja = null)"
				  + " and av.cepi.cesi.dependencia.id = '"+dependenciaRev+"'"
				  + " order by av.aviId desc");
			resultado = q.list();
			session.close();
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Aval> obtenerAvalesRectoria() throws DataAccessException {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();
			q = session.createQuery(
					"select av from Aval av" + " where "
							+ " av.aviEstado = 'RD' and av.avalRectoria is null and av.fechaAvalDRE > to_date('30/06/2022','dd/MM/YYYY') order by av.aviId desc");
			System.out.println(q);
			resultado = q.list();
			session.close();
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Aval> obtenerAvalesInvestigador(String doc, String tipodoc) throws DataAccessException {

		List AvalesInv = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();
		try {
		String query = "select "
				+ "AVI_ID as idAval, "
				+ "AVI_TITULO as tituloAval, "
				+ "AVI_TIPO as tipoAval, "
				+ "AVI_ESTADO as estadoAval, "
				+ "AVI_AVALFACULTAD as avalFacultad, "
				+ "AVI_FECHAREGISTRO as fechaSolicitud, "
				+ "AVI_FECHA_AVAL_FAC as fechaAvalFac, "
				+ "AVI_FECHA_AVAL_COOR as fechaDir, "
				+ "AVI_AVALDIRECCION as avalDireccion, "
				+ "AVI_AVALVICE as avalVice, "
				+ "AVI_ULTIMO_ENVIO_SOLICIT as ultimoEnvio, "
				+ "DPN_ID as dependencia, "
				+ "AVI_AVALUAB as avalUab, "
				+ "AVI_FECHA_AVAL_UAB as fechaUab, "
				+ "AVI_SOPORTE_PART_CONV as tieneArSopCon, "
				+ "AVI_TIENE_RECURSO as tieneRecurso, "
				+ "AVI_TIPO_RECURSO as tipoRecurso, "
				+ "AVI_AVALCEPI_RECURSO as avalCEPIRecurso, "
				+ "AVI_AVALDRE as avalDRE, "
				+ "AVI_AVALRECTORIA as avalRectoria, "
				+ "AVI_FECHA_AVAL_RECTORIA as fechaAvalRectoria, "
				+ "AVI_FECHA_AVAL_DRE as fechaAvalDRE, "
				+ "AVI_FECHA_AVAL_VICE as aviFechaAvalVice "
				+ " from HER_AVAL av" + " where  av.INV_ID = '" + doc + "' and av.TDO_ID = '" + tipodoc
				+ "' and AVI_TIPO <> 'BI' and AVI_TIPO <> 'AR' and AVI_ESTADO <> 'B' order by AVI_ID desc";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		AvalesInv = sqlQuery1.addScalar("idAval", Hibernate.LONG)
				.addScalar("tituloAval", Hibernate.STRING)
				.addScalar("tipoAval", Hibernate.STRING)
				.addScalar("estadoAval", Hibernate.STRING)
				.addScalar("avalFacultad", Hibernate.STRING)
				.addScalar("fechaSolicitud", Hibernate.DATE)
				.addScalar("fechaAvalFac", Hibernate.DATE)
				.addScalar("fechaDir", Hibernate.DATE)
				.addScalar("avalDireccion", Hibernate.STRING)
				.addScalar("avalVice", Hibernate.STRING)
				.addScalar("ultimoEnvio", Hibernate.DATE)
				.addScalar("dependencia", Hibernate.STRING)
				.addScalar("avalUab", Hibernate.STRING)
				.addScalar("fechaUab", Hibernate.DATE)
				.addScalar("tieneArSopCon", Hibernate.STRING)
				.addScalar("tieneRecurso", Hibernate.STRING)
				.addScalar("tipoRecurso", Hibernate.STRING)
				.addScalar("avalCEPIRecurso", Hibernate.STRING)
				.addScalar("avalDRE", Hibernate.STRING)
				.addScalar("avalRectoria", Hibernate.STRING)
				.addScalar("fechaAvalRectoria", Hibernate.DATE)
				.addScalar("fechaAvalDRE", Hibernate.DATE)
				.addScalar("aviFechaAvalVice", Hibernate.DATE).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = AvalesInv.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Aval av = new Aval();

			av.setAviId((Long) name[0]);
			av.setAviTitulo((String) name[1]);
			av.setTipo((String) name[2]);
			av.setAviEstado((String) name[3]);
			av.setAviAvalfacultad((String) name[4]);
			av.setFecha((Date) name[5]);
			av.setAviFechaAvalFac((Date) name[6]);
			av.setAviFechaAvalCoor((Date) name[7]);
			av.setAviAvaldireccion((String) name[8]);
			av.setAvalVice((String) name[9]);
			av.setFechaUltimoEnvio((Date) name[10]);
			Dependencia dep = new Dependencia((String) name[11]);
			av.setDependencia(dep);
			av.setAviAvalUab((String) name[12]);
			av.setAviFechaAvalUab((Date) name[13]);
			av.setTieneArchivosSoporteParticipacionConvocatoria((String) name[14]);
			av.setAvalTieneRecurso((String) name[15]);
			List<Tipos> lista = obtenerObjetoXID(Tipos.class, !esNulo(name[16]) ? name[16].toString() : "0");
			av.setTipoRecursoAvalEtico((Tipos) lista.get(0));
			av.setAvalCEPIRecurso((String) name[17]);
			av.setAvalDRE((String) name[18]);
			av.setAvalRectoria((String) name[19]);
			av.setFechaAvalRectoria((Date) name[20]);
			av.setFechaAvalDRE((Date) name[21]);
			av.setAviFechaAvalVice((Date) name[22]);

			result.add(av);
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Aval> obtenerAvalesProyecto(String idProyecto) throws DataAccessException {

		List AvalesInv = new ArrayList();
		List result = new ArrayList();

		Session session = getSession();
		try {
		String query = "select AVI_ID as idAval, AVI_TITULO as tituloAval, AVI_TIPO as tipoAval, AVI_ESTADO as estadoAval, AVI_AVALFACULTAD as avalFacultad,"
				+ " AVI_FECHAREGISTRO as fechaSolicitud, AVI_FECHA_AVAL_FAC as fechaAvalFac, AVI_FECHA_AVAL_COOR as fechaDir, AVI_RESPONSABLE_CAMBIO as resp, DPN_ID as dependencia,"
				+ " AVI_AVALVICE as avalVice, AVI_AVALUAB AS aviavalUab, AVI_AVALDIRECCION AS aviAvaldireccion"
				+ " from HER_AVAL av" + " where  av.pry_id = '" + idProyecto
				+ "' and AVI_TIPO <> 'BI' and AVI_ESTADO <> 'B' order by AVI_ID desc";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		AvalesInv = sqlQuery1.addScalar("idAval", Hibernate.LONG).addScalar("tituloAval", Hibernate.STRING)
				.addScalar("tipoAval", Hibernate.STRING).addScalar("estadoAval", Hibernate.STRING)
				.addScalar("avalFacultad", Hibernate.STRING).addScalar("fechaSolicitud", Hibernate.DATE)
				.addScalar("fechaAvalFac", Hibernate.DATE).addScalar("fechaDir", Hibernate.DATE)
				.addScalar("resp", Hibernate.STRING).addScalar("dependencia", Hibernate.STRING)
				.addScalar("avalVice", Hibernate.STRING).addScalar("aviavalUab", Hibernate.STRING)
				.addScalar("aviAvaldireccion", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = AvalesInv.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Aval av = new Aval();

			av.setAviId((Long) name[0]);
			av.setAviTitulo((String) name[1]);
			av.setTipo((String) name[2]);
			av.setAviEstado((String) name[3]);
			av.setAviAvalfacultad((String) name[4]);
			av.setFecha((Date) name[5]);
			av.setAviFechaAvalFac((Date) name[6]);
			av.setAviFechaAvalCoor((Date) name[7]);
			av.setResponsableCambio((String) name[8]);
			Dependencia dep = new Dependencia((String) name[9]);
			av.setDependencia(dep);
			av.setAvalVice((String) name[10]);
			av.setAviAvalUab((String) name[11]);
			av.setAviAvaldireccion((String) name[12]);

			result.add(av);
		}
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Aval> obtenerAvalesProyectoXEstatoAvalXTipoAval(String idProyecto, String tiposAval, String estadosAval, Boolean incluirnNoAprobados) throws DataAccessException {

		List AvalesInv = new ArrayList();
		List result = new ArrayList();
		String noAprobados = incluirnNoAprobados 
				? " OR (AVI_AVALFACULTAD = 'N' OR AVI_AVALFACULTAD = 'N' OR AVI_AVALVICE = 'N' OR AVI_AVALRECTORIA = 'N' OR AVI_AVALDRE = 'N' OR AVI_AVALCEPI = 'N' OR AVI_AVALCESI = 'N')" 
				: "";
		
		Session session = getSession();
		try {
		String query = "select "
				+ "AVI_ID as idAval, "
				+ "AVI_TITULO as tituloAval, "
				+ "AVI_TIPO as tipoAval, "
				+ "AVI_ESTADO as estadoAval, "
				+ "AVI_AVALFACULTAD as avalFacultad, "
				+ "AVI_FECHAREGISTRO as fechaSolicitud, "
				+ "AVI_FECHA_AVAL_FAC as fechaAvalFac, "
				+ "AVI_FECHA_AVAL_COOR as fechaDir, "
				+ "AVI_RESPONSABLE_CAMBIO as resp, "
				+ "DPN_ID as dependencia, "
				+ "AVI_AVALVICE as avalVice, "
				+ "AVI_AVALUAB AS aviavalUab, "
				+ "AVI_AVALDIRECCION AS aviAvaldireccion "
				+ "FROM HER_AVAL av " 
				+ "WHERE av.pry_id = '" + idProyecto + "' "
				+ "AND AVI_TIPO IN ("+ tiposAval +") "
				+ "AND (AVI_ESTADO IN ("+ estadosAval +") "+ noAprobados +") "
				+ "ORDER BY AVI_ID DESC";
		
		System.out.println("query" + query);

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		AvalesInv = sqlQuery1.addScalar("idAval", Hibernate.LONG).addScalar("tituloAval", Hibernate.STRING)
				.addScalar("tipoAval", Hibernate.STRING).addScalar("estadoAval", Hibernate.STRING)
				.addScalar("avalFacultad", Hibernate.STRING).addScalar("fechaSolicitud", Hibernate.DATE)
				.addScalar("fechaAvalFac", Hibernate.DATE).addScalar("fechaDir", Hibernate.DATE)
				.addScalar("resp", Hibernate.STRING).addScalar("dependencia", Hibernate.STRING)
				.addScalar("avalVice", Hibernate.STRING).addScalar("aviavalUab", Hibernate.STRING)
				.addScalar("aviAvaldireccion", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = AvalesInv.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Aval av = new Aval();

			av.setAviId((Long) name[0]);
			av.setAviTitulo((String) name[1]);
			av.setTipo((String) name[2]);
			av.setAviEstado((String) name[3]);
			av.setAviAvalfacultad((String) name[4]);
			av.setFecha((Date) name[5]);
			av.setAviFechaAvalFac((Date) name[6]);
			av.setAviFechaAvalCoor((Date) name[7]);
			av.setResponsableCambio((String) name[8]);
			Dependencia dep = new Dependencia((String) name[9]);
			av.setDependencia(dep);
			av.setAvalVice((String) name[10]);
			av.setAviAvalUab((String) name[11]);
			av.setAviAvaldireccion((String) name[12]);

			result.add(av);
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Aval> obtenerAval(String id) throws DataAccessException {

		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery(" from Aval av" + " where av.aviId=:id");
			q.setString("id", id);

			resultado = q.list();
			session.close();
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

	public List obtenerDependencias(Sede sede) throws DataAccessException {
		List resultado = new ArrayList();
		Util util = Util.getInstace();
		Session session = getSession();
		try {
		resultado = util.consultaPadre1Parametro(session, "Dependencia", "nombre", "", "sede", sede,false);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		session.close();
		return resultado;
	}

	/**
	 * Se obtienen todas las facultades de la Universidad de la tabla
	 * Dependencias
	 */
	public List obtenerFacultades() throws DataAccessException {
		Session session = getSession();
		List l = new ArrayList();
		try {
		l = session.createCriteria(Dependencia.class).add(Expression.eq("esFacultad", new Boolean(true))).list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return l;
	}

	public List obtenerFacultades(Dependencia pSede) throws DataAccessException {
		Session session = getSession();
		List l = new ArrayList();
		try {
		l = session.createCriteria(Dependencia.class).add(Expression.eq("esFacultad", new Boolean(true)))
				.add(Expression.eq("estado", "A"))
				.add(Expression.eq("sede.id", new Long(pSede.getId()))).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return l;
	}

	/**
	 * Obtiene una lista con todos los registros de una tabla mapeada con el
	 * mombre de la clase
	 * 
	 * @param clase
	 *            Nombre de la clase
	 */
	public List obtenerListaObjetos(String clase) throws DataAccessException {
		try {
			return getHibernateTemplate().find("from " + clase);
		} catch (DataAccessException de) {
			de.printStackTrace(System.out);
			throw de;
		}
	}

	public boolean ejecutarSentencia(String sql) throws DataAccessException {
		boolean ret = false;
		Session s = getSession();
		Statement st = null;
		try {
			s = getSession();
			st = s.connection().createStatement();
			ret = st.execute(sql);
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
		return ret;
	}

	public List obtenerListaObjetosAdministrador(String clase, String id) throws DataAccessException {
		return getHibernateTemplate().find("from " + clase + " where perId =" + id);
	}

	public List<DocumentoVice> obtenerListaDocumentosVice(String opcion) throws DataAccessException {
		try {
			if (opcion.equals("all")) {
				return getHibernateTemplate().find("from DocumentoVice where id != 0 order by nombre");
			} else if (opcion.equals("A")) {
				return getHibernateTemplate().find("from DocumentoVice where estado = 'A' and id != 0 order by nombre");
			} else if (opcion.equals("I")) {
				return getHibernateTemplate()
						.find("from DocumentoVice where estado = 'I' and  id != 0 order by nombre");
			} else if (opcion.trim().length() > 0) {
				return getHibernateTemplate().find("from DocumentoVice where idPadre = '" + opcion
						+ "' and id != 0 and  estado = 'A' order by nombre");
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List obtenerListaRequisitoHijo(Long idPadre) {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery(
					" select tr from TipoRequisito tr" + " where tr.padre.id=:idPadre ORDER BY tr.id asc ");
			q.setLong("idPadre", idPadre.longValue());

			resultado = q.list();
			session.close();
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

	public List obtenerListaCompromisoHijo(Long idPadre) {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery(" from TipoCompromiso tc" + " where tc.padre.id=:idPadre");
			q.setLong("idPadre", idPadre.longValue());

			resultado = q.list();
			session.close();
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

	/**
	 * Obtiene la ubicación
	 * 
	 */
	public List obtenerUbicacion(String ubicacion, String valorUbicacion, String s_padre, Object o_padre, boolean incluirTodos)
			throws DataAccessException {
		List resultado = new ArrayList();
		Util util = Util.getInstace();
		Session session = getSession();
		try {
		resultado = util.consultaPadre1Parametro(session, ubicacion, "nombre", valorUbicacion, s_padre, o_padre, incluirTodos);

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return resultado;
	}

	public void guardarObjeto(Object objeto) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(objeto);
	}

	public void guardarObjetoLaboratorio(Object objeto) throws DataAccessException {

		Session session = getSession();
		Statement st = null;
		Transaction tr = session.beginTransaction();

		try {

			getHibernateTemplate().saveOrUpdate(objeto);

			tr.commit();
			session.close();

		} catch (Exception ex) {
			tr.rollback();
			System.out.println(ex.toString());
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
	}

	public boolean guardarColeccion(Coleccion coleccion) throws DataAccessException {
		boolean guardo = true;
		try {
			getHibernateTemplate().saveOrUpdate(coleccion);
		} catch (Exception e) {
			guardo = false;
		}
		return guardo;

	}

	public void insertarObjeto(Object objeto) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(objeto);
	}

	public String insertarPosibleEvaluador(Persona ev) throws DataAccessException {
		getHibernateTemplate().save(ev);
		return null;
	}

	/**
	 * Devuelve una palabra si existe, si no devuelve null
	 */
	public PalabraClave obtenerPalabraClave(String nombre) throws DataAccessException {
		Session session = getSession();
		PalabraClave palabraClave = null;
		try {
		Criterion crit_palabra = Restrictions.ilike("palabra", nombre, MatchMode.EXACT);
		Criterion crit_palabra1 = Restrictions.ilike("idioma", "ES", MatchMode.EXACT);

		Criteria criteria = session.createCriteria(PalabraClave.class);
		criteria.add(crit_palabra);
		criteria.add(crit_palabra1);
		palabraClave = (PalabraClave) criteria.uniqueResult();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return palabraClave;
	}

	/**
	 * Devuelve una palabra si existe, si no devuelve null
	 */

	public PalabraClave obtenerPalabraClaveIngles(String nombre) throws DataAccessException {
		Session session = getSession();
		PalabraClave palabraClave = null;

		try {
		Criterion crit_palabra = Restrictions.ilike("palabra", nombre, MatchMode.EXACT);
		Criterion crit_palabra1 = Restrictions.ilike("idioma", "EN", MatchMode.EXACT);

		Criteria criteria = session.createCriteria(PalabraClave.class);
		criteria.add(crit_palabra);
		criteria.add(crit_palabra1);
		palabraClave = (PalabraClave) criteria.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return palabraClave;
	}

	/**
	 * Obtiene el departamento especifico de la ciudad
	 */
	public Departamento obtenerDepartamento(Ciudad ciudad) throws DataAccessException {

		Session session = getSession();
		Departamento departamento = null;
		try {
		Criteria criteria = session.createCriteria(Ciudad.class);
		ciudad = (Ciudad) criteria.add(Restrictions.idEq(ciudad.getId())).uniqueResult();
		departamento = ciudad.getDepartamento();
		departamento.getNombre();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return departamento;
	}

	public void eliminarObjeto(Object objeto) throws DataAccessException {
		Session session = getSession();
		try {
		Transaction tr = session.beginTransaction();

		session.delete(objeto);

		tr.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

	}

	public Object obtenerObjeto(Object clase, Object id) throws DataAccessException {

		Session session = getSession();
		Object object = null;

		try {
			object = session.createCriteria(clase.getClass()).add(Restrictions.idEq(id)).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return object;
	}

	/**
	 * 
	 * @param objeto
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public Object obtenerObjetoYPadre(Object objeto, Object id) throws DataAccessException {

		Session session = getSession();
		Object objetoFinal = null;
		try{
		objetoFinal = session.createCriteria(objeto.getClass()).setFetchMode("padre", FetchMode.JOIN)
				.add(Restrictions.idEq(id)).uniqueResult();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return objetoFinal;
	}

	/**
	 * La funcion obtenerHijos sirve para obtener todos los registros de una
	 * tabla que sean hijos del padre especificado esta funcion solo es valida
	 * para los objetos que tienen la propiedad padre en sus atributos y en la
	 * tabla es una relacion uno a muchos con la misma tabla (arbol)
	 * 
	 * @param padre
	 * @return
	 * @throws DataAccessException
	 */
	public List obtenerHijos(Object padre) throws DataAccessException {

		Session session = getSession();
		List lista = new ArrayList();
		try {
		Criteria criteria = session.createCriteria(padre.getClass());

		criteria.add(Restrictions.like("padre", padre));
		criteria.addOrder(Order.asc("nombre"));
		lista = criteria.list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return lista;
	}

	public Dependencia obtenerDependenciaPorPaginaWeb(String paginaWeb) throws DataAccessException {
		Session session = getSession();
		Dependencia dpn = null;
		try {
		Criteria dependenciaCriteria = session.createCriteria(Dependencia.class);
		dependenciaCriteria.add(Expression.eq("paginaWeb", paginaWeb));

		dpn = (Dependencia) dependenciaCriteria.uniqueResult();

			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				session.close();
			}
		return dpn;
	}

	public List obtenerListaObjetosOrdenadosAsc(Object object, String campoOrden) throws DataAccessException {

		Session session = getSession();
		List result = new ArrayList();
		try {
		Criteria criteria = session.createCriteria(object.getClass());
		criteria.addOrder(Order.asc(campoOrden));
		if (object.getClass().equals(Pais.class) || object.getClass().equals(Departamento.class)
				|| object.getClass().equals(Ciudad.class)) {
			criteria.add(Restrictions.isNotNull("sigla"));
		}
		result = criteria.list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return result;
	}

	public <T> List<T> obtenerListaObjetosOrdenadosAscG(Class<T> t, String campoOrden) throws DataAccessException {
		List result = new ArrayList();;
		Session session = getSession();
		try {
		Criteria criteria = session.createCriteria(t);
		criteria.addOrder(Order.asc(campoOrden));
		if (t.equals(Pais.class) || t.equals(Departamento.class) || t.equals(Ciudad.class)) {
			criteria.add(Restrictions.isNotNull("sigla"));
		}
		result = criteria.list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return result;
	}

	public List getReporteEstudianteProyecto(Long modalidad) throws DataAccessException {

		System.out.println("getReporteEstudianteProyecto- metodo en generalDAO");
		System.out.println(modalidad.longValue());
		Session session = getSession();
		List resultado = new Vector();
		try {
		Transaction tx = session.beginTransaction();
		Query q = session.createQuery(
				" " + " select ip.proyecto.id,ip.investigador.id.documento,ip.investigador.nombre1,ip.investigador.nombre2,ip.investigador.apellido1,ip.investigador.apellido2 "
						+ " from InvestigadorProyecto ip left outer join ip.proyecto"
						+ " where ip.proyecto.estadoProyecto.id != :estado and ip.proyecto.modalidad.id=:modalidad "
						+ " and ip.tipo=:tipo ");
		q.setString("estado", "B");
		q.setLong("modalidad", modalidad.longValue());
		q.setString("tipo", "P");
		System.out.println();

		List proyectos = q.list();
		
		System.out.println("fdjsafjdka" + proyectos.size());

		for (Iterator it = proyectos.iterator(); it.hasNext();) {
			Object[] hc = new Object[15];
			Object[] r = (Object[]) it.next();
			Query a2 = session.createQuery(" " + " select d.sede.nombre " + " from Dependencia d,InvestigadorInterno ii"
					+ " where d.id=ii.dependencia.id and ii.id.documento=:idPersona");
			a2.setString("idPersona", ((String) r[1]));
			hc[0] = r[2];
			hc[1] = r[3];
			hc[2] = r[4];
			hc[3] = r[5];
			hc[4] = a2.uniqueResult();
			Query a3 = session
					.createQuery(" " + " select d.facultad.nombre " + " from Dependencia d,InvestigadorInterno ii"
							+ " where d.id=ii.dependencia.id and ii.id.documento=:idPersona");
			a3.setString("idPersona", ((String) r[1]));
			hc[5] = a3.uniqueResult();
			Query a4 = session.createQuery(" " + " select d.nombre " + " from Dependencia d,InvestigadorInterno ii"
					+ " where d.id=ii.dependencia.departamento and ii.id.documento=:idPersona");
			a4.setString("idPersona", ((String) r[1]));
			hc[6] = a4.uniqueResult();
			Query a5 = session.createQuery(" "
					+ "select ip.investigador.nombre1,ip.investigador.nombre2,ip.investigador.apellido1,ip.investigador.apellido2,e.nombreCarrera"
					+ " ,ip.investigador.email,ip.investigador.id.documento "
					+ " from InvestigadorProyecto ip,Estudiante e"
					+ " where ip.proyecto.id=:proyecto and ip.tipo=:tipo and ip.investigador.id.documento =e.id.documento ");
			a5.setLong("proyecto", ((Long) r[0]).longValue());
			a5.setString("tipo", "A");
			a5.setMaxResults(1);
			Object[] r2 = (Object[]) a5.uniqueResult();
			if (r2 != null) {

				hc[7] = r2[0];
				hc[8] = r2[1];
				hc[9] = r2[2];
				hc[10] = r2[3];
				hc[11] = r2[4];
				hc[12] = r2[5];
				hc[13] = r2[6];
			}
			Query a6 = session.createQuery(
					" " + "select sum(f.valor) " + "from Financiacion f " + "where f.proyecto.id=:idProyecto");
			a6.setLong("idProyecto", ((Long) r[0]).longValue());
			hc[14] = a6.uniqueResult();
			System.out.println(((String) r[1]) + ((String) r[2]) + ((String) r[3]) + ((String) r[4]) + ((String) r[5])
					+ ((String) a2.uniqueResult()) + ((String) a3.uniqueResult()) + ((String) a4.uniqueResult())
					+ a6.uniqueResult());
			resultado.add(hc);
		}
		tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return resultado;

	}

	public String obtenerIdRubro() {

		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		String myString = new String();
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "select max(tru_id)+1 " + "from her_tipo_rubro ";
			System.out.println(query);
			rs = st.executeQuery(query);
			while (rs.next()) {
				myString = rs.getString("max(tru_id)+1");
				return myString;
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
		return myString;
	}

	public void insertarTipoRubro(String consecutivo, String nombre) {

		Session session = getSession();
		Statement st = null;
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "insert into her_tipo_rubro(tru_id,tru_nombre) " + "values(" + consecutivo + ",'" + nombre + "')";
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Se obtienen todas las facultades de la Universidad de la tabla
	 * Dependencias
	 */
	public List obtenerSedes() {
		Session session = getSession();
		List l = new ArrayList();
		try {
		l = session.createCriteria(Dependencia.class).add(Expression.eq("esSede", new Boolean(true)))
				.addOrder(Order.asc("nombre")).list();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
				session.close();
		}
		return l;
	}

	public Object adjuntarObjetoPropiedad(String nombreObjecto, Object id, String propiedadesAdjuntas) {
		Session session = getSession();
		Object o = null;
		try {
		Query q = session.createQuery(" select o" + " from " + nombreObjecto + " o " + " left outer join o."
				+ propiedadesAdjuntas + " where o.id=:id ");
		if (id instanceof Long) {
			q.setLong("id", ((Long) id).longValue());
		}
		if (id instanceof String) {
			q.setString("id", ((String) id));
		}
		System.out.println("codigo adjuntar objeto");
		o = q.uniqueResult();
		System.out.println("la session tiene ?" + session.contains(((Proyecto) o).getEmpresas()));
		Hibernate.initialize(((Proyecto) o).getEmpresas());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return o;
	}

	public Empresa buscarEmpresaXNIT(String nit) {
		Session session = getSession();
		Empresa e = null;
		
		try {
		Criteria criteria = session.createCriteria(Empresa.class);
		criteria.add(Expression.eq("nit", nit));
		e = (Empresa) criteria.uniqueResult();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return e;
	}

	public List obtenerListaObjetosXListaId(Object[] listaId, String objeto) {
		Session session = getSession();
		List resultado = new ArrayList();
		try {
		Query q = session.createQuery(" select o from " + objeto + " where o.id in (:listaId)");

		q.setParameterList("listaId", listaId);
		resultado = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return resultado;
	}

	public List obtenerListaTipoRubroXPadre(Long id) {
		Session session = getSession();
		List resultado = new ArrayList();
		try {
		Query q = session.createQuery("select tr from TipoRubro tr" + " where tr.padre.id = :id order by tr.id");

		q.setLong("id", id.longValue());
		resultado = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return resultado;
	}

	public List obtenerListaTipoRubro1Nivel(boolean incluirPadres) {
		String consultaAdicional = "";
		Session session = getSession();
		List resultado = new ArrayList();
		try {
		Query q = session.createQuery(" select tr from TipoRubro tr"
				+ " where (tr.descripcion = 'GASTOS_CP_2022' and tr.nombreVRI = '1' and tr.estado = 1) or tr.id = 53 order by tr.id");
		resultado = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return resultado;
	}
	

	public List obtenerListaTipoRubro2022porNivel(int nivel) {
		Session session = getSession();
		List resultado = new ArrayList();
		try {
		Query q = session.createQuery(" select tr from TipoRubro tr"
				+ " where (tr.descripcion = 'GASTOS_CP_2022' and tr.nombreVRI = '"+nivel+"') order by tr.id");
		resultado = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return resultado;
	}

	public void actualizarEstadoEvaluador(Persona person) {
		Session s = getSession();
		Statement st = null;
		try {
			s = getSession();
			st = s.connection().createStatement();
			String ins = "update her_investigador set inv_interno = '" + person.getGenero() + "',inv_evaluador ='"
					+ person.getNacionalidad() + "'" + " where inv_id = '" + person.getDireccion() + "'";
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

	public Persona obtenerEstadoInvestigador(String id) {

		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		Persona person = new Persona();
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "select per.per_id,per.per_nombre1,per.per_nombre2,per.per_apellido1,per.per_apellido2,inv.inv_interno,inv.inv_evaluador "
					+ "from her_persona per,her_investigador inv " + "where per.per_id = inv.inv_id and "
					+ "inv.inv_id = '" + id + "'";
			System.out.println(query);
			rs = st.executeQuery(query);
			while (rs.next()) {
				person.setDireccion(rs.getString("per_id"));
				person.setNombre1(rs.getString("per_nombre1"));
				person.setNombre2(rs.getString("per_nombre2"));
				person.setApellido1(rs.getString("per_apellido1"));
				person.setApellido2(rs.getString("per_apellido2"));
				person.setGenero(rs.getString("inv_interno"));
				person.setNacionalidad(rs.getString("inv_evaluador"));
			}
			return person;
		} catch (Exception ex) {

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
		return person;
	}

	public List obtenerHijosTipoXPadre(Long idTipoPadre) {
		Session session = getSession();
		List resultado = new ArrayList();
		try {
		Query q = session.createQuery(" select t from Tipos t" + " where t.padre.id=:idPadre ORDER BY orden");

		q.setLong("idPadre", idTipoPadre.longValue());

		resultado = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return resultado;
	}

	public List obtenerHijosTipoXPadreOrdenABC(Long idTipoPadre) {
		Session session = getSession();
		List resultado = new ArrayList();
		try {
		Query q = session.createQuery(" select t from Tipos t" + " where t.padre.id=:idPadre ORDER BY nombre ASC");

		q.setLong("idPadre", idTipoPadre.longValue());

		resultado = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return resultado;
	}

	public Dependencia cargaDependencia(String nit) {
		Session session = getSession();
		Dependencia e = null;
		try {
		Criteria criteria = session.createCriteria(Dependencia.class);
		criteria.add(Expression.eq("id", nit));
		criteria.setFetchMode("sede", FetchMode.JOIN);
		criteria.setFetchMode("facultad", FetchMode.JOIN);
		criteria.setFetchMode("nombre", FetchMode.JOIN);
		e = (Dependencia) criteria.uniqueResult();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return e;
	}

	public List obtenerPalabrasClaveConteniendoCadena(String nombre) {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery("select p.palabra " + " from  PalabraClave p where "
					+ ReemplazaAcentos.queryQuitaTildes("p.palabra") + " like :nombre" + "");
			q.setString("nombre", "%" + ReemplazaAcentos.quitarTildes(nombre.toLowerCase()) + "%");
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

	public List obtenerKeyWordEmpezandoCon(String nombre) {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery("select p.palabraOriginal " + " from  PalabraClave p where "
					+ ReemplazaAcentos.queryQuitaTildes("p.palabraOriginal") + " like :nombre"
					+ " and p.idioma = 'EN' ");
			q.setString("nombre", ReemplazaAcentos.quitarTildes(nombre.toLowerCase()) + "%");
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

	public List obtenerPalabrasClaveEmpezandoCon(String nombre) {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery("select p.palabraOriginal " + " from  PalabraClave p where "
					+ ReemplazaAcentos.queryQuitaTildes("p.palabraOriginal") + " like :nombre"
					+ " and p.idioma = 'ES' ");
			q.setString("nombre", ReemplazaAcentos.quitarTildes(nombre.toLowerCase()) + "%");
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

	public List obtenerAreastematicas(String nombre) {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery("select p.nombre" + " from  ClasificacionConocimiento p where "
					+ ReemplazaAcentos.queryQuitaTildes("p.palabraOriginal") + " like :nombre");
			q.setString("nombre", "%" + ReemplazaAcentos.quitarTildes(nombre.toLowerCase()).trim() + "%");
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

	public List obtenerLineasEmpezandoCon(String nombre) {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery("select l.nombre " + " from  LineaInvestigacion l where "
					+ ReemplazaAcentos.queryQuitaTildes("l.nombre") + " like :nombre");
			q.setString("nombre", "%" + ReemplazaAcentos.quitarTildes(nombre.toLowerCase()) + "%");
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

	public List obtenerInstitucionesQueContienen(String nombreBusqueda) throws DataAccessException {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery("select I.nombre " + " from  Institucion I where "
					+ ReemplazaAcentos.queryQuitaTildes("I.nombre") + " like :nombre order by I.nombre");
			q.setString("nombre", "%" + ReemplazaAcentos.quitarTildes(nombreBusqueda.toLowerCase()) + "%");

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

	public List obtenerAreasConocimientoEmpezandoCon(String nombre) {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			nombre = ReemplazaAcentos.quitarTildes(nombre.toLowerCase());
			q = session.createQuery("select c.nombre " + " from  ClasificacionConocimiento c where "
					+ ReemplazaAcentos.queryQuitaTildes("c.nombre") + "  like '%" + nombre + "%'");
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

	public Session getSesion() {
		return getSession();
	}

	public List listaDeObjetosYDiferenteElIdStringA(String clase, String id) {
		return getHibernateTemplate().find("from " + clase + " where id!='" + id + "'");
	}

	public List listaDeObjetosYDiferenteElIdStringAMod(String clase, String id, String modalidad) {
		return getHibernateTemplate()
				.find("from " + clase + " where id!='" + id + "' and tipoModalidad = '" + modalidad + "'");
	}

	public List buscarListaDeInstitucionesPorNombre(String nombre) {

		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery("select i " + " from  Institucion i where "
					+ ReemplazaAcentos.queryQuitaTildes("i.nombre") + " like :nombre" + "" + " order by i.nombre");
			q.setString("nombre", ReemplazaAcentos.quitarTildes(nombre.toLowerCase()) + "%");
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

	public List buscarDepartamentosFacultad(String idFacultad) {
		Session session = getSession();
		List departamentos = new ArrayList();
		try {
		departamentos = session.createCriteria(Dependencia.class)
				.add(Expression.eq("esDepartamento", new Boolean(true))).add(Expression.eq("facultad.id", idFacultad))
				.list();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return departamentos;
	}

	public List buscarDepartamentosFacultadPersona(String idPersona) {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery("select dep.id, dep.nombre" + " from Dependencia dep"
					+ " where dep.sede.id = (select d.sede.id" + " from Dependencia d, InvestigadorInterno i"
					+ " where i.dependencia.id = d.id" + " and i.id = :idPersona)" + " and dep.esFacultad = 'Y'");
			q.setString("idPersona", idPersona);
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

	public List obtenerServicioDe1NivelXRol(Rol r) {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session
					.createQuery("select s " + " from  Rol r,Servicio s" + " where s.padre.id is null and r.id=:idRol"
							+ " and r.servicios.id=s.id" + " order by lower(s.nombre)");
			q.setString("idRol", r.getId());
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

	public List obtenerServiciosXPadre(Servicio s) {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery(
					"select s " + " from  Servicio s where s.padre.id=:idPadre " + " order by lower(s.nombre)");
			q.setString("idPadre", s.getId());
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

	public List obtenerDepartamentos() {
		Session session = getSession();

		List departamentos = new ArrayList();
		try {
		departamentos = session.createCriteria(Dependencia.class)
				.add(Expression.eq("esDepartamento", new Boolean(true))).list();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return departamentos;
	}

	public List obtenerListaProductoHijo(String idPadre) {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery(" select pt from ProductoTipo pt" + " where pt.padre.id=:idPadre");
			q.setString("idPadre", idPadre);

			resultado = q.list();
			session.close();
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

	public List verificarEvaluadorConvocatoria(String mod) {

		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		PosibleEvaluador pos;
		List myList = new ArrayList();
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "select pev.per_id,pev.pev_nombre,pev.pev_apellido1 "
					+ "from her_posible_evaluador pev,her_proyecto pr, " + "her_modalidad mod,her_convocatoria con "
					+ "where pev.pry_id = pr.pry_id and " + "pr.mod_id = mod.mod_id and "
					+ "con.con_id = mod.mod_id and " + "con.con_id = '" + mod + "'";
			System.out.println(query);
			rs = st.executeQuery(query);
			while (rs.next()) {
				pos = new PosibleEvaluador();
				pos.setDocumento(rs.getString("per_id"));
				pos.setNombre(rs.getString("pev_nombre"));
				pos.setApellido1(rs.getString("pev_apellido1"));
				myList.add(pos);
				pos = null;
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

	public List verificarEvaluadorInternoConvocatoria(String mod) {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		PosibleEvaluador pos;
		List myList = new ArrayList();
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "select per.per_nombre1,per.per_apellido1,per.per_id "
					+ "from her_posible_evaluador_interno pevi,her_persona per,her_modalidad mod,her_convocatoria conv,her_proyecto pr "
					+ "where pevi.pry_id = pr.pry_id and " + "pr.mod_id = mod.mod_id and "
					+ "conv.con_id = mod.mod_id and " + "pevi.inv_id = per.per_id and " + "conv.con_id = '" + mod + "'";
			System.out.println(query);
			rs = st.executeQuery(query);
			while (rs.next()) {
				pos = new PosibleEvaluador();
				pos.setDocumento(rs.getString("per_id"));
				pos.setNombre(rs.getString("per_nombre1"));
				pos.setApellido1(rs.getString("per_apellido1"));
				myList.add(pos);
				pos = null;
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

	public List buscarListaDeNombresInstitucionesPorNombre(String nombre) {

		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery("select i.nombre " + " from  Institucion i where "
					+ ReemplazaAcentos.queryQuitaTildes("i.nombre") + " like :nombre" + "" + " order by i.nombre");
			q.setString("nombre", ReemplazaAcentos.quitarTildes(nombre.toLowerCase()) + "%");
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

	public void insertarRecursos(Long padre, Long mod, String sede, Long valor, Long apoyo) {

		Session session = getSession();
		Statement st = null;
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "insert into her_distribucion_recursos(cnp_id,con_id,sed_id,valor,apoyo) values(" + padre
					+ "," + mod + ",'" + sede + "'," + valor + "," + apoyo + ")";
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public void actualizarRecursos(Long padre, Long mod, String sede, Long valor, Long apoyo) {

		Session session = getSession();
		Statement st = null;
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "update her_distribucion_recursos " + "set valor = " + valor + ",apoyo = " + apoyo
					+ " where cnp_id = " + padre + " and con_id = " + mod + " and " + "sed_id = '" + sede + "'";
			System.out.println(query);
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public List seleccionarValores(Long padre, Long mod) {

		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		DistribucionRecursos dist;
		List myList = new ArrayList();
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "select sed_id,valor,apoyo " + "from her_distribucion_recursos " + "where cnp_id = " + padre
					+ " and con_id = " + mod + " order by sed_id ";
			System.out.println(query);
			rs = st.executeQuery(query);
			while (rs.next()) {
				dist = new DistribucionRecursos();
				dist.setSed_id(rs.getString("sed_id"));
				dist.setValor(new Long(rs.getLong("valor")));
				dist.setApoyo(new Long(rs.getLong("apoyo")));
				myList.add(dist);
				dist = null;
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

	public Institucion obtenerinstitucionPorNombre(String nombre) {

		Session session = null;
		Query q = null;
		Institucion resultado = null;
		try {
			session = getSession();

			q = session.createQuery("select i " + " from  Institucion i where "
					+ ReemplazaAcentos.queryQuitaTildes("i.nombre") + " like :nombre ");
			q.setString("nombre", ReemplazaAcentos.quitarTildes(nombre.toLowerCase()));
			q.setMaxResults(1);
			resultado = (Institucion) q.uniqueResult();

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

	public List<Institucion> obtenerListaInstituciones() throws DataAccessException {

		Session session = getSession();
		List result = new ArrayList();

		try {
		Criteria criteria = session.createCriteria(Institucion.class);
		criteria.addOrder(Order.asc("nombre"));
		result = criteria.list();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	public <T> List<T> obtenerObjetoXID(Class<T> clazz, String id) {
		return obtenerObjetoXID(clazz.getSimpleName(), id);
	}

	// Aurelio
	public List obtenerObjetoXID(String clase, String id) throws DataAccessException {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();

			q = session.createQuery(" select pt from " + clase + " pt" + " where pt.id=:idP");
			q.setString("idP", id);

			resultado = q.list();
			session.close();
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

	public List obtenerListaObjetosWhere(String clase, String where) throws DataAccessException {
		try {
			return getHibernateTemplate().find("from " + clase + " " + where);
		} catch (DataAccessException de) {
			de.printStackTrace(System.out);
			throw de;
		}
	}

	public <T> List<T> obtenerListaObjetosWhere(Class<T> clazz, String where) throws DataAccessException {
		try {
			return getHibernateTemplate().find(
					"SELECT " + clazz.getSimpleName().substring(0, 1).toLowerCase() + " FROM " + clazz.getSimpleName()
							+ " " + clazz.getSimpleName().substring(0, 1).toLowerCase() + " " + where);
		} catch (DataAccessException de) {
			de.printStackTrace(System.out);
			throw de;
		}
	}

	
	public List obtenerObjetos(String hql) throws DataAccessException {
		try {
			return getHibernateTemplate().find(hql);
		} catch (DataAccessException de) {
			de.printStackTrace(System.out);
			throw de;
		}
	}

	public Date obtenerFechaDB() throws DataAccessException {

		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		List myList = new ArrayList();
		Date nombre = new Date();
		Date fechadb = null;
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "select sysdate from dual";
			rs = st.executeQuery(query);
			while (rs.next()) {
				nombre = rs.getDate("sysdate");
			}
			return nombre;
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
		return nombre;
	}

	public void actualizarEvaluacion(int id, String valor) throws DataAccessException {

		Session session = getSession();
		Statement st = null;

		List myList = new ArrayList();
		Date nombre = new Date();
		Date fechadb = null;
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "update her_proyecto_evaluador set pre_activo = '" + valor + "' where pre_id = '" + id + "'";
			st.executeQuery(query);

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

	}

	public List<ConvocatoriaPadre> obtenerConvocatoriasBuscador(String where, String fromSql, boolean limite)
			throws DataAccessException {

		if (where.length() > 0)
			where = where + " and ";

		List<Object> convocatorias = null;

		List<ConvocatoriaPadre> resultado = new ArrayList<ConvocatoriaPadre>();

		Session session = getSession();
		try {

		String query = "select distinct * from (SELECT HER_CONVOCATORIA_PADRE.CNP_ID as idConvocatoria, CNP_TITULO as tituloConvocatoria, CNP_ESTADO as estadoConvocatoria,"
				+ " HER_DEPENDENCIA.DPN_NOMBRE as nombreDependenciaConvocatoria, HER_DEPENDENCIA.DPN_ID as idDependenciaConvocatoria, HER_CONVOCATORIA_PADRE.CNP_ANO as anoConvocatoria, HER_CONVOCATORIA_PADRE.CNP_VINCULO_CONVOCATORIA as vinculo"
				+ " FROM HER_CONVOCATORIA_PADRE, HER_DEPENDENCIA " + fromSql + " where " + where
				+ " (CNP_ESTADO = 'A' or CNP_ESTADO = 'I')"
				+ " and HER_DEPENDENCIA.DPN_ID = HER_CONVOCATORIA_PADRE.DNP_ID ";

		if (limite)
			query += " and ROWNUM <= 3 ";
		query += ") ORDER BY idConvocatoria DESC ";
		SQLQuery sqlQuery = session.createSQLQuery(query);
		convocatorias = sqlQuery.addScalar("idConvocatoria", Hibernate.LONG)
				.addScalar("tituloConvocatoria", Hibernate.STRING).addScalar("estadoConvocatoria", Hibernate.STRING)
				.addScalar("idDependenciaConvocatoria", Hibernate.STRING)
				.addScalar("nombreDependenciaConvocatoria", Hibernate.STRING)
				.addScalar("anoConvocatoria", Hibernate.STRING).addScalar("vinculo", Hibernate.STRING).list();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		Iterator<Object> it = convocatorias.iterator();

		while (it.hasNext()) {

			Object[] name = (Object[]) it.next();

			ConvocatoriaPadre convocatoria = new ConvocatoriaPadre();
			String nombre = (String) name[1];
			nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1, nombre.length()).toUpperCase();
			convocatoria.setId((Long) name[0]);
			convocatoria.setTitulo(nombre);
			convocatoria.setAno((String) name[5]);
			convocatoria.setVinculo((String) name[6]);
			EstadoConvocatoria estadoConvocatoria = new EstadoConvocatoria();
			estadoConvocatoria.setId((String) name[2]);
			if (estadoConvocatoria.getId().equals("A")) {
				estadoConvocatoria.setNombre("Activa");
			} else
				estadoConvocatoria.setNombre("Inactiva");
			convocatoria.setEstadoConvocatoria(estadoConvocatoria);
			Dependencia dependencia = new Dependencia();
			dependencia.setId((String) name[3]);
			dependencia.setNombre((String) name[4]);
			convocatoria.setDependencia(dependencia);
			resultado.add(convocatoria);
		}

		return resultado;

	}

	public List<ConvocatoriaPadre> obtenerConvocatoriasBuscadorPrincipal(String where, String fromSql, boolean limite)
			throws DataAccessException {

		if (where.length() > 0)
			where = where + " and ";

		List<Object> convocatorias = null;

		List<ConvocatoriaPadre> resultado = new ArrayList<ConvocatoriaPadre>();

		Session session = getSession();
		try {
		String query = "SELECT * FROM ("
				+ "SELECT DISTINCT * from (SELECT HER_CONVOCATORIA_PADRE.CNP_ID as idConvocatoria, CNP_TITULO as tituloConvocatoria, CNP_ESTADO as estadoConvocatoria,"
				+ " HER_DEPENDENCIA.DPN_NOMBRE as nombreDependenciaConvocatoria, HER_DEPENDENCIA.DPN_ID as idDependenciaConvocatoria, HER_CONVOCATORIA_PADRE.CNP_ANO as anoConvocatoria "
				+ " FROM HER_CONVOCATORIA_PADRE, HER_DEPENDENCIA " + fromSql + " where " + where
				+ " (CNP_ESTADO = 'A' or CNP_ESTADO = 'I')"
				+ " and HER_DEPENDENCIA.DPN_ID = HER_CONVOCATORIA_PADRE.DNP_ID ";

		query += " ) ORDER BY IDCONVOCATORIA DESC" + ")  ";
		if (limite)
			query += " WHERE ROWNUM <= 4 ";
		SQLQuery sqlQuery = session.createSQLQuery(query);
		convocatorias = sqlQuery.addScalar("idConvocatoria", Hibernate.LONG)
				.addScalar("tituloConvocatoria", Hibernate.STRING).addScalar("estadoConvocatoria", Hibernate.STRING)
				.addScalar("idDependenciaConvocatoria", Hibernate.STRING)
				.addScalar("nombreDependenciaConvocatoria", Hibernate.STRING)
				.addScalar("anoConvocatoria", Hibernate.STRING).list();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		Iterator<Object> it = convocatorias.iterator();

		while (it.hasNext()) {

			Object[] name = (Object[]) it.next();

			ConvocatoriaPadre convocatoria = new ConvocatoriaPadre();
			String nombre = (String) name[1];
			nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1, nombre.length()).toUpperCase();
			convocatoria.setId((Long) name[0]);
			convocatoria.setTitulo(nombre);
			convocatoria.setAno((String) name[5]);
			EstadoConvocatoria estadoConvocatoria = new EstadoConvocatoria();
			estadoConvocatoria.setId((String) name[2]);
			if (estadoConvocatoria.getId().equals("A")) {
				estadoConvocatoria.setNombre("Activa");
			} else
				estadoConvocatoria.setNombre("Inactiva");
			convocatoria.setEstadoConvocatoria(estadoConvocatoria);
			Dependencia dependencia = new Dependencia();
			dependencia.setId((String) name[3]);
			dependencia.setNombre((String) name[4]);
			convocatoria.setDependencia(dependencia);
			resultado.add(convocatoria);
		}

		return resultado;

	}

	public List<Proyecto> obtenerECPCatalogoBuscador(String where, String fromSql) throws DataAccessException {

		if (where.length() > 0)
			where = where + " and ";

		List<Object> catalogos = null;

		List<Proyecto> resultado = new ArrayList<Proyecto>();

		Session session = getSession();
		try {
		String query = " select HER_PROYECTO.PRY_ID as idCatalogo, HER_PROYECTO.PRY_NOMBRE as nombreCatalogo "
				+ ", HER_PROYECTO.PRY_OBJETIVO_GENERAL as objetoCatalogo, HER_DEPENDENCIA.DPN_ID as idDependencia, HER_DEPENDENCIA.DPN_NOMBRE as nombreDependencia "
				+ "FROM HER_PROYECTO, HER_DEPENDENCIA " + fromSql + " where " + where
				+ " HER_PROYECTO.DPN_ID = HER_DEPENDENCIA.DPN_ID ";

		SQLQuery sqlQuery = session.createSQLQuery(query);

		catalogos = sqlQuery.addScalar("idCatalogo", Hibernate.LONG).addScalar("nombreCatalogo", Hibernate.STRING)
				.addScalar("objetoCatalogo", Hibernate.STRING).list();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		Iterator<Object> it = catalogos.iterator();

		while (it.hasNext()) {

			Object[] name = (Object[]) it.next();
			Proyecto catalogo = new Proyecto();
			catalogo.setId((Long) name[0]);
			catalogo.setNombre((String) name[1]);
			catalogo.setObjetivoGeneral((String) name[2]);

			resultado.add(catalogo);
		}

		return resultado;

	}

	public List<LaboratorioDetalleEnsayosServicios> obtenerEnsayosLaboratorioBuscador(
			String where,
			String where2,
			String fromSql,
			List<Dependencia> sedesSeleccionadas,
			List<Dependencia> facultadesSeleccionadas
			)
			throws DataAccessException {

		if (where.length() > 0)
			where = where + " and ";
		if (where2.length() > 0)
			where2 = where2 + " and ";

		List<Object> ensayosLaboratorio = null;

		List<LaboratorioDetalleEnsayosServicios> resultado = new ArrayList<LaboratorioDetalleEnsayosServicios>();

		Session session = getSession();
		try {
		String query = "select "
				+ "l.LAB_ID as idEnsayo, "
				+ "max(s.sed_nombre) as ciudadSede, "
				+ "l.LAB_ID as idLaboratorio, "
				+ "s.sed_id as idSede, " 
				+ "max(s.sed_nombre) as ciudadSede, " 
				+ "L.LAB_NOMBRE as nombreLaboratorio, "
				+ "max(L.LAB_FACULTAD) as idFacultad, "
				+ "l.lab_gestion_acreditacion_cual as acreditacion, "
				+ "max(facultad.DPN_NOMBRE) as nombreFacultad, "
				+ "max(l.LAB_GESTION_ACREDITACION) as acred "
				+ "FROM HER_LABORATORIO_DETALLE_ENSAYO de, HER_LABORATORIO l, HER_SEDE s, HER_DEPENDENCIA facultad"
				+ " where facultad.DPN_ID = L.LAB_FACULTAD and "
				+ " "
				+ where
				+ " l.LAB_ACTIVO = 1 "
				+ "AND l.LAB_ID <> 49 "
				+ "AND L.LAB_TIPO IN (39) "
				+ "AND de.LAB_ID (+)= l.LAB_ID "
				+ "AND s.SED_ID = l.SED_ID "
				+ "GROUP BY l.LAB_ID, L.LAB_NOMBRE,s.sed_id, l.lab_gestion_acreditacion_cual"
				+ " UNION "
				+ "select "
				+ "l.LAB_ID as idEnsayo, "
				+ "max(s.sed_nombre) as ciudadSede, "
				+ "l.LAB_ID as idLaboratorio, "
				+ "s.sed_id as idSede, " 
				+ "max(s.sed_nombre) as ciudadSede, " 
				+ "L.LAB_NOMBRE as nombreLaboratorio, "
				+ "max(L.LAB_FACULTAD) as idFacultad, "
				+ "l.lab_gestion_acreditacion_cual as acreditacion, "
				+ "max(facultad.DPN_NOMBRE) as nombreFacultad, "
				+ "max(l.LAB_GESTION_ACREDITACION) as acred "
				+ "FROM HER_LABORATORIO_DETALLE_EQUIPO de, HER_LABORATORIO l, HER_SEDE s, HER_DEPENDENCIA facultad"
				+ " where facultad.DPN_ID = L.LAB_FACULTAD and "
				+ " "
				+ where2
				+ " l.LAB_ACTIVO = 1 "
				+ "AND l.LAB_ID <> 49 "
				+ "AND L.LAB_TIPO IN (39) "
				+ "AND de.LAB_ID (+)= l.LAB_ID "
				+ "AND s.SED_ID = l.SED_ID "
				+ "GROUP BY l.LAB_ID, L.LAB_NOMBRE,s.sed_id, l.lab_gestion_acreditacion_cual "
				+ "ORDER BY nombreLaboratorio";

		SQLQuery sqlQuery = session.createSQLQuery(query);

		ensayosLaboratorio = sqlQuery
				.addScalar("idEnsayo", Hibernate.LONG)
				.addScalar("ciudadSede", Hibernate.STRING)
				.addScalar("idLaboratorio", Hibernate.LONG)
				.addScalar("nombreLaboratorio", Hibernate.STRING)
				.addScalar("idSede", Hibernate.LONG)
				.addScalar("ciudadSede", Hibernate.STRING)
				.addScalar("idFacultad", Hibernate.LONG)
				.addScalar("acreditacion", Hibernate.STRING)
				.addScalar("nombreFacultad", Hibernate.STRING)
				.addScalar("acred", Hibernate.LONG).list();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		Iterator<Object> it = ensayosLaboratorio.iterator();
		boolean agregarFacultad;
		boolean agregarSede;
		while (it.hasNext()) {
			agregarFacultad = false;
			agregarSede = false;
			Object[] name = (Object[]) it.next();
			Sede sede = new Sede();
			sede.setId((Long) name[4]);
			Dependencia facultad = new Dependencia();
			if (name[6] != null) {
				facultad.setId(((Long) name[6]).toString());
			} else
				facultad = null;
			if (sedesSeleccionadas != null && sedesSeleccionadas.size() > 0) {
				for (Dependencia sede2 : sedesSeleccionadas) {
					if (sede.getId().toString().equals(sede2.getId())) {
						agregarSede = true;
					}
				}
			} else
				agregarSede = true;
			if (facultadesSeleccionadas != null && facultadesSeleccionadas.size() > 0) {
				if (facultad != null) {
					for (Dependencia facultad2 : facultadesSeleccionadas) {
						if (facultad.getId().equals(facultad2.getId())) {
							agregarFacultad = true;
						}
					}
				} else
					agregarFacultad = false;
			} else
				agregarFacultad = true;
			if (agregarSede && agregarFacultad) {
				sede.setNombre((String) name[5]);
				facultad.setNombre((String) name[8]);
				Laboratorio laboratorio = new Laboratorio();
				LaboratorioDetalleEnsayosServicios ensayoLaboratorio = new LaboratorioDetalleEnsayosServicios();
				ensayoLaboratorio.setId((Long) name[0]);
				ensayoLaboratorio.setNombre((String) name[1]);
				laboratorio.setId((Long) name[2]);
				laboratorio.setNombre((String) name[3]);
				laboratorio.setSede(sede);
				laboratorio.setFacultad(facultad);
				laboratorio.setGestionAcreditacionNorma((String) name[7]);
				laboratorio.setGestionAcreditacion((Long) name[9]);
				ensayoLaboratorio.setLaboratorio(laboratorio);
				resultado.add(ensayoLaboratorio);
			}
		}

		return resultado;

	}
	
	public Boolean esAmbienteProduccion() throws HibernateException, SQLException {
		
		Session session = (Session) getSesion();
		String url = "";
		try {
		url = session.connection().getMetaData().getURL();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		
		if(url.equals("jdbc:oracle:thin:@168.176.6.21:1521:tumaco"))
			return false;
		return true;
	}

	public List listaNombresEmpresasContiene(String nombreEmpresa) {
		Session session = null;
		Query q = null;
		List resultado = null;
		try {
			session = getSession();
			q = session.createQuery("select l.nombre " + " from Empresa l where "
					+ ReemplazaAcentos.queryQuitaTildes("l.nombre") + " like :nombre" + " order by l.nombre");
			q.setString("nombre", "%" + ReemplazaAcentos.quitarTildes(nombreEmpresa.toLowerCase()) + "%");
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

	public Empresa buscarEmpresaXNombre(String nombre) {
		Session session = getSession();
		Empresa e = null;
		try {
		Criteria criteria = session.createCriteria(Empresa.class);
		criteria.add(Expression.eq("nombre", nombre));
		e = (Empresa) criteria.uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
				session.close();
		}
		return e;
	}

	public void descargarDocumentoDisco(String path, Boolean origen) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (origen)
			path = "RUTA_ARCHIVOS" + path;
		else
			path = "D://" + path;
		File ficheroXLS = new File(path);

		FileInputStream fis;
		try {
			fis = new FileInputStream(ficheroXLS);

			byte[] bytes = new byte[1000];

			if (!ctx.getResponseComplete()) {
				String fileName = ficheroXLS.getName();
				String contentType = "application/msword";
				HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();

				response.setContentType(contentType);

				response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");

				ServletOutputStream out = response.getOutputStream();

				int read;
				while ((read = fis.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}

				out.flush();
				out.close();
				ctx.responseComplete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean esEstudiante(String tipoInvestigador) {
		Session session = getSession();
		Parametro p = new Parametro();
		try {
		Criteria criteria = session.createCriteria(Parametro.class);
		criteria.add(Expression.eq("nombre", "TIPO_ROL_ESTUDIANTE"));
		p = (Parametro) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				session.close();
		}
		if (p.getDescripcion()!=null && p.getDescripcion().contains("-" + tipoInvestigador + "-")) {
			return true;
		} else {
			return false;
		}
	}

	public Coleccion obtenerColeccion(Long id) throws DataAccessException {
		Session session = getSession();

		Coleccion c = (Coleccion) session.createCriteria(Coleccion.class).add(Restrictions.idEq(id))
				.add(Restrictions.not(Restrictions.in("estado", new String[] { "B" }))).uniqueResult();

		session.close();
		return c;
	}

	public Investigador obtenerInvestigadorPorEmail(String correo) throws DataAccessException {
		Session session = getSession();
		Investigador i = new Investigador();
		try {
		

		Criteria crit = session.createCriteria(Investigador.class).add(Restrictions.eq("email", correo));
		crit.setMaxResults(1);
		List<Investigador> list = crit.list();
		i = (Investigador) list.get(0);
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return i;
	}

	public List<Coleccion> obtenerColeccionBuscador(String sql) throws DataAccessException {
		List<Object> colecciones = null;
		List<Coleccion> resultado = new ArrayList<Coleccion>();

		Session session = getSession();

		try {
		if (sql.length() > 0) {
			sql += " and ";
		} else
			sql = " where ";

		String query = "SELECT HER_COLECCION.COL_ID as idColeccion, UPPER(COL_NOMBRE) as nombreColeccion, HER_DEPENDENCIA.DPN_ID as idDependencia, HER_DEPENDENCIA.DPN_NOMBRE as dependenciaNombre,"
				+ " HER_SEDE.SED_ID as idSede,  HER_SEDE.SED_NOMBRE as nombreSede "
				+ " FROM HER_COLECCION, HER_DEPENDENCIA , HER_SEDE " + sql
				+ " HER_COLECCION.DPN_ID = HER_DEPENDENCIA.DPN_ID and HER_DEPENDENCIA.SED_ID = HER_SEDE.SED_ID and HER_COLECCION.COL_ESTADO in ('A','E','C','AR','R')";

		SQLQuery sqlQuery = session.createSQLQuery(query);

		colecciones = sqlQuery.addScalar("idColeccion", Hibernate.LONG).addScalar("nombreColeccion", Hibernate.STRING)
				.addScalar("idDependencia", Hibernate.STRING).addScalar("dependenciaNombre", Hibernate.STRING)
				.addScalar("idSede", Hibernate.LONG).addScalar("nombreSede", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator<Object> it = colecciones.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Coleccion coleccion = new Coleccion();
			Dependencia dependencia = new Dependencia();
			Sede sede = new Sede();

			sede.setId((Long) name[4]);
			sede.setNombre((String) name[5]);

			dependencia.setId((String) name[2]);
			dependencia.setNombre((String) name[3]);
			dependencia.setSede(sede);

			coleccion.setId((Long) name[0]);
			String nombre = (String) name[1];
			coleccion.setNombre(nombre);
			coleccion.setDependencia(dependencia);

			resultado.add(coleccion);
		}

		return resultado;
	}

	// Liliana O.
	public Long duplicarProyecto(Long idProyecto) {

		Session session = getSession();
		Connection con = null;
		con = session.connection();
		Long newPry = 0L;

		try {
			CallableStatement upperProc = con.prepareCall("{ ? = call HERMES.FUN_DUPLICAR_PROYECTO( ? ) }");
			upperProc.registerOutParameter(1, java.sql.Types.INTEGER);
			upperProc.setLong(2, idProyecto);
			upperProc.execute();
			newPry = upperProc.getLong(1); // id nuevo proyecto
			System.out.println("Pry new === " + newPry);
			upperProc.close();
			// cerrar conexiones
			con = null;
			if (session != null)
				session.close();
			session = null;

		} catch (Exception ex) {
			System.out.println("FAIL connection ");
			ex.printStackTrace();
			// cerrar conexiones
			con = null;
			if (session != null)
				session.close();
			session = null;
		} finally {
			// cerrar conexiones
			con = null;
			if (session != null)
				session.close();
			session = null;
		}

		return newPry;
	}

	public Boolean esNulo(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			String str = (String) obj;
			return str.trim().length() == 0;
		}
		if (obj instanceof Number) {
			Number n = (Number) obj;
			return n.doubleValue() == 0.0;
		}
		return false;
	}

	public Boolean esNuloVacio(Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj.equals("")) {
			return true;
		}

		return false;
	}

	public void calcularPorcentajeLab(Laboratorio laboratorioActual) {
		Long idLabL = laboratorioActual.getId();

		String sql;
		List<Map> listaReporte2;
		int registros;

		if (idLabL != null) {
			try {
				Float completitud = new Float(100);
				String datosCompletitud = "";

				// (1) ********************* INFORMACIÓN GENERAL
				// ******************************************************

				// Sede
				if (esNuloVacio(laboratorioActual.getSede())) {
					completitud += -1;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Sede)\r\n";
				}

				// Nucleo, centro o campus
				if (esNuloVacio(laboratorioActual.getCampus())) {
					completitud += -1;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Nucleo, centro o campus)\r\n";
				}

				// Facultad
				if (esNuloVacio(laboratorioActual.getFacultad())) {
					completitud += -1;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Facultad)\r\n";
				}

				// Edificio
				if (esNuloVacio(laboratorioActual.getEdificio())) {
					completitud += -1;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Edificio)\r\n";
				}

				// Salón
				if (esNuloVacio(laboratorioActual.getSalon())) {
					completitud += -1;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Salón)\r\n";
				}

				// Piso
				if (esNuloVacio(laboratorioActual.getPiso())) {
					completitud += -1;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Piso)\r\n";
				}

				// Área
				if (esNuloVacio(laboratorioActual.getAreaM2()) || laboratorioActual.getAreaM2() <= 0) {
					completitud += -1;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Área)\r\n";
				}

				// E-mail
				if (esNuloVacio(laboratorioActual.getEmail())) {
					completitud += -1;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (E-mail)\r\n";
				}

				// Página web - Eliminado 04/19
				// Agregado 12/24 
				 if (esNuloVacio(laboratorioActual.getPaginaWeb())) {
				 completitud += -1;
				 datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Página web)\r\n";
				 }

				// Teléfono
				if (esNuloVacio(laboratorioActual.getTelefono())) {
					completitud += -1;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Teléfono)\r\n";
				}

				// Fax - Eliminado 04/19
				// if (esNuloVacio(laboratorioActual.getFax())) {
				// completitud += -0.2F;
				// datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Fax)\r\n";
				// }

				// Tipo - Actualizado 04/19
				if (esNuloVacio(laboratorioActual.getTipo())) {
					completitud += -2;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Tipo)\r\n";
				}

				// Portafolio
				// Eliminado Marzo 2025 [7380]
//				if (esNuloVacio(laboratorioActual.getPortafolioServicios())) {
//					completitud += -1;
//					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Portafolio)\r\n";
//				}

				// Descripción
				if (esNuloVacio(laboratorioActual.getDescripcion())) {
					completitud += -2;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Descripción)\r\n";
				}

				// Clasificación OCDE - Eliminado 04/19
				// sql = "SELECT HLAO_ID AS A, LAB_ID AS B FROM
				// HER_LABORATORIO_AREA_OCDE WHERE LAB_ID = '" + idLabL + "'";
				// System.out.println("sql: " + sql);
				// listaReporte2 = obtenerMapa(sql);
				// registros = listaReporte2.size();
				// if (registros == 0) {
				// completitud += -1.6F;
				// datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Clasificación
				// OCDE)\r\n";
				// }

				// Objetivo Socio-economico - Nuevo 04/19
				if (esNuloVacio(laboratorioActual.getObjetivoSocioEconomico())) {
					completitud += -1;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Objetivo Socio-Económico)\r\n";
				}

				// Area OCDE Principal - Nuevo 04/19
				if (esNuloVacio(laboratorioActual.getAreaPrincipal())) {
					completitud += -1;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Area principal OCDE)\r\n";
				}
				
				// Reglamento
				// Archivos reglamento
				sql = "SELECT HAL_ID AS A, HAL_LAB_ID AS B FROM HER_ARCHIVO_LABORATORIO WHERE HAL_LAB_ID = '" + idLabL
						+ "' AND HAL_TIPO_ARCHIVO IN (7665)";
				listaReporte2 = obtenerMapa(sql);
				registros = listaReporte2.size();
				if (registros == 0 && esNuloVacio(laboratorioActual.getLinkReglamento())) {
					completitud += -1;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Reglamento)\r\n";
				}

				// Dedicación - Ajustado 05/24
				Boolean dedicacionDocNulo = esNuloVacio(laboratorioActual.getDedicacionDocencia());
				Boolean dedicacionExtNulo = esNuloVacio(laboratorioActual.getDedicacionExtension());
				Boolean dedicacionInvNulo = esNuloVacio(laboratorioActual.getDedicacionInvestigacion());
				Float sumaDedicacion = laboratorioActual.getDedicacionDocencia()
						+ laboratorioActual.getDedicacionExtension() + laboratorioActual.getDedicacionInvestigacion();

				if ((dedicacionDocNulo && dedicacionExtNulo && dedicacionInvNulo) || sumaDedicacion != 100) {
					completitud += -1;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Dedicación)\r\n";
				}

				// Acto de creación
				Boolean tipoACNulo = esNuloVacio(laboratorioActual.getTipoActoCreacion());
				Boolean numACNulo = esNuloVacio(laboratorioActual.getNumeroActoCreacion());
				Boolean emanACNulo = esNuloVacio(laboratorioActual.getEmanadaPorActoCreacion());
				Boolean fechaACNulo = esNuloVacio(laboratorioActual.getFechaActoCreacion());

				if (tipoACNulo || numACNulo || emanACNulo || fechaACNulo) {
					completitud += -3;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Acto de creación)\r\n";
				}

				// Archivos acto de creación
				// Ajustado Marzo 2025 [7380]
				sql = "SELECT HAL_ID AS A, HAL_LAB_ID AS B FROM HER_ARCHIVO_LABORATORIO WHERE HAL_LAB_ID = '" + idLabL
						+ "' AND HAL_TIPO_ARCHIVO = 305";
				listaReporte2 = obtenerMapa(sql);
				registros = listaReporte2.size();
				if (registros == 0) {
					completitud += -2;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Archivos acto de creación)\r\n";
				}
				
				// Nuevo 05/24
//				String pathImgLabs = "//data//archivos//" + File.separator + "HER_LABORATORIO" + File.separator;
//			    File file = new File(pathImgLabs + laboratorioActual.getId() + ".jpg");
//				Boolean existeImagenLab = file.exists();
//				if(!existeImagenLab) {
//					completitud += -0.5F;
//					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Imagen del laboratorio)\r\n";
//				}
				
				// Nuevo 05/24
				if(esNuloVacio(laboratorioActual.getODSPrincipal())) {
					completitud += -1;
					datosCompletitud += "- [1-INFORMACIÓN GENERAL] (Objetivo Desarrollo Sostenible Principal)\r\n";
				}

				// (2) ********************* RECURSO HUMANO **************************

				// Recurso humano - Personas asociadas al laboratorio (ROL_ID =
				// CO)
				sql = "SELECT HPL_ID AS A, LAB_ID AS B FROM HER_PERSONA_LABORATORIO WHERE LAB_ID = '" + idLabL
						+ "' AND HER_PERSONA_LABORATORIO.ROL_ID = 'CO'";
				listaReporte2 = obtenerMapa(sql);
				registros = listaReporte2.size();
				if (registros == 0) {
					completitud += -8;
					datosCompletitud += "- [2-RECURSO HUMANO] (Coordinador Principal)\r\n";
				}

				// Número de funcionarios - Se elimina este porcentaje por que los campos se habian eliminado 16/05/2024
//				Boolean funcPlantaNulo = esNuloVacio(laboratorioActual.getFuncionariosPlanta());
//				Boolean funcProvNulo = esNuloVacio(laboratorioActual.getFuncionariosProvisionales());
//				Boolean funcODSNulo = esNuloVacio(laboratorioActual.getFuncionariosODS());
//				Integer sumaFuncionarios = laboratorioActual.getFuncionariosPlanta()
//						+ laboratorioActual.getFuncionariosProvisionales() + laboratorioActual.getFuncionariosODS();
//
//				if ((funcPlantaNulo && funcProvNulo && funcODSNulo) || sumaFuncionarios <= 0) {
//					completitud += -2.5F;
//					datosCompletitud += "- [2-RECURSO HUMANO] (Número de funcionarios)\r\n";
//				}

				// Estudiantes - Se elimina este porcentaje por que los campos se habian eliminado 16/05/2024
//				Boolean estAuxNulo = esNuloVacio(laboratorioActual.getEstudiantesAuxiliares());
//				Boolean estBecNulo = esNuloVacio(laboratorioActual.getEstudiantesBecarios());
//				Boolean estMonNulo = esNuloVacio(laboratorioActual.getEstudiantesMonitores());
//				if (estAuxNulo && estBecNulo && estMonNulo) {
//					completitud += -2;
//					datosCompletitud += "- [2-RECURSO HUMANO] (Número de estudiantes)\r\n";
//				}

				// Personal calificado
				// Eliminado 12/24
//				if (esNuloVacio(laboratorioActual.getPersonalCalificado())) {
//					completitud += -1;
//					datosCompletitud += "- [2-RECURSO HUMANO] (Personal calificado)\r\n";
//				}

				// Suficiente personal
				// Eliminado 12/24
//				if (esNuloVacio(laboratorioActual.getPersonalSuficiente())) {
//					completitud += -1;
//					datosCompletitud += "- [2-RECURSO HUMANO] (Suficiente personal)\r\n";
//				}

				// (3) ********************* INFRAESTRUCTURA Y RIESGOS *********************************

				// (3.1) ================== Infraestructura estado actual ====================

				// Instalaciones
				if (esNuloVacio(laboratorioActual.getEstadoInstalacionesFisicas())) {
					completitud += -1;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Infraestructura - Estado Actual] (Instalaciones Físicas)\r\n";
				}

				// Climatización
				if (esNuloVacio(laboratorioActual.getEstadoClimatizacion())) {
					completitud += -1;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Infraestructura - Estado Actual] (Climatización)\r\n";
				}

				// Ventilación
				if (esNuloVacio(laboratorioActual.getEstadoVentilacion())) {
					completitud += -1;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Infraestructura - Estado Actual] (Ventilación)\r\n";
				}

				// Iluminación
				if (esNuloVacio(laboratorioActual.getEstadoIluminacion())) {
					completitud += -1;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Infraestructura - Estado Actual] (Iluminación)\r\n";
				}

				// Ruido
				if (esNuloVacio(laboratorioActual.getEstadoRuido())) {
					completitud += -1;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Infraestructura - Estado Actual] (Ruido)\r\n";
				}

				// ================== Servicios requeridos ====================
				// Ajustado 05/24
				if (esNuloVacio(laboratorioActual.getRequiereAmpliacion())
						&& esNuloVacio(laboratorioActual.getRequiereAdecuacion())
						&& esNuloVacio(laboratorioActual.getRequiereCubiertas())
						&& esNuloVacio(laboratorioActual.getRequiereEquipos())
						&& esNuloVacio(laboratorioActual.getRequiereParedes())
						&& esNuloVacio(laboratorioActual.getRequierePisos())
						&& esNuloVacio(laboratorioActual.getRequiereRedistribucion())
						&& esNuloVacio(laboratorioActual.getSer_agua())
						&& esNuloVacio(laboratorioActual.getSer_gas())
						&& esNuloVacio(laboratorioActual.getSer_vacio())
						&& esNuloVacio(laboratorioActual.getSer_aire_comprimido())
						&& esNuloVacio(laboratorioActual.getSer_control_iluminacion())
						&& esNuloVacio(laboratorioActual.getSer_aire_acondicionado())
						&& esNuloVacio(laboratorioActual.getSer_insonorizacion())
						&& esNuloVacio(laboratorioActual.getSer_circuito_cerrado_de_tv())
						&& esNuloVacio(laboratorioActual.getSer_puntos_de_red())
						&& esNuloVacio(laboratorioActual.getRequiereOtros())) {
					completitud += -1;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Infraestructura] (Ampliación, Adecuación, Cubiertas, Equipos, Paredes, Pisos, Redistribución, Agua, Gast, Otros)\r\n";
				}

				//(3.2) ======================= Residuos ========================
//				if (esNuloVacio(laboratorioActual.getResiduosSolidosBiologicos())
//						&& esNuloVacio(laboratorioActual.getResiduosSolidosQuimicos())
//						&& esNuloVacio(laboratorioActual.getResiduosSolidosRadioactivos())
//						&& esNuloVacio(laboratorioActual.getResiduosSolidosCitotoxicos())
//						&& esNuloVacio(laboratorioActual.getResiduosSolidosElectricos())
//						&& esNuloVacio(laboratorioActual.getResiduosSolidosOtros())
//						&& esNuloVacio(laboratorioActual.getResiduosLiquidosBiologicos())
//						&& esNuloVacio(laboratorioActual.getResiduosLiquidosQuimicos())
//						&& esNuloVacio(laboratorioActual.getResiduosLiquidosRadioactivos())
//						&& esNuloVacio(laboratorioActual.getResiduosLiquidosOtros())
//						&& esNuloVacio(laboratorioActual.getSga_biodegradables())
//						&& esNuloVacio(laboratorioActual.getSga_reciclables())
//						&& esNuloVacio(laboratorioActual.getSga_inertes())
//						&& esNuloVacio(laboratorioActual.getSga_ordinarios())
//						&& esNuloVacio(laboratorioActual.getSga_grasas_y_aceites())
//						&& esNuloVacio(laboratorioActual.getSga_lixiviados())
//						&& esNuloVacio(laboratorioActual.getSga_residuos_infecciosos())
//						&& esNuloVacio(laboratorioActual.getSga_residuos_quimicos())
//						&& esNuloVacio(laboratorioActual.getSga_residuos_radioactivos())
//						&& esNuloVacio(laboratorioActual.getSga_derivados_de_combustion())
//						&& esNuloVacio(laboratorioActual.getSga_no_derivados_de_combustion())
//						&& esNuloVacio(laboratorioActual.getSga_ruido())
//						&& esNuloVacio(laboratorioActual.getSga_olores_ofensivos())
//						&& esNuloVacio(laboratorioActual.getSga_aguas_domesticas())
//						&& esNuloVacio(laboratorioActual.getSga_aguas_de_interes_ambiental())
//						&& esNuloVacio(laboratorioActual.getSga_aguas_de_interes_sanitario())
//						&& esNuloVacio(laboratorioActual.getSga_derivados_de_combustion())
//						) {
//					completitud += -1.5F;
//					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Residuos y emisiones] (Residuos sólidos, Residuos liquidos, etc)\r\n";
//				}
				
				if (esNuloVacio(laboratorioActual.getResiduosSolidosBiologicos())
						&& esNuloVacio(laboratorioActual.getResiduosSolidosQuimicos())
						&& esNuloVacio(laboratorioActual.getResiduosSolidosRadioactivos())
						&& esNuloVacio(laboratorioActual.getResiduosSolidosCitotoxicos())
						&& esNuloVacio(laboratorioActual.getResiduosSolidosElectricos())
						&& esNuloVacio(laboratorioActual.getResiduosSolidosOtros())) {
					completitud += -0.45F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Residuos y emisiones] (Residuos sólidos)\r\n";
				}
				
				if (esNuloVacio(laboratorioActual.getResiduosLiquidosBiologicos())
						&& esNuloVacio(laboratorioActual.getResiduosLiquidosQuimicos())
						&& esNuloVacio(laboratorioActual.getResiduosLiquidosRadioactivos())
						&& esNuloVacio(laboratorioActual.getResiduosLiquidosOtros())) {
					completitud += -0.45F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Residuos y emisiones] (Residuos liquidos)\r\n";
				}
				
				// (3.3) ================== Seguridad y Riesgos ====================

				// Biológico
				if (esNuloVacio(laboratorioActual.getRiesgoBiologico())) {
					completitud += -0.45F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Seguridad y Riesgos] (Biológico)\r\n";
				}

				// Químico
				if (esNuloVacio(laboratorioActual.getRiesgoQuimico())) {
					completitud += -0.45F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Seguridad y Riesgos] (Químico)\r\n";
				}

				// Eléctrico
				if (esNuloVacio(laboratorioActual.getRiesgoElectrico())) {
					completitud += -0.45F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Seguridad y Riesgos] (Eléctrico)\r\n";
				}

				// Emisiones
				if (esNuloVacio(laboratorioActual.getEmisionesAtmosfericas())) {
					completitud += -0.45F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Seguridad y Riesgos] (Emisiones)\r\n";
				}

				// Alta tensión
				if (esNuloVacio(laboratorioActual.getExposicionAltaTension())) {
					completitud += -0.45F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Seguridad y Riesgos] (Alta tensión)\r\n";
				}

				// Almacenamiento reactivos
				if (esNuloVacio(laboratorioActual.getAlmacenamientoAdecuadoReactivos())) {
					completitud += -0.45F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Seguridad y Riesgos] (Almacenamiento reactivos)\r\n";
				}
				
				// Exposición a radiación NO Ionizante
				if (esNuloVacio(laboratorioActual.getRadiacionExposicionNoIonizante())) {
					completitud += -0.1575F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Seguridad y Riesgos] (Exposición a radiación NO Ionizante)\r\n";
				}

				// Seguridad industrial
				if (esNuloVacio(laboratorioActual.getSeguridadExtintores())
						&& esNuloVacio(laboratorioActual.getSeguridadBotiquin())
						&& esNuloVacio(laboratorioActual.getSeguridadDuchaEmergencia())
						&& esNuloVacio(laboratorioActual.getSeguridadSenalizacionGeneral())
						&& esNuloVacio(laboratorioActual.getSeguridadSenalizacionSeguridad())
						&& esNuloVacio(laboratorioActual.getSeguridadSenalizacionEmergencia())
						&& esNuloVacio(laboratorioActual.getElementosProteccionPersonal())) {
					completitud += -0.45F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Seguridad y Riesgos] (Seguridad industrial)\r\n";
				}

				// Manuales
				if (esNuloVacio(laboratorioActual.getManualProteccionBiologica())
						&& esNuloVacio(laboratorioActual.getManualProteccionQuimica())
						&& esNuloVacio(laboratorioActual.getManualProteccionElectrica())
						&& esNuloVacio(laboratorioActual.getManualProteccionRadiologica())) {
					completitud += -0.45F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Seguridad y Riesgos] (Manuales)\r\n";
				}

				// (3.5) ================== Radiación ====================

				// Equipo Emisor
				if (esNuloVacio(laboratorioActual.getRadiacionEquipoEmisor())) {
					completitud += -0.2250F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Radiación] (Equipo Emisor)\r\n";
				}

				// Fuente
				if (esNuloVacio(laboratorioActual.getRadiacionFuente())) {
					completitud += -0.2250F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Radiación] (Fuente)\r\n";
				}

				// Material Radioactivo
				if (esNuloVacio(laboratorioActual.getRadiacionMaterial())) {
					completitud += -0.2250F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Radiación] (Material Radioactivo)\r\n";
				}

				// Generación de residuos radioactivos
				if (esNuloVacio(laboratorioActual.getRadiacionResiduosRadioactivos())) {
					completitud += -0.1575F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Radiación] (Generación de residuos radioactivos)\r\n";
				}

				// Frecuencia emisión residuos radioactivos
				if (esNuloVacio(laboratorioActual.getRadiacionEmisionDiario())
						&& esNuloVacio(laboratorioActual.getRadiacionEmisionSemanal())
						&& esNuloVacio(laboratorioActual.getRadiacionEmisionMensual())) {
					completitud += -0.1575F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Radiación] (Frecuencia emisión residuos radioactivos)\r\n";
				}

				// Dosímetros
				if (esNuloVacio(laboratorioActual.getRadiacionDosimetrosPersonales())
						&& esNuloVacio(laboratorioActual.getRadiacionDosimetrosAmbientales())
						&& esNuloVacio(laboratorioActual.getRadiacionDosimetrosDeControl())) {
					completitud += -0.18F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Radiación] (Dosímetros)\r\n";
				}

				// Cuenta con programa de monitoreo
				if (esNuloVacio(laboratorioActual.getRadiacionProgramaMonitoreo())) {
					completitud += -0.27F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Radiación] (Cuenta con programa de monitoreo)\r\n";
				}

				// Carné de protección radiológica expedido por Ingeominas
				if (esNuloVacio(laboratorioActual.getRadiacionCarneProteccionRadiologica())) {
					completitud += -0.225F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Radiación] (Carné de protección radiológica)\r\n";
				}

				// Licencia de manejo de Ingeominas
				if (esNuloVacio(laboratorioActual.getRadiacionLicenciaManejoFuncionamiento())) {
					completitud += -0.27F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Radiación] (Licencia de manejo de Ingeominas)\r\n";
				}

				// Cuarto de decaimiento
				if (esNuloVacio(laboratorioActual.getRadiacionCuartoDecaimiento())) {
					completitud += -0.1575F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Radiación] (Cuarto de decaimiento)\r\n";
				}
				
				// (3.4) ================== Sustancias y productos químicos controlados ===================

				// Sustancias controladas
				sql = "SELECT LDI_ID AS A, LAB_ID AS B FROM HER_LABORATORIO_DET_INSUMO WHERE LAB_ID = '" + idLabL + "'";
				listaReporte2 = obtenerMapa(sql);
				registros = listaReporte2.size();
				if ((laboratorioActual.getUsaSustanciasControladas() && registros == 0)
						|| (esNuloVacio(laboratorioActual.getUsaSustanciasControladas()) && registros == 0)) {
					completitud += -2.25F;
					datosCompletitud += "- [3-INFRAESTRUCTURA Y RIESGOS] [Sustancias controladas] (Lista sustancias controladas)\r\n";
				}

				// (4) ********************* GESTIÓN
				// ******************************************************

				// Acreditación
				if (esNuloVacio(laboratorioActual.getGestionAcreditacion())) {
					completitud += -0.4F;
					datosCompletitud += "- [4-GESTIÓN] (Acreditación)\r\n";
				}

				// Acreditación Norma
				if (esNuloVacio(laboratorioActual.getGestionAcreditacionNorma())
						&& laboratorioActual.getGestionAcreditacion().equals(50L)) {
					completitud += -0.3F;
					datosCompletitud += "- [4-GESTIÓN] (Acreditación Norma)\r\n";
				}

				// Documento Acreditación
				sql = "SELECT HAL_ID AS A, HAL_LAB_ID AS B FROM HER_ARCHIVO_LABORATORIO WHERE HAL_LAB_ID = '" + idLabL
						+ "' AND HAL_TIPO_ARCHIVO = 282";
				listaReporte2 = obtenerMapa(sql);
				registros = listaReporte2.size();
				if (registros == 0 && laboratorioActual.getGestionAcreditacion().equals(50L)) {
					completitud += -0.3F;
					datosCompletitud += "- [4-GESTIÓN] (Documento acreditación)\r\n";
				}

				// Certificación
				if (esNuloVacio(laboratorioActual.getGestionCertificacion())) {
					completitud += -0.4F;
					datosCompletitud += "- [4-GESTIÓN] (Certificación)\r\n";
				}

				// Certificación Norma
				if (esNuloVacio(laboratorioActual.getGestionCertificacionNorma())
						&& laboratorioActual.getGestionCertificacion().equals(50L)) {
					completitud += -0.3F;
					datosCompletitud += "- [4-GESTIÓN] (Certificación Norma)\r\n";
				}

				// Documento Certificación
				sql = "SELECT HAL_ID AS A, HAL_LAB_ID AS B FROM HER_ARCHIVO_LABORATORIO WHERE HAL_LAB_ID = '" + idLabL
						+ "' AND HAL_TIPO_ARCHIVO = 284";
				listaReporte2 = obtenerMapa(sql);
				registros = listaReporte2.size();
				if (registros == 0 && laboratorioActual.getGestionCertificacion().equals(50L)) {
					completitud += -0.3F;
					datosCompletitud += "- [4-GESTIÓN] (Documento Certificación)\r\n";
				}

				// Habilitación
				if (esNuloVacio(laboratorioActual.getGestionHabilitacion())) {
					completitud += -0.4F;
					datosCompletitud += "- [4-GESTIÓN] (Habilitación)\r\n";
				}

				// Habilitación Norma
				if (esNuloVacio(laboratorioActual.getGestionHabilitacionNorma())
						&& laboratorioActual.getGestionHabilitacion().equals(50L)) {
					completitud += -0.3F;
					datosCompletitud += "- [4-GESTIÓN] (Habilitación Norma)\r\n";
				}

				// Documento Habilitación
				sql = "SELECT HAL_ID AS A, HAL_LAB_ID AS B FROM HER_ARCHIVO_LABORATORIO WHERE HAL_LAB_ID = '" + idLabL
						+ "' AND HAL_TIPO_ARCHIVO = 286";
				listaReporte2 = obtenerMapa(sql);
				registros = listaReporte2.size();
				if (registros == 0 && laboratorioActual.getGestionHabilitacion().equals(50L)) {
					completitud += -0.3F;
					datosCompletitud += "- [4-GESTIÓN] (Documento Habilitación)\r\n";
				}

				// Registro ICA
				if (esNuloVacio(laboratorioActual.getGestionRegistroIca())) {
					completitud += -0.4F;
					datosCompletitud += "- [4-GESTIÓN] (Registro ICA)\r\n";
				}

				// Registro ICA Norma
				if (esNuloVacio(laboratorioActual.getGestionRegistroIcaNorma())
						&& laboratorioActual.getGestionRegistroIca().equals(50L)) {
					completitud += -0.3F;
					datosCompletitud += "- [4-GESTIÓN] (Registro ICA Norma)\r\n";
				}

				// Documento Registro ICA
				sql = "SELECT HAL_ID AS A, HAL_LAB_ID AS B FROM HER_ARCHIVO_LABORATORIO WHERE HAL_LAB_ID = '" + idLabL
						+ "' AND HAL_TIPO_ARCHIVO = 290";
				listaReporte2 = obtenerMapa(sql);
				registros = listaReporte2.size();
				if (registros == 0 && laboratorioActual.getGestionRegistroIca().equals(50L)) {
					completitud += -0.3F;
					datosCompletitud += "- [4-GESTIÓN] (Documento Registro ICA)\r\n";
				}

				// Licencias
				if (esNuloVacio(laboratorioActual.getGestionLicencias())) {
					completitud += -0.4F;
					datosCompletitud += "- [4-GESTIÓN] (Licencias)\r\n";
				}

				// Licencias Norma
				if (esNuloVacio(laboratorioActual.getGestionLicenciasCual())
						&& laboratorioActual.getGestionLicencias().equals(50L)) {
					completitud += -0.3F;
					datosCompletitud += "- [4-GESTIÓN] (Licencias ¿cuál?)\r\n";
				}

				// Documento Licencias
				sql = "SELECT HAL_ID AS A, HAL_LAB_ID AS B FROM HER_ARCHIVO_LABORATORIO WHERE HAL_LAB_ID = '" + idLabL
						+ "' AND HAL_TIPO_ARCHIVO = 288";
				listaReporte2 = obtenerMapa(sql);
				registros = listaReporte2.size();
				if (registros == 0 && laboratorioActual.getGestionLicencias().equals(50L)) {
					completitud += -0.3F;
					datosCompletitud += "- [4-GESTIÓN] (Documento Licencias)\r\n";
				}

				// (5) ********************* EQUIPOS **************************************************

				// Total equipos laboratorios / Equipos Registrados
				sql = "SELECT LDE_ID AS A, LAB_ID AS B FROM HER_LABORATORIO_DETALLE_EQUIPO WHERE LAB_ID = '" + idLabL
						+ "' AND LDE_DADO_DE_BAJA = 0";
				listaReporte2 = obtenerMapa(sql);
				registros = listaReporte2.size();
				if (esNuloVacio(laboratorioActual.getTotalEquipos()) || laboratorioActual.getTotalEquipos() <= 0) {
					completitud += -12;
					datosCompletitud += "- [5-EQUIPOS] (Cantidad total de equipos del laboratorio)\r\n";
				} else {
					Float registrosFloat = (float) registros;
					Float totalEquiposFloat = (float) laboratorioActual.getTotalEquipos();
					Float total_vs_eq_registrados = registrosFloat / totalEquiposFloat;
					if (total_vs_eq_registrados < 1) {
						completitud += -((1 - total_vs_eq_registrados) * 12);
						datosCompletitud += "- [5-EQUIPOS] (Equipos registrados en el sistema vs cantidad total de equipos)\r\n";
					}
				}

				// Equipos que requieren mantenimiento
				if (esNuloVacio(laboratorioActual.getTotalEquiposMantto())) {
					completitud += -4;
					datosCompletitud += "- [5-EQUIPOS] (Cantidad equipos que requieren mantenimiento)\r\n";
				}

				// Equipos que requieren calibración
				if (esNuloVacio(laboratorioActual.getTotalEquiposCalib())) {
					completitud += -4;
					datosCompletitud += "- [5-EQUIPOS] (Cantidad equipos que requieren calibración)\r\n";
				}

				// (6) ********************* INVESTIGACIÓN
				// ******************************************************

				// Lineas de investigacion - Actualizado 04/19
				if (laboratorioActual.getDedicacionInvestigacion() > 0
						&& laboratorioActual.getDedicacionInvestigacion() <= 100) {
					// Líneas de investigación - Actualizado 04/19 - Eliminado 25/09/2023 
//					sql = "SELECT LDLI_ID AS A, LAB_ID AS B FROM HER_LABORATORIO_DET_LINEA_INV WHERE LAB_ID = '"
//							+ idLabL + "'";
//					listaReporte2 = obtenerMapa(sql);
//					registros = listaReporte2.size();
//					if (registros == 0) {
//						completitud += -5;
//						datosCompletitud += "- [6-INVESTIGACION] (Líneas de investigación)\r\n";
//					}

					// Áreas temáticas - Eliminado 04/19
					// sql = "SELECT CLC_ID AS A, LAB_ID AS B FROM
					// HER_LABORATORIO_CLASIF_CONOC WHERE LAB_ID = '" + idLabL
					// + "'";
					// System.out.println("sql: " + sql);
					// listaReporte2 = obtenerMapa(sql);
					// registros = listaReporte2.size();
					// if (registros == 0) {
					// completitud += -3.3F;
					// datosCompletitud += "- [6-INVESTIGACION] (Áreas
					// temáticas)\r\n";
					// }

					// Proyectos de investigación - Actualizado 04/19
					/*sql = "SELECT LDP_ID AS A, LAB_ID AS B FROM HER_LABORATORIO_DET_PROYECTO WHERE LAB_ID = '" + idLabL
							+ "'";*/
					
					//19/03/2024 se modifica consulta para calcular la completitud en el item de proyectos de laboratorios.
					
					
					sql = "SELECT LP.LAB_ID AS A, LP.PRY_ID AS B "
							+ "FROM HER_LABORATORIO_PROYECTO LP, HER_PROYECTO PRY "
							+ "WHERE PRY.PRY_ID = LP.PRY_ID AND PRY.EPR_ID IN ('AP','A','CN','S','PF','F') AND LP.LAB_ID = '" + idLabL	+ "'";
		
					listaReporte2 = obtenerMapa(sql);
					registros = listaReporte2.size();
					if (registros == 0) {
						completitud += -4;
						datosCompletitud += "- [6-PROYECTOS] (Proyectos de investigación)\r\n";
					}
				}

				// (7) ********************* DOCENCIA *************************************************

				if (laboratorioActual.getDedicacionDocencia() > 0 && laboratorioActual.getDedicacionDocencia() <= 100) {
					// Asignaturas asociadas
					sql = "SELECT LDD_ID AS A, LAB_ID AS B FROM HER_LABORATORIO_DET_DOCENCIA WHERE LAB_ID = '" + idLabL
							+ "'";
					listaReporte2 = obtenerMapa(sql);
					registros = listaReporte2.size();
					if (registros == 0) {
						completitud += -5;
						datosCompletitud += "- [8-DOCENCIA] (Asignaturas asociadas)\r\n";
					}
				}

				// (8) ********************* ENSAYOS Y SERVICIOS
				// ******************************************************

				// Ensayos asociados
				sql = "SELECT LDENS_ID AS A, LAB_ID AS B FROM HER_LABORATORIO_DETALLE_ENSAYO WHERE LAB_ID = '" + idLabL
						+ "'";
				listaReporte2 = obtenerMapa(sql);
				registros = listaReporte2.size();
				if (registros == 0) {
					completitud += -8;
					datosCompletitud += "- [9-SERVICIOS] (Ensayos asociados)\r\n";
				}

				// Horario de atención
				if (esNuloVacio(laboratorioActual.getHorarioAtencion())) {
					completitud += -1;
					datosCompletitud += "- [9-SERVICIOS] (Horario de atención)\r\n";
				}

				// Usuarios actuales
				if (esNuloVacio(laboratorioActual.getEnsayosUsuariosActuales())) {
					completitud += -1;
					datosCompletitud += "- [9-SERVICIOS] (Usuarios actuales)\r\n";
				}

				// Usuarios potenciales
				if (esNuloVacio(laboratorioActual.getEnsayosUsuariosPotenciales())) {
					completitud += -1;
					datosCompletitud += "- [9-SERVICIOS] (Usuarios potenciales)\r\n";
				}
				
				// Acto administrativo tarifas
				sql = "SELECT HAL_ID AS A, HAL_LAB_ID AS B FROM HER_ARCHIVO_LABORATORIO WHERE HAL_LAB_ID = '" + idLabL
						+ "' AND HAL_TIPO_ARCHIVO = 1134";
				listaReporte2 = obtenerMapa(sql);
				registros = listaReporte2.size();
				if (registros == 0 || esNuloVacio(laboratorioActual.getTipoActoAdminTarifas())
						|| esNuloVacio(laboratorioActual.getNumeroActoAdminTarifas())
						|| esNuloVacio(laboratorioActual.getFechaActoAdminTarifas())
						|| esNuloVacio(laboratorioActual.getEmitidaActoAdminTarifas())) {
					completitud += -2;
					datosCompletitud += "- [9-SERVICIOS] (Acto administrativo tarifas)\r\n";
				}
				
				// Brochure
				sql = "SELECT HAL_ID AS A, HAL_LAB_ID AS B FROM HER_ARCHIVO_LABORATORIO WHERE HAL_LAB_ID = '" + idLabL
						+ "' AND HAL_TIPO_ARCHIVO = 1133";
				listaReporte2 = obtenerMapa(sql);
				registros = listaReporte2.size();
				if (registros == 0 || esNuloVacio(laboratorioActual.getLinkBrochure())) {
					completitud += -2;
					datosCompletitud += "- [9-SERVICIOS] (Portafolio servicios)\r\n";
				}
				
				// Condiciones prestacion servicio
				sql = "SELECT HAL_ID AS A, HAL_LAB_ID AS B FROM HER_ARCHIVO_LABORATORIO WHERE HAL_LAB_ID = '" + idLabL
						+ "' AND HAL_TIPO_ARCHIVO = 7654";
				listaReporte2 = obtenerMapa(sql);
				registros = listaReporte2.size();
				if (registros == 0 || esNuloVacio(laboratorioActual.getLinkCondiciones())) {
					completitud += -2;
					datosCompletitud += "- [9-SERVICIOS] (Condiciones prestacion servicio)\r\n";
				}
				
				

				// ================================================================================================================

				if (datosCompletitud.length() > 4000) {
					datosCompletitud = datosCompletitud.substring(0, 3999);
				}

				laboratorioActual.setPorcentajeCompletitud(completitud);
				laboratorioActual.setInformacionFaltante(datosCompletitud);
				guardarObjeto(laboratorioActual);
				System.out.println("Lab: " + laboratorioActual + " ! " + completitud + " | " + datosCompletitud);

			} catch (Exception e) {
				System.out.println("ERROR calcularCompletitud: [ManejadorLaboratorios]" + e.getStackTrace());
				laboratorioActual.setPorcentajeCompletitud(null);

			}
		}
	}

	public List obtenerEventosInvestigador(String tipo, String doc) throws DataAccessException {
		List eventosInv = new ArrayList();
		List result = new ArrayList<InvestigadorEvento>();

		Session session = getSession();

		try {
		String query = "select INE_ID as idEvento, INE_NOMBRE_EVENTO as nombreEvento, INE_FECHA as fechaEvento, INE_TITULO_TRABAJO as tituloTrabajo,"
				+ " p.PAIS_ID as idPais, p.PAIS_NOMBRE as nombrePais, p.PAIS_SIGLA as siglaPais, INE_CIUDAD_PAIS as ciudadEvento"
				+ " from HER_INVESTIGADOR_EVENTO ie, HER_PAIS p" + " where  ie.TDO_ID = '" + tipo
				+ "' and ie.INV_ID = '" + doc + "' and p.PAIS_ID = ie.PAIS_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		eventosInv = sqlQuery1.addScalar("idEvento", Hibernate.LONG).addScalar("nombreEvento", Hibernate.STRING)
				.addScalar("fechaEvento", Hibernate.DATE).addScalar("tituloTrabajo", Hibernate.STRING)
				.addScalar("idPais", Hibernate.STRING).addScalar("nombrePais", Hibernate.STRING)
				.addScalar("siglaPais", Hibernate.STRING).addScalar("ciudadEvento", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = eventosInv.iterator();
		List listaEventos;

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			InvestigadorEvento ie = new InvestigadorEvento();

			ie.setId((Long) name[0]);
			ie.setNombre((String) name[1]);
			ie.setFecha((Date) name[2]);
			ie.setTituloTrabajo((String) name[3]);
			ie.setCiudadPais((String) name[7]);
			Pais p = new Pais();
			p.setId((String) name[4]);
			p.setNombre((String) name[5]);
			p.setSigla((String) name[6]);
			ie.setPais(p);

			IdPersona idp = new IdPersona();
			idp.setTipoDocumento(tipo);
			idp.setDocumento(doc);
			InvestigadorInterno ii = new InvestigadorInterno();
			ii.setId(idp);
			ie.setInvestigador(ii);

			result.add(ie);
		}
		return result;
	}

	public List obtenerEnlacesInvestigador(String tipo, String doc) throws DataAccessException {
		List enlacesInv = new ArrayList();
		List result = new ArrayList<InvestigadorEvento>();

		Session session = getSession();

		try {
		String query = "select IEN_ID as idEnlace, IEN_NOMBRE_ENLACE as nombreEnlace, IEN_LINK as linkEnlace"
				+ " from HER_INVESTIGADOR_ENLACE ien" + " where  ien.TDO_ID = '" + tipo + "' and ien.INV_ID = '" + doc
				+ "'";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		enlacesInv = sqlQuery1.addScalar("idEnlace", Hibernate.LONG).addScalar("nombreEnlace", Hibernate.STRING)
				.addScalar("linkEnlace", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = enlacesInv.iterator();
		List listaEventos;

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			InvestigadorEnlace ien = new InvestigadorEnlace();

			ien.setId((Long) name[0]);
			ien.setNombre((String) name[1]);
			ien.setLink((String) name[2]);

			IdPersona idp = new IdPersona();
			idp.setTipoDocumento(tipo);
			idp.setDocumento(doc);
			InvestigadorInterno ii = new InvestigadorInterno();
			ii.setId(idp);
			ien.setInvestigador(ii);

			result.add(ien);
		}
		return result;
	}

	public List obtenerPublicacionesInvestigador(String tipo, String doc) throws DataAccessException {
		List publicacionesInv = new ArrayList();
		List result = new ArrayList<InvestigadorPublicacion>();

		Session session = getSession();

		try {
		String query = "select IPU_ID as idPublicacion, IPU_TIPO as tipoPublicacion, IPU_PUBLICACION as nombrePublicacion, IPU_AUTORES as autores"
				+ " from HER_INVESTIGADOR_PUBLICACION ipu" + " where  ipu.TDO_ID = '" + tipo + "' and ipu.INV_ID = '"
				+ doc + "'";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		publicacionesInv = sqlQuery1.addScalar("idPublicacion", Hibernate.LONG)
				.addScalar("tipoPublicacion", Hibernate.STRING).addScalar("nombrePublicacion", Hibernate.STRING)
				.addScalar("autores", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = publicacionesInv.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			InvestigadorPublicacion ipu = new InvestigadorPublicacion();

			ipu.setId((Long) name[0]);
			ipu.setTipo((String) name[1]);
			ipu.setPublicacion((String) name[2]);
			ipu.setAutores((String) name[3]);

			IdPersona idp = new IdPersona();
			idp.setTipoDocumento(tipo);
			idp.setDocumento(doc);
			InvestigadorInterno ii = new InvestigadorInterno();
			ii.setId(idp);
			ipu.setInvestigador(ii);

			result.add(ipu);
		}
		return result;
	}

	public List obtenerAreasInvestigacionInvestigador(String tipo, String doc) throws DataAccessException {
		List areasInvestigacionInv = new ArrayList();
		List result = new ArrayList<InvestigadorAreaInvestigacion>();

		Session session = getSession();
		try {
		String query = "select IAI_ID as idAreaInvestigador, dd.DOMDET_DESCRIPCION as areaInteres, iai.DOM_ID as dominio, iai.DOMDET_TIPO as dominioDetalle"
				+ " from HER_INVESTIGADOR_AREA_INVESTIG iai, HER_DOMINIO_DETALLE dd" + " where  iai.TDO_ID = '" + tipo
				+ "' and iai.INV_ID = '" + doc + "' and dd.DOM_ID = iai.DOM_ID and dd.DOMDET_TIPO = iai.DOMDET_TIPO";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		areasInvestigacionInv = sqlQuery1.addScalar("idAreaInvestigador", Hibernate.LONG)
				.addScalar("areaInteres", Hibernate.STRING).addScalar("dominio", Hibernate.STRING)
				.addScalar("dominioDetalle", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = areasInvestigacionInv.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			InvestigadorAreaInvestigacion iai = new InvestigadorAreaInvestigacion();

			iai.setId((Long) name[0]);

			IdDominioDetalle idd = new IdDominioDetalle();
			idd.setId((String) name[2]);
			idd.setTipo((String) name[3]);

			DominioDetalle dd = new DominioDetalle();
			dd.setIdentificador(idd);

			iai.setArea(dd);
			iai.setNombreArea((String) name[1]);

			IdPersona idp = new IdPersona();
			idp.setTipoDocumento(tipo);
			idp.setDocumento(doc);
			InvestigadorInterno ii = new InvestigadorInterno();
			ii.setId(idp);
			iai.setInvestigador(ii);

			result.add(iai);
		}
		return result;
	}

	public List obtenerAreasInteresInvestigador(String tipo, String doc) throws DataAccessException {
		List areasInteresInv = new ArrayList();
		List result = new ArrayList<InvestigadorAreaInvestigacion>();

		Session session = getSession();
		try {
		String query = "select IAIN_ID as idAreaInvestigador, iai.IAIN_NOMBRE_AREA_INT as areaInteres"
				+ " from HER_INVESTIGADOR_AREA_INTERES iai" + " where  iai.TDO_ID = '" + tipo + "' and iai.INV_ID = '"
				+ doc + "'";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		areasInteresInv = sqlQuery1.addScalar("idAreaInvestigador", Hibernate.LONG)
				.addScalar("areaInteres", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = areasInteresInv.iterator();
		List listaPublicaciones;

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			InvestigadorAreaInteres iai = new InvestigadorAreaInteres();

			iai.setId((Long) name[0]);

			iai.setNombreAreaInteres((String) name[1]);

			IdPersona idp = new IdPersona();
			idp.setTipoDocumento(tipo);
			idp.setDocumento(doc);
			InvestigadorInterno ii = new InvestigadorInterno();
			ii.setId(idp);
			iai.setInvestigador(ii);

			result.add(iai);
		}
		return result;
	}

	public List obtenerNombreLaboratoriosInvestigador(String tipo, String doc) throws DataAccessException {
		List LaboratoriosInv = new ArrayList();
		List result = new ArrayList<Laboratorio>();

		Session session = getSession();
		try {
		String query = "select lab.LAB_ID as idLaboratorio, lab.LAB_NOMBRE as nombreLaboratorio"
				+ " from HER_LABORATORIO lab, HER_PERSONA_LABORATORIO plab" + " where  plab.TDO_ID = '" + tipo
				+ "' and plab.PER_ID = '" + doc + "' and plab.LAB_ID = lab.LAB_ID and lab.LAB_ACTIVO <> '0'";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		LaboratoriosInv = sqlQuery1.addScalar("idLaboratorio", Hibernate.LONG)
				.addScalar("nombreLaboratorio", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = LaboratoriosInv.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Laboratorio lab = new Laboratorio();

			lab.setId((Long) name[0]);
			lab.setNombre((String) name[1]);

			result.add(lab);
		}
		return result;
	}

	public List<PersonaRol> obtenerPersonaRolXIdPersona(String tipoDoc, String numDoc, String rol)
			throws DataAccessException {
		String query = "from PersonaRol pr " + "where pr.tipoDocumento = '" + tipoDoc + "' and pr.documento = '"
				+ numDoc + "' and pr.nombre = '" + rol + "' order by pr.documento";

		return getHibernateTemplate().find(query);
	}

	public List<PersonaRol> obtenerPersonaRolXIdPersonaLaboratorios(String tipoDoc, String numDoc)
			throws DataAccessException {
		String query = "from PersonaRol pr " + "where pr.tipoDocumento = '" + tipoDoc + "' and pr.documento = '"
				+ numDoc
				+ "' and pr.nombre IN ('CO','CT','TL','DC','DT','DL','LS','LF','LD','CL','EV','IL','DS') order by pr.documento";
		System.out.println("obtenerPersonaRolXIdPersonaLaboratorios query: " + query);
		return getHibernateTemplate().find(query);
	}

	// public List<PersonaRol> obtenerListaPersonaRolXidRol(String idRol) throws
	// DataAccessException {
	// String query = "FROM PersonaRol PR WHERE PR.nombre = '" + idRol + "' "+
	// new Date() +" <= PR.fechaFinRol order by PR.nombre";
	// return getHibernateTemplate().find(query);
	// }

	public List<LaboratorioLogLaboratorios> obtenerHistoricoEstadosLaboratorio(Long labId) throws DataAccessException {
		return getHibernateTemplate().find("from LaboratorioLogLaboratorios lll " + "where lll.laboratorio.id = '"
				+ labId + "' order by lll.fechaRegistro desc");
	}

	public List<LaboratorioLogEquipos> obtenerHistoricoEstadosEquipo(Long equipoId) throws DataAccessException {
		return getHibernateTemplate().find("from LaboratorioLogEquipos lle " + "where lle.equipo.id = '" + equipoId
				+ "' order by lle.fechaRegistro desc");
	}

	public List<LaboratorioLogActividades> obtenerHistoricoEstadosActividad(Long actividadId)
			throws DataAccessException {
		return getHibernateTemplate().find("from LaboratorioLogActividades lla " + "where lla.actividad.id = '"
				+ actividadId + "' order by lla.fechaRegistro desc");
	}

	public List obtenerAsignaturasInvestigador(String tipo, String doc) throws DataAccessException {
		List asignaturasInv = new ArrayList();
		List result = new ArrayList<InvestigadorAsignatura>();

		Session session = getSession();

		try {
		String query = "select asi.ASI_ID as idAsignatura, d.dpn_nombre as nombreDepartamento, asi.COD_ASIGNATURA as codigo,"
				+ " asi.NMBRE_ASIGNATURA as nombreAsi, asi.NIVEL as nivel, asi.ASI_ID as idAsignaturaHermes, ia.IASIG_ID as invAsig"
				+ " from SIA_V_ASIGNATURAS_HERMES asi left join HER_DEPENDENCIA d on d.dpn_id = asi.uab, HER_INVESTIGADOR_ASIGNATURA ia"
				+ " where  ia.TDO_ID = '" + tipo + "' and ia.INV_ID = '" + doc + "' and ia.ASI_ID = asi.ASI_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		asignaturasInv = sqlQuery1.addScalar("idAsignatura", Hibernate.LONG)
				.addScalar("nombreDepartamento", Hibernate.STRING).addScalar("codigo", Hibernate.STRING)
				.addScalar("nombreAsi", Hibernate.STRING).addScalar("nivel", Hibernate.STRING)
				.addScalar("idAsignaturaHermes", Hibernate.LONG).addScalar("invAsig", Hibernate.LONG).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = asignaturasInv.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			InvestigadorAsignatura ia = new InvestigadorAsignatura();
			Dependencia uab = new Dependencia();
			VAsignaturasSIA asia = new VAsignaturasSIA();
			asia.setId((Long) name[0]);
			uab.setNombre((String) name[1]);
			asia.setUab(uab);
			asia.setCodAsignatura((String) name[2]);
			asia.setNombreAsignatura((String) name[3]);
			asia.setNivel((String) name[4]);

			ia.setId((Long) name[6]);
			ia.setAsignatura(asia);
			ia.setMateria((Long) name[5]);

			IdPersona idp = new IdPersona();
			idp.setTipoDocumento(tipo);
			idp.setDocumento(doc);
			InvestigadorInterno ii = new InvestigadorInterno();
			ii.setId(idp);
			ia.setInvestigador(ii);

			result.add(ia);
		}
		return result;
	}

	public List obtenerObraExposicionInvestigador(String tipo, String doc) throws DataAccessException {
		List obrasInv = new ArrayList();
		List result = new ArrayList<InvestigadorObraExposicion>();

		Session session = getSession();
		try {
		String query = "select ioe.IOE_ID as idObra, ioe.IOE_NOMBRE_EXPOSICION as nombreExposicion, ioe.IOE_NOMBRE_OBRA as nombreObra, p.PAIS_NOMBRE as pais, ioe.IOE_CIUDAD_PAIS as ciudad,"
				+ " ioe.IOE_ORGANIZADOR as organizador, ioe.IOE_FECHA_EXPOSICION as fechaExposicion, ioe.IOE_FECHA_OBRA as fechaObra, ioe.PAIS_ID as codigoPais"
				+ " from HER_INVESTIGADOR_OBRA_EXP ioe left join HER_PAIS p on p.PAIS_ID = ioe.PAIS_ID"
				+ " where  ioe.TDO_ID = '" + tipo + "' and ioe.INV_ID = '" + doc + "'";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		obrasInv = sqlQuery1.addScalar("idObra", Hibernate.LONG).addScalar("nombreObra", Hibernate.STRING)
				.addScalar("pais", Hibernate.STRING).addScalar("organizador", Hibernate.STRING)
				.addScalar("fechaExposicion", Hibernate.DATE).addScalar("codigoPais", Hibernate.STRING)
				.addScalar("nombreExposicion", Hibernate.STRING).addScalar("fechaObra", Hibernate.DATE)
				.addScalar("ciudad", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = obrasInv.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			InvestigadorObraExposicion ioe = new InvestigadorObraExposicion();

			ioe.setId((Long) name[0]);
			ioe.setNombreObra((String) name[1]);
			ioe.setNombreExposicion((String) name[6]);
			Pais lugar = new Pais();
			lugar.setId((String) name[5]);
			lugar.setNombre((String) name[2]);
			ioe.setPais(lugar);
			ioe.setOrganizador((String) name[3]);

			ioe.setFechaExposicion((Date) name[4]);
			ioe.setFechaObra((Date) name[7]);
			IdPersona idp = new IdPersona();
			idp.setTipoDocumento(tipo);
			idp.setDocumento(doc);
			InvestigadorInterno ii = new InvestigadorInterno();
			ii.setId(idp);
			ioe.setInvestigador(ii);
			ioe.setCiudadPais((String) name[8]);

			if (ioe.getNombreExposicion() == null || ioe.getNombreExposicion().equals("")) {
				ioe.setObra(true);
			} else {
				ioe.setObra(false);
			}

			result.add(ioe);
		}
		return result;
	}
	
	public List obtenerLineasInvestigacionInvestigador(String tipo, String doc) throws DataAccessException {
		List lineasInv = new ArrayList();
		List result = new ArrayList<InvestigadorLineaInvestigacion>();

		Session session = getSession();

		try {
		String query = "select il.ILI_ID as id, il.LIN_ID as linea, li.LIN_NOMBRE as nombre "
				+ " from HER_INVESTIGADOR_LINEA il left join HER_LINEA_INVESTIGACION li on li.LIN_ID = il.LIN_ID"
				+ " where  il.TDO_ID = '" + tipo + "' and il.INV_ID = '" + doc + "'";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		lineasInv = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("linea", Hibernate.STRING)
				.addScalar("nombre", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = lineasInv.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();

			
			IdPersona idp = new IdPersona();
			idp.setTipoDocumento(tipo);
			idp.setDocumento(doc);
			Investigador in = new InvestigadorInterno();
			in.setId(idp);
			
			InvestigadorLineaInvestigacion invLinea = new InvestigadorLineaInvestigacion();
			invLinea.setId((Long) name[0]);
			LineaInvestigacion i = new LineaInvestigacion();
			String idLinea = (String) name[1];
			i.setId(Long.parseLong(idLinea));
			i.setNombre((String) name[2]);
			invLinea.setLinea(i);
			invLinea.setInvestigador(in);
			

			result.add(invLinea);
		}
		return result;
	}

	public List obtenerSolicitudesAprobadasUnidadAdministrativa(String dependencias) throws DataAccessException {
		List result = new ArrayList();
		List solicitudes = new ArrayList();

		Session session = getSession();

		try {
		String query = "Select S.SOL_ID idSolicitud, P.PRY_ID idProyecto, P.PRY_NOMBRE nombreProyecto, S.SOL_FECHA fechaSolicitud,"
				+ " TS.TSO_NOMBRE nombreTipo from HER_SOLICITUD S, HER_TIPO_SOLICITUD TS,"
				+ "	HER_PROYECTO P, HER_INVESTIGADOR_PROYECTO IP, HER_INVESTIGADOR_INTERNO II, HER_DEPENDENCIA DEPA "
				+ "WHERE S.PRY_ID = P.PRY_ID AND IP.PRY_ID = P.PRY_ID AND IP.INP_TIPO = 'P' AND IP.INV_ID = II.INV_ID AND IP.TDO_ID = II.TDO_ID "
				+ "AND II.DPN_ID = DEPA.DPN_ID AND DEPA.DPN_FACULTAD IN ('" + dependencias
				+ "') AND S.SOL_RESPUESTA = 'A' AND S.TSO_ID IN ('23','24','7','6','22','28')  "
				+ "AND TS.TSO_ID = S.TSO_ID AND S.SOL_VISTA_UA IS NULL ORDER BY S.SOL_ID, P.PRY_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		solicitudes = sqlQuery1.addScalar("idSolicitud", Hibernate.LONG).addScalar("idProyecto", Hibernate.LONG)
				.addScalar("nombreProyecto", Hibernate.STRING).addScalar("nombreTipo", Hibernate.STRING)
				.addScalar("fechaSolicitud", Hibernate.DATE).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = solicitudes.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Solicitud s = new Solicitud();
			s.setId((Long) name[0]);
			Proyecto proyecto = new Proyecto();
			proyecto.setId((Long) name[1]);
			proyecto.setNombre((String) name[2]);
			s.setProyecto(proyecto);
			TipoSolicitud ts = new TipoSolicitud();
			ts.setNombre((String) name[3]);
			s.setTipoSolicitud(ts);
			s.setFecha((Date) name[4]);
			result.add(s);
		}
		return result;
	}

	public List obtenerConvocatoriaMovilidades(String convocatoria) throws DataAccessException {
		List result = new ArrayList();
		List convocatorias = new ArrayList();

		Session session = getSession();

		try {
		String query = "Select CP.DNP_ID AS idDependencia, CP.CNP_ID idPadre from HER_CONVOCATORIA C, HER_CONVOCATORIA_PADRE CP "
				+ "WHERE C.CON_ID = '" + convocatoria + "' and C.CNP_ID = CP.CNP_ID";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		convocatorias = sqlQuery1.addScalar("idDependencia", Hibernate.STRING)
				.addScalar("idPadre", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = convocatorias.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Convocatoria conv = new Convocatoria();
			ConvocatoriaPadre convocatoriaPadre = new ConvocatoriaPadre();
			Dependencia dependencia = new Dependencia();
			dependencia.setId((String) name[0]);
			convocatoriaPadre.setDependencia(dependencia);
			conv.setPadre(convocatoriaPadre);
			result.add(conv);
		}
		return result;
	}

	public Long obtenerTotalListaOtrosBoletin() throws DataAccessException {

		Long totalOtros = 0L;

		Session session = getSession();

		try {
		String query = "select count(*) as total from her_correo_otros";
		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		totalOtros = (Long) sqlQuery1.addScalar("total", Hibernate.LONG).uniqueResult();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		return totalOtros;

	}

	/**
	 * @param sql
	 *            la sentencia en SQL a ejecutar, de la forma: SELECT XXX AS
	 *            XXX, YYY AS YYY, ... FROM ... WHERE ...
	 * @return Lista de Mapas
	 */
	public List<Map> obtenerMapa(String sql) {
		Session session = getSession();
		List<Map> listaMapas = new ArrayList<Map>();
		
		try {
		
		SQLQuery sqlQuery = session.createSQLQuery(sql);

		String select = "SELECT ";
		String as = " AS ";
		int inicioAlias = sql.indexOf(select) + select.length();
		int finAlias = sql.indexOf("FROM");
		String alias = sql.trim().substring(inicioAlias, finAlias);
		alias = alias.replace(" as ", " AS ");
		// alias DEBE tener un espacio en blanco al final
		String[] aliases = new String[100];
		int h = 0;
		for (int j = 1; j < alias.length() - as.length(); j++) {
			if (alias.substring(j, j + as.length()).contains(as)) {
				String nombreAlias = alias.substring(j + as.length(), alias.length());
				bucle1: for (int k = 1; k < nombreAlias.length(); k++) {
					char c = nombreAlias.charAt(k);
					if (c == ' ' || c == ',') {
						String aliasS = nombreAlias.substring(0, k);
						nombreAlias = nombreAlias.substring(k, nombreAlias.length()).trim();
						j += aliasS.length();
						aliases[h] = aliasS;
						h++;
						break bucle1;
					}
				}
			}
		}

		String[] finalAlias = new String[h];
		for (int i = 0; i < h; i++) {
			finalAlias[i] = aliases[i];
		}

		for (String aall : finalAlias) {
			sqlQuery.addScalar(aall, Hibernate.STRING);
		}

		List result = new ArrayList();
		result = sqlQuery.list();

		
		
		for (Object row : result) {
			Object[] items = (Object[]) row;
			LinkedHashMap<String, String> node = new LinkedHashMap<String, String>();
			for (int i = 0; i < finalAlias.length; i++) {
				node.put(finalAlias[i], items[i] == null ? "" : items[i].toString());
			}
			listaMapas.add(node);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listaMapas;
	}

	public List<Map> obtenerMapa(String sql, String from) {
		List<Map> listaMapas = new ArrayList<Map>();
		Session session = getSession();
		
		try {
		SQLQuery sqlQuery = session.createSQLQuery(sql);

		String select = "SELECT ";
		String as = " AS ";
		int inicioAlias = sql.indexOf(select) + select.length();
		int finAlias = sql.indexOf(from);
		String alias = sql.trim().substring(inicioAlias, finAlias);
		alias = alias.replace(" as ", " AS ");
		// alias DEBE tener un espacio en blanco al final
		String[] aliases = new String[100];
		int h = 0;

		for (int j = 1; j < alias.length() - as.length(); j++) {
			if (alias.substring(j, j + as.length()).contains(as)) {
				String nombreAlias = alias.substring(j + as.length(), alias.length());
				bucle1: for (int k = 1; k < nombreAlias.length(); k++) {
					char c = nombreAlias.charAt(k);
					if (c == ' ' || c == ',') {
						String aliasS = nombreAlias.substring(0, k);
						nombreAlias = nombreAlias.substring(k, nombreAlias.length()).trim();
						j += aliasS.length();
						aliases[h] = aliasS;
						h++;
						break bucle1;
					}
				}
			}
		}

		String[] finalAlias = new String[h];
		for (int i = 0; i < h; i++) {
			finalAlias[i] = aliases[i];
		}

		for (String aall : finalAlias) {
			sqlQuery.addScalar(aall, Hibernate.STRING);
		}

		List result = new ArrayList();
		result = sqlQuery.list();

		
		for (Object row : result) {
			Object[] items = (Object[]) row;
			LinkedHashMap<String, String> node = new LinkedHashMap<String, String>();
			for (int i = 0; i < finalAlias.length; i++) {
				node.put(finalAlias[i], items[i] == null ? "" : items[i].toString());
			}
			listaMapas.add(node);
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		return listaMapas;
	}

	public List<Instructivo> obtenerInstructivos(String sql) throws DataAccessException {
		List<Object> instructivos = null;
		List<Instructivo> resultados = new ArrayList<Instructivo>();

		Session session = getSession();

		try {
		String query = "select HER_INSTRUCTIVO.INS_ID as idInstructivo, max(HER_INSTRUCTIVO.INS_NOMBRE) as nombreInstructivo, max(HER_INSTRUCTIVO_CLASIFICACION.ICL_ID) as idClasificacion, "
				+ "max(HER_INSTRUCTIVO_CLASIFICACION.ICL_NOMBRE) as nombreClasificacion, max(HER_INSTRUCTIVO.INS_COMPONENTE) as componente, max(ai.ARI_ID) as idArchivoInstructivo, "
				+ "max(ai.ARI_NOMBRE) as nombreArchivo, max(HER_INSTRUCTIVO.INS_DESCRIPCION) as descripcionInstructivo from HER_INSTRUCTIVO left join HER_ARCHIVO_INSTRUCTIVO ai on ai.ins_id = HER_INSTRUCTIVO.ins_id, "
				+ "HER_INSTRUCTIVO_CLASIFICACION  where HER_INSTRUCTIVO_CLASIFICACION.ICL_ESTADO = 'A' and HER_INSTRUCTIVO.INS_ESTADO = 'A' and HER_INSTRUCTIVO_CLASIFICACION.ICL_ID = HER_INSTRUCTIVO.ICL_ID "
				+ sql
				+ " group by HER_INSTRUCTIVO.INS_ID order by componente,nombreClasificacion,idInstructivo asc";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		instructivos = sqlQuery1.addScalar("idInstructivo", Hibernate.LONG)
				.addScalar("nombreInstructivo", Hibernate.STRING).addScalar("idClasificacion", Hibernate.LONG)
				.addScalar("nombreClasificacion", Hibernate.STRING).addScalar("componente", Hibernate.STRING)
				.addScalar("nombreArchivo", Hibernate.STRING).addScalar("descripcionInstructivo", Hibernate.STRING)
				.addScalar("idArchivoInstructivo", Hibernate.LONG).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = instructivos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Instructivo ins = new Instructivo();
			InstructivoClasificacion icl = new InstructivoClasificacion();
			ins.setId((Long) name[0]);
			ins.setNombre((String) name[1]);
			icl.setId((Long) name[2]);
			icl.setNombre((String) name[3]);
			ins.setClasificacion(icl);
			ins.setComponente((String) name[4]);
			String nombreArchivo = (String) name[5];
			String ext = "";
			if (nombreArchivo != null) {
				int index = nombreArchivo.lastIndexOf('.');
				if (index > 0) {
					ext = nombreArchivo.substring(index + 1);
				} else {
					ext = "_";
				}
			}
			ins.setExtensionArchivo(ext.toUpperCase());
			ins.setDescripcion((String) name[6]);
			ins.setIdArchivo((Long) name[7]);
			resultados.add(ins);
		}

		return resultados;
	}

	public CorreoPlantilla obtenerPlantillaCorreoCompleta(Long id) {
		Session session = getSession();
		CorreoPlantilla plantilla = new CorreoPlantilla();

		try {
		plantilla = (CorreoPlantilla) session.createCriteria(CorreoPlantilla.class)
				.setFetchMode("adjunto", FetchMode.JOIN).add(Restrictions.idEq(id)).uniqueResult();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return plantilla;
	}

	public void actualizarInfoAlertaActividadesLab(Long idActividad, Long alertaEnviada, String textoAlerta) {

		Session session = getSession();
		Statement st = null;
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "UPDATE HER_LABORATORIO_ACT_EQUIPO " + "SET " + "HLAE_ALERTA_ENVIADA = " + alertaEnviada
					+ ", " + "HLAE_FECHA_ALERTA = SYSDATE, " + "HLAE_TEXTO_ALERTA = '" + textoAlerta + "' "
					+ "WHERE HLAE_ID = " + idActividad;
			System.out.println(query);
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}
	
	public void insertarInfoAlertaMovilidades(LogAlertasAutomaticas alerta) {

	    Session session = getSession();
	    Statement st = null;
	    try {
	        session = getSession();
	        st = session.connection().createStatement();
	        String query = "INSERT INTO HER_LOG_ALERTAS_AUTOMATICAS (LAA_ID, OBJ_ID, OBJ_TIPO, OBJ_VALUE, LAA_FECHA, OBJ_DESTINATARIOS, OBJ_CUERPO_CORREO) "
	                + "VALUES"
	                + "("
	                + "SEQ_LOG_ALERTAS_AUTOMATICAS.nextval, '"
	                + alerta.getIdObjeto() + "', '"
	                + alerta.getTipoObjeto() + "', '"
	                + alerta.getValorObjeto() + "', "
	                + "TO_DATE('" + alerta.getFecha() + "', 'YYYY-MM-DD'), '"
	                + alerta.getDestinatarios() + "', '"
	                + alerta.getCuerpoCorreo() + "'"
	                + ")";
	        System.out.println("insertarInfoAlertaMovilidades - query: " + query);
	        st.execute(query);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        if (session != null)
	            session.close();
	    }
	}
	
	public void actualizarNotificacionLaboratoriosProyecto(Long idProyecto, Long idLab, Long valor, Long tipo) {

		Session session = getSession();
		Statement st = null;
		
		String nombreCampo = null;
		
		switch (tipo.intValue()) {
            case 358:  
            	nombreCampo = "LAP_NOTIFICA_ADICIONA_APROB";
                break;
//            case 359:  
//            	nombreCampo = "LAP_NOTIFICA_ELIMINA_ACTIVO";
//                break;
            case 360:  
            	nombreCampo = "LAP_NOTIFICA_ADICIONA_ACTIVO";
                break;
            default:
            	nombreCampo = null;
                break;
        }
		
		if(!esNulo(nombreCampo)) {
			try {
				session = getSession();
				st = session.connection().createStatement();
				String query = "UPDATE HER_LABORATORIO_PROYECTO SET " + nombreCampo + " = " + valor
							+ " WHERE LAB_ID = " + idLab 
							+ " AND PRY_ID = " + idProyecto;
				System.out.println(query);
				st.execute(query);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (session != null)
					session.close();
			}
		}
	}
	
	public Boolean consultaNotificacionEnviadaLabsProyecto(Long idProyecto, Long idLab, Long tipo) {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		Boolean enviada = false;
		
		String nombreCampo = null;
		
		switch (tipo.intValue()) {
            case 358:  
            	nombreCampo = "LAP_NOTIFICA_ADICIONA_APROB";
                break;
//            case 359:  
//            	nombreCampo = "LAP_NOTIFICA_ELIMINA_ACTIVO";
//                break;
            case 360:  
            	nombreCampo = "LAP_NOTIFICA_ADICIONA_ACTIVO";
                break;
            default:
            	nombreCampo = null;
                break;
        }
		
		if(!esNulo(nombreCampo)) {
			try {
				st = session.connection().createStatement();
				String query = "SELECT " + nombreCampo + " FROM HER_LABORATORIO_PROYECTO LP WHERE LP.LAB_ID = " + idLab + " AND LP.PRY_ID = " + idProyecto;
				rs = st.executeQuery(query);
				while (rs.next()) {
					enviada = rs.getBoolean(1);
				}
				rs.close();
			} catch (Exception ex) {
				System.out.println(ex.toString());
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
			return enviada;
		}
		return false;
	}

	public void actualizarInfoAlertaRequerimiento(Long idReq) {
		Session session = getSession();
		Statement st = null;
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "UPDATE HER_REQUERIMIENTO SET REQ_ALERTA_ENVIADA = 1 WHERE REQ_ID = " + idReq;
			// System.out.println(query);
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public void actualizarInfoAlertaAval(Long aviId) {
		Session session = getSession();
		Statement st = null;
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "UPDATE HER_AVAL SET AVI_ALERTA316_ENVIADA = 1 WHERE AVI_ID = " + aviId;
			// System.out.println(query);
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}
	
	public void actualizarInfoAlertaProyecto(Long pryId, int opt) {
		Session session = getSession();
		Statement st = null;
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "";
			if (opt == 1) {
				query = "UPDATE HER_PROYECTO SET PRY_ALERTA332_ENVIADA = 1 WHERE PRY_ID = " + pryId;
			} else if (opt == 2) {
				query = "UPDATE HER_PROYECTO SET PRY_ALERTA325_ENVIADA = 1 WHERE PRY_ID = " + pryId;
			}
			// System.out.println(query);
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public List<LaboratorioActividadEquipoInterfazAlerta> obtenerActividadesCandidatasAlertas(Integer diasAntesAlerta) {
		Session session = null;
		Query q = null;
		List<LaboratorioActividadEquipoInterfazAlerta> resultados = new ArrayList<LaboratorioActividadEquipoInterfazAlerta>();

		try {
			String sql2 = "SELECT " + "a.HLAE_ID AS ID_ACTIVIDAD, "
					+ "CASE WHEN hlae_estado_actividad = 'P' THEN 'Programada' WHEN hlae_estado_actividad = 'E' THEN 'Ejecutada' WHEN hlae_estado_actividad = 'L' THEN 'Planeada' else 'ERROR' END AS ESTADO_ACTIVIDAD, "
					+ "t.nombre AS TIPO_ACTIVIDAD, "
					+ "TO_CHAR(HLAE_FECHA_ACTIVIDAD, 'yyyy-MM-dd') AS FECHA_ACTIVIDAD, " + "E.LDE_EQUIPO AS EQUIPO, "
					+ "e.lde_placa AS PLACA, " + "l.lab_nombre AS LABORATORIO, "
					+ "PER.PER_NOMBRE1 || ' ' ||  PER.PER_NOMBRE2 || ' ' ||  PER.PER_APELLIDO1 || ' ' ||  PER.PER_APELLIDO2 AS COORDINADOR, "
					+ "PER.PER_EMAIL AS EMAIL, "
					+ "(select listagg(LPER.PER_EMAIL,';') WITHIN GROUP (ORDER BY LPER.PER_EMAIL) from HER_PERSONA_LABORATORIO lpl INNER JOIN HER_PERSONA LPER ON LPER.PER_ID = lpl.PER_ID AND LPER.TDO_ID = lpl.TDO_ID WHERE lpl.ROL_ID IN ('CT','TL') AND lpl.LAB_ID = e.LAB_ID) AS EMAILS_CO_TL, "
					+ "nvl(A.HLAE_ALERTA_ENVIADA,'0') AS ALERTA_ENVIADA " + "FROM " + "HER_LABORATORIO_ACT_EQUIPO a "
					+ "INNER JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON a.LDE_ID = e.lde_id "
					+ "INNER JOIN HER_LABORATORIO l ON l.lab_id = e.lab_id "
					+ "INNER JOIN HER_PERSONA_LABORATORIO pl ON pl.LAB_ID = l.LAB_ID AND pl.ROL_ID = 'CO' "
					+ "INNER JOIN HER_PERSONA PER ON PER.PER_ID = pl.PER_ID AND PER.TDO_ID = pl.TDO_ID "
					+ "LEFT JOIN HER_TIPOS t ON hlae_tipo_actividad = t.id " + "WHERE " + "e.LDE_DADO_DE_BAJA = '0' " // El																								// baja
					+ "AND l.LAB_ACTIVO = 1 " // Laboratorio activo
					// + "AND l.LAB_ID = '49' " //Laboratorio de pruebas
					+ "AND a.HLAE_ACTIVA = 1 " // Actividad debe estar activa - No borrada
					+ "AND a.HLAE_ESTADO_ACTIVIDAD IN ('P','L') " // Estado // planeada // o // programada
					+ "AND TO_DATE(HLAE_FECHA_ACTIVIDAD) = TO_DATE(SYSDATE) + " + diasAntesAlerta + " " // Se envia 10 dias antes de la fecha de programada o planeada
					+ "ORDER BY LABORATORIO, PLACA, a.HLAE_FECHA_ACTIVIDAD";

			session = getSession();
			SQLQuery sqlQuery = session.createSQLQuery(sql2);

			String select = "SELECT ";
			String as = " AS ";
			int inicioAlias = sql2.indexOf(select) + select.length();
			int finAlias = sql2.indexOf("FROM");
			String alias = sql2.trim().substring(inicioAlias, finAlias);
			alias = alias.replace(" as ", " AS ");
			// alias DEBE tener un espacio en blanco al final
			String[] aliases = new String[100];
			int h = 0;

			for (int j = 1; j < alias.length() - as.length(); j++) {
				if (alias.substring(j, j + as.length()).contains(as)) {
					String nombreAlias = alias.substring(j + as.length(), alias.length());
					bucle1: for (int k = 1; k < nombreAlias.length(); k++) {
						char c = nombreAlias.charAt(k);
						if (c == ' ' || c == ',') {
							String aliasS = nombreAlias.substring(0, k);
							nombreAlias = nombreAlias.substring(k, nombreAlias.length()).trim();
							j += aliasS.length();
							aliases[h] = aliasS;
							h++;
							break bucle1;
						}
					}
				}
			}

			String[] finalAlias = new String[h];
			for (int i = 0; i < h; i++) {
				finalAlias[i] = aliases[i];
			}

			List<Object> listaActividades;

			for (String aall : finalAlias) {
				sqlQuery.addScalar(aall, Hibernate.STRING);
			}

			List result = new ArrayList();
			result = sqlQuery.list();
			session.close();

			Iterator it = result.iterator();
			while (it.hasNext()) {
				Object[] name = (Object[]) it.next();

				LaboratorioActividadEquipoInterfazAlerta laeia = new LaboratorioActividadEquipoInterfazAlerta();
				laeia.setIdActividad((String) name[0]);
				laeia.setEstadoActividad((String) name[1]);
				laeia.setNombreTipoActividad((String) name[2]);
				laeia.setFechaActividad((String) name[3]);
				laeia.setNombreEquipo((String) name[4]);
				laeia.setPlacaEquipo((String) name[5]);
				laeia.setNombreLaboratorio((String) name[6]);
				laeia.setNombreCoordinador((String) name[7]);
				laeia.setEmailCoordinador((String) name[8]);
				laeia.setEmailsOtros((String) name[9]);
				laeia.setAlertaEnviada((String) name[10]);

				resultados.add(laeia);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			q = null;
			if (session != null)
				session.close();
			session = null;
		}
		return resultados;
	}

	public List<AlertasAutomaticasAval> obtenerAvalesAlertas(Integer diasAntesAlerta) {
		List<Object> genAvales;
		List<AlertasAutomaticasAval> resultados = new ArrayList<AlertasAutomaticasAval>();

		Session session = null;
		Query q = null;

		try {
			String sql2 = "select aval.AVI_ID as idAval, PER.PER_NOMBRE1||' '||DECODE(PER.PER_NOMBRE2, PER.PER_NOMBRE2,PER.PER_NOMBRE2,NULL,' ')||' '||PER.PER_APELLIDO1||' '||DECODE(PER.PER_APELLIDO2, PER.PER_APELLIDO2,PER.PER_APELLIDO2,NULL,' ') as principal, "
					+ "per.PER_EMAIL as email, aval.AVI_TITULO as pryTitulo "
					+ "from her_aval aval, her_convocatoria_externa ext, her_persona per "
					+ "where aval.AVI_CONVOCATORIA = to_char(ext.CEX_ID) " + "and aval.AVI_ESTADO = 'D' "
					+ "and aval.AVI_AVALDIRECCION = 'S' " + "and to_date(ext.CEX_FECHA_FINALIZACION) = to_date(sysdate-"
					+ diasAntesAlerta + ") " + "and per.PER_ID = aval.INV_ID "
					+ "and per.TDO_ID = aval.TDO_ID and aval.AVI_ALERTA316_ENVIADA = 0 " + "ORDER BY aval.AVI_ID asc";

			session = getSession();
			SQLQuery sqlQuery = session.createSQLQuery(sql2);

			genAvales = sqlQuery.addScalar("idAval", Hibernate.LONG).addScalar("principal", Hibernate.STRING)
					.addScalar("email", Hibernate.STRING).addScalar("pryTitulo", Hibernate.STRING).list();

			session.close();

			Iterator it = genAvales.iterator();

			while (it.hasNext()) {
				Object[] name = (Object[]) it.next();

				AlertasAutomaticasAval alertaAval = new AlertasAutomaticasAval();
				alertaAval.setAviId((Long) name[0]);
				alertaAval.setInvestigador((String) name[1]);
				alertaAval.setEmailInvestigador((String) name[2]);
				alertaAval.setTituloProyecto((String) name[3]);
				resultados.add(alertaAval);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			q = null;
			if (session != null)
				session.close();
			session = null;
		}
		return resultados;
	}

	public List<Requerimiento> obtenerRequerimientosAlerta() {
		Session session = null;
		Query q = null;
		List<Requerimiento> resultados = new ArrayList<Requerimiento>();

		try {
			String sql2 = "SELECT r.REQ_ID as REQ_ID, r.REQ_TIPO AS REQ_TIPO, "
					+ "dd.DOMDET_ESTADO as DOMDET_ESTADO, nvl(r.REQ_ALERTA_ENVIADA,0) as REQ_ALERTA_ENVIADA, r.REQ_COMENTARIOS_ING as REQ_COMENTARIOS_ING "
					+ "FROM HER_REQUERIMIENTO r "
					+ "INNER JOIN HER_DOMINIO_DETALLE dd ON to_char(dd.DOMDET_TIPO)=to_char(r.REQ_RECURSO) AND dd.DOM_ID='107' AND dd.DOMDET_OBSERVACION='A' "
					+ "WHERE r.REQ_TIPO='Mejora' " + "and r.REQ_DEPENDENCIA!='VRIE' " + "and r.REQ_ESTADO='Asignado' "
					+ "and TO_DATE('" + new SimpleDateFormat("dd/MM/yyyy").format(new Date())
					+ "', 'dd/MM/YYYY')=r.REQ_FECHA_TRAMITE " + "and nvl(r.REQ_ALERTA_ENVIADA,0)!=1 "
					+ "ORDER BY r.REQ_ID";
			session = getSession();
			SQLQuery sqlQuery = session.createSQLQuery(sql2);
			String select = "SELECT ";
			String as = " AS ";
			int inicioAlias = sql2.indexOf(select) + select.length();
			int finAlias = sql2.indexOf("FROM");
			String alias = sql2.trim().substring(inicioAlias, finAlias);
			alias = alias.replace(" as ", " AS ");
			String[] aliases = new String[100];
			int h = 0;

			for (int j = 1; j < alias.length() - as.length(); j++) {
				if (alias.substring(j, j + as.length()).contains(as)) {
					String nombreAlias = alias.substring(j + as.length(), alias.length());
					bucle1: for (int k = 1; k < nombreAlias.length(); k++) {
						char c = nombreAlias.charAt(k);
						if (c == ' ' || c == ',') {
							String aliasS = nombreAlias.substring(0, k);
							nombreAlias = nombreAlias.substring(k, nombreAlias.length()).trim();
							j += aliasS.length();
							aliases[h] = aliasS;
							h++;
							break bucle1;
						}
					}
				}
			}
			String[] finalAlias = new String[h];
			for (int i = 0; i < h; i++) {
				finalAlias[i] = aliases[i];
			}
			for (String aall : finalAlias) {
				sqlQuery.addScalar(aall, Hibernate.STRING);
			}
			List result = new ArrayList();
			result = sqlQuery.list();
			session.close();
			Iterator it = result.iterator();
			while (it.hasNext()) {
				Object[] name = (Object[]) it.next();
				Requerimiento req = new Requerimiento();
				req.setId(Long.parseLong((String) name[0]));
				req.setTipo((String) name[1]);
				req.setIngenieroAsignado((String) name[2]);
				req.setAlertaEnviada(((String) name[3]) == "1");
				req.setComentariosIngeniero((String) name[4]);
				resultados.add(req);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			q = null;
			if (session != null)
				session.close();
			session = null;
		}
		return resultados;
	}

	public List<AlertasAutoProyectosConvenios> obtenerProyectosConveniosFinalizacionAlertas(Integer diasAntesAlerta) {
		List<Object> genProyectos;
		List<AlertasAutoProyectosConvenios> resultados = new ArrayList<AlertasAutoProyectosConvenios>();

		Session session = null;
		Query q = null;

		try {
			String sql = "SELECT p.PRY_ID as idProyecto, " + "p.PRY_NOMBRE pryTitulo, "
					+ "add_months(c.cnv_fecha_fin, nvl(ppr.meses, 0)) as convFechaFin, "
					+ "pDocente.PER_EMAIL AS emailDocente, " + "pCoord.PER_EMAIL AS emailCoordinador "
					+ "FROM HER_PROYECTO p INNER JOIN HER_CONVENIO c ON p.PRY_ID = c.PRY_ID "
					+ "INNER JOIN HER_INVESTIGADOR_PROYECTO ip ON p.PRY_ID = ip.PRY_ID AND ip.INP_TIPO = 'P' "
					+ "INNER JOIN HER_PERSONA pDocente ON ip.INV_ID = pDocente.PER_ID "
					+ "INNER JOIN HER_SEG_PROYECTO_PERSONA pp ON p.PRY_ID = pp.PRY_ID "
					+ "LEFT JOIN ( SELECT pry_id, SUM(nvl(prpr_meses_vista_aux, 0)) meses FROM "
					+ " her_proyecto_prorroga WHERE nvl(prpr_estado, 'A') != 'B' GROUP BY pry_id"
					+ ") ppr ON ppr.pry_id = p.pry_id" + " INNER JOIN HER_PERSONA pCoord ON pp.PER_ID = pCoord.PER_ID "
					+ "WHERE p.EPR_ID = 'A'  AND p.PRY_ALERTA325_ENVIADA = 0 "
					+ "AND (to_date(add_months(c.cnv_fecha_fin, nvl(ppr.meses, 0)), 'dd/mm/yyyy') - to_date(SYSDATE, 'dd/mm/yyyy')) = "
					+ diasAntesAlerta;

			session = getSession();
			System.out.println("sql: " + sql);
			SQLQuery sqlQuery = session.createSQLQuery(sql);

			genProyectos = sqlQuery.addScalar("idProyecto", Hibernate.LONG).addScalar("pryTitulo", Hibernate.STRING)
					.addScalar("convFechaFin", Hibernate.DATE).addScalar("emailDocente", Hibernate.STRING)
					.addScalar("emailCoordinador", Hibernate.STRING).list();

			session.close();

			Iterator it = genProyectos.iterator();

			while (it.hasNext()) {
				Object[] name = (Object[]) it.next();

				AlertasAutoProyectosConvenios alertaConv = new AlertasAutoProyectosConvenios();
				alertaConv.setProyectoId((Long) name[0]);
				alertaConv.setTituloProyecto((String) name[1]);
				alertaConv.setFechaFinConvenio((Date) name[2]);
				alertaConv.setEmailInvestigador((String) name[3]);
				alertaConv.setEmailCoordinador((String) name[3]);
				resultados.add(alertaConv);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			q = null;
			if (session != null)
				session.close();
			session = null;
		}
		return resultados;
	}

	public List<AlertasAutoProyectos> obtenerProyectosFechaFinalizacionAlertasCoord(Integer diasAntesAlerta) {
		List<Object> genProyectos;
		List<AlertasAutoProyectos> resultados = new ArrayList<AlertasAutoProyectos>();

		Session session = null;
		Query q = null;

		try {
			String sql = "SELECT p.PRY_ID as idProyecto, " + "p.PRY_NOMBRE pryTitulo, "
					+ "(ADD_MONTHS(p.PRY_FECHA_TENTATIVA_INICIO, p.PRY_DURACION_ACUMULADA) + p.PRY_DURACION_DIAS_ACUMULADA) fechaFinalProyecto, "
					+ "PDOCENTE.PER_NOMBRE1 || ' ' || nvl(pdocente.PER_NOMBRE2,'') || ' ' || pdocente.PER_APELLIDO1 || ' ' || nvl(pdocente.PER_APELLIDO2,'') nombreDocentePrincipal, "
					+ "pCoord.PER_EMAIL AS emailCoordinador "
					+ "FROM HER_PROYECTO p INNER JOIN HER_INVESTIGADOR_PROYECTO ip ON p.PRY_ID = ip.PRY_ID AND ip.INP_TIPO = 'P' "
					+ "INNER JOIN HER_PERSONA pDocente ON ip.INV_ID = pDocente.PER_ID "
					+ "INNER JOIN HER_SEG_PROYECTO_PERSONA pp ON p.PRY_ID = pp.PRY_ID "
					+ "INNER JOIN HER_PERSONA pCoord ON pp.PER_ID = pCoord.PER_ID " + "WHERE p.EPR_ID = 'A' AND p.PRY_ALERTA332_ENVIADA = 0 "
					+ "AND (to_date((ADD_MONTHS(p.PRY_FECHA_TENTATIVA_INICIO, p.PRY_DURACION_ACUMULADA) + p.PRY_DURACION_DIAS_ACUMULADA), 'dd/mm/yyyy') - to_date(SYSDATE, 'dd/mm/yyyy')) = "
					+ diasAntesAlerta;

			session = getSession();
			SQLQuery sqlQuery = session.createSQLQuery(sql);

			genProyectos = sqlQuery.addScalar("idProyecto", Hibernate.LONG).addScalar("pryTitulo", Hibernate.STRING)
					.addScalar("fechaFinalProyecto", Hibernate.DATE)
					.addScalar("nombreDocentePrincipal", Hibernate.STRING)
					.addScalar("emailCoordinador", Hibernate.STRING).list();

			session.close();

			Iterator it = genProyectos.iterator();

			while (it.hasNext()) {
				Object[] name = (Object[]) it.next();

				AlertasAutoProyectos alertaProy = new AlertasAutoProyectos();
				alertaProy.setProyectoId((Long) name[0]);
				alertaProy.setTituloProyecto((String) name[1]);
				alertaProy.setFechaFinalProyecto((Date) name[2]);
				alertaProy.setNombreDocentePrincipal((String) name[3]);
				alertaProy.setEmailCoordinador((String) name[4]);
				resultados.add(alertaProy);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			q = null;
			if (session != null)
				session.close();
			session = null;
		}
		return resultados;
	}

	public void insertarObjetoConIdLong(Object objeto, Long id) throws DataAccessException {
		getHibernateTemplate().save(objeto, id);
	}

	public Long consecutivoSecuencia(String secuencia) {
		Session session = getSession();
		Statement st = null;
		ResultSet rs;
		String myString = new String();
		Long siguienteValor = new Long(0);
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "SELECT " + secuencia + ".NEXTVAL FROM dual";
			System.out.println("consecutivoSecuencia:" + query);
			rs = st.executeQuery(query);

			if (rs != null && rs.next()) {
				siguienteValor = rs.getLong(1);
				rs.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
				System.out.println("st.close");

			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			if (session != null) {
				session.close();
				System.out.println("session.close");

			}
		}
		System.out.println("siguienteValor: " + siguienteValor);
		return siguienteValor;
	}

	public List<Pregunta> obtenerPreguntas(String sql) throws DataAccessException {
		List<Object> instructivos = null;
		List<Pregunta> resultados = new ArrayList<Pregunta>();

		Session session = getSession();

		try {
		String query = "select HER_PREGUNTA.PRE_ID as idPregunta, HER_PREGUNTA.PRE_DESCRIPCION as pregunta, HER_PREGUNTA_CLASIFICACION.PCL_ID as idClasificacion, "
				+ "HER_PREGUNTA_CLASIFICACION.PCL_NOMBRE as nombreClasificacion, HER_PREGUNTA.PRE_ETIQUETA as etiqueta, "
				+ "HER_PREGUNTA.PRE_RESPUESTA as respuesta from HER_PREGUNTA, "
				+ "HER_PREGUNTA_CLASIFICACION where HER_PREGUNTA.PRE_ESTADO = 'A' and HER_PREGUNTA_CLASIFICACION.PCL_ID = HER_PREGUNTA.PCL_ID "
				+ sql + " order by nombreClasificacion, pregunta asc";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		instructivos = sqlQuery1.addScalar("idPregunta", Hibernate.LONG).addScalar("pregunta", Hibernate.STRING)
				.addScalar("idClasificacion", Hibernate.LONG).addScalar("nombreClasificacion", Hibernate.STRING)
				.addScalar("etiqueta", Hibernate.STRING).addScalar("respuesta", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator it = instructivos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Pregunta pre = new Pregunta();
			PreguntaClasificacion pcl = new PreguntaClasificacion();
			pre.setId((Long) name[0]);
			pre.setDescripcion((String) name[1]);
			pcl.setId((Long) name[2]);
			pcl.setNombre((String) name[3]);
			pre.setClasificacion(pcl);
			pre.setEtiqueta((String) name[4]);
			pre.setRespuesta((String) name[5]);
			resultados.add(pre);
		}

		return resultados;
	}

	public List<HistoricoEstadoSolicitud> obtenerHistoricoSolicitud(Solicitud solicitud) {
		Session session = getSession();
		List<HistoricoEstadoSolicitud> historicos;
		try {
			Criteria cr = session.createCriteria(HistoricoEstadoSolicitud.class);
			cr.add(Restrictions.eq("solicitud.id", solicitud.getId()));
			historicos = cr.list();
		} catch (Exception e) {
			historicos = null;
		} finally {
			session.close();
		}
		return historicos;
	}

	public List<Dependencia> obtenerDependenciasSede(String nivel, String estado) {
		Session session = null;
		Query q = null;
		List<Dependencia> resultado = null;

		try {
			session = getSession();
			q = session.createQuery("select d from Dependencia d where " + "d.estado = '" + estado
					+ "' and d.sede.id = '" + nivel + "' order by d.nombre");
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

	public List<DominioDetalle> obtenerDominioDetalle(String tipoDominio, boolean estado) {
		Session session = null;
		Query q = null;
		List<DominioDetalle> resultado = null;

		try {
			session = getSession();
			String query = "select dd from DominioDetalle dd, Dominio d where " + "d.tipo in ('" + tipoDominio
					+ "') and dd.identificador.id = d.id ";
			if (estado) {
				query += " and (dd.estado = 'A' or dd.estado is null) ";
			}
			query += " order by dd.identificador.tipo";
			q = session.createQuery(query);
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

	public List<DominioDetalle> obtenerDominioDetalleListaUnico(String idDominio, String domDetTipo) {
		Session session = null;
		Query q = null;
		List<DominioDetalle> resultado = null;

		try {
			session = getSession();
			String query = "select dd from DominioDetalle dd, Dominio d where " + "d.tipo = '" + idDominio
					+ "' and dd.estado = '" + domDetTipo + "' and d.id = dd.identificador.id ";
			q = session.createQuery(query);
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
	
	public DominioDetalle obtenerDominioDetalleUnico(String idDominio, String domDetTipo) {
		Session session = null;
		Query q = null;
		List<DominioDetalle> resultado = null;

		try {
			session = getSession();
			String query = "select dd from DominioDetalle dd where " + "dd.identificador.id = '" + idDominio
					+ "' and dd.identificador.tipo = '" + domDetTipo + "' ";
			q = session.createQuery(query);
			resultado = q.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			q = null;
			if (session != null)
				session.close();
			session = null;
		}
		if (!resultado.isEmpty()) {
			return (DominioDetalle) resultado.get(0);
		} else {
			return null;
		}
	}

	public List<Reporte> obtenerListaIndicadores(String nivel, Long categoria) {
		Session session = null;
		Query q = null;
		List<Reporte> resultado = null;

		try {
			session = getSession();
			q = session.createQuery(
					"select r from IndicadorReporte ir, Reporte r where ir.reporte.id = r.id and ir.nivelIndicador = '"
							+ nivel + "' and ir.categoria.id = '" + categoria + "' order by r.nombreExterno");
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

	public Reporte obtenerReporte(Long id) {
		Session session = null;
		Query q = null;
		List<Reporte> resultado = null;

		try {
			session = getSession();
			q = session.createQuery("select r from Reporte r where r.id = '" + id + "'");
			resultado = q.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			q = null;
			if (session != null)
				session.close();
			session = null;
		}
		if (resultado != null && resultado.size() > 0) {
			return resultado.get(0);
		} else {
			return null;
		}
	}

	public List<Requerimiento> obtenerRequerimientosXIngeniero(String ingeniero) throws DataAccessException {
		String query = "from Requerimiento r where r.ingenieroAsignado = '" + ingeniero
				+ "' and r.dependenciaAsociada != 'VRIE' and r.estSelRequerimiento = 'En Desarrollo'  "
				+ "and r.tipo='Mejora' order by r.id";

		return getHibernateTemplate().find(query);
	}

	public List<Requerimiento> obtenerRequerimientosPorIngeniero(String ccIng) throws DataAccessException {
		Session session = null;
		Query q = null;
		List<Requerimiento> resultado = null;

		try {
			session = getSession();
			q = session.createQuery("select r from Requerimiento r where r.ingenieroAsignado = '" + ccIng
					+ "' and r.dependenciaAsociada != 'VRIE' and r.estSelRequerimiento = 'En Desarrollo'  "
					+ "and r.tipo='Mejora' order by r.id");
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

	public Persona obtenerCoordinadorLaboratorio(Long idLab) throws DataAccessException {
		String query = "from PersonaLaboratorio pl where pl.laboratorio.id = " + idLab + " and pl.rol.id = 'CO'";
		List<PersonaLaboratorio> lista = getHibernateTemplate().find(query);

		if (lista.size() != 0)
			return lista.get(0).getPersona();
		else
			return null;
	}

	public List<Laboratorio> obtenerListaLaboratoriosActivos() throws DataAccessException {
		String query = "FROM Laboratorio LAB where LAB.activo = 1 order by LAB.nombre ASC";
		return getHibernateTemplate().find(query);
	}

	public Persona obtenerIngenieroRequerimiento(String idIng) throws DataAccessException {
		String query = "from Persona pl where pl.id.documento = '" + idIng + "'";
		List<Persona> lista = getHibernateTemplate().find(query);
		if (lista.size() != 0)
			return lista.get(0);
		else
			return null;
	}

	public Laboratorio obtenerLaboratorioXID(Long idLab) throws DataAccessException {
		List<Laboratorio> lista = getHibernateTemplate().find("from Laboratorio LAB where LAB.id = " + idLab);
		if (lista.size() != 0)
			return lista.get(0);
		else
			return null;
	}

	public Rol obtenerRolPersonaLaboratorioxIDLab(Long idLab, String tipoDocumento, String documento)
			throws DataAccessException {
		String query = "FROM PersonaLaboratorio PL WHERE PL.laboratorio.id = " + idLab
				+ " AND PL.persona.id.tipoDocumento = '" + tipoDocumento + "' AND PL.persona.id.documento = '"
				+ documento + "'";
		List<PersonaLaboratorio> lista = getHibernateTemplate().find(query);
		if (lista.size() != 0)
			return lista.get(0).getRol();
		else
			return null;
	}

	public List<TipoDocumento> obtenerTiposDeDocumento() throws DataAccessException {
		String query = "from TipoDocumento td where td.id not in ('N','D','I','CD','NT','RC','IV','O')";
		return getHibernateTemplate().find(query);
	}

	public List<TipoFormacion> obtenerTiposDeFormacion() throws DataAccessException {
		String query = "from TipoFormacion td where td.id in ('PH','DO','MA','EU','ETP','ET','U','TL','TC','EP','EMQ')";
		return getHibernateTemplate().find(query);
	}

	public List<EstadoCivil> obtenerTiposDeEstadoCivil() throws DataAccessException {
		String query = "from EstadoCivil td where td.id in ('S','C','E','V','U','R','A')";
		return getHibernateTemplate().find(query);
	}

	public List<LaboratorioCostosServicio> obtenerAnalisisCostosEnsayosServicios(Long idServicio)
			throws DataAccessException {
		String query = "from LaboratorioCostosServicio LCS where LCS.servicio.id IN (" + idServicio + ")";
		return getHibernateTemplate().find(query);
	}

	public List<LaboratorioSolicitud> obtenerListaSolicitudesLaboratorioXTIpoSol(Long idLab, Long tipoSol)
			throws DataAccessException {
		String query = "from LaboratorioSolicitud LS where LS.laboratorio.id IN (" + idLab + ") AND LS.tipo.id = "
				+ tipoSol + " order by LS.fechaRegistro DESC";
		return getHibernateTemplate().find(query);
	}

	public List<LaboratorioSolicitud> obtenerListaSolicitudesLaboratorioXPersonaXTIpoSol(IdPersona idPersona,
			Long tipoSol) throws DataAccessException {
		String query = "from LaboratorioSolicitud LS where " + "LS.persona.id.documento IN (" + idPersona.getDocumento()
				+ ") " + "AND LS.persona.id.tipoDocumento IN ('" + idPersona.getTipoDocumento() + "') "
				+ "AND LS.tipo.id = " + tipoSol + " order by LS.fechaRegistro DESC";
		return getHibernateTemplate().find(query);
	}

	public List<LaboratorioAreasSecundariasOCDE> obtenerAreasOCDESecundariasLab(Long idLab) throws DataAccessException {
		String query = "from LaboratorioAreasSecundariasOCDE LAS where LAS.laboratorio.id IN (" + idLab + ")";
		return getHibernateTemplate().find(query);
	}

	public List<ArchivoLaboratorio> obtenerArchivosLaboratorioXidSol(Long idSol) throws DataAccessException {
		String query = "from ArchivoLaboratorio AL where AL.solicitud.id IN (" + idSol + ")";
		return getHibernateTemplate().find(query);
	}

	public List<ArchivoLaboratorio> obtenerArchivosLaboratorioXidActividad(Long idActividad)
			throws DataAccessException {
		String query = "from ArchivoLaboratorio AL where AL.actividad.id IN (" + idActividad + ")";
		return getHibernateTemplate().find(query);
	}
	
	public List<ArchivoLaboratorio> obtenerArchivosInsumoXidInsumoXidTipoArchivo(Long idInsumo, Long tipoArchivo)
			throws DataAccessException {
		String query = "from ArchivoLaboratorio AL where AL.insumo.id IN (" + idInsumo + ") AND AL.tipoArchivo.id IN (" + tipoArchivo + ")";
		return getHibernateTemplate().find(query);
	}
	
	public List<ArchivoLaboratorio> obtenerArchivosLaboratorioXidReporteDanio(Long idReporte)
			throws DataAccessException {
		String query = "from ArchivoLaboratorio AL where AL.reporteDanio.id IN (" + idReporte + ")";
		return getHibernateTemplate().find(query);
	}

	public List obtenerListasMetrologia(String entidad, Long idLab) throws DataAccessException {
		String query = "from " + entidad + " lab where lab.laboratorio.id in (" + idLab
				+ ") order by lab.fechaRegistro desc";
		return getHibernateTemplate().find(query);
	}

	public List<Ciudad> obtenerListaCiudades() throws DataAccessException {
		String query = "from Ciudad td";
		return getHibernateTemplate().find(query);
	}

	public List<Pais> obtenerListaPaisesISO() throws DataAccessException {
		String query = "from Pais td where td.sigla is not null";
		return getHibernateTemplate().find(query);
	}

	public List<TipoDocumento> obtenerTiposDeDocumentoMetrologia() throws DataAccessException {
		String query = "from TipoDocumento td where td.id in ('C','E','CD','NT','P') order by td.nombre";
		return getHibernateTemplate().find(query);
	}
	
	public List<SemilleroInforme> obtenerInformesSemillerosAlertas(){
		Session session = null;
		Query q = null;
		List<SemilleroInforme> resultados = new ArrayList<SemilleroInforme>();
		try {
			String sql2 = "SELECT hsi.SIN_ID as SIN_ID, to_char(hsi.SIN_FECHA_COMPROMISO,'DD/MM/YYYY') as SIN_FECHA_COMPROMISO, hsi.SIN_NUM_NOTIFICACIONES as SIN_NUM_NOTIFICACIONES, hs.SEM_ID as SEM_ID, hs.SEM_NOMBRE as SEM_NOMBRE, hp.PER_EMAIL as PER_EMAIL"
					+ " FROM HER_SEMILLERO_INFORME hsi INNER JOIN HER_SEMILLERO hs ON hs.SEM_ID = hsi.SEM_ID"
					+ " INNER JOIN HER_SEMILLERO_INTEGRANTE hsi2 ON hsi2.SEM_ID = hsi.SEM_ID AND hsi2.SIT_ID = 'DD'"
					+ " INNER JOIN HER_PERSONA hp ON hp.PER_ID = hsi2.INV_ID AND hp.TDO_ID = hsi2.TDO_ID"
					+ " WHERE SIN_CUMPLE = 0 and SIN_NUM_NOTIFICACIONES = 0"
					+ " AND (to_char(SIN_FECHA_COMPROMISO, 'dd/MM/yyyy') = to_char(SYSDATE - 15, 'dd/MM/yyyy')"
					+ " OR to_char(SIN_FECHA_COMPROMISO, 'dd/MM/yyyy') = to_char(SYSDATE + 1, 'dd/MM/yyyy')"
					+ " OR to_char(SIN_FECHA_COMPROMISO, 'dd/MM/yyyy') = to_char(SYSDATE + 15, 'dd/MM/yyyy'))";
			session = getSession();
			SQLQuery sqlQuery = session.createSQLQuery(sql2);
			String select = "SELECT ";
			String as = " AS ";
			int inicioAlias = sql2.indexOf(select) + select.length();
			int finAlias = sql2.indexOf("FROM");
			String alias = sql2.trim().substring(inicioAlias, finAlias);
			alias = alias.replace(" as ", " AS ");
			String[] aliases = new String[100];
			int h = 0;
			for (int j = 1; j < alias.length() - as.length(); j++) {
				if (alias.substring(j, j + as.length()).contains(as)) {
					String nombreAlias = alias.substring(j + as.length(), alias.length());
					bucle1: for (int k = 1; k < nombreAlias.length(); k++) {
						char c = nombreAlias.charAt(k);
						if (c == ' ' || c == ',') {
							String aliasS = nombreAlias.substring(0, k);
							nombreAlias = nombreAlias.substring(k, nombreAlias.length()).trim();
							j += aliasS.length();
							aliases[h] = aliasS;
							h++;
							break bucle1;
						}
					}
				}
			}
			String[] finalAlias = new String[h];
			for (int i = 0; i < h; i++) {
				finalAlias[i] = aliases[i];
			}
			List<Object> listaActividades;
			for (String aall : finalAlias) {
				sqlQuery.addScalar(aall, Hibernate.STRING);
			}
			List result = new ArrayList();
			result = sqlQuery.list();
			session.close();
			Iterator it = result.iterator();
			while (it.hasNext()) {
				Object[] name = (Object[]) it.next();
				SemilleroInforme hsi = new SemilleroInforme();
				hsi.setId(Integer.parseInt((String) name[0]));
				hsi.setFechaCompromiso(new SimpleDateFormat("dd/MM/yyyy").parse((String) name[1]));
				hsi.setNumeroNotificaciones(Integer.parseInt((String) name[2]));
				hsi.setSemillero(new Semillero());
				hsi.getSemillero().setId(Integer.parseInt((String) name[3]));
				hsi.getSemillero().setNombre((String) name[4]);
				hsi.getSemillero().setEmail((String) name[5]);
				resultados.add(hsi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			q = null;
			if (session != null)
				session.close();
			session = null;
		}
		return resultados;
	}
	
	public void actualizarInfoSemilleroInforme(Integer idInforme, Integer numNotificaciones) {
		Session session = getSession();
		Statement st = null;
		try {
			session = getSession();
			st = session.connection().createStatement();
			String query = "UPDATE HER_SEMILLERO_INFORME SET SIN_NUM_NOTIFICACIONES = " + numNotificaciones
					+ " WHERE SIN_ID = " + idInforme;
			System.out.println(query);
			st.execute(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}
	
	public List<Aval> consultaAvalesPendientesRectoria() throws DataAccessException {
		List<?> documentos = null;
		List<Aval> result = new ArrayList<Aval>();

		Session session = getSession();

		try {
		String query = "  SELECT INI.AVI_ID as id, INI.AVI_TIPO as tipo, INI.AVI_ESTADO as estado,"
				+ " DPN1.DPN_NOMBRE as facultad, AVI_FECHAREGISTRO as fecha, AVI_TITULO as titulo, SED.SED_NOMBRE sede,  PER.PER_APELLIDO1 || ' ' || PER.PER_APELLIDO2 || ' ' || PER.PER_NOMBRE1 profesor, PER.PER_EMAIL mail "
				+ " FROM " + " HER_AVAL INI, HER_CONVOCATORIA_EXTERNA CEX, HER_DEPENDENCIA DPN, HER_DEPENDENCIA DPN1, HER_SEDE SED, HER_PERSONA PER "
				+ " WHERE INI.AVI_CONVOCATORIA=to_char(CEX.CEX_ID) AND" + " DPN.DPN_ID = INI.DPN_ID " + " AND  DPN.DPN_ID_2 = DPN1.DPN_ID "
				+ " AND ini.AVI_ESTADO IN ('I','C','P','F','D','RD') and INI.AVI_AVALRECTORIA IS NULL AND CEX.NAT_ID='376' AND SED.SED_ID = DPN1.SED_ID AND PER.PER_ID = INI.INV_ID AND PER.TDO_ID = INI.TDO_ID AND PER.PER_ID != '19380666' ORDER BY INI.AVI_ID DESC";

		SQLQuery sqlQuery1 = session.createSQLQuery(query);

		documentos = sqlQuery1.addScalar("id", Hibernate.LONG).addScalar("tipo", Hibernate.STRING)
				.addScalar("estado", Hibernate.STRING).addScalar("facultad", Hibernate.STRING)
				.addScalar("fecha", Hibernate.DATE).addScalar("titulo", Hibernate.STRING)
				.addScalar("sede", Hibernate.STRING).addScalar("profesor", Hibernate.STRING)
				.addScalar("mail", Hibernate.STRING).list();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator<?> it = documentos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			Aval aval = new Aval();

			aval.setAviId((Long) name[0]);
			aval.setTipo((String) name[1]);
			aval.setAviEstado((String) name[2]);
			aval.setNombreFacultad((String) name[3]);
			aval.setFecha((Date) name[4]);
			aval.setAviTitulo((String) name[5]);
			aval.setEvento((String) name[6]);
			aval.setNombreInvestigador((String) name[7]);
			aval.setAviCiudad((String) name[8]);

			result.add(aval);
		}
		return result;
	}
	
	public List<MovilidadAlertaAutomatica> consultaMovilidadesPendienteInforme() throws DataAccessException {
		List<?> documentos = null;
		List<MovilidadAlertaAutomatica> result = new ArrayList<MovilidadAlertaAutomatica>();

		Session session = getSession();
		try {
		String query = "SELECT\r\n"
				+ "	MOVS.MOV_ID,\r\n"
				+ "	MOVS.FECHA_FINAL,\r\n"
				+ "	MOVS.INV_ID,\r\n"
				+ "	MOVS.INV_EMAIL,\r\n"
				+ "	MOVS.EST_ID,\r\n"
				+ "	MOVS.EST_EMAIL,\r\n"
				+ "	MOVS.MESES_DESDE_FIN_MOV,\r\n"
				+ "	MOVS.MOV_TIPO\r\n"
				+ "FROM (\r\n"
				+ "	SELECT\r\n"
				+ "    MV.MOV_ID AS MOV_ID,\r\n"
				+ "    MV.MOV_FEC_FIN_EV AS FECHA_FINAL,\r\n"
				+ "    MV.MOV_ID_PER AS INV_ID,\r\n"
				+ "    PER.PER_EMAIL AS INV_EMAIL,\r\n"
				+ "    'NA' AS EST_ID,\r\n"
				+ "    'NA' AS EST_EMAIL,\r\n"
				+ "    MONTHS_BETWEEN(SYSDATE, MV.MOV_FEC_FIN_EV) AS MESES_DESDE_FIN_MOV,\r\n"
				+ "    'HER_MOVILIDAD_VISITANTES_EXT' AS MOV_TIPO\r\n"
				+ "	FROM\r\n"
				+ "	    HER_MOVILIDAD_VISITANTES_EXT MV\r\n"
				+ "	INNER JOIN HER_PERSONA PER ON PER.TDO_ID = MV.MOV_TDO_ID_PER AND PER.PER_ID = MV.MOV_ID_PER\r\n"
				+ "	WHERE\r\n"
				+ "	    (MV.MOV_ESTADO_SEG <> 'F' OR MV.MOV_ESTADO_SEG IS NULL)\r\n"
				+ "	    AND MV.MOV_APROB = 'SI'\r\n"
				+ "	    AND MV.MOV_ACEPT = 'SI'\r\n"
				+ "	    AND MOD(MONTHS_BETWEEN(SYSDATE, MV.MOV_FEC_FIN_EV), 1) = 0\r\n"
				+ "	    AND (MONTHS_BETWEEN(SYSDATE, MV.MOV_FEC_FIN_EV) = 0 or MONTHS_BETWEEN(SYSDATE, MV.MOV_FEC_FIN_EV) >= 3)\r\n"
				+ "	UNION\r\n"
				+ "	SELECT\r\n"
				+ "	    ME.MOV_ID AS MOV_ID,\r\n"
				+ "	    ME.MOV_FEC_FIN_EV AS FECHA_FINAL,\r\n"
				+ "	    ME.MOV_ID_PER AS INV_ID,\r\n"
				+ "	    PER.PER_EMAIL AS INV_EMAIL,\r\n"
				+ "	    EST.EST_ID AS EST_ID,\r\n"
				+ "	    CASE \r\n"
				+ "	        WHEN REGEXP_LIKE(EST.EST_EMAIL, '^[a-zA-Z0-9._%+-]+@unal\\.edu\\.co$') THEN EST.EST_EMAIL\r\n"
				+ "	        ELSE EST.EST_EMAIL || '@unal.edu.co'\r\n"
				+ "	    END AS EST_EMAIL,\r\n"
				+ "	    MONTHS_BETWEEN(SYSDATE, ME.MOV_FEC_FIN_EV) AS MESES_DESDE_FIN_MOV,\r\n"
				+ "	    'HER_MOVILIDAD_ESTUDIANTE_POS' AS MOV_TIPO\r\n"
				+ "	FROM\r\n"
				+ "	    HER_MOVILIDAD_ESTUDIANTE_POS ME\r\n"
				+ "	INNER JOIN HER_PERSONA PER ON PER.TDO_ID = ME.MOV_TDO_ID_PER AND PER.PER_ID = ME.MOV_ID_PER\r\n"
				+ "	LEFT JOIN HER_TMP_ESTUDIANTE EST ON EST.TDO_ID = ME.MOV_TDO_ID_EST AND EST.EST_ID = ME.MOV_ID_EST\r\n"
				+ "	WHERE\r\n"
				+ "	    (ME.MOV_ESTADO_SEG <> 'F' OR ME.MOV_ESTADO_SEG IS NULL)\r\n"
				+ "	    AND ME.MOV_APROB = 'SI'\r\n"
				+ "	    AND ME.MOV_ACEPT = 'SI'\r\n"
				+ "	    AND MOD(MONTHS_BETWEEN(SYSDATE, ME.MOV_FEC_FIN_EV), 1) = 0\r\n"
				+ "	    AND (MONTHS_BETWEEN(SYSDATE, ME.MOV_FEC_FIN_EV) = 0 or MONTHS_BETWEEN(SYSDATE, ME.MOV_FEC_FIN_EV) >= 3)\r\n"
				+ "	UNION\r\n"
				+ "	SELECT\r\n"
				+ "	    MD.MOV_ID AS MOV_ID,\r\n"
				+ "	    MD.MOV_FEC_FIN_EV AS FECHA_FINAL,\r\n"
				+ "	    MD.MOV_ID_PER AS INV_ID,\r\n"
				+ "	    PER.PER_EMAIL AS INV_EMAIL,\r\n"
				+ "	    'NA' AS EST_ID,\r\n"
				+ "	    'NA' AS EST_EMAIL,\r\n"
				+ "	    MONTHS_BETWEEN(SYSDATE, MD.MOV_FEC_FIN_EV) AS MESES_DESDE_FIN_MOV,\r\n"
				+ "	    'HER_MOVILIDAD_DOCENTES_EVENTOS' AS MOV_TIPO\r\n"
				+ "	FROM\r\n"
				+ "	    HER_MOVILIDAD_DOCENTES_EVENTOS MD\r\n"
				+ "	INNER JOIN HER_PERSONA PER ON PER.TDO_ID = MD.MOV_TDO_ID_PER AND PER.PER_ID = MD.MOV_ID_PER\r\n"
				+ "	WHERE\r\n"
				+ "	    (MD.MOV_ESTADO_SEG <> 'F' OR MD.MOV_ESTADO_SEG IS NULL)\r\n"
				+ "	    AND MD.MOV_APROB = 'SI'\r\n"
				+ "	    AND MD.MOV_ACEPT = 'SI'\r\n"
				+ "	    AND MOD(MONTHS_BETWEEN(SYSDATE, MD.MOV_FEC_FIN_EV), 1) = 0\r\n"
				+ "	    AND (MONTHS_BETWEEN(SYSDATE, MD.MOV_FEC_FIN_EV) = 0 or MONTHS_BETWEEN(SYSDATE, MD.MOV_FEC_FIN_EV) >= 3)\r\n"
				+ "	ORDER BY FECHA_FINAL DESC\r\n"
				+ ") MOVS LEFT JOIN HER_LOG_ALERTAS_AUTOMATICAS LAA\r\n"
				+ "ON LAA.OBJ_ID = MOVS.MOV_ID  AND LAA.OBJ_VALUE = MOVS.MESES_DESDE_FIN_MOV AND LAA.OBJ_TIPO = 'MOVILIDADES'\r\n"
				+ "WHERE LAA.OBJ_ID IS NULL AND LAA.OBJ_VALUE IS NULL\r\n"
				+ "ORDER BY MOVS.FECHA_FINAL DESC";
		SQLQuery sqlQuery1 = session.createSQLQuery(query);
		documentos = sqlQuery1
				.addScalar("MOV_ID", Hibernate.LONG) //0
				.addScalar("FECHA_FINAL", Hibernate.DATE) //1
				.addScalar("INV_ID", Hibernate.STRING) //2
				.addScalar("INV_EMAIL", Hibernate.STRING) //3
				.addScalar("EST_ID", Hibernate.STRING) //4
				.addScalar("EST_EMAIL", Hibernate.STRING) //5
				.addScalar("MESES_DESDE_FIN_MOV", Hibernate.INTEGER) //6
				.addScalar("MOV_TIPO", Hibernate.STRING) //7
				.list();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		Iterator<?> it = documentos.iterator();

		while (it.hasNext()) {
			Object[] name = (Object[]) it.next();
			MovilidadAlertaAutomatica mov = new MovilidadAlertaAutomatica();

			mov.setId((Long) name[0]);
			mov.setFechafinal((Date) name[1]);
			mov.setDocumentoDocente((String) name[2]);
			mov.setCorreoDocente((String) name[3]);
			mov.setDocumentoEstudiante((String) name[4]);
			mov.setCorreoEstudiante((String) name[5]);
			mov.setNumeroMesesDesdeFinMovilidad((Integer) name[6]);
			mov.setTipoMovilidadTabla((String) name[7]);

			result.add(mov);
		}
		
		return result;
	}
	
	public ParametroMaestro obtenerParametroPorNombre(String parametro) {
			List lista = getHibernateTemplate().find("from ParametroMaestro where nombre = '" + parametro + "'");
			if (lista.size() == 0) {
				return null;
			} else {
				return (ParametroMaestro) lista.get(0);
			}
	}
}
