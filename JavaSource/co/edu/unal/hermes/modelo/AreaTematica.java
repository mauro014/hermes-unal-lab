/*
 * Created on 14-sep-2005
 */
package co.edu.unal.hermes.modelo;

/**
 * The Class AreaTematica.
 *
 * @author jpduqueg
 */
public class AreaTematica {

    /** The id. */
    private Long id;
    
    /** The proyecto area tematica. */
    private DominioDetalle proyectoAreaTematica;
    
    /** The proyecto. */
    private Proyecto proyecto;
    
    /** The tipo. */
    private Long tipo;

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
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
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
     * @param proyecto the new proyecto
     */
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * Gets the proyecto area tematica.
     *
     * @return the proyecto area tematica
     */
    public DominioDetalle getProyectoAreaTematica() {
        return proyectoAreaTematica;
    }

    /**
     * Sets the proyecto area tematica.
     *
     * @param proyectoAreaTematica the new proyecto area tematica
     */
    public void setProyectoAreaTematica(DominioDetalle proyectoAreaTematica) {
        this.proyectoAreaTematica = proyectoAreaTematica;
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
     * @param tipo the new tipo
     */
    public void setTipo(Long tipo) {
        this.tipo = tipo;
    }
}
