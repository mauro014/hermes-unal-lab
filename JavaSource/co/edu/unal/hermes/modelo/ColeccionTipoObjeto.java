package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;


public class ColeccionTipoObjeto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Atributos de la clase coleccionclasificacion, correspondientes a la tabla HER_COLECCION_CLASIFICACION
	
	private Long id;
	
	private Coleccion coleccion ;
	private DominioDetalle subTipoObjeto;
	private DominioDetalle tipoObjeto;
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
	public DominioDetalle getSubTipoObjeto() {
		return subTipoObjeto;
	}
	public void setSubTipoObjeto(DominioDetalle subTipoObjeto) {
		this.subTipoObjeto = subTipoObjeto;
	}
	public DominioDetalle getTipoObjeto() {
		return tipoObjeto;
	}
	public void setTipoObjeto(DominioDetalle tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

}
