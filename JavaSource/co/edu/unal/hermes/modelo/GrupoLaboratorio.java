package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;

public class GrupoLaboratorio implements Serializable {

	private static final long serialVersionUID = 1L;
	private Grupo grupo;
	private Laboratorio laboratorio;
	private Date fechaRegistro;
	private Date fechaBorrado;
	private Persona responsableRetiro;

	public GrupoLaboratorio() {
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
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

	public Persona getResponsableRetiro() {
		return responsableRetiro;
	}

	public void setResponsableRetiro(Persona responsableRetiro) {
		this.responsableRetiro = responsableRetiro;
	}
}