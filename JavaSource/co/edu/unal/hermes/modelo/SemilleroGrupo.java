package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class SemilleroGrupo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Semillero semillero;
	private Grupo grupo;
	private Date fechaRegistro;
	private Date fechaBorrado;
	private String agregoSemillero;
	private Persona responsable;
	
	public SemilleroGrupo() {
		setFechaRegistro(null);
		setFechaBorrado(null);
	}

	public Semillero getSemillero() {
		return semillero;
	}

	public void setSemillero(Semillero semillero) {
		this.semillero = semillero;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
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

	public String getAgregoSemillero() {
		return agregoSemillero;
	}

	public void setAgregoSemillero(String agregoSemillero) {
		this.agregoSemillero = agregoSemillero;
	}

	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}
}