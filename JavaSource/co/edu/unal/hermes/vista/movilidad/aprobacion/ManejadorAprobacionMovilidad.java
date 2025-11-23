/**
 * Modified by Mauricio Amaya Ríos<br/>
 * Date:  12/12/2013<br/>
 */

package co.edu.unal.hermes.vista.movilidad.aprobacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.bd.imp.MovilidadDAOHibernate;
import co.edu.unal.hermes.modelo.ArchivoMovilidad;
import co.edu.unal.hermes.modelo.ArchivoMovilidadDE;
import co.edu.unal.hermes.modelo.ArchivoMovilidadEP;
import co.edu.unal.hermes.modelo.ArchivoMovilidadVE;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadDocentesArtes;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesArtes;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadRequisito;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoArchivoMovilidad;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.modelo.TipoRequisito;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.utils.Navegacion;
import co.edu.unal.hermes.vista.movilidad.ManejadorBaseRevisionMovilidad;

/**
 * The Class ManejadorAprobacionMovilidad.
 */
public class ManejadorAprobacionMovilidad extends ManejadorBaseRevisionMovilidad {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1234123413412341231L;

	/** The correo actual. */
	CorreoPlantilla correoActual = new CorreoPlantilla();

	/** The cuerpo correo. */
	String cuerpoCorreo = "";

	/** The Constant ID_MOVILIDAD_SESION. */
	private static final String ID_MOVILIDAD_SESION = "idMovilidad";

	/** The Constant MENSAJE_ERROR_VALOR_APROBADO. */
	private static final String MENSAJE_ERROR_VALOR_APROBADO = "Debe ingresar un valor aprobado por la facultad.";

	/** The lista requisito aux. */
	private List<MovilidadRequisito> listaRequisitoAux;

	/** The activar aprobacion. */
	private boolean cumpleRequisitos;

	/** The activar aprobacion. */
	private boolean conceptosIngresado;

	/** The mensaje correcto. */
	private static final String MENSAJE_CORRECTO = "La información ha sido guardada correctamente.";

	private static final String MENSAJE_INCORRECTO = "Se ha presentado un error al guardar la información.";

	private Dependencia dependenciaMov;

	private Long restriccionTiquetes = 0L;
	private Integer restriccionTiempoSolicitud = 0;

	private String validadcionCostosSubmodalidad;

	/**
	 * Cargar listas movilidades.
	 */
	public void cargarListasMovilidades() {
		Dependencia dependencia = ((InvestigadorInterno) personaActual).getDependencia();

		dependenciaMov = new Dependencia();
		dependenciaMov = dependencia;

		String modalidadId = null;

		if (StringUtils.isNotEmpty(convocatoriaPadreFiltro) && StringUtils.isNotEmpty(convocatoriaFiltro)) {
			modalidadId = convocatoriaFiltro;
		}

		listaMovilidadesGeneral = new ArrayList<ConvocatoriaMovilidadVista>();

		listaMovilidadesVisitante = new ArrayList<MovilidadVisitanteExterior>();

		// Se cargan las movilidades de Visitantes de convocatoria normal
		listaMovilidadesVisitante
				.addAll(servicioMovilidad.obtenerMovilidadesRevision(MovilidadVisitanteExterior.VISITANTEEXTERIOR,
						personaActual, dependencia, modalidadId, MovilidadDAOHibernate.FACULTAD));

		crearMovilidadVistaVisitantesExt();

		listaMovilidadesEvento = new ArrayList<MovilidadDocentesExterior>();

		// Se cargan las movilidades de Evento
		listaMovilidadesEvento
				.addAll(servicioMovilidad.obtenerMovilidadesRevision(MovilidadDocentesExterior.EVENTODOCENTE,
						personaActual, dependencia, modalidadId, MovilidadDAOHibernate.FACULTAD));

		crearMovilidadVistaDocentesEventos();

		listaMovilidadesEstudiantePosgrado = new ArrayList<MovilidadEstudiantesPosgrado>();
		// Estudiante de Posgrado
		listaMovilidadesEstudiantePosgrado
				.addAll(servicioMovilidad.obtenerMovilidadesRevision(MovilidadEstudiantesPosgrado.ESTUDIANTEPOSGRADO,
						personaActual, dependencia, modalidadId, MovilidadDAOHibernate.FACULTAD));

		crearMovilidadVistaEstudiantesPosgrados();

		// Docentes artes
		listaMovilidadesDocentesArtes = servicioMovilidad.obtenerMovilidadesArtes(
				MovilidadDocentesArtes.ARTISTASDOCENTES, personaActual, dependencia, modalidadId);

		crearMovilidadVistaDocentesArtes();

		// Estudiantes artes
		listaMovilidadesEstudiantesArtes = servicioMovilidad.obtenerMovilidadesArtes(
				MovilidadEstudiantesArtes.ARTISTASESTUDIANTES, personaActual, dependencia, modalidadId);

		crearMovilidadVistaEstudiantesArtes();

		crearListaConvocatoriasItem();

	}

	/**
	 * Imprimir movilidad visitante.
	 */
	public void imprimirMovilidadVisitante() {
		imprimirMovilidadVisitanteGenerico(movilidadVisitanteSeleccionada);
	}

	/**
	 * Imprimir movilidad evento.
	 */
	public void imprimirMovilidadEvento() {
		imprimirMovilidadEventoGenerico(movilidadEventosSeleccionada);
	}

	/**
	 * Imprimir movilidad posgrado.
	 */
	public void imprimirMovilidadPosgrado() {
		imprimirMovilidadEstudiantesPosgradoGenerico(movilidadEstudiantesSeleccionada);
	}

	/**
	 * Imprimir movilidad docente artes.
	 */
	public void imprimirMovilidadDocenteArtes() {
		imprimirMovilidadDocenteArtesGenerico(movilidadDocenteArtesSeleccionada);
	}

	/**
	 * Imprimir movilidad estudiantes artes.
	 */
	public void imprimirMovilidadEstudiantesArtes() {
		imprimirMovilidadEstudiantesArtesGenerico(movilidadEstudiantesArtesSeleccionada);
	}

	/**
	 * Descargar documento evento.
	 */
	public void descargarDocumentoEvento() {
		Long idArchivo = Long.valueOf(obtenerValorMapContext("archivoResumen"));
		descargarArchivoMovilidadDEGenerico(idArchivo);
	}

	/**
	 * Descargar documento est posgrado.
	 */
	public void descargarDocumentoEstPosgrado() {
		Long idArchivo = Long.valueOf(obtenerValorMapContext("archivoResumen4"));
		descargarArchivoMovilidadEPGenerico(idArchivo);
	}

	/**
	 * Descargar documento docen artes.
	 */
	public void descargarDocumentoDocenArtes() {
		Long idArchivo = Long.valueOf(obtenerValorMapContext("archivoResumen6"));
		descargarArchivoMovilidadGenerico(idArchivo);
	}

	/**
	 * Descargar documento visitante.
	 */
	public void descargarDocumentoVisitante() {
		Long idArchivo = Long.valueOf(obtenerValorMapContext("archivoResumen1"));
		descargarArchivoMovilidadVEGenerico(idArchivo);
	}

	/**
	 * Descargar documento visitante.
	 */
	public void descargarDocumentoEstudiante() {
		Long idArchivo = Long.valueOf(obtenerValorMapContext("archivoResumen1"));
		List<ArchivoMovilidad> listaArchivos = movilidadEstudiantesArtesModificacion.getListaArchivo();
		Iterator<ArchivoMovilidad> i = listaArchivos.iterator();
		while (i.hasNext()) {
			ArchivoMovilidad archivoMovilidad = i.next();
			if (archivoMovilidad.getId().equals(idArchivo)) {
				descargarArchivoMovilidadGenerico(archivoMovilidad);
				break;
			}
		}
	}

	/**
	 * Insertar archivo movilidad ve.
	 *
	 * @param event
	 *            the event
	 */
	public void insertarArchivoMovilidadVE(FileUploadEvent event) {
		UploadedFile archivo = event.getFile();
		// Obtener código de solicitud
		Long idMovilidad = (Long) event.getComponent().getAttributes().get(ID_MOVILIDAD_SESION);
		// Cargar archivo disco
		ArchivoMovilidadVE archivoMovilidad = insertarArchivoMovilidadVEGenerico(idMovilidad, archivo,
				TipoArchivoMovilidad.REVISION_REQUISITOS);
		if (archivoMovilidad != null) {
			for (MovilidadVisitanteExterior mve : listaMovilidadesVisitante) {
				if (mve.getId().equals(idMovilidad)) {
					mve.agregarArchivoRequisitosFacultad(archivoMovilidad);
				}
			}
		}
	}

	/**
	 * Insertar archivo movilidad de.
	 *
	 * @param event
	 *            the event
	 */
	public void insertarArchivoMovilidadDE(FileUploadEvent event) {
		UploadedFile archivo = event.getFile();
		// Obtener código de solicitud
		Long idMovilidad = (Long) event.getComponent().getAttributes().get(ID_MOVILIDAD_SESION);
		// Cargar archivo disco
		ArchivoMovilidadDE archivoMovilidad = insertarArchivoMovilidadDEGenerico(idMovilidad, archivo,
				TipoArchivoMovilidad.REVISION_REQUISITOS);
		if (archivoMovilidad != null) {
			for (MovilidadDocentesExterior mde : listaMovilidadesEvento) {
				if (mde.getId().equals(idMovilidad)) {
					mde.agregarArchivoRequisitosFacultad(archivoMovilidad);
				}
			}
		}
	}

