package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Especifíca la información de las dependencias de la Universidad Nacional que
 * están asociadas a proyectos
 */
public class Dependencia implements Comparable<Dependencia>, Serializable {

	private static final long serialVersionUID = -5189384069430015025L;
	public static final String NIVEL_NACIONAL = "1";
	public static final String TIPO_DEPENDENCIA_FACULTAD = "facultad";
	public static final String TIPO_DEPENDENCIA_SEDE = "sede";
	public static final String TIPO_DEPENDENCIA_DEPARTAMENTO = "departamento";

	public static final Long ID_VICERRECTORIA = 1096L;
	public static final Long ID_EDITORIAL_NIVEL_NACIONAL = 1060L;

	// Dependencias Sistema Nacional de Laboratorios
	public static final String ID_DNIL = "1041";
	public static final String ID_DIR_LABS_MEDELLIN = "3376";
	public static final String ID_DIR_LABS_BOGOTA = "2715";
	public static final String ID_DIR_INV_PALMIRA = "5056";
	
	

	private String id;

	/**
	 * Son las sedes de la Universidad Nacional. Dentro de estas encontramos:
	 * Nivel nacional, Bogotá, Medellín, Manizales, Palmira, Leticia, Arauca,
	 * San Andrés.
	 */
	private Sede sede;
	private String nombre;
	private String estado;
	private String telefono;
	private String extension;
	private String abreviatura;
	private String email;
	private String codigoDivisionInvestigacion;
	private Dependencia padre;

	private Boolean esFacultad;
	private Boolean esDepartamento;
	private Boolean esSede;
	private Dependencia facultad;
	private String departamento;
	private String paginaWeb;
	private String nombrePop;
	private Set grupos = new HashSet();
	private Set avales = new HashSet();
	private Boolean esInstitutoInterfacultad;

	public Set getGrupos() {
		return grupos;
	}

	public void setGrupos(Set grupos) {
		this.grupos = grupos;
	}

	public String getNombrePop() {
		return nombrePop;
	}

	public void setNombrePop(String nombrePop) {
		this.nombrePop = nombrePop;
	}

	public Dependencia() {
	}

	public Dependencia(String id) {
		this.id = id;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Dependencia getPadre() {
		return padre;
	}

	public void setPadre(Dependencia padre) {
		this.padre = padre;
	}

	public Boolean getEsFacultad() {
		return esFacultad;
	}

	public void setEsFacultad(Boolean esFacultad) {
		this.esFacultad = esFacultad;
	}

	public Boolean getEsSede() {
		return esSede;
	}

	public void setEsSede(Boolean esSede) {
		this.esSede = esSede;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public Dependencia getFacultad() {
		return facultad;
	}

	public void setFacultad(Dependencia facultad) {
		this.facultad = facultad;
	}

	public Boolean getEsDepartamento() {
		return esDepartamento;
	}

	public void setEsDepartamento(Boolean esDepartamento) {
		this.esDepartamento = esDepartamento;
	}

	public String getPaginaWeb() {
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	public String getCodigoDivisionInvestigacion() {
		return codigoDivisionInvestigacion;
	}

	public void setCodigoDivisionInvestigacion(
			String codigoDivisionInvestigacion) {
		this.codigoDivisionInvestigacion = codigoDivisionInvestigacion;
	}

	public int compareTo(Dependencia d) {
		if (d != null) {
			String s = d.getNombre();
			int i = s.compareTo(nombre);
			if (i < 0) {
				return +1;
			} else if (i > 0) {
				return -1;
			}
		}
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Dependencia)) {
			return false;
		}
		Dependencia d = (Dependencia) o;
		if (d.getId() == null || this.getId() == null)
			return false;

		return d.getId().equals(this.getId());

	}

	public Set getAvales() {
		return avales;
	}

	public void setAvales(Set avales) {
		this.avales = avales;
	}
	
	public boolean isEsEditorial(){
	    if(this.id!=null && this.id.equals(ID_EDITORIAL_NIVEL_NACIONAL.toString())){
	        return true;
	    }
	    return false;
	}

	public Boolean getEsInstitutoInterfacultad() {
		return esInstitutoInterfacultad;
	}

	public void setEsInstitutoInterfacultad(Boolean esInstitutoInterfacultad) {
		this.esInstitutoInterfacultad = esInstitutoInterfacultad;
	}

}
