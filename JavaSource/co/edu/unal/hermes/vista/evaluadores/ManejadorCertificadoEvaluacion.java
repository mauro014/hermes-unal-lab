package co.edu.unal.hermes.vista.evaluadores;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProyectoEvaluadorVistaCert;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorCertificadoEvaluacion extends ManejadorBase{
	
	private static final long serialVersionUID = 5215998351665012788L;
	private Investigador investigador;
	private InvestigadorInterno ii;
	
	private String tipoDocumento="";
	private String documento="";
	private String nombreCompletoPersona="";
	private String email ="";
	private String sede ="";
	private String facultad ="";
	private String departamento ="";
	private String institucion ="";
	private IdPersona idPersonaEvaluador;
	
	private String opcionesCertificado="";
	private boolean mostrarCertProyectos;
	private boolean mostrarCertGeneral;
	
	private List<ProyectoEvaluadorVistaCert> listaEvaluacionesRealizadas;
	private ProyectoEvaluadorVistaCert proyectoSeleccionado;	
	
	private boolean esInterno = false;
	
	public ManejadorCertificadoEvaluacion(){
		//super();
		personaActual = (Persona) sesion.getAttribute("persona");
		idPersonaEvaluador = new IdPersona();		
		ii = servicioPersona.obtenerInvestigadorInternoCompleto(personaActual.getId());
		
		if(ii != null){
			esInterno = true;
			tipoDocumento  = ii.getId().getTipoDocumento();
			documento  = ii.getId().getDocumento();
			idPersonaEvaluador.setDocumento(documento);
			idPersonaEvaluador.setTipoDocumento(tipoDocumento);
			nombreCompletoPersona = ii.getNombre1() + " " + ii.getNombre2() + " " + ii.getApellido1() + " " + ii.getApellido2();
			email = ii.getEmail();
			sede = ii.getDependencia().getSede().getNombre();
			facultad = ii.getDependencia().getFacultad().getNombre();
			departamento = ii.getDependencia().getNombre();
		}else{
			esInterno = false;
			investigador = servicioPersona.obtenerInvestigador(personaActual.getId());
			if (investigador != null){
				tipoDocumento  = investigador.getId().getTipoDocumento();
				documento  = investigador.getId().getDocumento();
				idPersonaEvaluador.setDocumento(documento);
				idPersonaEvaluador.setTipoDocumento(tipoDocumento);
				nombreCompletoPersona = investigador.getNombre1() + " " + investigador.getNombre2() + " " + investigador.getApellido1() + " " + investigador.getApellido2();
				email = investigador.getEmail();
				//institucion = investigador.getInstitucion().getNombre();
				
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha encontrado el evaluador.", "No se ha encontrado el evaluador."));
			}
		}		
	};
	
	public void cambiarTipoBusqueda()
	{
		if (opcionesCertificado.equals("proyectos")){
			mostrarCertProyectos = true;
			mostrarCertGeneral = false;
			cargarEvaluaciones();
		}else{
			mostrarCertProyectos = false;
			mostrarCertGeneral = true;
		}
	}
	
	public void cargarEvaluaciones(){		
		listaEvaluacionesRealizadas = servicioProyecto.obtenerProyectosXEvaluador(idPersonaEvaluador);
	}
		
	public void generarCertificadoProyectos()
	{
		ReporteBirt r = new ReporteBirt();
		if(esInterno){
			r.adicionarParametro("interno", "S");
		}else{
			r.adicionarParametro("interno", "N");
		}
		r.adicionarParametro("Id", proyectoSeleccionado.getIdProyecto());
		r.adicionarParametro("InvId", documento);
		r.adicionarParametro("InvTipoId", tipoDocumento);
		r.adicionarParametro("Dep", proyectoSeleccionado.getDependenciaConvocatoria());
		r.setFormato(ReporteBirt.FORMATO_PDF);			
		r.setNombreReporte("/cartas/CertificadoEvaluacionProyecto");	

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		
		try{
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			context.responseComplete();
		}		
	}
	
	public void descargarCertificadoGeneral(){
		ReporteBirt r = new ReporteBirt();
		if(esInterno){
			r.adicionarParametro("interno", "S");
			//r.adicionarParametro("Dep", proyectoSeleccionado.getDependenciaConvocatoria());
		}else{
			r.adicionarParametro("interno", "N");
		}
		r.adicionarParametro("InvId", documento);
		r.adicionarParametro("InvTipoId", tipoDocumento);
		r.setFormato(ReporteBirt.FORMATO_PDF);
		r.setNombreReporte("/cartas/CertificadoEvaluacionGeneralProyectos");

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try
		{
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			context.responseComplete();
		}
	}

	public String getNombreCompletoPersona() {
		return nombreCompletoPersona;
	}

	public void setNombreCompletoPersona(String nombreCompletoPersona) {
		this.nombreCompletoPersona = nombreCompletoPersona;
	}

	public Investigador getInvestigador() {
		return investigador;
	}

	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}

	public List<ProyectoEvaluadorVistaCert> getListaEvaluacionesRealizadas() {
		return listaEvaluacionesRealizadas;
	}

	public void setListaEvaluacionesRealizadas(
			List<ProyectoEvaluadorVistaCert> listaEvaluacionesRealizadas) {
		this.listaEvaluacionesRealizadas = listaEvaluacionesRealizadas;
	}

	public ProyectoEvaluadorVistaCert getProyectonSeleccionad() {
		return proyectoSeleccionado;
	}

	public void setEvaluacionSeleccionada(ProyectoEvaluadorVistaCert proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}

	public InvestigadorInterno getIi() {
		return ii;
	}

	public void setIi(InvestigadorInterno ii) {
		this.ii = ii;
	}

	public boolean isEsInterno() {
		return esInterno;
	}

	public void setEsInterno(boolean esInterno) {
		this.esInterno = esInterno;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getOpcionesCertificado() {
		return opcionesCertificado;
	}

	public void setOpcionesCertificado(String opcionesCertificado) {
		this.opcionesCertificado = opcionesCertificado;
	}

	public boolean isMostrarCertProyectos() {
		return mostrarCertProyectos;
	}

	public void setMostrarCertProyectos(boolean mostrarCertProyectos) {
		this.mostrarCertProyectos = mostrarCertProyectos;
	}

	public boolean isMostrarCertGeneral() {
		return mostrarCertGeneral;
	}

	public void setMostrarCertGeneral(boolean mostrarCertGeneral) {
		this.mostrarCertGeneral = mostrarCertGeneral;
	}

	public IdPersona getIdPersonaEvaluador() {
		return idPersonaEvaluador;
	}

	public void setIdPersonaEvaluador(IdPersona idPersonaEvaluador) {
		this.idPersonaEvaluador = idPersonaEvaluador;
	}

	public ProyectoEvaluadorVistaCert getProyectoSeleccionado() {
		return proyectoSeleccionado;
	}

	public void setProyectoSeleccionado(ProyectoEvaluadorVistaCert proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}

}
