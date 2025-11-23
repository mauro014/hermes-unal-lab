/*
 * Created on 30-sep-2005
 */
package co.edu.unal.hermes.vista.personas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import co.edu.unal.hermes.modelo.ArchivoConvocatoriaExterna;
import co.edu.unal.hermes.modelo.ArchivoConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.vista.ManejadorBase;


/**
 * The Class ManejadorConvocatoriaBusqueda.
 */
public class ManejadorConvocatoriaBusqueda extends ManejadorBase{
	
	
	/** The error. */
	private boolean error = false;
	
	/** The opcion. */
	private int opcion = 1;
	
	/** The file. */
	private StreamedContent file;  
	
	/** The convocatoria padre actual. */
	private ConvocatoriaPadre convocatoriaPadreActual;
	
	/** The convocatoria externa actual. */
	private ConvocatoriaExterna convocatoriaExternaActual;
	
	/** The pagina actual. */
	private String paginaActual;
	
	/** The lista modalidades. */
	private List<Convocatoria> listaModalidades;
	
	/** The tabla modalidades. */
	private HtmlDataTable tablaModalidades;
	
	/** The tabla documentos proceso. */
	private HtmlDataTable tablaDocumentosProceso;
	
	/** The tabla documentos resultados. */
	private HtmlDataTable tablaDocumentosResultados;
	
	/** The lista archivos convocatoria padre proceso. */
	private List<ArchivoConvocatoriaPadre> listaArchivosConvocatoriaPadreProceso;
	
	/** The lista archivos convocatoria padre resultado. */
	private List<ArchivoConvocatoriaPadre> listaArchivosConvocatoriaPadreResultado;
	
	/** The archivo proceso seleccionado. */
	private ArchivoConvocatoriaPadre archivoProcesoSeleccionado;
	
	/** The archivo resultados seleccionado. */
	private ArchivoConvocatoriaPadre archivoResultadosSeleccionado;
	
	/** The archivos proceso. */
	private List<StreamedContent> archivosProceso;
	
	/** The lista archivos. */
	private List<ArchivoConvocatoriaExterna> listaArchivos;
	
	/** The archivo seleccionado. */
	private ArchivoConvocatoriaExterna archivoSeleccionado;
	
	/**
	 * Instantiates a new manejador convocatoria busqueda.
	 */
	public ManejadorConvocatoriaBusqueda(){
		
        super();
        inicializarDatos();
		
    }
	
