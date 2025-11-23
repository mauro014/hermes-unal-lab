package co.edu.unal.hermes.modelo.laboratorios;

import java.util.Date;

import co.edu.unal.hermes.modelo.Dependencia;
import co.edu.unal.hermes.modelo.Empresa;
import co.edu.unal.hermes.modelo.Sede;
import co.edu.unal.hermes.modelo.Tipos;

/**
 * @author dgbenitezc
 */
public class EvaluacionProveedores {

	private Long id;
	private String tipoDocumento;
	private String documento;
	private Date fechaRegistro;
	private String cargo;
	private Sede sede;
	private Dependencia facultad;
	private Dependencia departamento;
	private Tipos mecanismoAdquisicion;
	private Tipos tipoAdquisicion;
	private Integer numeroOrden;
	private Integer annioOrden;
	private String objetoContrato;
	private Empresa empresa;
	private String otrosProductos;
	private Long calidad;
	private String comentarioCalidad;
	private Long oportunidad;
	private String comentarioOportunidad;
	private Long documentos;
	private String comentarioDocumentos;
	private Long comportamiento;
	private String comentarioComportamiento;
	private Long asesoria;
	private String comentarioAsesoria;
	private Long recomendacion;
	private String comentarioRecomendacion;

	private String nombreEvaluador;

