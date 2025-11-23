package co.edu.unal.hermes.vista.proyectos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorReporteProyectos extends ManejadorBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TIPO_CONV_INTERNA = "1";
	public static final String TIPO_JORNADA_DOCENTE = "2";

	private Date fechaInicio;
	private Date fechaFin;
	private String selItem;
	private boolean reporteHistorico = false;
	List listaEstadosProyecto = new ArrayList<SelectItem>();
	private SelectItem[] listaSedesItem;
	private List<Dependencia> listaSedes;
	private String sede;
	private String sedeLeg;
	private String sedeReg;
	private SelectItem[] listaDependenciasItem;
	private SelectItem[] listaFacultadesItem;
	private List<Dependencia> listaDependencias;
	private String facultad = "0";
	private String facultadLeg = "0";
	private String facultadReg = "0";
	private SelectItem[] listaDeptosItem;
	private List<Dependencia> listaDeptos;
	private String deptos;
	private String[] selectedDeptos;
	private String[] selectedEstados;
	private String[] selectedEstadosLeg;
	private String[] selectedFacultades;
	private String[] selectedSedes;
	private String facultades;
	private String estados;
	private List<ConvocatoriaPadre> listadoConvocatoriaPadre;
	private List listaConvocatorias;
	private String[] selectedConvocatorias;
	private String convocatoriaSel;
	private SelectItem[] convocatoriaItem;
	private String reporteHistoricoLeg = "No";
	private Date fechaInicioLeg;
	private Date fechaFinLeg;
	private List<Dependencia> facultadesUN;
	private SelectItem[] facultadItem;
	private String facultadSel;
	private boolean mostrarFacultades = true;
	private boolean mostrarPanelFechas = false;
	private List<Proyecto> listaProyectosJornada;
	private List<Proyecto> filteredProyectos;
	private int totalProyectosJornada;
	private Long proyectoSeleccionado;
	private InvestigadorInterno funcionarioActual;
	private SelectItem[] listaTiposReporteProyectoItem;
	private String tipoProyectoReporte;

	private List<Dependencia> listaDependenciasLeg;
	private SelectItem[] listaDependenciasItemLeg;
	private String[] selectedSedesLeg;
	private SelectItem[] listaSedesItemLeg;
	private String[] selectedFacultadesLeg;
	private SelectItem[] listaFacultadesItemLeg;
	private String[] selectedDeptosLeg;
	private SelectItem[] listaDeptosItemLeg;
	private String deptosLeg;
	private List<Dependencia> listaDeptosLeg;
	private boolean esNacional;
	private boolean esSede;
	private boolean esFacultad;

	private SelectItem[] listaInfoAdicionalReporteProyCoord;
	private String infoAdicionalReporteProyCoord;
	private String msgInfoAdicionalReportePry;
	
	private List<Dependencia> listaDependenciasReg;
	private SelectItem[] listaDependenciasItemReg;
	private String[] selectedSedesReg;
	private SelectItem[] listaSedesItemReg;
	private String[] selectedFacultadesReg;
	private SelectItem[] listaFacultadesItemReg;
	private String[] selectedDeptosReg;
	private SelectItem[] listaDeptosItemReg;
	private String deptosReg;
	private List<Dependencia> listaDeptosReg;

	protected Boolean[] columns = { true, false, false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false };
	protected Boolean[] columnsLeg = { true, true, false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
			false, false };
	protected Boolean[] columnsReg = { true, true, false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
			false };

	private SelectItem[] listaInfoAdicionalReporteProyExt;
	private String infoAdicionalReporteProyExt;
	private String msgInfoAdicionalReportePryExt;
	
	private SelectItem[] listaInfoAdicionalReporteProyReg;
	private String infoAdicionalReporteProyReg;
	private String msgInfoAdicionalReportePryReg;

	public ManejadorReporteProyectos() {
		super();
		esNacional = false;
		esSede = false;
		esFacultad = false;
		cargarTiposReporteProyectos();
		obtenerEstadosProyecto();
		funcionarioActual = servicioPersona.obtenerInvestigadorInterno(cargarPersonaActual().getId());
		verificarNivelUsuario();
		sede = funcionarioActual.getDependencia().getSede().getId().toString();
		sedeLeg = sede;
		listaSedes();
		listaConvocatorias();
		columnsLeg[0] = true;
		columnsLeg[1] = true;
		columnsReg[0] = true;
		columnsReg[1] = true;
		setListaProyectosJornada(new ArrayList<Proyecto>());
		listaInfoAdicionalReporteProyCoord = new SelectItem[13];
		listaInfoAdicionalReporteProyCoord[0] = new SelectItem("SUI", "<Seleccione Un Ítem>");
		listaInfoAdicionalReporteProyCoord[1] = new SelectItem("ATE", "Áreas Temáticas");
		listaInfoAdicionalReporteProyCoord[2] = new SelectItem("SEM", "Semilleros");
		listaInfoAdicionalReporteProyCoord[3] = new SelectItem("DOC", "Docentes Vincuados");
		listaInfoAdicionalReporteProyCoord[4] = new SelectItem("EST", "Estudiantes");
		listaInfoAdicionalReporteProyCoord[5] = new SelectItem("GRU", "Grupos de Investigación");
		listaInfoAdicionalReporteProyCoord[6] = new SelectItem("INF", "Informes de Avance y Final");
		listaInfoAdicionalReporteProyCoord[7] = new SelectItem("PRD", "Productos");
		listaInfoAdicionalReporteProyCoord[8] = new SelectItem("PRO", "Prórrogas");
		listaInfoAdicionalReporteProyCoord[9] = new SelectItem("FFE", "Fuentes de Financiación y Entidades");
		listaInfoAdicionalReporteProyCoord[10] = new SelectItem("LAB", "Laboratorios");
		listaInfoAdicionalReporteProyCoord[11] = new SelectItem("ODS", "Objetivos de Desarrollo Sostenibles");
		listaInfoAdicionalReporteProyCoord[12] = new SelectItem("IIF", "Impactos en Informe Final");
		setInfoAdicionalReporteProyCoord("SUI");
		msgInfoAdicionalReportePry = "";

		listaInfoAdicionalReporteProyReg = new SelectItem[2];
		listaInfoAdicionalReporteProyReg[0] = new SelectItem("SUI", "<Seleccione Un Ítem>");
		listaInfoAdicionalReporteProyReg[1] = new SelectItem("AVA", "Avales");
		setInfoAdicionalReporteProyReg("SUI");
		msgInfoAdicionalReportePryReg = "";
		
		listaInfoAdicionalReporteProyExt = new SelectItem[22];
		listaInfoAdicionalReporteProyExt[0] = new SelectItem("SUI", "<Seleccione Un Ítem>");
		listaInfoAdicionalReporteProyExt[1] = new SelectItem("SFV", "Sedes y Facultades Vinculadas");
		listaInfoAdicionalReporteProyExt[2] = new SelectItem("PRO", "Prórrogas");
		listaInfoAdicionalReporteProyExt[3] = new SelectItem("INA", "Informes de Avance");
		listaInfoAdicionalReporteProyExt[4] = new SelectItem("INF", "Informes Finales");
		listaInfoAdicionalReporteProyExt[5] = new SelectItem("AVA", "Avales");
		listaInfoAdicionalReporteProyExt[6] = new SelectItem("CON", "Convenios");
		listaInfoAdicionalReporteProyExt[7] = new SelectItem("ENF", "Entidades Financiadoras");
		listaInfoAdicionalReporteProyExt[8] = new SelectItem("ENE", "Entidades Ejecutoras");
		listaInfoAdicionalReporteProyExt[9] = new SelectItem("ENP", "Entidades Participantes");
		listaInfoAdicionalReporteProyExt[10] = new SelectItem("DES", "Seguimiento a Desembolsos");
		listaInfoAdicionalReporteProyExt[11] = new SelectItem("EST", "Estudiantes Vinculados");
		listaInfoAdicionalReporteProyExt[12] = new SelectItem("ENT", "Entes Territoriales");
		listaInfoAdicionalReporteProyExt[13] = new SelectItem("GRU", "Grupos de Investigación");
		listaInfoAdicionalReporteProyExt[14] = new SelectItem("CYT", "Programa Nacional de Ciencia y Tecnología");
		listaInfoAdicionalReporteProyExt[15] = new SelectItem("PRE", "Productos Esperados");
		listaInfoAdicionalReporteProyExt[16] = new SelectItem("PRL", "Productos Logrados");
		listaInfoAdicionalReporteProyExt[17] = new SelectItem("PIA", "Planeación - Informes de Avance");
		listaInfoAdicionalReporteProyExt[18] = new SelectItem("ING", "Seguimiento a Ingresos");
		listaInfoAdicionalReporteProyExt[19] = new SelectItem("DRP", "Dependencia Responsable del Proyecto");
		listaInfoAdicionalReporteProyExt[20] = new SelectItem("DOC", "Docentes Vinculados");
		listaInfoAdicionalReporteProyExt[21] = new SelectItem("ODS", "Objetivos de Desarrollo Sostenibles");
		setInfoAdicionalReporteProyExt("SUI");
		msgInfoAdicionalReportePryExt = "";
	}

	public void cargarTiposReporteProyectos() {
		listaTiposReporteProyectoItem = new SelectItem[2];
		listaTiposReporteProyectoItem[0] = new SelectItem(TIPO_CONV_INTERNA, "Proyectos vinculados a convocatorias");
		listaTiposReporteProyectoItem[1] = new SelectItem(TIPO_JORNADA_DOCENTE, "Proyectos de jornada docente");
	}

	public boolean isEsReporteJornadaDocente() {
		if (TIPO_JORNADA_DOCENTE.equals(tipoProyectoReporte)) {
			return true;
		}
		return false;
	}

	public void obtenerEstadosProyecto() {
		String hql = "select ep from EstadoProyecto ep where ep.id not in ('B','PB','BP','ISL','H','APV','NAPV','BS') order by ep.nombre asc";
		List lista = servicioGeneral.obtenerObjetos(hql);

		if (!esListaVacia(lista)) {
			for (int i = 0; i < lista.size(); i++) {
				EstadoProyecto ep = (EstadoProyecto) lista.get(i);
				listaEstadosProyecto.add(new SelectItem(ep.getId(), ep.getNombre()));
			}
		}
	}

	public String imprimirReporteHistorico() {
		personaActual = (Persona) sesion.getAttribute("persona");

		if (this.fechaInicio != null && this.fechaFin != null) {
			if (!this.fechaInicio.before(this.fechaFin)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"La fecha Final debe ser posterior a la de Inicio", ""));
				return "";
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe indicar el rango de fechas del reporte", ""));
			return "";
		}

		int totalFalse = 0;
		for (int a = 0; a < columns.length; a++) {
			if (columns[a].equals(false)) {
				totalFalse++;
			}
		}

		if (totalFalse == columns.length) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar al menos un campo para el reporte", ""));
			return "";
		}

		estadosSeleccionados();
		String sed;
		if (isEsReporteJornadaDocente()) {
			sed = sede;
		} else {
			sed = cargarSedes().substring(1).replace(",", " ");
		}
		convocatoriasSeleccionadas();
		deptosSeleccionados();

		if (esCadenaVacia(estados)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar al menos un estado de proyecto", ""));
			return "";
		}

		if (esCadenaVacia(sed)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos una sede", ""));
			return "";
		}

		if (esCadenaVacia(convocatoriaSel)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos una convocatoria", ""));
			return "";
		}

		if (esCadenaVacia(deptos)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos un departamento", ""));
			return "";
		}

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String fechainicial = sdf.format(fechaInicio);
		String fechafinal = sdf.format(fechaFin);

		ReporteBirt r = new ReporteBirt();
		

		r.setNombreReporte("/reportes-proyectos/reporteProyectoCoordinacionV2");
		r.adicionarParametro("mostrarHermes", String.valueOf(columns[0]));
		r.adicionarParametro("mostrarQuipu", String.valueOf(columns[1]));
		r.adicionarParametro("mostrarTipo", String.valueOf(columns[2]));
		r.adicionarParametro("mostrarNombre", String.valueOf(columns[3]));
		r.adicionarParametro("mostrarDuracion", String.valueOf(columns[4]));
		r.adicionarParametro("mostrarEstadoActual", String.valueOf(columns[5]));
		r.adicionarParametro("lugEjecPry", String.valueOf(columns[6]));
		r.adicionarParametro("mostrarObjetivo", String.valueOf(columns[7]));
		r.adicionarParametro("mostrarAnoConvocatoria", String.valueOf(columns[8]));
		r.adicionarParametro("mostrarTipoConvocatoria", String.valueOf(columns[9]));
		r.adicionarParametro("mostrarConvocatoria", String.valueOf(columns[10]));
		r.adicionarParametro("mostrarModalidad", String.valueOf(columns[11]));
		r.adicionarParametro("mostrarInvestigador", String.valueOf(columns[12]));
		r.adicionarParametro("mostrarFacultad", String.valueOf(columns[13]));
		r.adicionarParametro("mostrarSede", String.valueOf(columns[14]));
		r.adicionarParametro("mostrarFechaPropuesto", String.valueOf(columns[15]));
		r.adicionarParametro("mostrarFechaInicio", String.valueOf(columns[16]));
		r.adicionarParametro("mostrarFechaFin", String.valueOf(columns[17]));
		r.adicionarParametro("mostrarFechaFinProrroga", String.valueOf(columns[18]));
		r.adicionarParametro("mostrarValorPersonal", String.valueOf(columns[19]));
		r.adicionarParametro("mostrarVrInterno", String.valueOf(columns[20]));
		r.adicionarParametro("mostrarVrExterno", String.valueOf(columns[21]));
		r.adicionarParametro("mostrarFechaAprob", String.valueOf(columns[22]));
		r.adicionarParametro("mostrarVlrEjecutado", String.valueOf(columns[23]));
		r.adicionarParametro("mostrarContrapartidaUN", String.valueOf(columns[24]));
		r.adicionarParametro("mostrarCambioFinalizado", String.valueOf(columns[25]));
		r.adicionarParametro("mostrarCoord", String.valueOf(columns[26]));
		r.adicionarParametro("SedeACTIVARSE", String.valueOf(columns[27]));
		r.adicionarParametro("OtraSede", String.valueOf(columns[28]));
		r.adicionarParametro("MostarODSPRINCIPAL", String.valueOf(columns[29]));
		r.adicionarParametro("mostrarFechaInfF", String.valueOf(columns[30]));
		r.adicionarParametro("mostrarAdicionP", String.valueOf(columns[31]));
		r.adicionarParametro("verRes", String.valueOf(columns[32]));
		r.adicionarParametro("verOs", String.valueOf(columns[33]));
		r.adicionarParametro("verOcde", String.valueOf(columns[34]));
		r.adicionarParametro("convoca", convocatoriaSel);
		r.adicionarParametro("fechaDesde", fechainicial);
		r.adicionarParametro("fechaHasta", fechafinal);
		r.adicionarParametro("estados", estados);
		r.adicionarParametro("deptos", deptos);
		r.adicionarParametro("sedId", sed);
		r.adicionarParametro("facultad", facultad);
		r.adicionarParametro("jd", "N");
		if (isEsReporteJornadaDocente()) {
			r.adicionarParametro("jd", "Y");
		}
		r.adicionarParametro("actual", String.valueOf(false));
		r.adicionarParametro("sede", String.valueOf(false));
		r.adicionarParametro("infoAdic", String.valueOf(infoAdicionalReporteProyCoord));
		r.setFormato(ReporteBirt.FORMATO_XLS);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}

	public void convocatoriasSeleccionadas() {
		if (isEsReporteJornadaDocente()) {
			convocatoriaSel = "0 11";
		} else {
			convocatoriaSel = "";
			if (selectedConvocatorias.length != convocatoriaItem.length) {
				for (String convoca : selectedConvocatorias) {
					convocatoriaSel = convocatoriaSel + " " + convoca;
				}
			} else {
				convocatoriaSel = "ALL";
			}
		}
	}

	public void deptosSeleccionados() {
		deptos = "";
		for (String depto : selectedDeptos) {
			deptos = deptos + " " + depto;
		}
		if ("".equals(deptos) && "0".equals(facultad)) {
			deptos="ALL";
		}
	}

	public void deptosSeleccionadosLeg() {
		deptosLeg = "";
		for (String depto : selectedDeptosLeg) {
			deptosLeg = deptosLeg + " " + depto;
		}

		if ("".equals(deptosLeg) && "0".equals(facultadLeg)) {
			deptosLeg = "ALL";
		}
	}
	
	public void deptosSeleccionadosReg() {
		deptosReg = "";
		for (String depto : selectedDeptosReg) {
			deptosReg = deptosReg + " " + depto;
		}

		if ("".equals(deptosReg) && "0".equals(facultadReg)) {
			deptosReg = "ALL";
		}
	}

	public void estadosSeleccionados() {
		estados = "";
		if (selectedEstados.length != listaEstadosProyecto.size()) {
			for (String valor : selectedEstados) {
				estados = estados + " " + valor;
			}
		} else {
			estados = "ALL";
		}
	}

	public String imprimirReporteHistoricoSede() {
		personaActual = (Persona) sesion.getAttribute("persona");

		if (this.fechaInicio != null && this.fechaFin != null) {
			if (!this.fechaInicio.before(this.fechaFin)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"La fecha Final debe ser posterior a la de Inicio", ""));
				return "";
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe indicar el rango de fechas del reporte", ""));
			return "";
		}

		int totalFalse = 0;
		for (int a = 0; a < columns.length; a++) {
			if (columns[a].equals(false)) {
				totalFalse++;
			}
		}

		if (totalFalse == columns.length) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar al menos un campo para el reporte", ""));
			return "";
		}

		estadosSeleccionados();
		String sed;
		if (isEsReporteJornadaDocente()) {
			sed = sede;
		} else {
			sed = cargarSedes().substring(1).replace(",", " ");
		}
		convocatoriasSeleccionadas();
		deptosSeleccionados();

		if (esCadenaVacia(estados)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar al menos un estado de proyecto", ""));
			return "";
		}

		if (esCadenaVacia(sed)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos una sede", ""));
			return "";
		}

		if (esCadenaVacia(convocatoriaSel)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos una convocatoria", ""));
			return "";
		}

		if (esCadenaVacia(deptos)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos un departamento", ""));
			return "";
		}

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String fechainicial = sdf.format(fechaInicio);
		String fechafinal = sdf.format(fechaFin);

		ReporteBirt r = new ReporteBirt();

		r.setNombreReporte("/reportes-proyectos/reporteProyectoCoordinacionV2");
		r.adicionarParametro("mostrarHermes", String.valueOf(columns[0]));
		r.adicionarParametro("mostrarQuipu", String.valueOf(columns[1]));
		r.adicionarParametro("mostrarTipo", String.valueOf(columns[2]));
		r.adicionarParametro("mostrarNombre", String.valueOf(columns[3]));
		r.adicionarParametro("mostrarDuracion", String.valueOf(columns[4]));
		r.adicionarParametro("mostrarEstadoActual", String.valueOf(columns[5]));
		r.adicionarParametro("lugEjecPry", String.valueOf(columns[6]));
		r.adicionarParametro("mostrarObjetivo", String.valueOf(columns[7]));
		r.adicionarParametro("mostrarAnoConvocatoria", String.valueOf(columns[8]));
		r.adicionarParametro("mostrarTipoConvocatoria", String.valueOf(columns[9]));
		r.adicionarParametro("mostrarConvocatoria", String.valueOf(columns[10]));
		r.adicionarParametro("mostrarModalidad", String.valueOf(columns[11]));
		r.adicionarParametro("mostrarInvestigador", String.valueOf(columns[12]));
		r.adicionarParametro("mostrarFacultad", String.valueOf(columns[13]));
		r.adicionarParametro("mostrarSede", String.valueOf(columns[14]));
		r.adicionarParametro("mostrarFechaPropuesto", String.valueOf(columns[15]));
		r.adicionarParametro("mostrarFechaInicio", String.valueOf(columns[16]));
		r.adicionarParametro("mostrarFechaFin", String.valueOf(columns[17]));
		r.adicionarParametro("mostrarFechaFinProrroga", String.valueOf(columns[18]));
		r.adicionarParametro("mostrarValorPersonal", String.valueOf(columns[19]));
		r.adicionarParametro("mostrarVrInterno", String.valueOf(columns[20]));
		r.adicionarParametro("mostrarVrExterno", String.valueOf(columns[21]));
		r.adicionarParametro("mostrarFechaAprob", String.valueOf(columns[22]));
		r.adicionarParametro("mostrarVlrEjecutado", String.valueOf(columns[23]));
		r.adicionarParametro("mostrarContrapartidaUN", String.valueOf(columns[24]));
		r.adicionarParametro("mostrarCambioFinalizado", String.valueOf(columns[25]));
		r.adicionarParametro("mostrarCoord", String.valueOf(columns[26]));
		r.adicionarParametro("SedeACTIVARSE", String.valueOf(columns[27]));
		r.adicionarParametro("OtraSede", String.valueOf(columns[28]));
		r.adicionarParametro("MostarODSPRINCIPAL", String.valueOf(columns[29]));
		r.adicionarParametro("mostrarFechaInfF", String.valueOf(columns[30]));
		r.adicionarParametro("mostrarAdicionP", String.valueOf(columns[31]));
		r.adicionarParametro("verRes", String.valueOf(columns[32]));
		r.adicionarParametro("verOs", String.valueOf(columns[33]));
		r.adicionarParametro("verOcde", String.valueOf(columns[34]));
		r.adicionarParametro("convoca", convocatoriaSel);
		r.adicionarParametro("fechaDesde", fechainicial);
		r.adicionarParametro("fechaHasta", fechafinal);
		r.adicionarParametro("estados", estados);
		r.adicionarParametro("deptos", deptos);
		r.adicionarParametro("sedId", sed);
		r.adicionarParametro("jd", "N");
		if (isEsReporteJornadaDocente()) {
			r.adicionarParametro("jd", "Y");
		}
		r.adicionarParametro("actual", String.valueOf(false));
		r.adicionarParametro("sede", String.valueOf(true));
		r.adicionarParametro("infoAdic", String.valueOf(infoAdicionalReporteProyCoord));
		r.setFormato(ReporteBirt.FORMATO_XLS);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}

	public String imprimirReporte() {
		personaActual = (Persona) sesion.getAttribute("persona");

		int totalFalse = 0;
		for (int a = 0; a < columns.length; a++) {
			if (columns[a].equals(false)) {
				totalFalse++;
			}
		}

		if (totalFalse == columns.length) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar al menos un campo para el reporte", ""));
			return "";
		}

		estadosSeleccionados();
		String sed;
		if (isEsReporteJornadaDocente()) {
			sed = sede;
		} else {
			sed = cargarSedes().substring(1).replace(",", " ");
		}
		convocatoriasSeleccionadas();
		deptosSeleccionados();

		if (esCadenaVacia(estados)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar al menos un estado de proyecto", ""));
			return "";
		}

		if (esCadenaVacia(sed)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos una sede", ""));
			return "";
		}

		if (esCadenaVacia(convocatoriaSel)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos una convocatoria", ""));
			return "";
		}

		if (esCadenaVacia(deptos)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos un departamento", ""));
			return "";
		}

		ReporteBirt r = new ReporteBirt();

		r.setNombreReporte("/reportes-proyectos/reporteProyectoCoordinacionV2");
		r.adicionarParametro("mostrarHermes", String.valueOf(columns[0]));
		r.adicionarParametro("mostrarQuipu", String.valueOf(columns[1]));
		r.adicionarParametro("mostrarTipo", String.valueOf(columns[2]));
		r.adicionarParametro("mostrarNombre", String.valueOf(columns[3]));
		r.adicionarParametro("mostrarDuracion", String.valueOf(columns[4]));
		r.adicionarParametro("mostrarEstadoActual", String.valueOf(columns[5]));
		r.adicionarParametro("lugEjecPry", String.valueOf(columns[6]));
		r.adicionarParametro("mostrarObjetivo", String.valueOf(columns[7]));
		r.adicionarParametro("mostrarAnoConvocatoria", String.valueOf(columns[8]));
		r.adicionarParametro("mostrarTipoConvocatoria", String.valueOf(columns[9]));
		r.adicionarParametro("mostrarConvocatoria", String.valueOf(columns[10]));
		r.adicionarParametro("mostrarModalidad", String.valueOf(columns[11]));
		r.adicionarParametro("mostrarInvestigador", String.valueOf(columns[12]));
		r.adicionarParametro("mostrarFacultad", String.valueOf(columns[13]));
		r.adicionarParametro("mostrarSede", String.valueOf(columns[14]));
		r.adicionarParametro("mostrarFechaPropuesto", String.valueOf(columns[15]));
		r.adicionarParametro("mostrarFechaInicio", String.valueOf(columns[16]));
		r.adicionarParametro("mostrarFechaFin", String.valueOf(columns[17]));
		r.adicionarParametro("mostrarFechaFinProrroga", String.valueOf(columns[18]));
		r.adicionarParametro("mostrarValorPersonal", String.valueOf(columns[19]));
		r.adicionarParametro("mostrarVrInterno", String.valueOf(columns[20]));
		r.adicionarParametro("mostrarVrExterno", String.valueOf(columns[21]));
		r.adicionarParametro("mostrarFechaAprob", String.valueOf(columns[22]));
		r.adicionarParametro("mostrarVlrEjecutado", String.valueOf(columns[23]));
		r.adicionarParametro("mostrarContrapartidaUN", String.valueOf(columns[24]));
		r.adicionarParametro("mostrarCambioFinalizado", String.valueOf(columns[25]));
		r.adicionarParametro("mostrarCoord", String.valueOf(columns[26]));
		r.adicionarParametro("SedeACTIVARSE", String.valueOf(columns[27]));
		r.adicionarParametro("OtraSede", String.valueOf(columns[28]));
		r.adicionarParametro("MostarODSPRINCIPAL", String.valueOf(columns[29]));
		r.adicionarParametro("mostrarFechaInfF", String.valueOf(columns[30]));
		r.adicionarParametro("mostrarAdicionP", String.valueOf(columns[31]));
		r.adicionarParametro("verRes", String.valueOf(columns[32]));
		r.adicionarParametro("verOs", String.valueOf(columns[33]));
		r.adicionarParametro("verOcde", String.valueOf(columns[34]));
		r.adicionarParametro("convoca", convocatoriaSel);
		r.adicionarParametro("estados", estados);
		r.adicionarParametro("deptos", deptos);
		r.adicionarParametro("sedId", sed);
		r.adicionarParametro("facultad", facultad);
		r.adicionarParametro("jd", "N");
		if (isEsReporteJornadaDocente()) {
			r.adicionarParametro("jd", "Y");
		}
		r.adicionarParametro("actual", String.valueOf(true));
		r.adicionarParametro("sede", String.valueOf(false));
		r.adicionarParametro("infoAdic", String.valueOf(infoAdicionalReporteProyCoord));
		r.setFormato(ReporteBirt.FORMATO_XLS);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}

	public String imprimirReporteSede() {
		personaActual = (Persona) sesion.getAttribute("persona");

		int totalFalse = 0;
		for (int a = 0; a < columns.length; a++) {
			if (columns[a].equals(false)) {
				totalFalse++;
			}
		}

		if (totalFalse == columns.length) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar al menos un campo para el reporte", ""));
			return "";
		}

		estadosSeleccionados();
		String sed;
		if (isEsReporteJornadaDocente()) {
			sed = sede;
		} else {
			sed = cargarSedes().substring(1).replace(",", " ");
		}
		convocatoriasSeleccionadas();
		deptosSeleccionados();

		if (esCadenaVacia(estados)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar al menos un estado de proyecto", ""));
			return "";
		}
		if (esCadenaVacia(sed)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos una sede", ""));
			return "";
		}

		if (esCadenaVacia(convocatoriaSel)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos una convocatoria", ""));
			return "";
		}

		if (esCadenaVacia(deptos)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos un departamento", ""));
			return "";
		}

		ReporteBirt r = new ReporteBirt();

		r.setNombreReporte("/reportes-proyectos/reporteProyectoCoordinacionV2");
		r.adicionarParametro("mostrarHermes", String.valueOf(columns[0]));
		r.adicionarParametro("mostrarQuipu", String.valueOf(columns[1]));
		r.adicionarParametro("mostrarTipo", String.valueOf(columns[2]));
		r.adicionarParametro("mostrarNombre", String.valueOf(columns[3]));
		r.adicionarParametro("mostrarDuracion", String.valueOf(columns[4]));
		r.adicionarParametro("mostrarEstadoActual", String.valueOf(columns[5]));
		r.adicionarParametro("lugEjecPry", String.valueOf(columns[6]));
		r.adicionarParametro("mostrarObjetivo", String.valueOf(columns[7]));
		r.adicionarParametro("mostrarAnoConvocatoria", String.valueOf(columns[8]));
		r.adicionarParametro("mostrarTipoConvocatoria", String.valueOf(columns[9]));
		r.adicionarParametro("mostrarConvocatoria", String.valueOf(columns[10]));
		r.adicionarParametro("mostrarModalidad", String.valueOf(columns[11]));
		r.adicionarParametro("mostrarInvestigador", String.valueOf(columns[12]));
		r.adicionarParametro("mostrarFacultad", String.valueOf(columns[13]));
		r.adicionarParametro("mostrarSede", String.valueOf(columns[14]));
		r.adicionarParametro("mostrarFechaPropuesto", String.valueOf(columns[15]));
		r.adicionarParametro("mostrarFechaInicio", String.valueOf(columns[16]));
		r.adicionarParametro("mostrarFechaFin", String.valueOf(columns[17]));
		r.adicionarParametro("mostrarFechaFinProrroga", String.valueOf(columns[18]));
		r.adicionarParametro("mostrarValorPersonal", String.valueOf(columns[19]));
		r.adicionarParametro("mostrarVrInterno", String.valueOf(columns[20]));
		r.adicionarParametro("mostrarVrExterno", String.valueOf(columns[21]));
		r.adicionarParametro("mostrarFechaAprob", String.valueOf(columns[22]));
		r.adicionarParametro("mostrarVlrEjecutado", String.valueOf(columns[23]));
		r.adicionarParametro("mostrarContrapartidaUN", String.valueOf(columns[24]));
		r.adicionarParametro("mostrarCambioFinalizado", String.valueOf(columns[25]));
		r.adicionarParametro("mostrarCoord", String.valueOf(columns[26]));
		r.adicionarParametro("SedeACTIVARSE", String.valueOf(columns[27]));
		r.adicionarParametro("OtraSede", String.valueOf(columns[28]));
		r.adicionarParametro("MostarODSPRINCIPAL", String.valueOf(columns[29]));
		r.adicionarParametro("mostrarFechaInfF", String.valueOf(columns[30]));
		r.adicionarParametro("mostrarAdicionP", String.valueOf(columns[31]));
		r.adicionarParametro("verRes", String.valueOf(columns[32]));
		r.adicionarParametro("verOs", String.valueOf(columns[33]));
		r.adicionarParametro("verOcde", String.valueOf(columns[34]));
		r.adicionarParametro("convoca", convocatoriaSel);
		r.adicionarParametro("estados", estados);
		r.adicionarParametro("deptos", deptos);
		r.adicionarParametro("sedId", sed);
		r.adicionarParametro("jd", "N");
		if (isEsReporteJornadaDocente()) {
			r.adicionarParametro("jd", "Y");
		}
		r.adicionarParametro("actual", String.valueOf(true));
		r.adicionarParametro("sede", String.valueOf(true));
		r.adicionarParametro("infoAdic", String.valueOf(infoAdicionalReporteProyCoord));
		r.setFormato(ReporteBirt.FORMATO_XLS);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}

	// Legalizac
	public void verPanelFechas() {
		if (reporteHistoricoLeg.equals("Si")) {
			mostrarPanelFechas = true;
		} else {
			mostrarPanelFechas = false;
		}

	}

	public void cambiarSede() {
		System.out.println("-----------*****************=== cambiar sede=== " + sedeLeg);
		if (sedeLeg.equals("1") || sedeLeg.equals("6") || sedeLeg.equals("7") || sedeLeg.equals("8")
				|| sedeLeg.equals("9")) {
			setMostrarFacultades(false);
		} else {
			setMostrarFacultades(true);
			// Facultades
			facultadesUN = new ArrayList<Dependencia>();
			facultadesUN = servicioGeneral.obtenerObjetos("select e from Dependencia e where e.sede.id = '" + sedeLeg
					+ "' and e.esFacultad = 'Y' and e.estado = 'A' order by e.nombre");
			facultadItem = new SelectItem[facultadesUN.size()];
			for (int i = 0; i < facultadesUN.size(); i++) {
				Dependencia dd = (Dependencia) facultadesUN.get(i);
				facultadItem[i] = new SelectItem(dd.getId(), dd.getNombre());
				dd = null;
			}

		}

	}

	public String imprimirReportePryExternos() {
		personaActual = (Persona) sesion.getAttribute("persona");
		ReporteBirt r = new ReporteBirt();
		boolean band = true;

		if (reporteHistoricoLeg.equals("Si")) {
			if (this.fechaInicioLeg != null && this.fechaFinLeg != null) {
				if (!this.fechaInicioLeg.before(this.fechaFinLeg)) {
					band = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"La fecha Final debe ser posterior a la de Inicio", ""));
					return "";
				}
			} else {
				band = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe indicar el rango de fechas del reporte", ""));
				return "";
			}

		}

		int totalFalse = 0;
		for (int a = 0; a < columnsLeg.length; a++) {
			if (columnsLeg[a] == null) {
				columnsLeg[a] = false;
			}
			if (columnsLeg[a].equals(false)) {
				totalFalse++;
			}
		}

		String sed;
		sed = cargarSedesLeg().substring(1).replace(",", " ");
		deptosSeleccionadosLeg();
		if (esCadenaVacia(sed)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos una sede", ""));
			return "";
		}
		if (esCadenaVacia(deptosLeg)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos un departamento", ""));
			return "";
		}
		if (totalFalse == columnsLeg.length) {
			band = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar al menos un campo para el reporte", ""));
			return "";

		}

		if (band) {

			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			Date fechaI = new Date();
			String fechaIni = sdf.format(fechaI);
			String fechainicial;
			String fechafinal;

			if (fechaInicioLeg != null) {
				fechainicial = sdf.format(fechaInicioLeg);
			} else {
				fechainicial = "2015-10-01";
			}

			if (fechaFinLeg != null) {
				fechafinal = sdf.format(fechaFinLeg);
			} else {
				fechafinal = fechaIni;
			}
			r.setNombreReporte("/reportes-proyectos/reporteProyectoExtCoordinacionV2");
			r.adicionarParametro("fechaI", fechainicial);
			r.adicionarParametro("fechaF", fechafinal);
			r.adicionarParametro("sedId", sed);
			r.adicionarParametro("deptos", deptosLeg);
			r.adicionarParametro("IDHERMES", String.valueOf(columnsLeg[0]));
			r.adicionarParametro("CodigoQUIPU", String.valueOf(columnsLeg[1]));
			r.adicionarParametro("Tipo", String.valueOf(columnsLeg[2]));
			r.adicionarParametro("NombrePry", String.valueOf(columnsLeg[3]));
			r.adicionarParametro("EstadoPry", String.valueOf(columnsLeg[4]));
			r.adicionarParametro("lugEjecPry", String.valueOf(columnsLeg[5]));
			r.adicionarParametro("InvPrin", String.valueOf(columnsLeg[6]));
			r.adicionarParametro("Sede", String.valueOf(columnsLeg[7]));
			r.adicionarParametro("Facultad", String.valueOf(columnsLeg[8]));
			r.adicionarParametro("FechaInicio", String.valueOf(columnsLeg[9]));
			r.adicionarParametro("TFechaInicio", String.valueOf(columnsLeg[10]));
			r.adicionarParametro("FechaFin", String.valueOf(columnsLeg[11]));
			r.adicionarParametro("ActAdmEE", String.valueOf(columnsLeg[12]));
			r.adicionarParametro("ActAdmUN", String.valueOf(columnsLeg[13]));
			r.adicionarParametro("DepEjec", String.valueOf(columnsLeg[14]));
			r.adicionarParametro("EmprEjec", String.valueOf(columnsLeg[15]));
			r.adicionarParametro("CPEfect", String.valueOf(columnsLeg[16]));
			r.adicionarParametro("CPEsp", String.valueOf(columnsLeg[17]));
			r.adicionarParametro("SupCont", String.valueOf(columnsLeg[18]));
			r.adicionarParametro("Asistentes", String.valueOf(columnsLeg[19]));
			r.adicionarParametro("Regiones", String.valueOf(columnsLeg[20]));
			r.adicionarParametro("Fase", String.valueOf(columnsLeg[21]));
			r.adicionarParametro("MostarODSPRINCIPAL", String.valueOf(columnsLeg[22]));
			r.adicionarParametro("mostrarFechaInfF", String.valueOf(columnsLeg[23]));
			r.adicionarParametro("InfoAdic", String.valueOf(infoAdicionalReporteProyExt));
			r.setFormato(ReporteBirt.FORMATO_XLS);
			sesion.setAttribute("reporte", r);
			FacesContext context = FacesContext.getCurrentInstance();
			try {
				context.getExternalContext().dispatch("/ReporteEngineServlet");
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				context.responseComplete();
			}

		}

		return "";
	}

	// fin legalizac
	
	//pry regalias
	
	public String imprimirReportePryRegalias() {
		personaActual = (Persona) sesion.getAttribute("persona");
		ReporteBirt r = new ReporteBirt();
		boolean band = true;

		int totalFalse = 0;
		for (int a = 0; a < columnsReg.length; a++) {
			if (columnsReg[a] == null) {
				columnsReg[a] = false;
			}
			if (columnsReg[a].equals(false)) {
				totalFalse++;
			}
		}

		String sed;
		
		if (esCadenaVacia(cargarSedesReg())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos una sede", ""));
			return "";
		}else {		
			sed = cargarSedesReg().substring(1).replace(",", " ");
			deptosSeleccionadosReg();
			
		}
		if (esCadenaVacia(deptosReg)) {
	
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos un departamento", ""));
			return "";
		}
		
		if (totalFalse == columnsReg.length) {
			band = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar al menos un campo para el reporte", ""));
			return "";

		}

		if (band) {

			r.setNombreReporte("/reportes-proyectos/reporteProyectoRegCoordinador");
			r.adicionarParametro("sedId", sed);
			r.adicionarParametro("deptos", deptosReg);
			r.adicionarParametro("IDHERMES", String.valueOf(columnsReg[0]));
			r.adicionarParametro("NombrePry", String.valueOf(columnsReg[1]));
			r.adicionarParametro("duracion", String.valueOf(columnsReg[2]));
			r.adicionarParametro("InvPrin", String.valueOf(columnsReg[3]));
			r.adicionarParametro("EstadoPry", String.valueOf(columnsReg[4]));
			r.adicionarParametro("Facultad", String.valueOf(columnsReg[5]));
			r.adicionarParametro("Sede", String.valueOf(columnsReg[6]));
			r.adicionarParametro("rol", String.valueOf(columnsReg[7]));
			r.adicionarParametro("sigp", String.valueOf(columnsReg[8]));
			r.adicionarParametro("bpin", String.valueOf(columnsReg[9]));
			r.adicionarParametro("nrocad", String.valueOf(columnsReg[10]));
			r.adicionarParametro("fechaA", String.valueOf(columnsReg[11]));
			r.adicionarParametro("director", String.valueOf(columnsReg[12]));
			r.adicionarParametro("supervisor", String.valueOf(columnsReg[13]));
			r.adicionarParametro("ocad", String.valueOf(columnsReg[14]));
			r.adicionarParametro("ejecutora", String.valueOf(columnsReg[15]));
			r.adicionarParametro("extension", String.valueOf(columnsReg[16]));
			r.adicionarParametro("valorsgr", String.valueOf(columnsReg[17]));
			r.adicionarParametro("valorotras", String.valueOf(columnsReg[18]));
			r.adicionarParametro("valorpart", String.valueOf(columnsReg[19]));
			r.adicionarParametro("InfoAdic", String.valueOf(infoAdicionalReporteProyReg));
			r.setFormato(ReporteBirt.FORMATO_XLS);
			sesion.setAttribute("reporte", r);
			FacesContext context = FacesContext.getCurrentInstance();
			try {
				context.getExternalContext().dispatch("/ReporteEngineServlet");
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				context.responseComplete();
			}
		}

		return "";
	}
	
	//pry regalias

	private void verificarNivelUsuario() {
		if (validarSiNacional(funcionarioActual.getDependencia())) {
			esNacional = true;
			esSede = false;
			esFacultad = false;
		}
		if (!esNacional && validarSiSede(funcionarioActual.getDependencia())) {
			esNacional = false;
			esSede = true;
			esFacultad = false;
		}
		if (!esNacional && !esSede && validarSiFacultad(funcionarioActual.getDependencia())) {
			esNacional = false;
			esSede = false;
			esFacultad = true;
		}
	}

	private void listaSedes() {
		if (esNacional) {
			listaSedes = servicioGeneral.obtenerSedes();
		}
		if (esSede || esFacultad) {
			listaSedes = servicioGeneral.obtenerObjetoXID(Dependencia.class,
					funcionarioActual.getDependencia().getSede().getId().toString());
		}
		listaSedesItem = new SelectItem[listaSedes.size()];
		listaSedesItemLeg = new SelectItem[listaSedes.size()];
		listaSedesItemReg = new SelectItem[listaSedes.size()];
		for (int i = 0; i < listaSedes.size(); i++) {
			Dependencia sede = (Dependencia) listaSedes.get(i);
			listaSedesItem[i] = new SelectItem(sede.getId(), sede.getNombre().trim());
		}
		cargarDependencias();
		cargarFacultades();
		listaSedesItemLeg = listaSedesItem;
		listaSedesItemReg = listaSedesItem;
		cargarDependenciasLeg();
		cargarFacultadesLeg();
		cargarDependenciasReg();
		cargarFacultadesReg();

	}

	public void listaConvocatorias() {

		listadoConvocatoriaPadre = new ArrayList<ConvocatoriaPadre>();

		String sql = "select distinct cp from ConvocatoriaPadre cp, Convocatoria c where cp.id = c.padre.id and c.id in "
				+ "(select distinct p.modalidad.id from Proyecto p) and cp.estadoConvocatoria.id not in ('O','CR') and "
				+ "cp.id <> '257' and " // Becas doctorados colciencias
				+ "cp.id <> '248' and " // Registro de informes beca doctorados
				+ "cp.id <> '251' and " // Digitalizacion de libros
				+ "cp.id <> '11' and " // Registro unico de proyectos
				+ "cp.id <> '310' and " // eSCUELA DE PENSAMIENTO UNIVERSITARIO
				+ "cp.id <> '205' and " // Registro de idea apoyo regalias
				+ "cp.id <> '17' and " // Proyectos aprobados externos
				+ "cp.id <> '99' and " // jOVENES INVESTIGADORES ATLANTICO
				+ "cp.id <> '210' and " // USO DE EQUIPOS DE LABORATORIOS
				+ "cp.id <> '313' and " // PROYECTO EN CUNDINAMARCA
				+ "cp.id <> '202' and " // DOTACION DE EQUIPOS DE LABORATORIOS
				+ "c.id not in (" + MODALIDADES_PERMISO_CONTRATO + ") and " // REGISTROS
																			// DE
																			// BIODIVERSIDAD
				+ "cp.id <> '2' and " // Registro unico de proyectos
				+ "cp.id <> '0' " // Jornada docente
				+ "order by cp.titulo asc";

		listadoConvocatoriaPadre = (ArrayList<ConvocatoriaPadre>) servicioGeneral
				.obtenerObjetos(ConvocatoriaPadre.class, sql);
		if (!esListaVacia(listadoConvocatoriaPadre)) {
			convocatoriaItem = new SelectItem[listadoConvocatoriaPadre.size()];
			for (int i = 0; i < listadoConvocatoriaPadre.size(); i++) {
				ConvocatoriaPadre dd = (ConvocatoriaPadre) listadoConvocatoriaPadre.get(i);
				convocatoriaItem[i] = new SelectItem(dd.getId().toString(), dd.getTitulo().substring(0, 1).toUpperCase()
						+ dd.getTitulo().substring(1, dd.getTitulo().length()).toLowerCase());
			}
		}
	}

	public void cargarDependencias() {
		try {
			if (isEsReporteJornadaDocente()) {
				selectedSedes = null;
			} else if (selectedSedes.length == 0) {
				selectedSedes = new String[] { sede };
			}
		} catch (Exception e) {
			selectedSedes = new String[] { sede };
		}
		if (selectedSedes == null) {
			listaDependencias = servicioGeneral.obtenerListaObjetos(
					"Dependencia d where d.sede.id = " + sede + " AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		} else {
			listaDependencias = servicioGeneral.obtenerListaObjetos("Dependencia d where d.sede.id in ("
					+ cargarSedes().substring(1) + ") AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.sede.nombre, d.nombre");
		}
		if (!esListaVacia(listaDependencias)) {
			listaDependenciasItem = new SelectItem[listaDependencias.size() + 1];
			listaDependenciasItem[0] = new SelectItem("0", "Todas");
			for (int i = 1; i < listaDependencias.size() + 1; i++) {
				Dependencia dep = (Dependencia) listaDependencias.get(i - 1);
				listaDependenciasItem[i] = new SelectItem(dep.getId(), dep.getNombre());
			}
		}
	}

	public void cargarDependenciasLeg() {

		try {
			if (selectedSedesLeg.length == 0) {
				selectedSedesLeg = new String[] { sedeLeg };
			}
		} catch (Exception e) {
			selectedSedesLeg = new String[] { sedeLeg };
		}
		if (selectedSedesLeg == null) {
			listaDependenciasLeg = servicioGeneral.obtenerListaObjetos(
					"Dependencia d where d.sede.id = " + sedeLeg + " AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		} else {
			listaDependenciasLeg = servicioGeneral.obtenerListaObjetos("Dependencia d where d.sede.id in ("
					+ cargarSedesLeg().substring(1) + ") AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.sede.nombre, d.nombre");
		}
		if (!esListaVacia(listaDependenciasLeg)) {
			listaDependenciasItemLeg = new SelectItem[listaDependenciasLeg.size() + 1];
			listaDependenciasItemLeg[0] = new SelectItem("0", "Todas");
			for (int i = 1; i < listaDependenciasLeg.size() + 1; i++) {
				Dependencia dep = (Dependencia) listaDependenciasLeg.get(i - 1);
				listaDependenciasItemLeg[i] = new SelectItem(dep.getId(), dep.getNombre());
			}
		}
	}
	
	public void cargarDependenciasReg() {

		try {
			if (selectedSedesReg.length == 0) {
				selectedSedesReg = new String[] { sedeReg };
			}
		} catch (Exception e) {
			selectedSedesReg = new String[] { sedeReg };
		}
		if (selectedSedesReg == null) {
			listaDependenciasReg = servicioGeneral.obtenerListaObjetos(
					"Dependencia d where d.sede.id = " + sedeReg + " AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		} else {
			listaDependenciasReg = servicioGeneral.obtenerListaObjetos("Dependencia d where d.sede.id in ("
					+ cargarSedesReg().substring(1) + ") AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.sede.nombre, d.nombre");
		}
		if (!esListaVacia(listaDependenciasReg)) {
			listaDependenciasItemReg = new SelectItem[listaDependenciasReg.size() + 1];
			listaDependenciasItemReg[0] = new SelectItem("0", "Todas");
			for (int i = 1; i < listaDependenciasReg.size() + 1; i++) {
				Dependencia dep = (Dependencia) listaDependenciasReg.get(i - 1);
				listaDependenciasItemReg[i] = new SelectItem(dep.getId(), dep.getNombre());
			}
		}
	}

	private String cargarSedes() {
		String s = "";
		for (String data : selectedSedes) {
			s += "," + data;
		}
		return s;
	}

	private String cargarSedesLeg() {
		String s = "";
		for (String data : selectedSedesLeg) {
			s += "," + data;
		}
		return s;
	}
	
	private String cargarSedesReg() {
		String s = "";
		for (String data : selectedSedesReg) {
			s += "," + data;
		}
		return s;
	}

	public void cargarFacultades() {
		if (selectedSedes == null || selectedSedes.length == 0) {
			listaDependencias = servicioGeneral.obtenerListaObjetos(
					"Dependencia d where d.sede.id = " + sede + " AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		} else {
			listaDependencias = servicioGeneral.obtenerListaObjetos("Dependencia d where d.sede.id in ("
					+ cargarSedes().substring(1) + ") AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		}
		if (!esListaVacia(listaDependencias)) {
			listaFacultadesItem = new SelectItem[listaDependencias.size()];
			for (int i = 0; i < listaDependencias.size(); i++) {
				Dependencia dep = (Dependencia) listaDependencias.get(i);
				listaFacultadesItem[i] = new SelectItem(dep.getId(), dep.getNombre());
			}
		}
	}

	public void cargarFacultadesLeg() {
		if (selectedSedesLeg == null || selectedSedesLeg.length == 0) {
			listaDependenciasLeg = servicioGeneral.obtenerListaObjetos(
					"Dependencia d where d.sede.id = " + sedeLeg + " AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		} else {
			listaDependenciasLeg = servicioGeneral.obtenerListaObjetos("Dependencia d where d.sede.id in ("
					+ cargarSedesLeg().substring(1) + ") AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		}
		if (!esListaVacia(listaDependenciasLeg)) {
			listaFacultadesItemLeg = new SelectItem[listaDependenciasLeg.size()];
			for (int i = 0; i < listaDependenciasLeg.size(); i++) {
				Dependencia dep = (Dependencia) listaDependenciasLeg.get(i);
				listaFacultadesItemLeg[i] = new SelectItem(dep.getId(), dep.getNombre());
			}
		}
	}
	
	public void cargarFacultadesReg() {
		if (selectedSedesReg == null || selectedSedesReg.length == 0) {
			listaDependenciasReg = servicioGeneral.obtenerListaObjetos(
					"Dependencia d where d.sede.id = " + sedeReg + " AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		} else {
			listaDependenciasReg = servicioGeneral.obtenerListaObjetos("Dependencia d where d.sede.id in ("
					+ cargarSedesReg().substring(1) + ") AND d.esFacultad = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		}
		if (!esListaVacia(listaDependenciasReg)) {
			listaFacultadesItemReg = new SelectItem[listaDependenciasReg.size()];
			for (int i = 0; i < listaDependenciasReg.size(); i++) {
				Dependencia dep = (Dependencia) listaDependenciasReg.get(i);
				listaFacultadesItemReg[i] = new SelectItem(dep.getId(), dep.getNombre());
			}
		}
	}

	public void cargarDeptos() {
		if (selectedSedes == null || selectedSedes.length == 0) {
			listaDeptos = servicioGeneral.obtenerListaObjetos("Dependencia d where d.sede.id = " + sede
					+ " and d.facultad.id = " + facultad + " AND d.esDepartamento = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		} else {
			listaDeptos = servicioGeneral
					.obtenerListaObjetos("Dependencia d where d.sede.id in (" + cargarSedes().substring(1)
							+ ") and d.facultad.id = '" + facultad + "' AND d.esDepartamento = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		}
		if (!esListaVacia(listaDeptos)) {
			listaDeptosItem = new SelectItem[listaDeptos.size()];
			for (int i = 0; i < listaDeptos.size(); i++) {
				Dependencia dep = (Dependencia) listaDeptos.get(i);
				listaDeptosItem[i] = new SelectItem(dep.getId(), dep.getNombre());
			}
		} else {
			listaDeptosItem = new SelectItem[0];
		}
	}

	public void cargarDeptosLeg() {
		if (selectedSedesLeg == null || selectedSedesLeg.length == 0) {
			listaDeptosLeg = servicioGeneral.obtenerListaObjetos("Dependencia d where d.sede.id = " + sede
					+ " and d.facultad.id = " + facultadLeg + " AND d.esDepartamento = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		} else {
			listaDeptosLeg = servicioGeneral.obtenerListaObjetos(
					"Dependencia d where d.sede.id in (" + cargarSedesLeg().substring(1) + ") and d.facultad.id = '"
							+ facultadLeg + "' AND d.esDepartamento = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		}
		if (!esListaVacia(listaDeptosLeg)) {
			listaDeptosItemLeg = new SelectItem[listaDeptosLeg.size()];
			for (int i = 0; i < listaDeptosLeg.size(); i++) {
				Dependencia dep = (Dependencia) listaDeptosLeg.get(i);
				listaDeptosItemLeg[i] = new SelectItem(dep.getId(), dep.getNombre());
			}
		} else {
			listaDeptosItemLeg = new SelectItem[0];
		}
	}
	
	public void cargarDeptosReg() {
		if (selectedSedesReg == null || selectedSedesReg.length == 0) {
			listaDeptosReg = servicioGeneral.obtenerListaObjetos("Dependencia d where d.sede.id = " + sede
					+ " and d.facultad.id = " + facultadReg + " AND d.esDepartamento = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		} else {
			listaDeptosReg = servicioGeneral.obtenerListaObjetos(
					"Dependencia d where d.sede.id in (" + cargarSedesLeg().substring(1) + ") and d.facultad.id = '"
							+ facultadReg + "' AND d.esDepartamento = 'Y' AND d.estado = 'A' ORDER BY d.nombre");
		}
		if (!esListaVacia(listaDeptosReg)) {
			listaDeptosItemReg = new SelectItem[listaDeptosReg.size()];
			for (int i = 0; i < listaDeptosReg.size(); i++) {
				Dependencia dep = (Dependencia) listaDeptosReg.get(i);
				listaDeptosItemReg[i] = new SelectItem(dep.getId(), dep.getNombre());
			}
		} else {
			listaDeptosItemReg = new SelectItem[0];
		}
	}

	public String cargarProyecto() {
		if (proyectoSeleccionado != null) {
			sesion.setAttribute("idProyectoSeguimientoCoordinador", proyectoSeleccionado);
			return "informacionProyecto";
		}
		return "";
	}

	public void seleccionarTodoLegalizacion() {

		for (int i = 0; i < columnsLeg.length; i++) {
			columnsLeg[i] = true;
		}
	}

	public void desmarcarTodoLegalizacion() {

		for (int i = 0; i < columnsLeg.length; i++) {
			columnsLeg[i] = false;
		}
	}
	
	public void seleccionarTodoRegalias() {

		for (int i = 0; i < columnsReg.length; i++) {
			columnsReg[i] = true;
		}
	}

	public void desmarcarTodoRegalias() {

		for (int i = 0; i < columnsReg.length; i++) {
			columnsReg[i] = false;
		}
	}

	public void seleccionarTodoProyectos() {

		for (int i = 0; i < columns.length; i++) {
			columns[i] = true;
		}
	}

	public void desmarcarTodoProyectos() {

		for (int i = 0; i < columns.length; i++) {
			columns[i] = false;
		}
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getSelItem() {
		return selItem;
	}

	public void setSelItem(String selItem) {
		this.selItem = selItem;
	}

	public List getListaEstadosProyecto() {
		return listaEstadosProyecto;
	}

	public void setListaEstadosProyecto(List listaEstadosProyecto) {
		this.listaEstadosProyecto = listaEstadosProyecto;
	}

	public Boolean[] getColumns() {
		return columns;
	}

	public void setColumns(Boolean[] columns) {
		this.columns = columns;
	}

	public boolean isReporteHistorico() {
		return reporteHistorico;
	}

	public void setReporteHistorico(boolean reporteHistorico) {
		this.reporteHistorico = reporteHistorico;
	}

	public SelectItem[] getListaSedesItem() {
		return listaSedesItem;
	}

	public void setListaSedesItem(SelectItem[] listaSedesItem) {
		this.listaSedesItem = listaSedesItem;
	}

	public List<Dependencia> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(List<Dependencia> listaSedes) {
		this.listaSedes = listaSedes;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public SelectItem[] getListaDependenciasItem() {
		return listaDependenciasItem;
	}

	public void setListaDependenciasItem(SelectItem[] listaDependenciasItem) {
		this.listaDependenciasItem = listaDependenciasItem;
	}

	public List<Dependencia> getListaDependencias() {
		return listaDependencias;
	}

	public void setListaDependencias(List<Dependencia> listaDependencias) {
		this.listaDependencias = listaDependencias;
	}

	public String[] getSelectedEstados() {
		return selectedEstados;
	}

	public void setSelectedEstados(String[] selectedEstados) {
		this.selectedEstados = selectedEstados;
	}

	public List<ConvocatoriaPadre> getListadoConvocatoriaPadre() {
		return listadoConvocatoriaPadre;
	}

	public void setListadoConvocatoriaPadre(List<ConvocatoriaPadre> listadoConvocatoriaPadre) {
		this.listadoConvocatoriaPadre = listadoConvocatoriaPadre;
	}

	public List getListaConvocatorias() {
		return listaConvocatorias;
	}

	public void setListaConvocatorias(List listaConvocatorias) {
		this.listaConvocatorias = listaConvocatorias;
	}

	public String getConvocatoriaSel() {
		return convocatoriaSel;
	}

	public void setConvocatoriaSel(String convocatoriaSel) {
		this.convocatoriaSel = convocatoriaSel;
	}

	public SelectItem[] getConvocatoriaItem() {
		return convocatoriaItem;
	}

	public void setConvocatoriaItem(SelectItem[] convocatoriaItem) {
		this.convocatoriaItem = convocatoriaItem;
	}

	public String[] getSelectedConvocatorias() {
		return selectedConvocatorias;
	}

	public void setSelectedConvocatorias(String[] selectedConvocatorias) {
		this.selectedConvocatorias = selectedConvocatorias;
	}

	public String getEstados() {
		return estados;
	}

	public void setEstados(String estados) {
		this.estados = estados;
	}

	public SelectItem[] getListaDeptosItem() {
		return listaDeptosItem;
	}

	public void setListaDeptosItem(SelectItem[] listaDeptosItem) {
		this.listaDeptosItem = listaDeptosItem;
	}

	public List<Dependencia> getListaDeptos() {
		return listaDeptos;
	}

	public void setListaDeptos(List<Dependencia> listaDeptos) {
		this.listaDeptos = listaDeptos;
	}

	public String[] getSelectedDeptos() {
		return selectedDeptos;
	}

	public void setSelectedDeptos(String[] selectedDeptos) {
		this.selectedDeptos = selectedDeptos;
	}

	public String getDeptos() {
		return deptos;
	}

	public void setDeptos(String deptos) {
		this.deptos = deptos;
	}

	public String getSedeLeg() {
		return sedeLeg;
	}

	public void setSedeLeg(String sedeLeg) {
		this.sedeLeg = sedeLeg;
	}

	public String getFacultadLeg() {
		return facultadLeg;
	}

	public void setFacultadLeg(String facultadLeg) {
		this.facultadLeg = facultadLeg;
	}

	public String[] getSelectedEstadosLeg() {
		return selectedEstadosLeg;
	}

	public void setSelectedEstadosLeg(String[] selectedEstadosLeg) {
		this.selectedEstadosLeg = selectedEstadosLeg;
	}

	public Boolean[] getColumnsReg() {
		return columnsReg;
	}

	public void setColumnsReg(Boolean[] columnsReg) {
		this.columnsReg = columnsReg;
	}
	
	public Boolean[] getColumnsLeg() {
		return columnsLeg;
	}

	public void setColumnsLeg(Boolean[] columnsLeg) {
		this.columnsLeg = columnsLeg;
	}

	public String getReporteHistoricoLeg() {
		return reporteHistoricoLeg;
	}

	public void setReporteHistoricoLeg(String reporteHistoricoLeg) {
		this.reporteHistoricoLeg = reporteHistoricoLeg;
	}

	public Date getFechaInicioLeg() {
		return fechaInicioLeg;
	}

	public void setFechaInicioLeg(Date fechaInicioLeg) {
		this.fechaInicioLeg = fechaInicioLeg;
	}

	public Date getFechaFinLeg() {
		return fechaFinLeg;
	}

	public void setFechaFinLeg(Date fechaFinLeg) {
		this.fechaFinLeg = fechaFinLeg;
	}

	public List<Dependencia> getFacultadesUN() {
		return facultadesUN;
	}

	public void setFacultadesUN(List<Dependencia> facultadesUN) {
		this.facultadesUN = facultadesUN;
	}

	public SelectItem[] getFacultadItem() {
		return facultadItem;
	}

	public void setFacultadItem(SelectItem[] facultadItem) {
		this.facultadItem = facultadItem;
	}

	public String getFacultadSel() {
		return facultadSel;
	}

	public void setFacultadSel(String facultadSel) {
		this.facultadSel = facultadSel;
	}

	public boolean isMostrarFacultades() {
		return mostrarFacultades;
	}

	public void setMostrarFacultades(boolean mostrarFacultades) {
		this.mostrarFacultades = mostrarFacultades;
	}

	public boolean isMostrarPanelFechas() {
		return mostrarPanelFechas;
	}

	public void setMostrarPanelFechas(boolean mostrarPanelFechas) {
		this.mostrarPanelFechas = mostrarPanelFechas;
	}

	public List<Proyecto> getListaProyectosJornada() {
		return listaProyectosJornada;
	}

	public void setListaProyectosJornada(List<Proyecto> listaProyectosJornada) {
		this.listaProyectosJornada = listaProyectosJornada;
	}

	public int getTotalProyectosJornada() {
		return totalProyectosJornada;
	}

	public void setTotalProyectosJornada(int totalProyectosJornada) {
		this.totalProyectosJornada = totalProyectosJornada;
	}

	public String[] getSelectedFacultades() {
		return selectedFacultades;
	}

	public void setSelectedFacultades(String[] selectedFacultades) {
		this.selectedFacultades = selectedFacultades;
	}

	public List<Proyecto> getFilteredProyectos() {
		return filteredProyectos;
	}

	public void setFilteredProyectos(List<Proyecto> filteredProyectos) {
		this.filteredProyectos = filteredProyectos;
	}

	public String getFacultades() {
		return facultades;
	}

	public void setFacultades(String facultades) {
		this.facultades = facultades;
	}

	public SelectItem[] getListaFacultadesItem() {
		return listaFacultadesItem;
	}

	public void setListaFacultadesItem(SelectItem[] listaFacultadesItem) {
		this.listaFacultadesItem = listaFacultadesItem;
	}

	public Long getProyectoSeleccionado() {
		return proyectoSeleccionado;
	}

	public void setProyectoSeleccionado(Long proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}

	public InvestigadorInterno getFuncionarioActual() {
		return funcionarioActual;
	}

	public void setFuncionarioActual(InvestigadorInterno funcionarioActual) {
		this.funcionarioActual = funcionarioActual;
	}

	public SelectItem[] getListaTiposReporteProyectoItem() {
		return listaTiposReporteProyectoItem;
	}

	public void setListaTiposReporteProyectoItem(SelectItem[] listaTiposReporteProyectoItem) {
		this.listaTiposReporteProyectoItem = listaTiposReporteProyectoItem;
	}

	public String getTipoProyectoReporte() {
		return tipoProyectoReporte;
	}

	public void setTipoProyectoReporte(String tipoProyectoReporte) {
		this.tipoProyectoReporte = tipoProyectoReporte;
	}

	public String[] getSelectedSedes() {
		return selectedSedes;
	}

	public void setSelectedSedes(String[] selectedSedes) {
		this.selectedSedes = selectedSedes;
	}

	public String[] getSelectedSedesLeg() {
		return selectedSedesLeg;
	}

	public void setSelectedSedesLeg(String[] selectedSedesLeg) {
		this.selectedSedesLeg = selectedSedesLeg;
	}

	public List<Dependencia> getListaDependenciasLeg() {
		return listaDependenciasLeg;
	}

	public void setListaDependenciasLeg(List<Dependencia> listaDependenciasLeg) {
		this.listaDependenciasLeg = listaDependenciasLeg;
	}

	public SelectItem[] getListaSedesItemLeg() {
		return listaSedesItemLeg;
	}

	public void setListaSedesItemLeg(SelectItem[] listaSedesItemLeg) {
		this.listaSedesItemLeg = listaSedesItemLeg;
	}

	public SelectItem[] getListaDependenciasItemLeg() {
		return listaDependenciasItemLeg;
	}

	public void setListaDependenciasItemLeg(SelectItem[] listaDependenciasItemLeg) {
		this.listaDependenciasItemLeg = listaDependenciasItemLeg;
	}

	public String getDeptosLeg() {
		return deptosLeg;
	}

	public void setDeptosLeg(String deptosLeg) {
		this.deptosLeg = deptosLeg;
	}

	public SelectItem[] getListaFacultadesItemLeg() {
		return listaFacultadesItemLeg;
	}

	public void setListaFacultadesItemLeg(SelectItem[] listaFacultadesItemLeg) {
		this.listaFacultadesItemLeg = listaFacultadesItemLeg;
	}

	public SelectItem[] getListaDeptosItemLeg() {
		return listaDeptosItemLeg;
	}

	public void setListaDeptosItemLeg(SelectItem[] listaDeptosItemLeg) {
		this.listaDeptosItemLeg = listaDeptosItemLeg;
	}

	public List<Dependencia> getListaDeptosLeg() {
		return listaDeptosLeg;
	}

	public void setListaDeptosLeg(List<Dependencia> listaDeptosLeg) {
		this.listaDeptosLeg = listaDeptosLeg;
	}

	public String[] getSelectedFacultadesLeg() {
		return selectedFacultadesLeg;
	}

	public void setSelectedFacultadesLeg(String[] selectedFacultadesLeg) {
		this.selectedFacultadesLeg = selectedFacultadesLeg;
	}

	public String[] getSelectedDeptosLeg() {
		return selectedDeptosLeg;
	}

	public void setSelectedDeptosLeg(String[] selectedDeptosLeg) {
		this.selectedDeptosLeg = selectedDeptosLeg;
	}

	public SelectItem[] getListaInfoAdicionalReporteProyCoord() {
		return listaInfoAdicionalReporteProyCoord;
	}

	public void setListaInfoAdicionalReporteProyCoord(SelectItem[] listaInfoAdicionalReporteProyCoord) {
		this.listaInfoAdicionalReporteProyCoord = listaInfoAdicionalReporteProyCoord;
	}

	public String getInfoAdicionalReporteProyCoord() {
		return infoAdicionalReporteProyCoord;
	}

	public void setInfoAdicionalReporteProyCoord(String infoAdicionalReporteProyCoord) {
		this.infoAdicionalReporteProyCoord = infoAdicionalReporteProyCoord;
	}

	public String getMsgInfoAdicionalReportePry() {
		return msgInfoAdicionalReportePry;
	}

	public void setMsgInfoAdicionalReportePry(String msgInfoAdicionalReportePry) {
		this.msgInfoAdicionalReportePry = msgInfoAdicionalReportePry;
	}

	public SelectItem[] getListaInfoAdicionalReporteProyExt() {
		return listaInfoAdicionalReporteProyExt;
	}

	public void setListaInfoAdicionalReporteProyExt(SelectItem[] listaInfoAdicionalReporteProyExt) {
		this.listaInfoAdicionalReporteProyExt = listaInfoAdicionalReporteProyExt;
	}

	public String getInfoAdicionalReporteProyExt() {
		return infoAdicionalReporteProyExt;
	}

	public void setInfoAdicionalReporteProyExt(String infoAdicionalReporteProyExt) {
		this.infoAdicionalReporteProyExt = infoAdicionalReporteProyExt;
	}

	public String getMsgInfoAdicionalReportePryExt() {
		return msgInfoAdicionalReportePryExt;
	}

	public void setMsgInfoAdicionalReportePryExt(String msgInfoAdicionalReportePryExt) {
		this.msgInfoAdicionalReportePryExt = msgInfoAdicionalReportePryExt;
	}
	
	public void actualizarMsgReg() {
		msgInfoAdicionalReportePry = "";
		if (infoAdicionalReporteProyReg.equals("AVA")) {
			msgInfoAdicionalReportePry = "Además de los campos seleccionados en el contenido BASE, se incluye la información de avales.";
		}
	}

	public void actualizarMsgRP() {
		msgInfoAdicionalReportePry = "";
		if (infoAdicionalReporteProyCoord.equals("ATE")) {
			msgInfoAdicionalReportePry = "Además de los campos seleccionados en el contenido BASE, se incluye la información de las áreas temáticas asociadas a los proyectos.";
		} else if (infoAdicionalReporteProyCoord.equals("COM")) {
			msgInfoAdicionalReportePry = "Además de los campos seleccionados en el contenido BASE, se incluye la información de compromisos pendientes para informes finales asociados a los proyectos.";
		} else if (infoAdicionalReporteProyCoord.equals("DOC")) {
			msgInfoAdicionalReportePry = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los docentes vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyCoord.equals("EST")) {
			msgInfoAdicionalReportePry = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los estudiantes vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyCoord.equals("GRU")) {
			msgInfoAdicionalReportePry = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los grupos de investigación vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyCoord.equals("INF")) {
			msgInfoAdicionalReportePry = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los informes (tanto de avance como finales) vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyCoord.equals("PRD")) {
			msgInfoAdicionalReportePry = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los productos (tanto esperados como logrados) vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyCoord.equals("PRO")) {
			msgInfoAdicionalReportePry = "Además de los campos seleccionados en el contenido BASE, se incluye la información de las prórrogas aprobadas a los proyectos.";
		} else if (infoAdicionalReporteProyCoord.equals("DRP")) {
			msgInfoAdicionalReportePry = "Además de los campos seleccionados en el contenido BASE, se incluye la información de las dependencias responsables de los proyectos.";
		} else if (infoAdicionalReporteProyCoord.equals("ODS")) {
			msgInfoAdicionalReportePry = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los objetivos de desarrollo sostenible secundarios vinculados a los proyectos.";
		}
	}

	public String getselSedes() {
		String s = "";
		for (SelectItem data : listaSedesItem) {
			for (String si : selectedSedes) {
				if (((String) data.getValue()).equals(si)) {
					if (s.length() == 0) {
						s += data.getLabel();
					} else {
						s += ", " + data.getLabel();
					}
					break;
				}
			}
		}
		return s;
	}

	public String getselEstados() {
		String s = "";
		if (selectedEstados != null && selectedEstados.length != 0) {
			for (Object data : listaEstadosProyecto) {
				for (String si : selectedEstados) {
					if (((String) ((SelectItem) data).getValue()).equals(si)) {
						if (s.length() == 0) {
							s += ((SelectItem) data).getLabel();
						} else {
							s += ", " + ((SelectItem) data).getLabel();
						}
						break;
					}
				}
			}
		}
		return s;
	}
	
	public String getselEstadosExt() {
		String s = "";
		if (selectedEstadosLeg != null && selectedEstadosLeg.length != 0) {
			for (Object data : listaEstadosProyecto) {
				for (String si : selectedEstadosLeg) {
					if (((String) ((SelectItem) data).getValue()).equals(si)) {
						if (s.length() == 0) {
							s += ((SelectItem) data).getLabel();
						} else {
							s += ", " + ((SelectItem) data).getLabel();
						}
						break;
					}
				}
			}
		}
		return s;
	}

	public String getselSedesExt() {
		String s = "";
		for (SelectItem data : listaSedesItem) {
			for (String si : selectedSedesLeg) {
				if (((String) data.getValue()).equals(si)) {
					if (s.length() == 0) {
						s += data.getLabel();
					} else {
						s += ", " + data.getLabel();
					}
					break;
				}
			}
		}
		return s;
	}
	
	public String getselSedesReg() {
		String s = "";
		for (SelectItem data : listaSedesItem) {
			for (String si : selectedSedesReg) {
				if (((String) data.getValue()).equals(si)) {
					if (s.length() == 0) {
						s += data.getLabel();
					} else {
						s += ", " + data.getLabel();
					}
					break;
				}
			}
		}
		return s;
	}

	public void actualizarMsgRPE() {
		msgInfoAdicionalReportePryExt = "";
		if (infoAdicionalReporteProyExt.equals("SFV")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de las sedes y facultades vinculadas a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("PRO")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de las prórrogas aprobadas a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("INA")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los informes de avance vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("INF")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los informes finales vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("AVA")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los avales asociados a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("CON")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los convenios vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("ENF")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de las entidades financiadoras de los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("ENE")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de las entidades ejecutoras de los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("ENP")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de las entidades participantes de los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("DES")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los desembolsos vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("EST")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los estudiantes vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("ENT")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los entes territoriales vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("GRU")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los grupos de investigación vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("CYT")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información del Programa Nacional de Ciencia y Tecnología (CyT) relacionada a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("PRE")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los proudctos esperados que se vincularon a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("PRL")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los proudctos logrados que se vincularon a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("PIA")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de las fechas planeadas para los informes de avance vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("ING")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los ingresos vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("DRP")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de las dependencias responsables de los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("DOC")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los docentes vinculados a los proyectos.";
		} else if (infoAdicionalReporteProyExt.equals("ODS")) {
			msgInfoAdicionalReportePryExt = "Además de los campos seleccionados en el contenido BASE, se incluye la información de los objetivos de desarrollo sostenible secundarios vinculados a los proyectos.";
		}
	}

	public List<Dependencia> getListaDependenciasReg() {
		return listaDependenciasReg;
	}

	public void setListaDependenciasReg(List<Dependencia> listaDependenciasReg) {
		this.listaDependenciasReg = listaDependenciasReg;
	}

	public SelectItem[] getListaDependenciasItemReg() {
		return listaDependenciasItemReg;
	}

	public void setListaDependenciasItemReg(SelectItem[] listaDependenciasItemReg) {
		this.listaDependenciasItemReg = listaDependenciasItemReg;
	}

	public String[] getSelectedSedesReg() {
		return selectedSedesReg;
	}

	public void setSelectedSedesReg(String[] selectedSedesReg) {
		this.selectedSedesReg = selectedSedesReg;
	}

	public SelectItem[] getListaSedesItemReg() {
		return listaSedesItemReg;
	}

	public void setListaSedesItemReg(SelectItem[] listaSedesItemReg) {
		this.listaSedesItemReg = listaSedesItemReg;
	}

	public String[] getSelectedFacultadesReg() {
		return selectedFacultadesReg;
	}

	public void setSelectedFacultadesReg(String[] selectedFacultadesReg) {
		this.selectedFacultadesReg = selectedFacultadesReg;
	}

	public SelectItem[] getListaFacultadesItemReg() {
		return listaFacultadesItemReg;
	}

	public void setListaFacultadesItemReg(SelectItem[] listaFacultadesItemReg) {
		this.listaFacultadesItemReg = listaFacultadesItemReg;
	}

	public String[] getSelectedDeptosReg() {
		return selectedDeptosReg;
	}

	public void setSelectedDeptosReg(String[] selectedDeptosReg) {
		this.selectedDeptosReg = selectedDeptosReg;
	}

	public SelectItem[] getListaDeptosItemReg() {
		return listaDeptosItemReg;
	}

	public void setListaDeptosItemReg(SelectItem[] listaDeptosItemReg) {
		this.listaDeptosItemReg = listaDeptosItemReg;
	}

	public String getDeptosReg() {
		return deptosReg;
	}

	public void setDeptosReg(String deptosReg) {
		this.deptosReg = deptosReg;
	}

	public List<Dependencia> getListaDeptosReg() {
		return listaDeptosReg;
	}

	public void setListaDeptosReg(List<Dependencia> listaDeptosReg) {
		this.listaDeptosReg = listaDeptosReg;
	}

	public SelectItem[] getListaInfoAdicionalReporteProyReg() {
		return listaInfoAdicionalReporteProyReg;
	}

	public void setListaInfoAdicionalReporteProyReg(SelectItem[] listaInfoAdicionalReporteProyReg) {
		this.listaInfoAdicionalReporteProyReg = listaInfoAdicionalReporteProyReg;
	}

	public String getInfoAdicionalReporteProyReg() {
		return infoAdicionalReporteProyReg;
	}

	public void setInfoAdicionalReporteProyReg(String infoAdicionalReporteProyReg) {
		this.infoAdicionalReporteProyReg = infoAdicionalReporteProyReg;
	}

	public String getMsgInfoAdicionalReportePryReg() {
		return msgInfoAdicionalReportePryReg;
	}

	public void setMsgInfoAdicionalReportePryReg(String msgInfoAdicionalReportePryReg) {
		this.msgInfoAdicionalReportePryReg = msgInfoAdicionalReportePryReg;
	}

	public String getFacultadReg() {
		return facultadReg;
	}

	public void setFacultadReg(String facultadReg) {
		this.facultadReg = facultadReg;
	}

	public String getSedeReg() {
		return sedeReg;
	}

	public void setSedeReg(String sedeReg) {
		this.sedeReg = sedeReg;
	}
}
