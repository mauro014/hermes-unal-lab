/*
 * Created on 03-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Representa el estado actual del proyecto en la convocatoria Dentro de estos
 * están: Propuesto, rechazado, negado, aprobado, suspendido, activo, cancelado
 * y finalizado.
 */
public class EstadoProyecto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7130248075761154528L;

    /** The Constant ACTIVO. */
    public static final String ACTIVO = "A";

    /** The Constant APROBADO. */
    public static final String APROBADO = "AP";

    /** The Constant APROBADO. */
    public static final String APROBADO_NOMBRE = "APROBADO";

    /** The Constant BORRADO. */
    public static final String BORRADO = "B";

    /** The Constant CANCELADO. */
    public static final String CANCELADO = "CN";

    /** The Constant ELEGIBLE. */
    public static final String ELEGIBLE = "E";

    /** The Constant ELEGIBLE. */
    public static final String ELEGIBLE_NOMBRE = "ELEGIBLE";
    
    public static final String BANCO_FINANCIABLE_NOMBRE = "BANCO FINANCIABLE";

    /** The Constant FINALIZADO. */
    public static final String FINALIZADO = "F";

    /** The Constant HISTORICO. */
    public static final String HISTORICO = "H";

    /** The Constant INGRESANDO. */
    public static final String INGRESANDO = "I";

    /** The Constant NEGADO. */
    public static final String NEGADO = "N";

    /** The Constant PROPUESTO. */
    public static final String PROPUESTO = "P";

    /** The Constant PROPUESTO. */
    public static final String PROPUESTO_NOMBRE = "PROPUESTO";

    /** The Constant RECHAZADO. */
    public static final String RECHAZADO = "R";

    /** The Constant SUSPENDIDO. */
    public static final String SUSPENDIDO = "S";

    /** The Constant BANCOPROYECTO. */
    public static final String BANCOPROYECTO = "BP";

    /** The Constant BANCOPROYECTO. */
    public static final String VERSION_INICIAL = "BS";

    /** The Constant NO_APROBADO. */
    public static final String NO_APROBADO = "N";
    /** The Constant NO_APROBADO. */
    public static final String NO_APROBADO_NOMBRE = "NO APROBADO";

    /** The Constant PUBLICADO. */
    public static final String PUBLICADO = "PB";

    /** The Constant BASE. */
    public static final String BASE = "BS";

    /** The Constant APROBADO_OCAD. */
    public static final String APROBADO_OCAD = "OCAD";

    /** The Constant APROBADO_OCAD. */
    public static final String APROBADO_OCAD_NOMBRE = "APROBADO POR OCAD";

    /** The Constant POR_FINALIZAR. */
    public static final String POR_FINALIZAR = "PF";

    /** The Constant APROBADO_VICERRECTORIA. */
    // Convocatoría Artículos:
    public static final String APROBADO_VICERRECTORIA = "APV";

    /** The Constant NO_APROBADO_VICERRECTORIA. */
    public static final String NO_APROBADO_VICERRECTORIA = "NAPV";

    /** The Constant INGRESANDO_SOLICITUD_LABORATORIO. */
    // Solicitud Laboratorio:
    public static final String INGRESANDO_SOLICITUD_LABORATORIO = "ISL";
    
    public static final String AVAL_APROBADO = "PAA";
    
    public static final String EN_LEGALIZACION = "EL";
    
    public static final String BANCO_FINANCIABLE = "BF";
    
    //Estados proyecto editorial
    
    public static final String PUBLICABLE = "PU";
    public static final String NO_PUBLICABLE = "NPU";
    public static final String EN_PRODUCCION = "PRO";
    public static final String EN_SOL_ISBN = "SISBN";
    public static final String EN_SOL_SELLO_ED = "SSED";
    public static final String EN_SOL_FICHA_CARTO = "SFC";
    public static final String EN_CONTROL_CALIDAD = "CCU";
    public static final String EN_DISTRIBUCION_COMERCIAL = "DC";
    public static final String EN_DISTRIBUCION_INSTITUCIONAL = "DI";

    /** The id. */
    private String id;

    /** The nombre. */
    private String nombre;

    /** The nombre. */
    private Long numero;
    
    public EstadoProyecto() {
	    super();
    }

	public EstadoProyecto(String id) {
	    super();
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
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the numero.
     *
     * @return the numero
     */
    public Long getNumero() {
        return numero;
    }

    /**
     * Sets the numero.
     *
     * @param numero            the numero to set
     */
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    /**
     * Checks if is estado habilitado legalizacion.
     *
     * @return true, if is estado habilitado legalizacion
     */
    public boolean isEstadoHabilitadoLegalizacion() {
        return this.getId().equals(EstadoProyecto.ELEGIBLE) || this.getId().equals(EstadoProyecto.APROBADO_OCAD)
                || this.getId().equals(EstadoProyecto.PROPUESTO) || this.getId().equals(EstadoProyecto.APROBADO)
                || this.getId().equals(EstadoProyecto.NO_APROBADO);
    }

    /**
     * Checks if is estado habilitado legalizacion.
     *
     * @return true, if is estado habilitado legalizacion
     */
    public boolean isEstadoHabilitadoFormalizacion() {
        return this.getId().equals(EstadoProyecto.APROBADO);
    }

}
