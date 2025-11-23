package co.edu.unal.hermes.modelo;

import java.util.List;


public class TipoCompromiso{
	
	public static final String RAIZ = "0";
	
	private Long id;
	private String nombre;
	private String descripcion;
	private TipoCompromiso padre;
	private List hijos;
	private boolean tieneHijos;
	private boolean mostrarHijos;
	
	public List getHijos() {
		return hijos;
	}
	public void setHijos(List hijos) {
		this.hijos = hijos;
	}
	public boolean isTieneHijos() {
		return tieneHijos;
	}
	public void setTieneHijos(boolean tieneHijos) {
		this.tieneHijos = tieneHijos;
	}
	public boolean isMostrarHijos() {
		return mostrarHijos;
	}
	public void setMostrarHijos(boolean mostrarHijos) {
		this.mostrarHijos = mostrarHijos;
	}
	public TipoCompromiso(){
	}
	public TipoCompromiso(Long id){
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
	/**
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return Returns the padre.
	 */
	public TipoCompromiso getPadre() {
		return padre;
	}
	/**
	 * @param padre The padre to set.
	 */
	public void setPadre(TipoCompromiso padre) {
		this.padre = padre;
	}
}
