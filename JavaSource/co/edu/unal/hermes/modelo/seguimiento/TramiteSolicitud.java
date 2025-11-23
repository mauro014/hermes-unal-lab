/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Clase    	: co.edu.unal.hermes.modelo.seguimiento.TramiteSolicitud
Objetivo 	: Clase POJO para  la  tabla HER_TRAMITE_SOLICITUD,  en  la  cual  se  
			  encuentran los Tramites realizados para atender las solicitudes  de 
			  los investigadores.
Creación	: Septiembre 10 de 2007
Modificación:
Detalle		:
********************************************************************************/

package co.edu.unal.hermes.modelo.seguimiento;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import co.edu.unal.hermes.modelo.Persona;

public class TramiteSolicitud implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date fecha;
	private String descripcion;
	private Persona asesor;
	private Solicitud solicitud;
	
	private List listaDocumentos;
	private Set documentos = new HashSet();
	
	private boolean nueva = true;
	
	public Long getId() 
	{
		return id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
		nueva = false;
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
	
	public Solicitud getSolicitud() 
	{
		return solicitud;
	}
	
	public void setSolicitud(Solicitud solicitud) 
	{
		this.solicitud = solicitud;
	}

	public boolean getNueva() 
	{
		return nueva;
	}

	public void setNueva(boolean nueva) 
	{
		this.nueva = nueva;
	}	
	
	
	public Set getDocumentos() 
	{
		return documentos;
	}

	public void setDocumentos(Set documentos) 
	{
		this.documentos = documentos;
	}

	public List getListaDocumentos() 
	{
		return listaDocumentos;
	}

	public void setListaDocumentos(List listaDocumentos) 
	{
		this.listaDocumentos = listaDocumentos;
	}
	public int getTamañoListaDocumentos() {
		if(this.listaDocumentos!= null &&  this.listaDocumentos.size()> 0){
			return listaDocumentos.size();
		}
		return 0;
	}
	
}
