package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Empresa;
import co.edu.unal.hermes.modelo.Tipos;

/**
 * Objeto que relaciona un Equipo (LaboratorioDetalleEquipos) con una Actividad
 * realizada (Mantenimiento Preventivo, Mantenimiento Correctivo, Calibración,
 * Verificación).
 * 
 * @author dgbenitezc
 */
public class LaboratorioActividadEquipo implements Cloneable{

	private Long id;
	private LaboratorioDetalleEquipos equipo;
	private Date fechaRegistro;
	private Date fechaActualizacion;
	private Tipos tipoActividad;
	private Date fechaActividad;
	private Date fechaEjecucion;
	private String observaciones;
	private String estadoActividad;
	private String eventoId;
	private Date fechaAlerta;
	private Long costoActividad;
	private String costoActividadCadena;
	private String responsable;
	private boolean editada;
	private Boolean activa;
	private Tipos modalidad;
	private Boolean alertaEnviada;
	private String textoAlerta;
	
	private Boolean programarProximaActividad;
	private Tipos frecuencia;
	private String manttoCodInforme;
	private String calibEmpresa;
	private Boolean calibEmpresaAcreditada;
	private String calibCodInforme;
	private String calibIntervaloMed;
	private Boolean calibAdecuadoUso;
	private String calibAdecuadoUsoNoDesc;

	public static final String ESTADO_PROGRAMADA = "P";
	public static final String ESTADO_EJECUTADA = "E";
	public static final String ESTADO_PLANEADA = "L";

	public static final String NOMBRE_ESTADO_PROGRAMADA = "Programada";
	public static final String NOMBRE_ESTADO_EJECUTADA = "Ejecutada";
	public static final String NOMBRE_ESTADO_PLANEADA = "Planeada";

	// IDs de Tipos de actividades de Mantenimiento:
	public static final String TIPOS_ACT_MANTTO = Tipos.TIPO_ACTIVIDAD_EQUIPO_LABORATORIO_MANTTO_CORR
			.toString()
			+ ", "
			+ Tipos.TIPO_ACTIVIDAD_EQUIPO_LABORATORIO_MANTTO_PREV.toString();

