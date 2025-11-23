/*
 * Created on 02-sep-2005
 */
package co.edu.unal.hermes.seguridad;

import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.seguridad.autenticacion.BuscarInvestigadorExterno;
import co.edu.unal.hermes.seguridad.autenticacion.LdapHelper;
import co.edu.unal.hermes.seguridad.autenticacion.SinAutenticacionTemporal;
import co.edu.unal.hermes.seguridad.autenticacion.Usuario;
import co.edu.unal.hermes.seguridad.autorizacion.Autorizacion;

/**
 * @author Juan Pablo
 */
public class Seguridad {

	public Persona permitir(String user, String password) {
		LdapHelper ldap = new LdapHelper();
		Usuario u = null;
		Persona persona = null;

		// TODO CUIDADO LDAP desactivado

		if(user != null && user.trim().length() > 0 &&
				password != null && password.trim().length() > 0){
			u = ldap.autenticar(user, password);
		}
		if (u != null) {
			Autorizacion a = new Autorizacion();
			if (u.getCedula() != null && u.getCedula().equals("E340029")) {
				u.setCedula("340029");
			}
			persona = a.autorizar(u);
		}
		// Cuando el usuario no existe en ldap, se busca directamente en la base
		// de datos
		// para las sedes que no estan centralizadas por el momento
		else {
			SinAutenticacionTemporal sinAut = new SinAutenticacionTemporal();
			Usuario usuarioSinLdap = sinAut.autenticar(user, password);
			if (usuarioSinLdap != null) {
				Autorizacion a = new Autorizacion();
				persona = a.autorizar(usuarioSinLdap);
			} else {
				BuscarInvestigadorExterno b = new BuscarInvestigadorExterno();
				try {
					long clave = Long.valueOf(password).longValue();

					persona = b.autenticar(user, clave, persona);
				} catch (NumberFormatException e) {
					// e.printStackTrace();
				}
			}
		}
		return persona;
	}
}

/*
 * switch (Integer.parseInt(u.getRol())){ // Estudiante case 1: break; //
 * Docente case 2: break; // Administrativo case 3: break; // Intitucional case
 * 4: break; // Contratista case 5: break; // Dependencia case 6: break; //
 * Pensionado case 7: break; // Otro default: break; }
 */
