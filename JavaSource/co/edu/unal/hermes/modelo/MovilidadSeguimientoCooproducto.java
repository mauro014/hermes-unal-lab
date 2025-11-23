/*
 * Created on 9-dic-2007
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;


public class MovilidadSeguimientoCooproducto implements Serializable{

	private Long id;
	private Long idMovilidad;
	private String tipo;
	private String detalle;
	
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public Long getIdMovilidad() {
		return idMovilidad;
	}
	public void setIdMovilidad(Long idMovilidad) {
		this.idMovilidad = idMovilidad;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
	
	
