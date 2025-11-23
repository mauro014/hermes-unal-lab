/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Clase    	: co.edu.unal.hermes.modelo.seguimiento.DetalleSolicitud
Objetivo 	: Clase  POJO  para  la  tabla  HER_DETALLE_SOLICITUD,  en la cual se  
			  encuentra el detalle de solicitudes cuyo tipo es cambio de rubros.
Creación	: Septiembre 10 de 2007
Modificación:
Detalle		:
********************************************************************************/

package co.edu.unal.hermes.modelo.seguimiento;

import java.io.Serializable;

import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.TipoRubro;

public class DetalleSolicitud implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long valor;
	private Solicitud solicitud;
	private Gasto gasto;
	private TipoRubro tipoRubro;
	
	public Long getId() 
	{
		return id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public Long getValor() 
	{
		return valor;
	}
	
	public void setValor(Long valor) 
	{
		this.valor = valor;
	}
	
	public Solicitud getSolicitud() 
	{
		return solicitud;
	}
	
	public void setSolicitud(Solicitud solicitud) 
	{
		this.solicitud = solicitud;
	}
	
	public Gasto getGasto() 
	{
		return gasto;
	}
	
	public void setGasto(Gasto gasto) 
	{
		this.gasto = gasto;
	}
	
	public TipoRubro getTipoRubro() 
	{
		return tipoRubro;
	}
	
	public void setTipoRubro(TipoRubro tipoRubro) 
	{
		this.tipoRubro = tipoRubro;
	}
}
