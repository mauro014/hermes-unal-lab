/**
 * Modified by Mauricio Amaya Ríos<br/>
 * Date:  12/12/2013<br/>
 */

package co.edu.unal.hermes.vista.movilidad.aprobacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadDocentesArtes;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesArtes;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.MovilidadVisitantesArtes;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoArchivoMovilidad;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.vista.movilidad.ManejadorBaseRevisionMovilidad;

public class ManejadorAprobacionMovilidadSede extends ManejadorBaseRevisionMovilidad {

	private static final long serialVersionUID = -1417121309431286925L;

	private static final String MENSAJE_ERROR_VALOR_APROBADO = "Debe ingresar un valor aprobado por la sede";

	CorreoPlantilla correoActual = new CorreoPlantilla();
	String cuerpoCorreo = "";
	private Dependencia dependenciaMov;

	private Long restriccionTiquetes = 0L;

	public void insertarArchivoMovilidadDE(FileUploadEvent event) {
		UploadedFile archivo = event.getFile();
		// Obtener código de solicitud
		Long idMovilidad = (Long) event.getComponent().getAttributes().get("idMovilidad");
		// Cargar archivo disco
		ArchivoMovilidadDE archivoMovilidad = insertarArchivoMovilidadDEGenerico(idMovilidad, archivo,
				TipoArchivoMovilidad.REVISION_REQUISITOS_SEDE);
		if (archivoMovilidad != null) {
			for (MovilidadDocentesExterior mde : listaMovilidadesEvento) {
				if (mde.getId().equals(idMovilidad)) {
					mde.agregarArchivoRequisitosSede(archivoMovilidad);
				}
			}
		}
	}

	public void insertarArchivoMovilidadEP(FileUploadEvent event) {
		UploadedFile archivo = event.getFile();
		// Obtener código de solicitud
		Long idMovilidad = (Long) event.getComponent().getAttributes().get("idMovilidad");
		// Cargar archivo disco
		ArchivoMovilidadEP archivoMovilidad = insertarArchivoMovilidadEPGenerico(idMovilidad, archivo,
				TipoArchivoMovilidad.REVISION_REQUISITOS_SEDE);
		if (archivoMovilidad != null) {
			for (MovilidadEstudiantesPosgrado mep : listaMovilidadesEstudiantePosgrado) {
				if (mep.getId().equals(idMovilidad)) {
					mep.agregarArchivoRequisitosSede(archivoMovilidad);
				}
			}
		}
	}

	/**
	 * Modificar movilidad visitante.
	 *
	 * @return the string
	 */
	public String modificarMovilidadVisitante() {

		Iterator<MovilidadVisitanteExterior> i = listaMovilidadesVisitante.iterator();
		while (i.hasNext()) {
			MovilidadVisitanteExterior movilidadVisitanteExterior = i.next();
			if (movilidadVisitanteExterior.getId().equals(Long.valueOf(movilidadVistaSeleccionada.getIdSolicitud()))) {
				movilidadVisitanteModificacion = movilidadVisitanteExterior;
				break;
			}
		}
		movilidadVisitanteModificacion.setError("");

		return "";
	}

	/**
	 * Modificar movilidad docentes.
	 *
	 * @return the string
	 */
	public String modificarMovilidadDocentes() {

		Iterator<MovilidadDocentesExterior> i = listaMovilidadesEvento.iterator();
		while (i.hasNext()) {
			MovilidadDocentesExterior movilidadDocentesExterior = i.next();
			if (movilidadDocentesExterior.getId().equals(Long.valueOf(movilidadVistaSeleccionada.getIdSolicitud()))) {
				movilidadEventosModificacion = movilidadDocentesExterior;
				break;
			}
		}
		movilidadVisitanteModificacion.setError("");
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
		return "";
	}

	/**
	 * Modificar movilidad docentes.
	 *
	 * @return the string
	 */
	public String modificarMovilidadVisitantesArtes() {
		Iterator<MovilidadVisitantesArtes> i = listaMovilidadesVisitantesArtes.iterator();
		while (i.hasNext()) {
			MovilidadVisitantesArtes movilidadVisitantesArtes = i.next();
			if (movilidadVisitantesArtes.getId().equals(Long.valueOf(movilidadVistaSeleccionada.getIdSolicitud()))) {
				movilidadVisitantesArtesModificacion = movilidadVisitantesArtes;
				break;
			}
		}
		return "";
	}

	/**
	 * Modificar movilidad estudiantes.
	 *
	 * @return the string
	 */
	public String modificarMovilidadEstudiantes() {
		Iterator<MovilidadEstudiantesPosgrado> i = listaMovilidadesEstudiantePosgrado.iterator();
		while (i.hasNext()) {
			MovilidadEstudiantesPosgrado movilidadEstudiantesPosgrado = i.next();
			if (movilidadEstudiantesPosgrado.getId()
					.equals(Long.valueOf(movilidadVistaSeleccionada.getIdSolicitud()))) {
				movilidadEstudiantesModificacion = movilidadEstudiantesPosgrado;
				break;
			}
		}
		return "";
	}

