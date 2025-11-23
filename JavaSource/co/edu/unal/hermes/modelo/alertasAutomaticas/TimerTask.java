package co.edu.unal.hermes.modelo.alertasAutomaticas;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.hibernate.HibernateException;
import org.springframework.scheduling.timer.ScheduledTimerTask;

import co.edu.unal.hermes.bd.imp.GeneralDAOHibernate;
import co.edu.unal.hermes.modelo.CorreoDB;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.LogAlertasAutomaticas;
import co.edu.unal.hermes.modelo.MovilidadAlertaAutomatica;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Requerimiento;
import co.edu.unal.hermes.modelo.SemilleroInforme;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.correo.MyAuthenticator;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioActividadEquipoInterfazAlerta;
import co.edu.unal.hermes.modelo.servicioGeneral.ServicioGeneral;

/**
 * No need to implement any interface
 */
public class TimerTask extends GeneralDAOHibernate {

	private static final Long CORREO_PLANTILLA_ALERTA_ACT_LAB = 306L;
	private static final Long CORREO_PLANTILLA_ALERTA_REQ = 308L;
	private static final Long CORREO_PLANTILLA_ALERTA_INFORME_SEMILLERO = 362L;
	private static final Long CORREO_PLANTILLA_ALERTA_MOVILIDADES = 403L;

	public static final String CORREO_NOMBRE_PROFESOR = "<<NOMBRE_PROFESOR>>";
	public static final String CORREO_NOMBRE_EQUIPO = "<<NOMBRE_EQUIPO>>";
	public static final String CORREO_PLACA_EQUIPO = "<<PLACA_EQUIPO>>";
	public static final String CORREO_NOMBRE_LABORATORIO = "<<NOMBRE_LABORATORIO>>";
	public static final String CORREO_ESTADO_ACTIVIDAD = "<<ESTADO_ACTIVIDAD>>";
	public static final String CORREO_NOMBRE_TIPO_ACTIVIDAD = "<<NOMBRE_TIPO_ACTIVIDAD>>";
	public static final String CORREO_FECHA_ACTIVIDAD = "<<FECHA_ACTIVIDAD>>";

	// CORREO

	private static final String SERVIDOR = "172.217.203.28";
	private static final String PUERTO = "25";
	private static final String SENDER_NAME = "[No Responder] - Alertas y Notificaciones Sistema Hermes";
	private static final String PIE_CONTENIDO = "\n\n\n________________________________________________\n"
			+ "HERMES - Sistema de Información de la Investigación\n"
			+ "Universidad Nacional de Colombia\n"
			+ "Ciudad Universitaria\n"
			+ "Bogotá D.C.- Colombia\n" + "(+57)(1) 3165000 Ext. 11111\n\n"
			+ "Le recomendamos calificar el servicio que le brindó el Sistema de Información Hermes en el enlace http://www.hermes.unal.edu.co/pages/Requerimiento/EncuestaAtencionUsuario.xhtml. Su opinión es muy importante para nosotros.\n\n"
			+ "Así mismo puede consultar nuestros instructivos directamente haciendo clic en el siguiente enlace 'http://www.hermes.unal.edu.co/pages/html/descargas/index.xhtml'";
	
	private static final boolean DEBUG = false;
//	private static final String SENDER_NAME_SOL = "[No Responder] - Alertas y Notificaciones Sistema Hermes";

	private int correosEnviados = 0;
	private long horaPrimerEnvio;
	private long horaUltimoEnvio;
	private static final int maximoEnviosPorMinuto = 30;
	private CorreoDB mensajeNoEnviado;

