package co.edu.unal.hermes.vista.administrador;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Dominio;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.SolicitudFuente;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorCambioEstadosAdministrador.
 *
 * @author Liliana Olarte
 * @date 10/01/2016
 */

public class ManejadorFuentesAdministrador extends ManejadorBase {

	private static final long serialVersionUID = 359766235953042524L;

	/** The estados item. */
	private SelectItem[] estadosItem;

	/** The naturaleza item. */
	private SelectItem[] naturalezaItem;

	/** The justificacion. */
	private String justificacion;

	/** The nit fuente. */
	private String nitFuente;

	/** The nombre fuente. */
	private String nombreFuente;

	/** The estado seleccionado. */
	private String estadoSeleccionado;

	/** The fuente actual. */
	private FuenteFinanciacion fuenteActual;

	/** The lista de fuentes encontradas. */
	List<FuenteFinanciacion> fuentes = new ArrayList<FuenteFinanciacion>();

	private FuenteFinanciacion fuenteSeleccionada;

	private boolean verEditarFuente;
	/** The tipos naturaleza fuente item. */
	public SelectItem[] tiposNaturalezaFuenteItem;

	/** The tipos tiposFuenteFinanciacionItem. */
	public SelectItem[] tiposFuenteFinanciacionItem;

	/** The tipos caracterFuenteFinanciacionItem. */
	public SelectItem[] caracterFuenteFinanciacionItem;

	/* Mensaje */
	private UIComponent message2;

	private List<SolicitudFuente> listaSolicitudes = new ArrayList<SolicitudFuente>();
	private SolicitudFuente solicitudSeleccionada;
	private int numeroSolicitudes;
	private SelectItem[] listaPaisesItem;

	/* Constructor */
	public ManejadorFuentesAdministrador() {

		// Estados
		cargarEstados();

		// Cargar solicitudes
		listaSolicitudes = servicioGeneral.obtenerObjetos(SolicitudFuente.class,
				"from SolicitudFuente s where s.estado='Ingresado' order by s.id");
		if (listaSolicitudes.size() > 0) {
			setNumeroSolicitudes(listaSolicitudes.size());
			System.out.println(numeroSolicitudes);
		}

		List<Pais> listaPaises;
		listaPaises = cargarPaises(true);
		listaPaisesItem = new SelectItem[listaPaises.size() + 1];
		listaPaisesItem[0] = new SelectItem("00", "Seleccionar país");
		for (int i = 1; i <= listaPaises.size(); i++) {
			Pais p = (Pais) listaPaises.get(i-1);
			listaPaisesItem[i] = new SelectItem(p.getId(), p.getNombre());
		}

	}

	/**
	 * Cargar estados.
	 */
	private void cargarEstados() {
		List listaEstados = new ArrayList<DominioDetalle>();
		listaEstados = servicioGeneral.obtenerObjetos(
				"select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.id ='137' order by dd.descripcion");
		estadosItem = new SelectItem[listaEstados.size()];
		for (int i = 0; i < listaEstados.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaEstados.get(i);
			estadosItem[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}

	}

	/**
	 * Buscar fuente.
	 */
	public void buscarFuente() {

		String id = "0";
		fuenteActual = null;
		fuenteActual = new FuenteFinanciacion();

		if (nombreFuente != null) {
			// id = nitFuente.trim();
			buscarFuentePorNombre(nombreFuente);
		}

	}

	/**
	 * Buscar fuente.
	 *
	 * @param nombre
	 */
	public void buscarFuentePorNombre(String nombre) {
		
		fuentes = new ArrayList<FuenteFinanciacion>();

		// Si se carga un numero correcto se busca
		fuentes = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class,
				"select f " + " from FuenteFinanciacion f where upper(f.descripcion) like '%" + nombre.toUpperCase().trim() + "%'");
		if (fuentes != null && fuentes.size() > 0) {
			fuenteActual = new FuenteFinanciacion();
			fuenteActual = fuentes.get(0);
			estadoSeleccionado = fuenteActual.getInternaExterna();

		} else {
			fuenteActual = new FuenteFinanciacion();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No se ha encontrado una fuente con el nombre ingresado.", "");
			mostrarMensaje(message, null);
		}

	}

	/**
	 * Buscar fuente.
	 *
	 * @param nit
	 */
	public void buscarFuentePorNit() {

		fuentes = new ArrayList<FuenteFinanciacion>();
		String codigo = nitFuente;
		
		fuentes = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class,
				"select f " + " from FuenteFinanciacion f where f.nit like '%" + codigo.trim() + "%'");
		
