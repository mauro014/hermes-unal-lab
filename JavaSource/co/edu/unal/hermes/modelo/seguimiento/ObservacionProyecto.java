/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Clase    	: co.edu.unal.hermes.modelo.seguimiento.ObservacionProyecto
Objetivo 	: Clase POJO para  la  tabla HER_OBSERVACION_PROYECTO, en la  cual se  
			  encuentran las observaciones de seguimiento realizadas por parte de 
			  los asesores a los proyectos	de investigación.
Creación	: Septiembre 04 de 2007
Modificación:
Detalle		:
********************************************************************************/

package co.edu.unal.hermes.modelo.seguimiento;

import java.io.Serializable;
import java.util.Date;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;

public class ObservacionProyecto implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date fecha;
	private String descripcion;
	private Persona asesor;
	private Proyecto proyecto;
	
	private boolean nueva = true;
	
	public Long getId() 
	{
		return id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
		this.nueva = false;
	}
	
	public Date getFecha() 
	{
		return fecha;
	}
	
	public void setFecha(Date fecha) 
	{
		this.fecha = fecha;
	}
	
	public String getDescripcion() 
	{
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}
	
	public Persona getAsesor() 
	{
		return asesor;
	}
	
	public void setAsesor(Persona asesor) 
	{
		this.asesor = asesor;
	}
	
	public Proyecto getProyecto() 
	{
		return proyecto;
	}
	
	public void setProyecto(Proyecto proyecto) 
	{
		this.proyecto = proyecto;
	}
	
	public boolean getNueva() 
	{
		return nueva;
	}

	public void setNueva(boolean nueva) 
	{
		this.nueva = nueva;
	}	
}
