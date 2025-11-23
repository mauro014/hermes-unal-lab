package co.edu.unal.hermes.vista.laboratorios;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.VerticalAlignment;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.PieChartModel;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * @author dgbenitezc
 */
public class ManejadorLaboratoriosReportes extends ManejadorBase {

	private static final long serialVersionUID = -7362047411783196355L;

	private Boolean esLaboratorios = false;
	private List<Map> listaReporte;

	private SelectItem[] selectItemSedes;
	private Sede sedeSeleccionada;
	private SelectItem[] selectItemFacultades;
	private Dependencia facultadSeleccionada;
	private Dependencia departamentoSeleccionado;
	private SelectItem[] selectItemDepartamentos;
	private SelectItem[] laboratoriosSelectItem;
	private String labSeleccionado; 

	private List<ColumnModel> columnas;
	private String headerDatatable = "";
	private String nombreArchivo = "";
	private Boolean esLaboratoriosSede = false;
	private Boolean esLaboratoriosFacultad;
	private Boolean esLaboratoriosDepto;
	private Boolean esLaboratoriosConsulta;
	private Boolean esPersonalLaboratorio;
	private Boolean esCoordinadorLaboratorio;
	private String rolSeleccionadoLabs;
	private String tipoRolSeleccionado = "N";
	private Dependencia facultadLaboratorios;
	private String mensajeError = "";

	private PieChartModel model;
	private StreamedContent chart;
	private Double sumaTotal;

	public ManejadorLaboratoriosReportes() {
		esLaboratorios = (Boolean) sesion.getAttribute("esLaboratorios");
		esLaboratoriosSede = (Boolean) sesion.getAttribute("esLaboratoriosSede");
		esLaboratoriosFacultad = (Boolean) sesion.getAttribute("esLaboratoriosFacultad");
		esLaboratoriosDepto = (Boolean) sesion.getAttribute("esLaboratoriosDepto");
		facultadLaboratorios = (Dependencia) sesion.getAttribute("depLabsFacultad");
		esLaboratoriosConsulta = (Boolean) sesion.getAttribute("esConsultaLaboratorios");
		
		esPersonalLaboratorio = (Boolean) sesion.getAttribute("esPersonalLaboratorio");
		esCoordinadorLaboratorio = (Boolean) sesion.getAttribute("esCoordinadorLaboratorio");
		rolSeleccionadoLabs = (String) sesion.getAttribute("rolSeleccionadoLabs");
		validarRolInternoExterno();

		if(tipoRolSeleccionado.equals("I")) {
			laboratoriosSelectItem = laboratoriosSelectItem();
		} else if(tipoRolSeleccionado.equals("E")) {
			selectItemSedes = selectItemSedes();
			selectItemFacultades = selectItemFacultades(sedeSeleccionada.getId());
			if (esLaboratoriosFacultad) {
				facultadSeleccionada = facultadLaboratorios;
			} else if (esLaboratoriosDepto) {
				facultadSeleccionada = facultadLaboratorios.getFacultad();
			} else {
				facultadSeleccionada = new Dependencia();
				departamentoSeleccionado = new Dependencia();
			}
			selectItemDepartamentos = selectItemDepartamentos(facultadSeleccionada.getId());
		}
	}
	
	public SelectItem[] laboratoriosSelectItem() {
		List<Laboratorio> listaLaboratoriosAsociados = servicioGeneral.obtenerNombreLaboratoriosInvestigador(personaActual.getId().getTipoDocumento(), personaActual.getId().getDocumento());
		
		if(!listaLaboratoriosAsociados.isEmpty()) {
			SelectItem[] labsSelectItem = new SelectItem[listaLaboratoriosAsociados.size() + 1];
			labsSelectItem[0] = new SelectItem("", "Todos");
			for (int i = 0; i < listaLaboratoriosAsociados.size(); i++) {
				Laboratorio l = (Laboratorio) listaLaboratoriosAsociados.get(i);
				labsSelectItem[i + 1] = new SelectItem(l.getId()+ "",l.getNombre());
			}
			labSeleccionado = "";
			return labsSelectItem;
		}
		
		return new SelectItem[0];
	}
	
	public void validarRolInternoExterno() {
		List<String> rolesInternos = Arrays.asList("CO", "CT", "DC", "TL", "LID", "LEI", "LEA", "LPA", "PL");
		List<String> rolesExternos = Arrays.asList("DL", "LS", "LF", "LD", "CL");
		
		if(rolesExternos.contains(rolSeleccionadoLabs))
			tipoRolSeleccionado = "E";
		else if(rolesInternos.contains(rolSeleccionadoLabs))
			tipoRolSeleccionado = "I";
		else
			tipoRolSeleccionado = "N";
	}

	public SelectItem[] selectItemSedes() {

		if (esLaboratoriosSede) {
			Sede sedePersonaActual;
			personaActual = (Persona) sesion.getAttribute("persona");
			if (esLaboratoriosSede && personaActual instanceof InvestigadorInterno) {
				sedePersonaActual = ((InvestigadorInterno) personaActual).getDependencia().getSede();
				SelectItem[] sedeSelectItem = new SelectItem[1];
				sedeSelectItem[0] = new SelectItem(sedePersonaActual.getId(),sedePersonaActual.getNombre());
				sedeSeleccionada = sedePersonaActual;
				return sedeSelectItem;
			} else {
				mensajeError("Error al determinar la Sede.");
				return new SelectItem[0];
			}
		} else if (esLaboratorios || esLaboratoriosConsulta) {
			String hql = "from Sede WHERE id NOT IN (1) ORDER BY id";
			List<Sede> listaSedes = servicioGeneral.obtenerObjetos(Sede.class,hql);
			SelectItem[] sedeSelectItem = new SelectItem[listaSedes.size() + 1];
			sedeSelectItem[0] = new SelectItem("", "Todas");
			for (int i = 0; i < listaSedes.size(); i++) {
				Sede sede = (Sede) listaSedes.get(i);
				sedeSelectItem[i + 1] = new SelectItem(sede.getId(),
						sede.getNombre());
			}
			sedeSeleccionada = new Sede();
			return sedeSelectItem;
		} else if (esLaboratoriosFacultad || esLaboratoriosDepto) {
			Sede sedePersonaActual;
			if (facultadLaboratorios != null) {
				sedePersonaActual = facultadLaboratorios.getSede();
				SelectItem[] sedeSelectItem = new SelectItem[1];
				sedeSelectItem[0] = new SelectItem(sedePersonaActual.getId(),sedePersonaActual.getNombre());
				sedeSeleccionada = sedePersonaActual;
				return sedeSelectItem;
			} else {
				mensajeError("Error al determinar la Sede.");
				return new SelectItem[0];
			}
		} else {
			return new SelectItem[0];
		}
	}

	public SelectItem[] selectItemFacultades(Long idSede) {
		String hql = "from Dependencia WHERE sede = '" + idSede+ "' AND esFacultad = 'Y' ORDER BY nombre";
		if (esLaboratoriosFacultad) {
			hql = "from Dependencia WHERE id = '" + facultadLaboratorios.getId() + "'";
		}
		if (esLaboratoriosDepto) {
			hql = "from Dependencia WHERE id = '"+ facultadLaboratorios.getFacultad().getId() + "'";
		}

		List<Dependencia> listaFacultades = servicioGeneral.obtenerObjetos(Dependencia.class, hql);
		SelectItem[] facultadSelectItem = new SelectItem[listaFacultades.size() + 1];
		facultadSelectItem[0] = new SelectItem("", "Todas");
		for (int i = 0; i < listaFacultades.size(); i++) {
			Dependencia ci = (Dependencia) listaFacultades.get(i);
			facultadSelectItem[i + 1] = new SelectItem(ci.getId(),ci.getNombre());
			ci = null;
		}

		if (esLaboratoriosFacultad) {
			facultadSeleccionada = facultadLaboratorios;
			SelectItem siFac = facultadSelectItem[1];
			facultadSelectItem = new SelectItem[1];
			facultadSelectItem[0] = siFac;
		} else if (esLaboratoriosDepto) {
			facultadSeleccionada = facultadLaboratorios.getFacultad();
			SelectItem siFac = facultadSelectItem[1];
			facultadSelectItem = new SelectItem[1];
			facultadSelectItem[0] = siFac;
		} else {
			facultadSeleccionada = new Dependencia();
		}

		departamentoSeleccionado = new Dependencia();
		return facultadSelectItem;
	}

	private SelectItem[] selectItemDepartamentos(String idFacultad) {
		String hql = "from Dependencia WHERE facultad = '"
				+ idFacultad
				+ "' AND (esDepartamento = 'Y' OR UPPER(nombre) LIKE '%ESCUELA%') ORDER BY nombre";
		List<Dependencia> listaDepartamentos = servicioGeneral.obtenerObjetos(Dependencia.class, hql);
		SelectItem[] departamentoSelectItem = new SelectItem[listaDepartamentos.size() + 1];
		departamentoSelectItem[0] = new SelectItem("", "Todos");

		for (int i = 0; i < listaDepartamentos.size(); i++) {
			Dependencia ci = (Dependencia) listaDepartamentos.get(i);
			departamentoSelectItem[i + 1] = new SelectItem(ci.getId(),ci.getNombre());
			ci = null;
		}

		if (esLaboratoriosDepto) {
			departamentoSeleccionado = facultadLaboratorios;
			departamentoSelectItem = new SelectItem[1];
			departamentoSelectItem[0] = new SelectItem(
					facultadLaboratorios.getId(),
					facultadLaboratorios.getNombre());
		} else {
			departamentoSeleccionado = new Dependencia();
		}

		return departamentoSelectItem;
	}

	public void cambiarSede() {
		selectItemFacultades = selectItemFacultades(sedeSeleccionada.getId());
	}

	public void cambiarFacultad() {
		selectItemDepartamentos = selectItemDepartamentos(facultadSeleccionada.getId());
	}

	public void cambiarDepartamento() {
	}
	
	public String obtenerListaIdLaboratorios() {
	    String[] labs = new String[laboratoriosSelectItem.length-1];
	    for (int i = 1; i <= laboratoriosSelectItem.length-1; i++)
	        labs[i-1] = laboratoriosSelectItem[i].getValue().toString();
	    
	    //String.join
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < labs.length; i++) {
	        sb.append(labs[i]);
	        if (i < labs.length - 1) sb.append(",");
	    }
	    return sb.toString();
	}
	
	public String obtenerComplementoWhere(String where) {
		if(tipoRolSeleccionado.equals("E")) {
			if (sedeSeleccionada.getId() != null && sedeSeleccionada.getId() != 0) {
				where += "AND L.SED_ID = " + sedeSeleccionada.getId() + " ";
			}
			if (facultadSeleccionada.getId() != null
					&& facultadSeleccionada.getId() != "") {
				where += "AND L.LAB_FACULTAD = '" + facultadSeleccionada.getId() + "' ";
			}
			if (departamentoSeleccionado.getId() != null
					&& departamentoSeleccionado.getId() != "") {
				where += "AND L.LAB_DEPTO = '" + departamentoSeleccionado.getId() + "' ";
			}
		} else if(tipoRolSeleccionado.equals("I")) {
			if(labSeleccionado.equals(""))
		        where += "AND L.LAB_ID IN (" + obtenerListaIdLaboratorios() + ") ";
			else
				where += "AND L.LAB_ID = " + labSeleccionado + " ";
		}
		return where;
	}
	
	public String obtenerComplementoWhereRolesExternos(String where) {
		if(tipoRolSeleccionado.equals("E")) {
			if (sedeSeleccionada.getId() != null && sedeSeleccionada.getId() != 0) {
				where += "AND SED.SED_ID = " + sedeSeleccionada.getId() + " ";
			}
			if (facultadSeleccionada.getId() != null
					&& facultadSeleccionada.getId() != "") {
				where += "AND FAC.DPN_ID = '" + facultadSeleccionada.getId() + "' ";
			}
			if (departamentoSeleccionado.getId() != null
					&& departamentoSeleccionado.getId() != "") {
				where += "AND DEP.DPN_ID = '" + departamentoSeleccionado.getId() + "' ";
			}
		}
		return where;
	}
	
	public void crearColumnas() {
		// se crean las columnas, de acuerdo a los keys del map:
		columnas = new ArrayList<ColumnModel>();
		if (listaReporte.size() > 0) {
			for (Object key : listaReporte.get(0).keySet()) {
				columnas.add(new ColumnModel(key.toString()));
			}
		}
	}
	
