/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Específica la actividad asociada al proyecto.
 * 
 */
public class IdHorariosGruposECP implements Serializable{
        
    /**
     * 
     */
    private static final long serialVersionUID = -3858115715049032954L;

    private Proyecto proyecto;
    private String dia;
    private String grupo;
    
    
    
    public boolean equals(Object obj){
	    if(obj instanceof IdHorariosGruposECP){
		IdHorariosGruposECP idHorarios = (IdHorariosGruposECP)obj;
	        if(idHorarios.getDia().equals(dia) && idHorarios.getProyecto().getId().toString().equals(proyecto.getId().toString()) && idHorarios.getGrupo().equals(grupo)){
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



	public String getGrupo() {
		return grupo;
	}



	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}    
   
    

	

}
