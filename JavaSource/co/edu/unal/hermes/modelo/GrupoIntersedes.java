package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class GrupoIntersedes implements Serializable {

	private static final long serialVersionUID = 7302989567688868304L;
	private Long id;
	private Grupo grupo;
	private Sede sede;
	private Date fechaRegistro;
	private Date fechaBorrado;
	private Persona responsableRegistro;
	private Persona responsableRetiro;

	public GrupoIntersedes() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaBorrado() {
		return fechaBorrado;
	}

	public void setFechaBorrado(Date fechaBorrado) {
		this.fechaBorrado = fechaBorrado;
	}

	public Persona getResponsableRegistro() {
		return responsableRegistro;
	}

	public void setResponsableRegistro(Persona responsableRegistro) {
		this.responsableRegistro = responsableRegistro;
	}

	public Persona getResponsableRetiro() {
		return responsableRetiro;
	}

	public void setResponsableRetiro(Persona responsableRetiro) {
		this.responsableRetiro = responsableRetiro;
	}

}
