package co.edu.unal.hermes.modelo;

/**
 * Especifica los tipos de estado civil que puede tener el 
 * investigador como son: Soltero, casado, unión libre, 
 * viudo, divorciado
 */
public class EstadoCivil {
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
