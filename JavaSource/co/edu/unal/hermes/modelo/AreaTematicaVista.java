package co.edu.unal.hermes.modelo;


/**
 * The Class AreaTematicaVista.
 */
public class AreaTematicaVista {

    /** The id. */
    private Long id;
    
    /** The nombre sub area. */
    private String nombreSubArea;

    /** The nombre area. */
    private String nombreArea;

    /** The area tematica. */
    private DominioDetalle areaTematica;

    /** The sub area tematica. */
    private DominioDetalle subAreaTematica;

    /** The proyecto. */
    private Proyecto proyecto;
    
    /** The area tematica proyecto. */
    private AreaTematica areaTematicaProyecto;

    /** The tipo. */
    private Long tipo;

    /**
     * Gets the nombre sub area.
     *
     * @return the nombre sub area
     */
    public String getNombreSubArea() {
        return nombreSubArea;
    }

    /**
     * Sets the nombre sub area.
     *
     * @param nombreSubArea
     *            the new nombre sub area
     */
    public void setNombreSubArea(String nombreSubArea) {
        this.nombreSubArea = nombreSubArea;
    }

    /**
     * Gets the nombre area.
     *
     * @return the nombre area
     */
    public String getNombreArea() {
        return nombreArea;
    }

    /**
     * Sets the nombre area.
     *
     * @param nombreArea
     *            the new nombre area
     */
    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    /**
     * Gets the area tematica.
     *
     * @return the area tematica
     */
    public DominioDetalle getAreaTematica() {
        return areaTematica;
    }

    /**
     * Sets the area tematica.
     *
     * @param areaTematica
     *            the new area tematica
     */
    public void setAreaTematica(DominioDetalle areaTematica) {
        this.areaTematica = areaTematica;
    }

    /**
     * Gets the sub area tematica.
     *
     * @return the sub area tematica
     */
    public DominioDetalle getSubAreaTematica() {
        return subAreaTematica;
    }

    /**
     * Sets the sub area tematica.
     *
     * @param subAreaTematica
     *            the new sub area tematica
     */
    public void setSubAreaTematica(DominioDetalle subAreaTematica) {
        this.subAreaTematica = subAreaTematica;
    }

    /**
     * Gets the proyecto.
     *
     * @return the proyecto
     */
    public Proyecto getProyecto() {
        return proyecto;
    }

    /**
     * Sets the proyecto.
     *
     * @param proyecto
     *            the new proyecto
     */
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * Gets the tipo.
     *
     * @return the tipo
     */
    public Long getTipo() {
        return tipo;
    }

    /**
     * Sets the tipo.
     *
     * @param tipo
     *            the new tipo
     */
    public void setTipo(Long tipo) {
        this.tipo = tipo;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the area tematica proyecto.
     *
     * @return the areaTematicaProyecto
     */
    public AreaTematica getAreaTematicaProyecto() {
        return areaTematicaProyecto;
    }

    /**
     * Sets the area tematica proyecto.
     *
     * @param areaTematicaProyecto the areaTematicaProyecto to set
     */
    public void setAreaTematicaProyecto(AreaTematica areaTematicaProyecto) {
        this.areaTematicaProyecto = areaTematicaProyecto;
    }

}
