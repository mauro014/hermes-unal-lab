/**
 * @author Martha Liliana Correa O.
 * @date 21/07/2016
 */

package co.edu.unal.hermes.modelo;

public class ClasePropiedadIntelectual implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private DominioDetalle clase;
    private PropiedadIntelectual propiedad;
    private String observaciones;

    /** default constructor */
    public ClasePropiedadIntelectual() {
        /*
         * Constructor para crear el objeto clase propiedad vacio
         */
    }

    public ClasePropiedadIntelectual(Long id) {
        this.setId(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PropiedadIntelectual getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(PropiedadIntelectual propiedad) {
        this.propiedad = propiedad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public DominioDetalle getClase() {
        return clase;
    }

    public void setClase(DominioDetalle clase) {
        this.clase = clase;
    }

}