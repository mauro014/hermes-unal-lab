package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class SemilleroHistoricoCambios implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Semillero semillero;
	private String descripcion;
	private Date fecha;
	private String documentoResponsable;
	private String tipoDocResponsable;

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDocumentoResponsable() {
		return documentoResponsable;
	}

	public void setDocumentoResponsable(String documentoResponsable) {
		this.documentoResponsable = documentoResponsable;
	}

	public String getTipoDocResponsable() {
		return tipoDocResponsable;
	}

	public void setTipoDocResponsable(String tipoDocResponsable) {
		this.tipoDocResponsable = tipoDocResponsable;
	}

}