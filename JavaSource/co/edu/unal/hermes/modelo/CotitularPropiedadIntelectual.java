/**
 * @author Martha Liliana Correa O.
 * @date 13/12/2016
 */

package co.edu.unal.hermes.modelo;

public class CotitularPropiedadIntelectual implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private FuenteFinanciacion entidad;
	private float porcentaje;
	private PropiedadIntelectual propiedad;
	
	/** default constructor */
	public CotitularPropiedadIntelectual() {
		
	}
	
	public CotitularPropiedadIntelectual(Long id) {
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

    public FuenteFinanciacion getEntidad() {
        return entidad;
    }

    public void setEntidad(FuenteFinanciacion entidad) {
        this.entidad = entidad;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }

}