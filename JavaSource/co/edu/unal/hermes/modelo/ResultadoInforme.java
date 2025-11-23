/*
 * Created on 24/09/2014
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Comparator;


public class ResultadoInforme implements Comparator<ResultadoInforme>,Serializable{
       
	private static final long serialVersionUID = 3093423269863257309L;
	private Long id; 
    private ProyectoInforme proyectoInforme;
    private ResultadoProyecto resultadoProyecto;
    private String observaciones;
    private String logrado;
    
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

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int compare(ResultadoInforme arg0, ResultadoInforme arg1) {
		ResultadoInforme a1=(ResultadoInforme)arg0;
		ResultadoInforme a2=(ResultadoInforme)arg1;
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
	    if(!(a  instanceof ResultadoInforme))
	    {
	        return false;
	    }
	    ResultadoInforme act= (ResultadoInforme) a;
	    if(act.id==null || this.id == null )
	    {
	        return false;
        }
	    return(act.getId().equals(this.getId()));
	}

	public ResultadoProyecto getResultadoProyecto() {
		return resultadoProyecto;
	}

	public void setResultadoProyecto(ResultadoProyecto resultadoProyecto) {
		this.resultadoProyecto = resultadoProyecto;
	}

	public String getLogrado() {
		return logrado;
	}

	public void setLogrado(String logrado) {
		this.logrado = logrado;
	}
}
