package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;


public class AvalSubTipos implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Aval aval ;
	private DominioDetalle subTipo;
	private Date fechaAgregado;
	
	public Long getId() {
		return id; 
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFechaAgregado() {
		return fechaAgregado;
	}
	public void setFechaAgregado(Date fechaAgregado) {
		this.fechaAgregado = fechaAgregado;
	}
	public DominioDetalle getSubTipo() {
		return subTipo;
	}
	public void setSubTipo(DominioDetalle subTipo) {
		this.subTipo = subTipo;
	}
	public Aval getAval() {
		return aval;
	}
	public void setAval(Aval aval) {
		this.aval = aval;
	}
	

}
