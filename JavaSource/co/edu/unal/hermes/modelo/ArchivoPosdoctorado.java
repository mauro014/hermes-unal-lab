/********************************************************************************
Autor 		: gacantorm

********************************************************************************/

package co.edu.unal.hermes.modelo;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;

public class ArchivoPosdoctorado{
		
	private Long id;
	private String nombre;
	private Blob archivo;
	private Date fecha;
	private String estancia;
	private String tipo;
	private String numInforme;
	private TipoArchivoEstancia tipoArchivo;
		
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

		public String getEstancia() {
			return estancia;
		}

		public void setEstancia(String estancia) {
			this.estancia = estancia;
		}

		public TipoArchivoEstancia getTipoArchivo() {
			return tipoArchivo;
		}

		public void setTipoArchivo(TipoArchivoEstancia tipoArchivo) {
			this.tipoArchivo = tipoArchivo;
		}

		public String getTipo() {
		    return tipo;
		}

		public void setTipo(String tipo) {
		    this.tipo = tipo;
		}

		public String getNumInforme() {
		    return numInforme;
		}

		public void setNumInforme(String numInforme) {
		    this.numInforme = numInforme;
		}
		
}
