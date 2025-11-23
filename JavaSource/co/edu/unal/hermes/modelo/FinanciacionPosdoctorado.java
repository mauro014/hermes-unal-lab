/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Específica la actividad asociada a la movilidad de una visita.
 * 
 */
public class FinanciacionPosdoctorado implements Serializable{
        
    private Long id;    
    private String tipo;
    private String valor;
    private String fuente;
    private String estancia; 
   
    
    public boolean equals(Object a)
	{
	    if(!(a  instanceof FinanciacionPosdoctorado))
	    {
	        return false;	    }
	 
	    FinanciacionPosdoctorado act= (FinanciacionPosdoctorado) a;
	    
	    if(act.id==null || this.id == null )
	    {
	    	  if(act.tipo.equals(this.tipo) && act.valor.equals(this.valor) && act.fuente.equals(this.fuente)  && act.estancia.equals(this.estancia))
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public String getEstancia() {
		return estancia;
	}

	public void setEstancia(String estancia) {
		this.estancia = estancia;
	}



	
	
}
