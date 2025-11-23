package co.edu.unal.hermes.vista.serviciosacademicos;

import java.util.ArrayList;

import javax.faces.context.FacesContext;

import co.edu.unal.hermes.bd.conexion.ConexionBDECP;
import co.edu.unal.hermes.modelo.Icono;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorRedirect extends ManejadorBase {

	public String title = "Redirigiendo...";

	private Icono[] iconos;

	private ArrayList<Icono> iconosAgregados;

	public Icono[] getIconos() {
		return iconos;
	}

	public void setIconos(Icono[] iconos) {
		this.iconos = iconos;
	}

	public ManejadorRedirect() {
		personaActual = (Persona) sesion.getAttribute("persona");
		cargarIconos();
	}

	private void cargarIconos() {

		iconosAgregados = new ArrayList<Icono>();

		boolean esCentroExtension = (Boolean) sesion
				.getAttribute("esCentroExtension");
		boolean esInvestigador = (Boolean) sesion
				.getAttribute("esInvestigador");
		boolean esAvalDepartamento = (Boolean) sesion
				.getAttribute("esAvalDepartamento");

		if (esCentroExtension) {
			iconosAgregados.add(new Icono("Educación contínua y permanente",
					"irECP",Icono.SECCION_OTROS));
			iconosAgregados.add(new Icono("Servicios académicos",
					"irServiciosAcademicos",Icono.SECCION_OTROS));
		}
		else if (esInvestigador) {
			iconosAgregados.add(new Icono("Educación contínua y permanente",
					"irECP",Icono.SECCION_OTROS));
			iconosAgregados.add(new Icono("Servicios académicos",
					"irServiciosAcademicos",Icono.SECCION_OTROS));
		}
	    if (esAvalDepartamento){
			iconosAgregados.add(new Icono("Aprobar Avales",
					"irExtension",Icono.SECCION_OTROS));
		}

		iconos = new Icono[iconosAgregados.size()];
		for (int i = 0; i < iconosAgregados.size(); i++) {
			iconos[i] = iconosAgregados.get(i);
		}
	}

	public void accion() {
		FacesContext context = FacesContext.getCurrentInstance();
		Icono item = context.getApplication().evaluateExpressionGet(context,
				"#{iconos}", Icono.class);
		String accion = item.getAccion();

		String url = "";

		if (accion == "irECP") {
			try {
				System.out.println("login UEC user: "
						+ personaActual.getUid().toUpperCase());
				String sql = "SELECT '"+ConexionBDECP.APEX_PROD+"f?p=198:701:::::P701_USUARIO_HERMES,F198_HERMES_BRANCH:'||uec_schema.uecf_hash_hermes(upper(trim('"
						+ personaActual.getUid().toUpperCase()
						+ "')),'USUARIOECP')||',1' as ENLACE from dual";

				url = ConexionBDECP.execQueryLink(sql);
			} catch (Exception e) {
				url = "http://www.extension.hermes.unal.edu.co/apex/f?p=198:101";
			}
		} else if (accion == "irServiciosAcademicos") {
			try {
				System.out.println("login SAC user: "
						+ personaActual.getUid().toUpperCase());
				String sql = "SELECT '"+ConexionBDECP.APEX_PROD+"f?p=111:701:::::P701_USUARIO_HERMES,F209_HERMES_BRANCH:'||sac_schema.sieif_hash_hermes(upper(trim('"
						+ personaActual.getUid().toUpperCase()
						+ "')),'USUARIOSAC')||',1' as ENLACE from dual";

				url = ConexionBDECP.execQueryLink(sql);
			} catch (Exception e) {
				url = "http://www.extension.hermes.unal.edu.co/apex/f?p=111:101";
			}
		} else if (accion == "irExtension") {
			try {
				System.out.println("login UA aval user: "
						+ personaActual.getUid().toUpperCase());
				String sql = "SELECT '"+ConexionBDECP.APEX_PROD+"f?p=201:701:::::P701_USUARIO_HERMES:'||uec_schema.uecf_hash_hermes(upper(trim('"
						+ personaActual.getUid().toUpperCase()
						+ "')),'USUARIOECP') as ENLACE from dual";
				/*String sql = "SELECT '"+ConexionBDECP.APEX_PROD+"f?p=201:701:::::P701_USUARIO_HERMES,F201_HERMES_BRANCH:'||uec_schema.uecf_hash_hermes(upper(trim('"
						+ personaActual.getUid().toUpperCase()
						+ "')),'USUARIOECP')||',40' as ENLACE from dual";*/

				url = ConexionBDECP.execQueryLink(sql);
			} catch (Exception e) {
				url = "http://www.extnsion.hermes.unal.edu.co/apex/f?p=201:101";
			}
		}

		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			fc.getExternalContext().redirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
