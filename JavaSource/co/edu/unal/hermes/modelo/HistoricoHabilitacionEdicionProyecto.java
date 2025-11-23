/**
 * @author: Mauricio Amaya Ríos
 * @date: 05/02/2015
 */

package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class HistoricoHabilitacionEdicionProyecto implements Serializable  {

	private static final long serialVersionUID = 2215328871659263784L;
	
	private Long id;
    private Proyecto proyecto;
    private Date fecha;
    private Persona coordinador;
    private String asignacion;
    private String comentarios;
   
	public HistoricoHabilitacionEdicionProyecto() {
		super();
	}

	public HistoricoHabilitacionEdicionProyecto(Proyecto proyecto,
			Persona coordinador, String asignacion) {
		super();
		this.proyecto = proyecto;
		this.coordinador = coordinador;
		this.fecha = new Date();
		this.asignacion = asignacion;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Proyecto getProyecto() {
		return proyecto;
	}
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Persona getCoordinador() {
		return coordinador;
	}
	public void setCoordinador(Persona coordinador) {
		this.coordinador = coordinador;
	}

	public String getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(String asignacion) {
		this.asignacion = asignacion;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
}
