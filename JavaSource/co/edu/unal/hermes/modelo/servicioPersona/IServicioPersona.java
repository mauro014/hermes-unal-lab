/*
 * Created on 09-ago-2005
 */
package co.edu.unal.hermes.modelo.servicioPersona;

import java.util.Collection;
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

public interface IServicioPersona {

	public Persona obtenerCoordinadorNoAsignadoAsesor(IdPersona idAsesor, String idCoordinador);
	
	public Persona obtenerCoordinadorNoAsignadoAsesorEditorial(IdPersona idAsesor, String idCoordinador);

	public List obtenerGruposInvestigadorLiderIntersede(IdPersona id) throws DataAccessException;

	public List obtenerPersona(String where, boolean isParametros, Object[] parametros) throws DataAccessException;

	public List obtenerPersonasxRolId(Rol r, String id, String tipodoc) throws DataAccessException;
	
	public List obtenerPersonasxRolIdxDpnId(String rol, String dpn) throws DataAccessException;
	
	public List<Persona> obtenerPersonasxRolIdxSedeId(String rol, Long sedeId) throws DataAccessException;
	
	public List obtenerPersonasUnidadAdministrativa(String dpn, String sede) throws DataAccessException;

	public List obtenerInvestigadores(String where, boolean isParametros, Object[] parametros);

	public List obtenerlistaProyectosEvaluador(Proyecto p);

	public Investigador obtenerDocente(IdPersona id);

	public Investigador obtenerInvestigador(IdPersona id);

	public Investigador obtenerInvestigadorProyectos(IdPersona id);

	public Investigador obtenerInvestigadorSinProyectos(IdPersona id);
	
	public Investigador obtenerProyectosInvestigador(IdPersona id);

	public Investigador obtenerProyectosInvestigador(IdPersona id, boolean incluirCreadosPor, int conv_padre_id);

	public Investigador obtenerProyectosGruposInvestigador(IdPersona id);

	public List obtenerInvestigadorPrincipalProyectos(IdPersona id);

	public Investigador obtenerResumenInvestigador(IdPersona id);

	public List obtenerGruposInvestigadorPrincipal(IdPersona id);

	public Estudiante obtenerEstudiante(IdPersona id);

	public InvestigadorExterno obtenerInvestigadorExterno(IdPersona id);

	public InvestigadorInterno obtenerInvestigadorInterno(IdPersona id);

	public Investigador buscarInvestigadorParaAsociarAProyecto(IdPersona id);

	public Persona obtenerPersona(IdPersona id);

	public Persona obtenerPersonaRoles(IdPersona id);

	public Rol obtenerRol(String id);

	public List obtenerInvestigadoresPorNombre(String name);

	public List obtenerInvestigadoresPorNombresYApellidos(String nombres, String apellidos, Boolean busqExtacta);

	public void guardarInvestigador(Investigador inv);
	
	public void guardarInvestigador(Investigador inv, boolean rolInvestigador);

	public void guardarAsesor(Persona persona);

	public List<InvestigadorGrupo> obtenerGruposInvestigador(Investigador investigador);
	
	public List obtenerGruposInvestigadorMovilidades(Investigador investigador)	throws DataAccessException;

	public List<Grupo> obtenerGruposInvestigadorLider(IdPersona id);

	public Evaluador obtenerEvaluadorProyectos(IdPersona id);

	public Evaluador obtenerEvaluador(IdPersona id);

	public List obtenerInvestigadoresInternos(String name);

	public List obtenerPersonasxRol(Rol r);

	int obtenerNumeroDeInvestigadoresCategoriaDeProyecto(Proyecto proyecto, CategoriaInvestigador categoriaInvestigador);

	public InvestigadorInterno obtenerInvestigadorClasificacionConocimiento(IdPersona id);

	public List obtenerInvestigadoresPorNombresApellidosDependencia(String nombreInvestigador, String apellidoInvestigador, Dependencia dependencia, String tipoDependencia);

	public List obtenerProyectosAsesor(Persona persona);

	public List obtenerProyectosAsesor(Persona persona, Modalidad m);

	public List obtenerPosiblesEvaluadoresInternos(ClasificacionConocimiento cla, Modalidad m);

	public List obtenerPosiblesEvaluadoresExternos(ClasificacionConocimiento cla);

	public InvestigadorExterno obtenerInvestigadorExternoCompleto(IdPersona id);

	public InvestigadorInterno obtenerInvestigadorInternoCompleto(IdPersona id);

	public List obtenerInvestigadoresPertenecenDependencia(Dependencia dependencia);

	public Investigador obtenerInvestigadorProyectosAEvaluar(IdPersona id);

	public List obtenerParticipantesConvocatoria(Modalidad modalidad);

	public Long obtenerNumeroProyectosEvaluador(Persona p, Modalidad m);

	public List obtenerPosiblesEvaluadoresxPalabraClave(Proyecto p, Modalidad m);

	public long generarClaveExterno();

	public String login(Persona ie);

	public InvestigadorExterno buscarInvestigadorExternoId(String documento, String tipo);

	public List obtenerProyectosAsesorConListaEvaluadoresProyecto(IdPersona id, Modalidad m);

	public InvestigadorInterno obtenerInvestigadorInternoDependenciaYFacultad(IdPersona id);

	public List<Persona> buscarPersonaInvestigadoresExternos(long clave);

	public Investigador obtenerInvestigadorProyectosPropuestosAEvaluar(IdPersona id);

	public List obtenerInvestigadoresPorNombresYApellidosIndiferenteTildesYMayusculas(String nombres, String apellidos);

