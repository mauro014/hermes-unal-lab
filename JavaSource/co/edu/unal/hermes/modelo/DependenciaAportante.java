
package co.edu.unal.hermes.modelo;

/**
 * Especifíca la dependencia y su area de responsabilidad respectiva asociada al proyecto.
 * 
 */
public class DependenciaAportante{
        
    private Long id;  
    private Proyecto proyecto;
    private Dependencia dependencia;
    private Long valorAporte;   
    private Long valorAporteEspecie;
    
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
	
	public Long getValorAporte() {
		return valorAporte;
	}
	
	public void setValorAporte(Long valorAporte) {
		this.valorAporte = valorAporte;
	}
	public Long getValorAporteEspecie() {
		return valorAporteEspecie;
	}
	public void setValorAporteEspecie(Long valorAporteEspecie) {
		this.valorAporteEspecie = valorAporteEspecie;
	}
}