	public void imprimirMovilidadVisitante() {
		imprimirMovilidadVisitanteGenerico(movilidadVisitanteSeleccionada);
	}

	public void imprimirMovilidadEvento() {
		imprimirMovilidadEventoGenerico(movilidadEventosSeleccionada);
	}

	public void imprimirMovilidadDocenteArtes() {
		imprimirMovilidadDocenteArtesGenerico(movilidadDocenteArtesSeleccionada);
	}

	public void imprimirMovilidadPosgrado() {
		imprimirMovilidadEstudiantesPosgradoGenerico(movilidadEstudiantesSeleccionada);
	}

	public void imprimirMovilidadEstudiantesArtes() {
		imprimirMovilidadEstudiantesArtesGenerico(movilidadEstudiantesArtesSeleccionada);
	}

	public void cargarListasMovilidades() {

		Dependencia dependencia = ((InvestigadorInterno) personaActual).getDependencia();

		dependenciaMov = new Dependencia();
		dependenciaMov = dependencia;

		String modalidadId = null;

		if (StringUtils.isNotEmpty(convocatoriaPadreFiltro) && StringUtils.isNotEmpty(convocatoriaFiltro)) {
			modalidadId = convocatoriaFiltro;
		}

		listaMovilidadesGeneral = new ArrayList<ConvocatoriaMovilidadVista>();

		// Se cargan las movilidades de Visitantes
		listaMovilidadesVisitante = servicioMovilidad.obtenerMovilidadesRevision(
				MovilidadVisitanteExterior.VISITANTEEXTERIOR, personaActual, dependencia, modalidadId,
				MovilidadDAOHibernate.SEDE);

		crearMovilidadVistaVisitantesExt();

		// Se cargan las movilidades de Evento
		listaMovilidadesEvento = servicioMovilidad.obtenerMovilidadesRevision(MovilidadDocentesExterior.EVENTODOCENTE,
				personaActual, dependencia, modalidadId, MovilidadDAOHibernate.SEDE);

		crearMovilidadVistaDocentesEventos();

		// Estudiante de Posgrado
		listaMovilidadesEstudiantePosgrado = servicioMovilidad.obtenerMovilidadesRevision(
				MovilidadEstudiantesPosgrado.ESTUDIANTEPOSGRADO, personaActual, dependencia, modalidadId,
				MovilidadDAOHibernate.SEDE);

		crearMovilidadVistaEstudiantesPosgrados();

		// Docentes artes
		listaMovilidadesDocentesArtes = servicioMovilidad.obtenerMovilidadesRevision(
				MovilidadDocentesArtes.ARTISTASDOCENTES, personaActual, dependencia, modalidadId,
				MovilidadDAOHibernate.SEDE);

		crearMovilidadVistaDocentesArtes();

		// Visitantes artes
		listaMovilidadesVisitantesArtes = servicioMovilidad.obtenerMovilidadesRevision(
				MovilidadVisitantesArtes.ARTISTASVISITANTES, personaActual, dependencia, modalidadId,
				MovilidadDAOHibernate.SEDE);

		crearMovilidadVistaVisitantesArtes();

		// Estudiantes artes
		listaMovilidadesEstudiantesArtes = servicioMovilidad.obtenerMovilidadesRevision(
				MovilidadEstudiantesArtes.ARTISTASESTUDIANTES, personaActual, dependencia, modalidadId,
				MovilidadDAOHibernate.SEDE);

		crearMovilidadVistaEstudiantesArtes();

		crearListaConvocatoriasItem();
	}

	public void descargarDocumentoEvento() {
		Long idArchivo = Long.valueOf(obtenerValorMapContext("archivoResumen0"));
		descargarArchivoMovilidadDEGenerico(idArchivo);
	}

	public void descargarDocumentoEstPosgrado() {
		Long idArchivo = Long.valueOf(obtenerValorMapContext("archivoResumen4"));
		descargarArchivoMovilidadEPGenerico(idArchivo);
	}

	public void descargarDocumentoVisitante() {
		Long idArchivo = Long.valueOf(obtenerValorMapContext("archivoResumen1"));
		descargarArchivoMovilidadVEGenerico(idArchivo);
	}

	public void descargarDocumentoDocenArtes() {
		Long idArchivo = Long.valueOf(obtenerValorMapContext("archivoResumen6"));
		descargarArchivoMovilidadGenerico(idArchivo);
	}

	public void descargarDocumentoEstArtes() {
		Long idArchivo = Long.valueOf(obtenerValorMapContext("archivoResumenEstArtes"));
		descargarArchivoMovilidadGenerico(idArchivo);
	}

