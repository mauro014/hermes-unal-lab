package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.TipoRubro;

/**
 * Clase que representa la relacion entre un Laboratorio y su financiación
 * (Fuente, Rubro, Valor, Descripción).
 * 
 * @author dgbenitezc
 */
public class LaboratorioRubro {

	private Long id;
	private Date fechaRegistro;
	private Long idLaboratorio;
	private TipoRubro tipoRubro;
	private Long valor;
	private String descripcion;
	private FuenteFinanciacion fuenteFinanciacion;

	public LaboratorioRubro() {
		tipoRubro = new TipoRubro();
		fuenteFinanciacion = new FuenteFinanciacion();
		fuenteFinanciacion.setInternaExterna(FuenteFinanciacion.interna);
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
	 * @return the tipoRubro
	 */
	public TipoRubro getTipoRubro() {
		return tipoRubro;
	}

	/**
	 * @param tipoRubro
	 *            the tipoRubro to set
	 */
	public void setTipoRubro(TipoRubro tipoRubro) {
		this.tipoRubro = tipoRubro;
	}

	/**
	 * @return the valor
	 */
	public Long getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(Long valor) {
		this.valor = valor;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the idLaboratorio
	 */
	public Long getIdLaboratorio() {
		return idLaboratorio;
	}

	/**
	 * @param idLaboratorio
	 *            the idLaboratorio to set
	 */
	public void setIdLaboratorio(Long idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	/**
	 * @return the fuenteFinanciacion
	 */
	public FuenteFinanciacion getFuenteFinanciacion() {
		return fuenteFinanciacion;
	}

	/**
	 * @param fuenteFinanciacion
	 *            the fuenteFinanciacion to set
	 */
	public void setFuenteFinanciacion(FuenteFinanciacion fuenteFinanciacion) {
		this.fuenteFinanciacion = fuenteFinanciacion;
	}

}
