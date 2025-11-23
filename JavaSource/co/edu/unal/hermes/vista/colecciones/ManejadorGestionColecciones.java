package co.edu.unal.hermes.vista.colecciones;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.ArchivoColeccion;
import co.edu.unal.hermes.modelo.ArchivoGestionColeccion;
import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.ColeccionGestion;
import co.edu.unal.hermes.modelo.ColeccionPersona;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dominio;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.HistoricoEstadoGestionColeccion;
import co.edu.unal.hermes.modelo.IdDominioDetalle;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoInvestigador;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudInvestigador;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorGestionColecciones extends ManejadorBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Persona persona;
	private String idColeccion;
	private Coleccion coleccion;
	private UploadedFile archivoCargar;
	private List<ArchivoGestionColeccion> listaArchivos;
	private ArchivoColeccion archivoSeleccionado;
	private SelectItem[] categoriaItems;
	private ColeccionGestion gestion; // pendiente revisar
	private boolean esConsulta;
	private List<SelectItem> listaAutoridades;
	private String idAutoridad;
	private String idTipoGestion;
	private String idEstadoActual;
	private String aceptaPublicacion;
	private InvestigadorInterno directorPropuesto;

	/** The lista solicitudes proyecto. */
	private List<ColeccionGestion> listaGestionesColeccion;

	org.primefaces.model.UploadedFile archivoCargado;
	private ArchivoGestionColeccion archivoGestion;

	/** The solicitud seleccionada. */
	private ColeccionGestion gestionSeleccionada;

	/** The vista solicitud. */
	private ColeccionGestion vistaGestion;

	private String tipoSolicitudUsada;

	private List<SelectItem> tipoDocumentoItem;

	/** The tipo documento. */
	private String tipoDocumento;

	/** The documento identidad. */
	private String documentoIdentidad;

	public ManejadorGestionColecciones() {

		persona = (Persona) sesion.getAttribute("persona");
		gestion = new ColeccionGestion();
		DominioDetalle tipo = new DominioDetalle();
		IdDominioDetalle idTipo = new IdDominioDetalle();
		tipo.setIdentificador(idTipo);
		gestion.setTipo(tipo);
		cargarTiposGestiones();
		cargarEntidadesBiodiversidad();
		cargarTiposDocumento();

		coleccion = (Coleccion) sesion.getAttribute("colEdit");

		cargarGestionesColeccion();
	}

	public void cargarTiposDocumento() {
		List<TipoDocumento> tipos = servicioGeneral.obtenerTiposDeDocumento();

		Iterator<TipoDocumento> i = tipos.iterator();
		tipoDocumentoItem = new ArrayList<SelectItem>();
		while (i.hasNext()) {
			TipoDocumento tipoDocumentoNuevo = i.next();
			if(!tipoDocumentoNuevo.getId().equals("U"))
			tipoDocumentoItem.add(new SelectItem(tipoDocumentoNuevo.getId(), tipoDocumentoNuevo.getNombre()));
		}
	}

	public void cargarTiposGestiones() {

		List<DominioDetalle> lista;
		String consulta = "select dd from Dominio d, DominioDetalle dd where "
				+ "d.id = dd.identificador.id and d.tipo like 'TIPO_SOLICITUD_COLECCION' "
				+ " and dd.estado in ('A') order by dd.descripcion";

		lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		if (!esListaVacia(lista)) {
			categoriaItems = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				DominioDetalle dominio = (DominioDetalle) lista.get(i);
				categoriaItems[i] = new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion());
			}
		}
	}

	private DominioDetalle cargarTipoGestion(String tipo) {
		List<DominioDetalle> lista;
		String consulta = "select dd from Dominio d, DominioDetalle dd where "
				+ "d.id = dd.identificador.id and d.tipo like 'TIPO_SOLICITUD_COLECCION' "
				+ " and dd.identificador.tipo = '" + tipo + "' order by dd.descripcion";

		lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		if (lista != null && lista.size() > 0) {
			return lista.get(0);
		}

		return null;
	}

	private void cargarGestionesColeccion() {
		if (coleccion != null) {
			listaGestionesColeccion = servicioGeneral.obtenerObjetos(ColeccionGestion.class,
					"select g from ColeccionGestion g where g.idColeccion = '" + coleccion.getId() + "'"
							+ " and g.estado.identificador.tipo <> '" + ColeccionGestion.ESTADO_ELIMINADA + "' "
							+ "order by g.fechaRegistro DESC");
		}
	}

	private void cargarArchivosGestion(Long idGestion) {
		if (idGestion != null) {
			listaArchivos = servicioGeneral.obtenerObjetos(ArchivoGestionColeccion.class,
					"select a from ArchivoGestionColeccion a where a.gestion.id = '" + idGestion + "'"
							+ "and a.fechaBorrado is null " + " order by a.fecha asc");
		}
	}

	private FuenteFinanciacion cargarFuenteFinanciacion(String id) {
		List<FuenteFinanciacion> fuentes = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class,
				"select f from FuenteFinanciacion f where f.id = '" + id + "'");

		if (fuentes != null && fuentes.size() > 0) {
			return fuentes.get(0);
		}

		return null;
	}

	public void cargarEntidadesBiodiversidad() {

		listaAutoridades = new ArrayList<SelectItem>();
		List<FuenteFinanciacion> listaEntidadesExt = cargarEntidadesExternas(
				"select e from FuenteFinanciacion e where e.autoridadCompetenteBiodiversidad = '1'");

		if (!esListaVacia(listaEntidadesExt)) {
			for (int i = 0; i < listaEntidadesExt.size(); i++) {
				FuenteFinanciacion entidad = (FuenteFinanciacion) listaEntidadesExt.get(i);
				listaAutoridades.add(new SelectItem(entidad.getId(), entidad.getDescripcion()));
			}
		}
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getIdColeccion() {
		return idColeccion;
	}

	public void setIdColeccion(String idColeccion) {
		this.idColeccion = idColeccion;
	}

	public Coleccion getColeccion() {
		return coleccion;
	}

	public void setColeccion(Coleccion coleccion) {
		this.coleccion = coleccion;
	}

	public UploadedFile getArchivoCargar() {
		return archivoCargar;
	}

	public void setArchivoCargar(UploadedFile archivoCargar) {
		this.archivoCargar = archivoCargar;
	}

	public List<ArchivoGestionColeccion> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<ArchivoGestionColeccion> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public ArchivoColeccion getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(ArchivoColeccion archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public SelectItem[] getCategoriaItems() {
		return categoriaItems;
	}

	public void setCategoriaItems(SelectItem[] categoriaItems) {
		this.categoriaItems = categoriaItems;
	}

	public ColeccionGestion getGestion() {
		return gestion;
	}

	public void setGestion(ColeccionGestion gestion) {
		this.gestion = gestion;
	}

	public boolean isEsConsulta() {
		return esConsulta;
	}

	public void setEsConsulta(boolean esConsulta) {
		this.esConsulta = esConsulta;
	}

	public void adjuntarArchivo(FileUploadEvent event) {
		archivoCargado = event.getFile();
		ArchivoGestionColeccion aa = insertarArchivoGestionColeccion(0, archivoCargado, "DI");
		if (aa != null) {
			if (listaArchivos == null) {
				listaArchivos = new ArrayList<ArchivoGestionColeccion>();
			}
			listaArchivos.add(aa);
		}
	}

	private boolean validarExisteTipoYAutoridad(String tipo, String autoridad, Long idGestion) {
		if (listaGestionesColeccion != null && listaGestionesColeccion.size() > 0) {
			for (int i = 0; i < listaGestionesColeccion.size(); i++) {
				ColeccionGestion gestion = listaGestionesColeccion.get(i);

				// SI es el mismo ID que se esta editando se salta.
				if (idGestion != null && idGestion == gestion.getId()) {
					continue;
				}
				// Si es una gestión eliminada o rechazada se salta.
				if (gestion.getEstado().getIdentificador().getTipo() == ColeccionGestion.ESTADO_ELIMINADA
						|| gestion.getEstado().getIdentificador().getTipo() == ColeccionGestion.ESTADO_RECHAZADA) {
					continue;
				}
				// Se validan los datos
				if (gestion.getTipo().getIdentificador().getTipo().equals(tipo)
						&& gestion.getAutoridad().getId().equals(autoridad)) {
					return true;
				}
			}
		}
		return false;
	}

	public org.primefaces.model.UploadedFile getArchivoCargado() {
		return archivoCargado;
	}

	public void setArchivoCargado(org.primefaces.model.UploadedFile archivoCargado) {
		this.archivoCargado = archivoCargado;
	}

	public ArchivoGestionColeccion getArchivoGestion() {
		return archivoGestion;
	}

	public void setArchivoGestion(ArchivoGestionColeccion archivoGestion) {
		this.archivoGestion = archivoGestion;
	}

	public void descargarArchivo() {
		if (archivoGestion != null) {
			descargarArchivoGestionColeccion(archivoGestion);
		}
	}

	/**
	 * Elimina archivos de la solicitud
	 */
	public void eliminarArchivo() {

		listaArchivos.remove(archivoGestion);
		// archivoGestion.setGestion(0L);
		archivoGestion.setFechaBorrado(new Date());
		servicioGeneral.guardarObjeto(archivoGestion);
	}

	public List<SelectItem> getListaAutoridades() {
		return listaAutoridades;
	}

	public void setListaAutoridades(List<SelectItem> listaAutoridades) {
		this.listaAutoridades = listaAutoridades;
	}

	/**
	 * Ver agregar solicitud.
	 */
	public void verAgregarGestion() {
		limpiarCampos();
		inicializarSolicitud(null);
		setVistaGestion(gestion);
		esConsulta = false;
	}

	/**
	 * Checks if is existe vista solicitud.
	 *
	 * @return true, if is existe vista solicitud
	 */
	public boolean isExisteVistaGestion() {
		if (vistaGestion != null)
			return true;
		return false;
	}

	/**
	 * Inicializar nueva solicitud.
	 *
	 * @param gestion the solicitud
	 */
	private void inicializarSolicitud(ColeccionGestion gestion) {
		limpiarCampos();
		if (gestion != null) {
			this.gestion = gestion;
		} else {
			gestion = new ColeccionGestion();
			gestion.setId(null);
			gestion.setFechaRegistro(new Date());
			DominioDetalle tipoSolicitud = new DominioDetalle();
			IdDominioDetalle id = new IdDominioDetalle();
			id.setTipo(null);
			id.setId(Dominio.ID_DOMINIO_TIPO_GESTION_COL);
			tipoSolicitud.setIdentificador(id);
			gestion.setTipo(tipoSolicitud);

			FuenteFinanciacion fuenteFinanciacion = new FuenteFinanciacion();
			fuenteFinanciacion.setId("");

			this.gestion = gestion;
		}
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}
	

	public void buscarNuevoDirector() {
		buscarNuevoDirectorMensaje(true, true);
	}
	
	public void buscarNuevoDirectorMensaje(boolean conMensaje, boolean forzarInterno) {

		if ((esCadenaVacia(documentoIdentidad) || esCadenaVacia(tipoDocumento)) && conMensaje) {
			mensajeError("Debe indicar el tipo y número de documento de identidad del nuevo director.");
			return;
		}
		
		IdPersona id = new IdPersona();
		id.setDocumento(documentoIdentidad.trim());
		id.setTipoDocumento(tipoDocumento);

		InvestigadorInterno investigadorInterno = servicioPersona.obtenerInvestigadorInterno(id);
		
		if (investigadorInterno == null) {
			if(conMensaje) {
				mensajeError("No se ha encontrado ningún investigador con el número de documento ingresado.");
			}
			return;
		}
		
		if(forzarInterno && !investigadorInterno.getInterno().equals("S")) {
			if(conMensaje) {
				mensajeError("No se ha encontrado ningún investigador interno con el número de documento ingresado.");
			}
			return;
		}
		
		directorPropuesto = investigadorInterno;

	}

	public void limpiarCampos() {
		gestion = null;
		idTipoGestion = "";
		idAutoridad = "";
		listaArchivos = null;
		idEstadoActual = "";
		directorPropuesto = null;
		tipoDocumento = "";
		documentoIdentidad = "";
		aceptaPublicacion = "";
	}

	public void guardarParcialmente() {
		guardar(false);
	}

	public void enviarSolicitud() {
		guardar(true);
	}

	public void guardar(boolean enviarDefinitivamente) {

		// Se validan campos
		if (esCadenaVacia(this.idTipoGestion)) {
			mensajeError("Debe seleccionar el tipo de solicitud.");
			return;
		}

		if (esCadenaVacia(this.gestion.getJustificacion()) && enviarDefinitivamente) {
			mensajeError("Debe ingresar la Justificación.");
			return;
		}

		if (this.idTipoGestion.equals("RC") || this.idTipoGestion.equals("AC")) {
			if (esCadenaVacia(this.idAutoridad) && enviarDefinitivamente) {
				mensajeError("Debe seleccionar la Autoridad.");
				return;
			}

			FuenteFinanciacion autoridad = cargarFuenteFinanciacion(idAutoridad);

			if (autoridad == null) {
				mensajeError("Error al obtener el Autoridad desde la base de datos.");
				return;
			}
			gestion.setAutoridad(cargarFuenteFinanciacion(idAutoridad));
		}
		else {
			gestion.setAutoridad(null);
		}
		
		if ((this.idTipoGestion.equals("RC") || this.idTipoGestion.equals("AC") || this.idTipoGestion.equals("IC"))
				&& enviarDefinitivamente) {
			if (listaArchivos == null || (listaArchivos != null && listaArchivos.size() == 0)) {
				mensajeError("Debe adjuntar al menos un archivo.");
				return;
			}
		}

		if ((this.idTipoGestion.equals("RC") || this.idTipoGestion.equals("AC"))) {
			if (validarExisteTipoYAutoridad(idTipoGestion, idAutoridad, gestion.getId())) {
				mensajeError("Este tipo de solicitud con esta autoridad ya existe.");
				return;
			}
		}
		
		if ((this.idTipoGestion.equals("CD"))) {
			
			if(enviarDefinitivamente) {
				if((esCadenaVacia(tipoDocumento) || esCadenaVacia(documentoIdentidad))) {
					mensajeError("Debe ingresar el tipo y número de documento del nuevo director.");
					return;
				}
				buscarNuevoDirectorMensaje(false, true);
				if(directorPropuesto == null) {
					mensajeError("No se ha encontrado ningún investigador interno con el número de documento ingresado.");
					return;
				}
				List<ColeccionPersona> personal = coleccion.getListaPersonal();
				if(personal!= null && personal.size() > 0) {
					for (int i = 0; i < personal.size(); i++) {
						ColeccionPersona colPersona = personal.get(i);
						if(colPersona.getTipoPersona().equals("CU1")) {
							gestion.setDirectorAnterior(colPersona.getPersona());
						}
					}
				}
			}
			else {
				buscarNuevoDirectorMensaje(false,true);
			}
			
			if(directorPropuesto != null) {
				gestion.setDirectorNuevo(directorPropuesto);
			}
			
			if(enviarDefinitivamente && (gestion.getDirectorAnterior() == null || gestion.getDirectorNuevo() == null)) {
				mensajeError("Error al consultar información del personal actual de la colección.");
				return;
			}
		}

		if ((this.idTipoGestion.equals("PB")) && enviarDefinitivamente) {
			if(esCadenaVacia(aceptaPublicacion)) {
				mensajeError("Debe seleccionar si acepta la publicación de la información en el portal de colecciones.");
				return;
			}
		}
		
		gestion.setAceptaPublicacion(aceptaPublicacion);

		DominioDetalle tipoGestion = cargarTipoGestion(idTipoGestion);

		if (tipoGestion == null) {
			mensajeError("Error al obtener el Tipo de Gestión desde la base de datos.");
			return;
		}

		gestion.setTipo(tipoGestion);

		DominioDetalle estado;

		if (!enviarDefinitivamente) {
			if (!esCadenaVacia(idEstadoActual)) {// Por si es devuelto
				estado = cargarEstadoGestion(idEstadoActual);
			} else {
				estado = cargarEstadoGestion(ColeccionGestion.ESTADO_INGRESANDO);
			}
		} else {
			estado = cargarEstadoGestion(ColeccionGestion.ESTADO_ENVIADA);
		}

		if (estado == null) {
			mensajeError("Error al obtener el Estado desde la base de datos.");
			return;
		}

		gestion.setPersonaRegistra(getPersonaActual());
		gestion.setEstado(estado);
		gestion.setFechaRegistro(getToday());
		gestion.setIdColeccion(coleccion.getId());

		servicioGeneral.guardarObjeto(gestion);

		guardarArhivosGestion(gestion);

		HistoricoEstadoGestionColeccion hecg = HistoricoEstadoGestionColeccion.generarHistoricoEstado(gestion);
		hecg.setFecha(getToday());
		hecg.setResposable(getPersonaActual());

		servicioGeneral.guardarObjeto(hecg);
		
		if(enviarDefinitivamente) {
			enviarCorreoEnvioGestion(gestion);
		}

		cargarGestionesColeccion();

		limpiarCampos();

		setVistaGestion(null);

	}

	

	public void guardarArhivosGestion(ColeccionGestion coleccionGestion) {
		if (listaArchivos != null && listaArchivos.size() > 0) {
			for (int i = 0; i < listaArchivos.size(); i++) {
				ArchivoGestionColeccion agc = listaArchivos.get(i);
				agc.setGestion(coleccionGestion);

				servicioGeneral.guardarObjeto(agc);
			}
		}
	}

	public void verGestion() {
		cargarGestion(true);
	}

	public void editarGestionTabla() {
		cargarGestion(false);
	}

	public void eliminarSolicitudTabla() {

		gestion = gestionSeleccionada;

		DominioDetalle estado = cargarEstadoGestion(ColeccionGestion.ESTADO_ELIMINADA);

		if (estado == null) {
			mensajeError("Error al obtener el Estado desde la base de datos.");
			return;
		}

		gestion.setPersonaElimina(getPersonaActual());
		gestion.setEstado(estado);
		gestion.setFechaElimina(getToday());
		gestion.setIdColeccion(coleccion.getId());

		servicioGeneral.guardarObjeto(gestion);

		HistoricoEstadoGestionColeccion hecg = HistoricoEstadoGestionColeccion.generarHistoricoEstado(gestion);
		hecg.setFecha(getToday());
		hecg.setResposable(getPersonaActual());

		servicioGeneral.guardarObjeto(hecg);

		setVistaGestion(null);

		cargarGestionesColeccion();
		limpiarCampos();
	}

	private void cargarGestion(boolean esConsula) {
		limpiarCampos();
		this.esConsulta = esConsula;
		gestion = gestionSeleccionada;
		if (gestion.getAutoridad() != null) {
			idAutoridad = gestion.getAutoridad().getId();
		}
		if (gestion.getTipo() != null) {
			idTipoGestion = gestion.getTipo().getIdentificador().getTipo();
		}
		if (gestion != null && gestion.getId() != null) {
			cargarArchivosGestion(gestion.getId());
		}
		idEstadoActual = gestion.getEstado().getIdentificador().getTipo();
		
		if(idTipoGestion.equals("CD") && gestion.getDirectorNuevo() != null) {
			tipoDocumento = gestion.getDirectorNuevo().getId().getTipoDocumento();
			documentoIdentidad = gestion.getDirectorNuevo().getId().getDocumento();
			buscarNuevoDirectorMensaje(false, false);
		}
		aceptaPublicacion = gestion.getAceptaPublicacion();

		setVistaGestion(gestion);
	}

	public String getTipoSolicitudUsada() {
		return tipoSolicitudUsada;
	}

	public void setTipoSolicitudUsada(String tipoSolicitudUsada) {
		this.tipoSolicitudUsada = tipoSolicitudUsada;
	}

	public List<ColeccionGestion> getListaGestionesColeccion() {
		return listaGestionesColeccion;
	}

	public void setListaGestionesColeccion(List<ColeccionGestion> listaGestionesColeccion) {
		this.listaGestionesColeccion = listaGestionesColeccion;
	}

	public ColeccionGestion getVistaGestion() {
		return vistaGestion;
	}

	public void setVistaGestion(ColeccionGestion vistaGestion) {
		this.vistaGestion = vistaGestion;
	}

	public String getIdAutoridad() {
		return idAutoridad;
	}

	public void setIdAutoridad(String idAutoridad) {
		this.idAutoridad = idAutoridad;
	}

	public String getIdTipoGestion() {
		return idTipoGestion;
	}

	public void setIdTipoGestion(String idTipoGestion) {
		this.idTipoGestion = idTipoGestion;
	}

	public ColeccionGestion getGestionSeleccionada() {
		return gestionSeleccionada;
	}

	public void setGestionSeleccionada(ColeccionGestion gestionSeleccionada) {
		this.gestionSeleccionada = gestionSeleccionada;
	}

	public List<SelectItem> getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(List<SelectItem> tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public void setDocumentoIdentidad(String documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}

	public InvestigadorInterno getDirectorPropuesto() {
		return directorPropuesto;
	}

	public void setDirectorPropuesto(InvestigadorInterno directorPropuesto) {
		this.directorPropuesto = directorPropuesto;
	}

	public String getAceptaPublicacion() {
		return aceptaPublicacion;
	}

	public void setAceptaPublicacion(String aceptaPublicacion) {
		this.aceptaPublicacion = aceptaPublicacion;
	}

	
	public void enviarCorreoEnvioGestion(ColeccionGestion gestion) {
		Correo correo = new Correo();
		CorreoPlantilla cp = cargarPlantilla(412);
		correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
		correo.setAsunto(cp.getAsunto());
		correo.setCuerpo(cp.getCuerpo().replaceAll("<<COLECCION>>", coleccion.getNombre())
				.replaceAll("<<INVESTIGADOR>>", coleccion.getCuradorGeneral().getNombreCompletoMinusculas())
				.replaceAll("<<CODIGO_GESTION>>", gestion.getId().toString())
				.replaceAll("<<TIPO_SOLICITUD>>", gestion.getTipo().getDescripcion()));

		List<Parametro> listaParametro = this.servicioGeneral.obtenerListaObjetosWhere(Parametro.class,
				"WHERE p.nombre = 'COORD_BIODIVERSIDAD'");
		for (int i = 0; i < listaParametro.size(); i++) {
			Parametro par = listaParametro.get(i);
			IdPersona id = new IdPersona();
			id.setDocumento(par.getValor());
			if (par.getProfesion() != null) {
				id.setTipoDocumento(par.getProfesion());
			} else {
				id.setTipoDocumento("C");
			}
			Persona persona = servicioPersona.obtenerPersona(id);
			correo.adicionarDireccion(persona.getEmail());
		}
		servicioCorreo.enviarCorreo(correo);
	}

}
