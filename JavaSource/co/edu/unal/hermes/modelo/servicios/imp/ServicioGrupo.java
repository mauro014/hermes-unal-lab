/*
 * Created on 09-ago-2005
 */
package co.edu.unal.hermes.modelo.servicios.imp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import co.edu.unal.hermes.bd.IGrupoDAO;
import co.edu.unal.hermes.modelo.Grupo;
import co.edu.unal.hermes.modelo.GrupoProductoSara;
import co.edu.unal.hermes.modelo.servicios.IServicioGrupo;
import co.edu.unal.hermes.utils.ReemplazaAcentos;

// TODO: Auto-generated Javadoc
/**
 * The Class ServicioGrupo.
 */
public class ServicioGrupo implements IServicioGrupo {

    /** The grupo DAO. */
    private IGrupoDAO grupoDAO;

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#crearGrupo(co.edu.
     * unal.hermes.modelo.Grupo)
     */
    @Override
    public void crearGrupo(Grupo grupo) {
        grupoDAO.crearGrupo(grupo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#guardarGrupo(co.
     * edu.unal.hermes.modelo.Grupo)
     */
    @Override
    public void guardarGrupo(Grupo grupo) {
        grupoDAO.guardarGrupo(grupo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerDependenciasGrupo(java.lang.Long)
     */
    @Override
    public Grupo obtenerDependenciasGrupo(Long id) {
        return grupoDAO.obtenerDependenciasGrupo(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#obtenerGrupo(java.
     * lang.Long)
     */
    @Override
    public Grupo obtenerGrupo(Long id) {
        return grupoDAO.buscarPorId(id);

    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerGrupoBuscador(java.lang.String)
     */
    @Override
    public List<Grupo> obtenerGrupoBuscador(String where) {
        return grupoDAO.obtenerGrupoBuscador(where);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerGrupoDatosBasicos(java.lang.Long)
     */
    @Override
    public Grupo obtenerGrupoDatosBasicos(Long id) {
        return grupoDAO.obtenerGrupoDatosBasicos(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerGrupoInvestigadores(java.lang.Long)
     */
    @Override
    public Grupo obtenerGrupoInvestigadores(Long id) {
        return grupoDAO.obtenerGrupoInvestigadores(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#obtenerGrupoLineas
     * (java.lang.Long)
     */
    @Override
    public Grupo obtenerGrupoLineas(Long id) {
        return grupoDAO.obtenerGrupoLineas(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerGrupoPlanAccion(java.lang.Long)
     */
    @Override
    public Grupo obtenerGrupoPlanAccion(Long id) {
        return grupoDAO.obtenerGrupoPlanAccion(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerGrupoPlanAccionFinanciacion(java.lang.Long)
     */
    @Override
    public Grupo obtenerGrupoPlanAccionFinanciacion(Long id) {
        return grupoDAO.obtenerGrupoPlanAccionFinanciacion(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#obtenerGrupos(java
     * .lang.String, boolean, java.lang.Object[])
     */
    @Override
    public List obtenerGrupos(String where, boolean isParametros, Object[] parametros) {
        return grupoDAO.obtenerGrupos(where, isParametros, parametros);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerGruposParaAval()
     */
    @Override
    public List obtenerGruposParaAval() {
        return grupoDAO.obtenerGruposParaAval();
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerGruposPorCriterio(co.edu.unal.hermes.modelo.Grupo)
     */
    // Agregado por jmesa
    @Override
    public List obtenerGruposPorCriterio(Grupo grupo) {
        List a = new ArrayList();
        try {
            List titles = ReemplazaAcentos.listaPalabrasBusqueda(grupo.getNombre());
            Iterator it = titles.iterator();
            while (it.hasNext()) {
                String s = (String) it.next();
                grupo.setNombre(s);
                a.addAll(grupoDAO.obtenerGruposPorCriterio(grupo));
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return a;
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerGruposXInvestigador(java.lang.String)
     */
    @Override
    public List obtenerGruposXInvestigador(String documento) {
        return this.grupoDAO.obtenerGruposXInvestigador(documento);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerIntegrantesGrupo(java.lang.Long)
     */
    @Override
    public List obtenerIntegrantesGrupo(Long idGrupo) {
        return grupoDAO.obtenerIntegrantesGrupo(idGrupo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerInvestigadoresGrupo(java.lang.Long)
     */
    @Override
    public Set obtenerInvestigadoresGrupo(Long id) {
        return grupoDAO.obtenerInvestigadoresGrupo(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#obtenerMaximoId()
     */
    @Override
    public Long obtenerMaximoId() {
        return grupoDAO.obtenerMaximoId();

    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerProductosSaraGrupo(java.lang.String)
     */
    @Override
    public List<GrupoProductoSara> obtenerProductosSaraGrupo(String grupoId) {
        return this.grupoDAO.obtenerProductosSaraGrupo(grupoId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerProyectosGrupo(java.lang.Long)
     */
    @Override
    public Grupo obtenerProyectosGrupo(Long id) {
        return grupoDAO.obtenerProyectosGrupo(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerProyectosGrupoEdicion(java.lang.String)
     */
    @Override
    public List<String[]> obtenerProyectosGrupoEdicion(String grupoId) {
        return this.grupoDAO.obtenerProyectosGrupoEdicion(grupoId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioGrupo.IServicioGrupo#
     * obtenerResumenGrupo(java.lang.Long)
     */
    @Override
    public Grupo obtenerResumenGrupo(Long id) {
        return grupoDAO.obtenerResumenGrupo(id);
    }

    /**
     * Sets the grupo DAO.
     *
     * @param grupoDAO
     *            the new grupo DAO
     */
    public void setGrupoDAO(IGrupoDAO grupoDAO) {
        this.grupoDAO = grupoDAO;
    }

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
    public Grupo buscarPorId(Long id, boolean incluirProyectos) throws DataAccessException {
        return this.grupoDAO.buscarPorId(id, incluirProyectos);
    }
}
