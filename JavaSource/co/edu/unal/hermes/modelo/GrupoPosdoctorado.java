/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Específica la actividad asociada a la movilidad de una visita.
 * 
 */
public class GrupoPosdoctorado implements Serializable{
        
    private Long id;    
    private String grupo;
    private String estancia; 
   
    
    public boolean equals(Object a)
	{
	    if(!(a  instanceof GrupoPosdoctorado))
	    {
	        return false;
	    }
	    GrupoPosdoctorado act= (GrupoPosdoctorado) a;
	    
	   /* if(act.id==null || this.id == null )
	    {
	        return false; 
        }
	    
	   boolean bandera = false;
	    
	    if(act.getEstancia().equals(this.estancia) && act.getGrupo().equals(this.grupo))
	    {
	    	bandera = true;
	    }
	  
	    return bandera;//(act.getId().equals(this.getId()));*/
	    
	    if(act.id==null || this.id == null )
	    {
	    	  if(act.getGrupo().equals(this.grupo) && act.getEstancia().equals(this.estancia))
	  	    {
	  	        return true;
	          }
        }else {
        	return(act.getId().equals(this.getId()));
		}
	    
	    return false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstancia() {
		return estancia;
	}

	public void setEstancia(String estancia) {
		this.estancia = estancia;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	
	
}
