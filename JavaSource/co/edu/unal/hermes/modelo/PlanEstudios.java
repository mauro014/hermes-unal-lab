/*
 * Created on 21-jul-2006
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * The Class PlanEstudios.
 */
public class PlanEstudios implements Serializable{

    /** The Constant DOCTORADO. */
    public static final Long DOCTORADO = 7L;
    
    /** The Constant ESPECIALIZACION. */
    public static final Long ESPECIALIZACION = 4L;
    
    /** The Constant ESPECIALIDAD. */
    public static final Long ESPECIALIDAD = 5L;
    
    /** The Constant MAESTRIA. */
    public static final Long MAESTRIA = 6L;
    
    /** The Constant PREGRADO. */
    public static final Long PREGRADO = 3L;
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4815571248530247879L;
    
    /** The id. */
    private String id;
    
    /** The nombre. */
    private String nombre;
    
    /** The tipo. */
    private Long tipo;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the tipo.
	 *
	 * @return the tipo
	 */
	public Long getTipo() {
		return tipo;
	}

	/**
	 * Sets the tipo.
	 *
	 * @param tipo the new tipo
	 */
	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}   
	
	public String getNombreTipo() {
		if(tipo == 3L) {
			return "Pregrado";
		}
            return "Posgrado";
		
	}
	
}
