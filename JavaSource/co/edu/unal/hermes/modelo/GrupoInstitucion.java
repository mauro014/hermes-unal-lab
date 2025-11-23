package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class GrupoInstitucion implements Serializable {

	private static final long serialVersionUID = 1L;
	private Grupo grupo;
	private FuenteFinanciacion institucion;
	
	public GrupoInstitucion() {
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public FuenteFinanciacion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(FuenteFinanciacion institucion) {
		this.institucion = institucion;
	}
}