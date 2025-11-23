/*
 * Created on 02-sep-2005
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Juan Pablo Duque G.
 */
public class Persona implements Serializable {

	private static final long serialVersionUID = -6429970204105522556L;
	public static String GENERO_MASCULINO = "M";
	public static String GENERO_FEMENINO = "F";

	private IdPersona id;

	// Este atributo se trae del ldap pero no se inserta en la base de datos
	private String uid;

	private EstadoCivil estadoCivil;
	private String nombre1;
	private String nombre2;
	private String apellido1;
	private String apellido2;
	private String nombre11;
	private String nombre22;
	private String apellido11;
	private String apellido22;

	private String genero;
	private String email;
	private String direccion;
	private String telefono;
	private String telefonoHojaVida;
	private Date fechaNacimiento;
	private String nacionalidad;
	private String paisOrigen;
	private String coorIDDependencia;
	private String coorNombreDependencia;

	private String celular;
	private String profesion;
	private String resumenHojaDeVida;

	private String edad;
	private Date fechaTituloPregrado;
	private String promedioPregrado;
	private String facultadPregrado;

	/**
	 * Ciudad donde está radicado viviendo el investigador.
	 */
	private Ciudad ciudadDomicilio;
	private Ciudad ciudadNacimiento;
	private Ciudad ciudadExpedicion;
	
	private String esActivoSara;
	/**
	 * Roles asociados con la persona
	 */
	private Set<Rol> roles = new HashSet<Rol>();
	
	private Set<PersonaRol> personaRoles = new HashSet<PersonaRol>();

	/**
	 * Proyectos del asesor a los cuales les hace seguimiento
	 */
	private Set<Proyecto> proyectosAsesor = new HashSet<Proyecto>();

	public Persona() {
		id = new IdPersona(); // JASSAR FEB 15 DE 2006 4:00 pm instanciación
		// necesaria para JSFS
	}

	/**
	 * Constructor que recibe como parametro el id, para inicializar el id del
	 * objeto.
	 * 
	 * @param LongpIdTipoDato
	 */
	public Persona(String pDocumento, String pIdTipoDocumento) {
		this.id = new IdPersona(pDocumento, pIdTipoDocumento);
	}

	public Persona(IdPersona pIdPersona) {
		this.id = pIdPersona;
	}

	public IdPersona getId() {
		return id;
	}

	public void setId(IdPersona id) {
		this.id = id;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		if (apellido1 == null) {
			this.apellido1 = "";

		} else {
			this.apellido1 = apellido1.toUpperCase().trim();
		}
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		if (apellido2 == null) {
			this.apellido2 = "";

		} else {
			this.apellido2 = apellido2.toUpperCase().trim();
		}
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

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		if (nombre1 == null) {
			this.nombre1 = "";

		} else {
			this.nombre1 = nombre1.toUpperCase().trim();
		}
	}

