/**
 * @author Martha Liliana Correa O.
 * @date 09/11/2015
 */

package co.edu.unal.hermes.vista.administrador;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.Instructivo;
import co.edu.unal.hermes.modelo.Persona;

public class ManejadorAdministrarInstructivos extends ManejadorAdministrarClasificacionInstructivos {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Instructivo instructivoSeleccionado;
	private List<Instructivo> listaInstructivos;
	private List<Instructivo> filteredInstructivos;
	private Long tamanoLista;
	private String clasificacion;

	public ManejadorAdministrarInstructivos() {
		// Inicialización de valores
		personaActual = (Persona) sesion.getAttribute("persona");
		listaInstructivos = new ArrayList<Instructivo>();
		tamanoLista = 0L;
		consultarClasificacionesActivasInstructivos();
	}

	/**
	 * Método para consultar los instructivos asociados a una clasificación se
	 * listan en forma descendente de acuerdo con el número del Id
	 */
	public void consultarInstructivos() {
		listaInstructivos = new ArrayList<Instructivo>();
		if (clasificacion != null && !"".equals(clasificacion)) {

			try {
				listaInstructivos = servicioGeneral.obtenerObjetosLimitado(Instructivo.class,
						"select #id ins.id, #nombre ins.nombre, #estado ins.estado " + "from Instructivo ins "
								+ "where ins.clasificacion.id in ('" + clasificacion + "')" + "order by ins.nombre asc");
				tamanoLista = (long) listaInstructivos.size();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una clasificación.", ""));
		}
	}

	/**
	 * Método que remite al formulario para crear un nuevo instructivo
	 * 
	 * @return
	 */
	public String crearInstructivo() {
		sesion.setAttribute("idInstructivo", null);
		sesion.removeAttribute("manejadorCrearEditarInstructivos");
		return "crearEditarInstructivo";
	}

	/**
	 * Método para consultar / editar el instructivo seleccionado
	 * 
	 * @return
	 */
	public String editarInstructivo() {
		sesion.setAttribute("idInstructivo", null);
		sesion.setAttribute("idInstructivo", instructivoSeleccionado.getId());
		sesion.removeAttribute("manejadorCrearEditarInstructivos");
		return "crearEditarInstructivo";
	}

	/**
	 * Método para enviar al formulario de creación/edición de clasificación de
	 * los instructivos
	 * 
	 * @return
	 */
	public String crearEditarClasificacionInstructivo() {
		sesion.setAttribute("idInstructivo", null);
		return "administrarClasificacionInstructivo";
	}

	public Long getTamanoLista() {
		return tamanoLista;
	}

	public void setTamanoLista(Long tamanoLista) {
		this.tamanoLista = tamanoLista;
	}

	public List<Instructivo> getFilteredInstructivos() {
		return filteredInstructivos;
	}

	public void setFilteredInstructivos(List<Instructivo> filteredInstructivos) {
		this.filteredInstructivos = filteredInstructivos;
	}

	public Instructivo getInstructivoSeleccionado() {
		return instructivoSeleccionado;
	}

	public void setInstructivoSeleccionado(Instructivo instructivoSeleccionado) {
		this.instructivoSeleccionado = instructivoSeleccionado;
	}

	public List<Instructivo> getListaInstructivos() {
		return listaInstructivos;
	}

	public void setListaInstructivos(List<Instructivo> listaInstructivos) {
		this.listaInstructivos = listaInstructivos;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

}
