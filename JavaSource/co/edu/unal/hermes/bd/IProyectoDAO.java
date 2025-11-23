package co.edu.unal.hermes.bd;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.Actividad;
import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.modelo.ArchivoRequerimiento;
import co.edu.unal.hermes.modelo.Aval;
import co.edu.unal.hermes.modelo.Ciudad;
import co.edu.unal.hermes.modelo.Convocatoria;
import co.edu.unal.hermes.modelo.ConvocatoriaExterna;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.EstadoProyecto;
import co.edu.unal.hermes.modelo.Financiacion;
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
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.ObjetivoEspecifico;
import co.edu.unal.hermes.modelo.ObservacionSeguimiento;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.ProyectoCarta;
import co.edu.unal.hermes.modelo.ProyectoCoordinadorEditorial;
import co.edu.unal.hermes.modelo.ProyectoEvaluador;
import co.edu.unal.hermes.modelo.ProyectoInforme;
import co.edu.unal.hermes.modelo.TipoModalidad;
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleProyectos;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudInvestigador;
import co.edu.unal.hermes.modelo.seguimiento.SolicitudProrrogaInvestigador;
import co.edu.unal.hermes.vista.evaluadores.ProyectoCoordinador;
import co.edu.unal.hermes.vista.proyectos.ProyectoVista;
import co.edu.unal.hermes.vista.proyectos.ProyectosVistaOficiosConvocatoria;

/**
 * Interface para obtener, buscar y guardar proyectos
 * Obtiene y guarda histórico del estado del proyecto
 * obtiene histórico de los estados del proyecto
 * obtiene la lista de los tipos de investigación
 * @trows lanza la excepcion cuando no se puede acceder a la información de los grupos 
 */
public interface IProyectoDAO {
	
	public List obtenerProyectosCoordinadorxModalidadxSede(Modalidad mod,String dep, String idEstPry);
	public List obtenerProyectosCoordinador(Modalidad mod, String documento, String tipodoc,boolean Elegibles) throws DataAccessException;
	public List obtenerProyectosCoordinadorxModalidadSede(Modalidad mod,Dependencia dep,Persona pep) throws DataAccessException ;
	public List obtenerProyectosCoordinadorRevisionxModalidadSede(Modalidad mod,Dependencia dep,Persona pep) throws DataAccessException ;	
	public List obtenerProyectosCoordinadorxModalidadxFacultadEstado(Modalidad mod, String idFac) throws DataAccessException;
	public List<ProyectoCoordinador> obtenerProyectosCoordinadorxIdProyecto(	String id ) throws DataAccessException;
	public List obtenerProyectosPorCriterio(String sql) throws DataAccessException;
	public List obtenerProyectos(String where, boolean isParametros, Object[] parametros) throws DataAccessException;
	public List<Proyecto> obtenerProyectoBuscador(String sql, String facultad, String sedep, String investigador, String area, String palabra) throws DataAccessException;
	public List<ConvocatoriaExterna> obtenerConvocatorias(String sql, String tipo) throws DataAccessException;
	public void imprimirReporteProyecto(Proyecto proyectoActual, HttpSession sesion, Boolean esEvaluador);
	public void imprimirReporteProyectoEditorial(Long idproyecto, HttpSession sesion, Boolean esEvaluador);
	public void imprimirReporteProyectoLegalizacion(Proyecto proyectoActual, HttpSession sesion);
	public String consultarProyectoGenerico(Proyecto proyecto, HttpSession sesion, Convocatoria conv);
	public boolean validarVinculacionPersona(InvestigadorInterno invI, boolean permitirDocentesCatedra);
	public boolean validarGanadorSemilleros(String idPersona, String tipoDocumento, String id);
	public List<ProyectoEvaluador> obtenerProyectosEvaluador(Long idProyecto) throws DataAccessException;
	public List<ProyectoInforme> obtenerProyectoInforme(Long idProyecto) throws DataAccessException ;
	public List obtenerProyectos(String name) throws DataAccessException;    
    public List obtenerProyectosPorCriterio(Proyecto proyecto) throws DataAccessException;
    Proyecto buscarPorId(Long id, short informacion, boolean incluirGastos) throws DataAccessException;
    public Laboratorio buscarLaboratorioPorId(Long id, short informacion) throws DataAccessException;
    public Proyecto buscarPorIdAsignadosEvaluacion(Long id, IdPersona idAsesor) throws DataAccessException;
    public Proyecto obtenerResumenProyecto(Long id) throws DataAccessException;
    public Proyecto obtenerResumenProyectoExtension(Long id) throws DataAccessException;
    public List obtenerProyectosCoordinadorAsesorxModalidadxFacultad(Modalidad mod,String idFac, String idEstProy) throws DataAccessException;
    public List obtenerProyectosCoordinadorAsesorxModalidadxFacultad(Modalidad mod,String idFac) throws DataAccessException;
    public void guardarProyecto(Proyecto proyecto) throws DataAccessException;
    public List<HistoricoEstadoProyecto> obtenerHistoricosEstadoProyecto(Long id) throws DataAccessException;
    public List<HistoricoEstadoLegalizacion> obtenerHistoricosEstadosLegalizacion(Long pryId);
    public List<HistoricoEstadoAval> obtenerHistoricosEstadoAval(Long id) throws DataAccessException;
    public List obtenerTiposInvestigacion() throws DataAccessException;
    public List<ObjetivoEspecifico> obtenerObjetivosEspeficicos(Proyecto pry) throws DataAccessException;
    public List<HistoricoAsignacionProyecto> obtenerHistoricosAsignacionProyecto(Long pryId);
    public List<HistoricoHabilitacionEdicionProyecto> obtenerHistoricosHabilitacionEdicionProyecto(Long pryId);
    public List<HistoricoEstadoInforme> obtenerHistoricosEstadoInforme(Long informeId);   
    
