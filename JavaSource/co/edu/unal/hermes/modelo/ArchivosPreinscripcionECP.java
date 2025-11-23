package co.edu.unal.hermes.modelo;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

import org.hibernate.Hibernate;

public class ArchivosPreinscripcionECP implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1025053444927252346L;
	private Long idArchivo;
	private Preinscripcion_ECP preinscripcionECP;
	private String nombreArchivo;
	private Blob datosArchivo;
	private InputStream archivoInputStream;
	private boolean esNuevo = false;
	private String tipoArchivo;
	
	public Long getIdArchivo() { 
		return idArchivo;
	}
	public void setIdArchivo(Long idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public Blob getDatosArchivo() {
		return datosArchivo;
	}
	public void setDatosArchivo(Blob datosArchivo) {
		this.datosArchivo = datosArchivo;
	}
	public Preinscripcion_ECP getPreinscripcionECP() {
		return preinscripcionECP;
	}
	public void setPreinscripcionECP(Preinscripcion_ECP preinscripcionECP) {
		this.preinscripcionECP = preinscripcionECP;
	}
	
	public void setBytes(byte[] bytes){
		datosArchivo = Hibernate.createBlob(bytes);
    }
    public byte[] getBytes(){
        byte[] resultado = null;
        try {
            resultado = datosArchivo.getBytes(1, (int) datosArchivo.length());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
	public InputStream getArchivoInputStream() {
		return archivoInputStream;
	}
	public void setArchivoInputStream(InputStream archivoInputStream) {
		this.archivoInputStream = archivoInputStream;
	}
	public boolean isEsNuevo() {
	    return esNuevo;
	}
	public void setEsNuevo(boolean esNuevo) {
	    this.esNuevo = esNuevo;
	}
	public String getTipoArchivo() {
		return tipoArchivo;
	}
	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}
	
	

}
