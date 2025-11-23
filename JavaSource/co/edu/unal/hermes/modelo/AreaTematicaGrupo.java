package co.edu.unal.hermes.modelo;

public class AreaTematicaGrupo {

	private Long id;
	private DominioDetalle areaTematica;
	private DominioDetalle areaTematicaPadre;
	private Grupo grupo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DominioDetalle getAreaTematica() {
		return areaTematica;
	}

	public void setAreaTematica(DominioDetalle areaTematica) {
		this.areaTematica = areaTematica;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public DominioDetalle getAreaTematicaPadre() {
		return areaTematicaPadre;
	}

	public void setAreaTematicaPadre(DominioDetalle areaTematicaPadre) {
		this.areaTematicaPadre = areaTematicaPadre;
	}
	
	

}
