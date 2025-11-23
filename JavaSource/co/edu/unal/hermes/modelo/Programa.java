package co.edu.unal.hermes.modelo;

import java.io.Serializable;

/**
 * Suministra la información principal 
 * de los programas académicos de posgrado 
 */
public class Programa implements Serializable{

	private String id;
	private String idDependencia;
	private String idTipoNivelPrograma;
	private String idEstadoPrograma;
	private String nombre;
	
	//
	private String sede;
	private String facultad;
	
	/**
     * Sobreescritura del metodo equals() de "object", especifica para programa 
     */
    public boolean equals(Object object){        
        if(object instanceof Programa){
            Programa programa = (Programa) object;
            if(this.id.equals(programa.getId())){
                return true;                
            }else{
                return false;                
            }            
        }else{
            return false;
        }        
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

	public String getIdDependencia() {
		return idDependencia;
	}

	public void setIdDependencia(String idDependencia) {
		this.idDependencia = idDependencia;
	}

	public String getIdTipoNivelPrograma() {
		return idTipoNivelPrograma;
	}

	public void setIdTipoNivelPrograma(String idTipoNivelPrograma) {
		this.idTipoNivelPrograma = idTipoNivelPrograma;
	}

	public String getIdEstadoPrograma() {
		return idEstadoPrograma;
	}

	public void setIdEstadoPrograma(String idEstadoPrograma) {
		this.idEstadoPrograma = idEstadoPrograma;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}
	
	
}
