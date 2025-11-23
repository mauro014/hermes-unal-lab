package co.edu.unal.hermes.modelo.servicioDependencia;

import java.util.List;

import javax.faces.model.SelectItem;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.IdPersona;


/**
 * The Interface IServicioDependencia.
 */
public interface IServicioDependencia {
    
    public Dependencia obtenerDependencia(String id);
    
    /**
     * Obtener dependencias hija.
     *
     * @param d
     *            the d
     * @param listaDependencias
     *            the lista dependencias
     */
    public void obtenerDependenciasHija(Dependencia d, List<Dependencia> listaDependencias);
    
    /**
     * Obtener dependencias activas facultad y sede.
     *
     * @return the list
     */
    public List<Dependencia> obtenerDependenciasActivasFacultadYSede();
    
    /**
     * Obtener dependencia.
     *
     * @param id the id
     * @return the dependencia
     */
    public Dependencia obtenerDependencia(IdPersona id);
    
    /**
     * Obtener dependencia2.
     *
     * @param id the id
     * @return the dependencia
     */
    public Dependencia obtenerDependencia2(IdPersona id);
    
    /**
     * Crear select item.
     *
     * @param dependencias the dependencias
     * @return the list
     */
    public List<SelectItem> crearSelectItem(List<Dependencia> dependencias);
    
    /**
     * Obtener dependencia persona.
     *
     * @param id the id
     * @return the dependencia
     */
    public Dependencia obtenerDependenciaPersona(IdPersona id);

    /**
     * Obtener dependencia x sede.
     *
     * @param sedeId the sede id
     * @return the list
     */
    public List<Dependencia> obtenerDependenciaXSede(Long sedeId);
    
    /**
     * Obtener dependencia x sede.
     *
     * @param sedeId the sede id
     * @return the list
     */
    public List<Dependencia> obtenerDependenciaXSede(String sedeId);
    
    /**
     * Obtener facultades x sede.
     *
     * @param sedeId the sede id
     * @return the list
     */
    public List<Dependencia> obtenerFacultadesXSede(Long sedeId);
    
    /**
     * Obtener facultades x sede.
     *
     * @param sedeId the sede id
     * @return the list
     */
    public List<Dependencia> obtenerFacultadesXSede(String sedeId);
    
    /**
     * Obtener dependencias x facultad.
     *
     * @param sedeId the sede id
     * @return the list
     */
    public List<Dependencia> obtenerDependenciasXFacultad(Long facultadId);
    
    /**
     * Obtener dependencias x facultad.
     *
     * @param sedeId the sede id
     * @return the list
     */
    public List<Dependencia> obtenerDependenciasXFacultad(String facultadId);
    
}
