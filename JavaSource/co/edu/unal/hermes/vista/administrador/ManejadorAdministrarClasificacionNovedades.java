/**
 * @author Martha Liliana Correa O.
 * @date 09/11/2016
 */

package co.edu.unal.hermes.vista.administrador;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.NovedadClasificacion;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAdministrarClasificacionNovedades extends ManejadorBase {

    /**
     * 
     */
    private static final long serialVersionUID = -3770631835338180226L;
    private List<NovedadClasificacion> listaClasificacionNovedades;
    private List<NovedadClasificacion> filteredClasificacionNovedades;
    private List<SelectItem> listaClasificacionesActivasItem;
    private Long tamanoLista;
    private Long clasificacionSeleccionada;

    public ManejadorAdministrarClasificacionNovedades() {
        // Inicialización de valores
        listaClasificacionNovedades = new ArrayList<NovedadClasificacion>();
        tamanoLista = 0L;
        consultarClasificacionNovedades();
    }

    public void consultarClasificacionNovedades() {
        listaClasificacionNovedades = servicioGeneral.obtenerObjetosLimitado(NovedadClasificacion.class,
                "select #id ncl.id, #nombre ncl.nombre, #estado ncl.estado from NovedadClasificacion ncl "
                        + "order by ncl.nombre asc");
    }

    public void consultarClasificacionesActivasNovedades() {
        listaClasificacionNovedades = servicioGeneral.obtenerObjetosLimitado(NovedadClasificacion.class,
                "select #id ncl.id, #nombre ncl.nombre, #estado ncl.estado from NovedadClasificacion ncl "
                        + "where ncl.estado in ('A') " + "order by ncl.nombre asc");

        if (!esListaVacia(listaClasificacionNovedades)) {
            listaClasificacionesActivasItem = new ArrayList<SelectItem>();
            for (int i = 0; i < listaClasificacionNovedades.size(); i++) {
                NovedadClasificacion clasif = (NovedadClasificacion) listaClasificacionNovedades.get(i);
                listaClasificacionesActivasItem.add(new SelectItem(clasif.getId(), clasif.getNombre()));
            }
        }
    }

    /**
     * Método que remite al formulario para crear una nueva clasificacion
     * de novidad
     * 
     * @return
     */
    public String crearClasificacionNovedad() {
        eliminarManejadoresNovedades();
        sesion.setAttribute("esEdicion", false);
        return "crearEditarClasificacionNovedad";
    }

    /**
     * Método para consultar / editar la clasificacion de la novedad seleccionada
     * 
     * @return
     */
    public String editarClasificacionNovedad() {
        eliminarManejadoresNovedades();
        sesion.setAttribute("idClasificacionNovedad", clasificacionSeleccionada);
        sesion.setAttribute("esEdicion", true);
        return "crearEditarClasificacionNovedad";
    }
    
    /**
     * Método para enviar al formulario de creación/edición de clasificación de
     * las novedades
     * 
     * @return
     */
    public String crearEditarClasificacionNovedad() {
        eliminarManejadoresNovedades();
        return "administrarClasificacionNovedad";
    }
    
    public void eliminarManejadoresNovedades(){
        sesion.setAttribute("idNovedad", null);
        sesion.setAttribute("idClasificacionNovedad", null);
        sesion.removeAttribute("manejadorCrearEditarNovedad");
        sesion.removeAttribute("manejadorAdministrarNovedad");
        sesion.removeAttribute("manejadorCrearEditarClasificacionNovedades");
        sesion.removeAttribute("manejadorAdministrarClasificacionNovedades");
    }

    public Long getTamanoLista() {
        return tamanoLista;
    }

    public void setTamanoLista(Long tamanoLista) {
        this.tamanoLista = tamanoLista;
    }

    public List<SelectItem> getListaClasificacionesActivasItem() {
        return listaClasificacionesActivasItem;
    }

    public void setListaClasificacionesActivasItem(List<SelectItem> listaClasificacionesActivasItem) {
        this.listaClasificacionesActivasItem = listaClasificacionesActivasItem;
    }

    public Long getClasificacionSeleccionada() {
        return clasificacionSeleccionada;
    }

    public void setClasificacionSeleccionada(Long clasificacionSeleccionada) {
        this.clasificacionSeleccionada = clasificacionSeleccionada;
    }

    public List<NovedadClasificacion> getListaClasificacionNovedades() {
        return listaClasificacionNovedades;
    }

    public void setListaClasificacionNovedades(List<NovedadClasificacion> listaClasificacionNovedades) {
        this.listaClasificacionNovedades = listaClasificacionNovedades;
    }

    public List<NovedadClasificacion> getFilteredClasificacionNovedades() {
        return filteredClasificacionNovedades;
    }

    public void setFilteredClasificacionNovedades(List<NovedadClasificacion> filteredClasificacionNovedades) {
        this.filteredClasificacionNovedades = filteredClasificacionNovedades;
    }

}
