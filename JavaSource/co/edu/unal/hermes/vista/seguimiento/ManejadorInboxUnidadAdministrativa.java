package co.edu.unal.hermes.vista.seguimiento;

import java.util.List;

import javax.faces.component.UIData;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import co.edu.unal.hermes.bd.conexion.ConexionBDECP;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.ProyectoCarta;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.vista.ManejadorBase;
import co.edu.unal.hermes.vista.evaluadores.ProyectoCoordinador;

public class ManejadorInboxUnidadAdministrativa extends ManejadorBase {
	
	private List listaSolicitudesAprobadas;
	private UIData tablaAlertasUA;
	private Persona personaActual;
	private Dependencia dependenciaUA;
	
	//Consultar Solicitudes


	private Long sCodigoSolicitud = -1L;
	private Solicitud solicitudSeleccionada;
	private Solicitud solicitudUsada;
	private Investigador inPalPry;
	private String nombreInvPal;
	private String nombreCoor;
	private ProyectoCarta archivoSeleccionado;

	private List listaArchivosSolicitudes;
	private HtmlDataTable tablaArchivosSolicitudes;
	
	private List listaProyectoCoordinador;
	
	private Persona coordinador;
	private boolean mostrarAprobacionUA = true;



	public ManejadorInboxUnidadAdministrativa(){
		
		personaActual = (Persona) sesion.getAttribute("persona");
		dependenciaUA = servicioDependencia.obtenerDependenciaPersona(personaActual.getId());
		solicitudSeleccionada = new Solicitud();
		listaSolicitudesAprobadas = servicioGeneral.obtenerSolicitudesAprobadasUnidadAdministrativa(dependenciaUA.getId());
		
		
	}
	

	public String consultarSolicitudes(){
		//asignar codigo de la solicitud
		sCodigoSolicitud  = solicitudUsada.getId();
		List solSel = servicioGeneral.obtenerObjetoXID("Solicitud", sCodigoSolicitud.toString());
		solicitudSeleccionada = (Solicitud)solSel.get(0);
		
		inPalPry = servicioProyecto.obtenerInvestigadorPrincipalXProyecto(solicitudSeleccionada.getProyecto().getId());
		nombreInvPal = inPalPry.getNombre1() + " " + inPalPry.getNombre2() + " " + inPalPry.getApellido1() + " " + inPalPry.getApellido2();
		listaArchivosSolicitudes = servicioGeneral.obtenerListaObjetos("ProyectoCarta pc where (pc.carta.id = '1' or pc.carta.id = '2' or pc.idSolicitud = '" + sCodigoSolicitud + "') and pc.proyecto.id = '" + solicitudSeleccionada.getProyecto().getId() + "'");
		listaProyectoCoordinador = servicioGeneral.obtenerListaObjetos("ProyectoCoordinador pco where pco.idProyecto = '" + solicitudSeleccionada.getProyecto().getId() + "'");
		ProyectoCoordinador coor = (ProyectoCoordinador)listaProyectoCoordinador.get(0);
		IdPersona coorId = new IdPersona();
		coorId.setDocumento(coor.getPerId());
		coorId.setTipoDocumento(coor.getTdoId());
		coordinador = servicioPersona.obtenerPersona(coorId);
		nombreCoor = coordinador.getNombre1() + " " + coordinador.getNombre2() + " " + coordinador.getApellido1() + " " + coordinador.getApellido2();

		return "consultarSolicitudesUnidadAdministrativa";		
	}
	
