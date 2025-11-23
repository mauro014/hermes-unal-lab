/*
 * Created on 27-oct-2006
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package co.edu.unal.hermes.modelo.seguimiento;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;

public class TramiteDocumento implements Serializable 
{
	private static final long serialVersionUID = 1L;
    Long id;
    private TramiteSolicitud tramite;
	Blob archivo;  
	
	private Date fechaGeneracion;
	private int index;
		
	public TramiteDocumento(){
	    
	}

	
	
	public TramiteSolicitud getTramite() {
		return tramite;
	}



	public void setTramite(TramiteSolicitud tramite) {
		this.tramite = tramite;
	}



	public Blob getArchivo() {
		return archivo;
	}
	public void setArchivo(Blob archivo) {
		this.archivo = archivo;
	}


	public void setBytesArchivoCarta(byte[] bytes){
		archivo = Hibernate.createBlob(bytes);
	}
	 
	public byte[] getBytesArchivoCarta(){
	        byte[] resultado = null;
	        try {
	            resultado = archivo.getBytes(1, (int) archivo.length());
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return resultado;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}
	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
