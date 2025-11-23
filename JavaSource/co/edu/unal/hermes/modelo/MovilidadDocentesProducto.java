package co.edu.unal.hermes.modelo;

public class MovilidadDocentesProducto {

	private Long id;
	private MovilidadDocentesExterior movilidad;
	private ProductoTipo producto;
	private String descripcion;

	public MovilidadDocentesProducto() {

	}

	public MovilidadDocentesProducto(Long pId) {
		this.id = pId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductoTipo getProducto() {
		return producto;
	}

	public void setProducto(ProductoTipo producto) {
		this.producto = producto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public MovilidadDocentesExterior getMovilidad() {
		return movilidad;
	}

	public void setMovilidad(MovilidadDocentesExterior movilidad) {
		this.movilidad = movilidad;
	}

}
