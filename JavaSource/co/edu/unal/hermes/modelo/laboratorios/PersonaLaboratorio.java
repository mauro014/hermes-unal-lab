package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.TipoFormacion;
import co.edu.unal.hermes.modelo.Tipos;

/**
 * Objeto que relaciona a una Persona con un Laboratorio, asignándole un Rol
 * dentro de él.
 * 
 * @author dgbenitezc
 */
public class PersonaLaboratorio {

	private Long id;
	private Persona persona;
	private Laboratorio laboratorio;
	private Rol rol;
	private String cargo;
	private Tipos tipoVinculacion;
	private String extension;
	private TipoFormacion tipoFormacion;
	private Boolean permisoConsulta;
	private Boolean permisoEdicion;
	private Date fechaCreacion;
	
	/**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * @param persona
	 *            the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * @return the laboratorio
	 */
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	/**
	 * @param laboratorio
	 *            the laboratorio to set
	 */
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	/**
	 * @return the rol
	 */
	public Rol getRol() {
		return rol;
	}

	/**
	 * @param rol
	 *            the rol to set
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the cargo
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * @param cargo
	 *            the cargo to set
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return the tipoVinculacion
	 */
	public Tipos getTipoVinculacion() {
		return tipoVinculacion;
	}

	/**
	 * @param tipoVinculacion
	 *            the tipoVinculacion to set
	 */
	public void setTipoVinculacion(Tipos tipoVinculacion) {
		this.tipoVinculacion = tipoVinculacion;
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension
	 *            the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	public TipoFormacion getTipoFormacion() {
		return tipoFormacion;
	}

	public void setTipoFormacion(TipoFormacion tipoFormacion) {
		this.tipoFormacion = tipoFormacion;
	}

	public Boolean getPermisoConsulta() {
		return permisoConsulta;
	}

	public void setPermisoConsulta(Boolean permisoConsulta) {
		this.permisoConsulta = permisoConsulta;
	}

	public Boolean getPermisoEdicion() {
		return permisoEdicion;
	}

	public void setPermisoEdicion(Boolean permisoEdicion) {
		this.permisoEdicion = permisoEdicion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
