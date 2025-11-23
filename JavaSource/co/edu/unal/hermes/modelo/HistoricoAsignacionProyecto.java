/**
 * @author: Mauricio Amaya Ríos
 * @date: 05/02/2015
 */

package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class HistoricoAsignacionProyecto implements Serializable  {

	private static final long serialVersionUID = 2215323451659263784L;
	private Long id;
    private Proyecto proyecto;
    private Date fecha;
    private Persona asesor;
    private Persona coordinador;
    private String tipo;

    public static String SEGUIMIENTO = "S";
    public static String REQUISITOS = "R";
    public static String EVALUACION = "E";
   
	public HistoricoAsignacionProyecto() {
		super();
	}

	public HistoricoAsignacionProyecto(Proyecto proyecto, Persona asesor,
			Persona coordinador, String tipo) {
		super();
		this.proyecto = proyecto;
		this.asesor = asesor;
		this.coordinador = coordinador;
		this.tipo = tipo;
		this.fecha = new Date();
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
	public Persona getAsesor() {
		return asesor;
	}
	public void setAsesor(Persona asesor) {
		this.asesor = asesor;
	}
	public Persona getCoordinador() {
		return coordinador;
	}
	public void setCoordinador(Persona coordinador) {
		this.coordinador = coordinador;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
