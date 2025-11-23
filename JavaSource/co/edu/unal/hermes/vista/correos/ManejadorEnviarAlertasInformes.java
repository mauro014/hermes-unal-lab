package co.edu.unal.hermes.vista.correos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.seguimiento.AlertaProyecto;
import co.edu.unal.hermes.vista.ManejadorBase;


/**
 * The Class ManejadorEnviarAlertasMovilidades.
 *
 * @author Mauricio Amaya Ríos
 * @date 25/08/2015 Manejador para envio de alertas relacionados con informes
 *       que aún no han sido revisadas en la vicedecanatura de investigación.
 */
public class ManejadorEnviarAlertasInformes extends ManejadorBase {

	/** Listado coordiandores que no han revisado alertas. */
	private List<String[]> listaCoordinadores;

	/** Listado de vicedecanaturas que no han revisado alertas. */
	private List<String[]> listaVicedecanaturas;
	
	/** Cadena utilizada para las consultas de alertas de coordinadores */
	private final String CADENA_CONSULTA_ALERTAS_COORDINADOR =
	// Se descartan las que ya se cerraron.
	" (ap.estado <> 'C')"
			// Se valida que solo sean alertas de informes.
			+ "and (ap.mensaje like '%Informe de Avance%' or "
			+ "ap.mensaje like '%Informe Final%')"
			// Se suma uno al numero de notificaciones ya enviadas, y se
			// multiplica por 2 que son el numero de meses entre
			// notificaciones.
			// Si esta fecha es mas antigua que el dia de hoy entonces se
			// debe mandar una notificación.
			+ "and ADD_MONTHS(ap.fechaGenera, "
			+ "((ap.numeroNotificaciones + 1) * 2)) < SYSDATE";

	private final String CADENA_CONSULTAS_ALERTAS_VICEDENATURA =
	// Se asocia el investigador interno con la dependencia del informe.
	" ii.dependencia2 != null and ii.dependencia2.facultad.id = d.facultad.id "
			+ "and d.id = pi.dependenciaInforme "
			// Se valida que el informe este en el estado no revisado
			// y que sea informe final
			+ "and pi.estadoInforme.id= '2' and pi.tipoInforme.id = '2' "
			// Se suma uno al numero de notificaciones ya enviadas, y se
			// multiplica por 2 que son el numero de meses entre
			// notificaciones.
			// Si esta fecha es mas antigua que el dia de hoy entonces se
			// debe mandar una notificación.
			+ " and ADD_MONTHS(pi.fechaGeneracion, "
			+ "((pi.numeroNotificaciones + 1) * 2)) < SYSDATE ";

	/** Plantilla utilizada para el envio de alertas de coordinador */
	private final Long PLANTILLA_COORDINADOR = 249L;

	/** Plantilla utilizada para el envio de alertas de coordinador */
	private final Long PLANTILLA_VICEDECANATURA = 250L;

	public ManejadorEnviarAlertasInformes() {

		// Se cargan los coordinadores con alertas pendientes.
		listaCoordinadores = generarListadoCoordinadores();

		// Se cargan las Vicedecanaturas con alertas pendientes.
		listaVicedecanaturas = generarListadoVicedenatura();

	}

	/**
	 * Se cargan los coordinadores con la información necesaria para las
	 * alertas.
	 * 
	 * @author Mauricio Amaya Ríos
	 * @since 26-08-2015
	 * @return listado de coordinadores ordenados en un listado de cadenas
	 */
	public List<String[]> generarListadoCoordinadores() {
		// Se cargan los coordinadores con alertas pendientes.
		// Se guarda en genero el numero de alertas antiguas sin resolver.
		String selectAdicional = ", #genero count(*) ";
		String fromAdicional = ", AlertaProyecto ap ";
		String whereAdicional =
		// Se asocia la alerta con la persona de la consulta
		" and p.id.documento = ap.asesor.id.documento "
				+ "and p.id.tipoDocumento = ap.asesor.id.tipoDocumento "
				+ " and " + CADENA_CONSULTA_ALERTAS_COORDINADOR
				// Se agrupa por numero de alertas para saber cuantas
				// tiene aun sin revisar antigüas
				+ " group by p.id.documento";
		return generarListadoAlertas(selectAdicional, fromAdicional,
				whereAdicional, "C");
	}

