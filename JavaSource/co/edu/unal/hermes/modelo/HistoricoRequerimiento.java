package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * The Class HistoricoRequerimiento.
 */
public class HistoricoRequerimiento {

	private Long id;
	private Date fechaCambio;
	private Requerimiento requerimiento;
	private String comentariosUsuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public String getComentariosUsuario() {
		return comentariosUsuario;
	}

	public void setComentariosUsuario(String comentariosUsuario) {
		this.comentariosUsuario = comentariosUsuario;
	}
}
