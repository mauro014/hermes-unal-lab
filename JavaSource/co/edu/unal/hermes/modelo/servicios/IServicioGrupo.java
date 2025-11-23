/*
 * Created on 09-ago-2005
 */
package co.edu.unal.hermes.modelo.servicios;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.GrupoProductoSara;

// TODO: Auto-generated Javadoc
/**
 * The Interface IServicioGrupo.
 */
public interface IServicioGrupo {

    /**
     * Crear grupo.
     *
     * @param grupo
     *            the grupo
     */
    public void crearGrupo(Grupo grupo);

    /**
     * Guardar grupo.
     *
     * @param grupo
     *            the grupo
     */
    public void guardarGrupo(Grupo grupo);

    /**
     * Obtener dependencias grupo.
     *
     * @param id
     *            the id
     * @return the grupo
     */
    public Grupo obtenerDependenciasGrupo(Long id);

    /**
     * Obtener grupo.
     *
     * @param id
     *            the id
     * @return the grupo
     */
    public Grupo obtenerGrupo(Long id);

    /**
     * Obtener grupo buscador.
     *
     * @param where
     *            the where
     * @return the list
     */
    public List<Grupo> obtenerGrupoBuscador(String where);

    /**
     * Obtener grupo datos basicos.
     *
     * @param id
     *            the id
     * @return the grupo
     */
    public Grupo obtenerGrupoDatosBasicos(Long id);

    /**
     * Obtener grupo investigadores.
     *
     * @param id
     *            the id
     * @return the grupo
     */
    public Grupo obtenerGrupoInvestigadores(Long id);

    /**
     * Obtener grupo lineas.
     *
     * @param id
     *            the id
     * @return the grupo
     */
    public Grupo obtenerGrupoLineas(Long id);

    /**
     * Obtener grupo plan accion.
     *
     * @param id
     *            the id
     * @return the grupo
     */
    public Grupo obtenerGrupoPlanAccion(Long id);

    /**
     * Obtener grupo plan accion financiacion.
     *
     * @param id
     *            the id
     * @return the grupo
     */
    public Grupo obtenerGrupoPlanAccionFinanciacion(Long id);

    /**
     * Obtener grupos.
     *
     * @param where
     *            the where
     * @param isParametros
     *            the is parametros
     * @param parametros
     *            the parametros
     * @return the list
     */
    public List<Grupo> obtenerGrupos(String where, boolean isParametros, Object[] parametros);

    /**
     * Obtener grupos para aval.
     *
     * @return the list
     */
    public List obtenerGruposParaAval();

    /**
     * Obtener grupos por criterio.
     *
     * @param grupo
     *            the grupo
     * @return the list
     */
    public List obtenerGruposPorCriterio(Grupo grupo);

    /**
     * Obtener grupos X investigador.
     *
     * @param documento
     *            the documento
     * @return the list
     */
    public List obtenerGruposXInvestigador(String documento);

    /**
     * Obtener integrantes grupo.
     *
     * @param idGrupo
     *            the id grupo
     * @return the list
     */
    public List obtenerIntegrantesGrupo(Long idGrupo);

    /**
     * Obtener investigadores grupo.
     *
     * @param id
     *            the id
     * @return the sets the
     */
    public Set obtenerInvestigadoresGrupo(Long id);

    /**
     * Obtener maximo id.
     *
     * @return the long
     */
    public Long obtenerMaximoId();

    /**
     * Obtener productos sara grupo.
     *
     * @param grupoId
     *            the grupo id
     * @return the list
     */
    public List<GrupoProductoSara> obtenerProductosSaraGrupo(String grupoId);

    /**
     * Obtener proyectos grupo.
     *
     * @param id
     *            the id
     * @return the grupo
     */
    public Grupo obtenerProyectosGrupo(Long id);

    /**
     * Obtener proyectos grupo edicion.
     *
     * @param grupoId
     *            the grupo id
     * @return the list
     */
    public List<String[]> obtenerProyectosGrupoEdicion(String grupoId);

    /**
     * Obtener resumen grupo.
     *
     * @param id
     *            the id
     * @return the grupo
     */
    public Grupo obtenerResumenGrupo(Long id);

    /**
     * Buscar por id.
     *
     * @param id
     *            the id
     * @param incluirProyectos
     *            the incluir proyectos
     * @return the grupo
     * @throws DataAccessException
     *             the data access exception
     */
    public Grupo buscarPorId(Long id, boolean incluirProyectos) throws DataAccessException;
}
