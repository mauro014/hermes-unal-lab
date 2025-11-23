/*
 * Created on 27-ene-2006
 */
package co.edu.unal.hermes.modelo;

import org.apache.commons.lang.StringUtils;

/**
 * The Class ProyectoRequisito.
 *
 * @author jpduqueg
 */
public class ProyectoRequisito implements Comparable<ProyectoRequisito> {

    /** The id. */
    private Long id;

    /** The proyecto. */
    private Proyecto proyecto;

    /** The requisito. */
    private TipoRequisito requisito;

    /** The cumplido. */
    private String cumplido;

    /** The comentario. */
    private String comentario;

    /** The responsable id. */
    private String responsableId;

    /** The responsable tipo documento. */
    private String responsableTipoDocumento;

    // Este atributo no se mapea en la base de datos
    // Sirve para identificar el producto a asociar cuando
    /** The index. */
    // la cantidad es mayor a 1
    private int index;

    /** The entregado. */
    private boolean entregado;

    /**
     * Instantiates a new proyecto requisito.
     */
    public ProyectoRequisito() {
    }

    /**
     * Instantiates a new proyecto requisito.
     *
     * @param pId
     *            the id
     */
    public ProyectoRequisito(Long pId) {
        this.id = pId;
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
     * @param id
     *            the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the proyecto.
     *
     * @return the proyecto
     */
    public Proyecto getProyecto() {
        return proyecto;
    }

    /**
     * Sets the proyecto.
     *
     * @param proyecto
     *            the new proyecto
     */
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * Gets the requisito.
     *
     * @return the requisito
     */
    public TipoRequisito getRequisito() {
        return requisito;
    }

    /**
     * Sets the requisito.
     *
     * @param requisito
     *            the new requisito
     */
    public void setRequisito(TipoRequisito requisito) {
        this.requisito = requisito;
    }

    /**
     * Gets the cumplido.
     *
     * @return the cumplido
     */
    public String getCumplido() {
        return cumplido;
    }

    /**
     * Sets the cumplido.
     *
     * @param cumplido
     *            the new cumplido
     */
    public void setCumplido(String cumplido) {
        this.cumplido = cumplido;
    }

    /**
     * Gets the index.
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the index.
     *
     * @param index
     *            the new index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Checks if is entregado.
     *
     * @return true, if is entregado
     */
    public boolean isEntregado() {
        return entregado;
    }

    /**
     * Sets the entregado.
     *
     * @param entregado
     *            the new entregado
     */
    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    /**
     * Gets the comentario.
     *
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Sets the comentario.
     *
     * @param comentario
     *            the new comentario
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Sets the cumplido checkbox.
     *
     * @param cumplidoCheckbox
     *            the new cumplido checkbox
     */
    public void setCumplidoCheckbox(boolean cumplidoCheckbox) {
        if (cumplidoCheckbox) {
            cumplido = "S";
        } else {
            cumplido = "N";
        }
    }

    /**
     * Checks if is cumplido checkbox.
     *
     * @return true, if is cumplido checkbox
     */
    public boolean isCumplidoCheckbox() {
        return StringUtils.isNotEmpty(cumplido) && "S".equals(cumplido);
    }

    /**
     * Gets the responsable id.
     *
     * @return the responsable id
     */
    public String getResponsableId() {
        return responsableId;
    }

    /**
     * Sets the responsable id.
     *
     * @param responsableId
     *            the new responsable id
     */
    public void setResponsableId(String responsableId) {
        this.responsableId = responsableId;
    }

    /**
     * Gets the responsable tipo documento.
     *
     * @return the responsable tipo documento
     */
    public String getResponsableTipoDocumento() {
        return responsableTipoDocumento;
    }

    /**
     * Sets the responsable tipo documento.
     *
     * @param responsableTipoDocumento
     *            the new responsable tipo documento
     */
    public void setResponsableTipoDocumento(String responsableTipoDocumento) {
        this.responsableTipoDocumento = responsableTipoDocumento;
    }
    
    @Override
    public int compareTo(ProyectoRequisito o) {
        if (requisito.getId() < o.getRequisito().getId()) {
            return -1;
        }
        if (requisito.getId() > o.getRequisito().getId()) {
            return 1;
        }
        return 0;
    }

}
