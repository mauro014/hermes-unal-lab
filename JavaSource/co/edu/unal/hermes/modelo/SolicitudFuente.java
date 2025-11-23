package co.edu.unal.hermes.modelo;

import java.util.Date;


public class SolicitudFuente{


    private Long id; // llave primaria
    private String idSolicitante;
    private String tdoIdSolicitante;
    private String nombreSolicitante;
    private String apellidoSolicitante;
    private String emailSolicitante;
    private String estado;
    private Date fechaRegistro;
	private String idIngeniero;
	private String tdoIdIngeniero;
    private String fechaAtencion;
	private Long idFuenteFinanciacion;
	
	
    //Get /set 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdSolicitante() {
		return idSolicitante;
	}
	public void setIdSolicitante(String idSolicitante) {
		this.idSolicitante = idSolicitante;
	}
	public String getTdoIdSolicitante() {
		return tdoIdSolicitante;
	}
	public void setTdoIdSolicitante(String tdoIdSolicitante) {
		this.tdoIdSolicitante = tdoIdSolicitante;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getIdIngeniero() {
		return idIngeniero;
	}
	public void setIdIngeniero(String idIngeniero) {
		this.idIngeniero = idIngeniero;
	}
	public String getTdoIdIngeniero() {
		return tdoIdIngeniero;
	}
	public void setTdoIdIngeniero(String tdoIdIngeniero) {
		this.tdoIdIngeniero = tdoIdIngeniero;
	}
	public String getFechaAtencion() {
		return fechaAtencion;
	}
	public void setFechaAtencion(String fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}
	
	public String getNombreSolicitante() {
		return nombreSolicitante;
	}
	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}
	public String getApellidoSolicitante() {
		return apellidoSolicitante;
	}
	public void setApellidoSolicitante(String apellidoSolicitante) {
		this.apellidoSolicitante = apellidoSolicitante;
	}
	public String getEmailSolicitante() {
		return emailSolicitante;
	}
	public void setEmailSolicitante(String emailSolicitante) {
		this.emailSolicitante = emailSolicitante;
	}
	public Long getIdFuenteFinanciacion() {
		return idFuenteFinanciacion;
	}
	public void setIdFuenteFinanciacion(Long idFuenteFinanciacion) {
		this.idFuenteFinanciacion = idFuenteFinanciacion;
	}
	
	
   
}
