package co.edu.unal.hermes.vista.correos;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.EstadoCarta;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProyectoCarta;
import co.edu.unal.hermes.modelo.TipoCarta;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorEnviarAlertasActasInicio extends ManejadorBase{
	
	private String resultadoEnvio;
	private static int PLANTILLA_CORREO = 319;
	private Date fechaActual;
	private String fechaFormato;
	
	public ManejadorEnviarAlertasActasInicio(){
		super();
		resultadoEnvio = "";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		fechaActual = new Date();
		fechaFormato = formatter.format(fechaActual);
		
	}
	
	public void enviarAlertas() throws IOException {
		try {

			String hql = "from ProyectoCarta pc WHERE pc.carta.id = " + TipoCarta.INICIO +
					" AND pc.estadoCarta.id = 'G'" +
					" AND pc.proyecto.estadoProyecto.id = '" + EstadoProyecto.APROBADO + "'" +
					" AND to_date(pc.proyecto.fechaTentativaInicio,'dd/mm/yyyy') < to_date('" + fechaFormato + "','dd/mm/yyyy')";
			System.out.println(hql);

			List<ProyectoCarta> listaProyectos = new ArrayList<ProyectoCarta>();
			listaProyectos = servicioGeneral.obtenerObjetos(ProyectoCarta.class, hql);
			System.out.println("tamLista " + listaProyectos.size());
			
			int correosEnviados = 0;
			int totalMensajesAEnviar = 0;
			boolean enviar = true;
			for (int i = 0; i < listaProyectos.size(); i++) {
				ProyectoCarta proyAux = (ProyectoCarta) listaProyectos.get(i);
				Date fechaActual = new Date();
				Date fechaInicio = proyAux.getProyecto().getFechaTentativaInicio();
				long diasSinAceptar = (fechaActual.getTime() - fechaInicio.getTime()) / 86400000; // 1000 * 60 * 60 * 24
				int notificaciones = 0;
				if(proyAux.getNumNotificaciones() != null){
					notificaciones = proyAux.getNumNotificaciones().intValue();	
				}				
				
				if(diasSinAceptar > 14){
					totalMensajesAEnviar++;					
					CorreoPlantilla correoPlantilla = cargarPlantilla(PLANTILLA_CORREO);
					String cuerpo = correoPlantilla.getCuerpo();
					String asunto = correoPlantilla.getAsunto();
					Correo correo = new Correo();
					correo.setOrigen(Correo.CORREO_HERMES);
					//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
					asunto = asunto.replaceAll("<<ID>>", proyAux.getProyecto().getId().toString());
					correo.setAsunto(asunto);
					String cuerpoCorreo = cuerpo.replaceAll("<<TITULO>>", proyAux.getProyecto().getNombre());
					cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", proyAux.getProyecto().getId().toString());
					
					Persona coordinadorPry = servicioProyecto.obtenerCoordinadorProyecto(proyAux.getProyecto().getId());
					Investigador inv = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(proyAux.getProyecto().getId());
					
					if(inv != null 
							&& inv.getEmail() != null
							&& !inv.getEmail().equals("")){
						correo.adicionarDireccion(inv.getEmail());
						System.out.println("Correo inves: " +  inv.getEmail());
					}else{
						enviar = false;
						System.out.println("No email investigador");
					}
					if(coordinadorPry != null 
							&& coordinadorPry.getEmail() != null 
							&& !coordinadorPry.getEmail().equals("")){
						correo.adicionarCopiaOculta(coordinadorPry.getEmail());
						cuerpoCorreo = cuerpoCorreo.replaceAll(
								"<<COORDINADOR>>", coordinadorPry.getNombreCompleto());
						System.out.println("Correo coord: " + coordinadorPry.getEmail());
					}else{
						enviar = false;
						System.out.println("No email coordinador");
					}
					correo.setCuerpo(cuerpoCorreo);
					System.out.println(fechaActual
							+ " Enviando correo alerta proyecto acta de inicio: "
							+ proyAux.getProyecto().getId());
					if(enviar){
						if (servicioCorreo.enviarCorreo(correo)) {
							notificaciones++;
							proyAux.setNumNotificaciones(new Integer(notificaciones));
							proyAux.setFechaUltimaNotificacion(fechaActual);
							servicioGeneral.guardarObjeto(proyAux);
							correosEnviados++;
						}else{
							System.out.println("No se pudo enviar alerta de aceptación de acta de inicio pendiente");
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

	public String getResultadoEnvio() {
		return resultadoEnvio;
	}

	public void setResultadoEnvio(String resultadoEnvio) {
		this.resultadoEnvio = resultadoEnvio;
	}
	
}
