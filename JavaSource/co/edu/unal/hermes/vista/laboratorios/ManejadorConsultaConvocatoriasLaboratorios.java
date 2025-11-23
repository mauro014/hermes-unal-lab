package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.vista.convocatorias.ManejadorConsultaConvocatoriasBase;

/**
 * @author dgbenitezc
 * 
 */
public class ManejadorConsultaConvocatoriasLaboratorios extends ManejadorConsultaConvocatoriasBase {

    private static final long serialVersionUID = -8290163232728142592L;

    private List<ConvocatoriaPadre> listaConvocatoriasPadre;

    public ManejadorConsultaConvocatoriasLaboratorios() {

        listaConvocatoriasPadre = new ArrayList<ConvocatoriaPadre>();

        System.out.println("ManejadorConsultaConvocatoriasLaboratorios");

        try {

            // Se cargan las convocatorias padre.
            List<String> listaEstadoConvocatoriasPadre = new ArrayList<String>();
            listaEstadoConvocatoriasPadre.add(EstadoConvocatoria.CREACION);
            List<ConvocatoriaPadre> listaConvocatoriasPadreActiva = servicioModalidad
                    .obtenerConvocatoriasPadreEnEstados(listaEstadoConvocatoriasPadre);

            if (listaConvocatoriasPadreActiva != null) {
                boolean esProyLabs;
                for (Iterator<ConvocatoriaPadre> i = listaConvocatoriasPadreActiva.iterator(); i.hasNext();) {
                    ConvocatoriaPadre convocatoriaPadre = (ConvocatoriaPadre) i.next();
                    List<Convocatoria> listaHijos = servicioModalidad.obtenerConvocatoriasxPadre(convocatoriaPadre);
                    esProyLabs = verificarTipoConvocatoria(TipoModalidad.REGISTRO_PROYECTOS_LABORATORIOS, listaHijos);
                    if (esProyLabs) {
                        convocatoriaPadre
                                .setListaConvocatorias(obtenerListadeConvoctoriaActivasXPadre(convocatoriaPadre));
                        listaConvocatoriasPadre.add(convocatoriaPadre);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String AsignarConvocatoriaProyecto() {
        System.out.println("AsignarConvocatoriaProyecto:");

        sesion.removeAttribute("manejadorAceptarTerminosReferencia");
        sesion.setAttribute("proyecto", null);

        String idModalidad = obtenerValorMapContext("idModalidad");
        Long id = Long.valueOf(idModalidad);

        // SE OBTIENE LA CONVOCATORIA PARA LA CUAL VA A SER CREADO UN PROYECTO
        Convocatoria convocatoriaActual = (Convocatoria) servicioGeneral.obtenerObjeto(new Convocatoria(), id);

        Proyecto p = new Proyecto();
        p.setModalidad(convocatoriaActual);
        sesion.setAttribute("proyecto", p);
        borrarManejadoresInsercionProyecto();

        if (convocatoriaActual.getTipo().getId().equals(TipoModalidad.REGISTRO_PROYECTOS_LABORATORIOS)) {
            sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
            return "crearProyectoConFichaMinima";
        } else {
            return "";
        }

    }

    public List<ConvocatoriaPadre> getListaConvocatoriasPadre() {
        return listaConvocatoriasPadre;
    }
}
