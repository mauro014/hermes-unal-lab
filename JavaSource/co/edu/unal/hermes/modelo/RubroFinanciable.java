package co.edu.unal.hermes.modelo;

public class RubroFinanciable{
    
	private Long id;
	private TipoRubro tipoRubro;
	private double porcentajeMaximo;
	private Modalidad modalidad;
	public Double cantidadParametro;   
	public Parametro parametro;
	
    public TipoRubro getTipoRubro() {
        return tipoRubro;
    }
    public void setTipoRubro(TipoRubro tipoRubro) {
        this.tipoRubro = tipoRubro;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    
    public Modalidad getModalidad() {
		return modalidad;
	}
	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}
	public double getPorcentajeMaximo() {
        return porcentajeMaximo;
    }
    public void setPorcentajeMaximo(double porcentajeMaximo) {
        this.porcentajeMaximo = porcentajeMaximo;
    }
    public Double getCantidadParametro() {
        return cantidadParametro;
    }
    public void setCantidadParametro(Double cantidadParametro) {
        this.cantidadParametro = cantidadParametro;
    }
    public Parametro getParametro() {
        return parametro;
    }
    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
    }
}
