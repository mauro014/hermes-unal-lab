/**
 * @author Martha Liliana Correa O.
 * @date 09/11/2015
 */

package co.edu.unal.hermes.vista.administrador;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.InstructivoClasificacion;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAdministrarClasificacionInstructivos extends ManejadorBase {

	/**
     * 
     */
    private static final long serialVersionUID = 8165579792097048231L;
    private List<InstructivoClasificacion> listaClasificacionInstructivos;
	private List<InstructivoClasificacion> filteredClasificacionInstructivos;
	private ArrayList<SelectItem> listaClasificacionesActivasItem;
	private Long tamanoLista;
	private Long clasificacionSeleccionada;
	
	public ManejadorAdministrarClasificacionInstructivos() {
		// Inicialización de valores
		personaActual = (Persona) sesion.getAttribute("persona");
		listaClasificacionInstructivos = new ArrayList<InstructivoClasificacion>();
		tamanoLista = 0L;
		consultarClasificacionInstructivos();
	}
	
	public void consultarClasificacionInstructivos(){
		try {
			listaClasificacionInstructivos = servicioGeneral
					.obtenerObjetosLimitado(
							InstructivoClasificacion.class,
							"select #id icl.id, #nombre icl.nombre, #estado icl.estado "
									+ "from InstructivoClasificacion icl "
									+ "order by icl.nombre asc");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void consultarClasificacionesActivasInstructivos(){
		try {
			listaClasificacionInstructivos = servicioGeneral
					.obtenerObjetosLimitado(
							InstructivoClasificacion.class,
							"select #id icl.id, #nombre icl.nombre, #estado icl.estado  "
									+ "from InstructivoClasificacion icl "
									+ "where icl.estado in ('A') "
									+ "order by icl.nombre asc");
			
			if (listaClasificacionInstructivos.size() > 0) {
				listaClasificacionesActivasItem = new ArrayList<SelectItem>();
				for (int i = 0; i < listaClasificacionInstructivos.size(); i++) {
					InstructivoClasificacion clasif = (InstructivoClasificacion) listaClasificacionInstructivos
							.get(i);
					listaClasificacionesActivasItem.add(new SelectItem(clasif
							.getId(), clasif.getNombre()));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que remite al formulario para crear una nueva clasificacion instructivo
	 * 
	 * @return
	 */
	public String crearClasificacionInstructivo() {
		sesion.setAttribute("idClasificacionInstructivo", null);
		sesion.setAttribute("esEdicion", false);
		sesion.removeAttribute("manejadorCrearEditarClasificacionInstructivo");
		return "crearEditarClasificacionInstructivo";
	}

	/**
	 * Método para consultar / editar la clasficiacion de instructivo seleccionada
	 * 
	 * @return
	 */	
	public String editarClasificacionInstructivo() {
		sesion.setAttribute("idClasificacionInstructivo", null);
		sesion.setAttribute("idClasificacionInstructivo",
				clasificacionSeleccionada);
		sesion.setAttribute("esEdicion", true);
		sesion.removeAttribute("manejadorCrearEditarClasificacionInstructivo");
		return "crearEditarClasificacionInstructivo";
	}

	public Long getTamanoLista() {
		return tamanoLista;
	}

	public void setTamanoLista(Long tamanoLista) {
		this.tamanoLista = tamanoLista;
	}

	public List<InstructivoClasificacion> getListaClasificacionInstructivos() {
		return listaClasificacionInstructivos;
	}

	public void setListaClasificacionInstructivos(
			List<InstructivoClasificacion> listaClasificacionInstructivos) {
		this.listaClasificacionInstructivos = listaClasificacionInstructivos;
	}

	public List<InstructivoClasificacion> getFilteredClasificacionInstructivos() {
		return filteredClasificacionInstructivos;
	}

	public void setFilteredClasificacionInstructivos(
			List<InstructivoClasificacion> filteredClasificacionInstructivos) {
		this.filteredClasificacionInstructivos = filteredClasificacionInstructivos;
	}

	public ArrayList<SelectItem> getListaClasificacionesActivasItem() {
		return listaClasificacionesActivasItem;
	}

	public void setListaClasificacionesActivasItem(
			ArrayList<SelectItem> listaClasificacionesActivasItem) {
		this.listaClasificacionesActivasItem = listaClasificacionesActivasItem;
	}

	public Long getClasificacionSeleccionada() {
		return clasificacionSeleccionada;
	}

	public void setClasificacionSeleccionada(Long clasificacionSeleccionada) {
		this.clasificacionSeleccionada = clasificacionSeleccionada;
	}

}
