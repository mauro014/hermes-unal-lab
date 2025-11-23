/*
 * Created on 07-oct-2005
 */
package co.edu.unal.hermes.modelo;

/**
 * @author jpduqueg
 */
public class PlanGlobalDesarrollo{
    
    private Long id;    
    private PlanGlobalDesarrollo padre;   
	private String nombre;	
	private String periodo;	
	private String nombreCampoFormulario;	


	public PlanGlobalDesarrollo(String id){
		this.id=new Long(Long.parseLong(id));
	}
	
	public PlanGlobalDesarrollo(){}
		   
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }        
    
    public PlanGlobalDesarrollo getPadre() {    	
        return padre;
    }
    public void setPadre(PlanGlobalDesarrollo padre) {
        this.padre = padre;
    }

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getNombreCampoFormulario() {
		return nombreCampoFormulario;
	}

	public void setNombreCampoFormulario(String nombreCampoFormulario) {
		this.nombreCampoFormulario = nombreCampoFormulario;
	}
}
