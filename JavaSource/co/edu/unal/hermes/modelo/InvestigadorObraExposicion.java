/*
87 * Created on 26-may-2014
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class InvestigadorObraExposicion implements Serializable{
        
 
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private Long id;    
    private String nombreExposicion;
    private String nombreObra;
    private Pais pais;
    private String ciudadPais;
    private String organizador;
    private Date fechaExposicion;
    private Date fechaObra;
    private Investigador investigador;
    boolean obra;
    

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public Investigador getInvestigador() {
		return investigador;
	}
	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}
	public String getCiudadPais() {
		return ciudadPais;
	}
	public void setCiudadPais(String ciudadPais) {
		this.ciudadPais = ciudadPais;
	}
	public String getOrganizador() {
		return organizador;
	}
	public void setOrganizador(String organizador) {
		this.organizador = organizador;
	}
	public String getNombreExposicion() {
		return nombreExposicion;
	}
	public void setNombreExposicion(String nombreExposicion) {
		this.nombreExposicion = nombreExposicion;
	}
	public String getNombreObra() {
		return nombreObra;
	}
	public void setNombreObra(String nombreObra) {
		this.nombreObra = nombreObra;
	}
	public Date getFechaExposicion() {
		return fechaExposicion;
	}
	public void setFechaExposicion(Date fechaExposicion) {
		this.fechaExposicion = fechaExposicion;
	}
	public Date getFechaObra() {
		return fechaObra;
	}
	public void setFechaObra(Date fechaObra) {
		this.fechaObra = fechaObra;
	}
	public boolean isObra() {
		return obra;
	}
	public void setObra(boolean obra) {
		this.obra = obra;
	}
    
    
   
}