		if (fuentes != null && fuentes.size() > 0) {
			fuenteActual = new FuenteFinanciacion();
			fuenteActual = fuentes.get(0);
			estadoSeleccionado = fuenteActual.getInternaExterna();

		} else {
			fuenteActual = new FuenteFinanciacion();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No se ha encontrado una fuente con el nit ingresado.", "");
			mostrarMensaje(message, null);
		}

	}
	
	public void buscarFuentePorNit(String codigo ) {
		
		fuentes = new ArrayList<FuenteFinanciacion>();

		// Si se carga un numero correcto se busca
		fuentes = servicioGeneral.obtenerObjetosLimitado(FuenteFinanciacion.class,
				"select #id f.id, #descripcion f.descripcion, #internaExterna f.internaExterna"
						+ " from FuenteFinanciacion f where f.nit = '" + codigo.trim() + "'");
	
		if (fuentes != null && fuentes.size() > 0) {
			fuenteActual = fuentes.get(0);
			estadoSeleccionado = fuenteActual.getInternaExterna();

		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No se ha encontrado una fuente con el nit ingresado.", "");
			mostrarMensaje(message, null);
		}

	}

	/**
	 * Guardar cambios a la fuente
	 */
	public void guardarFuente() {
		try {

			if (fuenteActual != null) {
				if (!fuenteActual.getInternaExterna().equals("0")) {

					if (!fuenteActual.getNaturaleza().equals("0")) {
						
						
						if(fuenteActual.getPais() !=null && fuenteActual.getPais().getId()!=null && !esCadenaVacia(fuenteActual.getPais().getId()) && !fuenteActual.getPais().getId().equals("00")) {

							if(fuenteActual.getCaracter() !=null && !esCadenaVacia(fuenteActual.getCaracter())) {
								
								if(fuenteActual.getDireccion() !=null && !esCadenaVacia(fuenteActual.getDireccion())) {
									servicioGeneral.guardarObjeto(fuenteActual);
								FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
										"La fuente ha sido actualizada con éxito.", "");
								mostrarMensaje(message, message2);
								buscarFuente();
								verEditarFuente = false;
								}else {
									FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
											"Ingrese la dirección de la fuente.", "");
									mostrarMensaje(message, message2);
								}
							}else {
								FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Seleccione el caracter de la fuente.", "");
								mostrarMensaje(message, message2);
							}
							
						}else {
							FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Seleccione el país de la fuente.", "");
							mostrarMensaje(message, message2);
						}

					} else {
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Seleccione la naturaleza de la fuente.", "");
						mostrarMensaje(message, message2);
					}

				} else {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Seleccione la tipología de la fuente.", "");
					mostrarMensaje(message, message2);
				}

			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No ha sido posible actualizar la fuente.", "");
			mostrarMensaje(message, message2);
		}
	}

	/**
	 * Guardar nuevo estado.
	 */
	public void guardarNuevoEstado() {
		if (guardarEstados(fuenteActual, estadoSeleccionado)) {
			buscarFuentePorNit(fuenteActual.getId());
		} else {

		}
	}

	/*
	 * Seleccionar fuente para editarla
	 */
	public void editarFuente() {
		fuenteActual = fuenteSeleccionada;
		if (fuenteActual.getPais() == null) {
			fuenteActual.setPais(new Pais());
			fuenteActual.getPais().setId("00");
		}
		setVerEditarFuente(true);
		// cargarEstados();
		// buscarFuente();
	}

	/*
	 * Seleccionar fuente para eliminarla
	 */
	public void eliminarFuente() {

		try {
			servicioGeneral
					.eliminar("DELETE HER_FUENTE_FINANCIACION F WHERE F.FFI_ID = '" + fuenteSeleccionada.getId() + "'");
			fuentes.remove(fuenteSeleccionada);
			verEditarFuente = false;

		} catch (SQLException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hubo un error al eliminar la fuente",
					"");
			mostrarMensaje(message, null);
			e.printStackTrace();
		}

	}

	/**
	 * Guardar estado FUENTE.
	 */
	private boolean guardarEstados(FuenteFinanciacion fuente, String estado) {

		// Se crea script para cambio de estado
		String sql = "update HER_FUENTE_FINANCIACION set FFI_INTERNA_EXTERNA = '" + estado + "' where FFI_ID = '"
				+ fuente.getId() + "'";

		try {
			servicioGeneral.ejecutarSentencia(sql);
			justificacion = "";
			return true;
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Hubo un error al guardar el nuevo estado", "");
			mostrarMensaje(message, null);
			e.printStackTrace();
			return false;
		}
	}

	// Editar solicitudes de creacion
	public String editarSolicitud() {

		sesion.setAttribute("solicitudEditable", solicitudSeleccionada.getId());
		sesion.removeAttribute("ManejadorEditarFuente");
		return "revisarSolicitudFuente";
	}

	// Consultar solicitudes de creacion
	public String consultarSolicitud() {

		sesion.setAttribute("solicitudEditable", solicitudSeleccionada.getId());
		sesion.removeAttribute("ManejadorEditarFuente");
		return "consultarSolicitudFuente";
	}

	/**
	 * Gets the jsutificacion.
	 *
	 * @return the jsutificacion
	 */
	public String getJustificacion() {
		return justificacion;
	}

	/**
	 * Sets the jsutificacion.
	 *
	 * @param jsutificacion
	 *            the new jsutificacion
	 */
	public void setJustificacion(String jsutificacion) {
		this.justificacion = jsutificacion;
	}

	/**
	 * Gets the
	 *
	 * @return the nitFuente
	 */
	public String getnitFuente() {
		return nitFuente;
	}

	/**
	 * Sets
	 *
	 * @param nitFuente
	 *            the nitFuente to set
	 */
	public void setnitFuente(String nitFuente) {
		this.nitFuente = nitFuente;
	}

	/**
	 * Gets the estados item.
	 *
	 * @return the estados item
	 */
	public SelectItem[] getEstadosItem() {
		return estadosItem;
	}

	/**
	 * Gets the estado seleccionado.
	 *
	 * @return the estado seleccionado
	 */
	public String getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	/**
	 * Sets the estado seleccionado.
	 *
	 * @param estadoSeleccionado
	 *            the new estado seleccionado
	 */
	public void setEstadoSeleccionado(String estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	/**
	 * Mostrar mensaje.
	 *
	 * @param msg
	 *            the msg
	 * @param component
	 *            the component
	 */
	protected void mostrarMensaje(FacesMessage msg, UIComponent component) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (component == null) {
			context.addMessage(null, msg);
		} else {
			context.addMessage(component.getClientId(context), msg);
		}
	}

	public String getNombreFuente() {
		return nombreFuente;
	}

	public void setNombreFuente(String nombreFuente) {
		this.nombreFuente = nombreFuente;
	}

	public String getNitFuente() {
		return nitFuente;
	}

	public void setNitFuente(String nitFuente) {
		this.nitFuente = nitFuente;
	}

	public FuenteFinanciacion getFuenteActual() {
		return fuenteActual;
	}

	public void setFuenteActual(FuenteFinanciacion fuenteActual) {
		this.fuenteActual = fuenteActual;
	}

	public List<FuenteFinanciacion> getFuentes() {
		return fuentes;
	}

	public void setFuentes(List<FuenteFinanciacion> fuentes) {
		this.fuentes = fuentes;
	}

	public FuenteFinanciacion getFuenteSeleccionada() {
		return fuenteSeleccionada;
	}

	public void setFuenteSeleccionada(FuenteFinanciacion fuenteSeleccionada) {
		this.fuenteSeleccionada = fuenteSeleccionada;
	}

	public void setEstadosItem(SelectItem[] estadosItem) {
		this.estadosItem = estadosItem;
	}

	public boolean isVerEditarFuente() {
		return verEditarFuente;
	}

	public void setVerEditarFuente(boolean verEditarFuente) {
		this.verEditarFuente = verEditarFuente;
	}

	public UIComponent getMessage2() {
		return message2;
	}

	public void setMessage2(UIComponent message2) {
		this.message2 = message2;
	}

	public List<SolicitudFuente> getListaSolicitudes() {
		return listaSolicitudes;
	}

	public void setListaSolicitudes(List<SolicitudFuente> listaSolicitudes) {
		this.listaSolicitudes = listaSolicitudes;
	}

	public SolicitudFuente getSolicitudSeleccionada() {
		return solicitudSeleccionada;
	}

	public void setSolicitudSeleccionada(SolicitudFuente solicitudSeleccionada) {
		this.solicitudSeleccionada = solicitudSeleccionada;
	}

	public int getNumeroSolicitudes() {
		return numeroSolicitudes;
	}

	public void setNumeroSolicitudes(int numeroSolicitudes) {
		this.numeroSolicitudes = numeroSolicitudes;
	}

	public SelectItem[] getNaturalezaItem() {
		return crearListaItemDominioDetalle(Dominio.NAT_ENTIDAD);
	}

	public void setNaturalezaItem(SelectItem[] naturalezaItem) {
		this.naturalezaItem = naturalezaItem;
	}

	public SelectItem[] getTiposNaturalezaFuenteItem() {
		return crearListaItemDominioDetalle(Dominio.NAT_ENTIDAD);
	}

	public SelectItem[] getTiposFuenteFinanciacionItem() {
		return crearListaItemDominioDetalle(Dominio.TIPO_FUENTE_FINANCIACION);
	}

	public SelectItem[] getCaracterFuenteFinanciacionItem() {
		return crearListaItemDominioDetalle(Dominio.CARACTER_FUENTE_FINANCIACION);
	}

	public SelectItem[] getListaPaisesItem() {
		return listaPaisesItem;
	}

	public void setListaPaisesItem(SelectItem[] listaPaisesItem) {
		this.listaPaisesItem = listaPaisesItem;
	}

}
