package co.edu.unal.hermes.modelo;

public class DescuentoProyectoECP
{
	
	private Long id;
	private Proyecto proyecto;
	private Descuento_ECP descuento;
	private Long porcentaje;
	private Long numParticipantesEsperado;
	private Long numParticipantesDisponibles;
	private String descripcion;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Proyecto getProyecto() {
		return proyecto;
	}
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	public Descuento_ECP getDescuento() {
		return descuento;
	}
	public void setDescuento(Descuento_ECP descuento) {
		this.descuento = descuento;
	}
	
	public Long getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Long porcentaje) {
		this.porcentaje = porcentaje;
	}
	public Long getNumParticipantesEsperado() {
		return numParticipantesEsperado;
	}
	public void setNumParticipantesEsperado(Long numParticipantesEsperado) {
		this.numParticipantesEsperado = numParticipantesEsperado;
	}
	public Long getNumParticipantesDisponibles() {
		return numParticipantesDisponibles;
	}
	public void setNumParticipantesDisponibles(Long numParticipantesDisponibles) {
		this.numParticipantesDisponibles = numParticipantesDisponibles;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
//	public Object clone() throws CloneNotSupportedException {
//		return super.clone();
//	    }
//
//	public boolean equals(Object o) 
//	{
//	    	
//		if (!(o instanceof DescuentoProyectoECP)) {
//		    return false;
//		}
//		DescuentoProyectoECP descuentoProyectoECP = (DescuentoProyectoECP) o;
//		if (descuentoProyectoECP == null || descuentoProyectoECP.getId() == null || this.getId() == null) {
//		    return false;
//		}
//		return descuentoProyectoECP.getId().equals(this.getId());
//	}
	
}