/********************************************************************************
Autor 		: Mauricio Amaya Rios
********************************************************************************/

package co.edu.unal.hermes.modelo;


/**
 * The Class ArchivoConvocatoriaPadre.
 */
public class ArchivoConvocatoriaPadre{
		
	/** The id. */
	private Long id;
	
	/** The nombre. */
	private String nombre;
	
	/** The convocatoria padre. */
	private String convocatoriaPadre;
	
	/** The tipo archivo. */
	private String tipoArchivo;
	
	/** The terminos. */
	private String terminos;
	
	/** The etiqueta. */
	private String etiqueta;
	
	private String visiblePagina;
	
	public ArchivoConvocatoriaPadre() {
		/**
		 * Constructor por defecto de la clase aval
		 */
		visiblePagina = "S";
	}
		
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() 
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() 
	{
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	/**
	 * Gets the tipo archivo.
	 *
	 * @return the tipo archivo
	 */
	public String getTipoArchivo() {
		return tipoArchivo;
	}

	/**
	 * Sets the tipo archivo.
	 *
	 * @param tipoArchivo the new tipo archivo
	 */
	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	/**
	 * Gets the convocatoria padre.
	 *
	 * @return the convocatoria padre
	 */
	public String getConvocatoriaPadre() {
		return convocatoriaPadre;
	}

	/**
	 * Sets the convocatoria padre.
	 *
	 * @param convocatoriaPadre the new convocatoria padre
	 */
	public void setConvocatoriaPadre(String convocatoriaPadre) {
		this.convocatoriaPadre = convocatoriaPadre;
	}

	/**
	 * Sets the terminos.
	 *
	 * @param terminos the new terminos
	 */
	public void setTerminos(String terminos) {
		this.terminos = terminos;
	}

	/**
	 * Gets the terminos.
	 *
	 * @return the terminos
	 */
	public String getTerminos() {
		return terminos;
	}

	/**
	 * Gets the etiqueta.
	 *
	 * @return the etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * Sets the etiqueta.
	 *
	 * @param etiqueta the new etiqueta
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getVisiblePagina() {
		return visiblePagina;
	}

	public void setVisiblePagina(String visiblePagina) {
		this.visiblePagina = visiblePagina;
	}
	
}