	public String getNombre2() {
		return nombre2;
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

	public void setNombre2(String nombre2) {
		if (nombre2 == null) {
			this.nombre2 = "";

		} else {
			this.nombre2 = nombre2.toUpperCase().trim();
		}
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	public Set<Proyecto> getProyectosAsesor() {
		return proyectosAsesor;
	}

	public void setProyectosAsesor(Set<Proyecto> proyectosAsesor) {
		this.proyectosAsesor = proyectosAsesor;
	}

	public void adicionarProyectoAsesor(Proyecto proyecto) {
		proyectosAsesor.add(proyecto);
	}

	public void eliminarProyectoAsesor(Proyecto proyecto) {
		proyectosAsesor.remove(proyecto);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Persona) {
			Persona persona = (Persona) obj;
			return persona.getId().equals(id);
		}
		return false;
	}

	public void adicionarRol(Rol rol) {
		roles.add(rol);
	}

	public void borrarRol(Rol r) {
		roles.remove(r);
	}

	public void adicionarRolAdministrador() {
		if (!esAdministradorConvocatorias()) {
			Rol rolM = new Rol();
			rolM.setId(Rol.ADMINISTRADOR_CONVOCATORIAS);
			adicionarRol(rolM);
		}
	}

	public void adicionarRolAsesor() {
		if (!esAsesor()) {
			Rol rolA = new Rol();
			rolA.setId(Rol.ASESOR);
			adicionarRol(rolA);
		}
	}

	public void adicionarRolInvestigador() {
		if (!esInvestigador()) {
			Rol rolI = new Rol();
			rolI.setId(Rol.INVESTIGADOR);
			adicionarRol(rolI);
		}
	}

	public void adicionarRolCoordinador() {
		if (!esCoordinador()) {
			Rol rolC = new Rol();
			rolC.setId(Rol.COORDINADOR);
			adicionarRol(rolC);
		}
	}

	public void adicionarRolEvaluador() {
		if (!esEvaluador()) {
			Rol rolE = new Rol();
			rolE.setId(Rol.EVALUADOR);
			adicionarRol(rolE);
		}
	}

	public void adicionarRolBecario() {
		if (!esEvaluador()) {
			Rol rolE = new Rol();
			rolE.setId(Rol.BECARIO);
			adicionarRol(rolE);
		}
	}

	public boolean esCoordinador() {
		Rol rolC = new Rol();
		rolC.setId(Rol.COORDINADOR);
		return tieneRol(rolC);
	}

	public boolean esInvestigador() {
		Rol rolI = new Rol();
		rolI.setId(Rol.INVESTIGADOR);
		return tieneRol(rolI);
	}

	public boolean esAsesor() {
		Rol rolA = new Rol();
		rolA.setId(Rol.ASESOR);
		return tieneRol(rolA);
	}

	public boolean esEvaluador() {
		Rol rolE = new Rol();
		rolE.setId(Rol.EVALUADOR);
		return tieneRol(rolE);
	}

	public boolean esAdministradorConvocatorias() {
		Rol rolA = new Rol();
		rolA.setId(Rol.ADMINISTRADOR_CONVOCATORIAS);
		return tieneRol(rolA);
	}

	public boolean esBecario() {
		Rol rolA = new Rol();
		rolA.setId(Rol.BECARIO);
		return tieneRol(rolA);
	}

	public boolean tieneRol(Rol rol) {
		Iterator<Rol> it = roles.iterator();
		while (it.hasNext()) {
			Rol r = it.next();
			if (r.equals(rol)) {
				return true;
			}
		}
		return false;
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

	public void adicionarRolAux(Rol rol) {
		if (roles.isEmpty())
			roles.add(rol);
	}

	public String getCoorIDDependencia() {
		return coorIDDependencia;
	}

	public void setCoorIDDependencia(String coorIDDependencia) {
		this.coorIDDependencia = coorIDDependencia;
	}

	public String getCoorNombreDependencia() {
		return coorNombreDependencia;
	}

	public void setCoorNombreDependencia(String coorNombreDependencia) {
		this.coorNombreDependencia = coorNombreDependencia;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getResumenHojaDeVida() {
		return resumenHojaDeVida;
	}

	public void setResumenHojaDeVida(String resumenHojaDeVida) {
		this.resumenHojaDeVida = resumenHojaDeVida;
	}

	public String getNombre11() {
		if (nombre1 != null && nombre1.length() > 1) {
			return nombre1.substring(0, 1).toUpperCase() + nombre1.toLowerCase().substring(1, nombre1.length());
		}
		return nombre1;
	}

	public void setNombre11(String nombre11) {
		this.nombre11 = nombre11;
	}

	public String getNombre22() {
		if (nombre2 != null && nombre2.length() > 1) {
			return nombre2.substring(0, 1).toUpperCase() + nombre2.toLowerCase().substring(1, nombre2.length());
		}
		return nombre2;
	}

	public void setNombre22(String nombre22) {
		this.nombre22 = nombre22;
	}

	public String getApellido11() {
		if (apellido1 != null && apellido1.length() > 1) {
			return apellido1.substring(0, 1).toUpperCase() + apellido1.toLowerCase().substring(1, apellido1.length());
		}
		return apellido1;
	}

	public void setApellido11(String apellido11) {
		this.apellido11 = apellido11;
	}

	public String getApellido22() {
		if (apellido2 != null && apellido2.length() >= 1) {
			return apellido2.substring(0, 1).toUpperCase() + apellido2.toLowerCase().substring(1, apellido2.length());
		}
		return apellido2;
	}

	public void setApellido22(String apellido22) {
		this.apellido22 = apellido22;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public Date getFechaTituloPregrado() {
		return fechaTituloPregrado;
	}

	public void setFechaTituloPregrado(Date fechaTituloPregrado) {
		this.fechaTituloPregrado = fechaTituloPregrado;
	}

	public String getPromedioPregrado() {
		return promedioPregrado;
	}

	public void setPromedioPregrado(String promedioPregrado) {
		this.promedioPregrado = promedioPregrado;
	}

	public String getNombreCompletoMinusculas() {
		String nombreEnMinusculas = getNombre11() + (getNombre22() == null ? " " : " " + getNombre22()) + " "
				+ getApellido11() + (getApellido22() == null ? "" : " " + getApellido22());
		nombreEnMinusculas = nombreEnMinusculas.replaceAll(" null", "").replaceAll("  ", " ").trim();
		return nombreEnMinusculas;
	}

	public String getFacultadPregrado() {
		return facultadPregrado;
	}

	public void setFacultadPregrado(String facultadPregrado) {
		this.facultadPregrado = facultadPregrado;
	}

	public String getTelefonoHojaVida() {
		return telefonoHojaVida;
	}

	public void setTelefonoHojaVida(String telefonoHojaVida) {
		this.telefonoHojaVida = telefonoHojaVida;
	}

	public void validarActualizacionNombres(Persona personaNombresAntiguos) {
		if (personaNombresAntiguos.getNombre2() == null) {
			personaNombresAntiguos.setNombre2("");
		}
		if (this.nombre2 == null) {
			this.nombre2 = "";
		}
		if (!personaNombresAntiguos.getNombre2().equals("")
				&& !this.nombre2.equals(personaNombresAntiguos.getNombre2())) {
			this.nombre2 = personaNombresAntiguos.getNombre2();
		}
		if (personaNombresAntiguos.getApellido2() == null) {
			personaNombresAntiguos.setApellido2("");
		}
		if (this.apellido2 == null) {
			this.apellido2 = "";
		}
		if (!personaNombresAntiguos.getApellido2().equals("")
				&& !this.apellido2.equals(personaNombresAntiguos.getApellido2())) {
			this.apellido2 = personaNombresAntiguos.getApellido2();
		}
	}

	public boolean validarFechaAnios(Date fecha, int anios) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(GregorianCalendar.YEAR, -3);
		return !fecha.before(calendar.getTime());
	}

	public String getEsActivoSara() {
		return esActivoSara;
	}

	public void setEsActivoSara(String esActivoSara) {
		this.esActivoSara = esActivoSara;
	}

	public Set<PersonaRol> getPersonaRoles() {
	    return personaRoles;
	}

	public void setPersonaRoles(Set<PersonaRol> personaRoles) {
	    this.personaRoles = personaRoles;
	}

}
