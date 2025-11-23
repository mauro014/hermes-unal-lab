package co.edu.unal.hermes.modelo;

import java.util.Date;

public class ConvocatoriaEquipos {
    private static final long serialVersionUID = 1L;

    public static String REF = "ConvocatoriaEquipos";
    public static String PROP_PRODUCCION_ACADEMICA = "produccionAcademica";
    public static String PROP_GRUPOS_UN_ALIANZA = "gruposUnAlianza";
    public static String PROP_GRUPO_RESPONSABLE = "grupoResponsable";
    public static String PROP_PROPUESTA_EQUIPO = "propuestaEquipo";
    public static String PROP_MODALIDAD = "modalidad";
    public static String PROP_INVESTIGADOR = "investigador";
    public static String PROP_FORMACION_EST = "formacionEst";
    public static String PROP_AGENDA_CONOCIMIENTO = "agendaConocimiento";
    public static String PROP_ID = "id";
    public static String PROP_TIPO_APOYO = "tipoApoyo";
    public static String PROP_PARTICIPACION_EVENTOS = "participacionEventos";
    public static String PROP_TRAYECTORIA_GRUPO = "trayectoriaGrupo";
    public static String PROP_DOCENTES_PARTICIPANTES = "docentesParticipantes";
    public static String PROP_COA_ETAPA = "etapa";
    public static String PROP_COA_ESTADO = "estado";
    public static String PROP_COA_FECHA_SOLICITUD = "fechaSolicitud";

    // constructors
    public ConvocatoriaEquipos() {
	initialize();
    }

    /**
     * Constructor for primary key
     */
    public ConvocatoriaEquipos(java.lang.Integer id) {
	this.setId(id);
	initialize();
    }

    /**
     * Constructor for required fields
     */
    public ConvocatoriaEquipos(java.lang.Integer id, co.edu.unal.hermes.modelo.Grupo grupoResponsable, co.edu.unal.hermes.modelo.Investigador investigador) {

	this.setId(id);
	this.setGrupoResponsable(grupoResponsable);
	this.setInvestigador(investigador);
	initialize();
    }

    protected void initialize() {
    }

    private int hashCode = Integer.MIN_VALUE;

    // primary key
    private java.lang.Integer id;

    // fields
    private java.lang.String modalidad;
    private java.lang.String propuestaEquipo;
    private java.lang.String agendaConocimiento;
    private java.lang.String tipoApoyo;
    private java.lang.String trayectoriaGrupo;
    private java.lang.String gruposUnAlianza;
    private java.lang.String formacionEst;
    private java.lang.String produccionAcademica;
    private java.lang.String participacionEventos;
    private Integer etapa;
    private String estado;
    private java.util.Date fechaSolicitud;
    private String docentesParticipantes;
    private Date fechaActa;
    private String estadoActa;

    // many to one
    private co.edu.unal.hermes.modelo.Grupo grupoResponsable;
    private co.edu.unal.hermes.modelo.Investigador investigador;

    // collections
    private java.util.Set<co.edu.unal.hermes.modelo.ArchivoConvocatoria> archivos;

    /**
     * Return the unique identifier of this class
     * 
     * @hibernate.id generator-class="sequence" column="CNEQ_ID"
     */
    public java.lang.Integer getId() {
	return id;
    }

    /**
     * Set the unique identifier of this class
     * 
     * @param id
     *            the new ID
     */
    public void setId(java.lang.Integer id) {
	this.id = id;
	this.hashCode = Integer.MIN_VALUE;
    }

    /**
     * Return the value associated with the column: CNEQ_MODALIDAD
     */
    public java.lang.String getModalidad() {
	return modalidad;
    }

    /**
     * Set the value related to the column: CNEQ_MODALIDAD
     * 
     * @param modalidad
     *            the CNEQ_MODALIDAD value
     */
    public void setModalidad(java.lang.String modalidad) {
	this.modalidad = modalidad;
    }

