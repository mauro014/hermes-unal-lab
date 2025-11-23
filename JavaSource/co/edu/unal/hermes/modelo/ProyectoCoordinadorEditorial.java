/*
 * Created on 27-oct-2006
 * Edited by Mauricio Amaya Ríos
 * Date: 09/02/2015
 */
package co.edu.unal.hermes.modelo;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Iterator;

import org.hibernate.Hibernate;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.servicioGeneral.IServicioGeneral;
import co.edu.unal.hermes.modelo.servicioPersona.IServicioPersona;

public class ProyectoCoordinadorEditorial implements java.io.Serializable {
	
	private static final long serialVersionUID = -3094334601240794927L;
	public static final String COORDINADOR_PRUEBAS = "41654907";
    
	private Long id;
	public Long idProyecto;
    String nombreProyecto;
    String perId;
    String perNombre;
    String tdoId;
    String estadoProyecto="";
    String estadoProyectoAnterior="";
    private String nombreEstadoProyecto="";
    
	String idCoordinador;
	
	String idFacultad    ;
	String nombreFacultad;//id, nombre de la facultad;
	Proyecto proyecto;    //id, nombre
	
	String perRevision;
    Persona personRevision;
	String perRevisionNombre;
	String nombreRevision;
	String nombreEvaluacion;
	String nombreSeguimiento;
	String justificacion;
	boolean esCoordinadorSeguimiento;
	boolean esCoordinadorRequisitos;
	boolean esCoordinadorEvaluacion;
	boolean esCoordinadorConcepto;
	private String coordinadoresAsesor;
	
	String perConcepto;
	String nombreConcepto;
	String tdoIdConcepto;
	
	public String getPerNombre() {
		return perNombre;
	}

	public void setPerNombre(String perNombre) {
		this.perNombre = perNombre;
	}

	public Persona getPersonRevision() {
		return personRevision;
	}

	public void setPersonRevision(Persona personRevision) {
		this.personRevision = personRevision;
	}

	public String getPerRevisionNombre() {
		return perRevisionNombre;
	}

	public void setPerRevisionNombre(String perRevisionNombre) {
		this.perRevisionNombre = perRevisionNombre;
	}

	public String getPerEvaluacionNombre() {
		return perEvaluacionNombre;
	}

	public void setPerEvaluacionNombre(String perEvaluacionNombre) {
		this.perEvaluacionNombre = perEvaluacionNombre;
	}

	String tdoId2;
	String perEvaluacion;
	String perEvaluacionNombre;
	String tdoId3;
	
	
	IServicioPersona servicioPersona;
	IServicioGeneral servicioGeneral;
	
	public ProyectoCoordinadorEditorial(){
	    
	}
	
	public ProyectoCoordinadorEditorial(String idCoordinador,Proyecto p, IServicioPersona servicioPersona,IServicioGeneral servicioGeneral){
		this.servicioPersona=servicioPersona;
		this.servicioGeneral=servicioGeneral;
		//this.idCoordinador=idCoordinador;
		proyecto=p;
		//SE ASIGNA LA FACULTAD ASOCIADA AL INVESTIGADOR PRINCIPAL DEL PROYECTO
	    Iterator<InvestigadorProyecto> it=p.getInvestigadoresProyecto().iterator();
		boolean principalEncontrado=false;
		while(!principalEncontrado && it.hasNext()){
			InvestigadorProyecto ip=(InvestigadorProyecto)it.next();
			if(ip.getTipo().getId().equals("P")){
				principalEncontrado=true;
				InvestigadorInterno ii=servicioPersona.obtenerInvestigadorInterno(ip.getInvestigador().getId());
				
				/*personRevision = (Persona)servicioGeneral.obtenerObjeto(new ProyectoCoordinador(), perId);
				perRevisionNombre = personRevision.getNombre1();*/
				
				if(ii!=null){
					Dependencia d=ii.getDependencia();											
					if(d.getEsFacultad().booleanValue()){
						nombreFacultad=d.getNombre();
					}else{					
						if(d.getFacultad()!=null && d.getFacultad().getId()!=null){							
								Dependencia facultad=(Dependencia)servicioGeneral.obtenerObjeto(new Dependencia(),d.getFacultad().getId());
								nombreFacultad=facultad.getNombre();																						
						}else{
							nombreFacultad=d.getNombre();
						}
					}	
				}else{
					nombreFacultad="";
				}				
			}
		}
	}
	