//	INICIO QUERYS REPORTES
	public void reporteLaboratoriosRegistrados() {
		headerDatatable = "Laboratorios Registrados";
		nombreArchivo = "Reporte_Laboratorios_Registrados_";
		
		String where = "WHERE L.LAB_ID <> '49' ";		
		where = obtenerComplementoWhere(where);
		if (where.length() < 7) where = "";
		
		String sql = "SELECT "
				+ "CASE WHEN L.lab_Activo=0 THEN 'NO' WHEN L.lab_Activo=1 THEN 'SI' END AS ACTIVO, \r\n"
				+ "L.LAB_ID AS ID_LABORATORIO_HERMES, \r\n"
				+ "L.LAB_NOMBRE AS LABORATORIO, \r\n"
				+ "S.SED_NOMBRE AS SEDE, \r\n"
				+ "CAMPUS.CAMPUS_NOMBRE || ' - ' || CAMPUS.CAMPUS_DIRECCION || ' - ' || CAMPUS.CAMPUS_TELEFONO AS CAMPUS, \r\n"
				+ "DEPTO_CIUDAD.NOMBRE AS DEPARTAMENTO_CIUDAD, \r\n"
				+ "F.DPN_NOMBRE AS NOMBRE_FACULTAD, \r\n"
				+ "D.DPN_NOMBRE AS NOMBRE_DEPARTAMENTO, \r\n"
				+ "EDF.EDF_CODIGO || ' - ' || EDF.EDF_NOMBBRE AS EDIFICIO, \r\n"
				+ "L.LAB_SALON AS SALON, \r\n"
				+ "L.LAB_PISO AS PISO, \r\n"
				+ "TP_LAB_TIPO.NOMBRE AS TIPO, \r\n"
				+ "PER.TDO_ID || '-' || PER.PER_ID AS DOCUMENTO_COORDINADOR, \r\n"
				+ "PER.PER_NOMBRE1 || ' ' ||  PER.PER_NOMBRE2 || ' ' ||  PER.PER_APELLIDO1 || ' ' ||  PER.PER_APELLIDO2 AS NOMBRE_COORDINADOR, \r\n"
				+ "PER.PER_EMAIL AS EMAIL_COORDINADOR, \r\n"
				+ "CASE WHEN L.LAB_GESTION_ACREDITACION=50 THEN 'SI' WHEN L.LAB_GESTION_ACREDITACION=51 THEN 'NO' WHEN L.LAB_GESTION_ACREDITACION=52 THEN 'EN PROCESO' WHEN L.LAB_GESTION_ACREDITACION=49 THEN 'NO APLICA' END AS ACREDITADO, \r\n"
				+ "AREA_PR_OCDE.NOMBRE AS AREA_OCDE_PRINCIPAL, \r\n"
				+ "SUBAREA_PR_OCDE.NOMBRE AS SUBAREA_OCDE_PRINCIPAL, \r\n"
				+ "OBJ_SOC.NOMBRE AS OBJETIVO_SOCIOECONOMICO, \r\n"
				+ "(SELECT LISTAGG(PR.NOMBRE || ' (' || SEC.NOMBRE || ') ',' ; ') WITHIN GROUP (ORDER BY PR.NOMBRE) FROM HER_LABORATORIO_AREA_SEC_OCDE LAS LEFT JOIN HER_TIPOS PR ON LAS.LAS_AREA = PR.ID LEFT JOIN HER_TIPOS SEC ON LAS.LAS_SUBAREA = SEC.ID WHERE LAS.LAB_ID = L.LAB_ID GROUP BY LAS.LAB_ID) AS SUB_AREAS_OCDE, \r\n"
				+ "ODS_P.NOMBRE AS OBJETIVO_DES_SOST_PRINCIPAL, \r\n"
				+ "(SELECT LISTAGG(PR.NOMBRE,' ; ') WITHIN GROUP (ORDER BY PR.NOMBRE) FROM HER_LABORATORIO_ODS_SEC LAS LEFT JOIN HER_TIPOS PR ON LAS.ODS_ID = PR.ID WHERE LAS.LAB_ID = L.LAB_ID GROUP BY LAS.LAB_ID) AS OBJETIVOS_DES_SOST_SECUNDARIOS, \r\n"
				+ "L.LAB_AREA_M2 AS AREA_M2, \r\n"
				+ "L.LAB_EMAIL AS EMAIL, \r\n"
				+ "L.LAB_TELEFONO AS TELEFONO, \r\n"
				+ "L.LAB_TELEFONO_2 AS TELEFONO_2, \r\n"
				+ "L.LAB_PAGINA_WEB AS PAGINA_WEB, \r\n"
				+ "'www.hermes.unal.edu.co/pages/Consultas/EnsayoLaboratorio.xhtml?idEnsayo=' || L.LAB_ID AS URL_LAB, \r\n"
				+ "PORT_SERV.NOMBRE AS PORTAFOLIO_SERVICIOS, \r\n"
				+ "L.LAB_DESCRIPCION AS DESCRIPCION_ACTIVIDADES, \r\n"
				+ "CASE WHEN L.LAB_ES_TIPO_ENSAYO=0 THEN 'NO' WHEN L.LAB_ES_TIPO_ENSAYO=1 THEN 'SI' END AS ES_TIPO_ENSAYO, \r\n"
				+ "CASE WHEN L.LAB_ES_TIPO_MUESTREO=0 THEN 'NO' WHEN L.LAB_ES_TIPO_MUESTREO=1 THEN 'SI' END AS ES_TIPO_MUESTREO, \r\n"
				+ "CASE WHEN L.LAB_ES_TIPO_CALIBRACION=0 THEN 'NO' WHEN L.LAB_ES_TIPO_CALIBRACION=1 THEN 'SI' END AS ES_TIPO_CALIBRACION, \r\n"
				+ "CASE WHEN L.LAB_ES_TIPO_OTRO=0 THEN 'NO' WHEN L.LAB_ES_TIPO_OTRO=1 THEN 'SI' END AS ES_OTRO_TIPO, \r\n"
				+ "L.LAB_ES_TIPO_OTRO_CUAL AS ES_OTRO_TIPO_CUAL, \r\n"
				+ "LAB_ETAPA_REGISTRO AS ETAPA_REGISTRO, \r\n"
				+ "CE.EQUIPOS AS EQUIPOS, \r\n"
				+ "COSTO_EQUIPOS.COSTO_EQUIPOS AS VALOR_EQUIPOS, \r\n"
				+ "EQ_EN_USO.EQUIPOS AS EN_USO, \r\n"
				+ "EQ_ESPEC.EQUIPOS AS ESPECIALIZADOS, \r\n"
				+ "EQ_ROBUSTO.EQUIPOS AS ROBUSTOS, \r\n"
				+ "EQ_HV.EQUIPOS AS HV, \r\n"
				+ "EQ_ACTIV_REG.EQUIPOS AS ACTIV_REG, \r\n"
				+ "EQ_ACTIV_EJEC.EQUIPOS AS EJEC, \r\n"
				+ "EQ_ACTIV_PROG.EQUIPOS AS PROG, \r\n"
				+ "EQ_ACTIV_PLAN.EQUIPOS AS PLAN, \r\n"
				+ "REQ_MANTTO.EQUIPOS AS REQ_MANTTO, \r\n"
				+ "EQ_ACTIV_MANTTO.EQUIPOS AS ACTIV_MANTTO, \r\n"
				+ "EQ_MEDICION.EQUIPOS AS EQ_MEDICION, \r\n"
				+ "EQ_ACTIV_CALIBR.EQUIPOS AS ACTIV_CALIBR, \r\n"
				+ "CASE WHEN L.LAB_DEDICACION_DOC+L.LAB_DEDICACION_INV+L.LAB_DEDICACION_EXT not between 99 and 100 then 'Desconocida' WHEN L.LAB_DEDICACION_DOC=100 then 'Exclusiva Docencia' WHEN L.LAB_DEDICACION_INV=100 then 'Exclusiva Investigación' WHEN L.LAB_DEDICACION_EXT=100 then 'Exclusiva Extensión' WHEN L.LAB_DEDICACION_DOC=0 then 'Extensión - Investigación' WHEN L.LAB_DEDICACION_INV=0 then 'Docencia - Extensión' WHEN L.LAB_DEDICACION_EXT=0 then 'Docencia - Investigación' else 'Docencia - Extensión - Investigación' end AS DEDICACION, \r\n"
				+ "L.LAB_DEDICACION_INV AS DEDICACION_INVESTIGACION, \r\n"
				+ "L.LAB_DEDICACION_DOC AS DEDICACION_DOCENCIA, \r\n"
				+ "L.LAB_DEDICACION_EXT AS DEDICACION_EXTENSION, \r\n"
				+ "LAB_DEDICACION_DOC AS DOC, \r\n"
				+ "CA.ASIGNATURAS AS ASIGNATURAS, \r\n"
				+ "ESTUDIANTES_LAB(L.LAB_ID) AS ESTUDIANTES_DOCENCIA, \r\n"
				+ "LAB_DEDICACION_EXT AS EXT, \r\n"
				+ "CS.ENSAYOS_SERVICIOS AS SERVICIOS, \r\n"
				+ "LAB_DEDICACION_INV AS INV, \r\n"
				+ "CP.PROYECTOS_INVESTIGACION AS PROYECTOS_INVESTIGACION, \r\n"
//				+ "ESTUDIANTES_LAB_INV(L.LAB_ID) AS ESTUDIANTES_INVESTIGACION, \r\n"
				+ "CL.LINEAS_INVESTIGACION AS LINEAS_INVESTIGACION, \r\n"
				+ "CC.AREAS_TEMATICAS AS AREAS_TEMATICAS, \r\n"
				+ "CASE WHEN L.LAB_VALIDA_DATOS_COMPLETOS = 0 THEN 'NO' WHEN L.LAB_VALIDA_DATOS_COMPLETOS = 1 THEN 'SI' END AS COORD_VALIDA_DATOS, \r\n"
				+ "(SELECT LISTAGG(PER.per_nombre1 || ' ' ||PER.PER_NOMBRE2 || ' ' || PER.PER_APELLIDO1 || ' ' || PER.PER_APELLIDO2 || ' (' || PER.PER_EMAIL || ')',' ; ') WITHIN GROUP (ORDER BY PER.PER_APELLIDO1) FROM HER_PERSONA_LABORATORIO PLCT INNER JOIN HER_PERSONA PER ON PLCT.PER_ID = PER.PER_ID AND PLCT.TDO_ID = PER.TDO_ID WHERE PLCT.LAB_ID = L.LAB_ID AND PLCT.ROL_ID = 'CT' GROUP BY PLCT.LAB_ID) AS COORDINADORES_TECNICOS, \r\n"
				+ "L.LAB_ACTO_CREACION_TIPO || ' ' || L.LAB_ACTO_CREACION_NUMERO || ', ' || L.LAB_ACTO_CREACION_EMANADA AS ACTO_CREACION, \r\n"
				+ "L.LAB_ACTO_CREACION_FECHA AS FECHA_ACTO_CREACION, \r\n"
				+ "CASE WHEN ARCHIVOS_ACT_CREACION.ARCHIVOS > 0 THEN 'SI' ELSE 'NO' END AS ARCHIVOS_ACT_CREACION, \r\n"
				+ "CASE WHEN ARCHIVOS_REGLAMENTO.ARCHIVOS > 0 THEN 'SI' ELSE 'NO' END AS ARCHIVOS_REGLAMENTO, \r\n"
				+ "TIPO_REGL.NOMBRE AS TIPO_REGLAMENTO, \r\n"
				+ "L.LAB_LINK_REGLAMENTO AS LINK_REGLAMENTO, \r\n"
				+ "CASE WHEN ARCHIVOS_TARIFAS.ARCHIVOS > 0 THEN 'SI' ELSE 'NO' END AS ARCHIVOS_TARIFAS, \r\n"
				+ "CASE WHEN ARCHIVOS_BROCHURE.ARCHIVOS > 0 THEN 'SI' ELSE 'NO' END AS ARCHIVOS_PORTAFOLIO_SERVICIOS, \r\n"
				+ "L.LAB_LINK_BROCHURE AS LINK_PORTAFOLIO_SERVICIOS, \r\n"
				+ "CASE WHEN ARCHIVOS_COND_PREST_SERVICIOS.ARCHIVOS > 0 THEN 'SI' ELSE 'NO' END AS ARCHIVOS_COND_PREST_SERVICIOS, \r\n"
				+ "L.LAB_LINK_CONDICIONES AS LINK_CONDICIONES, \r\n"
				+ "L.LAB_HORARIO_ATENCION AS HORARIO_ATENCION, \r\n"
				+ "PER2.PER_NOMBRE1 || ' ' ||PER2.PER_NOMBRE2 || ' ' || PER2.PER_APELLIDO1 || ' ' || PER2.PER_APELLIDO2 AS PERSONA_ACTUALIZACION, \r\n"
				+ "L.LAB_FECHA_CREACION_REGISTRO AS FECHA_ACTUALIZACION, \r\n"
				+ "LAB_PORCENTAJE_COMPLETITUD AS PORCENTAJE_AVANCE, \r\n"
				+ "L.LAB_INACTIVO_JUSTIFICACION AS JUSTIFICACION_INACTIVO, \r\n"
				+ "L.LAB_INACTIVO_FECHA AS FECHA_INACTIVO, \r\n"
				+ "ACT_HV_EQUIPOS.FECHA_ACT_HV AS FECHA_ACT_HV_EQUIPOS, \r\n"
				+ "ACT_HV_EQUIPOS.PERSONA_ACT_HV_EQUIPOS AS PERSONA_ACT_HV_EQUIPOS \r\n"
				+ "FROM HER_LABORATORIO L "
				+ "LEFT JOIN HER_PERSONA_LABORATORIO PL ON PL.LAB_ID = L.LAB_ID AND PL.ROL_ID = 'CO' "
				+ "LEFT JOIN HER_PERSONA PER ON PER.TDO_ID = PL.TDO_ID AND PER.PER_ID = PL.PER_ID "
				+ "LEFT JOIN HER_PERSONA PER2 ON PER2.TDO_ID = L.LAB_REGISTRO_TDO_ID AND PER2.PER_ID = L.LAB_REGISTRO_PER_ID "
				+ "LEFT JOIN HER_SEDE S ON S.SED_ID = L.SED_ID "
				+ "LEFT JOIN HER_DEPENDENCIA F ON F.DPN_ID = L.LAB_FACULTAD "
				+ "LEFT JOIN HER_DEPENDENCIA D ON D.DPN_ID = L.LAB_DEPTO "
				+ "LEFT JOIN HER_TIPOS TP_LAB_TIPO ON TP_LAB_TIPO.ID = L.LAB_TIPO "
				+ "LEFT JOIN HER_TIPOS AREA_PR_OCDE ON AREA_PR_OCDE.ID = L.LAB_AREA_PRINCIPAL "
				+ "LEFT JOIN HER_TIPOS SUBAREA_PR_OCDE ON SUBAREA_PR_OCDE.ID = L.LAB_SUB_AREA_PRINCIPAL "
				+ "LEFT JOIN HER_TIPOS OBJ_SOC ON OBJ_SOC.ID = L.LAB_OBJETIVO_SOCIOECONOMICO "
				+ "LEFT JOIN HER_TIPOS PORT_SERV ON PORT_SERV.ID = L.LAB_PORTAFOLIO_SERVICIOS "
				+ "LEFT JOIN HER_TIPOS DEPTO_CIUDAD ON DEPTO_CIUDAD.ID = L.LAB_DEPTO_CIUDAD "
				+ "LEFT JOIN HER_EDIFICIO EDF ON EDF.EDF_ID = L.EDF_ID "
				+ "LEFT JOIN HER_CAMPUS CAMPUS ON CAMPUS.CAMPUS_ID = L.LAB_CAMPUS "
				+ "LEFT JOIN HER_TIPOS TIPO_REGL ON TIPO_REGL.ID = l.LAB_TIPO_REGLAMENTO "
				+ "LEFT JOIN HER_TIPOS ODS_P ON ODS_P.ID = l.LAB_ODS_PRINCIPAL "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(d.LAB_ID) AS ASIGNATURAS FROM HER_LABORATORIO l LEFT JOIN HER_LABORATORIO_DET_DOCENCIA d ON L.LAB_ID = d.LAB_ID GROUP BY L.LAB_ID) CA ON CA.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(s.LAB_ID) AS ENSAYOS_SERVICIOS FROM HER_LABORATORIO l LEFT JOIN HER_LABORATORIO_DETALLE_ENSAYO s ON L.LAB_ID = s.LAB_ID GROUP BY L.LAB_ID) CS ON CS.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT l.lab_id, COUNT(P.lab_id) AS PROYECTOS_INVESTIGACION FROM HER_LABORATORIO l LEFT JOIN HER_LABORATORIO_PROYECTO P ON l.lab_id = P.lab_id LEFT JOIN HER_PROYECTO PRY ON PRY.PRY_ID = P.PRY_ID WHERE PRY.EPR_ID IN ('AP', 'A', 'CN', 'S', 'PF', 'F') GROUP BY l.lab_id) CP ON CP.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(linea.LAB_ID) AS LINEAS_INVESTIGACION FROM HER_LABORATORIO l LEFT JOIN HER_LABORATORIO_DET_LINEA_INV linea ON L.LAB_ID = linea.LAB_ID GROUP BY L.LAB_ID) CL ON CL.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(CC.LAB_ID) AS AREAS_TEMATICAS FROM HER_LABORATORIO l LEFT JOIN HER_LABORATORIO_CLASIF_CONOC CC ON L.LAB_ID = CC.LAB_ID GROUP BY L.LAB_ID) CC ON CC.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(e.LAB_ID) AS EQUIPOS FROM her_laboratorio l LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID AND LDE_DADO_DE_BAJA = 0 GROUP BY L.LAB_ID) CE ON CE.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, SUM(nvl(e.lde_valor,0)) AS COSTO_EQUIPOS FROM her_laboratorio l LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID AND LDE_DADO_DE_BAJA = 0 GROUP BY L.LAB_ID) COSTO_EQUIPOS ON COSTO_EQUIPOS.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, MAX(LDE_FECHA_REGISTRO) AS FECHA_ACT_HV, NOMBRE_PERSONA(LDE_DOCUMENTO_PERSONA_REGISTRO,LDE_TIPO_DOC_PERSONA_REGISTRO) AS PERSONA_ACT_HV_EQUIPOS FROM her_laboratorio l "
				+ "LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID "
				+ "AND LDE_ID IN (SELECT max(lde_id) FROM HER_LABORATORIO_DETALLE_EQUIPO where (LAB_ID, LDE_FECHA_REGISTRO) in "
				+ "(SELECT LAB_ID,MAX(LDE_FECHA_REGISTRO) FROM HER_LABORATORIO_DETALLE_EQUIPO WHERE LAB_ID is not null AND LDE_DADO_DE_BAJA=0 AND LDE_TIENE_HOJA_DE_VIDA=1 GROUP BY LAB_ID) "
				+ "GROUP BY LAB_ID) GROUP BY L.LAB_ID,LDE_DOCUMENTO_PERSONA_REGISTRO,LDE_TIPO_DOC_PERSONA_REGISTRO) ACT_HV_EQUIPOS ON ACT_HV_EQUIPOS.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(e.LAB_ID) AS EQUIPOS FROM her_laboratorio l LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID AND LDE_DADO_DE_BAJA = 0 AND E.LDE_ESPECIALIZADO = 1 GROUP BY L.LAB_ID) EQ_ESPEC ON EQ_ESPEC.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(e.LAB_ID) AS EQUIPOS FROM her_laboratorio l LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID AND LDE_DADO_DE_BAJA = 0 AND E.LDE_ROBUSTO = 1 GROUP BY L.LAB_ID) EQ_ROBUSTO ON EQ_ROBUSTO.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(e.LAB_ID) AS EQUIPOS FROM her_laboratorio l LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID AND LDE_DADO_DE_BAJA = 0 AND E.LDE_ES_INSTRUMENTO_MEDICION = 1 GROUP BY L.LAB_ID) EQ_MEDICION ON EQ_MEDICION.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(e.LAB_ID) AS EQUIPOS FROM her_laboratorio l LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID AND LDE_DADO_DE_BAJA = 0 AND E.LDE_MANTENIMIENTO NOT IN (26) GROUP BY L.LAB_ID) REQ_MANTTO ON REQ_MANTTO.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(e.LAB_ID) AS EQUIPOS FROM her_laboratorio l LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID AND LDE_DADO_DE_BAJA = 0 AND E.LDE_TIENE_HOJA_DE_VIDA = 1 GROUP BY L.LAB_ID) EQ_HV ON EQ_HV.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(e.LAB_ID) AS EQUIPOS FROM her_laboratorio l LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID AND LDE_DADO_DE_BAJA = 0 AND E.LDE_EN_USO = 1 GROUP BY L.LAB_ID) EQ_EN_USO ON EQ_EN_USO.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(e.LAB_ID) AS EQUIPOS FROM her_laboratorio l "
				+ "LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID AND LDE_DADO_DE_BAJA = 0 AND E.LDE_ID IN (SELECT DISTINCT(LDE_ID) FROM HER_LABORATORIO_ACT_EQUIPO WHERE HLAE_ESTADO_ACTIVIDAD IN ('E','P','L') AND HLAE_ACTIVA = 1) GROUP BY L.LAB_ID) EQ_ACTIV_REG ON EQ_ACTIV_REG.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(e.LAB_ID) AS EQUIPOS FROM her_laboratorio l "
				+ "LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID AND LDE_DADO_DE_BAJA = 0 AND E.LDE_ID IN (SELECT DISTINCT(LDE_ID) FROM HER_LABORATORIO_ACT_EQUIPO WHERE HLAE_ESTADO_ACTIVIDAD IN ('E') AND HLAE_ACTIVA = 1) GROUP BY L.LAB_ID) EQ_ACTIV_EJEC ON EQ_ACTIV_EJEC.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(e.LAB_ID) AS EQUIPOS FROM her_laboratorio l "
				+ "LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID AND LDE_DADO_DE_BAJA = 0 AND E.LDE_ID IN (SELECT DISTINCT(LDE_ID) FROM HER_LABORATORIO_ACT_EQUIPO WHERE HLAE_ESTADO_ACTIVIDAD IN ('P') AND HLAE_ACTIVA = 1) GROUP BY L.LAB_ID) EQ_ACTIV_PROG ON EQ_ACTIV_PROG.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(e.LAB_ID) AS EQUIPOS FROM her_laboratorio l "
				+ "LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID AND LDE_DADO_DE_BAJA = 0 AND E.LDE_ID IN (SELECT DISTINCT(LDE_ID) FROM HER_LABORATORIO_ACT_EQUIPO WHERE HLAE_ESTADO_ACTIVIDAD IN ('L') AND HLAE_ACTIVA = 1) GROUP BY L.LAB_ID) EQ_ACTIV_PLAN ON EQ_ACTIV_PLAN.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(e.LAB_ID) AS EQUIPOS FROM her_laboratorio l "
				+ "LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID AND LDE_DADO_DE_BAJA = 0 AND E.LDE_ID IN (SELECT DISTINCT(LDE_ID) FROM HER_LABORATORIO_ACT_EQUIPO WHERE HLAE_TIPO_ACTIVIDAD = 142 AND HLAE_ACTIVA = 1) GROUP BY L.LAB_ID) EQ_ACTIV_CALIBR ON EQ_ACTIV_CALIBR.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT L.LAB_ID, COUNT(e.LAB_ID) AS EQUIPOS FROM her_laboratorio l "
				+ "LEFT JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON L.LAB_ID = e.LAB_ID AND LDE_DADO_DE_BAJA = 0 AND E.LDE_ID IN (SELECT DISTINCT(LDE_ID) FROM HER_LABORATORIO_ACT_EQUIPO WHERE HLAE_TIPO_ACTIVIDAD = 145 AND HLAE_ACTIVA = 1) GROUP BY L.LAB_ID) EQ_ACTIV_MANTTO ON EQ_ACTIV_MANTTO.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT A.HAL_LAB_ID, COUNT(A.HAL_LAB_ID) as ARCHIVOS FROM HER_ARCHIVO_LABORATORIO A WHERE A.HAL_TIPO_ARCHIVO = 305 GROUP BY A.HAL_LAB_ID) ARCHIVOS_ACT_CREACION ON ARCHIVOS_ACT_CREACION.HAL_LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT A.HAL_LAB_ID, COUNT(A.HAL_LAB_ID) as ARCHIVOS FROM HER_ARCHIVO_LABORATORIO A WHERE A.HAL_TIPO_ARCHIVO = 7665 GROUP BY A.HAL_LAB_ID) ARCHIVOS_REGLAMENTO ON ARCHIVOS_REGLAMENTO.HAL_LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT A.HAL_LAB_ID, COUNT(A.HAL_LAB_ID) as ARCHIVOS FROM HER_ARCHIVO_LABORATORIO A WHERE A.HAL_TIPO_ARCHIVO = 1134 GROUP BY A.HAL_LAB_ID) ARCHIVOS_TARIFAS ON ARCHIVOS_TARIFAS.HAL_LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT A.HAL_LAB_ID, COUNT(A.HAL_LAB_ID) as ARCHIVOS FROM HER_ARCHIVO_LABORATORIO A WHERE A.HAL_TIPO_ARCHIVO = 1133 GROUP BY A.HAL_LAB_ID) ARCHIVOS_BROCHURE ON ARCHIVOS_BROCHURE.HAL_LAB_ID = L.LAB_ID "
				+ "LEFT JOIN (SELECT A.HAL_LAB_ID, COUNT(A.HAL_LAB_ID) as ARCHIVOS FROM HER_ARCHIVO_LABORATORIO A WHERE A.HAL_TIPO_ARCHIVO = 7654 GROUP BY A.HAL_LAB_ID) ARCHIVOS_COND_PREST_SERVICIOS ON ARCHIVOS_COND_PREST_SERVICIOS.HAL_LAB_ID = L.LAB_ID "
				+ where
				+ "AND L.LAB_ID NOT IN (SELECT LAB_ID FROM HER_SOLICITUD_LABORATORIOS WHERE TIPO_SOLICITUD = 181 AND HSL_ESTADO IN ('P')) "
				+ "ORDER BY L.LAB_ACTIVO DESC, S.SED_NOMBRE, L.LAB_NOMBRE, L.LAB_ID";
		
