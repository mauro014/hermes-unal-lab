package co.edu.unal.hermes.modelo;

public class Icono {
String nombre;
String accion;
Long categoria; // Indica la seccion en la que se debe ubicar el ícono

public static final Long SECCION_OTROS = 0L;
public static final Long SECCION_REGISTRE = 1L;
public static final Long SECCION_SOLICITE = 2L;
public static final Long SECCION_DILIGENCIE = 3L;
public static final Long SECCION_CONSULTE = 4L;
public static final Long SECCION_GESTIONE_INVESTIGACION = 5L;
public static final Long SECCION_GESTIONE_EXTENSION = 6L;
public static final Long SECCION_GESTIONE_INVESTIGACION_EXTENSION = 7L;
public static final Long SECCION_GESTIONE_EDITORIAL = 8L;

public Icono(String nombre, String accion, Long categoria) {
	super();
	this.nombre = nombre;
	this.accion = accion;
	this.categoria = categoria;
}

public Icono(String nombre, String accion) {
	super();
	this.nombre = nombre;
	this.accion = accion;
}

public Long getCategoria() {
	return categoria;
}

public void setCategoria(Long categoria) {
	this.categoria = categoria;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public String getAccion() {
	return accion;
}

public void setAccion(String accion) {
	this.accion = accion;
}

public String accion() {
	return accion;
}


}
