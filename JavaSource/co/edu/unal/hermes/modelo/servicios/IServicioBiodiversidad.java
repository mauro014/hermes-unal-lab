/*
 * Created on 20-enero-2017
 */
package co.edu.unal.hermes.modelo.servicios;

import java.util.List;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaTramiteBiodiversidad;
import co.edu.unal.hermes.modelo.TipoTramiteBiodiversidad;

/**
 * The Interface IServicioBiodiversidad.
 */
public interface IServicioBiodiversidad {

    /**
     * Obtener lista tramites biodiversidad persona.
     *
     * @param persona the persona
     * @return the list
     * @throws DataAccessException the data access exception
     */
    public List<TipoTramiteBiodiversidad> obtenerListaTramitesBiodiversidadPersona(Persona persona)
			throws DataAccessException;       
	
	/**
	 * Obtener registro tramite biodiversidad.
	 *
	 * @param id the id
	 * @return the tipo tramite biodiversidad
	 * @throws DataAccessException the data access exception
	 */
	public TipoTramiteBiodiversidad obtenerRegistroTramiteBiodiversidad(Long id)
            throws DataAccessException;
	
	/**
	 * Obtener personas asignadas tramite dependencia.
	 *
	 * @param tramite the tramite
	 * @param dependencia the dependencia
	 * @return the list
	 * @throws DataAccessException the data access exception
	 */
	public List<PersonaTramiteBiodiversidad> obtenerPersonasAsignadasTramiteDependencia(Long tramite, String dependencia) throws DataAccessException;
	
	/**
	 * Obtener persona encargada biodiversidad vicerrectoria.
	 *
	 * @return the persona
	 * @throws DataAccessException the data access exception
	 */
	public Persona obtenerPersonaEncargadaBiodiversidadVicerrectoria() throws DataAccessException;
	
	/**
	 * Obtener persona encargada biodiversidad vicerrectoria de asignar .
	 *
	 * @return the persona
	 * @throws DataAccessException the data access exception
	 */
	public Persona obtenerPersonaControlBiodiversidad() throws DataAccessException;
	
	/**
	 * Obtener datos revision tramite biodiversidad.
	 *
	 * @param idTramite the id tramite
	 * @param personaActual the persona actual
	 * @return the object[]; where datos[0] = dependencia <String> que atiende el trámite (vicerrectoria, sede, facultad) de acuerdo a la dependencia del investigador que registra el trámite;
        datos[1] = listaEncargadosDependencia <PersonaTramiteBiodiversidad> para enviar las alertas respectivas si no se encuentra responsable las solicitudes se asignan e informan al encargado de biodiversidad en la
        Vicerrectoría de investigacion
	 */
	public Object[] obtenerDatosRevisionTramiteBiodiversidad(Long idTramite, Persona personaActual) throws DataAccessException;
	
	public boolean requiereMenuBiodiversidadCoordinador (Persona personaActual) throws DataAccessException;
	
	public Persona obtenerPersonaEncargadaSolicitudesEspecificasVicerrectoria() throws DataAccessException;
	
	public List<Coleccion> obtenerColeccionesAlertas();
	
	public List<Persona> obtenerPersonasEncargadasColeccionesBiologicas() throws DataAccessException;
	
}
