/*
 * Created on 9-dic-2007
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class MovilidadSeguimientoEvento implements Serializable{

	private Long id;
	private String detalleTipo;
	private String asistente;
	private String movilidadId;
	
    //asistentes
	private Set asistentes = new HashSet();

	public String getAsistente() {
		return asistente;
	}
	public Long getId() {
		return id;
	}
	
	public String getDetalleTipo() {
		return detalleTipo;
	}
	public void setDetalleTipo(String detalleTipo) {
		this.detalleTipo = detalleTipo;
	}
	public String getMovilidadId() {
		return movilidadId;
	}
	public void setAsistente(String asistente) {
		this.asistente = asistente;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public void setMovilidadId(String movilidadId) {
		this.movilidadId = movilidadId;
	}
	public Set getAsistentes() {
		return asistentes;
	}
	public void setAsistentes(Set asistentes) {
		this.asistentes = asistentes;
	}
	public ArrayList getListaAsistentes(){
		ArrayList list = new ArrayList();
		list.addAll(asistentes);
		return list;
	}
	
}
	
	
