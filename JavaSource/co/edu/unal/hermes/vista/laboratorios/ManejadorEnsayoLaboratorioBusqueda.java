/*
 * Created on 30-sep-2005
 */
package co.edu.unal.hermes.vista.laboratorios;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
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

import co.edu.unal.hermes.modelo.ArchivoConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEnsayosServicios;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleEquipos;
import co.edu.unal.hermes.modelo.laboratorios.PersonaLaboratorio;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorEnsayoLaboratorioBusqueda extends ManejadorBase{
	
	private boolean error = false;
	
	private Laboratorio laboratorioActual;
	
	LaboratorioDetalleEnsayosServicios ensayoActual;

	private String paginaActual;
	
	private Long idEnsayo;
	
	private HtmlDataTable tablaLaboratorios;

	private List<LaboratorioDetalleEnsayosServicios> ensayosServiciosActuales;
	
	private List<LaboratorioDetalleEquipos> equiposActuales;
	
	private PersonaLaboratorio coordinadorLaboratorioActual;
	
	private String emailPersona;
	
	List<LaboratorioDetalleEnsayosServicios> listaEnsayosLaboratorio = new ArrayList<LaboratorioDetalleEnsayosServicios>();
	
	List<Laboratorio> listaLaboratorios = new ArrayList<Laboratorio>();
	
	List<LaboratorioDetalleEquipos> listaEquipos = new ArrayList<LaboratorioDetalleEquipos>();
	
	List<PersonaLaboratorio> perLab = new ArrayList<PersonaLaboratorio>();
	
	private List<ArchivoLaboratorio> listaArchivosLaboratorio;
	private StreamedContent archivoLaboratorioBrochure;
	private StreamedContent archivoLaboratorioTarifas;
	private StreamedContent archivoLaboratorioReglamento;
	private StreamedContent archivoLaboratorioCondServicio;
	private List<StreamedContent> archivosProceso = new ArrayList<StreamedContent>();
	
	boolean bandera = true;
	private StreamedContent imagen;
    File actual;
    String path = RUTA_ARCHIVOS + File.separator + "HER_LABORATORIO" + File.separator;
    
//    listaArchivosEnsayosServicios;

	public String getInformacionBasica() {
		return this.paginaActual + "&opcion=1";
	}

	
	public void setError(boolean error) {
		this.error = error;
	}

	public boolean isError() {
		return error;
	}
	
	public ManejadorEnsayoLaboratorioBusqueda() throws SQLException{

		super();
        inicializarDatos();	
    }
	
	private void inicializarDatos(){
		
		if(this.request != null ){
			if(this.request.getParameter("idEnsayo") != null ){
				if(!this.request.getParameter("idEnsayo").equals("")) {
					cargarEnsayo();
				}
			}else{
				error = true;
			}	
		} else
			error = true;
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		
		if(error){
			String idEnsayo = (String) sesion.getAttribute("idEnsayo");
			if(idEnsayo!=null /*&& !esNulo(laboratorioActual)*/){
				cargarEnsayo();
				String viewId = "/pages/Consultas/EnsayoLaboratorio.jsf";
				viewId = extContext.getRequestContextPath() + viewId + '?' + "idEnsayo" + "=" + laboratorioActual.getId();
				this.setPaginaActual(context.getExternalContext().encodeActionURL(viewId));
			}
		}
		
		if(laboratorioActual != null)
			obtenerArchivosLaboratorio();
		
	}
	
	private void obtenerArchivosLaboratorio(){
		String hql4 = "FROM ArchivoLaboratorio AL WHERE AL.idLab = '"
				+ laboratorioActual.getId()
				+ "' AND AL.tipoArchivo in (SELECT t.id FROM Tipos t WHERE t.id IN ("
				+ "'" + Tipos.TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_BROCHURE + "',"
				+ "'" + Tipos.TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_ACTO_TARIFAS + "',"
				+ "'" + Tipos.TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_CONDICIONES + "',"
				+ "'" + Tipos.TIPO_DOCUMENTO_INF_GRAL_REGLAMENTO + "'"
				+ ")) ORDER BY AL.id DESC";
		
		listaArchivosLaboratorio = servicioGeneral.obtenerObjetos(ArchivoLaboratorio.class, hql4);
		
		for (ArchivoLaboratorio archivo : listaArchivosLaboratorio) {
			String path = RUTA_ARCHIVOS+"HER_ARCHIVO_LABORATORIO"+obtenerSubCarpetaArchivo(archivo.getId())+"//"+archivo.getId();
			InputStream stream;
			try {
				stream = new FileInputStream(path);
				StreamedContent file = new DefaultStreamedContent(stream, archivo.getTipoArchivo().getNombre(), archivo.getNombreArchivo());
				
				if(archivo.getTipoArchivo().getId().equals(Tipos.TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_BROCHURE))
					archivoLaboratorioBrochure = file;
				if(archivo.getTipoArchivo().getId().equals(Tipos.TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_ACTO_TARIFAS))
					archivoLaboratorioTarifas = file;
				if(archivo.getTipoArchivo().getId().equals(Tipos.TIPO_DOCUMENTO_INF_GRAL_REGLAMENTO))
					archivoLaboratorioReglamento = file;
				if(archivo.getTipoArchivo().getId().equals(Tipos.TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_CONDICIONES))
					archivoLaboratorioCondServicio = file;
				
				archivosProceso.add(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void cargarInfoLaboratorioPublicar(Long id)
	{
		ensayosServiciosActuales = new ArrayList();
		equiposActuales = new ArrayList();
		
		String hql = "from Laboratorio as laboratorio WHERE laboratorio.id = '"+ id +"'";
		String hql2 = "from LaboratorioDetalleEnsayosServicios as ensayo WHERE ensayo.laboratorio.id = '"+ id +"' order by ensayo.acreditado desc,ensayo.nombre asc";
		String hql3 = "from LaboratorioDetalleEquipos as equipo WHERE (equipo.especializado = 1 OR equipo.robusto = 1) AND equipo.laboratorio.id = '"+ id +"' order by equipo.equipo";
		String hql4 = "from PersonaLaboratorio as peronsalaboratorio WHERE peronsalaboratorio.laboratorio.id = '"+ id +"' and peronsalaboratorio.rol.id = 'CO'";

		listaLaboratorios = servicioGeneral.obtenerObjetos( Laboratorio.class, hql );
		listaEnsayosLaboratorio = servicioGeneral.obtenerObjetos( LaboratorioDetalleEnsayosServicios.class, hql2 );
		listaEquipos = servicioGeneral.obtenerObjetos( LaboratorioDetalleEquipos.class, hql3 );
		perLab = servicioGeneral.obtenerObjetos(PersonaLaboratorio.class, hql4);
		
		if(listaLaboratorios != null){
			if(listaLaboratorios.size() > 0){
				laboratorioActual = listaLaboratorios.get(0).getActivo() ? listaLaboratorios.get(0) : null;
			}
		 }
		if(listaEnsayosLaboratorio != null){
			if(listaEnsayosLaboratorio.size() > 0){
				for(LaboratorioDetalleEnsayosServicios ensayaAgregar : listaEnsayosLaboratorio){
					ensayosServiciosActuales.add(ensayaAgregar);
				}
			}
		 }
		if(listaEquipos != null){
			if(listaEquipos.size() > 0){
				String nombreEquipoAnterior = "";
				Long cantidad = 1L;
				//int i = 0;
				LaboratorioDetalleEquipos equipoAnterior = new LaboratorioDetalleEquipos();

				for(LaboratorioDetalleEquipos equipoAgregar : listaEquipos){
					String nombreEquipoActual = equipoAgregar.getEquipo();					
					if (!nombreEquipoActual.equals(nombreEquipoAnterior)) {
						cantidad = 1L;
						equipoAgregar.setEstadoFisico(cantidad);
						equiposActuales.add(equipoAgregar);
						equipoAnterior = equipoAgregar;
					} else  {
						cantidad++;
						equipoAnterior.setEstadoFisico(cantidad);
					}
					nombreEquipoAnterior = nombreEquipoActual;
					
				}
			}
			
			int equiposAgregados = 0;
			for (LaboratorioDetalleEquipos equipoAgregado : equiposActuales){
				equiposAgregados += equipoAgregado.getEstadoFisico();
			}
			
			if (listaEquipos.size() != equiposAgregados) {
				LaboratorioDetalleEquipos ldeq = new LaboratorioDetalleEquipos();
				ldeq.setEquipo("Error, favor contacte a HERMES.");
				ldeq.setEstadoFisico(20048L);
				equiposActuales.add(ldeq);
			}
			
		 }
		
		if(perLab != null){
			if(perLab.size() > 0){
				coordinadorLaboratorioActual = perLab.get(0);
				
				int arroba= coordinadorLaboratorioActual.getPersona().getEmail().indexOf("@");
				if(arroba!=-1){
	
					setEmailPersona(coordinadorLaboratorioActual.getPersona().getEmail().substring(0, arroba));
				}else{
					setEmailPersona(coordinadorLaboratorioActual.getPersona().getEmail());
				}
				
			}
		 }
		 else error = true;
	}
	
	private void cargarEnsayo(){
		
		this.error = false;
		if(this.request != null ){
			if(this.request.getParameter("idEnsayo") != null ){
				if(!this.request.getParameter("idEnsayo").equals(""))
					try{
						cargarInfoLaboratorioPublicar(new Long(this.request.getParameter("idEnsayo")));
						sesion = (HttpSession) facesContext.getExternalContext().getSession(false);
						sesion.setAttribute("idEnsayo", this.request.getParameter("idEnsayo"));
						if(this.laboratorioActual == null){
							this.error = true;
						}				
					}
					catch(NumberFormatException nfe){
						this.error = true;
					}
			} else {
				String idEnsayo = (String) sesion.getAttribute("idEnsayo");
				try{
					cargarInfoLaboratorioPublicar(new Long(idEnsayo));
					sesion = (HttpSession) facesContext.getExternalContext().getSession(false);
					if(this.laboratorioActual == null)						
						sesion.setAttribute("idEnsayo", this.request.getParameter("idEnsayo"));
					if(this.laboratorioActual == null){
						this.error = true;
					}				
				}
				catch(NumberFormatException nfe){
					this.error = true;
				}
				
			}
		} else {

			String idEnsayo = (String) sesion.getAttribute("idEnsayo");
			try{
				cargarInfoLaboratorioPublicar(new Long(idEnsayo));				
				sesion = (HttpSession) facesContext.getExternalContext().getSession(false);
				if(this.laboratorioActual == null)
					sesion.setAttribute("idEnsayo", this.request.getParameter("idEnsayo"));
				if(this.laboratorioActual == null){
					this.error = true;
				}				
			}
			catch(NumberFormatException nfe){
				this.error = true;
			}

			if(this.laboratorioActual == null){
				this.error = true;
			}
		}
		
		try {
			actual = new File(path + laboratorioActual.getId() + ".jpg");
			bandera = actual.exists();
        	if(actual!= null && actual.exists()) {
                imagen = new DefaultStreamedContent(
	                        new ByteArrayInputStream(org.apache.commons.io.FileUtils.readFileToByteArray(actual)),
	                        "image/jpg"
	                        );

                if (imagen != null)
                    sesion.setAttribute("imagenDocente", imagen);
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		if(this.error){
			this.opcion = 0;
		}
		
	}
	
	public void reporteLaboratorioBusqueda() throws SQLException {

		String id = laboratorioActual.getId().toString();
		
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", id);
		r.setNombreReporte("/portafolio/Laboratorio");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}
	
	private void cargarTipoPagina(){
		int maximaOpcion = 1;
		if(this.request.getParameter("opcion") != null && !this.request.getParameter("opcion").equals("")){
			try{
				this.opcion = Integer.parseInt(this.request.getParameter("opcion"));
				if(this.opcion > maximaOpcion || this.opcion < 1) this.opcion = 1;
			}
			catch(NumberFormatException nfe){
				this.opcion = 1;
			}
		}
		else if(sesion.getAttribute("opcionEnsayos") != null){
			try{
				this.opcion = (Integer) sesion.getAttribute("opcionEnsayos");
				if(this.opcion > maximaOpcion || this.opcion < 1) this.opcion = 1;
			}
			catch(NumberFormatException nfe){
				this.opcion = 1;
			}
		}
	}
		
	public String getTituloActual(){
		if(getOpcion1()) return "titulo_info_general.png";
		return "";
	}
	
	private void cargarPaginaActual(){
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		
		String viewId = "/pages/Consultas/EnsayoLaboratorio.jsf";
		
		viewId = extContext.getRequestContextPath() + viewId + '?' + "idEnsayo" + "=" + laboratorioActual.getId();
		
		this.paginaActual = context.getExternalContext().encodeActionURL(viewId);
	}
	
	public LaboratorioDetalleEnsayosServicios getEnsayoActual() {
		return ensayoActual;
	}

	public void setEnsayoActual(LaboratorioDetalleEnsayosServicios ensayoActual) {
		this.ensayoActual = ensayoActual;
	}
	
	public HtmlDataTable getTablaLaboratorios() {
		return tablaLaboratorios;
	}

	public void setTablaLaboratorios(HtmlDataTable tablaLaboratorios) {
		this.tablaLaboratorios = tablaLaboratorios;
	}
	











	private int opcion = 1;
	
	private ConvocatoriaPadre convocatoriaPadreActual;
	
	private List<Convocatoria> listaModalidades;
	
	private HtmlDataTable tablaModalidades;
	
	private HtmlDataTable tablaDocumentosProceso;
	
	private HtmlDataTable tablaDocumentosResultados;
	
	private List<ArchivoConvocatoriaPadre> listaArchivosConvocatoriaPadreProceso;
	
	private List<ArchivoConvocatoriaPadre> listaArchivosConvocatoriaPadreResultado;
	
	
	private void cargarConvocatoriaPadre(){
		
		this.error = false;
		
		if(this.request.getParameter("idConvocatoria") != null && !this.request.getParameter("idConvocatoria").equals("")){
			try{
				this.convocatoriaPadreActual = servicioModalidad.obtenerConvocatoriaPadre(new Long(this.request.getParameter("idConvocatoria")));
				if(this.convocatoriaPadreActual == null){
					this.error = true;
				}
			}
			catch(NumberFormatException nfe){
				this.error = true;
			}
		}
		else{
			this.convocatoriaPadreActual = (ConvocatoriaPadre) sesion.getAttribute("convocatoriaPadreBusqueda");
			sesion.removeAttribute("convocatoriaPadreBusqueda");
			if(this.convocatoriaPadreActual == null){
				this.error = true;
			}
		}
		if(this.error){
			this.opcion = 0;
		}
	}
	
	private void cargarModalidades(){
		this.listaModalidades = servicioModalidad.obtenerConvocatoriasxPadre(this.convocatoriaPadreActual);
	}
	
	 public ConvocatoriaPadre getConvocatoriaPadreActual() {
			return convocatoriaPadreActual;
	}

	public void setConvocatoriaPadreActual(ConvocatoriaPadre convocatoriaPadreActual) {
		this.convocatoriaPadreActual = convocatoriaPadreActual;
	}
	
	
	
	
	public boolean getOpcion1(){
		if(this.opcion == 1) return true;
		return false;
	}
	
	public boolean getOpcion2(){
		if(this.opcion == 2)return true;
		return false;
	}

	public void setListaModalidades(List<Convocatoria> listaConvocatorias) {
		this.listaModalidades = listaConvocatorias;
	}

	public List<Convocatoria> getListaModalidades() {
		return listaModalidades;
	}

	public HtmlDataTable getTablaModalidades() {
		return tablaModalidades;
	}

	public void setTablaModalidades(HtmlDataTable tablaModalidades) {
		this.tablaModalidades = tablaModalidades;
	}
	

	public void consultarDocumento(ArchivoConvocatoriaPadre archivoConvocatoriaPadre){
		
	    	FacesContext ctx = FacesContext.getCurrentInstance();
	    	
	    	String path = "D:\\HER_ARCHIVO_CONVOCATORIA_PADRE\\"+convocatoriaPadreActual.getId()+"\\"+archivoConvocatoriaPadre.getId()+"\\"+archivoConvocatoriaPadre.getNombre();
	    	
	    	File ficheroXLS = new File(path);
	    	
	    	FileInputStream fis;
			try {
				fis = new FileInputStream(ficheroXLS);
			
	    	byte[] bytes = new byte[1000];
	    	int read = 0;

	    	if (!ctx.getResponseComplete()) {
	    	   String fileName = ficheroXLS.getName();
	    	   String contentType = "application/msword";
	    	   //applicatoin/msword word
	    	   //String contentType = "application/pdf";
	    	   HttpServletResponse response =
	    	   (HttpServletResponse) ctx.getExternalContext().getResponse();

	    	   response.setContentType(contentType);

	    	   response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");

	    	   ServletOutputStream out = response.getOutputStream();

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
	
	public List<ArchivoConvocatoriaPadre> getListaArchivosConvocatoriaPadreProceso() {
		return listaArchivosConvocatoriaPadreProceso;
	}

	public void setListaArchivosConvocatoriaPadreProceso(
			List<ArchivoConvocatoriaPadre> listaArchivosConvocatoriaPadreProceso) {
		this.listaArchivosConvocatoriaPadreProceso = listaArchivosConvocatoriaPadreProceso;
	}

	public List<ArchivoConvocatoriaPadre> getListaArchivosConvocatoriaPadreResultado() {
		return listaArchivosConvocatoriaPadreResultado;
	}

	public void setListaArchivosConvocatoriaPadreResultado(
			List<ArchivoConvocatoriaPadre> listaArchivosConvocatoriaPadreResultado) {
		this.listaArchivosConvocatoriaPadreResultado = listaArchivosConvocatoriaPadreResultado;
	}
	
	public HtmlDataTable getTablaDocumentosProceso() {
		return tablaDocumentosProceso;
	}

	public void setTablaDocumentosProceso(HtmlDataTable tablaDocumentosProceso) {
		this.tablaDocumentosProceso = tablaDocumentosProceso;
	}

	public void consultarDocumentoProceso(){
		ArchivoConvocatoriaPadre archivo = (ArchivoConvocatoriaPadre) tablaDocumentosProceso.getRowData();
		consultarDocumento(archivo);
	}
	
	public void consultarDocumentoResultado(){
		ArchivoConvocatoriaPadre archivo = (ArchivoConvocatoriaPadre) tablaDocumentosResultados.getRowData();
		consultarDocumento(archivo);
	}

	public void setTablaDocumentosResultados(HtmlDataTable tablaDocumentosResultados) {
		this.tablaDocumentosResultados = tablaDocumentosResultados;
	}

	public HtmlDataTable getTablaDocumentosResultados() {
		return tablaDocumentosResultados;
	}
	
	public boolean getVisibleDocumentos(){
		if(listaArchivosConvocatoriaPadreResultado != null || listaArchivosConvocatoriaPadreProceso != null)
			if(listaArchivosConvocatoriaPadreResultado.size() > 0 || listaArchivosConvocatoriaPadreProceso.size() > 0){
				return true;
			}
		return false;
	}

	public void setEnsayosServiciosActuales(List<LaboratorioDetalleEnsayosServicios> ensayosServiciosActuales) {
		this.ensayosServiciosActuales = ensayosServiciosActuales;
	}

	public List<LaboratorioDetalleEnsayosServicios> getEnsayosServiciosActuales() {
		return ensayosServiciosActuales;
	}

	public Laboratorio getLaboratorioActual() {
		return laboratorioActual;
	}

	public void setLaboratorioActual(Laboratorio laboratorioActual) {
		this.laboratorioActual = laboratorioActual;
	}

	public void setEquiposActuales(List<LaboratorioDetalleEquipos> equiposActuales) {
		this.equiposActuales = equiposActuales;
	}

	public List<LaboratorioDetalleEquipos> getEquiposActuales() {
		return equiposActuales;
	}

	public PersonaLaboratorio getCoordinadorLaboratorioActual() {
		return coordinadorLaboratorioActual;
	}

	public void setCoordinadorLaboratorioActual(
			PersonaLaboratorio coordinadorLaboratorioActual) {
		this.coordinadorLaboratorioActual = coordinadorLaboratorioActual;
	}
	
	public Long getIdEnsayo() {
		return idEnsayo;
	}

	public void setIdEnsayo(Long idEnsayo) {
		this.idEnsayo = idEnsayo;
	}

	public List<LaboratorioDetalleEnsayosServicios> getListaEnsayosLaboratorio() {
		return listaEnsayosLaboratorio;
	}

	public void setListaEnsayosLaboratorio(
			List<LaboratorioDetalleEnsayosServicios> listaEnsayosLaboratorio) {
		this.listaEnsayosLaboratorio = listaEnsayosLaboratorio;
	}

	public List<Laboratorio> getListaLaboratorios() {
		return listaLaboratorios;
	}

	public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
		this.listaLaboratorios = listaLaboratorios;
	}

	public List<LaboratorioDetalleEquipos> getListaEquipos() {
		return listaEquipos;
	}

	public void setListaEquipos(List<LaboratorioDetalleEquipos> listaEquipos) {
		this.listaEquipos = listaEquipos;
	}

	public List<PersonaLaboratorio> getPerLab() {
		return perLab;
	}

	public void setPerLab(List<PersonaLaboratorio> perLab) {
		this.perLab = perLab;
	}


	public String getEmailPersona() {
		return emailPersona;
	}


	public void setEmailPersona(String emailPersona) {
		this.emailPersona = emailPersona;
	}


	public String getPaginaActual() {
		return paginaActual;
	}


	public void setPaginaActual(String paginaActual) {
		this.paginaActual = paginaActual;
	}
	
	public StreamedContent getImagen() {
        StreamedContent imagen2 = null;
        try {
            imagen2 = (StreamedContent) sesion.getAttribute("imagenDocente");
        } catch (Exception e) {
            // e.printStackTrace();
        }
        if (imagen2 != null) {
            return imagen2;
        } else {
            try {
                imagen = new DefaultStreamedContent(
                        new ByteArrayInputStream(org.apache.commons.io.FileUtils.readFileToByteArray(actual)),
                        "image/jpg");
            } catch (IOException e) {

                // e.printStackTrace();
            }
            return imagen;
        }
    }

    public void setImagen(StreamedContent imagen) {
        this.imagen = imagen;
    }

	public boolean isBandera() {
		return bandera;
	}

	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}


	public File getActual() {
		return actual;
	}

	public void setActual(File actual) {
		this.actual = actual;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getOpcion() {
		return opcion;
	}

	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}

	public List<ArchivoLaboratorio> getListaArchivosLaboratorio() {
		return listaArchivosLaboratorio;
	}

	public void setListaArchivosLaboratorio(List<ArchivoLaboratorio> listaArchivosLaboratorio) {
		this.listaArchivosLaboratorio = listaArchivosLaboratorio;
	}

	public StreamedContent getArchivoLaboratorioBrochure() {
		return archivoLaboratorioBrochure;
	}

	public void setArchivoLaboratorioBrochure(StreamedContent archivoLaboratorioBrochure) {
		this.archivoLaboratorioBrochure = archivoLaboratorioBrochure;
	}

	public StreamedContent getArchivoLaboratorioTarifas() {
		return archivoLaboratorioTarifas;
	}

	public void setArchivoLaboratorioTarifas(StreamedContent archivoLaboratorioTarifas) {
		this.archivoLaboratorioTarifas = archivoLaboratorioTarifas;
	}

	public StreamedContent getArchivoLaboratorioReglamento() {
		return archivoLaboratorioReglamento;
	}

	public void setArchivoLaboratorioReglamento(StreamedContent archivoLaboratorioReglamento) {
		this.archivoLaboratorioReglamento = archivoLaboratorioReglamento;
	}

	public StreamedContent getArchivoLaboratorioCondServicio() {
		return archivoLaboratorioCondServicio;
	}

	public void setArchivoLaboratorioCondServicio(StreamedContent archivoLaboratorioCondServicio) {
		this.archivoLaboratorioCondServicio = archivoLaboratorioCondServicio;
	}
}
