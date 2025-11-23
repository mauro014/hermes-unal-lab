/*
 * Created on 23-feb-2023
 */
package co.edu.unal.hermes.modelo;

/**
 * Maneja los objetivos específicos que enmarcan cada proyecto
 */
public class ProyectoInformeActividad {

	private Long id;
	private Long idObjetivo;
	private ProyectoInforme informe;
	private Actividad actividad;
	private float porcentajeAvance;
	private String justificacionAvance;
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

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Long getIdObjetivo() {
		return idObjetivo;
	}

	public void setIdObjetivo(Long idObjetivo) {
		this.idObjetivo = idObjetivo;
	}

	public String getComentariosCoordinador() {
		return comentariosCoordinador;
	}

	public void setComentariosCoordinador(String comentariosCoordinador) {
		this.comentariosCoordinador = comentariosCoordinador;
	}

}
