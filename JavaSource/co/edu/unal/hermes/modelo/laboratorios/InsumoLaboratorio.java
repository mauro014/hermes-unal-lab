package co.edu.unal.hermes.modelo.laboratorios;

import java.util.HashSet;
import java.util.Set;

import co.edu.unal.hermes.modelo.Archivo;
import co.edu.unal.hermes.modelo.Tipos;

/**
 * Objeto que representa a un Insumo de Laboratorio
 * 
 * @author dgbenitezc
 */

public class InsumoLaboratorio {

	private Long id;
	private String cas;
	private String nombre;
	private Tipos unidadDeMedida;
	private Boolean controlado;
	private Boolean activo;
	private Set<Archivo> archivos = new HashSet<Archivo>();
	private Integer archivosFC;
	
	public Integer getArchivosFC() {
		archivosFC = archivos.size();
		return archivosFC;
	}

	public void setArchivosFC(Integer archivosFC) {
		this.archivosFC = archivosFC;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the unidadDeMedida
	 */
	public Tipos getUnidadDeMedida() {
		return unidadDeMedida;
	}

	/**
	 * @param unidadDeMedida
	 *            the unidadDeMedida to set
	 */
	public void setUnidadDeMedida(Tipos unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}

	/**
	 * @return the controlado
	 */
	public Boolean getControlado() {
		return controlado;
	}

	/**
	 * @param controlado
	 *            the controlado to set
	 */
	public void setControlado(Boolean controlado) {
		this.controlado = controlado;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getCas() {
		return cas;
	}

	public void setCas(String cas) {
		this.cas = cas;
	}

	public Set<Archivo> getArchivos() {
		return archivos;
	}

	public void setArchivos(Set<Archivo> archivos) {
		this.archivos = archivos;
	}
	
	
}