    /**
     * Return the value associated with the column: CNEQ_PROPUESTA_EQUIPO
     */
    public java.lang.String getPropuestaEquipo() {
	return propuestaEquipo;
    }

    /**
     * Set the value related to the column: CNEQ_PROPUESTA_EQUIPO
     * 
     * @param propuestaEquipo
     *            the CNEQ_PROPUESTA_EQUIPO value
     */
    public void setPropuestaEquipo(java.lang.String propuestaEquipo) {
	this.propuestaEquipo = propuestaEquipo;
    }

    /**
     * Return the value associated with the column: CNEQ_AGENDA_CONOCIMIENTO
     */
    public java.lang.String getAgendaConocimiento() {
	return agendaConocimiento;
    }

    /**
     * Set the value related to the column: CNEQ_AGENDA_CONOCIMIENTO
     * 
     * @param agendaConocimiento
     *            the CNEQ_AGENDA_CONOCIMIENTO value
     */
    public void setAgendaConocimiento(java.lang.String agendaConocimiento) {
	this.agendaConocimiento = agendaConocimiento;
    }

    /**
     * Return the value associated with the column: CNEQ_TIPO_APOYO
     */
    public java.lang.String getTipoApoyo() {
	return tipoApoyo;
    }

    /**
     * Set the value related to the column: CNEQ_TIPO_APOYO
     * 
     * @param tipoApoyo
     *            the CNEQ_TIPO_APOYO value
     */
    public void setTipoApoyo(java.lang.String tipoApoyo) {
	this.tipoApoyo = tipoApoyo;
    }

    /**
     * Return the value associated with the column: CNEQ_TRAYECTORIA_GRUPO
     */
    public java.lang.String getTrayectoriaGrupo() {
	return trayectoriaGrupo;
    }

    /**
     * Set the value related to the column: CNEQ_TRAYECTORIA_GRUPO
     * 
     * @param trayectoriaGrupo
     *            the CNEQ_TRAYECTORIA_GRUPO value
     */
    public void setTrayectoriaGrupo(java.lang.String trayectoriaGrupo) {
	this.trayectoriaGrupo = trayectoriaGrupo;
    }

    /**
     * Return the value associated with the column: CNEQ_GRUPOS_UN_ALIANZA
     */
    public java.lang.String getGruposUnAlianza() {
	return gruposUnAlianza;
    }

    /**
     * Set the value related to the column: CNEQ_GRUPOS_UN_ALIANZA
     * 
     * @param gruposUnAlianza
     *            the CNEQ_GRUPOS_UN_ALIANZA value
     */
    public void setGruposUnAlianza(java.lang.String gruposUnAlianza) {
	this.gruposUnAlianza = gruposUnAlianza;
    }

    /**
     * Return the value associated with the column: CNEQ_FORMACION_EST
     */
    public java.lang.String getFormacionEst() {
	return formacionEst;
    }

    /**
     * Set the value related to the column: CNEQ_FORMACION_EST
     * 
     * @param formacionEst
     *            the CNEQ_FORMACION_EST value
     */
    public void setFormacionEst(java.lang.String formacionEst) {
	this.formacionEst = formacionEst;
    }

    /**
     * Return the value associated with the column: CNEQ_PRODUCCION_ACADEMICA
     */
    public java.lang.String getProduccionAcademica() {
	return produccionAcademica;
    }

    /**
     * Set the value related to the column: CNEQ_PRODUCCION_ACADEMICA
     * 
     * @param produccionAcademica
     *            the CNEQ_PRODUCCION_ACADEMICA value
     */
    public void setProduccionAcademica(java.lang.String produccionAcademica) {
	this.produccionAcademica = produccionAcademica;
    }

    /**
     * Return the value associated with the column: CNEQ_PARTICIPACION_EVENTOS
     */
    public java.lang.String getParticipacionEventos() {
	return participacionEventos;
    }

