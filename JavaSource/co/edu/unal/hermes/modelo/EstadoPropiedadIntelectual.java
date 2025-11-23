/**
 * @author Martha Liliana Correa O.
 * @date 21/07/2016
 */

package co.edu.unal.hermes.modelo;

public class EstadoPropiedadIntelectual implements java.io.Serializable {

    /**
     * 
     */
	
	public static final Long MANTENIMIENTO_PROPIEDAD_INDUSTRIAL = 32L;
	
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private String descripcion;
    private ClasificacionEstadoPropiedadIntelectual clasificacion;
    private TipoPropiedadIntelectual tipoPropiedad;
    private Long orden;

    /** default constructor */
    public EstadoPropiedadIntelectual() {
        /*
         * Constructor para crear el objeto estado propiedad vacio
         */
    }

    public EstadoPropiedadIntelectual(Long id) {
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

    public ClasificacionEstadoPropiedadIntelectual getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(ClasificacionEstadoPropiedadIntelectual clasificacion) {
        this.clasificacion = clasificacion;
    }

    public TipoPropiedadIntelectual getTipoPropiedad() {
        return tipoPropiedad;
    }

    public void setTipoPropiedad(TipoPropiedadIntelectual tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getOrden() {
        return orden;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }

}