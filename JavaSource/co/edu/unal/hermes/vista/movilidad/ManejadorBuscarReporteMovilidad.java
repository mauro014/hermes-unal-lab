package co.edu.unal.hermes.vista.movilidad;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;

import co.edu.unal.hermes.modelo.ArchivoMovilidad;
import co.edu.unal.hermes.modelo.ArchivoMovilidadDE;
import co.edu.unal.hermes.modelo.ArchivoMovilidadEP;
import co.edu.unal.hermes.modelo.ArchivoMovilidadVE;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.HistoricoEstadoMovilidad;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.MovilidadDocentesArtes;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesArtes;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.MovilidadVisitantesArtes;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;

/**
 * The Class ManejadorBuscarReporteMovilidad.
 */
public class ManejadorBuscarReporteMovilidad extends ManejadorBaseMovilidad {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8152799105794267354L;

	/** The mov doc ext. */
	private MovilidadDocentesExterior movDocExt;

	/** The mov est pos. */
	private MovilidadEstudiantesPosgrado movEstPos;

	/** The mov vis ext. */
	private MovilidadVisitanteExterior movVisExt;

	/** The mov doc art. */
	private MovilidadDocentesArtes movDocArt;

	/** The mov est art. */
	private MovilidadEstudiantesArtes movEstArt;

	/** The mov vis ext. */
	private MovilidadVisitantesArtes movVisArt;

	/** The s codigo movilidad. */
	private String sCodigoMovilidad = "";

	/** The s estado movilidad. */
	private String sEstadoMovilidad = "";

	/** The imprimir reporte movilidades. */
	private boolean imprimirReporteMovilidades = false;

	/** The archivo cargar. */
	private org.primefaces.model.UploadedFile archivoCargar;

	/** The lista archivos movilidad de. */
	private List<ArchivoMovilidadDE> listaArchivosMovilidadDE;

	/** The tipo archivo movilidad de item. */
	private List<SelectItem> tipoArchivoMovilidadDEItem;

	/** The hab mov de. */
	private boolean habMovDE = false;

	/** The lista archivos movilidad ep. */
	private List<ArchivoMovilidadEP> listaArchivosMovilidadEP;

	/** The tipo archivo movilidad ep item. */
	private List<SelectItem> tipoArchivoMovilidadEPItem;

	/** The hab mov ep. */
	private boolean habMovEP = false;

	/** The lista archivos movilidad ve. */
	private List<ArchivoMovilidadVE> listaArchivosMovilidadVE;

	/** The tipo archivo movilidad ve item. */
	private List<SelectItem> tipoArchivoMovilidadVEItem;

	/** The habilitar mov ve. */
	private boolean habilitarMovVE = false;

	/** The lista archivos movilidad da. */
	private List<ArchivoMovilidad> listaArchivosMovilidadDA;

	/** The tipo archivo movilidad da item. */
	private List<SelectItem> tipoArchivoMovilidadDAItem;

	/** The hab mov da. */
	private boolean habMovDA = false;

	/** The hab mov va. */
	private boolean habMovVA = false;

	/** The lista archivos movilidad ea. */
	private List<ArchivoMovilidad> listaArchivosMovilidadEA;

	/** The tipo archivo movilidad ea item. */
	private List<SelectItem> tipoArchivoMovilidadEAItem;

	/** The hab mov ea. */
	private boolean habMovEA = false;

	/** The tipo archivo movilidad ve sel. */
	private String tipoArchivoMovilidadVESel;

	/** The tipo archivo movilidad de sel. */
	private String tipoArchivoMovilidadDESel;

	/** The tipo archivo movilidad ep sel. */
	private String tipoArchivoMovilidadEPSel;

	/** The tipo archivo movilidad da sel. */
	private String tipoArchivoMovilidadDASel;

	/** The tipo archivo movilidad ea sel. */
	private String tipoArchivoMovilidadEASel;

	/** The documento seleccionado. */
	private ArchivoMovilidad documentoSeleccionado;

	/** The documento seleccionado ve. */
	private ArchivoMovilidadVE documentoSeleccionadoVE;

	/** The documento seleccionado de. */
	private ArchivoMovilidadDE documentoSeleccionadoDE;

	/** The documento seleccionado ep. */
	private ArchivoMovilidadEP documentoSeleccionadoEP;

	/** The documento seleccionado seg. */
	private ArchivoMovilidad documentoSeleccionadoSeg;

	/** The estado seguimiento. */
	private String estadoSeguimiento;

	/** The estado seguimiento. */
	private String estadoRevisionSeguimiento;

	/** The historico movilidad. */
	List<HistoricoEstadoMovilidad> historicoMovilidad;

	/** Variables para personas que revisan la movilidad. */
	private List<InvestigadorInterno> listaFacultad;

	/** The lista direccion. */
	private List<InvestigadorInterno> listaDireccion;

	private String observacionesSeg;
	private String idSeg;

	/**
	 * Instantiates a new manejador buscar reporte movilidad.
	 */
	public ManejadorBuscarReporteMovilidad() {
		reiniciarVariables();
	}
	
	public void eliminarArchivoObligatorioEP() {
		listaArchivosMovilidadEP.remove(documentoSeleccionadoEP);
        servicioGeneral.eliminarObjeto(documentoSeleccionadoEP);
        eliminarArchivoMovilidadGenerico(documentoSeleccionadoEP.getId());
    }
	
	public void eliminarArchivoObligatorioDE() {
		listaArchivosMovilidadDE.remove(documentoSeleccionadoDE);
        servicioGeneral.eliminarObjeto(documentoSeleccionadoDE);
        eliminarArchivoMovilidadGenerico(documentoSeleccionadoDE.getId());
    }
	
	public void eliminarArchivoObligatorioVE() {
		listaArchivosMovilidadVE.remove(documentoSeleccionadoVE);
        servicioGeneral.eliminarObjeto(documentoSeleccionadoVE);
        eliminarArchivoMovilidadGenerico(documentoSeleccionadoVE.getId());
    }
	
	public void eliminarArchivoObligatorioArt() {
		listaArchivosMovilidadDA.remove(documentoSeleccionado);
        servicioGeneral.eliminarObjeto(documentoSeleccionado);
        eliminarArchivoMovilidadGenerico(documentoSeleccionado.getId());
    }

	/**
	 * Reiniciar variables.
	 */
	private void reiniciarVariables() {
		idSeg = "";
		movDocExt = null;
		movEstPos = null;
		movVisExt = null;
		movDocArt = null;
		movEstArt = null;

		listaFacultad = new ArrayList<InvestigadorInterno>();
		listaFacultad.add(new InvestigadorInterno());
		listaDireccion = new ArrayList<InvestigadorInterno>();
		listaDireccion.add(new InvestigadorInterno());

		listaArchivosMovilidadDE = null;
		listaArchivosMovilidadEP = null;
		listaArchivosMovilidadVE = null;
		listaArchivosMovilidadDA = null;
		listaArchivosMovilidadEA = null;

		habMovDE = false;
		habMovEP = false;
		habilitarMovVE = false;
		habMovDA = false;
		habMovEA = false;

		estadoSeguimiento = "";
		estadoRevisionSeguimiento = "";
		imprimirReporteMovilidades = false;
	}

