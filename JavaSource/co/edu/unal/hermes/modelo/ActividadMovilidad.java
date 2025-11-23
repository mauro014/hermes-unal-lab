/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Específica la actividad asociada a la movilidad de una visita.
 * 
 */
public class ActividadMovilidad implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4516489229111658947L;
	
	/** The id. */
	private Long id;    
    
    /** The descripcion. */
    private String descripcion;
    
    /** The duracion. */
    private Integer duracion;
    
    /** The fecha. */
    private Date fecha;
    
    /** The movilidad. */
    private String movilidad; 
    
    /** The tipo actividad. */
    private String tipoActividad;    
    
    /** The error descripcion. */
    private String errorDescripcion;
    
    /** The error fecha. */
    private String errorFecha;
    
    /** The error duracion. */
    private String errorDuracion;
    
    /** The b error descripcion. */
    private boolean bErrorDescripcion = false;
    
    /** The b error fecha. */
    private boolean bErrorFecha = false;
    
    /** The b error duracion. */
    private boolean bErrorDuracion = false;
    
    

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	 * Gets the descripcion.
	 *
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Sets the descripcion.
	 *
	 * @param descripcion the new descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Gets the duracion.
	 *
	 * @return the duracion
	 */
	public Integer getDuracion() {
		return duracion;
	}

	/**
	 * Sets the duracion.
	 *
	 * @param duracion the new duracion
	 */
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	/**
	 * Gets the fecha.
	 *
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Sets the fecha.
	 *
	 * @param fecha the new fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Gets the movilidad.
	 *
	 * @return the movilidad
	 */
	public String getMovilidad() {
		return movilidad;
	}

	/**
	 * Sets the movilidad.
	 *
	 * @param movilidad the new movilidad
	 */
	public void setMovilidad(String movilidad) {
		this.movilidad = movilidad;
	}

	/**
	 * Gets the tipo actividad.
	 *
	 * @return the tipo actividad
	 */
	public String getTipoActividad() {
		return tipoActividad;
	}

	/**
	 * Sets the tipo actividad.
	 *
	 * @param tipoActividad the new tipo actividad
	 */
	public void setTipoActividad(String tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	/**
	 * Gets the error descripcion.
	 *
	 * @return the error descripcion
	 */
	public String getErrorDescripcion() {
		return errorDescripcion;
	}

	/**
	 * Sets the error descripcion.
	 *
	 * @param errorDescripcion the new error descripcion
	 */
	public void setErrorDescripcion(String errorDescripcion) {
		this.errorDescripcion = errorDescripcion;
	}

	/**
	 * Gets the error fecha.
	 *
	 * @return the error fecha
	 */
	public String getErrorFecha() {
		return errorFecha;
	}

	/**
	 * Sets the error fecha.
	 *
	 * @param errorFecha the new error fecha
	 */
	public void setErrorFecha(String errorFecha) {
		this.errorFecha = errorFecha;
	}

	/**
	 * Gets the error duracion.
	 *
	 * @return the error duracion
	 */
	public String getErrorDuracion() {
		return errorDuracion;
	}

	/**
	 * Sets the error duracion.
	 *
	 * @param errorDuracion the new error duracion
	 */
	public void setErrorDuracion(String errorDuracion) {
		this.errorDuracion = errorDuracion;
	}

	/**
	 * Checks if is b error descripcion.
	 *
	 * @return true, if is b error descripcion
	 */
	public boolean isbErrorDescripcion() {
		return bErrorDescripcion;
	}

	/**
	 * Sets the b error descripcion.
	 *
	 * @param bErrorDescripcion the new b error descripcion
	 */
	public void setbErrorDescripcion(boolean bErrorDescripcion) {
		this.bErrorDescripcion = bErrorDescripcion;
	}

	/**
	 * Checks if is b error fecha.
	 *
	 * @return true, if is b error fecha
	 */
	public boolean isbErrorFecha() {
		return bErrorFecha;
	}

	/**
	 * Sets the b error fecha.
	 *
	 * @param bErrorFecha the new b error fecha
	 */
	public void setbErrorFecha(boolean bErrorFecha) {
		this.bErrorFecha = bErrorFecha;
	}

	/**
	 * Checks if is b error duracion.
	 *
	 * @return true, if is b error duracion
	 */
	public boolean isbErrorDuracion() {
		return bErrorDuracion;
	}

	/**
	 * Sets the b error duracion.
	 *
	 * @param bErrorDuracion the new b error duracion
	 */
	public void setbErrorDuracion(boolean bErrorDuracion) {
		this.bErrorDuracion = bErrorDuracion;
	}
	
}
