package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * Maneja los objetivos específicos que enmarcan cada proyecto
 */
public class ProyectoInformeDepositoBio {

	private Long id;
	private ProyectoInforme informe;
	private String tipo;
	private int numeroespecimenes;
	private Coleccion coleccion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ProyectoInforme getInforme() {
		return informe;
	}
	public void setInforme(ProyectoInforme informe) {
		this.informe = informe;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getNumeroespecimenes() {
		return numeroespecimenes;
	}
	public void setNumeroespecimenes(int numeroespecimenes) {
		this.numeroespecimenes = numeroespecimenes;
	}
	public Coleccion getColeccion() {
		return coleccion;
	}
	public void setColeccion(Coleccion coleccion) {
		this.coleccion = coleccion;
	}

}
