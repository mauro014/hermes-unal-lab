/********************************************************************************
Autor 		: gacantorm

********************************************************************************/

package co.edu.unal.hermes.modelo;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;

public class ArchivoConvocatoria{
		
	private Long id;
	private String nombre;
	private Blob archivo;
	private Date fecha;
	private String convocatoria;
	private String tipoArchivo;
		
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public Blob getArchivo() 
	{
		return archivo;
	}

	public void setArchivo(Blob archivo) 
	{
		this.archivo = archivo;
	}

	

	 public void setBytes(byte[] bytes){
		 archivo = Hibernate.createBlob(bytes);
	    }
	    public byte[] getBytes(){
	        byte[] resultado = null;
	        try {
	            resultado = archivo.getBytes(1, (int) archivo.length());
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return resultado;
	    }

		public Date getFecha() {
			return fecha;
		}

		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}

		public String getConvocatoria() {
			return convocatoria;
		}

		public void setConvocatoria(String convocatoria) {
			this.convocatoria = convocatoria;
		}

		public String getTipoArchivo() {
			return tipoArchivo;
		}

		public void setTipoArchivo(String tipoArchivo) {
			this.tipoArchivo = tipoArchivo;
		}
		
}
