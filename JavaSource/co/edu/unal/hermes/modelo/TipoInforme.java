/********************************************************************************
Autor 		: Ing. Wilver Alexander Martínez Martínez - wam² - UN.
Clase    	: co.edu.unal.hermes.modelo.TipoInforme
Objetivo 	: Clase POJO para la tabla HER_TIPO_INFORME en la cual se encuentran  
			  los tipos de informes para un proyecto de investigación.
Creación	: Abril 07 de 2010
Modificación:
Detalle		:
 ********************************************************************************/

package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

import org.hibernate.Hibernate;

public class TipoInforme implements Serializable {

	private static final long serialVersionUID = 2000287822832331038L;
	public static final String RAIZ = "0";
	private Long id;
	private String nombre;
	Blob archivoInforme;
	private String nombreArchivo;

	public static final Long INFORME_AVANCE = 1L;
	public static final Long INFORME_FINAL = 2L;
	public static final Long DESEMBOLSOS = 4L;
	public static final Long OBLIGACIONES = 5L;

	public TipoInforme() {
	}

	public TipoInforme(Long id) {
		setId(id);
	}

	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public void setBytesArchivoInforme(byte[] bytes){
		archivoInforme= Hibernate.createBlob(bytes);
	}

	public byte[] getBytesArchivoInforme(){
		byte[] resultado = null;
		try {
			resultado = archivoInforme.getBytes(1, (int) archivoInforme.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public Blob getArchivoInforme() {
		return archivoInforme;
	}

	public void setArchivoInforme(Blob archivoInforme) {
		this.archivoInforme = archivoInforme;
	}

}
