package co.edu.unal.hermes.modelo;

public class ActividadEvento {

	private String descripcion = "";
	private  long valor = 0;
	private  String nombre;
	private String tipoContrato;
	private Long cantidad;
	private Long valorUnitario;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public long getValor() {
		return valor;
	}
	public void setValor(long valor) {
		this.valor = valor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipoContrato() {
		return tipoContrato;
	}
	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}
	public Long getCantidad() {
		return cantidad;
	}
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	public Long getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(Long valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
		
		
}
