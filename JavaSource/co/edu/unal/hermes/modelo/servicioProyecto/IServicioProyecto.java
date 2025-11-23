/*
 * Created on 09-ago-2005
 */
package co.edu.unal.hermes.modelo.servicioProyecto;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.Actividad;
import co.edu.unal.hermes.modelo.ActividadPersona;
import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.modelo.ArchivoRequerimiento;
import co.edu.unal.hermes.modelo.AreaTematica;
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
import co.edu.unal.hermes.modelo.laboratorios.Laboratorio;
import co.edu.unal.hermes.modelo.laboratorios.LaboratorioDetalleProyectos;
import co.edu.unal.hermes.modelo.seguimiento.ObservacionProyecto;
import co.edu.unal.hermes.modelo.seguimiento.ProyectoProrroga;
import co.edu.unal.hermes.modelo.seguimiento.Solicitud;
import co.edu.unal.hermes.vista.evaluadores.ProyectoCoordinador;
import co.edu.unal.hermes.vista.proyectos.ProyectoVista;
import co.edu.unal.hermes.vista.proyectos.ProyectosVistaOficiosConvocatoria;




public interface IServicioProyecto {
	
	public List obtenerProyectosCoordinadorxModalidadxSede(Modalidad mod, String dep, String idEstPry ) throws DataAccessException ;
	public List obtenerProyectosCoordinador(Modalidad mod, String documento, String tipodoc,boolean Elegibles);
	public List obtenerProyectosCoordinadorxModalidadSede(Modalidad mod,Dependencia dep, Persona pep) throws DataAccessException ;
	public List<ProyectoCoordinador> obtenerProyectosCoordinadorRevisionxModalidadSede(Modalidad mod,Dependencia dep, Persona pep) throws DataAccessException ;
	public List obtenerProyectosCoordinadorxModalidadxSede(Modalidad mod, String dep ) throws DataAccessException ;
	public List obtenerProyectosCoordinadorxModalidadxFacultadEstado(Modalidad mod, String idFac) throws DataAccessException;
	public List<ProyectoCoordinador> obtenerProyectosCoordinadorxIdProyecto(	String id ) throws DataAccessException;
	public List obtenerProyectosPorCriterio(String sql) throws DataAccessException;
	public List obtenerProyectos(String where, boolean isParametros, Object[] parametros)throws DataAccessException;
    public Proyecto obtenerProyecto(Long id, short informacion);
    public Proyecto obtenerProyecto(Long id, short informacion, boolean incluirGastos);
    public Laboratorio obtenerLaboratorio(Long id, short informacion);
	public List<Proyecto> obtenerProyectoBuscador(String sql, String facultad, String sedep, String investigador, String area, String palabra) throws DataAccessException;
	public List<ConvocatoriaExterna> obtenerConvocatorias(String sqlInternas, String sqlExternas) throws DataAccessException;
	public void imprimirReporteProyecto(Proyecto proyectoActual, HttpSession sesion, Boolean esEvaluador);
	public void imprimirReporteProyectoEditorial(Long idProyecto, HttpSession sesion, Boolean esEvaluador);
	public String consultarProyectoGenerico(Proyecto proyecto, HttpSession sesion, Convocatoria conv);
	public boolean validarVinculacionPersona(InvestigadorInterno invI, boolean permitirDocentesCatedra);
	public boolean validarGanadorSemilleros(String idPersona, String tipoDocumento, String id);
	public List<ProyectoEvaluador> obtenerProyectosEvaluador(Long idProyecto) throws DataAccessException;
	public List<ProyectoInforme> obtenerProyectoInforme(Long idProyecto) throws DataAccessException ;
    public Proyecto obtenerProyectoAsesorEvaluacion(Long id, IdPersona idAsesor);
    public Proyecto obtenerResumenProyecto(Long id);
    public Proyecto obtenerResumenProyectoExtension(Long id);
    public List obtenerProyectosPorCriterio(Proyecto proyecto);
	public List obtenerTodosProyectos();	
	public List obtenerProyectosPorNombre(String title, Boolean exacta);	
	public List obtenerProyectosPorCodigoQuipu(String codigo);
	public void ingresarProyecto(Proyecto pry);	
	public void actualizarProyecto(Proyecto pry);
	public List<HistoricoEstadoInforme> obtenerHistoricosEstadoInforme(Long informeId);
	public List<HistoricoEstadoProyecto> obtenerHistoricosEstadoProyecto(Proyecto proyecto);
	public List<HistoricoEstadoAval> obtenerHistoricosEstadoAval(Aval aval);
	public List obtenerTiposInvestigacion();
	public List<ObjetivoEspecifico> obtenerObjetivosEspeficicos(Proyecto proyecto);    
	public List<HistoricoAsignacionProyecto> obtenerHistoricosAsignacionProyecto(Long pryId);
	public List<HistoricoHabilitacionEdicionProyecto> obtenerHistoricosHabilitacionEdicionProyecto(Long pryId);
	public List<HistoricoEstadoLegalizacion> obtenerHistoricosEstadosLegalizacion(Long pryId);
	public List obtenerFuenteFinanciacionInterna();
	public List obtenerFuenteFinanciacionExterna();
	public Archivo obtenerArchivo(Long id);
	public ArchivoRequerimiento obtenerArchivoRequerimiento(Long id);
	public List<ArchivoRequerimiento> obtenerArchivosRequerimientosLista(Long id);
	public void eliminarProyecto(Long id);
	public List<Archivo> obtenerNombresArchivos(Proyecto proyecto);
	public List<Archivo> obtenerNombresArchivosReclamacion(Proyecto proyecto);
	@Deprecated
	public List obtenerNombresArchivosTipos(Proyecto proyecto);
	public List<Archivo> obtenerNombresArchivosConTipos(Proyecto proyecto);
	
