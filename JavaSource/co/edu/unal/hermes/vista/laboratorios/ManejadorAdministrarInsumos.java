/**
 * @author dgbenitezc
 */

package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.InsumoLaboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioEquipoActualizacionSoftware;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAdministrarInsumos extends ManejadorBase {

	private List<InsumoLaboratorio> listaInsumos;
	private List<InsumoLaboratorio> listaInsumosFiltrados;
	private SelectItem[] selectItemUnidadDeMedida;
	private InsumoLaboratorio nuevoInsumo;
	private Boolean esDNL;
	private String tipoOperación;
	
	private ArrayList<ArchivoLaboratorio> listaArchivosFichaSeguridad;
	private ArrayList<ArchivoLaboratorio> listaArchivosEliminadosFichaSeguridad;
	private ArrayList<ArchivoLaboratorio> listaArchivosFichaSeguridadVista;
	
	private ArrayList<ArchivoLaboratorio> listaArchivosEtiqueta;
	private ArrayList<ArchivoLaboratorio> listaArchivosEliminadosEtiqueta;
	private ArrayList<ArchivoLaboratorio> listaArchivosEtiquetaVista;
	
	private ArchivoLaboratorio archivoFSSeleccionado;
	private ArchivoLaboratorio archivoEtiquetaSeleccionado;

	public ManejadorAdministrarInsumos() {
		cargarlistaInsumos();
		nuevoInsumo = new InsumoLaboratorio();
		selectItemUnidadDeMedida = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.UNIDADES_MEDIDA_INSUMOS_LABORATORIO);
		esDNL = (Boolean) sesion.getAttribute("esLaboratorios");
		tipoOperación = "C";
		
		listaArchivosFichaSeguridad = new ArrayList<ArchivoLaboratorio>();
		listaArchivosEliminadosFichaSeguridad = new ArrayList<ArchivoLaboratorio>();
		listaArchivosFichaSeguridadVista = new ArrayList<ArchivoLaboratorio>();
		
		listaArchivosEtiqueta = new ArrayList<ArchivoLaboratorio>();
		listaArchivosEliminadosEtiqueta = new ArrayList<ArchivoLaboratorio>();
		listaArchivosEtiquetaVista = new ArrayList<ArchivoLaboratorio>();
	}

	public void cargarlistaInsumos() {
		String hql1 = "FROM InsumoLaboratorio IL WHERE IL.activo = 1 ORDER BY IL.nombre";
		listaInsumos = servicioGeneral.obtenerObjetos(InsumoLaboratorio.class,hql1);
	}

	public void agregarInsumo() {
		Boolean validar = true;

		String nombreInsumo = nuevoInsumo.getNombre();
		nombreInsumo = nombreInsumo.trim().replaceAll("  ", " ");
		nuevoInsumo.setNombre(nombreInsumo);
		
		//Validar nombre insumo vacio
		if (nombreInsumo.length() == 0) {
			validar = false;
			mensajeError("form:nombreInsumo", "Campo obligatorio.");
		}

		//Validar nombre insumo repetido
		for (InsumoLaboratorio iL : listaInsumos) {
			if (iL.getNombre().equalsIgnoreCase(nombreInsumo)) {
				validar = false;
				mensajeError("form:nombreInsumo",
						"Ya existe un Insumo con nombre " + iL.getNombre());
			}
		}

		if (validar) {
			nuevoInsumo.setControlado(false);
			nuevoInsumo.setActivo(true);
			servicioGeneral.guardarObjeto(nuevoInsumo);
			cargarlistaInsumos();
			nuevoInsumo = new InsumoLaboratorio();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Insumo creado satisfactoriamente", "");
			FacesContext.getCurrentInstance().addMessage("btnEditarInsumo", msg);
		}
	}
	
	public void guardarInsumoEditar() {
		Boolean validar = true;

		String nombreInsumo = nuevoInsumo.getNombre();
		nombreInsumo = nombreInsumo.trim().replaceAll("  ", " ");
		nuevoInsumo.setNombre(nombreInsumo);
		
		//Validar nombre insumo vacio
		if (nombreInsumo.length() == 0) {
			validar = false;
			mensajeError("form:nombreInsumo", "Campo obligatorio.");
		}
		
		if (validar) {
			servicioGeneral.guardarObjeto(nuevoInsumo);
			//cargarlistaInsumos();
			nuevoInsumo = new InsumoLaboratorio();
			tipoOperación = "C";
			
			FacesMessage msg = new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Insumo editado satisfactoriamente", "");
			FacesContext.getCurrentInstance().addMessage("btnEditarInsumo", msg);
		}
	}
	
	public void editarInsumo() {
		System.out.println("nuevoInsumo: " + nuevoInsumo.getNombre());
		tipoOperación = "E";
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Insumo seleccionado para ser editado. Esto lo puede realizar en el panel superior llamado EDITAR INSUMO", "");
		FacesContext.getCurrentInstance().addMessage("btnAgregarInsumo", msg);
	}
	
	public void cerrarModalesArchivos() {
		tipoOperación = "C";
		nuevoInsumo = new InsumoLaboratorio();
	}
	
	public String modificarArchivosFichaSeguridad()
	{		
		listaArchivosFichaSeguridadVista = (ArrayList<ArchivoLaboratorio>) servicioGeneral.obtenerArchivosInsumoXidInsumoXidTipoArchivo(nuevoInsumo.getId(), Tipos.TIPOS_ARCHIVOS_INSUMO_LABORATORIO_Ficha_Datos_Seguridad);
		return "";
	}
	
	public String modificarArchivosEtiqueta()
	{		
		listaArchivosEtiquetaVista = (ArrayList<ArchivoLaboratorio>) servicioGeneral.obtenerArchivosInsumoXidInsumoXidTipoArchivo(nuevoInsumo.getId(), Tipos.TIPOS_ARCHIVOS_INSUMO_LABORATORIO_Etiqueta);
		return "";
	}
	
	public void subirArchivoFichaSeguridad(FileUploadEvent event) {
		Tipos tipoArchivo = obtenerTipoXid(Tipos.TIPOS_ARCHIVOS_INSUMO_LABORATORIO_Ficha_Datos_Seguridad);
		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,tipoArchivo);
		servicioGeneral.guardarObjeto(nuevoInsumo);
		if (archivoNuevo != null) {
			archivoNuevo.setInsumo(nuevoInsumo);;
			listaArchivosFichaSeguridad.add(archivoNuevo);
			servicioGeneral.insertarObjetoConIdLong(archivoNuevo, archivoNuevo.getId());
			listaArchivosFichaSeguridadVista.add(archivoNuevo);
		} else {
			mensajeError("Error al subir archivo.");
			System.out.println("Error");
		}
	}
	
	public void subirArchivoEtiqueta(FileUploadEvent event) {
		Tipos tipoArchivo = obtenerTipoXid(Tipos.TIPOS_ARCHIVOS_INSUMO_LABORATORIO_Etiqueta);
		ArchivoLaboratorio archivoNuevo = subirArchivoLaboratorios(event,tipoArchivo);
		servicioGeneral.guardarObjeto(nuevoInsumo);
		if (archivoNuevo != null) {
			archivoNuevo.setInsumo(nuevoInsumo);;
			listaArchivosEtiqueta.add(archivoNuevo);
			servicioGeneral.insertarObjetoConIdLong(archivoNuevo, archivoNuevo.getId());
			listaArchivosEtiquetaVista.add(archivoNuevo);
		} else {
			mensajeError("Error al subir archivo.");
			System.out.println("Error");
		}
	}
	
	public void eliminarArchivoFichaSeguridad() {
		if (eliminarArchivoLaboratorio(archivoFSSeleccionado.getId())) {
			listaArchivosFichaSeguridad.remove(archivoFSSeleccionado);
			listaArchivosFichaSeguridadVista.remove(archivoFSSeleccionado);		
			servicioGeneral.eliminarObjeto(archivoFSSeleccionado);
		} else
			System.out.println("Delete operation is failed: " + archivoFSSeleccionado.getId());
	}
	
	public void eliminarArchivoEtiqueta() {
		if (eliminarArchivoLaboratorio(archivoEtiquetaSeleccionado.getId())) {
			listaArchivosEtiqueta.remove(archivoEtiquetaSeleccionado);
			listaArchivosEtiquetaVista.remove(archivoEtiquetaSeleccionado);		
			servicioGeneral.eliminarObjeto(archivoEtiquetaSeleccionado);
		} else
			System.out.println("Delete operation is failed: " + archivoEtiquetaSeleccionado.getId());
	}
	
	public void descargarArchivoFichaSeguridad() {
		descargarArchivoLaboratorios(archivoFSSeleccionado);
	}
	
	public void descargarArchivoEtiqueta() {
		descargarArchivoLaboratorios(archivoEtiquetaSeleccionado);
	}
	
	public void descargarArchivoLaboratorios(ArchivoLaboratorio archivoLaboratorioDescargar) {
//		System.out.println("descargarArchivoLaboratorios IN:");
		String idArchivo = archivoLaboratorioDescargar.getId().toString();
		String nombreArchivo = archivoLaboratorioDescargar.getNombreArchivo();
//		System.out.println("idArchivo: " + idArchivo);
//		System.out.println("nombreArchivo: " + nombreArchivo);
		descargarArchivoGenerico(ArchivoLaboratorio.TABLA_ARCHIVOS_LABORATORIOS, idArchivo, nombreArchivo);
//		System.out.println("descargarArchivoLaboratorios OUT.");
	}

	/**
	 * @return the listaInsumos
	 */
	public List<InsumoLaboratorio> getListaInsumos() {
		return listaInsumos;
	}

	/**
	 * @return the listaInsumosFiltrados
	 */
	public List<InsumoLaboratorio> getListaInsumosFiltrados() {
		return listaInsumosFiltrados;
	}

	/**
	 * @param listaInsumosFiltrados
	 *            the listaInsumosFiltrados to set
	 */
	public void setListaInsumosFiltrados(
			List<InsumoLaboratorio> listaInsumosFiltrados) {
		this.listaInsumosFiltrados = listaInsumosFiltrados;
	}

	/**
	 * @return the selectItemUnidadDeMedida
	 */
	public SelectItem[] getSelectItemUnidadDeMedida() {
		return selectItemUnidadDeMedida;
	}

	/**
	 * @return the nuevoInsumo
	 */
	public InsumoLaboratorio getNuevoInsumo() {
		return nuevoInsumo;
	}

	/**
	 * @return the esDNL
	 */
	public Boolean getEsDNL() {
		return esDNL;
	}

	public void setListaInsumos(List<InsumoLaboratorio> listaInsumos) {
		this.listaInsumos = listaInsumos;
	}

	public void setSelectItemUnidadDeMedida(SelectItem[] selectItemUnidadDeMedida) {
		this.selectItemUnidadDeMedida = selectItemUnidadDeMedida;
	}

	public void setNuevoInsumo(InsumoLaboratorio nuevoInsumo) {
		this.nuevoInsumo = nuevoInsumo;
	}

	public void setEsDNL(Boolean esDNL) {
		this.esDNL = esDNL;
	}

	public String getTipoOperación() {
		return tipoOperación;
	}

	public void setTipoOperación(String tipoOperación) {
		this.tipoOperación = tipoOperación;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosFichaSeguridad() {
		return listaArchivosFichaSeguridad;
	}

	public void setListaArchivosFichaSeguridad(ArrayList<ArchivoLaboratorio> listaArchivosFichaSeguridad) {
		this.listaArchivosFichaSeguridad = listaArchivosFichaSeguridad;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosEliminadosFichaSeguridad() {
		return listaArchivosEliminadosFichaSeguridad;
	}

	public void setListaArchivosEliminadosFichaSeguridad(
			ArrayList<ArchivoLaboratorio> listaArchivosEliminadosFichaSeguridad) {
		this.listaArchivosEliminadosFichaSeguridad = listaArchivosEliminadosFichaSeguridad;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosFichaSeguridadVista() {
		return listaArchivosFichaSeguridadVista;
	}

	public void setListaArchivosFichaSeguridadVista(ArrayList<ArchivoLaboratorio> listaArchivosFichaSeguridadVista) {
		this.listaArchivosFichaSeguridadVista = listaArchivosFichaSeguridadVista;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosEtiqueta() {
		return listaArchivosEtiqueta;
	}

	public void setListaArchivosEtiqueta(ArrayList<ArchivoLaboratorio> listaArchivosEtiqueta) {
		this.listaArchivosEtiqueta = listaArchivosEtiqueta;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosEliminadosEtiqueta() {
		return listaArchivosEliminadosEtiqueta;
	}

	public void setListaArchivosEliminadosEtiqueta(ArrayList<ArchivoLaboratorio> listaArchivosEliminadosEtiqueta) {
		this.listaArchivosEliminadosEtiqueta = listaArchivosEliminadosEtiqueta;
	}

	public ArrayList<ArchivoLaboratorio> getListaArchivosEtiquetaVista() {
		return listaArchivosEtiquetaVista;
	}

	public void setListaArchivosEtiquetaVista(ArrayList<ArchivoLaboratorio> listaArchivosEtiquetaVista) {
		this.listaArchivosEtiquetaVista = listaArchivosEtiquetaVista;
	}

	public ArchivoLaboratorio getArchivoFSSeleccionado() {
		return archivoFSSeleccionado;
	}

	public void setArchivoFSSeleccionado(ArchivoLaboratorio archivoFSSeleccionado) {
		this.archivoFSSeleccionado = archivoFSSeleccionado;
	}

	public ArchivoLaboratorio getArchivoEtiquetaSeleccionado() {
		return archivoEtiquetaSeleccionado;
	}

	public void setArchivoEtiquetaSeleccionado(ArchivoLaboratorio archivoEtiquetaSeleccionado) {
		this.archivoEtiquetaSeleccionado = archivoEtiquetaSeleccionado;
	}
	
}
