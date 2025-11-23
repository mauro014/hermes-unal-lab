package co.edu.unal.hermes.vista.laboratorios;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.PersonaLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.SolicitudLaboratorios;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * @author dgbenitezc
 * 
 */
public class ManejadorSolicitudSNL extends ManejadorBase {

	private SelectItem[] selectItemTipoSolicitud;
	private SelectItem[] siDependenciaDestino;
	private SelectItem[] siTipoDocumento;

	private IdPersona idPersona;
	private Persona personaSolicitud;

	private Boolean soloLectura = false;

	private SolicitudLaboratorios nuevaSolicitud;
	private Boolean personaEncontrada;

	private List<SolicitudLaboratorios> listaSolicitudes;
	private SolicitudLaboratorios solicitudSeleccionada;
	private List<Dependencia> listaSNL;

	private Boolean guardando;
	private String extensionPersonaInterna;
	private Boolean editarExtension;
	private SelectItem[] siLabs;

	public ManejadorSolicitudSNL() {
		System.out.println("CONSTRUCTOR ManejadorSolicitudSNL:");
		constructor();
	}

	public void constructor() {
		System.out.println("ManejadorSolicitudSNL");

		selectItemTipoSolicitud = servicioGeneral
				.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_SOLICITUD_SNL);
		selectItemTipoSolicitud = siConSeleccione();
		siDependenciaDestino = siDependenciasLaboratorio();
		idPersona = new IdPersona();
		personaSolicitud = new Persona();
		nuevaSolicitud = new SolicitudLaboratorios();
		nuevaSolicitud.setDirigidaA(new Dependencia());

		guardando = false;

		// siTipoDocumento
		List<TipoDocumento> listaTipoDocumento = servicioGeneral
				.obtenerListaObjetos(TipoDocumento.class);
		siTipoDocumento = new SelectItem[listaTipoDocumento.size()];
		int i = 0;
		for (TipoDocumento tD : listaTipoDocumento) {
			siTipoDocumento[i] = new SelectItem(tD.getId(), tD.getNombre());
			i++;
		}

		listadoSolicitudes();

