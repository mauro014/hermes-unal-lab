package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 ** @author dgbenitezc
 */

public class ConvocatoriaPublicacionLibrosDetalle {

	Long id;
	Long convocatoriaPublicacionLibros;
	String autor;
	String identificacion;
	String tipoDocumento;
	String universidad;
	String facultadDependencia;
	String email;
	String direccion;
	String telefono;
	Date fechaRegistro;

	public ConvocatoriaPublicacionLibrosDetalle() {
	}

	public boolean validar() {
		boolean valido = true;
		this.autor = this.autor.trim();
		if (this.autor.length() < 1 || this.autor.length() > 83) {
			valido = false;
		}
		this.identificacion = this.identificacion.trim();
		if (this.identificacion.length() < 1
				|| this.identificacion.length() > 15) {
			valido = false;
		}
		this.tipoDocumento = this.tipoDocumento.trim();
		if (this.tipoDocumento.length() < 1 || this.tipoDocumento.length() > 2) {
			valido = false;
		}
		this.universidad = this.universidad.trim();
		if (this.universidad.length() < 1 || this.universidad.length() > 100) {
			valido = false;
		}
		this.facultadDependencia = this.facultadDependencia.trim();
		if (this.facultadDependencia.length() < 1
				|| this.facultadDependencia.length() > 200) {
			valido = false;
		}
		this.email = this.email.trim();
		if (this.email.length() < 1 || this.email.length() > 60) {
			valido = false;
		}
		this.direccion = this.direccion.trim();
		if (this.direccion.length() < 1 || this.direccion.length() > 150) {
			valido = false;
		}
		this.telefono = this.telefono.trim();
		if (this.telefono.length() < 1 || this.telefono.length() > 36) {
			valido = false;
		}
		return valido;
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
	 * @return the convocatoriaPublicacionLibros
	 */
	public Long getConvocatoriaPublicacionLibros() {
		return convocatoriaPublicacionLibros;
	}

	/**
	 * @param convocatoriaPublicacionLibros
	 *            the convocatoriaPublicacionLibros to set
	 */
	public void setConvocatoriaPublicacionLibros(
			Long convocatoriaPublicacionLibros) {
		this.convocatoriaPublicacionLibros = convocatoriaPublicacionLibros;
	}

	/**
	 * @return the autor
	 */
	public String getAutor() {
		return autor;
	}

	/**
	 * @param autor
	 *            the autor to set
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}

	/**
	 * @return the identificacion
	 */
	public String getIdentificacion() {
		return identificacion;
	}

	/**
	 * @param identificacion
	 *            the identificacion to set
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	/**
	 * @return the universidad
	 */
	public String getUniversidad() {
		return universidad;
	}

	/**
	 * @param universidad
	 *            the universidad to set
	 */
	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}

	/**
	 * @return the facultadDependencia
	 */
	public String getFacultadDependencia() {
		return facultadDependencia;
	}

	/**
	 * @param facultadDependencia
	 *            the facultadDependencia to set
	 */
	public void setFacultadDependencia(String facultadDependencia) {
		this.facultadDependencia = facultadDependencia;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
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

}
