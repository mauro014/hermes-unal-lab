

package co.edu.unal.hermes.modelo;


import java.util.Date;



public class ArchivoGestionColeccion{
		
	private Long id;
	private String nombre;
	private Date fecha;
	private ColeccionGestion gestion;
	private String tipoUsuario;
	private String estado;
	private Persona responsable;
	private Date fechaBorrado;

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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ColeccionGestion getGestion() {
		return gestion;
	}

	public void setGestion(ColeccionGestion gestion) {
		this.gestion = gestion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Persona getResponsable() {
		return responsable;
	}

	public void setResponsable(Persona responsable) {
		this.responsable = responsable;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Date getFechaBorrado() {
		return fechaBorrado;
	}

	public void setFechaBorrado(Date fechaBorrado) {
		this.fechaBorrado = fechaBorrado;
	}

	
	
}
