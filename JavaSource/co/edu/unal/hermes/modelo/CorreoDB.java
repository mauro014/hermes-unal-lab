package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class CorreoDB implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String asunto;
	private String cco;
	private String de;
	private Integer enviado;
	private Date fechaUltimoIntento;
	private Integer intentosEnvio;
	private String mensaje;
	private String para;
	private String ultimoError;

	public CorreoDB() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getCco() {
		return cco;
	}

	public void setCco(String cco) {
		this.cco = cco;
	}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public Integer getEnviado() {
		return enviado;
	}

	public void setEnviado(Integer enviado) {
		this.enviado = enviado;
	}

	public Date getFechaUltimoIntento() {
		return fechaUltimoIntento;
	}

	public void setFechaUltimoIntento(Date fechaUltimoIntento) {
		this.fechaUltimoIntento = fechaUltimoIntento;
	}

	public Integer getIntentosEnvio() {
		return intentosEnvio;
	}

	public void setIntentosEnvio(Integer intentosEnvio) {
		this.intentosEnvio = intentosEnvio;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getUltimoError() {
		return ultimoError;
	}

	public void setUltimoError(String ultimoError) {
		this.ultimoError = ultimoError;
	}
}