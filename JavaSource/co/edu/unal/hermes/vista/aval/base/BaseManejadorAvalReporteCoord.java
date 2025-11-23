package co.edu.unal.hermes.vista.aval.base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.ui.VerticalAlignment;

import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Reporte;
import co.edu.unal.hermes.modelo.SemilleroEstado;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * Clase que se utiliza como manejador de la pagina de consultas e indicadores
 * del hermes
 * 
 * @author Martha Liliana Correa O.
 * 
 */
public class BaseManejadorAvalReporteCoord extends ManejadorBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Date fechaInicio;
	protected Date fechaFin;
	protected int selItem;
	protected String anoReporte;
	protected String anoReporteAval;
	protected String[] selectedEstados;
	protected String[] selectedTipoAval;
	protected String[] selectedEstadosGrupos;
	protected String[] selectedModalidades;
	protected String modalidades = "";
	protected String headerGrafico = "";
	protected List<SelectItem> anosReporteItems;
	protected List<SelectItem> anosReporteItemsConvocatoria;
	protected Double sumaTotal;
	protected Double sumaTotalRegistros;
	protected String convocatoriaSeleccionada;
	protected String convocatoriaSeleccionadaMovilidad;
	protected static final String MENSAJE_NO_DATOS = "No se encontraron datos para el reporte";
	protected static final String MENSAJE_FUENTE = "Fuente: Sistema de Información Hermes";
	protected static final String FORMATO_IMAGEN = "image/png";
	protected static final String NOMBRE_CHART_FILE = "dynamichart";
	protected static final String NOMBRE_FUENTE = "Tahoma";
	protected static final String NOMBRE_EJE_VERTICAL = "Cantidad";
	protected static final String NOMBRE_REPORTE_SESION = "reporte";
	protected List<ConvocatoriaPadre> listaConvocatorias;
	protected List<ConvocatoriaPadre> listaConvocatoriasMovilidad;
	protected SelectItem[] listaConvocatoriasItem;
	protected String consultaDetalladaActual;
	protected String convocatoriaSel;
	protected SelectItem[] modalidadItem;
	protected String[] selectedConvocatorias;

	// General
	protected SelectItem[] listaSedesItem;
	protected List<Dependencia> listaSedes;

	protected List<Dependencia> listaFacultades;
	protected SelectItem[] listaFacultadesItem;

	protected List<Dependencia> listaDeptos;
	protected SelectItem[] listaDeptosItem;

	protected String tipoReporteAval;
	protected String tipoReporteGrupo;
	protected String tipoReporteInvestigadores;
	protected String tipoReporteBiodiversidad;
	protected String tipoReporteConvocatorias;
	protected String sedeSeleccionada;
	protected String sedeSeleccionadaAval;
	protected String facultadSeleccionada;
	protected String facultadSeleccionadaAval;
	protected String deptoSeleccionado;
	protected String deptoSeleccionadoAval;
	protected boolean mostrarOpcionSede;
	protected boolean mostrarOpcionFacultad;
	protected boolean mostrarOpcionDepto;
	protected boolean mostrarOpcionAno;
	protected boolean mostrarOpcionConvocatoria;
	protected boolean mostrarOpcionConvocatoriaMovilidad;

	protected SelectItem[] reportesConvocatoriasItems;
	protected SelectItem[] reportesAvalesItems;
	protected SelectItem[] reportesGruposItems;
	protected SelectItem[] reportesInvestigadoresItems;
	protected SelectItem[] reportesBiodiversidadItems;

	protected Reporte indicador;

	// movilidades
	protected SelectItem[] listaConvocatoriasMovilidadesItem;
	protected SelectItem[] listaModalidadesMovilidadesItem;
	private boolean convNacional;

	// ver graficos por nivel
	protected boolean reporteAvales;
	protected boolean reporteGrupos;
	protected boolean reporteInvestigadores;
	protected boolean reporteBiodiversidad;
	protected boolean reporteConvocatorias;

	protected SelectItem[] estadoItems;
	
	protected SelectItem[] estadoItemsTodos = { 
			new SelectItem(Aval.ENVIADO, "Enviado"),
			new SelectItem(Aval.DEVUELTO, "Devuelto para correcciones"),
			new SelectItem(Aval.REVISADO_FACULTAD, "Revisado facultad"),
			new SelectItem(Aval.REVISADO_DIRECCION, "Revisado dirección"),
			new SelectItem(Aval.REVISADO_VICERRECTORIA, "Revisado Vicerrectoría"),
			new SelectItem(Aval.ENVIADO_FACULTAD, "Enviado a Facultad"),
			new SelectItem(Aval.ENVIADO_SEDE, "Enviado a Sede"), 
			new SelectItem(Aval.REVISADO_DRE, "Revisado DRE"), 
			new SelectItem(Aval.DEVUELTO_RECTORIA, "Devuelto Rectoría"), 
			new SelectItem(Aval.REVISADO_RECTORIA, "Revisado Rectoría"),
			new SelectItem(Aval.ETICO_ENVIADO_CEPI, "Enviado CEPI"),
			new SelectItem(Aval.ETICO_APROBADO_CEPI, "Aprobado CEPI"),
			new SelectItem(Aval.ETICO_DEVUELTO_CEPI, "Devuelto CEPI"),
			new SelectItem(Aval.ETICO_NO_APROBADO_CEPI, "No aprobado CEPI"),
			new SelectItem(Aval.ETICO_ENVIADO_CESI, "Enviado CESI"),
			new SelectItem(Aval.ETICO_APROBADO_CESI, "Aprobado CESI"),
			new SelectItem(Aval.ETICO_NO_APROBADO_CESI, "No aprobado CESI"),
	};
	
	protected SelectItem[] estadoItemsNoEtico = { 
			new SelectItem(Aval.ENVIADO, "Enviado"),
			new SelectItem(Aval.DEVUELTO, "Devuelto para correcciones"),
			new SelectItem(Aval.REVISADO_FACULTAD, "Revisado facultad"),
			new SelectItem(Aval.REVISADO_DIRECCION, "Revisado dirección"),
			new SelectItem(Aval.REVISADO_VICERRECTORIA, "Revisado Vicerrectoría"),
			new SelectItem(Aval.ENVIADO_FACULTAD, "Enviado a Facultad"),
			new SelectItem(Aval.ENVIADO_SEDE, "Enviado a Sede"), 
			new SelectItem(Aval.REVISADO_DRE, "Revisado DRE"), 
			new SelectItem(Aval.DEVUELTO_RECTORIA, "Devuelto Rectoría"), 
			new SelectItem(Aval.REVISADO_RECTORIA, "Revisado Rectoría"),
		};
	
	SelectItem[] estadosItemsEtico = {
			new SelectItem(Aval.ETICO_ENVIADO_CEPI, "Enviado CEPI"),
			new SelectItem(Aval.ETICO_APROBADO_CEPI, "Aprobado CEPI"),
			new SelectItem(Aval.ETICO_DEVUELTO_CEPI, "Devuelto CEPI"),
			new SelectItem(Aval.ETICO_NO_APROBADO_CEPI, "No aprobado CEPI"),
			new SelectItem(Aval.ETICO_ENVIADO_CESI, "Enviado CESI"),
			new SelectItem(Aval.ETICO_APROBADO_CESI, "Aprobado CESI"),
			new SelectItem(Aval.ETICO_NO_APROBADO_CESI, "No aprobado CESI")
		};
	
	protected SelectItem[] estadoItemsGrupos = { new SelectItem("I", "Ingresando"),
			new SelectItem("S", "Propuesto y solicitud de aval"), new SelectItem("N", "Inactivo"),
			new SelectItem("A", "Activo"), new SelectItem("NA", "No avalado"), new SelectItem("D", "Disuelto") };

	protected SelectItem[] tipoAvalItems = {
			new SelectItem(Aval.TIPO_CENTRO_INVESTIGACION, "Laboratorios, centros o Institutos de Investigación"),
			new SelectItem(Aval.TIPO_GRUPO_INVESTIGACION, "Grupo de Investigación"),
			new SelectItem(Aval.TIPO_INVESTIGADOR_INDEPENDIENTE, "Investigador independiente o Becas doctorales"),
			new SelectItem(Aval.TIPO_MOVILIDAD, "Movilidad / Evento"),
			new SelectItem(Aval.TIPO_REGALIAS, "Proyecto - Fondo de CT+I REGALIAS"),
			new SelectItem(Aval.TIPO_PAED_REGALIAS, "Proyecto - PAED CTeI - Regalías"),
			new SelectItem(Aval.TIPO_INVESTIGACION, "Proyecto de Investigación"),
			new SelectItem(Aval.TIPO_INVESTIGACION_CONT, "Proyecto de Investigación - Contrapartida"),
			new SelectItem(Aval.TIPO_JORNADA_DOCENTE, "Proyecto de Jornada Docente"),
			new SelectItem(Aval.TIPO_CONVOCATORIA_REGALIAS, "Aval etapa presentación proyectos Convocatorias SGR- Regalías"),
			new SelectItem(Aval.TIPO_REGALIAS_VERIF_REQ, "Aval etapa verificación de requisitos SGR - Regalías")
//			new SelectItem(Aval.TIPO_ETICO, "Ético")
		};

	protected Boolean[] columns = { true, true, true, true, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true, true, true, true, true, true };
	protected Boolean[] columnsGrupos = { true, true, true, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true, true};
	protected Boolean[] columnsMovilidades = { true, true, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true };
	protected Boolean[] columnsSemilleros = { true, true, true, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true };

	protected SelectItem[] estadosSemilleros;
	protected SelectItem[] contenidoAdicionalSemilleros;
	protected String mostrarContenidoAdicional;
	protected String[] selectedEstadosSemilleros;

	protected SelectItem[] contenidoAdicionalGrupos;
	protected String mostrarContenidoAdicionalGrupos;	
	Boolean esRolRevisionAvalEtico = false;
	public Boolean mostrarCamposAvalEtico = false;

	public BaseManejadorAvalReporteCoord() {
		String consultaInfoAdic = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='LISTA_CONT_ADIC_SEMILLEROS' order by dd.estado";
		contenidoAdicionalSemilleros = crearListaItems(
				servicioGeneral.obtenerObjetos(DominioDetalle.class, consultaInfoAdic));
		List<SemilleroEstado> estadosSemilleros = servicioGeneral.obtenerObjetos(SemilleroEstado.class,
				"select se from SemilleroEstado se order by se.id");
		setEstadosSemilleros(new SelectItem[estadosSemilleros.size()]);
		Integer idx = 0;
		for (SemilleroEstado se : estadosSemilleros) {
			getEstadosSemilleros()[idx] = new SelectItem(se.getId().toString(), se.getNombre());
			idx++;
		}
		String consultaInfoAdicGrupos = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='LISTA_CONT_ADIC_GRUPOS' order by dd.estado";
		contenidoAdicionalGrupos = crearListaItems(
				servicioGeneral.obtenerObjetos(DominioDetalle.class, consultaInfoAdicGrupos));
		cargarOpcionesFacultad();
		
		estadoItems = new SelectItem[0];
		estadoItems = estadoItemsNoEtico;
		
		esRolRevisionAvalEtico = (Boolean) sesion.getAttribute("esRolRevisionAvalEtico");
		if(esNulo(esRolRevisionAvalEtico)) esRolRevisionAvalEtico = false;
		
		validarCamposAvalEtico();
		consultarConvocatoriasReporte();
	}
	
	public void validarCamposAvalEtico() {
		if(esRolRevisionAvalEtico) {
			cargarListasAvalEtico();
			Boolean columnsAuxEtico[] = { true, true, true, true, false, false, false, false, false, true, true, true, true, true,
					false, false, false, false, false, false, false, true, true, true, true, true, true, true };
			columns = columnsAuxEtico;
			
			String selectedTipoAvalAux[] = {"ETICO"};
			selectedTipoAval = selectedTipoAvalAux;
			
			mostrarCamposAvalEtico = true;
		}
	}
	
	public void cargarListasAvalEtico() {
		estadoItems = new SelectItem[0];
		estadoItems = estadosItemsEtico;
		
		tipoAvalItems = new SelectItem[0];
		SelectItem[] tiposAvalEtico = {
			new SelectItem(Aval.TIPO_ETICO, "Aval Ético"),
		};
		tipoAvalItems = tiposAvalEtico;
	}
	
	public void mostrarCamposAvalEtico() {		
		//Estados
		if(mostrarCamposAvalEtico) {
			if(esRolRevisionAvalEtico)
				estadoItems = estadosItemsEtico;
			else
				estadoItems = estadoItemsTodos;
		} else {
			if(esRolRevisionAvalEtico)
				estadoItems = estadosItemsEtico;
			else
				estadoItems = estadoItemsNoEtico;
		}
		
		System.out.println("mostrarCamposAvalEtico: " + mostrarCamposAvalEtico);
		
		//Campos
		Boolean columnsAuxNoEtico[] = { true, true, true, true, true, true, true, true, true, true, true, true, true, true,
				true, true, true, true, true, true, true, false, false, false, false, false, false, false };
		
		Boolean columnsAuxTodos[] = { true, true, true, true, true, true, true, true, true, true, true, true, true, true,
				true, true, true, true, true, true, true, true, true, true, true, true, true, true };
		
		if(mostrarCamposAvalEtico)
			columns = columnsAuxTodos;
		else
			columns = columnsAuxNoEtico;
	}

	public void desmarcarTodoSemilleros() {
		for (int i = 0; i < columnsSemilleros.length; i++) {
			columnsSemilleros[i] = false;
		}
	}

	public void marcarTodoSemilleros() {
		for (int i = 0; i < columnsSemilleros.length; i++) {
			columnsSemilleros[i] = true;
		}
	}

	public void cargarListadoIndicadoresConvocatorias(String nivel, Long categoria) {
		List<Reporte> listaIndicadoresConvocatorias = servicioGeneral.obtenerListaIndicadores(nivel, categoria);
		if (!esListaVacia(listaIndicadoresConvocatorias)) {
			reportesConvocatoriasItems = new SelectItem[listaIndicadoresConvocatorias.size()];
			int i = 0;
			for (Iterator<Reporte> ic = listaIndicadoresConvocatorias.iterator(); ic.hasNext();) {
				Reporte r = ic.next();
				reportesConvocatoriasItems[i] = new SelectItem(r.getId().toString(), r.getNombreExterno());
				i++;
			}
		}
	}

	public void cargarListadoIndicadoresAvales(String nivel, Long categoria) {
		List<Reporte> listaIndicadoresAvales = servicioGeneral.obtenerListaIndicadores(nivel, categoria);
		if (!esListaVacia(listaIndicadoresAvales)) {
			reportesAvalesItems = new SelectItem[listaIndicadoresAvales.size()];
			int i = 0;
			for (Iterator<Reporte> ic = listaIndicadoresAvales.iterator(); ic.hasNext();) {
				Reporte r = ic.next();
				reportesAvalesItems[i] = new SelectItem(r.getId().toString(), r.getNombreExterno());
				i++;
			}
		}
	}

	public void cargarListadoIndicadoresGrupos(String nivel, Long categoria) {
		List<Reporte> listaIndicadoresGrupos = servicioGeneral.obtenerListaIndicadores(nivel, categoria);
		if (!esListaVacia(listaIndicadoresGrupos)) {
			reportesGruposItems = new SelectItem[listaIndicadoresGrupos.size()];
			int i = 0;
			for (Iterator<Reporte> ic = listaIndicadoresGrupos.iterator(); ic.hasNext();) {
				Reporte r = ic.next();
				reportesGruposItems[i] = new SelectItem(r.getId().toString(), r.getNombreExterno());
				i++;
			}
		}
	}

	public void cargarListadoIndicadoresInvestigadores(String nivel, Long categoria) {
		List<Reporte> listaIndicadoresInvestigadores = servicioGeneral.obtenerListaIndicadores(nivel, categoria);
		if (!esListaVacia(listaIndicadoresInvestigadores)) {
			reportesInvestigadoresItems = new SelectItem[listaIndicadoresInvestigadores.size()];
			int i = 0;
			for (Iterator<Reporte> ic = listaIndicadoresInvestigadores.iterator(); ic.hasNext();) {
				Reporte r = ic.next();
				reportesInvestigadoresItems[i] = new SelectItem(r.getId().toString(), r.getNombreExterno());
				i++;
			}
		}
	}

	public void cargarListadoIndicadoresColecciones(String nivel, Long categoria) {
		List<Reporte> listaIndicadoresColecciones = servicioGeneral.obtenerListaIndicadores(nivel, categoria);
		if (!esListaVacia(listaIndicadoresColecciones)) {
			reportesBiodiversidadItems = new SelectItem[listaIndicadoresColecciones.size()];
			int i = 0;
			for (Iterator<Reporte> ic = listaIndicadoresColecciones.iterator(); ic.hasNext();) {
				Reporte r = ic.next();
				reportesBiodiversidadItems[i] = new SelectItem(r.getId().toString(), r.getNombreExterno());
				i++;
			}
		}
	}

	private void cargarOpcionesSedes() {
		if (listaSedesItem != null && listaSedesItem.length > 0) {
			return;
		} else {
			listaSedes = (List<Dependencia>) servicioGeneral.obtenerSedes();
			listaSedesItem = new SelectItem[listaSedes.size()];
			for (int i = 0; i < listaSedes.size(); i++) {
				Dependencia sede = (Dependencia) listaSedes.get(i);
				String nombreSede = sede.getNombre().substring(0, 1).toUpperCase()
						+ sede.getNombre().substring(1, sede.getNombre().length());
				listaSedesItem[i] = new SelectItem(sede.getId(), nombreSede);
			}
		}
	}

	public void cargarOpcionesFacultad() {
		if(sedeSeleccionada!=null) {
		Dependencia sede = new Dependencia();
		sede.setId(sedeSeleccionada);
		listaFacultades = (List<Dependencia>) servicioGeneral.obtenerFacultades(sede);
		listaFacultadesItem = new SelectItem[listaFacultades.size()];
		for (int i = 0; i < listaFacultades.size(); i++) {
			Dependencia facultad = (Dependencia) listaFacultades.get(i);
			listaFacultadesItem[i] = new SelectItem(facultad.getId(), facultad.getNombre());
		}
		}
	}

	private void cargarOpcionesDepartamentos() {
		if (listaDeptosItem != null && listaDeptosItem.length > 0) {
			return;
		} else {
			listaDeptos = new ArrayList<Dependencia>();
			String dpnsql = "select e from Dependencia e where e.estado='A' and e.esDepartamento='Y' and e.nombre not like 'Comite%' and e.nombre "
					+ "not like 'Fondo%' and e.nombre not like 'Centro%' and e.facultad.id = '" + facultadSeleccionada
					+ "' order by e.nombre";

			setListaDeptos(servicioGeneral.obtenerObjetos(Dependencia.class, dpnsql));
			setListaDeptosItem(new SelectItem[getListaDeptos().size()]);
			for (int i = 0; i < getListaDeptos().size(); i++) {
				Dependencia depto = (Dependencia) getListaDeptos().get(i);
				getListaDeptosItem()[i] = new SelectItem(depto.getId(), depto.getNombre());
			}
		}
	}

	public void consultarOpciones(String tipoReporte) {
		desactivarOpciones();
		ocultarGraficos();
		if (!esCadenaVacia(tipoReporte)) {
			indicador = consultarReporte(Long.parseLong(tipoReporte));
			if (indicador != null) {
				if (indicador.getRequiereAno()) {
					anosParaReporteConvocatorias();
					anosParaReporteAval();
					mostrarOpcionAno = true;
				}
				if (indicador.getRequiereConvocatoriaPry()) {
					consultarConvocatoriasReporte();
					mostrarOpcionConvocatoria = true;
				}
				if (indicador.getRequiereConvocatoriaMov()) {
					consultarConvocatoriasMovilidades();
					mostrarOpcionConvocatoriaMovilidad = true;
				}
				if (indicador.getRequiereSede()) {
					cargarOpcionesSedes();
					mostrarOpcionSede = true;
				}
				if (indicador.getRequiereFacultad()) {
					cargarOpcionesFacultad();
					mostrarOpcionFacultad = true;
				}
				if (indicador.getRequiereUab()) {
					cargarOpcionesDepartamentos();
					mostrarOpcionDepto = true;
				}
			}
		}
	}

	public Reporte consultarReporte(Long id) {
		return servicioGeneral.obtenerReporte(id);
	}

	public void desactivarOpciones() {
		mostrarOpcionAno = false;
		mostrarOpcionConvocatoria = false;
		mostrarOpcionConvocatoriaMovilidad = false;
		mostrarOpcionSede = false;
		mostrarOpcionFacultad = false;
		mostrarOpcionDepto = false;
		convocatoriaSeleccionada = "";
		deptoSeleccionado = "";
		deptoSeleccionadoAval = "";
		anoReporte = "";
		anoReporteAval = "";
		consultaDetalladaActual = "";
	}

	public String reemplazarParametrosConsultaConvocatoria(String sql) {
		String newSql = sql.replaceAll("<<CONVOCATORIA>>",
				esCadenaVacia(convocatoriaSeleccionada) ? "" : convocatoriaSeleccionada);
		newSql = newSql.replaceAll("<<SEDE>>", esCadenaVacia(sedeSeleccionada) ? "" : sedeSeleccionada);
		newSql = newSql.replaceAll("<<FACULTAD>>", esCadenaVacia(facultadSeleccionada) ? "" : facultadSeleccionada);
		newSql = newSql.replaceAll("<<DEPARTAMENTO>>", esCadenaVacia(deptoSeleccionado) ? "" : deptoSeleccionado);
		newSql = newSql.replaceAll("<<ANO>>", esCadenaVacia(anoReporte) ? "" : anoReporte);
		newSql = newSql.replaceAll("<<MOD_BIODIVERSIDAD>>", MODALIDADES_PERMISO_CONTRATO);
		newSql = newSql.replaceAll("<<DOCUMENTOS_PRUEBA>>", DOCUMENTOS_PRUEBAS_DESARROLLO);
		return newSql;
	}

	public JFreeChart generarConsultaIndicadorConvocatoria() {

		JFreeChart jfreechart = null;
		String sql = reemplazarParametrosConsultaConvocatoria(
				esCadenaVacia(indicador.getConsultaIndicador()) ? "" : indicador.getConsultaIndicador());
		consultaDetalladaActual = reemplazarParametrosConsultaConvocatoria(
				esCadenaVacia(indicador.getConsultaDetalle()) ? "" : indicador.getConsultaDetalle());
		headerGrafico = indicador.getTituloGrafico();
		if (indicador.getRequiereConvocatoriaPry()) {
			headerGrafico = headerGrafico + obtenerNombreConvocatoria(
					esCadenaVacia(convocatoriaSeleccionada) ? "" : convocatoriaSeleccionada);
		} else if (indicador.getRequiereConvocatoriaMov()) {
			headerGrafico = headerGrafico + obtenerNombreConvocatoriaMovilidad(
					esCadenaVacia(convocatoriaSeleccionada) ? "" : convocatoriaSeleccionada);
		}

		if (indicador.getRequiereSede()) {
			headerGrafico = headerGrafico + " en la sede "
					+ obtenerNombreSede(esCadenaVacia(sedeSeleccionada) ? "" : sedeSeleccionada);
		}

		if (indicador.getRequiereFacultad()) {
			headerGrafico = headerGrafico + " en la "
					+ obtenerNombreFacultad(esCadenaVacia(facultadSeleccionada) ? "" : facultadSeleccionada);
		}

		if (indicador.getRequiereUab()) {
			headerGrafico = headerGrafico + " en "
					+ obtenerNombreDepto(esCadenaVacia(deptoSeleccionado) ? "" : deptoSeleccionado);
		}

		if (indicador.getRequiereAno()) {
			headerGrafico = headerGrafico + " en el año " + anoReporte;
		}
		return generarGrafico(jfreechart, sql);
	}

	public String reemplazarParametrosConsultaAvales(String sql) {
		String newSql = sql.replaceAll("<<SEDE>>", esCadenaVacia(sedeSeleccionadaAval) ? "" : sedeSeleccionadaAval);
		newSql = newSql.replaceAll("<<FACULTAD>>",
				esCadenaVacia(facultadSeleccionadaAval) ? "" : facultadSeleccionadaAval);
		newSql = newSql.replaceAll("<<DEPARTAMENTO>>",
				esCadenaVacia(deptoSeleccionadoAval) ? "" : deptoSeleccionadoAval);
		newSql = newSql.replaceAll("<<ANO>>", esCadenaVacia(anoReporteAval) ? "" : anoReporteAval);
		newSql = newSql.replaceAll("<<DOCUMENTOS_PRUEBA>>", DOCUMENTOS_PRUEBAS_DESARROLLO);
		return newSql;
	}

	public JFreeChart generarConsultaIndicadorAval() {

		JFreeChart jfreechart = null;
		String sql = reemplazarParametrosConsultaAvales(
				esCadenaVacia(indicador.getConsultaIndicador()) ? "" : indicador.getConsultaIndicador());
		consultaDetalladaActual = reemplazarParametrosConsultaAvales(
				esCadenaVacia(indicador.getConsultaDetalle()) ? "" : indicador.getConsultaDetalle());

		headerGrafico = indicador.getTituloGrafico();

		if (indicador.getRequiereSede()) {
			headerGrafico = headerGrafico + " en la sede "
					+ obtenerNombreSede(esCadenaVacia(sedeSeleccionadaAval) ? "" : sedeSeleccionadaAval);
		}

		if (indicador.getRequiereFacultad()) {
			headerGrafico = headerGrafico + " en la "
					+ obtenerNombreFacultad(esCadenaVacia(facultadSeleccionadaAval) ? "" : facultadSeleccionadaAval);
		}

		if (indicador.getRequiereUab()) {
			headerGrafico = headerGrafico + " en "
					+ obtenerNombreDepto(esCadenaVacia(deptoSeleccionadoAval) ? "" : deptoSeleccionadoAval);
		}

		if (indicador.getRequiereAno()) {
			headerGrafico = headerGrafico + " en el año " + anoReporteAval;
		}
		return generarGrafico(jfreechart, sql);
	}

	public JFreeChart generarConsultaIndicadorGrupo() {

		JFreeChart jfreechart = null;
		String sql = reemplazarParametrosConsultaGrupos(
				esCadenaVacia(indicador.getConsultaIndicador()) ? "" : indicador.getConsultaIndicador());
		consultaDetalladaActual = reemplazarParametrosConsultaGrupos(
				esCadenaVacia(indicador.getConsultaDetalle()) ? "" : indicador.getConsultaDetalle());

		headerGrafico = indicador.getTituloGrafico();
		return generarGrafico(jfreechart, sql);
	}

	public String reemplazarParametrosConsultaGrupos(String sql) {
		String newSql = sql.replaceAll("<<SEDE>>", esCadenaVacia(sedeSeleccionada) ? "" : sedeSeleccionada);
		newSql = newSql.replaceAll("<<FACULTAD>>", esCadenaVacia(facultadSeleccionada) ? "" : facultadSeleccionada);
		return newSql;
	}

	public String reemplazarParametrosConsultaInvestigadores(String sql) {
		String newSql = sql.replaceAll("<<MOD_BIODIVERSIDAD>>", MODALIDADES_PERMISO_CONTRATO);
		newSql = newSql.replaceAll("<<SEDE>>", esCadenaVacia(sedeSeleccionada) ? "" : sedeSeleccionada);
		newSql = newSql.replaceAll("<<FACULTAD>>", esCadenaVacia(facultadSeleccionada) ? "" : facultadSeleccionada);
		newSql = newSql.replaceAll("<<DOCUMENTOS_PRUEBA>>", DOCUMENTOS_PRUEBAS_DESARROLLO);
		return newSql;
	}

	public JFreeChart generarConsultaIndicadorInvestigadores() {

		JFreeChart jfreechart = null;
		String sql = reemplazarParametrosConsultaInvestigadores(
				esCadenaVacia(indicador.getConsultaIndicador()) ? "" : indicador.getConsultaIndicador());
		consultaDetalladaActual = reemplazarParametrosConsultaInvestigadores(
				esCadenaVacia(indicador.getConsultaDetalle()) ? "" : indicador.getConsultaDetalle());

		headerGrafico = indicador.getTituloGrafico();
		return generarGrafico(jfreechart, sql);
	}

	public String reemplazarParametrosConsultaBiodiversidad(String sql) {
		String newSql = sql.replaceAll("<<SEDE>>", esCadenaVacia(sedeSeleccionada) ? "" : sedeSeleccionada);
		newSql = newSql.replaceAll("<<FACULTAD>>", esCadenaVacia(facultadSeleccionada) ? "" : facultadSeleccionada);
		newSql = newSql.replaceAll("<<DOCUMENTOS_PRUEBA>>", DOCUMENTOS_PRUEBAS_DESARROLLO);
		return newSql;
	}

	public JFreeChart generarConsultaIndicadorBiodiversidad() {

		JFreeChart jfreechart = null;
		String sql = reemplazarParametrosConsultaBiodiversidad(
				esCadenaVacia(indicador.getConsultaIndicador()) ? "" : indicador.getConsultaIndicador());
		consultaDetalladaActual = reemplazarParametrosConsultaBiodiversidad(
				esCadenaVacia(indicador.getConsultaDetalle()) ? "" : indicador.getConsultaDetalle());
		headerGrafico = indicador.getTituloGrafico();
		return generarGrafico(jfreechart, sql);
	}

	public JFreeChart generarGrafico(JFreeChart jfreechart, String sql) {
		if (indicador.getGraficoPastel()) {
			jfreechart = ChartFactory.createPieChart3D(headerGrafico, createDataset(sql), true, true, true);
		} else {
			jfreechart = ChartFactory.createBarChart3D(headerGrafico, indicador.getEjeHorizontal(), NOMBRE_EJE_VERTICAL,
					createDatasetBarras(sql), PlotOrientation.VERTICAL, true, false, false);
		}
		return jfreechart;
	}

	public boolean validarParametrosReporteConvocatoria() {
		if ((indicador.getRequiereConvocatoriaPry() || indicador.getRequiereConvocatoriaMov())
				&& esCadenaVacia(convocatoriaSeleccionada)) {
			mensajeError("Debe seleccionar una convocatoria para la generación del indicador");
			return false;
		}
		if (indicador.getRequiereSede() && esCadenaVacia(sedeSeleccionada)) {
			mensajeError("Debe seleccionar una sede para la generación del indicador");
			return false;
		}
		if (indicador.getRequiereAno() && esCadenaVacia(anoReporte)) {
			mensajeError("Debe seleccionar un año para la generación del indicador");
			return false;
		}
		if (indicador.getRequiereFacultad() && esCadenaVacia(facultadSeleccionada)) {
			mensajeError("Debe seleccionar una facultad para la generación del indicador");
			return false;
		}
		if (indicador.getRequiereUab()) {
			mensajeError("Debe seleccionar una dependencia para la generación del indicador");
			return false;
		}
		return true;
	}

	public boolean validarParametrosReporteAval() {
		if (indicador.getRequiereSede() && esCadenaVacia(sedeSeleccionadaAval)) {
			mensajeError("Debe seleccionar una sede para la generación del indicador");
			return false;
		}
		if (indicador.getRequiereAno() && esCadenaVacia(anoReporteAval)) {
			mensajeError("Debe seleccionar un año para la generación del indicador");
			return false;
		}
		if (indicador.getRequiereFacultad() && esCadenaVacia(facultadSeleccionadaAval)) {
			mensajeError("Debe seleccionar una facultad para la generación del indicador");
			return false;
		}
		if (indicador.getRequiereUab() && esCadenaVacia(deptoSeleccionadoAval)) {
			mensajeError("Debe seleccionar una dependencia para la generación del indicador");
			return false;
		}
		return true;
	}

	public String obtenerSubtituloIndicador() {

		String subTitulo = indicador.getTituloRegistrosTotales();
		if (indicador.getTotalMoneda()) {
			DecimalFormat formateador = new DecimalFormat("###,###.##");
			subTitulo = subTitulo + formateador.format(sumaTotal);
		} else {
			subTitulo = subTitulo + sumaTotal.intValue();
		}
		return subTitulo;
	}

	public String obtenerNombreConvocatoria(String convocatoria) {

		String nombreConvocatoria = "";
		if (!esListaVacia(listaConvocatorias)) {
			for (int i = 0; i < listaConvocatorias.size(); i++) {
				if (listaConvocatorias.get(i).getId().toString().equals(convocatoria)) {
					nombreConvocatoria = listaConvocatorias.get(i).getTitulo().substring(0, 1).toUpperCase()
							+ listaConvocatorias.get(i).getTitulo()
									.substring(1, listaConvocatorias.get(i).getTitulo().length()).toLowerCase();
					break;
				}
			}
		}
		return nombreConvocatoria;
	}

	public String obtenerNombreConvocatoriaMovilidad(String convocatoria) {

		String nombreConvocatoria = "";
		if (!esListaVacia(listaConvocatoriasMovilidad)) {
			for (int i = 0; i < listaConvocatoriasMovilidad.size(); i++) {
				if (listaConvocatoriasMovilidad.get(i).getId().toString().equals(convocatoria)) {
					nombreConvocatoria = listaConvocatoriasMovilidad.get(i).getTitulo().substring(0, 1).toUpperCase()
							+ listaConvocatoriasMovilidad.get(i).getTitulo()
									.substring(1, listaConvocatoriasMovilidad.get(i).getTitulo().length())
									.toLowerCase();
					break;
				}
			}
		}

		return nombreConvocatoria;
	}

	public String obtenerNombreSede(String sedeSeleccionada) {
		String nombreSede = "";
		for (int i = 0; i < listaSedes.size(); i++) {
			if (listaSedes.get(i).getId().equals(sedeSeleccionada)) {
				nombreSede = listaSedes.get(i).getNombre().substring(0, 1).toUpperCase()
						+ listaSedes.get(i).getNombre().substring(1, listaSedes.get(i).getNombre().length());
				break;
			}
		}
		return nombreSede;
	}

	public String obtenerNombreFacultad(String idFacultad) {
		String nombreFacultad = "";
		for (int i = 0; i < listaFacultades.size(); i++) {
			if (listaFacultades.get(i).getId().equals(idFacultad)) {
				nombreFacultad = listaFacultades.get(i).getNombre();
				break;
			}
		}
		return nombreFacultad;
	}

	public String obtenerNombreDepto(String idDepto) {
		String nombreDepto = "";
		for (int i = 0; i < getListaDeptos().size(); i++) {
			if (getListaDeptos().get(i).getId().equals(idDepto)) {
				nombreDepto = getListaDeptos().get(i).getNombre();
				break;
			}
		}
		return nombreDepto;
	}

	public void opcionesReporteConvocatoria() {
		consultarOpciones(tipoReporteConvocatorias);
	}

	public void opcionesReporteAval() {
		consultarOpciones(tipoReporteAval);
	}

	public void opcionesReporteGrupo() {
		consultarOpciones(tipoReporteGrupo);
	}

	public void opcionesReporteInvestigador() {
		consultarOpciones(tipoReporteInvestigadores);
	}

	public void opcionesReporteBiodiversidad() {
		consultarOpciones(tipoReporteBiodiversidad);
	}

	public void ocultarGraficos() {
		reporteAvales = false;
		reporteInvestigadores = false;
		reporteGrupos = false;
		reporteBiodiversidad = false;
		reporteConvocatorias = false;
	}

	public class ChartDrawingSupplier extends DefaultDrawingSupplier {

		private static final long serialVersionUID = -1826859992461546297L;
		public Paint[] paintSequence;
		public int paintIndex;
		public int fillPaintIndex;

		{
			paintSequence = new Paint[] { new Color(5, 48, 97), new Color(33, 102, 172), new Color(67, 147, 195),
					new Color(146, 197, 222), new Color(209, 229, 240), new Color(247, 247, 247),
					new Color(253, 219, 199), new Color(244, 165, 130), new Color(214, 96, 77), new Color(178, 24, 43),
					new Color(103, 0, 31) };

		}

		@Override
		public Paint getNextFillPaint() {
			Paint result = paintSequence[fillPaintIndex % paintSequence.length];
			fillPaintIndex++;
			return result;
		}

		@Override
		public Paint getNextPaint() {
			Paint result = paintSequence[paintIndex % paintSequence.length];
			paintIndex++;
			return result;
		}
	}

	public void anosParaReporteAval() {

		anosReporteItems = new ArrayList<SelectItem>();
		Calendar c = Calendar.getInstance();

		int anoActual = c.get(Calendar.YEAR);

		for (int i = 2010; i <= anoActual; i++) {
			anosReporteItems.add(new SelectItem(String.valueOf(i), String.valueOf(i)));

		}
	}

	public void anosParaReporteConvocatorias() {

		anosReporteItemsConvocatoria = new ArrayList<SelectItem>();
		Calendar c = Calendar.getInstance();

		int anoActual = c.get(Calendar.YEAR);

		for (int i = 2006; i <= anoActual; i++) {
			anosReporteItemsConvocatoria.add(new SelectItem(String.valueOf(i), String.valueOf(i)));

		}
	}

	public PieDataset createDataset(String sql) {
		List<Map> listaReporte2;
		listaReporte2 = servicioGeneral.obtenerMapa(sql);

		Integer registros = listaReporte2.size();
		Integer columnas = 2; // series

		String[] datos = new String[registros * columnas];
		int k = 0;
		for (Map<String, String> mapa : listaReporte2) {
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
			sumaTotal += valorNum;
		}
		return dataset;
	}

	public CategoryDataset createDatasetBarras(String sql) {
		List<Map> listaReporte2;
		listaReporte2 = servicioGeneral.obtenerMapa(sql);

		Double registros = (double) listaReporte2.size();
		Integer columnas = 2; // series

		String[] datos = new String[(int) (registros * columnas)];
		int k = 0;
		for (Map<String, String> mapa : listaReporte2) {
			for (Object value : mapa.values()) {
				String cadena = (String) value;
				datos[k] = cadena;
				k++;
			}
		}
		sumaTotalRegistros = registros;
		sumaTotal = 0D;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int size = datos.length;
		for (int j = 0; j < size; j += columnas) {
			Double valorNum = new Double(datos[j + 1]);
			dataset.setValue(valorNum, datos[j], datos[j]);
			sumaTotal += valorNum;
		}
		return dataset;
	}

	public JFreeChart adicionarPieGrafico(JFreeChart jfreechart, String subTitulo) {
		jfreechart.addSubtitle(new TextTitle(MENSAJE_FUENTE, new Font(NOMBRE_FUENTE, Font.ITALIC, 12), Color.black,
				RectangleEdge.BOTTOM, HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM,
				RectangleInsets.ZERO_INSETS));

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
		String fechaGeneracion = sdf.format(new Date());

		jfreechart.addSubtitle(new TextTitle("Fecha de generación: " + fechaGeneracion,
				new Font(NOMBRE_FUENTE, Font.ITALIC, 12), Color.black, RectangleEdge.BOTTOM, HorizontalAlignment.RIGHT,
				VerticalAlignment.BOTTOM, RectangleInsets.ZERO_INSETS));

		jfreechart.addSubtitle(
				new TextTitle(subTitulo, new Font(NOMBRE_FUENTE, Font.ITALIC, 12), Color.black, RectangleEdge.BOTTOM,
						HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM, RectangleInsets.ZERO_INSETS));

		return jfreechart;
	}

	public StackedBarRenderer crearCategoryPlotRenderer(JFreeChart jfreechart) {
		CategoryPlot plot = (CategoryPlot) jfreechart.getCategoryPlot();

		CategoryAxis axis = plot.getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 8.0));

		plot.setNoDataMessage("No se encontraron datos para el reporte");
		plot.setBackgroundPaint(Color.white);

		StackedBarRenderer renderer = new StackedBarRenderer(false);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBasePositiveItemLabelPosition(
				new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER));
		return renderer;
	}

	public void generarPlotDefault(JFreeChart jfreechart) {
		PiePlot plot = (PiePlot) jfreechart.getPlot();
		plot.setDrawingSupplier(new ChartDrawingSupplier());
		plot.setBackgroundPaint(Color.white);
		PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0} = {1} ({2})",
				new DecimalFormat("0"), new DecimalFormat("0.0%"));
		plot.setLabelGenerator(generator);
		plot.setStartAngle(45);
		plot.setNoDataMessage(MENSAJE_NO_DATOS);
	}

	public void consultarConvocatoriasReporte() {

		setListaConvocatorias(new ArrayList<ConvocatoriaPadre>());

		String sql = "select distinct cp from ConvocatoriaPadre cp, Convocatoria c where cp.id = c.padre.id and c.id in "
				+ "(select distinct p.modalidad.id from Proyecto p) and cp.estadoConvocatoria.id not in ('O','CR') and "
				+ "cp.id <> '257' and " // Becas doctorados colciencias
				+ "cp.id <> '248' and " // Registro de informes beca doctorados
				+ "cp.id <> '251' and " // Digitalizacion de libros
				+ "cp.id <> '0' and " // Jornada docente
				+ "cp.id <> '11' and " // Registro unico de proyectos
				+ "cp.id <> '310' and " // eSCUELA DE PENSAMIENTO UNIVERSITARIO
				+ "cp.id <> '205' and " // Registro de idea apoyo regalias
				+ "cp.id <> '17' and " // Proyectos aprobados externos
				+ "cp.id <> '99' and " // jOVENES INVESTIGADORES ATLANTICO
				+ "cp.id <> '210' and " // USO DE EQUIPOS DE LABORATORIOS
				+ "cp.id <> '313' and " // PROYECTO EN CUNDINAMARCA
				+ "cp.id <> '202' " // DOTACION DE EQUIPOS DE LABORATORIOS
				+ "order by cp.titulo asc";

		listaConvocatorias = (ArrayList<ConvocatoriaPadre>) servicioGeneral.obtenerObjetos(ConvocatoriaPadre.class,
				sql);
		if (!esListaVacia(listaConvocatorias)) {
			setListaConvocatoriasItem(new SelectItem[listaConvocatorias.size()]);
			for (int i = 0; i < listaConvocatorias.size(); i++) {
				ConvocatoriaPadre dd = (ConvocatoriaPadre) listaConvocatorias.get(i);
				getListaConvocatoriasItem()[i] = new SelectItem(dd.getId().toString(),
						dd.getTitulo().substring(0, 1).toUpperCase()
								+ dd.getTitulo().substring(1, dd.getTitulo().length()).toLowerCase());
			}
		}
	}

	public void consultarConvocatoriasMovilidades() {
		String sql = "select distinct cp from ConvocatoriaPadre cp, Convocatoria c where cp.id = c.padre.id "
				+ "and cp.id != 360 and (c.id in "
				+ "(select distinct de.convocatoria.id from MovilidadDocentesExterior de) or c.id in (select distinct ep.convocatoria.id from MovilidadEstudiantesPosgrado ep) "
				+ "or c.id in (select da.convocatoria.id from MovilidadDocentesArtes da) or c.id in (select distinct ea.convocatoria.id from MovilidadEstudiantesArtes ea) "
				+ "or c.id in (select ve.convocatoria.id from MovilidadVisitanteExterior ve) or c.id in (select va.convocatoria.id from MovilidadVisitantesArtes va)) order by cp.titulo asc";
		listaConvocatoriasMovilidad = servicioGeneral.obtenerObjetos(ConvocatoriaPadre.class, sql);
		if (!esListaVacia(listaConvocatoriasMovilidad)) {
			listaConvocatoriasMovilidadesItem = new SelectItem[listaConvocatoriasMovilidad.size()];
			for (int i = 0; i < listaConvocatoriasMovilidad.size(); i++) {
				ConvocatoriaPadre conv = (ConvocatoriaPadre) listaConvocatoriasMovilidad.get(i);
				listaConvocatoriasMovilidadesItem[i] = new SelectItem(conv.getId().toString(),
						conv.getTitulo().substring(0, 1).toUpperCase()
								+ conv.getTitulo().substring(1, conv.getTitulo().length()).toLowerCase());
			}
			convocatoriaSeleccionadaMovilidad = (String) listaConvocatoriasMovilidad.get(0).getId().toString();
			cargarModalidades();
		}
	}

	public void cargarModalidades() {
		this.setConvNacional(false);
		String sql = "select distinct cp from ConvocatoriaPadre cp where cp.id =" + convocatoriaSeleccionadaMovilidad;
		List<ConvocatoriaPadre> result = servicioGeneral.obtenerObjetos(ConvocatoriaPadre.class, sql);
		if (!esListaVacia(result)) {
			for (int i = 0; i < result.size(); i++) {
				ConvocatoriaPadre conv = (ConvocatoriaPadre) result.get(i);
				if (conv.getDependencia().getId().equals("1")) {
					this.setConvNacional(true);
					break;
				}
			}
		}
		sql = "select c from Convocatoria c where c.padre.id = '" + convocatoriaSeleccionadaMovilidad
				+ "' order by c.titulo asc";
		List<Convocatoria> listaModalidadesMovilidad = servicioGeneral.obtenerObjetos(Convocatoria.class, sql);
		if (!esListaVacia(listaModalidadesMovilidad)) {
			listaModalidadesMovilidadesItem = new SelectItem[listaModalidadesMovilidad.size()];
			for (int i = 0; i < listaModalidadesMovilidad.size(); i++) {
				Convocatoria conv = (Convocatoria) listaModalidadesMovilidad.get(i);
				listaModalidadesMovilidadesItem[i] = new SelectItem(conv.getId().toString(), conv.getTitulo());
			}
		}
	}

	public void modalidadesSeleccionadas() {
		if (esCadenaVacia(modalidades)) {
			modalidades = "";
			for (String valor : selectedModalidades) {
				modalidades = modalidades + " " + valor;
			}
		}
	}

	public String imprimirReporteMovilidades(ReporteBirt r) {

		modalidadesSeleccionadas();
		if (esCadenaVacia(modalidades)) {
			mensajeError("Debe seleccionar al menos una modalidad de la convocatoria");
			return "";
		}
		if (isConvNacional()) {
			int totalFalse = 0;
			for (int a = 0; a < columnsMovilidades.length; a++) {
				if (columnsMovilidades[a].equals(false)) {
					totalFalse++;
				}
			}

			if (totalFalse == columnsMovilidades.length - 1) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe seleccionar al menos un campo para el reporte", ""));
				return "";
			}
			r.setNombreReporte("/movilidad/reporteMovilidades_ConvNacional");
			r.adicionarParametro("modalidades", modalidades);
			r.adicionarParametro("Estado", String.valueOf(columnsMovilidades[0]));
			r.adicionarParametro("FechaReg", String.valueOf(columnsMovilidades[1]));
			r.adicionarParametro("Nombre", String.valueOf(columnsMovilidades[2]));
			r.adicionarParametro("Convocatoria", String.valueOf(columnsMovilidades[3]));
			r.adicionarParametro("TipoMov", String.valueOf(columnsMovilidades[4]));
			r.adicionarParametro("TipoAct", String.valueOf(columnsMovilidades[5]));
			r.adicionarParametro("DatosSolic", String.valueOf(columnsMovilidades[6]));
			r.adicionarParametro("DatosDep", String.valueOf(columnsMovilidades[7]));
			r.adicionarParametro("GrupoInv", String.valueOf(columnsMovilidades[8]));
			r.adicionarParametro("DatosVis", String.valueOf(columnsMovilidades[9]));
			r.adicionarParametro("FechaIni", String.valueOf(columnsMovilidades[10]));
			r.adicionarParametro("FechaFin", String.valueOf(columnsMovilidades[11]));
			r.adicionarParametro("Duracion", String.valueOf(columnsMovilidades[12]));
			r.adicionarParametro("InfoEco", String.valueOf(columnsMovilidades[13]));
			r.adicionarParametro("ComentSede", String.valueOf(columnsMovilidades[14]));
			r.adicionarParametro("InformSeg", String.valueOf(columnsMovilidades[15]));
			r.adicionarParametro("MovRealiz", String.valueOf(columnsMovilidades[16]));
			r.adicionarParametro("FechaAprob", String.valueOf(columnsMovilidades[17]));
		} else {
			r.setNombreReporte("/movilidad/reporteMovilidades");
			r.adicionarParametro("modalidades", modalidades);
			r.adicionarParametro("fecha", String.valueOf(columnsMovilidades[0]));
			r.adicionarParametro("convocatoria", String.valueOf(columnsMovilidades[1]));
			r.adicionarParametro("investigador", String.valueOf(columnsMovilidades[2]));
			r.adicionarParametro("dependencia", String.valueOf(columnsMovilidades[3]));
			r.adicionarParametro("grupoInvestigacion", String.valueOf(columnsMovilidades[4]));
			r.adicionarParametro("fechaInicio", String.valueOf(columnsMovilidades[5]));
			r.adicionarParametro("fechaFin", String.valueOf(columnsMovilidades[6]));
			r.adicionarParametro("pais", String.valueOf(columnsMovilidades[7]));
			r.adicionarParametro("evento", String.valueOf(columnsMovilidades[8]));
			r.adicionarParametro("inscripcion", String.valueOf(columnsMovilidades[9]));
			r.adicionarParametro("gastosDia", String.valueOf(columnsMovilidades[10]));
			r.adicionarParametro("dias", String.valueOf(columnsMovilidades[11]));
			r.adicionarParametro("tiquetes", String.valueOf(columnsMovilidades[12]));
			r.adicionarParametro("viaticos", String.valueOf(columnsMovilidades[13]));
			r.adicionarParametro("programa", String.valueOf(columnsMovilidades[14]));
			r.adicionarParametro("estudiante", String.valueOf(columnsMovilidades[15]));
			r.adicionarParametro("visitante", String.valueOf(columnsMovilidades[16]));
			r.adicionarParametro("aportes", String.valueOf(columnsMovilidades[17]));
			r.adicionarParametro("informe", String.valueOf(columnsMovilidades[18]));
			r.adicionarParametro("dispPresupuestalFacultad", String.valueOf(columnsMovilidades[19]));
			r.adicionarParametro("dispPresupuestalSede", String.valueOf(columnsMovilidades[20]));
		}

		r.setFormato(ReporteBirt.FORMATO_XLS);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}

	public String imprimirReporteAvales(ReporteBirt r) {
		if (!verificarFechasReporte()) {
			return "";
		}
		
		if(mostrarCamposAvalEtico && !esNulo(selectedTipoAval) && !esRolRevisionAvalEtico) {
			String[] nuevoArreglo = Arrays.copyOf(selectedTipoAval, selectedTipoAval.length + 1);
			nuevoArreglo[selectedTipoAval.length] = "ETICO";
			selectedTipoAval = nuevoArreglo;
		}
		
		if (esCadenaVacia(tipoAvalSeleccionados(selectedTipoAval))) {
			mensajeError("Debe seleccionar al menos un tipo de aval");
			return "";
		}
		if (esCadenaVacia(estadosSeleccionados(selectedEstados))) {
			mensajeError("Debe seleccionar al menos un estado de aval");
			return "";
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String fechainicial = sdf.format(fechaInicio);
		String fechafinal = sdf.format(sumarRestarDiasFecha(fechaFin, 1));
		r.adicionarParametro("fechaDesde", fechainicial);
		r.adicionarParametro("fechaHasta", fechafinal);
		r.adicionarParametro("estado", estadosSeleccionados(selectedEstados));
		r.adicionarParametro("tipoAval", tipoAvalSeleccionados(selectedTipoAval));
		r.setNombreReporte("/aval/reporteCoordinacion");
		r.adicionarParametro("mostrarAval", String.valueOf(columns[0]));
		r.adicionarParametro("mostrarFechaEnvioAval", String.valueOf(columns[1]));
		r.adicionarParametro("mostrarTipoAval", String.valueOf(columns[2]));
		r.adicionarParametro("mostrarEstadoAval", String.valueOf(columns[3]));
		r.adicionarParametro("mostrarFechas", String.valueOf(columns[4]));
		r.adicionarParametro("mostrarConvocatoria", String.valueOf(columns[5]));
		r.adicionarParametro("mostrarNroConvocatoria", String.valueOf(columns[6]));
		r.adicionarParametro("mostrarFechaCierreConvocatoria", String.valueOf(columns[7]));
		r.adicionarParametro("mostrarEntidad", String.valueOf(columns[8]));
		r.adicionarParametro("mostrarInvestigador", String.valueOf(columns[9]));
		r.adicionarParametro("mostrarDepartamento", String.valueOf(columns[10]));
		r.adicionarParametro("mostrarFacultad", String.valueOf(columns[11]));
		r.adicionarParametro("mostrarSede", String.valueOf(columns[12]));
		r.adicionarParametro("mostrarProyecto", String.valueOf(columns[13]));
		r.adicionarParametro("mostrarVrProyecto", String.valueOf(columns[14]));
		r.adicionarParametro("mostrarGrupo", String.valueOf(columns[15]));
		r.adicionarParametro("mostrarVrSolEntConvocante", String.valueOf(columns[16]));
		r.adicionarParametro("mostrarVrContrapartidaPersonalUN", String.valueOf(columns[17]));
		r.adicionarParametro("mostrarVrContrapartidaEspecieUN", String.valueOf(columns[18]));
		r.adicionarParametro("mostrarVrContrapartidaEfectivoUN", String.valueOf(columns[19]));
		r.adicionarParametro("mostrarEntidadesParticipantes", String.valueOf(columns[20]));
		
		r.adicionarParametro("mostrarNombreCEPI", String.valueOf(columns[21]));
		r.adicionarParametro("mostrarAvalCEPI", String.valueOf(columns[22]));
		r.adicionarParametro("mostrarFechaRevisionCEPI", String.valueOf(columns[23]));
		r.adicionarParametro("mostrarTieneRecurso", String.valueOf(columns[24]));
		r.adicionarParametro("mostrarFechaRecursoDocente", String.valueOf(columns[25]));
		r.adicionarParametro("mostrarFechaRevisionRecursoCEPI", String.valueOf(columns[26]));
		r.adicionarParametro("mostrarConsideracionesEticas", String.valueOf(columns[27]));
		
		r.setFormato(ReporteBirt.FORMATO_XLS);
		sesion.setAttribute(NOMBRE_REPORTE_SESION, r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);

		return "";
	}

	public String imprimirReporteSemilleros(ReporteBirt r) {
		boolean valido = false;
		for (Boolean boolean1 : columnsSemilleros) {
			if (boolean1) {
				valido = true;
				break;
			}
		}
		if (!valido) {
			mensajeError("Debe seleccionar al menos un dato del contenido BASE.");
			return "";
		}
		if (esCadenaVacia(estadosSeleccionados(selectedEstadosSemilleros))) {
			mensajeError("Debe seleccionar al menos un estado de semillero.");
			return "";
		}
		r.setNombreReporte("/semilleros/consolidadoSemilleros");
		r.adicionarParametro("mostrarCodigo", String.valueOf(columnsSemilleros[0]));
		r.adicionarParametro("mostrarNombre", String.valueOf(columnsSemilleros[1]));
		r.adicionarParametro("mostrarEstadoActual", String.valueOf(columnsSemilleros[2]));
		r.adicionarParametro("mostrarLider", String.valueOf(columnsSemilleros[3]));
		r.adicionarParametro("mostrarEmail", String.valueOf(columnsSemilleros[4]));
		r.adicionarParametro("mostrarFacultadPrincipal", String.valueOf(columnsSemilleros[5]));
		r.adicionarParametro("mostrarSedePrincipal", String.valueOf(columnsSemilleros[6]));
		r.adicionarParametro("mostrarFechaCreacion", String.valueOf(columnsSemilleros[7]));
		r.adicionarParametro("mostrarPresentacion", String.valueOf(columnsSemilleros[8]));
		r.adicionarParametro("mostrarObjetivoGeneral", String.valueOf(columnsSemilleros[9]));
		r.adicionarParametro("mostrarJustificacion", String.valueOf(columnsSemilleros[10]));
		r.adicionarParametro("mostrarEnfoque", String.valueOf(columnsSemilleros[11]));
		r.adicionarParametro("mostrarFechaRegistro", String.valueOf(columnsSemilleros[12]));
		r.adicionarParametro("mostrarAreaOCDEPrincipal", String.valueOf(columnsSemilleros[13]));
		r.adicionarParametro("mostrarObjetivoDesarrolloSostenible", String.valueOf(columnsSemilleros[14]));
		r.adicionarParametro("mostrarAgendaConocimiento", String.valueOf(columnsSemilleros[15]));
		r.adicionarParametro("mostrarPertinencia", String.valueOf(columnsSemilleros[16]));
		r.adicionarParametro("mostrarMetodologia", String.valueOf(columnsSemilleros[17]));
		r.adicionarParametro("mostrarContenidoAdicional", mostrarContenidoAdicional);
		r.adicionarParametro("estados", estadosSeleccionados(selectedEstadosSemilleros));
		
		if(!esCadenaVacia(mostrarContenidoAdicional)) {
			if(mostrarContenidoAdicional.equals("LIN")) {
				r.setNombreReporte("/semilleros/consolidadoSemillerosLineas");
			}else if(mostrarContenidoAdicional.equals("RES")) {
				r.setNombreReporte("/semilleros/consolidadoSemillerosResultados");
			}else if(mostrarContenidoAdicional.equals("FAC")) {
				r.setNombreReporte("/semilleros/consolidadoSemillerosFacultades");
			}else if(mostrarContenidoAdicional.equals("GRU")) {
				r.setNombreReporte("/semilleros/consolidadoSemillerosGrupos");
			}else if(mostrarContenidoAdicional.equals("INT")) {
				r.setNombreReporte("/semilleros/consolidadoSemillerosIntegrantes");
			}else if(mostrarContenidoAdicional.equals("LAB")) {
				r.setNombreReporte("/semilleros/consolidadoSemillerosLaboratorios");
			}else if(mostrarContenidoAdicional.equals("PLA")) {
				r.setNombreReporte("/semilleros/consolidadoSemillerosPlanTrabajo");
			}else if(mostrarContenidoAdicional.equals("PRO")) {
				r.setNombreReporte("/semilleros/consolidadoSemillerosProyectos");
			}else if(mostrarContenidoAdicional.equals("SED")) {
				r.setNombreReporte("/semilleros/consolidadoSemillerosSedes");
			}else if(mostrarContenidoAdicional.equals("INF")) {
				r.setNombreReporte("/semilleros/consolidadoSemillerosInformes");
			}
		}
		
		r.setFormato(ReporteBirt.FORMATO_XLS);
		sesion.setAttribute(NOMBRE_REPORTE_SESION, r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}

	public String estadosSeleccionados(String[] itemsSeleccionados) {
		String items = "";
		if (itemsSeleccionados == null) {
			return items;
		}
		for (String valor : itemsSeleccionados) {
			items = items + " " + valor;
		}
		return items;
	}

	public String tipoAvalSeleccionados(String[] itemsSeleccionados) {
		String items = "";
		for (String valor : itemsSeleccionados) {
			items = items + " " + valor;
		}
		return items;
	}

	public boolean verificarFechasReporte() {
		if (this.fechaInicio != null && this.fechaFin != null) {
			if (!this.fechaInicio.before(this.fechaFin)) {
				mensajeError("La fecha Final debe ser posterior a la de Inicio");
				return false;
			}
		} else {
			mensajeError("Debe indicar el rango de fechas del reporte");
			return false;
		}
		return true;
	}

	public void descargarDetalleExcel() {
		servicioGeneral.descargarReporteExcelDesdeSql(consultaDetalladaActual, "detalle-reporte");
	}

	public SelectItem[] getListaConvocatoriasMovilidadesItem() {
		return listaConvocatoriasMovilidadesItem;
	}

	public void setListaConvocatoriasMovilidadesItem(SelectItem[] listaConvocatoriasMovilidadesItem) {
		this.listaConvocatoriasMovilidadesItem = listaConvocatoriasMovilidadesItem;
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

	public SelectItem[] getEstadoItems() {
		return estadoItems;
	}

	public void setEstadoItems(SelectItem[] estadoItems) {
		this.estadoItems = estadoItems;
	}

	public int getSelItem() {
		return selItem;
	}

	public void setSelItem(int selItem) {
		this.selItem = selItem;
	}

	public Boolean[] getColumns() {
		return columns;
	}

	public void setColumns(Boolean[] columns) {
		this.columns = columns;
	}

	public String[] getSelectedEstados() {
		return selectedEstados;
	}

	public void setSelectedEstados(String[] selectedEstados) {
		this.selectedEstados = selectedEstados;
	}

	public Boolean[] getColumnsGrupos() {
		return columnsGrupos;
	}

	public void setColumnsGrupos(Boolean[] columnsGrupos) {
		this.columnsGrupos = columnsGrupos;
	}

	public SelectItem[] getEstadoItemsGrupos() {
		return estadoItemsGrupos;
	}

	public void setEstadoItemsGrupos(SelectItem[] estadoItemsGrupos) {
		this.estadoItemsGrupos = estadoItemsGrupos;
	}

	public String[] getSelectedEstadosGrupos() {
		return selectedEstadosGrupos;
	}

	public void setSelectedEstadosGrupos(String[] selectedEstadosGrupos) {
		this.selectedEstadosGrupos = selectedEstadosGrupos;
	}

	public List<SelectItem> getAnosReporteItems() {
		return anosReporteItems;
	}

	public void setAnosReporteItems(List<SelectItem> anosReporteItems) {
		this.anosReporteItems = anosReporteItems;
	}

	public List<SelectItem> getAnosReporteItemsConvocatoria() {
		return anosReporteItemsConvocatoria;
	}

	public void setAnosReporteItemsConvocatoria(List<SelectItem> anosReporteItemsConvocatoria) {
		this.anosReporteItemsConvocatoria = anosReporteItemsConvocatoria;
	}

	public Double getSumaTotal() {
		return sumaTotal;
	}

	public void setSumaTotal(Double sumaTotal) {
		this.sumaTotal = sumaTotal;
	}

	public Double getSumaTotalRegistros() {
		return sumaTotalRegistros;
	}

	public void setSumaTotalRegistros(Double sumaTotalRegistros) {
		this.sumaTotalRegistros = sumaTotalRegistros;
	}

	public SelectItem[] getListaModalidadesMovilidadesItem() {
		return listaModalidadesMovilidadesItem;
	}

	public void setListaModalidadesMovilidadesItem(SelectItem[] listaModalidadesMovilidadesItem) {
		this.listaModalidadesMovilidadesItem = listaModalidadesMovilidadesItem;
	}

	public String getConvocatoriaSeleccionada() {
		return convocatoriaSeleccionada;
	}

	public void setConvocatoriaSeleccionada(String convocatoriaSeleccionada) {
		this.convocatoriaSeleccionada = convocatoriaSeleccionada;
	}

	public String[] getSelectedModalidades() {
		return selectedModalidades;
	}

	public void setSelectedModalidades(String[] selectedModalidades) {
		this.selectedModalidades = selectedModalidades;
	}

	public Boolean[] getColumnsMovilidades() {
		return columnsMovilidades;
	}

	public void setColumnsMovilidades(Boolean[] columnsMovilidades) {
		this.columnsMovilidades = columnsMovilidades;
	}

	public String getModalidades() {
		return modalidades;
	}

	public void setModalidades(String modalidades) {
		this.modalidades = modalidades;
	}

	public String getConvocatoriaSeleccionadaMovilidad() {
		return convocatoriaSeleccionadaMovilidad;
	}

	public void setConvocatoriaSeleccionadaMovilidad(String convocatoriaSeleccionadaMovilidad) {
		this.convocatoriaSeleccionadaMovilidad = convocatoriaSeleccionadaMovilidad;
	}

	public List<ConvocatoriaPadre> getListaConvocatorias() {
		return listaConvocatorias;
	}

	public void setListaConvocatorias(List<ConvocatoriaPadre> listaConvocatorias) {
		this.listaConvocatorias = listaConvocatorias;
	}

	public SelectItem[] getListaConvocatoriasItem() {
		return listaConvocatoriasItem;
	}

	public void setListaConvocatoriasItem(SelectItem[] listaConvocatoriasItem) {
		this.listaConvocatoriasItem = listaConvocatoriasItem;
	}

	public String getHeaderGrafico() {
		return headerGrafico;
	}

	public void setHeaderGrafico(String headerGrafico) {
		this.headerGrafico = headerGrafico;
	}

	public String getAnoReporte() {
		return anoReporte;
	}

	public void setAnoReporte(String anoReporte) {
		this.anoReporte = anoReporte;
	}

	public String getTipoReporteAval() {
		return tipoReporteAval;
	}

	public void setTipoReporteAval(String tipoReporteAval) {
		this.tipoReporteAval = tipoReporteAval;
	}

	public String getTipoReporteGrupo() {
		return tipoReporteGrupo;
	}

	public void setTipoReporteGrupo(String tipoReporteGrupo) {
		this.tipoReporteGrupo = tipoReporteGrupo;
	}

	public String getTipoReporteInvestigadores() {
		return tipoReporteInvestigadores;
	}

	public void setTipoReporteInvestigadores(String tipoReporteInvestigadores) {
		this.tipoReporteInvestigadores = tipoReporteInvestigadores;
	}

	public String getTipoReporteBiodiversidad() {
		return tipoReporteBiodiversidad;
	}

	public void setTipoReporteBiodiversidad(String tipoReporteBiodiversidad) {
		this.tipoReporteBiodiversidad = tipoReporteBiodiversidad;
	}

	public String getTipoReporteConvocatorias() {
		return tipoReporteConvocatorias;
	}

	public void setTipoReporteConvocatorias(String tipoReporteConvocatorias) {
		this.tipoReporteConvocatorias = tipoReporteConvocatorias;
	}

	public String getSedeSeleccionada() {
		return sedeSeleccionada;
	}

	public void setSedeSeleccionada(String sedeSeleccionada) {
		this.sedeSeleccionada = sedeSeleccionada;
	}

	public boolean isMostrarOpcionAno() {
		return mostrarOpcionAno;
	}

	public void setMostrarOpcionAno(boolean mostrarOpcionAno) {
		this.mostrarOpcionAno = mostrarOpcionAno;
	}

	public boolean isMostrarOpcionConvocatoria() {
		return mostrarOpcionConvocatoria;
	}

	public void setMostrarOpcionConvocatoria(boolean mostrarOpcionConvocatoria) {
		this.mostrarOpcionConvocatoria = mostrarOpcionConvocatoria;
	}

	public boolean isMostrarOpcionConvocatoriaMovilidad() {
		return mostrarOpcionConvocatoriaMovilidad;
	}

	public void setMostrarOpcionConvocatoriaMovilidad(boolean mostrarOpcionConvocatoriaMovilidad) {
		this.mostrarOpcionConvocatoriaMovilidad = mostrarOpcionConvocatoriaMovilidad;
	}

	public List<ConvocatoriaPadre> getListaConvocatoriasMovilidad() {
		return listaConvocatoriasMovilidad;
	}

	public void setListaConvocatoriasMovilidad(List<ConvocatoriaPadre> listaConvocatoriasMovilidad) {
		this.listaConvocatoriasMovilidad = listaConvocatoriasMovilidad;
	}

	public SelectItem[] getReportesConvocatoriasItems() {
		return reportesConvocatoriasItems;
	}

	public void setReportesConvocatoriasItems(SelectItem[] reportesConvocatoriasItems) {
		this.reportesConvocatoriasItems = reportesConvocatoriasItems;
	}

	public SelectItem[] getReportesAvalesItems() {
		return reportesAvalesItems;
	}

	public void setReportesAvalesItems(SelectItem[] reportesAvalesItems) {
		this.reportesAvalesItems = reportesAvalesItems;
	}

	public SelectItem[] getReportesGruposItems() {
		return reportesGruposItems;
	}

	public void setReportesGruposItems(SelectItem[] reportesGruposItems) {
		this.reportesGruposItems = reportesGruposItems;
	}

	public SelectItem[] getReportesBiodiversidadItems() {
		return reportesBiodiversidadItems;
	}

	public void setReportesBiodiversidadItems(SelectItem[] reportesBiodiversidadItems) {
		this.reportesBiodiversidadItems = reportesBiodiversidadItems;
	}

	public SelectItem[] getReportesInvestigadoresItems() {
		return reportesInvestigadoresItems;
	}

	public void setReportesInvestigadoresItems(SelectItem[] reportesInvestigadoresItems) {
		this.reportesInvestigadoresItems = reportesInvestigadoresItems;
	}

	public Reporte getIndicador() {
		return indicador;
	}

	public void setIndicador(Reporte indicador) {
		this.indicador = indicador;
	}

	public boolean isMostrarOpcionSede() {
		return mostrarOpcionSede;
	}

	public void setMostrarOpcionSede(boolean mostrarOpcionSede) {
		this.mostrarOpcionSede = mostrarOpcionSede;
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

	public String getAnoReporteAval() {
		return anoReporteAval;
	}

	public void setAnoReporteAval(String anoReporteAval) {
		this.anoReporteAval = anoReporteAval;
	}

	public String getSedeSeleccionadaAval() {
		return sedeSeleccionadaAval;
	}

	public void setSedeSeleccionadaAval(String sedeSeleccionadaAval) {
		this.sedeSeleccionadaAval = sedeSeleccionadaAval;
	}

	public boolean isReporteAvales() {
		return reporteAvales;
	}

	public void setReporteAvales(boolean reporteAvales) {
		this.reporteAvales = reporteAvales;
	}

	public boolean isReporteGrupos() {
		return reporteGrupos;
	}

	public void setReporteGrupos(boolean reporteGrupos) {
		this.reporteGrupos = reporteGrupos;
	}

	public boolean isReporteInvestigadores() {
		return reporteInvestigadores;
	}

	public void setReporteInvestigadores(boolean reporteInvestigadores) {
		this.reporteInvestigadores = reporteInvestigadores;
	}

	public boolean isReporteBiodiversidad() {
		return reporteBiodiversidad;
	}

	public void setReporteBiodiversidad(boolean reporteBiodiversidad) {
		this.reporteBiodiversidad = reporteBiodiversidad;
	}

	public boolean isReporteConvocatorias() {
		return reporteConvocatorias;
	}

	public void setReporteConvocatorias(boolean reporteConvocatorias) {
		this.reporteConvocatorias = reporteConvocatorias;
	}

	public boolean isMostrarOpcionFacultad() {
		return mostrarOpcionFacultad;
	}

	public void setMostrarOpcionFacultad(boolean mostrarOpcionFacultad) {
		this.mostrarOpcionFacultad = mostrarOpcionFacultad;
	}

	public List<Dependencia> getListaFacultades() {
		return listaFacultades;
	}

	public void setListaFacultades(List<Dependencia> listaFacultades) {
		this.listaFacultades = listaFacultades;
	}

	public SelectItem[] getListaFacultadesItem() {
		return listaFacultadesItem;
	}

	public void setListaFacultadesItem(SelectItem[] listaFacultadesItem) {
		this.listaFacultadesItem = listaFacultadesItem;
	}

	public String getFacultadSeleccionada() {
		return facultadSeleccionada;
	}

	public void setFacultadSeleccionada(String facultadSeleccionada) {
		this.facultadSeleccionada = facultadSeleccionada;
	}

	public String getFacultadSeleccionadaAval() {
		return facultadSeleccionadaAval;
	}

	public void setFacultadSeleccionadaAval(String facultadSeleccionadaAval) {
		this.facultadSeleccionadaAval = facultadSeleccionadaAval;
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

	public boolean isMostrarOpcionDepto() {
		return mostrarOpcionDepto;
	}

	public void setMostrarOpcionDepto(boolean mostrarOpcionDepto) {
		this.mostrarOpcionDepto = mostrarOpcionDepto;
	}

	public String getDeptoSeleccionado() {
		return deptoSeleccionado;
	}

	public void setDeptoSeleccionado(String deptoSeleccionado) {
		this.deptoSeleccionado = deptoSeleccionado;
	}

	public String getDeptoSeleccionadoAval() {
		return deptoSeleccionadoAval;
	}

	public void setDeptoSeleccionadoAval(String deptoSeleccionadoAval) {
		this.deptoSeleccionadoAval = deptoSeleccionadoAval;
	}

	public String getConsultaDetalladaActual() {
		return consultaDetalladaActual;
	}

	public void setConsultaDetalladaActual(String consultaDetalladaActual) {
		this.consultaDetalladaActual = consultaDetalladaActual;
	}

	public boolean isConvNacional() {
		return convNacional;
	}

	public void setConvNacional(boolean convNacional) {
		this.convNacional = convNacional;
	}

	public String[] getSelectedTipoAval() {
		return selectedTipoAval;
	}

	public void setSelectedTipoAval(String[] selectedTipoAval) {
		this.selectedTipoAval = selectedTipoAval;
	}

	public SelectItem[] getTipoAvalItems() {
		return tipoAvalItems;
	}

	public void setTipoAvalItems(SelectItem[] tipoAvalItems) {
		this.tipoAvalItems = tipoAvalItems;
	}

	public SelectItem[] getEstadosSemilleros() {
		return estadosSemilleros;
	}

	public void setEstadosSemilleros(SelectItem[] estadosSemilleros) {
		this.estadosSemilleros = estadosSemilleros;
	}

	public Boolean[] getColumnsSemilleros() {
		return columnsSemilleros;
	}

	public void setColumnsSemilleros(Boolean[] columnsSemilleros) {
		this.columnsSemilleros = columnsSemilleros;
	}

	public SelectItem[] getContenidoAdicionalSemilleros() {
		return contenidoAdicionalSemilleros;
	}

	public void setContenidoAdicionalSemilleros(SelectItem[] contenidoAdicionalSemilleros) {
		this.contenidoAdicionalSemilleros = contenidoAdicionalSemilleros;
	}

	public String getMostrarContenidoAdicional() {
		return mostrarContenidoAdicional;
	}

	public void setMostrarContenidoAdicional(String mostrarContenidoAdicional) {
		this.mostrarContenidoAdicional = mostrarContenidoAdicional;
	}

	public String[] getSelectedEstadosSemilleros() {
		return selectedEstadosSemilleros;
	}

	public void setSelectedEstadosSemilleros(String[] selectedEstadosSemilleros) {
		this.selectedEstadosSemilleros = selectedEstadosSemilleros;
	}

	public SelectItem[] getContenidoAdicionalGrupos() {
		return contenidoAdicionalGrupos;
	}

	public void setContenidoAdicionalGrupos(SelectItem[] contenidoAdicionalGrupos) {
		this.contenidoAdicionalGrupos = contenidoAdicionalGrupos;
	}

	public String getMostrarContenidoAdicionalGrupos() {
		return mostrarContenidoAdicionalGrupos;
	}

	public void setMostrarContenidoAdicionalGrupos(String mostrarContenidoAdicionalGrupos) {
		this.mostrarContenidoAdicionalGrupos = mostrarContenidoAdicionalGrupos;
	}

	public Boolean getEsRolRevisionAvalEtico() {
		return esRolRevisionAvalEtico;
	}

	public void setEsRolRevisionAvalEtico(Boolean esRolRevisionAvalEtico) {
		this.esRolRevisionAvalEtico = esRolRevisionAvalEtico;
	}

	public Boolean getMostrarCamposAvalEtico() {
		return mostrarCamposAvalEtico;
	}

	public void setMostrarCamposAvalEtico(Boolean mostrarCamposAvalEtico) {
		this.mostrarCamposAvalEtico = mostrarCamposAvalEtico;
	}

	public String getConvocatoriaSel() {
		return convocatoriaSel;
	}

	public void setConvocatoriaSel(String convocatoriaSel) {
		this.convocatoriaSel = convocatoriaSel;
	}

	public SelectItem[] getModalidadItem() {
		return modalidadItem;
	}

	public void setModalidadItem(SelectItem[] modalidadItem) {
		this.modalidadItem = modalidadItem;
	}
	
	public void cargarModalidad() {
		ConvocatoriaPadre cp = new ConvocatoriaPadre();
		cp.setId(Long.parseLong(convocatoriaSel));
		if (cp.getId() != null) {
			List<Convocatoria> listaConvocatorias = servicioModalidad.obtenerConvocatoriasxPadre(cp);

			if (!esListaVacia(listaConvocatorias)) {
				modalidadItem = new SelectItem[listaConvocatorias.size()];
				for (int i = 0; i < listaConvocatorias.size(); i++) {
					Convocatoria con = (Convocatoria) listaConvocatorias.get(i);
					modalidadItem[i] = new SelectItem(con.getId().toString(), con.getTitulo());
				}
			} else {
				modalidadItem = new SelectItem[0];
			}
		}
	}

	public String[] getSelectedConvocatorias() {
		return selectedConvocatorias;
	}

	public void setSelectedConvocatorias(String[] selectedConvocatorias) {
		this.selectedConvocatorias = selectedConvocatorias;
	}
	
	public String consultarReporteProyectosMorosos(ReporteBirt r) {
		String modalidades = estadosSeleccionados(selectedConvocatorias);
		if (esCadenaVacia(modalidades)) {
			mensajeError("Debe seleccionar al menos una modalidad.");
			return "";
		}
		r.setNombreReporte("/reportes-proyectos/reporteCompromisosDirectoresConvocatoria");
		r.adicionarParametro("mod", String.valueOf(modalidades));
		
		r.setFormato(ReporteBirt.FORMATO_XLS);
		sesion.setAttribute(NOMBRE_REPORTE_SESION, r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}
	
}
