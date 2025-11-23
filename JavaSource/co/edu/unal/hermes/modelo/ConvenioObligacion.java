package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class ConvenioObligacion.
 */
public class ConvenioObligacion implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5491346192974504103L;

    /** The id. */
	private Long id;
	
	/** The id convenio. */
	private Convenio convenio;
	
	/** The obligacion. */
	private String obligacion;
	
	/** The fecha. */
	private Date fecha;
	
	
	/**
	 * Instantiates a new convenio obligacion.
	 */
	public ConvenioObligacion() {
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
	 * Gets the obligacion.
	 *
	 * @return the obligacion
	 */
	public String getObligacion() {
		return obligacion;
	}


	/**
	 * Sets the obligacion.
	 *
	 * @param obligacion the new obligacion
	 */
	public void setObligacion(String obligacion) {
		this.obligacion = obligacion;
	}


	/**
	 * Gets the fecha.
	 *
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}


	/**
	 * Sets the fecha.
	 *
	 * @param fecha the new fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


    /**
     * Gets the convenio.
     *
     * @return the convenio
     */
    public Convenio getConvenio() {
        return convenio;
    }


    /**
     * Sets the convenio.
     *
     * @param convenio the convenio to set
     */
    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }
	
	
}
