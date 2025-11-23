package co.edu.unal.hermes.vista.articulo;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoConvocatoria;
import co.edu.unal.hermes.modelo.ArchivoResumen;
import co.edu.unal.hermes.modelo.ConvocatoriaArticulo;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.Navegacion;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultarSolicitudArticulo extends ManejadorBase {

	private ConvocatoriaArticulo conArt;
	private SelectItem[] articuloItem;
	private Long codigoArticulo = Long.parseLong("-1");

	private String errores[];
	private boolean panelRender[];
	private boolean panelRenderError[];

	private List listaArchivosConvocatoriaArticulos;
	private HtmlDataTable tablaArchivosConvocatoriaArticulos;
	private List<ArchivoResumen> listaArchivoResumen;
	
	private boolean imprimirReporteConvArticulo = false;
	private boolean adjuntarCertRad = false;
	
	private List listaArchivosObl;
	
	private String tipoDocumentoSel;
	private UploadedFile archivoObligatorio;
	private String nombreArchivo;

	public ManejadorConsultarSolicitudArticulo() {

		reiniciarVariables();

		//ca = new ConvocatoriaArticulo();
		personaActual = (Persona) sesion.getAttribute("persona");

		List listaArticulos = servicioGeneral.obtenerListaObjetos("ConvocatoriaArticulo e where e.personaInv.id.documento = '"
						+ personaActual.getId().getDocumento()
						+ "'  ORDER BY e.id");
		//and (e.estado = 'P' or e.estado = 'APV' or e.estado = 'AP')
		
		System.out.println("tamaño lista de solicitudes: " + listaArticulos.size());

		if (listaArticulos != null && listaArticulos.size() > 0) {
			articuloItem = new SelectItem[listaArticulos.size()];
			for (int i = 0; i < listaArticulos.size(); i++) {
				ConvocatoriaArticulo articulo = (ConvocatoriaArticulo) listaArticulos
						.get(i);
				articuloItem[i] = new SelectItem(articulo.getId(), "Id="
						+ articulo.getId() + " - Fecha Registro("
						+ articulo.getFechaRegistro() + ")");
			}
			codigoArticulo = ((ConvocatoriaArticulo) listaArticulos.get(0))
					.getId();
		} else {
			articuloItem = new SelectItem[0];
		}
		tablaArchivosConvocatoriaArticulos = new HtmlDataTable();
		
		panelRender[1] = false;
		
		listaArchivosObl = new Vector();
	
		
		listaArchivosObl.add(new SelectItem("CACRA", "Certificado de radicación artículo actual"));
	}

	public void buscarSolicitudConvocatoriaArticulos() {
		if(codigoArticulo != -1){
			imprimirReporteConvArticulo = true;
			listaArchivosConvocatoriaArticulos = servicioGeneral.obtenerListaObjetos("ArchivoConvocatoria e where e.convocatoria = '" + codigoArticulo + "'");
			/*for(int i = 0; i < listaArchivosConvocatoriaArticulos.size(); i++){
				ArchivoConvocatoria arCon = new ArchivoConvocatoria();
				arCon = (ArchivoConvocatoria) listaArchivosConvocatoriaArticulos.get(i);
			}			
			ArchivoConvocatoria arCon = new ArchivoConvocatoria();
			arCon = (ArchivoConvocatoria) listaArchivosConvocatoriaArticulos.get(0);*/
			List listaConArt = servicioGeneral.obtenerObjetos("select e from ConvocatoriaArticulo e where e.id = '"+ codigoArticulo.toString() +"'");
			conArt = (ConvocatoriaArticulo) listaConArt.get(0);
			System.out.println("conv arti estado = " + conArt.getEstado());
			if(conArt != null && conArt.getEstado().equals(EstadoProyecto.APROBADO_VICERRECTORIA)){
				adjuntarCertRad = true;				
			}
		}
	}
	
	public void insertarCertificadoRadicacion() {

		try {
			// Se recorta el nombre del archivo para que no tenga la ruta
			// absoluta
			// ProyectoInforme pin = (ProyectoInforme)
			// tablaArchivos.getRowData();

			if (archivoObligatorio.getBytes() != null) {

				int i = archivoObligatorio.getName().lastIndexOf("\\");
				nombreArchivo = archivoObligatorio.getName().substring(
						i + 1);

				ArchivoConvocatoria archivoCon = new ArchivoConvocatoria();
				archivoCon.setBytes(archivoObligatorio.getBytes());
				archivoCon.setNombre(nombreArchivo);				
				archivoCon.setFecha(new Date());
				archivoCon.setTipoArchivo("CACRA");
				archivoCon.setConvocatoria(conArt.getId().toString());

				servicioGeneral.guardarObjeto(archivoCon);
				
				listaArchivosConvocatoriaArticulos = servicioGeneral.obtenerListaObjetos("ArchivoConvocatoria e where e.convocatoria = '" + codigoArticulo + "'");


				
			}
		} catch (Exception x) {
			x.printStackTrace();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL, x.getClass().getName(),
					x.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
    }
	
public void descargarArchivoArticulo(){

        FacesContext ctx = FacesContext.getCurrentInstance();
        
        Long id  = ((ArchivoConvocatoria)(tablaArchivosConvocatoriaArticulos.getRowData())).getId();
        
        List archivos = servicioGeneral.obtenerObjetoXID("ArchivoConvocatoria", id.toString());
        
        ArchivoConvocatoria archivo = (ArchivoConvocatoria)archivos.get(0);

        try{
	        if (!ctx.getResponseComplete()) {
		        HttpServletResponse response = (HttpServletResponse)ctx.getExternalContext().getResponse();
		        response.setContentType("text/plain");
		        response.setHeader("Content-Disposition","attachment;filename=\"" + archivo.getNombre() + "\"");
		        ServletOutputStream out = response.getOutputStream();
		        out.write(archivo.getBytes());
		        out.flush();
		        ctx.responseComplete();
	        }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

	public String imprimirReporteConvocatoriaArticulo() {

		if (codigoArticulo != -1 && codigoArticulo != null) {
			return imprimirReporteConvArticulos();
		} else {
			noExisteSolicitudArticulo();
			return new String("");
		}
	}

	public void noExisteSolicitudArticulo() {
		errores[0] = "no existe una solicitud con ese código";
		panelRenderError[0] = true;
	}

	public String imprimirReporteConvArticulos() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", codigoArticulo.toString());
		r.setNombreReporte("/convocatoriaArticulos/ConvocatoriaArticulos");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		return Navegacion.REPORTE;
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

		sesion.removeAttribute("ManejadorConsultarSolicitudArticulo");

	}

	private void reiniciarVariables() {
		//ca = new ConvocatoriaArticulo();
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

	public void setImprimirReporteConvArticulo(boolean imprimirReporteConvArticulo) {
		this.imprimirReporteConvArticulo = imprimirReporteConvArticulo;
	}

	public List getListaArchivoResumen() {
		return listaArchivoResumen;
	}

	public void setListaArchivoResumen(List listaArchivoResumen) {
		this.listaArchivoResumen = listaArchivoResumen;
	}

	public boolean isAdjuntarCertRad() {
		return adjuntarCertRad;
	}

	public void setAdjuntarCertRad(boolean adjuntarCertRad) {
		this.adjuntarCertRad = adjuntarCertRad;
	}

	public ConvocatoriaArticulo getConArt() {
		return conArt;
	}

	public void setConArt(ConvocatoriaArticulo conArt) {
		this.conArt = conArt;
	}

	public String getTipoDocumentoSel() {
		return tipoDocumentoSel;
	}

	public void setTipoDocumentoSel(String tipoDocumentoSel) {
		this.tipoDocumentoSel = tipoDocumentoSel;
	}

	public UploadedFile getArchivoObligatorio() {
		return archivoObligatorio;
	}

	public void setArchivoObligatorio(UploadedFile archivoObligatorio) {
		this.archivoObligatorio = archivoObligatorio;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public List getListaArchivosObl() {
		return listaArchivosObl;
	}

	public void setListaArchivosObl(List listaArchivosObl) {
		this.listaArchivosObl = listaArchivosObl;
	}


}