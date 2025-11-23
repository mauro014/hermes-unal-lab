
package co.edu.unal.hermes.modelo;

/**
 * Especifíca la dependencia y su area de responsabilidad respectiva asociada al proyecto.
 * 
 */
public class CamposAdicionalesConvocatoriaTR{
        
    private Long id;  
    private ConvocatoriaTerminosReferencia convocatoriaTR;
    private String nombre;
    private String descripcion;
    private Long orden;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ConvocatoriaTerminosReferencia getConvocatoriaTR() {
		return convocatoriaTR;
	}
	public void setConvocatoriaTR(ConvocatoriaTerminosReferencia convocatoriaTR) {
		this.convocatoriaTR = convocatoriaTR;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getOrden() {
		return orden;
	}
	public void setOrden(Long orden) {
		this.orden = orden;
	}
    
   
}
