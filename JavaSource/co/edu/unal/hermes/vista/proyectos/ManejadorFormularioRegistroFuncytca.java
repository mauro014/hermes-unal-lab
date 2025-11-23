/*
 * Created on 11-jun-2013
 */
package co.edu.unal.hermes.vista.proyectos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Actividad;
import co.edu.unal.hermes.modelo.ActividadMovilidadVE;
import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Preinscripcion_ECP;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ResultadoProyecto;
import co.edu.unal.hermes.modelo.TipoActividadMovilidadVE;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoDocumento;
import co.edu.unal.hermes.modelo.TipoMovilidad;
import co.edu.unal.hermes.vista.proyectos.manejadorproyecto.ManejadorProyecto;

public class ManejadorFormularioRegistroFuncytca extends ManejadorProyecto {

	private Persona persona;
	private String nombreCompletoPersona;
	private String nombreTipoDocInv;
	private String numDocInv;
	private String direccionDomicilio;
	private String telDomicilio;
	private String email;
	private String celular;
	private Date fechaNacimiento;
	private String edad;
	private String programaPregado;
	private String instiPregado;
	private String instVinculado;
	private String rolInst;
	private String idioma;
	private String redesSociales;
	private String conocimientoFunda;
	private List<IdiomaInvestigador> listaIdiomasInvestigador;
	private List<RedesSociales> listaRedesSociales;
	private List<DominioDetalle> listaNivelIdiomas;
	private List<DominioDetalle> listaMedioDifusion;
	private List<DominioDetalle> listaAreaTrabajo;
	private List<DominioDetalle> listaComponentesProyecto;
	private List<DominioDetalle> listaEntidadesFundacion;
	private SelectItem[] tipoSuficienciaIdiomaItem;
	private SelectItem[] tipoMedioDifusionItem;
	private SelectItem[] areaTrabajoItem;
	private SelectItem[] tipoComponenteProyectoItem;
	private SelectItem[] entidadesFundacionItem;
	private String entidadConocimientoFunda;
	private String areaTrabajo;
	private String componenteProyecto;
	private TipoDocumento tipoDocumentoInv;
	private SelectItem[] tipoDocumentoItem;
	private String documentoInv;
	private List<TipoDocumento> listaTipoDocumento;
	private boolean esConsulta;
	private UIComponent objetoEspecifico;
	private UIComponent uiResultado;
	private String objetivoEspecifico;
	private List<ObjetivoEspecifico> listaObjetivos;
	private ObjetivoEspecifico objetivoTabla;
	private List<ResultadoProyecto> listaResultados;
	private ResultadoProyecto resultadoTabla;
	private String resultado;
	private List listaActividades;
	private String mensajeErrorActividad = "";
	private DataTable tablaActividades;
	private UploadedFile archivo;
	private TipoArchivo tipoArchivo;
	private List listaTipoArchivo;
	private SelectItem[] tipoArchivoItem;
	private List<Archivo> listaArchivos;
	private DataTable tablaArchivos;
	private String modConvo = "";
	private String nombreUniversidadPonencia;
	private String ciudadEvento;
	private Date fechaInicioEvento;
	private Date fechaFinEvento;
	private String resumenPonencia;
	private String actividadSel;
	private SelectItem[] actividadSelItem;
	private boolean bErrorActividades;
	private List<ActividadMovilidadVE> listaActividadesEstancia;
	private ActividadMovilidadVE actividadMovilidadVESeleccionada;
	private ActividadMovilidadVE temp;
	private Convocatoria convocatoriaActual;
	private MovilidadVisitanteExterior estanciaGuardar;
	private Proyecto proyectoGuardar;
	private Persona personaGuardar;
	private boolean proyectoExiste;
	private DataTable tablaRedSocial;
	private RedesSociales redSeleccionada;
	private Preinscripcion_ECP preInv;
	private boolean mostrarGrupoInvestigacion = true;
	private boolean mostrarEmprendedores = false;
	private String opcionesConv2 = "grupoInvestigacion";
	private String objetoClasificacionGrupoEmpr;
	private String nombreDirectorGrupo;
	private String tipoDocumentoDirectorGrupo;
	private String documentoDirectorGrupo;
	private String formacionExperto;
	private String nombreDocente;
	private String documentoVisitante;
	private String institucion;
	private SelectItem[] tipoEntidadItems;
	private String tipoEntidadSel;
	private SelectItem[] entidadItems;
	private String entidadSel;
	private Long valorEntidad = 0L;
	private Long valorEntidadEsp = 0L;
	private List<Financiacion> listaEntidadesAdicionadas;
	private Financiacion entidadSeleccionada;
	private String convOriginal2;
	private boolean mostrarDatosEntidadDifusion;
	private Archivo archivoSeleccionado;
	private boolean mostrarBotones = true;

	public ManejadorFormularioRegistroFuncytca() {
		super();
		persona = (Persona) sesion.getAttribute("persona");
		IdPersona idPer = persona.getId();
		persona = servicioPersona.obtenerPersona(idPer);
		String consultaTipoDocumento = "select #nombre e.nombre from TipoDocumento e where e.id = '"
				+ persona.getId().getTipoDocumento() + "'";
		List<TipoDocumento> listaTipoDocumentoInv = servicioGeneral.obtenerObjetosLimitado(TipoDocumento.class,
				consultaTipoDocumento);
		TipoDocumento tp = listaTipoDocumentoInv.get(0);
		nombreTipoDocInv = tp.getNombre();
		numDocInv = persona.getId().getDocumento();
		nombreCompletoPersona = persona.getNombre1() + " " + persona.getNombre2() + " " + persona.getApellido1() + " "
				+ persona.getApellido2();
		direccionDomicilio = persona.getDireccion();
		telDomicilio = persona.getTelefono();
		email = persona.getEmail();
		cargarConvocatoriaActual();
		cargarValoresIniciales();
		esConsulta = false;
		buscarProyectoConvocatoria();

		if (proyectoGuardar == null) {

			proyectoExiste = false;

			proyectoActual = new Proyecto();
			proyectoActual.setDuracion(1);
			proyectoActual.setModalidad(convocatoriaActual);
			proyectoActual.cambiarEstado(EstadoProyecto.INGRESANDO);
			proyectoActual.setFase(0);
			proyectoActual.setTienePryAsociado(false);
			proyectoActual.setTipoActividad("FM_PINV");
			proyectoActual.setCreadorId(persona.getId().getDocumento());
			proyectoActual.setCreadorDocumento(persona.getId().getTipoDocumento());
			preInv = new Preinscripcion_ECP();

		} else {

			proyectoActual = servicioProyecto.obtenerProyecto(proyectoGuardar.getId(),
					ProyectoDAOHibernate.TODO_POR_ID);
			// cargar lista de objetivos específicos
			listaObjetivos.addAll(proyectoActual.getObjetivosEspecificos());

			// cargar lista de resultados
			listaResultados.addAll(proyectoActual.getResultados());

			// cargar entidades
			listaEntidadesAdicionadas.addAll(proyectoActual.getFinanciaciones());

			// cargar actividades
			Set actividades = proyectoActual.getActividades();
			Iterator it = actividades.iterator();
			while (it.hasNext()) {
				Actividad a = (Actividad) it.next();
				a.pasarSemanasMeses();
				listaActividades.add(a);
			}

			tablaArchivos = new DataTable();
			listaTipoArchivo = new ArrayList();

			listaArchivos = servicioProyecto.obtenerNombresArchivosConTipos(proyectoActual);

			convOriginal2 = proyectoActual.getTipoActividad();
			cargarDatosInvestigador();

			cargarDatosEstancia();

			if (proyectoActual.getEstadoProyecto().getId().equals("P")) {
				mostrarBotones = false;
			}

		}

	}

