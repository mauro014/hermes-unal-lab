package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Categoriza que se le da al investigador de acuerdo a los estudios que ha
 * realizado
 */

public class CategoriaInvestigador implements Serializable {

	public static String ESTUDIANTE = "Estudiante";
	public static String DOCENTE = "Docente";
	public static String EXTERNO = "Externo";

	public static String IDEXTERNO = "3";
	public static String IDDOCENTE = "2";
	public static String IDESTUDIANTE = "1";
	public static String IDCODIRECTOR = "4";

	private Long id;
	private String nombre;
	
	public CategoriaInvestigador() {
		
	}
	
	public CategoriaInvestigador(Long id) {
		this.id = id;
	}

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
}
