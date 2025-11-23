package co.edu.unal.hermes.vista.proyectos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import co.edu.unal.hermes.modelo.Icono;
import co.edu.unal.hermes.vista.ManejadorBase;

public class ManejadorPaginaInicio extends ManejadorBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Icono[] iconos;

	private Icono[] iconosSeccionRegistre;
	private int cantidadSeccionRegistre = 0;

	private Icono[] iconosSeccionSolicite;
	private int cantidadSeccionSolicite = 0;

	private Icono[] iconosSeccionDiligencie;
	private int cantidadSeccionDiligencie = 0;

	private Icono[] iconosSeccionGestioneInv;
	private int cantidadSeccionGestioneInv = 0;

	private Icono[] iconosSeccionGestioneExt;
	private int cantidadSeccionGestioneExt = 0;

	private Icono[] iconosSeccionGestioneInvExt;
	private int cantidadSeccionGestioneInvExt = 0;

	private Icono[] iconosSeccionOtros;
	private int cantidadSeccionOtros = 0;

	private Icono[] iconosSeccionConsulte;
	private int cantidadSeccionConsulte = 0;
	
	private Icono[] iconosSeccionGestioneEditorial;
	private int cantidadSeccionEditorial = 0;

	private Icono iconoAccion;

	private ArrayList<Icono> iconosAgregados;

	// CONSTRUCTOR
	public ManejadorPaginaInicio() {
		try {
			inicilizarReglasNavegacion();
			cargarIconos();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void cargarIconos() {
		iconosAgregados = new ArrayList<Icono>();
		boolean esInvestigador = (Boolean) sesion.getAttribute("esInvestigador");
		boolean esDireccion = (Boolean) sesion.getAttribute("esDireccion");
		boolean esCoordinador = (Boolean) sesion.getAttribute("esCoordinador2");
		boolean esUnidadAdministrativa = (Boolean) sesion.getAttribute("esUnidadAdministrativa");
		boolean esUnidadAdministrativaExtension = (Boolean) sesion.getAttribute("esUnidadAdministrativaExtension");
		boolean esMovilidadFacultad = (Boolean) sesion.getAttribute("esMovilidadFacultad");
		boolean esMovilidadSede = (Boolean) sesion.getAttribute("esMovilidadSede");
		boolean esDNE = (Boolean) sesion.getAttribute("esDNE");
		boolean esCursosFormacion = (Boolean) sesion.getAttribute("esCursosFormacion");
		boolean esFacultad = (Boolean) sesion.getAttribute("esFacultad");
		boolean esEgresado = (Boolean) sesion.getAttribute("esEgresado");
		boolean esVicerrectoria = (Boolean) sesion.getAttribute("esVicerrectoria");
		boolean esVicerrectoriaGrupos = (Boolean) sesion.getAttribute("esVicerrectoriaGrupo");
		boolean esLaboratorios = (Boolean) sesion.getAttribute("esLaboratorios");
		boolean esLaboratoriosSede = (Boolean) sesion.getAttribute("esLaboratoriosSede");
		boolean esCoordinadorLaboratorio = (Boolean) sesion.getAttribute("esCoordinadorLaboratorio");
		boolean esPersonalLaboratorio = (Boolean) sesion.getAttribute("esPersonalLaboratorio");
		boolean esLaboratoriosFacultad = (Boolean) sesion.getAttribute("esLaboratoriosFacultad");
		boolean esLaboratoriosDepto = (Boolean) sesion.getAttribute("esLaboratoriosDepto");
		boolean esConsultaLaboratorios = (Boolean) sesion.getAttribute("esConsultaLaboratorios");
		boolean esInventariosLaboratorios = (Boolean) sesion.getAttribute("esInventariosLaboratorios");
		boolean esAsesor = (Boolean) sesion.getAttribute("esAsesor");
		boolean esEvaluador = (Boolean) sesion.getAttribute("esEvaluador");
		boolean esOficinaExtension = (Boolean) sesion.getAttribute("esOficinaExtension");
		boolean esComunicaciones = (Boolean) sesion.getAttribute("esComunicaciones");
//		boolean esEstudianteLider = false;
//		boolean esAsistenteAdministrativoLider = false;
		boolean esEstudianteLider = (Boolean) sesion.getAttribute("esEstudianteLider");
		boolean esAsistenteLider = (Boolean) sesion.getAttribute("esAsistenteLider");
		boolean esAvalDepartamentoUAB = (Boolean) sesion.getAttribute("esAvalUab");
		boolean esAvalComiteEticaPI = (Boolean) sesion.getAttribute("esComiteEticaPI");
		boolean esAvalComiteEticaSI = (Boolean) sesion.getAttribute("esComiteEticaSI");
		boolean esDocumentosVice = (Boolean) sesion.getAttribute("esDocumentosVice");
		boolean esDiagnosticoSoftware = (Boolean) sesion.getAttribute("esDiagnosticoSoftware");
		boolean esEducacionContinuaFacultad = (Boolean) sesion.getAttribute("esEducacionContinuaFacultad");
		boolean esEditorRevista = (Boolean) sesion.getAttribute("esEditorRevista");
		boolean esEditorial = (Boolean) sesion.getAttribute("esEditorial");
		boolean esCoordinadorEditorial = (Boolean) sesion.getAttribute("esCoordinadorEditorial");
		boolean esCurador = (Boolean) sesion.getAttribute("esCurador");
		boolean esRequerimiento = (Boolean) sesion.getAttribute("esRequerimiento");
		boolean esAdmRequerimiento = (Boolean) sesion.getAttribute("esAdmRequerimiento");
		boolean esDecano = (Boolean) sesion.getAttribute("esDecano");
		boolean esIndicadores = (Boolean) sesion.getAttribute("esIndicadores");
		boolean esVicerector = (Boolean) sesion.getAttribute("esVicerector");
		boolean esBecaDoctorado = (Boolean) sesion.getAttribute("esBecadoDoctorado");
		boolean esInnovacion = (Boolean) sesion.getAttribute("esInnovacion");
		boolean esPropiedadIntelectualNacional = (Boolean) sesion.getAttribute("esPropiedadIntelectualNacional");
		boolean esJovenInvestigadorInformes = (Boolean) sesion.getAttribute("esJovenInvestigadorInformes");
		boolean esAdmSolUsuario = (Boolean) sesion.getAttribute("esAdmSolUsuario");
		boolean esCrSolUsuario = (Boolean) sesion.getAttribute("esCrSolUsuario");
		boolean esAdministradorConvocatorias = (Boolean) sesion.getAttribute("esAdministradorConvocatorias");
		boolean esConsulta = (Boolean) sesion.getAttribute("esConsulta");
		boolean esAdministradorHermes = (Boolean) sesion.getAttribute("esAdministradorHermes");
		boolean esPropiedadIntelectualSede = (Boolean) sesion.getAttribute("esPropiedadIntelectualSede");
		boolean esPropiedadIntelectual = (Boolean) sesion.getAttribute("esPropiedadIntelectual");
		boolean esConsultaRequerimientos = (Boolean) sesion.getAttribute("esConsultaRequerimientos");
		boolean esAsesorEditorial = (Boolean) sesion.getAttribute("esAsesorEditorial");
		boolean esAvalDRE = (Boolean) sesion.getAttribute("esAvalDRE");
		boolean esRectoria = (Boolean) sesion.getAttribute("esRectoria");
		boolean esEditorialUN = (Boolean) sesion.getAttribute("esEditorialUN");

//		try {
//			esEstudianteLider = (Boolean) sesion.getAttribute("esEstudianteLider");
//		} catch (NullPointerException n) {
//			esEstudianteLider = false;
//		}
//		
//		try {
//			esAsistenteAdministrativoLider = (Boolean) sesion.getAttribute("esAsistenteLider");
//		} catch (NullPointerException n) {
//			esAsistenteAdministrativoLider = false;
//		}
		boolean muestraSAECP = false;

		// Para agregar un ícono por favor indique: En nombre a mostrar en la
		// página inicial, la regla de navegación a la que se dirige
		// y la sección en la cual se debe ubicar e incremente el contador de
		// íconos de la respectiva sección.
		//

		if (esInvestigador) {
			iconosAgregados.add(new Icono("Convocatorias internas (SIUN)", "consultarConvocatorias", Icono.SECCION_REGISTRE));
			cantidadSeccionRegistre = cantidadSeccionRegistre + 1;

			iconosAgregados.add(new Icono("Hoja de vida docentes", "hojaVidaDocente", Icono.SECCION_DILIGENCIE));
			iconosAgregados.add(new Icono("Permisos de investigación de biodiversidad", "tramitesBiodiversidad", Icono.SECCION_DILIGENCIE));
			//iconosAgregados.add(new Icono("Solicitudes de biodiversidad", "successBiodiversidad", Icono.SECCION_DILIGENCIE));
			//iconosAgregados.add(new Icono("Autorizaciones de biodiversidad", "convenioMarco", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 2;

			iconosAgregados.add(new Icono("Avales", "successProyectosAval", Icono.SECCION_SOLICITE));
			iconosAgregados.add(new Icono("Proyectos y convocatorias editoriales", "irVerTodasSolicitudesEditorial", Icono.SECCION_SOLICITE));
			iconosAgregados.add(new Icono("Certificaciones", "irCertificaciones", Icono.SECCION_SOLICITE));
			iconosAgregados.add(new Icono("Propiedad Intelectual", "irPropiedadIntelectual", Icono.SECCION_SOLICITE));
			cantidadSeccionSolicite = cantidadSeccionSolicite + 4;

			iconosAgregados.add(new Icono("Documentos SIUN", "documentosVicerrectoria", Icono.SECCION_CONSULTE));
			cantidadSeccionConsulte++;
			iconosAgregados.add(new Icono("Movilidades Internacionalización", "successProyectosMovilidad",Icono.SECCION_CONSULTE));
			cantidadSeccionConsulte++;
			iconosAgregados.add(new Icono("Búsqueda de Equipos", "hojaDeVidaEquipos", Icono.SECCION_CONSULTE));
			cantidadSeccionConsulte++;

			muestraSAECP = true;

			iconosAgregados.add(new Icono("Proyectos", "successProyectosProyecto", Icono.SECCION_REGISTRE));
			cantidadSeccionRegistre = cantidadSeccionRegistre + 1;
			iconosAgregados.add(new Icono("Semilleros", "semillerosHome", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 1;
			iconosAgregados.add(new Icono("Grupos", "consultaGruposPrincipal", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 1;
		}
		
		if (!esInvestigador && (esAsistenteLider || esEstudianteLider)) {			
			iconosAgregados.add(new Icono("Proyectos", "successProyectosProyecto", Icono.SECCION_REGISTRE));
			cantidadSeccionRegistre = cantidadSeccionRegistre + 1;
			iconosAgregados.add(new Icono("Semilleros", "semillerosHome", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 1;
			iconosAgregados.add(new Icono("Grupos", "consultaGruposPrincipal", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 1;
		}
		
		if (esRectoria) {
			iconosAgregados.add(new Icono("Rectoria", "rectoria",
					Icono.SECCION_GESTIONE_INVESTIGACION));
			cantidadSeccionGestioneInv = cantidadSeccionGestioneInv + 1;
		}
		
		if (esAvalDRE) {
			iconosAgregados.add(new Icono("Dir. Relaciones Exteriores", "avalarDRE",
					Icono.SECCION_GESTIONE_INVESTIGACION));
			cantidadSeccionGestioneInv = cantidadSeccionGestioneInv + 1;
		}
		
		if (esRequerimiento) {
			iconosAgregados
					.add(new Icono("Requerimientos Asignados", "consultarTodosReqAsignados", Icono.SECCION_OTROS));
			cantidadSeccionOtros = cantidadSeccionOtros + 1;
		}
		
		if (esAdmRequerimiento) {
			iconosAgregados
					.add(new Icono("Administrar Requerimientos", "AdministrarRequerimientos", Icono.SECCION_OTROS));
			cantidadSeccionOtros = cantidadSeccionOtros + 1;
		}
		if (esVicerrectoria) {
			iconosAgregados.add(
					new Icono("Vicerrectoría de investigación", "avalarVice", Icono.SECCION_GESTIONE_INVESTIGACION));
			cantidadSeccionGestioneInv = cantidadSeccionGestioneInv + 1;
		}
		if (esDireccion) {
			iconosAgregados.add(
					new Icono("Dirección de Investigación", "avalarDireccion", Icono.SECCION_GESTIONE_INVESTIGACION));
			cantidadSeccionGestioneInv = cantidadSeccionGestioneInv + 1;
		}
		if (esFacultad) {
			iconosAgregados.add(new Icono("Vicedecanatura de Investigación", "avalarFacultad",
					Icono.SECCION_GESTIONE_INVESTIGACION));
			cantidadSeccionGestioneInv = cantidadSeccionGestioneInv + 1;
		}
		if (esMovilidadFacultad) {
			iconosAgregados.add(
					new Icono("Movilidades / Facultad", "listadoAprobacion", Icono.SECCION_GESTIONE_INVESTIGACION));
			cantidadSeccionGestioneInv = cantidadSeccionGestioneInv + 1;
		}
		if (esMovilidadSede) {
			iconosAgregados.add(
					new Icono("Movilidades / Sede", "listadoAprobacionSede", Icono.SECCION_GESTIONE_INVESTIGACION));
			cantidadSeccionGestioneInv = cantidadSeccionGestioneInv + 1;
		}
		if (esAsesor) {
			iconosAgregados.add(new Icono("Asesor", "asociarCoordinadores", Icono.SECCION_GESTIONE_INVESTIGACION));
			cantidadSeccionGestioneInv = cantidadSeccionGestioneInv + 1;
		}
		if (esEvaluador) {
			iconosAgregados.add(new Icono("Evaluador", "evaluarProyectos", Icono.SECCION_GESTIONE_INVESTIGACION));
			cantidadSeccionGestioneInv = cantidadSeccionGestioneInv + 1;
		}
		if (esCoordinador) {
			iconosAgregados.add(new Icono("Coordinador", "inbox", Icono.SECCION_GESTIONE_INVESTIGACION));
			cantidadSeccionGestioneInv = cantidadSeccionGestioneInv + 1;
		}
		if (esEgresado) {
			iconosAgregados.add(new Icono("Egresado", "irEgresado", Icono.SECCION_REGISTRE));
			cantidadSeccionRegistre = cantidadSeccionRegistre + 1;
		}

		if (esLaboratorios) {
			iconosAgregados.add(
					new Icono("Sistema Nacional de Laboratorios", "AdministrarLaboratorios", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 1;
			iconosAgregados.add(new Icono("Búsqueda de Equipos", "hojaDeVidaEquipos", Icono.SECCION_CONSULTE));
			cantidadSeccionConsulte++;
		}
		if (esLaboratoriosSede) {
			iconosAgregados
					.add(new Icono("Dir. Laboratorios Sede", "AdministrarLaboratorios", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 1;
			iconosAgregados.add(new Icono("Búsqueda de Equipos", "hojaDeVidaEquipos", Icono.SECCION_CONSULTE));
			cantidadSeccionConsulte++;
		}
		if (esCoordinadorLaboratorio) {
			iconosAgregados.add(new Icono("Coordinador Laboratorio", "AdministrarLaboratorios", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 1;
			iconosAgregados.add(new Icono("Búsqueda de Equipos", "hojaDeVidaEquipos", Icono.SECCION_CONSULTE));
			cantidadSeccionConsulte++;
		}
		if (esPersonalLaboratorio) {
			iconosAgregados.add(new Icono("Personal Laboratorio", "AdministrarLaboratoriosRolInterno", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 1;
			iconosAgregados.add(new Icono("Búsqueda de Equipos", "hojaDeVidaEquipos", Icono.SECCION_CONSULTE));
			cantidadSeccionConsulte++;
		}
		if (esLaboratoriosFacultad) {
			iconosAgregados
					.add(new Icono("Laboratorios Facultad", "AdministrarLaboratorios", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 1;
			iconosAgregados.add(new Icono("Búsqueda de Equipos", "hojaDeVidaEquipos", Icono.SECCION_CONSULTE));
			cantidadSeccionConsulte++;
		}
		if (esLaboratoriosDepto) {
			iconosAgregados
					.add(new Icono("Laboratorios Departamento", "AdministrarLaboratorios", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 1;
			iconosAgregados.add(new Icono("Búsqueda de Equipos", "hojaDeVidaEquipos", Icono.SECCION_CONSULTE));
			cantidadSeccionConsulte++;
		}
		if (esConsultaLaboratorios) {
			iconosAgregados
					.add(new Icono("Consulta Laboratorios", "AdministrarLaboratorios", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 1;
		}
		if (esConsultaRequerimientos) {
			iconosAgregados
					.add(new Icono("Consulta Requerimientos", "AdministrarRequerimientos", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 1;
		}
		if (esInventariosLaboratorios) {
			iconosAgregados
					.add(new Icono("Inventarios Laboratorios", "inventariosLaboratorios", Icono.SECCION_DILIGENCIE));
		}
		if (esCurador) {
			iconosAgregados.add(new Icono("Colecciones científicas", "colecciones", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 1;
		}
		if (esComunicaciones) {
			iconosAgregados.add(new Icono("Comunicaciones", "enviarCorreoBoletin", Icono.SECCION_OTROS));
			cantidadSeccionOtros = cantidadSeccionOtros + 1;
		}
		if (esPropiedadIntelectualNacional) {// Si se cambia el nombre controlar
												// la acción para que liste los
												// de nivel nacional
			iconosAgregados.add(new Icono("Propiedad Intelectual nivel Nacional", "dinipi",
					Icono.SECCION_GESTIONE_INVESTIGACION_EXTENSION));
			cantidadSeccionGestioneInvExt = cantidadSeccionGestioneInvExt + 1;
		}
		if (esUnidadAdministrativa) {
			iconosAgregados.add(new Icono("Unidad Administrativa Investigación", "inboxUnidadAdministrativa",
					Icono.SECCION_GESTIONE_INVESTIGACION));
			cantidadSeccionGestioneInv = cantidadSeccionGestioneInv + 1;
		}
		// if(esUnidadAdministrativaExtension){
		// iconosAgregados.add(new Icono("Unidad Administrativa Extensión",
		// "inboxUnidadAdministrativaExtension",Icono.SECCION_GESTIONE_EXTENSION));
		// cantidadSeccionGestioneExt=cantidadSeccionGestioneExt+1;
		// }
		if (esDNE) {
			iconosAgregados.add(new Icono("Dir. Nal. de Extensión", "AdministrarConvocatoriaExtension",
					Icono.SECCION_GESTIONE_EXTENSION));
			cantidadSeccionGestioneExt = cantidadSeccionGestioneExt + 1;
		}
		// if(esCentroExtension){
		// if(!muestraSAECP){
		// iconosAgregados.add( new Icono("Servicios de extensión",
		// "irExtension",Icono.SECCION_SOLICITE));
		// cantidadSeccionSolicite=cantidadSeccionSolicite+1;
		// }
		// }
		if (esOficinaExtension) {
			iconosAgregados.add(new Icono("Oficina Extensión / Facultad", "ConsultarConvocatoriaExtension",
					Icono.SECCION_GESTIONE_EXTENSION));
			cantidadSeccionGestioneExt = cantidadSeccionGestioneExt + 1;
		}
		if (esCursosFormacion) {
			iconosAgregados.add(new Icono("Cursos de Formación", "principalCursosFormacion", Icono.SECCION_DILIGENCIE));
			cantidadSeccionDiligencie = cantidadSeccionDiligencie + 1;
		}
		if (esAvalDepartamentoUAB) {
			iconosAgregados.add(new Icono("Director de UAB", "AprobarAval", Icono.SECCION_GESTIONE_INVESTIGACION));
			cantidadSeccionGestioneInv = cantidadSeccionGestioneInv + 1;
		}
		if (esAvalComiteEticaPI) {
			iconosAgregados.add(new Icono("Comité Ética 1ra Instancia", "avalarCEPI", Icono.SECCION_GESTIONE_INVESTIGACION));
			cantidadSeccionGestioneInv = cantidadSeccionGestioneInv + 1;
		}
		if (esAvalComiteEticaSI) {
			iconosAgregados.add(new Icono("Comité Ética 2da Instancia", "avalarCESI", Icono.SECCION_GESTIONE_INVESTIGACION));
			cantidadSeccionGestioneInv = cantidadSeccionGestioneInv + 1;
		}
		if (esDiagnosticoSoftware) {
			iconosAgregados.add(new Icono("Diagnóstico Software", "encuestaDiagnosticoSoftware", Icono.SECCION_OTROS));
			cantidadSeccionOtros = cantidadSeccionOtros + 1;
		}
		if (esEducacionContinuaFacultad) {
			iconosAgregados.add(new Icono("Cursos", "listaCursosECP", Icono.SECCION_OTROS));
			cantidadSeccionOtros = cantidadSeccionOtros + 1;
		}
		if (esEditorRevista && !esInvestigador) {
			iconosAgregados.add(new Icono("Proyectos", "successProyectosProyecto", Icono.SECCION_REGISTRE));
			iconosAgregados
					.add(new Icono("Convocatorias internas (SIUN)", "consultarConvocatorias", Icono.SECCION_REGISTRE));
			cantidadSeccionRegistre = cantidadSeccionRegistre + 2;
		}
		if ((esEditorial && !esInvestigador) || (esEditorial && esInvestigador && esEstudianteLider)) {
			iconosAgregados.add(new Icono("Proyectos y convocatorias editoriales", "irVerTodasSolicitudesEditorial", Icono.SECCION_SOLICITE));
			cantidadSeccionSolicite = cantidadSeccionSolicite + 1;
		}
		if (esCoordinadorEditorial) {
			iconosAgregados.add(new Icono("Coordinador Editorial", "irCoordinadorEditorial", Icono.SECCION_GESTIONE_EDITORIAL));
			cantidadSeccionEditorial = cantidadSeccionEditorial + 1;
		}
		if (esAsesorEditorial) {
			iconosAgregados.add(new Icono("Asesor Editorial", "irAsesorEditorial", Icono.SECCION_GESTIONE_EDITORIAL));
			cantidadSeccionEditorial = cantidadSeccionEditorial + 1;
		}
		if (esEditorialUN) {
			iconosAgregados.add(new Icono("Editorial UN", "irEditorialUN", Icono.SECCION_GESTIONE_EDITORIAL));
			cantidadSeccionEditorial = cantidadSeccionEditorial + 1;
		}
		if (esDecano || esIndicadores || esVicerector) {
			iconosAgregados
					.add(new Icono("Indicadores", "indicadoresSAC", Icono.SECCION_GESTIONE_INVESTIGACION_EXTENSION));
			cantidadSeccionGestioneInvExt = cantidadSeccionGestioneInvExt + 1;
			// iconosAgregados.add( new Icono("Supervisión",
			// "interventoriaSAC",Icono.SECCION_GESTIONE_EXTENSION));
			// cantidadSeccionGestioneExt=cantidadSeccionGestioneExt+1;
		}
		/*
		 * if(esIndicadores){ iconosAgregados.add( new Icono("Indicadores",
		 * "indicadoresSAC","")); iconosAgregados.add( new Icono("Supervisión",
		 * "interventoriaSAC","")); } if(esVicerector){ iconosAgregados.add( new
		 * Icono("Indicadores", "indicadoresSAC","")); iconosAgregados.add( new
		 * Icono("Supervisión", "interventoriaSAC","")); }
		 */
		if (esDocumentosVice) {
			iconosAgregados.add(new Icono("Administrar Documentos", "documentosVicerrectoriaAdmin",
					Icono.SECCION_GESTIONE_INVESTIGACION_EXTENSION));
			cantidadSeccionGestioneInvExt = cantidadSeccionGestioneInvExt + 1;
		}
		if (esBecaDoctorado) {
			iconosAgregados.add(new Icono("Solicitud renovación", "solicitudRenovacion", Icono.SECCION_OTROS));
			cantidadSeccionOtros = cantidadSeccionOtros + 1;
		}
		if (esInnovacion) {
			iconosAgregados.add(new Icono("Innovación", "irInnovacion", Icono.SECCION_SOLICITE));
			cantidadSeccionSolicite = cantidadSeccionSolicite + 1;
		}
		if (esPropiedadIntelectual/* && !esInvestigador */) {
			iconosAgregados
					.add(new Icono("Gestor Propiedad Intelectual", "irPropiedadIntelectual", Icono.SECCION_SOLICITE));
			cantidadSeccionSolicite = cantidadSeccionSolicite + 1;
		}
		if (esJovenInvestigadorInformes) {
			iconosAgregados
					.add(new Icono("Informes joven investigador", "informesJovenInvestigador", Icono.SECCION_OTROS));
			cantidadSeccionOtros = cantidadSeccionOtros + 1;
		}
		if (esAdmSolUsuario) {
			iconosAgregados.add(new Icono("Administrar Solicitudes Usuario", "consultaTodasSolicitudesUsuariosAdm",
					Icono.SECCION_OTROS));
			cantidadSeccionOtros = cantidadSeccionOtros + 1;
		}
		if (esCrSolUsuario) {
			iconosAgregados.add(new Icono("Crear Solicitudes Usuario", "crSolUsuario", Icono.SECCION_OTROS));
			cantidadSeccionOtros = cantidadSeccionOtros + 1;
		}
		if (esAdministradorConvocatorias) {
			iconosAgregados.add(new Icono("Administrador convocatorias", "irAdministrador", Icono.SECCION_OTROS));
			cantidadSeccionOtros = cantidadSeccionOtros + 1;
		}
		if (esConsulta) {
			iconosAgregados.add(new Icono("Consulta", "irConsulta", Icono.SECCION_OTROS));
			cantidadSeccionOtros = cantidadSeccionOtros + 1;
		}

		if (esAdministradorHermes) {
			iconosAgregados.add(new Icono("Administrador Hermes", "irAdministracionHermes", Icono.SECCION_OTROS));
			cantidadSeccionOtros = cantidadSeccionOtros + 1;
		}
		if (esPropiedadIntelectualSede) { // Si se cambia el nombre se debe
											// controlar la acción del ícono
											// para que si muestre el listado de
											// sede
			iconosAgregados.add(new Icono("Propiedad Intelectual nivel Sede", "irPropiedadIntelectualSede",
					Icono.SECCION_GESTIONE_INVESTIGACION_EXTENSION));
			cantidadSeccionGestioneInvExt = cantidadSeccionGestioneInvExt + 1;
		}

		iconos = new Icono[iconosAgregados.size()];
		iconosSeccionRegistre = new Icono[cantidadSeccionRegistre];
		iconosSeccionSolicite = new Icono[cantidadSeccionSolicite];
		iconosSeccionDiligencie = new Icono[cantidadSeccionDiligencie];
		iconosSeccionGestioneInv = new Icono[cantidadSeccionGestioneInv];
		iconosSeccionGestioneExt = new Icono[cantidadSeccionGestioneExt];
		iconosSeccionGestioneInvExt = new Icono[cantidadSeccionGestioneInvExt];
		iconosSeccionOtros = new Icono[cantidadSeccionOtros];
		iconosSeccionConsulte = new Icono[cantidadSeccionConsulte];
		iconosSeccionGestioneEditorial = new Icono[cantidadSeccionEditorial];

		int c = 0;
		int a = 0;
		int d = 0;
		int in = 0;
		int e = 0;
		int ie = 0;
		int doc = 0;
		int o = 0;
		int ed = 0;

		for (int i = 0; i < iconosAgregados.size(); i++) {
			iconos[i] = iconosAgregados.get(i);
			if (iconosAgregados.get(i).getCategoria() == Icono.SECCION_REGISTRE) {
				iconosSeccionRegistre[c] = iconosAgregados.get(i);
				c++;
			} else if (iconosAgregados.get(i).getCategoria() == Icono.SECCION_SOLICITE) {
				iconosSeccionSolicite[a] = iconosAgregados.get(i);
				a++;
			} else if (iconosAgregados.get(i).getCategoria() == Icono.SECCION_DILIGENCIE) {
				iconosSeccionDiligencie[d] = iconosAgregados.get(i);
				d++;
			} else if (iconosAgregados.get(i).getCategoria() == Icono.SECCION_GESTIONE_INVESTIGACION) {
				iconosSeccionGestioneInv[in] = iconosAgregados.get(i);
				in++;
			} else if (iconosAgregados.get(i).getCategoria() == Icono.SECCION_GESTIONE_EXTENSION) {
				iconosSeccionGestioneExt[e] = iconosAgregados.get(i);
				e++;
			} else if (iconosAgregados.get(i).getCategoria() == Icono.SECCION_GESTIONE_INVESTIGACION_EXTENSION) {
				iconosSeccionGestioneInvExt[ie] = iconosAgregados.get(i);
				ie++;
			} else if (iconosAgregados.get(i).getCategoria() == Icono.SECCION_CONSULTE) {
				iconosSeccionConsulte[doc] = iconosAgregados.get(i);
				doc++;
			} else if (iconosAgregados.get(i).getCategoria() == Icono.SECCION_OTROS) {
				iconosSeccionOtros[o] = iconosAgregados.get(i);
				o++;
			} else if (iconosAgregados.get(i).getCategoria() == Icono.SECCION_GESTIONE_EDITORIAL) {
				iconosSeccionGestioneEditorial[ed] = iconosAgregados.get(i);
				ed++;
			}
		}
		
		iconosSeccionConsulte = eliminarIconosDuplicados(iconosSeccionConsulte);
	}
	
	public static Icono[] eliminarIconosDuplicados(Icono[] arreglo) {
        List<Icono> resultado = new ArrayList<Icono>();

        for (int i = 0; i < arreglo.length; i++) {
            Icono actual = arreglo[i];
            boolean yaExiste = false;

            // Verificar si ya fue agregado
            for (int j = 0; j < resultado.size(); j++) {
                Icono existente = resultado.get(j);
                // Aquí comparas la propiedad clave (puede ser id, nombre, etc.)
                if (actual.getNombre() != null && actual.getNombre().equals(existente.getNombre())) {
                    yaExiste = true;
                    break;
                }
            }

            if (!yaExiste) {
                resultado.add(actual);
            }
        }

        // Convertir lista a arreglo
        Icono[] sinDuplicados = new Icono[resultado.size()];
        return resultado.toArray(sinDuplicados);
    }

	public String accionIcono() {
		String nombreManejador = "";

		if (iconoAccion.getAccion().equals("irPropiedadIntelectualSede")) {
			sesion.setAttribute("rolIngresoPropiedadIntelectual", "S");
		} else if (iconoAccion.getAccion().equals("dinipi")) {
			sesion.setAttribute("rolIngresoPropiedadIntelectual", "N");
		} else if (iconoAccion.getNombre().equals("Informes joven investigador")) {
			nombreManejador = "ManejadorPrincipalJovenesInvestigadores";
		} else if (iconoAccion.getNombre().equals("Búsqueda de Equipos")) {
			sesion.setAttribute("esBusquedaEquiposServicioInvestigador", true);
		} else if(iconoAccion.getNombre().equals("Sistema Nacional de Laboratorios")) {
			sesion.setAttribute("rolSeleccionadoLabs", "DL");
			nombreManejador = "ManejadorAdministrarLaboratorios";
		} else if(iconoAccion.getNombre().equals("Dir. Laboratorios Sede")) {
			sesion.setAttribute("rolSeleccionadoLabs", "LS");
			nombreManejador = "ManejadorAdministrarLaboratorios";
		} else if(iconoAccion.getNombre().equals("Coordinador Laboratorio")) {
			sesion.setAttribute("rolSeleccionadoLabs", "CO");
			nombreManejador = "ManejadorAdministrarLaboratorios";
		} else if(iconoAccion.getNombre().equals("Personal Laboratorio")) {
			sesion.setAttribute("rolSeleccionadoLabs", "PL");
			nombreManejador = "ManejadorAdministrarLaboratoriosRolInterno";
		} else if(iconoAccion.getNombre().equals("Laboratorios Facultad")) {
			sesion.setAttribute("rolSeleccionadoLabs", "LF");
			nombreManejador = "ManejadorAdministrarLaboratorios";
		} else if(iconoAccion.getNombre().equals("Laboratorios Departamento")) {
			sesion.setAttribute("rolSeleccionadoLabs", "LD");
			nombreManejador = "ManejadorAdministrarLaboratorios";
		} else if(iconoAccion.getNombre().equals("Consulta Laboratorios")) {
			sesion.setAttribute("rolSeleccionadoLabs", "CL");
			nombreManejador = "ManejadorAdministrarLaboratorios";
		}
		
		sesion.removeAttribute(nombreManejador);
		agregarReglaNavegacion(iconoAccion.accion(), nombreManejador);
		return iconoAccion.accion();
	}

	public Icono[] getIconos() {
		return iconos;
	}

	public void setIconos(Icono[] iconos) {
		this.iconos = iconos;
	}

	public Icono getIconoAccion() {
		return iconoAccion;
	}

	public void setIconoAccion(Icono iconoAccion) {
		this.iconoAccion = iconoAccion;
	}

	public Icono[] getIconosSeccionRegistre() {
		return iconosSeccionRegistre;
	}

	public void setIconosSeccionRegistre(Icono[] iconosSeccionRegistre) {
		this.iconosSeccionRegistre = iconosSeccionRegistre;
	}

	public int getCantidadSeccionRegistre() {
		return cantidadSeccionRegistre;
	}

	public void setCantidadSeccionRegistre(int cantidadSeccionRegistre) {
		this.cantidadSeccionRegistre = cantidadSeccionRegistre;
	}

	public Icono[] getIconosSeccionSolicite() {
		return iconosSeccionSolicite;
	}

	public void setIconosSeccionSolicite(Icono[] iconosSeccionSolicite) {
		this.iconosSeccionSolicite = iconosSeccionSolicite;
	}

	public int getCantidadSeccionSolicite() {
		return cantidadSeccionSolicite;
	}

	public void setCantidadSeccionSolicite(int cantidadSeccionSolicite) {
		this.cantidadSeccionSolicite = cantidadSeccionSolicite;
	}

	public Icono[] getIconosSeccionDiligencie() {
		return iconosSeccionDiligencie;
	}

	public void setIconosSeccionDiligencie(Icono[] iconosSeccionDiligencie) {
		this.iconosSeccionDiligencie = iconosSeccionDiligencie;
	}

	public int getCantidadSeccionDiligencie() {
		return cantidadSeccionDiligencie;
	}

	public void setCantidadSeccionDiligencie(int cantidadSeccionDiligencie) {
		this.cantidadSeccionDiligencie = cantidadSeccionDiligencie;
	}

	public Icono[] getIconosSeccionGestioneInv() {
		return iconosSeccionGestioneInv;
	}

	public void setIconosSeccionGestioneInv(Icono[] iconosSeccionGestioneInv) {
		this.iconosSeccionGestioneInv = iconosSeccionGestioneInv;
	}

	public int getCantidadSeccionGestioneInv() {
		return cantidadSeccionGestioneInv;
	}

	public void setCantidadSeccionGestioneInv(int cantidadSeccionGestioneInv) {
		this.cantidadSeccionGestioneInv = cantidadSeccionGestioneInv;
	}

	public Icono[] getIconosSeccionGestioneExt() {
		return iconosSeccionGestioneExt;
	}

	public void setIconosSeccionGestioneExt(Icono[] iconosSeccionGestioneExt) {
		this.iconosSeccionGestioneExt = iconosSeccionGestioneExt;
	}

	public int getCantidadSeccionGestioneExt() {
		return cantidadSeccionGestioneExt;
	}

	public void setCantidadSeccionGestioneExt(int cantidadSeccionGestioneExt) {
		this.cantidadSeccionGestioneExt = cantidadSeccionGestioneExt;
	}

	public Icono[] getIconosSeccionGestioneInvExt() {
		return iconosSeccionGestioneInvExt;
	}

	public void setIconosSeccionGestioneInvExt(Icono[] iconosSeccionGestioneInvExt) {
		this.iconosSeccionGestioneInvExt = iconosSeccionGestioneInvExt;
	}

	public int getCantidadSeccionGestioneInvExt() {
		return cantidadSeccionGestioneInvExt;
	}

	public void setCantidadSeccionGestioneInvExt(int cantidadSeccionGestioneInvExt) {
		this.cantidadSeccionGestioneInvExt = cantidadSeccionGestioneInvExt;
	}

	public Icono[] getIconosSeccionOtros() {
		return iconosSeccionOtros;
	}

	public void setIconosSeccionOtros(Icono[] iconosSeccionOtros) {
		this.iconosSeccionOtros = iconosSeccionOtros;
	}

	public int getCantidadSeccionOtros() {
		return cantidadSeccionOtros;
	}

	public void setCantidadSeccionOtros(int cantidadSeccionOtros) {
		this.cantidadSeccionOtros = cantidadSeccionOtros;
	}

	public Icono[] getIconosSeccionConsulte() {
		return iconosSeccionConsulte;
	}

	public void setIconosSeccionConsulte(Icono[] iconosSeccionConsulte) {
		this.iconosSeccionConsulte = iconosSeccionConsulte;
	}

	public int getCantidadSeccionConsulte() {
		return cantidadSeccionConsulte;
	}

	public void setCantidadSeccionConsulte(int cantidadSeccionConsulte) {
		this.cantidadSeccionConsulte = cantidadSeccionConsulte;
	}

	public ArrayList<Icono> getIconosAgregados() {
		return iconosAgregados;
	}

	public void setIconosAgregados(ArrayList<Icono> iconosAgregados) {
		this.iconosAgregados = iconosAgregados;
	}

	public Icono[] getIconosSeccionGestioneEditorial() {
		return iconosSeccionGestioneEditorial;
	}

	public void setIconosSeccionGestioneEditorial(Icono[] iconosSeccionGestioneEditorial) {
		this.iconosSeccionGestioneEditorial = iconosSeccionGestioneEditorial;
	}

	public int getCantidadSeccionEditorial() {
		return cantidadSeccionEditorial;
	}

	public void setCantidadSeccionEditorial(int cantidadSeccionEditorial) {
		this.cantidadSeccionEditorial = cantidadSeccionEditorial;
	}

}
