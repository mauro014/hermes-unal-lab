package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Refiere el investigador Interno que pertenece y labora con la Universidad
 * Nacional.
 */
public class InvestigadorInterno extends Investigador implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4519080781112041464L;

	/**
	 * Trae la dependecia de la Universidad relacionadas al proyecto.
	 */
	private Dependencia dependencia;

	/** The dependencia2. */
	private Dependencia dependencia2;

	/** The url web docente. */
	private String urlWebDocente;

	/** The tel extension. */
	private String telExtension;

	/** The investigador_2017. */
	private Boolean investigador_2017;

	/** The tipo dedicacion. */
	private TipoDedicacion tipoDedicacion;

	/** The tipo formacion. */
	private TipoFormacion tipoFormacion;

	/** The tipo vinculacion. */
	private TipoVinculacion tipoVinculacion;

	/** The valor hora. */
	private Long valorHora;

	/** The tiene permiso marco. */
	private String tienePermisoMarco;

	/** The perfil. */
	private String perfil;

	/** The investigador_2007. */
	// Modificado Giovanni>>
	private Boolean investigador_2007;

	/** The temporal. */
	private String temporal; // Para recuperar informacion solo de consulta
	private String areaExperticia;
	
	private Boolean periodoPrueba;

	// <<Modificado Giovanni

	/** The tipo cargo. */
	private TipoCargo tipoCargo;
	
	/** The minciencias. */
	private String evaluadorMinciencias;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.modelo.Investigador#getTipoFormacion()
	 */
	public TipoFormacion getTipoFormacion() {
		return tipoFormacion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.unal.hermes.modelo.Investigador#setTipoFormacion(co.edu.unal.hermes.
	 * modelo.TipoFormacion)
	 */
	public void setTipoFormacion(TipoFormacion tipoFormacion) {
		this.tipoFormacion = tipoFormacion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.modelo.Investigador#getDependencia()
	 */
	public Dependencia getDependencia() {
		return dependencia;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.unal.hermes.modelo.Investigador#setDependencia(co.edu.unal.hermes.
	 * modelo.Dependencia)
	 */
	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	/**
	 * Gets the tel extension.
	 *
	 * @return the tel extension
	 */
	public String getTelExtension() {
		return telExtension;
	}

	/**
	 * Sets the tel extension.
	 *
	 * @param telExtension
	 *            the new tel extension
	 */
	public void setTelExtension(String telExtension) {
		this.telExtension = telExtension;
	}

	/**
	 * Gets the url web docente.
	 *
	 * @return the url web docente
	 */
	public String getUrlWebDocente() {
		return urlWebDocente;
	}

	/**
	 * Sets the url web docente.
	 *
	 * @param urlWebDocente
	 *            the new url web docente
	 */
	public void setUrlWebDocente(String urlWebDocente) {
		this.urlWebDocente = urlWebDocente;
	}

	/**
	 * Gets the investigador_2017.
	 *
	 * @return the investigador_2017
	 */
	public Boolean getInvestigador_2017() {
		return investigador_2017;
	}

	/**
	 * Sets the investigador_2017.
	 *
	 * @param investigador_2017
	 *            the new investigador_2017
	 */
	public void setInvestigador_2017(Boolean investigador_2017) {
		this.investigador_2017 = investigador_2017;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.modelo.Investigador#getTipoDedicacion()
	 */
	public TipoDedicacion getTipoDedicacion() {
		return tipoDedicacion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.unal.hermes.modelo.Investigador#setTipoDedicacion(co.edu.unal.hermes.
	 * modelo.TipoDedicacion)
	 */
	public void setTipoDedicacion(TipoDedicacion tipoDedicacion) {
		this.tipoDedicacion = tipoDedicacion;
	}

	/**
	 * Gets the investigador_2007.
	 *
	 * @return the investigador_2007
	 */
	public Boolean getInvestigador_2007() {
		return investigador_2007;
	}

	/**
	 * Sets the investigador_2007.
	 *
	 * @param investigador_2007
	 *            the new investigador_2007
	 */
	public void setInvestigador_2007(Boolean investigador_2007) {
		this.investigador_2007 = investigador_2007;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.modelo.Investigador#getTipoVinculacion()
	 */
	public TipoVinculacion getTipoVinculacion() {
		return tipoVinculacion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.unal.hermes.modelo.Investigador#setTipoVinculacion(co.edu.unal.hermes.
	 * modelo.TipoVinculacion)
	 */
	public void setTipoVinculacion(TipoVinculacion tipoVinculacion) {
		this.tipoVinculacion = tipoVinculacion;
	}

	/**
	 * Gets the dependencia2.
	 *
	 * @return the dependencia2
	 */
	public Dependencia getDependencia2() {
		if (this.dependencia2 == null) {
			return this.dependencia;
		}
		return dependencia2;
	}

	/**
	 * Sets the dependencia2.
	 *
	 * @param dependencia2
	 *            the new dependencia2
	 */
	public void setDependencia2(Dependencia dependencia2) {
		this.dependencia2 = dependencia2;
	}

	/**
	 * Gets the valor hora.
	 *
	 * @return the valor hora
	 */
	public Long getValorHora() {
		return valorHora;
	}

	/**
	 * If null returns 0.
	 * 
	 * @return
	 */
	public Long getValorHoraValidado() {
		return getValorHora() != null ? getValorHora() : 0;
	}

	/**
	 * Sets the valor hora.
	 *
	 * @param valorHora
	 *            the new valor hora
	 */
	public void setValorHora(Long valorHora) {
		this.valorHora = valorHora;
	}

	/**
	 * Gets the tipo cargo.
	 *
	 * @return the tipoCargo
	 */
	public TipoCargo getTipoCargo() {
		return tipoCargo;
	}

	/**
	 * Sets the tipo cargo.
	 *
	 * @param tipoCargo
	 *            the tipoCargo to set
	 */
	public void setTipoCargo(TipoCargo tipoCargo) {
		this.tipoCargo = tipoCargo;
	}

	/**
	 * Gets the perfil.
	 *
	 * @return the perfil
	 */
	public String getPerfil() {
		return perfil;
	}

	/**
	 * Sets the perfil.
	 *
	 * @param perfil
	 *            the new perfil
	 */
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	/**
	 * Sets the tiene permiso marco.
	 *
	 * @param tienePermisoMarco
	 *            the new tiene permiso marco
	 */
	public void setTienePermisoMarco(String tienePermisoMarco) {
		this.tienePermisoMarco = tienePermisoMarco;
	}

	/**
	 * Gets the tiene permiso marco.
	 *
	 * @return the tiene permiso marco
	 */
	public String getTienePermisoMarco() {
		return tienePermisoMarco;
	}

	/**
	 * Gets the temporal.
	 *
	 * @return the temporal
	 */
	public String getTemporal() {
		return temporal;
	}

	/**
	 * Sets the temporal.
	 *
	 * @param temporal
	 *            the new temporal
	 */
	public void setTemporal(String temporal) {
		this.temporal = temporal;
	}

	public String getEvaluadorMinciencias() {
		return evaluadorMinciencias;
	}

	public void setEvaluadorMinciencias(String evaluadorMinciencias) {
		this.evaluadorMinciencias = evaluadorMinciencias;
	}

	public String getAreaExperticia() {
		return areaExperticia;
	}

	public void setAreaExperticia(String areaExperticia) {
		this.areaExperticia = areaExperticia;
	}

	public Boolean getPeriodoPrueba() {
		return periodoPrueba;
	}

	public void setPeriodoPrueba(Boolean periodoPrueba) {
		this.periodoPrueba = periodoPrueba;
	}

}
