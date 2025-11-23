package co.edu.unal.hermes.vista.grupos.ediciongrupos;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.CategoriaGrupo;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoGrupo;
import co.edu.unal.hermes.modelo.EstadoGrupoColciencias;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.GrupoInstitucion;
import co.edu.unal.hermes.modelo.GrupoIntersedes;
import co.edu.unal.hermes.modelo.GrupoLaboratorio;
import co.edu.unal.hermes.modelo.HistoricoCambioIntegrantes;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.PlanEstudios;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroEstado;
import co.edu.unal.hermes.modelo.SemilleroGrupo;
import co.edu.unal.hermes.modelo.SemilleroLaboratorio;
import co.edu.unal.hermes.modelo.SemilleroObjetivo;
import co.edu.unal.hermes.modelo.SemilleroSede;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.utils.Util;
import sun.awt.image.ImageFormatException;

/**
 * The Class ManejadorEdicionGrupo.
 */
public class ManejadorEdicionGrupo extends ManejadorBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7246183668682350818L;

	/** The Constant REGLA_NAVEGACION_EDICION_GRUPO. */
	public static final String REGLA_NAVEGACION_EDICION_GRUPO = "editarGrupo";

	/** The grupo actual. */
	private Grupo grupoActual;

	/** The dependencia seleccionada. */
	private Dependencia dependenciaSeleccionada;

	/** The intesede seleccionada. */
	private GrupoIntersedes intesedeSeleccionada;

	/** The plan estudios seleccionado. */
	private PlanEstudios planEstudiosSeleccionado;

	/** The lista dependencia. */
	private List<Dependencia> listaDependencia;

	/** The lista dependencia aliada. */
	private List<Dependencia> listaDependenciaAliada;

	/** The lista sede. */
	private List<Sede> listaSede;

	/** The lista planes. */
	private List<PlanEstudios> listaPlanes;

	/** The lista sedes. */
	private List<GrupoIntersedes> listaSedes;

	/** The lista sedes eliminadas. */
	private List<GrupoIntersedes> listaSedesEliminadas;

	/** The dependencia item. */
	private SelectItem[] dependenciaItem;

	/** The dependencia aliada item. */
	private SelectItem[] dependenciaAliadaItem;

	/** The sede item. */
	private SelectItem[] sedeItem;

	/** The planes item. */
	private SelectItem[] planesItem;

	/** The es intersedes. */
	private String esIntersedes;

	/** The es interfacultades. */
	private String esInterfacultades;

	/** The dependencia nueva. */
	private String dependenciaNueva;

	/** The sede nueva. */
	private String sedeNueva;

	/** The plan nuevo. */
	private String planNuevo;

	/** The bandera tabla. */
	boolean banderaTabla = false;

	/** The lista planes. */
	private List<SemilleroGrupo> listaSemilleros;
	private List<SemilleroGrupo> listaSemillerosBorrados;
	private List<SemilleroGrupo> listaSemillerosHistorico;
	private List<GrupoIntersedes> listaSedesHistorico;

	public List<SemilleroGrupo> getListaSemilleros() {
		return listaSemilleros;
	}

	public void setListaSemilleros(List<SemilleroGrupo> listaSemilleros) {
		this.listaSemilleros = listaSemilleros;
	}

	/** The path. */
	// Variables necesarias para la gestión de las imagenes del grupo
	String path = RUTA_ARCHIVOS + File.separator + "HER_GRUPO" + File.separator;

	/** The archivo ya seleccionado. */
	boolean archivoYaSeleccionado = false;

	/** The imagen. */
	private StreamedContent imagen;

	/** The archivo existe. */
	boolean archivoExiste = true;

	/** The imagen recortada. */
	boolean imagenRecortada = false;

	/** The cropped image. */
	private CroppedImage croppedImage;

	/** The nombre imagen. */
	private String nombreImagenGrupo;

	/** The servlet context. */
	ServletContext servletContext;

	/** The actual. */
	File actual;

	/** The grupo nuevo. */
	private boolean grupoNuevo;

	/** The nombre archivo temporal. */
	String nombreArchivoTemporal;

	private boolean lider;

	private SelectItem[] fuentesExternasItem;
	private String institucionSeleccionada;
	private String sedeFiltroLabs;
	private SelectItem[] labsItem;
	private GrupoInstitucion institucionEliminar;
	private String labSeleccionado;
	private GrupoLaboratorio labEliminar;
	private String semilleroSeleccionado;
	private String sedeSemilleros;
	private SelectItem[] semItem;
	private List<SelectItem> facultadItemSem;
	private SemilleroGrupo semEliminar;
	private String facultadSelSem;
	private String semSeleccionado;

	/**
	 * Instantiates a new manejador edicion grupo.
	 */
	public ManejadorEdicionGrupo() {

		grupoActual = (Grupo) sesion.getAttribute(Grupo.VARIABLE_SESION_GRUPO);
		listaSemillerosBorrados = new ArrayList<SemilleroGrupo>();

		if (grupoActual == null || (grupoActual != null && grupoActual.getId() == null)) {
			grupoActual = new Grupo();
			setGrupoNuevo(true);

			// Se asigna estado ingresando
			grupoActual.setEstadoGrupo(new EstadoGrupo());
			grupoActual.getEstadoGrupo().setId("I");
			grupoActual.getEstadoGrupo().setNombre("Ingresando");

			// Se asigna estado sin asignar de colciencas
			grupoActual.setEstadoGrupoColciencias(new EstadoGrupoColciencias());
			grupoActual.getEstadoGrupoColciencias().setId("NR");
			grupoActual.getEstadoGrupoColciencias().setNombre("No Registrado");

			// Se asigna dependencia de investigador actual
			InvestigadorInterno investigadorInterno = (InvestigadorInterno) sesion.getAttribute("investigadorInterno");
			grupoActual.setDependencia(investigadorInterno.getDependencia().getFacultad());
			grupoActual.setSede(investigadorInterno.getDependencia().getSede());

			// Se asigna investigador lider
			InvestigadorGrupo investigadorGrupo = new InvestigadorGrupo();
			investigadorGrupo.setInvestigador(investigadorInterno);
			investigadorGrupo.setTipo("L");
			investigadorGrupo.setTipoVinculacion("I");
			grupoActual.adicionarInvestigadorGrupo(investigadorGrupo);

			// Se asigna categoria de colciencias sin registrar
			CategoriaGrupo categoriaGrupo = new CategoriaGrupo();
			categoriaGrupo.setId("S");
			categoriaGrupo.setNombre("Sin registrar");
			grupoActual.setCategoria(categoriaGrupo);

			// Se asigna menu 0
			grupoActual.setEstadoMenu(0);
		}

		if (grupoActual.getDependencia() == null
				|| (grupoActual.getDependencia() != null && grupoActual.getDependencia().getId() == null)) {
			// Se asigna una dependencia en blanco
			Dependencia dependencia = new Dependencia();
			grupoActual.setDependencia(dependencia);
		}

		cargarSedes();

		cargarDependencias();

		cargarDependenciasAliadas();

		cargarPlanes();

		cargarDatosGrupo();

		cargaInicialImagen();

		servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

		setLider(validarPermisos());

		cargarInstituciones();
		actualizarLabs();
		cargarSemilleros();
	}

	public void eliminarlab() {
		for (GrupoLaboratorio gl : grupoActual.getLaboratorios()) {
			if (gl.getLaboratorio().equals(labEliminar.getLaboratorio())) {
				gl.setFechaBorrado(getToday());
				gl.setResponsableRetiro(personaActual);
				servicioGeneral.guardarObjeto(gl);
			}
			grupoActual.getLaboratorios().remove(gl);
			break;
		}
	}

	public void adicionarLab() {
		if (StringUtils.isNotEmpty(getLabSeleccionado())) {
			boolean existe = false;
			for (GrupoLaboratorio s : grupoActual.getLaboratorios()) {
				if (s.getLaboratorio().getId().toString().equals(getLabSeleccionado())) {
					existe = true;
					generarMsg(2, "El Laboratorio ya se encuentra vinculado.");
					break;
				}
			}
			if (!existe) {
				Laboratorio l = servicioGeneral
						.obtenerObjetos(Laboratorio.class, "from Laboratorio s where s.id=" + getLabSeleccionado())
						.get(0);
				GrupoLaboratorio gl = new GrupoLaboratorio();
				gl.setLaboratorio(l);
				gl.setGrupo(grupoActual);
				gl.setFechaRegistro(getToday());
				grupoActual.getLaboratorios().add(gl);
			}
		} else {
			generarMsg(2, "Por favor, indicar un laboratorio.");
		}
	}

	public void eliminarInstitucion() {
		for (GrupoInstitucion gi : grupoActual.getInstituciones()) {
			if (gi.getInstitucion().equals(institucionEliminar.getInstitucion())) {
				servicioGeneral.eliminarObjeto(gi);
			}
			grupoActual.getInstituciones().remove(gi);
			break;
		}
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

	public void adicionarInstitucion() {
		if (StringUtils.isNotEmpty(getInstitucionSeleccionada())) {
			boolean existe = false;
			for (GrupoInstitucion gi : grupoActual.getInstituciones()) {
				if (gi.getInstitucion().getId().toString().equals(getInstitucionSeleccionada())) {
					existe = true;
					generarMsg(2, "La sede ya se encuentra vinculado.");
					break;
				}
			}
			if (!existe) {
				FuenteFinanciacion ff = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class,
						"from FuenteFinanciacion ff where ff.id=" + getInstitucionSeleccionada()).get(0);
				GrupoInstitucion gi = new GrupoInstitucion();
				gi.setInstitucion(ff);
				gi.setGrupo(grupoActual);
				grupoActual.getInstituciones().add(gi);
			}
		} else {
			generarMsg(2, "Por favor, indicar una sede.");
		}
	}

	private void cargarInstituciones() {
		String hql = "select ff from FuenteFinanciacion ff where ff.internaExterna like 'E' and ff.descripcion not like '%CODIGO%' and ff.observaciones = 'ENT_REG_PRY' and ff.quipu = 'S' order by ff.descripcion)";
		List<FuenteFinanciacion> lista = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class, hql);
		fuentesExternasItem = new SelectItem[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			FuenteFinanciacion d = (FuenteFinanciacion) lista.get(i);
			String nombre = d.getDescripcion().toUpperCase();
			fuentesExternasItem[i] = new SelectItem(d.getId(), nombre);
		}
	}

	public void actualizarLabs() {
		List<Laboratorio> listaLabs = servicioGeneral.obtenerObjetos(Laboratorio.class,
				"from Laboratorio s where s.sede.id='" + getSedeFiltroLabs() + "' and s.activo = '1' order by s.id");
		labsItem = new SelectItem[listaLabs.size()];
		for (int i = 0; i < listaLabs.size(); i++) {
			Laboratorio lab = listaLabs.get(i);
			labsItem[i] = new SelectItem(lab.getId(), lab.getNombre());
		}
	}

	/**
	 * Actualizar dependencia.
	 */
	public void actualizarDependencia() {
		cargarDependencias();
		grupoActual.setDependencia(new Dependencia());
	}

	/**
	 * Adicionar dependencia.
	 */
	public void adicionarDependencia() {
		if (StringUtils.isNotEmpty(dependenciaNueva)
				&& !dependenciaNueva.equals(grupoActual.getDependencia().getId())) {
			grupoActual.adicionarDependencia(buscarDependencia(dependenciaNueva));
		}
	}

	/**
	 * Adicionar plan.
	 */
	public void adicionarPlan() {
		if (StringUtils.isNotEmpty(planNuevo)) {
			grupoActual.adicionarPlan(buscarPlan(planNuevo));
		}
	}

	/**
	 * Adicionar sede.
	 */
	public void adicionarSede() {
		if (StringUtils.isNotEmpty(sedeNueva) && buscarSede(sedeNueva) != null) {
			Sede sede = buscarSede(sedeNueva);
			boolean existe = false;
			if (listaSedes == null) {
				listaSedes = new ArrayList<GrupoIntersedes>();
			}
			Iterator<GrupoIntersedes> i = listaSedes.iterator();
			while (i.hasNext()) {
				GrupoIntersedes grupoIntersedes = i.next();
				if (grupoIntersedes.getSede().getId().toString().equals(sedeNueva)) {
					existe = true;
					break;
				}
			}
			if (!existe) {
				GrupoIntersedes grupoIntersedes = new GrupoIntersedes();
				grupoIntersedes.setGrupo(grupoActual);
				grupoIntersedes.setSede(sede);
				grupoIntersedes.setFechaRegistro(getToday());
				grupoIntersedes.setResponsableRegistro(personaActual);
				listaSedes.add(grupoIntersedes);
			}
		}
	}

	/**
	 * Asignar dependencia grupo.
	 */
	private void asignarDependenciaGrupo() {
		// Guardar nueva dependencia del grupo
		if (StringUtils.isNotEmpty(grupoActual.getDependencia().getId())) {
			grupoActual.setDependencia(buscarDependencia(grupoActual.getDependencia().getId()));
			grupoActual.setSede(grupoActual.getDependencia().getSede());
		}
	}

	/**
	 * Buscar dependencia.
	 *
	 * @param id the id
	 * @return the dependencia
	 */
	private Dependencia buscarDependencia(String id) {
		if (StringUtils.isNotEmpty(id)) {
			Dependencia d;
			int i = 0;
			while (i < listaDependenciaAliada.size()) {
				d = listaDependenciaAliada.get(i);
				if (id.equals(d.getId())) {
					return d;
				}
				i = i + 1;
			}
		}
		return null;
	}

	/**
	 * Buscar plan.
	 *
	 * @param id the id
	 * @return the plan estudios
	 */
	private PlanEstudios buscarPlan(String id) {
		if (StringUtils.isNotEmpty(id)) {
			PlanEstudios planEstudios;
			int i = 0;
			while (i < listaPlanes.size()) {
				planEstudios = listaPlanes.get(i);
				if (id.equals(planEstudios.getId())) {
					return planEstudios;
				}
				i = i + 1;
			}
		}
		return null;
	}

	/**
	 * Buscar sede.
	 *
	 * @param sedeId the sede id
	 * @return the sede
	 */
	public Sede buscarSede(String sedeId) {
		Iterator<Sede> i = listaSede.iterator();
		while (i.hasNext()) {
			Sede sede = i.next();
			if (sede.getId().toString().equals(sedeId)) {
				return sede;
			}
		}
		return null;
	}

	/**
	 * Carga inicial imagen.
	 */
	private void cargaInicialImagen() {
		if (grupoActual.getId() != null) {
			nombreImagenGrupo = grupoActual.getId().toString();
		} else {
			nombreImagenGrupo = "imagenTemporal" + Util.getRandomImageName();
		}

		actual = new File(path + nombreImagenGrupo + ".jpg");

		archivoExiste = actual.exists();
	}

	/**
	 * Cargar datos grupo.
	 */
	private void cargarDatosGrupo() {
		if (grupoActual.getInterfacultades() != null) {
			esInterfacultades = grupoActual.getInterfacultades();
		} else {
			esInterfacultades = "N";
		}

		if (grupoActual.getIntersedes() != null) {
			esIntersedes = grupoActual.getIntersedes();
		} else {
			esIntersedes = "N";
		}
	}

	/**
	 * Cargar dependencias.
	 */
	private void cargarDependencias() {
		listaDependencia = servicioGeneral.obtenerListaObjetosWhere(Dependencia.class,
				"where d.esFacultad='Y' and d.estado = 'A' and d.sede.id = '" + grupoActual.getSede().getId() + "' order by d.nombre");
		dependenciaItem = new SelectItem[listaDependencia.size()];
		for (int i = 0; i < listaDependencia.size(); i++) {
			Dependencia td = listaDependencia.get(i);
			dependenciaItem[i] = new SelectItem(td.getId(), td.getNombre() + " (" + td.getSede().getNombre() + ")");
		}
	}

	/**
	 * Cargar dependencias aliadas.
	 */
	private void cargarDependenciasAliadas() {
		listaDependenciaAliada = servicioGeneral.obtenerListaObjetosWhere(Dependencia.class,
				"where d.esFacultad='Y' and d.estado = 'A' order by d.nombre");
		dependenciaAliadaItem = new SelectItem[listaDependenciaAliada.size()];
		for (int i = 0; i < listaDependenciaAliada.size(); i++) {
			Dependencia td = listaDependenciaAliada.get(i);
			dependenciaAliadaItem[i] = new SelectItem(td.getId(),
					td.getNombre() + " (" + td.getSede().getNombre() + ")");
		}
		dependenciaNueva = "";
	}

	/**
	 * Cargar planes.
	 */
	private void cargarPlanes() {
		listaPlanes = servicioGeneral.obtenerObjetos(PlanEstudios.class, "from PlanEstudios");
		planesItem = new SelectItem[listaPlanes.size()];
		for (int i = 0; i < listaPlanes.size(); i++) {
			PlanEstudios pe = listaPlanes.get(i);
			planesItem[i] = new SelectItem(pe.getId(), pe.getNombre());
		}
		planNuevo = "";
	}

	/**
	 * Cargar sedes.
	 */
	private void cargarSedes() {
		listaSede = servicioGeneral.obtenerObjetos(Sede.class,
				"from Sede s where s.id not in ('" + Sede.NIVEL_NACIONAL + "') order by s.nombre");
		sedeItem = new SelectItem[listaSede.size()];
		for (int i = 0; i < listaSede.size(); i++) {
			Sede sede = listaSede.get(i);
			sedeItem[i] = new SelectItem(sede.getId(), sede.getNombre());
		}
		if (grupoActual.getSede() == null || grupoActual.getSede().getId() == null) {
			Sede sede = new Sede();
			grupoActual.setSede(sede);
		}

		if (grupoActual.getId() != null) {
			listaSedes = servicioGeneral.obtenerObjetos(GrupoIntersedes.class,
					"from GrupoIntersedes gi where gi.grupo.id = " + "'" + grupoActual.getId() + "' and gi.fechaBorrado is null");
		}

	}

	/**
	 * Cerrar crop.
	 */
	public void cerrarCrop() {
		archivoYaSeleccionado = false;
	}

	/**
	 * Crop.
	 * 
	 * @throws ImageFormatException
	 */
	public void crop() throws ImageFormatException {

		String newFilePathDestino = servletContext.getRealPath("") + File.separator + CARPETA_TEMPORAL_IMAGENES
				+ File.separator + nombreImagenGrupo + ".jpg";

		imagenRecortada = recortarImagen(croppedImage, newFilePathDestino, servletContext);

		if (!imagenRecortada) {
			mensajeError("Ha ocurrido un problema en la edición de la imagen cargada.");
		}
	}

	/**
	 * Eliminar definitivamente sedes eliminadas.
	 *
	 * @param lista the lista
	 */
	private void eliminarDefinitivamenteSedesEliminadas(List<GrupoIntersedes> lista) {
		if (!esListaVacia(lista)) {
			Iterator<GrupoIntersedes> i = lista.iterator();
			while (i.hasNext()) {
				GrupoIntersedes grupoIntersedes = i.next();
				if (grupoIntersedes.getId() != null) {
					grupoIntersedes.setFechaBorrado(getToday());
					grupoIntersedes.setResponsableRetiro(personaActual);
					servicioGeneral.guardarObjeto(grupoIntersedes);
				}
			}
			lista.clear();
		}
	}

	/**
	 * Eliminar dependencia.
	 */
	public void eliminarDependencia() {
		Dependencia dependencia = dependenciaSeleccionada;
		grupoActual.borrarDependencia(dependencia);
	}

	/**
	 * Eliminar plan.
	 */
	public void eliminarPlan() {
		PlanEstudios plan = planEstudiosSeleccionado;
		grupoActual.borrarPlan(plan);
	}

	/**
	 * Eliminar sede.
	 */
	public void eliminarSede() {
		listaSedes.remove(intesedeSeleccionada);
		if (intesedeSeleccionada.getId() != null) {
			if (listaSedesEliminadas == null) {
				listaSedesEliminadas = new ArrayList<GrupoIntersedes>();
			}
			listaSedesEliminadas.add(intesedeSeleccionada);
		}
	}

	/**
	 * Gets the actual.
	 *
	 * @return the actual
	 */
	public File getActual() {
		return actual;
	}

	/**
	 * Gets the cropped image.
	 *
	 * @return the cropped image
	 */
	public CroppedImage getCroppedImage() {
		return croppedImage;
	}

	/**
	 * Gets the dependencia aliada item.
	 *
	 * @return the dependencia aliada item
	 */
	public SelectItem[] getDependenciaAliadaItem() {
		return dependenciaAliadaItem;
	}

	/**
	 * Gets the dependencia item.
	 *
	 * @return the dependencia item
	 */
	public SelectItem[] getDependenciaItem() {
		return dependenciaItem;
	}

	/**
	 * Gets the dependencia nueva.
	 *
	 * @return the dependencia nueva
	 */
	public String getDependenciaNueva() {
		return dependenciaNueva;
	}

	/**
	 * Gets the dependencia seleccionada.
	 *
	 * @return the dependencia seleccionada
	 */
	public Dependencia getDependenciaSeleccionada() {
		return dependenciaSeleccionada;
	}

	/**
	 * Gets the enlace hermes.
	 *
	 * @return the enlace hermes
	 */
	public String getEnlaceHermes() {
		String viewId = "/pages/Consultas/Grupo.xhtml";
		viewId = "http://www.hermes.unal.edu.co" + viewId + '?' + "idGrupo" + "=" + grupoActual.getId();
		return viewId;
	}

	/**
	 * Gets the es interfacultades.
	 *
	 * @return the es interfacultades
	 */
	public String getEsInterfacultades() {
		return esInterfacultades;
	}

	/**
	 * Gets the es intersedes.
	 *
	 * @return the es intersedes
	 */
	public String getEsIntersedes() {
		return esIntersedes;
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
	 * Gets the imagen.
	 *
	 * @return the imagen
	 */
	public StreamedContent getImagen() {
		try {
			imagen = new DefaultStreamedContent(
					new ByteArrayInputStream(org.apache.commons.io.FileUtils.readFileToByteArray(actual)), "image/png");
		} catch (IOException e) {
			return null;
		}
		return imagen;
	}

	/**
	 * Gets the intesede seleccionada.
	 *
	 * @return the intesede seleccionada
	 */
	public GrupoIntersedes getIntesedeSeleccionada() {
		return intesedeSeleccionada;
	}

	/**
	 * Gets the lista sede.
	 *
	 * @return the lista sede
	 */
	public List<Sede> getListaSede() {
		return listaSede;
	}

	/**
	 * Gets the lista sedes.
	 *
	 * @return the lista sedes
	 */
	public List<GrupoIntersedes> getListaSedes() {
		return listaSedes;
	}

	/**
	 * Gets the nombre archivo temporal.
	 *
	 * @return the nombre archivo temporal
	 */
	public String getNombreArchivoTemporal() {
		return nombreArchivoTemporal;
	}

	/**
	 * Gets the nombre imagen.
	 *
	 * @return the nombre imagen
	 */
	public String getNombreImagen() {
		return nombreImagenGrupo;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Gets the planes item.
	 *
	 * @return the planes item
	 */
	public SelectItem[] getPlanesItem() {
		return planesItem;
	}

	/**
	 * Gets the plan estudios seleccionado.
	 *
	 * @return the plan estudios seleccionado
	 */
	public PlanEstudios getPlanEstudiosSeleccionado() {
		return planEstudiosSeleccionado;
	}

	/**
	 * Gets the plan nuevo.
	 *
	 * @return the plan nuevo
	 */
	public String getPlanNuevo() {
		return planNuevo;
	}

	/**
	 * Gets the sede item.
	 *
	 * @return the sede item
	 */
	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	/**
	 * Gets the sede nueva.
	 *
	 * @return the sede nueva
	 */
	public String getSedeNueva() {
		return sedeNueva;
	}

	/**
	 * Gets the servlet context.
	 *
	 * @return the servlet context
	 */
	public ServletContext getServletContext() {
		return servletContext;
	}

	/**
	 * Guardar grupo.
	 */
	public void guardarGrupo() {
		guardarGrupo(false);
	}

	/**
	 * Guardar grupo.
	 *
	 * @param avanzar the avanzar
	 * @return the string
	 */
	public String guardarGrupo(boolean avanzar) {

		if (validacionBasicaGrupo()) {

			grupoActual.setInterfacultades(esInterfacultades);
			grupoActual.setIntersedes(esIntersedes);

			guardarSedes(listaSedes);
			eliminarDefinitivamenteSedesEliminadas(listaSedesEliminadas);
		
			guardarSemilleros();

			if (grupoActual.getDependencia() != null) {
				grupoActual.setSede(grupoActual.getDependencia().getSede());

				// Se actualiza estado menu del grupo
				grupoActual.asignarNuevoEstadoMenu(2);

				servicioGrupo.guardarGrupo(grupoActual);

				// Se guarda imagen del grupo
				if (imagenRecortada && grupoActual.getId() != null) {

					String rutaTemporalOrigen = servletContext.getRealPath("") + File.separator
							+ CARPETA_TEMPORAL_IMAGENES + File.separator + nombreImagenGrupo + ".jpg";

					String rutaDefintinivaDestino = path + grupoActual.getId() + ".jpg";

					// El archivo de imagen fue guardado en una ruta temporal en
					// la sesion, si el grupo se guarda este se pasa a una ruta
					// definitiva en las carpetas del grupo.
					cambiarArchivoUbicacionDisco(rutaTemporalOrigen, rutaDefintinivaDestino);
				}

				sesion.removeAttribute("manejadorMenuFormularioGrupos");
				sesion.removeAttribute("manejadorIntegrantesGrupo");
				sesion.setAttribute("grupo", grupoActual);

				mensajeInfo("La información ha sido guardada correctamente ");

				guardarHistoricoFormularioGrupo(grupoActual, getPersonaActual(), "1");
				if (isGrupoNuevo()) {
					setGrupoNuevo(false);
					cambiosGruposHistorico = "eg,";
					justificacionGrupoHistorico = "Creación inicial de grupo.";
					guardarHistoricoEstadoGrupo(grupoActual, getPersonaActual());
					// MACL: Historico cambio grupo.
					HistoricoCambioIntegrantes hci = new HistoricoCambioIntegrantes();
					hci.setGrupo(grupoActual);
					hci.setFechaIngreso(new Date());
					hci.setIntegrante(grupoActual.getLider().getInvestigador());
					hci.setTipo(InvestigadorGrupo.LIDER);
					hci.setTipoVinculacionGrupo("I");
					hci.setTipoVinculacion(grupoActual.getLider().getInvestigador().getTipoVinculacion());
					hci.setTipoDedicacion(grupoActual.getLider().getInvestigador().getTipoDedicacion());
					hci.setTipoFormacion(grupoActual.getLider().getInvestigador().getTipoFormacion());
					hci.setDependencia(grupoActual.getLider().getInvestigador().getDependencia().getFacultad());
					hci.setSede(grupoActual.getLider().getInvestigador().getDependencia().getFacultad().getSede());
					servicioGeneral.guardarObjeto(hci);
					setLider(validarPermisos());
				}
				if (!lider) {
					notificarCambiosEstLider();
				}
				if (avanzar) {
					return "irEdicionIntegrantes";
				}
			}
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
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<FORM>>", "INFORMACIÓN GENERAL");
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<FECHA>>",
				new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(getToday()));
		Correo mensaje = new Correo();
		mensaje.setOrigen(Correo.CORREO_HERMES);
		// mensaje.adicionarCopiaOculta(Correo.CORREO_HERMES);
		mensaje.setAsunto(correoActual.getAsunto());
		mensaje.setCuerpo(cuerpoCorreo);
		mensaje.adicionarDireccion(grupoActual.getLider().getInvestigador().getEmail());
		servicioCorreo.enviarCorreo(mensaje);
	}

	/**
	 * Guardar sedes.
	 *
	 * @param lista the lista
	 */
	private void guardarSedes(List<GrupoIntersedes> lista) {
		if (!esListaVacia(lista)) {
			Iterator<GrupoIntersedes> i = lista.iterator();
			while (i.hasNext()) {
				GrupoIntersedes grupoIntersedes = i.next();
				if (grupoIntersedes.getId() == null) {
					servicioGeneral.guardarObjeto(grupoIntersedes);
				}
			}
		}
	}
	
	/**
	 * Guardar semilleros.
	 *
	 * @param lista the lista
	 */
	private void guardarSemilleros() {
		if (!esListaVacia(listaSemilleros)) {
			Iterator<SemilleroGrupo> i = listaSemilleros.iterator();
			while (i.hasNext()) {
				SemilleroGrupo semillero = i.next();
				if (semillero.getFechaRegistro() == null) {
					semillero.setFechaRegistro(getToday());
					servicioGeneral.guardarObjeto(semillero);
				}
			}
		}
		
		if (!esListaVacia(listaSemillerosBorrados)) {
			Iterator<SemilleroGrupo> i = listaSemillerosBorrados.iterator();
			while (i.hasNext()) {
				SemilleroGrupo semillero = i.next();
				servicioGeneral.guardarObjeto(semillero);
				
			}
		}
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
	 * Checks if is archivo existe.
	 *
	 * @return true, if is archivo existe
	 */
	public boolean isArchivoExiste() {
		return archivoExiste;
	}

	/**
	 * Checks if is archivo ya seleccionado.
	 *
	 * @return true, if is archivo ya seleccionado
	 */
	public boolean isArchivoYaSeleccionado() {
		return archivoYaSeleccionado;
	}

	/**
	 * Checks if is imagen recortada.
	 *
	 * @return true, if is imagen recortada
	 */
	public boolean isImagenRecortada() {
		return imagenRecortada;
	}

	/**
	 * Sets the actual.
	 *
	 * @param actual the new actual
	 */
	public void setActual(File actual) {
		this.actual = actual;
	}

	/**
	 * Sets the cropped image.
	 *
	 * @param croppedImage the new cropped image
	 */
	public void setCroppedImage(CroppedImage croppedImage) {
		this.croppedImage = croppedImage;
	}

	/**
	 * Sets the dependencia aliada item.
	 *
	 * @param dependenciaAliadaItem the new dependencia aliada item
	 */
	public void setDependenciaAliadaItem(SelectItem[] dependenciaAliadaItem) {
		this.dependenciaAliadaItem = dependenciaAliadaItem;
	}

	/**
	 * Sets the dependencia nueva.
	 *
	 * @param dependenciaNueva the new dependencia nueva
	 */
	public void setDependenciaNueva(String dependenciaNueva) {
		this.dependenciaNueva = dependenciaNueva;
	}

	/**
	 * Sets the dependencia seleccionada.
	 *
	 * @param dependenciaSeleccionada the new dependencia seleccionada
	 */
	public void setDependenciaSeleccionada(Dependencia dependenciaSeleccionada) {
		this.dependenciaSeleccionada = dependenciaSeleccionada;
	}

	/**
	 * Sets the es interfacultades.
	 *
	 * @param esInterfacultades the new es interfacultades
	 */
	public void setEsInterfacultades(String esInterfacultades) {
		this.esInterfacultades = esInterfacultades;
	}

	/**
	 * Sets the es intersedes.
	 *
	 * @param esIntersedes the new es intersedes
	 */
	public void setEsIntersedes(String esIntersedes) {
		this.esIntersedes = esIntersedes;
	}

	/**
	 * Sets the imagen.
	 *
	 * @param imagen the new imagen
	 */
	public void setImagen(StreamedContent imagen) {
		this.imagen = imagen;
	}

	/**
	 * Sets the intesede seleccionada.
	 *
	 * @param intesedeSeleccionada the new intesede seleccionada
	 */
	public void setIntesedeSeleccionada(GrupoIntersedes intesedeSeleccionada) {
		this.intesedeSeleccionada = intesedeSeleccionada;
	}

	/**
	 * Sets the nombre imagen.
	 *
	 * @param nombreImagen the new nombre imagen
	 */
	public void setNombreImagen(String nombreImagen) {
		this.nombreImagenGrupo = nombreImagen;
	}

	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Sets the planes item.
	 *
	 * @param planesItem the new planes item
	 */
	public void setPlanesItem(SelectItem[] planesItem) {
		this.planesItem = planesItem;
	}

	/**
	 * Sets the plan estudios seleccionado.
	 *
	 * @param planEstudiosSeleccionado the new plan estudios seleccionado
	 */
	public void setPlanEstudiosSeleccionado(PlanEstudios planEstudiosSeleccionado) {
		this.planEstudiosSeleccionado = planEstudiosSeleccionado;
	}

	/**
	 * Sets the plan nuevo.
	 *
	 * @param planNuevo the new plan nuevo
	 */
	public void setPlanNuevo(String planNuevo) {
		this.planNuevo = planNuevo;
	}

	/**
	 * Sets the sede nueva.
	 *
	 * @param sedeNueva the new sede nueva
	 */
	public void setSedeNueva(String sedeNueva) {
		this.sedeNueva = sedeNueva;
	}

	/**
	 * Sets the servlet context.
	 *
	 * @param servletContext the new servlet context
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * Siguiente.
	 *
	 * @return the string
	 */
	public String siguiente() {
		return "irEdicionIntegrantes";
	}

	/**
	 * Subir archivo.
	 *
	 * @param event the event
	 */
	public void subirArchivo(FileUploadEvent event) {

		UploadedFile archivoSubir = event.getFile();

		String extension = obtenerExtensionArchivo(archivoSubir.getFileName());

		nombreArchivoTemporal = Util.getRandomImageName() + extension;

		String rutaArchivoTemporal = servletContext.getRealPath("") + File.separator + CARPETA_TEMPORAL_IMAGENES
				+ File.separator + nombreArchivoTemporal;

		int tamanioMinimo = 210;
		int tamanioEscala = 500;
		int tamanioMaximoLado = 1000;

		archivoYaSeleccionado = cargarImagenDisco(archivoSubir, servletContext, rutaArchivoTemporal, tamanioMinimo,
				tamanioEscala, tamanioMaximoLado);
	}

	/**
	 * Validar datos basicos grupo.
	 *
	 * @return true, if successful
	 */
	private boolean validacionBasicaGrupo() {
		grupoActual.setNombre(grupoActual.getNombre().toUpperCase());
		asignarDependenciaGrupo();

		// Se valida si el grupo ya existe.
		if (isGrupoNuevo() && validarGrupoYaExiste(grupoActual.getNombre().trim().toUpperCase())) {
			mensajeError("Ya existe un grupo con este nombre, por favor ingrese un nombre diferente.");
			return false;
		}

		// Validación de datos minimos
		if (grupoActual.getFechaCreacion() == null) {
			mensajeError("La fecha de creación del grupo es requerida.");
			return false;
		}
		if ("S".equals(esIntersedes) && esListaVacia(listaSedes)) {
			mensajeError("Si el grupo es intersede debe agregar las sedes.");
			return false;
		}
		if ("S".equals(esInterfacultades) && esListaVacia(grupoActual.getListaDependencias())) {
			mensajeError("Si el grupo es interfacultad debe agregar las facultades.");
			return false;
		}
		if (grupoActual.getInterinstitucion().equals("S") && esListaVacia(grupoActual.getListaInstituciones())) {
			mensajeError("Si el grupo cuenta con aval de otra institución, debe agregar al menos una institución.");
			return false;
		}

		return true;
	}

	/**
	 * Validar grupo ya existe.
	 *
	 * @param nombre the nombre
	 * @return true, if successful
	 */
	private boolean validarGrupoYaExiste(String nombre) {
		String consulta = "from Grupo g where upper(g.nombre) = '" + nombre + "' and g.estadoGrupo.id not in ('D','N')";
		List<Grupo> grupos = servicioGeneral.obtenerObjetos(Grupo.class, consulta);
		if (!esListaVacia(grupos)) {
			mensajeError("Ya existe un grupo con este nombre, por favor ingrese un nombre diferente.");
			return true;
		} else {
			return false;
		}
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

	public boolean isLider() {
		return lider;
	}

	public void setLider(boolean lider) {
		this.lider = lider;
	}

	public boolean isGrupoNuevo() {
		return grupoNuevo;
	}

	public void setGrupoNuevo(boolean grupoNuevo) {
		this.grupoNuevo = grupoNuevo;
	}

	public SelectItem[] getFuentesExternasItem() {
		return fuentesExternasItem;
	}

	public void setFuentesExternasItem(SelectItem[] fuentesExternasItem) {
		this.fuentesExternasItem = fuentesExternasItem;
	}

	public String getInstitucionSeleccionada() {
		return institucionSeleccionada;
	}

	public void setInstitucionSeleccionada(String institucionSeleccionada) {
		this.institucionSeleccionada = institucionSeleccionada;
	}

	public String getSedeFiltroLabs() {
		return sedeFiltroLabs;
	}

	public void setSedeFiltroLabs(String sedeFiltroLabs) {
		this.sedeFiltroLabs = sedeFiltroLabs;
	}

	public SelectItem[] getLabsItem() {
		return labsItem;
	}

	public void setLabsItem(SelectItem[] labsItem) {
		this.labsItem = labsItem;
	}

	public GrupoInstitucion getInstitucionEliminar() {
		return institucionEliminar;
	}

	public void setInstitucionEliminar(GrupoInstitucion institucionEliminar) {
		this.institucionEliminar = institucionEliminar;
	}

	public String getLabSeleccionado() {
		return labSeleccionado;
	}

	public void setLabSeleccionado(String labSeleccionado) {
		this.labSeleccionado = labSeleccionado;
	}

	public GrupoLaboratorio getLabEliminar() {
		return labEliminar;
	}

	public void setLabEliminar(GrupoLaboratorio labEliminar) {
		this.labEliminar = labEliminar;
	}

	public String reporteSemillero() {
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("idSemillero", semilleroSeleccionado);
		r.setNombreReporte("/semilleros/reporteSemillero");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		r.run(context);
		return "";
	}

	public String getSemilleroSeleccionado() {
		return semilleroSeleccionado;
	}

	public void setSemilleroSeleccionado(String semilleroSeleccionado) {
		this.semilleroSeleccionado = semilleroSeleccionado;
	}

	public void cargarSemilleros() {
		if (grupoActual != null && grupoActual.getId() != null) {
			listaSemilleros = servicioGeneral.obtenerObjetos(SemilleroGrupo.class,
					"from SemilleroGrupo s where s.grupo.id='" + grupoActual.getId()
							+ "' and s.fechaBorrado is null order by s.semillero.id");
		} else {
			listaSemilleros = new ArrayList<SemilleroGrupo>();
		}
	}

	public void cargarHistoricoSemilleros() {
		if (grupoActual != null && grupoActual.getId() != null) {
			listaSemillerosHistorico = servicioGeneral.obtenerObjetos(SemilleroGrupo.class,
					"from SemilleroGrupo s where s.grupo.id='" + grupoActual.getId() + "' order by s.semillero.id");
		} else {
			listaSemillerosHistorico = new ArrayList<SemilleroGrupo>();
		}
	}
	
	public void cargarHistoricoSedes() {
		if (grupoActual != null && grupoActual.getId() != null) {
			listaSedesHistorico = servicioGeneral.obtenerObjetos(GrupoIntersedes.class,
					"from GrupoIntersedes s where s.grupo.id='" + grupoActual.getId() + "' order by s.sede.id");
		} else {
			listaSedesHistorico = new ArrayList<GrupoIntersedes>();
		}
	}

	public String getSedeSemilleros() {
		return sedeSemilleros;
	}

	public void setSedeSemilleros(String sedeSemilleros) {
		this.sedeSemilleros = sedeSemilleros;
	}

	public SelectItem[] getSemItem() {
		return semItem;
	}

	public void setSemItem(SelectItem[] semItem) {
		this.semItem = semItem;
	}

	public SemilleroGrupo getSemEliminar() {
		return semEliminar;
	}

	public void setSemEliminar(SemilleroGrupo semEliminar) {
		this.semEliminar = semEliminar;
	}

	public void cambiarSedeSem() {
		List<Dependencia> facultadesUN = servicioDependencia.obtenerFacultadesXSede(sedeSemilleros);
		facultadItemSem = servicioDependencia.crearSelectItem(facultadesUN);
		setFacultadSelSem(((Dependencia) facultadesUN.get(0)).getId().toString());
		actualizarSemilleros();
	}

	public void actualizarSemilleros() {
		String consultaSemilleros = "select e from Semillero e, SemilleroIntegrante i, InvestigadorInterno ii where "
				+ "e.id = i.semillero.id and i.integrante.id.documento = ii.id.documento and ii.dependencia.facultad.id = '" + facultadSelSem
				+ "' and i.tipo.id = 'DD' order by e.id";
		List<Semillero> listaSemilleros = servicioGeneral.obtenerObjetos(Semillero.class, consultaSemilleros);
		List<Semillero> listaSemillerosActivos = new ArrayList<Semillero>();
		for (int i = 0; i < listaSemilleros.size(); i++) {
			listaSemillerosActivos.add(listaSemilleros.get(i));
		}

		semItem = new SelectItem[listaSemillerosActivos.size()];
		for (int i = 0; i < listaSemillerosActivos.size(); i++) {
			Semillero s = listaSemillerosActivos.get(i);
			semItem[i] = new SelectItem(s.getId(), s.getId() + " - " + s.getNombre());
		}
	}

	public List<SelectItem> getFacultadItemSem() {
		return facultadItemSem;
	}

	public void setFacultadItemSem(List<SelectItem> facultadItemSem) {
		this.facultadItemSem = facultadItemSem;
	}

	public String getFacultadSelSem() {
		return facultadSelSem;
	}

	public void setFacultadSelSem(String facultadSelSem) {
		this.facultadSelSem = facultadSelSem;
	}

	public String getSemSeleccionado() {
		return semSeleccionado;
	}

	public void setSemSeleccionado(String semSeleccionado) {
		this.semSeleccionado = semSeleccionado;
	}

	public List<SemilleroGrupo> getListaSemillerosBorrados() {
		return listaSemillerosBorrados;
	}

	public void setListaSemillerosBorrados(List<SemilleroGrupo> listaSemillerosBorrados) {
		this.listaSemillerosBorrados = listaSemillerosBorrados;
	}

	public List<SemilleroGrupo> getListaSemillerosHistorico() {
		return listaSemillerosHistorico;
	}

	public void setListaSemillerosHistorico(List<SemilleroGrupo> listaSemillerosHistorico) {
		this.listaSemillerosHistorico = listaSemillerosHistorico;
	}

	public void eliminarSemillero() {
		listaSemilleros.remove(semEliminar);
		if(semEliminar.getFechaRegistro()!=null) {
			semEliminar.setResponsable(personaActual);
			semEliminar.setFechaBorrado(getToday());
			listaSemillerosBorrados.add(semEliminar);
		}
	}

	public void agregarSem() {
		if (!esCadenaVacia(semSeleccionado)) {
			boolean existe = false;
			for (SemilleroGrupo s : listaSemilleros) {
				if (s.getSemillero().getId().toString().equals(semSeleccionado)) {
					existe = true;
					mensajeError("El semillero ya se encuentra vinculado.");
					break;
				}
			}
			if (!existe) {
				Semillero s = servicioGeneral
						.obtenerObjetos(Semillero.class, "from Semillero s where s.id=" + semSeleccionado).get(0);
				SemilleroGrupo sg = new SemilleroGrupo();
				sg.setGrupo(grupoActual);
				sg.setSemillero(s);
				sg.setFechaRegistro(null);
				sg.setAgregoSemillero("G");
				listaSemilleros.add(sg);
			}
		} else {
			mensajeError("Por favor, indicar un semillero.");
		}
	}

	public List<GrupoIntersedes> getListaSedesHistorico() {
		return listaSedesHistorico;
	}

	public void setListaSedesHistorico(List<GrupoIntersedes> listaSedesHistorico) {
		this.listaSedesHistorico = listaSedesHistorico;
	}

}