package co.edu.unal.hermes.modelo;

/**
 * The Class TipoCarta.
 */
public class TipoCarta {

	/** The Constant RAIZ. */
	public static final String RAIZ = "0";

	/** The Constant INICIO. */
	public static final long INICIO = 1;

	/** The Constant FINALIZACION. */
	public static final long FINALIZACION = 2;

	/** The Constant CAMBIO_RUBRO. */
	public static final long CAMBIO_RUBRO = 3;
	
	/** The Constant PRORROGA. */
	public static final long PRORROGA = 4;
	
	/** The Constant CANCELACION. */
	public static final long CANCELACION = 9;

	/** The Constant SUSPENSION. */
	public static final long SUSPENSION = 10;

	/** The Constant CERTIFICADO_MOVILIZACION. */
	public static final long CERTIFICADO_MOVILIZACION = 11;

	/** The Constant REACTIVACION. */
	public static final long REACTIVACION = 12;

	/** The Constant CAMBIO_INVESTIGADOR. */
	public static final long CAMBIO_INVESTIGADOR = 13;

	/** The Constant CAMBIO_INTEGRANTES. */
	public static final long CAMBIO_INTEGRANTES = 14;

    /** The Constant CAMBIO_CONTENIDO. */
    public static final long CAMBIO_CONTENIDO = 15;
    
    public static final long RESOLUCION_EXTERNA = 16;

    public static final long RESOLUCION_MODIFICATORIAS = 17;
    
	public static final long ADICION_PRESUPUESTAL = 18;

	/** The Constant CONECTOR. */
	public static final String CONECTOR = "--";

    /** The Constant CARTAS_UNICAS. */
    public static final String CARTAS_UNICAS = CONECTOR + INICIO + CONECTOR + FINALIZACION + CONECTOR + CANCELACION
            + CONECTOR + RESOLUCION_EXTERNA + CONECTOR ;

    /** The Constant CARTAS_FINANCIACION. */
    public static final String CARTAS_FINANCIACION = CONECTOR + FINALIZACION + CONECTOR + CANCELACION
            + CONECTOR;

	/** The Constant NECESITA_FIRMA. */
	public static final String NECESITA_FIRMA = "NECESITA_FIRMA";

	/** The id. */
	private Long id;

	/** The nombre. */
	private String nombre;

	/** The descripcion. */
	private String descripcion;

	/**
	 * Instantiates a new tipo carta.
	 */
	public TipoCarta() {
	}

	/**
	 * Instantiates a new tipo carta.
	 *
	 * @param id
	 *            the id
	 */
	public TipoCarta(Long id) {
		setId(id);
	}

	/**
	 * Gets the id.
	 *
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the descripcion.
	 *
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Sets the descripcion.
	 *
	 * @param descripcion
	 *            the new descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
