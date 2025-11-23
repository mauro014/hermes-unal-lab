package co.edu.unal.hermes.modelo;

public class MovilidadEstudiantesProducto {

	private Long id;
	private MovilidadEstudiantesPosgrado movilidad;
	private ProductoTipo producto;
	private String descripcion;

	public MovilidadEstudiantesProducto() {

	}

	public MovilidadEstudiantesProducto(Long pId) {
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

	public MovilidadEstudiantesPosgrado getMovilidad() {
		return movilidad;
	}

	public void setMovilidad(MovilidadEstudiantesPosgrado movilidad) {
		this.movilidad = movilidad;
	}

}
