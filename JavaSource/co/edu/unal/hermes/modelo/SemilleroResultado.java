package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroResultado implements Serializable {

	private static final long serialVersionUID = 1L;
	private Semillero semillero;
	private String resultado;

	public Semillero getSemillero() {
		return semillero;
	}

	public void setSemillero(Semillero semillero) {
		this.semillero = semillero;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String objetivo) {
		this.resultado = objetivo;
	}
}