package co.edu.unal.hermes.vista.aval;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.ArchivoAval;
import co.edu.unal.hermes.modelo.ArchivoConvocatoriaExterna;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.AvalActividad;
import co.edu.unal.hermes.modelo.AvalComiteEtica;
import co.edu.unal.hermes.modelo.AvalSubTipos;
import co.edu.unal.hermes.modelo.CoinvestigadorAval;
import co.edu.unal.hermes.modelo.ColeccionTipoObjeto;
import co.edu.unal.hermes.modelo.Convenio;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.CorreoPlantilla;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.DependenciaAreaResponsabilidad;
import co.edu.unal.hermes.modelo.Dominio;
import co.edu.unal.hermes.modelo.DominioDetalle;
import co.edu.unal.hermes.modelo.EntidadArticulo;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.FuenteFinanciacion;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.LaboratorioAval;
import co.edu.unal.hermes.modelo.MontoAno;
import co.edu.unal.hermes.modelo.PalabraClave;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoCompromiso;
import co.edu.unal.hermes.modelo.ResultadoAval;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.SemilleroGrupo;
import co.edu.unal.hermes.modelo.TipoArchivo;
import co.edu.unal.hermes.modelo.TipoConvenio;
import co.edu.unal.hermes.modelo.TipoRubro;
import co.edu.unal.hermes.modelo.Tipos;
import co.edu.unal.hermes.modelo.correo.Correo;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioVista;
import co.edu.unal.hermes.modelo.laboratorios.PersonaLaboratorio;
import co.edu.unal.hermes.vista.aval.base.BaseManejadorSolicitarAval;
import co.edu.unal.hermes.vista.laboratorios.ManejadorUtilidadesLaboratorios;

public class ManejadorSolicitudAvalHome extends BaseManejadorSolicitarAval {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	org.primefaces.model.UploadedFile archivoCargado;

	private boolean interSedes = false;
	private Grupo grupoEliminar;

	private List<SelectItem> participanteMovEventosItem;
	private List<SelectItem> participanteMovPasantiaItem;

	private List<Financiacion> listaEntidadesCoejecutorasPry;
	private List<EntidadArticulo> listaFinanciacionExternaME;
	private List<EntidadArticulo> listaEntidadesParticipantesFortalecimiento;
	private List<EntidadArticulo> listaEntidadesCoorganizadorasAval;

	private ArchivoConvocatoriaExterna archivoExterno; // archivos conv externa
	private String tipoDocParticipante;
	private String documentoParticipante;
	private Long horasParticipante = 0L;
	private String nombreProfesorVisitante;
	private CoinvestigadorAval participanteEliminar;
	private Long mesesParticipante = 0L;
	private List<CoinvestigadorAval> listaParticipantesJornadaDocente;
	private List<AvalActividad> listaActividades;
	private AvalActividad actividadSeleccionada;
	private String descripcionActividad;
	private Long mesInicialActividad = 0L;
	private Long duracionMesesActividad = 0L;
	private MontoAno monto;
	private String resultado;
	private List<ResultadoAval> listaResultados;
	private DataTable tablaResultados;
	private String mensajeErrorResultados;
	private ResultadoAval resultadoTabla;
	private List<CoinvestigadorAval> listaParticipantesAval;
	private String entidadProfesorVisitante;
	private String entidadFinanciacionExterna;
	private String rubro;
	private Long valorRubroEspecie = 0L;
	private Long valorRubroFrescos = 0L;
	private EntidadArticulo entidadEliminar;
	private String entidadFortalecimiento;
	private String descripcionRubro;
	private String entidadCooperante;
	private String sedeSel;
	private Dependencia sedeSeleccionada;
	private boolean tieneCompromisosPendientes;
	private List<ProyectoCompromiso> listaCompromisosProyectos;
	private List<Aval> listaAvalesFormulacionDevueltos;
	private boolean proyectoTieneAvalFormulacionODevuelto = false;
	private String idAvalEliminar;

	// Para ubicar mensajes de error
	private UIComponent financiacionExterna;
	private boolean mostrarInfoConvocatoriaProyReg = false;

	private List<Laboratorio> listaLaboratorios;
	private SelectItem[] laboratoriosFacItem;
	private Long idLaboratorio;
	private List<LaboratorioAval> listaLaboratoriosAval;
	private List<LaboratorioAval> listaLaboratoriosAvalBorrados;
	private LaboratorioAval laboratorioSeleccionado;
	private LaboratorioVista laboratorioVistaSeleccionado;
	private ManejadorUtilidadesLaboratorios mUL;

	private boolean formatoExterno = false;
	private String idTipoArchivo;
	private boolean tieneDependenciaSedePresencia;
	private boolean remitirSedePresencia;

	private String sedeFiltroGrupo;
	private SelectItem[] sedeItem;
	private SelectItem[] gruposItem;
	private String grupoSeleccionado;
	private boolean huboCambiosGrupos;

	private Dependencia dependenciaPresenciaNacional;
	private String subTipoAvalEspecificacion;
	private AvalSubTipos avalSubTipoEspecificacion;
	private AvalSubTipos avalSubTipoSeleccionado;
	private SelectItem[] listaSubTipoEspecificacionItem;

	// para aval de requisitos de regalias
	private Aval avalPresentacionRegalias;
	private Date fechaResultadosRegalias;

	public ManejadorSolicitudAvalHome() {

		Calendar c = Calendar.getInstance();
		int limit = c.get(Calendar.YEAR) + 10;
		listaAno = new SelectItem[limit - 2013 + 1];
		int index = 0;
		for (int idx = 2013; idx <= limit; idx++) {
			listaAno[index] = new SelectItem(String.valueOf(idx), String.valueOf(idx));
			index++;
		}

		setHuboCambiosGrupos(false);
		investigadorActual = servicioPersona
				.obtenerInvestigador(((Persona) sesion.getAttribute(VARIABLE_PERSONA_SESION_ACTUAL)).getId());
		consultarCompromisosPendientes();
		esConsulta = false;
		tieneDependenciaSedePresencia = false;
		remitirSedePresencia = false;
		if ((Boolean) sesion.getAttribute("esEstudianteLider") != null
				&& (Boolean) sesion.getAttribute("esEstudianteLider")) {
			esConsulta = true;
		}
		esEdicion = false;
		esAvalNuevo = false;

		if (sesion.getAttribute(SESION_MODO_CONSULTA) != null && (Boolean) sesion.getAttribute(SESION_MODO_CONSULTA)) {
			setEsConsulta((Boolean) sesion.getAttribute(SESION_MODO_CONSULTA));
		} else if (sesion.getAttribute(SESION_MODO_EDICION) != null
				&& (Boolean) sesion.getAttribute(SESION_MODO_EDICION)) {
			setEsEdicion((Boolean) sesion.getAttribute(SESION_MODO_EDICION));
		}

		verificarRevisionFacultad();

		// Iniciacializacion de listas
		tiposAval();
		convenioIngresado = new Convenio();
		convenioIngresado.setEntidad(new FuenteFinanciacion());
		listaParticipantesJornadaDocente = new ArrayList<CoinvestigadorAval>();
		listaResultados = new ArrayList<ResultadoAval>();
		listaParticipantesAval = new ArrayList<CoinvestigadorAval>();
		listaFinanciacionExternaME = new ArrayList<EntidadArticulo>();
		listaEntidadesCoorganizadorasAval = new ArrayList<EntidadArticulo>();
		listaActividades = new ArrayList<AvalActividad>();

		// Verificación si el aval existe y es consulta o edición
		if (sesion.getAttribute("idAval") != null) {
			Long id = (Long) sesion.getAttribute("idAval");
			List<Aval> lista = servicioGeneral.obtenerAval(id.toString());

			if (!esListaVacia(lista)) {
				aval = lista.get(0);
				setInterSedes(false);
				if (!esCadenaVacia(aval.getInterSedes()) && "S".equals(aval.getInterSedes())) {
					setInterSedes(true);
				}
				if (!aval.isEsGrupoInvestigacion() && !aval.isEsInvestigadorIndependiente()) {
					consultarProyecto();
				} else {
					habilitarCampos();
				}
				if (!aval.isEsJornadaDocente() && !aval.isEsRegalias() && !aval.isEsPaedRegalias()
						&& !aval.isEsEtico()) {
					cargarConvocatoriasExternas();
				}
				if (aval.getTieneConvocatoriaProyecto() != null) {
					if (aval.getTieneConvocatoriaProyecto().equals("SI")) {
						mostrarInfoConvocatoriaProyReg = true;
						consultarProyecto();
						cargarEntidadesConvocantes();
						cargarConvocatoriasExternas();
					}
				}

				consultarArchivos();
				for (ArchivoAval archivo : listaArchivos) {
					if (archivo.getTipo() != null
							&& (archivo.getTipo().equals("3") || archivo.getTipo().equals("FE"))) {
						formatoExterno = true;
					}
				}
			} else {
				esAvalNuevo = true;
			}
		} else {
			esAvalNuevo = true;
		}
		inicializarNuevoAval();
		if (!esAvalNuevo && aval.getTipo().equals("CIN") && aval.getTipoAvalCIN() == null) {
			aval.setTipoAvalCIN("CI");
		}
		String query = "select #id e.id, #nombre e.nombre from Laboratorio e where e.activo = '1' order by e.nombre asc";
		listaLaboratorios = servicioGeneral.obtenerObjetosLimitado(Laboratorio.class, query);
		laboratoriosFacItem = new SelectItem[listaLaboratorios.size()];
		for (int i = 0; i < listaLaboratorios.size(); i++) {
			Laboratorio dd = (Laboratorio) listaLaboratorios.get(i);
			laboratoriosFacItem[i] = new SelectItem(dd.getId(), dd.getNombre());
			dd = null;
		}
		if (aval.getAviId() != null) {
			query = "select #laboratorio e.laboratorio, #aval e.aval from LaboratorioAval e, Aval a where e.aval.id = a.id and a.id="
					+ aval.getAviId();
			setListaLaboratoriosAval(servicioGeneral.obtenerObjetosLimitado(LaboratorioAval.class, query));
		} else {
			setListaLaboratoriosAval(new ArrayList<LaboratorioAval>());
		}
		listaLaboratoriosAvalBorrados = new ArrayList<LaboratorioAval>();
		laboratorioVistaSeleccionado = new LaboratorioVista();
		mUL = new ManejadorUtilidadesLaboratorios();
		actualizarTipo();

		if (aval.isEsEtico()) {
			tiposRecursoAvalEtico = servicioGeneral
					.selectItemHijosDeTiposValorObjetoSeleccione(Tipos.TIPOS_TIPO_RECURSO_AVAL_ETICO);
			diasFaltantesPermitirRecurso = calcularDiasFaltantesPermitirRecurso();
			permitirInterponerRecurso = diasFaltantesPermitirRecurso >= 0 && diasFaltantesPermitirRecurso < 14;
			esConsultaRecurso = !esNulo(aval.getAvalTieneRecurso()) && aval.getAvalTieneRecurso().equals("SI")
					&& !esNulo(aval.getFechaRecurso());

			esConsultaQueja = !esNulo(aval.getAvalTieneQueja()) && aval.getAvalTieneQueja().equals("SI")
					&& !esNulo(aval.getFechaQueja());
		}

		palabraClave = new PalabraClave();
	}

	public int calcularDiasFaltantesPermitirRecurso() {
		double dias = -1;

		if (!esNulo(aval.getFechaAvalCEPI())) {
			long fechaInicialMs = aval.getFechaAvalCEPI().getTime();
			long fechaFinalMs = getToday().getTime();
			long diferencia = fechaFinalMs - fechaInicialMs;
			dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
		}

		return (int) dias;
	}

	private void inicializarNuevoAval() {
		if (esAvalNuevo) {
			aval = new Aval();
			aval.setTipo("");
			aval.setIdProyecto(0L);
			aval.setConvenio(new Convenio());
		}
	}

	/**
	 * Consulta y lista los proyectos sobre los que se puede solicitar el tipo de
	 * aval seleccionado
	 */
	public void cargarPosiblesOpciones() {
		nuevaConvocatoria = false;
		consultarListadoOpciones();
		if (habilitarCampos) {
			habilitarCampos();
			habilitarCampos = false;
		}
	}

	/**
	 * Consulta información del proyecto que se almacena en la tabla HER_AVAL
	 */
	public void consultarProyecto() {
		tieneDependenciaSedePresencia = false;
		if (!"".equals(aval.getIdProyecto().toString()) && aval.getIdProyecto() != 0L) {

			fichaSeleccionada = this.servicioProyecto.obtenerProyecto(aval.getIdProyecto(),
					ProyectoDAOHibernate.DATOS_PARA_AVAL);

			if (fichaSeleccionada != null) {
				fichaSeleccionada.setNombreConvocatoriaPadre(
						((Convocatoria) fichaSeleccionada.getModalidad()).getPadre().getTitulo());
				validarProyectoAval();

				if (aval.isEsRequisitosRegalias()) {
					if (!consultarAvalPresentacionRegalias()) {
						mensajeError("No se encontró el aval de presentación de regalías asociado al proyecto");
					}
				}

				if (esEdicion || esConsulta) {
					listaFichasItems.add(new SelectItem(fichaSeleccionada.getId(),
							fichaSeleccionada.getId() + " - " + fichaSeleccionada.getNombre()));
				} else {
					aval.setAviEspecieotros(0L);
					aval.setAviFrescosunal(0L);
					aval.setAviEspecieunal(0L);
					aval.setValorPersonalTotal(0L);
					aval.setAviMontoEntConv(0L);
					aval.setAviMontoFinal(0L);
				}
				consultarDatosEspecificosPry(fichaSeleccionada);

				List<DependenciaAreaResponsabilidad> prydep = servicioGeneral.obtenerObjetos(
						DependenciaAreaResponsabilidad.class,
						"select d from DependenciaAreaResponsabilidad d where d.proyecto.id = '"
								+ fichaSeleccionada.getId() + "'");

				if (prydep != null && prydep.size() > 0) {
					for (int i = 0; i < prydep.size(); i++) {
						DependenciaAreaResponsabilidad dep = prydep.get(i);
						if (dep.getDependencia().getSede().isEsSedePresenciaNacional()
								&& dep.getDependencia().getSede().getId().equals(Sede.TUMACO)) {
							tieneDependenciaSedePresencia = true;
							setDependenciaPresenciaNacional(dep.getDependencia());
							break;
						}
					}
				}
			} else {
				mensajeError("No se encontró información del proyecto seleccionado");
			}
		} else {
			mensajeError("Ha ocurrido un problema al consultar el proyecto seleccionado o no se ha seleccionado uno");
		}
		convocatoria = new ConvocatoriaExterna();
		listaArchivosConvocatoriaExterna = new ArrayList<ArchivoConvocatoriaExterna>();
	}

	public boolean consultarAvalPresentacionRegalias() {

		String hqlAvalProyecto = "select #aviId e.aviId, #aviConvocatoria e.aviConvocatoria, #aviEntidad e.aviEntidad "
				+ "from Aval e " + "where e.aviEstado in ('D','V') and e.tipo in ('" + Aval.TIPO_CONVOCATORIA_REGALIAS
				+ "','" + Aval.TIPO_PAED_REGALIAS + "') " + "and e.idProyecto = " + aval.getIdProyecto().toString();

		List<Aval> listaAvalesRegalias = servicioGeneral.obtenerObjetosLimitado(Aval.class, hqlAvalProyecto);
		if (listaAvalesRegalias.isEmpty()) {
			return false;
		} else {
			avalPresentacionRegalias = new Aval();
			avalPresentacionRegalias = listaAvalesRegalias.get(0);
			String hqlConvocatoriaExterna = "select #id e.id, #entidad e.entidad, #fechaResultados e.fechaResultados, #fechaCierre e.fechaCierre , #fechaMaxRegistro e.fechaMaxRegistro "
					+ "from ConvocatoriaExterna e " + "where e.id = " + avalPresentacionRegalias.getAviConvocatoria();

			List<ConvocatoriaExterna> convocatoriaAvalRegalias = servicioGeneral
					.obtenerObjetosLimitado(ConvocatoriaExterna.class, hqlConvocatoriaExterna);
			if (convocatoriaAvalRegalias.isEmpty()) {
				return false;
			} else {
				aval.setAviConvocatoria(avalPresentacionRegalias.getAviConvocatoria());
				aval.setAviEntidad(avalPresentacionRegalias.getAviEntidad());
				cargarConvocatoriasExternas();
				convocatoria = convocatoriaAvalRegalias.get(0);
				fechaResultadosRegalias = convocatoria.getFechaResultados();

			}
			return true;

		}
	}

	public void validarProyectoAval() {

		String condicionEticos = "";
		if (aval.getTipo().equals(Aval.TIPO_ETICO)) {
			condicionEticos = "and e.tipo not in ('" + Aval.TIPO_INVESTIGACION + "','" + Aval.TIPO_REGALIAS + "','"
					+ Aval.TIPO_PAED_REGALIAS + "','" + Aval.TIPO_CONVOCATORIA_REGALIAS + "','"
					+ Aval.TIPO_REGALIAS_VERIF_REQ + "')";
		} else if (aval.getTipo().equals(Aval.TIPO_INVESTIGACION) || aval.getTipo().equals(Aval.TIPO_REGALIAS)
				|| aval.getTipo().equals(Aval.TIPO_PAED_REGALIAS)
				|| aval.getTipo().equals(Aval.TIPO_CONVOCATORIA_REGALIAS)
				|| aval.getTipo().equals(Aval.TIPO_REGALIAS_VERIF_REQ)) {
			condicionEticos = "and e.tipo not in ('" + Aval.TIPO_ETICO + "')";
		}

		String hqlAvalProyecto = "select #aviId e.aviId, #aviTitulo e.aviTitulo, #fechaUltimoEnvio e.fechaUltimoEnvio, #aviEstado e.aviEstado "
				+ "from Aval e " + "where e.aviEstado in ('P','C') " + condicionEticos + "and e.idProyecto = "
				+ aval.getIdProyecto();
		listaAvalesFormulacionDevueltos = servicioGeneral.obtenerObjetosLimitado(Aval.class, hqlAvalProyecto);
		if (!esEdicion) {
			if (!listaAvalesFormulacionDevueltos.isEmpty()) {
				proyectoTieneAvalFormulacionODevuelto = true;
			} else {
				proyectoTieneAvalFormulacionODevuelto = false;
			}
		} else {
			proyectoTieneAvalFormulacionODevuelto = false;
		}
	}

