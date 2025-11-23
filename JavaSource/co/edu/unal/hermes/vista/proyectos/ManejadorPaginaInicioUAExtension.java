package co.edu.unal.hermes.vista.proyectos;

import javax.faces.context.FacesContext;

import co.edu.unal.hermes.bd.conexion.ConexionBDECP;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.vista.ManejadorBase;




public class ManejadorPaginaInicioUAExtension extends ManejadorBase {

	private String mensajeUAExtension;
	
	// CONSTRUCTOR
	public ManejadorPaginaInicioUAExtension() {
		mensajeUAExtension = "Por favor espere unos segundos.";
		loginExtensionUA();
	}
	
	
	public void loginExtensionUA(){
		personaActual = (Persona) sesion.getAttribute("persona");
		System.out.println("loginUA user: " + personaActual.getUid().toUpperCase());
		String sql = "SELECT '"+ConexionBDECP.APEX_PROD+"f?p=201:701:::::P701_USUARIO_HERMES:'||uec_schema.uecf_hash_hermes(upper(trim('" + personaActual.getUid().toUpperCase() + "')),'USUARIOECP') as ENLACE from dual";
		
		String url = ConexionBDECP.execQueryLink(sql);
		
		
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			fc.getExternalContext().redirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	public String getMensajeUAExtension()
	{
		return mensajeUAExtension;
	}


	public void setMensajeUAExtension(String mensajeUAExtension)
	{
		this.mensajeUAExtension = mensajeUAExtension;
	}
	
	
}
