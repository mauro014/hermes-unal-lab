/*
 * Created on 28-marzo-2017
 */
package co.edu.unal.hermes.modelo.servicios.imp;

import java.util.List;

import co.edu.unal.hermes.bd.IFinanciacionDAO;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.modelo.servicios.IServicioFinanciacion;

/**
 * The Class ServicioAval.
 */
public class ServicioFinanciacion implements IServicioFinanciacion {

	/** The financiacion DAO. */
	private IFinanciacionDAO financiacionDAO;

	/**
	 * Sets the financiacion DAO.
	 *
	 * @param financiacionDAO
	 *            the new financiacion DAO
	 */
	public void setFinanciacionDAO(IFinanciacionDAO financiacionDAO) {
		this.financiacionDAO = financiacionDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.modelo.servicios.IServicioFinanciacion#
	 * obtenerTipoIngresos(java.lang.String)
	 */
	@Override
	public List<TipoRubro> obtenerTipoIngresos() {
		return financiacionDAO.obtenerTipoIngresos();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.modelo.servicios.IServicioFinanciacion#
	 * obtenerTipoRubro(java.lang.Long)
	 */
	public TipoRubro obtenerTipoRubro(Long id) {
		return financiacionDAO.obtenerTipoRubro(id);
	}

}
