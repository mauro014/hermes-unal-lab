package co.edu.unal.hermes.vista.seguimiento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoCarta;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.ObservacionSeguimiento;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoCarta;
import co.edu.unal.hermes.modelo.ProyectoCompromiso;
import co.edu.unal.hermes.modelo.TipoCarta;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.modelo.seguimiento.DetalleAdicionPresupuesto;
import co.edu.unal.hermes.modelo.seguimiento.DetalleCambioRubro;
import co.edu.unal.hermes.modelo.seguimiento.ProyectoProrroga;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudAdicionPresupuestal;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudInvestigador;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudProrrogaInvestigador;
import co.edu.unal.hermes.modelo.seguimiento.TipoSolicitud;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorBaseSeguimiento extends ManejadorBase {
	private static final long serialVersionUID = 6804469657452777609L;
	protected List<ProyectoCarta> listaCartasProyecto = new ArrayList<ProyectoCarta>();
	protected Proyecto proyectoActual = new Proyecto();
	protected ProyectoCarta proyectoCartaSeleccionado;
	protected Date fechaFinalOriginalProyecto;
	protected Date fechaFinalProyecto;

	protected Persona coordinadorRequisitos;
	protected Persona coordinadorSeguimientoProyecto;
	protected Persona coordinadorEvaluacion;

	protected ProyectoProrroga prorrogaSeleccionada;

	protected String codigoQuipu = "";
	protected boolean mostrarCodigoQuipu = false;

	protected String proyectoId;
	protected String estadoProyecto;

	protected int duracionAcumuladaVista = 0;
	protected int duracionDiasAcumuladaVista = 0;

	protected Locale localidad;
	protected SimpleDateFormat formatoFecha;
	protected Long totalMesesSuspencion;
	protected Long totalDiasSuspencion;
	protected List<DetalleCambioRubro> detallesCambiosRubro;
	protected List<Object[]> informacionFinaciera;
	protected List<SolicitudAdicionPresupuestal> adicionesPresupuestales;

	protected List<ObservacionSeguimiento> observacionesSeguimiento;

	public ManejadorBaseSeguimiento() {
		super();
	}

	protected void cargarObservacionesSeguimiento(Long id) {
		observacionesSeguimiento = servicioProyecto.obtenerObservacionesSeguimientoXProyecto(new Proyecto(id));
	}

	public void cargarFormatoFecha() {

		localidad = new Locale("sp", "co");
		formatoFecha = new SimpleDateFormat("MMMM dd yyyy", localidad);
	}

	// Reporte de legalización de proyectos
	public void reporteLegalizacion() {
		Long id = proyectoActual.getId();
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", id.toString());
		r.setFormato(ReporteBirt.FORMATO_PDF);

		if (proyectoActual.getModalidad().isEsConvocatoriaLegalizacion()) {
			if (proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.ACTIVO)
					|| proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.APROBADO)
					|| proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.POR_FINALIZAR)
					|| proyectoActual.getEstadoProyecto().getId().equals(EstadoProyecto.FINALIZADO)) {

				r.setNombreReporte("proyecto/FICHA_LEGALIZACION");
			} else {
				r.setNombreReporte("proyecto/FICHA_LEGALIZACION_NAP");
			}
		} else {
			r.setNombreReporte("proyecto/FICHA_FORMALIZACION");
		}

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
	}

	public void cargarCartasProyecto(Long idProyectoActual) {
		listaCartasProyecto = servicioProyecto.obtenerCartasXProyecto(new Proyecto(idProyectoActual));

		Iterator<ProyectoCarta> i = listaCartasProyecto.iterator();

		// Se carga subtipo de carta.
		while (i.hasNext()) {
			ProyectoCarta proyectoCarta = i.next();
			if (proyectoCarta.getSubtipo() != null) {
				List<CorreoPlantilla> correosPlantilla = servicioGeneral.obtenerObjetos(CorreoPlantilla.class,
						"from CorreoPlantilla cp where cp.id = '" + proyectoCarta.getSubtipo() + "'");
				if (correosPlantilla != null && !correosPlantilla.isEmpty()) {
					CorreoPlantilla correoPlantilla = correosPlantilla.get(0);
					proyectoCarta.setSubtipoNombre(correoPlantilla.getDescripcion());
				}
			}
		}

	}

	public void obtenerCoordinadorSeguimiento(Long idProyectoActual) {
		coordinadorSeguimientoProyecto = this.servicioProyecto.obtenerCoordinadorProyecto(idProyectoActual);
	}

	public void obtenerCoordinadorEvaluacion(Long idProyectoActual) {
		coordinadorEvaluacion = this.servicioProyecto.obtenerCoordinadorEvaluacionProyecto(idProyectoActual);
	}

	public void obtenerCoordinadorRequisitos(Long idProyectoActual) {
		coordinadorRequisitos = this.servicioProyecto.obtenerCoordinadorRequisitosProyecto(idProyectoActual);
	}

	public void calcularPeriodoSuspension() {
		totalMesesSuspencion = 0L;
		totalDiasSuspencion = 0L;
		List<ProyectoProrroga> listaProrrogasProyecto = proyectoActual.getListaProrrogas();

		Iterator<ProyectoProrroga> i = listaProrrogasProyecto.iterator();
		while (i.hasNext()) {
			ProyectoProrroga proyectoProrroga = i.next();
			if (proyectoProrroga.getEsPeriodoSuspension() != null
					&& "S".equals(proyectoProrroga.getEsPeriodoSuspension())) {
				totalMesesSuspencion += proyectoProrroga.getDuracion();
				totalDiasSuspencion += proyectoProrroga.getDias();
			}

		}
	}

	protected void cargarDatosAdicionalesSolicitud(List<Solicitud> solicitudes) {
		List<ProyectoCarta> listaCartasTemporal = new ArrayList<ProyectoCarta>();
		listaCartasTemporal.addAll(listaCartasProyecto);
		for (int i = 0; i < proyectoActual.getListaSolicitudesEnviadas().size(); i++) {
			Solicitud solicitud = (Solicitud) proyectoActual.getListaSolicitudesEnviadas().get(i);
			if (solicitud != null && solicitud.getRespuesta() == null) {
				solicitud.setRespuesta("");
			}

			// Se valida si la solicitud ya tiene una carta
			Iterator<ProyectoCarta> k = listaCartasTemporal.iterator();
			while (k.hasNext()) {
				ProyectoCarta proyectoCarta = k.next();
				if (proyectoCarta.getIdSolicitud() != null) {
					if (proyectoCarta.getIdSolicitud().equals(solicitud.getId())) {
						listaCartasTemporal.remove(proyectoCarta);
						solicitud.setTieneCarta(true);
						break;
					}
				}
			}
			if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CAMBIO_INVESTIGADOR_PRINCIPAL)) {
				List<SolicitudInvestigador> solicitudesInvestigador = servicioSolicitudes
						.obtenerSolicitudInvestigador(solicitud);
				solicitud.setSolicitudInvestigadorPrincipal(solicitudesInvestigador);
			}
			if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.PRORROGA)) {
				List<SolicitudProrrogaInvestigador> solicitudesInvestigador = servicioSolicitudes
						.obtenerSolicitudProrrogaInvestigadores(solicitud);
				solicitud.setSolicitudesProrrogaInvestigadorIntegranes(solicitudesInvestigador);
			}
			if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CAMBIO_INTEGRANTES)
					|| solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CERTIFICADO_MOVILIZACION)) {
				List<SolicitudInvestigador> solicitudesInvestigador = servicioSolicitudes
						.obtenerSolicitudInvestigador(solicitud);
				solicitud.setSolicitudesInvestigadorIntegranes(solicitudesInvestigador);
			}
			if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.ADICION_PRESPUESTAL)) {
				SolicitudAdicionPresupuestal adicion = servicioSolicitudes
						.obtenerSolicitudAdicionPresupuestal(solicitud);
				for (DetalleAdicionPresupuesto detalle : adicion.getDetalleAdicionPresupuesto()) {
					if (!(detalle.getTipoRubro().getDescripcion() == null)
							&& detalle.getTipoRubro().getDescripcion().equals("GASTOS_CP_2022")) {
						detalle.setTipoRubro((TipoRubro) servicioGeneral.obtenerObjetoYPadre(new TipoRubro(),
								detalle.getTipoRubro().getId()));
						detalle.getTipoRubro().setPadre((TipoRubro) servicioGeneral.obtenerObjetoYPadre(new TipoRubro(),
								detalle.getTipoRubro().getPadre().getId()));
					} else if ("142,145,144,143,82,141,135,7,120,".contains(detalle.getTipoRubro().getId() + ",")) {
						TipoRubro padreProvisional = (TipoRubro) servicioGeneral.obtenerObjetoXID(TipoRubro.class, "53")
								.get(0);
						detalle.getTipoRubro().setPadre(padreProvisional);
						detalle.getTipoRubro().getPadre().setPadre(padreProvisional);
					} else {
						TipoRubro padreProvisional = new TipoRubro();
						padreProvisional.setDescripcion("Rubro previo 2022");
						padreProvisional.setNombre("Rubro previo 2022");
						detalle.getTipoRubro().setPadre(padreProvisional);
						detalle.getTipoRubro().getPadre().setPadre(padreProvisional);
					}

				}
				solicitud.setAdicionPresupuesto(adicion);
			}
			if (solicitud.getTipoSolicitud().getId().equals(TipoSolicitud.CAMBIO_RUBROS)) {
				for (DetalleCambioRubro detalle : solicitud.getDetalleCambioRubro()) {
					if (detalle.getGasto().getTipoRubro().getDescripcion() != null
							&& detalle.getGasto().getTipoRubro().getDescripcion().equals("GASTOS_CP_2022")) {
						detalle.getGasto().setTipoRubro((TipoRubro) servicioGeneral.obtenerObjetoYPadre(new TipoRubro(),
								detalle.getGasto().getTipoRubro().getId()));
						detalle.getGasto().getTipoRubro()
								.setPadre((TipoRubro) servicioGeneral.obtenerObjetoYPadre(new TipoRubro(),
										detalle.getGasto().getTipoRubro().getPadre().getId()));
					} else if (",142,145,144,143,82,141,135,7,120,"
							.contains("," + detalle.getGasto().getTipoRubro().getId() + ",")) {
						TipoRubro padreProvisional = (TipoRubro) servicioGeneral.obtenerObjetoXID(TipoRubro.class, "53")
								.get(0);
						detalle.getGasto().getTipoRubro().setPadre(padreProvisional);
						detalle.getGasto().getTipoRubro().getPadre().setPadre(padreProvisional);
					} else {
						TipoRubro padreProvisional = new TipoRubro();
						padreProvisional.setDescripcion("Rubro previo 2022");
						padreProvisional.setNombre("Rubro previo 2022");
						detalle.getGasto().getTipoRubro().setPadre(padreProvisional);
						detalle.getGasto().getTipoRubro().getPadre().setPadre(padreProvisional);

					}
					if (detalle.getTipoRubro().getDescripcion() != null
							&& detalle.getTipoRubro().getDescripcion().equals("GASTOS_CP_2022")) {
						detalle.setTipoRubro((TipoRubro) servicioGeneral.obtenerObjetoYPadre(new TipoRubro(),
								detalle.getTipoRubro().getId()));
						detalle.getTipoRubro().setPadre((TipoRubro) servicioGeneral.obtenerObjetoYPadre(new TipoRubro(),
								detalle.getTipoRubro().getPadre().getId()));
						System.out.println(detalle.getTipoRubro().getDescripcion());
					} else if (",142,145,144,143,82,141,135,7,120,"
							.contains("," + detalle.getTipoRubro().getId() + ",")) {
						TipoRubro padreProvisional = (TipoRubro) servicioGeneral.obtenerObjetoXID(TipoRubro.class, "53")
								.get(0);
						detalle.getTipoRubro().setPadre(padreProvisional);
						detalle.getTipoRubro().getPadre().setPadre(padreProvisional);

					} else {
						TipoRubro padreProvisional = new TipoRubro();
						padreProvisional.setDescripcion("Rubro previo 2022");
						padreProvisional.setNombre("Rubro previo 2022");
						detalle.getTipoRubro().setPadre(padreProvisional);
						detalle.getTipoRubro().getPadre().setPadre(padreProvisional);
					}
				}
				System.out.println(solicitud.getDetalleCambioRubro());
			}
		}
	}

	public void descargarCartaProyecto() {
		if (proyectoCartaSeleccionado != null) {
			descargarArchivoGenerico("HER_PROYECTO_CARTA", proyectoCartaSeleccionado.getId().toString(),
					proyectoCartaSeleccionado.getId() + ".pdf");
		}
	}

	public void borrarCartaProyecto() {
		if (proyectoCartaSeleccionado != null) {

			Persona responsableEliminacion = (Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL);

			proyectoCartaSeleccionado.setEstadoCarta(new EstadoCarta(EstadoCarta.BORRADO));
			if(proyectoCartaSeleccionado.getCarta().getId().equals(TipoCarta.CERTIFICADO_MOVILIZACION)) {
				proyectoCartaSeleccionado.setEstadoCarta(new EstadoCarta(EstadoCarta.SIN_ENVIAR));
			}
			proyectoCartaSeleccionado.setFechaEliminacion(new Date());
			proyectoCartaSeleccionado.setResponsableEliminacion(responsableEliminacion);
			servicioGeneral.guardarObjeto(proyectoCartaSeleccionado);
			cargarCartasProyecto(proyectoActual.getId());

		}
	}

	public void cargarFechasProyecto(Proyecto proyectoActual) {
		try {
			int duracionInicial = proyectoActual.getDuracion().intValue();

			int acumuladoDuracion = 0;
			int acumuladoDuracionDias = 0;

			if (proyectoActual.getListaProrrogas() != null) {
				for (int j = 0; j < proyectoActual.getListaProrrogas().size(); j++) {
					ProyectoProrroga prorroga = (ProyectoProrroga) proyectoActual.getListaProrrogas().get(j);
					acumuladoDuracion += prorroga.getDuracion().intValue();
					acumuladoDuracionDias += prorroga.getDias().intValue();
					if (proyectoActual.getFechaTentativaInicio() != null) {
						Date fechaFinal = new Date(proyectoActual.getFechaTentativaInicio().getTime());

						fechaFinal.setMonth(fechaFinal.getMonth() + proyectoActual.getDuracion() + acumuladoDuracion);

						Calendar calendar = Calendar.getInstance();
						calendar.setTime(fechaFinal);
						calendar.add(Calendar.DAY_OF_YEAR, acumuladoDuracionDias);
						fechaFinal = calendar.getTime();

						prorroga.setNuevaFechaFinal(fechaFinal);
					}
				}
			}

			int mesesAdicionales = 0;
			double mesesAdicionalesFraccion;
			double diasAdicionales = 0;

			if (acumuladoDuracionDias > 30) {
				if (acumuladoDuracionDias % 30 == 0) {
					mesesAdicionalesFraccion = acumuladoDuracionDias / 30;
					mesesAdicionales = (int) Math.floor(mesesAdicionalesFraccion);
				} else {
					mesesAdicionalesFraccion = (double) acumuladoDuracionDias / 30.0;
					mesesAdicionales = (int) Math.floor(mesesAdicionalesFraccion);

					diasAdicionales = Math.ceil((mesesAdicionalesFraccion - mesesAdicionales) * 30);
				}
				duracionAcumuladaVista = (new Integer(duracionInicial + acumuladoDuracion) + mesesAdicionales);
				duracionDiasAcumuladaVista = ((int) diasAdicionales);

			} else {
				duracionAcumuladaVista = (new Integer(duracionInicial + acumuladoDuracion));
				duracionDiasAcumuladaVista = (acumuladoDuracionDias);
			}

			proyectoActual.setDuracionAcumulada(new Integer(duracionInicial + acumuladoDuracion));
			proyectoActual.setDuracionDiasAcumulada(acumuladoDuracionDias);

			fechaFinalOriginalProyecto = null;
			if (proyectoActual.getFechaTentativaInicio() != null) {

				// Se calcula la fecha final antes de prorrrogas
				fechaFinalOriginalProyecto = new Date(proyectoActual.getFechaTentativaInicio().getTime());
				fechaFinalOriginalProyecto.setMonth(fechaFinalOriginalProyecto.getMonth() + duracionInicial);

				// Se calcula la nueva fecha final del proyecto
				fechaFinalProyecto = new Date(proyectoActual.getFechaTentativaInicio().getTime());
				fechaFinalProyecto
						.setMonth(fechaFinalProyecto.getMonth() + proyectoActual.getDuracionAcumulada().intValue());

				// Se agregan los días
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fechaFinalProyecto);
				calendar.add(Calendar.DAY_OF_YEAR, proyectoActual.getDuracionDiasAcumulada());
				fechaFinalProyecto = calendar.getTime();
			}
			for (ProyectoCompromiso compromiso : proyectoActual.getCompromisosProyecto()) {
				if (compromiso.getTipoInforme().getId().equals(2L) && compromiso.getFechaVencProrroga() == null) {
					compromiso.setFechaVencProrroga(fechaFinalProyecto);
				}
			}
			proyectoActual.setFechaFinalizacion(fechaFinalOriginalProyecto);
		} catch (Exception e) {

		}
	}

	public SimpleDateFormat getFormatoFecha() {
		return formatoFecha;
	}

	public void setFormatoFecha(SimpleDateFormat formatoFecha) {
		this.formatoFecha = formatoFecha;
	}

	public boolean isMostrarCodigoQuipu() {
		return mostrarCodigoQuipu;
	}

	public void setMostrarCodigoQuipu(boolean mostrarCodigoQuipu) {
		this.mostrarCodigoQuipu = mostrarCodigoQuipu;
	}

	public String getCodigoQuipu() {
		return codigoQuipu;
	}

	public void setCodigoQuipu(String codigoQuipu) {
		this.codigoQuipu = codigoQuipu;
	}

	public String getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}

	public String getEstadoProyecto() {
		return estadoProyecto;
	}

	public void setEstadoProyecto(String estadoProyecto) {
		this.estadoProyecto = estadoProyecto;
	}

	public void setProyectoCartaSeleccionado(ProyectoCarta proyectoCartaSeleccionado) {
		this.proyectoCartaSeleccionado = proyectoCartaSeleccionado;
	}

	public ProyectoCarta getProyectoCartaSeleccionado() {
		return proyectoCartaSeleccionado;
	}

	public void setProrrogaSeleccionada(ProyectoProrroga prorrogaSeleccionada) {
		this.prorrogaSeleccionada = prorrogaSeleccionada;
	}

	public ProyectoProrroga getProrrogaSeleccionada() {
		return prorrogaSeleccionada;
	}

	public List<ProyectoCarta> getListaCartasProyecto() {
		return listaCartasProyecto;
	}

	public void setListaCartasProyecto(List<ProyectoCarta> listaCartasProyecto) {
		this.listaCartasProyecto = listaCartasProyecto;
	}

	public Persona getCoordinadorRequisitos() {
		return coordinadorRequisitos;
	}

	public Persona getCoordinadorSeguimientoProyecto() {
		return coordinadorSeguimientoProyecto;
	}

	public void setCoordinadorSeguimientoProyecto(Persona coordinadorSeguimientoProyecto) {
		this.coordinadorSeguimientoProyecto = coordinadorSeguimientoProyecto;
	}

	public Persona getCoordinadorSeguimientoEvaluacionProyecto() {
		return coordinadorEvaluacion;
	}

	public void setCoordinadorSeguimientoEvaluacionProyecto(Persona coordinadorSeguimientoEvaluacionProyecto) {
		this.coordinadorEvaluacion = coordinadorSeguimientoEvaluacionProyecto;
	}

	/**
	 * @return the detallesCambiosRubro
	 */
	public List<DetalleCambioRubro> getDetallesCambiosRubro() {
		return detallesCambiosRubro;
	}

	public List<Object[]> getInformacionFinaciera() {
		return informacionFinaciera;
	}

	public List<ObservacionSeguimiento> getObservacionesSeguimiento() {
		return observacionesSeguimiento;
	}

	public List<SolicitudAdicionPresupuestal> getAdicionesPresupuestales() {
		return adicionesPresupuestales;
	}

	public void setAdicionesPresupuestales(List<SolicitudAdicionPresupuestal> adicionesPresupuestales) {
		this.adicionesPresupuestales = adicionesPresupuestales;
	}

	public int getDuracionAcumuladaVista() {
		return duracionAcumuladaVista;
	}

	public void setDuracionAcumuladaVista(int duracionAcumuladaVista) {
		this.duracionAcumuladaVista = duracionAcumuladaVista;
	}

	public int getDuracionDiasAcumuladaVista() {
		return duracionDiasAcumuladaVista;
	}

	public void setDuracionDiasAcumuladaVista(int duracionDiasAcumuladaVista) {
		this.duracionDiasAcumuladaVista = duracionDiasAcumuladaVista;
	}

	public Proyecto getProyectoActual() {
		return proyectoActual;
	}

	public void setProyectoActual(Proyecto proyectoActual) {
		this.proyectoActual = proyectoActual;
	}

	public Date getFechaFinalProyecto() {
		return fechaFinalProyecto;
	}

	public Date getFechaFinalOriginalProyecto() {
		return fechaFinalOriginalProyecto;
	}

	public Locale getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Locale localidad) {
		this.localidad = localidad;
	}
}