	public void eliminarSolicitudAval() {
		if (idAvalEliminar != null) {
			List<Aval> lista = servicioGeneral.obtenerAval(idAvalEliminar);
			if (!esListaVacia(lista)) {
				Aval aval = lista.get(0);
				aval.setAviEstado(Aval.BORRADO);
				servicioGeneral.guardarObjeto(aval);
				crearHistoricoEstadoAval(aval, cargarPersonaActual(), "D");
				validarProyectoAval();
			}
		}
	}

	public void consultarDatosEspecificosPry(Proyecto fichaSeleccionada) {

		this.aval.setIdProyecto(fichaSeleccionada.getId());
		this.aval.setAviTitulo(controlTamanoCadena(fichaSeleccionada.getNombre(), 1000));

		if (aval.isEsProyectoInvestigacion() || aval.isEsRegalias() || aval.isEsPaedRegalias()
				|| aval.isEsConvocatoriaRegalias() || aval.isEsRequisitosRegalias()) {
			obtenerListaEntidadesParticipantesPry();

			if (!esConsulta) {
				Long totalPersonal = 0L;
				totalPersonal = totalPersonal + fichaSeleccionada.getValorPersonalTotal();
				totalPersonal = totalPersonal + fichaSeleccionada.getValorAdministrativoTotal();
				aval.setValorPersonalTotal(totalPersonal);
				aval.setAviFrescosunal(fichaSeleccionada.getContrapartidaEfectivo());
				aval.setAviEspecieunal(fichaSeleccionada.getContrapartidaEspecieTotal());
				aval.setAviEspecieotros(fichaSeleccionada.getValorEspecieCalculado());

				/*
				 * if (esAvalNuevo && (fichaSeleccionada.getRolUniversidad() != null &&
				 * !"ROL_COOPER".equals(fichaSeleccionada.getRolUniversidad()) &&
				 * !"ROL_PARTICIP".equals(fichaSeleccionada.getRolUniversidad()))) {
				 */
				if (esAvalNuevo || esEdicion) {
					Long totalExterno = fichaSeleccionada.getMontofinanciarCalculado();
					if (totalExterno > 0L) {
						aval.setAviMontoEntConv(fichaSeleccionada.getMontofinanciarCalculado());
						noModificar = true;
					} else {
						aval.setAviMontoEntConv(fichaSeleccionada.getContrapartidaEfectivo());
						noModificar = false;
					}

				}
			}
		}

		if (!esConsulta && aval.isEsJornadaDocente()) {
			aval.setDuracion(fichaSeleccionada.getDuracion());
		}

		if (esAvalNuevo && aval.isEsProyectoContrapartida()) {
			aval.setAviFrescosunal(fichaSeleccionada.getContrapartidaEfectivo());
		}

		if (fichaSeleccionada.getRolUniversidad() != null) {
			this.aval.setModalidad(fichaSeleccionada.getRolUniversidad());
		}

		if (fichaSeleccionada.getMecanismoParticipacion() != null) {
			this.aval.setMecanismo(fichaSeleccionada.getMecanismoParticipacion());
		}

		if (!aval.isEsMovilidadEvento() && !aval.isEsJornadaDocente()) {
			actualizarTotal();
		}
		habilitarCampos();
	}

