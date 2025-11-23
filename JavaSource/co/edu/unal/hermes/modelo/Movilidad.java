/**
 * 
 */
package co.edu.unal.hermes.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Class Movilidad.
 *
 * @author Mauro
 */
public abstract class Movilidad {

    /** The id. */
    private Long id;

    /** The fechainicial. */
    private Date fechainicial;

    /** The fechafinal. */
    private Date fechafinal;

    /** The fechasolicitud. */
    private Date fechasolicitud;

    /** The seg mov fecha. */
    private Date segMovFecha;

    /** The persona inv. */
    private Persona personaInv;

    /** The tipo movilidad. */
    private TipoMovilidad tipoMovilidad;

    /** The convocatoria. */
    private Convocatoria convocatoria;

    /** The estado seguimiento. */
    private String estadoSeguimiento;

    /** The valor aprobado sede. */
    private Long valorAprobadoSede;

    /** The fecha revision seguimiento. */
    private Date fechaRevisionSeguimiento;

    /** The realizacion movilidad. */
    private String realizacionMovilidad;

    /** The razones no realizacion. */
    private String razonesNoRealizacion;

    /** The seg mov descripcion. */
    private String segMovDescripcion;

    /** The calificacion a. */
    private String calificacionA;

    /** The calificacion b. */
    private String calificacionB;

    /** The calificacion c. */
    private String calificacionC;

    /** The valor total apoyo. */
    private Long valorTotalApoyo;

    /** The valor total tiquetes. */
    private Long valorTotalTiquetes;

    /** The valor total viaticos. */
    private Long valorTotalViaticos;

    /** The id persona aprobacion. */
    private String idPersonaAprobacion;

    /** The tipo id persona aprobacion. */
    private String tipoIdPersonaAprobacion;

    /** The estado. */
    private String estado;

    /** The requisitos movilidad. */
    private Set<MovilidadRequisito> requisitosMovilidad = new HashSet<MovilidadRequisito>();

    /**
     * Instantiates a new movilidad.
     */
    public Movilidad() {
        /**
         * Zero args constructor.
         */
    }

    /**
     * Instantiates a new movilidad.
     *
     * @param id
     *            the id
     */
    public Movilidad(Long id) {
        this.id = id;
    }

    /**
     * Checks if is es fecha valida aprobacion.
     *
     * @return true, if is es fecha valida aprobacion
     */
    public boolean isEsFechaValidaAprobacion() {
        if (id != null) {
            GregorianCalendar calendarFechaInicio = new GregorianCalendar();
            calendarFechaInicio.setTime(this.fechainicial);
            calendarFechaInicio.set(GregorianCalendar.HOUR, 0);
            calendarFechaInicio.set(GregorianCalendar.MINUTE, 0);
            calendarFechaInicio.set(GregorianCalendar.SECOND, 1);

            GregorianCalendar calendarToday = new GregorianCalendar();
            calendarToday.setTime(new Date());
            calendarToday.set(GregorianCalendar.HOUR, 23);
            calendarToday.set(GregorianCalendar.MINUTE, 23);
            calendarToday.set(GregorianCalendar.SECOND, 59);
            
            return calendarFechaInicio.after(calendarToday);
        }
        return false;
    }

    /**
     * Gets the fecha minima inicial.
     *
     * @return the fecha minima inicial
     */
    public Date getFechaMinimaInicial() {
        GregorianCalendar calendar = new GregorianCalendar();
        if (fechasolicitud != null) {
            calendar.setTime(fechasolicitud);
        }
        calendar.add(GregorianCalendar.DATE, 30);
        return calendar.getTime();
    }

