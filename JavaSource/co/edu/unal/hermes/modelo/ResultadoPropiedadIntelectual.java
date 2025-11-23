/**
 * @author Martha Liliana Correa O.
 * @date 14/10/2016
 */

package co.edu.unal.hermes.modelo;

public class ResultadoPropiedadIntelectual implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private DominioDetalle resultado;
	private String descripcion;
	private PropiedadIntelectual propiedad;
	
	/** default constructor */
	public ResultadoPropiedadIntelectual() {
		
	}
	
	public ResultadoPropiedadIntelectual(Long id) {
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

    public DominioDetalle getResultado() {
        return resultado;
    }

    public void setResultado(DominioDetalle resultado) {
        this.resultado = resultado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}