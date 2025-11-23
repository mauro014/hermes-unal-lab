/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Específica la actividad asociada al proyecto.
 * 
 */
public class EncuestaSoporte implements Serializable{
        
    private Long id;    
    private String atencion;
    private String tiempo;
    private String proceso;
    private Date fecha;
    private Sede idSede;
    private String solucion;
    private String observaciones;
    
    //set get
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAtencion() {
		return atencion;
	}
	public void setAtencion(String atencion) {
		this.atencion = atencion;
	}
	public String getTiempo() {
		return tiempo;
	}
	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Sede getIdSede() {
		return idSede;
	}
	public void setIdSede(Sede idSede) {
		this.idSede = idSede;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public String getSolucion() {
		return solucion;
	}
	public void setSolucion(String solucion) {
		this.solucion = solucion;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
    
}