//		System.out.println("SQL: " + sql);

		listaReporte = servicioGeneral.obtenerMapa(sql,"FROM HER_LABORATORIO L");
		crearColumnas();
	}
	
	public void reporteEquiposDadosBaja() {
		reporteEquiposGenerico("1");
	}
	
	public void reporteEquipos() {
		reporteEquiposGenerico("0");
	}

	public void reporteEquiposGenerico(String dadoBaja) {
		headerDatatable = "Equipos Registrados";
		nombreArchivo = "Reporte_Equipos_Registrados_";
		
		String where = "WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' ";	
		where = obtenerComplementoWhere(where);
		
		String sql = "SELECT 1 as CANTIDAD,\r\n"
				+ "e.lab_id as ID_HERMES_LAB,\r\n"
				+ "L.LAB_NOMBRE as LABORATORIO,\r\n"
				+ "S.SED_NOMBRE as SEDE,\r\n"
				+ "FAC.DPN_NOMBRE as FACULTAD,\r\n"
				+ "DEP.DPN_NOMBRE as DEPARTAMENTO,\r\n"
				+ "EDF.EDF_CODIGO || ' - ' || EDF.EDF_NOMBBRE AS EDIFICIO, \r\n"
				+ "L.LAB_SALON as SALON, \r\n"
				+ "L.LAB_PISO as PISO, \r\n"
				+ "T.NOMBRE as TIPO_LABORATORIO, \r\n"
				+ "P.PER_NOMBRE1 || ' ' ||P.PER_NOMBRE2 || ' ' || P.PER_APELLIDO1 || ' ' || P.PER_APELLIDO2 as COORDINADOR, \r\n"
				+ "P.PER_EMAIL as EMAIL_COORDINADOR, \r\n"
				+ "E.LDE_ID as ID_HERMES_EQUIPO,\r\n"
				+ "E.LDE_PLACA as PLACA,\r\n"
				+ "E.LDE_EQUIPO as EQUIPO,\r\n"
				+ "E.LDE_MARCA as MARCA,\r\n"
				+ "E.LDE_MODELO as MODELO,\r\n"
				+ "E.LDE_SERIAL as SERIAL,\r\n"
				+ "E.LDE_VALOR as VALOR,\r\n"
				+ "CASE WHEN E.LDE_ESPECIALIZADO = 0 THEN 'NO' WHEN E.LDE_ESPECIALIZADO = 1 THEN 'SI' END as ESPECIALIZADO,\r\n"
				+ "CASE WHEN E.LDE_ROBUSTO = 0 THEN 'NO' WHEN E.LDE_ROBUSTO = 1 THEN 'SI' END as ROBUSTO,\r\n"
				+ "CASE WHEN E.LDE_EN_USO = 0 THEN 'NO' WHEN E.LDE_EN_USO = 1 THEN 'SI' END as EN_USO,\r\n"
				+ "CASE WHEN E.LDE_DADO_DE_BAJA = 0 THEN 'NO' WHEN E.LDE_DADO_DE_BAJA = 1 THEN 'SI' END as DADO_DE_BAJA,\r\n"
				+ "ESTADO.NOMBRE as ESTADO_CENSO,\r\n"
				+ "ESTADO_INV.NOMBRE as ESTADO_INV,\r\n"
				+ "CASE WHEN E.LDE_EXISTE_EN_BIENES = 0 THEN 'NO' WHEN E.LDE_EXISTE_EN_BIENES = 1 THEN 'SI' END as EXISTE_INV,\r\n"
				+ "CASE WHEN E.LDE_REQUIERE_MANTENIMIENTO = 0 THEN 'NO' WHEN E.LDE_REQUIERE_MANTENIMIENTO = 1 THEN 'SI' END as REQUIERE_MANTTO,\r\n"
				+ "CASE WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 0 THEN 'NO' WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 1 THEN 'SI' END as REQUIERE_CALIBRACION,\r\n"
				+ "MANTTO.NOMBRE as FRECUENCIA_MANTTO,\r\n"
				+ "CALIBR.NOMBRE as FRECUENCIA_CALIBRACION,\r\n"
				+ "CASE WHEN E.LDE_TIENE_HOJA_DE_VIDA = 0 THEN 'NO' WHEN E.LDE_TIENE_HOJA_DE_VIDA = 1 THEN 'SI' END as HOJA_DE_VIDA,\r\n"
				+ "CASE WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 0 THEN 'NO' WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 1 THEN 'SI' END as INSTRUMENTO_MEDICION,\r\n"
				+ "EM.ESPECIFICACIONES_METROLOGICAS as ESPECIFICACIONES_METROLOGICAS,ACT.ACTIVIDADES as ACTIVIDADES_REGISTRADAS,\r\n"
				+ "ACT_PROG.ACT_PROG as ACTIVIDADES_PROGRAMADAS,\r\n"
				+ "ACT_EJEC.ACT_EJEC as ACTIVIDADES_EJECUTADAS,\r\n"
				+ "ACT_PLAN.ACT_PLAN as ACTIVIDADES_PLANEADAS,\r\n"
				+ "ACT_MANTTO.ACT_MANTTO as ACTIVIDADES_MANTTO_PREV,\r\n"
				+ "ACT_CALIBR.ACT_CALIBR as ACTIVIDADES_CALIBRACION,\r\n"
				+ "ACT_MANTTO_CORR.ACT_MANTTO_CORR as ACTIVIDADES_MANTTO_CORR,\r\n"
				+ "ACT_FALLO.ACT_FALLO AS FALLOS,\r\n"
				+ "ACT_FALLOS_NO_SOL.ACT_FALLOS_NO_SOL AS FALLOS_SIN_SOLUCION,\r\n"
				+ "ACT_VERIF.ACT_VERIF as ACTIVIDADES_VERIFICACION,\r\n"
				+ "ACT_REVIS.ACT_REVIS as ACTIVIDADES_REVISION_GARANTIA,\r\n"
				+ "ARCHIVOS.ARCHIVOS as ARCHIVOS,\r\n"
				+ "ARCHIVOS_MANTTO.ARCHIVOS as ARCHIVOS_MANTTO,\r\n"
				+ "US.USO_EN_SERVICIOS as USO_EN_SERVICIOS,\r\n"
				+ "EMP_FAB.EMP_NOMBRE AS EMPRESA_FABRICANTE,\r\n"
				+ "E.LDE_ANNIO_FABRICACION AS AÑO_FABRICACION,\r\n"
				+ "E.LDE_FECHA_INSTALACION AS FECHA_INSTALACION,\r\n"
				+ "FLOOR(MONTHS_BETWEEN(SYSDATE, E.LDE_FECHA_INSTALACION) / 12) AS AÑOS_DESDE_INSTALACION,\r\n"
				+ "CASE WHEN FLOOR(MONTHS_BETWEEN(SYSDATE, E.LDE_FECHA_INSTALACION) / 12) > 10 THEN 'SI' ELSE 'NO' END as MAYOR_DIEZ_AÑOS,\r\n"
				+ "E.LDE_VIDA_UTIL_ANNIOS AS VIDA_UTIL_AÑOS,\r\n"
				+ "MEC_ADQ.NOMBRE AS MECANISMO_ADQUISICION,\r\n"
				+ "E.LDE_ACCESORIOS AS ACCESORIOS,\r\n"
				+ "E.LDE_SISTEMA_ALIMENTACION AS LDE_SISTEMA_ALIMENTACION,\r\n"
				+ "NVL(E.LDE_PORCENTAJE_COMPLETITUD,0) || '%' as AVANCE,\r\n"
				+ "E.LDE_FECHA_REGISTRO as FECHA_ACTUALIZACION,\r\n"
				+ (dadoBaja.equals("1") ? "E.LDE_DADO_BAJA_FECHA as FECHA_DADO_DE_BAJA,\r\n" : "")
				+ (dadoBaja.equals("1") ? "E.LDE_DADO_BAJA_MOTIVO as MOTIVO_DADO_DE_BAJA,\r\n" : "")
				+ (dadoBaja.equals("1") ? "CASE WHEN ARCHIVOS_DADO_BAJA.ARCHIVOS <= 0 THEN 'NO' WHEN ARCHIVOS_DADO_BAJA.ARCHIVOS >= 1 THEN 'SI' END as ARCHIVOS_DADO_BAJA,\r\n" : "")
				+ "NOMBRE_PERSONA(LDE_DOCUMENTO_PERSONA_REGISTRO, LDE_TIPO_DOC_PERSONA_REGISTRO) AS PERSONA_ACTUALIZACION,\r\n"
				+ "LDE_OBSERVACIONES as OBSERVACIONES \r\n"
				+ "FROM \r\n"
				+ " HER_LABORATORIO_DETALLE_EQUIPO E \r\n"
				+ "LEFT JOIN HER_LABORATORIO L ON L.LAB_ID = E.LAB_ID \r\n"
				+ "LEFT JOIN HER_SEDE S ON L.SED_ID = S.SED_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA FAC ON L.LAB_FACULTAD = FAC.DPN_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA DEP ON DEP.DPN_ID = NVL(L.LAB_DEPTO, 0) \r\n"
				+ "LEFT JOIN HER_TIPOS MANTTO ON MANTTO.ID  = E.LDE_MANTENIMIENTO \r\n"
				+ "LEFT JOIN HER_TIPOS CALIBR ON CALIBR.ID = E.LDE_CALIBRACION \r\n"
				+ "LEFT JOIN HER_TIPOS ESTADO ON ESTADO.ID = E.LDE_ESTADO_FISICO_CENSO \r\n"
				+ "LEFT JOIN HER_TIPOS ESTADO_INV ON ESTADO_INV.ID = E.LDE_ESTADO_FISICO \r\n"
				+ "LEFT JOIN HER_TIPOS MEC_ADQ ON MEC_ADQ.ID = E.LDE_MECANISMO_ADQUISICION_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ESPECIFICACIONES_METROLOGICAS FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_EQUIPO_METROL e ON l.lde_id = e.lde_id GROUP BY l.lde_id) EM ON EM.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as USO_EN_SERVICIOS FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ENSAYO_EQUIPOS e ON l.lde_id = e.lde_id GROUP BY l.lde_id) US ON US.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACTIVIDADES FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT ON ACT.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_PROG FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_ESTADO_ACTIVIDAD IN ('P') AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_PROG ON ACT_PROG.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_EJEC FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_ESTADO_ACTIVIDAD IN ('E') AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_EJEC ON ACT_EJEC.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_PLAN FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_ESTADO_ACTIVIDAD IN ('L') AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_PLAN ON ACT_PLAN.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_MANTTO FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_TIPO_ACTIVIDAD = 145 AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_MANTTO ON ACT_MANTTO.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_CALIBR FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_TIPO_ACTIVIDAD = 142 AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_CALIBR ON ACT_CALIBR.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_MANTTO_CORR FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_TIPO_ACTIVIDAD = 144 AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_MANTTO_CORR ON ACT_MANTTO_CORR.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_FALLO FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LAB_EQUIPO_REP_DANIO e ON l.lde_id = e.lde_id GROUP BY l.lde_id) ACT_FALLO ON ACT_FALLO.LDE_ID = E.LDE_ID\r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_FALLOS_NO_SOL FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LAB_EQUIPO_REP_DANIO e ON l.lde_id = e.lde_id AND E.LERD_FECHA_SOLUCION IS NULL GROUP BY l.lde_id) ACT_FALLOS_NO_SOL ON ACT_FALLOS_NO_SOL.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_VERIF FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_TIPO_ACTIVIDAD = 146 AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_VERIF ON ACT_VERIF.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_REVIS FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_TIPO_ACTIVIDAD = 148 AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_REVIS ON ACT_REVIS.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(a.hal_lde_id) as ARCHIVOS FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_ARCHIVO_LABORATORIO a ON l.lde_id = a.hal_lde_id GROUP BY l.lde_id) ARCHIVOS ON ARCHIVOS.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(a.hal_lde_id) as ARCHIVOS FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_ARCHIVO_LABORATORIO a ON l.lde_id = a.hal_lde_id AND a.hal_tipo_archivo = 88 GROUP BY l.lde_id) ARCHIVOS_MANTTO ON ARCHIVOS_MANTTO.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(a.hal_lde_id) as ARCHIVOS FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_ARCHIVO_LABORATORIO a ON l.lde_id = a.hal_lde_id AND a.hal_tipo_archivo = 7722 GROUP BY l.lde_id) ARCHIVOS_DADO_BAJA ON ARCHIVOS_DADO_BAJA.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN HER_TIPOS T ON T.ID = l.LAB_TIPO \r\n"
				+ "LEFT JOIN HER_EDIFICIO EDF ON EDF.EDF_ID = L.EDF_ID \r\n"
				+ "LEFT JOIN HER_PERSONA_LABORATORIO PL ON PL.LAB_ID = L.LAB_ID AND PL.ROL_ID = 'CO' \r\n"
				+ "LEFT JOIN HER_PERSONA P ON P.PER_ID = PL.PER_ID AND P.TDO_ID = PL.TDO_ID \r\n"
				+ "LEFT JOIN HER_EMPRESA EMP_FAB ON EMP_FAB.EMP_ID = E.LDE_EMP_FAB_ID \r\n"
				+ where
				+ " AND E.LDE_DADO_DE_BAJA = " + dadoBaja + " \r\n"
				+ "AND E.LAB_ID IS NOT NULL \r\n"
				+ "ORDER BY L.LAB_NOMBRE, E.LDE_ID DESC";

		listaReporte = servicioGeneral.obtenerMapa(sql);
		
		crearColumnas();
	}
	
	public void reporteCriticidadEquipos() {
		headerDatatable = "Criticidad de equipos";
		nombreArchivo = "Reporte_Criticidad_Equipos_";
		
		String where = "WHERE L.LAB_ACTIVO = '1' ";	
		where = obtenerComplementoWhere(where);
		
		String sql = "SELECT \r\n"
				+ "e.lab_id as ID_HERMES_LAB,\r\n"
				+ "L.LAB_NOMBRE as LABORATORIO,\r\n"
				+ "S.SED_NOMBRE as SEDE,\r\n"
				+ "FAC.DPN_NOMBRE as FACULTAD,\r\n"
				+ "DEP.DPN_NOMBRE as DEPARTAMENTO,\r\n"
				+ "E.LDE_ID as ID_HERMES_EQUIPO,\r\n"
				+ "E.LDE_PLACA as PLACA,\r\n"
				+ "E.LDE_EQUIPO as EQUIPO,\r\n"
				+ "E.LDE_MARCA as MARCA,\r\n"
				+ "E.LDE_MODELO as MODELO,\r\n"
				+ "E.LDE_SERIAL as SERIAL,\r\n"
				+ "E.LDE_CALIFICACION_RIESGO as CALIFICACION_RIESGO,\r\n"
				+ "E.LDE_CALIFICACION_IMPACTO as CALCULO_IMPACTO,\r\n"
				+ "CE_IMP_OPER_LAB.NOMBRE as OPERACIONES_LABORATORIO,\r\n"
				+ "CE_IMP_SEG_USUARIOS.NOMBRE as SEGURIDAD_USUARIOS,\r\n"
				+ "CE_IMP_DANIOS_INFRA.NOMBRE as DANIOS_INFRA_FISICA,\r\n"
				+ "CE_IMP_DANIOS_AMBIENT.NOMBRE as DANIOS_MEDIOAMBIENTE,\r\n"
				+ "CE_IMP_IMAGEN_UN.NOMBRE as IMAGEN_UN,\r\n"
				+ "CE_IMP_QUEJAS.NOMBRE as QUEJAS_SANCIONES_ECONO,\r\n"
				+ "CE_IMP_IMPACTOS_ECON.NOMBRE as IMPACTOS_ECONOMICOS,\r\n"
				+ "E.LDE_CALIFICACION_PROBABILIDAD as CALCULO_PROBABILIDAD,\r\n"
				+ "CE_PROB_REP_FALLA.NOMBRE as REPORTE_FALLAS,\r\n"
				+ "CE_PROB_TIEMPO_TRAB.NOMBRE as TIEMPO_TRABAJO,\r\n"
				+ "CE_PROB_COND_AMB.NOMBRE as COND_AMBIENTALES_UBICACION,\r\n"
				+ "CE_PROB_METROLOGIA.NOMBRE as MANTTO_CONFIRM_METROLOGICA,\r\n"
				+ "CASE WHEN E.LDE_EN_USO = 0 THEN 'NO' WHEN E.LDE_EN_USO = 1 THEN 'SI' END as EN_USO,\r\n"
				+ "ESTADO.NOMBRE as ESTADO_CENSO,\r\n"
				+ "ESTADO_INV.NOMBRE as ESTADO_INV,\r\n"
				+ "E.LDE_FECHA_INSTALACION AS FECHA_INSTALACION,\r\n"
				+ "E.LDE_VIDA_UTIL_ANNIOS AS VIDA_UTIL_AÑOS,\r\n"
				+ "ACT_FALLO.ACT_FALLO AS FALLOS,\r\n"
				+ "ACT_FALLOS_NO_SOL.ACT_FALLOS_NO_SOL AS FALLOS_SIN_SOLUCION,\r\n"
				+ "CASE WHEN E.LDE_REQUIERE_MANTENIMIENTO = 0 THEN 'NO' WHEN E.LDE_REQUIERE_MANTENIMIENTO = 1 THEN 'SI' END as REQUIERE_MANTTO,\r\n"
				+ "ACT_MANTTO.ACT_MANTTO as ACTIVIDADES_MANTTO,\r\n"
				+ "FECHA_PROX_MANTTO.FECHA as FECHA_PROX_MANTTO_PROG,\r\n"
				+ "CASE WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 0 THEN 'NO' WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 1 THEN 'SI' END as REQUIERE_CALIBRACION,\r\n"
				+ "ACT_CALIBR.ACT_CALIBR as ACTIVIDADES_CALIBRACION, \r\n"
				+ "FECHA_PROX_CALIB.FECHA as FECHA_PROX_CALIB \r\n"
				+ "FROM \r\n"
				+ " HER_LABORATORIO_DETALLE_EQUIPO E \r\n"
				+ "LEFT JOIN HER_LABORATORIO L ON L.LAB_ID = E.LAB_ID \r\n"
				+ "LEFT JOIN HER_SEDE S ON L.SED_ID = S.SED_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA FAC ON L.LAB_FACULTAD = FAC.DPN_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA DEP ON DEP.DPN_ID = NVL(L.LAB_DEPTO, 0) \r\n"
				+ "LEFT JOIN HER_TIPOS MANTTO ON MANTTO.ID  = E.LDE_MANTENIMIENTO \r\n"
				+ "LEFT JOIN HER_TIPOS CALIBR ON CALIBR.ID = E.LDE_CALIBRACION \r\n"
				+ "LEFT JOIN HER_TIPOS ESTADO ON ESTADO.ID = E.LDE_ESTADO_FISICO_CENSO \r\n"
				+ "LEFT JOIN HER_TIPOS ESTADO_INV ON ESTADO_INV.ID = E.LDE_ESTADO_FISICO \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_MANTTO FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_TIPO_ACTIVIDAD IN (144,145) AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_MANTTO ON ACT_MANTTO.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT e.LDE_ID AS LDE_ID, MIN(HLAE_FECHA_ACTIVIDAD) AS FECHA FROM HER_LABORATORIO_ACT_EQUIPO e WHERE e.HLAE_TIPO_ACTIVIDAD IN (144,145) AND e.HLAE_ACTIVA = 1 AND e.HLAE_ESTADO_ACTIVIDAD IN ('P') AND e.HLAE_FECHA_ACTIVIDAD >= SYSDATE GROUP BY LDE_ID, HLAE_FECHA_ACTIVIDAD ORDER BY HLAE_FECHA_ACTIVIDAD) FECHA_PROX_MANTTO ON FECHA_PROX_MANTTO.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT e.LDE_ID AS LDE_ID, MIN(HLAE_FECHA_ACTIVIDAD) AS FECHA FROM HER_LABORATORIO_ACT_EQUIPO e WHERE e.HLAE_TIPO_ACTIVIDAD IN (142) AND e.HLAE_ACTIVA = 1 AND e.HLAE_ESTADO_ACTIVIDAD IN ('P') AND e.HLAE_FECHA_ACTIVIDAD >= SYSDATE GROUP BY LDE_ID, HLAE_FECHA_ACTIVIDAD ORDER BY HLAE_FECHA_ACTIVIDAD) FECHA_PROX_CALIB ON FECHA_PROX_CALIB.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_CALIBR FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_TIPO_ACTIVIDAD = 142 AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_CALIBR ON ACT_CALIBR.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_FALLO FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LAB_EQUIPO_REP_DANIO e ON l.lde_id = e.lde_id GROUP BY l.lde_id) ACT_FALLO ON ACT_FALLO.LDE_ID = E.LDE_ID\r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_FALLOS_NO_SOL FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LAB_EQUIPO_REP_DANIO e ON l.lde_id = e.lde_id AND E.LERD_FECHA_SOLUCION IS NULL GROUP BY l.lde_id) ACT_FALLOS_NO_SOL ON ACT_FALLOS_NO_SOL.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN HER_TIPOS CE_IMP_OPER_LAB ON CE_IMP_OPER_LAB.ID = E.LDE_CE_IMP_OPER_LAB \r\n"
				+ "LEFT JOIN HER_TIPOS CE_IMP_SEG_USUARIOS ON CE_IMP_SEG_USUARIOS.ID = E.LDE_CE_IMP_SEG_USUARIOS \r\n"
				+ "LEFT JOIN HER_TIPOS CE_IMP_DANIOS_INFRA ON CE_IMP_DANIOS_INFRA.ID = E.LDE_CE_IMP_DANIOS_INFRA \r\n"
				+ "LEFT JOIN HER_TIPOS CE_IMP_DANIOS_AMBIENT ON CE_IMP_DANIOS_AMBIENT.ID = E.LDE_CE_IMP_DANIOS_AMBIENT \r\n"
				+ "LEFT JOIN HER_TIPOS CE_IMP_IMAGEN_UN ON CE_IMP_IMAGEN_UN.ID = E.LDE_CE_IMP_IMAGEN_UN \r\n"
				+ "LEFT JOIN HER_TIPOS CE_IMP_QUEJAS ON CE_IMP_QUEJAS.ID = E.LDE_CE_IMP_QUEJAS \r\n"
				+ "LEFT JOIN HER_TIPOS CE_IMP_IMPACTOS_ECON ON CE_IMP_IMPACTOS_ECON.ID = E.LDE_CE_IMP_IMPACTOS_ECON \r\n"
				+ "LEFT JOIN HER_TIPOS CE_PROB_REP_FALLA ON CE_PROB_REP_FALLA.ID = E.LDE_CE_PROB_REP_FALLA \r\n"
				+ "LEFT JOIN HER_TIPOS CE_PROB_TIEMPO_TRAB ON CE_PROB_TIEMPO_TRAB.ID = E.LDE_CE_PROB_TIEMPO_TRAB \r\n"
				+ "LEFT JOIN HER_TIPOS CE_PROB_COND_AMB ON CE_PROB_COND_AMB.ID = E.LDE_CE_PROB_COND_AMB \r\n"
				+ "LEFT JOIN HER_TIPOS CE_PROB_METROLOGIA ON CE_PROB_METROLOGIA.ID = E.LDE_CE_PROB_METROLOGIA \r\n"
				+ where
				+ " AND E.LAB_ID IS NOT NULL \r\n"
				+ "AND E.LDE_CALIFICACION_RIESGO IS NOT NULL \r\n"
				+ "ORDER BY L.LAB_NOMBRE, E.LDE_ID DESC";
		
		System.out.println("SQL: " + sql);

		listaReporte = servicioGeneral.obtenerMapa(sql);
		
		crearColumnas();
	}
	
	public void reporteEquiposFacultadSede() {
		headerDatatable = "Equipos Registrados";
		nombreArchivo = "Reporte_Equipos_Registrados_";
		String where = " WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' ";

		if (sedeSeleccionada.getId() != null && sedeSeleccionada.getId() != 0)
			where += "AND L.SED_ID = " + sedeSeleccionada.getId();
		
		String sql = "SELECT 1 as CANTIDAD,\r\n"
				+ "e.lab_id as ID_HERMES_LAB,\r\n"
				+ "S.SED_NOMBRE as SEDE,\r\n"
				+ "L.LAB_NOMBRE as LABORATORIO,\r\n"
				+ "FAC.DPN_NOMBRE as FACULTAD,\r\n"
				+ "DEP.DPN_NOMBRE as DEPARTAMENTO,\r\n"
				+ "E.LDE_PLACA as PLACA,\r\n"
				+ "E.LDE_EQUIPO as EQUIPO,\r\n"
				+ "E.LDE_MARCA as MARCA,\r\n"
				+ "E.LDE_MODELO as MODELO,\r\n"
				+ "E.LDE_SERIAL as SERIAL,\r\n"
				+ "E.LDE_VALOR as VALOR,\r\n"
				+ "CASE WHEN E.LDE_ESPECIALIZADO = 0 THEN 'NO' WHEN E.LDE_ESPECIALIZADO = 1 THEN 'SI' END as ESPECIALIZADO,\r\n"
				+ "CASE WHEN E.LDE_MAYOR_DIEZ_ANNIOS = 0 THEN 'NO' WHEN E.LDE_MAYOR_DIEZ_ANNIOS = 1 THEN 'SI' END as MAYOR_DIEZ_ANNIOS,\r\n"
				+ "CASE WHEN E.LDE_EN_USO = 0 THEN 'NO' WHEN E.LDE_EN_USO = 1 THEN 'SI' END as EN_USO,\r\n"
				+ "CASE WHEN E.LDE_DADO_DE_BAJA = 0 THEN 'NO' WHEN E.LDE_DADO_DE_BAJA = 1 THEN 'SI' END as DADO_DE_BAJA,\r\n"
				+ "ESTADO.NOMBRE as ESTADO_CENSO,\r\n"
				+ "ESTADO_INV.NOMBRE as ESTADO_INV,\r\n"
				+ "CASE WHEN E.LDE_EXISTE_EN_BIENES = 0 THEN 'NO' WHEN E.LDE_EXISTE_EN_BIENES = 1 THEN 'SI' END as EXISTE_INV,\r\n"
				+ "CASE WHEN E.LDE_REQUIERE_MANTENIMIENTO = 0 THEN 'NO' WHEN E.LDE_REQUIERE_MANTENIMIENTO = 1 THEN 'SI' END as REQUIERE_MANTTO,\r\n"
				+ "CASE WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 0 THEN 'NO' WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 1 THEN 'SI' END as REQUIERE_CALIBRACION,\r\n"
				+ "MANTTO.NOMBRE as FRECUENCIA_MANTTO,\r\n"
				+ "CALIBR.NOMBRE as FRECUENCIA_CALIBRACION,\r\n"
				+ "CASE WHEN E.LDE_TIENE_HOJA_DE_VIDA = 0 THEN 'NO' WHEN E.LDE_TIENE_HOJA_DE_VIDA = 1 THEN 'SI' END as HOJA_DE_VIDA,\r\n"
				+ "CASE WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 0 THEN 'NO' WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 1 THEN 'SI' END as INSTRUMENTO_MEDICION,\r\n"
				+ "EM.ESPECIFICACIONES_METROLOGICAS as ESPECIFICACIONES_METROLOGICAS,"
				+ "ACT.ACTIVIDADES as ACTIVIDADES_REGISTRADAS,\r\n"
				+ "ACT_PROG.ACT_PROG as ACTIVIDADES_PROGRAMADAS,\r\n"
				+ "ACT_EJEC.ACT_EJEC as ACTIVIDADES_EJECUTADAS,\r\n"
				+ "ACT_PLAN.ACT_PLAN as ACTIVIDADES_PLANEADAS,\r\n"
				+ "ACT_MANTTO.ACT_MANTTO as ACTIVIDADES_MANTTO_PREV,\r\n"
				+ "ACT_CALIBR.ACT_CALIBR as ACTIVIDADES_CALIBRACION,\r\n"
				+ "ACT_MANTTO_CORR.ACT_MANTTO_CORR as ACTIVIDADES_MANTTO_CORR,\r\n"
				+ "ACT_VERIF.ACT_VERIF as ACTIVIDADES_VERIFICACION,\r\n"
				+ "ACT_REVIS.ACT_REVIS as ACTIVIDADES_REVISION_GARANTIA,\r\n"
				+ "ARCHIVOS.ARCHIVOS as ARCHIVOS,\r\n"
				+ "ARCHIVOS_MANTTO.ARCHIVOS as ARCHIVOS_MANTTO,\r\n"
				+ "MEC_ADQ.NOMBRE AS MECANISMO_ADQUISICION,\r\n"
				+ "E.LDE_ACCESORIOS AS ACCESORIOS,\r\n"
				+ "E.LDE_SISTEMA_ALIMENTACION AS LDE_SISTEMA_ALIMENTACION,\r\n"
				+ "US.USO_EN_SERVICIOS as USO_EN_SERVICIOS,\r\n"
				+ "NVL(E.LDE_PORCENTAJE_COMPLETITUD,0) || '%' as AVANCE,\r\n"
				+ "E.LDE_FECHA_REGISTRO as FECHA_ACTUALIZACION,\r\n"
				+ "NOMBRE_PERSONA(LDE_DOCUMENTO_PERSONA_REGISTRO, LDE_TIPO_DOC_PERSONA_REGISTRO) AS PERSONA_ACTUALIZACION,\r\n" 
				+ "LDE_OBSERVACIONES as OBSERVACIONES \r\n"
				+ "FROM \r\n"
				+ " HER_LABORATORIO_DETALLE_EQUIPO E \r\n"
				+ "LEFT JOIN HER_LABORATORIO L ON L.LAB_ID = E.LAB_ID \r\n"
				+ "LEFT JOIN HER_SEDE S ON L.SED_ID = S.SED_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA FAC ON L.LAB_FACULTAD = FAC.DPN_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA DEP ON DEP.DPN_ID = NVL(L.LAB_DEPTO, 0) \r\n"
				+ "LEFT JOIN HER_TIPOS MANTTO ON MANTTO.ID  = E.LDE_MANTENIMIENTO \r\n"
				+ "LEFT JOIN HER_TIPOS CALIBR ON CALIBR.ID = E.LDE_CALIBRACION \r\n"
				+ "LEFT JOIN HER_TIPOS ESTADO ON ESTADO.ID = E.LDE_ESTADO_FISICO_CENSO \r\n"
				+ "LEFT JOIN HER_TIPOS ESTADO_INV ON ESTADO_INV.ID = E.LDE_ESTADO_FISICO \r\n"
				+ "LEFT JOIN HER_TIPOS MEC_ADQ ON MEC_ADQ.ID = E.LDE_MECANISMO_ADQUISICION_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ESPECIFICACIONES_METROLOGICAS FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_EQUIPO_METROL e ON l.lde_id = e.lde_id GROUP BY l.lde_id) EM ON EM.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as USO_EN_SERVICIOS FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ENSAYO_EQUIPOS e ON l.lde_id = e.lde_id GROUP BY l.lde_id) US ON US.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACTIVIDADES FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT ON ACT.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_PROG FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_ESTADO_ACTIVIDAD IN ('P') AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_PROG ON ACT_PROG.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_EJEC FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_ESTADO_ACTIVIDAD IN ('E') AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_EJEC ON ACT_EJEC.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_PLAN FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_ESTADO_ACTIVIDAD IN ('L') AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_PLAN ON ACT_PLAN.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_MANTTO FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_TIPO_ACTIVIDAD = 145 AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_MANTTO ON ACT_MANTTO.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_CALIBR FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_TIPO_ACTIVIDAD = 142 AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_CALIBR ON ACT_CALIBR.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_MANTTO_CORR FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_TIPO_ACTIVIDAD = 144 AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_MANTTO_CORR ON ACT_MANTTO_CORR.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_VERIF FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_TIPO_ACTIVIDAD = 146 AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_VERIF ON ACT_VERIF.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(e.lde_id) as ACT_REVIS FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_LABORATORIO_ACT_EQUIPO e ON l.lde_id = e.lde_id AND e.HLAE_TIPO_ACTIVIDAD = 148 AND e.HLAE_ACTIVA = 1 GROUP BY l.lde_id) ACT_REVIS ON ACT_REVIS.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(a.hal_lde_id) as ARCHIVOS FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_ARCHIVO_LABORATORIO a ON l.lde_id = a.hal_lde_id GROUP BY l.lde_id) ARCHIVOS ON ARCHIVOS.LDE_ID = E.LDE_ID \r\n"
				+ "LEFT JOIN (SELECT l.lde_id, COUNT(a.hal_lde_id) as ARCHIVOS FROM her_laboratorio_detalle_equipo l LEFT JOIN HER_ARCHIVO_LABORATORIO a ON l.lde_id = a.hal_lde_id AND a.hal_tipo_archivo = 88 GROUP BY l.lde_id) ARCHIVOS_MANTTO ON ARCHIVOS_MANTTO.LDE_ID = E.LDE_ID \r\n"
				+ where
				+ " AND E.LDE_DADO_DE_BAJA = 0 \r\n"
				+ "AND E.LAB_ID IS NOT NULL \r\n"
				+ "ORDER BY L.LAB_NOMBRE, E.LDE_ID DESC";

		listaReporte = servicioGeneral.obtenerMapa(sql);
		
		crearColumnas();
	}

	public void reporteActividadesRegistradas() {
		headerDatatable = "Actividades Registradas";
		nombreArchivo = "Reporte_Actividades_Registradas_";

		String where = "WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' AND e.LDE_DADO_DE_BAJA = '0' ";

		where = obtenerComplementoWhere(where);
		
		String sql = "SELECT \r\n"
				+ "L.LAB_ID AS ID_LABORATORIO, "
				+ "l.lab_nombre AS LABORATORIO, "
				+ "s.sed_nombre AS SEDE, "
				+ "f.dpn_nombre as FACULTAD, "
				+ "d.dpn_nombre as DEPARTAMENTO, "
				+ "EDF.EDF_CODIGO || ' - ' || EDF.EDF_NOMBBRE AS EDIFICIO, "
				+ "L.LAB_SALON as SALON, "
				+ "L.LAB_PISO AS PISO, "
				+ "TIPO_LAB.NOMBRE as TIPO_LABORATORIO, "
				+ "P.PER_NOMBRE1 || ' ' ||P.PER_NOMBRE2 || ' ' || P.PER_APELLIDO1 || ' ' || P.PER_APELLIDO2 as COORDINADOR, "
				+ "P.PER_EMAIL as EMAIL_COORDINADOR, "
				+ "AREA_PR_OCDE.NOMBRE AS AREA_OCDE_PRINCIPAL, "
				+ "e.LDE_ID as ID_HERMES_EQUIPO, "
				+ "e.lde_placa AS PLACA, "
				+ "E.LDE_EQUIPO AS EQUIPO, "
				+ "CASE WHEN E.LDE_REQUIERE_MANTENIMIENTO = 0 THEN 'NO' WHEN E.LDE_REQUIERE_MANTENIMIENTO = 1 THEN 'SI' END as EQUIPO_REQ_MANTTO_ACTUALMENTE, "
				+ "CASE WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 0 THEN 'NO' WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 1 THEN 'SI' END as EQUIPO_REQ_CALIB_ACTUALMENTE, "
				+ "t.nombre AS TIPO_ACTIVIDAD, "
				+ "CASE WHEN hlae_estado_actividad = 'P' THEN 'Programada' WHEN hlae_estado_actividad = 'E' THEN 'Ejecutada' WHEN hlae_estado_actividad = 'L' THEN 'Planeada' else 'ERROR' END AS ESTADO_ACTIVIDAD, "
				+ "TO_CHAR(NVL(HLAE_FECHA_EJECUCION, HLAE_FECHA_ACTIVIDAD), 'yyyy-MM-dd') AS FECHA_ACTIVIDAD, "
				+ "TO_CHAR(NVL(HLAE_FECHA_EJECUCION, HLAE_FECHA_ACTIVIDAD), 'YYYY') AS ANIO_ACTIVIDAD,"
				+ "TO_CHAR(NVL(HLAE_FECHA_EJECUCION, HLAE_FECHA_ACTIVIDAD), 'MM') AS MES_ACTIVIDAD,"
				+ "TO_CHAR(NVL(HLAE_FECHA_EJECUCION, HLAE_FECHA_ACTIVIDAD), 'DD') AS DIA_ACTIVIDAD,"
				+ "HLAE_COSTO_ACTIVIDAD AS COSTO_ACTIVIDAD, "
				+ "frec.nombre AS FRECUENCIA, "
				+ "mod.nombre AS MODALIDAD, "
				+ "HLAE_RESPONSABLE AS RESPONSABLE, "
				+ "CASE WHEN ARCHIVOS.ARCHIVOS <= 0 THEN 'NO' WHEN ARCHIVOS.ARCHIVOS >= 1 THEN 'SI' END AS ARCHIVOS, "
				+ "HLAE_FECHA_REGISTRO AS FECHA_REGISTRO, "
				+ "HLAE_OBSERVACIONES AS OBSERVACIONES_ACTIVIDAD_DESCR "
				+ "FROM HER_LABORATORIO_ACT_EQUIPO a\r\n"
				+ "INNER JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON a.LDE_ID = e.lde_id\r\n"
				+ "INNER JOIN HER_LABORATORIO L ON l.lab_id = e.lab_id\r\n"
				+ "LEFT JOIN HER_SEDE S ON L.SED_ID = S.SED_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA F ON L.LAB_FACULTAD = F.DPN_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA D ON D.DPN_ID = NVL(L.LAB_DEPTO, 0)\r\n"
				+ "LEFT JOIN HER_TIPOS AREA_PR_OCDE ON AREA_PR_OCDE.ID = L.LAB_AREA_PRINCIPAL\r\n"
				+ "LEFT JOIN HER_TIPOS TIPO_LAB ON TIPO_LAB.ID = L.LAB_TIPO\r\n"
				+ "LEFT JOIN HER_EDIFICIO EDF ON EDF.EDF_ID = L.EDF_ID\r\n"
				+ "LEFT JOIN HER_PERSONA_LABORATORIO PL ON PL.LAB_ID = L.LAB_ID AND PL.ROL_ID = 'CO'\r\n"
				+ "LEFT JOIN HER_PERSONA P ON P.PER_ID = PL.PER_ID AND P.TDO_ID = PL.TDO_ID\r\n"
				+ "LEFT JOIN HER_TIPOS t ON a.hlae_tipo_actividad = t.id\r\n"
				+ "LEFT JOIN HER_TIPOS frec ON frec.id = a.HLAE_FRECUENCIA\r\n"
				+ "LEFT JOIN HER_TIPOS mod ON mod.id = a.HLAE_MODALIDAD\r\n"
				+ "LEFT JOIN (SELECT l.HLAE_ID, COUNT(a.HAL_HLAE) as ARCHIVOS FROM HER_LABORATORIO_ACT_EQUIPO l LEFT JOIN HER_ARCHIVO_LABORATORIO a ON l.HLAE_ID = a.HAL_HLAE AND a.hal_tipo_archivo in (7277,7278,7279,7258,7269,7275) GROUP BY l.HLAE_ID) ARCHIVOS ON ARCHIVOS.HLAE_ID = A.HLAE_ID \r\n"
				+  where
				+ "AND a.HLAE_ACTIVA = 1\r\n"
				+ "UNION ALL\r\n"
				+ "SELECT\r\n"
				+ "L.LAB_ID AS ID_LABORATORIO,\r\n"
				+ "l.lab_nombre AS LABORATORIO,\r\n"
				+ "s.sed_nombre AS SEDE,\r\n"
				+ "f.dpn_nombre as FACULTAD,\r\n"
				+ "d.dpn_nombre as DEPARTAMENTO,\r\n"
				+ "EDF.EDF_CODIGO || ' - ' || EDF.EDF_NOMBBRE AS EDIFICIO,\r\n"
				+ "L.LAB_SALON as SALON,\r\n"
				+ "L.LAB_PISO AS PISO,\r\n"
				+ "TIPO_LAB.NOMBRE as TIPO_LABORATORIO,\r\n"
				+ "P.PER_NOMBRE1 || ' ' ||P.PER_NOMBRE2 || ' ' || P.PER_APELLIDO1 || ' ' || P.PER_APELLIDO2 as COORDINADOR,\r\n"
				+ "P.PER_EMAIL as EMAIL_COORDINADOR,\r\n"
				+ "AREA_PR_OCDE.NOMBRE AS AREA_OCDE_PRINCIPAL,\r\n"
				+ "e.LDE_ID as ID_HERMES_EQUIPO,\r\n"
				+ "e.lde_placa AS PLACA,\r\n"
				+ "E.LDE_EQUIPO AS EQUIPO,\r\n"
				+ "CASE WHEN E.LDE_REQUIERE_MANTENIMIENTO = 0 THEN 'NO' WHEN E.LDE_REQUIERE_MANTENIMIENTO = 1 THEN 'SI' END as EQUIPO_REQ_MANTTO_ACTUALMENTE,\r\n"
				+ "CASE WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 0 THEN 'NO' WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 1 THEN 'SI' END as EQUIPO_REQ_CALIB_ACTUALMENTE,\r\n"
				+ "'Fallo' AS TIPO_ACTIVIDAD,\r\n"
				+ "CASE WHEN a.LERD_FECHA_SOLUCION IS null THEN 'Sin solución' WHEN a.LERD_FECHA_SOLUCION IS NOT null THEN 'Resuelto' END as ESTADO_ACTIVIDAD,\r\n"
				+ "TO_CHAR(NVL(a.LERD_FECHA_SOLUCION, a.LERD_FECHA_REPORTE), 'yyyy-MM-dd') AS FECHA_ACTIVIDAD,\r\n"
				+ "TO_CHAR(NVL(a.LERD_FECHA_SOLUCION, a.LERD_FECHA_REPORTE), 'YYYY') AS ANIO_ACTIVIDAD,"
				+ "TO_CHAR(NVL(a.LERD_FECHA_SOLUCION, a.LERD_FECHA_REPORTE), 'MM') AS MES_ACTIVIDAD,"
				+ "TO_CHAR(NVL(a.LERD_FECHA_SOLUCION, a.LERD_FECHA_REPORTE), 'DD') AS DIA_ACTIVIDAD,"
				+ "null AS COSTO_ACTIVIDAD,\r\n"
				+ "'NA' AS FRECUENCIA, "
				+ "'NA' AS MODALIDAD, "
				+ "'NA' AS RESPONSABLE, "
				+ "CASE WHEN ARCHIVOS.ARCHIVOS <= 0 THEN 'NO' WHEN ARCHIVOS.ARCHIVOS >= 1 THEN 'SI' END AS ARCHIVOS, "
				+ "a.LERD_FECHA_REGISTRO AS FECHA_REGISTRO,\r\n"
				+ "CASE WHEN a.LERD_FECHA_SOLUCION IS null THEN a.LERD_DESCRIPCIÓN WHEN a.LERD_FECHA_SOLUCION IS NOT null THEN a.LERD_DESCRIPCION_SOLUCION END AS OBSERVACIONES_ACTIVIDAD_DESCR\r\n"
				+ "FROM HER_LAB_EQUIPO_REP_DANIO a\r\n"
				+ "INNER JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON a.LDE_ID = e.lde_id\r\n"
				+ "INNER JOIN HER_LABORATORIO L ON l.lab_id = e.lab_id\r\n"
				+ "LEFT JOIN HER_SEDE S ON L.SED_ID = S.SED_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA F ON L.LAB_FACULTAD = F.DPN_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA D ON D.DPN_ID = NVL(L.LAB_DEPTO, 0)\r\n"
				+ "LEFT JOIN HER_TIPOS AREA_PR_OCDE ON AREA_PR_OCDE.ID = L.LAB_AREA_PRINCIPAL\r\n"
				+ "LEFT JOIN HER_TIPOS TIPO_LAB ON TIPO_LAB.ID = L.LAB_TIPO\r\n"
				+ "LEFT JOIN HER_EDIFICIO EDF ON EDF.EDF_ID = L.EDF_ID\r\n"
				+ "LEFT JOIN HER_PERSONA_LABORATORIO PL ON PL.LAB_ID = L.LAB_ID AND PL.ROL_ID = 'CO'\r\n"
				+ "LEFT JOIN HER_PERSONA P ON P.PER_ID = PL.PER_ID AND P.TDO_ID = PL.TDO_ID\r\n"
				+ "LEFT JOIN (SELECT l.LERD_ID, COUNT(a.HAL_HLAE) as ARCHIVOS FROM HER_LAB_EQUIPO_REP_DANIO l LEFT JOIN HER_ARCHIVO_LABORATORIO a ON l.LERD_ID = a.LERD_ID AND a.hal_tipo_archivo in (7271) GROUP BY l.LERD_ID) ARCHIVOS ON ARCHIVOS.LERD_ID = A.LERD_ID \r\n"
				+  where
				+ "UNION ALL\r\n"
				+ "SELECT\r\n"
				+ "L.LAB_ID AS ID_LABORATORIO,\r\n"
				+ "l.lab_nombre AS LABORATORIO,\r\n"
				+ "s.sed_nombre AS SEDE,\r\n"
				+ "f.dpn_nombre as FACULTAD,\r\n"
				+ "d.dpn_nombre as DEPARTAMENTO,\r\n"
				+ "EDF.EDF_CODIGO || ' - ' || EDF.EDF_NOMBBRE AS EDIFICIO,\r\n"
				+ "L.LAB_SALON as SALON,\r\n"
				+ "L.LAB_PISO AS PISO,\r\n"
				+ "TIPO_LAB.NOMBRE as TIPO_LABORATORIO,\r\n"
				+ "P.PER_NOMBRE1 || ' ' ||P.PER_NOMBRE2 || ' ' || P.PER_APELLIDO1 || ' ' || P.PER_APELLIDO2 as COORDINADOR,\r\n"
				+ "P.PER_EMAIL as EMAIL_COORDINADOR,\r\n"
				+ "AREA_PR_OCDE.NOMBRE AS AREA_OCDE_PRINCIPAL,\r\n"
				+ "e.LDE_ID as ID_HERMES_EQUIPO,\r\n"
				+ "e.lde_placa AS PLACA,\r\n"
				+ "E.LDE_EQUIPO AS EQUIPO,\r\n"
				+ "CASE WHEN E.LDE_REQUIERE_MANTENIMIENTO = 0 THEN 'NO' WHEN E.LDE_REQUIERE_MANTENIMIENTO = 1 THEN 'SI' END as EQUIPO_REQ_MANTTO_ACTUALMENTE,\r\n"
				+ "CASE WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 0 THEN 'NO' WHEN E.LDE_ES_INSTRUMENTO_MEDICION = 1 THEN 'SI' END as EQUIPO_REQ_CALIB_ACTUALMENTE,\r\n"
				+ "'Cambio repuesto' AS TIPO_ACTIVIDAD,\r\n"
				+ "CASE WHEN a.LECR_FECHA_CAMBIO IS null THEN 'Programado' WHEN a.LECR_FECHA_CAMBIO IS NOT null THEN 'Ejecutado' END as ESTADO_ACTIVIDAD,\r\n"
				+ "TO_CHAR(a.LECR_FECHA_CAMBIO, 'yyyy-MM-dd') AS FECHA_ACTIVIDAD,\r\n"
				+ "TO_CHAR(a.LECR_FECHA_CAMBIO, 'YYYY') AS ANIO_ACTIVIDAD,"
				+ "TO_CHAR(a.LECR_FECHA_CAMBIO, 'MM') AS MES_ACTIVIDAD,"
				+ "TO_CHAR(a.LECR_FECHA_CAMBIO, 'DD') AS DIA_ACTIVIDAD,"
				+ "a.LECR_COSTO AS COSTO_ACTIVIDAD,\r\n"
				+ "'NA' AS FRECUENCIA, "
				+ "'NA' AS MODALIDAD, "
				+ "'NA' AS RESPONSABLE, "
				+ "'NA' AS ARCHIVOS, "
				+ "a.LECR_FECHA_REGISTRO AS FECHA_REGISTRO,\r\n"
				+ "a.LECR_NOMBRE || ' - ' || a.LECR_REFERENCIA AS OBSERVACIONES_ACTIVIDAD_DESCR\r\n"
				+ "FROM HER_LAB_EQUIPO_CONSUM_REPUESTO a\r\n"
				+ "INNER JOIN HER_LABORATORIO_DETALLE_EQUIPO e ON a.LDE_ID = e.lde_id\r\n"
				+ "INNER JOIN HER_LABORATORIO L ON l.lab_id = e.lab_id\r\n"
				+ "LEFT JOIN HER_SEDE S ON L.SED_ID = S.SED_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA F ON L.LAB_FACULTAD = F.DPN_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA D ON D.DPN_ID = NVL(L.LAB_DEPTO, 0)\r\n"
				+ "LEFT JOIN HER_TIPOS AREA_PR_OCDE ON AREA_PR_OCDE.ID = L.LAB_AREA_PRINCIPAL\r\n"
				+ "LEFT JOIN HER_TIPOS TIPO_LAB ON TIPO_LAB.ID = L.LAB_TIPO\r\n"
				+ "LEFT JOIN HER_EDIFICIO EDF ON EDF.EDF_ID = L.EDF_ID\r\n"
				+ "LEFT JOIN HER_PERSONA_LABORATORIO PL ON PL.LAB_ID = L.LAB_ID AND PL.ROL_ID = 'CO'\r\n"
				+ "LEFT JOIN HER_PERSONA P ON P.PER_ID = PL.PER_ID AND P.TDO_ID = PL.TDO_ID\r\n"
				+ where;
		
//		System.out.println("sql: " + sql);
		
		listaReporte = servicioGeneral.obtenerMapa(sql);
		crearColumnas();
	}
	
	public void reporteGestion() {
		headerDatatable = "Gestion";
		nombreArchivo = "Reporte_Gestion_";

		String where = "WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' ";
		where = obtenerComplementoWhere(where);
		
		String sql = "SELECT \r\n"
				+ "L.LAB_ID AS ID_LABORATORIO, \r\n"
				+ "L.LAB_NOMBRE AS NOMBRE_LABORATORIO, \r\n"
				+ "SEDE.DPN_NOMBRE AS SEDE, \r\n"
				+ "FAC.DPN_NOMBRE AS FACULTAD, \r\n"
				+ "DEPTO.DPN_NOMBRE AS DEPARTAMENTO, \r\n"
				+ "EDF.EDF_CODIGO || ' - ' || EDF.EDF_NOMBBRE AS EDIFICIO, \r\n"
				+ "L.LAB_SALON as SALON, \r\n"
				+ "L.LAB_PISO as PISO, \r\n"
				+ "TP_LAB_TIPO.NOMBRE AS TIPO_LABORATORIO, \r\n"
				+ "PER.PER_NOMBRE1 || ' ' ||  PER.PER_NOMBRE2 || ' ' ||  PER.PER_APELLIDO1 || ' ' ||  PER.PER_APELLIDO2 AS COORDINADOR, \r\n"
				+ "PER.PER_EMAIL as EMAIL_COORDINADOR, \r\n"
				+ "AREA_PR_OCDE.NOMBRE AS AREA_OCDE_PRINCIPAL_LAB, \r\n"
				+ "ACRED.NOMBRE AS ACREDITACION, \r\n"
				+ "L.LAB_GESTION_ACREDITACION_CUAL AS NORMA_ACREDITACION, \r\n"
				+ "CERT.NOMBRE AS CERTIFICACION, \r\n"
				+ "L.LAB_GESTION_CERTIFICACION_CUAL AS NORMA_CERTIFICACION, \r\n"
				+ "HAB.NOMBRE AS HABILITACION, \r\n"
				+ "L.LAB_GESTION_HABILITACION_CUAL AS NORMA_HABILITACION, \r\n"
				+ "ICA.NOMBRE AS REGISTRO_ICA, \r\n"
				+ "L.LAB_GESTION_REGISTRO_ICA_CUAL, \r\n"
				+ "LIC.NOMBRE AS LICENCIAS, \r\n"
				+ "L.LAB_GESTION_LICENCIAS_CUAL, \r\n"
				+ "INTE.NOMBRE AS INTERESADO, \r\n"
				+ "LAB_GESTION_INTERESADO_NORMA, \r\n"
				+ "REQ.NOMBRE AS REQUIERE, \r\n"
				+ "LAB_GESTION_REQUIERE_NORMA \r\n"
				+ "FROM HER_LABORATORIO L \r\n"
				+ "LEFT JOIN HER_TIPOS TP_LAB_TIPO ON TP_LAB_TIPO.ID = L.LAB_TIPO \r\n"
				+ "LEFT JOIN HER_TIPOS TP_LAB_ACRED ON TP_LAB_ACRED.ID = L.LAB_GESTION_ACREDITACION \r\n"
				+ "LEFT JOIN HER_EDIFICIO EDF ON EDF.EDF_ID = L.EDF_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA SEDE ON SEDE.DPN_ID = L.SED_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA FAC ON FAC.DPN_ID = L.LAB_FACULTAD \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA DEPTO ON DEPTO.DPN_ID = L.LAB_DEPTO \r\n"
				+ "LEFT JOIN HER_TIPOS ACRED ON L.LAB_GESTION_ACREDITACION = ACRED.ID \r\n"
				+ "LEFT JOIN HER_TIPOS CERT ON L.LAB_GESTION_CERTIFICACION = CERT.ID \r\n"
				+ "LEFT JOIN HER_TIPOS HAB ON L.LAB_GESTION_HABILITACION = HAB.ID \r\n"
				+ "LEFT JOIN HER_TIPOS ICA ON L.LAB_GESTION_REGISTRO_ICA = ICA.ID \r\n"
				+ "LEFT JOIN HER_TIPOS LIC ON L.LAB_GESTION_LICENCIAS = LIC.ID \r\n"
				+ "LEFT JOIN HER_TIPOS INTE ON L.LAB_GESTION_INTERESADO = INTE.ID \r\n"
				+ "LEFT JOIN HER_TIPOS REQ ON L.LAB_GESTION_REQUIERE = REQ.ID \r\n"
				+ "LEFT JOIN HER_TIPOS AREA_PR_OCDE ON AREA_PR_OCDE.ID = L.LAB_AREA_PRINCIPAL \r\n"
				+ "LEFT JOIN HER_PERSONA_LABORATORIO PLAB ON L.LAB_ID = PLAB.LAB_ID AND PLAB.ROL_ID = 'CO' \r\n"
				+ "LEFT JOIN HER_PERSONA PER ON PER.TDO_ID = PLAB.TDO_ID AND PER.PER_ID = PLAB.PER_ID \r\n"
				+ where
				+ "ORDER BY SEDE.DPN_ID, FAC.DPN_NOMBRE, DEPTO.DPN_NOMBRE, L.LAB_NOMBRE";
		listaReporte = servicioGeneral.obtenerMapa(sql);
		
		crearColumnas();
	}
	
	public void reporteSustanciasControladas() {
		headerDatatable = "Sustancias Controladas";
		nombreArchivo = "Reporte_Sustancias_Controladas_";

		String where = "WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' ";
		where = obtenerComplementoWhere(where);
		
		String sql = "SELECT \r\n"
				+ "L.LAB_ID AS ID_LABORATORIO, \r\n"
				+ "L.LAB_NOMBRE AS LABORATORIO, \r\n"
				+ "SEDE.dpn_nombre AS SEDE, \r\n"
				+ "FAC.dpn_nombre as FACULTAD, \r\n"
				+ "DEP.dpn_nombre as DEPARTAMENTO, \r\n"
				+ "EDF.EDF_CODIGO || ' - ' || EDF.EDF_NOMBBRE AS EDIFICIO, \r\n"
				+ "L.LAB_SALON as SALON, \r\n"
				+ "L.LAB_PISO as PISO, \r\n"
				+ "T.NOMBRE as TIPO_LABORATORIO, \r\n"
				+ "P.PER_NOMBRE1 || ' ' ||P.PER_NOMBRE2 || ' ' || P.PER_APELLIDO1 || ' ' || P.PER_APELLIDO2 as COORDINADOR, \r\n"
				+ "P.PER_EMAIL as EMAIL_COORDINADOR, \r\n"
				+ "IL.HIL_CAS AS CAS, \r\n"
				+ "IL.HIL_NOMBRE AS SUSTANCIA, \r\n"
				+ "LDI.LDI_CONSUMO AS CANTIDAD, \r\n"
				+ "UNI.NOMBRE AS UNIDAD_MEDIDA, \r\n"
				+ "LDI.LDI_VALOR_CONCENTRACION AS VALOR_CONCENTRACION, \r\n"
				+ "UC.NOMBRE AS UNIDAD_CONCENTRACION, \r\n"
				+ "LDI.LDI_ANNIO AS AÑO, \r\n"
				+ "UPPER(LDI.LDI_MES) AS MES, \r\n"
				+ "UPPER(AM.NOMBRE) AS ACTIVIDAD_MANEJO, \r\n"
				+ "CASE WHEN AM.ID = 654 THEN 'PROVEEDOR - ' || EMP.EMP_NOMBRE WHEN AM.ID = 655 THEN 'DESCRIPCION CONSUMO - ' || LDI.LDI_DESC_ACT_CONSUMO WHEN AM.ID = 656 THEN (CASE WHEN LDI.LDI_ALMACENAMIENTO_DETALLE='A' THEN 'ALMACENAMIENTO - Almacén' WHEN LDI.LDI_ALMACENAMIENTO_DETALLE='I' THEN 'ALMACENAMIENTO - In situ' END) END AS DESCRIPCION_ACTIVIDAD_MANEJO,  \r\n"
				+ "LDI.LDI_FECHA_REGISTRO AS FECHA_REGISTRO, \r\n"
				+ "AREA_PR_OCDE.NOMBRE AS AREA_OCDE_PRINCIPAL, \r\n"
				+ "decode(IL.HIL_CONTROLADO,1,'SI','NO') AS CONTROLADA \r\n"
				+ "FROM HER_LABORATORIO_DET_INSUMO LDI \r\n"
				+ "LEFT JOIN HER_LABORATORIO L ON LDI.LAB_ID = L.LAB_ID \r\n"
				+ "LEFT JOIN HER_INSUMO_LABORATORIO IL ON LDI.HIL_ID = IL.HIL_ID \r\n"
				+ "LEFT JOIN HER_TIPOS UNI ON UNI.ID = IL.HIL_UNIDAD_DE_MEDIDA \r\n"
				+ "LEFT JOIN HER_TIPOS AM ON AM.ID = LDI.LDI_ACTIVIDAD_MANEJO \r\n"
				+ "LEFT JOIN HER_TIPOS UC ON UC.ID = LDI.LDI_UNIDAD_CONCENTRACION \r\n"
				+ "LEFT JOIN HER_LABORATORIO L ON L.LAB_ID = LDI.LAB_ID \r\n"
				+ "LEFT JOIN HER_EMPRESA EMP ON EMP.EMP_ID = LDI.LDI_PROVEEDOR \r\n"
				+ "LEFT JOIN HER_TIPOS T ON T.ID = L.LAB_TIPO \r\n"
				+ "LEFT JOIN HER_EDIFICIO EDF ON EDF.EDF_ID = L.EDF_ID \r\n"
				+ "LEFT JOIN HER_PERSONA_LABORATORIO PL ON PL.LAB_ID = L.LAB_ID AND PL.ROL_ID = 'CO' \r\n"
				+ "LEFT JOIN HER_PERSONA P ON P.PER_ID = PL.PER_ID AND P.TDO_ID = PL.TDO_ID \r\n"
				+ "LEFT JOIN HER_TIPOS AREA_PR_OCDE ON AREA_PR_OCDE.ID = L.LAB_AREA_PRINCIPAL \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA SEDE ON L.SED_ID = SEDE.DPN_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA FAC ON FAC.DPN_ID = L.LAB_FACULTAD \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA DEP ON DEP.DPN_ID = L.LAB_DEPTO \r\n"
				+ where
				+ "ORDER BY LDI.LDI_FECHA_REGISTRO DESC";
		listaReporte = servicioGeneral.obtenerMapa(sql);
		crearColumnas();
	}
	
	public void reporteInfraRiesgos() {
		headerDatatable = "Infraestructura y Riesgos";
		nombreArchivo = "Infraestructura_y_Riesgos";

		String where = "WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' ";
		where = obtenerComplementoWhere(where);
		
		String sql = "SELECT "
				+ "L.LAB_ID         AS ID_LABORATORIO, "
				+ "L.LAB_NOMBRE     AS NOMBRE_LABORATORIO, "
				+ "SEDE.DPN_NOMBRE    AS SEDE, "
				+ "FAC.DPN_NOMBRE     AS FACULTAD, "
				+ "DEP.DPN_NOMBRE as DEPARTAMENTO, "
				+ "EDF.EDF_CODIGO || ' - ' || EDF.EDF_NOMBBRE AS EDIFICIO, "
				+ "L.LAB_SALON as SALON, "
				+ "L.LAB_PISO as PISO, "
				+ "T.NOMBRE as TIPO_LABORATORIO, "
				+ "P.PER_NOMBRE1 || ' ' ||P.PER_NOMBRE2 || ' ' || P.PER_APELLIDO1 || ' ' || P.PER_APELLIDO2 as COORDINADOR, "
				+ "P.PER_EMAIL as EMAIL_COORDINADOR, "
				+ "EIF.NOMBRE AS ESTADO_INSTAL_FISICAS, "
				+ "ECL.NOMBRE AS ESTADO_CLIMATIZACION, "
				+ "EVE.NOMBRE AS ESTADO_VENTILACION, "
				+ "EIL.NOMBRE AS ESTADO_ILUMINACION, "
				+ "ERU.NOMBRE AS ESTADO_RUIDO, "
				+ "CASE WHEN L.LAB_REQUIERE_AMPLIACION=0 THEN 'NO' WHEN L.LAB_REQUIERE_AMPLIACION=1 THEN 'SI' END AS REQUIERE_AMPLIACION, "
				+ "CASE WHEN L.LAB_REQUIERE_ADECUACION=0 THEN 'NO' WHEN L.LAB_REQUIERE_ADECUACION=1 THEN 'SI' END AS REQUIERE_ADECUACION, "
				+ "CASE WHEN L.LAB_REQUIERE_REDISTRIBUCION=0 THEN 'NO' WHEN L.LAB_REQUIERE_REDISTRIBUCION=1 THEN 'SI' END AS REQUIERE_REDISTRIBUCION, "
				+ "CASE WHEN L.LAB_REQUIERE_PAREDES=0 THEN 'NO' WHEN L.LAB_REQUIERE_PAREDES=1 THEN 'SI' END AS REQUIERE_PAREDES, "
				+ "CASE WHEN L.LAB_REQUIERE_CUBIERTAS=0 THEN 'NO' WHEN L.LAB_REQUIERE_CUBIERTAS=1 THEN 'SI' END AS REQUIERE_CUBIERTAS, "
				+ "CASE WHEN L.LAB_REQUIERE_PISOS=0 THEN 'NO' WHEN L.LAB_REQUIERE_PISOS=1 THEN 'SI' END AS REQUIERE_PISOS, "
				+ "CASE WHEN L.LAB_REQUIERE_EQUIPOS=0 THEN 'NO' WHEN L.LAB_REQUIERE_EQUIPOS=1 THEN 'SI' END AS REQUIERE_EQUIPOS, "
				+ "LAB_REQUIERE_OTROS AS REQUIERE_OTROS_DESC_REQUERI, "
				+ "CASE WHEN L.LAB_RIESGO_BIOLOGICO=0 THEN 'NO' WHEN L.LAB_RIESGO_BIOLOGICO=1 THEN 'SI' END AS RIESGO_BIOLOGICO, "
				+ "LAB_RIESGO_BIOLOGICO_CUAL      AS RIESGO_BIOLOGICO_CUAL, "
				+ "CASE WHEN L.LAB_RIESGO_QUIMICO=0 THEN 'NO' WHEN L.LAB_RIESGO_QUIMICO=1 THEN 'SI' END AS RIESGO_QUIMICO, "
				+ "LAB_RIESGO_QUIMICO_CUAL        AS RIESGO_QUIMICO_CUAL, "
				+ "CASE WHEN L.LAB_RIESGO_ELECTRICO=0 THEN 'NO' WHEN L.LAB_RIESGO_ELECTRICO=1 THEN 'SI' END AS RIESGO_ELECTRICO, "
				+ "LAB_RIESGO_ELECTRICO_CUAL      AS RIESGO_ELECTRICO_CUAL, "
				+ "CASE WHEN L.LAB_EMISIONES_ATMOSFERICA=0 THEN 'NO' WHEN L.LAB_EMISIONES_ATMOSFERICA=1 THEN 'SI' END AS EMISIONES_ATMOSFERICA, "
				+ "LAB_EMISIONES_ATMOSFERICA_CUAL AS EMISIONES_ATMOSFERICA_CUAL, "
				+ "CASE WHEN L.LAB_EXP_ALTA_TENSION=0 THEN 'NO' WHEN L.LAB_EXP_ALTA_TENSION=1 THEN 'SI' END AS EXP_ALTA_TENSION, "
				+ "LAB_EXP_ALTA_TENSION_CUAL      AS EXP_ALTA_TENSION_CUAL, "
				+ "CASE WHEN L.LAB_ALMACENAMIENTO_REACTIVOS=0 THEN 'NO' WHEN L.LAB_ALMACENAMIENTO_REACTIVOS=1 THEN 'SI' END AS ALMACENAMIENTO_REACTIVOS, "
				+ "CASE WHEN L.LAB_RESIDUOS_SOL_BIOLOGICOS=0 THEN 'NO' WHEN L.LAB_RESIDUOS_SOL_BIOLOGICOS=1 THEN 'SI' END AS RESIDUOS_SOL_BIOLOGICOS, "
				+ "CASE WHEN L.LAB_RESIDUOS_SOL_QUIMICOS=0 THEN 'NO' WHEN L.LAB_RESIDUOS_SOL_QUIMICOS=1 THEN 'SI' END AS RESIDUOS_SOL_QUIMICOS, "
				+ "CASE WHEN L.LAB_RESIDUOS_SOL_RADIOACTIVOS=0 THEN 'NO' WHEN L.LAB_RESIDUOS_SOL_RADIOACTIVOS=1 THEN 'SI' END AS RESIDUOS_SOL_RADIOACTIVOS, "
				+ "CASE WHEN L.LAB_RESIDUOS_SOL_CITOTOXICOS=0 THEN 'NO' WHEN L.LAB_RESIDUOS_SOL_CITOTOXICOS=1 THEN 'SI' END AS RESIDUOS_SOL_CITOTOXICOS, "
				+ "CASE WHEN L.LAB_RESIDUOS_SOL_ELECTRICOS=0 THEN 'NO' WHEN L.LAB_RESIDUOS_SOL_ELECTRICOS=1 THEN 'SI' END AS RESIDUOS_SOL_ELECTRICOS, "
				+ "LAB_RESIDUOS_SOL_OTROS         AS RESIDUOS_SOL_OTROS, "
				+ "CASE WHEN L.LAB_RESIDUOS_LIQ_BIOLOGICOS=0 THEN 'NO' WHEN L.LAB_RESIDUOS_LIQ_BIOLOGICOS=1 THEN 'SI' END AS RESIDUOS_LIQ_BIOLOGICOS, "
				+ "CASE WHEN L.LAB_RESIDUOS_LIQ_QUIMICOS=0 THEN 'NO' WHEN L.LAB_RESIDUOS_LIQ_QUIMICOS=1 THEN 'SI' END AS RESIDUOS_LIQ_QUIMICOS, "
				+ "CASE WHEN L.LAB_RESIDUOS_LIQ_RADIOACTIVOS=0 THEN 'NO' WHEN L.LAB_RESIDUOS_LIQ_RADIOACTIVOS=1 THEN 'SI' END AS RESIDUOS_LIQ_RADIOACTIVOS, "
				+ "LAB_RESIDUOS_LIQ_OTROS         AS RESIDUOS_LIQ_OTROS, "
				+ "LAB_SEGURIDAD_EXTINTORES       AS SEGURIDAD_EXTINTORES, "
				+ "CASE WHEN L.LAB_SEGURIDAD_BOTIQUIN=0 THEN 'NO' WHEN L.LAB_SEGURIDAD_BOTIQUIN=1 THEN 'SI' END AS SEGURIDAD_BOTIQUIN, "
				+ "CASE WHEN L.LAB_SEGURIDAD_DUCHA_EMERGENCIA=0 THEN 'NO' WHEN L.LAB_SEGURIDAD_DUCHA_EMERGENCIA=1 THEN 'SI' END AS SEGURIDAD_DUCHA_EMERGENCIA, "
				+ "CASE WHEN L.LAB_SEGURIDAD_SENALIZACION_GEN=0 THEN 'NO' WHEN L.LAB_SEGURIDAD_SENALIZACION_GEN=1 THEN 'SI' END AS SEGURIDAD_SENALIZACION_GEN, "
				+ "CASE WHEN L.LAB_SEGURIDAD_SENALIZACION_SEG=0 THEN 'NO' WHEN L.LAB_SEGURIDAD_SENALIZACION_SEG=1 THEN 'SI' END AS SEGURIDAD_SENALIZACION_SEG, "
				+ "CASE WHEN L.LAB_SEGURIDAD_SENALIZACION_EME=0 THEN 'NO' WHEN L.LAB_SEGURIDAD_SENALIZACION_EME=1 THEN 'SI' END AS SEGURIDAD_SENALIZACION_EME, "
				+ "CASE WHEN L.LAB_SEG_ELEM_PROTEC_PERS=0 THEN 'NO' WHEN L.LAB_SEG_ELEM_PROTEC_PERS=1 THEN 'SI' END AS ELEM_PROTEC_PERS, "
				+ "LAB_SEG_ELEM_PROTEC_PERS_CUAL AS ELEM_PROTEC_PERS_CUAL, "
				+ "CASE WHEN L.LAB_MANUAL_PROTECC_RADIOLOGICA=0 THEN 'NO' WHEN L.LAB_MANUAL_PROTECC_RADIOLOGICA=1 THEN 'SI' END AS MANUAL_PROTECC_RADIOLOGICA, "
				+ "CASE WHEN L.LAB_MANUAL_PROTECC_BIOLOGICA=0 THEN 'NO' WHEN L.LAB_MANUAL_PROTECC_BIOLOGICA=1 THEN 'SI' END AS MANUAL_PROTECC_BIOLOGICA, "
				+ "CASE WHEN L.LAB_MANUAL_PROTECC_QUIMICA=0 THEN 'NO' WHEN L.LAB_MANUAL_PROTECC_QUIMICA=1 THEN 'SI' END AS MANUAL_PROTECC_QUIMICA, "
				+ "CASE WHEN L.LAB_MANUAL_PROTECC_ELECTRICA=0 THEN 'NO' WHEN L.LAB_MANUAL_PROTECC_ELECTRICA=1 THEN 'SI' END AS MANUAL_PROTECC_ELECTRICA, "
				+ "CASE WHEN L.LAB_RADIACION_EXP_IONIZANTE=0 THEN 'NO' WHEN L.LAB_RADIACION_EXP_IONIZANTE=1 THEN 'SI' END AS RADIACION_EXP_IONIZANTE, "
				+ "CASE WHEN L.LAB_RADIACION_EQ_EMISOR=0 THEN 'NO' WHEN L.LAB_RADIACION_EQ_EMISOR=1 THEN 'SI' END AS RADIACION_EQ_EMISOR, "
				+ "LAB_RADIACION_EQ_EMISOR_CUAL    AS RADIACION_EQ_EMISOR_CUAL, "
				+ "CASE WHEN L.LAB_RADIACION_FUENTE=0 THEN 'NO' WHEN L.LAB_RADIACION_FUENTE=1 THEN 'SI' END AS RADIACION_FUENTE, "
				+ "LAB_RADIACION_FUENTE_CUAL       AS RADIACION_FUENTE_CUAL, "
				+ "CASE WHEN L.LAB_RADIACION_MATERIAL=0 THEN 'NO' WHEN L.LAB_RADIACION_MATERIAL=1 THEN 'SI' END AS RADIACION_MATERIAL, "
				+ "LAB_RADIACION_MATERIAL_CUAL     AS RADIACION_MATERIAL_CUAL, "
				+ "CASE WHEN L.LAB_RADIACION_EXP_NO_ION=0 THEN 'NO' WHEN L.LAB_RADIACION_EXP_NO_ION=1 THEN 'SI' END AS RADIACION_EXP_NO_ION, "
				+ "LAB_RADIACION_EXP_NO_ION_CUAL   AS RADIACION_EXP_NO_ION_CUAL, "
				+ "CASE WHEN L.LAB_RADIACION_RESIDUO_RAD=0 THEN 'NO' WHEN L.LAB_RADIACION_RESIDUO_RAD=1 THEN 'SI' END AS RADIACION_RESIDUO_RAD, "
				+ "LAB_RADIACION_RESIDUO_RAD_CUAL  AS RADIACION_RESIDUO_RAD_CUAL, "
				+ "CASE WHEN L.LAB_RADIACION_EMISION_DIARIO=0 THEN 'NO' WHEN L.LAB_RADIACION_EMISION_DIARIO=1 THEN 'SI' END AS RADIACION_EMISION_DIARIO, "
				+ "CASE WHEN L.LAB_RADIACION_EMISION_SEMANAL=0 THEN 'NO' WHEN L.LAB_RADIACION_EMISION_SEMANAL=1 THEN 'SI' END AS RADIACION_EMISION_SEMANAL, "
				+ "CASE WHEN L.LAB_RADIACION_EMISION_MENSUAL=0 THEN 'NO' WHEN L.LAB_RADIACION_EMISION_MENSUAL=1 THEN 'SI' END AS RADIACION_EMISION_MENSUAL, "
				+ "LAB_RADIACION_DOSIM_PERSONALES  AS RADIACION_DOSIM_PERSONALES, "
				+ "LAB_RADIACION_DOSIM_AMBIENTAL   AS RADIACION_DOSIM_AMBIENTAL, "
				+ "LAB_RADIACION_DOSIM_CONTROL     AS RADIACION_DOSIM_CONTROL, "
				+ "CASE WHEN L.LAB_RADIACION_PROG_MONITOREO=0 THEN 'NO' WHEN L.LAB_RADIACION_PROG_MONITOREO=1 THEN 'SI' END AS RADIACION_PROG_MONITOREO, "
				+ "CASE WHEN L.LAB_RADIACION_CARNE_PROT_RAD=0 THEN 'NO' WHEN L.LAB_RADIACION_CARNE_PROT_RAD=1 THEN 'SI' END AS RADIACION_CARNE_PROT_RAD, "
				+ "CASE WHEN L.LAB_RADIACION_LIC_MANEJO_FUNC=0 THEN 'NO' WHEN L.LAB_RADIACION_LIC_MANEJO_FUNC=1 THEN 'SI' END AS RADIACION_LIC_MANEJO_FUNC, "
				+ "CASE WHEN L.LAB_RADIACION_CUARTO_DECAIM=0 THEN 'NO' WHEN L.LAB_RADIACION_CUARTO_DECAIM=1 THEN 'SI' END AS RADIACION_CUARTO_DECAIM, "
				+ "LAB_RADIACION_OBSERVACIONES AS RADIACION_OBSERVACIONES "
				+ "FROM HER_LABORATORIO L "
				+ "LEFT JOIN HER_DEPENDENCIA SEDE ON L.SED_ID = SEDE.DPN_ID "
				+ "LEFT JOIN HER_DEPENDENCIA FAC ON FAC.DPN_ID = L.LAB_FACULTAD "
				+ "LEFT JOIN HER_DEPENDENCIA DEP ON DEP.DPN_ID = L.LAB_DEPTO "
				+ "LEFT JOIN HER_TIPOS EIF ON EIF.ID = L.LAB_ESTADO_INSTAL_FISICAS "
				+ "LEFT JOIN HER_TIPOS ECL ON ECL.ID = L.LAB_ESTADO_CLIMATIZACION "
				+ "LEFT JOIN HER_TIPOS EVE ON EVE.ID = L.LAB_ESTADO_VENTILACION "
				+ "LEFT JOIN HER_TIPOS EIL ON EIL.ID = L.LAB_ESTADO_ILUMINACION "
				+ "LEFT JOIN HER_TIPOS ERU ON ERU.ID = L.LAB_ESTADO_RUIDO "
				+ "LEFT JOIN HER_TIPOS T ON T.ID = L.LAB_TIPO "
				+ "LEFT JOIN HER_EDIFICIO EDF ON EDF.EDF_ID = L.EDF_ID "
				+ "LEFT JOIN HER_PERSONA_LABORATORIO PL ON PL.LAB_ID = L.LAB_ID AND PL.ROL_ID = 'CO' "
				+ "LEFT JOIN HER_PERSONA P ON P.PER_ID = PL.PER_ID AND P.TDO_ID = PL.TDO_ID "
				+ where
				+ "ORDER BY L.LAB_ID DESC";
		listaReporte = servicioGeneral.obtenerMapa(sql);
		crearColumnas();
	}
	
	public void reporteProyectos() {
		headerDatatable = "Proyectos";
		nombreArchivo = "Reporte_Proyectos_";

		String where = "WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' ";
		where = obtenerComplementoWhere(where);
		
		String sql = "SELECT "
				+ "L.LAB_ID AS ID_LABORATORIO, "
				+ "L.LAB_NOMBRE AS NOMBRE_LABORATORIO, "
				+ "S.sed_nombre AS SEDE_LABORATORIO, "
				+ "F.dpn_nombre as FACULTAD_LABORATORIO, "
				+ "D.dpn_nombre as DEPARTAMENTO_LABORATORIO, "
				+ "EDF.EDF_CODIGO || ' - ' || EDF.EDF_NOMBBRE AS EDIFICIO_LABORATORIO, "
				+ "L.LAB_SALON as SALON_LABORATORIO, "
				+ "L.LAB_PISO as PISO_LABORATORIO, "
				+ "T.NOMBRE as TIPO_LABORATORIO, "
				+ "PER.PER_NOMBRE1 || ' ' ||  PER.PER_NOMBRE2 || ' ' ||  PER.PER_APELLIDO1 || ' ' ||  PER.PER_APELLIDO2 AS COORDINADOR_LABORATORIO, "
				+ "PER.PER_EMAIL as EMAIL_COORDINADOR, "
				+ "PRY.PRY_ID AS ID_PRY, "
				+ "DD_TIPOLOGIA.DOMDET_DESCRIPCION AS TIPOLOGIA_PRY, "
				+ "SED.SED_NOMBRE AS SEDE_PRY, "
				+ "FAC.DPN_NOMBRE AS FACULTAD_PRY, "
				+ "DECODE(CON.CON_ID, 10, 'Externa', 'Interna') AS TIPO_CONVOCATORIA_PRY, "
				+ "CNP.CNP_TITULO AS CONVOCATORIA_PRY, "
				+ "CON.CON_TITULO AS MODALIDAD_PRY, "
				+ "PRY.PRY_NOMBRE AS NOMBRE_PRY, "
				+ "(DIRECTOR.PER_NOMBRE1 || ' ' || DIRECTOR.PER_NOMBRE2 ||' '|| DIRECTOR.PER_APELLIDO1||' '||DIRECTOR.PER_APELLIDO2 ) as DIRECTOR_PRY, "
				+ "EP.EPR_NOMBRE AS ESTADO_PRY, "
				+ "TO_CHAR(PRY.PRY_FECHA_TENTATIVA_INICIO, 'YYYY-MM-DD') as FECHA_INICIO_PRY, "
				+ "TO_CHAR(add_months(PRY.PRY_FECHA_TENTATIVA_INICIO,decode(PRY.PRY_DURACION_ACUMULADA,NULL,0,PRY.PRY_DURACION_ACUMULADA)) + decode(PRY.PRY_DURACION_DIAS_ACUMULADA,NULL,0,PRY.PRY_DURACION_DIAS_ACUMULADA), 'YYYY-MM-DD') AS FECHA_FIN_PRY, "
				+ "(select listagg(GR.GRU_NOMBRE,'; ') WITHIN GROUP (ORDER BY GR.GRU_NOMBRE) from HER_GRUPO GR, HER_GRUPO_PROYECTO GP where GR.GRU_ID = GP.GRU_ID and GP.PRY_ID = PRY.PRY_ID group by GP.PRY_ID) as GRUPOS_PRY, "
				+ "(select listagg(DOM.DOMDET_DESCRIPCION || ' (' || DET.DOMDET_DESCRIPCION || ')','; ') WITHIN GROUP (ORDER BY DOM.DOMDET_DESCRIPCION) from HER_PROYECTO_AREA_TEMATICA PAT, HER_DOMINIO_DETALLE DET, HER_DOMINIO_DETALLE DOM WHERE PAT.PRY_ID = PRY.PRY_ID AND PAT.DOMDET_TIPO = DET.DOMDET_TIPO AND DET.DOMDET_ESTADO = DOM.DOMDET_TIPO AND PAT.ART_TIPO = 1 GROUP BY PAT.PRY_ID) as AREA_OCDE_PRINCIPAL_PRY, "
				+ "(select listagg(DOM.DOMDET_DESCRIPCION || ' (' || DET.DOMDET_DESCRIPCION || ')','; ') WITHIN GROUP (ORDER BY DOM.DOMDET_DESCRIPCION) from HER_PROYECTO_AREA_TEMATICA PAT, HER_DOMINIO_DETALLE DET, HER_DOMINIO_DETALLE DOM WHERE PAT.PRY_ID = PRY.PRY_ID AND PAT.DOMDET_TIPO = DET.DOMDET_TIPO AND DET.DOMDET_ESTADO = DOM.DOMDET_TIPO AND PAT.ART_TIPO = 2 GROUP BY PAT.PRY_ID) as AREAS_OCDE_SECUNDARIA_PRY, "
//				+ "'GRUPOS' as GRUPOS, "
//				+ "'AREA_OCDE_PRINCIPAL' as AREA_OCDE_PRINCIPAL, "
//				+ "'AREAS_OCDE_SECUNDARIA' as AREAS_OCDE_SECUNDARIA, "
				+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'ESTUDIANTES_PREGRADO') AS ESTUDIANTES_PREGRADO_PRY, "
				+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'ESTUDIANTES_POSGRADO') AS ESTUDIANTES_POSGRADO_PRY, "
				+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'ESTUDIANTES_LIDERES') AS ESTUDIANTES_LIDERES_PRY, "
				+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'ESTUDIANTES_VISITANTES') AS ESTUDIANTES_VISITANTES_PRY, "
				+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'DOCENTES') AS DOCENTES_PRY, "
				+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'EGRESADOS') AS EGRESADOS_PRY, "
				+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'CONTRATISTAS') AS CONTRATISTAS_PRY, "
				+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'ADMINISTRATIVOS') AS ADMINISTRATIVOS_PRY, "
				+ "NUMERO_INTEGRANTES_PROYECTO(PRY.PRY_ID, 'EXTERNOS') AS EXTERNOS_PRY "
				+ "FROM HER_PROYECTO PRY "
				+ "INNER JOIN HER_LABORATORIO_PROYECTO LAP ON PRY.PRY_ID = LAP.PRY_ID "
				+ "LEFT JOIN HER_ESTADO_PROYECTO EP on EP.EPR_ID = PRY.EPR_ID "
				+ "LEFT JOIN HER_DOMINIO_DETALLE DD_TIPOLOGIA ON DD_TIPOLOGIA.DOMDET_TIPO = PRY.PRY_TIPO_ACTIVIDAD AND DD_TIPOLOGIA.DOM_ID = 117 "
				+ "LEFT JOIN HER_CONVOCATORIA CON ON CON.CON_ID = PRY.MOD_ID "
				+ "LEFT JOIN HER_CONVOCATORIA_PADRE CNP ON CNP.CNP_ID = CON.CNP_ID "
				+ "LEFT JOIN HER_INVESTIGADOR_PROYECTO INVP ON INVP.PRY_ID = PRY.PRY_ID AND INVP.INP_TIPO ='P' "
				+ "LEFT JOIN HER_PERSONA DIRECTOR ON DIRECTOR.PER_ID = INVP.INV_ID "
				+ "LEFT JOIN HER_INVESTIGADOR_INTERNO II on INVP.INV_ID = II.INV_ID and INVP.TDO_ID = II.TDO_ID "
				+ "LEFT JOIN HER_DEPENDENCIA DEP on DEP.DPN_ID = II.DPN_ID "
				+ "LEFT JOIN HER_DEPENDENCIA FAC on FAC.DPN_ID = DEP.DPN_FACULTAD "
				+ "LEFT JOIN HER_SEDE SED on DEP.SED_ID = SED.SED_ID "
				+ "INNER JOIN HER_LABORATORIO L ON L.LAB_ID = LAP.LAB_ID "
				+ "LEFT JOIN HER_PERSONA_LABORATORIO PLAB ON L.LAB_ID = PLAB.LAB_ID AND PLAB.ROL_ID = 'CO' "
				+ "LEFT JOIN HER_PERSONA PER ON PER.TDO_ID = PLAB.TDO_ID AND PER.PER_ID = PLAB.PER_ID "
				+ "LEFT JOIN HER_SEDE S ON L.SED_ID = S.SED_ID "
				+ "LEFT JOIN HER_DEPENDENCIA F ON F.dpn_id = L.lab_facultad "
				+ "LEFT JOIN HER_DEPENDENCIA D ON D.dpn_id = L.lab_depto "
				+ "LEFT JOIN HER_EDIFICIO EDF ON EDF.EDF_ID = L.EDF_ID "
				+ "LEFT JOIN HER_TIPOS T ON T.ID = L.LAB_TIPO "
				+ where
				+ "AND PRY.EPR_ID IN ('AP', 'A', 'CN', 'S', 'PF', 'F') "
				+ "ORDER BY L.LAB_ID DESC, L.SED_ID, L.lab_facultad, PRY.PRY_NOMBRE";
		
		listaReporte = servicioGeneral.obtenerMapa(sql);
		
		crearColumnas();
	}
	
	public void reporteGrupos() {
		headerDatatable = "Grupos";
		nombreArchivo = "Reporte_Grupos_";

		String where = "WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' ";
		where = obtenerComplementoWhere(where);
		
		String sql = "SELECT "
				+ "L.LAB_ID AS ID_LABORATORIO, "
				+ "L.LAB_NOMBRE AS NOMBRE_LABORATORIO, "
				+ "S.sed_nombre AS SEDE_LABORATORIO, "
				+ "F.dpn_nombre as FACULTAD_LABORATORIO, "
				+ "D.dpn_nombre as DEPARTAMENTO_LABORATORIO, "
				+ "EDF.EDF_CODIGO || ' - ' || EDF.EDF_NOMBBRE AS EDIFICIO_LABORATORIO, "
				+ "L.LAB_SALON as SALON_LABORATORIO, "
				+ "L.LAB_PISO as PISO_LABORATORIO, "
				+ "T.NOMBRE as TIPO_LABORATORIO, "
				+ "PER.PER_NOMBRE1 || ' ' ||  PER.PER_NOMBRE2 || ' ' ||  PER.PER_APELLIDO1 || ' ' ||  PER.PER_APELLIDO2 AS COORDINADOR_LABORATORIO, "
				+ "PER.PER_EMAIL as EMAIL_COORDINADOR, "
				+ "GRUPO.GRU_ID AS ID_GRUPO, "
				+ "GRUPO.GRU_NOMBRE AS NOMBRE_GRUPO, "
				+ "(LIDER.PER_NOMBRE1 || ' ' || LIDER.PER_NOMBRE2 ||' '|| LIDER.PER_APELLIDO1||' '||LIDER.PER_APELLIDO2 ) as LIDER_GRUPO, "
				+ "SED.SED_NOMBRE AS SEDE_GRUPO, "
				+ "FAC.DPN_NOMBRE AS FACULTAD_GRUPO, "
				+ "EGRU.EGR_NOMBRE AS ESTADO_UN_GRUPO, "
				+ "EGC.EGC_NOMBRE AS ESTADO_SCIENTI_GRUPO, "
				+ "CAT.GRC_NOMBRE AS CATEGORIA_SCIENTI_GRUPO, "
				+ "AREA.DOMDET_DESCRIPCION || ' (' || SUB_AREA.DOMDET_DESCRIPCION || ')' AS AREA_OCDE_PRINCIPAL_GRUPO, "
				+ "(SELECT LISTAGG(DD_PADRE2.DOMDET_DESCRIPCION || ' (' || DD2.DOMDET_DESCRIPCION || ')','; ') WITHIN GROUP (ORDER BY DD_PADRE2.DOMdET_DESCRIPCION) FROM HER_GRUPO_AREA_TEMATICA GAT LEFT JOIN HER_DOMINIO_DETALLE DD2 ON GAT.DOMDET_TIPO = DD2.DOMDET_TIPO AND GAT.DOM_ID = DD2.DOM_ID LEFT JOIN HER_DOMINIO_DETALLE DD_PADRE2 ON DD2.DOMDET_ESTADO = DD_PADRE2.DOMDET_TIPO WHERE GAT.GRU_ID = GRUPO.GRU_ID GROUP BY GAT.GRU_ID) AS AREAS_OCDE_SECUNDARIA, "
//				+ "--'AREAS_OCDE_SECUNDARIA' AS AREAS_OCDE_SECUNDARIA_GRUPO, "
				+ "NUMERO_INTEGRANTES_GRUPO(GRUPO.GRU_ID, 'ESTUDIANTES_PREGRADO') AS ESTUDIANTES_PREGRADO_GRUPO, "
				+ "NUMERO_INTEGRANTES_GRUPO(GRUPO.GRU_ID, 'ESTUDIANTES_POSGRADO') AS ESTUDIANTES_POSGRADO_GRUPO, "
				+ "NUMERO_INTEGRANTES_GRUPO(GRUPO.GRU_ID, 'ESTUDIANTES_VISITANTES') AS ESTUDIANTES_VISITANTES_GRUPO, "
				+ "NUMERO_INTEGRANTES_GRUPO(GRUPO.GRU_ID, 'DOCENTES') AS DOCENTES_GRUPO, "
				+ "NUMERO_INTEGRANTES_GRUPO(GRUPO.GRU_ID, 'ADMINISTRATIVOS') AS ADMINISTRATIVOS_GRUPO, "
				+ "NUMERO_INTEGRANTES_GRUPO(GRUPO.GRU_ID, 'EGRESADOS') AS EGRESADOS_GRUPO, "
				+ "NUMERO_INTEGRANTES_GRUPO(GRUPO.GRU_ID, 'EXTERNOS') AS EXTERNOS_GRUPO, "
				+ "PRY.PRY_ID AS ID_PROYECTO_ASOCIADO, "
				+ "PRY.PRY_NOMBRE AS NOMBRE_PROYECTO_ASOCIADO "
				+ "FROM HER_PROYECTO PRY "
				+ "INNER JOIN HER_LABORATORIO_PROYECTO LAP ON PRY.PRY_ID = LAP.PRY_ID "
				+ "INNER JOIN HER_GRUPO_PROYECTO GRP ON GRP.PRY_ID = PRY.PRY_ID "
				+ "INNER JOIN HER_GRUPO GRUPO ON GRUPO.GRU_ID = GRP.GRU_ID "
				+ "LEFT JOIN HER_INVESTIGADOR_GRUPO ING ON ING.GRU_ID = GRUPO.GRU_ID AND ING.ING_TIPO = 'L'   "
				+ "LEFT JOIN HER_GRUPO_CATEGORIA CAT ON CAT.GRC_ID = GRUPO.GRU_CATEGORIA_COLCIENCIAS "
				+ "LEFT JOIN HER_ESTADO_GRUPO_COLCIENCIAS EGC ON EGC.EGC_ID = GRUPO.EGC_ID "
				+ "LEFT JOIN HER_ESTADO_GRUPO EGRU ON EGRU.EGR_ID = GRUPO.EGR_ID "
				+ "LEFT JOIN HER_PERSONA LIDER ON LIDER.PER_ID = ING.INV_ID AND LIDER.TDO_ID = ING.TDO_ID "
				+ "LEFT JOIN HER_INVESTIGADOR_INTERNO II on ING.INV_ID = II.INV_ID and ING.TDO_ID = II.TDO_ID "
				+ "LEFT JOIN HER_DEPENDENCIA DEP on DEP.DPN_ID = II.DPN_ID "
				+ "LEFT JOIN HER_DEPENDENCIA FAC on FAC.DPN_ID = DEP.DPN_FACULTAD "
				+ "LEFT JOIN HER_SEDE SED on DEP.SED_ID = SED.SED_ID "
				+ "LEFT JOIN HER_DOMINIO_DETALLE SUB_AREA on GRUPO.DOM_ID_SUBAREA = SUB_AREA.DOM_ID and GRUPO.DOMDET_TIPO_SUBAREA = SUB_AREA.DOMDET_TIPO  "
				+ "LEFT JOIN HER_DOMINIO_DETALLE AREA on SUB_AREA.DOMDET_ESTADO = AREA.DOMDET_TIPO "
				+ "INNER JOIN HER_LABORATORIO L ON L.LAB_ID = LAP.LAB_ID "
				+ "LEFT JOIN HER_PERSONA_LABORATORIO PLAB ON L.LAB_ID = PLAB.LAB_ID AND PLAB.ROL_ID = 'CO' "
				+ "LEFT JOIN HER_PERSONA PER ON PER.TDO_ID = PLAB.TDO_ID AND PER.PER_ID = PLAB.PER_ID "
				+ "LEFT JOIN HER_SEDE S ON L.SED_ID = S.SED_ID "
				+ "LEFT JOIN HER_DEPENDENCIA F ON F.dpn_id = L.lab_facultad "
				+ "LEFT JOIN HER_DEPENDENCIA D ON D.dpn_id = L.lab_depto "
				+ "LEFT JOIN HER_EDIFICIO EDF ON EDF.EDF_ID = L.EDF_ID "
				+ "LEFT JOIN HER_TIPOS T ON T.ID = L.LAB_TIPO "
				+ where
				+ "AND PRY.EPR_ID IN ('AP', 'A', 'CN', 'S', 'PF', 'F') "
				+ "AND GRUPO.EGR_ID = 'A' "
				+ "ORDER BY LAP.LAB_ID, PRY.PRY_ID, GRUPO.GRU_NOMBRE";

		listaReporte = servicioGeneral.obtenerMapa(sql);
		
		crearColumnas();
	}
	
	public void reporteSemilleros() {
		headerDatatable = "Semilleros";
		nombreArchivo = "Reporte_Semilleros_";

		String where = "WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' ";
		where = obtenerComplementoWhere(where);
		
		String sql = "SELECT "
			+ "L.LAB_ID AS ID_LABORATORIO, "
			+ "L.LAB_NOMBRE AS NOMBRE_LABORATORIO, "
			+ "S.sed_nombre AS SEDE_LABORATORIO, "
			+ "F.dpn_nombre as FACULTAD_LABORATORIO, "
			+ "D.dpn_nombre as DEPARTAMENTO_LABORATORIO, "
			+ "EDF.EDF_CODIGO || ' - ' || EDF.EDF_NOMBBRE AS EDIFICIO_LABORATORIO, "
			+ "L.LAB_SALON as SALON_LABORATORIO, "
			+ "L.LAB_PISO as PISO_LABORATORIO, "
			+ "T.NOMBRE as TIPO_LABORATORIO, "
			+ "PER.PER_NOMBRE1 || ' ' ||  PER.PER_NOMBRE2 || ' ' ||  PER.PER_APELLIDO1 || ' ' ||  PER.PER_APELLIDO2 AS COORDINADOR_LABORATORIO, "
			+ "PER.PER_EMAIL as EMAIL_COORDINADOR, "
			+ "SEM.SEM_ID AS ID_SEM, "
			+ "SEM.SEM_NOMBRE AS NOMBRE_SEM,"
			+ "(LIDER.PER_NOMBRE1 || ' ' || LIDER.PER_NOMBRE2 ||' '|| LIDER.PER_APELLIDO1||' '||LIDER.PER_APELLIDO2 ) as LIDER_SEM, "
			+ "EST_ACTUAL.NOMBRE_ESTADO AS ESTADO_SEM,"
			+ "SED.SED_NOMBRE AS SEDE_SEM, "
			+ "FAC.DPN_NOMBRE AS FACULTAD_SEM,"
			+ "AREA.DOMDET_DESCRIPCION || ' (' || SUB_AREA.DOMDET_DESCRIPCION || ')' AS AREA_OCDE_PRINCIPAL_SEM, "
			+ "(SELECT LISTAGG(DD_PADRE2.DOMDET_DESCRIPCION || ' (' || DD2.DOMDET_DESCRIPCION || ')','; ') WITHIN GROUP (ORDER BY DD_PADRE2.DOMdET_DESCRIPCION) FROM HER_SEMILLERO_AREA_OCDE GAT LEFT JOIN HER_DOMINIO_DETALLE DD2 ON GAT.DOMDET_TIPO = DD2.DOMDET_TIPO AND GAT.DOM_ID = DD2.DOM_ID LEFT JOIN HER_DOMINIO_DETALLE DD_PADRE2 ON DD2.DOMDET_ESTADO = DD_PADRE2.DOMDET_TIPO WHERE GAT.SEM_ID = SEM.SEM_ID GROUP BY GAT.SEM_ID) AS AREAS_OCDE_SECUNDARIA_SEM, "
//			+ "--'AREAS_OCDE_SECUNDARIA' AS AREAS_OCDE_SECUNDARIA_SEM, "
			+ "NUMERO_INTEGRANTES_SEMILLERO(SEM.SEM_ID, 'ESTUDIANTES_PREGRADO') AS ESTUDIANTES_PREGRADO_SEM, "
			+ "NUMERO_INTEGRANTES_SEMILLERO(SEM.SEM_ID, 'ESTUDIANTES_POSGRADO') AS ESTUDIANTES_POSGRADO_SEM, "
			+ "NUMERO_INTEGRANTES_SEMILLERO(SEM.SEM_ID, 'ESTUDIANTES_LIDERES') AS ESTUDIANTES_LIDERES_SEM, "
			+ "NUMERO_INTEGRANTES_SEMILLERO(SEM.SEM_ID, 'ESTUDIANTES_VISITANTES') AS ESTUDIANTES_VISITANTES_SEM, "
			+ "NUMERO_INTEGRANTES_SEMILLERO(SEM.SEM_ID, 'DOCENTES') AS DOCENTES_SEM, "
			+ "NUMERO_INTEGRANTES_SEMILLERO(SEM.SEM_ID, 'EGRESADOS') AS EGRESADOS_SEM, "
			+ "NUMERO_INTEGRANTES_SEMILLERO(SEM.SEM_ID, 'EXTERNOS') AS EXTERNOS_SEM "
			+ "FROM HER_SEMILLERO SEM "
			+ "INNER JOIN HER_SEMILLERO_LABORATORIO SLA ON SLA.SEM_ID = SEM.SEM_ID "
			+ "LEFT JOIN HER_SEMILLERO_INTEGRANTE INTS ON INTS.SEM_ID = SEM.SEM_ID AND INTS.SIT_ID ='DD' "
			+ "LEFT JOIN HER_PERSONA LIDER ON LIDER.PER_ID = INTS.INV_ID "
			+ "LEFT JOIN HER_INVESTIGADOR_INTERNO II ON INTS.INV_ID = II.INV_ID and INTS.TDO_ID = II.TDO_ID "
			+ "LEFT JOIN HER_DEPENDENCIA DEP ON DEP.DPN_ID = II.DPN_ID "
			+ "LEFT JOIN HER_DEPENDENCIA FAC ON FAC.DPN_ID = DEP.DPN_FACULTAD "
			+ "LEFT JOIN HER_SEDE SED ON DEP.SED_ID = SED.SED_ID "
			+ "LEFT JOIN HER_DOMINIO_DETALLE SUB_AREA ON SUB_AREA.DOMDET_TIPO = SEM.SEM_SUBAREA_OCDE_PRINCIPAL "
			+ "LEFT JOIN HER_DOMINIO_DETALLE AREA ON AREA.DOMDET_TIPO = SEM.SEM_AREA_OCDE_PRINCIPAL "
			+ "LEFT JOIN ("
			+ "SELECT DISTINCT HSHE.SEM_ID AS SEM_ID, HSE.SEE_ID AS ID_ESTADO, HSE.SEE_NOMBRE AS NOMBRE_ESTADO "
			+ "FROM HER_SEMILLERO_HISTORICO_ESTADO HSHE "
			+ "INNER JOIN ( "
			+ "SELECT sem_id, max(HSE_ID) id "
			+ "FROM HER_SEMILLERO_HISTORICO_ESTADO "
			+ "GROUP BY sem_id) EA "
			+ "ON EA.SEM_ID = HSHE.SEM_ID "
			+ "INNER JOIN HER_SEMILLERO_HISTORICO_ESTADO HSHE2 ON EA.ID = HSHE2.HSE_ID "
			+ "INNER JOIN HER_SEMILLERO_ESTADO HSE ON HSHE2.SEE_ID = HSE.SEE_ID) EST_ACTUAL ON EST_ACTUAL.SEM_ID = SEM.SEM_ID "
			+ "INNER JOIN HER_LABORATORIO L ON L.LAB_ID = SLA.LAB_ID "
			+ "LEFT JOIN HER_PERSONA_LABORATORIO PLAB ON SLA.LAB_ID = PLAB.LAB_ID AND PLAB.ROL_ID = 'CO' "
			+ "LEFT JOIN HER_PERSONA PER ON PER.TDO_ID = PLAB.TDO_ID AND PER.PER_ID = PLAB.PER_ID "
			+ "LEFT JOIN HER_SEDE S ON L.SED_ID = S.SED_ID "
			+ "LEFT JOIN HER_DEPENDENCIA F ON F.dpn_id = L.lab_facultad "
			+ "LEFT JOIN HER_DEPENDENCIA D ON D.dpn_id = L.lab_depto "
			+ "LEFT JOIN HER_EDIFICIO EDF ON EDF.EDF_ID = L.EDF_ID "
			+ "LEFT JOIN HER_TIPOS T ON T.ID = L.LAB_TIPO "
			+ where
			+ "AND EST_ACTUAL.ID_ESTADO IN (5)";

		listaReporte = servicioGeneral.obtenerMapa(sql);
		
		crearColumnas();
	}
	
	public void reporteEnsayosServicios() {
		headerDatatable = "Ensayos Servicios";
		nombreArchivo = "Reporte_Ensayos_Servicios_";

		String where = "WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' ";
		where = obtenerComplementoWhere(where);
		
		String sql = "SELECT "
			+ "	L.LAB_ID AS ID_LABORATORIO, "
			+ "	L.LAB_NOMBRE AS NOMBRE_LAB, "
			+ "	S.sed_nombre AS SEDE, "
			+ "	F.dpn_nombre as FACULTAD, "
			+ "	D.dpn_nombre as DEPARTAMENTO, "
			+ "	EDF.EDF_CODIGO || ' - ' || EDF.EDF_NOMBBRE AS EDIFICIO, "
			+ "	L.LAB_SALON as SALON, "
			+ "	L.LAB_PISO as PISO, "
			+ "	TP_LAB_TIPO.NOMBRE AS TIPO_LABORATORIO, "
			+ "	PER.PER_NOMBRE1 || ' ' ||  PER.PER_NOMBRE2 || ' ' ||  PER.PER_APELLIDO1 || ' ' ||  PER.PER_APELLIDO2 AS COORDINADOR, "
			+ "	PER.PER_EMAIL as EMAIL_COORDINADOR, "
			+ "	AREA_PR_OCDE.NOMBRE AS AREA_OCDE_PRINCIPAL_LAB, "
			+ "	UPPER(TP_LAB_ACRED.NOMBRE) AS LABORATORIO_ACREDITADO, "
			+ "	L.LAB_EMAIL as EMAIL_LABORATORIO, "
			+ "	LES.LDENS_NOMBRE AS NOMBRE_SERVICIO, "
			+ "	LES.LDENS_DESCRIPCION AS DESCRIPCION_SERVICIO, "
			+ "	TP_ES.NOMBRE AS TIPO, "
			+ "	decode(TP_ES.ID,7246,LES.LDENS_TIPO_ENSAYO_OTROS,'No Aplica') AS OTRO_TIPO_CUAL, "
			+ "	decode(LES.LDENS_EXTENSION,'1','SI','NO') AS EXTENSION, "
			+ "	decode(LES.LDENS_INVESTIGACION,'1','SI','NO') AS INVESTIGACION, "
			+ "	decode(LES.LDENS_DOCENCIA,'1','SI','NO') AS DOCENCIA, "
			+ "	LES.LDENS_VALOR_SERVICIO AS VALOR_SERVICIO, "
			+ "	LES.LDENS_ENSAYOS_MES AS SERVICIOS_X_MES, "
			+ "	TP_NT.NOMBRE || ' ' || LES.LDENS_NORMA_TECNICA_NUMERO AS NORMA_TECNICA, "
			+ "	LES.LDENS_NORMA_TECNICA_VERSION AS VERSION_NORMA_TECNICA, "
			+ "	LES.LDENS_TIEMPO_TOTAL_ESTIMADO || ' ' || T_EST.NOMBRE AS TIEMPO_ESTIMADO, "
			+ "	LES.LDENS_PERSONAS AS NUMERO_PERSONAS, "
			+ "	decode(LES.LDENS_ACREDITADO,'1','SI','NO') AS SERVICIO_ACREDITADO, "
			+ "	decode(LES.LDENS_ACREDITADO,'1',ACRED_ORG.NOMBRE,'No Aplica') AS ORGANISMO_ACREDITA, "
			+ "	(SELECT LISTAGG(LDE.LDE_PLACA,';') WITHIN GROUP (ORDER BY LDE.LDE_PLACA) FROM HER_LABORATORIO_ENSAYO_EQUIPOS LEE INNER JOIN HER_LABORATORIO_DETALLE_ENSAYO LDES ON LDES.LDENS_ID = LEE.LDENS_ID INNER JOIN HER_LABORATORIO_DETALLE_EQUIPO LDE ON LDE.LDE_ID = LEE.LDE_ID WHERE LDES.LDENS_ID = LES.LDENS_ID GROUP BY LDES.LDENS_ID) AS EQUIPOS_ASOCIADOS_SERVICIO, "
			+ "	decode(TP_ES.ID,7244,MG_AREA.NOMBRE,'No Aplica') AS CALIB_MAGNITUD_AREA, "
			+ "	decode(TP_ES.ID,7244,LES.LDENS_CALIB_EQUIPO_INSTRUMENTO,'No Aplica') AS CALIB_EQUIPO_INSTRUMENTO, "
			+ "	decode(TP_ES.ID,7243,LES.LDENS_INTERV_MIN,'No Aplica') AS VALOR_MIN_INTERVALO,  "
			+ "	decode(TP_ES.ID,7243,LES.LDENS_INTERV_MAX,'No Aplica') AS VALOR_MAX_INTERVALO, "
			+ "	decode(TP_ES.ID,7243,PRE1.NOMBRE,'No Aplica') AS PREFIJO_UNID_INTERVALO, "
			+ "	decode(TP_ES.ID,7243,UNI1.NOMBRE,'No Aplica') AS UNIDAD_UNID_INTERVALO, "
			+ "	decode(TP_ES.ID,7243,LES.LDENS_UNID_INTERV_SIMBOLO,'No Aplica') AS SIMBOLO_UNID_INTERVALO, "
			+ "	decode(TP_ES.ID,7243,INC2.NOMBRE,'No Aplica') AS TIPO_INCERT_MED, "
			+ "	decode(TP_ES.ID,7243,LES.LDENS_INCERT_MED_VALOR,'No Aplica') AS VALOR_INCERT_MED, "
			+ "	decode(TP_ES.ID,7243,PRE2.NOMBRE,'No Aplica') AS PREFIJO_INCERT_MED, "
			+ "	decode(TP_ES.ID,7243,UNI2.NOMBRE,'No Aplica') AS UNIDAD_INCERT_MED, "
			+ "	decode(TP_ES.ID,7243,LES.LDENS_INCERT_MED_SIMBOLO,'No Aplica') AS SIMBOLO_INCERT_MED "
			+ "	FROM HER_LABORATORIO_DETALLE_ENSAYO LES "
			+ "	INNER JOIN HER_LABORATORIO L ON L.LAB_ID = LES.LAB_ID "
			+ "	LEFT JOIN HER_PERSONA_LABORATORIO PLAB ON L.LAB_ID = PLAB.LAB_ID AND PLAB.ROL_ID = 'CO' "
			+ "	LEFT JOIN HER_PERSONA PER ON PER.TDO_ID = PLAB.TDO_ID AND PER.PER_ID = PLAB.PER_ID "
			+ "	LEFT JOIN HER_SEDE S ON S.SED_ID = L.SED_ID "
			+ "	LEFT JOIN HER_DEPENDENCIA F ON F.dpn_id = L.lab_facultad "
			+ "	LEFT JOIN HER_DEPENDENCIA D ON D.dpn_id = L.lab_depto "
			+ "	LEFT JOIN HER_TIPOS TP_LAB_TIPO ON TP_LAB_TIPO.ID = L.LAB_TIPO "
			+ "	LEFT JOIN HER_TIPOS TP_LAB_ACRED ON TP_LAB_ACRED.ID = L.LAB_GESTION_ACREDITACION "
			+ "	LEFT JOIN HER_TIPOS TP_NT ON TP_NT.ID = LES.LDENS_NORMA_TECNICA_TIPO "
			+ "	LEFT JOIN HER_TIPOS T_EST ON T_EST.ID = LES.LDENS_TIEMPO_TOTAL_ESTIMADO_UN "
			+ "	LEFT JOIN HER_EDIFICIO EDF ON EDF.EDF_ID = L.EDF_ID "
			+ "	LEFT JOIN HER_TIPOS TP_ES ON TP_ES.ID = LES.LDENS_TIPO_ENSAYO "
			+ "	LEFT JOIN HER_TIPOS ACRED_ORG ON ACRED_ORG.ID = LES.LDENS_ACREDITADO_ORGANISMO "
			+ "	LEFT JOIN HER_TIPOS MG_AREA ON MG_AREA.ID = LES.LDENS_CALIB_MAGNITUD_AREA "
			+ "	LEFT JOIN HER_TIPOS PRE1 ON PRE1.ID = LES.LDENS_UNID_INTERV_PREFIJO "
			+ "	LEFT JOIN HER_TIPOS UNI1 ON UNI1.ID = LES.LDENS_UNID_INTERV_UNIDAD "
			+ "	LEFT JOIN HER_TIPOS INC2 ON INC2.ID = LES.LDENS_INCERT_MED_INCERT "
			+ "	LEFT JOIN HER_TIPOS PRE2 ON PRE2.ID = LES.LDENS_INCERT_MED_PREFIJO "
			+ "	LEFT JOIN HER_TIPOS UNI2 ON UNI2.ID = LES.LDENS_INCERT_MED_UNIDAD "
			+ "	LEFT JOIN HER_TIPOS AREA_PR_OCDE ON AREA_PR_OCDE.ID = L.LAB_AREA_PRINCIPAL "
			+ where
			+ "	ORDER BY L.LAB_ID DESC,LES.LDENS_NOMBRE";
		System.out.println("sql: " + sql);
		listaReporte = servicioGeneral.obtenerMapa(sql,"FROM HER_LABORATORIO_DETALLE_ENSAYO");
		
		crearColumnas();
	}

	public void reporteAsignaturasDeptos() {
		headerDatatable = "Asignaturas por Departamentos";
		nombreArchivo = "Reporte_Asignaturas_Departamentos_";
		
		String where = " where L.LAB_ACTIVO=1 AND L.LAB_ID <> 49 ";
		where = obtenerComplementoWhere(where);		
		
		String sql ="SELECT "
				+ "L.LAB_ID as ID_HERMES_LAB,\r\n"
				+ "L.LAB_NOMBRE AS NOMBRE_LABORATORIO,\r\n"
				+ "(select DPN_NOMBRE from HER_DEPENDENCIA where DPN_ID=L.SED_ID) as SEDE_LAB,\r\n"
				+ "(select DPN_NOMBRE from HER_DEPENDENCIA where DPN_ID=L.LAB_FACULTAD) as FACULTAD_LAB,\r\n"
				+ "(select DPN_NOMBRE from HER_DEPENDENCIA where DPN_ID=L.LAB_DEPTO) as DEPTO_LAB,\r\n"
				+ "EDF.EDF_CODIGO || ' - ' || EDF.EDF_NOMBBRE AS EDIFICIO,\r\n"
				+ "L.LAB_SALON as SALON,\r\n"
				+ "L.LAB_PISO as PISO,\r\n"
				+ "T.NOMBRE as TIPO_LABORATORIO,\r\n"
				+ "P.PER_NOMBRE1 || ' ' ||P.PER_NOMBRE2 || ' ' || P.PER_APELLIDO1 || ' ' || P.PER_APELLIDO2 as COORDINADOR,\r\n"
				+ "P.PER_EMAIL as EMAIL_COORDINADOR,\r\n"
				+ "A.NMBRE_ASIGNATURA AS NOMBRE_ASIGNATURA,\r\n"
				+ "A.COD_ASIGNATURA AS CODIGO,\r\n"
				+ "A.GRUPO AS GRUPO,\r\n"
				+ "A.AÑO || '-' ||A.SEMESTRE AS PERIODO,\r\n"
				+ "A.INSCRITOS AS INSCRITOS,\r\n"
				+ "A.NIVEL AS NIVEL,\r\n"
				+ "D.LDD_PRACTICAS_SEMANALES AS PRACTICAS_SEMANALES,\r\n"
				+ "D.LDD_HORAS_SEMANA AS HORAS_SEMANA,\r\n"
				+ "decode(D.LDD_MOD_VIRTUAL,'1','SI','NO') AS MODALIDAD_VIRTUAL,\r\n"
				+ "decode(D.LDD_MOD_PRESENCIAL,'1','SI','NO') AS MODALIDAD_PRESENCIAL,\r\n"
				+ "(select DPN_NOMBRE from HER_DEPENDENCIA where DPN_ID=A.COD_SEDE) as SEDE_ASIGNATURA,\r\n"
				+ "(select DPN_NOMBRE from HER_DEPENDENCIA where DPN_ID=A.FAC) as FACULTAD_ASIGNATURA,\r\n"
				+ "(select DPN_NOMBRE from HER_DEPENDENCIA where DPN_ID=A.UAB) as DEPTO_ASIGNATURA \r\n"
				+ "FROM HER_LABORATORIO L "
				+ "INNER JOIN HER_LABORATORIO_DET_DOCENCIA D ON L.LAB_ID = D.LAB_ID "
				+ "INNER JOIN SIA_V_ASIGNATURAS_HERMES A ON D.ASI_ID = A.ASI_ID "
				+ "LEFT JOIN HER_TIPOS T ON T.ID = L.LAB_TIPO "
				+ "LEFT JOIN HER_EDIFICIO EDF ON EDF.EDF_ID = L.EDF_ID "
				+ "LEFT JOIN HER_PERSONA_LABORATORIO PL ON PL.LAB_ID = L.LAB_ID AND PL.ROL_ID = 'CO' "
				+ "LEFT JOIN HER_PERSONA P ON P.PER_ID = PL.PER_ID AND P.TDO_ID = PL.TDO_ID "
				+ where
				+"ORDER BY L.LAB_ID,A.COD_ASIGNATURA,PERIODO,A.GRUPO,A.COD_SEDE,A.FAC,A.UAB";
		listaReporte = servicioGeneral.obtenerMapa(sql);
		crearColumnas();
	}
	
	public void reportePersonalAsociado() {
		headerDatatable = "Personal asociado al Laboratorio";
		nombreArchivo = "Personal_asociado_Laboratorio_";
		String where = " where L.LAB_ACTIVO=1 AND L.LAB_ID <> 49 ";
		where = obtenerComplementoWhere(where);
		
		String sql ="SELECT \r\n"
				+ "L.LAB_ID AS ID_LABORATORIO, \r\n"
				+ "L.LAB_NOMBRE AS NOMBRE_LABORATORIO, \r\n"
				+ "SEDE.dpn_nombre AS SEDE, \r\n"
				+ "FAC.dpn_nombre as FACULTAD, \r\n"
				+ "DEP.dpn_nombre as DEPARTAMENTO, \r\n"
				+ "EDF.EDF_CODIGO || ' - ' || EDF.EDF_NOMBBRE AS EDIFICIO, \r\n"
				+ "L.LAB_SALON as SALON, \r\n"
				+ "L.LAB_PISO as PISO, \r\n"
				+ "T.NOMBRE as TIPO_LABORATORIO, \r\n"
				+ "CASE WHEN L.LAB_PERSONAL_SUFICIENTE IN ('1') THEN 'SI' WHEN L.LAB_PERSONAL_SUFICIENTE IN ('0') THEN 'NO' END as PERSONAL_SUFICIENTE, \r\n"
				+ "CASE WHEN L.LAB_PERSONAL_CALIFICADO IN ('1') THEN 'SI' WHEN L.LAB_PERSONAL_CALIFICADO IN ('0') THEN 'NO' END as PERSONAL_CALIFICADO, \r\n"
				+ "pers.TDO_ID AS TDO, \r\n"
				+ "pers.PER_ID AS DOCUMENTO, \r\n"
				+ "pers.PER_NOMBRE1 ||' '||pers.PER_NOMBRE2 AS NOMBRES, \r\n"
				+ "pers.PER_APELLIDO1 ||' '||pers.PER_APELLIDO2 AS APELLIDOS, \r\n"
				+ "pers.PER_EMAIL AS CORREO, \r\n"
				+ "decode(pers.per_genero,'M','Masculino','F','Femenino','') AS GENERO, \r\n"
				+ "PL.HPL_CARGO AS CARGO, \r\n"
				+ "TFO.TFO_NOMBRE AS FORMACION, \r\n"
				+ "rol.ROL_NOMBRE AS NOMBRE_ROL, \r\n"
				+ "CASE WHEN PL.HPL_CONSULTA IN ('1') THEN 'SI' WHEN PL.HPL_CONSULTA IN ('0') THEN 'NO' END as PERMISO_CONSULTA, \r\n"
				+ "CASE WHEN PL.HPL_EDICION IN ('1') THEN 'SI' WHEN PL.HPL_EDICION IN ('0') THEN 'NO' END as PERMISO_EDICION, \r\n"
				+ "TVIN.ID, TVIN.NOMBRE AS VINCULACION, \r\n"
				+ "CASE WHEN TVIN.ID IN (316,314) THEN 'INTERNO' WHEN TVIN.ID = 312 THEN 'EXTERNO' END as INTERNO_EXTERNO, \r\n"
				+ "AREA_PR_OCDE.NOMBRE AS AREA_OCDE_PRINCIPAL \r\n"
				+ "FROM HER_PERSONA_LABORATORIO PL \r\n"
				+ "LEFT JOIN HER_LABORATORIO L ON L.LAB_ID = PL.LAB_ID \r\n"
				+ "LEFT JOIN HER_PERSONA_ROL pr ON (pr.PER_ID =  PL.PER_ID AND pr.TDO_ID = PL.TDO_ID AND pr.ROL_ID = PL.ROL_ID) \r\n"
				+ "LEFT JOIN HER_ROL rol ON PL.ROL_ID = rol.ROL_ID \r\n"
				+ "LEFT JOIN HER_PERSONA pers ON (PL.PER_ID =  pers.PER_ID AND PL.TDO_ID = pers.TDO_ID) \r\n"
				+ "LEFT JOIN HER_INVESTIGADOR inv ON (pers.PER_ID =  inv.INV_ID AND pers.TDO_ID = inv.TDO_ID) \r\n"
				+ "LEFT JOIN HER_INVESTIGADOR_INTERNO invInt ON (invInt.INV_ID =  inv.INV_ID AND invInt.TDO_ID = inv.TDO_ID) \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA dep ON invInt.DPN_ID = dep.DPN_ID \r\n"
				+ "LEFT JOIN HER_TIPOS TVIN ON TVIN.ID = PL.HPL_TIPO_VINCULACION \r\n"
				+ "LEFT JOIN HER_TIPO_FORMACION TFO ON TFO.TFO_ID = invInt.TFO_ID \r\n"
				+ "LEFT JOIN HER_TIPOS T ON T.ID = L.LAB_TIPO \r\n"
				+ "LEFT JOIN HER_EDIFICIO EDF ON EDF.EDF_ID = L.EDF_ID \r\n"
				+ "LEFT JOIN HER_TIPOS AREA_PR_OCDE ON AREA_PR_OCDE.ID = l.LAB_AREA_PRINCIPAL \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA SEDE ON L.SED_ID = SEDE.DPN_ID \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA FAC ON FAC.DPN_ID = L.LAB_FACULTAD \r\n"
				+ "LEFT JOIN HER_DEPENDENCIA DEP ON DEP.DPN_ID = L.LAB_DEPTO \r\n"
				+ where
				+ "ORDER BY L.SED_ID ASC, L.LAB_ID ASC, rol.ROL_NOMBRE ASC";
		listaReporte = servicioGeneral.obtenerMapa(sql);
		crearColumnas();
	}
	
	public void reporteRoles() {
		headerDatatable = "Roles del componente de laboratorios";
		nombreArchivo = "Reporte_Roles_Laboratorios_";

		String where = "WHERE ";
		if(rolSeleccionadoLabs.equals("DL"))
			where += "ROL.ROL_ID IN ('LS','LF','LD','CL') ";
		else if(rolSeleccionadoLabs.equals("LS"))
			where += "ROL.ROL_ID IN ('LF','LD','CL') ";
		else if(rolSeleccionadoLabs.equals("LF"))
			where += "ROL.ROL_ID IN ('LD','CL') ";
		where = obtenerComplementoWhereRolesExternos(where);
		
		String sql = "SELECT\r\n" + 
				"SED.sed_nombre AS SEDE,\r\n" + 
				"FAC.dpn_nombre AS FACULTAD,\r\n" + 
				"DEP.DPN_NOMBRE AS DEPENDENCIA1,\r\n" + 
				"hd.DPN_NOMBRE AS DEPENDENCIA2,\r\n" + 
				"R.ROL_NOMBRE AS ROL,\r\n" + 
				"per.per_nombre1 || decode(per.per_nombre2, NULL, ' ', ' ' || per.per_nombre2 || ' ') || per.per_apellido1 || decode(per.per_apellido2, NULL, '', ' ' || per.per_apellido2) AS NOMBRE_PERSONA,\r\n" + 
				"PER.PER_EMAIL AS CORREO,\r\n" + 
				"ROL.HPR_FECHA AS FECHA_INICIO_ROL,\r\n" + 
				"ROL.HPR_FECHA_FIN AS FECHA_FIN_ROl " + 
				"FROM HER_PERSONA_ROL ROL\r\n" + 
				"INNER JOIN HER_ROL R ON R.ROL_ID = ROL.ROL_ID\r\n" + 
				"INNER JOIN HER_PERSONA PER ON PER.PER_ID = ROL.PER_ID\r\n" + 
				"INNER JOIN her_investigador_interno ii ON ROL.tdo_id = ii.tdo_id AND ROL.PER_ID = ii.inv_id\r\n" + 
				"INNER JOIN HER_DEPENDENCIA DEP ON DEP.DPN_ID = ii.DPN_ID\r\n" + 
				"LEFT JOIN HER_DEPENDENCIA hd ON hd.DPN_ID = ii.DPN_ID2\r\n" + 
				"LEFT JOIN her_dependencia FAC ON FAC.dpn_id = DEP.dpn_facultad\r\n" + 
				"LEFT JOIN her_sede SED ON SED.sed_id = DEP.sed_id\r\n"
				+where;
		listaReporte = servicioGeneral.obtenerMapa(sql);
		crearColumnas();
	}
