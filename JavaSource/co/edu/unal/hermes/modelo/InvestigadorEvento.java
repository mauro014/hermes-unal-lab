/*
87 * Created on 26-may-2014
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class InvestigadorEvento implements Serializable {

	private static final long serialVersionUID = 1548098089480653008L;

	private Long id;
	private String nombre;
	private Pais pais;
	private String ciudadPais;
	private String tituloTrabajo;
	private Date fecha;
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

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public String getTituloTrabajo() {
		return tituloTrabajo;
	}

	public void setTituloTrabajo(String tituloTrabajo) {
		this.tituloTrabajo = tituloTrabajo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Investigador getInvestigador() {
		return investigador;
	}

	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}

	public String getCiudadPais() {
		return ciudadPais;
	}

	public void setCiudadPais(String ciudadPais) {
		this.ciudadPais = ciudadPais;
	}

}
