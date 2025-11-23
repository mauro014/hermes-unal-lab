package co.edu.unal.hermes.vista.articulo;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import co.edu.unal.hermes.modelo.ArchivoConvocatoria;
import co.edu.unal.hermes.modelo.ArchivoResumen;
import co.edu.unal.hermes.modelo.ConvocatoriaArticulo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultarSolicitudArticuloXId extends ManejadorBase {

	private SelectItem[] articuloItem;
	private Long codigoArticulo = Long.parseLong("-1");
	private String sCodigoArticulo = "";

	private String errores[];
	private boolean panelRender[];
	private boolean panelRenderError[];

	private List listaArchivosConvocatoriaArticulos;
	private HtmlDataTable tablaArchivosConvocatoriaArticulos;
	private List<ArchivoResumen> listaArchivoResumen;

	private boolean imprimirReporteConvArticulo = false;
	private boolean mostrarBuscar = true;

	public ManejadorConsultarSolicitudArticuloXId() {

		reiniciarVariables();

		tablaArchivosConvocatoriaArticulos = new HtmlDataTable();

		panelRender[1] = false;
		
		String parametroIdArticulo = (String) sesion.getAttribute("idArticulo");
		System.out.print("dgbenitezc parametroIdArticulo:"+parametroIdArticulo);
		if (parametroIdArticulo != null) {
			sCodigoArticulo = parametroIdArticulo;
			mostrarBuscar = false;
			buscarReporteSolicitudArticulos();
		}

	}
	
	public void buscarReporteSolicitudArticulos() {

		List listaArticulos = servicioGeneral.obtenerListaObjetos("ConvocatoriaArticulo e where e.id = '"
				+ sCodigoArticulo + "'");

		if (listaArticulos != null && listaArticulos.size() > 0) {
			articuloItem = new SelectItem[listaArticulos.size()];
			for (int i = 0; i < listaArticulos.size(); i++) {
				ConvocatoriaArticulo articulo = (ConvocatoriaArticulo) listaArticulos
						.get(i);
				articuloItem[i] = new SelectItem(articulo.getId(), "Id="
						+ articulo.getId() + " - Fecha Registro("
						+ articulo.getFechaRegistro() + ")(" +articulo.getEstado()+ ")");
			}
			codigoArticulo = ((ConvocatoriaArticulo) listaArticulos.get(0)).getId();
		} else {
			articuloItem = new SelectItem[0];

		}
		
		
		buscarSolicitudConvocatoriaArticulos();
	}

	public void buscarSolicitudConvocatoriaArticulos() {
		if (codigoArticulo != -1) {
			imprimirReporteConvArticulo = true;
			listaArchivosConvocatoriaArticulos = servicioGeneral.obtenerListaObjetos("ArchivoConvocatoria e where e.convocatoria = '" + codigoArticulo + "'");
		}else{
			imprimirReporteConvArticulo = false;
			panelRenderError[0] = true;
			errores[0] = "No existe una solicitud con ese código";
		}
	}

	public void descargarArchivoArticulo() {

		FacesContext ctx = FacesContext.getCurrentInstance();

		Long id = ((ArchivoConvocatoria) (tablaArchivosConvocatoriaArticulos
				.getRowData())).getId();

		List archivos = servicioGeneral.obtenerObjetoXID("ArchivoConvocatoria",
				id.toString());

		ArchivoConvocatoria archivo = (ArchivoConvocatoria) archivos.get(0);

		try {
			if (!ctx.getResponseComplete()) {
				HttpServletResponse response = (HttpServletResponse) ctx
						.getExternalContext().getResponse();
				response.setContentType("text/plain");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + archivo.getNombre() + "\"");
				ServletOutputStream out = response.getOutputStream();
				out.write(archivo.getBytes());
				out.flush();
				ctx.responseComplete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void imprimirReporteConvocatoriaArticulo() {

		if (codigoArticulo != -1 && codigoArticulo != null) {
			imprimirReporteConvArticulos();
		} else {
			noExisteSolicitudArticulo();
		}
	}

	public void noExisteSolicitudArticulo() {
		errores[0] = "no existe una solicitud con ese código";
		panelRenderError[0] = true;
	}

	public void imprimirReporteConvArticulos() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", codigoArticulo.toString());
		r.setNombreReporte("/convocatoriaArticulos/ConvocatoriaArticulos");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			//System.out.println(e);
		} finally {
			context.responseComplete();
		}
	}

	public void ocultarPaneles(int nivel) {
		for (int i = 0; i < 10; i++) {
			if (i < nivel) {
				panelRender[i] = true;
			} else {
				panelRender[i] = false;

			}

		}
	}

	public void limpiar() {

		sesion.removeAttribute("ManejadorConsultarSolicitudArticuloXId");

	}

	private void reiniciarVariables() {
		// ca = new ConvocatoriaArticulo();
		codigoArticulo = Long.parseLong("-1");
		errores = new String[60];
		panelRender = new boolean[10];
		panelRenderError = new boolean[60];
		tablaArchivosConvocatoriaArticulos = new HtmlDataTable();
		panelRender[1] = false;
		imprimirReporteConvArticulo = false;
	}

	void processChild(List childList) {
		for (int i = 0; i < childList.size(); i++) {
			UIComponent component = (UIComponent) childList.get(i);
			try {
				UIInput input = (UIInput) component;
				input.setSubmittedValue(null);
			} catch (Exception ex) {

			}
			List childList2 = component.getChildren();
			processChild(childList2);
		}
	}

	public void cancelAction(ActionEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		List childList = viewRoot.getChildren();
		processChild(childList);
	}

	public SelectItem[] getArticuloItem() {
		return articuloItem;
	}

	public void setArticuloItem(SelectItem[] articuloItem) {
		this.articuloItem = articuloItem;
	}

	public Long getCodigoArticulo() {
		return codigoArticulo;
	}

	public void setCodigoArticulo(Long codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

	public String[] getErrores() {
		return errores;
	}

	public void setErrores(String[] errores) {
		this.errores = errores;
	}

	public boolean[] getPanelRender() {
		return panelRender;
	}

	public void setPanelRender(boolean[] panelRender) {
		this.panelRender = panelRender;
	}

	public boolean[] getPanelRenderError() {
		return panelRenderError;
	}

	public void setPanelRenderError(boolean[] panelRenderError) {
		this.panelRenderError = panelRenderError;
	}

	public HtmlDataTable getTablaArchivosConvocatoriaArticulos() {
		return tablaArchivosConvocatoriaArticulos;
	}

	public void setTablaArchivosConvocatoriaArticulos(
			HtmlDataTable tablaArchivosConvocatoriaArticulos) {
		this.tablaArchivosConvocatoriaArticulos = tablaArchivosConvocatoriaArticulos;
	}

	public List getListaArchivosConvocatoriaArticulos() {
		return listaArchivosConvocatoriaArticulos;
	}

	public void setListaArchivosConvocatoriaArticulos(
			List listaArchivosConvocatoriaArticulos) {
		this.listaArchivosConvocatoriaArticulos = listaArchivosConvocatoriaArticulos;
	}

	public boolean isImprimirReporteConvArticulo() {
		return imprimirReporteConvArticulo;
	}

	public void setImprimirReporteConvArticulo(
			boolean imprimirReporteConvArticulo) {
		this.imprimirReporteConvArticulo = imprimirReporteConvArticulo;
	}

	public List getListaArchivoResumen() {
		return listaArchivoResumen;
	}

	public void setListaArchivoResumen(List listaArchivoResumen) {
		this.listaArchivoResumen = listaArchivoResumen;
	}	


	public String getsCodigoArticulo() {
		return sCodigoArticulo;
	}

	public void setsCodigoArticulo(String sCodigoArticulo) {
		this.sCodigoArticulo = sCodigoArticulo;
	}

	/**
	 * @return the mostrarBuscar
	 */
	public boolean isMostrarBuscar() {
		return mostrarBuscar;
	}

}
