package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroProyecto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Semillero semillero;
	private Proyecto proyecto;
	
	public Semillero getSemillero() {
		return semillero;
	}

	public void setSemillero(Semillero semillero) {
		this.semillero = semillero;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
}