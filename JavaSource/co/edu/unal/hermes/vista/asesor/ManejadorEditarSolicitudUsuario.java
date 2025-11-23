package co.edu.unal.hermes.vista.asesor;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.SolicitudPersonaRol;
import co.edu.unal.hermes.modelo.SolicitudUsuario;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorEditarSolicitudUsuario extends ManejadorBase implements Serializable {
	
	SolicitudUsuario solicitudUsuarioEdit; // clase
	SolicitudPersonaRol solicitudPRol;
	SolicitudPersonaRol sperRol1;
	SolicitudPersonaRol sperRol2;
	
	
	List<TipoDocumento> listaTipoDocumento;
	List<TipoDocumento> tipoD;
	List<Dependencia> depEd;
	List<Dependencia> dependenciasUN;
	List<Rol> listaRoles;
	
	private List<PersonaRol> rolesAntes;
	private List<Rol> rolesAntes2;
	private List<Rol> rolesActuales;
	private List<Rol> todRolesSol;
	private List<Rol> listarno;
	
	private SelectItem[] tipoDocumentoItem;
	private SelectItem[] dependenciaItem;
	private SelectItem[] rolesItem;
	
	private SelectItem[] rolesItemArray;
	private String[] rolesSeleccionadosArray;
	
	private String[] rolesAntesArray;
	private String[] rolesActualesArray;
	
	private String[] todRolesArray;
	private String[] rolesNoSeleccionadosArray;

	private Dependencia facultadSel;
	private String facultadSelec;
	private CategoriaInvestigador solicitudSeleccionada;
	private List<Rol> rolesSolicitados;
	private String[] rolesSolicitadosArray;
	private List facultadSel2;
	private String tdoc;
	private String dep;
	private String depId;
	
	
	List listaRolesSolicitud = new ArrayList();
	private List listaRoles1;
	private List<Rol> rol2;
	
	private Rol rolAux;
	private Rol rolAux2;
	
	//correo
	private String correo;
	private boolean respuesta;
	private boolean mostrarResp = false;
	private String observacion;
	
	public ManejadorEditarSolicitudUsuario() {
		//toma los datos de cons
		cargarListas();

		Long idSolicitudU = (Long) sesion.getAttribute("solicitudUEditable");
		
		if (idSolicitudU != null) {
			List lista = servicioGeneral.obtenerObjetoXID("SolicitudUsuario",
					idSolicitudU.toString());
			if (lista.size() > 0) {
				solicitudUsuarioEdit = (SolicitudUsuario) lista.get(0);
				
				tipoD = new ArrayList();
				tdoc = new String();
				tipoD = servicioGeneral.obtenerObjetos("select e from TipoDocumento e where e.id='"+solicitudUsuarioEdit.getUsuarioTipoDocumento()+"'");
				
				tdoc= ((tipoD.get(0)).getNombre());
				System.out.println(tdoc);	
				
				dep = new String();
				
				IdPersona id = new IdPersona(solicitudUsuarioEdit.getUsuarioDocumento(), solicitudUsuarioEdit.getUsuarioTipoDocumento());
				Dependencia dependencia = servicioDependencia.obtenerDependencia(id);
				if( dependencia!=null)
				{
					dep = dependencia.getNombre();
					setDepId(dependencia.getId());
				}else{
					dep = "";
					depId="0";
				}
				System.out.println(dep);	
				
				
				
				listaRolesSolicitud = servicioGeneral.obtenerListaObjetos("SolicitudPersonaRol where idSolicitud ='"+solicitudUsuarioEdit.getId()+"'");
				//solicitudes de rol
				System.out.println(idSolicitudU);
				System.out.println("****************antes ya estaban en la solicitud*************************");
				System.out.println(listaRolesSolicitud.size());
			}

		}
		mostrarCampos();
		cargarRol();
		
	}
	
	
	
	private void cargarListas() {

		// Listas
		// Tipos Documento

		listaTipoDocumento = new ArrayList<TipoDocumento>();
		listaTipoDocumento = servicioGeneral
				.obtenerListaObjetos(TipoDocumento.class);

		TipoDocumento tdAux = listaTipoDocumento.get(0);

		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];

		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
			td = null;
		}

		// Dependencias


		dependenciasUN = new ArrayList<Dependencia>();
		dependenciasUN = servicioGeneral
				.obtenerObjetos("select e from Dependencia e where e.esFacultad='Y' and e.estado='A' order by e.nombre");
		dependenciaItem = new SelectItem[dependenciasUN.size()];
		for (int i = 0; i < dependenciasUN.size(); i++) {
			Dependencia dd = (Dependencia) dependenciasUN.get(i);
			dependenciaItem[i] = new SelectItem(dd.getId(), dd.getNombre());
		//	dd = null;
		
		}
	
		facultadSel = ((Dependencia) dependenciasUN.get(0));
		
		
		// Roles a escoger

		listaRoles = new ArrayList<Rol>();
		listaRoles = this.servicioPersona.obtenerRolesSolicitud(); // todos elegibles
																	
		Rol rAux = listaRoles.get(0);

		rolesItem = new SelectItem[listaRoles.size()];

		for (int i = 0; i < listaRoles.size(); i++) {
			Rol rd = (Rol) listaRoles.get(i);
			rolesItem[i] = new SelectItem(rd.getId(), rd.getNombre());
			rd = null;
		}
		
		//*** mostrar tipo doc
		
	}
	
	

