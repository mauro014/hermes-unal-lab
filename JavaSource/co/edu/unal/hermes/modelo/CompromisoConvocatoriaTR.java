package co.edu.unal.hermes.modelo;

public class CompromisoConvocatoriaTR {
	
	private Long id;  
    private ConvocatoriaTerminosReferencia convocatoriaTR;
    private Long orden;
    private String compromiso;
    
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
	public String getCompromiso() {
		return compromiso;
	}
	public void setCompromiso(String compromiso) {
		this.compromiso = compromiso;
	}
	
	public boolean equals(Object object) {
        if (object instanceof CompromisoConvocatoriaTR) {
        	CompromisoConvocatoriaTR compromiso = (CompromisoConvocatoriaTR) object;
            if (this.getCompromiso().equals(compromiso.getCompromiso())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
