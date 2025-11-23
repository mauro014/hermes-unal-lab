package co.edu.unal.hermes.modelo;

public class CriteriosEvaluacionConvocatoriaTR {
	
	private Long id;  
    private ConvocatoriaTerminosReferencia convocatoriaTR;
    private Long orden;
    private String descripcion;
    private Long puntaje;
    private String tipoPuntaje;
    
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(Long puntaje) {
		this.puntaje = puntaje;
	}
	public String getTipoPuntaje() {
		return tipoPuntaje;
	}
	public void setTipoPuntaje(String tipoPuntaje) {
		this.tipoPuntaje = tipoPuntaje;
	}
	
	@Override
	public boolean equals(Object object) {
        if (object instanceof CriteriosEvaluacionConvocatoriaTR) {
        	CriteriosEvaluacionConvocatoriaTR criterio = (CriteriosEvaluacionConvocatoriaTR) object;
            if (this.getDescripcion().equals(criterio.getDescripcion()) && this.getTipoPuntaje().equals(criterio.getTipoPuntaje())){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
