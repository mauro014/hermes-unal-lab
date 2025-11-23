/**
 * @author  Ing Juan Pablo Duque
 */

package co.edu.unal.hermes.vista.grupos.ediciongrupos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.AreaTematicaGrupo;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.GrupoAgenda;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.LineaInvestigacion;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PosibleAgendaGrupo;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorLineasGrupo.
 */
public class ManejadorLineasGrupo extends ManejadorBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8228620653795848235L;

	/** The Constant DOMINIO_SUB_AREA_CIENCIA. */
	private static final String DOMINIO_SUB_AREA_CIENCIA = "SUB_AREA_CIENCIA_FM";

	/** The Constant DOMINIO_AREA_CIENCIA. */
	private static final String DOMINIO_AREA_CIENCIA = "AREA_CIENCIA";

	/** The linea investigacion A proponer. */
	private String lineaInvestigacionAProponer;

	/** The linea investigacion. */
	private String lineaInvestigacion;

	/** The grupo actual. */
	private Grupo grupoActual;

	/** The linea seleccionada. */
	private LineaInvestigacion lineaSeleccionada;

	/** The area ciencia. */
	// Objetos necesarios para la gestión de areas tematicas
	private String areaCiencia;

	/** The lista sub area ciencia. */
	private List<DominioDetalle> listaSubAreaCiencia;

	/** The lista area ciencia. */
	private List<DominioDetalle> listaAreaCiencia;

	/** The lista sub area ciencia secundaria. */
	private List<DominioDetalle> listaSubAreaCienciaSecundaria;

	/** The lista posibles agendas. */
	private List<PosibleAgendaGrupo> listaPosiblesAgendas;

	/** The lista agendas. */
	private List<GrupoAgenda> listaAgendas;

	/** The lista agendas eliminadas. */
	private List<GrupoAgenda> listaAgendasEliminadas;

	/** The area ciencia secundaria. */
	private String areaCienciaSecundaria;

	/** The sub area ciencia. */
	private String subAreaCiencia;

	/** The sub area ciencia secundaria. */
	private String subAreaCienciaSecundaria;

	/** The area ciencia items. */
	private ArrayList<SelectItem> areaCienciaItems;

	/** The sub area ciencia items. */
	private ArrayList<SelectItem> subAreaCienciaItems;

	/** The sub area ciencia secundaria items. */
	private ArrayList<SelectItem> subAreaCienciaSecundariaItems;

	/** The lista posibles agendas item. */
	private ArrayList<SelectItem> listaPosiblesAgendasItem;

	/** The link lineas. */
	private String linkLineas = "http://www.hermes.unal.edu.co/pages/descargas/AreasCiencia.pdf";

	/** The area seleccionada. */
	private AreaTematicaGrupo areaSeleccionada;

	/** The agenda principal grupo. */
	private String agendaPrincipalGrupo;

	/** The agenda secundaria grupo. */
	private String agendaSecundariaGrupo;

	/** The agenda seleccionada. */
	private GrupoAgenda agendaSeleccionada;

	private List<LineaInvestigacion> lineasInvestigacion;
	private ArrayList<SelectItem> lineasInvestigacionItem;

	private boolean lider;
	
	private String linkObjetivosDesarrolloSostenible = "http://www.undp.org/content/undp/es/home/sustainable-development-goals.html";
	private SelectItem[] objetivosDesarrolloSostenibleItems;

	/**
	 * Instantiates a new manejador lineas grupo.
	 */
	public ManejadorLineasGrupo() {
		grupoActual = (Grupo) sesion.getAttribute("grupo");
		grupoActual = servicioGrupo.obtenerGrupoLineas(grupoActual.getId());
		cargarAreasTematica();
		cargarInformacionAreasPadre();
		cargarSubAreatematicaDesdeGrupo();
		cargarPosiblesAgendas();
		cargarPosiblesLineas();
		lider = validarPermisos();
		List<DominioDetalle> listaObjetivosDesarrolloSostenible = servicioGeneral
				.obtenerDominioDetalle("OBJETIVOS_DESARROLLO_SOSTENIBLE");
		objetivosDesarrolloSostenibleItems = crearListaItems(listaObjetivosDesarrolloSostenible);
	}

	private void cargarPosiblesLineas() {
		lineasInvestigacion = servicioGeneral.obtenerListaObjetosOrdenadosAsc(new LineaInvestigacion(), "nombre");

		if (lineasInvestigacion != null) {
			Iterator<LineaInvestigacion> i = lineasInvestigacion.iterator();
			lineasInvestigacionItem = new ArrayList<SelectItem>();
			while (i.hasNext()) {
				LineaInvestigacion li = i.next();
				lineasInvestigacionItem.add(new SelectItem(li.getNombre(), li.getNombre()));
			}
		}
	}

	/**
	 * Adicionar linea.
	 */
	public void adicionarLinea() {
		if (lineaInvestigacion.equals("")) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("Debe escribrir una línea de investigación");
			mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("linea", mensaje);
		} else {
			lineaInvestigacion = lineaInvestigacion.trim();
			LineaInvestigacion lineaNueva = servicioLineaInvestigacion.adicionarLinea(lineaInvestigacion);
			grupoActual.adicionarLineaInvestigacion(lineaNueva);
			lineaInvestigacion = "";
		}
	}

	/**
	 * Agregar agenda.
	 */
	public void agregarAgenda() {
		if (agendaSecundariaGrupo != null && !agendaSecundariaGrupo.equals("")) {
			Iterator<PosibleAgendaGrupo> i = listaPosiblesAgendas.iterator();
			while (i.hasNext()) {
				PosibleAgendaGrupo posibleAgendaGrupo = i.next();
				Long idNuevo = Long.parseLong(agendaSecundariaGrupo);
				if (posibleAgendaGrupo.getId().equals(idNuevo)) {
					boolean existe = false;
					if (listaAgendas == null) {
						listaAgendas = new ArrayList<GrupoAgenda>();
					}
					Iterator<GrupoAgenda> j = listaAgendas.iterator();
					while (j.hasNext()) {
						GrupoAgenda grupoAgenda = j.next();
						if (grupoAgenda.getAgenda().getId().equals(idNuevo)) {
							existe = true;
							break;
						}
					}
					if (!existe) {
						GrupoAgenda grupoAgenda = new GrupoAgenda();
						grupoAgenda.setGrupo(grupoActual);
						grupoAgenda.setAgenda(posibleAgendaGrupo);
						listaAgendas.add(grupoAgenda);
					}
					break;
				}
			}
		}
	}

	/**
	 * Agregar area secundaria.
	 */
	public void agregarAreaSecundaria() {

		if (!subAreaCienciaSecundaria.equals("") && !subAreaCienciaSecundaria.equals("")) {

			DominioDetalle areaPadre = null;
			Iterator<DominioDetalle> k = listaAreaCiencia.iterator();
			while (k.hasNext()) {
				DominioDetalle dominioDetalle = k.next();
				if (dominioDetalle.getIdentificador().getTipo().equals(areaCienciaSecundaria)) {
					areaPadre = dominioDetalle;
					break;
				}
			}

			DominioDetalle areaHijo = null;
			Iterator<DominioDetalle> j = listaSubAreaCienciaSecundaria.iterator();
			while (j.hasNext()) {
				DominioDetalle dominioDetalle = j.next();
				if (dominioDetalle.getIdentificador().getTipo().equals(subAreaCienciaSecundaria)) {
					areaHijo = dominioDetalle;
					break;
				}
			}

			if (areaHijo != null && areaPadre != null) {
				AreaTematicaGrupo areaTematicaGrupo = new AreaTematicaGrupo();
				areaTematicaGrupo.setGrupo(grupoActual);
				areaTematicaGrupo.setAreaTematica(areaHijo);
				areaTematicaGrupo.setAreaTematicaPadre(areaPadre);

				boolean areaYaAgregada = false;
				if (grupoActual.getListaAreas() != null) {
					Iterator<AreaTematicaGrupo> i = grupoActual.getListaAreas().iterator();
					while (i.hasNext()) {
						AreaTematicaGrupo atg = i.next();
						if (atg.getAreaTematica().getIdentificador().getTipo()
								.equals(areaTematicaGrupo.getAreaTematica().getIdentificador().getId())) {
							areaYaAgregada = true;
						}
					}
				}

				if (!areaYaAgregada) {
					grupoActual.adicionarAreaTematica(areaTematicaGrupo);
				} else {
					publicarMensaje("El área temática seleccionada ya se encuentra registrada",
							FacesMessage.SEVERITY_ERROR);
				}
			} else {
				publicarMensaje("El área temática seleccionada no existe", FacesMessage.SEVERITY_ERROR);
			}
		} else {
			publicarMensaje("Debe seleccionar el área y sub-área", FacesMessage.SEVERITY_ERROR);
		}
	}

	/**
	 * Atras.
	 *
	 * @return the string
	 */
	public String atras() {
		servicioGeneral.guardarObjeto(grupoActual);
		sesion.removeAttribute("manejadorLineasGrupo");
		return "irEdicionIntegrantes";
	}

	/**
	 * Cambiar subarea tematica.
	 */
	public void cambiarSubareaTematica() {
		subAreaCienciaItems = (ArrayList<SelectItem>) cargarSubareaTematica(areaCiencia, true);
	}

	/**
	 * Cambiar subarea tematica secundaria.
	 */
	public void cambiarSubareaTematicaSecundaria() {
		subAreaCienciaSecundariaItems = (ArrayList<SelectItem>) cargarSubareaTematica(areaCienciaSecundaria, false);
	}

	/**
	 * Cargar areas tematica.
	 */
	private void cargarAreasTematica() {
		String consulta = "select dd from Dominio d, DominioDetalle dd "
				+ "where d.id = dd.identificador.id and d.tipo ='" + DOMINIO_AREA_CIENCIA
				+ "'  order by dd.descripcion";

		listaAreaCiencia = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		areaCienciaItems = new ArrayList<SelectItem>();

		if (listaAreaCiencia.size() > 0) {
			for (int i = 0; i < listaAreaCiencia.size(); i++) {
				DominioDetalle dominio = listaAreaCiencia.get(i);
				areaCienciaItems.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
			}
		}

	}

	/**
	 * Cargar informacion areas padre.
	 */
	private void cargarInformacionAreasPadre() {
		Iterator<AreaTematicaGrupo> i = grupoActual.getListaAreas().iterator();
		while (i.hasNext()) {
			AreaTematicaGrupo areaTematicaGrupo = i.next();
			Iterator<DominioDetalle> j = listaAreaCiencia.iterator();
			while (j.hasNext()) {
				DominioDetalle dominioDetalle = j.next();
				if (areaTematicaGrupo.getAreaTematica().getEstado()
						.equals(dominioDetalle.getIdentificador().getTipo())) {
					areaTematicaGrupo.setAreaTematicaPadre(dominioDetalle);
				}
			}
		}
	}

	/**
	 * Cargar posibles agendas.
	 */
	private void cargarPosiblesAgendas() {
		listaPosiblesAgendas = servicioGeneral.obtenerObjetos(PosibleAgendaGrupo.class,
				"from PosibleAgendaGrupo pag where pag.estado = 'A'");

		if (listaPosiblesAgendas != null) {
			Iterator<PosibleAgendaGrupo> i = listaPosiblesAgendas.iterator();
			listaPosiblesAgendasItem = new ArrayList<SelectItem>();
			while (i.hasNext()) {
				PosibleAgendaGrupo posibleAgendaGrupo = i.next();
				listaPosiblesAgendasItem
						.add(new SelectItem(posibleAgendaGrupo.getId().toString(), posibleAgendaGrupo.getNombre()));
			}
		}

		if (grupoActual != null && grupoActual.getAgendaPrincipal() != null
				&& grupoActual.getAgendaPrincipal().getId() != null) {
			agendaPrincipalGrupo = grupoActual.getAgendaPrincipal().getId().toString();
		}

		if (grupoActual.getId() != null) {
			listaAgendas = servicioGeneral.obtenerObjetos(GrupoAgenda.class,
					"from GrupoAgenda ga where ga.grupo.id = '" + grupoActual.getId() + "'");
		}
	}

	/**
	 * Cargar subarea tematica.
	 *
	 * @param areaCiencia
	 *            the area ciencia
	 * @param esPrimaria
	 *            the es primaria
	 * @return the list
	 */
	public List<SelectItem> cargarSubareaTematica(String areaCiencia, boolean esPrimaria) {
		String consulta = "select dd from Dominio d, "
				+ "DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='" + DOMINIO_SUB_AREA_CIENCIA
				+ "' and dd.estado = '" + areaCiencia + "' order by dd.descripcion";
		List<DominioDetalle> listaSubAreaCiencia = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);
		if (esPrimaria) {
			this.listaSubAreaCiencia = listaSubAreaCiencia;
		} else {
			listaSubAreaCienciaSecundaria = listaSubAreaCiencia;
		}
		List<SelectItem> subAreaList = new ArrayList<SelectItem>();
		if (listaSubAreaCiencia.size() > 0) {
			for (int i = 0; i < listaSubAreaCiencia.size(); i++) {
				DominioDetalle dominio = listaSubAreaCiencia.get(i);
				String nombre = dominio.getDescripcion();
				if (nombre != null && nombre.length() > 140) {
					nombre = nombre.substring(0, 140) + "...";
				}
				subAreaList.add(new SelectItem(dominio.getIdentificador().getTipo(), nombre));
			}
		}
		return subAreaList;
	}

	/**
	 * Cargar sub areatematica desde grupo.
	 */
	private void cargarSubAreatematicaDesdeGrupo() {
		if (grupoActual != null && grupoActual.getSubAreaTematica() != null) {
			Iterator<DominioDetalle> j = listaAreaCiencia.iterator();
			while (j.hasNext()) {
				DominioDetalle dominioDetalle = j.next();
				if (grupoActual.getSubAreaTematica().getEstado().equals(dominioDetalle.getIdentificador().getTipo())) {
					areaCiencia = dominioDetalle.getIdentificador().getTipo();
					break;
				}
			}
			subAreaCienciaItems = (ArrayList<SelectItem>) cargarSubareaTematica(areaCiencia, true);
			subAreaCiencia = grupoActual.getSubAreaTematica().getIdentificador().getTipo();
		}
	}

	/**
	 * Eliminar agenda.
	 */
	public void eliminarAgenda() {
		listaAgendas.remove(agendaSeleccionada);
		if (agendaSeleccionada.getId() != null) {
			if (listaAgendasEliminadas == null) {
				listaAgendasEliminadas = new ArrayList<GrupoAgenda>();
			}
			listaAgendasEliminadas.add(agendaSeleccionada);
		}
	}

	/**
	 * Eliminar area.
	 */
	public void eliminarArea() {
		grupoActual.borrarArea(areaSeleccionada);
	}

	/**
	 * Eliminar linea.
	 */
	public void eliminarLinea() {
		LineaInvestigacion linea = lineaSeleccionada;
		grupoActual.borrarLineaInvestigacion(linea);
	}

	/**
	 * Gets the agenda principal grupo.
	 *
	 * @return the agenda principal grupo
	 */
	public String getAgendaPrincipalGrupo() {
		return agendaPrincipalGrupo;
	}

	/**
	 * Gets the agenda secundaria grupo.
	 *
	 * @return the agenda secundaria grupo
	 */
	public String getAgendaSecundariaGrupo() {
		return agendaSecundariaGrupo;
	}

	/**
	 * Gets the agenda seleccionada.
	 *
	 * @return the agenda seleccionada
	 */
	public GrupoAgenda getAgendaSeleccionada() {
		return agendaSeleccionada;
	}

	/**
	 * Gets the area ciencia.
	 *
	 * @return the area ciencia
	 */
	public String getAreaCiencia() {
		return areaCiencia;
	}

	/**
	 * Gets the area ciencia items.
	 *
	 * @return the area ciencia items
	 */
	public ArrayList<SelectItem> getAreaCienciaItems() {
		return areaCienciaItems;
	}

	/**
	 * Gets the area ciencia secundaria.
	 *
	 * @return the area ciencia secundaria
	 */
	public String getAreaCienciaSecundaria() {
		return areaCienciaSecundaria;
	}

	/**
	 * Gets the area seleccionada.
	 *
	 * @return the area seleccionada
	 */
	public AreaTematicaGrupo getAreaSeleccionada() {
		return areaSeleccionada;
	}

	/**
	 * Gets the grupo actual.
	 *
	 * @return the grupo actual
	 */
	public Grupo getGrupoActual() {
		return grupoActual;
	}

	/**
	 * Gets the linea investigacion.
	 *
	 * @return the linea investigacion
	 */
	public String getLineaInvestigacion() {
		return lineaInvestigacion;
	}

	/**
	 * Gets the linea investigacion A proponer.
	 *
	 * @return the linea investigacion A proponer
	 */
	public String getLineaInvestigacionAProponer() {
		return lineaInvestigacionAProponer;
	}

	/**
	 * Gets the linea seleccionada.
	 *
	 * @return the linea seleccionada
	 */
	public LineaInvestigacion getLineaSeleccionada() {
		return lineaSeleccionada;
	}

	/**
	 * Gets the link lineas.
	 *
	 * @return the link lineas
	 */
	public String getLinkLineas() {
		return linkLineas;
	}

	/**
	 * Gets the lista agendas.
	 *
	 * @return the lista agendas
	 */
	public List<GrupoAgenda> getListaAgendas() {
		return listaAgendas;
	}

	/**
	 * Gets the lista posibles agendas.
	 *
	 * @return the lista posibles agendas
	 */
	public List<PosibleAgendaGrupo> getListaPosiblesAgendas() {
		return listaPosiblesAgendas;
	}

	/**
	 * Gets the lista posibles agendas item.
	 *
	 * @return the lista posibles agendas item
	 */
	public ArrayList<SelectItem> getListaPosiblesAgendasItem() {
		return listaPosiblesAgendasItem;
	}

	/**
	 * Gets the sub area ciencia.
	 *
	 * @return the sub area ciencia
	 */
	public String getSubAreaCiencia() {
		return subAreaCiencia;
	}

	/**
	 * Gets the sub area ciencia items.
	 *
	 * @return the sub area ciencia items
	 */
	public ArrayList<SelectItem> getSubAreaCienciaItems() {
		return subAreaCienciaItems;
	}

	/**
	 * Gets the sub area ciencia secundaria.
	 *
	 * @return the sub area ciencia secundaria
	 */
	public String getSubAreaCienciaSecundaria() {
		return subAreaCienciaSecundaria;
	}

	/**
	 * Gets the sub area ciencia secundaria items.
	 *
	 * @return the sub area ciencia secundaria items
	 */
	public ArrayList<SelectItem> getSubAreaCienciaSecundariaItems() {
		return subAreaCienciaSecundariaItems;
	}

	/**
	 * Guardar.
	 *
	 * @return the string
	 */
	public String guardar() {
		return guardarGrupo(false);
	}

	/**
	 * Guardar grupo.
	 *
	 * @param avanzar
	 *            the avanzar
	 * @return the string
	 */
	private String guardarGrupo(boolean avanzar) {
		
		if (esCadenaVacia(grupoActual.getObjetivoDesarrolloSosteniblePrincipal())) {
			publicarMensaje("Por favor, indique el Objetivo de Desarrollo Sostenible principal.", FacesMessage.SEVERITY_ERROR);
			return "";
		}
		
		if (grupoActual.getObjetivoDesarrolloSosteniblePrincipal().equals(grupoActual.getObjetivoDesarrolloSostenibleSecundario())) {
			publicarMensaje("El Objetivo de Desarrollo Sostenible principal es el mismo Objetivo de Desarrollo Sostenible secundario.", FacesMessage.SEVERITY_ERROR);
			return "";
		}

		if (grupoActual.getLineas().size() == 0) {
			publicarMensaje("El grupo debe tener líneas de investigación asociadas", FacesMessage.SEVERITY_ERROR);
			return "";
		}

		if (areaCiencia.equals("") || subAreaCiencia.equals("")) {
			publicarMensaje("Debe seleccionar un área y sub-área principal", FacesMessage.SEVERITY_ERROR);
			return "";
		}

		if (agendaPrincipalGrupo != null && !agendaPrincipalGrupo.equals("")) {
			PosibleAgendaGrupo posibleAgendaGrupo = new PosibleAgendaGrupo();
			Long idAgenda = Long.parseLong(agendaPrincipalGrupo);
			posibleAgendaGrupo.setId(idAgenda);
			grupoActual.setAgendaPrincipal(posibleAgendaGrupo);
		}

		if (listaAgendas != null && listaAgendas.size() > 0) {
			Iterator<GrupoAgenda> i = listaAgendas.iterator();
			while (i.hasNext()) {
				GrupoAgenda grupoAgenda = i.next();
				if (grupoAgenda.getId() == null) {
					servicioGeneral.guardarObjeto(grupoAgenda);
				}
			}
		}

		if (listaAgendasEliminadas != null && listaAgendasEliminadas.size() > 0) {
			Iterator<GrupoAgenda> i = listaAgendasEliminadas.iterator();
			while (i.hasNext()) {
				GrupoAgenda grupoAgenda = i.next();
				if (grupoAgenda.getId() != null) {
					servicioGeneral.eliminarObjeto(grupoAgenda);
				}
			}
			listaAgendasEliminadas.clear();
		}

		// Se actualiza estado menu del grupo
		if (grupoActual.getEstadoMenu() < 4) {
			grupoActual.setEstadoMenu(4);
		}

		obtenerAreaTematicaSeleccionada();

		servicioGeneral.guardarObjeto(grupoActual);

		sesion.setAttribute("grupo", grupoActual);

		sesion.removeAttribute("manejadorMenuFormularioGrupos");

		publicarMensaje("La información ha sido gurdada correctamente ", FacesMessage.SEVERITY_INFO);

		Persona persona = (Persona) sesion.getAttribute("persona");
		guardarHistoricoFormularioGrupo(grupoActual, persona, "3");
		if (!lider) {
			notificarCambiosEstLider();
		}
		if (avanzar) {
			return "irVisionPrioridadesPerspectiva";
		}
		return "";
	}

	private void notificarCambiosEstLider() {
		CorreoPlantilla correoActual = new CorreoPlantilla();
		correoActual = cargarPlantilla(320);
		String cuerpoCorreo = correoActual.getCuerpo().replaceAll("<<EST>>",
				((Investigador) sesion.getAttribute("persona")).getNombreCompleto());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", grupoActual.getId().toString());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<GRUPO>>", grupoActual.getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<FORM>>", "LÍNEAS DE INVESTIGACIÓN");
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<FECHA>>",
				new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(getToday()));
		Correo mensaje = new Correo();
		mensaje.setOrigen(Correo.CORREO_HERMES);
		//mensaje.adicionarCopiaOculta(Correo.CORREO_HERMES);
		mensaje.setAsunto(correoActual.getAsunto());
		mensaje.setCuerpo(cuerpoCorreo);
		mensaje.adicionarDireccion(grupoActual.getLider().getInvestigador().getEmail());
		servicioCorreo.enviarCorreo(mensaje);
	}

	/**
	 * Guardary avanza.
	 *
	 * @return the string
	 */
	public String guardaryAvanza() {
		return guardarGrupo(true);
	}

	/**
	 * Obtener area tematica seleccionada.
	 */
	private void obtenerAreaTematicaSeleccionada() {
		Iterator<DominioDetalle> i = listaSubAreaCiencia.iterator();
		while (i.hasNext()) {
			DominioDetalle dominioDetalle = i.next();
			if (dominioDetalle.getIdentificador().getTipo().equals(subAreaCiencia)) {
				grupoActual.setSubAreaTematica(dominioDetalle);
				break;
			}
		}

	}

	/**
	 * Publicar mensaje.
	 *
	 * @param error
	 *            the error
	 * @param severity
	 *            the severity
	 */
	private void publicarMensaje(String error, Severity severity) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage mensaje = new FacesMessage(severity, error, "");
		context.addMessage("mensajeError", mensaje);
	}

	/**
	 * Sets the agenda principal grupo.
	 *
	 * @param agendaPrincipalGrupo
	 *            the new agenda principal grupo
	 */
	public void setAgendaPrincipalGrupo(String agendaPrincipalGrupo) {
		this.agendaPrincipalGrupo = agendaPrincipalGrupo;
	}

	/**
	 * Sets the agenda secundaria grupo.
	 *
	 * @param agendaSecundariaGrupo
	 *            the new agenda secundaria grupo
	 */
	public void setAgendaSecundariaGrupo(String agendaSecundariaGrupo) {
		this.agendaSecundariaGrupo = agendaSecundariaGrupo;
	}

	/**
	 * Sets the agenda seleccionada.
	 *
	 * @param agendaSeleccionada
	 *            the new agenda seleccionada
	 */
	public void setAgendaSeleccionada(GrupoAgenda agendaSeleccionada) {
		this.agendaSeleccionada = agendaSeleccionada;
	}

	/**
	 * Sets the area ciencia.
	 *
	 * @param areaCiencia
	 *            the new area ciencia
	 */
	public void setAreaCiencia(String areaCiencia) {
		this.areaCiencia = areaCiencia;
	}

	/**
	 * Sets the area ciencia secundaria.
	 *
	 * @param areaCienciaSecundaria
	 *            the new area ciencia secundaria
	 */
	public void setAreaCienciaSecundaria(String areaCienciaSecundaria) {
		this.areaCienciaSecundaria = areaCienciaSecundaria;
	}

	/**
	 * Sets the area seleccionada.
	 *
	 * @param areaSeleccionada
	 *            the new area seleccionada
	 */
	public void setAreaSeleccionada(AreaTematicaGrupo areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	/**
	 * Sets the grupo actual.
	 *
	 * @param grupoActual
	 *            the new grupo actual
	 */
	public void setGrupoActual(Grupo grupoActual) {
		this.grupoActual = grupoActual;
	}

	/**
	 * Sets the linea investigacion.
	 *
	 * @param lineaInvestigacion
	 *            the new linea investigacion
	 */
	public void setLineaInvestigacion(String lineaInvestigacion) {
		this.lineaInvestigacion = lineaInvestigacion;
	}

	/**
	 * Sets the linea investigacion A proponer.
	 *
	 * @param lineaInvestigacionAProponer
	 *            the new linea investigacion A proponer
	 */
	public void setLineaInvestigacionAProponer(String lineaInvestigacionAProponer) {
		this.lineaInvestigacionAProponer = lineaInvestigacionAProponer;
	}

	/**
	 * Sets the linea seleccionada.
	 *
	 * @param lineaSeleccionada
	 *            the new linea seleccionada
	 */
	public void setLineaSeleccionada(LineaInvestigacion lineaSeleccionada) {
		this.lineaSeleccionada = lineaSeleccionada;
	}

	/**
	 * Sets the lista posibles agendas.
	 *
	 * @param listaPosiblesAgendas
	 *            the new lista posibles agendas
	 */
	public void setListaPosiblesAgendas(List<PosibleAgendaGrupo> listaPosiblesAgendas) {
		this.listaPosiblesAgendas = listaPosiblesAgendas;
	}

	/**
	 * Sets the lista posibles agendas item.
	 *
	 * @param listaPosiblesAgendasItem
	 *            the new lista posibles agendas item
	 */
	public void setListaPosiblesAgendasItem(ArrayList<SelectItem> listaPosiblesAgendasItem) {
		this.listaPosiblesAgendasItem = listaPosiblesAgendasItem;
	}

	/**
	 * Sets the sub area ciencia.
	 *
	 * @param subAreaCiencia
	 *            the new sub area ciencia
	 */
	public void setSubAreaCiencia(String subAreaCiencia) {
		this.subAreaCiencia = subAreaCiencia;
	}

	/**
	 * Sets the sub area ciencia secundaria.
	 *
	 * @param subAreaCienciaSecundaria
	 *            the new sub area ciencia secundaria
	 */
	public void setSubAreaCienciaSecundaria(String subAreaCienciaSecundaria) {
		this.subAreaCienciaSecundaria = subAreaCienciaSecundaria;
	}

	public List<LineaInvestigacion> getLineasInvestigacion() {
		return lineasInvestigacion;
	}

	public void setLineasInvestigacion(List<LineaInvestigacion> lineasInvestigacion) {
		this.lineasInvestigacion = lineasInvestigacion;
	}

	public ArrayList<SelectItem> getLineasInvestigacionItem() {
		return lineasInvestigacionItem;
	}

	public void setLineasInvestigacionItem(ArrayList<SelectItem> lineasInvestigacionItem) {
		this.lineasInvestigacionItem = lineasInvestigacionItem;
	}

	/**
	 * Siguiente.
	 *
	 * @return the string
	 */
	public String siguiente() {
		return "irVisionPrioridadesPerspectiva";
	}

	public boolean validarPermisos() {
		Investigador investigadorInterno = (Investigador) sesion.getAttribute("persona");
		List<InvestigadorGrupo> listGruposInvestigador = servicioPersona.obtenerGruposInvestigador(investigadorInterno);
		if (!esListaVacia(listGruposInvestigador)) {
			Iterator<InvestigadorGrupo> i = listGruposInvestigador.iterator();
			while (i.hasNext()) {
				InvestigadorGrupo investigadorGrupo = i.next();
				if (investigadorGrupo.getGrupo().equals(grupoActual)
						&& investigadorInterno.getId().getDocumento()
								.equals(investigadorGrupo.getInvestigador().getId().getDocumento())
						&& investigadorInterno.getId().getTipoDocumento()
								.equals(investigadorGrupo.getInvestigador().getId().getTipoDocumento())) {
					return (investigadorGrupo.getTipo().equals(InvestigadorGrupo.LIDER)
							|| investigadorGrupo.getTipo().equals(InvestigadorGrupo.CODIRECTOR));
				}
			}
		}
		return false;
	}

	public String getLinkObjetivosDesarrolloSostenible() {
		return linkObjetivosDesarrolloSostenible;
	}

	public void setLinkObjetivosDesarrolloSostenible(String linkObjetivosDesarrolloSostenible) {
		this.linkObjetivosDesarrolloSostenible = linkObjetivosDesarrolloSostenible;
	}

	public SelectItem[] getObjetivosDesarrolloSostenibleItems() {
		return objetivosDesarrolloSostenibleItems;
	}

	public void setObjetivosDesarrolloSostenibleItems(SelectItem[] objetivosDesarrolloSostenibleItems) {
		this.objetivosDesarrolloSostenibleItems = objetivosDesarrolloSostenibleItems;
	}
}
