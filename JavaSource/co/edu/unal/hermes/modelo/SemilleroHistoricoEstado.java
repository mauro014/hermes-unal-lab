package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class SemilleroHistoricoEstado implements Serializable, Comparable<SemilleroHistoricoEstado> {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Semillero semillero;
	private SemilleroEstado estado;
	private String motivo;
	private Date fecha;
	private Investigador responsable;

	public SemilleroHistoricoEstado() {
		setFecha(null);
	}

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

	public SemilleroEstado getEstado() {
		return estado;
	}

	public void setEstado(SemilleroEstado estado) {
		this.estado = estado;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Investigador getResponsable() {
		return responsable;
	}

	public void setResponsable(Investigador responsable) {
		this.responsable = responsable;
	}

	@Override
	public int compareTo(SemilleroHistoricoEstado o) {
		return getId() - o.getId();
	}

}