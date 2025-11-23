/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Maneja los objetivos específicos que enmarcan cada proyecto.
 */
public class ActividadPersona implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    private Long id;

    /** The investigador. */
    private Investigador investigador;
    
    /** The actividad. */
    private Actividad actividad;

    /** The semana inicial. */
    private Integer semanaInicial;
    
    /** The duracion semanas. */
    private Integer duracionSemanas;
    
    /** The inversion. */
    private String inversion;

    /**
     * Gets the inversion.
     *
     * @return the inversion
     */
    public String getInversion() {
        return inversion;
    }

    /**
     * Sets the inversion.
     *
     * @param inversion the new inversion
     */
    public void setInversion(String inversion) {
        this.inversion = inversion;
    }

    /**
     * Gets the actividad.
     *
     * @return the actividad
     */
    public Actividad getActividad() {
        return actividad;
    }

    /**
     * Sets the actividad.
     *
     * @param actividad the new actividad
     */
    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    /**
     * Gets the duracion semanas.
     *
     * @return the duracion semanas
     */
    public Integer getDuracionSemanas() {
        return duracionSemanas;
    }

    /**
     * Sets the duracion semanas.
     *
     * @param duracionSemanas the new duracion semanas
     */
    public void setDuracionSemanas(Integer duracionSemanas) {
        this.duracionSemanas = duracionSemanas;
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
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the investigador.
     *
     * @return the investigador
     */
    public Investigador getInvestigador() {
        return investigador;
    }

    /**
     * Sets the investigador.
     *
     * @param investigador the new investigador
     */
    public void setInvestigador(Investigador investigador) {
        this.investigador = investigador;
    }

    /**
     * Gets the semana inicial.
     *
     * @return the semana inicial
     */
    public Integer getSemanaInicial() {
        return semanaInicial;
    }

    /**
     * Sets the semana inicial.
     *
     * @param semanaInicial the new semana inicial
     */
    public void setSemanaInicial(Integer semanaInicial) {
        this.semanaInicial = semanaInicial;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if (!(o instanceof ActividadPersona)) {
            return false;
        }
        ActividadPersona ap = (ActividadPersona) o;
        if (ap.getId() != null && this.id != null && ap.getId().equals(this.getId())) {
            return true;
        }
        if (ap.getId() == null && this.id == null && ap.getDuracionSemanas().equals(this.getDuracionSemanas())
                && ap.getSemanaInicial().equals(this.getSemanaInicial())
                && ap.getInvestigador().equals(this.getInvestigador())
                && ap.getActividad().equals(this.getActividad())) {
            return true;
        }
        return false;
    }

    /**
     * Gets the obtener serializado.
     *
     * @return the obtener serializado
     */
    public String getObtenerSerializado() {
        return null;
    }

}
