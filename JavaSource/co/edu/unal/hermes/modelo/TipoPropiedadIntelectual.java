/**
 * @author Martha Liliana Correa O.
 * @date 21/07/2016
 */

package co.edu.unal.hermes.modelo;

public class TipoPropiedadIntelectual implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;

    /** default constructor */
    public TipoPropiedadIntelectual() {
        /*
         * Constructor para crear el objeto tipo propiedad vacio
         */
    }

    /*
     * Método para almacenar tipo de la propiedad intelectual unicamente a con
     * el Id No se requieren mas datos para el almacenamiento
     */
    public TipoPropiedadIntelectual(Long id) {
        this.setId(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}