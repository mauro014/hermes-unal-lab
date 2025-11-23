package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * The Class DominioDetalle.
 */
public class DominioDetalle implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1623754997472419377L;

    /** The Constant OTRO_CARACTER_OBRA. */
    public static final String OTRO_CARACTER_OBRA = "9";

    /** The Constant OTRO_AMBITO_OBRA. */
    public static final String OTRO_AMBITO_OBRA = "5";

    /** The Constant OTRO_TIPO_EDICION_OBRA. */
    public static final String OTRO_TIPO_EDICION_OBRA = "5";

    /** The Constant OTRO_CLASE_OBRA. */
    public static final String OTRO_CLASE_OBRA = "9";

    /** The Constant OTRO_FORMATO_OBRA. */
    public static final String OTRO_FORMATO_OBRA = "4";

    /** The Constant OTRO_RESULTADO_PI. */
    public static final String OTRO_RESULTADO_PI = "9";

    /** The descripcion. */
    String descripcion;

    /** The identificador. */
    IdDominioDetalle identificador;

    /** The estado. */
    String estado;

    /** The observacion. */
    String observacion;

    /** The id identificador areas ocde. */
    public static String ID_IDENTIFICADOR_AREAS_OCDE = "27";

    /** The id identificador ina. */
    public static String ID_IDENTIFICADOR_INA = "138";

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
     * @param Descripcion
     *            the new descripcion
     */
    public void setDescripcion(String Descripcion) {
        this.descripcion = Descripcion;
    }

    /**
     * Gets the identificador.
     *
     * @return the identificador
     */
    public IdDominioDetalle getIdentificador() {
        return identificador;
    }

    /**
     * Sets the identificador.
     *
     * @param identificador
     *            the new identificador
     */
    public void setIdentificador(IdDominioDetalle identificador) {
        this.identificador = identificador;
    }

    /**
     * Gets the estado.
     *
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the estado.
     *
     * @param estado
     *            the new estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Gets the observacion.
     *
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Sets the observacion.
     *
     * @param observacion
     *            the new observacion
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof DominioDetalle)) {
            return false;
        }
        DominioDetalle d = (DominioDetalle) o;
        if (d == null || d.getIdentificador().getId() == null || this.getIdentificador().getTipo() == null) {
            return false;
        }
        return (d.getIdentificador().getId().equals(this.getIdentificador().getId()) && d.getIdentificador().getTipo().equals(this.getIdentificador().getTipo()));
    }

}
