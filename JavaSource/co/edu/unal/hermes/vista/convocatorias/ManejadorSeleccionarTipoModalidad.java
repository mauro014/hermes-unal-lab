package co.edu.unal.hermes.vista.convocatorias;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Formulario;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorSeleccionarTipoModalidad extends ManejadorBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3775848031498435128L;
	private List<TipoModalidad> listaTipoModalidad;
	private SelectItem[] tipoModalidadesItem;
	private String idTipoModalidad;
	private List<Formulario> listaFormulariosModalidad;
	private boolean mostrarFormulariosModalidad = false;

	public ManejadorSeleccionarTipoModalidad() {
		super();

		String consultaModalidades = "select #id e.id, #nombre e.nombre from TipoModalidad e where e.estado ='A'";
		List<TipoModalidad> listTipoModalidades = servicioGeneral
				.obtenerObjetosLimitado(TipoModalidad.class,
						consultaModalidades);

		tipoModalidadesItem = new SelectItem[listTipoModalidades.size()];
		for (int i = 0; i < listTipoModalidades.size(); i++) {
			TipoModalidad ci = (TipoModalidad) listTipoModalidades.get(i);
			tipoModalidadesItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	public void cambiarTipoModalidad() {
		TipoModalidad tipoModalidadSeleccionada = servicioModalidad
				.obtenerTipoModalidadConvocatoria(idTipoModalidad);

		if (tipoModalidadSeleccionada != null) {
			if (tipoModalidadSeleccionada.getFormularios().size() > 0) {
				listaFormulariosModalidad = new ArrayList<Formulario>();
				listaFormulariosModalidad.addAll(tipoModalidadSeleccionada
						.getFormularios());
				mostrarFormulariosModalidad = true;
			} else {
				mostrarFormulariosModalidad = false;
			}
		} else {

			mostrarFormulariosModalidad = false;
			FacesContext
			.getCurrentInstance()
			.addMessage(
					"msgs",
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al consultar el tipo de modalidad.",
							""));
		}
	}
	
	public String siguiente(){		
		if(!idTipoModalidad.equals("")){
			TipoModalidad tipoModalidadSeleccionada = servicioModalidad
					.obtenerTipoModalidadConvocatoria(idTipoModalidad);
	    	sesion.setAttribute("tipoModalidadSeleccionada", tipoModalidadSeleccionada);
		    sesion.removeAttribute("manejadorInsercionConvocatorias");
			return "crearConvocatoriaModalidades";		
		}else{
			FacesContext
			.getCurrentInstance()
			.addMessage(
					"msgs",
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Por favor seleccionar el tipo de modalidad.",
							""));
			return "";
		}
		
	}

	/**
	 * @return the listaTipoModalidad
	 */
	public List<TipoModalidad> getListaTipoModalidad() {
		return listaTipoModalidad;
	}

	/**
	 * @param listaTipoModalidad
	 *            the listaTipoModalidad to set
	 */
	public void setListaTipoModalidad(List<TipoModalidad> listaTipoModalidad) {
		this.listaTipoModalidad = listaTipoModalidad;
	}

	public SelectItem[] getTipoModalidadesItem() {
		return tipoModalidadesItem;
	}

	public void setTipoModalidadesItem(SelectItem[] tipoModalidadesItem) {
		this.tipoModalidadesItem = tipoModalidadesItem;
	}

	public String getIdTipoModalidad() {
		return idTipoModalidad;
	}

	public void setIdTipoModalidad(String idTipoModalidad) {
		this.idTipoModalidad = idTipoModalidad;
	}

	public List<Formulario> getListaFormulariosModalidad() {
		return listaFormulariosModalidad;
	}

	public void setListaFormulariosModalidad(
			List<Formulario> listaFormulariosModalidad) {
		this.listaFormulariosModalidad = listaFormulariosModalidad;
	}

	public boolean isMostrarFormulariosModalidad() {
		return mostrarFormulariosModalidad;
	}

	public void setMostrarFormulariosModalidad(
			boolean mostrarFormulariosModalidad) {
		this.mostrarFormulariosModalidad = mostrarFormulariosModalidad;
	}

}
