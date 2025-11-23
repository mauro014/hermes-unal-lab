package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class TipoDuracion implements Serializable {

    public static final String HORA = "H";
    public static final String SEMANAS = "S";
    public static final String ANIOS = "A";
    
	private static final long serialVersionUID = 673042973133782465L;

	private String id;
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
