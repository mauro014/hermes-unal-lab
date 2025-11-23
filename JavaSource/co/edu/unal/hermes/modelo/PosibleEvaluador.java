/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.util.List;

/**
 * Relaciona la información de contacto de personas que pueden ser
 * posibles evaluadores, recomendados por el investigador los cuales 
 * son conocedores de los temas tratados en el proyecto.
 */
public class PosibleEvaluador {

    private Long id;    
    private String nombre;
    private String email;
    private String telefono;
    private String fax;
    private String institucion;
    private String experticia;
    private String direccionPostal;
    private String apellido1;
    private String apellido2;
    private String ciudad;
    private String tipo;
    private boolean externo;
    List   listaClasificacionConocimiento;
    
    private Proyecto proyecto;
    private ClasificacionConocimiento clasificacionConocimiento;

    private String documento;
    private String tipoDocumento;
    
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFax() {
        return fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }
    public String getInstitucion() {
        return institucion;
    }
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public Proyecto getProyecto() {
        return proyecto;
    }
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    /*
    public String getExperticia() {
        return experticia;
    }
    public void setExperticia(String experticia) {
        this.experticia = experticia;
    }*/
    
    public String getApellido1() {
        return apellido1;
    }
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }
    public String getApellido2() {
        return apellido2;
    }
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public String getDireccionPostal() {
        return direccionPostal;
    }
    public void setDireccionPostal(String direccionPostal) {
        this.direccionPostal = direccionPostal;
    }
    public ClasificacionConocimiento getClasificacionConocimiento() {
        return clasificacionConocimiento;
    }
    public void setClasificacionConocimiento(
            ClasificacionConocimiento clasificacionConocimiento) {
        this.clasificacionConocimiento = clasificacionConocimiento;
    }
    
    public boolean equals(Object obj){
	    if(obj instanceof PosibleEvaluador){
	        PosibleEvaluador posibleEvaluador = (PosibleEvaluador)obj;
	        if (posibleEvaluador.getId() == null || this.id == null ){
	        	if ( posibleEvaluador.getApellido1().equals(this.apellido1) && posibleEvaluador.getApellido2().equals(this.apellido2) && posibleEvaluador.getNombre().equals(this.nombre) && posibleEvaluador.getEmail().equals(this.email)){
	        		return true;
	        	}else{
	        		return false;
	        	}
	        	
	        }
	        if(posibleEvaluador.getId().equals(this.id)){
	            return true;
	        }
	    }
		return false;
	}
    
    
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public String getTipoDocumento() {
        return tipoDocumento;
    }
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
	public String getExperticia() {
		return experticia;
	}
	public void setExperticia(String experticia) {
		this.experticia = experticia;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipo() {
		return tipo;
	}
	
	 
	public void setExterno(boolean externo) {
		this.externo = externo;
	}
	public boolean isExterno() {
		return externo;
	}
	
	public String getNombreCompleto(){
		return nombre + " " + apellido1 + " " + apellido2;
	}
	
}