	/**
	 * Se cargan los coordinadores con la información necesaria para las
	 * alertas.
	 * 
	 * @author Mauricio Amaya Ríos
	 * @since 26-08-2015
	 * @return listado de coordinadores ordenados en un listado de cadenas
	 */
	public List<String[]> generarListadoVicedenatura() {
		// Se cargan los coordinadores con alertas pendientes.
		// Se guarda en genero el numero de alertas antiguas sin resolver.
		String selectAdicional = ", #genero count(*) ";
		String fromAdicional = ", ProyectoInforme pi, Dependencia d ";
		String whereAdicional = " and " +
		// Se usa consulta genérica.
				CADENA_CONSULTAS_ALERTAS_VICEDENATURA
				// Se agrupa por numero de alertas para saber cuantas
				// tiene aun sin revisar antigüas
				+ " group by p.id.documento ";
		return generarListadoAlertas(selectAdicional, fromAdicional,
				whereAdicional, "AF");
	}

	/**
	 * Se realiza la consulta en la base de datos en donde se traiga la
	 * información de personas con cierto rol asociado y de acuerdo al
	 * fromAdicional y el whereAdicional. La consulta por defecto tiene un p
	 * Persona y un ii para InvestigadorInterno que pueden ser usados en las
	 * variables adicionales.
	 * 
	 * @author Mauricio Amaya Ríos
	 * @since 25-08-2015
	 * @return lista de coordinadores ordenados en un array de Strings para que
	 *         pueda ser usado en la vista.
	 */
	public List<String[]> generarListadoAlertas(String selectAdional,
			String fromAdicional, String whereAdicional, String rol) {

		// Se realiza consulta en la base de datos.
		List<Persona> lista = servicioGeneral.obtenerObjetosLimitado(
				Persona.class, "select " + "#nombre1 max(p.nombre1), "
						+ "#nombre2 max(p.nombre2), "
						+ "#apellido1 max(p.apellido1), "
						+ "#apellido2 max(p.apellido2), "
						+ "#id.documento max(p.id.documento), "
						+ "#id.tipoDocumento max(p.id.tipoDocumento),"
						+ "#email max(p.email) " + selectAdional
						+ "from Persona p JOIN p.roles r, "
						+ "InvestigadorInterno ii " + fromAdicional + " "
						+ "where r.id = '" + rol + "' and "
						+ "ii.id.documento = p.id.documento and "
						+ "ii.id.tipoDocumento = p.id.tipoDocumento "
						+ whereAdicional);

		// Se prepara iterator para recorrer toda la lista del resultado.
		Iterator<Persona> i = lista.iterator();

		// Se crea lista para organizar los resultados en un array de String.
		List<String[]> listaCoordinadores = new ArrayList<String[]>();

		while (i.hasNext()) {

			String[] persona = new String[6];

			// Se recorre la lista y se crea array de String según los
			// resultados
			Persona p = i.next();
			persona[0] = p.getNombreCompletoMinusculas();
			persona[1] = p.getGenero();
			persona[2] = p.getId().getDocumento();
			persona[3] = "true";
			persona[4] = p.getId().getTipoDocumento();
			persona[5] = p.getEmail();

			// Se agrega array de String a la lista para gestión en la vista.
			listaCoordinadores.add(persona);

		}
		return listaCoordinadores;
	}

