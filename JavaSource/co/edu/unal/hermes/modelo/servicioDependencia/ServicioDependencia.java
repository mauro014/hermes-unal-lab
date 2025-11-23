package co.edu.unal.hermes.modelo.servicioDependencia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.bd.IDependenciaDAO;
import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;

/**
 * The Class ServicioDependencia.
 */
public class ServicioDependencia implements IServicioDependencia {
    
    /** The dependencia dao. */
    private IDependenciaDAO dependenciaDAO;

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioDependencia.IServicioDependencia#obtenerDependencia(java.lang.String)
     */
    public Dependencia obtenerDependencia(String id) {
        return dependenciaDAO.obtenerDependencia(id);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioDependencia.IServicioDependencia#
     * obtenerDependenciasHija(co.edu.unal.hermes.modelo.Dependencia,
     * java.util.List)
     */
    public void obtenerDependenciasHija(Dependencia d, List<Dependencia> listaDependencias) {
        dependenciaDAO.obtenerDependenciasHija(d, listaDependencias);
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.edu.unal.hermes.modelo.servicioDependencia.IServicioDependencia#
     * obtenerDependenciasActivasFacultadYSede()
     */
    public List<Dependencia> obtenerDependenciasActivasFacultadYSede() {
        return dependenciaDAO.obtenerDependenciasActivasFacultadYSede();
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioDependencia.IServicioDependencia#crearSelectItem(java.util.List)
     */
    public List<SelectItem> crearSelectItem(List<Dependencia> dependencias) {
        List<SelectItem> listaItems = new ArrayList<SelectItem>();
        if (dependencias != null && !dependencias.isEmpty()) {
            Iterator<Dependencia> i = dependencias.iterator();
            while (i.hasNext()) {
                Dependencia dependencia = i.next();
                listaItems.add(new SelectItem(dependencia.getId(), dependencia.getNombre()));
            }
        }
        return listaItems;
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioDependencia.IServicioDependencia#obtenerDependencia(co.edu.unal.hermes.modelo.IdPersona)
     */
    public Dependencia obtenerDependencia(IdPersona id) {
        return dependenciaDAO.obtenerDependencia(id);
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioDependencia.IServicioDependencia#obtenerDependencia2(co.edu.unal.hermes.modelo.IdPersona)
     */
    public Dependencia obtenerDependencia2(IdPersona id) {
        return dependenciaDAO.obtenerDependencia2(id);
    }

    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioDependencia.IServicioDependencia#obtenerDependenciaPersona(co.edu.unal.hermes.modelo.IdPersona)
     */
    public Dependencia obtenerDependenciaPersona(IdPersona id) {
        return dependenciaDAO.obtenerDependenciaPersona(id);
    }
    
    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioDependencia.IServicioDependencia#obtenerDependenciaXSede(java.lang.Long)
     */
    public List<Dependencia> obtenerDependenciaXSede(Long sedeId){
        return dependenciaDAO.obtenerDependenciaXSede(sedeId);
    }
    
    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioDependencia.IServicioDependencia#obtenerDependenciaXSede(java.lang.String)
     */
    public List<Dependencia> obtenerDependenciaXSede(String sedeId){
        try{
            Long idLong = Long.parseLong(sedeId);
            return dependenciaDAO.obtenerDependenciaXSede(idLong);
        }
        catch(NumberFormatException nfe){
            return new ArrayList<Dependencia>();
        }
    }
    
    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioDependencia.IServicioDependencia#obtenerFacultadesXSede(java.lang.Long)
     */
    public List<Dependencia> obtenerFacultadesXSede(Long sedeId){
        return dependenciaDAO.obtenerFacultadesXSede(sedeId);
    }
    
    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioDependencia.IServicioDependencia#obtenerFacultadesXSede(java.lang.String)
     */
    public List<Dependencia> obtenerFacultadesXSede(String sedeId){
        try{
            Long idLong = Long.parseLong(sedeId);
            return dependenciaDAO.obtenerFacultadesXSede(idLong);
        }
        catch(NumberFormatException nfe){
            return new ArrayList<Dependencia>();
        }
    }
    
    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioDependencia.IServicioDependencia#obtenerDependenciasXFacultad(java.lang.Long)
     */
    public List<Dependencia> obtenerDependenciasXFacultad(String facultadId){
        return dependenciaDAO.obtenerDependenciasXFacultad(facultadId);
    }
    
    /* (non-Javadoc)
     * @see co.edu.unal.hermes.modelo.servicioDependencia.IServicioDependencia#obtenerDependenciasXFacultad(java.lang.String)
     */
    public List<Dependencia> obtenerDependenciasXFacultad(Long facultadId){
        try{
            //Long idLong = Long.parseLong(facultadId);
            return dependenciaDAO.obtenerDependenciasXFacultad(facultadId.toString());
        }
        catch(NumberFormatException nfe){
            return new ArrayList<Dependencia>();
        }
    }

    /**
     * Sets the dependencia dao.
     *
     * @param dependenciaDAO the new dependencia dao
     */
    public void setDependenciaDAO(IDependenciaDAO dependenciaDAO) {
        this.dependenciaDAO = dependenciaDAO;
    }
    
}
