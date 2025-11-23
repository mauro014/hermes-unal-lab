package co.edu.unal.hermes.vista.utils;

import java.io.Serializable;

public class VistaBD implements Serializable{
	
	private String nombre;
	private Long longitud;
	private String texto;
	private Long longitudTipoTexto;
	private String tipoTexto;
	private Long longitudTextoOid;
	private String duenoVista;
	private String tipoVista;
	private String nombreSuperVista;
	private String textoOid;

	

	public String getTextoOid() {
		return textoOid;
	}

	public void setTextoOid(String textoOid) {
		this.textoOid = textoOid;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getLongitud() {
		return longitud;
	}

	public void setLongitud(Long longitud) {
		this.longitud = longitud;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Long getLongitudTipoTexto() {
		return longitudTipoTexto;
	}

	public void setLongitudTipoTexto(Long longitudTipoTexto) {
		this.longitudTipoTexto = longitudTipoTexto;
	}

	public String getTipoTexto() {
		return tipoTexto;
	}

	public void setTipoTexto(String tipoTexto) {
		this.tipoTexto = tipoTexto;
	}

	public Long getLongitudTextoOid() {
		return longitudTextoOid;
	}

	public void setLongitudTextoOid(Long longitudTextoOid) {
		this.longitudTextoOid = longitudTextoOid;
	}

	public String getDuenoVista() {
		return duenoVista;
	}

	public void setDuenoVista(String duenoVista) {
		this.duenoVista = duenoVista;
	}

	public String getTipoVista() {
		return tipoVista;
	}

	public void setTipoVista(String tipoVista) {
		this.tipoVista = tipoVista;
	}

	public String getNombreSuperVista() {
		return nombreSuperVista;
	}

	public void setNombreSuperVista(String nombreSuperVista) {
		this.nombreSuperVista = nombreSuperVista;
	}

}