	// IDs de Tipos de actividades de Calibración:
	public static final String TIPOS_ACT_CALIBR = Tipos.TIPO_ACTIVIDAD_EQUIPO_CALIBRACION
			.toString()
			+ ", "
			+ Tipos.TIPO_ACTIVIDAD_EQUIPO_LABORATORIO_VERIFICACION.toString();
	
	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}

	/**
	 * @return la fecha en que ha sido programada, o ha sido ejecutada la
	 *         actividad
	 */
	public Date getFecha() {
		if (estadoActividad.equals(ESTADO_EJECUTADA)) {
			return fechaEjecucion;
		} else {
			return fechaActividad;
		}
	}

	/**
	 * @return los primeros 30 caracteres de las observaciones
	 */
	public String getObservacionesCortas() {
		int maxCaracteres = 30;
		if (observaciones == null || observaciones.length() <= maxCaracteres) {
			return observaciones;
		} else {
			return observaciones.substring(0, maxCaracteres) + "...";
		}
	}

	public LaboratorioActividadEquipo() {
		fechaRegistro = new Date();
		costoActividad = 0L;
	}

	public String getNombreEstadoActividad() {
		if (estadoActividad.equals(ESTADO_PROGRAMADA)) {
			return NOMBRE_ESTADO_PROGRAMADA;
		}
		if (estadoActividad.equals(ESTADO_EJECUTADA)) {
			return NOMBRE_ESTADO_EJECUTADA;
		}
		if (estadoActividad.equals(ESTADO_PLANEADA)) {
			return NOMBRE_ESTADO_PLANEADA;
		}
		return "ERROR";
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the equipo
	 */
	public LaboratorioDetalleEquipos getEquipo() {
		return equipo;
	}

	/**
	 * @param equipo
	 *            the equipo to set
	 */
	public void setEquipo(LaboratorioDetalleEquipos equipo) {
		this.equipo = equipo;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the tipoActividad
	 */
	public Tipos getTipoActividad() {
		return tipoActividad;
	}

	/**
	 * @param tipoActividad
	 *            the tipoActividad to set
	 */
	public void setTipoActividad(Tipos tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	/**
	 * @return the fechaActividad
	 */
	public Date getFechaActividad() {
		return fechaActividad;
	}

	/**
	 * @param fechaActividad
	 *            the fechaActividad to set
	 */
	public void setFechaActividad(Date fechaActividad) {
		this.fechaActividad = fechaActividad;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones
	 *            the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the estadoActividad
	 */
	public String getEstadoActividad() {
		return estadoActividad;
	}

	/**
	 * @param estadoActividad
	 *            the estadoActividad to set
	 */
	public void setEstadoActividad(String estadoActividad) {
		this.estadoActividad = estadoActividad;
	}

	/**
	 * @return the eventoId
	 */
	public String getEventoId() {
		return eventoId;
	}

	/**
	 * @param eventoId
	 *            the eventoId to set
	 */
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	/**
	 * @return the fechaEjecucion
	 */
	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	/**
	 * @param fechaEjecucion
	 *            the fechaEjecucion to set
	 */
	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	/**
	 * @return the fechaAlerta
	 */
	public Date getFechaAlerta() {
		return fechaAlerta;
	}

	/**
	 * @param fechaAlerta
	 *            the fechaAlerta to set
	 */
	public void setFechaAlerta(Date fechaAlerta) {
		this.fechaAlerta = fechaAlerta;
	}

	/**
	 * @return the editada
	 */
	public Boolean getEditada() {
		return editada;
	}

	/**
	 * @param editada
	 *            the editada to set
	 */
	public void setEditada(Boolean editada) {
		this.editada = editada;
	}

	/**
	 * @return the costoActividad
	 */
	public Long getCostoActividad() {
		return costoActividad;
	}

	/**
	 * @param costoActividad
	 *            the costoActividad to set
	 */
	public void setCostoActividad(Long costoActividad) {
		this.costoActividad = costoActividad;
	}

	/**
	 * @return the responsable
	 */
	public String getResponsable() {
		return responsable;
	}

	/**
	 * @param responsable
	 *            the responsable to set
	 */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public Boolean getActiva() {
		return activa;
	}

	public void setActiva(Boolean activa) {
		this.activa = activa;
	}

	public Tipos getModalidad() {
		return modalidad;
	}

	public void setModalidad(Tipos modalidad) {
		this.modalidad = modalidad;
	}

	public Boolean getAlertaEnviada() {
		return alertaEnviada;
	}

	public void setAlertaEnviada(Boolean alertaEnviada) {
		this.alertaEnviada = alertaEnviada;
	}

	public String getTextoAlerta() {
		return textoAlerta;
	}

	public void setTextoAlerta(String textoAlerta) {
		this.textoAlerta = textoAlerta;
	}

	public Tipos getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Tipos frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getManttoCodInforme() {
		return manttoCodInforme;
	}

	public void setManttoCodInforme(String manttoCodInforme) {
		this.manttoCodInforme = manttoCodInforme;
	}

	public String getCalibEmpresa() {
		return calibEmpresa;
	}

	public void setCalibEmpresa(String calibEmpresa) {
		this.calibEmpresa = calibEmpresa;
	}

	public Boolean getCalibEmpresaAcreditada() {
		return calibEmpresaAcreditada;
	}

	public void setCalibEmpresaAcreditada(Boolean calibEmpresaAcreditada) {
		this.calibEmpresaAcreditada = calibEmpresaAcreditada;
	}

	public String getCalibCodInforme() {
		return calibCodInforme;
	}

	public void setCalibCodInforme(String calibCodInforme) {
		this.calibCodInforme = calibCodInforme;
	}

	public String getCalibIntervaloMed() {
		return calibIntervaloMed;
	}

	public void setCalibIntervaloMed(String calibIntervaloMed) {
		this.calibIntervaloMed = calibIntervaloMed;
	}

	public Boolean getCalibAdecuadoUso() {
		return calibAdecuadoUso;
	}

	public void setCalibAdecuadoUso(Boolean calibAdecuadoUso) {
		this.calibAdecuadoUso = calibAdecuadoUso;
	}

	public String getCalibAdecuadoUsoNoDesc() {
		return calibAdecuadoUsoNoDesc;
	}

	public void setCalibAdecuadoUsoNoDesc(String calibAdecuadoUsoNoDesc) {
		this.calibAdecuadoUsoNoDesc = calibAdecuadoUsoNoDesc;
	}

	public Boolean getProgramarProximaActividad() {
		return programarProximaActividad;
	}

	public void setProgramarProximaActividad(Boolean programarProximaActividad) {
		this.programarProximaActividad = programarProximaActividad;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getCostoActividadCadena() {
		return costoActividadCadena;
	}

	public void setCostoActividadCadena(String costoActividadCadena) {
		this.costoActividadCadena = costoActividadCadena;
	}
}
