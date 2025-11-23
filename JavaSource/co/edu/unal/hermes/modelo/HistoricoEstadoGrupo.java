package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * The Class HistoricoEstadoGrupo.
 */
public class HistoricoEstadoGrupo {

    /** The id. */
    private Long id;

    /** The estado grupo. */
    private EstadoGrupo estadoGrupo;

    /** The estado grupo colciencias. */
    private EstadoGrupoColciencias estadoGrupoColciencias;

    /** The grupo. */
    private Grupo grupo;

    /** The fecha. */
    private Date fecha;

    /** The responsable. */
    private Persona responsable;
    
    /** The justificacion. */
    private String justificacion;
    
    private String archivoCambio;


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
     * Gets the estado grupo.
     *
     * @return the estado grupo
     */
    public EstadoGrupo getEstadoGrupo() {
        return estadoGrupo;
    }

    /**
     * Sets the estado grupo.
     *
     * @param estadoGrupo
     *            the new estado grupo
     */
    public void setEstadoGrupo(EstadoGrupo estadoGrupo) {
        this.estadoGrupo = estadoGrupo;
    }

    /**
     * Gets the estado grupo colciencias.
     *
     * @return the estado grupo colciencias
     */
    public EstadoGrupoColciencias getEstadoGrupoColciencias() {
        return estadoGrupoColciencias;
    }

    /**
     * Sets the estado grupo colciencias.
     *
     * @param estadoGrupoColciencias
     *            the new estado grupo colciencias
     */
    public void setEstadoGrupoColciencias(EstadoGrupoColciencias estadoGrupoColciencias) {
        this.estadoGrupoColciencias = estadoGrupoColciencias;
    }

    /**
     * Gets the grupo.
     *
     * @return the grupo
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * Sets the grupo.
     *
     * @param grupo
     *            the new grupo
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
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
     * @param fecha
     *            the new fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Gets the responsable.
     *
     * @return the responsable
     */
    public Persona getResponsable() {
        return responsable;
    }

    /**
     * Sets the responsable.
     *
     * @param responsable
     *            the new responsable
     */
    public void setResponsable(Persona responsable) {
        this.responsable = responsable;
    }

	/**
	 * @return the justificacion
	 */
	public String getJustificacion() {
		return justificacion;
	}

	/**
	 * @param justificacion the justificacion to set
	 */
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getArchivoCambio() {
		return archivoCambio;
	}

	public void setArchivoCambio(String archivoCambio) {
		this.archivoCambio = archivoCambio;
	}

}
