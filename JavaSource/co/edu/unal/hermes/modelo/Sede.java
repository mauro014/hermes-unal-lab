package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * The Class Sede.
 */
public class Sede implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2702532954707747869L;

	/** The id. */
	private Long id;
	
	/** The nombre. */
	private String nombre;
	
	/** The ciudad. */
	private String ciudad;

	/** The Constant NIVEL_NACIONAL. */
	public static final Long NIVEL_NACIONAL = 1L;
	
	/** The Constant BOGOTA. */
	public static final Long BOGOTA = 2L;
	
	/** The Constant MEDELLIN. */
	public static final Long MEDELLIN = 3L;
	
	/** The Constant MANIZALES. */
	public static final Long MANIZALES = 4L;
	
	/** The Constant PALMIRA. */
	public static final Long PALMIRA = 5L;
	
	/** The Constant AMAZONIA. */
	public static final Long AMAZONIA = 6L;
	
	/** The Constant ORINOQUIA. */
	public static final Long ORINOQUIA = 7L;
	
	/** The Constant CARIBE. */
	public static final Long CARIBE = 8L;
	
	/** The Constant TUMACO. */
	public static final Long TUMACO = 9L;
	
	/** The Constant LA_PAZ. */
	public static final Long LA_PAZ = 0L;
	
	/** The Constant SEDES_ANDINAS. */
	
	public static final String SEDE_NIVEL_NACIONAL = "'1'";
	
	public static final String SEDES_ANDINAS = "'2','3','4','5'";
	
	public static final String SEDES_PRESENCIA_NACIONAL = "'6','7','8','9','0'";

	/**
	 * Instantiates a new sede.
	 */
	public Sede() {
		super();
	}

	/**
	 * Instantiates a new sede.
	 *
	 * @param id the id
	 */
	public Sede(Long id) {
		super();
		this.id = id;
	}
	
    /**
     * Instantiates a new sede.
     *
     * @param id the id
     */
    public Sede(String id) {
        super();
        setId(id);
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
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(String id) {
        if(id != null){
            try{
                this.id = Long.parseLong(id);
            }
            catch(NumberFormatException nfe){
                this.id = null;
            }
        }
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Sede && obj != null
				&& nombre.equals(((Sede) obj).getNombre());
	}

	/**
	 * Gets the ciudad.
	 *
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * Sets the ciudad.
	 *
	 * @param ciudad            the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
    
    /**
     * Checks if is es sede presencia nacional.
     *
     * @return true, if is es sede presencia nacional
     */
    public boolean isEsSedePresenciaNacional(){
        if(this.id!=null){
            if(this.id.equals(AMAZONIA) || this.id.equals(ORINOQUIA) || this.id.equals(CARIBE) 
            		|| this.id.equals(TUMACO) || this.id.equals(NIVEL_NACIONAL)|| this.id.equals(LA_PAZ)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if is es sede presencia nacional.
     *
     * @return true, if is es sede presencia nacional
     */
    public boolean isEsSedeAndina(){
        return !isEsSedePresenciaNacional();
    }
    
    public boolean isEsNivelNacional(){
        if(this.id.equals(NIVEL_NACIONAL)){
            return true;
        }
        return false;
    }

}
