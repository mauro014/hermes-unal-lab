/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;

/**
 * Especifica el archivo que contiene el proyecto, el cual va a ser
 * cargado por el investigador
 */
public class Archivo {

    private Long id;    
    private String nombre;
    private Blob datos;
    private TipoArchivo tipoArchivo;
    private Proyecto proyecto;
    private Date fecha;
    private Persona responsable;

    public Long getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Blob getDatos() {
        return datos;
    }
    public void setDatos(Blob datos) {
        this.datos = datos;
    }
    public TipoArchivo getTipoArchivo() {
        return tipoArchivo;
    }
    public void setTipoArchivo(TipoArchivo tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }
    public Proyecto getProyecto() {
        return proyecto;
    }
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    public void setBytes(byte[] bytes){
        datos = Hibernate.createBlob(bytes);
    }
    public byte[] getBytes(){
        byte[] resultado = null;
        try {
            resultado = datos.getBytes(1, (int) datos.length());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
	
	public boolean equals(Object object) {
        if (object instanceof Archivo) {
        	Archivo archivo = (Archivo) object;
            if (this.id.equals(archivo.getId())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
