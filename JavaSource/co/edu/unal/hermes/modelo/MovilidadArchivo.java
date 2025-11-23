package co.edu.unal.hermes.modelo;

public class MovilidadArchivo{
    
    
	private TipoMovilidad 	tipoMovilidad;
	private TipoArchivoMovilidad tipoArchivo;
	private Long id;
	private Long esObligatorio;
	private String submodalidad;

	
	
	
	public TipoMovilidad getTipoMovilidad() {
		return tipoMovilidad;
	}
	public void setTipoMovilidad(TipoMovilidad tipoMovilidad) {
		this.tipoMovilidad = tipoMovilidad;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TipoArchivoMovilidad getTipoArchivo() {
		return tipoArchivo;
	}
	public void setTipoArchivo(TipoArchivoMovilidad tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}
	/**
	 * @return the esObligatorio
	 */
	public Long getEsObligatorio()
	{
		return esObligatorio;
	}
	/**
	 * @param esObligatorio the esObligatorio to set
	 */
	public void setEsObligatorio(Long esObligatorio)
	{
		this.esObligatorio = esObligatorio;
	}
	/**
	 * @return the submodalidad
	 */
	public String getSubmodalidad()
	{
		return submodalidad;
	}
	/**
	 * @param submodalidad the submodalidad to set
	 */
	public void setSubmodalidad(String submodalidad)
	{
		this.submodalidad = submodalidad;
	}

	

}
