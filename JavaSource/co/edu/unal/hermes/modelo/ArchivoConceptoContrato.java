package co.edu.unal.hermes.modelo;

import java.util.Date;

public class ArchivoConceptoContrato implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private ConceptoContratoBiodiversidad concepto;
	private String nombre;
	private Date fechaCarga;
	private Date fechaElimina;
	private Persona personaCarga;
	private Persona personaElimina;
	private String estado;

	
	/** default constructor */
	public ArchivoConceptoContrato() {
		
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

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public Date getFechaElimina() {
		return fechaElimina;
	}

	public void setFechaElimina(Date fechaElimina) {
		this.fechaElimina = fechaElimina;
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

	public ConceptoContratoBiodiversidad getConcepto() {
		return concepto;
	}

	public void setConcepto(ConceptoContratoBiodiversidad concepto) {
		this.concepto = concepto;
	}
}