	public List obtenerInvestigadoresPorNombresApellidosDependenciaIndiferenteTildesYMayusculas(String nombreInvestigador, String apellidoInvestigador, Dependencia dependencia, String tipoDependencia);

	public List obtenerInvestigadoresPorDependenciaAreaDeConocimiento(String nombreAreaConocimiento, Dependencia dependencia, String tipo_dependencia);

	public List obtenerInvestigadoresPorAreaDeConocimiento(String nombreAreaConocimiento);

	public boolean seCruzanDependencia(Dependencia dependieciaInvestigador, Dependencia dependieciaConvocatoria);

	public Estudiante obtenerEstudianteXPersona(Persona p);

	public void insertarDatosEstudianteDePersona(Persona p);

	public void convertivirPersonaAInvestigadorExterno(Persona p, Investigador ie);

	public void insertaInterno(InvestigadorInterno ii);

	public void insertarExterno(InvestigadorExterno ie);
	
	public void insertarExternoContraseña(InvestigadorExterno ie);

	public void insertarInvestigador(Investigador i);

	public List obtenerInvestigadoresPrincipales();

	public List obtenerEvaluadores();

	public List obtenerInvestigadoresProyectos();

	public List obtenerInvestigadoresGrupos();

	public InvestigadorExterno obtenerInvestigadorExternoClasificacionConocimiento(IdPersona id);

	public boolean esInvestigadorExterno(IdPersona id);

	public boolean esInvestigadorInterno(IdPersona id);

	public void agregarRolPersona(IdPersona idPersona, Rol rol);

	public void insertaEvaluadorCorreo(Proyecto p, TipoDocumento td, String contactado[]);

	public String insertaRespuestaEvaluadorCorreo(EvaluadorCorreo evalco);

	public List obtenerEvaluadoresCorreoProyecto(Proyecto id);

	public String guardarRol(Rol rol);

	public List obtenerRols(IdPersona id);
	
	public List obtenerRolesSolicitud();
	
	public List obtenerRolesDependencia(String dep, String rol);
	
	public List obtenerTodosLosRoles();

	public void crearRoles(PersonaRol perRol);

	public Persona obtenerPosibleEvaluador(IdPersona id);

	public String obtenerUltimoConsecutivo();

	public void borrarRoles(PersonaRol perRol);

	public List buscarRoles(String documento);
	
	public List buscarRoles2(String documento, String tipoDocumento);

	public String insertarNuevoEvaluadorExterno(PosibleEvaluador posv, String consecutivo);

	public String buscarUltimoConsecutivo();

	public List obtenerDocenteinvestigador(String persona);

	public String insertarNuevoInvestigador(Investigador inv);

	public void insertarNuevaPersona(Persona person);
	
	public boolean insertarNuevaPersonaDatosCompletos(Persona person);
	
	public boolean insertarNuevaPersonaDatosBasicos(Persona person);
	
	public boolean insertarNuevaPersonaDatosBasicosPI(Persona person);
	
	public boolean insertarNuevaPersonaDatosBasicosFuncytca(Persona person);
	
	public boolean actualizarPersonaFuncytca(Persona person);
	
	public boolean actualizarPersonaDatosBasicos (Persona person);
	
	public boolean actualizarPersonaFuncytcaCompleto(Persona person);

	public void insertarNuevoInvestigadorExterno(InvestigadorExterno externo);

	public List obtenerListaCoordinadores(String persona);

	public boolean validarInvestigadorModalidad(Long idModalidad, IdPersona idPersona);

	public String actualizarEvaluadorExterno(PosibleEvaluador posv);

	public void agregarClasificacionConocimientoInvestigador(IdPersona idPersona, String idClasificacionConocimiento);

	public List obtenerListaDependenciasAsesor(IdPersona idAsesor);

	public List obtenerListaCoordinadoresAsesor(IdPersona idAsesor);

	public List obtenerListaCoordinadoresNoAsignados();

	public List obtenerListaCoordinadoresNoAsignadosAsesor(IdPersona asesor);

	public void insertarDependenciaAsesor(IdPersona idAsesor, String idDependencia);

	public void insertarCoordinadorAsesor(IdPersona idAsesor, IdPersona idCoordinador, String idDependencia);
	
	public void insertarCoordinadorAsesorEditorial(IdPersona idAsesor, IdPersona idCoordinador, String idDependencia);

	public void eliminarDependenciaAsesor(IdPersona idAsesor, String idDependencia);

	public void eliminarCoordinadorAsesor(IdPersona idAsesor, IdPersona idCoordinador, String idDependencia);

	public List obtenerPuntajesInvestigador(IdPersona idInvestigador);
	
	public List<Persona> obtenerInvestigadoresBuscador(String sql) throws DataAccessException ;

	public Date obtenerFechaFinRol(IdPersona id, String idRol);
	
	public void guardarInvInterno(InvestigadorInterno interno);

	public List obtenerListaCoordinadoresAsesorEditorial(IdPersona id);

	public List<Persona> obtenerListaCoordinadoresEditorial(String documento);

	public List <LineaInvestigacion> obtenerLineasInvestigacionEvaluadorExterno(IdPersona idPersona);
	
	public void insertarNuevoEvaluador(Evaluador e);
	
	public void guardarLineasInvestigacionEvaluadorExterno(List <LineaInvestigacion> lista, IdPersona id);

	public void actualizarEvaluador(Evaluador evaluador);

	public void actualizarFechaVencimientoRol(IdPersona id, Rol rolE, Date primerDiaSiguienteAno);

}
