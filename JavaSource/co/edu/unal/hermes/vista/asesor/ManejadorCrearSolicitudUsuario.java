package co.edu.unal.hermes.vista.asesor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.SolicitudPersonaRol;
import co.edu.unal.hermes.modelo.SolicitudUsuario;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.VEmpleadoSara;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.utils.VariablesEstaticas;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorCrearSolicitudUsuario extends ManejadorBase implements
		Serializable {

	SolicitudUsuario solicitudUsuario; // clase
	Persona persona; // aux para tomar datos
	Persona solicitante; // persona para tomar datos de solicitante
	SolicitudPersonaRol sperRol1;
	SolicitudPersonaRol sperRol2;

	TipoDocumento usuarioTipoDocumento;
	TipoDocumento solicTipoDocumento;
	String usuarioDocumento;
	String solicDocumento;
	boolean mostrarForm = false;

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
	List<Rol> listaRoles;
	private List<Rol> rolesAntes;
	private List<Rol> todRolesSol;
	private List<Rol> listarno;
	String documento;
	String tipoDocumento;

	private Dependencia facultadSel;
	private String facultadSelec;
	private String facultadSelec1;
	private String editDepend;
	private Dependencia dependencia;
	private String categoriaInvestigador;
	private Rol rolAux;
	private Rol rolAux2;

	// correo
	private String correo;
	private boolean respuesta;
	private String observacion;
	// busc est
	private Estudiante est;
	private InvestigadorInterno inv;

	// Panel Buscar
	private String tipoBusqueda = "Usuario";
	private boolean panelRender[];
	private boolean mostrarTabla;

	private List<Persona> listaPersonas = new ArrayList<Persona>();
	private Persona usuarioSeleccionado;
	private Persona usuarioHermes;
	private String dependenciaUsuario;
	private String dependenciaUsuario2;

	// Panel usuario nuevo
	private InvestigadorInterno investigadorExterno = new InvestigadorInterno();
	private String insitucionNombre;
	private List listaInstitucion;
	private SelectItem[] institucionItem;
	private Institucion institucion;
	private String genero;
	private SelectItem[] generoItem = {
			new SelectItem(VariablesEstaticas.GENERO_FEMENINO,
					VariablesEstaticas.GENERO_FEMENINO),
			new SelectItem(VariablesEstaticas.GENERO_MASCULINO,
					VariablesEstaticas.GENERO_MASCULINO) };
	private UIComponent buscarPer2;
	private boolean verDependencia;
	public SelectItem[] tieneCorreoItems = { new SelectItem("SI", "SI"), new SelectItem("NO", "NO") };
	private String tieneCorreoUN;
	
	// Constructor
	public ManejadorCrearSolicitudUsuario() {

		sesion.removeAttribute("ManejadorCrearSolicitudUsuario");
		cargarValoresIniciales();

		personaActual = null;
		if (sesion.getAttribute("persona") != null) {
			try {
				personaActual = (Persona) sesion.getAttribute("persona");
			} catch (Exception e) {
				personaActual = null;
			}
		}

	}

	public void cambiarForm() {
		String tipoB = (String) tipoBusqueda;
		if (tipoB.equals("usuario")) {
			panelRender[1] = true;
			panelRender[2] = false;
			mostrarTabla = false;

		}
		if (tipoB.equals("dependencia")) {
			panelRender[2] = true;
			panelRender[1] = false;
			mostrarTabla = false;

		}
	}

	public void cargarValoresIniciales() {

		solicitudUsuario = new SolicitudUsuario(); // nueva sol
		persona = new Persona();
		usuarioSeleccionado = new Persona();
		usuarioDocumento = new String();
		usuarioTipoDocumento = new TipoDocumento();
		panelRender = new boolean[10];
		insitucionNombre = new String();
		listaInstitucion = new ArrayList();
		institucion = new Institucion();
		genero = new String();
		facultadSelec = new String();
		facultadSelec1 = new String();

		cargarSolicitante();
		cargarListas();
		listaPersonas = new ArrayList<Persona>();
		panelRender[2] = false;
		panelRender[3] = false;
		panelRender[4] = false;
		panelRender[5] = false;
		mostrarForm = false;

	}

	private void cargarSolicitante() {
		panelRender[4] = false;
		panelRender[5] = false;
		mostrarForm = false;

		// carga id solicitante en solUsuario
		solicitante = new Persona();
		solicitante = servicioPersona.obtenerPersona(((Persona) sesion
				.getAttribute("persona")).getId()); // toma persona
		solicitudUsuario.setSolicDocumento(solicitante.getId().getDocumento());
		solicitudUsuario.setSolicTipoDocumento(solicitante.getId()
				.getTipoDocumento());

	}

	private void cargarListas() {

		panelRender[4] = false;
		panelRender[5] = false;
		mostrarForm = false;
		
		// Listas
		listaPersonas = new ArrayList<Persona>();
		investigadorExterno = new InvestigadorInterno();
		rolesSeleccionadosArray = new String[0];

		// Tipos Documento
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
		dependenciasUN = servicioGeneral
				.obtenerObjetos("select e from Dependencia e where e.estado='A' order by e.nombre");
		dependenciaItem = new SelectItem[dependenciasUN.size() + 1];
		dependenciaItem[0] = new SelectItem("",
				"Seleccione una dependencia a asociar");
		for (int i = 1; i < dependenciasUN.size() + 1; i++) {
			Dependencia dd = (Dependencia) dependenciasUN.get(i - 1);
			dependenciaItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			// dd = null;

		}

		facultadSel = ((Dependencia) dependenciasUN.get(0));

		// Roles a escoger
		listaRoles = new ArrayList<Rol>();
		// listaRoles = servicioGeneral.obtenerListaObjetos(Rol.class);
		listaRoles = this.servicioPersona.obtenerRolesSolicitud(); // todos
																	// elegibles

		if (listaRoles != null && listaRoles.size() > 0) {
			Rol rAux = listaRoles.get(0);

			rolesItemArray = new SelectItem[listaRoles.size()];

			for (int i = 0; i < listaRoles.size(); i++) {
				Rol rd = (Rol) listaRoles.get(i);
				rolesItemArray[i] = new SelectItem(rd.getId(), rd.getNombre()
						.toUpperCase());
				rd = null;
			}
		}

	}

	public void guardarInformacionUsuario() {
		InvestigadorInterno inv;
		
		try {
			if (usuarioHermes != null && usuarioHermes.getId()!=null) {
				
			 if (usuarioHermes instanceof InvestigadorInterno) {
					inv = servicioPersona.obtenerInvestigadorInternoCompleto(usuarioHermes.getId());
					if (inv != null) {
						if (dependenciaUsuario2 != null && !dependenciaUsuario2.equals("") ) {
							Dependencia dep = servicioDependencia.obtenerDependencia(dependenciaUsuario2);
													
							if (dep != null) {							
								inv.setDependencia2(dep);
								servicioGeneral.guardarObjeto(inv);
								cargarRol(usuarioHermes.getId().getDocumento(), usuarioHermes.getId().getTipoDocumento());
								mostrarForm = true;
							}
						}else{
							FacesMessage msg = new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Seleccione la dependencia del usuario.","");
							mostrarMensaje(msg, buscarPer2);						
						}
					}else{
						
					}
				
			}else{
				//No es interno
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"No es posible realizar una solicitud de rol a un usuario externo. Por favor verifique la vinculación del usuario con la Universidad.","");
				mostrarMensaje(msg, buscarPer2);
				mostrarForm = false;
			}
			
		}else{
			// persona null
		}
	} catch (Exception e) {
		e.printStackTrace();
		FacesMessage msg = new FacesMessage(
				FacesMessage.SEVERITY_ERROR,
				"No fue posible actualizar el usuario.","");
		mostrarMensaje(msg, buscarPer2);
	}
			
			
	}

	public void editarUsuario() {
		panelRender[4] = false;
		panelRender[5] = false;
		mostrarForm = false;

		try{
			if (usuarioSeleccionado != null) {
				usuarioHermes = usuarioSeleccionado;
				panelRender[4] = true;
				
				if (usuarioHermes instanceof InvestigadorInterno) {	
					Persona per2 = servicioPersona
							.obtenerInvestigadorInternoCompleto(usuarioHermes.getId());
					InvestigadorInterno investigadorInterno = (InvestigadorInterno) per2;

					if (investigadorInterno.getDependencia() != null) {
						dependenciaUsuario = investigadorInterno.getDependencia().getNombre();
					} else {
						dependenciaUsuario = "Sin asignar";
					}

					if (investigadorInterno.getDependencia2() != null) {
						dependenciaUsuario2 = investigadorInterno.getDependencia2().getId();
					}

						setVerDependencia(true);
				}else{
					setVerDependencia(false);
				}
			
	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void buscarUsuario() {
		listaPersonas = new ArrayList<Persona>();
		panelRender[2] = false;
		panelRender[3] = false;
		panelRender[4] = false;
		panelRender[5] = false;
		mostrarForm = false;

		personaActual = new Persona();
		investigadorExterno = new InvestigadorInterno();
		boolean resultado = false;

		// 1. ID
		if (!solicitudUsuario.getUsuarioTipoDocumento().equals("0")
				&& solicitudUsuario.getUsuarioDocumento() != null
				&& solicitudUsuario.getUsuarioDocumento().trim().length() >= 3
				&& (solicitudUsuario.getNombre1() == null || solicitudUsuario
						.getNombre1().equals(""))
				&& (solicitudUsuario.getApellido1() == null || solicitudUsuario
						.getApellido1().equals(""))) {

			// Por ID
			IdPersona id = new IdPersona();
			Persona persona = new Persona();

			id.setTipoDocumento(solicitudUsuario.getUsuarioTipoDocumento());
			id.setDocumento(solicitudUsuario.getUsuarioDocumento());
			// Asociar roles y datos de la persona
			if (id != null) {
				// persona = servicioPersona.obtenerPersonaRoles(id);
				persona = buscarPersona(id);
				if (persona != null) {
					if (persona.getNombre2() == null) {
						persona.setNombre2("");
					}
					if (persona.getApellido2() == null) {
						persona.setApellido2("");
					}
					listaPersonas.add(persona);
					panelRender[3] = true;
				} else {
					/*
					 * persona = buscarEstudiante(id); if(persona!=null){
					 * listaPersonas.add(persona); panelRender[3] = true; }else{
					 */
					FacesMessage msg = new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"El usuario no se encuentra registrado.",
							"El usuario no se encuentra registrado.");
					mostrarMensaje(msg, buscarPer2);
				}
				// }
			}

			// 2. nombres
		} else if (solicitudUsuario.getNombre1() != null
				&& solicitudUsuario.getNombre1().trim().length() >= 2
				&& solicitudUsuario.getApellido1() != null
				&& solicitudUsuario.getApellido1().trim().length() >= 2
				&& solicitudUsuario.getUsuarioTipoDocumento().equals("0")) {
			List<Persona> lista = servicioPersona
					.obtenerInvestigadoresPorNombresYApellidosIndiferenteTildesYMayusculas(
							solicitudUsuario.getNombre1(),
							solicitudUsuario.getApellido1());

			if (lista.size() > 0) {
				listaPersonas.addAll(lista);
				panelRender[3] = true;
			} else {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"El usuario no se encuentra registrado.",
						"El usuario no se encuentra registrado.");
				mostrarMensaje(msg, buscarPer2);
			}

			// 3. nombre1
		} else if (solicitudUsuario.getNombre1() != null
				&& solicitudUsuario.getNombre1().trim().length() > 2
				&& solicitudUsuario.getUsuarioTipoDocumento().equals("0")) {
			List<Persona> lista = servicioPersona
					.obtenerInvestigadoresPorNombre(solicitudUsuario
							.getNombre1());

			if (lista.size() > 0) {
				listaPersonas.addAll(lista);
				panelRender[3] = true;
			} else {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"El usuario no se encuentra registrado.",
						"El usuario no se encuentra registrado.");
				mostrarMensaje(msg, buscarPer2);
			}

			// 4. apellido1
		} else if (solicitudUsuario.getApellido1() != null
				&& solicitudUsuario.getApellido1().trim().length() >= 2
				&& solicitudUsuario.getUsuarioTipoDocumento().equals("0")) {
			List<Persona> lista = servicioPersona
					.obtenerInvestigadoresPorNombre(solicitudUsuario
							.getApellido1());

			if (lista.size() > 0) {
				listaPersonas.addAll(lista);
				panelRender[3] = true;
			} else {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"El usuario no se encuentra registrado.",
						"El usuario no se encuentra registrado.");
				mostrarMensaje(msg, buscarPer2);
			}

			// 5. solo documento
		} else if ((solicitudUsuario.getUsuarioDocumento() != null)
				&& solicitudUsuario.getUsuarioDocumento().trim().length() >= 3
				&& (solicitudUsuario.getNombre1() == null || solicitudUsuario
						.getNombre1().equals(""))
				&& (solicitudUsuario.getApellido1() == null || solicitudUsuario
						.getApellido1().equals(""))) {

			List listaP = servicioGeneral
					.obtenerObjetos("select p from Persona p where p.id.documento = '"
							+ solicitudUsuario.getUsuarioDocumento() + "'");

			if (listaP.size() > 0) {
				listaPersonas.addAll(listaP);
				panelRender[3] = true;
			} else {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"El usuario no se encuentra registrado.",
						"El usuario no se encuentra registrado.");
				mostrarMensaje(msg, buscarPer2);
			}

			// 6. tipodocumento y nombre
		} else if (!solicitudUsuario.getUsuarioTipoDocumento().equals("0")
				&& solicitudUsuario.getNombre1() != null) {

			List listaP = servicioGeneral
					.obtenerObjetos("select p from Persona p where p.id.tipoDocumento = '"
							+ solicitudUsuario.getUsuarioTipoDocumento()
							+ "' and p.nombre1 = '"
							+ solicitudUsuario.getNombre1().toUpperCase() + "'");

			if (listaP.size() > 0) {
				listaPersonas.addAll(listaP);
				panelRender[3] = true;
			} else {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"El usuario no se encuentra registrado.",
						"El usuario no se encuentra registrado.");
				mostrarMensaje(msg, buscarPer2);
			}

			// solo tipodocumento
		} else if (!solicitudUsuario.getUsuarioTipoDocumento().equals("0")
				&& (solicitudUsuario.getNombre1() == null || solicitudUsuario
						.getNombre1().equals(""))
				&& (solicitudUsuario.getApellido1() == null || solicitudUsuario
						.getApellido1().equals(""))) {

			List listaP = servicioGeneral
					.obtenerObjetos("select p from Persona p where p.id.tipoDocumento = '"
							+ solicitudUsuario.getUsuarioTipoDocumento() + "'");

			if (listaP.size() > 0) {
				listaPersonas.addAll(listaP);
				panelRender[3] = true;
			} else {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"El usuario no se encuentra registrado.",
						"El usuario no se encuentra registrado.");
				mostrarMensaje(msg, buscarPer2);
			}

		}

		if (listaPersonas == null || listaPersonas.size() == 0) {
			FacesMessage msg = new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"No se encuentran resultados de acuerdo a los campos ingresados.", "");
			mostrarMensaje(msg, buscarPer2);

			// No hay resultados - crear usuario
			panelRender[3] = false;
			panelRender[5] = true;
			investigadorExterno = new InvestigadorInterno();
			IdPersona idPer = new IdPersona();
			
			if ((!solicitudUsuario.getUsuarioTipoDocumento().equals("0"))
					&& solicitudUsuario.getUsuarioDocumento()!= null
					&& solicitudUsuario.getUsuarioDocumento().trim().length() >= 3) {
				idPer.setTipoDocumento(solicitudUsuario.getUsuarioTipoDocumento());
				idPer.setDocumento(solicitudUsuario.getUsuarioDocumento());
				investigadorExterno.setId(idPer);
				solicitudUsuario.setUsuarioTipoDocumento("0");
				solicitudUsuario.setUsuarioDocumento(null);
			}

		} else {// fin if
			/*Persona per = listaPersonas.get(0);
			if (per instanceof InvestigadorInterno) {

				Persona per2 = servicioPersona.obtenerInvestigadorInternoCompleto(per.getId());
				InvestigadorInterno investigadorInterno = (InvestigadorInterno) per2;

				if (investigadorInterno.getDependencia() != null) {
					dependenciaUsuario = investigadorInterno.getDependencia().getNombre();
				} else {
					dependenciaUsuario = "Sin asignar";
				}

				if (investigadorInterno.getDependencia2() != null) {
					dependenciaUsuario2 = investigadorInterno.getDependencia2().getId();					
				}

			}*/
		}

	}

	public Persona buscarPersona(IdPersona id) {
		Persona persona = new Persona();
		persona = servicioPersona.obtenerPersona(id);
		dependenciaUsuario = "Sin asignar";

		if (persona != null) {
			if (persona instanceof Investigador) {

				if (persona instanceof InvestigadorInterno) {
					
					persona = servicioPersona.obtenerInvestigadorInternoCompleto(persona.getId());
					InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;

					if (investigadorInterno.getDependencia() != null) {
						dependenciaUsuario = investigadorInterno.getDependencia().getNombre();
					}

					if (investigadorInterno.getDependencia2() != null) {
						dependenciaUsuario2 = investigadorInterno.getDependencia2().getId();					
					}					
					
				} else if (persona instanceof InvestigadorExterno) {

					persona = servicioPersona.obtenerInvestigadorExternoCompleto(persona.getId());

				}

			}
		}

		return persona;
	}

	public Persona buscarAdministrativo(IdPersona id) {
		Persona persona = new Persona();
		persona = servicioPersona.obtenerPersona(id);

		System.out
				.println("********************* persona nula, buscando VEmpleadoSara");
		String hql = "FROM VEmpleadoSara ves WHERE ves.id.documento = '"
				+ id.getDocumento() + "' AND ves.id.tipoDocumento = '"
				+ id.getTipoDocumento() + "' ORDER BY ves.estado";
		System.out.println("********************* hql: " + hql);
		List<VEmpleadoSara> listaEmpleados = servicioGeneral.obtenerObjetos(
				VEmpleadoSara.class, hql);
		if (listaEmpleados.size() > 0) {
			VEmpleadoSara eS = listaEmpleados.get(0);
			try {
				persona = new Persona();
				persona.setId(eS.getId());
				persona.setApellido1(eS.getApellido());
				persona.setApellido2(eS.getApellido());
				persona.setNombre1(eS.getNombres());
				persona.setEmail(eS.getEmail());
				persona.setDireccion(eS.getDireccion());
				// mensajeInfo("El usuario ingresado actualmente está vinculado a la Universidad como administrativo , Solicitamos diligenciar la información requerida en el formulario ");
				// //mostrarForm = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				persona = null;
				// mostrarForm = true;
			}
		}

		return persona;
	}

	public Persona buscarEstudiante(IdPersona id) {
		try {
			// Buscar Estudiantes
			est = new Estudiante();
			est = servicioPersona.obtenerEstudiante(id);
			persona = new Persona();

			if (est != null) {
				persona = est.convertirAPersona();
				solicitudUsuario.setDependencia(est.getDependencia());
				servicioGeneral.guardarObjeto(persona);

				if (solicitudUsuario.getDependencia() != null) {
					facultadSel = solicitudUsuario.getDependencia();
					facultadSelec = facultadSel.getFacultad().getId();
				} else {
					facultadSel = null;
				}
				// mostrarForm = true;
				// mensajeInfo("El usuario ingresado se encuentra como estudiante, Solicitamos diligenciar la información requerida en el formulario ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return persona;
	}
	
	
	public void agregarNuevoUsuario() {

		// Si no está registrado en Hermes
		boolean band = true;
		
		if(investigadorExterno.getEmail()!= null
				&& !investigadorExterno.getEmail().equals("")
				&& !investigadorExterno.getEmail().equals(" ")){
			
			if(investigadorExterno.getEmail().contains("@unal.edu.co")){
				band = true;
			}else{
				band = false;
			}
		}

		if(band){
			if (investigadorExterno.getId().getDocumento() != null
					&& investigadorExterno.getId().getTipoDocumento()!=null
					&& !investigadorExterno.getId().getTipoDocumento().equals("0")
					&& investigadorExterno.getNombre1() != null
					&& !investigadorExterno.getNombre1().equals("")
					&& !investigadorExterno.getNombre1().equals(" ")
					&& investigadorExterno.getApellido1() != null
					&& !investigadorExterno.getApellido1().equals("")
					&& !investigadorExterno.getApellido1().equals("")
					&& facultadSelec1 != null) {
	
				if (facultadSelec1 != null) {
					Dependencia dep = servicioDependencia.obtenerDependencia(facultadSelec1);
					if (dep != null) {
						investigadorExterno.setDependencia(dep);
	
						// Persona
						Persona person = new Persona();
						person.setId(investigadorExterno.getId());
						person.setApellido1(investigadorExterno.getApellido1());
						person.setApellido2(investigadorExterno.getApellido2());
						person.setNombre1(investigadorExterno.getNombre1());
						person.setNombre2(investigadorExterno.getNombre2());
						person.setGenero(investigadorExterno.getGenero());
						person.setEmail(investigadorExterno.getEmail());
	
						// Investigador
						Investigador inv = new Investigador();
						inv.setId(investigadorExterno.getId());
						inv.setInterno(Investigador.INTERNO);
						inv.setEvaluador(Investigador.NO_EVALUADOR);
	
						// Inv externo
						/*InvestigadorExterno ext = new InvestigadorExterno();
						ext.setId(investigadorExterno.getId());
						Institucion inst = new Institucion();
						inst.setId("3076");
						ext.setInstitucion(inst);
						ext.setDependencia(dep);*/
						
						
						// insertar persona
						try {
							Persona nuevaPersona = servicioPersona.obtenerPersona(investigadorExterno.getId());
							if (nuevaPersona == null) {
								servicioPersona.insertarNuevaPersonaDatosBasicos(person);
								servicioPersona.insertarNuevoInvestigador(inv);
								// servicioPersona.guardarInvestigador(inv);
								servicioPersona.insertaInterno(investigadorExterno);
							
							} else {
								Investigador investig = servicioPersona.obtenerInvestigador(investigadorExterno.getId());
								if (investig == null) {
									servicioPersona.insertarNuevoInvestigador(inv);
									servicioPersona.insertaInterno(investigadorExterno);
								} else {
									servicioPersona.guardarInvestigador(inv);
									servicioPersona.insertaInterno(investigadorExterno);
								}
							}
	
							// Agregar a la listapersonas
							Persona nuevaPersona2 = servicioPersona
									.obtenerPersona(investigadorExterno.getId());
							if (nuevaPersona2 != null) {
								listaPersonas.add(nuevaPersona2);
								FacesMessage msg = new FacesMessage(
										FacesMessage.SEVERITY_INFO,
										"El usuario fue ingresado",
										"El usuario fue ingresado");
								mostrarMensaje(msg, buscarPer2);
	
								mostrarForm = true;
								usuarioHermes = nuevaPersona2;
								cargarRol(usuarioHermes.getId().getDocumento(),
										usuarioHermes.getId().getTipoDocumento());
							}
	
						} catch (Exception e) {
							e.printStackTrace();
							FacesMessage msg = new FacesMessage(
									FacesMessage.SEVERITY_INFO,
									"El usuario no fue ingresado",
									"El usuario no fue ingresado");
							mostrarMensaje(msg, buscarPer2);
							mostrarForm = false;
						}
	
					} else {
						FacesMessage msg = new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Por favor seleccione la dependencia",
								"Por favor seleccione la dependencia");
						mostrarMensaje(msg, buscarPer2);
						mostrarForm = false;
					}
				} else {
					FacesMessage msg = new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Por favor seleccione la dependencia",
							"Por favor seleccione la dependencia");
					mostrarMensaje(msg, buscarPer2);
					mostrarForm = false;
				}
	
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Por favor diligencie los campos solicitados.",
						"Por favor diligencie los campos solicitados.");
				mostrarMensaje(msg, buscarPer2);
				mostrarForm = false;
			}
		
		}else{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Por favor ingrese un correo electrónico válido de la Universidad.",
					"Por favor ingrese un correo electrónico válido de la Universidad.");
			mostrarMensaje(msg, buscarPer2);
			mostrarForm = false;
		}
	}

	protected void mostrarMensaje(FacesMessage msg, UIComponent component) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (component == null) {
			context.addMessage(null, msg);
		} else {
			context.addMessage(component.getClientId(context), msg);
		}
		
	}

	public void buscarPersonaOld(Persona persona) {

		IdPersona id = persona.getId();
		/*
		 * id.setDocumento(solicitudUsuario.getUsuarioDocumento());
		 * id.setTipoDocumento(solicitudUsuario.getUsuarioTipoDocumento());
		 * 
		 * persona = servicioPersona.obtenerPersonaRoles(id);
		 */

		if (persona != null) {
			if (persona instanceof Investigador) {

				if (persona instanceof InvestigadorExterno) {
					persona = servicioPersona
							.obtenerInvestigadorExternoCompleto(persona.getId());
					// mostrarForm = true;
				} else if (persona instanceof InvestigadorInterno) {

					persona = servicioPersona
							.obtenerInvestigadorInternoCompleto(persona.getId());
					InvestigadorInterno investigadorInterno = (InvestigadorInterno) persona;

					if (investigadorInterno.getDependencia() != null
							&& investigadorInterno.getDependencia()
									.getFacultad() != null) {
						facultadSelec = investigadorInterno.getDependencia()
								.getFacultad().getId();
						dependencia = investigadorInterno.getDependencia();
					}

				}

				// mostrarForm = true;

			}

			// mostrarForm = true;
		} else {

			// Buscar Estudiantes
			est = new Estudiante();
			est = servicioPersona.obtenerEstudiante(id);

			if (est != null) {
				persona = est.convertirAPersona();
				solicitudUsuario.setDependencia(est.getDependencia());
				if (solicitudUsuario.getDependencia() != null) {
					facultadSel = ((Dependencia) dependenciasUN.get(0));
					facultadSel = solicitudUsuario.getDependencia();
					facultadSelec = facultadSel.getFacultad().getId();
				} else {
					facultadSel = null;
				}
				dependencia = solicitudUsuario.getDependencia();
				mostrarForm = true;
				mensajeInfo("El usuario ingresado se encuentra como estudiante, Solicitamos diligenciar la información requerida en el formulario ");
			}

			else {
				if (persona == null) {
					System.out
							.println("********************* persona nula, buscando VEmpleadoSara");
					String hql = "FROM VEmpleadoSara ves WHERE ves.id.documento = '"
							+ id.getDocumento()
							+ "' AND ves.id.tipoDocumento = '"
							+ id.getTipoDocumento() + "' ORDER BY ves.estado";
					System.out.println("********************* hql: " + hql);
					List<VEmpleadoSara> listaEmpleados = servicioGeneral
							.obtenerObjetos(VEmpleadoSara.class, hql);
					if (listaEmpleados.size() > 0) {
						VEmpleadoSara eS = listaEmpleados.get(0);
						try {
							persona = new Persona();
							// BeanUtils.copyProperties(persona, eS); // los
							// nombres
							// de atributos son diferentes...
							persona.setId(eS.getId());
							persona.setApellido1(eS.getApellido());
							persona.setApellido2(eS.getApellido());
							persona.setNombre1(eS.getNombres());
							persona.setEmail(eS.getEmail());
							persona.setDireccion(eS.getDireccion());
							// persona.setTelefono(eS.getTelefono());
							// persona.setFechaNacimiento(eS.get);
							mensajeInfo("El usuario ingresado actualmente está vinculado a la Universidad como administrativo , Solicitamos diligenciar la información requerida en el formulario ");
							mostrarForm = true;
						} catch (Exception e) {

							e.printStackTrace();
							persona = null;
							mostrarForm = true;
						}
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
		}
		mostrarForm = true;
		//cargarRol();
		mensajeInfo("El usuario ingresado no existe en el sistema, Solicitamos diligenciar la información requerida en el formulario ");
	}

	// ********************guardar

	public void guardarSolicitud() {

		try {			

			if (panelRender[5]) {
				solicitudUsuario.setDependencia(new Dependencia(
						facultadSelec1));
			} else {
				solicitudUsuario.setDependencia(new Dependencia(dependenciaUsuario2));
			}

			solicitudUsuario.setUsuario(usuarioHermes);
			solicitudUsuario.setUsuarioDocumento(usuarioHermes.getId().getDocumento());
			solicitudUsuario.setUsuarioTipoDocumento(usuarioHermes.getId().getTipoDocumento());
			solicitudUsuario.setApellido1(usuarioHermes.getApellido1());
			solicitudUsuario.setApellido2(usuarioHermes.getApellido2());
			solicitudUsuario.setNombre1(usuarioHermes.getNombre1());
			solicitudUsuario.setNombre2(usuarioHermes.getNombre2());
			solicitudUsuario.setEmail(usuarioHermes.getEmail());
			
			solicitudUsuario.setEstadoSol("I");
			servicioGeneral.guardarObjeto(solicitudUsuario);
			
			if (solicitudUsuario.getId() != null) {
				guardarRoles();
			}

			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage(
					"La información ha sido guardada correctamente con el número "
							+ solicitudUsuario.getId());
			context.addMessage("datosGuardados", mensaje);
			
			
			sesion.removeAttribute("ManejadorConsultarSolicitudUsuario");
			sesion.removeAttribute("manejadorConsultarSolicitudesUsuario");	
			sesion.removeAttribute("manejadorAdministrarSolicitudesUsuario");
			sesion.removeAttribute("manejadorConsultarHistoricoSolicitudesUsuario");
			sesion.removeAttribute("ManejadorCrearSolicitudUsuario");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void guardarEnviarSolicitud() {

		try {

			if (solicitudUsuario.getFechaFin() != null) {				

			  if(usuarioHermes!=null && usuarioHermes.getId()!=null){
				
				Persona personaAux = servicioPersona.obtenerPersonaRoles(usuarioHermes.getId());
				List rolesSeleccionados = Arrays.asList(rolesSeleccionadosArray);
				Investigador inv = null;

				if (panelRender[5]) {
					solicitudUsuario.setDependencia(new Dependencia(
							facultadSelec1));
				} else {
					solicitudUsuario.setDependencia(new Dependencia(dependenciaUsuario2));
				}

				solicitudUsuario.setUsuario(usuarioHermes);
				solicitudUsuario.setUsuarioDocumento(usuarioHermes.getId().getDocumento());
				solicitudUsuario.setUsuarioTipoDocumento(usuarioHermes.getId().getTipoDocumento());
				solicitudUsuario.setApellido1(usuarioHermes.getApellido1());
				solicitudUsuario.setApellido2(usuarioHermes.getApellido2());
				solicitudUsuario.setNombre1(usuarioHermes.getNombre1());
				solicitudUsuario.setNombre2(usuarioHermes.getNombre2());
				solicitudUsuario.setEmail(usuarioHermes.getEmail());
				

				solicitudUsuario.setEstadoSol("P");
				servicioGeneral.guardarObjeto(solicitudUsuario);
				
				if (solicitudUsuario.getId() != null) {
					guardarRoles();
				}
				
				respuestaGuardarEnviar();
				
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage mensaje = new FacesMessage(
						"La información ha sido enviada correctamente con el número "
								+ solicitudUsuario.getId());
				context.addMessage("datosGuardados", mensaje);

				sesion.removeAttribute("ManejadorConsultarSolicitudUsuario");
				sesion.removeAttribute("manejadorAdministrarSolicitudesUsuario");
				sesion.removeAttribute("manejadorConsultarSolicitudesUsuario");				
				sesion.removeAttribute("manejadorConsultarHistoricoSolicitudesUsuario");
				sesion.removeAttribute("ManejadorCrearSolicitudUsuario");

			  }else{
				  // usuario null
				  FacesContext context = FacesContext.getCurrentInstance();
				  FacesMessage mensaje = new FacesMessage(
							"La información NO ha sido enviada.");
				  mensaje.setSeverity(mensaje.SEVERITY_ERROR);
				context.addMessage("datosGuardados", mensaje);
			  }
			  
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage mensaje = new FacesMessage(
						"Ingrese la fecha de vigencia de los roles. La información NO ha sido enviada.");
				mensaje.setSeverity(mensaje.SEVERITY_ERROR);
				context.addMessage("datosGuardados", mensaje);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// *************************Roles****

	public void cargarRol(String documento, String tipoDocumento) {

		List lista2 = new ArrayList();

		rolesAntes = this.servicioPersona.buscarRoles2(documento, 
				tipoDocumento); // todos los roles que tiene y 
								// que corresponden con la lista de posibles roles para escoger
		
		if (rolesAntes != null && rolesAntes.size() > 0) {
			rolesAntesArray = new String[rolesAntes.size()];
			for (int i = 0; i < rolesAntes.size(); i++) {
				rolesAntesArray[i] = rolesAntes.get(i).getId();
			}
		}

		todRolesSol = this.servicioPersona.obtenerRolesSolicitud(); // todos los roles
																	// elegibles
																	// en la
																	// solicitud

		if (todRolesSol != null && todRolesSol.size() > 0) {

			this.rolesItemArray = new SelectItem[todRolesSol.size()];

			int i = 0; // guarda todos roles en lista
			for (Iterator it = todRolesSol.iterator(); it.hasNext();) {
				Rol roles = (Rol) it.next();
				rolesItemArray[i] = new SelectItem(roles.getId(), roles
						.getNombre().toLowerCase());
				i++;
			}

			this.rolesSiItemArray = new SelectItem[rolesAntes.size()];

			rolesSeleccionadosArray = new String[rolesAntes.size()];
			int k = 0;
			for (Iterator itr = rolesAntes.iterator(); itr.hasNext();) { // roles
																			// que
																			// tiene
																			// antes
																			// de la 
																			// solicitud
				Rol roles2 = (Rol) itr.next();
				lista2.add(roles2.getId());
				rolesSeleccionadosArray[k] = roles2.getId();
				k++;
			}

			rolesSeleccionadosArray = (String[]) lista2.toArray(new String[0]); // muestra
																				// los
																				// roles
																				// existentes
																				// en
																				// el
																				// panel
																				// de
																				// roles
		}

	}

		public void guardarRoles() {
	
			// buscar roles en la bd y seleccionados
			if(rolesSeleccionadosArray!=null){
				
			
			List<String> rolesSolicitudTodos = Arrays.asList(rolesSeleccionadosArray); // todos los roles de la sol
			
			System.out.println(rolesSolicitudTodos.size());
	
			List rolesRetirar = new ArrayList(); // roles a borrar
			List rolesAgregar = new ArrayList(); // roles a agreg
	
			HashMap<String, String> rolesNuevo = new HashMap<String, String>();
			for (int i = 0; i < rolesSolicitudTodos.size(); i++) {
				rolesNuevo.put(rolesSolicitudTodos.get(i),
						rolesSolicitudTodos.get(i));
			}
	
			if(rolesAntesArray!=null){
				List<String> rolesAntesTodos = Arrays.asList(rolesAntesArray); // todos los roles antes de la sol
				HashMap<String, String> rolesAnt = new HashMap<String, String>();
				for (int i = 0; i < rolesAntesTodos.size(); i++) {
					rolesAnt.put(rolesAntesTodos.get(i), rolesAntesTodos.get(i));
				}
		
				for (int j = 0; j < rolesAntesTodos.size(); j++) {
		
					if (!rolesNuevo.containsKey(rolesAntesTodos.get(j)) ) {
						
						rolAux = new Rol();
						rolAux.setId(rolesAntesTodos.get(j));
						rolAux.setNombre(rolesAntesTodos.get(j));
						
						if(listaRoles.contains(rolAux)){
							if (!rolAux.getId().equals("I")) {
								SolicitudPersonaRol sperRol1 = new SolicitudPersonaRol();
								sperRol1.setIdSolicitud(solicitudUsuario.getId());
								sperRol1.setRol(rolAux);
								sperRol1.setAccion("R");
								System.out.println(sperRol1.getRol().getId());
								System.out.println(sperRol1.getAccion());
								servicioGeneral.guardarObjeto(sperRol1);
							}
						}
							
					}
		
				  }
				
				for (int j = 0; j < rolesSolicitudTodos.size(); j++) {
					if (!rolesAnt.containsKey(rolesSolicitudTodos.get(j))) {
						rolAux2 = new Rol();
						rolAux2.setId(rolesSolicitudTodos.get(j));
						rolAux2.setNombre(rolesSolicitudTodos.get(j));
		
						SolicitudPersonaRol sperRol2 = new SolicitudPersonaRol();
						sperRol2.setIdSolicitud(solicitudUsuario.getId());
						sperRol2.setRol(rolAux2);
						sperRol2.setAccion("A");
						System.out.println(sperRol2.getRol().getId());
						System.out.println(sperRol2.getAccion());
						servicioGeneral.guardarObjeto(sperRol2);
					}
				}
			
			}else{
				for (int j = 0; j < rolesSolicitudTodos.size(); j++) {					
						rolAux2 = new Rol();
						rolAux2.setId(rolesSolicitudTodos.get(j));
						rolAux2.setNombre(rolesSolicitudTodos.get(j));
		
						SolicitudPersonaRol sperRol2 = new SolicitudPersonaRol();
						sperRol2.setIdSolicitud(solicitudUsuario.getId());
						sperRol2.setRol(rolAux2);
						sperRol2.setAccion("A");
						System.out.println(sperRol2.getRol().getId());
						System.out.println(sperRol2.getAccion());
						servicioGeneral.guardarObjeto(sperRol2);					
				}
				
			  }
			
			}
		}

	// *******************************************************************

	// public void respuestaGuardar(){
	//
	// String documento;
	// String correoAsesor = new String();
	// String nombreAsesor = new String();
	// String nombreUsuario = new String();
	//
	// IdPersona idPersonaUsuario;
	// IdPersona idPersonaAsesor;
	//
	//
	// try {
	//
	//
	// idPersonaAsesor = new IdPersona(solicitudUsuario.getSolicDocumento(),
	// solicitudUsuario.getSolicTipoDocumento());
	// correoAsesor = (String)
	// servicioPersona.obtenerPersona(idPersonaAsesor).getEmail();
	// nombreAsesor = (String)
	// servicioPersona.obtenerPersona(idPersonaAsesor).getNombreCompleto();
	// idPersonaUsuario = new IdPersona(solicitudUsuario.getUsuarioDocumento(),
	// solicitudUsuario.getUsuarioTipoDocumento());
	// nombreUsuario = (String)
	// servicioPersona.obtenerPersona(idPersonaUsuario).getNombreCompleto();
	//
	//
	// Correo correo = new Correo();
	// correo.setOrigen(Correo.CORREO_HERMES);
	// correo.adicionarDireccion(correoAsesor);
	// correo.adicionarCopiaOculta(Correo.CORREO_HERMES);
	// correo.setAsunto(cargarPlantilla(195).getAsunto());
	// documento = cargarPlantilla(195).getCuerpo()
	// .replaceAll("<<ASESOR>>", nombreAsesor)
	// .replaceAll("<<USUARIO>>",nombreUsuario)
	// .replaceAll("<<ACCION>>", "creada")
	// .replaceAll("<<COMPLEMENTO>>",
	// "Puede continuar la edición de su solicitud a través del link Editar, en su solicitud ubicada en la sección Consultar Solicitud Usuario.");
	// correo.setCuerpo(documento);
	// servicioCorreo.enviarCorreo(correo);
	//
	// } catch (Exception e) {
	// System.out.println(e.toString());
	// }
	// }

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

			idPersonaAsesor = new IdPersona(
					solicitudUsuario.getResponsableDocumento(),
					solicitudUsuario.getResponsableTipoDocumento());
			correoAsesor = (String) servicioPersona.obtenerPersona(
					idPersonaAsesor).getEmail();
			nombreAsesor = (String) servicioPersona.obtenerPersona(
					idPersonaAsesor).getNombreCompleto();
			idPersonaUsuario = new IdPersona(
					solicitudUsuario.getUsuarioDocumento(),
					solicitudUsuario.getUsuarioTipoDocumento());
			correoUsuario = solicitudUsuario.getEmail();
			nombreUsuario = (String) servicioPersona.obtenerPersona(
					idPersonaUsuario).getNombreCompleto();

			// usuario
			Correo correo2 = new Correo();
			correo2.setOrigen(Correo.CORREO_HERMES);
			correo2.adicionarDireccion(correoUsuario);
			//correo2.adicionarCopiaOculta(Correo.CORREO_HERMES);
			correo2.setAsunto(cargarPlantilla(196).getAsunto());
			documento2 = cargarPlantilla(196).getCuerpo()
					.replaceAll("<<USUARIO>>", nombreUsuario)
					.replaceAll("<<ASESOR>>", nombreAsesor)
					.replaceAll("<<CORREOA>>", correoAsesor);
			correo2.setCuerpo(documento2);
			servicioCorreo.enviarCorreo(correo2);

			// asesor
			Correo correo3 = new Correo();
			correo3.setOrigen(Correo.CORREO_HERMES);
			correo3.adicionarDireccion(correoAsesor);
			//correo3.adicionarCopiaOculta(Correo.CORREO_HERMES);
			correo3.setAsunto(cargarPlantilla(195).getAsunto());
			documento3 = cargarPlantilla(195)
					.getCuerpo()
					.replaceAll("<<USUARIO>>", nombreUsuario)
					.replaceAll("<<ASESOR>>", nombreAsesor)
					.replaceAll("<<ACCION>>", "enviada para revisión")
					.replaceAll("<<COMPLEMENTO>>",
							"Esta solicitud será evaluada y posteriormente se dará a conocer su respuesta.");
			correo3.setCuerpo(documento3);
			// System.out.println(correo3.getCuerpo());
			servicioCorreo.enviarCorreo(correo3);

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	// *******************************************************************************************

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

	public Dependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public Dependencia getFacultadSel() {
		return facultadSel;
	}

	public void setFacultadSel(Dependencia facultadSel) {
		this.facultadSel = facultadSel;
	}

	public String getFacultadSelec() {
		return facultadSelec;
	}

	public void setFacultadSelec(String facultadSelec) {
		this.facultadSelec = facultadSelec;
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

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Estudiante getEst() {
		return est;
	}

	public void setEst(Estudiante est) {
		this.est = est;
	}

	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public boolean[] getPanelRender() {
		return panelRender;
	}

	public void setPanelRender(boolean[] panelRender) {
		this.panelRender = panelRender;
	}

	public boolean isMostrarTabla() {
		return mostrarTabla;
	}

	public void setMostrarTabla(boolean mostrarTabla) {
		this.mostrarTabla = mostrarTabla;
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

	public InvestigadorInterno getInvestigadorExterno() {
		return investigadorExterno;
	}

	public void setInvestigadorExterno(InvestigadorInterno investigadorExterno) {
		this.investigadorExterno = investigadorExterno;
	}

	public InvestigadorInterno getInv() {
		return inv;
	}

	public void setInv(InvestigadorInterno inv) {
		this.inv = inv;
	}

	public String getInsitucionNombre() {
		return insitucionNombre;
	}

	public void setInsitucionNombre(String insitucionNombre) {
		this.insitucionNombre = insitucionNombre;
	}

	public List getListaInstitucion() {
		return listaInstitucion;
	}

	public void setListaInstitucion(List listaInstitucion) {
		this.listaInstitucion = listaInstitucion;
	}

	public SelectItem[] getInstitucionItem() {
		return institucionItem;
	}

	public void setInstitucionItem(SelectItem[] institucionItem) {
		this.institucionItem = institucionItem;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public SelectItem[] getGeneroItem() {
		return generoItem;
	}

	public void setGeneroItem(SelectItem[] generoItem) {
		this.generoItem = generoItem;
	}

	public UIComponent getBuscarPer2() {
		return buscarPer2;
	}

	public void setBuscarPer2(UIComponent buscarPer2) {
		this.buscarPer2 = buscarPer2;
	}

	public String getFacultadSelec1() {
		return facultadSelec1;
	}

	public void setFacultadSelec1(String facultadSelec1) {
		this.facultadSelec1 = facultadSelec1;
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

	public boolean isVerDependencia() {
		return verDependencia;
	}

	public void setVerDependencia(boolean verDependencia) {
		this.verDependencia = verDependencia;
	}

	public SelectItem[] getTieneCorreoItems() {
		return tieneCorreoItems;
	}

	public void setTieneCorreoItems(SelectItem[] tieneCorreoItems) {
		this.tieneCorreoItems = tieneCorreoItems;
	}

	public String getTieneCorreoUN() {
		return tieneCorreoUN;
	}

	public void setTieneCorreoUN(String tieneCorreoUN) {
		this.tieneCorreoUN = tieneCorreoUN;
	}

	public Persona getUsuarioHermes() {
		return usuarioHermes;
	}

	public void setUsuarioHermes(Persona usuarioHermes) {
		this.usuarioHermes = usuarioHermes;
	}

}