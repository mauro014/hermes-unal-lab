package co.edu.unal.hermes.vista.movilidad.aprobacion;

import java.util.Date;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.TipoMovilidad;

/**
 * The Class ConvocatoriaMovilidadVista.
 */
public class ConvocatoriaMovilidadVista {

    /** The nombre convocatoria padre. */
    private String nombreConvocatoriaPadre;

    /** The nombre modalidad. */
    private String nombreModalidad;

    /** The id solicitud. */
    private String idSolicitud;

    /** The fecha solicitud. */
    private Date fechaSolicitud;

    /** The tipo movilidad. */
    private TipoMovilidad tipoMovilidad;

    /** The nombre solicitante. */
    private String nombreSolicitante;

    /** The nombre archivo requisitos. */
    private String nombreArchivoRequisitos;

    /** The convocatoria. */
    private Convocatoria convocatoria;
    
    private String facultadRevisionFac;

    /**
     * Instantiates a new convocatoria movilidad vista.
     */
    public ConvocatoriaMovilidadVista() {
        super();
    }

    /**
     * Gets the nombre convocatoria padre.
     *
     * @return the nombre convocatoria padre
     */
    public String getNombreConvocatoriaPadre() {
        return nombreConvocatoriaPadre;
    }

    /**
     * Sets the nombre convocatoria padre.
     *
     * @param nombreConvocatoriaPadre
     *            the new nombre convocatoria padre
     */
    public void setNombreConvocatoriaPadre(String nombreConvocatoriaPadre) {
        this.nombreConvocatoriaPadre = nombreConvocatoriaPadre;
    }

    /**
     * Gets the nombre modalidad.
     *
     * @return the nombre modalidad
     */
    public String getNombreModalidad() {
        return nombreModalidad;
    }

    /**
     * Sets the nombre modalidad.
     *
     * @param nombreModalidad
     *            the new nombre modalidad
     */
    public void setNombreModalidad(String nombreModalidad) {
        this.nombreModalidad = nombreModalidad;
    }

    /**
     * Gets the id solicitud.
     *
     * @return the id solicitud
     */
    public String getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Sets the id solicitud.
     *
     * @param idSolicitud
     *            the new id solicitud
     */
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * Gets the fecha solicitud.
     *
     * @return the fecha solicitud
     */
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * Sets the fecha solicitud.
     *
     * @param fechaSolicitud
     *            the new fecha solicitud
     */
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * Gets the tipo movilidad.
     *
     * @return the tipo movilidad
     */
    public TipoMovilidad getTipoMovilidad() {
        return tipoMovilidad;
    }

    /**
     * Sets the tipo movilidad.
     *
     * @param tipoMovilidad
     *            the new tipo movilidad
     */
    public void setTipoMovilidad(TipoMovilidad tipoMovilidad) {
        this.tipoMovilidad = tipoMovilidad;
    }

    /**
     * Gets the nombre solicitante.
     *
     * @return the nombre solicitante
     */
    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    /**
     * Sets the nombre solicitante.
     *
     * @param nombreSolicitante
     *            the new nombre solicitante
     */
    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    /**
     * Gets the nombre archivo requisitos.
     *
     * @return the nombre archivo requisitos
     */
    public String getNombreArchivoRequisitos() {
        return nombreArchivoRequisitos;
    }

    /**
     * Sets the nombre archivo requisitos.
     *
     * @param nombreArchivoRequisitos
     *            the new nombre archivo requisitos
     */
    public void setNombreArchivoRequisitos(String nombreArchivoRequisitos) {
        this.nombreArchivoRequisitos = nombreArchivoRequisitos;
    }

    /**
     * Gets the convocatoria.
     *
     * @return the convocatoria
     */
    public Convocatoria getConvocatoria() {
        return convocatoria;
    }

    /**
     * Sets the convocatoria.
     *
     * @param convocatoria
     *            the convocatoria to set
     */
    public void setConvocatoria(Convocatoria convocatoria) {
        this.convocatoria = convocatoria;
    }

	public String getFacultadRevisionFac() {
		return facultadRevisionFac;
	}

	public void setFacultadRevisionFac(String facultadRevisionFac) {
		this.facultadRevisionFac = facultadRevisionFac;
	}


}