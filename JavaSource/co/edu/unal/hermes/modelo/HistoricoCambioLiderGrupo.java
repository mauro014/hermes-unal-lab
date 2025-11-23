package co.edu.unal.hermes.modelo;

import java.util.Date;


/**
 * The Class HistoricoCambioLiderGrupo.
 */
public class HistoricoCambioLiderGrupo {

    /** The id. */
    private Long id;

    /** The grupo. */
    private Grupo grupo;

    /** The fecha. */
    private Date fecha;

    /** The liderAnterior. */
    private Persona liderAnterior;

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
     * @param id
     *            the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the grupo.
     *
     * @return the grupo
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * Sets the grupo.
     *
     * @param grupo
     *            the new grupo
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    /**
     * Gets the fecha.
     *
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Sets the fecha.
     *
     * @param fecha
     *            the new fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Gets the lider anterior.
     *
     * @return the lider anterior
     */
    public Persona getLiderAnterior() {
        return liderAnterior;
    }

    /**
     * Sets the lider anterior.
     *
     * @param liderAnterior the new lider anterior
     */
    public void setLiderAnterior(Persona liderAnterior) {
        this.liderAnterior = liderAnterior;
    }
    
    

}
