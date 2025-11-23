package co.edu.unal.hermes.modelo;

import java.io.Serializable;

public class CategoriaGrupo implements Serializable {

	private static final long serialVersionUID = 6118953564420075295L;

	public static final String RECONOCIDO = "R";
	public static final String REGISTRADO = "G";
	public static final String CATEGORIA_A = "A";
	public static final String CATEGORIA_B = "B";
	public static final String CATEGORIA_C = "C";
	public static final String CATEGORIA_A1 = "A1";
	
	public static final String ACTIVO = "A";
	public static final String INGRESANDO = "I";
	public static final String SOLICITUD_AVAL = "S";
	public static final String DEVUELTO_CORRECCION = "C";
	public static final String AVAL = "P";

	// wam²
	// Grupos categorìa Grupo D
	public static final String CATEGORIA_D = "D";

	private String id;
	private String nombre;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
