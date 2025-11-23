/*
 * Created on 27-marzo-2024
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package co.edu.unal.hermes.modelo.seguimiento;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;

import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.TipoArchivo;

/**
 * The Class SolicitudDocumento.
 */
public class SolicitudCiudad implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8812185936075363837L;

	/** The fecha generacion. */
	private Date fecha;
	
	/** The id. */
	private Long id;

	/** The solicitud. */
	private Solicitud solicitud;
	
	/** The tipo. */
	private Ciudad ciudad;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}


}
