
package co.edu.unal.hermes.modelo;

/**
 * Especifíca la dependencia y su area de responsabilidad respectiva asociada al proyecto.
 * 
 */
public class DependenciaAreaResponsabilidad{
        
    private Long id;  
    private Proyecto proyecto;
    private Dependencia dependencia;
    private String areaResponsabilidad;
    private Long valorAporte;

    public static final String SEDE = "S";
    public static final String FACULTAD = "F";
   
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
   	public Dependencia getDependencia() {
		return dependencia;
	}
	
	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}
	
	public Proyecto getProyecto() {
		return proyecto;
	}
	
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	public String getAreaResponsabilidad() {
		return areaResponsabilidad;
	}
	public void setAreaResponsabilidad(String areaResponsabilidad) {
		this.areaResponsabilidad = areaResponsabilidad;
	}
	public Long getValorAporte() {
		return valorAporte;
	}
	public void setValorAporte(Long valorAporte) {
		this.valorAporte = valorAporte;
	}
}
