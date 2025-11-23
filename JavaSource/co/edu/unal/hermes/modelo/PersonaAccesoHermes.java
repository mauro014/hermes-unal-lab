package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * @author dgbenitezc 20141210
 */
public class PersonaAccesoHermes {

	private Long id;
	private String ip;
	private Date fecha;
	private String tipoDocumento;
	private String documento;
	private Boolean usoLdap;

	public PersonaAccesoHermes() {
		fecha = new Date();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento
	 *            the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * @return the documento
	 */
	public String getDocumento() {
		return documento;
	}

	/**
	 * @param documento
	 *            the documento to set
	 */
	public void setDocumento(String documento) {
		this.documento = documento;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the usoLdap
	 */
	public Boolean getUsoLdap() {
		return usoLdap;
	}

	/**
	 * @param usoLdap
	 *            the usoLdap to set
	 */
	public void setUsoLdap(Boolean usoLdap) {
		this.usoLdap = usoLdap;
	}

}
