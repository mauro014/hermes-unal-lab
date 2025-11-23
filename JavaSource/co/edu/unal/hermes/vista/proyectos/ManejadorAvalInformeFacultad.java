package co.edu.unal.hermes.vista.proyectos;

import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;

/**
 * The Class ManejadorAvalInformeFacultad.
 */
public class ManejadorAvalInformeFacultad extends ManejadorAvalInformeBase {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7087996150953799503L;

    /**
     * Instantiates a new manejador aval informe facultad.
     */
    public ManejadorAvalInformeFacultad() {
    	sesion.removeAttribute("manejadorSemillerosSolicitudVIF");
    	sesion.removeAttribute("manejadorSemillerosConsultaVIF");
        sesion.setAttribute("esVIF", new Long("0"));
        InvestigadorInterno investigadorInterno = servicioPersona
                .obtenerInvestigadorInterno(((Persona) sesion.getAttribute("persona")).getId());

        listaInforme = servicioGeneral
                .obtenerAvalInformeFacultad(investigadorInterno.getDependencia2().getFacultad().getId());

    }

    public String aprobarInformeVIF() {
        sesion.setAttribute("revisionInformes", "VIF");
        return revisarInforme();
    }

}