	private static final String HEADER_HTML = "<!DOCTYPE html>" + "<head>" + "<meta charset='utf-8'>"
			+ "<title>Correo Electrónico</title>" + "</head>" + "<body margin='0' style='margin:0'>"
			+ "<table cellpadding='0' cellspacing='0' width='100%' border='0' bgcolor='#ffffff' style='background-color:#ffffff'>"
			+ "<tbody>" + "<tr>" + "<td width='134' height='81' rowspan='3' valign='top'>"
			+ "<img src='http://unal.edu.co/fileadmin/templates/images/escudo_unal_mail_2.png' alt='escudo Universidad Nacional de Colombia' width='134' height='81'>"
			+ "</td>" + "<td bgcolor='#94b43b' height='5' style='background-color:#94b43b;line-height:0'>" + "</td>"
			+ "</tr>" + "<tr>" + "<td bgcolor='#444444' height='60'>"
			+ "<div style='float:right;text-align:right;margin-right:15px;color:#fff;font-size:14px;font-family:Georgia,serif;line-height:14px;max-height:60px;overflow:hidden'>"
			+ "VICERRECTORÍA DE INVESTIGACIÓN" + "</div>" + "</td>" + "</tr>" + "<tr><td height='17'>&nbsp;</td></tr>"
			+ "</tbody>" + "</table>";
	private static final String FOOTER_HTML = "<div dir='ltr'><br>______________________________<wbr>______________________<div dir='ltr'><b>Sistema de Información Hermes</b></div><div dir='ltr'>"
			+ "Universidad Nacional de Colombia</div><div dir='ltr'><b>Página web:</b>&nbsp;<a href='http://www.hermes.unal.edu.co' target='_blank'>www.hermes.unal.edu.co</a></div><div dir='ltr'><b>Correo electrónico:</b>"
			+ "<a href='mailto:hermes@unal.edu.co' target='_blank'>hermes@unal.edu.co</a></div><div dir='ltr'><b>Twitter:</b> <a href='https://twitter.com/Hermes_UN' target='_blank'>"
			+ "https://twitter.com/Hermes_UN</a></div></div>"
			+ "<div dir='ltr'><div>Califique nuestro servicio<b><i><font size='2'><span style='font-family:arial,sans-serif;font-style:normal;line-height:normal'><span style='font-weight:normal'>&nbsp;</span><font color='#0000ff'>"
			+ "<a href='http://www.hermes.unal.edu.co/pages/Requerimiento/EncuestaAtencionUsuario.xhtml' target='_blank'><font color='#0000ff'>aquí</font></a>'</font></span><span style='font-family:arial,sans-serif;font-style:normal;font-weight:normal;line-height:normal'>."
			+ "</span></font></i></b><br><span style='font-family:arial,helvetica,sans-serif'><span style='font-size:10pt'>Lo invitamos a&nbsp;"
			+ "<a href='http://www.hermes.unal.edu.co/pages/html/descargas/index.xhtml' target='_blank'><b><font color='#0000ff'>consultar nuestros Instructivos</font></b></a>.</span>"
			+ "</span></div>"
			+ "<div><span style='font-family:arial,helvetica,sans-serif'><span style='font-size:10pt'><br></span></span></div><div><span class='im'><span><div>"
			+ "<font face='arial, helvetica, sans-serif' size='1' color='#990000'><b>Aviso legal:</b>&nbsp;"
			+ "El contenido de este mensaje y los archivos adjuntos son confidenciales y de uso exclusivo de la Universidad Nacional de Colombia. Se encuentran dirigidos sólo para el uso del destinatario al cual van enviados. La reproducción, lectura y/o copia se encuentran prohibidas a cualquier persona diferente a este y puede ser ilegal. Si usted lo ha recibido por error, infórmenos y elimínelo de su correo. Los Datos Personales serán tratados conforme a la Ley 1581 de 2012 y a la Política de Datos Personales que podrá consultar en la página web&nbsp;"
			+ "<a href='http://www.unal.edu.co/' style='color:rgb(17,85,204)' target='_blank'>www.unal.edu.co</a>. Las opiniones, informaciones, conclusiones y cualquier otro tipo de dato contenido en este correo electrónico, no relacionados con la actividad de la Universidad Nacional de Colombia, se entenderá como personales y de ninguna manera son avaladas por la Universidad.</font></div></span></span></div></div></body></html>";

	private static ScheduledTimerTask timer;
	
	public void execute() {
		try {
			if (esAmbienteProduccion()) {
				enviarAlertasMovilidadesFinalizadas();
//				enviarAlertasActividadesLaboratorios();
//				enviarAlertaInicioTramite();
//				enviarAlertaInformesSemilleros();
			} else {
				System.out.println("ERROR: Método TimerTask.execute no se ejecuta por ser ambiente de pruebas");
			}
		} catch (HibernateException e) {
			System.out.println("ERROR: Método execute HibernateException");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR: Método execute SQLException");
			e.printStackTrace();
		}
	}
	
