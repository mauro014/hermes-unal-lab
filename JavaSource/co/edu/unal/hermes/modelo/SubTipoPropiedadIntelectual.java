/**
 * @author Martha Liliana Correa O.
 * @date 21/07/2016
 */

package co.edu.unal.hermes.modelo;

public class SubTipoPropiedadIntelectual implements java.io.Serializable {

    /**
     * 
     */
	
	public static final Long PATENTE_INVENCION_PROPIEDAD_INDUSTRIAL = 1L;
	public static final Long PATENTE_MODELO_UTILIDAD_PROPIEDAD_INDUSTRIAL = 2L;
	
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;
    private TipoPropiedadIntelectual tipo;
    private String estadoGestor;

    /** default constructor */
    public SubTipoPropiedadIntelectual() {
        /*
         * Constructor para crear el objeto subtipo propiedad vacio
         */
    }

    public SubTipoPropiedadIntelectual(Long id) {
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

    public TipoPropiedadIntelectual getTipo() {
        return tipo;
    }

    public void setTipo(TipoPropiedadIntelectual tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

	public String getEstadoGestor() {
		return estadoGestor;
	}

	public void setEstadoGestor(String estadoGestor) {
		this.estadoGestor = estadoGestor;
	}

}