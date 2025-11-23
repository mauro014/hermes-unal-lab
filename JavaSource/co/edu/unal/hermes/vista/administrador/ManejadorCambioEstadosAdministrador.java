package co.edu.unal.hermes.vista.administrador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.HistoricoEstadoAval;
import co.edu.unal.hermes.modelo.HistoricoEstadoMovilidad;
import co.edu.unal.hermes.modelo.HistoricoEstadoProyecto;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadInvestigador;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.vista.ManejadorBase;

/**
 * The Class ManejadorCambioEstadosAdministrador.
 *
 * @author Martha Liliana Correa O.
 * @date 09/11/2015
 */

public class ManejadorCambioEstadosAdministrador extends ManejadorBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3221433167798676144L;

	/** The Constant VISITANTES. */
	private static final int VISITANTES = 1;

	/** The Constant DOCENTES. */
	private static final int DOCENTES = 2;

	/** The Constant ESTUDIANTES. */
	private static final int ESTUDIANTES = 3;

	/** The Constant DEVOLVER_FACULTAD. */
	private static final int DEVOLVER_FACULTAD = 1;

	/** The Constant DEVOLVER_SEDE. */
	private static final int DEVOLVER_SEDE = 2;

	/** The estados item. */
	private SelectItem[] estadosItem;

	/** The justificacion. */
	private String justificacion;

	/** The codigo proyecto. */
	private String codigoProyecto;

	/** The estado seleccionado. */
	private String estadoSeleccionado;

	/** The proyecto actual. */
	private Proyecto proyectoActual;

	/** The historico estados proyecto. */
	private List<HistoricoEstadoProyecto> historicoEstadosProyecto;

	/** The codigo aval. */
	private String codigoAval;

	/** The aval actual. */
	private Aval avalActual;

	/** The historico estados aval. */
	private List<HistoricoEstadoAval> listaHistoricoEstadoAval;

	/** The historico estados aval. */
	private List<HistoricoEstadoMovilidad> listaHistoricoEstadoMovilidad;

	/** The historico seleccionado. */
	private HistoricoEstadoAval historicoAvalSeleccionado;

	/** The justificacionAval. */
	private String justificacionAval;

	/** The justificacionMovilidad. */
	private String justificacionMovilidad;

	/** The estados item. */
	private List<SelectItem> estadosAvalItem = new ArrayList<SelectItem>();

	/** The estados item. */
	private List<SelectItem> estadosMovilidadItem = new ArrayList<SelectItem>();

	/** The nuevo estado de aval. */
	private String estadoAvalSeleccionado;

	/** The nuevo estado de movilidad. */
	private String estadoMovilidadSeleccionado;

	/** The codigo aval. */
	private String codigoMovilidad;

	/** The estados item. */
	private List<SelectItem> tipoMovilidad;

	/** The tipo movilidad id. */
	private int tipoMovilidadId;

	/** The movilidad investigador encontrada. */
	private MovilidadInvestigador movilidadInvestigadorEncontrada;

	/**
	 * Instantiates a new manejador cambio estados administrador.
	 */
	public ManejadorCambioEstadosAdministrador() {

		cargarEstados();
		cargarTipoMovilidad();

		movilidadInvestigadorEncontrada = null;

	}

	/**
	 * Cargar estados.
	 */
	private void cargarEstados() {
		List<EstadoProyecto> listaEstados = servicioGeneral.obtenerObjetos(EstadoProyecto.class,
				"from EstadoProyecto e where e.id in ('CN','F','S','A','AP','I','P','R','E','N','B','PF','OCAD') order by e.numero asc");
		if (listaEstados != null) {
			estadosItem = new SelectItem[listaEstados.size()];
			for (int i = 0; i < listaEstados.size(); i++) {
				EstadoProyecto estado = (EstadoProyecto) listaEstados.get(i);
				estadosItem[i] = new SelectItem(estado.getId(), estado.getNombre());
			}
		}
	}

	/**
	 * Cargar tipo movilidad.
	 */
	private void cargarTipoMovilidad() {
		tipoMovilidad = new ArrayList<SelectItem>();
		tipoMovilidad.add(new SelectItem(VISITANTES, "Visitantes"));
		tipoMovilidad.add(new SelectItem(DOCENTES, "Docentes"));
		tipoMovilidad.add(new SelectItem(ESTUDIANTES, "Estudiantes"));
	}

	/**
	 * Buscar proyecto.
	 */
	public void buscarProyecto() {

		Long id = 0L;
		proyectoActual = null;

		try {
			id = Long.parseLong(codigoProyecto.trim());
		} catch (NumberFormatException nfe) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe ingresar un código de proyecto valido.", "");
			mostrarMensaje(message, null);
			return;
		}
		if (id > 0) {
			buscarProyectoPorCodigo(id);
		}

	}

	/**
	 * Buscar proyecto.
	 *
	 * @param codigo
	 *            the codigo
	 */
	public void buscarProyectoPorCodigo(Long codigo) {

		// Si se carga un numero correcto se busca
		List<Proyecto> proyectos = servicioGeneral.obtenerObjetosLimitado(Proyecto.class,
				"select #id p.id, #nombre p.nombre, #estadoProyecto p.estadoProyecto, #permitirModificacion p.permitirModificacion"
						+ " from Proyecto p where p.id = '" + codigo + "'");
		if (proyectos != null && proyectos.size() > 0) {
			proyectoActual = proyectos.get(0);
			estadoSeleccionado = proyectoActual.getEstadoProyecto().getId();
			historicoEstadosProyecto = cargarHistoricos(proyectoActual);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No se ha encontrado un proyecto con el código ingresado.", "");
			mostrarMensaje(message, null);
		}

	}

	/**
	 * Cargar historicos.
	 *
	 * @param proyecto
	 *            the proyecto
	 * @return the list
	 */
	private List<HistoricoEstadoProyecto> cargarHistoricos(Proyecto proyecto) {
		List<HistoricoEstadoProyecto> historicoEstadoProyectos = null;
		if (proyecto != null && proyecto.getId() != null) {
			historicoEstadoProyectos = servicioGeneral.obtenerObjetos(HistoricoEstadoProyecto.class,
					"from HistoricoEstadoProyecto h where h.proyecto.id = '" + proyecto.getId() + "' order by h.fecha");
		}
		return historicoEstadoProyectos;
	}

	/**
	 * Guardar nuevo estado.
	 */
	public void guardarNuevoEstado() {
		if (guardarEstados(proyectoActual, estadoSeleccionado)) {
			buscarProyectoPorCodigo(proyectoActual.getId());
		}
	}

	/**
	 * Guardar estados proyecto.
	 *
	 * @param proyecto
	 *            the proyecto
	 * @param estado
	 *            the estado
	 * @return true, if successful
	 */
	private boolean guardarEstados(Proyecto proyecto, String estado) {
		if (justificacion != null && justificacion.trim().length() > 0) {

			// Se crea script para cambio de estado
			String sql = "update HER_PROYECTO set EPR_ID = '" + estado + "' where pry_id = '" + proyecto.getId() + "'";

			// Se crea historico
			HistoricoEstadoProyecto hepry = new HistoricoEstadoProyecto();
			EstadoProyecto ep = new EstadoProyecto();
			ep.setId(estadoSeleccionado);
			hepry.setEstadoProyecto(ep);
			hepry.setProyecto(proyectoActual);
			hepry.setFecha(new Date());
			hepry.setResponsable(cargarPersonaActual());
			hepry.setJustificacion(justificacion);

			try {
				servicioGeneral.ejecutarSentencia(sql);
				servicioGeneral.guardarObjeto(hepry);
				justificacion = "";
				return true;
			} catch (Exception e) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Hubo un error al guardar el nuevo estado", "");
				mostrarMensaje(message, null);
				e.printStackTrace();
				return false;
			}
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar una justificación.",
					"");
			mostrarMensaje(message, null);
			return false;
		}
	}
	
	public void guardarHabilitacionProyecto() {
		try {
			String sql = "update HER_PROYECTO set PRY_PERMITIR_MODIFICACION = '";
			if (proyectoActual.getPermitirModificacion().equals("S")) {
				sql += "N' ";
			} else if (proyectoActual.getPermitirModificacion().equals("N")) {
				sql += "S' ";
			}
			sql += "where pry_id = '" + proyectoActual.getId() + "'";
			servicioGeneral.ejecutarSentencia(sql);
			buscarProyectoPorCodigo(proyectoActual.getId());
			FacesMessage message = null;
			if (proyectoActual.getPermitirModificacion().equals("S")) {
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha habilitado el proyecto correctamente.",
						"");
			} else if (proyectoActual.getPermitirModificacion().equals("N")) {
				message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Se ha bloqueado la edición del proyecto correctamente.", "");
			}
			mostrarMensaje(message, null);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Hubo un error al realizar el procedimiento de edición del proyecto.", "");
			mostrarMensaje(message, null);
			e.printStackTrace();
		}
	}
	
	public void guardarFechasMovilidad() {
		try {
			servicioGeneral.guardarObjeto(movilidadInvestigadorEncontrada);
			buscarMovilidad();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Se ha modificado la movilidad correctamente.", "");
			mostrarMensaje(message, null);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Hubo un error al modificar la movilidad.", "");
			mostrarMensaje(message, null);
			e.printStackTrace();
		}
	}

	/**	
	 * Buscar movilidad.
	 */
	public void buscarMovilidad() {

		Long id = 0L;
		movilidadInvestigadorEncontrada = null;
		estadosMovilidadItem.clear();

		try {
			id = Long.parseLong(codigoMovilidad.trim());
		} catch (NumberFormatException nfe) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe ingresar un código de movilidad válido.", "");
			mostrarMensaje(message, null);
			return;
		}
		if (id > 0) {
			if (tipoMovilidadId == VISITANTES) {
				movilidadInvestigadorEncontrada = (MovilidadInvestigador) obtenerMovilidad(
						MovilidadVisitanteExterior.class, id.toString());
			} else if (tipoMovilidadId == DOCENTES) {
				movilidadInvestigadorEncontrada = (MovilidadInvestigador) obtenerMovilidad(
						MovilidadDocentesExterior.class, id.toString());
			} else if (tipoMovilidadId == ESTUDIANTES) {
				movilidadInvestigadorEncontrada = (MovilidadInvestigador) obtenerMovilidad(
						MovilidadEstudiantesPosgrado.class, id.toString());
			}
		}

		if (movilidadInvestigadorEncontrada == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Movilidad no encontrada", "");
			mostrarMensaje(message, null);
		} else {
			if (StringUtils.isNotBlank(movilidadInvestigadorEncontrada.getAceptacion())
					|| StringUtils.isNotBlank(movilidadInvestigadorEncontrada.getAprobacion())) {
				estadosMovilidadItem.add(new SelectItem("", "Seleccione una opción"));
			}
			if (StringUtils.isNotBlank(movilidadInvestigadorEncontrada.getAceptacion())) {
				estadosMovilidadItem.add(new SelectItem(DEVOLVER_FACULTAD, "Devolver para revisión en facultad"));
			}
			if (StringUtils.isNotBlank(movilidadInvestigadorEncontrada.getAprobacion())) {
				estadosMovilidadItem.add(new SelectItem(DEVOLVER_SEDE, "Devolver para revisión en sede"));
			}

			listaHistoricoEstadoMovilidad = servicioMovilidad
					.getHistoricoEstadoMovilidad(movilidadInvestigadorEncontrada.getId());
		}
	}

	/**
	 * Buscar aval.
	 */
	public void buscarAval() {

		Long id = 0L;
		avalActual = null;

		try {
			id = Long.parseLong(codigoAval.trim());
		} catch (NumberFormatException nfe) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe ingresar un código de aval válido.", "");
			mostrarMensaje(message, null);
			return;
		}
		if (id > 0) {
			buscarAvalPorCodigo(id);
		}

	}

	/**
	 * Buscar aval a partir del codigo ingresado.
	 *
	 * @param codigo
	 *            the codigo
	 */
	public void buscarAvalPorCodigo(Long codigo) {

		List<Aval> aval = servicioGeneral.obtenerAval(codigo.toString());
		if (!esListaVacia(aval)) {
			avalActual = aval.get(0);
			listaHistoricoEstadoAval = servicioProyecto.obtenerHistoricosEstadoAval(avalActual);
			cargarEstadosAval(avalActual);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No se ha encontrado un aval con el código ingresado.", "");
			mostrarMensaje(message, null);
		}
	}

	/**
	 * Cargar estados aval.
	 *
	 * @param aval
	 *            the aval
	 */
	public void cargarEstadosAval(Aval aval) {
		estadosAvalItem.clear();
		estadosAvalItem.add(new SelectItem("", ""));
		estadosAvalItem.add(new SelectItem("0", "Anulado/borrado"));
		estadosAvalItem.add(new SelectItem("1", "Enviado"));
		estadosAvalItem.add(new SelectItem("12", "Enviado a Facultad"));
		estadosAvalItem.add(new SelectItem("13", "Enviado a Sede"));
		estadosAvalItem.add(new SelectItem("2", "Devuelto para correcciones"));

		if (!aval.isEsJornadaDocente()) {
			estadosAvalItem.add(new SelectItem("5", "Aprobado en Facultad/Instituto"));
			estadosAvalItem.add(new SelectItem("6", "No aprobado en Facultad/Instituto"));
			estadosAvalItem.add(new SelectItem("7", "Aprobado en Dirección de Investigación"));
			estadosAvalItem.add(new SelectItem("8", "No aprobado en Dirección de Investigación"));
		}

		if (aval.isEsJornadaDocente()) {
			estadosAvalItem.add(new SelectItem("3", "Aprobado en UAB"));
			estadosAvalItem.add(new SelectItem("4", "No aprobado en UAB"));
		} else if (((aval.isEsRegalias() || aval.isEsPaedRegalias()) && aval.isEsInterSedes())
				|| aval.isEsGrupoInvestigacion() || aval.isEsInvestigadorIndependiente()) {
			estadosAvalItem.add(new SelectItem("9", "Aprobado en Vicerrectoría de Investigación"));
			estadosAvalItem.add(new SelectItem("10", "No aprobado Vicerrectoría de Investigación"));
		}

	}

	/**
	 * Guardar nuevo estado aval.
	 */
	public void guardarNuevoEstadoAval() {
		if (esCadenaVacia(estadoAvalSeleccionado)) {
			mensajeError("Debe seleccionar un nuevo estado para el aval.");
			return;
		}
		justificacionAval = controlTamanoCadena(justificacionAval, 1000);
		if (!esCadenaVacia(justificacionAval)) {

			switch (Integer.parseInt(estadoAvalSeleccionado)) {
			case 0:
				avalActual.setAviEstado(Aval.BORRADO);
				break;
			case 1:
				avalActual.setAviEstado(Aval.ENVIADO);
				avalActual.setAviAvalUab(null);
				avalActual.setAviAvalfacultad(null);
				avalActual.setAviAvaldireccion(null);
				avalActual.setAvalVice(null);
				avalActual.setAviFechaAvalUab(null);
				avalActual.setAviFechaAvalFac(null);
				avalActual.setAviFechaAvalCoor(null);
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAvalDRE(null);
				avalActual.setAvalRectoria(null);
				avalActual.setFechaAvalDRE(null);
				avalActual.setFechaAvalRectoria(null);
				avalActual.setFechaUltimoEnvio(getToday());
				break;
			case 2:
				avalActual.setAviEstado(Aval.DEVUELTO);
				avalActual.setAviAvalUab(null);
				avalActual.setAviAvalfacultad(null);
				avalActual.setAviAvaldireccion(null);
				avalActual.setAvalVice(null);
				avalActual.setAviFechaAvalUab(null);
				avalActual.setAviFechaAvalFac(null);
				avalActual.setAviFechaAvalCoor(null);
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAvalDRE(null);
				avalActual.setAvalRectoria(null);
				avalActual.setFechaAvalDRE(null);
				avalActual.setFechaAvalRectoria(null);
				break;
			case 3:
				avalActual.setAviEstado(Aval.REVISADO_UAB);
				avalActual.setAviAvalUab(Aval.APROBADO);
				avalActual.setAviAvalfacultad(null);
				avalActual.setAviAvaldireccion(null);
				avalActual.setAvalVice(null);
				avalActual.setAviFechaAvalFac(null);
				avalActual.setAviFechaAvalCoor(null);
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAviFechaAvalUab(getToday());
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAvalDRE(null);
				avalActual.setAvalRectoria(null);
				avalActual.setFechaAvalDRE(null);
				avalActual.setFechaAvalRectoria(null);
				break;
			case 4:
				avalActual.setAviEstado(Aval.REVISADO_UAB);
				avalActual.setAviAvalUab(Aval.NEGADO);
				avalActual.setAviAvalfacultad(null);
				avalActual.setAviAvaldireccion(null);
				avalActual.setAvalVice(null);
				avalActual.setAviFechaAvalFac(null);
				avalActual.setAviFechaAvalCoor(null);
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAviFechaAvalUab(getToday());
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAvalDRE(null);
				avalActual.setAvalRectoria(null);
				avalActual.setFechaAvalDRE(null);
				avalActual.setFechaAvalRectoria(null);
				break;
			case 5:
				avalActual.setAviEstado(Aval.REVISADO_FACULTAD);
				avalActual.setAviAvalfacultad(Aval.APROBADO);
				avalActual.setAviAvaldireccion(null);
				avalActual.setAvalVice(null);
				avalActual.setAviFechaAvalFac(getToday());
				avalActual.setAviFechaAvalUab(null);
				avalActual.setAviFechaAvalCoor(null);
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAvalDRE(null);
				avalActual.setAvalRectoria(null);
				avalActual.setFechaAvalDRE(null);
				avalActual.setFechaAvalRectoria(null);
				break;
			case 6:
				avalActual.setAviEstado(Aval.REVISADO_FACULTAD);
				avalActual.setAviAvalfacultad(Aval.NEGADO);
				avalActual.setAviAvaldireccion(null);
				avalActual.setAvalVice(null);
				avalActual.setAviFechaAvalFac(getToday());
				avalActual.setAviFechaAvalCoor(null);
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAvalDRE(null);
				avalActual.setAvalRectoria(null);
				avalActual.setFechaAvalDRE(null);
				avalActual.setFechaAvalRectoria(null);
				break;
			case 7:
				avalActual.setAviEstado(Aval.REVISADO_DIRECCION);
				avalActual.setAviAvalfacultad(Aval.APROBADO);
				avalActual.setAviAvaldireccion(Aval.APROBADO);
				avalActual.setAvalVice(null);
				avalActual.setAviFechaAvalCoor(getToday());
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAvalDRE(null);
				avalActual.setAvalRectoria(null);
				avalActual.setFechaAvalDRE(null);
				avalActual.setFechaAvalRectoria(null);
				break;
			case 8:
				avalActual.setAviEstado(Aval.REVISADO_DIRECCION);
				avalActual.setAviAvalfacultad(Aval.APROBADO);
				avalActual.setAviAvaldireccion(Aval.NEGADO);
				avalActual.setAvalVice(null);
				avalActual.setAviFechaAvalCoor(getToday());
				avalActual.setAviFechaAvalVice(null);
				break;
			case 9:
				avalActual.setAviEstado(Aval.REVISADO_VICERRECTORIA);
				avalActual.setAviAvalfacultad(Aval.APROBADO);
				avalActual.setAviAvaldireccion(Aval.APROBADO);
				avalActual.setAvalVice(Aval.APROBADO);
				avalActual.setAviFechaAvalVice(getToday());
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAvalDRE(null);
				avalActual.setAvalRectoria(null);
				avalActual.setFechaAvalDRE(null);
				avalActual.setFechaAvalRectoria(null);
				break;
			case 10:
				avalActual.setAviEstado(Aval.REVISADO_VICERRECTORIA);
				avalActual.setAviAvalfacultad(Aval.APROBADO);
				avalActual.setAviAvaldireccion(Aval.APROBADO);
				avalActual.setAvalVice(Aval.NEGADO);
				avalActual.setAviFechaAvalVice(getToday());
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAvalDRE(null);
				avalActual.setAvalRectoria(null);
				avalActual.setFechaAvalDRE(null);
				avalActual.setFechaAvalRectoria(null);
				break;
			case 11:
				avalActual.setAviEstado(Aval.REVISADO_DRE);
				avalActual.setAviAvalfacultad(Aval.APROBADO);
				avalActual.setAviAvaldireccion(Aval.APROBADO);
				avalActual.setAvalVice(null);
				avalActual.setAviFechaAvalVice(getToday());
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAvalDRE(Aval.APROBADO);
				avalActual.setAvalRectoria(null);
				avalActual.setFechaAvalDRE(getToday());
				avalActual.setFechaAvalRectoria(null);
				break;
			case 12:
				avalActual.setAviEstado(Aval.ENVIADO_FACULTAD);
				avalActual.setAviAvalUab(null);
				avalActual.setAviAvalfacultad(null);
				avalActual.setAviAvaldireccion(null);
				avalActual.setAvalVice(null);
				avalActual.setAviFechaAvalUab(null);
				avalActual.setAviFechaAvalFac(null);
				avalActual.setAviFechaAvalCoor(null);
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAvalDRE(null);
				avalActual.setAvalRectoria(null);
				avalActual.setFechaAvalDRE(null);
				avalActual.setFechaAvalRectoria(null);
				avalActual.setFechaUltimoEnvio(getToday());
				break;
			case 13:
				avalActual.setAviEstado(Aval.ENVIADO_SEDE);
				avalActual.setAviAvalUab(null);
				avalActual.setAviAvalfacultad(null);
				avalActual.setAviAvaldireccion(null);
				avalActual.setAvalVice(null);
				avalActual.setAviFechaAvalUab(null);
				avalActual.setAviFechaAvalFac(null);
				avalActual.setAviFechaAvalCoor(null);
				avalActual.setAviFechaAvalVice(null);
				avalActual.setAvalDRE(null);
				avalActual.setAvalRectoria(null);
				avalActual.setFechaAvalDRE(null);
				avalActual.setFechaAvalRectoria(null);
				avalActual.setFechaUltimoEnvio(getToday());
				break;
			}
			
			servicioGeneral.guardarObjeto(avalActual);
			crearHistoricoEstadoAval(avalActual, cargarPersonaActual(), justificacionAval);
			reiniciarVariablesAval();
			buscarAvalPorCodigo(avalActual.getAviId());

		} else {
			mensajeError("Debe ingresar una justificación para el cambio de estado del aval.");
			return;
		}
	}

	/**
	 * Guardar nuevo estado aval.
	 */
	public void guardarNuevoEstadoMovilidad() {
		if (StringUtils.isBlank(estadoMovilidadSeleccionado)) {
			mensajeError("Debe seleccionar el cambio a realizar.");
			return;
		}
		justificacionMovilidad = controlTamanoCadena(justificacionMovilidad, 1000);
		if (!esCadenaVacia(justificacionMovilidad)) {

			switch (Integer.parseInt(estadoMovilidadSeleccionado)) {
			case DEVOLVER_FACULTAD:

				movilidadInvestigadorEncontrada.setAceptacion(null);
				movilidadInvestigadorEncontrada.setIdPersonaAprobacion(null);
				movilidadInvestigadorEncontrada.setTipoIdPersonaAprobacion(null);
				movilidadInvestigadorEncontrada.setComentariosFac(null);

			case DEVOLVER_SEDE:
				movilidadInvestigadorEncontrada.setAprobacion(null);
				movilidadInvestigadorEncontrada.setIdPersonaAprobacionSede(null);
				movilidadInvestigadorEncontrada.setTipoIdPersonaAprobacionSede(null);
				movilidadInvestigadorEncontrada.setComentariosDir(null);
				break;
			}
			servicioGeneral.guardarObjeto(movilidadInvestigadorEncontrada);
			crearHistoricoEstadoMovilidadInvestigador(movilidadInvestigadorEncontrada, justificacionMovilidad);
			reiniciarVariablesMovilidad();
			buscarMovilidad();

		} else {
			mensajeError("Debe ingresar una justificación para el cambio de estado de la movilidad.");
			return;
		}
	}

	/**
	 * Reiniciar variables aval.
	 */
	public void reiniciarVariablesAval() {
		estadoAvalSeleccionado = "";
		justificacionAval = "";
	}

	/**
	 * Reiniciar variables aval.
	 */
	public void reiniciarVariablesMovilidad() {
		estadoMovilidadSeleccionado = "";
		justificacionMovilidad = "";
	}

	/**
	 * Gets the jsutificacion.
	 *
	 * @return the jsutificacion
	 */
	public String getJustificacion() {
		return justificacion;
	}

	/**
	 * Sets the jsutificacion.
	 *
	 * @param jsutificacion
	 *            the new jsutificacion
	 */
	public void setJustificacion(String jsutificacion) {
		this.justificacion = jsutificacion;
	}

	/**
	 * Gets the historico estados proyecto.
	 *
	 * @return the historico estados proyecto
	 */
	public List<HistoricoEstadoProyecto> getHistoricoEstadosProyecto() {
		return historicoEstadosProyecto;
	}

	/**
	 * Gets the codigo proyecto.
	 *
	 * @return the codigoProyecto
	 */
	public String getCodigoProyecto() {
		return codigoProyecto;
	}

	/**
	 * Sets the codigo proyecto.
	 *
	 * @param codigoProyecto
	 *            the codigoProyecto to set
	 */
	public void setCodigoProyecto(String codigoProyecto) {
		this.codigoProyecto = codigoProyecto;
	}

	/**
	 * Gets the proyecto actual.
	 *
	 * @return the proyecto actual
	 */
	public Proyecto getProyectoActual() {
		return proyectoActual;
	}

	/**
	 * Gets the estados item.
	 *
	 * @return the estados item
	 */
	public SelectItem[] getEstadosItem() {
		return estadosItem;
	}

	/**
	 * Gets the estado seleccionado.
	 *
	 * @return the estado seleccionado
	 */
	public String getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	/**
	 * Sets the estado seleccionado.
	 *
	 * @param estadoSeleccionado
	 *            the new estado seleccionado
	 */
	public void setEstadoSeleccionado(String estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	/**
	 * Mostrar mensaje.
	 *
	 * @param msg
	 *            the msg
	 * @param component
	 *            the component
	 */
	protected void mostrarMensaje(FacesMessage msg, UIComponent component) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (component == null) {
			context.addMessage(null, msg);
		} else {
			context.addMessage(component.getClientId(context), msg);
		}
	}

	/**
	 * Gets the codigo aval.
	 *
	 * @return the codigo aval
	 */
	public String getCodigoAval() {
		return codigoAval;
	}

	/**
	 * Sets the codigo aval.
	 *
	 * @param codigoAval
	 *            the new codigo aval
	 */
	public void setCodigoAval(String codigoAval) {
		this.codigoAval = codigoAval;
	}

	/**
	 * Gets the aval actual.
	 *
	 * @return the aval actual
	 */
	public Aval getAvalActual() {
		return avalActual;
	}

	/**
	 * Sets the aval actual.
	 *
	 * @param avalActual
	 *            the new aval actual
	 */
	public void setAvalActual(Aval avalActual) {
		this.avalActual = avalActual;
	}

	/**
	 * Gets the lista historico estado aval.
	 *
	 * @return the lista historico estado aval
	 */
	public List<HistoricoEstadoAval> getListaHistoricoEstadoAval() {
		return listaHistoricoEstadoAval;
	}

	/**
	 * Sets the lista historico estado aval.
	 *
	 * @param listaHistoricoEstadoAval
	 *            the new lista historico estado aval
	 */
	public void setListaHistoricoEstadoAval(List<HistoricoEstadoAval> listaHistoricoEstadoAval) {
		this.listaHistoricoEstadoAval = listaHistoricoEstadoAval;
	}

	/**
	 * Gets the historico aval seleccionado.
	 *
	 * @return the historico aval seleccionado
	 */
	public HistoricoEstadoAval getHistoricoAvalSeleccionado() {
		return historicoAvalSeleccionado;
	}

	/**
	 * Sets the historico aval seleccionado.
	 *
	 * @param historicoAvalSeleccionado
	 *            the new historico aval seleccionado
	 */
	public void setHistoricoAvalSeleccionado(HistoricoEstadoAval historicoAvalSeleccionado) {
		this.historicoAvalSeleccionado = historicoAvalSeleccionado;
	}

	/**
	 * Gets the justificacion aval.
	 *
	 * @return the justificacion aval
	 */
	public String getJustificacionAval() {
		return justificacionAval;
	}

	/**
	 * Sets the justificacion aval.
	 *
	 * @param justificacionAval
	 *            the new justificacion aval
	 */
	public void setJustificacionAval(String justificacionAval) {
		this.justificacionAval = justificacionAval;
	}

	/**
	 * Gets the estados aval item.
	 *
	 * @return the estados aval item
	 */
	public List<SelectItem> getEstadosAvalItem() {
		return estadosAvalItem;
	}

	/**
	 * Sets the estados aval item.
	 *
	 * @param estadosAvalItem
	 *            the new estados aval item
	 */
	public void setEstadosAvalItem(List<SelectItem> estadosAvalItem) {
		this.estadosAvalItem = estadosAvalItem;
	}

	/**
	 * Gets the estado aval seleccionado.
	 *
	 * @return the estado aval seleccionado
	 */
	public String getEstadoAvalSeleccionado() {
		return estadoAvalSeleccionado;
	}

	/**
	 * Sets the estado aval seleccionado.
	 *
	 * @param estadoAvalSeleccionado
	 *            the new estado aval seleccionado
	 */
	public void setEstadoAvalSeleccionado(String estadoAvalSeleccionado) {
		this.estadoAvalSeleccionado = estadoAvalSeleccionado;
	}

	/**
	 * Gets the codigo movilidad.
	 *
	 * @return the codigoMovilidad
	 */
	public String getCodigoMovilidad() {
		return codigoMovilidad;
	}

	/**
	 * Sets the codigo movilidad.
	 *
	 * @param codigoMovilidad
	 *            the codigoMovilidad to set
	 */
	public void setCodigoMovilidad(String codigoMovilidad) {
		this.codigoMovilidad = codigoMovilidad;
	}

	/**
	 * Gets the tipo movilidad.
	 *
	 * @return the tipoMovilidad
	 */
	public List<SelectItem> getTipoMovilidad() {
		return tipoMovilidad;
	}

	/**
	 * Gets the tipo movilidad id.
	 *
	 * @return the tipoMovilidadId
	 */
	public int getTipoMovilidadId() {
		return tipoMovilidadId;
	}

	/**
	 * Sets the tipo movilidad id.
	 *
	 * @param tipoMovilidadId
	 *            the tipoMovilidadId to set
	 */
	public void setTipoMovilidadId(int tipoMovilidadId) {
		this.tipoMovilidadId = tipoMovilidadId;
	}

	/**
	 * Gets the movilidad investigador encontrada.
	 *
	 * @return the movilidad investigador encontrada
	 */
	public MovilidadInvestigador getMovilidadInvestigadorEncontrada() {
		return movilidadInvestigadorEncontrada;
	}

	/**
	 * Gets the estado movilidad seleccionado.
	 *
	 * @return the estado movilidad seleccionado
	 */
	public String getEstadoMovilidadSeleccionado() {
		return estadoMovilidadSeleccionado;
	}

	/**
	 * Sets the estado movilidad seleccionado.
	 *
	 * @param estadoMovilidadSeleccionado
	 *            the new estado movilidad seleccionado
	 */
	public void setEstadoMovilidadSeleccionado(String estadoMovilidadSeleccionado) {
		this.estadoMovilidadSeleccionado = estadoMovilidadSeleccionado;
	}

	/**
	 * Gets the justificacion movilidad.
	 *
	 * @return the justificacion movilidad
	 */
	public String getJustificacionMovilidad() {
		return justificacionMovilidad;
	}

	/**
	 * Sets the justificacion movilidad.
	 *
	 * @param justificacionMovilidad
	 *            the new justificacion movilidad
	 */
	public void setJustificacionMovilidad(String justificacionMovilidad) {
		this.justificacionMovilidad = justificacionMovilidad;
	}

	/**
	 * Gets the estados movilidad item.
	 *
	 * @return the estados movilidad item
	 */
	public List<SelectItem> getEstadosMovilidadItem() {
		return estadosMovilidadItem;
	}

	/**
	 * Sets the estados movilidad item.
	 *
	 * @param estadosMovilidadItem
	 *            the new estados movilidad item
	 */
	public void setEstadosMovilidadItem(List<SelectItem> estadosMovilidadItem) {
		this.estadosMovilidadItem = estadosMovilidadItem;
	}

	/**
	 * Gets the lista historico estado movilidad.
	 *
	 * @return the lista historico estado movilidad
	 */
	public List<HistoricoEstadoMovilidad> getListaHistoricoEstadoMovilidad() {
		return listaHistoricoEstadoMovilidad;
	}

}