	public EvaluacionProveedores() {
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
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento
	 *            the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * @return the documento
	 */
	public String getDocumento() {
		return documento;
	}

	/**
	 * @param documento
	 *            the documento to set
	 */
	public void setDocumento(String documento) {
		this.documento = documento;
	}

	/**
	 * @return the cargo
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * @param cargo
	 *            the cargo to set
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
	 * @return the mecanismoAdquisicion
	 */
	public Tipos getMecanismoAdquisicion() {
		return mecanismoAdquisicion;
	}

	/**
	 * @param mecanismoAdquisicion
	 *            the mecanismoAdquisicion to set
	 */
	public void setMecanismoAdquisicion(Tipos mecanismoAdquisicion) {
		this.mecanismoAdquisicion = mecanismoAdquisicion;
	}

	/**
	 * @return the tipoAdquisicion
	 */
	public Tipos getTipoAdquisicion() {
		return tipoAdquisicion;
	}

	/**
	 * @param tipoAdquisicion
	 *            the tipoAdquisicion to set
	 */
	public void setTipoAdquisicion(Tipos tipoAdquisicion) {
		this.tipoAdquisicion = tipoAdquisicion;
	}

	/**
	 * @return the numeroOrden
	 */
	public Integer getNumeroOrden() {
		return numeroOrden;
	}

	/**
	 * @param numeroOrden
	 *            the numeroOrden to set
	 */
	public void setNumeroOrden(Integer numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	/**
	 * @return the annioOrden
	 */
	public Integer getAnnioOrden() {
		return annioOrden;
	}

	/**
	 * @param annioOrden
	 *            the annioOrden to set
	 */
	public void setAnnioOrden(Integer annioOrden) {
		this.annioOrden = annioOrden;
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
	 * @return the calidad
	 */
	public Long getCalidad() {
		return calidad;
	}

	/**
	 * @param calidad
	 *            the calidad to set
	 */
	public void setCalidad(Long calidad) {
		this.calidad = calidad;
	}

	/**
	 * @return the comentarioCalidad
	 */
	public String getComentarioCalidad() {
		return comentarioCalidad;
	}

	/**
	 * @param comentarioCalidad
	 *            the comentarioCalidad to set
	 */
	public void setComentarioCalidad(String comentarioCalidad) {
		this.comentarioCalidad = comentarioCalidad;
	}

	/**
	 * @return the oportunidad
	 */
	public Long getOportunidad() {
		return oportunidad;
	}

	/**
	 * @param oportunidad
	 *            the oportunidad to set
	 */
	public void setOportunidad(Long oportunidad) {
		this.oportunidad = oportunidad;
	}

	/**
	 * @return the comentarioOportunidad
	 */
	public String getComentarioOportunidad() {
		return comentarioOportunidad;
	}

	/**
	 * @param comentarioOportunidad
	 *            the comentarioOportunidad to set
	 */
	public void setComentarioOportunidad(String comentarioOportunidad) {
		this.comentarioOportunidad = comentarioOportunidad;
	}

	/**
	 * @return the documentos
	 */
	public Long getDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos
	 *            the documentos to set
	 */
	public void setDocumentos(Long documentos) {
		this.documentos = documentos;
	}

	/**
	 * @return the comentarioDocumentos
	 */
	public String getComentarioDocumentos() {
		return comentarioDocumentos;
	}

	/**
	 * @param comentarioDocumentos
	 *            the comentarioDocumentos to set
	 */
	public void setComentarioDocumentos(String comentarioDocumentos) {
		this.comentarioDocumentos = comentarioDocumentos;
	}

	/**
	 * @return the comportamiento
	 */
	public Long getComportamiento() {
		return comportamiento;
	}

	/**
	 * @param comportamiento
	 *            the comportamiento to set
	 */
	public void setComportamiento(Long comportamiento) {
		this.comportamiento = comportamiento;
	}

	/**
	 * @return the comentarioComportamiento
	 */
	public String getComentarioComportamiento() {
		return comentarioComportamiento;
	}

	/**
	 * @param comentarioComportamiento
	 *            the comentarioComportamiento to set
	 */
	public void setComentarioComportamiento(String comentarioComportamiento) {
		this.comentarioComportamiento = comentarioComportamiento;
	}

	/**
	 * @return the asesoria
	 */
	public Long getAsesoria() {
		return asesoria;
	}

	/**
	 * @param asesoria
	 *            the asesoria to set
	 */
	public void setAsesoria(Long asesoria) {
		this.asesoria = asesoria;
	}

	/**
	 * @return the comentarioAsesoria
	 */
	public String getComentarioAsesoria() {
		return comentarioAsesoria;
	}

	/**
	 * @param comentarioAsesoria
	 *            the comentarioAsesoria to set
	 */
	public void setComentarioAsesoria(String comentarioAsesoria) {
		this.comentarioAsesoria = comentarioAsesoria;
	}

	/**
	 * @return the recomendacion
	 */
	public Long getRecomendacion() {
		return recomendacion;
	}

	/**
	 * @param recomendacion
	 *            the recomendacion to set
	 */
	public void setRecomendacion(Long recomendacion) {
		this.recomendacion = recomendacion;
	}

	/**
	 * @return the comentarioRecomendacion
	 */
	public String getComentarioRecomendacion() {
		return comentarioRecomendacion;
	}

	/**
	 * @param comentarioRecomendacion
	 *            the comentarioRecomendacion to set
	 */
	public void setComentarioRecomendacion(String comentarioRecomendacion) {
		this.comentarioRecomendacion = comentarioRecomendacion;
	}

	/**
	 * @return the objetoContrato
	 */
	public String getObjetoContrato() {
		return objetoContrato;
	}

	/**
	 * @param objetoContrato
	 *            the objetoContrato to set
	 */
	public void setObjetoContrato(String objetoContrato) {
		this.objetoContrato = objetoContrato;
	}

	/**
	 * @return the otrosProductos
	 */
	public String getOtrosProductos() {
		return otrosProductos;
	}

	/**
	 * @param otrosProductos
	 *            the otrosProductos to set
	 */
	public void setOtrosProductos(String otrosProductos) {
		this.otrosProductos = otrosProductos;
	}

	/**
	 * @return the nombreEvaluador
	 */
	public String getNombreEvaluador() {
		return nombreEvaluador;
	}

	/**
	 * @param nombreEvaluador
	 *            the nombreEvaluador to set
	 */
	public void setNombreEvaluador(String nombreEvaluador) {
		this.nombreEvaluador = nombreEvaluador;
	}

}
