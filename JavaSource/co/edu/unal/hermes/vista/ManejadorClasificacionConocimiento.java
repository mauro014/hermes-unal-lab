package co.edu.unal.hermes.vista;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.edu.unal.hermes.modelo.ClasificacionConocimiento;
import co.edu.unal.hermes.utils.ReemplazaAcentos;

public class ManejadorClasificacionConocimiento extends ManejadorBase {
	private List<ClasificacionConocimiento> listaAreasNivel3y4;
	private String textoBuscar;

	public ManejadorClasificacionConocimiento() {
		listaAreasNivel3y4 = new ArrayList<ClasificacionConocimiento>();
		// String hql3 =
		// "from ClasificacionConocimiento WHERE nivel in (3,4) ORDER BY nombre";
		// Registros sin hijos, sin importar el nivel:
		String hql3 = "FROM ClasificacionConocimiento AS c1 WHERE NOT EXISTS (SELECT c2.id FROM ClasificacionConocimiento AS c2 WHERE c2.padre.id = c1.id) ORDER BY c1.nombre";
		System.out.println("ManejadorClasificacionConocimiento, hql3: " + hql3);
		listaAreasNivel3y4 = servicioGeneral.obtenerObjetos(
				ClasificacionConocimiento.class, hql3);
	}

	/**
	 * @param nombre
	 * @return lista de Strings de áreas temáticas cuyo nombre contiene el
	 *         parámetro
	 */
	public List<String> obtenerAreasTematicasSugeridas(String nombre) {
		List<String> lstAreas = new ArrayList<String>();
		if (nombre != null && nombre.trim() != "")
			for (ClasificacionConocimiento cc : listaAreasNivel3y4) {
				if (ReemplazaAcentos
						.quitarTildes(cc.getCodigoNombre())
						.trim()
						.toUpperCase()
						.indexOf(
								ReemplazaAcentos.quitarTildes(nombre).trim()
										.toUpperCase()) >= 0) {
					lstAreas.add(cc.getCodigoNombre());
				}
			}
		return lstAreas;
	}

	/**
	 * @return ClasificacionConocimiento cuyo nombre es textoBuscar
	 */
	public ClasificacionConocimiento getAreaTematicaSeleccionadaEnBuscar() {
		ClasificacionConocimiento cc = null;
		if (textoBuscar != null && textoBuscar.trim() != "") {
			for (Iterator<ClasificacionConocimiento> ite = listaAreasNivel3y4
					.iterator(); ite.hasNext();) {
				cc = (ClasificacionConocimiento) ite.next();
				if (ReemplazaAcentos
						.quitarTildes(cc.getCodigoNombre())
						.trim()
						.toUpperCase()
						.equals(ReemplazaAcentos.quitarTildes(textoBuscar)
								.trim().toUpperCase())) {
					break;
				}
			}
		}
		if (cc != null) {
			textoBuscar = "";
		}
		return cc;
	}

	/**
	 * @return the textoBuscar
	 */
	public String getTextoBuscar() {
		return textoBuscar;
	}

	/**
	 * @param textoBuscar
	 *            the textoBuscar to set
	 */
	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}

}
