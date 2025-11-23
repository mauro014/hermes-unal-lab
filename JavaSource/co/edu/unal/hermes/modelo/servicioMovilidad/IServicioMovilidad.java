/*
 * Created on 09-ago-2005
 */
package co.edu.unal.hermes.modelo.servicioMovilidad;

import java.util.List;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.bd.IDependenciaDAO;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.HistoricoEstadoMovilidad;
import co.edu.unal.hermes.modelo.MovilidadDocentesArtes;
import co.edu.unal.hermes.modelo.MovilidadDocentesExterior;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesArtes;
import co.edu.unal.hermes.modelo.MovilidadEstudiantesPosgrado;
import co.edu.unal.hermes.modelo.MovilidadVisitanteExterior;
import co.edu.unal.hermes.modelo.Pais;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.Programa;

/**
 * The Interface IServicioMovilidad.
 */
public interface IServicioMovilidad {

    /**
     * Sets the dependencia dao.
     *
     * @param dependenciaDAO
     *            the new dependencia dao
     */
    public void setDependenciaDAO(IDependenciaDAO dependenciaDAO);

    /**
     * Obtener movilidades revision.
     *
     * @param tipo
     *            the tipo
     * @param persona
     *            the persona
     * @param dependencia
     *            the dependencia
     * @param idConvocatoria
     *            the id convocatoria
     * @param nivelConsulta
     *            the nivel consulta
     * @return the list
     */
    public List obtenerMovilidadesRevision(String tipo, Persona persona, Dependencia dependencia, String idConvocatoria,
            Long nivelConsulta);

    /**
     * Obtener movilidades x tipo conv facultad.
     *
     * @param tipo
     *            the tipo
     * @param persona
     *            the persona
     * @param dependencia
     *            the dependencia
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerMovilidadesXTipoConvFacultad(String tipo, Persona persona, Dependencia dependencia)
            throws DataAccessException;

    /**
     * Obtener movilidades artes.
     *
     * @param tipo            the tipo
     * @param persona            the persona
     * @param dependencia            the dependencia
     * @param modalidadId the modalidad id
     * @return the list
     * @throws DataAccessException             the data access exception
     */
    public List obtenerMovilidadesArtes(String tipo, Persona persona, Dependencia dependencia, String modalidadId)
            throws DataAccessException;

    /**
     * Obtener movilidades vis ext.
     *
     * @param id
     *            the id
     * @return the movilidad visitante exterior
     * @throws DataAccessException
     *             the data access exception
     */
    public MovilidadVisitanteExterior obtenerMovilidadesVisExt(Long id) throws DataAccessException;

    /**
     * Obtener movilidades estudiantes.
     *
     * @param id
     *            the id
     * @return the movilidad estudiantes posgrado
     * @throws DataAccessException
     *             the data access exception
     */
    public MovilidadEstudiantesPosgrado obtenerMovilidadesEstudiantes(Long id) throws DataAccessException;

    /**
     * Obtener movilidades docentes.
     *
     * @param id
     *            the id
     * @return the movilidad docentes exterior
     * @throws DataAccessException
     *             the data access exception
     */
    public MovilidadDocentesExterior obtenerMovilidadesDocentes(Long id) throws DataAccessException;

    /**
     * Obtener movilidades vis ext requisitos.
     *
     * @param id
     *            the id
     * @return the movilidad visitante exterior
     * @throws DataAccessException
     *             the data access exception
     */
    public MovilidadVisitanteExterior obtenerMovilidadesVisExtRequisitos(Long id) throws DataAccessException;

    /**
     * Obtener movilidades estudiantes requisitos.
     *
     * @param id
     *            the id
     * @return the movilidad estudiantes posgrado
     * @throws DataAccessException
     *             the data access exception
     */
    public MovilidadEstudiantesPosgrado obtenerMovilidadesEstudiantesRequisitos(Long id) throws DataAccessException;

    /**
     * Obtener movilidades docentes requisitos.
     *
     * @param id
     *            the id
     * @return the movilidad docentes exterior
     * @throws DataAccessException
     *             the data access exception
     */
    public MovilidadDocentesExterior obtenerMovilidadesDocentesRequisitos(Long id) throws DataAccessException;

    /**
     * Obtener movilidades estudiantes artes requisitos.
     *
     * @param id
     *            the id
     * @return the movilidad estudiantes artes
     * @throws DataAccessException
     *             the data access exception
     */
    public MovilidadEstudiantesArtes obtenerMovilidadesEstudiantesArtesRequisitos(Long id) throws DataAccessException;

    /**
     * Obtener movilidades docentes artes requisitos.
     *
     * @param id
     *            the id
     * @return the movilidad docentes artes
     * @throws DataAccessException
     *             the data access exception
     */
    public MovilidadDocentesArtes obtenerMovilidadesDocentesArtesRequisitos(Long id) throws DataAccessException;

    /**
     * Obtener movilidades x tipo cons.
     *
     * @param tipo
     *            the tipo
     * @param persona
     *            the persona
     * @param dependencia
     *            the dependencia
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerMovilidadesXTipoCons(String tipo, Persona persona, Dependencia dependencia)
            throws DataAccessException;

    /**
     * Obtener movilidades artes cons.
     *
     * @param tipo
     *            the tipo
     * @param persona
     *            the persona
     * @param dependencia
     *            the dependencia
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerMovilidadesArtesCons(String tipo, Persona persona, Dependencia dependencia)
            throws DataAccessException;

    /**
     * Obtener movilidades x tipo cons sede.
     *
     * @param tipo
     *            the tipo
     * @param persona
     *            the persona
     * @param dependencia
     *            the dependencia
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerMovilidadesXTipoConsSede(String tipo, Persona persona, Dependencia dependencia)
            throws DataAccessException;

    /**
     * Obtener movilidades artes cons sede.
     *
     * @param tipo
     *            the tipo
     * @param persona
     *            the persona
     * @param dependencia
     *            the dependencia
     * @return the list
     * @throws DataAccessException
     *             the data access exception
     */
    public List obtenerMovilidadesArtesConsSede(String tipo, Persona persona, Dependencia dependencia)
            throws DataAccessException;

    /**
     * Obtener pais.
     *
     * @param id
     *            the id
     * @return the pais
     * @throws DataAccessException
     *             the data access exception
     */
    public Pais obtenerPais(String id) throws DataAccessException;

    /**
     * Obtener programa.
     *
     * @param id
     *            the id
     * @return the programa
     * @throws DataAccessException
     *             the data access exception
     */
    public Programa obtenerPrograma(String id) throws DataAccessException;

    /**
     * Obtener actividades.
     *
     * @param id the id
     * @return the list
     * @throws DataAccessException the data access exception
     */
    public List obtenerActividades(Long id) throws DataAccessException;

    /**
     * Obtener nombres archivos movilidad ea.
     *
     * @param movilidadEA
     *            the movilidad ea
     * @return the list
     */
    public List obtenerNombresArchivosMovilidadEA(MovilidadEstudiantesArtes movilidadEA);
    
    /**
     * Gets the historico estado movilidad.
     *
     * @param id the id
     * @return the historico estado movilidad
     */
    public List<HistoricoEstadoMovilidad> getHistoricoEstadoMovilidad(Long id);

}