	/**
	 * Se realiza el envio de las alertas de acuerdo a las personas
	 * seleccionadas y se actualiza el numero de notificación en la base de
	 * datos-
	 * 
	 * @author Mauricio Amaya Ríos
	 * @since 25-08-2015
	 */
	public void enviarNotificacionesCoordinadores() {
		Iterator<String[]> i = listaCoordinadores.iterator();
		while (i.hasNext()) {
			String[] alertaInforme = i.next();
			// Se valida que se halla seleccionado
			if (alertaInforme[3].equals("true")) {
				// Se obtienen las alertas de cada persona seleccionada.
				List<AlertaProyecto> alertasProyecto = servicioGeneral
						.obtenerObjetosLimitado(AlertaProyecto.class,
								"select #id ap.id,"
										// En el estado se guarda el numero de
										// solicitud.
										+ "#estado ap.solicitud.id, "
										// En estadoMostrar se guarda el id y
										// nombre
										// del proyecto para mostrar.
										+ "#estadoMostrar ap.proyecto.id || "
										+ "' - ' || ap.proyecto.nombre,"
										+ "#fechaGenera ap.fechaGenera, "
										+ "#mensaje ap.mensaje "
										+ "from AlertaProyecto ap where "
										// Se hace la relación entre la persona
										// y la alerta.
										+ " ap.asesor.id.documento = '"
										+ alertaInforme[2] + "' "
										+ " and ap.asesor.id.tipoDocumento ='"
										+ alertaInforme[4] + "' and"
										// Se usa la misma consulta para los
										// filtros.
										+ CADENA_CONSULTA_ALERTAS_COORDINADOR);
				// Se verifica que la consulta haya tenido resultados.
				if (alertasProyecto != null && alertasProyecto.size() > 0) {
					String cadenaAlertas = "<table border='1' cellspacing='0' cellpadding='3' >"
							+ "<tr><th style='text-align: center;'>Solicitud</th>"
							+ "<th style='text-align: center;'>Tipo informe</th>"
							+ "<th style='text-align: center;'>Nombre proyecto</th></tr>";
					Iterator<AlertaProyecto> j = alertasProyecto.iterator();
					// Se itera sobre el listado de alertas devueltas.
					while (j.hasNext()) {
						AlertaProyecto alertaProyecto = j.next();
						// Se agrega información de la alerta según lo devuelto
						// en la consulta.
						cadenaAlertas += "<tr><td style='text-align: center;'>"
								+ alertaProyecto.getEstado() + "</td>"
								+ "<td  style='text-align: center;'>"
								+ alertaProyecto.getMensaje()
								+ "</td><td>"
								+ alertaProyecto.getEstadoMostrar() + "</td></tr>";
					}
					cadenaAlertas += "</table>";
					boolean error = false;
					if (cadenaAlertas.length() > 0) {
						// Cargar plantilla para el envío de alertas.
						CorreoPlantilla correoPlantilla = cargarPlantillaCorreo(PLANTILLA_COORDINADOR);
						Correo correo = new Correo();
						String asunto = correoPlantilla.getAsunto();
						correo.setAsunto(asunto);
						correo.setOrigen(Correo.CORREO_HERMES);
						correo.adicionarDireccion( alertaInforme[5] );
						//correo.adicionarDireccion(Correo.CORREO_HERMES);
						String cuerpoCorreo = correoPlantilla.getCuerpo()
								.replaceAll("<<INVESTIGADOR>>",
										alertaInforme[0]);
						cuerpoCorreo = cuerpoCorreo.replaceAll("<<INFORMES>>",
								cadenaAlertas);
						correo.setCuerpo(cuerpoCorreo);
						// Si se envia correo se realiza la actualización en la
						// base de datos del numero de notificaciones enviada.
						if (servicioCorreo.enviarCorreo(correo,true)) {
							Iterator<AlertaProyecto> k = alertasProyecto
									.iterator();
							try {
							while (k.hasNext() && !error) {
								AlertaProyecto alertaProyecto = k.next();
								String sql = "UPDATE HER_ALERTA_PROYECTO "
										+ " SET "
										+ "ALPR_NUMERO_NOTIFICACION = ALPR_NUMERO_NOTIFICACION + 1,"
										+ "ALPR_FECHA_NOTIFICACION = SYSDATE "
										+ "WHERE ALPR_ID = '"
										+ alertaProyecto.getId() + "'";
								servicioGeneral.ejecutarSentencia(sql);
							} 
							}catch(Exception e) {
								e.printStackTrace();
							}
						} else {
							error = true;
						}
					}
					if (error) {
						mostrarMensaje(
								"Ha ocurrido un error al enviar los mensajes",
								null, FacesMessage.SEVERITY_ERROR);

					} else {
						mostrarMensaje(
								"Las alertas han sido enviadas a los coordinadores.",
								null, FacesMessage.SEVERITY_INFO);
					}
				}
			}
		}
		// Se cargan los coordinadores con alertas pendientes.
		listaCoordinadores = generarListadoCoordinadores();
	}

