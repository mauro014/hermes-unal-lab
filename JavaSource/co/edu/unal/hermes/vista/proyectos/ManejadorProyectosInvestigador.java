package co.edu.unal.hermes.vista.proyectos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Proyecto;

/**
 * The Class ManejadorProyectosInvestigador.
 */
public class ManejadorProyectosInvestigador extends ManejadorBaseProyectosInvestigador {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7279955617436535065L;

	/**
	 * Instantiates a new manejador proyectos investigador.
	 */
	public ManejadorProyectosInvestigador() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.vista.proyectos.ManejadorBaseProyectosInvestigador#
	 * cargarListasProyectos()
	 */
	@Override
	protected void cargarListasProyectos() {

		// Se cargan los proyectos del investigador principal
		List<InvestigadorProyecto> listaProyectosInvestigador = new ArrayList<InvestigadorProyecto>();
		try {
			listaProyectosInvestigador.addAll(investigadorActual.getProyectosInvestigador());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterator<InvestigadorProyecto> iter = listaProyectosInvestigador.iterator();

		List<InvestigadorProyecto> listaProyectosActivos = new ArrayList<InvestigadorProyecto>();
		while (iter.hasNext()) {
			InvestigadorProyecto investigadorProyecto = (InvestigadorProyecto) iter.next();
			Proyecto p = investigadorProyecto.getProyecto();
			
			if (!p.getEstadoProyecto().getId().equals(EstadoProyecto.BORRADO)
					&& !investigadorProyecto.getTipo().getId().equals("A") && !p.getModalidad().getTipo().getId().equals("SIS")
                            && !p.getModalidad().getTipo().getId().equals("CLN")
                            && !p.getModalidad().getTipo().getId().equals("CL")) {
				listaProyectosActivos.add(investigadorProyecto);

			}
		}
		cargarProyectosVista(listaProyectosActivos);
		cargarConvocatoriasArtículos();
	}

}
