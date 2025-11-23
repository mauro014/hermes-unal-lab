/*
 * Created on 09-ago-2005
 */
package co.edu.unal.hermes.modelo.servicios.imp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.bd.ILineaDAO;
import co.edu.unal.hermes.modelo.LineaInvestigacion;
import co.edu.unal.hermes.modelo.servicios.IServicioLineaInvestigacion;
import co.edu.unal.hermes.utils.ReemplazaAcentos;

public class ServicioLineaInvestigacion implements IServicioLineaInvestigacion {

	private ILineaDAO lineaDAO;

	public void setLineaDAO(ILineaDAO dao) {
		lineaDAO = dao;
	}

	public LineaInvestigacion obtenerLinea(Long id) {
		return lineaDAO.buscarPorId(id);
	}

	public List obtenerLineas(String nombre) {
		List a = new ArrayList();
		try {
			List titles = ReemplazaAcentos.listaPalabrasBusqueda(nombre);
			Iterator it = titles.iterator();
			while (it.hasNext()) {
				String s = (String) it.next();
				a.addAll(lineaDAO.obtenerLineas(s));
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return a;
	}

	/**
	 * Cuando un investigador no encuentra una linea de investigacion para
	 * asociar a su proyecto, propone una nueva linea de investigacion.
	 */
	public LineaInvestigacion proponerLineaInvestigacion(
			LineaInvestigacion linea) {
		// Cuando se propone una nueva linea de investigacion se debe asociar al
		// origen
		// de creada por el usuario
		linea.setNombre(linea.getNombre().toUpperCase());
		linea.setOrigen(LineaInvestigacion.PROPUESTA);
		return lineaDAO.guardarLinea(linea);
	}

	public LineaInvestigacion adicionarLinea(String lineaInvestigacion) {
		LineaInvestigacion linea = lineaDAO.obtenerLinea(lineaInvestigacion);
		if (linea == null) {
			// Proponer linea
			LineaInvestigacion nuevaLinea = new LineaInvestigacion();
			nuevaLinea.setNombre(lineaInvestigacion);
			return proponerLineaInvestigacion(nuevaLinea);
		} else {
			return linea;
		}
	}

	public List listaNombreLineaXEmpienzaCon(String nombreLinea) {
		return lineaDAO.listaNombreLineaXEmpienzaCon(nombreLinea);
	}

	public List<LineaInvestigacion> listaLineasInvestigacionNombreContiene(
			String nombreLinea) {
		return lineaDAO.listaLineasInvestigacionNombreContiene(nombreLinea);
	}
}
