
package co.edu.unal.hermes.modelo;

/**
 * Especifíca la dependencia y su area de responsabilidad respectiva asociada al proyecto.
 * 
 */
public class DependenciaConvocatoriaTR{
        
    private Long id;  
    private ConvocatoriaTerminosReferencia convocatoriaTR;
    private Dependencia dependencia;
    
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
	
	public ConvocatoriaTerminosReferencia getConvocatoriaTR() {
		return convocatoriaTR;
	}
	
	public void setConvocatoriaTR(ConvocatoriaTerminosReferencia convocatoriaTR) {
		this.convocatoriaTR = convocatoriaTR;
	}
}
