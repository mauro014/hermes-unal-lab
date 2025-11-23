/*
 * Created on 09-ago-2005
 */
package co.edu.unal.hermes.modelo.servicioProyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.bd.IGeneralDAO;
import co.edu.unal.hermes.bd.IPalabraClaveDAO;
import co.edu.unal.hermes.bd.IProyectoDAO;
import co.edu.unal.hermes.bd.imp.ProyectoDAOHibernate;
import co.edu.unal.hermes.modelo.Actividad;
import co.edu.unal.hermes.modelo.ActividadPersona;
import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.modelo.ArchivoRequerimiento;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Financiacion;
import co.edu.unal.hermes.modelo.Gasto;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.HistoricoAsignacionProyecto;
import co.edu.unal.hermes.modelo.HistoricoEstadoAval;
import co.edu.unal.hermes.modelo.HistoricoEstadoInforme;
import co.edu.unal.hermes.modelo.HistoricoEstadoLegalizacion;
import co.edu.unal.hermes.modelo.HistoricoEstadoProyecto;
import co.edu.unal.hermes.modelo.HistoricoHabilitacionEdicionProyecto;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.InvestigadorProyecto;
import co.edu.unal.hermes.modelo.MetaProyecto;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.ObservacionSeguimiento;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoCarta;
import co.edu.unal.hermes.modelo.ProyectoCoordinadorEditorial;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.ProyectoProducto;
import co.edu.unal.hermes.modelo.ResultadoProyecto;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.editorial.CantidadMaterialGrafico;
import co.edu.unal.hermes.modelo.editorial.TipoMaterialGrafico;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleProyectos;
import co.edu.unal.hermes.modelo.seguimiento.ObservacionProyecto;
import co.edu.unal.hermes.modelo.seguimiento.ProyectoProrroga;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.utils.ReemplazaAcentos;
import co.edu.unal.hermes.utils.UtilidadesVarias;
import co.edu.unal.hermes.vista.evaluadores.ProyectoCoordinador;
import co.edu.unal.hermes.vista.proyectos.ProyectoVista;
import co.edu.unal.hermes.vista.proyectos.ProyectosVistaOficiosConvocatoria;

/**
 * Clase define los servicios relacionados con los proyectos en la logica del
 * negocio
 */
public class ServicioProyecto implements IServicioProyecto {

	private IProyectoDAO proyectoDAO;
	private IPalabraClaveDAO palabraClaveDAO;
	private IGeneralDAO generalDAO;

	public List obtenerProyectosCoordinadorxModalidadxSede(Modalidad mod, String dep, String idEstPry)
			throws DataAccessException {
		return proyectoDAO.obtenerProyectosCoordinadorxModalidadxSede(mod, dep, idEstPry);
	}

	public List obtenerProyectosCoordinador(Modalidad mod, String documento, String tipodoc, boolean Elegibles) {
		return proyectoDAO.obtenerProyectosCoordinador(mod, documento, tipodoc, Elegibles);
	}

	public List obtenerProyectosCoordinadorxModalidadSede(Modalidad mod, Dependencia dep, Persona pep)
			throws DataAccessException {
		return proyectoDAO.obtenerProyectosCoordinadorxModalidadSede(mod, dep, pep);
	}

	public List obtenerProyectosCoordinadorxModalidadxFacultadEstado(Modalidad mod, String idFac)
			throws DataAccessException {
		return proyectoDAO.obtenerProyectosCoordinadorxModalidadxFacultadEstado(mod, idFac);
	}

	public List<ProyectoCoordinador> obtenerProyectosCoordinadorxIdProyecto(String id) throws DataAccessException {
		return proyectoDAO.obtenerProyectosCoordinadorxIdProyecto(id);
	}

	public void setPalabraClaveDAO(IPalabraClaveDAO palabraClaveDAO) {
		this.palabraClaveDAO = palabraClaveDAO;
	}

	public List<Proyecto> obtenerProyectoBuscador(String sql, String facultad, String sedep, String investigador,
			String area, String palabra) throws DataAccessException {
		return proyectoDAO.obtenerProyectoBuscador(sql, facultad, sedep, investigador, area, palabra);
	}

	public List<ConvocatoriaExterna> obtenerConvocatorias(String sqlInternas, String sqlExternas)
			throws DataAccessException {
		return proyectoDAO.obtenerConvocatorias(sqlInternas, sqlExternas);
	}

	public void imprimirReporteProyecto(Proyecto proyectoActual, HttpSession sesion, Boolean esEvaluador) {
		proyectoDAO.imprimirReporteProyecto(proyectoActual, sesion, esEvaluador);
	}

	public void imprimirReporteProyectoEditorial(Long idProyecto, HttpSession sesion, Boolean esEvaluador) {
		proyectoDAO.imprimirReporteProyectoEditorial(idProyecto, sesion, esEvaluador);
	}
	
	public void imprimirReporteProyectoLegalizacion(Proyecto proyectoActual, HttpSession sesion) {
		proyectoDAO.imprimirReporteProyectoLegalizacion(proyectoActual, sesion);
	}

	public boolean validarVinculacionPersona(InvestigadorInterno invI, boolean permitirDocentesCatedra) {
		return proyectoDAO.validarVinculacionPersona(invI, permitirDocentesCatedra);
	}

	public boolean validarGanadorSemilleros(String idPersona, String tipoDocumento, String id) {
		return proyectoDAO.validarGanadorSemilleros(idPersona, tipoDocumento, id);
	}

	public List<ProyectoEvaluador> obtenerProyectosEvaluador(Long idProyecto) throws DataAccessException {
		return proyectoDAO.obtenerProyectosEvaluador(idProyecto);
	}

	public List<ProyectoInforme> obtenerProyectoInforme(Long idProyecto) throws DataAccessException {
		return proyectoDAO.obtenerProyectoInforme(idProyecto);
	}

	public String consultarProyectoGenerico(Proyecto proyecto, HttpSession sesion, Convocatoria conv) {
		return proyectoDAO.consultarProyectoGenerico(proyecto, sesion, conv);
	}

	public void setProyectoDAO(IProyectoDAO dao) {
		proyectoDAO = dao;
	}

	public List obtenerProyectos(String where, boolean isParametros, Object[] parametros) throws DataAccessException {
		return proyectoDAO.obtenerProyectos(where, isParametros, parametros);
	}
	
	/**
	 * Obtiene un objeto proyecto ya creado con la llave primaria "id"
	 * 
	 * @param id
	 *            Long que identifica el objeto proyecto
	 * @return Objeto proyecto
	 */
	public Proyecto obtenerProyecto(Long id, short informacion) {
		return proyectoDAO.buscarPorId(id, informacion, false);
	}

    public Proyecto obtenerProyecto(Long id, short informacion, boolean incluirGastos) {
        return proyectoDAO.buscarPorId(id, informacion, incluirGastos);
    }

