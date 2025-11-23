package co.edu.unal.hermes.vista.movilidad;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import co.edu.unal.hermes.modelo.ArchivoMovilidad;
import co.edu.unal.hermes.modelo.ArchivoMovilidadDE;
import co.edu.unal.hermes.modelo.ArchivoMovilidadEP;
import co.edu.unal.hermes.modelo.ArchivoMovilidadVE;
import co.edu.unal.hermes.modelo.MovilidadDocentesArtes;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesArtes;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.utils.Navegacion;

public class ManejadorConsultaMovilidadesInvestigador extends ManejadorBaseMovilidad {

	private static final long serialVersionUID = -3893273107939742377L;

	private List<MovilidadVisitanteExterior> listaMovilidadesVisitante;
	private List<MovilidadVisitanteExterior> listaMovilidadesVisitanteFil;

	private ArchivoMovilidadDE archivoDE;
	private ArchivoMovilidadVE archivoVE;
	private ArchivoMovilidadEP archivoEP;

	private List<MovilidadDocentesExterior> listaMovilidadesEvento;
	private List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgrado;
	private List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgradoEventos;
	private List<MovilidadDocentesExterior> listaMovilidadesEventoFil;
	private List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgradoFil;
	private List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgradoEventosFil;

	private List listaMovilidadesDocentesArtes;
	private List listaMovilidadesEstudiantesArtes;
	private List listaMovilidadesVisitantesArtes;

	private MovilidadDocentesExterior movilidadDocentesExteriorSeleccionada;
	private MovilidadDocentesExterior movilidadDocenteSel;
	private MovilidadEstudiantesPosgrado movilidadEstudianteSel;
	private MovilidadVisitanteExterior movilidadVisExtSel;

	private MovilidadVisitanteExterior movilidadVisitanteExteSeleccionada;
	private MovilidadDocentesArtes movilidadDocenteArt;
	private MovilidadEstudiantesArtes movilidadEstudianteArt;
	private List<MovilidadDocentesArtes> listaMovilidadesDocenteArt;
	private List<MovilidadEstudiantesArtes> listaMovilidadesEstudiantesArt;
	private List<MovilidadDocentesArtes> listaMovilidadesDocenteArtFil;
	private List<MovilidadEstudiantesArtes> listaMovilidadesEstudiantesArtFil;
	private MovilidadEstudiantesPosgrado movilidadEstudiantesPosgradoSel;

	public ManejadorConsultaMovilidadesInvestigador() {
		try {
			cargarListasMovilidades();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(" Error al cargar listas de movilidades.");
		}

	}

	public String editarMovDocente() {

		return "";
	}

	public String editarMovDocenteArt() {

		sesion.removeAttribute("ManejadorCrearEditarMovilidadDocArt");
		sesion.removeAttribute("ManejadorEditarMovilidadDocArt");

		Long id = movilidadDocenteArt.getId();
		sesion.setAttribute("movilidadDocArt", id);

		System.out.println("editar movilidad");

		return "editarMovDocArt";
	}

	public String consultarMovDocenteArt() {

		return "";
	}

	public String editarMovEstudianteArt() {

		sesion.removeAttribute("ManejadorCrearEditarMovilidadEstArt");
		sesion.removeAttribute("ManejadorEditarMovilidadEstArt");

		Long id = movilidadEstudianteArt.getId();

		sesion.setAttribute("movilidadEstudianteArtes", id);

		return "editarMovEstArt";
	}

	public String editarMovVisExt() {
		sesion.removeAttribute("ManejadorCrearEditarMovilidadVisitante");
		sesion.removeAttribute("ManejadorCrearEditarMovilidadVisitanteConvFacGen");
		Long id = movilidadVisExtSel.getId();
		
		String hqlMov = "select #tipoMovilidad e.tipoMovilidad from MovilidadVisitanteExterior e where e.id = '" + id + "'";
		List<MovilidadVisitanteExterior> listMve = servicioGeneral.obtenerObjetosLimitado(MovilidadVisitanteExterior.class, hqlMov);
		MovilidadVisitanteExterior mve = listMve.get(0);
		
		sesion.setAttribute("movilidadVisExtSel", id);
		
		return "editarMovVisExt";
		
//		if(mve.getTipoMovilidad().getId().equals("CF_MOV7")){
//			return "editarMovVisExtConvFacGen";
//		}else{
//			return "editarMovVisExt";
//		}
		
	}

