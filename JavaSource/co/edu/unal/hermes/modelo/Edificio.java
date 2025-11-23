/*
 * Created on 24-abr-2006
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package co.edu.unal.hermes.modelo;

/**
 * @author Jassar David Issa Co
 * 
 *         
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class Edificio implements IIdentidad {
	public static String FACULTAD_ENFERMERIA = "101";

	private String id;
	private String nombre;
	private Sede sede;
	private String codigo;
	
	public static String NINGUNO = "NINGUNO";
	
	public String getNombreEdificio() {
		if (nombre.contains(codigo) || nombre.equals(NINGUNO)) {
			return nombre;
		} else {
			return codigo + " - " + nombre;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
