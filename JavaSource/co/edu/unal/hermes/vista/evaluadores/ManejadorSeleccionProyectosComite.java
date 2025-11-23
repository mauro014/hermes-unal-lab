package co.edu.unal.hermes.vista.evaluadores;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.TipoConcepto;

public class ManejadorSeleccionProyectosComite extends ManejadorBaseEvaluacion{
	private boolean esConsultaSeleccion;
	private boolean esConsulta;
	private String calificacionSeleccion;
    private UploadedFile   archivoSubir;
    private SelectItem[] listaEstadosProyectoItem;
	private List listaFiltradaEstadosProyecto;
	private String idEstadoSeleccionado;
	private String nombreEstadoSeleccionado;

    public ManejadorSeleccionProyectosComite() {
    	cargarValoresIniciales();
		esConsulta = (Boolean) sesion.getAttribute("seleccionComite");
	}
    
    private void cargarValoresIniciales() {
		this.esConsultaSeleccion = false;
		this.calificacionSeleccion = new String();
		this.nombreEstadoSeleccionado = new String();
		this.listaFiltradaEstadosProyecto = new ArrayList();
		this.idEstadoSeleccionado = new String();
		
		evaluacionProyecto = servicioProyecto
				.obtenerProyectoEvaluador((ProyectoEvaluador) sesion
						.getAttribute("proyectoEvaluador"));
		if (evaluacionProyecto.getConceptoSeleccion() != null) {
			this.idEstadoSeleccionado = evaluacionProyecto.getConceptoSeleccion()
					.getId();
		}
		if (evaluacionProyecto.getCalificacionFinalSeleccion() != null) {
			Float flo = evaluacionProyecto.getCalificacionFinalSeleccion();
			this.calificacionSeleccion = flo.toString();
		}
		cargarEstadosProyecto();
	}
    
	public String atras() {
		return "asociarEvaluadorInter";
	}
	
	private void cargarEstadosProyecto() {
		try {
			EstadoProyecto estPry1 = new EstadoProyecto();
			EstadoProyecto estPry2 = new EstadoProyecto();
			EstadoProyecto estPry3 = new EstadoProyecto();
			EstadoProyecto estPry4 = new EstadoProyecto();
			estPry1.setId("A");
			estPry1.setNombre("Aprobado");

			estPry2.setId("E");
			estPry2.setNombre("Aprobado con recomendaciones y ajustes");

			estPry3.setId("N");
			estPry3.setNombre("No aprobado");
			
			estPry4.setId("BF");
			estPry4.setNombre("Banco financiable");


			this.listaFiltradaEstadosProyecto.add(estPry1);
			this.listaFiltradaEstadosProyecto.add(estPry2);
			this.listaFiltradaEstadosProyecto.add(estPry3);
			this.listaFiltradaEstadosProyecto.add(estPry4);

			this.listaEstadosProyectoItem = new SelectItem[4];
			this.listaEstadosProyectoItem[0] = new SelectItem(estPry1.getId(),
					estPry1.getNombre());// llena lista JSF
			this.listaEstadosProyectoItem[1] = new SelectItem(estPry2.getId(),
					estPry2.getNombre());
			this.listaEstadosProyectoItem[2] = new SelectItem(estPry3.getId(),
					estPry3.getNombre());
			this.listaEstadosProyectoItem[3] = new SelectItem(estPry4.getId(),
					estPry4.getNombre());


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void guardarDatosSeleccion() {
		this.esConsultaSeleccion = true;
		try {
			if (calificacionSeleccion == null || calificacionSeleccion.equals(""))
				calificacionSeleccion = "0";
			evaluacionProyecto
					.setCalificacionFinalSeleccion(new Float(this.calificacionSeleccion));
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
			evaluacionProyecto.setConceptoSeleccion(tc);
			EstadoProyecto estPry = new EstadoProyecto();
			estPry.setId(this.idEstadoSeleccionado);
			estPry.setNombre(this.nombreEstadoSeleccionado);
			evaluacionProyecto.setFechaSeleccion(new Date());
			evaluacionProyecto.setObservacionesSeleccion(cortarCadena(evaluacionProyecto.getObservacionesSeleccion(), 4000));
			servicioProyecto.guardarEvaluacionProyecto(evaluacionProyecto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isEsConsultaSeleccion() {
		return esConsultaSeleccion;
	}

	public void setEsConsultaSeleccion(boolean esConsultaSeleccion) {
		this.esConsultaSeleccion = esConsultaSeleccion;
	}

	public boolean isEsConsulta() {
		return esConsulta;
	}

	public void setEsConsulta(boolean esConsulta) {
		this.esConsulta = esConsulta;
	}

	public String getCalificacionSeleccion() {
		return calificacionSeleccion;
	}

	public void setCalificacionSeleccion(String calificacionSeleccion) {
		this.calificacionSeleccion = calificacionSeleccion;
	}

	public UploadedFile getArchivoSubir() {
		return archivoSubir;
	}

	public void setArchivoSubir(UploadedFile archivoSubir) {
		this.archivoSubir = archivoSubir;
	}

	public SelectItem[] getListaEstadosProyectoItem() {
		return listaEstadosProyectoItem;
	}

	public void setListaEstadosProyectoItem(SelectItem[] listaEstadosProyectoItem) {
		this.listaEstadosProyectoItem = listaEstadosProyectoItem;
	}

	public List getListaFiltradaEstadosProyecto() {
		return listaFiltradaEstadosProyecto;
	}

	public void setListaFiltradaEstadosProyecto(List listaFiltradaEstadosProyecto) {
		this.listaFiltradaEstadosProyecto = listaFiltradaEstadosProyecto;
	}

	public String getIdEstadoSeleccionado() {
		return idEstadoSeleccionado;
	}

	public void setIdEstadoSeleccionado(String idEstadoSeleccionado) {
		this.idEstadoSeleccionado = idEstadoSeleccionado;
	}

	public String getNombreEstadoSeleccionado() {
		return nombreEstadoSeleccionado;
	}

	public void setNombreEstadoSeleccionado(String nombreEstadoSeleccionado) {
		this.nombreEstadoSeleccionado = nombreEstadoSeleccionado;
	}
}
