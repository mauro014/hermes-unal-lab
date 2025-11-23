package co.edu.unal.hermes.vista.grupos.ediciongrupos;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanUtils;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoGrupo;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.GrupoIntersedes;
import co.edu.unal.hermes.modelo.GrupoProductoSara;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProductoSara;
import co.edu.unal.hermes.modelo.ProductoTipo;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.SolicitudGrupo;
import co.edu.unal.hermes.modelo.TipoSolicitudGrupo;
import co.edu.unal.hermes.modelo.VProductoSara;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.utils.Util;

/**
 * The Class ManejadorEdicionGrupoVisionPrioridadesPerspectiva.
 */
public class ManejadorEdicionGrupoVisionPrioridadesPerspectiva extends ManejadorBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1060943972439105935L;

	/** The grupo actual. */
	private Grupo grupoActual;

	/** The proyecto asociados. */
	private List<String[]> proyectoAsociados;

	/** The proyectos eliminados. */
	private List<String[]> proyectosEliminados;

	/** The opciones proyectos. */
	private List<SelectItem> opcionesProyectos;

	/** The nuevo proyecto. */
	private String nuevoProyecto;

	/** The lista proyectos. */
	private List<Proyecto> listaProyectos;

	/** The proyecto seleccionado. */
	private String[] proyectoSeleccionado;

	/** The lista opciones. */
	// Variables para relacionar productos de sara con grupos
	private List<SelectItem> listaOpciones;

	/** The prod sara. */
	private String prodSara;

	/** The nombre producto. */
	private String nombreProducto;

	/** The productos tmp. */
	private List<VProductoSara> productosTmp;

	/** The producto sara. */
	private ProductoSara productoSara;

	/** The productos sara items. */
	private SelectItem[] productosSaraItems;

	/** The producto llave. */
	private String productoLlave;

	/** The productos asociados. */
	private List<GrupoProductoSara> productosAsociados;

	/** The productos eliminados. */
	private List<GrupoProductoSara> productosEliminados;

	/** The producto seleccionado. */
	private GrupoProductoSara productoSeleccionado;

	/** The producto nivel 1. */
	private String productoNivel1;

	/** The producto nivel 2. */
	private String productoNivel2;

	/** The producto nivel 3. */
	private String productoNivel3;

	/** The producto nivel 1 item. */
	private SelectItem[] productoNivel1Item;

	/** The producto nivel 2 item. */
	private SelectItem[] productoNivel2Item;

	/** The producto nivel 3 item. */
	private SelectItem[] productoNivel3Item;

	private boolean lider;

	/**
	 * Instantiates a new manejador edicion grupo vision prioridades perspectiva.
	 */
	public ManejadorEdicionGrupoVisionPrioridadesPerspectiva() {
		grupoActual = (Grupo) sesion.getAttribute("grupo");
		cargarProyectos();
		cargarProductos();
		cargarPosiblesProyectos();
		generarListadoProyectos();
		prodSara = "NO";
		listaOpciones = new ArrayList<SelectItem>();
		listaOpciones.add(new SelectItem("SI", "SI"));
		listaOpciones.add(new SelectItem("NO", "NO"));
		lider = validarPermisos();
	}

	/**
	 * Agregar producto.
	 */
	public void agregarProducto() {

		if (productoSara != null) {
			try {
				boolean yaEsta = false;
				for (int i = 0; i < productosAsociados.size(); i++) {
					if (productoSara.getNombreSeccion().equals(productosAsociados.get(i).getTipoProducto())
							&& productoSara.getNombreProducto().equals(productosAsociados.get(i).getNombreProducto())) {
						publicarMensaje("El producto ya ha sido agregado a la lista.", FacesMessage.SEVERITY_ERROR);
						yaEsta = true;
						break;
					}
				}
				if (!yaEsta) {
					GrupoProductoSara gps = new GrupoProductoSara();
					gps.setGrupo(grupoActual);
					gps.setTipoProducto(productoSara.getNombreSeccion());
					gps.setNombreProducto(productoSara.getNombreProducto());

					productosAsociados.add(gps);
				}
			} catch (NullPointerException npe) {
				publicarMensaje("Hubo un problema al agregar el producto, por favor intentelo nuevamente.",
						FacesMessage.SEVERITY_ERROR);
			}
		}
	}

	/**
	 * Agregar producto nuevo.
	 */
	public void agregarProductoNuevo() {

		if (!Util.validarNoVacio(nombreProducto)) {
			mensajeError("Usted no puede agregar un producto vacío", "nombreProducto");
			return;
		}

		GrupoProductoSara grupoProductoSara = new GrupoProductoSara();
		grupoProductoSara.setGrupo(grupoActual);

		String productoEncontrado = "";
		// Se busca el nombre del producto
		for (int i = 0; i < productoNivel3Item.length; i++) {
			SelectItem selectItem = productoNivel3Item[i];
			if (((String) selectItem.getValue()).equals(productoNivel3)) {
				productoEncontrado = selectItem.getLabel();
			}
		}
		grupoProductoSara.setTipoProducto(productoEncontrado);
		grupoProductoSara.setNombreProducto(nombreProducto.trim());

		productosAsociados.add(grupoProductoSara);
	}

	/**
	 * Agregar proyecto.
	 */
	public void agregarProyecto() {
		if (!nuevoProyecto.equals("")) {
			String[] proyectoVista = null;
			Iterator<Proyecto> i = listaProyectos.iterator();
			while (i.hasNext()) {
				Proyecto p = i.next();
				if (nuevoProyecto.equals(p.getId().toString())) {
					proyectoVista = new String[5];
					proyectoVista[0] = p.getId().toString();
					proyectoVista[1] = p.getNombre();
					proyectoVista[2] = "S";
					proyectoVista[3] = "N";
					proyectoVista[4] = p.getEstadoProyecto().getNombre();
					break;
				}
			}
			if (proyectoVista != null) {
				Iterator<String[]> j = proyectoAsociados.iterator();
				boolean esNuevo = true;
				while (j.hasNext()) {
					String[] proyectoAgregado = j.next();
					if (proyectoAgregado[0].equals(proyectoVista[0])) {
						esNuevo = false;
						break;
					}
				}
				if (esNuevo) {
					proyectoAsociados.add(proyectoVista);
				}
			}
		}
		nuevoProyecto = "";
	}

	/**
	 * Atras.
	 *
	 * @return the string
	 */
	public String atras() {
		return "lineasGrupo";
	}

	/**
	 * Buscar productos.
	 */
	public void buscarProductos() {

		String investigadores = cargarCadenaInvestigadores();

		productosTmp = servicioGeneral.obtenerObjetos(VProductoSara.class,
				"SELECT p FROM VProductoSara p WHERE p.idInvestigador in (" + investigadores + ") "
						+ " AND upper (p.nombreProducto) LIKE upper ('%" + nombreProducto + "%')");
		productoSara = new ProductoSara();
		if (productosTmp != null && !productosTmp.isEmpty()) {
			try {
				BeanUtils.copyProperties(productoSara, productosTmp.get(0));
			} catch (Exception e) {
				e.printStackTrace();
			}
			productosSaraItems = new SelectItem[productosTmp.size() + 1];
			int i = 1;
			productosSaraItems[0] = new SelectItem("", "No hay producto asociado");
			for (VProductoSara vps : productosTmp) {
				productosSaraItems[i] = new SelectItem("" + i++, vps.getNombreProducto());
			}

			setProductoLlave("1");
		}
	}

	/**
	 * Cambiar producto nivel 1.
	 */
	public void cambiarProductoNivel1() {

		boolean productosTipoEncontrados = false;

		if (productoNivel1 != null && !productoNivel1.equals("")) {
			// valores para el segundo nivel
			ProductoTipo productoTipoPadre = new ProductoTipo();
			productoTipoPadre.setId(productoNivel1);
			List<ProductoTipo> listaProductosNivel2 = servicioGeneral.obtenerHijos(productoTipoPadre);
			if (listaProductosNivel2 != null && listaProductosNivel2.size() > 0) {
				productoNivel2 = listaProductosNivel2.get(0).getId();
				productoNivel2Item = crearProductoSelectItem(listaProductosNivel2);
				productosTipoEncontrados = true;
			}
		}

		if (!productosTipoEncontrados) {
			productoNivel2 = "";
			productoNivel2Item = new SelectItem[0];
		}

		cambiarProductoNivel2();
	}

	/**
	 * Cambiar producto nivel 2.
	 */
	public void cambiarProductoNivel2() {

		boolean productosTipoEncontrados = false;

		if (productoNivel2 != null && !productoNivel2.equals("")) {
			// valores para el segundo nivel
			ProductoTipo productoTipoPadre = new ProductoTipo();
			productoTipoPadre.setId(productoNivel2);
			List<ProductoTipo> listaProductosNivel3 = servicioGeneral.obtenerHijos(productoTipoPadre);
			if (listaProductosNivel3 != null && listaProductosNivel3.size() > 0) {
				productoNivel3 = listaProductosNivel3.get(0).getId();
				productoNivel3Item = crearProductoSelectItem(listaProductosNivel3);
				productosTipoEncontrados = true;
			}
		}

		if (!productosTipoEncontrados) {
			productoNivel3 = "";
			productoNivel3Item = new SelectItem[0];
		}
	}

	/**
	 * Se prepara una cadena con el documento de los investigadores asociados al
	 * proyecto para consultas conjuntas, separados por comas.
	 *
	 * @author Mauricio Amaya Ríos
	 * @return Cadena con documentos de investigadores.
	 * @since 18-08-2015
	 */
	public String cargarCadenaInvestigadores() {
		Iterator<InvestigadorGrupo> i = grupoActual.getInvestigadoresGrupo().iterator();

		// Se crea una cadena con los id de los investigadores
		String investigadores = "";
		while (i.hasNext()) {
			InvestigadorGrupo ig = i.next();
			if (ig.getInvestigador() != null) {
				Investigador investigador = ig.getInvestigador();
				Long valorNumerico = 0L;
				boolean error = false;
				// SARA no admite letras en los documentos, se valida que sea
				// numero y se agrega en la consulta,
				// de lo contrario no se agrega a la consulta.
				try {
					valorNumerico = Long.parseLong(investigador.getId().getDocumento().trim());
				} catch (NumberFormatException nfe) {
					error = true;
				}
				if (!error) {
					// se agrega el id del investigador a la cadena.
					if (investigadores.length() > 0) {
						investigadores += ",";
					}
					investigadores += "'" + valorNumerico + "'";
				}
			}
		}

		return investigadores;
	}

	/**
	 * Cargar posibles proyectos.
	 */
	private void cargarPosiblesProyectos() {
		try {
			String investigadores = cargarCadenaInvestigadores();

			// Se cargan todos proyectos asociados al listado de investigadores
			// del proyecto.
			listaProyectos = servicioGeneral.obtenerObjetosLimitado(Proyecto.class,
					"select #id p.id, #nombre p.nombre, #estadoProyecto p.estadoProyecto from Proyecto p," + "InvestigadorProyecto ip where "
							+ " ip.investigador.id.documento in (" + investigadores + ") "
							+ " and ip.tipo.id in ('P','PCD') and " + "ip.proyecto.id=p.id and " + "("
							+ "p.estadoProyecto.id " + "in ('A','AP','F')) and p.modalidad.id not in ("+MODALIDADES_PERMISO_CONTRATO+")");

		} catch (NullPointerException npe) {
			listaProyectos = new ArrayList<Proyecto>();
		}
	}

	/**
	 * Cargar productos.
	 */
	private void cargarProductos() {
		if (grupoActual.getId() != null) {
			String idGrupo = grupoActual.getId().toString();
			setProductosAsociados(servicioGrupo.obtenerProductosSaraGrupo(idGrupo));
		}

		cargarProductosNivel1();

	}

	/**
	 * Cargar productos nivel 1.
	 */
	private void cargarProductosNivel1() {
		// LIsta productos de primer nivel
		List<ProductoTipo> productosNivel1 = this.servicioGeneral.obtenerListaObjetosWhere(ProductoTipo.class,
				"where p.estado = 'A' and p.descripcion like 'LISTA_PRODUCTO_UNIFICADA_HERMES' and p.nivel = '0' order by p.nombre");

		if (productosNivel1 != null && productosNivel1.size() > 0) {
			productoNivel1 = productosNivel1.get(0).getId();
			productoNivel1Item = crearProductoSelectItem(productosNivel1);
		} else {
			productoNivel1 = "";
			productoNivel1Item = new SelectItem[0];
		}
		cambiarProductoNivel1();
	}

	/**
	 * Cargar proyectos.
	 */
	private void cargarProyectos() {
		if (grupoActual.getId() != null) {
			String idGrupo = grupoActual.getId().toString();
			proyectoAsociados = servicioGrupo.obtenerProyectosGrupoEdicion(idGrupo);
		}
	}

	/**
	 * Crear producto select item.
	 *
	 * @param lista the lista
	 * @return the select item[]
	 */
	private SelectItem[] crearProductoSelectItem(List lista) {
		SelectItem[] elementos;
		elementos = new SelectItem[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			ProductoTipo pro = (ProductoTipo) lista.get(i);
			String nombre = pro.getNombre();

			elementos[i] = new SelectItem(pro.getId(), nombre);
		}
		return elementos;
	}

	/**
	 * Dropdown P S process value change.
	 */
	// Carga información del producto seleccionado
	public void dropdownPS_processValueChange() {
		if (!productoLlave.equals("")) {
			Integer proInd = Integer.valueOf(productoLlave);
			productoSara = new ProductoSara();
			if (proInd != 0) {
				try {
					BeanUtils.copyProperties(productoSara, productosTmp.get(proInd - 1));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Eliminar producto.
	 */
	public void eliminarProducto() {
		if (productosEliminados == null) {
			productosEliminados = new ArrayList<GrupoProductoSara>();
		}
		if (productoSeleccionado != null) {
			productosEliminados.add(productoSeleccionado);
			productosAsociados.remove(productoSeleccionado);
		}
	}

	/**
	 * Eliminar proyecto.
	 */
	public void eliminarProyecto() {
		Iterator<String[]> i = proyectoAsociados.iterator();
		if (proyectosEliminados == null) {
			proyectosEliminados = new ArrayList<String[]>();
		}
		while (i.hasNext()) {
			String[] proyectoLista = i.next();
			if (proyectoSeleccionado[0].equals(proyectoLista[0])) {
				if (proyectoLista[3].equals("G")) {
					proyectoLista[3] = "D";// Borrar
					proyectosEliminados.add(proyectoLista);
				}
				proyectoAsociados.remove(proyectoLista);
				break;
			}
		}
	}

	/**
	 * Generar listado proyectos.
	 */
	private void generarListadoProyectos() {
		Iterator<Proyecto> i = listaProyectos.iterator();
		opcionesProyectos = new ArrayList<SelectItem>();
		while (i.hasNext()) {
			Proyecto proyecto = i.next();
			opcionesProyectos.add(new SelectItem(proyecto.getId(), proyecto.getId() + " - " + proyecto.getNombre()));
		}
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
	 * Gets the lista opciones.
	 *
	 * @return the lista opciones
	 */
	public List<SelectItem> getListaOpciones() {
		return listaOpciones;
	}

	/**
	 * Gets the nombre producto.
	 *
	 * @return the nombre producto
	 */
	public String getNombreProducto() {
		return nombreProducto;
	}

	/**
	 * Gets the nuevo proyecto.
	 *
	 * @return the nuevo proyecto
	 */
	public String getNuevoProyecto() {
		return nuevoProyecto;
	}

	/**
	 * Gets the opciones proyectos.
	 *
	 * @return the opciones proyectos
	 */
	public List<SelectItem> getOpcionesProyectos() {
		return opcionesProyectos;
	}

	/**
	 * Gets the prod sara.
	 *
	 * @return the prod sara
	 */
	public String getProdSara() {
		return prodSara;
	}

	/**
	 * Gets the producto llave.
	 *
	 * @return the producto llave
	 */
	public String getProductoLlave() {
		return productoLlave;
	}

	/**
	 * Gets the producto nivel 1.
	 *
	 * @return the productoNivel1
	 */
	public String getProductoNivel1() {
		return productoNivel1;
	}

	/**
	 * Gets the producto nivel 1 item.
	 *
	 * @return the productoNivel1Item
	 */
	public SelectItem[] getProductoNivel1Item() {
		return productoNivel1Item;
	}

	/**
	 * Gets the producto nivel 2.
	 *
	 * @return the productoNivel2
	 */
	public String getProductoNivel2() {
		return productoNivel2;
	}

	/**
	 * Gets the producto nivel 2 item.
	 *
	 * @return the productoNivel2Item
	 */
	public SelectItem[] getProductoNivel2Item() {
		return productoNivel2Item;
	}

	/**
	 * Gets the producto nivel 3.
	 *
	 * @return the productoNivel3
	 */
	public String getProductoNivel3() {
		return productoNivel3;
	}

	/**
	 * Gets the producto nivel 3 item.
	 *
	 * @return the productoNivel3Item
	 */
	public SelectItem[] getProductoNivel3Item() {
		return productoNivel3Item;
	}

	/**
	 * Gets the producto sara.
	 *
	 * @return the producto sara
	 */
	public ProductoSara getProductoSara() {
		return productoSara;
	}

	/**
	 * Gets the productos asociados.
	 *
	 * @return the productos asociados
	 */
	public List<GrupoProductoSara> getProductosAsociados() {
		return productosAsociados;
	}

	/**
	 * Gets the producto seleccionado.
	 *
	 * @return the producto seleccionado
	 */
	public GrupoProductoSara getProductoSeleccionado() {
		return productoSeleccionado;
	}

	/**
	 * Gets the productos eliminados.
	 *
	 * @return the productos eliminados
	 */
	public List<GrupoProductoSara> getProductosEliminados() {
		return productosEliminados;
	}

	/**
	 * Gets the productos sara items.
	 *
	 * @return the productos sara items
	 */
	public SelectItem[] getProductosSaraItems() {
		return productosSaraItems;
	}

	/**
	 * Gets the productos tmp.
	 *
	 * @return the productos tmp
	 */
	public List<VProductoSara> getProductosTmp() {
		return productosTmp;
	}

	/**
	 * Gets the proyecto asociados.
	 *
	 * @return the proyecto asociados
	 */
	public List<String[]> getProyectoAsociados() {
		return proyectoAsociados;
	}

	/**
	 * Gets the proyecto seleccionado.
	 *
	 * @return the proyecto seleccionado
	 */
	public String[] getProyectoSeleccionado() {
		return proyectoSeleccionado;
	}

	

	/**
	 * Guardar.
	 *
	 * @param enviar the enviar
	 * @return the string
	 */
	public String guardar(boolean enviar) {
		boolean nuevoEstado = false;
		if(esCadenaVacia(grupoActual.getPertinencia()) || esCadenaVacia(grupoActual.getNecesidad())) {
			mensajeError("Los campos de Necesidad Académica y Pertinencia de las Actividades del grupo son obligatorias");
			return "";
		}
		if (grupoActual.getEstadoGrupo() != null && (grupoActual.getEstadoGrupo().getId().equals(EstadoGrupo.INGRESANDO) || grupoActual.getEstadoGrupo().getId().equals(EstadoGrupo.CORRECCIONES))
				&& enviar) {
			nuevoEstado = true;
			grupoActual.setEstadoGrupo(new EstadoGrupo());
			grupoActual.getEstadoGrupo().setId("S");
			SolicitudGrupo solicitudGrupo = new SolicitudGrupo();
			solicitudGrupo.setFecha(getToday());
			solicitudGrupo.setGrupo(grupoActual);
			solicitudGrupo.setRespuesta(SolicitudGrupo.TRAMITE);
			TipoSolicitudGrupo ts = new TipoSolicitudGrupo();
			ts.setId(TipoSolicitudGrupo.CREACION_GRUPO);
			solicitudGrupo.setTipoSolicitud(ts);
			solicitudGrupo.setSolicitante(personaActual);
			solicitudGrupo.setDependenciaRevision(grupoActual.getLider().getInvestigador().getDependencia());
			servicioGeneral.guardarObjeto(solicitudGrupo);
		}

		// Se guardan los proyectos.
		if (proyectoAsociados != null && proyectoAsociados.size() > 0) {
			Iterator<String[]> i = proyectoAsociados.iterator();
			while (i.hasNext()) {
				String[] proyecto = i.next();
				if (proyecto[3].equals("N")) {
					String consulta = "INSERT INTO " + "HER_GRUPO_PROYECTO(GRU_ID,PRY_ID,GRP_OPCION_ELIMINAR) "
							+ "VALUES ('" + grupoActual.getId() + "','" + proyecto[0] + "','S')";
					servicioGeneral.ejecutarSentencia(consulta);
				}
			}
		}

		// Se eliminan los proyectos
		if (proyectosEliminados != null && proyectosEliminados.size() > 0) {
			Iterator<String[]> i = proyectosEliminados.iterator();
			while (i.hasNext()) {
				String[] proyecto = i.next();
				if (proyecto[3].equals("D")) {
					String consulta = "DELETE FROM " + "HER_GRUPO_PROYECTO " + "WHERE GRU_ID = '" + grupoActual.getId()
							+ "' AND PRY_ID = '" + proyecto[0] + "'";
					servicioGeneral.ejecutarSentencia(consulta);
				}
			}
			proyectosEliminados.clear();
		}

		// Se guardan los productos.
		if (productosAsociados != null && productosAsociados.size() > 0) {
			Iterator<GrupoProductoSara> i = productosAsociados.iterator();
			while (i.hasNext()) {
				GrupoProductoSara producto = i.next();
				servicioGeneral.guardarObjeto(producto);
			}
		}

		// Se eliminan los productos
		if (productosEliminados != null && productosEliminados.size() > 0) {
			Iterator<GrupoProductoSara> i = productosEliminados.iterator();
			while (i.hasNext()) {
				GrupoProductoSara producto = i.next();
				if (producto.getId() != null)
					servicioGeneral.eliminarObjeto(producto);
			}
			productosEliminados.clear();
		}

		if (nuevoEstado) {
			notificarVIF();
		}
		servicioGrupo.guardarGrupo(grupoActual);
		sesion.removeAttribute("manejadorEdicionGrupoVisionPrioridadesPerspectiva");
		sesion.removeAttribute("manejadorMenuFormularioGrupos");
		sesion.removeAttribute("manejadorGruposInvestigador");

		Persona persona = (Persona) sesion.getAttribute("persona");
		guardarHistoricoFormularioGrupo(grupoActual, persona, "4");
		if (nuevoEstado) {
			cambiosGruposHistorico = "eg,";
			justificacionGrupoHistorico = "Se completa la información solicitada..";
			guardarHistoricoEstadoGrupo(grupoActual, persona);
		}
		if (!lider) {
			notificarCambiosEstLider();
		}
		if (enviar) {
			return "consultaGrupos";
		} else
			return "";
	}

	private void notificarCambiosEstLider() {
		CorreoPlantilla correoActual = new CorreoPlantilla();
		correoActual = cargarPlantilla(320);
		String cuerpoCorreo = correoActual.getCuerpo().replaceAll("<<EST>>",
				((Investigador) sesion.getAttribute("persona")).getNombreCompleto());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", grupoActual.getId().toString());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<GRUPO>>", grupoActual.getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<FORM>>", "INFORMACIÓN ESPECÍFICA");
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

	@SuppressWarnings({ "deprecation", "unchecked" })
	private void notificarVIF() {
		List<Investigador> personasVIF = new ArrayList<Investigador>();
		if(!grupoActual.getSede().isEsSedePresenciaNacional()) {
		personasVIF = servicioGeneral
				.obtenerObjetos("select i from Investigador i, PersonaRol pr where pr.documento=i.id.documento"
						+ " and pr.tipoDocumento=i.id.tipoDocumento and pr.nombre='AF' and i.dependencia.id='"
						+ grupoActual.getDependencia().getId() + "'");
		}else {
			 personasVIF = servicioGeneral
					.obtenerObjetos("select i from Investigador i, PersonaRol pr where pr.documento=i.id.documento"
							+ " and pr.tipoDocumento=i.id.tipoDocumento and pr.nombre='AD' and i.dependencia.id='"
							+ grupoActual.getSede().getId() + "'");
		}
		CorreoPlantilla correoActual = new CorreoPlantilla();
		correoActual = cargarPlantilla(309);
		String cuerpoCorreo = correoActual.getCuerpo().replaceAll("<<NOMBRE>>", grupoActual.getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<LIDER>>", grupoActual.getResponsable().getNombreCompleto());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<FACULTAD>>", grupoActual.getDependencia().getFacultad().getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<SEDE>>", grupoActual.getSede().getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", grupoActual.getId().toString());
		for (Investigador i : personasVIF) {
			Correo mensaje = new Correo();
			mensaje.setOrigen(Correo.CORREO_HERMES);
			// mensaje.adicionarCopiaOculta(Correo.CORREO_HERMES);
			mensaje.setAsunto(correoActual.getAsunto().replaceAll("<<ID>>", grupoActual.getId().toString()));
			mensaje.setCuerpo(cuerpoCorreo);
			mensaje.adicionarDireccion(i.getEmail());
			servicioCorreo.enviarCorreo(mensaje);
		}
	}

	/**
	 * Guardar edicion grupo.
	 *
	 * @return the string
	 */
	public String guardarEdicionGrupo() {
		return guardar(false);
	}

	/**
	 * Guardar enviar.
	 *
	 * @return the string
	 */
	public String guardarEnviar() {
		return guardar(true);
	}

	/**
	 * Publicar mensaje.
	 *
	 * @param error    the error
	 * @param severity the severity
	 */
	private void publicarMensaje(String error, Severity severity) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage mensaje = new FacesMessage(severity, error, "");
		context.addMessage("mensajeError", mensaje);
	}

	/**
	 * Salir.
	 *
	 * @return the string
	 */
	public String salir() {
		return "consultaGrupos";
	}

	/**
	 * Sets the grupo actual.
	 *
	 * @param grupoActual the new grupo actual
	 */
	public void setGrupoActual(Grupo grupoActual) {
		this.grupoActual = grupoActual;
	}

	/**
	 * Sets the lista opciones.
	 *
	 * @param listaOpciones the new lista opciones
	 */
	public void setListaOpciones(List<SelectItem> listaOpciones) {
		this.listaOpciones = listaOpciones;
	}

	/**
	 * Sets the nombre producto.
	 *
	 * @param nombreProducto the new nombre producto
	 */
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	/**
	 * Sets the nuevo proyecto.
	 *
	 * @param nuevoProyecto the new nuevo proyecto
	 */
	public void setNuevoProyecto(String nuevoProyecto) {
		this.nuevoProyecto = nuevoProyecto;
	}

	/**
	 * Sets the prod sara.
	 *
	 * @param prodSara the new prod sara
	 */
	public void setProdSara(String prodSara) {
		this.prodSara = prodSara;
	}

	/**
	 * Sets the producto llave.
	 *
	 * @param productoLlave the new producto llave
	 */
	public void setProductoLlave(String productoLlave) {
		this.productoLlave = productoLlave;
	}

	/**
	 * Sets the producto nivel 1.
	 *
	 * @param productoNivel1 the productoNivel1 to set
	 */
	public void setProductoNivel1(String productoNivel1) {
		this.productoNivel1 = productoNivel1;
	}

	/**
	 * Sets the producto nivel 2.
	 *
	 * @param productoNivel2 the productoNivel2 to set
	 */
	public void setProductoNivel2(String productoNivel2) {
		this.productoNivel2 = productoNivel2;
	}

	/**
	 * Sets the producto nivel 3.
	 *
	 * @param productoNivel3 the productoNivel3 to set
	 */
	public void setProductoNivel3(String productoNivel3) {
		this.productoNivel3 = productoNivel3;
	}

	/**
	 * Sets the producto sara.
	 *
	 * @param productoSara the new producto sara
	 */
	public void setProductoSara(ProductoSara productoSara) {
		this.productoSara = productoSara;
	}

	/**
	 * Sets the productos asociados.
	 *
	 * @param productosAsociados the new productos asociados
	 */
	public void setProductosAsociados(List<GrupoProductoSara> productosAsociados) {
		this.productosAsociados = productosAsociados;
	}

	/**
	 * Sets the producto seleccionado.
	 *
	 * @param productoSeleccionado the new producto seleccionado
	 */
	public void setProductoSeleccionado(GrupoProductoSara productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

	/**
	 * Sets the productos eliminados.
	 *
	 * @param productosEliminados the new productos eliminados
	 */
	public void setProductosEliminados(List<GrupoProductoSara> productosEliminados) {
		this.productosEliminados = productosEliminados;
	}

	/**
	 * Sets the productos sara items.
	 *
	 * @param productosSaraItems the new productos sara items
	 */
	public void setProductosSaraItems(SelectItem[] productosSaraItems) {
		this.productosSaraItems = productosSaraItems;
	}

	/**
	 * Sets the productos tmp.
	 *
	 * @param productosTmp the new productos tmp
	 */
	public void setProductosTmp(List<VProductoSara> productosTmp) {
		this.productosTmp = productosTmp;
	}

	/**
	 * Sets the proyecto seleccionado.
	 *
	 * @param proyectoSeleccionado the new proyecto seleccionado
	 */
	public void setProyectoSeleccionado(String[] proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}

	/**
	 * Siguiente.
	 *
	 * @return the string
	 */
	public String siguiente() {
		servicioGrupo.guardarGrupo(grupoActual);
		return "irAProyectosNoHermesProgramasPublicaciones";
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
}
