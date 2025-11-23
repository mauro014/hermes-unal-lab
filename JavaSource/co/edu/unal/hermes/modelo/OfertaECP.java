package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class OfertaECP implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 933966367921121117L;
    
    private Long id;
    private Dependencia dependencia;
    private CatalogoECP catalogo;
    private String vigencia;
    private String semestre;
    private Date fechaInicio;
    private Long numHoras;
    private String tipoCertificado;
    private Long valorInscripcion;
    private String contactoNombre;
    private String contactoEmail;
    private String contactoTelefono;
    private String contactoExtension;
    private String contactoWeb;
    private Long participanOtrasFacultades;
    private Long participanOtrasSedes;
    private String observaciones;
    private String estadoOferta;
    private Long registroCompleto;
    private Date fechaRegistro;
    private Long persona;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Dependencia getDependencia() {
	return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
	this.dependencia = dependencia;
    }

    public CatalogoECP getCatalogo() {
	return catalogo;
    }

    public void setCatalogo(CatalogoECP catalogo) {
	this.catalogo = catalogo;
    }

    public String getVigencia() {
	return vigencia;
    }

    public void setVigencia(String vigencia) {
	this.vigencia = vigencia;
    }

    public String getSemestre() {
	return semestre;
    }

    public void setSemestre(String semestre) {
	this.semestre = semestre;
    }

    public Date getFechaInicio() {
	return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
	this.fechaInicio = fechaInicio;
    }

    public Long getNumHoras() {
	return numHoras;
    }

    public void setNumHoras(Long numHoras) {
	this.numHoras = numHoras;
    }

    public String getTipoCertificado() {
	return tipoCertificado;
    }

    public void setTipoCertificado(String tipoCertificado) {
	this.tipoCertificado = tipoCertificado;
    }

    public Long getValorInscripcion() {
	return valorInscripcion;
    }

    public void setValorInscripcion(Long valorInscripcion) {
	this.valorInscripcion = valorInscripcion;
    }

    public String getContactoNombre() {
	return contactoNombre;
    }

    public void setContactoNombre(String contactoNombre) {
	this.contactoNombre = contactoNombre;
    }

    public String getContactoEmail() {
	return contactoEmail;
    }

    public void setContactoEmail(String contactoEmail) {
	this.contactoEmail = contactoEmail;
    }

    public String getContactoTelefono() {
	return contactoTelefono;
    }

    public void setContactoTelefono(String contactoTelefono) {
	this.contactoTelefono = contactoTelefono;
    }

    public String getContactoExtension() {
	return contactoExtension;
    }

    public void setContactoExtension(String contactoExtension) {
	this.contactoExtension = contactoExtension;
    }

    public String getContactoWeb() {
	return contactoWeb;
    }

    public void setContactoWeb(String contactoWeb) {
	this.contactoWeb = contactoWeb;
    }

    public Long getParticipanOtrasFacultades() {
	return participanOtrasFacultades;
    }

    public void setParticipanOtrasFacultades(Long participanOtrasFacultades) {
	this.participanOtrasFacultades = participanOtrasFacultades;
    }

    public Long getParticipanOtrasSedes() {
	return participanOtrasSedes;
    }

    public void setParticipanOtrasSedes(Long participanOtrasSedes) {
	this.participanOtrasSedes = participanOtrasSedes;
    }

    public String getObservaciones() {
	return observaciones;
    }

    public void setObservaciones(String observaciones) {
	this.observaciones = observaciones;
    }

    public String getEstadoOferta() {
	return estadoOferta;
    }

    public void setEstadoOferta(String estadoOferta) {
	this.estadoOferta = estadoOferta;
    }

    public Long getRegistroCompleto() {
	return registroCompleto;
    }

    public void setRegistroCompleto(Long registroCompleto) {
	this.registroCompleto = registroCompleto;
    }

    public Date getFechaRegistro() {
	return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
	this.fechaRegistro = fechaRegistro;
    }

    public Long getPersona() {
	return persona;
    }

    public void setPersona(Long persona) {
	this.persona = persona;
    }

}
