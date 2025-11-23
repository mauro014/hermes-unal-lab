package co.edu.unal.hermes.vista.requerimiento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.dao.DataIntegrityViolationException;

import co.edu.unal.hermes.modelo.ArchivoRequerimiento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.HistoricoRequerimiento;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Requerimiento;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.ManejadorBase;
import sun.java2d.pipe.RenderQueue;

public class ManejadorConsultarRequerimientoAdm extends ManejadorBase implements Serializable {

	private List requerimientoLista;
	private List requerimientoListaCompleta;
	private List inconvenienteListaCompleta;
	private List listaReqAsignados;
	private List listaSolucionados;
	private List listaAsignadosYSolucionados;
	private List listaInconvenientesAsignados;
	private List requerimientosAsignadosListaCompleta;
	private Requerimiento requerimientoSeleccionado;
	private Requerimiento requerimientoEditar;
	private Requerimiento requerimiento;
	private int numeroInconvenientes = 0;
	private int numeroSolicitudes = 0;
	private int numeroAsignadosSolucionados = 0;
	TipoDocumento tipoDocumentoReq;

	private DataTable tablaArchivos;
	private ArchivoRequerimiento archivoSeleccionado;

	ArchivoRequerimiento archivo;
	List<Dependencia> dependenciasUN;
	List<TipoDocumento> listaTipoDocumento;

	List<DominioDetalle> listaEstReq;
	DominioDetalle estadosReq;
	String estadoSelReq;

	List<DominioDetalle> listaTipoArcReq;
	DominioDetalle tipoArcReq;
	String tipoArcSelReq;

	private UploadedFile archivoCargarUno;
	List<ArchivoRequerimiento> listaDocumentos;
	private String errorValidacion;
	private String mensajeAdjuntaDocumentos = "";

	Dependencia dependenciaReq;

	public SelectItem[] dependenciaItem;
	public SelectItem[] tipoDocumentoItem;
	public SelectItem[] estadosItem;
	public SelectItem[] tipoArcItem;

	List<DominioDetalle> listaIngenieros;
	DominioDetalle tipoListIng;
	String tipoListIngSel;
	public SelectItem[] tipoIngItem;
	private String documento;
	private Persona personaSolicitante;
	private boolean mostrarForm = false;
	private String ingenieroAsignado;

	public SelectItem[] prioridadItem = { new SelectItem("", "Todos"),  new SelectItem("Urgente", "Urgente"), new SelectItem("Alta", "Alta"),
			new SelectItem("Media", "Media"), new SelectItem("Baja", "Baja") };

	public SelectItem[] ingsItems;

	public SelectItem[] estadosRequerimientos = { new SelectItem("", "Todos"), new SelectItem("Asignado", "Asignado"),
			new SelectItem("En Desarrollo", "En Desarrollo"), new SelectItem("Solucionado", "Solucionado"),
			new SelectItem("No aplica", "No aplica"), new SelectItem("Verificado", "Verificado"),
			new SelectItem("En Pruebas", "En Pruebas") };

	public SelectItem[] moduloItem;

	public SelectItem[] tipoReporteItem = { new SelectItem("0", "Mejora"), new SelectItem("1", "Inconveniente") };

	private String tipoReporte;
	private Date fechaInicio;
	private Date fechaFinal;
	private UIComponent generarReporte;
	private SelectItem[] moduloItems;
	private List listaMod = new ArrayList();

	protected Boolean esConsultaRequerimientos = false;
	private List<HistoricoRequerimiento> historicoRequerimiento;

