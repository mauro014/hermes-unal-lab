package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;


public class ColeccionClasificacion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Atributos de la clase coleccionclasificacion, correspondientes a la tabla HER_COLECCION_CLASIFICACION
	
	private Long id;
	
	private Coleccion coleccion ;
	private DominioDetalle clasificacion;
	private Date fechaAgregado;
	
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
	public Date getFechaAgregado() {
		return fechaAgregado;
	}
	public void setFechaAgregado(Date fechaAgregado) {
		this.fechaAgregado = fechaAgregado;
	}
	public DominioDetalle getClasificacion() {
		return clasificacion;
	}
	public void setClasificacion(DominioDetalle clasificacion) {
		this.clasificacion = clasificacion;
	}


}
