package co.edu.unal.hermes.vista.evaluadores;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.modelo.CalificacionEvaluacion;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.ModalidadCriterioTipoPregunta;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.correo.Correo;

public class ManejadorEvaluacion extends ManejadorBaseEvaluacion {

	private static final long serialVersionUID = 5410204577887554352L;
	private UIInput calificacionFinal = new UIInput();

	CorreoPlantilla correoActual = new CorreoPlantilla();
	String cuerpoCorreo = "";

	private UploadedFile archivoSubir;

	String mensajeError;

	public ManejadorEvaluacion() {

		super();
		motrarAprobacion = false;
		cargarValoresGenerales();
		calcularPromedioInicial();
	}

	public String guardarYFinalizar() {
		if (guardarEvaluaciones()) {

			personaActual = (Persona) sesion.getAttribute("persona");

			correoActual = cargarPlantilla(91);// 42
			editarCorreo(personaActual, evaluacionProyecto.getProyecto());
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			String dirCorreo = personaActual.getEmail();
			correo.adicionarDireccion(dirCorreo);
			correo.adicionarCopiaOculta(new String(personaActual.getEmail()));
			correo.adicionarDireccion(Correo.CORREO_HERMES);
			correo.setAsunto(correoActual.getAsunto());
			correo.setCuerpo(cuerpoCorreo);
			try {
				servicioCorreo.enviarCorreo(correo);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (evaluacionProyecto.getProyecto() != null && evaluacionProyecto.getEstado().equals("S")) {
				Persona coordinadorEvaluacion = servicioProyecto
						.obtenerCoordinadorEvaluacionProyecto(evaluacionProyecto.getProyecto().getId());
				if (coordinadorEvaluacion != null) {
					correo = new Correo();
					correo.setOrigen(Correo.CORREO_HERMES);
					dirCorreo = coordinadorEvaluacion.getEmail();
					correo.adicionarDireccion(dirCorreo);
					correo.setAsunto("Confirmacion registro de evaluación.");
					correo.setCuerpo("Confirmación de registro de evaluación del proyecto '"
							+ evaluacionProyecto.getProyecto().getNombre() + "' con código '"
							+ evaluacionProyecto.getProyecto().getId() + "' por el profesor "
							+ personaActual.getNombreCompleto());
					correo.adicionarDireccion(Correo.CORREO_HERMES);
					try {
						servicioCorreo.enviarCorreo(correo);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (evaluacionProyecto.getEstado().equals("S")) {
				sesion.removeAttribute("manejadorEvaluacion");
				Boolean isEvaluadorCoordinador = false;
				isEvaluadorCoordinador = (Boolean) sesion.getAttribute("evaluadorCoordinador");
				if (isEvaluadorCoordinador != null) {
					if (isEvaluadorCoordinador) {
						sesion.removeAttribute("evaluadorCoordinador");
						return "inbox";
					}
				}
				sesion.removeAttribute("manejadorProyectosEvaluador");
				return "proyectosEvaluador";

			} else {
				mensajeError(
						"Su evaluación ha sido guardada pero no ha sido enviada. Debe diligenciar todos los campos.");
				return "";
			}
		}
		sesion.removeAttribute("manejadorProyectosEvaluador");
		return "";
	}

	public void descargarDocumentoEvaluacion() {
		descargarArchivoGenerico("HER_PROYECTO_EVALUADOR", evaluacionProyecto.getId().toString(),
				evaluacionProyecto.getDocumentoEvaluador());
	}

	public CorreoPlantilla cargarPlantilla(int cod_id) {

		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		CorreoPlantilla a = new CorreoPlantilla();

		List lista = servicioGeneral.obtenerObjetos("select c from CorreoPlantilla c where c.id='" + cod_id + "'");
		if (lista != null && lista.size() > 0) {
			correoActualAux = (CorreoPlantilla) lista.get(0);
		}

		return correoActualAux;
	}

	public String editarCorreo(Persona personaAux, Proyecto proyectoActual) {
		try {
			String coinvNombre = "";

			String correo = correoActual.getCuerpo();
			String investigador = "";
			String hora = "";

			// hora = String.valueOf(fechaActual.getHours()) + ":" +
			// String.valueOf(fechaActual.getSeconds());
			investigador = personaAux.getNombre1() + " " + personaAux.getApellido1() + " " + personaAux.getApellido2();
			correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);
			correo = correo.replaceAll("<<IDPROGRAMA>>", proyectoActual.getId().toString());
			correo = correo.replaceAll("<<PROGRAMA>>", proyectoActual.getNombre());
			// correo = correo.replaceAll("<<FECHA>>", Fecha.fechaActual()+"-"+
			// hora );
			// correo = correo.replaceAll("<<IDAVAL>>", id);
			// correo = correo.replaceAll("<<TIPO>>", mov.getTipoMovilidad()
			// .getNombre());

			cuerpoCorreo = correo;

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "";
	}
	
	public void guardarArchivo() {
		if (archivoSubir != null) {
			try {
				int i = archivoSubir.getFileName().lastIndexOf("\\");
				evaluacionProyecto.setDocumentoEvaluador(archivoSubir.getFileName());
				servicioGeneral.guardarObjeto(evaluacionProyecto);
				if (evaluacionProyecto.getId() != null) {
					cargarArchivoDisco(archivoSubir, "HER_PROYECTO_EVALUADOR", evaluacionProyecto.getId().toString());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("No se ha seleccionado ningun archivo.");
			mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("datosGuardados", mensaje);
		}
	}

	public String getCriterioModalidadTipoPregunta() {
		CalificacionEvaluacion ce = (CalificacionEvaluacion) tablaCC.getRowData();
		ModalidadCriterioTipoPregunta mctp = servicioEvaluacion.obtenerModalidadTipoPreguntaXModalidadYCriterio(
				evaluacionProyecto.getProyecto().getModalidad().getId(), ce.getCriterio().getId());
		System.out.println(mctp.getTipoPregunta().getPagina());
		return mctp.getTipoPregunta().getPagina();

	}

	public String atras() {
		Boolean isEvaluadorCoordinador = false;
		isEvaluadorCoordinador = (Boolean) sesion.getAttribute("evaluadorCoordinador");
		if (isEvaluadorCoordinador != null) {
			if (isEvaluadorCoordinador) {
				sesion.removeAttribute("evaluadorCoordinador");
				return "inbox";
			}
		}
		return "proyectosEvaluador";
	}

	public UIInput getCalificacionFinal() {
		return calificacionFinal;
	}

	public void setCalificacionFinal(UIInput calificacionFinal) {
		this.calificacionFinal = calificacionFinal;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public CorreoPlantilla getCorreoActual() {
		return correoActual;
	}

	public void setCorreoActual(CorreoPlantilla correoActual) {
		this.correoActual = correoActual;
	}

	public String getCuerpoCorreo() {
		return cuerpoCorreo;
	}

	public void setCuerpoCorreo(String cuerpoCorreo) {
		this.cuerpoCorreo = cuerpoCorreo;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public UploadedFile getArchivoSubir() {
		return archivoSubir;
	}

	public void setArchivoSubir(UploadedFile archivoSubir) {
		this.archivoSubir = archivoSubir;
	}

}
