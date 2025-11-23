package co.edu.unal.hermes.modelo;

public class materialSesion {
    private Long id;
    private SesionCurso sesion;
    private String descripcion;
    private Long cantidad;
    private Long valor;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public SesionCurso getSesion() {
	return sesion;
    }

    public void setSesion(SesionCurso sesion) {
	this.sesion = sesion;
    }

    public String getDescripcion() {
	return descripcion;
    }

    public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
    }

    public Long getCantidad() {
	return cantidad;
    }

    public void setCantidad(Long cantidad) {
	this.cantidad = cantidad;
    }

    public Long getValor() {
	return valor;
    }

    public void setValor(Long valor) {
	this.valor = valor;
    }

    public Object clone() throws CloneNotSupportedException {
	return super.clone();
    }

    public boolean equals(Object o) {
	if (!(o instanceof materialSesion)) {
	    return false;
	}
	materialSesion materialSesion = (materialSesion) o;
	if (materialSesion == null || materialSesion.getId() == null || this.getId() == null) {
	    return false;
	}
	return materialSesion.getId().equals(this.getId());
    }

}