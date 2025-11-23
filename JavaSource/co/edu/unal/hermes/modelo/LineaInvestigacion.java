/*
 * Created on 03-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author JuanPablo
 *Especifica las líneas de investigación determinadas por la
 *Universidad Nacional
 */
public class LineaInvestigacion implements Serializable{
    
    // identificacion para el campo origen en donde se denota que la linea de investigacion es propuesta por el usuario
    public static String PROPUESTA = "P";
   
    private Long id;
    private String nombre;
    private String origen;
   
    // Relaciones con otras entidades del modelo
    private Set proyectos = new HashSet();
    private Set grupos = new HashSet();
    private Set investigadores = new HashSet();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set getGrupos() {
        return grupos;
    }

    public void setGrupos(Set grupos) {
        this.grupos = grupos;
    }

    public Set getInvestigadores() {
        return investigadores;
    }

    public void setInvestigadores(Set investigadores) {
        this.investigadores = investigadores;
    }

    public Set getProyectos() {
        return proyectos;
    }

    public void setProyectos(Set proyectos) {
        this.proyectos = proyectos;
    }

    public String getOrigen() {
        return origen;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }
    
    public boolean equals(Object o)
    {
        if(!(o instanceof LineaInvestigacion))
        {
            return false;
        }
        if(o==null)
        {
            return false;
        }
        LineaInvestigacion l=(LineaInvestigacion) o;
        if(l.getId()!=null && this.id!=null)
        {
            return l.getId().equals(this.getId());
        }
        return false;
    }
}
