/**
 * @author Martha Liliana Correa O.
 * @date 04/10/2016
 */

package co.edu.unal.hermes.modelo;

public class FormatoPropiedadIntelectual implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private DominioDetalle medio;
    private PropiedadIntelectual propiedad;
    private String observaciones;

    /** default constructor */
    public FormatoPropiedadIntelectual() {
        /*
         * Constructor para crear el objeto medio de publicacion propiedad intelectual vacio
         */
    }

    public FormatoPropiedadIntelectual(Long id) {
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

    public DominioDetalle getMedio() {
        return medio;
    }

    public void setMedio(DominioDetalle medio) {
        this.medio = medio;
    }

}