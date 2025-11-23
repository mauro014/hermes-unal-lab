package co.edu.unal.hermes.vista.editorial;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaPadre;
import co.edu.unal.hermes.modelo.EstadoConvocatoria;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.vista.convocatorias.ManejadorConsultaConvocatoriasBase;

public class ManejadorConsultaConvocatoriasEditorial extends ManejadorConsultaConvocatoriasBase {

    private static final long serialVersionUID = 1573648775482142384L;

    private List<ConvocatoriaPadre> listaConvocatoriasPadre;

    public ManejadorConsultaConvocatoriasEditorial() {
        super();

        listaConvocatoriasPadre = new ArrayList<ConvocatoriaPadre>();

        // Se cargan las convocatorias padre.
        List<String> listaEstadoConvocatoriasPadre = new ArrayList<String>();
        listaEstadoConvocatoriasPadre.add(EstadoConvocatoria.ACTIVA);
        //listaEstadoConvocatoriasPadre.add(EstadoConvocatoria.CREACION);

        List<ConvocatoriaPadre> listaConvocatoriasPadreActiva = servicioModalidad
                .obtenerConvocatoriasPadreEnEstados(listaEstadoConvocatoriasPadre);

        if (listaConvocatoriasPadreActiva != null) {
            for (Iterator<ConvocatoriaPadre> i = listaConvocatoriasPadreActiva.iterator(); i.hasNext();) {
            	boolean esISBN = false;
                ConvocatoriaPadre convocatoriaPadre = (ConvocatoriaPadre) i.next();
                List<Convocatoria> listaHijos = servicioModalidad.obtenerConvocatoriasxPadre(convocatoriaPadre);
                esISBN = verificarTipoConvocatoria("SIS", listaHijos);
                boolean esConvLibrosNacional = verificarTipoConvocatoria("CLN", listaHijos);
                boolean esConvLibros = verificarTipoConvocatoria("CL", listaHijos);
                if (esISBN || esConvLibrosNacional || esConvLibros) {
                    convocatoriaPadre.setListaConvocatorias(obtenerListadeConvoctoriaActivasXPadre(convocatoriaPadre));
                    listaConvocatoriasPadre.add(convocatoriaPadre);
                }
            }
        }
    }

    public String AsignarConvocatoriaProyecto() {

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

        if (convocatoriaActual.getTipo().getId().equals("SIS") 
        		|| convocatoriaActual.getTipo().getId().equals("CLN")
        		|| convocatoriaActual.getTipo().getId().equals("CL")) {
            sesion.setAttribute("idConvocatoriaActual", convocatoriaActual.getId());
            return "irAceptarTerminosGenerico";
        } else {
            return "crearProyecto";
        }
    }

    public List<ConvocatoriaPadre> getListaConvocatoriasPadre() {
        return listaConvocatoriasPadre;
    }
}
