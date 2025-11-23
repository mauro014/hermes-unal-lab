package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class MovilidadPonencia implements Serializable{
    
    
	private TipoMovilidad 	tipoMovilidad;
	private MovilidadDocentesExterior movilidad;
	private Long id;
	private TipoPonencia ponencia;
	private String evento;
	private String  titulo;
	private String otra;


	
	public TipoMovilidad getTipoMovilidad() {
		return tipoMovilidad;
	}
	public void setTipoMovilidad(TipoMovilidad tipoMovilidad) {
		this.tipoMovilidad = tipoMovilidad;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MovilidadDocentesExterior getMovilidad() {
		return movilidad;
	}
	public void setMovilidad(MovilidadDocentesExterior movilidad) {
		this.movilidad = movilidad;
	}
	public TipoPonencia getPonencia() {
		return ponencia;
	}
	public void setPonencia(TipoPonencia ponencia) {
		this.ponencia = ponencia;
	}
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getOtra() {
		return otra;
	}
	public void setOtra(String otra) {
		this.otra = otra;
	}


	

}
