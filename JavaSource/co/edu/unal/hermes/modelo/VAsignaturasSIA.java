package co.edu.unal.hermes.modelo;

/**
 * @author dgbenitezc
 */
public class VAsignaturasSIA {

	private String annio;
	private String semestre;
	private Sede sede;
	private Dependencia facultad;
	private Dependencia uab;
	private String codAsignatura;
	private String grupo;
	private String nombreAsignatura;
	private Integer inscritos;
	private String tnivel;
	private String nivel;
	private Long id;

	public final static String NIVEL_PREGRADO = "PREGRADO";
	public final static String NIVEL_POSGRADO = "POSGRADO";
	public final static String NIVEL_DESCONOCIDO = "DESCONOCIDO";

	public VAsignaturasSIA() {
	}

	/**
	 * @return el periodo de la asignatura (año-semestre, por ejemplo 2015-03)
	 */
	public String getPeriodo() {
		return annio + "-" + semestre;
	}

	/**
	 * @return el curso de la asignatura (código-grupo, por ejemplo 1000009-5)
	 */
	public String getCurso() {
		return codAsignatura + "-" + grupo;
	}

	/**
	 * @return the annio
	 */
	public String getAnnio() {
		return annio;
	}

	/**
	 * @param annio
	 *            the annio to set
	 */
	public void setAnnio(String annio) {
		this.annio = annio;
	}

	/**
	 * @return the semestre
	 */
	public String getSemestre() {
		return semestre;
	}

	/**
	 * @param semestre
	 *            the semestre to set
	 */
	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	/**
	 * @return the sede
	 */
	public Sede getSede() {
		return sede;
	}

	/**
	 * @param sede
	 *            the sede to set
	 */
	public void setSede(Sede sede) {
		this.sede = sede;
	}

	/**
	 * @return the facultad
	 */
	public Dependencia getFacultad() {
		return facultad;
	}

	/**
	 * @param facultad
	 *            the facultad to set
	 */
	public void setFacultad(Dependencia facultad) {
		this.facultad = facultad;
	}

	/**
	 * @return the uab
	 */
	public Dependencia getUab() {
		return uab;
	}

	/**
	 * @param uab
	 *            the uab to set
	 */
	public void setUab(Dependencia uab) {
		this.uab = uab;
	}

	/**
	 * @return the codAsignatura
	 */
	public String getCodAsignatura() {
		return codAsignatura;
	}

	/**
	 * @param codAsignatura
	 *            the codAsignatura to set
	 */
	public void setCodAsignatura(String codAsignatura) {
		this.codAsignatura = codAsignatura;
	}

	/**
	 * @return the grupo
	 */
	public String getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo
	 *            the grupo to set
	 */
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return the nombreAsignatura
	 */
	public String getNombreAsignatura() {
		return nombreAsignatura;
	}

	/**
	 * @param nombreAsignatura
	 *            the nombreAsignatura to set
	 */
	public void setNombreAsignatura(String nombreAsignatura) {
		this.nombreAsignatura = nombreAsignatura;
	}

	/**
	 * @return the inscritos
	 */
	public Integer getInscritos() {
		return inscritos;
	}

	/**
	 * @param inscritos
	 *            the inscritos to set
	 */
	public void setInscritos(Integer inscritos) {
		this.inscritos = inscritos;
	}

	/**
	 * @return the tnivel
	 */
	public String getTnivel() {
		return tnivel;
	}

	/**
	 * @param tnivel
	 *            the tnivel to set
	 */
	public void setTnivel(String tnivel) {
		this.tnivel = tnivel;
	}

	/**
	 * @return the nivel
	 */
	public String getNivel() {
		return nivel;
	}

	/**
	 * @param nivel
	 *            the nivel to set
	 */
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
