package co.edu.unal.hermes.vista.proyectos;

import java.util.Date;

import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;

public class ManejadorProyectosReclamacion extends ManejadorProyectosReclamacionBase {

	private static final long serialVersionUID = 4172241077713699627L;

	public ManejadorProyectosReclamacion() {

		super("idProyectoReclamacion");

		cargarDatosVista(TIPO_RECLAMACION_REQUISITOS);

	}

	public void enviarReclamacion() {

		// Se guardan los cambios
		proyectoActual.setFechaEnvioReclamacionReq(new Date());
		proyectoActual.setEstadoReclamacion(Proyecto.RECLAMACION_ENVIADO);
		servicioGeneral.guardarObjeto(proyectoActual);

		// Se actualiza la vista
		cargarDatosVista(TIPO_RECLAMACION_REQUISITOS);

		Convocatoria convocatoria = (Convocatoria) proyectoActual.getModalidad();
		// se envia correo de confirmación
		if (!convocatoria.getPadre().isEsConvocatoriaEditorial()) {
			Persona coordinador = servicioProyecto.obtenerCoordinadorRequisitosProyecto(proyectoActual.getId());
			enviarCorreoReclamacion(coordinador, 266, proyectoActual.getReclamacion());
		}else {
			Persona coordinador = servicioProyecto.obtenerCoordinadorRequisitosProyectoEditorial(proyectoActual.getId());
			enviarCorreoReclamacion(coordinador, 386, proyectoActual.getReclamacion());
		}

	}

	public String atras() {
		
		Convocatoria convocatoria = (Convocatoria) proyectoActual.getModalidad();
		if (!convocatoria.getPadre().isEsConvocatoriaEditorial()) {
			sesion.removeAttribute("ManejadorProyectosReclamacion");
			return "successProyectosProyecto";
		}else {
			sesion.removeAttribute("ManejadorProyectosReclamacion");
			return "proyectosEditoriales";
		}
		
		
	}

}
