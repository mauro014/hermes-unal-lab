package co.edu.unal.hermes.modelo;

/**
 * Especifíca la información de la jornada docente como tipo de modalidad para
 * la inscripcion de proyectos
 */
public class Registro extends Modalidad {

	private static final long serialVersionUID = -2928244355311882721L;

	private String nombre;
	private int semestre;
	private int año;
	private String descripcion;
	private EstadoConvocatoria estadoRegistro;

	public int getAño() {
		return año;
	}

	public void setAño(int año) {
		this.año = año;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public EstadoConvocatoria getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoRegistro(EstadoConvocatoria estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}
}