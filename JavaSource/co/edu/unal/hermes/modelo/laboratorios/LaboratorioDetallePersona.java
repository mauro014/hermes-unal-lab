/**
 * @author dgbenitezc
 */

package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

public class LaboratorioDetallePersona {

	private Long id;
	private Long idDetalle;
	private String nombrePersona;
	private String tipoDocumento;
	private String documento;
	private String cargo;
	private Date fechaRegistro;

	public LaboratorioDetallePersona() {
	}

	@Override
	public boolean equals(Object otroObjeto) {
		boolean igual = false;
		if (otroObjeto != null
				&& otroObjeto instanceof LaboratorioDetallePersona) {
			LaboratorioDetallePersona otroDetalle = (LaboratorioDetallePersona) otroObjeto;

			if ((otroDetalle.idDetalle.equals(this.idDetalle))
					&& (otroDetalle.nombrePersona.equals(this.nombrePersona))) {
				igual = true;
			}
		}
		System.out.println("LaboratorioDetallePersona equals: " + igual);
		return igual;
	}

	@Override
	public String toString() {
		String cadena = "";
		if (documento != null && documento != "") {
			cadena += documento + ", ";
		}
		if (nombrePersona != null && nombrePersona != "") {
			cadena += nombrePersona + ", ";
		}
		if (cargo != null && cargo != "") {
			cadena += cargo;
		}

		cadena = cadena.trim();
		if (cadena.endsWith(",")) {
			cadena = cadena.substring(0, cadena.length() - 1);
		}

		System.out.println("LaboratorioDetallePersona toString: " + cadena);
		return cadena;
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
	 * @return the idDetalle
	 */
	public Long getIdDetalle() {
		return idDetalle;
	}

	/**
	 * @param idDetalle
	 *            the idDetalle to set
	 */
	public void setIdDetalle(Long idDetalle) {
		this.idDetalle = idDetalle;
	}

	/**
	 * @return the nombrePersona
	 */
	public String getNombrePersona() {
		return nombrePersona;
	}

	/**
	 * @param nombrePersona
	 *            the nombrePersona to set
	 */
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
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
	 * @return the cargo
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * @param cargo
	 *            the cargo to set
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}
