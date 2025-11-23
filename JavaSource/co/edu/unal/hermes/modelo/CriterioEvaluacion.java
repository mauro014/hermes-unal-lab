package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class CriterioEvaluacion implements Serializable{
    
	private Long id;
	private String nombre;
	private Float factor;
	private String descripcion;
	private TipoInvestigacion tipoInvestigacion;
	private String activo; 
	
	
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
	public CriterioEvaluacion()
	{	
	}
	
	public CriterioEvaluacion(Long id)
	{
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
   
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Float getFactor() {
        return factor;
    }
    public void setFactor(Float factor) {
        this.factor = factor;
    }
	public TipoInvestigacion getTipoInvestigacion() 
	{
		return tipoInvestigacion;
	}
	
	public void setTipoInvestigacion(TipoInvestigacion tipoInvestigacion) 
	{
		this.tipoInvestigacion = tipoInvestigacion;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
}