	/**
	 * Inicializar datos.
	 */
	private void inicializarDatos(){
		
		String tipo = "";
		if(this.request != null ){
			if(this.request.getParameter("tipo") != null ){
				if(!this.request.getParameter("tipo").equals("")){
					tipo = this.request.getParameter("tipo");
					if(tipo.equals("I")){
						cargarConvocatoriaPadre();
					}else if(tipo.equals("E")){
						cargarConvocatoriaExterna();
					}
				}
			}else{
				error = true;
			}	
		}else{
			error = true;
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();

		
		if(error){
			String idConvoExterna = (String) sesion.getAttribute("idConvocatoriaExterna");
			String idConvoInterna = (String) sesion.getAttribute("idConvocatoria");
			if(idConvoInterna!=null){
				cargarConvocatoriaPadre();
				String viewId = "/pages/Consultas/Convocatoria.xhtml";
				viewId = extContext.getRequestContextPath() + viewId + '?' + "idConvocatoria" + "=" + idConvoInterna +"&tipo=I";
				this.setPaginaActual(context.getExternalContext().encodeActionURL(viewId));
			}else{
				cargarConvocatoriaExterna();
				String viewId = "/pages/Consultas/Convocatoria.xhtml";
				viewId = extContext.getRequestContextPath() + viewId + '?' + "idConvocatoria" + "=" + idConvoExterna +"&tipo=E";
				this.setPaginaActual(context.getExternalContext().encodeActionURL(viewId));
			}
		}
		
		archivosProceso = new ArrayList<StreamedContent>();
		listaArchivos = new ArrayList<ArchivoConvocatoriaExterna>();
		
		if(convocatoriaPadreActual != null){
			
			obtenerArchivosConvocatoriaPadre();
			
			cargarModalidades();
		}
		
		if(convocatoriaExternaActual != null){
			
			consultarArchivosConvocatoria(convocatoriaExternaActual.getId().toString());
			
		}
	}
	/**
	 * Cargar convocatoria padre.
	 */
	private void cargarConvocatoriaPadre(){
		this.error = false;
		if(this.request != null ){
			if(this.request.getParameter("idConvocatoria") != null ){
				if(!this.request.getParameter("idConvocatoria").equals(""))
					try{
						this.convocatoriaPadreActual = servicioModalidad.obtenerConvocatoriaPadre(new Long(this.request.getParameter("idConvocatoria")));
						sesion = (HttpSession) facesContext.getExternalContext().getSession(false);
						sesion.setAttribute("idConvocatoria", this.request.getParameter("idConvocatoria"));
						if(this.convocatoriaPadreActual == null){
							this.error = true;
						}				
					}
					catch(NumberFormatException nfe){
						this.error = true;
					}
			}
			else{

				String idConvo = (String) sesion.getAttribute("idConvocatoria");
				try{
					this.convocatoriaPadreActual = servicioModalidad.obtenerConvocatoriaPadre(new Long(idConvo));
					sesion = (HttpSession) facesContext.getExternalContext().getSession(false);
					if(this.convocatoriaPadreActual == null)						
						sesion.setAttribute("idConvocatoria", this.request.getParameter("idConvocatoria"));
					if(this.convocatoriaPadreActual == null){
						this.error = true;
					}				
				}
				catch(NumberFormatException nfe){
					this.error = true;
				}
				
			}
		}
		else{

			String idConvo = (String) sesion.getAttribute("idConvocatoria");
			try{
				this.convocatoriaPadreActual = servicioModalidad.obtenerConvocatoriaPadre(new Long(idConvo));
				sesion = (HttpSession) facesContext.getExternalContext().getSession(false);
				if(this.convocatoriaPadreActual == null)
					sesion.setAttribute("idConvocatoria", this.request.getParameter("idConvocatoria"));
				if(this.convocatoriaPadreActual == null){
					this.error = true;
				}				
			}
			catch(NumberFormatException nfe){
				this.error = true;
			}

			if(this.convocatoriaPadreActual == null){
				this.error = true;
			}
		}
		
		if(this.error){
			this.opcion = 0;
		}
	}
	
	/**
	 * Cargar convocatoria externa.
	 */
	private void cargarConvocatoriaExterna(){
		this.error = false;
		if(this.request != null ){
			if(this.request.getParameter("idConvocatoria") != null ){
				if(!this.request.getParameter("idConvocatoria").equals(""))
					try{
						this.convocatoriaExternaActual = servicioModalidad.obtenerConvocatoriaExterna(new Long(this.request.getParameter("idConvocatoria")));
						sesion = (HttpSession) facesContext.getExternalContext().getSession(false);
						sesion.setAttribute("idConvocatoriaExterna", this.request.getParameter("idConvocatoria"));
						if(this.convocatoriaExternaActual == null){
							this.error = true;
						}				
					}
					catch(NumberFormatException nfe){
						this.error = true;
					}
			}
			else{
					String idConvo = (String) sesion.getAttribute("idConvocatoriaExterna");
					try{
						this.convocatoriaExternaActual = servicioModalidad.obtenerConvocatoriaExterna(new Long(idConvo));
						sesion = (HttpSession) facesContext.getExternalContext().getSession(false);
						if(this.convocatoriaExternaActual == null)						
							sesion.setAttribute("idConvocatoriaExterna", this.request.getParameter("idConvocatoria"));
						if(this.convocatoriaExternaActual == null){
							this.error = true;
						}				
					}
					catch(NumberFormatException nfe){
						this.error = true;
					}
					
				}
		}
		else{
			String idConvo = (String) sesion.getAttribute("idConvocatoriaExterna");
			try{
				this.convocatoriaExternaActual = servicioModalidad.obtenerConvocatoriaExterna(new Long(idConvo));
				sesion = (HttpSession) facesContext.getExternalContext().getSession(false);
				if(this.convocatoriaExternaActual == null)
					sesion.setAttribute("idConvocatoriaExterna", this.request.getParameter("idConvocatoria"));
				if(this.convocatoriaExternaActual == null){
					this.error = true;
				}				
			}
			catch(NumberFormatException nfe){
				this.error = true;
			}

			if(this.convocatoriaExternaActual == null){
				this.error = true;
			}
		}
	}
	
	/**
	 * Consultar archivos convocatoria.
	 *
	 * @param id the id
	 */
	public void consultarArchivosConvocatoria(String id) {
		listaArchivos = new ArrayList<ArchivoConvocatoriaExterna>();
		try {
			String consultaArchivos = "select a from ArchivoConvocatoriaExterna a where a.convocatoria ='"
					+ id + "' and a.estado = 'V'";
			List<ArchivoConvocatoriaExterna> listaA = (List<ArchivoConvocatoriaExterna>) servicioGeneral
					.obtenerObjetos(ArchivoConvocatoriaExterna.class,consultaArchivos);
			if (listaA.size() > 0) {
				listaArchivos.addAll(listaA);
			}
		} catch (Exception e) {
			System.out.println("Error al cargar archivos");
			e.printStackTrace();
		}
	}
	
	/**
	 * Cargar modalidades.
	 */
	private void cargarModalidades(){
		this.listaModalidades = servicioModalidad.obtenerConvocatoriasxPadre(this.convocatoriaPadreActual);
	}
	
	 /**
 	 * Gets the convocatoria padre actual.
 	 *
 	 * @return the convocatoria padre actual
 	 */
 	public ConvocatoriaPadre getConvocatoriaPadreActual() {
			return convocatoriaPadreActual;
	}

	/**
	 * Sets the convocatoria padre actual.
	 *
	 * @param convocatoriaPadreActual the new convocatoria padre actual
	 */
	public void setConvocatoriaPadreActual(ConvocatoriaPadre convocatoriaPadreActual) {
		this.convocatoriaPadreActual = convocatoriaPadreActual;
	}
	
	/**
	 * Cargar tipo pagina.
	 */
	private void cargarTipoPagina(){
		int maximaOpcion = 2;
		if(this.request != null){
			try{
				if(this.request.getParameter("opcion") != null && !this.request.getParameter("opcion").equals("")){
					try{
						this.opcion = Integer.parseInt(this.request.getParameter("opcion"));
						if(this.opcion > maximaOpcion || this.opcion < 1) this.opcion = 1;
					}
					catch(NumberFormatException nfe){
						this.opcion = 1;
					}
				}
				else if(sesion.getAttribute("opcionConvocatorias") != null){
					try{
						this.opcion = (Integer) sesion.getAttribute("opcionConvocatorias");
						if(this.opcion > maximaOpcion || this.opcion < 1) this.opcion = 1;
					}
					catch(NumberFormatException nfe){
						this.opcion = 1;
					}
				}
			}
			catch(Exception npe){
				this.opcion = 1;
			}
		}
		else this.opcion = 1; 
	}
	
	/**
	 * Gets the informacion basica.
	 *
	 * @return the informacion basica
	 */
	public String getInformacionBasica() {
		return "";
	}
	
	/**
	 * Documentos proceso.
	 */
	public void documentosProceso() {
		opcion = 2;
	}
		
	/**
	 * Gets the titulo actual.
	 *
	 * @return the titulo actual
	 */
	public String getTituloActual(){
		if(getOpcion1()) return "titulo_info_general.png";
		if(getOpcion2()) return "titulo_documentos.png";
		return "";
	}
	
	/**
	 * Gets the opcion1.
	 *
	 * @return the opcion1
	 */
	public boolean getOpcion1(){
		cargarTipoPagina();
		if(this.opcion == 1) return true;
		return false;
	}
	
	/**
	 * Gets the opcion2.
	 *
	 * @return the opcion2
	 */
	public boolean getOpcion2(){
		if(this.opcion == 2)return true;
		return false;
	}
	
	/**
	 * Sets the error.
	 *
	 * @param error the new error
	 */
	public void setError(boolean error) {
		this.error = error;
	}

	/**
	 * Checks if is error.
	 *
	 * @return true, if is error
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * Sets the lista modalidades.
	 *
	 * @param listaConvocatorias the new lista modalidades
	 */
	public void setListaModalidades(List<Convocatoria> listaConvocatorias) {
		this.listaModalidades = listaConvocatorias;
	}

	/**
	 * Gets the lista modalidades.
	 *
	 * @return the lista modalidades
	 */
	public List<Convocatoria> getListaModalidades() {
		return listaModalidades;
	}

	/**
	 * Gets the tabla modalidades.
	 *
	 * @return the tabla modalidades
	 */
	public HtmlDataTable getTablaModalidades() {
		return tablaModalidades;
	}

	/**
	 * Sets the tabla modalidades.
	 *
	 * @param tablaModalidades the new tabla modalidades
	 */
	public void setTablaModalidades(HtmlDataTable tablaModalidades) {
		this.tablaModalidades = tablaModalidades;
	}
	

	/**
	 * Consultar documento.
	 *
	 * @param archivoConvocatoriaPadre the archivo convocatoria padre
	 */
	public void consultarDocumento(ArchivoConvocatoriaPadre archivoConvocatoriaPadre){
		
	    	FacesContext ctx = FacesContext.getCurrentInstance();
	    	
	    	String path = RUTA_ARCHIVOS+"HER_ARCHIVO_CONVOCATORIA_PADRE//"+convocatoriaPadreActual.getId()+"//"+archivoConvocatoriaPadre.getId()+"//"+archivoConvocatoriaPadre.getNombre();
	    	File ficheroXLS = new File(path);
	    	FileInputStream fis;
			try {
				fis = new FileInputStream(ficheroXLS);
			
	    	byte[] bytes = new byte[1000];
	    	int read = 0;

	    	if (!ctx.getResponseComplete()) {
	    	   String fileName = ficheroXLS.getName();
	    	   HttpServletResponse response =
	    	   (HttpServletResponse) ctx.getExternalContext().getResponse();

	    	   response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");

	    	   ServletOutputStream out = response.getOutputStream();

	    	   while ((read = fis.read(bytes)) != -1) {
	    	        out.write(bytes, 0, read);
	    	   }

	    	   out.flush();
	    	   out.close();
	    	   System.out.println("\nDescargado\n");
	    	   ctx.responseComplete();
	    	}
			} catch (Exception e) {
				
			}
	    	System.out.println("Agregar Documento");
	}
	
	/**
	 * Obtener archivos convocatoria padre.
	 */
	private void obtenerArchivosConvocatoriaPadre(){
		listaArchivosConvocatoriaPadreProceso = servicioGeneral
		.obtenerObjetos(ArchivoConvocatoriaPadre.class,"from ArchivoConvocatoriaPadre a where a.visiblePagina = 'S' and a.tipoArchivo <> 'B' and a.convocatoriaPadre = '"
		+this.convocatoriaPadreActual.getId()+"' order by a.id");
		for (ArchivoConvocatoriaPadre archivo : listaArchivosConvocatoriaPadreProceso) {
			String path = RUTA_ARCHIVOS+"HER_ARCHIVO_CONVOCATORIA_PADRE"+obtenerSubCarpetaArchivo(archivo.getId())+"//"+archivo.getId();
			InputStream stream;
			try {
				stream = new FileInputStream(path);
				   file = new DefaultStreamedContent(stream, archivo.getEtiqueta(), archivo.getNombre()); 
				    archivosProceso.add(file);
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}  
		 
		}
		listaArchivosConvocatoriaPadreResultado = servicioGeneral
		.obtenerObjetos(ArchivoConvocatoriaPadre.class,
				"from ArchivoConvocatoriaPadre a where a.tipoArchivo = 'R' and a.convocatoriaPadre = '"+this.convocatoriaPadreActual.getId()+"' ");
	}
	
	/**
	 * Descargar archivo.
	 */
	public void descargarArchivo() {

		if(archivoSeleccionado!=null){
			descargarArchivoConvocatoriaExterna(archivoSeleccionado);
		}
	}

	/**
	 * Gets the lista archivos convocatoria padre proceso.
	 *
	 * @return the lista archivos convocatoria padre proceso
	 */
	public List<ArchivoConvocatoriaPadre> getListaArchivosConvocatoriaPadreProceso() {
		return listaArchivosConvocatoriaPadreProceso;
	}

	/**
	 * Sets the lista archivos convocatoria padre proceso.
	 *
	 * @param listaArchivosConvocatoriaPadreProceso the new lista archivos convocatoria padre proceso
	 */
	public void setListaArchivosConvocatoriaPadreProceso(
			List<ArchivoConvocatoriaPadre> listaArchivosConvocatoriaPadreProceso) {
		this.listaArchivosConvocatoriaPadreProceso = listaArchivosConvocatoriaPadreProceso;
	}

	/**
	 * Gets the lista archivos convocatoria padre resultado.
	 *
	 * @return the lista archivos convocatoria padre resultado
	 */
	public List<ArchivoConvocatoriaPadre> getListaArchivosConvocatoriaPadreResultado() {
		return listaArchivosConvocatoriaPadreResultado;
	}

	/**
	 * Sets the lista archivos convocatoria padre resultado.
	 *
	 * @param listaArchivosConvocatoriaPadreResultado the new lista archivos convocatoria padre resultado
	 */
	public void setListaArchivosConvocatoriaPadreResultado(
			List<ArchivoConvocatoriaPadre> listaArchivosConvocatoriaPadreResultado) {
		this.listaArchivosConvocatoriaPadreResultado = listaArchivosConvocatoriaPadreResultado;
	}
	
	/**
	 * Gets the tabla documentos proceso.
	 *
	 * @return the tabla documentos proceso
	 */
	public HtmlDataTable getTablaDocumentosProceso() {
		return tablaDocumentosProceso;
	}

	/**
	 * Sets the tabla documentos proceso.
	 *
	 * @param tablaDocumentosProceso the new tabla documentos proceso
	 */
	public void setTablaDocumentosProceso(HtmlDataTable tablaDocumentosProceso) {
		this.tablaDocumentosProceso = tablaDocumentosProceso;
	}

	/**
	 * Consultar documento proceso.
	 */
	public void consultarDocumentoProceso(){
		ArchivoConvocatoriaPadre archivo = archivoProcesoSeleccionado;
		consultarDocumento(archivo);
	}
	
	/**
	 * Consultar documento resultado.
	 */
	public void consultarDocumentoResultado(){
		ArchivoConvocatoriaPadre archivo = archivoResultadosSeleccionado;
		consultarDocumento(archivo);
	}

	/**
	 * Sets the tabla documentos resultados.
	 *
	 * @param tablaDocumentosResultados the new tabla documentos resultados
	 */
	public void setTablaDocumentosResultados(HtmlDataTable tablaDocumentosResultados) {
		this.tablaDocumentosResultados = tablaDocumentosResultados;
	}

	/**
	 * Gets the tabla documentos resultados.
	 *
	 * @return the tabla documentos resultados
	 */
	public HtmlDataTable getTablaDocumentosResultados() {
		return tablaDocumentosResultados;
	}
	
	/**
	 * Gets the visible documentos proceso.
	 *
	 * @return the visible documentos proceso
	 */
	public boolean getVisibleDocumentosProceso(){
		if(listaArchivosConvocatoriaPadreProceso != null)
			if(listaArchivosConvocatoriaPadreProceso.size() > 0){
				return true;
			}
		if(listaArchivos != null)
			if(listaArchivos.size() > 0){
				return true;
			}
		return false;
	}
	
	/**
	 * Gets the visible documentos resultado.
	 *
	 * @return the visible documentos resultado
	 */
	public boolean getVisibleDocumentosResultado(){
		if(listaArchivosConvocatoriaPadreResultado != null)
			if(listaArchivosConvocatoriaPadreResultado.size() > 0){
				return true;
			}
		return false;
	}
	
	/**
	 * Gets the visible documentos.
	 *
	 * @return the visible documentos
	 */
	public boolean getVisibleDocumentos(){
		if(listaArchivosConvocatoriaPadreResultado != null || listaArchivosConvocatoriaPadreProceso != null)
			if(listaArchivosConvocatoriaPadreResultado.size() > 0 || listaArchivosConvocatoriaPadreProceso.size() > 0){
				return true;
			}
		if(listaArchivos != null)
			if(listaArchivos.size() > 0){
				return true;
			}
		return false;
	}

	/**
	 * Sets the archivo proceso seleccionado.
	 *
	 * @param archivoProcesoSeleccionado the new archivo proceso seleccionado
	 */
	public void setArchivoProcesoSeleccionado(ArchivoConvocatoriaPadre archivoProcesoSeleccionado) {
		this.archivoProcesoSeleccionado = archivoProcesoSeleccionado;
	}

	/**
	 * Gets the archivo proceso seleccionado.
	 *
	 * @return the archivo proceso seleccionado
	 */
	public ArchivoConvocatoriaPadre getArchivoProcesoSeleccionado() {
		return archivoProcesoSeleccionado;
	}

	/**
	 * Sets the archivo resultados seleccionado.
	 *
	 * @param archivoResultadosSeleccionado the new archivo resultados seleccionado
	 */
	public void setArchivoResultadosSeleccionado(
			ArchivoConvocatoriaPadre archivoResultadosSeleccionado) {
		this.archivoResultadosSeleccionado = archivoResultadosSeleccionado;
	}

	/**
	 * Gets the archivo resultados seleccionado.
	 *
	 * @return the archivo resultados seleccionado
	 */
	public ArchivoConvocatoriaPadre getArchivoResultadosSeleccionado() {
		return archivoResultadosSeleccionado;
	}
  
    /**
     * Gets the file.
     *
     * @return the file
     */
    public StreamedContent getFile() {  
        return file;  
    }

	/**
	 * Gets the pagina actual.
	 *
	 * @return the pagina actual
	 */
	public String getPaginaActual() {
		return paginaActual;
	}

	/**
	 * Sets the pagina actual.
	 *
	 * @param paginaActual the new pagina actual
	 */
	public void setPaginaActual(String paginaActual) {
		this.paginaActual = paginaActual;
	}

	/**
	 * Sets the archivos proceso.
	 *
	 * @param archivosProceso the new archivos proceso
	 */
	public void setArchivosProceso(List<StreamedContent> archivosProceso) {
		this.archivosProceso = archivosProceso;
	}

	/**
	 * Gets the archivos proceso.
	 *
	 * @return the archivos proceso
	 */
	public List<StreamedContent> getArchivosProceso() {
		return archivosProceso;
	}   
	
	/**
	 * Checks if is es informacion adicional.
	 *
	 * @return true, if is es informacion adicional
	 */
	public boolean isEsInformacionAdicional(){
		
		if(convocatoriaPadreActual!=null && convocatoriaPadreActual.getInformacion() != null &&
				convocatoriaPadreActual.getInformacion().length() > 0)
			return true;
		return false;
	}

	/**
	 * Gets the convocatoria externa actual.
	 *
	 * @return the convocatoria externa actual
	 */
	public ConvocatoriaExterna getConvocatoriaExternaActual() {
		return convocatoriaExternaActual;
	}

	/**
	 * Sets the convocatoria externa actual.
	 *
	 * @param convocatoriaExternaActual the new convocatoria externa actual
	 */
	public void setConvocatoriaExternaActual(ConvocatoriaExterna convocatoriaExternaActual) {
		this.convocatoriaExternaActual = convocatoriaExternaActual;
	}

	/**
	 * Gets the lista archivos.
	 *
	 * @return the lista archivos
	 */
	public List<ArchivoConvocatoriaExterna> getListaArchivos() {
		return listaArchivos;
	}

	/**
	 * Sets the lista archivos.
	 *
	 * @param listaArchivos the new lista archivos
	 */
	public void setListaArchivos(List<ArchivoConvocatoriaExterna> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	/**
	 * Gets the archivo seleccionado.
	 *
	 * @return the archivo seleccionado
	 */
	public ArchivoConvocatoriaExterna getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	/**
	 * Sets the archivo seleccionado.
	 *
	 * @param archivoSeleccionado the new archivo seleccionado
	 */
	public void setArchivoSeleccionado(
			ArchivoConvocatoriaExterna archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}
	
}