	public String editarMovDoc() {
		sesion.removeAttribute("ManejadorCrearEditarMovilidadEvento");
		sesion.removeAttribute("ManejadorCrearEditarMovilidadEventoConvFacGen");

		Long id = movilidadDocentesExteriorSeleccionada.getId();

		sesion.setAttribute("movilidadDocEvSel", id);
		
		String hqlMov = "select #tipoMovilidad e.tipoMovilidad from MovilidadDocentesExterior e where e.id = '" + id + "'";
		List<MovilidadDocentesExterior> listMve = servicioGeneral.obtenerObjetosLimitado(MovilidadDocentesExterior.class, hqlMov);
		MovilidadDocentesExterior mve = listMve.get(0);
		
		return "editarMovDoc";
		
//		if(mve.getTipoMovilidad().getId().equals("CF_MOV8")){
//			return "editarMovVisExtConvFacGen";
//		}else{
//			return "editarMovDoc";
//		}
	}

	public String editarMovEstPon() {
		sesion.removeAttribute("ManejadorCrearMovilidadPosgradoInvModTres");

		Long id = movilidadEstudiantesPosgradoSel.getId();

		sesion.setAttribute("movilidadEstPonSel", id);

		return "editarMovEstPon";
	}

	public String editarMovEstPas() {
		sesion.removeAttribute("ManejadorCrearEditarMovilidadPosgradoInvestigacion");

		Long id = movilidadEstudiantesPosgradoSel.getId();

		sesion.setAttribute("movilidadEstPasSel", id);

		return "editarMovEstPas";
	}

	public String consultarMovEstudiante() {

		return "";
	}

	public String editarMovEstEv() {
		return "";
	}

	public String consultarMovEstudianteEve() {
		return "";
	}

	public String editarMovVisit() {

		return "";
	}

	public String consultarMovVisit() {

		return "";
	}

	public String registrarSeguimientoDoc() {
		boolean consultaFacultad = false;
		boolean esConsulta = false;
		return ingresarSeguimientoDoc(movilidadDocenteSel.getId(), consultaFacultad, esConsulta);
	}

	public String consultarSeguimientoDoc() {
		boolean consultaFacultad = false;
		boolean esConsulta = true;
		return ingresarSeguimientoDoc(movilidadDocenteSel.getId(), consultaFacultad, esConsulta);
	}
	
	public String registrarSeguimientoDocArtes() {
		sesion.setAttribute("movilidadDocenteArtes", movilidadDocenteArt);
		sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadDocenteArtes");
		return "SeguimientoMovilidadDocenteArtes";
	}

	public String consultarSeguimientoDocArtes() {
		sesion.setAttribute("movilidadDocenteArtes", movilidadDocenteArt);
		sesion.removeAttribute("manejadorCrearEditarSeguimientoMovilidadDocenteArtes");
		sesion.setAttribute("consultaMovilidadDocenteArtes", "SI");
		return "SeguimientoMovilidadDocenteArtes";
	}

	public String registrarSeguimientoEstPos() {
		boolean consultaFacultad = false;
		boolean esConsulta = false;
		return consultarSeguimientoEstPos(movilidadEstudianteSel.getId(), consultaFacultad, esConsulta);
	}

	public String consultarSeguimientoEstPos() {
		boolean consultaFacultad = false;
		boolean esConsulta = true;
		return consultarSeguimientoEstPos(movilidadEstudianteSel.getId(), consultaFacultad, esConsulta);
	}

	public String registrarSeguimientoEstRes() {
		boolean consultaFacultad = false;
		boolean esConsulta = false;
		return ingresarSeguimientoEstRes(movilidadEstudianteSel.getId(), consultaFacultad, esConsulta);
	}

	public String consultarSeguimientoEstRes() {
		boolean consultaFacultad = false;
		boolean esConsulta = true;
		return ingresarSeguimientoEstRes(movilidadEstudianteSel.getId(), consultaFacultad, esConsulta);
	}
	
	public String registrarSeguimientoEstArt() {
		sesion.setAttribute("movilidadEstudianteArt", movilidadEstudianteArt);
		sesion.removeAttribute("ManejadorCrearEditarSeguimientoMovilidadEstudianteArtes");
		return "SeguimientoMovilidadEstudianteArtes";
	}

	public String consultarSeguimientoEstArt() {
		sesion.setAttribute("movilidadEstudianteArt", movilidadEstudianteArt);
		sesion.removeAttribute("ManejadorCrearEditarSeguimientoMovilidadEstudianteArtes");
		sesion.setAttribute("esMovilidadResidencia", true);
		sesion.setAttribute("consultaMovilidadEstudiantesArtes", "SI");
		return "SeguimientoMovilidadEstudianteArtes";
	}

