/*
 * Created on 20-enero-2017
 */
package co.edu.unal.hermes.modelo.servicios.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.bd.IBiodiversidadDAO;
import co.edu.unal.hermes.bd.IPersonaDAO;
import co.edu.unal.hermes.modelo.Coleccion;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;
import co.edu.unal.hermes.modelo.InvestigadorInterno;
import co.edu.unal.hermes.modelo.Parametro;
import co.edu.unal.hermes.modelo.Persona;
import co.edu.unal.hermes.modelo.PersonaTramiteBiodiversidad;
import co.edu.unal.hermes.modelo.TipoTramiteBiodiversidad;
import co.edu.unal.hermes.modelo.servicios.IServicioBiodiversidad;

/**
 * The Class ServicioBiodiversidad.
 */
public class ServicioBiodiversidad implements IServicioBiodiversidad {

    /** The biodiversidad DAO. */
    private IBiodiversidadDAO biodiversidadDAO;
    private IPersonaDAO personaDAO;
    public static final int CORREO_PLANTILLA_ALERTA = 287;
    public static final String CORREO_NOMBRE_CURADOR = "<<NOMBRE_CURADOR>>";
    public static final String CORREO_NOMBRE_COLECCION = "<<NOMBRE_COLECCION>>";
    public static final String CORREO_FECHA_VENCIMIENTO_ACTUALIZACION = "<<FECHA_VENCIMIENTO_ACTUALIZACION>>";

    /**
     * Sets the biodiversidad DAO.
     *
     * @param biodiversidadDAO
     *            the new biodiversidad DAO
     */
    public void setBiodiversidadDAO(IBiodiversidadDAO biodiversidadDAO) {
        this.biodiversidadDAO = biodiversidadDAO;
    }

