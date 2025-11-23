package co.edu.unal.hermes.modelo;

import java.util.Date;


/**
 * 
 * @author Wilver Alexander Martínez Martínez - wam².
 */
public class CorreoTipoPersonaBoletin{

    // llave primaria
    private Long id;
    
  
    private String nombre;
    private String enviado;
    private Date fecha;
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
	public String getEnviado() {
		return enviado;
	}
	public void setEnviado(String enviado) {
		this.enviado = enviado;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	
   
   
  
    
}
