package co.edu.unal.hermes.vista.requerimiento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.dao.DataIntegrityViolationException;

import co.edu.unal.hermes.modelo.ArchivoRequerimiento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Requerimiento;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.vista.ManejadorBase;


public class ManejadorConsultarRequerimientoVRIE extends ManejadorBase implements Serializable {

	private List requerimientoLista;
	private List requerimientoListaCompleta;
	private List inconvenienteListaCompleta;
	private List listaReqAsignados;
	private Requerimiento requerimientoSeleccionado;
	private Requerimiento requerimientoEditar;
	private Requerimiento requerimiento;

	
	TipoDocumento tipoDocumentoReq;
	
	
	private DataTable tablaArchivos;
	private ArchivoRequerimiento archivoSeleccionado;

	
	ArchivoRequerimiento archivo;
	List<Dependencia> dependenciasUN; 
	List<TipoDocumento> listaTipoDocumento;

	List<DominioDetalle> listaEstReq;
	DominioDetalle estadosReq;
	String estadoSelReq;

	List<DominioDetalle> listaTipoArcReq;
	DominioDetalle tipoArcReq;
	String tipoArcSelReq;

	private UploadedFile archivoCargarUno;
	List<ArchivoRequerimiento> listaDocumentos;
	private String errorValidacion;
	private String mensajeAdjuntaDocumentos = "";
	
	Dependencia dependenciaReq;

	public SelectItem[] dependenciaItem;
	public SelectItem[] tipoDocumentoItem;
	public SelectItem[] estadosItem;
	public SelectItem[] tipoArcItem;
	
	
	
	
	@SuppressWarnings("deprecation")


	public ManejadorConsultarRequerimientoVRIE() {

		sesion.removeAttribute("ManejadorCrearRequerimientoVRIE");
		sesion.removeAttribute("ManejadorConsultarRequerimientoVRIE");
		
				
		tipoDocumentoReq = new TipoDocumento();
		dependenciaReq = new Dependencia();
		estadosReq = new DominioDetalle();
		tipoArcReq = new DominioDetalle();
		requerimientoLista = new ArrayList();
		
		requerimientoListaCompleta = new ArrayList();		
		requerimientoListaCompleta = servicioGeneral.obtenerObjetos(Requerimiento.class,"from Requerimiento r where r.tipo='Mejora' and r.dependenciaAsociada = 'VRIE' order by r.id" );	
			
		Persona personaActual = servicioPersona.obtenerPersona(((Persona) sesion.getAttribute("persona")).getId());			

		listaReqAsignados = new ArrayList();
		String nombreIngeniero = personaActual.getNombre1() + " " + personaActual.getApellido1();
		
		if (personaActual != null) {			
			requerimientoLista = servicioGeneral.obtenerObjetos(Requerimiento.class,"from Requerimiento r where PER_ID='" +  personaActual.getId().getDocumento()   
					+ "' order by r.id"  );				
			
			listaReqAsignados = servicioGeneral.obtenerObjetos(Requerimiento.class,"from Requerimiento r where r.ingenieroAsignado = '" + nombreIngeniero +  "' and r.dependenciaAsociada = 'VRIE' order by r.id"  );
			
		}else{
			requerimientoLista = servicioGeneral.obtenerObjetos(Requerimiento.class,"from Requerimiento r where r.dependenciaAsociada = 'VRIE' order by r.id"  );
			listaReqAsignados = servicioGeneral.obtenerObjetos(Requerimiento.class,"from Requerimiento r where r.dependenciaAsociada = 'VRIE' order by r.id"  );
		}
		
		Long idRequerimiento = (Long) sesion.getAttribute("requerimientoRegistrado"); //VRIE
		if(idRequerimiento!=null){
			List lista = servicioGeneral.obtenerObjetoXID("Requerimiento", idRequerimiento.toString());	
			if(lista.size()>0){
				requerimiento = (Requerimiento) lista.get(0);			
			}
		}	

		setRequerimientoSeleccionado(requerimiento);
		
	}

