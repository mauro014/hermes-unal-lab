/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * Referencia el resultado del proyecto de investigación 
 */ 
public class ResultadoProyecto {
    
    private Long id;
    private Proyecto proyecto;      
    private String descripcion;
    private boolean borrable=false;
    private Long numeroOrden; 
    private String entregable;
    private Date fechaEntregable;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
     
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }      
    
    public Proyecto getProyecto() {
        return proyecto;
    }
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }       
	public boolean isBorrable() {
		return borrable;
	}
	public void setBorrable(boolean borrable) {
		this.borrable = borrable;
	}
    /**
     * @return the numeroOrden
     */
    public Long getNumeroOrden() {
        return numeroOrden;
    }
    /**
     * @param numeroOrden the numeroOrden to set
     */
    public void setNumeroOrden(Long numeroOrden) {
        this.numeroOrden = numeroOrden;
    }
	public String getEntregable() {
		return entregable;
	}
	public void setEntregable(String entregable) {
		this.entregable = entregable;
	}
	public Date getFechaEntregable() {
		return fechaEntregable;
	}
	public void setFechaEntregable(Date fechaEntregable) {
		this.fechaEntregable = fechaEntregable;
	}
}
