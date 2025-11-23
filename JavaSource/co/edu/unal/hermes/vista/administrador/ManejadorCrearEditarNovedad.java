/**
 * @author Martha Liliana Correa O.
 * @date 10/11/2016
 */

package co.edu.unal.hermes.vista.administrador;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Novedad;
import co.edu.unal.hermes.modelo.NovedadClasificacion;
import co.edu.unal.hermes.modelo.Persona;

public class ManejadorCrearEditarNovedad extends ManejadorAdministrarNovedad {

    /**
     * 
     */
    private static final long serialVersionUID = -8339876891551006712L;
    private boolean esEdicion;
    private boolean esNuevo;
    private Novedad novedad;
    private SelectItem[] estadoItem = { new SelectItem("A", "Activa"), new SelectItem("I", "Inactiva") };

    public ManejadorCrearEditarNovedad() {
        Long id = null;
        personaActual = (Persona) sesion.getAttribute("persona");
        try {
            id = (Long) sesion.getAttribute("idNovedad");
        } catch (Exception e) {
            esEdicion = false;
        }

        if (id != null) {
            String consulta = "select n from Novedad n where n.id = '" + id + "'";
            List<Novedad> novedades = servicioGeneral.obtenerObjetos(Novedad.class, consulta);

            if (!esListaVacia(novedades)) {
                novedad = (Novedad) novedades.get(0);
            }
        } else {
            esNuevo = true;
            novedad = new Novedad();
            NovedadClasificacion clas = new NovedadClasificacion();
            novedad.setClasificacion(clas);
        }
        consultarClasificacionesActivasNovedades();
    }

    public void guardar() {
        if (validarInformacionNovedad()) {
            novedad.setNombre(controlTamanoCadena(novedad.getNombre(), 3000));
            novedad.setDescripcion(controlTamanoCadena(novedad.getDescripcion(), 3000));
            novedad.setUrl(controlTamanoCadena(novedad.getUrl(), 4000));
            novedad.setFecha(new Date());

            // Guardar persona que crea o elimina
            if ("I".equals(novedad.getEstado())) {
                novedad.setPersonaElimina(personaActual);
            } else if ("A".equals(novedad.getEstado())) {
                novedad.setPersonaCrea(personaActual);
            }

            // Guardar novedad
            servicioGeneral.guardarObjeto(novedad);
            sesion.removeAttribute("manejadorAdministrarNovedad");
            mensajeInfo("La novedad se ha guardado correctamente.");

        } else {
            return;
        }
    }

    private boolean validarInformacionNovedad() {

        boolean validada = true;

        if (novedad.getClasificacion().getId() == null || "".equals(novedad.getClasificacion().getId().toString())
                || novedad.getClasificacion().getId() == 0L) {
            mensajeError("Debe seleccionar una clasificación para la novedad.");
            validada = false;
        }
        if (esCadenaVacia(novedad.getNombre())) {
            mensajeError("Debe ingresar el nombre de la novedad.");
            validada = false;
        }
        if (esCadenaVacia(novedad.getDescripcion())) {
            mensajeError("Debe ingresar la descripción de la novedad.");
            validada = false;
        }
        if (esCadenaVacia(novedad.getUrl())) {
            mensajeError("Debe ingresar la url que remite a la información completa de la novedad.");
            validada = false;
        }
        if (esCadenaVacia(novedad.getEstado())) {
            mensajeError("Debe asignar un estado a la novedad.");
            validada = false;
        }
        if (novedad.getFechaNovedad() != null && esCadenaVacia(novedad.getFechaNovedad().toString())) {
            mensajeError("Debe indicar la fecha en que ocurrió la novedad.");
            validada = false;
        }
        return validada;
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

    public SelectItem[] getEstadoItem() {
        return estadoItem;
    }

    public void setEstadoItem(SelectItem[] estadoItem) {
        this.estadoItem = estadoItem;
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

}
