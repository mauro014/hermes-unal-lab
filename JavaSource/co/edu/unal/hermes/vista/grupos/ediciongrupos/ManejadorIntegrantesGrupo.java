package co.edu.unal.hermes.vista.grupos.ediciongrupos;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Departamento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoCivil;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.GrupoIntersedes;
import co.edu.unal.hermes.modelo.HistoricoCambioIntegrantes;
import co.edu.unal.hermes.modelo.HistoricoCambioLiderGrupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Institucion;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.PlanEstudios;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoFormacion;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.utils.VariablesEstaticas;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorIntegrantesGrupo.
 */
public class ManejadorIntegrantesGrupo extends ManejadorBase {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6949270969149829730L;
	/** The Constant TIPO_ASOCIACION_INTERNO. */
	private static final String TIPO_ASOCIACION_INTERNO = "I";
	/** The Constant TIPO_ASOCIACION_LIDER. */
	private static final String TIPO_ASOCIACION_LIDER = "L";
	/** The Constant VARIABLE_GRUPO_SESION. */
	private static final String VARIABLE_GRUPO_SESION = "grupo";
	/** The documento. */
	private String documento;
	/** The genero item. */
	private SelectItem[] generoItem = { new SelectItem(VariablesEstaticas.GENERO_FEMENINO, "Mujer - Femenino"),
			new SelectItem(VariablesEstaticas.GENERO_MASCULINO, "Hombre - Masculino") };
	/** The grupo actual. */
	private Grupo grupoActual;
	/** The integrante codirector seleccionado. */
	private InvestigadorGrupo integranteCodirectorSeleccionado;
	/** The integrante docente seleccionado. */
	private InvestigadorGrupo integranteDocenteSeleccionado;
	/** The integrante estudiante doctorado seleccionado. */
	private InvestigadorGrupo integranteEstudianteDoctoradoSeleccionado;
	/** The integrante estudiante visitante seleccionado. */
	private InvestigadorGrupo integranteEstudianteVisitanteSeleccionado;
	/** The integrante estudiante visitante seleccionado. */
	private InvestigadorGrupo integranteAdministrativoSeleccionado;
	/** The integrante estudiante maestria seleccionado. */
	private InvestigadorGrupo integranteEstudianteMaestriaSeleccionado;
	/** The integrante estudiante pregrado seleccionado. */
	private InvestigadorGrupo integranteEstudiantePregradoSeleccionado;
	/** The integrante estudiante lider seleccionado. */
	private InvestigadorGrupo integranteEstLiderSeleccionado;
	/** The integrante externo seleccionado. */
	private InvestigadorGrupo integranteExternoSeleccionado;
	/** The investigador actual externo. */
	private boolean investigadorActualExterno;
	/** The investigador externo. */
	private InvestigadorExterno investigadorExterno;
	/** The investigador grupo actual. */
	private InvestigadorGrupo investigadorGrupoActual;
	/** The lista integrantes cod. */
	private List<InvestigadorGrupo> listaIntegrantesCod;
	/** The lista integrantes docente. */
	private List<InvestigadorGrupo> listaIntegrantesDocente;
	/** The lista integrantes estudiantes doctorado. */
	private List<InvestigadorGrupo> listaIntegrantesEstudiantesDoctorado;
	/** The lista integrantes estudiantes maestria. */
	private List<InvestigadorGrupo> listaIntegrantesEstudiantesMaestria;
	/** The lista integrantes estudiantes pregrado. */
	private List<InvestigadorGrupo> listaIntegrantesEstudiantesPregrado;
	/** The lista integrantes estudiantes visitantes. */
	private List<InvestigadorGrupo> listaIntegrantesEstudiantesVisitantes;
	/** The lista integrantes administrativos. */
	private List<InvestigadorGrupo> listaIntegrantesAdministrativos;
	/** The lista integrantes externo. */
	private List<InvestigadorGrupo> listaIntegrantesExterno;
	/** The lista integrantes ESTUDIANTES LIDERES. */
	private List<InvestigadorGrupo> listaIntegrantesEstLider;
	/** The mostrar agregar integrante. */
	private boolean mostrarAgregarIntegrante;
	/** The mostrar cambio lider. */
	private boolean mostrarCambioLider;
	/** The tipo asociacion. */
	private String tipoAsociacion;
	/** The tipo documento inv. */
	private TipoDocumento tipoDocumentoInv;
	/** The tipo documento item. */
	private SelectItem[] tipoDocumentoItem;
	/** The tipo item. */
	private SelectItem[] tipoItem;
	/** The historico cambio lider grupo. */
	private HistoricoCambioLiderGrupo historicoCambioLiderGrupo;
	/** The historico cambio integrante grupo. */
	private HistoricoCambioIntegrantes hci;
	/** The historico Integrantes. */
	private List<HistoricoCambioIntegrantes> historicoIntegrantes;
	private List<HistoricoCambioIntegrantes> historicosPendientes;
	/** The nuevo lider nuevo. */
	private boolean nuevoLiderNuevo;
	/** The posibles lideres grupo. */
	private List<SelectItem> posiblesLideresGrupo;
	/** The nuevo lider grupo. */
	private String nuevoLiderGrupo;
	private String institucionEvaluador;
	private List<Institucion> listaInstituciones;
	private ArrayList<SelectItem> listaInstitucionesItem;
	private SelectItem[] estadoCivilItem;
	private String estadoCivil;
	private SelectItem[] maxNivelEstudioItem;
	private String tipoFormacion;
	private SelectItem[] selectItemPaises;
	private String paisSel;
	private List<Departamento> listaDepartamentos;
	private SelectItem[] departamentoItem;
	private String departamentoActual;
	private List<Ciudad> listaCiudades;
	private SelectItem[] ciudadItem;
	private String ciudadActual;
	private SelectItem[] areaCienciaItems;
	private String areaCienciaInv;
	private SelectItem[] subAreaCienciaItems;
	private String subAreaCienciaInv;
	private static final String DOMINIO_AREA_CIENCIA = "AREA_CIENCIA";
	private static final String DOMINIO_SUB_AREA_CIENCIA = "SUB_AREA_CIENCIA_FM";
	private boolean lider;
	private boolean addEstLider;
	private boolean huboCambioLider;
	private List<InvestigadorGrupo> listaEgresados;

	/**
	 * Instantiates a new manejador integrantes grupo.
	 */
	public ManejadorIntegrantesGrupo() {
		investigadorGrupoActual = new InvestigadorGrupo();
		huboCambioLider = false;
		historicosPendientes = new ArrayList<HistoricoCambioIntegrantes>();
		tipoAsociacion = TIPO_ASOCIACION_INTERNO;
		cargarListaTipos(2);
		cambiarTipoAsociacion();
		investigadorGrupoActual.setTipo(InvestigadorGrupo.LIDER);
		grupoActual = (Grupo) sesion.getAttribute(VARIABLE_GRUPO_SESION);
		grupoActual = servicioGrupo.obtenerGrupoInvestigadores(grupoActual.getId());
		List<TipoDocumento> listaTipoDocumento = servicioGeneral.obtenerTiposDeDocumento();
		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}
		tipoDocumentoInv = listaTipoDocumento.get(0);

