package co.edu.unal.hermes.vista.requerimiento;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.UploadedFile;
import co.edu.unal.hermes.modelo.ArchivoRequerimiento;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.HistoricoRequerimiento;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Requerimiento;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorEditarRequerimiento extends ManejadorBase {

	Requerimiento requerimiento;
	private ArchivoRequerimiento archivoSeleccionado;
	ArchivoRequerimiento archivo;
	List<Dependencia> dependenciasUN;
	List<TipoDocumento> listaTipoDocumento;
	List<DominioDetalle> listaEstReq;
	DominioDetalle estadosReq;
	String estadoSelReq;
	List<DominioDetalle> listaTipoArcReq;
	DominioDetalle tipoArcReq;
	String tipoArcSelReq;
	List<DominioDetalle> listaIngenieros;
	DominioDetalle tipoListIng;
	String tipoListIngSel;
	private UploadedFile archivoCargarUno;
	List<ArchivoRequerimiento> listaDocumentos;
	private String errorValidacion;
	private String mensajeAdjuntaDocumentos = "";
	private String antiguaFechaTramite;
	Dependencia dependenciaReq;
	public SelectItem[] dependenciaItem;
	public SelectItem[] tipoDocumentoItem;
	public SelectItem[] estadosItem;
	public SelectItem[] tipoArcItem;
	public SelectItem[] tipoIngItem;
	public SelectItem[] prioridadItem = { new SelectItem("Urgente", "Urgente"), new SelectItem("Alta", "Alta"),
			new SelectItem("Media", "Media"), new SelectItem("Baja", "Baja") };
	private TipoDocumento tipoDocAsignado;
	private String documentoAsignado;
	private boolean esCoord = false;
	private int nPlantilla = 186;
	private int nPlantilla_usuario = 207;
	// private Requerimiento requerimientoEditar;
	private Requerimiento requerimientoSeleccionado;
	private List requerimientoLista;
	public SelectItem[] moduloItems;
	public SelectItem[] subModuloItems;
	private UploadedFile archivoLeg;
	private TipoArchivo tipoArchivo;
	private List listaTipoArchivo;
	private SelectItem[] tipoArchivoItem;
	private DataTable tablaArchivosLeg;
	private ArchivoRequerimiento archivoSeleccionadoLeg = new ArchivoRequerimiento();
	private int plantilla = 227;
	private String observacionesIngOld;
	private String mensajeUsuarioOld;

	public ManejadorEditarRequerimiento() {
		listaDocumentos = new ArrayList<ArchivoRequerimiento>();
		estadosReq = new DominioDetalle();
		tipoArcReq = new DominioDetalle();
		Long idRequerimiento = (Long) sesion.getAttribute("requerimientoEditable");
		if (idRequerimiento != null) {
			List lista = servicioGeneral.obtenerObjetoXID("Requerimiento", idRequerimiento.toString());
			if (lista.size() > 0) {
				requerimiento = (Requerimiento) lista.get(0);
				if (this.requerimiento.getFechaTramite() != null) {
					antiguaFechaTramite = new SimpleDateFormat("yyyy-MM-dd")
							.format(this.requerimiento.getFechaTramite());
				} else {
					antiguaFechaTramite = "";
				}
				listaDocumentos = servicioProyecto.obtenerArchivosRequerimientosLista(idRequerimiento);
				if (requerimiento.getObservacionesIng() != null) {
					observacionesIngOld = requerimiento.getObservacionesIng();
				} else {
					observacionesIngOld = "";
				}
				if (requerimiento.getComentarios() != null) {
					setMensajeUsuarioOld(requerimiento.getComentarios());
				} else {
					setMensajeUsuarioOld("");
				}
			}
		}
		cargarListas();
	}

	public void cargarListas() {
		// Tipos Documento
		listaTipoDocumento = new ArrayList<TipoDocumento>();
		listaTipoDocumento = servicioGeneral.obtenerListaObjetos(TipoDocumento.class);
		TipoDocumento tdAux = listaTipoDocumento.get(0);
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
			td = null;
		}
		// Dependencias
		dependenciasUN = new ArrayList<Dependencia>();
		dependenciasUN = servicioGeneral.obtenerListaObjetos(Dependencia.class);
		Dependencia dAux = dependenciasUN.get(0);
		dependenciaItem = new SelectItem[dependenciasUN.size()];
		for (int i = 0; i < dependenciasUN.size(); i++) {
			Dependencia dd = (Dependencia) dependenciasUN.get(i);
			dependenciaItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}
		// Estados Requerimiento
		listaEstReq = new ArrayList<DominioDetalle>();
		listaEstReq = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"from DominioDetalle where identificador.id = '102'");
		DominioDetalle esAux = listaEstReq.get(0);
		estadosItem = new SelectItem[listaEstReq.size()];
		for (int i = 0; i < listaEstReq.size(); i++) {
			DominioDetalle es = (DominioDetalle) listaEstReq.get(i);
			estadosItem[i] = new SelectItem(es.getDescripcion(), es.getDescripcion());
			es = null;
		}
		estadoSelReq = estadosReq != null ? estadosReq.getDescripcion() : "R";
		// Tipos de Archivo Requerimiento
		listaTipoArcReq = new ArrayList<DominioDetalle>();
		listaTipoArcReq = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"from DominioDetalle where identificador.id = '104'");
		DominioDetalle taAux = listaTipoArcReq.get(0);
		if (listaTipoArcReq.size() > 0) {
			tipoArcItem = new SelectItem[listaTipoArcReq.size()];
			for (int i = 0; i < listaTipoArcReq.size(); i++) {
				DominioDetalle ta = (DominioDetalle) listaTipoArcReq.get(i);
				tipoArcItem[i] = new SelectItem(ta.getDescripcion(), ta.getDescripcion());
				ta = null;
			}
			tipoArcSelReq = (String) tipoArcItem[0].getValue();
		}
		listaTipoArchivo = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"from TipoArchivo e where e.id in (52)");
		tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
		for (int i = 0; i < listaTipoArchivo.size(); i++) {
			TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
			tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
		}
		tipoArchivo = (TipoArchivo) listaTipoArchivo.get(0);
		// Ingenieros
		listaIngenieros = new ArrayList<DominioDetalle>();
		listaIngenieros = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"from DominioDetalle where identificador.id = '107' and observacion='A'");
		if (listaIngenieros.size() > 0) {
			tipoIngItem = new SelectItem[listaIngenieros.size()];
			for (int i = 0; i < listaIngenieros.size(); i++) {
				DominioDetalle ta = (DominioDetalle) listaIngenieros.get(i);
				tipoIngItem[i] = new SelectItem(ta.getIdentificador().getTipo(), ta.getDescripcion());
				ta = null;
			}
			tipoListIngSel = (String) tipoIngItem[0].getValue();
		}
		// Módulos
		List listaMod = new ArrayList();
		listaMod = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"from DominioDetalle where identificador.id = '113' and estado='0'");
		DominioDetalle aux = (DominioDetalle) listaMod.get(0);
		if (listaMod.size() > 0) {
			moduloItems = new SelectItem[listaMod.size()];
			for (int i = 0; i < listaMod.size(); i++) {
				DominioDetalle ta = (DominioDetalle) listaMod.get(i);
				moduloItems[i] = new SelectItem(ta.getIdentificador().getTipo(), ta.getDescripcion());
				ta = null;
			}
		}
		// Sub-Módulos
		List listasMod = new ArrayList();
		if (this.requerimiento == null) {
			this.requerimiento = new Requerimiento();
			if (this.requerimiento.getModulo() == null)
				this.requerimiento.setModulo("113_30");
		}
		if (this.requerimiento.getSubModulo() == null) {
			listasMod = servicioGeneral.obtenerObjetos(DominioDetalle.class,
					"from DominioDetalle where identificador.id = '113' and estado='" + this.requerimiento.getModulo()
							+ "'");
			if (listasMod.size() > 0) {
				aux = (DominioDetalle) listasMod.get(0);
				subModuloItems = new SelectItem[listasMod.size()];
				for (int i = 0; i < listasMod.size(); i++) {
					DominioDetalle ta = (DominioDetalle) listasMod.get(i);
					subModuloItems[i] = new SelectItem(ta.getIdentificador().getTipo(), ta.getDescripcion());
					ta = null;
				}
			}
		}
	}

	public void cargarSubModulo() {
		if (this.requerimiento.getModulo() == null)
			this.requerimiento.setModulo("113_30");
		List listaMod = new ArrayList();
		listaMod = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"from DominioDetalle where identificador.id = '113' and estado='" + this.requerimiento.getModulo()
						+ "'");
		DominioDetalle aux = (DominioDetalle) listaMod.get(0);
		if (listaMod.size() > 0) {
			subModuloItems = new SelectItem[listaMod.size()];
			for (int i = 0; i < listaMod.size(); i++) {
				DominioDetalle ta = (DominioDetalle) listaMod.get(i);
				subModuloItems[i] = new SelectItem(ta.getIdentificador().getTipo(), ta.getDescripcion());
				ta = null;
			}
		}
	}

	public void buscarPersona() {
	}

	// subir archivos lmom
	public String insertarArchivoReq() {
		TipoArchivo tipoAr = new TipoArchivo();
		List listaAr = servicioGeneral.obtenerObjetoXID("TipoArchivo", tipoArchivo.getId() + "");
		tipoAr = (TipoArchivo) listaAr.get(0);
		if (requerimiento.getId() != null) {
			if (archivoLeg != null) {
				return insertarArchivoRequerimiento(archivoLeg, requerimiento, listaDocumentos, tipoAr);
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage mensaje = new FacesMessage("Por favor revise el documento seleccionado.");
				context.addMessage("datosGuardados", mensaje);
				return "";
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(
					"Por favor guarde primero su solicitud y luego adjunte su documento.");
			mensaje.setSeverity(mensaje.SEVERITY_ERROR);
			context.addMessage("datosGuardados", mensaje);
			return "";
		}
	}

	public void descargarArchivo() {
		Long id = archivoSeleccionadoLeg.getId();
		descargarArchivoRequerimientoGenerico(id, requerimiento.getId());
	}

	public String eliminarArchivoLeg() {
		Long id = archivoSeleccionadoLeg.getId();
		ArchivoRequerimiento archivo = servicioProyecto.obtenerArchivoRequerimiento(id);
		boolean entra = false;
		boolean elimina = false;
		if (archivo != null && (archivo.getArchivo() == null || archivo.getBytes().length <= 1)) {
			entra = true;
			elimina = eliminarArchivoRequerimientoGenerico(id);
		}
		if (!entra) {
			servicioGeneral.eliminarObjeto(archivo);
		} else if (elimina) {
			servicioGeneral.eliminarObjeto(archivo);
			listaDocumentos.remove(archivoSeleccionadoLeg);
		}
		return "";
	}

	public void guardarCoord() {
		esCoord = true;
		if (requerimiento.getIngenieroAsignado() != null && !requerimiento.getIngenieroAsignado().equals("0")) {
			guardar();
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("Por favor seleccione el ingeniero asignado.");
			mensaje.setSeverity(mensaje.SEVERITY_ERROR);
			context.addMessage("datosGuardados", mensaje);
		}
	}

	public void guardar() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (this.requerimiento.getEstSelRequerimiento().equals("En Desarrollo")) {
			String d;
			try {
				d = this.requerimiento.getFechaTramite().toString();
			} catch (Exception e) {
				d = "";
			}
			if (StringUtils.isEmpty(d)) {
				FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe definir una fecha de Inicio de Trámite.", "");
				context.addMessage("", mensaje);
				this.requerimiento.setEstSelRequerimiento("Registrado");
				return;
			}
			if (!new SimpleDateFormat("yyyy-MM-dd").format(this.requerimiento.getFechaTramite())
					.equals(antiguaFechaTramite)) {
				this.requerimiento.setAlertaEnviada(false);
				antiguaFechaTramite = new SimpleDateFormat("yyyy-MM-dd").format(this.requerimiento.getFechaTramite());
			}
		}
		if (requerimiento != null && !requerimiento.getModulo().equals("0")) {
			if (requerimiento != null && !requerimiento.getSubModulo().equals("0")) {
				if (requerimiento != null && requerimiento.getDescripcionSolic() != null
						&& !requerimiento.getDescripcionSolic().equals("")
						&& !requerimiento.getDescripcionSolic().equals(" ")) {
					Persona personaActual = servicioPersona
							.obtenerPersona(((Persona) sesion.getAttribute("persona")).getId());
					if (personaActual != null) {
						try {
							Investigador investigadorActual = servicioPersona
									.obtenerInvestigadorInterno(personaActual.getId());
							// Estado
							List listaEstReq = new ArrayList<DominioDetalle>();
							listaEstReq = servicioGeneral.obtenerObjetos(DominioDetalle.class,
									"from DominioDetalle where identificador.id = '102'");
							for (int i = 0; i < listaEstReq.size(); i++) {
								DominioDetalle es = (DominioDetalle) listaEstReq.get(i);
								if (es.getIdentificador().getTipo()
										.equals(this.requerimiento.getEstSelRequerimiento())) {
									this.requerimiento.setEstadoRequerimiento(es);
									this.requerimiento.setEstSelRequerimiento(es.getDescripcion());
								}
							}
							if (this.requerimiento.getEstSelRequerimiento().equals("Solucionado")) {
								this.requerimiento.setFechaEntrega(new Date());
							} else if (this.requerimiento.getEstSelRequerimiento().equals("En Desarrollo")) {
								Rol r = new Rol();
								r = servicioPersona.obtenerRol("AR");
								List<Persona> personaRol = servicioPersona.obtenerPersonasxRol(r);
								CorreoPlantilla correoActual = new CorreoPlantilla();
								correoActual = cargarPlantilla(307);
								Persona coord = new Persona();
								coord.setNombre1("Coordinador");
								coord.setApellido1("HERMES");
								coord.setEmail("coordihrms_nal@unal.edu.co");
								personaRol.add(coord);
								for (Persona adm : personaRol) {
									String cuerpoCorreo = correoActual.getCuerpo();
									cuerpoCorreo = cuerpoCorreo.replaceAll("<<ADMINISTRADOR>>",
											adm.getNombreCompleto());
									cuerpoCorreo = cuerpoCorreo.replaceAll("<<SOLICITANTE>>",
											(personaActual.getGenero() == "F" ? "la ingeniera " : "el ingeniero ")
													+ personaActual.getNombreCompleto());
									cuerpoCorreo = cuerpoCorreo.replaceAll("<<TIPO>>",
											this.requerimiento.getTipo() == "Inconveniente" ? "el Inconveniente"
													: "la Solicitud de Mejora");
									cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>",
											this.requerimiento.getId().toString());
									if (this.requerimiento.getComentariosIngeniero() != null) {
										cuerpoCorreo = cuerpoCorreo.replaceAll("<<COMENTARIO>>",
												this.requerimiento.getComentariosIngeniero());
									} else {
										cuerpoCorreo = cuerpoCorreo.replaceAll("<<COMENTARIO>>",
												"<Sin comentarios al ingeniero>");
									}
									Correo mensaje = new Correo();
									mensaje.setOrigen(Correo.CORREO_HERMES);
									//mensaje.adicionarCopiaOculta(Correo.CORREO_HERMES);
									mensaje.setAsunto(correoActual.getAsunto()
											.replaceAll("<<ID>>", this.requerimiento.getId().toString())
											.replaceAll("<<TIPO>>", this.requerimiento.getTipo()));
									mensaje.setCuerpo(cuerpoCorreo);
									mensaje.adicionarDireccion(adm.getEmail());
									servicioCorreo.enviarCorreo(mensaje);
								}
							}
							// Guardar objeto
							servicioGeneral.guardarObjeto(requerimiento);
							if (requerimiento.getComentarios() != null) {
								if (!requerimiento.getComentarios().trim().toUpperCase()
										.equals(mensajeUsuarioOld.trim().toUpperCase())) {
									HistoricoRequerimiento hr = new HistoricoRequerimiento();
									hr.setFechaCambio(getToday());
									hr.setComentariosUsuario(this.requerimiento.getComentarios());
									hr.setRequerimiento(this.requerimiento);
									servicioGeneral.guardarObjeto(hr);
								}
							}
							FacesMessage mensaje = new FacesMessage(
									"La información ha sido guardada correctamente con el número "
											+ requerimiento.getId());
							context.addMessage("datosGuardados", mensaje);
						} catch (Exception e) {
							System.out.print(e);
						}
					}
				} else {
					FacesMessage mensaje = new FacesMessage(
							"La información no ha sido guardada. Ingrese la descripción. ");
					mensaje.setSeverity(mensaje.SEVERITY_ERROR);
					context.addMessage("datosGuardados", mensaje);
				}
			} else {
				FacesMessage mensaje = new FacesMessage("La información no ha sido guardada. Seleccione el proceso. ");
				mensaje.setSeverity(mensaje.SEVERITY_ERROR);
				context.addMessage("datosGuardados", mensaje);
			}
		} else {
			FacesMessage mensaje = new FacesMessage("La información no ha sido guardada. Seleccione el módulo. ");
			mensaje.setSeverity(mensaje.SEVERITY_ERROR);
			context.addMessage("datosGuardados", mensaje);
		}
	}

	public void enviar() {
		try {
			// Guardar objeto
			guardar();
			requerimiento = new Requerimiento();
			listaDocumentos = new ArrayList<ArchivoRequerimiento>();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("La información no ha sido enviada. ");
			mensaje.setSeverity(mensaje.SEVERITY_ERROR);
			context.addMessage("datosGuardados", mensaje);
		}
	}

	public void mensajeUsuario() {
		try {
			// Correo al Solicitante
			Correo correo = new Correo();
			CorreoPlantilla cp = cargarPlantilla(nPlantilla_usuario);
			correo.setOrigen(Correo.CORREO_HERMES);
			correo.setAsunto(cp.getAsunto());
			correo.setCuerpo(cp.getCuerpo().replaceAll("<<USUARIO>>", this.requerimiento.getPersonaRequerimiento()));
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<ID>>", this.requerimiento.getId().toString()));
			correo.setCuerpo(
					correo.getCuerpo().replaceAll("<<DESCRIPCION>>", this.requerimiento.getDescripcionSolic()));
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<RTA>>", this.requerimiento.getComentarios()));
			System.out.println("correo ===" + correo.getCuerpo());
			if (this.requerimiento.getEmailSolicitante() != null) {
				correo.adicionarDireccion(this.requerimiento.getEmailSolicitante());
			} else {
				IdPersona idper = new IdPersona(this.requerimiento.getIdPersona(),
						this.requerimiento.getTipoDoc().getId());
				Persona per = servicioPersona.obtenerPersona(idper);
				if (per != null) {
					correo.adicionarDireccion(per.getEmail());
				} else {
					correo.adicionarDireccion(Correo.CORREO_HERMES_COMUNICACIONES);
				}
			}
			//correo.adicionarCopiaOculta(Correo.CORREO_HERMES_COMUNICACIONES);
			servicioCorreo.enviarCorreo(correo);
			if (requerimiento.getComentarios() != null) {
				if (!requerimiento.getComentarios().trim().toUpperCase()
						.equals(mensajeUsuarioOld.trim().toUpperCase())) {
					HistoricoRequerimiento hr = new HistoricoRequerimiento();
					hr.setFechaCambio(getToday());
					hr.setComentariosUsuario(this.requerimiento.getComentarios());
					hr.setRequerimiento(this.requerimiento);
					servicioGeneral.guardarObjeto(hr);
				}
			}
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("La información ha sido enviada correctamente.");
			context.addMessage("datosGuardados", mensaje);
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void mensajeIngeniero() {
		try {
			Correo correo = new Correo();
			CorreoPlantilla cp = cargarPlantilla(nPlantilla);
			correo.setOrigen(Correo.CORREO_HERMES);
			correo.setAsunto((cp.getAsunto().replaceAll("<<TIPO>>", this.requerimiento.getTipo())).replaceAll("<<ID>>",
					this.requerimiento.getId().toString()));
			TipoDocumento tipoD = new TipoDocumento();
			tipoD.setId("C");
			IdPersona idP = new IdPersona(this.requerimiento.getIngenieroAsignado(), tipoD.getId());
			Persona perAsig = servicioPersona.obtenerPersona(idP);
			correo.setCuerpo(cp.getCuerpo().replaceAll("<<INGENIERO>>", perAsig.getNombreCompleto()));
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<ID>>", this.requerimiento.getId().toString()));
			correo.setCuerpo(
					correo.getCuerpo().replaceAll("<<OBSERVACIONES>>", this.requerimiento.getComentariosIngeniero()));
			correo.setCuerpo(correo.getCuerpo().replaceAll("<<TIPO>>", this.requerimiento.getTipo().toUpperCase()));
			if (this.requerimiento.getTipo().equals("Mejora")) {
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<BUZON>>", "MEJORAS ASIGNADAS"));
				if (this.requerimiento.getFechaEstimada() != null) {
					correo.setCuerpo(correo.getCuerpo().replaceAll("<<ENTREGA>>",
							new SimpleDateFormat("dd/MM/yyyy").format(this.requerimiento.getFechaEstimada())));
				} else {
					correo.setCuerpo(correo.getCuerpo().replaceAll("<<ENTREGA>>", "No Definida"));
				}
				if (this.requerimiento.getFechaTramite() != null) {
					correo.setCuerpo(correo.getCuerpo().replaceAll("<<TRAMITE>>",
							new SimpleDateFormat("dd/MM/yyyy").format(this.requerimiento.getFechaTramite())));
				} else {
					correo.setCuerpo(correo.getCuerpo().replaceAll("<<TRAMITE>>", "No Definida"));
				}
			} else {
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<BUZON>>", "INCONVENIENTES ASIGNADOS"));
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<ENTREGA>>", "INMEDIATA"));
				correo.setCuerpo(correo.getCuerpo().replaceAll("<<TRAMITE>>", "A LA BREVEDAD POSIBLE"));
			}
			System.out.println("correo ===" + correo.getCuerpo());
			String email = "hermes@unal.edu.co";
			if (perAsig.getEmail() != null) {
				email = perAsig.getEmail();
			}
			correo.adicionarDireccion(email);
			//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
			servicioCorreo.enviarCorreo(correo);
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("La información ha sido enviada correctamente.");
			context.addMessage("datosGuardados", mensaje);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage mensaje = new FacesMessage(e.getStackTrace().toString());
		}
	}

	// Alertas
	public void enviarAlertas() {
		Date fechaActual = new Date();
		try {
			String hql = "from Requerimiento r WHERE r.estSelRequerimiento LIKE 'En Tr_mite'";
			System.out.println(hql);
			List<Requerimiento> listaReqs = new ArrayList<Requerimiento>();
			listaReqs = servicioGeneral.obtenerObjetos(Requerimiento.class, hql);
			int correosEnviados = 0;
			for (int i = 0; i < listaReqs.size(); i++) {
				Requerimiento req = (Requerimiento) listaReqs.get(i);
				Date fechaReq = req.getFechaEstimada();
				// Enviar alertas a solicitudes q estén pendientes
				if (fechaReq == null) {
					fechaReq = req.getFechaSolicitud();
				}
				if (req.getIngenieroAsignado() != null) {
					CorreoPlantilla correoPlantilla = new CorreoPlantilla();
					if (fechaReq.before(fechaActual)) {
						// Mejora vencida.
						correoPlantilla = cargarPlantilla(plantilla);
					} else if (((fechaReq.getTime() - fechaActual.getTime()) / (1000 * 60 * 60 * 24)) <= 7) {
						// Mejora próxima a vencer.
						correoPlantilla = cargarPlantilla(305);
					}
					if (correoPlantilla.getId() != null) {
						// Correo
						String asunto = correoPlantilla.getAsunto();
						Correo correo = new Correo();
						correo.setOrigen(Correo.CORREO_HERMES);
						asunto = (asunto.replaceAll("<<TIPO>>", req.getTipo())).replaceAll("<<ID>>",
								req.getId().toString());
						correo.setAsunto(asunto);
						correo.setCuerpo(correoPlantilla.getCuerpo());
						// email del ingeniero
						IdPersona idPer = new IdPersona(req.getIngenieroAsignado(), "C");
						Persona per = servicioPersona.obtenerPersona(idPer);
						if (per != null) {
							correo.adicionarDireccion(per.getEmail());
							//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
							String cuerpoCorreo = correo.getCuerpo();
							cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", req.getId().toString());
							cuerpoCorreo = cuerpoCorreo.replaceAll("<<TIPO>>", req.getTipo());
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							cuerpoCorreo = cuerpoCorreo.replaceAll("<<VENCIMIENTO>>",
									formatter.format(fechaReq) + " (día/mes/año)");
							correo.setCuerpo(cuerpoCorreo);
							if (servicioCorreo.enviarCorreo(correo)) {
								correosEnviados++;
							}
						}
					}
					System.out.println("Se han enviado " + correosEnviados + " alertas a los ingenieros");
				}
			}
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("Se han enviado " + correosEnviados + " alertas a los ingenieros");
			context.addMessage("datosGuardados", mensaje);
		} catch (Exception mex) {
			System.out.println("Error al enviar correo(s) de Alerta: " + mex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public void guardarAsignado() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (this.requerimiento.getEstSelRequerimiento().equals("En Desarrollo")) {
			String d;
			try {
				d = this.requerimiento.getFechaTramite().toString();
			} catch (Exception e) {
				d = "";
			}
			if (StringUtils.isEmpty(d)) {
				FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe definir una fecha de Inicio de Trámite.", "");
				context.addMessage("", mensaje);
				this.requerimiento.setEstSelRequerimiento("Registrado");
				return;
			}
			if (requerimiento.getObservacionesIng() != null) {
				if (!requerimiento.getObservacionesIng().trim().toUpperCase()
						.equals(observacionesIngOld.trim().toUpperCase())) {
					enviarMensajeCambioObservacion();
				}
			}
		}
		if (this.requerimiento.getFechaTramite() != null) {
			if (!new SimpleDateFormat("yyyy-MM-dd").format(this.requerimiento.getFechaTramite())
					.equals(antiguaFechaTramite)) {
				this.requerimiento.setAlertaEnviada(false);
				antiguaFechaTramite = new SimpleDateFormat("yyyy-MM-dd").format(this.requerimiento.getFechaTramite());
			}
		}
		Persona personaActual = servicioPersona.obtenerPersona(((Persona) sesion.getAttribute("persona")).getId());
		if (personaActual != null) {
			try {
				// Estado
				List listaEstReq = new ArrayList<DominioDetalle>();
				listaEstReq = servicioGeneral.obtenerObjetos(DominioDetalle.class,
						"from DominioDetalle where identificador.id = '102'");
				for (int i = 0; i < listaEstReq.size(); i++) {
					DominioDetalle es = (DominioDetalle) listaEstReq.get(i);
					if (es.getIdentificador().getTipo().equals(this.requerimiento.getEstSelRequerimiento())) {
						this.requerimiento.setEstadoRequerimiento(es);
						this.requerimiento.setEstSelRequerimiento(es.getDescripcion());
					}
				}
				if (this.requerimiento.getEstSelRequerimiento().equals("Solucionado")) {
					this.requerimiento.setFechaEntrega(new Date());
				} else if (this.requerimiento.getEstSelRequerimiento().equals("En Desarrollo")) {
					Rol r = new Rol();
					r = servicioPersona.obtenerRol("AR");
					List<Persona> personaRol = servicioPersona.obtenerPersonasxRol(r);
					CorreoPlantilla correoActual = new CorreoPlantilla();
					correoActual = cargarPlantilla(307);
					Persona coord = new Persona();
					coord.setNombre1("Coordinador");
					coord.setApellido1("HERMES");
					coord.setEmail("coordihrms_nal@unal.edu.co");
					personaRol.add(coord);
					for (Persona adm : personaRol) {
						String cuerpoCorreo = correoActual.getCuerpo();
						cuerpoCorreo = cuerpoCorreo.replaceAll("<<ADMINISTRADOR>>", adm.getNombreCompleto());
						cuerpoCorreo = cuerpoCorreo.replaceAll("<<SOLICITANTE>>",
								(personaActual.getGenero() == "F" ? "la ingeniera " : "el ingeniero ")
										+ personaActual.getNombreCompleto());
						cuerpoCorreo = cuerpoCorreo.replaceAll("<<TIPO>>",
								this.requerimiento.getTipo() == "Inconveniente" ? "el Inconveniente"
										: "la Solicitud de Mejora");
						cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", this.requerimiento.getId().toString());
						if (this.requerimiento.getComentariosIngeniero() != null) {
							cuerpoCorreo = cuerpoCorreo.replaceAll("<<COMENTARIO>>",
									this.requerimiento.getComentariosIngeniero());
						} else {
							cuerpoCorreo = cuerpoCorreo.replaceAll("<<COMENTARIO>>", "<Sin comentarios al ingeniero>");
						}
						Correo mensaje = new Correo();
						mensaje.setOrigen(Correo.CORREO_HERMES);
						//mensaje.adicionarCopiaOculta(Correo.CORREO_HERMES);
						mensaje.setAsunto(
								correoActual.getAsunto().replaceAll("<<ID>>", this.requerimiento.getId().toString())
										.replaceAll("<<TIPO>>", this.requerimiento.getTipo()));
						mensaje.setCuerpo(cuerpoCorreo);
						mensaje.adicionarDireccion(adm.getEmail());
						servicioCorreo.enviarCorreo(mensaje);
					}
				}
				// Guardar objeto
				servicioGeneral.guardarObjeto(requerimiento);
				if (requerimiento.getComentarios() != null) {
					if (!requerimiento.getComentarios().trim().toUpperCase()
							.equals(mensajeUsuarioOld.trim().toUpperCase())) {
						HistoricoRequerimiento hr = new HistoricoRequerimiento();
						hr.setFechaCambio(getToday());
						hr.setComentariosUsuario(this.requerimiento.getComentarios());
						hr.setRequerimiento(this.requerimiento);
						servicioGeneral.guardarObjeto(hr);
					}
				}
				FacesMessage mensaje = new FacesMessage("La información ha sido enviada correctamente");
				context.addMessage("datosGuardados", mensaje);
			} catch (Exception e) {
				System.out.print(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void enviarMensajeCambioObservacion() {
		Rol r = new Rol();
		r = servicioPersona.obtenerRol("AR");
		List<Persona> personaRol = servicioPersona.obtenerPersonasxRol(r);
		CorreoPlantilla correoActual = new CorreoPlantilla();
		correoActual = cargarPlantilla(315);
		Persona coord = new Persona();
		coord.setNombre1("Coordinador");
		coord.setApellido1("HERMES");
		coord.setEmail("coordihrms_nal@unal.edu.co");
		personaRol.add(coord);
		for (Persona adm : personaRol) {
			String cuerpoCorreo = correoActual.getCuerpo();
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<ADMINISTRADOR>>", adm.getNombreCompleto());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<SOLICITANTE>>",
					(personaActual.getGenero() == "F" ? "la ingeniera " : "el ingeniero ")
							+ personaActual.getNombreCompleto());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<TIPO>>",
					this.requerimiento.getTipo() == "Inconveniente" ? "el Inconveniente" : "la Solicitud de Mejora");
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", this.requerimiento.getId().toString());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<ESTADO>>",
					this.requerimiento.getEstSelRequerimiento().toUpperCase());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<DESCRIPCION>>",
					this.requerimiento.getDescripcionSolic().toString());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<COMENT_ING>>",
					this.requerimiento.getObservacionesIng().toString());
			Correo mensaje = new Correo();
			mensaje.setOrigen(Correo.CORREO_HERMES);
			//mensaje.adicionarCopiaOculta(Correo.CORREO_HERMES);
			mensaje.setAsunto(correoActual.getAsunto().replaceAll("<<ID>>", this.requerimiento.getId().toString())
					.replaceAll("<<TIPO>>", this.requerimiento.getTipo()));
			mensaje.setCuerpo(cuerpoCorreo);
			mensaje.adicionarDireccion(adm.getEmail());
			servicioCorreo.enviarCorreo(mensaje);
		}
	}

	public void actualizarIngeniero() {
		System.out.print(this.requerimiento.getIngenieroAsignado());
	}

	public String cancelar() {
		sesion.removeAttribute("Requerimiento");
		return "consultarRequerimiento";
	}

	public String anteriorCoord() {
		int i = 0;
		Requerimiento req = new Requerimiento();
		List requerimientoListaCompleta = new ArrayList<Requerimiento>();
		requerimientoListaCompleta = servicioGeneral.obtenerObjetos(Requerimiento.class,
				"from Requerimiento r where r.tipo='Mejora' and r.estSelRequerimiento != 'Solucionado' and r.estSelRequerimiento != 'No aplica' and r.dependenciaAsociada != 'VRIE' order by r.id");
		if (requerimientoListaCompleta != null && requerimientoListaCompleta.size() > 0) {
			for (int j = 0; j < requerimientoListaCompleta.size(); j++) {
				Requerimiento requerim = (Requerimiento) requerimientoListaCompleta.get(j);
				if (requerim.getId().equals(requerimiento.getId())) {
					i = j;
				}
			}
			if (i == 0) {
				return "";
			} else {
				req = (Requerimiento) requerimientoListaCompleta.get(i - 1);
				sesion.removeAttribute("ManejadorEditarRequerimiento");
				sesion.setAttribute("requerimientoEditable", req.getId());
				return "EditarRequerimientoCoord";
			}
		}
		return "";
	}

	public String siguienteCoord() {
		int i = 0;
		Requerimiento req = new Requerimiento();
		List requerimientoListaCompleta = new ArrayList<Requerimiento>();
		requerimientoListaCompleta = servicioGeneral.obtenerObjetos(Requerimiento.class,
				"from Requerimiento r where r.tipo='Mejora' and r.estSelRequerimiento != 'Solucionado' and r.estSelRequerimiento != 'No aplica' and r.dependenciaAsociada != 'VRIE' order by r.id");
		if (requerimientoListaCompleta != null && requerimientoListaCompleta.size() > 0) {
			for (int j = 0; j < requerimientoListaCompleta.size(); j++) {
				Requerimiento requerim = (Requerimiento) requerimientoListaCompleta.get(j);
				if (requerim.getId().equals(requerimiento.getId())) {
					i = j;
				}
			}
			if (i == requerimientoListaCompleta.size() - 1) {
				return "";
			} else {
				req = (Requerimiento) requerimientoListaCompleta.get(i + 1);
				sesion.removeAttribute("ManejadorEditarRequerimiento");
				sesion.setAttribute("requerimientoEditable", req.getId());
				return "EditarRequerimientoCoord";
			}
		}
		return "";
	}

	public String anterior() {
		int i = 0;
		Requerimiento req = new Requerimiento();
		List requerimientoListaCompleta = new ArrayList<Requerimiento>();
		requerimientoListaCompleta = servicioGeneral.obtenerObjetos(Requerimiento.class,
				"from Requerimiento r where r.ingenieroAsignado = '" + personaActual.getId().getDocumento()
						+ "' and r.tipo='Mejora' and r.dependenciaAsociada != 'VRIE' and r.estSelRequerimiento != 'Solucionado' "
						+ " and r.estSelRequerimiento != 'No aplica' order by r.id");
		if (requerimientoListaCompleta != null && requerimientoListaCompleta.size() > 0) {
			for (int j = 0; j < requerimientoListaCompleta.size(); j++) {
				Requerimiento requerim = (Requerimiento) requerimientoListaCompleta.get(j);
				if (requerim.getId().equals(requerimiento.getId())) {
					i = j;
				}
			}
			if (i == 0) {
				return "";
			} else {
				req = (Requerimiento) requerimientoListaCompleta.get(i - 1);
				sesion.removeAttribute("ManejadorEditarRequerimiento");
				sesion.setAttribute("requerimientoEditable", req.getId());
				return "editarRequerimientoAsignado";
			}
		}
		return "";
	}

	public String siguiente() {
		int i = 0;
		Requerimiento req = new Requerimiento();
		List requerimientoListaCompleta = new ArrayList<Requerimiento>();
		requerimientoListaCompleta = servicioGeneral.obtenerObjetos(Requerimiento.class,
				"from Requerimiento r where r.ingenieroAsignado = '" + personaActual.getId().getDocumento()
						+ "' and r.tipo='Mejora' and r.dependenciaAsociada != 'VRIE' and r.estSelRequerimiento != 'Solucionado' "
						+ " and r.estSelRequerimiento != 'No aplica' order by r.id");
		if (requerimientoListaCompleta != null && requerimientoListaCompleta.size() > 0) {
			for (int j = 0; j < requerimientoListaCompleta.size(); j++) {
				Requerimiento requerim = (Requerimiento) requerimientoListaCompleta.get(j);
				if (requerim.getId().equals(requerimiento.getId())) {
					i = j;
				}
			}
			if (i == requerimientoListaCompleta.size() - 1) {
				return "";
			} else {
				req = (Requerimiento) requerimientoListaCompleta.get(i + 1);
				sesion.removeAttribute("ManejadorEditarRequerimiento");
				sesion.setAttribute("requerimientoEditable", req.getId());
				return "editarRequerimientoAsignado";
			}
		}
		return "";
	}

	public String anteriorInconv() {
		int i = 0;
		Requerimiento req = new Requerimiento();
		List requerimientoListaCompleta = new ArrayList<Requerimiento>();
		requerimientoListaCompleta = servicioGeneral.obtenerObjetos(Requerimiento.class,
				"from Requerimiento r where r.ingenieroAsignado = '" + personaActual.getId().getDocumento()
						+ "' and r.tipo='Inconveniente' and r.dependenciaAsociada != 'VRIE' and r.estSelRequerimiento != 'Solucionado' order by r.id");
		if (requerimientoListaCompleta != null && requerimientoListaCompleta.size() > 0) {
			for (int j = 0; j < requerimientoListaCompleta.size(); j++) {
				Requerimiento requerim = (Requerimiento) requerimientoListaCompleta.get(j);
				if (requerim.getId().equals(requerimiento.getId())) {
					i = j;
				}
			}
			if (i == 0) {
				return "";
			} else {
				req = (Requerimiento) requerimientoListaCompleta.get(i - 1);
				sesion.removeAttribute("ManejadorEditarRequerimiento");
				sesion.setAttribute("requerimientoEditable", req.getId());
				return "editarInconvenienteAsignado";
			}
		}
		return "";
	}

	public String siguienteInconv() {
		int i = 0;
		Requerimiento req = new Requerimiento();
		List requerimientoListaCompleta = new ArrayList<Requerimiento>();
		requerimientoListaCompleta = servicioGeneral.obtenerObjetos(Requerimiento.class,
				"from Requerimiento r where r.ingenieroAsignado = '" + personaActual.getId().getDocumento()
						+ "' and r.tipo='Inconveniente' and r.dependenciaAsociada != 'VRIE' and r.estSelRequerimiento != 'Solucionado' order by r.id");
		if (requerimientoListaCompleta != null && requerimientoListaCompleta.size() > 0) {
			for (int j = 0; j < requerimientoListaCompleta.size(); j++) {
				Requerimiento requerim = (Requerimiento) requerimientoListaCompleta.get(j);
				if (requerim.getId().equals(requerimiento.getId())) {
					i = j;
				}
			}
			if (i == requerimientoListaCompleta.size() - 1) {
				return "";
			} else {
				req = (Requerimiento) requerimientoListaCompleta.get(i + 1);
				sesion.removeAttribute("ManejadorEditarRequerimiento");
				sesion.setAttribute("requerimientoEditable", req.getId());
				return "editarInconvenienteAsignado";
			}
		}
		return "";
	}

	public String anteriorInconvCoord() {
		int i = 0;
		Requerimiento req = new Requerimiento();
		List requerimientoListaCompleta = new ArrayList<Requerimiento>();
		requerimientoListaCompleta = servicioGeneral.obtenerObjetos(Requerimiento.class,
				"from Requerimiento r where r.tipo='Inconveniente' and r.estSelRequerimiento != 'Solucionado' and r.estSelRequerimiento != 'Oculto' and r.dependenciaAsociada != 'VRIE' order by r.id");
		if (requerimientoListaCompleta != null && requerimientoListaCompleta.size() > 0) {
			for (int j = 0; j < requerimientoListaCompleta.size(); j++) {
				Requerimiento requerim = (Requerimiento) requerimientoListaCompleta.get(j);
				if (requerim.getId().equals(requerimiento.getId())) {
					i = j;
				}
			}
			if (i == 0) {
				return "";
			} else {
				req = (Requerimiento) requerimientoListaCompleta.get(i - 1);
				sesion.removeAttribute("ManejadorEditarRequerimiento");
				sesion.setAttribute("requerimientoEditable", req.getId());
				return "editarInconvenienteAsignado";
			}
		}
		return "";
	}

	public String siguienteInconvCoord() {
		int i = 0;
		Requerimiento req = new Requerimiento();
		List requerimientoListaCompleta = new ArrayList<Requerimiento>();
		requerimientoListaCompleta = servicioGeneral.obtenerObjetos(Requerimiento.class,
				"from Requerimiento r where r.tipo='Inconveniente' and r.estSelRequerimiento != 'Solucionado' and r.estSelRequerimiento != 'Oculto' and r.dependenciaAsociada != 'VRIE' order by r.id");
		if (requerimientoListaCompleta != null && requerimientoListaCompleta.size() > 0) {
			for (int j = 0; j < requerimientoListaCompleta.size(); j++) {
				Requerimiento requerim = (Requerimiento) requerimientoListaCompleta.get(j);
				if (requerim.getId().equals(requerimiento.getId())) {
					i = j;
				}
			}
			if (i == requerimientoListaCompleta.size() - 1) {
				return "";
			} else {
				req = (Requerimiento) requerimientoListaCompleta.get(i + 1);
				sesion.removeAttribute("ManejadorEditarRequerimiento");
				sesion.setAttribute("requerimientoEditable", req.getId());
				return "editarInconvenienteAsignado";
			}
		}
		return "";
	}

	public String anteriorSol() {
		int i = 0;
		Requerimiento req = new Requerimiento();
		List requerimientoListaCompleta = new ArrayList<Requerimiento>();
		requerimientoListaCompleta = servicioGeneral.obtenerObjetos(Requerimiento.class,
				"from Requerimiento r where r.ingenieroAsignado = '" + personaActual.getId().getDocumento()
						+ "' and (r.estSelRequerimiento = 'Solucionado' or r.estSelRequerimiento = 'No aplica') and r.dependenciaAsociada != 'VRIE' order by r.id");
		if (requerimientoListaCompleta != null && requerimientoListaCompleta.size() > 0) {
			for (int j = 0; j < requerimientoListaCompleta.size(); j++) {
				Requerimiento requerim = (Requerimiento) requerimientoListaCompleta.get(j);
				if (requerim.getId().equals(requerimiento.getId())) {
					i = j;
				}
			}
			if (i == 0) {
				return "";
			} else {
				req = (Requerimiento) requerimientoListaCompleta.get(i - 1);
				sesion.removeAttribute("ManejadorEditarRequerimiento");
				sesion.setAttribute("requerimientoEditable", req.getId());
				return "editarRequerimientoSolucionadoAsignado";
			}
		}
		return "";
	}

	public String siguienteSol() {
		int i = 0;
		Requerimiento req = new Requerimiento();
		List requerimientoListaCompleta = new ArrayList<Requerimiento>();
		requerimientoListaCompleta = servicioGeneral.obtenerObjetos(Requerimiento.class,
				"from Requerimiento r where r.ingenieroAsignado = '" + personaActual.getId().getDocumento()
						+ "' and (r.estSelRequerimiento = 'Solucionado' or r.estSelRequerimiento = 'No aplica') and r.dependenciaAsociada != 'VRIE' order by r.id");
		if (requerimientoListaCompleta != null && requerimientoListaCompleta.size() > 0) {
			for (int j = 0; j < requerimientoListaCompleta.size(); j++) {
				Requerimiento requerim = (Requerimiento) requerimientoListaCompleta.get(j);
				if (requerim.getId().equals(requerimiento.getId())) {
					i = j;
				}
			}
			if (i == requerimientoListaCompleta.size() - 1) {
				return "";
			} else {
				req = (Requerimiento) requerimientoListaCompleta.get(i + 1);
				sesion.removeAttribute("ManejadorEditarRequerimiento");
				sesion.setAttribute("requerimientoEditable", req.getId());
				return "editarRequerimientoSolucionadoAsignado";
			}
		}
		return "";
	}

	// *******GET / SET *********
	public List<DominioDetalle> getListaEstReq() {
		return listaEstReq;
	}

	public void setListaEstReq(List<DominioDetalle> listaEstReq) {
		this.listaEstReq = listaEstReq;
	}

	public DominioDetalle getEstadosReq() {
		return estadosReq;
	}

	public void setEstadosReq(DominioDetalle estadosReq) {
		this.estadosReq = estadosReq;
	}

	public String getEstadoSelReq() {
		return estadoSelReq;
	}

	public void setEstadoSelReq(String estadoSelReq) {
		this.estadoSelReq = estadoSelReq;
	}

	public SelectItem[] getEstadosItem() {
		return estadosItem;
	}

	public void setEstadosItem(SelectItem[] estadosItem) {
		this.estadosItem = estadosItem;
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public Dependencia getDependenciaReq() {
		return dependenciaReq;
	}

	public void setDependenciaReq(Dependencia dependenciaReq) {
		this.dependenciaReq = dependenciaReq;
	}

	public List<Dependencia> getDependenciasUN() {
		return dependenciasUN;
	}

	public void setDependenciasUN(List<Dependencia> dependenciasUN) {
		this.dependenciasUN = dependenciasUN;
	}

	public List<TipoDocumento> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public SelectItem[] getDependenciaItem() {
		return dependenciaItem;
	}

	public void setDependenciaItem(SelectItem[] dependenciaItem) {
		this.dependenciaItem = dependenciaItem;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public List<DominioDetalle> getListaTipoArcReq() {
		return listaTipoArcReq;
	}

	public ArchivoRequerimiento getArchivo() {
		return archivo;
	}

	public void setArchivo(ArchivoRequerimiento archivo) {
		this.archivo = archivo;
	}

	public void setListaTipoArcReq(List<DominioDetalle> listaTipoArcReq) {
		this.listaTipoArcReq = listaTipoArcReq;
	}

	public DominioDetalle getTipoArcReq() {
		return tipoArcReq;
	}

	public void setTipoArcReq(DominioDetalle tipoArcReq) {
		this.tipoArcReq = tipoArcReq;
	}

	public String getTipoArcSelReq() {
		return tipoArcSelReq;
	}

	public void setTipoArcSelReq(String tipoArcSelReq) {
		this.tipoArcSelReq = tipoArcSelReq;
	}

	public SelectItem[] getTipoArcItem() {
		return tipoArcItem;
	}

	public void setTipoArcItem(SelectItem[] tipoArcItem) {
		this.tipoArcItem = tipoArcItem;
	}

	public UploadedFile getArchivoCargarUno() {
		return archivoCargarUno;
	}

	public void setArchivoCargarUno(UploadedFile archivoCargarUno) {
		this.archivoCargarUno = archivoCargarUno;
	}

	public ArchivoRequerimiento getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(ArchivoRequerimiento archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public List<ArchivoRequerimiento> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<ArchivoRequerimiento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public String getMensajeAdjuntaDocumentos() {
		return mensajeAdjuntaDocumentos;
	}

	public void setMensajeAdjuntaDocumentos(String mensajeAdjuntaDocumentos) {
		this.mensajeAdjuntaDocumentos = mensajeAdjuntaDocumentos;
	}

	public String getErrorValidacion() {
		return errorValidacion;
	}

	public void setErrorValidacion(String errorValidacion) {
		this.errorValidacion = errorValidacion;
	}

	public String getNombrePrueba() {
		return "prueba";
	}

	public Requerimiento getRequerimientoSeleccionado() {
		return requerimientoSeleccionado;
	}

	public void setRequerimientoSeleccionado(Requerimiento requerimientoSeleccionado) {
		this.requerimientoSeleccionado = requerimientoSeleccionado;
	}

	public List getRequerimientoLista() {
		return requerimientoLista;
	}

	public void setRequerimientoLista(List requerimientoLista) {
		this.requerimientoLista = requerimientoLista;
	}

	public long getSize() {
		return 0;
	}

	public InputStream getInputstream() throws IOException {
		return null;
	}

	public String getFileName() {
		return null;
	}

	public byte[] getContents() {
		return null;
	}

	public String getContentType() {
		return null;
	}

	public SelectItem[] getModuloItems() {
		return moduloItems;
	}

	public void setModuloItems(SelectItem[] moduloItems) {
		this.moduloItems = moduloItems;
	}

	public TipoDocumento getTipoDocAsignado() {
		return tipoDocAsignado;
	}

	public void setTipoDocAsignado(TipoDocumento tipoDocAsignado) {
		this.tipoDocAsignado = tipoDocAsignado;
	}

	public String getDocumentoAsignado() {
		return documentoAsignado;
	}

	public void setDocumentoAsignado(String documentoAsignado) {
		this.documentoAsignado = documentoAsignado;
	}

	public SelectItem[] getPrioridadItem() {
		return prioridadItem;
	}

	public void setPrioridadItem(SelectItem[] prioridadItem) {
		this.prioridadItem = prioridadItem;
	}

	public List<DominioDetalle> getListaIngenieros() {
		return listaIngenieros;
	}

	public void setListaIngenieros(List<DominioDetalle> listaIngenieros) {
		this.listaIngenieros = listaIngenieros;
	}

	public DominioDetalle getTipoListIng() {
		return tipoListIng;
	}

	public void setTipoListIng(DominioDetalle tipoListIng) {
		this.tipoListIng = tipoListIng;
	}

	public String getTipoListIngSel() {
		return tipoListIngSel;
	}

	public void setTipoListIngSel(String tipoListIngSel) {
		this.tipoListIngSel = tipoListIngSel;
	}

	public SelectItem[] getTipoIngItem() {
		return tipoIngItem;
	}

	public void setTipoIngItem(SelectItem[] tipoIngItem) {
		this.tipoIngItem = tipoIngItem;
	}

	public UploadedFile getArchivoLeg() {
		return archivoLeg;
	}

	public void setArchivoLeg(UploadedFile archivoLeg) {
		this.archivoLeg = archivoLeg;
	}

	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public List getListaTipoArchivo() {
		return listaTipoArchivo;
	}

	public void setListaTipoArchivo(List listaTipoArchivo) {
		this.listaTipoArchivo = listaTipoArchivo;
	}

	public SelectItem[] getTipoArchivoItem() {
		return tipoArchivoItem;
	}

	public void setTipoArchivoItem(SelectItem[] tipoArchivoItem) {
		this.tipoArchivoItem = tipoArchivoItem;
	}

	public DataTable getTablaArchivosLeg() {
		return tablaArchivosLeg;
	}

	public void setTablaArchivosLeg(DataTable tablaArchivosLeg) {
		this.tablaArchivosLeg = tablaArchivosLeg;
	}

	public ArchivoRequerimiento getArchivoSeleccionadoLeg() {
		return archivoSeleccionadoLeg;
	}

	public void setArchivoSeleccionadoLeg(ArchivoRequerimiento archivoSeleccionadoLeg) {
		this.archivoSeleccionadoLeg = archivoSeleccionadoLeg;
	}

	public SelectItem[] getSubModuloItems() {
		return subModuloItems;
	}

	public void setSubModuloItems(SelectItem[] subModuloItems) {
		this.subModuloItems = subModuloItems;
	}

	public String getMensajeUsuarioOld() {
		return mensajeUsuarioOld;
	}

	public void setMensajeUsuarioOld(String mensajeUsuarioOld) {
		this.mensajeUsuarioOld = mensajeUsuarioOld;
	}
}
