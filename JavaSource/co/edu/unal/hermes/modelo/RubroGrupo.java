package co.edu.unal.hermes.modelo;

public class RubroGrupo {

	private String nombre;
	private Integer valor;
	private String descripcion;

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
		
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}
	
}
