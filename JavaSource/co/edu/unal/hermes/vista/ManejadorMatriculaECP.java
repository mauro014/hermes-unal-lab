package co.edu.unal.hermes.vista;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import co.edu.unal.hermes.modelo.AgendaConocimiento;
import co.edu.unal.hermes.modelo.ArchivosPreinscripcionECP;
import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoCivil;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Preinscripcion_ECP;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoFormacion;
import co.edu.unal.hermes.modelo.VEmpleadoSara;
import co.edu.unal.hermes.modelo.correo.Correo;

public class ManejadorMatriculaECP extends ManejadorBase {
	private Preinscripcion_ECP preins_ecp_objeto;

	private boolean habilita_doc;
	private boolean habilita_cont;
	private boolean habilita_guardar;

	private SelectItem[] tipoDocumentoItem;
	private SelectItem[] paisItem;

	protected List<SelectItem> departamentoItemList;
	protected List<SelectItem> ciudadItemList;
	protected List<SelectItem> formacionAcademicaItemList;
	protected List<SelectItem> otrosInteresesItemList;
	protected List<SelectItem> tipoVinculacionItemList;
	protected List<SelectItem> estadoCivilItemList;
	protected List<SelectItem> sectorEmpresaItemList;
	protected List<SelectItem> tipoEmpresaItemList;
	protected List<SelectItem> medioPublicidadItemList;
	protected List<SelectItem> ocupacionItemList;

	private List<String> grupoSanguineoOpciones;
	private List<String> rhOpciones;

	// Foraneas
	private TipoDocumento tipo_documento;
	private Pais pais_atributo;
	private Departamento departamento_atributo;
	private Ciudad ciudad_atributo;
	private TipoFormacion formacion_academica_atributo;
	private AgendaConocimiento otros_intereses_atributo;
	private DominioDetalle tipo_vinculacion_atributo;
	private EstadoCivil estado_civil_atributo; // *
	private DominioDetalle ocupacion_atributo; // *
	private DominioDetalle sector_atributo; // *
	private DominioDetalle naturaleza_atributo; // *
	private DominioDetalle medio_publicidad_atributo; // *

	// Foraneas

	// Relacion con persona-investigador
	private Persona investigador;
	private Proyecto curso;

	private String tipo_doc;
	private String documento;
	private String primer_nombre;
	private String segundo_nombre;
	private String primer_apellido;
	private String segundo_apellido;
	private String correo_electronico;
	private String tel_fijo;
	private String direccion;
	private String pais;
	private String departamento;
	private String ciudad;
	private String formacion_academica;
	private String entidad;
	private String profesion;
	private String empresa;
	private String cargo;
	private String telefono_empresa;
	private String correo_empresa;
	private String grupo_sanguineo;
	private String rh;
	private String eps;
	private String otros_intereses;
	private String tipo_vinculacion;

	private String persona_contacto;
	private String tel_contacto;
	private String direccion_empresa;

	private String tel_movil; 
	private String medio_publicidad; 
	private Date fecNacimiento; 
	private String ocupacion;
	private String estado_civil;
	private String sector_empresa;
	private String tipo_empresa;
	private String genero;

	private String estudianteUN;

	private Date feccre;
	private long id_oferta;
	private String estado;

	private String ins_act;

	private Persona persona;
	private Estudiante estudiante;
	private VEmpleadoSara empleado;
	private Investigador docente;

	private boolean esPersona;
	private boolean esEmpleado;
	private boolean esEstudiante;
	private boolean esDocente;
	private boolean esContratista;
	private boolean esExterno;
	private boolean esEgresado;

	private String categoriaInvestigador;

	private List<ArchivosPreinscripcionECP> listaArchivos;
	private List<ArchivosPreinscripcionECP> listaArchivosNuevos;
	private List<ArchivosPreinscripcionECP> listaArchivosBorrados;

	private ArchivosPreinscripcionECP archivoPre;

	private List generoOpciones;

	// SEDE_FECHA
	private String sedeFechaSeleccionada;
	protected List<SelectItem> sedeFechaItemList;

	// OTRO_EMPRESA
	private String nitEmpresa;
	private String nombresRepLegal;
	private String apellidosRepLegal;
	private String numDocRepLegal;
	private String tipoDocRepLegal;

	// TERMINOS
	private String opcionesTerminos;
	private boolean mostrarBotonGuardar;
	private Hashtable<String, String> popayan = new Hashtable<String, String>();
	private Hashtable<String, String> palmira = new Hashtable<String, String>();
	private Hashtable<String, String> orinoquia = new Hashtable<String, String>();
	private Hashtable<String, String> santaMarta = new Hashtable<String, String>();
	private Hashtable<String, String> monteria = new Hashtable<String, String>();
	private Hashtable<String, String> medellin = new Hashtable<String, String>();
	private Hashtable<String, String> manizales = new Hashtable<String, String>();
	private Hashtable<String, String> ibague = new Hashtable<String, String>();
	private Hashtable<String, String> villavicencio = new Hashtable<String, String>();
	private Hashtable<String, String> bucaramanga = new Hashtable<String, String>();
	private Hashtable<String, String> leticia = new Hashtable<String, String>();
	private Hashtable<String, String> bogotaUno = new Hashtable<String, String>();
	private Hashtable<String, String> bogotaDos = new Hashtable<String, String>();
	private Hashtable<String, String> sanAndres = new Hashtable<String, String>();
	private Hashtable<String, String> valledupar = new Hashtable<String, String>();

	private static Logger logger = Logger
			.getLogger(ManejadorPreinscripcionECP.class.getName());
	int numeroTab;

	public ManejadorMatriculaECP() {

		cargarListasParticipantes();

		mostrarBotonGuardar = false;

		investigador = new Investigador();
		sesion.removeAttribute("ManejadorPreinscripcionECP");

		listaArchivos = new ArrayList<ArchivosPreinscripcionECP>();
		listaArchivosNuevos = new ArrayList<ArchivosPreinscripcionECP>();
		listaArchivosBorrados = new ArrayList<ArchivosPreinscripcionECP>();

		cargarValoresIniciales();

		if (this.request.getParameter("idCurso") != null
				&& !this.request.getParameter("idCurso").equals("")) {
			id_oferta = Long.parseLong(request.getParameter("idCurso"));
			String cons_oferta = "select pp from Proyecto pp where pp.id = "
					+ id_oferta;
			List lista_oferta = servicioGeneral.obtenerObjetos(cons_oferta);
			if (lista_oferta.size() > 0)
				curso = (Proyecto) lista_oferta.get(0);
			else
				curso = null;
		} else
			id_oferta = -1;

		tipo_doc = "C";

	}

	public void nuevaPreinscripcion() {
		cargarValoresIniciales();
		sesion.removeAttribute("ManejadorPreinscripcionECP");
	}

	public void cargarSedeFechaOpciones()// SEDE_FECHA
	{
		sedeFechaItemList = new ArrayList<SelectItem>();

		sedeFechaItemList.add(new SelectItem(
				"Bogotá (1) | Viernes, 06 de septiembre de 2013"));
		sedeFechaItemList.add(new SelectItem(
				"Bogotá (2) | Viernes, 13 de septiembre de 2013"));
		sedeFechaItemList.add(new SelectItem(
				"Bucaramanga | Viernes, 20 de septiembre de 2013"));
		sedeFechaItemList.add(new SelectItem(
				"Ibagué | Viernes, 04 de octubre de 2013"));
		sedeFechaItemList.add(new SelectItem(
				"Villavicencio | Viernes, 27 de septiembre de 2013"));
		sedeFechaItemList.add(new SelectItem(
				"Manizales | Viernes, 06 de septiembre de 2013"));
		sedeFechaItemList.add(new SelectItem(
				"Palmira | Viernes, 30 de agosto de 2013"));
		sedeFechaItemList.add(new SelectItem(
				"Popayán | Viernes, 20 de septiembre de 2013"));
		sedeFechaItemList.add(new SelectItem(
				"Medellín | Viernes, 06 de septiembre de 2013"));
		sedeFechaItemList.add(new SelectItem(
				"Santa Marta | Viernes, 13 de septiembre de 2013"));
		sedeFechaItemList.add(new SelectItem(
				"Montería | Viernes, 20 de septiembre de 2013"));
		sedeFechaItemList.add(new SelectItem(
				"San Andrés | Viernes, 06 de septiembre de 2013"));
		sedeFechaItemList.add(new SelectItem(
				"Arauca | Viernes, 06 de septiembre de 2013"));
		sedeFechaItemList.add(new SelectItem(
				"Leticia | Viernes, 13 de septiembre de 2013"));

	}

	public void cargarValoresIniciales() {

		cargarTiposDocumento();
		cargarPaises();
		cargarFormacionAcademica();
		cargarOtrosIntereses();
		cargarTipoVinculacion();
		cargarGrupoSanguineoOpciones();
		cargarRh();
		cargarEstadoCivilOpciones();
		cargarOcupacionOpciones();
		cargarMediosPublicidad();
		cargarSectoresEmpresa();
		cargarTiposEmpresa();
		cargarGeneroOpciones();
		cargarSedeFechaOpciones();// SEDE_FECHA
		cargarCiudadCompletas();

		ins_act = "N";
		habilita_cont = false;
		habilita_doc = false;
		habilita_guardar = false;

		persona = new Persona();
		estudiante = null;
		empleado = null;

		preins_ecp_objeto = null;

		documento = "";
		primer_nombre = "";
		segundo_nombre = "";
		primer_apellido = "";
		segundo_apellido = "";
		correo_electronico = "";
		tel_movil = "";
		tel_fijo = "";
		direccion = "";
		pais_atributo = null;
		pais = "";
		departamento_atributo = null;
		departamento = "CO11";
		ciudad_atributo = null;
		ciudad = "";
		formacion_academica_atributo = null;
		formacion_academica = "";
		entidad = "";
		profesion = "";
		empresa = "";
		cargo = "";
		telefono_empresa = "";
		correo_empresa = "";
		grupo_sanguineo = "";
		rh = "";
		eps = "";
		otros_intereses_atributo = null;
		otros_intereses = "";
		tipo_vinculacion_atributo = null;
		tipo_vinculacion = "";
		fecNacimiento = null;
		medio_publicidad = "";
		medio_publicidad_atributo = null;
		sector_empresa = "";
		sector_atributo = null;
		ocupacion = "";
		ocupacion_atributo = null;
		tipo_empresa = "";
		naturaleza_atributo = null;

		persona_contacto = "";
		tel_contacto = "";
		direccion_empresa = "00";

		estudianteUN = "N";

		numeroTab = 0;

		ciudad = "00";
		departamento = "00";
		pais = "00";

		// SEDE_FECHA
		sedeFechaSeleccionada = "";

		// OTRO_EMPRESA
		nitEmpresa = "";
		nombresRepLegal = "";
		apellidosRepLegal = "";
		numDocRepLegal = "";
		tipoDocRepLegal = "C";

		preins_ecp_objeto = new Preinscripcion_ECP();

	}

	public void validarTipoVinculacion() {
		esPersona = esPersona();
		esEstudiante = esEstudiante();
		esEmpleado = esEmpleado();
		esDocente = esDocente();
		esContratista = esContratista();
		esEgresado = esEgresado();

		if (esEstudiante || esEmpleado || esDocente || esContratista
				|| esEgresado)
			esExterno = false;
		else
			esExterno = true;

	}

