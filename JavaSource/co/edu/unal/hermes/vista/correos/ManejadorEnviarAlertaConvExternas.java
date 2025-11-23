package co.edu.unal.hermes.vista.correos;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorEnviarAlertaConvExternas extends ManejadorBase{
	
	private String resultadoEnvio;
	
	public ManejadorEnviarAlertaConvExternas() {
		super();
		resultadoEnvio = "";
	}
	
	public void enviarAlertas() throws IOException {
		try {
			int correosEnviados = 0;
			int totalMensajesAEnviar = 0;

			String gwk = "from ConvocatoriaExterna WHERE notificaciones <= 3 AND fechaResultados != null)";
			String pwk = "from Persona p WHERE (p.id.documento IN ("
					+ "select pr.documento FROM PersonaRol pr where pr.nombre = 'PH' or pr.nombre = 'PJ')" + ")";

			System.out.println(gwk);
			System.out.println(pwk);

			List<ConvocatoriaExterna> listaConvExternas = new ArrayList<ConvocatoriaExterna>();
			List<Persona> listaEncargados = new ArrayList<Persona>();

			listaConvExternas = servicioGeneral.obtenerObjetos(ConvocatoriaExterna.class, gwk);
			listaEncargados = servicioGeneral.obtenerObjetos(Persona.class, pwk);
			Date fechaActual = new Date();

			for (int i = 0; i < listaConvExternas.size(); i++) {
				ConvocatoriaExterna convAux = listaConvExternas.get(i);
				Date fechaResultados = convAux.getFechaResultados();
				int notificaciones = convAux.getNotificaciones();
				int plantilla = 0;
				long diasRestantes = (fechaResultados.getTime() - fechaActual.getTime()) / 86400000; // 1000*60*60*24
				if (notificaciones <= 3 && diasRestantes >= 0) {
					// Alerta entrega de resultados, cinco días antes
					if (diasRestantes <= 5) {
						plantilla = 295;
					}
				}
				if (plantilla != 0) {
					totalMensajesAEnviar++;
					CorreoPlantilla correoPlantilla = cargarPlantilla(plantilla);
					String cuerpo = correoPlantilla.getCuerpo();
					String asunto = correoPlantilla.getAsunto();
					Correo correo = new Correo();
					correo.setOrigen(Correo.CORREO_HERMES);
					//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
					asunto = asunto.replaceAll("<<TITULO>>", convAux.getNombre());
					correo.setAsunto(asunto);
					for (int j = 0; j < listaEncargados.size(); j++) {
						Persona pAux = (Persona) listaEncargados.get(j);
						correo.adicionarDireccion(pAux.getEmail());
					}
					String cuerpoCorreo = cuerpo.replaceAll("<<TITULO>>", convAux.getNombre());
					cuerpoCorreo = cuerpoCorreo.replaceAll("<<ENTIDAD>>", convAux.getEntidad().getDescripcion());
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					cuerpoCorreo = cuerpoCorreo.replaceAll("<<FECHA>>", formatter.format(fechaResultados));
					String viewId = " http://www.hermes.unal.edu.co/pages/Consultas/ConvocatoriaExterna.xhtml";
					Long numConvExterna = convAux.getId();
					viewId = viewId + '?' + "idConvocatoria" + "=" + numConvExterna + "&tipo=" + "E";
					cuerpoCorreo = cuerpoCorreo.replaceAll("<<ENLACE>>", viewId);
					correo.setCuerpo(cuerpoCorreo);

					System.out.println(fechaActual + " Enviando correo notificación convocatoria: " + convAux.getId());

					if (servicioCorreo.enviarCorreo(correo)) {
						notificaciones++;
						convAux.setNotificaciones(new Integer(notificaciones));
						convAux.setFechaNotificacion(fechaActual);
						servicioGeneral.guardarObjeto(convAux);
						correosEnviados++;
					}
				}
			}
			
			resultadoEnvio = correosEnviados + " de " + totalMensajesAEnviar + " Total de Correo(s) Enviado(s).";
			FacesContext.getCurrentInstance().addMessage("messageResultadoEnvio",
					new FacesMessage(FacesMessage.SEVERITY_INFO, resultadoEnvio, null));
		} catch (Exception mex) {
			resultadoEnvio = "Error al enviar correo(s) de Alerta: " + mex.getMessage();
			mex.printStackTrace();
		}
		System.out.println(new Date() + " resultadoEnvio: " + resultadoEnvio);
	}

	public CorreoPlantilla cargarPlantilla(int cod_id) {
		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		String hql = "FROM CorreoPlantilla WHERE id='" + cod_id + "'";
		List<CorreoPlantilla> lista = servicioGeneral.obtenerObjetos(CorreoPlantilla.class, hql);
		if (lista != null && lista.size() > 0) {
			correoActualAux = (CorreoPlantilla) lista.get(0);
		}
		return correoActualAux;
	}
	
	/**
	 * @return the resultadoEnvio
	 */
	public String getResultadoEnvio() {
		return resultadoEnvio;
	}

	/**
	 * @param resultadoEnvio
	 *            the resultadoEnvio to set
	 */
	public void setResultadoEnvio(String resultadoEnvio) {
		this.resultadoEnvio = resultadoEnvio;
	}

}