	public Laboratorio obtenerLaboratorio(Long id, short informacion) {
		return proyectoDAO.buscarLaboratorioPorId(id, informacion);
	}

	public List obtenerProyectosPorCriterio(String sql) throws DataAccessException {
		return proyectoDAO.obtenerProyectosPorCriterio(sql);
	}

	public Proyecto obtenerProyectoAsesorEvaluacion(Long id, IdPersona idAsesor) {
		return proyectoDAO.buscarPorIdAsignadosEvaluacion(id, idAsesor);
	}

	/**
	 * Obtiene todos los proyectos del sistema
	 * 
	 * @return devuelve una lista con los proyectos
	 */
	public List obtenerTodosProyectos() {
		return proyectoDAO.obtenerProyectos("");
	}

	/**
	 * Obtiene los proyectos de acuerdo a los criterios de búsqueda
	 * 
	 * @param proyecto
	 *            objeto de la clase objeto con las propiedades de la búsqueda
	 * @return de vuelve una lista con los proyectos encontrados
	 */
	public List obtenerProyectosPorCriterio(Proyecto proyecto) {
		List a = new ArrayList();
		try {
			List titles = ReemplazaAcentos.listaPalabrasBusqueda(proyecto.getNombre());
			Iterator it = titles.iterator();
			while (it.hasNext()) {
				String s = (String) it.next();
				proyecto.setNombre(s);
				a.addAll(proyectoDAO.obtenerProyectosPorCriterio(proyecto));
			}

		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return a;
	}

	/**
	 * Obtiene los proyectos que tienen en una parte de su nombre el string
	 * "title"
	 * 
	 * @param title
	 *            parte del titulo del proyecto para generar la busuqueda
	 * @return de vuelve una lista con los proyectos encontrados
	 */
	public List obtenerProyectosPorNombre(String title, Boolean exacta) {
		List a = null;
		try {
			Set set_proyectos = new HashSet();
			// busca primero en los titulos de los proyectos
			// set_proyectos.addAll(proyectoDAO.obtenerProyectos(title));
			if (exacta) {
				set_proyectos.addAll(proyectoDAO.obtenerProyectosXNombreExacto(title));
			} else {
				set_proyectos.addAll(proyectoDAO.obtenerProyectosXNombre(title));
				// busca los proyectos por las palabras claves
				set_proyectos.addAll(palabraClaveDAO.obtenerProyectosPalabraClave(title));
			}
			a = new ArrayList(set_proyectos);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return a;
	}
	
	
	public List obtenerProyectosXId(Long id) throws DataAccessException {
		return proyectoDAO.obtenerProyectosXId(id);
	}
	
	public List<Proyecto> obtenerProyectosPreAsociadosLaboratorio(Long idLab) throws DataAccessException {
		List<LaboratorioDetalleProyectos> listaDetalleProyecto = proyectoDAO.obtenerDetalleProyectosPreAsociadosLaboratorioXIdLab(idLab);
		List<Proyecto> listaProyectos = new ArrayList<Proyecto>();
		
		for (LaboratorioDetalleProyectos laboratorioDetalleProyectos : listaDetalleProyecto) 
			listaProyectos.add(laboratorioDetalleProyectos.getProyecto());
		
		return listaProyectos;
	}
	
	public List<Laboratorio> obtenerLaboratoriosPreAsociadosProyecto(Long idPry) throws DataAccessException {
		List<LaboratorioDetalleProyectos> listaDetalleProyecto = proyectoDAO.obtenerDetalleProyectosPreAsociadosLaboratorioXIdProyecto(idPry);
		List<Laboratorio> listaLaboratorios = new ArrayList<Laboratorio>();
		
		for (LaboratorioDetalleProyectos laboratorioDetalleProyectos : listaDetalleProyecto) 
			listaLaboratorios.add(laboratorioDetalleProyectos.getLaboratorio());
		
		return listaLaboratorios;
	}

	/**
	 * Obtiene los proyectos que tienen en una parte de su codigo quipu el
	 * string "title"
	 * 
	 * @param title
	 *            parte del titulo del proyecto para generar la busuqueda
	 * @return de vuelve una lista con los proyectos encontrados
	 */
	public List obtenerProyectosPorCodigoQuipu(String codigo) {
		List a = null;
		try {
			Set set_proyectos = new HashSet();
			// busca primero en los titulos de los proyectos
			// set_proyectos.addAll(proyectoDAO.obtenerProyectos(title));
			set_proyectos.addAll(proyectoDAO.obtenerProyectosXCodigoQuipu(codigo));
			a = new ArrayList(set_proyectos);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return a;
	}

	/**
	 * Obtiene los proyectos que tienen en una parte de su nombre el string
	 * "title"
	 * 
	 * @param title
	 *            parte del titulo del proyecto para generar la busuqueda
	 * @return de vuelve una lista con los proyectos encontrados
	 */
	public List obtenerProyectosPorNombreEstadoDiferente(String title, String idEstado) {
		List a = null;
		try {
			Set set_proyectos = new HashSet();
			// busca primero en los titulos de los proyectos
			// set_proyectos.addAll(proyectoDAO.obtenerProyectos(title));
			set_proyectos.addAll(proyectoDAO.obtenerProyectosXNombreYEstadoDiferente(title, idEstado));
			// busca los proyectos por las palabras claves
			set_proyectos.addAll(palabraClaveDAO.obtenerProyectosPalabraClaveEstadoProyectoDiferente(title, idEstado));
			a = new ArrayList(set_proyectos);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return a;
	}

	/**
	 * metodo privado que guarda o actualiza un proyecto dado
	 * 
	 * @param pry
	 *            proyecto a guardar
	 */
	private void guardarProyecto(Proyecto pry) {
		proyectoDAO.guardarProyecto(pry);
	}

	/**
	 * obtiene una lista con los historicos de los estados de un proyecto dado
	 */
	public List<HistoricoEstadoProyecto> obtenerHistoricosEstadoProyecto(Proyecto proyecto) {
		return proyectoDAO.obtenerHistoricosEstadoProyecto(proyecto.getId());
	}

	public List<HistoricoAsignacionProyecto> obtenerHistoricosAsignacionProyecto(Long pryId) {
		return proyectoDAO.obtenerHistoricosAsignacionProyecto(pryId);
	}

	public List<HistoricoEstadoInforme> obtenerHistoricosEstadoInforme(Long informeId) {
		return proyectoDAO.obtenerHistoricosEstadoInforme(informeId);
	}

	public List<HistoricoHabilitacionEdicionProyecto> obtenerHistoricosHabilitacionEdicionProyecto(Long pryId) {
		return proyectoDAO.obtenerHistoricosHabilitacionEdicionProyecto(pryId);
	}
	
	public List<HistoricoEstadoLegalizacion> obtenerHistoricosEstadosLegalizacion(Long pryId) {
		return proyectoDAO.obtenerHistoricosEstadosLegalizacion(pryId);
	}

	/**
	 * Obtener histotico de estados de un aval con su responsable
	 */

	public List<HistoricoEstadoAval> obtenerHistoricosEstadoAval(Aval aval) {
		return proyectoDAO.obtenerHistoricosEstadoAval(aval.getAviId());
	}

	/**
	 * obtiene todos los tipos de investigacion posibles
	 */
	public List obtenerTiposInvestigacion() {
		return proyectoDAO.obtenerTiposInvestigacion();
	}

	/**
	 * ingresa un proyecto
	 * 
	 * @param pry
	 *            proyecto propuesto
	 */
	public void ingresarProyecto(Proyecto pry) {

		guardarProyecto(pry);
	}

	public void actualizarProyecto(Proyecto pry) {
		guardarProyecto(pry);
	}

	public List<ObjetivoEspecifico> obtenerObjetivosEspeficicos(Proyecto proyecto) {
		return proyectoDAO.obtenerObjetivosEspeficicos(proyecto);
	}

	public List obtenerFuenteFinanciacionInterna() {
		return proyectoDAO.obtenerFuentesFinanciacionInternas();
	}

	public List obtenerFuenteFinanciacionExterna() {
		return proyectoDAO.obtenerFuentesFinanciacionExternas();
	}

	public Archivo obtenerArchivo(Long id) {
		return proyectoDAO.obtenerArchivo(id);
	}

	public ArchivoRequerimiento obtenerArchivoRequerimiento(Long id) {
		return proyectoDAO.obtenerArchivoRequerimiento(id);
	}

	public List<ArchivoRequerimiento> obtenerArchivosRequerimientosLista(Long id) {
		return proyectoDAO.obtenerArchivosRequerimientosLista(id);
	}

	public Proyecto obtenerResumenProyecto(Long id) {
		return proyectoDAO.obtenerResumenProyecto(id);
	}

	public Proyecto obtenerResumenProyectoExtension(Long id) {
		return proyectoDAO.obtenerResumenProyectoExtension(id);
	}

	public void eliminarProyecto(Long id) {
		proyectoDAO.eliminarProyecto(id);
	}

	public List<Archivo> obtenerNombresArchivos(Proyecto proyecto) {
		return proyectoDAO.obtenerNombresArchivos(proyecto);
	}
	
	public List<Archivo> obtenerNombresArchivosReclamacion(Proyecto proyecto) {
		return proyectoDAO.obtenerNombresArchivosReclamacion(proyecto);
	}

	public List obtenerNombresArchivosTipos(Proyecto proyecto) {
		return proyectoDAO.obtenerNombresArchivosTipos(proyecto);
	}

	public List<Archivo> obtenerNombresArchivosConTipos(Proyecto proyecto) {
		return proyectoDAO.obtenerNombresArchivosConTipos(proyecto);
	}

	public void eliminarInvestigadorProyecto(InvestigadorProyecto investigadorProyecto) {
		proyectoDAO.eliminarInvestigadorProyecto(investigadorProyecto);

	}

	public List obtenerProyectosTituloConvocatoria(String nombre, Modalidad mod) {
		return proyectoDAO.obtenerProyectosTituloConvocatoria(nombre, mod);
	}

	public Proyecto obtenerProyectoHistorico(Long id) {

		return proyectoDAO.obtenerProyectoHistorico(id);
	}

	public ProyectoEvaluador obtenerProyectoEvaluador(ProyectoEvaluador pe) {
		return proyectoDAO.obtenerProyectoEvaluador(pe);
	}

	public void guardarEvaluacionProyecto(ProyectoEvaluador evaluacionProyecto) {
		proyectoDAO.guardarEvaluacionProyecto(evaluacionProyecto);
	}

	/**
	 * Se obtiente el proyecto que tiene el codigo DIB correspondiente al
	 * criterio de busqueda
	 */
	public Proyecto obtenerProyectoCodigoDib(String codigoDib) {
		return proyectoDAO.obtenerProyectoCodigoDib(codigoDib);
	}

	public List obtenerProyectosxEjemplo(Proyecto pry) {
		return proyectoDAO.obtenerProyectosxEjemplo(pry);
	}

	public Financiacion obtenerFinanciacionGastos(Financiacion fin) {
		return proyectoDAO.obtenerFinanciacionGastos(fin.getId());

	}

	public List obtenerProyectosConvocatoria(EstadoProyecto[] estados, Modalidad mod) {

		return proyectoDAO.obtenerProyectosConvocatoria(estados, mod);

	}

	public List obtenerProyectosConvocatoriaConInvestigadores(EstadoProyecto[] estados, Modalidad mod) {

		return proyectoDAO.obtenerProyectosConvocatoriaConInvestigadores(estados, mod);

	}

	public List obtenerProyectosPorNombreDependencia(String nombreProyecto, Dependencia dependencia,
			String tipo_dependencia) {

		// diana
		List a = new ArrayList();
		try {
			List titles = ReemplazaAcentos.listaPalabrasBusqueda(nombreProyecto);
			Iterator it = titles.iterator();
			while (it.hasNext()) {
				String s = (String) it.next();
				a.addAll(proyectoDAO.obtenerProyectosPorNombreDependencia(s, dependencia, tipo_dependencia));
			}

		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return a;

	}

	public List obtenerProyectosPorNombreDependenciaIndiferenteTildesMayusculasYDiferenteEstado(String nombreProyecto,
			Dependencia dependencia, String tipo_dependencia, String idEstado) {

		// diana, version alvaro
		List a = new ArrayList();
		try {
			List titles = ReemplazaAcentos.listaPalabrasBusqueda(nombreProyecto);
			Iterator it = titles.iterator();
			while (it.hasNext()) {
				String s = (String) it.next();
				a.addAll(proyectoDAO.obtenerProyectosPorNombreDependenciaIndeferenteTildesMayusculasDiferenteEstado(s,
						dependencia, tipo_dependencia, idEstado));
			}

		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return a;

	}

	public List obtenerAreasTematicas(Proyecto proyectoActual) {
		return proyectoDAO.obtenerAreasTematicas(proyectoActual.getId());
	}
	
	public List<ProyectosVistaOficiosConvocatoria> obtenerProyectosXEstadoYConvocatoriaPadre(Long idConvocatoriaPadre, String idEstado) {
		return proyectoDAO.obtenerProyectosXEstadoYConvocatoriaPadre(idConvocatoriaPadre, idEstado);
	}
	
	public List<ProyectosVistaOficiosConvocatoria> obtenerProyectosXEstadoYConvocatoriaPadreYCorte(Long idConvocatoriaPadre, String idEstado, Long corte) {
		return proyectoDAO.obtenerProyectosXEstadoYConvocatoriaPadreYCorte(idConvocatoriaPadre, idEstado, corte);
	}

	public List obtenerProyectosxModalidad(Modalidad mod) {
		return proyectoDAO.obtenerProyectosxModalidad(mod);
	}

	public List obtenerProyectosCoordinadorxModalidad(Modalidad mod) {
		return proyectoDAO.obtenerProyectosCoordinadorxModalidad(mod);
	}

	public List obtenerProyectosCoordinadorxModalidadxEstado(Modalidad mod, String idEstado) {
		return proyectoDAO.obtenerProyectosCoordinadorxModalidadxEstado(mod, idEstado);
	}

	public List obtenerProyectosCoordinadorxModalidadxFacultad(Modalidad mod, String idFac) {
		return proyectoDAO.obtenerProyectosCoordinadorxModalidadxFacultad(mod, idFac);
	}

	public List<ProyectoCoordinador> obtenerProyectosxId(String idDoc, String tipoDoc, String idPro) {
		return proyectoDAO.obtenerProyectosxId(idDoc, tipoDoc, idPro);
	}

	public String modificarCoordinadorEnProyecto(int tipo, String idProyecto, IdPersona idPersona, Persona asesor,
			String tipoCoordinador, boolean guardarLog) {
		String mensajeAsignacion = "";
		try {
			proyectoDAO.modificarCoordinadorEnProyecto(tipo, idProyecto, idPersona, asesor, tipoCoordinador,
					guardarLog);
		} catch (Exception ex) {
			mensajeAsignacion = "Ocurrio un error al guardar o actualizar el Coordinador";
		}
		return mensajeAsignacion;
	}

	public String modificarCoordinadorEnProyecto(int tipoModificacion, String tipoPersona, String idProyecto,
			IdPersona idPersona, Persona asesor, String tipoCoordinador, boolean guardarLog) {
		String mensajeAsignacion = "";
		try {
			proyectoDAO.modificarCoordinadorEnProyecto(tipoModificacion, tipoPersona, idProyecto, idPersona, asesor,
					tipoCoordinador, guardarLog);
		} catch (Exception ex) {
			mensajeAsignacion = "Ocurrio un error al guardar o actualizar el Coordinador";
		}
		return mensajeAsignacion;
	}
	
	public String modificarCoordinadorEnProyectoEditorial(int tipoModificacion, String tipoPersona, String idProyecto,
			IdPersona idPersona, Persona asesor, String tipoCoordinador, boolean guardarLog) {
		String mensajeAsignacion = "";
		try {
			proyectoDAO.modificarCoordinadorEnProyectoEditorial(tipoModificacion, tipoPersona, idProyecto, idPersona, asesor,
					tipoCoordinador, guardarLog);
		} catch (Exception ex) {
			mensajeAsignacion = "Ocurrio un error al guardar o actualizar el Coordinador";
		}
		return mensajeAsignacion;
	}


	public List obtenerIntegrantesProyectosXEstados(List estadosProyectos) {
		return proyectoDAO.obtenerIntegrantesProyectosXEstados(estadosProyectos);
	}

	public List obtenerProyectoEvaluadoresInternos(Long idProyecto, Modalidad mod) {
		return proyectoDAO.obtenerProyectoEvaluadoresInternos(idProyecto, mod);
	}

	public long obtenerFiananciacionXProyectoYFuente(Long idProyecto, String fuente) {
		return proyectoDAO.obtenerFiananciacionXProyectoYFuente(idProyecto, fuente);
	}

	public List obtenerProyectosPorNombreEstado(String nombreProyecto, EstadoProyecto estadoProyecto) {

		return proyectoDAO.obtenerProyectosPorNombreEstado(nombreProyecto, estadoProyecto);
	}

	public IProyectoDAO getProyectoDAO() {
		return proyectoDAO;
	}

	public void setGeneralDAO(IGeneralDAO generalDAO) {
		this.generalDAO = generalDAO;
	}

	public Object obtenerSumaGastoXTipoRubroYVigenciaYProyecto(Long idProyecto, Long tipoRubro, Integer vigencia) {
		return proyectoDAO.obtenerSumaGastoXTipoRubroYVigenciaYProyecto(idProyecto, tipoRubro,
				new Long(vigencia.intValue()));
	}

	public List obtenerDiferentesVigenciasXProyecto(Long idProyecto) {
		return proyectoDAO.obtenerDiferentesVigenciasXProyecto(idProyecto);
	}

	public List obtenerDiferentesTipoRubroXProyecto(Long idProyecto) {
		return proyectoDAO.obtenerDiferentesTipoRubroXProyecto(idProyecto);
	}

	public ProyectoEvaluador obtenerProyectoEvaluadorConTipoFinancioacion(ProyectoEvaluador pe) {
		return proyectoDAO.obtenerProyectoEvaluadorConTipoFinancioacion(pe);
	}

	public void guardarEvaluacionProyectoConTipoFinanciacion(ProyectoEvaluador evaluacionProyecto)
			throws DataAccessException {
		proyectoDAO.guardarEvaluacionProyectoConTipoFinanciacion(evaluacionProyecto);
	}

	public List obtenerActividadesPersonaPorActividad(Long idActividad) {
		return proyectoDAO.obtenerActividadesPersonaPorActividad(idActividad);
	}

	public List obtenerListaDeProyectosXGrupoYEstado(Grupo g, EstadoProyecto e) {
		return proyectoDAO.obtenerListaDeProyectosXGrupoYEstado(g, e);
	}

	public String obtenerStringEntidadesFinanciadorasProyecto(Proyecto p) {
		StringBuffer sb = new StringBuffer();
		double total = 0;
		Proyecto pr = obtenerProyecto(p.getId(), ProyectoDAOHibernate.FUENTES_FINANCIACION);
		for (Iterator it = pr.getFinanciaciones().iterator(); it.hasNext();) {
			Financiacion f = (Financiacion) it.next();
			sb.append(f.getFuente().getDescripcion() + ",");
			total += f.getValor().longValue();
		}
		return sb.toString();
	}

	public long obtenerLongCantidadFinanciadaProyecto(Proyecto p) {
		long total = 0;
		Proyecto pr = obtenerProyecto(p.getId(), ProyectoDAOHibernate.FUENTES_FINANCIACION);
		for (Iterator it = pr.getFinanciaciones().iterator(); it.hasNext();) {
			Financiacion f = (Financiacion) it.next();
			total += f.getValor().longValue();
		}
		return total;
	}

	public List obtenerListaActividadesPersonaXActividadYPersona(Actividad a, IdPersona id) {
		return proyectoDAO.obtenerListaActividadesPersonaXActividadYPersona(a, id);
	}

	public boolean seTranslapanActividadPersona(ActividadPersona ap, List listaActividadPersona) {
		for (Iterator itActividadPersona = listaActividadPersona.iterator(); itActividadPersona.hasNext();) {
			ActividadPersona actividadPersona = (ActividadPersona) itActividadPersona.next();
			if (UtilidadesVarias.seTranslapanIntervalosInclusivo(actividadPersona.getSemanaInicial().intValue(),
					actividadPersona.getDuracionSemanas().intValue() + actividadPersona.getSemanaInicial().intValue(),
					ap.getSemanaInicial().intValue(),
					ap.getSemanaInicial().intValue() + ap.getDuracionSemanas().intValue())) {
				return true;
			}

		}
		return false;

	}
	
	public List obtenerProyectosXModalidadEInvestigador(Modalidad tm, Investigador i, String estadosProyecto) {
		return proyectoDAO.obtenerProyectosXModalidadEInvestigador(tm, i, estadosProyecto);
	}
	
	public List obtenerProyectosXModalidadEInvestigadorCortes(Modalidad tm, Investigador i, String estadosProyecto, Long idCorte) {
		return proyectoDAO.obtenerProyectosXModalidadEInvestigadorCortes(tm, i, estadosProyecto, idCorte);
	}
	
	public List obtenerProyectosXModalidadEInvestigadorPrincipal(Modalidad tm, Investigador i, String estadosProyecto) {
		return proyectoDAO.obtenerProyectosXModalidadEInvestigadorPrincipal(tm, i, estadosProyecto);
	}
	
	public List obtenerProyectosXModalidadEInvestigadorPrincipalCortes(Modalidad tm, Investigador i, String estadosProyecto, Long idCorte) {
		return proyectoDAO.obtenerProyectosXModalidadEInvestigadorPrincipalCortes(tm, i, estadosProyecto, idCorte);
	}

	public List obtenerProyectosXTipoModalidadYInvestigadorPrincipal(TipoModalidad tm, Investigador i) {
		return proyectoDAO.obtenerProyectosXTipoModalidadYInvestigadorPrincipal(tm, i);
	}

	public String guardaProyectoConHistoricoEstadoDeBD(Proyecto p, String justificacion) {
		/*************************************************************************
		 * NO CAMBIAR LOS STRING DE SALIDA PUES CON ESTOS SE REALIZAN
		 * VALIDACIONES
		 *************************************************************************/
		// Proyecto pDB =
		// obtenerProyecto(p.getId(),ProyectoDAOHibernate.DATOS_BASICOS);
		EstadoProyecto fuente = proyectoDAO.obtenerEstadoProyectoXProyecto(p.getId());
		EstadoProyecto destino = p.getEstadoProyecto();
		String resultadoActualizacion = "";

		HashMap listaDeCambios = new HashMap();

		List destinoActivo = new Vector();
		destinoActivo.add(EstadoProyecto.CANCELADO);
		destinoActivo.add(EstadoProyecto.FINALIZADO);
		destinoActivo.add(EstadoProyecto.SUSPENDIDO);
		listaDeCambios.put(EstadoProyecto.ACTIVO, destinoActivo);

		List destinoIngresando = new Vector();
		destinoIngresando.add(EstadoProyecto.SUSPENDIDO);
		destinoIngresando.add(EstadoProyecto.PROPUESTO);
		destinoIngresando.add(EstadoProyecto.BORRADO);
		listaDeCambios.put(EstadoProyecto.INGRESANDO, destinoIngresando);

		List destinoAprobado = new Vector();
		destinoAprobado.add(EstadoProyecto.ACTIVO);
		listaDeCambios.put(EstadoProyecto.APROBADO, destinoAprobado);

		// List destinoBorrado=new Vector();
		listaDeCambios.put(EstadoProyecto.BORRADO, null);

		List destinoCancelado = new Vector();
		destinoCancelado.add(EstadoProyecto.ACTIVO);
		listaDeCambios.put(EstadoProyecto.CANCELADO, destinoCancelado);

		// List destinoElegible=new Vector();
		// destinoElegible.add(EstadoProyecto.)
		// listaDeCambios.put(EstadoProyecto.ELEGIBLE, destinoElegible);

		// List destinoFinalizado=new Vector();
		listaDeCambios.put(EstadoProyecto.FINALIZADO, null);

		// List destinoHistorico=new Vector();
		listaDeCambios.put(EstadoProyecto.HISTORICO, null);

		// List destinoNegado=new Vector();
		listaDeCambios.put(EstadoProyecto.NEGADO, null);

		List destinoPropuesto = new Vector();
		destinoPropuesto.add(EstadoProyecto.NEGADO);
		destinoPropuesto.add(EstadoProyecto.RECHAZADO);
		destinoPropuesto.add(EstadoProyecto.APROBADO);
		listaDeCambios.put(EstadoProyecto.PROPUESTO, destinoPropuesto);

		// List destinoRechazado=new Vector();
		listaDeCambios.put(EstadoProyecto.RECHAZADO, null);

		List destinoSuspendido = new Vector();
		destinoSuspendido.add(EstadoProyecto.ACTIVO);
		destinoSuspendido.add(EstadoProyecto.CANCELADO);
		listaDeCambios.put(EstadoProyecto.SUSPENDIDO, destinoSuspendido);

		if (fuente != null && fuente.getId() != null && destino != null && destino.getId() != null) {
			if (!fuente.getId().equals(destino.getId())) {

				if (listaDeCambios.keySet().contains(fuente.getId())) {
					if (listaDeCambios.get(fuente.getId()) != null) {
						List destinos = (List) listaDeCambios.get(fuente.getId());

						if (destinos.contains(destino.getId())) {

							HistoricoEstadoProyecto hep = new HistoricoEstadoProyecto();
							hep.setEstadoProyecto(destino);
							hep.setFecha(new Date());
							hep.setJustificacion(justificacion);
							hep.setProyecto(p);
							p.setEstadoProyecto(destino);
							guardarProyecto(p);
							generalDAO.insertarObjeto(hep);
							resultadoActualizacion = "Guardado satisfactorio";
						} else {
							resultadoActualizacion = "No se puede cambiar el estado del proyecto de "
									+ fuente.getNombre() + " a " + destino.getNombre();
						}
					}
				} else {
					resultadoActualizacion = "Estado original no disponible en el diagrama de estados";
				}
			} else {
				guardarProyecto(p);
				resultadoActualizacion = "Proyecto guardado sin cambio de estado";
			}
		} else {
			resultadoActualizacion = "Estado nulo";
		}
		return resultadoActualizacion;
	}

	public String guardaProyectoConHistoricoEstadoDeBD(Proyecto p, String justificacion, Date fechaHistorico) {
		/*************************************************************************
		 * NO CAMBIAR LOS STRING DE SALIDA PUES CON ESTOS SE REALIZAN
		 * VALIDACIONES
		 *************************************************************************/
		// Proyecto pDB =
		// obtenerProyecto(p.getId(),ProyectoDAOHibernate.DATOS_BASICOS);
		EstadoProyecto fuente = proyectoDAO.obtenerEstadoProyectoXProyecto(p.getId());
		EstadoProyecto destino = p.getEstadoProyecto();
		String resultadoActualizacion = "";

		HashMap listaDeCambios = new HashMap();

		List destinoActivo = new Vector();
		destinoActivo.add(EstadoProyecto.CANCELADO);
		destinoActivo.add(EstadoProyecto.FINALIZADO);
		destinoActivo.add(EstadoProyecto.SUSPENDIDO);
		listaDeCambios.put(EstadoProyecto.ACTIVO, destinoActivo);

		List destinoIngresando = new Vector();
		destinoIngresando.add(EstadoProyecto.SUSPENDIDO);
		destinoIngresando.add(EstadoProyecto.PROPUESTO);
		destinoIngresando.add(EstadoProyecto.BORRADO);
		listaDeCambios.put(EstadoProyecto.INGRESANDO, destinoIngresando);

		List destinoAprobado = new Vector();
		destinoAprobado.add(EstadoProyecto.ACTIVO);
		listaDeCambios.put(EstadoProyecto.APROBADO, destinoAprobado);

		// List destinoBorrado=new Vector();
		listaDeCambios.put(EstadoProyecto.BORRADO, null);

		List destinoCancelado = new Vector();
		destinoCancelado.add(EstadoProyecto.ACTIVO);
		listaDeCambios.put(EstadoProyecto.CANCELADO, destinoCancelado);

		// List destinoElegible=new Vector();
		// destinoElegible.add(EstadoProyecto.)
		// listaDeCambios.put(EstadoProyecto.ELEGIBLE, destinoElegible);

		// List destinoFinalizado=new Vector();
		listaDeCambios.put(EstadoProyecto.FINALIZADO, null);

		// List destinoHistorico=new Vector();
		listaDeCambios.put(EstadoProyecto.HISTORICO, null);

		// List destinoNegado=new Vector();
		listaDeCambios.put(EstadoProyecto.NEGADO, null);

		List destinoPropuesto = new Vector();
		destinoPropuesto.add(EstadoProyecto.NEGADO);
		destinoPropuesto.add(EstadoProyecto.RECHAZADO);
		destinoPropuesto.add(EstadoProyecto.APROBADO);
		listaDeCambios.put(EstadoProyecto.PROPUESTO, destinoPropuesto);

		// List destinoRechazado=new Vector();
		listaDeCambios.put(EstadoProyecto.RECHAZADO, null);

		List destinoSuspendido = new Vector();
		destinoSuspendido.add(EstadoProyecto.ACTIVO);
		destinoSuspendido.add(EstadoProyecto.CANCELADO);
		listaDeCambios.put(EstadoProyecto.SUSPENDIDO, destinoSuspendido);

		if (fuente != null && fuente.getId() != null && destino != null && destino.getId() != null) {
			if (!fuente.getId().equals(destino.getId())) {

				if (listaDeCambios.keySet().contains(fuente.getId())) {
					if (listaDeCambios.get(fuente.getId()) != null) {
						List destinos = (List) listaDeCambios.get(fuente.getId());

						if (destinos.contains(destino.getId())) {

							HistoricoEstadoProyecto hep = new HistoricoEstadoProyecto();
							hep.setEstadoProyecto(destino);
							hep.setFecha(fechaHistorico);
							hep.setJustificacion(justificacion);
							hep.setProyecto(p);
							p.setEstadoProyecto(destino);
							guardarProyecto(p);
							generalDAO.insertarObjeto(hep);
							resultadoActualizacion = "Guardado satisfactorio";
						} else {
							resultadoActualizacion = "No se puede cambiar el estado del proyecto de "
									+ fuente.getNombre() + " a " + destino.getNombre();
						}
					}
				} else {
					resultadoActualizacion = "Estado original no disponible en el diagrama de estados";
				}
			} else {
				guardarProyecto(p);
				resultadoActualizacion = "Proyecto guardado sin cambio de estado";
			}
		} else {
			resultadoActualizacion = "Estado nulo";
		}
		return resultadoActualizacion;
	}

	public List obtenerObservacionesProyecto(Proyecto proyecto) {
		return proyectoDAO.obtenerObservacionesProyecto(proyecto);
	}

	public List obtenerListaEquipos2XProyecto(Long idProyecto) {
		return proyectoDAO.obtenerListaEquipos2XProyecto(idProyecto);
	}

	public Investigador obtenerInvestigadorPrincipalXProyecto(Long idProyecto) {
		return proyectoDAO.obtenerInvestigadorPrincipalXProyecto(idProyecto);
	}

	public Investigador obtenerInvestigadorPrincipalXCreador(String tipoDocumento, String documento) {
		return proyectoDAO.obtenerInvestigadorPrincipalXCreador(tipoDocumento, documento);
	}

	public Investigador estaInvestigadorEnProyecto(Investigador i, Long idProyecto) {
		return proyectoDAO.estaInvestigadorEnProyecto(i, idProyecto);
	}

	public List<Solicitud> obtenerSolicitudesProyecto(Proyecto proyecto, boolean incluirGuardados, boolean incluirDevueltos) {
		return proyectoDAO.obtenerSolicitudesProyecto(proyecto, incluirGuardados, incluirDevueltos);
	}

	public List<InvestigadorProyecto> obtenerInvestigadoresProyecto(Long idProyecto) {
		return proyectoDAO.obtenerInvestigadoresProyecto(idProyecto);
	}

	public List obtenerEvaluadoresProyecto(Long idProyecto) {
		return proyectoDAO.obtenerEvaluadoresProyecto(idProyecto);
	}

	public List obtenerGastosProyecto(Long idProyecto) {
		return proyectoDAO.obtenerGastosProyecto(idProyecto);
	}

	public List buscar(Persona asesor, Long idProyecto, String codigoDIBProyecto, String nombreProyecto,
			EstadoProyecto estadoProyecto, Long idConvocatoria, Long idModalidad, IdPersona idEvaluador,
			IdPersona idInvestigadorPrincipal, IdPersona idParticipante, String palabraClave, String idFacultad,
			Long ano, String idClasificacionConocimiento, String idDepartamento) {
		return proyectoDAO.buscar(asesor, idProyecto, codigoDIBProyecto, nombreProyecto, estadoProyecto, idConvocatoria,
				idModalidad, idEvaluador, idInvestigadorPrincipal, idParticipante, palabraClave, idFacultad, ano,
				idClasificacionConocimiento, idDepartamento);
	}

	public List<Financiacion> obtenerFunetesFinanciacionProyecto(Long idProyecto) {
		return proyectoDAO.obtenerFunetesFinanciacionProyecto(idProyecto);
	}

	public List<Financiacion> obtenerFunetesFinanciacionConGastosProyecto(Long idProyecto) {
		return proyectoDAO.obtenerFunetesFinanciacionConGastosProyecto(idProyecto);
	}

	public List<Gasto> obtenerGastosProyectoFinanciacion(Long idFinanciacion) {
		return proyectoDAO.obtenerGastosProyectoFinanciacion(idFinanciacion);
	}

	public String generarCodigoDi(String codigoDiTipoModalidad, Long idProyecto) {
		return proyectoDAO.generarCodigoDi(codigoDiTipoModalidad, idProyecto);
	}

	public boolean tienePrincipalProyecto(Long idProyecto) {
		Investigador i = obtenerInvestigadorPrincipalXProyecto(idProyecto);
		return (i != null);
	}

	public Ciudad obtenerCiudadProyecto(Long idProyecto) {
		return proyectoDAO.obtenerCiudadProyecto(idProyecto);
	}

	public Persona obtenerCoordinadorProyecto(Long idProyecto) {
		return proyectoDAO.obtenerCoordinadorProyecto(idProyecto);
	}

	public Persona obtenerCoordinadorEvaluacionProyecto(Long idProyecto) {
		return proyectoDAO.obtenerCoordinadorEvaluacionProyecto(idProyecto);
	}

	public Persona obtenerCoordinadorRequisitosProyecto(Long idProyecto) {
		return proyectoDAO.obtenerCoordinadorRequisitosProyecto(idProyecto);
	}

	public String validarProrrogaProyecto(ProyectoProrroga prorroga) {
		String mensajeValidacion = "";
		try {
			if (prorroga.getProyecto() == null || prorroga.getProyecto().getId() == null) {
				mensajeValidacion = "Debe asociar el proyecto al cual se realiza la prorroga";
				throw new Exception(mensajeValidacion);
			}
			if (prorroga.getDuracion() == null
					|| (prorroga.getDuracion() != null && prorroga.getDuracion().equals(0L))) {
				boolean error = true;
				if (prorroga.getDias() != null && prorroga.getDias().longValue() > 0) {
					error = false;
				}
				if (error) {
					mensajeValidacion = "Debe ingresar la duración de la prorroga";
					throw new Exception(mensajeValidacion);
				}
			}
			if (Integer.parseInt(prorroga.getMesesVista()) < 0 || Integer.parseInt(prorroga.getMesesVista()) > 99) {
				mensajeValidacion = "La duración de la prorroga debe estar en el rango 0 - 99";
				throw new Exception(mensajeValidacion);
			}
		} catch (Exception ex) {
			mensajeValidacion += " " + ex.toString();
		}
		return mensajeValidacion;
	}

	public String validarObservacionProyecto(ObservacionProyecto observacion) {
		String mensajeValidacion = "";

		try {
			if (observacion.getAsesor() == null || observacion.getAsesor().getId() == null
					|| observacion.getAsesor().getId().getDocumento() == null
					|| observacion.getAsesor().getId().getDocumento().equals("")
					|| observacion.getAsesor().getId().getTipoDocumento() == null
					|| observacion.getAsesor().getId().getTipoDocumento().equals("")) {
				mensajeValidacion = "Debe ingresar la información del asesor que realiza la observación";
				throw new Exception(mensajeValidacion);
			}

			if (observacion.getFecha() == null) {
				mensajeValidacion = "Debe ingresar la fecha en la que realiza la observación";
				throw new Exception(mensajeValidacion);
			}

			if (observacion.getDescripcion() == null || observacion.getDescripcion().equals("")) {
				mensajeValidacion = "Debe ingresar la observación";
				throw new Exception(mensajeValidacion);
			}

			if (observacion.getProyecto() == null || observacion.getProyecto().getId() == null) {
				mensajeValidacion = "Debe ingresar el proyecto al que se realiza la observación";
				throw new Exception(mensajeValidacion);
			}
		} catch (Exception ex) {
		}

		return mensajeValidacion;
	}

	public Long obtenerMontoAprobadoProyecto(Long idProyecto, String tipoFinanciacion) {
		return proyectoDAO.obtenerMontoAprobadoProyecto(idProyecto, tipoFinanciacion);
	}
	
	public Long obtenerMontoContrapartidaPersonal(Long idProyecto) {
		return proyectoDAO.obtenerMontoContrapartidaPersonal(idProyecto);
	}

	public Long obtenerMontoAprobadoProyectoLaboratorios(Long idProyecto, Long idModalidad) {
		return proyectoDAO.obtenerMontoAprobadoProyectoLaboratorios(idProyecto, idModalidad);
	}

	public List obtenerProyectoXId(String id) {

		return proyectoDAO.obtenerProyectoXId(id);
	}
	
	public List obtenerProyectosXEvaluador(IdPersona id){
		return proyectoDAO.obtenerProyectosXEvaluador(id);
	}

	public List obtenerProyectoXCordinador(IdPersona id) {
		return proyectoDAO.obtenerProyectoXCordinador(id);
	}

	public Date obtenerFechaInicioProyecto(Long idProyecto) {
		return proyectoDAO.obtenerFechaInicioProyecto(idProyecto);
	}

	public boolean tieneProyectosRefinanciar(IdPersona idInvestigador) {
		return proyectoDAO.tieneProyectosRefinanciar(idInvestigador);
	}

	public List obtenerProyectosRefinanciar(IdPersona idInvestigador) {
		return proyectoDAO.obtenerProyectosRefinanciar(idInvestigador);
	}

	public ProyectoCoordinador obtenerProyectoCoordinadorxCodigo(String codigo) {

		return proyectoDAO.obtenerProyectoCoordinadorxCodigo(codigo);
	}
	
	public ProyectoCoordinadorEditorial obtenerProyectoCoordinadorEditorialCodigo(String codigo) {

		return proyectoDAO.obtenerProyectoCoordinadorEditorialCodigo(codigo);
	}
	
	

	public List obtenerProyectosCoordinadorxModalidadxSede(Modalidad mod, String dep) throws DataAccessException {
		return proyectoDAO.obtenerProyectosCoordinadorxModalidadxSede(mod, dep);
	}

	public List obtenerProyectosCoordinadorAsesorxModalidadxFacultad(Modalidad mod, String idFac, String idEstProy) {
		return proyectoDAO.obtenerProyectosCoordinadorAsesorxModalidadxFacultad(mod, idFac, idEstProy);
	}

	public List obtenerProyectosCoordinadorAsesorxModalidadxFacultad(Modalidad mod, String idFac) {
		return proyectoDAO.obtenerProyectosCoordinadorAsesorxModalidadxFacultad(mod, idFac);
	}

	public List<ProyectoCoordinador> obtenerProyectosCoordinadorRevisionxModalidadSede(Modalidad mod, Dependencia dep, Persona pep)
			throws DataAccessException {
		return proyectoDAO.obtenerProyectosCoordinadorRevisionxModalidadSede(mod, dep, pep);
	}
	
	public List<ProyectoCoordinadorEditorial> obtenerProyectosCoordinadorEditorialRevisionxModalidadSede(Modalidad mod, Dependencia dep, Persona pep)
			throws DataAccessException {
		return proyectoDAO.obtenerProyectosCoordinadorEditorialRevisionxModalidadSede(mod, dep, pep);
	}

	public List<Proyecto> obtenerProyectosCompromisosPendientes(Persona coordinador, String modalidad)
			throws DataAccessException {
		return proyectoDAO.obtenerProyectosCompromisosPendientes(coordinador, modalidad);
	}
	
	/*Retorna todos los productos relacionados con formación estudiantes de un proyecto
     * LO
     */
    public List getFormacionPorProyecto(Long idProyecto){
            String idFormacionBD = "42";
        List productosProyecto = new ArrayList();
        List result = proyectoDAO.getProductosProyectoAEntregar(idProyecto);
        
        for(Iterator iterador =  result.iterator();iterador.hasNext(); )
        {
            ProyectoProducto proyectoProducto = (ProyectoProducto)iterador.next();            
            
            if(proyectoProducto.getProducto().getPadre().getId().equals(idFormacionBD)){
                    ProyectoProducto proyectoProductoNew = new ProyectoProducto();
                proyectoProductoNew.setId( proyectoProducto.getId() );
                proyectoProductoNew.setProducto( proyectoProducto.getProducto() );
                proyectoProductoNew.setProyecto( proyectoProducto.getProyecto() );
                proyectoProductoNew.setCantidad(proyectoProducto.getCantidad());                
                productosProyecto.add(proyectoProductoNew);
            }
        }
        return productosProyecto;
    }
    
    /*Retorna todos los productos de un proyecto sin incluir el producto : Estudiante en formación
     * LO
     */
    public List<ProyectoProducto> getProductosPorProyecto(Long idProyecto){
        List productosProyecto = new ArrayList();
        List result = proyectoDAO.getProductosProyectoAEntregar(idProyecto);
        
        for(Iterator iterador =  result.iterator();iterador.hasNext(); )
        {
            ProyectoProducto proyectoProducto = (ProyectoProducto)iterador.next();
            
                ProyectoProducto proyectoProductoNew = new ProyectoProducto();
                
                if(!proyectoProducto.getProducto().getNombre().equals("Estudiante en formación")){
                    proyectoProductoNew.setId( proyectoProducto.getId() );
                    proyectoProductoNew.setProducto( proyectoProducto.getProducto() );
                    proyectoProductoNew.setProyecto( proyectoProducto.getProyecto() );
                    proyectoProductoNew.setCantidad(proyectoProducto.getCantidad());                
                    productosProyecto.add(proyectoProductoNew);
                }
                
            
        }
        return productosProyecto;
    }
    
    public List<SelectItem> crearSelectItemObjetivos(List<ObjetivoEspecifico> objetivos) {
        List<SelectItem> listaItems = new ArrayList<SelectItem>();
        if (objetivos != null && !objetivos.isEmpty()) {
            Iterator<ObjetivoEspecifico> i = objetivos.iterator();
            while (i.hasNext()) {
            	ObjetivoEspecifico objetivo = i.next();
                listaItems.add(new SelectItem(objetivo.getNumeroOrden().toString(), objetivo.getNumeroOrden().toString() + " - " + objetivo.getNombre()));
            }
        }
        return listaItems;
    }
    
    public List<SelectItem> crearSelectItemMetas(List<MetaProyecto> metas) {
        List<SelectItem> listaItems = new ArrayList<SelectItem>();
        if (metas != null && !metas.isEmpty()) {
            Iterator<MetaProyecto> i = metas.iterator();
            while (i.hasNext()) {
            	MetaProyecto meta = i.next();
                listaItems.add(new SelectItem(meta.getId().toString(), meta.getObjetivo().getNumeroOrden() + "." + meta.getNumeroOrden() + " - " + meta.getNombre()));
            }
        }
        return listaItems;
    }
    
    public List<SelectItem> crearSelectItemResultados(List<ResultadoProyecto> resultados) {
        List<SelectItem> listaItems = new ArrayList<SelectItem>();
        if (resultados != null && !resultados.isEmpty()) {
            Iterator<ResultadoProyecto> i = resultados.iterator();
            while (i.hasNext()) {
            	ResultadoProyecto resultado = i.next();
                listaItems.add(new SelectItem(resultado.getId().toString(), resultado.getNumeroOrden().toString() + " - " + resultado.getDescripcion()));
            }
        }
        return listaItems;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioProyecto.IServicioProyecto#
     * obtenerCartasXProyecto(co.edu.unal.hermes.modelo.Proyecto)
     */
    public List<ProyectoCarta> obtenerCartasXProyecto(Proyecto proyecto) {
        return proyectoDAO.obtenerCartasXProyecto(proyecto.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioProyecto.IServicioProyecto#
     * obtenerObservacionesSeguimientoXProyecto(co.edu.unal.hermes.modelo.
     * Proyecto)
     */
    public List<ObservacionSeguimiento> obtenerObservacionesSeguimientoXProyecto(Proyecto proyecto) {
        return proyectoDAO.obtenerObservacionesSeguimientoXProyecto(proyecto.getId());
    }
    
    public List<CantidadMaterialGrafico> getCantidadesMaterialGraficoProyecto(Long idProyecto) {
        return proyectoDAO.getCantidadesMaterialGraficoProyecto(idProyecto);
    }
    
    public List<CantidadMaterialGrafico> getCantidadesMaterialGraficoNuevo() {
    	
    	List<CantidadMaterialGrafico> listaCantidadMaterialGrafico = new ArrayList<CantidadMaterialGrafico>();
    	
        List<TipoMaterialGrafico> list =  proyectoDAO.getTiposMaterialGrafico();
        
        if(list != null) {
	        for(TipoMaterialGrafico tmg : list) {
	        	listaCantidadMaterialGrafico.add(new CantidadMaterialGrafico(tmg));
	        }
        }
        
        return listaCantidadMaterialGrafico;        
    }

	@Override
	public List<ProyectoCoordinadorEditorial> obtenerProyectosEditorialCoordinadorxModalidadxSede(
			Modalidad buscarModalidadxId, String sede, String estado) {
		return proyectoDAO.obtenerProyectosEditorialCoordinadorxModalidadxSede(
				buscarModalidadxId, sede, estado);
	}

	@Override
	public Persona obtenerCoordinadorRequisitosProyectoEditorial(Long id) {
		// TODO Auto-generated method stub
		return proyectoDAO.obtenerCoordinadorRequisitosProyectoEditorial(id);
	}

	@Override
	public Proyecto obtenerProyectoEditorialEvaluacion(Long id, IdPersona id2) {
		// TODO Auto-generated method stub
		return proyectoDAO.obtenerProyectoEditorialEvaluacion(id, id2);
	}
}