	public void eliminarInvestigadorProyecto(InvestigadorProyecto investigadorProyecto);
	public List obtenerProyectosTituloConvocatoria(String nombre, Modalidad mod);
    public Proyecto obtenerProyectoHistorico(Long id);
    public ProyectoEvaluador obtenerProyectoEvaluador(ProyectoEvaluador pe);
    public void guardarEvaluacionProyecto(ProyectoEvaluador evaluacionProyecto);
    public List<ProyectosVistaOficiosConvocatoria> obtenerProyectosXEstadoYConvocatoriaPadre(Long idConvocatoriaPadre, String idEstado) throws DataAccessException;
    public List<ProyectosVistaOficiosConvocatoria> obtenerProyectosXEstadoYConvocatoriaPadreYCorte(Long idConvocatoriaPadre, String idEstado, Long corte) throws DataAccessException;
    
   
    
    
    public Proyecto obtenerProyectoCodigoDib(String codigoDib);
    public List obtenerProyectosxEjemplo(Proyecto pry);
    public List obtenerProyectosxModalidad(Modalidad mod); 
     
    List obtenerProyectosConvocatoria(EstadoProyecto[]  estados, Modalidad mod);
    List obtenerProyectosConvocatoriaConInvestigadores(EstadoProyecto[]  estados, Modalidad mod); 
    
    public Financiacion obtenerFinanciacionGastos(Financiacion fin);
    
    public List obtenerProyectosPorNombreDependencia(String nombreProyecto, Dependencia dependencia, String tipo_dependencia);
 
    public List<AreaTematica> obtenerAreasTematicas(Proyecto proyectoActual);
 
    public List obtenerProyectosCoordinadorxModalidad(Modalidad mod);

    public List obtenerProyectosCoordinadorxModalidadxEstado(Modalidad mod,String idEstado);
    public List obtenerProyectosCoordinadorxModalidadxFacultad(Modalidad mod,String idFac);
    public List obtenerProyectosCoordinadorAsesorxModalidadxFacultad(Modalidad mod,String idFac, String idEstProy);
    public List obtenerProyectosCoordinadorAsesorxModalidadxFacultad(Modalidad mod,String idFac);
    public ProyectoCoordinador obtenerProyectoCoordinadorxCodigo(String codigo);
    public List<ProyectoCoordinador> obtenerProyectosxId(String idDoc,String tipoDoc, String idPro);
    public ProyectoCoordinadorEditorial obtenerProyectoCoordinadorEditorialCodigo(String codigo);
    
