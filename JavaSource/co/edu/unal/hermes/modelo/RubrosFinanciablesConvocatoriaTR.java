package co.edu.unal.hermes.modelo;


/**
 * Específica la actividad asociada al proyecto.
 * 
 */
public class RubrosFinanciablesConvocatoriaTR{
        
    /**
	 * 
	 */
	private Long id;    
    private String justificacion;
    private Long orden;
    private ConvocatoriaTerminosReferencia convocatoriaTR;
    private TipoRubro tipoRubro;

	public RubrosFinanciablesConvocatoriaTR() {
	    super();
	    this.tipoRubro = new TipoRubro();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public ConvocatoriaTerminosReferencia getConvocatoriaTR() {
		return convocatoriaTR;
	}

	public void setConvocatoriaTR(ConvocatoriaTerminosReferencia convocatoriaTR) {
		this.convocatoriaTR = convocatoriaTR;
	}

	public TipoRubro getTipoRubro() {
		return tipoRubro;
	}

	public void setTipoRubro(TipoRubro tipoRubro) {
		this.tipoRubro = tipoRubro;
	}
	
	@Override
	public boolean equals(Object object) {
        if (object instanceof RubrosFinanciablesConvocatoriaTR) {
        	RubrosFinanciablesConvocatoriaTR rubro = (RubrosFinanciablesConvocatoriaTR) object;
            if (this.getTipoRubro().getId().equals(rubro.getTipoRubro().getId())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
