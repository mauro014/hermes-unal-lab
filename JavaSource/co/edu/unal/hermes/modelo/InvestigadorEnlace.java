/*
 * Created on 26-may-2014
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class InvestigadorEnlace implements Serializable {

	private static final long serialVersionUID = 6106986103943492462L;

	private Long id;
	private String nombre;
	private String link;
	private Investigador investigador;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Investigador getInvestigador() {
		return investigador;
	}

	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
