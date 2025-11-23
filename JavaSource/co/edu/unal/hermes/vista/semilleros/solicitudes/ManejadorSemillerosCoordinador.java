package co.edu.unal.hermes.vista.semilleros.solicitudes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.HistoricoCambioIntegrantes;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroArchivo;
import co.edu.unal.hermes.modelo.SemilleroEstado;
import co.edu.unal.hermes.modelo.SemilleroHistoricoEstado;
import co.edu.unal.hermes.modelo.SemilleroHistoricoInforme;
import co.edu.unal.hermes.modelo.SemilleroInforme;
import co.edu.unal.hermes.modelo.SemilleroInformeArchivo;
import co.edu.unal.hermes.modelo.SemilleroIntegrante;
import co.edu.unal.hermes.modelo.SemilleroIntegranteTipo;
import co.edu.unal.hermes.modelo.SemilleroProyecto;
import co.edu.unal.hermes.modelo.SemilleroSolicitud;
import co.edu.unal.hermes.modelo.SemilleroSolicitudArchivo;
import co.edu.unal.hermes.modelo.SemilleroSolicitudRespuesta;
import co.edu.unal.hermes.modelo.SemilleroSolicitudTipo;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.modelo.seguimiento.AlertaProyecto;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorSemillerosCoordinador extends ManejadorBase {

	private static final long serialVersionUID = 1L;
	private SelectItem[] tipoDocumentoItem;
	private String idSemillero;
	private String nombreSemillero;
	private String nombreLider;
	private String tipoDocLider;
	private String docLider;
	private List<Semillero> semillerosBusqueda;
	private Semillero semilleroActual;
	private boolean nuevaBusqueda;
	private SemilleroArchivo archivoDescargar;
	private String idModal;
	private List<SemilleroHistoricoEstado> historicoEstados;
	private List<HistoricoCambioIntegrantes> historicoIntegrantes;
	private boolean informeNuevo;
	private SemilleroInforme informeActual;
	private Long idProyecto;
	private UploadedFile archivoSeleccionado;
	private SemilleroSolicitud solicitudActual;
	private ArrayList<SelectItem> respuestasSolicitud;
	private String nuevoEstado;
	private Investigador nuevoLider;
	private ArrayList<SelectItem> listaProyectos;
	private String[] proyectosSeleccionados;
	private SemilleroInformeArchivo archivoInf;
	private boolean revisarInforme;
	private CorreoPlantilla cp;
	private Correo mail;
	private List<SemilleroSolicitud> listaTramitesSemilleroCoordinador;
	private List<SemilleroSolicitud> filteredTramites;
	private String idSemilleroSeleccionado;
	private Long tramitesEncontrados;
	private Long idInformeSeleccionado;
	private Long idSolicitudSeleccionada;
	// Variables para la funcionalidad de cambio de estado
	/** The estado seleccionado. */
	private String estadoSeleccionado;
	/** The justificacion. */
	private String justificacion;
	/** The estados item. */
	private SelectItem[] estadosItem;
	private boolean mostrarBtnRevisionInforme;

	// Variables para cambio de coordinador
	private SelectItem[] coordinadorNuevoItem;
	private String coordinadorNuevo;
	private String mensajeAsignacion;
	private boolean mostrarAsignacionSemillero;
	private boolean tieneInformeProgramado;
	private List<SemilleroInforme> listaInformesSemillero;
	private SemilleroSolicitudArchivo archivoSolicitud;

	public ManejadorSemillerosCoordinador() {
		super();
		tramitesEncontrados = 0L;
		init();
	}

	public void actualizarInforme() {
		if (esCadenaVacia(informeActual.getObservaciones())) {
			FacesContext.getCurrentInstance().addMessage("msgsModal", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Por favor, indique las observaciones de la respuesta emitida.", null));
		} else {
			informeActual.setFechaRespuesta(getToday());
			String respuesta = "NO HA SIDO APROBADO";
			if (informeActual.getRespuesta().getId().equals(7)) {
				informeActual.getRespuesta().setNombre("Aprobado");
				informeActual.setCumple(true);
				informeActual.setFechaEntrega(getToday());
				respuesta = "HA SIDO APROBADO";

			} else if (informeActual.getRespuesta().getId().equals(2)) {
				informeActual.setGuardadoParcial("S");
				informeActual.getRespuesta().setNombre("Devuelto para Correcciones");
				respuesta = "HA SIDO DEVUELTO PARA CORRECCIONES";
			}
			servicioGeneral.guardarObjeto(informeActual);
			guardarHistoricoInformeSemilleros(informeActual, "Revisión Informe");
			mostrarBtnRevisionInforme = false;
			cp = cargarPlantilla(363);
			cp.setCuerpo(cp.getCuerpo().replaceAll("<<RESPONSABLE>>", personaActual.getNombreCompleto()));
			mail = editarCorreo(cp);
			mail.setCuerpo(mail.getCuerpo().replace("<<RESPUESTA>>", respuesta));
			mail.adicionarDireccion(informeActual.getSemillero().getLider().getEmail());
			servicioCorreo.enviarCorreo(mail);

			FacesContext.getCurrentInstance().addMessage("msgsModal",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Informe actualizado Correctamente.", null));
			if (informeActual.getRespuesta().getId().equals(7)) {
				nuevoInformeEnSesisMeses();
				FacesContext.getCurrentInstance().addMessage("msgsModal",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo informe programado.", null));
			}

		}
	}

	public void descargarArchivoInf() {
		try {
			descargarArchivoGenerico("HER_SEMILLERO_INF_ARCHIVO", archivoInf.getId().toString(),
					archivoInf.getNombre());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarArchivoInf() {
		Integer id = archivoInf.getId();
		String ruta = "HER_SEMILLERO_INF_ARCHIVO" + "//" + id;
		if (eliminarArchivoGenerico(ruta)) {
			informeActual.getArchivos().remove(archivoInf);
			servicioGeneral.eliminarObjeto(archivoInf);
		}
	}

	public void agregarArchivoInf(FileUploadEvent event) {
		setArchivoSeleccionado(event.getFile());
		cargarArchivoInf();
	}

	private void cargarArchivoInf() {
		SemilleroInformeArchivo ia = new SemilleroInformeArchivo();
		ia.setNombre(archivoSeleccionado.getFileName());
		ia.setInforme(informeActual);
		servicioGeneral.guardarObjeto(ia);
		cargarArchivoDisco(archivoSeleccionado, "HER_SEMILLERO_INF_ARCHIVO", ia.getId().toString());
		informeActual.getArchivos().add(ia);
	}

	public void cargarInforme() {
		listaProyectos = new ArrayList<SelectItem>();
		for (SemilleroProyecto proyecto : semilleroActual.getProyectos()) {
			listaProyectos
					.add(new SelectItem(proyecto.getProyecto().getId().toString(), proyecto.getProyecto().getNombre()));
		}
		if (informeActual.getProyectos() != null && !informeActual.getProyectos().isEmpty()) {
			setProyectosSeleccionados(informeActual.getProyectos().split(","));
		}
		mostrarBtnRevisionInforme = true;
	}

	private void cambiarLider() {
	    // 1. Buscar el integrante actual con tipo "DD"
	    SemilleroIntegrante directorActual = null;
	    String actividadesPrevias = "";
	    for (SemilleroIntegrante integrante : semilleroActual.getIntegrantes()) {
	        if ("DD".equals(integrante.getTipo().getId())) {
	            directorActual = integrante;
	            break;
	        }
	    }

	    Investigador directorPrevio = null;
	    

	    if (directorActual != null) {
	        directorPrevio = directorActual.getIntegrante();
	        actividadesPrevias = directorActual.getActividades() != null ? directorActual.getActividades() : "Docente";

	        // 2. Cerrar el registro histórico del director saliente (activo)
	        List<HistoricoCambioIntegrantes> historicoActivo = servicioGeneral.obtenerObjetos(
	            HistoricoCambioIntegrantes.class,
	            "from HistoricoCambioIntegrantes h " +
	            "where h.semillero.id = '" + semilleroActual.getId() + "' " +
	            "and h.integrante.id.documento = '" + directorPrevio.getId().getDocumento() + "' " +
	            "and h.tipoInvestigadorSemillero.id = 'DD' " +
	            "and h.fechaRetiro is null"
	        );

	        for (HistoricoCambioIntegrantes h : historicoActivo) {
	            h.setFechaRetiro(new Date());
	            servicioGeneral.guardarObjeto(h);
	        }

	        // 3. Cambiar tipo del director saliente a "D"
	        SemilleroIntegranteTipo tipoD = (SemilleroIntegranteTipo) servicioGeneral.obtenerObjeto(new SemilleroIntegranteTipo(), "D");
	        directorActual.setTipo(tipoD);
	        servicioGeneral.guardarObjeto(directorActual);

	        // 4. Registrar en histórico como tipo "D"
	        HistoricoCambioIntegrantes historicoDocente = new HistoricoCambioIntegrantes();
	        historicoDocente.setFechaIngreso(new Date());
	        historicoDocente.setSemillero(semilleroActual);
	        historicoDocente.setIntegrante(directorPrevio);
	        historicoDocente.setTipoVinculacion(directorPrevio.getTipoVinculacion());
	        historicoDocente.setTipoDedicacion(directorPrevio.getTipoDedicacion());
	        historicoDocente.setTipoFormacion(directorPrevio.getTipoFormacion());
	        historicoDocente.setDependencia(directorPrevio.getDependencia().getFacultad());
	        historicoDocente.setSede(directorPrevio.getDependencia().getFacultad().getSede());
	        historicoDocente.setTipoInvestigadorSemillero(tipoD);

	        servicioGeneral.guardarObjeto(historicoDocente);
	    }

	    // 5. Verificar si el nuevo líder ya está en el semillero
	    SemilleroIntegrante nuevoIntegrante = null;
	    for (SemilleroIntegrante si : semilleroActual.getIntegrantes()) {
	        if (si.getIntegrante().getId().equals(nuevoLider.getId())) {
	            nuevoIntegrante = si;
	            break;
	        }
	    }

	    SemilleroIntegranteTipo tipoDD = (SemilleroIntegranteTipo) servicioGeneral.obtenerObjeto(new SemilleroIntegranteTipo(), "DD");

	    if (nuevoIntegrante != null) {
	        // Ya estaba en el semillero: solo se actualiza tipo a "DD"
	        nuevoIntegrante.setTipo(tipoDD);
	        servicioGeneral.guardarObjeto(nuevoIntegrante);
	    } else {
	        // Nuevo en el semillero: se crea y se le asignan las actividades del director anterior
	        nuevoIntegrante = new SemilleroIntegrante();
	        nuevoIntegrante.setIntegrante(nuevoLider);
	        nuevoIntegrante.setTipo(tipoDD);
	        nuevoIntegrante.setSemillero(semilleroActual);
	        nuevoIntegrante.setActividades(actividadesPrevias);

	        semilleroActual.getIntegrantes().add(nuevoIntegrante);
	        servicioGeneral.guardarObjeto(nuevoIntegrante);
	    }

	    // 6. Registrar histórico del nuevo director
	    HistoricoCambioIntegrantes historicoNuevo = new HistoricoCambioIntegrantes();
	    historicoNuevo.setFechaIngreso(new Date());
	    historicoNuevo.setSemillero(semilleroActual);
	    historicoNuevo.setIntegrante(nuevoLider);
	    historicoNuevo.setTipoInvestigadorSemillero(tipoDD);
	    historicoNuevo.setTipoVinculacion(nuevoLider.getTipoVinculacion());
	    historicoNuevo.setTipoDedicacion(nuevoLider.getTipoDedicacion());
	    historicoNuevo.setTipoFormacion(nuevoLider.getTipoFormacion());
	    historicoNuevo.setDependencia(nuevoLider.getDependencia().getFacultad());
	    historicoNuevo.setSede(nuevoLider.getDependencia().getFacultad().getSede());

	    servicioGeneral.guardarObjeto(historicoNuevo);

	    // 7. Notificar por correo
	    CorreoPlantilla cp = cargarPlantilla(416);
	    if (cp != null) {
	        String cuerpoCorreo = cp.getCuerpo();
	        cuerpoCorreo = cuerpoCorreo.replace("<<NOMBRE_SEMILLERO>>", semilleroActual.getNombre());
	        cuerpoCorreo = cuerpoCorreo.replace("<<ID>>", semilleroActual.getId().toString());
	        cuerpoCorreo = cuerpoCorreo.replace("<<DEP_SEMILLERO>>", nuevoLider.getDependencia().getFacultad().getNombre());
	        cuerpoCorreo = cuerpoCorreo.replace("<<DIRECTOR_PREVIO>>", directorPrevio != null ? directorPrevio.getNombreCompleto() : "N/A");
	        cuerpoCorreo = cuerpoCorreo.replace("<<DIRECTOR_NUEVO>>", nuevoLider.getNombreCompleto());

	        Correo mail = new Correo();
	        mail.setOrigen(Correo.CORREO_HERMES);
	        mail.setAsunto(cp.getAsunto());
	        mail.setCuerpo(cuerpoCorreo);
	        mail.adicionarDireccion(nuevoLider.getEmail());

	        servicioCorreo.enviarCorreo(mail);
	    }
	}


	public String actualizarSolicitud() {
		if (!getSolicitudActual().getRespuestaCoordinador().getId().equals(1)) {
			if (esCadenaVacia(getSolicitudActual().getComentariosCoordinador())) {
				generarMsgModal(2, "Por favor, indique las observaciones sobre la solicitud.");
				return "";
			} else {
				getSolicitudActual().setIdResponsableCoordinador(personaActual.getId().getDocumento());
				getSolicitudActual().setTipoDocResponsableCoordinador(personaActual.getId().getTipoDocumento());
				getSolicitudActual().setFechaRespuestaCoordinador(Calendar.getInstance().getTime());
				if (getSolicitudActual().getRespuestaCoordinador().getId().equals(7)) {
					if (solicitudActual.getTipo().getId().equals(2)) {
						SemilleroHistoricoEstado she = new SemilleroHistoricoEstado();
						she.setSemillero(getSemilleroActual());
						she.setEstado((SemilleroEstado) servicioGeneral.obtenerObjeto(new SemilleroEstado(),
								Integer.valueOf(getSolicitudActual().getDetalle())));
						she.setMotivo("Aprobación de Solicitud con ID " + solicitudActual.getId());
						she.setFecha(Calendar.getInstance().getTime());
						she.setResponsable((InvestigadorInterno) personaActual);
						getSemilleroActual().getHistoricoEstados().add(she);
						servicioGeneral.guardarObjeto(she);
						cp = cargarPlantilla(354);
					} else if (!solicitudActual.getTipo().getId().equals(5)) {
						cp = cargarPlantilla(355);
					}
					if (solicitudActual.getTipo().getId().equals(5)) {
						//nuevoEstado = solicitudActual.getSemillero().getLider().getEmail();
						cambiarLider();
					}
				} else if (getSolicitudActual().getRespuestaCoordinador().getId().equals(2)) {
					solicitudActual.setRespuestaUAB(
							servicioGeneral.obtenerObjetoXID(SemilleroSolicitudRespuesta.class, "0").get(0));
					solicitudActual.setFechaRespuestaUAB(null);
					cp = cargarPlantilla(356);
				} else {
					cp = cargarPlantilla(357);
				}
				if (cp != null) {
					cp.setCuerpo(cp.getCuerpo().replaceAll("<<RESPONSABLE>>", personaActual.getNombreCompleto()));
					mail = editarCorreo(cp);
					mail.adicionarDireccion(solicitudActual.getSemillero().getLider().getEmail());
					if (Sede.SEDES_ANDINAS
							.contains(getSemilleroActual().getLider().getDependencia().getSede().getId().toString())) {
						String sql = " JOIN i.roles r WHERE r.id = 'DD' AND i.dependencia2.id = '"
								+ semilleroActual.getLider().getDependencia().getId()
								+ "' and (i.dependencia2.id <> '0')";
						for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class,
								sql)) {
							PersonaRol pr = (PersonaRol) servicioGeneral.obtenerPersonaRolXIdPersona(
									p.getId().getTipoDocumento(), p.getId().getDocumento(), "DD").get(0);
							if (p.getEmail() != null && pr.getFechaInicioRol().before(getToday())
									&& pr.getFechaFinRol().after(getToday())) {
								mail.adicionarDireccion(p.getEmail());
							}
						}
					}
					servicioCorreo.enviarCorreo(mail);
				}
				servicioGeneral.guardarObjeto(getSolicitudActual());
				generarMsgModal(1, "Solicitud Actualizada correctamente.");
			}
		}
		return "";
	}

	public Correo editarCorreo(CorreoPlantilla plantilla) {
		String cuerpo = plantilla.getCuerpo();
		cuerpo = cuerpo.replaceAll("<<NOMBRE>>", semilleroActual.getNombre());
		cuerpo = cuerpo.replaceAll("<<ID>>", semilleroActual.getId().toString());
		if(semilleroActual.getLider()!=null) {
			cuerpo = cuerpo.replaceAll("<<LIDER>>", semilleroActual.getLider().getNombreCompleto());
		}else {
			cuerpo = cuerpo.replaceAll("<<LIDER>>", "Líder no identificado");
		}
		if (solicitudActual != null && solicitudActual.getId() != null) {
			cuerpo = cuerpo.replaceAll("<<TIPO>>", solicitudActual.getTipo().getNombre().toUpperCase());
			cuerpo = cuerpo.replaceAll("<<OBSERVACIONES>>", solicitudActual.getComentariosCoordinador());
			if (solicitudActual.getTipo().getId().equals(2)) {
				cuerpo = cuerpo.replaceAll("<<ESTADO>>", nuevoEstado.toUpperCase());
			}
		} else if (informeActual != null) {
			cuerpo = cuerpo.replaceAll("<<OBSERVACIONES>>", informeActual.getObservaciones());
		}
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		// correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
		correo.setAsunto(plantilla.getAsunto());
		correo.setCuerpo(cuerpo);
		return correo;
	}

	private void obtenerRespuestasSolicitud() {
		if (getRespuestasSolicitud().isEmpty()) {
			List<SemilleroSolicitudRespuesta> ssr = servicioGeneral.obtenerObjetos(SemilleroSolicitudRespuesta.class,
					"select srr from SemilleroSolicitudRespuesta srr where srr.estadoCoordinador='Y'");
			for (SemilleroSolicitudRespuesta resp : ssr) {
				getRespuestasSolicitud().add(new SelectItem(resp.getId(), resp.getNombre()));
			}
		}
	}

	private void init() {
		respuestasSolicitud = new ArrayList<SelectItem>();
		solicitudActual = new SemilleroSolicitud();
		setNuevaBusqueda(false);
		setInformeNuevo(false);
		informeActual = new SemilleroInforme();
		List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerTiposDeDocumento();
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
		obtenerRespuestasSolicitud();
		obtenerTramitesPendientesCoordinador();
		cargarEstadosCambio();

	}

	public boolean getTieneInformeVencido() {
		try {
			String hqlInformes = "select si from SemilleroInforme si, Semillero s "
					+ "where s.id = si.semillero.id and s.documentoCoordinador = '"
					+ personaActual.getId().getDocumento() + "' " + "and si.cumple in ('0') and s.id = '"
					+ semilleroActual.getId()
					+ "' and to_date(si.fechaCompromiso, 'dd/mm/yyyy') <= to_date(SYSDATE,'dd/mm/yyyy')";

			List<SemilleroInforme> listaInformesCoordinador = servicioGeneral.obtenerObjetos(SemilleroInforme.class,
					hqlInformes);
			if (listaInformesCoordinador != null && listaInformesCoordinador.size() > 0) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean verificarTieneInformeProgramado() {
		try {
			String hqlInformes = "select #id si.id from SemilleroInforme si, Semillero s "
					+ "where s.id = si.semillero.id and s.documentoCoordinador = '"
					+ personaActual.getId().getDocumento() + "' and s.id = '" + semilleroActual.getId() + "' "
					+ "and ((si.guardadoParcial is null or si.guardadoParcial in ('S'))  "
					+ "or (si.guardadoParcial in ('N') and si.respuesta.id in ('1'))))";

			List<SemilleroInforme> listaInformesProgramados = servicioGeneral
					.obtenerObjetosLimitado(SemilleroInforme.class, hqlInformes);
			if (listaInformesProgramados != null && listaInformesProgramados.size() > 0) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void obtenerTramitesPendientesCoordinador() {

		listaTramitesSemilleroCoordinador = new ArrayList<SemilleroSolicitud>();

		String hql = "select ss from SemilleroSolicitud ss, Semillero s "
				+ "where s.id = ss.semillero.id and s.documentoCoordinador = '" + personaActual.getId().getDocumento()
				+ "' " + "and ss.respuestaCoordinador.id in ('1')";

		listaTramitesSemilleroCoordinador = servicioGeneral.obtenerObjetos(SemilleroSolicitud.class, hql);

		String hqlInformes = "select si from SemilleroInforme si, Semillero s "
				+ "where s.id = si.semillero.id and s.documentoCoordinador = '" + personaActual.getId().getDocumento()
				+ "' " + "and si.respuesta.id in ('1') and si.guardadoParcial in ('N')";

		List<SemilleroInforme> listaInformesCoordinador = servicioGeneral.obtenerObjetos(SemilleroInforme.class,
				hqlInformes);

		if (listaInformesCoordinador != null && listaInformesCoordinador.size() > 0) {
			for (int i = 0; i < listaInformesCoordinador.size(); i++) {
				SemilleroSolicitud ss = new SemilleroSolicitud();
				ss.setId(listaInformesCoordinador.get(i).getId());
				ss.setFechaSolicitud(listaInformesCoordinador.get(i).getFechaEntrega());
				SemilleroSolicitudTipo tipoInforme = new SemilleroSolicitudTipo();
				tipoInforme.setNombre("Revisión de informe");
				ss.setTipo(tipoInforme);
				ss.setSemillero(listaInformesCoordinador.get(i).getSemillero());
				ss.setRespuestaCoordinador(listaInformesCoordinador.get(i).getRespuesta());
				listaTramitesSemilleroCoordinador.add(ss);
			}
		}

		if (listaTramitesSemilleroCoordinador != null && listaTramitesSemilleroCoordinador.size() > 0) {
			tramitesEncontrados = (long) listaTramitesSemilleroCoordinador.size();
		}

	}

	public void validarFinalizado() {
		init();
	}

	public void cargarSolicitud() {
		if (solicitudActual != null) {
			switch (solicitudActual.getTipo().getId()) {
			case 2:
				setNuevoEstado(solicitudActual.getDetalle().equals("5") ? "Activo"
						: (solicitudActual.getDetalle().equals("6") ? "Suspendido" : "Inactivo"));
				break;
			case 3:
				setNuevoEstado(solicitudActual.getDetalle().replace("-", "; "));
				break;
			case 5:
				IdPersona id = new IdPersona();
				id.setTipoDocumento(solicitudActual.getDetalle().split("-")[0]);
				id.setDocumento(solicitudActual.getDetalle().split("-")[1]);
				nuevoLider = servicioPersona.obtenerInvestigador(id);
				break;
			}
		}
	}

	public void agregarArchivo(FileUploadEvent event) {
		archivoSeleccionado = event.getFile();
		if (archivoSeleccionado != null) {
			if (archivoSeleccionado.getSize() <= 3145728) {
				SemilleroArchivo sa = new SemilleroArchivo();
				sa.setSemillero(getSemilleroActual());
				sa.setNombre(archivoSeleccionado.getFileName());
				servicioGeneral.guardarObjeto(sa);
				cargarArchivoDisco(archivoSeleccionado, "HER_SEMILLERO_ARCHIVO", sa.getId().toString());
				semilleroActual.getArchivos().add(sa);
			} else {
				generarMsgModal(2, "El tamaño del archivo excede el máximo permitido (3 Mb)");
			}
		} else {
			generarMsgModal(2, "Por favor seleccione un archivo");
		}
	}

	public void buscarSemilleros() {
		if (validarCriterios()) {
			setNuevaBusqueda(true);
			String consulta = "select s from Semillero s,SemilleroIntegrante si where s.id=si.semillero.id and si.tipo in ('DD')";
			if (!esCadenaVacia(getIdSemillero())) {
				consulta += " and s.id=" + getIdSemillero();
			}
			if (!esCadenaVacia(getNombreSemillero())) {
				consulta += " and lower(s.nombre) like '%" + getNombreSemillero().toLowerCase() + "%'";
			}
			if (!esCadenaVacia(getNombreLider())) {
				consulta += " and (";
				boolean first = true;
				for (String cadena : getNombreLider().split(" ")) {
					if (!first) {
						consulta += " or";
					}
					consulta += " lower(si.integrante.nombre1) like '%" + cadena.toLowerCase()
							+ "%' or lower(si.integrante.nombre2) like '%" + cadena.toLowerCase()
							+ "%' or lower(si.integrante.apellido1) like '%" + cadena.toLowerCase()
							+ "%' or lower(si.integrante.apellido2) like '%" + cadena.toLowerCase() + "%'";
					if (first) {
						first = false;
					}
				}

				consulta += ")";
			}
			if (!esCadenaVacia(getDocLider()) && !esCadenaVacia(getTipoDocLider())) {
				consulta += " and si.integrante.id.tipoDocumento='" + getTipoDocLider()
						+ "' and si.integrante.id.documento='" + getDocLider() + "'";
			}
			consulta += " order by s.id";
			setSemillerosBusqueda(servicioGeneral.obtenerObjetos(Semillero.class, consulta));
			System.out.println("Semilleros obtenidos: " + getSemillerosBusqueda().size());
			for (Semillero semillero : getSemillerosBusqueda()) {
				semillero.setCoordinador(servicioPersona.obtenerInvestigador(
						new IdPersona(semillero.getDocumentoCoordinador(), semillero.getTipoDocumentoCoordinador())));
			}
		}
	}

	public void buscarSemillerosAsignados() {
		String consulta = "select s from Semillero s where s.documentoCoordinador = '"
				+ personaActual.getId().getDocumento() + "' order by s.id";
		setSemillerosBusqueda(servicioGeneral.obtenerObjetos(Semillero.class, consulta));
		nuevaBusqueda = true;
	}

	public String verDetalleSemillero() {
		return historico();
	}

	public String verDetalle() {
		return historico();
	}

	public String historico() {
		setHistoricoEstados(null);
		setHistoricoIntegrantes(null);
		setHistoricoEstados(servicioGeneral.obtenerObjetos(SemilleroHistoricoEstado.class,
				"from SemilleroHistoricoEstado h where h.semillero.id = '" + semilleroActual.getId()
						+ "' order by h.id asc"));
		setHistoricoIntegrantes(servicioGeneral.obtenerObjetos(HistoricoCambioIntegrantes.class,
				"from HistoricoCambioIntegrantes h where h.semillero.id = '" + semilleroActual.getId()
						+ "' order by h.id asc"));
		for (HistoricoCambioIntegrantes item : historicoIntegrantes) {
			TipoDocumento td = servicioGeneral
					.obtenerObjetoXID(TipoDocumento.class, item.getIntegrante().getId().getTipoDocumento()).get(0);
			item.setDocumento(td.getNombre());
			if (item.getTipoInvestigadorSemillero() == null || item.getTipoInvestigadorSemillero().getTipo() == null) {
				item.setTipo("N/A");
			} else if (item.getTipoInvestigadorSemillero().getTipo().equals("DD")) {
				item.setTipoVinculacionGrupo("Docente Director");
			} else if (item.getTipoInvestigadorSemillero().getTipo().equals("I")) {
				item.setTipoVinculacionGrupo("Interno");
			} else {
				item.setTipoVinculacionGrupo("Externo");
			}
			item.setTipo(item.getTipoInvestigadorSemillero().getNombre());

		}
		tieneInformeProgramado = false;
		tieneInformeProgramado = verificarTieneInformeProgramado();
		return "irSemilleroConsulta";
	}

	public void nuevoInforme() {
		setInformeNuevo(true);
		informeActual = new SemilleroInforme();
	}

	public void nuevoInformeEnSesisMeses() {
		informeActual = new SemilleroInforme();
		// Get the current date
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();

		// Add six months to the current date
		calendar.add(Calendar.MONTH, 6);
		Date fechaProximaSeisMeses = calendar.getTime();
		informeActual.setFechaCompromiso(fechaProximaSeisMeses);
		agregarInforme();
	}

	public void actualizarInformes() {
		for (SemilleroInforme informe : semilleroActual.getInformes()) {
			if (informeActual.getFechaCompromiso().equals(informe.getFechaCompromiso())
					&& !informeActual.getId().equals(informe.getId())) {
				generarMsgModal(2, "Ya existe un informe con la misma fecha del compromiso.");
				semilleroActual.setInformes(new HashSet<SemilleroInforme>(servicioGeneral
						.obtenerObjetos(SemilleroInforme.class, "from SemilleroInforme h where h.semillero.id = '"
								+ getSemilleroActual().getId() + "' order by h.id asc")));
				return;
			}
		}
		for (SemilleroInforme informe : semilleroActual.getInformes()) {
			if (informeActual.getId().equals(informe.getId())) {
				informe.setFechaCompromiso(informeActual.getFechaCompromiso());
			}
		}
		informeActual.setNumeroNotificaciones(0);
		informeActual.setFechaUltimaNotificacion(null);
		SemilleroHistoricoInforme shi = new SemilleroHistoricoInforme();
		SemilleroInforme informeAntiguo = servicioGeneral
				.obtenerObjetoXID(SemilleroInforme.class, informeActual.getId().toString()).get(0);
		if (informeAntiguo.getFechaCompromiso().compareTo(informeActual.getFechaCompromiso()) != 0) {
			shi.setFechaCompromiso(informeActual.getFechaCompromiso());
			shi.setInforme(informeActual);
			shi.setResponsable((InvestigadorInterno) personaActual);
			shi.setFechaAjuste(getToday());
			servicioGeneral.guardarObjeto(shi);
			informeActual.getHistorico().add(shi);
		}
		servicioGeneral.guardarObjeto(informeActual);
		semilleroActual.setInformes(new HashSet<SemilleroInforme>(servicioGeneral.obtenerObjetos(SemilleroInforme.class,
				"from SemilleroInforme h where h.semillero.id = '" + getSemilleroActual().getId()
						+ "' order by h.id asc")));
		generarMsgModal(1, "Informe actualizado exitosamente.");
	}

	public void agregarInforme() {
		if (informeActual.getFechaCompromiso() == null) {
			generarMsgModal(2, "Por favor, indique la fecha del compromiso asociado al informe.");
			return;
		}
		for (SemilleroInforme informe : semilleroActual.getInformes()) {
			if (informeActual.getFechaCompromiso().equals(informe.getFechaCompromiso())) {
				generarMsgModal(2, "Ya existe un informe con la misma fecha del compromiso.");
				return;
			}
		}
		informeActual.setSemillero(semilleroActual);
		informeActual.setNumeroNotificaciones(0);
		informeActual.setCumple(false);
		SemilleroSolicitudRespuesta respuestaInformeProgramado = new SemilleroSolicitudRespuesta();
		respuestaInformeProgramado.setId(1);
		informeActual.setRespuesta(respuestaInformeProgramado);
		servicioGeneral.guardarObjeto(informeActual);
		guardarHistoricoInformeSemilleros(informeActual, "Creación de compromiso");
		semilleroActual.getInformes().add(informeActual);
		informeActual = new SemilleroInforme();
		setInformeNuevo(false);
	}

	public void cancelarInforme() {
		setInformeNuevo(false);
	}

	public void reporteProyecto() {
		Proyecto proyectoActual = servicioProyecto.obtenerProyecto(idProyecto, ProyectoDAOHibernate.LIMPIO_SEGUIMIENTO);
		servicioProyecto.imprimirReporteProyecto(proyectoActual, sesion, false);
	}

	private boolean validarCriterios() {
		if (esCadenaVacia(getIdSemillero()) && esCadenaVacia(getNombreSemillero()) && esCadenaVacia(getNombreLider())
				&& esCadenaVacia(getTipoDocLider()) && esCadenaVacia(getDocLider())) {
			generarMsg(2, "Por favor, indique al menos un criterio de búsqueda.");
			return false;
		} else if (!esCadenaVacia(getIdSemillero()) && !cadenaEsValorNumerico(getIdSemillero())) {
			generarMsg(2, "El ID del semillero no es válido, por favor indique un número.");
			return false;
		} else if (!esCadenaVacia(getTipoDocLider()) && esCadenaVacia(getDocLider())) {
			generarMsg(2, "Por favor, indique el número de documento del líder del semillero.");
			return false;
		} else if (esCadenaVacia(getTipoDocLider()) && !esCadenaVacia(getDocLider())) {
			generarMsg(2, "Por favor, indique el tipo de documento del líder del semillero.");
			return false;
		}
		return true;
	}

	private void generarMsg(int tipo, String mensaje) {
		switch (tipo) {
		case 1:
			FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null));
			break;
		case 2:
			FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
			break;
		}
	}

	private void generarMsgModal(int tipo, String mensaje) {
		switch (tipo) {
		case 1:
			FacesContext.getCurrentInstance().addMessage("msgsModal",
					new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null));
			break;
		case 2:
			FacesContext.getCurrentInstance().addMessage("msgsModal",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
			break;
		}
	}

	public void reiniciarBusqueda() {
		setIdSemillero("");
		setNombreLider("");
		setNombreSemillero("");
		setTipoDocLider("");
		setDocLider("");
		setSemillerosBusqueda(null);
		setNuevaBusqueda(false);
	}

	public int getTotalResultados() {
		return getSemillerosBusqueda() == null ? 0 : getSemillerosBusqueda().size();
	}

	public String reporteSemillero() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("idSemillero", getSemilleroActual().getId().toString());
		r.setNombreReporte("/semilleros/reporteSemillero");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			context.responseComplete();
		}
		return "";
	}

	public void descargarArchivo() {
		try {
			descargarArchivoGenerico("HER_SEMILLERO_ARCHIVO", archivoDescargar.getId().toString(),
					archivoDescargar.getNombre());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void descargarArchivoSolicitud() {
		try {
			descargarArchivoGenerico("HER_SEMILLERO_SOL_ARCHIVO", archivoSolicitud.getId().toString(), archivoSolicitud.getNombre());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public String getIdSemillero() {
		return idSemillero;
	}

	public void setIdSemillero(String idSemillero) {
		this.idSemillero = idSemillero;
	}

	public String getNombreSemillero() {
		return nombreSemillero;
	}

	public void setNombreSemillero(String nombreSemillero) {
		this.nombreSemillero = nombreSemillero;
	}

	public String getNombreLider() {
		return nombreLider;
	}

	public void setNombreLider(String nombreLider) {
		this.nombreLider = nombreLider;
	}

	public String getTipoDocLider() {
		return tipoDocLider;
	}

	public void setTipoDocLider(String tipoDocLider) {
		this.tipoDocLider = tipoDocLider;
	}

	public String getDocLider() {
		return docLider;
	}

	public void setDocLider(String docLider) {
		this.docLider = docLider;
	}

	public List<Semillero> getSemillerosBusqueda() {
		return semillerosBusqueda;
	}

	public void setSemillerosBusqueda(List<Semillero> semillerosBusqueda) {
		this.semillerosBusqueda = semillerosBusqueda;
	}

	public boolean isNuevaBusqueda() {
		return nuevaBusqueda;
	}

	public void setNuevaBusqueda(boolean nuevaBusqueda) {
		this.nuevaBusqueda = nuevaBusqueda;
	}

	public Semillero getSemilleroActual() {
		return semilleroActual;
	}

	public void setSemilleroActual(Semillero semilleroActual) {
		this.semilleroActual = semilleroActual;
	}

	public SemilleroArchivo getArchivoDescargar() {
		return archivoDescargar;
	}

	public void setArchivoDescargar(SemilleroArchivo archivoEliminar) {
		this.archivoDescargar = archivoEliminar;
	}

	public String getIdModal() {
		return idModal;
	}

	public void setIdModal(String idModal) {
		this.idModal = idModal;
	}

	public List<SemilleroHistoricoEstado> getHistoricoEstados() {
		return historicoEstados;
	}

	public void setHistoricoEstados(List<SemilleroHistoricoEstado> historicoEstados) {
		this.historicoEstados = historicoEstados;
	}

	public List<HistoricoCambioIntegrantes> getHistoricoIntegrantes() {
		return historicoIntegrantes;
	}

	public void setHistoricoIntegrantes(List<HistoricoCambioIntegrantes> historicoIntegrantes) {
		this.historicoIntegrantes = historicoIntegrantes;
	}

	public boolean isInformeNuevo() {
		return informeNuevo;
	}

	public void setInformeNuevo(boolean nuevoInforme) {
		this.informeNuevo = nuevoInforme;
	}

	public SemilleroInforme getInformeActual() {
		return informeActual;
	}

	public void setInformeActual(SemilleroInforme informeActual) {
		this.informeActual = informeActual;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public UploadedFile getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(UploadedFile archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public SemilleroSolicitud getSolicitudActual() {
		return solicitudActual;
	}

	public void setSolicitudActual(SemilleroSolicitud solicitudActual) {
		this.solicitudActual = solicitudActual;
	}

	public ArrayList<SelectItem> getRespuestasSolicitud() {
		return respuestasSolicitud;
	}

	public void setRespuestasSolicitud(ArrayList<SelectItem> respuestasSolicitud) {
		this.respuestasSolicitud = respuestasSolicitud;
	}

	public String getNuevoEstado() {
		return nuevoEstado;
	}

	public void setNuevoEstado(String nuevoEstado) {
		this.nuevoEstado = nuevoEstado;
	}

	public Investigador getNuevoLider() {
		return nuevoLider;
	}

	public void setNuevoLider(Investigador nuevoLider) {
		this.nuevoLider = nuevoLider;
	}

	public ArrayList<SelectItem> getListaProyectos() {
		return listaProyectos;
	}

	public void setListaProyectos(ArrayList<SelectItem> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}

	public String[] getProyectosSeleccionados() {
		return proyectosSeleccionados;
	}

	public void setProyectosSeleccionados(String[] proyectosSeleccionados) {
		this.proyectosSeleccionados = proyectosSeleccionados;
	}

	public SemilleroInformeArchivo getArchivoInf() {
		return archivoInf;
	}

	public void setArchivoInf(SemilleroInformeArchivo archivoInf) {
		this.archivoInf = archivoInf;
	}

	public boolean isRevisarInforme() {
		return revisarInforme;
	}

	public void setRevisarInforme(boolean revisarInforme) {
		this.revisarInforme = revisarInforme;
	}

	public void reporteInformeSemillero() {

		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("idInforme", idInformeSeleccionado.toString());
		r.setFormato(ReporteBirt.FORMATO_PDF);

		r.setNombreReporte("semilleros/informeSemillero");

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	public void reporteSolicitudSemillero() {

		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("idsol", idSolicitudSeleccionada.toString());
		r.setFormato(ReporteBirt.FORMATO_PDF);

		r.setNombreReporte("semilleros/solicitudSemillero");

		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	public List<SemilleroSolicitud> getListaTramitesSemilleroCoordinador() {
		return listaTramitesSemilleroCoordinador;
	}

	public void setListaTramitesSemilleroCoordinador(List<SemilleroSolicitud> listaTramitesSemilleroCoordinador) {
		this.listaTramitesSemilleroCoordinador = listaTramitesSemilleroCoordinador;
	}

	public String getIdSemilleroSeleccionado() {
		return idSemilleroSeleccionado;
	}

	public void setIdSemilleroSeleccionado(String idSemilleroSeleccionado) {
		this.idSemilleroSeleccionado = idSemilleroSeleccionado;
	}

	public Long getTramitesEncontrados() {
		return tramitesEncontrados;
	}

	public void setTramitesEncontrados(Long tramitesEncontrados) {
		this.tramitesEncontrados = tramitesEncontrados;
	}

	public Long getIdInformeSeleccionado() {
		return idInformeSeleccionado;
	}

	public void setIdInformeSeleccionado(Long idInformeSeleccionado) {
		this.idInformeSeleccionado = idInformeSeleccionado;
	}

	public Long getIdSolicitudSeleccionada() {
		return idSolicitudSeleccionada;
	}

	public void setIdSolicitudSeleccionada(Long idSolicitudSeleccionada) {
		this.idSolicitudSeleccionada = idSolicitudSeleccionada;
	}

	public List<SemilleroSolicitud> getFilteredTramites() {
		return filteredTramites;
	}

	public void setFilteredTramites(List<SemilleroSolicitud> filteredTramites) {
		this.filteredTramites = filteredTramites;
	}

	public String getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	public void setEstadoSeleccionado(String estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public SelectItem[] getEstadosItem() {
		return estadosItem;
	}

	public void setEstadosItem(SelectItem[] estadosItem) {
		this.estadosItem = estadosItem;
	}

	private void cargarEstadosCambio() {
		List<SemilleroEstado> listaEstados = servicioGeneral.obtenerObjetos(SemilleroEstado.class,
				"from SemilleroEstado e where e.id in ('5','6','7')"); // activo, suspendido, inactivo,
		if (listaEstados != null) {
			estadosItem = new SelectItem[listaEstados.size()];
			for (int i = 0; i < listaEstados.size(); i++) {
				SemilleroEstado estado = (SemilleroEstado) listaEstados.get(i);
				estadosItem[i] = new SelectItem(estado.getId(), estado.getNombre());
			}
		}
	}

	/**
	 * Guardar nuevo estado.
	 */
	public void guardarNuevoEstado() {
		if (esCadenaVacia(justificacion)) {
			FacesContext.getCurrentInstance().addMessage("msgsModal", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe ingresar una justificación para el cambio de estado.", null));
		}
		try {
			if (estadoSeleccionado.equals(SemilleroEstado.ESTADO_INACTIVO.toString())) {
				if (getSemilleroActual().getListaInformesCompleta() != null
						&& getSemilleroActual().getListaInformesCompleta().size() > 0) {
					for (SemilleroInforme ss : getSemilleroActual().getListaInformesCompleta()) {
						if (!ss.getCumple() && ss.isEsInformeEditable()) {
							servicioGeneral.eliminarObjeto(ss);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		SemilleroHistoricoEstado she = new SemilleroHistoricoEstado();
		she.setSemillero(getSemilleroActual());
		SemilleroEstado nuevoEstado = (SemilleroEstado) servicioGeneral.obtenerObjeto(new SemilleroEstado(),
				Integer.valueOf(estadoSeleccionado));
		she.setEstado(nuevoEstado);
		she.setMotivo(justificacion);
		she.setFecha(Calendar.getInstance().getTime());
		she.setResponsable((InvestigadorInterno) personaActual);
		getSemilleroActual().getHistoricoEstados().add(she);
		historicoEstados.add(she);
		servicioGeneral.guardarObjeto(she);
		try {
			cp = cargarPlantilla(391);
			cp.setCuerpo(cp.getCuerpo().replaceAll("<<JUSTIFICACION>>", justificacion));
			cp.setCuerpo(cp.getCuerpo().replaceAll("<<ESTADO>>", nuevoEstado.getNombre()));
			cp.setCuerpo(cp.getCuerpo().replaceAll("<<COORDINADOR>>", personaActual.getNombreCompletoMinusculas()));

			mail = editarCorreo(cp);
			mail.adicionarDireccion(getSemilleroActual().getLider().getEmail());
			servicioCorreo.enviarCorreo(mail);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public boolean isMostrarBtnRevisionInforme() {
		return mostrarBtnRevisionInforme;
	}

	public void setMostrarBtnRevisionInforme(boolean mostrarBtnRevisionInforme) {
		this.mostrarBtnRevisionInforme = mostrarBtnRevisionInforme;
	}

	public SelectItem[] getCoordinadorNuevoItem() {
		return coordinadorNuevoItem;
	}

	public void setCoordinadorNuevoItem(SelectItem[] coordinadorNuevoItem) {
		this.coordinadorNuevoItem = coordinadorNuevoItem;
	}

	public String getCoordinadorNuevo() {
		return coordinadorNuevo;
	}

	public void setCoordinadorNuevo(String coordinadorNuevo) {
		this.coordinadorNuevo = coordinadorNuevo;
	}

	public String getMensajeAsignacion() {
		return mensajeAsignacion;
	}

	public void setMensajeAsignacion(String mensajeAsignacion) {
		this.mensajeAsignacion = mensajeAsignacion;
	}

	public void cargarFormularioCambioCoordinador() {
		mostrarAsignacionSemillero = true;
		semilleroActual = getSemilleroActual();
		obtenerCoordinadoresAsesor();
	}

	public void guardarAsociacionSemilleroCoordinador() {
		try {
			if (!semilleroActual.getEstadoActual().getId().toString().equals("5")) {
				mensajeAsignacion = "Solo se puede cambiar el coordinador de un semillero Activo";
				return;
			}
			semilleroActual.setDocumentoCoordinador(coordinadorNuevo);
			semilleroActual.setTipoDocumentoCoordinador("C");
			semilleroActual.setFechaCambioCoordinador(getToday());
			semilleroActual.setDocumentoAsesor(personaActual.getId().getDocumento());
			semilleroActual.setTipoDocAsesor(personaActual.getId().getTipoDocumento());
			servicioGeneral.guardarObjeto(semilleroActual);
			mensajeAsignacion = "El coordinador del semillero ha sido actualizado";
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public boolean isMostrarAsignacionSemillero() {
		return mostrarAsignacionSemillero;
	}

	public void setMostrarAsignacionSemillero(boolean mostrarAsignacionSemillero) {
		this.mostrarAsignacionSemillero = mostrarAsignacionSemillero;
	}

	public void obtenerCoordinadoresAsesor() {
		try {
			List<Persona> listaCoordinadores = servicioPersona
					.obtenerListaCoordinadores(personaActual.getId().getDocumento());
			coordinadorNuevoItem = new SelectItem[listaCoordinadores.size() + 1];
			coordinadorNuevoItem[0] = new SelectItem("0", "Seleccione coordinador");
			for (int i = 0; i < listaCoordinadores.size(); i++) {
				Persona p = (Persona) listaCoordinadores.get(i);
				coordinadorNuevoItem[i + 1] = new SelectItem(p.getId().getDocumento(),
						p.getNombre1() + " " + p.getApellido1() + " " + p.getApellido2());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public boolean isTieneInformeProgramado() {
		return tieneInformeProgramado;
	}

	public void setTieneInformeProgramado(boolean tieneInformeProgramado) {
		this.tieneInformeProgramado = tieneInformeProgramado;
	}

	public List<SemilleroInforme> getListaInformesSemillero() {
		return listaInformesSemillero;
	}

	public void setListaInformesSemillero(List<SemilleroInforme> listaInformesSemillero) {
		this.listaInformesSemillero = listaInformesSemillero;
	}

	public SemilleroSolicitudArchivo getArchivoSolicitud() {
		return archivoSolicitud;
	}

	public void setArchivoSolicitud(SemilleroSolicitudArchivo archivoSolicitud) {
		this.archivoSolicitud = archivoSolicitud;
	}

}
