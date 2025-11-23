package co.edu.unal.hermes.modelo;

import java.io.Serializable;

import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;

public class LaboratorioAval implements Serializable {

	private static final long serialVersionUID = 1L;
	private Laboratorio laboratorio;
	private Aval aval;

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	public Aval getAval() {
		return aval;
	}

	public void setAval(Aval aval) {
		this.aval = aval;
	}
}