    public List obtenerFuentesFinanciacionInternas() throws DataAccessException;
    public List obtenerFuentesFinanciacionExternas() throws DataAccessException;
    public Archivo obtenerArchivo(Long id) throws DataAccessException;
    public void guardarActividad(Actividad actividad) throws DataAccessException;
             
    public void eliminarProyecto(Long id) throws DataAccessException;
    public List<Archivo> obtenerNombresArchivos(Proyecto proyecto) throws DataAccessException;
    public List<Archivo> obtenerNombresArchivosReclamacion(Proyecto proyecto) throws DataAccessException;
    public List<Archivo> obtenerNombresArchivosTipos(Proyecto proyecto) throws DataAccessException;
    public List<Archivo> obtenerNombresArchivosConTipos(Proyecto proyecto) throws DataAccessException;
    
    public void eliminarInvestigadorProyecto(InvestigadorProyecto investigadorProyecto) throws DataAccessException;
    public List obtenerProyectosTituloConvocatoria(String nombre, Modalidad mod) throws DataAccessException;
    public Proyecto obtenerProyectoHistorico(Long id) throws DataAccessException;
    public ProyectoEvaluador obtenerProyectoEvaluador(ProyectoEvaluador pe) throws DataAccessException;
    public void guardarEvaluacionProyecto(ProyectoEvaluador evaluacionProyecto) throws DataAccessException;
    
    public Proyecto obtenerProyectoCodigoDib(String codigoDib) throws DataAccessException;
    public List obtenerProyectosxEjemplo(Proyecto pry);
    public List obtenerProyectosxModalidad(Modalidad mod) throws DataAccessException;
    public List obtenerProyectosXEvaluador(IdPersona id) throws DataAccessException;
     
     List obtenerProyectosConvocatoria(EstadoProyecto[]  estados, Modalidad mod);
    public Financiacion obtenerFinanciacionGastos(Long id) throws DataAccessException;
    
    //TODO ARREGLAR ESTE METODO, NO BTRABAJA DEL TODO BIEN
    List obtenerProyectosConvocatoriaConInvestigadores(EstadoProyecto[]  estados, Modalidad mod) throws DataAccessException;
   
    public List obtenerProyectosPorNombreDependencia(String nombreProyecto, Dependencia dependencia, String tipoDependencia) throws DataAccessException;

    public List obtenerAreasTematicas(Long id);
    public List<ProyectosVistaOficiosConvocatoria> obtenerProyectosXEstadoYConvocatoriaPadre(Long idConvocatoriaPadre, String idEstado) throws DataAccessException;
    public List<ProyectosVistaOficiosConvocatoria> obtenerProyectosXEstadoYConvocatoriaPadreYCorte(Long idConvocatoriaPadre, String idEstado, Long corte) throws DataAccessException;
    public List obtenerProyectosCoordinadorxModalidad(Modalidad mod) throws DataAccessException;
    public List obtenerProyectosCoordinadorxModalidadxEstado(Modalidad mod,String idEstado) throws DataAccessException;
    public List obtenerProyectosCoordinadorxModalidadxFacultad(Modalidad mod,String idFac) throws DataAccessException;
    public List<ProyectoCoordinador> obtenerProyectosxId(String idDoc,String tipoDoc, String idPro) throws DataAccessException;
    
