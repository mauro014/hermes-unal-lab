package co.edu.unal.hermes.vista.evaluadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.apache.myfaces.custom.dynaForm.guiBuilder.impl.jsf.NewComponentListener;
import org.hibernate.util.ComparableComparator;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaTramiteBiodiversidad;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoCompromiso;
import co.edu.unal.hermes.modelo.ProyectoCoordinadorEditorial;
import co.edu.unal.hermes.modelo.ProyectoRequisito;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.TipoPropiedadIntelectual;
import co.edu.unal.hermes.modelo.TipoRequisito;
import co.edu.unal.hermes.modelo.TipoTramiteBiodiversidad;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorRevisarRequisitos.
 */
public class ManejadorRevisarRequisitosEditorial extends ManejadorBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9023593426513658167L;

	/** The convocatoria. */
	private String convocatoria;

	/** The lista convocatorias padre. */
	private List<ConvocatoriaPadre> listaConvocatoriasPadre;

	/** The lista convocatorias. */
	private List<Convocatoria> listaConvocatorias;

	/** The lista requisito. */
	private List<ProyectoRequisito> listaRequisito;

	/** The convocatoria item. */
	private SelectItem[] convocatoriaItem;

	/** The convocatoria padre item. */
	private SelectItem[] convocatoriaPadreItem;

	/** The lista archivos. */
	private List<Archivo> listaArchivos;

	/** The mensaje subida archivo. */
	private String mensajeSubidaArchivo;

	/** The lista proyectos coordinador. */
	private List<ProyectoCoordinadorEditorial> listaProyectosCoordinador;

	/** The mostrar estoy seguro. */
	private boolean mostrarEstoySeguro;

	/** The mensaje requisito. */
	private String mensajeRequisito;

	/** The nombre convocatoria. */
	private String nombreConvocatoria;

	/** The mostrar botones ap. */
	private boolean mostrarBotonesAp;

	/** The nombre modalidad. */
	private String nombreModalidad;

	/** The convocatoria padre. */
	private String convocatoriaPadre;

	/** The mostrar tabla. */
	private boolean mostrarTabla;

	/** The mensaje asignacion id. */
	private String mensajeAsignacionId;

	/** The nombre convocatoria busqueda. */
	private String nombreConvocatoriaBusqueda;

	/** The proyecto coordinador selecionado. */
	private ProyectoCoordinadorEditorial proyectoCoordinadorSelecionado;

	/** The activar aprobacion. */
	private boolean activarAprobacion;

	/** The proyecto actual. */
	private Proyecto proyectoActual;

	private final String REGLA_REVISION_REQUISITOS = "verRequisitosEditorial";

	/**
	 * Instantiates a new manejador revisar requisitos.
	 */
	public ManejadorRevisarRequisitosEditorial() {
		listaProyectosCoordinador = new ArrayList<ProyectoCoordinadorEditorial>();

		personaActual = (Persona) sesion.getAttribute("persona");

		this.convocatoriaPadre = null;
		this.convocatoria = null;
		this.proyectoActual = null;

	}

	/**
	 * Guardar requisitos.
	 */
	private void guardarRequisitos() {
		for (Iterator it = proyectoActual.getRequisitosProyecto().iterator(); it.hasNext();) {
			ProyectoRequisito requisitoProyecto = (ProyectoRequisito) it.next();
			if (personaActual != null && personaActual.getId() != null) {
				requisitoProyecto.setResponsableId(personaActual.getId().getDocumento());
				requisitoProyecto.setResponsableTipoDocumento(personaActual.getId().getTipoDocumento());
			}
			servicioGeneral.guardarObjeto(requisitoProyecto);
		}
	}

	/**
	 * Descargar archivo.
	 */
	public void descargarArchivo() {

		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> map = context.getExternalContext().getRequestParameterMap();
		Object o = map.get("archivoResumen");
		Long id = Long.valueOf((String) o);

		descargarArchivoProyectoGenerico(id, proyectoActual.getId());
	}

	/**
	 * Imprimir reporte proyecto.
	 */
	public void imprimirReporteProyecto() {
		servicioProyecto.imprimirReporteProyecto(proyectoActual, sesion, false);
	}

	/**
	 * Imprimir proyecto asociado.
	 */
	public void imprimirProyectoAsociado() {
		Proyecto proyectoActual2 = servicioProyecto.obtenerProyecto(Long.parseLong(proyectoActual.getCodigoDib()),
				ProyectoDAOHibernate.INFORMACION_GENERAL);
		servicioProyecto.imprimirReporteProyecto(proyectoActual2, sesion, false);
	}

	public boolean isCheckProyectoAsociado() {
		try {
			Proyecto proyectoActual2 = servicioProyecto.obtenerProyecto(Long.parseLong(proyectoActual.getCodigoDib()),
					ProyectoDAOHibernate.INFORMACION_GENERAL);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Gets the lista requisito.
	 *
	 * @return the lista requisito
	 */
	public List<ProyectoRequisito> getListaRequisito() {
		return listaRequisito;
	}

	/**
	 * Consultar convocatorias.
	 */
	public void consultarConvocatorias() {
		boolean encontrado = true;
		if (StringUtils.isNotEmpty(nombreConvocatoriaBusqueda)) {
			listaConvocatoriasPadre = servicioGeneral.obtenerObjetos(ConvocatoriaPadre.class,
					"from ConvocatoriaPadre cp where upper(cp.titulo)" + " like '%"
							+ nombreConvocatoriaBusqueda.toUpperCase()
							+ "%' and cp.tipoConvocatoria = '11' order by cp.id desc");
			if (!esListaVacia(listaConvocatoriasPadre)) {
				convocatoriaPadre = listaConvocatoriasPadre.get(0).getId().toString();
				convocatoriaPadreItem = new SelectItem[listaConvocatoriasPadre.size()];

				for (int i = 0; i < listaConvocatoriasPadre.size(); i++) {
					ConvocatoriaPadre con = (ConvocatoriaPadre) listaConvocatoriasPadre.get(i);
					convocatoriaPadreItem[i] = new SelectItem(con.getId().toString(), con.getTitulo());
				}
				listaConvocatorias = servicioGeneral.obtenerObjetos(Convocatoria.class,
						"from Convocatoria c where c.padre.id ='" + listaConvocatoriasPadre.get(0).getId()
								+ "' order by c.id desc");

				if (!esListaVacia(listaConvocatorias)) {
					convocatoriaItem = new SelectItem[listaConvocatorias.size()];
					for (int i = 0; i < listaConvocatorias.size(); i++) {
						Convocatoria con = (Convocatoria) listaConvocatorias.get(i);
						convocatoriaItem[i] = new SelectItem(con.getId().toString(), con.getTitulo());
					}
					nombreModalidad = ((Convocatoria) listaConvocatorias.get(0)).getTitulo();
					nombreConvocatoria = ((Convocatoria) listaConvocatorias.get(0)).getPadre().getTitulo();
				}
			} else {
				encontrado = false;
			}
		} else {
			encontrado = false;
		}
		if (!encontrado) {
			listaConvocatoriasPadre = new ArrayList<ConvocatoriaPadre>();
			listaConvocatorias = new ArrayList<Convocatoria>();
			convocatoriaPadreItem = new SelectItem[0];
			convocatoriaItem = new SelectItem[0];
		}
	}

	/**
	 * Cambiar convocatoria padre.
	 *
	 * @param event the event
	 */
	public void cambiarConvocatoriaPadre(ValueChangeEvent event) {
		ConvocatoriaPadre cp = buscarConvocatoriaPadrexId((String) event.getNewValue());
		if (cp != null) {
			listaConvocatorias.clear();
			listaConvocatorias = servicioGeneral.obtenerObjetos(Convocatoria.class,
					"from Convocatoria c where c.padre.id ='" + cp.getId() + "' order by c.id desc");

			convocatoriaItem = new SelectItem[listaConvocatorias.size()];
			if (!esListaVacia(listaConvocatorias)) {
				for (int i = 0; i < listaConvocatorias.size(); i++) {
					Convocatoria con = (Convocatoria) listaConvocatorias.get(i);
					convocatoriaItem[i] = new SelectItem(con.getId().toString(), con.getTitulo());
				}
				Convocatoria con = (Convocatoria) listaConvocatorias.get(0);
				convocatoria = con.getId().toString();
			}

		}
	}

	/**
	 * Mostrar requisitos.
	 *
	 * @return the string
	 */
	public String mostrarRequisitos() {
		mostrarEstoySeguro = true;
		mensajeRequisito = "";
		mostrarBotonesAp = false;
		proyectoActual = servicioProyecto.obtenerProyecto(proyectoCoordinadorSelecionado.idProyecto,
				ProyectoDAOHibernate.REQUISITOS);
		adicionarRequisitos(proyectoCoordinadorSelecionado.idProyecto);
		Proyecto proyecto = new Proyecto();
		proyecto.setId(proyectoCoordinadorSelecionado.idProyecto);
		listaArchivos = servicioProyecto.obtenerNombresArchivos(proyecto);
		listaRequisito = new ArrayList<ProyectoRequisito>();
		if (esListaVacia(new ArrayList(proyectoActual.getRequisitosProyecto()))) {
			proyectoActual = servicioProyecto.obtenerProyecto(proyectoCoordinadorSelecionado.idProyecto,
					ProyectoDAOHibernate.REQUISITOS);
		}
		for (Iterator it = proyectoActual.getRequisitosProyecto().iterator(); it.hasNext();) {
			ProyectoRequisito pPA = new ProyectoRequisito();
			ProyectoRequisito requisitoProyecto = (ProyectoRequisito) it.next();
			TipoRequisito tr = new TipoRequisito();
			tr.setId(requisitoProyecto.getRequisito().getId());
			pPA.setRequisito(tr);
			listaRequisito.add(requisitoProyecto);
		}
		Collections.sort(listaRequisito);

		{
		}
		;
		return REGLA_REVISION_REQUISITOS;
	}

	/**
	 * Adicionar requisitos.
	 *
	 * @param id the id
	 */
	public void adicionarRequisitos(Long id) {
		List<ProyectoRequisito> listaRequisitos = servicioGeneral.obtenerObjetos(ProyectoRequisito.class,
				"from ProyectoRequisito p where p.proyecto.id = " + id);
		if (esListaVacia(listaRequisitos)) {
			Convocatoria convocatoriaActual;
			convocatoriaActual = servicioModalidad.obtenerConvocatoriaRequisitos(proyectoActual.getModalidad().getId());

			for (Iterator<TipoRequisito> it = convocatoriaActual.getRequisitos().iterator(); it.hasNext();) {
				TipoRequisito tipoRequisito = (TipoRequisito) it.next();
				List<TipoRequisito> listaHijos = servicioGeneral.obtenerListaRequisitoHijo(tipoRequisito.getId());

				if (!esListaVacia(listaHijos)) {
					for (int j = 0; j < listaHijos.size(); j++) {
						ProyectoRequisito requisitoProyecto = new ProyectoRequisito();
						TipoRequisito hijo = (TipoRequisito) listaHijos.get(j);
						requisitoProyecto.setRequisito(hijo);
						requisitoProyecto.setProyecto(proyectoActual);
						requisitoProyecto.setCumplido("S");
						servicioGeneral.guardarObjeto(requisitoProyecto);
					}
				}
			}
		}
	}

	/**
	 * Aprobar requisitos.
	 *
	 * @return the string
	 */
	public String aprobarRequisitos() {
		guardarRequisitos();
		String observacionesCambioEstado = "Por aprobación de requisitos";
		EstadoProyecto estadoProyecto = new EstadoProyecto();
		estadoProyecto.setId(EstadoProyecto.ELEGIBLE);
		proyectoActual.cambiarEstadoPersona(estadoProyecto.getId(), cargarPersonaActual(), observacionesCambioEstado);
		servicioProyecto.actualizarProyecto(proyectoActual);

		try {
			Correo correoConfirmacion = new Correo();
			CorreoPlantilla cpConfirmacion = cargarPlantilla(385);
			correoConfirmacion.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
			//correoConfirmacion.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);
			correoConfirmacion.setAsunto(cpConfirmacion.getAsunto());
			Investigador autor = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
			correoConfirmacion
					.setCuerpo(cpConfirmacion.getCuerpo().replaceAll("<<AUTOR>>", autor.getNombreCompletoMinusculas())
							.replaceAll("<<TITULO>>", proyectoActual.getNombre().toString())
							.replaceAll("<<COORD_REQ>>", personaActual.getNombreCompletoMinusculas())
							.replaceAll("<<FACULTAD>>", autor.getDependencia().getFacultad().getNombre())
							.replaceAll("<<SEDE>>", autor.getDependencia().getSede().getNombre()));

			correoConfirmacion
					.adicionarDireccion(autor.getEmail());
			if (correoConfirmacion.getDirecciones() != null) {
				servicioCorreo.enviarCorreo(correoConfirmacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		proyectoActual = null;
		listaRequisito = new ArrayList<ProyectoRequisito>();
		listaArchivos = new ArrayList<Archivo>();
		buscarProyectosRevisionxModalidad();
		return "success";
	}

	/**
	 * Rechazar proyecto.
	 *
	 * @return the string
	 */
	public String rechazarProyecto() {
		guardarRequisitos();
		EstadoProyecto estadoProyecto = new EstadoProyecto();
		estadoProyecto.setId(EstadoProyecto.RECHAZADO);
		proyectoActual.cambiarEstadoPersona(estadoProyecto.getId(), cargarPersonaActual(),
				"Por no cumplimiento de requisitos");
		servicioProyecto.actualizarProyecto(proyectoActual);

		try {
			Correo correoConfirmacion = new Correo();
			CorreoPlantilla cpConfirmacion = cargarPlantilla(384);
			correoConfirmacion.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
			//correoConfirmacion.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);
			correoConfirmacion.setAsunto(cpConfirmacion.getAsunto());
			Investigador autor = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyectoActual.getId());
			correoConfirmacion
					.setCuerpo(cpConfirmacion.getCuerpo().replaceAll("<<AUTOR>>", autor.getNombreCompletoMinusculas())
							.replaceAll("<<TITULO>>", proyectoActual.getNombre().toString())
							.replaceAll("<<COORD_REQ>>", personaActual.getNombreCompletoMinusculas())
							.replaceAll("<<FACULTAD>>", autor.getDependencia().getFacultad().getNombre())
							.replaceAll("<<SEDE>>", autor.getDependencia().getSede().getNombre()));

			correoConfirmacion
					.adicionarDireccion(autor.getEmail());
			if (correoConfirmacion.getDirecciones() != null) {
				servicioCorreo.enviarCorreo(correoConfirmacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		proyectoActual = null;
		listaRequisito = new ArrayList<ProyectoRequisito>();
		listaArchivos = new ArrayList<Archivo>();
		buscarProyectosRevisionxModalidad();
		return "success";
	}

	/**
	 * Buscar proyectos revisionx modalidad.
	 */
	public void buscarProyectosRevisionxModalidad() {
		mensajeAsignacionId = "";
		listaProyectosCoordinador.clear();

		Dependencia dependencia = new Dependencia();

		try {
			Persona investigadorInternoCompleto = servicioPersona
					.obtenerInvestigadorInternoCompleto(personaActual.getId());
			InvestigadorInterno investigadorInterno = (InvestigadorInterno) investigadorInternoCompleto;
			dependencia = servicioDependencia.obtenerDependencia(investigadorInterno.getId());
			personaActual = investigadorInternoCompleto;
		} catch (Exception e) {
			System.out.println("No tiene investigador interno");
			return;
		}

		if (StringUtils.isNotEmpty(convocatoria)) {
			listaProyectosCoordinador
					.addAll(servicioProyecto.obtenerProyectosCoordinadorEditorialRevisionxModalidadSede(
							buscarModalidadxId(convocatoria), dependencia, personaActual));
			if (!esListaVacia(listaProyectosCoordinador)) {
				mostrarTabla = true;
			}
		}
		cambiarModalidad();
	}

	/**
	 * Cambiar modalidad.
	 */
	public void cambiarModalidad() {
		if (StringUtils.isNoneEmpty(convocatoria) && StringUtils.isNoneEmpty(convocatoriaPadre)) {
			Convocatoria convocatoriaEncontrada = (Convocatoria) buscarModalidadxId(convocatoria);
			if (convocatoriaEncontrada != null) {
				nombreModalidad = convocatoriaEncontrada.getTitulo();
				nombreConvocatoria = buscarConvocatoriaPadrexId(convocatoriaPadre).getTitulo();
			}
		}
	}

	/**
	 * Buscar modalidadx id.
	 *
	 * @param idModalidad the id modalidad
	 * @return the modalidad
	 */
	private Modalidad buscarModalidadxId(String idModalidad) {
		if (listaConvocatorias != null) {
			Iterator<Convocatoria> it = listaConvocatorias.iterator();
			Modalidad m = null;
			boolean modalidadEncontrada = false;
			while (it.hasNext() && !modalidadEncontrada) {
				m = (Modalidad) it.next();
				if (m.getId().longValue() == Long.parseLong(idModalidad)) {
					modalidadEncontrada = true;
				}
			}
			return m;
		} else
			return null;
	}

	/**
	 * Buscar convocatoria padrex id.
	 *
	 * @param idModalidad the id modalidad
	 * @return the convocatoria padre
	 */
	private ConvocatoriaPadre buscarConvocatoriaPadrexId(String idModalidad) {
		if (listaConvocatoriasPadre != null) {
			Iterator<ConvocatoriaPadre> it = listaConvocatoriasPadre.iterator();
			ConvocatoriaPadre m = null;
			boolean modalidadEncontrada = false;
			while (it.hasNext() && !modalidadEncontrada) {
				m = (ConvocatoriaPadre) it.next();
				if (m != null) {
					if (m.getId().longValue() == Long.parseLong(idModalidad)) {
						modalidadEncontrada = true;
					}
				}
			}
			return m;
		} else
			return null;
	}

	/**
	 * No mostrar botones.
	 */
	public void noMostrarBotones() {
		mostrarBotonesAp = false;
		mostrarEstoySeguro = true;
	}

	/**
	 * Mostrar botones.
	 */
	public void mostrarBotones() {
		ActivarAprobar();
		mostrarBotonesAp = true;
		mostrarEstoySeguro = false;
	}

	/**
	 * Activar aprobar.
	 */
	public void ActivarAprobar() {
		boolean todosActivos = true;
		for (Object pr : listaRequisito) {
			ProyectoRequisito pr2 = (ProyectoRequisito) pr;
			boolean valor = pr2.isCumplidoCheckbox();
			if (!valor) {
				todosActivos = false;
			}
		}
		if (todosActivos) {
			activarAprobacion = true;
		} else
			activarAprobacion = false;
	}

	/**
	 * Guardar proyecto.
	 *
	 * @return the string
	 */
	public String guardarProyecto() {
		guardarRequisitos();
		sesion.removeAttribute("manejadorRevisarRequisitosEditorial");
		sesion.removeAttribute("manejadorAsignacionEstadoProyectos");
		return "success";
	}

	/**
	 * Insertar archivo.
	 *
	 * @param event the event
	 */
	public void insertarArchivo(FileUploadEvent event) {
		UploadedFile archivo = event.getFile();
		insertarArchivoProyectoGenerico(archivo, proyectoActual, listaArchivos);
		listaArchivos = servicioProyecto.obtenerNombresArchivos(proyectoActual);
	}

	/**
	 * Gets the convocatoria.
	 *
	 * @return the convocatoria
	 */
	public String getConvocatoria() {
		return convocatoria;
	}

	/**
	 * Sets the convocatoria.
	 *
	 * @param convocatoria the new convocatoria
	 */
	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}

	/**
	 * Gets the lista proyectos coordinador.
	 *
	 * @return the lista proyectos coordinador
	 */
	public List<ProyectoCoordinadorEditorial> getListaProyectosCoordinador() {
		return listaProyectosCoordinador;
	}

	/**
	 * Sets the lista proyectos coordinador.
	 *
	 * @param listaProyectosCoordinador the new lista proyectos coordinador
	 */
	public void setListaProyectosCoordinador(List<ProyectoCoordinadorEditorial> listaProyectosCoordinador) {
		this.listaProyectosCoordinador = listaProyectosCoordinador;
	}

	/**
	 * Gets the convocatoria padre.
	 *
	 * @return the convocatoria padre
	 */
	public String getConvocatoriaPadre() {
		return convocatoriaPadre;
	}

	/**
	 * Sets the convocatoria padre.
	 *
	 * @param convocatoriaPadre the new convocatoria padre
	 */
	public void setConvocatoriaPadre(String convocatoriaPadre) {
		this.convocatoriaPadre = convocatoriaPadre;
	}

	/**
	 * Gets the convocatoria item.
	 *
	 * @return the convocatoria item
	 */
	public SelectItem[] getConvocatoriaItem() {
		return convocatoriaItem;
	}

	/**
	 * Gets the convocatoria padre item.
	 *
	 * @return the convocatoria padre item
	 */
	public SelectItem[] getConvocatoriaPadreItem() {
		return convocatoriaPadreItem;
	}

	/**
	 * Gets the lista archivos.
	 *
	 * @return the lista archivos
	 */
	public List<Archivo> getListaArchivos() {
		return listaArchivos;
	}

	/**
	 * Gets the mensaje subida archivo.
	 *
	 * @return the mensaje subida archivo
	 */
	public String getMensajeSubidaArchivo() {
		return mensajeSubidaArchivo;
	}

	/**
	 * Checks if is mostrar estoy seguro.
	 *
	 * @return true, if is mostrar estoy seguro
	 */
	public boolean isMostrarEstoySeguro() {
		return mostrarEstoySeguro;
	}

	/**
	 * Gets the mensaje requisito.
	 *
	 * @return the mensaje requisito
	 */
	public String getMensajeRequisito() {
		return mensajeRequisito;
	}

	/**
	 * Gets the nombre convocatoria.
	 *
	 * @return the nombre convocatoria
	 */
	public String getNombreConvocatoria() {
		return nombreConvocatoria;
	}

	/**
	 * Checks if is mostrar botones ap.
	 *
	 * @return true, if is mostrar botones ap
	 */
	public boolean isMostrarBotonesAp() {
		return mostrarBotonesAp;
	}

	/**
	 * Gets the nombre modalidad.
	 *
	 * @return the nombre modalidad
	 */
	public String getNombreModalidad() {
		return nombreModalidad;
	}

	/**
	 * Gets the mostrar tabla.
	 *
	 * @return the mostrar tabla
	 */
	public boolean getMostrarTabla() {
		return mostrarTabla;
	}

	/**
	 * Gets the mensaje asignacion id.
	 *
	 * @return the mensaje asignacion id
	 */
	public String getMensajeAsignacionId() {
		return mensajeAsignacionId;
	}

	/**
	 * Gets the nombre convocatoria busqueda.
	 *
	 * @return the nombre convocatoria busqueda
	 */
	public String getNombreConvocatoriaBusqueda() {
		return nombreConvocatoriaBusqueda;
	}

	/**
	 * Sets the nombre convocatoria busqueda.
	 *
	 * @param nombreConvocatoriaBusqueda the new nombre convocatoria busqueda
	 */
	public void setNombreConvocatoriaBusqueda(String nombreConvocatoriaBusqueda) {
		this.nombreConvocatoriaBusqueda = nombreConvocatoriaBusqueda;
	}

	/**
	 * Gets the proyecto coordinador selecionado.
	 *
	 * @return the proyecto coordinador selecionado
	 */
	public ProyectoCoordinadorEditorial getProyectoCoordinadorSelecionado() {
		return proyectoCoordinadorSelecionado;
	}

	/**
	 * Sets the proyecto coordinador selecionado.
	 *
	 * @param proyectoCoordinadorSelecionado the new proyecto coordinador
	 *                                       selecionado
	 */
	public void setProyectoCoordinadorSelecionado(ProyectoCoordinadorEditorial proyectoCoordinadorSelecionado) {
		this.proyectoCoordinadorSelecionado = proyectoCoordinadorSelecionado;
	}

	/**
	 * Checks if is activar aprobacion.
	 *
	 * @return true, if is activar aprobacion
	 */
	public boolean isActivarAprobacion() {
		return activarAprobacion;
	}

	/**
	 * Gets the proyecto actual.
	 *
	 * @return the proyecto actual
	 */
	public Proyecto getProyectoActual() {
		return proyectoActual;
	}

}
