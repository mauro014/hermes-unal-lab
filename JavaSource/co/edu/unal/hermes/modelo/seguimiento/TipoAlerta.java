/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Clase    	: co.edu.unal.hermes.modelo.seguimiento.TipoAlerta
Objetivo 	: Clase   POJO   para   la  tabla  HER_TIPO_ALERTA,  en  la   cual se 
			  almacenan los Tipos de alertas que se pueden generar con respecto a 
			  los proyectos de investigación.
Creación	: Agosto 21 de 2007
Modificación:
Detalle		:
********************************************************************************/
package co.edu.unal.hermes.modelo.seguimiento;

import java.io.Serializable;
import java.util.Date;

public class TipoAlerta implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nombre;
	private String descripcion;
	private String sentencia;
	private String campoGenerador;
	private Long valorLimite;
	private Long tipoLimite;
	private String vigencia;
	private Date desdeVigencia;
	private Date hastaVigencia;
	
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
	
	public String getDescripcion() 
	{
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}
	
	public String getVigencia() 
	{
		return vigencia;
	}
	
	public void setVigencia(String vigencia) 
	{
		this.vigencia = vigencia;
	}
	
	public Date getDesdeVigencia() 
	{
		return desdeVigencia;
	}
	
	public void setDesdeVigencia(Date desdeVigencia) 
	{
		this.desdeVigencia = desdeVigencia;
	}
	
	public Date getHastaVigencia() 
	{
		return hastaVigencia;
	}
	
	public void setHastaVigencia(Date hastaVigencia) 
	{
		this.hastaVigencia = hastaVigencia;
	}

	public String getSentencia() 
	{
		return sentencia;
	}

	public void setSentencia(String sentencia) 
	{
		this.sentencia = sentencia;
	}

	public String getCampoGenerador() 
	{
		return campoGenerador;
	}

	public void setCampoGenerador(String campoGenerador) 
	{
		this.campoGenerador = campoGenerador;
	}

	public Long getValorLimite() 
	{
		return valorLimite;
	}

	public void setValorLimite(Long valorLimite) 
	{
		this.valorLimite = valorLimite;
	}

	public Long getTipoLimite() 
	{
		return tipoLimite;
	}

	public void setTipoLimite(Long tipoLimite) 
	{
		this.tipoLimite = tipoLimite;
	}
}
