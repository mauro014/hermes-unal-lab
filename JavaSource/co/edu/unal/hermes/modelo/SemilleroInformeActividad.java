package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroInformeActividad implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private SemilleroInforme informe;
	private SemilleroActividad actividad;
	private Integer porcentajeAvance;
	private String avance;
	private String descripcionActividad;
	private Integer responsableActividad; 

	public SemilleroInformeActividad() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SemilleroInforme getInforme() {
		return informe;
	}

	public void setInforme(SemilleroInforme informe) {
		this.informe = informe;
	}

	public SemilleroActividad getActividad() {
		return actividad;
	}

	public void setActividad(SemilleroActividad actividad) {
		this.actividad = actividad;
	}

	public Integer getPorcentajeAvance() {
		return porcentajeAvance;
	}

	public void setPorcentajeAvance(Integer porcentajeAvance) {
		this.porcentajeAvance = porcentajeAvance;
	}

	public String getAvance() {
		return avance;
	}

	public void setAvance(String avance) {
		this.avance = avance;
	}

	public Integer getResponsableActividad() {
		return responsableActividad;
	}

	public void setResponsableActividad(Integer responsableActividad) {
		this.responsableActividad = responsableActividad;
	}

	public String getDescripcionActividad() {
		return descripcionActividad;
	}

	public void setDescripcionActividad(String descripcionActividad) {
		this.descripcionActividad = descripcionActividad;
	}
}