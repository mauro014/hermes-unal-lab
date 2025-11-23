package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroObjetivo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Semillero semillero;
	private String objetivo;

	public Semillero getSemillero() {
		return semillero;
	}

	public void setSemillero(Semillero semillero) {
		this.semillero = semillero;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
}