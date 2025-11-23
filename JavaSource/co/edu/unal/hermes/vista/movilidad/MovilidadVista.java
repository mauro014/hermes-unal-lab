/*
 * Created on 9-dic-2007
 */
package co.edu.unal.hermes.vista.movilidad;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Rodrigo Gallo
 */
public class MovilidadVista{

	public static String EVENTO = "E";
	public static String TESIS = "T";
	public static String VISITANTE = "V";

	private Long id;
	private Long idtipo;
	private String tipoDocumentoPersona;
	private String tipoDocumentoCreador;
	private Long idpersona;
	private Long idprograma;
	private Long idcreador;
	byte[] hojavida;
	private String plan;
	private Long costotiquete;
	private Long monto;
	private Integer numerodias;
	private Integer idinvestigador;
	private String aprobacion;
	private String resumen;
	private String idsubtipo;
	private Date fechasolicitud;
	private Integer idgrupo;
	private Integer idproyecto;
	private Long idplanAccion;
	private String evento;
	private String titulo;
	private String pais;
	private String ciudad;
	private String inscripcionevento;
	private Long costoevento;
	private Long aportefacultad;
	private String solicituddocente;
	private String resolucionviaje;
	private Date fechainicial;
	private Date fechafinal;
	private Date fechasalida;
	private Date fechallegada;
	private String aceptacion;
	private String programa;
	private String universidad;
	private String tituloAspira;
	private String nombreDocente;
	private String documentoVisitante;
	private String nacionalidad;
	private String tipoPonencia;
	private String otroTipoPonencia;
	private String nombreArchivo;
	private String nombreFacultadSolicitante;
	private String nombreDepartamentoSolicitante;
	private String nombreProyectoAsociado;
	private String nombrePais;
	private String nombreSolicitante;
	private String nombrePonencia;
	private String nombreGrupo;
	private String nombreProgramaAsociado;
	private String nombreLiderGrupo;
	private String nombrePlanAccion;
	
	private Set actividades = new HashSet();


	public MovilidadVista() {
	}

