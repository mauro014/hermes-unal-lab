
package co.edu.unal.hermes.modelo;

import java.util.HashSet;
import java.util.Set;

/**
 * The Class ModalidadFuenteFinanciacion.
 */
public class ModalidadFuenteFinanciacion {

    /** The Constant ID_MODALIDAD_FUENTE_FINANCIACION_FICHA. */
    public static final Long ID_MODALIDAD_FUENTE_FINANCIACION_FICHA = 889L;
    /** The id. */
    private Long id;

    /** The porcentaje. */
    private Double porcentaje;

    /** The modalidad. */
    private Modalidad modalidad;

    /** The fuente financiacion. */
    private FuenteFinanciacion fuenteFinanciacion;

    /** The borrar. */
    private boolean borrar;
    
    /** The incluir contrapartida. */
    private boolean incluirContrapartida;
    
    /** The arbol */
    private Long arbol;
    
    private Long montoMaximo;
    private Long montoMinimo;

    /** The rubros financiables arbol. */
    private Set<RubroFinanciable> rubrosFinanciablesArbol = new HashSet<RubroFinanciable>();

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
     * @param id
     *            the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the modalidad.
     *
     * @return the modalidad
     */
    public Modalidad getModalidad() {
        return modalidad;
    }

    /**
     * Sets the modalidad.
     *
     * @param modalidad
     *            the new modalidad
     */
    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * Gets the porcentaje.
     *
     * @return the porcentaje
     */
    public Double getPorcentaje() {
        return porcentaje;
    }

    /**
     * Sets the porcentaje.
     *
     * @param porcentaje
     *            the new porcentaje
     */
    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * Gets the fuente financiacion.
     *
     * @return the fuente financiacion
     */
    public FuenteFinanciacion getFuenteFinanciacion() {
        return fuenteFinanciacion;
    }

    /**
     * Sets the fuente financiacion.
     *
     * @param fuenteFinanciacion
     *            the new fuente financiacion
     */
    public void setFuenteFinanciacion(FuenteFinanciacion fuenteFinanciacion) {
        this.fuenteFinanciacion = fuenteFinanciacion;
    }

    /**
     * Checks if is borrar.
     *
     * @return true, if is borrar
     */
    public boolean isBorrar() {
        return borrar;
    }

    /**
     * Sets the borrar.
     *
     * @param borrar
     *            the new borrar
     */
    public void setBorrar(boolean borrar) {
        this.borrar = borrar;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if (!(o instanceof ModalidadFuenteFinanciacion)) {
            return false;
        }
        ModalidadFuenteFinanciacion mff = (ModalidadFuenteFinanciacion) o;
        if (mff.getFuenteFinanciacion().getId() == null || this.fuenteFinanciacion.getId() == null) {
            return false;
        }
        return mff.getFuenteFinanciacion().getId().equals(this.fuenteFinanciacion.getId());
    }

    /**
     * Gets the rubros financiables arbol.
     *
     * @return the rubros financiables arbol
     */
    public Set<RubroFinanciable> getRubrosFinanciablesArbol() {
        return rubrosFinanciablesArbol;
    }

    /**
     * Sets the rubros financiables arbol.
     *
     * @param rubrosFinanciablesArbol
     *            the new rubros financiables arbol
     */
    public void setRubrosFinanciablesArbol(Set<RubroFinanciable> rubrosFinanciablesArbol) {
        this.rubrosFinanciablesArbol = rubrosFinanciablesArbol;
    }

    /**
     * Checks if is incluir contrapartida.
     *
     * @return true, if is incluir contrapartida
     */
    public boolean isIncluirContrapartida() {
        return incluirContrapartida;
    }

    /**
     * Sets the incluir contrapartida.
     *
     * @param incluirContrapartida the new incluir contrapartida
     */
    public void setIncluirContrapartida(boolean incluirContrapartida) {
        this.incluirContrapartida = incluirContrapartida;
    }

	public Long getArbol() {
		return arbol;
	}

	public void setArbol(Long arbol) {
		this.arbol = arbol;
	}

	public Long getMontoMaximo() {
		return montoMaximo;
	}

	public void setMontoMaximo(Long montoMaximo) {
		this.montoMaximo = montoMaximo;
	}

	public Long getMontoMinimo() {
		return montoMinimo;
	}

	public void setMontoMinimo(Long montoMinimo) {
		this.montoMinimo = montoMinimo;
	}
}
