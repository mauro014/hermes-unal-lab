package co.edu.unal.hermes.vista.evaluadores;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.CalificacionEvaluacion;
import co.edu.unal.hermes.modelo.HistoricoEstadoEvaluacion;
import co.edu.unal.hermes.modelo.ModalidadCriterioTipoPregunta;
import co.edu.unal.hermes.modelo.OpcionMultiple;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.TipoConcepto;
import co.edu.unal.hermes.modelo.TipoPregunta;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorBaseEvaluacion extends ManejadorBase{
	protected ProyectoEvaluador evaluacionProyecto;
	protected List listaCriterios;
	protected List listaCC;
	protected HtmlDataTable tablaCC;
	protected float promedioCalificacion = 0;
	protected float maximaCalificacion = 0;
	protected SelectItem[] recomendaciones; // Posibles recomendaciones para el proyecto
	protected boolean motrarAprobacion = false;
	protected float promedioPonderado = 0;
	protected String tipoEvaluacion;
	
	public CalificacionEvaluacion buscarEvaluacion(long id) {
		Set listaEvaluaciones = evaluacionProyecto.getCalificaciones();
		Iterator it = listaEvaluaciones.iterator();
		CalificacionEvaluacion d = null;
		int i = 0;
		while (it.hasNext()) {
			d = (CalificacionEvaluacion) it.next();
			if (id == (d.getCriterio().getId()).longValue())
				break;
			else
				d = null;
			i = i + 1;
		}
		return d;
	}
	
	public void calcularPromedioCalificaciones() {
		System.out.println("ENTRO A PROMEDIO CALIFICACIONES");
		promedioCalificacion = 0;
		maximaCalificacion = 0;
		if (listaCC != null) {
			for (Iterator it = listaCC.iterator(); it.hasNext();) {
				CalificacionEvaluacion cc = (CalificacionEvaluacion) it.next();
				ModalidadCriterioTipoPregunta mctp = servicioEvaluacion.obtenerModalidadTipoPreguntaXModalidadYCriterio(
						evaluacionProyecto.getProyecto().getModalidad().getId(), cc.getCriterio().getId());
				if (cc.isPreguntaSeleccionUnica() && mctp.getTipoPregunta().getSeTieneParaPromedio().equals("S")) {
					Iterator<OpcionMultiple> i = mctp.getTipoPregunta().getOpciones().iterator();
					while (i.hasNext()) {
						OpcionMultiple opcionMultiple = i.next();
						Float id = (Float) opcionMultiple.getId().floatValue();
						if (id.equals(cc.getCuantitativa())) {
							cc.setCalificacionSeleccion(opcionMultiple.getValor());
							break;
						}
					}
				}
				if (mctp.getTipoPregunta().getSeTieneParaPromedio().equals("S")) {

					Double valorCalificacion = 0.0;
					if (cc.getCalificacionSeleccion() != null) {
						valorCalificacion = cc.getCalificacionSeleccion();
					} else if (cc.getCuantitativa() != null) {
						valorCalificacion = (double) cc.getCuantitativa();
					}

					promedioCalificacion += valorCalificacion * cc.getCriterio().getFactor().floatValue();
					maximaCalificacion += cc.getMaximmoVista() * cc.getCriterio().getFactor().floatValue();
				}
			}
		}
	}
	
	public void cargarValoresGenerales() {

		recomendaciones = new SelectItem[3];
		recomendaciones[0] = new SelectItem("A", "Aprobar");
		recomendaciones[1] = new SelectItem("E", "Aprobar con modificaciones");
		recomendaciones[2] = new SelectItem("N", "No aprobar");
		// SE OBTIENE EL OBJETO PROYECTO EVALUACION
		evaluacionProyecto = new ProyectoEvaluador();
		evaluacionProyecto = servicioProyecto
				.obtenerProyectoEvaluador((ProyectoEvaluador) sesion.getAttribute("proyectoEvaluador"));

		listaCriterios = servicioModalidad
				.listaModalidadCriterioTipoPregunta(evaluacionProyecto.getProyecto().getModalidad());

		if (evaluacionProyecto.getProyecto().getModalidad() != null) {
			if (evaluacionProyecto.getProyecto().getModalidad().getId() == 290) {
				motrarAprobacion = true;
			} else {
				motrarAprobacion = false;
			}
		}

		listaCC = new Vector();
		for (Iterator i = listaCriterios.iterator(); i.hasNext();) {
			ModalidadCriterioTipoPregunta ce = (ModalidadCriterioTipoPregunta) i.next();
			CalificacionEvaluacion cEvaluacion = servicioModalidad
					.obtenrCalificacionEvaluacionXCriterioYProyectoYEvaluador(ce.getCriterio(),
							evaluacionProyecto.getProyecto(), evaluacionProyecto.getEvaluador());

			if (cEvaluacion == null) {
				cEvaluacion = new CalificacionEvaluacion();
				cEvaluacion.setCriterio(ce.getCriterio());
				cEvaluacion.setCuantitativa(new Float(0));
				cEvaluacion.setProyectoEvaluador(evaluacionProyecto);

			} else {
				if (ce.getTipoPregunta().getId().longValue() == TipoPregunta.seleccionUnica
						&& cEvaluacion.getCuantitativa() != null) {
					OpcionMultiple om = servicioEvaluacion.obtenerOpcionMultipleXTipoPreguntaYValor(
							ce.getTipoPregunta().getId(), new Double(cEvaluacion.getCuantitativa().doubleValue()));
					if (om != null) {

						cEvaluacion.setCuantitativa(new Float(om.getId().floatValue()));
						cEvaluacion.setCualitativa(om.getNombre());
					}
				}
			}

			cEvaluacion.setModalidadId(evaluacionProyecto.getProyecto().getModalidad().getId());
			ModalidadCriterioTipoPregunta mctp = servicioEvaluacion.obtenerModalidadTipoPreguntaXModalidadYCriterio(
					evaluacionProyecto.getProyecto().getModalidad().getId(), ce.getCriterio().getId());
			cEvaluacion.setMctp(mctp);

			if (ce.getTipoPregunta().getMaximo() != null) {
				cEvaluacion.setMaximmoVista(new Double(ce.getTipoPregunta().getMaximo()));
			}
			if (ce.getTipoPregunta().getMinimo() != null) {
				cEvaluacion.setMinimoVista(new Double(ce.getTipoPregunta().getMinimo()));
			}
			cEvaluacion.setServicioEvaluacion(servicioEvaluacion);
			listaCC.add(cEvaluacion);
		}
		if (evaluacionProyecto != null && evaluacionProyecto.getConcepto() == null) {
			evaluacionProyecto.setConcepto(new TipoConcepto());
		}
		tablaCC = new HtmlDataTable();
	}
	
	@SuppressWarnings("rawtypes")
	public boolean validarEvaluacion() {
		boolean datosCompletos = true;
		for (Iterator i = listaCC.iterator(); i.hasNext();) {
			FacesContext context = FacesContext.getCurrentInstance();
			CalificacionEvaluacion ce = (CalificacionEvaluacion) i.next();
			ModalidadCriterioTipoPregunta mctp = ce.getMctp();
			if (ce != null && ce.getCuantitativa() == null) {
				FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Por favor, indique la calificación cuantitativa del criterio: "
								+ mctp.getCriterio().getNombre() + ".",
						"mensaje");
				context.addMessage(null, mensaje);
				datosCompletos = false;
			}
			if (ce != null && esCadenaVacia(ce.getCualitativa())) {
				FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Por favor, indique la calificación cualitativa del criterio: " + mctp.getCriterio().getNombre()
								+ ".",
						"mensaje");
				context.addMessage(null, mensaje);
				datosCompletos = false;
			}
		}
		if (datosCompletos) {
			for (Iterator i = listaCC.iterator(); i.hasNext();) {
				CalificacionEvaluacion ce = (CalificacionEvaluacion) i.next();
				ModalidadCriterioTipoPregunta mctp = ce.getMctp();
				if ((mctp.getTipoPregunta().getMaximo()) != null && ce.getCuantitativa() != null) {
					if (mctp.getTipoPregunta().getPagina().equals("/pages/Evaluacion/seleccionUnica.jsp")) {
						OpcionMultiple opcionMultiple = servicioEvaluacion
								.obtenerOpcionMultiplePorId(ce.getCuantitativa().longValue());
						if (new Double(mctp.getTipoPregunta().getMaximo()).doubleValue() < opcionMultiple.getValor()) {
							sesion.setAttribute("mensaje",
									"el valor de la calificacion para el criterio " + mctp.getCriterio().getNombre()
											+ " excede el maximo " + mctp.getTipoPregunta().getMaximo());
							FacesContext context = FacesContext.getCurrentInstance();
							FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"el valor de la calificacion para el criterio " + mctp.getCriterio().getNombre()
											+ " excede el maximo " + mctp.getTipoPregunta().getMaximo(),
									"mensaje");
							context.addMessage("datosGuardados", mensaje);
							return false;
						}
					} else if (new Double(mctp.getTipoPregunta().getMaximo()).doubleValue() < ce.getCuantitativa()
							.doubleValue()) {
						sesion.setAttribute("mensaje",
								"el valor de la calificacion para el criterio " + mctp.getCriterio().getNombre()
										+ " excede el maximo " + mctp.getTipoPregunta().getMaximo());
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"el valor de la calificacion para el criterio " + mctp.getCriterio().getNombre()
										+ " excede el maximo " + mctp.getTipoPregunta().getMaximo(),
								"mensaje");
						context.addMessage("datosGuardados", mensaje);
						return false;
					}
				}
				if ((mctp.getTipoPregunta().getMinimo()) != null && ce.getCuantitativa() != null) {
					if (new Double(mctp.getTipoPregunta().getMinimo()).doubleValue() > ce.getCuantitativa()
							.doubleValue()) {
						sesion.setAttribute("mensaje",
								"el valor de la calificacion para el criterio " + mctp.getCriterio().getNombre()
										+ " excede el minimo " + mctp.getTipoPregunta().getMinimo());
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"el valor de la calificacion para el criterio " + mctp.getCriterio().getNombre()
										+ " excede el minimo " + mctp.getTipoPregunta().getMinimo(),
								"mensaje");
						context.addMessage("datosGuardados", mensaje);
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public boolean estaInCompleto() {
		if (evaluacionProyecto.getConcepto() != null && evaluacionProyecto.getConcepto().getId() != null
				&& !evaluacionProyecto.getConcepto().getId().equals("")) {
			return true;
		}
		for (Iterator i = listaCC.iterator(); i.hasNext();) {
			CalificacionEvaluacion ce = (CalificacionEvaluacion) i.next();
			ModalidadCriterioTipoPregunta mctp = servicioEvaluacion.obtenerModalidadTipoPreguntaXModalidadYCriterio(
					evaluacionProyecto.getProyecto().getModalidad().getId(), ce.getCriterio().getId());
			if (mctp.getTipoPregunta().getSeTieneParaPromedio().equals("S")) {

				if ((ce != null && ce.getCualitativa() != null && !ce.getCualitativa().equals(""))
						|| ce.getCuantitativa() != null) {
					System.out.println("la caliicacion" + ce.getCriterio().getNombre());
					return true;
				}
			} else {
				if ((ce != null && ce.getCualitativa() != null && !ce.getCualitativa().equals(""))) {
					System.out.println("la caliicacion" + ce.getCriterio().getNombre());
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean estaCompleto() {
		/*
		 * if(evaluacionProyecto.getConcepto()==null ||
		 * evaluacionProyecto.getConcepto().getId()==null ||
		 * evaluacionProyecto.getConcepto().getId().equals("") ) {
		 * System.out.println("concepto balnco"); return false; }
		 */
		for (Iterator i = listaCC.iterator(); i.hasNext();) {
			CalificacionEvaluacion ce = (CalificacionEvaluacion) i.next();
			ModalidadCriterioTipoPregunta mctp = servicioEvaluacion.obtenerModalidadTipoPreguntaXModalidadYCriterio(
					evaluacionProyecto.getProyecto().getModalidad().getId(), ce.getCriterio().getId());
			boolean cualitativa = false;
			boolean cuantitatia = false;
			if (mctp.getTipoPregunta().getPagina().equals("/pages/Evaluacion/cualitativaCuantitativa.jsp")) {
				cuantitatia = true;
				cualitativa = true;
			}
			if (mctp.getTipoPregunta().getPagina().equals("/pages/Evaluacion/cuantitativa.jsp")
					|| mctp.getTipoPregunta().getPagina().equals("/pages/Evaluacion/seleccionUnica.jsp")) {
				cuantitatia = true;
			}
			if (mctp.getTipoPregunta().getPagina().equals("/pages/Evaluacion/cualitativa.jsp")) {
				cualitativa = true;
			}
			if (cualitativa) {
				if (ce == null || ce.getCualitativa() == null || ce.getCualitativa().equals("")) {
					System.out.println("esta blanco" + mctp.getCriterio().getNombre());
					return false;
				}
			}
			if (cuantitatia) {
				if (mctp.getTipoPregunta().getSeTieneParaPromedio().equals("S")) {
					if (ce == null || ce.getCuantitativa() == null) {
						System.out.println("esta blanco" + mctp.getCriterio().getNombre());
						return false;
					}

				}
			}
		}
		return true;
	}
	
	public void ultimaEvaluacion() {
		listaCriterios = servicioModalidad
				.listaModalidadCriterioTipoPregunta(evaluacionProyecto.getProyecto().getModalidad());
		listaCC = new Vector();
		for (Iterator i = listaCriterios.iterator(); i.hasNext();) {
			ModalidadCriterioTipoPregunta ce = (ModalidadCriterioTipoPregunta) i.next();
			CalificacionEvaluacion cEvaluacion = servicioModalidad
					.obtenrCalificacionEvaluacionXCriterioYProyectoYEvaluador(ce.getCriterio(),
							evaluacionProyecto.getProyecto(), evaluacionProyecto.getEvaluador());
			if (cEvaluacion == null) {
				cEvaluacion = new CalificacionEvaluacion();
				cEvaluacion.setCriterio(ce.getCriterio());
				cEvaluacion.setCuantitativa(new Float(0));

				cEvaluacion.setProyectoEvaluador(evaluacionProyecto);
			} else {
				if (ce.getTipoPregunta().getId().longValue() == TipoPregunta.seleccionUnica
						&& cEvaluacion.getCuantitativa() != null) {
					OpcionMultiple om = servicioEvaluacion.obtenerOpcionMultipleXTipoPreguntaYValor(
							ce.getTipoPregunta().getId(), new Double(cEvaluacion.getCuantitativa().doubleValue()));
					if (om != null) {

						cEvaluacion.setCuantitativa(new Float(om.getId().floatValue()));
						cEvaluacion.setCualitativa(om.getNombre());
						cEvaluacion.setCalificacionSeleccion(om.getValor());
					}
				}
			}

			if (ce.getTipoPregunta().getMaximo() != null) {
				cEvaluacion.setMaximmoVista(new Double(ce.getTipoPregunta().getMaximo()));
			}
			if (ce.getTipoPregunta().getMinimo() != null) {
				cEvaluacion.setMinimoVista(new Double(ce.getTipoPregunta().getMinimo()));
			}
			listaCC.add(cEvaluacion);
		}
	}
	
	public void guardarYContinuar() {
		calcularPromedioCalificaciones();
		evaluacionProyecto.setCalificacionFinal(new Float(promedioCalificacion));
		if (evaluacionProyecto.getConcepto().getId() != null) {
			TipoConcepto tc = (TipoConcepto) servicioGeneral.obtenerObjeto(new TipoConcepto(),
					evaluacionProyecto.getConcepto().getId());
			evaluacionProyecto.setConcepto(tc);
		} else {
			evaluacionProyecto.setConcepto(null);
		}
		boolean isPrimeraVez = false;
		List listaCCConCalificacinesAntes = new Vector();
		for (Iterator i = listaCC.iterator(); i.hasNext();) {
			CalificacionEvaluacion ce = (CalificacionEvaluacion) i.next();
			CalificacionEvaluacion ceAux = new CalificacionEvaluacion();
			ceAux.setId(ce.getId());
			if (ceAux.getId() == null) {
				isPrimeraVez = true;
			}
			ceAux.setCriterio(ce.getCriterio());
			ceAux.setCualitativa(ce.getCualitativa());
			ceAux.setCuantitativa(ce.getCuantitativa());
			ceAux.setMaximmoVista(ce.getMaximmoVista());
			ceAux.setMinimoVista(ce.getMinimoVista());
			ceAux.setProyectoEvaluador(ce.getProyectoEvaluador());
			listaCCConCalificacinesAntes.add(ceAux);
			ce = servicioEvaluacion.convertirCalificaion(ce);
		}

		if (estaInCompleto()) {
			evaluacionProyecto.setEstado("P");
		} else {
			evaluacionProyecto.setEstado("N");
		}

		// Se guarda historico.
		HistoricoEstadoEvaluacion historicoEstadoEvaluacion = new HistoricoEstadoEvaluacion();
		historicoEstadoEvaluacion.asignarProyectoEvaluador(evaluacionProyecto);
		historicoEstadoEvaluacion.setResponsable(getPersonaActual());
		historicoEstadoEvaluacion.setObservaciones("Devolución por coordinador de evaluación.");
		servicioGeneral.guardarObjeto(historicoEstadoEvaluacion);

		evaluacionProyecto.setCalificaciones(null);
		evaluacionProyecto.setFecha(new Date());
		evaluacionProyecto.setTipoEvaluacion(tipoEvaluacion);

		for (Iterator i = listaCC.iterator(); i.hasNext();) {
			CalificacionEvaluacion ce = (CalificacionEvaluacion) i.next();
			servicioGeneral.guardarObjeto(ce);
		}
		evaluacionProyecto.setCalificaciones(null);
		servicioGeneral.guardarObjeto(evaluacionProyecto);
		if (evaluacionProyecto.getConcepto() == null) {
			evaluacionProyecto.setConcepto(new TipoConcepto());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "La evaluación ha sido guardada con éxito",
				"mensaje");
		context.addMessage("datosGuardados", mensaje);
	}
	
	public boolean guardarEvaluaciones() {
		if (!validarEvaluacion()) {
			return false;
		}
		calcularPromedioCalificaciones();
		evaluacionProyecto.setCalificacionFinal(new Float(promedioCalificacion));
		if (evaluacionProyecto.getConcepto().getId() != null) {
			TipoConcepto tc = (TipoConcepto) servicioGeneral.obtenerObjeto(new TipoConcepto(),
					evaluacionProyecto.getConcepto().getId());
			evaluacionProyecto.setConcepto(tc);
		} else {
			evaluacionProyecto.setConcepto(null);
		}
		boolean isPrimeraVez = false;
		List listaCCConCalificacinesAntes = new Vector();
		for (Iterator i = listaCC.iterator(); i.hasNext();) {
			CalificacionEvaluacion ce = (CalificacionEvaluacion) i.next();
			CalificacionEvaluacion ceAux = new CalificacionEvaluacion();
			ceAux.setId(ce.getId());
			if (ceAux.getId() == null) {
				isPrimeraVez = true;
			}
			ceAux.setCriterio(ce.getCriterio());
			ceAux.setCualitativa(ce.getCualitativa());
			ceAux.setCuantitativa(ce.getCuantitativa());
			ceAux.setMaximmoVista(ce.getMaximmoVista());
			ceAux.setMinimoVista(ce.getMinimoVista());
			ceAux.setProyectoEvaluador(ce.getProyectoEvaluador());
			listaCCConCalificacinesAntes.add(ceAux);
			ce = servicioEvaluacion.convertirCalificaion(ce);
		}

		if (estaCompleto()) {
			evaluacionProyecto.setEstado("S");
		} else {
			if (estaInCompleto()) {
				evaluacionProyecto.setEstado("P");
			} else {
				evaluacionProyecto.setEstado("N");
			}
		}

		// Se guarda historico
		HistoricoEstadoEvaluacion historicoEstadoEvaluacion = new HistoricoEstadoEvaluacion();
		historicoEstadoEvaluacion.asignarProyectoEvaluador(evaluacionProyecto);
		historicoEstadoEvaluacion.setResponsable(getPersonaActual());
		historicoEstadoEvaluacion.setObservaciones("Devolución por coordinador de evaluación.");
		servicioGeneral.guardarObjeto(historicoEstadoEvaluacion);

		evaluacionProyecto.setCalificaciones(null);
		evaluacionProyecto.setFecha(new Date());
		evaluacionProyecto.setTipoEvaluacion(tipoEvaluacion);

		for (Iterator i = listaCC.iterator(); i.hasNext();) {
			CalificacionEvaluacion ce = (CalificacionEvaluacion) i.next();
			servicioGeneral.guardarObjeto(ce);
		}

		if (isPrimeraVez) {
			ultimaEvaluacion();
		} else {
			listaCC = listaCCConCalificacinesAntes;
		}
		evaluacionProyecto.setCalificaciones(null);
		servicioGeneral.guardarObjeto(evaluacionProyecto);
		if (evaluacionProyecto.getConcepto() == null) {
			evaluacionProyecto.setConcepto(new TipoConcepto());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"La evaluación ha sido guardada con éxito", "mensaje");
		context.addMessage("datosGuardados", mensaje);
		cargarValoresGenerales();
		return true;
	}
	
	public String getCriterioModalidadTipoPreguntaMinimo() {
		CalificacionEvaluacion ce = (CalificacionEvaluacion) tablaCC.getRowData();
		ModalidadCriterioTipoPregunta mctp = servicioEvaluacion.obtenerModalidadTipoPreguntaXModalidadYCriterio(
				evaluacionProyecto.getProyecto().getModalidad().getId(), ce.getCriterio().getId());
		System.out.println(mctp.getTipoPregunta().getPagina());
		return mctp.getTipoPregunta().getMinimo();

	}

	public String getCriterioModalidadTipoPreguntaMaximo() {
		CalificacionEvaluacion ce = (CalificacionEvaluacion) tablaCC.getRowData();
		ModalidadCriterioTipoPregunta mctp = servicioEvaluacion.obtenerModalidadTipoPreguntaXModalidadYCriterio(
				evaluacionProyecto.getProyecto().getModalidad().getId(), ce.getCriterio().getId());
		System.out.println(mctp.getTipoPregunta().getPagina());
		return mctp.getTipoPregunta().getMaximo();
	}
	
	public void calcularPromedioInicial() {
		calcularPromedioCalificaciones();
	}

	public List getListaCriterios() {
		return listaCriterios;
	}

	public void setListaCriterios(List listaCriterios) {
		this.listaCriterios = listaCriterios;
	}

	public List getListaCC() {
		return listaCC;
	}

	public void setListaCC(List listaCC) {
		this.listaCC = listaCC;
	}

	public ProyectoEvaluador getEvaluacionProyecto() {
		return evaluacionProyecto;
	}

	public void setEvaluacionProyecto(ProyectoEvaluador evaluacionProyecto) {
		this.evaluacionProyecto = evaluacionProyecto;
	}

	public float getPromedioCalificacion() {
		return promedioCalificacion;
	}

	public void setPromedioCalificacion(float promedioCalificacion) {
		this.promedioCalificacion = promedioCalificacion;
	}

	public float getMaximaCalificacion() {
		return maximaCalificacion;
	}

	public void setMaximaCalificacion(float maximaCalificacion) {
		this.maximaCalificacion = maximaCalificacion;
	}

	public HtmlDataTable getTablaCC() {
		return tablaCC;
	}

	public void setTablaCC(HtmlDataTable tablaCC) {
		this.tablaCC = tablaCC;
	}

	public SelectItem[] getRecomendaciones() {
		return recomendaciones;
	}

	public void setRecomendaciones(SelectItem[] recomendaciones) {
		this.recomendaciones = recomendaciones;
	}

	public boolean isMotrarAprobacion() {
		return motrarAprobacion;
	}

	public void setMotrarAprobacion(boolean motrarAprobacion) {
		this.motrarAprobacion = motrarAprobacion;
	}

	public float getPromedioPonderado() {
		return promedioPonderado;
	}

	public void setPromedioPonderado(float promedioPonderado) {
		this.promedioPonderado = promedioPonderado;
	}

	public String getTipoEvaluacion() {
		return tipoEvaluacion;
	}

	public void setTipoEvaluacion(String tipoEvaluacion) {
		this.tipoEvaluacion = tipoEvaluacion;
	}
	
}
