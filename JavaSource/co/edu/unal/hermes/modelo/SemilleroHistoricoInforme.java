package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SemilleroHistoricoInforme implements Serializable, Comparable<SemilleroHistoricoInforme> {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private SemilleroInforme informe;
	private Date fechaCompromiso;
	private Persona responsable;
	private Date fechaAjuste;
	private SimpleDateFormat format;
	private SemilleroSolicitudRespuesta estado;
	private String comentarios;

	public SemilleroHistoricoInforme() {
		format = new SimpleDateFormat("yyyy-MM-dd");
		setFechaCompromiso(null);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SemilleroInforme getInforme() {
		return informe;
	}

	public void setInforme(SemilleroInforme informe) {
		this.informe = informe;
	}

	public Date getFechaCompromiso() {
		return fechaCompromiso;
	}

	public void setFechaCompromiso(Date fechaCompromiso) {
		this.fechaCompromiso = fechaCompromiso;
	}

	public String getCompromiso() {
		return (getFechaCompromiso() != null) ? format.format(getFechaCompromiso()) : "";
	}

	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	public Date getFechaAjuste() {
		return fechaAjuste;
	}

	public void setFechaAjuste(Date fechaAjuste) {
		this.fechaAjuste = fechaAjuste;
	}

	@Override
	public int compareTo(SemilleroHistoricoInforme o) {
		return getId() - o.getId();
	}

	public SemilleroSolicitudRespuesta getEstado() {
		return estado;
	}

	public void setEstado(SemilleroSolicitudRespuesta estado) {
		this.estado = estado;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

}