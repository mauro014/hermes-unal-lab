/*
 * Created on 27-oct-2006
 * @authorIng. Wilver Alexander Martínez Martínez - wam² - UN.
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * The Class ProyectoCarta.
 */
public class ProyectoCarta {

    /** The Constant ORDENADOR_TIPO_DECANO. */
    public static final String ORDENADOR_TIPO_DECANO = "DCO";
    
    /** The Constant ORDENADOR_TIPO_DECANA. */
    public static final String ORDENADOR_TIPO_DECANA = "DCA";

    /** The Constant ORDENADOR_TIPO_VICERRECTOR. */
    public static final String ORDENADOR_TIPO_VICERRECTOR = "VRO";
    
    /** The Constant ORDENADOR_TIPO_VICERRECTORA. */
    public static final String ORDENADOR_TIPO_VICERRECTORA = "VRA";
    
    public static final String ORDENADOR_TIPO_DECANO_ENCARGADO = "DCOe";
       
    public static final String ORDENADOR_TIPO_DECANA_ENCARGADA = "DCAe";
    
    /** The Constant ORDENADOR_TIPO_DECANO. */
    public static final String ORDENADOR_TIPO_DIRECTOR = "DTO";
    
    /** The Constant ORDENADOR_TIPO_DECANA. */
    public static final String ORDENADOR_TIPO_DIRECTORA = "DTA";

    /** The id. */
    Long id;

    /** The proyecto. */
    private Proyecto proyecto;

    /** The carta. */
    private TipoCarta carta;

    /** The año. */
    private String año;

    /** The seq. */
    private String seq;

    /** The sede. */
    private String sede;

    /** The responsable. */
    private Persona responsable;

    /** The responsable eliminacion. */
    private Persona responsableEliminacion;

    /** The estado carta. */
    private EstadoCarta estadoCarta;

    /** The id aval. */
    Long idAval;

    /** The id solicitud. */
    Long idSolicitud;

    /** The fecha generacion. */
    private Date fechaGeneracion;

    /** The index. */
    private int index;

    /** The fecha eliminacion. */
    private Date fechaEliminacion;

    /** The num acta inicio ext. */
    // Legalización proyectos externos
    private Long numActaInicioExt;

    /** The subtipo. */
    private Long subtipo;

    /** The subtipo nombre. */
    private String subtipoNombre;

    /** The tipo acto administrativo. */
    // Información del acto administrativo de aprobación
    private DominioDetalle tipoActoAdministrativo;

    /** The numero acto administrativo. */
    private String numeroActoAdministrativo;

    /** The dependencia. */
    private Dependencia dependencia;

    /** The fecha resolucion. */
    private Date fechaResolucion;
    
    /** The tipo ordenador. */
    private String tipoOrdenador;
    
    private String nuevoConsiderando;
    
    private Date fechaUltimaNotificacion;
    
    private Integer numNotificaciones;