    public void modificarCoordinadorEnProyecto(int tipo, String idProyecto, IdPersona idPersona, Persona persona, String tipoCoordinador, boolean guardarLog) throws DataAccessException;
    public void modificarCoordinadorEnProyecto(int tipoModificacion, String tipoPersona, String idProyecto, IdPersona idPersona, Persona persona, String tipoCoordinador,boolean guardarLog) throws DataAccessException;
    
    /**
     * Se obtienen todos los integrates de proyectos segun los estados de proyectos
     * @param estadosProyectos
     * @return lista de investigadores
     */
    public List obtenerIntegrantesProyectosXEstados(List estadosProyectos);
    public List obtenerProyectoEvaluadoresInternos(Long idProyecto, Modalidad mod) throws DataAccessException;
    public long obtenerFiananciacionXProyectoYFuente(Long idProyecto,String fuente);
	/**
	 * @param nombreProyecto
	 * @param estadoProyecto
	 * @return
	 */
	public List obtenerProyectosPorNombreEstado(String nombreProyecto, EstadoProyecto estadoProyecto);
	public List obtenerProyectosXNombre(String title);
	public List obtenerProyectosXCodigoQuipu(String codigo);
	public List obtenerProyectosXNombreYEstadoDiferente(String name,String idEstado) throws DataAccessException ;
	public List obtenerProyectosPorNombreDependenciaIndeferenteTildesMayusculasDiferenteEstado(String nombreProyecto, Dependencia dependencia, String tipoDependencia,String idEstado ) throws DataAccessException ;
	public Object obtenerSumaGastoXTipoRubroYVigenciaYProyecto(Long idProyecto,Long tipoRubro,Long vigencia);
	public List obtenerDiferentesVigenciasXProyecto(Long idProyecto);
	public List obtenerDiferentesTipoRubroXProyecto(Long idProyecto);
	public ProyectoEvaluador obtenerProyectoEvaluadorConTipoFinancioacion(ProyectoEvaluador pe);
	public void guardarEvaluacionProyectoConTipoFinanciacion(ProyectoEvaluador evaluacionProyecto) throws DataAccessException ;
    public List obtenerActividadesPersonaPorActividad(Long idActividad );
    public List obtenerListaDeProyectosXGrupoYEstado(Grupo g,EstadoProyecto e);
    public List obtenerListaActividadesPersonaXActividadYPersona(Actividad a,IdPersona id);
    public List obtenerProyectosXTipoModalidadYInvestigadorPrincipal(TipoModalidad tm,Investigador i);
    public List obtenerProyectosXModalidadEInvestigador(Modalidad tm, Investigador i, String estadosProyecto);
    public List obtenerProyectosXModalidadEInvestigadorCortes(Modalidad tm, Investigador i, String estadosProyecto, Long idCorte);
    public List obtenerProyectosXModalidadEInvestigadorPrincipal(Modalidad tm, Investigador i, String estadosProyecto);
    public List obtenerProyectosXModalidadEInvestigadorPrincipalCortes(Modalidad tm, Investigador i, String estadosProyecto, Long idCorte);
        

    
    public List obtenerObservacionesProyecto(Proyecto proyecto);
    public List<Solicitud> obtenerSolicitudesProyecto(Proyecto proyecto, boolean incluirGuardados, boolean incluirDevueltos);
    public List obtenerInvestigadoresProyecto(Long idProyecto);
    public List obtenerEvaluadoresProyecto(Long idProyecto);
    public List obtenerGastosProyecto(Long idProyecto);
    public EstadoProyecto obtenerEstadoProyectoXProyecto(Long idProyecto);
    public List obtenerListaEquipos2XProyecto(Long idProyecto);
    public Investigador obtenerInvestigadorPrincipalXProyecto(Long idProyecto);
    public Investigador obtenerInvestigadorPrincipalXCreador(String tipoDocumento, String documento);
    public Investigador estaInvestigadorEnProyecto(Investigador i,Long idProyecto);
    
