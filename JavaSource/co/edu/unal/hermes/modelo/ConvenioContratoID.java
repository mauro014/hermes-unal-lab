package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class ConvenioContratoID implements Serializable{
	
	private int idConvenio;
	private int idContrato;
	
	public int getIdConvenio() {
		return idConvenio;
	}
	public void setIdConvenio(int idConvenio) {
		this.idConvenio = idConvenio;
	}
	public int getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(int idContrato) {
		this.idContrato = idContrato;
	}
	

}
