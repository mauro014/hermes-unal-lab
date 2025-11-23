/**
 * @author Martha Liliana Correa O.
 * @date 09/11/2015
 */

package co.edu.unal.hermes.modelo;

public class PreguntaClasificacion {

    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;

    public PreguntaClasificacion() {
        // Para crear objeto vacio
    }

    public PreguntaClasificacion(Long id) {
        setId(id);
    }

    /**
     * @return Returns the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Returns the nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *            The nombre to set.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
