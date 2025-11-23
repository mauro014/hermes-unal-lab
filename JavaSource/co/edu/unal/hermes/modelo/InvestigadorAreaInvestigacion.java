/*
 * Created on 26-may-2014
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class InvestigadorAreaInvestigacion implements Serializable {

	private static final long serialVersionUID = -2296346577220633747L;

	private Long id;
	private DominioDetalle area;
	private String nombreArea;
	private Investigador investigador;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Investigador getInvestigador() {
		return investigador;
	}

	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}

	public DominioDetalle getArea() {
		return area;
	}

	public void setArea(DominioDetalle area) {
		this.area = area;
	}

	public String getNombreArea() {
		return nombreArea;
	}

	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

}