    /**
     * Instantiates a new proyecto carta.
     */
    public ProyectoCarta() {

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
     * Gets the carta.
     *
     * @return the carta
     */
    public TipoCarta getCarta() {
        return carta;
    }

    /**
     * Sets the carta.
     *
     * @param carta
     *            the new carta
     */
    public void setCarta(TipoCarta carta) {
        this.carta = carta;
    }

    /**
     * Gets the fecha generacion.
     *
     * @return the fecha generacion
     */
    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    /**
     * Sets the fecha generacion.
     *
     * @param fechaGeneracion
     *            the new fecha generacion
     */
    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
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
     * Gets the estado carta.
     *
     * @return the estado carta
     */
    public EstadoCarta getEstadoCarta() {
        return estadoCarta;
    }

    /**
     * Sets the estado carta.
     *
     * @param estadoCarta
     *            the new estado carta
     */
    public void setEstadoCarta(EstadoCarta estadoCarta) {
        this.estadoCarta = estadoCarta;
    }

    /**
     * Gets the id aval.
     *
     * @return the id aval
     */
    public Long getIdAval() {
        return idAval;
    }

    /**
     * Sets the id aval.
     *
     * @param idAval
     *            the new id aval
     */
    public void setIdAval(Long idAval) {
        this.idAval = idAval;
    }

    /**
     * Gets the id solicitud.
     *
     * @return the id solicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Sets the id solicitud.
     *
     * @param idSolicitud
     *            the new id solicitud
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * Gets the año.
     *
     * @return the año
     */
    public String getAño() {
        return año;
    }

    /**
     * Sets the año.
     *
     * @param año
     *            the new año
     */
    public void setAño(String año) {
        this.año = año;
    }

    /**
     * Gets the seq.
     *
     * @return the seq
     */
    public String getSeq() {
        return seq;
    }

    /**
     * Sets the seq.
     *
     * @param seq
     *            the new seq
     */
    public void setSeq(String seq) {
        this.seq = seq;
    }

    /**
     * Gets the sede.
     *
     * @return the sede
     */
    public String getSede() {
        return sede;
    }

    /**
     * Sets the sede.
     *
     * @param sede
     *            the new sede
     */
    public void setSede(String sede) {
        this.sede = sede;
    }

    /**
     * Gets the num acta inicio ext.
     *
     * @return the num acta inicio ext
     */
    public Long getNumActaInicioExt() {
        return numActaInicioExt;
    }

    /**
     * Sets the num acta inicio ext.
     *
     * @param numActaInicioExt
     *            the new num acta inicio ext
     */
    public void setNumActaInicioExt(Long numActaInicioExt) {
        this.numActaInicioExt = numActaInicioExt;
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
     * Gets the responsable eliminacion.
     *
     * @return the responsable eliminacion
     */
    public Persona getResponsableEliminacion() {
        return responsableEliminacion;
    }

    /**
     * Sets the responsable eliminacion.
     *
     * @param responsableEliminacion
     *            the new responsable eliminacion
     */
    public void setResponsableEliminacion(Persona responsableEliminacion) {
        this.responsableEliminacion = responsableEliminacion;
    }

    /**
     * Gets the fecha eliminacion.
     *
     * @return the fecha eliminacion
     */
    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    /**
     * Sets the fecha eliminacion.
     *
     * @param fechaEliminacion
     *            the new fecha eliminacion
     */
    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    /**
     * Gets the subtipo.
     *
     * @return the subtipo
     */
    public Long getSubtipo() {
        return subtipo;
    }

    /**
     * Sets the subtipo.
     *
     * @param subtipo
     *            the subtipo to set
     */
    public void setSubtipo(Long subtipo) {
        this.subtipo = subtipo;
    }

    /**
     * Gets the subtipo nombre.
     *
     * @return the subtipoNombre
     */
    public String getSubtipoNombre() {
        return subtipoNombre;
    }

    /**
     * Sets the subtipo nombre.
     *
     * @param subtipoNombre
     *            the subtipoNombre to set
     */
    public void setSubtipoNombre(String subtipoNombre) {
        this.subtipoNombre = subtipoNombre;
    }

    /**
     * Gets the tipo acto administrativo.
     *
     * @return the tipoActoAdministrativo
     */
    public DominioDetalle getTipoActoAdministrativo() {
        return tipoActoAdministrativo;
    }

    /**
     * Sets the tipo acto administrativo.
     *
     * @param tipoActoAdministrativo
     *            the tipoActoAdministrativo to set
     */
    public void setTipoActoAdministrativo(DominioDetalle tipoActoAdministrativo) {
        this.tipoActoAdministrativo = tipoActoAdministrativo;
    }

    /**
     * Gets the numero acto administrativo.
     *
     * @return the numeroActoAdministrativo
     */
    public String getNumeroActoAdministrativo() {
        return numeroActoAdministrativo;
    }

    /**
     * Sets the numero acto administrativo.
     *
     * @param numeroActoAdministrativo
     *            the numeroActoAdministrativo to set
     */
    public void setNumeroActoAdministrativo(String numeroActoAdministrativo) {
        this.numeroActoAdministrativo = numeroActoAdministrativo;
    }

    /**
     * Gets the dependencia.
     *
     * @return the dependencia
     */
    public Dependencia getDependencia() {
        return dependencia;
    }

    /**
     * Sets the dependencia.
     *
     * @param dependencia
     *            the dependencia to set
     */
    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    /**
     * Gets the fecha resolucion.
     *
     * @return the fechaResolucion
     */
    public Date getFechaResolucion() {
        return fechaResolucion;
    }

    /**
     * Sets the fecha resolucion.
     *
     * @param fechaResolucion
     *            the fechaResolucion to set
     */
    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }
    
    /**
     * Obtener articulo dependencia.
     *
     * @param tipoOrdenador the tipo ordenador
     * @return the string
     */
    public static String obtenerArticuloDependencia(String tipoOrdenador){
        if (tipoOrdenador.equals(ProyectoCarta.ORDENADOR_TIPO_DECANO)
        		|| tipoOrdenador.equals(ProyectoCarta.ORDENADOR_TIPO_DECANO_ENCARGADO)
        		|| tipoOrdenador.equals(ProyectoCarta.ORDENADOR_TIPO_DECANA)
                || tipoOrdenador.equals(ProyectoCarta.ORDENADOR_TIPO_DECANA_ENCARGADA)) {
            // Facultades
            return "de la";
        } else if (tipoOrdenador.equals(ProyectoCarta.ORDENADOR_TIPO_VICERRECTOR)
        		|| tipoOrdenador.equals(ProyectoCarta.ORDENADOR_TIPO_DECANO_ENCARGADO)
                || tipoOrdenador.equals(ProyectoCarta.ORDENADOR_TIPO_VICERRECTORA)
                || tipoOrdenador.equals(ProyectoCarta.ORDENADOR_TIPO_DECANA_ENCARGADA)) {
            // Sedes
            return "de la sede";
        } else {
            // Institutos.
            return "del centro o instituto";
        }
    }

    /**
     * Gets the tipo ordenador.
     *
     * @return the tipoOrdenador
     */
    public String getTipoOrdenador() {
        return tipoOrdenador;
    }

    /**
     * Sets the tipo ordenador.
     *
     * @param tipoOrdenador the tipoOrdenador to set
     */
    public void setTipoOrdenador(String tipoOrdenador) {
        this.tipoOrdenador = tipoOrdenador;
    }

	public String getNuevoConsiderando() {
		return nuevoConsiderando;
	}

	public void setNuevoConsiderando(String nuevoConsiderando) {
		this.nuevoConsiderando = nuevoConsiderando;
	}

	public Date getFechaUltimaNotificacion() {
		return fechaUltimaNotificacion;
	}

	public void setFechaUltimaNotificacion(Date fechaUltimaNotificacion) {
		this.fechaUltimaNotificacion = fechaUltimaNotificacion;
	}

	public Integer getNumNotificaciones() {
		return numNotificaciones;
	}

	public void setNumNotificaciones(Integer numNotificaciones) {
		this.numNotificaciones = numNotificaciones;
	}

}
