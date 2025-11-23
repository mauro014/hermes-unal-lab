package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class SemilleroSolicitudRespuesta implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nombre;
	private String estadoUAB;
	private String estadoVif;
	private String estadoDi;
	private String estadoCoordinador;
	
	public static final String PENDIENTE_REVISION = "1";

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstadoUAB() {
		return estadoUAB;
	}

	public void setEstadoUAB(String estadoUAB) {
		this.estadoUAB = estadoUAB;
	}

	public String getEstadoVif() {
		return estadoVif;
	}

	public void setEstadoVif(String estadoVif) {
		this.estadoVif = estadoVif;
	}

	public String getEstadoDi() {
		return estadoDi;
	}

	public void setEstadoDi(String estadoDi) {
		this.estadoDi = estadoDi;
	}

	public String getEstadoCoordinador() {
		return estadoCoordinador;
	}

	public void setEstadoCoordinador(String estadoCoordinador) {
		this.estadoCoordinador = estadoCoordinador;
	}
}