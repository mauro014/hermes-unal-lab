package co.edu.unal.hermes.modelo.servicios.imp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.codec.binary.Base64;

import co.edu.unal.hermes.bd.ICorreoDAO;
import co.edu.unal.hermes.bd.IGeneralDAO;
import co.edu.unal.hermes.modelo.ArchivoAdjunto;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.ParametroMaestro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.CorreoDB;
import co.edu.unal.hermes.modelo.correo.MyAuthenticator;
import co.edu.unal.hermes.modelo.servicios.IServicioCorreo;

public class ServicioCorreo implements IServicioCorreo {

	private ICorreoDAO correoDAO;
	private IGeneralDAO generalDAO;

	private static final String SERVIDOR = "172.217.203.28";
//	private static final String SERVIDOR = "smtp-relay.gmail.com";
//	private static final String SERVIDOR_BOL = "unmtaout.unal.edu.co";
	private static final String PUERTO = "25";
//	private static final String PUERTO_TLS = "465";
	//private static final String PUERTO_TLS = "587";
	private static final String SENDER_NAME = "[No Responder] - Alertas y Notificaciones Sistema Hermes";
	private static final String PIE_CONTENIDO = "\n\n\n________________________________________________\n"
			+ "HERMES - Sistema de Información de la Investigación\n"
			+ "Universidad Nacional de Colombia\n"
			+ "Ciudad Universitaria\n"
			+ "Bogotá D.C.- Colombia\n" + "(+57)(1) 3165000 Ext. 11111\n\n"
			+ "Le recomendamos calificar el servicio que le brindó el Sistema de Información Hermes en el enlace http://www.hermes.unal.edu.co/pages/Requerimiento/EncuestaAtencionUsuario.xhtml. Su opinión es muy importante para nosotros.\n\n"
			+ "Así mismo puede consultar nuestros instructivos directamente haciendo clic en el siguiente enlace 'http://www.hermes.unal.edu.co/pages/html/descargas/index.xhtml'";
	
	private static final boolean DEBUG = false;
	private static final String SENDER_NAME_SOL = "[No Responder] - Alertas y Notificaciones Sistema Hermes";
	private static final String SENDER_NAME_COM = "SIUN · Sistema de Investigación de la Universidad Nacional de Colombia";
	
	private int correosEnviados = 0;
	private long horaPrimerEnvio;
	private long horaUltimoEnvio;
	private static final int maximoEnviosPorMinuto = 20;
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

	
	private static final String ALGORITHM = "AES";
    private static final String CIPHER_MODE = "AES/CBC/PKCS5Padding";
    private static final String CHARSET = "UTF-8";

    // Clave fija (debe ser segura en producción)
    private static final String SECRET_KEY = "qIzGl6ClePeZYQN5"; // 16 bytes
    private static final String IV = "0653ed4b304a57ff"; // 16 bytes
    
	
	public void setCorreoDAO(ICorreoDAO correoDAO) {
		this.correoDAO = correoDAO;
	}
	
