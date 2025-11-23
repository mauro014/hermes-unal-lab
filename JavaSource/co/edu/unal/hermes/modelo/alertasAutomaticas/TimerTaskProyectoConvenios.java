package co.edu.unal.hermes.modelo.alertasAutomaticas;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import org.springframework.scheduling.timer.ScheduledTimerTask;

import co.edu.unal.hermes.bd.imp.GeneralDAOHibernate;
import co.edu.unal.hermes.modelo.CorreoDB;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.correo.MyAuthenticator;

public class TimerTaskProyectoConvenios extends GeneralDAOHibernate {
	private static final Long CORREO_PLANTILLA_ALERTA_FIN_CONVENIO = 325L;
	private static final Integer CANT_DIAS_ANTES = 60;

//	private static final String SERVIDOR = "unmtaout.unal.edu.co";
	private static final String SERVIDOR = "smtp-relay.gmail.com";
//	private static final String PUERTO = "25";
	private static final String PUERTO = "587";
	private static final String SENDER_NAME = "[No Responder] - Alertas y Notificaciones Sistema Hermes";
	private static final String PIE_CONTENIDO = "\n\n\n________________________________________________\n"
			+ "HERMES - Sistema de Información de la Investigación\n" + "Universidad Nacional de Colombia\n"
			+ "Ciudad Universitaria\n" + "Bogotá D.C.- Colombia\n" + "(+57)(1) 3165000 Ext. 11111\n\n"
			+ "Le recomendamos calificar el servicio que le brindó el Sistema de Información Hermes en el enlace http://www.hermes.unal.edu.co/pages/Requerimiento/EncuestaAtencionUsuario.xhtml. Su opinión es muy importante para nosotros.\n\n"
			+ "Así mismo puede consultar nuestros instructivos directamente haciendo clic en el siguiente enlace 'http://www.hermes.unal.edu.co/pages/html/descargas/index.xhtml'";

	private static final boolean DEBUG = false;

	private int correosEnviados = 0;
	private long horaPrimerEnvio;
	private long horaUltimoEnvio;
	private static final int maximoEnviosPorMinuto = 40;
	private static ScheduledTimerTask timer;
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

	public TimerTaskProyectoConvenios() {
		super();
	}

	public void execute() {
		enviarAlertasFinalizacionConvenio();
	}

	protected boolean esListaVacia(List lista) {
		return lista == null || (lista != null && lista.isEmpty());
	}

	public void enviarAlertasFinalizacionConvenio() {

		List<AlertasAutoProyectosConvenios> conveniosAlertas = obtenerProyectosConveniosFinalizacionAlertas(
				CANT_DIAS_ANTES);
		CorreoPlantilla correoPlantilla = obtenerPlantillaCorreoCompleta(CORREO_PLANTILLA_ALERTA_FIN_CONVENIO);

		if (!esListaVacia(conveniosAlertas)) {
			Iterator<AlertasAutoProyectosConvenios> i = conveniosAlertas.iterator();
			while (i.hasNext()) {
				AlertasAutoProyectosConvenios alertaConvenio = i.next();

				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

				String dateString = format.format(alertaConvenio.getFechaFinConvenio());

				Correo correo = new Correo();
				String cuerpo = correoPlantilla.getCuerpo();
				String asunto = correoPlantilla.getAsunto();

				asunto = asunto.replaceAll("<<ID_PROYECTO>>", alertaConvenio.getProyectoId().toString());

				cuerpo = cuerpo.replaceAll("<<ID_PROYECTO>>", alertaConvenio.getProyectoId().toString());
				cuerpo = cuerpo.replaceAll("<<NOMBRE_PROYECTO>>", alertaConvenio.getTituloProyecto());
				cuerpo = cuerpo.replaceAll("<<CONV_FEC_FIN>>", dateString);

				correo.setOrigen(Correo.CORREO_HERMES);
				correo.adicionarDireccion(alertaConvenio.getEmailInvestigador());
				correo.adicionarDireccion(alertaConvenio.getEmailCoordinador());
				correo.adicionarCopiaOculta(Correo.CORREO_HERMES);

				correo.setAsunto(asunto);
				correo.setCuerpo(cuerpo);
				// System.out.println("cuerpo correo: " + correo.getCuerpo());
				enviarCorreo(correo, 1, true);
				actualizarInfoAlertaProyecto(alertaConvenio.getProyectoId(), 2);
			}
		}

	}

