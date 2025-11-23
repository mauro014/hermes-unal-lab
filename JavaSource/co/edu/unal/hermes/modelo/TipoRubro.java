package co.edu.unal.hermes.modelo;

/**
 * The Class TipoRubro.
 */
public class TipoRubro implements Cloneable {

    /** The equipos. */
    public static Long EQUIPOS = new Long(6);
    
    /** The ingreso. */
    public static String INGRESO = "INGRESO";
    
    /** The Constant ADMINISTRACION_PROYECTO. */
    public static final Long ADMINISTRACION_PROYECTO = 55L;
    
    /** The Constant DESEMBOLSO. */
    public static final Long DESEMBOLSO = 170L;
    
    /** The Constant INGRESOS. */
    public static final Long INGRESOS = 171L;
    
    /** The CCP 2022. */
    public static String DESCRIPCION_CCP2022 = "GASTOS_CP_2022";

    /** The id. */
    private Long id;
    
    /** The nombre. */
    private String nombre;
    
    /** The descripcion. */
    private String descripcion;
    
    /** The padre. */
    public TipoRubro padre;
    
    /** The quipu. */
    private String quipu;
    
    private String codigoQuipu;
    
    private String nombreVRI;
    
    private boolean rubroContrapartida;
    private boolean estado; //Activo - Inactivo

    /**
     * Gets the padre.
     *
     * @return the padre
     */
    public TipoRubro getPadre() {
        return padre;
    }

    /**
     * Sets the padre.
     *
     * @param padre the new padre
     */
    public void setPadre(TipoRubro padre) {
        this.padre = padre;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

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
     * @param descripcion the new descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the quipu.
     *
     * @return the quipu
     */
    public String getQuipu() {
        return quipu;
    }

    /**
     * Sets the quipu.
     *
     * @param quipu the new quipu
     */
    public void setQuipu(String quipu) {
        this.quipu = quipu;
    }

	public String getCodigoQuipu() {
		return codigoQuipu;
	}

	public void setCodigoQuipu(String codigoQuipu) {
		this.codigoQuipu = codigoQuipu;
	}

	public String getNombreVRI() {
		return nombreVRI;
	}

	public void setNombreVRI(String nombreVRI) {
		this.nombreVRI = nombreVRI;
	}

	public boolean isRubroContrapartida() {
		if(id.equals(130L)) { // Rubro de gastos de personal se hace esto para la convocatoria conjunta con la universidad milagro de ecuador
			return true;
		}
		return rubroContrapartida;
	}

	public void setRubroContrapartida(boolean rubroContrapartida) {
		this.rubroContrapartida = rubroContrapartida;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
}
