package co.edu.unal.hermes.modelo;

import java.util.Date;

public class LogAlertasAutomaticas {

	private Long id;
	private String idObjeto;
	private String tipoObjeto;
	private String valorObjeto;
	private Date fecha;
	private String destinatarios;
	private String cuerpoCorreo;
		
	public LogAlertasAutomaticas() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdObjeto() {
		return idObjeto;
	}

	public void setIdObjeto(String idObjeto) {
		this.idObjeto = idObjeto;
	}

	public String getTipoObjeto() {
		return tipoObjeto;
	}

	public void setTipoObjeto(String tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}

	public String getCuerpoCorreo() {
		return cuerpoCorreo;
	}

	public void setCuerpoCorreo(String cuerpoCorreo) {
		this.cuerpoCorreo = cuerpoCorreo;
	}

	public String getValorObjeto() {
		return valorObjeto;
	}

	public void setValorObjeto(String valorObjeto) {
		this.valorObjeto = valorObjeto;
	}
}