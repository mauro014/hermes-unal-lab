/********************************************************************************
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Clase    	: co.edu.unal.hermes.modelo.seguimiento.Solicitud
Objetivo 	: Clase POJO para  la  tabla HER_SOLICITUD, en la cual se  encuentran 
			  las  solicitudes  realiizadas  por  parte de los investigadores con 
			  respecto a un proyecto de investigación.
Creación	: Agosto 23 de 2007

Modificación: Septiembre 21 de 2007
Autor 		: Oscar Javier Castro Medina - Consult Soft S.A.
Detalle		: Se adiciona relacion para la via en que se realiza la solicitud.

Modificación: 
Autor 		: 
Detalle		: 
********************************************************************************/

package co.edu.unal.hermes.modelo.seguimiento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Tipos;

/**
 * The Class Solicitud.
 */
public class Solicitud implements Serializable, Comparable<Solicitud> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant BORRADA. */
    public static final String BORRADA = "B";

    /** The Constant GUARDADA. */
    public static final String GUARDADA = "G";

    /** The Constant TRAMITE. */
    public static final String TRAMITE = "T";

    /** The Constant PENDIENTE. */
    public static final String PENDIENTE = "P";

    /** The Constant RESUELTO. */
    public static final String RESUELTO = "R";

    /** The Constant DEVUELTO_CORRECCIONES. */
    public static final String DEVUELTO_CORRECCIONES = "C";

    /** The Constant ALERTA_CERRADA. */
    public static final String ALERTA_CERRADA = "C";

    /** The Constant APROBADA. */
    public static final String APROBADA = "A";

    /** The id. */
    private Long id;

    /** The fecha. */
    private Date fecha;

    /** The fecha eliminacion. */
    private Date fechaEliminacion;

    /** The respuesta. */
    private String respuesta;

    /** The vista UA. */
    private String vistaUA;

    /** The descripcion. */
    private String descripcion;

    /** The proyecto. */
    private Proyecto proyecto;

    /** The tipo solicitud. */
    private TipoSolicitud tipoSolicitud;

    /** The tipo via solicitud. */
    private TipoViaSolicitud tipoViaSolicitud;

    /** The numero especimenes. */
    private Long numeroEspecimenes;

    /** The familia taxonomica. */
    private String familiaTaxonomica;

    /** The ubicacion. */
    private String ubicacion;

    /** The sede. */
    private String sede;

    /** The detalle cambio rubro. */
    private Set<DetalleCambioRubro> detalleCambioRubro = new HashSet<DetalleCambioRubro>();

    /** The tramites. */
    private Set<TramiteSolicitud> tramites = new HashSet<TramiteSolicitud>();

    /** The documentos. */
    private Set<SolicitudDocumento> documentos = new HashSet<SolicitudDocumento>();
    
    /** The documentos. */
    private Set<SolicitudCiudad> ciudades = new HashSet<SolicitudCiudad>();

    /** The detalle solicitud. */
    private DetalleSolicitud detalleSolicitud;

    /** The responsable. */
    private Persona responsable;

    /** The investigador principal propuesto. */
    private SolicitudInvestigador investigadorPrincipalPropuesto;

    /** The investigador principal anterior. */
    private SolicitudInvestigador investigadorPrincipalAnterior;

    /** The lita solicitudes investigador pendientes. */
    private List<SolicitudInvestigador> litaSolicitudesInvestigadorPendientes;

    /** The lita solicitudes investigador procesadas. */
    private List<SolicitudInvestigador> litaSolicitudesInvestigadorProcesadas;

    /** The lista solicitudes investigadores. */
    private List<SolicitudInvestigador> listaSolicitudesInvestigadores;
    
    /** The lita solicitudes investigador pendientes. */
    private List<SolicitudProrrogaInvestigador> listaSolicitudesProrrogaInvestigadorPendientes;

    /** The lita solicitudes investigador procesadas. */
    private List<SolicitudProrrogaInvestigador> listaSolicitudesProrrogaInvestigadorProcesadas;

    /** The lista solicitudes investigadores. */
    private List<SolicitudProrrogaInvestigador> listaSolicitudesProrrogaInvestigadores;

    /** The fecha aprobacion. */
    private Date fechaAprobacion;
    
    /** The fecha inicio recoleccion. */
    private Date fechaInicioRecoleccion;

    /** The fecha esperada terminacion. */
    private Date fechaEsperadaTerminacion;

    /** The tipo cambio contenido. */
    private TipoCambioContenido tipoCambioContenido;

    /** The descripcion cambio contenido. */
    private String descripcionCambioContenido;

    /** The estado procesamiento solicitud. */
    private String estadoProcesamientoSolicitud;

    /** The fecha reactivacion. */
    private Date fechaReactivacion;

    /** The fecha procesamiento. */
    private Date fechaProcesamiento;

    /** The institucion apoyo biodiversidad. */
    private DominioDetalle institucionApoyoBiodiversidad;

    /** The otra institucion nacional apoyo. */
    private String otraInstitucionNacionalApoyo;

    /** The tiene carta. */
    private boolean tieneCarta;

    /** The respuesta anterior. */
    private String respuestaAnterior;

    /** The mensaje reactivacion. */
    private String mensajeReactivacion;

    /** The nueva. */
    private boolean nueva = true;
    
    private Tipos tipoSolLab;
    
    private SolicitudAdicionPresupuestal adicionPresupuesto;
    
    private String radicadoAnla;
    private Date fechaRadicadoAnla;
    

    /**
     * Gets the nueva.
     *
     * @return the nueva
     */
    public boolean getNueva() {
        return nueva;
    }

    /**
     * Sets the nueva.
     *
     * @param nueva
     *            the new nueva
     */
    public void setNueva(boolean nueva) {
        this.nueva = nueva;
    }

    /**
     * Gets the detalle solicitud.
     *
     * @return the detalle solicitud
     */
    public DetalleSolicitud getDetalleSolicitud() {
        return detalleSolicitud;
    }

    /**
     * Sets the detalle solicitud.
     *
     * @param detalleSolicitud
     *            the new detalle solicitud
     */
    public void setDetalleSolicitud(DetalleSolicitud detalleSolicitud) {
        this.detalleSolicitud = detalleSolicitud;
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
        this.nueva = false;
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
     * Gets the respuesta.
     *
     * @return the respuesta
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * Sets the respuesta.
     *
     * @param respuesta
     *            the new respuesta
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * Gets the descripcion.
     *
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the descripcion.
     *
     * @param descripcion
     *            the new descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
     * Gets the tipo solicitud.
     *
     * @return the tipo solicitud
     */
    public TipoSolicitud getTipoSolicitud() {
        return tipoSolicitud;
    }

    /**
     * Sets the tipo solicitud.
     *
     * @param tipoSolicitud
     *            the new tipo solicitud
     */
    public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    /**
     * Gets the tipo via solicitud.
     *
     * @return the tipo via solicitud
     */
    public TipoViaSolicitud getTipoViaSolicitud() {
        return tipoViaSolicitud;
    }

    /**
     * Sets the tipo via solicitud.
     *
     * @param tipoViaSolicitud
     *            the new tipo via solicitud
     */
    public void setTipoViaSolicitud(TipoViaSolicitud tipoViaSolicitud) {
        this.tipoViaSolicitud = tipoViaSolicitud;
    }

    /**
     * Gets the tramites.
     *
     * @return the tramites
     */
    public Set<TramiteSolicitud> getTramites() {
        return tramites;
    }

    /**
     * Gets the lista tramites.
     *
     * @return the lista tramites
     */
    public List<TramiteSolicitud> getListaTramites() {
        List<TramiteSolicitud> listaTramites = new ArrayList<TramiteSolicitud>();
        if(tramites != null) {
            listaTramites.addAll(tramites);
        }
        return listaTramites;
    }

    /**
     * Sets the tramites.
     *
     * @param tramites
     *            the new tramites
     */
    public void setTramites(Set<TramiteSolicitud> tramites) {
        this.tramites = tramites;
    }

    /**
     * Adicionar tramite.
     *
     * @param tramiteSolicitud
     *            the tramite solicitud
     */
    public void adicionarTramite(TramiteSolicitud tramiteSolicitud) {
        if (tramites == null) {
            tramites = new HashSet<TramiteSolicitud>();
        }
        tramites.add(tramiteSolicitud);
    }

    /**
     * Gets the tamaño lista tramite.
     *
     * @return the tamaño lista tramite
     */
    public int getTamañoListaTramite() {
        if (this.tramites != null && this.tramites.size() > 0) {
            return tramites.size();
        }
        return 0;
    }

    /**
     * Gets the documentos.
     *
     * @return the documentos
     */
    public Set<SolicitudDocumento> getDocumentos() {
        return documentos;
    }

    /**
     * Sets the documentos.
     *
     * @param documentos
     *            the new documentos
     */
    public void setDocumentos(Set<SolicitudDocumento> documentos) {
        this.documentos = documentos;
    }

    /**
     * Gets the lista documentos.
     *
     * @return the lista documentos
     */
    public List<SolicitudDocumento> getListaDocumentos() {
        List<SolicitudDocumento> listaDocumentos = new ArrayList<SolicitudDocumento>();
        if (documentos != null) {
            listaDocumentos.addAll(documentos);
        }
        return listaDocumentos;
    }
    
    /**
     * Gets the lista ciudades.
     *
     * @return the lista ciudades
     */
    public List<SolicitudCiudad> getListaCiudades() {
        List<SolicitudCiudad> listaCiudades = new ArrayList<SolicitudCiudad>();
        if (ciudades != null) {
        	listaCiudades.addAll(ciudades);
        }
        return listaCiudades;
    }

    /**
     * Gets the detalle cambio rubro.
     *
     * @return the detalle cambio rubro
     */
    public Set<DetalleCambioRubro> getDetalleCambioRubro() {
        return detalleCambioRubro;
    }
    
    public List<DetalleCambioRubro> getListaDetalleCambioRubro(){
        List<DetalleCambioRubro> listaDetalleCambioRubro = new ArrayList<DetalleCambioRubro>();
        if(detalleCambioRubro != null) {
            listaDetalleCambioRubro.addAll(detalleCambioRubro);
        }
        return listaDetalleCambioRubro;
    }

    /**
     * Sets the detalle cambio rubro.
     *
     * @param detalleCambioRubro
     *            the new detalle cambio rubro
     */
    public void setDetalleCambioRubro(Set<DetalleCambioRubro> detalleCambioRubro) {
        this.detalleCambioRubro = detalleCambioRubro;
    }

    /**
     * Gets the tamaño lista detalle rubro.
     *
     * @return the tamaño lista detalle rubro
     */
    public int getTamañoListaDetalleRubro() {
        if (this.detalleCambioRubro != null) {
            return detalleCambioRubro.size();
        }
        return 0;
    }

    /**
     * Gets the vista UA.
     *
     * @return the vista UA
     */
    public String getVistaUA() {
        return vistaUA;
    }

    /**
     * Sets the vista UA.
     *
     * @param vistaUA
     *            the new vista UA
     */
    public void setVistaUA(String vistaUA) {
        this.vistaUA = vistaUA;
    }

    /**
     * Sets the numero especimenes.
     *
     * @param numeroEspecimenes
     *            the new numero especimenes
     */
    public void setNumeroEspecimenes(Long numeroEspecimenes) {
        this.numeroEspecimenes = numeroEspecimenes;
    }

    /**
     * Gets the numero especimenes.
     *
     * @return the numero especimenes
     */
    public Long getNumeroEspecimenes() {
        return numeroEspecimenes;
    }

    /**
     * Sets the familia taxonomica.
     *
     * @param familiaTaxonomica
     *            the new familia taxonomica
     */
    public void setFamiliaTaxonomica(String familiaTaxonomica) {
        this.familiaTaxonomica = familiaTaxonomica;
    }

    /**
     * Gets the familia taxonomica.
     *
     * @return the familia taxonomica
     */
    public String getFamiliaTaxonomica() {
        return familiaTaxonomica;
    }

    /**
     * Sets the ubicacion.
     *
     * @param ubicacion
     *            the new ubicacion
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * Gets the ubicacion.
     *
     * @return the ubicacion
     */
    public String getUbicacion() {
        return ubicacion;
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
     * Gets the sede.
     *
     * @return the sede
     */
    public String getSede() {
        return sede;
    }

    /**
     * Gets the respuesta tramite.
     *
     * @return the respuesta tramite
     */
    public String getRespuestaTramite() {
        String resupuesta = "";
        if (tramites != null) {
            Iterator<TramiteSolicitud> it = tramites.iterator();
            while (it.hasNext()) {
                if (resupuesta.length() > 0) {
                    respuesta += " * ";
                }
                resupuesta += it.next().getDescripcion();
            }
        }
        return resupuesta;
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
     * Gets the fecha aprobacion.
     *
     * @return the fecha aprobacion
     */
    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    /**
     * Sets the fecha aprobacion.
     *
     * @param fechaAprobacion
     *            the new fecha aprobacion
     */
    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    /**
     * Gets the investigador principal propuesto.
     *
     * @return the investigadorPrincipalPropuesto
     */
    public SolicitudInvestigador getInvestigadorPrincipalPropuesto() {
        return investigadorPrincipalPropuesto;
    }

    /**
     * Sets the investigador principal propuesto.
     *
     * @param investigadorPrincipalPropuesto
     *            the investigadorPrincipalPropuesto to set
     */
    public void setInvestigadorPrincipalPropuesto(SolicitudInvestigador investigadorPrincipalPropuesto) {
        this.investigadorPrincipalPropuesto = investigadorPrincipalPropuesto;
    }

    /**
     * Gets the investigador principal anterior.
     *
     * @return the investigadorPrincipalAnterior
     */
    public SolicitudInvestigador getInvestigadorPrincipalAnterior() {
        return investigadorPrincipalAnterior;
    }

    /**
     * Sets the investigador principal anterior.
     *
     * @param investigadorPrincipalAnterior
     *            the investigadorPrincipalAnterior to set
     */
    public void setInvestigadorPrincipalAnterior(SolicitudInvestigador investigadorPrincipalAnterior) {
        this.investigadorPrincipalAnterior = investigadorPrincipalAnterior;
    }

    /**
     * Gets the fecha esperada terminacion.
     *
     * @return the fechaEsperadaTerminacion
     */
    public Date getFechaEsperadaTerminacion() {
        return fechaEsperadaTerminacion;
    }

    /**
     * Sets the fecha esperada terminacion.
     *
     * @param fechaEsperadaTerminacion
     *            the fechaEsperadaTerminacion to set
     */
    public void setFechaEsperadaTerminacion(Date fechaEsperadaTerminacion) {
        this.fechaEsperadaTerminacion = fechaEsperadaTerminacion;
    }

    /**
     * Gets the lita solicitudes investigador pendientes.
     *
     * @return the litaSolicitudesInvestigador
     */
    public List<SolicitudInvestigador> getLitaSolicitudesInvestigadorPendientes() {
        return litaSolicitudesInvestigadorPendientes;
    }

    /**
     * Sets the lita solicitudes investigador pendientes.
     *
     * @param litaSolicitudesInvestigador
     *            the litaSolicitudesInvestigador to set
     */
    public void setLitaSolicitudesInvestigadorPendientes(List<SolicitudInvestigador> litaSolicitudesInvestigador) {
        this.litaSolicitudesInvestigadorPendientes = litaSolicitudesInvestigador;
    }

    /**
     * Gets the lita solicitudes investigador procesadas.
     *
     * @return the litaSolicitudesInvestigadorProcesadas
     */
    public List<SolicitudInvestigador> getLitaSolicitudesInvestigadorProcesadas() {
        return litaSolicitudesInvestigadorProcesadas;
    }

    /**
     * Sets the lita solicitudes investigador procesadas.
     *
     * @param litaSolicitudesInvestigadorProcesadas
     *            the litaSolicitudesInvestigadorProcesadas to set
     */
    public void setLitaSolicitudesInvestigadorProcesadas(
            List<SolicitudInvestigador> litaSolicitudesInvestigadorProcesadas) {
        this.litaSolicitudesInvestigadorProcesadas = litaSolicitudesInvestigadorProcesadas;
    }

    /**
     * Gets the lista solicitudes investigadores.
     *
     * @return the litaSolicitudesInvestigadores
     */
    public List<SolicitudInvestigador> getListaSolicitudesInvestigadores() {
        return listaSolicitudesInvestigadores;
    }

    /**
     * Sets the lista solicitudes investigadores.
     *
     * @param litaSolicitudesInvestigadores
     *            the litaSolicitudesInvestigadores to set
     */
    public void setListaSolicitudesInvestigadores(List<SolicitudInvestigador> litaSolicitudesInvestigadores) {
        this.listaSolicitudesInvestigadores = litaSolicitudesInvestigadores;
    }

    /**
     * Gets the tipo cambio contenido.
     *
     * @return the tipoCambioContenido
     */
    public TipoCambioContenido getTipoCambioContenido() {
        return tipoCambioContenido;
    }

    /**
     * Sets the tipo cambio contenido.
     *
     * @param tipoCambioContenido
     *            the tipoCambioContenido to set
     */
    public void setTipoCambioContenido(TipoCambioContenido tipoCambioContenido) {
        this.tipoCambioContenido = tipoCambioContenido;
    }

    /**
     * Gets the descripcion cambio contenido.
     *
     * @return the descripcionCambioContenido
     */
    public String getDescripcionCambioContenido() {
        return descripcionCambioContenido;
    }

    /**
     * Sets the descripcion cambio contenido.
     *
     * @param descripcionCambioContenido
     *            the descripcionCambioContenido to set
     */
    public void setDescripcionCambioContenido(String descripcionCambioContenido) {
        this.descripcionCambioContenido = descripcionCambioContenido;
    }

    /**
     * Gets the estado procesamiento solicitud.
     *
     * @return the estadoProcesamientoSolicitud
     */
    public String getEstadoProcesamientoSolicitud() {
        return estadoProcesamientoSolicitud;
    }

    /**
     * Sets the estado procesamiento solicitud.
     *
     * @param estadoProcesamientoSolicitud
     *            the estadoProcesamientoSolicitud to set
     */
    public void setEstadoProcesamientoSolicitud(String estadoProcesamientoSolicitud) {
        this.estadoProcesamientoSolicitud = estadoProcesamientoSolicitud;
    }

    /**
     * Gets the fecha reactivacion.
     *
     * @return the fechaReactivacion
     */
    public Date getFechaReactivacion() {
        return fechaReactivacion;
    }

    /**
     * Sets the fecha reactivacion.
     *
     * @param fechaReactivacion
     *            the fechaReactivacion to set
     */
    public void setFechaReactivacion(Date fechaReactivacion) {
        this.fechaReactivacion = fechaReactivacion;
    }

    /**
     * Gets the fecha procesamiento.
     *
     * @return the fechaProcesamiento
     */
    public Date getFechaProcesamiento() {
        return fechaProcesamiento;
    }

    /**
     * Sets the fecha procesamiento.
     *
     * @param fechaProcesamiento
     *            the fechaProcesamiento to set
     */
    public void setFechaProcesamiento(Date fechaProcesamiento) {
        this.fechaProcesamiento = fechaProcesamiento;
    }

    /**
     * Gets the institucion apoyo biodiversidad.
     *
     * @return the institucion apoyo biodiversidad
     */
    public DominioDetalle getInstitucionApoyoBiodiversidad() {
        return institucionApoyoBiodiversidad;
    }

    /**
     * Sets the institucion apoyo biodiversidad.
     *
     * @param institucionApoyoBiodiversidad
     *            the new institucion apoyo biodiversidad
     */
    public void setInstitucionApoyoBiodiversidad(DominioDetalle institucionApoyoBiodiversidad) {
        this.institucionApoyoBiodiversidad = institucionApoyoBiodiversidad;
    }

    /**
     * Gets the otra institucion nacional apoyo.
     *
     * @return the otra institucion nacional apoyo
     */
    public String getOtraInstitucionNacionalApoyo() {
        return otraInstitucionNacionalApoyo;
    }

    /**
     * Sets the otra institucion nacional apoyo.
     *
     * @param otraInstitucionNacionalApoyo
     *            the new otra institucion nacional apoyo
     */
    public void setOtraInstitucionNacionalApoyo(String otraInstitucionNacionalApoyo) {
        this.otraInstitucionNacionalApoyo = otraInstitucionNacionalApoyo;
    }

    /**
     * Checks if is tiene carta.
     *
     * @return the tieneCarta
     */
    public boolean isTieneCarta() {
        return tieneCarta;
    }

    /**
     * Sets the tiene carta.
     *
     * @param tieneCarta
     *            the tieneCarta to set
     */
    public void setTieneCarta(boolean tieneCarta) {
        this.tieneCarta = tieneCarta;
    }

    /**
     * Gets the respuesta anterior.
     *
     * @return the respuestaAnterior
     */
    public String getRespuestaAnterior() {
        return respuestaAnterior;
    }

    /**
     * Sets the respuesta anterior.
     *
     * @param respuestaAnterior
     *            the respuestaAnterior to set
     */
    public void setRespuestaAnterior(String respuestaAnterior) {
        this.respuestaAnterior = respuestaAnterior;
    }

    /**
     * Gets the mensaje reactivacion.
     *
     * @return the mensajeReactivacion
     */
    public String getMensajeReactivacion() {
        return mensajeReactivacion;
    }

    /**
     * Sets the mensaje reactivacion.
     *
     * @param mensajeReactivacion
     *            the mensajeReactivacion to set
     */
    public void setMensajeReactivacion(String mensajeReactivacion) {
        this.mensajeReactivacion = mensajeReactivacion;
    }
    
	public SolicitudAdicionPresupuestal getAdicionPresupuesto() {
		return adicionPresupuesto;
	}

	public void setAdicionPresupuesto(
			SolicitudAdicionPresupuestal adicionPresupuesto) {
		this.adicionPresupuesto = adicionPresupuesto;
	}

	public Tipos getTipoSolLab() {
		return tipoSolLab;
	}

	public void setTipoSolLab(Tipos tipoSolLab) {
		this.tipoSolLab = tipoSolLab;
	}

	/*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Solicitud o2) {
        return (int) (this.getId() - o2.getId());
    }
    
    public void setSolicitudInvestigadorPrincipal(List<SolicitudInvestigador> solicitudesInvestigador){
        if(solicitudesInvestigador != null){
            Iterator<SolicitudInvestigador> i = solicitudesInvestigador.iterator();
            while(i.hasNext()){
                SolicitudInvestigador solicitudInvestigador = i.next();
                if((SolicitudInvestigador.AGREGAR.equals(solicitudInvestigador.getTipoSolicitud()) || SolicitudInvestigador.CAMBIAR_DIRECTOR_A_COINVESTIGADOR.equals(solicitudInvestigador.getTipoSolicitud())) && (solicitudInvestigador.getFechaBorrado()==null)){
                    this.setInvestigadorPrincipalPropuesto(solicitudInvestigador);
                }
                else if(solicitudInvestigador.getFechaProcesamiento() != null){
                    this.setInvestigadorPrincipalAnterior(solicitudInvestigador);
                }
            }
        }
    }
    
    public void setSolicitudesInvestigadorIntegranes(List<SolicitudInvestigador> solicitudesInvestigador) {
        
        this.setLitaSolicitudesInvestigadorPendientes(null);
        this.setLitaSolicitudesInvestigadorProcesadas(null);
        this.setListaSolicitudesInvestigadores(solicitudesInvestigador);
        
        if (solicitudesInvestigador != null) {
            Iterator<SolicitudInvestigador> j = solicitudesInvestigador.iterator();
            while (j.hasNext()) {
                SolicitudInvestigador solicitudInvestigador = j.next();
                if (!solicitudInvestigador.getEstado().equals(SolicitudInvestigador.BORRADO)) {
                    if (solicitudInvestigador.getEstado().equals(SolicitudInvestigador.NO_TRAMITADO)) {
                        if (this.getLitaSolicitudesInvestigadorPendientes() == null) {
                            this.setLitaSolicitudesInvestigadorPendientes(new ArrayList<SolicitudInvestigador>());
                        }
                        this.getLitaSolicitudesInvestigadorPendientes().add(solicitudInvestigador);
                    }
                    if (solicitudInvestigador.getEstado().equals(SolicitudInvestigador.PROCESADO)) {
                        if (this.getLitaSolicitudesInvestigadorProcesadas() == null) {
                            this.setLitaSolicitudesInvestigadorProcesadas(new ArrayList<SolicitudInvestigador>());
                        }
                        this.getLitaSolicitudesInvestigadorProcesadas().add(solicitudInvestigador);
                    }
                }
            }
        }
    }
    
public void setSolicitudesProrrogaInvestigadorIntegranes(List<SolicitudProrrogaInvestigador> solicitudesInvestigador) {
        
        this.setListaSolicitudesProrrogaInvestigadorPendientes(null);
        listaSolicitudesProrrogaInvestigadorPendientes = new ArrayList<SolicitudProrrogaInvestigador>();
        this.setListaSolicitudesProrrogaInvestigadorProcesadas(null);
        listaSolicitudesProrrogaInvestigadorProcesadas = new ArrayList<SolicitudProrrogaInvestigador>();
        this.setListaSolicitudesProrrogaInvestigadores(solicitudesInvestigador);
        
        if (solicitudesInvestigador != null) {
            Iterator<SolicitudProrrogaInvestigador> j = solicitudesInvestigador.iterator();
            while (j.hasNext()) {
            	SolicitudProrrogaInvestigador solicitudInvestigador = j.next();
                if (!solicitudInvestigador.getEstado().equals(SolicitudProrrogaInvestigador.BORRADO)) {
                    if (solicitudInvestigador.getEstado().equals(SolicitudProrrogaInvestigador.NO_TRAMITADO)) {
                        this.getListaSolicitudesProrrogaInvestigadorPendientes().add(solicitudInvestigador);
                    }
                    if (solicitudInvestigador.getEstado().equals(SolicitudProrrogaInvestigador.PROCESADO)) {
                        this.getListaSolicitudesProrrogaInvestigadorProcesadas().add(solicitudInvestigador);
                    }
                }
            }
        }
    }

	public List<SolicitudProrrogaInvestigador> getListaSolicitudesProrrogaInvestigadorPendientes() {
		return listaSolicitudesProrrogaInvestigadorPendientes;
	}

	public void setListaSolicitudesProrrogaInvestigadorPendientes(
			List<SolicitudProrrogaInvestigador> listaSolicitudesProrrogaInvestigadorPendientes) {
		this.listaSolicitudesProrrogaInvestigadorPendientes = listaSolicitudesProrrogaInvestigadorPendientes;
	}

	public List<SolicitudProrrogaInvestigador> getListaSolicitudesProrrogaInvestigadorProcesadas() {
		return listaSolicitudesProrrogaInvestigadorProcesadas;
	}

	public void setListaSolicitudesProrrogaInvestigadorProcesadas(
			List<SolicitudProrrogaInvestigador> listaSolicitudesProrrogaInvestigadorProcesadas) {
		this.listaSolicitudesProrrogaInvestigadorProcesadas = listaSolicitudesProrrogaInvestigadorProcesadas;
	}

	public List<SolicitudProrrogaInvestigador> getListaSolicitudesProrrogaInvestigadores() {
		return listaSolicitudesProrrogaInvestigadores;
	}

	public void setListaSolicitudesProrrogaInvestigadores(List<SolicitudProrrogaInvestigador> listaSolicitudesProrrogaInvestigadores) {
		this.listaSolicitudesProrrogaInvestigadores = listaSolicitudesProrrogaInvestigadores;
	}

	public Set<SolicitudCiudad> getCiudades() {
		return ciudades;
	}

	public void setCiudades(Set<SolicitudCiudad> ciudades) {
		this.ciudades = ciudades;
	}

	public Date getFechaInicioRecoleccion() {
		return fechaInicioRecoleccion;
	}

	public void setFechaInicioRecoleccion(Date fechaInicioRecoleccion) {
		this.fechaInicioRecoleccion = fechaInicioRecoleccion;
	}

	public String getRadicadoAnla() {
		return radicadoAnla;
	}

	public void setRadicadoAnla(String radicadoAnla) {
		this.radicadoAnla = radicadoAnla;
	}

	public Date getFechaRadicadoAnla() {
		return fechaRadicadoAnla;
	}

	public void setFechaRadicadoAnla(Date fechaRadicadoAnla) {
		this.fechaRadicadoAnla = fechaRadicadoAnla;
	}

}
