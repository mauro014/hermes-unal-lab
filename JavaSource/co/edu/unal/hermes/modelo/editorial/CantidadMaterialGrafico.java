package co.edu.unal.hermes.modelo.editorial;

import co.edu.unal.hermes.modelo.Proyecto;

public class CantidadMaterialGrafico {

	private Long id;
	private TipoMaterialGrafico tipoMaterialGrafico;
	private Proyecto proyecto;
	private int cantidadPropio;
	private int cantidadAjeno;
	
	public CantidadMaterialGrafico() {	}
	
	public CantidadMaterialGrafico(TipoMaterialGrafico tipoMaterialGrafico) {
		this.tipoMaterialGrafico = tipoMaterialGrafico;
		cantidadPropio = 0;
		cantidadAjeno = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCantidadAjeno() {
		return cantidadAjeno;
	}

	public void setCantidadAjeno(int cantidadAjeno) {
		this.cantidadAjeno = cantidadAjeno;
	}

	public int getCantidadPropio() {
		return cantidadPropio;
	}

	public void setCantidadPropio(int cantidadPropio) {
		this.cantidadPropio = cantidadPropio;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public TipoMaterialGrafico getTipoMaterialGrafico() {
		return tipoMaterialGrafico;
	}

	public void setTipoMaterialGrafico(TipoMaterialGrafico tipoMaterialGrafico) {
		this.tipoMaterialGrafico = tipoMaterialGrafico;
	}
	
	public int getTotal() {
		return cantidadAjeno + cantidadPropio;
	}

}
