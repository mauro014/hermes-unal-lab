package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class SemilleroSede implements Serializable {

	private static final long serialVersionUID = 1L;
	private Semillero semillero;
	private Sede sede;
	private Date fechaRegistro;
	private Date fechaBorrado;

	public SemilleroSede() {
		setFechaRegistro(null);
		setFechaBorrado(null);
	}

	public Semillero getSemillero() {
		return semillero;
	}

	public void setSemillero(Semillero semillero) {
		this.semillero = semillero;
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
}