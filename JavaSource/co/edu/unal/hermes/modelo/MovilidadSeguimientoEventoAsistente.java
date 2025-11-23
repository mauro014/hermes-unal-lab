/*
 * Created on 9-dic-2007
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;


public class MovilidadSeguimientoEventoAsistente implements Serializable{

	private Long id;    
	private Long idEvento;
	private String detalleTipo;	
	
	public Long getIdEvento() {
		return idEvento;
	}	
	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDetalleTipo() {
		return detalleTipo;
	}

	public void setDetalleTipo(String detalleTipo) {
		this.detalleTipo = detalleTipo;
	}
		
	
}
	
	