//**** nuevo
	
	public void cargarRol() {

		rolesAntes2= new ArrayList<Rol>();
		
		rolesAntes = servicioGeneral.obtenerListaObjetos("PersonaRol where documento ='"+solicitudUsuarioEdit.getUsuarioDocumento()+"' and nombre in ('FA','UE','UA','AV','FO','PI','AF','AD','EF','MD','MV','A','I','C','EA','ED','JI','JU','AE','MF','CX','RD')" ); // todos los roles tiene

		for (int i = 0; i < rolesAntes.size(); i++) {
			String s = ((PersonaRol) rolesAntes.get(i)).getNombre();
			System.out.println(s);

			rol2 = servicioGeneral.obtenerObjetos("select e from Rol e where e.id='" +s+ "'");
			System.out.println(rol2.size());
			System.out.println((rol2.get(0).getNombre()));
			rolesAntes2.add(rol2.get(0));
			
		}

		
		
		rolesAntesArray = new String[rolesAntes2.size()];
		
		if (rolesAntes2 != null) {
			for (int i = 0; i < rolesAntes2.size(); i++) {
				rolesAntesArray[i] = ((Rol)rolesAntes2.get(i)).getId();
			}
			
		}
		else {
			System.out.println(rol2.size());
		}	
		
		System.out.println("****************ya tenia en el sistema*************************");
		System.out.println(rolesAntes2.size());
		
		System.out.println(rolesAntesArray.length);
		
		
	
		todRolesSol = this.servicioPersona.obtenerRolesSolicitud(); // todos elegibles			
		
		
//*************************		
		
		
		List  definitivos = new ArrayList();
		
		for (int i = 0; i < rolesAntes2.size(); i++) {
			definitivos.add(((Rol)rolesAntes2.get(i)));
		}
		
		
		HashMap<String, SolicitudPersonaRol> rolesSolicitudEliminados = new HashMap<String, SolicitudPersonaRol>();
		
		for (int i = 0; i < listaRolesSolicitud.size(); i++) {
			if(((SolicitudPersonaRol)listaRolesSolicitud.get(i)).getAccion().equals("A")){	
		    	definitivos.add(((SolicitudPersonaRol)listaRolesSolicitud.get(i)).getRol());
		    	
		    }
		        
		    if(((SolicitudPersonaRol)listaRolesSolicitud.get(i)).getAccion().equals("R")){		    	
		    	rolesSolicitudEliminados.put(((SolicitudPersonaRol)listaRolesSolicitud.get(i)).getRol().getId(), ((SolicitudPersonaRol)listaRolesSolicitud.get(i)));				   
		    }
		}
		
		 
		int i = 0; // guarda todos roles en list
		boolean bandera = false;
	
		for (Iterator it = listaRoles.iterator(); it.hasNext();) {
			
			Rol roles = (Rol) it.next();
			if(rolesSolicitudEliminados.containsKey(roles.getId())){
				bandera = true;
				definitivos.remove(roles);
     		}
			if(bandera){
				bandera = false;
			}

		}
	

		//*****************
		
		
		this.rolesItemArray = new SelectItem[todRolesSol.size()];
		for (int j = 0; j < todRolesSol.size(); j++) {
			rolesItemArray[j] = new SelectItem(((Rol)todRolesSol.get(j)).getId(), ((Rol)todRolesSol.get(j)).getNombre());        
		}
		

		
		List lista2 = new ArrayList();
		rolesSeleccionadosArray = new String[definitivos.size()];
	
		for (int k = 0; k < definitivos.size(); k++) {	
			System.out.println("*****");	
			System.out.println(definitivos.size());
				
			System.out.println(k);
			
			String aa =((Rol)definitivos.get(k)).getId();	
			List<Rol> laux = servicioGeneral.obtenerObjetos("select e from Rol e where e.id = '"+ aa +"'");
			
			Rol roles2 = new Rol();
			roles2 =((Rol)laux.get(0)) ;
			
			lista2.add(roles2.getId());
			rolesSeleccionadosArray[k] = roles2.getId();
			
			System.out.println(roles2.getId());
			
		}
	
		rolesSeleccionadosArray = (String[]) lista2.toArray(new String[0]);}

		
