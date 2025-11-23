package co.edu.unal.hermes.modelo.alertasAutomaticas;

import java.util.Date;

public class AlertasAutoProyectos {

private static final long serialVersionUID = 1L;
	
	//Proyecto
	private Long proyectoId;
	private String tituloProyecto;
	private Date fechaFinalProyecto;
	//Investigador
	private String nombreDocentePrincipal;
	//Coordinador
	private String emailCoordinador;
	
	public Long getProyectoId() {
		return proyectoId;
	}
	public void setProyectoId(Long proyectoId) {
		this.proyectoId = proyectoId;
	}
	public String getTituloProyecto() {
		return tituloProyecto;
	}
	public void setTituloProyecto(String tituloProyecto) {
		this.tituloProyecto = tituloProyecto;
	}
	public Date getFechaFinalProyecto() {
		return fechaFinalProyecto;
	}
	public void setFechaFinalProyecto(Date fechaFinalProyecto) {
		this.fechaFinalProyecto = fechaFinalProyecto;
	}
	public String getEmailCoordinador() {
		return emailCoordinador;
	}
	public void setEmailCoordinador(String emailCoordinador) {
		this.emailCoordinador = emailCoordinador;
	}
	public String getNombreDocentePrincipal() {
		return nombreDocentePrincipal;
	}
	public void setNombreDocentePrincipal(String nombreDocentePrincipal) {
		this.nombreDocentePrincipal = nombreDocentePrincipal;
	}
	
}
