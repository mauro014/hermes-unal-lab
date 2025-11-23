package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroAgenda implements Serializable {

	private static final long serialVersionUID = 1L;
	private Semillero semillero;
	private PosibleAgendaGrupo agenda;

	public Semillero getSemillero() {
		return semillero;
	}

	public void setSemillero(Semillero semillero) {
		this.semillero = semillero;
	}

	public PosibleAgendaGrupo getAgenda() {
		return agenda;
	}

	public void setAgenda(PosibleAgendaGrupo agenda) {
		this.agenda = agenda;
	}
}