package co.edu.unal.hermes.modelo;

import java.util.Date;

public class ProyectoCompromiso {

	private Long id;
	private Proyecto proyecto;
	private String cumplido;
	private Date fechaVencimiento;
	private Integer tiempoAlerta;
	private TipoInforme tipoInforme;
	private Date fechaNotificacion;
	private Integer numeroNotificaciones;
	private Date fechaVencProrroga;
	private String nombreTipoInforme;
	private Date fechaNotificacionAcumulada;
	private Integer numeroNotificacionesAcumuladas;

	public static final String CUMPLIDO = "S";
	public static final String NO_CUMPLIDO = "N";
	
	private String observaciones;
	private ConvenioObligacion convenioObligacion;
	private Date fechaSeguimiento;

	public ProyectoCompromiso() {
	}

	public ProyectoCompromiso(Long pId) {
		this.id = pId;
	}

	/**
	 * Este método retorna NULL; se mantiene para no generar errores de
	 * compilación en co.edu.unal.hermes.vista.proyectos.manejadorproyecto.
	 * ManejadorCompromisos. El atributo tipoCompromiso NO existe en la clase
	 * ProyectoCompromiso.
	 * 
	 * @return Returns null.
	 */
	public TipoCompromiso getTipoCompromiso() {
		TipoCompromiso nulo = null;
		return nulo;
	}

	/**
	 * Este método no hace nada; se mantiene para no generar errores de
	 * compilación en co.edu.unal.hermes.vista.proyectos.manejadorproyecto.
	 * ManejadorCompromisos. El atributo tipoCompromiso NO existe en la clase
	 * ProyectoCompromiso.
	 * 
	 * @param nulo
	 *            The TipoCompromiso to set.
	 */
	public void setTipoCompromiso(TipoCompromiso nulo) {
	}

	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the proyecto.
	 */
	public Proyecto getProyecto() {
		return proyecto;
	}

	/**
	 * @param proyecto
	 *            The proyecto to set.
	 */
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getCumplido() {
		return cumplido;
	}

	public void setCumplido(String cumplido) {
		this.cumplido = cumplido;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Integer getTiempoAlerta() {
		return tiempoAlerta;
	}

	public void setTiempoAlerta(Integer tiempoAlerta) {
		this.tiempoAlerta = tiempoAlerta;
	}

	/**
	 * @return the tipoInforme
	 */
	public TipoInforme getTipoInforme() {
		return tipoInforme;
	}

	/**
	 * @param tipoInforme
	 *            the tipoInforme to set
	 */
	public void setTipoInforme(TipoInforme tipoInforme) {
		this.tipoInforme = tipoInforme;
	}

	/**
	 * @return the fechaNotificacion
	 */
	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	/**
	 * @param fechaNotificacion
	 *            the fechaNotificacion to set
	 */
	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	/**
	 * @return the numeroNotificaciones
	 */
	public Integer getNumeroNotificaciones() {
		return numeroNotificaciones;
	}

	/**
	 * @param numeroNotificaciones
	 *            the numeroNotificaciones to set
	 */
	public void setNumeroNotificaciones(Integer numeroNotificaciones) {
		this.numeroNotificaciones = numeroNotificaciones;
	}

	/**
	 * @return the fechaVencProrroga
	 */
	public Date getFechaVencProrroga() {
		return fechaVencProrroga;
	}

	/**
	 * @param fechaVencProrroga the fechaVencProrroga to set
	 */
	public void setFechaVencProrroga(Date fechaVencProrroga) {
		this.fechaVencProrroga = fechaVencProrroga;
	}

	public Integer getNumeroNotificacionesAcumuladas() {
		return numeroNotificacionesAcumuladas;
	}

	public void setNumeroNotificacionesAcumuladas(
			Integer numeroNotificacionesAcumuladas) {
		this.numeroNotificacionesAcumuladas = numeroNotificacionesAcumuladas;
	}

	public Date getFechaNotificacionAcumulada() {
		return fechaNotificacionAcumulada;
	}

	public void setFechaNotificacionAcumulada(Date fechaNotificacionAcumulada) {
		this.fechaNotificacionAcumulada = fechaNotificacionAcumulada;
	}

	public String getNombreTipoInforme() {
		return nombreTipoInforme;
	}

	public void setNombreTipoInforme(String nombreTipoInforme) {
		this.nombreTipoInforme = nombreTipoInforme;
	}

	public ConvenioObligacion getConvenioObligacion() {
		return convenioObligacion;
	}

	public void setConvenioObligacion(ConvenioObligacion convenioObligacion) {
		this.convenioObligacion = convenioObligacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFechaSeguimiento() {
		return fechaSeguimiento;
	}

	public void setFechaSeguimiento(Date fechaSeguimiento) {
		this.fechaSeguimiento = fechaSeguimiento;
	}

}
