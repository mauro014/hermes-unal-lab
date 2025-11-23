package co.edu.unal.hermes.vista.semilleros.registro;

import java.util.Calendar;
import java.util.Iterator;

import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroArchivo;
import co.edu.unal.hermes.modelo.SemilleroEstado;
import co.edu.unal.hermes.modelo.SemilleroHistoricoEstado;
import co.edu.unal.hermes.modelo.SemilleroIntegrante;
import co.edu.unal.hermes.modelo.SemilleroSolicitud;
import co.edu.unal.hermes.modelo.SemilleroSolicitudRespuesta;
import co.edu.unal.hermes.modelo.SemilleroSolicitudTipo;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;

public class ManejadorSemilleroArchivosEnviar extends ManejadorSemilleroRegistro {

	private static final long serialVersionUID = 1L;
	private Semillero semilleroActual;
	private UploadedFile archivoSeleccionado;
	private SemilleroArchivo archivoEliminar;

	public ManejadorSemilleroArchivosEnviar() {
		init();
	}

	private void init() {
		setSemilleroActual(servicioGeneral.obtenerObjetos(Semillero.class,
				"from Semillero s where s.id=" + (Integer) sesion.getAttribute("semillero")).get(0));
	}

	public boolean isEsLider() {
		return getSemilleroActual().getLider().getId().equals(personaActual.getId());
	}

