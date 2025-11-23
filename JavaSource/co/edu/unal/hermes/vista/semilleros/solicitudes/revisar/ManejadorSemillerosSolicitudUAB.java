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
import co.edu.unal.hermes.modelo.SemilleroSolicitud;
import co.edu.unal.hermes.modelo.SemilleroSolicitudArchivo;
import co.edu.unal.hermes.modelo.SemilleroSolicitudRespuesta;
import co.edu.unal.hermes.modelo.correo.Correo;

public class ManejadorSemillerosSolicitudUAB extends ManejadorSemillerosSolicitudBase {

	private static final long serialVersionUID = 1L;
	private String nuevoEstado;

	public ManejadorSemillerosSolicitudUAB() {
		super();
		sesion.removeAttribute("manejadorSemillerosConsultaUAB");
		init();
	}

	private void init() {
		semilleros = servicioGeneral.obtenerObjetos(Semillero.class, "select s from Semillero s, SemilleroIntegrante si, InvestigadorInterno ii where si.semillero.id = s.id and si.tipo.id = 'DD' "
				+ "and ii.id.documento = si.integrante.id.documento and ii.id.tipoDocumento = si.integrante.id.tipoDocumento and ii.dependencia.id in ('"+investigadorInterno.getDependencia2().getId()+"')" );
		obtenerRespuestasSolicitud();
		setListaSolicitudes(new ArrayList<SemilleroSolicitud>());
		for (Semillero s : semilleros) {
			if (s.getLider()!=null && Sede.SEDES_ANDINAS.contains(s.getLider().getDependencia().getSede().getId().toString())
					&& investigadorInterno.getDependencia2().getId().equals(s.getLider().getDependencia().getId())) {
				for (SemilleroSolicitud ssol : s.getListaSolicitudes()) {
					if (ssol.getRespuestaUAB()!=null && ssol.getFechaRespuestaUAB() == null && ssol.getTipo().getId() <= 2) {
						getListaSolicitudes().add(ssol);
					}
				}
			}
		}
	}

	public void cargarSolicitud() {
		if (solicitudActual != null) {
			switch (solicitudActual.getTipo().getId()) {
			case 1:
				if(solicitudActual.getRespuestaUAB()==null || solicitudActual.getRespuestaUAB().getId()==null) {
					SemilleroSolicitudRespuesta uab = new SemilleroSolicitudRespuesta();
					uab.setId(1);
					solicitudActual.setRespuestaUAB(uab);
				}
				break;
			case 2:
				nuevoEstado = solicitudActual.getDetalle().equals("5") ? "Activo"
						: (solicitudActual.getDetalle().equals("6") ? "Suspendido" : "Inactivo");
				break;
			}
		}
	}

	private void obtenerRespuestasSolicitud() {
		if (getRespuestasSolicitud().isEmpty()) {
			List<SemilleroSolicitudRespuesta> ssr = servicioGeneral.obtenerObjetos(SemilleroSolicitudRespuesta.class,
					"select srr from SemilleroSolicitudRespuesta srr where srr.estadoUAB='Y'");
			for (SemilleroSolicitudRespuesta resp : ssr) {
				getRespuestasSolicitud().add(new SelectItem(resp.getId(), resp.getNombre()));
			}
		}
	}

	public void agregarArchivo(FileUploadEvent event) {
		archivoSeleccionado = event.getFile();
		super.agregarArchivo("D");
	}

	public void validarFinalizado() {
		init();
	}