    public String modificarCoordinadorEnProyecto(int tipo, String idProyecto, 
    		IdPersona idPersona, Persona asesor, String tipoCoordinador,boolean guardarLog);
    public String modificarCoordinadorEnProyecto(int tipoModificacion, 
    		String tipoPersona, String idProyecto, IdPersona idPersona, Persona asesor, String tipoCoordinador, boolean guardarLog);
    public String modificarCoordinadorEnProyectoEditorial(int tipoModificacion, 
    		String tipoPersona, String idProyecto, IdPersona idPersona, Persona asesor, String tipoCoordinador, boolean guardarLog);
    public List obtenerProyectosPorNombreEstado(String nombreProyecto, EstadoProyecto estadoProyecto);
    
    /**
     * Se obtienen todos los integrates de proyectos segun los estados de proyectos
     * @param estadosProyectos
     * @return lista de investigadores
     */
    public List obtenerIntegrantesProyectosXEstados(List estadosProyectos);
    public List obtenerProyectoEvaluadoresInternos(Long idProyecto, Modalidad mod);
    public long obtenerFiananciacionXProyectoYFuente(Long idProyecto,String fuente);
    public List obtenerProyectosPorNombreEstadoDiferente(String title,String idEstado) ;
	public List obtenerProyectosPorNombreDependenciaIndiferenteTildesMayusculasYDiferenteEstado(String nombreProyecto, Dependencia dependencia, String tipo_dependencia,String idEstado) ;
    public Object obtenerSumaGastoXTipoRubroYVigenciaYProyecto(Long idProyecto,Long tipoRubro,Integer vigencia);
	public List obtenerDiferentesVigenciasXProyecto(Long idProyecto);		
	public List obtenerDiferentesTipoRubroXProyecto(Long idProyecto);
	public ProyectoEvaluador obtenerProyectoEvaluadorConTipoFinancioacion(ProyectoEvaluador pe) ;
	public void guardarEvaluacionProyectoConTipoFinanciacion(ProyectoEvaluador evaluacionProyecto) throws DataAccessException ;
    public List obtenerActividadesPersonaPorActividad(Long idActividad );
    public List obtenerListaDeProyectosXGrupoYEstado(Grupo g,EstadoProyecto e);
    public long obtenerLongCantidadFinanciadaProyecto(Proyecto p);
    public String obtenerStringEntidadesFinanciadorasProyecto(Proyecto p);
    public List obtenerListaActividadesPersonaXActividadYPersona(Actividad a,IdPersona id);
    public boolean seTranslapanActividadPersona(ActividadPersona ap,List listaActividadPersona);
    public List obtenerProyectosXTipoModalidadYInvestigadorPrincipal(TipoModalidad tm,Investigador i);
    public List obtenerProyectosXModalidadEInvestigador(Modalidad tm, Investigador i, String estadosProyecto);
    public List obtenerProyectosXModalidadEInvestigadorCortes(Modalidad tm, Investigador i, String estadosProyecto, Long idCorte);
    public List obtenerProyectosXModalidadEInvestigadorPrincipal(Modalidad tm, Investigador i, String estadosProyecto);
    public List obtenerProyectosXModalidadEInvestigadorPrincipalCortes(Modalidad tm, Investigador i, String estadosProyecto, Long idCorte);
    public String guardaProyectoConHistoricoEstadoDeBD(Proyecto p,String justificacion);
    public String guardaProyectoConHistoricoEstadoDeBD(Proyecto p,String justificacion, Date fechaHistorico);
    
