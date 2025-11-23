package co.edu.unal.hermes.vista.asesor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.VEmpleadoSara;
import co.edu.unal.hermes.vista.ManejadorBase;


public class ManejadorBuscarUsuario extends ManejadorBase implements
		Serializable {

	private Persona persona;
	List<TipoDocumento> listaTipoDocumento;
	List<Dependencia> dependenciasUN;
	List<Dependencia> dependenciasUN_Deptos;
	List<Sede> sedesUN;
	List<Rol> listaRoles;
	private List<PersonaRol> rolesAntes;
	private List<Rol> todRolesSol;
	private List<Rol> listarno;
	private List usuariosDep;
	private List usuariosDatos;

	private Rol rolesCons;

	private SelectItem[] tipoDocumentoItem;
	private SelectItem[] dependenciaItem;
	private SelectItem[] dependenciaItem_Deptos;
	private SelectItem[] rolesItem;
	private SelectItem[] rolesItemArray;

	String sedeSel;
	private Dependencia facultadSel;
	private Dependencia deptoSel;
	private String deptoSeleccionado;
	String usuarioDocumento;
	String usuarioTipoDocumento;
	String usuarioEmail;
	String solicDocumento;
	TipoDocumento solicTipoDocumento;

	Dependencia dependencia;
	boolean mostrarDependencias;
	boolean mostrarpanelDoc = false;
	boolean mostrarpanelDep = false;
	boolean mostrarpanelDoc2 = false;
	boolean mostrarpanelDep2 = false;
	boolean mostrarForm;
	boolean mostrarpanelEmail;
	boolean mostrarUsuarios;
	boolean esInvestigadorInterno=false;
	boolean esDocenteActivo=false;

	String tipoBusqueda;
	private VEmpleadoSara empleado;

	String[] rolesAntesArray;
	List<Rol> rolesMostrar;
	private String rolSel;
	private String facultad;
	List<Rol> rol2;
	String role;
	private boolean esActivo;
	private List<Persona> listaPersonas;
	private Persona usuarioSeleccionado;
	private String dependenciaUsuario;
	private String dependenciaUsuario2;
	
	private String vinculacionDocente;
	private String dedicacionDocente;
	private String formacionDocente;
	private String tipoCargoDocente;
	private String valorHoraDocente;
	
	
	//TEMP
	private String ext;
	
	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public ManejadorBuscarUsuario() {
		esInvestigadorInterno = false;
		esDocenteActivo =  false;
		cargarValoresIniciales();

	}

	private void cargarValoresIniciales() {

		persona = new Persona();
		tipoBusqueda = new String();
		cargarListas();

	}

	private void cargarListas() {

		listaTipoDocumento = new ArrayList<TipoDocumento>();
		listaTipoDocumento = servicioGeneral.obtenerListaObjetos(TipoDocumento.class);

		TipoDocumento tdAux = listaTipoDocumento.get(0);

		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];

		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
			td = null;
		}
		
		// Dependencias
		dependenciasUN = new ArrayList<Dependencia>();
		dependenciasUN = servicioGeneral.obtenerObjetos("select e from Dependencia e where e.estado='A' order by e.nombre");
		dependenciaItem = new SelectItem[dependenciasUN.size() + 1];
		dependenciaItem[0] = new SelectItem("","Seleccione una dependencia a asociar");
		for (int i = 1; i < dependenciasUN.size() + 1; i++) {
			Dependencia dd = (Dependencia) dependenciasUN.get(i - 1);
			dependenciaItem[i] = new SelectItem(dd.getId(), dd.getNombre());
		}

		facultadSel = ((Dependencia) dependenciasUN.get(0));

		
		// Roles a escoger
		listaRoles = new ArrayList<Rol>();
		listaRoles = this.servicioPersona.obtenerRolesSolicitud(); // todos

		Rol rAux = listaRoles.get(0);
		rolesItem = new SelectItem[listaRoles.size()];

		for (int i = 0; i < listaRoles.size(); i++) {
			Rol rd = (Rol) listaRoles.get(i);
			rolesItem[i] = new SelectItem(rd.getId(), rd.getNombre());
			rd = null;
		}
		rolSel = ((Rol) listaRoles.get(0)).getId().toString();
	}
	
	public void buscarPersona() {
		rolesMostrar = new ArrayList<Rol>();
		esInvestigadorInterno = false;
		esDocenteActivo = false;
		
		if(mostrarpanelDoc){
			IdPersona id = new IdPersona();
			
			if(usuarioTipoDocumento!=null && !usuarioTipoDocumento.equals("0")){
				id.setDocumento(usuarioDocumento);
				id.setTipoDocumento(usuarioTipoDocumento);

				//persona = servicioPersona.obtenerPersonaRoles(id);
				persona = servicioPersona.obtenerPersona(id);
				
				if (persona != null) {
					mostrarForm = true;
//					cargarRol(persona.getId().getDocumento());
					cargarRolesUsuario(id);
					
					if (persona instanceof Investigador) {
	
						if (persona instanceof InvestigadorInterno) {
							esInvestigadorInterno = true;
							persona = servicioPersona.obtenerInvestigadorInternoCompleto(persona.getId());

							InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;
							//cargarRol();
							
							Boolean esInterno = false;
							if (!esNulo(investigadorInterno.getInterno())) {
							    esInterno = investigadorInterno.getInterno().equals("S");
							}

							Boolean esVinculacionDocente = false;
							if (!esNulo(investigadorInterno.getTipoVinculacion())) {
							    String tipoVinculacionId = investigadorInterno.getTipoVinculacion().getId();
							    esVinculacionDocente = tipoVinculacionId.equals("16") || tipoVinculacionId.equals("30") || tipoVinculacionId.equals("34");
							}
							
							Boolean existeRolI = false;
							for (Rol rol : rolesMostrar) {
							    if ("I".equals(rol.getId())) {
							        existeRolI = true;
							        break;
							    }
							}
							
							esDocenteActivo = esInterno && esVinculacionDocente && existeRolI;
							
							if(esDocenteActivo && !esNulo(investigadorInterno)) {
								vinculacionDocente = !esNulo(investigadorInterno.getTipoVinculacion()) ? investigadorInterno.getTipoVinculacion().getNombre() : "";
								dedicacionDocente = !esNulo(investigadorInterno.getTipoDedicacion()) ? investigadorInterno.getTipoDedicacion().getNombre() : "";
								formacionDocente = !esNulo(investigadorInterno.getTipoFormacion()) ? investigadorInterno.getTipoFormacion().getNombre() : "";
								tipoCargoDocente = !esNulo(investigadorInterno.getTipoCargo()) ? investigadorInterno.getTipoCargo().getNombre() : "";
								valorHoraDocente = !esNulo(investigadorInterno.getValorHora()) ? investigadorInterno.getValorHora().toString() : "";
							}
							
							if (investigadorInterno.getDependencia() != null && investigadorInterno.getDependencia().getFacultad() != null) {
								facultad = investigadorInterno.getDependencia().getFacultad().getId();
								dependencia = investigadorInterno.getDependencia();
								sedeSel = dependencia.getSede().getId().toString();
								mostrarDependencias = true;

								persona.setCoorIDDependencia(investigadorInterno.getDependencia().getId());
								persona.setCoorNombreDependencia(investigadorInterno.getDependencia().getNombre());
			
								//TEMP
								ext=investigadorInterno.getTelExtension();
								dependenciaUsuario=investigadorInterno.getDependencia().getId();
								dependenciaUsuario2=investigadorInterno.getDependencia2().getId();
								
							} else {
	
							}

						} else if (persona instanceof InvestigadorExterno) {
							
							persona = servicioPersona.obtenerInvestigadorExternoCompleto(persona.getId());
							facultad = persona.getCoorIDDependencia();
							mostrarForm = true;
							
							//cargarRol();
					} else {

					}

				} else {

					/*FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage mensaje = new FacesMessage("El usuario no es un investigador en el sistema. ");
					mensaje.setSeverity(mensaje.SEVERITY_ERROR);
					context.addMessage("msg", mensaje);
					mostrarForm = false;*/
				}

		   	  }else{
		   		FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage mensaje = new FacesMessage("El usuario ingresado no se encuentra registrado en el sistema. ");
				mensaje.setSeverity(mensaje.SEVERITY_ERROR);
				context.addMessage("msg", mensaje);
				mostrarForm = false;
		   	  }
			}
			
		
		}else if(usuarioEmail!=null){
			
			List<Persona> lista = servicioGeneral.obtenerObjetos("select p from Persona p where p.email like '%" + usuarioEmail +"%'");
			if (lista!=null && lista.size()>0){
				//persona = (Persona) lista.get(0);
				
				listaPersonas = new ArrayList<Persona>();
				listaPersonas.addAll(lista);
				mostrarUsuarios =  true;
				mostrarForm = false;
			}else{
		   		FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage mensaje = new FacesMessage("No se encuentran usuarios registrados en el sistema con el correo ingresado. ");
				mensaje.setSeverity(mensaje.SEVERITY_ERROR);
				context.addMessage("msg", mensaje);
				mostrarUsuarios =  false;
				mostrarForm = false;
		   	}
			
		}
	
	}
	
	
	
	public void editarUsuario() {
		mostrarForm = false;
		esInvestigadorInterno = false;
		try{
			if (usuarioSeleccionado != null) {

				IdPersona id = new IdPersona();
				id.setDocumento(usuarioSeleccionado.getId().getDocumento());
				id.setTipoDocumento(usuarioSeleccionado.getId().getTipoDocumento());

				//persona = servicioPersona.obtenerPersonaRoles(id);
				persona = servicioPersona.obtenerPersona(id);
				
				if (persona != null) {
					
//					cargarRol(persona.getId().getDocumento());
					cargarRolesUsuario(id);
					mostrarForm = true;

					//cargarRol(persona.getId().getDocumento());
					//if (persona instanceof Investigador) {
						
						

						if (persona instanceof InvestigadorInterno) {
							
							esInvestigadorInterno = true;
							persona = servicioPersona
									.obtenerInvestigadorInternoCompleto(persona.getId());

							InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;
							
							
							if (investigadorInterno.getDependencia() != null
									&& investigadorInterno.getDependencia()
											.getFacultad() != null) {
								
							
								facultad = investigadorInterno.getDependencia()
										.getFacultad().getId();
								dependencia = investigadorInterno.getDependencia();
								sedeSel = dependencia.getSede().getId().toString();
								mostrarDependencias = true;
										
								
								dependencia = investigadorInterno.getDependencia();
								persona.setCoorIDDependencia(investigadorInterno
										.getDependencia().getId());
								persona.setCoorNombreDependencia(investigadorInterno
										.getDependencia().getNombre());
			
								
							} else {
	
							}

						} else if (persona instanceof InvestigadorExterno) {
							
							persona = servicioPersona
									.obtenerInvestigadorExternoCompleto(persona.getId());
							facultad = persona.getCoorIDDependencia();
							mostrarForm = true;
							
					} 

				/*} else {

					FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage mensaje = new FacesMessage("El usuario no es un investigador en el sistema. ");
					mensaje.setSeverity(mensaje.SEVERITY_ERROR);
					context.addMessage("msg", mensaje);
					mostrarForm = false;
				}*/

		   	  	
				}else{
			   		FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage mensaje = new FacesMessage("No es posible editar la información del usuario. ");
					mensaje.setSeverity(mensaje.SEVERITY_ERROR);
					context.addMessage("msg", mensaje);
					mostrarForm = false;
		   	  }
			
	
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// ****
	public void actualizarUsuario(){
		
		if(persona!=null){
			
		try{
			
			if(dependenciaUsuario!=null && !dependenciaUsuario.equals("0") ){
				
				if(dependenciaUsuario2==null || dependenciaUsuario2.equals("0") ){
					dependenciaUsuario2 = dependenciaUsuario;	
				}
				
				Dependencia depInt = servicioDependencia.obtenerDependencia(dependenciaUsuario);
				Dependencia depInt2 = servicioDependencia.obtenerDependencia(dependenciaUsuario2);
				Investigador invest = servicioPersona.obtenerInvestigador(persona.getId());
				InvestigadorInterno interno = servicioPersona.obtenerInvestigadorInterno(persona.getId());
				
				if (invest != null) {

					if (interno != null) { 
						interno = servicioPersona.obtenerInvestigadorInterno(persona.getId());
						//Guardar interno					
						interno.setDependencia(depInt);
						interno.setDependencia2(depInt2);
						//TEMP
						interno.setTelExtension(ext);
						servicioPersona.guardarInvInterno(interno);
						
						//mensaje
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage mensaje = new FacesMessage("Se ha actualizado la vinculación del usuario. ");
						mensaje.setSeverity(mensaje.SEVERITY_INFO);
						context.addMessage("msg", mensaje);
					
					}else{// No interno								
						interno = new InvestigadorInterno();
						interno.setId(persona.getId());
						interno.setNombre1(persona.getNombre1());
						interno.setNombre2(persona.getNombre2());
						interno.setApellido1(persona.getApellido1());
						interno.setApellido2(persona.getApellido2());
						interno.setEmail(persona.getEmail());
						interno.setGenero(persona.getGenero());			
						interno.setDependencia(depInt);
						interno.setDependencia2(depInt2);
						//TEMP
						interno.setTelExtension(ext);
						servicioPersona.insertaInterno(interno);
						
						//mensaje
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage mensaje = new FacesMessage("Se ha actualizado la vinculación del usuario. ");
						mensaje.setSeverity(mensaje.SEVERITY_INFO);
						context.addMessage("msg", mensaje);
					}
					
					
					
					
				}else{ //No Investigador
					
					//Validar correo
					if(persona.getEmail()!=null){
						
						if(persona.getEmail().contains("unal")){
							// Investigador
							Investigador inv = new Investigador();
							inv.setInterno(Investigador.INTERNO);
							inv.setEvaluador(Investigador.NO_EVALUADOR);
							servicioPersona.guardarInvestigador(inv);
							
							//Interno
							interno = new InvestigadorInterno();
							interno.setId(persona.getId());
							interno.setDependencia(depInt);
							interno.setDependencia2(depInt2);
							//TEMP
							interno.setTelExtension(ext);
							servicioPersona.insertaInterno(interno);
							
							//mensaje
							FacesContext context = FacesContext.getCurrentInstance();
							FacesMessage mensaje = new FacesMessage("Se ha actualizado la vinculación del usuario. ");
							mensaje.setSeverity(mensaje.SEVERITY_INFO);
							context.addMessage("msg", mensaje);
							
						}else{
							//mensaje
							FacesContext context = FacesContext.getCurrentInstance();
							FacesMessage mensaje = new FacesMessage("No es posible cambiar la vinculación del usuario debido a que no cuenta con correo institucional. ");
							mensaje.setSeverity(mensaje.SEVERITY_ERROR);
							context.addMessage("msg", mensaje);
						}
					
					}else{
						//mensaje
						FacesContext context = FacesContext.getCurrentInstance();
						FacesMessage mensaje = new FacesMessage("No es posible cambiar la vinculación del usuario debido a que no cuenta con correo institucional. ");
						mensaje.setSeverity(mensaje.SEVERITY_ERROR);
						context.addMessage("msg", mensaje);
					}
					
					
				}
				
			}else{
				//mensaje
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage mensaje = new FacesMessage("Por favor seleccione la dependencia principal del usuario. ");
				mensaje.setSeverity(mensaje.SEVERITY_ERROR);
				context.addMessage("msg", mensaje);				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
		}else{ // persona null
			
		}
	}
	
	
	
	// *************************Roles****
	public void cargarRolesUsuario(IdPersona id) {
		try{
			rol2 = new ArrayList();
			rolesMostrar = new ArrayList();
			
			rol2 = servicioPersona.obtenerRols(id);
			if(rol2!=null){
				rolesMostrar.addAll(rol2);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void cargarRol(String id) {
		try{
			List lista2 = new ArrayList();
			rolesAntes = servicioGeneral
					.obtenerObjetos("select pr from PersonaRol pr where pr.documento ='" + id + "' and sysdate between pr.fechaInicioRol and pr.fechaFinRol");
	
			rol2 = new ArrayList();
			rolesMostrar = new ArrayList<Rol>();
			
			if (rolesAntes != null && rolesAntes.size()>0) {
				System.out.println(rolesAntes.size());
				rolesAntesArray = new String[rolesAntes.size()];
	
				for (int i = 0; i < rolesAntes.size(); i++) {
					PersonaRol perRol = rolesAntes.get(i);
					rolesAntesArray[i] = perRol.toString();
					rol2 = servicioGeneral.obtenerObjetos("select e from Rol e where e.id='"
									+ (perRol.getNombre() + "'"));
					Date fechaRol = null;
					try {
						IdPersona idUsuario = new IdPersona();
						idUsuario.setTipoDocumento(perRol.getTipoDocumento());
						idUsuario.setDocumento(perRol.getDocumento());
						fechaRol = servicioPersona.obtenerFechaFinRol(idUsuario, rol2.get(0).getId());
						if (fechaRol != null) {
							rol2.get(0).setFechaVencimientoPersonaRol(fechaRol);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					rolesMostrar.add(rol2.get(0));
					
				}
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// *****************************

	public void cambiarForm() {
		String tipoB = tipoBusqueda;
		if (tipoB.equals("busqDoc")) {
			
			mostrarpanelDoc = true;
			mostrarpanelDep = false;
			mostrarForm = false;
			mostrarpanelEmail = false;
			mostrarUsuarios =  false;

		}
		if (tipoB.equals("busqDep")) {

			mostrarForm = false;
			mostrarpanelDoc = false;
			mostrarpanelDep = true;
			mostrarpanelEmail = false;
			mostrarUsuarios =  false;
		}
		
		if (tipoB.equals("busqEmail")) {

			mostrarpanelEmail = true;
			mostrarForm = false;
			mostrarpanelDoc = false;
			mostrarpanelDep = false;
			mostrarUsuarios =  false;
		}

	}
	
	
	public void cambiarFacultad() {

		System.out
				.println("Estoy entrando a la funcion de cambiar facultad: *******************************************");
		// dependencias
		dependenciasUN_Deptos = new ArrayList<Dependencia>();
		dependenciasUN_Deptos = servicioGeneral
				.obtenerObjetos("select e from Dependencia e where e.facultad.id = '"
						+ facultadSel.getId()
						+ "' and e.estado = 'A' order by e.nombre");

		System.out.println("El tamaño de las dependencias es: ");
		if (dependenciasUN_Deptos != null && dependenciasUN_Deptos.size() > 0) {
			dependenciaItem_Deptos = new SelectItem[dependenciasUN_Deptos.size()];
			for (int i = 0; i < dependenciasUN_Deptos.size(); i++) {
				Dependencia dd = (Dependencia) dependenciasUN_Deptos.get(i);
				dependenciaItem_Deptos[i] = new SelectItem(dd.getId(), dd.getNombre());
				dd = null;
			}
		} else {
			dependenciasUN_Deptos = servicioGeneral
					.obtenerObjetos("select e from Dependencia e where e.id = '"
							+ facultadSel.getId() + "'");
			dependenciaItem_Deptos = new SelectItem[dependenciasUN_Deptos.size()];
			for (int i = 0; i < dependenciasUN_Deptos.size(); i++) {
				Dependencia dd = (Dependencia) dependenciasUN_Deptos.get(i);
				dependenciaItem_Deptos[i] = new SelectItem(dd.getId(), dd.getNombre());
				dd = null;
			}

		}

	}

	// *****

	public void buscarXDependencia() {

		//facultad = facultadSel.getId();
	//	facultad = deptoSel.getId();
		/*if(deptoSeleccionado.equals("0")){
			deptoSeleccionado = facultadSel.getId();
		}*/
		
		usuariosDep = new ArrayList();
		if(facultadSel!=null && facultadSel.getId()!=null){
			usuariosDep = servicioPersona.obtenerRolesDependencia(facultadSel.getId().toString(), rolSel);
			System.out.println(usuariosDep.size());
		}
		
	}

	// *******************************

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
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

	public List<Sede> getSedesUN() {
		return sedesUN;
	}

	public void setSedesUN(List<Sede> sedesUN) {
		this.sedesUN = sedesUN;
	}

	public List<Rol> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<Rol> listaRoles) {
		this.listaRoles = listaRoles;
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

	public Rol getRolesCons() {
		return rolesCons;
	}

	public void setRolesCons(Rol rolesCons) {
		this.rolesCons = rolesCons;
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

	public String getSedeSel() {
		return sedeSel;
	}

	public void setSedeSel(String sedeSel) {
		this.sedeSel = sedeSel;
	}

	public String getUsuarioDocumento() {
		return usuarioDocumento;
	}

	public void setUsuarioDocumento(String usuarioDocumento) {
		this.usuarioDocumento = usuarioDocumento;
	}

	public String getSolicDocumento() {
		return solicDocumento;
	}

	public void setSolicDocumento(String solicDocumento) {
		this.solicDocumento = solicDocumento;
	}

	public TipoDocumento getSolicTipoDocumento() {
		return solicTipoDocumento;
	}

	public void setSolicTipoDocumento(TipoDocumento solicTipoDocumento) {
		this.solicTipoDocumento = solicTipoDocumento;
	}

	public Dependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public boolean isMostrarDependencias() {
		return mostrarDependencias;
	}

	public void setMostrarDependencias(boolean mostrarDependencias) {
		this.mostrarDependencias = mostrarDependencias;
	}

	public boolean isMostrarpanelDoc() {
		return mostrarpanelDoc;
	}

	public void setMostrarpanelDoc(boolean mostrarpanelDoc) {
		this.mostrarpanelDoc = mostrarpanelDoc;
	}

	public boolean isMostrarpanelDep() {
		return mostrarpanelDep;
	}

	public void setMostrarpanelDep(boolean mostrarpanelDep) {
		this.mostrarpanelDep = mostrarpanelDep;
	}

	public boolean isMostrarForm() {
		return mostrarForm;
	}

	public void setMostrarForm(boolean mostrarForm) {
		this.mostrarForm = mostrarForm;
	}

	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public String[] getRolesAntesArray() {
		return rolesAntesArray;
	}

	public void setRolesAntesArray(String[] rolesAntesArray) {
		this.rolesAntesArray = rolesAntesArray;
	}

	public String getUsuarioTipoDocumento() {
		return usuarioTipoDocumento;
	}

	public void setUsuarioTipoDocumento(String usuarioTipoDocumento) {
		this.usuarioTipoDocumento = usuarioTipoDocumento;
	}

	public List<PersonaRol> getRolesAntes() {
		return rolesAntes;
	}

	public void setRolesAntes(List<PersonaRol> rolesAntes) {
		this.rolesAntes = rolesAntes;
	}

	public String getRolSel() {
		return rolSel;
	}

	public void setRolSel(String rolSel) {
		this.rolSel = rolSel;
	}

	public Dependencia getFacultadSel() {
		return facultadSel;
	}

	public void setFacultadSel(Dependencia facultadSel) {
		this.facultadSel = facultadSel;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public List getUsuariosDatos() {
		return usuariosDatos;
	}

	public void setUsuariosDatos(List usuariosDatos) {
		this.usuariosDatos = usuariosDatos;
	}

	public List getUsuariosDep() {
		return usuariosDep;
	}

	public void setUsuariosDep(List usuariosDep) {
		this.usuariosDep = usuariosDep;
	}

	public boolean isMostrarpanelDoc2() {
		return mostrarpanelDoc2;
	}

	public void setMostrarpanelDoc2(boolean mostrarpanelDoc2) {
		this.mostrarpanelDoc2 = mostrarpanelDoc2;
	}

	public boolean isMostrarpanelDep2() {
		return mostrarpanelDep2;
	}

	public void setMostrarpanelDep2(boolean mostrarpanelDep2) {
		this.mostrarpanelDep2 = mostrarpanelDep2;
	}

	public List<Rol> getRol2() {
		return rol2;
	}

	public void setRol2(List<Rol> rol2) {
		this.rol2 = rol2;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List getRolesMostrar() {
		return rolesMostrar;
	}

	public void setRolesMostrar(List rolesMostrar) {
		this.rolesMostrar = rolesMostrar;
	}

	public VEmpleadoSara getEmpleado() {
		return empleado;
	}

	public void setEmpleado(VEmpleadoSara empleado) {
		this.empleado = empleado;
	}

	public boolean isEsActivo() {
		return esActivo;
	}

	public void setEsActivo(boolean esActivo) {
		this.esActivo = esActivo;
	}

	public List<Dependencia> getDependenciasUN_Deptos()
	{
		return dependenciasUN_Deptos;
	}

	public void setDependenciasUN_Deptos(List<Dependencia> dependenciasUN_Deptos)
	{
		this.dependenciasUN_Deptos = dependenciasUN_Deptos;
	}

	public Dependencia getDeptoSel()
	{
		return deptoSel;
	}

	public void setDeptoSel(Dependencia deptoSel)
	{
		this.deptoSel = deptoSel;
	}

	public SelectItem[] getDependenciaItem_Deptos()
	{
		return dependenciaItem_Deptos;
	}

	public void setDependenciaItem_Deptos(SelectItem[] dependenciaItem_Deptos)
	{
		this.dependenciaItem_Deptos = dependenciaItem_Deptos;
	}

	public String getDeptoSeleccionado()
	{
		return deptoSeleccionado;
	}

	public void setDeptoSeleccionado(String deptoSeleccionado)
	{
		this.deptoSeleccionado = deptoSeleccionado;
	}

	public boolean isMostrarpanelEmail() {
		return mostrarpanelEmail;
	}

	public void setMostrarpanelEmail(boolean mostrarpanelEmail) {
		this.mostrarpanelEmail = mostrarpanelEmail;
	}

	public String getUsuarioEmail() {
		return usuarioEmail;
	}

	public void setUsuarioEmail(String usuarioEmail) {
		this.usuarioEmail = usuarioEmail;
	}

	public boolean isMostrarUsuarios() {
		return mostrarUsuarios;
	}

	public void setMostrarUsuarios(boolean mostrarUsuarios) {
		this.mostrarUsuarios = mostrarUsuarios;
	}

	public List<Persona> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(List<Persona> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}

	public Persona getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(Persona usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public String getDependenciaUsuario() {
		return dependenciaUsuario;
	}

	public void setDependenciaUsuario(String dependenciaUsuario) {
		this.dependenciaUsuario = dependenciaUsuario;
	}

	public String getDependenciaUsuario2() {
		return dependenciaUsuario2;
	}

	public void setDependenciaUsuario2(String dependenciaUsuario2) {
		this.dependenciaUsuario2 = dependenciaUsuario2;
	}

	public boolean isEsInvestigadorInterno() {
		return esInvestigadorInterno;
	}

	public void setEsInvestigadorInterno(boolean esInvestigadorInterno) {
		this.esInvestigadorInterno = esInvestigadorInterno;
	}

	public boolean isEsDocenteActivo() {
		return esDocenteActivo;
	}

	public void setEsDocenteActivo(boolean esDocenteActivo) {
		this.esDocenteActivo = esDocenteActivo;
	}

	public String getVinculacionDocente() {
		return vinculacionDocente;
	}

	public void setVinculacionDocente(String vinculacionDocente) {
		this.vinculacionDocente = vinculacionDocente;
	}

	public String getDedicacionDocente() {
		return dedicacionDocente;
	}

	public void setDedicacionDocente(String dedicacionDocente) {
		this.dedicacionDocente = dedicacionDocente;
	}

	public String getFormacionDocente() {
		return formacionDocente;
	}

	public void setFormacionDocente(String formacionDocente) {
		this.formacionDocente = formacionDocente;
	}

	public String getValorHoraDocente() {
		return valorHoraDocente;
	}

	public void setValorHoraDocente(String valorHoraDocente) {
		this.valorHoraDocente = valorHoraDocente;
	}

	public String getTipoCargoDocente() {
		return tipoCargoDocente;
	}

	public void setTipoCargoDocente(String tipoCargoDocente) {
		this.tipoCargoDocente = tipoCargoDocente;
	}
	
	
}