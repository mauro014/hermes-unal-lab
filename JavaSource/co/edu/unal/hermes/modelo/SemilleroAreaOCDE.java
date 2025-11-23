package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroAreaOCDE implements Serializable {

	private static final long serialVersionUID = 1L;
	private Semillero semillero;
	private DominioDetalle subAreaOCDE;
	private DominioDetalle areaOCDE;

	public Semillero getSemillero() {
		return semillero;
	}

	public void setSemillero(Semillero semillero) {
		this.semillero = semillero;
	}

	public DominioDetalle getSubAreaOCDE() {
		return subAreaOCDE;
	}

	public void setSubAreaOCDE(DominioDetalle subAreaOCDE) {
		this.subAreaOCDE = subAreaOCDE;
	}

	public DominioDetalle getAreaOCDE() {
		return areaOCDE;
	}

	public void setAreaOCDE(DominioDetalle areOCDE) {
		this.areaOCDE = areOCDE;
	}
}