package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * The Class TipoFormacion.
 */
public class TipoFormacion implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2673648183162538127L;

	/** The Constant EDUCACIÓN_NO_FORMAL. */
	public static final String EDUCACIÓN_NO_FORMAL = "NF";
	
	/** The Constant ESPECIALIZACIÓN. */
	public static final String ESPECIALIZACIÓN = "ES";
	
	/** The Constant ESPECIALIZACIÓN_MEDICINA_HUMANA. */
	public static final String ESPECIALIZACIÓN_MEDICINA_HUMANA = "EM";
	
	/** The Constant MAESTRIA_O_MAGISTER. */
	public static final String MAESTRIA_O_MAGISTER = "MA";
	
	/** The Constant MEDICINA_O_MÚSICA. */
	public static final String MEDICINA_O_MÚSICA = "MM";
	
	/** The Constant PH_DODOCTORADO. */
	public static final String PH_DODOCTORADO = "PH";
	
	/** The Constant TÍTULO_DE_PREGRADO. */
	public static final String TÍTULO_DE_PREGRADO = "PR";
	
	/** The Constant ESPECIALIZACION_UNIVERSITARIA. */
	public static final String ESPECIALIZACION_UNIVERSITARIA = "EU";
	
	/** The Constant ESPECIALIZACION_TECNICO_PROFESIONAL. */
	public static final String ESPECIALIZACION_TECNICO_PROFESIONAL = "ETP";
	
	/** The Constant ESPECIALIZACION_TECNOLOGICA. */
	public static final String ESPECIALIZACION_TECNOLOGICA = "ET";
	
	/** The Constant UNIVERSITARIA. */
	public static final String UNIVERSITARIA = "U";
	
	/** The Constant ESTUDIANTE_PREGRADO. */
	public static final String ESTUDIANTE_PREGRADO = "EP";
	
	/** The Constant ESPECIALIZACION_MEDICO_QUIRURGICA. */
	public static final String ESPECIALIZACION_MEDICO_QUIRURGICA = "EMQ";

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
