
package co.edu.unal.hermes.modelo;

import java.util.List;
import java.util.Vector;

/**
 * Relaciona el investigador que no tiene relación con la Universidad 
 * proviene de una entidad externa y se presenta su proyecto. 
 */
public class InvestigadorExterno extends Investigador {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2558032337427637638L;
    
    /** The contrasena. */
    private Long contrasena;
    
    /** The grupos investigador ext. */
    private List gruposInvestigadorExt;
    /**
	 * Institucion de la cual proviene el investigador.
	 */	
    private Institucion institucion;
    private FuenteFinanciacion institucionInvestigador;
    private TipoFormacion tipoFormacion;
    
    
    /** The tel extension. */
    private String telExtension;
    
    
    /** The tipo investigador. */
    //Aurelio
    private String tipoInvestigador;
   
    /**
     * Instantiates a new investigador externo.
     */
    public InvestigadorExterno () {
    	gruposInvestigadorExt = new Vector();
	}


	/**
	 * Gets the contrasena.
	 *
	 * @return Returns the contrasena.
	 */
	public Long getContrasena() {
		return contrasena;
	}


	/* (non-Javadoc)
	 * @see co.edu.unal.hermes.modelo.Investigador#getGrupos()
	 */
	public List getGrupos() {
		return grupos;
	}

  
    /**
     * Gets the grupos investigador ext.
     *
     * @return the grupos investigador ext
     */
    public List getGruposInvestigadorExt() {
		return gruposInvestigadorExt;
	}
    
    /**
     * Gets the institucion.
     *
     * @return the institucion
     */
    public Institucion getInstitucion() {
        return institucion;
    }
    

	/**
	 * Gets the tel extension.
	 *
	 * @return the tel extension
	 */
	public String getTelExtension() {
		return telExtension;
	}


	/**
	 * Gets the tipo investigador.
	 *
	 * @return the tipo investigador
	 */
	public String getTipoInvestigador() {
		return tipoInvestigador;
	}


	/**
	 * Sets the contrasena.
	 *
	 * @param contrasena The contrasena to set.
	 */
	public void setContrasena(Long contrasena) {
		this.contrasena = contrasena;
	}
	
	/* (non-Javadoc)
	 * @see co.edu.unal.hermes.modelo.Investigador#setGrupos(java.util.List)
	 */
	public void setGrupos(List grupos) {
		this.grupos = grupos;
	}
	
	/**
	 * Sets the grupos investigador ext.
	 *
	 * @param gruposInvestigadorExt the new grupos investigador ext
	 */
	public void setGruposInvestigadorExt(List gruposInvestigadorExt) {
		this.gruposInvestigadorExt = gruposInvestigadorExt;
	}

	/**
	 * Sets the institucion.
	 *
	 * @param institucion the new institucion
	 */
	public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;        
    }

	/**
	 * Sets the tel extension.
	 *
	 * @param telExtension the new tel extension
	 */
	public void setTelExtension(String telExtension) {
		this.telExtension = telExtension;
	}

	/**
	 * Sets the tipo investigador.
	 *
	 * @param tipoInvestigador the new tipo investigador
	 */
	public void setTipoInvestigador(String tipoInvestigador) {
		this.tipoInvestigador = tipoInvestigador;
	}


	public TipoFormacion getTipoFormacion() {
		return tipoFormacion;
	}


	public void setTipoFormacion(TipoFormacion tipoFormacion) {
		this.tipoFormacion = tipoFormacion;
	}


	public FuenteFinanciacion getInstitucionInvestigador() {
		return institucionInvestigador;
	}


	public void setInstitucionInvestigador(FuenteFinanciacion institucionInvestigador) {
		this.institucionInvestigador = institucionInvestigador;
	}


}
