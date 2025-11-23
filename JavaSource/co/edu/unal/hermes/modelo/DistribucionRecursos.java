/*
 * Created on 05-sep-2005
 */
package co.edu.unal.hermes.modelo;

/**
 * @author Juan Pablo
 */

public class DistribucionRecursos {
    
    private Long cnp_id;
	private Long con_id;
	private String sed_id;
	private Long valor;
	private Long apoyo;
	
	
	public Long getApoyo() {
		return apoyo;
	}
	public void setApoyo(Long apoyo) {
		this.apoyo = apoyo;
	}
	public Long getCnp_id() {
		return cnp_id;
	}
	public void setCnp_id(Long cnp_id) {
		this.cnp_id = cnp_id;
	}
	public Long getCon_id() {
		return con_id;
	}
	public void setCon_id(Long con_id) {
		this.con_id = con_id;
	}
	public String getSed_id() {
		return sed_id;
	}
	public void setSed_id(String sed_id) {
		this.sed_id = sed_id;
	}
	public Long getValor() {
		return valor;
	}
	public void setValor(Long valor) {
		this.valor = valor;
	}
	
	
}
