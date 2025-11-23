package co.edu.unal.hermes.vista.aval;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IndicadorReporte;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.SemilleroEstado;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.aval.base.BaseManejadorAvalReporteCoord;

public class ManejadorReportesSede extends BaseManejadorAvalReporteCoord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// General
	private String nombreSede;
	private InvestigadorInterno investigador;

	// Avales
	private StreamedContent graficoAvalesSede;

	// Grupos
	private StreamedContent graficoGruposSede;

	// Investigadores
	private StreamedContent graficoDocentesSede;

	// Colecciones
	private StreamedContent graficoColeccionesSede;

	// Convocatorias internas
	private StreamedContent graficoConvocatoriasSede;

	// portafolio
	private boolean incluirInvestigadores;
	
	private String[] facultadesSeleccionadas;
	
	private String contenidoAdicional;

	public ManejadorReportesSede() {
		super();
		// Inicializacion valores
		investigador = servicioPersona
				.obtenerInvestigadorInterno(((Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL)).getId());
		sedeSeleccionada = investigador.getDependencia().getSede().getId().toString();
		sedeSeleccionadaAval = sedeSeleccionada;
		nombreSede = investigador.getDependencia().getSede().getNombre();
		selItem = 1;
		sesion.removeAttribute("manejadorSemillerosSolicitudDI");
		sesion.removeAttribute("manejadorSemillerosConsultaDI");
		cargarListadosIndicadores();
		consultarConvocatoriasMovilidades();
		ocultarGraficos();
		incluirInvestigadores = true;
		listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
		listaSedesItem = new SelectItem[listaSedes.size()];
		for (int i = 0; i < listaSedes.size(); i++) {
			Dependencia sede = (Dependencia) listaSedes.get(i);
			String nombreSede = sede.getNombre().substring(0, 1).toUpperCase()
					+ sede.getNombre().substring(1, sede.getNombre().length());
			listaSedesItem[i] = new SelectItem(sede.getId(), nombreSede);
		}
		Dependencia sede = new Dependencia();
		sede.setId(sedeSeleccionada);
		listaFacultades = (List<Dependencia>) servicioGeneral.obtenerFacultades(sede);
		listaFacultadesItem = new SelectItem[listaFacultades.size()];
		for (int i = 0; i < listaFacultades.size(); i++) {
			Dependencia facultad = (Dependencia) listaFacultades.get(i);
			listaFacultadesItem[i] = new SelectItem(facultad.getId(), facultad.getNombre());
		}
	}

	public void cargarListadosIndicadores() {
		cargarListadoIndicadoresConvocatorias(IndicadorReporte.SEDE, Tipos.CAT_CONV_INTERNA);
		cargarListadoIndicadoresAvales(IndicadorReporte.SEDE, Tipos.CAT_AVALES);
		cargarListadoIndicadoresGrupos(IndicadorReporte.SEDE, Tipos.CAT_GRUPOS);
		cargarListadoIndicadoresInvestigadores(IndicadorReporte.SEDE, Tipos.CAT_INVESTIGADORES);
		cargarListadoIndicadoresColecciones(IndicadorReporte.SEDE, Tipos.CAT_COLECCIONES);
	}

	public String imprimirReporteSede() {
		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("fac", "NO");
		r.adicionarParametro("sedId", ii.getDependencia2().getSede().getId().toString());
		return imprimirReporteAvales(r);
	}

	public String reporteGruposSede() {
		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());

		if (esCadenaVacia(estadosSeleccionados(selectedEstadosGrupos))) {
			mensajeError("Debe seleccionar al menos un estado de grupo para el reporte");
			return "";
		}

		ReporteBirt r = new ReporteBirt();
		r.setNombreReporte("/grupo/reporteGruposSede");
		r.adicionarParametro("sed", ii.getDependencia().getSede().getId().toString());
		r.adicionarParametro("estado", estadosSeleccionados(selectedEstadosGrupos));
		
		r.adicionarParametro("codigoCol", String.valueOf(columnsGrupos[0]));
		r.adicionarParametro("CatColciencias", String.valueOf(columnsGrupos[1]));
		r.adicionarParametro("nombreGrupo", String.valueOf(columnsGrupos[2]));
		r.adicionarParametro("fechaReg", String.valueOf(columnsGrupos[3]));
		r.adicionarParametro("estadoGrupo", String.valueOf(columnsGrupos[4]));
		r.adicionarParametro("enlG", String.valueOf(columnsGrupos[5]));
		r.adicionarParametro("enlH", String.valueOf(columnsGrupos[6]));
		r.adicionarParametro("contacto", String.valueOf(columnsGrupos[7]));
		r.adicionarParametro("facultad", String.valueOf(columnsGrupos[8]));
		r.adicionarParametro("sede", String.valueOf(columnsGrupos[9]));		
		r.adicionarParametro("interfac", String.valueOf(columnsGrupos[10]));
		r.adicionarParametro("inters", String.valueOf(columnsGrupos[11]));
		r.adicionarParametro("interi", String.valueOf(columnsGrupos[12]));
		r.adicionarParametro("lider", String.valueOf(columnsGrupos[13]));
		r.adicionarParametro("enf", String.valueOf(columnsGrupos[14]));
		r.adicionarParametro("pri", String.valueOf(columnsGrupos[15]));
		r.adicionarParametro("persp", String.valueOf(columnsGrupos[16]));		
		r.adicionarParametro("nec", String.valueOf(columnsGrupos[17]));
		r.adicionarParametro("pert", String.valueOf(columnsGrupos[18]));
		r.adicionarParametro("sim", String.valueOf(columnsGrupos[19]));
		r.adicionarParametro("taval", String.valueOf(columnsGrupos[20]));
		r.adicionarParametro("ocde", String.valueOf(columnsGrupos[21]));
		r.adicionarParametro("agenda", String.valueOf(columnsGrupos[22]));
		r.adicionarParametro("infoAdic", String.valueOf(getMostrarContenidoAdicionalGrupos()));
		r.adicionarParametro("fechaActivo", String.valueOf(true));
		r.adicionarParametro("fechaMod", String.valueOf(true));
		r.adicionarParametro("ods", String.valueOf(true));

		r.setFormato(ReporteBirt.FORMATO_XLS);
		sesion.setAttribute(NOMBRE_REPORTE_SESION, r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}

	public void generarGraficoAvalesSede() throws IOException {
		if (esCadenaVacia(tipoReporteAval)) {
			mensajeError("Debe seleccionar un indicador para que pueda ser generado.");
			return;
		}
		if (!validarParametrosReporteAval()) {
			return;
		}
		ocultarGraficos();
		reporteAvales = true;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getRenderResponse()) {
			graficoAvalesSede = new DefaultStreamedContent();
			return;
		}
		JFreeChart jfreechart = generarConsultaIndicadorAval();

		if (jfreechart != null) {
			jfreechart = adicionarPieGrafico(jfreechart, obtenerSubtituloIndicador());

			if (!indicador.getGraficoPastel()) {
				jfreechart.getCategoryPlot().setRenderer(crearCategoryPlotRenderer(jfreechart));
			} else {
				generarPlotDefault(jfreechart);
			}
			File chartFile = new File(NOMBRE_CHART_FILE);
			if (indicador.getGraficoPastel()) {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			} else {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 750, 600);
			}
			graficoAvalesSede = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}
	}
	
	public String generarReporteSemilleros() {
		if (esCadenaVacia(estadosSeleccionados(facultadesSeleccionadas))) {
			mensajeError("Debe seleccionar, al menos, una facultad.");
			return "";
		}
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("sede", investigador.getDependencia2().getSede().getId().toString());
		r.adicionarParametro("facultad", estadosSeleccionados(facultadesSeleccionadas));
		mostrarContenidoAdicional=contenidoAdicional;
		return imprimirReporteSemilleros(r);
	}

	public void generarGraficoGruposSede() throws IOException {
		if (esCadenaVacia(tipoReporteGrupo)) {
			mensajeError("Debe seleccionar un indicador para que pueda ser generado.");
			return;
		}
		ocultarGraficos();
		reporteGrupos = true;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getRenderResponse()) {
			graficoGruposSede = new DefaultStreamedContent();
			return;
		}

		JFreeChart jfreechart = generarConsultaIndicadorGrupo();

		if (jfreechart != null) {
			jfreechart = adicionarPieGrafico(jfreechart, obtenerSubtituloIndicador());

			if (indicador.getGraficoPastel()) {
				generarPlotDefault(jfreechart);
			} else {
				jfreechart.getCategoryPlot().setRenderer(crearCategoryPlotRenderer(jfreechart));
			}

			File chartFile = new File(NOMBRE_CHART_FILE);
			if (!indicador.getGraficoPastel()) {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 725, 725);
			} else {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			}
			graficoGruposSede = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}
	}

	public void generarGraficoDocentesSedes() throws IOException {
		if (esCadenaVacia(tipoReporteInvestigadores)) {
			mensajeError("Debe seleccionar un indicador para que pueda ser generado.");
			return;
		}
		ocultarGraficos();
		reporteInvestigadores = true;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getRenderResponse()) {
			graficoDocentesSede = new DefaultStreamedContent();
			return;
		}

		JFreeChart jfreechart = generarConsultaIndicadorInvestigadores();

		if (jfreechart != null) {
			jfreechart = adicionarPieGrafico(jfreechart, obtenerSubtituloIndicador());
			jfreechart.getCategoryPlot().setRenderer(crearCategoryPlotRenderer(jfreechart));

			File chartFile = new File(NOMBRE_CHART_FILE);
			if ("2".equals(sedeSeleccionada)) {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 750, 750);
			} else {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			}
			graficoDocentesSede = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}
	}

	public void generarGraficoColeccionesSede() throws IOException {
		if (esCadenaVacia(tipoReporteBiodiversidad)) {
			mensajeError("Debe seleccionar un indicador para que pueda ser generado.");
			return;
		}
		ocultarGraficos();
		reporteBiodiversidad = true;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getRenderResponse()) {
			graficoColeccionesSede = new DefaultStreamedContent();
			return;
		}

		JFreeChart jfreechart = generarConsultaIndicadorBiodiversidad();

		if (jfreechart != null) {
			jfreechart = adicionarPieGrafico(jfreechart, obtenerSubtituloIndicador());
			generarPlotDefault(jfreechart);

			File chartFile = new File(NOMBRE_CHART_FILE);
			ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			graficoColeccionesSede = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}
	}

	public void generarGraficoConvocatoriasSede() throws IOException {
		if (esCadenaVacia(tipoReporteConvocatorias)) {
			mensajeError("Debe seleccionar un indicador para que pueda ser generado.");
			return;
		}
		if (!validarParametrosReporteConvocatoria()) {
			return;
		}
		ocultarGraficos();
		reporteConvocatorias = true;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getRenderResponse()) {
			graficoConvocatoriasSede = new DefaultStreamedContent();
			return;
		}

		JFreeChart jfreechart = generarConsultaIndicadorConvocatoria();

		if (jfreechart != null) {
			jfreechart = adicionarPieGrafico(jfreechart, obtenerSubtituloIndicador());
			if (!indicador.getGraficoPastel()) {
				jfreechart.getCategoryPlot().setRenderer(crearCategoryPlotRenderer(jfreechart));
			} else {
				PiePlot plot = (PiePlot) jfreechart.getPlot();
				plot.setDrawingSupplier(new ChartDrawingSupplier());
				plot.setBackgroundPaint(Color.white);

				PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0} = {1} ({2})",
						new DecimalFormat("0"), new DecimalFormat("0.0%"));

				if (indicador.getTotalMoneda()) {
					generator = new StandardPieSectionLabelGenerator("{0} = {1} ({2})",
							NumberFormat.getCurrencyInstance(), new DecimalFormat("0.0%"));
				}
				plot.setLabelGenerator(generator);
				plot.setStartAngle(45);
				plot.setNoDataMessage(MENSAJE_NO_DATOS);
			}

			File chartFile = new File(NOMBRE_CHART_FILE);

			if (indicador.getGraficoPastel()) {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			} else {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 1000, 750);
			}
			graficoConvocatoriasSede = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}
	}

	public String generarPortafolio() {
		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());

		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("s", ii.getDependencia().getSede().getId().toString());
		if (incluirInvestigadores) {
			r.adicionarParametro("invest", "S");
		} else {
			r.adicionarParametro("invest", "N");
		}
		r.setNombreReporte("/portafolio/reporte_portafolio_sede");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute(NOMBRE_REPORTE_SESION, r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}

	public String imprimirReporteSedeMovilidades() {
		ReporteBirt r = new ReporteBirt();
		personaActual = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);
		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
		r.adicionarParametro("sedId", ii.getDependencia().getId().substring(0, 1));
		r.adicionarParametro("facultadId", "NO");
		return imprimirReporteMovilidades(r);
	}

	public StreamedContent getGraficoAvalesSede() {
		return graficoAvalesSede;
	}

	public void setGraficoAvalesSede(StreamedContent graficoAvalesSede) {
		this.graficoAvalesSede = graficoAvalesSede;
	}

	public StreamedContent getGraficoGruposSede() {
		return graficoGruposSede;
	}

	public void setGraficoGruposSede(StreamedContent graficoGruposSede) {
		this.graficoGruposSede = graficoGruposSede;
	}

	public StreamedContent getGraficoDocentesSede() {
		return graficoDocentesSede;
	}

	public void setGraficoDocentesSede(StreamedContent graficoDocentesSede) {
		this.graficoDocentesSede = graficoDocentesSede;
	}

	public StreamedContent getGraficoColeccionesSede() {
		return graficoColeccionesSede;
	}

	public void setGraficoColeccionesSede(StreamedContent graficoColeccionesSede) {
		this.graficoColeccionesSede = graficoColeccionesSede;
	}

	public StreamedContent getGraficoConvocatoriasSede() {
		return graficoConvocatoriasSede;
	}

	public void setGraficoConvocatoriasSede(StreamedContent graficoConvocatoriasSede) {
		this.graficoConvocatoriasSede = graficoConvocatoriasSede;
	}

	public String getNombreSede() {
		return nombreSede;
	}

	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
	}

	public InvestigadorInterno getInvestigador() {
		return investigador;
	}

	public void setInvestigador(InvestigadorInterno investigador) {
		this.investigador = investigador;
	}

	public boolean isIncluirInvestigadores() {
		return incluirInvestigadores;
	}

	public void setIncluirInvestigadores(boolean incluirInvestigadores) {
		this.incluirInvestigadores = incluirInvestigadores;
	}

	public String[] getFacultadesSeleccionadas() {
		return facultadesSeleccionadas;
	}

	public void setFacultadesSeleccionadas(String[] facultadesSeleccionadas) {
		this.facultadesSeleccionadas = facultadesSeleccionadas;
	}

	public String getContenidoAdicional() {
		return contenidoAdicional;
	}

	public void setContenidoAdicional(String contenidoAdicional) {
		this.contenidoAdicional = contenidoAdicional;
	}
	
	public String consultarReporteProyectosMorosos() {
		ReporteBirt r = new ReporteBirt();
		//r.adicionarParametro("sedId", "1 2 3 4 5 6 7 8 9 0");
		//r.adicionarParametro("facultadId", "NO");
		return consultarReporteProyectosMorosos(r);
	}

}