	/**
	 * Mensaje no existe movilidad.
	 */
	private void mensajeNoExisteMovilidad() {
		mensajeInfo("La movilidad solicitada, no se encuentra registrada en el sistema ");
	}

	/**
	 * Buscar reporte movilidad visitante artes.
	 */
	public void buscarReporteMovilidadVisitanteArtes() {

		movVisArt = (MovilidadVisitantesArtes) obtenerMovilidad(MovilidadVisitantesArtes.class, sCodigoMovilidad);

		if (movVisArt != null) {
			if (movVisArt.getEstado() == null) {
				sEstadoMovilidad = "";
			} else if (movVisArt.getEstado().equals("I")) {
				sEstadoMovilidad = "Ingresando";
			} else if (movVisArt.getEstado().equals("P")) {
				sEstadoMovilidad = "Propuesta";
			} else if (movVisArt.getEstado().equals("C")) {
				sEstadoMovilidad = "Cancelada";
			} else {
				sEstadoMovilidad = "";
			}
			// Se manejan elementos graficos
			imprimirReporteMovilidades = true;
			habMovVA = true;
			// Se cargan archivos
			listaArchivosMovilidadDA = consultarArchivosModalidad(ArchivoMovilidad.class, movVisArt.getId().toString());

			cargarHistoricoCambioEstado(movVisArt.getId());
			if (historicoMovilidad.size() != 0) {
				if (!historicoMovilidad.get(historicoMovilidad.size() - 1).getEstado().equals("Estado indeterminado")) {
					sEstadoMovilidad = historicoMovilidad.get(historicoMovilidad.size() - 1).getEstado();
				}
			}
		}
	}

	/**
	 * Buscar reporte movilidad visitante exterior.
	 */
	public void buscarReporteMovilidadVisitanteExterior() {

		movVisExt = (MovilidadVisitanteExterior) obtenerMovilidad(MovilidadVisitanteExterior.class, sCodigoMovilidad);

		if (movVisExt != null) {
			if (movVisExt.getEstado() == null) {
				sEstadoMovilidad = "";
			} else if (movVisExt.getEstado().equals("I")) {
				sEstadoMovilidad = "Ingresando";
			} else if (movVisExt.getEstado().equals("P")) {
				sEstadoMovilidad = "Propuesta";
			} else if (movVisExt.getEstado().equals("C")) {
				sEstadoMovilidad = "Cancelada";
			} else {

			}

			// Se manejan elementos graficos
			imprimirReporteMovilidades = true;
			habilitarMovVE = true;
			estadoSeguimiento = movVisExt.getEstadoSeguimiento();
			estadoRevisionSeguimiento = movVisExt.getEstadoRevisionSeguimiento();

			// Se cargan archivos
			listaArchivosMovilidadVE = consultarArchivosModalidad(ArchivoMovilidadVE.class,
					movVisExt.getId().toString());

			cargarHistoricoCambioEstado(movVisExt.getId());
			if (historicoMovilidad.size() != 0) {
				if (!historicoMovilidad.get(historicoMovilidad.size() - 1).getEstado().equals("Estado indeterminado")) {
					sEstadoMovilidad = historicoMovilidad.get(historicoMovilidad.size() - 1).getEstado();
				}
			}

			/**
			 * Se verifican personas asignadas para la revision
			 */
			listaFacultad = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
					"select ii from Persona p, PersonaRol pr, MovilidadVisitanteExterior mv, InvestigadorInterno ii "
							+ " where pr.nombre = 'MF' and mv.dependencia.facultad.id = ii.dependencia.facultad.id"
							+ " and p.id.documento = ii.id.documento and ii.id.tipoDocumento = p.id.tipoDocumento and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and "
							+ "mv.id = '" + movVisExt.getId() + "'" + "and p.id.documento not in ("
							+ DOCUMENTOS_PRUEBAS_DESARROLLO + ") and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");

			listaDireccion = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
					"select ii from Persona p, PersonaRol pr, MovilidadVisitanteExterior mv, InvestigadorInterno ii "
							+ " where pr.nombre = 'MD' and mv.dependencia.sede.id = ii.dependencia.sede.id"
							+ " and p.id.documento = ii.id.documento and ii.id.tipoDocumento = p.id.tipoDocumento and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and "
							+ "mv.id = '" + movVisExt.getId() + "'" + "and p.id.documento not in ("
							+ DOCUMENTOS_PRUEBAS_DESARROLLO + ") and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");

