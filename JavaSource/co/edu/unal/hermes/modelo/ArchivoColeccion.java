

package co.edu.unal.hermes.modelo;


import java.util.Date;



public class ArchivoColeccion{
		
	private Long id;
	private String nombre;
	private Date fecha;
	private Coleccion coleccion;
	private String tipo;


	private Long aval;

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}	
	
	public Long getAval() {
		return aval;
	}

	public void setAval(Long aval) {
		this.aval = aval;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Coleccion getColeccion() {
		return coleccion;
	}

	public void setColeccion(Coleccion coleccion) {
		this.coleccion = coleccion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
	
}