	public boolean enviarCorreoBoletin(Correo correo) {

		Address sender = null;
		try {
			sender = new InternetAddress(Correo.CORREO_BOLETIN, SENDER_NAME_COM,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		MimeMultipart multipart = new MimeMultipart();
		Properties properties = new Properties();
		properties.put("mail.smtp.host", SERVIDOR);
		properties.put("mail.smtp.port", PUERTO);
		//properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");		
		
		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				ParametroMaestro pmEmail = new ParametroMaestro();
				pmEmail = generalDAO.obtenerParametroPorNombre(ParametroMaestro.EMAIL_BOLETIN);
				
				ParametroMaestro pmPass = new ParametroMaestro();
				pmPass = generalDAO.obtenerParametroPorNombre(ParametroMaestro.PASS_BOLETIN);
				
				try {
					String valor = encrypt("");
					System.out.println("constraseña encrpitada: "+valor);
					System.out.println("contrseña desencriptada: "+decrypt(valor));
					System.out.println("contraseña desemcriptada base de datos"+ decrypt(pmPass.getValor()));
					return new PasswordAuthentication(pmEmail.getValor(), decrypt(pmPass.getValor()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		});
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
				//msg.addRecipients(Message.RecipientType.BCC, "sisii_nal@unal.edu.co");
			}

			// Si tiene se agregan las direcciones de correo electronicas
			// adicionales o copias que recibiran el correo
			Iterator<String> iter = correo.getEnvios().iterator();
			while (iter.hasNext()) {
				String copia = (String) iter.next();
				msg.addRecipients(Message.RecipientType.BCC, copia);
			}
			//msg.addRecipients(Message.RecipientType.CC,"sisii_nal@unal.edu.co");
			msg.setSubject(correo.getAsunto(),"utf-8");
			msg.setSentDate(new Date());
			
			MimeBodyPart mbp = new MimeBodyPart(); 
			mbp.setContent(correo.getCuerpo(), "text/html"); 
			multipart.addBodyPart(mbp);
			msg.setContent(multipart);
					
			// Si el correo tiene un archivo adjunto
			if (correo.getAdjunto() != null) {
				BodyPart messageBodyPart2 = new MimeBodyPart();
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

		} catch (Exception mex) {
			mex.printStackTrace();
			correo.setCuerpo("Mensaje de Boletín: "+correo.getAsunto());
			guardarRegistroMail(correo, mex);
			return false;
		}

		return true;
	}
	

	public boolean enviarCorreoSolicitud(Correo correo) {

		Address sender = null;
		try {
			// Se crea la direccion Hermes que envia los correos
			sender = new InternetAddress(Correo.CORREO_HERMES_SOLICITUDES, SENDER_NAME_SOL,"utf-8");
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
				//msg.addRecipients(Message.RecipientType.BCC, "sisii_nal@unal.edu.co");
			}

			// Si tiene se agregan las direcciones de correo electronicas
			// adicionales o copias que recibiran el correo
			Iterator<String> iter = correo.getEnvios().iterator();
			while (iter.hasNext()) {
				String copia = (String) iter.next();
				msg.addRecipients(Message.RecipientType.BCC, copia);
			}
			// **Descomentariar para subir a produccion mientras se hacen la
			// pruebas**//
			msg.setSubject(correo.getAsunto(),"utf-8");
			msg.setSentDate(new Date());

			// Create the message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setHeader("Content-Transfer-Encoding", "quoted-printable");
			correo.setCuerpo(correo.getCuerpo().replaceAll("(\r\n|\n)", "<br />"));
			messageBodyPart.setContent(HEADER_HTML + correo.getCuerpo() + FOOTER_HTML, "text/html");
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

		} catch (Exception mex) {
			mex.printStackTrace();
			guardarRegistroMail(correo, mex);
			return false;
		}
		return true;
	}

	public boolean enviarCorreo(Correo correo) {
		return enviarCorreo(correo, 1, true);
	}
	
	public boolean enviarCorreo(Correo correo, boolean envioHtml) {
		return enviarCorreo(correo, 1,envioHtml);
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
		System.out.print(cadenaSeparadaComa(eliminarDuplicados(correo.getDirecciones())));
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
		correoDAO.guardarMensajeNoEnviado(mensajeNoEnviado);
	}

	public ArchivoAdjunto obtenerArchivoAdjunto(CorreoPlantilla plantillaCorreo) {
		return correoDAO.obtenerArchivoAdjunto(plantillaCorreo.getId());
	}

	public CorreoPlantilla obtenerPlantillaCorreoCompleta(Long id) {
		return correoDAO.obtenerPlantillaCorreoCompleta(id);
	}

	public boolean crearArchivo(String ruta, byte[] bytes) {
		try {
			File file = new File(ruta);
			FileOutputStream os;
			os = new FileOutputStream(file);
			PrintStream ps = new PrintStream(os);
			try {
				ps.write(bytes);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			ps.flush();
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean eliminarArchivo(String rutaArchivo) {
		File file = new File(rutaArchivo);
		return file.delete();
	}

	/**
	 * Sirve para cojer una plantilla y aplicarsela a un correo, todos los
	 * campos de la plantilla quedan en el objeto correo para poder enviarlo. Se
	 * crea el archivo en disco (temporalmente) para poder ser enviado
	 * 
	 * @param coreo
	 * @param plantilla
	 */
	public void aplicarPlantillaCorreo(Correo correo,
			CorreoPlantilla plantilla, String rutaAdjunto) {
		correo.setAsunto(plantilla.getAsunto());
		correo.setCuerpo(plantilla.getCuerpo());
		if (plantilla.getNombreAdjunto() != null
				&& !plantilla.getNombreAdjunto().equals("")) {
			correo.setNombreAdjunto(plantilla.getNombreAdjunto());
			ArchivoAdjunto a = obtenerArchivoAdjunto(plantilla);
			crearArchivo(rutaAdjunto, a.getBytes());
			correo.setAdjunto(rutaAdjunto);
		}
	}

	public void aplicarPlantillaCorreoAdjunto(Correo correo,
			CorreoPlantilla plantilla, String rutaAdjunto) {

		if (plantilla.getNombreAdjunto() != null
				&& !plantilla.getNombreAdjunto().equals("")) {
			correo.setNombreAdjunto(plantilla.getNombreAdjunto());
			ArchivoAdjunto a = obtenerArchivoAdjunto(plantilla);
			crearArchivo(rutaAdjunto, a.getBytes());
			correo.setAdjunto(rutaAdjunto);
		}
	}	
	
	public static String decrypt(String encryptedText) throws Exception {
		System.out.println(encryptedText);
        Cipher cipher = Cipher.getInstance(CIPHER_MODE);
        SecretKey key = new SecretKeySpec(SECRET_KEY.getBytes(CHARSET), ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(CHARSET));
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decodedBytes = Base64.decodeBase64(encryptedText.getBytes());
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, CHARSET);
    }
	
	
	/**
	 * Este metodo se debe utilizar si se desea cambiar la contraseña del correo para enviar el boletín.
	 * @param plaintext
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String plaintext) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_MODE);
        SecretKey key = new SecretKeySpec(SECRET_KEY.getBytes(CHARSET), ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(CHARSET));
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes(CHARSET));
        return new String(Base64.encodeBase64(encrypted));
    }
	
	
	/**
	 * Gets the general dao.
	 *
	 * @return the general dao
	 */
	public IGeneralDAO getGeneralDAO() {
		return generalDAO;
	}

	/**
	 * Sets the general dao.
	 *
	 * @param generalDAO
	 *            the new general dao
	 */
	public void setGeneralDAO(IGeneralDAO generalDAO) {
		this.generalDAO = generalDAO;
	}
}
