package co.edu.unal.hermes.bd;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.CategoriaInvestigador;
import co.edu.unal.hermes.modelo.ClasificacionConocimiento;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Estudiante;
import co.edu.unal.hermes.modelo.Evaluador;
import co.edu.unal.hermes.modelo.EvaluadorCorreo;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.Investigador;
import co.edu.unal.hermes.modelo.InvestigadorExterno;
import co.edu.unal.hermes.modelo.InvestigadorGrupo;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.LineaInvestigacion;
import co.edu.unal.hermes.modelo.Modalidad;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaRol;
import co.edu.unal.hermes.modelo.PosibleEvaluador;
import co.edu.unal.hermes.modelo.Proyecto;
import co.edu.unal.hermes.modelo.Rol;
import co.edu.unal.hermes.modelo.TipoDocumento;

/**
 * Interface para obtener buscar y guardar investigadores
 * 
 * @trows lanza la excepcion cuando no se puede acceder a los datos de los
 *        grupos
 */
public interface IPersonaDAO {

	public Persona obtenerCoordinadorNoAsignadoAsesor(IdPersona idAsesor, String idCoordinador);

	public List obtenerGruposInvestigadorLiderIntersede(IdPersona id) throws DataAccessException;

	public List obtenerListaCoordinadoresNoAsignadosAsesor(IdPersona idAsesor);

	public List obtenerPersona(String where, boolean isParametros, Object[] parametros) throws DataAccessException;

	public List obtenerPersonasxRolId(Rol r, String id, String tipodoc) throws DataAccessException;
	
	public List obtenerPersonasxRolIdxDpnId(String rol, String dpn) throws DataAccessException;
	
	public List<Persona> obtenerPersonasxRolIdxSedeId(String rol, Long sedeId) throws DataAccessException;
	
	public List obtenerPersonasUnidadAdministrativa(String dpn, String sede) throws DataAccessException;	

	public List obtenerlistaProyectosEvaluador(Proyecto p);

	public List obtenerInvestigadores(String name) throws DataAccessException;

	public List obtenerInvestigadores(String where, boolean isParametros, Object[] parametros) throws DataAccessException;

	public Investigador obtenerDocente(IdPersona id) throws DataAccessException;

	public Investigador obtenerInvestigador(IdPersona id) throws DataAccessException;

	public Investigador obtenerResumenInvestigador(IdPersona id) throws DataAccessException;

	public Investigador obtenerInvestigadorProyectos(IdPersona id) throws DataAccessException;

	public Investigador obtenerInvestigadorSinProyectos(IdPersona id) throws DataAccessException;
	
	public Investigador obtenerProyectosInvestigador(IdPersona id) throws DataAccessException;
	
	public Investigador obtenerProyectosInvestigador(IdPersona id, boolean incluirCreadosPor, int conv_padre_id) throws DataAccessException;
	 
	public Investigador obtenerProyectosGruposInvestigador(IdPersona id) throws DataAccessException;

	public Investigador obtenerInvestigadorCategoria(IdPersona id) throws DataAccessException;

	public Estudiante obtenerEstudiante(IdPersona id) throws DataAccessException;

	public InvestigadorExterno obtenerInvestigadorExterno(IdPersona id) throws DataAccessException;

	public InvestigadorInterno obtenerInvestigadorInterno(IdPersona id) throws DataAccessException;

	public Persona obtenerPersona(IdPersona id) throws DataAccessException;

	public Persona obtenerPersonaRoles(IdPersona id) throws DataAccessException;

	public Rol obtenerRol(String id) throws DataAccessException;

	public void guardarPersona(Persona persona) throws DataAccessException;

	public void guardarInvestigador(Investigador inv) throws DataAccessException;

	public List<InvestigadorGrupo> obtenerGruposInvestigador(Investigador investigador) throws DataAccessException;
	
	public List obtenerGruposInvestigadorMovilidades(Investigador investigador)	throws DataAccessException;

	public List obtenerGruposInvestigadorPrincipal(IdPersona id) throws DataAccessException;

	public List<Grupo> obtenerGruposInvestigadorLider(IdPersona id) throws DataAccessException;