	@SuppressWarnings("deprecation")
	public ManejadorConsultarRequerimientoAdm() {

		cargarListas();

		esConsultaRequerimientos = (Boolean) sesion.getAttribute("esConsultaRequerimientos");

		tipoDocumentoReq = new TipoDocumento();
		dependenciaReq = new Dependencia();
		estadosReq = new DominioDetalle();
		tipoArcReq = new DominioDetalle();
		requerimientoLista = new ArrayList();

		requerimientoListaCompleta = new ArrayList();
		// requerimientoListaCompleta =
		// servicioGeneral.obtenerObjetos(Requerimiento.class,"from
		// Requerimiento r where r.tipo='Mejora' and r.estSelRequerimiento !=
		// 'Solucionado' and r.estSelRequerimiento != 'No aplica' and
		// r.dependenciaAsociada != 'VRIE' order by r.id"
		// );
		requerimientoListaCompleta = servicioGeneral.obtenerObjetos(Requerimiento.class,
				"from Requerimiento r where r.tipo='Mejora' and r.estSelRequerimiento = 'Registrado' and r.dependenciaAsociada != 'VRIE' order by r.id");
		if (requerimientoListaCompleta != null && requerimientoListaCompleta.size() > 0) {
			numeroSolicitudes = requerimientoListaCompleta.size();
		}

		requerimientoListaCompleta = configurarVista(requerimientoListaCompleta);

		//
		requerimientoLista = servicioGeneral.obtenerObjetos(Requerimiento.class,
				"from Requerimiento r where r.dependenciaAsociada != 'VRIE' order by r.id");

		listaAsignadosYSolucionados = new ArrayList();
		listaAsignadosYSolucionados = servicioGeneral.obtenerObjetos(Requerimiento.class,
				"from Requerimiento r where r.tipo='Mejora' and r.estSelRequerimiento != 'Registrado' "
						+ "and r.dependenciaAsociada != 'VRIE' order by r.id");
		if (listaAsignadosYSolucionados != null && listaAsignadosYSolucionados.size() > 0) {
			numeroAsignadosSolucionados = listaAsignadosYSolucionados.size();
		}
		listaAsignadosYSolucionados = configurarVista(listaAsignadosYSolucionados);

		setRequerimientoSeleccionado(requerimiento);

	}

	public List configurarVista(List lista) {
		if (lista != null && lista.size() > 0) {
			for (int i = 0; i < lista.size(); i++) {
				Requerimiento req = (Requerimiento) lista.get(i);

				for (int j = 0; j < listaMod.size(); j++) {
					DominioDetalle d = (DominioDetalle) listaMod.get(j);
					if (d.getIdentificador().getTipo().equals(req.getModulo())) {
						req.setModuloVista(d.getDescripcion());
						lista.set(i, req);
						break;
					}
				}

				for (int j = 0; j < listaIngenieros.size(); j++) {
					DominioDetalle d = (DominioDetalle) listaIngenieros.get(j);
					if (d.getIdentificador().getTipo().equals(req.getIngenieroAsignado())) {
						req.setIngenieroVista(d.getDescripcion());
						lista.set(i, req);
						break;
					}
				}

			}
		}
		return lista;
	}

	// Buscar Persona
	public void buscarIngeniero() {

		personaSolicitante = new Persona();

		if (this.requerimiento.getIngenieroAsignado() != null) {

			try {

				// búsqueda de solicitud por ing.
				String hql = "from Requerimiento r where r.ingenieroAsignado = '" + requerimiento.getIngenieroAsignado()
						+ "' and r.dependenciaAsociada != 'VRIE' and r.estSelRequerimiento = 'En Desarrollo'  "
						+ "and r.tipo='Mejora' order by r.id";

				requerimientosAsignadosListaCompleta = (List<Requerimiento>) servicioGeneral
						.obtenerRequerimientosPorIngeniero(requerimiento.getIngenieroAsignado());

				if (requerimientosAsignadosListaCompleta != null) {
					mostrarForm = true;
					numeroSolicitudes = requerimientosAsignadosListaCompleta.size();
				}

				requerimientosAsignadosListaCompleta = configurarVista(requerimientosAsignadosListaCompleta);

			} catch (Exception e) {
				mostrarForm = false;
			}

		} else {
			mostrarForm = false;
		}

	}

	public void actualizarIngeniero() {
		requerimiento = new Requerimiento();
		this.requerimiento.setIngenieroAsignado(ingenieroAsignado);
		// System.out.print(this.requerimiento.getIngenieroAsignado());
	}

	public void cargarListas() {

		// Ingenieros
		listaIngenieros = new ArrayList<DominioDetalle>();
		listaIngenieros = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"from DominioDetalle where identificador.id = '107' and observacion='A'");

