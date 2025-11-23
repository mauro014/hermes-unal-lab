/**
 * @author Martha Liliana Correa O.
 * @date 12/12/2016
 */

package co.edu.unal.hermes.modelo;

public class ProyectoPropiedadIntelectual implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String tipoProyecto;
	private Proyecto proyecto;
	private String nombreProyecto;
	private String idProyectoExtension;
	private String nombreProyectoExtension;
	private PropiedadIntelectual propiedad;
	private String sistemaAlmacenamiento;
	
	/** default constructor */
	public ProyectoPropiedadIntelectual() {
		
	}
	
	public ProyectoPropiedadIntelectual(Long id) {
		this.setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public PropiedadIntelectual getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(PropiedadIntelectual propiedad) {
        this.propiedad = propiedad;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getSistemaAlmacenamiento() {
        return sistemaAlmacenamiento;
    }

    public void setSistemaAlmacenamiento(String sistemaAlmacenamiento) {
        this.sistemaAlmacenamiento = sistemaAlmacenamiento;
    }

    public String getIdProyectoExtension() {
        return idProyectoExtension;
    }

    public void setIdProyectoExtension(String idProyectoExtension) {
        this.idProyectoExtension = idProyectoExtension;
    }

    public String getNombreProyectoExtension() {
        return nombreProyectoExtension;
    }

    public void setNombreProyectoExtension(String nombreProyectoExtension) {
        this.nombreProyectoExtension = nombreProyectoExtension;
    }

}