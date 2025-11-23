package co.edu.unal.hermes.modelo;

public class ProyectoEditorialTitulo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Proyecto proyecto;
	private String titulo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}