	/**
	 * Constructor que recibe como parametro el id, para inicializar
	 * el id del objeto.
	 * 
	 * @param LongpIdTipoDato
	 */
	public MovilidadVista(Long pId){
		this.id = pId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdtipo() {
		return idtipo;
	}

	public void setIdtipo(Long idtipo) {
		this.idtipo = idtipo;
	}

	public Long getIdpersona() {
		return idpersona;
	}

	public void setIdpersona(Long idpersona) {
		this.idpersona = idpersona;
	}

	public Long getIdprograma() {
		return idprograma;
	}

	public void setIdprograma(Long idprograma) {
		this.idprograma = idprograma;
	}

	public byte[] getHojavida() {
		return hojavida;
	}

	public void setHojavida(byte[] hojavida) {
		this.hojavida = hojavida;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Long getCostotiquete() {
		return costotiquete;
	}

	public void setCostotiquete(Long costotiquete) {
		this.costotiquete = costotiquete;
	}

	public Long getMonto() {
		return monto;
	}

	public void setMonto(Long monto) {
		this.monto = monto;
	}

	public Integer getNumerodias() {
		return numerodias;
	}

	public void setNumerodias(Integer numerodias) {
		this.numerodias = numerodias;
	}

	public Integer getIdinvestigador() {
		return idinvestigador;
	}

	public void setIdinvestigador(Integer idinvestigador) {
		this.idinvestigador = idinvestigador;
	}

	public String getAprobacion() {
		return aprobacion;
	}

	public void setAprobacion(String aprobacion) {
		this.aprobacion = aprobacion;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getIdsubtipo() {
		return idsubtipo;
	}

	public void setIdsubtipo(String idsubtipo) {
		this.idsubtipo = idsubtipo;
	}

	public Date getFechasolicitud() {
		return fechasolicitud;
	}

	public void setFechasolicitud(Date fechasolicitud) {
		this.fechasolicitud = fechasolicitud;
	}

	public Integer getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(Integer idgrupo) {
		this.idgrupo = idgrupo;
	}

	public Integer getIdproyecto() {
		return idproyecto;
	}

	public void setIdproyecto(Integer idproyecto) {
		this.idproyecto = idproyecto;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getInscripcionevento() {
		return inscripcionevento;
	}

	public void setInscripcionevento(String inscripcionevento) {
		this.inscripcionevento = inscripcionevento;
	}

	public Long getCostoevento() {
		return costoevento;
	}

	public void setCostoevento(Long costoevento) {
		this.costoevento = costoevento;
	}

	public Long getAportefacultad() {
		return aportefacultad;
	}

	public void setAportefacultad(Long aportefacultad) {
		this.aportefacultad = aportefacultad;
	}

	public String getSolicituddocente() {
		return solicituddocente;
	}

	public void setSolicituddocente(String solicituddocente) {
		this.solicituddocente = solicituddocente;
	}

	public String getResolucionviaje() {
		return resolucionviaje;
	}

	public void setResolucionviaje(String resolucionviaje) {
		this.resolucionviaje = resolucionviaje;
	}

	public Date getFechainicial() {
		return fechainicial;
	}

	public void setFechainicial(Date fechainicial) {
		this.fechainicial = fechainicial;
	}

	public Date getFechafinal() {
		return fechafinal;
	}

	public void setFechafinal(Date fechafinal) {
		this.fechafinal = fechafinal;
	}

	public Date getFechasalida() {
		return fechasalida;
	}

	public void setFechasalida(Date fechasalida) {
		this.fechasalida = fechasalida;
	}

	public Date getFechallegada() {
		return fechallegada;
	}

	public void setFechallegada(Date fechallegada) {
		this.fechallegada = fechallegada;
	}

	public String getAceptacion() {
		return aceptacion;
	}

	public void setAceptacion(String aceptacion) {
		this.aceptacion = aceptacion;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getUniversidad() {
		return universidad;
	}

	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}

	public String getTituloAspira() {
		return tituloAspira;
	}

	public void setTituloAspira(String tituloAspira) {
		this.tituloAspira = tituloAspira;
	}

	public String getNombreDocente() {
		return nombreDocente;
	}

	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}

	public String getDocumentoVisitante() {
		return documentoVisitante;
	}

	public void setDocumentoVisitante(String documentoVisitante) {
		this.documentoVisitante = documentoVisitante;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getTipoPonencia() {
		return tipoPonencia;
	}

	public void setTipoPonencia(String tipoPonencia) {
		this.tipoPonencia = tipoPonencia;
	}

	public String getOtroTipoPonencia() {
		return otroTipoPonencia;
	}

	public void setOtroTipoPonencia(String otroTipoPonencia) {
		this.otroTipoPonencia = otroTipoPonencia;
	}

	public Set getActividades() {
		return actividades;
	}

	public void setActividades(Set actividades) {
		this.actividades = actividades;
	}

	public List getListaActividades(){
		List listaActividades=new ArrayList();
		listaActividades.addAll(actividades);
		return 	listaActividades;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Long getIdcreador() {
		return idcreador;
	}

	public void setIdcreador(Long idcreador) {
		this.idcreador = idcreador;
	}

	public String getTipoDocumentoPersona() {
		return tipoDocumentoPersona;
	}

	public void setTipoDocumentoPersona(String tipoDocumentoPersona) {
		this.tipoDocumentoPersona = tipoDocumentoPersona;
	}

	public String getTipoDocumentoCreador() {
		return tipoDocumentoCreador;
	}

	public void setTipoDocumentoCreador(String tipoDocumentoCreador) {
		this.tipoDocumentoCreador = tipoDocumentoCreador;
	}

	public String getNombreFacultadSolicitante() {
		return nombreFacultadSolicitante;
	}

	public void setNombreFacultadSolicitante(String nombreFacultadSolicitante) {
		this.nombreFacultadSolicitante = nombreFacultadSolicitante;
	}

	public String getNombreDepartamentoSolicitante() {
		return nombreDepartamentoSolicitante;
	}

	public void setNombreDepartamentoSolicitante(
			String nombreDepartamentoSolicitante) {
		this.nombreDepartamentoSolicitante = nombreDepartamentoSolicitante;
	}

	public String getNombreProyectoAsociado() {
		return nombreProyectoAsociado;
	}

	public void setNombreProyectoAsociado(String nombreProyectoAsociado) {
		this.nombreProyectoAsociado = nombreProyectoAsociado;
	}

	public String getNombrePais() {
		return nombrePais;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}

	public String getNombreSolicitante() {
		return nombreSolicitante;
	}

	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}

	public String getNombrePonencia() {
		return nombrePonencia;
	}

	public void setNombrePonencia(String nombrePonencia) {
		this.nombrePonencia = nombrePonencia;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public String getNombreProgramaAsociado() {
		return nombreProgramaAsociado;
	}

	public void setNombreProgramaAsociado(String nombreProgramaAsociado) {
		this.nombreProgramaAsociado = nombreProgramaAsociado;
	}

	public String getNombreLiderGrupo() {
		return nombreLiderGrupo;
	}

	public void setNombreLiderGrupo(String nombreLiderGrupo) {
		this.nombreLiderGrupo = nombreLiderGrupo;
	}

	public String getNombrePlanAccion() {
		return nombrePlanAccion;
	}

	public void setNombrePlanAccion(String nombrePlanAccion) {
		this.nombrePlanAccion = nombrePlanAccion;
	}

	public Long getIdplanAccion() {
		return idplanAccion;
	}

	public void setIdplanAccion(Long idplanAccion) {
		this.idplanAccion = idplanAccion;
	}
}
