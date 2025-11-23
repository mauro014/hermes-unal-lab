package co.edu.unal.hermes.vista.proyectos.manejadorproyecto;

import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.PosibleEvaluador;

public class EvaluadorVista {
	
	private String nombre;
	private String telefono;
	private String email;
	private String idExperticia;
	private String tipoEvaluador;
	
	//CONSTRUCTOR DE EVALUADOR VISTA PARA EVALUADORES EXTERNOS 
	public EvaluadorVista(PosibleEvaluador pe){
		this.nombre       =pe.getNombre()+" "+pe.getApellido1()+" "+pe.getApellido2();
		this.telefono     =pe.getTelefono();
		this.email        =pe.getEmail();
		//TODO cuando no tiene clasificacion del conocimiento
		this.idExperticia =pe.getClasificacionConocimiento().getId();		
		this.tipoEvaluador="E";
	}
	
    //CONSTRUCTOR DE EVALAUDOR VISTA PARA EVALUADORES INTERNOS
	public EvaluadorVista(InvestigadorInterno ie,String idExperticia){
		this.nombre       =ie.getNombre1()+" "+ie.getNombre2()+" "+ie.getApellido1()+" "+ie.getApellido2();
		this.telefono     =ie.getTelefono();
		this.email        =ie.getEmail();		
		this.idExperticia =idExperticia;
		this.tipoEvaluador="I";
	}
		
	public EvaluadorVista(InvestigadorExterno ie,String idExperticia){
		this.nombre       =ie.getNombre1()+" "+ie.getNombre2()+" "+ie.getApellido1()+" "+ie.getApellido2();
		this.telefono     =ie.getTelefono();
		this.email        =ie.getEmail();		
		this.idExperticia =idExperticia;
		this.tipoEvaluador="I";
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdExperticia() {
		return idExperticia;
	}
	public void setIdExperticia(String idExperticia) {
		this.idExperticia = idExperticia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}		
	public String getTipoEvaluador() {
		return tipoEvaluador;
	}
	public void setTipoEvaluador(String tipoEvaluador) {
		this.tipoEvaluador = tipoEvaluador;
	}
}
