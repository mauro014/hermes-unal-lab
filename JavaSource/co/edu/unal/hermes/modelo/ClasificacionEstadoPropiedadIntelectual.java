/**
 * @author Martha Liliana Correa O.
 * @date 21/07/2016
 */

package co.edu.unal.hermes.modelo;

public class ClasificacionEstadoPropiedadIntelectual implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Long EN_TRAMITE_INTERNO = 2L;
	public static final Long EN_TRAMITE_EXTERNO = 3L;
	
	private Long id;
	private String nombre;
	private String descripcion;
	private Long ordenLogico;

	
	/** default constructor */
	public ClasificacionEstadoPropiedadIntelectual() {
		
	}
	
	public ClasificacionEstadoPropiedadIntelectual(Long id) {
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

    public Long getOrdenLogico() {
        return ordenLogico;
    }

    public void setOrdenLogico(Long ordenLogico) {
        this.ordenLogico = ordenLogico;
    }

}