package co.edu.unal.hermes.vista.aval;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
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

import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.IndicadorReporte;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.SemilleroEstado;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.aval.base.BaseManejadorAvalReporteCoord;

public class ManejadorAvalReporteFacultad extends BaseManejadorAvalReporteCoord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// General
	private String nombreFacultad;
	private Investigador investigador;

	// Avales
	private StreamedContent graficoAvalesFacultad;

	// Grupos
	private StreamedContent graficoGruposFacultad;

	// Investigadores
	private StreamedContent graficoDocentesFacultad;

	// Colecciones
	private StreamedContent graficoColeccionesFacultad;

	// Convocatorias internas
	private StreamedContent graficoConvocatoriasFacultad;

	// portafolio
	private boolean incluirInvestigadores;

	// movilidades

	public ManejadorAvalReporteFacultad() {
		super();
		sesion.removeAttribute("manejadorSemillerosSolicitudVIF");
		sesion.removeAttribute("manejadorSemillerosConsultaVIF");
		// Inicializacion valores
		investigador = servicioPersona
				.obtenerInvestigadorInterno(((Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL)).getId());
		sedeSeleccionada = investigador.getDependencia().getSede().getId().toString();
		facultadSeleccionada = investigador.getDependencia().getFacultad().getId().toString();
		facultadSeleccionadaAval = facultadSeleccionada;
		nombreFacultad = investigador.getDependencia().getFacultad().getNombre();

		selItem = 1;

		cargarListadosIndicadores();
		consultarConvocatoriasMovilidades();
		ocultarGraficos();
		incluirInvestigadores = true;
		
	}

	public void cargarListadosIndicadores() {
		cargarListadoIndicadoresConvocatorias(IndicadorReporte.FACULTAD, Tipos.CAT_CONV_INTERNA);
		cargarListadoIndicadoresAvales(IndicadorReporte.FACULTAD, Tipos.CAT_AVALES);
		cargarListadoIndicadoresGrupos(IndicadorReporte.FACULTAD, Tipos.CAT_GRUPOS);
		cargarListadoIndicadoresInvestigadores(IndicadorReporte.FACULTAD, Tipos.CAT_INVESTIGADORES);
		cargarListadoIndicadoresColecciones(IndicadorReporte.FACULTAD, Tipos.CAT_COLECCIONES);
	}

	public String imprimirReporteFacultadAvales() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("sedId", "NO");
		r.adicionarParametro("fac", investigador.getDependencia2().getFacultad().getId());
		return imprimirReporteAvales(r);
	}
	
	public String generarReporteSemilleros() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("sede", investigador.getDependencia2().getSede().getId().toString());
		r.adicionarParametro("facultad", investigador.getDependencia2().getFacultad().getId());
		return imprimirReporteSemilleros(r);
	}

	public String imprimirReporteFacultadMovilidades() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("sedId", "NO");
		r.adicionarParametro("facultadId", investigador.getDependencia().getFacultad().getId());
		return imprimirReporteMovilidades(r);
	}

	public String generarPortafolio() {
		ReporteBirt r = new ReporteBirt();
		if (incluirInvestigadores) {
			r.adicionarParametro("invest", "S");
		} else {
			r.adicionarParametro("invest", "N");
		}
		r.adicionarParametro("fac", investigador.getDependencia().getFacultad().getId());
		r.setNombreReporte("/portafolio/reporte_portafolio_facultad");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute(NOMBRE_REPORTE_SESION, r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}

	public String reporteGruposFacultad() {
		if (esCadenaVacia(estadosSeleccionados(selectedEstadosGrupos))) {
			mensajeError("Debe seleccionar al menos un estado de grupo para el reporte");
			return "";
		}

		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("fac", investigador.getDependencia().getFacultad().getId().toString());
		r.setNombreReporte("/grupo/reporteGruposFacultad");
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
		r.adicionarParametro("fechaActivo", String.valueOf(true));
		r.adicionarParametro("fechaMod", String.valueOf(true));
		r.adicionarParametro("ods", String.valueOf(true));
		r.adicionarParametro("infoAdic", String.valueOf(getMostrarContenidoAdicionalGrupos()));
		
		

		r.setFormato(ReporteBirt.FORMATO_XLS);
		sesion.setAttribute(NOMBRE_REPORTE_SESION, r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}

	public void generarGraficoAvalesFacultad() throws IOException {
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
			graficoAvalesFacultad = new DefaultStreamedContent();
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
			if (!indicador.getGraficoPastel()) {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 700, 600);
			} else {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			}
			graficoAvalesFacultad = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}
	}

	public void generarGraficoGruposFacultad() throws IOException {
		ocultarGraficos();
		reporteGrupos = true;

		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getRenderResponse()) {
			graficoGruposFacultad = new DefaultStreamedContent();
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
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 700, 700);
			} else {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			}
			graficoGruposFacultad = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}
	}

	public void generarGraficoDocentesFacultades() throws IOException {
		if (esCadenaVacia(tipoReporteInvestigadores)) {
			mensajeError("Debe seleccionar un indicador para que pueda ser generado.");
			return;
		}
		ocultarGraficos();
		reporteInvestigadores = true;

		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getRenderResponse()) {
			graficoDocentesFacultad = new DefaultStreamedContent();
			return;
		}
		JFreeChart jfreechart = generarConsultaIndicadorInvestigadores();

		if (jfreechart != null) {
			jfreechart = adicionarPieGrafico(jfreechart, obtenerSubtituloIndicador());
			jfreechart.getCategoryPlot().setRenderer(crearCategoryPlotRenderer(jfreechart));

			File chartFile = new File(NOMBRE_CHART_FILE);
			// facultades de minas, ciencias y medicina por cantidad de
			// deptos
			if ("3068".equals(facultadSeleccionada) || "2050".equals(facultadSeleccionada)
					|| "2056".equals(facultadSeleccionada)) {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 700, 700);
			} else {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			}
			graficoDocentesFacultad = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}
	}

	public void generarGraficoColeccionesFacultad() throws IOException {
		if (esCadenaVacia(tipoReporteBiodiversidad)) {
			mensajeError("Debe seleccionar un indicador para que pueda ser generado.");
			return;
		}
		ocultarGraficos();
		reporteBiodiversidad = true;

		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getRenderResponse()) {
			graficoColeccionesFacultad = new DefaultStreamedContent();
			return;
		}

		JFreeChart jfreechart = generarConsultaIndicadorBiodiversidad();

		if (jfreechart != null) {

			jfreechart = adicionarPieGrafico(jfreechart, obtenerSubtituloIndicador());
			generarPlotDefault(jfreechart);

			File chartFile = new File(NOMBRE_CHART_FILE);
			ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			graficoColeccionesFacultad = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}
	}

	public String obtenerNombreConvocatoria(String convocatoria) {
		String nombreConvocatoria = "";
		for (int i = 0; i < listaConvocatorias.size(); i++) {
			if (listaConvocatorias.get(i).getId().toString().equals(convocatoria)) {
				nombreConvocatoria = listaConvocatorias.get(i).getTitulo().substring(0, 1).toUpperCase()
						+ listaConvocatorias.get(i).getTitulo()
								.substring(1, listaConvocatorias.get(i).getTitulo().length()).toLowerCase();
				break;
			}
		}
		return nombreConvocatoria;
	}

	public void generarGraficoConvocatoriasFacultad() throws IOException {
		ocultarGraficos();
		reporteConvocatorias = true;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getRenderResponse()) {
			graficoConvocatoriasFacultad = new DefaultStreamedContent();
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

				plot.setLabelGenerator(generator);
				plot.setStartAngle(45);
				plot.setNoDataMessage("No se encontraron datos para el reporte");
			} else {
				jfreechart.getCategoryPlot().setRenderer(crearCategoryPlotRenderer(jfreechart));
			}

			File chartFile = new File(NOMBRE_CHART_FILE);
			if (("3068".equals(facultadSeleccionada) || "2050".equals(facultadSeleccionada))
					&& !indicador.getGraficoPastel()) {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 750, 750);
			} else {
				ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 600, 400);
			}
			graficoConvocatoriasFacultad = new DefaultStreamedContent(new FileInputStream(chartFile), FORMATO_IMAGEN);
		}
	}

	public String getNombreFacultad() {
		return nombreFacultad;
	}

	public void setNombreFacultad(String nombreFacultad) {
		this.nombreFacultad = nombreFacultad;
	}

	public Investigador getInvestigador() {
		return investigador;
	}

	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}

	public StreamedContent getGraficoAvalesFacultad() {
		return graficoAvalesFacultad;
	}

	public void setGraficoAvalesFacultad(StreamedContent graficoAvalesFacultad) {
		this.graficoAvalesFacultad = graficoAvalesFacultad;
	}

	public StreamedContent getGraficoGruposFacultad() {
		return graficoGruposFacultad;
	}

	public void setGraficoGruposFacultad(StreamedContent graficoGruposFacultad) {
		this.graficoGruposFacultad = graficoGruposFacultad;
	}

	public StreamedContent getGraficoDocentesFacultad() {
		return graficoDocentesFacultad;
	}

	public void setGraficoDocentesFacultad(StreamedContent graficoDocentesFacultad) {
		this.graficoDocentesFacultad = graficoDocentesFacultad;
	}

	public StreamedContent getGraficoColeccionesFacultad() {
		return graficoColeccionesFacultad;
	}

	public void setGraficoColeccionesFacultad(StreamedContent graficoColeccionesFacultad) {
		this.graficoColeccionesFacultad = graficoColeccionesFacultad;
	}

	public StreamedContent getGraficoConvocatoriasFacultad() {
		return graficoConvocatoriasFacultad;
	}

	public void setGraficoConvocatoriasFacultad(StreamedContent graficoConvocatoriasFacultad) {
		this.graficoConvocatoriasFacultad = graficoConvocatoriasFacultad;
	}

	public boolean isIncluirInvestigadores() {
		return incluirInvestigadores;
	}

	public void setIncluirInvestigadores(boolean incluirInvestigadores) {
		this.incluirInvestigadores = incluirInvestigadores;
	}
	
	public String consultarReporteProyectosMorosos() {
		ReporteBirt r = new ReporteBirt();
		//r.adicionarParametro("sedId", "1 2 3 4 5 6 7 8 9 0");
		//r.adicionarParametro("facultadId", "NO");
		return consultarReporteProyectosMorosos(r);
	}

}