    public List obtenerObservacionesProyecto(Proyecto proyecto);
    public List obtenerListaEquipos2XProyecto(Long idProyecto);
    public Investigador obtenerInvestigadorPrincipalXProyecto(Long idProyecto);
    public Investigador obtenerInvestigadorPrincipalXCreador(String tipoDocumento, String documento);
    public Investigador estaInvestigadorEnProyecto(Investigador i,Long idProyecto);
    public List<InvestigadorProyecto> obtenerInvestigadoresProyecto(Long idProyecto);
    public List obtenerEvaluadoresProyecto(Long idProyecto);
    public List<Solicitud> obtenerSolicitudesProyecto(Proyecto proyecto, boolean incluirGuardados, boolean incluirDevueltos);
    public List obtenerGastosProyecto(Long idProyecto);
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
					   String idDepartamento);
    public List<Financiacion> obtenerFunetesFinanciacionProyecto(Long idProyecto);
    public List<Financiacion> obtenerFunetesFinanciacionConGastosProyecto(Long idProyecto);
    public List<Gasto> obtenerGastosProyectoFinanciacion(Long idFinanciacion);
    public String generarCodigoDi(String codigoDiTipoModalidad, Long idProyecto);
    public boolean tienePrincipalProyecto(Long idProyecto);
    
    public Ciudad obtenerCiudadProyecto(Long idProyecto);
    public Persona obtenerCoordinadorProyecto(Long idProyecto);
    public Persona obtenerCoordinadorEvaluacionProyecto(Long idProyecto);
    public Persona obtenerCoordinadorRequisitosProyecto(Long idProyecto);
    public String validarProrrogaProyecto(ProyectoProrroga prorroga);
    public String validarObservacionProyecto(ObservacionProyecto observacion);
    public Long obtenerMontoAprobadoProyecto(Long idProyecto, String tipoFinanciacion);
    public Long obtenerMontoContrapartidaPersonal(Long idProyecto);
    public Long obtenerMontoAprobadoProyectoLaboratorios(Long idProyecto, Long idModalidad);
    public List obtenerProyectoXId(String id);
    public List obtenerProyectosXEvaluador(IdPersona id);
    public List obtenerProyectoXCordinador(IdPersona id);
    public Date obtenerFechaInicioProyecto(Long idProyecto);
    public boolean tieneProyectosRefinanciar(IdPersona idInvestigador);
    public List obtenerProyectosRefinanciar(IdPersona idInvestigador);
    public List<Proyecto> obtenerProyectosCompromisosPendientes(Persona persona, String modalidad);
    public List getFormacionPorProyecto(Long idProyecto);
    public List<ProyectoProducto> getProductosPorProyecto(Long idProyecto);
    public List<SelectItem> crearSelectItemObjetivos(List<ObjetivoEspecifico> objetivos);
    public List<SelectItem> crearSelectItemMetas(List<MetaProyecto> metas);
    public List<SelectItem> crearSelectItemResultados(List<ResultadoProyecto> resultados);
    public List<ProyectoCarta> obtenerCartasXProyecto(Proyecto proyecto);
    public List<ObservacionSeguimiento> obtenerObservacionesSeguimientoXProyecto(Proyecto proyecto);
    public List obtenerProyectosXId(Long id) throws DataAccessException;
    public List<Proyecto> obtenerProyectosPreAsociadosLaboratorio(Long idLab) throws DataAccessException;
    public List<Laboratorio> obtenerLaboratoriosPreAsociadosProyecto(Long idPry) throws DataAccessException;
	public void imprimirReporteProyectoLegalizacion(Proyecto proyectoActual, HttpSession sesion);
	public List<CantidadMaterialGrafico> getCantidadesMaterialGraficoProyecto(Long idProyecto);
	public List<CantidadMaterialGrafico> getCantidadesMaterialGraficoNuevo();
	public List <ProyectoCoordinadorEditorial> obtenerProyectosEditorialCoordinadorxModalidadxSede(
			Modalidad buscarModalidadxId, String sede, String estado);
	public List <ProyectoCoordinadorEditorial> obtenerProyectosCoordinadorEditorialRevisionxModalidadSede(
			Modalidad buscarModalidadxId, Dependencia dependencia, Persona personaActual);
	public Persona obtenerCoordinadorRequisitosProyectoEditorial(Long id);
	public Proyecto obtenerProyectoEditorialEvaluacion(Long id, IdPersona id2);	
}