	/**
	 * Se realiza el envio de las alertas de acuerdo a las personas
	 * seleccionadas en el listado de vicedecanatura y se actualiza el numero de
	 * notificación en la base de datos-
	 * 
	 * @author Mauricio Amaya Ríos
	 * @since 25-08-2015
	 * @return lista de personas de la vicedenat ordenados en un array de
	 *         Strings para que pueda ser usado en la vista.
	 */
	public void enviarNotificacionesVicedecanatura() {
		Iterator<String[]> i = listaVicedecanaturas.iterator();
		while (i.hasNext()) {
			String[] vicedecanaturaInforme = i.next();
			// Se valida que se halla seleccionado
			if (vicedecanaturaInforme[3].equals("true")) {
				// Se obtienen las alertas de cada persona seleccionada.
				List<ProyectoInforme> informes = servicioGeneral
						.obtenerObjetosLimitado(
								ProyectoInforme.class,
								"select #id pi.id,"
										+ "#avanceResumen pi.proyecto.id || "
										+ "' - ' || pi.proyecto.nombre"
										+ " from ProyectoInforme pi, Dependencia d, "
										+ "InvestigadorInterno ii " + "where "
										+ "ii.id.documento = '"
										+ vicedecanaturaInforme[2] + "' and "
										+ "ii.id.tipoDocumento = '"
										+ vicedecanaturaInforme[4] + "' and "
										+ CADENA_CONSULTAS_ALERTAS_VICEDENATURA);
				// Se verifica que la consulta haya tenido resultados.
				if (informes != null && informes.size() > 0) {
					String cadenaInformes = "<table border='1' cellspacing='0' cellpadding='3' >"
							+ "<tr><th style='text-align: center;'>Id informe</th>"
							+ "<th style='text-align: center;'>Tipo informe</th>"
							+ "<th style='text-align: center;'>Nombre proyecto</th></tr>";
					Iterator<ProyectoInforme> j = informes.iterator();
					// Se itera sobre el listado de informes devueltos.
					while (j.hasNext()) {
						ProyectoInforme informe = j.next();
						// Se agrega información de la alerta según lo devuelto
						// en la consulta.
						cadenaInformes += "<tr><td style='text-align: center;'>"
								+ informe.getId() + "</td>"
								+ "<td  style='text-align: center;'>"
								+ "Informe final"
								+ "</td><td>"
								+ informe.getAvanceResumen()
								+ "</td></tr>";
					}
					cadenaInformes += "</table>";
					boolean error = false;
					if (cadenaInformes.length() > 0) {
						// Cargar plantilla para el envío de alertas.
						CorreoPlantilla correoPlantilla = cargarPlantillaCorreo(PLANTILLA_VICEDECANATURA);
						Correo correo = new Correo();
						String asunto = correoPlantilla.getAsunto();
						correo.setAsunto(asunto);
						correo.setOrigen(Correo.CORREO_HERMES);
						correo.adicionarDireccion( vicedecanaturaInforme[5] );
						//correo.adicionarDireccion(Correo.CORREO_HERMES);
						String cuerpoCorreo = correoPlantilla.getCuerpo()
								.replaceAll("<<INVESTIGADOR>>",
										vicedecanaturaInforme[0]);
						cuerpoCorreo = cuerpoCorreo.replaceAll("<<INFORMES>>",
								cadenaInformes);
						correo.setCuerpo(cuerpoCorreo);
						// Si se envia correo se realiza la actualización en la
						// base de datos del numero de notificaciones enviada.
						if (servicioCorreo.enviarCorreo(correo,true)) {
							Iterator<ProyectoInforme> k = informes.iterator();
							while (k.hasNext() && !error) {
								ProyectoInforme proyectoInforme = k.next();
								String sql = "UPDATE HER_PROYECTO_INFORME "
										+ " SET "
										+ "PIN_NUMERO_NOTIFICACION = PIN_NUMERO_NOTIFICACION + 1,"
										+ "PIN_FECHA_NOTIFICACION = SYSDATE "
										+ "WHERE PIN_ID = '"
										+ proyectoInforme.getId() + "'"
										+ " and TO_DATE(PIN_FECHA_NOTIFICACION, 'dd/mm/yyyy') < "
										+ "TO_DATE(SYSDATE, 'dd/mm/yyyy')";
								servicioGeneral.ejecutarSentencia(sql);
							}
						} else {
							error = true;
						}
					}
				}
			}
		}

		// Se cargan las Vicedecanaturas con alertas pendientes.
		listaVicedecanaturas = generarListadoVicedenatura();
	}