		if (listaIngenieros.size() > 0) {
			tipoIngItem = new SelectItem[listaIngenieros.size()];
			ingsItems = new SelectItem[listaIngenieros.size() + 1];
			ingsItems[0] = new SelectItem("", "Todos");

			for (int i = 0; i < listaIngenieros.size(); i++) {
				DominioDetalle ta = (DominioDetalle) listaIngenieros.get(i);
				tipoIngItem[i] = new SelectItem(ta.getIdentificador().getTipo(), ta.getDescripcion());
				ingsItems[i + 1] = new SelectItem(ta.getDescripcion(), ta.getDescripcion());
				ta = null;
			}

			tipoListIngSel = (String) tipoIngItem[0].getValue();
		}

		// Estados Requerimiento
		listaEstReq = new ArrayList<DominioDetalle>();
		listaEstReq = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"from DominioDetalle where identificador.id = '102'");

		DominioDetalle esAux = listaEstReq.get(0);

		estadosItem = new SelectItem[listaEstReq.size()];

		for (int i = 0; i < listaEstReq.size(); i++) {
			DominioDetalle es = (DominioDetalle) listaEstReq.get(i);
			estadosItem[i] = new SelectItem(es.getDescripcion(), es.getDescripcion());
			es = null;
		}

		estadoSelReq = estadosReq != null ? estadosReq.getDescripcion() : "R";

		// Módulos
		listaMod = new ArrayList();
		listaMod = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				"from DominioDetalle where identificador.id = '113' and estado='0' order by descripcion");

		DominioDetalle aux = (DominioDetalle) listaMod.get(0);

		if (listaMod.size() > 0) {
			moduloItems = new SelectItem[listaMod.size()];
			moduloItem = new SelectItem[listaMod.size() + 1];
			moduloItem[0] = new SelectItem("", "Todos");

			for (int i = 0; i < listaMod.size(); i++) {
				DominioDetalle ta = (DominioDetalle) listaMod.get(i);
				moduloItems[i] = new SelectItem(ta.getIdentificador().getTipo(), ta.getDescripcion());
				moduloItem[i + 1] = new SelectItem(ta.getDescripcion(), ta.getDescripcion());
				ta = null;
			}

		}
	}

	// VER REQUERIMIENTOS
	public String verInconvenientes() {
		sesion.removeAttribute("ManejadorConsultarInconvenienteAdm");
		return "consultarTodosInconvenientes";

	}

	public String verInconvenientesAsignadosOSolucionados() {
		sesion.removeAttribute("ManejadorConsultarInconvenienteAdm");
		return "consultarTodosInconvAsignadosOSolucionados";

	}

	public String verInconvenientesAsignados() {
		sesion.removeAttribute("ManejadorConsultarInconvenienteAdm");
		return "consultarTodosInconvAsignados";

	}

	public String verMejoras() {
		sesion.removeAttribute("ManejadorConsultarRequerimientoAdm");

		return "consultarTodosRequerimientos";

	}

	public String verAsignadosSolucionados() {
		sesion.removeAttribute("ManejadorConsultarRequerimientoAdm");

		return "consultarTodosSolucionados";

	}

	public String verTodosAsignados() {
		sesion.removeAttribute("ManejadorConsultarRequerimientoAdm");

		return "consultarTodosReqPorRecurso";

	}

	public String generarReportes() {
		sesion.removeAttribute("ManejadorConsultarRequerimientoAdm");

		return "ReporteRequerimientos";

	}

	public String reportarInconveniente() {
		sesion.removeAttribute("ManejadorMensajeError");

		return "errorDefectoAdm";

	}

	// VRIE
	public String verSolicitudesVRIE() {
		sesion.removeAttribute("ManejadorConsultarRequerimientoVRIE");

		return "ConsultarTodosRequerimientosVRIE";

	}

	public String verSolicAsignadasVRIE() {
		sesion.removeAttribute("ManejadorConsultarRequerimientoVRIE");

		return "ConsultarRequerimientosFiltVRIE";

	}

	// ******

	// public void agregarArchivoObligatorio() {
	// try {
	//
	// if (archivo.getBytes() != null) {
	//
	// int i = archivoObligatorio.getName().lastIndexOf("\\");
	//
	// ArchivoConvocatoria archivo = new ArchivoConvocatoria();
	// archivo.setBytes(archivoObligatorio.getBytes());
	// archivo.setNombre(archivoObligatorio.getName().substring(i + 1));
	// archivo.setFecha(Calendar.getInstance().getTime());
	// archivo.setConvocatoria(convocatoriaAlianzas.getId().toString());
	// archivo.setTipoArchivo(desTipoArchivo);
	//
	// listaArchivosObligatoriosSel.add(archivo);
	// }
	//
	// } catch (Exception x) {
	// System.out.println(x.toString());
	//
	// }
	// }

	// ******

	public String editarRequerimiento() {

		cargarListas();
		requerimientoEditar = new Requerimiento();
		requerimientoEditar = requerimientoSeleccionado;
		sesion.removeAttribute("ManejadorEditarRequerimiento");
		sesion.setAttribute("requerimientoEditable", requerimientoSeleccionado.getId());
		return "editarRequerimiento";
	}

	public String editarRequerimientoCoord() {

		cargarListas();
		requerimientoEditar = new Requerimiento();
		requerimientoEditar = requerimientoSeleccionado;
		sesion.removeAttribute("ManejadorEditarRequerimiento");
		sesion.setAttribute("requerimientoEditable", requerimientoSeleccionado.getId());
		return "EditarRequerimientoCoord";
	}

	public String editarRequerimientoAsignado() {

		cargarListas();
		requerimientoEditar = new Requerimiento();
		requerimientoEditar = requerimientoSeleccionado;
		sesion.removeAttribute("ManejadorEditarRequerimiento");
		sesion.setAttribute("requerimientoEditable", requerimientoSeleccionado.getId());
		return "editarRequerimientoAsignado";
	}

	public String editarInconvenienteCoord() {

		cargarListas();
		requerimientoEditar = new Requerimiento();
		requerimientoEditar = requerimientoSeleccionado;
		sesion.removeAttribute("ManejadorEditarRequerimiento");
		sesion.setAttribute("requerimientoEditable", requerimientoSeleccionado.getId());
		return "editarInconvenienteCoord";
	}

	public String consultarRequerimiento() {

		cargarListas();
		sesion.removeAttribute("ManejadorEditarRequerimiento");
		sesion.setAttribute("requerimientoEditable", requerimientoSeleccionado.getId());
		return "ConsultarRequerimientoPorId";
	}

	public String consultarRequerimientoCoord() {

		cargarListas();
		requerimientoEditar = new Requerimiento();
		requerimientoEditar = requerimientoSeleccionado;
		sesion.removeAttribute("ManejadorEditarRequerimiento");
		sesion.setAttribute("requerimientoEditable", requerimientoSeleccionado.getId());
		return "ConsultarRequerimientoCoord";
	}

	public String consultarInconvenienteCoord() {

		cargarListas();
		requerimientoEditar = new Requerimiento();
		requerimientoEditar = requerimientoSeleccionado;
		sesion.removeAttribute("ManejadorEditarRequerimiento");
		sesion.setAttribute("requerimientoEditable", requerimientoSeleccionado.getId());
		return "consultarInconvenienteCoord";
	}

	// **********

	public void insertarArchivo(FileUploadEvent event) {
		archivoCargarUno = event.getFile();
		if (archivoCargarUno != null) {
			try {
				archivo = new ArchivoRequerimiento();
				int i = archivoCargarUno.getFileName().lastIndexOf("\\");

				archivo.setBytes(archivoCargarUno.getContents());

				archivo.setNombre(archivoCargarUno.getFileName().substring(i + 1));
				archivo.setRequerimiento(requerimientoSeleccionado);

				archivo.setTipoArchivoReq(tipoArcSelReq);
				System.out.println(archivo.tipoArchivoReq);

				archivo.setArchivoInputStream(event.getFile().getInputstream());

				requerimientoSeleccionado.adicionarArchivo(archivo);

				listaDocumentos.add(archivo);

			} catch (DataIntegrityViolationException ex) {
				System.out.println(ex.toString());
				if (ex.getMessage().indexOf("AD_COMBINACION01_UK") > 0)
					errorValidacion = "Ya existe un archivo con este nombre";
				else
					errorValidacion = "Ocurrio un error inesperado al publicar el archivo";
			} catch (Exception ex) {
				System.out.println(ex.toString());
				errorValidacion = "Ocurrio un error inesperado al publicar el archivo";
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("No se ha seleccionado ningun archivo.");
			mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("datosGuardados", mensaje);
		}
	}

	// *******
	public void eliminarArchivo() {
		ArchivoRequerimiento archivo = archivoSeleccionado;
		requerimiento.borrarArchivo(archivo);
	}

	public void descargarArchivo() {
		try {
			if (archivoSeleccionado.getId() != null)
				descargarArchivoGenerico("HER_ARCHIVO_REQUERIMIENTO", archivoSeleccionado.getId() + "",
						archivoSeleccionado.getNombre());
			else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						"Para poder descargar el archivo primero debe guardar los cambios");
				FacesContext.getCurrentInstance().addMessage("growl", msg);
			}
		} catch (Exception e) {
			System.out.println("Ha ocurrido un problema al DESCARGAR archivo [" + e.getMessage() + "]");
		}

	}

	// **********
	public String imprimirReporte() {
		if (this.fechaInicio != null && this.fechaFinal != null) {
			if (!this.fechaInicio.before(this.fechaFinal)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"La fecha final debe ser posterior a la de inicio", ""));
				return "";
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe indicar el rango de fechas del reporte", ""));
			return "";
		}

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String fechainicial = sdf.format(fechaInicio);
		String fechafinal = sdf.format(fechaFinal);

		ReporteBirt r = new ReporteBirt();

		if (this.tipoReporte.equals("0")) {
			r.setNombreReporte("/requerimientos/reporteRequerimientos");
			// r.setNombreReporte("/requerimientos/reporteRequerimientosFecha");
		} else {
			r.setNombreReporte("/requerimientos/reporteInconvenientes");
			// r.setNombreReporte("/requerimientos/reporteInconvenientesFecha");
		}

		r.adicionarParametro("fecha1", fechainicial);
		r.adicionarParametro("fecha2", fechafinal);

		r.setFormato(ReporteBirt.FORMATO_XLS);
		sesion.setAttribute("reporte", r);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			context.responseComplete();
		}

		return "";
	}

	// **********

	// **********
	public void guardar() {

		servicioGeneral.guardarObjeto(requerimientoEditar);

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage mensaje = new FacesMessage(
				"La información ha sido guardada correctamente con el número " + requerimientoEditar.getId());
		context.addMessage("datosGuardados", mensaje);

	}

	public String cancelar() {
		sesion.removeAttribute("ManejadorCrearRequerimiento");
		return "consultarRequerimiento";
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public List getRequerimientoLista() {
		return requerimientoLista;
	}

	public void setRequerimientoLista(List requerimientoLista) {
		this.requerimientoLista = requerimientoLista;
	}

	public void setRequerimientoSeleccionado(Requerimiento requerimientoSeleccionado) {
		this.requerimientoSeleccionado = requerimientoSeleccionado;
	}

	public Requerimiento getRequerimientoSeleccionado() {
		return requerimientoSeleccionado;
	}

	public Requerimiento getRequerimientoEditar() {
		return requerimientoEditar;
	}

	public void setRequerimientoEditar(Requerimiento requerimientoEditar) {
		this.requerimientoEditar = requerimientoEditar;
	}

	public List<Dependencia> getDependenciasUN() {
		return dependenciasUN;
	}

	public void setDependenciasUN(List<Dependencia> dependenciasUN) {
		this.dependenciasUN = dependenciasUN;
	}

	public List<TipoDocumento> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public List<DominioDetalle> getListaEstReq() {
		return listaEstReq;
	}

	public void setListaEstReq(List<DominioDetalle> listaEstReq) {
		this.listaEstReq = listaEstReq;
	}

	public TipoDocumento getTipoDocumentoReq() {
		return tipoDocumentoReq;
	}

	public void setTipoDocumentoReq(TipoDocumento tipoDocumentoReq) {
		this.tipoDocumentoReq = tipoDocumentoReq;
	}

	public Dependencia getDependenciaReq() {
		return dependenciaReq;
	}

	public void setDependenciaReq(Dependencia dependenciaReq) {
		this.dependenciaReq = dependenciaReq;
	}

	public DominioDetalle getEstadosReq() {
		return estadosReq;
	}

	public void setEstadosReq(DominioDetalle estadosReq) {
		this.estadosReq = estadosReq;
	}

	public String getEstadoSelReq() {
		return estadoSelReq;
	}

	public UploadedFile getArchivoCargarUno() {
		return archivoCargarUno;
	}

	public void setArchivoCargarUno(UploadedFile archivoCargarUno) {
		this.archivoCargarUno = archivoCargarUno;
	}

	public List<ArchivoRequerimiento> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<ArchivoRequerimiento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public String getErrorValidacion() {
		return errorValidacion;
	}

	public void setErrorValidacion(String errorValidacion) {
		this.errorValidacion = errorValidacion;
	}

	public String getMensajeAdjuntaDocumentos() {
		return mensajeAdjuntaDocumentos;
	}

	public void setMensajeAdjuntaDocumentos(String mensajeAdjuntaDocumentos) {
		this.mensajeAdjuntaDocumentos = mensajeAdjuntaDocumentos;
	}

	public DataTable getTablaArchivos() {
		return tablaArchivos;
	}

	public void setTablaArchivos(DataTable tablaArchivos) {
		this.tablaArchivos = tablaArchivos;
	}

	public ArchivoRequerimiento getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(ArchivoRequerimiento archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public void setEstadoSelReq(String estadoSelReq) {
		this.estadoSelReq = estadoSelReq;
	}

	public SelectItem[] getDependenciaItem() {
		return dependenciaItem;
	}

	public void setDependenciaItem(SelectItem[] dependenciaItem) {
		this.dependenciaItem = dependenciaItem;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public SelectItem[] getEstadosItem() {
		return estadosItem;
	}

	public void setEstadosItem(SelectItem[] estadosItem) {
		this.estadosItem = estadosItem;
	}

	public ArchivoRequerimiento getArchivo() {
		return archivo;
	}

	public void setArchivo(ArchivoRequerimiento archivo) {
		this.archivo = archivo;
	}

	public List<DominioDetalle> getListaTipoArcReq() {
		return listaTipoArcReq;
	}

	public void setListaTipoArcReq(List<DominioDetalle> listaTipoArcReq) {
		this.listaTipoArcReq = listaTipoArcReq;
	}

	public DominioDetalle getTipoArcReq() {
		return tipoArcReq;
	}

	public void setTipoArcReq(DominioDetalle tipoArcReq) {
		this.tipoArcReq = tipoArcReq;
	}

	public String getTipoArcSelReq() {
		return tipoArcSelReq;
	}

	public void setTipoArcSelReq(String tipoArcSelReq) {
		this.tipoArcSelReq = tipoArcSelReq;
	}

	public SelectItem[] getTipoArcItem() {
		return tipoArcItem;
	}

	public void setTipoArcItem(SelectItem[] tipoArcItem) {
		this.tipoArcItem = tipoArcItem;
	}

	public List getRequerimientoListaCompleta() {
		return requerimientoListaCompleta;
	}

	public void setRequerimientoListaCompleta(List requerimientoListaCompleta) {
		this.requerimientoListaCompleta = requerimientoListaCompleta;
	}

	public List getInconvenienteListaCompleta() {
		return inconvenienteListaCompleta;
	}

	public void setInconvenienteListaCompleta(List inconvenienteListaCompleta) {
		this.inconvenienteListaCompleta = inconvenienteListaCompleta;
	}

	public List getListaReqAsignados() {
		return listaReqAsignados;
	}

	public void setListaReqAsignados(List listaReqAsignados) {
		this.listaReqAsignados = listaReqAsignados;
	}

	public SelectItem[] getEstadosRequerimientos() {
		return estadosRequerimientos;
	}

	public void setEstadosRequerimientos(SelectItem[] estadosRequerimientos) {
		this.estadosRequerimientos = estadosRequerimientos;
	}

	public List getListaSolucionados() {
		return listaSolucionados;
	}

	public void setListaSolucionados(List listaSolucionados) {
		this.listaSolucionados = listaSolucionados;
	}

	public List getListaInconvenientesAsignados() {
		return listaInconvenientesAsignados;
	}

	public void setListaInconvenientesAsignados(List listaInconvenientesAsignados) {
		this.listaInconvenientesAsignados = listaInconvenientesAsignados;
	}

	public List getListaAsignadosYSolucionados() {
		return listaAsignadosYSolucionados;
	}

	public void setListaAsignadosYSolucionados(List listaTodosSolucionados) {
		this.listaAsignadosYSolucionados = listaTodosSolucionados;
	}

	public List<DominioDetalle> getListaIngenieros() {
		return listaIngenieros;
	}

	public void setListaIngenieros(List<DominioDetalle> listaIngenieros) {
		this.listaIngenieros = listaIngenieros;
	}

	public DominioDetalle getTipoListIng() {
		return tipoListIng;
	}

	public void setTipoListIng(DominioDetalle tipoListIng) {
		this.tipoListIng = tipoListIng;
	}

	public String getTipoListIngSel() {
		return tipoListIngSel;
	}

	public void setTipoListIngSel(String tipoListIngSel) {
		this.tipoListIngSel = tipoListIngSel;
	}

	public SelectItem[] getTipoIngItem() {
		return tipoIngItem;
	}

	public void setTipoIngItem(SelectItem[] tipoIngItem) {
		this.tipoIngItem = tipoIngItem;
	}

	public SelectItem[] getIngsItems() {
		return ingsItems;
	}

	public void setIngsItems(SelectItem[] ingsItems) {
		this.ingsItems = ingsItems;
	}

	public SelectItem[] getPrioridadItem() {
		return prioridadItem;
	}

	public void setPrioridadItem(SelectItem[] prioridadItem) {
		this.prioridadItem = prioridadItem;
	}

	public int getNumeroInconvenientes() {
		return numeroInconvenientes;
	}

	public void setNumeroInconvenientes(int numeroInconvenientes) {
		this.numeroInconvenientes = numeroInconvenientes;
	}

	public int getNumeroSolicitudes() {
		return numeroSolicitudes;
	}

	public void setNumeroSolicitudes(int numeroSolicitudes) {
		this.numeroSolicitudes = numeroSolicitudes;
	}

	public int getNumeroAsignadosSolucionados() {
		return numeroAsignadosSolucionados;
	}

	public void setNumeroAsignadosSolucionados(int numeroAsignadosSolucionados) {
		this.numeroAsignadosSolucionados = numeroAsignadosSolucionados;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Persona getPersonaSolicitante() {
		return personaSolicitante;
	}

	public void setPersonaSolicitante(Persona personaSolicitante) {
		this.personaSolicitante = personaSolicitante;
	}

	public boolean isMostrarForm() {
		return mostrarForm;
	}

	public void setMostrarForm(boolean mostrarForm) {
		this.mostrarForm = mostrarForm;
	}

	public List getRequerimientosAsignadosListaCompleta() {
		return requerimientosAsignadosListaCompleta;
	}

	public void setRequerimientosAsignadosListaCompleta(List requerimientosAsignadosListaCompleta) {
		this.requerimientosAsignadosListaCompleta = requerimientosAsignadosListaCompleta;
	}

	public String getIngenieroAsignado() {
		return ingenieroAsignado;
	}

	public void setIngenieroAsignado(String ingenieroAsignado) {
		this.ingenieroAsignado = ingenieroAsignado;
	}

	public SelectItem[] getTipoReporteItem() {
		return tipoReporteItem;
	}

	public void setTipoReporteItem(SelectItem[] tipoReporteItem) {
		this.tipoReporteItem = tipoReporteItem;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public UIComponent getGenerarReporte() {
		return generarReporte;
	}

	public void setGenerarReporte(UIComponent generarReporte) {
		this.generarReporte = generarReporte;
	}

	public String getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(String tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

	public SelectItem[] getModuloItems() {
		return moduloItems;
	}

	public void setModuloItems(SelectItem[] moduloItems) {
		this.moduloItems = moduloItems;
	}

	public SelectItem[] getModuloItem() {
		return moduloItem;
	}

	public void setModuloItem(SelectItem[] moduloItem) {
		this.moduloItem = moduloItem;
	}

	public Boolean getEsConsultaRequerimientos() {
		return esConsultaRequerimientos;
	}

	public void setEsConsultaRequerimientos(Boolean esConsultaRequerimientos) {
		this.esConsultaRequerimientos = esConsultaRequerimientos;
	}

	public List<HistoricoRequerimiento> getHistoricoRequerimiento() {
		return historicoRequerimiento;
	}

	public void setHistoricoRequerimiento(List<HistoricoRequerimiento> historicoRequerimiento) {
		this.historicoRequerimiento = historicoRequerimiento;
	}

	public void cargarHistorico() {
		setHistoricoRequerimiento(servicioGeneral.obtenerObjetos(HistoricoRequerimiento.class,
				"from HistoricoRequerimiento h where h.requerimiento.id = '" + this.requerimientoSeleccionado.getId()
						+ "' order by h.id asc"));
	}
}