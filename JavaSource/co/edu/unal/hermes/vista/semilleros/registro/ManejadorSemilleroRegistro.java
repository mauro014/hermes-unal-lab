package co.edu.unal.hermes.vista.semilleros.registro;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.HistoricoCambioIntegrantes;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorSemilleroRegistro extends ManejadorBase {

	private static final long serialVersionUID = 1L;
	protected static final String ID_VINCULACIONES = "264";
	protected boolean isOK = true;
	protected HistoricoCambioIntegrantes hci;
	protected boolean esConsulta;

	public ManejadorSemilleroRegistro() {
		sesion.removeAttribute("manejadorSemillerosHome");
		if (sesion.getAttribute("consultaSemillero") != null) {
			esConsulta = (Boolean) sesion.getAttribute("consultaSemillero");
		} else {
			esConsulta = false;
		}
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

	protected void generarMsgModal(int tipo, String mensaje) {
		switch (tipo) {
		case 1:
			FacesContext.getCurrentInstance().addMessage("msgsModal",
					new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null));
			break;
		case 2:
			FacesContext.getCurrentInstance().addMessage("msgsModal",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
			break;
		}
	}

	private void enviarSesion(Integer id) {
		sesion.setAttribute("semillero", id);
	}

	public String irGeneral(Integer id) {
		enviarSesion(id);
		return "irGeneral";
	}

	public String irIntegrantes(Integer id) {
		enviarSesion(id);
		return "irIntegrantes";
	}

	public String irLineasAreas(Integer id) {
		enviarSesion(id);
		return "irLineasAreas";
	}

	public String irPlanTrabajo(Integer id) {
		enviarSesion(id);
		return "irPlanTrabajo";
	}

	protected String irArchivosEnviar(Integer id) {
		enviarSesion(id);
		return "irArchivosEnviar";
	}

	public void guardarParcialmente() {
		guardar(true);
	}

	public String siguiente() {
		return guardar(false);
	}

	String guardar(boolean parcial) {
		return null;
	}

	boolean validarForm() {
		return isOK;
	}

	public HistoricoCambioIntegrantes getHci() {
		return hci;
	}

	public void setHci(HistoricoCambioIntegrantes hci) {
		this.hci = hci;
	}

	public boolean isEsConsulta() {
		return esConsulta;
	}
}
