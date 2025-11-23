package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;

public class SemilleroLaboratorio implements Serializable {

	private static final long serialVersionUID = 1L;
	private Semillero semillero;
	private Laboratorio laboratorio;
	private Date fechaRegistro;
	private Date fechaBorrado;

	public SemilleroLaboratorio() {
		setFechaRegistro(null);
		setFechaBorrado(null);
	}

	public Semillero getSemillero() {
		return semillero;
	}

	public void setSemillero(Semillero semillero) {
		this.semillero = semillero;
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
}