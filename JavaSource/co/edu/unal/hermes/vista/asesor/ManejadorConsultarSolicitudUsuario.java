package co.edu.unal.hermes.vista.asesor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.SolicitudPersonaRol;
import co.edu.unal.hermes.modelo.SolicitudUsuario;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorConsultarSolicitudUsuario extends ManejadorBase implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	SolicitudUsuario solicitudUsuario; // clase
	Persona persona; // aux para tomar datos
	Persona solicitante; // persona para tomar datos de solicitante
	SolicitudPersonaRol sperRol1;
	SolicitudPersonaRol sperRol2;

	TipoDocumento usuarioTipoDocumento;
	TipoDocumento solicTipoDocumento;
	String usuarioDocumento;
	String solicDocumento;
	private String sedeSel;
	boolean mostrarForm = false;
	private boolean mostrarFacultades = false;
	private boolean editarDependencia = false;
	private String[] rolesAntesArray;
	private String[] rolesBDArray;
	private String[] rolesSeleccionadosArray;
	private String[] todRolesArray;
	private String[] rolesNoSeleccionadosArray;
	private String mensajeRol;
	private String accion;

	private SelectItem[] tipoDocumentoItem;
	private SelectItem[] dependenciaItem;
	private SelectItem[] facultadItem;
	private SelectItem[] rolesItem;
	private SelectItem[] sedeItem;
	private SelectItem[] rolesItemArray;
	private SelectItem[] rolesSiItemArray;
	private SelectItem[] rolesNoItemArray;

	List<TipoDocumento> listaTipoDocumento;
	List<Dependencia> dependenciasUN;
	List<Sede> sedesUN;
	List<Rol> listaRoles;
	private List<Rol> rolesAntes;
	private List<Rol> todRolesSol;
	private List<Rol> listarno;
	private List<Dependencia> facultadesUN;
	private String facultadSel;
	private String editDepend;
	private String dependencia;
	private String dependenciaId;
	private String categoriaInvestigador;
	
	
	private List<SolicitudUsuario> solicitudULista;
	
	private SolicitudUsuario solicitudU;
	private SolicitudUsuario solicitudSeleccionada;
	private SolicitudUsuario solicitudUEditar;
	
	private SolicitudPersonaRol solicitudUR;
	private SolicitudPersonaRol solicitudPRSeleccionada;
	private SolicitudPersonaRol solicitudUREditar;
	private String nombreAux = null;
	private String nombreUAux = null;
	Persona nombUsuario;
	private String depSol;
	private List depNombre;
	
	
	


	public ManejadorConsultarSolicitudUsuario() {
		
		sesion.removeAttribute("ManejadorCrearSolicitudUsuario");
		sesion.removeAttribute("ManejadorEditarSolicitudUsuario");
				
		cargarValoresIniciales();

	}

	private void cargarValoresIniciales() {
		
		sesion.removeAttribute("ManejadorCrearSolicitudUsuario");
		sesion.removeAttribute("ManejadorEditarSolicitudUsuario");
		
		//trae las solic 
		cargarSolicitante();
		
		solicitudULista = new ArrayList<SolicitudUsuario>();
		solicitudULista = servicioGeneral.obtenerObjetos(SolicitudUsuario.class,"from SolicitudUsuario r where r.estadoSol in ('I', 'P','A','N') and r.solicDocumento='"+solicitante.getId().getDocumento()+"' order by r.id" );
		
		setSolicitudSeleccionada(solicitudU);
		
		System.out.println(solicitudULista.size());
		
		cargarListas();
		cargarSolicitante();
		
		
	}

	private void cargarSolicitante() {

		// carga id solicitante en solUsuario
		solicitante = new Persona();
		solicitante = servicioPersona.obtenerPersona(((Persona) sesion
				.getAttribute("persona")).getId()); // toma persona
		nombreAux= solicitante.getNombreCompleto();	

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

		sedesUN = new ArrayList<Sede>();
		sedesUN = servicioGeneral
				.obtenerObjetos("select e from Sede e where  e.id<>0 ");
		
		if (sedesUN!=null && sedesUN.size()>0){
			sedeItem = new SelectItem[sedesUN.size()];
	
			for (int i = 0; i < sedesUN.size(); i++) {
				Sede dd = (Sede) sedesUN.get(i);
				sedeItem[i] = new SelectItem(dd.getId(), dd.getNombre());
				dd = null;
			}
			sedeSel = ((Sede) sedesUN.get(0)).getId().toString();
	
			if (sedeSel.equals("1") || sedeSel.equals("6") || sedeSel.equals("7")
					|| sedeSel.equals("8") || sedeSel.equals("9")) {
				mostrarFacultades = false;
				// dependencias
				dependenciasUN = new ArrayList<Dependencia>();
				dependenciasUN = servicioGeneral
						.obtenerObjetos("select e from Dependencia e where e.sede.id = '"
								+ sedeSel + "' and e.estado='A' order by e.nombre");
				
				if (dependenciasUN!=null && dependenciasUN.size()>0){
					dependenciaItem = new SelectItem[dependenciasUN.size()];
					for (int i = 0; i < dependenciasUN.size(); i++) {
						Dependencia dd = (Dependencia) dependenciasUN.get(i);
						dependenciaItem[i] = new SelectItem(dd.getId(), dd.getNombre());
						dd = null;
					}
				}
				
		}
		
			// Roles a escoger
			listaRoles = new ArrayList<Rol>();
			//listaRoles = servicioGeneral.obtenerListaObjetos(Rol.class);
			listaRoles = this.servicioPersona.obtenerRolesSolicitud(); //todos elegibles en solicitud

			if (listaRoles!=null && listaRoles.size()>0){
				Rol rAux = listaRoles.get(0);

				rolesItem = new SelectItem[listaRoles.size()];

				for (int i = 0; i < listaRoles.size(); i++) {
					Rol rd = (Rol) listaRoles.get(i);
					rolesItem[i] = new SelectItem(rd.getId(), rd.getNombre());
					rd = null;
				}
			}
			
		}

	}

	public void buscarPersona() {

		IdPersona id = new IdPersona();
		id.setDocumento(solicitudUsuario.getUsuarioDocumento());
		id.setTipoDocumento(solicitudUsuario.getUsuarioTipoDocumento());

		persona = servicioPersona.obtenerPersonaRoles(id);

		if (persona != null) {
			if (persona instanceof Investigador) {
				
				if (persona instanceof InvestigadorExterno) {
					persona = servicioPersona.obtenerInvestigadorExternoCompleto(persona.getId());
					mostrarForm = true;

				} else if (persona instanceof InvestigadorInterno) {
					
					persona = servicioPersona.obtenerInvestigadorInternoCompleto(persona.getId());
					// mostrarDatosInvestigadorInterno();
					InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;
					mostrarForm = true;
					dependencia=investigadorInterno.getDependencia().getId();
					persona.setCoorIDDependencia(investigadorInterno.getDependencia().getId());
					persona.setCoorNombreDependencia(investigadorInterno.getDependencia().getNombre());
					
				} else {

					
				}
			} else {
			
			}
			
		} else {
			persona = new Persona();
			IdPersona idP = new IdPersona();
			idP.setDocumento(usuarioDocumento);
			idP.setTipoDocumento(usuarioTipoDocumento.getId());
			persona.setId(idP);
			

			mostrarForm = true;

			mensajeInfo("El usuario ingresado no existe en el sistema, Solicitamos diligenciar la información requerida en el formulario ");

		}	
	
	}
	
	
	//*************datos para edición******
	
	public String editarSolicitudAdm() {
		
		cargarListas();
		
		solicitudUEditar = new SolicitudUsuario();
		solicitudUEditar = solicitudSeleccionada;
		sesion.removeAttribute("ManejadorEditarSolicitudUsuario");
		sesion.setAttribute("solicitudUEditable", solicitudSeleccionada.getId());
		sesion.setAttribute("solicitudURolesEditable", solicitudSeleccionada.getId());
		return "editarSolicitudUsuario";
		
	}
	
	
	public String editarSolicitudU() {
		
		cargarListas();
		
		solicitudUEditar = new SolicitudUsuario();
		solicitudUEditar = solicitudSeleccionada;
		sesion.removeAttribute("ManejadorEditarSolicitudUsuario");
		sesion.setAttribute("solicitudUEditable", solicitudSeleccionada.getId());
		sesion.setAttribute("solicitudURolesEditable", solicitudSeleccionada.getId());
		return "editarSolicitudUsuario";
		
	}
	
		public String consultarSolicitudU() {
		
		cargarListas();
		
		solicitudUEditar = new SolicitudUsuario();
		solicitudUEditar = solicitudSeleccionada;
		sesion.removeAttribute("ManejadorEditarSolicitudUsuario");
		sesion.setAttribute("solicitudUEditable", solicitudSeleccionada.getId());
		sesion.setAttribute("solicitudURolesEditable", solicitudSeleccionada.getId());
		return "consultarSolicitudUsuarioCreada";
		

	}
	
