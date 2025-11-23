package co.edu.unal.hermes.modelo;

import java.util.Date;


public class SolicitudUsuario{


    private Long id; // llave primaria
    private String usuarioDocumento;
    private String usuarioTipoDocumento;
    private String estadoSol;
    private String nombreEstadoSol;
	private String apellido1;
	private String apellido2;
    private String nombre1;
	private String nombre2;
	private String email;
	public Persona usuario;
	private Date fechaSolicitud = new Date();
	private String solicDocumento;
    private String solicTipoDocumento;
    Dependencia dependencia; 
	private String observaciones;
	private String comentarios;
	private Date fechaAprobacion;
	private String responsableDocumento;
    private String responsableTipoDocumento;
    private Date fechaFin = new Date();
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNombre1() {
		return nombre1;
	}
	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}
	public String getNombre2() {
		return nombre2;
	}
	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
    /*
	public String getDependencia() {
		return dependencia;
	}
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}
	*/
	public Dependencia getDependencia() {
		return dependencia;
	}
	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}
	public String getEstadoSol() {
		//String accionNombre = new String();
				if(this.estadoSol.equals("A")){
					this.nombreEstadoSol=("APROBADA");
				}
				if(this.estadoSol.equals("N")){
					this.nombreEstadoSol=("NEGADA");
				}
				if(this.estadoSol.equals("I")){
					this.nombreEstadoSol=("INGRESANDO");
				}
				if(this.estadoSol.equals("P")){
					this.nombreEstadoSol=("PROPUESTA");
				}
		
		return estadoSol;
	}
	public void setEstadoSol(String estadoSol) {
		this.estadoSol = estadoSol;
	}
	
	
	
	public String getUsuarioDocumento() {
		return usuarioDocumento;
	}
	public void setUsuarioDocumento(String usuarioDocumento) {
		this.usuarioDocumento = usuarioDocumento;
	}
	public String getUsuarioTipoDocumento() {
		return usuarioTipoDocumento;
	}
	public void setUsuarioTipoDocumento(String usuarioTipoDocumento) {
		this.usuarioTipoDocumento = usuarioTipoDocumento;
	}
	public String getSolicDocumento() {
		return solicDocumento;
	}
	public void setSolicDocumento(String solicDocumento) {
		this.solicDocumento = solicDocumento;
	}
	public String getSolicTipoDocumento() {
		return solicTipoDocumento;
	}
	public void setSolicTipoDocumento(String solicTipoDocumento) {
		this.solicTipoDocumento = solicTipoDocumento;
	}
	public Persona getUsuario() {
		return usuario;
	}
	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
    
	public String getNombreEstadoSol() {
		return nombreEstadoSol;
	}
	public void setNombreEstadoSol(String nombreEstadoSol) {
		this.nombreEstadoSol = nombreEstadoSol;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}
	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}
	public String getResponsableDocumento() {
		return responsableDocumento;
	}
	public void setResponsableDocumento(String responsableDocumento) {
		this.responsableDocumento = responsableDocumento;
	}
	public String getResponsableTipoDocumento() {
		return responsableTipoDocumento;
	}
	public void setResponsableTipoDocumento(String responsableTipoDocumento) {
		this.responsableTipoDocumento = responsableTipoDocumento;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
   
}
