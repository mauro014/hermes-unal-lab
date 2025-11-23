package co.edu.unal.hermes.vista.semilleros.registro;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.LineaInvestigacion;
import co.edu.unal.hermes.modelo.PosibleAgendaGrupo;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroAgenda;
import co.edu.unal.hermes.modelo.SemilleroAreaOCDE;
import co.edu.unal.hermes.modelo.SemilleroHistoricoCambios;
import co.edu.unal.hermes.modelo.SemilleroLinea;

public class ManejadorSemilleroLineasAreas extends ManejadorSemilleroRegistro {

	private static final long serialVersionUID = 1L;
	private Semillero semilleroActual;
	private ArrayList<SelectItem> areaOCDEItems;
	private ArrayList<SelectItem> subAreaOCDEItems;
	private List<DominioDetalle> listaSubAreaCiencia;
	private String linkLineas = "http://www.hermes.unal.edu.co/pages/descargas/AreasCiencia.pdf";
	private List<DominioDetalle> listaSubAreaOCDESecundaria;
	private String areaOCDESecundaria;
	private ArrayList<SelectItem> subAreaOCDESecundariaItems;
	private String subAreaOCDESecundaria;
	private List<DominioDetalle> listaAreaOCDE;
	private SemilleroAreaOCDE areaEliminar;
	private List<PosibleAgendaGrupo> listaPosiblesAgendas;
	private ArrayList<SelectItem> listaPosiblesAgendasItem;
	private String agendaSecundariaSeleccionada;
	private SemilleroAgenda agendaSecundariaEliminar;
	protected SelectItem[] objetivosDesarrolloSostenibleItems;
	private String linkObjetivosDesarrolloSostenible = "http://www.undp.org/content/undp/es/home/sustainable-development-goals.html";
	private String lineaSeleccionada;
	private ArrayList<SelectItem> lineasInvestigacionItem;
	private SemilleroLinea lineaEliminar;
	private String agendaPrincipal;

	public ManejadorSemilleroLineasAreas() {
		init();
	}

	private void init() {
		setSemilleroActual(servicioGeneral.obtenerObjetos(Semillero.class,
				"from Semillero s where s.id=" + (Integer) sesion.getAttribute("semillero")).get(0));
		sesion.setAttribute("semillero", semilleroActual.getId());
		if (getSemilleroActual().getAgendaPrincipal() != null
				&& getSemilleroActual().getAgendaPrincipal().getId() != null) {
			setAgendaPrincipal(getSemilleroActual().getAgendaPrincipal().getId().toString());
		}
		if (!esCadenaVacia(getSemilleroActual().getAreaOCDEPrincipal())) {
			cambiarSubAreaODCE();
		}
		cargarAreasOCDE();
		cargarInformacionAreasOCDESecundarias();
		cargarPosiblesAgendas();
		cargarPosiblesLineas();
		List<DominioDetalle> listaObjetivosDesarrolloSostenible = servicioGeneral
				.obtenerDominioDetalle("OBJETIVOS_DESARROLLO_SOSTENIBLE");
		objetivosDesarrolloSostenibleItems = crearListaItems(listaObjetivosDesarrolloSostenible);
	}

