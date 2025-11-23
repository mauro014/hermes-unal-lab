/*
 * Created on 23-feb-2023
 */
package co.edu.unal.hermes.modelo;

/**
 * Maneja los objetivos específicos que enmarcan cada proyecto
 */
public class ProyectoInformeObjetivoEspecifico {

	private Long id;
	private ObjetivoEspecifico objetivo;
	private ProyectoInforme informe;
	private float porcentajeAvance;
	private String justificacionAvance;
	private String comentariosCoordinador;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ObjetivoEspecifico getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(ObjetivoEspecifico objetivo) {
		this.objetivo = objetivo;
	}

	public ProyectoInforme getInforme() {
		return informe;
	}

	public void setInforme(ProyectoInforme informe) {
		this.informe = informe;
	}

	public float getPorcentajeAvance() {
		return porcentajeAvance;
	}

	public void setPorcentajeAvance(float porcentajeAvance) {
		this.porcentajeAvance = porcentajeAvance;
	}

	public String getJustificacionAvance() {
		return justificacionAvance;
	}

	public void setJustificacionAvance(String justificacionAvance) {
		this.justificacionAvance = justificacionAvance;
	}

	public String getComentariosCoordinador() {
		return comentariosCoordinador;
	}

	public void setComentariosCoordinador(String comentariosCoordinador) {
		this.comentariosCoordinador = comentariosCoordinador;
	}

}
