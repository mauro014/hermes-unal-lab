/*
 * Created on 30-sep-2005
 */
package co.edu.unal.hermes.vista.proyectos;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.datalist.HtmlDataList;

import co.edu.unal.hermes.modelo.Contrapartida;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.JornadaDocente;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorProyectoBusqueda extends ManejadorBase{
	
	private boolean error = false;
	
	private int opcion = 1;
	
    public String nombreConvocatoria;
    
    public String nombreModalidad;
	
	private String paginaActual;
    
    private Investigador responsable = null;
    
    private Dependencia dependenciaProyecto;
    
    private String nombreFacultad;
    
    private String nombreDependencia;
    
    private String nombreSede;
    
    private String emailPersona;
	
	public ManejadorProyectoBusqueda(){
        super();
        
        cargarProyecto();
        
        nombreConvocatoria = "";
        
        nombreModalidad = "";
        
        nombreSede = "";
        
        Modalidad modalidad;
        
        if(proyectoActual!=null){
        	modalidad = proyectoActual.getModalidad();
        }else{
        	modalidad = new Modalidad();
        }
        
        if(modalidad instanceof Convocatoria){
            Convocatoria c = (Convocatoria) modalidad;
            nombreModalidad = c.getTitulo();
            
            if(c.getPadre()!=null){
            	nombreConvocatoria = c.getPadre().getTitulo();
            }
            else {
            	nombreConvocatoria=c.getTitulo();
            }
        }else if(modalidad instanceof JornadaDocente){
            JornadaDocente jornadaDocente = (JornadaDocente) modalidad; 
            nombreModalidad = jornadaDocente.getDescripcion();
        }else if(modalidad instanceof Contrapartida){             
            nombreModalidad = "Contrapartida";
        }else {
            nombreModalidad = "SIN MODALIDAD";
        }
        
        cargarPaginaActual();
        
        responsable = proyectoActual.getResponsable();
        
        int arroba= responsable.getEmail().indexOf("@");
		if(arroba!=-1){

			setEmailPersona(responsable.getEmail().substring(0, arroba));
		}else{
			setEmailPersona(responsable.getEmail());
		}
		if(responsable != null)
	    {
	        if(proyectoActual.isEsInvestigacion()){
		      
		        	if(responsable.getNombre22()==null)responsable.setNombre22("");
					nombreResponsable = responsable.getNombre11().trim() + " " + responsable.getNombre22().trim()+" "+responsable.getApellido11().trim() + " "
					+ " " + responsable.getApellido22().trim();
					
					dependenciaProyecto = this.servicioDependencia.obtenerDependencia(responsable.getId());
					if(dependenciaProyecto != null)
					{
						nombreDependencia = dependenciaProyecto.getNombre();
						if(dependenciaProyecto.getFacultad() != null)
							nombreFacultad = dependenciaProyecto.getFacultad().getNombre();
						if(dependenciaProyecto.getSede() != null)
							nombreSede = dependenciaProyecto.getSede().getNombre();
					}
		         
	        }else{
	        	nombreResponsable = responsable.getNombre11();
	        }
	    }
        
    }
	
	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
	
	private void cargarProyecto(){
		this.error = false;
		if(this.request.getParameter("idProyecto") != null && !this.request.getParameter("idProyecto").equals("") && 
				(this.request.getParameter("tipo") == null || (this.request.getParameter("tipo") != null  && this.request.getParameter("tipo").equals("0")))){
			try{
				proyectoActual = servicioProyecto.obtenerResumenProyecto(new Long(this.request.getParameter("idProyecto"))); 
				if(!(proyectoActual.getEstadoProyecto().getId().equals("A") || proyectoActual.getEstadoProyecto().getId().equals("AP") || proyectoActual.getEstadoProyecto().getId().equals("F")))
					proyectoActual = null;	
				if(proyectoActual == null){
					this.error = true;
				}
				sesion.setAttribute("proyectoBusqueda",proyectoActual);
			}
			catch(NumberFormatException nfe){
				this.error = true;
			}
		}else if(this.request.getParameter("idProyecto") != null && !this.request.getParameter("idProyecto").equals("") && 
				(this.request.getParameter("tipo") == null || (this.request.getParameter("tipo") != null  && this.request.getParameter("tipo").equals("1")))){
			try{
				
				proyectoActual = servicioProyecto.obtenerResumenProyectoExtension(new Long(this.request.getParameter("idProyecto")));
				sesion.setAttribute("proyectoBusqueda",proyectoActual);
			}catch(Exception e){
				
			}
			
		}else{
			proyectoActual = (Proyecto) sesion.getAttribute("proyectoBusqueda");
			sesion.removeAttribute("proyectoBusqueda");
			if(proyectoActual == null){
				this.error = true;
			}
		}
		if(this.error){
			this.opcion = 0;
		}
	}
	
	public int getOpcion() {
		return opcion;
	}

	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}
	
	public String getTituloActual(){
		if(getOpcion1()) return "titulo_info_general.png";
		return "";
	}
	
	public boolean getOpcion1(){
		if(this.opcion == 1) return true;
		return false;
	}
	
	public void reporteProyectoBusqueda() throws SQLException {

		String id = proyectoActual.getId().toString();
		
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("pry", id);
		r.setNombreReporte("/portafolio/Proyecto");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}
	
	public void reporteProyectoBusquedaExtension() throws SQLException {

		String id = proyectoActual.getId().toString();
		
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("pry", id);
		r.setNombreReporte("/portafolio/Proyecto-extension");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}
	
	public boolean getVisibleResumen(){
		if(proyectoActual.getResumen().trim().length() > 0) return true;
		return false;
	}
	
	public boolean getVisibleSede(){
		if(nombreSede.trim().length() > 0) return true;
		return false;
	}
	
	public boolean getVisibleFacultad(){
		if(nombreFacultad.trim().length() > 0) return true;
		return false;
	}
	
	public boolean getVisibleDependencia(){
		if(nombreDependencia.trim().length() > 0) return true;
		return false;
	}
	
	public boolean getVisibleConvocatoria(){
		if(nombreConvocatoria.trim().length() > 0) return true;
		return false;
	}
	
	public boolean getVisibleModalidad(){
		if(nombreModalidad.trim().length() > 0) return true;
		return false;
	}
	
	public boolean getVisibleLugar(){
		try{
			if(proyectoActual.getLugar().trim().length() > 0) return true;
		}
		catch(NullPointerException npe){
			return false;
		}
		return false;
	}
	
    public String getNombreModalidad() {              
        return nombreModalidad;        
    }
    
    public void setNombreModalidad(String nombreModalidad) {
        this.nombreModalidad = nombreModalidad;
    }    

	public String getNombreSede() {
		return nombreSede;
	}

	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
	}

	public String getNombreFacultad() {
		return nombreFacultad;
	}

	public void setNombreFacultad(String nombreFacultad) {
		this.nombreFacultad = nombreFacultad;
	}

	public String getNombreDependencia() {
		return nombreDependencia;
	}

	public void setNombreDependencia(String nombreDependencia) {
		this.nombreDependencia = nombreDependencia;
	}
	
	public String getInformacionBasica() {
		return this.paginaActual + "&opcion=1";
	}
	
	private void cargarPaginaActual(){
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		
		String viewId = "/pages/Consultas/Proyecto.xhtml";
		
		viewId = extContext.getRequestContextPath() + viewId + '?' + "idProyecto" + "=" + proyectoActual.getId();
		
		this.paginaActual = context.getExternalContext().encodeActionURL(viewId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    
    

	








	private HtmlDataList dataListInvestigadoresProyecto;  
    
    public String nombreGrupo;
    public String nombreResponsable;
    private Date fechaInicio;
    
    private Proyecto proyectoActual = null; //proyecto ACTUALMENTE CARGADO
    private Grupo grupoActual = null;
    
    public Dependencia getDependenciaProyecto() {
		return dependenciaProyecto;
	}

	public void setDependenciaProyecto(Dependencia dependenciaProyecto) {
		this.dependenciaProyecto = dependenciaProyecto;
	}

	
            
    public String buscarResponsable(){
        if(responsable != null){
            responsable = servicioPersona.obtenerResumenInvestigador(responsable.getId());
		 	InvestigadorInterno investigadorInterno = servicioPersona.obtenerInvestigadorInterno(responsable.getId());
		 	if(investigadorInterno != null){
		 		investigadorInterno = servicioPersona.obtenerInvestigadorClasificacionConocimiento(responsable.getId());
	 		 	responsable.setClasificacionesConocimiento(investigadorInterno.getClasificacionesConocimiento());		 		
		 	}
		 	else{
	 		 	responsable.setClasificacionesConocimiento(new HashSet());	 		
		 	}
            sesion.setAttribute("investigadorBusqueda", responsable);
            return "successPersona";
        }        
        return "fails";
    }
    
    public String buscarInvestigador(){        
        InvestigadorProyecto invProyecto = (InvestigadorProyecto) dataListInvestigadoresProyecto.getRowData();
        Investigador investigador = invProyecto.getInvestigador(); 
        investigador = servicioPersona.obtenerResumenInvestigador(investigador.getId());
	 	InvestigadorInterno investigadorInterno = servicioPersona.obtenerInvestigadorInterno(investigador.getId());
	 	if(investigadorInterno != null){
	 		investigadorInterno = servicioPersona.obtenerInvestigadorClasificacionConocimiento(investigador.getId());
	 		investigador.setClasificacionesConocimiento(investigadorInterno.getClasificacionesConocimiento());		 		
	 	}
	 	else{
	 		investigador.setClasificacionesConocimiento(new HashSet());	 		
	 	}
        sesion.setAttribute("investigadorBusqueda", investigador);
        return "successPersona";        
    }
    
    public String buscarGrupo(){
        if(grupoActual != null){
            this.grupoActual = servicioGrupo.obtenerResumenGrupo(grupoActual.getId());
            sesion.setAttribute("grupoBusqueda", grupoActual);            
            return "successGrupo";
        }        
        return "fails";
    }
  
    public Proyecto getProyectoActual() {
        return proyectoActual;
    }
    
    public void setProyectoActual(Proyecto proyectoActual) {
        this.proyectoActual = proyectoActual;
    }
    
    public String getNombreGrupo() {              
        return nombreGrupo;        
    }
    
    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getNombreResponsable() {              
        return nombreResponsable;
    }
    
    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }    
    public HtmlDataList getDataListInvestigadoresProyecto() {
        return dataListInvestigadoresProyecto;
    }
    public void setDataListInvestigadoresProyecto(
            HtmlDataList dataListInvestigadoresProyecto) {
        this.dataListInvestigadoresProyecto = dataListInvestigadoresProyecto;
    }

	public String getNombreConvocatoria() {
		return nombreConvocatoria;
	}

	public void setNombreConvocatoria(String nombreConvocatoria) {
		this.nombreConvocatoria = nombreConvocatoria;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getEmailPersona() {
		return emailPersona;
	}

	public void setEmailPersona(String emailPersona) {
		this.emailPersona = emailPersona;
	}
}