	/**
	 * @author Mauricio Amaya Ríos
	 * @since 31-08-2015
	 * 
	 * @param msg
	 *            con mensaje que sera enviado.
	 * @param component
	 *            el componente al que esta asociado el mensaje para que lo
	 *            bloquee.
	 * @param el
	 *            tipo de rror para que este se muestre si es error, informativo
	 *            o alerta.
	 */
	protected void mostrarMensaje(String msg, UIComponent component, Severity s) {
		FacesMessage message = new FacesMessage(s, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		if (component == null) {
			context.addMessage(null, message);
		} else {
			context.addMessage(component.getClientId(context), message);
		}
	}

	/**
	 * Cargar correo plantilla de correo segun parametro enviado.
	 *
	 * @author Mauricio Amaya Ríos
	 * @param tipo
	 *            the tipo
	 * @return PlantillaCorreo con información
	 * @date 17/04/2015
	 */
	private CorreoPlantilla cargarPlantillaCorreo(Long nPlantilla) {
		if (nPlantilla != 0L) {
			return servicioCorreo.obtenerPlantillaCorreoCompleta(nPlantilla);
		} else
			return null;
	}

	/**
	 * Gets numero alertas coordinadores.
	 *
	 * @return numero alertas coordinadores
	 */
	public Integer getNumeroAlertasCoordinadores() {
		if (listaCoordinadores != null) {
			return listaCoordinadores.size();
		} else
			return 0;
	}

	/**
	 * Gets numero alertas vicedecanatura.
	 *
	 * @return numero alertas vicedecanatura
	 */
	public Integer getNumeroAlertasVicedecanatura() {
		if (listaVicedecanaturas != null) {
			return listaVicedecanaturas.size();
		} else
			return 0;
	}

	/**
	 * Gets lista coordinadores.
	 *
	 * @return lista coordinadores
	 */
	public List<String[]> getListaCoordinadores() {
		return listaCoordinadores;
	}

	/**
	 * Gets lista vicedecanaturas.
	 *
	 * @return lista vicedecanaturas
	 */
	public List<String[]> getListaVicedecanaturas() {
		return listaVicedecanaturas;
	}

}
