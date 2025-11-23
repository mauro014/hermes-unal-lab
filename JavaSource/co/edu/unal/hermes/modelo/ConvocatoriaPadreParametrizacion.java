package co.edu.unal.hermes.modelo;

public class ConvocatoriaPadreParametrizacion {

    private Long id;
    private ConvocatoriaPadre convocatoriaPadre;
    private Dependencia dependencia;
    private Tipos tipoParametro;
    private Long valorParametro;
    
	public ConvocatoriaPadreParametrizacion() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ConvocatoriaPadre getConvocatoriaPadre() {
		return convocatoriaPadre;
	}

	public void setConvocatoriaPadre(ConvocatoriaPadre convocatoriaPadre) {
		this.convocatoriaPadre = convocatoriaPadre;
	}

	public Dependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public Tipos getTipoParametro() {
		return tipoParametro;
	}

	public void setTipoParametro(Tipos tipoParametro) {
		this.tipoParametro = tipoParametro;
	}

	public Long getValorParametro() {
		return valorParametro;
	}

	public void setValorParametro(Long valorParametro) {
		this.valorParametro = valorParametro;
	}
}