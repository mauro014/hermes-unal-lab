/**
 * @author Martha Liliana Correa O.
 * @date 21/07/2016
 */

package co.edu.unal.hermes.modelo;

public class PersonaPropiedadIntelectual implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Tipos de vinculacion (calidad)
	public static final Long ADMINISTRATIVO = (long) 1;
	public static final Long DOCENTE = (long) 2;
	public static final Long ESTUDIANTE = (long) 3;
	public static final Long EXTERNO = (long) 4;
	public static final Long EGRESADO = (long) 5;

	private Long id;
	private PropiedadIntelectual propiedad;
	private Investigador persona;
	private TipoPersonaPropiedadIntelectual tipoPersona;
	private String calidad;
	private Dependencia dependencia;
	private PlanEstudios plan;
	private FuenteFinanciacion entidad;
	private ObraFonogramaPropiedadIntelectual obraFijadaFonograma;
	private Pais pais;
	private String ciudad;
	private String sitioWeb;

	/** default constructor */
	public PersonaPropiedadIntelectual() {
		/*
		 * Se crea un objeto vacio de persona relacionada con la propiedad intelectual
		 */
	}

	public PersonaPropiedadIntelectual(Long id) {
		this.setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PropiedadIntelectual getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(PropiedadIntelectual propiedad) {
		this.propiedad = propiedad;
	}

	public Investigador getPersona() {
		return persona;
	}

	public void setPersona(Investigador persona) {
		this.persona = persona;
	}

	public TipoPersonaPropiedadIntelectual getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(TipoPersonaPropiedadIntelectual tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getCalidad() {
		return calidad;
	}

	public void setCalidad(String calidad) {
		this.calidad = calidad;
	}

	public boolean isEsExterno() {
		if (this.calidad != null && this.calidad.equals(EXTERNO.toString())) {
			return true;
		}
		return false;
	}

	public boolean isEsDocente() {
		if (this.calidad != null && this.calidad.equals(DOCENTE.toString())) {
			return true;
		}
		return false;
	}

	public boolean isEsAdministrativo() {
		if (this.calidad != null && this.calidad.equals(ADMINISTRATIVO.toString())) {
			return true;
		}
		return false;
	}

	public boolean isEsEstudiante() {
		if (this.calidad != null && this.calidad.equals(ESTUDIANTE.toString())) {
			return true;
		}
		return false;
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

	public FuenteFinanciacion getEntidad() {
		return entidad;
	}

	public void setEntidad(FuenteFinanciacion entidad) {
		this.entidad = entidad;
	}

	public ObraFonogramaPropiedadIntelectual getObraFijadaFonograma() {
		return obraFijadaFonograma;
	}

	public void setObraFijadaFonograma(ObraFonogramaPropiedadIntelectual obraFijadaFonograma) {
		this.obraFijadaFonograma = obraFijadaFonograma;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getSitioWeb() {
		return sitioWeb;
	}

	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}

	/**
	 * @author Angela Devia
	 */
	// Inicio Requerimiento #2323
	public String getNombreCalidad() {
		String nombreCalidad = null;
		if (this.isEsAdministrativo()) {
			nombreCalidad = "Administrativo(a)";
		} else if (this.isEsDocente()) {
			nombreCalidad = "Docente";
		} else if (this.isEsEstudiante()) {
			nombreCalidad = "Estudiante";
		} else if (this.isEsExterno()) {
			nombreCalidad = "Externo";
		}
		return nombreCalidad;
	}
	// Fin Requerimiento #2323

}