	public void buscarPreinscripcion() {

		listaArchivos = new ArrayList<ArchivosPreinscripcionECP>();
		listaArchivosNuevos = new ArrayList<ArchivosPreinscripcionECP>();
		listaArchivosBorrados = new ArrayList<ArchivosPreinscripcionECP>();

		if (curso.getEstadoProyecto().getId().equals("PB") && comprobarParticipante())
		{
			preins_ecp_objeto = null;

			validarTipoVinculacion();

			if (!documento.equals("")) {
				habilita_cont = true;
				habilita_doc = true;

				String cons_preins = "Select pp from Preinscripcion_ECP pp where pp.investigador.id.documento like '%"
						+ this.documento
						+ "%' "
						+ "and pp.investigador.id.tipoDocumento like '%"
						+ this.tipo_doc
						+ "%' "
						+ "and pp.curso.id = "
						+ id_oferta;

				List lista_preins = servicioGeneral.obtenerObjetos(cons_preins);

				if (lista_preins.size() != 0) // LA PREINSCRIPCION YA EXISTE
												// [ACTUALIZAR]
				{
					preins_ecp_objeto = (Preinscripcion_ECP) lista_preins
							.get(0);

					List listaArchivosExistentes = servicioGeneral
							.obtenerObjetos("select e from ArchivosPreinscripcionECP e where e.preinscripcionECP.id_pre = "
									+ preins_ecp_objeto.getId_pre());
					if (listaArchivosExistentes.size() > 0
							&& listaArchivosExistentes != null) {
						for (int i = 0; i < listaArchivosExistentes.size(); i++) {
							ArchivosPreinscripcionECP ar = (ArchivosPreinscripcionECP) listaArchivosExistentes
									.get(i);
							ar.setEsNuevo(false);
							listaArchivos.add(ar);
						}

					}

					if (esPersona)
						datosPersona();

					if (preins_ecp_objeto.getPais() != null) {
						cargarPaises();
						setPais_atributo(preins_ecp_objeto.getPais());
						setPais(preins_ecp_objeto.getPais().getId());
					} else {
						setPais_atributo(null);
						setPais(null);
					}
					if (preins_ecp_objeto.getDepartamento() != null) {
						cargarDepto();
						setDepartamento_atributo(preins_ecp_objeto
								.getDepartamento());
						setDepartamento(preins_ecp_objeto.getDepartamento()
								.getId());
					} else {
						setDepartamento_atributo(null);
						setDepartamento(null);
					}

					if (preins_ecp_objeto.getCiudad() != null) {
						cargarCiudad();
						setCiudad_atributo(preins_ecp_objeto.getCiudad());
						setCiudad(preins_ecp_objeto.getCiudad().getId());
					} else {
						setCiudad_atributo(null);
						setCiudad(null);
					}
					setFormacion_academica_atributo(preins_ecp_objeto
							.getFormacion_academica());
					setEntidad(preins_ecp_objeto.getEntidad());
					setProfesion(preins_ecp_objeto.getProfesion());
					setEmpresa(preins_ecp_objeto.getEmpresa());
					setCargo(preins_ecp_objeto.getCargo());
					setTelefono_empresa(preins_ecp_objeto.getTelefono_empresa());
					setCorreo_empresa(preins_ecp_objeto.getCorreo_empresa());
					setGrupo_sanguineo(preins_ecp_objeto.getGrupo_sanguineo());
					setRh(preins_ecp_objeto.getRh());
					setEps(preins_ecp_objeto.getEps());

					setTel_movil(preins_ecp_objeto.getTel_movil());
					setOcupacion(preins_ecp_objeto.getOcupacion());
					setMedio_publicidad(preins_ecp_objeto.getMedio_publicidad());
					setSector_empresa(preins_ecp_objeto.getSector_empresa());
					setTipo_empresa(preins_ecp_objeto.getTipo_empresa());

					setDireccion_empresa(preins_ecp_objeto
							.getDireccion_empresa());
					setPersona_contacto(preins_ecp_objeto.getPersona_contacto());
					setTel_contacto(preins_ecp_objeto.getTel_contacto());

					if (preins_ecp_objeto.getOtros_intereses() != null) {
						setOtros_intereses_atributo(preins_ecp_objeto
								.getOtros_intereses());
						setOtros_intereses(preins_ecp_objeto
								.getOtros_intereses().getId());
					} else {
						setOtros_intereses_atributo(null);
						setOtros_intereses("N");
					}

					setTipo_vinculacion(preins_ecp_objeto.getTipo_vinculacion());

					// SEDE_FECHA
					setSedeFechaSeleccionada(preins_ecp_objeto.getSedeFecha());

					// OTRO_EMPRESA
					setNitEmpresa(preins_ecp_objeto.getNitEmpresa());
					setNumDocRepLegal(preins_ecp_objeto.getNumDocRepLegal());
					setTipoDocRepLegal(preins_ecp_objeto.getTipoDocRepLegal());
					setNombresRepLegal(preins_ecp_objeto.getNombresRepLegal());
					setApellidosRepLegal(preins_ecp_objeto
							.getApellidosRepLegal());

					ins_act = "A";
					FacesContext.getCurrentInstance().addMessage(
							"growl",
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Registro existente",
									"Por favor actualice sus datos"));
				} else { // LA PREINSCRIPCION NO EXISTE [INSERTAR]
					ins_act = "I";
					preins_ecp_objeto = null;
					if (esPersona)
						datosPersona();
					else if (esEstudiante)
						datosEstudiante();
					else if (esEmpleado)
						datosEmpleado();


					FacesContext
							.getCurrentInstance()
							.addMessage(
									"growl",
									new FacesMessage(
											FacesMessage.SEVERITY_INFO,
											"Nuevo Registro",
											"Por favor ingrese los siguientes datos para realizar la preinscripcion"));

				}
			} else {
				ins_act = "N";
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"growl",
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Ingrese su numero de documento para poder continuar con la preinscripcion",
										""));
			}
		} else

			FacesContext.getCurrentInstance().addMessage(
					"growl",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Registro no autorizado", ""));
	}

	public boolean comprobarParticipante() {
		boolean ret = true;
		if (popayan.containsKey(documento)) {
			sedeFechaSeleccionada = "Popayán | Viernes, 20 de septiembre de 2013";
			ret = true;
		} else {
			if (palmira.containsKey(documento)) {
				sedeFechaSeleccionada = "Palmira | Viernes, 30 de agosto de 2013";
				ret = true;
			} else {
				if (orinoquia.containsKey(documento)) {
					sedeFechaSeleccionada = "Arauca | Viernes, 06 de septiembre de 2013";
					ret = true;
				} else {
					if (santaMarta.containsKey(documento)) {
						sedeFechaSeleccionada = "Santa Marta | Viernes, 13 de septiembre de 2013";
						ret = true;
					} else {
						if (monteria.containsKey(documento)) {
							sedeFechaSeleccionada = "Montería | Viernes, 20 de septiembre de 2013";
							ret = true;
						} else {
							if (medellin.containsKey(documento)) {
								sedeFechaSeleccionada = "Medellín | Viernes, 06 de septiembre de 2013";
								ret = true;
							} else {
								if (manizales.containsKey(documento)) {
									sedeFechaSeleccionada = "Manizales | Viernes, 06 de septiembre de 2013";
									ret = true;
								} else {
									if (ibague.containsKey(documento)) {
										sedeFechaSeleccionada = "Ibagué | Viernes, 04 de octubre de 2013";
										ret = true;
									} else {
										if (villavicencio
												.containsKey(documento)) {
											sedeFechaSeleccionada = "Villavicencio | Viernes, 27 de septiembre de 2013";
											ret = true;
										} else {
											if (bucaramanga
													.containsKey(documento)) {
												sedeFechaSeleccionada = "Bucaramanga | Viernes, 20 de septiembre de 2013";
												ret = true;
											} else {
												if (leticia
														.containsKey(documento)) {
													sedeFechaSeleccionada = "Leticia | Viernes, 13 de septiembre de 2013";
													ret = true;
												} else {
													if (bogotaUno
															.containsKey(documento)) {
														sedeFechaSeleccionada = "Bogotá (1) | Viernes, 06 de septiembre de 2013";
														ret = true;
													} else {
														if (bogotaDos
																.containsKey(documento)) {
															sedeFechaSeleccionada = "Bogotá (2) | Viernes, 13 de septiembre de 2013";
															ret = true;
														} else {
															if (sanAndres
																	.containsKey(documento)) {
																sedeFechaSeleccionada = "San Andrés | Viernes, 06 de septiembre de 2013";
																ret = true;
															} else {
																if (valledupar
																		.containsKey(documento)) {
																	sedeFechaSeleccionada = "Valledupar | Viernes, 27 de septiembre de 2013";
																	ret = true;
																} else {
																	ret = false;
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}

	public boolean esPersona() {
		String cons_persona;
		if (!documento.equals("")) {
			cons_persona = "Select pp from Persona pp where pp.id.documento like '"
					+ this.documento
					+ "' and pp.id.tipoDocumento like '"
					+ this.tipo_doc + "'";
			List lista_persona = servicioGeneral.obtenerObjetos(cons_persona);
			if (lista_persona.size() != 0) {
				persona = (Persona) lista_persona.get(0);
				System.out.println("   -> ES PERSONA  <-  "
						+ persona.getId().getDocumento() + " - "
						+ persona.getNombre1() + " " + persona.getNombre2()
						+ " " + persona.getApellido1() + " "
						+ persona.getApellido2());
				return true;
			} else
				return false;
		} else
			return false;
	}

	public boolean esEstudiante() {
		String cons_estudiante;
		if (!documento.equals("")) {
			cons_estudiante = "Select pp from Estudiante pp where pp.id.documento like '"
					+ this.documento
					+ "' and pp.id.tipoDocumento like '"
					+ this.tipo_doc + "'" + "and pp.interno in ('S')"; // Estudiantes
																		// activos

			List lista_estudiante = servicioGeneral
					.obtenerObjetos(cons_estudiante);
			if (lista_estudiante.size() != 0) {
				estudiante = (Estudiante) lista_estudiante.get(0);
				System.out.println("   -> ES ESTUDIANTE  <-  "
						+ estudiante.getId().getDocumento());
				return true;
			} else
				return false;
		} else
			return false;
	}

	public boolean esDocente() {
		String cons_docente;
		if (!documento.equals("")) {
			cons_docente = "Select pp from Investigador pp where pp.id.documento like '"
					+ this.documento
					+ "'"
					+ "and pp.id.tipoDocumento like '"
					+ this.tipo_doc + "' " + "and pp.interno in ('S') " + // Docente
																			// activo
					"and pp.categoriaInvestigador = 2"; // Categoria del
														// investigador = 2.
														// Docente

			List lista_docente = servicioGeneral.obtenerObjetos(cons_docente);

			if (lista_docente.size() != 0) {
				docente = (Investigador) lista_docente.get(0);
				System.out.println("   -> ES DOCENTE  <-  "
						+ docente.getId().getDocumento());
				return true;
			} else
				return false;
		} else {
			return false;
		}
	}

	public boolean esEmpleado() {
		String cons_empleado;
		if (!documento.equals("")) {
			cons_empleado = "Select pp from VEmpleadoSara pp where pp.id.documento like '"
					+ this.documento
					+ "'"
					+ "and pp.id.tipoDocumento like '"
					+ this.tipo_doc
					+ "' "
					+ "and pp.vinculacion in (1,10,12,14,29) " + // Tipos de
																	// vinculacion
																	// en SARA
																	// asociados
																	// a
																	// empleados
					"and pp.estado in ('A')"; // Activos

			List lista_empleado = servicioGeneral.obtenerObjetos(cons_empleado);

			if (lista_empleado.size() != 0) {
				empleado = (VEmpleadoSara) lista_empleado.get(0);
				System.out.println("   -> ES EMPLEADO  <-  "
						+ empleado.getId().getDocumento());
				return true;
			} else
				return false;
		} else {
			return false;
		}
	}

	public boolean esContratista() {
		return false;
	}

	public boolean esEgresado() {
		return false;
	}

	public void datosPersona() {
		if (persona != null) {
			setPrimer_nombre(persona.getNombre1());
			setSegundo_nombre(persona.getNombre2());
			setPrimer_apellido(persona.getApellido1());
			setSegundo_apellido(persona.getApellido2());
			setCorreo_electronico(persona.getEmail());
			setTel_fijo(persona.getTelefono());
			setFecNacimiento(persona.getFechaNacimiento());
			setTel_movil(null);
			setDireccion(persona.getDireccion());
			setGenero(persona.getGenero());
		}
	}

	public void datosEstudiante() {
		if (estudiante != null) {
			setPrimer_nombre(estudiante.getNombre1());
			setSegundo_nombre(estudiante.getNombre2());
			setPrimer_apellido(estudiante.getApellido1());
			setSegundo_apellido(estudiante.getApellido2());
			setCorreo_electronico(estudiante.getEmail());
			setTel_fijo(estudiante.getTelefono());
			setFecNacimiento(estudiante.getFechaNacimiento());
			setTel_movil(null);
			setDireccion(estudiante.getDireccion());
			setEstudianteUN("S");
		}
	}

	public void datosDocente() {
		if (docente != null) {
			setPrimer_nombre(docente.getNombre1());
			setSegundo_nombre(docente.getNombre2());
			setPrimer_apellido(docente.getApellido1());
			setSegundo_apellido(docente.getApellido2());
			setCorreo_electronico(docente.getEmail());
			setTel_fijo(docente.getTelefono());
			setFecNacimiento(docente.getFechaNacimiento());
			setTel_movil(null);
			setDireccion(docente.getDireccion());
			setGenero(docente.getGenero());
		}
	}

	public void datosEmpleado() {

		if (empleado != null) {
			String[] listaNombres = empleado.getNombres().split(" ");
			String nombre1 = listaNombres[0];
			String nombre2 = "";

			for (int i = 1; i < listaNombres.length; i++) {
				nombre2 += listaNombres[i] + " ";
			}

			setPrimer_nombre(nombre1);
			setSegundo_nombre(nombre2);
			setPrimer_apellido(empleado.getApellido());
			setSegundo_apellido(empleado.getApellidos());
			setCorreo_electronico(empleado.getEmail());

			setTel_fijo(null);
			setTel_movil(null);
			setDireccion(empleado.getDireccion());
		}
	}

	public void construir_objetosOriginal() {
		String cons_pais, cons_depto, cons_ciud, cons_formacion, cons_tddoc, cons_otros_intereses, cons_tpvinculacion, cons_oferta, cons_estado_civil, cons_ocupacion, cons_sector, cons_naturaleza, cons_medio;

		tipo_documento = new TipoDocumento();
		pais_atributo = new Pais();
		departamento_atributo = new Departamento();
		ciudad_atributo = new Ciudad();
		formacion_academica_atributo = new TipoFormacion();
		estado_civil_atributo = new EstadoCivil();
		ocupacion_atributo = new DominioDetalle();
		sector_atributo = new DominioDetalle();
		naturaleza_atributo = new DominioDetalle();
		medio_publicidad_atributo = new DominioDetalle();
		tipo_vinculacion_atributo = new DominioDetalle();				

		cons_tddoc = "select pp from TipoDocumento pp where  pp.id like '%"
				+ this.tipo_doc + "%'";
		List lista_tpdoc = servicioGeneral.obtenerObjetos(cons_tddoc);
		tipo_documento = (TipoDocumento) lista_tpdoc.get(0);

		cons_pais = "select pp from Pais pp where  pp.id like '%" + this.pais
				+ "%'";
		List lista_pais = servicioGeneral.obtenerObjetos(cons_pais);
		pais_atributo = (Pais) lista_pais.get(0);

		if (pais.equals("CO")) {
			cons_depto = "select dd from Departamento dd where  dd.id  like '%"
					+ this.departamento + "%'";
			List lista_depto = servicioGeneral.obtenerObjetos(cons_depto);
			departamento_atributo = (Departamento) lista_depto.get(0);

			cons_ciud = "select cc from Ciudad cc where  cc.id like '%"
					+ this.ciudad + "%'";
			List lista_ciud = servicioGeneral.obtenerObjetos(cons_ciud);
			ciudad_atributo = (Ciudad) lista_ciud.get(0);
		}

		cons_formacion = "select pp from TipoFormacion pp where  pp.id like '%"
				+ this.formacion_academica + "%'";
		List lista_tpformacion = servicioGeneral.obtenerObjetos(cons_formacion);
		formacion_academica_atributo = (TipoFormacion) lista_tpformacion.get(0);

		if (!otros_intereses.equals("")) {
			if (!otros_intereses.equals("N")) {
				cons_otros_intereses = "select pp from AgendaConocimiento pp where  pp.id like "
						+ this.otros_intereses;
				List lista_otrosIntereses = servicioGeneral
						.obtenerObjetos(cons_otros_intereses);
				otros_intereses_atributo = (AgendaConocimiento) lista_otrosIntereses
						.get(0);
			} else
				otros_intereses_atributo = null;
		}

		cons_ocupacion = "select pp from DominioDetalle pp where  pp.identificador.tipo like '%"
				+ this.ocupacion + "%'";
		List lista_ocupacion = servicioGeneral.obtenerObjetos(cons_ocupacion);
		ocupacion_atributo = (DominioDetalle) lista_ocupacion.get(0);

		cons_sector = "select pp from DominioDetalle pp where  pp.identificador.tipo like '%"
				+ this.sector_empresa + "%'";
		List lista_sector = servicioGeneral.obtenerObjetos(cons_sector);
		sector_atributo = (DominioDetalle) lista_sector.get(0);

		cons_naturaleza = "select pp from DominioDetalle pp where  pp.identificador.tipo like '%"
				+ this.tipo_empresa + "%'";
		List lista_naturaleza = servicioGeneral.obtenerObjetos(cons_naturaleza);
		naturaleza_atributo = (DominioDetalle) lista_naturaleza.get(0);

		if (!medio_publicidad.equals("")) {
			cons_medio = "select pp from DominioDetalle pp where  pp.identificador.tipo like '%"
					+ this.medio_publicidad + "%'";
			List lista_medio = servicioGeneral.obtenerObjetos(cons_medio);
			medio_publicidad_atributo = (DominioDetalle) lista_medio.get(0);
		}

		cons_tpvinculacion = "select pp from DominioDetalle pp where  pp.identificador.tipo like '%"
				+ this.tipo_vinculacion + "%'";
		List lista_tpVinculacion = servicioGeneral
				.obtenerObjetos(cons_tpvinculacion);
		tipo_vinculacion_atributo = (DominioDetalle) lista_tpVinculacion.get(0);

		cons_oferta = "select pp from Proyecto pp where pp.id = " + id_oferta;
		List lista_oferta = servicioGeneral.obtenerObjetos(cons_oferta);
		if (lista_oferta.size() > 0)
			curso = (Proyecto) lista_oferta.get(0);
		else
			curso = null;

	}

	public void construir_objetos() {
		String cons_pais, cons_depto, cons_ciud, cons_formacion, cons_tddoc, cons_otros_intereses, cons_tpvinculacion, cons_oferta, cons_ocupacion, cons_medio;

		tipo_documento = new TipoDocumento();
		pais_atributo = new Pais();
		departamento_atributo = new Departamento();
		ciudad_atributo = new Ciudad();
		formacion_academica_atributo = new TipoFormacion();
		medio_publicidad_atributo = new DominioDetalle();
		tipo_vinculacion_atributo = new DominioDetalle();

		cons_tddoc = "select pp from TipoDocumento pp where  pp.id like '%"
				+ this.tipo_doc + "%'";
		List lista_tpdoc = servicioGeneral.obtenerObjetos(cons_tddoc);
		tipo_documento = (TipoDocumento) lista_tpdoc.get(0);
		
		pais = "CO";// solo para colombia
		this.departamento = "CO11";
		
		cons_pais = "select pp from Pais pp where  pp.id like '%" + this.pais
				+ "%'";
		List lista_pais = servicioGeneral.obtenerObjetos(cons_pais);
		pais_atributo = (Pais) lista_pais.get(0);

		

		if (pais.equals("CO")) {
			cons_depto = "select dd from Departamento dd where  dd.id  like '%"
					+ this.departamento + "%'";
			List lista_depto = servicioGeneral.obtenerObjetos(cons_depto);
			departamento_atributo = (Departamento) lista_depto.get(0);

			cons_ciud = "select cc from Ciudad cc where  cc.id like '%"
					+ this.ciudad + "%'";
			List lista_ciud = servicioGeneral.obtenerObjetos(cons_ciud);
			ciudad_atributo = (Ciudad) lista_ciud.get(0);
		}

		cons_formacion = "select pp from TipoFormacion pp where  pp.id like '%"
				+ this.formacion_academica + "%'";
		List lista_tpformacion = servicioGeneral.obtenerObjetos(cons_formacion);
		formacion_academica_atributo = (TipoFormacion) lista_tpformacion.get(0);

		if (!otros_intereses.equals("")) {
			if (!otros_intereses.equals("N")) {
				cons_otros_intereses = "select pp from AgendaConocimiento pp where  pp.id like "
						+ this.otros_intereses;
				List lista_otrosIntereses = servicioGeneral
						.obtenerObjetos(cons_otros_intereses);
				otros_intereses_atributo = (AgendaConocimiento) lista_otrosIntereses
						.get(0);
			} else
				otros_intereses_atributo = null;
		}

		if (!medio_publicidad.equals("")) {
			cons_medio = "select pp from DominioDetalle pp where  pp.identificador.tipo like '%"
					+ this.medio_publicidad + "%'";
			List lista_medio = servicioGeneral.obtenerObjetos(cons_medio);
			medio_publicidad_atributo = (DominioDetalle) lista_medio.get(0);
		}

		cons_tpvinculacion = "select pp from DominioDetalle pp where  pp.identificador.tipo like '%"
				+ this.tipo_vinculacion + "%'";
		List lista_tpVinculacion = servicioGeneral
				.obtenerObjetos(cons_tpvinculacion);
		tipo_vinculacion_atributo = (DominioDetalle) lista_tpVinculacion.get(0);

		cons_oferta = "select pp from Proyecto pp where pp.id = " + id_oferta;
		List lista_oferta = servicioGeneral.obtenerObjetos(cons_oferta);
		if (lista_oferta.size() > 0)
			curso = (Proyecto) lista_oferta.get(0);
		else
			curso = null;

	}

	public void construir_objetos_matricula() {
		String cons_pais, cons_depto, cons_ciud, cons_formacion, cons_tddoc, cons_otros_intereses, cons_tpvinculacion, cons_oferta, cons_estado_civil, cons_ocupacion, cons_sector, cons_naturaleza, cons_medio;

		tipo_documento = new TipoDocumento();
		pais_atributo = new Pais();
		departamento_atributo = new Departamento();
		ciudad_atributo = new Ciudad();

		cons_tddoc = "select pp from TipoDocumento pp where  pp.id like '%"
				+ this.tipo_doc + "%'";
		List lista_tpdoc = servicioGeneral.obtenerObjetos(cons_tddoc);
		tipo_documento = (TipoDocumento) lista_tpdoc.get(0);

		cons_pais = "select pp from Pais pp where  pp.id like '%" + this.pais
				+ "%'";
		List lista_pais = servicioGeneral.obtenerObjetos(cons_pais);
		pais_atributo = (Pais) lista_pais.get(0);

		if (pais.equals("CO")) {
			cons_depto = "select dd from Departamento dd where  dd.id  like '%"
					+ this.departamento + "%'";
			List lista_depto = servicioGeneral.obtenerObjetos(cons_depto);
			departamento_atributo = (Departamento) lista_depto.get(0);

			cons_ciud = "select cc from Ciudad cc where  cc.id like '%"
					+ this.ciudad + "%'";
			List lista_ciud = servicioGeneral.obtenerObjetos(cons_ciud);
			ciudad_atributo = (Ciudad) lista_ciud.get(0);
		}

		cons_oferta = "select pp from Proyecto pp where pp.id = " + id_oferta;
		List lista_oferta = servicioGeneral.obtenerObjetos(cons_oferta);
		if (lista_oferta.size() > 0)
			curso = (Proyecto) lista_oferta.get(0);
		else
			curso = null;

	}

	private boolean guardarPersonaInvestigadorBasico() {
		// Persona-Investigador Vs OfertaECP
		try {

			Investigador inv = null;
			boolean errorInsertaPersona = false;

			String cons_persona_invest = "select pp from Persona pp where pp.id.documento like '"
					+ documento
					+ "' and pp.id.tipoDocumento like '"
					+ tipo_doc
					+ "'";
			List lista_persona = servicioGeneral
					.obtenerObjetos(cons_persona_invest);
			if (lista_persona.size() > 0) {
				investigador = (Persona) lista_persona.get(0);
				investigador.setTelefono(tel_fijo);
				investigador.setGenero("S");
				investigador.setEmail(correo_electronico);
				investigador.setDireccion(direccion);

				boolean tiRol = false;
				List rolPer = servicioPersona.obtenerRols(investigador.getId());
				for (int l = 0; l < rolPer.size(); l++) {
					Rol r = (Rol) rolPer.get(l);
					if (r.getId().equals("FO")) {
						tiRol = true;
					}
				}
				if (!tiRol) {
					Rol rolExt = new Rol();
					rolExt.setId("FO");
					investigador.adicionarRol(rolExt);
				}

				servicioGeneral.guardarObjeto(investigador);// 1
				preins_ecp_objeto.setInvestigador(investigador);
			} else {

				System.out.println("Insertando Nuevo Investigador");

				if (persona != null)
					persona = new Persona();

				IdPersona idP = new IdPersona();
				idP.setDocumento(documento);
				idP.setTipoDocumento(tipo_doc);
				persona.setId(idP);
				categoriaInvestigador = CategoriaInvestigador.IDEXTERNO;// ========================================================>Revisar<===================================================TODO

				Persona personaAux = servicioPersona
						.obtenerPersonaRoles(persona.getId());
				persona.setCiudadDomicilio(ciudad_atributo);

				inv = (Investigador) servicioGeneral.obtenerObjeto(
						new Investigador(), persona.getId());

				if (personaAux == null) {

					persona.setNombre1(primer_nombre);
					persona.setNombre2(segundo_nombre);
					persona.setApellido1(primer_apellido);
					persona.setApellido2(segundo_apellido);
					persona.setEmail(correo_electronico);
					persona.setTelefono(tel_fijo);
					persona.setDireccion(direccion);
					persona.setGenero("S");

					Rol rol = new Rol();
					rol.setId("FO");
					persona.adicionarRol(rol);

					System.out.println("Insertando Nueva persona");

					if (esEstudiante) {

						if (estudiante != null) {
							Persona per = servicioPersona
									.obtenerPersona(estudiante.getId());
							if (per == null) {

								if (servicioPersona
										.insertarNuevaPersonaDatosBasicos(persona))// 2
								{
									servicioPersona.agregarRolPersona(
											persona.getId(), rol);
									preins_ecp_objeto.setInvestigador(persona);
								} else
									errorInsertaPersona = true;

							} else {
								servicioGeneral.guardarObjeto(persona);
								servicioPersona.agregarRolPersona(
										persona.getId(), rol);
							}
						}

					} else {
						if (esEmpleado) {

							Persona per = servicioPersona
									.obtenerPersona(empleado.getId());

							if (per == null) {

								if (servicioPersona
										.insertarNuevaPersonaDatosBasicos(persona))// 3
								{
									servicioPersona.agregarRolPersona(
											persona.getId(), rol);
									preins_ecp_objeto.setInvestigador(persona);
								} else
									errorInsertaPersona = true;

							} else {
								servicioGeneral.guardarObjeto(persona);// 4
								preins_ecp_objeto.setInvestigador(persona);
								servicioPersona.agregarRolPersona(
										persona.getId(), rol);
							}

						} else {
							if (esPersona) {

								InvestigadorInterno iiAux = (InvestigadorInterno) servicioGeneral
										.obtenerObjeto(
												new InvestigadorInterno(),
												persona.getId());
								CategoriaInvestigador ci = (CategoriaInvestigador) servicioGeneral
										.obtenerObjeto(
												new CategoriaInvestigador(),
												new Long(categoriaInvestigador));
								if (iiAux != null) {
									servicioGeneral.guardarObjeto(iiAux);
									servicioPersona.agregarRolPersona(
											persona.getId(), rol);
								} else {
									servicioGeneral.guardarObjeto(persona);// 5
									preins_ecp_objeto.setInvestigador(persona);
									servicioPersona.agregarRolPersona(
											persona.getId(), rol);
								}

							} else {

								Institucion i = (Institucion) servicioGeneral
										.obtenerObjeto(new Institucion(),
												"2843");
								InvestigadorExterno ieAux = (InvestigadorExterno) servicioGeneral
										.obtenerObjeto(
												new InvestigadorExterno(),
												persona.getId());
								CategoriaInvestigador ci = (CategoriaInvestigador) servicioGeneral
										.obtenerObjeto(
												new CategoriaInvestigador(),
												new Long(categoriaInvestigador));
								if (ieAux != null) {
									ieAux.setEvaluador(Investigador.NO_EVALUADOR);
									ieAux.setCategoriaInvestigador(ci);
									ieAux.setInterno(Investigador.EXTERNO);
									ieAux.setInstitucion(i);

									if (ieAux.getContrasena() == null) {
										ieAux.setContrasena(new Long(
												servicioPersona
														.generarClaveExterno()));
									}
									servicioGeneral.guardarObjeto(ieAux);
								} else {

									if (servicioPersona
											.insertarNuevaPersonaDatosBasicos(persona)) {
										InvestigadorExterno ie = new InvestigadorExterno();
										servicioPersona
												.convertivirPersonaAInvestigadorExterno(
														persona, ie);
										ie.setCategoriaInvestigador(ci);
										ie.setInterno(Investigador.EXTERNO);
										ie.setEvaluador(Investigador.NO_EVALUADOR);
										ie.setId(persona.getId());
										ie.setInstitucion(i);
										if (ie.getContrasena() == null) {
											ie.setContrasena(new Long(
													servicioPersona
															.generarClaveExterno()));
										}

										if (inv == null) {
											servicioPersona
													.insertarInvestigador(ie);
										}
										servicioPersona
												.insertarExternoContraseña(ie);
										servicioPersona.agregarRolPersona(
												persona.getId(), rol);
										preins_ecp_objeto.setInvestigador(ie);
									} else
										errorInsertaPersona = true;
									//
								}

							}
						}
					}
				} else {
					servicioGeneral.guardarObjeto(persona);// 6
					preins_ecp_objeto.setInvestigador(persona);
				}

				if (persona != null && !errorInsertaPersona)
					return true;
				else
					return false;

			}

		} catch (Exception e) {
			System.out.println("Persona no registrada" + e.getMessage());
			return false;
		}

		if (persona != null)
			return true;
		else
			return false;
	}

	private boolean guardarPersonaInvestigador() {
		// Persona-Investigador Vs OfertaECP
		try {

			Investigador inv = null;
			boolean errorInsertaPersona = false;

			String cons_persona_invest = "select pp from Persona pp where pp.id.documento like '"
					+ documento
					+ "' and pp.id.tipoDocumento like '"
					+ tipo_doc
					+ "'";
			List lista_persona = servicioGeneral
					.obtenerObjetos(cons_persona_invest);
			if (lista_persona.size() > 0) {
				investigador = (Persona) lista_persona.get(0);
				investigador.setTelefono(tel_fijo);
				investigador.setEstadoCivil(estado_civil_atributo);
				investigador.setGenero(genero);
				investigador.setEmail(correo_electronico);
				// //
				// ========================================================>Revisar<===================================================

				boolean tiRol = false;
				List rolPer = servicioPersona.obtenerRols(investigador.getId());
				for (int l = 0; l < rolPer.size(); l++) {
					Rol r = (Rol) rolPer.get(l);
					if (r.getId().equals("FO")) {
						tiRol = true;
					}
				}
				if (!tiRol) {
					Rol rolExt = new Rol();
					rolExt.setId("FO");
					investigador.adicionarRol(rolExt);
				}

				servicioGeneral.guardarObjeto(investigador);// 1
				preins_ecp_objeto.setInvestigador(investigador);
			} else {

				System.out.println("Insertando Nuevo Investigador");

				if (persona != null)
					persona = new Persona();

				IdPersona idP = new IdPersona();
				idP.setDocumento(documento);
				idP.setTipoDocumento(tipo_doc);
				persona.setId(idP);
				categoriaInvestigador = CategoriaInvestigador.IDEXTERNO;// ========================================================>Revisar<===================================================TODO

				Persona personaAux = servicioPersona
						.obtenerPersonaRoles(persona.getId());
				persona.setCiudadDomicilio(ciudad_atributo);

				inv = (Investigador) servicioGeneral.obtenerObjeto(
						new Investigador(), persona.getId());

				if (personaAux == null) {

					persona.setNombre1(primer_nombre);
					persona.setNombre2(segundo_nombre);
					persona.setApellido1(primer_apellido);
					persona.setApellido2(segundo_apellido);
					persona.setEmail(correo_electronico);
					persona.setTelefono(tel_fijo);
					persona.setDireccion(direccion);
					persona.setGenero(genero);
					persona.setFechaNacimiento(fecNacimiento);

					Rol rol = new Rol();
					rol.setId("FO");
					persona.adicionarRol(rol);

					System.out.println("Insertando Nueva persona");

					if (esEstudiante) {

						if (estudiante != null) {
							Persona per = servicioPersona
									.obtenerPersona(estudiante.getId());
							if (per == null) {

								if (servicioPersona
										.insertarNuevaPersonaDatosCompletos(persona))// 2
								{
									servicioPersona.agregarRolPersona(
											persona.getId(), rol);
									preins_ecp_objeto.setInvestigador(persona);
								} else
									errorInsertaPersona = true;

							} else {
								servicioGeneral.guardarObjeto(persona);
								servicioPersona.agregarRolPersona(
										persona.getId(), rol);
							}
						}

					} else {
						if (esEmpleado) {

							Persona per = servicioPersona
									.obtenerPersona(empleado.getId());

							if (per == null) {

								if (servicioPersona
										.insertarNuevaPersonaDatosCompletos(persona))// 3
								{
									servicioPersona.agregarRolPersona(
											persona.getId(), rol);
									preins_ecp_objeto.setInvestigador(persona);
								} else
									errorInsertaPersona = true;

							} else {
								servicioGeneral.guardarObjeto(persona);// 4
								preins_ecp_objeto.setInvestigador(persona);
								servicioPersona.agregarRolPersona(
										persona.getId(), rol);
							}

						} else {
							if (esPersona) {

								InvestigadorInterno iiAux = (InvestigadorInterno) servicioGeneral
										.obtenerObjeto(
												new InvestigadorInterno(),
												persona.getId());
								CategoriaInvestigador ci = (CategoriaInvestigador) servicioGeneral
										.obtenerObjeto(
												new CategoriaInvestigador(),
												new Long(categoriaInvestigador));
								if (iiAux != null) {
									servicioGeneral.guardarObjeto(iiAux);
									servicioPersona.agregarRolPersona(
											persona.getId(), rol);
								} else {
									servicioGeneral.guardarObjeto(persona);// 5
									preins_ecp_objeto.setInvestigador(persona);
									servicioPersona.agregarRolPersona(
											persona.getId(), rol);
								}

							} else {

								Institucion i = (Institucion) servicioGeneral
										.obtenerObjeto(new Institucion(),
												"2843");
								InvestigadorExterno ieAux = (InvestigadorExterno) servicioGeneral
										.obtenerObjeto(
												new InvestigadorExterno(),
												persona.getId());
								CategoriaInvestigador ci = (CategoriaInvestigador) servicioGeneral
										.obtenerObjeto(
												new CategoriaInvestigador(),
												new Long(categoriaInvestigador));
								if (ieAux != null) {
									ieAux.setEvaluador(Investigador.NO_EVALUADOR);
									ieAux.setCategoriaInvestigador(ci);
									ieAux.setInterno(Investigador.EXTERNO);
									ieAux.setInstitucion(i);

									if (ieAux.getContrasena() == null) {
										ieAux.setContrasena(new Long(
												servicioPersona
														.generarClaveExterno()));
									}
									servicioGeneral.guardarObjeto(ieAux);
								} else {

									if (servicioPersona
											.insertarNuevaPersonaDatosBasicos(persona)) {
										InvestigadorExterno ie = new InvestigadorExterno();
										servicioPersona
												.convertivirPersonaAInvestigadorExterno(
														persona, ie);
										ie.setCategoriaInvestigador(ci);
										ie.setInterno(Investigador.EXTERNO);
										ie.setEvaluador(Investigador.NO_EVALUADOR);
										ie.setId(persona.getId());
										ie.setInstitucion(i);
										if (ie.getContrasena() == null) {
											ie.setContrasena(new Long(
													servicioPersona
															.generarClaveExterno()));
										}

										if (inv == null) {
											servicioPersona
													.insertarInvestigador(ie);
										}
										servicioPersona
												.insertarExternoContraseña(ie);
										servicioPersona.agregarRolPersona(
												persona.getId(), rol);

									} else
										errorInsertaPersona = true;
									//
								}

							}
						}
					}
				} else {
					servicioGeneral.guardarObjeto(persona);// 6
					preins_ecp_objeto.setInvestigador(persona);
				}

				if (persona != null && !errorInsertaPersona)
					return true;
				else
					return false;

			}

		} catch (Exception e) {
			System.out.println("Persona no registrada" + e.getMessage());
			return false;
		}

		if (persona != null)
			return true;
		else
			return false;

	}

	public void enviarCorreoMatricula() {

		try {

			String mensajeConfirmacion;

			if (persona != null) {

				mensajeConfirmacion = "\nMATRICULA GUARDADA CON EXITO" + "\n\n"
						+ "Estos son los datos que ha ingresado: " + "\n"
						+ "\n" + "Curso:"
						+ curso.getNombre()
						+ "\n"
						+ "Nombres: "
						+ preins_ecp_objeto.getInvestigador().getNombre1()
						+ " "
						+ preins_ecp_objeto.getInvestigador().getNombre2()
						+ "\n"
						+ "Apellidos: "
						+ preins_ecp_objeto.getInvestigador().getApellido1()
						+ " "
						+ preins_ecp_objeto.getInvestigador().getApellido2()
						+ "\n"
						+ "Documento: "
						+ preins_ecp_objeto.getInvestigador().getId()
								.getTipoDocumento()
						+ ". "
						+ preins_ecp_objeto.getInvestigador().getId()
								.getDocumento()
						+ "\n"
						+ "Correo: "
						+ preins_ecp_objeto.getInvestigador().getEmail()
						+ "\n"
						+ "Teléfono fijo: "
						+ preins_ecp_objeto.getInvestigador().getTelefono()
						+ "\n"
						+ "Teléfono movil: "
						+ preins_ecp_objeto.getTel_movil()
						+ "\n"
						+ "Dirección: "
						+ preins_ecp_objeto.getInvestigador().getDireccion()
						+ "\n"
						+ "Empresa postulante: "
						+ preins_ecp_objeto.getEmpresa() + "\n" + "\n\n\n" + "";

				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				correo.adicionarDireccion(preins_ecp_objeto.getInvestigador()
						.getEmail());

				//correo.adicionarCopiaOculta("sisii_nal@unal.edu.co");

				correo.setAsunto("Matricula CIENCIA PARA LAS REGIONES - Formulación de Proyectos Regionales en Ciencia, Tecnología e Innovación Realizada con Éxito");
				correo.setCuerpo(mensajeConfirmacion);

				if (!preins_ecp_objeto.getInvestigador().getEmail().equals("")
						&& preins_ecp_objeto.getInvestigador().getEmail() != null)
					servicioCorreo.enviarCorreo(correo);
				else
					System.out.println("NO SE HA ENVIADO EL CORREO");

			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public void enviarCorreoECP() {
		try {

			String mensajeConfirmacion;

			if (persona != null) {

				mensajeConfirmacion = "\nPREINSCRIPCIÓN GUARDADA CON EXITO"
						+ "\n\n" + "Estos son los datos que ha ingresado: "
						+ "\n" + "\n" + "Curso:"
						+ curso.getNombre()
						+ "\n"
						+ "Nombres: "
						+ preins_ecp_objeto.getInvestigador().getNombre1()
						+ " "
						+ preins_ecp_objeto.getInvestigador().getNombre2()
						+ "\n"
						+ "Apellidos: "
						+ preins_ecp_objeto.getInvestigador().getApellido1()
						+ " "
						+ preins_ecp_objeto.getInvestigador().getApellido2()
						+ "\n"
						+ "Documento: "
						+ preins_ecp_objeto.getInvestigador().getId()
								.getTipoDocumento()
						+ ". "
						+ preins_ecp_objeto.getInvestigador().getId()
								.getDocumento()
						+ "\n"
						+ "Correo: "
						+ preins_ecp_objeto.getInvestigador().getEmail()
						+ "\n"
						+ "Teléfono fijo: "
						+ preins_ecp_objeto.getInvestigador().getTelefono()
						+ "\n"
						+ "Teléfono movil: "
						+ preins_ecp_objeto.getTel_movil()
						+ "\n"
						+ "Ocupación: "
						+ ocupacion_atributo.getDescripcion()
						+ "\n"
						+ "Vinculación: "
						+ tipo_vinculacion_atributo.getDescripcion()
						+ "\n"
						+ "\n\n\n";

				mensajeConfirmacion += "Si desea actualizar la informacion ingresada puede hacerlo por medio del mismo enlace por el cual hizo la preinscripción\n\n"
						+ "";

				Correo correo = new Correo();
				correo.setOrigen(Correo.CORREO_HERMES);
				correo.adicionarDireccion(preins_ecp_objeto.getInvestigador()
						.getEmail());

				//correo.adicionarCopiaOculta("sisii_nal@unal.edu.co");

				correo.setAsunto("Preinscripción Realizada con Éxito");
				correo.setCuerpo(mensajeConfirmacion);

				if (!preins_ecp_objeto.getInvestigador().getEmail().equals("")
						&& preins_ecp_objeto.getInvestigador().getEmail() != null)
					servicioCorreo.enviarCorreo(correo);
				else
					System.out.println("NO SE HA ENVIADO EL CORREO");

			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public void guardarPreinscripcionOriginal() {

		construir_objetos();

		if (ins_act.equals("I"))
			preins_ecp_objeto = new Preinscripcion_ECP();

		if (guardarPersonaInvestigadorBasico()) {
			estado = "P";
			feccre = new Date();

			preins_ecp_objeto.setCargo(cargo);
			preins_ecp_objeto.setCorreo_empresa(correo_empresa);
			preins_ecp_objeto.setEmpresa(empresa);
			preins_ecp_objeto.setEntidad(entidad);
			preins_ecp_objeto.setEps(eps);
			preins_ecp_objeto.setEstado(estado);// llenar
			preins_ecp_objeto.setFeccre(feccre);
			preins_ecp_objeto
					.setFormacion_academica(formacion_academica_atributo);
			preins_ecp_objeto.setGrupo_sanguineo(grupo_sanguineo);

			preins_ecp_objeto.setCurso(curso);

			if (!pais_atributo.getId().equals("00")) {
				preins_ecp_objeto.setPais(pais_atributo);
				if (pais_atributo.getId().equals("CO")) {
					preins_ecp_objeto.setCiudad(ciudad_atributo);
					preins_ecp_objeto.setDepartamento(departamento_atributo);
				} else {
					preins_ecp_objeto.setCiudad(null);
					preins_ecp_objeto.setDepartamento(null);
				}
			} else {
				preins_ecp_objeto.setPais(null);
				preins_ecp_objeto.setCiudad(null);
				preins_ecp_objeto.setDepartamento(null);
			}
			preins_ecp_objeto.setProfesion(profesion);
			preins_ecp_objeto.setRh(rh);
			preins_ecp_objeto.setTel_movil(tel_movil);
			preins_ecp_objeto.setTelefono_empresa(telefono_empresa);

			preins_ecp_objeto.setPersona_contacto(persona_contacto);
			preins_ecp_objeto.setTel_contacto(tel_contacto);
			preins_ecp_objeto.setDireccion_empresa(direccion_empresa);

			if (!otros_intereses.equals("N"))
				preins_ecp_objeto.setOtros_intereses(otros_intereses_atributo);
			else
				preins_ecp_objeto.setOtros_intereses(null);

			preins_ecp_objeto.setTipo_vinculacion(tipo_vinculacion);

			preins_ecp_objeto.setOcupacion(ocupacion);
			preins_ecp_objeto.setMedio_publicidad(medio_publicidad);
			preins_ecp_objeto.setSector_empresa(sector_empresa);
			preins_ecp_objeto.setTipo_empresa(tipo_empresa);
			preins_ecp_objeto.setEstado_civil(estado_civil_atributo);

			// SEDE_FECHA
			preins_ecp_objeto.setSedeFecha(sedeFechaSeleccionada);

			// OTRO_EMPRESA
			preins_ecp_objeto.setNitEmpresa(nitEmpresa);
			preins_ecp_objeto.setNumDocRepLegal(numDocRepLegal);
			preins_ecp_objeto.setTipoDocRepLegal(tipoDocRepLegal);
			preins_ecp_objeto.setNombresRepLegal(nombresRepLegal);
			preins_ecp_objeto.setApellidosRepLegal(apellidosRepLegal);

			// Valida si es estudiante activo
			if (esEstudiante)
				preins_ecp_objeto.setEstudianteUN("S");
			else
				preins_ecp_objeto.setEstudianteUN("N");

			// Valida si es docente activo
			if (esDocente)
				preins_ecp_objeto.setEsDocente("S");
			else
				preins_ecp_objeto.setEsDocente("N");

			// Valida si es empleado activo
			if (esEmpleado)
				preins_ecp_objeto.setEsEmpleado("S");
			else
				preins_ecp_objeto.setEsEmpleado("N");

			// Valida si es contratista
			if (esContratista)
				preins_ecp_objeto.setEsContratista("S");
			else
				preins_ecp_objeto.setEsContratista("N");

			// Valida si es egresado
			if (esEgresado)
				preins_ecp_objeto.setEsEgresado("S");
			else
				preins_ecp_objeto.setEsEgresado("N");

			// Valida si es externo
			if (esExterno)
				preins_ecp_objeto.setEsExterno("S");
			else
				preins_ecp_objeto.setEsExterno("N");

			if (documento != "" && primer_nombre != "" && primer_apellido != ""
					&& segundo_apellido != "" && correo_electronico != "") {

				if (id_oferta != -1 && curso != null) {
					servicioGeneral.guardarObjeto(this.preins_ecp_objeto);

					// save file

					for (int i = 0; i < listaArchivosNuevos.size(); i++) {
						ArchivosPreinscripcionECP ar = listaArchivosNuevos
								.get(i);
						ar.setPreinscripcionECP(preins_ecp_objeto);
						servicioGeneral.guardarObjeto(ar);

						String directorio = RUTA_ARCHIVOS+"HER_EXT_ARC_PREINS//"
								+ ar.getIdArchivo();
						String destination = directorio + "//"
								+ ar.getNombreArchivo();
						new File(directorio).mkdirs();
						copyFile(destination, ar.getArchivoInputStream());
					}

					// borrar archivo

					for (int i = 0; i < listaArchivosBorrados.size(); i++) {
						ArchivosPreinscripcionECP ar = listaArchivosBorrados
								.get(i);
						ar.setPreinscripcionECP(preins_ecp_objeto);
						servicioGeneral.eliminarObjeto(ar);
						String directorio = RUTA_ARCHIVOS+"HER_EXT_ARC_PREINS//"
								+ ar.getIdArchivo();
						if (new File(directorio).exists()
								&& new File(directorio).isDirectory()) {
							deleteWithChildren(directorio);
						}

					}

					enviarCorreoECP();

					if (ins_act == "I") {
						FacesContext
								.getCurrentInstance()
								.addMessage(
										"msgForm",
										new FacesMessage(
												FacesMessage.SEVERITY_INFO,
												"La informacion ha sido registrada con éxito",
												""));
						habilita_guardar = true;
					} else
						FacesContext
								.getCurrentInstance()
								.addMessage(
										"msgForm",
										new FacesMessage(
												FacesMessage.SEVERITY_INFO,
												"La informacion ha sido actualizada con éxito",
												""));

					FacesContext
							.getCurrentInstance()
							.addMessage(
									"growl",
									new FacesMessage(
											FacesMessage.SEVERITY_INFO, "",
											"Se ha enviado un correo electronico con los datos registrados"));

				} else
					FacesContext
							.getCurrentInstance()
							.addMessage(
									"msgForm",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"No se ha podido registrar la información. El curso no tiene una oferta vigente",
											""));

				cargarValoresIniciales();

			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"msgForm",
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Algunos campos obligatorios no han sido registrados",
										""));
			}
		} else
			FacesContext.getCurrentInstance().addMessage(
					"growl",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Se ha presentado un error",
							"Intentelo de nuevo por favor"));

	}

	public void guardarPreinscripcion() {

		construir_objetos();

		if (ins_act.equals("I"))
			preins_ecp_objeto = new Preinscripcion_ECP();

		if (guardarPersonaInvestigadorBasico()) {
			estado = "P";
			feccre = new Date();

			preins_ecp_objeto.setCargo(cargo);
			preins_ecp_objeto.setEmpresa(empresa);
			preins_ecp_objeto.setFeccre(feccre);
			preins_ecp_objeto
					.setFormacion_academica(formacion_academica_atributo);

			preins_ecp_objeto.setCurso(curso);

			if (!pais_atributo.getId().equals("00")) {
				preins_ecp_objeto.setPais(pais_atributo);
				if (pais_atributo.getId().equals("CO")) {
					preins_ecp_objeto.setCiudad(ciudad_atributo);
					preins_ecp_objeto.setDepartamento(departamento_atributo);
				} else {
					preins_ecp_objeto.setCiudad(null);
					preins_ecp_objeto.setDepartamento(null);
				}
			} else {
				preins_ecp_objeto.setPais(null);
				preins_ecp_objeto.setCiudad(null);
				preins_ecp_objeto.setDepartamento(null);
			}

			preins_ecp_objeto.setProfesion(profesion);
			preins_ecp_objeto.setTel_movil(tel_movil);
			preins_ecp_objeto.setDireccion_empresa(direccion_empresa);

			if (!otros_intereses.equals("N"))
				preins_ecp_objeto.setOtros_intereses(otros_intereses_atributo);
			else
				preins_ecp_objeto.setOtros_intereses(null);

			preins_ecp_objeto.setTipo_vinculacion(tipo_vinculacion);

			preins_ecp_objeto.setMedio_publicidad(medio_publicidad);
			// Valida si es estudiante activo
			if (esEstudiante)
				preins_ecp_objeto.setEstudianteUN("S");
			else
				preins_ecp_objeto.setEstudianteUN("N");

			// Valida si es docente activo
			if (esDocente)
				preins_ecp_objeto.setEsDocente("S");
			else
				preins_ecp_objeto.setEsDocente("N");

			// Valida si es empleado activo
			if (esEmpleado)
				preins_ecp_objeto.setEsEmpleado("S");
			else
				preins_ecp_objeto.setEsEmpleado("N");

			// Valida si es contratista
			if (esContratista)
				preins_ecp_objeto.setEsContratista("S");
			else
				preins_ecp_objeto.setEsContratista("N");

			// Valida si es egresado
			if (esEgresado)
				preins_ecp_objeto.setEsEgresado("S");
			else
				preins_ecp_objeto.setEsEgresado("N");

			// Valida si es externo
			if (esExterno)
				preins_ecp_objeto.setEsExterno("S");
			else
				preins_ecp_objeto.setEsExterno("N");

			if (documento != "" && primer_nombre != "" && primer_apellido != ""
					&& segundo_apellido != "" && correo_electronico != "") {

				if (id_oferta != -1 && curso != null) {
					servicioGeneral.guardarObjeto(this.preins_ecp_objeto);

					enviarCorreoECP();

					if (ins_act == "I") {
						FacesContext
								.getCurrentInstance()
								.addMessage(
										"msgForm",
										new FacesMessage(
												FacesMessage.SEVERITY_INFO,
												"La informacion ha sido registrada con éxito",
												""));
						habilita_guardar = true;
					} else
						FacesContext
								.getCurrentInstance()
								.addMessage(
										"msgForm",
										new FacesMessage(
												FacesMessage.SEVERITY_INFO,
												"La informacion ha sido actualizada con éxito",
												""));

					FacesContext
							.getCurrentInstance()
							.addMessage(
									"growl",
									new FacesMessage(
											FacesMessage.SEVERITY_INFO, "",
											"Se ha enviado un correo electronico con los datos registrados"));

				} else
					FacesContext
							.getCurrentInstance()
							.addMessage(
									"msgForm",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"No se ha podido registrar la información. El curso no tiene una oferta vigente",
											""));

				cargarValoresIniciales();

			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"msgForm",
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Algunos campos obligatorios no han sido registrados",
										""));
			}
		} else
			FacesContext.getCurrentInstance().addMessage(
					"growl",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Se ha presentado un error",
							"Intentelo de nuevo por favor"));

	}

	public void guardarMatriculaCPR() {

		construir_objetos_matricula();

		if (ins_act.equals("I"))
			preins_ecp_objeto = new Preinscripcion_ECP();

		if (guardarPersonaInvestigadorBasico()) {
			estado = "P";
			feccre = new Date();
			preins_ecp_objeto.setCargo(cargo);
			preins_ecp_objeto.setCorreo_empresa(correo_empresa);
			preins_ecp_objeto.setEmpresa(empresa);
			preins_ecp_objeto.setEntidad(entidad);
			preins_ecp_objeto.setEps(eps);
			preins_ecp_objeto.setEstado(estado);// llenar
			preins_ecp_objeto.setFeccre(feccre);
			preins_ecp_objeto
					.setFormacion_academica(formacion_academica_atributo);
			preins_ecp_objeto.setGrupo_sanguineo(grupo_sanguineo);

			preins_ecp_objeto.setCurso(curso);

			if (!pais_atributo.getId().equals("00")) {
				preins_ecp_objeto.setPais(pais_atributo);
				if (pais_atributo.getId().equals("CO")) {
					preins_ecp_objeto.setCiudad(ciudad_atributo);
					preins_ecp_objeto.setDepartamento(departamento_atributo);
				} else {
					preins_ecp_objeto.setCiudad(null);
					preins_ecp_objeto.setDepartamento(null);
				}
			} else {
				preins_ecp_objeto.setPais(null);
				preins_ecp_objeto.setCiudad(null);
				preins_ecp_objeto.setDepartamento(null);
			}
			preins_ecp_objeto.setProfesion(profesion);
			preins_ecp_objeto.setRh(rh);
			preins_ecp_objeto.setTel_movil(tel_movil);
			preins_ecp_objeto.setTelefono_empresa(telefono_empresa);

			preins_ecp_objeto.setPersona_contacto(persona_contacto);
			preins_ecp_objeto.setTel_contacto(tel_contacto);
			preins_ecp_objeto.setDireccion_empresa(direccion_empresa);

			if (!otros_intereses.equals("N"))
				preins_ecp_objeto.setOtros_intereses(otros_intereses_atributo);
			else
				preins_ecp_objeto.setOtros_intereses(null);

			preins_ecp_objeto.setTipo_vinculacion(tipo_vinculacion);

			preins_ecp_objeto.setOcupacion(ocupacion);
			preins_ecp_objeto.setMedio_publicidad(medio_publicidad);
			preins_ecp_objeto.setSector_empresa(sector_empresa);
			preins_ecp_objeto.setTipo_empresa(tipo_empresa);
			preins_ecp_objeto.setEstado_civil(estado_civil_atributo);

			// SEDE_FECHA
			preins_ecp_objeto.setSedeFecha(sedeFechaSeleccionada);

			// OTRO_EMPRESA
			preins_ecp_objeto.setNitEmpresa(nitEmpresa);
			preins_ecp_objeto.setNumDocRepLegal(numDocRepLegal);
			preins_ecp_objeto.setTipoDocRepLegal(tipoDocRepLegal);
			preins_ecp_objeto.setNombresRepLegal(nombresRepLegal);
			preins_ecp_objeto.setApellidosRepLegal(apellidosRepLegal);

			// Valida si es estudiante activo
			if (esEstudiante)
				preins_ecp_objeto.setEstudianteUN("S");
			else
				preins_ecp_objeto.setEstudianteUN("N");

			// Valida si es docente activo
			if (esDocente)
				preins_ecp_objeto.setEsDocente("S");
			else
				preins_ecp_objeto.setEsDocente("N");

			// Valida si es empleado activo
			if (esEmpleado)
				preins_ecp_objeto.setEsEmpleado("S");
			else
				preins_ecp_objeto.setEsEmpleado("N");

			// Valida si es contratista
			if (esContratista)
				preins_ecp_objeto.setEsContratista("S");
			else
				preins_ecp_objeto.setEsContratista("N");

			// Valida si es egresado
			if (esEgresado)
				preins_ecp_objeto.setEsEgresado("S");
			else
				preins_ecp_objeto.setEsEgresado("N");

			// Valida si es externo
			if (esExterno)
				preins_ecp_objeto.setEsExterno("S");
			else
				preins_ecp_objeto.setEsExterno("N");

			if (documento != "" && primer_nombre != "" && primer_apellido != ""
					&& segundo_apellido != "" && correo_electronico != "") {

				if (id_oferta != -1 && curso != null) {

					servicioGeneral.guardarObjeto(this.preins_ecp_objeto);

					for (int i = 0; i < listaArchivosNuevos.size(); i++) {
						ArchivosPreinscripcionECP ar = listaArchivosNuevos
								.get(i);
						ar.setPreinscripcionECP(preins_ecp_objeto);
						servicioGeneral.guardarObjeto(ar);

						String directorio = RUTA_ARCHIVOS+"HER_EXT_ARC_PREINS//"
								+ ar.getIdArchivo();
						String destination = directorio + "//"
								+ ar.getNombreArchivo();
						new File(directorio).mkdirs();
						copyFile(destination, ar.getArchivoInputStream());
					}

					// borrar archivo

					for (int i = 0; i < listaArchivosBorrados.size(); i++) {
						ArchivosPreinscripcionECP ar = listaArchivosBorrados
								.get(i);
						ar.setPreinscripcionECP(preins_ecp_objeto);
						servicioGeneral.eliminarObjeto(ar);
						String directorio = RUTA_ARCHIVOS+"HER_EXT_ARC_PREINS//"
								+ ar.getIdArchivo();
						if (new File(directorio).exists()
								&& new File(directorio).isDirectory()) {
							deleteWithChildren(directorio);
						}

					}

					enviarCorreoMatricula();

					if (ins_act == "I") {
						FacesContext
								.getCurrentInstance()
								.addMessage(
										"msgForm",
										new FacesMessage(
												FacesMessage.SEVERITY_INFO,
												"La informacion ha sido registrada con éxito",
												""));
						habilita_guardar = true;
					} else
						FacesContext
								.getCurrentInstance()
								.addMessage(
										"msgForm",
										new FacesMessage(
												FacesMessage.SEVERITY_INFO,
												"La informacion ha sido actualizada con éxito",
												""));

					FacesContext
							.getCurrentInstance()
							.addMessage(
									"growl",
									new FacesMessage(
											FacesMessage.SEVERITY_INFO, "",
											"Se ha enviado un correo electronico con los datos registrados"));

				} else
					FacesContext
							.getCurrentInstance()
							.addMessage(
									"msgForm",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"No se ha podido registrar la información. El curso no tiene una oferta vigente",
											""));

				cargarValoresIniciales();

			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"msgForm",
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Algunos campos obligatorios no han sido registrados",
										""));
			}
		} else
			FacesContext.getCurrentInstance().addMessage(
					"growl",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Se ha presentado un error",
							"Intentelo de nuevo por favor"));

	}

	public void subirArchivos(FileUploadEvent event) throws IOException {
		if (event.getFile() != null) {
			System.out.println(event.getFile().getFileName());
			ArchivosPreinscripcionECP archivo = new ArchivosPreinscripcionECP();
			archivo.setPreinscripcionECP(preins_ecp_objeto);
			archivo.setBytes(event.getFile().getContents());
			archivo.setArchivoInputStream(event.getFile().getInputstream());
			archivo.setNombreArchivo(quitarAcentos(event.getFile()
					.getFileName()));
			archivo.setEsNuevo(true);
			listaArchivosNuevos.add(archivo);
			listaArchivos.add(archivo);

		}

	}

	/**
	 * Deletes the given path and, if it is a directory, deletes all its
	 * children.
	 */
	public boolean deleteWithChildren(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return true;
		}
		if (!file.isDirectory()) {
			return file.delete();
		}
		return this.deleteChildren(file) && file.delete();
	}

	private boolean deleteChildren(File dir) {
		File[] children = dir.listFiles();
		boolean childrenDeleted = true;
		for (int i = 0; children != null && i < children.length; i++) {
			File child = children[i];
			if (child.isDirectory()) {
				childrenDeleted = this.deleteChildren(child) && childrenDeleted;
			}
			if (child.exists()) {
				childrenDeleted = child.delete() && childrenDeleted;
			}
		}
		return childrenDeleted;
	}

	public void eliminarArchivo() {
		System.out.println("eliminar archivo");
		if (archivoPre != null && archivoPre.isEsNuevo()) {
			System.out.println("eliminar archivo nuevo");
			listaArchivosNuevos.remove(archivoPre);
			listaArchivos.remove(archivoPre);
			listaArchivosBorrados.add(archivoPre);
		} else {
			if (archivoPre != null && !archivoPre.isEsNuevo()) {
				System.out.println("eliminar archivo viejo");
				listaArchivos.remove(archivoPre);
				listaArchivosBorrados.add(archivoPre);
			}
		}
	}

	public void copyFile(String fileName, InputStream in) {
		try {

			OutputStream out = new FileOutputStream(new File(fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.out.println("New file created!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public String quitarAcentos(String input) {
		// Cadena de caracteres original a sustituir.
		String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ ";
		// Cadena de caracteres ASCII que reemplazarán los originales.
		String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC_";
		String output = input;
		for (int i = 0; i < original.length(); i++) {
			// Reemplazamos los caracteres especiales.
			output = output.replace(original.charAt(i), ascii.charAt(i));
		}// for i
		return output;
	}

	public void aceptarTerminos() {
		System.out.println("aceptar terminos");
		if (opcionesTerminos.equals("Si")) {
			mostrarBotonGuardar = true;
		} else {
			mostrarBotonGuardar = false;
		}

	}

	public String volver() {
		sesion.removeAttribute("ManejadorPreinscripcionECP");
		return "salir";
	}

	private void cargarTiposDocumento() {
		List listaTipoDocumento = servicioGeneral
				.obtenerListaObjetos("TipoDocumento");
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = (TipoDocumento) listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
	}

	private void cargarPaises() {
		List listaPaises = servicioGeneral.obtenerListaObjetos("Pais");
		paisItem = new SelectItem[listaPaises.size()];
		for (int i = 0; i < listaPaises.size(); i++) {
			Pais paisObjeto = (Pais) listaPaises.get(i);
			paisItem[i] = new SelectItem(paisObjeto.getId(),
					paisObjeto.getNombre());
		}
	}

	private void cargarFormacionAcademica() {

		formacionAcademicaItemList = new ArrayList<SelectItem>();
		String consulta = "select ff from TipoFormacion ff where ff.id in "
				+ "('ME','UT','UL','UM','BS','TL','11','NA','UN','PD','PG','NF','ES','EM','MA','MM','PH','PR','DO','TC')";
		List lista = servicioGeneral.obtenerObjetos(consulta);

		for (int i = 0; i < lista.size(); i++) {
			TipoFormacion tipoFor = (TipoFormacion) lista.get(i);
			formacionAcademicaItemList.add(new SelectItem(tipoFor.getId(),
					tipoFor.getNombre()));
		}

	}

	public void cargarDepto() {
		departamentoItemList = new ArrayList<SelectItem>();
		if (pais.equals("CO")) {
			String consulta = "select dd from Departamento dd where dd.id like '%"
					+ pais + "%' ";
			List lista = servicioGeneral.obtenerObjetos(consulta);
			for (int i = 0; i < lista.size(); i++) {
				Departamento depto = (Departamento) lista.get(i);
				departamentoItemList.add(new SelectItem(depto.getId(), depto
						.getNombre()));
			}
			departamento = "CO11";
			cargarCiudad();

		}
	}

	public void cargarCiudad() {
		ciudadItemList = new ArrayList<SelectItem>();
		if (pais.equals("CO")) {
			String consulta = "select cc from Ciudad cc where cc.departamento like '%"
					+ departamento + "%' ";
			List lista = servicioGeneral.obtenerObjetos(consulta);
			for (int i = 0; i < lista.size(); i++) {
				Ciudad ciudad = (Ciudad) lista.get(i);
				ciudadItemList.add(new SelectItem(ciudad.getId(), ciudad
						.getNombre()));
			}
		}
	}

	public void cargarCiudadCompletas() {
		ciudadItemList = new ArrayList<SelectItem>();

		String consulta = "select cc from Ciudad cc";
		List lista = servicioGeneral.obtenerObjetos(consulta);
		for (int i = 0; i < lista.size(); i++) {
			Ciudad ciudad = (Ciudad) lista.get(i);
			ciudadItemList.add(new SelectItem(ciudad.getId(), ciudad
					.getNombre()));
		}
	}

	public void cargarOtrosIntereses() {
		otrosInteresesItemList = new ArrayList<SelectItem>();
		String consulta = "select ff from AgendaConocimiento ff where ff.padre like '0'";
		List lista = servicioGeneral.obtenerObjetos(consulta);

		for (int i = 0; i < lista.size(); i++) {
			AgendaConocimiento agd_conocimiento = (AgendaConocimiento) lista
					.get(i);
			otrosInteresesItemList.add(new SelectItem(agd_conocimiento.getId(),
					agd_conocimiento.getNombre()));
		}
	}

	public void cargarTipoVinculacion() {
		tipoVinculacionItemList = new ArrayList<SelectItem>();
		String consulta = "select ff from DominioDetalle ff, Dominio d where ff.identificador.id = d.id and d.tipo like 'TIPO_VINCULACION_PERSONA' ";
		List lista = servicioGeneral.obtenerObjetos(consulta);

		for (int i = 0; i < lista.size(); i++) {
			DominioDetalle tipo_vinculacion = (DominioDetalle) lista.get(i);
			tipoVinculacionItemList.add(new SelectItem(tipo_vinculacion
					.getIdentificador().getTipo(), tipo_vinculacion
					.getDescripcion()));
		}

	}

	public void cargarGrupoSanguineoOpciones() {
		grupoSanguineoOpciones = new ArrayList<String>();
		grupoSanguineoOpciones.add("A");
		grupoSanguineoOpciones.add("B");
		grupoSanguineoOpciones.add("AB");
		grupoSanguineoOpciones.add("O");
	}

	public void cargarRh() {
		rhOpciones = new ArrayList<String>();

		rhOpciones.add("RH+");
		rhOpciones.add("RH-");
	}

	public void cargarGeneroOpciones() {
		generoOpciones = new Vector();
		generoOpciones.add(new SelectItem("F", "Femenino"));
		generoOpciones.add(new SelectItem("M", "Masculino"));

	}

	public void cargarEstadoCivilOpciones() {
		List listaEstadoCivil = servicioGeneral
				.obtenerListaObjetos("EstadoCivil");
		estadoCivilItemList = new ArrayList<SelectItem>();
		for (int i = 0; i < listaEstadoCivil.size(); i++) {
			EstadoCivil estado = (EstadoCivil) listaEstadoCivil.get(i);
			estadoCivilItemList.add(new SelectItem(estado.getId(), estado
					.getNombre()));
		}
	}

	public void cargarSectoresEmpresa() {
		sectorEmpresaItemList = new ArrayList<SelectItem>();
		String consulta = "select ff from DominioDetalle ff, Dominio d where ff.identificador.id = d.id and d.tipo like 'EXT_SECTOR_ENTIDAD' ";
		List lista = servicioGeneral.obtenerObjetos(consulta);

		for (int i = 0; i < lista.size(); i++) {
			DominioDetalle sector = (DominioDetalle) lista.get(i);
			sectorEmpresaItemList.add(new SelectItem(sector.getIdentificador()
					.getTipo(), sector.getDescripcion()));
		}
	}

	public void cargarTiposEmpresa() {
		tipoEmpresaItemList = new ArrayList<SelectItem>();
		String consulta = "select ff from DominioDetalle ff, Dominio d where ff.identificador.id = d.id and d.tipo like 'EXT_NATURALEZA_ENTIDAD' ";
		List lista = servicioGeneral.obtenerObjetos(consulta);

		for (int i = 0; i < lista.size(); i++) {
			DominioDetalle tipo = (DominioDetalle) lista.get(i);
			tipoEmpresaItemList.add(new SelectItem(tipo.getIdentificador()
					.getTipo(), tipo.getDescripcion()));
		}
	}

	public void cargarMediosPublicidad() {
		medioPublicidadItemList = new ArrayList<SelectItem>();
		String consulta = "select ff from DominioDetalle ff, Dominio d where ff.identificador.id = d.id and d.tipo like 'ECP_MEDIO_PUBLICIDAD' ";
		List lista = servicioGeneral.obtenerObjetos(consulta);

		for (int i = 0; i < lista.size(); i++) {
			DominioDetalle medio = (DominioDetalle) lista.get(i);
			medioPublicidadItemList.add(new SelectItem(medio.getIdentificador()
					.getTipo(), medio.getDescripcion()));
		}
	}

	public void cargarOcupacionOpciones() {
		ocupacionItemList = new ArrayList<SelectItem>();
		String consulta = "select ff from DominioDetalle ff, Dominio d where ff.identificador.id = d.id and d.tipo like 'ECP_OCUPACION' ";
		List lista = servicioGeneral.obtenerObjetos(consulta);

		for (int i = 0; i < lista.size(); i++) {
			DominioDetalle ocupacion = (DominioDetalle) lista.get(i);
			ocupacionItemList.add(new SelectItem(ocupacion.getIdentificador()
					.getTipo(), ocupacion.getDescripcion()));
		}
	}

	public String onFlowProcess(FlowEvent event) {

		logger.info("Current wizard step:" + event.getOldStep());
		logger.info("Next step:" + event.getNewStep());

		if (event.getNewStep().equals("confirmar"))
			construir_objetos();
		return event.getNewStep();
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public SelectItem[] getPaisItem() {
		return paisItem;
	}

	public void setPaisItem(SelectItem[] paisItem) {
		this.paisItem = paisItem;
	}

	public List<SelectItem> getDepartamentoItemList() {
		return departamentoItemList;
	}

	public void setDepartamentoItemList(List<SelectItem> departamentoItemList) {
		this.departamentoItemList = departamentoItemList;
	}

	public List<SelectItem> getCiudadItemList() {
		return ciudadItemList;
	}

	public void setCiudadItemList(List<SelectItem> ciudadItemList) {
		this.ciudadItemList = ciudadItemList;
	}

	public List<SelectItem> getFormacionAcademicaItemList() {
		return formacionAcademicaItemList;
	}

	public void setFormacionAcademicaItemList(
			List<SelectItem> formacionAcademicaItemList) {
		this.formacionAcademicaItemList = formacionAcademicaItemList;
	}

	public List<String> getGrupoSanguineoOpciones() {
		return grupoSanguineoOpciones;
	}

	public void setGrupoSanguineoOpciones(List<String> grupoSanguineoOpciones) {
		this.grupoSanguineoOpciones = grupoSanguineoOpciones;
	}

	public List<String> getRhOpciones() {
		return rhOpciones;
	}

	public void setRhOpciones(List<String> rhOpciones) {
		this.rhOpciones = rhOpciones;
	}

	public TipoDocumento getTipo_documento() {
		return tipo_documento;
	}

	public void setTipo_documento(TipoDocumento tipo_documento) {
		this.tipo_documento = tipo_documento;
	}

	public Pais getPais_atributo() {
		return pais_atributo;
	}

	public void setPais_atributo(Pais pais_atributo) {
		this.pais_atributo = pais_atributo;
	}

	public Departamento getDepartamento_atributo() {
		return departamento_atributo;
	}

	public void setDepartamento_atributo(Departamento departamento_atributo) {
		this.departamento_atributo = departamento_atributo;
	}

	public Ciudad getCiudad_atributo() {
		return ciudad_atributo;
	}

	public void setCiudad_atributo(Ciudad ciudad_atributo) {
		this.ciudad_atributo = ciudad_atributo;
	}

	public String getTipo_doc() {
		return tipo_doc;
	}

	public void setTipo_doc(String tipo_doc) {
		this.tipo_doc = tipo_doc;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getPrimer_apellido() {
		return primer_apellido;
	}

	public void setPrimer_apellido(String primer_apellido) {
		this.primer_apellido = primer_apellido;
	}

	public String getSegundo_apellido() {
		return segundo_apellido;
	}

	public void setSegundo_apellido(String segundo_apellido) {
		this.segundo_apellido = segundo_apellido;
	}

	public String getCorreo_electronico() {
		return correo_electronico;
	}

	public void setCorreo_electronico(String correo_electronico) {
		this.correo_electronico = correo_electronico;
	}

	public String getTel_fijo() {
		return tel_fijo;
	}

	public void setTel_fijo(String tel_fijo) {
		this.tel_fijo = tel_fijo;
	}

	public String getTel_movil() {
		return tel_movil;
	}

	public void setTel_movil(String tel_movil) {
		this.tel_movil = tel_movil;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getFormacion_academica() {
		return formacion_academica;
	}

	public void setFormacion_academica(String formacion_academica) {
		this.formacion_academica = formacion_academica;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getTelefono_empresa() {
		return telefono_empresa;
	}

	public void setTelefono_empresa(String telefono_empresa) {
		this.telefono_empresa = telefono_empresa;
	}

	public String getCorreo_empresa() {
		return correo_empresa;
	}

	public void setCorreo_empresa(String correo_empresa) {
		this.correo_empresa = correo_empresa;
	}

	public String getGrupo_sanguineo() {
		return grupo_sanguineo;
	}

	public void setGrupo_sanguineo(String grupo_sanguineo) {
		this.grupo_sanguineo = grupo_sanguineo;
	}

	public String getRh() {
		return rh;
	}

	public void setRh(String rh) {
		this.rh = rh;
	}

	public String getEps() {
		return eps;
	}

	public void setEps(String eps) {
		this.eps = eps;
	}

	public Date getFeccre() {
		return feccre;
	}

	public void setFeccre(Date feccre) {
		this.feccre = feccre;
	}

	public long getId_oferta() {
		return id_oferta;
	}

	public void setId_oferta(long id_oferta) {
		this.id_oferta = id_oferta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Preinscripcion_ECP getPreins_ecp_objeto() {
		return preins_ecp_objeto;
	}

	public void setPreins_ecp_objeto(Preinscripcion_ECP preins_ecp_objeto) {
		this.preins_ecp_objeto = preins_ecp_objeto;
	}

	public TipoFormacion getFormacion_academica_atributo() {
		return formacion_academica_atributo;
	}

	public void setFormacion_academica_atributo(
			TipoFormacion formacion_academica_atributo) {
		this.formacion_academica_atributo = formacion_academica_atributo;
	}

	public String getIns_act() {
		return ins_act;
	}

	public void setIns_act(String ins_act) {
		this.ins_act = ins_act;
	}

	public boolean isHabilita_doc() {
		return habilita_doc;
	}

	public void setHabilita_doc(boolean habilita_doc) {
		this.habilita_doc = habilita_doc;
	}

	public boolean isHabilita_cont() {
		return habilita_cont;
	}

	public void setHabilita_cont(boolean habilita_cont) {
		this.habilita_cont = habilita_cont;
	}

	public boolean isHabilita_guardar() {
		return habilita_guardar;
	}

	public void setHabilita_guardar(boolean habilita_guardar) {
		this.habilita_guardar = habilita_guardar;
	}

	public String getOtros_intereses() {
		return otros_intereses;
	}

	public void setOtros_intereses(String otros_intereses) {
		this.otros_intereses = otros_intereses;
	}

	public List<SelectItem> getOtrosInteresesItemList() {
		return otrosInteresesItemList;
	}

	public void setOtrosInteresesItemList(
			List<SelectItem> otrosInteresesItemList) {
		this.otrosInteresesItemList = otrosInteresesItemList;
	}

	public AgendaConocimiento getOtros_intereses_atributo() {
		return otros_intereses_atributo;
	}

	public void setOtros_intereses_atributo(
			AgendaConocimiento otros_intereses_atributo) {
		this.otros_intereses_atributo = otros_intereses_atributo;
	}

	public String getTipo_vinculacion() {
		return tipo_vinculacion;
	}

	public void setTipo_vinculacion(String tipo_vinculacion) {
		this.tipo_vinculacion = tipo_vinculacion;
	}

	public List<SelectItem> getTipoVinculacionItemList() {
		return tipoVinculacionItemList;
	}

	public void setTipoVinculacionItemList(
			List<SelectItem> tipoVinculacionItemList) {
		this.tipoVinculacionItemList = tipoVinculacionItemList;
	}

	public DominioDetalle getTipo_vinculacion_atributo() {
		return tipo_vinculacion_atributo;
	}

	public void setTipo_vinculacion_atributo(
			DominioDetalle tipo_vinculacion_atributo) {
		this.tipo_vinculacion_atributo = tipo_vinculacion_atributo;
	}

	public boolean isEsPersona() {
		return esPersona;
	}

	public void setEsPersona(boolean esPersona) {
		this.esPersona = esPersona;
	}

	public boolean isEsEmpleado() {
		return esEmpleado;
	}

	public void setEsEmpleado(boolean esEmpleado) {
		this.esEmpleado = esEmpleado;
	}

	public boolean isEsEstudiante() {
		return esEstudiante;
	}

	public void setEsEstudiante(boolean esEstudiante) {
		this.esEstudiante = esEstudiante;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public VEmpleadoSara getEmpleado() {
		return empleado;
	}

	public void setEmpleado(VEmpleadoSara empleado) {
		this.empleado = empleado;
	}

	public Proyecto getCurso() {
		return curso;
	}

	public void setCurso(Proyecto curso) {
		this.curso = curso;
	}

	public String getCategoriaInvestigador() {
		return categoriaInvestigador;
	}

	public void setCategoriaInvestigador(String categoriaInvestigador) {
		this.categoriaInvestigador = categoriaInvestigador;
	}

	/**
	 * @return the segundo_nombre
	 */
	public String getSegundo_nombre() {
		return segundo_nombre;
	}

	/**
	 * @param segundo_nombre
	 *            the segundo_nombre to set
	 */
	public void setSegundo_nombre(String segundo_nombre) {
		this.segundo_nombre = segundo_nombre;
	}

	public String getPrimer_nombre() {
		return primer_nombre;
	}

	public void setPrimer_nombre(String primer_nombre) {
		this.primer_nombre = primer_nombre;
	}

	/**
	 * @return the fecNacimiento
	 */
	public Date getFecNacimiento() {
		return fecNacimiento;
	}

	/**
	 * @param fecNacimiento
	 *            the fecNacimiento to set
	 */
	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}

	public String getSector_empresa() {
		return sector_empresa;
	}

	public void setSector_empresa(String sector_empresa) {
		this.sector_empresa = sector_empresa;
	}

	public String getTipo_empresa() {
		return tipo_empresa;
	}

	public void setTipo_empresa(String tipo_empresa) {
		this.tipo_empresa = tipo_empresa;
	}

	public String getMedio_publicidad() {
		return medio_publicidad;
	}

	public void setMedio_publicidad(String medio_publicidad) {
		this.medio_publicidad = medio_publicidad;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getEstudianteUN() {
		return estudianteUN;
	}

	public void setEstudianteUN(String estudianteUN) {
		this.estudianteUN = estudianteUN;
	}

	public List<SelectItem> getEstadoCivilItemList() {
		return estadoCivilItemList;
	}

	public void setEstadoCivilItemList(List<SelectItem> estadoCivilItemList) {
		this.estadoCivilItemList = estadoCivilItemList;
	}

	public List<SelectItem> getSectorEmpresaItemList() {
		return sectorEmpresaItemList;
	}

	public void setSectorEmpresaItemList(List<SelectItem> sectorEmpresaItemList) {
		this.sectorEmpresaItemList = sectorEmpresaItemList;
	}

	public List<SelectItem> getTipoEmpresaItemList() {
		return tipoEmpresaItemList;
	}

	public void setTipoEmpresaItemList(List<SelectItem> tipoEmpresaItemList) {
		this.tipoEmpresaItemList = tipoEmpresaItemList;
	}

	public List<SelectItem> getMedioPublicidadItemList() {
		return medioPublicidadItemList;
	}

	public void setMedioPublicidadItemList(
			List<SelectItem> medioPublicidadItemList) {
		this.medioPublicidadItemList = medioPublicidadItemList;
	}

	public List<SelectItem> getOcupacionItemList() {
		return ocupacionItemList;
	}

	public void setOcupacionItemList(List<SelectItem> ocupacionItemList) {
		this.ocupacionItemList = ocupacionItemList;
	}

	public String getEstado_civil() {
		return estado_civil;
	}

	public void setEstado_civil(String estado_civil) {
		this.estado_civil = estado_civil;
	}

	public EstadoCivil getEstado_civil_atributo() {
		return estado_civil_atributo;
	}

	public void setEstado_civil_atributo(EstadoCivil estado_civil_atributo) {
		this.estado_civil_atributo = estado_civil_atributo;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public List getGeneroOpciones() {
		return generoOpciones;
	}

	public void setGeneroOpciones(List generoOpciones) {
		this.generoOpciones = generoOpciones;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		ManejadorMatriculaECP.logger = logger;
	}

	public int getNumeroTab() {
		return numeroTab;
	}

	public void setNumeroTab(int numeroTab) {
		this.numeroTab = numeroTab;
	}

	public DominioDetalle getOcupacion_atributo() {
		return ocupacion_atributo;
	}

	public void setOcupacion_atributo(DominioDetalle ocupacion_atributo) {
		this.ocupacion_atributo = ocupacion_atributo;
	}

	public DominioDetalle getSector_atributo() {
		return sector_atributo;
	}

	public void setSector_atributo(DominioDetalle sector_atributo) {
		this.sector_atributo = sector_atributo;
	}

	public DominioDetalle getMedio_publicidad_atributo() {
		return medio_publicidad_atributo;
	}

	public void setMedio_publicidad_atributo(
			DominioDetalle medio_publicidad_atributo) {
		this.medio_publicidad_atributo = medio_publicidad_atributo;
	}

	public DominioDetalle getNaturaleza_atributo() {
		return naturaleza_atributo;
	}

	public void setNaturaleza_atributo(DominioDetalle naturaleza_atributo) {
		this.naturaleza_atributo = naturaleza_atributo;
	}

	public Persona getInvestigador() {
		return investigador;
	}

	public void setInvestigador(Persona investigador) {
		this.investigador = investigador;
	}

	public String getPersona_contacto() {
		return persona_contacto;
	}

	public void setPersona_contacto(String persona_contacto) {
		this.persona_contacto = persona_contacto;
	}

	public String getTel_contacto() {
		return tel_contacto;
	}

	public void setTel_contacto(String tel_contacto) {
		this.tel_contacto = tel_contacto;
	}

	public String getDireccion_empresa() {
		return direccion_empresa;
	}

	public void setDireccion_empresa(String direccion_empresa) {
		this.direccion_empresa = direccion_empresa;
	}

	public Investigador getDocente() {
		return docente;
	}

	public void setDocente(Investigador docente) {
		this.docente = docente;
	}

	public boolean isEsDocente() {
		return esDocente;
	}

	public void setEsDocente(boolean esDocente) {
		this.esDocente = esDocente;
	}

	public String getSedeFechaSeleccionada() {
		return sedeFechaSeleccionada;
	}

	public void setSedeFechaSeleccionada(String sedeFechaSeleccionada) {
		this.sedeFechaSeleccionada = sedeFechaSeleccionada;
	}

	public List<SelectItem> getSedeFechaItemList() {
		return sedeFechaItemList;
	}

	public void setSedeFechaItemList(List<SelectItem> sedeFechaItemList) {
		this.sedeFechaItemList = sedeFechaItemList;
	}

	public boolean isEsContratista() {
		return esContratista;
	}

	public void setEsContratista(boolean esContratista) {
		this.esContratista = esContratista;
	}

	public boolean isEsExterno() {
		return esExterno;
	}

	public void setEsExterno(boolean esExterno) {
		this.esExterno = esExterno;
	}

	public boolean isEsEgresado() {
		return esEgresado;
	}

	public void setEsEgresado(boolean esEgresado) {
		this.esEgresado = esEgresado;
	}

	public List<ArchivosPreinscripcionECP> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<ArchivosPreinscripcionECP> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public List<ArchivosPreinscripcionECP> getListaArchivosNuevos() {
		return listaArchivosNuevos;
	}

	public void setListaArchivosNuevos(
			List<ArchivosPreinscripcionECP> listaArchivosNuevos) {
		this.listaArchivosNuevos = listaArchivosNuevos;
	}

	public ArchivosPreinscripcionECP getArchivoPre() {
		return archivoPre;
	}

	public void setArchivoPre(ArchivosPreinscripcionECP archivoPre) {
		this.archivoPre = archivoPre;
	}

	public List<ArchivosPreinscripcionECP> getListaArchivosBorrados() {
		return listaArchivosBorrados;
	}

	public void setListaArchivosBorrados(
			List<ArchivosPreinscripcionECP> listaArchivosBorrados) {
		this.listaArchivosBorrados = listaArchivosBorrados;
	}

	public String getNitEmpresa() {
		return nitEmpresa;
	}

	public void setNitEmpresa(String nitEmpresa) {
		this.nitEmpresa = nitEmpresa;
	}

	public String getNombresRepLegal() {
		return nombresRepLegal;
	}

	public void setNombresRepLegal(String nombresRepLegal) {
		this.nombresRepLegal = nombresRepLegal;
	}

	public String getApellidosRepLegal() {
		return apellidosRepLegal;
	}

	public void setApellidosRepLegal(String apellidosRepLegal) {
		this.apellidosRepLegal = apellidosRepLegal;
	}

	public String getNumDocRepLegal() {
		return numDocRepLegal;
	}

	public void setNumDocRepLegal(String numDocRepLegal) {
		this.numDocRepLegal = numDocRepLegal;
	}

	public String getTipoDocRepLegal() {
		return tipoDocRepLegal;
	}

	public void setTipoDocRepLegal(String tipoDocRepLegal) {
		this.tipoDocRepLegal = tipoDocRepLegal;
	}

	public String getOpcionesTerminos() {
		return opcionesTerminos;
	}

	public void setOpcionesTerminos(String opcionesTerminos) {
		this.opcionesTerminos = opcionesTerminos;
	}

	public boolean isMostrarBotonGuardar() {
		return mostrarBotonGuardar;
	}

	public void setMostrarBotonGuardar(boolean mostrarBotonGuardar) {
		this.mostrarBotonGuardar = mostrarBotonGuardar;
	}

	public Hashtable<String, String> getPopayan() {
		return popayan;
	}

	public void setPopayan(Hashtable<String, String> popayan) {
		this.popayan = popayan;
	}

	public Hashtable<String, String> getPalmira() {
		return palmira;
	}

	public void setPalmira(Hashtable<String, String> palmira) {
		this.palmira = palmira;
	}

	public Hashtable<String, String> getOrinoquia() {
		return orinoquia;
	}

	public void setOrinoquia(Hashtable<String, String> orinoquia) {
		this.orinoquia = orinoquia;
	}

	public Hashtable<String, String> getSantaMarta() {
		return santaMarta;
	}

	public void setSantaMarta(Hashtable<String, String> santaMarta) {
		this.santaMarta = santaMarta;
	}

	public Hashtable<String, String> getMonteria() {
		return monteria;
	}

	public void setMonteria(Hashtable<String, String> monteria) {
		this.monteria = monteria;
	}

	public Hashtable<String, String> getMedellin() {
		return medellin;
	}

	public void setMedellin(Hashtable<String, String> medellin) {
		this.medellin = medellin;
	}

	public Hashtable<String, String> getManizales() {
		return manizales;
	}

	public void setManizales(Hashtable<String, String> manizales) {
		this.manizales = manizales;
	}

	public Hashtable<String, String> getIbague() {
		return ibague;
	}

	public void setIbague(Hashtable<String, String> ibague) {
		this.ibague = ibague;
	}

	public Hashtable<String, String> getVillavicencio() {
		return villavicencio;
	}

	public void setVillavicencio(Hashtable<String, String> villavicencio) {
		this.villavicencio = villavicencio;
	}

	public Hashtable<String, String> getBucaramanga() {
		return bucaramanga;
	}

	public void setBucaramanga(Hashtable<String, String> bucaramanga) {
		this.bucaramanga = bucaramanga;
	}

	public Hashtable<String, String> getLeticia() {
		return leticia;
	}

	public void setLeticia(Hashtable<String, String> leticia) {
		this.leticia = leticia;
	}

	public Hashtable<String, String> getBogotaUno() {
		return bogotaUno;
	}

	public void setBogotaUno(Hashtable<String, String> bogotaUno) {
		this.bogotaUno = bogotaUno;
	}

	public Hashtable<String, String> getBogotaDos() {
		return bogotaDos;
	}

	public void setBogotaDos(Hashtable<String, String> bogotaDos) {
		this.bogotaDos = bogotaDos;
	}

	public Hashtable<String, String> getSanAndres() {
		return sanAndres;
	}

	public void setSanAndres(Hashtable<String, String> sanAndres) {
		this.sanAndres = sanAndres;
	}

	public Hashtable<String, String> getValledupar() {
		return valledupar;
	}

	public void setValledupar(Hashtable<String, String> valledupar) {
		this.valledupar = valledupar;
	}

	public void cargarListasParticipantes() {
	
		orinoquia.put("68295849", "Arauca | Viernes, 06 de septiembre de 2013");
		
		manizales.put("1053780110", "Manizales | Viernes, 06 de septiembre de 2013");
	
		ibague.put("93298463", "Ibagué | Viernes, 04 de octubre de 2013");
		
		villavicencio.put("35260857", "Villavicencio | Viernes, 27 de septiembre de 2013");
		villavicencio.put("86076748", "Villavicencio | Viernes, 27 de septiembre de 2013");
		villavicencio.put("86043192", "Villavicencio | Viernes, 27 de septiembre de 2013");
				
		bogotaUno.put("51641650","Bogotá (1) | Viernes, 06 de septiembre de 2013");
		
		bogotaDos.put("51914811",	"Bogotá (2) | Viernes, 13 de septiembre de 2013");
		bogotaDos.put("51693790","Bogotá (2) | Viernes, 13 de septiembre de 2013");
		
		sanAndres.put("73095117", "San Andrés | Viernes, 06 de septiembre de 2013");

	}

}