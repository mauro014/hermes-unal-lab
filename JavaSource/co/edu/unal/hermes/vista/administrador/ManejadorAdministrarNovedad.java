/**
 * @author Martha Liliana Correa O.
 * @date 09/11/2016
 */

package co.edu.unal.hermes.vista.administrador;

import java.util.ArrayList;
import java.util.List;

import co.edu.unal.hermes.modelo.Novedad;

public class ManejadorAdministrarNovedad extends ManejadorAdministrarClasificacionNovedades {

    /**
     * 
     */
    private static final long serialVersionUID = -7548089425399445265L;
    private Novedad novedadSeleccionada;
    private List<Novedad> listaNovedades;
    private List<Novedad> filteredNovedades;
    private Long tamanoLista;
    private String clasificacion;

    public ManejadorAdministrarNovedad() {
        // Inicialización de valores
        listaNovedades = new ArrayList<Novedad>();
        tamanoLista = 0L;
        consultarClasificacionesActivasNovedades();
    }

    /**
     * Método para consultar las novedades asociadas a una clasificación se
     * listan en forma ascendente de acuerdo con el nombre
     */
    public void consultarNovedades() {
        listaNovedades = new ArrayList<Novedad>();
        if (!esCadenaVacia(clasificacion)) {
            listaNovedades = servicioGeneral.obtenerObjetosLimitado(Novedad.class,
                    "select #id nov.id, #fechaNovedad nov.fechaNovedad, #estado nov.estado, #nombre nov.nombre "
                            + "from Novedad nov where nov.clasificacion.id in ('" + clasificacion + "')"
                            + "order by nov.fechaNovedad desc");
            tamanoLista = (long) listaNovedades.size();
        } else {
            mensajeError("Debe seleccionar una clasificación.");
        }
    }

    /**
     * Método que remite al formulario para crear una nueva novedad
     * 
     * @return
     */
    public String crearNovedad() {
        eliminarManejadoresNovedades();
        return "crearEditarNovedad";
    }

    /**
     * Método para consultar / editar la novedad seleccionada
     * 
     * @return
     */
    public String editarNovedad() {
        eliminarManejadoresNovedades();
        sesion.setAttribute("idNovedad", novedadSeleccionada.getId());
        return "crearEditarNovedad";
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

    public Novedad getNovedadSeleccionada() {
        return novedadSeleccionada;
    }

    public void setNovedadSeleccionada(Novedad novedadSeleccionada) {
        this.novedadSeleccionada = novedadSeleccionada;
    }

    public List<Novedad> getListaNovedades() {
        return listaNovedades;
    }

    public void setListaNovedades(List<Novedad> listaNovedades) {
        this.listaNovedades = listaNovedades;
    }

    public List<Novedad> getFilteredNovedades() {
        return filteredNovedades;
    }

    public void setFilteredNovedades(List<Novedad> filteredNovedades) {
        this.filteredNovedades = filteredNovedades;
    }

}