//	FIN QUERYS REPORTES
	
//	INICIO METROLOGIA
	public void reporteMetrologiaMedidasQuimicas() {
		headerDatatable = "Medidas Quimicas";
		nombreArchivo = "Reporte_Medidas_Quimicas_";

		String where = "WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' ";
		where = obtenerComplementoWhere(where);
		
		String sql = "SELECT "
				+ "L.LAB_ID AS ID_LABORATORIO, "
				+ "L.LAB_NOMBRE AS NOMBRE_LABORATORIO, "
				+ "MEN.NOMBRE AS MENSURANDO, "
				+ "SEC.NOMBRE AS SECTOR, "
				+ "MAT.NOMBRE AS MATRIZ, "
				+ "TEC.NOMBRE AS TECNICA, "
				+ "CON.NOMBRE AS CONCENTRACION, "
				+ "LMQ.LMQ_NORMA_METODO_REF AS NORMA_METODO_REFERENCIA, "
				+ "LMQ.LMQ_METODO_VALIDO_DESC AS METODO_VALIDO, "
				+ "decode(LMQ.LMQ_MATERIALES_REF,1,'SI','NO') AS MATERIALES_REFERENCIA, "
				+ "LMQ.LMQ_FECHA_REGISTRO AS FECHA_REGISTRO "
				+ "FROM HER_LAB_MEDIDAS_QUIMICAS LMQ "
				+ "INNER JOIN HER_LABORATORIO L ON LMQ.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN HER_TIPOS MEN ON LMQ.LMQ_MENSURANDO = MEN.ID "
				+ "LEFT JOIN HER_TIPOS SEC ON LMQ.LMQ_SECTOR = SEC.ID "
				+ "LEFT JOIN HER_TIPOS MAT ON LMQ.LMQ_MATRIZ = MAT.ID "
				+ "LEFT JOIN HER_TIPOS TEC ON LMQ.LMQ_TECNICA = TEC.ID "
				+ "LEFT JOIN HER_TIPOS CON ON LMQ.LMQ_CONCEN_INTERV = CON.ID "
				+ where
				+ "ORDER BY L.LAB_ID, LMQ.LMQ_FECHA_REGISTRO";
		listaReporte = servicioGeneral.obtenerMapa(sql);
		crearColumnas();
	}
	
	public void reporteMetrologiaMedidasMicrobiologicas() {
		headerDatatable = "Medidas Microbiologicas";
		nombreArchivo = "Reporte_Medidas_Microbiologicas_";

		String where = "WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' ";
		where = obtenerComplementoWhere(where);
		
		String sql ="SELECT "
				+ "L.LAB_ID AS ID_LABORATORIO, "
				+ "L.LAB_NOMBRE AS NOMBRE_LABORATORIO, "
				+ "NEN.NOMBRE AS NOMBRE_ENSAYO, "
				+ "SEC.NOMBRE AS SECTOR, "
				+ "MAT.NOMBRE AS MATRIZ, "
				+ "TEN.NOMBRE AS TIPO_ENSAYO, "
				+ "TEC.NOMBRE AS TECNICA, "
				+ "NTE.NOMBRE AS NORMA_TECNICA, "
				+ "MRC.NOMBRE AS MATERIAL_REF_CEPA_CERTIFICADA, "
				+ "LMM.LMM_MAT_REF_CEPA_USADA AS MATERIAL_REF_CEPA_USADA, "
				+ "LMM.LMM_VALIDACION_DESC AS VALIDACION, "
				+ "LMM.LMM_CONTROL_CALIDAD_DESC AS CONTROL_CALIDAD_MEDIOS_CULTIVO, "
				+ "LMM.LMM_COND_AMBIENTALES_DESC AS CONDICIONES_AMBIENTALES, "
				+ "LMM.LMM_FECHA_REGISTRO AS FECHA_REGISTRO "
				+ "FROM HER_LAB_MEDIDAS_MICROBIO LMM "
				+ "INNER JOIN HER_LABORATORIO L ON LMM.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN HER_TIPOS NEN ON LMM.LMM_NOMBRE_ENSAYO = NEN.ID "
				+ "LEFT JOIN HER_TIPOS SEC ON LMM.LMM_SECTOR = SEC.ID "
				+ "LEFT JOIN HER_TIPOS MAT ON LMM.LMM_MATRIZ = MAT.ID "
				+ "LEFT JOIN HER_TIPOS TEN ON LMM.LMM_TIPO_ENSAYO = TEN.ID "
				+ "LEFT JOIN HER_TIPOS TEC ON LMM.LMM_TECNICA = TEC.ID "
				+ "LEFT JOIN HER_TIPOS NTE ON LMM.LMM_NORMA_TECNICA = NTE.ID "
				+ "LEFT JOIN HER_TIPOS MRC ON LMM.LMM_MAT_REF_CEPA_CERT = MRC.ID "
				+ where
				+ "ORDER BY L.LAB_ID, LMM.LMM_FECHA_REGISTRO";

;
		listaReporte = servicioGeneral.obtenerMapa(sql);
		crearColumnas();
	}
	
	public void reporteMetrologiaMedidasFisicas() {
		headerDatatable = "Medidas Físicas";
		nombreArchivo = "Reporte_Medidas_Fisicas_";

		String where = "WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' ";
		where = obtenerComplementoWhere(where);
		
		String sql = "SELECT "
				+ "L.LAB_ID AS ID_LABORATORIO, "
				+ "L.LAB_NOMBRE AS NOMBRE_LABORATORIO, "
				+ "MFI.NOMBRE AS MAGNITUD_FISICA, "
				+ "CAM.NOMBRE AS CAMPO_APLICACION, "
				+ "LMF.LMF_DESCIP_MENSURANDO AS DESCRIPCION_MENSURANDO, "
				+ "LMF.LMF_FACTOR_COBERTURA AS FACTOR_COBERTURA, "
				+ "LMF.LMF_NORMA_DOC_MET_REF AS NORMA_DOC_METODO_REFERENCIA, "
				+ "LMF.LMF_INTERV_MIN AS VALOR_MINIMO_INTERVALO, "
				+ "LMF.LMF_INTERV_MAX AS VALOR_MAXIMO_INTERVALO, "
				+ "PRE1.NOMBRE AS PREFIJO_UNID_INTERVALO, "
				+ "UNI1.NOMBRE AS UNIDAD_UNID_INTERVALO, "
				+ "LMF.LMF_UNID_INTERV_SIMBOLO AS SIMBOLO_UNID_INTERVALO, "
				+ "INC2.NOMBRE AS TIPO_INCERTIDUMBRE_MEDICION, "
				+ "LMF.LMF_INCERT_MED_VALOR AS VALOR_INCERTIDUMBRE_MEDICION, "
				+ "PRE2.NOMBRE AS PREFIJO_INCERTIDUMBRE_MEDICION, "
				+ "UNI2.NOMBRE AS UNIDAD_INCERTIDUMBRE_MEDICION, "
				+ "LMF.LMF_INCERT_MED_SIMBOLO AS SIMBOLO_INCERTIDUMBRE_MEDICION, "
				+ "decode(LMF.LMF_ACREDITADO,1,'SI','NO') AS ACREDITADO, "
				+ "LMF.LMF_ENT_ACRED_TIPO_DOC AS TIPO_DOC_ENTIDAD_ACREDITA, "
				+ "LMF.LMF_ENT_ACRED_NUM_DOC AS NUM_DOC_ENTIDAD_ACREDITA, "
				+ "LMF.LMF_ENT_ACRED_NOMBRE AS NOMBRE_ENTIDAD_ACREDITA, "
				+ "LMF.LMF_FECHA_REGISTRO AS FECHA_REGISTRO "
				+ "FROM HER_LAB_MEDIDAS_FISICAS LMF "
				+ "INNER JOIN HER_LABORATORIO L ON LMF.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN HER_TIPOS MFI ON LMF.LMF_MAGNITUD_FISICA = MFI.ID "
				+ "LEFT JOIN HER_TIPOS CAM ON LMF.LMF_CAMPO_APLICACION = CAM.ID "
				+ "LEFT JOIN HER_TIPOS PRE1 ON LMF.LMF_UNID_INTERV_PREFIJO = PRE1.ID "
				+ "LEFT JOIN HER_TIPOS UNI1 ON LMF.LMF_UNID_INTERV_UNIDAD = UNI1.ID "
				+ "LEFT JOIN HER_TIPOS INC2 ON LMF.LMF_INCERT_MED_INCERT = INC2.ID "
				+ "LEFT JOIN HER_TIPOS PRE2 ON LMF.LMF_INCERT_MED_PREFIJO = PRE2.ID "
				+ "LEFT JOIN HER_TIPOS UNI2 ON LMF.LMF_INCERT_MED_UNIDAD = UNI2.ID "
				+ "LEFT JOIN HER_TIPO_DOCUMENTO TDO ON TDO.TDO_ID = LMF.LMF_ENT_ACRED_TIPO_DOC "
				+ where
				+ "ORDER BY L.LAB_ID, LMF.LMF_FECHA_REGISTRO";
;
		listaReporte = servicioGeneral.obtenerMapa(sql);
		crearColumnas();
	}
	
	public void reporteMetrologiaEnsayosFisicos() {
		headerDatatable = "Ensayos Físicos";
		nombreArchivo = "Reporte_Ensayos_Fisicos_";

		String where = "WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' ";
		where = obtenerComplementoWhere(where);
		
		String sql = "SELECT "
				+ "L.LAB_ID AS ID_LABORATORIO, "
				+ "L.LAB_NOMBRE AS NOMBRE_LABORATORIO, "
				+ "LEF.LEF_NOMBRE_ENSAYO AS NOMBRE_ENSAYO, "
				+ "LEF.LEF_PRODUCTO_MATERIAL AS PRODUCTO_MATERIAL, "
				+ "LEF.LEF_PROPIEDADES_MEDIBLES AS PROPIEDADES_MEDIBLES, "
				+ "LEF.LEF_MINIMO AS MINIMO, "
				+ "LEF.LEF_MAXIMO AS MAXIMO, "
				+ "LEF.LEF_UNIDADES AS UNIDADES, "
				+ "LEF.LEF_DESCRIPCION AS DESCRIPCION, "
				+ "LEF.LEF_NORMA_TEC_PROCED AS NORMA_TECNICA_PROCEDIMIENTO, "
				+ "LEF.LEF_FECHA_REGISTRO AS FECHA_REGISTRO "
				+ "FROM HER_LAB_ENSAYOS_FISICOS LEF "
				+ "INNER JOIN HER_LABORATORIO L ON LEF.LAB_ID = L.LAB_ID "
				+ where
				+ "ORDER BY L.LAB_ID, LEF.LEF_FECHA_REGISTRO";

		listaReporte = servicioGeneral.obtenerMapa(sql);
		crearColumnas();
	}
	
	public void reporteMetrologiaMaterialesReferencia() {
		headerDatatable = "Materiales Referencia";
		nombreArchivo = "Reporte_Materiales_Referencia_";

		String where = "WHERE L.LAB_ID <> '49' AND L.LAB_ACTIVO = '1' ";
		where = obtenerComplementoWhere(where);
		
		String sql = "SELECT "
				+ "L.LAB_ID AS ID_LABORATORIO, "
				+ "L.LAB_NOMBRE AS NOMBRE_LABORATORIO, "
				+ "LMR.LMR_NOMBRE AS NOMBRE, "
				+ "LMR.LMR_PRODUCTOR AS PRODUCTOR, "
				+ "LMR.LMR_PROVEEDOR AS PROVEEDOR, "
				+ "NMR.NOMBRE AS USA_MATERIAL_REFERENCIA, "
				+ "TPO.NOMBRE AS TIPO, "
				+ "decode(LMR.LMR_DIFICIL_ADQUI,1,'SI','NO') AS DIFICIL_ADQUISICION, "
				+ "decode(LMR.LMR_USO_TRAZAB_MEDIC,1,'SI','NO') AS ASEGURA_TRAZABILIDAD, "
				+ "LMR.LMR_FECHA_REGISTRO AS FECHA_REGISTRO "
				+ "FROM HER_LAB_MATERIALES_REFERENCIA LMR "
				+ "INNER JOIN HER_LABORATORIO L ON LMR.LAB_ID = L.LAB_ID "
				+ "LEFT JOIN HER_TIPOS NMR ON LMR.LMR_USA_MATERIAL = NMR.ID "
				+ "LEFT JOIN HER_TIPOS TPO ON LMR.LMR_TIPO = TPO.ID "
				+ where
				+ "ORDER BY L.LAB_ID, LMR.LMR_FECHA_REGISTRO";


		listaReporte = servicioGeneral.obtenerMapa(sql);
		crearColumnas();
	}
	
