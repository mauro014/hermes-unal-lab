package co.edu.unal.hermes.vista.laboratorios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.PieChartModel;

import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.TipoCargo;
import co.edu.unal.hermes.modelo.TipoDedicacion;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoFormacion;
import co.edu.unal.hermes.modelo.TipoVinculacion;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.VEmpleadoSara;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.ArchivoLaboratorioPersona;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioObservaciones;
import co.edu.unal.hermes.modelo.laboratorios.PersonaLaboratorio;

public class ManejadorLaboratoriosRecursoHumano extends ManejadorLaboratorios {

	private static final long serialVersionUID = 7800706908529459710L;

	private SelectItem[] tipoDocumentoItem;
	private List<PersonaLaboratorio> personasLaboratorioEliminadas;
	private Boolean cambiosEnListaPersonas;
	private Boolean cambiosEnPermisos = false;
	private SelectItem[] rolSelectItem;
	private List<Rol> listaRolesLaboratorio;
	private Rol rolSeleccionado;
	private PersonaLaboratorio personaSeleccionada;
	private String cargoPersona;
	private Tipos tipoVinculacionLaboratorio;
	private String extension;
	private String extensionLaboratorio;
	
	private ArchivoLaboratorioPersona archivoLaboratorioSeleccionado;
	private Tipos tipoArchivoSeleccionado;
	private SelectItem[] tipoArchivoSelectItem;
	private LinkedList<ArchivoLaboratorioPersona> listaArchivos;
	
	//METROLOGÍA
	private SelectItem[] selectItemNivelFormacion;
	private String nivelFormacionSeleccionada;
	
	//Roles
	private SelectItem[] listaTiposPersona;
	private Tipos tipoPersonaSeleccionado;
	private Boolean mostrarDatosPersona = false;
	
	private Persona persona;
	private IdPersona idPersonaBuscar;
	private InvestigadorInterno investigadorInterno;
	private Estudiante estudiante;
	
	private String telefonoPersona;
	
	private Integer numDocentesPlanta = 0;
	private Integer numDocentesNoPlanta = 0;
	private Integer numAdminPlanta = 0;
	private Integer numAdminNoPlanta = 0;
	private Integer numEstudiantes = 0;
	private Integer numEgresados = 0;
	private Integer numContratistas = 0;
	private Integer numExternos = 0;
	
	private LaboratorioObservaciones observacionSel;
	
	private String aceptaTratamientoDatos = "N";

	public ManejadorLaboratoriosRecursoHumano() {
		personasLaboratorioEliminadas = new ArrayList<PersonaLaboratorio>();
		cargarTiposDocumento();
		cargarPersonasLaboratorio();
		idManejador = RECURSO_HUMANO;
		persona = new Persona();
		idPersonaBuscar = new IdPersona();
		cambiosEnListaPersonas = false;
		extension = extensionLaboratorio;		
		tipoArchivoSelectItem = servicioGeneral.selectItemHijosDeTiposValorObjeto(Tipos.TIPOS_ARCHIVOS_PERSONA_LABORATORIO);
		tipoArchivoSeleccionado = (Tipos) tipoArchivoSelectItem[0].getValue();
		listaArchivos = new LinkedList<ArchivoLaboratorioPersona>();
		selectItemNivelFormacion = servicioGeneral.retornaSelectItemArregloDeHijosDeTipos(Tipos.TIPO_LAB_METRO_NIVEL_FORMACION);
		listaTiposPersona = servicioGeneral.selectItemHijosDeTiposValorObjetoSeleccione2(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO);
		observacionSel = new LaboratorioObservaciones();
	}
	
	private PieChartModel pieModel;
	