    /**
     * Gets the fecha minima final.
     *
     * @return the fecha minima final
     */
    public Date getFechaMinimaFinal() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(getFechaMinimaInicial());
        calendar.add(GregorianCalendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * Sets the convocatoria id.
     *
     * @param convocatoriaId the new convocatoria id
     */
    public void setConvocatoriaId(String convocatoriaId) {
        Convocatoria convocatoria = new Convocatoria();
        Long id = Long.parseLong(convocatoriaId);
        convocatoria.setId(id);
        this.setConvocatoria(convocatoria);
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
     * Gets the fechainicial.
     *
     * @return the fechainicial
     */
    public Date getFechainicial() {
        return fechainicial;
    }

    /**
     * Sets the fechainicial.
     *
     * @param fechainicial
     *            the new fechainicial
     */
    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    /**
     * Gets the fechafinal.
     *
     * @return the fechafinal
     */
    public Date getFechafinal() {
        return fechafinal;
    }

    /**
     * Sets the fechafinal.
     *
     * @param fechafinal
     *            the fechafinal to set
     */
    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    /**
     * Gets the fechasolicitud.
     *
     * @return the fechasolicitud
     */
    public Date getFechasolicitud() {
        return fechasolicitud;
    }

    /**
     * Sets the fechasolicitud.
     *
     * @param fechasolicitud            the fechasolicitud to set
     */
    public void setFechasolicitud(Date fechasolicitud) {
        this.fechasolicitud = fechasolicitud;
    }

    /**
     * Gets the seg mov fecha.
     *
     * @return the segMovFecha
     */
    public Date getSegMovFecha() {
        return segMovFecha;
    }

    /**
     * Sets the seg mov fecha.
     *
     * @param segMovFecha
     *            the segMovFecha to set
     */
    public void setSegMovFecha(Date segMovFecha) {
        this.segMovFecha = segMovFecha;
    }

    /**
     * Gets the persona inv.
     *
     * @return the personaInv
     */
    public Persona getPersonaInv() {
        return personaInv;
    }

    /**
     * Sets the persona inv.
     *
     * @param personaInv
     *            the personaInv to set
     */
    public void setPersonaInv(Persona personaInv) {
        this.personaInv = personaInv;
    }

    /**
     * Gets the tipo movilidad.
     *
     * @return the tipoMovilidad
     */
    public TipoMovilidad getTipoMovilidad() {
        return tipoMovilidad;
    }

    /**
     * Sets the tipo movilidad.
     *
     * @param tipoMovilidad
     *            the tipoMovilidad to set
     */
    public void setTipoMovilidad(TipoMovilidad tipoMovilidad) {
        this.tipoMovilidad = tipoMovilidad;
    }

    /**
     * Gets the valor aprobado sede.
     *
     * @return the valorAprobadoSede
     */
    public Long getValorAprobadoSede() {
        return valorAprobadoSede;
    }

    /**
     * Sets the valor aprobado sede.
     *
     * @param valorAprobadoSede
     *            the valorAprobadoSede to set
     */
    public void setValorAprobadoSede(Long valorAprobadoSede) {
        this.valorAprobadoSede = valorAprobadoSede;
    }

    /**
     * Gets the fecha revision seguimiento.
     *
     * @return the fechaRevisionSeguimiento
     */
    public Date getFechaRevisionSeguimiento() {
        return fechaRevisionSeguimiento;
    }

    /**
     * Sets the fecha revision seguimiento.
     *
     * @param fechaRevisionSeguimiento
     *            the fechaRevisionSeguimiento to set
     */
    public void setFechaRevisionSeguimiento(Date fechaRevisionSeguimiento) {
        this.fechaRevisionSeguimiento = fechaRevisionSeguimiento;
    }

    /**
     * Gets the realizacion movilidad.
     *
     * @return the realizacionMovilidad
     */
    public String getRealizacionMovilidad() {
        return realizacionMovilidad;
    }

    /**
     * Sets the realizacion movilidad.
     *
     * @param realizacionMovilidad
     *            the realizacionMovilidad to set
     */
    public void setRealizacionMovilidad(String realizacionMovilidad) {
        this.realizacionMovilidad = realizacionMovilidad;
    }

    /**
     * Gets the razones no realizacion.
     *
     * @return the razonesNoRealizacion
     */
    public String getRazonesNoRealizacion() {
        return razonesNoRealizacion;
    }

    /**
     * Sets the razones no realizacion.
     *
     * @param razonesNoRealizacion
     *            the razonesNoRealizacion to set
     */
    public void setRazonesNoRealizacion(String razonesNoRealizacion) {
        this.razonesNoRealizacion = razonesNoRealizacion;
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

    /**
     * Gets the estado seguimiento.
     *
     * @return the estadoSeguimiento
     */
    public String getEstadoSeguimiento() {
        return estadoSeguimiento;
    }

    /**
     * Sets the estado seguimiento.
     *
     * @param estadoSeguimiento
     *            the estadoSeguimiento to set
     */
    public void setEstadoSeguimiento(String estadoSeguimiento) {
        this.estadoSeguimiento = estadoSeguimiento;
    }

    /**
     * Gets the seg mov descripcion.
     *
     * @return the segMovDescripcion
     */
    public String getSegMovDescripcion() {
        return segMovDescripcion;
    }

    /**
     * Sets the seg mov descripcion.
     *
     * @param segMovDescripcion
     *            the segMovDescripcion to set
     */
    public void setSegMovDescripcion(String segMovDescripcion) {
        this.segMovDescripcion = segMovDescripcion;
    }

    /**
     * Gets the calificacion a.
     *
     * @return the calificacionA
     */
    public String getCalificacionA() {
        return calificacionA;
    }

    /**
     * Sets the calificacion a.
     *
     * @param calificacionA
     *            the calificacionA to set
     */
    public void setCalificacionA(String calificacionA) {
        this.calificacionA = calificacionA;
    }

    /**
     * Gets the calificacion b.
     *
     * @return the calificacionB
     */
    public String getCalificacionB() {
        return calificacionB;
    }

    /**
     * Sets the calificacion b.
     *
     * @param calificacionB
     *            the calificacionB to set
     */
    public void setCalificacionB(String calificacionB) {
        this.calificacionB = calificacionB;
    }

    /**
     * Gets the calificacion c.
     *
     * @return the calificacionC
     */
    public String getCalificacionC() {
        return calificacionC;
    }

    /**
     * Sets the calificacion c.
     *
     * @param calificacionC
     *            the calificacionC to set
     */
    public void setCalificacionC(String calificacionC) {
        this.calificacionC = calificacionC;
    }

    /**
     * Gets the valor total apoyo.
     *
     * @return the valorTotalApoyo
     */
    public Long getValorTotalApoyo() {
        return valorTotalApoyo;
    }

    /**
     * Sets the valor total apoyo.
     *
     * @param valorTotalApoyo
     *            the valorTotalApoyo to set
     */
    public void setValorTotalApoyo(Long valorTotalApoyo) {
        this.valorTotalApoyo = valorTotalApoyo;
    }

    /**
     * Gets the valor total tiquetes.
     *
     * @return the valorTotalTiquetes
     */
    public Long getValorTotalTiquetes() {
        return valorTotalTiquetes;
    }

    /**
     * Sets the valor total tiquetes.
     *
     * @param valorTotalTiquetes
     *            the valorTotalTiquetes to set
     */
    public void setValorTotalTiquetes(Long valorTotalTiquetes) {
        this.valorTotalTiquetes = valorTotalTiquetes;
    }

    /**
     * Gets the valor total viaticos.
     *
     * @return the valorTotalViaticos
     */
    public Long getValorTotalViaticos() {
        return valorTotalViaticos;
    }

    /**
     * Sets the valor total viaticos.
     *
     * @param valorTotalViaticos
     *            the valorTotalViaticos to set
     */
    public void setValorTotalViaticos(Long valorTotalViaticos) {
        this.valorTotalViaticos = valorTotalViaticos;
    }

    /**
     * Gets the id persona aprobacion.
     *
     * @return the idPersonaAprobacion
     */
    public String getIdPersonaAprobacion() {
        return idPersonaAprobacion;
    }

    /**
     * Sets the id persona aprobacion.
     *
     * @param idPersonaAprobacion
     *            the idPersonaAprobacion to set
     */
    public void setIdPersonaAprobacion(String idPersonaAprobacion) {
        this.idPersonaAprobacion = idPersonaAprobacion;
    }

    /**
     * Gets the tipo id persona aprobacion.
     *
     * @return the tipoIdPersonaAprobacion
     */
    public String getTipoIdPersonaAprobacion() {
        return tipoIdPersonaAprobacion;
    }

    /**
     * Sets the tipo id persona aprobacion.
     *
     * @param tipoIdPersonaAprobacion
     *            the tipoIdPersonaAprobacion to set
     */
    public void setTipoIdPersonaAprobacion(String tipoIdPersonaAprobacion) {
        this.tipoIdPersonaAprobacion = tipoIdPersonaAprobacion;
    }

    /**
     * Gets the estado.
     *
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the estado.
     *
     * @param estado
     *            the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Gets the requisitos movilidad.
     *
     * @return the requisitosMovilidad
     */
    public Set<MovilidadRequisito> getRequisitosMovilidad() {
        return requisitosMovilidad;
    }

    /**
     * Sets the requisitos movilidad.
     *
     * @param requisitosMovilidad
     *            the requisitosMovilidad to set
     */
    public void setRequisitosMovilidad(Set<MovilidadRequisito> requisitosMovilidad) {
        this.requisitosMovilidad = requisitosMovilidad;
    }

    /**
     * Gets the lista requisitos movilidad.
     *
     * @return the lista requisitos movilidad
     */
    public List<MovilidadRequisito> getListaRequisitosMovilidad() {
        List<MovilidadRequisito> listaRequisitos = new ArrayList<MovilidadRequisito>();
        listaRequisitos.addAll(requisitosMovilidad);
        return listaRequisitos;
    }
}
