package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * The Class HistoricoEstadoEvaluacion.
 */
public class HistoricoEstadoEvaluacion {

    /** The id. */
    private Long id;

    /** The fecha. */
    private Date fecha;

    /** The responsable. */
    private Persona responsable;
    
    /** The proyecto evaluador. */
    private ProyectoEvaluador proyectoEvaluador;
    
    /** The estado evaluacion. */
    private String estadoEvaluacion;
    
    /** The observaciones. */
    private String observaciones;

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
     * Gets the responsable.
     *
     * @return the responsable
     */
    public Persona getResponsable() {
        return responsable;
    }

    /**
     * Sets the responsable.
     *
     * @param responsable
     *            the new responsable
     */
    public void setResponsable(Persona responsable) {
        this.responsable = responsable;
    }

    /**
     * Gets the proyecto evaluador.
     *
     * @return the proyectoEvaluador
     */
    public ProyectoEvaluador getProyectoEvaluador() {
        return proyectoEvaluador;
    }

    /**
     * Sets the proyecto evaluador.
     *
     * @param proyectoEvaluador the proyectoEvaluador to set
     */
    public void setProyectoEvaluador(ProyectoEvaluador proyectoEvaluador) {
        this.proyectoEvaluador = proyectoEvaluador;
    }

    /**
     * Gets the estado evaluacion.
     *
     * @return the estadoEvaluacion
     */
    public String getEstadoEvaluacion() {
        return estadoEvaluacion;
    }

    /**
     * Sets the estado evaluacion.
     *
     * @param estadoEvaluacion the estadoEvaluacion to set
     */
    public void setEstadoEvaluacion(String estadoEvaluacion) {
        this.estadoEvaluacion = estadoEvaluacion;
    }

    /**
     * Gets the observaciones.
     *
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Sets the observaciones.
     *
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public void asignarProyectoEvaluador(ProyectoEvaluador proyectoEvaluador){
        this.proyectoEvaluador = proyectoEvaluador;
        this.estadoEvaluacion = proyectoEvaluador.getEstado();
        this.fecha = new Date();        
    }

}
