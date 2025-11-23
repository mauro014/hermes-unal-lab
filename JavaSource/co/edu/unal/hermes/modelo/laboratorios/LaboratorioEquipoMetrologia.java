package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Tipos;

/**
 * Objeto que relaciona un Equipo (LaboratorioDetalleEquipos) con una
 * Especificación Metrológica (magnitud, unidad, rango, división)
 * 
 * @author dgbenitezc
 */
public class LaboratorioEquipoMetrologia {

	private Long id;
	private LaboratorioDetalleEquipos equipo;
	private String magnitud;
	private String unidad;
	private String rangoMedicion;
	private String divisionEscala;
	private Tipos frecuenciaCalibracion;
	private Tipos frecuenciaVerificacion;
	private Date fechaRegistro;
	private Tipos tipoMagnitud;
	
	private Boolean equipoAltaPrecision;
	private String precisionValor;
	private Boolean equipoAltaExactitud;
	private String exactitudValor;
	private String incertidumbre;
	private String errorMaximo;

	public LaboratorioEquipoMetrologia() {
		frecuenciaCalibracion = new Tipos(
				Tipos.FRECUENCIA_MANTENIMIENTO_NO_APLICA);
		frecuenciaVerificacion = new Tipos(
				Tipos.FRECUENCIA_MANTENIMIENTO_NO_APLICA);
		fechaRegistro = new Date();
	}

	public String getNombreMagnitud() {
		if (tipoMagnitud == null
				|| tipoMagnitud.getId().equals(Tipos.TIPO_MAGNITUD_FISICA_OTRA)) {
			return magnitud;
		} else {
			return tipoMagnitud.getNombre();
		}
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
	 * @return the magnitud
	 */
	public String getMagnitud() {
		return magnitud;
	}

	/**
	 * @param magnitud
	 *            the magnitud to set
	 */
	public void setMagnitud(String magnitud) {
		this.magnitud = magnitud;
	}

	/**
	 * @return the unidad
	 */
	public String getUnidad() {
		return unidad;
	}

	/**
	 * @param unidad
	 *            the unidad to set
	 */
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	/**
	 * @return the rangoMedicion
	 */
	public String getRangoMedicion() {
		return rangoMedicion;
	}

	/**
	 * @param rangoMedicion
	 *            the rangoMedicion to set
	 */
	public void setRangoMedicion(String rangoMedicion) {
		this.rangoMedicion = rangoMedicion;
	}

	/**
	 * @return the divisionEscala
	 */
	public String getDivisionEscala() {
		return divisionEscala;
	}

	/**
	 * @param divisionEscala
	 *            the divisionEscala to set
	 */
	public void setDivisionEscala(String divisionEscala) {
		this.divisionEscala = divisionEscala;
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
	 * @return the frecuenciaCalibracion
	 */
	public Tipos getFrecuenciaCalibracion() {
		return frecuenciaCalibracion;
	}

	/**
	 * @param frecuenciaCalibracion
	 *            the frecuenciaCalibracion to set
	 */
	public void setFrecuenciaCalibracion(Tipos frecuenciaCalibracion) {
		this.frecuenciaCalibracion = frecuenciaCalibracion;
	}

	/**
	 * @return the frecuenciaVerificacion
	 */
	public Tipos getFrecuenciaVerificacion() {
		return frecuenciaVerificacion;
	}

	/**
	 * @param frecuenciaVerificacion
	 *            the frecuenciaVerificacion to set
	 */
	public void setFrecuenciaVerificacion(Tipos frecuenciaVerificacion) {
		this.frecuenciaVerificacion = frecuenciaVerificacion;
	}

	/**
	 * @return the tipoMagnitud
	 */
	public Tipos getTipoMagnitud() {
		return tipoMagnitud;
	}

	/**
	 * @param tipoMagnitud
	 *            the tipoMagnitud to set
	 */
	public void setTipoMagnitud(Tipos tipoMagnitud) {
		this.tipoMagnitud = tipoMagnitud;
	}

	public Boolean getEquipoAltaPrecision() {
		return equipoAltaPrecision;
	}

	public void setEquipoAltaPrecision(Boolean equipoAltaPrecision) {
		this.equipoAltaPrecision = equipoAltaPrecision;
	}

	public String getPrecisionValor() {
		return precisionValor;
	}

	public void setPrecisionValor(String precisionValor) {
		this.precisionValor = precisionValor;
	}

	public Boolean getEquipoAltaExactitud() {
		return equipoAltaExactitud;
	}

	public void setEquipoAltaExactitud(Boolean equipoAltaExactitud) {
		this.equipoAltaExactitud = equipoAltaExactitud;
	}

	public String getExactitudValor() {
		return exactitudValor;
	}

	public void setExactitudValor(String exactitudValor) {
		this.exactitudValor = exactitudValor;
	}

	public String getIncertidumbre() {
		return incertidumbre;
	}

	public void setIncertidumbre(String incertidumbre) {
		this.incertidumbre = incertidumbre;
	}

	public String getErrorMaximo() {
		return errorMaximo;
	}

	public void setErrorMaximo(String errorMaximo) {
		this.errorMaximo = errorMaximo;
	}

}
