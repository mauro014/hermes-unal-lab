package co.edu.unal.hermes.modelo;

public class IncompatibilidadConvocatoriaTR {
	
	private Long id;  
    private ConvocatoriaTerminosReferencia convocatoriaTR;
    private Long orden;
    private String incompatibilidad;
    
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
	public Long getOrden() {
		return orden;
	}
	public void setOrden(Long orden) {
		this.orden = orden;
	}
	public String getIncompatibilidad() {
		return incompatibilidad;
	}
	public void setIncompatibilidad(String incompatibilidad) {
		this.incompatibilidad = incompatibilidad;
	}
	
	public boolean equals(Object object) {
        if (object instanceof IncompatibilidadConvocatoriaTR) {
        	IncompatibilidadConvocatoriaTR incompatibilidad = (IncompatibilidadConvocatoriaTR) object;
            if (this.getIncompatibilidad().equals(incompatibilidad.getIncompatibilidad())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
