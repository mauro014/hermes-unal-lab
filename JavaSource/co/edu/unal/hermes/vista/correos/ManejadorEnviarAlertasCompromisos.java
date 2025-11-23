/**
 * @author dgbenitezc
 * @edited amdevias
 */

package co.edu.unal.hermes.vista.correos;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.ProyectoCompromiso;
import co.edu.unal.hermes.modelo.TipoInforme;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorEnviarAlertasCompromisos extends ManejadorBase {

	private String resultadoEnvio;

	public ManejadorEnviarAlertasCompromisos() {
		super();
		resultadoEnvio = "";
	}

	public void enviarAlertas() throws IOException {
		try {
			// String hql = "from ProyectoCompromiso WHERE cumplido = '" +
			// ProyectoCompromiso.NO_CUMPLIDO + "' ";

			String hql = "from ProyectoCompromiso WHERE cumplido = '" + ProyectoCompromiso.NO_CUMPLIDO
					+ "' AND id NOT IN (SELECT C.id FROM ProyectoCompromiso C, ProyectoInforme I WHERE C.proyecto = I.proyecto AND C.tipoInforme = I.tipoInforme AND C.cumplido = '"
					+ ProyectoCompromiso.NO_CUMPLIDO + "' AND I.estadoInforme > 1)";
			System.out.println(hql);

			List<ProyectoCompromiso> listaCompromisos = new ArrayList<ProyectoCompromiso>();

			listaCompromisos = servicioGeneral.obtenerObjetos(ProyectoCompromiso.class, hql);
			int correosEnviados = 0;
			int totalMensajesAEnviar = 0;
			for (int i = 0; i < listaCompromisos.size(); i++) {
				ProyectoCompromiso compAux = (ProyectoCompromiso) listaCompromisos
						.get(i);
				String idEstadoProyecto = compAux.getProyecto()
						.getEstadoProyecto().getId();
				// dgbenitezc: se envían alertas únicamente a proyectos ACTIVOS
				if (idEstadoProyecto.equals(EstadoProyecto.ACTIVO)) {
					Date fechaActual = new Date();
					Date fechaVencimiento = compAux.getFechaVencProrroga();
					if (fechaVencimiento == null) { // NO ha habido prórroga
						fechaVencimiento = compAux.getFechaVencimiento();
					}
					long diasRestantes = (fechaVencimiento.getTime() - fechaActual
							.getTime()) / 86400000; // 1000 * 60 * 60 * 24
					int notificaciones = compAux.getNumeroNotificaciones()
							.intValue();
					int plantilla = 0;

					if (compAux.getTipoInforme().getId()
							.equals(TipoInforme.INFORME_FINAL)) {
						// Alerta entrega Informe Final, dos meses antes
						if (notificaciones == 0 && diasRestantes <= 61) {
							plantilla = 125;
							if(compAux.getProyecto().getEsContratoBiodiversidad()) {
								plantilla = 405;
							}
						}
						// Alerta entrega Informe Final, una semana antes
						if (notificaciones == 1 && diasRestantes <= 15) {
							plantilla = 125;
							if(compAux.getProyecto().getEsContratoBiodiversidad()) {
								plantilla = 405;
							}
						}
						// Alerta entrega Informe Final, un día después
						if (notificaciones == 2 && diasRestantes <= -1) {
							plantilla = 126;
							if(compAux.getProyecto().getEsContratoBiodiversidad()) {
								plantilla = 405;
							}
						}
						// Alerta entrega Informe Final, 15 días después
						if (notificaciones == 3 && diasRestantes <= -15) {
							plantilla = 127;
							if(compAux.getProyecto().getEsContratoBiodiversidad()) {
								plantilla = 405;
							}
						}
					} else if (compAux.getTipoInforme().getId()
							.equals(TipoInforme.INFORME_AVANCE)) {
						// Alerta entrega Informe de Avance, 15 días antes
						if (notificaciones == 0 && diasRestantes <= 15) {
							plantilla = 123;
						}
						// Alerta entrega Informe de Avance, 1 día después
						if (notificaciones == 1 && diasRestantes <= -1) {
							plantilla = 124;
						}
						// Alerta entrega Informe de Avance, 15 días después
						if (notificaciones == 2 && diasRestantes <= -15) {
							plantilla = 124;
						}
					} else if (compAux.getTipoInforme().getId()
							.equals(TipoInforme.DESEMBOLSOS)) {
						// Alerta 15 días antes
						if (notificaciones == 0 && diasRestantes <= 15) {
							plantilla = 209;
						}
						// Alerta 1 día después
						if (notificaciones == 1 && diasRestantes <= -1) {
							plantilla = 211;
						}
						// Alerta 15 días después
						if (notificaciones == 2 && diasRestantes <= -15) {
							plantilla = 211;
						}
					} else if (compAux.getTipoInforme().getId()
							.equals(TipoInforme.OBLIGACIONES)) {
						// Alerta 15 días antes
						if (notificaciones == 0 && diasRestantes <= 15) {
							plantilla = 210;
						}
						// Alerta 1 día después
						if (notificaciones == 1 && diasRestantes <= -1) {
							plantilla = 212;
						}
						// Alerta 15 días después
						if (notificaciones == 2 && diasRestantes <= -15) {
							plantilla = 212;
						}
					}

					if (plantilla != 0) {
						totalMensajesAEnviar++;
						CorreoPlantilla correoPlantilla = cargarPlantilla(plantilla);
						String cuerpo = correoPlantilla.getCuerpo();
						String asunto = correoPlantilla.getAsunto();
						if(compAux.getProyecto().getResponsable() != null 
								&& compAux.getProyecto().getResponsable().getEmail() != null
								&& !compAux.getProyecto().getResponsable().getEmail().equals("")){
							Correo correo = new Correo();
							correo.setOrigen(Correo.CORREO_HERMES);
							correo.adicionarDireccion(compAux.getProyecto()
									.getResponsable().getEmail());
							//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
							asunto = asunto.replaceAll("<<ID>>", compAux
									.getProyecto().getId().toString());
							correo.setAsunto(asunto);
							String cuerpoCorreo = cuerpo.replaceAll("<<TITULO>>",
									compAux.getProyecto().getNombre());
							cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>",
									compAux.getProyecto().getId().toString());
							cuerpoCorreo = cuerpoCorreo.replaceAll(
									"<<TIPO_INFORME>>", compAux.getTipoInforme()
											.getNombre());
							SimpleDateFormat formatter = new SimpleDateFormat(
									"dd/MM/yyyy");
							cuerpoCorreo = cuerpoCorreo.replaceAll(
									"<<VENCIMIENTO>>",
									formatter.format(fechaVencimiento)
											+ " (día/mes/año)");
							if(compAux.getProyecto() != null && compAux.getProyecto().getModalidad() != null){
								if(compAux.getProyecto().getModalidad().getTipo().getId().equals(TIPO_MODALIDAD_PERMISO_MARCO)){
									cuerpoCorreo = cuerpoCorreo.replaceAll(
											"<<NOTA_BIODIVERSIDAD>>", "Recuerde que este proyecto está asociado a un Permiso Marco de Recolección. " +
													"Por lo tanto, debe estar al día en los compromisos de biodiversidad para entregar el informe final. " +
													"Si tiene alguna duda al respecto se puede comunicar con el Grupo de Trámites Ambientales en la extensión 20064.");
								}else{
									cuerpoCorreo = cuerpoCorreo.replaceAll(
											"<<NOTA_BIODIVERSIDAD>>", "");
								}
							}else{
								cuerpoCorreo = cuerpoCorreo.replaceAll(
										"<<NOTA_BIODIVERSIDAD>>", "");
							}
							correo.setCuerpo(cuerpoCorreo);
							System.out.println(fechaActual
									+ " Enviando correo alerta proyecto: "
									+ compAux.getProyecto().getId());

							if (servicioCorreo.enviarCorreo(correo)) {
								notificaciones++;
								compAux.setNumeroNotificaciones(new Integer(
										notificaciones));
								compAux.setFechaNotificacion(fechaActual);
								servicioGeneral.guardarObjeto(compAux);
								correosEnviados++;
							}
						}else{
							if(compAux.getProyecto().getResponsable() != null){
								System.out.println("Proyecto responsable no nulo: " + compAux.getProyecto().getId() + " INV_TP_DOC: " + compAux.getProyecto().getResponsable().getId().getTipoDocumento() + " INV_DOC: " + compAux.getProyecto().getResponsable().getId().getDocumento());
								if(compAux.getProyecto().getResponsable().getEmail() == null){
									System.out.println("Proyecto inv sin correo: " + compAux.getProyecto().getId() + " INV_TP_DOC: " + compAux.getProyecto().getResponsable().getId().getTipoDocumento() + " INV_DOC: " + compAux.getProyecto().getResponsable().getId().getDocumento());
								}
							}else{
								System.out.println("Proyecto con responsable null: "+ compAux.getProyecto().getId());
							}
						}
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