	public ProyectoCoordinadorEditorial(Proyecto p, IServicioPersona servicioPersona, IServicioGeneral servicioGeneral){
		this.servicioPersona=servicioPersona;
		this.servicioGeneral=servicioGeneral;
		proyecto=p;
		//SE ASIGNA EL COORDINADOR ASOCIADO ANTERIORMENTE
		Iterator it=p.getAsesores().iterator();
		if(it.hasNext()){
		  Persona pr=(Persona)it.next();
		  idCoordinador=pr.getId().getDocumento();
		  pr=null;
		}
		it=null;
		//SE ASIGNA LA FACULTAD ASOCIADA AL INVESTIGADOR PRINCIPAL DEL PROYECTO
		it=p.getInvestigadoresProyecto().iterator();
		boolean principalEncontrado=false;
		while(!principalEncontrado && it.hasNext()){
			InvestigadorProyecto ip=(InvestigadorProyecto)it.next();
			if(ip.getTipo().getId().equals("P")){
				principalEncontrado=true;
				InvestigadorInterno ii=servicioPersona.obtenerInvestigadorInterno(ip.getInvestigador().getId());
				if(ii!=null){
					Dependencia d=ii.getDependencia();											
					if(d.getEsFacultad().booleanValue()){
						nombreFacultad=d.getNombre();
					}else{					
						if(d.getFacultad()!=null && d.getFacultad().getId()!=null){							
								Dependencia facultad=(Dependencia)servicioGeneral.obtenerObjeto(new Dependencia(),d.getFacultad().getId());
								nombreFacultad=facultad.getNombre();																						
						}else{
							nombreFacultad=d.getNombre();
						}
					}	
				}else{
					nombreFacultad="";
				}				
			}
		}
	}
		
	public String getIdCoordinador() {
		return idCoordinador;
	}
	public void setIdCoordinador(String idCoordinador) {
		this.idCoordinador = idCoordinador;
	}
	public String getNombreFacultad() {
		return nombreFacultad;
	}
	public void setNombreFacultad(String nombreFacultad) {
		this.nombreFacultad = nombreFacultad;
	}

