/*
 * Created on 23/09/2014
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Comparator;


public class ActividadInforme implements Comparator<ActividadInforme>,Serializable{
       
	private static final long serialVersionUID = -7254226430143052952L;
	private Long id; 
    private ProyectoInforme proyectoInforme;
    private Actividad actividad;
    private String observaciones;
    private String desarrollada;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProyectoInforme getProyectoInforme() {
		return proyectoInforme;
	}

	public void setProyectoInforme(ProyectoInforme proyectoInforme) {
		this.proyectoInforme = proyectoInforme;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getDesarrollada() {
		return desarrollada;
	}

	public void setDesarrollada(String desarrollada) {
		this.desarrollada = desarrollada;
	}

	public int compare(ActividadInforme arg0, ActividadInforme arg1) {
		ActividadInforme a1=(ActividadInforme)arg0;
		ActividadInforme a2=(ActividadInforme)arg1;
		if(a1.getId()!=null && a2.getId()!=null)
		{
		   if((a1.getId()).longValue()<a1.getId().longValue())
		      return -1;	
		   if((a1.getId()).longValue()==a1.getId().longValue())
		      return 0;
		   if((a1.getId()).longValue()>a1.getId().longValue())
			  return 1;
	    }
		return 0;
	}
	
	public boolean equals(Object a)
	{
	    if(!(a  instanceof ActividadInforme))
	    {
	        return false;
	    }
	    ActividadInforme act= (ActividadInforme) a;
	    if(act.id==null || this.id == null )
	    {
	        return false;
        }
	    return(act.getId().equals(this.getId()));
	}
}
