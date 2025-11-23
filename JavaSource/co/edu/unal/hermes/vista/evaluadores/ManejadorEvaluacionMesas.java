package co.edu.unal.hermes.vista.evaluadores;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.TipoConcepto;

public class ManejadorEvaluacionMesas extends ManejadorBaseEvaluacion {
	private UISelectOne UISelectOneConcepto;
	private boolean presentacionDatos;// la edicion esta por defecto
	private String eventualidad;
	private String calificacion;
	private String idEstadoSeleccionado;
	private String nombreEstadoSeleccionado;
	private SelectItem[] listaEstadosProyectoItem;
	private List listaFiltradaEstadosProyecto;
	private boolean esConsulta;
    private UploadedFile   archivoSubir;
    private boolean mostrarEvaluacionGeneral = false;
    private boolean mostrarEvaluacionCriterios = false; 

	// private Proyecto proyectoActual;

	public ManejadorEvaluacionMesas() {
		cargarValoresIniciales();
		esConsulta = (Boolean) sesion.getAttribute("lecturaEvaluadorMesa");
		// pregunta si ya fue evaluado
	}

	private void cargarValoresIniciales() {
		// 1. inicializa propiedades
		this.UISelectOneConcepto = new UISelectOne();
		this.eventualidad = "";
		this.presentacionDatos = false;
		this.nombreEstadoSeleccionado = new String();
		this.listaFiltradaEstadosProyecto = new ArrayList();
		this.idEstadoSeleccionado = new String();
		this.calificacion = new String();
		// 2. obtiene el objeto evaluador proyecto, es decir carga el evaluador
		// especifico con su respectivo proyecto.
		// recupera el proyectoactual de sesion
		evaluacionProyecto = servicioProyecto
				.obtenerProyectoEvaluador((ProyectoEvaluador) sesion
						.getAttribute("proyectoEvaluador"));
		if (evaluacionProyecto.getConcepto() != null) {
			this.idEstadoSeleccionado = evaluacionProyecto.getConcepto()
					.getId();
		}
		if (evaluacionProyecto.getCalificacionFinal() != null) {
			Float flo = evaluacionProyecto.getCalificacionFinal();
			this.calificacion = flo.toString();
		}
		if(evaluacionProyecto.getTipoEvaluacion() != null){
			tipoEvaluacion = evaluacionProyecto.getTipoEvaluacion();
			if(tipoEvaluacion.equals("evaluacionCriterios")){
				cargarValoresGenerales();
				calcularPromedioInicial();
			}
		}
		cargarEstadosProyecto();
	}
	
	public String atras() {
		return "asociarEvaluadorInter";
	}

