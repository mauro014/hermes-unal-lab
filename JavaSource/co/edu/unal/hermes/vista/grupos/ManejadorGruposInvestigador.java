/**
 * Modificado por Mauricio
 */

package co.edu.unal.hermes.vista.grupos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.EstadoGrupo;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoFormularioGrupo;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.SolicitudGrupo;
import co.edu.unal.hermes.modelo.TipoSolicitudGrupo;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.grupos.ediciongrupos.ManejadorEdicionGrupo;

/**
 * The Class ManejadorGruposInvestigador.
 */
public class ManejadorGruposInvestigador extends ManejadorBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3995286768472964947L;

	/** The lista grupos. */
	private List<GrupoVista> listaGrupos;

	/** The investigador interno. */
	private InvestigadorInterno investigadorInterno;
	private Investigador investigador;

	/** The grupo seleccionado. */
	private GrupoVista grupoSeleccionado;
	
	private String justificacionCambioEstado;
	private String estadoSolicitado;
	private SolicitudGrupo solicitudGrupo;
	/** The historicos formulario grupo. */
	private List<HistoricoFormularioGrupo> historicosFormularioGrupo;

	/**
	 * Instantiates a new manejador grupos investigador.
	 */
	public ManejadorGruposInvestigador() {
		super();
		listaGrupos = new ArrayList<GrupoVista>();
		cargadoDatosInicial();
	}

	/**
	 * Agregar nuevo grupo.
	 *
	 * @return the string
	 */
	public String agregarNuevoGrupo() {
		sesion.setAttribute("investigadorInterno", this.investigadorInterno);
		eliminarManejadores();
		return ManejadorEdicionGrupo.REGLA_NAVEGACION_EDICION_GRUPO;
	}

	/**
	 * Cargado datos inicial.
	 */
	private void cargadoDatosInicial() {
		personaActual = (Persona) sesion.getAttribute("persona");
		investigadorInterno = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());
		investigador = (Investigador) servicioPersona.obtenerInvestigador(personaActual.getId());
		
		List<InvestigadorGrupo> listGruposInvestigador = servicioPersona.obtenerGruposInvestigador(!esNulo(investigadorInterno) ? investigadorInterno : investigador);

		if (!esListaVacia(listGruposInvestigador)) {
			Iterator<InvestigadorGrupo> i = listGruposInvestigador.iterator();
			while (i.hasNext()) {

				InvestigadorGrupo investigadorGrupo = i.next();
				Grupo grupo = investigadorGrupo.getGrupo();

				listaGrupos.add(cargarInformacionGrupoVista(grupo, investigadorGrupo));

			}
		}
	}

	/**
	 * Cargar informacion grupo vista.
	 *
	 * @param grupo
	 *            the grupo
	 * @param investigadorGrupo
	 *            the investigador grupo
	 * @return the grupo vista
	 */
	private GrupoVista cargarInformacionGrupoVista(Grupo grupo, InvestigadorGrupo investigadorGrupo) {
		GrupoVista grupoVista = new GrupoVista();
		if (grupo.getEstadoGrupoColciencias() != null) {
			grupoVista.setEstadoColciencias(grupo.getEstadoGrupoColciencias().getNombre());
		}

//		Boolean esInvInterno = !esNulo(servicioPersona.obtenerInvestigadorInterno(investigadorGrupo.getInvestigador().getId()));
		// Se valida si el investigador actual es el principal
		grupoVista.setInvGrupoPrincipal(!esNulo(investigadorInterno) ? validarPrincipal(investigadorGrupo) : false);
		grupoVista.setResponsable(grupo.getResponsable());
		grupoVista.setInvEstudianteLider(isEstudianteLider(investigadorGrupo));
		grupoVista.setInvAsistenteLider(isAsistenteLider(investigadorGrupo));
		grupoVista.setSede(grupo.getSede());
		grupoVista.setDependencia(grupo.getDependencia());

		if (grupo.getEstadoGrupo().getId().equalsIgnoreCase(EstadoGrupo.REGISTRADO)) {
			grupoVista.setEsPropuesto(true);
		} else {
			grupoVista.setEsPropuesto(false);
		}

		if (grupo.getEstadoGrupo().getId().equalsIgnoreCase(EstadoGrupo.SOLICITUD_AVAL)) {
			grupoVista.setSolicitudAval(true);
		} else {
			grupoVista.setSolicitudAval(false);
		}

		if (grupo.getEstadoGrupo().getId().equalsIgnoreCase(EstadoGrupo.INACTIVO)) {
			grupoVista.setSolicitudAval(false);
			grupoVista.setInactivo(true);
		}
		
		if (grupo.getEstadoGrupo().getId().equalsIgnoreCase(EstadoGrupo.ACTIVO)) {
			grupoVista.setSolicitudAval(false);
			grupoVista.setActivo(true);
		}

		if (grupo.getEstadoGrupo().getId().equalsIgnoreCase(EstadoGrupo.INGRESANDO)) {
			grupoVista.setSolicitudAval(false);
			grupoVista.setIngresando(true);
		}

		if (grupo.getEstadoGrupo().getId().equalsIgnoreCase(EstadoGrupo.CORRECCIONES)) {
			grupoVista.setCorrecciones(true);
			grupoVista.setEsDevuelto(true);
		} else {
			grupoVista.setCorrecciones(false);
		}
		grupoVista.setId(grupo.getId());
		grupoVista.setNombreGrupo(grupo.getNombre());
		grupoVista.setInterfacultades(grupo.getInterfacultades());
		grupoVista.setIntersedes(grupo.getIntersedes());
		grupoVista.setInterinstitucion(grupo.getInterinstitucion());
		grupoVista.setArchivoAval(grupo.getArchivoAval());
		return grupoVista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.vista.ManejadorBase#cargarPlantilla(int)
	 */
	@Override
	public CorreoPlantilla cargarPlantilla(int codId) {
		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		List<CorreoPlantilla> lista = servicioGeneral.obtenerListaObjetosWhere(CorreoPlantilla.class,
				"where c.id='" + codId + "'");
		if (!esListaVacia(lista)) {
			correoActualAux = lista.get(0);
		}
		return correoActualAux;
	}

	/**
	 * Consultar grupo.
	 *
	 * @return the string
	 */
	public String consultarGrupo() {
		GrupoVista grupoVistaActual = grupoSeleccionado;
		Grupo grupoActual = servicioGrupo.obtenerGrupo(grupoVistaActual.getId());
		eliminarManejadores();
		sesion.setAttribute("grupo", grupoActual);
		sesion.setAttribute("esConsulta", true);
		return ManejadorEdicionGrupo.REGLA_NAVEGACION_EDICION_GRUPO;
	}

	/**
	 * Editar grupo.
	 *
	 * @return the string
	 */
	public String editarGrupo() {
		GrupoVista grupoVistaActual = grupoSeleccionado;
		Grupo grupoActual = servicioGrupo.obtenerGrupo(grupoVistaActual.getId());
		eliminarManejadores();
		sesion.setAttribute(Grupo.VARIABLE_SESION_GRUPO, grupoActual);
		sesion.removeAttribute("esConsulta");
		return ManejadorEdicionGrupo.REGLA_NAVEGACION_EDICION_GRUPO;
	}

	/**
	 * Consultar grupo.
	 *
	 * @return the string
	 */
	public String eliminarGrupo() {
		GrupoVista grupoVistaActual = grupoSeleccionado;
		Grupo grupoActual = servicioGrupo.obtenerGrupo(grupoVistaActual.getId());
		eliminarManejadores();
		EstadoGrupo estadoGrupo = new EstadoGrupo();
		estadoGrupo.setId("D");
		grupoActual.setEstadoGrupo(estadoGrupo);
		servicioGeneral.guardarObjeto(grupoActual);
		cambiosGruposHistorico = "eg,";
		justificacionGrupoHistorico = "Eliminación realizada por líder de grupo.";
		guardarHistoricoEstadoGrupo(grupoActual, getPersonaActual());
		cargadoDatosInicial();
		return "";
	}

	/**
	 * Eliminar manejadores.
	 */
	public void eliminarManejadores() {
		sesion.removeAttribute("manejadorGruposInvestigador");
		sesion.removeAttribute("manejadorEdicionGrupo");
		sesion.removeAttribute("manejadorIntegrantesGrupo");
		sesion.removeAttribute("manejadorLineasGrupo");
		sesion.removeAttribute("manejadorEdicionGrupoVisionPrioridadesPerspectiva");
		sesion.removeAttribute("manejadorMenuFormularioGrupos");
		sesion.removeAttribute("grupo");
	}

	/**
	 * Gets the grupo seleccionado.
	 *
	 * @return the grupo seleccionado
	 */
	public GrupoVista getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	/**
	 * Gets the lista grupos.
	 *
	 * @return the lista grupos
	 */
	public List<GrupoVista> getListaGrupos() {
		return listaGrupos;
	}

	/**
	 * Reporte.
	 */
	public void reporte() {
		imprimirReporteGrupo(grupoSeleccionado.getId().toString());
	}

	/**
	 * Sets the grupo seleccionado.
	 *
	 * @param grupoSeleccionado
	 *            the new grupo seleccionado
	 */
	public void setGrupoSeleccionado(GrupoVista grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}

	/**
	 * Validar principal.
	 *
	 * @param investigadorGrupo
	 *            the investigador grupo
	 * @return true, if successful
	 */
	private boolean validarPrincipal(InvestigadorGrupo investigadorGrupo) {
		return investigadorInterno.getId().getDocumento()
				.equals(investigadorGrupo.getInvestigador().getId().getDocumento())
				&& investigadorInterno.getId().getTipoDocumento()
						.equals(investigadorGrupo.getInvestigador().getId().getTipoDocumento())
				&& (investigadorGrupo.getTipo().equals(InvestigadorGrupo.LIDER));

	}

	private boolean isEstudianteLider(InvestigadorGrupo investigadorGrupo) {
		return personaActual.getId().getDocumento()
				.equals(investigadorGrupo.getInvestigador().getId().getDocumento())
				&& personaActual.getId().getTipoDocumento()
						.equals(investigadorGrupo.getInvestigador().getId().getTipoDocumento())
				&& (investigadorGrupo.getTipo().equals(InvestigadorGrupo.ESTUDIANTE_LIDER));

	}
	
	private boolean isAsistenteLider(InvestigadorGrupo investigadorGrupo) {
		return personaActual.getId().getDocumento()
				.equals(investigadorGrupo.getInvestigador().getId().getDocumento())
				&& personaActual.getId().getTipoDocumento()
						.equals(investigadorGrupo.getInvestigador().getId().getTipoDocumento())
				&& (investigadorGrupo.getTipo().equals(InvestigadorGrupo.ASISTENTE_LIDER));

	}

	public String getJustificacionCambioEstado() {
		return justificacionCambioEstado;
	}

	public void setJustificacionCambioEstado(String justificacionCambioEstado) {
		this.justificacionCambioEstado = justificacionCambioEstado;
	}
	
	public String crearNuevaSolicitudCambioEstado() {
		solicitudGrupo = new SolicitudGrupo();
		TipoSolicitudGrupo ts = new TipoSolicitudGrupo();
		ts.setId(TipoSolicitudGrupo.CAMBIO_ESTADO);
		solicitudGrupo.setTipoSolicitud(ts);
		solicitudGrupo.setRespuesta(SolicitudGrupo.TRAMITE);
		Grupo grupo = new Grupo();
		grupo.setId(grupoSeleccionado.getId());
		solicitudGrupo.setGrupo(grupo);
		solicitudGrupo.setFecha(getToday());
		solicitudGrupo.setResponsable(personaActual);
		solicitudGrupo.setDependenciaRevision(investigadorInterno.getDependencia());
		justificacionCambioEstado="";
		return "";
	}
	
	public String guardarSolicitudCambioEstado() {
		if(esCadenaVacia(estadoSolicitado) || esCadenaVacia(justificacionCambioEstado)) {
			mensajeError("Debe seleccionar un estado e ingresar la justificación para enviar la solicitud");
			return "";
		}
		solicitudGrupo.setDescripcion(justificacionCambioEstado);
		EstadoGrupo eg = new EstadoGrupo();
		eg.setId(estadoSolicitado);
		solicitudGrupo.setEstadoSolicitado(eg);

		try {
			servicioGeneral.guardarObjeto(solicitudGrupo);
		}catch (Exception e){
			e.printStackTrace();
			mensajeError("Ocurrió un error al guardar y enviar la solicitud.");
			return "";
		}
		enviarNotificacion(398); // Plantilla cambio de estado grupo
		mensajeInfo("Solicitud enviada. Código Asignado: "+solicitudGrupo.getId());
		return "";
		
	}

	public SolicitudGrupo getSolicitudGrupo() {
		return solicitudGrupo;
	}

	public void setSolicitudGrupo(SolicitudGrupo solicitudGrupo) {
		this.solicitudGrupo = solicitudGrupo;
	}
	
	/**
	 * Descargar el documento del aval
	 */
	public void descargarAval() {
		descargarArchivoDocumentoAvalGrupo(grupoSeleccionado.getId(),grupoSeleccionado.getArchivoAval());
	}
	
	public void imprimirReporteFormatoGrupo() {
		imprimirReporteFormatoGrupo(grupoSeleccionado.getId().toString());
	}
	
	/**
	 * Función para cargar historico de edicion de formularios de grupos.
	 */
	public void cargarHistoricoFormulario() {
		setHistoricosFormularioGrupo(servicioGeneral.obtenerObjetos(HistoricoFormularioGrupo.class,
				"from HistoricoFormularioGrupo h where h.grupo.id = '" + grupoSeleccionado.getId().toString() + "' order by h.fecha desc"));

	}

	public String getEstadoSolicitado() {
		return estadoSolicitado;
	}

	public void setEstadoSolicitado(String estadoSolicitado) {
		this.estadoSolicitado = estadoSolicitado;
	}
	
	public void enviarNotificacion(int numberPlantilla) {
		CorreoPlantilla correoActual = new CorreoPlantilla();
		correoActual = cargarPlantilla(numberPlantilla);
		String cuerpoCorreo = correoActual.getCuerpo().replaceAll("<<NOMBRE>>", grupoSeleccionado.getNombreGrupo());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<LIDER>>", grupoSeleccionado.getResponsable().getNombreCompleto());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<FACULTAD>>", grupoSeleccionado.getDependencia().getFacultad().getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<SEDE>>", grupoSeleccionado.getSede().getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", grupoSeleccionado.getId().toString());
		Correo mensaje = new Correo();
		mensaje.setOrigen(Correo.CORREO_HERMES);
		mensaje.setAsunto(correoActual.getAsunto().replaceAll("<<ID>>", grupoSeleccionado.getId().toString()));
		mensaje.setCuerpo(cuerpoCorreo);
		mensaje.adicionarDireccion(grupoSeleccionado.getResponsable().getEmail());
		servicioCorreo.enviarCorreo(mensaje);
	}

	public List<HistoricoFormularioGrupo> getHistoricosFormularioGrupo() {
		return historicosFormularioGrupo;
	}

	public void setHistoricosFormularioGrupo(List<HistoricoFormularioGrupo> historicosFormularioGrupo) {
		this.historicosFormularioGrupo = historicosFormularioGrupo;
	}
	

}