		List<EstadoCivil> listaEstadoCivil = servicioGeneral.obtenerTiposDeEstadoCivil();
		estadoCivilItem = new SelectItem[listaEstadoCivil.size()];
		for (int i = 0; i < listaEstadoCivil.size(); i++) {
			EstadoCivil ec = listaEstadoCivil.get(i);
			estadoCivilItem[i] = new SelectItem(ec.getId(), ec.getNombre());
		}
		List<TipoFormacion> listaTipoFormacion = servicioGeneral.obtenerTiposDeFormacion();
		maxNivelEstudioItem = new SelectItem[listaTipoFormacion.size()];
		for (int i = 0; i < listaTipoFormacion.size(); i++) {
			TipoFormacion tf = listaTipoFormacion.get(i);
			maxNivelEstudioItem[i] = new SelectItem(tf.getId(), tf.getNombre());
		}
		cargarListasIntegrantes();
		consultarListaInstituciones();
		cargarPaises();
		lider = validarPermisos();
		List<DominioDetalle> listaAreaCiencia = servicioGeneral.obtenerDominioDetalle(DOMINIO_AREA_CIENCIA, false);
		areaCienciaItems = crearListaItems(listaAreaCiencia);
		validarTipoIntegrante();
	}

	private void cargarPaises() {
		List<Pais> listaPaises = servicioGeneral.obtenerListaObjetosOrdenadosAscG(Pais.class, "nombre");
		setSelectItemPaises(new SelectItem[listaPaises.size()]);
		for (int i = 0; i < listaPaises.size(); i++) {
			Pais p = listaPaises.get(i);
			getSelectItemPaises()[i] = new SelectItem(p.getId(), p.getNombre());
		}
	}

	private void consultarListaInstituciones() {
		try {
			listaInstituciones = servicioGeneral.obtenerObjetosLimitado(Institucion.class,
					"select #id ins.id, #nombre ins.nombre  " + "from Institucion ins " + "order by ins.nombre asc");

			listaInstitucionesItem = new ArrayList<SelectItem>();
			for (int i = 0; i < listaInstituciones.size(); i++) {
				Institucion institucion = (Institucion) listaInstituciones.get(i);
				listaInstitucionesItem.add(new SelectItem(institucion.getId(), institucion.getNombre()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adicionar integrante.
	 */
	public void adicionarIntegrante() {
		if(esCadenaVacia(tipoAsociacion)) {
			mensajeError("Debe seleccionar un tipo de asociación con la Universidad para el integrante.");
			return;
		}
		if (!validarDocumentoParticipante(tipoDocumentoInv.getId(), documento)) {
			mensajeError("El número de documento ingresado no es válido para el tipo de documento seleccionado.");
			return;
		}
		boolean integranteAgrado = false;
		// Se valida si se va a agregar un investigador con datos nuevos
		if (!investigadorActualExterno) {
			// Se crea objeto id para busquedas
			IdPersona id = new IdPersona();
			id.setTipoDocumento(tipoDocumentoInv.getId());
			id.setDocumento(documento);
			// Se valida si el investigador ya existe en la vista
			if (buscarInvestigador(id) != null) {
				mensajeError("El investigador ya se encuentra asociado al grupo de investigación.");
			} else {
				// Se obtiene investigador desde base de datos
				Investigador nuevoInvestigadorAgregar = servicioPersona.obtenerInvestigador(id);
				if (tipoAsociacion.equals(TIPO_ASOCIACION_INTERNO) || tipoAsociacion.equals(TIPO_ASOCIACION_LIDER)) {
					// Si es interno o lider
					integranteAgrado = agregarIntegranteInterno(nuevoInvestigadorAgregar, id);
				} else {
					if (nuevoInvestigadorAgregar instanceof InvestigadorInterno
							&& !esCadenaVacia(nuevoInvestigadorAgregar.getInterno())
							&& nuevoInvestigadorAgregar.getInterno().equals("S")) {
						mensajeError(
								"No se puede agregar al integrante Externo, ya que el documento se encuentra asociado a un Investigador Interno.");
					} else {
						// Si es externo
						integranteAgrado = agregarIntegranteExterno(nuevoInvestigadorAgregar, 1);
					}
				}
			}
		} else if (validarExterno()) {
			// Si los datos del extenro son validos se crea
			TipoFormacion tf = new TipoFormacion();
			tf.setId(tipoFormacion);
			investigadorExterno.setTipoFormacion(tf);
			EstadoCivil ec = new EstadoCivil();
			ec.setId(estadoCivil);
			investigadorExterno.setEstadoCivil(ec);
			investigadorExterno.setPaisOrigen(paisSel);
			Ciudad c = new Ciudad();
			c.setId(ciudadActual);
			investigadorExterno.setCiudadNacimiento(c);
			investigadorExterno.setAreaOcde(areaCienciaInv);
			investigadorExterno.setSubareaOcde(subAreaCienciaInv);
			investigadorExterno.setInterno(Investigador.EXTERNO);
			investigadorExterno.setEvaluador(Investigador.NO_EVALUADOR);
			List instituciones = servicioGeneral.obtenerObjetos(Institucion.class,
					"select i from Institucion i where i.id = '" + institucionEvaluador + "'");
			investigadorExterno.setInstitucion((Institucion) (instituciones.get(0)));
			CategoriaInvestigador categoria = new CategoriaInvestigador();
			categoria.setId(Long.parseLong(CategoriaInvestigador.IDEXTERNO));
			investigadorExterno.setCategoriaInvestigador(categoria);
			investigadorExterno.setEsFuncionario("N");
			// Se guarda
			try {
				servicioPersona.guardarInvestigador(investigadorExterno, false);
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				Persona per = servicioPersona.obtenerPersona(investigadorExterno.getId());
				if(per==null || per.getId()== null) {
					servicioPersona.insertarNuevaPersona(investigadorExterno);
				}
				
				Investigador i = servicioPersona.obtenerInvestigador(investigadorExterno.getId());
				if (i == null || i.getId()==null) {
					servicioPersona.insertarInvestigador(investigadorExterno);
				}else {
					servicioPersona.guardarInvestigador(investigadorExterno, false);
				}
				
				InvestigadorExterno ie = servicioPersona.buscarInvestigadorExternoId(investigadorExterno.getId().getDocumento(),investigadorExterno.getId().getTipoDocumento());
				if(ie==null || ie.getId()==null || esCadenaVacia(ie.getId().getDocumento())) {
					servicioPersona.insertarExterno(investigadorExterno);
				}
			}
			// Se obtiene investigador desde base de datos
			Investigador nuevoInvestigadorAgregar = servicioPersona.obtenerInvestigador(investigadorExterno.getId());
			// Se agrega
			integranteAgrado = agregarIntegranteExterno(nuevoInvestigadorAgregar, 2);
		}
		cargarListasIntegrantes();
		if (integranteAgrado) {
			mensajeInfo("El integrante ha sido agregado al grupo de investigación.");
		}
		// this.documento = "";
	}

	/**
	 * Agregar docente o lider.
	 *
	 * @param nuevoInvestigadorAgregar the nuevo investigador agregar
	 * @param esInvestigadorNuevo      the es investigador nuevo
	 * @return true, if successful
	 */
	private boolean agregarDocenteOLiderInterno(Investigador nuevoInvestigadorAgregar, boolean esInvestigadorNuevo) {
		// Se valida si es docente activo
		if (nuevoInvestigadorAgregar != null && "S".equals(nuevoInvestigadorAgregar.getInterno())) {
			// Si se esta agregando un nuevo lider el lider
			// anterior se convierte en docente
			// investigador.
			if (tipoAsociacion.equals(TIPO_ASOCIACION_LIDER)) {

				historicoCambioLiderGrupo = new HistoricoCambioLiderGrupo();
				historicoCambioLiderGrupo.setFecha(new Date());
				historicoCambioLiderGrupo.setGrupo(grupoActual);
				historicoCambioLiderGrupo.setLiderAnterior(grupoActual.getLider().getInvestigador());

				listaIntegrantesDocente.add(grupoActual.getLider());
				grupoActual.getLider().setTipo(InvestigadorGrupo.DOCENTE);

				investigadorGrupoActual.setTipo(InvestigadorGrupo.LIDER);
			} else {
				// Si se agrega un docente
				investigadorGrupoActual.setTipo(InvestigadorGrupo.DOCENTE);
			}
			finalizarAdicionIntegranteValidado(nuevoInvestigadorAgregar, esInvestigadorNuevo);
			return true;
		} else {
			mensajeError("El investigador no se encuentra como docente " + "interno activo a la Universidad");
		}
		return false;
	}

	/**
	 * Agregar estudiante interno.
	 *
	 * @param nuevoInvestigadorAgregar the nuevo investigador agregar
	 * @param id                       the id
	 * @return true, if successful
	 */
	private boolean agregarEstudianteInterno(Investigador nuevoInvestigadorAgregar, IdPersona id) {

		Estudiante e = servicioPersona.obtenerEstudiante(id);
		String tipoEstudiante = investigadorGrupoActual.getTipo();
		if (tipoEstudiante.equals(InvestigadorGrupo.ESTUDIANTE_VISITANTE)) {
			if (e.getPlan().getTipo() != 8L) {
				mensajeError("ERROR: El estudiante no pertenece a un programa de visitantes.");
				return false;
			}
		} else {
			if (e.getPlan().getTipo() == 8L) {
				mensajeError("ERROR: El estudiante pertenece a un programa de visitantes.");
				return false;
			}
		}
		// Si no existe investigador de este estudiante, se agrega
		if (nuevoInvestigadorAgregar != null) {
			finalizarAdicionIntegranteValidado(nuevoInvestigadorAgregar, true);
		} else {
			if (servicioPersona.obtenerPersona(id) == null) {
				servicioPersona.insertarNuevaPersona(e.convertirAPersona());
			}

			Investigador investigadorNuevo = new Investigador();
			investigadorNuevo.setEvaluador("N");
			investigadorNuevo.setEsFuncionario("N");
			investigadorNuevo.setInterno("N");
			investigadorNuevo.setId(id);
			investigadorNuevo.setInterno("N");
			CategoriaInvestigador categoria = new CategoriaInvestigador();
			categoria.setId(3L);
			investigadorNuevo.setCategoriaInvestigador(categoria);
			servicioPersona.insertarInvestigador(investigadorNuevo);

			// Se obtiene el investigador ingerno
			InvestigadorInterno investigadoriInterno = servicioPersona.obtenerInvestigadorInterno(id);

			// Si no existe el interno se agrega
			if (investigadoriInterno == null) {
				InvestigadorInterno nuevoInvestigadorInterno = new InvestigadorInterno();
				nuevoInvestigadorInterno.setId(id);
				nuevoInvestigadorInterno.setDependencia(e.getDependencia());
				servicioPersona.insertaInterno(nuevoInvestigadorInterno);
				InvestigadorInterno investigadorInsertado = servicioPersona.obtenerInvestigadorInterno(id);
				if (investigadorInsertado == null) {
					mensajeError("Ha habido un error al agregar al estudiante.");
					return false;
				} else {
					investigadorNuevo = investigadorInsertado;
				}
			} else {
				investigadorNuevo = investigadoriInterno;
			}

			finalizarAdicionIntegranteValidado(investigadorNuevo, true);
		}
		
		if(tipoEstudiante.equals(InvestigadorGrupo.ESTUDIANTE_LIDER))
			crearUsuarioEstudianteAsistenteLider(InvestigadorGrupo.ESTUDIANTE_LIDER, id);
		
		return true;

	}
	
	private void crearUsuarioEstudianteAsistenteLider(String tipo, IdPersona id) {
		try {
			PersonaRol pr = new PersonaRol();
			pr.setDocumento(id.getDocumento());
			pr.setTipoDocumento(id.getTipoDocumento());
			pr.setNombre(tipo);
			pr.setFechaInicioRol(getToday());
			Calendar c = Calendar.getInstance();
			c.add(Calendar.YEAR, 1);
			pr.setFechaFinRol(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/" + c.get(Calendar.YEAR)));
			servicioGeneral.guardarObjeto(pr);
		} catch (Exception ex) {
			System.out.println("Ya tenía el rol Estudiante Líder");
		}
	}

	/**
	 * Agregar estudiante.
	 *
	 * @param investigadorGrupo the investigador grupo
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	private boolean agregarEstudianteListas(InvestigadorGrupo investigadorGrupo) throws SQLException {
		if(investigadorGrupo.getTipoVinculacion().equals(InvestigadorGrupo.INTERNO)) {
			Estudiante estudiante = buscarEstudiante(investigadorGrupo);
			if (estudiante != null && estudiante.getPlan() != null) {
				investigadorGrupo.setNombrePlanEstudiante(estudiante.getPlan().getNombre());
				if (investigadorGrupo.getTipo().equals(InvestigadorGrupo.ESTUDIANTE_VISITANTE)) {
					listaIntegrantesEstudiantesVisitantes.add(investigadorGrupo);
					return true;
				} else if (estudiante.getPlan().getTipo().equals(PlanEstudios.DOCTORADO)) {
					listaIntegrantesEstudiantesDoctorado.add(investigadorGrupo);
					return true;
				} else if (estudiante.getPlan().getTipo().equals(PlanEstudios.MAESTRIA)
						|| estudiante.getPlan().getTipo().equals(PlanEstudios.ESPECIALIDAD)
						|| estudiante.getPlan().getTipo().equals(PlanEstudios.ESPECIALIZACION)) {
					listaIntegrantesEstudiantesMaestria.add(investigadorGrupo);
					return true;
				} else if (estudiante.getPlan().getTipo().equals(PlanEstudios.PREGRADO)) {
					listaIntegrantesEstudiantesPregrado.add(investigadorGrupo);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Agregar integrante externo.
	 *
	 * @param nuevoInvestigadorAgregar the nuevo investigador agregar
	 * @return true, if successful
	 */
	private boolean agregarIntegranteExterno(Investigador nuevoInvestigadorAgregar, int process) {
		String tipoInvGrupo = investigadorGrupoActual.getTipo();
		switch (process) {
		case 1:
			if (nuevoInvestigadorAgregar != null) {
				investigadorExterno = new InvestigadorExterno();
				investigadorExterno.setId(
						nuevoInvestigadorAgregar.getId() == null ? new IdPersona() : nuevoInvestigadorAgregar.getId());
				investigadorExterno.setNombre1(
						nuevoInvestigadorAgregar.getNombre1() == null ? "" : nuevoInvestigadorAgregar.getNombre1());
				investigadorExterno.setNombre2(
						nuevoInvestigadorAgregar.getNombre2() == null ? "" : nuevoInvestigadorAgregar.getNombre2());
				investigadorExterno.setApellido1(
						nuevoInvestigadorAgregar.getApellido1() == null ? "" : nuevoInvestigadorAgregar.getApellido1());
				investigadorExterno.setApellido2(
						nuevoInvestigadorAgregar.getApellido2() == null ? "" : nuevoInvestigadorAgregar.getApellido2());
				investigadorExterno.setGenero(
						nuevoInvestigadorAgregar.getGenero() == null ? "" : nuevoInvestigadorAgregar.getGenero());
				tipoFormacion = (nuevoInvestigadorAgregar.getTipoFormacion() == null ? ""
						: nuevoInvestigadorAgregar.getTipoFormacion().getId());
				estadoCivil = (nuevoInvestigadorAgregar.getEstadoCivil() == null ? ""
						: nuevoInvestigadorAgregar.getEstadoCivil().getId());
				investigadorExterno
						.setFechaNacimiento(nuevoInvestigadorAgregar.getFechaNacimiento() == null ? getToday()
								: nuevoInvestigadorAgregar.getFechaNacimiento());
				paisSel = (nuevoInvestigadorAgregar.getPaisOrigen() == null ? ""
						: nuevoInvestigadorAgregar.getPaisOrigen());
				if (!paisSel.equals("")) {
					revisarPais();
				}
				if (paisSel != null && !paisSel.equals("") && paisSel.equals("CO")) {
					departamentoActual = (nuevoInvestigadorAgregar.getCiudadNacimiento() == null ? ""
							: nuevoInvestigadorAgregar.getCiudadNacimiento().getDepartamento().getId());
					cambiarDepartamento();
				}
				ciudadActual = (nuevoInvestigadorAgregar.getCiudadNacimiento() == null ? ""
						: nuevoInvestigadorAgregar.getCiudadNacimiento().getId());
				investigadorExterno.setEmail(
						nuevoInvestigadorAgregar.getEmail() == null ? "" : nuevoInvestigadorAgregar.getEmail());
				investigadorExterno.setTelefono(
						nuevoInvestigadorAgregar.getTelefono() == null ? "" : nuevoInvestigadorAgregar.getTelefono());
				institucionEvaluador = (nuevoInvestigadorAgregar.getInstitucion() == null ? ""
						: nuevoInvestigadorAgregar.getInstitucion().getId());
				areaCienciaInv = (nuevoInvestigadorAgregar.getAreaOcde() == null ? ""
						: nuevoInvestigadorAgregar.getAreaOcde());
				cambiarAreaInv();
				subAreaCienciaInv = (nuevoInvestigadorAgregar.getSubareaOcde() == null ? ""
						: nuevoInvestigadorAgregar.getSubareaOcde());
				investigadorActualExterno = true;
			} else {
				// Si no existe se crea un nuevo investigador externo
				investigadorExterno = new InvestigadorExterno();
				investigadorExterno.setInstitucion(new Institucion());
				investigadorExterno.setId(new IdPersona());
				CategoriaInvestigador ci = new CategoriaInvestigador();
				if (investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.DOCENTE)) {
					ci.setId(new Long(CategoriaInvestigador.IDDOCENTE));
				}
				if (investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.ESTUDIANTE)
						|| investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.ESTUDIANTE_POSGRADO)
						|| investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.ESTUDIANTE_PREGRADO)) {
					ci.setId(new Long(CategoriaInvestigador.IDESTUDIANTE));
				}
				if (investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.EXTERNO)) {
					ci.setId(new Long(CategoriaInvestigador.IDEXTERNO));
				}
				if (investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.CODIRECTOR)) {
					ci.setId(new Long(CategoriaInvestigador.IDCODIRECTOR));
				}
				investigadorExterno.setCategoriaInvestigador(ci);
				investigadorExterno.getId().setTipoDocumento(tipoDocumentoInv.getId());
				investigadorExterno.getId().setDocumento(documento.trim());
				investigadorActualExterno = true;
			}
			if(tipoInvGrupo.equals(InvestigadorGrupo.ASISTENTE_LIDER))
				crearUsuarioEstudianteAsistenteLider(InvestigadorGrupo.ASISTENTE_LIDER, investigadorExterno.getId());
			return false;
		case 2:
			// El investigador ya existe como externo
			investigadorGrupoActual.setTipoVinculacion("E");
			finalizarAdicionIntegranteValidado(nuevoInvestigadorAgregar, true);
			if(tipoInvGrupo.equals(InvestigadorGrupo.ASISTENTE_LIDER))
				crearUsuarioEstudianteAsistenteLider(InvestigadorGrupo.ASISTENTE_LIDER, new IdPersona(documento.trim(), tipoDocumentoInv.getId()));
			return true;
		default:
			return false;
		}
	}

	/**
	 * Agregar integrante interno.
	 *
	 * @param nuevoInvestigadorAgregar the nuevo investigador agregar
	 * @param id                       the id
	 * @return true, if successful
	 */
	private boolean agregarIntegranteInterno(Investigador nuevoInvestigadorAgregar, IdPersona id) { //CAZ
		investigadorGrupoActual.setTipoVinculacion("I");
		if (investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.DOCENTE)) {

			return agregarDocenteOLiderInterno(nuevoInvestigadorAgregar, true);

		} else if (investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.CODIRECTOR)) {
			if (nuevoInvestigadorAgregar != null && "N".equals(nuevoInvestigadorAgregar.getEsFuncionario())) {
				finalizarAdicionIntegranteValidado(nuevoInvestigadorAgregar, true);
				return true;
			}
			// Si no existe no se puede agregar como cordirector.
			mensajeError("No se encuentra información del investigador o no tiene vinculación de DOCENTE.");
		} else if (investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.ESTUDIANTE)
				|| investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.ESTUDIANTE_LIDER)
				|| investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.ESTUDIANTE_VISITANTE)) {

			Estudiante e = servicioPersona.obtenerEstudiante(id);
			if (e != null && e.getInterno() != null && e.getInterno().equals("S")) {
				return agregarEstudianteInterno(nuevoInvestigadorAgregar, id);
			} else {
				mensajeError("El estudiante no ha sido encontrado o no se encuentra activo");
			}

		} else if (investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.ADMINISTRATIVO)) {
			if (nuevoInvestigadorAgregar != null && "S".equals(nuevoInvestigadorAgregar.getEsFuncionario())) {
				finalizarAdicionIntegranteValidado(nuevoInvestigadorAgregar, true);
				return true;
			}
			// Si no existe no se puede agregar como administrativo.
			mensajeError("No se encuentra información del administrativo o no tiene vinculación de ADMINISTRATIVO.");
		} else if (investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.DOCENTE_IPARM)) {
//			Boolean esDocenteIparm = false;
			if(!esNulo(nuevoInvestigadorAgregar) && !esNulo(nuevoInvestigadorAgregar.getTipoVinculacion())) {
				String tVinculacion = nuevoInvestigadorAgregar.getTipoVinculacion().getId();
				if(tVinculacion.equals("20") || tVinculacion.equals("21")) {
					finalizarAdicionIntegranteValidado(nuevoInvestigadorAgregar, true);
					return true;
				}
			}
			// Si no existe no se puede agregar como Docente de Enseñanza educación básica y media.
			mensajeError("No se encuentra información del Docente de Enseñanza educación básica y media o no tiene vinculación de correspondiente.");
		}
		return false;
	}

	/**
	 * Borrar manejador integrantes.
	 */
	private void borrarManejadorIntegrantes() {
		sesion.removeAttribute("manejadorIntegrantesGrupo");
	}

	/**
	 * Buscar estudiante.
	 *
	 * @param ig the ig
	 * @return the estudiante
	 * @throws SQLException the SQL exception
	 */
	public Estudiante buscarEstudiante(InvestigadorGrupo ig) throws SQLException {
		Estudiante estudiante;
		if (ig != null && ig.getInvestigador().getId() != null) {
			List<Estudiante> est = servicioGeneral.obtenerObjetos(Estudiante.class,
					"from Estudiante e where e.id.documento='" + ig.getInvestigador().getId().getDocumento()
							+ "' and e.id.tipoDocumento='" + ig.getInvestigador().getId().getTipoDocumento() + "'");
			if (!esListaVacia(est)) {
				estudiante = est.get(0);
				return estudiante;
			}
		}

		return null;
	}

	/**
	 * Buscar investigador.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	private InvestigadorGrupo buscarInvestigador(IdPersona id) {
		// BUSCA UN INVESTIGADOR DE ACUERDO A SU ID
		if (id != null) {
			Iterator<InvestigadorGrupo> i = grupoActual.getInvestigadoresGrupo().iterator();
			while (i.hasNext()) {
				InvestigadorGrupo investigadorGrupo = i.next();
				// Se verifican documentos de identidad
				if (investigadorGrupo != null
						&& id.getDocumento().equals(investigadorGrupo.getInvestigador().getId().getDocumento())
						&& id.getTipoDocumento()
								.equals(investigadorGrupo.getInvestigador().getId().getTipoDocumento())) {
					return investigadorGrupo;
				}
			}
		}
		return null;
	}

	/**
	 * Cambiar lider.
	 */
	public void cambiarLider() {
		Iterator<InvestigadorGrupo> i = listaIntegrantesDocente.iterator();
		Investigador investigadorNuevo = null;
		tipoAsociacion = TIPO_ASOCIACION_LIDER;
		InvestigadorGrupo investigadorGrupo = null;
		while (i.hasNext()) {
			investigadorGrupo = i.next();
			if (investigadorGrupo.getId().toString().equals(nuevoLiderGrupo)) {
				investigadorNuevo = investigadorGrupo.getInvestigador();
				investigadorGrupoActual = investigadorGrupo;
				break;
			}
		}
		if (investigadorNuevo != null) {
			agregarDocenteOLiderInterno(investigadorNuevo, false);
			listaIntegrantesDocente.remove(investigadorGrupo);
		}
	}

	/**
	 * Cambiar tipo asociacion.
	 */
	public void cambiarTipoAsociacion() {
		if (tipoAsociacion.equals(TIPO_ASOCIACION_INTERNO)) {
			cargarListaTipos(2);
			if (investigadorActualExterno) {
				investigadorActualExterno = false;
			}
		} else {
			cargarListaTipos(1);
		}
	}

	public void validarTipoIntegrante() {
		if (investigadorGrupoActual.getTipo().equals("AL")) {
			setAddEstLider(true);
		} else {
			setAddEstLider(false);
		}
		return;
	}

	/**
	 * Cargar listas integrantes.
	 */
	public void cargarListasIntegrantes() {
		listaIntegrantesDocente = new ArrayList<InvestigadorGrupo>();
		listaIntegrantesCod = new ArrayList<InvestigadorGrupo>();
		listaIntegrantesEstudiantesDoctorado = new ArrayList<InvestigadorGrupo>();
		listaIntegrantesEstudiantesMaestria = new ArrayList<InvestigadorGrupo>();
		listaIntegrantesEstudiantesPregrado = new ArrayList<InvestigadorGrupo>();
		setListaIntegrantesEstudiantesVisitantes(new ArrayList<InvestigadorGrupo>());
		setListaIntegrantesAdministrativos(new ArrayList<InvestigadorGrupo>());
		listaIntegrantesExterno = new ArrayList<InvestigadorGrupo>();
		listaIntegrantesEstLider = new ArrayList<InvestigadorGrupo>();
		listaEgresados = new ArrayList<InvestigadorGrupo>();
		String tipoInvestigador;
		Iterator<InvestigadorGrupo> it = grupoActual.getInvestigadoresGrupo().iterator();
		while (it.hasNext()) {
			InvestigadorGrupo ig = it.next();
			tipoInvestigador = ig.getTipo();
			if (InvestigadorGrupo.EXTERNO.equals(ig.getTipoVinculacion()) 
				&& !InvestigadorGrupo.EGRESADO.equals(tipoInvestigador) 
				&& ig.getInvestigador().getAreaOcde()!=null
			) {
				ig.setNombreAreaOcde(nombreAreaOcde(ig.getInvestigador().getAreaOcde()));
				ig.setNombreSubAreaOcde(nombreSubAreaOcde(ig.getInvestigador().getAreaOcde(), ig.getInvestigador().getSubareaOcde()));
				if(esCadenaVacia(ig.getInvestigador().getInstitucion().getNombre())) {
					InvestigadorExterno ie = servicioPersona.buscarInvestigadorExternoId(ig.getInvestigador().getId().getDocumento(),ig.getInvestigador().getId().getTipoDocumento());
					if(ie!=null && ie.getId()!=null && ie.getInstitucion()!=null && !esCadenaVacia(ie.getInstitucion().getId())) {
						List instituciones = servicioGeneral.obtenerObjetos(Institucion.class,
								"select i from Institucion i where i.id = '" + ie.getInstitucion().getId() + "'");
						Institucion in = (Institucion) (instituciones.get(0));
						ig.setNombreInstitucion(in.getNombre());		
					}
				}else {
					ig.setNombreInstitucion(ig.getInvestigador().getInstitucion().getNombre());
				}
				
				listaIntegrantesExterno.add(ig);
			}else if (InvestigadorGrupo.DOCENTE.equals(tipoInvestigador)) {
				listaIntegrantesDocente.add(ig);
			} else if (InvestigadorGrupo.CODIRECTOR.equals(tipoInvestigador)) {
				listaIntegrantesCod.add(ig);
			}  else if (InvestigadorGrupo.ADMINISTRATIVO.equals(tipoInvestigador)) {
				getListaIntegrantesAdministrativos().add(ig);
			} else if ((InvestigadorGrupo.ESTUDIANTE.equals(tipoInvestigador)
					|| InvestigadorGrupo.ESTUDIANTE_VISITANTE.equals(tipoInvestigador)) && ig != null) {
				try {
					if (!agregarEstudianteListas(ig)) {
						listaIntegrantesExterno.add(ig);
					}
				} catch (SQLException se) {
					mensajeError("El estudiante no ha sido encontrado.");
				}
			} else if (InvestigadorGrupo.ESTUDIANTE_LIDER.equals(tipoInvestigador) && ig != null) {
				Estudiante estudiante = null;
				try {
					estudiante = buscarEstudiante(ig);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (estudiante != null) {
					ig.setNombrePlanEstudiante(estudiante.getPlan().getNombre());
				}
				listaIntegrantesEstLider.add(ig);
			} else if (InvestigadorGrupo.EGRESADO.equals(tipoInvestigador) && ig != null) {
				listaEgresados.add(ig);
			} else {
				if (!InvestigadorGrupo.LIDER.equals(tipoInvestigador)) {
					listaIntegrantesExterno.add(ig);
				}
			}
		}
	}

	/**
	 * Cargar lista tipos.
	 *
	 * @param tipo the nivel
	 */
//	public void cargarListaTipos(int tipo) {
//		String[][] tipos = { 
//				{ "C", "Co-líder" }, 
//				{ "D", "Docente" }, 
//				{ "AD", "Administrativo" },
//				{ "E", "Investigador" }, 
//				{ "A", "Estudiante" }, 
//				{ "AV", "Estudiante Visitante" }
//		};
//		if (tipo == 2) {
//			tipos[3][0] = "AL";
//			tipos[3][1] = "Estudiante Líder";
//			tipoItem = new SelectItem[tipos.length];
//		} else {
//			tipos[2][0] = "O";
//			tipos[2][1] = "Egresado";
//			tipos[5][0] = "P";
//			tipos[5][1] = "Pensionado";
//			tipos[6][0] = "ADL";
//			tipos[6][1] = "Asistente Líder";
//			tipoItem = new SelectItem[tipos.length];
//		}
//		for (int i = 0; i < tipoItem.length; i++) {
//			tipoItem[i] = new SelectItem(tipos[i][0], tipos[i][1]);
//		}
//	}
	
	public void cargarListaTipos(int tipo) {
        // Usamos una lista para almacenar los arreglos de tipo {codigo, descripcion}
        List<String[]> tipos = new ArrayList<String[]>();

        // Añadir tipos base
        tipos.add(new String[]{"C", "Co-líder"});
        tipos.add(new String[]{"D", "Docente"});
        tipos.add(new String[]{"AD", "Administrativo"});
        tipos.add(new String[]{"E", "Investigador"});
        tipos.add(new String[]{"A", "Estudiante"});
        tipos.add(new String[]{"AV", "Estudiante Visitante"});

        // Modificar tipos según la condición
        if (tipo == 2) {
            // Modificar el valor del tipo en la posición 3 (Investigador -> Estudiante Líder)
            tipos.set(3, new String[]{"AL", "Estudiante Líder"});
            tipos.add(new String[]{"DI", "Docente de Enseñanza educación básica y media"});
        } else {
            // Modificar varios valores según otra condición
            tipos.set(2, new String[]{"O", "Egresado"});           // Administrativo -> Egresado
            tipos.set(5, new String[]{"P", "Pensionado"});          // Estudiante Visitante -> Pensionado
//            tipos.add(new String[]{"ADL", "Asistente Líder"});      // Añadir nuevo tipo
        }
        
        tipoItem = new SelectItem[tipos.size()];

        // Convertir la lista de arreglos a un arreglo de SelectItem[]
//        SelectItem[] tipoItem = new SelectItem[tipos.size()];
        for (int i = 0; i < tipos.size(); i++) {
            tipoItem[i] = new SelectItem(tipos.get(i)[0], tipos.get(i)[1]);
        }
    }

	/**
	 * Cargar posibles lideres grupo.
	 *
	 * @return the list
	 */
	private List<SelectItem> cargarPosiblesLideresGrupo() {
		Iterator<InvestigadorGrupo> i = listaIntegrantesDocente.iterator();
		List<SelectItem> listaPosiblesLideres = new ArrayList<SelectItem>();
		while (i.hasNext()) {
			InvestigadorGrupo investigadorGrupo = i.next();
			String consula = "from Investigador i where i.id.documento = '"
					+ investigadorGrupo.getInvestigador().getId().getDocumento() + "' and i.id.tipoDocumento = '"
					+ investigadorGrupo.getInvestigador().getId().getTipoDocumento() + "'";
			List<Investigador> investigadores = servicioGeneral.obtenerObjetos(Investigador.class, consula);
			if (!esListaVacia(investigadores)) {
				Investigador investigador = investigadores.get(0);
				if (!esCadenaVacia(investigador.getInterno())
						&& investigador.getInterno().equals(Investigador.INTERNO)) {
					listaPosiblesLideres.add(new SelectItem(investigadorGrupo.getId(),
							investigadorGrupo.getInvestigador().getNombreCompletoMinusculas()));
				}
			}

		}
		return listaPosiblesLideres;
	}

	/**
	 * Eliminar integrante codirector.
	 */
	@SuppressWarnings("deprecation")
	public void eliminarIntegranteCodirector() {
		grupoActual.borrarInvestigadorGrupo(integranteCodirectorSeleccionado);
		List objetos = servicioGeneral.obtenerObjetos(
				"select h from HistoricoCambioIntegrantes h where h.grupo.id = '" + grupoActual.getId() + "'");
		if (objetos != null && objetos.size() > 0) {
			for (Object object : objetos) {
				hci = (HistoricoCambioIntegrantes) object;
				if (hci.getIntegrante().equals(integranteCodirectorSeleccionado.getInvestigador())
						&& hci.getFechaRetiro() == null) {
					hci.setFechaRetiro(new Date());
					servicioGeneral.guardarObjeto(hci);
					break;
				}
			}
		}
		cargarListasIntegrantes();
	}

	/**
	 * Eliminar integrante docente.
	 */
	@SuppressWarnings("deprecation")
	public void eliminarIntegranteDocente() {
		grupoActual.borrarInvestigadorGrupo(integranteDocenteSeleccionado);
		List objetos = servicioGeneral.obtenerObjetos(
				"select h from HistoricoCambioIntegrantes h where h.grupo.id = '" + grupoActual.getId() + "'");
		if (objetos != null && objetos.size() > 0) {
			for (Object object : objetos) {
				hci = (HistoricoCambioIntegrantes) object;
				if (hci.getIntegrante().equals(integranteDocenteSeleccionado.getInvestigador())
						&& hci.getFechaRetiro() == null) {
					hci.setFechaRetiro(new Date());
					servicioGeneral.guardarObjeto(hci);
					break;
				}
			}
		}
		cargarListasIntegrantes();
	}

	/**
	 * Eliminar integrante estudiante doctorado.
	 */
	@SuppressWarnings("deprecation")
	public void eliminarIntegranteEstudianteDoctorado() {
		grupoActual.borrarInvestigadorGrupo(integranteEstudianteDoctoradoSeleccionado);
		List objetos = servicioGeneral.obtenerObjetos(
				"select h from HistoricoCambioIntegrantes h where h.grupo.id = '" + grupoActual.getId() + "'");
		if (objetos != null && objetos.size() > 0) {
			for (Object object : objetos) {
				hci = (HistoricoCambioIntegrantes) object;
				if (hci.getIntegrante().equals(integranteEstudianteDoctoradoSeleccionado.getInvestigador())
						&& hci.getFechaRetiro() == null) {
					hci.setFechaRetiro(new Date());
					servicioGeneral.guardarObjeto(hci);
					break;
				}
			}
		}
		cargarListasIntegrantes();
	}

	@SuppressWarnings("deprecation")
	public void eliminarIntegranteAdministrativo() {
		grupoActual.borrarInvestigadorGrupo(integranteAdministrativoSeleccionado);
		List objetos = servicioGeneral.obtenerObjetos(
				"select h from HistoricoCambioIntegrantes h where h.grupo.id = '" + grupoActual.getId() + "'");
		if (objetos != null && objetos.size() > 0) {
			for (Object object : objetos) {
				hci = (HistoricoCambioIntegrantes) object;
				if (hci.getIntegrante().equals(integranteAdministrativoSeleccionado.getInvestigador())
						&& hci.getFechaRetiro() == null) {
					hci.setFechaRetiro(new Date());
					servicioGeneral.guardarObjeto(hci);
					break;
				}
			}
		}
		cargarListasIntegrantes();
	}

	@SuppressWarnings("deprecation")
	public void eliminarIntegranteEstudianteVisitante() {
		grupoActual.borrarInvestigadorGrupo(integranteEstudianteVisitanteSeleccionado);
		List objetos = servicioGeneral.obtenerObjetos(
				"select h from HistoricoCambioIntegrantes h where h.grupo.id = '" + grupoActual.getId() + "'");
		if (objetos != null && objetos.size() > 0) {
			for (Object object : objetos) {
				hci = (HistoricoCambioIntegrantes) object;
				if (hci.getIntegrante().equals(integranteEstudianteVisitanteSeleccionado.getInvestigador())
						&& hci.getFechaRetiro() == null) {
					hci.setFechaRetiro(new Date());
					servicioGeneral.guardarObjeto(hci);
					break;
				}
			}
		}
		cargarListasIntegrantes();
	}

	/**
	 * Eliminar integrante estudiante maestria.
	 */
	@SuppressWarnings("deprecation")
	public void eliminarIntegranteEstudianteMaestria() {
		grupoActual.borrarInvestigadorGrupo(integranteEstudianteMaestriaSeleccionado);
		List objetos = servicioGeneral.obtenerObjetos(
				"select h from HistoricoCambioIntegrantes h where h.grupo.id = '" + grupoActual.getId() + "'");
		if (objetos != null && objetos.size() > 0) {
			for (Object object : objetos) {
				hci = (HistoricoCambioIntegrantes) object;
				if (hci.getIntegrante().equals(integranteEstudianteMaestriaSeleccionado.getInvestigador())
						&& hci.getFechaRetiro() == null) {
					hci.setFechaRetiro(new Date());
					servicioGeneral.guardarObjeto(hci);
					break;
				}
			}
		}
		cargarListasIntegrantes();
	}

	/**
	 * Eliminar integrante estudiante pregrado.
	 */
	@SuppressWarnings("deprecation")
	public void eliminarIntegranteEstudiantePregrado() {
		grupoActual.borrarInvestigadorGrupo(integranteEstudiantePregradoSeleccionado);
		List objetos = servicioGeneral.obtenerObjetos(
				"select h from HistoricoCambioIntegrantes h where h.grupo.id = '" + grupoActual.getId() + "'");
		if (objetos != null && objetos.size() > 0) {
			for (Object object : objetos) {
				hci = (HistoricoCambioIntegrantes) object;
				if (hci.getIntegrante().equals(integranteEstudiantePregradoSeleccionado.getInvestigador())
						&& hci.getFechaRetiro() == null) {
					hci.setFechaRetiro(new Date());
					servicioGeneral.guardarObjeto(hci);
					break;
				}
			}
		}
		cargarListasIntegrantes();
	}

	/**
	 * Eliminar integrante estudiante lider.
	 */
	@SuppressWarnings("deprecation")
	public void eliminarIntegranteEstLider() {
		grupoActual.borrarInvestigadorGrupo(integranteEstLiderSeleccionado);
		List objetos = servicioGeneral.obtenerObjetos(
				"select h from HistoricoCambioIntegrantes h where h.grupo.id = '" + grupoActual.getId() + "'");
		if (objetos != null && objetos.size() > 0) {
			for (Object object : objetos) {
				hci = (HistoricoCambioIntegrantes) object;
				if (hci.getIntegrante().equals(integranteEstLiderSeleccionado.getInvestigador())
						&& hci.getFechaRetiro() == null) {
					hci.setFechaRetiro(new Date());
					servicioGeneral.guardarObjeto(hci);
					break;
				}
			}
		}
		cargarListasIntegrantes();
	}

	/**
	 * Eliminar integrante externo.
	 */
	@SuppressWarnings("deprecation")
	public void eliminarIntegranteExterno() {
		grupoActual.borrarInvestigadorGrupo(integranteExternoSeleccionado);
		List objetos = servicioGeneral.obtenerObjetos(
				"select h from HistoricoCambioIntegrantes h where h.grupo.id = '" + grupoActual.getId() + "'");
		if (objetos != null && objetos.size() > 0) {
			for (Object object : objetos) {
				hci = (HistoricoCambioIntegrantes) object;
				if (hci.getIntegrante().equals(integranteExternoSeleccionado.getInvestigador())
						&& hci.getFechaRetiro() == null) {
					hci.setFechaRetiro(new Date());
					servicioGeneral.guardarObjeto(hci);
					break;
				}
			}
		}
		cargarListasIntegrantes();
	}

	/**
	 * Finalizar adicion integrante validado.
	 *
	 * @param nuevoInvestigador   the nuevo investigador
	 * @param esNuevoInvestigador the es nuevo investigador
	 */
	public void finalizarAdicionIntegranteValidado(Investigador nuevoInvestigador, boolean esNuevoInvestigador) {
		investigadorGrupoActual.setInvestigador(nuevoInvestigador);
		if (esNuevoInvestigador) {
			hci = new HistoricoCambioIntegrantes();
			hci.setGrupo(grupoActual);
			hci.setFechaIngreso(new Date());
			hci.setIntegrante(nuevoInvestigador);
			hci.setTipo(investigadorGrupoActual.getTipo());
			hci.setTipoVinculacionGrupo(investigadorGrupoActual.getTipoVinculacion());
			if ((investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.ESTUDIANTE)
					|| investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.ESTUDIANTE_LIDER)
					|| investigadorGrupoActual.getTipo().equals(InvestigadorGrupo.ESTUDIANTE_VISITANTE))
					&& investigadorGrupoActual.getTipoVinculacion().equals("I")) {
				Estudiante e = servicioPersona.obtenerEstudiante(nuevoInvestigador.getId());
				investigadorGrupoActual.setNombrePlanEstudiante(e.getPlan().getNombre());
				investigadorGrupoActual.setPlanEstudioEstudiante(e.getPlan());
				hci.setPlanEstudios(e.getPlan());
				hci.setSemestreActual(e.getSemestreActual());
				hci.setDependencia(e.getDependencia());
				hci.setSede(e.getDependencia().getSede());
			} else if (investigadorGrupoActual.getTipoVinculacion().equals("I")) {
				hci.setTipoVinculacion(investigadorGrupoActual.getInvestigador().getTipoVinculacion());
				hci.setTipoDedicacion(investigadorGrupoActual.getInvestigador().getTipoDedicacion());
				hci.setTipoFormacion(investigadorGrupoActual.getInvestigador().getTipoFormacion());
				hci.setDependencia(investigadorGrupoActual.getInvestigador().getDependencia().getFacultad());
				hci.setSede(investigadorGrupoActual.getInvestigador().getDependencia().getFacultad().getSede());
			}
			grupoActual.adicionarInvestigadorGrupo(investigadorGrupoActual);
			//servicioGeneral.guardarObjeto(hci);
			historicosPendientes.add(hci);
		}
		investigadorGrupoActual = new InvestigadorGrupo();
		mostrarAgregarIntegrante = false;
		investigadorActualExterno = false;
		mostrarCambioLider = false;
	}

	/**
	 * Gets the documento.
	 *
	 * @return the documento
	 */
	public String getDocumento() {
		return documento;
	}

	/**
	 * Gets the genero item.
	 *
	 * @return the genero item
	 */
	public SelectItem[] getGeneroItem() {
		return generoItem;
	}

	/**
	 * Gets the grupo actual.
	 *
	 * @return the grupo actual
	 */
	public Grupo getGrupoActual() {
		return grupoActual;
	}

	/**
	 * Gets the integrante codirector seleccionado.
	 *
	 * @return the integranteCodirectorSeleccionado
	 */
	public InvestigadorGrupo getIntegranteCodirectorSeleccionado() {
		return integranteCodirectorSeleccionado;
	}

	/**
	 * Gets the integrante docente seleccionado.
	 *
	 * @return the integrante docente seleccionado
	 */
	public InvestigadorGrupo getIntegranteDocenteSeleccionado() {
		return integranteDocenteSeleccionado;
	}

	/**
	 * Gets the integrante estudiante doctorado seleccionado.
	 *
	 * @return the integrante estudiante doctorado seleccionado
	 */
	public InvestigadorGrupo getIntegranteEstudianteDoctoradoSeleccionado() {
		return integranteEstudianteDoctoradoSeleccionado;
	}

	/**
	 * Gets the integrante estudiante maestria seleccionado.
	 *
	 * @return the integrante estudiante maestria seleccionado
	 */
	public InvestigadorGrupo getIntegranteEstudianteMaestriaSeleccionado() {
		return integranteEstudianteMaestriaSeleccionado;
	}

	/**
	 * Gets the integrante estudiante pregrado seleccionado.
	 *
	 * @return the integrante estudiante pregrado seleccionado
	 */
	public InvestigadorGrupo getIntegranteEstudiantePregradoSeleccionado() {
		return integranteEstudiantePregradoSeleccionado;
	}

	/**
	 * Gets the integrante externo seleccionado.
	 *
	 * @return the integrante externo seleccionado
	 */
	public InvestigadorGrupo getIntegranteExternoSeleccionado() {
		return integranteExternoSeleccionado;
	}

	/**
	 * Gets the investigador externo.
	 *
	 * @return the investigador externo
	 */
	public InvestigadorExterno getInvestigadorExterno() {
		return investigadorExterno;
	}

	/**
	 * Gets the investigador grupo actual.
	 *
	 * @return the investigador grupo actual
	 */
	public InvestigadorGrupo getInvestigadorGrupoActual() {
		return investigadorGrupoActual;
	}

	/**
	 * Gets the lista integrantes cod.
	 *
	 * @return the lista integrantes cod
	 */
	public List<InvestigadorGrupo> getListaIntegrantesCod() {
		return listaIntegrantesCod;
	}

	/**
	 * Gets the lista integrantes docente.
	 *
	 * @return the lista integrantes docente
	 */
	public List<InvestigadorGrupo> getListaIntegrantesDocente() {
		return listaIntegrantesDocente;
	}

	/**
	 * Gets the lista integrantes estudiante doctorado.
	 *
	 * @return the lista integrantes estudiante doctorado
	 */
	public List<InvestigadorGrupo> getListaIntegrantesEstudianteDoctorado() {
		return listaIntegrantesEstudiantesDoctorado;
	}

	/**
	 * Gets the lista integrantes estudiante maestria.
	 *
	 * @return the lista integrantes estudiante maestria
	 */
	public List<InvestigadorGrupo> getListaIntegrantesEstudianteMaestria() {
		return listaIntegrantesEstudiantesMaestria;
	}

	/**
	 * Gets the lista integrantes estudiante pregrado.
	 *
	 * @return the lista integrantes estudiante pregrado
	 */
	public List<InvestigadorGrupo> getListaIntegrantesEstudiantePregrado() {
		return listaIntegrantesEstudiantesPregrado;
	}

	/**
	 * Gets the lista integrantes externo.
	 *
	 * @return the lista integrantes externo
	 */
	public List<InvestigadorGrupo> getListaIntegrantesExterno() {
		return listaIntegrantesExterno;
	}

	/**
	 * Gets the lista integrantes lider.
	 *
	 * @return the lista integrantes lider
	 */
	public List<InvestigadorGrupo> getListaIntegrantesLider() {
		ArrayList<InvestigadorGrupo> listaInvestigadorGrupos = new ArrayList<InvestigadorGrupo>();
		listaInvestigadorGrupos.add(grupoActual.getLider());
		return listaInvestigadorGrupos;
	}

	/**
	 * Gets the nuevo lider grupo.
	 *
	 * @return the nuevoLiderGrupo
	 */
	public String getNuevoLiderGrupo() {
		return nuevoLiderGrupo;
	}

	/**
	 * Gets the posibles lideres grupo.
	 *
	 * @return the posibles lideres grupo
	 */
	public List<SelectItem> getPosiblesLideresGrupo() {
		return posiblesLideresGrupo;
	}

	/**
	 * Gets the tipo asociacion.
	 *
	 * @return the tipo asociacion
	 */
	public String getTipoAsociacion() {
		return tipoAsociacion;
	}

	/**
	 * Gets the tipo documento inv.
	 *
	 * @return the tipo documento inv
	 */
	public TipoDocumento getTipoDocumentoInv() {
		return tipoDocumentoInv;
	}

	/**
	 * Gets the tipo documento item.
	 *
	 * @return the tipo documento item
	 */
	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	/**
	 * Gets the tipo item.
	 *
	 * @return the tipo item
	 */
	public SelectItem[] getTipoItem() {
		return tipoItem;
	}

	/**
	 * Gets the valor modificable doctorado.
	 *
	 * @return the valor modificable doctorado
	 */
	public String getValorModificableDoctorado() {
		return "";
	}

	/**
	 * Gets the visible cod.
	 *
	 * @return the visible cod
	 */
	public boolean getVisibleCod() {
		return !esListaVacia(getListaIntegrantesCod());
	}

	/**
	 * Gets the visible docente.
	 *
	 * @return the visible docente
	 */
	public boolean getVisibleDocente() {
		return !esListaVacia(getListaIntegrantesDocente());
	}

	/**
	 * Gets the visible estudiante doctorado.
	 *
	 * @return the visible estudiante doctorado
	 */
	public boolean getVisibleEstudianteDoctorado() {
		return !esListaVacia(getListaIntegrantesEstudianteDoctorado());
	}

	public boolean getvisibleEstudianteVisitante() {
		return !esListaVacia(getListaIntegrantesEstudiantesVisitantes());
	}

	/**
	 * Gets the visible estudiante maestria.
	 *
	 * @return the visible estudiante maestria
	 */
	public boolean getVisibleEstudianteMaestria() {
		return !esListaVacia(getListaIntegrantesEstudianteMaestria());
	}

	/**
	 * Gets the visible estudiante pregrado.
	 *
	 * @return the visible estudiante pregrado
	 */
	public boolean getVisibleEstudiantePregrado() {
		return !esListaVacia(getListaIntegrantesEstudiantePregrado());
	}

	/**
	 * Gets the visible estudiante lider.
	 *
	 * @return the visible estudiante lider
	 */
	public boolean getVisibleEstLider() {
		return !esListaVacia(getListaIntegrantesEstLider());
	}

	/**
	 * Gets the visible externo.
	 *
	 * @return the visible externo
	 */
	public boolean getVisibleExterno() {
		return !esListaVacia(getListaIntegrantesExterno());
	}

	public boolean getVisibleEgresado() {
		return !esListaVacia(getListaEgresados());
	}
	
	public void guardarHistoricosIntegrantes() {
		Iterator<HistoricoCambioIntegrantes> it = historicosPendientes.iterator();
		while (it.hasNext()) {
		    HistoricoCambioIntegrantes h = it.next();
		    try {
		        servicioGeneral.guardarObjeto(h);
		        it.remove(); 
		    } catch (Exception e) {
		        e.printStackTrace();
		        break;       
		    }
		}
	}

	/**
	 * Guardar avanzar.
	 *
	 * @return the string
	 */
	public String guardarAvanzar() {
		if (validarIntegrantes()) {
			if (grupoActual.getEstadoMenu() < 3)
				grupoActual.setEstadoMenu(3);
			servicioGrupo.guardarGrupo(grupoActual);
			
			guardarHistoricosIntegrantes();

			borrarManejadorIntegrantes();
			sesion.removeAttribute("manejadorMenuFormularioGrupos");
			sesion.removeAttribute("manejadorEdicionGrupoVisionPrioridadesPerspectiva");
			sesion.removeAttribute(VARIABLE_GRUPO_SESION);
			sesion.setAttribute(VARIABLE_GRUPO_SESION, grupoActual);
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("Los datos han sido guardados correctamente");
			context.addMessage("datosGuardados", mensaje);
			Persona persona = (Persona) sesion.getAttribute("persona");
			guardarHistoricoFormularioGrupo(grupoActual, persona, "2");
			if (!lider && historicoCambioLiderGrupo == null) {
				notificarCambiosEstLider();
			}
			if (historicoCambioLiderGrupo != null) {
				servicioGeneral.guardarObjeto(historicoCambioLiderGrupo);
				enviarCorreoCambioLider(historicoCambioLiderGrupo.getLiderAnterior().getNombreCompletoMinusculas());
				historicoCambioLiderGrupo = null;
			}

			return "lineasGrupo";
		} else
			return "";
	}

	/**
	 * Guardar edicion grupo.
	 */
	public void guardarEdicionGrupo() {
		if (validarIntegrantes()) {
			if (grupoActual.getEstadoMenu() < 3)
				grupoActual.setEstadoMenu(3);
			servicioGrupo.guardarGrupo(grupoActual);
			
			guardarHistoricosIntegrantes();

			borrarManejadorIntegrantes();
			sesion.removeAttribute("manejadorMenuFormularioGrupos");
			sesion.removeAttribute(VARIABLE_GRUPO_SESION);
			sesion.setAttribute(VARIABLE_GRUPO_SESION, grupoActual);
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensaje = new FacesMessage("Los datos han sido guardados correctamente");
			Persona persona = (Persona) sesion.getAttribute("persona");
			guardarHistoricoFormularioGrupo(grupoActual, persona, "2");
			if (historicoCambioLiderGrupo != null) {
				servicioGeneral.guardarObjeto(historicoCambioLiderGrupo);
				enviarCorreoCambioLider(historicoCambioLiderGrupo.getLiderAnterior().getNombreCompletoMinusculas());
				historicoCambioLiderGrupo = null;
			}
			if (!lider) {
				notificarCambiosEstLider();
			}
			context.addMessage("datosGuardados", mensaje);
		}
	}

	private void notificarCambiosEstLider() {
		CorreoPlantilla correoActual = new CorreoPlantilla();
		correoActual = cargarPlantilla(320);
		String cuerpoCorreo = correoActual.getCuerpo().replaceAll("<<EST>>",
				((Investigador) sesion.getAttribute("persona")).getNombreCompleto());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", grupoActual.getId().toString());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<GRUPO>>", grupoActual.getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<FORM>>", "INTEGRANTES");
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<FECHA>>",
				new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(getToday()));
		Correo mensaje = new Correo();
		mensaje.setOrigen(Correo.CORREO_HERMES);
		// mensaje.adicionarCopiaOculta(Correo.CORREO_HERMES);
		mensaje.setAsunto(correoActual.getAsunto());
		mensaje.setCuerpo(cuerpoCorreo);
		mensaje.adicionarDireccion(grupoActual.getLider().getInvestigador().getEmail());
		servicioCorreo.enviarCorreo(mensaje);
	}

	private void enviarCorreoCambioLider(String nombreAntiguoLider) {
		CorreoPlantilla correoActual = new CorreoPlantilla();
		correoActual = cargarPlantilla(394);
		String cuerpoCorreo = correoActual.getCuerpo().replaceAll("<<EST>>",
				((Investigador) sesion.getAttribute("persona")).getNombreCompleto());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<ID>>", grupoActual.getId().toString());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<GRUPO>>", grupoActual.getNombre());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<LIDER_ANT>>", nombreAntiguoLider);
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<LIDER_NUEVO>>",
				grupoActual.getLider().getInvestigador().getNombreCompletoMinusculas());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<SEDE>>", grupoActual.getSede().getNombre().toString());
		cuerpoCorreo = cuerpoCorreo.replaceAll("<<FACULTAD>>", grupoActual.getDependencia().getNombre());
		Correo mensaje = new Correo();
		mensaje.setOrigen(Correo.CORREO_HERMES);
		mensaje.setAsunto(correoActual.getAsunto().replaceAll("<<ID>>", grupoActual.getId().toString()));
		mensaje.setCuerpo(cuerpoCorreo);
		mensaje.adicionarDireccion(grupoActual.getLider().getInvestigador().getEmail());
		servicioCorreo.enviarCorreo(mensaje);
	}

	/**
	 * Ir datos basicos grupo.
	 *
	 * @return the string
	 */
	public String irDatosBasicosGrupo() {
		if (validarIntegrantes()) {
			servicioGrupo.guardarGrupo(grupoActual);
			borrarManejadorIntegrantes();
			return "editarGrupo";
		} else
			return "";
	}

	/**
	 * Ir dependencias.
	 *
	 * @return the string
	 */
	public String irDependencias() {
		if (validarIntegrantes()) {
			servicioGrupo.guardarGrupo(grupoActual);
			borrarManejadorIntegrantes();
			return "dependenciasGrupo";
		} else
			return null;
	}

	/**
	 * Checks if is investigador actual externo.
	 *
	 * @return true, if is investigador actual externo
	 */
	public boolean isInvestigadorActualExterno() {
		return investigadorActualExterno;
	}

	/**
	 * Checks if is mostrar agregar integrante.
	 *
	 * @return true, if is mostrar agregar integrante
	 */
	public boolean isMostrarAgregarIntegrante() {
		return mostrarAgregarIntegrante;
	}

	/**
	 * Checks if is mostrar cambio lider.
	 *
	 * @return true, if is mostrar cambio lider
	 */
	public boolean isMostrarCambioLider() {
		return mostrarCambioLider;
	}

	/**
	 * Checks if is nuevo lider nuevo.
	 *
	 * @return the nuevoLiderNuevo
	 */
	public boolean isNuevoLiderNuevo() {
		return nuevoLiderNuevo;
	}

	/**
	 * Sets the documento.
	 *
	 * @param documento the new documento
	 */
	public void setDocumento(String documento) {
		this.documento = documento;
	}

	/**
	 * Sets the genero item.
	 *
	 * @param generoItem the new genero item
	 */
	public void setGeneroItem(SelectItem[] generoItem) {
		this.generoItem = generoItem;
	}

	/**
	 * Sets the grupo actual.
	 *
	 * @param grupoActual the new grupo actual
	 */
	public void setGrupoActual(Grupo grupoActual) {
		this.grupoActual = grupoActual;
	}

	/**
	 * Sets the integrante codirector seleccionado.
	 *
	 * @param integranteCodirectorSeleccionado the integranteCodirectorSeleccionado
	 *                                         to set
	 */
	public void setIntegranteCodirectorSeleccionado(InvestigadorGrupo integranteCodirectorSeleccionado) {
		this.integranteCodirectorSeleccionado = integranteCodirectorSeleccionado;
	}

	/**
	 * Sets the integrante docente seleccionado.
	 *
	 * @param integranteDocenteSeleccionado the new integrante docente seleccionado
	 */
	public void setIntegranteDocenteSeleccionado(InvestigadorGrupo integranteDocenteSeleccionado) {
		this.integranteDocenteSeleccionado = integranteDocenteSeleccionado;
	}

	/**
	 * Sets the integrante estudiante doctorado seleccionado.
	 *
	 * @param integranteEstudianteDoctoradoSeleccionado the new integrante
	 *                                                  estudiante doctorado
	 *                                                  seleccionado
	 */
	public void setIntegranteEstudianteDoctoradoSeleccionado(
			InvestigadorGrupo integranteEstudianteDoctoradoSeleccionado) {
		this.integranteEstudianteDoctoradoSeleccionado = integranteEstudianteDoctoradoSeleccionado;
	}

	/**
	 * Sets the integrante estudiante maestria seleccionado.
	 *
	 * @param integranteEstudianteMaestriaSeleccionado the new integrante estudiante
	 *                                                 maestria seleccionado
	 */
	public void setIntegranteEstudianteMaestriaSeleccionado(
			InvestigadorGrupo integranteEstudianteMaestriaSeleccionado) {
		this.integranteEstudianteMaestriaSeleccionado = integranteEstudianteMaestriaSeleccionado;
	}

	/**
	 * Sets the integrante estudiante pregrado seleccionado.
	 *
	 * @param integranteEstudiantePregradoSeleccionado the new integrante estudiante
	 *                                                 pregrado seleccionado
	 */
	public void setIntegranteEstudiantePregradoSeleccionado(
			InvestigadorGrupo integranteEstudiantePregradoSeleccionado) {
		this.integranteEstudiantePregradoSeleccionado = integranteEstudiantePregradoSeleccionado;
	}

	/**
	 * Sets the integrante externo seleccionado.
	 *
	 * @param integranteExternoSeleccionado the new integrante externo seleccionado
	 */
	public void setIntegranteExternoSeleccionado(InvestigadorGrupo integranteExternoSeleccionado) {
		this.integranteExternoSeleccionado = integranteExternoSeleccionado;
	}

	/**
	 * Sets the investigador actual externo.
	 *
	 * @param investigadorActualExterno the new investigador actual externo
	 */
	public void setInvestigadorActualExterno(boolean investigadorActualExterno) {
		this.investigadorActualExterno = investigadorActualExterno;
	}

	/**
	 * Sets the investigador externo.
	 *
	 * @param investigadorExterno the new investigador externo
	 */
	public void setInvestigadorExterno(InvestigadorExterno investigadorExterno) {
		this.investigadorExterno = investigadorExterno;
	}

	/**
	 * Sets the investigador grupo actual.
	 *
	 * @param investigadorGrupoActual the new investigador grupo actual
	 */
	public void setInvestigadorGrupoActual(InvestigadorGrupo investigadorGrupoActual) {
		this.investigadorGrupoActual = investigadorGrupoActual;
	}

	/**
	 * Sets the nuevo lider grupo.
	 *
	 * @param nuevoLiderGrupo the nuevoLiderGrupo to set
	 */
	public void setNuevoLiderGrupo(String nuevoLiderGrupo) {
		this.nuevoLiderGrupo = nuevoLiderGrupo;
	}

	/**
	 * Sets the nuevo lider nuevo.
	 *
	 * @param nuevoLiderNuevo the nuevoLiderNuevo to set
	 */
	public void setNuevoLiderNuevo(boolean nuevoLiderNuevo) {
		this.nuevoLiderNuevo = nuevoLiderNuevo;
	}

	/**
	 * Sets the tipo asociacion.
	 *
	 * @param tipoAsociacion the new tipo asociacion
	 */
	public void setTipoAsociacion(String tipoAsociacion) {
		this.tipoAsociacion = tipoAsociacion;
	}

	/**
	 * Sets the tipo documento inv.
	 *
	 * @param tipoDocumentoInv the new tipo documento inv
	 */
	public void setTipoDocumentoInv(TipoDocumento tipoDocumentoInv) {
		this.tipoDocumentoInv = tipoDocumentoInv;
	}

	/**
	 * Sets the tipo documento item.
	 *
	 * @param tipoDocumentoItem the new tipo documento item
	 */
	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	/**
	 * Sets the tipo item.
	 *
	 * @param tipoItem the new tipo item
	 */
	public void setTipoItem(SelectItem[] tipoItem) {
		this.tipoItem = tipoItem;
	}

	/**
	 * Siguiente.
	 *
	 * @return the string
	 */
	public String siguiente() {
		return "lineasGrupo";
	}

	/**
	 * Validar externo.
	 *
	 * @return true, if successful
	 */
	public boolean validarExterno() {
		boolean isOK = true;
		try {
			InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(investigadorExterno.getId());
			if (ii != null && (ii.getInterno().equals("S") || ii.getEsFuncionario().equals("S"))) {
				mensajeError(
						"No puede agregar el investigador como externo ya que es un docente o funcionario activo.");
				isOK = false;
				return isOK;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (investigadorExterno.getNombre1().length() == 0) {
			mensajeError("Es necesario ingresar el primer nombre del integrante.");
			isOK = false;
		} else if (!validarTextoSinNumeros(investigadorExterno.getNombre1())) {
			mensajeError("Primer nombre del integrante inválido.");
			isOK = false;
		}
		if (investigadorExterno.getNombre2().length() != 0
				&& !validarTextoSinNumeros(investigadorExterno.getNombre2())) {
			mensajeError("Segundo nombre del integrante inválido.");
			isOK = false;
		}
		if (investigadorExterno.getApellido1().length() == 0) {
			mensajeError("Es necesario ingresar el primer apellido del integrante.");
			isOK = false;
		} else if (!validarTextoSinNumeros(investigadorExterno.getApellido1())) {
			mensajeError("Primer apellido del integrante inválido.");
			isOK = false;
		}
		if (investigadorExterno.getApellido2().length() != 0
				&& !validarTextoSinNumeros(investigadorExterno.getApellido2())) {
			mensajeError("Segundo apellido del integrante inválido.");
			isOK = false;
		}
		if (investigadorExterno.getGenero().length() == 0) {
			mensajeError("Es necesario ingresar el género del integrante.");
			isOK = false;
		}
		if (tipoFormacion.length() == 0) {
			mensajeError("Es necesario ingresar el máximo nivel de estudios del integrante.");
			isOK = false;
		}
		if (estadoCivil.length() == 0) {
			mensajeError("Es necesario ingresar el estado civil del integrante.");
			isOK = false;
		}
		if (investigadorExterno.getFechaNacimiento() == null) {
			mensajeError("Es necesario ingresar la fecha de nacimiento del integrante.");
			isOK = false;
		} else if (investigadorExterno.getFechaNacimiento().after(getToday())) {
			mensajeError("La fecha de nacimiento del integrante no puede ser superior a HOY.");
			isOK = false;
		} else if (calcularEdad(investigadorExterno.getFechaNacimiento()) < 18
				&& investigadorExterno.getId().getTipoDocumento().equals(TipoDocumento.CEDULA)) {
			mensajeError("El tipo de documento no coincide con la fecha de nacimiento del integrante.");
			isOK = false;
		} else if (calcularEdad(investigadorExterno.getFechaNacimiento()) >= 18
				&& calcularEdad(investigadorExterno.getFechaNacimiento()) < 10
				&& investigadorExterno.getId().getTipoDocumento().equals(TipoDocumento.TARJETA_IDENTIDAD)) {
			mensajeError("El tipo de documento no coincide con la fecha de nacimiento del integrante.");
			isOK = false;
		}
		if (paisSel.length() == 0) {
			mensajeError("Es necesario ingresar el país de nacimiento del integrante.");
			isOK = false;
		}
		if (ciudadActual.length() == 0) {
			mensajeError("Es necesario ingresar la ciudad de nacimiento del integrante.");
			isOK = false;
		}
		if (investigadorExterno.getEmail().length() == 0) {
			mensajeError("Es necesario ingresar el e-mail del integrante.");
			isOK = false;
		} else if (!validarEmail(investigadorExterno.getEmail())) {
			mensajeError("E-mail del integrante inválido.");
			isOK = false;
		} else if (investigadorExterno.getEmail().toLowerCase().contains("@unal.edu.co")) {
			mensajeError("Por favor, coloque otro correo diferente al de la UNal.");
			isOK = false;
		}
		if (investigadorExterno.getTelefono().length() == 0) {
			mensajeError("Es necesario ingresar el teléfono del integrante.");
			isOK = false;
		} else if (investigadorExterno.getTelefono().length() < 7) {
			mensajeError("Teléfono del integrante inválido.");
			isOK = false;
		}
		if (institucionEvaluador.length() == 0) {
			mensajeError("Es necesario ingresar la institución del integrante.");
			isOK = false;
		}
		if (areaCienciaInv.length() == 0) {
			mensajeError("Es necesario ingresar la área OCDE del integrante.");
			isOK = false;
		}
		if (subAreaCienciaInv.length() == 0) {
			mensajeError("Es necesario ingresar la sub-área OCDE del integrante.");
			isOK = false;
		}
		if ((investigadorExterno.getId().getTipoDocumento().equals(TipoDocumento.CEDULA_EXTRANJERIA)
				|| investigadorExterno.getId().getTipoDocumento().equals(TipoDocumento.DOCUMENTO_IDENTIDAD_EXTRANJERA))
				&& paisSel.equals("CO")) {
			mensajeError(
					"Si el integrante nació en Colombia, su documento no puede ser de tipo CÉDULA DE EXTRANJERÍA o DOCUMENTO DE IDENTIDAD EXTRANJERA.");
			isOK = false;
		}
		return isOK;
	}

	/**
	 * Validar integrantes.
	 *
	 * @return true, if successful
	 */
	private boolean validarIntegrantes() {
		System.out.println("validar integrantes");

		if (grupoActual.getInvestigadoresGrupo().isEmpty()) {
			return false;
		} else {
			if (grupoActual.getInvestigadoresGrupo() != null || grupoActual.getInvestigadoresGrupo().size() > 0) {
				Iterator<InvestigadorGrupo> it = grupoActual.getInvestigadoresGrupo().iterator();
				while (it.hasNext()) {
					InvestigadorGrupo ig = it.next();
					if (esCadenaVacia(ig.getFuncion())) {
						mensajeError("NO SE HA GUARDADO LA INFORMACIÓN DEL FOMULARIO. Debe especificar la función para todos los integrantes.");
						return false;
					}
				}
			}
		}

		if (!validarGrupo()) {
			return false;
		}

		return true;
	}

	public boolean validarGrupo() {
		boolean valido = true;
		boolean tieneExterno = false;
		boolean tieneCodirectorFacultad = false;

		String tipoInvestigador = "";
		if (!esCadenaVacia(grupoActual.getInterinstitucion()) && grupoActual.getInterinstitucion().equals("S")) {
			if (grupoActual.getInvestigadoresGrupo() != null) {
				Iterator<InvestigadorGrupo> it = grupoActual.getInvestigadoresGrupo().iterator();
				while (it.hasNext()) {
					InvestigadorGrupo ig = it.next();
					tipoInvestigador = ig.getTipo();
					if (InvestigadorGrupo.EXTERNO.equals(ig.getTipoVinculacion())) {
						tieneExterno = true;
						break;
					}
				}
				if (!tieneExterno) {
					valido = false;
					mensajeError(
							"NO SE HA GUARDADO LA INFORMACIÓN DEL FOMULARIO. El grupo debe tener al menos un integrante externo, ya que se indicó que es Interinstitucional");
				}
			} else {
				valido = false;
				mensajeError(
						"NO SE HA GUARDADO LA INFORMACIÓN DEL FOMULARIO. El grupo debe tener al menos un integrante externo, ya que se indicó que es Interinstitucional");
			}
		}

		if (!esCadenaVacia(grupoActual.getIntersedes()) && grupoActual.getIntersedes().equals("S")) {
			if (grupoActual.getInvestigadoresGrupo() != null) {
				Iterator<InvestigadorGrupo> it = grupoActual.getInvestigadoresGrupo().iterator();
				List sedes = new ArrayList();
				int countSedes = 0;
				int totalSedes = 0;
				try {
					sedes = servicioGeneral.obtenerObjetos(GrupoIntersedes.class,
							"from GrupoIntersedes gi where gi.grupo.id = " + "'" + grupoActual.getId() + "' and gi.fechaBorrado is null");
					totalSedes = sedes.size();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				while (it.hasNext()) {
					InvestigadorGrupo ig = it.next();
					tipoInvestigador = ig.getTipo();
					if (InvestigadorGrupo.INTERNO.equals(ig.getTipoVinculacion()) && ig.getTipo().equals(InvestigadorGrupo.CODIRECTOR)) {
						// Se obtiene el investigador ingerno
						InvestigadorInterno investigadoriInterno = servicioPersona
								.obtenerInvestigadorInterno(ig.getInvestigador().getId());
						// Si no existe el interno se agrega
						if (investigadoriInterno != null && sedes != null && sedes.size() > 0) {
							
							for (int j = 0; j < sedes.size(); j++) {
								GrupoIntersedes s = (GrupoIntersedes) sedes.get(j);
								Sede sede = s.getSede();

								if (sede.getId().equals(investigadoriInterno.getDependencia().getSede().getId())) {
									countSedes = countSedes +1;
									sedes.remove(j);
										break;
								}
							}
						}
					}
				}
				
				if (totalSedes>countSedes) {
					valido = false;
					mensajeError(
							"NO SE HA GUARDADO LA INFORMACIÓN DEL FOMULARIO. Ya que se indicó que es Intersedes, el grupo debe tener al menos un co-líder en cada una de ellas.");
				}
			} else {
				valido = false;
				mensajeError(
						"NO SE HA GUARDADO LA INFORMACIÓN DEL FOMULARIO. Ya que se indicó que es Intersedes, el grupo debe tener al menos un co-líder en cada una de ellas.");
			}
		}

		if (!esCadenaVacia(grupoActual.getInterfacultades()) && grupoActual.getInterfacultades().equals("S")) {
			if (grupoActual.getInvestigadoresGrupo() != null) {
				Iterator<InvestigadorGrupo> it = grupoActual.getInvestigadoresGrupo().iterator();
				List dependencias = new ArrayList();
				int countDependencias = 0;
				int totalDependencias = 0;
				try {
					dependencias = grupoActual.getListaDependencias();
					totalDependencias = dependencias.size();
				} catch (Exception e) {
					e.printStackTrace();
				}
				while (it.hasNext()) {
					InvestigadorGrupo ig = it.next();
					tipoInvestigador = ig.getTipo();
					if (InvestigadorGrupo.INTERNO.equals(ig.getTipoVinculacion()) && ig.getTipo().equals(InvestigadorGrupo.CODIRECTOR)) {
						// Se obtiene el investigador ingerno
						InvestigadorInterno investigadoriInterno = servicioPersona
								.obtenerInvestigadorInterno(ig.getInvestigador().getId());
						// Si no existe el interno se agrega
						if (investigadoriInterno != null && dependencias != null && dependencias.size() > 0) {
							
							for (int j = 0; j < dependencias.size(); j++) {
								
								Dependencia dep = (Dependencia) dependencias.get(j);

								if (dep.getId().equals(investigadoriInterno.getDependencia().getFacultad().getId())) {
									countDependencias = countDependencias +1;
									dependencias.remove(j);
										break;
								}
							}
						}
					}
				}
				if (totalDependencias>countDependencias) {
					valido = false;
					mensajeError(
							"NO SE HA GUARDADO LA INFORMACIÓN DEL FOMULARIO. Ya que se indicó que es InterFacultades, el grupo debe tener al menos un co-líder en cada una de ellas. ");
				}
			} else {
				valido = false;
				mensajeError(
						"NO SE HA GUARDADO LA INFORMACIÓN DEL FOMULARIO. Ya que se indicó que es InterFacultades, el grupo debe tener al menos un co-líder en cada una de ellas. ");
			}
		}

		return valido;
	}

	/**
	 * Ver agregar integrante.
	 */
	public void verAgregarIntegrante() {
		investigadorExterno = null;
		tipoDocumentoInv = new TipoDocumento();
		tipoFormacion = "";
		estadoCivil = "";
		paisSel = "";
		ciudadActual = "";
		areaCienciaInv = "";
		subAreaCienciaInv = "";
		investigadorActualExterno = false;
		documento = "";
		mostrarAgregarIntegrante = true;
		mostrarCambioLider = false;
		tipoAsociacion = "";
	}

	/**
	 * Ver cambio lider.
	 */
	public void verCambioLider() {
		mostrarAgregarIntegrante = false;
		mostrarCambioLider = true;
		tipoAsociacion = TIPO_ASOCIACION_LIDER;
		investigadorGrupoActual.setTipo("D");
		posiblesLideresGrupo = cargarPosiblesLideresGrupo();
		if (esListaVacia(posiblesLideresGrupo)) {
			nuevoLiderNuevo = true;
		}
		nuevoLiderGrupo = "";
	}

	public List<Institucion> getListaInstituciones() {
		return listaInstituciones;
	}

	public void setListaInstituciones(List<Institucion> listaInstituciones) {
		this.listaInstituciones = listaInstituciones;
	}

	public ArrayList<SelectItem> getListaInstitucionesItem() {
		return listaInstitucionesItem;
	}

	public void setListaInstitucionesItem(ArrayList<SelectItem> listaInstitucionesItem) {
		this.listaInstitucionesItem = listaInstitucionesItem;
	}

	public String getInstitucionEvaluador() {
		return institucionEvaluador;
	}

	public void setInstitucionEvaluador(String institucionEvaluador) {
		this.institucionEvaluador = institucionEvaluador;
	}

	public List<HistoricoCambioIntegrantes> getHistoricoIntegrantes() {
		return historicoIntegrantes;
	}

	public void setHistoricoIntegrantes(List<HistoricoCambioIntegrantes> historicoIntegrantes) {
		this.historicoIntegrantes = historicoIntegrantes;
	}

	public SelectItem[] getEstadoCivilItem() {
		return estadoCivilItem;
	}

	public void setEstadoCiviltItem(SelectItem[] estadoCivilItem) {
		this.estadoCivilItem = estadoCivilItem;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public SelectItem[] getMaxNivelEstudioItem() {
		return maxNivelEstudioItem;
	}

	public void setMaxNivelEstudioItem(SelectItem[] maxNivelEstudioItem) {
		this.maxNivelEstudioItem = maxNivelEstudioItem;
	}

	public String getTipoFormacion() {
		return tipoFormacion;
	}

	public void setTipoFormacion(String tipoFormacion) {
		this.tipoFormacion = tipoFormacion;
	}

	public SelectItem[] getSelectItemPaises() {
		return selectItemPaises;
	}

	public void setSelectItemPaises(SelectItem[] selectItemPaises) {
		this.selectItemPaises = selectItemPaises;
	}

	public String getPaisSel() {
		return paisSel;
	}

	public void setPaisSel(String paisSel) {
		this.paisSel = paisSel;
	}

	public List<Departamento> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	/**
	 * @return the departamentoItem
	 */
	public SelectItem[] getDepartamentoItem() {
		return departamentoItem;
	}

	/**
	 * @param departamentoItem the departamentoItem to set
	 */
	public void setDepartamentoItem(SelectItem[] departamentoItem) {
		this.departamentoItem = departamentoItem;
	}

	/**
	 * @return the departamentoActual
	 */
	public String getDepartamentoActual() {
		return departamentoActual;
	}

	/**
	 * @param departamentoActual the departamentoActual to set
	 */
	public void setDepartamentoActual(String departamentoActual) {
		this.departamentoActual = departamentoActual;
	}

	/**
	 * @return the listaCiudades
	 */
	public List<Ciudad> getListaCiudades() {
		return listaCiudades;
	}

	/**
	 * @param listaCiudades the listaCiudades to set
	 */
	public void setListaCiudades(List<Ciudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}

	/**
	 * @return the ciudadItem
	 */
	public SelectItem[] getCiudadItem() {
		return ciudadItem;
	}

	/**
	 * @param ciudadItem the ciudadItem to set
	 */
	public void setCiudadItem(SelectItem[] ciudadItem) {
		this.ciudadItem = ciudadItem;
	}

	/**
	 * @return the ciudadActual
	 */
	public String getCiudadActual() {
		return ciudadActual;
	}

	/**
	 * @param ciudadActual the ciudadActual to set
	 */
	public void setCiudadActual(String ciudadActual) {
		this.ciudadActual = ciudadActual;
	}

	/**
	 * @return the areaCienciaItems
	 */
	public SelectItem[] getAreaCienciaItems() {
		return areaCienciaItems;
	}

	/**
	 * @param areaCienciaItems the areaCienciaItems to set
	 */
	public void setAreaCienciaItems(SelectItem[] areaCienciaItems) {
		this.areaCienciaItems = areaCienciaItems;
	}

	/**
	 * @return the subAreaCienciaItems
	 */
	public SelectItem[] getSubAreaCienciaItems() {
		return subAreaCienciaItems;
	}

	/**
	 * @param subAreaCienciaItems the subAreaCienciaItems to set
	 */
	public void setSubAreaCienciaItems(SelectItem[] subAreaCienciaItems) {
		this.subAreaCienciaItems = subAreaCienciaItems;
	}

	/**
	 * @return the areaCienciaInv
	 */
	public String getAreaCienciaInv() {
		return areaCienciaInv;
	}

	/**
	 * @param areaCienciaInv the areaCienciaInv to set
	 */
	public void setAreaCienciaInv(String areaCienciaInv) {
		this.areaCienciaInv = areaCienciaInv;
	}

	/**
	 * @return the subAreaCienciaInv
	 */
	public String getSubAreaCienciaInv() {
		return subAreaCienciaInv;
	}

	/**
	 * @param subAreaCienciaInv the subAreaCienciaInv to set
	 */
	public void setSubAreaCienciaInv(String subAreaCienciaInv) {
		this.subAreaCienciaInv = subAreaCienciaInv;
	}

	public List<InvestigadorGrupo> getListaIntegrantesEstLider() {
		return listaIntegrantesEstLider;
	}

	public void setListaIntegrantesEstLider(List<InvestigadorGrupo> listaIntegrantesEstLider) {
		this.listaIntegrantesEstLider = listaIntegrantesEstLider;
	}

	public InvestigadorGrupo getIntegranteEstLiderSeleccionado() {
		return integranteEstLiderSeleccionado;
	}

	public void setIntegranteEstLiderSeleccionado(InvestigadorGrupo integranteEstLiderSeleccionado) {
		this.integranteEstLiderSeleccionado = integranteEstLiderSeleccionado;
	}

	public void cargarHistorico() {
		// { "D", "Docente" }, { "A", "Estudiante" }, { "C", "Docente
		// Codirector" },
		// { "E", "Otros" }
		setHistoricoIntegrantes(servicioGeneral.obtenerObjetos(HistoricoCambioIntegrantes.class,
				"from HistoricoCambioIntegrantes h where h.grupo.id = '" + grupoActual.getId()
						+ "' order by h.id asc"));
		for (HistoricoCambioIntegrantes item : historicoIntegrantes) {
			TipoDocumento td = servicioGeneral
					.obtenerObjetoXID(TipoDocumento.class, item.getIntegrante().getId().getTipoDocumento()).get(0);
			item.setDocumento(td.getNombre());
			if (item.getTipoVinculacionGrupo() == null) {
				item.setTipoVinculacionGrupo("N/A");
			} else if (item.getTipoVinculacionGrupo().equals("I")) {
				item.setTipoVinculacionGrupo("Interno");
			} else if (item.getTipoVinculacionGrupo().equals("E")) {
				item.setTipoVinculacionGrupo("Externo");
			}
			if (item.getTipo() == null) {
				item.setTipo("N/A");
			} else if (item.getTipo().equals(InvestigadorGrupo.DOCENTE)) {
				item.setTipo("Docente");
			} else if (item.getTipo().equals(InvestigadorGrupo.ESTUDIANTE)) {
				item.setTipo("Estudiante");
			} else if (item.getTipo().equals(InvestigadorGrupo.CODIRECTOR)) {
				item.setTipo("Co-líder");
			} else if (item.getTipo().equals(InvestigadorGrupo.EXTERNO)) {
				item.setTipo("Otros");
			} else if (item.getTipo().equals(InvestigadorGrupo.ESTUDIANTE_LIDER)) {
				item.setTipo("Estudiante Líder");
			} else if (item.getTipo().equals(InvestigadorGrupo.LIDER)) {
				item.setTipo("Líder");
			} else if (item.getTipo().equals(InvestigadorGrupo.ESTUDIANTE_VISITANTE)) {
				item.setTipo("Estudiante Visitante");
			} else if (item.getTipo().equals(InvestigadorGrupo.ADMINISTRATIVO)) {
				item.setTipo("Administrativo");
			} else if (item.getTipo().equals(InvestigadorGrupo.ESTUDIANTE_PREGRADO)) {
				item.setTipo("Estudiante Pregrado");
			} else if (item.getTipo().equals(InvestigadorGrupo.ESTUDIANTE_POSGRADO)) {
				item.setTipo("Estudiante Posgrado");
			}
			// System.out.println(tipoItem.);
		}
	}

	public void revisarPais() {
		if (paisSel.equals("")) {
			ciudadItem = null;
		} else if (paisSel != null && !paisSel.equals("") && paisSel.equals("CO")) {
			obtenerListaDepartamentos();
			ciudadItem = null;
		} else {
			obtenerListaCiudadesDiferentesColombia();
		}
		departamentoActual = "";
		ciudadActual = "";
	}

	private void obtenerListaDepartamentos() {
		setListaDepartamentos(servicioGeneral.obtenerObjetosLimitado(Departamento.class,
				"select #id e.id, #nombre e.nombre from Departamento e where e.id like 'CO%' order by e.nombre asc"));
		setDepartamentoItem(new SelectItem[getListaDepartamentos().size()]);
		for (int i = 0; i < getListaDepartamentos().size(); i++) {
			Departamento dep = (Departamento) getListaDepartamentos().get(i);
			getDepartamentoItem()[i] = new SelectItem(dep.getId(), dep.getNombre());
			dep = null;
		}
	}

	@SuppressWarnings("unchecked")
	public void cambiarDepartamento() {
		setListaCiudades(servicioGeneral.obtenerObjetosLimitado(Ciudad.class,
				"select #id e.id, #nombre e.nombre from Ciudad e where e.departamento.id like '" + departamentoActual
						+ "' and e.sigla is not null order by e.nombre asc"));
		setCiudadItem(new SelectItem[getListaCiudades().size()]);
		for (int i = 0; i < getListaCiudades().size(); i++) {
			Ciudad ci = (Ciudad) getListaCiudades().get(i);
			getCiudadItem()[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	public void cambiarAreaInv() {
		subAreaCienciaItems = cambiarAreaCiencia(areaCienciaInv);
		setSubAreaCienciaInv("");
	}

	protected SelectItem[] cambiarAreaCiencia(String areaCiencia) {
		String consultaAreasSec = "select dd from Dominio d, DominioDetalle dd where d.id = dd.identificador.id and d.tipo ='"
				+ DOMINIO_SUB_AREA_CIENCIA + "' and  dd.estado = '" + areaCiencia + "' order by dd.descripcion";
		List<DominioDetalle> listaSubAreaCiencia = servicioGeneral.obtenerObjetos(DominioDetalle.class,
				consultaAreasSec);
		return crearListaItems(listaSubAreaCiencia);
	}

	private void obtenerListaCiudadesDiferentesColombia() {
		String idPaisBus = "";
		try {
			idPaisBus = paisSel.substring(0, 2);
		} catch (Exception e) {
			idPaisBus = paisSel;
		}
		String hql = "select #id e.id, #nombre e.nombre from Ciudad e where e.id like ('" + idPaisBus + "%')";
		listaCiudades = servicioGeneral.obtenerObjetosLimitado(Ciudad.class, hql);
		ciudadItem = new SelectItem[listaCiudades.size()];
		for (int i = 0; i < listaCiudades.size(); i++) {
			Ciudad ci = (Ciudad) listaCiudades.get(i);
			ciudadItem[i] = new SelectItem(ci.getId(), ci.getNombre());
			ci = null;
		}
	}

	private static Integer calcularEdad(Date fechaNac) {
		Calendar fechaNacimiento = Calendar.getInstance();
		// Se crea un objeto con la fecha actual
		Calendar fechaActual = Calendar.getInstance();
		// Se asigna la fecha recibida a la fecha de nacimiento.
		fechaNacimiento.setTime(fechaNac);
		// Se restan la fecha actual y la fecha de nacimiento
		int anio = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
		int mes = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH);
		int dia = fechaActual.get(Calendar.DATE) - fechaNacimiento.get(Calendar.DATE);
		// Se ajusta el año dependiendo el mes y el día
		if (mes < 0 || (mes == 0 && dia < 0)) {
			anio--;
		}
		// Regresa la edad en base a la fecha de nacimiento
		return anio;
	}

	public boolean validarPermisos() {
		Investigador investigadorInterno = (Investigador) sesion.getAttribute("persona");
		List<InvestigadorGrupo> listGruposInvestigador = servicioPersona.obtenerGruposInvestigador(investigadorInterno);
		if (!esListaVacia(listGruposInvestigador)) {
			Iterator<InvestigadorGrupo> i = listGruposInvestigador.iterator();
			while (i.hasNext()) {
				InvestigadorGrupo investigadorGrupo = i.next();
				if (investigadorGrupo.getGrupo().equals(grupoActual)
						&& investigadorInterno.getId().getDocumento()
								.equals(investigadorGrupo.getInvestigador().getId().getDocumento())
						&& investigadorInterno.getId().getTipoDocumento()
								.equals(investigadorGrupo.getInvestigador().getId().getTipoDocumento())) {
					return (investigadorGrupo.getTipo().equals(InvestigadorGrupo.LIDER)
							|| investigadorGrupo.getTipo().equals(InvestigadorGrupo.CODIRECTOR));
				}
			}
		}
		return false;
	}

	public boolean isLider() {
		return lider;
	}

	public void setLider(boolean lider) {
		this.lider = lider;
	}

	public boolean isAddEstLider() {
		return addEstLider;
	}

	public void setAddEstLider(boolean addEstLider) {
		this.addEstLider = addEstLider;
	}

	public List<InvestigadorGrupo> getListaIntegrantesEstudiantesVisitantes() {
		return listaIntegrantesEstudiantesVisitantes;
	}

	public void setListaIntegrantesEstudiantesVisitantes(
			List<InvestigadorGrupo> listaIntegrantesEstudiantesVisitantes) {
		this.listaIntegrantesEstudiantesVisitantes = listaIntegrantesEstudiantesVisitantes;
	}

	public InvestigadorGrupo getIntegranteEstudianteVisitanteSeleccionado() {
		return integranteEstudianteVisitanteSeleccionado;
	}

	public void setIntegranteEstudianteVisitanteSeleccionado(
			InvestigadorGrupo integranteEstudianteVisitanteSeleccionado) {
		this.integranteEstudianteVisitanteSeleccionado = integranteEstudianteVisitanteSeleccionado;
	}

	public List<InvestigadorGrupo> getListaIntegrantesAdministrativos() {
		return listaIntegrantesAdministrativos;
	}

	public void setListaIntegrantesAdministrativos(List<InvestigadorGrupo> listaIntegrantesAdministrativos) {
		this.listaIntegrantesAdministrativos = listaIntegrantesAdministrativos;
	}

	public boolean getvisibleAdministrativos() {
		return !esListaVacia(getListaIntegrantesAdministrativos());
	}

	public InvestigadorGrupo getIntegranteAdministrativoSeleccionado() {
		return integranteAdministrativoSeleccionado;
	}

	public void setIntegranteAdministrativoSeleccionado(InvestigadorGrupo integranteAdministrativoSeleccionado) {
		this.integranteAdministrativoSeleccionado = integranteAdministrativoSeleccionado;
	}

	public List<InvestigadorGrupo> getListaEgresados() {
		return listaEgresados;
	}

	public void setListaEgresados(List<InvestigadorGrupo> listaEgresados) {
		this.listaEgresados = listaEgresados;
	}

	public String nombreAreaOcde(String idArea) {
		String consulta = "select dd from Dominio d, DominioDetalle dd "
				+ "where d.id = dd.identificador.id and d.tipo ='AREA_CIENCIA' and dd.identificador.tipo = '" + idArea
				+ "'";

		List<DominioDetalle> listaAreaCiencia = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		if (!esListaVacia(listaAreaCiencia)) {
			DominioDetalle dd = listaAreaCiencia.get(0);
			return dd.getDescripcion();
		}
		return "";
	}

	public String nombreSubAreaOcde(String area, String subarea) {
		String consulta = "select dd from DominioDetalle dd " + "where dd.identificador.tipo = '" + subarea
				+ "' and dd.estado = '" + area + "'";

		List<DominioDetalle> listaAreaCiencia = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		if (!esListaVacia(listaAreaCiencia)) {
			DominioDetalle dd = listaAreaCiencia.get(0);
			return dd.getDescripcion();
		}
		return "";
	}

	public boolean isHuboCambioLider() {
		return huboCambioLider;
	}

	public void setHuboCambioLider(boolean huboCambioLider) {
		this.huboCambioLider = huboCambioLider;
	}

	public List<HistoricoCambioIntegrantes> getHistoricosPendientes() {
		return historicosPendientes;
	}

	public void setHistoricosPendientes(List<HistoricoCambioIntegrantes> historicosPendientes) {
		this.historicosPendientes = historicosPendientes;
	}
}