	/**
	 * Insertar archivo movilidad ep.
	 *
	 * @param event
	 *            the event
	 */
	public void insertarArchivoMovilidadEP(FileUploadEvent event) {
		UploadedFile archivo = event.getFile();
		// Obtener código de solicitud
		Long idMovilidad = (Long) event.getComponent().getAttributes().get(ID_MOVILIDAD_SESION);
		// Cargar archivo disco
		ArchivoMovilidadEP archivoMovilidad = insertarArchivoMovilidadEPGenerico(idMovilidad, archivo,
				TipoArchivoMovilidad.REVISION_REQUISITOS);
		if (archivoMovilidad != null) {
			for (MovilidadEstudiantesPosgrado mep : listaMovilidadesEstudiantePosgrado) {
				if (mep.getId().equals(idMovilidad)) {
					mep.agregarArchivoRequisitosFacultad(archivoMovilidad);
				}
			}
		}
	}

	/**
	 * Insertar archivo movilidad da.
	 *
	 * @param event
	 *            the event
	 */
	public void insertarArchivoMovilidadDA(FileUploadEvent event) {
		UploadedFile archivo = event.getFile();
		// Obtener código de solicitud
		Long idMovilidad = (Long) event.getComponent().getAttributes().get(ID_MOVILIDAD_SESION);
		// Cargar archivo disco
		ArchivoMovilidad archivoMovilidad = insertarArchivoMovilidadGenerico(idMovilidad, archivo,
				TipoArchivoMovilidad.REVISION_REQUISITOS, "C1");
		if (archivoMovilidad != null) {
			for (MovilidadDocentesArtes mda : listaMovilidadesDocentesArtes) {
				if (mda.getId().equals(idMovilidad)) {
					mda.agregarArchivoRequisitos(archivoMovilidad);
				}
			}
		}
	}

	/**
	 * Editar correo.
	 *
	 * @param personaAux
	 *            the persona aux
	 * @param tipo
	 *            the tipo
	 * @param id
	 *            the id
	 * @param comFac
	 *            the com fac
	 * @return the string
	 */
	public String editarCorreo(Persona personaAux, String tipo, Long id, String comFac) {
		String comentariosFacultado = eliminarCaracterSinReplace("$", comFac);
		String correo = correoActual.getCuerpo();
		String investigador = personaAux.getNombre1() + " " + personaAux.getApellido1() + " "
				+ personaAux.getApellido2();
		correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);
		correo = correo.replaceAll("<<IDMOVILIDAD>>", id.toString());
		correo = correo.replaceAll("<<TIPO>>", tipo);

		if (StringUtils.isEmpty(comentariosFacultado)) {
			comentariosFacultado = "No se realizaron observaciones.";
		}

		correo = correo.replaceAll("<<OBSERVACION>>", comentariosFacultado);

		cuerpoCorreo = correo;
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.unal.hermes.vista.ManejadorBase#cargarPlantilla(int)
	 */
	public CorreoPlantilla cargarPlantilla(int codId) {
		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		List<CorreoPlantilla> lista = servicioGeneral.obtenerObjetos(CorreoPlantilla.class,
				"select c from " + "CorreoPlantilla c where c.id='" + codId + "'");
		if (!esListaVacia(lista)) {
			correoActualAux = (CorreoPlantilla) lista.get(0);
		}
		return correoActualAux;
	}

	/**
	 * Aprobar movilidad visitante.
	 *
	 * @return the string
	 */
	public String aprobarMovilidadVisitante() {
		if (movilidadVisitanteSeleccionada.getAnioVigenciaAprFacultad() < Long.valueOf(getMinYear())) {
			movilidadVisitanteSeleccionada.setError("El año de vigencia definido no es válido.");
			return "";
		}
		String sqlBuscaConvocatoria = "select #id e.id, #montoApoyoGanadores e.montoApoyoGanadores, #tiempoEjecucionProyecto e.tiempoEjecucionProyecto from Convocatoria e where e.id = "
				+ movilidadVisitanteSeleccionada.getConvocatoria().getId() + "";

		List<Convocatoria> listConvs = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class, sqlBuscaConvocatoria);
		Convocatoria convActual = listConvs.get(0);
		if (convActual.getMontoApoyoGanadores() != null) {
			restriccionTiquetes = Long.parseLong(convActual.getMontoApoyoGanadores());
		}
		if (movilidadVisitanteSeleccionada.getValorAprobadoFacultad() == null && movilidadVisitanteSeleccionada.getDispPresupuestal().equals("SI")) {
			movilidadVisitanteSeleccionada
					.setError("Por favor, indicar el valor de aporte por la facultad.");
			return "";
		}
		if (movilidadVisitanteSeleccionada.getDispPresupuestal().equals("SI") && movilidadVisitanteSeleccionada.getValorAprobadoFacultad() > restriccionTiquetes) {
			movilidadVisitanteSeleccionada
					.setError("El valor máximo que cubre la convocatoria es de $" + restriccionTiquetes + ".");
			return "";
		}
		/*if (movilidadVisitanteSeleccionada.getValorAprobadoFacultad() != null) {
			boolean error = false;
			if (movilidadVisitanteSeleccionada.getValorAprobadoFacultad() == 0) {
				error = true;
			}
			if (error) {
				errorString = MENSAJE_ERROR_VALOR_APROBADO;
				movilidadVisitanteSeleccionada.setError(errorString);
				return "";
			}
		} else {
			errorString = MENSAJE_ERROR_VALOR_APROBADO;
			movilidadVisitanteSeleccionada.setError(errorString);
			return "";
		}*/

		verificarRequisitos(listaRequisitoAux);
		if (!cumpleRequisitos) {
			errorString = "Hay un requisito que no se cumple o un criterio no aprobado.";
			movilidadVisitanteSeleccionada.setError(errorString);
			return "";
		}
		if (!conceptosIngresado) {
			errorString = "Debe ingresar el concepto para todos los criterios de evaluación.";
			movilidadVisitanteSeleccionada.setError(errorString);
			return "";
		}

		guardarRequisitosVisitanteExt();

