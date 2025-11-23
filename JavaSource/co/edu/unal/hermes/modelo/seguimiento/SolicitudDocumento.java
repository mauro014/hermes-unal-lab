/*
 * Created on 27-oct-2006
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

import co.edu.unal.hermes.modelo.TipoArchivo;

/**
 * The Class SolicitudDocumento.
 */
public class SolicitudDocumento implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6696498757744562290L;

	/** The archivo. */
	Blob archivo;
	
	/** The fecha generacion. */
	private Date fechaGeneracion;
	
	/** The id. */
	Long id;

	/** The index. */
	private int index;
	
	/** The nombre. */
	private String nombre;
	
	/** The observaciones. */
	private String observaciones;
	
	/** The solicitud. */
	private Solicitud solicitud;
	
	/** The tipo. */
	private TipoArchivo tipo;

	/**
	 * Gets the archivo.
	 *
	 * @return the archivo
	 */
	public Blob getArchivo() {
		return archivo;
	}

	/**
	 * Gets the bytes archivo carta.
	 *
	 * @return the bytes archivo carta
	 */
	public byte[] getBytesArchivoCarta() {
		byte[] resultado = null;
		try {
			resultado = archivo.getBytes(1, (int) archivo.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	/**
	 * Gets the fecha generacion.
	 *
	 * @return the fecha generacion
	 */
	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Gets the observaciones.
	 *
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * Gets the solicitud.
	 *
	 * @return the solicitud
	 */
	public Solicitud getSolicitud() {
		return solicitud;
	}

	/**
	 * Gets the tipo.
	 *
	 * @return the tipo
	 */
	public TipoArchivo getTipo() {
		return tipo;
	}

	/**
	 * Sets the archivo.
	 *
	 * @param archivo the new archivo
	 */
	public void setArchivo(Blob archivo) {
		this.archivo = archivo;
	}

	/**
	 * Sets the bytes archivo carta.
	 *
	 * @param bytes the new bytes archivo carta
	 */
	public void setBytesArchivoCarta(byte[] bytes) {
		archivo = Hibernate.createBlob(bytes);
	}

	/**
	 * Sets the fecha generacion.
	 *
	 * @param fechaGeneracion the new fecha generacion
	 */
	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Sets the index.
	 *
	 * @param index the new index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Sets the observaciones.
	 *
	 * @param observaciones the new observaciones
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * Sets the solicitud.
	 *
	 * @param solicitud the new solicitud
	 */
	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	/**
	 * Sets the tipo.
	 *
	 * @param tipo the new tipo
	 */
	public void setTipo(TipoArchivo tipo) {
		this.tipo = tipo;
	}

}
