package co.edu.unal.hermes.modelo;

import java.io.Serializable;


public class ColeccionNomenclatura implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Atributos de la clase ColeccionNomenclatura, correspondientes a la tabla HER_COLECCION_NOMENCLATURA
	
	private Long id;
	
	private Coleccion coleccion ;
	private DominioDetalle nomenclatura;
	private DominioDetalle grupoBiologico;
	private long cantidad ;
	
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
	public void setNomenclatura(DominioDetalle nomenclatura) {
		this.nomenclatura = nomenclatura;
	}
	public DominioDetalle getNomenclatura() {
		return nomenclatura;
	}
	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}
	public long getCantidad() {
		return cantidad;
	}
	public DominioDetalle getGrupoBiologico() {
		return grupoBiologico;
	}
	public void setGrupoBiologico(DominioDetalle grupoBiologico) {
		this.grupoBiologico = grupoBiologico;
	}
	
}
