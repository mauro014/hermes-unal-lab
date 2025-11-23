/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * Específica la actividad asociada al proyecto.
 * 
 */
public class Parametro {

    /** The Constant HAB_CART_DPN_COOR. */
    public static final String HAB_CART_DPN_COOR = "HAB_CART_DPN_COORDINADOR";

    /** The Constant SEG_FIRMA. */
    public static final String SEG_FIRMA = "SEG_FIRMA";

    /** The Constant PAR_TIPOLOGIA_PROYECTO. */
    public static final String PAR_TIPOLOGIA_PROYECTO = "PAR_TIPOLOGIA_PROYECTO";
    
    /** The Constant TIPO_VINCULACION_CONVOCATORIA. */
    public static final String TIPO_VINCULACION_CONVOCATORIA = "TIPO_VINCULACION_CONVOCATORIA";

    /** The Constant PAR_TIPO_PROYECTO_LAB. */
    public static final String PAR_TIPO_PROYECTO_LAB = "PAR_TIPO_PROYECTO_LAB";
    
    /** The Constant PAR_TIPO_PROYECTO_LAB. */
    public static final String PAR_SUBTIPO_FINANCIACION = "PAR_SUBTIPO_FINANCIACION";

    /** The Constant PAR_SUBTIPO_PROYECTO_LAB. */
    public static final String PAR_SUBTIPO_PROYECTO_LAB = "PAR_SUBTIPO_PROYECTO_LAB";

    /** The id. */
    private Long id;

    /** The descripcion. */
    private String descripcion;

    /** The fecha inicial. */
    private Date fechaInicial;

    /** The fecha final. */
    private Date fechaFinal;

    /** The valor. */
    private String valor;

    /** The nombre. */
    private String nombre;

    /** The profesion. */
    private String profesion;

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
     * Gets the fecha final.
     *
     * @return the fecha final
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * Sets the fecha final.
     *
     * @param fechaFinal
     *            the new fecha final
     */
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * Gets the fecha inicial.
     *
     * @return the fecha inicial
     */
    public Date getFechaInicial() {
        return fechaInicial;
    }

    /**
     * Sets the fecha inicial.
     *
     * @param fechaInicial
     *            the new fecha inicial
     */
    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
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
     * Gets the valor.
     *
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * Sets the valor.
     *
     * @param valor
     *            the new valor
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * Gets the profesion.
     *
     * @return the profesion
     */
    public String getProfesion() {
        return profesion;
    }

    /**
     * Sets the profesion.
     *
     * @param profesion
     *            the new profesion
     */
    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

}
