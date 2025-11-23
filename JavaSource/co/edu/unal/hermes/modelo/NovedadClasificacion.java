/**
 * @author Martha Liliana Correa O.
 * @date 09/11/2016
 */

package co.edu.unal.hermes.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NovedadClasificacion {

    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;
    
    private Set<Novedad> novedades = new HashSet<Novedad>();

    public NovedadClasificacion() {
        // Para crear objeto vacio
    }

    public NovedadClasificacion(Long id) {
        setId(id);
    }

    /**
     * @return Returns the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Returns the nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *            The nombre to set.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Novedad> getNovedades() {
        return novedades;
    }

    public void setNovedades(Set<Novedad> novedades) {
        this.novedades = novedades;
    }
    
    public List<Novedad> getListaNovedades() {
        ArrayList<Novedad> listaNovedades = new ArrayList<Novedad>();
        listaNovedades.addAll(this.novedades);
        return listaNovedades;
    }
}