	@Override
	String guardar(boolean parcial) {
		if (esCadenaVacia(getSemilleroActual().getAreaOCDEPrincipal())) {
			generarMsg(2, "Debe indicar el Área OCDE Pprincipal.");
			return "";
		}
		if (esCadenaVacia(getSemilleroActual().getObjetivoDesarrolloSosteniblePrincipal())) {
			generarMsg(2, "Debe indicar el Objetivo de Desarrollo Sostenible Principal.");
			return "";
		}
		Semillero semOriginal = servicioGeneral
				.obtenerObjetoXID(Semillero.class, getSemilleroActual().getId().toString()).get(0);
		SemilleroHistoricoCambios shc = new SemilleroHistoricoCambios();
		shc.setSemillero(getSemilleroActual());
		shc.setFecha(new Date());
		if (semOriginal.getAreaOCDEPrincipal() != null
				&& !semOriginal.getAreaOCDEPrincipal().equals(getSemilleroActual().getAreaOCDEPrincipal())) {
			for (SelectItem selectItem : areaOCDEItems) {
				if (selectItem.getValue().toString().equals(getSemilleroActual().getAreaOCDEPrincipal())) {
					shc.setDescripcion("Área OCDE Principal Actual: " + selectItem.getLabel());
					break;
				}
			}
		}
		String texto = "";
		if (semOriginal.getObjetivoDesarrolloSosteniblePrincipal()!=null && !semOriginal.getObjetivoDesarrolloSosteniblePrincipal()
				.equals(getSemilleroActual().getObjetivoDesarrolloSosteniblePrincipal())) {
			for (SelectItem selectItem : objetivosDesarrolloSostenibleItems) {
				if (selectItem.getValue().toString()
						.equals(getSemilleroActual().getObjetivoDesarrolloSosteniblePrincipal())) {
					texto = "Objetivo Desarrollo Sostenible Actual: " + selectItem.getLabel();
					break;
				}
			}
		}
		if (esCadenaVacia(shc.getDescripcion())) {
			shc.setDescripcion(texto);
		} else {
			shc.setDescripcion(shc.getDescripcion() + "<br />" + texto);
		}
		if (!esCadenaVacia(shc.getDescripcion())) {
			servicioGeneral.guardarObjeto(shc);
			getSemilleroActual().getHistoricoCambios().add(shc);
		}
		if (getSemilleroActual().getFase().equals(2)) {
			getSemilleroActual().setFase(3);
		}
		try {
		servicioGeneral.guardarObjeto(getSemilleroActual());
		}catch(Exception e) {
			e.printStackTrace();
		}
		if (!parcial) {
			if (validarForm()) {
				getSemilleroActual().setAgendaPrincipal((PosibleAgendaGrupo) servicioGeneral
						.obtenerObjeto(new PosibleAgendaGrupo(), Long.parseLong(getAgendaPrincipal())));
				servicioGeneral.guardarObjeto(getSemilleroActual());
				return irPlanTrabajo(getSemilleroActual().getId());
			} else {
				return "";
			}
		} else {
			generarMsg(1, "Semillero registrado correctamente con el ID " + getSemilleroActual().getId());
			return "";
		}
	}

	@Override
	boolean validarForm() {
		isOK = true;
		if (esCadenaVacia(getSemilleroActual().getAreaOCDEPrincipal())) {
			isOK = false;
			generarMsg(2, "Debe indicar el Área OCDE Pprincipal.");
		}
		if (esCadenaVacia(getSemilleroActual().getSubAreaOCDEPrincipal())) {
			isOK = false;
			generarMsg(2, "Debe indicar el Sub-Área OCDE Principal.");
		}
		if (getSemilleroActual().getAreasOCDESecundarias().isEmpty()) {
			isOK = false;
			generarMsg(2, "Debe indicar al menos una Área OCDE Secundaria.");
		} else {
			for (SemilleroAreaOCDE item : getSemilleroActual().getAreasOCDESecundarias()) {
				if (item.getSubAreaOCDE().getIdentificador().getTipo()
						.equals(getSemilleroActual().getSubAreaOCDEPrincipal())) {
					isOK = false;
					generarMsg(2, "El Sub-Área OCDE Principal esta definido como Sub-Área OCDE Secundaria.");
					break;
				}
			}
		}
		if (esCadenaVacia(getAgendaPrincipal())) {
			isOK = false;
			generarMsg(2, "Debe indicar la Agenda de conocimiento Principal.");
		}
		if (getSemilleroActual().getAgendasSecundarias().isEmpty()) {
			isOK = false;
			generarMsg(2, "Debe indicar al menos una Agenda de conocimiento Secundaria.");
		} else {
			for (SemilleroAgenda item : getSemilleroActual().getAgendasSecundarias()) {
				if (item.getAgenda().getId().toString().equals(getAgendaPrincipal())) {
					isOK = false;
					generarMsg(2,
							"La Agenda de conocimiento Principal esta definido como Agenda de conocimiento Secundaria.");
					break;
				}
			}
		}
		if (esCadenaVacia(getSemilleroActual().getObjetivoDesarrolloSosteniblePrincipal())) {
			isOK = false;
			generarMsg(2, "Debe indicar el Objetivo de Desarrollo Sostenible Principal.");
		}
		if (esCadenaVacia(getSemilleroActual().getObjetivoDesarrolloSostenibleSecundario())) {
			isOK = false;
			generarMsg(2, "Debe indicar el Objetivo de Desarrollo Sostenible Secundario.");
		} else if (getSemilleroActual().getObjetivoDesarrolloSostenibleSecundario()
				.equals(getSemilleroActual().getObjetivoDesarrolloSosteniblePrincipal())) {
			isOK = false;
			generarMsg(2, "El Objetivo de Desarrollo Sostenible Principal se encuentra vinculado como Secundario.");
		}
		if (getSemilleroActual().getLineasInvestigacion().isEmpty()) {
			isOK = false;
			generarMsg(2, "Debe indicar al menos una Línea de Investigación.");
		}
		return isOK;
	}