//	public String consultarSolicitudSeg() {
//
//		cargarListas();
//
//		solicitudUEditar = new SolicitudUsuario();
//		solicitudUEditar = solicitudSeleccionada;
//		sesion.removeAttribute("ManejadorEditarSolicitudUsuario");
//		sesion
//				.setAttribute("solicitudUEditable", solicitudSeleccionada
//						.getId());
//		sesion.setAttribute("solicitudURolesEditable", solicitudSeleccionada
//				.getId());
//		return "consultarSolicitudUsuarioSeguimiento";
//
//	}
		

	// *******************************************************************

	public SolicitudUsuario getSolicitudUsuario() {
		return solicitudUsuario;
	}

	public void setSolicitudUsuario(SolicitudUsuario solicitudUsuario) {
		this.solicitudUsuario = solicitudUsuario;
	}

	public Persona getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Persona solicitante) {
		this.solicitante = solicitante;
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

	public List<TipoDocumento> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public Persona getPersonaActual() {
		return personaActual;
	}

	public void setPersonaActual(Persona personaActual) {
		this.personaActual = personaActual;
	}

	public TipoDocumento getUsuarioTipoDocumento() {
		return usuarioTipoDocumento;
	}

	public void setUsuarioTipoDocumento(TipoDocumento usuarioTipoDocumento) {
		this.usuarioTipoDocumento = usuarioTipoDocumento;
	}

	public String getUsuarioDocumento() {
		return usuarioDocumento;
	}

	public void setUsuarioDocumento(String usuarioDocumento) {
		this.usuarioDocumento = usuarioDocumento;
	}

	public TipoDocumento getSolicTipoDocumento() {
		return solicTipoDocumento;
	}

	public void setSolicTipoDocumento(TipoDocumento solicTipoDocumento) {
		this.solicTipoDocumento = solicTipoDocumento;
	}

	public String getSolicDocumento() {
		return solicDocumento;
	}

	public void setSolicDocumento(String solicDocumento) {
		this.solicDocumento = solicDocumento;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public boolean isMostrarForm() {
		return mostrarForm;
	}

	public void setMostrarForm(boolean mostrarForm) {
		this.mostrarForm = mostrarForm;
	}

	public SelectItem[] getRolesItem() {
		return rolesItem;
	}

	public void setRolesItem(SelectItem[] rolesItem) {
		this.rolesItem = rolesItem;
	}

	public List<Rol> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<Rol> listaRoles) {
		this.listaRoles = listaRoles;
	}

	public String getSedeSel() {
		return sedeSel;
	}

	public void setSedeSel(String sedeSel) {
		this.sedeSel = sedeSel;
	}

	public boolean isMostrarFacultades() {
		return mostrarFacultades;
	}

	public void setMostrarFacultades(boolean mostrarFacultades) {
		this.mostrarFacultades = mostrarFacultades;
	}

	public SelectItem[] getFacultadItem() {
		return facultadItem;
	}

	public void setFacultadItem(SelectItem[] facultadItem) {
		this.facultadItem = facultadItem;
	}

	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	public void setSedeItem(SelectItem[] sedeItem) {
		this.sedeItem = sedeItem;
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

	public List<Dependencia> getFacultadesUN() {
		return facultadesUN;
	}

	public void setFacultadesUN(List<Dependencia> facultadesUN) {
		this.facultadesUN = facultadesUN;
	}

	public String getFacultadSel() {
		return facultadSel;
	}

	public void setFacultadSel(String facultadSel) {
		this.facultadSel = facultadSel;
	}

	public boolean isEditarDependencia() {
		return editarDependencia;
	}

	public void setEditarDependencia(boolean editarDependencia) {
		this.editarDependencia = editarDependencia;
	}

	public String[] getRolesSeleccionadosArray() {
		return rolesSeleccionadosArray;
	}

	public void setRolesSeleccionadosArray(String[] rolesSeleccionadosArray) {
		this.rolesSeleccionadosArray = rolesSeleccionadosArray;
	}

	public SelectItem[] getRolesItemArray() {
		return rolesItemArray;
	}

	public void setRolesItemArray(SelectItem[] rolesItemArray) {
		this.rolesItemArray = rolesItemArray;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public List<Rol> getrolesAntes() {
		return rolesAntes;
	}

	public void setrolesAntes(List<Rol> rolesAntes) {
		this.rolesAntes = rolesAntes;
	}

	public String getMensajeRol() {
		return mensajeRol;
	}

	public void setMensajeRol(String mensajeRol) {
		this.mensajeRol = mensajeRol;
	}


	public String getDependenciaId() {
		return dependenciaId;
	}

	public void setDependenciaId(String dependenciaId) {
		this.dependenciaId = dependenciaId;
	}

	public String getCategoriaInvestigador() {
		return categoriaInvestigador;
	}

	public void setCategoriaInvestigador(String categoriaInvestigador) {
		this.categoriaInvestigador = categoriaInvestigador;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getEditDepend() {
		return editDepend;
	}

	public void setEditDepend(String editDepend) {
		this.editDepend = editDepend;
	}

	public SelectItem[] getRolesNoItemArray() {
		return rolesNoItemArray;
	}

	public void setRolesNoItemArray(SelectItem[] rolesNoItemArray) {
		this.rolesNoItemArray = rolesNoItemArray;
	}

	public String[] getRolesNoSeleccionadosArray() {
		return rolesNoSeleccionadosArray;
	}

	public void setRolesNoSeleccionadosArray(String[] rolesNoSeleccionadosArray) {
		this.rolesNoSeleccionadosArray = rolesNoSeleccionadosArray;
	}

	public SelectItem[] getRolesSiItemArray() {
		return rolesSiItemArray;
	}

	public void setRolesSiItemArray(SelectItem[] rolesSiItemArray) {
		this.rolesSiItemArray = rolesSiItemArray;
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



	public List<Rol> getListarno() {
		return listarno;
	}

	public void setListarno(List<Rol> listarno) {
		this.listarno = listarno;
	}

	public String[] getTodRolesArray() {
		return todRolesArray;
	}

	public void setTodRolesArray(String[] todRolesArray) {
		this.todRolesArray = todRolesArray;
	}

	public String[] getRolesAntesArray() {
		return rolesAntesArray;
	}

	public void setRolesAntesArray(String[] rolesAntesArray) {
		this.rolesAntesArray = rolesAntesArray;
	}

	public List<Rol> getRolesAntes() {
		return rolesAntes;
	}

	public void setRolesAntes(List<Rol> rolesAntes) {
		this.rolesAntes = rolesAntes;
	}

	public List<Rol> getTodRolesSol() {
		return todRolesSol;
	}

	public void setTodRolesSol(List<Rol> todRolesSol) {
		this.todRolesSol = todRolesSol;
	}

	public String[] getRolesBDArray() {
		return rolesBDArray;
	}

	public void setRolesBDArray(String[] rolesBDArray) {
		this.rolesBDArray = rolesBDArray;
	}


	public SolicitudUsuario getSolicitudSeleccionada() {
		return solicitudSeleccionada;
	}

	public void setSolicitudSeleccionada(SolicitudUsuario solicitudSeleccionada) {
		this.solicitudSeleccionada = solicitudSeleccionada;
	}

	public SolicitudUsuario getSolicitudUEditar() {
		return solicitudUEditar;
	}

	public void setSolicitudUEditar(SolicitudUsuario solicitudUEditar) {
		this.solicitudUEditar = solicitudUEditar;
	}

	public SolicitudUsuario getSolicitudU() {
		return solicitudU;
	}

	public void setSolicitudU(SolicitudUsuario solicitudU) {
		this.solicitudU = solicitudU;
	}

	public SolicitudPersonaRol getSolicitudUR() {
		return solicitudUR;
	}

	public void setSolicitudUR(SolicitudPersonaRol solicitudUR) {
		this.solicitudUR = solicitudUR;
	}

	public SolicitudPersonaRol getSolicitudPRSeleccionada() {
		return solicitudPRSeleccionada;
	}

	public void setSolicitudPRSeleccionada(
			SolicitudPersonaRol solicitudPRSeleccionada) {
		this.solicitudPRSeleccionada = solicitudPRSeleccionada;
	}

	public SolicitudPersonaRol getSolicitudUREditar() {
		return solicitudUREditar;
	}

	public void setSolicitudUREditar(SolicitudPersonaRol solicitudUREditar) {
		this.solicitudUREditar = solicitudUREditar;
	}

	public String getNombreAux() {
		return nombreAux;
	}

	public void setNombreAux(String nombreAux) {
		this.nombreAux = nombreAux;
	}

	public String getNombreUAux() {
		return nombreUAux;
	}

	public void setNombreUAux(String nombreUAux) {
		this.nombreUAux = nombreUAux;
	}

	public Persona getNombUsuario() {
		return nombUsuario;
	}

	public void setNombUsuario(Persona nombUsuario) {
		this.nombUsuario = nombUsuario;
	}

	public List<SolicitudUsuario> getSolicitudULista() {
		return solicitudULista;
	}

	public void setSolicitudULista(List<SolicitudUsuario> solicitudULista) {
		this.solicitudULista = solicitudULista;
	}

	public String getDepSol() {
		return depSol;
	}

	public void setDepSol(String depSol) {
		this.depSol = depSol;
	}

}