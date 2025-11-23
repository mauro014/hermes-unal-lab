package co.edu.unal.hermes.modelo;

import java.util.Date;


public class HistoricoBusqueda implements java.io.Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String categoria;
	private String palabra;
	private Date fechaBusqueda;
	private String nombreCategoria;
	
	/** default constructor */
	public HistoricoBusqueda() {
	}

	/** minimal constructor */
	public HistoricoBusqueda(Long id) {
		this.setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPalabra() {
		return palabra;
	}

	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}

	public Date getFechaBusqueda() {
		return fechaBusqueda;
	}

	public void setFechaBusqueda(Date fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	
	
}