	public Correo configurarCorreoAlertasMovilidades(MovilidadAlertaAutomatica mov) {
		
		CorreoPlantilla correoPlantilla = obtenerPlantillaCorreoCompleta(CORREO_PLANTILLA_ALERTA_MOVILIDADES);
		Correo correo = new Correo();
		String cuerpo = correoPlantilla.getCuerpo();
		String asunto = correoPlantilla.getAsunto();
		
		try {
			correo.setOrigen(Correo.CORREO_HERMES);
			correo.adicionarDireccion(mov.getCorreoDocente());
			if(!esNulo(mov.getTipoMovilidadTabla()) && mov.getTipoMovilidadTabla().equals("HER_MOVILIDAD_ESTUDIANTE_POS"))
				correo.adicionarDireccion(mov.getCorreoEstudiante());
//			correo.adicionarCopiaOculta("cazapatamar@unal.edu.co");

			asunto = asunto.replaceAll("<<ID_MOVILIDAD>>", mov.getId().toString());

			cuerpo = cuerpo.replaceAll("<<ID_MOVILIDAD>>", mov.getId().toString());
			cuerpo = cuerpo.replaceAll("<<NUM_MESES>>", String.valueOf(mov.getNumeroMesesDesdeFinMovilidad()));
			cuerpo = cuerpo.replaceAll("<<FECHA_FIN_MOVILIDAD>>", new SimpleDateFormat("dd/MM/yyyy").format(mov.getFechafinal()));

			correo.setAsunto(asunto);
			correo.setCuerpo(cuerpo);
		} catch (Exception e) {
			return null;
		}
		
		return correo;
	}
	
	public void enviarAlertasMovilidadesFinalizadas() {
		System.out.println("enviarAlertasMovilidadesFinalizadas ++++++++++++++++++++++++++++++++++ ");
		List<MovilidadAlertaAutomatica> movilidades = consultaMovilidadesPendienteInforme();
		int correosEnviados = 0;
		
		for (MovilidadAlertaAutomatica mov : movilidades) {
			System.out.println("Id: " + mov.getId() + " - Meses: " + mov.getNumeroMesesDesdeFinMovilidad());
			
			Correo correo = configurarCorreoAlertasMovilidades(mov);
			
			if (!esNulo(correo) && true) {
				if (enviarCorreo(correo, 1, true)) {
					//actualizarInfoAlertaActividadesLab(Long.parseLong(actividad.getIdActividad()), 1L, cuerpo);
					LogAlertasAutomaticas logAlertaMov = new LogAlertasAutomaticas();
					logAlertaMov.setIdObjeto(mov.getId().toString());
					logAlertaMov.setTipoObjeto("MOVILIDADES");
					logAlertaMov.setValorObjeto(String.valueOf(mov.getNumeroMesesDesdeFinMovilidad()));
					logAlertaMov.setFecha(mov.getFechafinal());
					logAlertaMov.setDestinatarios(cadenaSeparadaComa(eliminarDuplicados(correo.getDirecciones())));
					logAlertaMov.setCuerpoCorreo(correo.getCuerpo());
					insertarInfoAlertaMovilidades(logAlertaMov);
					
					System.out.println("CORREO ENVIADO EXITOSAMENTE A : " + mov.getCorreoDocente() + " - Cuerpo: " + correo.getCuerpo() + "\n");
					correosEnviados++;
				}
			} else {
				System.out.println("ALERTA YA ENVIADA PARA LA MOVILIDAD ID: " + mov.getId());
			}
		}
		System.out.println("CORREOS ENVIADOS: " + correosEnviados);
	}
	
