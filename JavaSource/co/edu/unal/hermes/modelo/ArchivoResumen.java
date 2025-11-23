/*
 * Created on 18-ago-2005
 */
package co.edu.unal.hermes.modelo;

import java.util.Date;

/**
 * Especifica el resumen del archivo
 */
public class ArchivoResumen {

	/**
	 * @param id
	 * @param nombre
	 * @param tipoArchivo
	 */
	public ArchivoResumen(Long id, String nombre, TipoArchivo tipoArchivo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipoArchivo = tipoArchivo;
	}

	public ArchivoResumen() {
		// TODO Auto-generated constructor stub
	}

	private Long id;
	private String nombre;

	private TipoArchivo tipoArchivo;
	private Persona responsable;
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

	public TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(TipoArchivo tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
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

}
