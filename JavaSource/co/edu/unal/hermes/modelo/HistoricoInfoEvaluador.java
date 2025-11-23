package co.edu.unal.hermes.modelo;

import java.util.Date;

public class HistoricoInfoEvaluador {
	
	private Long id;
	private Investigador evaluador;

	private String nombres;
	private String apellidos;
	private String genero;	
	private String formacion;
    private String tipo;
	private String institucionLabora;
	private String areaCiencia;
	private String subAreaCiencia;
	private String cvlac;
	private String minciencias;
    private String email;
    private String lineaInvestigacion;
    private Persona responsable;
    private Date fechaCambio;
    private String observacion;
    
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getFormacion() {
		return formacion;
	}
	public void setFormacion(String formacion) {
		this.formacion = formacion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getInstitucionLabora() {
		return institucionLabora;
	}
	public void setInstitucionLabora(String institucionLabora) {
		this.institucionLabora = institucionLabora;
	}
	public String getAreaCiencia() {
		return areaCiencia;
	}
	public void setAreaCiencia(String areaCiencia) {
		this.areaCiencia = areaCiencia;
	}
	public String getSubAreaCiencia() {
		return subAreaCiencia;
	}
	public void setSubAreaCiencia(String subAreaCiencia) {
		this.subAreaCiencia = subAreaCiencia;
	}
	public String getCvlac() {
		return cvlac;
	}
	public void setCvlac(String cvlac) {
		this.cvlac = cvlac;
	}
	public String getMinciencias() {
		return minciencias;
	}
	public void setMinciencias(String minciencias) {
		this.minciencias = minciencias;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLineaInvestigacion() {
		return lineaInvestigacion;
	}
	public void setLineaInvestigacion(String lineaInvestigacion) {
		this.lineaInvestigacion = lineaInvestigacion;
	}
	public Persona getResponsable() {
		return responsable;
	}
	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}
	public Date getFechaCambio() {
		return fechaCambio;
	}
	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Investigador getEvaluador() {
		return evaluador;
	}
	public void setEvaluador(Investigador evaluador) {
		this.evaluador = evaluador;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

    
}
