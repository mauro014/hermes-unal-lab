package co.edu.unal.hermes.vista;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import co.edu.unal.hermes.bd.conexion.ConexionBDECP;
import co.edu.unal.hermes.modelo.ArchivosPreinscripcionECP;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Preinscripcion_ECP;
import co.edu.unal.hermes.modelo.Proyecto;

public class ManejadorFormalizarCursosAsistenteECP extends ManejadorBase { 

	//public static final String RUTA_ARCHIVOS = "E://";
	
	private InvestigadorInterno investigadorInterno;
	private Proyecto cursoSeleccionado;
	public Investigador investigadorActual;
	public String tipoFormalizacion;
	public List listaTiposFormalizacion;
	
	//Archivos Pago Consignacion
	private List<ArchivosPreinscripcionECP> listaArchivos;
	private List<ArchivosPreinscripcionECP> listaArchivosNuevos;
	private List<ArchivosPreinscripcionECP> listaArchivosBorrados;

	private ArchivosPreinscripcionECP archivoPre;
	
	//Archivos Soportes
	private List<ArchivosPreinscripcionECP> listaArchivosSoportes;
	private List<ArchivosPreinscripcionECP> listaArchivosNuevosSoportes;
	private List<ArchivosPreinscripcionECP> listaArchivosBorradosSoportes;

	private ArchivosPreinscripcionECP archivoPreSoportes;
	
	private Preinscripcion_ECP preins_ecp_objeto;
	
	private List<String> grupoSanguineoOpciones;
    private List<String> rhOpciones;
    
    boolean aceptaTerminos;
    private String opcionesTerminos;;

	public ManejadorFormalizarCursosAsistenteECP() {

		listaArchivos = new ArrayList<ArchivosPreinscripcionECP>();
		listaArchivosNuevos = new ArrayList<ArchivosPreinscripcionECP>();
		listaArchivosBorrados = new ArrayList<ArchivosPreinscripcionECP>();
		
		listaArchivosSoportes = new ArrayList<ArchivosPreinscripcionECP>();
		listaArchivosNuevosSoportes = new ArrayList<ArchivosPreinscripcionECP>();
		listaArchivosBorradosSoportes = new ArrayList<ArchivosPreinscripcionECP>();
		
		//preins_ecp_objeto = new Preinscripcion_ECP();
		
		Investigador investigadorActual = new Investigador();
		investigadorActual.setId(((Persona) sesion.getAttribute("persona"))
				.getId());
		this.investigadorInterno = servicioPersona
				.obtenerInvestigadorInterno(investigadorActual.getId());
		
		this.investigadorActual = servicioPersona
					.obtenerInvestigadorProyectos(((Persona) sesion
							.getAttribute("persona")).getId());
		
		cursoSeleccionado = (Proyecto) sesion.getAttribute("cursoECP");
		
		preins_ecp_objeto = (Preinscripcion_ECP) sesion.getAttribute("preinscripcionECP");
		
		String buscarPreAnterior = "Select pp from Preinscripcion_ECP pp where pp.investigador.id.documento = '"
				+ preins_ecp_objeto.getInvestigador().getId().getDocumento()
				+ "' "
				+ "and pp.investigador.id.tipoDocumento = '"
				+ preins_ecp_objeto.getInvestigador().getId().getTipoDocumento()
				+ "' order by pp.id_pre desc";

		List lista_preins_anterior = servicioGeneral.obtenerObjetos(buscarPreAnterior);
		
		if(lista_preins_anterior != null && lista_preins_anterior.size() > 1){
			Preinscripcion_ECP pr = (Preinscripcion_ECP) lista_preins_anterior.get(1);
			preins_ecp_objeto.setPersona_contacto(pr.getPersona_contacto());
			preins_ecp_objeto.setPersona_contacto(pr.getTel_contacto());
			preins_ecp_objeto.setPersona_contacto(pr.getGrupo_sanguineo());
			preins_ecp_objeto.setPersona_contacto(pr.getRh());
			preins_ecp_objeto.setPersona_contacto(pr.getEps());
			
		}
		
		if(preins_ecp_objeto == null)
			System.out.println("preins_ecp_objeto NULO - preins_ecp_objeto NULO - preins_ecp_objeto NULO - preins_ecp_objeto NULO - preins_ecp_objeto NULO - ");
		
		FacesContext.getCurrentInstance().addMessage("msgPdf", new FacesMessage(FacesMessage.SEVERITY_WARN, "Recuerde adjuntar documentos unicamente en formato PDF.",""));
		
		cargarListaTiposFormalizacion();
		cargarGrupoSanguineoOpciones();
		cargarRh();
		cargarArchivos();
		cargarArchivosSoportes();
	}
	