		if (StringUtils.isNoneEmpty(movilidadVisitanteSeleccionada.getMovilidadConvocatoriaFacultad())
				&& "S".equals(movilidadVisitanteSeleccionada.getMovilidadConvocatoriaFacultad())) {
			return guardarRevisionMovilidadVisitante("SI", "SI", 66);
		} else {
			return guardarRevisionMovilidadVisitante("SI", null, 66);
		}
	}

	/**
	 * No cumple requisitos movilidad visitante.
	 *
	 * @return the string
	 */
	public String noCumpleRequisitosMovilidadEstudiantes() {
		movilidadEstudiantesSeleccionada.setError("");
		verificarRequisitos(getListaRequisitoAuxVerificacion());
		if (cumpleRequisitos) {
			errorString = "Por favor seleccione el requisito que no se cumple.";
			movilidadEstudiantesSeleccionada.setError(errorString);
			return "";
		} else {
			movilidadEstudiantesSeleccionada.setCumpleRequisitos("NO");
			return noAprobarMovilidadPosgrado();
		}
	}

	/**
	 * No cumple requisitos movilidad visitante.
	 *
	 * @return the string
	 */
	public String cumpleRequisitosMovilidadEstudiantes() {
		movilidadEstudiantesSeleccionada.setError("");
		verificarRequisitos(getListaRequisitoAuxVerificacion());
		if (!cumpleRequisitos) {
			errorString = "Existen requisitos que no se cumplen. Por favor verifique la revisión de requisitos.";
			movilidadEstudiantesSeleccionada.setError(errorString);
		} else {
			movilidadEstudiantesSeleccionada.setCumpleRequisitos("SI");
		}
		return "";
	}

	/**
	 * No cumple requisitos movilidad visitante.
	 *
	 * @return the string
	 */
	public String noCumpleRequisitosMovilidadEventos() {
		movilidadEventosSeleccionada.setError("");
		verificarRequisitos(getListaRequisitoAuxVerificacion());
		if (cumpleRequisitos) {
			errorString = "Por favor seleccione el requisito que no se cumple.";
			movilidadEventosSeleccionada.setError(errorString);
			return "";
		} else {
			movilidadEventosSeleccionada.setCumpleRequisitos("NO");
			return noAprobarMovilidadEvento();
		}
	}

	/**
	 * No cumple requisitos movilidad visitante.
	 *
	 * @return the string
	 */
	public String cumpleRequisitosMovilidadEventos() {
		movilidadEventosSeleccionada.setError("");
		verificarRequisitos(getListaRequisitoAuxVerificacion());
		if (!cumpleRequisitos) {
			errorString = "Existen requisitos que no se cumplen. Por favor verifique la revisión de requisitos.";
			movilidadEventosSeleccionada.setError(errorString);
		} else {
			movilidadEventosSeleccionada.setCumpleRequisitos("SI");
		}
		return "";
	}

	/**
	 * No cumple requisitos movilidad visitante.
	 *
	 * @return the string
	 */
	public String noCumpleRequisitosMovilidadVisitante() {
		movilidadVisitanteSeleccionada.setError("");
		verificarRequisitos(getListaRequisitoAuxVerificacion());
		if (cumpleRequisitos) {
			errorString = "Por favor seleccione el requisito que no se cumple.";
			movilidadVisitanteSeleccionada.setError(errorString);
			return "";
		} else {
			movilidadVisitanteSeleccionada.setCumpleRequisitos("NO");
			return noAprobarMovilidadVisitante();
		}
	}

	/**
	 * Cumple requisitos movilidad visitante.
	 *
	 * @return the string
	 */
	public String cumpleRequisitosMovilidadVisitante() {
		movilidadVisitanteModificacion.setError("");
		verificarRequisitos(getListaRequisitoAuxVerificacion());
		if (!cumpleRequisitos) {
			errorString = "Existen requisitos que no se cumplen. Por favor verifique la revisión de requisitos.";
			movilidadVisitanteSeleccionada.setError(errorString);
		} else {
			movilidadVisitanteSeleccionada.setCumpleRequisitos("SI");
		}
		return "";
	}

	/**
	 * No aprobar movilidad visitante.
	 *
	 * @return the string
	 */
	public String noAprobarMovilidadVisitante() {

		if (movilidadVisitanteSeleccionada.getDispPresupuestal().equals("SI")) {
			verificarRequisitos(listaRequisitoAux);
			if (!conceptosIngresado && movilidadVisitanteSeleccionada.getCumpleRequisitos().equals("SI")) {
				errorString = "Debe ingresar el concepto para todos los criterios de evaluación.";
				movilidadVisitanteSeleccionada.setError(errorString);
				return "";
			}
			guardarRequisitosVisitanteExt();
		}

		if (movilidadVisitanteSeleccionada.getMovilidadConvocatoriaFacultad() != null
				&& movilidadVisitanteSeleccionada.getMovilidadConvocatoriaFacultad().equals("S")) {
			return guardarRevisionMovilidadVisitante("NO", "NO", 68);
		} else {
			return guardarRevisionMovilidadVisitante("NO", null, 68);
		}
	}

	/**
	 * Modificar movilidad visitante.
	 *
	 * @return the string
	 */
	public String modificarMovilidadVisitante() {
		movilidadEstudiantesModificacion=new MovilidadEstudiantesPosgrado();
		movilidadEventosModificacion=new MovilidadDocentesExterior();
		Iterator<MovilidadVisitanteExterior> i = listaMovilidadesVisitante.iterator();
		while (i.hasNext()) {
			MovilidadVisitanteExterior movilidadVisitanteExterior = i.next();
			if (movilidadVisitanteExterior.getId().equals(Long.valueOf(movilidadVistaSeleccionada.getIdSolicitud()))) {
				movilidadVisitanteModificacion = movilidadVisitanteExterior;
				break;
			}
		}
		movilidadVisitanteModificacion.setError("");
		if (movilidadVisitanteModificacion.getAnioVigenciaAprFacultad() == null
				|| movilidadVisitanteModificacion.getAnioVigenciaAprFacultad() == 0L) {
			movilidadVisitanteModificacion.setAnioVigenciaAprFacultad(
					Long.valueOf(String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));
		}
		mostrarRequisitosVisitanteExt(movilidadVisitanteModificacion.getId());

		return "";
	}

	/**
	 * Guardar movilidad visitante.
	 */
	public void guardarMovilidadVisitante() {
		try {
			if (validarMovilidadRevisionVE()) {
				servicioGeneral.guardarObjeto(movilidadVisitanteModificacion);
				crearHistoricoEstadoMovilidadInvestigador(movilidadVisitanteModificacion, "Actualización de valores o fechas por parte de la Facultad");
				mensajeInfo(MENSAJE_CORRECTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mensajeError(MENSAJE_INCORRECTO);
		}
	}

	private boolean validarMovilidadRevisionVE() {
		String sqlBuscaConvocatoria = "select #id e.id, #montoApoyoGanadores e.montoApoyoGanadores, #tiempoEjecucionProyecto e.tiempoEjecucionProyecto from Convocatoria e where e.id = "
				+ movilidadVisitanteModificacion.getConvocatoria().getId() + "";

		List<Convocatoria> listConvs = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class, sqlBuscaConvocatoria);
		Convocatoria convActual = listConvs.get(0);
		restriccionTiempoSolicitud = convActual.getTiempoEjecucionProyecto();
		if (convActual.getMontoApoyoGanadores() != null) {
			restriccionTiquetes = Long.parseLong(convActual.getMontoApoyoGanadores());
		}
		if ((movilidadVisitanteModificacion.getMonto() * movilidadVisitanteModificacion.getNumerodias())
				+ movilidadVisitanteModificacion.getCostotiquete() > restriccionTiquetes) {
			mensajeError("El valor máximo que cubre la convocatoria es de $" + restriccionTiquetes + ".");
			return false;
		}
		if(!esNulo(movilidadVisitanteModificacion.getMovilidadTipo())) {
			if (movilidadVisitanteModificacion.getMovilidadTipo().equals("V") && movilidadVisitanteModificacion.getCostoEstimuloVirtual() > 3511208L) {
				mensajeError("El estímulo para modalidad virtual no puede ser mayor a $3'511.208");
				return false;
			}
		}	
		return true;
	}

	/**
	 * Guardar movilidad visitante.
	 */
	public void guardarMovilidadEstudiantes() {
		try {
			if (validarMovilidadRevisionEst()) {
				servicioGeneral.guardarObjeto(movilidadEstudiantesModificacion);
				crearHistoricoEstadoMovilidadInvestigador(movilidadEstudiantesModificacion, "Actualización de valores o fechas por parte de la Facultad");
				mensajeInfo(MENSAJE_CORRECTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mensajeError(MENSAJE_INCORRECTO);
		}
	}

	/**
	 * Guardar movilidad docentes.
	 */
	public void guardarMovilidadDocentes() {
		try {
			if (validarMovilidadRevisionDE()) {
				servicioGeneral.guardarObjeto(movilidadEventosModificacion);
				crearHistoricoEstadoMovilidadInvestigador(movilidadEventosModificacion, "Actualización de valores o fechas por parte de la Facultad");
				mensajeInfo(MENSAJE_CORRECTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mensajeError(MENSAJE_INCORRECTO);
		}
	}

	private boolean validarMovilidadRevisionDE() {
		String sqlBuscaConvocatoria = "select #id e.id, #montoApoyoGanadores e.montoApoyoGanadores, #tiempoEjecucionProyecto e.tiempoEjecucionProyecto, #validacionCostosConvocatoriaMovilidad e.validacionCostosConvocatoriaMovilidad from Convocatoria e where e.id = "
				+ movilidadEventosModificacion.getConvocatoria().getId() + "";

		List<Convocatoria> listConvs = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class, sqlBuscaConvocatoria);
		Convocatoria convActual = listConvs.get(0);
		restriccionTiempoSolicitud = convActual.getTiempoEjecucionProyecto();
		if (convActual.getMontoApoyoGanadores() != null) {
			restriccionTiquetes = Long.parseLong(convActual.getMontoApoyoGanadores());
		}
		if (convActual.getValidacionCostosConvocatoriaMovilidad() != null) {
			validadcionCostosSubmodalidad = convActual.getValidacionCostosConvocatoriaMovilidad();
		}
		if (movilidadEventosModificacion.getSubModalidadConvocatoria() != null) {
			if (validadcionCostosSubmodalidad != null) {
				String[] valMov = validadcionCostosSubmodalidad.split(",");
				for (int i = 0; i < valMov.length; i++) {
					String[] valSubmov = valMov[i].split("=");
					if (valSubmov[0].equals(movilidadEventosModificacion.getSubModalidadConvocatoria())) {
						restriccionTiquetes = Long.parseLong(valSubmov[1]);
					}
				}
			}
		}
		if (movilidadEventosModificacion.getCostoevento().longValue()
				+ movilidadEventosModificacion.getCostotiquete().longValue()
				+ movilidadEventosModificacion.getValorViaticos().longValue() > restriccionTiquetes) {
			mensajeError("El valor máximo que cubre la convocatoria es de $" + restriccionTiquetes + ".");
			return false;
		}
		return true;
	}

	/**
	 * Guardar movilidad docentes.
	 */
	public void guardarMovilidadEstudaintes() {
		try {
			if (validarMovilidadRevisionEst()) {
				servicioGeneral.guardarObjeto(movilidadEstudiantesModificacion);
				crearHistoricoEstadoMovilidadInvestigador(movilidadEstudiantesModificacion, "Actualización de valores o fechas por parte de la Facultad");
				mensajeInfo(MENSAJE_CORRECTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mensajeError(MENSAJE_INCORRECTO);
		}
	}

	private boolean validarMovilidadRevisionEst() {
		String sqlBuscaConvocatoria = "select #id e.id, #montoApoyoGanadores e.montoApoyoGanadores, #tiempoEjecucionProyecto e.tiempoEjecucionProyecto from Convocatoria e where e.id = "
				+ movilidadEstudiantesModificacion.getConvocatoria().getId() + "";

		List<Convocatoria> listConvs = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class, sqlBuscaConvocatoria);
		Convocatoria convActual = listConvs.get(0);
		restriccionTiempoSolicitud = convActual.getTiempoEjecucionProyecto();
		if (convActual.getMontoApoyoGanadores() != null) {
			restriccionTiquetes = Long.parseLong(convActual.getMontoApoyoGanadores());
		}
		if (movilidadEstudiantesModificacion.getCostoevento().longValue()
				+ movilidadEstudiantesModificacion.getCostotiquete().longValue()
				+ movilidadEstudiantesModificacion.getValorViaticos().longValue() > restriccionTiquetes) {
			mensajeError("El valor máximo que cubre la convocatoria es de $" + restriccionTiquetes + ".");
			return false;
		}
		return true;
	}

	/**
	 * Modificar movilidad docentes.
	 *
	 * @return the string
	 */
	public String modificarMovilidadDocentes() {
		movilidadVisitanteModificacion=new MovilidadVisitanteExterior();
		movilidadEstudiantesModificacion=new MovilidadEstudiantesPosgrado();
		Iterator<MovilidadDocentesExterior> i = listaMovilidadesEvento.iterator();
		while (i.hasNext()) {
			MovilidadDocentesExterior movilidadDocentesExterior = i.next();
			if (movilidadDocentesExterior.getId().equals(Long.valueOf(movilidadVistaSeleccionada.getIdSolicitud()))) {
				movilidadEventosModificacion = movilidadDocentesExterior;
				break;
			}
		}
		movilidadVisitanteModificacion.setError("");
		mostrarRequisitosDocentes(movilidadEventosModificacion.getId());
		return "";
	}

	/**
	 * Modificar movilidad docentes.
	 *
	 * @return the string
	 */
	public String modificarMovilidadDocentesArtes() {
		Iterator<MovilidadDocentesArtes> i = listaMovilidadesDocentesArtes.iterator();
		while (i.hasNext()) {
			MovilidadDocentesArtes movilidadDocentesArtes = i.next();
			if (movilidadDocentesArtes.getId().equals(Long.valueOf(movilidadVistaSeleccionada.getIdSolicitud()))) {
				movilidadDocentesArtesModificacion = movilidadDocentesArtes;
				break;
			}
		}
		mostrarRequisitosDocentesArtes(movilidadDocentesArtesModificacion.getId());
		return "";
	}

	/**
	 * Modificar movilidad docentes.
	 *
	 * @return the string
	 */
	public String modificarMovilidadEstudiantesArtes() {
		Iterator<MovilidadEstudiantesArtes> i = listaMovilidadesEstudiantesArtes.iterator();
		while (i.hasNext()) {
			MovilidadEstudiantesArtes movilidadEstudiantesArtes = i.next();
			if (movilidadEstudiantesArtes.getId().equals(Long.valueOf(movilidadVistaSeleccionada.getIdSolicitud()))) {
				movilidadEstudiantesArtesModificacion = movilidadEstudiantesArtes;
				break;
			}
		}
		mostrarRequisitosEstudianteArtes(movilidadEstudiantesArtesModificacion.getId());
		return "";
	}

	/**
	 * Modificar movilidad estudiantes.
	 *
	 * @return the string
	 */
	public String modificarMovilidadEstudiantes() {
		movilidadVisitanteModificacion=new MovilidadVisitanteExterior();
		movilidadEventosModificacion=new MovilidadDocentesExterior();
		Iterator<MovilidadEstudiantesPosgrado> i = listaMovilidadesEstudiantePosgrado.iterator();
		while (i.hasNext()) {
			MovilidadEstudiantesPosgrado movilidadEstudiantesPosgrado = i.next();
			if (movilidadEstudiantesPosgrado.getId()
					.equals(Long.valueOf(movilidadVistaSeleccionada.getIdSolicitud()))) {
				movilidadEstudiantesModificacion = movilidadEstudiantesPosgrado;
				break;
			}
		}
		mostrarRequisitosEstudiantes(movilidadEstudiantesModificacion.getId());
		return "";
	}

	/**
	 * Validar si archivo requisitos.
	 *
	 * @param idConvocatoria
	 *            the id convocatoria
	 * @return true, if successful
	 */
	public boolean validarSiArchivoRequisitos(Long idConvocatoria) {

		String sqlBuscaConvocatoria = "select #id e.id, #criteriosCalificacionConv e.criteriosCalificacionConv from Convocatoria e where e.id = "
				+ idConvocatoria + "";

		List<Convocatoria> listConvs = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class, sqlBuscaConvocatoria);
		if (!esListaVacia(listConvs)) {
			Convocatoria convActual = listConvs.get(0);
			return !esCadenaVacia(convActual.getCriteriosCalificacionConv())
					&& !convActual.getCriteriosCalificacionConv().equals("CONFIGURADO_SISTEMA");
		} else {
			return false;
		}
	}

	/**
	 * Guardar revision movilidad visitante.
	 *
	 * @param aceptacion
	 *            the aceptacion
	 * @param aprobacion
	 *            the aprobacion
	 * @param numeroPlantilla
	 *            the numero plantilla
	 * @return the string
	 */
	public String guardarRevisionMovilidadVisitante(String aceptacion, String aprobacion, int numeroPlantilla) {
		movilidadVisitanteModificacion.setError("");
		if (movilidadVisitanteSeleccionada.getComentariosFac().length() == 0) {
			errorString = "Debe ingresar los comentarios de la facultad";
			movilidadVisitanteSeleccionada.setError(errorString);
			return "";
		}
		List<MovilidadVisitanteExterior> listaMovilidad = servicioGeneral.obtenerObjetos(
				MovilidadVisitanteExterior.class,
				"from MovilidadVisitanteExterior where id ='" + movilidadVisitanteSeleccionada.getId() + "'");
		if (listaMovilidad != null) {
			MovilidadVisitanteExterior mov = (MovilidadVisitanteExterior) listaMovilidad.get(0);
			if (personaActual != null) {
				mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
				mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
			}
			if (aprobacion != null) {
				mov.setAprobacion(aprobacion);
			}
			mov.setAceptacion(aceptacion);
			mov.setValorAprobadoFacultad(movilidadVisitanteSeleccionada.getValorAprobadoFacultad());
			mov.setComentariosFac(movilidadVisitanteSeleccionada.getComentariosFac());
			mov.setValorAprobadoFacultad(movilidadVisitanteSeleccionada.getValorAprobadoFacultad());
			mov.setDispPresupuestal(movilidadVisitanteSeleccionada.getDispPresupuestal());
			mov.setCumpleRequisitos(movilidadVisitanteSeleccionada.getCumpleRequisitos());
			mov.setFechaAceptacion(new Date());
			mov.setAnioVigenciaAprFacultad(movilidadVisitanteSeleccionada.getAnioVigenciaAprFacultad());

			boolean valReq = validarSiArchivoRequisitos(mov.getConvocatoria().getId());
			if (!valReq || (valReq && mov.getArchivosRevisionRequisitos() != null
					&& mov.getArchivosRevisionRequisitos().size() > 0)) {

				servicioGeneral.guardarObjeto(mov);

				correoActual = cargarPlantilla(numeroPlantilla);
				editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(),
						mov.getComentariosFac());
				String dirCorreo = mov.getPersonaInv().getEmail();

				crearHistoricoEstadoMovilidadInvestigador(mov, movilidadVisitanteSeleccionada.getComentariosFac());
				String tipoMovilidad = TipoMovilidad.HER_MOVILIDAD_VISITANTES_EXT;
				boolean noAprobar = false;
				if (aprobacion != null && aprobacion.equals("S")) {
					noAprobar = false;
				} else {
					noAprobar = true;
				}
				return finalizacionyEnvio(dirCorreo, tipoMovilidad, noAprobar);
			} else {
				String error = "Debe adjuntar un archivo de revisión de requisitos";
				movilidadVisitanteSeleccionada.setError(error);
				return "";
			}
		}
		return "";
	}

	/**
	 * Aprobar movilidad eventos.
	 *
	 * @return the string
	 */
	public String aprobarMovilidadEventos() {
		if (movilidadEventosSeleccionada.getAnioVigenciaAprFacultad() < Long.valueOf(getMinYear())) {
			movilidadEventosSeleccionada.setError("El año de vigencia definido no es válido.");
			return "";
		}
		String sqlBuscaConvocatoria = "select #id e.id, #montoApoyoGanadores e.montoApoyoGanadores, #tiempoEjecucionProyecto e.tiempoEjecucionProyecto, #validacionCostosConvocatoriaMovilidad e.validacionCostosConvocatoriaMovilidad from Convocatoria e where e.id = "
				+ movilidadEventosSeleccionada.getConvocatoria().getId() + "";

		List<Convocatoria> listConvs = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class, sqlBuscaConvocatoria);
		Convocatoria convActual = listConvs.get(0);
		if (convActual.getMontoApoyoGanadores() != null) {
			restriccionTiquetes = Long.parseLong(convActual.getMontoApoyoGanadores());
		}
		if (movilidadEventosSeleccionada.getSubModalidadConvocatoria() != null) {
			if (convActual.getValidacionCostosConvocatoriaMovilidad() != null) {
				String[] valMov = convActual.getValidacionCostosConvocatoriaMovilidad().split(",");
				for (int i = 0; i < valMov.length; i++) {
					String[] valSubmov = valMov[i].split("=");
					if (valSubmov[0].equals(movilidadEventosSeleccionada.getSubModalidadConvocatoria())) {
						restriccionTiquetes = Long.parseLong(valSubmov[1]);
					}
				}
			}
		}
		if (movilidadEventosSeleccionada.getValorAprobadoFacultad() == null && movilidadEventosSeleccionada.getDispPresupuestal().equals("SI")) {
			movilidadEventosSeleccionada
					.setError("Por favor, indicar el valor de aporte por la facultad.");
			return "";
		}
		if (movilidadEventosSeleccionada.getDispPresupuestal().equals("SI") && movilidadEventosSeleccionada.getValorAprobadoFacultad() > restriccionTiquetes) {
			movilidadEventosSeleccionada
					.setError("El valor máximo que cubre la convocatoria es de $" + restriccionTiquetes + ".");
			return "";
		}
		/*if (movilidadEventosSeleccionada.getValorAprobadoFacultad() != null) {
			boolean error = false;
			if (movilidadEventosSeleccionada.getValorAprobadoFacultad() == 0) {
				error = true;
			}
			if (error) {
				errorString = MENSAJE_ERROR_VALOR_APROBADO;
				movilidadEventosSeleccionada.setError(errorString);
				return "";
			}
		} else {
			errorString = MENSAJE_ERROR_VALOR_APROBADO;
			movilidadEventosSeleccionada.setError(errorString);
			return "";
		}*/

		verificarRequisitos(listaRequisitoAux);
		if (!cumpleRequisitos) {
			errorString = "Hay un requisito que no se cumple o un criterio no aprobado.";
			movilidadEventosSeleccionada.setError(errorString);
			return "";
		}
		if (!conceptosIngresado) {
			errorString = "Debe ingresar el concepto para todos los criterios de evaluación.";
			movilidadEventosSeleccionada.setError(errorString);
			return "";
		}

		guardarRequisitosDocentes();

		if (movilidadEventosSeleccionada.getMovilidadConvocatoriaFacultad() != null
				&& movilidadEventosSeleccionada.getMovilidadConvocatoriaFacultad().equals("S")) {
			return guardarMovilidadEvento("SI", "SI", 66);
		} else {
			return guardarMovilidadEvento("SI", null, 66);
		}

	}

	/**
	 * No aprobar movilidad evento.
	 *
	 * @return the string
	 */
	public String noAprobarMovilidadEvento() {

		movilidadEventosSeleccionada.setError("");

		if (movilidadEventosSeleccionada.getDispPresupuestal().equals("SI")) {
			verificarRequisitos(listaRequisitoAux);
			if (!conceptosIngresado && movilidadEventosSeleccionada.getCumpleRequisitos().equals("SI")) {
				errorString = "Debe ingresar el concepto para todos los criterios de evaluación.";
				movilidadEventosSeleccionada.setError(errorString);
				return "";
			}
			guardarRequisitosDocentes();
		}

		if (movilidadEventosSeleccionada.getMovilidadConvocatoriaFacultad() != null
				&& movilidadEventosSeleccionada.getMovilidadConvocatoriaFacultad().equals("S")) {
			return guardarMovilidadEvento("NO", "NO", 68);
		} else {
			return guardarMovilidadEvento("NO", null, 68);
		}
	}

	/**
	 * Guardar modificacion movilidad visitante.
	 */
	public void guardarModificacionMovilidadVisitante() {
		servicioGeneral.guardarObjeto(movilidadVisitanteModificacion);
	}

	/**
	 * Guardar modificacion movilidad docentes.
	 */
	public void guardarModificacionMovilidadDocentes() {
		servicioGeneral.guardarObjeto(movilidadEventosModificacion);
	}

	/**
	 * Guardar modificacion movilidad estudiantes.
	 */
	public void guardarModificacionMovilidadEstudiantes() {
		servicioGeneral.guardarObjeto(movilidadEstudiantesModificacion);
	}

	/**
	 * Guardar movilidad evento.
	 *
	 * @param aceptacion
	 *            the aceptacion
	 * @param aprobacion
	 *            the aprobacion
	 * @param numeroPlantilla
	 *            the numero plantilla
	 * @return the string
	 */
	public String guardarMovilidadEvento(String aceptacion, String aprobacion, int numeroPlantilla) {
		if (movilidadEventosSeleccionada.getComentariosFac().length() == 0) {
			errorString = "Debe ingresar los comentarios de la facultad";
			movilidadEventosSeleccionada.setError(errorString);
			return "";
		}
		List<MovilidadDocentesExterior> listaMovilidad = servicioGeneral.obtenerObjetos(MovilidadDocentesExterior.class,
				"from MovilidadDocentesExterior where id ='" + movilidadEventosSeleccionada.getId() + "'");
		if (listaMovilidad != null) {

			MovilidadDocentesExterior mov = (MovilidadDocentesExterior) listaMovilidad.get(0);
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
			mov.setAceptacion(aceptacion);
			if (aprobacion != null) {
				mov.setAprobacion(aprobacion);
			}
			mov.setValorAprobadoFacultad(movilidadEventosSeleccionada.getValorAprobadoFacultad());
			mov.setComentariosFac(movilidadEventosSeleccionada.getComentariosFac());
			mov.setValorAprobadoFacultad(movilidadEventosSeleccionada.getValorAprobadoFacultad());
			mov.setCumpleRequisitos(movilidadEventosSeleccionada.getCumpleRequisitos());
			mov.setDispPresupuestal(movilidadEventosSeleccionada.getDispPresupuestal());
			mov.setFechaAceptacion(new Date());
			mov.setAnioVigenciaAprFacultad(movilidadEventosSeleccionada.getAnioVigenciaAprFacultad());

			boolean valReq = validarSiArchivoRequisitos(mov.getConvocatoria().getId());

			if (!valReq || (valReq && mov.getArchivosRevisionRequisitos() != null
					&& mov.getArchivosRevisionRequisitos().size() > 0)) {

				servicioGeneral.guardarObjeto(mov);

				correoActual = cargarPlantilla(numeroPlantilla);
				editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(),
						mov.getComentariosFac());
				String dirCorreo = mov.getPersonaInv().getEmail();

				crearHistoricoEstadoMovilidadInvestigador(mov, movilidadEventosSeleccionada.getComentariosFac());
				String tipoMovilidad = TipoMovilidad.HER_MOVILIDAD_DOCENTES_EVENTOS;
				boolean noAprobar = false;
				if (aprobacion != null && aprobacion.equals("S")) {
					noAprobar = false;
				} else {
					noAprobar = true;
				}
				return finalizacionyEnvio(dirCorreo, tipoMovilidad, noAprobar);
			} else {
				String error = "Debe adjuntar un archivo de revisión de requisitos";
				movilidadEventosSeleccionada.setError(error);
				return "";
			}
		}
		return "";
	}

	/**
	 * Aprobar movilidad posgrado.
	 *
	 * @return the string
	 */
	public String aprobarMovilidadPosgrado() {
		if (movilidadEstudiantesSeleccionada.getAnioVigenciaAprFacultad() < Long.valueOf(getMinYear())) {
			movilidadEstudiantesSeleccionada.setError("El año de vigencia definido no es válido.");
			return "";
		}
		String sqlBuscaConvocatoria = "select #id e.id, #montoApoyoGanadores e.montoApoyoGanadores, #tiempoEjecucionProyecto e.tiempoEjecucionProyecto from Convocatoria e where e.id = "
				+ movilidadEstudiantesSeleccionada.getConvocatoria().getId() + "";

		List<Convocatoria> listConvs = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class, sqlBuscaConvocatoria);
		Convocatoria convActual = listConvs.get(0);
		if (convActual.getMontoApoyoGanadores() != null) {
			restriccionTiquetes = Long.parseLong(convActual.getMontoApoyoGanadores());
		}
		if (movilidadEstudiantesSeleccionada.getValorAprobadoFacultad() == null && movilidadEstudiantesSeleccionada.getDispPresupuestal().equals("SI")) {
			movilidadEstudiantesSeleccionada
					.setError("Por favor, indicar el valor de aporte por la facultad.");
			return "";
		}
		if (movilidadEstudiantesSeleccionada.getDispPresupuestal().equals("SI") && movilidadEstudiantesSeleccionada.getValorAprobadoFacultad() > restriccionTiquetes) {
			movilidadEstudiantesSeleccionada
					.setError("El valor máximo que cubre la convocatoria es de $" + restriccionTiquetes + ".");
			return "";
		}
		/*if (movilidadEstudiantesSeleccionada.getValorAprobadoFacultad() != null) {
			boolean error = false;
			if (movilidadEstudiantesSeleccionada.getValorAprobadoFacultad() == 0) {
				error = true;
			}
			if (error) {
				errorString = MENSAJE_ERROR_VALOR_APROBADO;
				movilidadEstudiantesSeleccionada.setError(errorString);
				return "";
			}
		} else {
			errorString = MENSAJE_ERROR_VALOR_APROBADO;
			return "";
		}*/

		verificarRequisitos(listaRequisitoAux);
		if (!cumpleRequisitos) {
			errorString = "Hay un requisito que no se cumple.";
			movilidadEstudiantesSeleccionada.setError(errorString);
			return "";
		}
		if (!conceptosIngresado) {
			errorString = "Debe ingresar el concepto para todos los criterios de evaluación.";
			movilidadEstudiantesSeleccionada.setError(errorString);
			return "";
		}

		guardarRequisitosEstudiantes();

		if (movilidadEstudiantesSeleccionada.getMovilidadConvocatoriaFacultad() != null
				&& movilidadEstudiantesSeleccionada.getMovilidadConvocatoriaFacultad().equals("S")) {
			return guardarMovilidadPosgrado("SI", "SI", 66);
		} else {

			return guardarMovilidadPosgrado("SI", null, 66);
		}
	}

	/**
	 * Guardar movilidad posgrado.
	 *
	 * @param aceptacion
	 *            the aceptacion
	 * @param aprobacion
	 *            the aprobacion
	 * @param numeroPlantilla
	 *            the numero plantilla
	 * @return the string
	 */
	public String guardarMovilidadPosgrado(String aceptacion, String aprobacion, int numeroPlantilla) {
		if (movilidadEstudiantesSeleccionada.getComentariosFac().length() == 0) {
			errorString = "Debe ingresar los comentarios de la facultad";
			movilidadEstudiantesSeleccionada.setError(errorString);
			return "";
		}
		List<MovilidadEstudiantesPosgrado> listaMovilidad = servicioGeneral.obtenerObjetos(
				MovilidadEstudiantesPosgrado.class,
				"from MovilidadEstudiantesPosgrado where id ='" + movilidadEstudiantesSeleccionada.getId() + "'");
		if (listaMovilidad != null) {
			MovilidadEstudiantesPosgrado mov = (MovilidadEstudiantesPosgrado) listaMovilidad.get(0);

			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
			mov.setAceptacion(aceptacion);
			if (aprobacion != null) {
				mov.setAprobacion(aprobacion);
			}
			mov.setValorAprobadoFacultad(movilidadEstudiantesSeleccionada.getValorAprobadoFacultad());
			mov.setComentariosFac(movilidadEstudiantesSeleccionada.getComentariosFac());
			mov.setValorAprobadoFacultad(movilidadEstudiantesSeleccionada.getValorAprobadoFacultad());
			mov.setCumpleRequisitos(movilidadEstudiantesSeleccionada.getCumpleRequisitos());
			mov.setDispPresupuestal(movilidadEstudiantesSeleccionada.getDispPresupuestal());
			mov.setFechaAceptacion(new Date());
			mov.setAnioVigenciaAprFacultad(movilidadEstudiantesSeleccionada.getAnioVigenciaAprFacultad());

			boolean valReq = validarSiArchivoRequisitos(mov.getConvocatoria().getId());
			if (!valReq || (valReq && mov.getArchivosRevisionRequisitos() != null
					&& mov.getArchivosRevisionRequisitos().size() > 0)) {

				servicioGeneral.guardarObjeto(mov);

				correoActual = cargarPlantilla(numeroPlantilla);
				editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(),
						mov.getComentariosFac());
				String dirCorreo = mov.getPersonaInv().getEmail();

				crearHistoricoEstadoMovilidadInvestigador(mov, movilidadEstudiantesSeleccionada.getComentariosFac());
				String tipoMovilidad = TipoMovilidad.HER_MOVILIDAD_ESTUDIANTE_POS;
				boolean noAprobar = false;
				if (aprobacion != null && aprobacion.equals("S")) {
					noAprobar = false;
				} else {
					noAprobar = true;
				}
				return finalizacionyEnvio(dirCorreo, tipoMovilidad, noAprobar);
			} else {
				String error = "Debe adjuntar un archivo de revisión de requisitos";
				movilidadEstudiantesSeleccionada.setError(error);
				return "";
			}
		}
		return "";
	}

	/**
	 * No aprobar movilidad posgrado.
	 *
	 * @return the string
	 */
	public String noAprobarMovilidadPosgrado() {

		if (movilidadEstudiantesSeleccionada.getDispPresupuestal().equals("SI")) {
			guardarRequisitosEstudiantes();

			verificarRequisitos(listaRequisitoAux);
			if (!conceptosIngresado && StringUtils.isNotBlank(movilidadEstudiantesSeleccionada.getCumpleRequisitos())
					&& movilidadEstudiantesSeleccionada.getCumpleRequisitos().equals("SI")) {
				errorString = "Debe ingresar el concepto para todos los criterios de evaluación.";
				movilidadEstudiantesSeleccionada.setError(errorString);
				return "";
			}
		}

		if (movilidadEstudiantesSeleccionada.getMovilidadConvocatoriaFacultad() != null
				&& movilidadEstudiantesSeleccionada.getMovilidadConvocatoriaFacultad().equals("S")) {
			return guardarMovilidadPosgrado("NO", "NO", 68);
		} else {
			return guardarMovilidadPosgrado("NO", null, 68);
		}
	}

	/**
	 * Aprobar movilidad estudiantes artes.
	 *
	 * @return the string
	 */
	public String aprobarMovilidadEstudiantesArtes() {
		MovilidadEstudiantesArtes mov;
		List<MovilidadEstudiantesArtes> listaMovilidad = servicioGeneral.obtenerObjetos(MovilidadEstudiantesArtes.class,
				"from MovilidadEstudiantesArtes where id ='" + movilidadEstudiantesArtesSeleccionada.getId() + "'");
		if (listaMovilidad != null) {
			mov = (MovilidadEstudiantesArtes) listaMovilidad.get(0);
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
			mov.setAceptacionFacultad("SI");
			mov.setDescripcionFacultad(movilidadEstudiantesArtesSeleccionada.getDescripcionFacultad());
			servicioGeneral.guardarObjeto(mov);
			guardarRequisitosEstudiantesArtes();
			correoActual = cargarPlantilla(66);
			editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(),
					mov.getDescripcionFacultad());
			String dirCorreo = mov.getPersonaInv().getEmail();
			String tipoMovilidad = TipoMovilidad.HER_MOVILIDAD_ESTUDIANTES_ART;
			boolean noAprobar = false;
			return finalizacionyEnvio(dirCorreo, tipoMovilidad, noAprobar);
		}
		return "";
	}

	/**
	 * No aprobar movilidad estudiantes artes.
	 *
	 * @return the string
	 */
	public String noAprobarMovilidadEstudiantesArtes() {
		MovilidadEstudiantesArtes mov;
		List<MovilidadEstudiantesArtes> listaMovilidad = servicioGeneral.obtenerObjetos(MovilidadEstudiantesArtes.class,
				"from MovilidadEstudiantesArtes where id ='" + movilidadEstudiantesArtesSeleccionada.getId() + "'");
		if (listaMovilidad != null) {
			mov = (MovilidadEstudiantesArtes) listaMovilidad.get(0);
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
			mov.setAceptacionFacultad("NO");
			mov.setDescripcionFacultad(movilidadEstudiantesArtesSeleccionada.getDescripcionFacultad());
			servicioGeneral.guardarObjeto(mov);
			guardarRequisitosEstudiantesArtes();
			correoActual = cargarPlantilla(68);
			editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(),
					mov.getDescripcionFacultad());
			String dirCorreo = mov.getPersonaInv().getEmail();
			String tipoMovilidad = TipoMovilidad.HER_MOVILIDAD_ESTUDIANTES_ART;
			boolean noAprobar = true;
			return finalizacionyEnvio(dirCorreo, tipoMovilidad, noAprobar);
		}
		return "";
	}

	/**
	 * No aprobar movilidad docente artes.
	 *
	 * @return the string
	 */
	public String noAprobarMovilidadDocenteArtes() {
		MovilidadDocentesArtes mov;
		List<MovilidadDocentesArtes> listaMovilidad = servicioGeneral.obtenerObjetos(MovilidadDocentesArtes.class,
				"from " + "MovilidadDocentesArtes where id ='" + movilidadDocenteArtesSeleccionada.getId() + "'");
		if (listaMovilidad != null) {
			mov = (MovilidadDocentesArtes) listaMovilidad.get(0);
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
			mov.setAceptacionFacultad("NO");
			mov.setDescripcionFacultad(movilidadDocenteArtesSeleccionada.getDescripcionFacultad());
			servicioGeneral.guardarObjeto(mov);
			guardarRequisitosDocentesArtes();
			correoActual = cargarPlantilla(68);
			editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(),
					mov.getDescripcionFacultad());
			String dirCorreo = mov.getPersonaInv().getEmail();
			String tipoMovilidad = TipoMovilidad.HER_MOVILIDAD_DOCENTES_ART;
			boolean noAprobar = true;
			return finalizacionyEnvio(dirCorreo, tipoMovilidad, noAprobar);
		}
		return "";
	}

	/**
	 * Aprobar movilidad docente artes.
	 *
	 * @return the string
	 */
	public String aprobarMovilidadDocenteArtes() {
		MovilidadDocentesArtes mov;
		List<MovilidadDocentesArtes> listaMovilidad = servicioGeneral.obtenerObjetos(MovilidadDocentesArtes.class,
				"from MovilidadDocentesArtes where id ='" + movilidadDocenteArtesSeleccionada.getId() + "'");
		if (listaMovilidad != null) {
			mov = (MovilidadDocentesArtes) listaMovilidad.get(0);
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
			mov.setAceptacionFacultad("SI");
			mov.setDescripcionFacultad(movilidadDocenteArtesSeleccionada.getDescripcionFacultad());
			servicioGeneral.guardarObjeto(mov);
			guardarRequisitosDocentesArtes();
			correoActual = cargarPlantilla(66);
			editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(),
					mov.getDescripcionFacultad());
			String dirCorreo = mov.getPersonaInv().getEmail();
			String tipoMovilidad = TipoMovilidad.HER_MOVILIDAD_DOCENTES_ART;
			boolean noAprobar = false;
			return finalizacionyEnvio(dirCorreo, tipoMovilidad, noAprobar);
		}
		return "";
	}

	/**
	 * Finalizaciony envio.
	 *
	 * @param dirCorreo
	 *            the dir correo
	 * @return the string
	 */
	private String finalizacionyEnvio(String dirCorreo, String tipoMovilidad, boolean noAprobar) {
		String dirCorreoConfirmacion = personaActual.getEmail();
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		correo.adicionarDireccion(dirCorreo);
		String correoEstudiante;
		if (tipoMovilidad.equals(TipoMovilidad.HER_MOVILIDAD_ESTUDIANTE_POS)) {
			correoEstudiante = movilidadEstudiantesSeleccionada.getEstudianteInv().getEmail();
			if (!correoEstudiante.contains("@unal.edu.co")) {
				correoEstudiante += "@unal.edu.co";
			}
			correo.adicionarDireccion(correoEstudiante);
		}
		if (tipoMovilidad.equals(TipoMovilidad.HER_MOVILIDAD_ESTUDIANTES_ART)) {
			correoEstudiante = movilidadEstudiantesArtesSeleccionada.getEstudianteInv().getEmail();
			if (!correoEstudiante.contains("@unal.edu.co")) {
				correoEstudiante += "@unal.edu.co";
			}
			correo.adicionarDireccion(correoEstudiante);
		}
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);
		cargarListasMovilidades();
		sesion.removeAttribute("manejadorAprobacionMovilidad");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacion";
	}

	/**
	 * Mostrar requisitos visitante ext.
	 *
	 * @param idMovilidadRequisitos
	 *            the id movilidad requisitos
	 */
	public void mostrarRequisitosVisitanteExt(Long idMovilidadRequisitos) {
		MovilidadVisitanteExterior movActual = servicioMovilidad
				.obtenerMovilidadesVisExtRequisitos(idMovilidadRequisitos);
		adicionarRequisitos(movActual.getId(), movActual.getConvocatoria().getId());
		MovilidadVisitanteExterior mov = new MovilidadVisitanteExterior();
		mov.setId(idMovilidadRequisitos);

		listaRequisitoAux = new ArrayList<MovilidadRequisito>();
		if (mov.getRequisitosMovilidad() == null || mov.getRequisitosMovilidad().size() == 0) {
			movActual = servicioMovilidad.obtenerMovilidadesVisExtRequisitos(idMovilidadRequisitos);
		}
		for (Iterator<MovilidadRequisito> it = movActual.getRequisitosMovilidad().iterator(); it.hasNext();) {
			MovilidadRequisito pPA = new MovilidadRequisito();
			MovilidadRequisito requisitoMovilidad = (MovilidadRequisito) it.next();
			TipoRequisito tr = new TipoRequisito();
			tr.setId(requisitoMovilidad.getRequisito().getId());
			pPA.setRequisito(tr);
			listaRequisitoAux.add(requisitoMovilidad);

		}
	}

	/**
	 * Mostrar requisitos estudiantes.
	 *
	 * @param idMovilidadRequisitos
	 *            the id movilidad requisitos
	 */
	public void mostrarRequisitosEstudiantes(Long idMovilidadRequisitos) {
		MovilidadEstudiantesPosgrado movActual = servicioMovilidad
				.obtenerMovilidadesEstudiantesRequisitos(idMovilidadRequisitos);
		adicionarRequisitos(movActual.getId(), movActual.getConvocatoria().getId());
		MovilidadEstudiantesPosgrado mov = new MovilidadEstudiantesPosgrado();
		mov.setId(idMovilidadRequisitos);

		listaRequisitoAux = new ArrayList<MovilidadRequisito>();
		if (mov.getRequisitosMovilidad() == null || mov.getRequisitosMovilidad().size() == 0) {
			movActual = servicioMovilidad.obtenerMovilidadesEstudiantesRequisitos(idMovilidadRequisitos);
		}
		for (Iterator<MovilidadRequisito> it = movActual.getRequisitosMovilidad().iterator(); it.hasNext();) {
			MovilidadRequisito pPA = new MovilidadRequisito();
			MovilidadRequisito requisitoMovilidad = (MovilidadRequisito) it.next();
			TipoRequisito tr = new TipoRequisito();
			tr.setId(requisitoMovilidad.getRequisito().getId());
			pPA.setRequisito(tr);
			listaRequisitoAux.add(requisitoMovilidad);

		}
	}

	/**
	 * Mostrar requisitos docentes.
	 *
	 * @param idMovilidadRequisitos
	 *            the id movilidad requisitos
	 */
	public void mostrarRequisitosDocentes(Long idMovilidadRequisitos) {
		MovilidadDocentesExterior movActual = servicioMovilidad
				.obtenerMovilidadesDocentesRequisitos(idMovilidadRequisitos);
		adicionarRequisitos(movActual.getId(), movActual.getConvocatoria().getId());
		MovilidadDocentesExterior mov = new MovilidadDocentesExterior();
		mov.setId(idMovilidadRequisitos);

		listaRequisitoAux = new ArrayList<MovilidadRequisito>();
		if (mov.getRequisitosMovilidad() == null || mov.getRequisitosMovilidad().size() == 0) {
			movActual = servicioMovilidad.obtenerMovilidadesDocentesRequisitos(idMovilidadRequisitos);
		}
		for (Iterator<MovilidadRequisito> it = movActual.getRequisitosMovilidad().iterator(); it.hasNext();) {
			MovilidadRequisito pPA = new MovilidadRequisito();
			MovilidadRequisito requisitoMovilidad = (MovilidadRequisito) it.next();
			TipoRequisito tr = new TipoRequisito();
			tr.setId(requisitoMovilidad.getRequisito().getId());
			pPA.setRequisito(tr);
			listaRequisitoAux.add(requisitoMovilidad);

		}
	}

	/**
	 * Mostrar requisitos estudiante artes.
	 *
	 * @param idMovilidadRequisitos
	 *            the id movilidad requisitos
	 */
	public void mostrarRequisitosEstudianteArtes(Long idMovilidadRequisitos) {
		MovilidadEstudiantesArtes movActual = servicioMovilidad
				.obtenerMovilidadesEstudiantesArtesRequisitos(idMovilidadRequisitos);
		adicionarRequisitos(movActual.getId(), movActual.getConvocatoria().getId());
		MovilidadEstudiantesArtes mov = new MovilidadEstudiantesArtes();
		mov.setId(idMovilidadRequisitos);

		listaRequisitoAux = new ArrayList<MovilidadRequisito>();
		if (mov.getRequisitosMovilidad() == null || mov.getRequisitosMovilidad().size() == 0) {
			movActual = servicioMovilidad.obtenerMovilidadesEstudiantesArtesRequisitos(idMovilidadRequisitos);
		}
		for (Iterator<MovilidadRequisito> it = movActual.getRequisitosMovilidad().iterator(); it.hasNext();) {
			MovilidadRequisito pPA = new MovilidadRequisito();
			MovilidadRequisito requisitoMovilidad = (MovilidadRequisito) it.next();
			TipoRequisito tr = new TipoRequisito();
			tr.setId(requisitoMovilidad.getRequisito().getId());
			pPA.setRequisito(tr);
			listaRequisitoAux.add(requisitoMovilidad);

		}
	}

	/**
	 * Mostrar requisitos docentes artes.
	 *
	 * @param idMovilidadRequisitos
	 *            the id movilidad requisitos
	 */
	public void mostrarRequisitosDocentesArtes(Long idMovilidadRequisitos) {
		MovilidadDocentesArtes movActual = servicioMovilidad
				.obtenerMovilidadesDocentesArtesRequisitos(idMovilidadRequisitos);
		adicionarRequisitos(movActual.getId(), movActual.getConvocatoria().getId());
		MovilidadDocentesArtes mov = new MovilidadDocentesArtes();
		mov.setId(idMovilidadRequisitos);

		listaRequisitoAux = new ArrayList<MovilidadRequisito>();
		if (mov.getRequisitosMovilidad() == null || mov.getRequisitosMovilidad().size() == 0) {
			movActual = servicioMovilidad.obtenerMovilidadesDocentesArtesRequisitos(idMovilidadRequisitos);
		}
		for (Iterator<MovilidadRequisito> it = movActual.getRequisitosMovilidad().iterator(); it.hasNext();) {
			MovilidadRequisito pPA = new MovilidadRequisito();
			MovilidadRequisito requisitoMovilidad = (MovilidadRequisito) it.next();
			TipoRequisito tr = new TipoRequisito();
			tr.setId(requisitoMovilidad.getRequisito().getId());
			pPA.setRequisito(tr);
			listaRequisitoAux.add(requisitoMovilidad);

		}
	}

	/**
	 * Adicionar requisitos.
	 *
	 * @param idMovilidad
	 *            the id movilidad
	 * @param idConvocatoria
	 *            the id convocatoria
	 */
	public void adicionarRequisitos(Long idMovilidad, Long idConvocatoria) {
		try {
			List<MovilidadRequisito> listaRequisitos = servicioGeneral.obtenerObjetos(MovilidadRequisito.class,
					"from MovilidadRequisito p where p.idMovilidad = " + idMovilidad);
			if (esListaVacia(listaRequisitos)) {
				Convocatoria convocatoriaActual;
				convocatoriaActual = servicioModalidad.obtenerConvocatoriaRequisitos(idConvocatoria);

				for (Iterator<TipoRequisito> it = convocatoriaActual.getRequisitos().iterator(); it.hasNext();) {
					TipoRequisito tipoRequisito = (TipoRequisito) it.next();
					List<TipoRequisito> listaHijos = servicioGeneral.obtenerListaRequisitoHijo(tipoRequisito.getId());

					if (!esListaVacia(listaHijos)) {
						for (int j = 0; j < listaHijos.size(); j++) {
							MovilidadRequisito requisitoMovilidad = new MovilidadRequisito();
							TipoRequisito hijo = (TipoRequisito) listaHijos.get(j);
							requisitoMovilidad.setRequisito(hijo);
							requisitoMovilidad.setIdMovilidad(idMovilidad);
							requisitoMovilidad.setCumplido("S");
							servicioGeneral.guardarObjeto(requisitoMovilidad);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Problemas Registrando Requisitos");
			e.printStackTrace();
		}
	}

	/**
	 * Activar aprobar.
	 */
	private void verificarRequisitos(List<MovilidadRequisito> listaRequisitos) {
		cumpleRequisitos = true;
		conceptosIngresado = true;
		for (Object object : listaRequisitos) {
			MovilidadRequisito movilidadRequisito = (MovilidadRequisito) object;
			boolean valor = movilidadRequisito.isCumplidoCheckbox();
			if (cumpleRequisitos && !valor) {
				cumpleRequisitos = false;
			}
			if (conceptosIngresado && !esCadenaVacia(movilidadRequisito.getRequisito().getSeleccion())
					&& "S".equals(movilidadRequisito.getRequisito().getSeleccion())
					&& esCadenaVacia(movilidadRequisito.getComentario())) {
				conceptosIngresado = false;
			}
		}
	}

	/**
	 * Guardar requisitos visitante ext.
	 */
	private void guardarRequisitosVisitanteExt() {
		MovilidadVisitanteExterior mov = servicioMovilidad
				.obtenerMovilidadesVisExtRequisitos(movilidadVisitanteSeleccionada.getId());
		mov.setRequisitosMovilidad(new HashSet<MovilidadRequisito>(listaRequisitoAux));
		for (Iterator<MovilidadRequisito> it = mov.getRequisitosMovilidad().iterator(); it.hasNext();) {
			MovilidadRequisito requisitoMovilidad = (MovilidadRequisito) it.next();
			requisitoMovilidad.setResponsable_id(personaActual.getId().getDocumento());
			requisitoMovilidad.setResponsable_tdo_id(personaActual.getId().getTipoDocumento());
			servicioGeneral.guardarObjeto(requisitoMovilidad);
		}
	}

	/**
	 * Guardar requisitos estudiantes.
	 */
	private void guardarRequisitosEstudiantes() {
		MovilidadEstudiantesPosgrado mov = servicioMovilidad
				.obtenerMovilidadesEstudiantesRequisitos(movilidadEstudiantesSeleccionada.getId());
		mov.setRequisitosMovilidad(new HashSet<MovilidadRequisito>(listaRequisitoAux));
		for (Iterator<MovilidadRequisito> it = mov.getRequisitosMovilidad().iterator(); it.hasNext();) {
			MovilidadRequisito requisitoMovilidad = (MovilidadRequisito) it.next();
			requisitoMovilidad.setResponsable_id(personaActual.getId().getDocumento());
			requisitoMovilidad.setResponsable_tdo_id(personaActual.getId().getTipoDocumento());
			servicioGeneral.guardarObjeto(requisitoMovilidad);
		}
	}

	/**
	 * Guardar requisitos docentes.
	 */
	private void guardarRequisitosDocentes() {
		MovilidadDocentesExterior mov = servicioMovilidad
				.obtenerMovilidadesDocentesRequisitos(movilidadEventosSeleccionada.getId());
		mov.setRequisitosMovilidad(new HashSet<MovilidadRequisito>(listaRequisitoAux));
		for (Iterator<MovilidadRequisito> it = mov.getRequisitosMovilidad().iterator(); it.hasNext();) {
			MovilidadRequisito requisitoMovilidad = (MovilidadRequisito) it.next();
			requisitoMovilidad.setResponsable_id(personaActual.getId().getDocumento());
			requisitoMovilidad.setResponsable_tdo_id(personaActual.getId().getTipoDocumento());
			servicioGeneral.guardarObjeto(requisitoMovilidad);
		}
	}

	/**
	 * Guardar requisitos estudiantes artes.
	 */
	private void guardarRequisitosEstudiantesArtes() {
		MovilidadEstudiantesArtes mov = servicioMovilidad
				.obtenerMovilidadesEstudiantesArtesRequisitos(movilidadEstudiantesArtesSeleccionada.getId());
		mov.setRequisitosMovilidad(new HashSet<MovilidadRequisito>(listaRequisitoAux));
		for (Iterator<MovilidadRequisito> it = mov.getRequisitosMovilidad().iterator(); it.hasNext();) {
			MovilidadRequisito requisitoMovilidad = (MovilidadRequisito) it.next();
			requisitoMovilidad.setResponsable_id(personaActual.getId().getDocumento());
			requisitoMovilidad.setResponsable_tdo_id(personaActual.getId().getTipoDocumento());
			servicioGeneral.guardarObjeto(requisitoMovilidad);
		}
	}

	/**
	 * Guardar requisitos docentes artes.
	 */
	private void guardarRequisitosDocentesArtes() {
		MovilidadDocentesArtes mov = servicioMovilidad
				.obtenerMovilidadesDocentesArtesRequisitos(movilidadDocenteArtesSeleccionada.getId());
		mov.setRequisitosMovilidad(new HashSet<MovilidadRequisito>(listaRequisitoAux));
		for (Iterator<MovilidadRequisito> it = mov.getRequisitosMovilidad().iterator(); it.hasNext();) {
			MovilidadRequisito requisitoMovilidad = (MovilidadRequisito) it.next();
			requisitoMovilidad.setResponsable_id(personaActual.getId().getDocumento());
			requisitoMovilidad.setResponsable_tdo_id(personaActual.getId().getTipoDocumento());
			servicioGeneral.guardarObjeto(requisitoMovilidad);
		}
	}

	/**
	 * Reporte movilidad.
	 *
	 * @return the string
	 */
	public String reporteMovilidad() {
		return Navegacion.REPORTE;
	}

	/**
	 * Gets the lista requisito aux.
	 *
	 * @return the lista requisito aux
	 */
	public List<MovilidadRequisito> getListaRequisitoAux() {
		return listaRequisitoAux;
	}

	/**
	 * Gets the lista requisito aux.
	 *
	 * @return the lista requisito aux
	 */
	public List<MovilidadRequisito> getListaRequisitoAuxVerificacion() {
		List<MovilidadRequisito> listaRequisitoAuxVerificacion = new ArrayList<MovilidadRequisito>();
		if (!esListaVacia(listaRequisitoAux)) {
			Iterator<MovilidadRequisito> i = listaRequisitoAux.iterator();
			while (i.hasNext()) {
				MovilidadRequisito movilidadRequisito = i.next();
				if (StringUtils.isEmpty(movilidadRequisito.getRequisito().getSeleccion())) {
					listaRequisitoAuxVerificacion.add(movilidadRequisito);
				}
			}
		}
		return listaRequisitoAuxVerificacion;
	}

	/**
	 * Gets the lista requisito aux.
	 *
	 * @return the lista requisito aux
	 */
	public List<MovilidadRequisito> getListaRequisitoAuxSeleccion() {
		List<MovilidadRequisito> listaRequisitoAuxSeleccion = new ArrayList<MovilidadRequisito>();
		if (!esListaVacia(listaRequisitoAux)) {
			Iterator<MovilidadRequisito> i = listaRequisitoAux.iterator();
			while (i.hasNext()) {
				MovilidadRequisito movilidadRequisito = i.next();
				if (StringUtils.isNotEmpty(movilidadRequisito.getRequisito().getSeleccion())
						&& "S".equals(movilidadRequisito.getRequisito().getSeleccion())) {
					listaRequisitoAuxSeleccion.add(movilidadRequisito);
				}
			}
		}
		return listaRequisitoAuxSeleccion;
	}

	/**
	 * Sets the lista requisito aux.
	 *
	 * @param listaRequisitoAux
	 *            the new lista requisito aux
	 */
	public void setListaRequisitoAux(List<MovilidadRequisito> listaRequisitoAux) {
		this.listaRequisitoAux = listaRequisitoAux;
	}

	public Dependencia getDependenciaMov() {
		return dependenciaMov;
	}

	public void setDependenciaMov(Dependencia dependenciaMov) {
		this.dependenciaMov = dependenciaMov;
	}

	public String getMinYear() {
		return String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1);
	}

}