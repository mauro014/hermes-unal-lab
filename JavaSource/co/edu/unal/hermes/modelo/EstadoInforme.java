/*
 * @author Wilver Alexander Martínez Martínez, wam²
 * Fecha: Agosto 15 de 2007
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Ç Bean que mapea la tabla "HER_ESTADO_INFORME" de la base de datos.
 *
 * @author Wilver Alexander Martínez Martínez, wam²
 * @version 1.0
 */
public class EstadoInforme implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8928009786473956728L;

    /** The Constant INGRESANDO. */
    public static final Long INGRESANDO = 1L;

    /** The Constant ENVIADO. */
    public static final Long ENVIADO = 2L;

    /** The Constant ACEPTADO_DIRECCION. */
    public static final Long ACEPTADO_DIRECCION = 3L;

    /** The Constant ACEPTADO_FACULTAD. */
    public static final Long ACEPTADO_FACULTAD = 4L;

    /** The Constant DEVUELTO. */
    public static final Long DEVUELTO = 5L;

    /** The Constant DEVUELTO. */
    public static final Long BORRADO = 0L;

    /** The Constant PAGO. */
    public static final Long PAGO = 6L;

    /** The Constant ACEPTADO_UAB. */
    public static final Long ACEPTADO_UAB = 7L;
    
    /** The Constant REVISION_ENTIDAD_EXTERNA. */
    public static final Long REVISION_ENTIDAD_EXTERNA = 8L;
    
    /** The Constant REVISION_ENTIDAD_EXTERNA. */
    public static final Long ESTUDIO_ANLA = 9L;
    
    /** The Constant REVISION_ENTIDAD_EXTERNA. */
    public static final Long ACEPTADO_ANLA = 10L;

    /** Atributos que hacen referencia a los campos de la tabla. */
    private Long id;

    /** The nombre estado. */
    private String nombreEstado;

    /**
     * Constructo tradicional.
     */
    public EstadoInforme() {

    }

    /**
     * Constructor que recibe como parametro el id, para inicializar el id del
     * objeto.
     *
     * @param id
     *            the id
     */
    public EstadoInforme(Long id) {
        this.id = id;
    }

    /**
     * Gets the nombre estado.
     *
     * @return the nombre estado
     */
    public String getNombreEstado() {
        return nombreEstado;
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
     * Sets the nombre estado.
     *
     * @param nombreEstado
     *            the new nombre estado
     */
    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

}