	public void consultarCompromisosPendientes() {
		try {
			String consultaProyectosCompromiso = "select e " + "from ProyectoCompromiso e, InvestigadorProyecto i "
					+ "where e.proyecto.estadoProyecto.id = 'A' " + "and e.numeroNotificaciones <> 0 "
					+ "and e.cumplido = 'N' " + "and i.proyecto.id = e.proyecto.id "
					+ "and i.investigador.id.documento = '" + investigadorActual.getId().getDocumento()
					+ "' and i.investigador.id.tipoDocumento = '" + investigadorActual.getId().getTipoDocumento()
					+ "' and  nvl(e.fechaVencProrroga,e.fechaVencimiento) < trunc(sysdate)";
			listaCompromisosProyectos = servicioGeneral.obtenerObjetos(ProyectoCompromiso.class,
					consultaProyectosCompromiso);

			if (listaCompromisosProyectos != null) {
				if (listaCompromisosProyectos.size() > 0) {
					tieneCompromisosPendientes = true;
				} else {
					tieneCompromisosPendientes = false;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void validarSiFinanConvo() {
		if (aval.getTieneConvocatoriaProyecto().equals("SI")) {
			mostrarInfoConvocatoriaProyReg = true;
			cargarEntidadesConvocantes();
		} else {
			mostrarInfoConvocatoriaProyReg = false;
		}

	}

	/**
	 * Se realizan las consultas necesarias de acuerdo al tipo de aval
	 */
	public void habilitarCampos() {
		if (!aval.isEsJornadaDocente() && !aval.isEsRegalias() && !aval.isEsPaedRegalias()) {
			cargarEntidadesConvocantes();
		}

		if (aval.isEsRequiereTerminos()) {
			cargarTerminos();
		}

		if (aval.isEsRegalias() || aval.isEsPaedRegalias() || aval.isEsConvocatoriaRegalias()
				|| aval.isEsRequisitosRegalias()) {
			cargarDepartamentos();
			cargarRegiones();
			cambiarCiudad();
			cargarSedes();
			if (aval.isEsConvocatoriaRegalias() || aval.isEsRequisitosRegalias()) {
				aval.setTieneConvocatoriaProyecto("SI");
				mostrarInfoConvocatoriaProyReg = true;
				cargarEntidadesConvocantes();
			}
		}

		if (aval.isEsMovilidadEvento()) {
			cargarTiposDocumento();
			cargarPaises();
			cargarParticipantesMovEventos();
			cargarParticipantesMovPasantia();
			obtenerListaResultados();
			dependenciasEjecucion();
			listaRubrosQuipu();
			obtenerListaEntidadesFortalecimiento();
			obtenerListaEntidadesCoorganizadoras();
			obtenerListaFinanciacion();
		}

		if (aval.isEsProyectoContrapartida()) {
			obtenerListaFinanciacion();
			dependenciasEjecucion();
			listaRubrosQuipu();
		}

		if (aval.isEsJornadaDocente()) {
			obtenerListaParticipantesJornadaDocente();
			obtenerListaActividadesAval();
		}

		if (aval.isEsEtico())
			cargarListaComitesEtica();

		if (aval.isEsArticuloInvestigacion()) {
			cargarListaArticulosCientificos();
		}

		cargarGruposInvestigacion();
		cargarConvenios();
		cargarSedesGrupos();
		cargarListaSubTiposEspecificacion();
		avalesInvestigador(aval.getTipo());
		cargarListaTipoArchivosInicial();
	}

	private void cargarListaTipoArchivosInicial() {
		listaTipoArchivo = servicioGeneral.obtenerListaObjetos("TipoArchivo e where e.parametro in ('" + aval.getTipo()
				+ "') or e.id = 0 and e.estado in ('A') order by e.nombre");
		selTipoArchivoAval = new SelectItem[listaTipoArchivo.size()];
		for (int i = 0; i < listaTipoArchivo.size(); i++) {
			TipoArchivo ta = (TipoArchivo) listaTipoArchivo.get(i);
			String info = "";
			if (ta.isObligatorio()) {
				info = "(Obligatorio)";
			} else {
				info = "(Opcional)";
			}
			selTipoArchivoAval[i] = new SelectItem(ta.getId(), ta.getNombre() + " " + info);
		}
	}

	/**
	 * Consultar convenio seleccionado
	 */
	public void consultarConvenio() {

		if (aval.getConvenio().getId() != null && !"0".equals(aval.getConvenio().getId())
				&& !"-1000".equals(aval.getConvenio().getId())) {
			List<Convenio> convenioSeleccionado = servicioGeneral.obtenerObjetoXID(Convenio.class,
					aval.getConvenio().getId().toString());
			if (!esListaVacia(convenioSeleccionado)) {
				aval.setConvenio(convenioSeleccionado.get(0));
			}
		} else {
			nuevaConvocatoria = false;
			mensajeError("No se ha seleccionado un convenio");
		}
	}

	/**
	 * Guardar archivo del convenio
	 */
	public void guardarArchivoConvenio(FileUploadEvent event) {

		TipoConvenio tc = new TipoConvenio();
		tc.setId(1L);
		convenioIngresado.setTipo(tc);
		convenioIngresado.setEstado("I");
		servicioGeneral.guardarObjeto(convenioIngresado);
		archivoCargar = event.getFile();
		cargarArchivoDisco(archivoCargar, "HER_CONVENIO", convenioIngresado.getId().toString());
		int i = archivoCargar.getFileName().lastIndexOf("\\");
		this.convenioIngresado.setArchivo(archivoCargar.getFileName().substring(i + 1));
	}

	/**
	 * Consulta entidades participantes en el proyecto
	 */
	public void obtenerListaEntidadesParticipantesPry() {
		listaEntidadesCoejecutorasPry = new ArrayList<Financiacion>();
		String dpnsql = "select f from Financiacion f where f.tipoEntidad='P' and f.proyecto.id='"
				+ aval.getIdProyecto() + "' order by f.id";
		listaEntidadesCoejecutorasPry = servicioGeneral.obtenerObjetos(Financiacion.class, dpnsql);
	}

	/**
	 * Obtiene el total del aporte de entidades externas a las movilidades/eventos
	 */
	public Long getTotalOtrasEntidadesMov() {
		Long total = 0L;
		boolean suma = false;
		if (esListaVacia(listaFinanciacionExternaME)) {
			return total;
		}
		for (Iterator<EntidadArticulo> iterador = listaFinanciacionExternaME.iterator(); iterador.hasNext();) {
			EntidadArticulo entidadParticipante = (EntidadArticulo) iterador.next();

			if (!FUENTE_FINANCIACION_UNAL.equals(entidadParticipante.getEntidad().getId())
					&& (aval.getAviEntidad() == null || (aval.getAviEntidad() != null
							&& !entidadParticipante.getEntidad().getId().equals(aval.getAviEntidad())))) {
				suma = true;
			}

			if (suma) {
				total = total + entidadParticipante.getEspecie() + entidadParticipante.getFrescos();
				suma = false;
			}
		}
		return total;
	}

	public void actualizarTipo() {
	}

	/**
	 * Adjunta archivos de la solicitud de aval
	 */
	public void adjuntarArchivo(FileUploadEvent event) {
		String tipo = null;
		if (esCadenaVacia(idTipoArchivo)) {
			mensajeError("Por favor, indicar el tipo del archivo.");
			return;
		} else {
			tipo = idTipoArchivo;
		}
		if (convocatoriaInternacional) {
			if ((idTipoArchivo.equals("3") || idTipoArchivo.equals("FE")) && !formatoExterno) {
				mensajeError("No se puede adjuntar el formato de aval cuando la convocatoria no lo pide.");
				return;
			}
		}
		archivoCargado = event.getFile();
		for (ArchivoAval archivo : listaArchivos) {
			if (!tipo.equals("0") && archivo.getTipo() != null && archivo.getTipo().equals(tipo)) {
				mensajeError("No se puede adjuntar el archivo, ya que existe un documento asociado a este tipo.");
				return;
			}
		}

		ArchivoAval aa = insertarArchivoAvalGenerico(0, archivoCargado, false, tipo);
		TipoArchivo tipoArchivoAval = new TipoArchivo();
		tipoArchivoAval.setId(Short.parseShort(tipo));

		try {
			List<TipoArchivo> objetoTipoArchivo = servicioGeneral
					.obtenerListaObjetos("TipoArchivo e where e.id in ('" + tipo + "'))");
			tipoArchivoAval = objetoTipoArchivo.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		aa.setTipoArchivo(tipoArchivoAval);
		if (aa != null) {
			listaArchivos.add(aa);
		}
	}

	/**
	 * Método para actualizar el total del aval, cuando se cambien valores.
	 */
	public void actualizarTotal() {
		if (aval.isEsProyectoInvestigacion()) {
			aval.setAviMontoFinal(aval.getValorPersonalTotal() + aval.getAviMontoEntConv()
					+ fichaSeleccionada.getValorEntidadesParticipantesCalculado() + aval.getAviFrescosunal()
					+ aval.getAviEspecieunal() + aval.getAviEspecieotros());

		} else if (aval.isEsJornadaDocente()) {
			aval.setAviMontoFinal(aval.getValorPersonalTotal());

		} else if (aval.isEsProyectoContrapartida()) {
			aval.setAviMontoFinal(aval.getAviFrescosunal());

		} else if (aval.isEsMovilidadEvento()) {
			aval.setAviMontoFinal(aval.getValorPersonalTotal() + aval.getAviMontoEntConv() + aval.getAviFrescosunal()
					+ aval.getAviEspecieunal() + getTotalOtrasEntidadesMov());

		} else if (aval.isEsRegalias() || aval.isEsPaedRegalias() || aval.isEsConvocatoriaRegalias()
				|| aval.isEsRequisitosRegalias()) {
			aval.setAviMontoFinal(aval.getValorPersonalTotal() + aval.getAviMontoEntConv()
					+ fichaSeleccionada.getValorEntidadesParticipantesCalculado() + aval.getAviFrescosunal()
					+ aval.getAviEspecieunal() + aval.getAviEspecieotros());
		} else {
			aval.setAviMontoFinal(0L);
		}
	}

	/**
	 * Consultar participantes del aval de jornada docente
	 */
	public void obtenerListaParticipantesJornadaDocente() {

		listaParticipantesJornadaDocente = new ArrayList<CoinvestigadorAval>();
		if (aval != null && aval.getCoinvestigador().size() > 0) {
			for (Iterator<CoinvestigadorAval> iterador = aval.getCoinvestigador().iterator(); iterador.hasNext();) {
				CoinvestigadorAval participante = (CoinvestigadorAval) iterador.next();
				InvestigadorInterno investigadorInterno = servicioPersona.obtenerInvestigadorInterno(
						new IdPersona(participante.getIdPersona(), participante.getIdDocumento()));
				if (investigadorInterno != null) {
					participante.setNombre(investigadorInterno.getNombreCompletoMinusculas());
				}
				listaParticipantesJornadaDocente.add(participante);
			}
		}
	}

	/**
	 * Agrega actividad al proyecto de jornada docente
	 */
	public void insertarActividad() {
		String idMensajeActividadJornada = "actividadesJornadaDocente";
		if (aval.getDuracion() == 0L) {
			mensajeErrorIdCampo(idMensajeActividadJornada, "No se ha podido determinar la duración del proyecto.");
			return;
		}

		if (esCadenaVacia(descripcionActividad)) {
			mensajeErrorIdCampo(idMensajeActividadJornada, "Debe ingresar la descripción de la actividad.");
			return;
		}

		if (duracionMesesActividad == null || duracionMesesActividad == 0L) {
			mensajeErrorIdCampo(idMensajeActividadJornada, "La duración de la actividad debe ser mayor a cero.");
			return;
		}

		Long tiempo = mesInicialActividad + duracionMesesActividad;
		if (tiempo > aval.getDuracion()) {
			mensajeErrorIdCampo(idMensajeActividadJornada, "La actividad supera el tiempo de duración del proyecto.");
			return;
		}

		AvalActividad a = new AvalActividad();
		a.setDescripcion(descripcionActividad);
		a.setMesInicial(mesInicialActividad);
		a.setDuracionMeses(duracionMesesActividad);
		listaActividades.add(a);
	}

	/**
	 * Elimina actividad del aval de jornada docente
	 */
	public void eliminarActividad() {
		listaActividades.remove(actividadSeleccionada);
		if (actividadSeleccionada.getId() != null) {
			servicioGeneral.eliminarObjeto(actividadSeleccionada);
		}
	}

	/**
	 * Consulta lista de actividades del aval de jornada docente
	 */
	public void obtenerListaActividadesAval() {
		if (aval != null && aval.getActividadAval().size() > 0) {
			for (Iterator<AvalActividad> iterador = aval.getActividadAval().iterator(); iterador.hasNext();) {
				AvalActividad actividad = (AvalActividad) iterador.next();
				listaActividades.add(actividad);
			}
		}
	}

	/**
	 * Agrega montos a la region seleccionada
	 */
	public void agregarMonto() {
		if (!validaMonto()) {
			return;
		}

		MontoAno montoAgregar = new MontoAno();
		montoAgregar.setMonto(Long.parseLong(this.montoReg));
		montoAgregar.setAno(this.anoMonto);

		for (int i = 0; i < listaRegiones.size(); i++) {
			SelectItem item = listaRegiones.get(i);
			if (region.equals(item.getValue())) {
				montoAgregar.setRegion(region);
				montoAgregar.setRegiontxt(item.getLabel());
				break;
			}
		}

		for (int i = 0; i < listaSubRegiones.size(); i++) {
			SelectItem item = listaSubRegiones.get(i);
			if (subregion.equals(item.getValue())) {
				montoAgregar.setSubregion(subregion);
				montoAgregar.setSubregiontxt(item.getLabel());
				break;
			}
		}
		aval.adicionarMonto(montoAgregar);
		montoReg = "";
	}

	private boolean validaMonto() {
		String idMensajeMontoRegalias = "montosRegalias";
		boolean bandera = true;
		if (esCadenaVacia(montoReg)) {
			mensajeErrorIdCampo(idMensajeMontoRegalias, "Debe indicar el valor para la entidad territorial");
			bandera = false;
		}
		try {
			Long valor = Long.parseLong(montoReg);
			if (valor <= 0) {
				mensajeErrorIdCampo(idMensajeMontoRegalias, "Debe indicar un valor mayor que cero");
				bandera = false;
			}
		} catch (NumberFormatException e) {
			mensajeErrorIdCampo(idMensajeMontoRegalias, "Debe indicar un valor válido mayor a cero para la región.");
			return false;
		}

		if (verificarExistenciaMonto(subregion, anoMonto)) {
			mensajeErrorIdCampo(idMensajeMontoRegalias, "La entidad territorial ya ha sido agregada, si necesita "
					+ "cambiar el monto, debe eliminarla y volverla a agregar");
			bandera = false;
		}

		Long total = getValorMontosDepartamentosPorAgregar();

		if (total.equals(0L)) {
			mensajeErrorIdCampo(idMensajeMontoRegalias,
					"No queda valor por ingresar, de acuerdo a lo indicado en el campo 'Monto a solicitar'.");
			bandera = false;
		}

		Long montoValor = Long.parseLong(this.montoReg);
		if (total < montoValor) {
			mensajeErrorIdCampo(idMensajeMontoRegalias,
					"El valor registrado supera el valor que queda por ingresar, de acuerdo a lo indicado en el campo 'Monto a solicitar'.");
			bandera = false;
		}
		return bandera;
	}

	private boolean verificarExistenciaMonto(String subregion, String anoMonto) {
		boolean montoYaesta = false;
		for (int i = 0; i < aval.getListaMontos().size(); i++) {
			MontoAno montoAgregado = (MontoAno) aval.getListaMontos().get(i);
			if (montoAgregado.getSubregion().equals(subregion) && montoAgregado.getAno().equals(anoMonto)) {
				montoYaesta = true;
				break;
			}
		}
		return montoYaesta;
	}

	/**
	 * Elimina monto de la región seleccionada
	 */
	public void eliminarMontoFecha() {
		aval.borrarMonto(monto);
		getValorMontosDepartamentosPorAgregar();
	}

	/**
	 * Calcular el valor por agregar a las regiones de acuredo al monto indicado a
	 * solicitar
	 */
	public long getValorMontosDepartamentosPorAgregar() {
		List<MontoAno> list = aval.getListaMontos();
		long total = 0L;
		if (!esListaVacia(list)) {
			Iterator<MontoAno> it = list.iterator();
			while (it.hasNext()) {
				MontoAno montoFaltante = (MontoAno) it.next();
				Long valor = 0L;
				try {
					valor = montoFaltante.getMonto();
				} catch (NumberFormatException nfe) {
					valor = 0L;
				}
				total += valor;
			}
		}
		return aval.getAviMontoEntConv() - total;
	}

	/**
	 * Inserta resultado esperado del aval de movilidad/evento
	 */
	public void insertarResultado() {
		if (this.resultado != null && !"".equals(this.resultado.trim())) {
			ResultadoAval re = new ResultadoAval();
			re.setDescripcion(this.resultado);
			listaResultados.add(re);
			this.resultado = "";
		} else {
			mensajeError("Por favor escriba el resultado.");
		}
	}

	/**
	 * Elimina resultado esperado del aval de movilidad/evento
	 */
	public void eliminarResultado() {
		listaResultados.remove(resultadoTabla);
		aval.borrarResultado(resultadoTabla);
		resultadoTabla = new ResultadoAval();
	}

	/**
	 * Consulta participantes de la movilidad/evento
	 */
	public void cargarParticipantesMovEventos() {
		participanteMovEventosItem = new ArrayList<SelectItem>();
		String consulta = "select dd from Dominio d, DominioDetalle dd where "
				+ "d.id = dd.identificador.id and d.id = 103 order by dd.identificador.tipo";
		List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		for (int i = 0; i < lista.size(); i++) {
			DominioDetalle dominio = (DominioDetalle) lista.get(i);
			participanteMovEventosItem
					.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
		}
	}

	/**
	 * Consulta participantes de la pasantia
	 */
	public void cargarParticipantesMovPasantia() {
		participanteMovPasantiaItem = new ArrayList<SelectItem>();
		String consulta = "select dd " + "from Dominio d, DominioDetalle dd " + "where d.id = dd.identificador.id "
				+ "and d.id = 103 " + "and dd.identificador.tipo in ('TP_PAR_MV_DO','TP_PAR_MV_ES')"
				+ "order by dd.identificador.tipo";
		List<DominioDetalle> lista = servicioGeneral.obtenerObjetos(DominioDetalle.class, consulta);

		for (int i = 0; i < lista.size(); i++) {
			DominioDetalle dominio = (DominioDetalle) lista.get(i);
			participanteMovPasantiaItem
					.add(new SelectItem(dominio.getIdentificador().getTipo(), dominio.getDescripcion()));
		}
	}

	public void mensajeErrorIdCampo(String idComponente, String mensaje) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(idComponente, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
	}

	/**
	 * Agrega participante al aval de movilidad/evento
	 */
	public void agregarParticipanteMovilidad() {

		if (!validarParticipanteMovilidad()) {
			return;
		}

		CoinvestigadorAval participante = new CoinvestigadorAval();
		participante.setIdPersona(documentoParticipante);
		participante.setIdDocumento(tipoDocParticipante);

		if ("TP_PAR_MV_DO".equals(this.aval.getParticipanteMovilidad())) {
			InvestigadorInterno investigadorInterno = servicioPersona
					.obtenerInvestigadorInterno(new IdPersona(documentoParticipante, tipoDocParticipante));
			Long valorHora = 0L;
			if (investigadorInterno.getValorHora() != null) {
				valorHora = investigadorInterno.getValorHora();
			}
			participante.setNombre(investigadorInterno.getNombreCompletoMinusculas());
			participante.setDependenciaDoc(investigadorInterno.getDependencia().getNombre());
			participante.setTipoVinculacion("Docente");
			participante.setHoras(horasParticipante);
			participante.setValorPagar(valorHora * horasParticipante);
			Long valorContrapartidaPersonal = this.aval.getValorPersonalTotal() + participante.getValorPagar();
			this.aval.setValorPersonalTotal(valorContrapartidaPersonal);
			actualizarTotal();

		} else if ("TP_PAR_MV_ES".equals(this.aval.getParticipanteMovilidad())) {
			horasParticipante = 0L;
			Estudiante estudiante = servicioPersona
					.obtenerEstudiante(new IdPersona(documentoParticipante, tipoDocParticipante));
			participante.setNombre(estudiante.getNombreCompleto());
			participante.setTipoVinculacion("Estudiante");
			if (estudiante.getNombreCarrera() != null) {
				participante.setFacultadDoc(estudiante.getNombreCarrera());
			} else {
				participante.setFacultadDoc("No encontrado");
			}

		} else if ("TP_PAR_MV_PV".equals(this.aval.getParticipanteMovilidad())) {
			participante.setNombreCandidato(nombreProfesorVisitante);
			participante.setTipoVinculacion("Profesor visitante");
			participante.setDependenciaDoc(entidadProfesorVisitante);
		}
		participante.setAval(this.aval);
		aval.setCoinvestigador(participante);
		listaParticipantesAval.add(participante);
	}

	private boolean validarParticipanteMovilidad() {
		String idCampoMensajeParticipanteMovilidad = "MsgParticipanteMovilidad";
		boolean bandera = true;
		if (esCadenaVacia(aval.getParticipanteMovilidad())) {
			mensajeErrorIdCampo(idCampoMensajeParticipanteMovilidad,
					"Debe indicar el tipo de participante de la movilidad.");
			bandera = false;
		}
		if (esCadenaVacia(tipoDocParticipante) || esCadenaVacia(documentoParticipante)) {
			mensajeErrorIdCampo(idCampoMensajeParticipanteMovilidad,
					"Debe indicar el tipo de documento y el documento del participante de la movilidad.");
			bandera = false;
		}

		if ("TP_PAR_MV_DO".equals(this.aval.getParticipanteMovilidad())) {
			if (horasParticipante == null || horasParticipante.equals(0L)) {
				mensajeErrorIdCampo(idCampoMensajeParticipanteMovilidad,
						"Debe indicar la cantidad de horas del docente.");
				bandera = false;
			} else {
				InvestigadorInterno investigadorInterno = servicioPersona
						.obtenerInvestigadorInterno(new IdPersona(documentoParticipante, tipoDocParticipante));
				if (investigadorInterno == null || (investigadorInterno != null
						&& investigadorInterno.getInterno() != null && !"S".equals(investigadorInterno.getInterno()))) {
					mensajeErrorIdCampo(idCampoMensajeParticipanteMovilidad,
							"El docente no fue encontrado o no se encuentra activo en la Universidad, verifique "
									+ "el tipo y documento de identidad.");
					bandera = false;
				}
			}
		}

		if ("TP_PAR_MV_ES".equals(this.aval.getParticipanteMovilidad())) {
			Estudiante estudiante = servicioPersona
					.obtenerEstudiante(new IdPersona(documentoParticipante, tipoDocParticipante));
			if (estudiante == null || (estudiante != null && estudiante.getInterno() != null
					&& !"S".equals(estudiante.getInterno()))) {
				mensajeErrorIdCampo(idCampoMensajeParticipanteMovilidad, "La persona indicada no se ha encontrado como "
						+ "estudiante activo de la Universidad Nacional de Colombia, verifique el documento.");
				bandera = false;
			}
		}

		if ("TP_PAR_MV_PV".equals(this.aval.getParticipanteMovilidad())) {
			if (esCadenaVacia(nombreProfesorVisitante)) {
				mensajeErrorIdCampo(idCampoMensajeParticipanteMovilidad,
						"Debe indicar el nombre del profesor visitante.");
				bandera = false;
			}
			if (esCadenaVacia(entidadProfesorVisitante)) {
				mensajeErrorIdCampo(idCampoMensajeParticipanteMovilidad,
						"Debe indicar el nombre de la institución del profesor visitante.");
				bandera = false;
			}
		}

		if (verificarExistenciaParticipante(documentoParticipante, tipoDocParticipante)) {
			mensajeErrorIdCampo(idCampoMensajeParticipanteMovilidad, "El participante ya ha sido agregado, si necesita "
					+ "cambiar la información asociada, debe eliminarlo y agregarlo nuevamente.");
			bandera = false;
		}
		return bandera;
	}

	private boolean verificarExistenciaParticipante(String documentoParticipante, String tipoDocParticipante) {
		boolean personaYaesta = false;

		for (int i = 0; i < listaParticipantesAval.size(); i++) {
			CoinvestigadorAval personaAgregada = (CoinvestigadorAval) listaParticipantesAval.get(i);
			if (personaAgregada.getIdPersona().equals(documentoParticipante)
					&& personaAgregada.getIdDocumento().equals(tipoDocParticipante)) {
				personaYaesta = true;
				break;
			}
		}
		return personaYaesta;
	}

	/**
	 * Elimina participante de la movilidad/evento
	 */
	public void eliminarParticipante() {

		if ("Docente".equals(participanteEliminar.getTipoVinculacion())) {
			Long valorContrapartidaPersonal = this.aval.getValorPersonalTotal() - participanteEliminar.getValorPagar();

			if (valorContrapartidaPersonal > 0) {
				this.aval.setValorPersonalTotal(valorContrapartidaPersonal);
			} else {
				aval.setValorPersonalTotal(0L);
			}
			actualizarTotal();
		}
		listaParticipantesAval.remove(participanteEliminar);
		aval.eliminarCoinvestigador(participanteEliminar);
	}

	/**
	 * Obtiene lista de particpantes del aval de Jornada docente
	 */
	public List<CoinvestigadorAval> obtenerListaParticipantesAval() {
		listaParticipantesAval = new ArrayList<CoinvestigadorAval>();
		if (aval != null && aval.getEntidadAval().size() > 0) {
			for (Iterator<CoinvestigadorAval> iterador = aval.getCoinvestigador().iterator(); iterador.hasNext();) {
				CoinvestigadorAval participante = (CoinvestigadorAval) iterador.next();
				listaParticipantesAval.add(participante);
			}
		}
		return listaParticipantesAval;
	}

	/**
	 * Agregar entidad financiadora de movilidad/evento con su respectivo rubro
	 */
	public void agregarEntidadFinanciacionExterna() {

		if (!validarEntidadExternaFinanciacionMovilidad()) {
			return;
		}

		EntidadArticulo entidadSeleccionada = new EntidadArticulo();
		List<FuenteFinanciacion> entidad = servicioGeneral.obtenerObjetoXID(FuenteFinanciacion.class,
				entidadFinanciacionExterna);
		entidadSeleccionada.setEntidad(entidad.get(0));
		List<TipoRubro> rubros = servicioGeneral.obtenerObjetoXID(TipoRubro.class, rubro);
		entidadSeleccionada.setTipoRubro(rubros.get(0));
		entidadSeleccionada.setFuente("FinanciacionExterna");
		entidadSeleccionada.setFrescos(valorRubroFrescos);
		entidadSeleccionada.setEspecie(valorRubroEspecie);

		if (FUENTE_FINANCIACION_UNAL.equals(entidadFinanciacionExterna)) {
			Long totalUnParcial = aval.getAviMontosolicitado() + valorRubroEspecie + valorRubroFrescos;
			aval.setAviMontosolicitado(totalUnParcial);
			Long parcialEspecie = aval.getAviEspecieunal() + valorRubroEspecie;
			aval.setAviEspecieunal(parcialEspecie);
			Long parcialFrescos = aval.getAviFrescosunal() + valorRubroFrescos;
			aval.setAviFrescosunal(parcialFrescos);
		}

		entidadSeleccionada.setAval(aval);
		aval.adicionarEntidad(entidadSeleccionada);
		listaFinanciacionExternaME.add(entidadSeleccionada);
		actualizarTotal();

	}

	private boolean validarEntidadExternaFinanciacionMovilidad() {
		String idCampoMensajeFinanciacionExtena = "financiacionExterna";
		boolean bandera = true;
		if (esCadenaVacia(entidadFinanciacionExterna)) {
			mensajeErrorIdCampo(idCampoMensajeFinanciacionExtena,
					"Debe seleccionar la entidad que aporta los recursos.");
			bandera = false;
		}

		if (esCadenaVacia(rubro)) {
			mensajeErrorIdCampo(idCampoMensajeFinanciacionExtena, "Debe indicar el rubro al que asignará recursos");
			bandera = false;
		}

		for (int i = 0; i < listaFinanciacionExternaME.size(); i++) {
			EntidadArticulo entidadAgregada = (EntidadArticulo) listaFinanciacionExternaME.get(i);
			if (entidadAgregada.getEntidad().getId().equals(entidadFinanciacionExterna)
					&& entidadAgregada.getTipoRubro().getId().equals(Long.parseLong(rubro))) {

				mensajeErrorIdCampo(idCampoMensajeFinanciacionExtena,
						"La entidad asociada al tipo de rubro seleccionado "
								+ "ya ha sido agregada, si necesita cambiar los "
								+ "valores, debe eliminarla y volverla a agregar.");
				bandera = false;
			}
		}

		if (valorRubroFrescos.equals(0L) && valorRubroEspecie.equals(0L)) {
			mensajeErrorIdCampo(idCampoMensajeFinanciacionExtena,
					"Debe indicar los valores que aporta la entidad, en efectivo y/o especie.");
			bandera = false;
		} else {
			FuenteFinanciacion entidadExt = new FuenteFinanciacion();
			entidadExt.setId(entidadFinanciacionExterna);
			if (entidadFinanciacionExterna.equals(aval.getAviEntidad()) && (valorRubroFrescos + valorRubroEspecie
					+ verificarTotalEntidad(entidadExt) > aval.getAviMontoEntConv())) {
				mensajeErrorIdCampo(idCampoMensajeFinanciacionExtena,
						"El valor que está ingresando para la entidad convocate, está superando el "
								+ "valor solicitado, modifique dicho valor "
								+ "o verifique la asignación a los rubros.");
				bandera = false;
			}
		}
		return bandera;
	}

	/**
	 * Verifica el total agregado a una entidad
	 */
	public Long verificarTotalEntidad(FuenteFinanciacion entidad) {

		Long total = 0L;
		if (aval.getTipo().equals(Aval.TIPO_MOVILIDAD)) {
			for (Iterator<EntidadArticulo> iterador = listaFinanciacionExternaME.iterator(); iterador.hasNext();) {
				EntidadArticulo entidadFinanciacionExt = (EntidadArticulo) iterador.next();
				if (entidad.getId().equals(entidadFinanciacionExt.getEntidad().getId())) {
					total += entidadFinanciacionExt.getFrescos() + entidadFinanciacionExt.getEspecie();
				}
			}
		}
		if (aval.getTipo().equals(Aval.TIPO_INVESTIGACION_CONT)) {
			for (Iterator<EntidadArticulo> iterador = listaFinanciacionExternaME.iterator(); iterador.hasNext();) {
				EntidadArticulo entidadFinanciacionExt = (EntidadArticulo) iterador.next();
				total += entidadFinanciacionExt.getFrescos();
			}
		}
		return total;
	}

	/**
	 * Eliminar una entidad del aval de contrapartida o de movilidad
	 */
	public void eliminarEntidadFinanciacion() {

		if (aval.getTipo().equals(Aval.TIPO_INVESTIGACION_CONT)) {
			aval.borrarEntidad(entidadEliminar);
			obtenerListaFinanciacion();
		} else {
			if (FUENTE_FINANCIACION_UNAL.equals(entidadEliminar.getEntidad().getId())
					|| (entidadEliminar.getNombre() != null
							&& "UNIVERSIDAD NACIONAL DE COLOMBIA".equals(entidadEliminar.getNombre()))) {
				Long totalUnParcial = aval.getAviMontosolicitado() - entidadEliminar.getEspecie()
						- entidadEliminar.getFrescos();

				if (totalUnParcial < 0L) {
					aval.setAviMontosolicitado(0L);
				} else {
					aval.setAviMontosolicitado(totalUnParcial);
				}
				Long parcialEspecie = aval.getAviEspecieunal() - entidadEliminar.getEspecie();
				if (parcialEspecie < 0L) {
					aval.setAviEspecieunal(0L);
				} else {
					aval.setAviEspecieunal(parcialEspecie);
				}
				Long parcialFrescos = aval.getAviFrescosunal() - entidadEliminar.getFrescos();
				if (parcialFrescos < 0L) {
					aval.setAviFrescosunal(0L);
				} else {
					aval.setAviFrescosunal(parcialFrescos);
				}
			}
			listaFinanciacionExternaME.remove(entidadEliminar);
			aval.borrarEntidad(entidadEliminar);
			actualizarTotal();
		}
	}

	/**
	 * Consultar lista de entidades de financiacion externa para aval de movilidad
	 */
	public List<EntidadArticulo> obtenerListaFinanciacion() {
		listaFinanciacionExternaME = new ArrayList<EntidadArticulo>();
		if (aval != null && aval.getEntidadAval().size() > 0) {
			for (Iterator<EntidadArticulo> iterador = aval.getEntidadAval().iterator(); iterador.hasNext();) {
				EntidadArticulo entidadFinanciacionExt = (EntidadArticulo) iterador.next();
				if (entidadFinanciacionExt.getFuente() != null
						&& "FinanciacionExterna".equals(entidadFinanciacionExt.getFuente())) {
					listaFinanciacionExternaME.add(entidadFinanciacionExt);
				} else if (aval.getTipo().equals(Aval.TIPO_INVESTIGACION_CONT)
						&& entidadFinanciacionExt.getFuente() != null
						&& "FinanciacionInterna".equals(entidadFinanciacionExt.getFuente())) {
					listaFinanciacionExternaME.add(entidadFinanciacionExt);
				}
			}
		}
		return listaFinanciacionExternaME;
	}

	/**
	 * Agregar entidad de fortalecimiento
	 */
	public void agregarEntidadFortalecimiento() {

		if (entidadFortalecimiento == null || "".equals(entidadFortalecimiento)) {
			mensajeError("Debe seleccionar una entidad participante del fortalecimiento.");
			return;
		}

		for (int i = 0; i < listaEntidadesParticipantesFortalecimiento.size(); i++) {
			EntidadArticulo entidadAgregada = (EntidadArticulo) listaEntidadesParticipantesFortalecimiento.get(i);
			if (entidadAgregada.getEntidad().getId().equals(entidadFortalecimiento)) {
				mensajeError("El entidad ya ha sido agregada.");
				return;
			}
		}

		EntidadArticulo entidadSeleccionada = new EntidadArticulo();
		TipoRubro general = new TipoRubro();
		List<FuenteFinanciacion> entidadForta = servicioGeneral.obtenerObjetoXID(FuenteFinanciacion.class,
				entidadFortalecimiento);
		entidadSeleccionada.setEntidad(entidadForta.get(0));
		general.setId(1L);
		entidadSeleccionada.setTipoRubro(general);
		entidadSeleccionada.setFuente("Fortalecimiento");
		entidadSeleccionada.setAval(aval);
		aval.adicionarEntidad(entidadSeleccionada);
		listaEntidadesParticipantesFortalecimiento.add(entidadSeleccionada);
	}

	/**
	 * Eliminar entidad participante del fortalecimiento
	 */
	public void eliminarEntidadFortalecimiento() {
		listaEntidadesParticipantesFortalecimiento.remove(entidadEliminar);
		aval.borrarEntidad(entidadEliminar);
	}

	/**
	 * Consultar entidades participantes de fortalecimiento
	 */
	public void obtenerListaEntidadesFortalecimiento() {
		listaEntidadesParticipantesFortalecimiento = new ArrayList<EntidadArticulo>();
		if (aval != null && aval.getEntidadAval().size() > 0) {
			for (Iterator<EntidadArticulo> iterador = aval.getEntidadAval().iterator(); iterador.hasNext();) {
				EntidadArticulo entidadFortalecimientoObtenida = (EntidadArticulo) iterador.next();

				if (entidadFortalecimientoObtenida.getFuente() != null
						&& "Fortalecimiento".equals(entidadFortalecimientoObtenida.getFuente())) {
					listaEntidadesParticipantesFortalecimiento.add(entidadFortalecimientoObtenida);
				}
			}
		}
	}

	/**
	 * Agregar entidad de coorganizadora de evento
	 */
	public void agregarEntidadCoorganizadora() {

		if (esCadenaVacia(entidadCooperante)) {
			mensajeError("Debe seleccionar una entidad co-organizadora.");
			return;
		}

		for (int i = 0; i < listaEntidadesCoorganizadorasAval.size(); i++) {
			EntidadArticulo entidadAgregada = (EntidadArticulo) listaEntidadesCoorganizadorasAval.get(i);
			if (entidadAgregada.getEntidad().getId().equals(entidadCooperante)) {
				mensajeError("La entidad ya ha sido agregada.");
				return;
			}
		}

		EntidadArticulo entidadSeleccionada = new EntidadArticulo();
		TipoRubro general = new TipoRubro();
		List<FuenteFinanciacion> entidadForta = servicioGeneral.obtenerObjetoXID(FuenteFinanciacion.class,
				entidadCooperante);
		entidadSeleccionada.setEntidad(entidadForta.get(0));
		general.setId(1L);
		entidadSeleccionada.setTipoRubro(general);
		entidadSeleccionada.setFuente("Coorganizadora");
		entidadSeleccionada.setAval(aval);
		aval.adicionarEntidad(entidadSeleccionada);
		listaEntidadesCoorganizadorasAval.add(entidadSeleccionada);
	}

	/**
	 * Eliminar entidad coorganizadora de evento
	 */
	public void eliminarEntidadCoorganizadora() {
		listaEntidadesCoorganizadorasAval.remove(entidadEliminar);
		aval.borrarEntidad(entidadEliminar);
	}

	/**
	 * Consultar entidades participantes de fortalecimiento
	 */
	public void obtenerListaEntidadesCoorganizadoras() {
		listaEntidadesCoorganizadorasAval = new ArrayList<EntidadArticulo>();
		if (aval != null && aval.getEntidadAval().size() > 0) {
			for (Iterator<EntidadArticulo> iterador = aval.getEntidadAval().iterator(); iterador.hasNext();) {
				EntidadArticulo entidadFortalecimientoObtenida = (EntidadArticulo) iterador.next();
				if (entidadFortalecimientoObtenida.getFuente() != null
						&& "Coorganizadora".equals(entidadFortalecimientoObtenida.getFuente())) {
					listaEntidadesCoorganizadorasAval.add(entidadFortalecimientoObtenida);
				}
			}
		}
	}

	/**
	 * Agregar detalle de financiacion solicitada en contrapartida a la UN
	 */
	public void agregarFinanciacionUnal() {
		if (!validarFinanciacionUnalContrapartida()) {
			return;
		}
		FuenteFinanciacion entidadParticipanteAgregar = new FuenteFinanciacion();
		entidadParticipanteAgregar.setId(FUENTE_FINANCIACION_UNAL);
		entidadParticipanteAgregar.setDescripcion("UNIVERSIDAD NACIONAL DE COLOMBIA");
		EntidadArticulo entidadSeleccionada = new EntidadArticulo();
		List<TipoRubro> rubros = servicioGeneral.obtenerObjetoXID(TipoRubro.class, rubro);
		entidadSeleccionada.setTipoRubro(rubros.get(0));
		entidadSeleccionada.setFuente("FinanciacionInterna");
		entidadSeleccionada.setFrescos(valorRubroFrescos);
		entidadSeleccionada.setEntidad(entidadParticipanteAgregar);
		entidadSeleccionada.setAval(aval);
		entidadSeleccionada.setDescripcionRubro(descripcionRubro);
		aval.adicionarEntidad(entidadSeleccionada);
		listaFinanciacionExternaME.add(entidadSeleccionada);
	}

	private boolean validarFinanciacionUnalContrapartida() {
		String idMensajeFinanciacionUN = "rubrosContrapartidaUN";
		FuenteFinanciacion entidadParticipanteAgregar = new FuenteFinanciacion();
		entidadParticipanteAgregar.setId(FUENTE_FINANCIACION_UNAL);

		if (esCadenaVacia(rubro)) {
			mensajeErrorIdCampo(idMensajeFinanciacionUN, "Debe indicar el rubro al que asignará recursos");
			return false;
		}

		for (int i = 0; i < listaFinanciacionExternaME.size(); i++) {
			EntidadArticulo entidadAgregada = (EntidadArticulo) listaFinanciacionExternaME.get(i);
			if (entidadAgregada.getTipoRubro().getId().equals(Long.parseLong(rubro))) {
				mensajeErrorIdCampo(idMensajeFinanciacionUN, "El tipo de rubro seleccionado ya ha sido agregado,"
						+ " si necesita cambiar los valores, debe eliminarlo y volverlo a agregar.");
				return false;
			}
		}

		if (valorRubroFrescos.equals(0L)) {
			mensajeErrorIdCampo(idMensajeFinanciacionUN, "Debe indicar el valor del rubro en efectivo.");
			return false;
		}

		if (valorRubroFrescos + verificarTotalEntidad(entidadParticipanteAgregar) > aval.getAviFrescosunal()) {
			mensajeErrorIdCampo(idMensajeFinanciacionUN,
					"El valor que está ingresando, supera el valor solicitado a la Universidad, modifique "
							+ "dicho valor o verifique la asignación a los rubros.");
			return false;
		}
		return true;
	}

	/**
	 * Descargar documento del convenio
	 */
	public void descargarConvenio() {
		if (convenioIngresado != null && convenioIngresado.getArchivo() != null) {
			descargarArchivoDocumentoConvenioGenerico(convenioIngresado);
		} else {
			descargarArchivoDocumentoConvenioGenerico(aval.getConvenio());
		}
	}

	/**
	 * Descargar archivo de consulta sobre la convocatoria externa
	 */
	public void descargarArchivoExterno() {
		if (archivoExterno != null) {
			descargarArchivoConvocatoriaExterna(archivoExterno);
		}
	}

	/**
	 * Descargar el documento del aval
	 */
	public void descargarAval() {
		descargarArchivoDocumentoAvalGenerico(aval);
	}

	public List obtenerPalabraClavesSugeridas(String nombre) {
		return servicioGeneral.obtenerPalabrasClaveEmpezandoCon(nombre);
	}

	public void insertarPalabraClave() {

		// System.out.println("inserta " + palabraClave.getPalabra());
		if ((!palabraClave.getPalabra().equals(""))) {
			PalabraClave pc = new PalabraClave();
			pc.setPalabra(palabraClave.getPalabra().toUpperCase());
			pc.setPalabraOriginal(palabraClave.getPalabra());
			try {
				PalabraClave pc1 = servicioGeneral.obtenerPalabraClave(pc.getPalabra());
				if (pc1 == null) {
					pc.setIdioma("ES");
					servicioGeneral.guardarObjeto(pc);
				} else {
					pc = (PalabraClave) pc1.clone();
				}
				aval.adicionarPalabraClave(pc);
				palabraClave.setPalabra("");
				pc1 = null;
				pc = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		palabraClave = new PalabraClave();
	}

	public void eliminarPalabraClave() {
		aval.borrarPalabraClave(this.palabraClave);
	}

	/**
	 * Guardar la edicion de la solicitud de aval
	 */

	private boolean guardar(String estado) {
		boolean guardo = true;

		this.aval.setDocumento(investigadorActual.getId().getDocumento());
		this.aval.setTipoDocumento(investigadorActual.getId().getTipoDocumento());
		InvestigadorInterno ii = servicioPersona.obtenerInvestigadorInterno(investigadorActual.getId());
		this.aval.setDependencia(ii.getDependencia());

		if (remitirSedePresencia) {
			this.aval.setDependencia(dependenciaPresenciaNacional);
		}

		if ((aval.isEsRegalias() || aval.isEsPaedRegalias() || aval.isEsConvocatoriaRegalias()
				|| aval.isEsRequisitosRegalias()) && !estado.equals(Aval.FORMULACION)
				&& !estado.equals(Aval.DEVUELTO)) {
			if (aval.getDependencia().getSede().isEsSedePresenciaNacional()) {
				aval.setAviEstado(Aval.ENVIADO_SEDE);
				aval.setAviAvalfacultad(Aval.APROBADO);
			} else {
				aval.setAviEstado(Aval.ENVIADO_FACULTAD);
			}
		} else {
			aval.setAviEstado(estado);
		}

		verificarCamposVacios();

		if (aval.getAviEstado().equals(Aval.ENVIADO) || aval.getAviEstado().equals(Aval.ENVIADO_SEDE)
				|| aval.getAviEstado().equals(Aval.ENVIADO_FACULTAD)
				|| aval.getAviEstado().equals(Aval.ETICO_ENVIADO_CEPI)) {
			aval.setFechaUltimoEnvio(new Date());
			esConsulta = true;
		}

		if (aval.getDependencia() == null || "0".equals(aval.getDependencia().getId())) {
			mensajeError("No se ha podido determinar la dependencia a la que pertenece");
		}

		if (aval.isEsJornadaDocente()) {
			aval.setAviEntidad(FUENTE_FINANCIACION_UNAL);
			if (!Sede.SEDES_ANDINAS.contains(investigadorActual.getDependencia().getSede().getId().toString())
					&& !investigadorActual.getDependencia().getSede().getId().equals(1L)) {
				this.aval.setAviEstado(Aval.REVISADO_UAB);
				this.aval.setAviAvalUab(Aval.APROBADO);
			}
		}

		if (aval.isEsGrupoInvestigacion()) {
			Convenio conv = new Convenio();
			conv.setId(0L);
			aval.setConvenio(conv);
			aval.setAviTitulo(obtenerNombreGrupoAval());
		}

		if (aval.isEsInvestigadorIndependiente()) {
			Convenio conv = new Convenio();
			conv.setId(0L);
			aval.setConvenio(conv);
			aval.setAviTitulo(investigadorActual.getNombreCompletoMinusculas());
		}

		if (aval.isEsCentroInvestigacion() || aval.isEsEtico()) {
			Convenio conv = new Convenio();
			conv.setId(0L);
			aval.setConvenio(conv);
		}

		if (aval.isEsArticuloInvestigacion()) {
			Convenio conv = new Convenio();
			conv.setId(0L);
			aval.setConvenio(conv);
			aval.setAviTitulo(aval.getAvalArticuloTitulo());
		}

		if (aval.isEsRegalias() || aval.isEsPaedRegalias() || aval.isEsConvocatoriaRegalias()
				|| aval.isEsRequisitosRegalias()) {
			Convenio conv = new Convenio();
			conv.setId(0L);
			aval.setConvenio(conv);
			aval.setInterSedes("N");
			if (interSedes) {
				aval.setInterSedes("S");
			}
		}

		List<ConvocatoriaExterna> convocaExt = servicioGeneral.obtenerObjetoXID(ConvocatoriaExterna.class,
				aval.getAviConvocatoria());
		if (!esListaVacia(convocaExt) && convocaExt.get(0).getNaturaleza().getId().equals(376L)) {
			aval.setEsAvalParaRevisionDRE("S");
		} else {
			aval.setEsAvalParaRevisionDRE("N");
		}

		try {
			// Guardar Aval
			this.servicioGeneral.guardarObjeto(this.aval);
			guardarArchivos();
			guardarActividades();
			confirmarGuardadoSubtiposGrupo();
			crearHistoricoEstadoAval(aval, cargarPersonaActual(), "D");
			if (huboCambiosGrupos) {
				servicioGeneral.guardarObjeto(fichaSeleccionada);
			}

		} catch (Exception e) {
			e.printStackTrace();
			guardo = false;
		}
		return guardo;
	}

	private void confirmarGuardadoSubtiposGrupo() {
		if (aval.isEsGrupoInvestigacion()) {
			for (AvalSubTipos tipoEsp : aval.getListaSubTiposEspecificacion()) {
				servicioGeneral.guardarObjeto(tipoEsp);
			}
		}
	}

	private void verificarCamposVacios() {
		if (aval.getFecha() == null) {
			aval.setFecha(new Date());
		}
		if (esCadenaVacia(aval.getMecanismo())) {
			aval.setMecanismo("No aplica");
		}
		if (esCadenaVacia(aval.getModalidad())) {
			aval.setModalidad("No aplica");
		}
	}

	private String obtenerNombreGrupoAval() {
		List<Grupo> grupoAval = servicioGeneral.obtenerObjetosLimitado(Grupo.class,
				"select #id g.id, #nombre g.nombre from Grupo g where g.id = '" + aval.getNombreGrupoPINV() + "'");
		if (!esListaVacia(grupoAval)) {
			Grupo grupo = (Grupo) grupoAval.get(0);
			return grupo.getNombre();
		}
		return "";
	}

	private void guardarArchivos() {
		if (!esListaVacia(listaArchivos)) {
			// Guardar Archivos
			Iterator<ArchivoAval> itSet = listaArchivos.iterator();
			while (itSet.hasNext()) {
				ArchivoAval archivo = itSet.next();
				archivo.setAval(aval.getAviId());
				this.servicioGeneral.guardarObjeto(archivo);
			}
		}
	}

	private void guardarActividades() {
		// Guardar actividades del aval de jornada docente
		if (this.aval.getTipo().equals(Aval.TIPO_JORNADA_DOCENTE) && !esListaVacia(listaActividades)) {

			Iterator<AvalActividad> itSet2 = listaActividades.iterator();
			while (itSet2.hasNext()) {
				AvalActividad actividad = itSet2.next();
				aval.adicionarActividad(actividad);
				actividad.setAval(aval);
				this.servicioGeneral.guardarObjeto(actividad);
			}
		}
	}

	/**
	 * Guardar parcialmente la solicitud de aval
	 */
	public void guardarParcialmente() {
		String estado;
		if (esCadenaVacia(aval.getAviEstado())) {
			estado = Aval.FORMULACION;
		} else {
			estado = aval.getAviEstado();
		}

		if (guardar(estado)) {
			mensajeInfo("Guardado parcialmente. Código de aval asignado = " + aval.getAviId());
			if (aval.getTipo().equals("CIN")) {
				for (int i = 0; i < listaLaboratoriosAval.size(); i++) {
					LaboratorioAval lab = listaLaboratoriosAval.get(i);
					servicioGeneral.guardarObjeto(lab);
				}

				if (listaLaboratoriosAvalBorrados != null) {
					if (listaLaboratoriosAvalBorrados.size() > 0) {
						for (int i = 0; i < listaLaboratoriosAvalBorrados.size(); i++) {
							LaboratorioAval lab = listaLaboratoriosAvalBorrados.get(i);
							servicioGeneral.eliminarObjeto(lab);
						}
					}
				}
				listaLaboratoriosAvalBorrados = new ArrayList<LaboratorioAval>();
			}
		} else {
			if (aval.getAviId() != null) {
				mensajeError("Ocurrió un problema al guardar la solicitud. Por favor capture su información, "
						+ "salga del Sistema e ingrese de nuevo, verifique la "
						+ "solicitud de aval con código asignado = " + aval.getAviId()
						+ " en caso de problemas con la misma comuníquese con la línea de soporte 11111");
			} else {
				mensajeError("La solicitud no ha sido guardada. Por favor verifique su información.");
			}
		}
		eliminarManejadorAvalMenu();
	}

	public String regresar() {
		return "successProyectosAval";
	}

	public Boolean validarRecursoAvalEtico() {

		if (esNulo(aval.getAvalTieneRecurso()) || aval.getAvalTieneRecurso().equals("")
				|| aval.getAvalTieneRecurso().equals("NO")) {
			mensajeError("Debe confirmar que desea interponer un recurso");
			return false;
		}

		if (esNulo(aval.getTipoRecursoAvalEtico())) {
			mensajeError("Debe seleccionar el tipo de recurso a interponer");
			return false;
		}

		return true;
	}

	public void guardarRecursoAvalEtico() {
		if (validarRecursoAvalEtico()) {
			aval.setFechaRecurso(new Date());
			this.servicioGeneral.guardarObjeto(this.aval);

			// Notificacion a comite de etica
			Correo correoCEPI = new Correo();
			CorreoPlantilla cpRecurso = cargarPlantilla(371);

			AvalComiteEtica cepi = servicioGeneral
					.obtenerObjetoXID(AvalComiteEtica.class, aval.getCepi().getId().toString()).get(0);
			if (!esNulo(cepi))
				aval.setCepi(cepi);

			String sql = "JOIN i.roles r " + "WHERE r.id = 'CEPI' " + "AND i.dependencia2.id = '"
					+ cepi.getDependencia().getId().toString() + "'";

			for (InvestigadorInterno inv : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
				if (tieneRolVigente(inv, "CEPI") && !esNulo(inv.getEmail())) {
					correoCEPI.adicionarDireccion(inv.getEmail());
				}
			}

			construirCorreo(correoCEPI, cpRecurso);
			servicioCorreo.enviarCorreo(correoCEPI);

			esConsultaRecurso = true;

			// Mensaje exito operacion
			mensajeInfo("Recurso guardado y enviado al comité de ética asociado para la revisión de su solicitud.");
		}
	}

	public void guardarQuejaEtico() {
		aval.setFechaQueja(new Date());
		this.servicioGeneral.guardarObjeto(this.aval);

		// Notificacion a comite de etica
		Correo correoCESI = new Correo();
		CorreoPlantilla cpQueja = cargarPlantilla(382);

		AvalComiteEtica cepi = servicioGeneral
				.obtenerObjetoXID(AvalComiteEtica.class, aval.getCepi().getId().toString()).get(0);
		if (!esNulo(cepi))
			aval.setCepi(cepi);

		String sql = "JOIN i.roles r " + "WHERE r.id = 'CESI' " + "AND i.dependencia2.id = '"
				+ cepi.getCesi().getDependencia().getId().toString() + "'";

		for (InvestigadorInterno inv : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
			if (tieneRolVigente(inv, "CESI") && !esNulo(inv.getEmail())) {
				correoCESI.adicionarDireccion(inv.getEmail());
			}
		}

		construirCorreo(correoCESI, cpQueja);
		servicioCorreo.enviarCorreo(correoCESI);

		esConsultaQueja = true;

		// Mensaje exito operacion
		mensajeInfo("Queja guardada y enviada al comité de ética de segunda instancia.");
	}

	/**
	 * Guarda la solicitud de aval y la envia para revision
	 */
	public void enviarSolicitud() {
		if (aval.getFecha() == null) {
			aval.setFecha(new Date());
		}

		if (!validaInformacionSolicitud()) {
			return;
		}

		String avalEnviado = aval.isEsEtico() ? Aval.ETICO_ENVIADO_CEPI : Aval.ENVIADO;

		if (!guardar(avalEnviado)) {
			if (aval.getAviId() != null) {
				mensajeError(
						"Ocurrió un problema al guardar la solicitud. Por favor haga una captura de pantalla con la información, "
								+ "salga del Sistema e ingrese de nuevo, verifique la "
								+ "solicitud de aval con código asignado = " + aval.getAviId()
								+ " en caso de problemas con la misma comuníquese con la línea de soporte 11111");
			} else {
				mensajeError("La solicitud no ha sido guardada. Por favor verifique su información.");
			}
			return;
		}

		if (aval.getTipo().equals("CIN")) {
			for (int i = 0; i < listaLaboratoriosAval.size(); i++) {
				LaboratorioAval lab = listaLaboratoriosAval.get(i);
				servicioGeneral.guardarObjeto(lab);
			}

			if (listaLaboratoriosAvalBorrados != null) {
				if (listaLaboratoriosAvalBorrados.size() > 0) {
					for (int i = 0; i < listaLaboratoriosAvalBorrados.size(); i++) {
						LaboratorioAval lab = listaLaboratoriosAvalBorrados.get(i);
						servicioGeneral.eliminarObjeto(lab);
					}
				}
			}
		}

		// Se verifica si en la sede del investigador, se realiza
		// revisión en facultad
		boolean sedeContieneFacultad = sedesIds.contains(investigadorActual.getDependencia().getSede().getId());
		if (remitirSedePresencia) {
			sedeContieneFacultad = false;
		}

		if ((!sedeContieneFacultad || (aval.getDependencia() != null && aval.getDependencia().getId().length() == 1))
				&& !aval.isEsJornadaDocente() && !aval.isEsProyectoContrapartida() && !aval.isEsEtico()) {
			if (!aval.isEsRegalias()) {
				this.aval.setAviAvalfacultad(Aval.APROBADO);
				if (!Sede.SEDES_ANDINAS.contains(investigadorActual.getDependencia().getSede().getId().toString())
						&& !investigadorActual.getDependencia().getSede().getId().equals(1L)
						&& aval.isEsProyectoInvestigacion()) {
					this.aval.setAviEstado(Aval.ENVIADO_SEDE);
					this.aval.setAviAvalfacultad(Aval.APROBADO);
				} else {
					this.aval.setAviEstado(Aval.REVISADO_FACULTAD);
				}
			} else {
				this.aval.setAviAvalfacultad(Aval.APROBADO);
				if (!Sede.SEDES_ANDINAS.contains(investigadorActual.getDependencia().getSede().getId().toString())
						&& !investigadorActual.getDependencia().getSede().getId().equals(1L)) {
					this.aval.setAviEstado(Aval.ENVIADO_SEDE);
				} else {
					this.aval.setAviEstado(Aval.REVISADO_FACULTAD);
				}
			}
		}

		String ruta = obtenerRutaRevision();

		/*
		 * if(this.aval.getEsAvalParaRevisionSede() != null){
		 * if(this.aval.getEsAvalParaRevisionSede().equals("SI")){ ruta = "DI"; } }
		 */

		/*
		 * Si la ruta del aval inicia en la dirección o es de contrapartida y se dirige
		 * a la dirección se asume aprobado en facultad
		 */
		if (ruta.startsWith("DI-") || "DI".equals(ruta) || (aval.getTipo().equals(Aval.TIPO_INVESTIGACION_CONT)
				&& "D".equals(aval.getDependenciaContrapartida()))) {

			if (aval.isEsRegalias() || aval.isEsPaedRegalias() || aval.isEsConvocatoriaRegalias()
					|| aval.isEsRequisitosRegalias()) {
				/*
				 * if(aval.getEsAvalParaRevisionSede().equals("SI")){
				 * aval.setAviEstado(Aval.ENVIADO_SEDE);
				 * this.aval.setAviAvalfacultad(Aval.APROBADO); }else{
				 */
				aval.setAviEstado(Aval.ENVIADO_FACULTAD);
				// }
			} else {
				this.aval.setAviEstado(Aval.REVISADO_FACULTAD);
				this.aval.setAviAvalfacultad(Aval.APROBADO);
			}
		}

		// Guardar Aval
		this.servicioGeneral.guardarObjeto(this.aval);

		// Envio de correos
		/*
		 * Notificación al investigador
		 */
		CorreoPlantilla cp = cargarPlantilla(76);
		construirCorreo(correo, cp);
		correo.adicionarDireccion(this.investigadorActual.getEmail());
		servicioCorreo.enviarCorreo(correo);

		verificarCorreoInformativoVicerrectoria();

		// Notificación de revisión de aval a la dependencia donde inicia el trámite.
		Correo correoNotificacion = new Correo();
		CorreoPlantilla cpNotificacion = cargarPlantilla(141);
		construirCorreo(correoNotificacion, cpNotificacion);

		// Notificación de solicitud aval a la dependencia donde se emite el aval.
		Correo correoNotificacionGen = new Correo();
		CorreoPlantilla cpNotificacionGen = cargarPlantilla(155);
		construirCorreo(correoNotificacionGen, cpNotificacionGen);

		/*
		 * Si la revisión inicia en la Facultad del docente se envia el correo de la
		 * plantilla 141
		 */
		if (ruta.startsWith("FAC-") && sedeContieneFacultad && !aval.getTipo().equals(Aval.TIPO_INVESTIGACION_CONT)) {
			String sql = "JOIN i.roles r " + "WHERE r.id = 'AF' " + "AND i.dependencia2.facultad.id = '"
					+ investigadorActual.getDependencia().getFacultad().getId() + "' " + "AND i.dependencia2.id <> '0'";

			for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
				if (tieneRolVigente(p, "AF") && p.getEmail() != null) {
					correoNotificacion.adicionarDireccion(p.getEmail());
				}
			}

			servicioCorreo.enviarCorreo(correoNotificacion);
		}

		/*
		 * Si en la sede no se realiza revisión en las facultades
		 */
		if (!sedeContieneFacultad && ("FAC".equals(ruta) || ruta.startsWith("FAC-"))) {
			String sql = "JOIN i.roles r " + "WHERE r.id = 'AD' " + "AND i.dependencia2.sede.id = '"
					+ investigadorActual.getDependencia().getSede().getId() + "'";

			for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
				if (tieneRolVigente(p, "AD") && p.getEmail() != null) {
					correoNotificacionGen.adicionarDireccion(p.getEmail());
				}
			}

			servicioCorreo.enviarCorreo(correoNotificacionGen);
		}

		/*
		 * Si la revisión inicia en la Dirección de Investigación se envia el correo de
		 * la plantilla 141
		 */
		if (ruta.startsWith("DI-") || "DI".equals(ruta)
				|| (aval.getTipo().equals(Aval.TIPO_INVESTIGACION_CONT) && !aval.isEsContrapartidaFacultad())) {

			String hql = "JOIN i.roles r " + "WHERE r.id = 'AD' " + "AND i.dependencia2.sede.id = '"
					+ investigadorActual.getDependencia().getSede().getId() + "'";

			for (InvestigadorInterno inv : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, hql)) {
				if (tieneRolVigente(inv, "AD") && inv.getEmail() != null) {
					correoNotificacion.adicionarDireccion(inv.getEmail());
				}
			}

			servicioCorreo.enviarCorreo(correoNotificacion);
		}

		/*
		 * Si la generación de aval se realiza en la en Facultad
		 */
		if (("FAC".equals(ruta) && sedeContieneFacultad)
				|| (aval.getTipo().equals(Aval.TIPO_INVESTIGACION_CONT) && aval.isEsContrapartidaFacultad())) {

			String sql = "JOIN i.roles r " + "WHERE r.id = 'AF' " + "AND i.dependencia2.facultad.id = '"
					+ this.investigadorActual.getDependencia().getFacultad().getId() + "'";

			for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
				if (tieneRolVigente(p, "AF") && p.getEmail() != null) {
					correoNotificacionGen.adicionarDireccion(p.getEmail());
				}
			}

			servicioCorreo.enviarCorreo(correoNotificacionGen);
		}

		/*
		 * Generación del aval en la Direccion de Investigacion
		 */
		if (ruta.endsWith("-DI") && !aval.getTipo().equals(Aval.TIPO_INVESTIGACION_CONT)) {
			String sql = "JOIN i.roles r " + "WHERE r.id = 'AD' " + "AND i.dependencia2.sede.id = '"
					+ investigadorActual.getDependencia().getSede().getId() + "'";

			for (InvestigadorInterno inv : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
				if (tieneRolVigente(inv, "AD") && inv.getEmail() != null) {
					correoNotificacionGen.adicionarDireccion(inv.getEmail());
				}
			}

			servicioCorreo.enviarCorreo(correoNotificacionGen);
		}

		/*
		 * Generacion del aval en la Vicerrectoría de Investigación
		 */
		if (ruta.endsWith("-VRIE") || (aval.isEsRegaliasVicerrectoria() || aval.isEsPaedRegaliasVicerrectoria()
				|| aval.isEsConvocatoriaRegaliasVicerrectoria())) {

			String sql = "JOIN i.roles r " + "WHERE r.id = 'AV'";

			for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
				if (tieneRolVigente(p, "AV") && p.getEmail() != null) {
					correoNotificacionGen.adicionarDireccion(p.getEmail());
				}
			}

			servicioCorreo.enviarCorreo(correoNotificacionGen);
			verificarEnviarCorreoNotificacionSedes();
		}

		/*
		 * Generación de aval en Unidad Académica Básica
		 */
		if ("UAB".equals(ruta) || ruta.startsWith("UAB-")) {
			enviarCorreoUab();

			String sql = "JOIN i.roles r " + "WHERE r.id = 'DD' " + "AND i.dependencia2.id = '"
					+ investigadorActual.getDependencia().getId() + "'";

			for (InvestigadorInterno inv : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
				if (tieneRolVigente(inv, "DD") && inv.getEmail() != null) {
					correoNotificacion.adicionarDireccion(inv.getEmail());
				}
			}

			servicioCorreo.enviarCorreo(correoNotificacion);
		}

		// Avales éticos
		if (aval.isEsEtico()) {
			Correo correoCEPI = new Correo();
			CorreoPlantilla cpCEPIEnviado = cargarPlantilla(367);

			AvalComiteEtica cepi = servicioGeneral
					.obtenerObjetoXID(AvalComiteEtica.class, aval.getCepi().getId().toString()).get(0);
			if (!esNulo(cepi))
				aval.setCepi(cepi);

			String sql = "JOIN i.roles r " + "WHERE r.id = 'CEPI' " + "AND i.dependencia2.id = '"
					+ cepi.getDependencia().getId().toString() + "'";

			for (InvestigadorInterno inv : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
				if (tieneRolVigente(inv, "CEPI") && !esNulo(inv.getEmail())) {
					correoCEPI.adicionarDireccion(inv.getEmail());
				}
			}

			construirCorreo(correoCEPI, cpCEPIEnviado);
			servicioCorreo.enviarCorreo(correoCEPI);
		}

		mensajeInfo("Guardado y enviado. Código de aval asignado = " + aval.getAviId()
				+ ". El siguiente paso, será la revisión y "
				+ "generación del aval por parte de la dependencia respectiva."
				+ " Recibirá una notificación por correo electrónico " + "informándole acerca de esta revisión. ");

		eliminarManejadorAvalMenu();
	}

	private String obtenerRutaRevision() {
		// Ruta de revisión
		String hql = "select dd from Dominio d, DominioDetalle dd where dd.identificador.id = d.id "
				+ " and dd.identificador.tipo = '" + aval.getTipo() + "' and d.tipo = 'ACTIVIDAD_AVAL_NEW'";
		List<DominioDetalle> listaDom = servicioGeneral.obtenerObjetos(DominioDetalle.class, hql);
		DominioDetalle dominio = (DominioDetalle) listaDom.get(0);
		return dominio.getEstado();
	}

	private void enviarCorreoUab() {

		Correo correoUab = new Correo();
		CorreoPlantilla cpUab = cargarPlantilla(180);
		construirCorreo(correoUab, cpUab);
		String nombreUab = "";

		List<Dependencia> dep = servicioGeneral.obtenerListaObjetosWhere(Dependencia.class,
				" WHERE d.id = '" + aval.getDependencia() + "'");
		if (!esListaVacia(dep)) {
			nombreUab = dep.get(0).getNombre();
			if (!esCadenaVacia(dep.get(0).getEmail())) {
				correoUab.adicionarDireccion(dep.get(0).getEmail());
			}
		}
		correoUab.setCuerpo(reemplazarEspaciosCuerpo(cpUab.getCuerpo()).replaceAll("<<DEPENDENCIA>>", nombreUab));
		servicioCorreo.enviarCorreo(correoUab);
	}

	private void verificarEnviarCorreoNotificacionSedes() {
		if (esListaVacia(aval.getListaDependencias())) {
			return;
		}
		Correo notificacionSedes = new Correo();
		CorreoPlantilla cpNotif = cargarPlantilla(213);
		construirCorreo(notificacionSedes, cpNotif);
		String nombreSedes = "";

		for (int i = 0; i < aval.getListaDependencias().size(); i++) {
			Dependencia dep = servicioDependencia.obtenerDependencia(aval.getListaDependencias().get(i).getId());
			if ("".equals(nombreSedes)) {
				nombreSedes = nombreSedes + dep.getNombre();
			} else {
				nombreSedes = nombreSedes + ", " + dep.getNombre();
			}

			String sql = "JOIN i.roles r " + "WHERE r.id = 'AD' " + "AND i.dependencia2.sede.id = '"
					+ aval.getListaDependencias().get(i).getId() + "'";

			for (InvestigadorInterno per : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
				if (tieneRolVigente(per, "AD") && per.getEmail() != null) {
					notificacionSedes.adicionarDireccion(per.getEmail());
				}
			}

		}
		notificacionSedes.setCuerpo(reemplazarEspaciosCuerpo(cpNotif.getCuerpo()).replaceAll("<<SEDES>>", nombreSedes));
		servicioCorreo.enviarCorreo(notificacionSedes);
	}

	/*
	 * Notificación de registro de aval de regalías a la vicerrectoría. Cuando no es
	 * intersedes, solo es informativo
	 */
	private void verificarCorreoInformativoVicerrectoria() {
		if ((this.aval.isEsRegalias() && !interSedes) || (this.aval.isEsPaedRegalias() && !interSedes)
				|| (this.aval.isEsConvocatoriaRegalias() && !interSedes)
				|| (this.aval.isEsRequisitosRegalias() && !interSedes)) {
			Correo correoinfo = new Correo();
			CorreoPlantilla cpvri = cargarPlantilla(157);
			construirCorreo(correoinfo, cpvri);
			String sql = "JOIN i.roles r WHERE r.id = 'AV'";

			for (InvestigadorInterno p : servicioGeneral.obtenerListaObjetosWhere(InvestigadorInterno.class, sql)) {
				if (tieneRolVigente(p, "AV") && p.getEmail() != null) {
					correoinfo.adicionarDireccion(p.getEmail());
				}
			}

			servicioCorreo.enviarCorreo(correoinfo);
		}
	}

	public void eliminarManejadorAvalMenu() {
		sesion.removeAttribute("ManejadorAvalMenu");
	}

	private Correo construirCorreo(Correo correo, CorreoPlantilla cp) {
		correo.setOrigen(Correo.CORREO_HERMES_SOLICITUDES);
		correo.setAsunto(reemplazarEspaciosAsunto(cp.getAsunto()));
		correo.setCuerpo(reemplazarEspaciosCuerpo(cp.getCuerpo()));
		// correo.adicionarCopiaOculta(Correo.CORREO_HERMES_SOLICITUDES);
		return correo;
	}

	private String reemplazarEspaciosAsunto(String asunto) {
		asunto = asunto.replaceAll("<<ID_AVAL>>", aval.getAviId().toString());
		return asunto;
	}

	private String reemplazarEspaciosCuerpo(String cuerpo) {
		cuerpo = cuerpo.replaceAll("<<INVESTIGADOR>>", this.investigadorActual.getNombreCompletoMinusculas());
		cuerpo = cuerpo.replaceAll("<<ID_AVAL>>", aval.getAviId().toString());
		cuerpo = cuerpo.replaceAll("<<TIPO_AVAL>>", aval.getNombreTipo());

		if (aval.isEsEtico()) {
			String dependenciaCEPI = !esNulo(aval.getCepi().getDependencia())
					? aval.getCepi().getDependencia().getNombre()
					: "ERROR NOMBRE DEPENDENCIA";
			cuerpo = cuerpo.replaceAll("<<NOMBRE_DEPENDENCIA>>", dependenciaCEPI);
			cuerpo = cuerpo.replaceAll("<<TIPO_RECURSO>>",
					!esNulo(aval.getTipoRecursoAvalEtico()) ? aval.getTipoRecursoAvalEtico().getNombre()
							: "ERROR NOMBRE TIPO RECURSO");
		}

		return cuerpo;
	}

	/**
	 * Validar completitud de la información de la solicitud de aval
	 */
	public boolean validaInformacionSolicitud() {

		boolean bandera = true;

		if (this.aval == null) {
			return false;
		}

		if (!verificarEstadoProyecto()) {
			return false;
		}

		if (!aval.isEsRegalias() && !aval.isEsPaedRegalias() && !aval.isEsConvocatoriaRegalias()
				&& !aval.isEsRequisitosRegalias() && !aval.isEsJornadaDocente() && !aval.isEsEtico()
				&& !validarGeneral()) {
			bandera = false;
		}

		if ((aval.isEsRegalias() || aval.isEsPaedRegalias() || aval.isEsConvocatoriaRegalias()
				|| aval.isEsRequisitosRegalias()) && !validarPaedRegalias()) {
			bandera = false;
		}

		if (aval.isEsMovilidadEvento() && !validarMovilidad()) {
			bandera = false;
		}

		if (aval.isEsProyectoInvestigacion() && !validarMontoEntidadConvocante()) {
			bandera = false;
		}

		if (aval.isEsProyectoContrapartida() && !validarProyectoContrapartida()) {
			bandera = false;
		}

		if (aval.isEsJornadaDocente() && !validarJornadaDocente()) {
			bandera = false;
		}

		if (aval.isEsEtico() && !validarEtico()) {
			bandera = false;
		}

		if (aval.isEsArticuloInvestigacion() && !validarArticuloInvestigacion()) {
			bandera = false;
		}

		if (aval.isEsCentroInvestigacion() && esCadenaVacia(aval.getTipoAvalCIN())) {
			mensajeError("No se ha seleccionado la opción de solicitud de aval, es obligatorio. ");
			bandera = false;
		}

		if (aval.isEsCentroInvestigacion() && esCadenaVacia(aval.getAviTitulo())
				&& aval.getTipoAvalCIN().equals("CI")) {
			mensajeError("No se ha diligenciado el nombre del centro o instituto de investigación, es obligatorio. ");
			bandera = false;
		} else if (aval.isEsCentroInvestigacion() && getListaLaboratoriosAval().isEmpty()
				&& aval.getTipoAvalCIN().equals("L")) {
			mensajeError("No se han asociado laboratorios al aval, es obligatorio. ");
			bandera = false;
		}

		if (aval.isEsCentroInvestigacion() && aval.getTipoAvalCIN().equals("L") && aval.getIdProyecto().equals(0L)) {
			mensajeError("No se ha asociado proyecto al aval, es obligatorio. ");
			bandera = false;
		}

		if (aval.isEsGrupoInvestigacion() && esCadenaVacia(aval.getNombreGrupoPINV())) {
			mensajeError("No se ha seleccionado el grupo de investigación, para este tipo de aval, es obligatorio. ");
			bandera = false;
		}

		if (aval.isEsGrupoInvestigacion() && aval.getListaSubTiposEspecificacion().isEmpty()) {
			mensajeError("No se ha agregado la especificación del aval. ");
			bandera = false;
		}

		if (!aceptaTerminos && (!aval.isEsJornadaDocente() && !aval.isEsProyectoContrapartida())) {
			mensajeError("Para enviar la solicitud de aval debe "
					+ "aceptar los términos marcando la casilla en '¿Acepta los términos?'");
			bandera = false;
		}

		/**
		 * if (convocatoriaInternacional) { boolean tieneArchivoTipo4 = false; for
		 * (ArchivoAval archivo : listaArchivos) { if (archivo.getTipo()!=null &&
		 * archivo.getTipo().equals("4")) { tieneArchivoTipo4 = true; break; } } if
		 * (!tieneArchivoTipo4) { mensajeError( "Por favor, cargue la carta de
		 * autorización de las horas de los docentes participantes especificando el
		 * valor de la dedicación."); bandera = false; } } if (formatoExterno) { boolean
		 * tieneCarta = false; for (ArchivoAval archivo : listaArchivos) { if
		 * (archivo.getTipo()!=null && archivo.getTipo().equals("3")) { tieneCarta =
		 * true; break; } } if (!tieneCarta) { mensajeError("Por favor, cargue el
		 * borrador de carta de aval institucional en español."); bandera = false; } }
		 */

		for (int i = 0; i < listaTipoArchivo.size(); i++) {
			boolean loEncontro = false;
			for (int j = 0; j < listaArchivos.size(); j++) {
				if (listaTipoArchivo.get(i).getId().equals(listaArchivos.get(j).getTipoArchivo().getId())
						&& listaTipoArchivo.get(i).isObligatorio()) {
					loEncontro = true;
					break;
				}
			}
			if (!loEncontro && listaTipoArchivo.get(i).isObligatorio()) {
				mensajeError("Por favor debe adjuntar el documento: " + listaTipoArchivo.get(i).getNombre() + "");
				bandera = false;
			}

		}

		if (!aval.isEsGrupoInvestigacion() && !aval.isEsInvestigadorIndependiente() && !aval.isEsCentroInvestigacion()
				&& !aval.isEsArticuloInvestigacion() && fichaSeleccionada.getListaGrupos() != null
				&& fichaSeleccionada.getListaGrupos().size() > 0) {
			boolean investigadorEnGrupo = false;
			for (int i = 0; i < fichaSeleccionada.getListaGrupos().size(); i++) {
				Grupo g = (Grupo) fichaSeleccionada.getListaGrupos().get(i);
				for (int j = 0; j < g.getListaInvestigadoresGrupo().size(); j++) {
					InvestigadorGrupo ig = g.getListaInvestigadoresGrupo().get(j);
					if (ig.getInvestigador().getId().getDocumento().equals(personaActual.getId().getDocumento())) {
						investigadorEnGrupo = true;
						break;
					}
				}
				if (investigadorEnGrupo) {
					break;
				}
			}

			if (!investigadorEnGrupo) {
				mensajeError("Usted debe pertenecer al menos a uno de los grupos asociados");
				bandera = false;
			}

		}
		return bandera;
	}

	private boolean verificarEstadoProyecto() {
		if (((!aval.getTipo().equals(Aval.TIPO_GRUPO_INVESTIGACION)
				&& !aval.getTipo().equals(Aval.TIPO_INVESTIGADOR_INDEPENDIENTE))) && fichaSeleccionada != null
				&& fichaSeleccionada.getEstadoProyecto().getId().equals(EstadoProyecto.INGRESANDO)) {
			mensajeError("El proyecto " + fichaSeleccionada.getId().toString()
					+ " asociado a este aval se encuentra en estado 'Ingresando', debe finalizar "
					+ "su edición para enviar la solicitud de aval.");
			return false;
		}
		if ((aval.isEsCentroInvestigacion() && aval.getTipoAvalCIN().equals("L"))
				&& ((Proyecto) servicioProyecto.obtenerProyectosXId(aval.getIdProyecto()).get(0)).getEstadoProyecto()
						.getId().equals(EstadoProyecto.INGRESANDO)) {
			mensajeError("El proyecto " + fichaSeleccionada.getId().toString()
					+ " asociado a este aval se encuentra en estado 'Ingresando', debe finalizar "
					+ "su edición para enviar la solicitud de aval.");
			return false;
		}
		return true;
	}

	private boolean validarGeneral() {
		boolean bandera = true;
		if (esCadenaVacia(aval.getAviEntidad())) {
			mensajeError("Debe seleccionar la entidad externa para que pueda seleccionar la convocatoria");
			bandera = false;
		}
		if (esCadenaVacia(aval.getAviConvocatoria())) {
			mensajeError("Debe seleccionar la convocatoria a la cual se presentará, si no la "
					+ "encuentra puede solicitar su habilitación, "
					+ "ingresando los datos solicitados en la opción 'Otra'.");
			bandera = false;
		} else if ("0".equals(aval.getAviConvocatoria())) {
			mensajeError("Si ya solicitó la creación de una nueva convocatoria externa, debe esperar respuesta sobre "
					+ "su habilitación para continuar con el trámite del aval. La respuesta será enviada a "
					+ "su correo electrónico.");
			bandera = false;
		}

		if (!aval.isEsCentroInvestigacion() && fichaSeleccionada != null
				&& !esCadenaVacia(fichaSeleccionada.getMecanismoParticipacion())
				&& !esCadenaVacia(aval.getAviConvocatoria()) && !validarConvocatoria()) {
			bandera = false;
		}

		if ((aval.getAviEstado() == null || !aval.getAviEstado().equals(Aval.DEVUELTO))
				&& !aval.isEsProyectoContrapartida()) {
			if (aval.getAviFechaCierreConv() != null && aval.getAviFechaCierreConv().before(aval.getFecha())) {
				mensajeError("La convocatoria seleccionada ya finalizó "
						+ "o cierra el día de hoy, recuerde que la solicitud debe hacerse al menos "
						+ "dos días antes de la fecha de cierre de la convocatoria.");
				bandera = false;

			} else if (!esCadenaVacia(aval.getAviConvocatoria())) {

				if (fechaLimiteSede != null) {
					if ((new Date()).after((Date) fechaLimiteSede)) {
						mensajeError("No es posible enviar la solicitud de aval, la solicitud debe "
								+ "realizarse máximo en la fecha límite establecida para la sede.");
						bandera = false;
					}
				} else {
					if ((new Date()).after((Date) convocatoria.getFechaMaxRegistro())) {
						mensajeError("No es posible enviar la solicitud de aval, la solicitud debe "
								+ "realizarse máximo en la fecha límite de registro interno.");
						bandera = false;
					}
				}

			}
		}
		if (esCadenaVacia(aval.getAviEntidadejecutora()) && !aval.getTipo().equals(Aval.TIPO_GRUPO_INVESTIGACION)
				&& !aval.getTipo().equals(Aval.TIPO_INVESTIGADOR_INDEPENDIENTE) && !aval.isEsCentroInvestigacion()) {
			mensajeError("Debe diligenciar el nombre de la entidad ejecutora de los recursos");
			bandera = false;
		}
		return bandera;
	}

	private boolean validarEtico() {
		boolean bandera = true;

		if (esNulo(aval.getCepi()) || aval.getCepi().getId().equals(0L)) {
			mensajeError("Debe seleccionar el comité de ética de primera instancia.");
			bandera = false;
		}

		if (aval.getIdProyecto().equals(0L)) {
			mensajeError("No se ha asociado proyecto al aval, es obligatorio.");
			bandera = false;
		}

		if (esCadenaVacia(aval.getConsideracionesEticas())) {
			mensajeError(
					"Debe ingresar las consideraciones éticas de su proyecto, en caso de que no se tengan, por favor indíquelo.");
			bandera = false;
		}

		if (listaArchivos.isEmpty()) {
			mensajeError("Debe adjuntar al menos un archivo.");
			bandera = false;
		}

		return bandera;
	}

	private boolean validarArticuloInvestigacion() {
		boolean bandera = true;

		if (esCadenaVacia(aval.getAvalArticuloTitulo())) {
			mensajeError("Debe ingresar el título del articulo.");
			bandera = false;
		}

		if (esNulo(aval.getAvalArticuloEstado())) {
			mensajeError("Debe seleccionar una opción de la lista 'Artículos científicos'");
			bandera = false;
		}

		if (esCadenaVacia(aval.getAvalArticuloResumen())) {
			mensajeError("Debe ingresar el resumen del articulo.");
			bandera = false;
		}

		if (esCadenaVacia(aval.getAvalArticuloRevista())) {
			mensajeError("Debe ingresar la revista del articulo.");
			bandera = false;
		}

		if (listaArchivos.isEmpty()) {
			mensajeError("Debe adjuntar al menos un archivo.");
			bandera = false;
		}

		return bandera;
	}

	private boolean validarConvocatoria() {
		boolean bandera = true;
		if (fichaSeleccionada.getMecanismoParticipacion().equals("FIN_EXTERNA")
				&& Long.parseLong(aval.getAviConvocatoria()) >= 0) {
			mensajeError(
					"Por favor verifique la forma de presentación a la entidad externa, en el proyecto se ha indicado que la financiación es externa sin convocatoria");
			bandera = false;
		} else if (fichaSeleccionada.getMecanismoParticipacion().equals("FIN_EXT_CONV")
				&& Long.parseLong(aval.getAviConvocatoria()) < 0) {
			mensajeError(
					"Por favor verifique la forma de presentación a la entidad externa, en el proyecto se ha indicado que la financiación es externa a través de una convocatoria");
			bandera = false;
		}
		return bandera;
	}

	private boolean validarMontoEntidadConvocante() {
		boolean bandera = true;
		if (aval.getAviMontoEntConv() == 0L && (aval.getModalidad() == null
				|| (!"ROL_COOPER".equals(aval.getModalidad()) && !"ROL_PARTICIP".equals(aval.getModalidad())))) {
			mensajeError("Debe indicar el valor que va a solicitar a la entidad convocante");
			bandera = false;
		}
		return bandera;
	}

	private boolean validarPaedRegalias() {
		boolean bandera = true;

		if (!aval.getTieneConvocatoriaProyecto().equals("SI")) {
			aval.setAviConvocatoria("0");
		}
		/*
		 * if (esCadenaVacia(aval.getEsAvalParaRevisionSede())) {
		 * mensajeError("Debe seleccionar si el proyecto es presentado por nivel sede y no requiere aval de la Facultad"
		 * ); bandera = false; }
		 */

		if (aval.getTieneConvocatoriaProyecto().equals("SI")) {
			if (esCadenaVacia(aval.getAviEntidad())) {
				mensajeError("Debe seleccionar la entidad externa para que pueda seleccionar la convocatoria");
				bandera = false;
			}
			if (esCadenaVacia(aval.getAviConvocatoria())) {
				mensajeError("Debe seleccionar la convocatoria a la cual se presentará, si no la "
						+ "encuentra puede solicitar su habilitación, "
						+ "ingresando los datos solicitados en la opción 'Otra'.");
				bandera = false;
			} else if ("0".equals(aval.getAviConvocatoria())) {
				mensajeError(
						"Si ya solicitó la creación de una nueva convocatoria externa, debe esperar respuesta sobre "
								+ "su habilitación para continuar con el trámite del aval. La respuesta será enviada a "
								+ "su correo electrónico.");
				bandera = false;
			} else {
				if (aval.isEsConvocatoriaRegalias()) {
					if ((aval.getAviEstado() == null || !aval.getAviEstado().equals(Aval.DEVUELTO))) {
						if (aval.getAviFechaCierreConv() != null
								&& aval.getAviFechaCierreConv().before(aval.getFecha())) {
							mensajeError("La convocatoria seleccionada ya finalizó "
									+ "o cierra el día de hoy, recuerde que la solicitud debe hacerse al menos "
									+ "dos días antes de la fecha de cierre de la convocatoria.");
							bandera = false;

						} else if (!esCadenaVacia(aval.getAviConvocatoria())) {

							if (fechaLimiteSede != null) {
								if ((new Date()).after((Date) fechaLimiteSede)) {
									mensajeError("No es posible enviar la solicitud de aval, la solicitud debe "
											+ "realizarse máximo en la fecha límite establecida para la sede.");
									bandera = false;
								}
							} else {
								if ((new Date()).after((Date) convocatoria.getFechaMaxRegistro())) {
									mensajeError("No es posible enviar la solicitud de aval, la solicitud debe "
											+ "realizarse máximo en la fecha límite de registro interno.");
									bandera = false;
								}
							}

						}
					}
				}
			}
			if (aval.isEsRequisitosRegalias() && (fechaResultadosRegalias == null
					|| (fechaResultadosRegalias != null && getToday().before(fechaResultadosRegalias)))) {
				mensajeError(
						"Todavía no se conocen los resultados de la convocatoria del aval de presentación de regalías asociado al proyecto");
				bandera = false;
			}
		}

		/*
		 * if (aval.getAviMontoEntConv() == 0L) {
		 * mensajeError("Debe indicar el valor que va a solicitar a la entidad territorial, este se toma de las fuentes externas del proyecto asociado."
		 * ); bandera = false; }
		 */

		if ((aval.getEntidadTerritorialProyectoRegalidas() == null
				|| "".equals(aval.getEntidadTerritorialProyectoRegalidas())) && !aval.isEsConvocatoriaRegalias()
				&& !aval.isEsRequisitosRegalias()) {
			mensajeError("Debe seleccionar la entidad territorial a la que se presentará el proyecto.");
			bandera = false;
		}

		if (aval.isEsConvocatoriaRegalias() && aval.getAviEntidad().equals(FuenteFinanciacion.ID_MINCIENCIAS)) {
			if (esCadenaVacia(aval.getCodigoMinciencias())) {
				mensajeError("Debe indicar el código SIGP de Minciencias.");
				bandera = false;
			} else {
				try {
					Integer.parseInt(aval.getCodigoMinciencias());
				} catch (NumberFormatException e) {
					mensajeError("El código SIGP de Minciencias debe ser numérico");
					bandera = false;
				}
			}
		}

		Long total = getValorMontosDepartamentosPorAgregar();
		if (total > 0) {
			mensajeError(
					"Aún queda valor por asignar a las regiones, de acuerdo al valor indicado en el campo 'Monto a solicitar'.");
			bandera = false;
		} else if (total < 0) {
			mensajeError("La suma de los valores ingresados "
					+ "por regiones, supera el valor ingresado a financiar en el proyecto.");
			bandera = false;
		}

		if (esCadenaVacia(aval.getAviEntidadejecutora())) {
			mensajeError("Debe diligenciar el nombre de la entidad ejecutora de los recursos");
			bandera = false;
		}

		if (esCadenaVacia(aval.getDepartamento())) {
			mensajeError("Debe indicar el departamento de ejecución del proyecto");
			bandera = false;
		}

		if (esCadenaVacia(aval.getLugarEjecucion())) {
			mensajeError("Debe indicar el municipio de ejecución del proyecto");
			bandera = false;
		}

		if (esCadenaVacia(aval.getFase())) {
			mensajeError("Debe indicar la fase del proyecto");
			bandera = false;
		}
		return bandera;
	}

	private boolean validarMovilidad() {
		boolean bandera = true;
		if (esCadenaVacia(aval.getTipoEvento())) {
			mensajeError("Debe seleccionar el subtipo de aval para el aval de movilidad/evento");
			bandera = false;
		}

		if (aval.getAviMontoEntConv() == 0L) {
			mensajeError("Debe indicar el valor que va a solicitar a la entidad convocante");
			bandera = false;
		}

		if (esCadenaVacia(aval.getPais()) || "00".equals(aval.getPais())) {
			mensajeError("Debe seleccionar el país de la movilidad/evento");
			bandera = false;
		}

		if (esCadenaVacia(aval.getLugarEjecucion())) {
			mensajeError("Debe seleccionar la dependencia de ejecución financiera dentro de la Universidad Nacional");
			bandera = false;
		}

		if (("Participación en evento".equals(aval.getTipoEvento()) || "Evento".equals(aval.getTipoEvento()))
				&& esCadenaVacia(aval.getEvento())) {
			mensajeError("Debe indicar el nombre del evento.");
			bandera = false;
		}

		if ("Fortalecimiento de redes".equals(aval.getTipoEvento())
				&& esListaVacia(listaEntidadesParticipantesFortalecimiento)) {
			mensajeError("No se han agregado entidades participantes del fortalecimiento.");
			bandera = false;
		}

		if (aval.getFechaInicio() == null || aval.getFechaFin() == null) {
			mensajeError("Las fechas de inicio y fin de la movilidad/evento deben estar diligenciadas.");
			bandera = false;
		}

		if (aval.getFechaInicio() != null && aval.getFechaFin() == null
				&& aval.getFechaInicio().after(aval.getFechaFin())) {
			mensajeError("Las fechas de inicio no debe ser superior a la fecha final verifique su información.");
			bandera = false;
		}

		if (esCadenaVacia(aval.getObjetivoPrincipal())) {
			mensajeError("No se ha ingresado el objetivo de la movilidad/evento.");
			bandera = false;
		}

		if (!validarResultados(listaResultados)) {
			bandera = false;
		}

		if (esListaVacia(listaParticipantesAval)) {
			mensajeError("No se encuentran personas participantes de la movilidad/evento.");
			bandera = false;
		}

		if (!verificarInformacionFinancieraMovilidad()) {
			bandera = false;
		}

		return bandera;
	}

	private boolean verificarInformacionFinancieraMovilidad() {
		boolean bandera = true;
		FuenteFinanciacion entidadConvocante = new FuenteFinanciacion();
		entidadConvocante.setId(aval.getAviEntidad());
		if (verificarTotalEntidad(entidadConvocante) < aval.getAviMontoEntConv()) {
			mensajeError("No se ha discriminado en rubros la totalidad del valor solicitado a la entidad convocante.");
			bandera = false;
		} else if (verificarTotalEntidad(entidadConvocante) > aval.getAviMontoEntConv()) {
			mensajeError("Los rubros discriminados para el valor solicituado a la entidad convocante "
					+ "están superando dicho valor.");
			bandera = false;
		}
		return bandera;
	}

	private boolean validarResultados(List<ResultadoAval> listaResultados) {
		boolean bandera = true;
		// VALIDA LA EXISTENCIA DE RESULTADOS EN LA RESPECTIVA LISTA
		List<ResultadoAval> listaAuxiliar = new ArrayList<ResultadoAval>();
		for (int i = 0; i < listaResultados.size(); i++) {
			ResultadoAval rp = (ResultadoAval) listaResultados.get(i);
			if (rp.isBorrable()) {
				listaAuxiliar.add(rp);
				aval.borrarResultado(rp);
			} else {
				aval.adicionarResultado(rp);
			}
		}
		listaResultados.removeAll(listaAuxiliar);

		if (esListaVacia(listaResultados)) {
			mensajeError("No se encuentran resultados asociados al proyecto.");
			bandera = false;
		}
		return bandera;
	}

	private boolean validarProyectoContrapartida() {
		boolean bandera = true;
		if (aval.getAviFrescosunal() == 0L) {
			mensajeError("Debe indicar el valor que va a solicitar a la Universidad");
			bandera = false;
		}
		if (aval.getLugarEjecucion() == null || "".equals(aval.getLugarEjecucion())) {
			mensajeError("Debe indicar el lugar de ejecución del proyecto en la Universidad Nacional");
			bandera = false;
		}
		if (aval.getDependenciaContrapartida() == null || "".equals(aval.getDependenciaContrapartida())) {
			mensajeError("Debe indicar el la dependencia a la que va a solicitar la contrapartida para el proyecto.");
			bandera = false;
		}
		FuenteFinanciacion entidad = new FuenteFinanciacion();
		entidad.setId(FUENTE_FINANCIACION_UNAL);
		Long total = verificarTotalEntidad(entidad);
		if (total < aval.getAviFrescosunal()) {
			mensajeError("Aún queda valor solicitado a la Universidad por asignar a los rubros.");
			bandera = false;
		}
		return bandera;
	}

	private boolean validarJornadaDocente() {
		boolean bandera = true;
		aval.setAviConvocatoria("0");
		if (!fichaSeleccionada.getEstadoProyecto().getId().equals(EstadoProyecto.PROPUESTO)) {
			mensajeError("Para solicitar aval de Jornada Docente  el proyecto debe encontrarse en estado "
					+ "'Propuesto', el estado del proyecto " + aval.getIdProyecto() + " es "
					+ fichaSeleccionada.getEstadoProyecto().getNombre() + ".");
			bandera = false;
		}
		if (esCadenaVacia(aval.getConsideracionesEticas())) {
			mensajeError("Debe ingresar las consideraciones éticas de su "
					+ "proyecto, en caso de que no se tengan, por favor indíquelo. ");
			bandera = false;
		}

		if (aval.getDuracion() == 0L) {
			mensajeError("No se ha podido determinar la duración del proyecto. ");
			bandera = false;
		}

		if (esListaVacia(listaActividades)) {
			mensajeError("No se han encontrado las actividades, de acuerdo a la duración del proyecto");
			bandera = false;
		} else {
			int desdeCero = 0;
			Long tiempo;
			for (int i = 0; i < listaActividades.size(); i++) {
				AvalActividad actividad = listaActividades.get(i);
				tiempo = actividad.getMesInicial() + actividad.getDuracionMeses();
				if (actividad.getMesInicial() == 0) {
					desdeCero++;
				}
				if (tiempo > aval.getDuracion()) {
					mensajeError("Una de las actividades supera la duración del proyecto");
					bandera = false;
				}
			}
			if (desdeCero == 0) {
				mensajeError("No se ha encontrado ninguna actividad que inicie desde el mes cero. "
						+ "Recuerde las actividades deben iniciar desde este mes.");
				bandera = false;
			}
		}
		return bandera;
	}

	public List<ResultadoAval> obtenerListaResultados() {

		listaResultados = new ArrayList<ResultadoAval>();
		if (aval != null && aval.getResultados().size() > 0) {
			for (Iterator<ResultadoAval> iterador = aval.getResultados().iterator(); iterador.hasNext();) {
				ResultadoAval resultadoAval = (ResultadoAval) iterador.next();
				listaResultados.add(resultadoAval);
			}
		}
		return listaResultados;
	}

	// Agregar sede
	public void agregarSede() {
		boolean yaEsta = false;
		if (aval.getListaDependencias() != null && aval.getListaDependencias().size() > 0) {
			for (int i = 0; i < aval.getListaDependencias().size(); i++) {
				if (aval.getListaDependencias().get(i).getId().equals(sedeSel)) {
					yaEsta = true;
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage("msgs", new FacesMessage("La sede ya ha sido agregada a la lista", ""));
					break;
				} else {
					yaEsta = false;
				}
			}
		}

		if (!yaEsta) {
			Dependencia dep = servicioDependencia.obtenerDependencia(sedeSel);
			aval.adicionarDependencia(dep);
		}
	}

	public void borrarSede() {
		aval.borrarDependencia(sedeSeleccionada);
	}

	public boolean isInterSedes() {
		return interSedes;
	}

	public void setInterSedes(boolean interSedes) {
		this.interSedes = interSedes;
	}

	public ArchivoConvocatoriaExterna getArchivoExterno() {
		return archivoExterno;
	}

	public void setArchivoExterno(ArchivoConvocatoriaExterna archivoExterno) {
		this.archivoExterno = archivoExterno;
	}

	public List<Financiacion> getListaEntidadesCoejecutorasPry() {
		return listaEntidadesCoejecutorasPry;
	}

	public void setListaEntidadesCoejecutorasPry(List<Financiacion> listaEntidadesCoejecutorasPry) {
		this.listaEntidadesCoejecutorasPry = listaEntidadesCoejecutorasPry;
	}

	public org.primefaces.model.UploadedFile getArchivoCargado() {
		return archivoCargado;
	}

	public void setArchivoCargado(org.primefaces.model.UploadedFile archivoCargado) {
		this.archivoCargado = archivoCargado;
	}

	public String getTipoDocParticipante() {
		return tipoDocParticipante;
	}

	public void setTipoDocParticipante(String tipoDocParticipante) {
		this.tipoDocParticipante = tipoDocParticipante;
	}

	public String getDocumentoParticipante() {
		return documentoParticipante;
	}

	public void setDocumentoParticipante(String documentoParticipante) {
		this.documentoParticipante = documentoParticipante;
	}

	public Long getHorasParticipante() {
		return horasParticipante;
	}

	public void setHorasParticipante(Long horasParticipante) {
		this.horasParticipante = horasParticipante;
	}

	public String getNombreProfesorVisitante() {
		return nombreProfesorVisitante;
	}

	public void setNombreProfesorVisitante(String nombreProfesorVisitante) {
		this.nombreProfesorVisitante = nombreProfesorVisitante;
	}

	public CoinvestigadorAval getParticipanteEliminar() {
		return participanteEliminar;
	}

	public void setParticipanteEliminar(CoinvestigadorAval participanteEliminar) {
		this.participanteEliminar = participanteEliminar;
	}

	public List<CoinvestigadorAval> getListaParticipantesJornadaDocente() {
		return listaParticipantesJornadaDocente;
	}

	public void setListaParticipantesJornadaDocente(List<CoinvestigadorAval> listaParticipantesJornadaDocente) {
		this.listaParticipantesJornadaDocente = listaParticipantesJornadaDocente;
	}

	public Long getMesesParticipante() {
		return mesesParticipante;
	}

	public void setMesesParticipante(Long mesesParticipante) {
		this.mesesParticipante = mesesParticipante;
	}

	public List<AvalActividad> getListaActividades() {
		return listaActividades;
	}

	public void setListaActividades(List<AvalActividad> listaActividades) {
		this.listaActividades = listaActividades;
	}

	public AvalActividad getActividadSeleccionada() {
		return actividadSeleccionada;
	}

	public void setActividadSeleccionada(AvalActividad actividadSeleccionada) {
		this.actividadSeleccionada = actividadSeleccionada;
	}

	public String getDescripcionActividad() {
		return descripcionActividad;
	}

	public void setDescripcionActividad(String descripcionActividad) {
		this.descripcionActividad = descripcionActividad;
	}

	public Long getMesInicialActividad() {
		return mesInicialActividad;
	}

	public void setMesInicialActividad(Long mesInicialActividad) {
		this.mesInicialActividad = mesInicialActividad;
	}

	public Long getDuracionMesesActividad() {
		return duracionMesesActividad;
	}

	public void setDuracionMesesActividad(Long duracionMesesActividad) {
		this.duracionMesesActividad = duracionMesesActividad;
	}

	public MontoAno getMonto() {
		return monto;
	}

	public void setMonto(MontoAno monto) {
		this.monto = monto;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public List<ResultadoAval> getListaResultados() {
		return listaResultados;
	}

	public void setListaResultados(List<ResultadoAval> listaResultados) {
		this.listaResultados = listaResultados;
	}

	public DataTable getTablaResultados() {
		return tablaResultados;
	}

	public void setTablaResultados(DataTable tablaResultados) {
		this.tablaResultados = tablaResultados;
	}

	public String getMensajeErrorResultados() {
		return mensajeErrorResultados;
	}

	public void setMensajeErrorResultados(String mensajeErrorResultados) {
		this.mensajeErrorResultados = mensajeErrorResultados;
	}

	public ResultadoAval getResultadoTabla() {
		return resultadoTabla;
	}

	public void setResultadoTabla(ResultadoAval resultadoTabla) {
		this.resultadoTabla = resultadoTabla;
	}

	public List<SelectItem> getParticipanteMovEventosItem() {
		return participanteMovEventosItem;
	}

	public List<SelectItem> getParticipanteMovPasantiaItem() {
		return participanteMovPasantiaItem;
	}

	public List<CoinvestigadorAval> getListaParticipantesAval() {
		return listaParticipantesAval;
	}

	public void setListaParticipantesAval(List<CoinvestigadorAval> listaParticipantesAval) {
		this.listaParticipantesAval = listaParticipantesAval;
	}

	public String getEntidadProfesorVisitante() {
		return entidadProfesorVisitante;
	}

	public void setEntidadProfesorVisitante(String entidadProfesorVisitante) {
		this.entidadProfesorVisitante = entidadProfesorVisitante;
	}

	public String getEntidadFinanciacionExterna() {
		return entidadFinanciacionExterna;
	}

	public void setEntidadFinanciacionExterna(String entidadFinanciacionExterna) {
		this.entidadFinanciacionExterna = entidadFinanciacionExterna;
	}

	public Long getValorRubroEspecie() {
		return valorRubroEspecie;
	}

	public void setValorRubroEspecie(Long valorRubroEspecie) {
		this.valorRubroEspecie = valorRubroEspecie;
	}

	public String getRubro() {
		return rubro;
	}

	public void setRubro(String rubro) {
		this.rubro = rubro;
	}

	public Long getValorRubroFrescos() {
		return valorRubroFrescos;
	}

	public void setValorRubroFrescos(Long valorRubroFrescos) {
		this.valorRubroFrescos = valorRubroFrescos;
	}

	public List<EntidadArticulo> getListaFinanciacionExternaME() {
		return listaFinanciacionExternaME;
	}

	public EntidadArticulo getEntidadEliminar() {
		return entidadEliminar;
	}

	public void setEntidadEliminar(EntidadArticulo entidadEliminar) {
		this.entidadEliminar = entidadEliminar;
	}

	public String getEntidadFortalecimiento() {
		return entidadFortalecimiento;
	}

	public void setEntidadFortalecimiento(String entidadFortalecimiento) {
		this.entidadFortalecimiento = entidadFortalecimiento;
	}

	public List<EntidadArticulo> getListaEntidadesParticipantesFortalecimiento() {
		return listaEntidadesParticipantesFortalecimiento;
	}

	public void setListaEntidadesParticipantesFortalecimiento(
			List<EntidadArticulo> listaEntidadesParticipantesFortalecimiento) {
		this.listaEntidadesParticipantesFortalecimiento = listaEntidadesParticipantesFortalecimiento;
	}

	public String getDescripcionRubro() {
		return descripcionRubro;
	}

	public void setDescripcionRubro(String descripcionRubro) {
		this.descripcionRubro = descripcionRubro;
	}

	public String getEntidadCooperante() {
		return entidadCooperante;
	}

	public void setEntidadCooperante(String entidadCooperante) {
		this.entidadCooperante = entidadCooperante;
	}

	public List<EntidadArticulo> getListaEntidadesCoorganizadorasAval() {
		return listaEntidadesCoorganizadorasAval;
	}

	public void setListaEntidadesCoorganizadorasAval(List<EntidadArticulo> listaEntidadesCoorganizadorasAval) {
		this.listaEntidadesCoorganizadorasAval = listaEntidadesCoorganizadorasAval;
	}

	public UIComponent getFinanciacionExterna() {
		return financiacionExterna;
	}

	public void setFinanciacionExterna(UIComponent financiacionExterna) {
		this.financiacionExterna = financiacionExterna;
	}

	public String getSedeSel() {
		return sedeSel;
	}

	public void setSedeSel(String sedeSel) {
		this.sedeSel = sedeSel;
	}

	public Dependencia getSedeSeleccionada() {
		return sedeSeleccionada;
	}

	public void setSedeSeleccionada(Dependencia sedeSeleccionada) {
		this.sedeSeleccionada = sedeSeleccionada;
	}

	public boolean isMostrarInfoConvocatoriaProyReg() {
		return mostrarInfoConvocatoriaProyReg;
	}

	public void setMostrarInfoConvocatoriaProyReg(boolean mostrarInfoConvocatoriaProyReg) {
		this.mostrarInfoConvocatoriaProyReg = mostrarInfoConvocatoriaProyReg;
	}

	public boolean isTieneCompromisosPendientes() {
		return tieneCompromisosPendientes;
	}

	public void setTieneCompromisosPendientes(boolean tieneCompromisosPendientes) {
		this.tieneCompromisosPendientes = tieneCompromisosPendientes;
	}

	public List<ProyectoCompromiso> getListaCompromisosProyectos() {
		return listaCompromisosProyectos;
	}

	public void setListaCompromisosProyectos(List<ProyectoCompromiso> listaCompromisosProyectos) {
		this.listaCompromisosProyectos = listaCompromisosProyectos;
	}

	public boolean isProyectoTieneAvalFormulacionODevuelto() {
		return proyectoTieneAvalFormulacionODevuelto;
	}

	public void setProyectoTieneAvalFormulacionODevuelto(boolean proyectoTieneAvalFormulacionODevuelto) {
		this.proyectoTieneAvalFormulacionODevuelto = proyectoTieneAvalFormulacionODevuelto;
	}

	public List<Aval> getListaAvalesFormulacionDevueltos() {
		return listaAvalesFormulacionDevueltos;
	}

	public void setListaAvalesFormulacionDevueltos(List<Aval> listaAvalesFormulacionDevueltos) {
		this.listaAvalesFormulacionDevueltos = listaAvalesFormulacionDevueltos;
	}

	public String getIdAvalEliminar() {
		return idAvalEliminar;
	}

	public void setIdAvalEliminar(String idAvalEliminar) {
		this.idAvalEliminar = idAvalEliminar;
	}

	public SelectItem[] getLaboratoriosFacItem() {
		return laboratoriosFacItem;
	}

	public void setLaboratoriosFacItem(SelectItem[] laboratoriosFacItem) {
		this.laboratoriosFacItem = laboratoriosFacItem;
	}

	public Long getIdLaboratorio() {
		return idLaboratorio;
	}

	public void setIdLaboratorio(Long idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	public List<LaboratorioAval> getListaLaboratoriosAval() {
		return listaLaboratoriosAval;
	}

	public void setListaLaboratoriosAval(List<LaboratorioAval> listaLaboratoriosAval) {
		this.listaLaboratoriosAval = listaLaboratoriosAval;
	}

	public void adicionarLaboratorio() {
		if (!idLaboratorio.equals(0L)) {
			LaboratorioAval labAval = new LaboratorioAval();
			labAval.setAval(aval);
			String sqlLab = "select #id e.id, #nombre e.nombre from Laboratorio e where e.id = " + idLaboratorio;
			List<Laboratorio> listaLabSel = servicioGeneral.obtenerObjetosLimitado(Laboratorio.class, sqlLab);
			Laboratorio lab = listaLabSel.get(0);
			labAval.setLaboratorio(lab);
			for (LaboratorioAval laboratorio : listaLaboratoriosAval) {
				if (laboratorio.getLaboratorio().getId().equals(idLaboratorio)) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"El laboratorio seleccionado ya se encuentra vinculado al aval",
									"El laboratorio seleccionado ya se encuentra vinculado al aval"));
					return;
				}
			}
			listaLaboratoriosAval.add(labAval);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Por favor seleccione un laboratorio", "Por favor seleccione un laboratorio"));
		}
	}

	public LaboratorioAval getLaboratorioSeleccionado() {
		return laboratorioSeleccionado;
	}

	public void setLaboratorioSeleccionado(LaboratorioAval laboratorioSeleccionado) {
		this.laboratorioSeleccionado = laboratorioSeleccionado;
	}

	public void consultarLaboratorio() {
		laboratorioVistaSeleccionado = new LaboratorioVista();
		laboratorioVistaSeleccionado.setIdLaboratorio(laboratorioSeleccionado.getLaboratorio().getId());
		laboratorioVistaSeleccionado.setNombreLaboratorio(laboratorioSeleccionado.getLaboratorio().getNombre());
		laboratorioVistaSeleccionado.setEmailLaboratorio(laboratorioSeleccionado.getLaboratorio().getEmail());
		PersonaLaboratorio pL = mUL.coordinadorLaboratorio(laboratorioSeleccionado.getLaboratorio().getId());
		laboratorioVistaSeleccionado
				.setCoordiadorLaboratorio(pL.getPersona().getNombre1() + " " + pL.getPersona().getNombre2() + " "
						+ pL.getPersona().getApellido1() + " " + pL.getPersona().getApellido2());
	}

	public LaboratorioVista getLaboratorioVistaSeleccionado() {
		return laboratorioVistaSeleccionado;
	}

	public void setLaboratorioVistaSeleccionado(LaboratorioVista laboratorioVistaSeleccionado) {
		this.laboratorioVistaSeleccionado = laboratorioVistaSeleccionado;
	}

	public void eliminarLaboratorio() {
		listaLaboratoriosAval.remove(laboratorioSeleccionado);
		listaLaboratoriosAvalBorrados.add(laboratorioSeleccionado);
	}

	public boolean isFormatoExterno() {
		return formatoExterno;
	}

	public void setFormatoExterno(boolean formatoExterno) {
		this.formatoExterno = formatoExterno;
	}

	public String getIdTipoArchivo() {
		return idTipoArchivo;
	}

	public void setIdTipoArchivo(String idTipoArchivo) {
		this.idTipoArchivo = idTipoArchivo;
	}

	public boolean isTieneDependenciaSedePresencia() {
		return tieneDependenciaSedePresencia;
	}

	public void setTieneDependenciaSedePresencia(boolean tieneDependenciaSedePresencia) {
		this.tieneDependenciaSedePresencia = tieneDependenciaSedePresencia;
	}

	public Dependencia getDependenciaPresenciaNacional() {
		return dependenciaPresenciaNacional;
	}

	public void setDependenciaPresenciaNacional(Dependencia dependenciaPresenciaNacional) {
		this.dependenciaPresenciaNacional = dependenciaPresenciaNacional;
	}

	public boolean isRemitirSedePresencia() {
		return remitirSedePresencia;
	}

	public void setRemitirSedePresencia(boolean remitirSedePresencia) {
		this.remitirSedePresencia = remitirSedePresencia;
	}

	public Grupo getGrupoEliminar() {
		return grupoEliminar;
	}

	public void setGrupoEliminar(Grupo grupoEliminar) {
		this.grupoEliminar = grupoEliminar;
	}

	public String getSedeFiltroGrupo() {
		return sedeFiltroGrupo;
	}

	public void setSedeFiltroGrupo(String sedeFiltroGrupo) {
		this.sedeFiltroGrupo = sedeFiltroGrupo;
	}

	public void cargarSedesGrupos() {
		List<Sede> listaSede = servicioGeneral.obtenerObjetos(Sede.class,
				"from Sede s where s.id not in ('" + Sede.NIVEL_NACIONAL + "') order by s.id");
		sedeItem = new SelectItem[listaSede.size()];
		for (int i = 0; i < listaSede.size(); i++) {
			Sede sede = listaSede.get(i);
			sedeItem[i] = new SelectItem(sede.getId(), sede.getNombre());
		}
	}

	public SelectItem[] getSedeItem() {
		return sedeItem;
	}

	public void setSedeItem(SelectItem[] sedeItem) {
		this.sedeItem = sedeItem;
	}

	public void actualizarGrupos() {
		List<Grupo> listaGrupos = servicioGeneral.obtenerObjetos(Grupo.class,
				"from Grupo s where s.sede.id='" + getSedeFiltroGrupo() + "' and s.estadoGrupo.id='A' order by s.id");
		gruposItem = new SelectItem[listaGrupos.size()];
		for (int i = 0; i < listaGrupos.size(); i++) {
			Grupo grupo = listaGrupos.get(i);
			gruposItem[i] = new SelectItem(grupo.getId(), grupo.getNombre());
		}
	}

	public SelectItem[] getGruposItem() {
		return gruposItem;
	}

	public void setGruposItem(SelectItem[] gruposItem) {
		this.gruposItem = gruposItem;
	}

	public void adicionarGrupo() {
		if (StringUtils.isNotEmpty(getGrupoSeleccionado())) {
			boolean existe = false;
			for (int i = 0; i < fichaSeleccionada.getListaGrupos().size(); i++) {
				Grupo s = (Grupo) fichaSeleccionada.getListaGrupos().get(i);
				if (s.getId().toString().equals(getGrupoSeleccionado())) {
					existe = true;
					mensajeError("El grupo ya se encuentra vinculado.");
					break;
				}
			}
			if (!existe) {
				Grupo g = servicioGeneral
						.obtenerObjetos(Grupo.class, "from Grupo s where s.id=" + getGrupoSeleccionado()).get(0);
				fichaSeleccionada.adicionarGrupo(g);
				huboCambiosGrupos = true;
			}
		} else {
			mensajeError("Por favor, indicar un grupo.");
		}
	}

	public void eliminarGrupo() {
		fichaSeleccionada.borrarGrupo(grupoEliminar);
		huboCambiosGrupos = true;
	}

	public String getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	public void setGrupoSeleccionado(String grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}

	public boolean isHuboCambiosGrupos() {
		return huboCambiosGrupos;
	}

	public void setHuboCambiosGrupos(boolean huboCambiosGrupos) {
		this.huboCambiosGrupos = huboCambiosGrupos;
	}

	public void agregarSubTipo() {
		DominioDetalle subtipo = servicioGeneral.obtenerDominioDetalleUnico(Dominio.ID_DOMINIO_AVAL_SUBTIPO,
				subTipoAvalEspecificacion);

		if (subtipo != null) {
			avalSubTipoEspecificacion = new AvalSubTipos();
			if (aval.getListaSubTiposEspecificacion().isEmpty()) {
				avalSubTipoEspecificacion.setSubTipo(subtipo);
				aval.adicionarSubTipoEspecificacion(avalSubTipoEspecificacion);
			} else {
				Boolean repetido = false;
				for (AvalSubTipos a : aval.getListaSubTiposEspecificacion()) {
					if (a.getSubTipo().getIdentificador().getTipo().equals(subTipoAvalEspecificacion)) {
						repetido = true;
						break;
					}
				}
				if (repetido) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Tipo de especificación:", "Ya fue agregado el tipo de especificación al aval"));
				} else {
					avalSubTipoEspecificacion.setSubTipo(subtipo);
					aval.adicionarSubTipoEspecificacion(avalSubTipoEspecificacion);
				}
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Tipo de especificación: ", "Debe seleccionar un tipo de especificación para agregar"));
		}
	}

	public void eliminarSubTipo() {
		aval.borrarSubTipoEspecificacion(avalSubTipoSeleccionado);
		if (avalSubTipoSeleccionado.getId() != null) {
			servicioGeneral.eliminarObjeto(avalSubTipoSeleccionado);
		}
	}

	public AvalSubTipos getAvalSubTipoEspecificacion() {
		return avalSubTipoEspecificacion;
	}

	public void setAvalSubTipoEspecificacion(AvalSubTipos avalSubTipoEspecificacion) {
		this.avalSubTipoEspecificacion = avalSubTipoEspecificacion;
	}

	public SelectItem[] getListaSubTipoEspecificacionItem() {
		return listaSubTipoEspecificacionItem;
	}

	public void setListaSubTipoEspecificacionItem(SelectItem[] listaSubTipoEspecificacionItem) {
		this.listaSubTipoEspecificacionItem = listaSubTipoEspecificacionItem;
	}

	public String getSubTipoAvalEspecificacion() {
		return subTipoAvalEspecificacion;
	}

	public void setSubTipoAvalEspecificacion(String subTipoAvalEspecificacion) {
		this.subTipoAvalEspecificacion = subTipoAvalEspecificacion;
	}

	public AvalSubTipos getAvalSubTipoSeleccionado() {
		return avalSubTipoSeleccionado;
	}

	public void setAvalSubTipoSeleccionado(AvalSubTipos avalSubTipoSeleccionado) {
		this.avalSubTipoSeleccionado = avalSubTipoSeleccionado;
	}

	private void cargarListaSubTiposEspecificacion() {
		try {
			listaSubTipoEspecificacionItem = crearListaItemDominioDetalle(Dominio.DOMINIO_AVAL_SUBTIPO);
			subTipoAvalEspecificacion = listaSubTipoEspecificacionItem[0].getValue().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Date getFechaResultadosRegalias() {
		return fechaResultadosRegalias;
	}

	public void setFechaResultadosRegalias(Date fechaResultadosRegalias) {
		this.fechaResultadosRegalias = fechaResultadosRegalias;
	}

}