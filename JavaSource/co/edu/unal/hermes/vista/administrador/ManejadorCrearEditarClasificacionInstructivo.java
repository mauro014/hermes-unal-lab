/**
 * @author Martha Liliana Correa O.
 * @date 10/11/2015
 */

package co.edu.unal.hermes.vista.administrador;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.InstructivoClasificacion;
import co.edu.unal.hermes.modelo.Persona;

public class ManejadorCrearEditarClasificacionInstructivo extends ManejadorAdministrarClasificacionInstructivos {

    /**
     * 
     */
    private static final long serialVersionUID = 1549012520043047691L;
    private boolean esEdicion;
    private boolean esNuevo;
    private InstructivoClasificacion clasificacionInstructivo;
    private SelectItem[] estadoClasificacion = { new SelectItem("", ""), new SelectItem("A", "Activa"),
            new SelectItem("I", "Inactiva") };

    public ManejadorCrearEditarClasificacionInstructivo() {

        personaActual = (Persona) sesion.getAttribute("persona");
        if ((Boolean) sesion.getAttribute("esEdicion")) {
            esEdicion = true;
        } else {
            esEdicion = false;
        }

        if (esEdicion) {
            try {
                Long id = (Long) sesion.getAttribute("idClasificacionInstructivo");
                String consulta = "select icl from InstructivoClasificacion icl where icl.id = '" + id + "'";
                List<InstructivoClasificacion> clasificaciones = servicioGeneral
                        .obtenerObjetos(InstructivoClasificacion.class, consulta);

                if (!esListaVacia(clasificaciones)) {
                    clasificacionInstructivo = (InstructivoClasificacion) clasificaciones.get(0);
                }
            } catch (Exception e) {
                // TODO
            }
        } else {
            esNuevo = true;
            clasificacionInstructivo = new InstructivoClasificacion();
        }

    }

    public String guardarClasificacion() {
        boolean validada = true;
        if (esCadenaVacia(clasificacionInstructivo.getNombre())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe indicar un nombre para la clasificación.", ""));
            validada = false;
        } else if (esCadenaVacia(clasificacionInstructivo.getDescripcion())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Debe ingresar una breve descripción para la clasificación, ésta será utilizada para la búsqueda.",
                            ""));
            validada = false;
        } else if (esCadenaVacia(clasificacionInstructivo.getEstado())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe indicar un estado para la clasificación.", ""));
            validada = false;
        }

        if (validada) {
            // Guardar clasificacion
            servicioGeneral.guardarObjeto(clasificacionInstructivo);
            if (clasificacionInstructivo.getId() != null) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "La clasificación se ha creado correctamente.", ""));
                sesion.removeAttribute("manejadorAdministrarClasificacionInstructivos");
                return "administrarClasificacionInstructivo";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Verifique la información que está ingresando, no ha superado la validación. "
                                    + "Verifique longidud del texto y la selección del estado.",
                            ""));

        }
        sesion.removeAttribute("manejadorBusquedaAyuda");
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

    public InstructivoClasificacion getClasificacionInstructivo() {
        return clasificacionInstructivo;
    }

    public void setClasificacionInstructivo(InstructivoClasificacion clasificacionInstructivo) {
        this.clasificacionInstructivo = clasificacionInstructivo;
    }

    public SelectItem[] getEstadoClasificacion() {
        return estadoClasificacion;
    }

    public void setEstadoClasificacion(SelectItem[] estadoClasificacion) {
        this.estadoClasificacion = estadoClasificacion;
    }

}
