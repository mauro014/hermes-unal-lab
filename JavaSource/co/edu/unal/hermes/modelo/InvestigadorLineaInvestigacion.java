/*
87 * Created on 26-may-2014
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class InvestigadorLineaInvestigacion implements Serializable{
        
 
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private Long id;    
    private Investigador investigador;
    private LineaInvestigacion linea;
    

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Investigador getInvestigador() {
		return investigador;
	}
	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}
	public LineaInvestigacion getLinea() {
		return linea;
	}
	public void setLinea(LineaInvestigacion linea) {
		this.linea = linea;
	}
	
}
