package co.edu.unal.hermes.bd;

import java.util.List;

import co.edu.unal.hermes.modelo.TipoRubro;

// TODO: Auto-generated Javadoc
/**
 * Interface para temas relacionados con financiacion y rubros.
 */

public interface IFinanciacionDAO {

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
