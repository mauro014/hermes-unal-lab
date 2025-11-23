/**
 * @author Martha Liliana Correa O.
 * @date 30/06/2017
 */

package co.edu.unal.hermes.modelo;

public class NegociacionPropiedadIntelectual implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private FuenteFinanciacion entidad;
	private PropiedadIntelectual propiedad;
	private String formaPago;
	private float tiempoAnos;
	
	/** default constructor */
	public NegociacionPropiedadIntelectual() {
		
	}
	
	public NegociacionPropiedadIntelectual(Long id) {
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

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public float getTiempoAnos() {
        return tiempoAnos;
    }

    public void setTiempoAnos(float tiempoAnos) {
        this.tiempoAnos = tiempoAnos;
    }

}