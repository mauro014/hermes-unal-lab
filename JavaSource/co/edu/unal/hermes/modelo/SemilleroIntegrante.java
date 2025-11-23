package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SemilleroIntegrante implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Semillero semillero;
	private Investigador integrante;
	private SemilleroIntegranteTipo tipo;
	private String actividades;
	private Set<SemilleroActividad> planTrabajo;
	private Integer horasDedicacion;
	private String planEstudios;
	private String nivelPlanEstudios;
	private String estado; // R para retirado, A: Activo
	public static final String ESTADO_RETIRADO_SEM = "R";

	public SemilleroIntegrante() {
		setSemillero(new Semillero());
		setIntegrante(new Investigador());
		setTipo(new SemilleroIntegranteTipo());
		setPlanTrabajo(new HashSet<SemilleroActividad>());
		this.estado = "A";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Semillero getSemillero() {
		return semillero;
	}

	public void setSemillero(Semillero semillero) {
		this.semillero = semillero;
	}

	public Investigador getIntegrante() {
		return integrante;
	}

	public void setIntegrante(Investigador investigador) {
		this.integrante = investigador;
	}

	public SemilleroIntegranteTipo getTipo() {
		return tipo;
	}

	public void setTipo(SemilleroIntegranteTipo tipo) {
		this.tipo = tipo;
	}

	public boolean esExterno() {
		return getTipo().getId().equals("EE") || getTipo().getId().equals("DE") || getTipo().getId().equals("EG") || getTipo().getId().equals("PRO");
	}

	public boolean esLider() {
		return getTipo().getId().equals("DD");
	}
	
	public boolean esEstudianteLider() {
		return getTipo().getId().equals("EL");
	}

	public String getActividades() {
		return actividades;
	}

	public void setActividades(String actividades) {
		this.actividades = actividades;
	}

	public Set<SemilleroActividad> getPlanTrabajo() {
		return planTrabajo;
	}

	public void setPlanTrabajo(Set<SemilleroActividad> planTrabajo) {
		this.planTrabajo = planTrabajo;
	}

	public ArrayList<SemilleroActividad> getListaPlanTrabajo() {
		ArrayList<SemilleroActividad> retVal = new ArrayList<SemilleroActividad>();
		for (SemilleroActividad sf : getPlanTrabajo()) {
			retVal.add(sf);
		}
		return retVal;
	}

	public String getPlanEstudios() {
		return planEstudios;
	}

	public void setPlanEstudios(String planEstudios) {
		this.planEstudios = planEstudios;
	}
	
	public String getUsuario() {
		int arroba = integrante.getEmail().indexOf("@");
		if (arroba != -1) {
			return integrante.getEmail().substring(0, arroba);
		} else {
			return integrante.getEmail();
		}
	}

	public Integer getHorasDedicacion() {
		return horasDedicacion;
	}

	public void setHorasDedicacion(Integer horasDedicacion) {
		this.horasDedicacion = horasDedicacion;
	}

	public String getNivelPlanEstudios() {
		return nivelPlanEstudios;
	}

	public void setNivelPlanEstudios(String nivelPlanEstudios) {
		this.nivelPlanEstudios = nivelPlanEstudios;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		if (estado == null) {
	        this.estado = "A";
	    } else {
	        this.estado = estado;
	    }
	}
}