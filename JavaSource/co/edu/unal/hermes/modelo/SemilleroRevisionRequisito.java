package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroRevisionRequisito implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private SemilleroSolicitud solicitudSemillero;
	private SemilleroRequisito requisito;
	private String cumple;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SemilleroSolicitud getSolicitudSemillero() {
		return solicitudSemillero;
	}

	public void setSolicitudSemillero(SemilleroSolicitud solicitudSemillero) {
		this.solicitudSemillero = solicitudSemillero;
	}

	public SemilleroRequisito getRequisito() {
		return requisito;
	}

	public void setRequisito(SemilleroRequisito requisito) {
		this.requisito = requisito;
	}

	public String getCumple() {
		return cumple == null ? "N" : cumple;
	}

	public void setCumple(String cumple) {
		this.cumple = cumple;
	}
}