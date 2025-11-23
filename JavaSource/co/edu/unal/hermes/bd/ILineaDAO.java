package co.edu.unal.hermes.bd;

import java.util.List;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.LineaInvestigacion;

/**
 * Interface para obtener todas las líneas buscar o guardar líneas específicas
 * 
 * @trows lanza la excepcion cuando no se puede acceder a los datos de las
 *        líneas
 */
public interface ILineaDAO {

	public List obtenerLineas(String name) throws DataAccessException;

	public LineaInvestigacion buscarPorId(Long id) throws DataAccessException;

	public LineaInvestigacion guardarLinea(LineaInvestigacion linea)
			throws DataAccessException;

	public LineaInvestigacion obtenerLinea(String lineaInvestigacion)
			throws DataAccessException;

	public List listaNombreLineaXEmpienzaCon(String nombreLinea);

	public List<LineaInvestigacion> listaLineasInvestigacionNombreContiene(
			String nombreLinea);
}
