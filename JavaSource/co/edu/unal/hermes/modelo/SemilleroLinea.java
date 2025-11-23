package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroLinea implements Serializable {

	private static final long serialVersionUID = 1L;
	private Semillero semillero;
	private LineaInvestigacion linea;

	public Semillero getSemillero() {
		return semillero;
	}

	public void setSemillero(Semillero semillero) {
		this.semillero = semillero;
	}

	public LineaInvestigacion getLinea() {
		return linea;
	}

	public void setLinea(LineaInvestigacion linea) {
		this.linea = linea;
	}
}