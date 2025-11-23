package co.edu.unal.hermes.modelo;

import java.io.Serializable;


public class ColeccionCatalogacion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Atributos de la clase ColeccionCatalogacion, correspondientes a la tabla HER_COLECCION_
	
	private Long id;
	
	private Coleccion coleccion ;
	private DominioDetalle grupoBiologico;
	private DominioDetalle subGrupoBiologico;
	private Long numeroEjemplares;
	private Long numeroEjemplaresNoBiologico;
	private Float ejemplaresCatalogados;
	private Float ejemplaresSistematizados;
	private Float ejemplaresIdentificadosFilum;
	private Float ejemplaresIdentificadosOrden;
	private Float ejemplaresIdentificadosFamilia;
	private Float ejemplaresIdentificadosGenero;
	private Float ejemplaresIdentificadosEspecie;
	private String grupoNoBiologico;
	private String subGrupoNoBiologico;
	private String unidadMedidaNoBiologico;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Coleccion getColeccion() {
		return coleccion;
	}
	public void setColeccion(Coleccion coleccion) {
		this.coleccion = coleccion;
	}
	public DominioDetalle getGrupoBiologico() {
		return grupoBiologico;
	}
	public void setGrupoBiologico(DominioDetalle grupoBiologico) {
		this.grupoBiologico = grupoBiologico;
	}
	public DominioDetalle getSubGrupoBiologico() {
		return subGrupoBiologico;
	}
	public void setSubGrupoBiologico(DominioDetalle subGrupoBiologico) {
		this.subGrupoBiologico = subGrupoBiologico;
	}
	public Long getNumeroEjemplares() {
		return numeroEjemplares;
	}
	public void setNumeroEjemplares(Long numeroEjemplares) {
		this.numeroEjemplares = numeroEjemplares;
	}
	public Float getEjemplaresCatalogados() {
		return ejemplaresCatalogados;
	}
	public void setEjemplaresCatalogados(Float ejemplaresCatalogados) {
		this.ejemplaresCatalogados = ejemplaresCatalogados;
	}
	public Float getEjemplaresSistematizados() {
		return ejemplaresSistematizados;
	}
	public void setEjemplaresSistematizados(Float ejemplaresSistematizados) {
		this.ejemplaresSistematizados = ejemplaresSistematizados;
	}
	public Float getEjemplaresIdentificadosOrden() {
		return ejemplaresIdentificadosOrden;
	}
	public void setEjemplaresIdentificadosOrden(Float ejemplaresIdentificadosOrden) {
		this.ejemplaresIdentificadosOrden = ejemplaresIdentificadosOrden;
	}
	public Float getEjemplaresIdentificadosFamilia() {
		return ejemplaresIdentificadosFamilia;
	}
	public void setEjemplaresIdentificadosFamilia(
			Float ejemplaresIdentificadosFamilia) {
		this.ejemplaresIdentificadosFamilia = ejemplaresIdentificadosFamilia;
	}
	public Float getEjemplaresIdentificadosGenero() {
		return ejemplaresIdentificadosGenero;
	}
	public void setEjemplaresIdentificadosGenero(Float ejemplaresIdentificadosGenero) {
		this.ejemplaresIdentificadosGenero = ejemplaresIdentificadosGenero;
	}
	public Float getEjemplaresIdentificadosEspecie() {
		return ejemplaresIdentificadosEspecie;
	}
	public void setEjemplaresIdentificadosEspecie(
			Float ejemplaresIdentificadosEspecie) {
		this.ejemplaresIdentificadosEspecie = ejemplaresIdentificadosEspecie;
	}
	public Float getEjemplaresIdentificadosFilum() {
		return ejemplaresIdentificadosFilum;
	}
	public void setEjemplaresIdentificadosFilum(
			Float ejemplaresIdentificadosFilum) {
		this.ejemplaresIdentificadosFilum = ejemplaresIdentificadosFilum;
	}
	public String getGrupoNoBiologico() {
		return grupoNoBiologico;
	}
	public void setGrupoNoBiologico(String grupoNoBiologico) {
		this.grupoNoBiologico = grupoNoBiologico;
	}
	public String getSubGrupoNoBiologico() {
		return subGrupoNoBiologico;
	}
	public void setSubGrupoNoBiologico(String subGrupoNoBiologico) {
		this.subGrupoNoBiologico = subGrupoNoBiologico;
	}
	public String getUnidadMedidaNoBiologico() {
		return unidadMedidaNoBiologico;
	}
	public void setUnidadMedidaNoBiologico(String unidadMedidaNoBiologico) {
		this.unidadMedidaNoBiologico = unidadMedidaNoBiologico;
	}
	public Long getNumeroEjemplaresNoBiologico() {
		return numeroEjemplaresNoBiologico;
	}
	public void setNumeroEjemplaresNoBiologico(Long numeroEjemplaresNoBiologico) {
		this.numeroEjemplaresNoBiologico = numeroEjemplaresNoBiologico;
	}

}