	public String actualizarSolicitud() {
		if (!getSolicitudActual().getRespuestaUAB().getId().equals(1)) {
			CorreoPlantilla cp = null;
			if (esCadenaVacia(getSolicitudActual().getComentariosUAB())) {
				if (getSolicitudActual().getRespuestaUAB().getId().equals(3)
						&& solicitudActual!=null && solicitudActual.getTipo()!=null && solicitudActual.getTipo().getId().equals(1)) {
					generarMsgModal(2, "Por favor, indique la pertinencia de la creación del semillero.");
				} else {
					generarMsgModal(2, "Por favor, indique las observaciones sobre la solicitud.");
				}
				return "";
			} else {
				getSolicitudActual().setIdResponsableUAB(personaActual.getId().getDocumento());
				getSolicitudActual().setTipoDocResponsableUAB(personaActual.getId().getTipoDocumento());
				if (solicitudActual!=null && solicitudActual.getTipo()!=null && solicitudActual.getTipo().getId()!=null && solicitudActual.getTipo().getId().equals(1)) {
					SemilleroHistoricoEstado she = new SemilleroHistoricoEstado();
					she.setSemillero(getSolicitudActual().getSemillero());
					she.setFecha(Calendar.getInstance().getTime());
					she.setResponsable(servicioPersona.obtenerInvestigador(personaActual.getId()));
					getSolicitudActual().getSemillero().getHistoricoEstados().add(she);
					if (getSolicitudActual().getRespuestaUAB().getId().equals(2)) {
						she.setEstado((SemilleroEstado) servicioGeneral.obtenerObjeto(new SemilleroEstado(), 1));
						she.setMotivo("Semillero Deuelto para correcciones a Docente.");
						cp = cargarPlantilla(344);
					} else if (getSolicitudActual().getRespuestaUAB().getId().equals(3)) {
						she.setEstado((SemilleroEstado) servicioGeneral.obtenerObjeto(new SemilleroEstado(), 3));
						she.setMotivo("Semillero con Visto Bueno de UAB.");
						cp = cargarPlantilla(346);
						getSolicitudActual().setRespuestaVifDi(
								servicioGeneral.obtenerObjetoXID(SemilleroSolicitudRespuesta.class, "1").get(0));
					} else if (getSolicitudActual().getRespuestaUAB().getId().equals(4)) {
						she.setEstado((SemilleroEstado) servicioGeneral.obtenerObjeto(new SemilleroEstado(), 4));
						she.setMotivo("Semillero No Aprobado en UAB.");
						cp = cargarPlantilla(345);
					}
					if (cp != null) {
						cp.setCuerpo(
								cp.getCuerpo().replaceAll("<<OBSERVACIONES>>", solicitudActual.getComentariosUAB()));
						cp.setCuerpo(cp.getCuerpo().replaceAll("<<ROL>>", "UAB"));
						cp.setCuerpo(cp.getCuerpo().replaceAll("<<RESPONSABLE>>", personaActual.getNombreCompleto()));
						Correo mail = editarCorreo(cp);
						mail.adicionarDireccion(solicitudActual.getSemillero().getLider().getEmail());
						servicioCorreo.enviarCorreo(mail);
						if (cp.getId().equals(346L)) {
							cp = cargarPlantilla(347);
							cp.setCuerpo(cp.getCuerpo().replaceAll("<<DEPENDENCIA>>",
									((InvestigadorInterno) personaActual).getDependencia2().getNombre()));
							mail = editarCorreo(cp);
							String sql = " JOIN i.roles r WHERE r.id = 'AF' AND i.dependencia2.facultad.id = '"
									+ solicitudActual.getSemillero().getLider().getDependencia().getFacultad().getId()
									+ "' and (i.dependencia2.id <> '0')";
							for (InvestigadorInterno p : servicioGeneral
									.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
								if (p.getEmail() != null) {
									mail.adicionarDireccion(p.getEmail());
								}
							}
							servicioCorreo.enviarCorreo(mail);
						}
					}
					servicioGeneral.guardarObjeto(she);
				} else {
					cp = cargarPlantilla(353);
					cp.setCuerpo(cp.getCuerpo().replaceAll("<<DEPENDENCIA>>",
							((InvestigadorInterno) personaActual).getDependencia2().getNombre()));
					cp.setCuerpo(cp.getCuerpo().replaceAll("<<RESPONSABLE>>", personaActual.getNombreCompleto()));
					cp.setCuerpo(cp.getCuerpo().replaceAll("<<OBSERVACIONES>>", solicitudActual.getComentariosUAB()));
					if (getSolicitudActual().getRespuestaUAB().getId().equals(2)) {
						cp.setCuerpo(cp.getCuerpo().replaceAll("<<RESPUESTA>>", "ha DEVUELTO PARA CORRECCIONES"));
						solicitudActual.setRespuestaUAB(
								servicioGeneral.obtenerObjetoXID(SemilleroSolicitudRespuesta.class, "0").get(0));
						solicitudActual.setFechaRespuestaUAB(null);
					} else if (getSolicitudActual().getRespuestaUAB().getId().equals(3)) {
						cp = cargarPlantilla(352);
						getSolicitudActual().setRespuestaCoordinador(
								servicioGeneral.obtenerObjetoXID(SemilleroSolicitudRespuesta.class, "1").get(0));
					} else if (getSolicitudActual().getRespuestaUAB().getId().equals(4)) {
						cp.setCuerpo(cp.getCuerpo().replaceAll("<<RESPUESTA>>", "ha RECHAZADO"));
						solicitudActual.setRespuestaUAB(
								servicioGeneral.obtenerObjetoXID(SemilleroSolicitudRespuesta.class, "4").get(0));
					}
					cp.setCuerpo(cp.getCuerpo().replaceAll("<<ESTADO>>", nuevoEstado.toUpperCase()));
					Correo mail = editarCorreo(cp);
					mail.adicionarDireccion(solicitudActual.getSemillero().getLider().getEmail());
					if (cp.getId().equals(352L)) {
						mail.adicionarDireccion((servicioPersona
								.obtenerInvestigador(
										new IdPersona(solicitudActual.getSemillero().getDocumentoCoordinador(),
												solicitudActual.getSemillero().getTipoDocumentoCoordinador()))
								.getEmail()));
					}
					servicioCorreo.enviarCorreo(mail);
				}
				getSolicitudActual().setFechaRespuestaUAB(Calendar.getInstance().getTime());
				getSolicitudActual().setFechaRespuestaVifDi(null);
				servicioGeneral.guardarObjeto(getSolicitudActual());
				generarMsgModal(1, "Solicitud Actualizada correctamente.");
				if (getSolicitudActual().getRespuestaUAB().getId().equals(0)) {
					solicitudActual.setRespuestaUAB(
							servicioGeneral.obtenerObjetoXID(SemilleroSolicitudRespuesta.class, "2").get(0));
				}
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

	public String getNuevoEstado() {
		return nuevoEstado;
	}

	public void setNuevoEstado(String nuevoEstado) {
		this.nuevoEstado = nuevoEstado;
	}
}
