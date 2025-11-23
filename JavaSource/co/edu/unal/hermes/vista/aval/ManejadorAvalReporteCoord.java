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

import co.edu.unal.hermes.modelo.IndicadorReporte;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.aval.base.BaseManejadorAvalReporteCoord;

public class ManejadorAvalReporteCoord extends BaseManejadorAvalReporteCoord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Avales
	private StreamedContent graficoAvalesNacional;

	// Grupos
	private StreamedContent graficoGruposNacional;

	// Investigadores
	private StreamedContent graficoDocentesNacional;

	// Colecciones
	private StreamedContent graficoColeccionesNacional;

	// Convocatorias internas
	private StreamedContent graficoConvocatoriasNacional;

	private SelectItem[] listaInfoAdicionalReporteGrupos;
	private String infoAdicionalReporteGrupos;
	private String msgInfoAdicionalReporteGrupos;
	
	private String[] sedesSeleccionadas;
	
	private String contenidoAdicional;

	public ManejadorAvalReporteCoord() {
		super();
		// Inicializacion valores
		selItem = 1;
		msgInfoAdicionalReporteGrupos = "";
		cargarListadosIndicadores();
		ocultarGraficos();
		consultarConvocatoriasMovilidades();
	}

	public void cargarListadosIndicadores() {
		cargarListadoIndicadoresConvocatorias(IndicadorReporte.NACIONAL, Tipos.CAT_CONV_INTERNA);
		cargarListadoIndicadoresAvales(IndicadorReporte.NACIONAL, Tipos.CAT_AVALES);
		cargarListadoIndicadoresGrupos(IndicadorReporte.NACIONAL, Tipos.CAT_GRUPOS);
		cargarListadoIndicadoresInvestigadores(IndicadorReporte.NACIONAL, Tipos.CAT_INVESTIGADORES);
		cargarListadoIndicadoresColecciones(IndicadorReporte.NACIONAL, Tipos.CAT_COLECCIONES);
		setListaInfoAdicionalReporteGrupos(new SelectItem[13]);
		getListaInfoAdicionalReporteGrupos()[0] = new SelectItem("SUI", "<Seleccione Un Ítem>");
		getListaInfoAdicionalReporteGrupos()[1] = new SelectItem("SFS", "Sedes y Facultades Secundarias");
		getListaInfoAdicionalReporteGrupos()[2] = new SelectItem("OCD", "Áreas OCDE");
		getListaInfoAdicionalReporteGrupos()[3] = new SelectItem("LIN", "Líneas de Investigación");
		getListaInfoAdicionalReporteGrupos()[4] = new SelectItem("ADC", "Agendas de Conocimiento");
		getListaInfoAdicionalReporteGrupos()[5] = new SelectItem("PDE", "Planes de Estudio");
		getListaInfoAdicionalReporteGrupos()[6] = new SelectItem("INT", "Integrantes");
		getListaInfoAdicionalReporteGrupos()[7] = new SelectItem("PRY", "Proyectos");
		getListaInfoAdicionalReporteGrupos()[8] = new SelectItem("PRD", "Productos");
		getListaInfoAdicionalReporteGrupos()[9] = new SelectItem("AVL", "Avales");
		getListaInfoAdicionalReporteGrupos()[10] = new SelectItem("ENT", "Entidades Asociadas");
		getListaInfoAdicionalReporteGrupos()[11] = new SelectItem("LAB", "Laboratorios Asociados");
		getListaInfoAdicionalReporteGrupos()[12] = new SelectItem("SEM", "Semilleros Asociados");
		setInfoAdicionalReporteGrupos("SUI");
	}

	public void generarGraficoAvalesNacional() throws IOException {
		if (esCadenaVacia(tipoReporteAval)) {
			mensajeError("Debe seleccionar un indicador para que pueda ser generado.");
			return;
		}
		if (!validarParametrosReporteAval()) {
			return;
		}
		reporteAvales = true;
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getRenderResponse()) {
			graficoAvalesNacional = new DefaultStreamedContent();
			return;
		}

		JFreeChart jfreechart = generarConsultaIndicadorAval();
		if (jfreechart != null) {
			jfreechart = adicionarPieGrafico(jfreechart, obtenerSubtituloIndicador());
			generarPlotDefault(jfreechart);
			File chartFile = new File(NOMBRE_CHART_FILE);
			ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			graficoAvalesNacional = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}
	}

	public StreamedContent getGraficoAvalesNacional() {
		return graficoAvalesNacional;
	}

	public void generarGraficoGruposNacional() throws IOException {
		if (esCadenaVacia(tipoReporteGrupo)) {
			mensajeError("Debe seleccionar un indicador para que pueda ser generado.");
			return;
		}
		ocultarGraficos();
		reporteGrupos = true;
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getRenderResponse()) {
			graficoGruposNacional = new DefaultStreamedContent();
			return;
		}

		JFreeChart jfreechart = generarConsultaIndicadorGrupo();
		if (jfreechart != null) {
			jfreechart = adicionarPieGrafico(jfreechart, obtenerSubtituloIndicador());
			if (!indicador.getGraficoPastel()) {
				jfreechart.getCategoryPlot().setRenderer(crearCategoryPlotRenderer(jfreechart));
			} else {
				generarPlotDefault(jfreechart);
			}
			File chartFile = new File(NOMBRE_CHART_FILE);
			if (!indicador.getGraficoPastel()) {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 700, 700);
			} else {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			}
			graficoGruposNacional = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}
	}

	public StreamedContent getGraficoGruposNacional() {
		return graficoGruposNacional;
	}

	public void generarGraficoDocentesNacional() throws IOException {
		if (esCadenaVacia(tipoReporteInvestigadores)) {
			mensajeError("Debe seleccionar un indicador para que pueda ser generado.");
			return;
		}
		ocultarGraficos();
		reporteInvestigadores = true;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getRenderResponse()) {
			graficoDocentesNacional = new DefaultStreamedContent();
			return;
		}

		JFreeChart jfreechart = generarConsultaIndicadorInvestigadores();

		if (jfreechart != null) {
			jfreechart = adicionarPieGrafico(jfreechart, obtenerSubtituloIndicador());
			generarPlotDefault(jfreechart);
			File chartFile = new File(NOMBRE_CHART_FILE);
			ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			graficoDocentesNacional = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}
	}

	public StreamedContent getGraficoDocentesNacional() {
		return graficoDocentesNacional;
	}
	
	public String generarReporteSemilleros() {
		if (esCadenaVacia(estadosSeleccionados(sedesSeleccionadas))) {
			mensajeError("Debe seleccionar una sede.");
			return "";
		}
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("sede", estadosSeleccionados(sedesSeleccionadas));
		r.adicionarParametro("facultad", "ALL");
		mostrarContenidoAdicional=contenidoAdicional;
		return imprimirReporteSemilleros(r);
	}

	public void generarGraficoColeccionesNacional() throws IOException {
		if (esCadenaVacia(tipoReporteBiodiversidad)) {
			mensajeError("Debe seleccionar un indicador para que pueda ser generado.");
			return;
		}
		ocultarGraficos();
		reporteBiodiversidad = true;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getRenderResponse()) {
			graficoColeccionesNacional = new DefaultStreamedContent();
			return;
		}

		JFreeChart jfreechart = generarConsultaIndicadorBiodiversidad();

		if (jfreechart != null) {
			jfreechart = adicionarPieGrafico(jfreechart, obtenerSubtituloIndicador());
			generarPlotDefault(jfreechart);
			File chartFile = new File(NOMBRE_CHART_FILE);
			ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			graficoColeccionesNacional = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}
	}

	public void generarGraficoConvocatoriasNacional() throws IOException {
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
			graficoConvocatoriasNacional = new DefaultStreamedContent();
			return;
		}

		JFreeChart jfreechart = generarConsultaIndicadorConvocatoria();

		if (jfreechart != null) {

			jfreechart = adicionarPieGrafico(jfreechart, obtenerSubtituloIndicador());

			if (indicador.getGraficoPastel()) {
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
			} else {
				jfreechart.getCategoryPlot().setRenderer(crearCategoryPlotRenderer(jfreechart));
			}

			File chartFile = new File(NOMBRE_CHART_FILE);
			if (!indicador.getGraficoPastel()) {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 1300, 600);
			} else {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			}
			graficoConvocatoriasNacional = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}

	}

	public String imprimirReporteAvalesNacional() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("sedId", "1 2 3 4 5 6 7 8 9 0");
		r.adicionarParametro("fac", "NO");
		return imprimirReporteAvales(r);
	}

	public String imprimirReporteNacionalMovilidades() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("sedId", "1 2 3 4 5 6 7 8 9 0");
		r.adicionarParametro("facultadId", "NO");
		return imprimirReporteMovilidades(r);
	}
	
	public String consultarReporteProyectosMorosos() {
		ReporteBirt r = new ReporteBirt();
		//r.adicionarParametro("sedId", "1 2 3 4 5 6 7 8 9 0");
		//r.adicionarParametro("facultadId", "NO");
		return consultarReporteProyectosMorosos(r);
	}

	public String reporteGruposUniversidad() {
		if (esCadenaVacia(estadosSeleccionados(selectedEstadosGrupos))) {
			mensajeError("Debe seleccionar al menos un estado de grupo para el reporte");
			return "";
		}

		ReporteBirt r = new ReporteBirt();
		r.setNombreReporte("/grupo/reporteGruposUniversidad");
		
		r.adicionarParametro("estado", estadosSeleccionados(selectedEstadosGrupos));	
		
		if (getInfoAdicionalReporteGrupos().equals("OCD")) {
			r.setNombreReporte("/grupo/reporteGruposUniversidad_ocde");
		}
		
		/*
		// Old report
		if (getInfoAdicionalReporteGrupos().equals("PRY")) {
			r.setNombreReporte("/grupo/reporteGruposUniversidad_Proyectos");
		} else if (getInfoAdicionalReporteGrupos().equals("PRD")) {
			r.setNombreReporte("/grupo/reporteGruposUniversidad_Productos");
		}
		// r.adicionarParametro("sed", "1");
		 * */
		 

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
		r.adicionarParametro("fechaActivo", String.valueOf(true));
		r.adicionarParametro("fechaMod", String.valueOf(true));
		r.adicionarParametro("ods", String.valueOf(true));
		
		
		r.adicionarParametro("infoAdic", String.valueOf(getInfoAdicionalReporteGrupos()));
	
		r.setFormato(ReporteBirt.FORMATO_XLS);
		sesion.setAttribute(NOMBRE_REPORTE_SESION, r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}

	public void setGraficoAvalesNacional(StreamedContent graficoAvalesNacional) {
		this.graficoAvalesNacional = graficoAvalesNacional;
	}

	public void setGraficoGruposNacional(StreamedContent graficoGruposNacional) {
		this.graficoGruposNacional = graficoGruposNacional;
	}

	public void setGraficoDocentesNacional(StreamedContent graficoDocentesNacional) {
		this.graficoDocentesNacional = graficoDocentesNacional;
	}

	public StreamedContent getGraficoColeccionesNacional() {
		return graficoColeccionesNacional;
	}

	public void setGraficoColeccionesNacional(StreamedContent graficoColeccionesNacional) {
		this.graficoColeccionesNacional = graficoColeccionesNacional;
	}

	public StreamedContent getGraficoConvocatoriasNacional() {
		return graficoConvocatoriasNacional;
	}

	public void setGraficoConvocatoriasNacional(StreamedContent graficoConvocatoriasNacional) {
		this.graficoConvocatoriasNacional = graficoConvocatoriasNacional;
	}

	public SelectItem[] getListaInfoAdicionalReporteGrupos() {
		return listaInfoAdicionalReporteGrupos;
	}

	public void setListaInfoAdicionalReporteGrupos(SelectItem[] listaInfoAdicionalReporteGrupos) {
		this.listaInfoAdicionalReporteGrupos = listaInfoAdicionalReporteGrupos;
	}

	public String getMsgInfoAdicionalReporteGrupos() {
		return msgInfoAdicionalReporteGrupos;
	}

	public void setMsgInfoAdicionalReporteGrupos(String msgInfoAdicionalReporteGrupos) {
		this.msgInfoAdicionalReporteGrupos = msgInfoAdicionalReporteGrupos;
	}

	public String getInfoAdicionalReporteGrupos() {
		return infoAdicionalReporteGrupos;
	}

	public void setInfoAdicionalReporteGrupos(String infoAdicionalReporteGrupos) {
		this.infoAdicionalReporteGrupos = infoAdicionalReporteGrupos;
	}

	public void actualizarMsgRG() {
		msgInfoAdicionalReporteGrupos = "";
		if (infoAdicionalReporteGrupos.equals("SFS")) {
			msgInfoAdicionalReporteGrupos = "Además de los campos seleccionados en el contenido BASE, se incluyen las sede y facultades secundarias vinculadas a los grupos.";
		} else if (infoAdicionalReporteGrupos.equals("OCD")) {
			msgInfoAdicionalReporteGrupos = "Además de los campos seleccionados en el contenido BASE, se incluyen las áreas y subáreas OCDE (Tanto principales como secundarias) vinculadas a los grupos.";
		} else if (infoAdicionalReporteGrupos.equals("LIN")) {
			msgInfoAdicionalReporteGrupos = "Además de los campos seleccionados en el contenido BASE, se incluyen las líneas de investigación vinculadas a los grupos.";
		} else if (infoAdicionalReporteGrupos.equals("ADC")) {
			msgInfoAdicionalReporteGrupos = "Además de los campos seleccionados en el contenido BASE, se incluyen las agendas de conocimiento (Tanto principales como secundarias) vinculadas a los grupos.";
		} else if (infoAdicionalReporteGrupos.equals("PDE")) {
			msgInfoAdicionalReporteGrupos = "Además de los campos seleccionados en el contenido BASE, se incluyen los planes de estudio vinculados a los grupos.";
		} else if (infoAdicionalReporteGrupos.equals("INT")) {
			msgInfoAdicionalReporteGrupos = "Además de los campos seleccionados en el contenido BASE, se incluyen los integrantes vinculados a los grupos.";
		} else if (infoAdicionalReporteGrupos.equals("PRY")) {
			msgInfoAdicionalReporteGrupos = "Además de los campos seleccionados en el contenido BASE, se incluyen los proyectos vinculados a los grupos.";
		} else if (infoAdicionalReporteGrupos.equals("PRD")) {
			msgInfoAdicionalReporteGrupos = "Además de los campos seleccionados en el contenido BASE, se incluyen los productos vinculados a los grupos.";
		} else if (infoAdicionalReporteGrupos.equals("AVL")) {
			msgInfoAdicionalReporteGrupos = "Además de los campos seleccionados en el contenido BASE, se incluyen los avales vinculados a los grupos.";
		} else if (infoAdicionalReporteGrupos.equals("ENT")) {
			msgInfoAdicionalReporteGrupos = "Además de los campos seleccionados en el contenido BASE, se incluyen las entidades vinculadas a los grupos.";
		} else if (infoAdicionalReporteGrupos.equals("LAB")) {
			msgInfoAdicionalReporteGrupos = "Además de los campos seleccionados en el contenido BASE, se incluyen los laboratorios vinculados a los grupos.";
		}
	}

	public String[] getSedesSeleccionadas() {
		return sedesSeleccionadas;
	}

	public void setSedesSeleccionadas(String[] sedesSeleccionadas) {
		this.sedesSeleccionadas = sedesSeleccionadas;
	}

	public String getContenidoAdicional() {
		return contenidoAdicional;
	}

	public void setContenidoAdicional(String contenidoAdicional) {
		this.contenidoAdicional = contenidoAdicional;
	}
}
