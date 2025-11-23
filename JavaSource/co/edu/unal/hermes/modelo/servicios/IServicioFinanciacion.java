/*
 * Created on 28-marzo-2017
 */
package co.edu.unal.hermes.modelo.servicios;

import java.util.List;

import co.edu.unal.hermes.modelo.TipoRubro;

/**
 * The Interface IServicioBiodiversidad.
 */
public interface IServicioFinanciacion {

	/**
	 * Obtener ingresos.
	 *
	 * @return the list
	 */
	List<TipoRubro> obtenerTipoIngresos();

	/**
	 * Obtener tipo rubro.
	 *
	 * @param id
	 *            the id
	 * @return the tipo rubro
	 */
	public TipoRubro obtenerTipoRubro(Long id);
}