    public void setPersonaDAO(IPersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.modelo.servicioBiodiversidad.IServicioBiodiversidad#
     * obtenerListaTramitesBiodiversidadPersona(co.edu.unal.hermes.modelo.
     * Persona)
     */
    public List<TipoTramiteBiodiversidad> obtenerListaTramitesBiodiversidadPersona(Persona persona)
            throws DataAccessException {
        return biodiversidadDAO.obtenerListaTramitesBiodiversidadPersona(persona);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.modelo.servicioBiodiversidad.IServicioBiodiversidad#
     * obtenerRegistroTramiteBiodiversidad(java.lang.Long)
     */
    public TipoTramiteBiodiversidad obtenerRegistroTramiteBiodiversidad(Long id) throws DataAccessException {
        return biodiversidadDAO.obtenerRegistroTramiteBiodiversidad(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.modelo.servicioBiodiversidad.IServicioBiodiversidad#
     * obtenerPersonasAsignadasTramiteDependencia(java.lang.Long,
     * java.lang.String)
     */
    public List<PersonaTramiteBiodiversidad> obtenerPersonasAsignadasTramiteDependencia(Long tramite,
            String dependencia) throws DataAccessException {
        return biodiversidadDAO.obtenerPersonasAsignadasTramiteDependencia(tramite, dependencia);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.modelo.servicioBiodiversidad.IServicioBiodiversidad#
     * obtenerPersonaEncargadaBiodiversidadVicerrectoria()
     */
    public Persona obtenerPersonaEncargadaBiodiversidadVicerrectoria() throws DataAccessException {
        return biodiversidadDAO.obtenerPersonaEncargadaBiodiversidadVicerrectoria();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.modelo.servicioBiodiversidad.IServicioBiodiversidad#
     * obtenerPersonaControlBiodiversidad()
     */
    public Persona obtenerPersonaControlBiodiversidad() throws DataAccessException {
        return biodiversidadDAO.obtenerPersonaControlBiodiversidad();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.modelo.servicioBiodiversidad.IServicioBiodiversidad#
     * obtenerDatosRevisionTramiteBiodiversidad(java.lang.Long,
     * co.edu.unal.hermes.modelo.InvestigadorInterno)
     */
    public Object[] obtenerDatosRevisionTramiteBiodiversidad(Long idTramite, Persona personaActual)
            throws DataAccessException {
        String dependencia = Dependencia.ID_VICERRECTORIA.toString();
        InvestigadorInterno investigadorActual = personaDAO.obtenerInvestigadorInterno(personaActual.getId());
        TipoTramiteBiodiversidad tramite = obtenerRegistroTramiteBiodiversidad(idTramite);
        List<PersonaTramiteBiodiversidad> listaEncargadosDependencia = obtenerPersonasAsignadasTramiteDependencia(
                idTramite, dependencia);
        Object[] datos = new Object[2];

        if (tramite != null && tramite.getNivelTramite().equals(TipoTramiteBiodiversidad.FACULTAD)) {

            listaEncargadosDependencia = obtenerPersonasAsignadasTramiteDependencia(idTramite,
                    investigadorActual.getDependencia().getFacultad().getId());
            if (listaEncargadosDependencia != null && !listaEncargadosDependencia.isEmpty()) {
                dependencia = investigadorActual.getDependencia().getFacultad().getId();
            }

        } else if (tramite != null && tramite.getNivelTramite().equals(TipoTramiteBiodiversidad.SEDE)) {
            listaEncargadosDependencia = obtenerPersonasAsignadasTramiteDependencia(idTramite,
                    investigadorActual.getDependencia().getSede().getId().toString());

            if (listaEncargadosDependencia != null && !listaEncargadosDependencia.isEmpty()) {
                dependencia = investigadorActual.getDependencia().getSede().getId().toString();
            }
        }

        if (listaEncargadosDependencia == null || listaEncargadosDependencia.isEmpty()) {

            Persona personaVicerrectoria = obtenerPersonaEncargadaBiodiversidadVicerrectoria();
            PersonaTramiteBiodiversidad persona = new PersonaTramiteBiodiversidad();
            InvestigadorInterno encargado;
            if (personaVicerrectoria != null) {
                encargado = personaDAO.obtenerInvestigadorInterno(personaVicerrectoria.getId());
            } else {
                encargado = personaDAO.obtenerInvestigadorInterno(new IdPersona("19380666", "C"));
            }
            persona.setPersonaEncargada(encargado);
            listaEncargadosDependencia.add(persona);
        }

        datos[0] = dependencia;
        datos[1] = listaEncargadosDependencia;

        return datos;
    }

    public boolean requiereMenuBiodiversidadCoordinador(Persona personaActual) throws DataAccessException {
        boolean requiere = false;
        List<TipoTramiteBiodiversidad> listaTramites = biodiversidadDAO
                .obtenerListaTramitesBiodiversidadPersona(personaActual);
        if (listaTramites != null && !listaTramites.isEmpty()) {
            for (int i = 0; i < listaTramites.size(); i++) {
                TipoTramiteBiodiversidad tramite = listaTramites.get(i);
                if (tramite.isEsMenuAdicionalCoordinador()) {
                    requiere = true;
                    break;
                }
            }
        }
        return requiere;
    }

    public Persona obtenerPersonaEncargadaSolicitudesEspecificasVicerrectoria() throws DataAccessException {
        return biodiversidadDAO.obtenerPersonaEncargadaSolicitudesEspecificasVicerrectoria();
    }

    public List<Persona> obtenerPersonasEncargadasColeccionesBiologicas() throws DataAccessException {
        List<Parametro> listaEncargados = biodiversidadDAO.obtenerPersonasEncargadasColeccionesBiologicas();
        List<Persona> listaPersonasEncargadas = new ArrayList<Persona>();
        if (listaEncargados != null && !listaEncargados.isEmpty()) {
            for (int i = 0; i < listaEncargados.size(); i++) {
                Parametro par = listaEncargados.get(i);
                IdPersona id = new IdPersona();
                id.setTipoDocumento(par.getProfesion());
                id.setDocumento(par.getValor());
                Persona persona = personaDAO.obtenerPersona(id);
                listaPersonasEncargadas.add(persona);
            }
        }
        return listaPersonasEncargadas;
    }

    public List<Coleccion> obtenerColeccionesAlertas() {
        List<Coleccion> coleccionesAlerta = new ArrayList<Coleccion>();
        List<Coleccion> coleccionesEvaluar = biodiversidadDAO.obtenerColeccionesCandidatasAlertas();
        for (int i = 0; i < coleccionesEvaluar.size(); i++) {
            Coleccion col = coleccionesEvaluar.get(i);
            if (col.getDiasRestantesActualizacion() > 0 
                    && (col.getDiasRestantesActualizacion() <= (2 - col.getControlAlertas() * 15))) {
                coleccionesAlerta.add(col);
            }
        }
        return coleccionesAlerta;
    }

}
