
package co.edu.unal.hermes.vista.proyectos.evaluacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.CalificacionEvaluacion;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.TipoFinanciacion;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorConsultaEvaluadores.
 *
 * @author Ing Hernán Darío Bernal Parra
 */

public class ManejadorConsultaEvaluadores extends ManejadorBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1674544394627351317L;

	/** The lista proyecto evaluador aux. */
	private List listaProyectoEvaluador;

	/** The proyecto actual. */
	private Proyecto proyectoActual;

	/** The tabla proyecto evaluador. */
	private HtmlDataTable tablaProyectoEvaluador;

	/** The concepto final. */
	private String conceptoFinal;

	/** The proyecto evaluador seleccionado. */
	private ProyectoEvaluador proyectoEvaluadorSeleccionado;

	/**
	 * Instantiates a new manejador consulta evaluadores.
	 */
	public ManejadorConsultaEvaluadores() {
		super();
		tablaProyectoEvaluador = new HtmlDataTable();
		listaProyectoEvaluador = new ArrayList();
		List listaProyectoEvaluadorAux = new ArrayList();
		conceptoFinal = "";

		try {
			sesion.removeAttribute("manejadorEvaluadores");
			// Obtener el proyecto actual de la sesión
			proyectoActual = (Proyecto) sesion.getAttribute("proyecto");

			// Obtener los evaluadores asociados al proyecto diferenciandolos
			// del evaluador Comite de seleccion
			Iterator it = proyectoActual.getEvaluadoresProyecto().iterator();
			while (it.hasNext()) {
				ProyectoEvaluador pe = (ProyectoEvaluador) it.next();
				if (pe.getEvaluador().getId().getDocumento().equals("COMITESELECCION")
						|| pe.getEvaluador().getId().getDocumento().equals("MESATRABAJO")) {
					listaProyectoEvaluadorAux.add(pe);

						// Cuando el evaluador es el comite de seleccion o decision
						// final
						if (pe.getTipoFinanciacion() != null && !esCadenaVacia(pe.getTipoFinanciacion().getId())) {
							TipoFinanciacion tf = (TipoFinanciacion) servicioGeneral
									.obtenerObjeto(new TipoFinanciacion(), pe.getTipoFinanciacion().getId());
							conceptoFinal = tf.getNombre();
						}

				} else {
					try {
						Convocatoria convocatoria = (Convocatoria) proyectoActual.getModalidad();
						if (convocatoria.isMostrarEvaluacionesIndividuales()) {
							listaProyectoEvaluadorAux.add(pe);
						}

					} catch (Exception e) {
						listaProyectoEvaluadorAux.add(pe);
					}
				}
			}

			// Juan Pablo DINAIN: no se deben mostrar los evaluadores que no
			// diligenciaron la evaluacion
			Iterator itPr = listaProyectoEvaluadorAux.iterator();
			while (itPr.hasNext()) {
				ProyectoEvaluador proEval = (ProyectoEvaluador) itPr.next();
				proEval = servicioProyecto.obtenerProyectoEvaluador(proEval);
				// Si el evaluador es MESATRABAJO se adiciona a la lista sin
				// ninguna comprobacion
				if (proEval.getEvaluador().getId().getDocumento().equals("MESATRABAJO")) {
					listaProyectoEvaluador.add(proEval);
					if (proEval.getFechaSeleccion() != null) {
						// Cuando el evaluador es el comite de seleccion o decision
						// final
						ProyectoEvaluador copia = new ProyectoEvaluador();
				        copia.setId(proEval.getId());
				        copia.setEvaluador(proEval.getEvaluador());
				        copia.setFechaSeleccion(proEval.getFechaSeleccion());
				        copia.setBanderaSeleccion(true);
						listaProyectoEvaluador.add(copia);
					}
					
				} else {
					Iterator itCalif = proEval.getCalificaciones().iterator();
					boolean bCalificacion = false;
					while (itCalif.hasNext()) {
						CalificacionEvaluacion calEval = (CalificacionEvaluacion) itCalif.next();
						if (calEval.getCuantitativa() != null) {
							bCalificacion = true;
						}
					}
					// Si tiene al menos una calificacion se adiciona a la lista
					// de evaluadores

					if (bCalificacion && proEval.getActivo() == null) {
						listaProyectoEvaluador.add(proEval);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Asociar evaluador.
	 */
	public void asociarEvaluador() {
		ProyectoEvaluador e = proyectoEvaluadorSeleccionado;

		if (e.getProyecto().getModalidad().getTipo().getId() != null
				&& e.getProyecto().getModalidad().getTipo().getId().compareTo("PN") == 0) {
			if (e.getEvaluador().getId().getDocumento().equals("MESATRABAJO")) {
				ReporteBirt r = new ReporteBirt();
				ProyectoEvaluador pe = (ProyectoEvaluador) tablaProyectoEvaluador.getRowData();
				r.adicionarParametro("id", pe.getId().toString());
				r.setFormato(ReporteBirt.FORMATO_PDF);
				r.setNombreReporte("/evaluacion/Evaluacion");
				sesion.setAttribute("reporte", r);
				FacesContext context = FacesContext.getCurrentInstance();
				try {
					context.getExternalContext().dispatch("/ReporteEngineServlet");
				} catch (Exception es) {
					System.out.println(e);
				} finally {
					context.responseComplete();
				}
			} else {
				ReporteBirt r = new ReporteBirt();
				ProyectoEvaluador pe = (ProyectoEvaluador) tablaProyectoEvaluador.getRowData();
				r.adicionarParametro("id", pe.getId().toString());
				r.setFormato(ReporteBirt.FORMATO_PDF);
				r.setNombreReporte("/evaluacion/Evaluacion");
				sesion.setAttribute("reporte", r);
				FacesContext context = FacesContext.getCurrentInstance();
				try {
					context.getExternalContext().dispatch("/ReporteEngineServlet");
				} catch (Exception es) {
					System.out.println(e);
				} finally {
					context.responseComplete();
				}
			}
		}
		sesion.setAttribute("proyectoEvaluador", e);
		sesion.removeAttribute("manejadorConsultaEvaluadores");
		sesion.removeAttribute("manejadorResumenEvaluacion");
	}

	/**
	 * Reporte.
	 */
	public void reporte() {
		ReporteBirt r = new ReporteBirt();
		ProyectoEvaluador pe = (ProyectoEvaluador) proyectoEvaluadorSeleccionado;
		r.adicionarParametro("id", pe.getId().toString());
		r.setFormato(ReporteBirt.FORMATO_PDF);

		if (proyectoActual.getModalidad().getTipo().getId().compareTo("PN") == 0) {
			r.setNombreReporte("/evaluacion/EvaluacionPrograma");
		} else {
			r.setNombreReporte("/evaluacion/Evaluacion");
		}
		if (pe.isBanderaSeleccion()) {
			r.setNombreReporte("/evaluacion/ComiteSeleccion");
		}
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			context.responseComplete();
		}
	}

	public void descargarDocumentoEvaluacion() {
		ProyectoEvaluador pe = (ProyectoEvaluador) proyectoEvaluadorSeleccionado;
		descargarArchivoGenerico("HER_PROYECTO_EVALUADOR", pe.getId().toString(), pe.getDocumentoEvaluador());
	}

	/**
	 * Gets the lista proyecto evaluador.
	 *
	 * @return the lista proyecto evaluador
	 */
	public List getListaProyectoEvaluador() {
		return listaProyectoEvaluador;
	}

	/**
	 * Sets the lista proyecto evaluador.
	 *
	 * @param listaProyectoEvaluador the new lista proyecto evaluador
	 */
	public void setListaProyectoEvaluador(List listaProyectoEvaluador) {
		this.listaProyectoEvaluador = listaProyectoEvaluador;
	}

	/**
	 * Gets the proyecto actual.
	 *
	 * @return the proyecto actual
	 */
	public Proyecto getProyectoActual() {
		return proyectoActual;
	}

	/**
	 * Sets the proyecto actual.
	 *
	 * @param proyectoActual the new proyecto actual
	 */
	public void setProyectoActual(Proyecto proyectoActual) {
		this.proyectoActual = proyectoActual;
	}

	/**
	 * Gets the tabla proyecto evaluador.
	 *
	 * @return the tabla proyecto evaluador
	 */
	public HtmlDataTable getTablaProyectoEvaluador() {
		return tablaProyectoEvaluador;
	}

	/**
	 * Sets the tabla proyecto evaluador.
	 *
	 * @param tablaProyectoEvaluador the new tabla proyecto evaluador
	 */
	public void setTablaProyectoEvaluador(HtmlDataTable tablaProyectoEvaluador) {
		this.tablaProyectoEvaluador = tablaProyectoEvaluador;
	}

	/**
	 * Gets the concepto final.
	 *
	 * @return the concepto final
	 */
	public String getConceptoFinal() {
		return conceptoFinal;
	}

	/**
	 * Sets the concepto final.
	 *
	 * @param conceptoFinal the new concepto final
	 */
	public void setConceptoFinal(String conceptoFinal) {
		this.conceptoFinal = conceptoFinal;
	}

	/**
	 * Gets the proyecto evaluador seleccionado.
	 *
	 * @return the proyecto evaluador seleccionado
	 */
	public ProyectoEvaluador getProyectoEvaluadorSeleccionado() {
		return proyectoEvaluadorSeleccionado;
	}

	/**
	 * Sets the proyecto evaluador seleccionado.
	 *
	 * @param proyectoEvaluadorSeleccionado the new proyecto evaluador seleccionado
	 */
	public void setProyectoEvaluadorSeleccionado(ProyectoEvaluador proyectoEvaluadorSeleccionado) {
		this.proyectoEvaluadorSeleccionado = proyectoEvaluadorSeleccionado;
	}

}
