package co.edu.unal.hermes.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author dgbenitezc
 */
public class DiagnosticoSoftware implements Cloneable {

	public DiagnosticoSoftware() {
	}

	private Long id;
	private String documentoPersona;
	private String tipoDocumentoPersona;
	private Date fecha;
	private String cargoPersona;
	private Sede sede;
	private Dependencia facultad;
	private Dependencia departamento;

	private String tipoRegistro;
	private String nombreSoftware;
	private String sistemaOperativo;
	private String descripcionSoftware;
	private Boolean softwareLibre;
	private Boolean disponibidadEquipos;
	private Tipos ejecucion;
	private String comentarios;

	private Empresa empresa;
	private ClasificacionConocimiento areaConocimiento;
	private VAsignaturasSIA asignatura;

	private Integer profesores;
	private Integer estudiantesPregrado;
	private Integer estudiantesPosgrado;

	private Long idPadre;

	private Set<DiagnosticoSoftware> empresasDistribuidoras = new HashSet<DiagnosticoSoftware>();
	private Set<DiagnosticoSoftware> asignaturas = new HashSet<DiagnosticoSoftware>();
	private Set<DiagnosticoSoftware> areasConocimiento = new HashSet<DiagnosticoSoftware>();

	public static String TIPO_REGISTRO_REGISTRO = "REG";
	public static String TIPO_REGISTRO_DOCENCIA = "DOC";
	public static String TIPO_REGISTRO_INVESTIGACION = "INV";
	public static String TIPO_REGISTRO_DETALLE_EMPRESA = "DE";
	public static String TIPO_REGISTRO_DETALLE_DOCENCIA = "DD";
	public static String TIPO_REGISTRO_DETALLE_INVESTIGACION = "DI";

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
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

	/**
	 * @return the documentoPersona
	 */
	public String getDocumentoPersona() {
		return documentoPersona;
	}

	/**
	 * @param documentoPersona
	 *            the documentoPersona to set
	 */
	public void setDocumentoPersona(String documentoPersona) {
		this.documentoPersona = documentoPersona;
	}

	/**
	 * @return the tipoDocumentoPersona
	 */
	public String getTipoDocumentoPersona() {
		return tipoDocumentoPersona;
	}

