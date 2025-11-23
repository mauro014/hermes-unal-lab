/*
 * Created on 07-oct-2005
 */
package co.edu.unal.hermes.seguridad.autenticacion;

import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;

import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.servicioPersona.IServicioPersona;

/**
 * @author jpduqueg
 */
public class BuscarInvestigadorExterno {

    public Persona autenticar(String user, long clave, Persona p) {
        ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        System.out.println("buscando iunvestigsdor externo" + clave);
        IServicioPersona servicioPersona = (IServicioPersona) ctx.getBean("servicioPersona");
        List<Persona> personas = servicioPersona.buscarPersonaInvestigadoresExternos(clave);
        
        Persona persona = null;
        
        if(personas != null){
            Iterator<Persona> i = personas.iterator();
            while(i.hasNext()){
                Persona personaTemporal =i.next();
                if(servicioPersona.login(personaTemporal).equals(user)){
                    persona = personaTemporal;
                }
            }
        }
        
        if (persona != null) {
            persona = servicioPersona.obtenerPersonaRoles(persona.getId());
            InvestigadorExterno ie = servicioPersona
                    .obtenerInvestigadorExterno(persona.getId());
            String usuario = servicioPersona.login(ie);
            System.out.println(usuario.equals(user));
            if (usuario.equals(user)) {
                return persona;

            }
        }
        return null;
    }
}