	public void descargarArchivoSolicitud(){
		FacesContext ctx = FacesContext.getCurrentInstance();

		Long id = archivoSeleccionado.getId();

		List archivos = servicioGeneral.obtenerObjetoXID("ProyectoCarta",
				id.toString());

		ProyectoCarta archivo = (ProyectoCarta) archivos.get(0);

		try {
			if (!ctx.getResponseComplete()) {
				HttpServletResponse response = (HttpServletResponse) ctx
						.getExternalContext().getResponse();
				response.setContentType("text/plain");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + archivo.getCarta().getNombre() + ".pdf\"");
				ServletOutputStream out = response.getOutputStream();
				out.flush();
				ctx.responseComplete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void confirmarSolicitudUA(){
		solicitudSeleccionada.setVistaUA("A");
		servicioGeneral.guardarObjeto(solicitudSeleccionada);
		mostrarAprobacionUA = false;
	}

	public void reiniciarVariablesSolicitud(){
		sCodigoSolicitud = -1L;
		solicitudSeleccionada = new Solicitud();
		inPalPry = new Investigador();
		nombreInvPal = "";
		nombreCoor = "";

		listaArchivosSolicitudes.clear();		
		listaProyectoCoordinador.clear();
		
		coordinador = new Persona();
	}
	
	public void limpiar() {

		sesion.removeAttribute("ManejadorInboxUnidadAdministrativa");

	}
	
	public String atras(){
		limpiar();
		return "inboxUnidadAdministrativa";
	}
	
	public void loginExtensionUA(){
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
	
	public List getListaSolicitudesAprobadas() {
		return listaSolicitudesAprobadas;
	}

	public void setListaSolicitudesAprobadas(List listaSolicitudesAprobadas) {
		this.listaSolicitudesAprobadas = listaSolicitudesAprobadas;
	}

	public UIData getTablaAlertasUA() {
		return tablaAlertasUA;
	}

	public void setTablaAlertasUA(UIData tablaAlertasUA) {
		this.tablaAlertasUA = tablaAlertasUA;
	}

	public Persona getPersonaActual() {
		return personaActual;
	}

	public void setPersonaActual(Persona personaActual) {
		this.personaActual = personaActual;
	}

	public Long getsCodigoSolicitud() {
		return sCodigoSolicitud;
	}

	public void setsCodigoSolicitud(Long sCodigoSolicitud) {
		this.sCodigoSolicitud = sCodigoSolicitud;
	}
		
	public Solicitud getSolicitudSeleccionada() {
		return solicitudSeleccionada;
	}

	public void setSolicitudSeleccionada(Solicitud solicitudSeleccionada) {
		this.solicitudSeleccionada = solicitudSeleccionada;
	}
	
	public Investigador getInPalPry() {
		return inPalPry;
	}

	public void setInPalPry(Investigador inPalPry) {
		this.inPalPry = inPalPry;
	}	

	public String getNombreInvPal() {
		return nombreInvPal;
	}

	public void setNombreInvPal(String nombreInvPal) {
		this.nombreInvPal = nombreInvPal;
	}

	public List getListaArchivosSolicitudes() {
		return listaArchivosSolicitudes;
	}

	public void setListaArchivosSolicitudes(List listaArchivosSolicitudes) {
		this.listaArchivosSolicitudes = listaArchivosSolicitudes;
	}

	public HtmlDataTable getTablaArchivosSolicitudes() {
		return tablaArchivosSolicitudes;
	}

	public void setTablaArchivosSolicitudes(HtmlDataTable tablaArchivosSolicitudes) {
		this.tablaArchivosSolicitudes = tablaArchivosSolicitudes;
	}	

	public List getListaProyectoCoordinador() {
		return listaProyectoCoordinador;
	}

	public void setListaProyectoCoordinador(List listaProyectoCoordinador) {
		this.listaProyectoCoordinador = listaProyectoCoordinador;
	}

	public Persona getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Persona coordinador) {
		this.coordinador = coordinador;
	}
	
	public String getNombreCoor() {
		return nombreCoor;
	}

	public void setNombreCoor(String nombreCoor) {
		this.nombreCoor = nombreCoor;
	}

	public boolean isMostrarAprobacionUA() {
		return mostrarAprobacionUA;
	}

	public void setMostrarAprobacionUA(boolean mostrarAprobacionUA) {
		this.mostrarAprobacionUA = mostrarAprobacionUA;
	}


	public void setSolicitudUsada(Solicitud solicitudUsada) {
		this.solicitudUsada = solicitudUsada;
	}


	public Solicitud getSolicitudUsada() {
		return solicitudUsada;
	}


	public void setArchivoSeleccionado(ProyectoCarta archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}


	public ProyectoCarta getArchivoSeleccionado() {
		return archivoSeleccionado;
	}


}
