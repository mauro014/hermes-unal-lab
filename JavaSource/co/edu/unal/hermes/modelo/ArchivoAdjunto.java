/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.sql.Blob;
import java.sql.SQLException;

import org.hibernate.Hibernate;

/**
 * Especifica el archivo adjunto
 */
public class ArchivoAdjunto {

    private Long id;    
    private String nombre;
    private Blob datos;

	/**
	 * Constructo tradicional
	 */
	public ArchivoAdjunto(){
		
	}
	
	/**
	 * Constructor que recibe como parametro el id, para inicializar
	 * el id del objeto.
	 * 
	 * @param LongpIdTipoDato
	 */
	public ArchivoAdjunto(Long pId){
		this.id = pId;
	}
    
    public Long getId() {
        return id;
    }
    private void setId(Long id) {
        this.id = id;
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
}
