package co.edu.unal.hermes.vista.movilidad.aprobacion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.unal.hermes.modelo.ArchivoMovilidad;
import co.edu.unal.hermes.modelo.ArchivoMovilidadDE;
import co.edu.unal.hermes.modelo.ArchivoMovilidadEP;
import co.edu.unal.hermes.modelo.ArchivoMovilidadVE;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Movilidad;
import co.edu.unal.hermes.modelo.MovilidadDocentesArtes;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesArtes;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.reporte.ReporteBirt;
import co.edu.unal.hermes.utils.Fecha;
import co.edu.unal.hermes.utils.Navegacion;
import co.edu.unal.hermes.vista.movilidad.ManejadorBaseMovilidad;
import co.edu.unal.hermes.vista.movilidad.MovilidadVista;

public class ManejadorAprobacionMovilidadConsSede extends ManejadorBaseMovilidad {

	private static final long serialVersionUID = -710665515911642923L;

	private List listaMovilidadesTesis;
	private List listaMovilidadesEvento;
	private List listaMovilidadesVisitante;
	private List listaMovilidadesEstudiantePosgrado;
	private List listaMovilidadesEstudiantePosgradoEventos;
	private List listaMovilidadesDocentesArtes;
	private List listaMovilidadesEstudiantesArtes;
	private List listaMovilidadesVisitantesArtes;
	/*-*/

	private List listaMovilidadesVisitanteSede;
	private List listaMovilidadesEstudiantePosgradoSede;
	private List listaMovilidadesEstudiantePosgradoEventosSede;
	private List listaMovilidadesDocentesArtesSede;
	private List listaMovilidadesEstudiantesArtesSede;

	private MovilidadVista movilidadVista;
	private MovilidadDocentesExterior movilidadEventosSeleccionada;
	private MovilidadVisitanteExterior movilidadVisitanteSeleccionada;
	private MovilidadDocentesArtes movilidadDocenteArtesSeleccionada;
	private MovilidadEstudiantesPosgrado movilidadEstudiantesSeleccionada;
	private Movilidad movilidadCancelSeleccionada;
	private ArchivoMovilidadDE archivoMovilidadDESeleccionada;
	private ArchivoMovilidadVE archivoMovilidadVSeleccionada;
	private ArchivoMovilidadEP ArchivoMovilidadEPSeleccionada;
	private String aceptacionDIB;
	private boolean mostrarPonencia;
	private boolean mostrarInscripcion;
	private boolean mostrarArchivo;

	private String aceptacionFacultad;
	private String aceptacionFacultadVisitante;
	private String aceptacionFacultadPosgrado;
	private String motivoCancelacion;

	private String aceptacionFacultadDocentesArt;
	private String aceptacionFacultadEstudiantesArt;
	private String aceptacionFacultadVisitantesArt;

	CorreoPlantilla correoActual = new CorreoPlantilla();
	String cuerpoCorreo = "";

	private UIData tablaActividades;

	private SelectItem[] aprobacion = { new SelectItem("NO", "NO"), new SelectItem("SI", "SI") };

	private SelectItem[] aprobacionVisitante = { new SelectItem("NO", "NO"), new SelectItem("SI", "SI") };

	private SelectItem[] aprobacionPosgrado = { new SelectItem("NO", "NO"), new SelectItem("SI", "SI") };

	private SelectItem[] aprobacionDocentesArt = { new SelectItem("NO", "NO"), new SelectItem("SI", "SI") };

	private SelectItem[] aprobacionEstudiantesArt = { new SelectItem("NO", "NO"), new SelectItem("SI", "SI") };

	private SelectItem[] aprobacionVisitantesArt = { new SelectItem("NO", "NO"), new SelectItem("SI", "SI") };

