/*
 * Created on 26-may-2014
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class InvestigadorAsignatura implements Serializable {

	private static final long serialVersionUID = -3472571957350852078L;

	private Long id;
	private VAsignaturasSIA asignatura;
	private Investigador investigador;
	private Long materia;

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

	public Long getMateria() {
		return materia;
	}

	public void setMateria(Long materia) {
		this.materia = materia;
	}

	public VAsignaturasSIA getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(VAsignaturasSIA asignatura) {
		this.asignatura = asignatura;
	}

}
