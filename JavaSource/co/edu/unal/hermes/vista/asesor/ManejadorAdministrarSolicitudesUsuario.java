package co.edu.unal.hermes.vista.asesor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.SolicitudPersonaRol;
import co.edu.unal.hermes.modelo.SolicitudUsuario;
import co.edu.unal.hermes.modelo.TipoCargo;
import co.edu.unal.hermes.modelo.TipoDedicacion;
import co.edu.unal.hermes.modelo.TipoFormacion;
import co.edu.unal.hermes.modelo.TipoVinculacion;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorAdministrarSolicitudesUsuario extends ManejadorBase {

	List listaSolicitudesAdministrar;
	List listaRoles;
	List investigadorInterno;
	List investigador;
	List personaSeleccionada;

	private Boolean respuesta = false;
	private IdPersona idpersona;
	private Long id;
	private Rol rolSeleccionado;

	private Long idSolicitud;
	private String observacion;
	private String correo;
	private String nom1;
	private String nom2;
	private String apell1;
	private String apell2;
	private SolicitudUsuario solicitudadmin;
	private Persona solicitudadminSol;
	private Persona personaActual;
	
	public ManejadorAdministrarSolicitudesUsuario() {
		sesion.removeAttribute("manejadorAdministrarSolicitudesUsuario");
		personaActual = (Persona) sesion.getAttribute("persona");

		listaSolicitudesAdministrar = new ArrayList();
		listaRoles = new ArrayList();
		sesion.setAttribute("mensajeTramite", "");

		id = (Long) sesion.getAttribute("solicitudId");
		if(id!=null){
			listaSolicitudesAdministrar = servicioGeneral
					.obtenerListaObjetos("SolicitudUsuario where id = '" + id + "'");
			if(listaSolicitudesAdministrar.size()>0){
				solicitudadmin = (SolicitudUsuario) listaSolicitudesAdministrar.get(0);
				if(solicitudadmin!=null && solicitudadmin.getSolicDocumento()!=null){
					IdPersona idPer = new IdPersona(solicitudadmin.getSolicDocumento(), solicitudadmin.getSolicTipoDocumento());
					solicitudadminSol = servicioPersona.obtenerPersona(idPer);
					
					listaRoles = servicioGeneral
							.obtenerListaObjetos("SolicitudPersonaRol where idSolicitud = '"	+ id + "'");
					
				}
				
			}	
		}
		
		//String mostrar = (String) sesion.getAttribute("mensajeTramite");
        
		/*if(mostrar!=null){
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, mostrar,""));
			System.out.println(mostrar);
		}*/
		

	}

	
	//Cambiar valores de la solicitud
	public void cambiarValoresSolicitud() {
		
		solicitudadmin.setComentarios(this.observacion);
		Date fechaRevision = new Date();
		solicitudadmin.setFechaAprobacion(fechaRevision);
		//Responsable solicitud					
		solicitudadmin.setResponsableDocumento(personaActual.getId().getDocumento());
		solicitudadmin.setResponsableTipoDocumento(personaActual.getId().getTipoDocumento());
	}

	//Aprobar solicitudes
	public void aprobarSolicitud() {
		//Estado
		solicitudadmin.setEstadoSol("A"); //Aprobada
		//Cambiar Valores de aprobacion
		cambiarValoresSolicitud();
		
		//Actualizar roles
		try{
			String identificacionPersona;
			String tipoDocumento;
			identificacionPersona = solicitudadmin.getUsuarioDocumento();
			tipoDocumento = solicitudadmin.getUsuarioTipoDocumento();
			String rol = new String();
			String accion = new String();
			SolicitudPersonaRol solicitudRoles = new SolicitudPersonaRol();
			
			if(listaRoles!=null)
			{
				java.util.Iterator it = listaRoles.iterator();
				// itera para crear los roles
				while (it.hasNext()) {
					solicitudRoles = (SolicitudPersonaRol) it.next();
					rol = solicitudRoles.getRol().getId();
					accion = solicitudRoles.getAccion();
					PersonaRol perRol = new PersonaRol();
					perRol.setNombre(rol);
					perRol.setDocumento(identificacionPersona);
					perRol.setTipoDocumento(tipoDocumento);
					perRol.setFechaInicioRol(new Date());
					perRol.setFechaFinRol(solicitudadmin.getFechaFin());
					
					// acción para crear ó borrar los roles
					if (accion.equals("A")) { //Agregar
						servicioPersona.crearRoles(perRol);
					} else if (accion.equals("R")) { //borrar
						if(!rol.equals("I")){
							servicioPersona.borrarRoles(perRol);
							
						}
					}

				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error actualizando roles");
		}
		
		//Guardar Solicitud
		try{
			servicioGeneral.guardarObjeto(solicitudadmin);
			//Mensaje
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("Se ha aprobado la Solicitud");
			context.addMessage("msgs", mensaje);
			
			//Enviar correo de rta
			respuesta = true;
			enviarRespuesta();
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error guardando solicitud rol - manejador admin roles");
		}
		
	}
	
	//Negar solicitudes
	public void negarSolicitud() {
		//Estado
		solicitudadmin.setEstadoSol("N"); //Negada
		//Cambiar Valores de aprobacion
		cambiarValoresSolicitud();
		
		//Guardar Solicitud
		try{
			servicioGeneral.guardarObjeto(solicitudadmin);
			//Mensaje
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("Se ha negado la Solicitud");
			context.addMessage("msgs", mensaje);
			
			//Enviar correo de rta
			respuesta = false;
			enviarRespuesta();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//Enviar correo de rta de revision de solicitud
	public void enviarRespuesta() {
		String correoAsesor;
		String cuerpoCorreo;
		
		//Valores correo
		Correo correo = new Correo();
		correo.setOrigen(Correo.CORREO_HERMES);
		correo.adicionarDireccion(this.correo);
		
		// adicionar a copias a Hermes y asesor
		correoAsesor = personaActual.getEmail();
		correo.adicionarCopiaOculta(correoAsesor);
		//correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
		correo.setAsunto(cargarPlantilla(190).getAsunto());
		cuerpoCorreo = cargarPlantilla(190).getCuerpo().replaceAll(
						"<<INVESTIGADOR>>",
						solicitudadmin.getNombre1() + " " + solicitudadmin.getNombre2() + " "
								+ solicitudadmin.getApellido1() + " "
								+ solicitudadmin.getApellido2());
		//Observaciones
		if(this.observacion!=null && !this.observacion.equals("")){
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<OBSERVACION>>", this.observacion);
		}else{
			cuerpoCorreo = cuerpoCorreo.replaceAll("Se realiza la siguiente observación:", "");
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<OBSERVACION>>", "");
		}
		
		//Roles
		String rolesSolicitud = ""; //String con todos los roles de la solicitud
		if(listaRoles!=null){
			java.util.Iterator it = listaRoles.iterator(); 
			
			// itera para recuperar los roles de la solicitud
			while (it.hasNext()) {
				SolicitudPersonaRol solicitudRoles = (SolicitudPersonaRol) it.next();
				String accion = solicitudRoles.getAccion();
				
				if (accion.equals("A")) {
					 accion = "Agregar";
				}else{
					 accion = "Eliminar";
				}
							
				rolesSolicitud = rolesSolicitud  +
								(solicitudRoles.getRol().getNombre() + "(" + accion + ")" + ", ");			
				System.out.println(rolesSolicitud);
	
			}
		}
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<ROLES>>", rolesSolicitud);
		
		if(respuesta){
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<RESPUESTA>>","Aprobada");
		}else{
			cuerpoCorreo = cuerpoCorreo.replaceAll("<<RESPUESTA>>","Negada");
		}
		
		//Enviar correo
		correo.setCuerpo(cuerpoCorreo);
		System.out.println(cuerpoCorreo);
		servicioCorreo.enviarCorreo(correo);
		
		//Limpiar manejadores
		sesion.removeAttribute("manejadorConsultarSolicitudesUsuario");
		sesion.removeAttribute("manejadorConsultarHistoricoSolicitudesUsuario");
		sesion.removeAttribute("manejadorAdministrarSolicitudesUsuario");
	}

	// metodo para crear investigador interno
	public void crearInvInterno(Dependencia depend, IdPersona idPersona) {
		try {
			// crear el investigador interno
			InvestigadorInterno investigadori = new InvestigadorInterno();
			investigadori.setDependencia(depend);

			investigadori.setId(idPersona);

			TipoDedicacion tipodedicacion = new TipoDedicacion();
			tipodedicacion.setId(".");
			investigadori.setTipoDedicacion(tipodedicacion);

			TipoFormacion tipoformacion = new TipoFormacion();
			tipoformacion.setId(".");
			investigadori.setTipoFormacion(tipoformacion);

			TipoVinculacion tipovinculacion = new TipoVinculacion();
			tipovinculacion.setId("30");
			investigadori.setTipoVinculacion(tipovinculacion);

			TipoCargo tipocargo = new TipoCargo();
			tipocargo.setId(".");
			investigadori.setTipoCargo(tipocargo);

			Dependencia dependencia2 = new Dependencia();
			dependencia2.setId(".");
			investigadori.setDependencia2(dependencia2);

			Long hora = new Long(0);
			investigadori.setValorHora(hora);

			servicioPersona.insertaInterno(investigadori);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// metodo para crear investigador
	public void crearInv(IdPersona idPersona) {
		try {
			// crear el investigador
			Investigador investigadorCrear = new Investigador();
			investigadorCrear.setEvaluador("N");
			investigadorCrear.setEsFuncionario("N");
			// mirar S o N
			investigadorCrear.setInterno("S");
			investigadorCrear.setId(idPersona);

			servicioPersona.insertarInvestigador(investigadorCrear);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public List getlistaRoles() {
		return listaRoles;
	}

	public void setlistaRoles(List listaRoles) {
		this.listaRoles = listaRoles;
	}

	public List getListaSolicitudesAdministrar() {
		return listaSolicitudesAdministrar;
	}

	public void setListaSolicitudesAdministrar(List listaSolicitudesAdministrar) {
		this.listaSolicitudesAdministrar = listaSolicitudesAdministrar;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public SolicitudUsuario getSolicitudadmin() {
		return solicitudadmin;
	}

	public void setSolicitudadmin(SolicitudUsuario solicitudadmin) {
		this.solicitudadmin = solicitudadmin;
	}

	public Persona getSolicitudadminSol() {
		return solicitudadminSol;
	}

	public void setSolicitudadminSol(Persona solicitudadminSol) {
		this.solicitudadminSol = solicitudadminSol;
	}

}