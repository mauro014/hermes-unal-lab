/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Específica la actividad asociada al proyecto.
 * 
 */
public class IdHorariosECP implements Serializable{
        
    /**
     * 
     */
    private static final long serialVersionUID = -3858115715049032954L;

    private Proyecto proyecto;
    private String dia;
    
    
    
    public boolean equals(Object obj){
	    if(obj instanceof IdHorariosECP){
		IdHorariosECP idHorarios = (IdHorariosECP)obj;
	        if(idHorarios.getDia().equals(dia) && idHorarios.getProyecto().getId().toString().equals(proyecto.getId().toString())){
	            return true;
	        }
	    }
		return false;
	}



    public Proyecto getProyecto() {
        return proyecto;
    }



    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }



    public String getDia() {
        return dia;
    }



    public void setDia(String dia) {
        this.dia = dia;
    }    
   
    

	

}