	@Override
	String guardar(boolean parcial) {
		servicioGeneral.guardarObjeto(getSemilleroActual());
		if (!parcial) {
			String vinculacionesEstudiantes = "'EL','EV','EPO','EPR','EE'";
			int contadorValidacion = 0;
			Iterator<SemilleroIntegrante> it = semilleroActual.getIntegrantes().iterator();
			while (it.hasNext()) {
				SemilleroIntegrante si = it.next();
				if (vinculacionesEstudiantes.contains(si.getTipo().getId())) {
					contadorValidacion++;
				}
			}
			if (Sede.SEDES_ANDINAS
					.contains(getSemilleroActual().getLider().getDependencia().getSede().getId().toString())
					&& contadorValidacion < 3) {
				generarMsg(2, "Para semilleros de SEDES ANDINAS, debe registrar mínimo 3 estudiantes.");
				return "";
			} else if (Sede.SEDES_PRESENCIA_NACIONAL
					.contains(getSemilleroActual().getLider().getDependencia().getSede().getId().toString())
					&& contadorValidacion < 1) {
				generarMsg(2, "Para semilleros de SEDES DE PRESENCIA NACIONAL, debe registrar mínimo 1 estudiante.");
				return "";

			}
			CorreoPlantilla cp = cargarPlantilla(343);
			Correo mail = editarCorreo(cp);
			SemilleroHistoricoEstado she = new SemilleroHistoricoEstado();
			she.setSemillero(getSemilleroActual());
			she.setEstado((SemilleroEstado) servicioGeneral.obtenerObjeto(new SemilleroEstado(), 2));
			she.setMotivo("Envio de registro de Semillero.");
			she.setFecha(Calendar.getInstance().getTime());
			she.setResponsable(getSemilleroActual().getLider());
			getSemilleroActual().getHistoricoEstados().add(she);
			servicioGeneral.guardarObjeto(she);
			if (getSemilleroActual().getSolicitudes().isEmpty()) {
				SemilleroSolicitud ss = new SemilleroSolicitud();
				ss.setSemillero(getSemilleroActual());
				ss.setFechaSolicitud(she.getFecha());
				ss.setTipo((SemilleroSolicitudTipo) servicioGeneral.obtenerObjeto(new SemilleroSolicitudTipo(), 1));
				ss.setRespuestaCoordinador(null);
				if (Sede.SEDES_ANDINAS
						.contains(getSemilleroActual().getLider().getDependencia().getSede().getId().toString())) {
					mail.setCuerpo(mail.getCuerpo().replaceAll("<<ROL>>", "UAB"));
					SemilleroSolicitudRespuesta estadoUab = new SemilleroSolicitudRespuesta();
					estadoUab.setId(1);
					ss.setRespuestaUAB(estadoUab);
					ss.setRespuestaVifDi(null);
					String sql = " JOIN i.roles r WHERE r.id = 'DD' AND i.dependencia2.id = '"
							+ semilleroActual.getLider().getDependencia().getId() + "' and (i.dependencia2.id <> '0')";
					for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class,
							sql)) {
						PersonaRol pr = (PersonaRol) servicioGeneral.obtenerPersonaRolXIdPersona(
								p.getId().getTipoDocumento(), p.getId().getDocumento(), "DD").get(0);
						if (p.getEmail() != null && pr.getFechaInicioRol().before(getToday())
								&& pr.getFechaFinRol().after(getToday())) {
							mail.adicionarDireccion(p.getEmail());
						}
					}
				} else if (Sede.SEDES_PRESENCIA_NACIONAL
						.contains(getSemilleroActual().getLider().getDependencia().getSede().getId().toString())) {
					mail.setCuerpo(mail.getCuerpo().replaceAll("<<ROL>>", "DI"));
					ss.setRespuestaUAB(null);
					SemilleroSolicitudRespuesta estadoVif = new SemilleroSolicitudRespuesta();
					estadoVif.setId(1);
					ss.setRespuestaVifDi(estadoVif);
					String sql = " JOIN i.roles r WHERE r.id = 'AD' AND i.dependencia2.sede.id = '"
							+ semilleroActual.getLider().getDependencia().getSede().getId()
							+ "' and (i.dependencia2.id <> '0')";
					for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class,
							sql)) {
						PersonaRol pr = (PersonaRol) servicioGeneral.obtenerPersonaRolXIdPersona(
								p.getId().getTipoDocumento(), p.getId().getDocumento(), "AD").get(0);
						if (p.getEmail() != null && pr.getFechaInicioRol().before(getToday())
								&& pr.getFechaFinRol().after(getToday())) {
							mail.adicionarDireccion(p.getEmail());
						}
					}
				}
				servicioGeneral.guardarObjeto(ss);
				getSemilleroActual().getSolicitudes().add(ss);
			} else {
				for (SemilleroSolicitud semSol : getSemilleroActual().getSolicitudes()) {
					if (semSol.getTipo().getId().equals(1)) {
						semSol.setFechaSolicitud(she.getFecha());
						semSol.setRespuestaCoordinador(null);
						if (Sede.SEDES_ANDINAS.contains(
								getSemilleroActual().getLider().getDependencia().getSede().getId().toString())) {
							mail.setCuerpo(mail.getCuerpo().replaceAll("<<ROL>>", "UAB"));
							SemilleroSolicitudRespuesta estadoUab = new SemilleroSolicitudRespuesta();
							estadoUab.setId(1);
							semSol.setRespuestaUAB(estadoUab);
							semSol.setRespuestaVifDi(null);
							semSol.setTipoDocResponsableUAB(null);
							semSol.setComentariosUAB(null);
							semSol.setIdResponsableUAB(null);
							semSol.setFechaRespuestaUAB(null);
							semSol.setRespuestaVifDi(null);
							semSol.setTipoDocResponsableVifDi(null);
							semSol.setComentariosVifDi(null);
							semSol.setIdResponsableVifDi(null);
							semSol.setFechaRespuestaVifDi(null);
							String sql = " JOIN i.roles r WHERE r.id = 'DD' AND i.dependencia2.id = '"
									+ semilleroActual.getLider().getDependencia().getId()
									+ "' and (i.dependencia2.id <> '0')";
							for (InvestigadorInterno p : servicioGeneral
									.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
								PersonaRol pr = (PersonaRol) servicioGeneral.obtenerPersonaRolXIdPersona(
										p.getId().getTipoDocumento(), p.getId().getDocumento(), "DD").get(0);
								if (p.getEmail() != null && pr.getFechaInicioRol().before(getToday())
										&& pr.getFechaFinRol().after(getToday())) {
									mail.adicionarDireccion(p.getEmail());
								}
							}
						} else if (Sede.SEDES_PRESENCIA_NACIONAL.contains(
								getSemilleroActual().getLider().getDependencia().getSede().getId().toString())) {
							mail.setCuerpo(mail.getCuerpo().replaceAll("<<ROL>>", "DI"));
							semSol.setRespuestaUAB(null);
							SemilleroSolicitudRespuesta estadoVif = new SemilleroSolicitudRespuesta();
							estadoVif.setId(1);
							semSol.setRespuestaVifDi(estadoVif);
							semSol.setRespuestaVifDi(null);
							semSol.setTipoDocResponsableVifDi(null);
							semSol.setComentariosVifDi(null);
							semSol.setIdResponsableVifDi(null);
							semSol.setFechaRespuestaVifDi(null);
							semSol.setTipoDocResponsableUAB(null);
							semSol.setIdResponsableUAB(null);
							semSol.setFechaRespuestaUAB(null);
							String sql = " JOIN i.roles r WHERE r.id = 'AD' AND i.dependencia2.sede.id = '"
									+ semilleroActual.getLider().getDependencia().getSede().getId()
									+ "' and (i.dependencia2.id <> '0')";
							for (InvestigadorInterno p : servicioGeneral
									.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
								PersonaRol pr = (PersonaRol) servicioGeneral.obtenerPersonaRolXIdPersona(
										p.getId().getTipoDocumento(), p.getId().getDocumento(), "AD").get(0);
								if (p.getEmail() != null && pr.getFechaInicioRol().before(getToday())
										&& pr.getFechaFinRol().after(getToday())) {
									mail.adicionarDireccion(p.getEmail());
								}
							}
						}
						servicioGeneral.guardarObjeto(semSol);
					}
				}
			}
			getSemilleroActual().setFechaRegistro(getToday());
			servicioGeneral.guardarObjeto(getSemilleroActual());
			if (!mail.getDirecciones().isEmpty()) {
				servicioCorreo.enviarCorreo(mail);
			}
			generarMsg(1, "Semillero propuesto correctamente con el ID " + getSemilleroActual().getId());
		} else {
			servicioGeneral.guardarObjeto(getSemilleroActual());
			generarMsg(1, "Semillero registrado correctamente con el ID " + getSemilleroActual().getId());
		}
		return "";
	}

	public Correo editarCorreo(CorreoPlantilla plantilla) {
		String cuerpo = plantilla.getCuerpo();
		cuerpo = cuerpo.replaceAll("<<NOMBRE>>", semilleroActual.getNombre());
		cuerpo = cuerpo.replaceAll("<<ID>>", semilleroActual.getId().toString());
		cuerpo = cuerpo.replaceAll("<<LIDER>>", semilleroActual.getLider().getNombreCompleto());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		correo.adicionarDireccion(semilleroActual.getLider().getEmail());
		//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
		correo.setAsunto(plantilla.getAsunto());
		correo.setCuerpo(cuerpo);
		return correo;
	}

	public void eliminarArchivo() {
		Integer id = archivoEliminar.getId();
		String ruta = "HER_SEMILLERO_ARCHIVO" + "//" + id;
		if (eliminarArchivoGenerico(ruta)) {
			semilleroActual.getArchivos().remove(archivoEliminar);
			servicioGeneral.eliminarObjeto(archivoEliminar);
		}
	}

	public void descargarArchivo() {
		try {
			descargarArchivoGenerico("HER_SEMILLERO_ARCHIVO", archivoEliminar.getId().toString(),
					archivoEliminar.getNombre());

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public void agregarArchivo() {
		if (archivoSeleccionado != null) {
			if (archivoSeleccionado.getSize() <= 3145728) {
				SemilleroArchivo sa = new SemilleroArchivo();
				sa.setSemillero(getSemilleroActual());
				sa.setNombre(archivoSeleccionado.getFileName());
				servicioGeneral.guardarObjeto(sa);
				cargarArchivoDisco(archivoSeleccionado, "HER_SEMILLERO_ARCHIVO", sa.getId().toString());
				semilleroActual.getArchivos().add(sa);
			} else {
				generarMsg(2, "El tamaño del archivo excede el máximo permitido (3 Mb)");
			}
		} else {
			generarMsg(2, "Por favor seleccione un archivo");
		}
	}

	public Semillero getSemilleroActual() {
		return semilleroActual;
	}

	public void setSemilleroActual(Semillero semilleroActual) {
		this.semilleroActual = semilleroActual;
	}

	public UploadedFile getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(UploadedFile archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public SemilleroArchivo getArchivoEliminar() {
		return archivoEliminar;
	}

	public void setArchivoEliminar(SemilleroArchivo archivoEliminar) {
		this.archivoEliminar = archivoEliminar;
	}
}