	public Evaluador obtenerEvaluadorProyectos(IdPersona id) throws DataAccessException;

	public Evaluador obtenerEvaluador(IdPersona id) throws DataAccessException;

	public List obtenerInvestigadoresInternos(String name) throws DataAccessException;

	public List obtenerInvestigadores(String nombre1, String nombre2, String apellido1, String apellido2, Boolean exact);

	public List obtenerPersonasxRol(Rol r) throws DataAccessException;

	int obtenerNumeroDeInvestigadoresCategoriaDeProyecto(Proyecto proyecto, CategoriaInvestigador categoriaInvestigador) throws DataAccessException;

	public InvestigadorInterno obtenerInvestigadorClasificacionConocimiento(IdPersona id) throws DataAccessException;

	public List obtenerInvestigadoresPorNombresApellidosDependencia(String nombre1, String nombre2, String apellido1, String apellido2, Dependencia dependencia, String tipoDependencia);

	public List obtenerProyectosAsesor(IdPersona id);

	public List obtenerProyectosAsesor(IdPersona id, Modalidad m);

	public List obtenerPosiblesEvaluadoresInternos(ClasificacionConocimiento cla, Modalidad m);

	public List obtenerPosiblesEvaluadoresExternos(ClasificacionConocimiento cla);

	public InvestigadorExterno obtenerInvestigadorExternoCompleto(IdPersona id);

	public InvestigadorInterno obtenerInvestigadorInternoCompleto(IdPersona id);

	public List obtenerInvestigadoresXDependencia(Dependencia dependencia);

	public Investigador obtenerInvestigadorProyectosAEvaluar(IdPersona id) throws DataAccessException;

	public List obtenerParticipantesConvocatoria(Modalidad modalidad);

	public Long obtenerNumeroProyectosEvaluador(Persona p, Modalidad m);

	public List obtenerPosiblesEvaluadoresxPalabraClave(Proyecto p, Modalidad m);

	public long generarClaveExterno();

	public InvestigadorExterno buscarInvestigadorExternoId(String documento, String tipo);

	public List obtenerProyectosAsesorConListaEvaluadoresProyecto(IdPersona id, Modalidad m);

	public InvestigadorInterno obtenerInvestigadorInternoDependenciaYFacultad(IdPersona id);

	public List<Persona> buscarPersonaInvestigadoresExternos(long clave);

	public Investigador obtenerInvestigadorProyectosPropuestosAEvaluar(IdPersona id);

	public List obtenerInvestigadoresIndeferenteTildesYMayusculas(String nombre1, String nombre2, String apellido1, String apellido2);

	public List obtenerInvestigadoresPorNombresApellidosDependenciaIndiferenteTildesYMayusculas(String nombre1, String nombre2, String apellido1, String apellido2, Dependencia dependencia, String tipoDependencia);

	public List obtenerInvestigadoresPorAreaDeConocimiento(String nombreAreaConocimiento);

	public List obtenerInvestigadoresPorDependenciaAreaDeConocimiento(String nombreAreaConocimiento, Dependencia dependencia, String tipo_dependencia);

	public Estudiante obtenerEstudianteXPersona(Persona p);

	public Long obtenerIdCategoriaInvestigador(IdPersona id);

	public String obtenerEsInterno(IdPersona id);

	public void insertaInterno(InvestigadorInterno ii);

	public void insertarExterno(InvestigadorExterno ie);
	
	public void insertarExternoContraseña(InvestigadorExterno ie);

	public void insertarInvestigador(Investigador i);

	public List obtenerInvestigadoresPrincipales();

	public List obtenerEvaluadores();

	public List obtenerInvestigadoresProyectos();

	public List obtenerInvestigadoresGrupos();

	public List obtenerInvestigadorPrincipalProyectos(IdPersona id) throws DataAccessException;

	public InvestigadorExterno obtenerInvestigadorExternoClasificacionConocimiento(IdPersona id);

	public boolean esInvestigadorExterno(IdPersona id);

	public boolean esInvestigadorInterno(IdPersona id);

	public void agregarRolPersona(IdPersona idPersona, Rol rol);

