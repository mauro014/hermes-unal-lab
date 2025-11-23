package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.DominioDetalle;

/**
 * Objeto que representa la asociación entre un Laboratorio y un área científica
 * o tecnológica de la OCDE (DominioDetalle)
 * 
 * @author dgbenitezc
 */
public class LaboratorioAreaOCDE {

	private Long id;
	private Long idLaboratorio;
	private DominioDetalle areaOCDE;
	private Date fecha;

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
	 * @return the areaOCDE
	 */
	public DominioDetalle getAreaOCDE() {
		return areaOCDE;
	}

	/**
	 * @param areaOCDE
	 *            the areaOCDE to set
	 */
	public void setAreaOCDE(DominioDetalle areaOCDE) {
		this.areaOCDE = areaOCDE;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
