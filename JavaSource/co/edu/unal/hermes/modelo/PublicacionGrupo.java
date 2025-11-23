package co.edu.unal.hermes.modelo;

public class PublicacionGrupo {
	
	private String annio;
    private String tipo;
    private String cantidad;
    private String producto;
    
	public String getAnnio() {
		return annio;
	}
	
	public void setAnnio(String annio) {
		this.annio = annio;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getProducto() {
		return producto;
	}
	
}
