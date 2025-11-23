package co.edu.unal.hermes.modelo;

import java.io.Serializable;


public class PosibleAgendaGrupo implements Serializable{

	private static final long serialVersionUID = -5175418145166214592L;

	private Long id;

    private String nombre;
   
    private String estado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
    
}
