/*
 * Created on 23-feb-2023
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * Maneja los resultados reporntados en los informes de proyectos
 */
public class ProyectoInformeResultado {

	private Long id;
	private ProyectoInforme informe;
	private ResultadoProyecto resultado;
	private boolean seEntrega;
	private Date fechaRealEntrega;
	private String justificacion;
	private String comentariosCoordinador;

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

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public ResultadoProyecto getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoProyecto resultado) {
		this.resultado = resultado;
	}

	public Date getFechaRealEntrega() {
		return fechaRealEntrega;
	}

	public void setFechaRealEntrega(Date fechaRealEntrega) {
		this.fechaRealEntrega = fechaRealEntrega;
	}

	public boolean isSeEntrega() {
		return seEntrega;
	}

	public void setSeEntrega(boolean seEntrega) {
		this.seEntrega = seEntrega;
	}

	public String getComentariosCoordinador() {
		return comentariosCoordinador;
	}

	public void setComentariosCoordinador(String comentariosCoordinador) {
		this.comentariosCoordinador = comentariosCoordinador;
	}

}
