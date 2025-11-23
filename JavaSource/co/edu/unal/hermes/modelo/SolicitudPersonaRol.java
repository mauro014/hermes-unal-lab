
package co.edu.unal.hermes.modelo;

import java.io.Serializable;


public class SolicitudPersonaRol implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long idSolicitud;
	//private String rol;
	private Rol rol;
	private String accion;
	private String nombreAccion;
	
	
	/*public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}*/
		
	public String getAccion() {
		//String accionNombre = new String();
		if(this.accion.equals("A")){
			this.nombreAccion=("AGREGAR");
		}
		if(this.accion.equals("R")){
			this.nombreAccion=("RETIRAR");
		}
		return accion;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public String getnombreAccion() {
	  
		return nombreAccion;
	}
	public void setnombreAccion(String nombreAccion) {
		this.nombreAccion = nombreAccion;
	}
}