	/**
	 * @param tipoDocumentoPersona
	 *            the tipoDocumentoPersona to set
	 */
	public void setTipoDocumentoPersona(String tipoDocumentoPersona) {
		this.tipoDocumentoPersona = tipoDocumentoPersona;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the cargoPersona
	 */
	public String getCargoPersona() {
		return cargoPersona;
	}

	/**
	 * @param cargoPersona
	 *            the cargoPersona to set
	 */
	public void setCargoPersona(String cargoPersona) {
		this.cargoPersona = cargoPersona;
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
	 * @return the nombreSoftware
	 */
	public String getNombreSoftware() {
		return nombreSoftware;
	}

	/**
	 * @param nombreSoftware
	 *            the nombreSoftware to set
	 */
	public void setNombreSoftware(String nombreSoftware) {
		this.nombreSoftware = nombreSoftware;
	}

	/**
	 * @return the descripcionSoftware
	 */
	public String getDescripcionSoftware() {
		return descripcionSoftware;
	}

	/**
	 * @param descripcionSoftware
	 *            the descripcionSoftware to set
	 */
	public void setDescripcionSoftware(String descripcionSoftware) {
		this.descripcionSoftware = descripcionSoftware;
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
	 * @return the departamento
	 */
	public Dependencia getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento
	 *            the departamento to set
	 */
	public void setDepartamento(Dependencia departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the idPadre
	 */
	public Long getIdPadre() {
		return idPadre;
	}

	/**
	 * @param idPadre
	 *            the idPadre to set
	 */
	public void setIdPadre(Long idPadre) {
		this.idPadre = idPadre;
	}

	/**
	 * @return the tipoRegistro
	 */
	public String getTipoRegistro() {
		return tipoRegistro;
	}

	/**
	 * @param tipoRegistro
	 *            the tipoRegistro to set
	 */
	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	/**
	 * @return the empresasDistribuidoras
	 */
	public Set<DiagnosticoSoftware> getEmpresasDistribuidoras() {
		return empresasDistribuidoras;
	}

	/**
	 * @param empresasDistribuidoras
	 *            the empresasDistribuidoras to set
	 */
	public void setEmpresasDistribuidoras(
			Set<DiagnosticoSoftware> empresasDistribuidoras) {
		this.empresasDistribuidoras = empresasDistribuidoras;
	}

	public void agregarEmpresaDistribuidora(Empresa empresa) {
		DiagnosticoSoftware dSE = new DiagnosticoSoftware();
		dSE.setTipoRegistro(TIPO_REGISTRO_DETALLE_EMPRESA);
		dSE.setEmpresa(empresa);
		dSE.setFecha(new Date());
		empresasDistribuidoras.add(dSE);
	}

	public List<DiagnosticoSoftware> getListaEmpresasDistribuidoras() {
		List<DiagnosticoSoftware> listaEquipos = new ArrayList<DiagnosticoSoftware>();
		listaEquipos.addAll(empresasDistribuidoras);
		return listaEquipos;
	}

	public void agregarAsignatura(VAsignaturasSIA asignatura) {
		DiagnosticoSoftware dSE = new DiagnosticoSoftware();
		dSE.setTipoRegistro(TIPO_REGISTRO_DETALLE_DOCENCIA);
		dSE.setSede(asignatura.getSede());
		Dependencia facultad = new Dependencia(String.valueOf(asignatura
				.getFacultad()));
		dSE.setFacultad(facultad);
		Dependencia departamento = new Dependencia(String.valueOf(asignatura
				.getUab()));
		dSE.setDepartamento(departamento);
		dSE.setAsignatura(asignatura);
		dSE.setFecha(new Date());
		dSE.setProfesores(1);
		dSE.setEstudiantesPregrado(0);
		dSE.setEstudiantesPosgrado(0);
		if (asignatura.getNivel().equals(VAsignaturasSIA.NIVEL_PREGRADO)) {
			dSE.setEstudiantesPregrado(asignatura.getInscritos());
		} else {
			dSE.setEstudiantesPosgrado(asignatura.getInscritos());
		}
		asignaturas.add(dSE);
	}

	public void eliminarAsignatura(DiagnosticoSoftware dSE) {
		asignaturas.remove(dSE);
	}

	public List<DiagnosticoSoftware> getListaAsignaturas() {
		List<DiagnosticoSoftware> listaAsignaturas = new ArrayList<DiagnosticoSoftware>();
		listaAsignaturas.addAll(asignaturas);
		return listaAsignaturas;
	}

	public void agregarAreaConocimiento(
			ClasificacionConocimiento clasificacionConocimiento) {
		DiagnosticoSoftware dSE = new DiagnosticoSoftware();
		dSE.setTipoRegistro(TIPO_REGISTRO_DETALLE_INVESTIGACION);
		dSE.setAreaConocimiento(clasificacionConocimiento);
		dSE.setProfesores(0);
		dSE.setEstudiantesPregrado(0);
		dSE.setEstudiantesPosgrado(0);
		dSE.setFecha(new Date());
		areasConocimiento.add(dSE);
	}

	public void agregarAreaConocimiento(
			ClasificacionConocimiento clasificacionConocimiento, Sede sede,
			Dependencia facultad, Dependencia departamento) {
		DiagnosticoSoftware dSE = new DiagnosticoSoftware();
		dSE.setTipoRegistro(TIPO_REGISTRO_DETALLE_INVESTIGACION);
		dSE.setAreaConocimiento(clasificacionConocimiento);
		dSE.setProfesores(0);
		dSE.setEstudiantesPregrado(0);
		dSE.setEstudiantesPosgrado(0);
		dSE.setSede(new Sede(sede.getId()));
		dSE.setFacultad(new Dependencia(facultad.getId()));
		dSE.setDepartamento(new Dependencia(departamento.getId()));
		dSE.setFecha(new Date());
		areasConocimiento.add(dSE);
	}

	public void eliminarAreaConocimiento(DiagnosticoSoftware dSE) {
		areasConocimiento.remove(dSE);
	}

	public List<DiagnosticoSoftware> getListaAreasConocimiento() {
		List<DiagnosticoSoftware> listaCC = new ArrayList<DiagnosticoSoftware>();
		listaCC.addAll(areasConocimiento);
		return listaCC;
	}

	/**
	 * @return the empresa
	 */
	public Empresa getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa
	 *            the empresa to set
	 */
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	/**
	 * @return the asignaturas
	 */
	public Set<DiagnosticoSoftware> getAsignaturas() {
		return asignaturas;
	}

	/**
	 * @param asignaturas
	 *            the asignaturas to set
	 */
	public void setAsignaturas(Set<DiagnosticoSoftware> asignaturas) {
		this.asignaturas = asignaturas;
	}

	/**
	 * @return the asignatura
	 */
	public VAsignaturasSIA getAsignatura() {
		return asignatura;
	}

	/**
	 * @param asignatura
	 *            the asignatura to set
	 */
	public void setAsignatura(VAsignaturasSIA asignatura) {
		this.asignatura = asignatura;
	}

	/**
	 * @return the profesores
	 */
	public Integer getProfesores() {
		return profesores;
	}

	/**
	 * @param profesores
	 *            the profesores to set
	 */
	public void setProfesores(Integer profesores) {
		this.profesores = profesores;
	}

	/**
	 * @return the estudiantesPregrado
	 */
	public Integer getEstudiantesPregrado() {
		return estudiantesPregrado;
	}

	/**
	 * @param estudiantesPregrado
	 *            the estudiantesPregrado to set
	 */
	public void setEstudiantesPregrado(Integer estudiantesPregrado) {
		this.estudiantesPregrado = estudiantesPregrado;
	}

	/**
	 * @return the estudiantesPosgrado
	 */
	public Integer getEstudiantesPosgrado() {
		return estudiantesPosgrado;
	}

	/**
	 * @param estudiantesPosgrado
	 *            the estudiantesPosgrado to set
	 */
	public void setEstudiantesPosgrado(Integer estudiantesPosgrado) {
		this.estudiantesPosgrado = estudiantesPosgrado;
	}

	/**
	 * @return the areasConocimiento
	 */
	public Set<DiagnosticoSoftware> getAreasConocimiento() {
		return areasConocimiento;
	}

	/**
	 * @param areasConocimiento
	 *            the areasConocimiento to set
	 */
	public void setAreasConocimiento(Set<DiagnosticoSoftware> areasConocimiento) {
		this.areasConocimiento = areasConocimiento;
	}

	/**
	 * @return the areaConocimiento
	 */
	public ClasificacionConocimiento getAreaConocimiento() {
		return areaConocimiento;
	}

	/**
	 * @param areaConocimiento
	 *            the areaConocimiento to set
	 */
	public void setAreaConocimiento(ClasificacionConocimiento areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}

	/**
	 * @return the sistemaOperativo
	 */
	public String getSistemaOperativo() {
		return sistemaOperativo;
	}

	/**
	 * @param sistemaOperativo
	 *            the sistemaOperativo to set
	 */
	public void setSistemaOperativo(String sistemaOperativo) {
		this.sistemaOperativo = sistemaOperativo;
	}

	/**
	 * @return the softwareLibre
	 */
	public Boolean getSoftwareLibre() {
		return softwareLibre;
	}

	/**
	 * @param softwareLibre
	 *            the softwareLibre to set
	 */
	public void setSoftwareLibre(Boolean softwareLibre) {
		this.softwareLibre = softwareLibre;
	}

	/**
	 * @return the disponibidadEquipos
	 */
	public Boolean getDisponibidadEquipos() {
		return disponibidadEquipos;
	}

	/**
	 * @param disponibidadEquipos
	 *            the disponibidadEquipos to set
	 */
	public void setDisponibidadEquipos(Boolean disponibidadEquipos) {
		this.disponibidadEquipos = disponibidadEquipos;
	}

	/**
	 * @return the ejecucion
	 */
	public Tipos getEjecucion() {
		return ejecucion;
	}

	/**
	 * @param ejecucion
	 *            the ejecucion to set
	 */
	public void setEjecucion(Tipos ejecucion) {
		this.ejecucion = ejecucion;
	}

	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios
	 *            the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

}
