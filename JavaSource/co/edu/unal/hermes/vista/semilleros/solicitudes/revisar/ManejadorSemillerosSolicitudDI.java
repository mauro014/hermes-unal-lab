package co.edu.unal.hermes.vista.semilleros.solicitudes.revisar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroArchivo;
import co.edu.unal.hermes.modelo.SemilleroEstado;
import co.edu.unal.hermes.modelo.SemilleroHistoricoEstado;
import co.edu.unal.hermes.modelo.SemilleroRequisito;
import co.edu.unal.hermes.modelo.SemilleroRevisionRequisito;
import co.edu.unal.hermes.modelo.SemilleroSolicitud;
import co.edu.unal.hermes.modelo.SemilleroSolicitudArchivo;
import co.edu.unal.hermes.modelo.SemilleroSolicitudRespuesta;
import co.edu.unal.hermes.modelo.correo.Correo;

public class ManejadorSemillerosSolicitudDI extends ManejadorSemillerosSolicitudBase {

	private static final long serialVersionUID = 1L;

	private Boolean cumpleRequisitos;
	private ArrayList<SelectItem> listaCoordinador;
	private ArrayList<SemilleroRevisionRequisito> listaRequisitos;

	public ManejadorSemillerosSolicitudDI() {
		super();
		init();
	}

