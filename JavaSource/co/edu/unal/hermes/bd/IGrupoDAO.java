package co.edu.unal.hermes.bd;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.GrupoProductoSara;

/**
 * Interface para obtener y buscar grupos
 * 
 * @trows lanza la excepcion cuando no se puede acceder a la información de los
 *        grupos
 */
public interface IGrupoDAO {

    public List<Grupo> obtenerGrupos(String where, boolean isParametros, Object[] parametros);
    
    public List<String[]> obtenerProyectosGrupoEdicion(String grupoId);
    
    public List<GrupoProductoSara> obtenerProductosSaraGrupo(String grupoId);

	public List obtenerGruposPorCriterio(Grupo grupo) throws DataAccessException;

	public Grupo buscarPorId(Long id) throws DataAccessException;

	public Grupo obtenerResumenGrupo(Long id) throws DataAccessException;
	
	public Grupo obtenerGrupoDatosBasicos(Long id) throws DataAccessException;

	public void guardarGrupo(Grupo grupo) throws DataAccessException;

	public void crearGrupo(Grupo grupo) throws DataAccessException;

	public Long obtenerMaximoId() throws DataAccessException;

	public Grupo obtenerProyectosGrupo(Long id) throws DataAccessException;

	public Grupo obtenerDependenciasGrupo(Long id) throws DataAccessException;

	public Set obtenerInvestigadoresGrupo(Long id);

	public Grupo obtenerGrupoPlanAccionFinanciacion(Long id);

	public Grupo obtenerGrupoPlanAccion(Long id);

	public Grupo obtenerGrupoInvestigadores(Long id);

	public Grupo obtenerGrupoLineas(Long id) throws DataAccessException;

	public List obtenerGruposParaAval();

	public List obtenerGruposXInvestigador(String documento);

	public List<Grupo> obtenerGrupoBuscador(String where);
	
	public List obtenerIntegrantesGrupo(Long idGrupo);
	
	public Grupo buscarPorId(Long id, boolean incluirProyectos) throws DataAccessException;
}