	public void cargarArchivos()
	{
		String sql = "Select pp from ArchivosPreinscripcionECP pp where pp.preinscripcionECP.id_pre = "+preins_ecp_objeto.getId_pre()+" and pp.tipoArchivo = 'SP'";
		List lista = servicioGeneral.obtenerObjetos(sql);
		
		for (int i = 0; i < lista.size(); i++) 
		{
			ArchivosPreinscripcionECP archivo = (ArchivosPreinscripcionECP) lista.get(i);
			listaArchivos.add(archivo);
		}
	}
	
	public void cargarArchivosSoportes()
	{
		String sql = "Select pp from ArchivosPreinscripcionECP pp where pp.preinscripcionECP.id_pre = "+preins_ecp_objeto.getId_pre()+" and pp.tipoArchivo = 'OS'";
		List lista = servicioGeneral.obtenerObjetos(sql);
		
		for (int i = 0; i < lista.size(); i++) 
		{
			ArchivosPreinscripcionECP archivo = (ArchivosPreinscripcionECP) lista.get(i);
			listaArchivosSoportes.add(archivo);
		}
	}
	
	public String guardar()
	{
		
//		if(listaArchivos.size() > 0 && tipoFormalizacion.equals("CB")) 
//		{
//			guardarArchivosPago();
//		}
//		else
//		{	
//			FacesMessage msg = new FacesMessage( FacesMessage.SEVERITY_ERROR, "","Debe adjuntar el soporte del pago");
//			FacesContext.getCurrentInstance().addMessage("growl", msg);
//			
//			System.out.println("La lista de los archivos [Soporte Pago] est· vacia");
//		}
		try
		{
			if(guardarArchivosPago() && guardarArchivosOtrosSoportes())
			{
				preins_ecp_objeto.setEstado("FE");
				servicioGeneral.guardarObjeto(preins_ecp_objeto);
				
				//ConexionBDECP.StoreProcedure("DECLARE res NUMBER; BEGIN res := UEC_SCHEMA.UECF_INSERTA_PREINSCRITO('"+preins_ecp_objeto.getInvestigador().getId().getDocumento()+"'); END;");
				ConexionBDECP.StoreProcedure("DECLARE res NUMBER; BEGIN res := UEC_SCHEMA.UECF_INSERTA_PREINSCRITO('"+preins_ecp_objeto.getInvestigador().getId().getDocumento()+"','"+preins_ecp_objeto.getInvestigador().getId().getTipoDocumento()+"','"+cursoSeleccionado.getId()+"'); END;");
				
				FacesMessage msg = new FacesMessage( FacesMessage.SEVERITY_INFO, "","Se formalizÛ correctamente la inscripciÛn al curso");
				FacesContext.getCurrentInstance().addMessage("growl", msg);
				
				sesion.removeAttribute("ManejadorFormalizarCursosAsistenteECP");
				sesion.removeAttribute("ManejadorCursosAsistenteECP");
				
				Thread.sleep(5000);
				
				return "misProyectos";
			}
			else
			{
				System.out.println("Ha ocurrido algun problema al guardar los archivos");
				FacesMessage msg = new FacesMessage( FacesMessage.SEVERITY_ERROR, "","Ha ocurrido alg˙n problema al guardar los archivos");
				FacesContext.getCurrentInstance().addMessage("growl", msg);
				
				return "formalizarCurso";
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Ha ocurrido un problema al guardar los soportes del pago [" + e.getMessage()+"]");
			return "formalizarCurso";
		}
	}
	
	public boolean guardarArchivosPago()
	{
	
		try
		{
			// save file
			for (int i = 0; i < listaArchivosNuevos.size(); i++) {
				ArchivosPreinscripcionECP ar = listaArchivosNuevos
						.get(i);
				ar.setPreinscripcionECP(preins_ecp_objeto);
				servicioGeneral.guardarObjeto(ar);

				String directorio = RUTA_ARCHIVOS + "HER_EXT_ARC_PREINS"+"//" + ar.getIdArchivo();
				//String directorio = RUTA_ARCHIVOS+"HER_EXT_ARC_PREINS//" + ar.getIdArchivo();
				/*String destination = directorio + "//" 	+ ar.getNombreArchivo();
				new File(directorio).mkdirs();*/
				copyFile(directorio, ar.getArchivoInputStream());
			}

			// borrar archivo
			for (int i = 0; i < listaArchivosBorrados.size(); i++) {
				ArchivosPreinscripcionECP ar = listaArchivosBorrados
						.get(i);
				ar.setPreinscripcionECP(preins_ecp_objeto);
				servicioGeneral.eliminarObjeto(ar);
				
				String directorio = RUTA_ARCHIVOS + "HER_EXT_ARC_PREINS"+"//" + ar.getIdArchivo();
				//String directorio = RUTA_ARCHIVOS+"HER_EXT_ARC_PREINS//"+ ar.getIdArchivo();
				if (new File(directorio).exists()
						&& new File(directorio).isDirectory()) {
					deleteWithChildren(directorio);
				}else{
					if (new File(directorio).exists()
							&& new File(directorio).isFile()) {
						new File(directorio).delete();
					}
				}

			}
			
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Ha ocurrido un problema al guardar los soportes del pago [" + e.getMessage()+"]");
			return false;
		}
	}
	
	public boolean guardarArchivosOtrosSoportes()
	{
		try
		{
			// save file
			for (int i = 0; i < listaArchivosNuevosSoportes.size(); i++) {
				ArchivosPreinscripcionECP ar = listaArchivosNuevosSoportes
						.get(i);
				ar.setPreinscripcionECP(preins_ecp_objeto);
				servicioGeneral.guardarObjeto(ar);

				 String directorio = RUTA_ARCHIVOS + "HER_EXT_ARC_PREINS"+"//" + ar.getIdArchivo();
				 //String directorio = RUTA_ARCHIVOS+"HER_EXT_ARC_PREINS//" + ar.getIdArchivo();
				/*String destination = directorio + "//" 	+ ar.getNombreArchivo();
				new File(directorio).mkdirs();*/
				copyFile(directorio, ar.getArchivoInputStream());
			}

			// borrar archivo
			for (int i = 0; i < listaArchivosBorradosSoportes.size(); i++) {
				
				ArchivosPreinscripcionECP ar = listaArchivosBorradosSoportes.get(i);
				
				ar.setPreinscripcionECP(preins_ecp_objeto);
				
				//if(ar.getIdArchivo() != null)
					servicioGeneral.eliminarObjeto(ar);
					
					String directorio = RUTA_ARCHIVOS + "HER_EXT_ARC_PREINS"+"//" + ar.getIdArchivo();
					//String directorio = RUTA_ARCHIVOS+"HER_EXT_ARC_PREINS//"+ ar.getIdArchivo();
				
					if (new File(directorio).exists() && new File(directorio).isDirectory()) {
						deleteWithChildren(directorio);
					}else{
							if (new File(directorio).exists() && new File(directorio).isFile()) {
								new File(directorio).delete();
					}
				}

			}	
			
			return true;
		}	
		catch(Exception e)
		{
			System.out.println("Ha ocurrido un problema al guardar archivos otros soportes [" + e.getMessage()+"]");
			return false;
		}
	}
	
	public void descargarArchivoSoportes()
	{
		try
		{
			if(archivoPreSoportes.getIdArchivo() != null)
				descargarArchivoGenerico("HER_EXT_ARC_PREINS",archivoPreSoportes.getIdArchivo()+"",archivoPreSoportes.getNombreArchivo());
			else
			{
				FacesMessage msg = new FacesMessage( FacesMessage.SEVERITY_INFO, "","Para poder descargar el archivo primero debe guardar los cambios");
				FacesContext.getCurrentInstance().addMessage("growl", msg);
			}
		}
		catch(Exception e)
		{
			System.out.println("Ha ocurrido un problema al DESCARGAR archivo [" + e.getMessage()+"]");
		}
		
	}
	
	public void descargarArchivo()
	{
		try
		{
			if(archivoPre.getIdArchivo() != null)
				descargarArchivoGenerico("HER_EXT_ARC_PREINS",archivoPre.getIdArchivo()+"",archivoPre.getNombreArchivo());
			else
			{
				FacesMessage msg = new FacesMessage( FacesMessage.SEVERITY_INFO, "","Para poder descargar el archivo primero debe guardar los cambios");
				FacesContext.getCurrentInstance().addMessage("growl", msg);
			}
		}
		catch(Exception e)
		{
			System.out.println("Ha ocurrido un problema al DESCARGAR archivo [" + e.getMessage()+"]");
		}
		
	}
	
	public void cargarGrupoSanguineoOpciones() 
    {
		grupoSanguineoOpciones = new ArrayList<String>();
		grupoSanguineoOpciones.add("A");
		grupoSanguineoOpciones.add("B");
		grupoSanguineoOpciones.add("AB");
		grupoSanguineoOpciones.add("O");
    }
	
	public void cargarRh() {
		rhOpciones = new ArrayList<String>();

		rhOpciones.add("RH+");
		rhOpciones.add("RH-");
	    }
	
	public void copyFile(String fileName, InputStream in) {
		try {

			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.out.println("New file created!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean deleteWithChildren(String path) {  
	    File file = new File(path);  
	    if (!file.exists()) {  
	        return true;  
	    }  
	    if (!file.isDirectory()) {  
	        return file.delete();  
	    }  
	    return this.deleteChildren(file) && file.delete();  
	}
	
	private boolean deleteChildren(File dir) {  
	    File[] children = dir.listFiles();  
	    boolean childrenDeleted = true;  
	    for (int i = 0; children != null && i < children.length; i++) {  
	        File child = children[i];  
	        if (child.isDirectory()) {  
	            childrenDeleted = this.deleteChildren(child) && childrenDeleted;  
	        }  
	        if (child.exists()) {  
	            childrenDeleted = child.delete() && childrenDeleted;  
	        }  
	    }  
	    return childrenDeleted;  
	} 
	
	public void cargarListaTiposFormalizacion()
	{
		listaTiposFormalizacion = new ArrayList<SelectItem>();
		listaTiposFormalizacion.add(new SelectItem("CB","ConsignaciÛn Bancaria"));
		listaTiposFormalizacion.add(new SelectItem("PL","Pago en LÌnea"));
	}
	
	public void subirArchivos(FileUploadEvent event) throws IOException {
		
		if (event.getFile() != null) {
			System.out.println(event.getFile().getFileName());
			ArchivosPreinscripcionECP archivo = new ArchivosPreinscripcionECP();
			archivo.setPreinscripcionECP(preins_ecp_objeto);
			archivo.setBytes(event.getFile().getContents());
			archivo.setArchivoInputStream(event.getFile().getInputstream());
			archivo.setNombreArchivo(quitarAcentos(event.getFile()
					.getFileName()));
			archivo.setEsNuevo(true);
			archivo.setTipoArchivo("SP");
			listaArchivosNuevos.add(archivo);
			listaArchivos.add(archivo);

		}

	}
	
public void subirArchivosSoportes(FileUploadEvent event) throws IOException {
		
		if (event.getFile() != null) {
			System.out.println(event.getFile().getFileName());
			ArchivosPreinscripcionECP archivo = new ArchivosPreinscripcionECP();
			archivo.setPreinscripcionECP(preins_ecp_objeto);
			archivo.setBytes(event.getFile().getContents());
			archivo.setArchivoInputStream(event.getFile().getInputstream());
			archivo.setNombreArchivo(quitarAcentos(event.getFile()
					.getFileName()));
			archivo.setEsNuevo(true);
			archivo.setTipoArchivo("OS");
			listaArchivosNuevosSoportes.add(archivo);
			listaArchivosSoportes.add(archivo);

		}

	}
	
	public void eliminarArchivo() {
		System.out.println("eliminar archivo");
		if (archivoPre != null && archivoPre.isEsNuevo()) {
			System.out.println("eliminar archivo nuevo");
			listaArchivosNuevos.remove(archivoPre);
			listaArchivos.remove(archivoPre);
			listaArchivosBorrados.add(archivoPre);
		} else {
			if (archivoPre != null && !archivoPre.isEsNuevo()) {
				System.out.println("eliminar archivo viejo");
				listaArchivos.remove(archivoPre);
				listaArchivosBorrados.add(archivoPre);
			}
		}
		/*
		 * archivoPre = new ArchivosPreinscripcionECP();
		 */
	}
	
	public void eliminarArchivoSoportes() {
		System.out.println("eliminar archivo Soportes");
		if (archivoPreSoportes != null && archivoPreSoportes.isEsNuevo()) {
			System.out.println("eliminar archivo nuevo Soportes");
			listaArchivosNuevosSoportes.remove(archivoPreSoportes);
			listaArchivosSoportes.remove(archivoPreSoportes);
			listaArchivosBorradosSoportes.add(archivoPreSoportes);
		} else {
			if (archivoPreSoportes != null && !archivoPreSoportes.isEsNuevo()) {
				System.out.println("eliminar archivo viejo Soportes");
				listaArchivosSoportes.remove(archivoPreSoportes);
				listaArchivosBorradosSoportes.add(archivoPreSoportes);
			}
		}
		/*
		 * archivoPre = new ArchivosPreinscripcionECP();
		 */
	}
	
	public String quitarAcentos(String input) {
		// Cadena de caracteres original a sustituir.
		String original = "·‡‰ÈËÎÌÏÔÛÚˆ˙˘uÒ¡¿ƒ…»ÀÕÃœ”“÷⁄Ÿ‹—Á« ";
		// Cadena de caracteres ASCII que reemplazar·n los originales.
		String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC_";
		String output = input;
		for (int i = 0; i < original.length(); i++) {
			// Reemplazamos los caracteres especiales.
			output = output.replace(original.charAt(i), ascii.charAt(i));
		}// for i
		return output;
	}

	public InvestigadorInterno getInvestigadorInterno() {
		return investigadorInterno;
	}

	public void setInvestigadorInterno(InvestigadorInterno investigadorInterno) {
		this.investigadorInterno = investigadorInterno;
	}

	public Proyecto getCursoSeleccionado() {
		return cursoSeleccionado;
	}

	public void setCursoSeleccionado(Proyecto cursoSeleccionado) {
		this.cursoSeleccionado = cursoSeleccionado;
	}

	public Investigador getInvestigadorActual() {
		return investigadorActual;
	}

	public void setInvestigadorActual(Investigador investigadorActual) {
		this.investigadorActual = investigadorActual;
	}

	public String getTipoFormalizacion() {
		return tipoFormalizacion;
	}

	public void setTipoFormalizacion(String tipoFormalizacion) {
		this.tipoFormalizacion = tipoFormalizacion;
	}

	public List getListaTiposFormalizacion() {
		return listaTiposFormalizacion;
	}

	public void setListaTiposFormalizacion(List listaTiposFormalizacion) {
		this.listaTiposFormalizacion = listaTiposFormalizacion;
	}

	public List<ArchivosPreinscripcionECP> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<ArchivosPreinscripcionECP> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public List<ArchivosPreinscripcionECP> getListaArchivosNuevos() {
		return listaArchivosNuevos;
	}

	public void setListaArchivosNuevos(
			List<ArchivosPreinscripcionECP> listaArchivosNuevos) {
		this.listaArchivosNuevos = listaArchivosNuevos;
	}

	public List<ArchivosPreinscripcionECP> getListaArchivosBorrados() {
		return listaArchivosBorrados;
	}

	public void setListaArchivosBorrados(
			List<ArchivosPreinscripcionECP> listaArchivosBorrados) {
		this.listaArchivosBorrados = listaArchivosBorrados;
	}

	public ArchivosPreinscripcionECP getArchivoPre() {
		return archivoPre;
	}

	public void setArchivoPre(ArchivosPreinscripcionECP archivoPre) {
		this.archivoPre = archivoPre;
	}

	public Preinscripcion_ECP getPreins_ecp_objeto() {
		return preins_ecp_objeto;
	}

	public void setPreins_ecp_objeto(Preinscripcion_ECP preins_ecp_objeto) {
		this.preins_ecp_objeto = preins_ecp_objeto;
	}

	public List<String> getGrupoSanguineoOpciones() {
		return grupoSanguineoOpciones;
	}

	public void setGrupoSanguineoOpciones(List<String> grupoSanguineoOpciones) {
		this.grupoSanguineoOpciones = grupoSanguineoOpciones;
	}

	public List<String> getRhOpciones() {
		return rhOpciones;
	}

	public void setRhOpciones(List<String> rhOpciones) {
		this.rhOpciones = rhOpciones;
	}

	public List<ArchivosPreinscripcionECP> getListaArchivosSoportes() {
		return listaArchivosSoportes;
	}

	public void setListaArchivosSoportes(
			List<ArchivosPreinscripcionECP> listaArchivosSoportes) {
		this.listaArchivosSoportes = listaArchivosSoportes;
	}

	public List<ArchivosPreinscripcionECP> getListaArchivosNuevosSoportes() {
		return listaArchivosNuevosSoportes;
	}

	public void setListaArchivosNuevosSoportes(
			List<ArchivosPreinscripcionECP> listaArchivosNuevosSoportes) {
		this.listaArchivosNuevosSoportes = listaArchivosNuevosSoportes;
	}

	public List<ArchivosPreinscripcionECP> getListaArchivosBorradosSoportes() {
		return listaArchivosBorradosSoportes;
	}

	public void setListaArchivosBorradosSoportes(
			List<ArchivosPreinscripcionECP> listaArchivosBorradosSoportes) {
		this.listaArchivosBorradosSoportes = listaArchivosBorradosSoportes;
	}

	public ArchivosPreinscripcionECP getArchivoPreSoportes() {
		return archivoPreSoportes;
	}

	public void setArchivoPreSoportes(ArchivosPreinscripcionECP archivoPreSoportes) {
		this.archivoPreSoportes = archivoPreSoportes;
	}

	public boolean isAceptaTerminos() {
		return aceptaTerminos;
	}

	public void setAceptaTerminos(boolean aceptaTerminos) {
		this.aceptaTerminos = aceptaTerminos;
	}

	public String getOpcionesTerminos() {
		return opcionesTerminos;
	}

	public void setOpcionesTerminos(String opcionesTerminos) {
		this.opcionesTerminos = opcionesTerminos;
	}
	
}
