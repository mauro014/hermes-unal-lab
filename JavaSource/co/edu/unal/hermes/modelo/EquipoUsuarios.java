/*
 * Created on 26-abr-2006
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * @author Jassar David Issa Co
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EquipoUsuarios implements Serializable{
	 
	private	Long	id;//es una secuencia generada por la base de datos
	private	Long	cantidadUsuarios;
	
	private Equipo equipo;
	private VinculacionUN vinculacionUsuario;
	

	public Long getCantidadUsuarios() {
		return cantidadUsuarios;
	}
	public void setCantidadUsuarios(Long cantidadUsuarios) {
		this.cantidadUsuarios = cantidadUsuarios;
	}
	public Equipo getEquipo() {
		return equipo;
	}
	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public VinculacionUN getVinculacionUsuario() {
		return vinculacionUsuario;
	}
	public void setVinculacionUsuario(VinculacionUN vinculacionUsuario) {
		this.vinculacionUsuario = vinculacionUsuario;
	}

}