	public ManejadorAprobacionMovilidadConsSede() {
		try {

			listaMovilidadesEventoSede = new ArrayList();
			listaMovilidadesVisitanteSede = new ArrayList();
			listaMovilidadesEstudiantePosgradoSede = new ArrayList();
			listaMovilidadesEstudiantePosgradoEventosSede = new ArrayList();
			listaMovilidadesDocentesArtesSede = new ArrayList();
			listaMovilidadesEstudiantesArtesSede = new ArrayList();

			mostrarPonencia = false;
			mostrarInscripcion = false;
			mostrarArchivo = true;
			cargarListasMovilidades();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void imprimirMovilidadVisitante() {
		imprimirMovilidadVisitanteGenerico(movilidadVisitanteSeleccionada);
	}

	public void imprimirMovilidadEvento() {
		imprimirMovilidadEventoGenerico(movilidadEventosSeleccionada);
	}

	public void imprimirMovilidadPosgrado() {
		imprimirMovilidadEstudiantesPosgradoGenerico(movilidadEstudiantesSeleccionada);
	}

	public void imprimirMovilidadDocenteArtes() {
		FacesContext context = FacesContext.getCurrentInstance();
		Long id = movilidadDocenteArtesSeleccionada.getId();
		ReporteBirt r = new ReporteBirt();
		r.adicionarParametro("id", Long.toString(id));
		r.setNombreReporte("/movilidad/ReporteMovilidadDocArt");
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

	private String armarNombre(Persona p) {
		String nombre, nombre1, nombre2, apellido1, apellido2;
		if (p.getNombre1() != null) {
			nombre1 = p.getNombre1();
		} else {
			nombre1 = "";
		}
		if (p.getNombre2() != null) {
			nombre2 = p.getNombre2();
		} else {
			nombre2 = "";
		}
		if (p.getApellido1() != null) {
			apellido1 = p.getApellido1();
		} else {
			apellido1 = "";
		}
		if (p.getApellido2() != null) {
			apellido2 = p.getApellido2();
		} else {
			apellido2 = "";
		}
		nombre = nombre1 + " " + nombre2 + " " + apellido1 + " " + apellido2;
		return nombre;
	}

	private void cargarListasMovilidades() {
		// Se cargan las movilidades de Tesis
		// listaMovilidadesTesis =
		// servicioMovilidad.obtenerMovilidadesXTipo(Movilidad.TESIS);

		Persona persona = new Persona();
		persona = (Persona) sesion.getAttribute("persona");

		Dependencia dependencia;

		dependencia = new Dependencia();

		// if (persona instanceof Investigador) {
		// if (persona instanceof InvestigadorInterno) {

		try {
			persona = servicioPersona.obtenerInvestigadorInternoCompleto(persona.getId());
			InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;
			dependencia = servicioDependencia.obtenerDependencia2(investigadorInterno.getId());
			// }
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
		persona = (Persona) sesion.getAttribute("persona");

		// Se cargan las movilidades de Visitantes
		listaMovilidadesVisitanteSede = servicioMovilidad
				.obtenerMovilidadesXTipoConsSede(MovilidadVisitanteExterior.VISITANTEEXTERIOR, persona, dependencia);
		// Se cargan las movilidades de Evento
		listaMovilidadesEventoSede = servicioMovilidad
				.obtenerMovilidadesXTipoConsSede(MovilidadDocentesExterior.EVENTODOCENTE, persona, dependencia);
		// Estudiante de Posgrado eventos
		listaMovilidadesEstudiantePosgradoEventosSede = servicioMovilidad.obtenerMovilidadesXTipoConsSede(
				MovilidadEstudiantesPosgrado.ESTUDIANTEPOSGRADOEVENTOS, persona, dependencia);
		// Estudiante de Posgrado
		listaMovilidadesEstudiantePosgradoSede = servicioMovilidad
				.obtenerMovilidadesXTipoConsSede(MovilidadEstudiantesPosgrado.ESTUDIANTEPOSGRADO, persona, dependencia);
		// docentes artes
		listaMovilidadesDocentesArtesSede = servicioMovilidad
				.obtenerMovilidadesArtesConsSede(MovilidadDocentesArtes.ARTISTASDOCENTES, persona, dependencia);
		// Estudiantes artes
		listaMovilidadesEstudiantesArtesSede = servicioMovilidad
				.obtenerMovilidadesArtesConsSede(MovilidadEstudiantesArtes.ARTISTASESTUDIANTES, persona, dependencia);

		// ---

	}
	
	public void descargarDocumentoEvento() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		Object o = (Object) map.get("archivoResumen");
		Long idArchivo = Long.valueOf((String) o);
		descargarArchivoMovilidadDEGenerico(idArchivo);
	}


	public void descargarDocumentoEstPosgrado() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		Object o = (Object) map.get("archivoResumen4");
		Long idArchivo = Long.valueOf((String) o);
		descargarArchivoMovilidadEPGenerico(idArchivo);
	}

	public void descargarDocumentoDocenArtes() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		Object o = (Object) map.get("archivoResumen6");
		Long idArchivo = Long.valueOf((String) o);
		descargarArchivoMovilidadGenerico(idArchivo);
	}

	public void descargarDocumentoVisitante() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		Object o = (Object) map.get("archivoResumen1");
		Long idArchivo = Long.valueOf((String) o);
		descargarArchivoMovilidadVEGenerico(idArchivo);
	}


