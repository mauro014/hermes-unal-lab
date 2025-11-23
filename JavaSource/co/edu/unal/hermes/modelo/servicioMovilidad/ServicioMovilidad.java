/*
 * Created on 09-ago-20
 */
package co.edu.unal.hermes.modelo.servicioMovilidad;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.bd.IDependenciaDAO;
import co.edu.unal.hermes.bd.IMovilidadDAO;
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

public class ServicioMovilidad implements IServicioMovilidad {

    private IDependenciaDAO dependenciaDAO;
    private IMovilidadDAO movilidadDAO;

    public void setDependenciaDAO(IDependenciaDAO dependenciaDAO) {
        this.dependenciaDAO = dependenciaDAO;
    }

    public List obtenerMovilidadesRevision(String tipo, Persona persona, Dependencia dependencia, String idConvocatoria,
            Long nivelConsulta) {
        try {
            return movilidadDAO.obtenerMovilidadesRevision(tipo, persona, dependencia, idConvocatoria, nivelConsulta);
        } catch (DataAccessException dae) {
            return new ArrayList();
        }
    }

    public IDependenciaDAO getDependenciaDAO() {
        return dependenciaDAO;
    }

    public IMovilidadDAO getMovilidadDAO() {
        return movilidadDAO;
    }

    public void setMovilidadDAO(IMovilidadDAO movilidadDAO) {
        this.movilidadDAO = movilidadDAO;
    }

    public List obtenerMovilidadesXTipoConvFacultad(String tipo, Persona persona, Dependencia dependencia) {
        return movilidadDAO.obtenerMovilidadesXTipoConvFacultad(tipo, persona, dependencia);
    }

    public List obtenerMovilidadesArtes(String tipo, Persona persona, Dependencia dependencia, String idMovilidad) {
        return movilidadDAO.obtenerMovilidadesArtes(tipo, persona, dependencia, idMovilidad);
    }

    /* consulta */

    public MovilidadVisitanteExterior obtenerMovilidadesVisExt(Long id) {
        return movilidadDAO.obtenerMovilidadesVisExt(id);
    }

    public MovilidadEstudiantesPosgrado obtenerMovilidadesEstudiantes(Long id) {
        return movilidadDAO.obtenerMovilidadesEstudiantes(id);
    }

    public MovilidadDocentesExterior obtenerMovilidadesDocentes(Long id) {
        return movilidadDAO.obtenerMovilidadesDocentes(id);
    }

    public MovilidadVisitanteExterior obtenerMovilidadesVisExtRequisitos(Long id) {
        return movilidadDAO.obtenerMovilidadesVisExtRequisitos(id);
    }

    public MovilidadEstudiantesPosgrado obtenerMovilidadesEstudiantesRequisitos(Long id) {
        return movilidadDAO.obtenerMovilidadesEstudiantesRequisitos(id);
    }

    public MovilidadDocentesExterior obtenerMovilidadesDocentesRequisitos(Long id) {
        return movilidadDAO.obtenerMovilidadesDocentesRequisitos(id);
    }

    public MovilidadEstudiantesArtes obtenerMovilidadesEstudiantesArtesRequisitos(Long id) {
        return movilidadDAO.obtenerMovilidadesEstudiantesArtesRequisitos(id);
    }

    public MovilidadDocentesArtes obtenerMovilidadesDocentesArtesRequisitos(Long id) {
        return movilidadDAO.obtenerMovilidadesDocentesArtesRequisitos(id);
    }

    public List obtenerMovilidadesXTipoCons(String tipo, Persona persona, Dependencia dependencia) {
        return movilidadDAO.obtenerMovilidadesXTipoCons(tipo, persona, dependencia);
    }

    public List obtenerMovilidadesArtesCons(String tipo, Persona persona, Dependencia dependencia) {
        return movilidadDAO.obtenerMovilidadesArtesCons(tipo, persona, dependencia);
    }

    public List obtenerMovilidadesXTipoConsSede(String tipo, Persona persona, Dependencia dependencia) {
        return movilidadDAO.obtenerMovilidadesXTipoConsSede(tipo, persona, dependencia);
    }

    public List obtenerMovilidadesArtesConsSede(String tipo, Persona persona, Dependencia dependencia) {
        return movilidadDAO.obtenerMovilidadesArtesConsSede(tipo, persona, dependencia);
    }

    public Pais obtenerPais(String id) throws DataAccessException {
        return movilidadDAO.obtenerPais(id);
    }

    public Programa obtenerPrograma(String id) throws DataAccessException {
        return movilidadDAO.obtenerPrograma(id);
    }

    public List obtenerActividades(Long id) throws DataAccessException {
        return movilidadDAO.obtenerActividades(id);
    }

    public List obtenerNombresArchivosMovilidadEA(MovilidadEstudiantesArtes movilidadEA) {
        return movilidadDAO.obtenerNombresArchivosMovilidadEA(movilidadEA);
    }
    
    public List<HistoricoEstadoMovilidad> getHistoricoEstadoMovilidad(Long id){
    	return movilidadDAO.getHistoricoEstadoMovilidad(id);
    }
}
