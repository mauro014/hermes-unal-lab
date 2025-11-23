package co.edu.unal.hermes.modelo;

/**
 * Representa la Institución a la cual está vinculado el proyecto.
 */
public class Institucion {

    /** The id. */
    private String id;
    
    /** The nombre. */
    private String nombre;

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
     * @param nombre the new nombre
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
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }
}
