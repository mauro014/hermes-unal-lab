/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Clase    	: co.edu.unal.hermes.modelo.seguimiento.TipoViaSolicitud
Objetivo 	: Clase   POJO   para   la  tabla  HER_TIPO_VIA_SOLICITUD, en la cual
  			  se  almacenan  los  tipos  de  vias  por las que se pueden realizar 
  			  solicitudes para los proyectos de investigación.
Creación	: Septiembre 20 de 2007
Modificación:
Detalle		:
********************************************************************************/

package co.edu.unal.hermes.modelo.seguimiento;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class TipoViaSolicitud.
 */
public class TipoViaSolicitud implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant VIA_HERMES. */
    public static final Long VIA_HERMES = 1L;

    /** The id. */
    private Long id;

    /** The nombre. */
    private String nombre;

    /** The descripcion. */
    private String descripcion;

    /** The vigencia. */
    private String vigencia;

    /** The desde vigencia. */
    private Date desdeVigencia;

    /** The hasta vigencia. */
    private Date hastaVigencia;

    /**
     * Instantiates a new tipo via solicitud.
     */
    public TipoViaSolicitud() {
        /**
         * Empty constructor.
         */
    }

    /**
     * Instantiates a new tipo via solicitud.
     *
     * @param id
     *            the id
     */
    public TipoViaSolicitud(Long id) {
        super();
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

    /**
     * Gets the nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre.
     *
     * @param nombre
     *            the new nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * @param descripcion
     *            the new descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Gets the vigencia.
     *
     * @return the vigencia
     */
    public String getVigencia() {
        return vigencia;
    }

    /**
     * Sets the vigencia.
     *
     * @param vigencia
     *            the new vigencia
     */
    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    /**
     * Gets the desde vigencia.
     *
     * @return the desde vigencia
     */
    public Date getDesdeVigencia() {
        return desdeVigencia;
    }

    /**
     * Sets the desde vigencia.
     *
     * @param desdeVigencia
     *            the new desde vigencia
     */
    public void setDesdeVigencia(Date desdeVigencia) {
        this.desdeVigencia = desdeVigencia;
    }

    /**
     * Gets the hasta vigencia.
     *
     * @return the hasta vigencia
     */
    public Date getHastaVigencia() {
        return hastaVigencia;
    }

    /**
     * Sets the hasta vigencia.
     *
     * @param hastaVigencia
     *            the new hasta vigencia
     */
    public void setHastaVigencia(Date hastaVigencia) {
        this.hastaVigencia = hastaVigencia;
    }
}
