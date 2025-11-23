/*
 * Created on 9-dic-2007
 */
package co.edu.unal.hermes.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author gacantorm
 */
public class ConvocatoriaEvento implements Serializable{


	//INFORMACIÓN GENERAL
	private Long id;
	private Date fechaSolicitud;
	private Persona personaInv;
	private String dependenciaSolicitud;
	private String nombreEvento;
	private String justificacion;
	private String tipoEvento;
	private String otroTipo;
	private String caracterEvento;
	private String dependenciasUN;
	private String dependenciasEXT;
	private String productos;
	private String valorSolicitado;
	private String dependenciaEjecutora;
	private String valorEvento;
	private String actividades;
	private String estado;
	private String evaluacion;
	private String comentarioEvaluacion;
	private String requerimiento1;
	private String requerimiento2;
	private String requerimiento3;
	private String requerimiento4;
	private String montoAprobado;
	private String tipoSolicitud;
	private Date fechaEvento;
	private Date fechaFinalizacionEvento;
	private String lugar;
	private String aporteEconomico;
	private String asistentes;
	private String conferencistasNacExt;
	private String conferencistasNacVinc;
	private String conferencistasInternac;
	private String tipoParticipacion;
	private String rei1 = "NO";
	private String rei2 = "NO";
	private String rei3 = "NO";
	private String rei4 = "NO";
	private String rei5 = "NO";
	private String rei6 = "NO";
	private String rei7 = "NO";
	private String ren1 = "NO";
	private String ren2 = "NO";
	private String ren3 = "NO";
	private String ren4 = "NO";
	private String ren5 = "NO";
	private String ren6 = "NO";
	private String ren7 = "NO";
	private String rel1 = "NO";
	private String rel2 = "NO";
	private String rel3 = "NO";
	private String rel4 = "NO";
	private String rel5 = "NO";
	private String rel6 = "NO";
	private String rel7 = "NO";
	private String rel8 = "NO";
	private String conferencistas;
	private String actividadesRealizadas;
	private String asistentesEvento;
	private String alianzas;
	private String financiacionUN;
	private String observaciones;
	private String resultados;
	private String observaciones_VRI;


	
	public String getRei1() {
		return rei1;
	}


	public void setRei1(String rei1) {
		this.rei1 = rei1;
	}


	public String getRei2() {
		return rei2;
	}


	public void setRei2(String rei2) {
		this.rei2 = rei2;
	}


	public String getRei3() {
		return rei3;
	}


	public void setRei3(String rei3) {
		this.rei3 = rei3;
	}


	public String getRei4() {
		return rei4;
	}


	public void setRei4(String rei4) {
		this.rei4 = rei4;
	}


	public String getRei5() {
		return rei5;
	}


	public void setRei5(String rei5) {
		this.rei5 = rei5;
	}


	public String getRei6() {
		return rei6;
	}


	public void setRei6(String rei6) {
		this.rei6 = rei6;
	}


	public String getRei7() {
		return rei7;
	}


	public void setRei7(String rei7) {
		this.rei7 = rei7;
	}


	public String getRen1() {
		return ren1;
	}


	public void setRen1(String ren1) {
		this.ren1 = ren1;
	}


	public String getRen2() {
		return ren2;
	}


	public void setRen2(String ren2) {
		this.ren2 = ren2;
	}


	public String getRen3() {
		return ren3;
	}


	public void setRen3(String ren3) {
		this.ren3 = ren3;
	}


	public String getRen4() {
		return ren4;
	}


	public void setRen4(String ren4) {
		this.ren4 = ren4;
	}


	public String getRen5() {
		return ren5;
	}


	public void setRen5(String ren5) {
		this.ren5 = ren5;
	}


	public String getRen6() {
		return ren6;
	}


	public void setRen6(String ren6) {
		this.ren6 = ren6;
	}


	public String getRen7() {
		return ren7;
	}


	public void setRen7(String ren7) {
		this.ren7 = ren7;
	}


	public String getRel1() {
		return rel1;
	}


	public void setRel1(String rel1) {
		this.rel1 = rel1;
	}


	public String getRel2() {
		return rel2;
	}


	public void setRel2(String rel2) {
		this.rel2 = rel2;
	}


	public String getRel3() {
		return rel3;
	}


	public void setRel3(String rel3) {
		this.rel3 = rel3;
	}


	public String getRel4() {
		return rel4;
	}


	public void setRel4(String rel4) {
		this.rel4 = rel4;
	}


	public String getRel5() {
		return rel5;
	}


	public void setRel5(String rel5) {
		this.rel5 = rel5;
	}


	public String getRel6() {
		return rel6;
	}


	public void setRel6(String rel6) {
		this.rel6 = rel6;
	}


	public String getRel7() {
		return rel7;
	}


	public void setRel7(String rel7) {
		this.rel7 = rel7;
	}


	public String getRel8() {
		return rel8;
	}


	public void setRel8(String rel8) {
		this.rel8 = rel8;
	}


	//ARCHIVOS
	private Set archivos = new HashSet();
	