		extensionPersonaInterna = "";
		editarExtension = false;

	}

	public void listadoSolicitudes() {
		System.out.println("listadoSolicitudes");

		personaActual = (Persona) sesion.getAttribute("persona");
		System.out.println("personaActual:" + personaActual);

		if (personaActual != null) {
			Boolean esLaboratoriosNacional = (Boolean) sesion
					.getAttribute("esLaboratorios");
			Boolean esLaboratoriosSede = (Boolean) sesion
					.getAttribute("esLaboratoriosSede");
			Sede sedePersona = (Sede) sesion.getAttribute("sedeLabsSede");

			System.out.println("esLaboratoriosNacional:"
					+ esLaboratoriosNacional);
			System.out.println("esLaboratoriosSede:" + esLaboratoriosSede);
			System.out.println("sedePersona:" + sedePersona);

			String idDependencia = "";
			if (esLaboratoriosNacional) {
				idDependencia = Dependencia.ID_DNIL;
			} else if (esLaboratoriosSede) {
				for (Dependencia d : listaSNL) {
					System.out.println("iterando listaSNL:" + d.getNombre());
					if (d.getSede().equals(sedePersona)) {
						System.out.println("sedepersona:" + d.getNombre());
						idDependencia = d.getId();
						break;
					}
				}

			}
			System.out.println("idDependencia:" + idDependencia);

			// consultar solicitudes:
			String hql = "FROM SolicitudLaboratorios WHERE laboratorio is NULL AND dirigidaA = '"
					+ idDependencia + "' ORDER BY estadoSolicitud, id DESC";
			System.out.println("hql:" + hql);
			listaSolicitudes = servicioGeneral.obtenerObjetos(
					SolicitudLaboratorios.class, hql);
			System.out.println("listaSolicitudes:" + listaSolicitudes.size());
		} else {
			System.out.println("sesión NULA.");
		}
	}

	public String consultarSolicitud() {
		System.out.println("consultarSolicitud:");
		soloLectura = true;
		return "consultarEditarSolicitudSNL";
	}

	public String editarSolicitud() {
		System.out.println("editarSolicitud:");
		soloLectura = false;
		return "consultarEditarSolicitudSNL";
	}

	public Boolean getMostrarLaboratorios() {
		Boolean mostrarLaboratorios = false;
		if (siLabs != null && siLabs.length > 0) {
			mostrarLaboratorios = true;
		} else {
			mostrarLaboratorios = false;
		}
		System.out.println("mostrarLaboratorios:" + mostrarLaboratorios);
		return mostrarLaboratorios;
	}

	public Boolean getSolicitanteInterno() {
		System.out.println("isSolicitanteInterno:");
		Boolean solicitanteEsInterno = false;
		if (solicitudSeleccionada.getPersona() instanceof InvestigadorInterno) {
			solicitanteEsInterno = true;
		}
		System.out.println("isSolicitanteInterno:" + solicitanteEsInterno);
		return solicitanteEsInterno;
	}

	public String volverListado() {
		System.out.println("volverListado:");
		soloLectura = false;
		limpiarSesion();
		return "listadoSolicitudesSNL";
	}

	public void limpiarFormulario() {
		System.out.println("limpiarFormulario:");
		limpiarSesion();
	}

	public void limpiarSesion() {
		System.out.println("limpiarSesion:");
		sesion.removeAttribute("manejadorSolicitudSNL");
	}

	public String responderSolicitud() {
		System.out.println("responderSolicitud:");

		solicitudSeleccionada.setObservaciones(solicitudSeleccionada
				.getObservaciones().trim());

		if (solicitudSeleccionada.getObservaciones().isEmpty()) {
			mensajeError("form:itObservaciones",
					"Debe diligenciar el campo observaciones.");
			return "";
		}

		// guardar y enviar correo
		EstadoProyecto ep = new EstadoProyecto();
		ep.setId(EstadoProyecto.FINALIZADO);
		solicitudSeleccionada.setEstadoSolicitud(ep);
		Date fechaRespuesta = new Date();
		solicitudSeleccionada.setFechaRespuesta(fechaRespuesta);
		servicioGeneral.guardarObjeto(solicitudSeleccionada);
		mensajeInfo("responderSolicitud");

		String emailDependencia = solicitudSeleccionada.getDirigidaA()
				.getEmail();

		CorreoPlantilla correoPlantilla = cargarPlantilla(CorreoPlantilla.RESPUESTA_SOLICITUD_SNL);
		String cuerpo = correoPlantilla.getCuerpo();
		String asunto = correoPlantilla.getAsunto();
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		correo.adicionarDireccion(solicitudSeleccionada.getPersona().getEmail());

		if (emailDependencia != null && !emailDependencia.isEmpty()) {
			correo.adicionarCopiaOculta(emailDependencia);
		}
		correo.adicionarCopiaOculta(personaActual.getEmail());
		//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);

		asunto = asunto.replaceAll("<<ID_SOLICITUD>>", solicitudSeleccionada
				.getId().toString());
		correo.setAsunto(asunto);

		String cuerpoCorreo = cuerpo.replaceAll("<<ID_SOLICITUD>>",
				solicitudSeleccionada.getId().toString());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<SOLICITANTE>>",
				solicitudSeleccionada.getPersona().getNombreCompleto());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<EMAIL>>",
				solicitudSeleccionada.getPersona().getEmail());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<TELEFONO>>",
				solicitudSeleccionada.getPersona().getTelefono());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<TIPO_SOLICITUD>>",
				solicitudSeleccionada.getTipoSolicitud().getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<DIRIGIDA_A>>",
				solicitudSeleccionada.getDirigidaA().getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<JUSTIFICACION>>",
				solicitudSeleccionada.getJustificacion());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<OBSERVACIONES>>",
				solicitudSeleccionada.getObservaciones());

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy/MM/dd, HH:mm:ss");
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<FECHA_SOLICITUD>>",
				formatter.format(solicitudSeleccionada.getFecha()));
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<FECHA_RESPUESTA>>",
				formatter.format(solicitudSeleccionada.getFechaRespuesta()));

		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);
		System.out.println("correo:" + correo.getAsunto());
		System.out.println("correo:" + correo.getCuerpo());

		limpiarSesion();
		return "listadoSolicitudesSNL";
	}

	public void guardarSolicitud() {
		System.out.println(this.getClass().getName() + " guardarSolicitud:");

		guardando = true;
		buscarPersona();
		if (validarSolicitud()) {
			System.out.println("guardarSolicitud: validarSolicitud");
			nuevaSolicitud.setLaboratorio(null);
			if (!getMostrarLaboratorios()) {
				nuevaSolicitud.setLaboratorioSolicitante(null);
			}

			if (!personaEncontrada) {
				System.out.println("guardarSolicitud: creando nueva persona:");
				personaSolicitud.setDireccion("");
				personaSolicitud.setGenero("");
				servicioPersona
						.insertarNuevaPersonaDatosBasicos(personaSolicitud);
				// mensajeError("form:itDocumento", null);
			}

			if (editarExtension) {
				System.out.println("editarExtension: actualizando persona in:");
				((InvestigadorInterno) personaSolicitud)
						.setTelExtension(extensionPersonaInterna);
				servicioGeneral.guardarObjeto(personaSolicitud);
				System.out
						.println("editarExtension: actualizando persona out.");
			}

			nuevaSolicitud.setPersona(personaSolicitud);
			EstadoProyecto ep = new EstadoProyecto();
			ep.setId(EstadoProyecto.ACTIVO);
			nuevaSolicitud.setEstadoSolicitud(ep);
			nuevaSolicitud.setFecha(new Date());
			servicioGeneral.guardarObjeto(nuevaSolicitud);
			mensajeInfo("Su solicitud ha sido registrada con el Id "
					+ nuevaSolicitud.getId() + ", se ha enviado un correo a "
					+ personaSolicitud.getEmail());

			nuevaSolicitud = (SolicitudLaboratorios) servicioGeneral
					.obtenerObjeto(new SolicitudLaboratorios(),
							nuevaSolicitud.getId());

			Dependencia dependencia = (Dependencia) servicioGeneral
					.obtenerObjeto(new Dependencia(), nuevaSolicitud
							.getDirigidaA().getId());
			String emailDependencia = dependencia.getEmail();

			// ENVÍAR CORREO:
			CorreoPlantilla correoPlantilla = cargarPlantilla(CorreoPlantilla.REGISTRO_SOLICITUD_SNL);
			String cuerpo = correoPlantilla.getCuerpo();
			String asunto = correoPlantilla.getAsunto();
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			correo.adicionarDireccion(personaSolicitud.getEmail());
			if (emailDependencia != null && !emailDependencia.isEmpty()) {
				correo.adicionarCopiaOculta(emailDependencia);
			}
			//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);

			asunto = asunto.replaceAll("<<ID_SOLICITUD>>", nuevaSolicitud
					.getId().toString());
			correo.setAsunto(asunto);

			String cuerpoCorreo = cuerpo.replaceAll("<<ID_SOLICITUD>>",
					nuevaSolicitud.getId().toString());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<SOLICITANTE>>",
					personaSolicitud.getNombreCompleto());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<EMAIL>>",
					personaSolicitud.getEmail());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<TELEFONO>>",
					personaSolicitud.getTelefono());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<TIPO_SOLICITUD>>",
					nuevaSolicitud.getTipoSolicitud().getNombre());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<DIRIGIDA_A>>",
					nuevaSolicitud.getDirigidaA().getNombre());
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<JUSTIFICACION>>",
					nuevaSolicitud.getJustificacion());

			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy/MM/dd, HH:mm:ss");
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<FECHA_SOLICITUD>>",
					formatter.format(nuevaSolicitud.getFecha()));
			correo.setCuerpo(cuerpoCorreo);
			servicioCorreo.enviarCorreo(correo);
			System.out.println("correo:" + correo.getAsunto());
			System.out.println("correo:" + correo.getCuerpo());

			// Reiniciar todo:
			// constructor();
			limpiarSesion();

		} else {
			System.out.println("guardarSolicitud: validarSolicitud FALSE");
			guardando = false;
		}
	}

	public Boolean validarSolicitud() {
		Boolean validar = true;
		System.out.println("validarSolicitud():");

		if (idPersona.getDocumento().isEmpty()) {
			validar = false;
		}

		System.out.println("personaSolicitud.getNombre1():"
				+ personaSolicitud.getNombre1());

		if (personaSolicitud.getNombre1().isEmpty()) {
			mensajeError("form:itNombre1", "Primer nombre: Campo obligatorio.");
			validar = false;
		}

		if (personaSolicitud.getApellido1().isEmpty()) {
			mensajeError("form:itApellido1",
					"Primer apellido: Campo obligatorio.");
			validar = false;
		}

		if (!isValidEmail(personaSolicitud.getEmail())) {
			mensajeError("form:itEMail",
					"e-mail: Campo obligatorio, debe contener un correo electrónico valido.");
			validar = false;
		}

		if (!personaEncontrada && personaSolicitud.getTelefono().length() < 7) {
			mensajeError("form:itTelefono",
					"Teléfono: Campo obligatorio, longitud mínima siete caracteres.");
			validar = false;
		}

		if (nuevaSolicitud.getCargoSolicitante().isEmpty()) {
			mensajeError("form:itCargo", "Empresa/Cargo: Campo obligatorio.");
			validar = false;
		}

		if (nuevaSolicitud.getTipoSolicitud() == null) {
			mensajeError("form:siTipo",
					"Tipo: Seleccione el tipo de solicitud a realizar.");
			validar = false;
		}

		if (nuevaSolicitud.getDirigidaA().getId().isEmpty()) {
			mensajeError("form:siDestino",
					"Dirigida a: Seleccione una dependencia.");
			validar = false;
		}

		if (editarExtension) {
			Boolean errorExt = false;
			int longitudExt = extensionPersonaInterna.length();

			try {
				new Long(extensionPersonaInterna);
			} catch (Exception e) {
				System.out.println("error conviertiendo a número:"
						+ extensionPersonaInterna);
				errorExt = true;
			}

			if (longitudExt < 4 || errorExt) {
				mensajeError("form:extension",
						"Extensión: Campo obligatorio, debe tener cuatro o cinco dígitos.");
				validar = false;
			}
		}

		nuevaSolicitud.setJustificacion(nuevaSolicitud.getJustificacion()
				.trim());
		if (nuevaSolicitud.getJustificacion().isEmpty()) {
			mensajeError("form:itJust", "Justificación: Campo obligatorio.");
			validar = false;
		}

		return validar;
	}

	public static boolean isValidEmail(String enteredEmail) {
		System.out.println("isValidEmail:" + enteredEmail);
		String EMAIL_REGIX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(EMAIL_REGIX);
		Matcher matcher = pattern.matcher(enteredEmail);
		return ((!enteredEmail.isEmpty()) && (enteredEmail != null) && (matcher
				.matches()));
	}

	public void buscarPersona() {
		System.out.println(this.getClass().getName() + " buscarPersona");
		System.out.println("guardando:" + guardando);

		Boolean buscarPersona = true;
		String documento = idPersona.getDocumento();
		System.out.println("documento in:" + documento);
		documento = documento.trim();
		idPersona.setDocumento(documento);

		if (documento.contains(" ") || documento.contains(",")
				|| documento.contains(".")) {
			mensajeError(
					"form:itDocumento",
					"Documento: El campo no debe contener caracteres especiales (espacios, puntos, comas, etc.).");
			buscarPersona = false;
		}

		if (documento.isEmpty() || documento.length() < 8) {
			mensajeError(
					"form:itDocumento",
					"Documento: Campo obligatorio, longitud mínima ocho caracteres. No debe contener caracteres especiales (espacios, puntos, comas, etc.).");
			buscarPersona = false;
		}

		if (buscarPersona) {
			System.out.println("buscarPersona:");
			Persona persona = servicioPersona.obtenerPersona(idPersona);
			if (persona != null) {
				personaSolicitud = persona;

				if (personaSolicitud instanceof InvestigadorInterno) {
					String extension = ((InvestigadorInterno) personaSolicitud)
							.getTelExtension();
					System.out.println("extension:" + extension);
					if (extension != null) {
						extensionPersonaInterna = extension;
						editarExtension = false;
					} else {
						editarExtension = true;
					}
				}

				// buscar laboratorios a los que está asociada la persona
				String hql = "FROM PersonaLaboratorio pl WHERE pl.persona.id.documento = '"
						+ personaSolicitud.getId().getDocumento()
						+ "' ORDER BY pl.laboratorio.nombre";
				System.out.println("hql:" + hql);
				List<PersonaLaboratorio> listaLabs = servicioGeneral
						.obtenerObjetos(PersonaLaboratorio.class, hql);
				int numeroLabs = listaLabs.size();
				System.out.println("numeroLabs:" + numeroLabs);
				siLabs = new SelectItem[numeroLabs];
				if (numeroLabs > 0) {
					for (int i = 0; i < numeroLabs; i++) {
						PersonaLaboratorio pl = (PersonaLaboratorio) listaLabs
								.get(i);
						siLabs[i] = new SelectItem(pl.getLaboratorio().getId(),
								pl.getLaboratorio().getNombre());
					}
				}

				personaEncontrada = true;
				if (!guardando) {
					String mensajeInfo = "La persona con ese tipo/documento ha sido encontrada en nuestra base de datos.  Si la información no es correcta, debe comunicarse con nosotros para actualizarla.";
					if (editarExtension) {
						mensajeInfo += " Debe ingresar el número de extensión.";
					}
					if (getMostrarLaboratorios()) {
						mensajeInfo += " Por favor seleccione el Laboratorio para el que realiza la solicitud.";
					}
					// mensajeInfo(mensajeInfo);
					mensajeInfo("form:itDocumento", mensajeInfo);
				}
			} else {
				System.out.println("buscarPersona: persona NO encontrada");
				// personaSolicitud = new Persona();
				personaSolicitud.setId(idPersona);

				if (!guardando) {
					mensajeError(
							"form:itDocumento",
							"La persona con ese tipo/documento no existe en nuestra base de datos. Si está seguro de la información de identificación, debe diligenciar los campos correspondientes a nombre(s), apellido(s), e-mail y teléfono.");
				}
				personaEncontrada = false;
			}
		} else {
			personaEncontrada = false;
		}

	}

	public Boolean getPersonaEncontradaInterna() {
		System.out.println("getPersonaEncontradaInterna:");
		Boolean personaEncontradaEsInterna = false;
		if (personaEncontrada != null && personaEncontrada) {
			if (personaSolicitud instanceof InvestigadorInterno) {
				personaEncontradaEsInterna = true;
			}
		} else {
			personaEncontradaEsInterna = false;
		}
		System.out.println("personaEncontradaEsInterna:"
				+ personaEncontradaEsInterna);
		return personaEncontradaEsInterna;
	}

	public SelectItem[] siConSeleccione() {
		SelectItem[] si = servicioGeneral
				.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_SOLICITUD_SNL);
		int cantidad = si.length;
		SelectItem[] itemHijos = new SelectItem[cantidad + 1];
		itemHijos[0] = new SelectItem(null, "Seleccione...");
		for (int i = 0; i < cantidad; i++) {
			itemHijos[i + 1] = si[i];
		}
		return itemHijos;
	}

	public SelectItem[] siDependenciasLaboratorio() {
		// String hql = "FROM Dependencia WHERE UPPER(nombre) LIKE '%LABORATORIOS%' AND estado = 'A' ORDER BY id";
		String hql = "FROM Dependencia WHERE id='"+ Dependencia.ID_DNIL+"' AND estado = 'A' ORDER BY id"; // solo DNIL
		listaSNL = servicioGeneral.obtenerObjetos(Dependencia.class, hql);
		int cantidad = listaSNL.size();
		SelectItem[] si = new SelectItem[cantidad + 1];
		si[0] = new SelectItem(null, "Seleccione...");
		for (int i = 0; i < cantidad; i++) {
			Dependencia dep = (Dependencia) listaSNL.get(i);
			si[i + 1] = new SelectItem(dep.getId(), dep.getNombre());
		}
		return si;
	}

	/**
	 * @return the selectItemTipoSolicitud
	 */
	public SelectItem[] getSelectItemTipoSolicitud() {
		return selectItemTipoSolicitud;
	}

	/**
	 * @return the siDependenciaDestino
	 */
	public SelectItem[] getSiDependenciaDestino() {
		return siDependenciaDestino;
	}

	/**
	 * @return the siTipoDocumento
	 */
	public SelectItem[] getSiTipoDocumento() {
		return siTipoDocumento;
	}

	/**
	 * @return the idPersona
	 */
	public IdPersona getIdPersona() {
		return idPersona;
	}

	/**
	 * @return the personaSolicitud
	 */
	public Persona getPersonaSolicitud() {
		return personaSolicitud;
	}

	/**
	 * @return the soloLectura
	 */
	public Boolean getSoloLectura() {
		return soloLectura;
	}

	/**
	 * @return the nuevaSolicitud
	 */
	public SolicitudLaboratorios getNuevaSolicitud() {
		return nuevaSolicitud;
	}

	/**
	 * @return the personaEncontrada
	 */
	public Boolean getPersonaEncontrada() {
		return personaEncontrada;
	}

	/**
	 * @return the listaSolicitudes
	 */
	public List<SolicitudLaboratorios> getListaSolicitudes() {
		return listaSolicitudes;
	}

	/**
	 * @return the solicitudSeleccionada
	 */
	public SolicitudLaboratorios getSolicitudSeleccionada() {
		return solicitudSeleccionada;
	}

	/**
	 * @param solicitudSeleccionada
	 *            the solicitudSeleccionada to set
	 */
	public void setSolicitudSeleccionada(
			SolicitudLaboratorios solicitudSeleccionada) {
		this.solicitudSeleccionada = solicitudSeleccionada;
	}

	/**
	 * @return the editarExtension
	 */
	public Boolean getEditarExtension() {
		return editarExtension;
	}

	/**
	 * @return the extensionPersonaInterna
	 */
	public String getExtensionPersonaInterna() {
		return extensionPersonaInterna;
	}

	/**
	 * @param extensionPersonaInterna
	 *            the extensionPersonaInterna to set
	 */
	public void setExtensionPersonaInterna(String extensionPersonaInterna) {
		this.extensionPersonaInterna = extensionPersonaInterna;
	}

	/**
	 * @return the siLabs
	 */
	public SelectItem[] getSiLabs() {
		return siLabs;
	}

}
