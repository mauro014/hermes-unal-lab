/*
 * Created on 27-octubre-2024
 */
package co.edu.unal.hermes.modelo;

/**
 * The Class ColeccionRequisito.
 *
 * @author martha correa
 */
public class ColeccionRequisito {

    /** The id. */
    private Long id;

    /** The proyecto. */
    private ColeccionGestion gestion;

    /** The requisito. */
    private DominioDetalle requisito;

    /** The cumplido. */
    private boolean cumplido;



    /**
     * Instantiates a new proyecto requisito.
     */
    public ColeccionRequisito() {
    }

   
	public DominioDetalle getRequisito() {
		return requisito;
	}

	public void setRequisito(DominioDetalle requisito) {
		this.requisito = requisito;
	}

	public boolean isCumplido() {
		return cumplido;
	}

	public void setCumplido(boolean cumplido) {
		this.cumplido = cumplido;
	}

	public ColeccionGestion getGestion() {
		return gestion;
	}

	public void setGestion(ColeccionGestion gestion) {
		this.gestion = gestion;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

  
}
