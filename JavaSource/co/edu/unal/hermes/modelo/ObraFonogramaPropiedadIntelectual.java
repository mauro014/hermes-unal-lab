/**
 * @author Martha Liliana Correa O.
 * @date 07/10/2016
 */

package co.edu.unal.hermes.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ObraFonogramaPropiedadIntelectual implements java.io.Serializable {
 
	
	/**
     * 
     */
    private static final long serialVersionUID = 5403802960210997631L;
    private Long id;
	private PropiedadIntelectual propiedad;
	private String nombreObra;
	
	private Set<PersonaPropiedadIntelectual> autores = new HashSet<PersonaPropiedadIntelectual>();
	
	/** default constructor */
	public ObraFonogramaPropiedadIntelectual() {
		/*
		 * Se crea un objeto vacio de obra fijada en fonograma para ser relacionada con la propiedad intelectual
		 */
	}
	
	public ObraFonogramaPropiedadIntelectual(Long id) {
		this.setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public PropiedadIntelectual getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(PropiedadIntelectual propiedad) {
        this.propiedad = propiedad;
    }

    public String getNombreObra() {
        return nombreObra;
    }

    public void setNombreObra(String nombreObra) {
        this.nombreObra = nombreObra;
    }

    public Set<PersonaPropiedadIntelectual> getAutores() {
        return autores;
    }

    public void setAutores(Set<PersonaPropiedadIntelectual> autores) {
        this.autores = autores;
    }
    
    public List<PersonaPropiedadIntelectual> getListaAutores() {
        ArrayList<PersonaPropiedadIntelectual> listaPersonal = new ArrayList<PersonaPropiedadIntelectual>();
        listaPersonal.addAll(this.autores);
        return listaPersonal;
    }

    public void adicionarAutor(PersonaPropiedadIntelectual per) {
        this.autores.add(per);
    }

    public void borrarAutor(PersonaPropiedadIntelectual per) {
        this.autores.remove(per);
    }
}