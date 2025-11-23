package co.edu.unal.hermes.vista.correos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorEnviarAlertasCopiaProyectosLegalizacion extends ManejadorBase{
	
	private String resultadoEnvio;

	public ManejadorEnviarAlertasCopiaProyectosLegalizacion() {
		super();
		resultadoEnvio = "";
	}
	
	public void enviarAlertas() {
		try {			
			
			List listaProyectos = new ArrayList();
			
			String hql = "select #id p.id, #nombre p.nombre from Proyecto p, InvestigadorProyecto ip"
					+" WHERE p.creadorId is not null"
					+" AND p.creadorDocumento is not null"
					+" AND p.id = ip.proyecto.id" 									
					+" AND p.modalidad.id = 10"
					+" AND p.estadoProyecto.id = 'I'"
					+" AND ip.tipo.id = 'P'"
					+" order by p.id";
			System.out.println(hql);

			listaProyectos = servicioGeneral.obtenerObjetosLimitado(Proyecto.class, hql);			
			
			int correosEnviados = 0;
			int totalMensajesAEnviar = 0;
			int plantilla = 296;
			
			if(listaProyectos.size()>0){
				for (int i = 0; i < listaProyectos.size(); i++) {
                    Proyecto pry = (Proyecto) listaProyectos.get(i);
                    
                    Long idProyecto = pry.getId();
                    String titulo = pry.getNombre();
					
					Investigador directorProyecto =servicioProyecto.obtenerInvestigadorPrincipalXProyecto(idProyecto);
					
					String nombreCompletoDirector = directorProyecto.getNombre1() + " " + directorProyecto.getNombre2() + " " 
												  + directorProyecto.getApellido1() + " " + directorProyecto.getApellido2();
					
					totalMensajesAEnviar++;
					CorreoPlantilla correoPlantilla = cargarPlantilla(plantilla);
					String cuerpo = correoPlantilla.getCuerpo();
					String asunto = correoPlantilla.getAsunto();
					Correo correo = new Correo();
					correo.setOrigen(Correo.CORREO_HERMES);
					correo.adicionarDireccion(directorProyecto.getEmail());
					//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);					
					asunto = asunto.replaceAll("<<CODIGO>>", idProyecto.toString());
					correo.setAsunto(asunto);
					String cuerpoCorreo = cuerpo.replaceAll("<<TITULO>>",
							titulo);
					cuerpoCorreo = cuerpoCorreo.replaceAll("<<CODIGO>>",
							idProyecto.toString());
					cuerpoCorreo = cuerpoCorreo.replaceAll(
							"<<INVESTIGADOR>>", nombreCompletoDirector);										
					correo.setCuerpo(cuerpoCorreo);

					System.out.println(" Enviando correo alerta proyecto: "
							+ idProyecto);
												
					if (servicioCorreo.enviarCorreo(correo)) {						
						correosEnviados++;
					}					
				}
			}
									
			resultadoEnvio = correosEnviados + " de " + totalMensajesAEnviar
					+ " Correo(s) de Alerta Enviado(s).";
			FacesContext.getCurrentInstance().addMessage(
					"messageResultadoEnvio",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							resultadoEnvio, null));
		} catch (Exception mex) {
			resultadoEnvio = "Error al enviar correo(s) de Alerta: "
					+ mex.getMessage();
			mex.printStackTrace();
		}
		System.out.println(new Date() + " resultadoEnvio: " + resultadoEnvio);
	}

	public CorreoPlantilla cargarPlantilla(int cod_id) {
		CorreoPlantilla correoActualAux = new CorreoPlantilla();
		String hql = "FROM CorreoPlantilla WHERE id='" + cod_id + "'";
		List<CorreoPlantilla> lista = servicioGeneral.obtenerObjetos(
				CorreoPlantilla.class, hql);
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
