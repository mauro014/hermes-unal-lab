package co.edu.unal.hermes.vista.proyectos;

import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;

public class ManejadorAvalInformeUab extends ManejadorAvalInformeBase {

    private static final long serialVersionUID = 1L;

    public ManejadorAvalInformeUab() {
    	sesion.removeAttribute("manejadorSemillerosSolicitudUAB");
    	sesion.removeAttribute("manejadorSemillerosConsultaUAB");
		try {
			InvestigadorInterno investigadorInterno = servicioPersona
					.obtenerInvestigadorInterno(((Persona) sesion.getAttribute("persona")).getId());

			listaInforme = servicioGeneral
					.obtenerAvalInformeUab(investigadorInterno.getDependencia2().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
    
    public String aprobarInformeUAB(){
        sesion.setAttribute("revisionInformes", "UAB");
        return revisarInforme();
    }

}
