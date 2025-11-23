/**
 * Modificado por Mauricio
 */

package co.edu.unal.hermes.vista.grupos;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.EstadoGrupo;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.GrupoRequisito;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProyectoRequisito;
import co.edu.unal.hermes.modelo.SolicitudGrupo;
import co.edu.unal.hermes.modelo.TipoRequisito;
import co.edu.unal.hermes.modelo.TipoRequisitoGrupo;
import co.edu.unal.hermes.modelo.TipoSolicitudGrupo;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.grupos.ediciongrupos.ManejadorEdicionGrupo;

/**
 * The Class ManejadorGruposInvestigador.
 */
public class ManejadorTramitesGrupo extends ManejadorBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3995286768472964947L;

	/** The lista grupos. */
	private List<SolicitudGrupo> listaSolicitudes;
	private List<SolicitudGrupo> filteredSolicitudes;
	private SolicitudGrupo solicitudGrupo;

	/** The investigador interno. */
	private InvestigadorInterno investigadorInterno;
	/** The path. */
	// Variables necesarias para la gestión de los avales del grupo
	String path = RUTA_ARCHIVOS + File.separator + "HER_GRUPO_AVAL" + File.separator;
	protected UploadedFile archivoCargar;

	/** The lista requisito. */
	private List<GrupoRequisito> listaRequisito;
	protected SelectItem[] categoriaItems = { new SelectItem(new Integer(1), "Pendiente de Aprobación"),
			new SelectItem(new Integer(2), "Aprobado"), new SelectItem(new Integer(3), "No Aprobado"),
			new SelectItem(new Integer(4), "Devolver para correcciones") };
	private int selItem;

	/**
	 * Instantiates a new manejador grupos investigador.
	 */
	public ManejadorTramitesGrupo() {
		super();
		setListaSolicitudes(new ArrayList<SolicitudGrupo>());
		solicitudGrupo = new SolicitudGrupo();
		cargadoDatosInicial();
	}

	public void guardarArchivo(FileUploadEvent event) {
		archivoCargar = event.getFile();
		cargarArchivoDisco(archivoCargar, "HER_GRUPO_AVAL", solicitudGrupo.getGrupo().getId().toString() + "");
		int i = archivoCargar.getFileName().lastIndexOf("\\");
		this.solicitudGrupo.getGrupo().setArchivoAval(archivoCargar.getFileName().substring(i + 1));
	}

	/**
	 * Cargado datos inicial.
	 */
	private void cargadoDatosInicial() {
		personaActual = (Persona) sesion.getAttribute("persona");
		investigadorInterno = servicioPersona.obtenerInvestigadorInterno(personaActual.getId());

		if (investigadorInterno.getDependencia().getSede().isEsSedePresenciaNacional()) {
			setListaSolicitudes(servicioGeneral.obtenerObjetos(
					"select s from SolicitudGrupo s where (s.respuesta is null or s.respuesta = 'T')"
							+ " and s.dependenciaRevision.sede.id ='"
							+ investigadorInterno.getDependencia2().getSede().getId() + "'"));
		} else {
			setListaSolicitudes(servicioGeneral.obtenerObjetos(
					"select s from SolicitudGrupo s where (s.respuesta is null or s.respuesta = 'T')"
							+ " and s.dependenciaRevision.facultad.id='"
							+ investigadorInterno.getDependencia2().getFacultad().getId() + "'"));
		}
	}

	public String revisar() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			borrarManejadoresInsercionProyecto();
			@SuppressWarnings("unchecked")
			Map<String, String> map = context.getExternalContext().getRequestParameterMap();
			Object o = map.get("idSolGrupo");
			Long id = Long.valueOf((String) o);
			List<SolicitudGrupo> lista = servicioGeneral.obtenerObjetoXID(SolicitudGrupo.class, id.toString());

			if (!esListaVacia(lista)) {
				solicitudGrupo = lista.get(0);
				cargarRequisitosGrupo(id);
			}
		} catch (Exception e) {
			return "";
		}

		return "revisarSolicitudGrupo";
	}

	public void cargarRequisitosGrupo(Long id) {
		listaRequisito = new ArrayList<GrupoRequisito>();
		listaRequisito = servicioGeneral.obtenerObjetos(GrupoRequisito.class,
				"from GrupoRequisito p where p.grupo.id = " + id);
		if (esListaVacia(listaRequisito)) {
			List<TipoRequisitoGrupo> listaRequisitosActivos = servicioGeneral.obtenerObjetos(TipoRequisitoGrupo.class,
					"from TipoRequisitoGrupo p where p.vigencia = '1'");

			for (Iterator<TipoRequisitoGrupo> it = listaRequisitosActivos.iterator(); it.hasNext();) {
				TipoRequisitoGrupo tipoRequisito = (TipoRequisitoGrupo) it.next();
				GrupoRequisito requisitoGrupo = new GrupoRequisito();
				requisitoGrupo.setRequisito(tipoRequisito);
				requisitoGrupo.setGrupo(solicitudGrupo.getGrupo());
				requisitoGrupo.setCumplido("N");
				listaRequisito.add(requisitoGrupo);
			}
		}

	}

	public void guardar() {
		int plantilla = 0;
		if (selItem == 0) {
			mensajeError("Debe seleccionar una decisión para la solicitud");
			return;
		} else if (selItem == 1) { // pendiente
			return;
		} else if (selItem == 2) { // aprobado
			if (solicitudGrupo.getTipoSolicitud().getId().equals(TipoSolicitudGrupo.CREACION_GRUPO)
					&& !verificarCumplimientoRequisitos()) {
				mensajeError("Deben marcarse como cumplidos todos los requisitos para aprobar la solicitud.");
				return;
			} else if (solicitudGrupo.getTipoSolicitud().getId().equals(TipoSolicitudGrupo.CREACION_GRUPO)
					&& esCadenaVacia(solicitudGrupo.getGrupo().getArchivoAval())) {
				mensajeError("Si la decisión es aprobar debe adjuntar el archivo del aval");
				return;
			} else {
				solicitudGrupo.setRespuesta("A");
				solicitudGrupo.getGrupo().setEstadoGrupo(new EstadoGrupo());
				if (solicitudGrupo.getTipoSolicitud().getId().equals(TipoSolicitudGrupo.CREACION_GRUPO)) {
					guardarRequisitos();
					plantilla = 396;
					solicitudGrupo.getGrupo().getEstadoGrupo().setId("A");
					justificacionGrupoHistorico = "Cambio de estado grupo por aprobación de aval FIC / Creación de grupo";
				} else if (solicitudGrupo.getTipoSolicitud().getId().equals(TipoSolicitudGrupo.CAMBIO_ESTADO)) {
					plantilla = 399;
					solicitudGrupo.getGrupo().getEstadoGrupo().setId(solicitudGrupo.getEstadoSolicitado().getId());
					justificacionGrupoHistorico = "Cambio de estado grupo por aprobación de solicitud "
							+ solicitudGrupo.getId();
				}

				cambiosGruposHistorico = "eg,";
				guardarHistoricoEstadoGrupo(solicitudGrupo.getGrupo(), personaActual);

			}
		} else if (selItem == 3) { // no aprobado
			solicitudGrupo.setRespuesta("N");
			if (solicitudGrupo.getTipoSolicitud().getId().equals(TipoSolicitudGrupo.CREACION_GRUPO)) {
				guardarRequisitos();
				solicitudGrupo.getGrupo().setEstadoGrupo(new EstadoGrupo());
				solicitudGrupo.getGrupo().getEstadoGrupo().setId("NA");
				cambiosGruposHistorico = "eg,";
				justificacionGrupoHistorico = "Cambio de estado grupo por no aprobación de aval FIC / Creación de grupo";
				guardarHistoricoEstadoGrupo(solicitudGrupo.getGrupo(), personaActual);
				plantilla = 395;
			} else {
				plantilla = 400;
			}

		} else if (selItem == 4) { // devolver para correcciones
			solicitudGrupo.setRespuesta("C");
			solicitudGrupo.getGrupo().setEstadoGrupo(new EstadoGrupo());
			solicitudGrupo.getGrupo().getEstadoGrupo().setId("C");
			cambiosGruposHistorico = "eg,";
			justificacionGrupoHistorico = "Cambio de estado grupo por devolución de aval FIC / Creación de grupo";
			guardarHistoricoEstadoGrupo(solicitudGrupo.getGrupo(), personaActual);
			plantilla = 397;
		}

		solicitudGrupo.setFechaRespuesta(getToday());
		solicitudGrupo.setResponsable(personaActual);
		try {
		servicioGeneral.guardarObjeto(solicitudGrupo);
		servicioGeneral.guardarObjeto(solicitudGrupo.getGrupo());
		}catch (Exception e) {
			e.printStackTrace();
			mensajeInfo("Ocurrió un problema en el trámite de la solicitud");
			return;
		}
		
		mensajeInfo("Solicitud tramitada");
		enviarNotificacion(plantilla);

	}

	public boolean verificarCumplimientoRequisitos() {
		if (!listaRequisito.isEmpty()) {
			GrupoRequisito req = new GrupoRequisito();
			for (int i = 0; i < listaRequisito.size(); i++) {
				req = listaRequisito.get(i);
				if (!req.isCumplidoCheckbox()) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean guardarRequisitos() {
		if (!listaRequisito.isEmpty()) {
			GrupoRequisito req = new GrupoRequisito();
			for (int i = 0; i < listaRequisito.size(); i++) {
				req = listaRequisito.get(i);
				req.setResponsableId(personaActual.getId().getDocumento());
				req.setResponsableTipoDocumento(personaActual.getId().getTipoDocumento());
				req.setCumplido(req.isCumplidoCheckbox() ? "S" : "N");
				servicioGeneral.guardarObjeto(req);
			}
		}
		return true;
	}

	public void enviarNotificacion(int numberPlantilla) {
		CorreoPlantilla correoActual = new CorreoPlantilla();
		correoActual = cargarPlantilla(numberPlantilla);
		String cuerpoCorreo = correoActual.getCuerpo().replaceAll("<<NOMBRE>>", solicitudGrupo.getGrupo().getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<LIDER>>",
				solicitudGrupo.getGrupo().getResponsable().getNombreCompleto());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<FACULTAD>>",
				solicitudGrupo.getGrupo().getDependencia().getFacultad().getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<SEDE>>", solicitudGrupo.getGrupo().getSede().getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", solicitudGrupo.getGrupo().getId().toString());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<COMENTARIOS>>", solicitudGrupo.getGrupo().getId().toString());
		Correo mensaje = new Correo();
		mensaje.setOrigen(Correo.CORREO_HERMES);
		mensaje.setAsunto(correoActual.getAsunto().replaceAll("<<ID>>", solicitudGrupo.getGrupo().getId().toString()));
		mensaje.setCuerpo(cuerpoCorreo);
		mensaje.adicionarDireccion(solicitudGrupo.getGrupo().getResponsable().getEmail());
		servicioCorreo.enviarCorreo(mensaje);
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

	public void eliminarManejadores() {
		sesion.removeAttribute("manejadorGruposInvestigador");
		sesion.removeAttribute("manejadorEdicionGrupo");
		sesion.removeAttribute("manejadorIntegrantesGrupo");
		sesion.removeAttribute("manejadorLineasGrupo");
		sesion.removeAttribute("manejadorEdicionGrupoVisionPrioridadesPerspectiva");
		sesion.removeAttribute("manejadorMenuFormularioGrupos");
		sesion.removeAttribute("grupo");
	}

	public List<SolicitudGrupo> getFilteredSolicitudes() {
		return filteredSolicitudes;
	}

	public void setFilteredSolicitudes(List<SolicitudGrupo> filteredSolicitudes) {
		this.filteredSolicitudes = filteredSolicitudes;
	}

	public SolicitudGrupo getSolicitudGrupo() {
		return solicitudGrupo;
	}

	public void setSolicitudGrupo(SolicitudGrupo solicitudGrupo) {
		this.solicitudGrupo = solicitudGrupo;
	}

	public List<SolicitudGrupo> getListaSolicitudes() {
		return listaSolicitudes;
	}

	public void setListaSolicitudes(List<SolicitudGrupo> listaSolicitudes) {
		this.listaSolicitudes = listaSolicitudes;
	}

	public InvestigadorInterno getInvestigadorInterno() {
		return investigadorInterno;
	}

	public void setInvestigadorInterno(InvestigadorInterno investigadorInterno) {
		this.investigadorInterno = investigadorInterno;
	}

	public UploadedFile getArchivoCargar() {
		return archivoCargar;
	}

	public void setArchivoCargar(UploadedFile archivoCargar) {
		this.archivoCargar = archivoCargar;
	}

	public List<GrupoRequisito> getListaRequisito() {
		return listaRequisito;
	}

	public void setListaRequisito(List<GrupoRequisito> listaRequisito) {
		this.listaRequisito = listaRequisito;
	}

	public SelectItem[] getCategoriaItems() {
		return categoriaItems;
	}

	public void setCategoriaItems(SelectItem[] categoriaItems) {
		this.categoriaItems = categoriaItems;
	}

	public int getSelItem() {
		return selItem;
	}

	public void setSelItem(int selItem) {
		this.selItem = selItem;
	}
	
	public void imprimirReporteFormatoGrupo() {
		imprimirReporteFormatoGrupo(solicitudGrupo.getGrupo().getId().toString());
	}

}
