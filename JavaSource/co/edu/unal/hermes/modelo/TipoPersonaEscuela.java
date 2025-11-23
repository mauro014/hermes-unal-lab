package co.edu.unal.hermes.modelo;

public class TipoPersonaEscuela {

	public static final String RAIZ = "0";

	private Long id;
	private String nombre;

	public TipoPersonaEscuela() {
	}

	public TipoPersonaEscuela(Long id) {
		setId(id);
	}

	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
