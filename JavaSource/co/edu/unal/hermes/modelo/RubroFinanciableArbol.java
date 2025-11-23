package co.edu.unal.hermes.modelo;

import java.util.HashSet;
import java.util.Set;

/**
 * The Class RubroFinanciableArbol.
 */
public class RubroFinanciableArbol {

    /** The Constant TOTAL. */
    public static final int TOTAL = 1;

    /** The Constant CONTRAPARTIDA. */
    public static final Long CONTRAPARTIDA = 2L;

    /** The id. */
    private Long id;

    /** The modalidad fuente financiacion. */
    private ModalidadFuenteFinanciacion modalidadFuenteFinanciacion;

    /** The tipo rubro. */
    private TipoRubro tipoRubro;

    /** The padre. */
    private Long padre;

    /** The tipo. */
    private Long tipo;

    /** The tipo. */
    private Long orden;

    /** The hijos. */
    private Set<RubroFinanciableArbol> hijos = new HashSet<RubroFinanciableArbol>();

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
     * Gets the modalidad fuente financiacion.
     *
     * @return the modalidad fuente financiacion
     */
    public ModalidadFuenteFinanciacion getModalidadFuenteFinanciacion() {
        return modalidadFuenteFinanciacion;
    }

    /**
     * Sets the modalidad fuente financiacion.
     *
     * @param modalidadFuenteFinanciacion
     *            the new modalidad fuente financiacion
     */
    public void setModalidadFuenteFinanciacion(ModalidadFuenteFinanciacion modalidadFuenteFinanciacion) {
        this.modalidadFuenteFinanciacion = modalidadFuenteFinanciacion;
    }

    /**
     * Gets the tipo rubro.
     *
     * @return the tipo rubro
     */
    public TipoRubro getTipoRubro() {
        return tipoRubro;
    }

    /**
     * Sets the tipo rubro.
     *
     * @param tipoRubro
     *            the new tipo rubro
     */
    public void setTipoRubro(TipoRubro tipoRubro) {
        this.tipoRubro = tipoRubro;
    }

    /**
     * Gets the padre.
     *
     * @return the padre
     */
    public Long getPadre() {
        return padre;
    }

    /**
     * Sets the padre.
     *
     * @param padre
     *            the new padre
     */
    public void setPadre(Long padre) {
        this.padre = padre;
    }

    /**
     * Gets the tipo.
     *
     * @return the tipo
     */
    public Long getTipo() {
        return tipo;
    }

    /**
     * Sets the tipo.
     *
     * @param tipo
     *            the new tipo
     */
    public void setTipo(Long tipo) {
        this.tipo = tipo;
    }

    /**
     * Gets the orden.
     *
     * @return the orden
     */
    public Long getOrden() {
        return orden;
    }

    /**
     * Sets the orden.
     *
     * @param orden
     *            the new orden
     */
    public void setOrden(Long orden) {
        this.orden = orden;
    }

    /**
     * Gets the hijos.
     *
     * @return the hijos
     */
    public Set<RubroFinanciableArbol> getHijos() {
        return hijos;
    }

    /**
     * Sets the hijos.
     *
     * @param hijos the new hijos
     */
    public void setHijos(Set<RubroFinanciableArbol> hijos) {
        this.hijos = hijos;
    }

}
