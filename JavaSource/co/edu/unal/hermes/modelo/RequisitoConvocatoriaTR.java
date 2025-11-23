package co.edu.unal.hermes.modelo;

public class RequisitoConvocatoriaTR {
	
	private Long id;  
    private ConvocatoriaTerminosReferencia convocatoriaTR;
    private Long orden;
    private String requisito;
    
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
	public String getRequisito() {
		return requisito;
	}
	public void setRequisito(String requisito) {
		this.requisito = requisito;
	}
	
	public boolean equals(Object object) {
        if (object instanceof RequisitoConvocatoriaTR) {
        	RequisitoConvocatoriaTR requisito = (RequisitoConvocatoriaTR) object;
            if (this.getRequisito().equals(requisito.getRequisito())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
