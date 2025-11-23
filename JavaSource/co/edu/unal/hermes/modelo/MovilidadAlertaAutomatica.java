/**
 * 
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;

public class MovilidadAlertaAutomatica {

    private Long id;
    private Date fechafinal;
    private String documentoDocente;
    private String nombreDocente;
    private String correoDocente;
    private String documentoEstudiante;
    private String nombreEstudiante;
    private String correoEstudiante;
    private Integer numeroMesesDesdeFinMovilidad;
    private String tipoMovilidadTabla;

    public MovilidadAlertaAutomatica() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechafinal() {
		return fechafinal;
	}

	public void setFechafinal(Date fechafinal) {
		this.fechafinal = fechafinal;
	}

	public String getDocumentoDocente() {
		return documentoDocente;
	}

	public void setDocumentoDocente(String documentoDocente) {
		this.documentoDocente = documentoDocente;
	}

	public String getNombreDocente() {
		return nombreDocente;
	}

	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}

	public String getCorreoDocente() {
		return correoDocente;
	}

	public void setCorreoDocente(String correoDocente) {
		this.correoDocente = correoDocente;
	}

	public String getDocumentoEstudiante() {
		return documentoEstudiante;
	}

	public void setDocumentoEstudiante(String documentoEstudiante) {
		this.documentoEstudiante = documentoEstudiante;
	}

	public String getNombreEstudiante() {
		return nombreEstudiante;
	}

	public void setNombreEstudiante(String nombreEstudiante) {
		this.nombreEstudiante = nombreEstudiante;
	}

	public String getCorreoEstudiante() {
		return correoEstudiante;
	}

	public void setCorreoEstudiante(String correoEstudiante) {
		this.correoEstudiante = correoEstudiante;
	}

	public Integer getNumeroMesesDesdeFinMovilidad() {
		return numeroMesesDesdeFinMovilidad;
	}

	public void setNumeroMesesDesdeFinMovilidad(Integer numeroMesesDesdeFinMovilidad) {
		this.numeroMesesDesdeFinMovilidad = numeroMesesDesdeFinMovilidad;
	}

	public String getTipoMovilidadTabla() {
		return tipoMovilidadTabla;
	}

	public void setTipoMovilidadTabla(String tipoMovilidadTabla) {
		this.tipoMovilidadTabla = tipoMovilidadTabla;
	}
}
