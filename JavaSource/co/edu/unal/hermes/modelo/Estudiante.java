/*
 * Created on 14-sep-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author jpduqueg
 */
public class Estudiante implements Serializable {

	// Persona
	private static final long serialVersionUID = 4079388711543108493L;

	private IdPersona id;

	private EstadoCivil estadoCivil;

	private String nombre1;
	private String nombre2;
	private String apellido1;
	private String apellido2;
	private String genero;
	private String email;
	private String direccion;
	private String telefono;
	private String semestreActual;
	private String nombreCarrera;
	private PlanEstudios plan;

	private Date fechaNacimiento;

	private Ciudad ciudadDomicilio;
	private Ciudad ciudadNacimiento;
	private Ciudad ciudadExpedicion;
	// Investigador
	private CategoriaInvestigador categoriaInvestigador;
	private String interno = "N";
	private String evaluador = "N";
	private Dependencia dependencia;

	private Float papa;

	public Boolean esInterno() {
		return interno.equals("S");
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public CategoriaInvestigador getCategoriaInvestigador() {
		return categoriaInvestigador;
	}

	public void setCategoriaInvestigador(
			CategoriaInvestigador categoriaInvestigador) {
		this.categoriaInvestigador = categoriaInvestigador;
	}

	public Ciudad getCiudadDomicilio() {
		return ciudadDomicilio;
	}

	public void setCiudadDomicilio(Ciudad ciudadDomicilio) {
		this.ciudadDomicilio = ciudadDomicilio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public IdPersona getId() {
		return id;
	}

	public void setId(IdPersona id) {
		this.id = id;
	}

	public String getInterno() {
		return interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}

	public String getNombre2() {
		return nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNombreCarrera() {
		return nombreCarrera;
	}

	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}

	public String getSemestreActual() {
		return semestreActual;
	}

	public void setSemestreActual(String semestreActual) {
		this.semestreActual = semestreActual;
	}

	public InvestigadorInterno convertirAInvestigador() {
		InvestigadorInterno investigador = new InvestigadorInterno();
		IdPersona idInvestigador = new IdPersona();
		idInvestigador.setDocumento(id.getDocumento());
		idInvestigador.setTipoDocumento(id.getTipoDocumento());
		investigador.setId(idInvestigador);
		investigador.setApellido1(apellido1);
		investigador.setApellido2(apellido2);
		investigador.setCategoriaInvestigador(categoriaInvestigador);
		investigador.setCiudadDomicilio(ciudadDomicilio);
		investigador.setDireccion(direccion);
		investigador.setEmail(email);
		investigador.setEstadoCivil(estadoCivil);
		investigador.getEvaluador();
		investigador.setDependencia(dependencia);
		investigador.setFechaNacimiento(fechaNacimiento);
		investigador.setGenero(genero);
		investigador.setInterno("N");
		investigador.setEvaluador(evaluador);
		investigador.setEsFuncionario("N");
		investigador.setNombre1(nombre1);
		investigador.setNombre2(nombre2);
		investigador.setTelefono(telefono);

		return investigador;
	}

	/**
	 * @return una nueva Persona desde un Estudiante
	 */
	public Persona convertirAPersona() {
		Persona persona = new Persona();
		try {
			BeanUtils.copyProperties(persona, this);
			if (!email.contains("@")) {
				persona.setEmail(email + "@unal.edu.co");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return persona;
	}

	public Ciudad getCiudadExpedicion() {
		return ciudadExpedicion;
	}

	public void setCiudadExpedicion(Ciudad ciudadExpedicion) {
		this.ciudadExpedicion = ciudadExpedicion;
	}

	public Ciudad getCiudadNacimiento() {
		return ciudadNacimiento;
	}

	public void setCiudadNacimiento(Ciudad ciudadNacimiento) {
		this.ciudadNacimiento = ciudadNacimiento;
	}

	public Float getPapa() {
		return papa;
	}

	public void setPapa(Float papa) {
		this.papa = papa;
	}

	public Dependencia getDependencia() {
		return dependencia;
	}

	public void setDependencia(Dependencia dependencia) {
		this.dependencia = dependencia;
	}

	public PlanEstudios getPlan() {
		return plan;
	}

	public void setPlan(PlanEstudios plan) {
		this.plan = plan;
	}

	public String getNombreCompleto() {
		String nombreCompleto = "";
		if (nombre1 != null && !nombre1.equals("")) {
			nombreCompleto += nombre1;
			nombreCompleto += " ";
		}
		if (nombre2 != null && !nombre2.equals("")) {
			nombreCompleto += nombre2;
			nombreCompleto += " ";
		}
		if (apellido1 != null && !apellido1.equals("")) {
			nombreCompleto += apellido1;
			nombreCompleto += " ";
		}
		if (apellido2 != null && !apellido2.equals("")) {
			nombreCompleto += apellido2;
		}
		return nombreCompleto.trim();
	}

}
