/*
 * Created on 03-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * Guarda un Histórico de estados de los proyectos en la convocatoria 
 */
public class HistoricoBorrarFecha {

    private Long id;

    // llave foranea
    private Proyecto proyecto;

    // Atributos propios del historico de proyectos
    private Date fecha;
    private Persona responsable;
    private Date fechaBorrada;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

	public Date getFechaBorrada() {
		return fechaBorrada;
	}

	public void setFechaBorrada(Date fechaBorrada) {
		this.fechaBorrada = fechaBorrada;
	}
}
