package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class SemilleroFacultad implements Serializable {

	private static final long serialVersionUID = 1L;
	private Semillero semillero;
	private Dependencia facultad;
	private Date fechaRegistro;
	private Date fechaBorrado;
	
	public SemilleroFacultad() {
		setFechaRegistro(null);
		setFechaBorrado(null);
	}

	public Semillero getSemillero() {
		return semillero;
	}

	public void setSemillero(Semillero semillero) {
		this.semillero = semillero;
	}

	public Dependencia getFacultad() {
		return facultad;
	}

	public void setFacultad(Dependencia facultad) {
		this.facultad = facultad;
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