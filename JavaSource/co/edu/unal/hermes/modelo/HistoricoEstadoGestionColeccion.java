/**
 * @author: Martha Liliana Correa O.
 * @date: 14/06/2016
 */

package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

public class HistoricoEstadoGestionColeccion implements Serializable  {
	
	private static final long serialVersionUID = 8416118791158760060L;
	
	private Long id;
    private ColeccionGestion coleccionGestion;
    private String estadoGestion;
    private Date fecha;
    private String observacion;
    private Persona resposable;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ColeccionGestion getColeccionGestion() {
		return coleccionGestion;
	}
	public void setColeccionGestion(ColeccionGestion coleccionGestion) {
		this.coleccionGestion = coleccionGestion;
	}
	public String getEstadoGestion() {
		return estadoGestion;
	}
	public void setEstadoGestion(String estadoGestion) {
		this.estadoGestion = estadoGestion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Persona getResposable() {
		return resposable;
	}
	public void setResposable(Persona resposable) {
		this.resposable = resposable;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public static HistoricoEstadoGestionColeccion generarHistoricoEstado(ColeccionGestion coleccionGestion) {

		HistoricoEstadoGestionColeccion hecg = new HistoricoEstadoGestionColeccion();

		hecg.setColeccionGestion(coleccionGestion);
		hecg.setObservacion(coleccionGestion.getJustificacion());
		hecg.setEstadoGestion(coleccionGestion.getEstado().getIdentificador().getTipo());

		return hecg;

	}
   
}
