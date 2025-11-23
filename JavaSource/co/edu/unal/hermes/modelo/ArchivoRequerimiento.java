package co.edu.unal.hermes.modelo;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;

import org.hibernate.Hibernate;
import org.primefaces.model.UploadedFile;




/**
 * Especifica el archivo que contiene el requerimiento escaneado
 */

public class ArchivoRequerimiento implements Serializable, UploadedFile{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Long id;
	public String nombre;
    Blob archivo;
    public String tipoArchivoReq;
    private InputStream archivoInputStream;
	private Requerimiento requerimiento;

	public static String DIRECTORIO_ARCHIVOS ="data//archivos//HER_ARCHIVO_REQUERIMIENTO//";
	//public static String DIRECTORIO_ARCHIVOS ="D://HER_ARCHIVO_REQUERIMIENTO//"; //PARA PRUEBAS
	
 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Blob getArchivo() {
		return archivo;
	}

	public void setArchivo(Blob archivo) {
		this.archivo = archivo;
	}

		
	public void setBytes(byte[] bytes)
	{
        archivo = Hibernate.createBlob(bytes);
    }
	
	public byte[] getBytes()
	{
		try
		{
			return this.archivo.getBytes(1, (int)archivo.length());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}			
	}
	
	public String getTipoArchivoReq() {
		return tipoArchivoReq;
	}
	public void setTipoArchivoReq(String tipoArchivoReq) {
		this.tipoArchivoReq = tipoArchivoReq;
	}
	public Requerimiento getRequerimiento() {
		return requerimiento;
	}
	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}
	
	public String getContentType() {
		
		return null;
	}
	public byte[] getContents() {
		
		return null;
	}
	public String getFileName() {
		
		return null;
	}
	public InputStream getInputstream() throws IOException {
		
		return null;
	}
	public long getSize() {
		
		return 0;
	}
	public InputStream getArchivoInputStream() {
		return archivoInputStream;
	}
	public void setArchivoInputStream(InputStream archivoInputStream) {
		this.archivoInputStream = archivoInputStream;
	}
	

	
	
}
