package co.edu.unal.hermes.vista.requerimiento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoRequerimiento;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Requerimiento;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorRevisarRequerimientoVRIE extends ManejadorBase implements Serializable  {

	Requerimiento requerimiento;
	ArchivoRequerimiento archivoSeleccionado;

	ArchivoRequerimiento archivoRequerimiento;
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
	
	public SelectItem[] moduloItems;
	public SelectItem[] subModuloItems;
	private String dependenciaAsociada = "VRIE";
	private int nPlantillaUsuario = 199;
	private boolean esConsulta;
	private Long idRequerimiento;	
	

	public ManejadorRevisarRequerimientoVRIE() {
		
		sesion.removeAttribute("ManejadorCrearRequerimientoVRIE");
		sesion.removeAttribute("ManejadorConsultarRequerimientoVRIE");
		
		cargarListas();

		requerimiento = new Requerimiento();
		archivoRequerimiento = new ArchivoRequerimiento();

		requerimiento.tipoDoc = new TipoDocumento();
		requerimiento.idDependencia = new Dependencia();
	
		listaDocumentos = new ArrayList<ArchivoRequerimiento>();
		estadosReq = new DominioDetalle();
		tipoArcReq = new DominioDetalle();
		
		idRequerimiento = (Long) sesion.getAttribute("requerimientoRegistrado"); //VRIE
		if(idRequerimiento!=null){
			List lista = servicioGeneral.obtenerObjetoXID("Requerimiento", idRequerimiento.toString());	
			if(lista.size()>0){
				requerimiento = (Requerimiento) lista.get(0);			
			}
		}	

		String sConsulta = (String) sesion.getAttribute("esConsulta"); //VRIE
		if(sConsulta!=null && sConsulta.equals("true")){
			esConsulta = true;
		}
		
	}
		
	
	public void cargarListas() {

		
		// Estados Requerimiento

		listaEstReq = new ArrayList<DominioDetalle>();
		listaEstReq = servicioGeneral
				.obtenerListaObjetos("DominioDetalle where identificador.id = '102'");

		DominioDetalle esAux = listaEstReq.get(0);

		estadosItem = new SelectItem[listaEstReq.size()];

		for (int i = 0; i < listaEstReq.size(); i++) {
			DominioDetalle es = (DominioDetalle) listaEstReq.get(i);
			estadosItem[i] = new SelectItem(es.getDescripcion(), es.getDescripcion());
			es = null;
		}

		estadoSelReq = estadosReq!=null?estadosReq.getDescripcion(): "R";

				
		// Módulos
		List listaMod = new ArrayList();
		listaMod = servicioGeneral.obtenerListaObjetos("DominioDetalle where identificador.id = '114' and estado='0'");

		DominioDetalle aux = (DominioDetalle)listaMod.get(0);

		if(listaMod.size()>0){
			moduloItems = new SelectItem[listaMod.size()];
			
			for (int i = 0; i < listaMod.size(); i++) {
				DominioDetalle ta = (DominioDetalle) listaMod.get(i);
				moduloItems[i] = new SelectItem(ta.getIdentificador().getTipo(), ta.getDescripcion());
				ta = null;
			}

			//this.requerimiento.setModulo((String)moduloItems[0].getValue());
		}
		
	}
	
	

	public String verSolicAsignadas()
    {   
		sesion.removeAttribute("ManejadorConsultarRequerimiento");
	
		return "consultarTodosReqAsignados";
			
    }
	
	
	// ********************
	
	public void asignar(){
		
		//****Asignar Revisión ******
		String dependenciaVRIE = this.requerimiento.getModulo();
		String emailFuncionario = Correo.CORREO_HERMES;
		String nombreFuncionario = "HERMES HERMES";
		
		//Personal VRIE
		List listaPers = new ArrayList();
		listaPers = servicioGeneral.obtenerListaObjetos("DominioDetalle where identificador.id = '115'");			

		if(listaPers.size()>0){
							
			for (int i = 0; i < listaPers.size(); i++) {
				DominioDetalle ta = (DominioDetalle) listaPers.get(i);
				
				if(dependenciaVRIE.equals(ta.getDescripcion()) ){					
					emailFuncionario = ta.getEstado();
					ta = null;
					break;
				}
		
			}
		}
		
		//persona encargada
		List listaPersonas = new ArrayList();
		listaPersonas = servicioGeneral.obtenerListaObjetos("Persona where email = '" + emailFuncionario  + "'");			

		if(listaPersonas.size()>0){
			Persona per = (Persona) listaPersonas.get(0);
			nombreFuncionario = per.getNombre1() + " " + per.getApellido1();					
		}
		
		this.requerimiento.setIngenieroAsignado(nombreFuncionario);
					
		//********FIN ASIGNAR ***************
		
		//Guardar objeto
		servicioGeneral.guardarObjeto(this.requerimiento);
		
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage mensaje = new FacesMessage(
				"La información ha sido guardada correctamente con el número "
						+ requerimiento.getId());
		context.addMessage("datosGuardados", mensaje);
		
	}

	
	
	public void guardar() {
		
		boolean bandera = true;

		//Persona personaActual = servicioPersona.obtenerPersona(((Persona) sesion.getAttribute("persona")).getId());			

		if (this.requerimiento.getPersonaRequerimiento() != null ) {
			
			if (this.requerimiento.getEmailSolicitante() != null) {
			
			//Estado
			List listaEstReq = new ArrayList<DominioDetalle>();
			listaEstReq = servicioGeneral.obtenerListaObjetos("DominioDetalle where identificador.id = '102'");
			
				for (int i = 0; i < listaEstReq.size(); i++) {
					DominioDetalle es = (DominioDetalle) listaEstReq.get(i);
					if(es.getIdentificador().getTipo().equals("S")){
						this.requerimiento.setEstadoRequerimiento(es);
						this.requerimiento.setEstSelRequerimiento(es.getDescripcion());
					}
				}
				
			//Fecha solución
			this.requerimiento.setFechaEntrega(new Date());	
				
			//Guardar objeto
			servicioGeneral.guardarObjeto(this.requerimiento);
			
						
			//Correo al Solicitante
			Correo correo = new Correo();
			CorreoPlantilla cp = cargarPlantilla(nPlantillaUsuario);
			correo.setOrigen(Correo.CORREO_HERMES);
			correo.setAsunto(cp.getAsunto());
			correo.setCuerpo(cp.getCuerpo().replaceAll("<<USUARIO>>", this.requerimiento.getPersonaRequerimiento()));
			correo.setCuerpo(cp.getCuerpo().replaceAll("<<ID>>",this.requerimiento.getId().toString()));
			correo.setCuerpo(cp.getCuerpo().replaceAll("<<RTA>>",this.requerimiento.getComentarios()));
			
			System.out.println("correo ===" + correo.getCuerpo());
			
			correo.adicionarDireccion(this.requerimiento.getEmailSolicitante());
			//correo.adicionarCopiaOculta(Correo.CORREO_HERMES_COMUNICACIONES);
			servicioCorreo.enviarCorreo(correo);
			
			
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage mensaje = new FacesMessage(
				"La información ha sido guardada correctamente con el número "
						+ requerimiento.getId());
		context.addMessage("datosGuardados", mensaje);

		}else{
			
			
		}
			
	  }else{
		  FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(
					"Por favor ingrese el nombre del solicitante "
							+ requerimiento.getId());
			context.addMessage("datosGuardados", mensaje);
	  }
		
	}

	// ****************

	public String cancelar() {
		sesion.removeAttribute("Requerimiento");
		return "consultarRequerimiento";
	}

	// *******

	public List<DominioDetalle> getListaEstReq() {
		return listaEstReq;
	}

	public void setListaEstReq(List<DominioDetalle> listaEstReq) {
		this.listaEstReq = listaEstReq;
	}

	// ****

	public DominioDetalle getEstadosReq() {
		return estadosReq;
	}

	public void setEstadosReq(DominioDetalle estadosReq) {
		this.estadosReq = estadosReq;
	}

	// ****

	public String getEstadoSelReq() {
		return estadoSelReq;
	}

	public void setEstadoSelReq(String estadoSelReq) {
		this.estadoSelReq = estadoSelReq;
	}

	public SelectItem[] getEstadosItem() {
		return estadosItem;
	}

	public void setEstadosItem(SelectItem[] estadosItem) {
		this.estadosItem = estadosItem;
	}
	
	//****

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public Dependencia getDependenciaReq() {
		return dependenciaReq;
	}

	public void setDependenciaReq(Dependencia dependenciaReq) {
		this.dependenciaReq = dependenciaReq;
	}

	/*
	 * public TipoDocumento getTipoDocumentoReq() { return tipoDocumentoReq; }
	 * 
	 * public void setTipoDocumentoReq(TipoDocumento tipoDocumentoReq) {
	 * this.tipoDocumentoReq = tipoDocumentoReq; }
	 */

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

	public ArchivoRequerimiento getArchivoRequerimiento() {
		return archivoRequerimiento;
	}

	public void setArchivoRequerimiento(
			ArchivoRequerimiento archivoRequerimiento) {
		this.archivoRequerimiento = archivoRequerimiento;
	}

	//****


	public DominioDetalle getTipoArcReq() {
		return tipoArcReq;
	}

	public List<DominioDetalle> getListaTipoArcReq() {
		return listaTipoArcReq;
	}


	public void setListaTipoArcReq(List<DominioDetalle> listaTipoArcReq) {
		this.listaTipoArcReq = listaTipoArcReq;
	}


	public void setTipoArcReq(DominioDetalle tipoArcReq) {
		this.tipoArcReq = tipoArcReq;
	}

	public String getTipoArcSelReq() {
		return tipoArcSelReq;
	}

	public void setTipoArcSelReq(String tipoArcSelReq) {
		System.out.println("set*****************"+ tipoArcSelReq);
		this.tipoArcSelReq = tipoArcSelReq;
	}

	public SelectItem[] getTipoArcItem() {
		return tipoArcItem;
	}

	public void setTipoArcItem(SelectItem[] tipoArcItem) {
		this.tipoArcItem = tipoArcItem;
	}

	public UploadedFile getArchivoCargarUno() {
		return archivoCargarUno;
	}

	public void setArchivoCargarUno(UploadedFile archivoCargarUno) {
		this.archivoCargarUno = archivoCargarUno;
	}

	// ****

	public ArchivoRequerimiento getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(ArchivoRequerimiento archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	// ****

	public List<ArchivoRequerimiento> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<ArchivoRequerimiento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public String getMensajeAdjuntaDocumentos() {
		return mensajeAdjuntaDocumentos;
	}

	public void setMensajeAdjuntaDocumentos(String mensajeAdjuntaDocumentos) {
		this.mensajeAdjuntaDocumentos = mensajeAdjuntaDocumentos;
	}

	public String getErrorValidacion() {
		return errorValidacion;
	}

	public void setErrorValidacion(String errorValidacion) {
		this.errorValidacion = errorValidacion;
	}

	//
	//
	// public List<DominioDetalle> getListaTipoArchivoReq() {
	// return listaTipoArchivoReq;
	// }
	//
	//
	//
	//
	// public void setListaTipoArchivoReq(List<DominioDetalle>
	// listaTipoArchivoReq) {
	// this.listaTipoArchivoReq = listaTipoArchivoReq;
	// }

	public String getNombrePrueba() {
		return "prueba";
	}

	public ArchivoRequerimiento getArchivo() {
		return archivo;
	}

	public void setArchivo(ArchivoRequerimiento archivo) {
		this.archivo = archivo;
	}


	public SelectItem[] getModuloItems() {
		return moduloItems;
	}


	public void setModuloItems(SelectItem[] moduloItems) {
		this.moduloItems = moduloItems;
	}
	
	public SelectItem[] getSubModuloItems() {
		return subModuloItems;
	}


	public void setSubModuloItems(SelectItem[] subModuloItems) {
		this.subModuloItems = subModuloItems;
	}


	public boolean isEsConsulta() {
		return esConsulta;
	}


	public void setEsConsulta(boolean esConsulta) {
		this.esConsulta = esConsulta;
	}
	
	public Long getIdRequerimiento() {
		return idRequerimiento;
	}


	public void setIdRequerimiento(Long idRequerimiento) {
		this.idRequerimiento = idRequerimiento;
	}



}
