package co.edu.unal.hermes.modelo;

import java.util.Date;

public class InvestigadorPuntaje 
{
	private String tipoDocumento;
	private String documento;
	private Date fechaObtencion;
	private String descripcion;
	private float puntajeReal;
	
	public float getPuntajeReal() 
	{
		return puntajeReal;
	}

	public void setPuntajeReal(float puntajeReal) 
	{
		this.puntajeReal = puntajeReal;
	}

	public String getTipoDocumento() 
	{
		return tipoDocumento;
	}
	
	public void setTipoDocumento(String tipoDocumento) 
	{
		this.tipoDocumento = tipoDocumento;
	}
	
	public String getDocumento() 
	{
		return documento;
	}
	
	public void setDocumento(String documento) 
	{
		this.documento = documento;
	}
	
	public Date getFechaObtencion() 
	{
		return fechaObtencion;
	}
	
	public void setFechaObtencion(Date fechaObtencion) 
	{
		this.fechaObtencion = fechaObtencion;
	}
	
	public String getDescripcion() 
	{
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}
}