	public ConvocatoriaEvento() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}


	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}


	public Persona getPersonaInv() {
		return personaInv;
	}


	public void setPersonaInv(Persona personaInv) {
		this.personaInv = personaInv;
	}


	public String getDependenciaSolicitud() {
		return dependenciaSolicitud;
	}


	public void setDependenciaSolicitud(String dependenciaSolicitud) {
		this.dependenciaSolicitud = dependenciaSolicitud;
	}


	public String getNombreEvento() {
		return nombreEvento;
	}


	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}


	public String getJustificacion() {
		return justificacion;
	}


	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}


	public String getTipoEvento() {
		return tipoEvento;
	}


	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}


	public String getCaracterEvento() {
		return caracterEvento;
	}


	public void setCaracterEvento(String caracterEvento) {
		this.caracterEvento = caracterEvento;
	}


	public String getDependenciasUN() {
		return dependenciasUN;
	}


	public void setDependenciasUN(String dependenciasUN) {
		this.dependenciasUN = dependenciasUN;
	}


	public String getDependenciasEXT() {
		return dependenciasEXT;
	}


	public void setDependenciasEXT(String dependenciasEXT) {
		this.dependenciasEXT = dependenciasEXT;
	}


	public String getProductos() {
		return productos;
	}


	public void setProductos(String productos) {
		this.productos = productos;
	}


	public String getValorSolicitado() {
		return valorSolicitado;
	}


	public void setValorSolicitado(String valorSolicitado) {
		this.valorSolicitado = valorSolicitado;
	}


	public String getDependenciaEjecutora() {
		return dependenciaEjecutora;
	}


	public void setDependenciaEjecutora(String dependenciaEjecutora) {
		this.dependenciaEjecutora = dependenciaEjecutora;
	}


	public String getValorEvento() {
		return valorEvento;
	}


	public void setValorEvento(String valorEvento) {
		this.valorEvento = valorEvento;
	}


	public String getActividades() {
		return actividades;
	}


	public void setActividades(String actividades) {
		this.actividades = actividades;
	}


	public Set getArchivos() {
		return archivos;
	}


	public void setArchivos(Set archivos) {
		this.archivos = archivos;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
	}


	public String getEvaluacion() {
		return evaluacion;
	}


	public String getComentarioEvaluacion() {
		return comentarioEvaluacion;
	}


	public void setComentarioEvaluacion(String comentarioEvaluacion) {
		this.comentarioEvaluacion = comentarioEvaluacion;
	}


	public void setRequerimiento1(String requerimiento1) {
		this.requerimiento1 = requerimiento1;
	}


	public String getRequerimiento1() {
		return requerimiento1;
	}


	public void setRequerimiento2(String requerimiento2) {
		this.requerimiento2 = requerimiento2;
	}


	public String getRequerimiento2() {
		return requerimiento2;
	}


	public void setRequerimiento3(String requerimiento3) {
		this.requerimiento3 = requerimiento3;
	}


	public String getRequerimiento3() {
		return requerimiento3;
	}


	public void setRequerimiento4(String requerimiento4) {
		this.requerimiento4 = requerimiento4;
	}


	public String getRequerimiento4() {
		return requerimiento4;
	}


	public void setMontoAprobado(String montoAprobado) {
		this.montoAprobado = montoAprobado;
	}


	public String getMontoAprobado() {
		return montoAprobado;
	}


	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}


	public String getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}


	public Date getFechaEvento() {
		return fechaEvento;
	}


	public void setAporteEconomico(String aporteEconomico) {
		this.aporteEconomico = aporteEconomico;
	}


	public String getAporteEconomico() {
		return aporteEconomico;
	}


	public void setLugar(String lugar) {
		this.lugar = lugar;
	}


	public String getLugar() {
		return lugar;
	}


	public void setAsistentes(String asistentes) {
		this.asistentes = asistentes;
	}


	public String getAsistentes() {
		return asistentes;
	}


	public void setConferencistasNacExt(String conferencistasNacExt) {
		this.conferencistasNacExt = conferencistasNacExt;
	}


	public String getConferencistasNacExt() {
		return conferencistasNacExt;
	}


	public void setConferencistasNacVinc(String conferencistasNacVinc) {
		this.conferencistasNacVinc = conferencistasNacVinc;
	}


	public String getConferencistasNacVinc() {
		return conferencistasNacVinc;
	}


	public void setConferencistasInternac(String conferencistasInternac) {
		this.conferencistasInternac = conferencistasInternac;
	}


	public String getConferencistasInternac() {
		return conferencistasInternac;
	}


	public void setTipoParticipacion(String tipoParticipacion) {
		this.tipoParticipacion = tipoParticipacion;
	}


	public String getTipoParticipacion() {
		return tipoParticipacion;
	}


	public void setFechaFinalizacionEvento(Date fechaFinalizacionEvento) {
		this.fechaFinalizacionEvento = fechaFinalizacionEvento;
	}


	public Date getFechaFinalizacionEvento() {
		return fechaFinalizacionEvento;
	}


	public void setOtroTipo(String otroTipo) {
		this.otroTipo = otroTipo;
	}


	public String getOtroTipo() {
		return otroTipo;
	}


	public String getConferencistas() {
		return conferencistas;
	}


	public void setConferencistas(String conferencistas) {
		this.conferencistas = conferencistas;
	}


	public String getActividadesRealizadas() {
		return actividadesRealizadas;
	}


	public void setActividadesRealizadas(String actividadesRealizadas) {
		this.actividadesRealizadas = actividadesRealizadas;
	}


	public String getAsistentesEvento() {
		return asistentesEvento;
	}


	public void setAsistentesEvento(String asistentesEvento) {
		this.asistentesEvento = asistentesEvento;
	}


	public String getAlianzas() {
		return alianzas;
	}


	public void setAlianzas(String alianzas) {
		this.alianzas = alianzas;
	}


	public String getFinanciacionUN() {
		return financiacionUN;
	}


	public void setFinanciacionUN(String financiacionUN) {
		this.financiacionUN = financiacionUN;
	}


	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


	public void setResultados(String resultados) {
		this.resultados = resultados;
	}


	public String getResultados() {
		return resultados;
	}


	public void setObservaciones_VRI(String observaciones_VRI) {
		this.observaciones_VRI = observaciones_VRI;
	}


	public String getObservaciones_VRI() {
		return observaciones_VRI;
	}




	
}
