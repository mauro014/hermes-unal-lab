package co.edu.unal.hermes.modelo;

/**
 * 
 * @author Wilver Alexander Martínez Martínez - wam².
 */
public class CorreoPersonaBoletin{

    // llave primaria
    private Long id;
    private String correoPersona;
    private String sede;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCorreoPersona() {
		return correoPersona;
	}


	public void setCorreoPersona(String correoPersona) {
		this.correoPersona = correoPersona;
	}


	public String getSede() {
		return sede;
	}


	public void setSede(String sede) {
		this.sede = sede;
	}
   
   
  
    
}