	public void insertaEvaluadorCorreo(Proyecto p, TipoDocumento td, String contactado[]);

	public void insertaRespuestaEvaluadorCorreo(EvaluadorCorreo evalco);

	public List obtenerEvaluadoresCorreoProyecto(Proyecto id);

	public String guardarRol(Rol rol);

	public List obtenerRols(IdPersona id);

	public List obtenerTodosLosRoles();

	public void crearRoles(PersonaRol perRol);

	public Persona obtenerPosibleEvaluador(IdPersona id);

	public String obtenerUltimoConsecutivo();

	public void borrarRoles(PersonaRol perRol);

	public List buscarRoles(String documento);
	
	public List buscarRoles2(String documento, String tipoDocumento);

	public void insertarNuevoEvaluadorExterno(PosibleEvaluador posv, String consecutivo);

	public String buscarUltimoConsecutivo();

	public List obtenerDocenteinvestigador(String persona);

	public void insertarNuevoInvestigador(Investigador inv);

	public void insertarNuevaPersona(Persona person);
	
	public boolean insertarNuevaPersonaDatosCompletos(Persona person);
	
	public boolean insertarNuevaPersonaDatosBasicos(Persona person);
	
	public boolean insertarNuevaPersonaDatosBasicosPI(Persona person);
	
	public boolean insertarNuevaPersonaDatosBasicosFuncytca(Persona person);
	
	public boolean actualizarPersonaFuncytca(Persona person);	
	
	public boolean actualizarPersonaDatosBasicos(Persona person);		
	
	public boolean actualizarPersonaFuncytcaCompleto(Persona person);		

	public void insertarNuevoInvestigadorExterno(InvestigadorExterno externo);

	public List obtenerListaCoordinadores(String persona);
	
	public List obtenerListaCoordinadoresEditorial(String persona);

	public boolean validarInvestigadorModalidad(Long idModalidad, IdPersona idPersona);

	public void actualizarEvaluadorExterno(PosibleEvaluador posv);

	public void agregarClasificacionConocimientoInvestigador(IdPersona idPersona, String idClasificacionConocimiento);

	public List obtenerListaDependenciasAsesor(IdPersona idAsesor);
	
	public List obtenerListaDependenciasAsesorEditorial(IdPersona idAsesor);

	public List obtenerListaCoordinadoresAsesor(IdPersona idAsesor);
	
	public List obtenerListaCoordinadoresAsesorEditorial(IdPersona idAsesor);

	public List obtenerListaCoordinadoresNoAsignados();

	public void insertarDependenciaAsesor(IdPersona idAsesor, String idDependencia);

	public void insertarCoordinadorAsesor(IdPersona idAsesor, IdPersona idCoordinador, String idDependencia);
	
	public void insertarCoordinadorAsesorEditorial(IdPersona idAsesor, IdPersona idCoordinador, String idDependencia);

	public void eliminarDependenciaAsesor(IdPersona idAsesor, String idDependencia);

	public void eliminarCoordinadorAsesor(IdPersona idAsesor, IdPersona idCoordinador, String idDependencia);

	public List obtenerPuntajesInvestigador(IdPersona idInvestigador);
	
	public List<Persona> obtenerInvestigadoresBuscador(String sql) throws DataAccessException ;

	public List obtenerRolesDependencia(String dep, String rol);

	public List obtenerRolesSolicitud();
	
	public Date obtenerFechaFinRol(IdPersona id, String idRol);

	public void guardarInvInterno(InvestigadorInterno interno);

	public Persona obtenerCoordinadorNoAsignadoAsesorEditorial(IdPersona idAsesor, String idCoordinador);

	public List<LineaInvestigacion> obtenerLineasInvestigacionEvaluadorExterno(IdPersona id);

	public void insertarNuevoEvaluador(Evaluador e);

	public void guardarLineasInvestigacionEvaluadorExterno(List<LineaInvestigacion> list, IdPersona id);

	public void actualizarEvaluador(Evaluador evaluador);

	public void actualizarFechaVencimientoRol(IdPersona persona, Rol rol, Date fechaVencimiento);
}