	public void actualizarGraficoPersonal() {
		pieModel = new PieChartModel();
		pieModel.set("Docente Planta", numDocentesPlanta);
		pieModel.set("Docente No Planta", numDocentesNoPlanta);
		pieModel.set("Administrativo Planta", numAdminPlanta);
		pieModel.set("Administrativo No Planta", numAdminNoPlanta);
		pieModel.set("Estudiante", numEstudiantes);
		pieModel.set("Egresado", numEgresados);
		pieModel.set("Contratista", numContratistas);
		pieModel.set("Externo", numExternos);
	}	

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}
	
	public void insertarObservacion() {
		if (!esCadenaVacia(observacionSel.getDescripcion())) {
			observacionSel.setDescripcion(controlTamanoCadena(observacionSel.getDescripcion(), 3000));
			LaboratorioObservaciones observacionObj = new LaboratorioObservaciones();
			observacionObj.setFecha(getToday());
			observacionObj.setFormulario(1L);
			observacionObj.setPersona(personaActual);
			observacionObj.setDescripcion(observacionSel.getDescripcion());
			laboratorioActual.adicionarObservacion(observacionObj);
			observacionSel = new LaboratorioObservaciones();
		} else {
			mensajeError("Ingrese la observación y luego de clic en el boton agregar (Máx. 2000 caracteres).");
		}
	}

	public void calcularNumPersonalLab() {
		for (PersonaLaboratorio persona : listaPersonasLaboratorio) {
			if(!esNulo(persona.getTipoVinculacion())) {
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Docente_planta))
					numDocentesPlanta+=1;
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Docente_no_planta))
					numDocentesNoPlanta+=1;
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Admin_planta))
					numAdminPlanta+=1;
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Admin_no_planta))
					numAdminNoPlanta+=1;
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Estudiante))
					numEstudiantes+=1;
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Contratista))
					numContratistas+=1;
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Egresado))
					numEgresados+=1;
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Externo))
					numExternos+=1;
			}
		}
		
		for (PersonaLaboratorio persona : personasLaboratorioEliminadas) {
			if(!esNulo(persona.getTipoVinculacion())) {
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Docente_planta))
					numDocentesPlanta-=1;
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Docente_no_planta))
					numDocentesNoPlanta-=1;
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Admin_planta))
					numAdminPlanta-=1;
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Admin_no_planta))
					numAdminNoPlanta-=1;
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Estudiante))
					numEstudiantes-=1;
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Contratista))
					numContratistas-=1;
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Egresado))
					numEgresados-=1;
				if(persona.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Externo))
					numExternos-=1;
			}
		}
	}
	
	public void cambiosEnPermisos() {
		cambiosEnPermisos=true;
	}
	
	public String modificarArchivosPersonaLaboratorio()
	{		
		listaArchivos = obtenerArchivosPersonaLaboratorio(personaSeleccionada.getPersona(), laboratorioActual);
		return "";
	}
	
	public LinkedList<ArchivoLaboratorioPersona> obtenerArchivosPersonaLaboratorio(Persona p, Laboratorio lab)
	{
		List<ArchivoLaboratorioPersona> listaArchivosPorPersona = new ArrayList<ArchivoLaboratorioPersona>();
		LinkedList<ArchivoLaboratorioPersona> listaArchivosPorPersonalLink = new LinkedList<ArchivoLaboratorioPersona>();
		String hql;
		
		hql = "from ArchivoLaboratorioPersona al WHERE al.laboratorio = '"+ lab.getId() +"' AND al.persona.id.documento = '"+p.getId().getDocumento()+"' AND al.persona.id.tipoDocumento = '"+p.getId().getTipoDocumento()+"' ORDER BY al.fechaRegistro";
		listaArchivosPorPersona = servicioGeneral.obtenerObjetos(ArchivoLaboratorioPersona.class, hql);
		
		if(listaArchivosPorPersona != null && listaArchivosPorPersona.size() > 0) {	
			for (ArchivoLaboratorioPersona archivoLaboratorioPersona : listaArchivosPorPersona)
				listaArchivosPorPersonalLink.add(archivoLaboratorioPersona);
		}	
		
		return listaArchivosPorPersonalLink;
	}
	
	public void seleccionarTipos() {
	}
	
	public void subirArchivo(FileUploadEvent event) {
		
		UploadedFile archivoSubir = event.getFile();
		String nombreArchivo = archivoSubir.getFileName();

		String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1, nombreArchivo.length()).toUpperCase();
		
		Tipos tipoArchivoSeleccionadoAux = new Tipos();
		
		if(tipoArchivoSeleccionado == null) {	
			tipoArchivoSeleccionadoAux = (Tipos) tipoArchivoSelectItem[0].getValue();
			tipoArchivoSeleccionado = (Tipos) servicioGeneral.obtenerObjeto(new Tipos(), tipoArchivoSeleccionadoAux.getId());
		}	
		else	
			tipoArchivoSeleccionado = (Tipos) servicioGeneral.obtenerObjeto(new Tipos(), tipoArchivoSeleccionado.getId());

		ArchivoLaboratorioPersona archivoNuevo = subirArchivoLaboratoriosPersona(event,tipoArchivoSeleccionado);
		archivoNuevo.setLaboratorio(laboratorioActual);
		
		Persona per = servicioPersona.obtenerPersona(new IdPersona(personaSeleccionada.getPersona().getId().getDocumento(), personaSeleccionada.getPersona().getId().getTipoDocumento()));
		archivoNuevo.setPersona(per);

		servicioGeneral.insertarObjetoConIdLong(archivoNuevo,archivoNuevo.getId());
		listaArchivos.add(archivoNuevo);
		
	}
	
	public void descargarArchivo() {
		descargarArchivoPersonaLaboratorios(archivoLaboratorioSeleccionado);
	}
	
	public void eliminarArchivo() {
		if (eliminarArchivoLaboratorioPersona(archivoLaboratorioSeleccionado.getId())) {
			listaArchivos.remove(archivoLaboratorioSeleccionado);
			System.out.println("eliminarArchivo listaArchivos.size: "+ listaArchivos.size());
			servicioGeneral.eliminarObjeto(archivoLaboratorioSeleccionado);
		} else {
			System.out.println("Delete operation is failed.");
		}
	}

	public void cargarPersonasLaboratorio() {
		String hql = "FROM PersonaLaboratorio pl WHERE pl.laboratorio.id = '" + laboratorioActual.getId() + "' ORDER BY pl.rol.id";
		listaPersonasLaboratorio = servicioGeneral.obtenerObjetos(PersonaLaboratorio.class, hql);
		calcularNumPersonalLab();
		actualizarGraficoPersonal();
	}

	public void eliminarPersona() {
		LinkedList<ArchivoLaboratorioPersona> listaArchivosPersonaLab = obtenerArchivosPersonaLaboratorio(personaSeleccionada.getPersona(), laboratorioActual);
		if(listaArchivosPersonaLab.size() == 0) {
			listaPersonasLaboratorio.remove(personaSeleccionada);
			personasLaboratorioEliminadas.add(personaSeleccionada);
			cambiosEnListaPersonas = true;
			calcularNumPersonalLab();
		}
		else
			mensajeError("RecursoHumanoLaboratorio:documentoCoordinador","Para eliminar la persona del laboraorio primero debe borrar los archivos que tenga asociados");
	}

	@Override
	public String salirGuardar() {
		if (validar()) {
			guardar();
			return (salir());
		} else {
			return null;
		}
	}

	public void guardar() {
		guardarLaboratorioActual(idManejador);
		if (cambiosEnListaPersonas || cambiosEnPermisos) {
			// se borran personas de BD
			for (PersonaLaboratorio pLE : personasLaboratorioEliminadas) {
				if (pLE.getId() != null) {
					servicioGeneral.eliminarObjeto(pLE);
				}
				// si la persona no tiene el rol en otro lab, se borra
				List<Laboratorio> laboratoriosCoordinador = new ArrayList<Laboratorio>();
				String documento = pLE.getPersona().getId().getDocumento();
				String tipoDocumento = pLE.getPersona().getId().getTipoDocumento();
				String rol = pLE.getRol().getId();
				String hHql = "FROM PersonaLaboratorio pL WHERE pL.persona.id.documento = '"
						+ documento
						+ "' AND pL.persona.id.tipoDocumento = '"
						+ tipoDocumento + "' AND pL.rol.id = '" + rol + "'";
				laboratoriosCoordinador = servicioGeneral.obtenerObjetos(Laboratorio.class, hHql);
				if (laboratoriosCoordinador.size() == 0) {
					PersonaRol perRol = new PersonaRol(tipoDocumento,documento, rol);
					servicioPersona.borrarRoles(perRol);
				}
			}

			// guardar personas nuevas:
			for (PersonaLaboratorio pL : listaPersonasLaboratorio) {
				if (pL.getId() == null) {
					// Se guarda Persona nueva en BD:
					if (servicioPersona.obtenerPersona(pL.getPersona().getId()) == null)
						servicioPersona.insertarNuevaPersonaDatosCompletos(pL.getPersona());
					servicioGeneral.guardarObjeto(pL);
					
					Investigador investigador = servicioPersona.obtenerInvestigador(pL.getPersona().getId());
					InvestigadorInterno investigadorInterno = servicioPersona.obtenerInvestigadorInterno(pL.getPersona().getId());
					
//					if(!(pL.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Egresado) 
//							|| pL.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Externo)
//							|| pL.getTipoVinculacion().getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Egresado))) {
//						
//					}
					
					if (investigador == null) {
						
						//Se crea Investigador
						investigador = new Investigador();
		                investigador.setId(pL.getPersona().getId());
		                CategoriaInvestigador categoriaInvestigador = new CategoriaInvestigador();
		                categoriaInvestigador.setId(3L);
		                investigador.setCategoriaInvestigador(categoriaInvestigador);
		                investigador.setInterno(Investigador.EXTERNO);
		                investigador.setEvaluador(Investigador.NO_EVALUADOR);
		                servicioPersona.insertarInvestigador(investigador);
		                investigador = servicioPersona.obtenerInvestigador(pL.getPersona().getId());
		                
		                //Se crea Investigador Interno
		                investigadorInterno = new InvestigadorInterno();
		                generarGuardarInvestigadorInterno(pL,investigadorInterno);
		                		                
		            } else {			            	
		            	if (investigadorInterno == null) {
		            		investigadorInterno = new InvestigadorInterno();
		            		generarGuardarInvestigadorInterno(pL,investigadorInterno);
		            	} else{
		            		investigadorInterno.setTipoFormacion(pL.getTipoFormacion());
		            		servicioPersona.guardarInvInterno(investigadorInterno);
		            	}
	            	}
					
					if(!esNulo(pL.getPermisoEdicion()) && pL.getPermisoEdicion())
						enviarCorreoNuevaPersonaLaboratorio(pL);

				} else {
					servicioGeneral.guardarObjeto(pL);
				}
				
				try {
					List<PersonaRol> listaPerRol = (List<PersonaRol>) servicioGeneral.obtenerPersonaRolXIdPersona(pL.getPersona().getId().getTipoDocumento(),pL.getPersona().getId().getDocumento(),pL.getRol().getId());
					PersonaRol pR = new PersonaRol();
					
					if(listaPerRol.size() == 0) {	
						pR = new PersonaRol(pL.getPersona().getId().getTipoDocumento(), pL.getPersona().getId().getDocumento(), pL.getRol().getId());
						pR.setFechaInicioRol(new Date());
						servicioGeneral.guardarObjeto(pR);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		}
		calcularCompletitud();
	}
	
	public void enviarCorreoNuevaPersonaLaboratorio(PersonaLaboratorio pl) {
		CorreoPlantilla correoActual = cargarPlantilla(390);
		Correo correoElectronico = new Correo();
		
		Persona coordinador = servicioGeneral.obtenerCoordinadorLaboratorio(laboratorioActual.getId());
		
		try {
			String correo = correoActual.getCuerpo();
			correo = correo.replaceAll("<<NOMBRE_COORDINADOR>>", coordinador.getNombreCompleto());
			correo = correo.replaceAll("<<NOMBRE_LABORATORIO>>", laboratorioActual.getNombre());
			correo = correo.replaceAll("<<NOMBRE_ROL>>", pl.getTipoVinculacion().getNombre());
			
			correoElectronico.setOrigen(Correo.CORREO_HERMES);
			//correoElectronico.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);
			correoElectronico.adicionarDireccion(pl.getPersona().getEmail());
						
			String asunto = correoActual.getAsunto();
			asunto = asunto.replaceAll("<<NOMBRE_LABORATORIO>>", laboratorioActual.getNombre());
			
			correoElectronico.setAsunto(asunto);
			correoElectronico.setCuerpo(correo);

		} catch (Exception e) {
		}
		
		servicioCorreo.enviarCorreo(correoElectronico);
	}
	
	public void generarGuardarInvestigadorInterno(PersonaLaboratorio pL , InvestigadorInterno investigadorInterno)
	{
		//Se crea Investigador Interno
        investigadorInterno = new InvestigadorInterno();
        investigadorInterno.setId(pL.getPersona().getId());
        
        if(laboratorioActual.getDepartamento() == null || laboratorioActual.getDepartamento().getId().equals("0"))
        	investigadorInterno.setDependencia(laboratorioActual.getFacultad());
        else
        	investigadorInterno.setDependencia(laboratorioActual.getDepartamento());
        
        servicioPersona.insertaInterno(investigadorInterno);// Se guardan datos mínimos inv interno
        investigadorInterno = servicioPersona.obtenerInvestigadorInterno(pL.getPersona().getId()); //Se busca objeto recien guardado
        
        TipoDedicacion tipodedicacion = new TipoDedicacion();
		tipodedicacion.setId(".");
		investigadorInterno.setTipoDedicacion(tipodedicacion);
		
		investigadorInterno.setTipoFormacion(pL.getTipoFormacion());

		TipoVinculacion tipovinculacion = new TipoVinculacion();
		tipovinculacion.setId("0");
		investigadorInterno.setTipoVinculacion(tipovinculacion);

		TipoCargo tipocargo = new TipoCargo();
		tipocargo.setId(".");
		investigadorInterno.setTipoCargo(tipocargo);
		
		if(laboratorioActual.getDepartamento() == null || laboratorioActual.getDepartamento().getId().equals("0"))
        	investigadorInterno.setDependencia(laboratorioActual.getFacultad());
        else
        	investigadorInterno.setDependencia(laboratorioActual.getDepartamento());
		
		servicioPersona.guardarInvInterno(investigadorInterno);
	}

	@Override
	public String siguiente() {
		if (validar()) {
			if (idManejador == laboratorioActual.getEtapaRegistro().intValue()) {
				laboratorioActual.setEtapaRegistro(new Integer((laboratorioActual.getEtapaRegistro()).intValue() + 1));
			}
			guardar();
			limpiarSesion();
			sesion.setAttribute("Laboratorio", laboratorioActual);
			sesion.setAttribute("solicitudLaboratorio", solicitudLab);
			sesion.removeAttribute("ManejadorMenuFormularioLaboratorios");
			return "laboratorioRiesgos";
		} else {
			return null;
		}
	}

	public Boolean validar() {
		boolean validar = true;

		if (listaPersonasLaboratorio.size() == 0) {
			mensajeError("RecursoHumanoLaboratorio:documentoCoordinador","Debe asociar al menos una persona al laboratorio.");
			validar = false;
		} else {
			int coordinadores = 0;
			for (PersonaLaboratorio pL : listaPersonasLaboratorio) {
				if (pL.getRol().getId().equals(Rol.COORDINADOR_LABORATORIO)) {
					coordinadores++;
				}
			}
			if (coordinadores == 0) {
				mensajeError("RecursoHumanoLaboratorio:documentoCoordinador","Debe asociar al menos una persona con el rol Coordinador laboratorio");
				validar = false;
			}
		}

		return validar;
	}

	private void cargarTiposDocumento() {
		List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerListaObjetosWhere(
				TipoDocumento.class,
				" WHERE "
				+ "t.id = '" + TipoDocumento.CEDULA + "' "
				+ "OR t.id = '" + TipoDocumento.CEDULA_EXTRANJERIA + "' "
				+ "OR t.id = '" + TipoDocumento.TARJETA_IDENTIDAD + "' "
				+ "OR t.id = '" + TipoDocumento.PASAPORTE + "'"
				);
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}

		// Cargar roles dentro del laboratorio:
		String hql = "FROM Rol WHERE comentario like 'Laboratorios - PER - LAB' ORDER BY nombre";
		listaRolesLaboratorio = servicioGeneral.obtenerObjetos(Rol.class, hql);
		rolSelectItem = new SelectItem[listaRolesLaboratorio.size()];
		for (int i = 0; i < listaRolesLaboratorio.size(); i++) {
			Rol r = listaRolesLaboratorio.get(i);
			rolSelectItem[i] = new SelectItem(r.getId(), r.getNombre());
		}
		rolSeleccionado = new Rol();
		tipoVinculacionLaboratorio = new Tipos();
	}
	
	public void asociarPersona() {
		if (persona != null) {
			Boolean asociar = true;
			for (PersonaLaboratorio pL : listaPersonasLaboratorio) {
				if (pL.getPersona().getId().equals(persona.getId())) {
					mensajeError("RecursoHumanoLaboratorio:numDocumento","La persona ya está asociada al Laboratorio.");
					asociar = false;
				}
				// un solo coordinador:
				if (pL.getRol().getId().equals(Rol.COORDINADOR_LABORATORIO) && rolSeleccionado.getId().equals(Rol.COORDINADOR_LABORATORIO)) {
					mensajeError("RecursoHumanoLaboratorio:rolSI","Solo puede existir un Coordinador.");
					asociar = false;
				}
				
				// Nivel de formación:
				if (!getEsEmpleadoUN() && nivelFormacionSeleccionada.equals(".")) {
					mensajeError("RecursoHumanoLaboratorio:siNivFormacion","Debe seleccionar el nivel de formación");
					asociar = false;
				}
				
				// Nombres, apellidos y correo - Externos
				if (getEsExterno() && persona.getNombre1().equals("")) {
					mensajeError("RecursoHumanoLaboratorio:primerNombre","Debe ingresar el primer nombre");
					asociar = false;
				}
				
				if (getEsExterno() && persona.getApellido1().equals("")) {
					mensajeError("RecursoHumanoLaboratorio:primerApellido","Debe ingresar el primer apellido");
					asociar = false;
				}
				
				if (getEsExterno() && persona.getEmail().equals("")) {
					mensajeError("RecursoHumanoLaboratorio:emailPersona","Debe ingresar el correo electrónico");
					asociar = false;
				}
			}

			if (asociar) {
				PersonaLaboratorio pl = new PersonaLaboratorio();
				pl.setLaboratorio(laboratorioActual);
				pl.setPersona(persona);
				pl.setTipoVinculacion(tipoPersonaSeleccionado);
				pl.setExtension(telefonoPersona);
				
				Rol rol = servicioGeneral.obtenerObjetoXID(Rol.class, rolSeleccionado.getId()).get(0);
				pl.setRol(rol);
				
				if(pl.getRol().getId().equals(Rol.COORDINADOR_LABORATORIO)) {
					pl.setPermisoConsulta(true);
					pl.setPermisoEdicion(true);
				}
				
				if(!getEsEmpleadoUN()) {
					TipoFormacion tf = convertirNivelFormacionMetroredAHermes(nivelFormacionSeleccionada);
					if(tf != null)
						pl.setTipoFormacion(tf);
				}
				
				listaPersonasLaboratorio.add(pl);
				
				rolSeleccionado = new Rol();				
				persona = new Persona();
				idPersonaBuscar = new IdPersona();
				investigadorInterno = new InvestigadorInterno();
				estudiante = new Estudiante();
				
				mostrarDatosPersona = false;
				tipoPersonaSeleccionado = null;
				cambiosEnListaPersonas = true;
				
				telefonoPersona = null;
				nivelFormacionSeleccionada = null;
			}
			calcularNumPersonalLab();
		}
	}
	
	public TipoFormacion convertirNivelFormacionMetroredAHermes(String nvlFormacionMetrored)
	{
		TipoFormacion tf= null;
		String tfId = "SNA";
		
		if(nvlFormacionMetrored.equals(Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Doctorado.toString()))
			tfId = "DO"; 
		else if(nvlFormacionMetrored.equals(Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Especializacion.toString()))
			tfId = "ES"; 
		else if(nvlFormacionMetrored.equals(Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Maestria.toString()))
			tfId = "MA";
		else if(nvlFormacionMetrored.equals(Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Operativo.toString()))
			tfId = "PRBS";
		else if(nvlFormacionMetrored.equals(Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Otro.toString()))
			tfId = "SNA";
		else if(nvlFormacionMetrored.equals(Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Tecnico.toString()))
			tfId = "TC";
		else if(nvlFormacionMetrored.equals(Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Tecnologo.toString()))
			tfId = "TL";
		else if(nvlFormacionMetrored.equals(Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Universitario.toString()))
			tfId = "UN";
		
		List<TipoFormacion> lista = servicioGeneral.obtenerObjetoXID(TipoFormacion.class, tfId);
		
		if(lista != null)
			tf = lista.get(0);
		
		return tf;
	}
	
	public Boolean getEsEstudiante() {
		if(tipoPersonaSeleccionado.getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Estudiante))
			return true;
		return false;
	}
	
	public Boolean getEsExterno() {
		if(!esNulo(tipoPersonaSeleccionado)) {
			if(tipoPersonaSeleccionado.getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Contratista)
					|| tipoPersonaSeleccionado.getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Egresado)
					|| tipoPersonaSeleccionado.getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Externo))
				return true;
		}
		return false;
	}
	
	public Boolean getEsEmpleadoUN() {
		if(tipoPersonaSeleccionado.getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Docente_planta)
				|| tipoPersonaSeleccionado.getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Docente_no_planta)
				|| tipoPersonaSeleccionado.getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Admin_planta)
				|| tipoPersonaSeleccionado.getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Admin_no_planta)
				)
			return true;
		return false;
	}
	
	public Boolean validarBuscarPersona() {
		String identificacion = idPersonaBuscar.getDocumento().trim();
		String tipoDocumento = idPersonaBuscar.getTipoDocumento();
		IdPersona idPersona = new IdPersona(identificacion, tipoDocumento);
		
		//Campos vacios
		if(identificacion.equals("")) {
			mensajeError("RecursoHumanoLaboratorio:numDocumento","Debe ingresar el número de documento de la persona");
			return false;
		}
		
		if (tipoPersonaSeleccionado.getId().equals(Tipos.Ninguno)) {
			mensajeError("RecursoHumanoLaboratorio:listaTipoPersonaLab","Debe seleccionar el tipo de asociación de la persona con la universidad");
			return false;
		}
		
		//Tipos de asociacion
		persona = new Persona();
		persona = servicioPersona.obtenerPersona(idPersona);
		investigadorInterno = servicioPersona.obtenerInvestigadorInterno(idPersona);
		
		//DOCENTES Y ADMINISTRATIVOS
		if(getEsEmpleadoUN()) {
			if(!esNulo(persona) && !esNulo(investigadorInterno) && persona.getEsActivoSara().equals("S")) {
				//Docente planta
				if(tipoPersonaSeleccionado.getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Docente_planta)) {
					if(validarDocentePlanta(investigadorInterno)) {
						return true;
					} else {
						mensajeError("RecursoHumanoLaboratorio:numDocumento","El documento ingresado no corresponde a un DOCENTE DE PLANTA");
						return false;
					}
				//Docente no planta
				} else if(tipoPersonaSeleccionado.getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Docente_no_planta)){
					if(validarDocenteNoPlanta(investigadorInterno)) {
						return true;
					} else {
						mensajeError("RecursoHumanoLaboratorio:numDocumento","El documento ingresado no corresponde a un DOCENTE QUE NO ES DE PLANTA");
						return false;
					}
				//Administrativo planta	
				} else if(tipoPersonaSeleccionado.getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Admin_planta)){
					if(validarAdministrativoPlanta(investigadorInterno)) {
						return true;
					} else {
						mensajeError("RecursoHumanoLaboratorio:numDocumento","El documento ingresado no corresponde a un ADMINISTRATIVO DE PLANTA");
						return false;
					}
				//Administrativo no planta	
				} else if(tipoPersonaSeleccionado.getId().equals(Tipos.TIPOS_TIPO_PERSONA_LABORATORIO_Admin_no_planta)){
					if(validarAdministrativoNoPlanta(investigadorInterno)) {
						return true;
					} else {
						mensajeError("RecursoHumanoLaboratorio:numDocumento","El documento ingresado no corresponde a un ADMINISTRATIVO QUE NO ES DE PLANTA");
						return false;
					}
				}	
			} else {
				mensajeError("RecursoHumanoLaboratorio:numDocumento","El documento ingresado no corresponde a un EMPLEADO ACTIVO DE LA UN");
				return false;
			}
		}
		
		//ESTUDIANTES
		if(getEsEstudiante()) {
			estudiante = validarEstudianteUN(idPersona);
			if (!esNulo(estudiante)) {
				persona = estudiante.convertirAPersona();
				return true;
			} else {
				mensajeError("RecursoHumanoLaboratorio:numDocumento","El documento ingresado no corresponde a un ESTUDIANTE ACTIVO");
				return false;
			}
		}
		
		if(getEsExterno()) {
			Boolean banderaExterno = true;
			if(!esNulo(persona) && !esNulo(investigadorInterno) && persona.getEsActivoSara().equals("S")) {
				if(validarEmpleadoUN(investigadorInterno)) {
					mensajeError("RecursoHumanoLaboratorio:numDocumento","El documento ingresado corresponde a un empleado activo de la UN. Considere seleccionar otro tipo de vinculación.");
					banderaExterno = false;
				}
			}
			
			if(!esNulo(idPersona)) {
				if(!esNulo(validarEstudianteUN(idPersona))) {
					mensajeError("RecursoHumanoLaboratorio:numDocumento","El documento ingresado corresponde a un estudiante activo de la UN. Considere seleccionar otro tipo de vinculación.");
					banderaExterno = false;
				}
			}
			
			if(esNulo(persona)) {
				persona = new Persona();
				persona.setId(idPersona);
				persona.setGenero("F");
			}
			telefonoPersona = null;
			return banderaExterno;
		}
		
		telefonoPersona = null;
		
		return false;
	}
	
	public void buscarPersona() {
		mostrarDatosPersona = validarBuscarPersona();			
	}
	
	public Long convertirNivelFormacionHermesAMetrored(String nvlFormacionHermes)
	{
		Long nvlFormacionMet = Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Otro;
		
		if(nvlFormacionHermes.equals("DO"))
			nvlFormacionMet = Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Doctorado;
		else if(nvlFormacionHermes.equals("PRBS"))
			nvlFormacionMet = Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Otro;
		else if(nvlFormacionHermes.equals("SNA"))
			nvlFormacionMet = Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Otro;
		else if(nvlFormacionHermes.equals("TC"))
			nvlFormacionMet = Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Tecnico;
		else if(nvlFormacionHermes.equals("EM"))
			nvlFormacionMet = Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Especializacion;
		else if(nvlFormacionHermes.equals("TL"))
			nvlFormacionMet = Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Tecnologo;
		else if(nvlFormacionHermes.equals("ES"))
			nvlFormacionMet = Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Especializacion;
		else if(nvlFormacionHermes.equals("MA"))
			nvlFormacionMet = Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Maestria;
		else if(nvlFormacionHermes.equals("UN"))
			nvlFormacionMet = Tipos.TIPO_LAB_METRO_NIVEL_FORMACION_Universitario;
		
		return nvlFormacionMet;
	}
	
	

	public void limpiarCoordinador() {
	}

	/**
	 * @return the tipoDocumentoItem
	 */
	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	/**
	 * @param tipoDocumentoItem
	 *            the tipoDocumentoItem to set
	 */
	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	/**
	 * @return the rolSelectItem
	 */
	public SelectItem[] getRolSelectItem() {
		return rolSelectItem;
	}

	/**
	 * @return the rolSeleccionado
	 */
	public Rol getRolSeleccionado() {
		return rolSeleccionado;
	}

	/**
	 * @param rolSeleccionado
	 *            the rolSeleccionado to set
	 */
	public void setRolSeleccionado(Rol rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}

	/**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * @param personaSeleccionada
	 *            the personaSeleccionada to set
	 */
	public void setPersonaSeleccionada(PersonaLaboratorio personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	/**
	 * @return the cargoPersona
	 */
	public String getCargoPersona() {
		return cargoPersona;
	}

	/**
	 * @param cargoPersona
	 *            the cargoPersona to set
	 */
	public void setCargoPersona(String cargoPersona) {
		this.cargoPersona = cargoPersona;
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension
	 *            the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	public ArchivoLaboratorioPersona getArchivoLaboratorioSeleccionado() {
		return archivoLaboratorioSeleccionado;
	}

	public void setArchivoLaboratorioSeleccionado(
			ArchivoLaboratorioPersona archivoLaboratorioSeleccionado) {
		this.archivoLaboratorioSeleccionado = archivoLaboratorioSeleccionado;
	}

	public LinkedList<ArchivoLaboratorioPersona> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(LinkedList<ArchivoLaboratorioPersona> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public Boolean getCambiosEnListaPersonas() {
		return cambiosEnListaPersonas;
	}

	public void setCambiosEnListaPersonas(Boolean cambiosEnListaPersonas) {
		this.cambiosEnListaPersonas = cambiosEnListaPersonas;
	}

	public Tipos getTipoVinculacionLaboratorio() {
		return tipoVinculacionLaboratorio;
	}

	public void setTipoVinculacionLaboratorio(Tipos tipoVinculacionLaboratorio) {
		this.tipoVinculacionLaboratorio = tipoVinculacionLaboratorio;
	}

	public Tipos getTipoArchivoSeleccionado() {
		return tipoArchivoSeleccionado;
	}

	public void setTipoArchivoSeleccionado(Tipos tipoArchivoSeleccionado) {
		this.tipoArchivoSeleccionado = tipoArchivoSeleccionado;
	}

	public SelectItem[] getTipoArchivoSelectItem() {
		return tipoArchivoSelectItem;
	}

	public void setTipoArchivoSelectItem(SelectItem[] tipoArchivoSelectItem) {
		this.tipoArchivoSelectItem = tipoArchivoSelectItem;
	}

	public SelectItem[] getSelectItemNivelFormacion() {
		return selectItemNivelFormacion;
	}

	public void setSelectItemNivelFormacion(SelectItem[] selectItemNivelFormacion) {
		this.selectItemNivelFormacion = selectItemNivelFormacion;
	}

	public String getNivelFormacionSeleccionada() {
		return nivelFormacionSeleccionada;
	}

	public void setNivelFormacionSeleccionada(String nivelFormacionSeleccionada) {
		this.nivelFormacionSeleccionada = nivelFormacionSeleccionada;
	}

	public PersonaLaboratorio getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public SelectItem[] getListaTiposPersona() {
		return listaTiposPersona;
	}

	public void setListaTiposPersona(SelectItem[] listaTiposPersona) {
		this.listaTiposPersona = listaTiposPersona;
	}

	public Tipos getTipoPersonaSeleccionado() {
		return tipoPersonaSeleccionado;
	}

	public void setTipoPersonaSeleccionado(Tipos tipoPersonaSeleccionado) {
		this.tipoPersonaSeleccionado = tipoPersonaSeleccionado;
	}

	public Boolean getMostrarDatosPersona() {
		return mostrarDatosPersona;
	}

	public void setMostrarDatosPersona(Boolean mostrarDatosPersona) {
		this.mostrarDatosPersona = mostrarDatosPersona;
	}

	public InvestigadorInterno getInvestigadorInterno() {
		return investigadorInterno;
	}

	public void setInvestigadorInterno(InvestigadorInterno investigadorInterno) {
		this.investigadorInterno = investigadorInterno;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public String getTelefonoPersona() {
		return telefonoPersona;
	}

	public void setTelefonoPersona(String telefonoPersona) {
		this.telefonoPersona = telefonoPersona;
	}

	public Boolean getCambiosEnPermisos() {
		return cambiosEnPermisos;
	}

	public void setCambiosEnPermisos(Boolean cambiosEnPermisos) {
		this.cambiosEnPermisos = cambiosEnPermisos;
	}

	public LaboratorioObservaciones getObservacionSel() {
		return observacionSel;
	}

	public void setObservacionSel(LaboratorioObservaciones observacionSel) {
		this.observacionSel = observacionSel;
	}

	public String getAceptaTratamientoDatos() {
		return aceptaTratamientoDatos;
	}

	public void setAceptaTratamientoDatos(String aceptaTratamientoDatos) {
		this.aceptaTratamientoDatos = aceptaTratamientoDatos;
	}

	public IdPersona getIdPersonaBuscar() {
		return idPersonaBuscar;
	}

	public void setIdPersonaBuscar(IdPersona idPersonaBuscar) {
		this.idPersonaBuscar = idPersonaBuscar;
	}
}