	public void insertarArchivoMovilidadVE(FileUploadEvent event) {
		UploadedFile archivo = event.getFile();
		// Obtener código de solicitud
		Long idMovilidad = (Long) event.getComponent().getAttributes().get("idMovilidad");
		// Cargar archivo disco
		ArchivoMovilidadVE archivoMovilidad = insertarArchivoMovilidadVEGenerico(idMovilidad, archivo,
				TipoArchivoMovilidad.REVISION_REQUISITOS_SEDE);
		if (archivoMovilidad != null) {
			for (MovilidadVisitanteExterior mve : listaMovilidadesVisitante) {
				if (mve.getId().equals(idMovilidad)) {
					mve.agregarArchivoRequisitosSede(archivoMovilidad);
				}
			}
		}
	}

	public String editarCorreo(Persona personaAux, String tipo, Long id, String comDir) {
		comDir = eliminarCaracterSinReplace("$", comDir);
		try {
			String correo = correoActual.getCuerpo();
			String investigador = "";
			investigador = personaAux.getNombre1() + " " + personaAux.getApellido1() + " " + personaAux.getApellido2();
			correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);
			correo = correo.replaceAll("<<IDMOVILIDAD>>", id.toString());
			correo = correo.replaceAll("<<TIPO>>", tipo);
			if (comDir == null || comDir.trim().equals("")) {
				comDir = "No se realizaron observaciones.";
			}
			correo = correo.replaceAll("<<OBSERVACION>>", comDir);
			cuerpoCorreo = correo;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "";
	}

	public CorreoPlantilla cargarPlantilla(int cod_id) {
		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		List<CorreoPlantilla> lista = servicioGeneral.obtenerObjetos(CorreoPlantilla.class,
				"select c from CorreoPlantilla c where c.id='" + cod_id + "'");
		if (lista != null && lista.size() > 0) {
			correoActualAux = (CorreoPlantilla) lista.get(0);
		}
		return correoActualAux;
	}