    public Proyecto getProyecto() {
        if (proyecto == null && getIdProyecto() != null) {
            return new Proyecto(getIdProyecto());
        } else {
            return proyecto;
        }
    }
    
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
    public Long getIdProyecto() {
        return idProyecto;
    }
    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }
    public String getNombreProyecto() {
        return nombreProyecto;
    }
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }
    public String getPerId() {
        return perId;
    }
    public void setPerId(String perId) {
        this.perId = perId;
    }
    public String getTdoId() {
        return tdoId;
    }
    public void setTdoId(String tdoId) {
        this.tdoId = tdoId;
    }    
	public String getIdFacultad() {
		return idFacultad;
	}	
	public void setIdFacultad(String idFacultad) {
		this.idFacultad = idFacultad;
	}



	public String getEstadoProyecto() {
		return estadoProyecto;
	}

	public void setEstadoProyecto(String estadoProyecto) {
		this.estadoProyecto = estadoProyecto;
	}
	
	public String getPerRevision() {
		return perRevision;
	}

	public void setPerRevision(String perRevision) {
		this.perRevision = perRevision;
	}
	
	public String getTdoId2() {
		return tdoId2;
	}

	public void setTdoId2(String tdoId2) {
		this.tdoId2 = tdoId2;
	}

	public String getPerEvaluacion() {
		return perEvaluacion;
	}

	public void setPerEvaluacion(String perEvaluacion) {
		this.perEvaluacion = perEvaluacion;
	}

	public String getTdoId3() {
		return tdoId3;
	}

	public void setTdoId3(String tdoId3) {
		this.tdoId3 = tdoId3;
	}

	public boolean isEsCoordinadorSeguimiento() {
		return esCoordinadorSeguimiento;
	}

	public void setEsCoordinadorSeguimiento(boolean esCoordinadorSeguimiento) {
		this.esCoordinadorSeguimiento = esCoordinadorSeguimiento;
	}

	public boolean isEsCoordinadorRequisitos() {
		return esCoordinadorRequisitos;
	}

	public void setEsCoordinadorRequisitos(boolean esCoordinadorRequisitos) {
		this.esCoordinadorRequisitos = esCoordinadorRequisitos;
	}

	public boolean isEsCoordinadorEvaluacion() {
		return esCoordinadorEvaluacion;
	}

	public void setEsCoordinadorEvaluacion(boolean esCoordinadorEvaluacion) {
		this.esCoordinadorEvaluacion = esCoordinadorEvaluacion;
	}

	public String getCoordinadoresAsesor() {
		return coordinadoresAsesor;
	}

	public void setCoordinadoresAsesor(String coordinadoresAsesor) {
		this.coordinadoresAsesor = coordinadoresAsesor;
	}
	
	public boolean isCoordinadorRequisitosAsociadoAsesor(){
		if(perRevision != null && tdoId2 != null){
			return validarCoordinador(perRevision+tdoId2);
		}
		return true;
	}
	
	public boolean isCoordinadorEvaluacionAsociadoAsesor(){
		if(perEvaluacion != null && tdoId3 != null){
			return validarCoordinador(perEvaluacion+tdoId3);
		}
		return true;
	}
	
	public boolean isCoordinadorConceptoAsociadoAsesor(){
		if(perConcepto != null && tdoIdConcepto != null){
			return validarCoordinador(perConcepto+tdoIdConcepto);
		}
		return true;
	}
	
	public boolean isCoordinadorSeguimientoAsociadoAsesor(){
		if(perId != null && tdoId != null){
			return validarCoordinador(perId+tdoId);
		}
		return true;
	}
	
	private boolean validarCoordinador(String coordinador){
		if(coordinadoresAsesor != null && coordinadoresAsesor.length() > 0){
			if(coordinador != null && coordinador.length()> 0){
				if(coordinadoresAsesor.indexOf(coordinador + "==") != -1){
					return true;
				}
				else return false;
			}
			else{
				return true;
			}
		}
		else {
			return false;
		}
	}

	public String getNombreRevision() {
		return nombreRevision;
	}

	public void setNombreRevision(String nombreRevision) {
		this.nombreRevision = nombreRevision;
	}

	public String getNombreEvaluacion() {
		return nombreEvaluacion;
	}

	public void setNombreEvaluacion(String nombreEvaluacion) {
		this.nombreEvaluacion = nombreEvaluacion;
	}

	public String getNombreSeguimiento() {
		return nombreSeguimiento;
	}

	public void setNombreSeguimiento(String nombreSeguimiento) {
		this.nombreSeguimiento = nombreSeguimiento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreEstadoProyecto() {
		return nombreEstadoProyecto;
	}

	public void setNombreEstadoProyecto(String nombreEstadoProyecto) {
		this.nombreEstadoProyecto = nombreEstadoProyecto;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getEstadoProyectoAnterior() {
		return estadoProyectoAnterior;
	}

	public void setEstadoProyectoAnterior(String estadoProyectoAnterior) {
		this.estadoProyectoAnterior = estadoProyectoAnterior;
	}

	public String getPerConcepto() {
		return perConcepto;
	}

	public void setPerConcepto(String perConcepto) {
		this.perConcepto = perConcepto;
	}

	public String getNombreConcepto() {
		return nombreConcepto;
	}

	public void setNombreConcepto(String nombreConcepto) {
		this.nombreConcepto = nombreConcepto;
	}

	public String getTdoIdConcepto() {
		return tdoIdConcepto;
	}

	public void setTdoIdConcepto(String tdoIdConcepto) {
		this.tdoIdConcepto = tdoIdConcepto;
	}

	public boolean isEsCoordinadorConcepto() {
		return esCoordinadorConcepto;
	}

	public void setEsCoordinadorConcepto(boolean esCoordinadorConcepto) {
		this.esCoordinadorConcepto = esCoordinadorConcepto;
	}
	
}