	private void cargarEstadosProyecto() {
		/*
		 * listaEstadosProyecto =
		 * servicioGeneral.obtenerListaObjetos("EstadoProyecto");
		 * this.listaEstadosProyectoItem= new selectItem[ Iterator iter=
		 * listaEstadosProyecto.iterator();
		 * System.out.println("cargarEstadosProyecto()"); try { int i=0;
		 * while(iter.hasNext()) { Object obj = iter.next(); String id= new
		 * String(); String nombre = new String();
		 * id=((EstadoProyecto)obj).getId(); System.out.println("id " +id);
		 * nombre= ((EstadoProyecto)obj).getNombre(); if(id.equals("N") ||
		 * id.equals("AP") || id.equals("E")) {
		 * System.out.println("nombre estado "+nombre );
		 * this.listaFiltradaEstadosProyecto.add(obj);
		 * this.listaEstadosProyectoItem[i] = new SelectItem(id,nombre);//llena
		 * lista JSF
		 * 
		 * } i++; } }catch(Exception e) { e.printStackTrace(); }
		 */
		try {
			EstadoProyecto estPry1 = new EstadoProyecto();
			EstadoProyecto estPry2 = new EstadoProyecto();
			EstadoProyecto estPry3 = new EstadoProyecto();
			estPry1.setId("A");
			estPry1.setNombre("Aprobado");

			estPry2.setId("E");
			estPry2.setNombre("Aprobado con recomendaciones y ajustes");

			estPry3.setId("N");
			estPry3.setNombre("Negado");

			this.listaFiltradaEstadosProyecto.add(estPry1);
			this.listaFiltradaEstadosProyecto.add(estPry2);
			this.listaFiltradaEstadosProyecto.add(estPry3);

			this.listaEstadosProyectoItem = new SelectItem[3];
			this.listaEstadosProyectoItem[0] = new SelectItem(estPry1.getId(),
					estPry1.getNombre());// llena lista JSF
			this.listaEstadosProyectoItem[1] = new SelectItem(estPry2.getId(),
					estPry2.getNombre());
			this.listaEstadosProyectoItem[2] = new SelectItem(estPry3.getId(),
					estPry3.getNombre());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editarDatos() {
		this.presentacionDatos = false;
		this.eventualidad = "";

	}

	public void guardarDatosEvGeneral() {
		this.presentacionDatos = true;
		try {
			// guarda el objeto evaluador proyecto con sus propiedades:
			// calificación, observaciones, concepto
			// 1. agrega calificacion
			if (calificacion == null || calificacion.equals(""))
				calificacion = "0";
			evaluacionProyecto
					.setCalificacionFinal(new Float(this.calificacion));
			// 2. Agrega observaciones : estas las agrega directamente a traves
			// del jsf
			// 3. agrega estado (id y nombre)
			Iterator iter = this.listaFiltradaEstadosProyecto.iterator();
			String id = new String();
			String nombre = new String();
			while (iter.hasNext()) {
				Object obj = iter.next();
				id = ((EstadoProyecto) obj).getId();
				nombre = ((EstadoProyecto) obj).getNombre();
				if (id.equals(this.idEstadoSeleccionado)) {
					this.nombreEstadoSeleccionado = nombre;
				}
			}
			TipoConcepto tc = (TipoConcepto) servicioGeneral.obtenerObjeto(
					new TipoConcepto(), this.idEstadoSeleccionado);
			evaluacionProyecto.setConcepto(tc);
			EstadoProyecto estPry = new EstadoProyecto();
			estPry.setId(this.idEstadoSeleccionado);
			estPry.setNombre(this.nombreEstadoSeleccionado);
			evaluacionProyecto.setTipoEvaluacion(tipoEvaluacion);
			evaluacionProyecto.setFecha(getToday());
			// proyectoActual.setEstadoProyecto(estPry); //cambia el estado del
			// proyecto
			evaluacionProyecto.setObservaciones(cortarCadena(evaluacionProyecto.getObservaciones(), 4000));
			servicioProyecto.guardarEvaluacionProyecto(evaluacionProyecto);
			// servicioProyecto.actualizarProyecto(proyectoActual);
			// System.out.println("proyectoActual " +
			// proyectoActual.getEstadoProyecto().getNombre());
			// System.out.println("proyectoActual evaluacionProyecto concepto "
			// + evaluacionProyecto.getConcepto());
			// System.out.println("proyectoActual calificacion " +
			// evaluacionProyecto.getCalificacionFinal());
			// System.out.println("proyectoActual observaciones "
			// +evaluacionProyecto.getObservaciones());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void guardarArchivo() {
		if(archivoSubir != null){
			try {
				int i = archivoSubir.getFileName().lastIndexOf("\\");
				evaluacionProyecto.setDocumentoEvaluador(archivoSubir.getFileName());
				servicioGeneral.guardarObjeto(evaluacionProyecto);
				if(evaluacionProyecto.getId() != null){
					cargarArchivoDisco(archivoSubir, "HER_PROYECTO_EVALUADOR",evaluacionProyecto.getId().toString() );
				}
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else{
			FacesContext context = FacesContext.getCurrentInstance();
    		FacesMessage mensaje = new FacesMessage("No se ha seleccionado ningun archivo.");
    		mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
    		context.addMessage("datosGuardados", mensaje );
		}
	}
	
	public void cambiarTipoEvaluacion(){
		if (tipoEvaluacion.equals("evaluacionGeneral")){
			mostrarEvaluacionGeneral = true;
			mostrarEvaluacionCriterios = false;
		}else{
			mostrarEvaluacionGeneral = false;
			mostrarEvaluacionCriterios = true;
			motrarAprobacion = false;
			cargarValoresGenerales();
			calcularPromedioInicial();
			
		}
	}
	
	public void descargarDocumentoEvaluacion(){
    	descargarArchivoGenerico("HER_PROYECTO_EVALUADOR", evaluacionProyecto.getId().toString() , evaluacionProyecto.getDocumentoEvaluador());    	
    }
	
	public void eliminarArchivo() {
		try {
			String nombreArchivo = evaluacionProyecto.getDocumentoEvaluador();
			Long idProyecto = evaluacionProyecto.getId();

			if (nombreArchivo != null && idProyecto != null) {
				
				eliminarArchivoGenerico("HER_PROYECTO_EVALUADOR//" + idProyecto);
				
				// Limpiar el campo y guardar el objeto
				evaluacionProyecto.setDocumentoEvaluador(null);
				servicioGeneral.guardarObjeto(evaluacionProyecto);

				FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo eliminado correctamente", null));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "No hay archivo para eliminar", null));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar el archivo: " + e.getMessage(), null));
		}
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public String getIdEstadoSeleccionado() {
		return idEstadoSeleccionado;
	}

	public void setIdEstadoSeleccionado(String idEstadoSeleccionado) {
		this.idEstadoSeleccionado = idEstadoSeleccionado;
	}

	public SelectItem[] getListaEstadosProyectoItem() {
		return listaEstadosProyectoItem;
	}

	public void setListaEstadosProyectoItem(
			SelectItem[] listaEstadosProyectoItem) {
		this.listaEstadosProyectoItem = listaEstadosProyectoItem;
	}

	public List getListaFiltradaEstadosProyecto() {
		return listaFiltradaEstadosProyecto;
	}

	public void setListaFiltradaEstadosProyecto(
			List listaFiltradaEstadosProyecto) {
		this.listaFiltradaEstadosProyecto = listaFiltradaEstadosProyecto;
	}

	public String getNombreEstadoSeleccionado() {
		return nombreEstadoSeleccionado;
	}

	public void setNombreEstadoSeleccionado(String nombreEstadoSeleccionado) {
		this.nombreEstadoSeleccionado = nombreEstadoSeleccionado;
	}

	public boolean isPresentacionDatos() {
		return presentacionDatos;
	}

	public void setPresentacionDatos(boolean presentacionDatos) {
		this.presentacionDatos = presentacionDatos;
	}

	public String getEventualidad() {
		return eventualidad;
	}

	public void setEventualidad(String eventualidad) {
		this.eventualidad = eventualidad;
	}

	public UISelectOne getUISelectOneConcepto() {
		return UISelectOneConcepto;
	}

	public void setUISelectOneConcepto(UISelectOne selectOneConcepto) {
		UISelectOneConcepto = selectOneConcepto;
	}

	public void setEsConsulta(boolean esConsulta) {
		this.esConsulta = esConsulta;
	}

	public boolean isEsConsulta() {
		return esConsulta;
	}
	
	public String regresar(){
		return "informacionProyecto";
	}

	public UploadedFile getArchivoSubir() {
		return archivoSubir;
	}

	public void setArchivoSubir(UploadedFile archivoSubir) {
		this.archivoSubir = archivoSubir;
	}

	public List getListaCriterios() {
		return listaCriterios;
	}

	public void setListaCriterios(List listaCriterios) {
		this.listaCriterios = listaCriterios;
	}

	public List getListaCC() {
		return listaCC;
	}

	public void setListaCC(List listaCC) {
		this.listaCC = listaCC;
	}

	public boolean isMostrarEvaluacionGeneral() {
		return mostrarEvaluacionGeneral;
	}

	public void setMostrarEvaluacionGeneral(boolean mostrarEvaluacionGeneral) {
		this.mostrarEvaluacionGeneral = mostrarEvaluacionGeneral;
	}

	public boolean isMostrarEvaluacionCriterios() {
		return mostrarEvaluacionCriterios;
	}

	public void setMostrarEvaluacionCriterios(boolean mostrarEvaluacionCriterios) {
		this.mostrarEvaluacionCriterios = mostrarEvaluacionCriterios;
	}
}