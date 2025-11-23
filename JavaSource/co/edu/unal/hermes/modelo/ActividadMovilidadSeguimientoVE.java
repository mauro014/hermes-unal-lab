package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class ActividadMovilidadSeguimientoVE implements Serializable{
		
	private static final long serialVersionUID = 9618360147751316L;
	private Long id;
	private String descripcion;
	private MovilidadVisitanteExterior movilidad;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public MovilidadVisitanteExterior getMovilidad() {
		return movilidad;
	}
	public void setMovilidad(MovilidadVisitanteExterior movilidad) {
		this.movilidad = movilidad;
	}
	
		
}
