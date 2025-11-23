/**
 * @author Martha Liliana Correa O.
 * @date 09/11/2015
 */

package co.edu.unal.hermes.vista.administrador;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Pregunta;

public class ManejadorAdministrarPreguntas extends ManejadorAdministrarClasificacionPreguntas {

    /**
     * 
     */
    private static final long serialVersionUID = 1398295104999274070L;
    private Pregunta preguntaSeleccionada;
    private List<Pregunta> listaPreguntas;
    private List<Pregunta> filteredPreguntas;
    private Long tamanoLista;
    private String clasificacion;

    public ManejadorAdministrarPreguntas() {
        // Inicialización de valores
        personaActual = (Persona) sesion.getAttribute("persona");
        listaPreguntas = new ArrayList<Pregunta>();
        tamanoLista = 0L;
        consultarClasificacionesActivasPreguntas();
    }

    /**
     * Método para consultar las preguntas asociadas a una clasificación se
     * listan en forma descendente de acuerdo con el número del Id
     */
    public void consultarPreguntas() {
        listaPreguntas = new ArrayList<Pregunta>();
        if (clasificacion != null && !"".equals(clasificacion)) {
            listaPreguntas = servicioGeneral.obtenerObjetosLimitado(Pregunta.class,
                    "select #id pre.id, #descripcion pre.descripcion, #estado pre.estado, #etiqueta pre.etiqueta "
                            + "from Pregunta pre " + "where pre.clasificacion.id in ('" + clasificacion + "')"
                            + "order by pre.descripcion asc");
            tamanoLista = (long) listaPreguntas.size();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una clasificación.", ""));
        }
    }

    /**
     * Método que remite al formulario para crear una nueva pregunta
     * 
     * @return
     */
    public String crearPregunta() {
        eliminarManejadoresPregunta();
        return "crearEditarPregunta";
    }

    /**
     * Método para consultar / editar la pregunta seleccionada
     * 
     * @return
     */
    public String editarPregunta() {
        eliminarManejadoresPregunta();
        sesion.setAttribute("idPregunta", preguntaSeleccionada.getId());
        return "crearEditarPregunta";
    }

    /**
     * Método para enviar al formulario de creación/edición de clasificación de
     * las preguntas
     * 
     * @return
     */
    public String crearEditarClasificacionPregunta() {
        eliminarManejadoresPregunta();
        return "administrarClasificacionPregunta";
    }
    
    private void eliminarManejadoresPregunta(){
        sesion.setAttribute("idPregunta", null);
        sesion.removeAttribute("manejadorCrearEditarPreguntas");
    }

    public Long getTamanoLista() {
        return tamanoLista;
    }

    public void setTamanoLista(Long tamanoLista) {
        this.tamanoLista = tamanoLista;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Pregunta getPreguntaSeleccionada() {
        return preguntaSeleccionada;
    }

    public void setPreguntaSeleccionada(Pregunta preguntaSeleccionada) {
        this.preguntaSeleccionada = preguntaSeleccionada;
    }

    public List<Pregunta> getListaPreguntas() {
        return listaPreguntas;
    }

    public void setListaPreguntas(List<Pregunta> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    public List<Pregunta> getFilteredPreguntas() {
        return filteredPreguntas;
    }

    public void setFilteredPreguntas(List<Pregunta> filteredPreguntas) {
        this.filteredPreguntas = filteredPreguntas;
    }

}
