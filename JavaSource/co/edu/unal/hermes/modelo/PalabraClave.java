/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import co.edu.unal.hermes.utils.UtilPalabraClave;

/**
 * Maneja las palabras claves.
 */
public class PalabraClave implements Cloneable{
    
    /** The id. */
    private Long id;   
    
    /** The palabra. */
    private String palabra;
    
    /** The palabra vista. */
    private String palabraVista;
    
    /** The palabra original. */
    private String palabraOriginal;
    
    /** The idioma. */
    private String idioma;
    
    /** The proyectos. */
    private Set proyectos = new HashSet();
    
    /** The evaluadores. */
    private Set evaluadores = new HashSet();
    
    /** The avales. */
    private Set avales = new HashSet();
    
    /** The movilidades visitante extranjero. */
    private Set movilidadesVisitanteExtranjero = new HashSet();
    
    /** The movilidades docente extranjero. */
    private Set movilidadesDocenteExtranjero = new HashSet();
    
    /** The movilidades estudiantes posgrado. */
    private Set movilidadesEstudiantesPosgrado = new HashSet();
	
	/**
	 * Constructo tradicional.
	 */
	public PalabraClave(){
		
	}
	
	/**
	 * Constructor que recibe como parametro el id, para inicializar
	 * el id del objeto.
	 *
	 * @param pId the id
	 */
	public PalabraClave(Long pId){
		this.id = pId;
	}
	
    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}
    
    /**
     * Existe palabra en set.
     *
     * @param palabras the palabras
     * @return true, if successful
     */
    public boolean existePalabraEnSet(Set palabras){
        Iterator it = palabras.iterator();
        while(it.hasNext()){
            Object obj = it.next();
            if( obj instanceof PalabraClave ){
                if( ((PalabraClave)obj).getPalabra().toUpperCase().equals(palabra.toUpperCase()) &&  ((PalabraClave)obj).getIdioma().equals("ES")){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Existe palabra en set en.
     *
     * @param palabras the palabras
     * @return true, if successful
     */
    public boolean existePalabraEnSetEN(Set palabras){
        Iterator it = palabras.iterator();
        while(it.hasNext()){
            Object obj = it.next();
            if( obj instanceof PalabraClave ){
                if( ((PalabraClave)obj).getPalabra().toUpperCase().equals(palabra.toUpperCase()) &&  ((PalabraClave)obj).getIdioma().equals("EN")){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }
       
    /**
     * Gets the palabra.
     *
     * @return the palabra
     */
    public String getPalabra() {
        return palabra;
    }
    
    /**
     * Sets the palabra.
     *
     * @param palabra the new palabra
     */
    public void setPalabra(String palabra) {
    	this.palabra=palabra;
    }
    
    /**
     * Gets the palabra vista.
     *
     * @return the palabra vista
     */
    public String getPalabraVista() {
        return UtilPalabraClave.convertirPrimeraLetraMayuscula(palabra);
    }
    
    /**
     * Sets the palabra vista.
     *
     * @param palabraVista the new palabra vista
     */
    private void setPalabraVista(String palabraVista) {
        this.palabraVista = palabraVista;
    }
    
    /**
     * Gets the proyectos.
     *
     * @return the proyectos
     */
    public Set getProyectos() {
        return proyectos;
    }    
    
    /**
     * Sets the proyectos.
     *
     * @param proyectos the new proyectos
     */
    public void setProyectos(Set proyectos) {
        this.proyectos = proyectos;
    }
           
    /**
     * Gets the evaluadores.
     *
     * @return the evaluadores
     */
    public Set getEvaluadores() {
        return evaluadores;
    }
    
    /**
     * Sets the evaluadores.
     *
     * @param evaluadores the new evaluadores
     */
    public void setEvaluadores(Set evaluadores) {
        this.evaluadores = evaluadores;
    }

	/**
	 * Gets the palabra original.
	 *
	 * @return the palabra original
	 */
	public String getPalabraOriginal() {
		return palabraOriginal;
	}

	/**
	 * Sets the palabra original.
	 *
	 * @param palabraOriginal the new palabra original
	 */
	public void setPalabraOriginal(String palabraOriginal) {
		this.palabraOriginal = palabraOriginal;
	}

	/**
	 * Gets the idioma.
	 *
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Sets the idioma.
	 *
	 * @param idioma the new idioma
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Gets the avales.
	 *
	 * @return the avales
	 */
	public Set getAvales() {
		return avales;
	}

	/**
	 * Sets the avales.
	 *
	 * @param avales the new avales
	 */
	public void setAvales(Set avales) {
		this.avales = avales;
	}

	/**
	 * Sets the movilidades visitante extranjero.
	 *
	 * @param movilidadesVisitanteExtranjero the new movilidades visitante extranjero
	 */
	public void setMovilidadesVisitanteExtranjero(
			Set movilidadesVisitanteExtranjero) {
		this.movilidadesVisitanteExtranjero = movilidadesVisitanteExtranjero;
	}

	/**
	 * Gets the movilidades visitante extranjero.
	 *
	 * @return the movilidades visitante extranjero
	 */
	public Set getMovilidadesVisitanteExtranjero() {
		return movilidadesVisitanteExtranjero;
	}

	/**
	 * Sets the movilidades docente extranjero.
	 *
	 * @param movilidadesDocenteExtranjero the new movilidades docente extranjero
	 */
	public void setMovilidadesDocenteExtranjero(
			Set movilidadesDocenteExtranjero) {
		this.movilidadesDocenteExtranjero = movilidadesDocenteExtranjero;
	}

	/**
	 * Gets the movilidades docente extranjero.
	 *
	 * @return the movilidades docente extranjero
	 */
	public Set getMovilidadesDocenteExtranjero() {
		return movilidadesDocenteExtranjero;
	}

	/**
	 * Sets the movilidades estudiantes posgrado.
	 *
	 * @param movilidadesEstudiantesPosgrado the new movilidades estudiantes posgrado
	 */
	public void setMovilidadesEstudiantesPosgrado(
			Set movilidadesEstudiantesPosgrado) {
		this.movilidadesEstudiantesPosgrado = movilidadesEstudiantesPosgrado;
	}

	/**
	 * Gets the movilidades estudiantes posgrado.
	 *
	 * @return the movilidades estudiantes posgrado
	 */
	public Set getMovilidadesEstudiantesPosgrado() {
		return movilidadesEstudiantesPosgrado;
	}
	
	
	
}
