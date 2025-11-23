/********************************************************************************
Autor 		: Ing. Wilver Alexander Martínez Martínez - wam² - UN.
Clase    	: co.edu.unal.hermes.modelo.ArchivoInforme
Objetivo 	: Clase POJO para la tabla HER_ARCHIVO_GRUPO en la cual se encuentran  
			  los archivos publicados como descargas por los grupos de investigación.
Creación	: Abril 07 de 2010
Modificación: Diciembre 12 de 2014 - Mauricio Amaya Ríos
Detalle		: Cambio para que no se reviente cuando bytes esta en null, dado que 
			  los rachivos se guardan en disco ahora.
********************************************************************************/

package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;

public class ArchivoMovilidadVE implements Serializable{
		
	private static final long serialVersionUID = 961836014775154316L;
	private Long id;
	private String nombre;
	private Blob archivo;
	private Date fecha;
	private MovilidadVisitanteExterior movilidad;
	private TipoMovilidad tipoMovilidad;
	private TipoArchivoMovilidad tipoArchivo;
	private Persona persona;
		
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public Blob getArchivo() 
	{
		return archivo;
	}

	public void setArchivo(Blob archivo) 
	{
		this.archivo = archivo;
	}

	public void setBytes(byte[] bytes){
		archivo = Hibernate.createBlob(bytes);
    }
    public byte[] getBytes(){
        byte[] resultado = null;
        try {
            resultado = archivo.getBytes(1, (int) archivo.length());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch(NullPointerException npe){
        	System.out.println("Archivo esta en disco");
        }
        return resultado;
    }

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public TipoMovilidad getTipoMovilidad() {
		return tipoMovilidad;
	}

	public void setTipoMovilidad(TipoMovilidad tipoMovilidad) {
		this.tipoMovilidad = tipoMovilidad;
	}

	public MovilidadVisitanteExterior getMovilidad() {
		return movilidad;
	}

	public void setMovilidad(MovilidadVisitanteExterior movilidad) {
		this.movilidad = movilidad;
	}

	public TipoArchivoMovilidad getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(TipoArchivoMovilidad tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
		
}
