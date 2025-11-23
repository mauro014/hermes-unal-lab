package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class ObservacionSeguimiento.
 */
public class ObservacionSeguimiento implements Comparator<Object>, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7285171834898501660L;

    /** The Constant REGISTRADO. */
    public static final String REGISTRADO = "R";

    /** The Constant BORRADO. */
    public static final String BORRADO = "B";

    /** The id. */
    private Long id;

    /** The descripcion. */
    private String descripcion;

    /** The estado. */
    private String estado;

    /** The tipo. */
    private String tipo;

    /** The fecha. */
    private Date fecha;

    /** The fecha eliminacion. */
    private Date fechaEliminacion;

    /** The responsable. */
    private Persona responsable;

    /** The responsable eliminacion. */
    private Persona responsableEliminacion;

    /** The proyecto. */
    private Proyecto proyecto;
    
    private String respuesta;
    
    private Date fechaRespuesta;

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
     * Gets the descripcion.
     *
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Gets the descripcion vista. Add a <br />
     * element to show propertly the value in the view.
     *
     * @return the descripcion vista
     */
    public String getDescripcionVista() {
        if (StringUtils.isNotBlank(descripcion)) {
            return descripcion.replaceAll("(\r\n|\n\r|\r|\n)", "<br />");
        } else {
            return "";
        }
    }

    /**
     * Sets the descripcion.
     *
     * @param descripcion
     *            the new descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object arg1, Object arg2) {
        ObservacionSeguimiento observacion1 = (ObservacionSeguimiento) arg1;
        ObservacionSeguimiento observacion2 = (ObservacionSeguimiento) arg2;
        if (observacion1.getId() != null && observacion2.getId() != null) {
            if ((observacion1.getId()).longValue() < observacion2.getId().longValue())
                return -1;
            if ((observacion1.getId()).longValue() == observacion2.getId().longValue())
                return 0;
            if ((observacion1.getId()).longValue() > observacion2.getId().longValue())
                return 1;
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object a) {
        if (!(a instanceof ObservacionSeguimiento)) {
            return false;
        }
        ObservacionSeguimiento observacionSeguimiento = (ObservacionSeguimiento) a;
        if (observacionSeguimiento.id == null || this.id == null) {
            return false;
        }
        return (observacionSeguimiento.getId().equals(this.getId()));
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
     * Gets the fecha eliminacion.
     *
     * @return the fecha eliminacion
     */
    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    /**
     * Sets the fecha eliminacion.
     *
     * @param fechaEliminacion
     *            the new fecha eliminacion
     */
    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    /**
     * Gets the responsable eliminacion.
     *
     * @return the responsable eliminacion
     */
    public Persona getResponsableEliminacion() {
        return responsableEliminacion;
    }

    /**
     * Sets the responsable eliminacion.
     *
     * @param responsableEliminacion
     *            the new responsable eliminacion
     */
    public void setResponsableEliminacion(Persona responsableEliminacion) {
        this.responsableEliminacion = responsableEliminacion;
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
     * Gets the estado.
     *
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the estado.
     *
     * @param estado
     *            the new estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
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
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the tipo.
     *
     * @param tipo
     *            the new tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}
    
    

}
