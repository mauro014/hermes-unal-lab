package co.edu.unal.hermes.modelo;

import java.util.Date;

public class HistoricoEstadoInforme {

	private Long id;
	private EstadoInforme estadoInforme;
	private ProyectoInforme proyectoInforme;
	private Date fecha;
	private String comentarios;
	private Persona responsable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EstadoInforme getEstadoInforme() {
		return estadoInforme;
	}

	public void setEstadoInforme(EstadoInforme estadoInforme) {
		this.estadoInforme = estadoInforme;
	}

	public ProyectoInforme getProyectoInforme() {
		return proyectoInforme;
	}

	public void setProyectoInforme(ProyectoInforme proyectoInforme) {
		this.proyectoInforme = proyectoInforme;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

}
