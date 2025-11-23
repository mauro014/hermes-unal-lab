package co.edu.unal.hermes.vista.serviciosacademicos;

import java.util.ArrayList;

import javax.faces.context.FacesContext;

import co.edu.unal.hermes.bd.conexion.ConexionBDECP;
import co.edu.unal.hermes.modelo.Icono;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorInterventoriaSAC extends ManejadorBase{
	
	private Icono[] iconos;
	
	private ArrayList<Icono> iconosAgregados;

	public Icono[] getIconos() {
		return iconos;
	}

	public void setIconos(Icono[] iconos) {
		this.iconos = iconos;
	}
	
	public ManejadorInterventoriaSAC() {
		// TODO Auto-generated constructor stub
		personaActual = (Persona) sesion.getAttribute("persona");
		cargarIconos();
	}
	
	private void cargarIconos() {
		
		iconosAgregados = new ArrayList<Icono>();
		
		boolean esDecano = (Boolean) sesion.getAttribute("esDecano");
		boolean esVicerector = (Boolean) sesion.getAttribute("esVicerector");
		boolean esIndicadores = (Boolean) sesion.getAttribute("esIndicadores");

		if (esDecano) {
			iconosAgregados.add(new Icono("Educación contínua y permanente", "intervenUEC"));
			iconosAgregados.add(new Icono("Otras modalidades de extensión", "intervenSAC"));
		}
		if (esVicerector) {
			iconosAgregados.add(new Icono("Educación contínua y permanente", "intervenUEC"));
			iconosAgregados.add(new Icono("Otras modalidades de extensión", "intervenSAC"));
		}
		if (esIndicadores) {
			iconosAgregados.add(new Icono("Educación contínua y permanente", "intervenUEC"));
			iconosAgregados.add(new Icono("Otras modalidades de extensión", "intervenSAC"));
		}

		iconos = new Icono[iconosAgregados.size()];
		for (int i = 0; i < iconosAgregados.size(); i++) {
			iconos[i] = iconosAgregados.get(i);
		}
	}

	public void accion(){
		FacesContext context = FacesContext.getCurrentInstance();
	    Icono item = context.getApplication().evaluateExpressionGet(context, "#{iconos}", Icono.class);
	    String accion = item.getAccion();
		
		String url = "";
		
		if(accion == "intervenUEC"){
		
		System.out.println("login UEC user: " + personaActual.getUid().toUpperCase());
		String sql = "SELECT '"+ConexionBDECP.APEX_PROD+"f?p=198:701:::::P701_USUARIO_HERMES,F198_HERMES_BRANCH:'||uec_schema.uecf_hash_hermes(upper(trim('" + personaActual.getUid().toUpperCase() + "')),'USUARIOECP')||',310' as ENLACE from dual";
		
		 	url = ConexionBDECP.execQueryLink(sql);
		}
		else if(accion == "intervenSAC"){
			System.out.println("login SAC user: " + personaActual.getUid().toUpperCase());
			String sql = "SELECT '"+ConexionBDECP.APEX_PROD+"f?p=111:701:::::P701_USUARIO_HERMES,F209_HERMES_BRANCH:'||sac_schema.sieif_hash_hermes(upper(trim('" + personaActual.getUid().toUpperCase() + "')),'USUARIOSAC')||',361' as ENLACE from dual";
			
			url = ConexionBDECP.execQueryLink(sql);
		}
		
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			fc.getExternalContext().redirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
