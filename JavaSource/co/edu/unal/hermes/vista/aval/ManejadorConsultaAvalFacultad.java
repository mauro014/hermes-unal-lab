package co.edu.unal.hermes.vista.aval;

import java.util.List;

import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultaAvalFacultad extends ManejadorBase {

	/**
	 * @author Martha Correa
	 */
	private static final long serialVersionUID = 1L;

	private List<Aval> listaAval;
	protected Aval avalActual;
	private Investigador investigadorActual;

	public ManejadorConsultaAvalFacultad() {
		sesion.removeAttribute("manejadorSemillerosSolicitudDI");
		sesion.removeAttribute("manejadorSemillerosConsultaDI");
		investigadorActual = servicioPersona
				.obtenerInvestigadorInterno(((Persona) sesion.getAttribute("persona")).getId());
		listaAval = servicioGeneral.consultaAvalesFacultadSede(investigadorActual.getDependencia2().getSede().getId());
	}

	public Investigador getInvestigadorActual() {
		return investigadorActual;
	}

	public void setInvestigadorActual(Investigador investigadorActual) {
		this.investigadorActual = investigadorActual;
	}

	public int getTamanoLista() {
		if (!esListaVacia(listaAval)) {
			return listaAval.size();
		} else {
			return 0;
		}
	}

	public List<Aval> getListaAval() {
		return listaAval;
	}

	public void setListaAval(List<Aval> listaAvales) {
		this.listaAval = listaAvales;
	}

	// Requerimiento #2218 - Angela Devia
	public Aval getAvalActual() {
		return avalActual;
	}

	public void setAvalActual(Aval avalActual) {
		this.avalActual = avalActual;
	}

	/**
	 * @author Angela Devia
	 * @used in consultarAvalFacultad.xhtml
	 */
	public void negarAval() {
		setAvalActual(servicioGeneral.obtenerAval(this.avalActual.getAviId().toString()).get(0));

		avalActual.setAviEstado(Aval.BORRADO);
		avalActual.setAviAvaldireccion(Aval.BORRADO);
		avalActual.setAvalVice(null);
		avalActual.setAviFechaAvalCoor(getToday());
		avalActual.setAviFechaAvalVice(null);
		servicioGeneral.guardarObjeto(avalActual);

		crearHistoricoEstadoAval(getAvalActual(), getInvestigadorActual(),
				"Dirección de Investigación - Aval borrado en la dirección porque se ha cerrado la convocatoria o el proceso correspondiente");

		this.listaAval.remove(this.avalActual);
	}
	// Fin Requerimiento #2218 - Angela Devia

}
