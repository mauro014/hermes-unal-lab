package co.edu.unal.hermes.vista.proyectos;

import java.util.Date;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;

public class ManejadorProyectosReclamacionEvaluacion extends ManejadorProyectosReclamacionBase {

    private static final long serialVersionUID = -72332156590804740L;

    public ManejadorProyectosReclamacionEvaluacion() {

        super("idProyectoReclamacionEval");
        cargarDatosVista(TIPO_RECLAMACION_EVALUACION);

    }

    public void enviarReclamacion() {

        // Se guardan los cambios
        proyectoActual.setEstadoReclamacionEvaluacion(Proyecto.RECLAMACION_ENVIADO);
        proyectoActual.setFechaEnvioReclamacionEva(new Date());
        servicioGeneral.guardarObjeto(proyectoActual);

        // Se actualiza la vista
        cargarDatosVista(TIPO_RECLAMACION_EVALUACION);

        // se envia correo de confirmación
        Persona coordinador = servicioProyecto.obtenerCoordinadorEvaluacionProyecto(proyectoActual.getId());
        enviarCorreoReclamacion(coordinador, 282, proyectoActual.getReclamacionEvaluacion());

    }

    public String atras() {
        sesion.removeAttribute("manejadorProyectosReclamacionEvaluacion");
        return "successProyectosProyecto";
    }

}