	public void cargarListas() {

//		Dependencias
		
		dependenciasUN = new ArrayList<Dependencia>();
		dependenciasUN = servicioGeneral.obtenerListaObjetos(Dependencia.class);
		
		Dependencia dAux = dependenciasUN.get(0);

		dependenciaReq.setId(dAux.getId());
		dependenciaReq.setNombre(dAux.getNombre());
				
		dependenciaItem = new SelectItem[dependenciasUN.size()];

		for (int i = 0; i < dependenciasUN.size(); i++) {
			Dependencia dd = (Dependencia) dependenciasUN.get(i);
			dependenciaItem[i] = new SelectItem(dd.getId(), dd.getNombre());
		}
		

//      Tipos Documento
		
		listaTipoDocumento = new ArrayList<TipoDocumento>();
		listaTipoDocumento = servicioGeneral.obtenerListaObjetos(TipoDocumento.class);
		
		TipoDocumento tdAux = listaTipoDocumento.get(0);

		tipoDocumentoReq.setId(tdAux.getId());
		tipoDocumentoReq.setNombre(tdAux.getNombre());
		
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());	

		}
		
//      Estados Requerimiento
		
		listaEstReq = new ArrayList<DominioDetalle>();
		listaEstReq = servicioGeneral.obtenerListaObjetos("DominioDetalle where identificador.id = '102'");

		DominioDetalle ddAux = listaEstReq.get(0);
		
		estadosReq.setIdentificador(ddAux.getIdentificador());
		estadosReq.setDescripcion(ddAux.getDescripcion());
		estadosItem = new SelectItem[listaEstReq.size()];


		for (int i = 0; i < listaEstReq.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaEstReq.get(i);
			estadosItem[i] = new SelectItem( dd.getDescripcion(), dd.getDescripcion());	
			dd = null;
		}

		estadoSelReq = estadosReq.getDescripcion() ;

	
	
	
	//      Tipos de Archivo Requerimiento

	listaTipoArcReq = new ArrayList<DominioDetalle>();
	listaTipoArcReq = servicioGeneral.obtenerListaObjetos("DominioDetalle where identificador.id = '104'");
	
	DominioDetalle taAux = listaTipoArcReq.get(0);

	tipoArcReq.setIdentificador(taAux.getIdentificador());
	tipoArcReq.setDescripcion(taAux.getDescripcion());
	tipoArcItem = new SelectItem[listaTipoArcReq.size()];

	for (int i = 0; i < listaTipoArcReq.size(); i++) {
		DominioDetalle ta = (DominioDetalle) listaTipoArcReq.get(i);
		tipoArcItem[i] = new SelectItem( ta.getDescripcion(), ta.getDescripcion());	
		
		ta = null;
	}

	tipoArcSelReq = tipoArcReq.getDescripcion() ;

	}

//******	
	
//	public void agregarArchivoObligatorio() {
//		try {
//
//			if (archivo.getBytes() != null) {
//
//				int i = archivoObligatorio.getName().lastIndexOf("\\");
//
//				ArchivoConvocatoria archivo = new ArchivoConvocatoria();
//				archivo.setBytes(archivoObligatorio.getBytes());
//				archivo.setNombre(archivoObligatorio.getName().substring(i + 1));
//				archivo.setFecha(Calendar.getInstance().getTime());
//				archivo.setConvocatoria(convocatoriaAlianzas.getId().toString());
//				archivo.setTipoArchivo(desTipoArchivo);
//
//				listaArchivosObligatoriosSel.add(archivo);
//			}
//
//		} catch (Exception x) {
//			System.out.println(x.toString());
//
//		}
//	}

	
	
	

//******


	public String editarRequerimiento() {
		
		cargarListas();
		requerimientoEditar = new Requerimiento();
		requerimientoEditar = requerimientoSeleccionado;
		sesion.removeAttribute("ManejadorEditarRequerimiento");
		sesion.setAttribute("requerimientoEditable", requerimientoSeleccionado.getId());
		return "editarRequerimiento";
	}
	
	public String editarRequerimientoCoord() {
		
		/*cargarListas();
		requerimientoEditar = new Requerimiento();
		requerimientoEditar = requerimientoSeleccionado;
		sesion.removeAttribute("ManejadorCrearRequerimientoVRIE");
		sesion.setAttribute("requerimientoRegistrado", requerimientoSeleccionado.getId());
		return "CrearRequerimientoVRIE";*/
		sesion.removeAttribute("ManejadorRevisarRequerimientoVRIE");
		sesion.setAttribute("requerimientoRegistrado", requerimientoSeleccionado.getId());
		return "EditarRequerimientoVRIECoord";
	}
	
	public String consultarRequerimiento() {
		
		cargarListas();
		sesion.removeAttribute("ManejadorEditarRequerimiento");
		sesion.setAttribute("requerimientoEditable", requerimientoSeleccionado.getId());
		return "ConsultarRequerimientoPorId";
	}
	
	public String consultarRequerimientoCoord() {
		
		/*cargarListas();
		requerimientoEditar = new Requerimiento();
		requerimientoEditar = requerimientoSeleccionado;
		sesion.removeAttribute("ManejadorCrearRequerimientoVRIE");
		sesion.setAttribute("requerimientoRegistrado", requerimientoSeleccionado.getId());
		sesion.setAttribute("esConsulta", "true");
		return "CrearRequerimientoVRIE";*/
		sesion.removeAttribute("ManejadorRevisarRequerimientoVRIE");
		sesion.setAttribute("requerimientoRegistrado", requerimientoSeleccionado.getId());
		return "EditarRequerimientoVRIECoord";
	}

