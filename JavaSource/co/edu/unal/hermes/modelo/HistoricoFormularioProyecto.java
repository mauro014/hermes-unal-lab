/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Maneja los objetivos específicos que enmarcan cada proyecto.
 */
public class HistoricoFormularioProyecto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2947464220645361866L;
    
    /** The id. */
    private Long id;
    
    /** The proyecto. */
    private Proyecto proyecto;
    
    /** The doc persona. */
    private String docPersona;
    
    /** The tipo documento persona. */
    private String tipoDocumentoPersona;
    
    /** The formulario. */
    private Formulario formulario;
    
    /** The fecha cambio. */
    private Date fechaCambio;
    
    /** The es parcial. */
    private String esParcial;

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
     * Gets the formulario.
     *
     * @return the formulario
     */
    public Formulario getFormulario() {
        return formulario;
    }

    /**
     * Sets the formulario.
     *
     * @param formulario the new formulario
     */
    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    /**
     * Gets the fecha cambio.
     *
     * @return the fecha cambio
     */
    public Date getFechaCambio() {
        return fechaCambio;
    }

    /**
     * Sets the fecha cambio.
     *
     * @param fechaCambio the new fecha cambio
     */
    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    /**
     * Gets the doc persona.
     *
     * @return the docPersona
     */
    public String getDocPersona() {
        return docPersona;
    }

    /**
     * Sets the doc persona.
     *
     * @param docPersona the docPersona to set
     */
    public void setDocPersona(String docPersona) {
        this.docPersona = docPersona;
    }

    /**
     * Gets the tipo documento persona.
     *
     * @return the tipoDocumentoPersona
     */
    public String getTipoDocumentoPersona() {
        return tipoDocumentoPersona;
    }

    /**
     * Sets the tipo documento persona.
     *
     * @param tipoDocumentoPersona the tipoDocumentoPersona to set
     */
    public void setTipoDocumentoPersona(String tipoDocumentoPersona) {
        this.tipoDocumentoPersona = tipoDocumentoPersona;
    }

    /**
     * Gets the es parcial.
     *
     * @return the esParcial
     */
    public String getEsParcial() {
        return esParcial;
    }

    /**
     * Sets the es parcial.
     *
     * @param esParcial the esParcial to set
     */
    public void setEsParcial(String esParcial) {
        this.esParcial = esParcial;
    }

}
