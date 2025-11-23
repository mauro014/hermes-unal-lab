package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Requerimiento implements Serializable {

	private static final long serialVersionUID = -7459145277479725471L;
	private Long id;
	private String nombre;
	public DominioDetalle estadoRequerimiento;
	public String estSelRequerimiento;
	private String personaRequerimiento;
	public Dependencia idDependencia;
	private String idPersona;
	public TipoDocumento tipoDoc;
	private String modulo;
	private String moduloVista;
	private Date fechaSolicitud;
	private Date fechaEstimada;
	private Date fechaEntrega;
	private Date fechaTramite;
	private String comentarios;
	private String seqRequerimiento;
	private String ingenieroAsignado;
	private String ingenieroVista;
	private Boolean alertaEnviada;
	private String sprint;
	private Set documentos = new HashSet();
	// Solicitudes
	private String documentoRecurso;
	public TipoDocumento tipoDocRecurso;
	private String descripcionSolic;
	private String prioridad;
	private String proceso;
	private String tipo;
	private String subModulo;
	private String SOLICITUD_MEJORA = "Mejora";
	private String SOLICITUD_ERROR = "Inconveniente";
	private String comentariosIngeniero;
	private String dependenciaAsociada;
	private String emailSolicitante;
	private String telefonoSolicitante;
	private String observacionesIng;

	public List<ArchivoRequerimiento> getListaDocumentos() {
		try {
			List<ArchivoRequerimiento> listaDocumentos = new ArrayList<ArchivoRequerimiento>();
			listaDocumentos.addAll(documentos);
			System.out.println(listaDocumentos.size());
			return listaDocumentos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void borrarArchivo(ArchivoRequerimiento archivo) {
		this.documentos.remove(archivo);
	}

	public void adicionarArchivo(ArchivoRequerimiento archivo) {
		this.documentos.add(archivo);
	}

	// *****

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

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getPersonaRequerimiento() {
		return personaRequerimiento;
	}

	public void setPersonaRequerimiento(String personaRequerimiento) {
		this.personaRequerimiento = personaRequerimiento;
	}

	public DominioDetalle getEstadoRequerimiento() {
		return estadoRequerimiento;
	}

	public void setEstadoRequerimiento(DominioDetalle estadoRequerimiento) {
		this.estadoRequerimiento = estadoRequerimiento;
	}

	public Dependencia getIdDependencia() {
		return idDependencia;
	}

	public void setIdDependencia(Dependencia idDependencia) {
		this.idDependencia = idDependencia;
	}

	public TipoDocumento getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(TipoDocumento tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getSeqRequerimiento() {
		return seqRequerimiento;
	}

	public void setSeqRequerimiento(String seqRequerimiento) {
		this.seqRequerimiento = seqRequerimiento;
	}

	public String getEstSelRequerimiento() {
		return estSelRequerimiento;
	}

	public void setEstSelRequerimiento(String estSelRequerimiento) {
		this.estSelRequerimiento = estSelRequerimiento;
	}

	// ****

	public Set getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set documentos) {
		this.documentos = documentos;
	}

	public String getDocumentoRecurso() {
		return documentoRecurso;
	}

	public void setDocumentoRecurso(String documentoRecurso) {
		this.documentoRecurso = documentoRecurso;
	}

	public TipoDocumento getTipoDocRecurso() {
		return tipoDocRecurso;
	}

	public void setTipoDocRecurso(TipoDocumento tipoDocRecurso) {
		this.tipoDocRecurso = tipoDocRecurso;
	}

	public String getDescripcionSolic() {
		return descripcionSolic;
	}

	public void setDescripcionSolic(String descripcionSolic) {
		this.descripcionSolic = descripcionSolic;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getIngenieroAsignado() {
		return ingenieroAsignado;
	}

	public void setIngenieroAsignado(String ingenieroAsignado) {
		this.ingenieroAsignado = ingenieroAsignado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSOLICITUD_MEJORA() {
		return SOLICITUD_MEJORA;
	}

	public void setSOLICITUD_MEJORA(String sOLICITUD_MEJORA) {
		SOLICITUD_MEJORA = sOLICITUD_MEJORA;
	}

	public String getSOLICITUD_ERROR() {
		return SOLICITUD_ERROR;
	}

	public void setSOLICITUD_ERROR(String sOLICITUD_ERROR) {
		SOLICITUD_ERROR = sOLICITUD_ERROR;
	}

	public String getComentariosIngeniero() {
		return comentariosIngeniero;
	}

	public void setComentariosIngeniero(String comentariosIngeniero) {
		this.comentariosIngeniero = comentariosIngeniero;
	}

	public String getSubModulo() {
		return subModulo;
	}

	public void setSubModulo(String subModulo) {
		this.subModulo = subModulo;
	}

	public String getDependenciaAsociada() {
		return dependenciaAsociada;
	}

	public void setDependenciaAsociada(String dependenciaAsociada) {
		this.dependenciaAsociada = dependenciaAsociada;
	}

	public String getEmailSolicitante() {
		return emailSolicitante;
	}

	public void setEmailSolicitante(String emailSolicitante) {
		this.emailSolicitante = emailSolicitante;
	}

	public String getTelefonoSolicitante() {
		return telefonoSolicitante;
	}

	public void setTelefonoSolicitante(String telefonoSolicitante) {
		this.telefonoSolicitante = telefonoSolicitante;
	}

	public String getObservacionesIng() {
		return observacionesIng;
	}

	public void setObservacionesIng(String observacionesIng) {
		this.observacionesIng = observacionesIng;
	}

	public Date getFechaEstimada() {
		return fechaEstimada;
	}

	public void setFechaEstimada(Date fechaEstimada) {
		this.fechaEstimada = fechaEstimada;
	}

	public String getModuloVista() {
		return moduloVista;
	}

	public void setModuloVista(String moduloVista) {
		this.moduloVista = moduloVista;
	}

	public String getIngenieroVista() {
		return ingenieroVista;
	}

	public void setIngenieroVista(String ingenieroVista) {
		this.ingenieroVista = ingenieroVista;
	}

	public Date getFechaTramite() {
		return fechaTramite;
	}

	public void setFechaTramite(Date fechaTramite) {
		this.fechaTramite = fechaTramite;
	}

	public Boolean getAlertaEnviada() {
		return alertaEnviada;
	}

	public void setAlertaEnviada(Boolean alertaEnviada) {
		this.alertaEnviada = alertaEnviada;
	}

	public String getSprint() {
		return sprint;
	}

	public void setSprint(String sprint) {
		this.sprint = sprint;
	}

	// *************

}
