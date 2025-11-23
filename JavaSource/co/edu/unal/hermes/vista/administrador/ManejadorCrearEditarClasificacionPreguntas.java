/**
 * @author Martha Liliana Correa O.
 * @date 10/11/2015
 */

package co.edu.unal.hermes.vista.administrador;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PreguntaClasificacion;

public class ManejadorCrearEditarClasificacionPreguntas extends ManejadorAdministrarClasificacionPreguntas {

    /**
     * 
     */
    private static final long serialVersionUID = -8113064025315682089L;
    private boolean esEdicion;
    private boolean esNuevo;
    private PreguntaClasificacion clasificacionPregunta;
    private SelectItem[] estadoClasificacion = { new SelectItem("", ""), new SelectItem("A", "Activa"),
            new SelectItem("I", "Inactiva") };

    public ManejadorCrearEditarClasificacionPreguntas() {

        personaActual = (Persona) sesion.getAttribute("persona");

        try {
            setEsEdicion((Boolean) sesion.getAttribute("esEdicion"));
        } catch (Exception e) {
            esEdicion = false;
        }

        if (esEdicion) {
            try {
                Long id = (Long) sesion.getAttribute("idClasificacionPregunta");
                String consulta = "select pcl from PreguntaClasificacion pcl where pcl.id = '" + id + "'";
                List<PreguntaClasificacion> clasificaciones = servicioGeneral
                        .obtenerObjetos(PreguntaClasificacion.class, consulta);

                if (!esListaVacia(clasificaciones)) {
                    clasificacionPregunta = (PreguntaClasificacion) clasificaciones.get(0);
                }
            } catch (Exception e) {
                // TODO
            }
        } else {
            esNuevo = true;
            clasificacionPregunta = new PreguntaClasificacion();
        }

    }

    public String guardarClasificacion() {
        boolean validada = true;
        if (clasificacionPregunta.getNombre() == null || "".equals(clasificacionPregunta.getNombre().trim())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe indicar un nombre para la clasificación.", ""));
            validada = false;
        } else if (clasificacionPregunta.getDescripcion().trim() == null
                || "".equals(clasificacionPregunta.getDescripcion().trim())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Debe ingresar una breve descripción para la clasificación, ésta será utilizada para la búsqueda.",
                            ""));
            validada = false;
        } else if (clasificacionPregunta.getEstado().trim() == null
                || "".equals(clasificacionPregunta.getEstado().trim())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe indicar un estado para la clasificación.", ""));
            validada = false;
        }

        if (validada) {
            // Guardar clasificacion
            servicioGeneral.guardarObjeto(clasificacionPregunta);
            if (clasificacionPregunta.getId() != null) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "La clasificación se ha creado correctamente.", ""));
                sesion.removeAttribute("manejadorAdministrarClasificacionPreguntas");
                sesion.removeAttribute("manejadorAdministrarPreguntas");
                return "administrarClasificacionPregunta";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Verifique la información que está ingresando, no ha superado la validación. "
                                    + "Verifique longidud del texto y la selección del estado.",
                            ""));

        }
        sesion.removeAttribute("manejadorAdministrarClasificacionPreguntas");
        sesion.removeAttribute("manejadorAdministrarPreguntas");
        return "";
    }

    public boolean isEsEdicion() {
        return esEdicion;
    }

    public void setEsEdicion(boolean esEdicion) {
        this.esEdicion = esEdicion;
    }

    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public SelectItem[] getEstadoClasificacion() {
        return estadoClasificacion;
    }

    public void setEstadoClasificacion(SelectItem[] estadoClasificacion) {
        this.estadoClasificacion = estadoClasificacion;
    }

    public PreguntaClasificacion getClasificacionPregunta() {
        return clasificacionPregunta;
    }

    public void setClasificacionPregunta(PreguntaClasificacion clasificacionPregunta) {
        this.clasificacionPregunta = clasificacionPregunta;
    }

}