	public void eliminarLinea() {
		for (SemilleroLinea item : getSemilleroActual().getLineasInvestigacion()) {
			if (item.getLinea().getNombre().equals(lineaEliminar.getLinea().getNombre())) {
				getSemilleroActual().getLineasInvestigacion().remove(lineaEliminar);
				break;
			}
		}
		servicioGeneral.eliminarObjeto(lineaEliminar);
	}

	public void adicionarLinea() {
		if (lineaSeleccionada.equals("")) {
			generarMsg(2, "Debe escoger una línea de investigación.");
		} else {
			lineaSeleccionada = lineaSeleccionada.trim();
			SemilleroLinea ls = new SemilleroLinea();
			ls.setSemillero(getSemilleroActual());
			ls.setLinea(servicioLineaInvestigacion.adicionarLinea(lineaSeleccionada));
			for (SemilleroLinea item : getSemilleroActual().getLineasInvestigacion()) {
				if (item.getLinea().getNombre().equals(lineaSeleccionada)) {
					generarMsg(2, "La línea de investigación ya fue asociada al semillero.");
					return;
				}
			}
			getSemilleroActual().getLineasInvestigacion().add(ls);
			lineaSeleccionada = "";
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private void cargarPosiblesLineas() {
		List<LineaInvestigacion> lineasInvestigacion = servicioGeneral
				.obtenerListaObjetosOrdenadosAsc(new LineaInvestigacion(), "nombre");
		if (lineasInvestigacion != null) {
			Iterator<LineaInvestigacion> i = lineasInvestigacion.iterator();
			lineasInvestigacionItem = new ArrayList<SelectItem>();
			while (i.hasNext()) {
				LineaInvestigacion li = i.next();
				lineasInvestigacionItem.add(new SelectItem(li.getNombre(), li.getNombre()));
			}
		}
	}

	public void eliminarAgenda() {
		getSemilleroActual().getAgendasSecundarias().remove(agendaSecundariaEliminar);
		servicioGeneral.eliminarObjeto(agendaSecundariaEliminar);
	}

	public void agregarAgenda() {
		if (agendaSecundariaSeleccionada != null && !agendaSecundariaSeleccionada.equals("")) {
			Long idNuevo = Long.parseLong(agendaSecundariaSeleccionada);
			if (getAgendaPrincipal().equals(idNuevo)) {
				generarMsg(2, "La agenda de conocimiento secundaria a añadir ya está asignada como principal.");
			} else {
				SemilleroAgenda sa = new SemilleroAgenda();
				sa.setSemillero(getSemilleroActual());
				for (PosibleAgendaGrupo pag : listaPosiblesAgendas) {
					if (pag.getId().equals(idNuevo)) {
						sa.setAgenda(pag);
						break;
					}
				}
				getSemilleroActual().getAgendasSecundarias().add(sa);
			}
		} else {
			generarMsg(2, "Debe seleccionar la agenda de conocimiento secundaria a añadir.");
		}
	}

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
	}

	public void cambiarSubAreaODCE() {
		setSubAreaOCDEItems(
				(ArrayList<SelectItem>) cargarSubareaTematica(getSemilleroActual().getAreaOCDEPrincipal(), true));
	}

	public List<SelectItem> cargarSubareaTematica(String areaCiencia, boolean esPrimaria) {
		String consulta = "select dd from Dominio d, "
				+ "DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='SUB_AREA_CIENCIA_FM' and dd.estado = '"
				+ areaCiencia + "' order by dd.descripcion";
		List<DominioDetalle> listaSubAreaCiencia = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);
		if (esPrimaria) {
			this.setListaSubAreaCiencia(listaSubAreaCiencia);
		} else {
			setListaSubAreaOCDESecundaria(listaSubAreaCiencia);
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

	public void cambiarSubAreaODCESecundaria() {
		subAreaOCDESecundariaItems = (ArrayList<SelectItem>) cargarSubareaTematica(getAreaOCDESecundaria(), false);
	}

	private void cargarInformacionAreasOCDESecundarias() {
		if (semilleroActual.getAreasOCDESecundarias() != null) {
			Iterator<SemilleroAreaOCDE> i = semilleroActual.getAreasOCDESecundarias().iterator();
			while (i.hasNext()) {
				SemilleroAreaOCDE areaTematicaGrupo = i.next();
				Iterator<DominioDetalle> j = listaAreaOCDE.iterator();
				while (j.hasNext()) {
					DominioDetalle dominioDetalle = j.next();
					if (areaTematicaGrupo.getSubAreaOCDE().getEstado()
							.equals(dominioDetalle.getIdentificador().getTipo())) {
						areaTematicaGrupo.setAreaOCDE(dominioDetalle);
					}
				}
			}
		}

	}

	public void agregarAreaOCDESecundaria() {
		if (!areaOCDESecundaria.equals("") && !subAreaOCDESecundaria.equals("")) {
			if (areaOCDESecundaria.equals(getSemilleroActual().getAreaOCDEPrincipal())
					&& subAreaOCDESecundaria.equals(getSemilleroActual().getSubAreaOCDEPrincipal())) {
				generarMsg(2, "El área OCDE seleccionada ya se encuentra registrada como principal.");
			} else {
				DominioDetalle areaSec = null;
				Iterator<DominioDetalle> k = listaAreaOCDE.iterator();
				while (k.hasNext()) {
					DominioDetalle dominioDetalle = k.next();
					if (dominioDetalle.getIdentificador().getTipo().equals(areaOCDESecundaria)) {
						areaSec = dominioDetalle;
						break;
					}
				}
				DominioDetalle subAreaSec = null;
				Iterator<DominioDetalle> j = listaSubAreaOCDESecundaria.iterator();
				while (j.hasNext()) {
					DominioDetalle dominioDetalle = j.next();
					if (dominioDetalle.getIdentificador().getTipo().equals(subAreaOCDESecundaria)) {
						subAreaSec = dominioDetalle;
						break;
					}
				}
				if (subAreaSec != null && areaSec != null) {
					SemilleroAreaOCDE ocdeSec = new SemilleroAreaOCDE();
					ocdeSec.setSemillero(getSemilleroActual());
					ocdeSec.setAreaOCDE(areaSec);
					ocdeSec.setSubAreaOCDE(subAreaSec);
					boolean areaYaAgregada = false;
					if (semilleroActual.getAreasOCDESecundarias() != null) {
						Iterator<SemilleroAreaOCDE> i = semilleroActual.getAreasOCDESecundarias().iterator();
						while (i.hasNext()) {
							SemilleroAreaOCDE saos = i.next();
							if (saos.getSubAreaOCDE().getIdentificador().getTipo()
									.equals(ocdeSec.getSubAreaOCDE().getIdentificador().getTipo())) {
								areaYaAgregada = true;
							}
						}
					}
					if (!areaYaAgregada) {
						servicioGeneral.guardarObjeto(ocdeSec);
						semilleroActual.getAreasOCDESecundarias().add(ocdeSec);
					} else {
						generarMsg(2, "El área OCDE seleccionada ya se encuentra registrada como secundaria.");
					}
				} else {
					generarMsg(2, "El área OCDE seleccionada no existe.");
				}
			}
		} else {
			generarMsg(2, "Debe seleccionar el área y sub-área OCDE.");
		}
	}

	public void eliminarArea() {
		semilleroActual.getAreasOCDESecundarias().remove(areaEliminar);
		servicioGeneral.eliminarObjeto(areaEliminar);
	}

	private void cargarAreasOCDE() {
		String consulta = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='AREA_CIENCIA'  order by dd.descripcion";
		listaAreaOCDE = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);
		areaOCDEItems = new ArrayList<SelectItem>();
		if (listaAreaOCDE.size() > 0) {
			for (int i = 0; i < listaAreaOCDE.size(); i++) {
				DominioDetalle dominio = listaAreaOCDE.get(i);
				areaOCDEItems.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
			}
		}
	}

	public ArrayList<SelectItem> getAreaOCDEItems() {
		return areaOCDEItems;
	}

	public ArrayList<SelectItem> getSubAreaOCDEItems() {
		return subAreaOCDEItems;
	}

	public List<DominioDetalle> getListaSubAreaCiencia() {
		return listaSubAreaCiencia;
	}

	public List<DominioDetalle> getListaSubAreaOCDESecundaria() {
		return listaSubAreaOCDESecundaria;
	}

	public String getLinkLineas() {
		return linkLineas;
	}

	public String getAreaOCDESecundaria() {
		return areaOCDESecundaria;
	}

	public ArrayList<SelectItem> getSubAreaOCDESecundariaItems() {
		return subAreaOCDESecundariaItems;
	}

	public String getSubAreaOCDESecundaria() {
		return subAreaOCDESecundaria;
	}

	public void setAreaOCDEItems(ArrayList<SelectItem> areaCienciaItems) {
		this.areaOCDEItems = areaCienciaItems;
	}

	public void setSubAreaOCDEItems(ArrayList<SelectItem> subAreaCienciaItems) {
		this.subAreaOCDEItems = subAreaCienciaItems;
	}

	public void setListaSubAreaCiencia(List<DominioDetalle> listaSubAreaCiencia) {
		this.listaSubAreaCiencia = listaSubAreaCiencia;
	}

	public void setListaSubAreaOCDESecundaria(List<DominioDetalle> listaSubAreaCienciaSecundaria) {
		this.listaSubAreaOCDESecundaria = listaSubAreaCienciaSecundaria;
	}

	public void setLinkLineas(String linkLineas) {
		this.linkLineas = linkLineas;
	}

	public void setAreaOCDESecundaria(String areaCienciaSecundaria) {
		this.areaOCDESecundaria = areaCienciaSecundaria;
	}

	public void setSubAreaOCDESecundariaItems(ArrayList<SelectItem> subAreaCienciaSecundariaItems) {
		this.subAreaOCDESecundariaItems = subAreaCienciaSecundariaItems;
	}

	public void setSubAreaOCDESecundaria(String subAreaCienciaSecundaria) {
		this.subAreaOCDESecundaria = subAreaCienciaSecundaria;
	}

	public List<DominioDetalle> getListaAreaOCDE() {
		return listaAreaOCDE;
	}

	public void setListaAreaOCDE(List<DominioDetalle> listaAreaOCDE) {
		this.listaAreaOCDE = listaAreaOCDE;
	}

	public SemilleroAreaOCDE getAreaEliminar() {
		return areaEliminar;
	}

	public void setAreaEliminar(SemilleroAreaOCDE areaEliminar) {
		this.areaEliminar = areaEliminar;
	}

	public List<PosibleAgendaGrupo> getListaPosiblesAgendas() {
		return listaPosiblesAgendas;
	}

	public void setListaPosiblesAgendas(List<PosibleAgendaGrupo> listaPosiblesAgendas) {
		this.listaPosiblesAgendas = listaPosiblesAgendas;
	}

	public ArrayList<SelectItem> getListaPosiblesAgendasItem() {
		return listaPosiblesAgendasItem;
	}

	public void setListaPosiblesAgendasItem(ArrayList<SelectItem> listaPosiblesAgendasItem) {
		this.listaPosiblesAgendasItem = listaPosiblesAgendasItem;
	}

	public Semillero getSemilleroActual() {
		return semilleroActual;
	}

	public void setSemilleroActual(Semillero semilleroActual) {
		this.semilleroActual = semilleroActual;
	}

	public String getAgendaSecundariaSeleccionada() {
		return agendaSecundariaSeleccionada;
	}

	public void setAgendaSecundariaSeleccionada(String agendaSecundariaSeleccionada) {
		this.agendaSecundariaSeleccionada = agendaSecundariaSeleccionada;
	}

	public SemilleroAgenda getAgendaSecundariaEliminar() {
		return agendaSecundariaEliminar;
	}

	public void setAgendaSecundariaEliminar(SemilleroAgenda agendaSecundariaEliminar) {
		this.agendaSecundariaEliminar = agendaSecundariaEliminar;
	}

	public SelectItem[] getObjetivosDesarrolloSostenibleItems() {
		return objetivosDesarrolloSostenibleItems;
	}

	public void setObjetivosDesarrolloSostenibleItems(SelectItem[] objetivosDesarrolloSostenibleItems) {
		this.objetivosDesarrolloSostenibleItems = objetivosDesarrolloSostenibleItems;
	}

	public String getLinkObjetivosDesarrolloSostenible() {
		return linkObjetivosDesarrolloSostenible;
	}

	public void setLinkObjetivosDesarrolloSostenible(String linkObjetivosDesarrolloSostenible) {
		this.linkObjetivosDesarrolloSostenible = linkObjetivosDesarrolloSostenible;
	}

	public String getLineaSeleccionada() {
		return lineaSeleccionada;
	}

	public void setLineaSeleccionada(String lineaSeleccionada) {
		this.lineaSeleccionada = lineaSeleccionada;
	}

	public ArrayList<SelectItem> getLineasInvestigacionItem() {
		return lineasInvestigacionItem;
	}

	public void setLineasInvestigacionItem(ArrayList<SelectItem> lineasInvestigacionItem) {
		this.lineasInvestigacionItem = lineasInvestigacionItem;
	}

	public SemilleroLinea getLineaEliminar() {
		return lineaEliminar;
	}

	public void setLineaEliminar(SemilleroLinea lineaEliminar) {
		this.lineaEliminar = lineaEliminar;
	}

	public String getAgendaPrincipal() {
		return agendaPrincipal;
	}

	public void setAgendaPrincipal(String agendaPrincipal) {
		this.agendaPrincipal = agendaPrincipal;
	}

}
