package co.edu.unal.hermes.modelo;


public class DocumentoConvocatoriaTR {
	
	private Long id;  
    private ConvocatoriaTerminosReferencia convocatoriaTR;
    private Long orden;
    private String documento;
    
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
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public boolean equals(Object object) {
        if (object instanceof DocumentoConvocatoriaTR) {
        	DocumentoConvocatoriaTR documento = (DocumentoConvocatoriaTR) object;
            if (this.getDocumento().equals(documento.getDocumento())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
