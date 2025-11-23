/********************************************************************************
Autor 		: Ing. Wilver Alexander Martínez Martínez - wam² - UN.
Clase    	: co.edu.unal.hermes.modelo.ArchivoInforme
Objetivo 	: Clase POJO para la tabla HER_ARCHIVO_GRUPO en la cual se encuentran  
			  los archivos publicados como descargas por los grupos de investigación.
Creación	: Abril 07 de 2010
Modificación:
Detalle		:
 ********************************************************************************/

package co.edu.unal.hermes.modelo;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;

public class ArchivoInforme {

	public static String BORRADO = "B";
	private Long id;
	private String nombre;
	private Blob archivo;
	private String descripcion;
	private String estado;
	private Date fecha;
	private ProyectoInforme informe;
	public static final String RUTA_ARCHIVO_DISCO = "HER_ARCHIVO_INFORME//";
	private Persona responsable;

	private Date fechaEliminacion;
    private Persona responsableEliminacion;

	private Long aval;
	private TipoArchivo tipoArchivo;

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

	public Blob getArchivo() {
		return archivo;
	}

	public void setArchivo(Blob archivo) {
		this.archivo = archivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getAval() {
		return aval;
	}

	public void setAval(Long aval) {
		this.aval = aval;
	}

	public void setBytes(byte[] bytes) {
		archivo = Hibernate.createBlob(bytes);
	}

	public byte[] getBytes() {
		byte[] resultado = null;
		try {
			resultado = archivo.getBytes(1, (int) archivo.length());
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

	public ProyectoInforme getInforme() {
		return informe;
	}

	public void setInforme(ProyectoInforme informe) {
		this.informe = informe;
	}

	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}

	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}

	public Persona getResponsableEliminacion() {
		return responsableEliminacion;
	}

	public void setResponsableEliminacion(Persona responsableEliminacion) {
		this.responsableEliminacion = responsableEliminacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

}
