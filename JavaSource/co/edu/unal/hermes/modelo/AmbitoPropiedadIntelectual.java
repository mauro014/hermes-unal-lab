/**
 * @author Martha Liliana Correa O.
 * @date 21/07/2016
 */

package co.edu.unal.hermes.modelo;

public class AmbitoPropiedadIntelectual implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private DominioDetalle ambito;
    private PropiedadIntelectual propiedad;
    private String observaciones;

    /** default constructor */
    public AmbitoPropiedadIntelectual() {
        /*
         * Constructor para crear el objeto ambito propiedad vacio
         */
    }

    public AmbitoPropiedadIntelectual(Long id) {
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

    public DominioDetalle getAmbito() {
        return ambito;
    }

    public void setAmbito(DominioDetalle ambito) {
        this.ambito = ambito;
    }

}