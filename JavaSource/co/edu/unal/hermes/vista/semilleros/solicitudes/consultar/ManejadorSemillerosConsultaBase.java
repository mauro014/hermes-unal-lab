package co.edu.unal.hermes.vista.semilleros.solicitudes.consultar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Semillero;
import co.edu.unal.hermes.modelo.SemilleroSolicitud;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorSemillerosConsultaBase extends ManejadorBase {

	private static final long serialVersionUID = 1L;
	protected List<SemilleroSolicitud> listaSolicitudes;
	protected SemilleroSolicitud solicitudActual;
	protected ArrayList<SelectItem> respuestasSolicitud;
	protected List<Semillero> semilleros;
	protected InvestigadorInterno investigadorInterno;

	public ManejadorSemillerosConsultaBase() {
		respuestasSolicitud = new ArrayList<SelectItem>();
		solicitudActual = new SemilleroSolicitud();
		Investigador investigadorActual = servicioPersona
				.obtenerInvestigador(((Persona) sesion.getAttribute("persona")).getId());
		investigadorInterno = servicioPersona
				.obtenerInvestigadorInterno(investigadorActual.getId());
		listaSolicitudes = new ArrayList<SemilleroSolicitud>();
		semilleros = new ArrayList<Semillero>();
	}
	
	public int getTotalSolicitudes() {
		return listaSolicitudes.size();
	}
	
	protected void generarMsg(int tipo, String mensaje) {
		switch (tipo) {
		case 1:
			FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null));
			break;
		case 2:
			FacesContext.getCurrentInstance().addMessage("msgs",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
			break;
		}
	}
}
