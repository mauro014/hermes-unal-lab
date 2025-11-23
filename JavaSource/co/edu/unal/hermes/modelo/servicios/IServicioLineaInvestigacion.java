/*
 * Created on 09-ago-2005
 */
package co.edu.unal.hermes.modelo.servicios;

import java.util.List;

import co.edu.unal.hermes.modelo.LineaInvestigacion;

public interface IServicioLineaInvestigacion {

	public LineaInvestigacion obtenerLinea(Long id);

	public List obtenerLineas(String nombre);

	public LineaInvestigacion proponerLineaInvestigacion(
			LineaInvestigacion linea);

	public LineaInvestigacion adicionarLinea(String lineaInvestigacion);

	public List listaNombreLineaXEmpienzaCon(String nombreLinea);

	public List<LineaInvestigacion> listaLineasInvestigacionNombreContiene(
			String nombreLinea);
}
