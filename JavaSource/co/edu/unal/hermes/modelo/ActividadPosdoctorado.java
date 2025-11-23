/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Específica la actividad asociada a la movilidad de una visita.
 * 
 */
public class ActividadPosdoctorado implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4865570979473520280L;

    /** The id. */
    private Long id;
    
    /** The nombre. */
    private String nombre;
    
    /** The descripcion. */
    private String descripcion;
    
    /** The pertinencia. */
    private String pertinencia;
    
    /** The impacto. */
    private String impacto;
    
    /** The estancia. */
    private String estancia;
    
    /** The tipo. */
    private String tipo;
    
    /** The fecha realizacion. */
    private Date fechaRealizacion;
    
    /** The porcentaje avance. */
    private Long porcentajeAvance;
    
    /** The num informe. */
    private String numInforme;

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object a) {
        if (!(a instanceof ActividadPosdoctorado)) {
            return false;
        }
        ActividadPosdoctorado act = (ActividadPosdoctorado) a;

        if (act.id == null || this.id == null) {
            if (act.descripcion.equals(this.descripcion) && act.impacto.equals(this.impacto)
                    && act.pertinencia.equals(this.pertinencia) && act.estancia.equals(this.estancia)) {
                return true;
            }
        } else {
            return (act.getId().equals(this.getId()));
        }

        return false;
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
     * Gets the descripcion.
     *
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the descripcion.
     *
     * @param descripcion the new descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Gets the pertinencia.
     *
     * @return the pertinencia
     */
    public String getPertinencia() {
        return pertinencia;
    }

    /**
     * Sets the pertinencia.
     *
     * @param pertinencia the new pertinencia
     */
    public void setPertinencia(String pertinencia) {
        this.pertinencia = pertinencia;
    }

    /**
     * Gets the impacto.
     *
     * @return the impacto
     */
    public String getImpacto() {
        return impacto;
    }

    /**
     * Sets the impacto.
     *
     * @param impacto the new impacto
     */
    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }

    /**
     * Gets the estancia.
     *
     * @return the estancia
     */
    public String getEstancia() {
        return estancia;
    }

    /**
     * Sets the estancia.
     *
     * @param estancia the new estancia
     */
    public void setEstancia(String estancia) {
        this.estancia = estancia;
    }

    /**
     * Gets the nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre.
     *
     * @param nombre the new nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets the tipo.
     *
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the tipo.
     *
     * @param tipo the new tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Gets the fecha realizacion.
     *
     * @return the fecha realizacion
     */
    public Date getFechaRealizacion() {
        return fechaRealizacion;
    }

    /**
     * Sets the fecha realizacion.
     *
     * @param fechaRealizacion the new fecha realizacion
     */
    public void setFechaRealizacion(Date fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    /**
     * Gets the porcentaje avance.
     *
     * @return the porcentaje avance
     */
    public Long getPorcentajeAvance() {
        return porcentajeAvance;
    }

    /**
     * Sets the porcentaje avance.
     *
     * @param porcentajeAvance the new porcentaje avance
     */
    public void setPorcentajeAvance(Long porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    /**
     * Gets the num informe.
     *
     * @return the num informe
     */
    public String getNumInforme() {
        return numInforme;
    }

    /**
     * Sets the num informe.
     *
     * @param numInforme the new num informe
     */
    public void setNumInforme(String numInforme) {
        this.numInforme = numInforme;
    }

}
