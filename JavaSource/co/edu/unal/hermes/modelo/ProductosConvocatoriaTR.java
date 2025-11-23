package co.edu.unal.hermes.modelo;

public class ProductosConvocatoriaTR {
	
	private Long id;
	private String otroProducto;
	private Long orden;
	private ConvocatoriaTerminosReferencia  convocatoriaTR;
	private ProductoTipo producto;
	private Long cantidad;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOtroProducto() {
		return otroProducto;
	}
	public void setOtroProducto(String otroProducto) {
		this.otroProducto = otroProducto;
	}
	public Long getOrden() {
		return orden;
	}
	public void setOrden(Long orden) {
		this.orden = orden;
	}
	public ConvocatoriaTerminosReferencia getConvocatoriaTR() {
		return convocatoriaTR;
	}
	public void setConvocatoriaTR(ConvocatoriaTerminosReferencia convocatoriaTR) {
		this.convocatoriaTR = convocatoriaTR;
	}
	public ProductoTipo getProducto() {
		return producto;
	}
	public void setProducto(ProductoTipo producto) {
		this.producto = producto;
	}  
	
	public ProductosConvocatoriaTR() {
	    super();
	    producto = new ProductoTipo();
    }
	
	public Long getCantidad() {
		return cantidad;
	}
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	
	@Override
	public boolean equals(Object object) {
        if (object instanceof ProductosConvocatoriaTR) {
        	ProductosConvocatoriaTR producto = (ProductosConvocatoriaTR) object;
        	if(this.getProducto().getId().equals(producto.getProducto().getId()) && !this.producto.getId().equals("74")){
        		return true;
        	}else{
        		return false;
        	}
        } else {
            return false;
        }
    }


}