//	FIN METROLOGIA
	
	public StreamedContent getChart() {
		FacesContext context = FacesContext.getCurrentInstance();

		// if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
		if (context.getRenderResponse()) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			chart = new DefaultStreamedContent();
		} else {
			try {
				headerDatatable = "Laboratorios Activos Universidad Nacional de Colombia";
				String sql = "SELECT S.SED_NOMBRE AS SEDE, COUNT(*) AS CANTIDAD FROM HER_LABORATORIO L, HER_SEDE S WHERE L.LAB_ACTIVO=1 AND L.LAB_ID<>49 and L.SED_ID = S.SED_ID group by S.SED_NOMBRE order by CANTIDAD DESC";
				JFreeChart jfreechart = ChartFactory.createPieChart3D(headerDatatable, createDataset(sql),false, true, false);

				// footnote
				// jfreechart.addSubtitle(new TextTitle("textTile***"));

				String subTitulo = "Total Laboratorios Activos UN: "+ sumaTotal.intValue();
				// from Dialog
				jfreechart.addSubtitle(new TextTitle(subTitulo, new Font(
						"Tahoma", Font.ITALIC, 14), Color.black,
						RectangleEdge.BOTTOM, HorizontalAlignment.CENTER,
						VerticalAlignment.BOTTOM, RectangleInsets.ZERO_INSETS));

				PiePlot plot = (PiePlot) jfreechart.getPlot();
				plot.setDrawingSupplier(new ChartDrawingSupplier());
				plot.setBackgroundPaint(Color.white);
				// plot.setLegendLabelGenerator(new
				// StandardPieSectionLabelGenerator("aaa {0}: {2}"));
				// plot.setToolTipGenerator(new
				// StandardPieToolTipGenerator("ccc {0}"));
				// plot.setLabelGenerator(new
				// StandardPieSectionLabelGenerator("{0} = {1} ({2})"));
				PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(
						"{0} = {1} ({2})", new DecimalFormat("0"),
						new DecimalFormat("0.0%"));
				plot.setLabelGenerator(generator);
				// plot.setSimpleLabels(false); // etiqueta sobre el pie
				plot.setStartAngle(45);
				// plot.setForegroundAlpha(0.5f); // transparencia pie
				plot.setNoDataMessage("No data to display");

				File chartFile = new File("dynamichart");
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 640, 400); // 375
				// *
				// 300
				chart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chart;
	}

	public TextTitle subTitulo(String texto) {
		TextTitle tt = new TextTitle(texto,
				new Font("Tahoma", Font.ITALIC, 14), Color.black,
				RectangleEdge.BOTTOM, HorizontalAlignment.CENTER,
				VerticalAlignment.BOTTOM, RectangleInsets.ZERO_INSETS);
		return tt;
	}

	public StreamedContent getChart2() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getRenderResponse()) {
			chart = new DefaultStreamedContent();
		} else {
			try {
				headerDatatable = "Laboratorios UN por tipo de Dedicación";
				String sql = "SELECT DEDICACION AS DEDICACION, COUNT(*) AS CANTIDAD FROM (\r\n"
						+ "SELECT case when L.LAB_DEDICACION_DOC+L.LAB_DEDICACION_INV+L.LAB_DEDICACION_EXT not between 99 and 100 then 'Desconocida' \r\n"
						+ "when L.LAB_DEDICACION_DOC=100 then 'Exclusiva Docencia' when L.LAB_DEDICACION_INV=100 \r\n"
						+ "then 'Exclusiva Investigación' when L.LAB_DEDICACION_EXT=100 then 'Exclusiva Extensión' \r\n"
						+ "when L.LAB_DEDICACION_DOC=0 then 'Extensión - Investigación' \r\n"
						+ "when L.LAB_DEDICACION_INV=0 then 'Docencia - Extensión' \r\n"
						+ "when L.LAB_DEDICACION_EXT=0 then 'Docencia - Investigación' else 'Docencia - Extensión - Investigación' \r\n"
						+ "end as DEDICACION\r\n"
						+ "FROM HER_LABORATORIO L WHERE L.LAB_ACTIVO=1)\r\n"
						+ "GROUP BY DEDICACION ORDER BY DEDICACION ";
				JFreeChart jfreechart = ChartFactory
						.createPieChart3D(headerDatatable, createDataset(sql),
								false, true, false);
				jfreechart.addSubtitle(subTitulo("Total Laboratorios UN por tipo de Dedicación: "+ sumaTotal.intValue()));
				PiePlot plot = (PiePlot) jfreechart.getPlot();
				plot.setDrawingSupplier(new ChartDrawingSupplier());
				plot.setBackgroundPaint(Color.white);
				PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(
						"{0} = {1} ({2})", new DecimalFormat("0"),
						new DecimalFormat("0.0%"));
				plot.setLabelGenerator(generator);
				plot.setStartAngle(45);
				plot.setNoDataMessage("No data to display");

				File chartFile = new File("dynamichart2");
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 640, 400);
				chart = new DefaultStreamedContent(new FileInputStream(
						chartFile), "image/png");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chart;
	}

	public StreamedContent getChart3() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getRenderResponse()) {
			chart = new DefaultStreamedContent();
		} else {
			try {
				String sql = "SELECT dd.domdet_descripcion AS AREA_OCDE, COUNT(*) AS CANTIDAD_LABORATORIOS "
						+ "FROM HER_LABORATORIO_AREA_OCDE ao, her_dominio_detalle dd "
						+ "where dd.DOMDET_TIPO = ao.DOMDET_TIPO and dd.DOM_ID = ao.DOM_ID and ao.HLAO_ID in "
						+ "(SELECT MIN(HLAO_ID) FROM HER_LABORATORIO_AREA_OCDE "
						+ " WHERE LAB_ID IN (select LAB_ID from her_laboratorio Where LAB_ACTIVO = 1) GROUP BY LAB_ID) "
						+ "GROUP BY dd.domdet_descripcion ORDER BY dd.domdet_descripcion";
				String headerChart = "Laboratorios UN por áreas OCDE";

				// sql =
				// "SELECT dpn_nombre AS FACULTAD, 1 AS CANTIDAD FROM her_dependencia where dpn_es_facultad = 'Y' and sed_id = '2'";
				// sql =
				// "SELECT dpn_nombre AS FACULTAD, 1 AS CANTIDAD FROM her_dependencia where dpn_es_facultad = 'Y' and sed_id = '2' and dpn_id in (select lab_facultad from her_laboratorio)";

				JFreeChart jfreechart = ChartFactory.createPieChart3D(
						headerChart, createDataset(sql), false, true, false);
				jfreechart.addSubtitle(subTitulo("Total Laboratorios UN por áreas OCDE: "+ sumaTotal.intValue()));
				PiePlot plot = (PiePlot) jfreechart.getPlot();
				plot.setDrawingSupplier(new ChartDrawingSupplier());
				plot.setBackgroundPaint(Color.white);
				PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(
						"{0} = {1} ({2})", new DecimalFormat("0"),
						new DecimalFormat("0.0%"));
				plot.setLabelGenerator(generator);
				plot.setStartAngle(225);
				plot.setNoDataMessage("No data to display");

				File chartFile = new File("dynamichart3");
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 640, 400);
				chart = new DefaultStreamedContent(new FileInputStream(
						chartFile), "image/png");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chart;
	}

	public class ChartDrawingSupplier extends DefaultDrawingSupplier {

		private static final long serialVersionUID = -1826859992461546297L;
		public Paint[] paintSequence;
		public int paintIndex;
		public int fillPaintIndex;
		{
			paintSequence = new Paint[] { new Color(227, 26, 28),
					new Color(000, 102, 204), new Color(102, 051, 153),
					new Color(102, 51, 0), new Color(156, 136, 48),
					new Color(153, 204, 102), new Color(153, 51, 51),
					new Color(102, 51, 0), new Color(204, 153, 51),
					new Color(0, 51, 0), };

			// azules y naranjas:
			paintSequence = new Paint[] { new Color(239, 243, 255),
					new Color(198, 219, 239), new Color(158, 202, 225),
					new Color(107, 174, 214), new Color(49, 130, 189),
					new Color(8, 81, 156), new Color(253, 208, 162),
					new Color(253, 174, 107), new Color(253, 141, 60),
					new Color(230, 85, 13), new Color(166, 54, 3), };

			// http://colorbrewer2.org/ : qualitative
			paintSequence = new Paint[] { new Color(166, 206, 227),
					new Color(31, 120, 180), new Color(178, 223, 138),
					new Color(51, 160, 44), new Color(251, 154, 153),
					new Color(227, 26, 28), new Color(253, 191, 111),
					new Color(255, 127, 0), new Color(202, 178, 214),
					new Color(106, 61, 154), new Color(255, 255, 153),
					new Color(177, 89, 40), };

		}

		@Override
		public Paint getNextPaint() {
			Paint result = paintSequence[paintIndex % paintSequence.length];
			paintIndex++;
			return result;
		}

		@Override
		public Paint getNextFillPaint() {
			Paint result = paintSequence[fillPaintIndex % paintSequence.length];
			fillPaintIndex++;
			return result;
		}
	}

	private PieDataset createDataset(String sql) {
		List<Map> listaReporte2;
		listaReporte2 = servicioGeneral.obtenerMapa(sql);

		Integer registros = listaReporte2.size();
		Integer columnas = 2; // / siempre dos ???? series???

		String[] datos = new String[registros * columnas];
		int k = 0;
		for (Map<String, String> mapa : listaReporte2) {
			// columnas = mapa.size();
			for (Object value : mapa.values()) {
				String cadena = (String) value;
				datos[k] = cadena;
				k++;
			}
		}

		sumaTotal = 0D;
		DefaultPieDataset dataset = new DefaultPieDataset();
		int size = datos.length;
		for (int j = 0; j < size; j += columnas) {
			Double valorNum = new Double(datos[j + 1]);
			dataset.setValue(datos[j], valorNum);
//			sumaTotal += valorNum;
		}
		
		try {
			sumaTotal = (double) servicioGeneral.consultaTotalLaboratoriosActivos();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataset;
	}

	// gráficas primefaces:
	public PieChartModel getModel() {
		model = new PieChartModel();
		model.set("Brand 1", 540);
		model.set("Brand 2", 325);
		model.set("Brand 3", 702);
		model.set("Brand 4", 421);
		return model;
	}
	
	static public class ColumnModel implements Serializable {
		private static final long serialVersionUID = -7233345602229901803L;
		private String header;
		private String property;

		public ColumnModel(String header, String property) {
			this.header = header;
			this.property = property;
		}

		/**
		 * Constructor con un solo parámetro, header y property son iguales.
		 * 
		 * @param headerProperty
		 */
		public ColumnModel(String headerProperty) {
			this.header = headerProperty;
			this.property = headerProperty;
		}

		public String getHeader() {
			return header;
		}

		public String getProperty() {
			return property;
		}
	}

	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		wb.setSheetName(0, "SNL");
		int numeroColumnas = header.getPhysicalNumberOfCells();
		int numeroFilas = sheet.getPhysicalNumberOfRows();
		boolean filaTotalesCreada = false;
		for (int columna = 0; columna < numeroColumnas; columna++) {
			double suma = 0;
			boolean sumarColumna = true;

			// NO se suman algunas columnas, definidas en este IF:
			String stringHeader = header.getCell(columna).getStringCellValue()
					.toUpperCase();
			if (stringHeader.startsWith("ID_")
					|| stringHeader.startsWith("ETAPA")
					|| stringHeader.startsWith("PLACA")
					|| stringHeader.equals("ANNIO")
					|| stringHeader.equals("SEMESTRE")) {
				sumarColumna = false;
			}

			if (sumarColumna) {
				for (int fila = 1; fila < numeroFilas; fila++) {
					try {
						double valorCasilla = Double.parseDouble(sheet
								.getRow(fila).getCell(columna)
								.getStringCellValue());
						suma += valorCasilla;
						sheet.getRow(fila)
								.createCell(columna, HSSFCell.CELL_TYPE_NUMERIC)
								.setCellValue(valorCasilla);
					} catch (NumberFormatException nfe) {
						sumarColumna = false;
						fila = numeroFilas;
					}
				}
			}

			if (sumarColumna) {
				if (!filaTotalesCreada) {
					sheet.createRow(numeroFilas);
				}
				// ahora existe una nueva fila
				sheet.getRow(numeroFilas)
						.createCell(columna, HSSFCell.CELL_TYPE_NUMERIC)
						.setCellValue(suma);
				sheet.getRow(numeroFilas).getCell(columna)
						.setCellStyle(cellStyle);
				filaTotalesCreada = true;
			}

		}

		// formato fechas a las columnas cuyo header contiene "fecha":
		for (int columna = 0; columna < numeroColumnas; columna++) {
			HSSFCell celda = header.getCell(columna);
			if (celda.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				String valorCelda = celda.getRichStringCellValue().getString();
				if (valorCelda.toUpperCase().contains("FECHA")) {
					HSSFCellStyle estiloCelda = wb.createCellStyle();
					HSSFCreationHelper createHelper = wb.getCreationHelper();
					estiloCelda.setDataFormat(createHelper.createDataFormat()
							.getFormat("dd/MM/yyyy"));
					for (int fila = 1; fila < numeroFilas; fila++) {
						String stringFecha = sheet.getRow(fila)
								.getCell(columna).getRichStringCellValue()
								.getString();
						DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
						try {
							Date fecha = format.parse(stringFecha);
							sheet.getRow(fila).getCell(columna).setCellValue(fecha);
							sheet.getRow(fila).getCell(columna).setCellStyle(estiloCelda);
						} catch (Exception e) {
						}
					}
				}
			}
		}

		// fija el estilo al encabezado
		for (int i = 0; i < numeroColumnas; i++) {
			header.getCell(i).setCellStyle(cellStyle);
			//sheet.autoSizeColumn(i);
		}

		// Inmovilizar fila superior:
		sheet.createFreezePane(0, 1);
	}

	public void preProcessPDF(Object document) throws IOException,
			BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.setPageSize(PageSize.LETTER.rotate());
		HeaderFooter footer = new HeaderFooter(new Phrase(headerDatatable
				+ ", página: "), true);// (new Phrase("This is my footer"),
		// true);
		pdf.setFooter(footer);
		pdf.open();
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "Logo_Hermes_Home.png";
		Image imagen = Image.getInstance(logo);
		imagen.scalePercent(50);
		pdf.add(imagen);
	}

	/**
	 * @return the esLaboratorios
	 */
	public Boolean getEsLaboratorios() {
		return esLaboratorios;
	}

	/**
	 * @return the listaSedes
	 */
	/*
	 * public List<Map> getListaSedes() { return listaReporte; }
	 */

	/**
	 * @return the selectItemSedes
	 */
	public SelectItem[] getSelectItemSedes() {
		return selectItemSedes;
	}

	/**
	 * @return the sedeSeleccionada
	 */
	public Sede getSedeSeleccionada() {
		return sedeSeleccionada;
	}

	/**
	 * @param sedeSeleccionada
	 *            the sedeSeleccionada to set
	 */
	public void setSedeSeleccionada(Sede sedeSeleccionada) {
		this.sedeSeleccionada = sedeSeleccionada;
	}

	/**
	 * @return the selectItemFacultades
	 */
	public SelectItem[] getSelectItemFacultades() {
		return selectItemFacultades;
	}

	/**
	 * @return the facultadSeleccionada
	 */
	public Dependencia getFacultadSeleccionada() {
		return facultadSeleccionada;
	}

	/**
	 * @param facultadSeleccionada
	 *            the facultadSeleccionada to set
	 */
	public void setFacultadSeleccionada(Dependencia facultadSeleccionada) {
		this.facultadSeleccionada = facultadSeleccionada;
	}

	/**
	 * @return the selectItemDepartamentos
	 */
	public SelectItem[] getSelectItemDepartamentos() {
		return selectItemDepartamentos;
	}

	/**
	 * @return the departamentoSeleccionado
	 */
	public Dependencia getDepartamentoSeleccionado() {
		return departamentoSeleccionado;
	}

	/**
	 * @param departamentoSeleccionado
	 *            the departamentoSeleccionado to set
	 */
	public void setDepartamentoSeleccionado(Dependencia departamentoSeleccionado) {
		this.departamentoSeleccionado = departamentoSeleccionado;
	}

	/**
	 * @return the listaReporte08
	 */
	public List<Map> getListaReporte() {
		mensajeError = "";
		if (listaReporte != null && listaReporte.size() < 1) {
			mensajeError = "El reporte no se puede generar porque no existe información que cumpla con los criterios seleccionados";
		}

		if (listaReporte != null && listaReporte.size() > 0) {
			mensajeError = listaReporte.size() + " registros encontrados";
		}

		return listaReporte;
	}

	/**
	 * @return the columnas
	 */
	public List<ColumnModel> getColumnas() {
		return columnas;
	}

	/**
	 * @return the headerDatatable
	 */
	public String getHeaderDatatable() {
		return headerDatatable;
	}

	/**
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	}

	/**
	 * @return the esLaboratoriosSede
	 */
	public Boolean getEsLaboratoriosSede() {
		return esLaboratoriosSede;
	}

	/**
	 * @return the mensajeError
	 */
	public String getMensajeError() {
		return mensajeError;
	}

	/**
	 * @return the esLaboratoriosFacultad
	 */
	public Boolean getEsLaboratoriosFacultad() {
		return esLaboratoriosFacultad;
	}

	/**
	 * @return the esLaboratoriosDepto
	 */
	public Boolean getEsLaboratoriosDepto() {
		return esLaboratoriosDepto;
	}

	/**
	 * @return the esLaboratoriosConsulta
	 */
	public Boolean getEsLaboratoriosConsulta() {
		return esLaboratoriosConsulta;
	}

	public String getRolSeleccionadoLabs() {
		return rolSeleccionadoLabs;
	}

	public void setRolSeleccionadoLabs(String rolSeleccionadoLabs) {
		this.rolSeleccionadoLabs = rolSeleccionadoLabs;
	}

	public Boolean getEsPersonalLaboratorio() {
		return esPersonalLaboratorio;
	}

	public void setEsPersonalLaboratorio(Boolean esPersonalLaboratorio) {
		this.esPersonalLaboratorio = esPersonalLaboratorio;
	}

	public Boolean getEsCoordinadorLaboratorio() {
		return esCoordinadorLaboratorio;
	}

	public void setEsCoordinadorLaboratorio(Boolean esCoordinadorLaboratorio) {
		this.esCoordinadorLaboratorio = esCoordinadorLaboratorio;
	}

	public String getTipoRolSeleccionado() {
		return tipoRolSeleccionado;
	}

	public void setTipoRolSeleccionado(String tipoRolSeleccionado) {
		this.tipoRolSeleccionado = tipoRolSeleccionado;
	}

	public SelectItem[] getLaboratoriosSelectItem() {
		return laboratoriosSelectItem;
	}

	public void setLaboratoriosSelectItem(SelectItem[] laboratoriosSelectItem) {
		this.laboratoriosSelectItem = laboratoriosSelectItem;
	}

	public String getLabSeleccionado() {
		return labSeleccionado;
	}

	public void setLabSeleccionado(String labSeleccionado) {
		this.labSeleccionado = labSeleccionado;
	}	
	
}