    /**
     * Set the value related to the column: CNEQ_PARTICIPACION_EVENTOS
     * 
     * @param participacionEventos
     *            the CNEQ_PARTICIPACION_EVENTOS value
     */
    public void setParticipacionEventos(java.lang.String participacionEventos) {
	this.participacionEventos = participacionEventos;
    }

    /**
     * Return the value associated with the column: CNEQ_GRUPO_RESPONSABLE
     */
    public co.edu.unal.hermes.modelo.Grupo getGrupoResponsable() {
	return grupoResponsable;
    }

    /**
     * Set the value related to the column: CNEQ_GRUPO_RESPONSABLE
     * 
     * @param grupoResponsable
     *            the CNEQ_GRUPO_RESPONSABLE value
     */
    public void setGrupoResponsable(co.edu.unal.hermes.modelo.Grupo grupoResponsable) {
	this.grupoResponsable = grupoResponsable;
    }

    /**
     * Return the value associated with the column: TDO_ID
     */
    public co.edu.unal.hermes.modelo.Investigador getInvestigador() {
	return investigador;
    }

    /**
     * Set the value related to the column: TDO_ID
     * 
     * @param investigador
     *            the TDO_ID value
     */
    public void setInvestigador(co.edu.unal.hermes.modelo.Investigador investigador) {
	this.investigador = investigador;
    }

    /**
     * Return the value associated with the column: archivos
     */
    public java.util.Set<co.edu.unal.hermes.modelo.ArchivoConvocatoria> getArchivos() {
	return archivos;
    }

    /**
     * Set the value related to the column: archivos
     * 
     * @param archivos
     *            the archivos value
     */
    public void setArchivos(java.util.Set<co.edu.unal.hermes.modelo.ArchivoConvocatoria> archivos) {
	this.archivos = archivos;
    }

    public void addToarchivos(co.edu.unal.hermes.modelo.ArchivoConvocatoria archivoConvocatoria) {
	if (null == getArchivos())
	    setArchivos(new java.util.TreeSet<co.edu.unal.hermes.modelo.ArchivoConvocatoria>());
	getArchivos().add(archivoConvocatoria);
    }

    public boolean equals(Object obj) {
	if (null == obj)
	    return false;
	if (!(obj instanceof co.edu.unal.hermes.modelo.ConvocatoriaEquipos))
	    return false;
	else {
	    co.edu.unal.hermes.modelo.ConvocatoriaEquipos convocatoriaEquipos = (co.edu.unal.hermes.modelo.ConvocatoriaEquipos) obj;
	    if (null == this.getId() || null == convocatoriaEquipos.getId())
		return false;
	    else
		return (this.getId().equals(convocatoriaEquipos.getId()));
	}
    }

    public int hashCode() {
	if (Integer.MIN_VALUE == this.hashCode) {
	    if (null == this.getId())
		return super.hashCode();
	    else {
		String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
		this.hashCode = hashStr.hashCode();
	    }
	}
	return this.hashCode;
    }

    public String toString() {
	return super.toString();
    }

    public Integer getEtapa() {
	return etapa;
    }

    public void setEtapa(Integer etapa) {
	this.etapa = etapa;
    }

    public String getEstado() {
	return estado;
    }

    public void setEstado(String estado) {
	this.estado = estado;
    }

    public java.util.Date getFechaSolicitud() {
	return fechaSolicitud;
    }

    public void setFechaSolicitud(java.util.Date fechaSolicitud) {
	this.fechaSolicitud = fechaSolicitud;
    }

    public String getDocentesParticipantes() {
	return docentesParticipantes;
    }

    public void setDocentesParticipantes(String docentesParticipantes) {
	this.docentesParticipantes = docentesParticipantes;
    }

    public Date getFechaActa() {
        return fechaActa;
    }

    public void setFechaActa(Date fechaActa) {
        this.fechaActa = fechaActa;
    }

    public String getEstadoActa() {
        return estadoActa;
    }

    public void setEstadoActa(String estadoActa) {
        this.estadoActa = estadoActa;
    }

}