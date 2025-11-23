package co.edu.unal.hermes.modelo;

public class ConvocatoriaParametrizacion {

    private Long id;
    private Convocatoria convocatoria;
    private Tipos tipoParametro;
    private Long valorParametro;
    private Dependencia dependencia;
    private String año;
    private TipoPonencia objetoMovilidad;
    private Float montoMaximo;
    private String tipoPlanStudios;
    private Pais pais;
    
	public ConvocatoriaParametrizacion() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Convocatoria getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(Convocatoria convocatoria) {
		this.convocatoria = convocatoria;
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

	public Dependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public String getAño() {
		return año;
	}

	public void setAño(String año) {
		this.año = año;
	}

	public TipoPonencia getObjetoMovilidad() {
		return objetoMovilidad;
	}

	public void setObjetoMovilidad(TipoPonencia objetoMovilidad) {
		this.objetoMovilidad = objetoMovilidad;
	}

	public Float getMontoMaximo() {
		return montoMaximo;
	}

	public void setMontoMaximo(Float montoMaximo) {
		this.montoMaximo = montoMaximo;
	}

	public String getTipoPlanStudios() {
		return tipoPlanStudios;
	}

	public void setTipoPlanStudios(String tipoPlanStudios) {
		this.tipoPlanStudios = tipoPlanStudios;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
}