/**
 * @author Martha Liliana Correa Ospina
 * Fecha: Marzo 22 de 2024
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;


public class PermisoBiodiversidad implements Serializable {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Atributos que hacen referencia a los campos de la tabla. */
    private Long id;
    private String nombrePermiso;
    private String tipoPermiso;
    private Date fechaInicio;
    private Date fechaFin;
    private String mensajeResolucion;
    private String mensajeInclusionPermiso;
    private String mensajeInfoAdicional;
    private String estado;
    
 // Tipos permiso
 	public static final String TIPO_PERMISO_MARCO = "PM";
 	public static final String TIPO_ACCESO_RECURSO = "AR";
 	
 // ESTADO
  	public static final String ACTIVO = "A";
  	public static final String INACTIVO = "I";
    

    /**
     * Constructo tradicional.
     */
    public PermisoBiodiversidad() {

    }

    /**
     * Constructor que recibe como parametro el id, para inicializar el id del
     * objeto.
     *
     * @param id
     *            the id
     */
    public PermisoBiodiversidad(Long id) {
        this.id = id;
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
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

	public String getNombrePermiso() {
		return nombrePermiso;
	}

	public void setNombrePermiso(String nombrePermiso) {
		this.nombrePermiso = nombrePermiso;
	}

	public String getMensajeInclusionPermiso() {
		return mensajeInclusionPermiso;
	}

	public void setMensajeInclusionPermiso(String mensajeInclusionPermiso) {
		this.mensajeInclusionPermiso = mensajeInclusionPermiso;
	}

	public String getMensajeInfoAdicional() {
		return mensajeInfoAdicional;
	}

	public void setMensajeInfoAdicional(String mensajeInfoAdicional) {
		this.mensajeInfoAdicional = mensajeInfoAdicional;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getMensajeResolucion() {
		return mensajeResolucion;
	}

	public void setMensajeResolucion(String mensajeResolucion) {
		this.mensajeResolucion = mensajeResolucion;
	}

	public String getTipoPermiso() {
		return tipoPermiso;
	}

	public void setTipoPermiso(String tipoPermiso) {
		this.tipoPermiso = tipoPermiso;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
