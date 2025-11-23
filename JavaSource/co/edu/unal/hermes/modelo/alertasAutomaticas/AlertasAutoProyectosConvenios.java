package co.edu.unal.hermes.modelo.alertasAutomaticas;

import java.util.Date;


public class AlertasAutoProyectosConvenios implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long proyectoId;
	private String tituloProyecto;
	private Date fechaFinConvenio;
	private String emailInvestigador;
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
	public Date getFechaFinConvenio() {
		return fechaFinConvenio;
	}
	public void setFechaFinConvenio(Date fechaFinConvenio) {
		this.fechaFinConvenio = fechaFinConvenio;
	}
	public String getEmailInvestigador() {
		return emailInvestigador;
	}
	public void setEmailInvestigador(String emailInvestigador) {
		this.emailInvestigador = emailInvestigador;
	}
	public String getEmailCoordinador() {
		return emailCoordinador;
	}
	public void setEmailCoordinador(String emailCoordinador) {
		this.emailCoordinador = emailCoordinador;
	}
	
}
