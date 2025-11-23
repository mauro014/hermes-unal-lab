/*
 * Created on 9-dic-2007
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author gacantorm
 */
public class ConvocatoriaArticulo implements Serializable {

	private static final long serialVersionUID = -3227604269842926287L;

	// INFORMACIÓN GENERAL
	private Long id;
	private String modalidad;
	private String convocatoria;
	private String idioma;
	private Date fechaRegistro;
	private Persona personaInv;
	private String dependencia;
	private String numeroApoyos;
	private String telefonoDocente;
	private String emailDocente;
	private String celularDocente;
	private String titulo;
	private String palabras;
	private String coautores;
	private String resumen;
	private String issn;
	private String estado;
	private String requerimiento1 = "NO";
	private String requerimiento2 = "NO";
	private String requerimiento3 = "NO";
	private String area;
	private String comentarioEvaluacion;
	private Date fechaEstimadaVri;
	private String comentarioVri;
	private Date fechaEnvio;

	// ARCHIVOS
	private Set archivos = new HashSet();

	public ConvocatoriaArticulo() {

	}

	public ConvocatoriaArticulo(Long pId) {
		this.id = pId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Persona getPersonaInv() {
		return personaInv;
	}

	public void setPersonaInv(Persona personaInv) {
		this.personaInv = personaInv;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public String getNumeroApoyos() {
		return numeroApoyos;
	}

	public void setNumeroApoyos(String numeroApoyos) {
		this.numeroApoyos = numeroApoyos;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getPalabras() {
		return palabras;
	}

	public void setPalabras(String palabras) {
		this.palabras = palabras;
	}

	public String getCoautores() {
		return coautores;
	}

	public void setCoautores(String coautores) {
		this.coautores = coautores;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getEstado() {
		return estado;
	}

	public String getEstadoNombre() {
		String nombreEstado = "";
		if (estado.equals(EstadoProyecto.APROBADO_VICERRECTORIA)) {
			nombreEstado = "Aprobado Vicerrectoría";
		}
		if (estado.equals(EstadoProyecto.NO_APROBADO_VICERRECTORIA)) {
			nombreEstado = "No Aprobado Vicerrectoría";
		}
		if (estado.equals(EstadoProyecto.NO_APROBADO)) {
			nombreEstado = "No aprobado";
		}
		if (estado.equals(EstadoProyecto.APROBADO)) {
			nombreEstado = "Aprobado";
		}
		if (estado.equals(EstadoProyecto.PROPUESTO)) {
			nombreEstado = "Propuesto";
		}
		return nombreEstado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Set getArchivos() {
		return archivos;
	}

	public void setArchivos(Set archivos) {
		this.archivos = archivos;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getTelefonoDocente() {
		return telefonoDocente;
	}

	public void setTelefonoDocente(String telefonoDocente) {
		this.telefonoDocente = telefonoDocente;
	}

	public String getEmailDocente() {
		return emailDocente;
	}

	public void setEmailDocente(String emailDocente) {
		this.emailDocente = emailDocente;
	}

	public String getCelularDocente() {
		return celularDocente;
	}

	public void setCelularDocente(String celularDocente) {
		this.celularDocente = celularDocente;
	}

	public void setRequerimiento1(String requerimiento1) {
		this.requerimiento1 = requerimiento1;
	}

	public String getRequerimiento1() {
		return requerimiento1;
	}

	public void setRequerimiento2(String requerimiento2) {
		this.requerimiento2 = requerimiento2;
	}

	public String getRequerimiento2() {
		return requerimiento2;
	}

	public void setRequerimiento3(String requerimiento3) {
		this.requerimiento3 = requerimiento3;
	}

	public String getRequerimiento3() {
		return requerimiento3;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea() {
		return area;
	}

	public void setComentarioEvaluacion(String comentarioEvaluacion) {
		this.comentarioEvaluacion = comentarioEvaluacion;
	}

	public String getComentarioEvaluacion() {
		return comentarioEvaluacion;
	}

	public Date getFechaEstimadaVri() {
		return fechaEstimadaVri;
	}

	public void setFechaEstimadaVri(Date fechaEstimadaVri) {
		this.fechaEstimadaVri = fechaEstimadaVri;
	}

	public String getComentarioVri() {
		return comentarioVri;
	}

	public void setComentarioVri(String comentarioVri) {
		this.comentarioVri = comentarioVri;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

}
