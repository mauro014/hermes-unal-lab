package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * The Class EstadoGrupo.
 */
public class EstadoGrupo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5936177193364897013L;
	
	/** The Constant ACTIVO. */
	public static final String ACTIVO = "A";
	
	/** The Constant INGRESANDO. */
	public static final String INGRESANDO = "I";
    
    /** The Constant INACTIVO. */
    public static final String INACTIVO = "N";
    
    /** The Constant REGISTRADO. */
    public static final String REGISTRADO = "P";
    
    /** The Constant SOLICITUD_AVAL. */
    public static final String SOLICITUD_AVAL = "S";

    /** The Constant CORRECCIONES. */
    public static final String CORRECCIONES = "C";
    
    /** The Constant CORRECCIONES. */
    public static final String DISUELTO = "D";
	
	/** The id. */
	private String id;
	
	/** The nombre. */
	private String nombre;

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
}
