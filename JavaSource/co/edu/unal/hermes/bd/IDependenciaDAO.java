
package co.edu.unal.hermes.bd;

import java.util.List;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;

/**
 * The Interface IDependenciaDAO.
 */
public interface IDependenciaDAO {

	/**
	 * Obtener dependencia.
	 *
	 * @param id
	 *            the id
	 * @return the dependencia
	 */
	public Dependencia obtenerDependencia(String id);

	/**
	 * Obtener dependencias hija.
	 *
	 * @param d
	 *            the d
	 * @param listaDependencias
	 *            the lista dependencias
	 */
	public void obtenerDependenciasHija(Dependencia d, List<Dependencia> listaDependencias);

	/**
	 * Obtener dependencias activas facultad y sede.
	 *
	 * @return the list
	 */
	public List<Dependencia> obtenerDependenciasActivasFacultadYSede();

	/**
	 * Obtener dependencia.
	 *
	 * @param id
	 *            the id
	 * @return the dependencia
	 * @throws DataAccessException
	 *             the data access exception
	 */
	public Dependencia obtenerDependencia(IdPersona id) throws DataAccessException;

	/**
	 * Obtener dependencia2.
	 *
	 * @param id
	 *            the id
	 * @return the dependencia
	 * @throws DataAccessException
	 *             the data access exception
	 */
	public Dependencia obtenerDependencia2(IdPersona id) throws DataAccessException;

	/**
	 * Obtener dependencia persona.
	 *
	 * @param id
	 *            the id
	 * @return the dependencia
	 * @throws DataAccessException
	 *             the data access exception
	 */
	public Dependencia obtenerDependenciaPersona(IdPersona id) throws DataAccessException;

	/**
	 * Obtener dependencia x sede.
	 *
	 * @param sedeId
	 *            the sede id
	 * @return the list
	 */
	public List<Dependencia> obtenerDependenciaXSede(Long sedeId);

	/**
	 * Obtener facultades x sede.
	 *
	 * @param sedeId
	 *            the sede id
	 * @return the list
	 */
	public List<Dependencia> obtenerFacultadesXSede(Long sedeId);

	/**
	 * Obtener dependencias x facultad.
	 *
	 * @param facultadId
	 *            the facultad id
	 * @return the list
	 */
	public List<Dependencia> obtenerDependenciasXFacultad(String facultadId);
}
