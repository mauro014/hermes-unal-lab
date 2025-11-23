/*
 * Created on 24-jul-2006
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package co.edu.unal.hermes.modelo;

import java.util.List;

/**
 * @author Jassar David Issa Co
 * 
 */
public class ProductoTipo implements IIdentidad {

	public static final String RAIZ = "0";
	public static final String RAIZ_LISTA_UNIFICADA = "-1";

	private String id;
	private String nombre;
	private String nombrePop;
	private ProductoTipo padre;
	private String descripcion;
	private String nivel;
	private List hijos;
	private boolean tieneHijos;
	private boolean mostrarHijos;
	private String estado;

	public ProductoTipo(String id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public boolean isTieneHijos() {
		return tieneHijos;
	}

	public void setTieneHijos(boolean tieneHijos) {
		this.tieneHijos = tieneHijos;
	}

	public List getHijos() {
		return hijos;
	}

	public void setHijos(List hijos) {
		this.hijos = hijos;
	}

	public ProductoTipo() {
	}

	public ProductoTipo(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ProductoTipo getPadre() {
		return padre;
	}

	public void setPadre(ProductoTipo padre) {
		this.padre = padre;
	}

	/**
	 * @return Returns the nombrePop.
	 */
	public String getNombrePop() {
		return nombrePop;
	}

	/**
	 * @param nombrePop
	 *            The nombrePop to set.
	 */
	public void setNombrePop(String nombrePop) {
		this.nombrePop = nombrePop;
	}

	public boolean isMostrarHijos() {
		return mostrarHijos;
	}

	public void setMostrarHijos(boolean mostrarHijos) {
		this.mostrarHijos = mostrarHijos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
