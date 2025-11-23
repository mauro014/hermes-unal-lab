package co.edu.unal.hermes.modelo;

public class GrupoAgenda {

	private Long id;
	private Grupo grupo;
	private PosibleAgendaGrupo agenda;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public PosibleAgendaGrupo getAgenda() {
		return agenda;
	}

	public void setAgenda(PosibleAgendaGrupo agenda) {
		this.agenda = agenda;
	}

}
