/**
 * @author Martha Liliana Correa O.
 * @date 10/11/2016
 */

package co.edu.unal.hermes.vista.administrador;

import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.NovedadClasificacion;

public class ManejadorCrearEditarClasificacionNovedades extends ManejadorAdministrarClasificacionNovedades {

    /**
     * 
     */
    private static final long serialVersionUID = 1837621569716175888L;
    private boolean esEdicion;
    private boolean esNuevo;
    private NovedadClasificacion clasificacionNovedad;
    private SelectItem[] estadoClasificacion = { new SelectItem("", ""), new SelectItem("A", "Activa"),
            new SelectItem("I", "Inactiva") };

    public ManejadorCrearEditarClasificacionNovedades() {

        try {
            setEsEdicion((Boolean) sesion.getAttribute("esEdicion"));
        } catch (Exception e) {
            esEdicion = false;
        }

        if (esEdicion) {
            try {
                Long id = (Long) sesion.getAttribute("idClasificacionNovedad");
                String consulta = "select ncl from NovedadClasificacion ncl where ncl.id = '" + id + "'";
                List<NovedadClasificacion> clasificaciones = servicioGeneral
                        .obtenerObjetos(NovedadClasificacion.class, consulta);

                if (!esListaVacia(clasificaciones)) {
                    clasificacionNovedad = (NovedadClasificacion) clasificaciones.get(0);
                }
            } catch (Exception e) {
                // TODO
            }
        } else {
            esNuevo = true;
            clasificacionNovedad = new NovedadClasificacion();
        }
    }

    public String guardarClasificacion() {
        boolean validada = true;
        if (esCadenaVacia(clasificacionNovedad.getNombre())) {
            mensajeError("Debe indicar un nombre para la clasificación.");
            validada = false;
        } else if (esCadenaVacia(clasificacionNovedad.getDescripcion())) {
            mensajeError("Debe ingresar una breve descripción para la clasificación, ésta será utilizada para la búsqueda.");
            validada = false;
        } else if (clasificacionNovedad.getEstado().trim() == null
                || "".equals(clasificacionNovedad.getEstado().trim())) {
            mensajeError("Debe indicar un estado para la clasificación.");
            validada = false;
        }

        if (validada) {
            // Guardar clasificacion
            servicioGeneral.guardarObjeto(clasificacionNovedad);
            if (clasificacionNovedad.getId() != null) {
                mensajeInfo("La clasificación se ha creado correctamente.");
                return crearEditarClasificacionNovedad();
            }
        } else {
            mensajeError("Verifique la información que está ingresando, no ha superado la validación. "
                    + "Verifique longidud del texto y la selección del estado.");
        }
        
        sesion.removeAttribute("manejadorAdministrarClasificacionNovedades");
        sesion.removeAttribute("manejadorAdministrarNovedad");
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

    public NovedadClasificacion getClasificacionNovedad() {
        return clasificacionNovedad;
    }

    public void setClasificacionNovedad(NovedadClasificacion clasificacionNovedad) {
        this.clasificacionNovedad = clasificacionNovedad;
    }
}
