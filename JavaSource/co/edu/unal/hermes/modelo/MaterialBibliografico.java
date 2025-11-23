package co.edu.unal.hermes.modelo;

import java.util.Date;

public class MaterialBibliografico {
	
	private Long id;
	private String titulo;
	private String autor;
	private String codigo;
	private String edicion;
	private String ano;
	int anoEntero;
	private String editorial;
	private String cantidad;
	int cantidadEntero;
	private String proyecto;
	private String aval;
	private String avalDescripcion;	
	private Persona curricular;
	private Persona profesor;
	private Date fechaRegistro;
	
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEdicion() {
		return edicion;
	}
	public void setEdicion(String edicion) {
		this.edicion = edicion;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getEditorial() {
		return editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	public String getProyecto() {
		return proyecto;
	}
	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}
	public Persona getCurricular() {
		return curricular;
	}
	public void setCurricular(Persona curricular) {
		this.curricular = curricular;
	}
	public Persona getProfesor() {
		return profesor;
	}
	public void setProfesor(Persona profesor) {
		this.profesor = profesor;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public int getAnoEntero() {
		return anoEntero;
	}
	public void setAnoEntero(int anoEntero) {
		this.anoEntero = anoEntero;
	}
	public int getCantidadEntero() {
		return cantidadEntero;
	}
	public void setCantidadEntero(int cantidadEntero) {
		this.cantidadEntero = cantidadEntero;
	}
	public String getAval() {
		return aval;
	}
	public void setAval(String aval) {
		this.aval = aval;
	}
	public String getAvalDescripcion() {
		return avalDescripcion;
	}
	public void setAvalDescripcion(String avalDescripcion) {
		this.avalDescripcion = avalDescripcion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	
}