	public boolean enviarCorreo(Correo correo, int numeroIntento, boolean envioHtml) {
		System.out.println("TimerTask.enviarCorreo inicio Finalizacion Convenios, intento número: " + numeroIntento);

		Address sender = null;
		try {
			// Se crea la direccion Hermes que envia los correos
			sender = new InternetAddress(Correo.CORREO_HERMES, SENDER_NAME, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		MimeMultipart multipart = new MimeMultipart();
		Authenticator auth = new MyAuthenticator();
		Properties properties = new Properties();

		properties.put("mail.smtp.host", SERVIDOR);
		properties.put("mail.smtp.port", PUERTO);
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(properties, auth);
		session.setDebug(DEBUG);
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(sender);

			// Se agregan las personas que recibiran el correo
			Iterator<Persona> it = correo.getPersonas().iterator();
			while (it.hasNext()) {
				Persona persona = (Persona) it.next();
				msg.addRecipients(Message.RecipientType.TO, persona.getEmail());
			}

			// Si tiene se agregan las direcciones de correo electronicas que
			// recibiran el correo
			Iterator<String> ite = correo.getDirecciones().iterator();
			while (ite.hasNext()) {
				String direccion = (String) ite.next();
				msg.addRecipients(Message.RecipientType.TO, direccion);
			}

			// Si tiene se agregan las direcciones de correo electronicas que
			// recibiran el correo
			Iterator<String> iteO = correo.getCopiaOculta().iterator();
			while (iteO.hasNext()) {
				String direccion = (String) iteO.next();
				msg.addRecipients(Message.RecipientType.BCC, direccion);
				// msg.addRecipients(Message.RecipientType.BCC, "sisii_nal@unal.edu.co");
			}
			// Si tiene se agregan las direcciones de correo electronicas
			// adicionales o copias que recibiran el correo
			Iterator<String> iter = correo.getEnvios().iterator();
			while (iter.hasNext()) {
				String copia = (String) iter.next();
				msg.addRecipients(Message.RecipientType.BCC, copia);
			}
			// **Descomentariar para subir a produccion mientras se hacen la
			msg.setSubject(correo.getAsunto(), "utf-8");
			msg.setSentDate(new Date());

			// Create the message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setHeader("Content-Transfer-Encoding", "quoted-printable");
			if (envioHtml) {
				correo.setCuerpo(correo.getCuerpo().replaceAll("(\r\n|\n)", "<br />"));
				messageBodyPart.setContent(HEADER_HTML + correo.getCuerpo() + FOOTER_HTML, "text/html");
			} else {
				messageBodyPart.setText(correo.getCuerpo() + PIE_CONTENIDO, "utf-8");
			}
			multipart.addBodyPart(messageBodyPart);
			// Si el correo tiene un archivo adjunto
			if (correo.getAdjunto() != null) {
				BodyPart messageBodyPart2 = new MimeBodyPart();
				// Codigo revisado por que no reconoce el tipo .zip
				if (correo.getUrl() == null) {
					FileDataSource source = new FileDataSource(correo.getAdjunto());
					messageBodyPart2.setDataHandler(new DataHandler(source));
				} else {
					messageBodyPart2.setDataHandler(new DataHandler(correo.getUrl()));
				}
				messageBodyPart2.setFileName(correo.getNombreAdjunto());
				multipart.addBodyPart(messageBodyPart2);
			}
			if (correo.getAdjuntos() != null && correo.getAdjuntos().size() > 0) {
				for (Iterator<String> ia = correo.getAdjuntos().iterator(); ia.hasNext();) {
					BodyPart messageBodyPart2 = new MimeBodyPart();
					FileDataSource source = new FileDataSource((String) ia.next());
					messageBodyPart2.setDataHandler(new DataHandler(source));
					messageBodyPart2.setFileName("adjunto" + 1 + ".rtf");
					multipart.addBodyPart(messageBodyPart2);
				}
			}
			// Put parts in message
			msg.setContent(multipart);
			Transport.send(msg);
			System.out.println("Mail sent successfully.");

			if (correosEnviados == 0) {
				horaPrimerEnvio = new Date().getTime();
			}
			correosEnviados++;
			System.out.println(
					"ServicioCorreo.enviarCorreo fin Finalizacion Convenios correosEnviados: " + correosEnviados);

			if (correosEnviados >= maximoEnviosPorMinuto) {
				horaUltimoEnvio = new Date().getTime();
				long diff = horaUltimoEnvio - horaPrimerEnvio;
				if (diff < 60000) {
					System.out.println("ServicioCorreo.enviarCorreo: " + correosEnviados
							+ " correos enviados en menos de un minuto (" + diff + " milisegundos).");
					correosEnviados = 0;
					long tiempoRestanteMinuto = 60000 - diff;
					System.out.println("ServicioCorreo.enviarCorreo: Esperando " + tiempoRestanteMinuto
							+ " milisegundos para continuar enviando...");
					Thread.sleep(tiempoRestanteMinuto);
				}
			}
		} catch (Exception mex) {
			String msgError = ">> MailSender.send() error = " + mex;
			System.out.println(msgError);
			mex.printStackTrace();
			guardarRegistroMail(correo, mex);
			return false;
		}
		return true;
	}

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

	public static ScheduledTimerTask getTimer() {
		return timer;
	}

	public static void setTimer(ScheduledTimerTask timer) {
		TimerTaskProyectoConvenios.timer = timer;
	}
}