	// entidades:
	public void agregarEntidad() {
		List<FuenteFinanciacion> facultades = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class,
				"select d from FuenteFinanciacion d  where d.id =" + entidadSel);
		FuenteFinanciacion ff = (FuenteFinanciacion) facultades.get(0);
		Financiacion fin = new Financiacion();
		fin.setFuente(ff);
		fin.setValor(valorEntidad);
		fin.setTipoEntidad(tipoEntidadSel);
		fin.setValorEspecie(valorEntidadEsp);
		listaEntidadesAdicionadas.add(fin);
	}

	public void eliminarEntidad() {
		try {
			listaEntidadesAdicionadas.remove(entidadSeleccionada);
			proyectoActual.borrarFinanciacion(entidadSeleccionada);
			entidadSeleccionada = new Financiacion();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void validarOpcionesConvocatoria2() {
		if (opcionesConv2.equals("grupoInvestigacion")) {
			mostrarGrupoInvestigacion = true;
			mostrarEmprendedores = false;
		} else {
			mostrarGrupoInvestigacion = false;
			mostrarEmprendedores = true;
		}
	}

	public void cambiarDifusionConvocatoria() {
		if (conocimientoFunda.equals("Afiche/Poster") || conocimientoFunda.equals("Internet/Redes sociales")
				|| conocimientoFunda.equals("Sesión de divulgación")) {
			mostrarDatosEntidadDifusion = true;
		} else {
			mostrarDatosEntidadDifusion = false;
		}
	}

	public void cargarDatosInvestigador() {
		personaGuardar = servicioPersona.obtenerPersona(persona.getId());
		celular = personaGuardar.getCelular();
		fechaNacimiento = personaGuardar.getFechaNacimiento();
		edad = personaGuardar.getEdad();
		programaPregado = personaGuardar.getFacultadPregrado();
		instiPregado = personaGuardar.getProfesion();
		listaIdiomasInvestigador.clear();
		obtenerIdiomas();

		String consultaPre = "select #empresa e.empresa, #cargo e.cargo, #medio_publicidad e.medio_publicidad, #nombreProblema e.nombreProblema, #nombresRepLegal e.nombresRepLegal, #numDocRepLegal e.numDocRepLegal, #tipoDocRepLegal e.tipoDocRepLegal, #descripcionProblema e.descripcionProblema, #sector_empresa e.sector_empresa  from Preinscripcion_ECP e where e.curso = '"
				+ proyectoActual.getId() + "' and e.investigador.id.documento = '" + persona.getId().getDocumento()
				+ "' and e.investigador.id.tipoDocumento = '" + persona.getId().getTipoDocumento() + "'";
		List<Preinscripcion_ECP> lisPre = servicioGeneral.obtenerObjetosLimitado(Preinscripcion_ECP.class, consultaPre);
		if (lisPre != null && lisPre.size() > 0) {
			preInv = lisPre.get(0);
			instVinculado = preInv.getEmpresa();
			rolInst = preInv.getCargo();
			conocimientoFunda = preInv.getMedio_publicidad();
			// mod dos
			nombreDirectorGrupo = preInv.getNombresRepLegal();
			documentoDirectorGrupo = preInv.getNumDocRepLegal();
			tipoDocumentoDirectorGrupo = preInv.getTipoDocRepLegal();
			objetoClasificacionGrupoEmpr = preInv.getDescripcionProblema();
			entidadConocimientoFunda = preInv.getSector_empresa();

			obtenerRedesSociales();
		}

		cambiarDifusionConvocatoria();

	}

	public void cargarDatosEstancia() {

		String consultaMovilidad = "select e from MovilidadVisitanteExterior e where e.personaInv.id.documento = '"
				+ personaGuardar.getId().getDocumento() + "' and e.proyectoFicha = '" + proyectoActual.getId() + "'";
		List listaMovilidadesPer = servicioGeneral.obtenerObjetos(consultaMovilidad);
		if (listaMovilidadesPer != null && listaMovilidadesPer.size() > 0) {
			estanciaGuardar = (MovilidadVisitanteExterior) listaMovilidadesPer.get(0);
			nombreUniversidadPonencia = estanciaGuardar.getUniversidad();
			ciudadEvento = estanciaGuardar.getCiudad();
			fechaInicioEvento = estanciaGuardar.getFechafinal();
			fechaFinEvento = estanciaGuardar.getFechafinal();
			resumenPonencia = estanciaGuardar.getPlan();

			if (modConvo.equals("524")) {
				nombreDocente = estanciaGuardar.getNombreVisitante();
				documentoVisitante = estanciaGuardar.getDocumentoVisitante();
				institucion = estanciaGuardar.getUniversidad();
				formacionExperto = estanciaGuardar.getAportePrograma();
			}

			String consultaActiEsta = "select e from ActividadMovilidadVE e where e.movilidad.id = "
					+ estanciaGuardar.getId();
			listaActividadesEstancia = servicioGeneral.obtenerObjetos(consultaActiEsta);

		} else {
			estanciaGuardar = new MovilidadVisitanteExterior();
		}

	}

	public String convertirIdiomas() {
		String sIdiomas = "";

		for (int i = 0; i < listaIdiomasInvestigador.size(); i++) {
			IdiomaInvestigador ii = listaIdiomasInvestigador.get(i);
			sIdiomas += ii.getIdioma() + ": lee " + ii.getLee() + ", escribre " + ii.getEscribe() + ", habla "
					+ ii.getHabla() + "~";
		}

		return sIdiomas;
	}

	public String convertirRedesSociales() {
		String eva = "";

		for (int i = 0; i < listaRedesSociales.size(); i++) {
			RedesSociales rs = listaRedesSociales.get(i);
			eva += rs.getNombre() + ": " + rs.getUsuario() + "~";
		}

		return eva;
	}

	public ArrayList<IdiomaInvestigador> obtenerIdiomas() {
		ArrayList<IdiomaInvestigador> cre = new ArrayList<IdiomaInvestigador>();

		if (personaGuardar.getResumenHojaDeVida() != null && !personaGuardar.getResumenHojaDeVida().equals("")) {
			String c = personaGuardar.getResumenHojaDeVida();
			c = c.replace(": lee ", " -> ");
			c = c.replace(", escribre ", " -> ");
			c = c.replace(", habla ", " -> ");
			String[] cr = c.split("~");
			for (int i = 0; i < cr.length; i++) {
				String[] cri = cr[i].split(" -> ");
				IdiomaInvestigador idiomaInv = new IdiomaInvestigador();
				idiomaInv.setIdioma(cri[0]);
				idiomaInv.setLee(cri[1]);
				idiomaInv.setEscribe(cri[2]);
				idiomaInv.setHabla(cri[3]);
				this.listaIdiomasInvestigador.add(idiomaInv);
			}
		}

		return cre;
	}

	public ArrayList<RedesSociales> obtenerRedesSociales() {
		ArrayList<RedesSociales> cre = new ArrayList<RedesSociales>();

		if (preInv.getNombreProblema() != null && !preInv.getNombreProblema().equals("")) {
			String c = preInv.getNombreProblema();
			String[] cr = c.split("~");
			for (int i = 0; i < cr.length; i++) {
				String[] cri = cr[i].split(": ");
				RedesSociales red = new RedesSociales();
				red.setNombre(cri[0]);
				red.setUsuario(cri[1]);
				this.listaRedesSociales.add(red);
			}
		}

		return cre;
	}

	public void eliminarRed() {
		listaRedesSociales.remove(redSeleccionada);
		redSeleccionada = new RedesSociales();
	}

	public void buscarProyectoConvocatoria() {
		String consultaProyecto = "select #id e.id from Proyecto e where e.creadorId = '"
				+ persona.getId().getDocumento() + "' and e.creadorDocumento = '" + persona.getId().getTipoDocumento()
				+ "' and e.modalidad.id in (523, 524, 525)";
		List<Proyecto> listaPersonaGuardar = servicioGeneral.obtenerObjetosLimitado(Proyecto.class, consultaProyecto);
		if (listaPersonaGuardar != null && listaPersonaGuardar.size() > 0) {
			proyectoGuardar = listaPersonaGuardar.get(0);
		} else {
			proyectoGuardar = null;
		}
	}

	public void salirGuardarGeneral() {
		guardarGeneral();
		sesion.removeAttribute("ManejadorFormularioRegistroFuncytca");
		sesion.removeAttribute("ManejadorConvocatoriaFuncytca");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/funcytca/convocatorias.xhtml");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void guardarFinalizar() {
		try {
			proyectoActual.cambiarEstadoPersona(EstadoProyecto.PROPUESTO, cargarPersonaActual());
			servicioProyecto.ingresarProyecto(proyectoActual);
			generarHistoricoProyecto();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("confirmacion", new FacesMessage(
					"Su proyecto se ingresó satisfactoriamente con el número: " + this.proyectoActual.getId()));

			sesion.removeAttribute("ManejadorFormularioRegistroFuncytca");
			sesion.removeAttribute("ManejadorConvocatoriaFuncytca");

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/funcytca/convocatorias.xhtml");
			} catch (IOException e) {

				e.printStackTrace();
			}
			// enviarCorreoCoordinador();
		} catch (Exception e) {
		}
	}

	public void guardarGeneral() {
		guardarProyecto();
		guardarInvestigador();
		guardarEstancia();

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("confirmacion", new FacesMessage(
				"Su proyecto se ingresó satisfactoriamente con el número: " + this.proyectoActual.getId()));
	}

	public void guardarInvestigador() {

		personaGuardar = servicioPersona.obtenerPersona(persona.getId());
		if (personaGuardar != null) {
			personaGuardar.setCelular(celular);
			personaGuardar.setFechaNacimiento(fechaNacimiento);
			personaGuardar.setEdad(edad);
			personaGuardar.setFacultadPregrado(programaPregado);
			personaGuardar.setProfesion(instiPregado);
			personaGuardar.setResumenHojaDeVida(convertirIdiomas());
			personaGuardar.setGenero("S");
			servicioPersona.actualizarPersonaFuncytcaCompleto(personaGuardar);

			String consultaPre = "select #empresa e.empresa, #cargo e.cargo, #medio_publicidad e.medio_publicidad, #nombreProblema e.nombreProblema from Preinscripcion_ECP e where e.curso = '"
					+ proyectoActual.getId() + "' and e.investigador.id.documento = '" + persona.getId().getDocumento()
					+ "' and e.investigador.id.tipoDocumento = '" + persona.getId().getTipoDocumento() + "'";
			List<Preinscripcion_ECP> lisPre = servicioGeneral.obtenerObjetosLimitado(Preinscripcion_ECP.class,
					consultaPre);
			Preinscripcion_ECP preGuardar = new Preinscripcion_ECP();

			try {
				servicioGeneral.eliminar("DELETE HER_EXT_PREINSCRIPCION_ECP WHERE PRY_ID =" + proyectoActual.getId()
						+ " AND INV_ID = '" + personaGuardar.getId().getDocumento() + "'");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (lisPre != null && lisPre.size() > 0) {
				preGuardar = lisPre.get(0);
				preGuardar.setInvestigador(personaGuardar);
				preGuardar.setCurso(proyectoActual);
				preGuardar.setEmpresa(instVinculado);
				preGuardar.setCargo(rolInst);
				preGuardar.setMedio_publicidad(conocimientoFunda);
				preGuardar.setNombreProblema(convertirRedesSociales());
				preGuardar.setSector_empresa(entidadConocimientoFunda);

				// mod dos
				preGuardar.setNombresRepLegal(nombreDirectorGrupo);
				preGuardar.setNumDocRepLegal(documentoDirectorGrupo);
				preGuardar.setTipoDocRepLegal(tipoDocumentoDirectorGrupo);
				preGuardar.setDescripcionProblema(objetoClasificacionGrupoEmpr);
				servicioGeneral.guardarObjeto(preGuardar);
			} else {
				preGuardar.setInvestigador(personaGuardar);
				preGuardar.setCurso(proyectoActual);
				preGuardar.setEmpresa(instVinculado);
				preGuardar.setCargo(rolInst);
				preGuardar.setMedio_publicidad(conocimientoFunda);
				preGuardar.setNombreProblema(convertirRedesSociales());
				preGuardar.setSector_empresa(entidadConocimientoFunda);

				// mod dos
				preGuardar.setNombresRepLegal(nombreDirectorGrupo);
				preGuardar.setNumDocRepLegal(documentoDirectorGrupo);
				preGuardar.setTipoDocRepLegal(tipoDocumentoDirectorGrupo);
				preGuardar.setDescripcionProblema(objetoClasificacionGrupoEmpr);

				servicioGeneral.guardarObjeto(preGuardar);
			}
		}

	}

	public void guardarProyecto() {
		proyectoActual.setTipoActividad(convOriginal2);

		for (int i = 0; i < listaObjetivos.size(); i++) {
			proyectoActual.adicionarObjetivoEspecifico(listaObjetivos.get(i));
		}

		for (int i = 0; i < listaResultados.size(); i++) {
			proyectoActual.adicionarResultado(listaResultados.get(i));
		}

		validarActividades();

		for (int i = 0; i < listaEntidadesAdicionadas.size(); i++) {
			proyectoActual.adicionarFinanciacion(listaEntidadesAdicionadas.get(i));
		}

		servicioGeneral.guardarObjeto(proyectoActual);

	}

	public void guardarEstancia() {

		estanciaGuardar.setPersonaInv(this.personaGuardar);
		estanciaGuardar.setFechainicial(fechaInicioEvento);
		estanciaGuardar.setFechafinal(fechaFinEvento);
		estanciaGuardar.setPlan(resumenPonencia);
		estanciaGuardar.setProyectoFicha(proyectoActual.getId());
		estanciaGuardar.setConvocatoriaId(this.convocatoriaActual.getId().toString());
		if (modConvo.equals("524")) {
			estanciaGuardar.setNombreVisitante(nombreDocente);
			estanciaGuardar.setDocumentoVisitante(documentoVisitante);
			estanciaGuardar.setUniversidad(cortarCadena(institucion, 255));
			estanciaGuardar.setAportePrograma(formacionExperto);
		} else {
			estanciaGuardar.setUniversidad(nombreUniversidadPonencia);
			estanciaGuardar.setCiudad(ciudadEvento);
		}
		Calendar actual = Calendar.getInstance();
		Date date = actual.getTime();
		estanciaGuardar.setFechasolicitud(date);

		Set act = new HashSet();
		for (Iterator it = listaActividadesEstancia.iterator(); it.hasNext();) {
			ActividadMovilidadVE a = (ActividadMovilidadVE) it.next();

			act.add(a);
		}
		estanciaGuardar.setActividades(act);

		List listaTipoMovilidad = new ArrayList();
		listaTipoMovilidad = servicioGeneral.obtenerListaObjetos("TipoMovilidad where id ='FUNCYT'");
		TipoMovilidad tm = new TipoMovilidad();
		tm = (TipoMovilidad) listaTipoMovilidad.get(0);
		estanciaGuardar.setTipoMovilidad(tm);

		servicioGeneral.guardarObjeto(estanciaGuardar);

	}

	public void subirArchivos() {

	}

	public void cargarConvocatoriaActual() {
		String idConvocatoria = (String) sesion.getAttribute("idConvocatoriaFuncytca");
		convOriginal2 = (String) sesion.getAttribute("idConvocatoriaFuncytca");

		if (idConvocatoria.contains("524")) {
			idConvocatoria = "524";
		}

		List listConvocatorias = servicioGeneral
				.obtenerObjetos("select e from Convocatoria e where e.id = " + idConvocatoria);
		if (listConvocatorias.size() > 0 && listConvocatorias != null) {
			convocatoriaActual = (Convocatoria) listConvocatorias.get(0);
		}
		modConvo = convocatoriaActual.getId().toString();
	}

	public void actualizarEdad() {
		int ed = calcularEdad(fechaNacimiento);
		setEdad(Integer.toString(ed));
		System.out.println(ed);
	}

	public static Integer calcularEdad(Date fechaNac) {

		Calendar fechaNacimiento = Calendar.getInstance();
		System.out.println(fechaNacimiento);
		// Se crea un objeto con la fecha actual
		Calendar fechaActual = Calendar.getInstance();
		System.out.println(fechaActual);
		// Se asigna la fecha recibida a la fecha de nacimiento.
		fechaNacimiento.setTime(fechaNac);

		// Se restan la fecha actual y la fecha de nacimiento
		int año = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);

		int mes = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH);
		int dia = fechaActual.get(Calendar.DATE) - fechaNacimiento.get(Calendar.DATE);
		// Se ajusta el año dependiendo el mes y el día
		if (mes < 0 || (mes == 0 && dia < 0)) {
			año--;
		}
		// Regresa la edad en base a la fecha de nacimiento
		System.out.println("****************************");
		System.out.println(año);
		System.out.println("****************************");
		return año;

	}

	private void cargarTiposActividades() {
		List<TipoActividadMovilidadVE> listaTipoActividad = servicioGeneral
				.obtenerObjetos(TipoActividadMovilidadVE.class, "from TipoActividadMovilidadVE");
		actividadSelItem = new SelectItem[listaTipoActividad.size() - 5];
		int j = 0;
		for (int i = 0; i < listaTipoActividad.size(); i++) {
			TipoActividadMovilidadVE tipoActividad = listaTipoActividad.get(i);
			if (!tipoActividad.getId().equals(new Long(6L)) && !tipoActividad.getId().equals(new Long(8L))
					&& !tipoActividad.getId().equals(new Long(9L)) && !tipoActividad.getId().equals(new Long(3L))
					&& !tipoActividad.getId().equals(new Long(10L))) {
				actividadSelItem[j++] = new SelectItem(tipoActividad.getId().toString(),
						tipoActividad.getId().toString() + "-" + tipoActividad.getNombre());
			}
		}
		TipoActividadMovilidadVE tipoActividad1 = (TipoActividadMovilidadVE) listaTipoActividad.get(0);
		actividadSel = tipoActividad1.getId().toString();
	}

	public void insertarActividad() {
		Actividad a = new Actividad();
		a.setDescripcion("");
		a.setMesInicial(new Integer(0));
		a.setDuracionMeses(new Integer(1));
		listaActividades.add(a);
		a = null;
		mensajeErrorActividad = "";
	}

	public void eliminarActividad() {
		((Actividad) listaActividades.get(tablaActividades.getRowIndex())).setBorrable(true);
	}

	// VALIDADORES
	private boolean validarActividades() {
		// SE MIRA SI EXISTE ALGUNA ACTIVIDAD MARCADA PARA BORRAR
		List listaAuxiliar = new ArrayList();
		for (int i = 0; i < listaActividades.size(); i++) {
			Actividad a = (Actividad) listaActividades.get(i);
			if (a.isBorrable()) {
				listaAuxiliar.add(a);
				proyectoActual.borrarActividad(a);
			} else {
				proyectoActual.adicionarActividad(a);
			}
		}
		listaActividades.removeAll(listaAuxiliar);
		mensajeErrorActividad = "";
		// SE VALIDA QUE LA LISTA CONTENGA OBJETOS
		if (!listaActividades.isEmpty()) {
			int duracionProyecto = proyectoActual.getDuracion().intValue();
			int i_actividad = 1;
			listaActividades.iterator();
			Iterator it = listaActividades.iterator();
			while (it.hasNext()) {
				Actividad actividad = (Actividad) it.next();
				int mesInicial = actividad.getMesInicial().intValue();
				int duracionMeses = actividad.getDuracionMeses().intValue();
				if (!actividad.isBorrable() && (duracionProyecto < (mesInicial + duracionMeses) - 1)) {
					mensajeErrorActividad = "La duración de la actividad " + i_actividad + " supera a la del proyecto";
					FacesContext.getCurrentInstance().addMessage("msgForm",
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"La duración de la actividad " + i_actividad + " supera a la del proyecto", ""));
					return false;
				} else {
					i_actividad++;
				}
			}
		} else {
			// mensajeErrorActividad = "No se encuentran actividades asociados
			// al proyecto";
			// FacesContext.getCurrentInstance().addMessage("msgForm", new
			// FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encuentran
			// actividades asociados al proyecto", ""));
			// return false;
		}
		return true;
	}

	public void insertarObjetivo() {
		if (this.objetivoEspecifico != null && !this.objetivoEspecifico.equals("")) {
			ObjetivoEspecifico oe = new ObjetivoEspecifico();
			oe.setNombre(this.objetivoEspecifico);
			listaObjetivos.add(oe);
			this.objetivoEspecifico = "";
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Por favor escriba el objetivo específico", "Por favor escriba el objetivo específico");
			mostrarMensaje(message, objetoEspecifico);
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

	public void insertarResultado() {
		if (this.resultado != null && !this.resultado.equals("")) {
			ResultadoProyecto re = new ResultadoProyecto();
			re.setDescripcion(this.resultado);
			listaResultados.add(re);
			this.resultado = "";
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor escriba el resultado",
					"Por favor escriba el resultado");
			mostrarMensaje(message, uiResultado);
		}

	}

	public void eliminarResultado() {
		listaResultados.remove(resultadoTabla);
		proyectoActual.borrarResultado(resultadoTabla);
		resultadoTabla = new ResultadoProyecto();
	}

	public void eliminarObjetivo() {
		// ((ObjetivoEspecifico)listaObjetivos.get(tablaObjetivos.getRowIndex())).setBorrable(true);
		listaObjetivos.remove(objetivoTabla);
		proyectoActual.borrarObjetivoEspecifico(objetivoTabla);

		objetivoTabla = new ObjetivoEspecifico();
	}

	public String insertarArchivo() {

		TipoArchivo tipoAr = new TipoArchivo();
		List listaAr = servicioGeneral.obtenerObjetoXID("TipoArchivo", tipoArchivo.getId() + "");
		tipoAr = (TipoArchivo) listaAr.get(0);

		return insertarArchivoProyectoGenericoConTipos(archivo, proyectoActual, listaArchivos, tipoAr);
	}

	public String eliminarArchivo() {
		// Long id = ((ArchivoResumen) (tablaArchivos.getRowData())).getId();
		Long id = archivoSeleccionado.getId();
		Archivo archivo = servicioProyecto.obtenerArchivo(id);
		boolean entra = false;
		boolean elimina = false;
		if (archivo != null && (archivo.getDatos() == null || archivo.getBytes().length <= 1)) {
			entra = true;
			elimina = eliminarArchivoProyectoGenerico(archivo.getId(), proyectoActual, archivo.getNombre());
		}
		if (!entra) {
			servicioGeneral.eliminarObjeto(archivo);
		} else if (elimina) {
			servicioGeneral.eliminarObjeto(archivo);
		}
		return "archivos";
	}

	public void descargarArchivo() {
		Long id = archivoSeleccionado.getId();
		// Long id = ((ArchivoResumen) (tablaArchivos.getRowData())).getId();
		descargarArchivoProyectoGenerico(id, proyectoActual.getId());
	}

	public class IdiomaInvestigador {

		private String idioma;
		private String lee;
		private String escribe;
		private String habla;

		public IdiomaInvestigador() {

		}

		public IdiomaInvestigador(String idioma, String lee, String escribe, String habla) {
			super();
			this.idioma = idioma;
			this.lee = lee;
			this.escribe = escribe;
			this.habla = habla;
		}

		public String getIdioma() {
			return idioma;
		}

		public void setIdioma(String idioma) {
			this.idioma = idioma;
		}

		public String getLee() {
			return lee;
		}

		public void setLee(String lee) {
			this.lee = lee;
		}

		public String getEscribe() {
			return escribe;
		}

		public void setEscribe(String escribe) {
			this.escribe = escribe;
		}

		public String getHabla() {
			return habla;
		}

		public void setHabla(String habla) {
			this.habla = habla;
		}

	}

	public class RedesSociales {
		private String nombre;
		private String usuario;
		private boolean borrable = false;

		public RedesSociales() {

		}

		public RedesSociales(String nombre, String usuario) {
			super();
			this.nombre = nombre;
			this.usuario = usuario;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		public boolean isBorrable() {
			return borrable;
		}

		public void setBorrable(boolean borrable) {
			this.borrable = borrable;
		}

	}

	public void cargarListaIdiomas() {
		IdiomaInvestigador ingles = new IdiomaInvestigador("Inglés", "Básico", "Básico", "Básico");
		IdiomaInvestigador aleman = new IdiomaInvestigador("Alemán", "Básico", "Básico", "Básico");
		listaIdiomasInvestigador.add(ingles);
		listaIdiomasInvestigador.add(aleman);
	}

	public void insertarRed() {
		RedesSociales oe = new RedesSociales();
		oe.setNombre("");
		oe.setUsuario("");
		listaRedesSociales.add(oe);
	}

	@Override
	protected void cargarValoresIniciales() {
		listaObjetivos = new ArrayList<ObjetivoEspecifico>();
		listaResultados = new ArrayList<ResultadoProyecto>();
		listaActividades = new ArrayList();
		listaActividadesEstancia = new ArrayList();
		temp = new ActividadMovilidadVE();
		estanciaGuardar = new MovilidadVisitanteExterior();

		listaIdiomasInvestigador = new ArrayList<IdiomaInvestigador>();
		listaRedesSociales = new ArrayList<RedesSociales>();

		listaNivelIdiomas = new ArrayList<DominioDetalle>();
		listaNivelIdiomas = servicioGeneral.obtenerObjetos(
				"select e from DominioDetalle e where e.identificador.id = '124' order by e.descripcion asc");
		tipoSuficienciaIdiomaItem = new SelectItem[listaNivelIdiomas.size()];
		for (int i = 0; i < listaNivelIdiomas.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaNivelIdiomas.get(i);
			tipoSuficienciaIdiomaItem[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}

		listaMedioDifusion = new ArrayList<DominioDetalle>();
		listaMedioDifusion = servicioGeneral.obtenerObjetos(
				"select e from DominioDetalle e where e.identificador.id = '125' order by e.descripcion asc");
		tipoMedioDifusionItem = new SelectItem[listaMedioDifusion.size()];
		for (int i = 0; i < listaMedioDifusion.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaMedioDifusion.get(i);
			tipoMedioDifusionItem[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}

		listaAreaTrabajo = new ArrayList<DominioDetalle>();
		listaAreaTrabajo = servicioGeneral.obtenerObjetos(
				"select e from DominioDetalle e where e.identificador.id = '126' order by e.descripcion asc");
		areaTrabajoItem = new SelectItem[listaAreaTrabajo.size()];
		for (int i = 0; i < listaAreaTrabajo.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaAreaTrabajo.get(i);
			areaTrabajoItem[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}

		listaComponentesProyecto = new ArrayList<DominioDetalle>();
		listaComponentesProyecto = servicioGeneral.obtenerObjetos(
				"select e from DominioDetalle e where e.identificador.id = '127' order by e.descripcion asc");
		tipoComponenteProyectoItem = new SelectItem[listaComponentesProyecto.size()];
		for (int i = 0; i < listaComponentesProyecto.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaComponentesProyecto.get(i);
			tipoComponenteProyectoItem[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}

		listaEntidadesFundacion = new ArrayList<DominioDetalle>();
		listaEntidadesFundacion = servicioGeneral.obtenerObjetos(
				"select e from DominioDetalle e where e.identificador.id = '128' order by e.descripcion asc");
		entidadesFundacionItem = new SelectItem[listaEntidadesFundacion.size()];
		for (int i = 0; i < listaEntidadesFundacion.size(); i++) {
			DominioDetalle dd = (DominioDetalle) listaEntidadesFundacion.get(i);
			entidadesFundacionItem[i] = new SelectItem(dd.getIdentificador().getTipo(), dd.getDescripcion());
			dd = null;
		}

		cargarListaIdiomas();

		tipoDocumentoInv = new TipoDocumento();

		listaTipoDocumento = servicioGeneral.obtenerListaObjetos(TipoDocumento.class);

		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];

		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}

		tipoDocumentoItem = new SelectItem[listaTipoDocumento.size()];
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			TipoDocumento td = listaTipoDocumento.get(i);
			tipoDocumentoItem[i] = new SelectItem(td.getId(), td.getNombre());
		}

		if (modConvo.equals("523")) {
			listaTipoArchivo = servicioGeneral.obtenerListaObjetos(
					"TipoArchivo e where e.id in (39, 38, 35, 36, 40, 37, 33, 3, 5) order by e.nombre");
			tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
			for (int i = 0; i < listaTipoArchivo.size(); i++) {
				TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
				tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
			}
			tipoArchivo = (TipoArchivo) listaTipoArchivo.get(0);
		} else {
			if (modConvo.equals("524")) {
				listaTipoArchivo = servicioGeneral
						.obtenerListaObjetos("TipoArchivo e where e.id in (44, 47, 45, 41, 43, 42) order by e.nombre");
				tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
				for (int i = 0; i < listaTipoArchivo.size(); i++) {
					TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
					tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
				}
				tipoArchivo = (TipoArchivo) listaTipoArchivo.get(0);
			} else {
				listaTipoArchivo = servicioGeneral.obtenerListaObjetos(
						"TipoArchivo e where e.id in (39, 46, 35, 36, 37, 33, 3) order by e.nombre");
				tipoArchivoItem = new SelectItem[listaTipoArchivo.size()];
				for (int i = 0; i < listaTipoArchivo.size(); i++) {
					TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
					tipoArchivoItem[i] = new SelectItem(ta.getId(), ta.getNombre());
				}
				tipoArchivo = (TipoArchivo) listaTipoArchivo.get(0);
			}
		}

		if (tipoEntidadItems == null) {
			// Tipos de entidad
			tipoEntidadItems = new SelectItem[3];
			tipoEntidadItems[0] = new SelectItem("F", "Financiadora");
			tipoEntidadItems[1] = new SelectItem("P", "Participante");
			tipoEntidadItems[2] = new SelectItem("R", "Responsable");

			// tipoEntidadItems[3] = new SelectItem("Participante co-ejecutora",
			// "Participante co-ejecutora");
			// tipoEntidadItems[4] = new SelectItem("Participante ejecutora",
			// "Participante ejecutora");
		}

		if (entidadItems == null) {
			// entidades contratantes
			String hql = "select ff from FuenteFinanciacion ff where ff.internaExterna like "
					+ "'E' and ff.descripcion not like '%CODIGO%' and ff.observaciones = 'ENT_REG_PRY' "
					+ "and ff.quipu = 'S' order by ff.descripcion)";
			List<FuenteFinanciacion> lista = servicioGeneral.obtenerObjetos(FuenteFinanciacion.class, hql);

			entidadItems = new SelectItem[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				FuenteFinanciacion d = (FuenteFinanciacion) lista.get(i);
				String nombre = d.getDescripcion().toUpperCase();
				if (nombre != null && nombre.length() > 80) {
					nombre = nombre.substring(0, 80) + "...";
				}
				entidadItems[i] = new SelectItem(d.getId(), nombre);
			}
		}

		listaEntidadesAdicionadas = new ArrayList<Financiacion>();

		cargarTiposActividades();
		conocimientoFunda = "Afiche/Poster";
		cambiarDifusionConvocatoria();

		tablaArchivos = new DataTable();
		listaTipoArchivo = new ArrayList();

	}

	public void adicionarActividadEstancia() {
		bErrorActividades = false;
		try {

			temp.setBErrorDescripcion(false);
			temp.setBErrorDuracion(false);
			temp.setBErrorFecha(false);
			if (temp.getDescripcion().equals("")) {
				temp.setBErrorDescripcion(true);
				temp.setErrorDescripcion("La descripción es obligatoria");
				bErrorActividades = true;
			} else {
				temp.setDescripcion(cortarCadena(temp.getDescripcion(), 200));
			}
			if (temp.getDuracion() == null || temp.getDuracion().intValue() == 0) {
				temp.setBErrorDuracion(true);
				temp.setErrorDuracion("La duración es obligatoria");
				bErrorActividades = true;
			}
			if (temp.getFecha() == null) {
				temp.setBErrorFecha(true);
				temp.setErrorFecha("La fecha es obligatoria");
				bErrorActividades = true;
			} else {
				if (temp.getFecha().before(this.fechaInicioEvento)) {
					bErrorActividades = true;
					temp.setBErrorFecha(true);

					temp.setErrorFecha("La fecha debe ser posterior a la fecha de inicio del viaje");
				} else if (temp.getFecha().after(this.fechaFinEvento)) {
					bErrorActividades = true;
					temp.setBErrorFecha(true);
					temp.setErrorFecha("La fecha debe ser anterior a la fecha de fin del viaje");
				}
			}

			if (!bErrorActividades) {
				List<TipoActividadMovilidadVE> listaTipoActividad = servicioGeneral.obtenerObjetos(
						TipoActividadMovilidadVE.class, "from TipoActividadMovilidadVE where id = '11'");
				TipoActividadMovilidadVE sd = new TipoActividadMovilidadVE();
				sd = (TipoActividadMovilidadVE) listaTipoActividad.get(0);
				temp.setTipoActividad(sd);
				listaActividadesEstancia.add(temp);
				temp = new ActividadMovilidadVE();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void eliminarActividadEstancia() {
		listaActividadesEstancia.remove(actividadMovilidadVESeleccionada);
		if (listaActividadesEstancia.size() == 0) {
		}
	}

	@Override
	public String atras() {

		return null;
	}

	@Override
	public String salir() {

		return null;
	}

	@Override
	public String salirGuardar() {

		return null;
	}

	@Override
	public String siguiente() {

		return null;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getNombreCompletoPersona() {
		return nombreCompletoPersona;
	}

	public void setNombreCompletoPersona(String nombreCompletoPersona) {
		this.nombreCompletoPersona = nombreCompletoPersona;
	}

	public String getNombreTipoDocInv() {
		return nombreTipoDocInv;
	}

	public void setNombreTipoDocInv(String nombreTipoDocInv) {
		this.nombreTipoDocInv = nombreTipoDocInv;
	}

	public String getNumDocInv() {
		return numDocInv;
	}

	public void setNumDocInv(String numDocInv) {
		this.numDocInv = numDocInv;
	}

	public String getDireccionDomicilio() {
		return direccionDomicilio;
	}

	public void setDireccionDomicilio(String direccionDomicilio) {
		this.direccionDomicilio = direccionDomicilio;
	}

	public String getTelDomicilio() {
		return telDomicilio;
	}

	public void setTelDomicilio(String telDomicilio) {
		this.telDomicilio = telDomicilio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getProgramaPregado() {
		return programaPregado;
	}

	public void setProgramaPregado(String programaPregado) {
		this.programaPregado = programaPregado;
	}

	public String getInstiPregado() {
		return instiPregado;
	}

	public void setInstiPregado(String instiPregado) {
		this.instiPregado = instiPregado;
	}

	public String getInstVinculado() {
		return instVinculado;
	}

	public void setInstVinculado(String instVinculado) {
		this.instVinculado = instVinculado;
	}

	public String getRolInst() {
		return rolInst;
	}

	public void setRolInst(String rolInst) {
		this.rolInst = rolInst;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getRedesSociales() {
		return redesSociales;
	}

	public void setRedesSociales(String redesSociales) {
		this.redesSociales = redesSociales;
	}

	public String getConocimientoFunda() {
		return conocimientoFunda;
	}

	public void setConocimientoFunda(String conocimientoFunda) {
		this.conocimientoFunda = conocimientoFunda;
	}

	public List<IdiomaInvestigador> getListaIdiomasInvestigador() {
		return listaIdiomasInvestigador;
	}

	public void setListaIdiomasInvestigador(List<IdiomaInvestigador> listaIdiomasInvestigador) {
		this.listaIdiomasInvestigador = listaIdiomasInvestigador;
	}

	public List<DominioDetalle> getListaNivelIdiomas() {
		return listaNivelIdiomas;
	}

	public void setListaNivelIdiomas(List<DominioDetalle> listaNivelIdiomas) {
		this.listaNivelIdiomas = listaNivelIdiomas;
	}

	public SelectItem[] getTipoSuficienciaIdiomaItem() {
		return tipoSuficienciaIdiomaItem;
	}

	public void setTipoSuficienciaIdiomaItem(SelectItem[] tipoSuficienciaIdiomaItem) {
		this.tipoSuficienciaIdiomaItem = tipoSuficienciaIdiomaItem;
	}

	public List<RedesSociales> getListaRedesSociales() {
		return listaRedesSociales;
	}

	public void setListaRedesSociales(List<RedesSociales> listaRedesSociales) {
		this.listaRedesSociales = listaRedesSociales;
	}

	public List<DominioDetalle> getListaMedioDifusion() {
		return listaMedioDifusion;
	}

	public void setListaMedioDifusion(List<DominioDetalle> listaMedioDifusion) {
		this.listaMedioDifusion = listaMedioDifusion;
	}

	public SelectItem[] getTipoMedioDifusionItem() {
		return tipoMedioDifusionItem;
	}

	public void setTipoMedioDifusionItem(SelectItem[] tipoMedioDifusionItem) {
		this.tipoMedioDifusionItem = tipoMedioDifusionItem;
	}

	public TipoDocumento getTipoDocumentoInv() {
		return tipoDocumentoInv;
	}

	public void setTipoDocumentoInv(TipoDocumento tipoDocumentoInv) {
		this.tipoDocumentoInv = tipoDocumentoInv;
	}

	public SelectItem[] getTipoDocumentoItem() {
		return tipoDocumentoItem;
	}

	public void setTipoDocumentoItem(SelectItem[] tipoDocumentoItem) {
		this.tipoDocumentoItem = tipoDocumentoItem;
	}

	public String getDocumentoInv() {
		return documentoInv;
	}

	public void setDocumentoInv(String documentoInv) {
		this.documentoInv = documentoInv;
	}

	public List<TipoDocumento> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public List<DominioDetalle> getListaAreaTrabajo() {
		return listaAreaTrabajo;
	}

	public void setListaAreaTrabajo(List<DominioDetalle> listaAreaTrabajo) {
		this.listaAreaTrabajo = listaAreaTrabajo;
	}

	public List<DominioDetalle> getListaComponentesProyecto() {
		return listaComponentesProyecto;
	}

	public void setListaComponentesProyecto(List<DominioDetalle> listaComponentesProyecto) {
		this.listaComponentesProyecto = listaComponentesProyecto;
	}

	public SelectItem[] getAreaTrabajoItem() {
		return areaTrabajoItem;
	}

	public void setAreaTrabajoItem(SelectItem[] areaTrabajoItem) {
		this.areaTrabajoItem = areaTrabajoItem;
	}

	public SelectItem[] getTipoComponenteProyectoItem() {
		return tipoComponenteProyectoItem;
	}

	public void setTipoComponenteProyectoItem(SelectItem[] tipoComponenteProyectoItem) {
		this.tipoComponenteProyectoItem = tipoComponenteProyectoItem;
	}

	public String getAreaTrabajo() {
		return areaTrabajo;
	}

	public void setAreaTrabajo(String areaTrabajo) {
		this.areaTrabajo = areaTrabajo;
	}

	public String getComponenteProyecto() {
		return componenteProyecto;
	}

	public void setComponenteProyecto(String componenteProyecto) {
		this.componenteProyecto = componenteProyecto;
	}

	public boolean isEsConsulta() {
		return esConsulta;
	}

	public void setEsConsulta(boolean esConsulta) {
		this.esConsulta = esConsulta;
	}

	public UIComponent getObjetoEspecifico() {
		return objetoEspecifico;
	}

	public void setObjetoEspecifico(UIComponent objetoEspecifico) {
		this.objetoEspecifico = objetoEspecifico;
	}

	public UIComponent getUiResultado() {
		return uiResultado;
	}

	public void setUiResultado(UIComponent uiResultado) {
		this.uiResultado = uiResultado;
	}

	public String getObjetivoEspecifico() {
		return objetivoEspecifico;
	}

	public void setObjetivoEspecifico(String objetivoEspecifico) {
		this.objetivoEspecifico = objetivoEspecifico;
	}

	public List<ObjetivoEspecifico> getListaObjetivos() {
		return listaObjetivos;
	}

	public void setListaObjetivos(List<ObjetivoEspecifico> listaObjetivos) {
		this.listaObjetivos = listaObjetivos;
	}

	public ObjetivoEspecifico getObjetivoTabla() {
		return objetivoTabla;
	}

	public void setObjetivoTabla(ObjetivoEspecifico objetivoTabla) {
		this.objetivoTabla = objetivoTabla;
	}

	public List<ResultadoProyecto> getListaResultados() {
		return listaResultados;
	}

	public void setListaResultados(List<ResultadoProyecto> listaResultados) {
		this.listaResultados = listaResultados;
	}

	public ResultadoProyecto getResultadoTabla() {
		return resultadoTabla;
	}

	public void setResultadoTabla(ResultadoProyecto resultadoTabla) {
		this.resultadoTabla = resultadoTabla;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public List getListaActividades() {
		return listaActividades;
	}

	public void setListaActividades(List listaActividades) {
		this.listaActividades = listaActividades;
	}

	public String getMensajeErrorActividad() {
		return mensajeErrorActividad;
	}

	public void setMensajeErrorActividad(String mensajeErrorActividad) {
		this.mensajeErrorActividad = mensajeErrorActividad;
	}

	public DataTable getTablaActividades() {
		return tablaActividades;
	}

	public void setTablaActividades(DataTable tablaActividades) {
		this.tablaActividades = tablaActividades;
	}

	public UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public List getListaTipoArchivo() {
		return listaTipoArchivo;
	}

	public void setListaTipoArchivo(List listaTipoArchivo) {
		this.listaTipoArchivo = listaTipoArchivo;
	}

	public SelectItem[] getTipoArchivoItem() {
		return tipoArchivoItem;
	}

	public void setTipoArchivoItem(SelectItem[] tipoArchivoItem) {
		this.tipoArchivoItem = tipoArchivoItem;
	}

	public List<Archivo> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<Archivo> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public DataTable getTablaArchivos() {
		return tablaArchivos;
	}

	public void setTablaArchivos(DataTable tablaArchivos) {
		this.tablaArchivos = tablaArchivos;
	}

	public String getModConvo() {
		return modConvo;
	}

	public void setModConvo(String modConvo) {
		this.modConvo = modConvo;
	}

	public String getNombreUniversidadPonencia() {
		return nombreUniversidadPonencia;
	}

	public void setNombreUniversidadPonencia(String nombreUniversidadPonencia) {
		this.nombreUniversidadPonencia = nombreUniversidadPonencia;
	}

	public String getCiudadEvento() {
		return ciudadEvento;
	}

	public void setCiudadEvento(String ciudadEvento) {
		this.ciudadEvento = ciudadEvento;
	}

	public Date getFechaInicioEvento() {
		return fechaInicioEvento;
	}

	public void setFechaInicioEvento(Date fechaInicioEvento) {
		this.fechaInicioEvento = fechaInicioEvento;
	}

	public Date getFechaFinEvento() {
		return fechaFinEvento;
	}

	public void setFechaFinEvento(Date fechaFinEvento) {
		this.fechaFinEvento = fechaFinEvento;
	}

	public String getResumenPonencia() {
		return resumenPonencia;
	}

	public void setResumenPonencia(String resumenPonencia) {
		this.resumenPonencia = resumenPonencia;
	}

	public String getActividadSel() {
		return actividadSel;
	}

	public void setActividadSel(String actividadSel) {
		this.actividadSel = actividadSel;
	}

	public SelectItem[] getActividadSelItem() {
		return actividadSelItem;
	}

	public void setActividadSelItem(SelectItem[] actividadSelItem) {
		this.actividadSelItem = actividadSelItem;
	}

	public boolean isbErrorActividades() {
		return bErrorActividades;
	}

	public void setbErrorActividades(boolean bErrorActividades) {
		this.bErrorActividades = bErrorActividades;
	}

	public List getListaActividadesEstancia() {
		return listaActividadesEstancia;
	}

	// public void setListaActividadesEstancia(List listaActividadesEstancia)
	// {
	// this.listaActividadesEstancia = listaActividadesEstancia;
	// }

	public ActividadMovilidadVE getActividadMovilidadVESeleccionada() {
		return actividadMovilidadVESeleccionada;
	}

	public void setActividadMovilidadVESeleccionada(ActividadMovilidadVE actividadMovilidadVESeleccionada) {
		this.actividadMovilidadVESeleccionada = actividadMovilidadVESeleccionada;
	}

	public ActividadMovilidadVE getTemp() {
		return temp;
	}

	public void setTemp(ActividadMovilidadVE temp) {
		this.temp = temp;
	}

	public Convocatoria getConvocatoriaActual() {
		return convocatoriaActual;
	}

	public void setConvocatoriaActual(Convocatoria convocatoriaActual) {
		this.convocatoriaActual = convocatoriaActual;
	}

	public MovilidadVisitanteExterior getEstanciaGuardar() {
		return estanciaGuardar;
	}

	public void setEstanciaGuardar(MovilidadVisitanteExterior estanciaGuardar) {
		this.estanciaGuardar = estanciaGuardar;
	}

	public Proyecto getProyectoGuardar() {
		return proyectoGuardar;
	}

	public void setProyectoGuardar(Proyecto proyectoGuardar) {
		this.proyectoGuardar = proyectoGuardar;
	}

	public Persona getPersonaGuardar() {
		return personaGuardar;
	}

	public void setPersonaGuardar(Persona personaGuardar) {
		this.personaGuardar = personaGuardar;
	}

	public boolean isProyectoExiste() {
		return proyectoExiste;
	}

	public void setProyectoExiste(boolean proyectoExiste) {
		this.proyectoExiste = proyectoExiste;
	}

	public DataTable getTablaRedSocial() {
		return tablaRedSocial;
	}

	public void setTablaRedSocial(DataTable tablaRedSocial) {
		this.tablaRedSocial = tablaRedSocial;
	}

	public RedesSociales getRedSeleccionada() {
		return redSeleccionada;
	}

	public void setRedSeleccionada(RedesSociales redSeleccionada) {
		this.redSeleccionada = redSeleccionada;
	}

	public Preinscripcion_ECP getPreInv() {
		return preInv;
	}

	public void setPreInv(Preinscripcion_ECP preInv) {
		this.preInv = preInv;
	}

	public void setListaActividadesEstancia(List<ActividadMovilidadVE> listaActividadesEstancia) {
		this.listaActividadesEstancia = listaActividadesEstancia;
	}

	public boolean isMostrarGrupoInvestigacion() {
		return mostrarGrupoInvestigacion;
	}

	public void setMostrarGrupoInvestigacion(boolean mostrarGrupoInvestigacion) {
		this.mostrarGrupoInvestigacion = mostrarGrupoInvestigacion;
	}

	public boolean isMostrarEmprendedores() {
		return mostrarEmprendedores;
	}

	public void setMostrarEmprendedores(boolean mostrarEmprendedores) {
		this.mostrarEmprendedores = mostrarEmprendedores;
	}

	public String getOpcionesConv2() {
		return opcionesConv2;
	}

	public void setOpcionesConv2(String opcionesConv2) {
		this.opcionesConv2 = opcionesConv2;
	}

	public String getObjetoClasificacionGrupoEmpr() {
		return objetoClasificacionGrupoEmpr;
	}

	public void setObjetoClasificacionGrupoEmpr(String objetoClasificacionGrupoEmpr) {
		this.objetoClasificacionGrupoEmpr = objetoClasificacionGrupoEmpr;
	}

	public String getNombreDirectorGrupo() {
		return nombreDirectorGrupo;
	}

	public void setNombreDirectorGrupo(String nombreDirectorGrupo) {
		this.nombreDirectorGrupo = nombreDirectorGrupo;
	}

	public String getTipoDocumentoDirectorGrupo() {
		return tipoDocumentoDirectorGrupo;
	}

	public void setTipoDocumentoDirectorGrupo(String tipoDocumentoDirectorGrupo) {
		this.tipoDocumentoDirectorGrupo = tipoDocumentoDirectorGrupo;
	}

	public String getDocumentoDirectorGrupo() {
		return documentoDirectorGrupo;
	}

	public void setDocumentoDirectorGrupo(String documentoDirectorGrupo) {
		this.documentoDirectorGrupo = documentoDirectorGrupo;
	}

	public String getFormacionExperto() {
		return formacionExperto;
	}

	public void setFormacionExperto(String formacionExperto) {
		this.formacionExperto = formacionExperto;
	}

	public String getNombreDocente() {
		return nombreDocente;
	}

	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}

	public String getDocumentoVisitante() {
		return documentoVisitante;
	}

	public void setDocumentoVisitante(String documentoVisitante) {
		this.documentoVisitante = documentoVisitante;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public SelectItem[] getTipoEntidadItems() {
		return tipoEntidadItems;
	}

	public void setTipoEntidadItems(SelectItem[] tipoEntidadItems) {
		this.tipoEntidadItems = tipoEntidadItems;
	}

	public String getTipoEntidadSel() {
		return tipoEntidadSel;
	}

	public void setTipoEntidadSel(String tipoEntidadSel) {
		this.tipoEntidadSel = tipoEntidadSel;
	}

	public SelectItem[] getEntidadItems() {
		return entidadItems;
	}

	public void setEntidadItems(SelectItem[] entidadItems) {
		this.entidadItems = entidadItems;
	}

	public String getEntidadSel() {
		return entidadSel;
	}

	public void setEntidadSel(String entidadSel) {
		this.entidadSel = entidadSel;
	}

	public Long getValorEntidad() {
		return valorEntidad;
	}

	public void setValorEntidad(Long valorEntidad) {
		this.valorEntidad = valorEntidad;
	}

	public Long getValorEntidadEsp() {
		return valorEntidadEsp;
	}

	public void setValorEntidadEsp(Long valorEntidadEsp) {
		this.valorEntidadEsp = valorEntidadEsp;
	}

	public List<Financiacion> getListaEntidadesAdicionadas() {
		return listaEntidadesAdicionadas;
	}

	public void setListaEntidadesAdicionadas(List<Financiacion> listaEntidadesAdicionadas) {
		this.listaEntidadesAdicionadas = listaEntidadesAdicionadas;
	}

	public Financiacion getEntidadSeleccionada() {
		return entidadSeleccionada;
	}

	public void setEntidadSeleccionada(Financiacion entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}

	public String getConvOriginal2() {
		return convOriginal2;
	}

	public void setConvOriginal2(String convOriginal2) {
		this.convOriginal2 = convOriginal2;
	}

	public List<DominioDetalle> getListaEntidadesFundacion() {
		return listaEntidadesFundacion;
	}

	public void setListaEntidadesFundacion(List<DominioDetalle> listaEntidadesFundacion) {
		this.listaEntidadesFundacion = listaEntidadesFundacion;
	}

	public SelectItem[] getEntidadesFundacionItem() {
		return entidadesFundacionItem;
	}

	public void setEntidadesFundacionItem(SelectItem[] entidadesFundacionItem) {
		this.entidadesFundacionItem = entidadesFundacionItem;
	}

	public String getEntidadConocimientoFunda() {
		return entidadConocimientoFunda;
	}

	public void setEntidadConocimientoFunda(String entidadConocimientoFunda) {
		this.entidadConocimientoFunda = entidadConocimientoFunda;
	}

	public boolean isMostrarDatosEntidadDifusion() {
		return mostrarDatosEntidadDifusion;
	}

	public void setMostrarDatosEntidadDifusion(boolean mostrarDatosEntidadDifusion) {
		this.mostrarDatosEntidadDifusion = mostrarDatosEntidadDifusion;
	}

	public Archivo getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(Archivo archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public boolean isMostrarBotones() {
		return mostrarBotones;
	}

	public void setMostrarBotones(boolean mostrarBotones) {
		this.mostrarBotones = mostrarBotones;
	}

}