	public String aprobarMovilidadVisitante() {
		/*
		 * if (movilidadVisitanteSeleccionada.getValorAprobadoSede() != null) {
		 * boolean error = false; if
		 * (movilidadVisitanteSeleccionada.getValorAprobadoSede() == 0) { error
		 * = true; } if (error) { errorString = MENSAJE_ERROR_VALOR_APROBADO;
		 * movilidadVisitanteSeleccionada.setError(errorString); return ""; } }
		 * else { errorString = MENSAJE_ERROR_VALOR_APROBADO;
		 * movilidadVisitanteSeleccionada.setError(errorString); return ""; }
		 */
		String sqlBuscaConvocatoria = "select #id e.id, #montoApoyoGanadores e.montoApoyoGanadores from Convocatoria e where e.id = "
				+ movilidadVisitanteSeleccionada.getConvocatoria().getId() + "";
		List<Convocatoria> listConvs = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class, sqlBuscaConvocatoria);
		Convocatoria convActual = listConvs.get(0);
		if (convActual.getMontoApoyoGanadores() != null) {
			restriccionTiquetes = Long.parseLong(convActual.getMontoApoyoGanadores());
		}
		if (movilidadVisitanteSeleccionada.getValorAprobadoSede() == null
				&& movilidadVisitanteSeleccionada.getDispPresupuestalSede().equals("SI")) {
			movilidadVisitanteSeleccionada.setError("Por favor, indicar el valor de aporte por la sede.");
			return "";
		}
		if (movilidadVisitanteSeleccionada.getDispPresupuestalSede().equals("SI")
				&& movilidadVisitanteSeleccionada.getValorAprobadoSede() > restriccionTiquetes) {
			movilidadVisitanteSeleccionada
					.setError("El valor máximo que cubre la convocatoria es de $" + restriccionTiquetes + ".");
			return "";
		}
		return guardarRevisionMovilidadVisitante("SI", 67, false);
	}

	public String noAprobarMovilidadVisitante() {
		return guardarRevisionMovilidadVisitante("NO", 69, true);
	}

	public String aprobarMovilidadEventos() {
		String sqlBuscaConvocatoria = "select #id e.id, #montoApoyoGanadores e.montoApoyoGanadores, #validacionCostosConvocatoriaMovilidad e.validacionCostosConvocatoriaMovilidad from Convocatoria e where e.id = "
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
		if (movilidadEventosSeleccionada.getValorAprobadoSede() == null
				&& movilidadEventosSeleccionada.getDispPresupuestalSede().equals("SI")) {
			movilidadEventosSeleccionada.setError("Por favor, indicar el valor de aporte por la sede.");
			return "";
		}
		if (movilidadEventosSeleccionada.getDispPresupuestalSede().equals("SI")
				&& movilidadEventosSeleccionada.getValorAprobadoSede() > restriccionTiquetes) {
			movilidadEventosSeleccionada
					.setError("El valor máximo que cubre la convocatoria es de $" + restriccionTiquetes + ".");
			return "";
		}
		/*
		 * if (movilidadEventosSeleccionada.getValorAprobadoSede() != null) {
		 * boolean error = false; if
		 * (movilidadEventosSeleccionada.getValorAprobadoSede() == 0) { error =
		 * true; } if (error) { errorString =
		 * "Debe ingresar un valor aprobado por la sede";
		 * movilidadEventosSeleccionada.setError(errorString); return ""; } }
		 * else { errorString = "Debe ingresar un valor aprobado por la sede";
		 * movilidadEventosSeleccionada.setError(errorString); return ""; }
		 */
		return guardarMovilidadEvento("SI", 67, false);
	}

	public String noAprobarMovilidadEvento() {
		return guardarMovilidadEvento("NO", 69, true);
	}

	public String guardarRevisionMovilidadVisitante(String aprobacion, int numeroPlantilla, boolean noAprobar) {
		if (movilidadVisitanteSeleccionada.getComentariosDir().length() == 0) {
			errorString = "Debe ingresar los comentarios de la sede";
			movilidadVisitanteSeleccionada.setError(errorString);
			return "";
		}
		List<MovilidadVisitanteExterior> listaMovilidad = servicioGeneral.obtenerObjetos(
				MovilidadVisitanteExterior.class,
				"from MovilidadVisitanteExterior where id ='" + movilidadVisitanteSeleccionada.getId() + "'");
		if (listaMovilidad != null) {
			MovilidadVisitanteExterior mov = (MovilidadVisitanteExterior) listaMovilidad.get(0);
			personaActual = (Persona) sesion.getAttribute("persona");
			if (personaActual != null) {
				mov.setIdPersonaAprobacionSede(personaActual.getId().getDocumento());
				mov.setTipoIdPersonaAprobacionSede(personaActual.getId().getTipoDocumento());
			}
			Persona per = servicioPersona
					.obtenerPersona(new IdPersona(mov.getIdPersonaAprobacion(), mov.getTipoIdPersonaAprobacion()));
			String direccionFacultad = "";
			if (per != null) {
				direccionFacultad = per.getEmail();
			}
			mov.setFechaAprobacion(new Date());
			mov.setValorAprobadoSede(movilidadVisitanteSeleccionada.getValorAprobadoSede());
			mov.setAprobacion(aprobacion);
			mov.setComentariosDir(movilidadVisitanteSeleccionada.getComentariosDir());
			mov.setDispPresupuestalSede(movilidadVisitanteSeleccionada.getDispPresupuestalSede());
			servicioGeneral.guardarObjeto(mov);

			correoActual = cargarPlantilla(numeroPlantilla);
			editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(), mov.getComentariosDir());
			String dirCorreo = mov.getPersonaInv().getEmail();

			crearHistoricoEstadoMovilidadInvestigador(mov, movilidadVisitanteSeleccionada.getComentariosDir());

			String tipoMovilidad = TipoMovilidad.HER_MOVILIDAD_VISITANTES_EXT;
			return finalizacionyEnvio(dirCorreo, direccionFacultad, tipoMovilidad, noAprobar);
		}
		return "";
	}

	public String guardarMovilidadEvento(String aprobacion, int numeroPlantilla, boolean noAprobar) {
		if (movilidadEventosSeleccionada.getComentariosDir().length() == 0) {
			errorString = "Debe ingresar los comentarios de la sede";
			movilidadEventosSeleccionada.setError(errorString);
			return "";
		}
		List<MovilidadDocentesExterior> listaMovilidad = servicioGeneral.obtenerObjetos(MovilidadDocentesExterior.class,
				"from MovilidadDocentesExterior where id ='" + movilidadEventosSeleccionada.getId() + "'");
		if (listaMovilidad != null) {
			MovilidadDocentesExterior mov = (MovilidadDocentesExterior) listaMovilidad.get(0);
			personaActual = (Persona) sesion.getAttribute("persona");
			if (personaActual != null) {
				mov.setIdPersonaAprobacionSede(personaActual.getId().getDocumento());
				mov.setTipoIdPersonaAprobacionSede(personaActual.getId().getTipoDocumento());
			}
			Persona per = servicioPersona
					.obtenerPersona(new IdPersona(mov.getIdPersonaAprobacion(), mov.getTipoIdPersonaAprobacion()));
			String direccionFacultad = "";
			if (per != null) {
				direccionFacultad = per.getEmail();
			}
			mov.setAprobacion(aprobacion);
			mov.setFechaAprobacion(new Date());
			mov.setValorAprobadoSede(movilidadEventosSeleccionada.getValorAprobadoSede());
			mov.setComentariosDir(movilidadEventosSeleccionada.getComentariosDir());
			mov.setDispPresupuestalSede(movilidadEventosSeleccionada.getDispPresupuestalSede());
			servicioGeneral.guardarObjeto(mov);

			correoActual = cargarPlantilla(numeroPlantilla);
			editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(), mov.getComentariosDir());
			String dirCorreo = mov.getPersonaInv().getEmail();

			crearHistoricoEstadoMovilidadInvestigador(mov, movilidadEventosSeleccionada.getComentariosDir());

			String tipoMovilidad = TipoMovilidad.HER_MOVILIDAD_DOCENTES_EVENTOS;
			return finalizacionyEnvio(dirCorreo, direccionFacultad, tipoMovilidad, noAprobar);
		}
		return "";
	}

	private String finalizacionyEnvio(String dirCorreo, String direcionFacultad, String tipoMovilidad,
			boolean noAprobar) {

		String dirCorreoConfirmacion = personaActual.getEmail();
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarDireccion(dirCorreoConfirmacion);
		//correo.adicionarDireccion(Correo.CORREO_HERMES);
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
		if (!esCadenaVacia(direcionFacultad)) {
			correo.adicionarDireccion(direcionFacultad);
		}
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);
		cargarListasMovilidades();
		sesion.removeAttribute("manejadorAprobacionMovilidadSede");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacionSede";

	}

	public String aprobarMovilidadVisitanteArtes() {
		MovilidadVisitantesArtes mov = new MovilidadVisitantesArtes();

		MovilidadVisitantesArtes movAux = new MovilidadVisitantesArtes();

		Long id = ((MovilidadVisitantesArtes) (movilidadResidenciasArtesSeleccionada)).getId();
		movAux = (MovilidadVisitantesArtes) (movilidadResidenciasArtesSeleccionada);

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral.obtenerListaObjetos("MovilidadVisitantesArtes where id ='" + id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		mov = (MovilidadVisitantesArtes) listaMovilidad.get(0);
		mov.setAprobacionSede("SI");
		mov.setDescripcionSede(movAux.getDescripcionSede());
		mov.setDispPresupuestalSede(movAux.getDispPresupuestalSede());
		// servicioMovilidad.updateMovilidad(mov);
		// enviarMail();
		servicioGeneral.guardarObjeto(mov);

		personaActual = (Persona) sesion.getAttribute("persona");
		String dirCorreoConfirmacion = personaActual.getEmail();

		correoActual = cargarPlantilla(67);// 42
		editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(), mov.getDescripcionSede());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);

		sesion.removeAttribute("manejadorAprobacionMovilidadSede");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacionSede";
	}

	public String noAprobarMovilidadVisitanteArtes() {

		MovilidadVisitantesArtes mov = new MovilidadVisitantesArtes();
		MovilidadVisitantesArtes movAux = new MovilidadVisitantesArtes();
		Long id = ((MovilidadVisitantesArtes) (movilidadResidenciasArtesSeleccionada)).getId();
		movAux = (MovilidadVisitantesArtes) (movilidadResidenciasArtesSeleccionada);

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral.obtenerListaObjetos("MovilidadVisitantesArtes where id ='" + id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		mov = (MovilidadVisitantesArtes) listaMovilidad.get(0);
		personaActual = (Persona) sesion.getAttribute("persona");

		mov.setAprobacionSede("NO");
		mov.setDescripcionSede(movAux.getDescripcionSede());
		mov.setDispPresupuestalSede(movAux.getDispPresupuestalSede());
		// servicioMovilidad.updateMovilidad(mov);
		// enviarMail();
		servicioGeneral.guardarObjeto(mov);

		String dirCorreoConfirmacion = personaActual.getEmail();

		correoActual = cargarPlantilla(69);// 42
		editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(), mov.getDescripcionSede());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		Persona per = servicioPersona
				.obtenerPersona(new IdPersona(mov.getIdFacultad(), mov.getTipoDocumentoFacultad()));
		String dirCorreo = "";
		if (per != null) {
			dirCorreo = mov.getPersonaInv().getEmail();
		} else {
			dirCorreo = "sisii_nal@unal.edu.co";
		}

		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);

		sesion.removeAttribute("manejadorAprobacionMovilidadSede");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacionSede";
	}

	public String aprobarMovilidadEstudianteArtes() {

		MovilidadEstudiantesArtes mov = new MovilidadEstudiantesArtes();
		MovilidadEstudiantesArtes movAux = new MovilidadEstudiantesArtes();

		Long id = movilidadEstudiantesArtesSeleccionada.getId();
		movAux = movilidadEstudiantesArtesSeleccionada;

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral.obtenerListaObjetos("MovilidadEstudiantesArtes where id ='" + id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		mov = (MovilidadEstudiantesArtes) listaMovilidad.get(0);

		mov.setAprobacionSede("SI");
		mov.setDescripcionSede(movAux.getDescripcionSede());
		mov.setDispPresupuestalSede(movAux.getDispPresupuestalSede());
		servicioGeneral.guardarObjeto(mov);

		personaActual = (Persona) sesion.getAttribute("persona");
		String dirCorreoConfirmacion = personaActual.getEmail();

		correoActual = cargarPlantilla(67);// 42
		editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(),
				mov.getDescripcionFacultad());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);

		sesion.removeAttribute("manejadorAprobacionMovilidadSede");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacionSede";

	}

	public String noAprobarMovilidadEstudianteArtes() {

		MovilidadEstudiantesArtes mov = new MovilidadEstudiantesArtes();
		MovilidadEstudiantesArtes movAux = new MovilidadEstudiantesArtes();

		Long id = movilidadEstudiantesArtesSeleccionada.getId();
		movAux = movilidadEstudiantesArtesSeleccionada;

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral.obtenerListaObjetos("MovilidadEstudiantesArtes where id ='" + id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		mov = (MovilidadEstudiantesArtes) listaMovilidad.get(0);

		mov.setAprobacionSede("NO");
		mov.setDescripcionSede(movAux.getDescripcionSede());
		mov.setDispPresupuestalSede(movAux.getDispPresupuestalSede());
		servicioGeneral.guardarObjeto(mov);

		personaActual = (Persona) sesion.getAttribute("persona");
		String dirCorreoConfirmacion = personaActual.getEmail();

		correoActual = cargarPlantilla(69);// 42
		editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(),
				mov.getDescripcionFacultad());

		String correoEstudiante = movilidadEstudiantesArtesSeleccionada.getEstudianteInv().getEmail();

		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.adicionarDireccion(correoEstudiante);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);

		sesion.removeAttribute("manejadorAprobacionMovilidadSede");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacionSede";

	}

	public String noAprobarMovilidadPosgrado() {
		return guardarMovilidadPosgrado("NO", 69, true);
	}

	public String aprobarMovilidadPosgrado() {
		String sqlBuscaConvocatoria = "select #id e.id, #montoApoyoGanadores e.montoApoyoGanadores, #tiempoEjecucionProyecto e.tiempoEjecucionProyecto from Convocatoria e where e.id = "
				+ movilidadEstudiantesSeleccionada.getConvocatoria().getId() + "";

		List<Convocatoria> listConvs = servicioGeneral.obtenerObjetosLimitado(Convocatoria.class, sqlBuscaConvocatoria);
		Convocatoria convActual = listConvs.get(0);
		if (convActual.getMontoApoyoGanadores() != null) {
			restriccionTiquetes = Long.parseLong(convActual.getMontoApoyoGanadores());
		}
		if (movilidadEstudiantesSeleccionada.getValorAprobadoSede() == null && movilidadEstudiantesSeleccionada.getDispPresupuestalSede().equals("SI")) {
			movilidadEstudiantesSeleccionada
					.setError("Por favor, indicar el valor de aporte por la sede.");
			return "";
		}
		if (movilidadEstudiantesSeleccionada.getDispPresupuestalSede().equals("SI") && movilidadEstudiantesSeleccionada.getValorAprobadoSede() > restriccionTiquetes) {
			movilidadEstudiantesSeleccionada
					.setError("El valor máximo que cubre la convocatoria es de $" + restriccionTiquetes + ".");
			return "";
		}
		/*
		 * if (movilidadEstudiantesSeleccionada.getValorAprobadoSede() != null)
		 * { boolean error = false; if
		 * (movilidadEstudiantesSeleccionada.getValorAprobadoSede() == 0) {
		 * error = true; } if (error) { errorString =
		 * "Debe ingresar un valor aprobado por la sede";
		 * movilidadEstudiantesSeleccionada.setError(errorString); return ""; }
		 * } else { errorString = "Debe ingresar un valor aprobado por la sede";
		 * movilidadEstudiantesSeleccionada.setError(errorString); return ""; }
		 */
		return guardarMovilidadPosgrado("SI", 67, false);
	}

	public String guardarMovilidadPosgrado(String aprobacion, int numeroPlantilla, boolean noAprobar) {
		if (movilidadEstudiantesSeleccionada.getComentariosDir().length() == 0) {
			errorString = "Debe ingresar los comentarios de la sede";
			movilidadEstudiantesSeleccionada.setError(errorString);
			return "";
		}
		List<MovilidadEstudiantesPosgrado> listaMovilidad = servicioGeneral.obtenerObjetos(
				MovilidadEstudiantesPosgrado.class,
				"from MovilidadEstudiantesPosgrado where id ='" + movilidadEstudiantesSeleccionada.getId() + "'");
		if (listaMovilidad != null) {
			MovilidadEstudiantesPosgrado mov = (MovilidadEstudiantesPosgrado) listaMovilidad.get(0);
			personaActual = (Persona) sesion.getAttribute("persona");
			if (personaActual != null) {
				mov.setIdPersonaAprobacionSede(personaActual.getId().getDocumento());
				mov.setTipoIdPersonaAprobacionSede(personaActual.getId().getTipoDocumento());
			}
			Persona per = servicioPersona
					.obtenerPersona(new IdPersona(mov.getIdPersonaAprobacion(), mov.getTipoIdPersonaAprobacion()));
			String direccionFacultad = "";
			if (per != null) {
				direccionFacultad = per.getEmail();
			}
			mov.setValorAprobadoSede(movilidadEstudiantesSeleccionada.getValorAprobadoSede());
			mov.setAprobacion(aprobacion);
			mov.setFechaAprobacion(new Date());
			mov.setComentariosDir(movilidadEstudiantesSeleccionada.getComentariosDir());
			mov.setDispPresupuestalSede(movilidadEstudiantesSeleccionada.getDispPresupuestalSede());
			servicioGeneral.guardarObjeto(mov);

			correoActual = cargarPlantilla(numeroPlantilla);
			editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(), mov.getComentariosDir());
			String dirCorreo = mov.getPersonaInv().getEmail();

			crearHistoricoEstadoMovilidadInvestigador(mov, movilidadEstudiantesSeleccionada.getComentariosDir());

			String tipoMovilidad = TipoMovilidad.HER_MOVILIDAD_ESTUDIANTE_POS;
			return finalizacionyEnvio(dirCorreo, direccionFacultad, tipoMovilidad, noAprobar);
		}
		return "";
	}

	public void descargarDocumentoVistArtes() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		Object o = (Object) map.get("archivoResumenVisArtes");
		Long idArchivo = Long.valueOf((String) o);
		o = (Object) map.get("movilidadResidenciasArtes");
		Long idMEP = Long.valueOf((String) o);
		ArchivoMovilidad archMEPSel = null;
		MovilidadVisitantesArtes mdepSel = null;
		for (Object o2 : listaMovilidadesVisitantesArtes) {
			MovilidadVisitantesArtes me = (MovilidadVisitantesArtes) o2;
			Long idMep2 = me.getId();
			if (idMep2.equals(idMEP)) {
				mdepSel = me;
				break;
			}
		}
		if (mdepSel != null) {
			Iterator it = mdepSel.getArchivos().iterator();
			while (it.hasNext()) {
				ArchivoMovilidad amde = (ArchivoMovilidad) it.next();
				if (amde.getId().equals(idArchivo)) {
					archMEPSel = amde;
					break;
				}
			}
		}
		if (archMEPSel != null) {
			ArchivoMovilidad archivo = (ArchivoMovilidad) archMEPSel;

			FacesContext ctx = FacesContext.getCurrentInstance();
			try {
				if (!ctx.getResponseComplete()) {
					HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition", "attachment;filename=\"" + archivo.getNombre()
					// "attachment;filename=\"" + "Documento.pdf"
							+ "\"");
					ServletOutputStream out = response.getOutputStream();
					out.write(archivo.getBytes());
					out.flush();
					ctx.responseComplete();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void imprimirMovilidadVisitanteArtes() {
		FacesContext context = FacesContext.getCurrentInstance();
		Long id = movilidadResidenciasArtesSeleccionada.getId();
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", Long.toString(id));
		r.setNombreReporte("/movilidad/ReporteMovilidadVisArt");
		r.setFormato(ReporteBirt.FORMATO_PDF);
		sesion.setAttribute("reporte", r);
		try {
			context.getExternalContext().dispatch("/ReporteEngineServlet");
		} catch (Exception e) {
			// System.out.println(e);
		} finally {
			context.responseComplete();
		}
	}

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
			mov.setDispPresupuestalSede(movilidadDocenteArtesSeleccionada.getDispPresupuestalSede());
			servicioGeneral.guardarObjeto(mov);
			correoActual = cargarPlantilla(69);
			editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(),
					mov.getDescripcionFacultad());
			String dirCorreo = mov.getPersonaInv().getEmail();
			Persona per = servicioPersona
					.obtenerPersona(new IdPersona(mov.getIdPersonaAprobacion(), mov.getTipoIdPersonaAprobacion()));
			String direccionFacultad = "";
			if (per != null) {
				direccionFacultad = per.getEmail();
			}
			String tipoMovilidad = TipoMovilidad.HER_MOVILIDAD_DOCENTES_ART;
			boolean noAprobar = true;
			return finalizacionyEnvio(dirCorreo, direccionFacultad, tipoMovilidad, noAprobar);
		}

		return "";
	}

	public String aprobarMovilidadDocenteArtes() {

		MovilidadDocentesArtes mov;
		List<MovilidadDocentesArtes> listaMovilidad = servicioGeneral.obtenerObjetos(MovilidadDocentesArtes.class,
				"from MovilidadDocentesArtes where id ='" + movilidadDocenteArtesSeleccionada.getId() + "'");

		if (listaMovilidad != null) {
			mov = (MovilidadDocentesArtes) listaMovilidad.get(0);
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
			mov.setAprobacionSede("SI");
			mov.setDescripcionSede(movilidadDocenteArtesSeleccionada.getDescripcionSede());
			mov.setDispPresupuestalSede(movilidadDocenteArtesSeleccionada.getDispPresupuestalSede());
			servicioGeneral.guardarObjeto(mov);
			correoActual = cargarPlantilla(67);
			editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(),
					mov.getDescripcionFacultad());
			String dirCorreo = mov.getPersonaInv().getEmail();
			Persona per = servicioPersona
					.obtenerPersona(new IdPersona(mov.getIdPersonaAprobacion(), mov.getTipoIdPersonaAprobacion()));
			String direccionFacultad = "";
			if (per != null) {
				direccionFacultad = per.getEmail();
			}
			String tipoMovilidad = TipoMovilidad.HER_MOVILIDAD_DOCENTES_ART;
			boolean noAprobar = false;
			return finalizacionyEnvio(dirCorreo, direccionFacultad, tipoMovilidad, noAprobar);
		}

		return "";
	}

	public List<MovilidadDocentesExterior> getListaMovilidadesEvento() {
		return listaMovilidadesEvento;
	}

	public List<MovilidadEstudiantesPosgrado> getListaMovilidadesEstudiantePosgrado() {
		return listaMovilidadesEstudiantePosgrado;
	}

	public List<MovilidadVisitanteExterior> getListaMovilidadesVisitante() {
		return listaMovilidadesVisitante;
	}

	public List<MovilidadDocentesArtes> getListaMovilidadesDocentesArtes() {
		return listaMovilidadesDocentesArtes;
	}

	public List<MovilidadEstudiantesArtes> getListaMovilidadesEstudiantesArtes() {
		return listaMovilidadesEstudiantesArtes;
	}

	public List<MovilidadVisitantesArtes> getListaMovilidadesVisitantesArtes() {
		return listaMovilidadesVisitantesArtes;
	}

	public void setMovilidadEventosSeleccionada(MovilidadDocentesExterior movilidadEventosSeleccionada) {
		this.movilidadEventosSeleccionada = movilidadEventosSeleccionada;
	}

	public MovilidadDocentesExterior getMovilidadEventosSeleccionada() {
		return movilidadEventosSeleccionada;
	}

	public void setMovilidadVisitanteSeleccionada(MovilidadVisitanteExterior movilidadVisitanteSeleccionada) {
		this.movilidadVisitanteSeleccionada = movilidadVisitanteSeleccionada;
	}

	public MovilidadVisitanteExterior getMovilidadVisitanteSeleccionada() {
		return movilidadVisitanteSeleccionada;
	}

	public void setMovilidadEstudiantesSeleccionada(MovilidadEstudiantesPosgrado movilidadEstudiantesSeleccionada) {
		this.movilidadEstudiantesSeleccionada = movilidadEstudiantesSeleccionada;
	}

	public MovilidadEstudiantesPosgrado getMovilidadEstudiantesSeleccionada() {
		return movilidadEstudiantesSeleccionada;
	}

	public MovilidadDocentesArtes getMovilidadDocenteArtesSeleccionada() {
		return movilidadDocenteArtesSeleccionada;
	}

	public void setMovilidadDocenteArtesSeleccionada(MovilidadDocentesArtes movilidadDocenteArtesSeleccionada) {
		this.movilidadDocenteArtesSeleccionada = movilidadDocenteArtesSeleccionada;
	}

	public MovilidadVisitantesArtes getMovilidadResidenciasArtesSeleccionada() {
		return movilidadResidenciasArtesSeleccionada;
	}

	public void setMovilidadResidenciasArtesSeleccionada(
			MovilidadVisitantesArtes movilidadResidenciasArtesSeleccionada) {
		this.movilidadResidenciasArtesSeleccionada = movilidadResidenciasArtesSeleccionada;
	}

	public MovilidadEstudiantesArtes getMovilidadEstudiantesArtesSeleccionada() {
		return movilidadEstudiantesArtesSeleccionada;
	}

	public void setMovilidadEstudiantesArtesSeleccionada(
			MovilidadEstudiantesArtes movilidadEstudiantesArtesSeleccionada) {
		this.movilidadEstudiantesArtesSeleccionada = movilidadEstudiantesArtesSeleccionada;
	}

	public Dependencia getDependenciaMov() {
		return dependenciaMov;
	}

	public void setDependenciaMov(Dependencia dependenciaMov) {
		this.dependenciaMov = dependenciaMov;
	}

}