			if (obtenerRolPersona()) {
				cargarTiposDocumentosVisExt(movVisExt.getTipoMovilidad().getId());
			}

		}
	}

	/**
	 * Buscar reporte movilidad docente exterior.
	 */
	public void buscarReporteMovilidadDocenteExterior() {

		movDocExt = (MovilidadDocentesExterior) obtenerMovilidad(MovilidadDocentesExterior.class, sCodigoMovilidad);

		if (movDocExt != null) {

			if (movDocExt.getEstado() == null) {
				sEstadoMovilidad = "";
			} else if (movDocExt.getEstado().equals("I")) {
				sEstadoMovilidad = "Ingresando";
			} else if (movDocExt.getEstado().equals("P")) {
				sEstadoMovilidad = "Propuesta";
			} else if (movDocExt.getEstado().equals("C")) {
				sEstadoMovilidad = "Cancelada";
			}

			// Se manejan elementos graficos
			imprimirReporteMovilidades = true;
			habMovDE = true;
			estadoSeguimiento = movDocExt.getEstadoSeguimiento();
			estadoRevisionSeguimiento = movDocExt.getEstadoRevisionSeguimiento();

			// Se cargan archivos
			listaArchivosMovilidadDE = consultarArchivosModalidad(ArchivoMovilidadDE.class,
					movDocExt.getId().toString());

			cargarHistoricoCambioEstado(movDocExt.getId());
			if (historicoMovilidad.size() != 0) {
				if (!historicoMovilidad.get(historicoMovilidad.size() - 1).getEstado().equals("Estado indeterminado")) {
					sEstadoMovilidad = historicoMovilidad.get(historicoMovilidad.size() - 1).getEstado();
				}
			}

			/**
			 * Se verifican personas asignadas para la revision
			 */

			listaFacultad = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
					"select ii2 from Persona p, PersonaRol pr, MovilidadDocentesExterior mv, InvestigadorInterno ii, InvestigadorInterno ii2 "
							+ " where pr.nombre = 'MF' and mv.personaInv.id.documento = ii.id.documento and mv.personaInv.id.tipoDocumento = ii.id.tipoDocumento and ii.dependencia.facultad.id = ii2.dependencia.facultad.id"
							+ " and p.id.documento = ii2.id.documento and ii2.id.tipoDocumento = p.id.tipoDocumento and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and "
							+ "mv.id = '" + movDocExt.getId() + "'" + "and p.id.documento not in ("
							+ DOCUMENTOS_PRUEBAS_DESARROLLO + ") and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");

			listaDireccion = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
					"select ii2 from Persona p, PersonaRol pr, MovilidadDocentesExterior mv, InvestigadorInterno ii, InvestigadorInterno ii2"
							+ " where pr.nombre = 'MD' and mv.personaInv.id.documento = ii.id.documento and mv.personaInv.id.tipoDocumento = ii.id.tipoDocumento and ii.dependencia.sede.id = ii2.dependencia.sede.id"
							+ " and p.id.documento = ii2.id.documento and ii2.id.tipoDocumento = p.id.tipoDocumento and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and "
							+ "mv.id = '" + movDocExt.getId() + "'" + "and p.id.documento not in ("
							+ DOCUMENTOS_PRUEBAS_DESARROLLO + ") and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");

			/**
			 * 
			 */
			if (obtenerRolPersona()) {
				cargarTiposDocumentosDocExt(movDocExt.getTipoMovilidad().getId());
			}
		}
	}

	/**
	 * Buscar reporte movilidad estudiantes posgrado.
	 */
	public void buscarReporteMovilidadEstudiantesPosgrado() {

		movEstPos = (MovilidadEstudiantesPosgrado) obtenerMovilidad(MovilidadEstudiantesPosgrado.class,
				sCodigoMovilidad);

		if (movEstPos != null) {
			if (movEstPos.getEstado() == null) {
				sEstadoMovilidad = "";
			} else if (movEstPos.getEstado().equals("I")) {
				sEstadoMovilidad = "Ingresando";
			} else if (movEstPos.getEstado().equals("P")) {
				sEstadoMovilidad = "Propuesta";
			} else if (movEstPos.getEstado().equals("C")) {
				sEstadoMovilidad = "Cancelada";
			}

			// Se manejan elementos graficos
			imprimirReporteMovilidades = true;
			habMovEP = true;
			estadoSeguimiento = movEstPos.getEstadoSeguimiento();
			estadoRevisionSeguimiento = movEstPos.getEstadoRevisionSeguimiento();

			// Se cargan archivos
			listaArchivosMovilidadEP = consultarArchivosModalidad(ArchivoMovilidadEP.class,
					movEstPos.getId().toString());

			cargarHistoricoCambioEstado(movEstPos.getId());
			if (historicoMovilidad.size() != 0) {
				if (!historicoMovilidad.get(historicoMovilidad.size() - 1).getEstado().equals("Estado indeterminado")) {
					sEstadoMovilidad = historicoMovilidad.get(historicoMovilidad.size() - 1).getEstado();
				}
			}

			/**
			 * Se verifican personas asignadas para la revision
			 */
			listaFacultad = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
					"select ii from Persona p, PersonaRol pr, MovilidadEstudiantesPosgrado mv, InvestigadorInterno ii, Estudiante e "
							+ " where pr.nombre = 'MF' and mv.estudianteInv.id.documento = e.id.documento and mv.estudianteInv.id.tipoDocumento = e.id.tipoDocumento and e.dependencia.facultad.id = ii.dependencia.facultad.id"
							+ " and p.id.documento = ii.id.documento and ii.id.tipoDocumento = p.id.tipoDocumento and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and "
							+ "mv.id = '" + movEstPos.getId() + "'" + "and p.id.documento not in ("
							+ DOCUMENTOS_PRUEBAS_DESARROLLO + ") and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");

			listaDireccion = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
					"select ii from Persona p, PersonaRol pr, MovilidadEstudiantesPosgrado mv, InvestigadorInterno ii, Estudiante e"
							+ " where pr.nombre = 'MD' and mv.estudianteInv.id.documento = e.id.documento and mv.estudianteInv.id.tipoDocumento = e.id.tipoDocumento and e.dependencia.sede.id = ii.dependencia.sede.id"
							+ " and p.id.documento = ii.id.documento and ii.id.tipoDocumento = p.id.tipoDocumento and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and "
							+ "mv.id = '" + movEstPos.getId() + "'" + "and p.id.documento not in ("
							+ DOCUMENTOS_PRUEBAS_DESARROLLO + ") and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");

			if (obtenerRolPersona()) {
				cargarTiposDocumentosEstPos(movEstPos.getTipoMovilidad().getId());
			}

		}
	}

	/**
	 * Buscar reporte movilidad docentes artes.
	 */
	public void buscarReporteMovilidadDocentesArtes() {

		movDocArt = (MovilidadDocentesArtes) obtenerMovilidad(MovilidadDocentesArtes.class, sCodigoMovilidad);

		if (movDocArt != null) {
			if (movDocArt.getEstado() == null) {
				sEstadoMovilidad = "";
			} else if (movDocArt.getEstado().equals("I")) {
				sEstadoMovilidad = "Ingresando";
			} else if (movDocArt.getEstado().equals("P")) {
				sEstadoMovilidad = "Propuesta";
			} else if (movDocArt.getEstado().equals("C")) {
				sEstadoMovilidad = "Cancelada";
			}
			estadoSeguimiento = movDocArt.getEstadoSeguimiento();
			estadoRevisionSeguimiento = movDocArt.getEstadoRevisionSeguimiento();
			imprimirReporteMovilidades = true;
			listaArchivosMovilidadDA = consultarArchivosModalidad(ArchivoMovilidad.class, movDocArt.getId().toString());
			habMovDA = true;
			listaFacultad = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
					"select ii2 from Persona p, PersonaRol pr, MovilidadDocentesArtes mv, InvestigadorInterno ii, InvestigadorInterno ii2 "
							+ " where pr.nombre = 'MF' and mv.personaInv.id.documento = ii.id.documento and mv.personaInv.id.tipoDocumento = ii.id.tipoDocumento and ii.dependencia.facultad.id = ii2.dependencia.facultad.id"
							+ " and p.id.documento = ii2.id.documento and ii2.id.tipoDocumento = p.id.tipoDocumento and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and "
							+ "mv.id = '" + movDocArt.getId() + "'" + "and p.id.documento not in ("
							+ DOCUMENTOS_PRUEBAS_DESARROLLO + ") and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");

			listaDireccion = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
					"select ii2 from Persona p, PersonaRol pr, MovilidadDocentesArtes mv, InvestigadorInterno ii, InvestigadorInterno ii2"
							+ " where pr.nombre = 'MD' and mv.personaInv.id.documento = ii.id.documento and mv.personaInv.id.tipoDocumento = ii.id.tipoDocumento and ii.dependencia.sede.id = ii2.dependencia.sede.id"
							+ " and p.id.documento = ii2.id.documento and ii2.id.tipoDocumento = p.id.tipoDocumento and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and "
							+ "mv.id = '" + movDocArt.getId() + "'" + "and p.id.documento not in ("
							+ DOCUMENTOS_PRUEBAS_DESARROLLO + ") and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");
			if (obtenerRolPersona()) {
				cargarTiposDocumentosDocArt(movDocArt.getTipoMovilidad().getId());
			}
			cargarHistoricoCambioEstado(movDocArt.getId());
			if (historicoMovilidad.size() != 0) {
				if (!historicoMovilidad.get(historicoMovilidad.size() - 1).getEstado().equals("Estado indeterminado")) {
					sEstadoMovilidad = historicoMovilidad.get(historicoMovilidad.size() - 1).getEstado();
				}
			}
		}
	}

	/**
	 * Buscar reporte movilidad estudiantes artes.
	 */
	public void buscarReporteMovilidadEstudiantesArtes() {

		movEstArt = (MovilidadEstudiantesArtes) obtenerMovilidad(MovilidadEstudiantesArtes.class, sCodigoMovilidad);

		if (movEstArt != null) {
			if (movEstArt.getEstado() == null) {
				sEstadoMovilidad = "";
			} else if (movEstArt.getEstado().equals("I")) {
				sEstadoMovilidad = "Ingresando";
			} else if (movEstArt.getEstado().equals("P")) {
				sEstadoMovilidad = "Propuesta";
			} else if (movEstArt.getEstado().equals("C")) {
				sEstadoMovilidad = "Cancelada";
			} else {
				sEstadoMovilidad = "";
			}
			estadoSeguimiento = movEstArt.getEstadoSeguimiento();
			estadoRevisionSeguimiento = movEstArt.getEstadoRevisionSeguimiento();
			imprimirReporteMovilidades = true;
			listaArchivosMovilidadEA = servicioMovilidad.obtenerNombresArchivosMovilidadEA(movEstArt);
			habMovEA = true;
			listaFacultad = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
					"select ii from Persona p, PersonaRol pr, MovilidadEstudiantesArtes mv, InvestigadorInterno ii, Estudiante e "
							+ " where pr.nombre = 'MF' and mv.estudianteInv.id.documento = e.id.documento and mv.estudianteInv.id.tipoDocumento = e.id.tipoDocumento and e.dependencia.facultad.id = ii.dependencia.facultad.id"
							+ " and p.id.documento = ii.id.documento and ii.id.tipoDocumento = p.id.tipoDocumento and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and "
							+ "mv.id = '" + movEstArt.getId() + "'" + "and p.id.documento not in ("
							+ DOCUMENTOS_PRUEBAS_DESARROLLO + ") and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");

			listaDireccion = servicioGeneral.obtenerObjetos(InvestigadorInterno.class,
					"select ii from Persona p, PersonaRol pr, MovilidadEstudiantesArtes mv, InvestigadorInterno ii, Estudiante e"
							+ " where pr.nombre = 'MD' and mv.estudianteInv.id.documento = e.id.documento and mv.estudianteInv.id.tipoDocumento = e.id.tipoDocumento and e.dependencia.sede.id = ii.dependencia.sede.id"
							+ " and p.id.documento = ii.id.documento and ii.id.tipoDocumento = p.id.tipoDocumento and p.id.documento = pr.documento and p.id.tipoDocumento = pr.tipoDocumento and "
							+ "mv.id = '" + movEstArt.getId() + "'" + "and p.id.documento not in ("
							+ DOCUMENTOS_PRUEBAS_DESARROLLO + ") and (pr.fechaFinRol IS NULL OR pr.fechaFinRol >= CURRENT_DATE)");
			if (obtenerRolPersona()) {
				cargarTiposDocumentosEstArt(movEstArt.getTipoMovilidad().getId());
			}
			cargarHistoricoCambioEstado(movEstArt.getId());
			if (historicoMovilidad.size() != 0) {
				if (!historicoMovilidad.get(historicoMovilidad.size() - 1).getEstado().equals("Estado indeterminado")) {
					sEstadoMovilidad = historicoMovilidad.get(historicoMovilidad.size() - 1).getEstado();
				}
			}
		}
	}

	/**
	 * Agregar historico.
	 *
	 * @param descripcion
	 *            the descripcion
	 * @param fecha
	 *            the fecha
	 * @param persona
	 *            the persona
	 */
	private void cargarHistoricoCambioEstado(Long id) {
		historicoMovilidad = servicioMovilidad.getHistoricoEstadoMovilidad(id);
	}

	/**
	 * Buscar reporte movilidad.
	 */
	public void buscarReporteMovilidad() {
		sCodigoMovilidad = sCodigoMovilidad.trim();
		reiniciarVariables();
		idSeg = sCodigoMovilidad;
		buscarReporteMovilidadDocenteExterior();
		if (!imprimirReporteMovilidades) {
			buscarReporteMovilidadEstudiantesPosgrado();
		}
		if (!imprimirReporteMovilidades) {
			buscarReporteMovilidadVisitanteExterior();
		}
		if (!imprimirReporteMovilidades) {
			buscarReporteMovilidadDocentesArtes();
		}
		if (!imprimirReporteMovilidades) {
			buscarReporteMovilidadEstudiantesArtes();
		}
		if (!imprimirReporteMovilidades) {
			buscarReporteMovilidadVisitanteArtes();
		}
		if (!imprimirReporteMovilidades) {
			mensajeNoExisteMovilidad();
		}

	}

	/**
	 * Imprimir reportes movilidades.
	 */
	public void imprimirReportesMovilidades() {
		if (movDocExt != null) {
			imprimirReporteDocEventos();
		} else if (movEstPos != null) {
			imprimirReporteEstPosg();
		} else if (movVisExt != null) {
			imprimirReporteVisitantesInt();
		} else if (movDocArt != null) {
			imprimirReporteDocArt();
		} else if (movEstArt != null) {
			imprimirReporteEstArt();
		} else if (movVisArt != null) {
			imprimirReporteVisArt();
		} else {
			mensajeNoExisteMovilidad();
		}
	}

	/**
	 * Consultar seguimiento movilidad.
	 *
	 * @return the string
	 */
	public String consultarSeguimientoMovilidad() {
		if (movDocExt != null) {
			return consultarSeguimientoDoc();
		} else if (movEstPos != null) {
			if (movEstPos.getTipoMovilidad().getId().equals(MOV3_IN)
					|| movEstPos.getTipoMovilidad().getId().equals(CF_MOV1)
					|| movEstPos.getTipoMovilidad().getId().equals(CF_MOV6)) {
				return consultarSeguimientoEstPos();
			} else if (movEstPos.getTipoMovilidad().getId().equals(D1)
					|| movEstPos.getTipoMovilidad().getId().equals(CF_MOV5)) {
				return consultarSeguimientoEstRes();
			}
		} else if (movVisExt != null) {
			return consultarSeguimientoVisExt();
		} else if (movDocArt != null) {
			return consultarSeguimientoDocArt();
		} else if (movEstArt != null) {
			return consultarSeguimientoEstArt();
		}
		return "";
	}
	
	public String consultarSeguimientoDocArt() {
		sesion.setAttribute("movilidadDocenteArtes", movDocArt);
		sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadDocenteArtes");
		sesion.setAttribute("consultaMovilidadDocenteArtes", "SI");
		return "SeguimientoMovilidadDocenteArtes";
	}

	public String consultarSeguimientoEstArt() {
		sesion.setAttribute("movilidadEstudianteArt", movEstArt);
		sesion.removeAttribute("ManejadorCrearEditarSeguimientoMovilidadEstudianteArtes");
		sesion.setAttribute("esMovilidadResidencia", true);
		sesion.setAttribute("consultaMovilidadEstudiantesArtes", "SI");
		return "SeguimientoMovilidadEstudianteArtes";
	}

	/**
	 * Consultar seguimiento vis ext.
	 *
	 * @return the string
	 */
	public String consultarSeguimientoVisExt() {
		Long id = Long.parseLong(sCodigoMovilidad);
		boolean consultaFacultad = false;
		boolean esConsulta = true;
		return consultarSeguimientoVisExt(id, consultaFacultad, esConsulta);
	}

	/**
	 * Consultar seguimiento doc.
	 *
	 * @return the string
	 */
	public String consultarSeguimientoDoc() {
		Long id = Long.parseLong(sCodigoMovilidad);
		boolean consultaFacultad = false;
		boolean esConsulta = true;
		return ingresarSeguimientoDoc(id, consultaFacultad, esConsulta);
	}

	/**
	 * Consultar seguimiento est res.
	 *
	 * @return the string
	 */
	public String consultarSeguimientoEstRes() {
		Long id = Long.parseLong(sCodigoMovilidad);
		boolean consultaFacultad = false;
		boolean esConsulta = true;
		return ingresarSeguimientoEstRes(id, consultaFacultad, esConsulta);
	}

	/**
	 * Consultar seguimiento est pos.
	 *
	 * @return the string
	 */
	public String consultarSeguimientoEstPos() {
		Long id = Long.parseLong(sCodigoMovilidad);
		boolean consultaFacultad = false;
		boolean esConsulta = true;
		return consultarSeguimientoEstPos(id, consultaFacultad, esConsulta);
	}

	/**
	 * Imprimir reporte visitantes int.
	 */
	public void imprimirReporteVisitantesInt() {
		imprimirMovilidadVisitanteGenerico(movVisExt);
	}

	/**
	 * Imprimir reporte doc eventos.
	 */
	public void imprimirReporteDocEventos() {
		imprimirMovilidadEventoGenerico(movDocExt);
	}

	/**
	 * Imprimir reporte est posg.
	 */
	public void imprimirReporteEstPosg() {
		imprimirMovilidadEstudiantesPosgradoGenerico(movEstPos);
	}

	/**
	 * Imprimir reporte doc art.
	 */
	public void imprimirReporteDocArt() {
		imprimirMovilidadDocenteArtesGenerico(movDocArt);
	}

	/**
	 * Imprimir reporte est art.
	 */
	public void imprimirReporteEstArt() {
		imprimirMovilidadEstudiantesArtesGenerico(movEstArt);
	}

	/**
	 * Imprimir reporte vis art.
	 */
	public void imprimirReporteVisArt() {
		imprimirMovilidadVisitantesArtesGenerico(movVisArt);
	}

	// /*******Cargar Menu Tipo Documento

	/**
	 * Cargar tipos documentos vis ext.
	 */
	private void cargarTiposDocumentosVisExt(String tipo) {
		
		if(!esNulo(movVisExt.getConvocatoria())) {
			String restriccionArchivosConv = movVisExt.getConvocatoria().getRequisitosConvTexto();
			Object[] lista = cargarTiposDocumentosGenericoItem(restriccionArchivosConv);
			if(esArrayVacio(lista)) cargarTiposDocumentosGenericoItem(tipo);
			if (!esArrayVacio(lista)) {
				tipoArchivoMovilidadVEItem = (List<SelectItem>) lista[0];
				tipoArchivoMovilidadVESel = (String) lista[1];
			}
		}

	}

	/**
	 * Cargar tipos documentos doc ext.
	 */
	private void cargarTiposDocumentosDocExt(String tipo) {
		if(!esNulo(movDocExt.getConvocatoria())) {
			String restriccionArchivosConv = movDocExt.getConvocatoria().getRequisitosConvTexto();
			Object[] lista = cargarTiposDocumentosGenericoItem(restriccionArchivosConv);
			if(esArrayVacio(lista)) cargarTiposDocumentosGenericoItem(tipo);
			if (!esArrayVacio(lista)) {
				tipoArchivoMovilidadDEItem = (List<SelectItem>) lista[0];
				tipoArchivoMovilidadDESel = (String) lista[1];
			}
		}
	}

	/**
	 * Cargar tipos documentos est pos.
	 */
	private void cargarTiposDocumentosEstPos(String tipo) {
		
		if(!esNulo(movEstPos.getConvocatoria())) {
			String restriccionArchivosConv = movEstPos.getConvocatoria().getRequisitosConvTexto();
			Object[] lista = cargarTiposDocumentosGenericoItem(restriccionArchivosConv);
			if(esArrayVacio(lista)) cargarTiposDocumentosGenericoItem(tipo);
			if (!esArrayVacio(lista)) {
				tipoArchivoMovilidadEPItem = (List<SelectItem>) lista[0];
				tipoArchivoMovilidadEPSel = (String) lista[1];
			}
		}
	}

	/**
	 * Cargar tipos documentos doc art.
	 */
	private void cargarTiposDocumentosDocArt(String tipo) {
		
		if(!esNulo(movDocArt.getConvocatoria())) {
			String restriccionArchivosConv = movDocArt.getConvocatoria().getRequisitosConvTexto();
			Object[] lista = cargarTiposDocumentosGenericoItem(restriccionArchivosConv);
			if(esArrayVacio(lista)) cargarTiposDocumentosGenericoItem(tipo);
			if (!esArrayVacio(lista)) {
				tipoArchivoMovilidadDAItem = (List<SelectItem>) lista[0];
				tipoArchivoMovilidadDASel = (String) lista[1];
			}
		}
	}

	/**
	 * Cargar tipos documentos est art.
	 */
	private void cargarTiposDocumentosEstArt(String tipo) {

		if(!esNulo(movEstArt.getConvocatoria())) {
			String restriccionArchivosConv = movEstArt.getConvocatoria().getRequisitosConvTexto();
			Object[] lista = cargarTiposDocumentosGenericoItem(restriccionArchivosConv);
			if(esArrayVacio(lista)) cargarTiposDocumentosGenericoItem(tipo);
			if (!esArrayVacio(lista)) {
				tipoArchivoMovilidadEAItem = (List<SelectItem>) lista[0];
				tipoArchivoMovilidadEASel = (String) lista[1];
			}
		}

	}

	/**
	 * Guardar archivo mov ve.
	 *
	 * @param event
	 *            the event
	 */
	public void guardarArchivoMovVE(FileUploadEvent event) {
		archivoCargar = event.getFile();
		insertarArchivoMovilidadVEGenerico(movVisExt.getId(), archivoCargar, tipoArchivoMovilidadVESel);

		listaArchivosMovilidadVE = consultarArchivosModalidad(ArchivoMovilidadVE.class, sCodigoMovilidad);
	}

	/**
	 * Guardar archivo mov de.
	 *
	 * @param event
	 *            the event
	 */
	public void guardarArchivoMovDE(FileUploadEvent event) {
		archivoCargar = event.getFile();
		insertarArchivoMovilidadDEGenerico(movDocExt.getId(), archivoCargar, tipoArchivoMovilidadDESel);
		listaArchivosMovilidadDE = consultarArchivosModalidad(ArchivoMovilidadDE.class, sCodigoMovilidad);
	}

	/**
	 * Guardar archivo mov ep.
	 *
	 * @param event
	 *            the event
	 */
	public void guardarArchivoMovEP(FileUploadEvent event) {
		archivoCargar = event.getFile();
		insertarArchivoMovilidadEPGenerico(movEstPos.getId(), archivoCargar, tipoArchivoMovilidadEPSel);
		listaArchivosMovilidadEP = consultarArchivosModalidad(ArchivoMovilidadEP.class, sCodigoMovilidad);
	}

	/**
	 * Guardar archivo mov da.
	 *
	 * @param event
	 *            the event
	 */
	public void guardarArchivoMovDA(FileUploadEvent event) {
		archivoCargar = event.getFile();
		insertarArchivoMovilidadDAGenerico(movDocArt.getId(), archivoCargar, tipoArchivoMovilidadDASel);
		listaArchivosMovilidadDA = consultarArchivosModalidad(ArchivoMovilidad.class, sCodigoMovilidad);
	}

	/**
	 * Guardar archivo mov ea.
	 *
	 * @param event
	 *            the event
	 */
	public void guardarArchivoMovEA(FileUploadEvent event) {
		archivoCargar = event.getFile();
		insertarArchivoMovilidadEAGenerico(movEstArt.getId(), archivoCargar, tipoArchivoMovilidadEASel);
		listaArchivosMovilidadEA = consultarArchivosModalidad(ArchivoMovilidad.class, sCodigoMovilidad);
	}

	/**
	 * Descargar archivo mov ve.
	 */
	public void descargarArchivoMovVE() {
		descargarArchivoMovilidadVEGenerico(documentoSeleccionadoVE.getId());
	}

	/**
	 * Descargar archivo mov de.
	 */
	public void descargarArchivoMovDE() {
		descargarArchivoMovilidadDEGenerico(documentoSeleccionadoDE.getId());
	}

	/**
	 * Descargar archivo mov ep.
	 */
	public void descargarArchivoMovEP() {
		descargarArchivoMovilidadEPGenerico(documentoSeleccionadoEP.getId());
	}

	/**
	 * Descargar archivo movilidad art.
	 */
	public void descargarArchivoMovilidadArt() {
		descargarArchivoMovilidadGenerico(documentoSeleccionado.getId());
	}

	/**
	 * Descargar archivo mov seg.
	 */
	public void descargarArchivoMovSeg() {
		descargarArchivoMovilidadSegGenerico(documentoSeleccionadoSeg.getId());
	}

	/**
	 * Obtener rol persona.
	 *
	 * @return true, if successful
	 */
	public boolean obtenerRolPersona() {
		personaActual = (Persona) sesion.getAttribute("persona");
		List<PersonaRol> personaRol = servicioGeneral.obtenerObjetos(PersonaRol.class,
				"from PersonaRol where tipoDocumento ='" + personaActual.getId().getTipoDocumento()
						+ "' and documento = '" + personaActual.getId().getDocumento() + "' and nombre in ('MD','MF') and (fechaFinRol IS NULL OR fechaFinRol >= CURRENT_DATE)");
		return !esListaVacia(personaRol);

	}

	/**
	 * Sets the s codigo movilidad.
	 *
	 * @param sCodigoMovilidad
	 *            the new s codigo movilidad
	 */
	public void setsCodigoMovilidad(String sCodigoMovilidad) {
		this.sCodigoMovilidad = sCodigoMovilidad;
	}

	/**
	 * Gets the s codigo movilidad.
	 *
	 * @return the s codigo movilidad
	 */
	public String getsCodigoMovilidad() {
		return sCodigoMovilidad;
	}

	/**
	 * Gets the lista archivos movilidad de.
	 *
	 * @return the lista archivos movilidad de
	 */
	public List<ArchivoMovilidadDE> getListaArchivosMovilidadDE() {
		return listaArchivosMovilidadDE;
	}

	/**
	 * Gets the tipo archivo movilidad de item.
	 *
	 * @return the tipo archivo movilidad de item
	 */
	public List<SelectItem> getTipoArchivoMovilidadDEItem() {
		return tipoArchivoMovilidadDEItem;
	}

	/**
	 * Gets the lista archivos movilidad ep.
	 *
	 * @return the lista archivos movilidad ep
	 */
	public List<ArchivoMovilidadEP> getListaArchivosMovilidadEP() {
		return listaArchivosMovilidadEP;
	}

	/**
	 * Sets the lista archivos movilidad ep.
	 *
	 * @param listaArchivosMovilidadEP
	 *            the new lista archivos movilidad ep
	 */
	public void setListaArchivosMovilidadEP(List<ArchivoMovilidadEP> listaArchivosMovilidadEP) {
		this.listaArchivosMovilidadEP = listaArchivosMovilidadEP;
	}

	/**
	 * Gets the tipo archivo movilidad ep item.
	 *
	 * @return the tipo archivo movilidad ep item
	 */
	public List<SelectItem> getTipoArchivoMovilidadEPItem() {
		return tipoArchivoMovilidadEPItem;
	}

	/**
	 * Checks if is hab mov de.
	 *
	 * @return true, if is hab mov de
	 */
	public boolean isHabMovDE() {
		return habMovDE;
	}

	/**
	 * Sets the hab mov de.
	 *
	 * @param habMovDE
	 *            the new hab mov de
	 */
	public void setHabMovDE(boolean habMovDE) {
		this.habMovDE = habMovDE;
	}

	/**
	 * Checks if is hab mov ep.
	 *
	 * @return true, if is hab mov ep
	 */
	public boolean isHabMovEP() {
		return habMovEP;
	}

	/**
	 * Sets the hab mov ep.
	 *
	 * @param habMovEP
	 *            the new hab mov ep
	 */
	public void setHabMovEP(boolean habMovEP) {
		this.habMovEP = habMovEP;
	}

	/**
	 * Gets the lista archivos movilidad ve.
	 *
	 * @return the lista archivos movilidad ve
	 */
	public List<ArchivoMovilidadVE> getListaArchivosMovilidadVE() {
		return listaArchivosMovilidadVE;
	}

	/**
	 * Sets the lista archivos movilidad ve.
	 *
	 * @param listaArchivosMovilidadVE
	 *            the new lista archivos movilidad ve
	 */
	public void setListaArchivosMovilidadVE(List<ArchivoMovilidadVE> listaArchivosMovilidadVE) {
		this.listaArchivosMovilidadVE = listaArchivosMovilidadVE;
	}

	/**
	 * Gets the tipo archivo movilidad ve item.
	 *
	 * @return the tipo archivo movilidad ve item
	 */
	public List<SelectItem> getTipoArchivoMovilidadVEItem() {
		return tipoArchivoMovilidadVEItem;
	}

	/**
	 * Checks if is imprimir reporte movilidades.
	 *
	 * @return true, if is imprimir reporte movilidades
	 */
	public boolean isImprimirReporteMovilidades() {
		return imprimirReporteMovilidades;
	}

	/**
	 * Sets the imprimir reporte movilidades.
	 *
	 * @param imprimirReporteMovilidades
	 *            the new imprimir reporte movilidades
	 */
	public void setImprimirReporteMovilidades(boolean imprimirReporteMovilidades) {
		this.imprimirReporteMovilidades = imprimirReporteMovilidades;
	}

	/**
	 * Gets the mov doc ext.
	 *
	 * @return the mov doc ext
	 */
	public MovilidadDocentesExterior getMovDocExt() {
		return movDocExt;
	}

	/**
	 * Sets the mov doc ext.
	 *
	 * @param movDocExt
	 *            the new mov doc ext
	 */
	public void setMovDocExt(MovilidadDocentesExterior movDocExt) {
		this.movDocExt = movDocExt;
	}

	/**
	 * Gets the mov est pos.
	 *
	 * @return the mov est pos
	 */
	public MovilidadEstudiantesPosgrado getMovEstPos() {
		return movEstPos;
	}

	/**
	 * Sets the mov est pos.
	 *
	 * @param movEstPos
	 *            the new mov est pos
	 */
	public void setMovEstPos(MovilidadEstudiantesPosgrado movEstPos) {
		this.movEstPos = movEstPos;
	}

	/**
	 * Gets the mov vis ext.
	 *
	 * @return the mov vis ext
	 */
	public MovilidadVisitanteExterior getMovVisExt() {
		return movVisExt;
	}

	/**
	 * Sets the mov vis ext.
	 *
	 * @param movVisExt
	 *            the new mov vis ext
	 */
	public void setMovVisExt(MovilidadVisitanteExterior movVisExt) {
		this.movVisExt = movVisExt;
	}

	/**
	 * Gets the archivo cargar.
	 *
	 * @return the archivo cargar
	 */
	public org.primefaces.model.UploadedFile getArchivoCargar() {
		return archivoCargar;
	}

	/**
	 * Sets the archivo cargar.
	 *
	 * @param archivoCargar
	 *            the new archivo cargar
	 */
	public void setArchivoCargar(org.primefaces.model.UploadedFile archivoCargar) {
		this.archivoCargar = archivoCargar;
	}

	/**
	 * Gets the tipo archivo movilidad ve sel.
	 *
	 * @return the tipo archivo movilidad ve sel
	 */
	public String getTipoArchivoMovilidadVESel() {
		return tipoArchivoMovilidadVESel;
	}

	/**
	 * Sets the tipo archivo movilidad ve sel.
	 *
	 * @param tipoArchivoMovilidadVESel
	 *            the new tipo archivo movilidad ve sel
	 */
	public void setTipoArchivoMovilidadVESel(String tipoArchivoMovilidadVESel) {
		this.tipoArchivoMovilidadVESel = tipoArchivoMovilidadVESel;
	}

	/**
	 * Gets the tipo archivo movilidad de sel.
	 *
	 * @return the tipo archivo movilidad de sel
	 */
	public String getTipoArchivoMovilidadDESel() {
		return tipoArchivoMovilidadDESel;
	}

	/**
	 * Sets the tipo archivo movilidad de sel.
	 *
	 * @param tipoArchivoMovilidadDESel
	 *            the new tipo archivo movilidad de sel
	 */
	public void setTipoArchivoMovilidadDESel(String tipoArchivoMovilidadDESel) {
		this.tipoArchivoMovilidadDESel = tipoArchivoMovilidadDESel;
	}

	/**
	 * Gets the tipo archivo movilidad ep sel.
	 *
	 * @return the tipo archivo movilidad ep sel
	 */
	public String getTipoArchivoMovilidadEPSel() {
		return tipoArchivoMovilidadEPSel;
	}

	/**
	 * Sets the tipo archivo movilidad ep sel.
	 *
	 * @param tipoArchivoMovilidadEPSel
	 *            the new tipo archivo movilidad ep sel
	 */
	public void setTipoArchivoMovilidadEPSel(String tipoArchivoMovilidadEPSel) {
		this.tipoArchivoMovilidadEPSel = tipoArchivoMovilidadEPSel;
	}

	/**
	 * Checks if is hab mov da.
	 *
	 * @return true, if is hab mov da
	 */
	public boolean isHabMovDA() {
		return habMovDA;
	}

	/**
	 * Gets the lista archivos movilidad da.
	 *
	 * @return the lista archivos movilidad da
	 */
	public List<ArchivoMovilidad> getListaArchivosMovilidadDA() {
		return listaArchivosMovilidadDA;
	}

	/**
	 * Gets the tipo archivo movilidad da item.
	 *
	 * @return the tipo archivo movilidad da item
	 */
	public List<SelectItem> getTipoArchivoMovilidadDAItem() {
		return tipoArchivoMovilidadDAItem;
	}

	/**
	 * Gets the lista archivos movilidad ea.
	 *
	 * @return the lista archivos movilidad ea
	 */
	public List<ArchivoMovilidad> getListaArchivosMovilidadEA() {
		return listaArchivosMovilidadEA;
	}

	/**
	 * Gets the tipo archivo movilidad ea item.
	 *
	 * @return the tipo archivo movilidad ea item
	 */
	public List<SelectItem> getTipoArchivoMovilidadEAItem() {
		return tipoArchivoMovilidadEAItem;
	}

	/**
	 * Gets the mov doc art.
	 *
	 * @return the mov doc art
	 */
	public MovilidadDocentesArtes getMovDocArt() {
		return movDocArt;
	}

	/**
	 * Sets the mov doc art.
	 *
	 * @param movDocArt
	 *            the new mov doc art
	 */
	public void setMovDocArt(MovilidadDocentesArtes movDocArt) {
		this.movDocArt = movDocArt;
	}

	/**
	 * Gets the mov est art.
	 *
	 * @return the mov est art
	 */
	public MovilidadEstudiantesArtes getMovEstArt() {
		return movEstArt;
	}

	/**
	 * Sets the mov est art.
	 *
	 * @param movEstArt
	 *            the new mov est art
	 */
	public void setMovEstArt(MovilidadEstudiantesArtes movEstArt) {
		this.movEstArt = movEstArt;
	}

	/**
	 * Gets the tipo archivo movilidad da sel.
	 *
	 * @return the tipo archivo movilidad da sel
	 */
	public String getTipoArchivoMovilidadDASel() {
		return tipoArchivoMovilidadDASel;
	}

	/**
	 * Sets the tipo archivo movilidad da sel.
	 *
	 * @param tipoArchivoMovilidadDASel
	 *            the new tipo archivo movilidad da sel
	 */
	public void setTipoArchivoMovilidadDASel(String tipoArchivoMovilidadDASel) {
		this.tipoArchivoMovilidadDASel = tipoArchivoMovilidadDASel;
	}

	/**
	 * Gets the tipo archivo movilidad ea sel.
	 *
	 * @return the tipo archivo movilidad ea sel
	 */
	public String getTipoArchivoMovilidadEASel() {
		return tipoArchivoMovilidadEASel;
	}

	/**
	 * Sets the tipo archivo movilidad ea sel.
	 *
	 * @param tipoArchivoMovilidadEASel
	 *            the new tipo archivo movilidad ea sel
	 */
	public void setTipoArchivoMovilidadEASel(String tipoArchivoMovilidadEASel) {
		this.tipoArchivoMovilidadEASel = tipoArchivoMovilidadEASel;
	}

	/**
	 * Gets the documento seleccionado.
	 *
	 * @return the documento seleccionado
	 */
	public ArchivoMovilidad getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	/**
	 * Sets the documento seleccionado.
	 *
	 * @param documentoSeleccionado
	 *            the new documento seleccionado
	 */
	public void setDocumentoSeleccionado(ArchivoMovilidad documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}

	/**
	 * Gets the documento seleccionado ve.
	 *
	 * @return the documento seleccionado ve
	 */
	public ArchivoMovilidadVE getDocumentoSeleccionadoVE() {
		return documentoSeleccionadoVE;
	}

	/**
	 * Sets the documento seleccionado ve.
	 *
	 * @param documentoSeleccionadoVE
	 *            the new documento seleccionado ve
	 */
	public void setDocumentoSeleccionadoVE(ArchivoMovilidadVE documentoSeleccionadoVE) {
		this.documentoSeleccionadoVE = documentoSeleccionadoVE;
	}

	/**
	 * Gets the documento seleccionado de.
	 *
	 * @return the documento seleccionado de
	 */
	public ArchivoMovilidadDE getDocumentoSeleccionadoDE() {
		return documentoSeleccionadoDE;
	}

	/**
	 * Sets the documento seleccionado de.
	 *
	 * @param documentoSeleccionadoDE
	 *            the new documento seleccionado de
	 */
	public void setDocumentoSeleccionadoDE(ArchivoMovilidadDE documentoSeleccionadoDE) {
		this.documentoSeleccionadoDE = documentoSeleccionadoDE;
	}

	/**
	 * Gets the documento seleccionado ep.
	 *
	 * @return the documento seleccionado ep
	 */
	public ArchivoMovilidadEP getDocumentoSeleccionadoEP() {
		return documentoSeleccionadoEP;
	}

	/**
	 * Sets the documento seleccionado ep.
	 *
	 * @param documentoSeleccionadoEP
	 *            the new documento seleccionado ep
	 */
	public void setDocumentoSeleccionadoEP(ArchivoMovilidadEP documentoSeleccionadoEP) {
		this.documentoSeleccionadoEP = documentoSeleccionadoEP;
	}

	/**
	 * Gets the documento seleccionado seg.
	 *
	 * @return the documento seleccionado seg
	 */
	public ArchivoMovilidad getDocumentoSeleccionadoSeg() {
		return documentoSeleccionadoSeg;
	}

	/**
	 * Sets the documento seleccionado seg.
	 *
	 * @param documentoSeleccionadoSeg
	 *            the new documento seleccionado seg
	 */
	public void setDocumentoSeleccionadoSeg(ArchivoMovilidad documentoSeleccionadoSeg) {
		this.documentoSeleccionadoSeg = documentoSeleccionadoSeg;
	}

	/**
	 * Checks if is habilitar mov ve.
	 *
	 * @return true, if is habilitar mov ve
	 */
	public boolean isHabilitarMovVE() {
		return habilitarMovVE;
	}

	/**
	 * Gets the estado seguimiento.
	 *
	 * @return the estado seguimiento
	 */
	public String getEstadoSeguimiento() {
		return estadoSeguimiento;
	}

	/**
	 * Gets the historico movilidad.
	 *
	 * @return the historico movilidad
	 */
	public List<HistoricoEstadoMovilidad> getHistoricoMovilidad() {
		return historicoMovilidad;
	}

	/**
	 * Gets the lista facultad.
	 *
	 * @return the lista facultad
	 */
	public List<InvestigadorInterno> getListaFacultad() {
		return listaFacultad;
	}

	/**
	 * Gets the lista direccion.
	 *
	 * @return the lista direccion
	 */
	public List<InvestigadorInterno> getListaDireccion() {
		return listaDireccion;
	}

	/**
	 * Checks if is hab mov ea.
	 *
	 * @return the habMovEA
	 */
	public boolean isHabMovEA() {
		return habMovEA;
	}

	public MovilidadVisitantesArtes getMovVisArt() {
		return movVisArt;
	}

	public void setMovVisArt(MovilidadVisitantesArtes movVisArt) {
		this.movVisArt = movVisArt;
	}

	public boolean isHabMovVA() {
		return habMovVA;
	}

	public void setHabMovVA(boolean habMovVA) {
		this.habMovVA = habMovVA;
	}

	public String getEstadoRevisionSeguimiento() {
		return estadoRevisionSeguimiento;
	}

	public String getsEstadoMovilidad() {
		return sEstadoMovilidad;
	}

	public void setsEstadoMovilidad(String sEstadoMovilidad) {
		this.sEstadoMovilidad = sEstadoMovilidad;
	}

	public String getObservacionesSeg() {
		return observacionesSeg;
	}

	public void setObservacionesSeg(String observacionesSeg) {
		this.observacionesSeg = observacionesSeg;
	}

	public String getIdSeg() {
		return idSeg;
	}

	public void setIdSeg(String idSeg) {
		this.idSeg = idSeg;
	}

	public void consultarDevolucion() {
		if (movDocExt != null) {
			observacionesSeg = servicioGeneral.obtenerObjetoXID(MovilidadDocentesExterior.class, idSeg).get(0)
					.getDescripcionRevision();
			// consultarDevolucionDocEventos();
		} else if (movEstPos != null) {
			observacionesSeg = servicioGeneral.obtenerObjetoXID(MovilidadEstudiantesPosgrado.class, idSeg).get(0)
					.getDescripcionRevision();
			// consultarDevolucionEstPosg();
		} else if (movVisExt != null) {
			observacionesSeg = servicioGeneral.obtenerObjetoXID(MovilidadVisitanteExterior.class, idSeg).get(0)
					.getDescripcionRevision();
			// consultarDevolucionVisitantesInt();
		}
	}
}