	private void consultarCoordinadoresSede() {
		List<InvestigadorInterno> listaInv = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
				"select ii from PersonaRol pr, InvestigadorInterno ii "
						+ "where (sysdate between pr.fechaInicioRol and pr.fechaFinRol) and pr.nombre = 'C' and ii.id.documento = pr.documento and ii.id.tipoDocumento = pr.tipoDocumento and "
						+ "ii.dependencia.facultad.sede.id = '"
						+ investigadorInterno.getDependencia2().getFacultad().getSede().getId() + "'");
		setListaCoordinador(new ArrayList<SelectItem>());
		for (InvestigadorInterno inv : listaInv) {
			getListaCoordinador().add(new SelectItem(inv.getId().getDocumento(), inv.getNombreCompletoMinusculas()));
		}
	}

	private void init() {
		semilleros = servicioGeneral.obtenerObjetos(Semillero.class, "select s from Semillero s, SemilleroIntegrante si, InvestigadorInterno ii where si.semillero.id = s.id and si.tipo.id = 'DD' "
				+ " and ii.id.documento = si.integrante.id.documento and ii.dependencia.sede.id = '"+investigadorInterno.getDependencia2().getSede().getId()+"'" );
		obtenerRespuestasSolicitud();
		setListaSolicitudes(new ArrayList<SemilleroSolicitud>());
		for (Semillero s : semilleros) {
			if(s.getLider()!=null && s.getLider().getId()!=null) {
			if (Sede.SEDES_PRESENCIA_NACIONAL.contains(s.getLider().getDependencia().getSede().getId().toString())
					&& investigadorInterno.getDependencia2().getSede().getId()
							.equals(s.getLider().getDependencia().getSede().getId())) {
				for (SemilleroSolicitud ssol : s.getSolicitudes()) {
					if (ssol.getFechaRespuestaVifDi()  == null
							&& ssol.getSemillero().getEstadoActual().getId().equals(2)) {
						if(ssol.getRespuestaVifDi() == null) {
							SemilleroSolicitudRespuesta estadoFacultad = new SemilleroSolicitudRespuesta();
							estadoFacultad.setId(1);
							ssol.setRespuestaVifDi(estadoFacultad);
						}
						getListaSolicitudes().add(ssol);
					}
				}
			}
		}
		}
		setCumpleRequisitos(false);
		consultarCoordinadoresSede();
	}

	public void obtenerRequisitos() {
		if (getSolicitudActual().getRevisionRequisitos().isEmpty()) {
			setListaRequisitos(new ArrayList<SemilleroRevisionRequisito>());
			List<SemilleroRequisito> sr = servicioGeneral.obtenerObjetos(SemilleroRequisito.class,
					"select s from SemilleroRequisito s where s.visible='Y'");
			for (SemilleroRequisito req : sr) {
				SemilleroRevisionRequisito srev = new SemilleroRevisionRequisito();
				srev.setSolicitudSemillero(getSolicitudActual());
				srev.setRequisito(req);
				srev.setCumple("N");
				getListaRequisitos().add(srev);
				getSolicitudActual().getRevisionRequisitos().add(srev);
				servicioGeneral.guardarObjeto(srev);
			}
		} else {
			setListaRequisitos(getSolicitudActual().getListaRevisionRequisitos());
		}
	}

	public void actualizarRequisitos() {
		boolean cumple = true;
		for (SemilleroRevisionRequisito ssr : listaRequisitos) {
			if (ssr.getCumple().equals("N")) {
				cumple = false;
				break;
			}
		}
		setCumpleRequisitos(cumple);
	}

	private void obtenerRespuestasSolicitud() {
		if (getRespuestasSolicitud().isEmpty()) {
			List<SemilleroSolicitudRespuesta> ssr = servicioGeneral.obtenerObjetos(SemilleroSolicitudRespuesta.class,
					"select srr from SemilleroSolicitudRespuesta srr where srr.estadoDi='Y'");
			for (SemilleroSolicitudRespuesta resp : ssr) {
				getRespuestasSolicitud().add(new SelectItem(resp.getId(), resp.getNombre()));
			}
		}
	}

	public void actualizarRespuesta() {
		if (!getSolicitudActual().getRespuestaVifDi().getId().equals(1)) {
			actualizarRequisitos();
			if (!getCumpleRequisitos() && getSolicitudActual().getRespuestaVifDi().getId().equals(7)) {
				generarMsgModal(2,
						"La solicitud no puede pasarse a estado APROBADO cuando no se han cumplido TODOS los requisitos.");
				getSolicitudActual().getRespuestaVifDi().setId(1);
				return;
			} else if (getCumpleRequisitos() && !getSolicitudActual().getRespuestaVifDi().getId().equals(7)) {
				generarMsgModal(2,
						"La solicitud no puede pasarse a estado NO APROBADO, ni DEVUELTO PARA CORRECCIONES cuando se han cumplido TODOS los requisitos.");
				getSolicitudActual().getRespuestaVifDi().setId(1);
				return;
			}
		}
	}

	public void agregarArchivo(FileUploadEvent event) {
		archivoSeleccionado = event.getFile();
		super.agregarArchivo("S");
	}

	public void validarFinalizado() {
		init();
	}

	public String actualizarSolicitud() {
		if (!getSolicitudActual().getRespuestaVifDi().getId().equals(1)) {
			if (getSolicitudActual().getRespuestaVifDi().getId().equals(7)) {
				if (!getCumpleRequisitos()) {
					generarMsgModal(2,
							"La solicitud no puede pasarse a estado APROBADO cuando no se han cumplido TODOS los requisitos.");
					getSolicitudActual().getRespuestaVifDi().setId(1);
					return "";
				} else if (esCadenaVacia(getSolicitudActual().getSemillero().getDocumentoCoordinador())) {
					generarMsgModal(2,
							"La solicitud no puede pasarse a estado APROBADO si no se ha definido un coordinador para el semillero.");
					getSolicitudActual().getRespuestaVifDi().setId(1);
					return "";
				}
			} else if (getCumpleRequisitos() && !getSolicitudActual().getRespuestaVifDi().getId().equals(7)) {
				generarMsgModal(2,
						"La solicitud no puede pasarse a estado NO APROBADO, ni DEVUELTO PARA CORRECCIONES cuando se han cumplido TODOS los requisitos.");
				getSolicitudActual().getRespuestaVifDi().setId(1);
				return "";
			}
			if (esCadenaVacia(getSolicitudActual().getComentariosVifDi())) {
				generarMsgModal(2, "Por favor, indique las observaciones sobre la solicitud.");
				return "";
			} else {
				CorreoPlantilla cp = null;
				for (SemilleroRevisionRequisito ssr : listaRequisitos) {
					servicioGeneral.guardarObjeto(ssr);
				}
				getSolicitudActual().setIdResponsableVifDi(personaActual.getId().getDocumento());
				getSolicitudActual().setTipoDocResponsableVifDi(personaActual.getId().getTipoDocumento());
				SemilleroHistoricoEstado she = new SemilleroHistoricoEstado();
				she.setSemillero(getSolicitudActual().getSemillero());
				she.setFecha(Calendar.getInstance().getTime());
				she.setResponsable(servicioPersona.obtenerInvestigador(personaActual.getId()));
				getSolicitudActual().getSemillero().getHistoricoEstados().add(she);
				if (getSolicitudActual().getRespuestaVifDi().getId().equals(4)) {
					she.setEstado((SemilleroEstado) servicioGeneral.obtenerObjeto(new SemilleroEstado(), 4));
					she.setMotivo("Semillero No Aprobado en DI.");
					cp = cargarPlantilla(345);
					getSolicitudActual().getSemillero().setDocumentoCoordinador(null);
				} else if (getSolicitudActual().getRespuestaVifDi().getId().equals(6)) {
					she.setEstado((SemilleroEstado) servicioGeneral.obtenerObjeto(new SemilleroEstado(), 1));
					she.setMotivo("Semillero Deuelto para correcciones a Docente.");
					getSolicitudActual().setFechaRespuestaUAB(null);
					getSolicitudActual().setRespuestaUAB((SemilleroSolicitudRespuesta) servicioGeneral
							.obtenerObjeto(new SemilleroSolicitudRespuesta(), 1));
					getSolicitudActual().setIdResponsableUAB(null);
					getSolicitudActual().setTipoDocResponsableUAB(null);
					getSolicitudActual().getSemillero().setDocumentoCoordinador(null);
					cp = cargarPlantilla(344);
				} else if (getSolicitudActual().getRespuestaVifDi().getId().equals(7)) {
					she.setEstado((SemilleroEstado) servicioGeneral.obtenerObjeto(new SemilleroEstado(), 5));
					she.setMotivo("Semillero Aprobado por DI.");
					getSolicitudActual().getSemillero().setTipoDocumentoCoordinador("C");
					servicioGeneral.guardarObjeto(getSolicitudActual().getSemillero());
					cp = cargarPlantilla(349);
				}
				if (cp != null) {
					cp.setAsunto(cp.getAsunto().replaceAll("<<ROL>>", "Dirección de Investigación"));
					cp.setCuerpo(cp.getCuerpo().replaceAll("<<OBSERVACIONES>>", solicitudActual.getComentariosVifDi()));
					cp.setCuerpo(cp.getCuerpo().replaceAll("<<ROL>>", "Dirección de Investigación"));
					cp.setCuerpo(cp.getCuerpo().replaceAll("<<RESPONSABLE>>", personaActual.getNombreCompleto()));
					Correo mail = editarCorreo(cp);
					mail.adicionarDireccion(solicitudActual.getSemillero().getLider().getEmail());
					servicioCorreo.enviarCorreo(mail);
					if (cp.getId().equals(349L)) {
						cp = cargarPlantilla(350);
						cp.setAsunto(cp.getAsunto().replaceAll("<<ROL>>", "Dirección de Investigación"));
						cp.setCuerpo(
								cp.getCuerpo().replaceAll("<<OBSERVACIONES>>", solicitudActual.getComentariosVifDi()));
						cp.setCuerpo(cp.getCuerpo().replaceAll("<<ROL>>", "Dirección de Investigación"));
						cp.setCuerpo(cp.getCuerpo().replaceAll("<<RESPONSABLE>>", personaActual.getNombreCompleto()));
						mail = editarCorreo(cp);
						IdPersona idp = new IdPersona(solicitudActual.getSemillero().getDocumentoCoordinador(),
								solicitudActual.getSemillero().getTipoDocumentoCoordinador());
						mail.adicionarDireccion((servicioPersona.obtenerInvestigadorInterno(idp)).getEmail());
						servicioCorreo.enviarCorreo(mail);
					}
				}
				servicioGeneral.guardarObjeto(she);
				getSolicitudActual().setFechaRespuestaVifDi(Calendar.getInstance().getTime());
				servicioGeneral.guardarObjeto(getSolicitudActual());
				generarMsgModal(1, "Solicitud Actualizada correctamente.");
			}
		}
		return "";
	}

	public List<SemilleroSolicitud> getListaSolicitudes() {
		return listaSolicitudes;
	}

	public void setListaSolicitudes(List<SemilleroSolicitud> listaSolicitudes) {
		this.listaSolicitudes = listaSolicitudes;
	}

	public SemilleroSolicitud getSolicitudActual() {
		return solicitudActual;
	}

	public void setSolicitudActual(SemilleroSolicitud solicitudActual) {
		this.solicitudActual = solicitudActual;
	}

	public SemilleroArchivo getArchivoActual() {
		return archivoActual;
	}

	public void setArchivoActual(SemilleroArchivo archivoActual) {
		this.archivoActual = archivoActual;
	}

	public UploadedFile getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(UploadedFile archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public ArrayList<SelectItem> getRespuestasSolicitud() {
		return respuestasSolicitud;
	}

	public void setRespuestasSolicitud(ArrayList<SelectItem> respuestasSolicitud) {
		this.respuestasSolicitud = respuestasSolicitud;
	}

	public SemilleroSolicitudArchivo getArchivoSolActual() {
		return archivoSolActual;
	}

	public void setArchivoSolActual(SemilleroSolicitudArchivo archivoSolActual) {
		this.archivoSolActual = archivoSolActual;
	}

	public Boolean getCumpleRequisitos() {
		return cumpleRequisitos;
	}

	public void setCumpleRequisitos(Boolean cumpleRequisitos) {
		this.cumpleRequisitos = cumpleRequisitos;
	}

	public ArrayList<SemilleroRevisionRequisito> getListaRequisitos() {
		return listaRequisitos;
	}

	public void setListaRequisitos(ArrayList<SemilleroRevisionRequisito> listaRequisitos) {
		this.listaRequisitos = listaRequisitos;
	}

	public ArrayList<SelectItem> getListaCoordinador() {
		return listaCoordinador;
	}

	public void setListaCoordinador(ArrayList<SelectItem> listaCoordinador) {
		this.listaCoordinador = listaCoordinador;
	}
}
