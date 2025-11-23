/*
 * Created on 11-dic-2007
 */
package co.edu.unal.hermes.modelo;

/**
 * @author lgrojas
 */
public class EvaluadorCorreo {
         
    private long proyecto;    
    private String investigador;
    private String contactado;
    private String respuesta;
    private String nombre;
    private boolean mostrado;
    
    //private Proyecto proyecto2;
    //private Investigador investigador2;
	

	public EvaluadorCorreo(){
    	this.mostrado = true;
    }
    
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public long getProyecto() {
		return proyecto;
	}
	public void setProyecto(long proyecto) {
		this.proyecto = proyecto;
	}
	public String getInvestigador() {
		return investigador;
	}
	public void setInvestigador(String investigador) {
		this.investigador = investigador;
	} 
	public String getContactado() {
		return contactado;
	}
	public void setContactado(String contactado) {
		this.contactado = contactado;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}    
	public boolean isMostrado() {
		return mostrado;
	}

	public void setMostrado(boolean mostrado) {
		this.mostrado = mostrado;
	}
   
}
