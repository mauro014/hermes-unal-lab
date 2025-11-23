package co.edu.unal.hermes.modelo.correo;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("avisohermes_nal@unal.edu.co",
				"NoReplyHermes123");
	}

}