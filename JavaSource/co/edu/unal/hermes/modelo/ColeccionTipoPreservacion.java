package co.edu.unal.hermes.modelo;

import java.io.Serializable;


public class ColeccionTipoPreservacion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Atributos de la clase ColeccionRegion, correspondientes a la tabla HER_COLECCION_TIPO_PRE
	
	private Long id;
	
	private Coleccion coleccion ;
	private DominioDetalle tipoPreservacion;
	private String cobertura;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setColeccion(Coleccion coleccion) {
		this.coleccion = coleccion;
	}
	public Coleccion getColeccion() {
		return coleccion;
	}
	public void setCobertura(String cobertura) {
		this.cobertura = cobertura;
	}
	public String getCobertura() {
		return cobertura;
	}
	public DominioDetalle getTipoPreservacion() {
		return tipoPreservacion;
	}
	public void setTipoPreservacion(DominioDetalle tipoPreservacion) {
		this.tipoPreservacion = tipoPreservacion;
	}


}
