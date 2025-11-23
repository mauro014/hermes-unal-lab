package co.edu.unal.hermes.modelo;

import java.util.Set;

public class TipoPregunta {
	public static final long cualitaticacuantivativa=1;
	public static final long cualitativa=2;
	public static final long seleccionUnica=3;
	
	private Long id;
	private String nombre;
	private String minimo;
	private String maximo;
	private String pagina;
	private String seTieneParaPromedio;
	private Set opciones;
	
	public Set getOpciones() {
		return opciones;
	}
	public void setOpciones(Set opciones) {
		this.opciones = opciones;
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
	public String getMinimo() {
		return minimo;
	}
	public void setMinimo(String minimo) {
		this.minimo = minimo;
	}
	public String getMaximo() {
		return maximo;
	}
	public void setMaximo(String maximo) {
		this.maximo = maximo;
	}
	public String getPagina() {
		return pagina;
	}
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
	public String getSeTieneParaPromedio() {
		return seTieneParaPromedio;
	}
	public void setSeTieneParaPromedio(String seTieneParaPromedio) {
		this.seTieneParaPromedio = seTieneParaPromedio;
	}
}
