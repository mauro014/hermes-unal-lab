package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Relaciona la lista del pais donde los investigadores están 
 * ejecutando el proyecto
 */
public class Pais implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String id;
	private String nombre;
	private String sigla;
	private Long continente;
	
	public Pais(){
	    
	}
	
	public Pais(String id){
	    this.id=id;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
        
	/**
	 * @return Returns the sigla.
	 */
	public String getSigla() {
		return sigla;
	}
	/**
	 * @param sigla The sigla to set.
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * @return the continente
	 */
	public Long getContinente()
	{
		return continente;
	}

	/**
	 * @param continente the continente to set
	 */
	public void setContinente(Long continente)
	{
		this.continente = continente;
	}
}