//****************		

		
	
	public void guardarRoles() {
		
		System.out.println("****************ojo*************************");
		System.out.println(rolesAntes2.size());
	
		
		System.out.println(rolesAntesArray.length);		
		
		
		
		List<String> rolesAntesTodos = Arrays.asList(rolesAntesArray); // todos antes
		
		HashMap<String, String> rolesAnt = new HashMap<String, String>();
		for (int ii = 0; ii < rolesAntesTodos.size(); ii++) {
			rolesAnt.put(rolesAntesTodos.get(ii), rolesAntesTodos.get(ii));
		}
		
		System.out.println("*******************antes todos**********************");
		System.out.println(rolesAntesTodos.size());

		System.out.println("*******************antes todos**********************");
		System.out.println(rolesAnt.values());
		
		
		// buscar roles en la bd y seleccionados
		List<String> rolesSolicitudTodos = Arrays.asList(rolesSeleccionadosArray); // todos los roles de la sol
		

		HashMap<String, String> rolesNuevo = new HashMap<String, String>();
		for (int i = 0; i < rolesSolicitudTodos.size(); i++) {
			rolesNuevo.put(rolesSolicitudTodos.get(i), rolesSolicitudTodos.get(i));
		}
		
		System.out.println("*******************sol todos**********************");
		System.out.println(rolesSolicitudTodos.size());

		System.out.println("*******************sol todos**********************");
		System.out.println(rolesNuevo.values());

		
	//	List<String> rolesActualesTodos = Arrays.asList(rolesActualesArray); // todos antes
		
//		HashMap<String, String> rolesActu = new HashMap<String, String>();
//		for (int i = 0; i < rolesActualesTodos.size(); i++) {
//			rolesActu.put(rolesActualesTodos.get(i), rolesActualesTodos.get(i));
//		}
		
	//********Roles que posee - Roles inscritos en solicitud anterior	
		
		
		try {
			servicioGeneral
			.eliminar("DELETE HER_SOL_PERSONA_ROL WHERE SLP_ID IS NOT NULL AND SLP_ID='"+solicitudUsuarioEdit.getId()+"'");
			} catch (SQLException e) {
			e.printStackTrace();
			}

				
		for (int j = 0; j < rolesAntesTodos.size(); j++) {

			if (!rolesNuevo.containsKey(rolesAntesTodos.get(j))) {
				rolAux= new Rol();
				rolAux.setId(rolesAntesTodos.get(j));
				rolAux.setNombre(rolesAntesTodos.get(j));
				
				SolicitudPersonaRol sperRol1 = new SolicitudPersonaRol();
				sperRol1.setIdSolicitud(solicitudUsuarioEdit.getId());
				sperRol1.setRol(rolAux);
				sperRol1.setAccion("R");
				System.out.println(sperRol1.getRol().getNombre());
				System.out.println(sperRol1.getAccion());
				servicioGeneral.guardarObjeto(sperRol1);
			}

		}

		for (int j = 0; j < rolesSolicitudTodos.size(); j++) {
			if (!rolesAnt.containsKey(rolesSolicitudTodos.get(j))) {
				
				rolAux2=new Rol();
				rolAux2.setId(rolesSolicitudTodos.get(j));
				rolAux2.setNombre(rolesSolicitudTodos.get(j));
								
				SolicitudPersonaRol sperRol2 = new SolicitudPersonaRol();
				sperRol2.setIdSolicitud(solicitudUsuarioEdit.getId());
				sperRol2.setRol(rolAux2);
				sperRol2.setAccion("A");
				System.out.println(sperRol2.getRol().getNombre());
				System.out.println(sperRol2.getAccion());
				servicioGeneral.guardarObjeto(sperRol2);
			}
		}

	}
	//****************************** correo
	
	
	// *******************************************************************
	
	public void respuestaGuardar(){
		
		String documento;
		String correoAsesor = new String();
		String nombreAsesor = new String();
		String nombreUsuario = new String();
		
		IdPersona idPersonaUsuario;
		IdPersona idPersonaAsesor;
		

		try {
			

			idPersonaAsesor = new IdPersona(solicitudUsuarioEdit.getSolicDocumento(), solicitudUsuarioEdit.getSolicTipoDocumento());
			correoAsesor = (String) servicioPersona.obtenerPersona(idPersonaAsesor).getEmail();
			nombreAsesor = (String) servicioPersona.obtenerPersona(idPersonaAsesor).getNombreCompleto();
			idPersonaUsuario = new IdPersona(solicitudUsuarioEdit.getUsuarioDocumento(), solicitudUsuarioEdit.getUsuarioTipoDocumento());
			nombreUsuario = (String) servicioPersona.obtenerPersona(idPersonaUsuario).getNombreCompleto();

			
			Correo correo = new Correo();
			correo.setOrigen(Correo.CORREO_HERMES);
			correo.adicionarDireccion(correoAsesor);
			correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
			correo.setAsunto(cargarPlantilla(195).getAsunto());
			documento = cargarPlantilla(195).getCuerpo()
						.replaceAll("<<ASESOR>>", nombreAsesor)
						.replaceAll("<<USUARIO>>",nombreUsuario)
						.replaceAll("<<ACCION>>", "creada")
						.replaceAll("<<COMPLEMENTO>>",
								"Puede continuar la edición de su solicitud a través del link Editar, en su solicitud ubicada en la sección Consultar Solicitud Usuario.");
			correo.setCuerpo(documento);
			servicioCorreo.enviarCorreo(correo);
			
		} catch (Exception e) {
			System.out.println(e.toString());
    	}
    }
        
	public void respuestaGuardarEnviar() {

		String documento2;
		String documento3;
		String correoAsesor = new String();
		String nombreAsesor = new String();
		String nombreUsuario = new String();
		String correoUsuario = new String();
		IdPersona idPersonaUsuario;
		IdPersona idPersonaAsesor;

		try {
			

			idPersonaAsesor = new IdPersona(solicitudUsuarioEdit.getSolicDocumento(), solicitudUsuarioEdit.getSolicTipoDocumento());
			correoAsesor = (String) servicioPersona.obtenerPersona(idPersonaAsesor).getEmail();
			nombreAsesor = (String) servicioPersona.obtenerPersona(idPersonaAsesor).getNombreCompleto();
			idPersonaUsuario = new IdPersona(solicitudUsuarioEdit.getUsuarioDocumento(), solicitudUsuarioEdit.getUsuarioTipoDocumento());
			correoUsuario = solicitudUsuarioEdit.getEmail();
			nombreUsuario = (String) servicioPersona.obtenerPersona(idPersonaUsuario).getNombreCompleto();

			// usuario
			
			Correo correo2 = new Correo();
			correo2.setOrigen(Correo.CORREO_HERMES);
			correo2.adicionarDireccion(correoUsuario);
			correo2.adicionarCopiaOculta(Correo.CORREO_HERMES);
			correo2.setAsunto(cargarPlantilla(196).getAsunto());
			documento2 = cargarPlantilla(196).getCuerpo().replaceAll("<<USUARIO>>", nombreUsuario)
					.replaceAll("<<ASESOR>>",nombreAsesor)
					.replaceAll("<<CORREOA>>", correoAsesor);
			correo2.setCuerpo(documento2);
			servicioCorreo.enviarCorreo(correo2);

			// asesor

			Correo correo3 = new Correo();
			correo3.setOrigen(Correo.CORREO_HERMES);
			correo3.adicionarDireccion(correoAsesor);
			correo3.adicionarCopiaOculta(Correo.CORREO_HERMES);
			correo3.setAsunto(cargarPlantilla(195).getAsunto());
			documento3 = cargarPlantilla(195)
					.getCuerpo()
					.replaceAll("<<USUARIO>>", nombreUsuario)
					.replaceAll("<<ASESOR>>", nombreAsesor)
					.replaceAll("<<ACCION>>", "enviada para revisión")
					.replaceAll("<<COMPLEMENTO>>",
							"Esta solicitud será evaluada y posteriormente se dará a conocer su respuesta.");
			correo3.setCuerpo(documento3);
			servicioCorreo.enviarCorreo(correo3);

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	//****************************** mostrar seguim
	
	private void mostrarCampos() {
		if (solicitudUsuarioEdit.getEstadoSol()!=null && solicitudUsuarioEdit.getEstadoSol().equals("A")|| solicitudUsuarioEdit.getEstadoSol().equals("N") ){
			mostrarResp = true;
		}else{
			mostrarResp = false;
		}
	}
	
	
	// ********************guardar

	public void guardarSolicitud() {
		try {

			List rolesSeleccionados = Arrays.asList(rolesSeleccionadosArray);
	
			
			solicitudUsuarioEdit.setDependencia(new Dependencia(depId));
			solicitudUsuarioEdit.setApellido1(solicitudUsuarioEdit.getApellido1());
			solicitudUsuarioEdit.setApellido2(solicitudUsuarioEdit.getApellido2());
			solicitudUsuarioEdit.setNombre1(solicitudUsuarioEdit.getNombre1());
			solicitudUsuarioEdit.setNombre2(solicitudUsuarioEdit.getNombre2());
			solicitudUsuarioEdit.setEmail(solicitudUsuarioEdit.getEmail());
			

			servicioGeneral.guardarObjeto(solicitudUsuarioEdit);

			if (solicitudUsuarioEdit.getId() != null) {
				guardarRoles();
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage mensaje = new FacesMessage(
						"La información ha sido editada correctamente con el número "
								+ solicitudUsuarioEdit.getId());
				context.addMessage("datosGuardados", mensaje);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}



		public void guardarEnviarSolicitud() {
			try {
				
				List rolesSeleccionados = Arrays.asList(rolesSeleccionadosArray);
				Investigador inv = null;
				solicitudUsuarioEdit.setDependencia(new Dependencia(depId));
				solicitudUsuarioEdit.setApellido1(solicitudUsuarioEdit.getApellido1());
				solicitudUsuarioEdit.setApellido2(solicitudUsuarioEdit.getApellido2());
				solicitudUsuarioEdit.setNombre1(solicitudUsuarioEdit.getNombre1());
				solicitudUsuarioEdit.setNombre2(solicitudUsuarioEdit.getNombre2());
				solicitudUsuarioEdit.setEmail(solicitudUsuarioEdit.getEmail());
				
			
			solicitudUsuarioEdit.setEstadoSol("P");
			servicioGeneral.guardarObjeto(solicitudUsuarioEdit);
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(
					"La información ha sido enviada correctamente para revisión con el número "
							+ solicitudUsuarioEdit.getId());
			context.addMessage("datosGuardados", mensaje);
			respuestaGuardarEnviar();

			sesion.removeAttribute("ManejadorConsultarSolicitudUsuario");
			if (solicitudUsuarioEdit.getId() != null) {
				guardarRoles();
				

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		}

	
	
	
//**********************************************************************	
	

	public SolicitudUsuario getSolicitudUsuarioEdit() {
		return solicitudUsuarioEdit;
	}



	public void setSolicitudUsuarioEdit(SolicitudUsuario solicitudUsuarioEdit) {
		this.solicitudUsuarioEdit = solicitudUsuarioEdit;
	}



	public SolicitudPersonaRol getSolicitudPRol() {
		return solicitudPRol;
	}



	public void setSolicitudPRol(SolicitudPersonaRol solicitudPRol) {
		this.solicitudPRol = solicitudPRol;
	}



	public SolicitudPersonaRol getSperRol1() {
		return sperRol1;
	}



	public void setSperRol1(SolicitudPersonaRol sperRol1) {
		this.sperRol1 = sperRol1;
	}



	public SolicitudPersonaRol getSperRol2() {
		return sperRol2;
	}



	public void setSperRol2(SolicitudPersonaRol sperRol2) {
		this.sperRol2 = sperRol2;
	}



	public List<TipoDocumento> getListaTipoDocumento() {
		return listaTipoDocumento;
	}



	public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}



	public List<Dependencia> getDependenciasUN() {
		return dependenciasUN;
	}



	public void setDependenciasUN(List<Dependencia> dependenciasUN) {
		this.dependenciasUN = dependenciasUN;
	}



	public List<Rol> getListaRoles() {
		return listaRoles;
	}



	public void setListaRoles(List<Rol> listaRoles) {
		this.listaRoles = listaRoles;
	}



	public List<Rol> getRolesActuales() {
		return rolesActuales;
	}



	public void setRolesActuales(List<Rol> rolesActuales) {
		this.rolesActuales = rolesActuales;
	}



	public List<Rol> getTodRolesSol() {
		return todRolesSol;
	}



	public void setTodRolesSol(List<Rol> todRolesSol) {
		this.todRolesSol = todRolesSol;
	}



	public List<Rol> getListarno() {
		return listarno;
	}



	public void setListarno(List<Rol> listarno) {
		this.listarno = listarno;
	}



	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}



	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}



	public SelectItem[] getDependenciaItem() {
		return dependenciaItem;
	}



	public void setDependenciaItem(SelectItem[] dependenciaItem) {
		this.dependenciaItem = dependenciaItem;
	}



	public SelectItem[] getRolesItem() {
		return rolesItem;
	}



	public void setRolesItem(SelectItem[] rolesItem) {
		this.rolesItem = rolesItem;
	}



	public SelectItem[] getRolesItemArray() {
		return rolesItemArray;
	}



	public void setRolesItemArray(SelectItem[] rolesItemArray) {
		this.rolesItemArray = rolesItemArray;
	}


	public String[] getRolesAntesArray() {
		return rolesAntesArray;
	}



	public void setRolesAntesArray(String[] rolesAntesArray) {
		this.rolesAntesArray = rolesAntesArray;
	}



	public String[] getRolesSeleccionadosArray() {
		return rolesSeleccionadosArray;
	}



	public void setRolesSeleccionadosArray(String[] rolesSeleccionadosArray) {
		this.rolesSeleccionadosArray = rolesSeleccionadosArray;
	}



	public String[] getTodRolesArray() {
		return todRolesArray;
	}



	public void setTodRolesArray(String[] todRolesArray) {
		this.todRolesArray = todRolesArray;
	}



	public String[] getRolesNoSeleccionadosArray() {
		return rolesNoSeleccionadosArray;
	}



	public void setRolesNoSeleccionadosArray(String[] rolesNoSeleccionadosArray) {
		this.rolesNoSeleccionadosArray = rolesNoSeleccionadosArray;
	}



	public CategoriaInvestigador getSolicitudSeleccionada() {
		return solicitudSeleccionada;
	}



	public void setSolicitudSeleccionada(CategoriaInvestigador solicitudSeleccionada) {
		this.solicitudSeleccionada = solicitudSeleccionada;
	}



	public String[] getRolesActualesArray() {
		return rolesActualesArray;
	}



	public void setRolesActualesArray(String[] rolesActualesArray) {
		this.rolesActualesArray = rolesActualesArray;
	}



	public String getFacultadSelec() {
		return facultadSelec;
	}



	public void setFacultadSelec(String facultadSelec) {
		this.facultadSelec = facultadSelec;
	}



	public List<Rol> getRolesSolicitados() {
		return rolesSolicitados;
	}



	public void setRolesSolicitados(List<Rol> rolesSolicitados) {
		this.rolesSolicitados = rolesSolicitados;
	}



	public String[] getRolesSolicitadosArray() {
		return rolesSolicitadosArray;
	}



	public void setRolesSolicitadosArray(String[] rolesSolicitadosArray) {
		this.rolesSolicitadosArray = rolesSolicitadosArray;
	}



	public void setFacultadSel(Dependencia facultadSel) {
		this.facultadSel = facultadSel;
	}



	public Dependencia getFacultadSel() {
		return facultadSel;
	}



	public List<PersonaRol> getRolesAntes() {
		return rolesAntes;
	}



	public void setRolesAntes(List<PersonaRol> rolesAntes) {
		this.rolesAntes = rolesAntes;
	}



	public List getFacultadSel2() {
		return facultadSel2;
	}



	public void setFacultadSel2(List facultadSel2) {
		this.facultadSel2 = facultadSel2;
	}



	public List getListaRolesSolicitud() {
		return listaRolesSolicitud;
	}



	public void setListaRolesSolicitud(List listaRolesSolicitud) {
		this.listaRolesSolicitud = listaRolesSolicitud;
	}



	public List getListaRoles1() {
		return listaRoles1;
	}



	public void setListaRoles1(List listaRoles1) {
		this.listaRoles1 = listaRoles1;
	}



	public List<Rol> getRolesAntes2() {
		return rolesAntes2;
	}



	public void setRolesAntes2(List<Rol> rolesAntes2) {
		this.rolesAntes2 = rolesAntes2;
	}



	public List<Rol> getRol2() {
		return rol2;
	}



	public void setRol2(List<Rol> rol2) {
		this.rol2 = rol2;
	}



	public Rol getRolAux() {
		return rolAux;
	}



	public void setRolAux(Rol rolAux) {
		this.rolAux = rolAux;
	}



	public Rol getRolAux2() {
		return rolAux2;
	}



	public void setRolAux2(Rol rolAux2) {
		this.rolAux2 = rolAux2;
	}



	public List<TipoDocumento> getTipoD() {
		return tipoD;
	}



	public void setTipoD(List<TipoDocumento> tipoD) {
		this.tipoD = tipoD;
	}



	public String getTdoc() {
		return tdoc;
	}



	public void setTdoc(String tdoc) {
		this.tdoc = tdoc;
	}



	public List<Dependencia> getDepEd() {
		return depEd;
	}



	public void setDepEd(List<Dependencia> depEd) {
		this.depEd = depEd;
	}



	public String getDep() {
		return dep;
	}



	public void setDep(String dep) {
		this.dep = dep;
	}



	public String getCorreo() {
		return correo;
	}



	public void setCorreo(String correo) {
		this.correo = correo;
	}



	public boolean isRespuesta() {
		return respuesta;
	}



	public void setRespuesta(boolean respuesta) {
		this.respuesta = respuesta;
	}



	public boolean isMostrarResp() {
		return mostrarResp;
	}



	public void setMostrarResp(boolean mostrarResp) {
		this.mostrarResp = mostrarResp;
	}



	public String getObservacion() {
		return observacion;
	}



	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}



	public String getDepId() {
		return depId;
	}



	public void setDepId(String depId) {
		this.depId = depId;
	}


}