package co.edu.unal.hermes.modelo;

/**
 * The Class IdDominioDetalle.
 */
public class IdDominioDetalle implements java.io.Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1945093266868273888L;

    /** The id. */
    String id;

    /** The tipo. */
    String tipo;

    /**
     * Instantiates a new id dominio detalle.
     */
    public IdDominioDetalle() {
        /*
         * Public empty constructor.
         */
    }

    /**
     * Instantiates a new id dominio detalle.
     *
     * @param id
     *            the id
     * @param tipo
     *            the tipo
     */
    public IdDominioDetalle(String id, String tipo) {
        super();
        this.id = id;
        this.tipo = tipo;
    }
    
    /**
     * Instantiates a new id dominio detalle.
     *
     * @param tipo the tipo
     */
    public IdDominioDetalle(String tipo) {
        super();
        this.tipo = tipo;
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
     * Gets the tipo.
     *
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the tipo.
     *
     * @param tipo
     *            the new tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
