/*
 * Created on 03-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * Guarda un Histórico de estados de los proyectos en la convocatoria 
 */
public class HistoricoEstadoProyecto {

    private Long id;

    // llave foranea
    private EstadoProyecto estadoProyecto;
    private Proyecto proyecto;

    // Atributos propios del historico de proyectos
    private Date fecha;
    private String justificacion;
    private Persona responsable;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public EstadoProyecto getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}
}
