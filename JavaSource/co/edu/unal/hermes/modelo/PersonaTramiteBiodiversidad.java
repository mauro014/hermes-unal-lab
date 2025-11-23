/**
 * @author Martha Liliana Correa O.
 * @date 22/12/2016
 */

package co.edu.unal.hermes.modelo;

public class PersonaTramiteBiodiversidad implements java.io.Serializable {


	/**
     * 
     */
    private static final long serialVersionUID = -5436274822524455298L;
    private Long id;
	private InvestigadorInterno personaEncargada;
	private Dependencia dependencia;
	private TipoTramiteBiodiversidad tipoTramite; 

	
	/** default constructor */
	public PersonaTramiteBiodiversidad() {
		
	}
	
	public PersonaTramiteBiodiversidad(Long id) {
		this.setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    public TipoTramiteBiodiversidad getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(TipoTramiteBiodiversidad tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public InvestigadorInterno getPersonaEncargada() {
        return personaEncargada;
    }

    public void setPersonaEncargada(InvestigadorInterno personaEncargada) {
        this.personaEncargada = personaEncargada;
    }	
}