/**
 * @author Martha Liliana Correa O.
 * @date 09/11/2015
 */

package co.edu.unal.hermes.vista.administrador;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PreguntaClasificacion;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAdministrarClasificacionPreguntas extends ManejadorBase {

    /**
     * 
     */
    private static final long serialVersionUID = -3770631835338180226L;
    private List<PreguntaClasificacion> listaClasificacionPreguntas;
    private List<PreguntaClasificacion> filteredClasificacionPreguntas;
    private ArrayList<SelectItem> listaClasificacionesActivasItem;
    private Long tamanoLista;
    private Long clasificacionSeleccionada;

    public ManejadorAdministrarClasificacionPreguntas() {
        // Inicialización de valores
        personaActual = (Persona) sesion.getAttribute("persona");
        setListaClasificacionPreguntas(new ArrayList<PreguntaClasificacion>());
        tamanoLista = 0L;
        consultarClasificacionPreguntas();
    }

    public void consultarClasificacionPreguntas() {
        listaClasificacionPreguntas = servicioGeneral.obtenerObjetosLimitado(PreguntaClasificacion.class,
                "select #id icl.id, #nombre icl.nombre, #estado icl.estado from PreguntaClasificacion icl "
                        + "order by icl.nombre asc");
    }

    public void consultarClasificacionesActivasPreguntas() {
        listaClasificacionPreguntas = servicioGeneral.obtenerObjetosLimitado(PreguntaClasificacion.class,
                "select #id pcl.id, #nombre pcl.nombre, #estado pcl.estado  from PreguntaClasificacion pcl "
                        + "where pcl.estado in ('A') order by pcl.nombre asc");

        if (!esListaVacia(listaClasificacionPreguntas)) {
            listaClasificacionesActivasItem = new ArrayList<SelectItem>();
            for (int i = 0; i < listaClasificacionPreguntas.size(); i++) {
                PreguntaClasificacion clasif = (PreguntaClasificacion) listaClasificacionPreguntas.get(i);
                listaClasificacionesActivasItem.add(new SelectItem(clasif.getId(), clasif.getNombre()));
            }
        }
    }
    
    private void eliminarSesion(){
        sesion.setAttribute("idClasificacionPregunta", null);
        sesion.setAttribute("esEdicion", false);
        sesion.removeAttribute("manejadorCrearEditarClasificacionPreguntas");
    }

    /**
     * Método que remite al formulario para crear una nueva clasificacion
     * pregunta
     * 
     * @return
     */
    public String crearClasificacionPregunta() {
        eliminarSesion();
        return "crearEditarClasificacionPregunta";
    }

    /**
     * Método para consultar / editar la clasficiacion de pregunta seleccionada
     * 
     * @return
     */
    public String editarClasificacionPregunta() {
        eliminarSesion();
        sesion.setAttribute("idClasificacionPregunta", clasificacionSeleccionada);
        sesion.setAttribute("esEdicion", true);
        return "crearEditarClasificacionPregunta";
    }

    public Long getTamanoLista() {
        return tamanoLista;
    }

    public void setTamanoLista(Long tamanoLista) {
        this.tamanoLista = tamanoLista;
    }

    public ArrayList<SelectItem> getListaClasificacionesActivasItem() {
        return listaClasificacionesActivasItem;
    }

    public void setListaClasificacionesActivasItem(ArrayList<SelectItem> listaClasificacionesActivasItem) {
        this.listaClasificacionesActivasItem = listaClasificacionesActivasItem;
    }

    public Long getClasificacionSeleccionada() {
        return clasificacionSeleccionada;
    }

    public void setClasificacionSeleccionada(Long clasificacionSeleccionada) {
        this.clasificacionSeleccionada = clasificacionSeleccionada;
    }

    public List<PreguntaClasificacion> getListaClasificacionPreguntas() {
        return listaClasificacionPreguntas;
    }

    public void setListaClasificacionPreguntas(List<PreguntaClasificacion> listaClasificacionPreguntas) {
        this.listaClasificacionPreguntas = listaClasificacionPreguntas;
    }

    public List<PreguntaClasificacion> getFilteredClasificacionPreguntas() {
        return filteredClasificacionPreguntas;
    }

    public void setFilteredClasificacionPreguntas(List<PreguntaClasificacion> filteredClasificacionPreguntas) {
        this.filteredClasificacionPreguntas = filteredClasificacionPreguntas;
    }

}
