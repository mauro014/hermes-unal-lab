/**
 * @author Martha Liliana Correa O.
 * @date 21/07/2016
 */

package co.edu.unal.hermes.modelo;

public class CaracterPropiedadIntelectual implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private DominioDetalle caracter;
	private PropiedadIntelectual propiedad;
	private String observaciones;
	
	public static final String OBRA_INDIVIDUAL = "1";
	
	/** default constructor */
	public CaracterPropiedadIntelectual() {
		
	}
	
	public CaracterPropiedadIntelectual(Long id) {
		this.setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public DominioDetalle getCaracter() {
        return caracter;
    }

    public void setCaracter(DominioDetalle caracter) {
        this.caracter = caracter;
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



}