	public void enviarAlertasActividadesLaboratorios() {
		try {
			List<LaboratorioActividadEquipoInterfazAlerta> actividadesAlertas = obtenerActividadesCandidatasAlertas(10);
			CorreoPlantilla correoPlantilla = obtenerPlantillaCorreoCompleta(CORREO_PLANTILLA_ALERTA_ACT_LAB);

			int totalMensajesAEnviar = 0;
			int correosEnviados = 0;

			if (!esListaVacia(actividadesAlertas)) {
				Iterator<LaboratorioActividadEquipoInterfazAlerta> i = actividadesAlertas.iterator();
				while (i.hasNext()) {
					LaboratorioActividadEquipoInterfazAlerta actividad = i.next();

					Correo correo = new Correo();
					String cuerpo = correoPlantilla.getCuerpo();
					String asunto = correoPlantilla.getAsunto();

					correo.setOrigen(Correo.CORREO_HERMES);
					correo.adicionarDireccion(actividad.getEmailCoordinador());
					correo.adicionarCopiaOculta(Correo.CORREO_HERMES);

					asunto = asunto.replaceAll(CORREO_NOMBRE_TIPO_ACTIVIDAD, actividad.getNombreTipoActividad());
					asunto = asunto.replaceAll(CORREO_NOMBRE_EQUIPO, actividad.getNombreEquipo());
					asunto = asunto.replaceAll(CORREO_PLACA_EQUIPO, actividad.getPlacaEquipo());

					cuerpo = cuerpo.replaceAll(CORREO_NOMBRE_PROFESOR, actividad.getNombreCoordinador());
					cuerpo = cuerpo.replaceAll(CORREO_NOMBRE_EQUIPO, actividad.getNombreEquipo());
					cuerpo = cuerpo.replaceAll(CORREO_PLACA_EQUIPO, actividad.getPlacaEquipo());
					cuerpo = cuerpo.replaceAll(CORREO_NOMBRE_LABORATORIO, actividad.getNombreLaboratorio());
					cuerpo = cuerpo.replaceAll(CORREO_ESTADO_ACTIVIDAD, actividad.getEstadoActividad());
					cuerpo = cuerpo.replaceAll(CORREO_NOMBRE_TIPO_ACTIVIDAD, actividad.getNombreTipoActividad());
					cuerpo = cuerpo.replaceAll(CORREO_FECHA_ACTIVIDAD, actividad.getFechaActividad());

					correo.setAsunto(asunto);
					correo.setCuerpo(cuerpo);

					if (actividad.getEmailsOtros() != null)
						correo.setCopias(actividad.getEmailsOtros());

					totalMensajesAEnviar++;

					if (actividad.getAlertaEnviada().equals("0")) {
						if (enviarCorreo(correo, 1, true)) {
							actualizarInfoAlertaActividadesLab(Long.parseLong(actividad.getIdActividad()), 1L, cuerpo);
							System.out.println("CORREO ENVIADO EXITOSAMENTE A : " + actividad.getEmailCoordinador()
									+ " - totalMensajesAEnviar: " + totalMensajesAEnviar);
						}
					} else {
						System.out.println("ALERTA YA ENVIADA PARA LA ACTIVIDAD ID: " + actividad.getIdActividad());
					}
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR: Método TimerTask.enviarAlertasActividadesLaboratorios");
			e.printStackTrace();
		}
	}

	private void enviarAlertaInformesSemilleros() {
		List<SemilleroInforme> actividadesAlertas = obtenerInformesSemillerosAlertas();
		CorreoPlantilla correoPlantilla = obtenerPlantillaCorreoCompleta(CORREO_PLANTILLA_ALERTA_INFORME_SEMILLERO);
		if (!esListaVacia(actividadesAlertas)) {
			Iterator<SemilleroInforme> i = actividadesAlertas.iterator();
			while (i.hasNext()) {
				SemilleroInforme actividad = i.next();
				String cuerpo = correoPlantilla.getCuerpo();
				cuerpo = cuerpo.replaceAll("<<NOMBRE>>", actividad.getSemillero().getNombre());
				cuerpo = cuerpo.replaceAll("<<ID>>", actividad.getSemillero().getId().toString());
				cuerpo = cuerpo.replaceAll("<<FECHA>>",
						new SimpleDateFormat("dd/MM/yyyy").format(actividad.getFechaCompromiso()));
				String asunto = correoPlantilla.getAsunto();
				asunto = asunto.replaceAll("<<ID>>", actividad.getSemillero().getId().toString());
				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
				correo.adicionarDireccion(actividad.getSemillero().getEmail());
				correo.setAsunto(asunto);
				correo.setCuerpo(cuerpo);
				enviarCorreo(correo, 1, true);
				actualizarInfoSemilleroInforme(actividad.getId(), actividad.getNumeroNotificaciones() + 1);
			}
		}
	}

	public void enviarAlertaInicioTramite() {
		try {
			List<Requerimiento> requerimientos = obtenerRequerimientosAlerta();
			CorreoPlantilla correoPlantilla = obtenerPlantillaCorreoCompleta(CORREO_PLANTILLA_ALERTA_REQ);
			int msgEnviados = 0;
			if (!esListaVacia(requerimientos)) {
				Iterator<Requerimiento> i = requerimientos.iterator();
				while (i.hasNext()) {
					Requerimiento req = i.next();
					Correo correo = new Correo();
					String cuerpo = correoPlantilla.getCuerpo();
					String asunto = correoPlantilla.getAsunto();
					correo.setOrigen(Correo.CORREO_HERMES);
					correo.adicionarDireccion(req.getIngenieroAsignado());
					correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
					asunto = asunto.replaceAll("<<TIPO>>", req.getTipo());
					asunto = asunto.replaceAll("<<ID>>", req.getId().toString());
					cuerpo = cuerpo.replaceAll("<<TIPO>>", req.getTipo());
					cuerpo = cuerpo.replaceAll("<<ID>>", req.getId().toString());
					if (req.getComentariosIngeniero() != null) {
						cuerpo = cuerpo.replaceAll("<<COMENTARIO>>", req.getComentariosIngeniero());
					} else {
						cuerpo = cuerpo.replaceAll("<<COMENTARIO>>", "<Sin comentarios al ingeniero>");
					}
					correo.setAsunto(asunto);
					correo.setCuerpo(cuerpo);
					msgEnviados++;
					if (!req.getAlertaEnviada()) {
						if (enviarCorreo(correo, 1, true)) {
							actualizarInfoAlertaRequerimiento(req.getId());
							System.out.println("CORREO ENVIADO EXITOSAMENTE A : " + req.getIngenieroAsignado()
									+ " - Mensajes Enviados: " + msgEnviados);
						}
					} else {
						System.out.println("ALERTA YA ENVIADA PARA LA SOLICITUD ID: " + req.getId());
					}
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR: Método TimerTask.enviarAlertaInicioTramite");
			e.printStackTrace();
		}
	}
	
	public boolean enviarCorreo(Correo correo, int numeroIntento, boolean envioHtml) {		
		Address sender = null;
		try {
			// Se crea la direccion Hermes que envia los correos
			if (correo.getAsunto().startsWith("Respuesta solicitud de mejora")
					|| correo.getAsunto().startsWith("Solicitud creación fuentes de financiación")
					|| correo.getAsunto().startsWith("Solicitud de habilitación de convocatoria externa")) {
				sender = new InternetAddress(Correo.CORREO_HERMES, "Alertas y Notificaciones Sistema Hermes", "utf-8");
			} else {
				sender = new InternetAddress(Correo.CORREO_HERMES, SENDER_NAME, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		MimeMultipart multipart = new MimeMultipart();
		Authenticator auth = new MyAuthenticator();
		Properties properties = new Properties();

		properties.put("mail.smtp.host", SERVIDOR);
		properties.put("mail.smtp.port", PUERTO);
		//properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
    	Session session = Session.getDefaultInstance(properties, auth);
		session.setDebug(DEBUG);
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(sender);
			// Se agregan las personas que recibiran el correo
			/*Iterator<Persona> it = correo.getPersonas().iterator();
			while (it.hasNext()) {
				Persona persona = (Persona) it.next();
				msg.addRecipients(Message.RecipientType.TO, persona.getEmail());
			}*/

			// Si tiene se agregan las direcciones de correo electronicas que
			// recibiran el correo
		msg.addRecipients(Message.RecipientType.TO, cadenaSeparadaComa(eliminarDuplicados(correo.getDirecciones())));
		System.out.print("Correos: " + cadenaSeparadaComa(eliminarDuplicados(correo.getDirecciones())) + "\n");
			/*Iterator<String> ite = eliminarDuplicados(correo.getDirecciones()).iterator();
			String direccion = "";
			while (ite.hasNext()) {
				direccion += (String) ite.next();
				
				msg.addRecipients(Message.RecipientType.TO,direccion);
			}*/
			
			//Copia permanente coordihrms_nal@unal.edu.co
			msg.addRecipients(Message.RecipientType.CC, Correo.CORREO_HERMES);

			// Si tiene se agregan las direcciones de correo electronicas que
			// recibiran el correo
			Iterator<String> iteO = eliminarDuplicados(correo.getCopiaOculta()).iterator();
			while (iteO.hasNext()) {
				String direccion = (String) iteO.next();
				msg.addRecipients(Message.RecipientType.BCC, direccion);
			}
			// Si tiene se agregan las direcciones de correo electronicas
			// adicionales o copias que recibiran el correo
			/*Iterator<String> iter = correo.getEnvios().iterator();
			while (iter.hasNext()) {
				String copia = (String) iter.next();
				msg.addRecipients(Message.RecipientType.BCC, copia);
			}*/
			// **Descomentariar para subir a produccion mientras se hacen la
			msg.setSubject(correo.getAsunto(),"utf-8");
			msg.setSentDate(new Date());

			// Create the message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setHeader("Content-Transfer-Encoding", "quoted-printable");
			if(envioHtml){
				correo.setCuerpo(correo.getCuerpo().replaceAll("(\r\n|\n)", "<br />"));
				messageBodyPart.setContent(HEADER_HTML + correo.getCuerpo() + FOOTER_HTML, "text/html");
			}
			else{
				messageBodyPart.setText(correo.getCuerpo() + PIE_CONTENIDO,  "utf-8");
			}
			multipart.addBodyPart(messageBodyPart);
			// Si el correo tiene un archivo adjunto
			if (correo.getAdjunto() != null) {
				BodyPart messageBodyPart2 = new MimeBodyPart();
				//Codigo revisado por que no reconoce el tipo .zip
				if(correo.getUrl()==null){
					FileDataSource source = new FileDataSource(correo.getAdjunto());
					messageBodyPart2.setDataHandler(new DataHandler(source));
				}else{
					messageBodyPart2.setDataHandler(new DataHandler(correo.getUrl()));					
				}
				messageBodyPart2.setFileName(correo.getNombreAdjunto());
				multipart.addBodyPart(messageBodyPart2);
			}
			if (correo.getAdjuntos() != null && correo.getAdjuntos().size() > 0) {
				for (Iterator<String> ia = correo.getAdjuntos().iterator(); ia
						.hasNext();) {
					BodyPart messageBodyPart2 = new MimeBodyPart();
					FileDataSource source = new FileDataSource((String) ia
							.next());
					messageBodyPart2.setDataHandler(new DataHandler(source));
					messageBodyPart2.setFileName("adjunto" + 1 + ".rtf");
					multipart.addBodyPart(messageBodyPart2);
				}
			}
			// Put parts in message
			msg.setContent(multipart);
			Transport.send(msg);
		
			if (correosEnviados == 0) {
				horaPrimerEnvio = new Date().getTime();
			}
			correosEnviados++;
			
			if (correosEnviados >= maximoEnviosPorMinuto) {
				horaUltimoEnvio = new Date().getTime();
				long diff = horaUltimoEnvio - horaPrimerEnvio;
				if (diff < 60000) {
					correosEnviados = 0;
					long tiempoRestanteMinuto = 60000 - diff;
					System.out
							.println("ServicioCorreo.enviarCorreo: Esperando "
									+ tiempoRestanteMinuto
									+ " milisegundos para continuar enviando...");
					Thread.sleep(tiempoRestanteMinuto);
				}
			}
		}
		catch (Exception mex) {
			String msgError = ">> MailSender.send() error = " + mex;
			System.out.println(msgError);
			mex.printStackTrace();
			guardarRegistroMail(correo, mex);
			return false;
			/*boolean reintentar = false;
			// De acuerdo a la excepción, se reintenta o no:
			if (msgError.contains("javax.mail.AuthenticationFailedException")) {
				System.out.println("ServicioCorreo.enviarCorreo error: javax.mail.AuthenticationFailedException");
				reintentar = true;
			}
			if (mex.getMessage() != null &&  mex.getMessage().contains("Could not connect to SMTP host")) {
				System.out.println("ServicioCorreo.enviarCorreo error: Could not connect to SMTP host");
				reintentar = true;
			}
			
			if (!reintentar) {
				System.out.println("ServicioCorreo.enviarCorreo error: Excepción desconocida, no se reintentará el envio.");
				mex.printStackTrace();
			}
			
			numeroIntento++;
			int numeroMaximoIntentos = 3;
			if (reintentar && numeroIntento <= numeroMaximoIntentos) {
				long segundosEspera = 15;
				try {
					System.out.println("ServicioCorreo.enviarCorreo, esperando " + segundosEspera + " segundos para reintentar...");
					Thread.sleep(segundosEspera * 1000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				return enviarCorreo(correo, numeroIntento, envioHtml);
			} else {
				return false;
			}*/
		}
		return true;
	}
	
	private List<String> eliminarDuplicados(List<String> lista) {
        return new ArrayList<String>(new HashSet<String>(lista));
    }
	
	private String cadenaSeparadaComa(List<String> list) {
	    if (list == null || list.isEmpty()) {
	        return "";
	    }
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < list.size(); i++) {
	        sb.append(list.get(i));
	        if (i < list.size() - 1) {
	            sb.append(", ");
	        }
	    }
	    return sb.toString();
	}


//	public boolean enviarCorreo(Correo correo, int numeroIntento, boolean envioHtml) {
//		System.out.println("TimerTask.enviarCorreo, intento número: " + numeroIntento);
//
//		Address sender = null;
//		try {
//			// Se crea la direccion Hermes que envia los correos
//			sender = new InternetAddress(Correo.CORREO_HERMES, SENDER_NAME, "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		MimeMultipart multipart = new MimeMultipart();
//		Authenticator auth = new MyAuthenticator();
//		Properties properties = new Properties();
//
//		properties.put("mail.smtp.host", SERVIDOR);
//		properties.put("mail.smtp.port", PUERTO);
//		properties.put("mail.smtp.starttls.enable", "true");
//		properties.put("mail.smtp.auth", "true");
//		Session session = Session.getDefaultInstance(properties, auth);
//		session.setDebug(DEBUG);
//		try {
//			MimeMessage msg = new MimeMessage(session);
//			msg.setFrom(sender);
//
//			// Se agregan las personas que recibiran el correo
//			Iterator<Persona> it = correo.getPersonas().iterator();
//			while (it.hasNext()) {
//				Persona persona = (Persona) it.next();
//				msg.addRecipients(Message.RecipientType.TO, persona.getEmail());
//			}
//
//			// Si tiene se agregan las direcciones de correo electronicas que
//			// recibiran el correo
//			Iterator<String> ite = correo.getDirecciones().iterator();
//			while (ite.hasNext()) {
//				String direccion = (String) ite.next();
//				msg.addRecipients(Message.RecipientType.TO, direccion);
//			}
//
//			// Si tiene se agregan las direcciones de correo electronicas que
//			// recibiran el correo
//			Iterator<String> iteO = correo.getCopiaOculta().iterator();
//			while (iteO.hasNext()) {
//				String direccion = (String) iteO.next();
//				msg.addRecipients(Message.RecipientType.BCC, direccion);
//				// msg.addRecipients(Message.RecipientType.BCC, "sisii_nal@unal.edu.co");
//			}
//			// Si tiene se agregan las direcciones de correo electronicas
//			// adicionales o copias que recibiran el correo
//			Iterator<String> iter = correo.getEnvios().iterator();
//			while (iter.hasNext()) {
//				String copia = (String) iter.next();
//				msg.addRecipients(Message.RecipientType.BCC, copia);
//			}
//			// **Descomentariar para subir a produccion mientras se hacen la
//			msg.setSubject(correo.getAsunto(), "utf-8");
//			msg.setSentDate(new Date());
//
//			// Create the message part
//			MimeBodyPart messageBodyPart = new MimeBodyPart();
//			messageBodyPart.setHeader("Content-Transfer-Encoding", "quoted-printable");
//			if (envioHtml) {
//				correo.setCuerpo(correo.getCuerpo().replaceAll("(\r\n|\n)", "<br />"));
//				messageBodyPart.setContent(HEADER_HTML + correo.getCuerpo() + FOOTER_HTML, "text/html");
//			} else {
//				messageBodyPart.setText(correo.getCuerpo() + PIE_CONTENIDO, "utf-8");
//			}
//			multipart.addBodyPart(messageBodyPart);
//			// Si el correo tiene un archivo adjunto
//			if (correo.getAdjunto() != null) {
//				BodyPart messageBodyPart2 = new MimeBodyPart();
//				// Codigo revisado por que no reconoce el tipo .zip
//				if (correo.getUrl() == null) {
//					FileDataSource source = new FileDataSource(correo.getAdjunto());
//					messageBodyPart2.setDataHandler(new DataHandler(source));
//				} else {
//					messageBodyPart2.setDataHandler(new DataHandler(correo.getUrl()));
//				}
//				messageBodyPart2.setFileName(correo.getNombreAdjunto());
//				multipart.addBodyPart(messageBodyPart2);
//			}
//			if (correo.getAdjuntos() != null && correo.getAdjuntos().size() > 0) {
//				for (Iterator<String> ia = correo.getAdjuntos().iterator(); ia.hasNext();) {
//					BodyPart messageBodyPart2 = new MimeBodyPart();
//					FileDataSource source = new FileDataSource((String) ia.next());
//					messageBodyPart2.setDataHandler(new DataHandler(source));
//					messageBodyPart2.setFileName("adjunto" + 1 + ".rtf");
//					multipart.addBodyPart(messageBodyPart2);
//				}
//			}
//			// Put parts in message
//			msg.setContent(multipart);
//			Transport.send(msg);
//			System.out.println("Mail sent successfully.");
//
//			if (correosEnviados == 0) {
//				horaPrimerEnvio = new Date().getTime();
//			}
//			correosEnviados++;
//			System.out.println("ServicioCorreo.enviarCorreo correosEnviados: " + correosEnviados);
//
//			if (correosEnviados >= maximoEnviosPorMinuto) {
//				horaUltimoEnvio = new Date().getTime();
//				long diff = horaUltimoEnvio - horaPrimerEnvio;
//				if (diff < 60000) {
//					System.out.println("ServicioCorreo.enviarCorreo: " + correosEnviados
//							+ " correos enviados en menos de un minuto (" + diff + " milisegundos).");
//					correosEnviados = 0;
//					long tiempoRestanteMinuto = 60000 - diff;
//					System.out.println("ServicioCorreo.enviarCorreo: Esperando " + tiempoRestanteMinuto
//							+ " milisegundos para continuar enviando...");
//					Thread.sleep(tiempoRestanteMinuto);
//				}
//			}
//		} catch (Exception mex) {
//			String msgError = ">> MailSender.send() error = " + mex;
//			System.out.println(msgError);
//			mex.printStackTrace();
//			guardarRegistroMail(correo, mex);
//			return false;
//		}
//		return true;
//	}

	private void guardarRegistroMail(Correo correo, Exception exp) {
		mensajeNoEnviado = new CorreoDB();
		mensajeNoEnviado.setAsunto(correo.getAsunto());
		mensajeNoEnviado.setMensaje(correo.getCuerpo());
		mensajeNoEnviado.setEnviado(0);
		mensajeNoEnviado.setIntentosEnvio(0);
		mensajeNoEnviado.setFechaUltimoIntento(Calendar.getInstance().getTime());
		mensajeNoEnviado.setUltimoError(exp.toString());
		mensajeNoEnviado.setDe(correo.getOrigen());
		String listaDirecciones = "";
		for (Persona para : correo.getPersonas()) {
			listaDirecciones += para.getEmail() + ";";
		}
		for (String direccion : correo.getDirecciones()) {
			listaDirecciones += direccion + ";";
		}
		mensajeNoEnviado.setPara(listaDirecciones);
		listaDirecciones = "";
		for (String direccion : correo.getEnvios()) {
			listaDirecciones += direccion + ";";
		}
		for (String direccion : correo.getCopiaOculta()) {
			listaDirecciones += direccion + ";";
		}
		mensajeNoEnviado.setCco(listaDirecciones);
		guardarObjeto(mensajeNoEnviado);
	}
	
	protected boolean esListaVacia(List lista) {
		return lista == null || (lista != null && lista.isEmpty());
	}

	public static ScheduledTimerTask getTimer() {
		return timer;
	}

	public static void setTimer(ScheduledTimerTask timer) {
		TimerTask.timer = timer;
	}
}