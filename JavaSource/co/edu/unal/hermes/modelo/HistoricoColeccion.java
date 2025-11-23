/*
 * Created on 03-ago-2024
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;


/**
 * Guarda un Histórico de edición de la colección por formulario 
 */
public class HistoricoColeccion {

    private Long id;
    private Coleccion coleccion;
    private String seccionFormulario;
    private String estado;
    private String justificacion;
    private Persona responsable;
    private Date fecha;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String getNombreFormulario() {
		String nombre = "";
		if(seccionFormulario!=null) {
			if(seccionFormulario.equals("1")) {
				nombre = "Actualización - Información Básica";
			}else if(seccionFormulario.equals("2")) {
				nombre = "Actualización - Clasificación";
			}else if(seccionFormulario.equals("3")) {
				nombre = "Actualización - Cobertura";
			}else if (seccionFormulario.equals("4")){
				nombre = "Actualización - Tipos de preservación";
			}else if (seccionFormulario.equals("5")){
				nombre = "Actualización - Ejemplares tipo";
			}else if (seccionFormulario.equals("6")){
				nombre = "Actualización - Catalogación";
			}else if (seccionFormulario.equals("7")){
				nombre = "Actualización - Personal asociado";
			}else if (seccionFormulario.equals("8")){
				nombre = "Actualización - Documentos";
			}else if (seccionFormulario.equals("-1")){
				nombre = "Gestión Coordinador - Envío de comentarios a director";
			}else if (seccionFormulario.equals("-2")){
				nombre = "Actualización Estado - Gestión coordinador";
			}else if (seccionFormulario.equals("0")){
				nombre = "Finalización de edicion colección";
			}else {
				nombre="Desconocido";
			}
		}			
		return nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Coleccion getColeccion() {
		return coleccion;
	}

	public void setColeccion(Coleccion coleccion) {
		this.coleccion = coleccion;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getSeccionFormulario() {
		return seccionFormulario;
	}

	public void setSeccionFormulario(String seccionFormulario) {
		this.seccionFormulario = seccionFormulario;
	}
    
}
