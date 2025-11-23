/**
 * @author Martha Liliana Correa O.
 * @date 09/11/2015
 */

package co.edu.unal.hermes.modelo;

import java.util.Date;

public class Instructivo implements java.io.Serializable {
	
	/**
	 * 
	 */
	public static final String COMPONENTE_LINEAMIENTOS_PROTOCOLOS = "DP";
	public static final String COMPONENTE_INVESTIGACION = "I";
	public static final String COMPONENTE_EXTENSION = "E";
	public static final String COMPONENTE_LABORATORIOS = "L";
	public static final String COMPONENTE_OTROS = "O";
	public static final String COMPONENTE_PROPIEDAD = "PI";
	public static final String COMPONENTE_COLECCIONES = "CB";
	public static final String COMPONENTE_EDITORIAL = "ED";
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nombre;
	private String descripcion;
	private String componente;
	private InstructivoClasificacion clasificacion;
	private Rol rol;
	private Persona personaCarga;
	private Persona personaElimina;
	private String estado;
	private Date fecha;
	
	/**
	 * Variables no mapeadas en la base de datos
	 * Se usan para la selección del archivo a descargar
	 */
	private Long idArchivo;
	private String extensionArchivo;

	
	/** default constructor */
	public Instructivo() {
		
	}
	
	public Instructivo(Long id) {
		this.id = id;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


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

	public InstructivoClasificacion getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(InstructivoClasificacion clasificacion) {
		this.clasificacion = clasificacion;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Persona getPersonaCarga() {
		return personaCarga;
	}

	public void setPersonaCarga(Persona personaCarga) {
		this.personaCarga = personaCarga;
	}

	public Persona getPersonaElimina() {
		return personaElimina;
	}

	public void setPersonaElimina(Persona personaElimina) {
		this.personaElimina = personaElimina;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getComponente() {
		return componente;
	}

	public void setComponente(String componente) {
		this.componente = componente;
	}

	public Long getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(Long idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getExtensionArchivo() {
		return extensionArchivo;
	}

	public void setExtensionArchivo(String extensionArchivo) {
		this.extensionArchivo = extensionArchivo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}