	public String registrarSeguimientoVisExt() {
		boolean consultaFacultad = false;
		boolean esConsulta = false;
		return consultarSeguimientoVisExt(movilidadVisExtSel.getId(), consultaFacultad, esConsulta);
	}

	public String consultarSeguimientoVisExt() {
		boolean consultaFacultad = false;
		boolean esConsulta = true;
		return consultarSeguimientoVisExt(movilidadVisExtSel.getId(), consultaFacultad, esConsulta);
	}

	public void descargarDocumentoEvento() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		Object o = (Object) map.get("archivoResumen");
		Long idArchivo = Long.valueOf((String) o);
		descargarArchivoMovilidadDEGenerico(idArchivo);
	}

	public void descargarDocumentoEstPosgradoEventos() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		Object o = (Object) map.get("archivoResumen4");
		Long idArchivo = Long.valueOf((String) o);
		descargarArchivoMovilidadEPGenerico(idArchivo);
	}

	public void descargarDocumentoEstPosgradoEventos2() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		Object o = (Object) map.get("archivoResumen4");
		Long idArchivo = Long.valueOf((String) o);
		descargarArchivoMovilidadEPGenerico(idArchivo);
	}

	public void descargarDocumentoVisitante() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		Object o = (Object) map.get("archivoResumen1");
		Long idArchivo = Long.valueOf((String) o);
		descargarArchivoMovilidadVEGenerico(idArchivo);
	}

	public void imprimirMovilidadEvento() {
		imprimirMovilidadEventoGenerico(movilidadDocentesExteriorSeleccionada);
	}

	public void imprimirMovilidadVisitante() {
		imprimirMovilidadVisitanteGenerico(movilidadVisitanteExteSeleccionada);
	}

	public void imprimirMovilidadPosgrado() {
		imprimirMovilidadEstudiantesPosgradoGenerico(movilidadEstudiantesPosgradoSel);
	}

	public void imprimirMovilidadPosgradoArtes() {
		imprimirMovilidadEstudiantesArtesGenerico(movilidadEstudianteArt);
	}

	private <T> List<T> cargarMovilidades(Class<T> clase, Persona persona, String nombreAceptacion,
			String nombreAprobacion, boolean seg, boolean estudiante, String hqlWhereAdicional, boolean tipoM) {
		List<T> lista;
		String hql = "select #id mov.id, #estado mov.estado, #fechainicial mov.fechainicial, #convocatoria mov.convocatoria, "
				+ "#fechasolicitud mov.fechasolicitud, #" + nombreAceptacion + " mov." + nombreAceptacion + "," + "#"
				+ nombreAprobacion + " mov." + nombreAprobacion + " ";
		if (seg) {
			hql += ",#estadoSeguimiento mov.estadoSeguimiento, #estadoRevisionSeguimiento mov.estadoRevisionSeguimiento ";
		}
		if (estudiante) {
			hql += ",#estudianteInv mov.estudianteInv ";
		}
		if (tipoM) {
			hql += ",#tipoMovilidad mov.tipoMovilidad ";
		}
		hql += " from " + clase.getSimpleName() + " mov where " + "mov.personaInv.id.documento = '"
				+ persona.getId().getDocumento() + "' and " + "mov.personaInv.id.tipoDocumento = '"
				+ persona.getId().getTipoDocumento() + "'";
		hql += hqlWhereAdicional;
		lista = servicioGeneral.obtenerObjetosLimitado(clase, hql);
		return lista;
	}

	private <T> Set<T> obtenerArchivosMovilidad(Class<T> clase, Long id) {
		Set<T> set = new HashSet<T>();
		String consulta = "select am from " + clase.getSimpleName() + " am where am.movilidad.id = '" + id + "'";
		List<T> list = servicioGeneral.obtenerObjetos(clase, consulta);
		if (list != null) {
			set.addAll(list);
		}
		return set;
	}

	private void cargarListasMovilidades() {
		// Se cargan las movilidades de Tesis

		Persona persona = new Persona();

		persona = (Persona) sesion.getAttribute("persona");
		listaMovilidadesVisitante = cargarMovilidades(MovilidadVisitanteExterior.class, persona, "aceptacion",
				"aprobacion", true, false, "and mov.tipoMovilidad.id IN ('A1','CF_MOV3','CF_MOV7')", true);
		listaMovilidadesEvento = cargarMovilidades(MovilidadDocentesExterior.class, persona, "aceptacion", "aprobacion",
				true, false, "and mov.tipoMovilidad.id IN ('B1','CF_MOV2','CF_MOV4','CF_MOV8')", true);
		listaMovilidadesEstudiantePosgradoEventos = cargarMovilidades(MovilidadEstudiantesPosgrado.class, persona,
				"aceptacion", "aprobacion", true, true, "and mov.tipoMovilidad.id in ('MOV3_IN','CF_MOV1','CF_MOV6')",true);
		listaMovilidadesEstudiantePosgrado = cargarMovilidades(MovilidadEstudiantesPosgrado.class, persona,
				"aceptacion", "aprobacion", true, true, "and mov.tipoMovilidad.id in ('D1','CF_MOV5')", true);
		listaMovilidadesEstudiantesArt = cargarMovilidades(MovilidadEstudiantesArtes.class, persona,
				"aceptacionFacultad", "aprobacionSede", false, true, "", false);
		listaMovilidadesDocenteArt = cargarMovilidades(MovilidadDocentesArtes.class, persona, "aceptacionFacultad",
				"aprobacionSede", false, false, "", false);

		if (!esListaVacia(listaMovilidadesVisitante)) {
			Iterator<MovilidadVisitanteExterior> i = listaMovilidadesVisitante.iterator();
			while (i.hasNext()) {
				MovilidadVisitanteExterior movilidadVisitanteExterior = i.next();
				movilidadVisitanteExterior.setArchivos(
						obtenerArchivosMovilidad(ArchivoMovilidadVE.class, movilidadVisitanteExterior.getId()));
			}
		}

		if (!esListaVacia(listaMovilidadesEvento)) {
			Iterator<MovilidadDocentesExterior> i = listaMovilidadesEvento.iterator();
			while (i.hasNext()) {
				MovilidadDocentesExterior movilidadDocentesExterior = i.next();
				movilidadDocentesExterior.setArchivos(
						obtenerArchivosMovilidad(ArchivoMovilidadDE.class, movilidadDocentesExterior.getId()));
			}
		}

		if (!esListaVacia(listaMovilidadesEstudiantePosgradoEventos)) {
			Iterator<MovilidadEstudiantesPosgrado> i = listaMovilidadesEstudiantePosgradoEventos.iterator();
			while (i.hasNext()) {
				MovilidadEstudiantesPosgrado movilidadEstudiantesPosgrado = i.next();
				movilidadEstudiantesPosgrado.setArchivos(
						obtenerArchivosMovilidad(ArchivoMovilidadEP.class, movilidadEstudiantesPosgrado.getId()));
			}
		}

		if (!esListaVacia(listaMovilidadesEstudiantePosgrado)) {
			Iterator<MovilidadEstudiantesPosgrado> i = listaMovilidadesEstudiantePosgrado.iterator();
			while (i.hasNext()) {
				MovilidadEstudiantesPosgrado movilidadEstudiantesPosgrado = i.next();
				movilidadEstudiantesPosgrado.setArchivos(
						obtenerArchivosMovilidad(ArchivoMovilidadEP.class, movilidadEstudiantesPosgrado.getId()));
			}
		}

		if (!esListaVacia(listaMovilidadesEstudiantesArt)) {
			Iterator<MovilidadEstudiantesArtes> i = listaMovilidadesEstudiantesArt.iterator();
			while (i.hasNext()) {
				MovilidadEstudiantesArtes movilidadEstudiantesArtes = i.next();
				movilidadEstudiantesArtes.setArchivos(
						obtenerArchivosMovilidad(ArchivoMovilidad.class, movilidadEstudiantesArtes.getId()));
			}
		}

		if (!esListaVacia(listaMovilidadesDocenteArt)) {
			Iterator<MovilidadDocentesArtes> i = listaMovilidadesDocenteArt.iterator();
			while (i.hasNext()) {
				MovilidadDocentesArtes movilidadDocentesArtes = i.next();
				movilidadDocentesArtes
						.setArchivos(obtenerArchivosMovilidad(ArchivoMovilidad.class, movilidadDocentesArtes.getId()));
			}
		}

	}

	public List<MovilidadDocentesExterior> getListaMovilidadesEvento() {
		return listaMovilidadesEvento;
	}

	public void setListaMovilidadesEvento(List<MovilidadDocentesExterior> listaMovilidadesEvento) {
		this.listaMovilidadesEvento = listaMovilidadesEvento;
	}

	public List<MovilidadEstudiantesPosgrado> getListaMovilidadesEstudiantePosgrado() {
		return listaMovilidadesEstudiantePosgrado;
	}

	public void setListaMovilidadesEstudiantePosgrado(
			List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgrado) {
		this.listaMovilidadesEstudiantePosgrado = listaMovilidadesEstudiantePosgrado;
	}

	public List<MovilidadVisitanteExterior> getListaMovilidadesVisitante() {
		return listaMovilidadesVisitante;
	}

	public List getListaMovilidadesDocentesArtes() {
		return listaMovilidadesDocentesArtes;
	}

	public void setListaMovilidadesDocentesArtes(List listaMovilidadesDocentesArtes) {
		this.listaMovilidadesDocentesArtes = listaMovilidadesDocentesArtes;
	}

	public List getListaMovilidadesEstudiantesArtes() {
		return listaMovilidadesEstudiantesArtes;
	}

	public void setListaMovilidadesEstudiantesArtes(List listaMovilidadesEstudiantesArtes) {
		this.listaMovilidadesEstudiantesArtes = listaMovilidadesEstudiantesArtes;
	}

	public List getListaMovilidadesVisitantesArtes() {
		return listaMovilidadesVisitantesArtes;
	}

	public void setListaMovilidadesVisitantesArtes(List listaMovilidadesVisitantesArtes) {
		this.listaMovilidadesVisitantesArtes = listaMovilidadesVisitantesArtes;
	}

	public String reporteMovilidad() {
		return Navegacion.REPORTE;
	}

	public ArchivoMovilidadDE getArchivoDE() {
		return archivoDE;
	}

	public void setArchivoDE(ArchivoMovilidadDE archivoDE) {
		this.archivoDE = archivoDE;
	}

	public ArchivoMovilidadVE getArchivoVE() {
		return archivoVE;
	}

	public void setArchivoVE(ArchivoMovilidadVE archivoVE) {
		this.archivoVE = archivoVE;
	}

	public ArchivoMovilidadEP getArchivoEP() {
		return archivoEP;
	}

	public void setArchivoEP(ArchivoMovilidadEP archivoEP) {
		this.archivoEP = archivoEP;
	}

	public MovilidadDocentesExterior getMovilidadDocenteSel() {
		return movilidadDocenteSel;
	}

	public void setMovilidadDocenteSel(MovilidadDocentesExterior movilidadDocenteSel) {
		this.movilidadDocenteSel = movilidadDocenteSel;
	}

	public MovilidadEstudiantesPosgrado getMovilidadEstudianteSel() {
		return movilidadEstudianteSel;
	}

	public void setMovilidadEstudianteSel(MovilidadEstudiantesPosgrado movilidadEstudianteSel) {
		this.movilidadEstudianteSel = movilidadEstudianteSel;
	}

	public MovilidadVisitanteExterior getMovilidadVisExtSel() {
		return movilidadVisExtSel;
	}

	public void setMovilidadVisExtSel(MovilidadVisitanteExterior movilidadVisExtSel) {
		this.movilidadVisExtSel = movilidadVisExtSel;
	}

	public MovilidadDocentesArtes getMovilidadDocenteArt() {
		return movilidadDocenteArt;
	}

	public void setMovilidadDocenteArt(MovilidadDocentesArtes movilidadDocenteArt) {
		this.movilidadDocenteArt = movilidadDocenteArt;
	}

	public List<MovilidadDocentesArtes> getListaMovilidadesDocenteArt() {
		return listaMovilidadesDocenteArt;
	}

	public void setListaMovilidadesDocenteArt(List<MovilidadDocentesArtes> listaMovilidadesDocenteArt) {
		this.listaMovilidadesDocenteArt = listaMovilidadesDocenteArt;
	}

	public List<MovilidadEstudiantesArtes> getListaMovilidadesEstudiantesArt() {
		return listaMovilidadesEstudiantesArt;
	}

	public void setListaMovilidadesEstudiantesArt(List<MovilidadEstudiantesArtes> listaMovilidadesEstudiantesArt) {
		this.listaMovilidadesEstudiantesArt = listaMovilidadesEstudiantesArt;
	}

	public MovilidadEstudiantesArtes getMovilidadEstudianteArt() {
		return movilidadEstudianteArt;
	}

	public void setMovilidadEstudianteArt(MovilidadEstudiantesArtes movilidadEstudianteArt) {
		this.movilidadEstudianteArt = movilidadEstudianteArt;
	}

	public List<MovilidadEstudiantesPosgrado> getListaMovilidadesEstudiantePosgradoEventos() {
		return listaMovilidadesEstudiantePosgradoEventos;
	}

	public void setListaMovilidadesEstudiantePosgradoEventos(
			List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgradoEventos) {
		this.listaMovilidadesEstudiantePosgradoEventos = listaMovilidadesEstudiantePosgradoEventos;
	}

	public void setMovilidadVisitanteExteSeleccionada(MovilidadVisitanteExterior movilidadVisitanteExteSeleccionada) {
		this.movilidadVisitanteExteSeleccionada = movilidadVisitanteExteSeleccionada;
	}

	public MovilidadVisitanteExterior getMovilidadVisitanteExteSeleccionada() {
		return movilidadVisitanteExteSeleccionada;
	}

	public MovilidadDocentesExterior getMovilidadDocentesExteriorSeleccionada() {
		return movilidadDocentesExteriorSeleccionada;
	}

	public void setMovilidadDocentesExteriorSeleccionada(
			MovilidadDocentesExterior movilidadDocentesExteriorSeleccionada) {
		this.movilidadDocentesExteriorSeleccionada = movilidadDocentesExteriorSeleccionada;
	}

	public MovilidadEstudiantesPosgrado getMovilidadEstudiantesPosgradoSel() {
		return movilidadEstudiantesPosgradoSel;
	}

	public void setMovilidadEstudiantesPosgradoSel(MovilidadEstudiantesPosgrado movilidadEstudiantesPosgradoSel) {
		this.movilidadEstudiantesPosgradoSel = movilidadEstudiantesPosgradoSel;
	}

	public List<MovilidadVisitanteExterior> getListaMovilidadesVisitanteFil() {
		return listaMovilidadesVisitanteFil;
	}

	public void setListaMovilidadesVisitanteFil(
			List<MovilidadVisitanteExterior> listaMovilidadesVisitanteFil) {
		this.listaMovilidadesVisitanteFil = listaMovilidadesVisitanteFil;
	}

	public List<MovilidadDocentesExterior> getListaMovilidadesEventoFil() {
		return listaMovilidadesEventoFil;
	}

	public void setListaMovilidadesEventoFil(
			List<MovilidadDocentesExterior> listaMovilidadesEventoFil) {
		this.listaMovilidadesEventoFil = listaMovilidadesEventoFil;
	}

	public List<MovilidadEstudiantesPosgrado> getListaMovilidadesEstudiantePosgradoFil() {
		return listaMovilidadesEstudiantePosgradoFil;
	}

	public void setListaMovilidadesEstudiantePosgradoFil(
			List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgradoFil) {
		this.listaMovilidadesEstudiantePosgradoFil = listaMovilidadesEstudiantePosgradoFil;
	}

	public List<MovilidadEstudiantesPosgrado> getListaMovilidadesEstudiantePosgradoEventosFil() {
		return listaMovilidadesEstudiantePosgradoEventosFil;
	}

	public void setListaMovilidadesEstudiantePosgradoEventosFil(
			List<MovilidadEstudiantesPosgrado> listaMovilidadesEstudiantePosgradoEventosFil) {
		this.listaMovilidadesEstudiantePosgradoEventosFil = listaMovilidadesEstudiantePosgradoEventosFil;
	}

	public List<MovilidadDocentesArtes> getListaMovilidadesDocenteArtFil() {
		return listaMovilidadesDocenteArtFil;
	}

	public void setListaMovilidadesDocenteArtFil(
			List<MovilidadDocentesArtes> listaMovilidadesDocenteArtFil) {
		this.listaMovilidadesDocenteArtFil = listaMovilidadesDocenteArtFil;
	}

	public List<MovilidadEstudiantesArtes> getListaMovilidadesEstudiantesArtFil() {
		return listaMovilidadesEstudiantesArtFil;
	}

	public void setListaMovilidadesEstudiantesArtFil(
			List<MovilidadEstudiantesArtes> listaMovilidadesEstudiantesArtFil) {
		this.listaMovilidadesEstudiantesArtFil = listaMovilidadesEstudiantesArtFil;
	}

	public void setListaMovilidadesVisitante(
			List<MovilidadVisitanteExterior> listaMovilidadesVisitante) {
		this.listaMovilidadesVisitante = listaMovilidadesVisitante;
	}

}
