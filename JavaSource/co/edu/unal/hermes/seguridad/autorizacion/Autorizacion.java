/*
 * Created on 05-sep-2005
 */
package co.edu.unal.hermes.seguridad.autorizacion;

import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.servicioPersona.IServicioPersona;
import co.edu.unal.hermes.seguridad.autenticacion.Usuario;

/**
 * @author Juan Pablo
 */
public class Autorizacion {
    
    public Persona autorizar(Usuario usuario){
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		Persona persona = null;
		if (usuario.getCedula() != null) {
			IdPersona id = new IdPersona();
			id.setDocumento(usuario.getCedula());
			id.setTipoDocumento(TipoDocumento.CEDULA);
			persona = ((IServicioPersona) ctx.getBean("servicioPersona")).obtenerPersonaRoles(id);
			if (persona == null) {
				System.out.println("no cedula" );
				id.setTipoDocumento(TipoDocumento.CEDULA_EXTRANJERIA);
				persona = ((IServicioPersona) ctx.getBean("servicioPersona")).obtenerPersonaRoles(id);
				if (persona == null) {
					System.out.println("no extranjeria" );
					id.setTipoDocumento(TipoDocumento.NIP);
					persona = ((IServicioPersona) ctx.getBean("servicioPersona")).obtenerPersonaRoles(id);
					if (persona == null) {
						System.out.println("no nit" );
						id.setTipoDocumento(TipoDocumento.PASAPORTE);
						persona = ((IServicioPersona) ctx.getBean("servicioPersona")).obtenerPersonaRoles(id);
						if (persona == null) {
							System.out.println("pasaporte" );
							id.setTipoDocumento(TipoDocumento.TARJETA_IDENTIDAD);
							persona = ((IServicioPersona) ctx.getBean("servicioPersona")).obtenerPersonaRoles(id);
							if(persona == null) {
								System.out.println("no TI" );
							}
						}
					}
				}
			}
		} else {
			persona = (Persona) ((IServicioPersona) ctx.getBean("servicioPersona"))
					.obtenerPersona("where per.email like '" + usuario.getUid() + "%'", false, null).get(0);
		}
        
        if(persona != null){
            persona.setUid(usuario.getUid());
        }        
        return persona;
    }
}