	public String aprobarMovilidadEventos() {

		MovilidadDocentesExterior mov = new MovilidadDocentesExterior();
		MovilidadDocentesExterior movAux = new MovilidadDocentesExterior();

		Long id = ((MovilidadDocentesExterior) (movilidadEventosSeleccionada)).getId();

		movAux = (MovilidadDocentesExterior) (movilidadEventosSeleccionada);

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral.obtenerListaObjetos("MovilidadDocentesExterior where id ='" + id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		mov = (MovilidadDocentesExterior) listaMovilidad.get(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		if (personaActual != null) {
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacion("SI");
		mov.setComentariosFac(movAux.getComentariosFac());
		// servicioMovilidad.updateMovilidad(mov);
		// enviarMail();
		servicioGeneral.guardarObjeto(mov);

		String dirCorreoConfirmacion = personaActual.getEmail();

		correoActual = cargarPlantilla(66);// 42
		editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(), mov.getComentariosFac());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);

		List listaCorreoEncargado = new ArrayList();
		List listaParametroAux;
		listaParametroAux = new ArrayList();

		Dependencia dependenciaAux;
		dependenciaAux = new Dependencia();
		Persona personaEnvio = new Persona();

		personaEnvio = servicioPersona.obtenerInvestigadorInternoCompleto(mov.getPersonaInv().getId());

		InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
		dependenciaAux = servicioDependencia.obtenerDependencia(investigadorInterno.getId());

		sesion.removeAttribute("ManejadorAprobacionMovilidad");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacion";
	}

	public String editarCorreo(Persona personaAux, String tipo, Long id, String comFac) {

		try {
			String coinvNombre = "";

			String correo = correoActual.getCuerpo();

			String investigador = "";

			investigador = personaAux.getNombre1() + " " + personaAux.getApellido1() + " " + personaAux.getApellido2();
			correo = correo.replaceAll("<<INVESTIGADOR>>", investigador);

			correo = correo.replaceAll("<<IDMOVILIDAD>>", id.toString());
			correo = correo.replaceAll("<<TIPO>>", tipo);

			correo = correo.replaceAll("<<OBSERVACION>>", comFac);

			cuerpoCorreo = correo;

			// cuerpoCorreo2 = investigador + " " + coinvNombre;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "";
	}

	public CorreoPlantilla cargarPlantilla(int cod_id) {

		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		// CorreoPlantilla correoActualAux=(CorreoPlantilla)
		CorreoPlantilla a = new CorreoPlantilla();
		// CorreoPlantilla correoActualAux3 = (CorreoPlantilla) servicioGeneral
		// .obtenerObjeto(a, Long.valueOf(String.valueOf(cod_id)));
		List lista = servicioGeneral.obtenerObjetos("select c from CorreoPlantilla c where c.id='" + cod_id + "'");
		if (lista != null && lista.size() > 0) {
			correoActualAux = (CorreoPlantilla) lista.get(0);
		}
		// String
		// correo=correoActual.getCuerpo().replaceAll("<<fecha>>",Fecha.fechaActual());
		return correoActualAux;
	}

	public String aprobarMovilidadVisitante() {
		MovilidadVisitanteExterior mov = new MovilidadVisitanteExterior();
		MovilidadVisitanteExterior movAux = new MovilidadVisitanteExterior();

		Long id = movilidadVisitanteSeleccionada.getId();
		movAux = movilidadVisitanteSeleccionada;

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral.obtenerListaObjetos("MovilidadVisitanteExterior where id ='" + id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		mov = (MovilidadVisitanteExterior) listaMovilidad.get(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		if (personaActual != null) {
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacion("SI");
		mov.setComentariosFac(movAux.getComentariosFac());
		// servicioMovilidad.updateMovilidad(mov);
		// enviarMail();
		servicioGeneral.guardarObjeto(mov);

		String dirCorreoConfirmacion = personaActual.getEmail();

		correoActual = cargarPlantilla(66);// 42
		editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(), mov.getComentariosFac());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);

		List listaCorreoEncargado = new ArrayList();
		List listaParametroAux;
		listaParametroAux = new ArrayList();

		Dependencia dependenciaAux;
		dependenciaAux = new Dependencia();
		Persona personaEnvio = new Persona();

		personaEnvio = servicioPersona.obtenerInvestigadorInternoCompleto(mov.getPersonaInv().getId());

		InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
		dependenciaAux = servicioDependencia.obtenerDependencia(investigadorInterno.getId());

		/*
		 * correoActual = cargarPlantilla(86);
		 * 
		 * listaCorreoEncargado = this.servicioGeneral
		 * .obtenerObjetos("FROM Parametro WHERE nombre = 'R_MOVILIDAD'    AND DESCRIPCION= '"
		 * + dependenciaAux.getSede().getId() + "'");
		 * 
		 * 
		 * String correoEnvio ="sisii_nal@unal.edu.co"; Persona
		 * personaActualAux2 = new Persona();
		 * 
		 * if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0)
		 * { Parametro paActual = (Parametro) listaCorreoEncargado .get(0);
		 * String numeroDocumento = "0"; numeroDocumento = paActual.getValor();
		 * 
		 * IdPersona idAux = new IdPersona();
		 * idAux.setDocumento(numeroDocumento); idAux.setTipoDocumento("C");
		 * personaActualAux2 = servicioPersona.obtenerPersona(idAux); if
		 * (personaActualAux2.getEmail() != null &&
		 * !personaActualAux2.getEmail().equals("")) {
		 * 
		 * correoEnvio = personaActualAux2.getEmail(); } else { correoActual =
		 * cargarPlantilla(87); }
		 * 
		 * } else { correoActual = cargarPlantilla(87); }
		 * 
		 * editarCorreo(personaActualAux2,mov.getTipoMovilidad().getNombre(),
		 * mov.getId(),mov.getComentariosFac()); correo = new Correo();
		 * correo.setOrigen(Correo.CORREO_HERMES); dirCorreo = correoEnvio;
		 * correo.adicionarDireccion(dirCorreo);
		 * correo.adicionarCopiaOculta(dirCorreo);
		 * correo.setAsunto(correoActual.getAsunto());
		 * correo.setCuerpo(cuerpoCorreo); servicioCorreo.enviarCorreo(correo);
		 */

		sesion.removeAttribute("ManejadorAprobacionMovilidad");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacion";
	}

	public String noAprobarMovilidadEvento() {

		MovilidadDocentesExterior mov = new MovilidadDocentesExterior();
		MovilidadDocentesExterior movAux = new MovilidadDocentesExterior();

		Long id = ((MovilidadDocentesExterior) (movilidadEventosSeleccionada)).getId();

		movAux = (MovilidadDocentesExterior) (movilidadEventosSeleccionada);

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral.obtenerListaObjetos("MovilidadDocentesExterior where id ='" + id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		personaActual = (Persona) sesion.getAttribute("persona");
		mov = (MovilidadDocentesExterior) listaMovilidad.get(0);
		if (personaActual != null) {
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacion("NO");
		mov.setComentariosFac(movAux.getComentariosFac());

		servicioGeneral.guardarObjeto(mov);

		String dirCorreoConfirmacion = personaActual.getEmail();

		correoActual = cargarPlantilla(68);// 42
		editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(), mov.getComentariosFac());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);

		sesion.removeAttribute("ManejadorAprobacionMovilidad");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacion";
	}

	public String noAprobarMovilidadVisitante() {

		MovilidadVisitanteExterior mov = new MovilidadVisitanteExterior();
		MovilidadVisitanteExterior movAux = new MovilidadVisitanteExterior();

		Long id = ((MovilidadVisitanteExterior) (movilidadVisitanteSeleccionada)).getId();
		movAux = (MovilidadVisitanteExterior) (movilidadVisitanteSeleccionada);

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral.obtenerListaObjetos("MovilidadVisitanteExterior where id ='" + id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		mov = (MovilidadVisitanteExterior) listaMovilidad.get(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		if (personaActual != null) {
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacion("NO");
		mov.setComentariosFac(movAux.getComentariosFac());
		// servicioMovilidad.updateMovilidad(mov);
		// enviarMail();
		servicioGeneral.guardarObjeto(mov);

		String dirCorreoConfirmacion = personaActual.getEmail();

		correoActual = cargarPlantilla(68);// 42
		editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(), mov.getComentariosFac());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);

		sesion.removeAttribute("ManejadorAprobacionMovilidad");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacion";
	}

	public String noAprobarMovilidadPosgrado() {

		MovilidadEstudiantesPosgrado mov = new MovilidadEstudiantesPosgrado();
		MovilidadEstudiantesPosgrado movAux = new MovilidadEstudiantesPosgrado();

		Long id = movilidadEstudiantesSeleccionada.getId();
		movAux = movilidadEstudiantesSeleccionada;

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral.obtenerListaObjetos("MovilidadEstudiantesPosgrado where id ='" + id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		mov = (MovilidadEstudiantesPosgrado) listaMovilidad.get(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		if (personaActual != null) {
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacion("NO");
		mov.setComentariosFac(movAux.getComentariosFac());
		// servicioMovilidad.updateMovilidad(mov);
		// enviarMail();
		servicioGeneral.guardarObjeto(mov);

		String dirCorreoConfirmacion = personaActual.getEmail();

		correoActual = cargarPlantilla(68);// 42
		editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(), mov.getComentariosFac());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);

		sesion.removeAttribute("ManejadorAprobacionMovilidad");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacion";
	}

	public String aprobarMovilidadPosgrado() {

		MovilidadEstudiantesPosgrado mov = new MovilidadEstudiantesPosgrado();
		MovilidadEstudiantesPosgrado movAux = new MovilidadEstudiantesPosgrado();

		Long id = (movilidadEstudiantesSeleccionada).getId();

		movAux = movilidadEstudiantesSeleccionada;

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral.obtenerListaObjetos("MovilidadEstudiantesPosgrado where id ='" + id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		mov = (MovilidadEstudiantesPosgrado) listaMovilidad.get(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		if (personaActual != null) {
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacion("SI");
		mov.setComentariosFac(movAux.getComentariosFac());
		// servicioMovilidad.updateMovilidad(mov);
		// enviarMail();
		servicioGeneral.guardarObjeto(mov);

		String dirCorreoConfirmacion = personaActual.getEmail();

		correoActual = cargarPlantilla(66);// 42
		editarCorreo(mov.getPersonaInv(), mov.getTipoMovilidad().getNombre(), mov.getId(), mov.getComentariosFac());
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		String dirCorreo = mov.getPersonaInv().getEmail();
		correo.adicionarDireccion(dirCorreo);
		correo.adicionarCopiaOculta(dirCorreoConfirmacion);
		correo.setAsunto(correoActual.getAsunto());
		correo.setCuerpo(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);

		List listaCorreoEncargado = new ArrayList();
		List listaParametroAux;
		listaParametroAux = new ArrayList();

		Dependencia dependenciaAux;
		dependenciaAux = new Dependencia();
		Persona personaEnvio = new Persona();

		personaEnvio = servicioPersona.obtenerInvestigadorInternoCompleto(mov.getPersonaInv().getId());

		InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
		dependenciaAux = servicioDependencia.obtenerDependencia(investigadorInterno.getId());

		/*
		 * correoActual = cargarPlantilla(86);
		 * 
		 * listaCorreoEncargado = this.servicioGeneral
		 * .obtenerObjetos("FROM Parametro WHERE nombre = 'R_MOVILIDAD'    AND DESCRIPCION= '"
		 * + dependenciaAux.getSede().getId() + "'");
		 * 
		 * 
		 * String correoEnvio ="sisii_nal@unal.edu.co"; Persona
		 * personaActualAux2 = new Persona();
		 * 
		 * if (listaCorreoEncargado != null && listaCorreoEncargado.size() > 0)
		 * { Parametro paActual = (Parametro) listaCorreoEncargado .get(0);
		 * String numeroDocumento = "0"; numeroDocumento = paActual.getValor();
		 * 
		 * IdPersona idAux = new IdPersona();
		 * idAux.setDocumento(numeroDocumento); idAux.setTipoDocumento("C");
		 * personaActualAux2 = servicioPersona.obtenerPersona(idAux); if
		 * (personaActualAux2.getEmail() != null &&
		 * !personaActualAux2.getEmail().equals("")) {
		 * 
		 * correoEnvio = personaActualAux2.getEmail(); } else { correoActual =
		 * cargarPlantilla(87); }
		 * 
		 * } else { correoActual = cargarPlantilla(87); }
		 * 
		 * editarCorreo(personaActualAux2,mov.getTipoMovilidad().getNombre(),
		 * mov.getId(),mov.getComentariosFac()); correo = new Correo();
		 * correo.setOrigen(Correo.CORREO_HERMES); dirCorreo = correoEnvio;
		 * correo.adicionarDireccion(dirCorreo);
		 * correo.adicionarCopiaOculta(dirCorreo);
		 * correo.setAsunto(correoActual.getAsunto());
		 * correo.setCuerpo(cuerpoCorreo); servicioCorreo.enviarCorreo(correo);
		 */

		sesion.removeAttribute("ManejadorAprobacionMovilidad");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacion";
	}

	public String noAprobarMovilidadDocenteArtes() {

		MovilidadDocentesArtes mov = new MovilidadDocentesArtes();
		MovilidadDocentesArtes movAux = new MovilidadDocentesArtes();

		Long id = movilidadDocenteArtesSeleccionada.getId();
		movAux = movilidadDocenteArtesSeleccionada;

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral.obtenerListaObjetos("MovilidadDocentesArtes where id ='" + id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		mov = (MovilidadDocentesArtes) listaMovilidad.get(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		if (personaActual != null) {
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacionFacultad("NO");
		mov.setDescripcionFacultad(movAux.getDescripcionFacultad());
		// servicioMovilidad.updateMovilidad(mov);
		// enviarMail();
		servicioGeneral.guardarObjeto(mov);

		String dirCorreoConfirmacion = personaActual.getEmail();

		correoActual = cargarPlantilla(68);// 42
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

		sesion.removeAttribute("ManejadorAprobacionMovilidad");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacion";
	}

	public String aprobarMovilidadDocenteArtes() {

		MovilidadDocentesArtes mov = new MovilidadDocentesArtes();
		MovilidadDocentesArtes movAux = new MovilidadDocentesArtes();

		Long id = (movilidadDocenteArtesSeleccionada).getId();

		movAux = movilidadDocenteArtesSeleccionada;

		List listaMovilidad;
		listaMovilidad = new ArrayList();

		listaMovilidad = servicioGeneral.obtenerListaObjetos("MovilidadDocentesArtes where id ='" + id + "'");

		if (listaMovilidad == null) {
			listaMovilidad = new ArrayList();
		}

		mov = (MovilidadDocentesArtes) listaMovilidad.get(0);
		personaActual = (Persona) sesion.getAttribute("persona");
		if (personaActual != null) {
			mov.setIdPersonaAprobacion(personaActual.getId().getDocumento());
			mov.setTipoIdPersonaAprobacion(personaActual.getId().getTipoDocumento());
		}
		mov.setAceptacionFacultad("SI");
		mov.setDescripcionFacultad(movAux.getDescripcionFacultad());
		// servicioMovilidad.updateMovilidad(mov);
		// enviarMail();
		servicioGeneral.guardarObjeto(mov);

		String dirCorreoConfirmacion = personaActual.getEmail();

		correoActual = cargarPlantilla(66);// 42
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

		List listaCorreoEncargado = new ArrayList();
		List listaParametroAux;
		listaParametroAux = new ArrayList();

		Dependencia dependenciaAux;
		dependenciaAux = new Dependencia();
		Persona personaEnvio = new Persona();

		personaEnvio = servicioPersona.obtenerInvestigadorInternoCompleto(mov.getPersonaInv().getId());

		InvestigadorInterno investigadorInterno = (InvestigadorInterno) personaEnvio;
		dependenciaAux = servicioDependencia.obtenerDependencia(investigadorInterno.getId());

		sesion.removeAttribute("ManejadorAprobacionMovilidad");
		sesion.removeAttribute("movilidad");
		return "listadoAprobacion";
	}

	public void enviarMail() {
		try {

			CorreoPlantilla cp = new CorreoPlantilla();
			// Johar Mantilla
			// se debe cambiar a la plantilla que corresponde
			CorreoPlantilla correoActual = (CorreoPlantilla) servicioGeneral.obtenerObjeto(cp, Long.valueOf(29));
			String strCorreo = correoActual.getCuerpo().replaceAll("<<fecha>>", Fecha.fechaActual());
			String cuerpoCorreo = strCorreo;

			String strAdjunto = correoActual.getNombreAdjunto();

			MovilidadVista mv = (MovilidadVista) sesion.getAttribute("movilidad");
			Persona persona = new Persona();
			IdPersona idpersona = new IdPersona(mv.getIdcreador().toString(), mv.getTipoDocumentoCreador());
			Grupo grupo = new Grupo();
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);

			Persona personaActual = (Persona) servicioPersona.obtenerPersona(idpersona);

			correo.adicionarDireccion(persona.getEmail());

			correo.setAsunto(correoActual.getAsunto());
			correo.setCuerpo(cuerpoCorreo);
			servicioCorreo.aplicarPlantillaCorreoAdjunto(correo, correoActual,
					System.getProperty("java.io.tmpdir") + File.separator + "adjunto.zip");
			FacesContext facesContext;
			facesContext = javax.faces.context.FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
			String r = request.getRealPath("plantillaCorreo");
			if (correoActual.getPlantilla() != null && !correoActual.getPlantilla().equals("")) {
				FileReader fr = new FileReader(r + File.separator + correoActual.getPlantilla());// "Plantilla
																									// e-mail
																									// consulta
																									// datos
																									// básicos
																									// evaluadores
																									// externos
																									// 2007.rtf");
				BufferedReader br = new BufferedReader(fr);
				String l = br.readLine();
				StringBuffer sb = new StringBuffer();
				while (l != null) {
					sb.append(l);
					l = br.readLine();
				}

				l = sb.toString();
				l = l.replaceAll("<<fecha>>", Fecha.fechaActual());
				br.close();
				fr.close();
				File a = File.createTempFile("adjunto", "rtf", new File(System.getProperty("java.io.tmpdir")));
				FileWriter fw = new FileWriter(a);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(l);
				bw.close();
				fw.close();
			}

			cuerpoCorreo = correoActual.getCuerpo().replaceAll("<<fecha>>", Fecha.fechaActual());

			strAdjunto = correoActual.getNombreAdjunto();

			servicioCorreo.enviarCorreo(correo);

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	public List getListaMovilidadesTesis() {
		return listaMovilidadesTesis;
	}

	public void setListaMovilidadesTesis(List listaMovilidadesTesis) {
		this.listaMovilidadesTesis = listaMovilidadesTesis;
	}

	public List getListaMovilidadesEvento() {
		return listaMovilidadesEvento;
	}

	public void setListaMovilidadesEvento(List listaMovilidadesEvento) {
		this.listaMovilidadesEvento = listaMovilidadesEvento;
	}

	public List getListaMovilidadesEstudiantePosgrado() {
		return listaMovilidadesEstudiantePosgrado;
	}

	public void setListaMovilidadesEstudiantePosgrado(List listaMovilidadesEstudiantePosgrado) {
		this.listaMovilidadesEstudiantePosgrado = listaMovilidadesEstudiantePosgrado;
	}

	public List getListaMovilidadesVisitante() {
		return listaMovilidadesVisitante;
	}

	public void setListaMovilidadesVisitante(List listaMovilidadesVisitante) {
		this.listaMovilidadesVisitante = listaMovilidadesVisitante;
	}

	public List getListaMovilidadesEstudiantePosgradoEventos() {
		return listaMovilidadesEstudiantePosgradoEventos;
	}

	public void setListaMovilidadesEstudiantePosgradoEventos(List listaMovilidadesEstudiantePosgradoEventos) {
		this.listaMovilidadesEstudiantePosgradoEventos = listaMovilidadesEstudiantePosgradoEventos;
	}

	public List getListaMovilidadesDocentesArtes() {
		return listaMovilidadesDocentesArtes;
	}

	public void setListaMovilidadesDocentesArtes(List listaMovilidadesDocentesArtes) {
		this.listaMovilidadesDocentesArtes = listaMovilidadesDocentesArtes;
	}

	public List getListaMovilidadesEstudiantesArtes() {
		return listaMovilidadesEstudiantesArtes;
	}

	public void setListaMovilidadesEstudiantesArtes(List listaMovilidadesEstudiantesArtes) {
		this.listaMovilidadesEstudiantesArtes = listaMovilidadesEstudiantesArtes;
	}

	public List getListaMovilidadesVisitantesArtes() {
		return listaMovilidadesVisitantesArtes;
	}

	public void setListaMovilidadesVisitantesArtes(List listaMovilidadesVisitantesArtes) {
		this.listaMovilidadesVisitantesArtes = listaMovilidadesVisitantesArtes;
	}

	public String getAceptacionDIB() {
		return aceptacionDIB;
	}

	public void setAceptacionDIB(String aceptacionDIB) {
		this.aceptacionDIB = aceptacionDIB;
	}

	public SelectItem[] getAprobacion() {
		return aprobacion;
	}

	public void setAprobacion(SelectItem[] aprobacion) {
		this.aprobacion = aprobacion;
	}

	public MovilidadVista getMovilidadVista() {
		return movilidadVista;
	}

	public void setMovilidadVista(MovilidadVista movilidadVista) {
		this.movilidadVista = movilidadVista;
	}

	public boolean isMostrarPonencia() {
		return mostrarPonencia;
	}

	public void setMostrarPonencia(boolean mostrarPonencia) {
		this.mostrarPonencia = mostrarPonencia;
	}

	public boolean isMostrarInscripcion() {
		return mostrarInscripcion;
	}

	public void setMostrarInscripcion(boolean mostrarInscripcion) {
		this.mostrarInscripcion = mostrarInscripcion;
	}

	public UIData getTablaActividades() {
		return tablaActividades;
	}

	public void setTablaActividades(UIData tablaActividades) {
		this.tablaActividades = tablaActividades;
	}

	public boolean isMostrarArchivo() {
		return mostrarArchivo;
	}

	public void setMostrarArchivo(boolean mostrarArchivo) {
		this.mostrarArchivo = mostrarArchivo;
	}

	public String reporteMovilidad() {
		return Navegacion.REPORTE;
	}

	public String getAceptacionFacultad() {
		return aceptacionFacultad;
	}

	public void setAceptacionFacultad(String aceptacionFacultad) {
		this.aceptacionFacultad = aceptacionFacultad;
	}

	public String getAceptacionFacultadVisitante() {
		return aceptacionFacultadVisitante;
	}

	public void setAceptacionFacultadVisitante(String aceptacionFacultadVisitante) {
		this.aceptacionFacultadVisitante = aceptacionFacultadVisitante;
	}

	public SelectItem[] getAprobacionVisitante() {
		return aprobacionVisitante;
	}

	public void setAprobacionVisitante(SelectItem[] aprobacionVisitante) {
		this.aprobacionVisitante = aprobacionVisitante;
	}

	public String getAceptacionFacultadDocentesArt() {
		return aceptacionFacultadDocentesArt;
	}

	public void setAceptacionFacultadDocentesArt(String aceptacionFacultadDocentesArt) {
		this.aceptacionFacultadDocentesArt = aceptacionFacultadDocentesArt;
	}

	public String getAceptacionFacultadEstudiantesArt() {
		return aceptacionFacultadEstudiantesArt;
	}

	public void setAceptacionFacultadEstudiantesArt(String aceptacionFacultadEstudiantesArt) {
		this.aceptacionFacultadEstudiantesArt = aceptacionFacultadEstudiantesArt;
	}

	public String getAceptacionFacultadVisitantesArt() {
		return aceptacionFacultadVisitantesArt;
	}

	public void setAceptacionFacultadVisitantesArt(String aceptacionFacultadVisitantesArt) {
		this.aceptacionFacultadVisitantesArt = aceptacionFacultadVisitantesArt;
	}

	public SelectItem[] getAprobacionDocentesArt() {
		return aprobacionDocentesArt;
	}

	public void setAprobacionDocentesArt(SelectItem[] aprobacionDocentesArt) {
		this.aprobacionDocentesArt = aprobacionDocentesArt;
	}

	public SelectItem[] getAprobacionEstudiantesArt() {
		return aprobacionEstudiantesArt;
	}

	public void setAprobacionEstudiantesArt(SelectItem[] aprobacionEstudiantesArt) {
		this.aprobacionEstudiantesArt = aprobacionEstudiantesArt;
	}

	public SelectItem[] getAprobacionVisitantesArt() {
		return aprobacionVisitantesArt;
	}

	public void setAprobacionVisitantesArt(SelectItem[] aprobacionVisitantesArt) {
		this.aprobacionVisitantesArt = aprobacionVisitantesArt;
	}

	public String getAceptacionFacultadPosgrado() {
		return aceptacionFacultadPosgrado;
	}

	public void setAceptacionFacultadPosgrado(String aceptacionFacultadPosgrado) {
		this.aceptacionFacultadPosgrado = aceptacionFacultadPosgrado;
	}

	public SelectItem[] getAprobacionPosgrado() {
		return aprobacionPosgrado;
	}

	public void setAprobacionPosgrado(SelectItem[] aprobacionPosgrado) {
		this.aprobacionPosgrado = aprobacionPosgrado;
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

	public void setArchivoMovilidadDESeleccionada(ArchivoMovilidadDE archivoMovilidadDESeleccionada) {
		this.archivoMovilidadDESeleccionada = archivoMovilidadDESeleccionada;
	}

	public ArchivoMovilidadDE ManejadorAprobacionMovilidadSede() {
		return archivoMovilidadDESeleccionada;
	}

	public void setArchivoMovilidadVSeleccionada(ArchivoMovilidadVE archivoMovilidadVSeleccionada) {
		this.archivoMovilidadVSeleccionada = archivoMovilidadVSeleccionada;
	}

	public ArchivoMovilidadVE getArchivoMovilidadVSeleccionada() {
		return archivoMovilidadVSeleccionada;
	}

	public void setArchivoMovilidadEPSeleccionada(ArchivoMovilidadEP archivoMovilidadEPSeleccionada) {
		ArchivoMovilidadEPSeleccionada = archivoMovilidadEPSeleccionada;
	}

	public ArchivoMovilidadEP getArchivoMovilidadEPSeleccionada() {
		return ArchivoMovilidadEPSeleccionada;
	}

	public MovilidadDocentesArtes getMovilidadDocenteArtesSeleccionada() {
		return movilidadDocenteArtesSeleccionada;
	}

	public void setMovilidadDocenteArtesSeleccionada(MovilidadDocentesArtes movilidadDocenteArtesSeleccionada) {
		this.movilidadDocenteArtesSeleccionada = movilidadDocenteArtesSeleccionada;
	}

	public void setListaMovilidadesVisitanteSede(List listaMovilidadesVisitanteSede) {
		this.listaMovilidadesVisitanteSede = listaMovilidadesVisitanteSede;
	}

	public List getListaMovilidadesVisitanteSede() {
		return listaMovilidadesVisitanteSede;
	}

	public void setListaMovilidadesEventoSede(List listaMovilidadesEventoSede) {
		this.listaMovilidadesEventoSede = listaMovilidadesEventoSede;
	}

	public List getListaMovilidadesEventoSede() {
		return listaMovilidadesEventoSede;
	}

	private List listaMovilidadesEventoSede;

	public List getListaMovilidadesEstudiantePosgradoSede() {
		return listaMovilidadesEstudiantePosgradoSede;
	}

	public void setListaMovilidadesEstudiantePosgradoSede(List listaMovilidadesEstudiantePosgradoSede) {
		this.listaMovilidadesEstudiantePosgradoSede = listaMovilidadesEstudiantePosgradoSede;
	}

	public List getListaMovilidadesEstudiantePosgradoEventosSede() {
		return listaMovilidadesEstudiantePosgradoEventosSede;
	}

	public void setListaMovilidadesEstudiantePosgradoEventosSede(List listaMovilidadesEstudiantePosgradoEventosSede) {
		this.listaMovilidadesEstudiantePosgradoEventosSede = listaMovilidadesEstudiantePosgradoEventosSede;
	}

	public List getListaMovilidadesDocentesArtesSede() {
		return listaMovilidadesDocentesArtesSede;
	}

	public void setListaMovilidadesDocentesArtesSede(List listaMovilidadesDocentesArtesSede) {
		this.listaMovilidadesDocentesArtesSede = listaMovilidadesDocentesArtesSede;
	}

	public List getListaMovilidadesEstudiantesArtesSede() {
		return listaMovilidadesEstudiantesArtesSede;
	}

	public void setListaMovilidadesEstudiantesArtesSede(List listaMovilidadesEstudiantesArtesSede) {
		this.listaMovilidadesEstudiantesArtesSede = listaMovilidadesEstudiantesArtesSede;
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

	public ArchivoMovilidadDE getArchivoMovilidadDESeleccionada() {
		return archivoMovilidadDESeleccionada;
	}

	public Movilidad getMovilidadCancelSeleccionada() {
		return movilidadCancelSeleccionada;
	}

	public void setMovilidadCancelSeleccionada(Movilidad movilidadCancelSeleccionada) {
		this.movilidadCancelSeleccionada = movilidadCancelSeleccionada;
	}

	public String getMotivoCancelacion() {
		return motivoCancelacion;
	}

	public void setMotivoCancelacion(String motivoCancelacion) {
		this.motivoCancelacion = motivoCancelacion;
	}

	public void cancelarMovilidad() {
		movilidadCancelSeleccionada.getSegMovFecha();
		if (movilidadCancelSeleccionada instanceof MovilidadVisitanteExterior) {
			((MovilidadVisitanteExterior) movilidadCancelSeleccionada)
					.setRazonesNoRealizacion("MOVILIDAD CANCELADA POR LA SEDE.\r\nMOTIVO: " + motivoCancelacion);
			((MovilidadVisitanteExterior) movilidadCancelSeleccionada).setEstado("C");
			((MovilidadVisitanteExterior) movilidadCancelSeleccionada).setRealizacionMovilidad("NO");
			((MovilidadVisitanteExterior) movilidadCancelSeleccionada).setSegMovFecha(getToday());
		} else if (movilidadCancelSeleccionada instanceof MovilidadDocentesExterior) {
			((MovilidadDocentesExterior) movilidadCancelSeleccionada)
					.setRazonesNoRealizacion("MOVILIDAD CANCELADA POR LA SEDE.\r\nMOTIVO: " + motivoCancelacion);
			((MovilidadDocentesExterior) movilidadCancelSeleccionada).setEstado("C");
			((MovilidadDocentesExterior) movilidadCancelSeleccionada).setRealizacionMovilidad("NO");
			((MovilidadDocentesExterior) movilidadCancelSeleccionada).setSegMovFecha(getToday());

		} else if (movilidadCancelSeleccionada instanceof MovilidadDocentesArtes) {
			((MovilidadDocentesArtes) movilidadCancelSeleccionada)
					.setRazonesNoRealizacion("MOVILIDAD CANCELADA POR LA SEDE.\r\nMOTIVO: " + motivoCancelacion);
			((MovilidadDocentesArtes) movilidadCancelSeleccionada).setEstado("C");
			((MovilidadDocentesArtes) movilidadCancelSeleccionada).setRealizacionMovilidad("NO");
			((MovilidadDocentesArtes) movilidadCancelSeleccionada).setSegMovFecha(getToday());

		} else if (movilidadCancelSeleccionada instanceof MovilidadEstudiantesPosgrado) {
			((MovilidadEstudiantesPosgrado) movilidadCancelSeleccionada)
					.setRazonesNoRealizacion("MOVILIDAD CANCELADA POR LA SEDE.\r\nMOTIVO: " + motivoCancelacion);
			((MovilidadEstudiantesPosgrado) movilidadCancelSeleccionada).setEstado("C");
			((MovilidadEstudiantesPosgrado) movilidadCancelSeleccionada).setRealizacionMovilidad("NO");
			((MovilidadEstudiantesPosgrado) movilidadCancelSeleccionada).setSegMovFecha(getToday());

		} else if (movilidadCancelSeleccionada instanceof MovilidadEstudiantesArtes) {
			((MovilidadEstudiantesArtes) movilidadCancelSeleccionada)
					.setRazonesNoRealizacion("MOVILIDAD CANCELADA POR LA SEDE.\r\nMOTIVO: " + motivoCancelacion);
			((MovilidadEstudiantesArtes) movilidadCancelSeleccionada).setEstado("C");
			((MovilidadEstudiantesArtes) movilidadCancelSeleccionada).setRealizacionMovilidad("NO");
			((MovilidadEstudiantesArtes) movilidadCancelSeleccionada).setSegMovFecha(getToday());
		}
		servicioGeneral.guardarObjeto(movilidadCancelSeleccionada);
	}

}