    public List buscar(Persona asesor,
    				   Long idProyecto,
    				   String codigoDIBProyecto,
    				   String nombreProyecto,
    				   EstadoProyecto estadoProyecto,
    				   Long idConvocatoria,
    				   Long idModalidad,
    				   IdPersona idEvaluador,
    				   IdPersona idInvestigadorPrincipal,
    				   IdPersona idParticipante,
    				   String palabraClave,
    				   String idFacultad,
    				   Long ano,
    				   String idClasificacionConocimiento,
    				   String iddepartamento);
    
    public List<Financiacion> obtenerFunetesFinanciacionProyecto(Long idProyecto);
    public List<Financiacion> obtenerFunetesFinanciacionConGastosProyecto(Long idProyecto);
    public List obtenerGastosProyectoFinanciacion(Long idFinanciacion);
    public String generarCodigoDi(String codigoDiTipoModalidad, Long idProyecto);
    public Ciudad obtenerCiudadProyecto(Long idProyecto);
    public Persona obtenerCoordinadorProyecto(Long idProyecto);
    public Persona obtenerCoordinadorEvaluacionProyecto(Long idProyecto);
    public Persona obtenerCoordinadorRequisitosProyecto(Long idProyecto);
    public Long obtenerMontoAprobadoProyecto(Long idProyecto, String tipoFinanciacion);
    public Long obtenerMontoContrapartidaPersonal(Long idProyecto);
    public Long obtenerMontoAprobadoProyectoLaboratorios(Long idProyecto, Long idModalidad);
    public List obtenerProyectoXId(String id);
    public List obtenerProyectoXCordinador(IdPersona id);
    public Date obtenerFechaInicioProyecto(Long idProyecto);
    public boolean tieneProyectosRefinanciar(IdPersona idInvestigador);
    public List obtenerProyectosRefinanciar(IdPersona idInvestigador);
	public ProyectoCoordinador obtenerProyectoCoordinadorxCodigo(String codigo);
	public ProyectoCoordinadorEditorial obtenerProyectoCoordinadorEditorialCodigo(String codigo);

	public List obtenerProyectosCoordinadorxModalidadxSede(Modalidad mod,
			String dep);
	
	 public ArchivoRequerimiento obtenerArchivoRequerimiento(Long id) throws DataAccessException;
	 public List<ArchivoRequerimiento> obtenerArchivosRequerimientosLista(Long id) throws DataAccessException;

	 public List obtenerProyectosCompromisosPendientes(Persona persona, String modalidad) throws DataAccessException;
	 
	 public List getProductosProyectoAEntregar(Long pIdProyecto);
	 
	 public List<ProyectoCarta> obtenerCartasXProyecto(Long idProyecto)
	            throws DataAccessException;
	 
	 public List<ObservacionSeguimiento> obtenerObservacionesSeguimientoXProyecto(Long idProyecto)
	            throws DataAccessException;
	 
	 public List<SolicitudInvestigador> obtenerSolicitudInvestigador(Long idSolicitud);
	 
	 public List<SolicitudProrrogaInvestigador> obtenerSolicitudProrrogaInvestigadores(Long idSolicitud);
	 
	 public List<LaboratorioDetalleProyectos> obtenerDetalleProyectosPreAsociadosLaboratorioXIdLab(Long idLab) throws DataAccessException;
	 
	 public List<LaboratorioDetalleProyectos> obtenerDetalleProyectosPreAsociadosLaboratorioXIdProyecto(Long idPry) throws DataAccessException;
	 
	 public List obtenerProyectosXId(Long id) throws DataAccessException;
	
	 public List obtenerProyectosXNombreExacto(String title);
	 
	 public List getCantidadesMaterialGraficoProyecto(Long pIdProyecto);
	 
	 public List getTiposMaterialGrafico();
	public List<ProyectoCoordinadorEditorial> obtenerProyectosEditorialCoordinadorxModalidadxSede(
			Modalidad buscarModalidadxId, String sede, String estado);
	public void modificarCoordinadorEnProyectoEditorial(int tipoModificacion, String tipoPersona, String idProyecto,
			IdPersona idPersona, Persona asesor, String tipoCoordinador, boolean guardarLog);
	public List<ProyectoCoordinadorEditorial> obtenerProyectosCoordinadorEditorialRevisionxModalidadSede(Modalidad mod,
			Dependencia dep, Persona pep);
	public Persona obtenerCoordinadorRequisitosProyectoEditorial(Long id);
	public Proyecto obtenerProyectoEditorialEvaluacion(Long id, IdPersona id2);
	 
}