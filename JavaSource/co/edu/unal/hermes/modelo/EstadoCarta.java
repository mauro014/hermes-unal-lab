/*
 * @author Wilver Alexander Martínez Martínez, wam²
 * Fecha: Agosto 15 de 2007
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Ç Bean que mapea la tabla "HER_ESTADO_CARTA" de la base de datos.
 *
 * @author Wilver Alexander Martínez Martínez, wam²
 * @version 1.0
 */
public class EstadoCarta implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2203977756822248233L;
    
    /** The Constant PENDIENTE_APROBACION. */
    public static final long PENDIENTE_APROBACION = 1;
    
    /** The Constant SOMETIDO. */
    public static final long SOMETIDO = 2;
    
    /** The Constant FINALIZADO. */
    public static final long FINALIZADO = 3;
    
    /** The Constant BORRADO. */
    public static final String BORRADO = "B";
    public static final String SIN_ENVIAR = "SE";

    /** Atributos que hacen referencia a los campos de la tabla. */
    private String id; // ETC_ID
    
    /** The nombre estado. */
    private String nombreEstado; // ETC_NOMBRE

    /**
     * Constructo tradicional.
     */
    public EstadoCarta() {
    }

    /**
     * Instantiates a new estado carta.
     *
     * @param id the id
     */
    public EstadoCarta(String id) {
        this.id = id;
    }

    /**
     * Constructor que recibe como parametro el id, para inicializar el id del
     * objeto.
     *
     * @return the nombre estado
     */

    public String getNombreEstado() {
        return nombreEstado;
    }

    /**
     * Sets the nombre estado.
     *
     * @param nombreEstado the new nombre estado
     */
    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
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
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }

}
