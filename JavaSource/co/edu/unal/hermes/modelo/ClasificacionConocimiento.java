/*
 * Created on 07-oct-2005
 */
package co.edu.unal.hermes.modelo;

/**
 * @author jpduqueg
 */
public class ClasificacionConocimiento implements IIdentidad {

	public static String RAIZ = "0";

	private String id;
	private ClasificacionConocimiento padre;
	private Integer nivel;
	private String nombre;

	public ClasificacionConocimiento() {
	}

	public ClasificacionConocimiento(String id) {
		this.id = id;
	}

	/**
	 * @return String del tipo: Código - Nombre
	 */
	public String getCodigoNombre() {
		String codigoNombre = "";
		if(id.length() == 1) {
			codigoNombre += id;
		} else {
			codigoNombre+= id.substring(1);
		}
		codigoNombre += " - " + nombre;
		return codigoNombre;
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

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public ClasificacionConocimiento getPadre() {
		return padre;
	}

	public void setPadre(ClasificacionConocimiento padre) {
		this.padre = padre;
	}

	@Override
	public boolean equals(Object otroObjeto) {
		if (otroObjeto == null
				|| !(otroObjeto instanceof ClasificacionConocimiento)) {
			return false;
		} else {
			ClasificacionConocimiento otraClasificacionConocimiento = (ClasificacionConocimiento) otroObjeto;
			if (id.equals(otraClasificacionConocimiento.id)) {
				return true;
			} else {
				return false;
			}

		}

	}
}
