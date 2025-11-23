package co.edu.unal.hermes.modelo;

public class ModalidadConvocatoriaTR {
	
	private Long id;  
    private ConvocatoriaTerminosReferencia convocatoriaTR;
    private Long orden;
    private String modalidad;
    
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
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	@Override
	public boolean equals(Object object) {
        if (object instanceof ModalidadConvocatoriaTR) {
        	ModalidadConvocatoriaTR modalidad = (ModalidadConvocatoriaTR) object;
            if (this.getModalidad().equals(modalidad.getModalidad())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
	
	
}