//**********
	
	public void insertarArchivo(FileUploadEvent event) {
		archivoCargarUno = event.getFile();
		if (archivoCargarUno != null) {
			try {
				archivo = new ArchivoRequerimiento();
				int i = archivoCargarUno.getFileName().lastIndexOf("\\");

				archivo.setBytes(archivoCargarUno.getContents());

				archivo.setNombre(archivoCargarUno.getFileName().substring(i + 1));
				archivo.setRequerimiento(requerimientoSeleccionado);
				
				archivo.setTipoArchivoReq(tipoArcSelReq);
				System.out.println(archivo.tipoArchivoReq);
		
								
				archivo.setArchivoInputStream(event.getFile().getInputstream());
				
				requerimientoSeleccionado.adicionarArchivo(archivo);
				
				listaDocumentos.add(archivo);
				


			} catch (DataIntegrityViolationException ex) {
				System.out.println(ex.toString());
				if (ex.getMessage().indexOf("AD_COMBINACION01_UK") > 0)
					errorValidacion = "Ya existe un archivo con este nombre";
				else
					errorValidacion = "Ocurrio un error inesperado al publicar el archivo";
			} catch (Exception ex) {
				System.out.println(ex.toString());
				errorValidacion = "Ocurrio un error inesperado al publicar el archivo";
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(
					"No se ha seleccionado ningun archivo.");
			mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("datosGuardados", mensaje);
		}
	}




//***********************
	
	


	public void eliminarArchivo(){
		ArchivoRequerimiento archivo = archivoSeleccionado;
		requerimiento.borrarArchivo(archivo);
	}
	
	
	public void descargarArchivo()
	{
		try
		{
			if(archivoSeleccionado.getId() != null)
				descargarArchivoGenerico("HER_ARCHIVO_REQUERIMIENTO",archivoSeleccionado.getId()+"",archivoSeleccionado.getNombre());
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

	
	
	
	
	
//**********
	
	public void guardar() {

		servicioGeneral.guardarObjeto(requerimientoEditar);
		
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage mensaje = new FacesMessage(
				"La información ha sido guardada correctamente con el número " + requerimientoEditar.getId());
		context.addMessage("datosGuardados", mensaje);

	}



	public String cancelar() {
		sesion.removeAttribute("ManejadorCrearRequerimiento");
		return "consultarRequerimiento";
	}

	
	
	


	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}
	
	
	public List getRequerimientoLista() {
		return requerimientoLista;
	}

	public void setRequerimientoLista(List requerimientoLista) {
		this.requerimientoLista = requerimientoLista;
	}

	public void setRequerimientoSeleccionado(Requerimiento requerimientoSeleccionado) {
		this.requerimientoSeleccionado = requerimientoSeleccionado;
	}

	public Requerimiento getRequerimientoSeleccionado() {
		return requerimientoSeleccionado;
	}

	public Requerimiento getRequerimientoEditar() {
		return requerimientoEditar;
	}

	public void setRequerimientoEditar(Requerimiento requerimientoEditar) {
		this.requerimientoEditar = requerimientoEditar;
	}

	public List<Dependencia> getDependenciasUN() {
		return dependenciasUN;
	}

	public void setDependenciasUN(List<Dependencia> dependenciasUN) {
		this.dependenciasUN = dependenciasUN;
	}

	public List<TipoDocumento> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public List<DominioDetalle> getListaEstReq() {
		return listaEstReq;
	}

	public void setListaEstReq(List<DominioDetalle> listaEstReq) {
		this.listaEstReq = listaEstReq;
	}

	public TipoDocumento getTipoDocumentoReq() {
		return tipoDocumentoReq;
	}

	public void setTipoDocumentoReq(TipoDocumento tipoDocumentoReq) {
		this.tipoDocumentoReq = tipoDocumentoReq;
	}

	public Dependencia getDependenciaReq() {
		return dependenciaReq;
	}

	public void setDependenciaReq(Dependencia dependenciaReq) {
		this.dependenciaReq = dependenciaReq;
	}

	public DominioDetalle getEstadosReq() {
		return estadosReq;
	}

	public void setEstadosReq(DominioDetalle estadosReq) {
		this.estadosReq = estadosReq;
	}

	public String getEstadoSelReq() {
		return estadoSelReq;
	}

	public UploadedFile getArchivoCargarUno() {
		return archivoCargarUno;
	}

	public void setArchivoCargarUno(UploadedFile archivoCargarUno) {
		this.archivoCargarUno = archivoCargarUno;
	}

	public List<ArchivoRequerimiento> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<ArchivoRequerimiento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public String getErrorValidacion() {
		return errorValidacion;
	}

	public void setErrorValidacion(String errorValidacion) {
		this.errorValidacion = errorValidacion;
	}

	public String getMensajeAdjuntaDocumentos() {
		return mensajeAdjuntaDocumentos;
	}

	public void setMensajeAdjuntaDocumentos(String mensajeAdjuntaDocumentos) {
		this.mensajeAdjuntaDocumentos = mensajeAdjuntaDocumentos;
	}

	public DataTable getTablaArchivos() {
		return tablaArchivos;
	}

	public void setTablaArchivos(DataTable tablaArchivos) {
		this.tablaArchivos = tablaArchivos;
	}

	public ArchivoRequerimiento getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(ArchivoRequerimiento archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public void setEstadoSelReq(String estadoSelReq) {
		this.estadoSelReq = estadoSelReq;
	}

	public SelectItem[] getDependenciaItem() {
		return dependenciaItem;
	}

	public void setDependenciaItem(SelectItem[] dependenciaItem) {
		this.dependenciaItem = dependenciaItem;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public SelectItem[] getEstadosItem() {
		return estadosItem;
	}

	public void setEstadosItem(SelectItem[] estadosItem) {
		this.estadosItem = estadosItem;
	}

	public ArchivoRequerimiento getArchivo() {
		return archivo;
	}

	public void setArchivo(ArchivoRequerimiento archivo) {
		this.archivo = archivo;
	}

	public List<DominioDetalle> getListaTipoArcReq() {
		return listaTipoArcReq;
	}

	public void setListaTipoArcReq(List<DominioDetalle> listaTipoArcReq) {
		this.listaTipoArcReq = listaTipoArcReq;
	}

	public DominioDetalle getTipoArcReq() {
		return tipoArcReq;
	}

	public void setTipoArcReq(DominioDetalle tipoArcReq) {
		this.tipoArcReq = tipoArcReq;
	}

	public String getTipoArcSelReq() {
		return tipoArcSelReq;
	}

	public void setTipoArcSelReq(String tipoArcSelReq) {
		this.tipoArcSelReq = tipoArcSelReq;
	}

	public SelectItem[] getTipoArcItem() {
		return tipoArcItem;
	}

	public void setTipoArcItem(SelectItem[] tipoArcItem) {
		this.tipoArcItem = tipoArcItem;
	}

	public List getRequerimientoListaCompleta() {
		return requerimientoListaCompleta;
	}

	public void setRequerimientoListaCompleta(List requerimientoListaCompleta) {
		this.requerimientoListaCompleta = requerimientoListaCompleta;
	}

	public List getInconvenienteListaCompleta() {
		return inconvenienteListaCompleta;
	}

	public void setInconvenienteListaCompleta(List inconvenienteListaCompleta) {
		this.inconvenienteListaCompleta = inconvenienteListaCompleta;
	}

	public List getListaReqAsignados() {
		return listaReqAsignados;
	}

	public void setListaReqAsignados(List listaReqAsignados) {
		this.listaReqAsignados = listaReqAsignados;
	}
	
}